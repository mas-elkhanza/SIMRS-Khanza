/*
  by Ananda Widitomo,S.Kom.
  IT - SIMRS Hj. Fatimah Sulhan
 */
package bridging;
import bridging.ApiSatuSehat;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fungsi.koneksiDB;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

/**
 * TTE — pembuat permintaan tanda tangan lewat SATU Bundle transaction (Provenance + Task).
 *
 * Mengikuti koleksi Postman resmi Kemenkes "00. TTE versi 21072026" -> "02. Bundle Buat TTE":
 * satu Bundle (type=transaction) berisi N entry placeholder Provenance + 1 entry Task; Task.input
 * mereferensikan tiap Provenance via "urn:uuid:&lt;fullUrl&gt;" sehingga FHIR meng-resolve referensi
 * antar-entry saat commit. Keunggulan vs POST individual (SatuSehatProvenance + SatuSehatTask):
 * ATOMIK — bila Task gagal, seluruh Provenance ikut batal (tak ada placeholder yatim di server).
 *
 * Endpoint = ROOT FHIR base (POST {FHIR_BASE}) — pola sama dgn {@link SatuSehatBundle}.
 *
 * Alur:
 *   1) kirimBundle(daftar dokumen, DPJP, encounter) -> HasilBundle{taskId, provIds[]}.
 *   2) taskId di-encode ke QR (SatuSehatSignatureState) & dipindai SATUSEHAT Mobile.
 *   3) SSM menandatangani -> Provenance.signature terisi & Task.status=completed.
 *
 * Payload Provenance dibangun ulang {@link SatuSehatProvenance#bangunProvenance} dan Task
 * {@link SatuSehatTask#bangunTaskDenganRef} agar konsisten dgn jalur POST individual.
 */
public class SatuSehatBundleProvenance {

    private final ApiSatuSehat api = new ApiSatuSehat();
    private final ObjectMapper mapper = new ObjectMapper();
    private String link = "";
    private String idOrg = "";
    private String namaOrg = "";

    /** Rekaman kiriman terakhir (payload + balasan) untuk diarsipkan pemanggil ke DB. */
    public final SatuSehatKiriman terakhir = new SatuSehatKiriman();

    public SatuSehatBundleProvenance() {
        try {
            link = koneksiDB.URLFHIRSATUSEHAT();
            idOrg = koneksiDB.IDSATUSEHAT();
        } catch (Exception e) {
            System.out.println("Notifikasi BundleTTE : " + e);
        }
        try {
            java.sql.PreparedStatement ps = koneksiDB.condb().prepareStatement(
                    "select nama_instansi from setting");
            java.sql.ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                namaOrg = rs.getString(1) == null ? "" : rs.getString(1);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            System.out.println("Notifikasi BundleTTE namaOrg : " + e);
        }
    }

    /** Satu target dokumen yang di-TTE (murni data, tanpa ketergantungan UI). */
    public static class Target {
        public final String targetRef;   // "Composition/<id>", "DiagnosticReport/<id>", "ServiceRequest/<id>"
        public final String display;     // label dokumen
        public final String mulaiUtc;    // occurredPeriod.start (null/"" -> recorded)
        public final String selesaiUtc;  // occurredPeriod.end   (null/"" -> recorded)

        public Target(String targetRef, String display, String mulaiUtc, String selesaiUtc) {
            this.targetRef = targetRef;
            this.display = display;
            this.mulaiUtc = mulaiUtc;
            this.selesaiUtc = selesaiUtc;
        }
    }

    /** Hasil commit Bundle: id Task + id Provenance server (urut sesuai daftar target). */
    public static class HasilBundle {
        public String taskId = "";
        public final List<String> provIds = new ArrayList<>();
    }

    /**
     * Bangun & kirim Bundle transaction untuk sekumpulan dokumen milik SATU dokter penanda-tangan.
     *
     * @param targets        daftar dokumen (target Provenance) — minimal satu.
     * @param idPractitioner IHS Practitioner penanda-tangan (agent author Provenance & requester Task).
     * @param namaDpjp       nama dokter (display).
     * @param idEncounter    Encounter yang sudah dikirim (Task.encounter wajib — RuleNumber 10875).
     * @return HasilBundle berisi Task id + Provenance id (urut sama dgn targets).
     */
    public HasilBundle kirimBundle(List<Target> targets, String idPractitioner, String namaDpjp,
                                   String idEncounter) throws Exception {
        return kirimBundle(targets, idPractitioner, namaDpjp, idEncounter, false);
    }

    /**
     * Varian yang bisa memakai Task bulk lintas-encounter "electronic-sign-doc-out".
     *
     * @param bulkOut true = Task tanpa encounter; hanya sah bila SELURUH target DiagnosticReport
     *                (RuleNumber 10871, diuji ke staging 1 Agustus 2026). Lihat
     *                {@link SatuSehatTask#buatTaskBulkDiagnosticReport}.
     */
    public HasilBundle kirimBundle(List<Target> targets, String idPractitioner, String namaDpjp,
                                   String idEncounter, boolean bulkOut) throws Exception {
        if (idPractitioner == null || idPractitioner.trim().equals("")) {
            throw new IllegalArgumentException("IHS Practitioner (DPJP) belum ter-mapping");
        }
        if (!bulkOut && (idEncounter == null || idEncounter.trim().equals(""))) {
            throw new IllegalArgumentException("Encounter belum terkirim ke SATUSEHAT (Task.encounter wajib - RuleNumber 10875)");
        }
        if (targets == null || targets.isEmpty()) {
            throw new IllegalArgumentException("Minimal satu dokumen diperlukan untuk Bundle TTE");
        }
        List<String> provUuids = new ArrayList<>();
        for (int i = 0; i < targets.size(); i++) {
            provUuids.add(UUID.randomUUID().toString());
        }
        String taskUuid = UUID.randomUUID().toString();

        ObjectNode bundle = bangunBundle(mapper, targets, provUuids, taskUuid,
                idPractitioner, namaDpjp, idEncounter, idOrg, namaOrg, nowUtc(), bulkOut);

        JsonNode resp = mapper.readTree(post(link, bundle));
        try {
            System.out.println("Response JSON Bundle TTE : "
                    + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(resp));
        } catch (Exception e) {
            System.out.println("Notifikasi BundleTTE cetak response : " + e);
        }
        HasilBundle hasil = bacaHasil(resp);
        cetakRingkasan(hasil, targets, resp);
        return hasil;
    }

    /**
     * Cetak ringkasan hasil Bundle ke konsol: satu baris per resource (Provenance & Task) berisi
     * id, status HTTP per-entry, dokumen sumber, dan URL FHIR-nya. Tujuannya agar operator/IT bisa
     * menelusuri langsung ke server tanpa membaca JSON mentah. Murni logging - tidak melempar
     * exception apa pun agar kegagalan cetak tak pernah membatalkan pengiriman yang sudah sukses.
     */
    private void cetakRingkasan(HasilBundle hasil, List<Target> targets, JsonNode resp) {
        try {
            System.out.println("=== Hasil Bundle TTE : " + targets.size() + " dokumen ===");
            System.out.println("  Task       : " + hasil.taskId
                    + "   [HTTP " + statusEntry(resp, "Task") + "]");
            System.out.println("  URL Task   : " + link + "/Task/" + hasil.taskId);
            for (int i = 0; i < hasil.provIds.size(); i++) {
                String pid = hasil.provIds.get(i);
                String dok = (i < targets.size()) ? nz(targets.get(i).display) : "";
                String ref = (i < targets.size()) ? nz(targets.get(i).targetRef) : "";
                System.out.println("  [" + (i + 1) + "] Provenance : " + pid
                        + "   [HTTP " + statusEntryKe(resp, "Provenance", i) + "]"
                        + (dok.equals("") ? "" : "   dokumen: " + dok)
                        + (ref.equals("") ? "" : "   target: " + ref));
                System.out.println("      URL      : " + link + "/Provenance/" + pid);
            }
            System.out.println("  Catatan    : signature masih kosong; akan diisi SATUSEHAT Mobile (SSM)"
                    + " setelah DPJP menandatangani lewat QR.");
        } catch (Exception e) {
            System.out.println("Notifikasi BundleTTE ringkasan : " + e);
        }
    }

    /** Status HTTP entry pertama bertipe {@code type} pada transaction-response ("?" bila tak ada). */
    private static String statusEntry(JsonNode resp, String type) {
        return statusEntryKe(resp, type, 0);
    }

    /** Status HTTP entry ke-{@code urutan} (0-based) bertipe {@code type} ("?" bila tak ada). */
    private static String statusEntryKe(JsonNode resp, String type, int urutan) {
        if (resp == null) {
            return "?";
        }
        int ketemu = 0;
        for (JsonNode entry : resp.path("entry")) {
            String loc = entry.path("response").path("location").asText();
            if (idDari(loc, type).equals("")) {
                continue;
            }
            if (ketemu == urutan) {
                String st = entry.path("response").path("status").asText();
                return st.equals("") ? "?" : st;
            }
            ketemu++;
        }
        return "?";
    }

    /** null-safe: null -&gt; "". */
    private static String nz(String s) {
        return s == null ? "" : s.trim();
    }

    /**
     * Bangun Bundle FHIR (type=transaction) — MURNI, tanpa I/O, deterministik terhadap uuid & recorded
     * (bisa diuji unit). Entry Provenance memakai {@link SatuSehatProvenance#bangunProvenance}; entry
     * Task memakai {@link SatuSehatTask#bangunTaskDenganRef} dgn input ref "urn:uuid:&lt;provUuid&gt;".
     *
     * @param provUuids fullUrl UUID tiap Provenance (ukuran == targets); di-refer Task.input.
     * @param taskUuid  fullUrl UUID Task.
     */
    public static ObjectNode bangunBundle(ObjectMapper mapper, List<Target> targets,
                                          List<String> provUuids, String taskUuid,
                                          String idPractitioner, String namaDpjp, String idEncounter,
                                          String idOrg, String namaOrg, String recordedUtc) {
        return bangunBundle(mapper, targets, provUuids, taskUuid, idPractitioner, namaDpjp,
                idEncounter, idOrg, namaOrg, recordedUtc, false);
    }

    /** Varian dengan pilihan Task bulk lintas-encounter; lihat {@link #kirimBundle(List, String, String, String, boolean)}. */
    public static ObjectNode bangunBundle(ObjectMapper mapper, List<Target> targets,
                                          List<String> provUuids, String taskUuid,
                                          String idPractitioner, String namaDpjp, String idEncounter,
                                          String idOrg, String namaOrg, String recordedUtc,
                                          boolean bulkOut) {
        if (targets == null || targets.isEmpty()) {
            throw new IllegalArgumentException("targets kosong");
        }
        if (provUuids == null || provUuids.size() != targets.size()) {
            throw new IllegalArgumentException("Jumlah provUuids harus sama dengan targets");
        }
        ObjectNode bundle = mapper.createObjectNode();
        bundle.put("resourceType", "Bundle");
        bundle.put("type", "transaction");
        ArrayNode entries = bundle.putArray("entry");

        // Entry Provenance (satu per target).
        List<String> taskRefs = new ArrayList<>();
        for (int i = 0; i < targets.size(); i++) {
            Target t = targets.get(i);
            String provUuid = provUuids.get(i);
            ObjectNode entry = entries.addObject();
            entry.put("fullUrl", "urn:uuid:" + provUuid);
            entry.set("resource", SatuSehatProvenance.bangunProvenance(mapper, t.targetRef, t.display,
                    idPractitioner, namaDpjp, idOrg, namaOrg, recordedUtc, t.mulaiUtc, t.selesaiUtc));
            ObjectNode req = entry.putObject("request");
            req.put("method", "POST");
            req.put("url", "Provenance");
            taskRefs.add("urn:uuid:" + provUuid);
        }

        // Entry Task (membungkus seluruh Provenance via urn:uuid).
        ObjectNode entryTask = entries.addObject();
        entryTask.put("fullUrl", "urn:uuid:" + taskUuid);
        entryTask.set("resource", SatuSehatTask.bangunTaskDenganRef(mapper, idPractitioner, namaDpjp,
                idEncounter, idOrg, namaOrg, recordedUtc, taskRefs, bulkOut));
        ObjectNode reqTask = entryTask.putObject("request");
        reqTask.put("method", "POST");
        reqTask.put("url", "Task");
        return bundle;
    }

    /**
     * Baca Bundle transaction-response: ekstrak id Provenance (urut) & id Task dari entry.response.location
     * ("Provenance/&lt;id&gt;/_history/..", "Task/&lt;id&gt;/.."). Fallback ke entry.resource.id bila location kosong.
     */
    public HasilBundle bacaHasil(JsonNode resp) {
        HasilBundle h = new HasilBundle();
        if (resp == null) {
            return h;
        }
        for (JsonNode entry : resp.path("entry")) {
            String loc = entry.path("response").path("location").asText();
            String prov = idDari(loc, "Provenance");
            String task = idDari(loc, "Task");
            if (prov.equals("") && task.equals("")) {
                // Fallback: sebagian server sertakan resource pada response.
                String rt = entry.path("resource").path("resourceType").asText();
                String id = entry.path("resource").path("id").asText();
                if ("Provenance".equals(rt)) {
                    prov = id;
                } else if ("Task".equals(rt)) {
                    task = id;
                }
            }
            if (!prov.equals("")) {
                h.provIds.add(prov);
            }
            if (!task.equals("")) {
                h.taskId = task;
            }
        }
        return h;
    }

    /** Ekstrak id resource dari string location: "...{type}/{id}[/...]" -> {id}; "" bila tak ada. */
    static String idDari(String location, String type) {
        if (location == null || type == null) {
            return "";
        }
        String kunci = type + "/";
        int idx = location.indexOf(kunci);
        if (idx < 0) {
            return "";
        }
        String sisa = location.substring(idx + kunci.length());
        int potong = sisa.indexOf('/');
        if (potong >= 0) {
            sisa = sisa.substring(0, potong);
        }
        return sisa.trim();
    }

    /** Timestamp UTC ISO-8601 offset "+00:00" (recorded/occurredPeriod/authoredOn). */
    private String nowUtc() {
        return java.time.OffsetDateTime.now(java.time.ZoneOffset.UTC)
                .format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssxxx"));
    }

    /** POST Bundle ke ROOT FHIR; kembalikan response body. */
    private String post(String url, JsonNode body) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + api.TokenSatuSehat());
        String payload = mapper.writeValueAsString(body);
        terakhir.mulai(payload);
        // Dicetak RAPI: Bundle untuk beberapa berkas sangat panjang, dan versi satu baris praktis
        // tak terbaca di jendela Output. Yang DIARSIPKAN tetap payload mentah di atas.
        System.out.println("Request JSON Bundle TTE :\n"
                + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(body));
        HttpEntity requestEntity = new HttpEntity(payload, headers);
        try {
            org.springframework.http.ResponseEntity<String> re =
                    api.getRest().exchange(url, HttpMethod.POST, requestEntity, String.class);
            terakhir.sukses(re.getStatusCode().value(), re.getBody());
            return re.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            System.out.println("Error Bundle TTE Status Code: " + ex.getStatusCode());
            String errBody = ex.getResponseBodyAsString();
            terakhir.gagal(ex.getStatusCode().value(), errBody, ex.getStatusCode().toString());
            try {
                JsonNode err = mapper.readTree(errBody);
                System.out.println("Error Bundle TTE OperationOutcome:\n"
                        + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(err));
            } catch (Exception e2) {
                System.out.println("Error Bundle TTE Body: " + errBody);
            }
            throw ex;
        } catch (Exception ex) {
            // Gagal sebelum sempat menerima balasan (DNS mati, timeout, token gagal) — tetap
            // direkam supaya arsip memuat payload yang sudah terlanjur disusun.
            terakhir.gagal(0, "", String.valueOf(ex));
            throw ex;
        }
    }
}

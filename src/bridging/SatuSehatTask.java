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
import java.util.List;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

/**
 * TTE (Tanda Tangan Elektronik) — pembuat Task penggabung permintaan tanda tangan.
 *
 * Satu Task membungkus satu (single) atau banyak (bulk) placeholder Provenance melalui
 * Task.input[]. Task UUID inilah yang di-encode ke QR (bersama timestamp) dan dipindai
 * SATUSEHAT Mobile. Setelah SSM menandatangani, Task.status menjadi "completed".
 * Referensi: Panduan Teknis TTE v1.1 (Kemenkes), Flow 1 + "Payload POST Task (Initialize)".
 *
 * Alur:
 *   1) {@link SatuSehatProvenance#buatPlaceholder} -> daftar Provenance ID.
 *   2) buatTask(idPractitioner, provenanceIds) -> Task UUID (status=requested).
 *   3) bacaTask(taskUuid) di-poll berkala untuk cek status (requested/in-progress/
 *      completed/rejected) dan mengambil Task.output saat verifikasi.
 *
 * CATATAN (dikonfirmasi saat bootcamp 20-24 Juli 2026):
 *   - Sampel input pada panduan malformed; di sini tiap elemen input dibentuk valid:
 *       { "type": { "coding":[...] }, "valueReference": { "reference":"Provenance/<id>" } }.
 *   - Endpoint diasumsikan POST individual "{FHIR_BASE}/Task"; ubah {@link #post} bila
 *     platform mensyaratkan Bundle transaction.
 */
public class SatuSehatTask {

    private static final String SYS_KEMKES = "http://terminology.kemkes.go.id";

    private final ApiSatuSehat api = new ApiSatuSehat();
    private final ObjectMapper mapper = new ObjectMapper();
    private String link = "";
    private String idOrg = "";
    private String namaOrg = "";

    /** Rekaman kiriman terakhir (payload + balasan) untuk diarsipkan pemanggil ke DB. */
    public final SatuSehatKiriman terakhir = new SatuSehatKiriman();

    public SatuSehatTask() {
        try {
            link = koneksiDB.URLFHIRSATUSEHAT();
            idOrg = koneksiDB.IDSATUSEHAT();
        } catch (Exception e) {
            System.out.println("Notifikasi TaskTTE : " + e);
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
            System.out.println("Notifikasi TaskTTE namaOrg : " + e);
        }
    }

    /**
     * Buat Task (status=requested) yang membungkus seluruh Provenance.
     *
     * @param idPractitioner IHS Practitioner id DPJP pemohon (tanpa prefix).
     * @param provenanceIds  daftar Provenance ID (single -> 1 elemen, bulk -> banyak).
     * @return Task UUID hasil server (kosong "" bila gagal parse).
     */
    public String buatTask(String idPractitioner, String namaDpjp, String idEncounter,
                           List<String> provenanceIds) throws Exception {
        if (idPractitioner == null || idPractitioner.trim().equals("")) {
            throw new IllegalArgumentException("IHS Practitioner (DPJP) belum ter-mapping");
        }
        if (idEncounter == null || idEncounter.trim().equals("")) {
            throw new IllegalArgumentException("Encounter belum terkirim ke SATUSEHAT (Task.encounter wajib - RuleNumber 10875)");
        }
        if (provenanceIds == null || provenanceIds.isEmpty()) {
            throw new IllegalArgumentException("Minimal satu Provenance diperlukan untuk Task TTE");
        }
        ObjectNode task = bangunTask(mapper, idPractitioner, namaDpjp, idEncounter, idOrg, namaOrg, nowUtc(), provenanceIds);

        JsonNode resp = mapper.readTree(post(link + "/Task", task));
        String uuid = resp.path("id").asText();
        if (uuid == null) {
            uuid = "";
        }
        System.out.println("Result Task UUID : " + uuid);
        return uuid;
    }

    /**
     * Buat Task varian BULK lintas-encounter ("electronic-sign-doc-out", koleksi resmi
     * "00. TTE versi 24072026" → Task - Multiple Provenance + Multiple Encounter).
     *
     * ⚠ Varian ini BUKAN sekadar "Task tanpa encounter". Diuji ke staging 1 Agustus 2026:
     * server menolaknya dengan <code>Provenance target must contain DiagnosticReport
     * (RuleNumber: 10871)</code> bila ada Provenance yang target-nya BUKAN DiagnosticReport —
     * jadi ia khusus untuk penandatanganan expertise penunjang (lab/radiologi) yang memang
     * lintas kunjungan. Dengan target DiagnosticReport, Task tanpa encounter diterima 201.
     *
     * Pemanggil WAJIB memastikan seluruh target Provenance berupa DiagnosticReport; pakai
     * {@link #semuaTargetDiagnosticReport(java.util.List)} untuk memeriksanya.
     */
    public String buatTaskBulkDiagnosticReport(String idPractitioner, String namaDpjp,
                                               List<String> provenanceIds) throws Exception {
        if (idPractitioner == null || idPractitioner.trim().equals("")) {
            throw new IllegalArgumentException("IHS Practitioner (DPJP) belum ter-mapping");
        }
        if (provenanceIds == null || provenanceIds.isEmpty()) {
            throw new IllegalArgumentException("Minimal satu Provenance diperlukan untuk Task TTE");
        }
        java.util.List<String> refs = new java.util.ArrayList<>();
        for (String provId : provenanceIds) {
            if (provId != null && !provId.trim().equals("")) {
                refs.add("Provenance/" + provId.trim());
            }
        }
        ObjectNode task = bangunTaskDenganRef(mapper, idPractitioner, namaDpjp, "", idOrg, namaOrg,
                nowUtc(), refs, true);

        JsonNode resp = mapper.readTree(post(link + "/Task", task));
        String uuid = resp.path("id").asText();
        if (uuid == null) {
            uuid = "";
        }
        System.out.println("Result Task UUID (bulk out) : " + uuid);
        return uuid;
    }

    /**
     * true bila SELURUH referensi target berupa DiagnosticReport — syarat varian
     * "electronic-sign-doc-out" (RuleNumber 10871). Daftar kosong dianggap tidak memenuhi.
     */
    public static boolean semuaTargetDiagnosticReport(List<String> targetRefs) {
        if (targetRefs == null || targetRefs.isEmpty()) {
            return false;
        }
        for (String ref : targetRefs) {
            if (ref == null || !ref.trim().startsWith("DiagnosticReport/")) {
                return false;
            }
        }
        return true;
    }

    /**
     * Bangun resource FHIR Task (status=requested) pembungkus Provenance — murni, tanpa I/O.
     * Struktur mengikuti koleksi Postman resmi Kemenkes "00. TTE versi 21072026" (01. Buat TTE):
     * code=electronic-sign-doc + authoredOn/lastModified. Tiap Provenance jadi satu elemen
     * input[] yang valid: {type, valueReference}. Referensi Provenance dibentuk "Provenance/&lt;id&gt;".
     * @param authoredOn timestamp UTC ISO (authoredOn = lastModified); null/"" -> field di-skip.
     * @throws IllegalArgumentException bila seluruh Provenance ID kosong.
     */
    public static ObjectNode bangunTask(ObjectMapper mapper, String idPractitioner, String namaDpjp,
                                        String idEncounter, String idOrg, String namaOrg,
                                        String authoredOn, List<String> provenanceIds) {
        java.util.List<String> refs = new java.util.ArrayList<>();
        if (provenanceIds != null) {
            for (String provId : provenanceIds) {
                if (provId != null && !provId.trim().equals("")) {
                    refs.add("Provenance/" + provId.trim());
                }
            }
        }
        return bangunTaskDenganRef(mapper, idPractitioner, namaDpjp, idEncounter, idOrg, namaOrg, authoredOn, refs);
    }

    /**
     * Varian {@link #bangunTask} yang menerima REFERENSI Provenance apa adanya (tanpa prefix),
     * mis. "urn:uuid:&lt;uuid&gt;" untuk Task di dalam Bundle transaction ("02. Bundle Buat TTE"),
     * atau "Provenance/&lt;id&gt;" untuk POST individual. Dipakai {@link SatuSehatBundleProvenance}.
     * @param provenanceRefs daftar referensi input Provenance (dipakai verbatim).
     * @throws IllegalArgumentException bila seluruh referensi kosong.
     */
    public static ObjectNode bangunTaskDenganRef(ObjectMapper mapper, String idPractitioner, String namaDpjp,
                                                 String idEncounter, String idOrg, String namaOrg,
                                                 String authoredOn, List<String> provenanceRefs) {
        return bangunTaskDenganRef(mapper, idPractitioner, namaDpjp, idEncounter, idOrg, namaOrg,
                authoredOn, provenanceRefs, false);
    }

    /**
     * Varian yang memilih bentuk Task secara EKSPLISIT.
     *
     * @param bulkOut false = "electronic-sign-doc" + encounter (Task biasa, RuleNumber 10875);
     *                true  = "electronic-sign-doc-out" TANPA encounter — hanya sah bila seluruh
     *                target Provenance berupa DiagnosticReport (RuleNumber 10871), lihat
     *                {@link #buatTaskBulkDiagnosticReport}.
     */
    public static ObjectNode bangunTaskDenganRef(ObjectMapper mapper, String idPractitioner, String namaDpjp,
                                                 String idEncounter, String idOrg, String namaOrg,
                                                 String authoredOn, List<String> provenanceRefs,
                                                 boolean bulkOut) {
        ObjectNode task = mapper.createObjectNode();
        task.put("resourceType", "Task");
        task.put("status", "requested");
        task.put("intent", "order");
        task.put("priority", "routine");
        // code : DUA varian dari koleksi resmi "00. TTE versi 24072026" (folder "01. Buat TTE/Task"):
        //   - electronic-sign-doc      : Task biasa, WAJIB ber-encounter (RuleNumber 10875)
        //   - electronic-sign-doc-out  : bulk lintas-encounter, TANPA encounter, dan hanya sah bila
        //                                seluruh target Provenance DiagnosticReport (RuleNumber 10871)
        // Pilihannya EKSPLISIT lewat parameter bulkOut, bukan ditebak dari kosongnya idEncounter:
        // uji staging 1 Agustus 2026 membuktikan Task tanpa encounter untuk target Composition
        // DITOLAK, jadi "tidak ada encounter" bukan alasan yang sah untuk memakai varian bulk.
        boolean adaEncounter = !bulkOut && idEncounter != null && !idEncounter.trim().equals("");
        ObjectNode code = task.putObject("code");
        ObjectNode codeCoding = code.putArray("coding").addObject();
        codeCoding.put("system", SYS_KEMKES);
        codeCoding.put("code", bulkOut ? "electronic-sign-doc-out" : "electronic-sign-doc");
        codeCoding.put("display", bulkOut
                ? "Penandatanganan dokumen elektronik Bulk"
                : "Penandatanganan dokumen elektronik");
        code.put("text", "Penandatanganan elektronik dokumen");
        // authoredOn & lastModified (wajib di contoh resmi).
        if (authoredOn != null && !authoredOn.trim().equals("")) {
            task.put("authoredOn", authoredOn.trim());
            task.put("lastModified", authoredOn.trim());
        }
        ObjectNode requester = task.putObject("requester");
        requester.put("reference", "Practitioner/" + idPractitioner);
        if (namaDpjp != null && !namaDpjp.trim().equals("")) {
            requester.put("display", namaDpjp.trim());
        }
        ObjectNode owner = task.putObject("owner");
        owner.put("reference", "Organization/" + idOrg);
        if (namaOrg != null && !namaOrg.trim().equals("")) {
            owner.put("display", namaOrg.trim());
        }
        // encounter WAJIB untuk varian "electronic-sign-doc" (RuleNumber 10875); pada varian bulk
        // "electronic-sign-doc-out" field ini sengaja tidak ada.
        if (adaEncounter) {
            task.putObject("encounter").put("reference", "Encounter/" + idEncounter.trim());
        }

        // input[] : satu elemen per Provenance (single & bulk seragam). Referensi dipakai apa adanya
        // ("Provenance/<id>" untuk POST individual, "urn:uuid:<uuid>" untuk Bundle transaction).
        ArrayNode input = task.putArray("input");
        if (provenanceRefs != null) {
            for (String ref : provenanceRefs) {
                if (ref == null || ref.trim().equals("")) {
                    continue;
                }
                ObjectNode item = input.addObject();
                ObjectNode typeCoding = item.putObject("type").putArray("coding").addObject();
                typeCoding.put("system", SYS_KEMKES);
                typeCoding.put("code", "document-provenance");
                typeCoding.put("display", "Document Provenance");
                item.putObject("valueReference").put("reference", ref.trim());
            }
        }
        if (input.size() == 0) {
            throw new IllegalArgumentException("Seluruh referensi Provenance kosong; Task tidak dibuat");
        }
        return task;
    }

    /**
     * GET Task untuk polling status (Flow 4). Dipanggil berkala oleh dialog QR.
     * @return JsonNode resource Task, atau null bila gagal.
     */
    public JsonNode bacaTask(String taskUuid) {
        if (taskUuid == null || taskUuid.trim().equals("")) {
            return null;
        }
        try {
            HttpHeaders h = new HttpHeaders();
            h.add("Authorization", "Bearer " + api.TokenSatuSehat());
            HttpEntity req = new HttpEntity(h);
            java.net.URI uri = java.net.URI.create(link + "/Task/" + taskUuid);
            String hasil = api.getRest().exchange(uri, HttpMethod.GET, req, String.class).getBody();
            return mapper.readTree(hasil);
        } catch (Exception e) {
            System.out.println("Notifikasi TaskTTE bacaTask : " + e);
            return null;
        }
    }

    /** Ambil Task.status ("requested" bila null/gagal). */
    public String status(JsonNode task) {
        if (task == null) {
            return "requested";
        }
        String s = task.path("status").asText();
        return (s == null || s.equals("")) ? "requested" : s;
    }

    /** true bila Task selesai ditandatangani. */
    public boolean selesai(JsonNode task) {
        return "completed".equalsIgnoreCase(status(task));
    }

    /** true bila permintaan TTE ditolak DPJP di SSM. */
    public boolean ditolak(JsonNode task) {
        return "rejected".equalsIgnoreCase(status(task));
    }

    /**
     * Peta hasil TTE per dokumen dari Task.output[] — SUMBER KEBENARAN per-Provenance.
     *
     * Saat SSM menuntaskan penandatanganan lalu PUT Task, Task.output[] berjumlah SAMA dengan
     * Task.input[] (satu elemen per Provenance) — dikonfirmasi Kemenkes TTDK (23 Juli 2026).
     * Tiap elemen output: {type.coding[0].code ∈ (signed|failed|rejected), valueReference:"Provenance/&lt;id&gt;"}.
     *   - signed   : dokumen berhasil ditandatangani.
     *   - failed   : penandatanganan gagal (mis. galat BSrE / validasi server) — bisa diulang.
     *   - rejected : DPJP menolak permintaan di SSM — bisa diulang.
     * Untuk TTE bulk, satu Task bisa {@code status=completed} dengan CAMPURAN outcome; karena itu status
     * per dokumen HARUS diambil dari output[] ini, bukan sekadar Task.status keseluruhan.
     *
     * @return map id-Provenance (tanpa prefix "Provenance/") -&gt; kode outcome (lowercase);
     *         kosong bila output[] belum ada (Task belum dituntaskan SSM).
     */
    public java.util.Map<String, String> hasilPerProvenance(JsonNode task) {
        java.util.Map<String, String> hasil = new java.util.LinkedHashMap<>();
        if (task == null) {
            return hasil;
        }
        JsonNode outputs = task.path("output");
        if (!outputs.isArray()) {
            return hasil;
        }
        for (JsonNode o : outputs) {
            String kode = "";
            JsonNode coding = o.path("type").path("coding");
            if (coding.isArray() && coding.size() > 0) {
                kode = coding.get(0).path("code").asText();
            }
            String ref = o.path("valueReference").path("reference").asText();
            if (ref == null) {
                ref = "";
            }
            if (ref.startsWith("Provenance/")) {
                ref = ref.substring("Provenance/".length());
            }
            if (!ref.trim().equals("") && kode != null && !kode.trim().equals("")) {
                hasil.put(ref.trim(), kode.trim().toLowerCase());
            }
        }
        return hasil;
    }

    /**
     * Cek konsistensi Task.output terhadap Task.input: SSM menjanjikan {@code |output| == |input|}
     * saat Task dituntaskan. Bila keduanya &gt; 0 tapi berbeda, ada dokumen yang hasilnya belum
     * terlaporkan — pemanggil sebaiknya tetap memakai jejak per-Provenance yang ADA dan menunggu
     * sisanya, bukan menyimpulkan seluruh Task selesai. "" bila seimbang / belum ada output.
     *
     * @return pesan peringatan singkat bila timpang; "" bila seimbang atau output belum terisi.
     */
    public String cekTimpangInputOutput(JsonNode task) {
        if (task == null) {
            return "";
        }
        int inN = task.path("input").isArray() ? task.path("input").size() : 0;
        int outN = task.path("output").isArray() ? task.path("output").size() : 0;
        if (outN == 0 || inN == outN) {
            return "";
        }
        return "Task.output(" + outN + ") != Task.input(" + inN + ")";
    }

    /**
     * Ambil trace-id dari Task.meta.tag (diisi SSM saat Task selesai — lihat "03. SSM" Task completed:
     * meta.tag[system="http://terminology.kemkes.go.id/trace-id"].code). "" bila tak ada.
     */
    public String traceId(JsonNode task) {
        if (task == null) {
            return "";
        }
        JsonNode tags = task.path("meta").path("tag");
        if (tags.isArray()) {
            for (JsonNode t : tags) {
                String sys = t.path("system").asText();
                if (sys != null && sys.contains("trace-id")) {
                    String c = t.path("code").asText();
                    return c == null ? "" : c;
                }
            }
        }
        return "";
    }

    /** Timestamp UTC ISO-8601 dengan offset "+00:00" (untuk authoredOn/lastModified Task). */
    private String nowUtc() {
        return java.time.OffsetDateTime.now(java.time.ZoneOffset.UTC)
                .format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssxxx"));
    }

    /** POST resource FHIR; kembalikan response body. Isolasi bentuk endpoint (individual vs bundle). */
    private String post(String url, JsonNode body) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + api.TokenSatuSehat());
        String payload = mapper.writeValueAsString(body);
        terakhir.mulai(payload);
        System.out.println("Request JSON TaskTTE :\n"
                + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(body));
        HttpEntity requestEntity = new HttpEntity(payload, headers);
        try {
            org.springframework.http.ResponseEntity<String> re =
                    api.getRest().exchange(url, HttpMethod.POST, requestEntity, String.class);
            terakhir.sukses(re.getStatusCode().value(), re.getBody());
            return re.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            System.out.println("Error TaskTTE Status Code: " + ex.getStatusCode());
            String errBody = ex.getResponseBodyAsString();
            terakhir.gagal(ex.getStatusCode().value(), errBody, ex.getStatusCode().toString());
            try {
                JsonNode err = mapper.readTree(errBody);
                System.out.println("Error TaskTTE OperationOutcome:\n"
                        + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(err));
            } catch (Exception e2) {
                System.out.println("Error TaskTTE Body: " + errBody);
            }
            throw ex;
        } catch (Exception ex) {
            terakhir.gagal(0, "", String.valueOf(ex));
            throw ex;
        }
    }
}

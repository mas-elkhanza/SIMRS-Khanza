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
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

/**
 * TTE (Tanda Tangan Elektronik) — pembuat placeholder Provenance untuk 1 dokumen RME.
 *
 * SIMRS TIDAK menandatangani sendiri. Kelas ini hanya membuat "Placeholder Provenance"
 * (signature = []) di platform SATUSEHAT; penandatanganan sesungguhnya dilakukan oleh
 * SATUSEHAT Mobile (SSM) + BSrE, lalu SSM meng-update Provenance.signature.
 * Referensi: Panduan Teknis TTE v1.1 (Kemenkes), Flow 1 + "Payload POST Provenance (Initialize)".
 *
 * Alur pemakaian:
 *   1) buatPlaceholder(...) per dokumen -> dapat Provenance ID (server assign).
 *   2) Kumpulan Provenance ID dipakai {@link SatuSehatTask} untuk membuat satu Task.
 *   3) Saat verifikasi, bacaProvenance(id) untuk mengambil signature yang sudah terisi.
 *
 * target Provenance mereferensikan resource yang SUDAH dikirim sender lain (Composition
 * resume medis, ServiceRequest SPRI, DiagnosticReport lab/radiologi, dst) — id resource
 * tersebut sudah tersimpan di tabel satu_sehat_* masing-masing.
 *
 * Pembangunan payload dipisah ke {@link #bangunProvenance} (statik, murni tanpa I/O) agar
 * bisa diuji unit tanpa jaringan; buatPlaceholder menambahkan pengiriman HTTP-nya.
 *
 * CATATAN (dikonfirmasi saat bootcamp 20-24 Juli 2026):
 *   - Endpoint diasumsikan POST individual "{FHIR_BASE}/Provenance". Bila platform
 *     mensyaratkan Bundle transaction, cukup ubah method {@link #post(String, JsonNode)}.
 */
public class SatuSehatProvenance {

    // activity PLACEHOLDER memakai v3-DataOperation / "CREATE" — permintaan tanda tangan, BUKAN hasil.
    //
    // Sebelumnya kode ini mengirim v3-DocumentCompletion / "LA" (Legally Authenticated) dengan alasan
    // "agar konsisten dengan resource hasil". Itu keliru: LA menggambarkan dokumen yang SUDAH sah
    // ditandatangani, sedangkan yang kita POST baru permintaannya. Koleksi resmi "00. TTE versi
    // 04082026" memastikannya — dari 10 contoh placeholder, SEPULUH memakai DataOperation/CREATE dan
    // TIDAK SATU PUN memakai LA. Nilai LA hanya muncul pada resource hasil yang ditulis SSM.
    private static final String SYS_DATA_OPERATION =
            "http://terminology.hl7.org/CodeSystem/v3-DataOperation";
    private static final String SYS_PARTICIPANT_TYPE =
            "http://terminology.hl7.org/CodeSystem/provenance-participant-type";
    private static final DateTimeFormatter FMT_UTC =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssxxx"); // -> ...+00:00

    private final ApiSatuSehat api = new ApiSatuSehat();
    private final ObjectMapper mapper = new ObjectMapper();
    private String link = "";
    private String idOrg = "";
    private String namaOrg = "";

    public SatuSehatProvenance() {
        try {
            link = koneksiDB.URLFHIRSATUSEHAT();
            idOrg = koneksiDB.IDSATUSEHAT();
        } catch (Exception e) {
            System.out.println("Notifikasi Provenance : " + e);
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
            System.out.println("Notifikasi Provenance namaOrg : " + e);
        }
    }

    /**
     * Satu pihak yang menandatangani sebuah dokumen.
     *
     * Tiga model TTE (semuanya diuji ke staging 1 Agustus 2026 dan diterima server):
     * <ul>
     *   <li><b>single</b>  — satu Penanda berperan {@code author}.</li>
     *   <li><b>paralel</b> — BANYAK Penanda dalam SATU Provenance (mis. author=pasien,
     *       attester=saksi). Tiap penanda menandatangani lewat Task-nya sendiri yang
     *       menunjuk Provenance yang sama.</li>
     *   <li><b>serial</b>  — satu Penanda per Provenance, dan Provenance berikutnya
     *       menunjuk pendahulunya lewat {@code entity[]} (lihat parameter
     *       {@code idProvSebelumnya}).</li>
     * </ul>
     * {@code peran} memakai CodeSystem provenance-participant-type: author, attester,
     * verifier, performer, enterer. Organization RS TIDAK ditulis sebagai Penanda —
     * ia selalu ditambahkan builder sebagai agent {@code custodian}.
     */
    public static class Penanda {
        public final String ihs;
        public final String nama;
        public final String peran;

        public Penanda(String ihs, String nama, String peran) {
            this.ihs = (ihs == null) ? "" : ihs.trim();
            this.nama = (nama == null) ? "" : nama.trim();
            this.peran = (peran == null || peran.trim().equals("")) ? "author" : peran.trim();
        }

        /** Penanda tunggal berperan author — bentuk yang dipakai model single. */
        public static Penanda author(String ihs, String nama) {
            return new Penanda(ihs, nama, "author");
        }

        /** Display peran untuk agent.type.coding.display ("author" -> "Author"). */
        public String peranDisplay() {
            return peran.substring(0, 1).toUpperCase() + peran.substring(1);
        }
    }

    /**
     * Buat placeholder Provenance untuk satu dokumen dengan SATU ATAU BANYAK penanda, lalu POST.
     *
     * @param penanda          daftar penanda (minimal satu); banyak penanda = model paralel.
     * @param idProvSebelumnya model serial: id Provenance sebelumnya yang ikut ditunjuk
     *                         {@code entity[]}; ""/null untuk tanda tangan pertama.
     */
    public String buatPlaceholder(String targetRef, String targetDisplay,
                                  java.util.List<Penanda> penanda,
                                  String mulaiUtc, String selesaiUtc,
                                  String idProvSebelumnya) throws Exception {
        if (targetRef == null || targetRef.trim().equals("")) {
            throw new IllegalArgumentException("targetRef (resource yang di-TTE) wajib diisi");
        }
        if (penanda == null || penanda.isEmpty()) {
            throw new IllegalArgumentException("Minimal satu penanda tangan diperlukan");
        }
        for (Penanda p : penanda) {
            if (p == null || p.ihs.equals("")) {
                throw new IllegalArgumentException("Ada penanda tangan yang IHS Practitioner-nya belum ter-mapping");
            }
        }
        ObjectNode prov = bangunProvenance(mapper, targetRef, targetDisplay, penanda, idOrg, namaOrg,
                nowUtc(), mulaiUtc, selesaiUtc, idProvSebelumnya);

        JsonNode resp = mapper.readTree(post(link + "/Provenance", prov));
        String id = resp.path("id").asText();
        if (id == null) {
            id = "";
        }
        System.out.println("Result Provenance ID : " + id);
        return id;
    }

    /**
     * Buat placeholder Provenance (signature = []) untuk satu dokumen, lalu POST ke SATUSEHAT.
     *
     * @param targetRef      referensi resource yang di-TTE, mis. "Composition/&lt;id&gt;".
     * @param targetDisplay  label dokumen, mis. "Resume Medis Rawat Inap".
     * @param idPractitioner IHS Practitioner id DPJP (tanpa prefix "Practitioner/").
     * @param namaDpjp       nama dokter untuk display.
     * @param mulaiUtc       awal periode pelayanan (ISO offset) atau null -> pakai sekarang.
     * @param selesaiUtc     akhir periode pelayanan (ISO offset) atau null -> pakai sekarang.
     * @return Provenance ID hasil dari server (kosong "" bila gagal parse).
     */
    public String buatPlaceholder(String targetRef, String targetDisplay,
                                  String idPractitioner, String namaDpjp,
                                  String mulaiUtc, String selesaiUtc) throws Exception {
        if (targetRef == null || targetRef.trim().equals("")) {
            throw new IllegalArgumentException("targetRef (resource yang di-TTE) wajib diisi");
        }
        if (idPractitioner == null || idPractitioner.trim().equals("")) {
            throw new IllegalArgumentException("IHS Practitioner (DPJP) belum ter-mapping");
        }
        ObjectNode prov = bangunProvenance(mapper, targetRef, targetDisplay, idPractitioner,
                namaDpjp, idOrg, namaOrg, nowUtc(), mulaiUtc, selesaiUtc);

        JsonNode resp = mapper.readTree(post(link + "/Provenance", prov));
        String id = resp.path("id").asText();
        if (id == null) {
            id = "";
        }
        System.out.println("Result Provenance ID : " + id);
        return id;
    }

    /**
     * Bangun resource FHIR Provenance placeholder (signature = []) — murni, tanpa I/O.
     * Deterministik terhadap recordedUtc sehingga bisa diuji unit.
     *
     * @param recordedUtc timestamp pembuatan (ISO offset). Juga jadi default periode.
     * @param mulaiUtc    awal occurredPeriod; null/"" -> recordedUtc.
     * @param selesaiUtc  akhir occurredPeriod; null/"" -> recordedUtc.
     */
    public static ObjectNode bangunProvenance(ObjectMapper mapper, String targetRef, String targetDisplay,
                                              String idPractitioner, String namaDpjp, String idOrg,
                                              String recordedUtc, String mulaiUtc, String selesaiUtc) {
        return bangunProvenance(mapper, targetRef, targetDisplay, idPractitioner, namaDpjp, idOrg, "",
                recordedUtc, mulaiUtc, selesaiUtc);
    }

    /**
     * Varian yang juga menuliskan nama Organization pada agent {@code custodian}.
     *
     * @param namaOrg nama RS untuk display agent custodian; ""/null -> display di-skip.
     */
    public static ObjectNode bangunProvenance(ObjectMapper mapper, String targetRef, String targetDisplay,
                                              String idPractitioner, String namaDpjp, String idOrg,
                                              String namaOrg,
                                              String recordedUtc, String mulaiUtc, String selesaiUtc) {
        java.util.List<Penanda> satu = new java.util.ArrayList<>();
        satu.add(Penanda.author(idPractitioner, namaDpjp));
        return bangunProvenance(mapper, targetRef, targetDisplay, satu, idOrg, namaOrg,
                recordedUtc, mulaiUtc, selesaiUtc, "");
    }

    /**
     * Bentuk lengkap: SATU ATAU BANYAK penanda, plus rantai ke Provenance sebelumnya.
     *
     * <p>Model <b>paralel</b>: isi {@code penanda} dengan semua pihak (mis. author=pasien,
     * attester=saksi 1, attester=saksi 2) — seluruhnya masuk ke SATU Provenance, persis seperti
     * template "Repository Provenance (Single / Multiple Agent)" di koleksi resmi.
     *
     * <p>Model <b>serial</b>: kirim satu Provenance per penanda, dan untuk penanda ke-2 dst isi
     * {@code idProvSebelumnya} dengan id Provenance pendahulunya. Elemen {@code entity[]} kedua
     * itulah rantainya (template "01. Buat TTE/Provenance/f. Chaining Signature"). Karena butuh id
     * pendahulunya, Provenance serial TIDAK bisa dikirim serentak dalam satu Bundle.
     *
     * @param penanda          daftar penanda; agent {@code custodian} (Organization) ditambahkan sendiri.
     * @param idProvSebelumnya id Provenance pendahulu untuk model serial; ""/null bila tidak ada.
     */
    public static ObjectNode bangunProvenance(ObjectMapper mapper, String targetRef, String targetDisplay,
                                              java.util.List<Penanda> penanda, String idOrg, String namaOrg,
                                              String recordedUtc, String mulaiUtc, String selesaiUtc,
                                              String idProvSebelumnya) {
        if (penanda == null || penanda.isEmpty()) {
            throw new IllegalArgumentException("Minimal satu penanda tangan diperlukan");
        }
        String mulai = (mulaiUtc == null || mulaiUtc.equals("")) ? recordedUtc : mulaiUtc;
        String selesai = (selesaiUtc == null || selesaiUtc.equals("")) ? recordedUtc : selesaiUtc;

        ObjectNode prov = mapper.createObjectNode();
        prov.put("resourceType", "Provenance");

        // target : resource yang ditandatangani.
        ObjectNode target = prov.putArray("target").addObject();
        target.put("reference", targetRef);
        if (targetDisplay != null && !targetDisplay.equals("")) {
            target.put("display", targetDisplay);
        }

        // entity : dokumen sumber yang menjadi asal permintaan tanda tangan. Sejak koleksi
        // resmi "00. TTE versi 22072026" elemen ini ada di SEMUA template placeholder
        // (Composition/Observation/DiagnosticReport/DocumentReference/ServiceRequest) maupun
        // di Bundle, dan tetap dibawa SSM saat PUT signature. Referensinya sama dgn target.
        ArrayNode entities = prov.putArray("entity");
        ObjectNode entity = entities.addObject();
        entity.putObject("what").put("reference", targetRef);
        entity.put("role", "source");

        // MODEL SERIAL: entity KEDUA menunjuk Provenance pendahulunya — inilah rantai tanda tangan
        // pada template "f. Chaining Signature" (TTE perawat lalu DPJP). Tanpa elemen ini, dua
        // tanda tangan pada dokumen yang sama tidak punya kaitan apa pun di server.
        if (idProvSebelumnya != null && !idProvSebelumnya.trim().equals("")) {
            ObjectNode rantai = entities.addObject();
            rantai.putObject("what").put("reference", "Provenance/" + idProvSebelumnya.trim());
            rantai.put("role", "source");
        }

        // recorded : tanggal pembuatan permintaan TTE.
        prov.put("recorded", recordedUtc);

        // occurredPeriod : rentang waktu pelayanan/dokumen.
        ObjectNode period = prov.putObject("occurredPeriod");
        period.put("start", mulai);
        period.put("end", selesai);

        // activity : CREATE — ini PERMINTAAN tanda tangan, bukan dokumen yang sudah sah. Nilai LA
        // (Legally Authenticated) baru muncul pada resource hasil yang ditulis SSM setelah menandatangani.
        ObjectNode actCoding = prov.putObject("activity").putArray("coding").addObject();
        actCoding.put("system", SYS_DATA_OPERATION);
        actCoding.put("code", "CREATE");
        actCoding.put("display", "Create Signature Request");

        // agent : SATU elemen per penanda + SATU custodian (Organization RS), mengikuti seluruh
        // template koleksi resmi "00. TTE versi 24072026".
        //
        // MODEL PARALEL memakai banyak elemen di sini (mis. author=pasien, attester=saksi 1,
        // attester=saksi 2) — semuanya menandatangani Provenance yang SAMA. Sebelum 1 Agustus 2026
        // kode hanya bisa mengirim satu agent author dengan Organization di onBehalfOf; itu
        // menaruh Organization di tempat yang tidak dipakai koleksi mana pun, dan menutup
        // kemungkinan multi-penanda sama sekali.
        ArrayNode agents = prov.putArray("agent");

        for (Penanda p : penanda) {
            if (p == null || p.ihs.equals("")) {
                continue;   // penanda tanpa IHS dilewati; validasi keras ada di buatPlaceholder
            }
            ObjectNode agentPenanda = agents.addObject();
            ObjectNode typeCoding = agentPenanda.putObject("type").putArray("coding").addObject();
            typeCoding.put("system", SYS_PARTICIPANT_TYPE);
            typeCoding.put("code", p.peran);
            typeCoding.put("display", p.peranDisplay());
            ObjectNode who = agentPenanda.putObject("who");
            who.put("reference", "Practitioner/" + p.ihs);
            if (!p.nama.equals("")) {
                who.put("display", p.nama);
            }
        }

        ObjectNode agentCustodian = agents.addObject();
        ObjectNode custodianType = agentCustodian.putObject("type").putArray("coding").addObject();
        custodianType.put("system", SYS_PARTICIPANT_TYPE);
        custodianType.put("code", "custodian");
        custodianType.put("display", "Custodian");
        ObjectNode custodianWho = agentCustodian.putObject("who");
        custodianWho.put("reference", "Organization/" + idOrg);
        if (namaOrg != null && !namaOrg.equals("")) {
            custodianWho.put("display", namaOrg);
        }

        // signature sengaja TIDAK dikirim: tidak ada di satu pun template placeholder koleksi resmi,
        // dan SSM yang mengisinya saat menandatangani.
        return prov;
    }

    /**
     * GET Provenance dari server (dipakai saat verifikasi untuk membaca signature).
     * @return JsonNode resource Provenance, atau null bila gagal.
     */
    public JsonNode bacaProvenance(String idProvenance) {
        if (idProvenance == null || idProvenance.trim().equals("")) {
            return null;
        }
        try {
            HttpHeaders h = new HttpHeaders();
            h.add("Authorization", "Bearer " + api.TokenSatuSehat());
            HttpEntity req = new HttpEntity(h);
            java.net.URI uri = java.net.URI.create(link + "/Provenance/" + idProvenance);
            String hasil = api.getRest().exchange(uri, HttpMethod.GET, req, String.class).getBody();
            return mapper.readTree(hasil);
        } catch (Exception e) {
            System.out.println("Notifikasi Provenance bacaProvenance : " + e);
            return null;
        }
    }

    /**
     * GET versionId TERKINI resource target (mis. "Composition/{id}") dari server -&gt; {@code meta.versionId}.
     * Dipakai membandingkan versi yang DITANDATANGANI (dari Provenance.target "…/_history/{v}") vs versi
     * dokumen sekarang -&gt; deteksi "dokumen berubah setelah TTE". "" bila gagal / ref kosong.
     */
    public String bacaVersiTerkini(String ref) {
        if (ref == null || ref.trim().equals("")) {
            return "";
        }
        try {
            HttpHeaders h = new HttpHeaders();
            h.add("Authorization", "Bearer " + api.TokenSatuSehat());
            HttpEntity req = new HttpEntity(h);
            java.net.URI uri = java.net.URI.create(link + "/" + ref.trim());
            String hasil = api.getRest().exchange(uri, HttpMethod.GET, req, String.class).getBody();
            JsonNode n = mapper.readTree(hasil);
            String v = n.path("meta").path("versionId").asText();
            return v == null ? "" : v;
        } catch (Exception e) {
            System.out.println("Notifikasi Provenance bacaVersiTerkini : " + e);
            return "";
        }
    }

    /**
     * Cari id Provenance PALING BARU yang BENAR-BENAR BERTANDA-TANGAN atas resource target
     * (mis. "Composition/{id}") di SATUSEHAT. Dokumen yang diedit lalu di-TTE ulang punya BEBERAPA
     * Provenance; verifikasi harus menampilkan yang terbaru & signed. HANYA entry yang punya
     * signature[0].data yang dipertimbangkan (placeholder CREATE kosong DIABAIKAN — tanpa ini
     * placeholder yang baru dibuat mengalahkan Provenance signed yang lebih lama), diurut berdasar
     * signature[0].when terbaru. "" bila tak ada yang signed / gagal / search tak didukung.
     *
     * CATATAN: setelah TTE, target Provenance bisa terkunci ke referensi ber-versi
     * ("Composition/{id}/_history/{v}") sehingga TAK terjaring search target tanpa versi ini; jadi
     * metode ini hanya FALLBACK. Sumber utama verifikasi = id Provenance dari Task.output[code=signed].
     */
    public String cariProvenanceTerbaru(String targetRef) {
        if (targetRef == null || targetRef.trim().equals("")) {
            return "";
        }
        try {
            HttpHeaders h = new HttpHeaders();
            h.add("Authorization", "Bearer " + api.TokenSatuSehat());
            HttpEntity req = new HttpEntity(h);
            java.net.URI uri = java.net.URI.create(link + "/Provenance?target=" + targetRef.trim() + "&_count=100");
            String hasil = api.getRest().exchange(uri, HttpMethod.GET, req, String.class).getBody();
            JsonNode root = mapper.readTree(hasil);
            String bestId = "";
            String bestWhen = "";
            for (JsonNode e : root.path("entry")) {
                JsonNode res = e.path("resource");
                String id = res.path("id").asText();
                if (id == null || id.equals("")) {
                    continue;
                }
                String when = res.path("signature").path(0).path("when").asText();
                if (when == null || when.equals("")) {
                    when = res.path("recorded").asText();
                }
                if (when == null) {
                    when = "";
                }
                // ISO timestamp -> perbandingan leksikografis = urutan waktu (format seragam).
                if (bestId.equals("") || when.compareTo(bestWhen) > 0) {
                    bestId = id;
                    bestWhen = when;
                }
            }
            return bestId;
        } catch (Exception e) {
            System.out.println("Notifikasi Provenance cariProvenanceTerbaru : " + e);
            return "";
        }
    }

    /** true bila Provenance sudah punya signature (sudah ditandatangani SSM). */
    public boolean sudahDitandatangani(JsonNode provenance) {
        if (provenance == null) {
            return false;
        }
        JsonNode sig = provenance.path("signature");
        if (!sig.isArray() || sig.size() == 0) {
            return false;
        }
        String data = sig.get(0).path("data").asText();
        return data != null && !data.equals("");
    }

    /** POST resource FHIR; kembalikan response body. Isolasi bentuk endpoint (individual vs bundle). */
    private String post(String url, JsonNode body) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + api.TokenSatuSehat());
        String payload = mapper.writeValueAsString(body);
        System.out.println("Request JSON Provenance : " + payload);
        HttpEntity requestEntity = new HttpEntity(payload, headers);
        try {
            return api.getRest().exchange(url, HttpMethod.POST, requestEntity, String.class).getBody();
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            System.out.println("Error Provenance Status Code: " + ex.getStatusCode());
            String errBody = ex.getResponseBodyAsString();
            try {
                JsonNode err = mapper.readTree(errBody);
                System.out.println("Error Provenance OperationOutcome:\n"
                        + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(err));
            } catch (Exception e2) {
                System.out.println("Error Provenance Body: " + errBody);
            }
            throw ex;
        }
    }

    /** Timestamp UTC ISO-8601 dengan offset "+00:00" (sesuai contoh Panduan Teknis). */
    private String nowUtc() {
        return OffsetDateTime.now(ZoneOffset.UTC).format(FMT_UTC);
    }
}

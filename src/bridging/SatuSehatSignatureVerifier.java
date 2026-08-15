/*
  by Ananda Widitomo,S.Kom.
  IT - SIMRS Hj. Fatimah Sulhan
 */
package bridging;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * TTE (Tanda Tangan Elektronik) — Flow 5 (Verifikasi).
 *
 * Parser MURNI (tanpa I/O jaringan) yang membaca resource Provenance yang SUDAH
 * ditandatangani SATUSEHAT Mobile (via BSrE) dan mengekstrak detail verifikasi untuk
 * ditampilkan ke operator: status tanda tangan, penanda-tangan, waktu, tipe (OID),
 * JWT/JOSE (di-decode header+payload), perangkat, dan lokasi (lat/long untuk peta).
 *
 * Struktur Provenance signed (Panduan Teknis TTE v1.2, Flow 5) — dibaca defensif:
 *   - signature[] : 1..2 elemen.
 *       * Author  : type.code 1.2.840.10065.1.12.1.1 ("Author's Signature"),
 *                   sigFormat "application/jose", data = base64(JWS compact).
 *       * Timestamp: type.code 1.2.840.10065.1.12.1.18, onBehalfOf Organization/bsre,
 *                   sigFormat "application/timestamp-reply".
 *   - contained[] : Device (perangkat penanda-tangan) + Location (position lat/long).
 *   - location    : Reference ke Location contained (lokasi penandatanganan).
 *
 * CATATAN: kelas ini TIDAK memverifikasi tanda tangan secara kriptografis (kunci publik
 * BSrE ada di sisi SATUSEHAT). Yang dilaporkan adalah KEHADIRAN + STRUKTUR tanda tangan;
 * validitas kriptografis dijamin oleh BSrE saat penandatanganan.
 */
public class SatuSehatSignatureVerifier {

    private static final String OID_AUTHOR = "1.2.840.10065.1.12.1.1";
    private static final String OID_TIMESTAMP = "1.2.840.10065.1.12.1.18";

    private final ObjectMapper mapper = new ObjectMapper();

    /** Hasil ekstraksi Provenance signed untuk panel verifikasi. */
    public static class Hasil {
        public boolean ditandatangani = false;   // ada signature.data (Author) terisi
        public int jumlahSignature = 0;
        public String penandatangan = "";        // Signed By  (signature.who.display / agent)
        public String penandatanganRef = "";     // Practitioner/xxx
        public String tglTandatangan = "";        // Signed Date (signature Author.when, lokal)
        public String tipeTandatangan = "";       // Signature Type (display + OID)
        public String sigFormat = "";             // application/jose
        public String jwt = "";                   // raw data (base64 JOSE) Author
        public String joseHeader = "";            // JOSE header (decoded, pretty JSON)
        public String josePayload = "";           // JOSE payload (decoded, pretty JSON)
        public String perangkat = "";             // Device (contained)
        public String lokasiNama = "";            // Location name/address (contained)
        public String lat = "";                   // Location.position.latitude
        public String lon = "";                   // Location.position.longitude
        public String penyediaStempel = "";       // Timestamp onBehalfOf (BSrE)
        public String tglStempel = "";            // Timestamp signature.when (lokal)
        public String aktivitas = "";             // Provenance.activity ("LA"/Legally Authenticated setelah TTE; SSM ubah SIGN -> LA)
        public String versiDitandatangani = "";   // versionId DOKUMEN dari target "…/_history/{versionId}" (SSM mengunci versi saat TTE)
        public String versiWaktu = "";            // versionId di-decode -> waktu lokal (SATUSEHAT = base64 nanodetik epoch)
        public String targetRefBersih = "";       // target tanpa "/_history/…" (mis. "Composition/{id}") utk cek versi terkini
        public final java.util.List<String> tandaTangan = new java.util.ArrayList<>(); // ringkas semua signature: "tipe — who"

        public boolean adaKoordinat() {
            return !lat.equals("") && !lon.equals("");
        }
    }

    /** Ekstrak seluruh detail verifikasi dari satu resource Provenance. Tidak pernah null. */
    public Hasil parse(JsonNode prov) {
        Hasil h = new Hasil();
        if (prov == null) {
            return h;
        }
        // activity : placeholder "LA" -> resource hasil tetap "LA" (Legally Authenticated). SSM memerbarui
        // activity dari "SIGN" -> "LA" saat menandatangani (dikonfirmasi Kemenkes TTDK 23 Juli 2026).
        JsonNode act = prov.path("activity").path("coding");
        if (act.isArray() && act.size() > 0) {
            String ad = teks(act.get(0).path("display"));
            String ac = teks(act.get(0).path("code"));
            h.aktivitas = ad.equals("") ? ac : (ac.equals("") ? ad : ad + " (" + ac + ")");
        }
        JsonNode sigs = prov.path("signature");
        if (sigs.isArray()) {
            h.jumlahSignature = sigs.size();
            for (JsonNode s : sigs) {
                String ringkas = ringkasTandaTangan(s);
                if (!ringkas.equals("")) {
                    h.tandaTangan.add(ringkas);
                }
                String kode = kodeTipe(s);
                if (OID_TIMESTAMP.equals(kode)) {
                    isiStempel(h, s);
                } else {
                    // Default: perlakukan sebagai tanda tangan Author (juga bila OID tak dikenal).
                    // "03. Variasi" mengenumerasi OID lain (Consent .1.7, Consent Witness .1.11, Verification .1.5, dst).
                    isiAuthor(h, s);
                }
            }
            // Bila tak ada elemen ber-OID Author eksplisit, pakai elemen pertama sebagai Author.
            if (h.penandatangan.equals("") && h.tglTandatangan.equals("") && sigs.size() > 0) {
                isiAuthor(h, sigs.get(0));
            }
        }
        isiPerangkatDanLokasi(h, prov);
        isiVersi(h, prov);
        return h;
    }

    /**
     * Ekstrak versionId DOKUMEN dari Provenance.target[0].reference. Setelah TTE, SSM mengunci referensi
     * ke versi: "Composition/{id}/_history/{versionId}". Simpan versionId + target tanpa versi (utk cek
     * versi terkini) + decode versionId (SATUSEHAT = base64 nanodetik epoch) ke waktu lokal.
     */
    private void isiVersi(Hasil h, JsonNode prov) {
        JsonNode tgt = prov.path("target");
        if (!tgt.isArray() || tgt.size() == 0) {
            return;
        }
        String ref = teks(tgt.get(0).path("reference"));
        if (ref.equals("")) {
            return;
        }
        int idx = ref.indexOf("/_history/");
        if (idx >= 0) {
            h.targetRefBersih = ref.substring(0, idx);                          // "Composition/{id}"
            h.versiDitandatangani = ref.substring(idx + "/_history/".length());
            h.versiWaktu = decodeVersionWaktu(h.versiDitandatangani);
        } else {
            h.targetRefBersih = ref;                                            // placeholder: belum ber-versi
        }
    }

    /** Decode versionId SATUSEHAT (base64 dari nanodetik epoch) -&gt; "dd-MM-yyyy HH:mm:ss" lokal; "" bila gagal. */
    private String decodeVersionWaktu(String versionId) {
        if (versionId == null || versionId.trim().equals("")) {
            return "";
        }
        try {
            String ns = new String(Base64.getDecoder().decode(rapikanB64(versionId.trim())),
                    StandardCharsets.UTF_8).trim();
            if (!ns.matches("\\d{13,}")) {   // nanodetik epoch masa kini = 19 digit; longgar >=13
                return "";
            }
            long nano = Long.parseLong(ns);
            java.time.Instant inst = java.time.Instant.ofEpochSecond(
                    nano / 1_000_000_000L, nano % 1_000_000_000L);
            return inst.atZone(java.time.ZoneId.systemDefault())
                    .format(java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
        } catch (Exception e) {
            return "";
        }
    }

    private String kodeTipe(JsonNode sig) {
        JsonNode t = sig.path("type");
        if (t.isArray() && t.size() > 0) {
            return teks(t.get(0).path("code"));
        }
        return "";
    }

    /** Ringkas satu signature -> "&lt;tipe display/code&gt; — &lt;who.display&gt;" untuk daftar tanda tangan. */
    private String ringkasTandaTangan(JsonNode sig) {
        String tipe = "";
        JsonNode t = sig.path("type");
        if (t.isArray() && t.size() > 0) {
            String disp = teks(t.get(0).path("display"));
            String code = teks(t.get(0).path("code"));
            tipe = disp.equals("") ? code : disp;
        }
        String who = pilih(teks(sig.path("who").path("display")), teks(sig.path("who").path("reference")));
        if (tipe.equals("") && who.equals("")) {
            return "";
        }
        if (tipe.equals("")) {
            return who;
        }
        return who.equals("") ? tipe : tipe + " — " + who;
    }

    private void isiAuthor(Hasil h, JsonNode sig) {
        String data = teks(sig.path("data"));
        if (!data.equals("")) {
            h.ditandatangani = true;
            h.jwt = data;
            String[] hp = decodeJose(data);
            h.joseHeader = hp[0];
            h.josePayload = hp[1];
        }
        h.penandatangan = pilih(h.penandatangan,
                teks(sig.path("who").path("display")));
        h.penandatanganRef = pilih(h.penandatanganRef,
                teks(sig.path("who").path("reference")));
        h.tglTandatangan = pilih(h.tglTandatangan, tglLokal(teks(sig.path("when"))));
        h.sigFormat = pilih(h.sigFormat, teks(sig.path("sigFormat")));
        JsonNode t = sig.path("type");
        if (t.isArray() && t.size() > 0) {
            String disp = teks(t.get(0).path("display"));
            String code = teks(t.get(0).path("code"));
            String gab = disp.equals("") ? code : (code.equals("") ? disp : disp + "  (" + code + ")");
            h.tipeTandatangan = pilih(h.tipeTandatangan, gab);
        }
    }

    private void isiStempel(Hasil h, JsonNode sig) {
        h.tglStempel = pilih(h.tglStempel, tglLokal(teks(sig.path("when"))));
        h.penyediaStempel = pilih(h.penyediaStempel,
                pilih(teks(sig.path("onBehalfOf").path("display")),
                      teks(sig.path("onBehalfOf").path("reference"))));
    }

    private void isiPerangkatDanLokasi(Hasil h, JsonNode prov) {
        JsonNode contained = prov.path("contained");
        if (!contained.isArray()) {
            return;
        }
        for (JsonNode c : contained) {
            String rt = teks(c.path("resourceType"));
            if ("Device".equalsIgnoreCase(rt)) {
                String nama = "";
                JsonNode dn = c.path("deviceName");
                if (dn.isArray() && dn.size() > 0) {
                    nama = teks(dn.get(0).path("name"));
                }
                if (nama.equals("")) {
                    nama = pilih(teks(c.path("manufacturer")), teks(c.path("modelNumber")));
                }
                h.perangkat = pilih(h.perangkat, nama);
            } else if ("Location".equalsIgnoreCase(rt)) {
                h.lat = pilih(h.lat, teks(c.path("position").path("latitude")));
                h.lon = pilih(h.lon, teks(c.path("position").path("longitude")));
                String nm = teks(c.path("name"));
                if (nm.equals("")) {
                    nm = teks(c.path("address").path("text"));
                }
                h.lokasiNama = pilih(h.lokasiNama, nm);
            }
        }
    }

    /**
     * Decode JOSE/JWS: kembalikan {headerJson, payloadJson} yang sudah di-pretty-print.
     * Menerima dua bentuk `data`: (a) JWS compact langsung "h.p.s", atau
     * (b) base64(JWS compact). Elemen yang gagal di-decode dikembalikan apa adanya.
     */
    public String[] decodeJose(String data) {
        String h = "";
        String p = "";
        if (data == null || data.trim().equals("")) {
            return new String[]{h, p};
        }
        String jws = data.trim();
        if (jws.indexOf('.') < 0) {
            // Bukan JWS compact langsung -> mungkin base64 dari JWS.
            String dec = decodeB64(jws);
            if (dec != null && dec.indexOf('.') >= 0) {
                jws = dec;
            }
        }
        String[] parts = jws.split("\\.");
        if (parts.length >= 2) {
            h = prettyJsonAtau(decodeB64Url(parts[0]));
            p = prettyJsonAtau(decodeB64Url(parts[1]));
        }
        return new String[]{h, p};
    }

    private String decodeB64(String s) {
        try {
            return new String(Base64.getDecoder().decode(rapikanB64(s)), StandardCharsets.UTF_8);
        } catch (Exception e) {
            return null;
        }
    }

    private String decodeB64Url(String s) {
        try {
            return new String(Base64.getUrlDecoder().decode(pad(s)), StandardCharsets.UTF_8);
        } catch (Exception e) {
            try {
                return new String(Base64.getDecoder().decode(rapikanB64(s)), StandardCharsets.UTF_8);
            } catch (Exception e2) {
                return null;
            }
        }
    }

    private String rapikanB64(String s) {
        String t = s.replaceAll("\\s", "");
        int mod = t.length() % 4;
        if (mod != 0) {
            t = t + "====".substring(mod);
        }
        return t;
    }

    private String pad(String s) {
        String t = s.replaceAll("\\s", "");
        int mod = t.length() % 4;
        if (mod != 0) {
            t = t + "====".substring(mod);
        }
        return t;
    }

    private String prettyJsonAtau(String s) {
        if (s == null || s.trim().equals("")) {
            return s == null ? "" : s;
        }
        try {
            JsonNode n = mapper.readTree(s);
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(n);
        } catch (Exception e) {
            return s; // bukan JSON valid -> tampilkan mentah
        }
    }

    /** ISO instant/offset -> "dd-MM-yyyy HH:mm:ss" waktu lokal. Gagal -> kembalikan apa adanya. */
    private String tglLokal(String iso) {
        if (iso == null || iso.trim().equals("")) {
            return "";
        }
        try {
            java.time.OffsetDateTime odt = java.time.OffsetDateTime.parse(iso.trim());
            return odt.atZoneSameInstant(java.time.ZoneId.systemDefault())
                    .format(java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
        } catch (Exception e) {
            return iso.trim();
        }
    }

    private String teks(JsonNode n) {
        if (n == null || n.isMissingNode() || n.isNull()) {
            return "";
        }
        String s = n.asText();
        return s == null ? "" : s.trim();
    }

    private String pilih(String lama, String baru) {
        if (lama != null && !lama.equals("")) {
            return lama;
        }
        return baru == null ? "" : baru;
    }
}

/*
  by Ananda Widitomo,S.Kom.
 */
package bridging;

import fungsi.koneksiDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Aturan model TTE per jenis dokumen, dibaca dari tabel {@code satu_sehat_tte_model}
 * (lihat satu_sehat_tte_multisign.sql).
 *
 * Tiga model — semuanya sudah diuji ke staging 1 Agustus 2026 dan diterima server:
 * <ul>
 *   <li>{@code SINGLE}  — satu penanda (author). Satu Provenance + satu Task.</li>
 *   <li>{@code PARALEL} — banyak penanda dalam SATU Provenance; tiap penanda dapat Task
 *       sendiri yang menunjuk Provenance yang sama. Urutan bebas.</li>
 *   <li>{@code SERIAL}  — satu Provenance per penanda, berurutan; Provenance ke-n menunjuk
 *       Provenance ke-(n-1) lewat {@code entity[]}. Giliran WAJIB ditaati karena penanda
 *       berikutnya butuh id Provenance pendahulunya.</li>
 * </ul>
 *
 * Dokumen yang belum punya baris di tabel diperlakukan {@code SINGLE} dengan satu slot
 * author bersumber {@code dpjp} — persis perilaku sebelum multi-signature ada, sehingga
 * instalasi yang belum menjalankan SQL-nya tetap berjalan seperti biasa.
 */
public class SatuSehatTteModel {

    public enum Model { SINGLE, PARALEL, SERIAL }

    /** Satu giliran/peran penanda tangan pada sebuah jenis dokumen. */
    public static class Slot {
        public final int urutan;
        public final String peran;
        /** Kode sumber penanda yang dikenali perakit dokumen, mis. "dpjp", "nip_analis". */
        public final String sumber;
        public final boolean wajib;
        public final String keterangan;

        public Slot(int urutan, String peran, String sumber, boolean wajib, String keterangan) {
            this.urutan = urutan < 1 ? 1 : urutan;
            this.peran = (peran == null || peran.trim().equals("")) ? "author" : peran.trim();
            this.sumber = (sumber == null) ? "" : sumber.trim();
            this.wajib = wajib;
            this.keterangan = (keterangan == null) ? "" : keterangan;
        }
    }

    /** Aturan lengkap satu jenis dokumen. */
    public static class Aturan {
        public final String jenis;
        public final Model model;
        public final List<Slot> slot;

        public Aturan(String jenis, Model model, List<Slot> slot) {
            this.jenis = jenis;
            this.model = model;
            this.slot = slot;
        }

        public boolean serial() {
            return model == Model.SERIAL;
        }

        public boolean paralel() {
            return model == Model.PARALEL;
        }

        /** true bila dokumen ini butuh lebih dari satu tanda tangan. */
        public boolean multi() {
            return slot.size() > 1;
        }
    }

    /**
     * Jenis dokumen yang tidak punya baris di tabel TIDAK diberi slot sama sekali — penandanya
     * ditentukan {@code SatuSehatSignatureAssembler.tetapkanSigner()}, yang sudah memetakan tiap
     * jenis ke pembuat dokumennya (Laporan Operasi -> operator bedah, USG -> dokter USG, dst).
     *
     * Sebelumnya di sini dikembalikan satu slot bersumber "dpjp". Akibatnya perluasPenanda selalu
     * berhasil meresolusikan DPJP lalu MENIMPA penanda per-dokumen tadi, sehingga seluruh berkas
     * tampil atas nama DPJP — padahal Laporan Anestesi harusnya dokter anestesi, USG harusnya
     * dokter USG, dan seterusnya.
     */
    private static final List<Slot> TANPA_SLOT = java.util.Collections.emptyList();

    private static Map<String, Aturan> cache = null;

    /** Aturan untuk satu jenis dokumen; tak pernah null. */
    public static synchronized Aturan untuk(String jenis) {
        if (cache == null) {
            cache = muat();
        }
        String kunci = (jenis == null) ? "" : jenis.trim();
        Aturan a = cache.get(kunci.toLowerCase());
        if (a != null) {
            return a;
        }
        return new Aturan(kunci, Model.SINGLE, TANPA_SLOT);
    }

    /** Buang cache — dipanggil bila tabel model diubah saat aplikasi berjalan. */
    public static synchronized void segarkan() {
        cache = null;
    }

    private static Map<String, Aturan> muat() {
        Map<String, List<Slot>> slotPerJenis = new LinkedHashMap<>();
        Map<String, Model> modelPerJenis = new LinkedHashMap<>();
        Connection koneksi = koneksiDB.condb();
        try (PreparedStatement p = koneksi.prepareStatement(
                "select jenis_dokumen, urutan, model, peran, sumber, wajib, keterangan "
                + "from satu_sehat_tte_model order by jenis_dokumen, urutan")) {
            try (ResultSet r = p.executeQuery()) {
                while (r.next()) {
                    String jenis = nz(r.getString("jenis_dokumen")).trim();
                    String kunci = jenis.toLowerCase();
                    if (!slotPerJenis.containsKey(kunci)) {
                        slotPerJenis.put(kunci, new ArrayList<Slot>());
                    }
                    slotPerJenis.get(kunci).add(new Slot(r.getInt("urutan"), nz(r.getString("peran")),
                            nz(r.getString("sumber")), r.getInt("wajib") == 1, nz(r.getString("keterangan"))));
                    modelPerJenis.put(kunci, model(nz(r.getString("model"))));
                }
            }
        } catch (Exception e) {
            // Tabel belum ada (satu_sehat_tte_multisign.sql belum dijalankan) -> semua dokumen
            // jatuh ke aturan bawaan SINGLE. Bukan kegagalan: perilaku lama tetap utuh.
            System.out.println("Notifikasi TteModel muat : " + e);
        }

        Map<String, Aturan> hasil = new LinkedHashMap<>();
        for (Map.Entry<String, List<Slot>> e : slotPerJenis.entrySet()) {
            Model m = modelPerJenis.get(e.getKey());
            hasil.put(e.getKey(), new Aturan(e.getKey(), m == null ? Model.SINGLE : m, e.getValue()));
        }
        System.out.println("Notifikasi TteModel : " + hasil.size() + " jenis dokumen dipetakan.");
        return hasil;
    }

    private static Model model(String s) {
        if (s.equalsIgnoreCase("paralel")) {
            return Model.PARALEL;
        }
        if (s.equalsIgnoreCase("serial")) {
            return Model.SERIAL;
        }
        return Model.SINGLE;
    }

    private static String nz(String s) {
        return s == null ? "" : s;
    }
}

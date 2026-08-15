/*
  by Ananda Widitomo,S.Kom.
  IT - SIMRS Hj. Fatimah Sulhan
 */
package bridging;
import bridging.ApiSatuSehat;
import bridging.SatuSehatCekNIK;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fungsi.koneksiDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

/**
 * Merakit daftar dokumen kandidat TTE untuk satu kunjungan (no_rawat) dari tabel satu_sehat_*,
 * plus meresolusi DPJP (IHS Practitioner) — agar handler menu di frmUtama tetap ringkas.
 *
 * Dokumen yang dirakit saat ini:
 *   - Resume Medis Rawat Inap  : satu_sehat_resume_ranap.id_composition  -> Composition (id lokal)
 *   - Resume Medis Rawat Jalan : satu_sehat_resume_ralan.id_composition  -> Composition (id lokal)
 *   - Resume Triase IGD        : satu_sehat_triase_igd.id_composition    -> Composition LOINC 75500-9 (id lokal; signer = dokter IGD/reg)
 *   - Surat Kontrol / Rencana Kontrol : satu_sehat_servicerequest_kontrol.id_servicerequest -> ServiceRequest (id lokal; sumber bridging_surat_kontrol_bpjs)
 *   - Surat Perintah Rawat Inap : satu_sehat_servicerequest_spri.id_servicerequest -> ServiceRequest (id lokal; signer = surat_perintah_rawat_inap.kd_dokter)
 *   - Laporan Operasi          : Composition LOINC 11504-8 (id DICARI ke server per encounter; signer = operasi.operator1)
 *   - Laporan Anestesi         : Composition LOINC 84062-9 (id DICARI ke server per encounter; signer = laporan_anestesi.kd_dokter_anastesi)
 *   - Laporan Persalinan       : Composition LOINC 57057-2 (id server per encounter; signer = catatan_persalinan.kd_dokter / .nip bidan)
 *   - Laporan USG/EKG/ESWL     : Composition LOINC 28570-0 (id lokal satu_sehat_laporan_usg/ekg/eswl; signer = hasil_*.kd_dokter)
 *   - Laporan Tindakan Echo    : Composition LOINC 28570-0 (id server per encounter, cocok by TITLE; signer = hasil_pemeriksaan_echo.kd_dokter)
 *
 * Belum dirakit (extension point {@link #tambahPenunjang}): Expertise Lab/Radiologi
 * (DiagnosticReport) & Resep (DocumentReference) — keterkaitannya per-ORDER, bukan langsung
 * no_rawat; kunci tabel diagnosticreport perlu dikonfirmasi (bootcamp) sebelum diquery.
 */
public class SatuSehatSignatureAssembler {

    private final Connection koneksi = koneksiDB.condb();
    private final SatuSehatCekNIK cek = new SatuSehatCekNIK();
    private final ObjectMapper mapper = new ObjectMapper();
    private final ApiSatuSehat api = new ApiSatuSehat();
    private String link = "";

    public SatuSehatSignatureAssembler() {
        try {
            link = koneksiDB.URLFHIRSATUSEHAT();
        } catch (Exception e) {
            System.out.println("Notifikasi TTEPerakit init : " + e);
        }
    }

    /** Hasil rakitan: identitas DPJP + dokter registrasi + daftar dokumen siap-TTE. */
    public static class Hasil {
        public String noRawat = "";
        public String idPractitioner = ""; // IHS DPJP (dpjp_ranap) — penanda-tangan resume ranap.
        public String namaDpjp = "";
        public String idReg = "";          // IHS dokter registrasi/IGD — penanda-tangan resume ralan/SPRI.
        public String namaReg = "";
        public String idRanap = "";        // IHS author resume ranap (resume_pasien_ranap.kd_dokter).
        public String namaRanap = "";
        public String idOperator = "";     // IHS operator bedah (operasi.operator1) -> Laporan Operasi.
        public String namaOperator = "";
        public String idAnestesi = "";     // IHS dokter anestesi (laporan_anestesi.kd_dokter_anastesi) -> Laporan Anestesi.
        public String namaAnestesi = "";
        public String idPersalinan = "";   // penolong (catatan_persalinan.kd_dokter/.nip) -> Laporan Persalinan.
        public String namaPersalinan = "";
        public String idUsg = "";          // hasil_pemeriksaan_usg.kd_dokter -> Laporan USG.
        public String namaUsg = "";
        public String idEkg = "";          // hasil_pemeriksaan_ekg.kd_dokter -> Laporan EKG.
        public String namaEkg = "";
        public String idEswl = "";         // hasil_tindakan_eswl.kd_dokter -> Laporan Tindakan ESWL.
        public String namaEswl = "";
        public String idEcho = "";         // hasil_pemeriksaan_echo.kd_dokter -> Laporan Tindakan Echo.
        public String namaEcho = "";
        public String idSpri = "";         // surat_perintah_rawat_inap.kd_dokter -> Surat Perintah Rawat Inap.
        public String namaSpri = "";
        public String idEncounter = "";
        public List<SatuSehatBridgingTTE.DokumenTte> dokumen = new ArrayList<>();

        public boolean kosong() {
            return dokumen.isEmpty();
        }
    }

    public Hasil rakit(String noRawat) {
        Hasil h = new Hasil();
        h.noRawat = nz(noRawat);
        if (h.noRawat.equals("")) {
            return h;
        }
        resolveReg(h.noRawat, h);         // dokter registrasi/IGD (reg_periksa) -> resume ralan/SPRI
        resolveRanapAuthor(h.noRawat, h); // author resume ranap (resume_pasien_ranap.kd_dokter)
        resolveDpjp(h.noRawat, h);        // DPJP ranap (dpjp_ranap) -> cadangan bila author ranap kosong
        resolveOperator(h.noRawat, h);    // operator bedah (operasi.operator1) -> Laporan Operasi
        resolveAnestesi(h.noRawat, h);    // dokter anestesi (laporan_anestesi) -> Laporan Anestesi
        resolvePersalinan(h.noRawat, h);  // penolong (catatan_persalinan) -> Laporan Persalinan
        resolveLaporanTindakan(h.noRawat, h); // dokter USG/EKG/ESWL/Echo (hasil_*)
        resolveSpriRanap(h.noRawat, h);   // dokter pemberi perintah (surat_perintah_rawat_inap) -> SPRI
        resolveEncounter(h.noRawat, h);
        tambahResume(h.noRawat, "satu_sehat_resume_ranap", "Resume Medis Rawat Inap", h);
        tambahResume(h.noRawat, "satu_sehat_resume_ralan", "Resume Medis Rawat Jalan", h);
        tambahResume(h.noRawat, "satu_sehat_triase_igd", "Resume Triase IGD", h); // Composition 75500-9, id lokal; signer = dokter IGD (reg)
        tambahResume(h.noRawat, "satu_sehat_laporan_usg", "Laporan USG", h);      // Composition 28570-0, id lokal
        tambahResume(h.noRawat, "satu_sehat_laporan_ekg", "Laporan EKG", h);      // Composition 28570-0, id lokal
        tambahEswlMulti(h.noRawat, h); // Composition 28570-0, id lokal — BISA >1 tindakan ESWL per kunjungan
        tambahSpri(h.noRawat, h);
        tambahSpriRanap(h.noRawat, h);    // Surat Perintah Rawat Inap (ServiceRequest, id lokal)
        tambahLaporanBedah(h.noRawat, h); // Laporan Operasi/Anestesi/Persalinan/Echo (Composition, id dicari ke server)
        tambahPenunjang(h.noRawat, h);
        tetapkanSigner(h);
        perluasPenanda(h);
        return h;
    }

    /**
     * Ubah daftar dokumen menjadi daftar BARIS TANDA TANGAN: satu baris per penanda.
     *
     * Sampai 1 Agustus 2026 tiap dokumen selalu punya tepat satu penanda (hasil
     * {@link #tetapkanSigner}). Aturan sebenarnya bergantung jenis dokumen — lembar anestesi
     * ditandatangani penata lalu dokter anestesi, laporan persalinan oleh bidan penolong lalu
     * dokter, SPRI oleh dokter pemberi perintah lalu DPJP yang merawat. Aturan itu dibaca dari
     * tabel {@code satu_sehat_tte_model} sehingga bisa diubah RS tanpa menyentuh kode.
     *
     * Dokumen "Expertise ..." sudah dirakit per penanda di {@link #tambahExpertise} (penandanya
     * per-order, tak bisa diambil dari Hasil), jadi di sini hanya dilewati.
     */
    private void perluasPenanda(Hasil h) {
        List<SatuSehatBridgingTTE.DokumenTte> tambahan = new ArrayList<>();
        for (SatuSehatBridgingTTE.DokumenTte d : h.dokumen) {
            String j = nz(d.jenis).toLowerCase();
            if (j.startsWith("expertise")) {
                continue;
            }
            SatuSehatTteModel.Aturan aturan = SatuSehatTteModel.untuk(d.jenis);
            d.model = aturan.model;
            if (aturan.slot.isEmpty()) {
                continue;
            }

            // Resolusikan SELURUH slot lebih dulu. Slot pertama pun dibaca dari `sumber`, bukan
            // diasumsikan sama dengan hasil tetapkanSigner: pada aturan resmi "perawat lalu DPJP",
            // penanda pertama justru BUKAN dokter yang dipilih tetapkanSigner. Bila sumbernya tak
            // bisa diresolusikan, slot itu dilewati — dokumen tetap bisa di-TTE oleh sisanya.
            List<SatuSehatBridgingTTE.DokumenTte> barisSlot = new ArrayList<>();
            java.util.Set<String> ihsDipakai = new java.util.HashSet<>();
            for (SatuSehatTteModel.Slot slot : aturan.slot) {
                List<String[]> penanda = penandaDariSumber(h, slot.sumber);
                if (penanda.isEmpty() && slot.urutan == 1 && aturan.slot.size() == 1) {
                    // Aturan slot-tunggal yang sumbernya tak dikenali -> pertahankan signer lama.
                    penanda = satuPenanda(d.signer, d.signerIhs);
                }
                if (penanda.isEmpty()) {
                    if (slot.wajib) {
                        System.out.println("Notifikasi TTEPerakit perluasPenanda LEWAT: " + d.jenis
                                + " slot " + slot.urutan + " (" + slot.sumber + ") IHS kosong.");
                    }
                    continue;
                }
                // SATU SLOT BISA BANYAK ORANG: pasien ranap dengan 2 DPJP menghasilkan 2 baris
                // ber-urutan sama, jadi keduanya wajib menandatangani dan tak saling menunggu.
                for (String[] orang : penanda) {
                    if (!ihsDipakai.add(orang[1])) {
                        // Orang yang sama mengisi dua peran (mis. dokter IGD merangkap DPJP):
                        // cukup satu baris — satu orang tak menandatangani dokumen yang sama dua kali.
                        continue;
                    }
                    SatuSehatBridgingTTE.DokumenTte baris = new SatuSehatBridgingTTE.DokumenTte(
                            d.jenis, d.targetRef, d.display);
                    baris.mulaiUtc = d.mulaiUtc;
                    baris.selesaiUtc = d.selesaiUtc;
                    baris.signer = orang[0];
                    baris.signerIhs = orang[1];
                    baris.peran = slot.peran;
                    baris.urutan = slot.urutan;
                    baris.model = aturan.model;
                    barisSlot.add(baris);
                }
            }
            if (barisSlot.isEmpty()) {
                continue;   // tak ada slot yang bisa diresolusikan -> biarkan baris lama apa adanya
            }
            // Baris yang sudah ada dipakai ulang untuk slot pertama supaya status/id yang sudah
            // dimuat pemanggil tidak hilang; sisanya jadi baris baru.
            SatuSehatBridgingTTE.DokumenTte pertama = barisSlot.get(0);
            d.signer = pertama.signer;
            d.signerIhs = pertama.signerIhs;
            d.peran = pertama.peran;
            d.urutan = pertama.urutan;
            for (int i = 1; i < barisSlot.size(); i++) {
                tambahan.add(barisSlot.get(i));
            }
        }
        h.dokumen.addAll(tambahan);
    }

    /**
     * Daftar penanda [nama, ihs] untuk sebuah kode sumber. JAMAK, karena satu peran bisa dipegang
     * beberapa orang — pasien ranap boleh punya lebih dari satu DPJP (PK dpjp_ranap =
     * (no_rawat, kd_dokter)), dan seluruhnya menandatangani resume yang sama.
     * Daftar kosong bila sumbernya tak ada / tak ter-mapping ke IHS.
     */
    private List<String[]> penandaDariSumber(Hasil h, String sumber) {
        String s = nz(sumber).toLowerCase();
        switch (s) {
            case "dpjp":
                // SELURUH DPJP kunjungan ini, bukan sekadar yang pertama. Diurutkan agar daftarnya
                // stabil antar-pemuatan (dpjp_ranap tak punya kolom waktu).
                return pegawaiDariKolomJamak(h.noRawat,
                        "select ifnull(pg.nama,'') nama, ifnull(pg.no_ktp,'') ktp "
                        + "from dpjp_ranap d join pegawai pg on pg.nik=d.kd_dokter "
                        + "where d.no_rawat=? order by d.kd_dokter");
            case "reg":        return satuPenanda(h.namaReg, h.idReg);
            case "ranap":      return satuPenanda(h.namaRanap, h.idRanap);
            case "operator":   return satuPenanda(h.namaOperator, h.idOperator);
            case "anestesi":   return satuPenanda(h.namaAnestesi, h.idAnestesi);
            case "persalinan": return satuPenanda(h.namaPersalinan, h.idPersalinan);
            case "kd_dokter_igd":
                // Dokter jaga IGD yang memeriksa pasien saat masuk. Untuk pasien ranap yang lewat
                // IGD, no_rawat-nya sama dengan kunjungan ranap (Khanza mengubah status_lanjut,
                // bukan membuat no_rawat baru) — sumber yang sama dipakai Resume Ranap untuk
                // keluhan utama & RPS.
                return pegawaiDariKolomJamak(h.noRawat,
                        "select ifnull(pg.nama,'') nama, ifnull(pg.no_ktp,'') ktp "
                        + "from penilaian_medis_igd pmi "
                        + "join pegawai pg on pg.nik=pmi.kd_dokter where pmi.no_rawat=? limit 1");
            case "nip_perawat_ranap":
                return pegawaiDariKolomJamak(h.noRawat,
                        "select ifnull(pg.nama,'') nama, ifnull(pg.no_ktp,'') ktp "
                        + "from penilaian_awal_keperawatan_ranap k "
                        + "join pegawai pg on pg.nik=k.nip1 where k.no_rawat=? limit 1");
            case "kd_penata":
                return pegawaiDariKolomJamak(h.noRawat,
                        "select ifnull(pg.nama,'') nama, ifnull(pg.no_ktp,'') ktp from laporan_anestesi la "
                        + "join pegawai pg on pg.nik=la.kd_penata where la.no_rawat=? limit 1");
            case "kd_dokter_bedah":
                return pegawaiDariKolomJamak(h.noRawat,
                        "select ifnull(pg.nama,'') nama, ifnull(pg.no_ktp,'') ktp from laporan_anestesi la "
                        + "join pegawai pg on pg.nik=la.kd_dokter_bedah where la.no_rawat=? limit 1");
            case "nip_bidan":
                return pegawaiDariKolomJamak(h.noRawat,
                        "select ifnull(pg.nama,'') nama, ifnull(pg.no_ktp,'') ktp from catatan_persalinan cp "
                        + "join pegawai pg on pg.nik=cp.nip where cp.no_rawat=? limit 1");
            case "kd_dpjp_spri":
                return pegawaiDariKolomJamak(h.noRawat,
                        "select ifnull(pg.nama,'') nama, ifnull(pg.no_ktp,'') ktp "
                        + "from surat_perintah_rawat_inap spri "
                        + "join pegawai pg on pg.nik=spri.kd_dpjp where spri.no_rawat=? limit 1");
            case "nip_eswl":
                return pegawaiDariKolomJamak(h.noRawat,
                        "select ifnull(pg.nama,'') nama, ifnull(pg.no_ktp,'') ktp from hasil_tindakan_eswl te "
                        + "join pegawai pg on pg.nik=te.nip where te.no_rawat=? limit 1");
            case "operator2":
                return pegawaiDariKolomJamak(h.noRawat,
                        "select ifnull(pg.nama,'') nama, ifnull(pg.no_ktp,'') ktp from operasi o "
                        + "join pegawai pg on pg.nik=o.operator2 where o.no_rawat=? limit 1");
            default:
                return new ArrayList<String[]>();
        }
    }

    /** Bungkus satu pasangan (nama, ihs) jadi daftar; kosong bila IHS-nya belum ada. */
    private List<String[]> satuPenanda(String nama, String ihs) {
        List<String[]> out = new ArrayList<>();
        if (!nz(ihs).equals("")) {
            out.add(new String[]{nz(nama), nz(ihs)});
        }
        return out;
    }

    /**
     * Jalankan query (satu param no_rawat) yang mengembalikan (nama, ktp) — BISA BANYAK BARIS —
     * lalu resolusikan tiap barisnya ke IHS Practitioner. Baris yang IHS-nya kosong dibuang:
     * penanda tanpa Practitioner valid akan menggagalkan Provenance-nya.
     */
    private List<String[]> pegawaiDariKolomJamak(String noRawat, String sql) {
        List<String[]> hasil = new ArrayList<>();
        try (PreparedStatement p = koneksi.prepareStatement(sql)) {
            p.setString(1, noRawat);
            try (ResultSet r = p.executeQuery()) {
                while (r.next()) {
                    String nama = nz(r.getString("nama"));
                    String ktp = nz(r.getString("ktp"));
                    String ihs = ktp.equals("") ? "" : nz(cek.tampilIDParktisi(ktp));
                    if (!ihs.equals("")) {
                        hasil.add(new String[]{nama, ihs});
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi TTEPerakit penandaDariSumber : " + e);
        }
        return hasil;
    }

    /**
     * Tetapkan penanda-tangan tiap berkas ("Dokter Sign") = AUTHOR sebenarnya berkas itu,
     * mengikuti sumber yang sama dengan sender yang dulu mengirimnya ke SATUSEHAT:
     *   - Resume Medis Rawat Inap  -> resume_pasien_ranap.kd_dokter (SatuSehatResumeMedisRanap).
     *   - Resume Medis Rawat Jalan -> reg_periksa.kd_dokter        (SatuSehatResumeMedisRajal).
     *   - Surat Kontrol / Rencana Kontrol -> reg_periksa.kd_dokter (SatuSehatRencanaKontrol).
     */
    private void tetapkanSigner(Hasil h) {
        for (SatuSehatBridgingTTE.DokumenTte d : h.dokumen) {
            String j = nz(d.jenis).toLowerCase();
            // Signer sudah di-set PER-DOKUMEN di perakit: Expertise (per-order di tambahPenunjang) &
            // ESWL (per-tindakan di tambahEswlMulti) -> jangan ditimpa dokter reg/DPJP.
            if (j.startsWith("expertise") || j.contains("eswl")) {
                continue;
            }
            if (j.contains("operasi")) {
                // Laporan Operasi -> operator bedah (operasi.operator1), sama dgn author Composition-nya.
                d.signer = h.namaOperator;
                d.signerIhs = h.idOperator;
            } else if (j.contains("anestesi")) {
                // Laporan Anestesi -> dokter anestesi (laporan_anestesi.kd_dokter_anastesi).
                d.signer = h.namaAnestesi;
                d.signerIhs = h.idAnestesi;
            } else if (j.contains("persalinan")) {
                d.signer = h.namaPersalinan;
                d.signerIhs = h.idPersalinan;
            } else if (j.contains("usg")) {
                d.signer = h.namaUsg;
                d.signerIhs = h.idUsg;
            } else if (j.contains("ekg")) {
                d.signer = h.namaEkg;
                d.signerIhs = h.idEkg;
            } else if (j.contains("echo")) {
                d.signer = h.namaEcho;
                d.signerIhs = h.idEcho;
            } else if (j.contains("perintah rawat inap")) {
                // SPRI -> dokter pemberi perintah (surat_perintah_rawat_inap.kd_dokter).
                // WAJIB dicek sebelum isRawatInap: label memuat "rawat inap".
                d.signer = h.namaSpri;
                d.signerIhs = h.idSpri;
            } else if (isRawatInap(d.jenis)) {
                d.signer = h.namaRanap;
                d.signerIhs = h.idRanap;
                // cadangan bila author resume ranap tak tercatat: DPJP ranap, lalu dokter reg.
                if (d.signer.equals("") && d.signerIhs.equals("")) {
                    d.signer = h.namaDpjp;
                    d.signerIhs = h.idPractitioner;
                }
                if (d.signer.equals("") && d.signerIhs.equals("")) {
                    d.signer = h.namaReg;
                    d.signerIhs = h.idReg;
                }
            } else {
                d.signer = h.namaReg;
                d.signerIhs = h.idReg;
            }
        }
    }

    private boolean isRawatInap(String jenis) {
        return nz(jenis).toLowerCase().contains("rawat inap");
    }

    /** Ambil id_encounter kunjungan (untuk Task.encounter yang wajib). */
    private void resolveEncounter(String noRawat, Hasil h) {
        try {
            PreparedStatement p = koneksi.prepareStatement(
                    "select ifnull(id_encounter,'') as id_encounter from satu_sehat_encounter where no_rawat=? limit 1");
            p.setString(1, noRawat);
            ResultSet r = p.executeQuery();
            if (r.next()) {
                h.idEncounter = nz(r.getString("id_encounter"));
            }
            r.close();
            p.close();
        } catch (Exception e) {
            System.out.println("Notifikasi TTEPerakit resolveEncounter : " + e);
        }
    }

    /**
     * Resolusi penanda-tangan (DPJP). PRIORITAS DPJP asli dari {@code dpjp_ranap} — inilah
     * author Composition resume di SATUSEHAT (mis. dr. Caesar Sp.OG), BUKAN dokter registrasi/IGD.
     * Fallback ke dokter reg_periksa hanya bila kunjungan tak punya DPJP ranap (mis. rawat jalan).
     */
    private void resolveDpjp(String noRawat, Hasil h) {
        if (resolveDariDpjpRanap(noRawat, h)) {
            return;
        }
        // Tak ada DPJP ranap (mis. kunjungan rawat jalan) -> DPJP = dokter registrasi.
        h.namaDpjp = h.namaReg;
        h.idPractitioner = h.idReg;
    }

    /** DPJP asli dari dpjp_ranap. @return true bila kunjungan punya DPJP (dipakai sebagai signer). */
    private boolean resolveDariDpjpRanap(String noRawat, Hasil h) {
        try {
            PreparedStatement p = koneksi.prepareStatement(
                    "select ifnull(dr.nm_dokter,'') as nm_dokter, ifnull(pg.no_ktp,'') as ktp_dokter "
                    + "from dpjp_ranap dj "
                    + "left join dokter dr on dr.kd_dokter=dj.kd_dokter "
                    + "left join pegawai pg on pg.nik=dj.kd_dokter "
                    + "where dj.no_rawat=? limit 1");
            p.setString(1, noRawat);
            ResultSet r = p.executeQuery();
            boolean ada = false;
            if (r.next()) {
                // Ada baris DPJP -> pakai DPJP ini sebagai signer (walau IHS belum ter-mapping,
                // biar tidak keliru memakai dokter IGD; guard mapping ada di dialog saat TTE).
                h.namaDpjp = nz(r.getString("nm_dokter"));
                h.idPractitioner = nz(cek.tampilIDParktisi(nz(r.getString("ktp_dokter"))));
                ada = true;
            }
            r.close();
            p.close();
            return ada;
        } catch (Exception e) {
            // dpjp_ranap bisa tak ada di skema tertentu -> fallback ke reg_periksa.
            System.out.println("Notifikasi TTEPerakit resolveDariDpjpRanap : " + e);
            return false;
        }
    }

    /** Dokter registrasi/IGD dari reg_periksa -> h.namaReg/idReg (penanda-tangan resume ralan/SPRI). */
    private void resolveReg(String noRawat, Hasil h) {
        try {
            PreparedStatement p = koneksi.prepareStatement(
                    "select ifnull(dr.nm_dokter,'') as nm_dokter, ifnull(pg.no_ktp,'') as ktp_dokter "
                    + "from reg_periksa rp "
                    + "left join dokter dr on dr.kd_dokter=rp.kd_dokter "
                    + "left join pegawai pg on pg.nik=rp.kd_dokter "
                    + "where rp.no_rawat=? limit 1");
            p.setString(1, noRawat);
            ResultSet r = p.executeQuery();
            if (r.next()) {
                h.namaReg = nz(r.getString("nm_dokter"));
                h.idReg = nz(cek.tampilIDParktisi(nz(r.getString("ktp_dokter"))));
            }
            r.close();
            p.close();
        } catch (Exception e) {
            System.out.println("Notifikasi TTEPerakit resolveReg : " + e);
        }
    }

    /** Author resume ranap dari resume_pasien_ranap.kd_dokter -> h.namaRanap/idRanap (dokter yg TTD). */
    private void resolveRanapAuthor(String noRawat, Hasil h) {
        try {
            PreparedStatement p = koneksi.prepareStatement(
                    "select ifnull(dr.nm_dokter,'') as nm_dokter, ifnull(pg.no_ktp,'') as ktp_dokter "
                    + "from resume_pasien_ranap rpr "
                    + "left join dokter dr on dr.kd_dokter=rpr.kd_dokter "
                    + "left join pegawai pg on pg.nik=rpr.kd_dokter "
                    + "where rpr.no_rawat=? limit 1");
            p.setString(1, noRawat);
            ResultSet r = p.executeQuery();
            if (r.next()) {
                h.namaRanap = nz(r.getString("nm_dokter"));
                h.idRanap = nz(cek.tampilIDParktisi(nz(r.getString("ktp_dokter"))));
            }
            r.close();
            p.close();
        } catch (Exception e) {
            // Tabel bisa tak ada di skema tertentu -> biarkan kosong (jatuh ke cadangan DPJP/reg).
            System.out.println("Notifikasi TTEPerakit resolveRanapAuthor : " + e);
        }
    }

    /** Operator bedah (operasi.operator1) -> penanda-tangan Laporan Operasi (= author Composition-nya). */
    private void resolveOperator(String noRawat, Hasil h) {
        try {
            PreparedStatement p = koneksi.prepareStatement(
                    "select ifnull(dr.nm_dokter,'') as nm_dokter, ifnull(pg.no_ktp,'') as ktp_dokter "
                    + "from laporan_operasi lo "
                    + "left join operasi o on o.no_rawat=lo.no_rawat "
                    + "left join dokter dr on dr.kd_dokter=o.operator1 "
                    + "left join pegawai pg on pg.nik=o.operator1 "
                    + "where lo.no_rawat=? limit 1");
            p.setString(1, noRawat);
            ResultSet r = p.executeQuery();
            if (r.next()) {
                h.namaOperator = nz(r.getString("nm_dokter"));
                h.idOperator = nz(cek.tampilIDParktisi(nz(r.getString("ktp_dokter"))));
            }
            r.close();
            p.close();
        } catch (Exception e) {
            // Tabel operasi/laporan_operasi bisa tak ada di skema tertentu -> biarkan kosong.
            System.out.println("Notifikasi TTEPerakit resolveOperator : " + e);
        }
    }

    /** Dokter anestesi (laporan_anestesi.kd_dokter_anastesi) -> penanda-tangan Laporan Anestesi. */
    private void resolveAnestesi(String noRawat, Hasil h) {
        try {
            PreparedStatement p = koneksi.prepareStatement(
                    "select ifnull(dr.nm_dokter,'') as nm_dokter, ifnull(pg.no_ktp,'') as ktp_dokter "
                    + "from laporan_anestesi la "
                    + "left join dokter dr on dr.kd_dokter=la.kd_dokter_anastesi "
                    + "left join pegawai pg on pg.nik=la.kd_dokter_anastesi "
                    + "where la.no_rawat=? order by la.tanggal desc limit 1");
            p.setString(1, noRawat);
            ResultSet r = p.executeQuery();
            if (r.next()) {
                h.namaAnestesi = nz(r.getString("nm_dokter"));
                h.idAnestesi = nz(cek.tampilIDParktisi(nz(r.getString("ktp_dokter"))));
            }
            r.close();
            p.close();
        } catch (Exception e) {
            System.out.println("Notifikasi TTEPerakit resolveAnestesi : " + e);
        }
    }

    /** Penolong persalinan (catatan_persalinan.kd_dokter, cadangan .nip bidan) -> signer Laporan Persalinan. */
    private void resolvePersalinan(String noRawat, Hasil h) {
        try (PreparedStatement p = koneksi.prepareStatement(
                "select ifnull(pdok.no_ktp,'') as ktp_dokter, ifnull(pdok.nama,'') as nama_dokter, "
                + "ifnull(pbid.no_ktp,'') as ktp_bidan, ifnull(pbid.nama,'') as nama_bidan "
                + "from catatan_persalinan cp "
                + "left join pegawai pdok on pdok.nik=cp.kd_dokter "
                + "left join pegawai pbid on pbid.nik=cp.nip "
                + "where cp.no_rawat=? limit 1")) {
            p.setString(1, noRawat);
            try (ResultSet r = p.executeQuery()) {
                if (r.next()) {
                    String ktpDok = nz(r.getString("ktp_dokter"));
                    boolean pakaiDok = !ktpDok.equals("");
                    h.namaPersalinan = pakaiDok ? nz(r.getString("nama_dokter")) : nz(r.getString("nama_bidan"));
                    h.idPersalinan = nz(cek.tampilIDParktisi(pakaiDok ? ktpDok : nz(r.getString("ktp_bidan"))));
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi TTEPerakit resolvePersalinan : " + e);
        }
    }

    /** Signer 4 laporan tindakan (USG/EKG/ESWL/Echo) dari tabel hasil_* masing-masing. */
    private void resolveLaporanTindakan(String noRawat, Hasil h) {
        String[] u = resolveDokterTabel(noRawat, "hasil_pemeriksaan_usg");  h.namaUsg = u[0];  h.idUsg = u[1];
        String[] k = resolveDokterTabel(noRawat, "hasil_pemeriksaan_ekg");  h.namaEkg = k[0];  h.idEkg = k[1];
        String[] s = resolveDokterTabel(noRawat, "hasil_tindakan_eswl");    h.namaEswl = s[0]; h.idEswl = s[1];
        String[] c = resolveDokterTabel(noRawat, "hasil_pemeriksaan_echo"); h.namaEcho = c[0]; h.idEcho = c[1];
    }

    /** Dokter dari {@code <tabel>.kd_dokter} -> {nama, IHS}; {"",""} bila tabel/baris tak ada. */
    private String[] resolveDokterTabel(String noRawat, String tabel) {
        String[] out = {"", ""};
        try (PreparedStatement p = koneksi.prepareStatement(
                "select ifnull(dr.nm_dokter,'') as nm_dokter, ifnull(pg.no_ktp,'') as ktp "
                + "from " + tabel + " t "
                + "left join dokter dr on dr.kd_dokter=t.kd_dokter "
                + "left join pegawai pg on pg.nik=t.kd_dokter "
                + "where t.no_rawat=? limit 1")) {
            p.setString(1, noRawat);
            try (ResultSet r = p.executeQuery()) {
                if (r.next()) {
                    out[0] = nz(r.getString("nm_dokter"));
                    out[1] = nz(cek.tampilIDParktisi(nz(r.getString("ktp"))));
                }
            }
        } catch (Exception e) {
            // Tabel bisa tak ada di skema tertentu -> biarkan kosong.
            System.out.println("Notifikasi TTEPerakit resolveDokterTabel(" + tabel + ") : " + e);
        }
        return out;
    }

    /** Tambah dokumen Resume (Composition) dari tabel resume ranap/ralan bila id sudah ada. */
    /**
     * Tambah SEMUA Composition ESWL kunjungan sebagai berkas TTE terpisah — bisa &gt;1 tindakan per
     * kunjungan; masing-masing punya id_composition sendiri di satu_sehat_laporan_eswl (keyed no_rawat+mulai).
     * jenis tetap "Laporan Tindakan ESWL" (agar signer ESWL benar); display dibedakan dengan waktu tindakan.
     */
    private void tambahEswlMulti(String noRawat, Hasil h) {
        try (PreparedStatement p = koneksi.prepareStatement(
                "select se.id_composition, se.mulai, ifnull(dr.nm_dokter,'') as nm_dokter, "
                + "ifnull(pg.no_ktp,'') as ktp "
                + "from satu_sehat_laporan_eswl se "
                + "left join hasil_tindakan_eswl te on te.no_rawat=se.no_rawat and te.mulai=se.mulai "
                + "left join dokter dr on dr.kd_dokter=te.kd_dokter "
                + "left join pegawai pg on pg.nik=te.kd_dokter "
                + "where se.no_rawat=? and ifnull(se.id_composition,'')<>'' order by se.mulai asc")) {
            p.setString(1, noRawat);
            try (ResultSet r = p.executeQuery()) {
                while (r.next()) {
                    String id = nz(r.getString("id_composition"));
                    if (id.equals("")) {
                        continue;
                    }
                    String mulai = nz(r.getString("mulai"));
                    String label = "Laporan Tindakan ESWL";
                    String display = mulai.equals("") ? label : (label + " — " + mulai);
                    SatuSehatBridgingTTE.DokumenTte d =
                            new SatuSehatBridgingTTE.DokumenTte(label, "Composition/" + id, display);
                    // Signer PER TINDAKAN = dokter baris hasil_tindakan_eswl itu (bukan satu dokter utk semua).
                    d.signer = nz(r.getString("nm_dokter"));
                    d.signerIhs = nz(cek.tampilIDParktisi(nz(r.getString("ktp"))));
                    h.dokumen.add(d);
                }
            }
        } catch (Exception e) {
            // Tabel/kolom mulai bisa tak ada di instalasi lama -> lewati diam-diam.
            System.out.println("Notifikasi TTEPerakit tambahEswlMulti : " + e);
        }
    }

    private void tambahResume(String noRawat, String tabel, String label, Hasil h) {
        try {
            PreparedStatement p = koneksi.prepareStatement(
                    "select id_composition from " + tabel
                    + " where no_rawat=? and ifnull(id_composition,'')<>'' limit 1");
            p.setString(1, noRawat);
            ResultSet r = p.executeQuery();
            if (r.next()) {
                String id = nz(r.getString("id_composition"));
                if (!id.equals("")) {
                    h.dokumen.add(new SatuSehatBridgingTTE.DokumenTte(label, "Composition/" + id, label));
                }
            }
            r.close();
            p.close();
        } catch (Exception e) {
            // Tabel/kolom bisa saja tak ada di skema tertentu -> lewati diam-diam.
            System.out.println("Notifikasi TTEPerakit tambahResume(" + tabel + ") : " + e);
        }
    }

    /** Dokter pemberi perintah (surat_perintah_rawat_inap.kd_dokter) -> signer SPRI. */
    private void resolveSpriRanap(String noRawat, Hasil h) {
        String[] sp = resolveDokterTabel(noRawat, "surat_perintah_rawat_inap");
        h.namaSpri = sp[0];
        h.idSpri = sp[1];
    }

    /** Tambah dokumen Surat Perintah Rawat Inap (ServiceRequest, satu_sehat_servicerequest_spri) bila id sudah ada. */
    private void tambahSpriRanap(String noRawat, Hasil h) {
        try (PreparedStatement p = koneksi.prepareStatement(
                "select id_servicerequest from satu_sehat_servicerequest_spri "
                + "where no_rawat=? and ifnull(id_servicerequest,'')<>'' limit 1")) {
            p.setString(1, noRawat);
            try (ResultSet r = p.executeQuery()) {
                if (r.next()) {
                    String id = nz(r.getString("id_servicerequest"));
                    if (!id.equals("")) {
                        h.dokumen.add(new SatuSehatBridgingTTE.DokumenTte(
                                "Surat Perintah Rawat Inap", "ServiceRequest/" + id, "Surat Perintah Rawat Inap (SPRI)"));
                    }
                }
            }
        } catch (Exception e) {
            // Tabel bisa tak ada di skema tertentu -> lewati diam-diam.
            System.out.println("Notifikasi TTEPerakit tambahSpriRanap : " + e);
        }
    }

    /** Tambah dokumen Surat Kontrol / Rencana Kontrol (ServiceRequest, satu_sehat_servicerequest_kontrol) bila id sudah ada. */
    private void tambahSpri(String noRawat, Hasil h) {
        try {
            PreparedStatement p = koneksi.prepareStatement(
                    "select id_servicerequest from satu_sehat_servicerequest_kontrol "
                    + "where no_rawat=? and ifnull(id_servicerequest,'')<>'' limit 1");
            p.setString(1, noRawat);
            ResultSet r = p.executeQuery();
            if (r.next()) {
                String id = nz(r.getString("id_servicerequest"));
                if (!id.equals("")) {
                    h.dokumen.add(new SatuSehatBridgingTTE.DokumenTte(
                            "Surat Kontrol / Rencana Kontrol", "ServiceRequest/" + id, "Surat Kontrol / Rencana Kontrol"));
                }
            }
            r.close();
            p.close();
        } catch (Exception e) {
            System.out.println("Notifikasi TTEPerakit tambahSpri : " + e);
        }
    }

    /**
     * Laporan Operasi (Composition LOINC 11504-8) & Laporan Anestesi (Composition 84062-9).
     * Id Composition-nya TIDAK disimpan lokal (Operasi random-UUID; Anestesi via server/deterministik),
     * jadi dicari ke SATUSEHAT lewat SATU GET {FHIR}/Composition?encounter={idEncounter} lalu dicocokkan
     * identifier "OPERASI-"/"ANESTESI-"+noRawat atau LOINC type. Dokumen HANYA muncul bila Composition-nya
     * benar-benar ada di server (self-guard: "laporan sudah dikirim") — menghindari target Provenance hantu.
     * Signer di-set {@link #tetapkanSigner} = operator bedah / dokter anestesi.
     */
    private void tambahLaporanBedah(String noRawat, Hasil h) {
        if (h.idEncounter.equals("") || link.equals("")) {
            return; // tanpa Encounter tak bisa search (dan Task.encounter wajib).
        }
        // Pre-check murah: hindari GET ke server bila pasien tak punya laporan-laporan ini (mayoritas).
        boolean adaOperasi = punyaBaris("laporan_operasi", noRawat);
        boolean adaAnestesi = punyaBaris("laporan_anestesi", noRawat);
        boolean adaPersalinan = punyaBaris("catatan_persalinan", noRawat);
        boolean adaEcho = punyaBaris("hasil_pemeriksaan_echo", noRawat);
        if (!adaOperasi && !adaAnestesi && !adaPersalinan && !adaEcho) {
            return;
        }
        try {
            HttpHeaders hd = new HttpHeaders();
            hd.add("Authorization", "Bearer " + api.TokenSatuSehat());
            HttpEntity req = new HttpEntity(hd);
            java.net.URI uri = java.net.URI.create(
                    link + "/Composition?encounter=" + h.idEncounter + "&_count=100");
            String body = api.getRest().exchange(uri, HttpMethod.GET, req, String.class).getBody();
            JsonNode root = mapper.readTree(body);
            String idOperasi = "", idAnestesi = "", idPersalinan = "", idEcho = "";
            for (JsonNode e : root.path("entry")) {
                JsonNode res = e.path("resource");
                String id = nz(res.path("id").asText());
                if (id.equals("")) {
                    continue;
                }
                // Composition.identifier = objek tunggal (bukan array); server pertahankan title.
                String iden = nz(res.path("identifier").path("value").asText());
                String title = nz(res.path("title").asText());
                boolean tOperasi = iden.equals("OPERASI-" + noRawat);
                boolean tAnestesi = iden.equals("ANESTESI-" + noRawat);
                boolean tPersalinan = iden.equals("PERSALINAN-" + noRawat);
                for (JsonNode cd : res.path("type").path("coding")) {
                    String c = nz(cd.path("code").asText());
                    if (c.equals("11504-8")) tOperasi = true;    // Surgical operation note
                    if (c.equals("84062-9")) tAnestesi = true;   // Anesthesiology procedure note
                    if (c.equals("57057-2")) tPersalinan = true; // Labor and delivery summary note
                }
                // Echo LOINC 28570-0 dipakai bersama ESWL/EKG/USG -> dibedakan HANYA via title.
                boolean tEcho = title.equals("Laporan Tindakan Echo");
                if (idOperasi.equals("") && tOperasi) {
                    idOperasi = id;
                }
                if (idAnestesi.equals("") && tAnestesi) {
                    idAnestesi = id;
                }
                if (idPersalinan.equals("") && tPersalinan) {
                    idPersalinan = id;
                }
                if (idEcho.equals("") && tEcho) {
                    idEcho = id;
                }
            }
            // Guard ganda: hanya tampilkan jenis yang ADA lokal (laporan_*) DAN ketemu Composition di server.
            if (adaOperasi && !idOperasi.equals("")) {
                h.dokumen.add(new SatuSehatBridgingTTE.DokumenTte(
                        "Laporan Operasi", "Composition/" + idOperasi, "Laporan Operasi"));
            }
            if (adaAnestesi && !idAnestesi.equals("")) {
                h.dokumen.add(new SatuSehatBridgingTTE.DokumenTte(
                        "Laporan Anestesi", "Composition/" + idAnestesi, "Laporan Anestesi"));
            }
            if (adaPersalinan && !idPersalinan.equals("")) {
                h.dokumen.add(new SatuSehatBridgingTTE.DokumenTte(
                        "Laporan Persalinan", "Composition/" + idPersalinan, "Laporan Persalinan"));
            }
            if (adaEcho && !idEcho.equals("")) {
                h.dokumen.add(new SatuSehatBridgingTTE.DokumenTte(
                        "Laporan Tindakan Echo", "Composition/" + idEcho, "Laporan Tindakan Echo"));
            }
        } catch (Exception e) {
            // Gagal jaringan/token -> jangan tambahkan (dokumen hanya muncul bila Composition ada di server).
            System.out.println("Notifikasi TTEPerakit tambahLaporanBedah : " + e);
        }
    }

    /** true bila tabel punya minimal satu baris untuk no_rawat (pre-check murah sebelum GET server). */
    private boolean punyaBaris(String tabel, String noRawat) {
        try (PreparedStatement p = koneksi.prepareStatement(
                "select 1 from " + tabel + " where no_rawat=? limit 1")) {
            p.setString(1, noRawat);
            try (ResultSet r = p.executeQuery()) {
                return r.next();
            }
        } catch (Exception e) {
            // Tabel bisa tak ada di skema tertentu -> anggap tak ada (lewati diam-diam).
            return false;
        }
    }

    /**
     * Rakit Expertise penunjang (DiagnosticReport) yang SUDAH dikirim ke SATUSEHAT untuk kunjungan ini:
     *   - Expertise Radiologi     : satu_sehat_diagnosticreport_radiologi (signer = periksa_radiologi.kd_dokter)
     *   - Expertise Laboratorium  : satu_sehat_diagnosticreport_lab       (signer = periksa_lab.kd_dokter)
     *   - Expertise Mikrobiologi  : satu_sehat_diagnosticreport_lab_mb    (signer = periksa_lab.kd_dokter)
     * Join mengikuti sender resmi (SatuSehatKirimDiagnosticReport*) agar targetRef tidak salah tunjuk.
     * Signer di-set langsung di sini (per-order, bisa beda dokter) -> {@link #tetapkanSigner} melewati
     * dokumen berawalan "Expertise".
     *
     * Resep (DocumentReference) BELUM dirakit: SIMRS belum punya sender DocumentReference resep, jadi
     * target DocumentReference/&lt;id&gt; belum ada di server (tak bisa di-TTE). Aktifkan setelah sender resep ada.
     */
    private void tambahPenunjang(String noRawat, Hasil h) {
        tambahExpertise(noRawat, h, "Expertise Radiologi",
                "select distinct dr.id_diagnosticreport as id_dr, pr.noorder as noorder, "
                + "ifnull(pg.nama,'') as nama, ifnull(pg.no_ktp,'') as ktp, "
                + "ifnull(pgx.nama,'') as nama_pelaksana, ifnull(pgx.no_ktp,'') as ktp_pelaksana "
                + "from reg_periksa rp "
                + "join permintaan_radiologi pr on pr.no_rawat=rp.no_rawat "
                + "join permintaan_pemeriksaan_radiologi ppr on ppr.noorder=pr.noorder "
                + "join satu_sehat_servicerequest_radiologi sr on sr.noorder=ppr.noorder "
                + "join periksa_radiologi px on px.no_rawat=pr.no_rawat and px.tgl_periksa=pr.tgl_hasil "
                + "and px.jam=pr.jam_hasil and px.dokter_perujuk=pr.dokter_perujuk "
                + "join satu_sehat_diagnosticreport_radiologi dr on sr.noorder=dr.noorder and sr.kd_jenis_prw=dr.kd_jenis_prw "
                + "join pegawai pg on pg.nik=px.kd_dokter "
                + "left join pegawai pgx on pgx.nik=px.nip "
                + "where rp.no_rawat=? and ifnull(dr.id_diagnosticreport,'')<>''");

        tambahExpertise(noRawat, h, "Expertise Laboratorium",
                "select distinct dr.id_diagnosticreport as id_dr, pl.noorder as noorder, "
                + "ifnull(pg.nama,'') as nama, ifnull(pg.no_ktp,'') as ktp, "
                + "ifnull(pgx.nama,'') as nama_pelaksana, ifnull(pgx.no_ktp,'') as ktp_pelaksana "
                + "from reg_periksa rp "
                + "join permintaan_lab pl on pl.no_rawat=rp.no_rawat "
                + "join permintaan_detail_permintaan_lab pdl on pdl.noorder=pl.noorder "
                + "join satu_sehat_servicerequest_lab sr on sr.noorder=pdl.noorder "
                + "join periksa_lab px on px.no_rawat=pl.no_rawat and px.tgl_periksa=pl.tgl_hasil "
                + "and px.jam=pl.jam_hasil and px.dokter_perujuk=pl.dokter_perujuk "
                + "join satu_sehat_diagnosticreport_lab dr on sr.noorder=dr.noorder "
                + "join pegawai pg on pg.nik=px.kd_dokter "
                + "left join pegawai pgx on pgx.nik=px.nip "
                + "where rp.no_rawat=? and ifnull(dr.id_diagnosticreport,'')<>''");

        tambahExpertise(noRawat, h, "Expertise Mikrobiologi",
                "select distinct dr.id_diagnosticreport as id_dr, pl.noorder as noorder, "
                + "ifnull(pg.nama,'') as nama, ifnull(pg.no_ktp,'') as ktp, "
                + "ifnull(pgx.nama,'') as nama_pelaksana, ifnull(pgx.no_ktp,'') as ktp_pelaksana "
                + "from reg_periksa rp "
                + "join permintaan_labmb pl on pl.no_rawat=rp.no_rawat "
                + "join permintaan_detail_permintaan_labmb pdl on pdl.noorder=pl.noorder "
                + "join satu_sehat_servicerequest_lab_mb sr on sr.noorder=pdl.noorder "
                + "and sr.id_template=pdl.id_template and sr.kd_jenis_prw=pdl.kd_jenis_prw "
                + "join periksa_lab px on px.no_rawat=pl.no_rawat and px.tgl_periksa=pl.tgl_hasil "
                + "and px.jam=pl.jam_hasil and px.dokter_perujuk=pl.dokter_perujuk "
                + "join satu_sehat_diagnosticreport_lab_mb dr on sr.noorder=dr.noorder "
                + "and sr.id_template=dr.id_template and sr.kd_jenis_prw=dr.kd_jenis_prw "
                + "join pegawai pg on pg.nik=px.kd_dokter "
                + "left join pegawai pgx on pgx.nik=px.nip "
                + "where rp.no_rawat=? and ifnull(dr.id_diagnosticreport,'')<>''");
    }

    /**
     * Jalankan {@code sql} (satu param no_rawat) yang mengembalikan (id_dr, noorder, nama, ktp) lalu
     * tambahkan tiap baris sebagai DokumenTte target "DiagnosticReport/&lt;id_dr&gt;" dgn signer per-order.
     * Tabel bisa tak ada di skema tertentu -> lewati diam-diam (konsisten pola perakit lain).
     */
    private void tambahExpertise(String noRawat, Hasil h, String jenis, String sql) {
        try (PreparedStatement p = koneksi.prepareStatement(sql)) {
            p.setString(1, noRawat);
            try (ResultSet r = p.executeQuery()) {
                while (r.next()) {
                    String idDr = nz(r.getString("id_dr"));
                    if (idDr.equals("")) {
                        continue;
                    }
                    String noorder = nz(r.getString("noorder"));
                    String nama = nz(r.getString("nama"));
                    String ktp = nz(r.getString("ktp"));
                    String ihs = nz(cek.tampilIDParktisi(ktp));
                    if (ihs.equals("")) {
                        // Signer tak ter-mapping (mis. NIK tak valid/kosong -> Practitioner lookup 400).
                        // DILEWATI: Provenance tanpa Practitioner valid ("Practitioner/") akan menggagalkan
                        // SELURUH Bundle transaction (atomik). Log agar master data NIK dokter bisa dibetulkan.
                        System.out.println("Notifikasi TTEPerakit tambahExpertise(" + jenis + ") LEWAT: signer IHS kosong"
                                + " (order=" + noorder + ", dokter=" + nama + ", nik=" + ktp + ")");
                        continue;
                    }
                    String display = jenis + " - order " + noorder;
                    String namaPelaksana = nz(r.getString("nama_pelaksana"));
                    String ktpPelaksana = nz(r.getString("ktp_pelaksana"));

                    // SATU BARIS PER PENANDA. Untuk expertise penunjang, aturan yang lazim adalah
                    // SERIAL: pelaksana (analis lab / radiografer, periksa_*.nip) menandatangani
                    // lebih dulu, lalu dokter penanggung jawab (periksa_*.kd_dokter) memverifikasi.
                    // Model & perannya dibaca dari satu_sehat_tte_model, jadi RS bisa mengubah
                    // urutan/peran tanpa menyentuh kode ini.
                    SatuSehatTteModel.Aturan aturan = SatuSehatTteModel.untuk(jenis);
                    if (aturan.slot.isEmpty()) {
                        // Belum dipetakan di satu_sehat_tte_model -> satu penanda saja: dokter
                        // penanggung jawab hasil. Baris ini WAJIB ada; tanpa cadangan ini seluruh
                        // Expertise hilang dari daftar, karena barisnya cuma dibuat di dalam loop slot.
                        SatuSehatBridgingTTE.DokumenTte d = new SatuSehatBridgingTTE.DokumenTte(
                                jenis, "DiagnosticReport/" + idDr, display);
                        d.signer = nama;
                        d.signerIhs = ihs;
                        d.peran = "author";
                        d.urutan = 1;
                        d.model = SatuSehatTteModel.Model.SINGLE;
                        h.dokumen.add(d);
                        continue;
                    }
                    for (SatuSehatTteModel.Slot slot : aturan.slot) {
                        boolean pakaiPelaksana = slot.sumber.equalsIgnoreCase("nip_pelaksana");
                        String namaSlot = pakaiPelaksana ? namaPelaksana : nama;
                        String ihsSlot = pakaiPelaksana ? nz(cek.tampilIDParktisi(ktpPelaksana)) : ihs;
                        if (ihsSlot.equals("")) {
                            if (slot.wajib) {
                                System.out.println("Notifikasi TTEPerakit tambahExpertise(" + jenis
                                        + ") LEWAT slot " + slot.urutan + " (" + slot.sumber
                                        + "): IHS kosong, order=" + noorder + " nama=" + namaSlot);
                            }
                            continue;   // penanda ini dilewati; penanda lain tetap bisa TTE
                        }
                        SatuSehatBridgingTTE.DokumenTte d = new SatuSehatBridgingTTE.DokumenTte(
                                jenis, "DiagnosticReport/" + idDr, display);
                        d.signer = namaSlot;
                        d.signerIhs = ihsSlot;
                        d.peran = slot.peran;
                        d.urutan = slot.urutan;
                        d.model = aturan.model;
                        h.dokumen.add(d);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi TTEPerakit tambahExpertise(" + jenis + ") : " + e);
        }
    }

    private String nz(String s) {
        return s == null ? "" : s;
    }
}

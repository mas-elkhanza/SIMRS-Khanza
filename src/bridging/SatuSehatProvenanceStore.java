/*
  by Ananda Widitomo,S.Kom.
  IT - SIMRS Hj. Fatimah Sulhan
 */
package bridging;

import fungsi.koneksiDB;
import fungsi.sekuel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Penyimpanan status TTE per dokumen (tabel satu_sehat_provenance) untuk idempotensi & refresh status.
 * Satu Task bulk -> banyak baris berbagi task_uuid yang sama (mirror Task.input[]).
 * DDL tabel: berkas satu_sehat_provenance.sql.
 */
public class SatuSehatProvenanceStore {

    private final Connection koneksi = koneksiDB.condb();
    private final sekuel Sequel = new sekuel();

    /**
     * Baris status TTE satu dokumen untuk SATU penanda tangan.
     *
     * Sejak TTE multi-signature (1 Agustus 2026) satu dokumen bisa punya beberapa baris: satu per
     * penanda. Kuncinya (no_rawat, target_ref, id_practitioner) — lihat satu_sehat_tte_multisign.sql.
     */
    public static class Baris {
        public String noRawat = "", jenis = "", targetRef = "";
        public String idProvenance = "", taskUuid = "", status = "belum", idPractitioner = "";
        /** Peran penanda (author/attester/verifier/...) dan giliran tanda tangan (serial: 1,2,...). */
        public String peran = "author";
        public int urutan = 1;
        /** Model serial: id Provenance pendahulu yang ditunjuk entity[]; "" bila tanda tangan pertama. */
        public String idProvenanceSebelumnya = "";
    }

    /**
     * Ambil SATU baris untuk (no_rawat,target_ref) — dipakai jalur lama yang masih menganggap satu
     * dokumen punya satu penanda. Bila dokumennya multi-penanda, yang dikembalikan adalah penanda
     * dengan urutan TERKECIL yang belum selesai (atau urutan terakhir bila semuanya selesai),
     * sehingga pemanggil lama tetap melihat "giliran yang sedang berjalan".
     *
     * Untuk tampilan per penanda pakai {@link #ambilSemua(String, String)}.
     */
    public Baris ambil(String noRawat, String targetRef) {
        List<Baris> semua = ambilSemua(noRawat, targetRef);
        if (semua.isEmpty()) {
            return null;
        }
        for (Baris b : semua) {
            if (!b.status.equals("completed")) {
                return b;
            }
        }
        return semua.get(semua.size() - 1);
    }

    /**
     * Baris milik SATU penanda pada sebuah dokumen — inilah bentuk yang benar sejak multi-signature:
     * status TTE melekat pada pasangan (dokumen, penanda), bukan pada dokumen saja.
     * null bila penanda itu belum pernah dibuatkan TTE.
     */
    public Baris ambil(String noRawat, String targetRef, String idPractitioner) {
        String ihs = nz(idPractitioner);
        for (Baris b : ambilSemua(noRawat, targetRef)) {
            if (b.idPractitioner.equals(ihs)) {
                return b;
            }
        }
        return null;
    }

    /**
     * Seluruh baris tanda tangan sebuah dokumen, urut giliran (urutan lalu peran).
     * Kosong bila dokumen belum pernah dibuatkan TTE.
     */
    public List<Baris> ambilSemua(String noRawat, String targetRef) {
        List<Baris> hasil = new ArrayList<>();
        try (PreparedStatement p = koneksi.prepareStatement(
                "select jenis_dokumen, id_provenance, task_uuid, status_tte, id_practitioner, "
                + "ifnull(peran,'author') peran, ifnull(urutan,1) urutan, "
                + "ifnull(id_provenance_sebelumnya,'') sebelumnya "
                + "from satu_sehat_provenance where no_rawat=? and target_ref=? "
                + "order by urutan, peran")) {
            p.setString(1, nz(noRawat));
            p.setString(2, nz(targetRef));
            try (ResultSet r = p.executeQuery()) {
                while (r.next()) {
                    Baris b = new Baris();
                    b.noRawat = nz(noRawat);
                    b.targetRef = nz(targetRef);
                    b.jenis = nz(r.getString("jenis_dokumen"));
                    b.idProvenance = nz(r.getString("id_provenance"));
                    b.taskUuid = nz(r.getString("task_uuid"));
                    b.status = nz(r.getString("status_tte"));
                    b.idPractitioner = nz(r.getString("id_practitioner"));
                    b.peran = nz(r.getString("peran"));
                    b.urutan = r.getInt("urutan");
                    b.idProvenanceSebelumnya = nz(r.getString("sebelumnya"));
                    hasil.add(b);
                }
            }
        } catch (Exception e) {
            // Kolom peran/urutan belum ada -> instalasi belum menjalankan satu_sehat_tte_multisign.sql.
            System.out.println("Notifikasi TTEStore ambilSemua : " + e);
        }
        return hasil;
    }

    /**
     * Baca [version_id, version_id_terkini] satu dokumen — best-effort (kolom mungkin belum ada di
     * instalasi lama; jalankan satu_sehat_provenance.sql). {"",""} bila gagal / baris tak ada.
     * Dipakai grid untuk menandai "Perlu TTE Ulang" saat versi ditandatangani != versi terkini.
     */
    public String[] ambilVersi(String noRawat, String targetRef) {
        String[] out = {"", ""};
        try (PreparedStatement p = koneksi.prepareStatement(
                "select ifnull(version_id,'') v, ifnull(version_id_terkini,'') vt "
                + "from satu_sehat_provenance where no_rawat=? and target_ref=? "
                // Tanpa ORDER BY, baris mana pun (penanda mana pun) bisa terambil.
                + "order by urutan desc, updated_at desc limit 1")) {
            p.setString(1, nz(noRawat));
            p.setString(2, nz(targetRef));
            try (ResultSet r = p.executeQuery()) {
                if (r.next()) {
                    out[0] = nz(r.getString("v"));
                    out[1] = nz(r.getString("vt"));
                }
            }
        } catch (Exception e) {
            // Kolom belum ada / error lain -> fitur badge nonaktif diam-diam (tak mengganggu load).
            System.out.println("Notifikasi TTEStore ambilVersi : " + e);
        }
        return out;
    }

    /**
     * Upsert baris penanda TUNGGAL (peran author, urutan 1) — bentuk lama, dipertahankan supaya
     * pemanggil model single tak perlu berubah.
     */
    public void simpan(String noRawat, String jenis, String targetRef, String idProvenance,
                       String taskUuid, String status, String idPractitioner) {
        simpan(noRawat, jenis, targetRef, idProvenance, taskUuid, status, idPractitioner,
                "author", 1, "");
    }

    /**
     * Upsert baris untuk SATU penanda tangan. Idempoten per (no_rawat, target_ref, id_practitioner)
     * — kunci itulah yang membuat tanda tangan kedua tidak lagi menimpa yang pertama
     * (sebelum satu_sehat_tte_multisign.sql, PK-nya hanya (no_rawat, target_ref) sehingga
     * `replace into` menghapus jejak penanda sebelumnya tanpa error).
     *
     * @param peran      provenance-participant-type: author/attester/verifier/performer/enterer.
     * @param urutan     giliran tanda tangan; model serial 1,2,...; model paralel semuanya 1.
     * @param provSebelumnya id Provenance pendahulu (model serial), "" bila tidak ada.
     */
    public void simpan(String noRawat, String jenis, String targetRef, String idProvenance,
                       String taskUuid, String status, String idPractitioner,
                       String peran, int urutan, String provSebelumnya) {
        // INSERT ... ON DUPLICATE KEY UPDATE, BUKAN `replace into`: replace menghapus baris lama
        // lalu menyisipkan yang baru, sehingga kolom yang tidak ikut disebut (trace_id, version_id,
        // version_id_terkini) HILANG jadi NULL. Itu tepat menghapus jejak audit pada kasus yang
        // paling membutuhkannya: retry berkas yang ditolak.
        Sequel.queryu2(
                "insert into satu_sehat_provenance (no_rawat, jenis_dokumen, target_ref, id_provenance, "
                + "task_uuid, status_tte, id_practitioner, peran, urutan, id_provenance_sebelumnya, "
                + "updated_at) values (?,?,?,?,?,?,?,?,?,?, now()) "
                + "on duplicate key update jenis_dokumen=values(jenis_dokumen), "
                + "id_provenance=values(id_provenance), task_uuid=values(task_uuid), "
                + "status_tte=values(status_tte), peran=values(peran), urutan=values(urutan), "
                + "id_provenance_sebelumnya=values(id_provenance_sebelumnya), updated_at=now()",
                10, new String[]{nz(noRawat), nz(jenis), nz(targetRef), nz(idProvenance),
                    nz(taskUuid), nz(status), nz(idPractitioner),
                    (peran == null || peran.equals("")) ? "author" : peran,
                    String.valueOf(urutan < 1 ? 1 : urutan), nz(provSebelumnya)});
    }

    /**
     * Perbarui status_tte SATU dokumen berdasarkan id_provenance — untuk hasil per-dokumen dari
     * Task.output[] (TTE bulk bisa punya outcome berbeda per Provenance: signed/failed/rejected).
     * Nilai {@code status} disimpan apa adanya (kode output SSM). Best-effort; tak melempar.
     */
    public void updateStatusByProvenance(String idProvenance, String status) {
        updateStatusByProvenance(idProvenance, status, "");
    }

    /**
     * Varian yang MENYARING per Task — wajib dipakai pada model paralel.
     *
     * Di model paralel satu Provenance dipakai bersama beberapa penanda (satu Task per penanda),
     * jadi {@code where id_provenance=?} saja akan menimpa status SEMUA penanda dengan hasil milik
     * salah satu orang. Dengan task_uuid ikut disaring, tiap baris hanya menerima hasil Task-nya.
     *
     * @param taskUuid ""/null -> perilaku lama (semua baris ber-id_provenance itu).
     */
    public void updateStatusByProvenance(String idProvenance, String status, String taskUuid) {
        if (idProvenance == null || idProvenance.trim().equals("")) {
            return;
        }
        if (taskUuid == null || taskUuid.trim().equals("")) {
            Sequel.queryu2(
                    "update satu_sehat_provenance set status_tte=?, updated_at=now() where id_provenance=?",
                    2, new String[]{nz(status), idProvenance.trim()});
            return;
        }
        Sequel.queryu2(
                "update satu_sehat_provenance set status_tte=?, updated_at=now() "
                + "where id_provenance=? and task_uuid=?",
                3, new String[]{nz(status), idProvenance.trim(), taskUuid.trim()});
    }

    /** Perbarui status seluruh baris satu Task (dipanggil saat polling/verifikasi). */
    public void updateStatusByTask(String taskUuid, String status) {
        if (taskUuid == null || taskUuid.trim().equals("")) {
            return;
        }
        Sequel.queryu2("update satu_sehat_provenance set status_tte=?, updated_at=now() where task_uuid=?",
                2, new String[]{nz(status), taskUuid});
    }

    /**
     * Baca status_tte satu Task. Sumbernya bisa polling SATUSEHAT ATAU webhook "taskSignatureDone"
     * (server RS mem-PUT status ke sini saat SSM completed/rejected). Dipakai dialog utk cek status
     * lebih dulu ke DB lokal (instan) sebelum fallback GET Task ke SATUSEHAT. "" bila baris tak ada.
     */
    public String statusByTask(String taskUuid) {
        if (taskUuid == null || taskUuid.trim().equals("")) {
            return "";
        }
        try (PreparedStatement p = koneksi.prepareStatement(
                "select status_tte from satu_sehat_provenance where task_uuid=? limit 1")) {
            p.setString(1, taskUuid.trim());
            try (ResultSet r = p.executeQuery()) {
                if (r.next()) {
                    return nz(r.getString(1));
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi TTEStore statusByTask : " + e);
        }
        return "";
    }

    /** Satu baris sinyal webhook yang belum ditindaklanjuti. */
    public static class Sinyal {
        public long id = 0L;
        public String event = "";
        public String taskUuid = "";
    }

    /**
     * Ambil sinyal webhook yang belum diproses (tabel satu_sehat_task_webhook, diisi penerima
     * webhook PHP). Sinyal diperlakukan sebagai PEMICU: isinya tidak dipercaya sebagai status
     * akhir — pemanggil tetap wajib GET Task ke SATUSEHAT untuk memastikan.
     * Tabel bisa saja belum dibuat (webhook belum dipasang) -> kembalikan daftar kosong, jangan
     * mengganggu jalannya dialog.
     *
     * HANYA SINYAL TTE (SatuSehatWebhookEvent.klausaTte): tabel ini dipakai bersama alur
     * Klaim BPJS-K dan pemroses menandai `diproses=1` setelah selesai, jadi tanpa penyaring
     * dialog ini ikut menelan sinyal klaim — pada data dev 27 Juli 2026 sudah terjadi atas
     * 10 baris (untung semuanya cuma gema kiriman sendiri, jadi belum ada yang hilang).
     *
     * Penyaringnya ke ISI payload, BUKAN kolom `event`: `event` hanya nama kanal, dan kanal
     * "ss_klaim" ternyata mengangkut notifikasi taskSignatureDone milik dialog ini juga
     * (7 dari 7 baris). Menyaring dengan nama kanal justru akan memutus TTE.
     * Yang membaca sisi klaim: satusehatklaim.SatuSehatClaimResponse.sinkronDariWebhook().
     */
    public java.util.List<Sinyal> ambilSinyalBelumDiproses(int maksimal) {
        java.util.List<Sinyal> hasil = new java.util.ArrayList<>();
        if (maksimal <= 0) {
            return hasil;
        }
        try (PreparedStatement p = koneksi.prepareStatement(
                "select id, ifnull(event,'') as event, ifnull(task_uuid,'') as task_uuid "
                + "from satu_sehat_task_webhook where diproses=0 "
                + "and " + SatuSehatWebhookEvent.klausaTte("payload") + " "
                + "order by id asc limit ?")) {
            p.setInt(1, maksimal);
            try (ResultSet r = p.executeQuery()) {
                while (r.next()) {
                    Sinyal s = new Sinyal();
                    s.id = r.getLong("id");
                    s.event = nz(r.getString("event"));
                    s.taskUuid = nz(r.getString("task_uuid")).trim();
                    hasil.add(s);
                }
            }
        } catch (Exception e) {
            // Umumnya: tabel belum ada karena webhook belum dipasang. Cukup dicatat sekali per putaran.
            System.out.println("Notifikasi TTEStore ambilSinyalBelumDiproses : " + e);
        }
        return hasil;
    }

    /**
     * Tandai sinyal sudah ditindaklanjuti. Dipanggil SETELAH GET Task selesai, supaya sinyal tidak
     * hilang bila proses gagal di tengah jalan (aman diulang pada putaran berikutnya).
     */
    public void tandaiSinyalDiproses(java.util.List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return;
        }
        StringBuilder tanya = new StringBuilder();
        for (int i = 0; i < ids.size(); i++) {
            tanya.append(i == 0 ? "?" : ",?");
        }
        try (PreparedStatement p = koneksi.prepareStatement(
                "update satu_sehat_task_webhook set diproses=1 where id in (" + tanya + ")")) {
            for (int i = 0; i < ids.size(); i++) {
                p.setLong(i + 1, ids.get(i));
            }
            p.executeUpdate();
        } catch (Exception e) {
            System.out.println("Notifikasi TTEStore tandaiSinyalDiproses : " + e);
        }
    }

    /**
     * Simpan trace-id (dari Task.meta.tag saat completed — "03. SSM") ke seluruh baris satu Task.
     * Best-effort: kolom trace_id mungkin belum ada di instalasi lama (jalankan satu_sehat_provenance.sql).
     */
    public void updateTraceByTask(String taskUuid, String traceId) {
        if (taskUuid == null || taskUuid.trim().equals("") || traceId == null || traceId.trim().equals("")) {
            return;
        }
        try (PreparedStatement p = koneksi.prepareStatement(
                "update satu_sehat_provenance set trace_id=?, updated_at=now() where task_uuid=?")) {
            p.setString(1, traceId.trim());
            p.setString(2, taskUuid.trim());
            p.executeUpdate();
        } catch (Exception e) {
            // Kolom trace_id belum ada / error lain -> abaikan (tak mengganggu polling).
            System.out.println("Notifikasi TTEStore updateTraceByTask : " + e);
        }
    }

    /**
     * Simpan versionId DOKUMEN yang ditandatangani (dari Provenance.target "…/_history/{versionId}")
     * ke baris (by id_provenance) — jejak audit "tanda tangan mengikat versi X". Best-effort: kolom
     * version_id mungkin belum ada di instalasi lama (jalankan satu_sehat_provenance.sql / ALTER).
     */
    public void updateVersionByProvenance(String idProvenance, String versionId) {
        if (idProvenance == null || idProvenance.trim().equals("")
                || versionId == null || versionId.trim().equals("")) {
            return;
        }
        try (PreparedStatement p = koneksi.prepareStatement(
                "update satu_sehat_provenance set version_id=?, updated_at=now() where id_provenance=?")) {
            p.setString(1, versionId.trim());
            p.setString(2, idProvenance.trim());
            p.executeUpdate();
        } catch (Exception e) {
            System.out.println("Notifikasi TTEStore updateVersionByProvenance : " + e);
        }
    }

    /**
     * Simpan version_id_terkini = versi DOKUMEN saat ini di server (jejak perubahan). BUKAN yang
     * ditandatangani. Status TTE (status_tte) TIDAK diubah — berkas tetap "Sudah TTE"; kolom ini
     * hanya mencatat bahwa dokumen sudah berpindah versi setelah ditandatangani. Best-effort.
     */
    public void updateVersionTerkiniByProvenance(String idProvenance, String versionTerkini) {
        if (idProvenance == null || idProvenance.trim().equals("")
                || versionTerkini == null || versionTerkini.trim().equals("")) {
            return;
        }
        try (PreparedStatement p = koneksi.prepareStatement(
                "update satu_sehat_provenance set version_id_terkini=?, updated_at=now() where id_provenance=?")) {
            p.setString(1, versionTerkini.trim());
            p.setString(2, idProvenance.trim());
            p.executeUpdate();
        } catch (Exception e) {
            System.out.println("Notifikasi TTEStore updateVersionTerkiniByProvenance : " + e);
        }
    }

    /**
     * Arsipkan satu kiriman TTE (payload + balasan) ke satu_sehat_provenance_bundle.
     *
     * Kiriman GAGAL pun disimpan — justru itu yang dibutuhkan saat melapor penolakan ke Kemenkes.
     * Best-effort penuh: kegagalan mengarsip TIDAK BOLEH menggagalkan pengiriman TTE yang sudah
     * berhasil, jadi seluruh exception ditelan dan hanya dicatat ke konsol.
     *
     * JSON ditulis sebagai byte UTF-8 ke kolom MEDIUMBLOB, bukan sebagai String ke kolom TEXT.
     * Koneksi JDBC aplikasi tidak menyetel characterEncoding sedangkan tabel-tabel SIMRS berkolasi
     * latin1; menulis JSON ber-UTF-8 sebagai String lewat jalur itu berisiko merusak karakter
     * seperti ± dan – persis seperti bug pengiriman yang pernah terjadi. Dengan byte mentah,
     * tidak ada konversi charset di sisi mana pun.
     *
     * @param jenisKiriman "bundle" (Provenance+Task atomik) atau "task" (POST Task saat reuse).
     */
    public void simpanBundle(String jenisKiriman, String noRawat, String taskUuid,
                             String idPractitioner, int jmlDokumen, SatuSehatKiriman k) {
        if (k == null || !k.adaIsi()) {
            return;
        }
        try (PreparedStatement p = koneksi.prepareStatement(
                "insert into satu_sehat_provenance_bundle "
                + "(dibuat_at, jenis_kiriman, no_rawat, task_uuid, id_practitioner, jml_dokumen, "
                + " http_status, berhasil, request_json, response_json, galat) "
                + "values (now(),?,?,?,?,?,?,?,?,?,?)")) {
            boolean berhasil = k.galat.equals("") && k.httpStatus >= 200 && k.httpStatus < 300;
            p.setString(1, (jenisKiriman == null || jenisKiriman.equals("")) ? "bundle" : jenisKiriman);
            p.setString(2, nz(noRawat));
            p.setString(3, nz(taskUuid));
            p.setString(4, nz(idPractitioner));
            p.setInt(5, jmlDokumen);
            p.setInt(6, k.httpStatus);
            p.setBoolean(7, berhasil);
            p.setBytes(8, k.requestJson.getBytes(java.nio.charset.StandardCharsets.UTF_8));
            p.setBytes(9, k.responseJson.getBytes(java.nio.charset.StandardCharsets.UTF_8));
            p.setString(10, k.galat);
            p.executeUpdate();
        } catch (Exception e) {
            // Tabel belum dibuat (jalankan satu_sehat_provenance_bundle.sql) atau error lain.
            System.out.println("Notifikasi TTEStore simpanBundle : " + e);
        }
    }

    private String nz(String s) {
        return s == null ? "" : s;
    }
}

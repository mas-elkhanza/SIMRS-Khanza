/*
  by Ananda Widitomo,S.Kom.
  IT - SIMRS Hj. Fatimah Sulhan
 */
package bridging;

/**
 * Logika murni (tanpa I/O/UI) untuk state TTE: pemetaan status Task+Provenance ke state UI,
 * pembentukan payload QR (Task UUID + timestamp), dan aturan kedaluwarsa (TTL 10 detik).
 * Dipisah agar bisa diuji unit tanpa jaringan/DB/Swing.
 * Referensi: Panduan Teknis TTE v1.1 — "TTE Status Resource (UI)" + Flow 2a (Render QR).
 */
public final class SatuSehatSignatureState {

    private SatuSehatSignatureState() { }

    /** TTL QR — operasional 10 detik agar QR cukup lama tampil untuk dipindai SSM.
     *  (Panduan Teknis v1.1 menyebut 3 detik saat bootcamp; diagram lama tulis "155".) */
    public static final long TTL_QR_DETIK = 10L;

    /** State dokumen di UI, memetakan kombinasi Task.status + ada/tidaknya Provenance.signature. */
    public enum Status {
        BUAT,     // Provenance & Task belum ada           -> tombol "Buat TTE"
        BELUM,    // Task requested, signature kosong        -> "Belum TTE", QR aktif
        DIPROSES, // Task in-progress, signature kosong      -> "Sedang Diproses", QR tetap aktif
        SUDAH,    // Task completed, signature terisi        -> "Sudah TTE", verifikasi
        DITOLAK   // Task rejected, signature kosong         -> retry
    }

    /**
     * Petakan status ke state UI.
     * @param taskUuid    id Task (kosong/null -> belum pernah dibuat -> BUAT).
     * @param taskStatus  Task.status dari server (requested/in-progress/completed/rejected).
     * @param adaSignature true bila Provenance.signature sudah terisi.
     */
    public static Status uiState(String taskUuid, String taskStatus, boolean adaSignature) {
        if (taskUuid == null || taskUuid.trim().equals("")) {
            return Status.BUAT;
        }
        String s = (taskStatus == null) ? "" : taskStatus.trim().toLowerCase();
        if (adaSignature || s.equals("completed")) {
            return Status.SUDAH;
        }
        if (s.equals("rejected")) {
            return Status.DITOLAK;
        }
        if (s.equals("in-progress")) {
            return Status.DIPROSES;
        }
        return Status.BELUM; // requested / kosong
    }

    /**
     * Petakan kode Task.output type (per dokumen) ke Status UI. Sumbernya
     * {@link SatuSehatTask#hasilPerProvenance} — SSM melaporkan hasil per Provenance:
     *   signed   -&gt; SUDAH.
     *   rejected -&gt; DITOLAK (DPJP menolak; bisa diulang lewat reuse Provenance).
     *   failed   -&gt; DITOLAK (gagal TTE; juga bisa diulang) — dibedakan hanya di log & pesan.
     * @return Status terpetakan; null bila kode tak dikenal (pemanggil fallback ke Task.status).
     */
    public static Status statusDariOutput(String outputCode) {
        if (outputCode == null) {
            return null;
        }
        switch (outputCode.trim().toLowerCase()) {
            case "signed":   return Status.SUDAH;
            case "rejected": return Status.DITOLAK;
            case "failed":   return Status.DITOLAK;
            default:         return null;
        }
    }

    /**
     * Petakan kode Task.output type ke nilai kolom {@code satu_sehat_provenance.status_tte}, yang
     * bertipe ENUM('belum','requested','in-progress','completed','rejected') — TIDAK memuat
     * 'signed'/'failed'. Menulis kode mentah membuat MySQL menolak (Data truncated) sehingga status
     * tak tersimpan. Pemetaan: signed-&gt;completed, rejected/failed-&gt;rejected.
     * @return nilai ENUM yang sah; "" bila kode tak dikenal (pemanggil lewati agar tak menimpa).
     */
    public static String statusDbDariOutput(String outputCode) {
        if (outputCode == null) {
            return "";
        }
        switch (outputCode.trim().toLowerCase()) {
            case "signed":   return "completed";
            case "rejected": return "rejected";
            case "failed":   return "rejected";
            default:         return "";
        }
    }

    /** Label Indonesia untuk state (dipakai badge di UI). */
    public static String label(Status st) {
        if (st == null) {
            return "";
        }
        switch (st) {
            case BUAT:     return "Buat TTE";
            case BELUM:    return "Belum TTE";
            case DIPROSES: return "Sedang Diproses";
            case SUDAH:    return "Sudah TTE";
            case DITOLAK:  return "Ditolak";
            default:       return "";
        }
    }

    /** QR masih perlu di-refresh (aktif) selama status BELUM atau DIPROSES. */
    public static boolean qrAktif(Status st) {
        return st == Status.BELUM || st == Status.DIPROSES;
    }

    /**
     * Payload QR = deep-link applink SATUSEHAT "{basis}/{taskUuid}/{epochDetik}".
     * FORMAT RESMI Kemenkes (22 Juli 2026), menggantikan format lama "Task/{uuid}/{ts}":
     *   staging  https://applink-stg.dto.kemkes.go.id/ssm/tte/{taskuuid}/{timestamp}
     *   produksi https://applink.kemkes.go.id/ssm/tte/{taskuuid}/{timestamp}
     * Contoh: https://applink-stg.dto.kemkes.go.id/ssm/tte/70e053ad-4df0-4703-a5d2-dfbd8554af4d/1784139457
     * {taskUuid} dipakai SSM untuk GET Task, {epochDetik} (Unix detik) sebagai timestamp/anti-replay
     * (TTL 10 dtk). Basis diambil pemanggil dari koneksiDB#URLAPPLINKSATUSEHAT agar kelas ini tetap
     * murni (tanpa I/O) dan lingkungan QR selalu mengikuti lingkungan FHIR yang aktif.
     *
     * @param basisApplink basis deep-link tanpa trailing slash; "" bila belum terkonfigurasi.
     * @return payload siap render; "" bila basisApplink kosong — pemanggil WAJIB memeriksa dan
     *         tidak menerbitkan QR, sebab QR tanpa basis akan menunjuk lingkungan yang salah.
     */
    public static String qrPayload(String basisApplink, String taskUuid, long epochDetik) {
        String basis = (basisApplink == null) ? "" : basisApplink.trim();
        while (basis.endsWith("/")) {
            basis = basis.substring(0, basis.length() - 1);
        }
        if (basis.equals("")) {
            return "";
        }
        String uuid = (taskUuid == null) ? "" : taskUuid.trim();
        return basis + "/" + uuid + "/" + epochDetik;
    }

    /** true bila QR yang diterbitkan pada tsDetik sudah kedaluwarsa relatif ke nowDetik (TTL 10 dtk). */
    public static boolean qrKedaluwarsa(long tsDetik, long nowDetik) {
        return (nowDetik - tsDetik) >= TTL_QR_DETIK;
    }
}

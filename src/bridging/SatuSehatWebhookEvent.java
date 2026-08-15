/*
  by Ananda Widitomo,S.Kom.
 */
package bridging;

/**
 * Pemilah sinyal webhook SATUSEHAT pada tabel `satu_sehat_task_webhook`.
 *
 * SATU tabel dipakai bersama DUA subsistem yang tak saling kenal:
 *   - TTE   (satusehattte.DlgTTESatuSehat)          -> notifikasi Task tanda tangan
 *   - Klaim (satusehatklaim.SatuSehatClaimResponse) -> alur Klaim BPJS-K langkah 8 &amp; 10
 * Keduanya menandai baris `diproses=1` setelah menindaklanjuti, jadi tanpa pemilah
 * siapa pun yang jalan duluan MENELAN sinyal milik yang lain. Pada data dev 27 Juli 2026
 * dialog TTE sudah menelan 10 sinyal kanal klaim; kebetulan semuanya cuma gema kiriman
 * kita sendiri sehingga belum ada kerugian nyata — tapi ClaimResponse asli akan bernasib
 * sama begitu BPJS-K mulai mengirimnya.
 *
 * PEMBEDANYA BUKAN KOLOM `event`. Diperiksa langsung ke payload dev 27 Juli 2026:
 *
 *   {"method":"ss_klaim","data":{"meta":{"method":"taskSignatureDone"},...}}   <- punya TTE
 *   {"method":"ssklaim","data":{"meta":{"method":"coverageEligibilityResponseSubmission"},...}}
 *
 * `method` terluar — yang disimpan penerima PHP ke kolom `event` — adalah nama KANAL
 * ("ss_klaim"/"ssklaim"), dan kanal itu MENGANGKUT notifikasi TTE juga: 7 dari 7 baris
 * ber-event `ss_klaim` isinya resourceType Task/taskSignatureDone. Menyaring dengan kolom
 * `event` karena itu justru memutus TTE. Yang menentukan operasi adalah `data.meta.method`.
 *
 * Kelasnya diletakkan di `bridging` (bukan di salah satu subsistem) supaya aturannya hanya
 * ada SATU salinan: satusehatklaim dan satusehattte sama-sama boleh mengimpor bridging,
 * tapi tidak boleh saling impor.
 */
public final class SatuSehatWebhookEvent {

    private SatuSehatWebhookEvent() {
    }

    /** Operasi notifikasi TTE pada `data.meta.method`. */
    public static final String METODE_TTE = "taskSignatureDone";

    /**
     * Potongan SQL pemilih baris milik TTE.
     *
     * Dicocokkan ke ISI payload, bukan kolom `event`, karena kolom itu cuma nama kanal.
     * LIKE berawalan wildcard memang tak bisa pakai indeks, tapi penyaring `diproses=0`
     * sudah lebih dulu memangkas ke antrean yang belum ditindaklanjuti — tabel ini antrean
     * sinyal, bukan tabel transaksi.
     */
    public static String klausaTte(String kolomPayload) {
        return kolomPayload + " like '" + polaTte() + "'";
    }

    /**
     * Kebalikannya: segala yang BUKAN notifikasi TTE dianggap milik alur klaim.
     *
     * Sengaja "sisanya", bukan daftar putih: kanal klaim juga memantulkan kiriman kita
     * sendiri (Coverage/CoverageEligibilityResponse/ChargeItem) dengan nama operasi yang
     * bisa bertambah kapan saja. Pembaca klaim yang menyerap sisa ini — kalau tidak, baris
     * gema akan menumpuk selamanya sebagai `diproses=0`.
     */
    public static String klausaBukanTte(String kolomPayload) {
        return "(" + kolomPayload + " is null or " + kolomPayload + " not like '" + polaTte() + "')";
    }

    /**
     * Pemeriksaan yang sama di sisi Java — dipakai bila payload sudah di tangan
     * (mis. sesudah diambil) supaya tak bergantung pada penyaring SQL saja.
     */
    public static boolean milikTte(String payload) {
        return payload != null && payload.contains("\"" + METODE_TTE + "\"");
    }

    /** Pola LIKE: nama operasi dicari BESERTA kutipnya supaya tak tersangkut teks bebas. */
    private static String polaTte() {
        return "%\"" + METODE_TTE + "\"%";
    }
}

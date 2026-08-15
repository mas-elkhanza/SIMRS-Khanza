/*
  by Ananda Widitomo,S.Kom.
  IT - SIMRS Hj. Fatimah Sulhan
 */
package bridging;

/**
 * Rekaman kiriman HTTP terakhir sebuah sender TTE: payload persis yang dikirim, balasan server,
 * dan status HTTP-nya. Dipakai untuk mengarsipkan bukti ke tabel satu_sehat_provenance_bundle.
 *
 * Dipakai bersama oleh {@link SatuSehatBundleProvenance} (jalur Bundle transaction) dan
 * {@link SatuSehatTask} (jalur POST Task saat reuse Provenance), supaya keduanya menghasilkan
 * bentuk arsip yang sama.
 *
 * Yang direkam adalah payload MENTAH apa adanya, bukan versi yang sudah dirapikan — arsip ini
 * berfungsi sebagai bukti, jadi harus identik dengan yang melintas di kabel. Perapian dilakukan
 * saat menampilkan (konsol & dashboard), bukan saat menyimpan.
 */
public class SatuSehatKiriman {

    /** Payload persis yang dikirim ke SATUSEHAT. */
    public String requestJson = "";
    /** Balasan server; saat ditolak, berisi OperationOutcome. */
    public String responseJson = "";
    /** Status HTTP balasan. 0 berarti gagal sebelum sempat menerima balasan (mis. DNS/timeout). */
    public int httpStatus = 0;
    /** Ringkasan kegagalan; kosong bila sukses. */
    public String galat = "";

    /** Tandai awal satu pengiriman; menghapus jejak pengiriman sebelumnya. */
    public void mulai(String payload) {
        requestJson = (payload == null) ? "" : payload;
        responseJson = "";
        httpStatus = 0;
        galat = "";
    }

    public void sukses(int status, String body) {
        httpStatus = status;
        responseJson = (body == null) ? "" : body;
        galat = "";
    }

    public void gagal(int status, String body, String pesan) {
        httpStatus = status;
        responseJson = (body == null) ? "" : body;
        galat = (pesan == null) ? "" : (pesan.length() > 255 ? pesan.substring(0, 255) : pesan);
    }

    /** true bila ada payload yang layak diarsipkan. */
    public boolean adaIsi() {
        return requestJson != null && !requestJson.equals("");
    }
}

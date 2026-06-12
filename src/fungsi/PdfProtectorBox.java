package fungsi;

import java.io.File;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy;

public class PdfProtectorBox {

    /**
     * Enkripsi PDF menggunakan PDFBox (AES-128 atau 256).
     * @param src    file sumber (PDF plain)
     * @param dst    file output terenkripsi
     * @param userPw password untuk membuka PDF (yang dibagikan ke pasien)
     * @param ownerPw password pemilik (bebas, untuk RS)
     * @param bits   128 atau 256 (pilih 128 kalau ingin kompat lebih luas)
     * @throws Exception bila gagal
     */
    public static void encrypt(File src, File dst, String userPw, String ownerPw, int bits) throws Exception {
        try (PDDocument doc = PDDocument.load(src)) {
            // Set hak akses (boleh disesuaikan)
            AccessPermission ap = new AccessPermission();
            ap.setCanPrint(true);           // boleh print
            ap.setCanModify(false);         // tidak boleh edit
            ap.setCanExtractContent(false); // blok copy teks

            // Protection policy
            StandardProtectionPolicy spp = new StandardProtectionPolicy(ownerPw, userPw, ap);
            spp.setEncryptionKeyLength((bits == 256) ? 256 : 128); // default 128

            doc.protect(spp);
            doc.save(dst);
        }
    }
}
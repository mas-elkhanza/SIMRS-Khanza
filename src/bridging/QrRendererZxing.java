/*
  by Ananda Widitomo,S.Kom.
  IT - SIMRS Hj. Fatimah Sulhan
 */
package bridging;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

/**
 * Perender QR asli berbasis ZXing (core + javase di ~/lib). Dipilih otomatis oleh
 * {@link QrRendererFactory} bila kelas ini + jar ZXing tersedia; jika tidak, dipakai
 * {@link QrRendererFallback}. Menghasilkan QR yang dapat dipindai SATUSEHAT Mobile.
 */
public class QrRendererZxing implements QrRenderer {

    @Override
    public BufferedImage render(String konten, int ukuran) throws Exception {
        if (ukuran < 120) {
            ukuran = 120;
        }
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.MARGIN, 1);
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        // ERROR_CORRECTION = H (pemulihan ~30%) — dinaikkan dari M karena tengah QR ditimpa logo
        // Kemenkes; tanpa H, area yang tertutup logo bisa membuat QR gagal dipindai SATUSEHAT Mobile.
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        BitMatrix matrix = new QRCodeWriter().encode(
                konten == null ? "" : konten, BarcodeFormat.QR_CODE, ukuran, ukuran, hints);
        // MatrixToImageWriter mengembalikan BYTE_BINARY (1-bit hitam/putih); menggambar logo berwarna
        // di atasnya membuat warna terkuantisasi ke hitam/putih (logo jadi pudar/hilang). Salin ke
        // kanvas INT_RGB dulu agar logo Kemenkes tetap BERWARNA.
        BufferedImage bw = MatrixToImageWriter.toBufferedImage(matrix);
        BufferedImage qr = new BufferedImage(bw.getWidth(), bw.getHeight(), BufferedImage.TYPE_INT_RGB);
        java.awt.Graphics2D gq = qr.createGraphics();
        gq.drawImage(bw, 0, 0, null);
        gq.dispose();
        tempelLogoKemenkes(qr);   // best-effort; QR tetap valid walau logo gagal dimuat
        return qr;
    }

    /**
     * Tempel logo Kemenkes KECIL & BERWARNA di tengah QR, di atas kotak putih membulat agar kontras
     * & tetap terpindai. Aset: classpath {@code /48x48/kemenkes.png} (logo Kemenkes asli, PNG transparan).
     * Logo di-crop ke isinya dulu (aset punya bingkai transparan) lalu digambar ~18% lebar QR. Dengan
     * ERROR_CORRECTION=H area sekecil itu jauh di dalam batas pulih.
     *
     * Best-effort penuh: kegagalan memuat/menggambar logo TIDAK boleh menggagalkan QR (penandatanganan
     * lebih penting daripada logo) — cukup dicatat lalu QR polos dikembalikan.
     */
    private void tempelLogoKemenkes(BufferedImage qr) {
        try {
            java.io.InputStream in = QrRendererZxing.class.getResourceAsStream("/48x48/kemenkes.png");
            if (in == null) {
                return;
            }
            BufferedImage sumber;
            try {
                sumber = javax.imageio.ImageIO.read(in);
            } finally {
                in.close();
            }
            if (sumber == null || sumber.getWidth() <= 0) {
                return;
            }
            BufferedImage logo = cropKonten(sumber);                             // buang bingkai transparan
            int w = qr.getWidth();
            int h = qr.getHeight();
            int logoW = Math.round(w * 0.18f);                                   // kecil, tapi cukup utk logo asli
            int logoH = Math.round(logoW * (logo.getHeight() / (float) logo.getWidth()));
            int pad = Math.max(2, Math.round(logoW * 0.14f));
            int boxW = logoW + pad * 2;
            int boxH = logoH + pad * 2;
            int lx = (w - logoW) / 2;
            int ly = (h - logoH) / 2;
            int bx = (w - boxW) / 2;
            int by = (h - boxH) / 2;

            java.awt.Graphics2D g = qr.createGraphics();
            g.setRenderingHint(java.awt.RenderingHints.KEY_INTERPOLATION,
                    java.awt.RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                    java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
            g.setColor(java.awt.Color.WHITE);
            g.fillRoundRect(bx, by, boxW, boxH, pad * 2, pad * 2);               // alas putih
            g.drawImage(logo, lx, ly, logoW, logoH, null);                       // logo berwarna di tengah
            g.dispose();
        } catch (Exception e) {
            // Logo gagal -> biarkan QR polos, jangan ganggu alur TTE.
            System.out.println("Notifikasi QrRendererZxing tempelLogoKemenkes : " + e);
        }
    }

    /** Potong gambar ke kotak-batas piksel non-transparan (alpha &gt; 20); kembalikan apa adanya bila kosong. */
    private BufferedImage cropKonten(BufferedImage img) {
        int minX = img.getWidth(), minY = img.getHeight(), maxX = -1, maxY = -1;
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                if ((img.getRGB(x, y) >>> 24) > 20) {
                    if (x < minX) { minX = x; }
                    if (x > maxX) { maxX = x; }
                    if (y < minY) { minY = y; }
                    if (y > maxY) { maxY = y; }
                }
            }
        }
        if (maxX < minX || maxY < minY) {
            return img; // tak ada piksel non-transparan
        }
        return img.getSubimage(minX, minY, maxX - minX + 1, maxY - minY + 1);
    }

    @Override
    public boolean aktif() {
        return true;
    }
}

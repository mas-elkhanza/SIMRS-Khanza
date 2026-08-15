/*
  by Ananda Widitomo,S.Kom.
  IT - SIMRS Hj. Fatimah Sulhan
 */
package bridging;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

/**
 * Perender QR sementara TANPA dependency (dipakai bila ZXing belum terpasang di ~/lib).
 * Menggambar kotak placeholder + teks agar dialog tetap fungsional & jelas bahwa QR belum aktif.
 * Ganti otomatis oleh QrRendererZxing saat library QR tersedia (lihat {@link QrRendererFactory}).
 */
public class QrRendererFallback implements QrRenderer {

    @Override
    public BufferedImage render(String konten, int ukuran) {
        if (ukuran < 120) {
            ukuran = 120;
        }
        BufferedImage img = new BufferedImage(ukuran, ukuran, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = img.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, ukuran, ukuran);
        g.setColor(new Color(180, 180, 180));
        g.drawRect(4, 4, ukuran - 9, ukuran - 9);
        g.setColor(new Color(120, 120, 120));
        g.setFont(new Font("SansSerif", Font.PLAIN, 12));
        g.drawString("QR belum aktif", 16, ukuran / 2 - 8);
        g.drawString("pasang ZXing", 16, ukuran / 2 + 10);
        g.dispose();
        return img;
    }

    @Override
    public boolean aktif() {
        return false;
    }
}

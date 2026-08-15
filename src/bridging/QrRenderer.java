/*
  by Ananda Widitomo,S.Kom.
  IT - SIMRS Hj. Fatimah Sulhan
 */
package bridging;

import java.awt.image.BufferedImage;

/**
 * Abstraksi perender QR agar {@link DlgTTESatuSehat} tidak bergantung langsung ke library QR
 * tertentu. Implementasi sesungguhnya (ZXing) di-drop-in belakangan sebagai QrRendererZxing;
 * bila belum terpasang, dipakai {@link QrRendererFallback}. Lihat {@link QrRendererFactory}.
 */
public interface QrRenderer {

    /** Render konten menjadi gambar QR persegi berukuran {@code ukuran} px. */
    BufferedImage render(String konten, int ukuran) throws Exception;

    /** true bila ini perender QR sungguhan (scannable); false untuk placeholder fallback. */
    boolean aktif();
}

/*
 * Kanvas Penandaan Lokasi Luka (RM.29.1) - SIMRS Khanza RS Asri Medika.
 *
 * Delapan gambar (laki-laki / perempuan, kepala depan-belakang, kepala samping, telapak & punggung
 * tangan, telapak & punggung kaki) disusun 2 kolom x 4 baris seperti formulir kertas. Lokasi luka
 * ditandai dengan menggambar langsung di atas gambar memakai mouse, pena (stylus) atau layar sentuh
 * (pada Windows sentuhan satu jari diteruskan sebagai kejadian mouse), seperti memakai spidol merah.
 *
 * Gambar sumber : /picture/lokasi_luka_*.png (letakkan di folder picture proyek, satu paket dengan ikon lain).
 * Goresan disimpan sebagai rasio terhadap gambar (0..1) sehingga tidak tergantung ukuran layar/cetak.
 * Format teks satu gambar :  RRGGBB,tebal:x,y;x,y;...|RRGGBB,tebal:x,y;...   (goresan dipisah '|')
 */
package rekammedis;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

public class PanelGambarTubuh extends javax.swing.JPanel {
    public static final String[] KODE = {"TUBUH_PRIA", "TUBUH_WANITA", "KEPALA_DEPAN_BELAKANG", "KEPALA_SAMPING",
        "TELAPAK_TANGAN", "PUNGGUNG_TANGAN", "TELAPAK_KAKI", "PUNGGUNG_KAKI"};
    public static final String[] NAMA = {"Tubuh Laki-laki", "Tubuh Perempuan", "Kepala Depan/Belakang", "Kepala Samping",
        "Telapak Tangan", "Punggung Tangan", "Telapak Kaki", "Punggung Kaki"};
    private static final String[] FILE = {"lokasi_luka_tubuh_pria.png", "lokasi_luka_tubuh_wanita.png",
        "lokasi_luka_kepala_depan_belakang.png", "lokasi_luka_kepala_samping.png", "lokasi_luka_telapak_tangan.png",
        "lokasi_luka_punggung_tangan.png", "lokasi_luka_telapak_kaki.png", "lokasi_luka_punggung_kaki.png"};
    /** judul kolom kiri/kanan pada tiap baris (seperti kertas) */
    private static final int GAP = 6;

    public static class Goresan {
        public Color warna = Color.RED;
        public float tebal = 4f;            // dalam piksel pada lebar sel acuan 380 px
        public List<double[]> titik = new ArrayList<>();
    }

    private static final double LEBAR_ACUAN = 380.0;

    @SuppressWarnings("unchecked")
    private final List<Goresan>[] goresan = new List[8];
    private final BufferedImage[] gambar = new BufferedImage[8];
    private final List<Integer> riwayat = new ArrayList<>();   // untuk undo : indeks gambar tiap goresan
    private Goresan aktif = null;
    private int selAktif = -1;
    private Color warna = Color.RED;
    private float tebal = 4f;
    private boolean mode_hapus = false;
    private boolean bisaEdit = true;

    public PanelGambarTubuh() {
        setBackground(Color.WHITE);
        for (int i = 0; i < 8; i++) {
            goresan[i] = new ArrayList<>();
            gambar[i] = muat(FILE[i]);
        }
        setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        MouseAdapter ma = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (!bisaEdit || e.getButton() != MouseEvent.BUTTON1) {
                    return;
                }
                int sel = selDi(e.getX(), e.getY());
                if (sel < 0) {
                    return;
                }
                if (mode_hapus) {
                    hapusDi(sel, e.getX(), e.getY());
                    return;
                }
                selAktif = sel;
                aktif = new Goresan();
                aktif.warna = warna;
                aktif.tebal = tebal;
                tambahTitik(aktif, sel, e.getX(), e.getY());
                goresan[sel].add(aktif);
                riwayat.add(sel);
                repaint();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (!bisaEdit) {
                    return;
                }
                if (mode_hapus) {
                    int sel = selDi(e.getX(), e.getY());
                    if (sel >= 0) {
                        hapusDi(sel, e.getX(), e.getY());
                    }
                    return;
                }
                if (aktif != null && selAktif >= 0) {
                    tambahTitik(aktif, selAktif, e.getX(), e.getY());
                    repaint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                aktif = null;
                selAktif = -1;
            }
        };
        addMouseListener(ma);
        addMouseMotionListener(ma);
    }

    private static BufferedImage muat(String f) {
        try {
            java.net.URL u = PanelGambarTubuh.class.getResource("/picture/" + f);
            return u == null ? null : ImageIO.read(u);
        } catch (Exception e) {
            return null;
        }
    }

    // ------------------------------------------------------------------ tata letak
    private static double rasio(int i) {
        int baris = i / 2;
        double r = 0;
        for (int k = baris * 2; k < baris * 2 + 2; k++) {
            r = Math.max(r, RASIO[k]);
        }
        return r;
    }
    private static final double[] RASIO = {570 / 723.0, 570 / 700.0, 335 / 723.0, 335 / 700.0, 378 / 723.0, 378 / 700.0, 398 / 723.0, 398 / 700.0};

    /** persegi sel ke-i pada lebar panel w (gambar digambar mengisi lebar sel, rata atas) */
    private Rectangle sel(int i, int w) {
        int colW = (w - GAP) / 2;
        int y = 0;
        for (int b = 0; b < i / 2; b++) {
            y += (int) Math.round(colW * rasio(b * 2)) + GAP;
        }
        int x = (i % 2) * (colW + GAP);
        return new Rectangle(x, y, colW, (int) Math.round(colW * RASIO[i]));
    }

    private int tinggiTotal(int w) {
        Rectangle r = sel(6, w);
        return (int) Math.round(r.y + (w - GAP) / 2 * rasio(6)) + 2;
    }

    @Override
    public Dimension getPreferredSize() {
        int w = getParent() != null && getParent().getWidth() > 200 ? getParent().getWidth() : 760;
        return new Dimension(w, tinggiTotal(w));
    }

    private int selDi(int x, int y) {
        for (int i = 0; i < 8; i++) {
            if (sel(i, getWidth()).contains(x, y)) {
                return i;
            }
        }
        return -1;
    }

    private void tambahTitik(Goresan g, int i, int x, int y) {
        Rectangle r = sel(i, getWidth());
        double rx = Math.min(1.0, Math.max(0.0, (x - r.x) / (double) r.width));
        double ry = Math.min(1.0, Math.max(0.0, (y - r.y) / (double) r.height));
        if (!g.titik.isEmpty()) {
            double[] l = g.titik.get(g.titik.size() - 1);
            if (Math.abs(l[0] - rx) < 0.0015 && Math.abs(l[1] - ry) < 0.0015) {
                return;
            }
        }
        g.titik.add(new double[]{rx, ry});
    }

    private void hapusDi(int i, int x, int y) {
        Rectangle r = sel(i, getWidth());
        double rx = (x - r.x) / (double) r.width;
        double ry = (y - r.y) / (double) r.height;
        double batas = 10.0 / r.width;
        for (int k = goresan[i].size() - 1; k >= 0; k--) {
            Goresan g = goresan[i].get(k);
            if (dekat(g, rx, ry, batas, r.height / (double) r.width)) {
                goresan[i].remove(k);
                for (int h = riwayat.size() - 1; h >= 0; h--) {
                    if (riwayat.get(h) == i) {
                        riwayat.remove(h);
                        break;
                    }
                }
                repaint();
                return;
            }
        }
    }

    private static boolean dekat(Goresan g, double rx, double ry, double batas, double aspek) {
        for (int p = 0; p < g.titik.size(); p++) {
            double[] a = g.titik.get(p);
            double[] b = g.titik.get(Math.min(p + 1, g.titik.size() - 1));
            double dx = b[0] - a[0], dy = (b[1] - a[1]) * aspek;
            double px = rx - a[0], py = (ry - a[1]) * aspek;
            double len2 = dx * dx + dy * dy;
            double t = len2 == 0 ? 0 : Math.max(0, Math.min(1, (px * dx + py * dy) / len2));
            double ex = px - t * dx, ey = py - t * dy;
            if (Math.sqrt(ex * ex + ey * ey) <= batas) {
                return true;
            }
        }
        return false;
    }

    // ------------------------------------------------------------------ gambar
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        gambarSemua(g2, getWidth(), true);
        g2.dispose();
    }

    private void gambarSemua(Graphics2D g2, int w, boolean garisBantu) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        for (int i = 0; i < 8; i++) {
            Rectangle r = sel(i, w);
            g2.setColor(Color.WHITE);
            g2.fillRect(r.x, r.y, r.width, r.height);
            if (gambar[i] != null) {
                g2.drawImage(gambar[i], r.x, r.y, r.width, r.height, null);
            } else {
                g2.setColor(Color.GRAY);
                g2.setFont(new Font("SansSerif", Font.PLAIN, 11));
                g2.drawString("[gambar " + FILE[i] + " tidak ditemukan di /picture]", r.x + 6, r.y + 16);
            }
            g2.setColor(Color.BLACK);
            g2.setStroke(new BasicStroke(1f));
            g2.drawRect(r.x, r.y, r.width - 1, r.height - 1);
            for (Goresan s : goresan[i]) {
                gambarGoresan(g2, s, r);
            }
            // nama gambar di pojok kanan-bawah sel (pojok atas sudah berisi tulisan Kanan/Kiri pada gambar)
            g2.setFont(new Font("SansSerif", Font.BOLD, 10));
            FontMetrics fm = g2.getFontMetrics();
            g2.setColor(new Color(110, 110, 110));
            g2.drawString(NAMA[i], r.x + r.width - fm.stringWidth(NAMA[i]) - 5, r.y + r.height - 5);
        }
    }

    private static void gambarGoresan(Graphics2D g2, Goresan s, Rectangle r) {
        if (s.titik.isEmpty()) {
            return;
        }
        float lebar = (float) (s.tebal * r.width / LEBAR_ACUAN);
        g2.setColor(s.warna);
        g2.setStroke(new BasicStroke(Math.max(1.2f, lebar), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        if (s.titik.size() == 1) {
            double[] p = s.titik.get(0);
            double d = Math.max(1.2f, lebar);
            g2.fill(new java.awt.geom.Ellipse2D.Double(r.x + p[0] * r.width - d / 2, r.y + p[1] * r.height - d / 2, d, d));
            return;
        }
        Path2D.Double path = new Path2D.Double();
        for (int k = 0; k < s.titik.size(); k++) {
            double[] p = s.titik.get(k);
            double px = r.x + p[0] * r.width, py = r.y + p[1] * r.height;
            if (k == 0) {
                path.moveTo(px, py);
            } else {
                path.lineTo(px, py);
            }
        }
        g2.draw(path);
    }

    /** seluruh lembar (2 x 4 gambar) berikut goresan, untuk parameter report. */
    public BufferedImage renderKomposit(int lebar) {
        int h = tinggiTotal(lebar);
        BufferedImage img = new BufferedImage(lebar, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = img.createGraphics();
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, lebar, h);
        gambarSemua(g2, lebar, false);
        g2.dispose();
        return img;
    }

    /** satu gambar saja berikut goresannya. */
    public BufferedImage renderSatu(int idx, int lebar) {
        int h = (int) Math.round(lebar * RASIO[idx]);
        BufferedImage img = new BufferedImage(lebar, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = img.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, lebar, h);
        if (gambar[idx] != null) {
            g2.drawImage(gambar[idx], 0, 0, lebar, h, null);
        }
        Rectangle r = new Rectangle(0, 0, lebar, h);
        for (Goresan s : goresan[idx]) {
            gambarGoresan(g2, s, r);
        }
        g2.dispose();
        return img;
    }

    // ------------------------------------------------------------------ kendali
    public void setWarna(Color c) {
        warna = c;
        mode_hapus = false;
    }

    /** ketebalan spidol : 1 = halus, 2 = sedang, 3 = tebal */
    public void setTebalTingkat(int t) {
        tebal = t <= 1 ? 2.5f : (t == 2 ? 4.5f : 8f);
    }

    public void setModeHapus(boolean b) {
        mode_hapus = b;
        setCursor(Cursor.getPredefinedCursor(b ? Cursor.HAND_CURSOR : Cursor.CROSSHAIR_CURSOR));
    }

    public void setBisaEdit(boolean b) {
        bisaEdit = b;
    }

    public void undo() {
        if (riwayat.isEmpty()) {
            return;
        }
        int i = riwayat.remove(riwayat.size() - 1);
        if (!goresan[i].isEmpty()) {
            goresan[i].remove(goresan[i].size() - 1);
        }
        repaint();
    }

    public void hapusSemua() {
        for (int i = 0; i < 8; i++) {
            goresan[i].clear();
        }
        riwayat.clear();
        repaint();
    }

    public boolean isKosong() {
        for (int i = 0; i < 8; i++) {
            if (!goresan[i].isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public boolean adaGoresan(int idx) {
        return !goresan[idx].isEmpty();
    }

    /** ringkasan nama gambar yang diberi tanda, mis. "Tubuh Laki-laki, Kepala Samping" */
    public String getRingkasan() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            if (!goresan[i].isEmpty()) {
                sb.append(sb.length() > 0 ? ", " : "").append(NAMA[i]).append(" (").append(goresan[i].size()).append(")");
            }
        }
        return sb.toString();
    }

    // ------------------------------------------------------------------ simpan / muat
    public String getGoresan(int idx) {
        StringBuilder sb = new StringBuilder();
        for (Goresan s : goresan[idx]) {
            if (s.titik.isEmpty()) {
                continue;
            }
            if (sb.length() > 0) {
                sb.append('|');
            }
            sb.append(String.format("%02X%02X%02X", s.warna.getRed(), s.warna.getGreen(), s.warna.getBlue()))
                    .append(',').append(Math.round(s.tebal * 10) / 10.0).append(':');
            for (int k = 0; k < s.titik.size(); k++) {
                double[] p = s.titik.get(k);
                sb.append(k > 0 ? ";" : "").append(f5(p[0])).append(',').append(f5(p[1]));
            }
        }
        return sb.toString();
    }

    private static String f5(double v) {
        String s = String.format(java.util.Locale.US, "%.4f", v);
        return s.replaceAll("0+$", "").replaceAll("\\.$", "");
    }

    public void setGoresan(int idx, String teks) {
        goresan[idx].clear();
        if (teks == null || teks.trim().isEmpty()) {
            return;
        }
        for (String st : teks.split("\\|")) {
            try {
                int c = st.indexOf(':');
                String[] kepala = st.substring(0, c).split(",");
                Goresan g = new Goresan();
                g.warna = new Color(Integer.parseInt(kepala[0], 16));
                g.tebal = Float.parseFloat(kepala[1]);
                for (String p : st.substring(c + 1).split(";")) {
                    String[] xy = p.split(",");
                    g.titik.add(new double[]{Double.parseDouble(xy[0]), Double.parseDouble(xy[1])});
                }
                goresan[idx].add(g);
            } catch (Exception e) {
                // goresan rusak dilewati
            }
        }
    }

    /** kosongkan lalu isi dari semua gambar (peta kode -> teks goresan) */
    public void setSemuaGoresan(java.util.Map<String, String> peta) {
        riwayat.clear();
        for (int i = 0; i < 8; i++) {
            setGoresan(i, peta == null ? null : peta.get(KODE[i]));
            for (int k = 0; k < goresan[i].size(); k++) {
                riwayat.add(i);
            }
        }
        repaint();
    }
}

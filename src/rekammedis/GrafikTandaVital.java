/*
 * Grafik tanda vital untuk form Durante Operasi (RM.34) dan Ruang Pulih (RM.34 hal.4).
 * Dipakai bersama oleh RMDataCatatanDuranteAnestesi dan RMDataCatatanObservasiRuangPulih.
 *
 * Simbol mengikuti legenda pada formulir kertas:
 *   v = sistole, ^ = diastole, titik hitam/merah = denyut (nadi), O = respirasi, * = SpO2,
 *   X-> awal anestesi, <-X akhir anestesi, O-> awal operasi, <-O akhir operasi, T = intubasi/ekstubasi.
 * Pada mode PULIH suhu (33-43 C) dipetakan ke skala 20-240 seperti kertas (43 C = 220, 33 C = 20).
 */
package rekammedis;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GrafikTandaVital extends javax.swing.JPanel {
    public static final int MODE_DURANTE = 0;
    public static final int MODE_PULIH = 1;

    public static final String AWAL_ANESTESI = "Awal Anestesi";
    public static final String AKHIR_ANESTESI = "Akhir Anestesi";
    public static final String AWAL_OPERASI = "Awal Operasi";
    public static final String AKHIR_OPERASI = "Akhir Operasi";
    public static final String INTUBASI = "Intubasi/Extubasi";
    public static final String EKSTUBASI = "Ekstubasi";

    /** Satu titik pengukuran. Nilai null = tidak diukur. */
    public static class Titik {
        public LocalDateTime waktu;
        public Double sistole, diastole, nadi, resp, spo2, suhu;
        public String air = "", o2 = "", n2o = "", sevo = "", iso = "";
        public String kejadian = "";

        public Titik(LocalDateTime waktu) {
            this.waktu = waktu;
        }
    }

    private static final int NILAI_MIN = 20;
    private static final int NILAI_MAX = 240;
    private static final int NILAI_STEP = 20;

    private final int mode;
    private int intervalMenit;
    private int labelSetiap;
    private int minKolom = 24;
    private List<Titik> data = new ArrayList<>();
    private LocalDateTime mulai = null;

    private final Color cGrid = new Color(185, 185, 185);
    private final Color cGridTebal = new Color(120, 120, 120);
    private final Color cSistol = new Color(20, 60, 160);
    private final Color cNadi = new Color(200, 20, 20);
    private final Color cResp = new Color(20, 20, 20);
    private final Color cSpo2 = new Color(0, 130, 60);
    private final Color cSuhu = new Color(210, 120, 0);

    public GrafikTandaVital(int mode) {
        this.mode = mode;
        if (mode == MODE_DURANTE) {
            intervalMenit = 5;
            labelSetiap = 3;
        } else {
            intervalMenit = 15;
            labelSetiap = 1;
        }
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(900, mode == MODE_DURANTE ? 470 : 380));
    }

    public void setData(List<Titik> d) {
        data = new ArrayList<>(d);
        Collections.sort(data, new Comparator<Titik>() {
            @Override
            public int compare(Titik a, Titik b) {
                return a.waktu.compareTo(b.waktu);
            }
        });
        repaint();
    }

    public List<Titik> getData() {
        return data;
    }

    /** Waktu awal sumbu X (mis. mulai anestesi). Jika null dipakai titik pertama. */
    public void setMulai(LocalDateTime m) {
        mulai = m;
        repaint();
    }

    public void setInterval(int menit, int labelSetiapKolom) {
        intervalMenit = menit;
        labelSetiap = labelSetiapKolom;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        gambar(g2, getWidth(), getHeight());
        g2.dispose();
    }

    /** Render ke gambar (untuk parameter report). */
    public BufferedImage render(int w, int h) {
        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = img.createGraphics();
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, w, h);
        gambar(g2, w, h);
        g2.dispose();
        return img;
    }

    // ------------------------------------------------------------------ gambar
    private void gambar(Graphics2D g2, int w, int h) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        Font fN = new Font("SansSerif", Font.PLAIN, 10);
        Font fB = new Font("SansSerif", Font.BOLD, 10);
        g2.setFont(fN);
        FontMetrics fm = g2.getFontMetrics();

        final boolean dur = mode == MODE_DURANTE;
        final int kiri = dur ? 50 : 78;
        final int kanan = 14;
        final int atas = 8;
        final int barisGas = dur ? 5 : 0;
        final int tinggiBaris = 17;
        final int tinggiLegenda = 30;
        final int tinggiKejadian = 22;
        final int tinggiLabelWaktu = 16;

        int gasTop = atas;
        int gridTop = gasTop + barisGas * tinggiBaris + (dur ? 4 : 0);
        int gridBottom = h - tinggiLegenda - tinggiKejadian - tinggiLabelWaktu - 4;
        if (gridBottom - gridTop < 120) gridBottom = gridTop + 120;
        int gridLeft = kiri;
        int gridRight = w - kanan;

        // ---- sumbu waktu
        LocalDateTime t0 = mulai;
        LocalDateTime tMax = null;
        for (Titik t : data) {
            if (t0 == null || t.waktu.isBefore(t0)) t0 = (mulai == null) ? t.waktu : t0;
            if (tMax == null || t.waktu.isAfter(tMax)) tMax = t.waktu;
        }
        if (t0 == null) t0 = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        int mnt = t0.getMinute() - (t0.getMinute() % intervalMenit);
        t0 = t0.withMinute(mnt).withSecond(0).withNano(0);
        long spanMenit = tMax == null ? 0 : Duration.between(t0, tMax).toMinutes();
        int kolom = (int) Math.max(minKolom, spanMenit / intervalMenit + 2);
        double lebarKolom = (gridRight - gridLeft) / (double) kolom;

        // ---- latar
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, w, h);

        // ---- baris gas (Air, O2, N2O, Sevo, ISO)
        if (dur) {
            String[] nm = {"Air", "O2", "N2O", "Sevo", "ISO"};
            g2.setStroke(new BasicStroke(1f));
            for (int i = 0; i < barisGas; i++) {
                int y = gasTop + i * tinggiBaris;
                g2.setColor(cGridTebal);
                g2.drawRect(gridLeft, y, gridRight - gridLeft, tinggiBaris);
                g2.setFont(fB);
                g2.setColor(Color.BLACK);
                g2.drawString(nm[i], 6, y + tinggiBaris - 5);
            }
            g2.setFont(fN);
            for (int c = 0; c <= kolom; c++) {
                int x = (int) Math.round(gridLeft + c * lebarKolom);
                g2.setColor(cGrid);
                g2.drawLine(x, gasTop, x, gasTop + barisGas * tinggiBaris);
            }
            for (Titik t : data) {
                double x = xPos(t.waktu, t0, gridLeft, lebarKolom);
                String[] v = {t.air, t.o2, t.n2o, t.sevo, t.iso};
                g2.setColor(Color.BLACK);
                for (int i = 0; i < barisGas; i++) {
                    if (v[i] != null && v[i].trim().length() > 0) {
                        String s = v[i].trim();
                        int sw = fm.stringWidth(s);
                        g2.drawString(s, (int) (x - sw / 2.0), gasTop + i * tinggiBaris + tinggiBaris - 5);
                    }
                }
            }
        }

        // ---- grid utama
        g2.setStroke(new BasicStroke(1f));
        Stroke putus = new BasicStroke(1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10f, new float[]{2f, 3f}, 0f);
        for (int c = 0; c <= kolom; c++) {
            int x = (int) Math.round(gridLeft + c * lebarKolom);
            g2.setColor(c % labelSetiap == 0 ? cGridTebal : cGrid);
            g2.setStroke(c % labelSetiap == 0 ? new BasicStroke(1f) : putus);
            g2.drawLine(x, gridTop, x, gridBottom);
        }
        for (int v = NILAI_MIN; v <= NILAI_MAX; v += NILAI_STEP) {
            int y = yPos(v, gridTop, gridBottom);
            g2.setColor(v % 100 == 0 ? cGridTebal : cGrid);
            g2.setStroke(v % 100 == 0 ? new BasicStroke(1f) : putus);
            g2.drawLine(gridLeft, y, gridRight, y);
            g2.setStroke(new BasicStroke(1f));
            g2.setColor(Color.BLACK);
            g2.setFont(fB);
            String s = String.valueOf(v);
            if (dur) {
                g2.drawString(s, gridLeft - 6 - fm.stringWidth(s), y + 4);
            } else {
                g2.drawString(s, gridLeft - 6 - fm.stringWidth(s), y + 4);
                if (v >= 20 && v <= 220 && v % 20 == 0) {
                    int tc = 33 + (v - 20) / 20;
                    String ts = tc + "°";
                    g2.setColor(cSuhu);
                    g2.drawString(ts, 8, y + 4);
                }
            }
        }
        g2.setStroke(new BasicStroke(1.4f));
        g2.setColor(Color.BLACK);
        g2.drawRect(gridLeft, gridTop, gridRight - gridLeft, gridBottom - gridTop);

        // ---- label waktu
        g2.setFont(fN);
        for (int c = 0; c < kolom; c += labelSetiap) {
            LocalDateTime tc = t0.plusMinutes((long) c * intervalMenit);
            String s = String.format("%02d:%02d", tc.getHour(), tc.getMinute());
            int x = (int) Math.round(gridLeft + c * lebarKolom);
            g2.setColor(Color.BLACK);
            g2.drawString(s, x - fm.stringWidth(s) / 2, gridBottom + 12);
        }

        // ---- garis penghubung
        g2.setStroke(new BasicStroke(1f));
        sambung(g2, cSistol, gridTop, gridBottom, gridLeft, lebarKolom, t0, 0);
        sambung(g2, cSistol, gridTop, gridBottom, gridLeft, lebarKolom, t0, 1);
        sambung(g2, cNadi, gridTop, gridBottom, gridLeft, lebarKolom, t0, 2);
        if (!dur) sambung(g2, cSuhu, gridTop, gridBottom, gridLeft, lebarKolom, t0, 5);

        // ---- simbol
        for (Titik t : data) {
            double x = xPos(t.waktu, t0, gridLeft, lebarKolom);
            if (t.sistole != null && t.diastole != null) {
                g2.setColor(new Color(cSistol.getRed(), cSistol.getGreen(), cSistol.getBlue(), 120));
                g2.setStroke(new BasicStroke(1f));
                g2.drawLine((int) x, yPos(t.sistole, gridTop, gridBottom), (int) x, yPos(t.diastole, gridTop, gridBottom));
            }
            g2.setStroke(new BasicStroke(1.6f));
            if (t.sistole != null) {          // v
                int y = yPos(t.sistole, gridTop, gridBottom);
                g2.setColor(cSistol);
                g2.drawLine((int) x - 4, y - 6, (int) x, y);
                g2.drawLine((int) x + 4, y - 6, (int) x, y);
            }
            if (t.diastole != null) {         // ^
                int y = yPos(t.diastole, gridTop, gridBottom);
                g2.setColor(cSistol);
                g2.drawLine((int) x - 4, y + 6, (int) x, y);
                g2.drawLine((int) x + 4, y + 6, (int) x, y);
            }
            if (t.nadi != null) {             // titik
                int y = yPos(t.nadi, gridTop, gridBottom);
                g2.setColor(cNadi);
                g2.fillOval((int) x - 4, y - 4, 8, 8);
            }
            if (t.resp != null) {             // O
                int y = yPos(t.resp, gridTop, gridBottom);
                g2.setColor(cResp);
                g2.drawOval((int) x - 4, y - 4, 8, 8);
            }
            if (t.spo2 != null) {             // *
                int y = yPos(t.spo2, gridTop, gridBottom);
                g2.setColor(cSpo2);
                asterisk(g2, (int) x, y, 5);
            }
            if (!dur && t.suhu != null) {     // belah ketupat
                int y = yPos(suhuKeSkala(t.suhu), gridTop, gridBottom);
                g2.setColor(cSuhu);
                int[] px = {(int) x, (int) x + 5, (int) x, (int) x - 5};
                int[] py = {y - 5, y, y + 5, y};
                g2.fillPolygon(px, py, 4);
            }
        }

        // ---- kejadian
        int yKej = gridBottom + tinggiLabelWaktu + 2;
        g2.setStroke(new BasicStroke(1f));
        g2.setColor(cGridTebal);
        g2.drawRect(gridLeft, yKej - 2, gridRight - gridLeft, tinggiKejadian);
        g2.setFont(fB);
        g2.setColor(Color.BLACK);
        g2.drawString("Kejadian", 6, yKej + 13);
        for (Titik t : data) {
            if (t.kejadian == null || t.kejadian.trim().isEmpty()) continue;
            double x = xPos(t.waktu, t0, gridLeft, lebarKolom);
            g2.setColor(new Color(200, 20, 20, 90));
            g2.setStroke(putus);
            g2.drawLine((int) x, gridTop, (int) x, yKej - 2);
            g2.setStroke(new BasicStroke(1.3f));
            g2.setColor(Color.BLACK);
            simbolKejadian(g2, t.kejadian.trim(), (int) x, yKej + 9);
        }

        // ---- legenda
        int yl = h - tinggiLegenda + 6;
        g2.setFont(fN);
        g2.setStroke(new BasicStroke(1.3f));
        int x = 6;
        x = legendaKejadian(g2, fm, AWAL_ANESTESI, ": Awal Anestesi", x, yl);
        x = legendaKejadian(g2, fm, AKHIR_ANESTESI, ": Akhir Anestesi", x, yl);
        x = legendaKejadian(g2, fm, AWAL_OPERASI, ": Awal Operasi", x, yl);
        x = legendaKejadian(g2, fm, AKHIR_OPERASI, ": Akhir Operasi", x, yl);
        int y2 = yl + 14;
        x = 6;
        g2.setColor(cSistol);
        g2.drawLine(x, y2 - 7, x + 4, y2 - 1);
        g2.drawLine(x + 8, y2 - 7, x + 4, y2 - 1);
        g2.setColor(Color.BLACK);
        g2.drawString(": Sistole", x + 12, y2);
        x += 70;
        g2.setColor(cSistol);
        g2.drawLine(x, y2 - 1, x + 4, y2 - 7);
        g2.drawLine(x + 8, y2 - 1, x + 4, y2 - 7);
        g2.setColor(Color.BLACK);
        g2.drawString(": Diastole", x + 12, y2);
        x += 75;
        g2.setColor(cNadi);
        g2.fillOval(x, y2 - 8, 8, 8);
        g2.setColor(Color.BLACK);
        g2.drawString(": Denyut (Nadi)", x + 12, y2);
        x += 105;
        g2.setColor(cResp);
        g2.drawOval(x, y2 - 8, 8, 8);
        g2.setColor(Color.BLACK);
        g2.drawString(": Respirasi", x + 12, y2);
        x += 82;
        g2.setColor(cSpo2);
        asterisk(g2, x + 4, y2 - 4, 4);
        g2.setColor(Color.BLACK);
        g2.drawString(": SpO2", x + 12, y2);
        x += 62;
        g2.drawString("T : Intubasi/Ekstubasi", x, y2);
        if (!dur) {
            x += 128;
            g2.setColor(cSuhu);
            int[] px = {x + 4, x + 9, x + 4, x - 1};
            int[] py = {y2 - 9, y2 - 4, y2 + 1, y2 - 4};
            g2.fillPolygon(px, py, 4);
            g2.setColor(Color.BLACK);
            g2.drawString(": Suhu (°C)", x + 14, y2);
        }
    }

    // ------------------------------------------------------------------ bantu
    public static double suhuKeSkala(double suhu) {
        return NILAI_MIN + (suhu - 33.0) * NILAI_STEP;
    }

    private double xPos(LocalDateTime t, LocalDateTime t0, int gridLeft, double lebarKolom) {
        double menit = Duration.between(t0, t).getSeconds() / 60.0;
        return gridLeft + (menit / intervalMenit) * lebarKolom + lebarKolom / 2.0; // titik di tengah sel, seperti formulir kertas
    }

    private int yPos(double v, int top, int bottom) {
        double c = Math.max(NILAI_MIN, Math.min(NILAI_MAX, v));
        return (int) Math.round(bottom - (c - NILAI_MIN) / (NILAI_MAX - NILAI_MIN) * (bottom - top));
    }

    /** jenis: 0 sistole, 1 diastole, 2 nadi, 5 suhu */
    private void sambung(Graphics2D g2, Color c, int top, int bottom, int gridLeft, double lk, LocalDateTime t0, int jenis) {
        g2.setColor(new Color(c.getRed(), c.getGreen(), c.getBlue(), 150));
        int px = Integer.MIN_VALUE, py = 0;
        for (Titik t : data) {
            Double v = jenis == 0 ? t.sistole : jenis == 1 ? t.diastole : jenis == 2 ? t.nadi : (t.suhu == null ? null : suhuKeSkala(t.suhu));
            if (v == null) continue;
            int x = (int) xPos(t.waktu, t0, gridLeft, lk);
            int y = yPos(v, top, bottom);
            if (px != Integer.MIN_VALUE) g2.drawLine(px, py, x, y);
            px = x;
            py = y;
        }
    }

    private void asterisk(Graphics2D g2, int x, int y, int r) {
        g2.drawLine(x - r, y, x + r, y);
        g2.drawLine(x, y - r, x, y + r);
        g2.drawLine(x - r + 1, y - r + 1, x + r - 1, y + r - 1);
        g2.drawLine(x - r + 1, y + r - 1, x + r - 1, y - r + 1);
    }

    private void tanda(Graphics2D g2, int x, int y, int r) {
        g2.drawLine(x - r, y - r, x + r, y + r);
        g2.drawLine(x - r, y + r, x + r, y - r);
    }

    private void panah(Graphics2D g2, int x1, int x2, int y) {
        g2.drawLine(x1, y, x2, y);
        int d = x2 > x1 ? -4 : 4;
        g2.drawLine(x2, y, x2 + d, y - 3);
        g2.drawLine(x2, y, x2 + d, y + 3);
    }

    /** Gambar simbol kejadian berpusat pada x. */
    private void simbolKejadian(Graphics2D g2, String k, int x, int y) {
        if (AWAL_ANESTESI.equalsIgnoreCase(k)) {
            tanda(g2, x - 6, y, 4);
            panah(g2, x - 1, x + 12, y);
        } else if (AKHIR_ANESTESI.equalsIgnoreCase(k)) {
            panah(g2, x + 1, x - 12, y);
            tanda(g2, x + 6, y, 4);
        } else if (AWAL_OPERASI.equalsIgnoreCase(k)) {
            g2.drawOval(x - 10, y - 4, 8, 8);
            panah(g2, x - 1, x + 12, y);
        } else if (AKHIR_OPERASI.equalsIgnoreCase(k)) {
            panah(g2, x + 1, x - 12, y);
            g2.drawOval(x + 2, y - 4, 8, 8);
        } else if (INTUBASI.equalsIgnoreCase(k) || EKSTUBASI.equalsIgnoreCase(k)) {
            g2.drawString("T", x - 3, y + 4);
        } else {
            g2.drawString("*", x - 2, y + 4);
        }
    }

    private int legendaKejadian(Graphics2D g2, FontMetrics fm, String kej, String teks, int x, int y) {
        g2.setColor(Color.BLACK);
        simbolKejadian(g2, kej, x + 14, y - 4);
        g2.drawString(teks, x + 30, y);
        return x + 30 + fm.stringWidth(teks) + 14;
    }
}

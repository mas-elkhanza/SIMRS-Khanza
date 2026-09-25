package widget;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JPasswordField;
import javax.swing.border.AbstractBorder;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicPasswordFieldUI;

/**
 * PasswordBox modern untuk SIMRS Khanza (pengganti usu.widget.glass.PasswordBox),
 * bentuk sama dengan widget.TextBox, tetap transparan.
 * <ul>
 *   <li>Kotak rounded, border tipis; fokus = border hijau + ring lembut; hover = border lebih gelap</li>
 *   <li>Karakter tersamar berupa titik bulat (•)</li>
 *   <li>Ikon mata di kanan: klik untuk menampilkan / menyembunyikan password</li>
 *   <li>TETAP TRANSPARAN seperti aslinya: tanpa latar, teks putih tebal, border putih tipis</li>
 * </ul>
 * Font tetap tebal 12 seperti aslinya. getText()/getPassword() tidak berubah.
 * Kompatibel Java 8.
 *
 * @author usu (dimodernkan)
 */
public class PasswordBox extends JPasswordField {

    private static final long serialVersionUID = 2L;

    // tema transparan: dirancang untuk latar gelap/gambar (seperti PasswordBox glass lama)
    static final Color AKSEN    = new Color(0x3DDC84);                  // hijau terang, kontras di latar gelap
    static final Color BORDER   = new Color(255, 255, 255, 150);
    static final Color HOVER    = new Color(255, 255, 255, 200);
    static final Color IKON     = new Color(255, 255, 255, 170);
    static final char  TITIK    = '•';
    static final int   RADIUS   = 6;
    static final int   LEBAR_IKON = 22;

    // tanpa initializer: updateUI() dipanggil dari konstruktor JPasswordField
    private boolean hover;
    private boolean hoverIkon;
    private boolean tampilkan;
    private boolean ikonMata;

    public PasswordBox() {
        super();
        ikonMata = true;
        setFont(new Font("Tahoma", Font.BOLD, 12));
        setForeground(Color.WHITE);
        setSelectionColor(new Color(255, 255, 255, 90));
        setSelectedTextColor(Color.WHITE);
        setCaretColor(Color.WHITE);
        setDisabledTextColor(new Color(255, 255, 255, 110));
        setHorizontalAlignment(LEFT);
        setEchoChar(TITIK);
        setOpaque(false);
        setBorder(new BorderModern());

        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) { repaint(); }

            @Override
            public void focusLost(FocusEvent e) { repaint(); }
        });

        MouseAdapter m = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) { hover = true; repaint(); }

            @Override
            public void mouseExited(MouseEvent e) {
                hover = false;
                hoverIkon = false;
                setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
                repaint();
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                boolean di = ikonMata && areaIkon().contains(e.getPoint());
                if (di != hoverIkon) {
                    hoverIkon = di;
                    setCursor(Cursor.getPredefinedCursor(di ? Cursor.HAND_CURSOR : Cursor.TEXT_CURSOR));
                    repaint();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (ikonMata && isEnabled() && areaIkon().contains(e.getPoint())) {
                    tampilkan = !tampilkan;
                    setEchoChar(tampilkan ? (char) 0 : TITIK);
                    e.consume();
                    requestFocusInWindow();
                    repaint();
                }
            }
        };
        addMouseListener(m);
        addMouseMotionListener(m);
    }

    /**
     * Border selalu BorderModern. Border dari luar (mis. setBorder di initComponents
     * NetBeans atau dari L&amp;F) diabaikan supaya garis rounded tidak hilang.
     */
    @Override
    public void setBorder(Border border) {
        if (!(getBorder() instanceof BorderModern)) {
            super.setBorder(new BorderModern());
        }
    }

    /** Pakai BasicPasswordFieldUI supaya tampilan sama di semua OS / L&amp;F. */
    @Override
    public void updateUI() {
        setUI(new BasicPasswordFieldUI());
    }

    private Rectangle areaIkon() {
        return new Rectangle(getWidth() - LEBAR_IKON - 3, 0, LEBAR_IKON, getHeight());
    }

    // ------------------------------------------------------------------ painting

    @Override
    protected void paintComponent(Graphics g) {
        // transparan: tidak ada latar, hanya teks + border
        super.paintComponent(g);

        if (ikonMata && isEnabled()) {
            gambarMata((Graphics2D) g.create());
        }
    }

    private void gambarMata(Graphics2D g2) {
        try {
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            Rectangle r = areaIkon();
            float cx = r.x + r.width / 2f;
            float cy = r.y + r.height / 2f;

            if (hoverIkon) {
                g2.setColor(new Color(255, 255, 255, 40));
                g2.fill(new RoundRectangle2D.Float(cx - 9, cy - 7, 18, 14, 6, 6));
            }

            g2.setColor(hoverIkon || tampilkan ? AKSEN : IKON);
            g2.setStroke(new BasicStroke(1.3f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

            // bentuk mata
            Path2D mata = new Path2D.Float();
            mata.moveTo(cx - 6.5f, cy);
            mata.quadTo(cx, cy - 6.5f, cx + 6.5f, cy);
            mata.quadTo(cx, cy + 6.5f, cx - 6.5f, cy);
            g2.draw(mata);
            g2.fill(new Ellipse2D.Float(cx - 2f, cy - 2f, 4f, 4f));

            // garis coret saat password tersembunyi
            if (!tampilkan) {
                g2.draw(new Line2D.Float(cx - 6f, cy + 5f, cx + 6f, cy - 5f));
            }
        } finally {
            g2.dispose();
        }
    }

    /** Border rounded + ring fokus (identik dengan TextBox), ruang kanan untuk ikon mata. */
    private class BorderModern extends AbstractBorder {

        private static final long serialVersionUID = 1L;

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
            Graphics2D g2 = (Graphics2D) g.create();
            try {
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                boolean aktif = isEnabled() && isEditable();
                boolean fokus = aktif && isFocusOwner();
                float arc = RADIUS * 2f;

                if (fokus) {
                    g2.setColor(new Color(AKSEN.getRed(), AKSEN.getGreen(), AKSEN.getBlue(), 80));
                    g2.setStroke(new BasicStroke(2f));
                    g2.draw(new RoundRectangle2D.Float(x + 1, y + 1, w - 2, h - 2, arc + 1, arc + 1));
                }
                Color garis = !isEnabled() ? new Color(255, 255, 255, 60)
                        : fokus ? AKSEN
                        : (hover && aktif) ? HOVER
                        : BORDER;
                // lapis 1: garis gelap tipis di luar (agar tetap terlihat di latar terang)
                g2.setStroke(new BasicStroke(1f));
                g2.setColor(new Color(0, 0, 0, isEnabled() ? 70 : 35));
                g2.draw(new RoundRectangle2D.Float(x + 0.5f, y + 0.5f, w - 1, h - 1, arc + 1, arc + 1));
                // lapis 2: garis utama (putih / hijau saat fokus)
                g2.setColor(garis);
                g2.draw(new RoundRectangle2D.Float(x + 1.5f, y + 1.5f, w - 3, h - 3, arc, arc));
            } finally {
                g2.dispose();
            }
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return getBorderInsets(c, new Insets(0, 0, 0, 0));
        }

        @Override
        public Insets getBorderInsets(Component c, Insets insets) {
            insets.set(3, 8, 3, ikonMata ? LEBAR_IKON + 5 : 8);
            return insets;
        }
    }

    // ------------------------------------------------------------------ properti

    public boolean isIkonMata() { return ikonMata; }

    /** false = sembunyikan ikon mata (password tidak bisa ditampilkan). */
    public void setIkonMata(boolean b) {
        ikonMata = b;
        if (!b && tampilkan) {
            tampilkan = false;
            setEchoChar(TITIK);
        }
        revalidate();
        repaint();
    }
}
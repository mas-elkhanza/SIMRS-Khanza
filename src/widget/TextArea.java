package widget;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JViewport;
import javax.swing.border.AbstractBorder;
import javax.swing.border.EmptyBorder;

/**
 * TextArea modern untuk SIMRS Khanza, tampilannya SAMA dengan widget.TextBox.
 * <p>
 * Karena TextArea selalu dibungkus ScrollPane, border rounded dipasang otomatis
 * ke ScrollPane pembungkusnya (apa pun border lamanya), sehingga:
 * <ul>
 *   <li>Kotak rounded, border tipis, warna sama persis dengan TextBox</li>
 *   <li>Fokus: border hijau + ring lembut; hover: border lebih gelap</li>
 *   <li>Non-editable / disabled: latar hampir putih, teks tetap terbaca</li>
 *   <li>Scrollbar tipis modern (sama dengan widget.ScrollPane)</li>
 *   <li>Seleksi hijau muda, caret hijau, placeholder opsional</li>
 * </ul>
 * Drop-in: konstruktor sama, extends JTextArea. Kompatibel Java 8.
 *
 * @author usu (dimodernkan)
 */
public class TextArea extends JTextArea {

    private static final long serialVersionUID = 2L;

    // warna & ukuran disamakan dengan widget.TextBox
    static final Color AKSEN    = new Color(0x16A05D);
    static final Color BORDER   = new Color(0xA7B6AD);
    static final Color HOVER    = new Color(0x6F8578);
    static final Color NONAKTIF = new Color(0xFAFCFB);
    static final int   RADIUS   = 6;

    private String placeholder;
    private boolean hover;

    public TextArea() {
        super();
        setOpaque(false);
        setLineWrap(true);
        setWrapStyleWord(true);
        setBorder(new EmptyBorder(4, 6, 4, 6));
        setFont(new Font("Tahoma", Font.PLAIN, 11));
        setSelectionColor(new Color(0xCDEBDA));
        setSelectedTextColor(new Color(0x0E3B24));
        setCaretColor(AKSEN);
        setForeground(new Color(0x1C2520));
        setDisabledTextColor(new Color(0x55625B));
        setBackground(Color.WHITE);

        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) { repaintBungkus(); }

            @Override
            public void focusLost(FocusEvent e) { repaintBungkus(); }
        });
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) { hover = true; repaintBungkus(); }

            @Override
            public void mouseExited(MouseEvent e) { hover = false; repaintBungkus(); }
        });
    }

    // ------------------------------------------------------------------ pasang gaya ke ScrollPane

    @Override
    public void addNotify() {
        super.addNotify();
        JScrollPane sp = scrollPane();
        if (sp != null && !(sp.getBorder() instanceof BorderTextArea)) {
            sp.setBorder(new BorderTextArea());
            sp.setOpaque(false);
            sp.setViewportBorder(null);
            JViewport vp = sp.getViewport();
            vp.setOpaque(false);
            vp.setScrollMode(JViewport.SIMPLE_SCROLL_MODE);   // aman untuk viewport transparan
            sp.getVerticalScrollBar().setUI(new ScrollPane.ModernScrollBarUI());
            sp.getHorizontalScrollBar().setUI(new ScrollPane.ModernScrollBarUI());
            sp.getVerticalScrollBar().setOpaque(false);
            sp.getHorizontalScrollBar().setOpaque(false);
            MouseAdapter m = new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) { hover = true; repaintBungkus(); }

                @Override
                public void mouseExited(MouseEvent e) { hover = false; repaintBungkus(); }
            };
            sp.getVerticalScrollBar().addMouseListener(m);
            sp.addMouseListener(m);
        }
    }

    private JScrollPane scrollPane() {
        Container p = getParent();
        if (p instanceof JViewport && p.getParent() instanceof JScrollPane) {
            return (JScrollPane) p.getParent();
        }
        return null;
    }

    private void repaintBungkus() {
        JScrollPane sp = scrollPane();
        if (sp != null) {
            sp.repaint();
        } else {
            repaint();
        }
    }

    /** Border rounded identik dengan TextBox; sekaligus menggambar latar di dalamnya. */
    private class BorderTextArea extends AbstractBorder {

        private static final long serialVersionUID = 1L;

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
            Graphics2D g2 = (Graphics2D) g.create();
            try {
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                float arc = RADIUS * 2f;
                boolean aktif = isEnabled() && isEditable();
                boolean fokus = aktif && isFocusOwner();

                // latar (dipakai karena scrollpane & viewport transparan)
                g2.setColor(aktif ? getBackground() : NONAKTIF);
                g2.fill(new RoundRectangle2D.Float(x + 1, y + 1, w - 2, h - 2, arc, arc));

                if (fokus) {
                    g2.setColor(new Color(AKSEN.getRed(), AKSEN.getGreen(), AKSEN.getBlue(), 60));
                    g2.setStroke(new BasicStroke(2f));
                    g2.draw(new RoundRectangle2D.Float(x + 1, y + 1, w - 2, h - 2, arc + 1, arc + 1));
                }
                Color garis = !isEnabled() ? new Color(BORDER.getRed(), BORDER.getGreen(), BORDER.getBlue(), 130)
                        : fokus ? AKSEN
                        : (hover && aktif) ? HOVER
                        : BORDER;
                g2.setColor(garis);
                g2.setStroke(new BasicStroke(1f));
                g2.draw(new RoundRectangle2D.Float(x + 1.5f, y + 1.5f, w - 3, h - 3, arc, arc));
            } finally {
                g2.dispose();
            }
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(3, 3, 3, 3);
        }

        @Override
        public Insets getBorderInsets(Component c, Insets insets) {
            insets.set(3, 3, 3, 3);
            return insets;
        }
    }

    // ------------------------------------------------------------------ painting

    @Override
    protected void paintComponent(Graphics g) {
        // bila TIDAK dibungkus scrollpane, latar non-aktif digambar sendiri
        if (scrollPane() == null && (!isEnabled() || !isEditable())) {
            g.setColor(NONAKTIF);
            g.fillRect(0, 0, getWidth(), getHeight());
        }

        super.paintComponent(g);

        if (placeholder != null && !placeholder.isEmpty() && getText().isEmpty() && !isFocusOwner()) {
            Graphics2D g2 = (Graphics2D) g.create();
            try {
                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                g2.setFont(getFont().deriveFont(Font.ITALIC));
                g2.setColor(new Color(0xA3ADA8));
                Insets in = getInsets();
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(placeholder, in.left, in.top + fm.getAscent());
            } finally {
                g2.dispose();
            }
        }
    }

    // ------------------------------------------------------------------ properti

    public String getPlaceholder() { return placeholder; }
    public void setPlaceholder(String p) { placeholder = p; repaint(); }
}
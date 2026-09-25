package widget;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
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
import javax.swing.JTextField;
import javax.swing.border.AbstractBorder;
import javax.swing.plaf.basic.BasicTextFieldUI;

public class TextBox extends JTextField {
    private static final long serialVersionUID = 2L;
    static final Color AKSEN_DEFAULT = new Color(0x16A05D);
    private Color warnaAksen;
    private Color warnaBorder;
    private Color warnaNonAktif;
    private String placeholder;
    private int radius;
    private boolean hover;

    public TextBox() {
        super();
        warnaAksen = AKSEN_DEFAULT;
        warnaBorder = new Color(0xA7B6AD);
        warnaNonAktif = new Color(0xFAFCFB);  
        radius = 6;

        setFont(new Font("Tahoma", Font.PLAIN, 11));
        setForeground(new Color(0x1C2520));
        setBackground(Color.WHITE);
        setSelectionColor(new Color(0xCDEBDA));
        setSelectedTextColor(new Color(0x0E3B24));
        setCaretColor(warnaAksen);
        setDisabledTextColor(new Color(0x55625B));   
        setHorizontalAlignment(LEFT);
        setOpaque(false);
        setBorder(new BorderModern());

        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) { repaint(); }

            @Override
            public void focusLost(FocusEvent e) { repaint(); }
        });
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) { hover = true; repaint(); }

            @Override
            public void mouseExited(MouseEvent e) { hover = false; repaint(); }
        });
    }

    @Override
    public void updateUI() {
        setUI(new BasicTextFieldUI());
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        try {
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            Color latar = (!isEnabled() || !isEditable()) ? warnaNonAktif : getBackground();
            g2.setColor(latar);
            g2.fill(new RoundRectangle2D.Float(1, 1, getWidth() - 2, getHeight() - 2, radius * 2f, radius * 2f));
        } finally {
            g2.dispose();
        }

        super.paintComponent(g);  
        
        if (placeholder != null && !placeholder.isEmpty() && getText().isEmpty() && !isFocusOwner()) {
            Graphics2D gp = (Graphics2D) g.create();
            try {
                gp.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                gp.setFont(getFont().deriveFont(Font.ITALIC));
                gp.setColor(new Color(0xA3ADA8));
                Insets in = getInsets();
                FontMetrics fm = gp.getFontMetrics();
                int y = in.top + (getHeight() - in.top - in.bottom - fm.getHeight()) / 2 + fm.getAscent();
                gp.drawString(placeholder, in.left, y);
            } finally {
                gp.dispose();
            }
        }
    }

    private class BorderModern extends AbstractBorder {

        private static final long serialVersionUID = 1L;

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
            Graphics2D g2 = (Graphics2D) g.create();
            try {
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                boolean aktif = isEnabled() && isEditable();
                boolean fokus = aktif && isFocusOwner();
                float arc = radius * 2f;

                if (fokus) {
                    g2.setColor(alpha(warnaAksen, 60));
                    g2.setStroke(new BasicStroke(2f));
                    g2.draw(new RoundRectangle2D.Float(x + 1, y + 1, w - 2, h - 2, arc + 1, arc + 1));
                }

                Color garis;
                if (!isEnabled()) {
                    garis = alpha(warnaBorder, 130);
                } else if (fokus) {
                    garis = warnaAksen;
                } else if (hover && aktif) {
                    garis = new Color(0x6F8578);
                } else {
                    garis = warnaBorder;
                }
                g2.setColor(garis);
                g2.setStroke(new BasicStroke(1f));
                g2.draw(new RoundRectangle2D.Float(x + 1.5f, y + 1.5f, w - 3, h - 3, arc, arc));
            } finally {
                g2.dispose();
            }
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(4, 8, 4, 8);
        }

        @Override
        public Insets getBorderInsets(Component c, Insets insets) {
            insets.set(4, 8, 4, 8);
            return insets;
        }
    }

    private static Color alpha(Color c, int a) {
        return new Color(c.getRed(), c.getGreen(), c.getBlue(), a);
    }

    public String getPlaceholder() { return placeholder; }
    public void setPlaceholder(String p) { placeholder = p; repaint(); }

    public Color getWarnaAksen() { return warnaAksen; }
    public void setWarnaAksen(Color c) { warnaAksen = c; setCaretColor(c); repaint(); }

    public Color getWarnaBorder() { return warnaBorder; }
    public void setWarnaBorder(Color c) { warnaBorder = c; repaint(); }

    public Color getWarnaNonAktif() { return warnaNonAktif; }
    public void setWarnaNonAktif(Color c) { warnaNonAktif = c; repaint(); }

    public int getRadius() { return radius; }
    public void setRadius(int r) { radius = r; repaint(); }
}
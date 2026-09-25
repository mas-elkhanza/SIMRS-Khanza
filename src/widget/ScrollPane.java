package widget;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class ScrollPane extends JScrollPane {

    private static final long serialVersionUID = 2L;

    static final Color AKSEN_DEFAULT = new Color(0x16A05D);
    static final Color TRACK_DEFAULT = new Color(0xF6F9F7);

    public ScrollPane() {
        super();
        JViewport vp = new JViewport();
        vp.setBackground(Color.WHITE);
        setViewport(vp);

        setOpaque(false);
        setBackground(Color.WHITE);
        setBorder(new LineBorder(new Color(239, 244, 234)));  

        getVerticalScrollBar().setUI(new ModernScrollBarUI());
        getHorizontalScrollBar().setUI(new ModernScrollBarUI());
        getVerticalScrollBar().setUnitIncrement(15);
        getHorizontalScrollBar().setUnitIncrement(15);
        getVerticalScrollBar().setOpaque(false);
        getHorizontalScrollBar().setOpaque(false);

        setCorner(LOWER_RIGHT_CORNER, pojok());
        setCorner(UPPER_RIGHT_CORNER, pojok());
    }

    private static JPanel pojok() {
        JPanel p = new JPanel();
        p.setBackground(TRACK_DEFAULT);
        return p;
    }

    public static class ModernScrollBarUI extends BasicScrollBarUI {
        private static final int LEBAR = 11;      
        private static final int THUMB = 6;        
        private static final int THUMB_HOVER = 8;  

        @Override
        protected void configureScrollBarColors() {
            super.configureScrollBarColors();
            trackColor = TRACK_DEFAULT;
            thumbColor = new Color(0xC2CEC7);
        }

        @Override
        public Dimension getPreferredSize(JComponent c) {
            return scrollbar.getOrientation() == JScrollBar.VERTICAL
                    ? new Dimension(LEBAR, 48)
                    : new Dimension(48, LEBAR);
        }

        @Override
        protected JButton createDecreaseButton(int orientation) {
            return tombolKosong();
        }

        @Override
        protected JButton createIncreaseButton(int orientation) {
            return tombolKosong();
        }

        private static JButton tombolKosong() {
            JButton b = new JButton();
            Dimension nol = new Dimension(0, 0);
            b.setPreferredSize(nol);
            b.setMinimumSize(nol);
            b.setMaximumSize(nol);
            b.setFocusable(false);
            b.setBorderPainted(false);
            b.setContentAreaFilled(false);
            return b;
        }

        @Override
        protected Dimension getMinimumThumbSize() {
            return new Dimension(24, 24);
        }

        @Override
        protected void paintTrack(Graphics g, JComponent c, Rectangle r) {
            g.setColor(trackColor);
            g.fillRect(r.x, r.y, r.width, r.height);
            g.setColor(new Color(0xE6EDE9));
            if (scrollbar.getOrientation() == JScrollBar.VERTICAL) {
                g.drawLine(r.x, r.y, r.x, r.y + r.height - 1);
            } else {
                g.drawLine(r.x, r.y, r.x + r.width - 1, r.y);
            }
        }

        @Override
        protected void paintThumb(Graphics g, JComponent c, Rectangle r) {
            if (r.isEmpty() || !scrollbar.isEnabled()) {
                return;
            }
            Graphics2D g2 = (Graphics2D) g.create();
            try {
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                boolean aktif = isDragging || isThumbRollover();
                int tebal = aktif ? THUMB_HOVER : THUMB;
                Color warna = isDragging ? AKSEN_DEFAULT
                        : isThumbRollover() ? new Color(0x98ABA0) : thumbColor;
                g2.setColor(warna);

                if (scrollbar.getOrientation() == JScrollBar.VERTICAL) {
                    int x = r.x + (r.width - tebal) / 2 + 1;
                    g2.fillRoundRect(x, r.y + 2, tebal, r.height - 4, tebal, tebal);
                } else {
                    int y = r.y + (r.height - tebal) / 2 + 1;
                    g2.fillRoundRect(r.x + 2, y, r.width - 4, tebal, tebal, tebal);
                }
            } finally {
                g2.dispose();
            }
        }

        @Override
        protected void setThumbRollover(boolean active) {
            boolean lama = isThumbRollover();
            super.setThumbRollover(active);
            if (lama != active) {
                scrollbar.repaint();
            }
        }
    }

    public void setWarnaBorder(Color c) {
        setBorder(new LineBorder(c));
    }

    @Override
    public void setViewportView(Component view) {
        super.setViewportView(view);
        getViewport().setBackground(Color.WHITE);
    }
}
package widget;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import javax.swing.JComponent;
import javax.swing.JTabbedPane;
import javax.swing.plaf.basic.BasicGraphicsUtils;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import javax.swing.text.View;

public class TabPane extends JTabbedPane {

    private static final long serialVersionUID = 2L;

    static final Color AKSEN_DEFAULT = new Color(0x16A05D);
    static final Color STRIP_DEFAULT = new Color(0xF2F7F4);
    static final Color GARIS_DEFAULT = new Color(0xD6E3DB);

    private Color warnaAksen;
    private Color warnaStrip;
    private Color warnaGaris;

    public TabPane() {
        super();
        warnaAksen = AKSEN_DEFAULT;
        warnaStrip = STRIP_DEFAULT;
        warnaGaris = GARIS_DEFAULT;
        setBackground(Color.WHITE);
        setForeground(new Color(0x56615B));
        setOpaque(true);
    }

    @Override
    public void updateUI() {
        setUI(new ModernTabUI());
    }

    public Color getWarnaAksen() { return warnaAksen != null ? warnaAksen : AKSEN_DEFAULT; }
    public void setWarnaAksen(Color c) { warnaAksen = c; repaint(); }

    public Color getWarnaStrip() { return warnaStrip != null ? warnaStrip : STRIP_DEFAULT; }
    public void setWarnaStrip(Color c) { warnaStrip = c; repaint(); }

    public Color getWarnaGaris() { return warnaGaris != null ? warnaGaris : GARIS_DEFAULT; }
    public void setWarnaGaris(Color c) { warnaGaris = c; repaint(); }

    private static class ModernTabUI extends BasicTabbedPaneUI {

        private static final int TEBAL_AKSEN = 3;

        private TabPane tp() {
            return (tabPane instanceof TabPane) ? (TabPane) tabPane : null;
        }

        private Color aksen() { return tp() != null ? tp().getWarnaAksen() : AKSEN_DEFAULT; }
        private Color strip() { return tp() != null ? tp().getWarnaStrip() : STRIP_DEFAULT; }
        private Color garis() { return tp() != null ? tp().getWarnaGaris() : GARIS_DEFAULT; }

        @Override
        protected void installDefaults() {
            super.installDefaults();
            tabInsets = new Insets(6, 14, 6, 14);
            selectedTabPadInsets = new Insets(0, 0, 0, 0);
            tabAreaInsets = new Insets(3, 4, 0, 4);
            contentBorderInsets = new Insets(1, 0, 0, 0);
            tabRunOverlay = 0;
        }

        @Override
        protected Insets getContentBorderInsets(int placement) {
            switch (placement) {
                case BOTTOM: return new Insets(0, 0, 1, 0);
                case LEFT:   return new Insets(0, 1, 0, 0);
                case RIGHT:  return new Insets(0, 0, 0, 1);
                default:     return new Insets(1, 0, 0, 0);
            }
        }

        @Override
        protected Insets getTabAreaInsets(int placement) {
            switch (placement) {
                case BOTTOM: return new Insets(0, 4, 3, 4);
                case LEFT:   return new Insets(4, 3, 4, 0);
                case RIGHT:  return new Insets(4, 0, 4, 3);
                default:     return new Insets(3, 4, 0, 4);
            }
        }

        @Override
        protected void setRolloverTab(int index) {
            int lama = getRolloverTab();
            super.setRolloverTab(index);
            if (lama != index && tabPane != null) {
                tabPane.repaint();
            }
        }

        @Override
        public void paint(Graphics g, JComponent c) {
            // strip latar area tab
            if (tabPane.getTabCount() > 0) {
                Rectangle area = tabAreaBounds();
                g.setColor(strip());
                g.fillRect(area.x, area.y, area.width, area.height);
            }
            super.paint(g, c);
        }

        private Rectangle tabAreaBounds() {
            Insets ins = tabPane.getInsets();
            int placement = tabPane.getTabPlacement();
            int w = tabPane.getWidth() - ins.left - ins.right;
            int h = tabPane.getHeight() - ins.top - ins.bottom;
            switch (placement) {
                case BOTTOM: {
                    int th = calculateTabAreaHeight(placement, runCount, maxTabHeight);
                    return new Rectangle(ins.left, ins.top + h - th, w, th);
                }
                case LEFT: {
                    int tw = calculateTabAreaWidth(placement, runCount, maxTabWidth);
                    return new Rectangle(ins.left, ins.top, tw, h);
                }
                case RIGHT: {
                    int tw = calculateTabAreaWidth(placement, runCount, maxTabWidth);
                    return new Rectangle(ins.left + w - tw, ins.top, tw, h);
                }
                default: {
                    int th = calculateTabAreaHeight(placement, runCount, maxTabHeight);
                    return new Rectangle(ins.left, ins.top, w, th);
                }
            }
        }

        @Override
        protected void paintTabBackground(Graphics g, int placement, int tabIndex,
                                          int x, int y, int w, int h, boolean isSelected) {
            if (isSelected) {
                g.setColor(tabPane.getBackground());
                g.fillRect(x, y, w, h);
            } else if (tabIndex == getRolloverTab() && tabPane.isEnabledAt(tabIndex)) {
                Color a = aksen();
                g.setColor(new Color(a.getRed(), a.getGreen(), a.getBlue(), 22));
                g.fillRect(x, y, w, h);
            }
        }

        @Override
        protected void paintTabBorder(Graphics g, int placement, int tabIndex,
                                      int x, int y, int w, int h, boolean isSelected) {
            if (!isSelected) {
                return;
            }
            Graphics2D g2 = (Graphics2D) g.create();
            try {
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // garis tipis di sisi tab aktif supaya "menyatu" dengan konten
                g2.setColor(garis());
                switch (placement) {
                    case BOTTOM:
                    case TOP:
                        g2.drawLine(x, y, x, y + h - 1);
                        g2.drawLine(x + w - 1, y, x + w - 1, y + h - 1);
                        break;
                    default:
                        g2.drawLine(x, y, x + w - 1, y);
                        g2.drawLine(x, y + h - 1, x + w - 1, y + h - 1);
                }
                // indikator aksen
                g2.setColor(aksen());
                int t = TEBAL_AKSEN;
                switch (placement) {
                    case BOTTOM: g2.fillRoundRect(x + 1, y + h - t, w - 2, t, t, t); break;
                    case LEFT:   g2.fillRoundRect(x, y + 1, t, h - 2, t, t);         break;
                    case RIGHT:  g2.fillRoundRect(x + w - t, y + 1, t, h - 2, t, t); break;
                    default:     g2.fillRoundRect(x + 1, y, w - 2, t, t, t);
                }
            } finally {
                g2.dispose();
            }
        }

        @Override
        protected void paintText(Graphics g, int placement, Font font, FontMetrics metrics,
                                 int tabIndex, String title, Rectangle textRect, boolean isSelected) {
            View v = getTextViewForTab(tabIndex);
            if (v != null) {                    // judul HTML: pakai bawaan
                super.paintText(g, placement, font, metrics, tabIndex, title, textRect, isSelected);
                return;
            }
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                    RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g2.setFont(font);

            Color warna;
            if (!tabPane.isEnabled() || !tabPane.isEnabledAt(tabIndex)) {
                warna = new Color(0xB3BCB7);
            } else if (isSelected) {
                warna = aksen().darker();
            } else {
                warna = tabPane.getForegroundAt(tabIndex);
            }
            g2.setColor(warna);
            BasicGraphicsUtils.drawStringUnderlineCharAt(g2, title,
                    tabPane.getDisplayedMnemonicIndexAt(tabIndex),
                    textRect.x, textRect.y + metrics.getAscent());
        }

        @Override
        protected void paintFocusIndicator(Graphics g, int placement, Rectangle[] rects,
                                           int tabIndex, Rectangle iconRect, Rectangle textRect,
                                           boolean isSelected) {
        }

        @Override
        protected void paintContentBorder(Graphics g, int placement, int selectedIndex) {
            Rectangle area = tabAreaBounds();
            Insets ins = tabPane.getInsets();
            int x = ins.left, y = ins.top;
            int w = tabPane.getWidth() - ins.left - ins.right;
            int h = tabPane.getHeight() - ins.top - ins.bottom;

            // celah di bawah tab aktif supaya tab menyatu dengan konten
            Rectangle sel = (selectedIndex >= 0 && selectedIndex < rects.length) ? rects[selectedIndex] : null;

            g.setColor(garis());
            switch (placement) {
                case BOTTOM: garisH(g, x, x + w - 1, area.y, sel);  break;
                case LEFT:   garisV(g, area.x + area.width, y, y + h - 1, sel); break;
                case RIGHT:  garisV(g, area.x - 1, y, y + h - 1, sel); break;
                default:     garisH(g, x, x + w - 1, area.y + area.height, sel);
            }
        }

        private static void garisH(Graphics g, int x1, int x2, int y, Rectangle sel) {
            if (sel == null) {
                g.drawLine(x1, y, x2, y);
                return;
            }
            if (sel.x > x1) {
                g.drawLine(x1, y, sel.x, y);
            }
            if (sel.x + sel.width - 1 < x2) {
                g.drawLine(sel.x + sel.width - 1, y, x2, y);
            }
        }

        private static void garisV(Graphics g, int x, int y1, int y2, Rectangle sel) {
            if (sel == null) {
                g.drawLine(x, y1, x, y2);
                return;
            }
            if (sel.y > y1) {
                g.drawLine(x, y1, x, sel.y);
            }
            if (sel.y + sel.height - 1 < y2) {
                g.drawLine(x, sel.y + sel.height - 1, x, y2);
            }
        }
    }
}
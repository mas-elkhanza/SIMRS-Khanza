package widget;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JComponent;
import javax.swing.JTabbedPane;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicGraphicsUtils;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import javax.swing.text.View;

public class TabPane extends JTabbedPane {
    private static final long serialVersionUID = 3L;
    static final Color AKSEN = new Color(0x16A05D);
    private Color warnaAksen;

    public TabPane() {
        super();
        warnaAksen = AKSEN;
        setBackground(new Color(255, 255, 255));
        setForeground(new Color(50, 50, 50));
        setBorder(null);   
    }

    @Override
    public void updateUI() {
        setUI(new MacTabUI());
    }


    public Color getWarnaAksen() { return warnaAksen != null ? warnaAksen : AKSEN; }
    public void setWarnaAksen(Color c) { warnaAksen = c; repaint(); }

    public static class MacTabUI extends BasicTabbedPaneUI {
        private static final Color TRACK        = new Color(239, 244, 234);  
        private static final Color TRACK_GARIS  = new Color(214, 224, 207);  
        private static final Color PILIH        = Color.WHITE;
        private static final Color PILIH_GARIS  = new Color(206, 218, 198);
        private static final Color BAYANGAN     = new Color(60, 90, 50, 26);
        private static final Color HOVER        = new Color(255, 255, 255, 140);
        private static final Color PEMISAH      = new Color(206, 218, 198);
        private static final Color BINGKAI      = new Color(239, 244, 234);  
        private static final Color TEKS         = new Color(50, 50, 50);     
        private static final Color TEKS_OFF     = new Color(165, 175, 160);
        private static final int   ARC_TRACK    = 8;
        private static final int   ARC_PILIH    = 6;
        private static final int   ARC_BINGKAI  = 8;
        private static final boolean TAMPIL_BINGKAI = false;
        private boolean lewatiBingkai;

        public static ComponentUI createUI(JComponent c) {
            return new MacTabUI();
        }

        @Override
        protected void installDefaults() {
            super.installDefaults();
            tabInsets = new Insets(3, 12, 3, 12);
            selectedTabPadInsets = new Insets(0, 0, 0, 0);
            tabAreaInsets = new Insets(4, 8, 4, 8);
            contentBorderInsets = new Insets(0, 0, 0, 0);   
            tabRunOverlay = 0;
        }

        @Override
        protected Insets getContentBorderInsets(int placement) {
            return new Insets(0, 0, 0, 0);
        }

        @Override
        protected int calculateTabHeight(int placement, int tabIndex, int fontHeight) {
            return Math.max(super.calculateTabHeight(placement, tabIndex, fontHeight), 22);
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
        protected LayoutManager createLayoutManager() {
            if (tabPane.getTabLayoutPolicy() == JTabbedPane.SCROLL_TAB_LAYOUT) {
                return super.createLayoutManager();
            }
            return new LayoutTengah();
        }

        private class LayoutTengah extends TabbedPaneLayout {
            @Override
            protected void calculateTabRects(int placement, int tabCount) {
                super.calculateTabRects(placement, tabCount);
                if (tabCount == 0 || (placement != TOP && placement != BOTTOM)) {
                    return;
                }
                Insets ins = tabPane.getInsets();
                Insets area = getTabAreaInsets(placement);
                int kiri = ins.left + area.left;
                int lebar = tabPane.getWidth() - ins.left - ins.right - area.left - area.right;
                for (int r = 0; r < runCount; r++) {
                    int awal = tabRuns[r];
                    int akhir = lastTabInRun(tabCount, r);
                    int lebarRun = rects[akhir].x + rects[akhir].width - rects[awal].x;
                    int geser = kiri + (lebar - lebarRun) / 2 - rects[awal].x;
                    if (geser > 0) {
                        for (int i = awal; i <= akhir; i++) {
                            rects[i].x += geser;
                        }
                    }
                }
            }
        }

        @Override
        public void paint(Graphics g, JComponent c) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            if (tabPane.getTabCount() > 0) {
                getTabBounds(tabPane, 0);      
                gambarBingkai(g2);            
            }
            lewatiBingkai = true;
            try {
                super.paint(g, c);
            } finally {
                lewatiBingkai = false;
            }
        }

        @Override
        protected void paintContentBorder(Graphics g, int placement, int selectedIndex) {
            if (!lewatiBingkai) {
                gambarBingkai((Graphics2D) g);
            }
        }

        private void gambarBingkai(Graphics2D g2) {
            if (!TAMPIL_BINGKAI) {
                return;  
            }
            Insets ins = tabPane.getInsets();
            int placement = tabPane.getTabPlacement();
            int x = ins.left, y = ins.top;
            int w = tabPane.getWidth() - ins.left - ins.right;
            int h = tabPane.getHeight() - ins.top - ins.bottom;
            int th = calculateTabAreaHeight(placement, runCount, maxTabHeight);
            int tw = calculateTabAreaWidth(placement, runCount, maxTabWidth);
            Insets area = getTabAreaInsets(placement);

            switch (placement) {
                case BOTTOM: h -= th - (area.top + maxTabHeight / 2); break;
                case LEFT:   { int d = tw - (area.right + maxTabWidth / 2); x += d; w -= d; } break;
                case RIGHT:  w -= tw - (area.left + maxTabWidth / 2); break;
                default:     { int d = th - area.bottom - maxTabHeight / 2; y += d; h -= d; }
            }
            g2.setColor(BINGKAI);
            g2.setStroke(new BasicStroke(1f));
            g2.draw(new RoundRectangle2D.Float(x + 0.5f, y + 0.5f, w - 1, h - 1, ARC_BINGKAI, ARC_BINGKAI));
        }

        @Override
        protected void paintTabArea(Graphics g, int placement, int selectedIndex) {
            Graphics2D g2 = (Graphics2D) g.create();
            try {
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int n = tabPane.getTabCount();
                for (int r = 0; r < runCount; r++) {
                    int awal = tabRuns[r];
                    int akhir = lastTabInRun(n, r);
                    Rectangle a = new Rectangle(rects[awal]);
                    for (int i = awal + 1; i <= akhir; i++) {
                        a = a.union(rects[i]);
                    }
                    RoundRectangle2D pil = new RoundRectangle2D.Float(a.x + 0.5f, a.y + 0.5f,
                            a.width - 1, a.height - 1, ARC_TRACK, ARC_TRACK);
                    g2.setColor(TRACK);
                    g2.fill(pil);
                    g2.setColor(TRACK_GARIS);
                    g2.draw(pil);
                }
            } finally {
                g2.dispose();
            }
            super.paintTabArea(g, placement, selectedIndex);
        }

        @Override
        protected void paintTabBackground(Graphics g, int placement, int tabIndex,
                                          int x, int y, int w, int h, boolean isSelected) {
            Graphics2D g2 = (Graphics2D) g.create();
            try {
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (isSelected) {
                    RoundRectangle2D r = new RoundRectangle2D.Float(x + 2, y + 2, w - 4, h - 4, ARC_PILIH, ARC_PILIH);
                    g2.setColor(BAYANGAN);
                    g2.fill(new RoundRectangle2D.Float(x + 2, y + 3, w - 4, h - 4, ARC_PILIH, ARC_PILIH));
                    g2.setColor(PILIH);
                    g2.fill(r);
                    g2.setColor(PILIH_GARIS);
                    g2.setStroke(new BasicStroke(0.8f));
                    g2.draw(r);
                } else if (tabIndex == getRolloverTab() && tabPane.isEnabledAt(tabIndex)) {
                    g2.setColor(HOVER);
                    g2.fill(new RoundRectangle2D.Float(x + 2, y + 2, w - 4, h - 4, ARC_PILIH, ARC_PILIH));
                }
            } finally {
                g2.dispose();
            }
        }

        @Override
        protected void paintTabBorder(Graphics g, int placement, int tabIndex,
                                      int x, int y, int w, int h, boolean isSelected) {
            int n = tabPane.getTabCount();
            int run = getRunForTab(n, tabIndex);
            if (isSelected || tabIndex == lastTabInRun(n, run)) {
                return;
            }
            int berikut = tabIndex + 1;
            if (berikut < n && berikut == tabPane.getSelectedIndex()) {
                return;
            }
            g.setColor(PEMISAH);
            if (placement == LEFT || placement == RIGHT) {
                g.drawLine(x + 6, y + h - 1, x + w - 7, y + h - 1);
            } else {
                g.drawLine(x + w - 1, y + 6, x + w - 1, y + h - 7);
            }
        }

        @Override
        protected void paintText(Graphics g, int placement, Font font, FontMetrics metrics,
                                 int tabIndex, String title, Rectangle textRect, boolean isSelected) {
            View v = getTextViewForTab(tabIndex);
            if (v != null) {
                super.paintText(g, placement, font, metrics, tabIndex, title, textRect, isSelected);
                return;
            }
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g2.setFont(font);
            g2.setColor(tabPane.isEnabled() && tabPane.isEnabledAt(tabIndex) ? TEKS : TEKS_OFF);
            BasicGraphicsUtils.drawStringUnderlineCharAt(g2, title,
                    tabPane.getDisplayedMnemonicIndexAt(tabIndex),
                    textRect.x, textRect.y + metrics.getAscent());
        }

        @Override
        protected int getTabLabelShiftX(int placement, int tabIndex, boolean isSelected) {
            return 0;
        }

        @Override
        protected int getTabLabelShiftY(int placement, int tabIndex, boolean isSelected) {
            return 0;
        }

        @Override
        protected void paintFocusIndicator(Graphics g, int placement, Rectangle[] rects,
                                           int tabIndex, Rectangle iconRect, Rectangle textRect,
                                           boolean isSelected) {
        }
    }
}
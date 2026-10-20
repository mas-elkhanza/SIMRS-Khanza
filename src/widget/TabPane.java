package widget;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import javax.swing.JComponent;
import javax.swing.JTabbedPane;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicGraphicsUtils;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import javax.swing.text.View;

public class TabPane extends JTabbedPane {

    private static final long serialVersionUID = 4L;
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

        private static final Color GARIS_DASAR = new Color(255, 255, 255);   
        private static final Color GARIS_PILIH = new Color(22, 160, 93);     
        private static final Color TEKS_PILIH  = new Color(50, 50, 50);      
        private static final Color TEKS_HOVER  = new Color(22, 120, 70);     
        private static final Color TEKS        = new Color(112, 128, 118);   
        private static final Color TEKS_OFF    = new Color(190, 196, 192);   
        private static final int   TEBAL_PILIH = 1;                          
        private static final int   SISI_GARIS  = 4;                          

        public static ComponentUI createUI(JComponent c) {
            return new MacTabUI();
        }

        @Override
        protected void installDefaults() {
            super.installDefaults();
            tabInsets = new Insets(1, 8, 1, 8);            
            selectedTabPadInsets = new Insets(0, 0, 0, 0);
            tabAreaInsets = new Insets(0, 4, 0, 4);          
            contentBorderInsets = new Insets(0, 0, 0, 0);
            tabRunOverlay = 0;
        }

        @Override
        protected Insets getContentBorderInsets(int placement) {
            return new Insets(0, 0, 0, 0);
        }

        @Override
        protected int calculateTabHeight(int placement, int tabIndex, int fontHeight) {
            return Math.max(super.calculateTabHeight(placement, tabIndex, fontHeight), 26);
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
        protected void paintContentBorder(Graphics g, int placement, int selectedIndex) {
        }

        @Override
        protected void paintTabArea(Graphics g, int placement, int selectedIndex) {
            int n = tabPane.getTabCount();
            if (n > 0) {
                Insets ins = tabPane.getInsets();
                int w = tabPane.getWidth() - ins.left - ins.right;
                int h = tabPane.getHeight() - ins.top - ins.bottom;
                g.setColor(GARIS_DASAR);
                switch (placement) {
                    case BOTTOM: {
                        int y = Integer.MAX_VALUE;
                        for (int i = 0; i < n; i++) {
                            y = Math.min(y, rects[i].y);
                        }
                        g.fillRect(ins.left, y, w, 1);
                        break;
                    }
                    case LEFT: {
                        int x = 0;
                        for (int i = 0; i < n; i++) {
                            x = Math.max(x, rects[i].x + rects[i].width);
                        }
                        g.fillRect(x - 1, ins.top, 1, h);
                        break;
                    }
                    case RIGHT: {
                        int x = Integer.MAX_VALUE;
                        for (int i = 0; i < n; i++) {
                            x = Math.min(x, rects[i].x);
                        }
                        g.fillRect(x, ins.top, 1, h);
                        break;
                    }
                    default: {
                        int y = 0;
                        for (int i = 0; i < n; i++) {
                            y = Math.max(y, rects[i].y + rects[i].height);
                        }
                        g.fillRect(ins.left, y - 1, w, 1);
                    }
                }
            }
            super.paintTabArea(g, placement, selectedIndex);
        }

        @Override
        protected void paintTabBackground(Graphics g, int placement, int tabIndex,
                                          int x, int y, int w, int h, boolean isSelected) {
        }

        @Override
        protected void paintTabBorder(Graphics g, int placement, int tabIndex,
                                      int x, int y, int w, int h, boolean isSelected) {
            if (!isSelected) {
                return;
            }
            g.setColor(GARIS_PILIH);
            switch (placement) {
                case BOTTOM:
                    g.fillRect(x + SISI_GARIS, y, w - SISI_GARIS * 2, TEBAL_PILIH);
                    break;
                case LEFT:
                    g.fillRect(x + w - TEBAL_PILIH, y + SISI_GARIS, TEBAL_PILIH, h - SISI_GARIS * 2);
                    break;
                case RIGHT:
                    g.fillRect(x, y + SISI_GARIS, TEBAL_PILIH, h - SISI_GARIS * 2);
                    break;
                default:
                    g.fillRect(x + SISI_GARIS, y + h - TEBAL_PILIH, w - SISI_GARIS * 2, TEBAL_PILIH);
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
            Color warna;
            if (!tabPane.isEnabled() || !tabPane.isEnabledAt(tabIndex)) {
                warna = TEKS_OFF;
            } else if (isSelected) {
                warna = TEKS_PILIH;
            } else if (tabIndex == getRolloverTab()) {
                warna = TEKS_HOVER;
            } else {
                warna = TEKS;
            }
            g2.setColor(warna);
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
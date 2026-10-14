package widget;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.Action;
import javax.swing.ButtonModel;
import javax.swing.Icon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicMenuUI;

public class Menu extends JMenu {

    private static final long serialVersionUID = 1L;

    static final Color AKSEN       = new Color(22, 160, 93);     
    static final Color PILIH_PASIF = new Color(220, 234, 226);
    static final Color TEKS        = new Color(50, 50, 50);
    static final Color SHORTCUT    = new Color(120, 120, 120);
    static final Color LATAR       = new Color(255, 255, 254);  
    static final Font  FONT        = new Font("Tahoma", Font.PLAIN, 11);
    static final Border PADDING    = new EmptyBorder(0, 6, 0, 6);
    static final int   TINGGI_MIN  = 24;
    static final int   R           = 4;   
    static final Color PANAH       = new Color(140, 140, 140);
    static final Color PILL_ATAS   = new Color(255, 255, 255, 50);  

    private static final BufferedImage PANAH_ABU   = buatPanah(PANAH);
    private static final BufferedImage PANAH_PUTIH = buatPanah(Color.WHITE);
    private static final Icon IKON_PANAH = new IkonPanah();

    private static final BufferedImage SUDUT_AKTIF = buatSudut(AKSEN);
    private static final BufferedImage SUDUT_PASIF = buatSudut(PILIH_PASIF);

    private static BufferedImage buatSudut(Color c) {
        BufferedImage img = new BufferedImage(R * 2, R * 2, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(c);
        g.fillOval(0, 0, R * 2, R * 2);
        g.dispose();
        return img;
    }

    private static void gambarPil(Graphics g, int x, int y, int w, int h, boolean aktif) {
        if (w < R * 2 || h < R * 2) {
            return;
        }
        BufferedImage s = aktif ? SUDUT_AKTIF : SUDUT_PASIF;
        g.setColor(aktif ? AKSEN : PILIH_PASIF);
        g.fillRect(x + R, y, w - R * 2, h);
        g.fillRect(x, y + R, R, h - R * 2);
        g.fillRect(x + w - R, y + R, R, h - R * 2);
        int x2 = x + w - R;
        int y2 = y + h - R;
        g.drawImage(s, x, y, x + R, y + R, 0, 0, R, R, null);
        g.drawImage(s, x2, y, x + w, y + R, R, 0, R * 2, R, null);
        g.drawImage(s, x, y2, x + R, y + h, 0, R, R, R * 2, null);
        g.drawImage(s, x2, y2, x + w, y + h, R, R, R * 2, R * 2, null);
    }

    private static BufferedImage buatPanah(Color c) {
        BufferedImage img = new BufferedImage(6, 10, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        g.setColor(c);
        g.setStroke(new BasicStroke(1.4f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g.drawLine(1, 1, 4, 5);
        g.drawLine(4, 5, 1, 9);
        g.dispose();
        return img;
    }

    public Menu() {
        super();
        gaya();
    }

    public Menu(String s) {
        super(s);
        gaya();
    }

    public Menu(String s, boolean b) {
        super(s, b);
        gaya();
    }

    public Menu(Action a) {
        super(a);
        gaya();
    }

    private void gaya() {
        setFont(FONT);
        setForeground(TEKS);
        setBackground(LATAR);
        setIconTextGap(6);
        setBorder(PADDING);
    }

    @Override
    public void updateUI() {
        setUI(new ModernMenuUI());
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension d = super.getPreferredSize();
        if (!isPreferredSizeSet() && !isTopLevelMenu() && d.height < TINGGI_MIN) {
            d.height = TINGGI_MIN;
        }
        return d;
    }

    private static boolean terangkat(JMenuItem item) {
        ButtonModel m = item.getModel();
        return m.isArmed() || m.isSelected();
    }

    private static class ModernMenuUI extends BasicMenuUI {

        @Override
        protected void installDefaults() {
            super.installDefaults();
            selectionForeground = Color.WHITE;
            acceleratorForeground = SHORTCUT;
            acceleratorSelectionForeground = Color.WHITE;
            checkIcon = null;          
            arrowIcon = IKON_PANAH;    
        }

        @Override
        protected void paintBackground(Graphics g, JMenuItem item, Color bgColor) {
            int w = item.getWidth();
            int h = item.getHeight();
            boolean atas = ((JMenu) item).isTopLevelMenu();

            if (atas) {
                Component p = item.getParent();
                if (item.isOpaque()) {
                    g.setColor(item.getBackground());
                    g.fillRect(0, 0, w, h);
                }
                if (!(p instanceof MenuBar) && p instanceof JMenuBar && terangkat(item)) {
                    Graphics2D g2 = (Graphics2D) g;
                    Object aa = g2.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(PILL_ATAS);
                    g2.fillRoundRect(2, 2, w - 4, h - 4, 8, 8);
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, aa);
                }
            } else {
                if (item.isOpaque()) {
                    g.setColor(item.getBackground());
                    g.fillRect(0, 0, w, h);
                }
                if (terangkat(item)) {
                    gambarPil(g, 3, 1, w - 6, h - 2, item.isEnabled());
                }
            }
            g.setColor(item.getForeground());
        }

        @Override
        protected void paintText(Graphics g, JMenuItem item, Rectangle textRect, String text) {
            Color simpan = selectionForeground;
            if (((JMenu) item).isTopLevelMenu()) {
                selectionForeground = item.getForeground();
            }
            super.paintText(g, item, textRect, text);
            selectionForeground = simpan;
        }
    }

    private static class IkonPanah implements Icon {

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            boolean putih = c instanceof JMenuItem && terangkat((JMenuItem) c) && c.isEnabled();
            g.drawImage(putih ? PANAH_PUTIH : PANAH_ABU, x, y, null);
        }

        @Override
        public int getIconWidth() {
            return 6;
        }

        @Override
        public int getIconHeight() {
            return 10;
        }
    }
}
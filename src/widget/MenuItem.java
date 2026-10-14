package widget;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.Action;
import javax.swing.ButtonModel;
import javax.swing.Icon;
import javax.swing.JMenuItem;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicMenuItemUI;

public class MenuItem extends JMenuItem {
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

    static void gambarPil(Graphics g, int x, int y, int w, int h, boolean aktif) {
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

    public MenuItem() {
        super();
        gaya();
    }

    public MenuItem(String text) {
        super(text);
        gaya();
    }

    public MenuItem(Icon icon) {
        super(icon);
        gaya();
    }

    public MenuItem(String text, Icon icon) {
        super(text, icon);
        gaya();
    }

    public MenuItem(String text, int mnemonic) {
        super(text, mnemonic);
        gaya();
    }

    public MenuItem(Action a) {
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
        setUI(new ModernMenuItemUI());
    }

    
    @Override
    public Dimension getPreferredSize() {
        Dimension d = super.getPreferredSize();
        if (!isPreferredSizeSet() && d.height < TINGGI_MIN) {
            d.height = TINGGI_MIN;
        }
        return d;
    }


    private static class ModernMenuItemUI extends BasicMenuItemUI {

        @Override
        protected void installDefaults() {
            super.installDefaults();
            selectionForeground = Color.WHITE;
            acceleratorForeground = SHORTCUT;
            acceleratorSelectionForeground = Color.WHITE;
            checkIcon = null;
            arrowIcon = null;
        }

        @Override
        protected void paintBackground(Graphics g, JMenuItem item, Color bgColor) {
            int w = item.getWidth();
            int h = item.getHeight();
            if (item.isOpaque()) {
                g.setColor(item.getBackground());
                g.fillRect(0, 0, w, h);
            }
            ButtonModel m = item.getModel();
            if (m.isArmed() || m.isSelected()) {
                gambarPil(g, 3, 1, w - 6, h - 2, item.isEnabled());
            }
            g.setColor(item.getForeground());
        }
    }
}
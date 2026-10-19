package widget;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Window;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.awt.image.BufferedImage;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;
import javax.swing.border.AbstractBorder;
import javax.swing.plaf.SeparatorUI;
import javax.swing.plaf.basic.BasicPopupMenuUI;

public class PopupMenu extends JPopupMenu {
    private static final long serialVersionUID = 1L;
    static final int   TEMBUS = 228;
    static final Color LATAR  = new Color(255, 255, 254, TEMBUS);
    static final Color LATAR_PEJAL = new Color(255, 255, 254);
    static final Color GARIS  = new Color(22, 160, 93, 40);   
    static final int   ARC    = 18;                           
    private static final String KUNCI = "widget.PopupMenu.gaya";
    private static final String KOTAK = "widget.PopupMenu.kotak";

    static final Color PEMISAH = new Color(228, 239, 232);  

    private static final AbstractBorder BORDER = new BorderPopup();
    private static final SeparatorUI UI_PEMISAH = new PemisahUI();
    private static final ContainerAdapter PEMISAH_BARU = new ContainerAdapter() {
        @Override
        public void componentAdded(ContainerEvent e) {
            tembuskan(e.getChild());
        }
    };
    private static final HierarchyListener PASANG = new HierarchyListener() {
        @Override
        public void hierarchyChanged(HierarchyEvent e) {
            if ((e.getChangeFlags() & (HierarchyEvent.PARENT_CHANGED | HierarchyEvent.SHOWING_CHANGED)) != 0
                    && e.getComponent() instanceof JComponent) {
                aturJendela((JComponent) e.getComponent());
            }
        }
    };

    public PopupMenu() {
        super();
    }

    public PopupMenu(String label) {
        super(label);
    }

    @Override
    public void setVisible(boolean b) {
        if (b && !diDesigner(null)) {
            gayakanBilaAman(this, getInvoker());
        }
        super.setVisible(b);
    }

    public static void gayakanBilaAman(JPopupMenu p, Component pemicu) {
        if (p == null || Boolean.TRUE.equals(p.getClientProperty(KUNCI))) {
            return;
        }
        if (diDesigner(pemicu) || diDesigner(p)) {
            return;
        }
        try {
            gayakan(p);
        } catch (RuntimeException e) {
        }
    }

    public static boolean diDesigner(Component c) {
        if (java.beans.Beans.isDesignTime()) {
            return true;
        }
        ClassLoader cl = PopupMenu.class.getClassLoader();  
        String nl = cl == null ? "" : cl.getClass().getName();
        if (nl.contains("netbeans") || nl.contains("openide")) {
            return true;
        }
        for (int i = 0; c != null && i < 60; i++) {
            String n = c.getClass().getName();
            if (n.startsWith("org.netbeans.") || n.startsWith("org.openide.")) {
                return true;
            }
            c = (c instanceof JPopupMenu && ((JPopupMenu) c).getInvoker() != null)
                    ? ((JPopupMenu) c).getInvoker() : c.getParent();
        }
        return false;
    }

    private static void gayakan(JPopupMenu p) {
        p.putClientProperty(KUNCI, Boolean.TRUE);
        p.setUI(new BasicPopupMenuUI());   
        p.setOpaque(false);                
        p.setBackground(LATAR);
        p.setBorder(BORDER);
        p.addHierarchyListener(PASANG);
        p.addContainerListener(PEMISAH_BARU);
        for (Component k : p.getComponents()) {
            tembuskan(k);
        }
    }

    private static void tembuskan(Component k) {
        if (k instanceof JSeparator) {
            ((JSeparator) k).setUI(UI_PEMISAH);
        } else if (k instanceof JMenuItem) {
            ((JMenuItem) k).setOpaque(false);
        }
    }

    private static void aturJendela(JComponent p) {
        Window w = SwingUtilities.getWindowAncestor(p);
        boolean jendelaSendiri = w instanceof JWindow
                && p.getParent() == ((JWindow) w).getContentPane();
        if (!jendelaSendiri) {
            p.putClientProperty(KOTAK, null);     
            return;
        }
        if (bisaTransparan(w)) {
            JWindow jw = (JWindow) w;
            if (jw.getBackground() == null || jw.getBackground().getAlpha() != 0) {
                jw.setBackground(new Color(0, 0, 0, 0));
            }
            Container isi = jw.getContentPane();
            if (isi instanceof JComponent) {
                ((JComponent) isi).setOpaque(false);
            }
            jw.getRootPane().setOpaque(false);
            jw.getRootPane().putClientProperty("Window.shadow", Boolean.FALSE);
            p.putClientProperty(KOTAK, null);
        } else {
            p.putClientProperty(KOTAK, Boolean.TRUE);
        }
    }

    private static boolean bisaTransparan(Window w) {
        try {
            String os = System.getProperty("os.name", "").toLowerCase();
            if (os.contains("linux") || os.contains("bsd") || os.contains("sunos")) {
                return false;      
            }
            GraphicsDevice gd = w.getGraphicsConfiguration().getDevice();
            return gd.isWindowTranslucencySupported(GraphicsDevice.WindowTranslucency.PERPIXEL_TRANSLUCENT);
        } catch (RuntimeException e) {
            return false;
        }
    }

    private static final class BorderPopup extends AbstractBorder {

        private static final long serialVersionUID = 2L;
        private static final int R = ARC / 2;                     
        private static final BufferedImage SUDUT = buatSudut();   

        private static BufferedImage buatSudut() {
            BufferedImage img = new BufferedImage(R * 2, R * 2, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = img.createGraphics();
            gambarHalus(g2, LATAR, 0, 0, R * 2, R * 2);
            g2.dispose();
            return img;
        }

        private static void gambarHalus(Graphics2D g2, Color latar, int x, int y, int w, int h) {
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(latar);
            g2.fill(new RoundRectangle2D.Float(x, y, w, h, ARC, ARC));
            g2.setStroke(new BasicStroke(1f));
            g2.setColor(GARIS);
            g2.draw(new RoundRectangle2D.Float(x + 0.5f, y + 0.5f, w - 1, h - 1, ARC, ARC));
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
            boolean kotak = c instanceof JComponent
                    && Boolean.TRUE.equals(((JComponent) c).getClientProperty(KOTAK));
            Color latar = c.getBackground() != null ? c.getBackground() : LATAR;
            if (kotak) {
                g.setColor(latar.getAlpha() == 255 ? latar : LATAR_PEJAL);   
                g.fillRect(x, y, w, h);
                g.setColor(GARIS);
                g.drawRect(x, y, w - 1, h - 1);
                return;
            }
            if (!LATAR.equals(latar) || w < R * 2 || h < R * 2) {
                Graphics2D g2 = (Graphics2D) g.create();
                try {
                    gambarHalus(g2, latar, x, y, w, h);
                } finally {
                    g2.dispose();
                }
                return;
            }
            
            g.setColor(latar);
            g.fillRect(x + R, y, w - R * 2, h);
            g.fillRect(x, y + R, R, h - R * 2);
            g.fillRect(x + w - R, y + R, R, h - R * 2);
            
            g.setColor(GARIS);
            g.fillRect(x + R, y, w - R * 2, 1);
            g.fillRect(x + R, y + h - 1, w - R * 2, 1);
            g.fillRect(x, y + R, 1, h - R * 2);
            g.fillRect(x + w - 1, y + R, 1, h - R * 2);
            
            int x2 = x + w - R;
            int y2 = y + h - R;
            g.drawImage(SUDUT, x, y, x + R, y + R, 0, 0, R, R, null);
            g.drawImage(SUDUT, x2, y, x + w, y + R, R, 0, R * 2, R, null);
            g.drawImage(SUDUT, x, y2, x + R, y + h, 0, R, R, R * 2, null);
            g.drawImage(SUDUT, x2, y2, x + w, y + h, R, R, R * 2, R * 2, null);
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(6, 4, 6, 4);
        }

        @Override
        public Insets getBorderInsets(Component c, Insets insets) {
            insets.set(6, 4, 6, 4);
            return insets;
        }
    }

    private static final class PemisahUI extends SeparatorUI {

        @Override
        public void paint(Graphics g, JComponent c) {
            g.setColor(PEMISAH);
            g.fillRect(8, c.getHeight() / 2, c.getWidth() - 16, 1);
        }

        @Override
        public Dimension getPreferredSize(JComponent c) {
            return new Dimension(0, 9);
        }

        @Override
        public Dimension getMaximumSize(JComponent c) {
            return new Dimension(Short.MAX_VALUE, 9);  
        }
    }
}
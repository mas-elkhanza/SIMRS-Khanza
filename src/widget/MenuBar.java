package widget;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import javax.swing.BorderFactory;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.event.ChangeListener;

public class MenuBar extends JMenuBar {

    private static final long serialVersionUID = 2L;
    public enum Tema {
        EXCEL(new Color(69, 122, 85), new Color(59, 114, 75), new Color(47, 95, 62), Color.WHITE, 10),
        HIJAU(new Color(0x1CB06C), new Color(0x0B8A4F), new Color(0x077242), Color.WHITE, 45),
        BIRU (new Color(0x3C8FF0), new Color(0x1D6DCE), new Color(0x165AAD), Color.WHITE, 45),
        UNGU (new Color(0x8C5FF6), new Color(0x6A40DA), new Color(0x5530B8), Color.WHITE, 45),
        GELAP(new Color(0x2D323A), new Color(0x1E2127), new Color(0x3DDC84), new Color(0xE8ECF1), 20);

        final Color atas, bawah, garis, teks;
        final int kilau;   // intensitas efek glass (0 = flat)

        Tema(Color atas, Color bawah, Color garis, Color teks, int kilau) {
            this.atas = atas;
            this.bawah = bawah;
            this.garis = garis;
            this.teks = teks;
            this.kilau = kilau;
        }
    }

    private static final int ARC = 10;
    private static final int INSET = 2;

    private Color warnaAtas, warnaBawah, warnaGaris, warnaTeks;
    private int kilau = 45;
    private JMenu menuHover;

    private final MouseAdapter hoverHandler = new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent e) {
            menuHover = (JMenu) e.getSource();
            repaint();
        }

        @Override
        public void mouseExited(MouseEvent e) {
            if (menuHover == e.getSource()) {
                menuHover = null;
                repaint();
            }
        }
    };

    private final ChangeListener modelHandler = e -> repaint();

    public MenuBar() {
        super();
        setOpaque(true);
        setBorderPainted(false);
        setBorder(BorderFactory.createEmptyBorder(5, 6, 6, 6));
        setTema(Tema.HIJAU);

        addContainerListener(new ContainerAdapter() {
            @Override
            public void componentAdded(ContainerEvent e) {
                pasangEfek(e.getChild());
            }

            @Override
            public void componentRemoved(ContainerEvent e) {
                lepasEfek(e.getChild());
            }
        });
    }

    private void pasangEfek(Component c) {
        if (!(c instanceof JMenu)) {
            return;
        }
        JMenu m = (JMenu) c;
        m.addMouseListener(hoverHandler);
        m.addChangeListener(modelHandler);
        m.setOpaque(false);
        m.setForeground(warnaTeks);
        m.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    private void lepasEfek(Component c) {
        if (c instanceof JMenu) {
            JMenu m = (JMenu) c;
            m.removeMouseListener(hoverHandler);
            m.removeChangeListener(modelHandler);
            if (menuHover == m) {
                menuHover = null;
            }
        }
    }


    @Override
    protected void paintComponent(Graphics g) {
        if (!isOpaque()) {
            super.paintComponent(g);
            return;
        }
        int w = getWidth();
        int h = getHeight();
        if (w <= 0 || h <= 0) {
            return;
        }

        Graphics2D g2 = (Graphics2D) g.create();
        try {
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setPaint(new GradientPaint(0, 0, warnaAtas, 0, h, warnaBawah));
            g2.fillRect(0, 0, w, h);
            g2.setPaint(new GradientPaint(0, 0, new Color(255, 255, 255, kilau),0, h / 2f, new Color(255, 255, 255, 0)));
            g2.fillRect(0, 0, w, h / 2);
            g2.setColor(new Color(255, 255, 255, Math.min(60, kilau + 15)));
            g2.drawLine(0, 0, w, 0);
            g2.setColor(warnaGaris);
            g2.drawLine(0, h - 1, w, h - 1);

            for (Component c : getComponents()) {
                if (!(c instanceof JMenu) || !c.isVisible()) {
                    continue;
                }
                JMenu m = (JMenu) c;
                boolean terbuka = m.isSelected();
                if (!terbuka && m != menuHover) {
                    continue;
                }
                Rectangle r = m.getBounds();
                RoundRectangle2D pill = new RoundRectangle2D.Float(
                        r.x + INSET, r.y + INSET,
                        r.width - INSET * 2, r.height - INSET * 2, ARC, ARC);
                g2.setColor(new Color(255, 255, 255, terbuka ? 70 : 38));
                g2.fill(pill);
                g2.setColor(new Color(255, 255, 255, terbuka ? 110 : 70));
                g2.draw(pill);
            }
        } finally {
            g2.dispose();
        }
    }

    public void setTema(Tema tema) {
        warnaAtas = tema.atas;
        warnaBawah = tema.bawah;
        warnaGaris = tema.garis;
        warnaTeks = tema.teks;
        kilau = tema.kilau;
        for (Component c : getComponents()) {
            if (c instanceof JMenu) {
                c.setForeground(warnaTeks);
            }
        }
        repaint();
    }

    public Color getWarnaAtas()  { return warnaAtas; }
    public Color getWarnaBawah() { return warnaBawah; }
    public Color getWarnaGaris() { return warnaGaris; }

    public void setWarnaAtas(Color c)  { warnaAtas = c;  repaint(); }
    public void setWarnaBawah(Color c) { warnaBawah = c; repaint(); }
    public void setWarnaGaris(Color c) { warnaGaris = c; repaint(); }
}
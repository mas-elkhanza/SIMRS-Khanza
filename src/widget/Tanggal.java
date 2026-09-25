package widget;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Rectangle;
import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Path2D;
import java.awt.geom.RoundRectangle2D;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.border.AbstractBorder;
import javax.swing.text.JTextComponent;
import uz.ncipro.calendar.JDateTimePicker;

public final class Tanggal extends JDateTimePicker {
    private static final long serialVersionUID = 2L;
    private static final Color AKSEN     = new Color(0x16A05D);
    private static final Color BORDER    = new Color(0xA7B6AD);
    private static final Color NONAKTIF  = new Color(0xFAFCFB);
    private static final Color PEMISAH   = new Color(0xD3DDD7);
    private static final Color TINT      = new Color(0x16, 0xA0, 0x5D, 30);  
    private static final Color BENING    = new Color(0, 0, 0, 0);            
    private static final Color LATAR_PANAH = Color.WHITE;
    private static final Color TEKS      = new Color(0x1C2520);
    private static final int   RADIUS    = 6;
    private static final String KUNCI    = "widget.Tanggal.digayakan";

    private boolean hover;
    private boolean hoverPanah;

    public Tanggal() {
        super();
        setForeground(TEKS);
        setBackground(BENING);   
        setFont(new Font("Tahoma", Font.PLAIN, 11));
        gayakan();

        addContainerListener(new ContainerAdapter() {
            @Override
            public void componentAdded(ContainerEvent e) {
                gayakanAnak(e.getChild());
            }
        });
    }

    @Override
    public void updateUI() {
        super.updateUI();
        gayakan();
    }

    private void gayakan() {
        setOpaque(false);
        setBorder(new BorderModern());
        for (Component c : getComponents()) {
            gayakanAnak(c);
        }
        repaint();
    }

    private void gayakanAnak(Component c) {
        if (!(c instanceof JComponent)) {
            return;
        }
        JComponent jc = (JComponent) c;
        boolean sudah = Boolean.TRUE.equals(jc.getClientProperty(KUNCI));

        if (c instanceof AbstractButton) {                 
            AbstractButton b = (AbstractButton) c;
            b.setBackground(BENING);
            b.setOpaque(false);
            b.setContentAreaFilled(false);
            b.setBorderPainted(false);
            b.setBorder(BorderFactory.createEmptyBorder());
            b.setFocusable(false);
            b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            if (!sudah) {
                b.addMouseListener(hoverTombol(b));
                b.addMouseListener(hoverKomponen());
            }
        } else if (c instanceof JTextComponent) {          
            JTextComponent t = (JTextComponent) c;
            t.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 4));
            t.setBackground(BENING);
            t.setOpaque(false);
            t.setForeground(TEKS);
            t.setSelectionColor(new Color(0xCDEBDA));
            t.setSelectedTextColor(new Color(0x0E3B24));
            t.setCaretColor(AKSEN);
            if (!sudah) {
                t.addFocusListener(fokusRepaint());
                t.addMouseListener(hoverKomponen());
            }
        }
        jc.putClientProperty(KUNCI, Boolean.TRUE);
    }

    private MouseListener hoverTombol(final AbstractButton b) {
        return new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                hoverPanah = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hoverPanah = false;
                repaint();
            }
        };
    }

    private transient FocusListener fokusRepaintCache;
    private transient MouseListener hoverKomponenCache;

    private FocusListener fokusRepaint() {
        if (fokusRepaintCache == null) {
            fokusRepaintCache = new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) { repaint(); }

                @Override
                public void focusLost(FocusEvent e) { repaint(); }
            };
        }
        return fokusRepaintCache;
    }

    private MouseListener hoverKomponen() {
        if (hoverKomponenCache == null) {
            hoverKomponenCache = new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) { hover = true; repaint(); }

                @Override
                public void mouseExited(MouseEvent e) { hover = false; repaint(); }
            };
        }
        return hoverKomponenCache;
    }

    @Override
    public void setEnabled(boolean b) {
        super.setEnabled(b);
        for (Component c : getComponents()) {
            if (c instanceof AbstractButton) {
                c.setBackground(BENING);
            } else if (c instanceof JTextComponent) {
                c.setBackground(BENING);
            }
        }
        repaint();
    }

    private boolean fokus() {
        if (!isEnabled()) {
            return false;
        }
        if (isFocusOwner()) {
            return true;
        }
        for (Component c : getComponents()) {
            if (c.isFocusOwner()) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        AbstractButton b = tombolPanah();
        if (b == null || !b.isVisible() || b.getWidth() <= 0) {
            return;
        }
        Graphics2D g2 = (Graphics2D) g.create();
        try {
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            Rectangle r = b.getBounds();
            
            g2.clip(new RoundRectangle2D.Float(2, 2, getWidth() - 4, getHeight() - 4,
                    RADIUS * 2f - 2, RADIUS * 2f - 2));
            g2.clipRect(r.x, r.y, r.width, r.height);
            g2.setColor(isEnabled() ? LATAR_PANAH : NONAKTIF);
            g2.fillRect(r.x, r.y, r.width, r.height);
            if (hoverPanah && isEnabled()) {
                g2.setColor(TINT);
                g2.fillRect(r.x, r.y, r.width, r.height);
            }
            
            g2.setColor(PEMISAH);
            g2.drawLine(r.x, r.y + 3, r.x, r.y + r.height - 4);

            float cx = r.x + r.width / 2f + 0.5f;
            float cy = r.y + r.height / 2f + 0.5f;
            Path2D chevron = new Path2D.Float();
            chevron.moveTo(cx - 4, cy - 2);
            chevron.lineTo(cx, cy + 2);
            chevron.lineTo(cx + 4, cy - 2);
            g2.setColor(isEnabled() ? new Color(0x3E4B44) : new Color(0xB3BCB7));
            g2.setStroke(new BasicStroke(1.6f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2.draw(chevron);
        } finally {
            g2.dispose();
        }
    }

    private AbstractButton tombolPanah() {
        for (Component c : getComponents()) {
            if (c instanceof AbstractButton) {
                return (AbstractButton) c;
            }
        }
        return null;
    }

    private class BorderModern extends AbstractBorder {

        private static final long serialVersionUID = 1L;

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
            Graphics2D g2 = (Graphics2D) g.create();
            try {
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                float arc = RADIUS * 2f;
                boolean f = fokus();

                if (f) {
                    g2.setColor(new Color(AKSEN.getRed(), AKSEN.getGreen(), AKSEN.getBlue(), 60));
                    g2.setStroke(new BasicStroke(2f));
                    g2.draw(new RoundRectangle2D.Float(x + 1, y + 1, w - 2, h - 2, arc + 1, arc + 1));
                }
                Color garis = !isEnabled() ? new Color(BORDER.getRed(), BORDER.getGreen(), BORDER.getBlue(), 130)
                        : f ? AKSEN
                        : hover ? new Color(0x6F8578)
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
            return new Insets(2, 6, 2, 3);  
        }

        @Override
        public Insets getBorderInsets(Component c, Insets insets) {
            insets.set(2, 6, 2, 3);
            return insets;
        }
    }
}
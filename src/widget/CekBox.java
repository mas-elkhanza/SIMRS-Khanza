package widget;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Path2D;
import java.awt.geom.RoundRectangle2D;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonModel;
import javax.swing.Icon;
import javax.swing.JCheckBox;
import javax.swing.plaf.basic.BasicCheckBoxUI;

public class CekBox extends JCheckBox {
    private static final long serialVersionUID = 2L;
    static final Color AKSEN    = new Color(0x16A05D);
    static final Color BORDER   = new Color(0xA7B6AD);
    static final Color HOVER    = new Color(0x6F8578);
    static final Color NONAKTIF = new Color(0xF1F4F2);

    public CekBox() {
        super();
        setFont(new Font("Tahoma", Font.PLAIN, 11));
        setBackground(Color.WHITE);
        setForeground(new Color(50, 50, 50));
        setFocusPainted(false);
        setRolloverEnabled(true);
        setOpaque(false);                                        
        setBorder(javax.swing.BorderFactory.createLineBorder(new Color(239,244,234)));  
        setIconTextGap(6);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        Icon ikon = new IkonCentang();
        setIcon(ikon);
        setSelectedIcon(ikon);
        setRolloverIcon(ikon);
        setRolloverSelectedIcon(ikon);
        setPressedIcon(ikon);
        setDisabledIcon(ikon);
        setDisabledSelectedIcon(ikon);
    }

    @Override
    public void updateUI() {
        setUI(new BasicCheckBoxUI());
    }

    private static class IkonCentang implements Icon {

        private static final int UKURAN = 15;

        @Override
        public int getIconWidth()  { return UKURAN; }

        @Override
        public int getIconHeight() { return UKURAN; }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            AbstractButton b = (AbstractButton) c;
            ButtonModel m = b.getModel();
            boolean aktif   = b.isEnabled();
            boolean dipilih = m.isSelected();
            boolean ditekan = aktif && m.isArmed() && m.isPressed();
            boolean hover   = aktif && m.isRollover();
            boolean fokus   = aktif && b.isFocusOwner();

            Graphics2D g2 = (Graphics2D) g.create();
            try {
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
                g2.translate(x, y);

                float s = UKURAN;
                RoundRectangle2D kotak = new RoundRectangle2D.Float(0.5f, 0.5f, s - 1, s - 1, 5, 5);

                if (fokus) {
                    g2.setColor(new Color(AKSEN.getRed(), AKSEN.getGreen(), AKSEN.getBlue(), 70));
                    g2.setStroke(new BasicStroke(2f));
                    g2.draw(new RoundRectangle2D.Float(-1f, -1f, s + 2, s + 2, 7, 7));
                }

                if (dipilih) {
                    Color isi = !aktif ? new Color(0x9CC9B1) : ditekan ? AKSEN.darker() : AKSEN;
                    g2.setColor(isi);
                    g2.fill(kotak);

                    Path2D centang = new Path2D.Float();
                    centang.moveTo(s * 0.26f, s * 0.52f);
                    centang.lineTo(s * 0.43f, s * 0.69f);
                    centang.lineTo(s * 0.75f, s * 0.33f);
                    g2.setColor(Color.WHITE);
                    g2.setStroke(new BasicStroke(2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                    g2.draw(centang);
                } else {
                    g2.setColor(!aktif ? NONAKTIF : ditekan ? new Color(0xE3F2EA) : Color.WHITE);
                    g2.fill(kotak);
                    g2.setColor(!aktif ? new Color(0xC9D3CD) : (hover || fokus) ? HOVER : BORDER);
                    g2.setStroke(new BasicStroke(1f));
                    g2.draw(kotak);
                }
            } finally {
                g2.dispose();
            }
        }
    }
}
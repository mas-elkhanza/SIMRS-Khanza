package widget;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.Icon;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicRadioButtonUI;

public class RadioButton extends JRadioButton {

    private static final long serialVersionUID = 2L;

    static final Color AKSEN    = new Color(0x16A05D);
    static final Color BORDER   = new Color(0xA7B6AD);
    static final Color HOVER    = new Color(0x6F8578);
    static final Color NONAKTIF = new Color(0xF1F4F2);
    static final Color RING     = new Color(0x16, 0xA0, 0x5D, 70);

    private static final Icon IKON = new IkonRadio();

    public RadioButton() {
        super();
        setFont(new Font("Tahoma", Font.PLAIN, 11));
        setBackground(Color.WHITE);
        setForeground(new Color(50, 50, 50));
        setFocusPainted(false);
        setBorder(new EmptyBorder(1, 1, 1, 1));
        setOpaque(false);
        setRolloverEnabled(true);
        setIconTextGap(6);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        setIcon(IKON);
        setSelectedIcon(IKON);
        setRolloverIcon(IKON);
        setRolloverSelectedIcon(IKON);
        setPressedIcon(IKON);
        setDisabledIcon(IKON);
        setDisabledSelectedIcon(IKON);
    }

    @Override
    public void updateUI() {
        setUI(new BasicRadioButtonUI());
    }

    private static class IkonRadio implements Icon {

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
                Ellipse2D lingkaran = new Ellipse2D.Float(0.5f, 0.5f, s - 1, s - 1);

                if (fokus) {
                    g2.setColor(RING);
                    g2.setStroke(new BasicStroke(2f));
                    g2.draw(new Ellipse2D.Float(-1f, -1f, s + 2, s + 2));
                }

                if (dipilih) {
                    Color isi = !aktif ? new Color(0x9CC9B1) : ditekan ? AKSEN.darker() : AKSEN;
                    g2.setColor(isi);
                    g2.fill(lingkaran);
                    float d = 6f;
                    g2.setColor(Color.WHITE);
                    g2.fill(new Ellipse2D.Float((s - d) / 2f, (s - d) / 2f, d, d));
                } else {
                    g2.setColor(!aktif ? NONAKTIF : ditekan ? new Color(0xE3F2EA) : Color.WHITE);
                    g2.fill(lingkaran);
                    g2.setColor(!aktif ? new Color(0xC9D3CD) : (hover || fokus) ? HOVER : BORDER);
                    g2.setStroke(new BasicStroke(1f));
                    g2.draw(lingkaran);
                }
            } finally {
                g2.dispose();
            }
        }
    }
}
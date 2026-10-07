package widget;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;
import java.awt.geom.Path2D;
import java.awt.geom.RoundRectangle2D;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.InputMap;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.plaf.ActionMapUIResource;
import javax.swing.plaf.InputMapUIResource;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.border.AbstractBorder;
import javax.swing.plaf.basic.BasicComboBoxEditor;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;

@SuppressWarnings({"rawtypes", "unchecked"})
public final class ComboBox extends JComboBox {
    private static final long serialVersionUID = 2L;
    static final Color AKSEN     = new Color(0x16A05D);
    static final Color BORDER    = new Color(0xA7B6AD);
    static final Color HOVER     = new Color(0x6F8578);
    static final Color NONAKTIF  = new Color(0xFAFCFB);
    static final Color PEMISAH   = new Color(0xD3DDD7);
    static final Color TINT      = new Color(0x16, 0xA0, 0x5D, 30);  
    static final Color TEKS      = new Color(50, 50, 50);
    static final Color TEKS_OFF  = new Color(0x55625B);
    static final Color PILIH_BG  = new Color(0xD5EFE0);
    static final Color PILIH_FG  = new Color(0x0E3B24);
    static final int   RADIUS    = 6;

    private boolean hover;

    public ComboBox() {
        super();
        setFont(new Font("Tahoma", Font.PLAIN, 11));
        setBackground(Color.WHITE);
        setForeground(TEKS);
        setOpaque(false);
        setBorder(new BorderModern());
        setRenderer(new RendererItem());
        setMaximumRowCount(12);

        MouseAdapter m = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) { hover = true; repaint(); }

            @Override
            public void mouseExited(MouseEvent e) { hover = false; repaint(); }
        };
        addMouseListener(m);
        for (Component c : getComponents()) {
            c.addMouseListener(m);
        }
        FocusAdapter f = new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) { repaint(); }

            @Override
            public void focusLost(FocusEvent e) { repaint(); }
        };
        addFocusListener(f);
        getEditor().getEditorComponent().addFocusListener(f);
    }

    @Override
    public void updateUI() {
        setUI(new ModernComboUI());
    }

    private boolean fokus() {
        if (!isEnabled()) {
            return false;
        }
        return isFocusOwner() || (isEditable() && getEditor().getEditorComponent().isFocusOwner());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    private class BorderModern extends AbstractBorder {
        private static final long serialVersionUID = 1L;

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
            Graphics2D g2 = (Graphics2D) g.create();
            try {
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                float arc = RADIUS * 2f;
                boolean f = fokus() || isPopupVisible();

                if (f) {
                    g2.setColor(new Color(AKSEN.getRed(), AKSEN.getGreen(), AKSEN.getBlue(), 60));
                    g2.setStroke(new BasicStroke(2f));
                    g2.draw(new RoundRectangle2D.Float(x + 1, y + 1, w - 2, h - 2, arc + 1, arc + 1));
                }
                Color garis = !isEnabled() ? new Color(0xC9D3CD)
                        : f ? AKSEN
                        : hover ? HOVER
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
            return new Insets(2, 2, 2, 3);
        }

        @Override
        public Insets getBorderInsets(Component c, Insets insets) {
            insets.set(2, 2, 2, 3);
            return insets;
        }
    }

    private static class RendererItem extends DefaultListCellRenderer {

        private static final long serialVersionUID = 1L;

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, false);
            setBorder(BorderFactory.createEmptyBorder(0, 6, 0, 6));
            if (index >= 0) {                       
                setOpaque(true);
                setBackground(isSelected ? PILIH_BG : Color.WHITE);
                setForeground(isSelected ? PILIH_FG : TEKS);
            }
            return this;
        }

        @Override
        public Dimension getPreferredSize() {
            Dimension d = super.getPreferredSize();
            d.height = Math.max(d.height, 22);
            return d;
        }
    }

    private static class ModernComboUI extends BasicComboBoxUI {
        private static final String NEXT = "khanzaPilihBerikut";
        private static final String PREV = "khanzaPilihSebelum";

        private static final InputMap PETA_TOMBOL = buatPetaTombol();

        private static InputMap buatPetaTombol() {
            InputMap m = new InputMapUIResource();
            m.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "hidePopup");
            m.put(KeyStroke.getKeyStroke(KeyEvent.VK_PAGE_UP, 0), "pageUpPassThrough");
            m.put(KeyStroke.getKeyStroke(KeyEvent.VK_PAGE_DOWN, 0), "pageDownPassThrough");
            m.put(KeyStroke.getKeyStroke(KeyEvent.VK_HOME, 0), "homePassThrough");
            m.put(KeyStroke.getKeyStroke(KeyEvent.VK_END, 0), "endPassThrough");
            m.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), NEXT);
            m.put(KeyStroke.getKeyStroke(KeyEvent.VK_KP_DOWN, 0), NEXT);
            m.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), PREV);
            m.put(KeyStroke.getKeyStroke(KeyEvent.VK_KP_UP, 0), PREV);
            m.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, KeyEvent.ALT_DOWN_MASK), "togglePopup");
            m.put(KeyStroke.getKeyStroke(KeyEvent.VK_KP_DOWN, KeyEvent.ALT_DOWN_MASK), "togglePopup");
            m.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, KeyEvent.ALT_DOWN_MASK), "togglePopup");
            m.put(KeyStroke.getKeyStroke(KeyEvent.VK_KP_UP, KeyEvent.ALT_DOWN_MASK), "togglePopup");
            m.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), "spacePopup");
            m.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "enterPressed");
            return m;
        }

        @Override
        protected void installKeyboardActions() {
            super.installKeyboardActions();
            SwingUtilities.replaceUIInputMap(comboBox, JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT, PETA_TOMBOL);
            ActionMap am = new ActionMapUIResource();
            am.put(NEXT, new Navigasi(1));
            am.put(PREV, new Navigasi(-1));
            am.setParent(SwingUtilities.getUIActionMap(comboBox));   
            SwingUtilities.replaceUIActionMap(comboBox, am);
        }

        private static final class Navigasi extends AbstractAction {

            private static final long serialVersionUID = 1L;
            private final int arah;

            Navigasi(int arah) {
                this.arah = arah;
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!(e.getSource() instanceof JComboBox)) {
                    return;
                }
                JComboBox c = (JComboBox) e.getSource();
                int n = c.getItemCount();
                if (n == 0 || !c.isEnabled()) {
                    return;
                }
                int awal = c.getSelectedIndex();
                if (c.isPopupVisible() && c.getUI() instanceof ModernComboUI) {
                    int sorot = ((ModernComboUI) c.getUI()).listBox.getSelectedIndex();
                    if (sorot >= 0) {
                        awal = sorot;
                    }
                }
                int baru = awal < 0 ? (arah > 0 ? 0 : n - 1) : Math.max(0, Math.min(n - 1, awal + arah));
                if (baru != c.getSelectedIndex()) {
                    c.setSelectedIndex(baru);
                }
                if (c.isPopupVisible() && c.getUI() instanceof ModernComboUI) {
                    JList l = ((ModernComboUI) c.getUI()).listBox;
                    l.setSelectedIndex(baru);
                    l.ensureIndexIsVisible(baru);
                }
            }
        }

        @Override
        protected JButton createArrowButton() {
            JButton b = new TombolPanah();
            b.setName("ComboBox.arrowButton");
            return b;
        }

        @Override
        public void paintCurrentValueBackground(Graphics g, Rectangle bounds, boolean hasFocus) {
        }

        @Override
        public void paintCurrentValue(Graphics g, Rectangle bounds, boolean hasFocus) {
            ListCellRenderer r = comboBox.getRenderer();
            Component c = r.getListCellRendererComponent(listBox, comboBox.getSelectedItem(), -1, false, false);
            c.setFont(comboBox.getFont());
            c.setForeground(comboBox.isEnabled() ? comboBox.getForeground() : TEKS_OFF);
            c.setBackground(comboBox.getBackground());
            if (c instanceof JComponent) {
                ((JComponent) c).setOpaque(false);
            }
            currentValuePane.paintComponent(g, c, comboBox,
                    bounds.x, bounds.y, bounds.width, bounds.height, c instanceof JComponent);
        }

        @Override
        protected ComboPopup createPopup() {
            BasicComboPopup p = new BasicComboPopup(comboBox) {
                private static final long serialVersionUID = 1L;

                @Override
                protected JScrollPane createScroller() {
                    JScrollPane sp = super.createScroller();
                    if (sp.getVerticalScrollBar() != null) {
                        sp.getVerticalScrollBar().setUI(new ScrollPane.ModernScrollBarUI());
                    }
                    sp.setBorder(BorderFactory.createEmptyBorder());
                    return sp;
                }
            };
            p.setBorder(BorderFactory.createLineBorder(BORDER));
            return p;
        }

        @Override
        protected void installDefaults() {
            super.installDefaults();
            padding = new Insets(0, 0, 0, 0);
        }

        @Override
        protected javax.swing.ComboBoxEditor createEditor() {
            BasicComboBoxEditor.UIResource ed = new BasicComboBoxEditor.UIResource();
            JComponent tf = (JComponent) ed.getEditorComponent();
            tf.setBorder(BorderFactory.createEmptyBorder(0, 6, 0, 2));
            tf.setOpaque(false);
            return ed;
        }
    }

    private static class TombolPanah extends JButton {
        private static final long serialVersionUID = 1L;

        TombolPanah() {
            setContentAreaFilled(false);
            setBorderPainted(false);
            setFocusPainted(false);
            setFocusable(false);
            setOpaque(false);
            setRolloverEnabled(true);
            setBorder(BorderFactory.createEmptyBorder());
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(20, 20);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            try {
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int w = getWidth();
                int h = getHeight();
                Component parent = getParent();
                boolean aktif = parent == null || parent.isEnabled();

                if (aktif && getModel().isRollover()) {
                    g2.setColor(TINT);
                    g2.fill(new RoundRectangle2D.Float(1, 1, w - 2, h - 2, RADIUS * 2f - 3, RADIUS * 2f - 3));
                }
                
                g2.setColor(PEMISAH);
                g2.drawLine(0, 3, 0, h - 4);

                float cx = w / 2f + 0.5f;
                float cy = h / 2f + 0.5f;
                Path2D chevron = new Path2D.Float();
                chevron.moveTo(cx - 4, cy - 2);
                chevron.lineTo(cx, cy + 2);
                chevron.lineTo(cx + 4, cy - 2);
                g2.setColor(aktif ? new Color(0x3E4B44) : new Color(0xB3BCB7));
                g2.setStroke(new BasicStroke(1.6f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g2.draw(chevron);
            } finally {
                g2.dispose();
            }
        }
    }
}
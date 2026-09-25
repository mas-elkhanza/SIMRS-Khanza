package widget;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.plaf.UIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

public class Table extends JTable {

    private static final long serialVersionUID = 2L;

    static final Color AKSEN     = new Color(0x16A05D);
    static final Color ZEBRA     = new Color(0xF7FAF8);
    static final Color GARIS     = new Color(0xE3EAE6);
    private static final Border PADDING_SEL = BorderFactory.createEmptyBorder(0, 6, 0, 6);
    private static final Color  WARNA_HOVER = new Color(0x16, 0xA0, 0x5D, 20);
    static final int   TINGGI_HEADER = 22;

    private int barisHover = -1;
    private boolean zebra = true;

    public Table() {
        super();
        setBackground(Color.WHITE);
        setForeground(new Color(0x2E3833));
        setGridColor(GARIS);
        setFont(new Font("Tahoma", Font.PLAIN, 11));
        setRowHeight(24);
        setSelectionBackground(new Color(0xD5EFE0));
        setSelectionForeground(new Color(0x0E3B24));
        setShowGrid(true);

        
        getTableHeader().setForeground(new Color(50,50,50));
        getTableHeader().setBackground(new Color(255,250,250));
        getTableHeader().setBorder(BorderFactory.createEmptyBorder());
        getTableHeader().setFont(new java.awt.Font("Tahoma", 0, 11));
        getTableHeader().setDefaultRenderer(new HeaderFlat());   

        MouseAdapter hover = new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                setBarisHover(rowAtPoint(e.getPoint()));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBarisHover(-1);
            }
        };
        addMouseListener(hover);
        addMouseMotionListener(hover);
    }

    @Override
    protected JTableHeader createDefaultTableHeader() {
        return new JTableHeader(columnModel) {
            private static final long serialVersionUID = 1L;

            @Override
            public Dimension getPreferredSize() {
                Dimension d = super.getPreferredSize();
                d.height = TINGGI_HEADER;
                return d;
            }
        };
    }


    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
        Component c = super.prepareRenderer(renderer, row, column);
        // hanya renderer bawaan JTable (UIResource) yang diberi zebra,
        // renderer custom (WarnaTable dsb.) dibiarkan apa adanya
        if (zebra && renderer instanceof UIResource && !isCellSelected(row, column)) {
            c.setBackground(row % 2 == 0 ? getBackground() : ZEBRA);
        }
        if (c instanceof JComponent && renderer instanceof UIResource) {
            ((JComponent) c).setBorder(PADDING_SEL);
        }
        return c;
    }

    private void setBarisHover(int row) {
        if (row == barisHover) {
            return;
        }
        repaintBaris(barisHover);
        barisHover = row;
        repaintBaris(barisHover);
    }

    private void repaintBaris(int row) {
        if (row >= 0 && row < getRowCount()) {
            Rectangle r = getCellRect(row, 0, true);
            repaint(0, r.y, getWidth(), r.height);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (getRowCount() == 0) {
            return;
        }
        Graphics2D g2 = (Graphics2D) g.create();
        try {
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            Rectangle clip = g2.getClipBounds();
           
            if (barisHover >= 0 && barisHover < getRowCount() && isEnabled()) {
                Rectangle r = getCellRect(barisHover, 0, true);
                if (clip == null || clip.intersects(0, r.y, getWidth(), r.height)) {
                    g2.setColor(WARNA_HOVER);
                    g2.fillRect(0, r.y, getWidth(), r.height);
                }
            }

            if (getColumnCount() > 0 && getRowSelectionAllowed()) {
                g2.setColor(AKSEN);
                for (int row : getSelectedRows()) {
                    Rectangle r = getCellRect(row, 0, true);
                    if (clip == null || clip.intersects(r)) {
                        g2.fillRect(0, r.y, 3, r.height);
                    }
                }
            }
        } finally {
            g2.dispose();
        }
    }

    private static class HeaderFlat extends DefaultTableCellRenderer {

        private static final long serialVersionUID = 1L;
        private static final Color LATAR    = new Color(255, 250, 250);
        private static final Color PEMISAH  = new Color(0xE2D9D9);
        private static final Color GARIS_BW = new Color(0xD9CFCF);
        private static final Border PADDING = BorderFactory.createEmptyBorder(0, 4, 0, 3);

        HeaderFlat() {
            setHorizontalAlignment(SwingConstants.LEFT);
            setOpaque(false);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, false, false, row, column);
            if (table != null && table.getTableHeader() != null) {
                setFont(table.getTableHeader().getFont());
                setForeground(table.getTableHeader().getForeground());
            }
            setBorder(PADDING);
            return this;
        }

        @Override
        protected void paintComponent(Graphics g) {
            int w = getWidth();
            int h = getHeight();
            g.setColor(LATAR);
            g.fillRect(0, 0, w, h);
            g.setColor(PEMISAH);
            g.drawLine(w - 1, 4, w - 1, h - 5);     
            g.setColor(GARIS_BW);
            g.drawLine(0, h - 1, w, h - 1);          
            super.paintComponent(g);
        }
    }
    
    public boolean isZebra() { return zebra; }
    public void setZebra(boolean z) { zebra = z; repaint(); }
}
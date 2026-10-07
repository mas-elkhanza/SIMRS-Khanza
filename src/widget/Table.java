package widget;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.geom.RoundRectangle2D;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.UIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

public class Table extends JTable {
    private static final long serialVersionUID = 3L;
    static final Color AKSEN        = new Color(22, 160, 93);     
    static final Color PILIH_PASIF  = new Color(220, 234, 226);   
    static final Color LAPIS_CUSTOM = new Color(22, 160, 93, 28); 
    static final Color TEKS         = new Color(50, 50, 50);
    static final Color GARIS_BARIS  = new Color(228, 235, 230);   
    static final Color GARIS_KOLOM  = new Color(238, 242, 239);   
    static final Color TEKS_PILIH   = new Color(14, 59, 36);      
    static final int   TINGGI_HEADER = 22;
    static final int   ARC_PILIH     = 8;
    private JViewport viewportTerpasang;
    private final ChangeListener scrollHandler = e -> repaint();

    public Table() {
        super();
        setBackground(Color.WHITE);
        setForeground(TEKS);
        setFont(new Font("Tahoma", Font.PLAIN, 11));
        setRowHeight(22);
        setSelectionBackground(PILIH_PASIF);
        setSelectionForeground(TEKS_PILIH);

        setShowHorizontalLines(false);
        setShowVerticalLines(false);
        setIntercellSpacing(new Dimension(0, 0));
        setOpaque(false);   

        getTableHeader().setForeground(new Color(50, 50, 50));
        getTableHeader().setBackground(new Color(255, 250, 250));
        getTableHeader().setBorder(BorderFactory.createEmptyBorder());
        getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 11));
        getTableHeader().setDefaultRenderer(new HeaderFlat());

        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) { repaint(); }

            @Override
            public void focusLost(FocusEvent e) { repaint(); }
        });
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
    public void addNotify() {
        super.addNotify();
        if (getParent() instanceof JViewport) {
            viewportTerpasang = (JViewport) getParent();
            viewportTerpasang.addChangeListener(scrollHandler);
        }
    }

    @Override
    public void removeNotify() {
        if (viewportTerpasang != null) {
            viewportTerpasang.removeChangeListener(scrollHandler);
            viewportTerpasang = null;
        }
        super.removeNotify();
    }

    private boolean aktif() {
        return isFocusOwner() && isEnabled();
    }

    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
        Component c = super.prepareRenderer(renderer, row, column);
        if (renderer instanceof UIResource) {
            boolean pilih = isRowSelected(row) && getRowSelectionAllowed();
            if (c instanceof JComponent) {
                ((JComponent) c).setOpaque(!pilih);
            }
            c.setForeground(pilih && aktif() ? Color.WHITE : getForeground());
        }
        
        return c;
    }

    @Override
    protected void paintComponent(Graphics g) {
        int n = getRowCount();
        Rectangle clip = g.getClipBounds();
        if (clip == null) {
            clip = new Rectangle(0, 0, getWidth(), getHeight());
        }

        int awal = 0;
        int akhir = -1;
        if (n > 0) {
            Point p = new Point(0, clip.y);
            awal = Math.max(0, rowAtPoint(p));
            p.y = clip.y + clip.height - 1;
            akhir = rowAtPoint(p);
            if (akhir < 0) {
                akhir = n - 1;
            }
        }
        boolean bisaPilih = getRowSelectionAllowed() && getSelectedRowCount() > 0;
        boolean aktif = aktif();
        Rectangle terlihat = bisaPilih ? getVisibleRect() : null;

        int bawahData = n > 0 ? getCellRect(n - 1, 0, true).y + getRowHeight(n - 1) : 0;
        if (clip.y + clip.height > bawahData) {
            int y0 = Math.max(clip.y, bawahData);
            g.setColor(getBackground());
            g.fillRect(clip.x, y0, clip.width, clip.y + clip.height - y0);
        }

        if (bisaPilih) {
            Graphics2D g2 = null;
            try {
                for (int row = awal; row <= akhir; row++) {
                    if (!isRowSelected(row)) {
                        continue;
                    }
                    if (g2 == null) {
                        g2 = (Graphics2D) g.create();
                        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    }
                    Rectangle r = getCellRect(row, 0, true);
                    g2.setColor(getBackground());
                    g2.fillRect(clip.x, r.y, clip.width, r.height);
                    g2.setColor(aktif ? AKSEN : PILIH_PASIF);
                    g2.fill(new RoundRectangle2D.Float(terlihat.x + 4, r.y + 1,
                            terlihat.width - 8, r.height - 2, ARC_PILIH, ARC_PILIH));
                }
            } finally {
                if (g2 != null) {
                    g2.dispose();
                }
            }
        }

        super.paintComponent(g);

        if (n == 0 || getColumnCount() == 0) {
            return;
        }

        gambarGaris(g, clip, awal, akhir, n);

        if (bisaPilih) {
            Graphics2D g3 = null;
            try {
                for (int row = awal; row <= akhir; row++) {
                    if (!isRowSelected(row) || !adaRendererCustom(row)) {
                        continue;
                    }
                    if (g3 == null) {
                        g3 = (Graphics2D) g.create();
                        g3.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                        g3.setColor(LAPIS_CUSTOM);
                    }
                    Rectangle r = getCellRect(row, 0, true);
                    g3.fill(new RoundRectangle2D.Float(terlihat.x + 4, r.y + 1,
                            terlihat.width - 8, r.height - 2, ARC_PILIH, ARC_PILIH));
                }
            } finally {
                if (g3 != null) {
                    g3.dispose();
                }
            }
        }
    }

    private void gambarGaris(Graphics g, Rectangle clip, int awal, int akhir, int n) {
        TableColumnModel cm = getColumnModel();
        int kolom = cm.getColumnCount();
        Point p = new Point(clip.x, 0);
        int kolAwal = Math.max(0, columnAtPoint(p));
        p.x = clip.x + clip.width - 1;
        int kolAkhir = columnAtPoint(p);
        if (kolAkhir < 0) {
            kolAkhir = kolom - 1;
        }

        int jml = kolAkhir - kolAwal + 1;
        int[] xs = new int[jml];
        int x = 0;
        for (int col = 0; col < kolAwal; col++) {
            x += cm.getColumn(col).getWidth();
        }
        for (int i = 0; i < jml; i++) {
            x += cm.getColumn(kolAwal + i).getWidth();
            xs[i] = x - 1;
        }

        boolean adaSeleksi = getRowSelectionAllowed();
        int x1 = clip.x + clip.width;
        int blokY = -1;  

        for (int row = awal; row <= akhir; row++) {
            Rectangle r = getCellRect(row, 0, true);
            boolean pilih = adaSeleksi && isRowSelected(row);
            if (pilih) {
                if (blokY >= 0) {
                    garisKolom(g, xs, blokY, r.y - 1);
                    blokY = -1;
                }
                continue;
            }
            if (blokY < 0) {
                blokY = r.y;
            }
            int yBawah = r.y + r.height - 1;
            if (!(adaSeleksi && row + 1 < n && isRowSelected(row + 1))) {
                g.setColor(GARIS_BARIS);
                g.drawLine(clip.x, yBawah, x1, yBawah);
            }
            if (row == akhir) {
                garisKolom(g, xs, blokY, yBawah);
            }
        }
    }

    private static void garisKolom(Graphics g, int[] xs, int y0, int y1) {
        g.setColor(GARIS_KOLOM);
        for (int x : xs) {
            g.drawLine(x, y0, x, y1);
        }
    }

    private boolean adaRendererCustom(int row) {
        int kolom = getColumnCount();
        for (int col = 0; col < kolom; col++) {
            if (!(getCellRenderer(row, col) instanceof UIResource)) {
                return true;
            }
        }
        return false;
    }

    private static class HeaderFlat extends DefaultTableCellRenderer {

        private static final long serialVersionUID = 1L;
        private static final Color LATAR    = new Color(255, 250, 250);
        private static final Color PEMISAH  = new Color(0xE2D9D9);
        private static final Color GARIS_BW = new Color(0xD9CFCF);
        private Border padding;

        private Border padding() {
            if (padding == null) {
                int kiri = 2, kanan = 2;   
                Border b = javax.swing.UIManager.getBorder("TableHeader.cellBorder");
                if (b != null) {
                    try {
                        java.awt.Insets in = b.getBorderInsets(this);
                        kiri = Math.min(Math.max(in.left, 1), 4);
                        kanan = Math.min(Math.max(in.right, 1), 4);
                    } catch (RuntimeException ignore) {
                    }
                }
                padding = BorderFactory.createEmptyBorder(0, kiri, 0, kanan);
            }
            return padding;
        }

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
            setBorder(padding());
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
}
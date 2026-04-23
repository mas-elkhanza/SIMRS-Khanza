package widget;

import java.awt.Color;
import javax.swing.JTable;
import java.awt.Component;
import java.awt.Font;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.BorderFactory;
import java.awt.Dimension;

/**
 *
 * @author usu
 */
public class Table extends JTable {

    /*
     * Serial version UID
     */
    private static final long serialVersionUID = 1L;

    public Table() {
        super();
        //setBackground(new Color(255,235,255));
        //setGridColor(new Color(245,170,245));
        //setForeground(new Color(90,90,90));
        //setBackground(new Color(255,255,255));
        setGridColor(new Color(255,255,255));
        //setForeground(new Color(50,50,50));//warna font
        setFont(new java.awt.Font("Tahoma", 0, 11)); //font isi
        setRowHeight(24);//tinggi tabel
        setSelectionBackground(new Color(255,255,255));
        setSelectionForeground(new Color(0,0,0));//warna saat dipilih
        //getTableHeader().setFont(new java.awt.Font("Tahoma", 1, 11)); //font judul
        getTableHeader().setForeground(new Color(255, 255, 255));//warna judul
        getTableHeader().setBackground(new Color(50,50,50));//bg judul
        //getTableHeader().setBorder(javax.swing.BorderFactory.createLineBorder(new Color(255,250,250)));
        getTableHeader().setFont(new java.awt.Font("Tahoma", 1, 11)); //font judul
        
        // Tambahkan renderer untuk bold baris yang dipilih
        setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (isSelected) {
                    c.setFont(c.getFont().deriveFont(Font.BOLD));
                } else {
                    c.setFont(c.getFont().deriveFont(Font.PLAIN));
                }
                return c;
            }
        });
        // Judul kolom
        JTableHeader header = getTableHeader();
        header.setForeground(Color.WHITE);
        header.setBackground(new Color(0,0,0)); // Hitam
        header.setFont(new java.awt.Font("Tahoma", Font.BOLD, 11));
        header.setBorder(BorderFactory.createLineBorder(new Color(0, 80, 40)));
        header.setPreferredSize(new Dimension(header.getPreferredSize().width, 35));
        setIntercellSpacing(new Dimension(1, 1));
        header.setDefaultRenderer(new MultiLineHeader());
    }
}
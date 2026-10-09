package fungsi;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.plaf.UIResource;
import javax.swing.table.DefaultTableCellRenderer;

public class WarnaTable extends DefaultTableCellRenderer implements UIResource {
    private static final long serialVersionUID = 4L;
    private static final Color PUTIH = new Color(255, 255, 255);
    private static final Color ZEBRA = new Color(245, 250, 245); 

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (!isSelected) {
            component.setBackground(row % 2 == 1 ? ZEBRA : PUTIH);
        }
        return component;
    }
}
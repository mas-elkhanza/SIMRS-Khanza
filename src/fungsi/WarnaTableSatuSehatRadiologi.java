package fungsi;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Renderer untuk mewarnai baris tabel radiologi Satu Sehat
 * berdasarkan status keberadaan ImagingStudy di Satu Sehat.
 * 
 * @author Antigravity
 */
public class WarnaTableSatuSehatRadiologi extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
            int row, int column) {
        Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        try {
            // Kita asumsikan kolom status ImagingStudy ada di kolom terakhir (index 22)
            Object statusImaging = table.getValueAt(row, 22);
            String status = statusImaging != null ? statusImaging.toString() : "";

            if (status.equalsIgnoreCase("Ada") && column == 1) {
                // Warna Hijau Muda hanya untuk kolom No. Rawat
                component.setBackground(new Color(200, 255, 200));
                component.setForeground(new Color(50, 50, 50));
            } else {
                // Default warna untuk kolom lain atau baris yang belum ada datanya
                if (isSelected) {
                    component.setBackground(table.getSelectionBackground());
                    component.setForeground(table.getSelectionForeground());
                } else {
                    component.setBackground(new Color(255, 255, 255));
                    component.setForeground(new Color(50, 50, 50));
                }
            }

        } catch (Exception e) {
            component.setBackground(new Color(255, 255, 255));
            component.setForeground(new Color(50, 50, 50));
        }

        return component;
    }
}

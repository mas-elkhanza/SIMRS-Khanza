/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fungsi;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Owner
 */

//public class WarnaTableResepRalan extends DefaultTableCellRenderer {
//    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
//        Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
//
//        // Memeriksa apakah nilai sel pada kolom ke-10 sama dengan "BPJS"
//        String cellValue = table.getValueAt(row, 9).toString();
//
//        if (table.getValueAt(row,9).toString().equals("BPJS")) {
//            // Warna untuk kondisi khusus (nilai pada kolom ke-10 adalah "BPJS")
//            component.setBackground(new Color(254, 251, 127));
//        } else {
//            if (row % 2 == 1){
//                // Warna untuk baris ganjil
//                component.setBackground(new Color(254, 255, 255));
//            } else {
//                // Warna untuk baris genap
//                component.setBackground(new Color(254, 242, 255));
//            }
//        }
//
//        // Set warna teks
//        component.setForeground(new Color(50, 50, 50));
//
//        return component;
//    }
//}
//public class WarnaTableResepRalan extends DefaultTableCellRenderer {
//    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
//        Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
//
//        // Memeriksa apakah nilai sel pada kolom 1 hingga 13 sama dengan "BPJS"
//        for (int i = 1; i <= 13; i++) {
//            String cellValue = table.getValueAt(row, i).toString();
//            if ("UMUM".equals(cellValue)) {
//                // Warna untuk kondisi khusus (nilai pada kolom 1 hingga 13 adalah "BPJS")
//                component.setBackground(new Color(251, 153, 0));
//                component.setForeground(new Color(50, 50, 50));
//                return component;
//            }
//        }
//
//        if (row % 2 == 1){
//            // Warna untuk baris ganjil
//            component.setBackground(new Color(255, 244, 244));
//        } else {
//            // Warna untuk baris genap
//            component.setBackground(new Color(251, 251, 255));
//        }
//
//        // Set warna teks
//        component.setForeground(new Color(50, 50, 50));
//
//        return component;
//    }
//}

//public class WarnaTableResepRalan extends DefaultTableCellRenderer {
//    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
//        Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
//
//        // Memeriksa apakah nilai sel pada kolom 1 hingga 13 bukan "BPJS"
//        boolean isNonBPJS = true;
//        for (int i = 1; i <= 13; i++) {
//            String cellValue = table.getValueAt(row, i).toString();
//            if ("BPJS".equals(cellValue)) {
//                // Jika ditemukan "BPJS", set flag menjadi false
//                isNonBPJS = false;
//                break;
//            }
//        }
//
//        if (isNonBPJS) {
//            // Warna untuk kondisi khusus (nilai pada kolom 1 hingga 13 bukan "BPJS")
//            component.setBackground(new Color(251, 153, 0));
//            component.setForeground(new Color(50, 50, 50));
//            return component;
//        }
//
//        if (row % 2 == 1){
//            // Warna untuk baris ganjil
//            component.setBackground(new Color(255, 244, 244));
//        } else {
//            // Warna untuk baris genap
//            component.setBackground(new Color(251, 251, 255));
//        }
//
//        // Set warna teks
//        component.setForeground(new Color(50, 50, 50));
//
//        return component;
//    }
//}
public class WarnaTableResepRalan extends DefaultTableCellRenderer {
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
        Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        // Memeriksa apakah nilai sel pada kolom 1 hingga 13 bukan "BPJS"
        boolean isNonBPJS = true;
        for (int i = 1; i <= 13; i++) {
            String cellValue = table.getValueAt(row, i).toString();
            if ("BPJS".equals(cellValue)) {
                // Jika ditemukan "BPJS", set flag menjadi false
                isNonBPJS = false;
                break;
            }
        }

        // Jika nilai pada kolom 14 (indeks 13) adalah "Sudah Dilayani", beri warna hijau
        if (value != null && "Sudah Terlayani".equals(value.toString())) {
            component.setBackground(new Color(144, 238, 144)); // Warna hijau
            component.setForeground(new Color(50, 50, 50));
            return component;
        }

        if (isNonBPJS) {
            // Warna untuk kondisi khusus (nilai pada kolom 1 hingga 13 bukan "BPJS")
            component.setBackground(new Color(251, 153, 0));
            component.setForeground(new Color(50, 50, 50));
            return component;
        }

        if (row % 2 == 1){
            // Warna untuk baris ganjil
            component.setBackground(new Color(255, 244, 244));
        } else {
            // Warna untuk baris genap
            component.setBackground(new Color(251, 251, 255));
        }

        // Set warna teks
        component.setForeground(new Color(50, 50, 50));

        return component;
    }
}




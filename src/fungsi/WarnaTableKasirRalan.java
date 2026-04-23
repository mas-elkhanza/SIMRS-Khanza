/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fungsi;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Owner
 */
public class WarnaTableKasirRalan extends DefaultTableCellRenderer {
    ////// tambahan untuk men set kolom mana yang mau di ubah warna bersadarkan status
    public int kolom = 15;
    public int statrawat = 10;
//
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
        Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (row % 2 == 1){
            component.setBackground(new Color(229,248,255));
//            component.setBackground(new Color(229,246,244));
        }else{
            component.setBackground(new Color(255,255,255));
        }
        if (table.getValueAt(row, kolom).toString().equals("Sudah Bayar")){
            component.setBackground(new Color(51, 185, 255));
        }
        if (table.getValueAt(row, statrawat).toString().equals("Batal")){
            component.setBackground(new Color(211,84,0));
        }
//        if (isSelected) {
//            component.setBackground(new Color(148, 198, 238)); // Warna biru untuk baris yang dipilih
//            component.setForeground(Color.WHITE);           // Warna teks putih agar kontras
//        }
// Membuat teks tebal saat baris dipilih
        if (isSelected) {
            component.setFont(component.getFont().deriveFont(Font.BOLD));
        } else {
            component.setFont(component.getFont().deriveFont(Font.PLAIN));
        }
        return component;
        
    }

}
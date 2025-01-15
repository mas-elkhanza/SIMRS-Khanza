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
public class WarnaTableRawatInap extends DefaultTableCellRenderer {
   public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
        Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (row % 2 == 1){
            component.setBackground(new Color(255,244,244));
            component.setForeground(new Color(188,33,114));
        }else{
            component.setBackground(new Color(255,255,255));
            component.setForeground(new Color(188,33,114));
        } 
        if(table.getValueAt(row,6).toString().equals("BPJS")){
            component.setBackground(new Color(37,163,8));
            component.setForeground(new Color(0,0,0));
        }else if(table.getValueAt(row,6).toString().equals("Umum")){
            component.setBackground(new Color(249,52,87));
            component.setForeground(new Color(0,0,0));
        }else if(table.getValueAt(row,6).toString().equals("Asuransi")){
            component.setBackground(new Color(166,77,121));
            component.setForeground(new Color(0,0,0));
        }else if(table.getValueAt(row,6).toString().equals("Perusahaan")){
            component.setBackground(new Color(11,83,148));
            component.setForeground(new Color(0,0,0));
    
        }
        
         if (isSelected) {
            component.setForeground(new Color(188,33,114));
        }
        return component;
    }

}

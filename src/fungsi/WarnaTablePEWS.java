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
public class WarnaTablePEWS extends DefaultTableCellRenderer {
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
        Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (row % 2 == 1){
            component.setBackground(new Color(255,246,244));
            component.setForeground(new Color(50,50,50));
        }else{
            component.setBackground(new Color(255,255,255));
            component.setForeground(new Color(50,50,50));
        } 
        
        if ((column == 6)||(column == 7)){
            if(Integer.parseInt(table.getValueAt(row,7).toString())==0){
                component.setBackground(Color.GREEN);
                component.setForeground(Color.WHITE);
            }else if(Integer.parseInt(table.getValueAt(row,7).toString())==1){
                component.setBackground(Color.YELLOW);
                component.setForeground(Color.GREEN);
            }else if(Integer.parseInt(table.getValueAt(row,7).toString())==2){
                component.setBackground(Color.ORANGE);
                component.setForeground(Color.WHITE);
            }else if(Integer.parseInt(table.getValueAt(row,7).toString())==3){
                component.setBackground(Color.RED);
                component.setForeground(Color.WHITE);
            }
        }
        
        if ((column == 8)||(column == 9)){
            if(Integer.parseInt(table.getValueAt(row,9).toString())==0){
                component.setBackground(Color.GREEN);
                component.setForeground(Color.WHITE);
            }else if(Integer.parseInt(table.getValueAt(row,9).toString())==1){
                component.setBackground(Color.YELLOW);
                component.setForeground(Color.GREEN);
            }else if(Integer.parseInt(table.getValueAt(row,9).toString())==2){
                component.setBackground(Color.ORANGE);
                component.setForeground(Color.WHITE);
            }else if(Integer.parseInt(table.getValueAt(row,9).toString())==3){
                component.setBackground(Color.RED);
                component.setForeground(Color.WHITE);
            }
        }
        
        if ((column == 10)||(column == 11)){
            if(Integer.parseInt(table.getValueAt(row,11).toString())==0){
                component.setBackground(Color.GREEN);
                component.setForeground(Color.WHITE);
            }else if(Integer.parseInt(table.getValueAt(row,11).toString())==1){
                component.setBackground(Color.YELLOW);
                component.setForeground(Color.GREEN);
            }else if(Integer.parseInt(table.getValueAt(row,11).toString())==2){
                component.setBackground(Color.ORANGE);
                component.setForeground(Color.WHITE);
            }else if(Integer.parseInt(table.getValueAt(row,11).toString())==3){
                component.setBackground(Color.RED);
                component.setForeground(Color.WHITE);
            }
        }
        
        if ((column == 12)||(column == 13)){
            if(Integer.parseInt(table.getValueAt(row,12).toString())>7){
                component.setBackground(Color.BLUE);
                component.setForeground(Color.WHITE);
            }
        }   
        return component;
    }

}

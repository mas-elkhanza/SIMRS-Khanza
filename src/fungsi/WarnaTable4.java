/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fungsi;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Owner
 */
public class WarnaTable4 extends JTextArea implements TableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
        this.setText((String)value);
        this.setWrapStyleWord(true);                    
        this.setLineWrap(true);  
        if (row % 2 == 1){
            this.setBackground(new Color(255,246,244));
        }else{
            this.setBackground(new Color(255,255,255));
        } 
        this.setSelectionColor(new Color(255,255,255));
        this.setSelectedTextColor(new Color(255,0,0));
        this.setForeground(new Color(50,50,50));
        this.setFont(new java.awt.Font("Tahoma", 0, 11));
        return this;
    }

}

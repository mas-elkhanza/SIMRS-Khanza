/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package component;

import java.awt.Color;
import javax.swing.JTable;
import javax.swing.border.LineBorder;

/**
 *
 * @author root
 */
public class Table extends JTable{
    public Table() {
        setBackground(new Color(255,255,255));
        setGridColor(new Color(240,200,240));
        setForeground(new Color(160,130,160));
        setFont(new java.awt.Font("Tahoma", 0, 18));
        setRowHeight(33);
        setSelectionBackground(new Color(80,80,80));
        setSelectionForeground(new Color(130,134,0));
        getTableHeader().setBackground(new Color(240,255,255));
        getTableHeader().setBorder(new LineBorder(new Color(240,255,255)));
        getTableHeader().setFont(new java.awt.Font("Tahoma", 0, 18));
        getTableHeader().setForeground(new Color(160,130,160));
    }
}

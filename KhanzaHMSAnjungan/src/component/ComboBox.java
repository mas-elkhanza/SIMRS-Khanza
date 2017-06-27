/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package component;

import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.border.LineBorder;

/**
 *
 * @author dosen3
 */
public final class ComboBox extends JComboBox {
    public ComboBox(){    
        setFont(new java.awt.Font("Tahoma", 0, 11));
        setBorder(new LineBorder(new Color(225,120,225), 1, true));
        setBackground(new Color(255,255,255));
        setForeground(new Color(140,90,140));
        setSize(WIDTH,23);
    } 
}

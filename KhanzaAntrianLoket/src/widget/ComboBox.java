/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package widget; 

import java.awt.Color;
import javax.swing.JComboBox;

/**
 *
 * @author dosen3
 */
public final class ComboBox extends JComboBox {

    public ComboBox(){
        setFont(new java.awt.Font("Tahoma", 0, 11));
        setBackground(new Color(255,255,255));
        setForeground(new Color(70,70,70));
        setSize(WIDTH,23);
    } 
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package widget; 

import java.awt.Color;
import javax.swing.JComboBox;
import setting.Thema;

/**
 *
 * @author dosen3
 */
public final class ComboBox extends JComboBox {

    /**
     *
     */
    public ComboBox(){
        setFont(new java.awt.Font("Tahoma", 0, 11));
        setBackground(Thema.COKLAT_TUA);
        setForeground(new Color(50,50,50));
        setSize(WIDTH,23);
    } 
}

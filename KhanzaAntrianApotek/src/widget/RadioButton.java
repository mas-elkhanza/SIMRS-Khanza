/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package widget;

import java.awt.Color;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author dosen3
 */
public class RadioButton extends JRadioButton{

    private static final long serialVersionUID = 1L;

    public RadioButton(){
        super();
        setFont(new java.awt.Font("Tahoma", 0, 11));
        //setForeground(new Color(90,90,90));
        //setBackground(new Color(245,170,245));
        setBackground(new Color(255,255,255));
        setForeground(new Color(70,70,70));
        setFocusPainted(false);
        setBorder(new EmptyBorder(1,1,1,1));
        setOpaque(false);
        setSize(WIDTH,23);
    }
}

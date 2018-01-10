/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package component;

import java.awt.Color;
import uz.ncipro.calendar.JDateTimePicker;

/**
 *
 * @author khanzasoft
 */
public final class Tanggal extends JDateTimePicker {
    private static final long serialVersionUID = 1L;

    public Tanggal(){
        //setBackground(new Color(245,160,245));
        //setForeground(new Color(90,90,90));
        setForeground(new Color(140,90,140));
        setBackground(new Color(255,255,255));
        setFont(new java.awt.Font("Tahoma", 0, 11));
        setBorder(javax.swing.BorderFactory.createEmptyBorder());
        setSize(WIDTH,23);
    }

}

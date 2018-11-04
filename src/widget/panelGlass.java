/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package widget;

import java.awt.Color;
import javax.swing.JPanel;

/**
 *
 * @author dosen
 */
public class panelGlass extends JPanel {

    public panelGlass() {
        super();
        setBackground(new Color(255,255,255));
        this.setForeground(new Color(110,120,100));
        this.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(239,244,234)));
    }

}

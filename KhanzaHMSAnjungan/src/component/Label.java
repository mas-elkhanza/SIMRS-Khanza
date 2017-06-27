package component;

import java.awt.Color;
import javax.swing.JLabel;

/**
 *
 * @author usu
 */
public class Label extends JLabel {

    /*
     * Serial version UID
     */
    private static final long serialVersionUID = 1L;

    public Label() {
        super();
        //setForeground(new Color(90,90,90));
        setForeground(new Color(160,130,160));
        setFont(new java.awt.Font("Tahoma", 0, 20));

        setHorizontalAlignment(RIGHT);
        setVerticalAlignment(CENTER);
        setHorizontalTextPosition(CENTER);
        setVerticalTextPosition(CENTER);
    }
}

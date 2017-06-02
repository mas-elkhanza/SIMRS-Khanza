package component;

import java.awt.Color;
import java.awt.Insets;
import javax.swing.JButton;

/**
 *
 * @author usu
 */
public class Button extends JButton {

    /*
     * Serial version UID
     */
    private static final long serialVersionUID = 1L;

    public Button() {
        super();
        setFont(new java.awt.Font("Tahoma", 0, 18));
        setForeground(new Color(130,80,130));
        setMargin(new Insets(2, 5, 2, 5));
        setIconTextGap(4);
    }
}

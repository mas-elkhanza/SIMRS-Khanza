package component;

import java.awt.Color;
import java.awt.Insets;
import javax.swing.JTextField;

/**
 *
 * @author usu
 */
public class TextBox extends JTextField {
    public TextBox() {
        //setForeground(new Color(90,90,90));
        setForeground(new Color(140,90,140));
        setBackground(new Color(255,255,255));
        setCaretColor(Color.red);
        setHorizontalAlignment(LEFT);
        setSize(WIDTH,23);
        setMargin(new Insets(1, 4, 1,1));
        setFont(new java.awt.Font("Tahoma", 0, 18));
        setBorder(javax.swing.BorderFactory.createLineBorder(new Color(205,150,205)));
        setSelectionColor(new Color(200,51,0));

    }
}

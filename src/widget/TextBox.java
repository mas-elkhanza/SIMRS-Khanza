
package widget;

import java.awt.Color;
import usu.widget.glass.TextBoxGlass;

/**
 *
 * @author usu
 */
public class TextBox extends TextBoxGlass {
    public TextBox() {
        super();
        setFont(new java.awt.Font("Tahoma", 0, 11));        
        setSelectionColor(new Color(255,255,255));
        setSelectedTextColor(new Color(255,0,0));
        setForeground(new Color(90,120,80));
        setBackground(new Color(255,255,253));
        setHorizontalAlignment(LEFT);
        setSize(WIDTH,23);
    }
}

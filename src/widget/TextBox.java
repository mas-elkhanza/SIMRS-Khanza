
package widget;

import java.awt.Color;
import setting.Thema;
import usu.widget.glass.TextBoxGlass;

/**
 *
 * @author usu
 */
public class TextBox extends TextBoxGlass {

    /**
     *
     */
    public TextBox() {
        super();
        setFont(new java.awt.Font("Tahoma", 0, 11));        
        setSelectionColor(Thema.CREAM);
        setSelectedTextColor(Thema.COKLAT_TUA);
        setForeground(new Color(50,50,50));
        setBackground(new Color(255,255,255));
        setHorizontalAlignment(LEFT);
        setSize(WIDTH,23);
    }
}


package widget;

import java.awt.Color;
import java.awt.Font;

/**
 *
 * @author usu
 */
public class PasswordBox extends usu.widget.glass.PasswordBox {
    public PasswordBox() {
        super();
        setSelectionColor(Color.BLUE.brighter());
        setCaretColor(Color.red);
        setFont(getFont().deriveFont(Font.BOLD,12));
        setForeground(Color.WHITE);
        setHorizontalAlignment(LEFT);
    }
}

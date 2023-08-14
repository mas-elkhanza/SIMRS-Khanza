package widget;

import java.awt.Color;
import usu.widget.glass.ButtonImageReflection;

/**
 *
 * @author usu
 */
public class ButtonBig extends ButtonImageReflection {

    /*
     * Serial version UID
     */
    private static final long serialVersionUID = 1L;

    public ButtonBig() {
        super();
        setForeground(new Color(50,50,50));
        setFont(new java.awt.Font("Tahoma", 0, 11));
    }
}

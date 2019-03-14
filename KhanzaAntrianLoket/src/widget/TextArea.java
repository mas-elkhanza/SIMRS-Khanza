
package widget;

import java.awt.Color;
import static java.awt.image.ImageObserver.WIDTH;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author usu
 */
public class TextArea extends JTextArea {
    public TextArea() {
        super();
        setOpaque(false);
        setLineWrap(true);
        setWrapStyleWord(true);
        setBorder(new EmptyBorder(5, 5, 5, 5));
        setFont(new java.awt.Font("Tahoma", 0, 11));        
        setSelectionColor(new Color(255,255,255));
        setSelectedTextColor(new Color(255,0,0));
        setForeground(new Color(70,70,70));
        setBackground(new Color(255,255,255));
        setSize(WIDTH,23);
        setSize(WIDTH,23);
    }
}

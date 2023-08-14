
package component;

import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

/**
 *
 * @author usu
 */
public class ScrollPane extends JScrollPane {

    /*
     * Serial version UID
     */
    private static final long serialVersionUID = 1L;

    public ScrollPane() {
        super();
        //setBorder(new LineBorder(new Color(235,140,235)));
        //setBackground(new Color(255,235,255));
        getViewport().setOpaque(false);
        setBorder(new LineBorder(new Color(240,205,240)));
        setBackground(new Color(255,255,255));
    }
}

package widget;

import java.awt.Color;
import java.awt.Insets;

public class Button extends usu.widget.ButtonGlass {
    private static final long serialVersionUID = 1L;

    public Button() {
        super();
        setFont(new java.awt.Font("Tahoma", 1, 11));
        setForeground(new Color(50,50,50));
        setGlassColor(new Color(240,245,240));
        setMargin(new Insets(2, 7, 2, 7));
        setIconTextGap(4);
        setRoundRect(true);
    }
}
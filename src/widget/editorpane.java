package widget;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JEditorPane;
import javax.swing.JViewport;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.text.Caret;

public class editorpane extends JEditorPane {
    private static final long serialVersionUID = 2L;

    static final Color AKSEN   = new Color(22, 160, 93);
    static final Color GARIS   = new Color(239, 244, 234);   
    static final Color TEKS    = new Color(50, 50, 50);

    private static final Border PADDING         = new EmptyBorder(6, 8, 6, 8);
    private static final Border BORDER_MANDIRI  = new CompoundBorder(new LineBorder(GARIS), PADDING);
    private static final int    KEDIP_CARET     = 500;

    public editorpane() {
        super();
        setFont(new Font("Tahoma", Font.PLAIN, 11));
        setForeground(TEKS);
        setBackground(Color.WHITE);
        setSelectionColor(new Color(0xCDEBDA));
        setSelectedTextColor(new Color(0x0E3B24));
        setCaretColor(AKSEN);
        setBorder(BORDER_MANDIRI);

        putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, Boolean.TRUE);

        aturCaret();
        addPropertyChangeListener("editable", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent e) {
                aturCaret();
            }
        });
    }

    private void aturCaret() {
        Caret c = getCaret();
        if (c == null) {
            return;
        }
        if (isEditable()) {
            c.setBlinkRate(KEDIP_CARET);
            c.setVisible(hasFocus());
        } else {
            c.setBlinkRate(0);
            c.setVisible(false);
        }
    }

    @Override
    public void setCaret(Caret c) {
        super.setCaret(c);
        aturCaret();
    }

    @Override
    public void addNotify() {
        super.addNotify();
        Container p = getParent();
        if (p instanceof JViewport) {
            if (getBorder() == BORDER_MANDIRI) {
                setBorder(PADDING);
            }
        } else if (getBorder() == PADDING) {
            setBorder(BORDER_MANDIRI);
        }
    }
}
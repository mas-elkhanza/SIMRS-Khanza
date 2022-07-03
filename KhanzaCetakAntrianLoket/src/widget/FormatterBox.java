
package widget;

import java.awt.Color;
import java.awt.Font;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import usu.util.StringUtil;
import usu.widget.glass.FormatedTextBox;
import usu.widget.text.DefaultDocument;

/**
 *
 * @author usu
 */
public class FormatterBox extends FormatedTextBox {

    /*
     * Serial version UID
     */
    private static final long serialVersionUID = 1L;

    public FormatterBox() {
        super();
        setFont(getFont().deriveFont(Font.BOLD));
        setForeground(Color.WHITE);
        setSelectionColor(Color.BLUE.brighter());
        setCaretColor(Color.white);
        setHorizontalAlignment(LEFT);
        setDocument(new DefaultDocument() {

            /*
             * Serial version UID
             */
            private static final long serialVersionUID = 1L;

            @Override
            public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
                if (StringUtil.containQuote(str)) {
                    return;
                }
                super.insertString(offs, str, a);
            }
        });
    }
}

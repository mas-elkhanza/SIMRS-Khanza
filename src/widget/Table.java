package widget;

import java.awt.Color;
import javax.swing.JTable;
import setting.Thema;
import usu.widget.util.WidgetUtilities;

/**
 *
 * @author usu
 */
public class Table extends JTable {

    /*
     * Serial version UID
     */
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    public Table() {
        super();
        //setBackground(new Color(255,235,255));
        //setGridColor(new Color(245,170,245));
        //setForeground(new Color(90,90,90));
        setBackground(new Color(255, 255, 255));
        setGridColor(Thema.CREAM);
        setForeground(new Color(50, 50, 50));
        setFont(new java.awt.Font("Tahoma", 0, 11));
        setRowHeight(22);
        setSelectionBackground(Thema.CREAM);
        setSelectionForeground(Color.RED);
        getTableHeader().setForeground(Thema.COKLAT_TUA);
        getTableHeader().setBackground(Thema.CREAM);
        WidgetUtilities.setAutomaticPopUpMenu(this);
        getTableHeader().setBorder(javax.swing.BorderFactory.createLineBorder(Thema.CREAM));
        getTableHeader().setFont(new java.awt.Font("Tahoma", 0, 11));
    }
}

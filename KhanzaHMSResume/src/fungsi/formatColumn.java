/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fungsi;

import java.text.NumberFormat;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author dosen
 */
public class formatColumn {
    private DefaultTableModel model;
    private TableColumnModel tcm;
    private TableColumn tc;
    public static int curr = 0;
    public static int angka = 1;
    public static int kanan = 2;
    public static int tengah = 3;
    public static int tanggal = 4;
    
    public void  setColumnFormat(DefaultTableModel tabMode,int column, int format) {
        tc = tcm.getColumn(column);
        switch (format) {
            case 0:{
                class CurrRender extends DefaultTableCellRenderer{
                public CurrRender(){
                    super();
                    setHorizontalAlignment(SwingConstants.RIGHT);
                }
                @Override
                public void setValue(Object value){
                    if ((value != null) && (value instanceof Number)){
                         Number numberValue = (Number) value;
                         NumberFormat formatter = NumberFormat.getNumberInstance();
                         value = formatter.format(numberValue.doubleValue());
                    }
                    super.setValue(value);
                }                                   
                }
            }
        }
    }

}

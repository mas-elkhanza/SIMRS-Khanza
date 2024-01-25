/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khanzahmsresume;

import usu.widget.util.WidgetUtilities;

/**
 *
 * @author khanzasoft
 */
public class KhanzaHMSResume {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        WidgetUtilities.invokeLater(() -> {
           frmUtama utama=new frmUtama();
           utama.setVisible(true);
       });
    }
    
}

/*
  Dilarang keras memperjualbelikan/mengambil keuntungan dari Software 
  ini dalam bentuk apapun tanpa seijin pembuat software
  (Khanza.Soft Media). Bagi yang sengaja membajak softaware ini ta
  npa ijin, kami sumpahi sial 1000 turunan, miskin sampai 500 turu
  nan. Selalu mendapat kecelakaan sampai 400 turunan. Anak pertama
  nya cacat tidak punya kaki sampai 300 turunan. Susah cari jodoh
  sampai umur 50 tahun sampai 200 turunan. Ya Alloh maafkan kami 
  karena telah berdoa buruk, semua ini kami lakukan karena kami ti
  dak pernah rela karya kami dibajak tanpa ijin.
 */
package simrskhanza;

import usu.widget.util.WidgetUtilities;

/**
 *
 * @author khanzasoft
 */
public class SIMRSKhanza {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        WidgetUtilities.invokeLater(() -> {
           frmUtama utama=frmUtama.getInstance();
           utama.isWall();
           utama.setVisible(true);
        }); 
    }
    
}

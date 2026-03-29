package fungsi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author windiartonugroho
 */
public class pengaturanbillingralan {
    private static final Connection koneksi=koneksiDB.condb();
    private static String CetakNotaSimpanRalan="",CentangDokterRalan="",RincianDokterRalan="",RincianOperasi="",TampilkanPpnObatRalan="",
            CentangObatRalan="",TampilkanTombolNotaRalan="";
    public static void SetBillingRalan() {
        try (
            PreparedStatement ps = koneksi.prepareStatement(
                "select set_nota.cetaknotasimpanralan,set_nota.centangdokterralan,"+
                "set_nota.rinciandokterralan,set_nota.rincianoperasi,set_nota.tampilkan_ppnobat_ralan,"+
                "set_nota.centangobatralan,set_nota.tampilkan_tombol_nota_ralan from set_nota"
            );
            ResultSet rs = ps.executeQuery();
            ){
            if(rs.next()){
                CetakNotaSimpanRalan=rs.getString(1);
                CentangDokterRalan=rs.getString(2);
                RincianDokterRalan=rs.getString(3);
                RincianOperasi=rs.getString(4);
                TampilkanPpnObatRalan=rs.getString(5);
                CentangObatRalan=rs.getString(6);
                TampilkanTombolNotaRalan=rs.getString(7);
            }else{
                CetakNotaSimpanRalan="";
                CentangDokterRalan="";
                RincianDokterRalan="";
                RincianOperasi="";
                TampilkanPpnObatRalan="";
                CentangObatRalan="";
                TampilkanTombolNotaRalan="";
            }
        } catch (Exception e) {
            System.out.println("Notif Pengaturan Billing Ralan : "+e);
        }
    }
    
    public static String getCetakNotaSimpanRalan(){return pengaturanbillingralan.CetakNotaSimpanRalan;}
    public static String getCentangDokterRalan(){return pengaturanbillingralan.CentangDokterRalan;}
    public static String getRincianDokterRalan(){return pengaturanbillingralan.RincianDokterRalan;}
    public static String getRincianOperasi(){return pengaturanbillingralan.RincianOperasi;}
    public static String getTampilkanPpnObatRalan(){return pengaturanbillingralan.TampilkanPpnObatRalan;}
    public static String getCentangObatRalan(){return pengaturanbillingralan.CentangObatRalan;}
    public static String getTampilkanTombolNotaRalan(){return pengaturanbillingralan.TampilkanTombolNotaRalan;}
}

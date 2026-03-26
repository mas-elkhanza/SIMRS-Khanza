package fungsi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author windiartonugroho
 */
public class ppnralan {
    private static final Connection koneksi=koneksiDB.condb();
    private static String TampilPPNRalan="";
    public static void SetPPNRalan() {
        try (
            PreparedStatement ps = koneksi.prepareStatement(
                "select set_nota.tampilkan_ppnobat_ralan from set_nota"
            );
            ResultSet rs = ps.executeQuery();
            ){
            if(rs.next()){
                TampilPPNRalan=rs.getString(1);
            }else{
                TampilPPNRalan="No";
            }
        } catch (Exception e) {
            System.out.println("Notif Tampil PPN Ralan : "+e);
        }
    }
    
    public static String getTampilPPNRalan(){return ppnralan.TampilPPNRalan;}
}

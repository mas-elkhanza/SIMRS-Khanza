package fungsi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author windiartonugroho
 */
public class kodebpjs {
    private static final Connection koneksi=koneksiDB.condb();
    private static String KodeBPJS="";
    public static void SetKodeBPJS() {
        try (
            PreparedStatement ps = koneksi.prepareStatement(
                "select password_asuransi.kd_pj from password_asuransi"
            );
            ResultSet rs = ps.executeQuery();
            ){
            if(rs.next()){
                KodeBPJS=rs.getString(1);
            }
        } catch (Exception e) {
            System.out.println("Notif Kode BPJS : "+e);
        }
    }
    
    public static String getKodeBPJS(){return kodebpjs.KodeBPJS;}
}

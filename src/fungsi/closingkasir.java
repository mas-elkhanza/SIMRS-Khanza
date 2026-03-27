package fungsi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author windiartonugroho
 */
public class closingkasir {
    private static final Connection koneksi=koneksiDB.condb();
    private static String WajibClosingKasir="";
    public static void SetClosingKasir() {
        try (
            PreparedStatement ps = koneksi.prepareStatement(
                "select set_validasi_registrasi.wajib_closing_kasir from set_validasi_registrasi"
            );
            ResultSet rs = ps.executeQuery();
            ){
            if(rs.next()){
                WajibClosingKasir=rs.getString(1);
            }else{
                WajibClosingKasir="No";
            }
        } catch (Exception e) {
            System.out.println("Notif Pengaturan Wajib Closing Kasir : "+e);
        }
    }
    
    public static String getWajibClosingKasir(){return closingkasir.WajibClosingKasir;}
}

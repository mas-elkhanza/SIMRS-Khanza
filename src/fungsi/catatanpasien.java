package fungsi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author windiartonugroho
 */
public class catatanpasien {
    private static final Connection koneksi=koneksiDB.condb();
    private static String TampilkanCatatan="";
    public static void SetCatatanPasien() {
        try (
            PreparedStatement ps = koneksi.prepareStatement(
                "select set_validasi_catatan.tampilkan_catatan from set_validasi_catatan"
            );
            ResultSet rs = ps.executeQuery();
            ){
            if(rs.next()){
                TampilkanCatatan=rs.getString(1);
            }else{
                TampilkanCatatan="No";
            }
        } catch (Exception e) {
            System.out.println("Notif Pengaturan Catatan Pasien : "+e);
        }
    }
    
    public static String getTampilkanCatatan(){return catatanpasien.TampilkanCatatan;}
}

package fungsi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author windiartonugroho
 */
public class akseskamarinapralan {
    private static final Connection koneksi=koneksiDB.condb();
    private static String HakAksesDiRawatJalan="";
    public static void SetKamarInap() {
        try (
            PreparedStatement ps = koneksi.prepareStatement(
                "select set_jam_minimal.kamar_inap_kasir_ralan from set_jam_minimal"
            );
            ResultSet rs = ps.executeQuery();
            ){
            if(rs.next()){
                HakAksesDiRawatJalan=rs.getString(1);
            }else{
                HakAksesDiRawatJalan="No";
            }
        } catch (Exception e) {
            System.out.println("Notif Pengaturan Hak Akses Kamar Inap : "+e);
        }
    }
    
    public static String getHakAksesDiRawatJalan(){return akseskamarinapralan.HakAksesDiRawatJalan;}
}

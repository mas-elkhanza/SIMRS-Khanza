package fungsi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author windiartonugroho
 */
public class lokasidepoutama {
    private static final Connection koneksi=koneksiDB.condb();
    private static String DepoDefault="",AsalStok="";
    public static void SetLokasiDepoUtama() {
        try (
            PreparedStatement ps = koneksi.prepareStatement(
                "select set_lokasi.kd_bangsal,set_lokasi.asal_stok from set_lokasi limit 1"
            );
            ResultSet rs = ps.executeQuery();
            ){
            if(rs.next()){
                DepoDefault=rs.getString(1);
                AsalStok=rs.getString(2);
            }
        } catch (Exception e) {
            System.out.println("Notif Depo : "+e);
        }
    }
    
    public static String getDepoDefault(){return lokasidepoutama.DepoDefault;}
    public static String getAsalStok(){return lokasidepoutama.AsalStok;}
}

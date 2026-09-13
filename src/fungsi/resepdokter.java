package fungsi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author windiartonugroho
 */
public class resepdokter {
    private static final Connection koneksi=koneksiDB.condb();
    private static String ResepPerCaraBayar="";
    public static void SetResepPerCaraBayar() {
        try (
            PreparedStatement ps = koneksi.prepareStatement(
                "select set_tarif.cara_bayar_resep from set_tarif"
            );
            ResultSet rs = ps.executeQuery();
            ){
            if(rs.next()){
                ResepPerCaraBayar=rs.getString(1);
            }else{
                ResepPerCaraBayar="No";
            }
        } catch (Exception e) {
            System.out.println("Notif Pengaturan Resep Dokter Per Cara Bayar : "+e);
        }
    }
    
    public static String getResepPerCaraBayar(){return resepdokter.ResepPerCaraBayar;}
}

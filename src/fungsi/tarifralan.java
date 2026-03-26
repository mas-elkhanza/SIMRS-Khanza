package fungsi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author windiartonugroho
 */
public class tarifralan {
    private static final Connection koneksi=koneksiDB.condb();
    private static String PoliRalan="",CaraBayarRalan="";
    public static void SetTarifRalan() {
        try (
            PreparedStatement ps = koneksi.prepareStatement(
                "select set_tarif.poli_ralan,set_tarif.cara_bayar_ralan from set_tarif"
            );
            ResultSet rs = ps.executeQuery();
            ){
            if(rs.next()){
                PoliRalan=rs.getString(1);
                CaraBayarRalan=rs.getString(2);
            }else{
                PoliRalan="No";
                CaraBayarRalan="No";
            }
        } catch (Exception e) {
            System.out.println("Notif Pengaturan Tarif Ralan : "+e);
        }
    }
    
    public static String getPoliRalan(){return tarifralan.PoliRalan;}
    public static String getCaraBayarRalan(){return tarifralan.CaraBayarRalan;}
}

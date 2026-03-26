package fungsi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author windiartonugroho
 */
public class tarifranap {
    private static final Connection koneksi=koneksiDB.condb();
    private static String RuangRanap="",CaraBayarRanap="",KelasRanap="";
    public static void SetTarifRanap() {
        try (
            PreparedStatement ps = koneksi.prepareStatement(
                "select set_tarif.ruang_ranap,set_tarif.cara_bayar_ranap,set_tarif.kelas_ranap from set_tarif"
            );
            ResultSet rs = ps.executeQuery();
            ){
            if(rs.next()){
                RuangRanap=rs.getString(1);
                CaraBayarRanap=rs.getString(2);
                KelasRanap=rs.getString(3);
            }else{
                RuangRanap="No";
                CaraBayarRanap="No";
                KelasRanap="No";
            }
        } catch (Exception e) {
            System.out.println("Notif Pengaturan Tarif Ranap : "+e);
        }
    }
    
    public static String getRuangRanap(){return tarifranap.RuangRanap;}
    public static String getCaraBayarRanap(){return tarifranap.CaraBayarRanap;}
    public static String getKelasRanap(){return tarifranap.KelasRanap;}
}

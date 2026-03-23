package fungsi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author windiartonugroho
 */
public class embalasetuslah {
    private static final Connection koneksi=koneksiDB.condb();
    private static Double Embalase=null,Tuslah=null;
    public static void SetEmbalaseTuslah() {
        try (
            PreparedStatement ps = koneksi.prepareStatement(
                "select set_embalase.embalase_per_obat,set_embalase.tuslah_per_obat from set_embalase"
            );
            ResultSet rs = ps.executeQuery();
            ){
            if(rs.next()){
                Embalase=rs.getDouble(1);
                Tuslah=rs.getDouble(2);
            }else{
                Embalase=0.0;
                Tuslah=0.0;
            }
        } catch (Exception e) {
            System.out.println("Notif Embalase : "+e);
        }
    }
    
    public static Double getEmbalase(){return embalasetuslah.Embalase;}
    public static Double getTuslah(){return embalasetuslah.Tuslah;}
}

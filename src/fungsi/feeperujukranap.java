package fungsi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author windiartonugroho
 */
public class feeperujukranap {
    private static final Connection koneksi=koneksiDB.condb();
    private static Double FeePerujukRanap=null;
    public static void SetFeeRujukan() {
        try (
            PreparedStatement ps = koneksi.prepareStatement(
                "select set_jam_minimal.feeperujuk from set_jam_minimal"
            );
            ResultSet rs = ps.executeQuery();
            ){
            if(rs.next()){
                FeePerujukRanap=rs.getDouble(1);
            }else{
                FeePerujukRanap=0.0;
            }
        } catch (Exception e) {
            System.out.println("Notif Pengaturan Fee Perujuk Ranap : "+e);
        }
    }
    
    public static Double getFeePerujukRanap(){return feeperujukranap.FeePerujukRanap;}
}

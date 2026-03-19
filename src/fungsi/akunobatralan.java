package fungsi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author windiartonugroho
 */
public class akunobatralan {
    private static final Connection koneksi=koneksiDB.condb();
    private static String Suspen_Piutang_Obat_Ralan="",Obat_Ralan="",HPP_Obat_Rawat_Jalan="",Persediaan_Obat_Rawat_Jalan="";
    public static void SetAkunObatRalan() {
        try (
            PreparedStatement ps = koneksi.prepareStatement(
                "select set_akun_ralan.Suspen_Piutang_Obat_Ralan,set_akun_ralan.Obat_Ralan,set_akun_ralan.HPP_Obat_Rawat_Jalan,set_akun_ralan.Persediaan_Obat_Rawat_Jalan from set_akun_ralan"
            );
            ResultSet rs = ps.executeQuery();
            ){
            if(rs.next()){
                Suspen_Piutang_Obat_Ralan=rs.getString("Suspen_Piutang_Obat_Ralan");
                Obat_Ralan=rs.getString("Obat_Ralan");
                HPP_Obat_Rawat_Jalan=rs.getString("HPP_Obat_Rawat_Jalan");
                Persediaan_Obat_Rawat_Jalan=rs.getString("Persediaan_Obat_Rawat_Jalan");
            }
        } catch (Exception e) {
            System.out.println("Notif Rekening : "+e);
        }
    }
    public static String getSuspen_Piutang_Obat_Ralan(){return akunobatralan.Suspen_Piutang_Obat_Ralan;}
    public static String getObat_Ralan(){return akunobatralan.Obat_Ralan;}
    public static String getHPP_Obat_Rawat_Jalan(){return akunobatralan.HPP_Obat_Rawat_Jalan;}
    public static String getPersediaan_Obat_Rawat_Jalan(){return akunobatralan.Persediaan_Obat_Rawat_Jalan;}
}

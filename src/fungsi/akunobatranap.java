package fungsi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author windiartonugroho
 */
public class akunobatranap {
    private static final Connection koneksi=koneksiDB.condb();
    private static String Suspen_Piutang_Obat_Ranap="",Obat_Ranap="",HPP_Obat_Rawat_Inap="",Persediaan_Obat_Rawat_Inap="";
    public static void SetAkunObatRanap() {
        try (
            PreparedStatement ps = koneksi.prepareStatement(
                "select set_akun_ranap.Suspen_Piutang_Obat_Ranap,set_akun_ranap.Obat_Ranap,set_akun_ranap.HPP_Obat_Rawat_Inap,set_akun_ranap.Persediaan_Obat_Rawat_Inap from set_akun_ranap"
            );
            ResultSet rs = ps.executeQuery();
            ){
            if(rs.next()){
                Suspen_Piutang_Obat_Ranap=rs.getString("Suspen_Piutang_Obat_Ranap");
                Obat_Ranap=rs.getString("Obat_Ranap");
                HPP_Obat_Rawat_Inap=rs.getString("HPP_Obat_Rawat_Inap");
                Persediaan_Obat_Rawat_Inap=rs.getString("Persediaan_Obat_Rawat_Inap");
            }
        } catch (Exception e) {
            System.out.println("Notif Rekening : "+e);
        }
    }
    public static String getSuspen_Piutang_Obat_Ranap(){return akunobatranap.Suspen_Piutang_Obat_Ranap;}
    public static String getObat_Ranap(){return akunobatranap.Obat_Ranap;}
    public static String getHPP_Obat_Rawat_Inap(){return akunobatranap.HPP_Obat_Rawat_Inap;}
    public static String getPersediaan_Obat_Rawat_Inap(){return akunobatranap.Persediaan_Obat_Rawat_Inap;}
}

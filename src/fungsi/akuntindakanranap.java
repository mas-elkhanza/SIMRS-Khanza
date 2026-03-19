package fungsi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author windiartonugroho
 */
public final class akuntindakanranap {
    private static final Connection koneksi=koneksiDB.condb();
    private static String Suspen_Piutang_Tindakan_Ranap="",Tindakan_Ranap="",Beban_Jasa_Medik_Dokter_Tindakan_Ranap="",Utang_Jasa_Medik_Dokter_Tindakan_Ranap="",
            Beban_Jasa_Medik_Paramedis_Tindakan_Ranap="",Utang_Jasa_Medik_Paramedis_Tindakan_Ranap="",Beban_KSO_Tindakan_Ranap="",Utang_KSO_Tindakan_Ranap="",
            Beban_Jasa_Sarana_Tindakan_Ranap="",Utang_Jasa_Sarana_Tindakan_Ranap="",Beban_Jasa_Menejemen_Tindakan_Ranap="",Utang_Jasa_Menejemen_Tindakan_Ranap="",
            HPP_BHP_Tindakan_Ranap="",Persediaan_BHP_Tindakan_Ranap="";
    
    public static void SetAkunTindakanRanap() {
        try (
            PreparedStatement ps = koneksi.prepareStatement(
                "select set_akun_ranap.Suspen_Piutang_Tindakan_Ranap,set_akun_ranap.Tindakan_Ranap,set_akun_ranap.Beban_Jasa_Medik_Dokter_Tindakan_Ranap,"+
                "set_akun_ranap.Utang_Jasa_Medik_Dokter_Tindakan_Ranap,set_akun_ranap.Beban_Jasa_Medik_Paramedis_Tindakan_Ranap,set_akun_ranap.Utang_Jasa_Medik_Paramedis_Tindakan_Ranap,"+
                "set_akun_ranap.Beban_KSO_Tindakan_Ranap,set_akun_ranap.Utang_KSO_Tindakan_Ranap,set_akun_ranap.Beban_Jasa_Sarana_Tindakan_Ranap,set_akun_ranap.Utang_Jasa_Sarana_Tindakan_Ranap,"+
                "set_akun_ranap.Beban_Jasa_Menejemen_Tindakan_Ranap,set_akun_ranap.Utang_Jasa_Menejemen_Tindakan_Ranap,set_akun_ranap.HPP_BHP_Tindakan_Ranap,"+
                "set_akun_ranap.Persediaan_BHP_Tindakan_Ranap from set_akun_ranap"
            );
            ResultSet rs = ps.executeQuery();
            ){
            if(rs.next()){
                Suspen_Piutang_Tindakan_Ranap=rs.getString("Suspen_Piutang_Tindakan_Ranap");
                Tindakan_Ranap=rs.getString("Tindakan_Ranap");
                Beban_Jasa_Medik_Dokter_Tindakan_Ranap=rs.getString("Beban_Jasa_Medik_Dokter_Tindakan_Ranap");
                Utang_Jasa_Medik_Dokter_Tindakan_Ranap=rs.getString("Utang_Jasa_Medik_Dokter_Tindakan_Ranap");
                Beban_Jasa_Medik_Paramedis_Tindakan_Ranap=rs.getString("Beban_Jasa_Medik_Paramedis_Tindakan_Ranap");
                Utang_Jasa_Medik_Paramedis_Tindakan_Ranap=rs.getString("Utang_Jasa_Medik_Paramedis_Tindakan_Ranap");
                Beban_KSO_Tindakan_Ranap=rs.getString("Beban_KSO_Tindakan_Ranap");
                Utang_KSO_Tindakan_Ranap=rs.getString("Utang_KSO_Tindakan_Ranap");
                Beban_Jasa_Sarana_Tindakan_Ranap=rs.getString("Beban_Jasa_Sarana_Tindakan_Ranap");
                Utang_Jasa_Sarana_Tindakan_Ranap=rs.getString("Utang_Jasa_Sarana_Tindakan_Ranap");
                Beban_Jasa_Menejemen_Tindakan_Ranap=rs.getString("Beban_Jasa_Menejemen_Tindakan_Ranap");
                Utang_Jasa_Menejemen_Tindakan_Ranap=rs.getString("Utang_Jasa_Menejemen_Tindakan_Ranap");
                HPP_BHP_Tindakan_Ranap=rs.getString("HPP_BHP_Tindakan_Ranap");
                Persediaan_BHP_Tindakan_Ranap=rs.getString("Persediaan_BHP_Tindakan_Ranap");
            }
        } catch (Exception e) {
            System.out.println("Notif Rekening : "+e);
        }
    }
    
    public static String getSuspen_Piutang_Tindakan_Ranap(){return akuntindakanranap.Suspen_Piutang_Tindakan_Ranap;}
    public static String getTindakan_Ranap(){return akuntindakanranap.Tindakan_Ranap;}
    public static String getBeban_Jasa_Medik_Dokter_Tindakan_Ranap(){return akuntindakanranap.Beban_Jasa_Medik_Dokter_Tindakan_Ranap;}
    public static String getUtang_Jasa_Medik_Dokter_Tindakan_Ranap(){return akuntindakanranap.Utang_Jasa_Medik_Dokter_Tindakan_Ranap;}
    public static String getBeban_Jasa_Medik_Paramedis_Tindakan_Ranap(){return akuntindakanranap.Beban_Jasa_Medik_Paramedis_Tindakan_Ranap;}
    public static String getUtang_Jasa_Medik_Paramedis_Tindakan_Ranap(){return akuntindakanranap.Utang_Jasa_Medik_Paramedis_Tindakan_Ranap;}
    public static String getBeban_KSO_Tindakan_Ranap(){return akuntindakanranap.Beban_KSO_Tindakan_Ranap;}
    public static String getUtang_KSO_Tindakan_Ranap(){return akuntindakanranap.Utang_KSO_Tindakan_Ranap;}
    public static String getBeban_Jasa_Sarana_Tindakan_Ranap(){return akuntindakanranap.Beban_Jasa_Sarana_Tindakan_Ranap;}
    public static String getUtang_Jasa_Sarana_Tindakan_Ranap(){return akuntindakanranap.Utang_Jasa_Sarana_Tindakan_Ranap;}
    public static String getBeban_Jasa_Menejemen_Tindakan_Ranap(){return akuntindakanranap.Beban_Jasa_Menejemen_Tindakan_Ranap;}
    public static String getUtang_Jasa_Menejemen_Tindakan_Ranap(){return akuntindakanranap.Utang_Jasa_Menejemen_Tindakan_Ranap;}
    public static String getHPP_BHP_Tindakan_Ranap(){return akuntindakanranap.HPP_BHP_Tindakan_Ranap;}
    public static String getPersediaan_BHP_Tindakan_Ranap(){return akuntindakanranap.Persediaan_BHP_Tindakan_Ranap;}
}

package fungsi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author windiartonugroho
 */
public final class akuntindakanralan {
    private static final Connection koneksi=koneksiDB.condb();
    private static String Suspen_Piutang_Tindakan_Ralan="",Tindakan_Ralan="",Beban_Jasa_Medik_Dokter_Tindakan_Ralan="",Utang_Jasa_Medik_Dokter_Tindakan_Ralan="",
            Beban_Jasa_Medik_Paramedis_Tindakan_Ralan="",Utang_Jasa_Medik_Paramedis_Tindakan_Ralan="",Beban_KSO_Tindakan_Ralan="",Utang_KSO_Tindakan_Ralan="",
            Beban_Jasa_Sarana_Tindakan_Ralan="",Utang_Jasa_Sarana_Tindakan_Ralan="",HPP_BHP_Tindakan_Ralan="",Persediaan_BHP_Tindakan_Ralan="",
            Beban_Jasa_Menejemen_Tindakan_Ralan="",Utang_Jasa_Menejemen_Tindakan_Ralan="";
    
    public static void SetAkunTindakanRalan() {
        try (
            PreparedStatement ps = koneksi.prepareStatement(
                "select set_akun_ralan.Suspen_Piutang_Tindakan_Ralan,set_akun_ralan.Tindakan_Ralan,set_akun_ralan.Beban_Jasa_Medik_Dokter_Tindakan_Ralan,"+
                "set_akun_ralan.Utang_Jasa_Medik_Dokter_Tindakan_Ralan,set_akun_ralan.Beban_Jasa_Medik_Paramedis_Tindakan_Ralan,"+
                "set_akun_ralan.Utang_Jasa_Medik_Paramedis_Tindakan_Ralan,set_akun_ralan.Beban_KSO_Tindakan_Ralan,"+
                "set_akun_ralan.Utang_KSO_Tindakan_Ralan,set_akun_ralan.Beban_Jasa_Sarana_Tindakan_Ralan,"+
                "set_akun_ralan.Utang_Jasa_Sarana_Tindakan_Ralan,set_akun_ralan.Beban_Jasa_Menejemen_Tindakan_Ralan,"+
                "set_akun_ralan.Utang_Jasa_Menejemen_Tindakan_Ralan,set_akun_ralan.HPP_BHP_Tindakan_Ralan,set_akun_ralan.Persediaan_BHP_Tindakan_Ralan from set_akun_ralan"
            );
            ResultSet rs = ps.executeQuery();
            ){
            if(rs.next()){
                Suspen_Piutang_Tindakan_Ralan=rs.getString("Suspen_Piutang_Tindakan_Ralan");
                Tindakan_Ralan=rs.getString("Tindakan_Ralan");
                Beban_Jasa_Medik_Dokter_Tindakan_Ralan=rs.getString("Beban_Jasa_Medik_Dokter_Tindakan_Ralan");
                Utang_Jasa_Medik_Dokter_Tindakan_Ralan=rs.getString("Utang_Jasa_Medik_Dokter_Tindakan_Ralan");
                Beban_Jasa_Medik_Paramedis_Tindakan_Ralan=rs.getString("Beban_Jasa_Medik_Paramedis_Tindakan_Ralan");
                Utang_Jasa_Medik_Paramedis_Tindakan_Ralan=rs.getString("Utang_Jasa_Medik_Paramedis_Tindakan_Ralan");
                Beban_KSO_Tindakan_Ralan=rs.getString("Beban_KSO_Tindakan_Ralan");
                Utang_KSO_Tindakan_Ralan=rs.getString("Utang_KSO_Tindakan_Ralan");
                Beban_Jasa_Sarana_Tindakan_Ralan=rs.getString("Beban_Jasa_Sarana_Tindakan_Ralan");
                Utang_Jasa_Sarana_Tindakan_Ralan=rs.getString("Utang_Jasa_Sarana_Tindakan_Ralan");
                Beban_Jasa_Menejemen_Tindakan_Ralan=rs.getString("Beban_Jasa_Menejemen_Tindakan_Ralan");
                Utang_Jasa_Menejemen_Tindakan_Ralan=rs.getString("Utang_Jasa_Menejemen_Tindakan_Ralan");
                HPP_BHP_Tindakan_Ralan=rs.getString("HPP_BHP_Tindakan_Ralan");
                Persediaan_BHP_Tindakan_Ralan=rs.getString("Persediaan_BHP_Tindakan_Ralan");
            }
        } catch (Exception e) {
            System.out.println("Notif Rekening : "+e);
        }
    }
    
    public static String getSuspen_Piutang_Tindakan_Ralan(){return akuntindakanralan.Suspen_Piutang_Tindakan_Ralan;}
    public static String getTindakan_Ralan(){return akuntindakanralan.Tindakan_Ralan;}
    public static String getBeban_Jasa_Medik_Dokter_Tindakan_Ralan(){return akuntindakanralan.Beban_Jasa_Medik_Dokter_Tindakan_Ralan;}
    public static String getUtang_Jasa_Medik_Dokter_Tindakan_Ralan(){return akuntindakanralan.Utang_Jasa_Medik_Dokter_Tindakan_Ralan;}
    public static String getBeban_Jasa_Medik_Paramedis_Tindakan_Ralan(){return akuntindakanralan.Beban_Jasa_Medik_Paramedis_Tindakan_Ralan;}
    public static String getUtang_Jasa_Medik_Paramedis_Tindakan_Ralan(){return akuntindakanralan.Utang_Jasa_Medik_Paramedis_Tindakan_Ralan;}
    public static String getBeban_KSO_Tindakan_Ralan(){return akuntindakanralan.Beban_KSO_Tindakan_Ralan;}
    public static String getUtang_KSO_Tindakan_Ralan(){return akuntindakanralan.Utang_KSO_Tindakan_Ralan;}
    public static String getBeban_Jasa_Sarana_Tindakan_Ralan(){return akuntindakanralan.Beban_Jasa_Sarana_Tindakan_Ralan;}
    public static String getUtang_Jasa_Sarana_Tindakan_Ralan(){return akuntindakanralan.Utang_Jasa_Sarana_Tindakan_Ralan;}
    public static String getBeban_Jasa_Menejemen_Tindakan_Ralan(){return akuntindakanralan.Beban_Jasa_Menejemen_Tindakan_Ralan;}
    public static String getUtang_Jasa_Menejemen_Tindakan_Ralan(){return akuntindakanralan.Utang_Jasa_Menejemen_Tindakan_Ralan;}
    public static String getHPP_BHP_Tindakan_Ralan(){return akuntindakanralan.HPP_BHP_Tindakan_Ralan;}
    public static String getPersediaan_BHP_Tindakan_Ralan(){return akuntindakanralan.Persediaan_BHP_Tindakan_Ralan;}
}

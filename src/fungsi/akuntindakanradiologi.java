package fungsi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author windiartonugroho
 */
public final class akuntindakanradiologi {
    private static final Connection koneksi=koneksiDB.condb();
    private static String Suspen_Piutang_Radiologi_Ranap="",Radiologi_Ranap="",Beban_Jasa_Medik_Dokter_Radiologi_Ranap="",Utang_Jasa_Medik_Dokter_Radiologi_Ranap="",
            Beban_Jasa_Medik_Petugas_Radiologi_Ranap="",Utang_Jasa_Medik_Petugas_Radiologi_Ranap="",Beban_Kso_Radiologi_Ranap="",Utang_Kso_Radiologi_Ranap="",
            HPP_Persediaan_Radiologi_Rawat_Inap="",Persediaan_BHP_Radiologi_Rawat_Inap="",Beban_Jasa_Sarana_Radiologi_Ranap="",Utang_Jasa_Sarana_Radiologi_Ranap="",
            Beban_Jasa_Perujuk_Radiologi_Ranap="",Utang_Jasa_Perujuk_Radiologi_Ranap="",Beban_Jasa_Menejemen_Radiologi_Ranap="",Utang_Jasa_Menejemen_Radiologi_Ranap="",
            Suspen_Piutang_Radiologi_Ralan="",Radiologi_Ralan="",Beban_Jasa_Medik_Dokter_Radiologi_Ralan="",Utang_Jasa_Medik_Dokter_Radiologi_Ralan="",
            Beban_Jasa_Medik_Petugas_Radiologi_Ralan="",Utang_Jasa_Medik_Petugas_Radiologi_Ralan="",Beban_Kso_Radiologi_Ralan="",Utang_Kso_Radiologi_Ralan="",
            HPP_Persediaan_Radiologi_Rawat_Jalan="",Persediaan_BHP_Radiologi_Rawat_Jalan="",Beban_Jasa_Sarana_Radiologi_Ralan="",Utang_Jasa_Sarana_Radiologi_Ralan="",
            Beban_Jasa_Perujuk_Radiologi_Ralan="",Utang_Jasa_Perujuk_Radiologi_Ralan="",Beban_Jasa_Menejemen_Radiologi_Ralan="",Utang_Jasa_Menejemen_Radiologi_Ralan="";
    
    public static void SetAkunTindakanRadiologi() {
        try (
            PreparedStatement ps = koneksi.prepareStatement(
                "select set_akun_ranap.Suspen_Piutang_Radiologi_Ranap,set_akun_ranap.Radiologi_Ranap,"+
                "set_akun_ranap.Beban_Jasa_Medik_Dokter_Radiologi_Ranap,set_akun_ranap.Utang_Jasa_Medik_Dokter_Radiologi_Ranap,"+
                "set_akun_ranap.Beban_Jasa_Medik_Petugas_Radiologi_Ranap,set_akun_ranap.Utang_Jasa_Medik_Petugas_Radiologi_Ranap,"+
                "set_akun_ranap.Beban_Kso_Radiologi_Ranap,set_akun_ranap.Utang_Kso_Radiologi_Ranap,"+
                "set_akun_ranap.HPP_Persediaan_Radiologi_Rawat_Inap,set_akun_ranap.Persediaan_BHP_Radiologi_Rawat_Inap,"+
                "set_akun_ranap.Beban_Jasa_Sarana_Radiologi_Ranap,set_akun_ranap.Utang_Jasa_Sarana_Radiologi_Ranap,"+
                "set_akun_ranap.Beban_Jasa_Perujuk_Radiologi_Ranap,set_akun_ranap.Utang_Jasa_Perujuk_Radiologi_Ranap,"+
                "set_akun_ranap.Beban_Jasa_Menejemen_Radiologi_Ranap,set_akun_ranap.Utang_Jasa_Menejemen_Radiologi_Ranap from set_akun_ranap"
            );
            ResultSet rs = ps.executeQuery();
            ){
            if(rs.next()){
                Suspen_Piutang_Radiologi_Ranap=rs.getString("Suspen_Piutang_Radiologi_Ranap");
                Radiologi_Ranap=rs.getString("Radiologi_Ranap");
                Beban_Jasa_Medik_Dokter_Radiologi_Ranap=rs.getString("Beban_Jasa_Medik_Dokter_Radiologi_Ranap");
                Utang_Jasa_Medik_Dokter_Radiologi_Ranap=rs.getString("Utang_Jasa_Medik_Dokter_Radiologi_Ranap");
                Beban_Jasa_Medik_Petugas_Radiologi_Ranap=rs.getString("Beban_Jasa_Medik_Petugas_Radiologi_Ranap");
                Utang_Jasa_Medik_Petugas_Radiologi_Ranap=rs.getString("Utang_Jasa_Medik_Petugas_Radiologi_Ranap");
                Beban_Kso_Radiologi_Ranap=rs.getString("Beban_Kso_Radiologi_Ranap");
                Utang_Kso_Radiologi_Ranap=rs.getString("Utang_Kso_Radiologi_Ranap");
                HPP_Persediaan_Radiologi_Rawat_Inap=rs.getString("HPP_Persediaan_Radiologi_Rawat_Inap");
                Persediaan_BHP_Radiologi_Rawat_Inap=rs.getString("Persediaan_BHP_Radiologi_Rawat_Inap");
                Beban_Jasa_Sarana_Radiologi_Ranap=rs.getString("Beban_Jasa_Sarana_Radiologi_Ranap");
                Utang_Jasa_Sarana_Radiologi_Ranap=rs.getString("Utang_Jasa_Sarana_Radiologi_Ranap");
                Beban_Jasa_Perujuk_Radiologi_Ranap=rs.getString("Beban_Jasa_Perujuk_Radiologi_Ranap");
                Utang_Jasa_Perujuk_Radiologi_Ranap=rs.getString("Utang_Jasa_Perujuk_Radiologi_Ranap");
                Beban_Jasa_Menejemen_Radiologi_Ranap=rs.getString("Beban_Jasa_Menejemen_Radiologi_Ranap");
                Utang_Jasa_Menejemen_Radiologi_Ranap=rs.getString("Utang_Jasa_Menejemen_Radiologi_Ranap");
            }
        } catch (Exception e) {
            System.out.println("Notif Rekening : "+e);
        }
        
        try (
            PreparedStatement ps = koneksi.prepareStatement(
                "select set_akun_ralan.Suspen_Piutang_Radiologi_Ralan,set_akun_ralan.Radiologi_Ralan,"+
                "set_akun_ralan.Beban_Jasa_Medik_Dokter_Radiologi_Ralan,set_akun_ralan.Utang_Jasa_Medik_Dokter_Radiologi_Ralan,"+
                "set_akun_ralan.Beban_Jasa_Medik_Petugas_Radiologi_Ralan,set_akun_ralan.Utang_Jasa_Medik_Petugas_Radiologi_Ralan,"+
                "set_akun_ralan.Beban_Kso_Radiologi_Ralan,set_akun_ralan.Utang_Kso_Radiologi_Ralan,"+
                "set_akun_ralan.HPP_Persediaan_Radiologi_Rawat_Jalan,set_akun_ralan.Persediaan_BHP_Radiologi_Rawat_Jalan,"+
                "set_akun_ralan.Beban_Jasa_Sarana_Radiologi_Ralan,set_akun_ralan.Utang_Jasa_Sarana_Radiologi_Ralan,"+
                "set_akun_ralan.Beban_Jasa_Perujuk_Radiologi_Ralan,set_akun_ralan.Utang_Jasa_Perujuk_Radiologi_Ralan,"+
                "set_akun_ralan.Beban_Jasa_Menejemen_Radiologi_Ralan,set_akun_ralan.Utang_Jasa_Menejemen_Radiologi_Ralan from set_akun_ralan"
            );
            ResultSet rs = ps.executeQuery();
            ){
            if(rs.next()){
                Suspen_Piutang_Radiologi_Ralan=rs.getString("Suspen_Piutang_Radiologi_Ralan");
                Radiologi_Ralan=rs.getString("Radiologi_Ralan");
                Beban_Jasa_Medik_Dokter_Radiologi_Ralan=rs.getString("Beban_Jasa_Medik_Dokter_Radiologi_Ralan");
                Utang_Jasa_Medik_Dokter_Radiologi_Ralan=rs.getString("Utang_Jasa_Medik_Dokter_Radiologi_Ralan");
                Beban_Jasa_Medik_Petugas_Radiologi_Ralan=rs.getString("Beban_Jasa_Medik_Petugas_Radiologi_Ralan");
                Utang_Jasa_Medik_Petugas_Radiologi_Ralan=rs.getString("Utang_Jasa_Medik_Petugas_Radiologi_Ralan");
                Beban_Kso_Radiologi_Ralan=rs.getString("Beban_Kso_Radiologi_Ralan");
                Utang_Kso_Radiologi_Ralan=rs.getString("Utang_Kso_Radiologi_Ralan");
                HPP_Persediaan_Radiologi_Rawat_Jalan=rs.getString("HPP_Persediaan_Radiologi_Rawat_Jalan");
                Persediaan_BHP_Radiologi_Rawat_Jalan=rs.getString("Persediaan_BHP_Radiologi_Rawat_Jalan");
                Beban_Jasa_Sarana_Radiologi_Ralan=rs.getString("Beban_Jasa_Sarana_Radiologi_Ralan");
                Utang_Jasa_Sarana_Radiologi_Ralan=rs.getString("Utang_Jasa_Sarana_Radiologi_Ralan");
                Beban_Jasa_Perujuk_Radiologi_Ralan=rs.getString("Beban_Jasa_Perujuk_Radiologi_Ralan");
                Utang_Jasa_Perujuk_Radiologi_Ralan=rs.getString("Utang_Jasa_Perujuk_Radiologi_Ralan");
                Beban_Jasa_Menejemen_Radiologi_Ralan=rs.getString("Beban_Jasa_Menejemen_Radiologi_Ralan");
                Utang_Jasa_Menejemen_Radiologi_Ralan=rs.getString("Utang_Jasa_Menejemen_Radiologi_Ralan");
            }
        } catch (Exception e) {
            System.out.println("Notif Rekening : "+e);
        }
    }
    
    public static String getSuspen_Piutang_Radiologi_Ranap(){return akuntindakanradiologi.Suspen_Piutang_Radiologi_Ranap;}
    public static String getRadiologi_Ranap(){return akuntindakanradiologi.Radiologi_Ranap;}
    public static String getBeban_Jasa_Medik_Dokter_Radiologi_Ranap(){return akuntindakanradiologi.Beban_Jasa_Medik_Dokter_Radiologi_Ranap;}
    public static String getUtang_Jasa_Medik_Dokter_Radiologi_Ranap(){return akuntindakanradiologi.Utang_Jasa_Medik_Dokter_Radiologi_Ranap;}
    public static String getBeban_Jasa_Medik_Petugas_Radiologi_Ranap(){return akuntindakanradiologi.Beban_Jasa_Medik_Petugas_Radiologi_Ranap;}
    public static String getUtang_Jasa_Medik_Petugas_Radiologi_Ranap(){return akuntindakanradiologi.Utang_Jasa_Medik_Petugas_Radiologi_Ranap;}
    public static String getBeban_Kso_Radiologi_Ranap(){return akuntindakanradiologi.Beban_Kso_Radiologi_Ranap;}
    public static String getUtang_Kso_Radiologi_Ranap(){return akuntindakanradiologi.Utang_Kso_Radiologi_Ranap;}
    public static String getHPP_Persediaan_Radiologi_Rawat_Inap(){return akuntindakanradiologi.HPP_Persediaan_Radiologi_Rawat_Inap;}
    public static String getPersediaan_BHP_Radiologi_Rawat_Inap(){return akuntindakanradiologi.Persediaan_BHP_Radiologi_Rawat_Inap;}
    public static String getBeban_Jasa_Sarana_Radiologi_Ranap(){return akuntindakanradiologi.Beban_Jasa_Sarana_Radiologi_Ranap;}
    public static String getUtang_Jasa_Sarana_Radiologi_Ranap(){return akuntindakanradiologi.Utang_Jasa_Sarana_Radiologi_Ranap;}
    public static String getBeban_Jasa_Perujuk_Radiologi_Ranap(){return akuntindakanradiologi.Beban_Jasa_Perujuk_Radiologi_Ranap;}
    public static String getUtang_Jasa_Perujuk_Radiologi_Ranap(){return akuntindakanradiologi.Utang_Jasa_Perujuk_Radiologi_Ranap;}
    public static String getBeban_Jasa_Menejemen_Radiologi_Ranap(){return akuntindakanradiologi.Beban_Jasa_Menejemen_Radiologi_Ranap;}
    public static String getUtang_Jasa_Menejemen_Radiologi_Ranap(){return akuntindakanradiologi.Utang_Jasa_Menejemen_Radiologi_Ranap;}
    public static String getSuspen_Piutang_Radiologi_Ralan(){return akuntindakanradiologi.Suspen_Piutang_Radiologi_Ralan;}
    public static String getRadiologi_Ralan(){return akuntindakanradiologi.Radiologi_Ralan;}
    public static String getBeban_Jasa_Medik_Dokter_Radiologi_Ralan(){return akuntindakanradiologi.Beban_Jasa_Medik_Dokter_Radiologi_Ralan;}
    public static String getUtang_Jasa_Medik_Dokter_Radiologi_Ralan(){return akuntindakanradiologi.Utang_Jasa_Medik_Dokter_Radiologi_Ralan;}
    public static String getBeban_Jasa_Medik_Petugas_Radiologi_Ralan(){return akuntindakanradiologi.Beban_Jasa_Medik_Petugas_Radiologi_Ralan;}
    public static String getUtang_Jasa_Medik_Petugas_Radiologi_Ralan(){return akuntindakanradiologi.Utang_Jasa_Medik_Petugas_Radiologi_Ralan;}
    public static String getBeban_Kso_Radiologi_Ralan(){return akuntindakanradiologi.Beban_Kso_Radiologi_Ralan;}
    public static String getUtang_Kso_Radiologi_Ralan(){return akuntindakanradiologi.Utang_Kso_Radiologi_Ralan;}
    public static String getHPP_Persediaan_Radiologi_Rawat_Jalan(){return akuntindakanradiologi.HPP_Persediaan_Radiologi_Rawat_Jalan;}
    public static String getPersediaan_BHP_Radiologi_Rawat_Jalan(){return akuntindakanradiologi.Persediaan_BHP_Radiologi_Rawat_Jalan;}
    public static String getBeban_Jasa_Sarana_Radiologi_Ralan(){return akuntindakanradiologi.Beban_Jasa_Sarana_Radiologi_Ralan;}
    public static String getUtang_Jasa_Sarana_Radiologi_Ralan(){return akuntindakanradiologi.Utang_Jasa_Sarana_Radiologi_Ralan;}
    public static String getBeban_Jasa_Perujuk_Radiologi_Ralan(){return akuntindakanradiologi.Beban_Jasa_Perujuk_Radiologi_Ralan;}
    public static String getUtang_Jasa_Perujuk_Radiologi_Ralan(){return akuntindakanradiologi.Utang_Jasa_Perujuk_Radiologi_Ralan;}
    public static String getBeban_Jasa_Menejemen_Radiologi_Ralan(){return akuntindakanradiologi.Beban_Jasa_Menejemen_Radiologi_Ralan;}
    public static String getUtang_Jasa_Menejemen_Radiologi_Ralan(){return akuntindakanradiologi.Utang_Jasa_Menejemen_Radiologi_Ralan;}
}

package fungsi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author windiartonugroho
 */
public final class akuntindakanlaborat {
    private static final Connection koneksi=koneksiDB.condb();
    private static String Suspen_Piutang_Laborat_Ranap="",Laborat_Ranap="",Beban_Jasa_Medik_Dokter_Laborat_Ranap="",Utang_Jasa_Medik_Dokter_Laborat_Ranap="",
            Beban_Jasa_Medik_Petugas_Laborat_Ranap="",Utang_Jasa_Medik_Petugas_Laborat_Ranap="",Beban_Kso_Laborat_Ranap="",Utang_Kso_Laborat_Ranap="",
            HPP_Persediaan_Laborat_Rawat_inap="",Persediaan_BHP_Laborat_Rawat_Inap="",Beban_Jasa_Sarana_Laborat_Ranap="",Utang_Jasa_Sarana_Laborat_Ranap="",
            Beban_Jasa_Perujuk_Laborat_Ranap="",Utang_Jasa_Perujuk_Laborat_Ranap="",Beban_Jasa_Menejemen_Laborat_Ranap="",Utang_Jasa_Menejemen_Laborat_Ranap="",
            Suspen_Piutang_Laborat_Ralan="",Laborat_Ralan="",Beban_Jasa_Medik_Dokter_Laborat_Ralan="",Utang_Jasa_Medik_Dokter_Laborat_Ralan="",
            Beban_Jasa_Medik_Petugas_Laborat_Ralan="",Utang_Jasa_Medik_Petugas_Laborat_Ralan="",Beban_Kso_Laborat_Ralan="",Utang_Kso_Laborat_Ralan="",
            HPP_Persediaan_Laborat_Rawat_Jalan="",Persediaan_BHP_Laborat_Rawat_Jalan="",Beban_Jasa_Sarana_Laborat_Ralan="",Utang_Jasa_Sarana_Laborat_Ralan="",
            Beban_Jasa_Perujuk_Laborat_Ralan="",Utang_Jasa_Perujuk_Laborat_Ralan="",Beban_Jasa_Menejemen_Laborat_Ralan="",Utang_Jasa_Menejemen_Laborat_Ralan="";
    
    public static void SetAkunTindakanLaborat() {
        try (
            PreparedStatement ps = koneksi.prepareStatement(
                "select set_akun_ranap.Suspen_Piutang_Laborat_Ranap,set_akun_ranap.Laborat_Ranap,"+
                "set_akun_ranap.Beban_Jasa_Medik_Dokter_Laborat_Ranap,set_akun_ranap.Utang_Jasa_Medik_Dokter_Laborat_Ranap,"+
                "set_akun_ranap.Beban_Jasa_Medik_Petugas_Laborat_Ranap,set_akun_ranap.Utang_Jasa_Medik_Petugas_Laborat_Ranap,"+
                "set_akun_ranap.Beban_Kso_Laborat_Ranap,set_akun_ranap.Utang_Kso_Laborat_Ranap,"+
                "set_akun_ranap.HPP_Persediaan_Laborat_Rawat_inap,set_akun_ranap.Persediaan_BHP_Laborat_Rawat_Inap,"+
                "set_akun_ranap.Beban_Jasa_Sarana_Laborat_Ranap,set_akun_ranap.Utang_Jasa_Sarana_Laborat_Ranap,"+
                "set_akun_ranap.Beban_Jasa_Perujuk_Laborat_Ranap,set_akun_ranap.Utang_Jasa_Perujuk_Laborat_Ranap,"+
                "set_akun_ranap.Beban_Jasa_Menejemen_Laborat_Ranap,set_akun_ranap.Utang_Jasa_Menejemen_Laborat_Ranap from set_akun_ranap"
            );
            ResultSet rs = ps.executeQuery();
            ){
            if(rs.next()){
                Suspen_Piutang_Laborat_Ranap=rs.getString("Suspen_Piutang_Laborat_Ranap");
                Laborat_Ranap=rs.getString("Laborat_Ranap");
                Beban_Jasa_Medik_Dokter_Laborat_Ranap=rs.getString("Beban_Jasa_Medik_Dokter_Laborat_Ranap");
                Utang_Jasa_Medik_Dokter_Laborat_Ranap=rs.getString("Utang_Jasa_Medik_Dokter_Laborat_Ranap");
                Beban_Jasa_Medik_Petugas_Laborat_Ranap=rs.getString("Beban_Jasa_Medik_Petugas_Laborat_Ranap");
                Utang_Jasa_Medik_Petugas_Laborat_Ranap=rs.getString("Utang_Jasa_Medik_Petugas_Laborat_Ranap");
                Beban_Kso_Laborat_Ranap=rs.getString("Beban_Kso_Laborat_Ranap");
                Utang_Kso_Laborat_Ranap=rs.getString("Utang_Kso_Laborat_Ranap");
                HPP_Persediaan_Laborat_Rawat_inap=rs.getString("HPP_Persediaan_Laborat_Rawat_inap");
                Persediaan_BHP_Laborat_Rawat_Inap=rs.getString("Persediaan_BHP_Laborat_Rawat_Inap");
                Beban_Jasa_Sarana_Laborat_Ranap=rs.getString("Beban_Jasa_Sarana_Laborat_Ranap");
                Utang_Jasa_Sarana_Laborat_Ranap=rs.getString("Utang_Jasa_Sarana_Laborat_Ranap");
                Beban_Jasa_Perujuk_Laborat_Ranap=rs.getString("Beban_Jasa_Perujuk_Laborat_Ranap");
                Utang_Jasa_Perujuk_Laborat_Ranap=rs.getString("Utang_Jasa_Perujuk_Laborat_Ranap");
                Beban_Jasa_Menejemen_Laborat_Ranap=rs.getString("Beban_Jasa_Menejemen_Laborat_Ranap");
                Utang_Jasa_Menejemen_Laborat_Ranap=rs.getString("Utang_Jasa_Menejemen_Laborat_Ranap");
            }
        } catch (Exception e) {
            System.out.println("Notif Rekening : "+e);
        }
        
        try (
            PreparedStatement ps = koneksi.prepareStatement(
                "select set_akun_ralan.Suspen_Piutang_Laborat_Ralan,set_akun_ralan.Laborat_Ralan,"+
                "set_akun_ralan.Beban_Jasa_Medik_Dokter_Laborat_Ralan,set_akun_ralan.Utang_Jasa_Medik_Dokter_Laborat_Ralan,"+
                "set_akun_ralan.Beban_Jasa_Medik_Petugas_Laborat_Ralan,set_akun_ralan.Utang_Jasa_Medik_Petugas_Laborat_Ralan,"+
                "set_akun_ralan.Beban_Kso_Laborat_Ralan,set_akun_ralan.Utang_Kso_Laborat_Ralan,"+
                "set_akun_ralan.HPP_Persediaan_Laborat_Rawat_Jalan,set_akun_ralan.Persediaan_BHP_Laborat_Rawat_Jalan,"+
                "set_akun_ralan.Beban_Jasa_Sarana_Laborat_Ralan,set_akun_ralan.Utang_Jasa_Sarana_Laborat_Ralan,"+
                "set_akun_ralan.Beban_Jasa_Perujuk_Laborat_Ralan,set_akun_ralan.Utang_Jasa_Perujuk_Laborat_Ralan,"+
                "set_akun_ralan.Beban_Jasa_Menejemen_Laborat_Ralan,set_akun_ralan.Utang_Jasa_Menejemen_Laborat_Ralan from set_akun_ralan"
            );
            ResultSet rs = ps.executeQuery();
            ){
            if(rs.next()){
                Suspen_Piutang_Laborat_Ralan=rs.getString("Suspen_Piutang_Laborat_Ralan");
                Laborat_Ralan=rs.getString("Laborat_Ralan");
                Beban_Jasa_Medik_Dokter_Laborat_Ralan=rs.getString("Beban_Jasa_Medik_Dokter_Laborat_Ralan");
                Utang_Jasa_Medik_Dokter_Laborat_Ralan=rs.getString("Utang_Jasa_Medik_Dokter_Laborat_Ralan");
                Beban_Jasa_Medik_Petugas_Laborat_Ralan=rs.getString("Beban_Jasa_Medik_Petugas_Laborat_Ralan");
                Utang_Jasa_Medik_Petugas_Laborat_Ralan=rs.getString("Utang_Jasa_Medik_Petugas_Laborat_Ralan");
                Beban_Kso_Laborat_Ralan=rs.getString("Beban_Kso_Laborat_Ralan");
                Utang_Kso_Laborat_Ralan=rs.getString("Utang_Kso_Laborat_Ralan");
                HPP_Persediaan_Laborat_Rawat_Jalan=rs.getString("HPP_Persediaan_Laborat_Rawat_Jalan");
                Persediaan_BHP_Laborat_Rawat_Jalan=rs.getString("Persediaan_BHP_Laborat_Rawat_Jalan");
                Beban_Jasa_Sarana_Laborat_Ralan=rs.getString("Beban_Jasa_Sarana_Laborat_Ralan");
                Utang_Jasa_Sarana_Laborat_Ralan=rs.getString("Utang_Jasa_Sarana_Laborat_Ralan");
                Beban_Jasa_Perujuk_Laborat_Ralan=rs.getString("Beban_Jasa_Perujuk_Laborat_Ralan");
                Utang_Jasa_Perujuk_Laborat_Ralan=rs.getString("Utang_Jasa_Perujuk_Laborat_Ralan");
                Beban_Jasa_Menejemen_Laborat_Ralan=rs.getString("Beban_Jasa_Menejemen_Laborat_Ralan");
                Utang_Jasa_Menejemen_Laborat_Ralan=rs.getString("Utang_Jasa_Menejemen_Laborat_Ralan");
            }
        } catch (Exception e) {
            System.out.println("Notif Rekening : "+e);
        }
    }
    
    public static String getSuspen_Piutang_Laborat_Ranap(){return akuntindakanlaborat.Suspen_Piutang_Laborat_Ranap;}
    public static String getLaborat_Ranap(){return akuntindakanlaborat.Laborat_Ranap;}
    public static String getBeban_Jasa_Medik_Dokter_Laborat_Ranap(){return akuntindakanlaborat.Beban_Jasa_Medik_Dokter_Laborat_Ranap;}
    public static String getUtang_Jasa_Medik_Dokter_Laborat_Ranap(){return akuntindakanlaborat.Utang_Jasa_Medik_Dokter_Laborat_Ranap;}
    public static String getBeban_Jasa_Medik_Petugas_Laborat_Ranap(){return akuntindakanlaborat.Beban_Jasa_Medik_Petugas_Laborat_Ranap;}
    public static String getUtang_Jasa_Medik_Petugas_Laborat_Ranap(){return akuntindakanlaborat.Utang_Jasa_Medik_Petugas_Laborat_Ranap;}
    public static String getBeban_Kso_Laborat_Ranap(){return akuntindakanlaborat.Beban_Kso_Laborat_Ranap;}
    public static String getUtang_Kso_Laborat_Ranap(){return akuntindakanlaborat.Utang_Kso_Laborat_Ranap;}
    public static String getHPP_Persediaan_Laborat_Rawat_inap(){return akuntindakanlaborat.HPP_Persediaan_Laborat_Rawat_inap;}
    public static String getPersediaan_BHP_Laborat_Rawat_Inap(){return akuntindakanlaborat.Persediaan_BHP_Laborat_Rawat_Inap;}
    public static String getBeban_Jasa_Sarana_Laborat_Ranap(){return akuntindakanlaborat.Beban_Jasa_Sarana_Laborat_Ranap;}
    public static String getUtang_Jasa_Sarana_Laborat_Ranap(){return akuntindakanlaborat.Utang_Jasa_Sarana_Laborat_Ranap;}
    public static String getBeban_Jasa_Perujuk_Laborat_Ranap(){return akuntindakanlaborat.Beban_Jasa_Perujuk_Laborat_Ranap;}
    public static String getUtang_Jasa_Perujuk_Laborat_Ranap(){return akuntindakanlaborat.Utang_Jasa_Perujuk_Laborat_Ranap;}
    public static String getBeban_Jasa_Menejemen_Laborat_Ranap(){return akuntindakanlaborat.Beban_Jasa_Menejemen_Laborat_Ranap;}
    public static String getUtang_Jasa_Menejemen_Laborat_Ranap(){return akuntindakanlaborat.Utang_Jasa_Menejemen_Laborat_Ranap;}
    public static String getSuspen_Piutang_Laborat_Ralan(){return akuntindakanlaborat.Suspen_Piutang_Laborat_Ralan;}
    public static String getLaborat_Ralan(){return akuntindakanlaborat.Laborat_Ralan;}
    public static String getBeban_Jasa_Medik_Dokter_Laborat_Ralan(){return akuntindakanlaborat.Beban_Jasa_Medik_Dokter_Laborat_Ralan;}
    public static String getUtang_Jasa_Medik_Dokter_Laborat_Ralan(){return akuntindakanlaborat.Utang_Jasa_Medik_Dokter_Laborat_Ralan;}
    public static String getBeban_Jasa_Medik_Petugas_Laborat_Ralan(){return akuntindakanlaborat.Beban_Jasa_Medik_Petugas_Laborat_Ralan;}
    public static String getUtang_Jasa_Medik_Petugas_Laborat_Ralan(){return akuntindakanlaborat.Utang_Jasa_Medik_Petugas_Laborat_Ralan;}
    public static String getBeban_Kso_Laborat_Ralan(){return akuntindakanlaborat.Beban_Kso_Laborat_Ralan;}
    public static String getUtang_Kso_Laborat_Ralan(){return akuntindakanlaborat.Utang_Kso_Laborat_Ralan;}
    public static String getHPP_Persediaan_Laborat_Rawat_Jalan(){return akuntindakanlaborat.HPP_Persediaan_Laborat_Rawat_Jalan;}
    public static String getPersediaan_BHP_Laborat_Rawat_Jalan(){return akuntindakanlaborat.Persediaan_BHP_Laborat_Rawat_Jalan;}
    public static String getBeban_Jasa_Sarana_Laborat_Ralan(){return akuntindakanlaborat.Beban_Jasa_Sarana_Laborat_Ralan;}
    public static String getUtang_Jasa_Sarana_Laborat_Ralan(){return akuntindakanlaborat.Utang_Jasa_Sarana_Laborat_Ralan;}
    public static String getBeban_Jasa_Perujuk_Laborat_Ralan(){return akuntindakanlaborat.Beban_Jasa_Perujuk_Laborat_Ralan;}
    public static String getUtang_Jasa_Perujuk_Laborat_Ralan(){return akuntindakanlaborat.Utang_Jasa_Perujuk_Laborat_Ralan;}
    public static String getBeban_Jasa_Menejemen_Laborat_Ralan(){return akuntindakanlaborat.Beban_Jasa_Menejemen_Laborat_Ralan;}
    public static String getUtang_Jasa_Menejemen_Laborat_Ralan(){return akuntindakanlaborat.Utang_Jasa_Menejemen_Laborat_Ralan;}
}

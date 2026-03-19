package fungsi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author windiartonugroho
 */
public final class akuntindakanoperasi {
    private static final Connection koneksi=koneksiDB.condb();
    private static String Suspen_Piutang_Operasi_Ranap="",Operasi_Ranap="",Beban_Jasa_Medik_Dokter_Operasi_Ranap="",Utang_Jasa_Medik_Dokter_Operasi_Ranap="",
            Beban_Jasa_Medik_Paramedis_Operasi_Ranap="",Utang_Jasa_Medik_Paramedis_Operasi_Ranap="",HPP_Obat_Operasi_Ranap="",Persediaan_Obat_Kamar_Operasi_Ranap="",
            Suspen_Piutang_Operasi_Ralan="",Operasi_Ralan="",Beban_Jasa_Medik_Dokter_Operasi_Ralan="",Utang_Jasa_Medik_Dokter_Operasi_Ralan="",
            Beban_Jasa_Medik_Paramedis_Operasi_Ralan="",Utang_Jasa_Medik_Paramedis_Operasi_Ralan="",HPP_Obat_Operasi_Ralan="",Persediaan_Obat_Kamar_Operasi_Ralan="";
    
    public static void SetAkunTindakanOperasi() {
        try (
            PreparedStatement ps = koneksi.prepareStatement(
                "select set_akun_ralan.Suspen_Piutang_Operasi_Ralan,set_akun_ralan.Operasi_Ralan,set_akun_ralan.Beban_Jasa_Medik_Dokter_Operasi_Ralan,"+
                "set_akun_ralan.Utang_Jasa_Medik_Dokter_Operasi_Ralan,set_akun_ralan.Beban_Jasa_Medik_Paramedis_Operasi_Ralan,"+
                "set_akun_ralan.Utang_Jasa_Medik_Paramedis_Operasi_Ralan,set_akun_ralan.HPP_Obat_Operasi_Ralan,"+
                "set_akun_ralan.Persediaan_Obat_Kamar_Operasi_Ralan from set_akun_ralan"
            );
            ResultSet rs = ps.executeQuery();
            ){
            if(rs.next()){
                Suspen_Piutang_Operasi_Ralan=rs.getString("Suspen_Piutang_Operasi_Ralan");
                Operasi_Ralan=rs.getString("Operasi_Ralan");
                Beban_Jasa_Medik_Dokter_Operasi_Ralan=rs.getString("Beban_Jasa_Medik_Dokter_Operasi_Ralan");
                Utang_Jasa_Medik_Dokter_Operasi_Ralan=rs.getString("Utang_Jasa_Medik_Dokter_Operasi_Ralan");
                Beban_Jasa_Medik_Paramedis_Operasi_Ralan=rs.getString("Beban_Jasa_Medik_Paramedis_Operasi_Ralan");
                Utang_Jasa_Medik_Paramedis_Operasi_Ralan=rs.getString("Utang_Jasa_Medik_Paramedis_Operasi_Ralan");
                HPP_Obat_Operasi_Ralan=rs.getString("HPP_Obat_Operasi_Ralan");
                Persediaan_Obat_Kamar_Operasi_Ralan=rs.getString("Persediaan_Obat_Kamar_Operasi_Ralan");
            }
        } catch (Exception e) {
            System.out.println("Notif Rekening : "+e);
        }
        
        try (
            PreparedStatement ps = koneksi.prepareStatement(
                "select set_akun_ranap.Suspen_Piutang_Operasi_Ranap,set_akun_ranap.Operasi_Ranap,set_akun_ranap.Beban_Jasa_Medik_Dokter_Operasi_Ranap,"+
                "set_akun_ranap.Utang_Jasa_Medik_Dokter_Operasi_Ranap,set_akun_ranap.Beban_Jasa_Medik_Paramedis_Operasi_Ranap,"+
                "set_akun_ranap.Utang_Jasa_Medik_Paramedis_Operasi_Ranap,set_akun_ranap.HPP_Obat_Operasi_Ranap from set_akun_ranap"
            );
            ResultSet rs = ps.executeQuery();
            ){
            if(rs.next()){
                Suspen_Piutang_Operasi_Ranap=rs.getString("Suspen_Piutang_Operasi_Ranap");
                Operasi_Ranap=rs.getString("Operasi_Ranap");
                Beban_Jasa_Medik_Dokter_Operasi_Ranap=rs.getString("Beban_Jasa_Medik_Dokter_Operasi_Ranap");
                Utang_Jasa_Medik_Dokter_Operasi_Ranap=rs.getString("Utang_Jasa_Medik_Dokter_Operasi_Ranap");
                Beban_Jasa_Medik_Paramedis_Operasi_Ranap=rs.getString("Beban_Jasa_Medik_Paramedis_Operasi_Ranap");
                Utang_Jasa_Medik_Paramedis_Operasi_Ranap=rs.getString("Utang_Jasa_Medik_Paramedis_Operasi_Ranap");
                HPP_Obat_Operasi_Ranap=rs.getString("HPP_Obat_Operasi_Ranap");
            }
        } catch (Exception e) {
            System.out.println("Notif Rekening : "+e);
        }
        
        try (
                PreparedStatement ps = koneksi.prepareStatement(
                    "select set_akun_ranap2.Persediaan_Obat_Kamar_Operasi_Ranap from set_akun_ranap2"
                );
                ResultSet rs = ps.executeQuery();
            ){
            if(rs.next()){
                Persediaan_Obat_Kamar_Operasi_Ranap=rs.getString("Persediaan_Obat_Kamar_Operasi_Ranap");
            }
        } catch (Exception e) {
            System.out.println("Notif Rekening : "+e);
        }
    }
    
    public static String getSuspen_Piutang_Operasi_Ranap(){return akuntindakanoperasi.Suspen_Piutang_Operasi_Ranap;}
    public static String getOperasi_Ranap(){return akuntindakanoperasi.Operasi_Ranap;}
    public static String getBeban_Jasa_Medik_Dokter_Operasi_Ranap(){return akuntindakanoperasi.Beban_Jasa_Medik_Dokter_Operasi_Ranap;}
    public static String getUtang_Jasa_Medik_Dokter_Operasi_Ranap(){return akuntindakanoperasi.Utang_Jasa_Medik_Dokter_Operasi_Ranap;}
    public static String getBeban_Jasa_Medik_Paramedis_Operasi_Ranap(){return akuntindakanoperasi.Beban_Jasa_Medik_Paramedis_Operasi_Ranap;}
    public static String getUtang_Jasa_Medik_Paramedis_Operasi_Ranap(){return akuntindakanoperasi.Utang_Jasa_Medik_Paramedis_Operasi_Ranap;}
    public static String getHPP_Obat_Operasi_Ranap(){return akuntindakanoperasi.HPP_Obat_Operasi_Ranap;}
    public static String getPersediaan_Obat_Kamar_Operasi_Ranap(){return akuntindakanoperasi.Persediaan_Obat_Kamar_Operasi_Ranap;}
    public static String getSuspen_Piutang_Operasi_Ralan(){return akuntindakanoperasi.Suspen_Piutang_Operasi_Ralan;}
    public static String getOperasi_Ralan(){return akuntindakanoperasi.Operasi_Ralan;}
    public static String getBeban_Jasa_Medik_Dokter_Operasi_Ralan(){return akuntindakanoperasi.Beban_Jasa_Medik_Dokter_Operasi_Ralan;}
    public static String getUtang_Jasa_Medik_Dokter_Operasi_Ralan(){return akuntindakanoperasi.Utang_Jasa_Medik_Dokter_Operasi_Ralan;}
    public static String getBeban_Jasa_Medik_Paramedis_Operasi_Ralan(){return akuntindakanoperasi.Beban_Jasa_Medik_Paramedis_Operasi_Ralan;}
    public static String getUtang_Jasa_Medik_Paramedis_Operasi_Ralan(){return akuntindakanoperasi.Utang_Jasa_Medik_Paramedis_Operasi_Ralan;}
    public static String getHPP_Obat_Operasi_Ralan(){return akuntindakanoperasi.HPP_Obat_Operasi_Ralan;}
    public static String getPersediaan_Obat_Kamar_Operasi_Ralan(){return akuntindakanoperasi.Persediaan_Obat_Kamar_Operasi_Ralan;}
}

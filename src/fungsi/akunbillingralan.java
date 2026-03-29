package fungsi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author windiartonugroho
 */
public class akunbillingralan {
    private static final Connection koneksi=koneksiDB.condb();
    private static String Tindakan_Ralan="",Laborat_Ralan="",Radiologi_Ralan="",Obat_Ralan="",Registrasi_Ralan="",Tambahan_Ralan="",Potongan_Ralan="",
            Obat_Langsung_Ralan="",Operasi_Ralan="",PPNKeluaran="";
    public static void SetAkunBillingRalan() {
        try (
            PreparedStatement ps = koneksi.prepareStatement(
                "select set_akun_ralan.Suspen_Piutang_Tindakan_Ralan,set_akun_ralan.Suspen_Piutang_Laborat_Ralan,"+
                "set_akun_ralan.Suspen_Piutang_Radiologi_Ralan,set_akun_ralan.Suspen_Piutang_Obat_Ralan,"+
                "set_akun_ralan.Obat_Ralan,set_akun_ralan.Registrasi_Ralan,set_akun_ralan.Tambahan_Ralan,"+
                "set_akun_ralan.Potongan_Ralan,set_akun_ralan.Suspen_Piutang_Operasi_Ralan from set_akun_ralan"
            );
            ResultSet rs = ps.executeQuery();
            ){
            if(rs.next()){
                Tindakan_Ralan=rs.getString("Suspen_Piutang_Tindakan_Ralan");
                Laborat_Ralan=rs.getString("Suspen_Piutang_Laborat_Ralan");
                Radiologi_Ralan=rs.getString("Suspen_Piutang_Radiologi_Ralan");
                Obat_Ralan=rs.getString("Suspen_Piutang_Obat_Ralan");
                Obat_Langsung_Ralan=rs.getString("Obat_Ralan");
                Registrasi_Ralan=rs.getString("Registrasi_Ralan");
                Tambahan_Ralan=rs.getString("Tambahan_Ralan");
                Potongan_Ralan=rs.getString("Potongan_Ralan");
                Operasi_Ralan=rs.getString("Suspen_Piutang_Operasi_Ralan");
            }
        } catch (Exception e) {
            System.out.println("Notif Rekening : "+e);
        }
        
        try (
            PreparedStatement ps = koneksi.prepareStatement(
                "select set_akun.PPN_Keluaran from set_akun"
            );
            ResultSet rs = ps.executeQuery();
            ){
            if(rs.next()){
                PPNKeluaran=rs.getString(1);
            }else{
                PPNKeluaran="";
            }
        } catch (Exception e) {
            System.out.println("Notif Pengaturan Akun PPN Keluaran Ralan : "+e);
        }
    }
    
    public static String getTindakan_Ralan(){return akunbillingralan.Tindakan_Ralan;}
    public static String getLaborat_Ralan(){return akunbillingralan.Laborat_Ralan;}
    public static String getRadiologi_Ralan(){return akunbillingralan.Radiologi_Ralan;}
    public static String getObat_Ralan(){return akunbillingralan.Obat_Ralan;}
    public static String getRegistrasi_Ralan(){return akunbillingralan.Registrasi_Ralan;}
    public static String getTambahan_Ralan(){return akunbillingralan.Tambahan_Ralan;}
    public static String getPotongan_Ralan(){return akunbillingralan.Potongan_Ralan;}
    public static String getObat_Langsung_Ralan(){return akunbillingralan.Obat_Langsung_Ralan;}
    public static String getOperasi_Ralan(){return akunbillingralan.Operasi_Ralan;}
    public static String getPPNKeluaran(){return akunbillingralan.PPNKeluaran;}
}

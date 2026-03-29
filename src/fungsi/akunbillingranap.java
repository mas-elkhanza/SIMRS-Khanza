package fungsi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author windiartonugroho
 */
public class akunbillingranap {
    private static final Connection koneksi=koneksiDB.condb();
    private static String Tindakan_Ranap="",Laborat_Ranap="",Radiologi_Ranap="",Obat_Ranap="",Registrasi_Ranap="",Persediaan_Obat_Rawat_Inap="",
            Tambahan_Ranap="",Potongan_Ranap="",Retur_Obat_Ranap="",Resep_Pulang_Ranap="",Kamar_Inap="",Operasi_Ranap="",Harian_Ranap="",
            Uang_Muka_Ranap="",Sisa_Uang_Muka_Ranap="",HPP_Obat_Rawat_Inap="",Service_Ranap="",Obat_Langsung_Ranap="",PPNKeluaran="";
    public static void SetAkunBillingRanap() {
        try (
            PreparedStatement ps = koneksi.prepareStatement(
                "select set_akun_ranap.Suspen_Piutang_Tindakan_Ranap,set_akun_ranap.Suspen_Piutang_Laborat_Ranap,"+
                "set_akun_ranap.Suspen_Piutang_Radiologi_Ranap,set_akun_ranap.Suspen_Piutang_Obat_Ranap,"+
                "set_akun_ranap.Obat_Ranap,set_akun_ranap.Registrasi_Ranap,set_akun_ranap.Tambahan_Ranap,"+
                "set_akun_ranap.Potongan_Ranap,set_akun_ranap.Retur_Obat_Ranap,set_akun_ranap.HPP_Obat_Rawat_Inap,"+
                "set_akun_ranap.Persediaan_Obat_Rawat_Inap,set_akun_ranap.Resep_Pulang_Ranap,set_akun_ranap.Kamar_Inap,"+
                "set_akun_ranap.Suspen_Piutang_Operasi_Ranap,set_akun_ranap.Service_Ranap from set_akun_ranap"
            );
            ResultSet rs = ps.executeQuery();
            ){
            if(rs.next()){
                Tindakan_Ranap=rs.getString("Suspen_Piutang_Tindakan_Ranap");
                Laborat_Ranap=rs.getString("Suspen_Piutang_Laborat_Ranap");
                Radiologi_Ranap=rs.getString("Suspen_Piutang_Radiologi_Ranap");
                Obat_Ranap=rs.getString("Suspen_Piutang_Obat_Ranap");
                Obat_Langsung_Ranap=rs.getString("Obat_Ranap");
                Registrasi_Ranap=rs.getString("Registrasi_Ranap");
                Tambahan_Ranap=rs.getString("Tambahan_Ranap");
                Potongan_Ranap=rs.getString("Potongan_Ranap");
                Retur_Obat_Ranap=rs.getString("Retur_Obat_Ranap");
                HPP_Obat_Rawat_Inap=rs.getString("HPP_Obat_Rawat_Inap");
                Persediaan_Obat_Rawat_Inap=rs.getString("Persediaan_Obat_Rawat_Inap");
                Resep_Pulang_Ranap=rs.getString("Resep_Pulang_Ranap");
                Kamar_Inap=rs.getString("Kamar_Inap");
                Operasi_Ranap=rs.getString("Suspen_Piutang_Operasi_Ranap");
                Service_Ranap=rs.getString("Service_Ranap");
            }
        } catch (Exception e) {
            System.out.println("Notif Rekening : "+e);
        }
        
        try (
            PreparedStatement ps = koneksi.prepareStatement(
                "select set_akun_ranap2.Harian_Ranap,set_akun_ranap2.Uang_Muka_Ranap,set_akun_ranap2.Sisa_Uang_Muka_Ranap from set_akun_ranap2"
            );
            ResultSet rs = ps.executeQuery();
            ){
            if(rs.next()){
                Harian_Ranap=rs.getString("Harian_Ranap");
                Uang_Muka_Ranap=rs.getString("Uang_Muka_Ranap");
                Sisa_Uang_Muka_Ranap=rs.getString("Sisa_Uang_Muka_Ranap");
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
    
    public static String getTindakan_Ranap(){return akunbillingranap.Tindakan_Ranap;}
    public static String getLaborat_Ranap(){return akunbillingranap.Laborat_Ranap;}
    public static String getRadiologi_Ranap(){return akunbillingranap.Radiologi_Ranap;}
    public static String getObat_Ranap(){return akunbillingranap.Obat_Ranap;}
    public static String getRegistrasi_Ranap(){return akunbillingranap.Registrasi_Ranap;}
    public static String getPersediaan_Obat_Rawat_Inap(){return akunbillingranap.Persediaan_Obat_Rawat_Inap;}
    public static String getTambahan_Ranap(){return akunbillingranap.Tambahan_Ranap;}
    public static String getPotongan_Ranap(){return akunbillingranap.Potongan_Ranap;}
    public static String getRetur_Obat_Ranap(){return akunbillingranap.Retur_Obat_Ranap;}
    public static String getResep_Pulang_Ranap(){return akunbillingranap.Resep_Pulang_Ranap;}
    public static String getKamar_Inap(){return akunbillingranap.Kamar_Inap;}
    public static String getOperasi_Ranap(){return akunbillingranap.Operasi_Ranap;}
    public static String getHarian_Ranap(){return akunbillingranap.Harian_Ranap;}
    public static String getUang_Muka_Ranap(){return akunbillingranap.Uang_Muka_Ranap;}
    public static String getSisa_Uang_Muka_Ranap(){return akunbillingranap.Sisa_Uang_Muka_Ranap;}
    public static String getHPP_Obat_Rawat_Inap(){return akunbillingranap.HPP_Obat_Rawat_Inap;}
    public static String getService_Ranap(){return akunbillingranap.Service_Ranap;}
    public static String getObat_Langsung_Ranap(){return akunbillingranap.Obat_Langsung_Ranap;}
    public static String getPPNKeluaran(){return akunbillingranap.PPNKeluaran;}
}

package fungsi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author windiartonugroho
 */
public class pengaturankamarinap {
    private static final Connection koneksi=koneksiDB.condb();
    private static Double JamMinimalKamar=null,PersenHargaKamarBayi=null;
    private static String AktifkanDiagnosaAkhir="",HitungHariAwal="",AktifkanHapusDataSalah="",UbahStatusKamar="";
    public static void SetKamarInap() {
        try (
            PreparedStatement ps = koneksi.prepareStatement(
                "select set_jam_minimal.lamajam,set_jam_minimal.bayi,set_jam_minimal.diagnosaakhir,set_jam_minimal.hariawal,"+
                "set_jam_minimal.aktifkan_hapus_data_salah,set_jam_minimal.ubah_status_kamar from set_jam_minimal"
            );
            ResultSet rs = ps.executeQuery();
            ){
            if(rs.next()){
                JamMinimalKamar=rs.getDouble(1);
                PersenHargaKamarBayi=rs.getDouble(2);
                AktifkanDiagnosaAkhir=rs.getString(3);
                HitungHariAwal=rs.getString(4);
                AktifkanHapusDataSalah=rs.getString(5);
                UbahStatusKamar=rs.getString(6);
            }else{
                JamMinimalKamar=0.0;
                PersenHargaKamarBayi=0.0;
                AktifkanDiagnosaAkhir="No";
                HitungHariAwal="No";
                AktifkanHapusDataSalah="No";
                UbahStatusKamar="No";
            }
        } catch (Exception e) {
            System.out.println("Notif Pengaturan Kamar Inap: "+e);
        }
    }
    
    public static Double getJamMinimalKamar(){return pengaturankamarinap.JamMinimalKamar;}
    public static Double getPersenHargaKamarBayi(){return pengaturankamarinap.PersenHargaKamarBayi;}
    public static String getAktifkanDiagnosaAkhir(){return pengaturankamarinap.AktifkanDiagnosaAkhir;}
    public static String getHitungHariAwal(){return pengaturankamarinap.HitungHariAwal;}
    public static String getAktifkanHapusDataSalah(){return pengaturankamarinap.AktifkanHapusDataSalah;}
    public static String getUbahStatusKamar(){return pengaturankamarinap.UbahStatusKamar;}
}

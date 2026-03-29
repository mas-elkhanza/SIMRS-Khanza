package fungsi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author windiartonugroho
 */
public class pengaturanbillingranap {
    private static final Connection koneksi=koneksiDB.condb();
    private static String CetakNotaSimpanRanap="",CentangDokterRanap="",AdministrasiRanap="",RincianDokterRanap="",RincianOperasi="",TampilkanPpnObatRanap="",
            CentangObatRanap="",TampilkanTombolNotaRanap="";
    public static void SetBillingRanap() {
        try (
            PreparedStatement ps = koneksi.prepareStatement(
                "select set_nota.cetaknotasimpanranap,set_nota.centangdokterranap,set_nota.rinciandokterranap,"+
                "set_nota.rincianoperasi,set_nota.tampilkan_administrasi_di_billingranap,set_nota.tampilkan_ppnobat_ranap,"+
                "set_nota.centangobatranap,set_nota.tampilkan_tombol_nota_ranap from set_nota"
            );
            ResultSet rs = ps.executeQuery();
            ){
            if(rs.next()){
                CetakNotaSimpanRanap=rs.getString(1);
                CentangDokterRanap=rs.getString(2);
                RincianDokterRanap=rs.getString(3);
                RincianOperasi=rs.getString(4);
                AdministrasiRanap=rs.getString(5);
                TampilkanPpnObatRanap=rs.getString(6);
                CentangObatRanap=rs.getString(7);
                TampilkanTombolNotaRanap=rs.getString(8);
            }else{
                CetakNotaSimpanRanap="";
                CentangDokterRanap="";
                RincianDokterRanap="";
                RincianOperasi="";
                AdministrasiRanap="";
                TampilkanPpnObatRanap="";
                CentangObatRanap="";
                TampilkanTombolNotaRanap="";
            }
        } catch (Exception e) {
            System.out.println("Notif Pengaturan Billing Ranap : "+e);
        }
    }
    
    public static String getCetakNotaSimpanRanap(){return pengaturanbillingranap.CetakNotaSimpanRanap;}
    public static String getCentangDokterRanap(){return pengaturanbillingranap.CentangDokterRanap;}
    public static String getRincianDokterRanap(){return pengaturanbillingranap.RincianDokterRanap;}
    public static String getRincianOperasi(){return pengaturanbillingranap.RincianOperasi;}
    public static String getAdministrasiRanap(){return pengaturanbillingranap.AdministrasiRanap;}
    public static String getTampilkanPpnObatRanap(){return pengaturanbillingranap.TampilkanPpnObatRanap;}
    public static String getCentangObatRanap(){return pengaturanbillingranap.CentangObatRanap;}
    public static String getTampilkanTombolNotaRanap(){return pengaturanbillingranap.TampilkanTombolNotaRanap;}
}

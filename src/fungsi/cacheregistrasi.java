package fungsi;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author windiartonugroho
 */
public class cacheregistrasi {
    private static Date TanggalAwal=null,TanggalAkhir=null;
    private static String KeyWord=null,Dokter=null,Poli=null;
    private static ArrayList<Object[]> DataPasien = new ArrayList<>();
    
    public static void SetTanggalAwal(Date tanggal) {TanggalAwal=tanggal;}
    public static Date getTanggalAwal(){return cacheregistrasi.TanggalAwal;}
    
    public static void SetTanggalAkhir(Date tanggal) {TanggalAkhir=tanggal;}
    public static Date getTanggalAkhir(){return cacheregistrasi.TanggalAkhir;}
    
    public static void SetKeyWord(String data) {KeyWord=data;}
    public static String getKeyWord(){return cacheregistrasi.KeyWord;}
    
    public static void SetDokter(String data) {Dokter=data;}
    public static String getDokter(){return cacheregistrasi.Dokter;}
    
    public static void SetPoli(String data) {Poli=data;}
    public static String getPoli(){return cacheregistrasi.Poli;}
    
    public static void SetDataPasien(ArrayList<Object[]> data) {DataPasien = data;}
    public static ArrayList<Object[]> getDataPasien(){return cacheregistrasi.DataPasien;}
    public static void clearDataPasien(){DataPasien.clear();}
}

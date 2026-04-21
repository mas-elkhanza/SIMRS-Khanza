package fungsi;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author windiartonugroho
 */
public class cacherawatjalan {
    private static Date TanggalAwal=null,TanggalAkhir=null;
    private static String KeyWord=null,Dokter=null,Poli=null,Penjab=null,StatusBayar=null,StatusPelayanan=null;
    private static ArrayList<Object[]> DataPasien = new ArrayList<>();
    
    public static void SetTanggalAwal(Date tanggal) {TanggalAwal=tanggal;}
    public static Date getTanggalAwal(){return cacherawatjalan.TanggalAwal;}
    
    public static void SetTanggalAkhir(Date tanggal) {TanggalAkhir=tanggal;}
    public static Date getTanggalAkhir(){return cacherawatjalan.TanggalAkhir;}
    
    public static void SetKeyWord(String data) {KeyWord=data;}
    public static String getKeyWord(){return cacherawatjalan.KeyWord;}
    
    public static void SetDokter(String data) {Dokter=data;}
    public static String getDokter(){return cacherawatjalan.Dokter;}
    
    public static void SetPoli(String data) {Poli=data;}
    public static String getPoli(){return cacherawatjalan.Poli;}
    
    public static void SetPenjab(String data) {Penjab=data;}
    public static String getPenjab(){return cacherawatjalan.Penjab;}
    
    public static void SetStatusBayar(String data) {StatusBayar=data;}
    public static String getStatusBayar(){return cacherawatjalan.StatusBayar;}
    
    public static void SetStatusPelayanan(String data) {StatusPelayanan=data;}
    public static String getStatusPelayanan(){return cacherawatjalan.StatusPelayanan;}
    
    public static void SetDataPasien(ArrayList<Object[]> data) {DataPasien = new ArrayList<>(data);}
    public static ArrayList<Object[]> getDataPasien(){return cacherawatjalan.DataPasien;}
    public static void clearDataPasien(){DataPasien.clear();}
}

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
    private static final ArrayList<Object[]> DataPasien = new ArrayList<>();
    private static boolean isLogout = false;
    
    public static void SetTanggalAwal(Date tanggal) {TanggalAwal=tanggal;}
    public static Date getTanggalAwal(){return TanggalAwal;}
    
    public static void SetTanggalAkhir(Date tanggal) {TanggalAkhir=tanggal;}
    public static Date getTanggalAkhir(){return TanggalAkhir;}
    
    public static void SetKeyWord(String data) {KeyWord=data;}
    public static String getKeyWord(){return KeyWord;}
    
    public static void SetDokter(String data) {Dokter=data;}
    public static String getDokter(){return Dokter;}
    
    public static void SetPoli(String data) {Poli=data;}
    public static String getPoli(){return Poli;}
    
    public static void SetPenjab(String data) {Penjab=data;}
    public static String getPenjab(){return Penjab;}
    
    public static void SetStatusBayar(String data) {StatusBayar=data;}
    public static String getStatusBayar(){return StatusBayar;}
    
    public static void SetStatusPelayanan(String data) {StatusPelayanan=data;}
    public static String getStatusPelayanan(){return StatusPelayanan;}
    
    public static void setLogout(boolean status) { isLogout = status; }
    public static boolean isLogout() { return isLogout; }
    
    public static void setDataPasien(Object[] baris) { DataPasien.add(baris); }
    public static ArrayList<Object[]> getDataPasien() { return DataPasien; }
    public static void clearDataPasien() { DataPasien.clear(); }
    public static void reset() {
        isLogout = true;
        TanggalAwal=null;
        TanggalAkhir=null;
        KeyWord=null;
        Dokter=null;
        Poli=null;
        Penjab=null;
        StatusBayar=null;
        StatusPelayanan=null;
        DataPasien.clear();
    }
}

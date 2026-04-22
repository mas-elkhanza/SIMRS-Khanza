package fungsi;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author windiartonugroho
 */
public class cacheigd {
    private static Date TanggalAwal=null,TanggalAkhir=null;
    private static String KeyWord=null;
    private static final ArrayList<Object[]> DataPasien = new ArrayList<>();
    
    public static void SetTanggalAwal(Date tanggal) {TanggalAwal=tanggal;}
    public static Date getTanggalAwal(){return TanggalAwal;}
    
    public static void SetTanggalAkhir(Date tanggal) {TanggalAkhir=tanggal;}
    public static Date getTanggalAkhir(){return TanggalAkhir;}
    
    public static void SetKeyWord(String data) {KeyWord=data;}
    public static String getKeyWord(){return KeyWord;}
    
    public static void setDataPasien(Object[] baris) { DataPasien.add(baris); }
    public static ArrayList<Object[]> getDataPasien() { return DataPasien; }
    public static void clearDataPasien() { DataPasien.clear(); }
    public static void reset() {
        TanggalAwal = null;
        TanggalAkhir = null;
        KeyWord = null;
        DataPasien.clear();
    }
}

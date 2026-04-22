package fungsi;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author windiartonugroho
 */
public class cacherawatinap {
    private static boolean PilihanBelumPulang=true,PilihanTanggalMasuk=false,PilihanTanggalPulang=false;
    private static Date TanggalMasuk1=null,TanggalMasuk2=null,TanggalPulang1=null,TanggalPulang2=null;
    private static String KeyWord=null,StatusBayar=null,Kamar=null;
    private static final ArrayList<Object[]> DataPasien = new ArrayList<>();
    
    public static void SetPilihanBelumPulang(boolean status) {PilihanBelumPulang=status;}
    public static boolean getPilihanBelumPulang(){return PilihanBelumPulang;}
    
    public static void SetPilihanTanggalMasuk(boolean status) {PilihanTanggalMasuk=status;}
    public static boolean getPilihanTanggalMasuk(){return PilihanTanggalMasuk;}
    
    public static void SetPilihanTanggalPulang(boolean status) {PilihanTanggalPulang=status;}
    public static boolean getPilihanTanggalPulang(){return PilihanTanggalPulang;}
    
    public static void SetTanggalMasuk1(Date tanggal) {TanggalMasuk1=tanggal;}
    public static Date getTanggalMasuk1(){return TanggalMasuk1;}
    
    public static void SetTanggalMasuk2(Date tanggal) {TanggalMasuk2=tanggal;}
    public static Date getTanggalMasuk2(){return TanggalMasuk2;}
    
    public static void SetTanggalPulang1(Date tanggal) {TanggalPulang1=tanggal;}
    public static Date getTanggalPulang1(){return TanggalPulang1;}
    
    public static void SetTanggalPulang2(Date tanggal) {TanggalPulang2=tanggal;}
    public static Date getTanggalPulang2(){return TanggalPulang2;}
    
    public static void SetKeyWord(String data) {KeyWord=data;}
    public static String getKeyWord(){return KeyWord;}
    
    public static void SetStatusBayar(String data) {StatusBayar=data;}
    public static String getStatusBayar(){return StatusBayar;}
    
    public static void SetKamar(String data) {Kamar=data;}
    public static String getKamar(){return Kamar;}
    
    public static void setDataPasien(Object[] baris) { DataPasien.add(baris); }
    public static ArrayList<Object[]> getDataPasien() { return DataPasien; }
    public static void clearDataPasien() { DataPasien.clear(); }
    public static void reset() {
        TanggalMasuk1=null;
        TanggalMasuk2=null;
        TanggalPulang1=null;
        TanggalPulang2=null;
        KeyWord=null;
        StatusBayar=null;
        Kamar=null;
        DataPasien.clear();
    }
}

package fungsi;

/**
 *
 * @author windiartonugroho
 */
public class cacherawatinap {
    private static String KodePetugas=null,NamaPetugas=null,KodePetugas2=null,NamaPetugas2=null,KodePemeriksa=null,NamaPemeriksa=null,JabatanPemeriksa;
    
    public static void SetKodePetugas(String data) {KodePetugas=data;}
    public static String getKodePetugas(){return KodePetugas;}
    
    public static void SetNamaPetugas(String data) {NamaPetugas=data;}
    public static String getNamaPetugas(){return NamaPetugas;}
    
    public static void SetKodePetugas2(String data) {KodePetugas2=data;}
    public static String getKodePetugas2(){return KodePetugas2;}
    
    public static void SetNamaPetugas2(String data) {NamaPetugas2=data;}
    public static String getNamaPetugas2(){return NamaPetugas2;}
    
    public static void SetKodePemeriksa(String data) {KodePemeriksa=data;}
    public static String getKodePemeriksa(){return KodePemeriksa;}
    
    public static void SetNamaPemeriksa(String data) {NamaPemeriksa=data;}
    public static String getNamaPemeriksa(){return NamaPemeriksa;}
    
    public static void SetJabatanPemeriksa(String data) {JabatanPemeriksa=data;}
    public static String getJabatanPemeriksa(){return JabatanPemeriksa;}
    
    public static void reset() {
        KodePetugas=null;
        NamaPetugas=null;
        KodePetugas2=null;
        NamaPetugas2=null;
        KodePemeriksa=null;
        NamaPemeriksa=null;
        JabatanPemeriksa=null;
    }
}

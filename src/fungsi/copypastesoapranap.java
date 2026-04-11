package fungsi;

/**
 *
 * @author windiartonugroho
 */
public class copypastesoapranap {
    private static String Subjek="",Objek="",Asesmen="",Plan="",Implementasi="",Evaluasi="";
    public static void SetCatatanPasien(String subjek,String objek,String asesmen,String plan,String implementasi,String evaluasi) {
        Subjek=subjek;
        Objek=objek;
        Asesmen=asesmen;
        Plan=plan;
        Implementasi=implementasi;
        Evaluasi=evaluasi;
    }
    
    public static String getDataSubjek(){return copypastesoapranap.Subjek;}
    public static String getDataObjek(){return copypastesoapranap.Objek;}
    public static String getDataAsesmen(){return copypastesoapranap.Asesmen;}
    public static String getDataPlan(){return copypastesoapranap.Plan;}
    public static String getDataImplementasi(){return copypastesoapranap.Implementasi;}
    public static String getDataEvaluasi(){return copypastesoapranap.Evaluasi;}
}

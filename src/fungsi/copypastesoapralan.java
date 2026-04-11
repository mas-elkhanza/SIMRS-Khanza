package fungsi;

/**
 *
 * @author windiartonugroho
 */
public class copypastesoapralan {
    private static String Subjek="",Objek="",Asesmen="",Plan="",Implementasi="",Evaluasi="";
    public static void SetCatatanPasien(String subjek,String objek,String asesmen,String plan,String implementasi,String evaluasi) {
        Subjek=subjek;
        Objek=objek;
        Asesmen=asesmen;
        Plan=plan;
        Implementasi=implementasi;
        Evaluasi=evaluasi;
    }
    
    public static String getDataSubjek(){return copypastesoapralan.Subjek;}
    public static String getDataObjek(){return copypastesoapralan.Objek;}
    public static String getDataAsesmen(){return copypastesoapralan.Asesmen;}
    public static String getDataPlan(){return copypastesoapralan.Plan;}
    public static String getDataImplementasi(){return copypastesoapralan.Implementasi;}
    public static String getDataEvaluasi(){return copypastesoapralan.Evaluasi;}
}

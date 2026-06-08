//modifikasi dari source Mas Ikhsan, RS Karina Medika Purwakarta
package rekammedis;

import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import kepegawaian.DlgCariDokter;
import kepegawaian.DlgCariPetugas;


/**
 *
 * @author perpustakaan
 */
public final class RMMCU extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;
    private DlgCariDokter dokter;
    private DlgCariPetugas petugas;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private volatile boolean ceksukses = false;
    private String TANGGALMUNDUR="yes";

    private static final String[] PENILAIAN_MCU_COLUMNS = new String[]{
        "no_rawat","tanggal","year","kd_dokter","kd_petugas","note1","nama_pasien","surname","mcu_group","dass_21","phy_exam","conc_lab","conc_radiologi",
        "conc_ecg","conc_spirometry","conc_audiometry","kesimpulan1","no_rkm_medis","tmp_lahir","tgl_lahir","jk",
        "no_tlp","suku_bangsa","stts_nikah","doe","yoe","job_title","activities","hobby","other_job","posisi_kerja",
        "job_involves_driving_or_operating_mobile_equipment","job_involves_working_at_heights","job_involves_clerical_office_based_or_administrative",
        "job_involves_requires_colour_vision","job_involves_potential_dust_exposure","job_involves_catering_staff_including_food_handlers",
        "job_involves_exposing_to_other_potential_dangerous","med_hist_head_injury_or_contussion","med_hist_fainting_blackouts_epilepsy",
        "med_hist_visual_changes","med_hist_hearing_loss","med_hist_nose_sinus_throat_trouble_more_4_weeks","med_hist_gynaecological_problems",
        "med_hist_chronic_skin_problem","med_hist_chronic_diarrhea","med_hist_anorexia_more_4_weeks","med_hist_gastritis",
        "med_hist_jaundice_hepatitis","med_hist_chronic_cough_more_4_weeks","med_hist_haemorhoid","med_hist_chronic_abdominal_pain",
        "med_hist_diabetes","med_hist_asthma","med_hist_allergies","med_hist_tuberculosis_bronchitis","med_hist_psychiatric_disorder",
        "med_hist_sexual_transmitted_diseases","med_hist_unusual_change_of_weight_more_5kg_per_month","med_hist_hypertension",
        "med_hist_chest_pain_heart_disease","med_hist_malaria_tropical_disease","med_hist_surgery_operation","med_hist_back_pain_more_4_weeks",
        "med_hist_thypoid_fever","med_hist_swollen_or_painful_joints","med_hist_kidney_problem_urinary_stones","med_hist_other_chronical_diseases",
        "family_history_father","family_history_mother","family_history_siblings","family_history_other","cigarettes_perday","alcohol_gr_week",
        "prescribed_medication","prescribed_medication_2","any_allergies","hb","wbc","esr","bl_group","gamaa_gt","sgot","sgpt","urea","creatinin","glucose",
        "random_glucose","total_cholestrol","protein","blood","bilirubin","malaria","tpha","mantoux_test","leukosit","lab_others",
        "ova","culture","cysta","parasites1","pnemunosicosis","pnemunosicosis2","ILO_clasification","ILO_clasification2","oth_abnormal","tb1","tb2","page3_comment",
        "td","nadi","rr","tb","bb","bmi","kasifikasi_bmi","laborat","radiologi","ekg",
        "spirometri_vc_1","spirometri_vc_2","spirometri_vc_3","spirometri_vc_4","spirometri_fvc_1","spirometri_fvc_2",
        "spirometri_fvc_3","spirometri_fvc_4","spirometri_fev_1_1","spirometri_fev_1_2","spirometri_fev_1_3","spirometri_fev_1_4",
        "spirometri_fev_1_fvc_1","spirometri_fev_1_fvc_2","spirometri_fev_1_fvc_3","spirometri_fev_1_fvc_4",
        "audiometri_tinitus_never","audiometri_tinitus_previously","audiometri_tinitus_rarely","audiometri_tinitus_often","audiometri_tinitus_always",
        "audiometri_ear_protection_worn_never","audiometri_ear_protection_worn_previously","audiometri_ear_protection_worn_rarely",
        "audiometri_ear_protection_worn_often","audiometri_ear_protection_worn_always","type_of_hearing","audiometri_left_ear_500_AB","audiometri_left_ear_1000_AB",
        "audiometri_left_ear_1500_AB","audiometri_left_ear_2000_AB","audiometri_left_ear_3000_AB","audiometri_left_ear_4000_AB","audiometri_left_ear_5000_AB",
        "audiometri_left_ear_6000_AB","audiometri_left_ear_500_AC","audiometri_left_ear_1000_AC","audiometri_left_ear_1500_AC",
        "audiometri_left_ear_2000_AC","audiometri_left_ear_3000_AC","audiometri_left_ear_4000_AC","audiometri_left_ear_5000_AC",
        "audiometri_left_ear_6000_AC","audiometri_right_ear_500_ab","audiometri_right_ear_1000_ab","audiometri_right_ear_1500_ab",
        "audiometri_right_ear_2000_ab","audiometri_right_ear_3000_ab","audiometri_right_ear_4000_ab","audiometri_right_ear_5000_ab",
        "audiometri_right_ear_6000_ab","audiometri_right_ear_500_ac","audiometri_right_ear_1000_ac","audiometri_right_ear_1500_ac",
        "audiometri_right_ear_2000_ac","audiometri_right_ear_3000_ac","audiometri_right_ear_4000_ac","audiometri_right_ear_5000_ac",
        "audiometri_right_ear_6000_ac","eye_unaided_distant_r",
        "eye_unaided_distant_l","eye_glasses_distant_r","eye_glasses_distant_l","eye_unaided_near_r","eye_unaided_near_l",
        "eye_glasses_near_r","eye_glasses_near_l","eye_night_vision_1","eye_night_vision_2","eye_brake_test_1","eye_brake_test_2",
        "eye_color_blindless","visual_fields_left","visual_fields_right","fundi","imunisasi_bcg","imunisasi_dpt","imunisasi_polio","imunisasi_morbili","imunisasi_thyphoid",
        "imunisasi_hep_a","imunisasi_hep_b","imunisasi_tetanus","imunisasi_others","vertebra_scoliosis","vertebra_kyphosis",
        "vertebra_lordosis","vertebra_forward_flexion_0_80","vertebra_hyperextensi_0_25","vertebra_lateral_flexion_0_20",
        "vertebra_heel_walking","vertebra_toe_walking","vertebra_squats_x3","exam_ent_comments","exam_cardio_vascular_system_comments",
        "exam_respiratory_system_comments","exam_abdomen_comments","exam_genito_urinary_system_comments",
        "exam_central_peripheral_nervous_system_comments","exam_skin_comments","exam_lymph_nodes_comments","exam_dental_comments",
        "conclusion_requires_spectacles","conclusion_colour_blindness","conclusion_respiratory_problem",
        "conclusion_impaired_hearing","conclusion_vertigo","blood_group","medically_fit","fit_with_restrictions","specify","unfit_comment_1"
    };

    private static final String[] TABEL_PENILAIAN_MCU_COLUMNS = kolomTabelPenilaianMcu();
    // Ubah judul header/lebar kolom tabel di sini. Lebar 0 memakai lebar default.
    private static final Object[][] TABEL_PENILAIAN_MCU_STYLE = new Object[][]{
        {"year", "Tahun", 40},
        {"no_rawat", "No.Rawat", 0},
        {"no_rkm_medis", "No.RM", 0},
        {"nama_pasien", "Nama Pasien", 200},
        {"surname", "Surname", 100},
        {"perusahaan_pasien", "Perusahaan", 200},
        {"nip", "Badge", 55},
        {"tanggal", "Tanggal", 0},
        {"kd_dokter", "Kode Dokter", 0},
        {"nm_dokter", "Dokter PJ", 200},
        {"kd_petugas", "Kode Petugas", 0},
        {"nm_petugas", "Nama Petugas", 0},
        {"note1", "Saran", 200},
        {"mcu_group", "MCU Grup", 0},
        {"dass_21", "Medical History", 85},
        {"phy_exam", "Physical Examination", 170},
        {"conc_lab", "Kesimpulan Laboratorium", 180},
        {"conc_radiologi", "Conclusion Chest X Ray", 180},
        {"conc_ecg", "Conclusion EKG", 130},
        {"conc_spirometry", "Conclusion Spirometry", 180},
        {"conc_audiometry", "Conclusion Audiometry", 180},
        {"kesimpulan1", "Kesimpulan", 200},
        {"tmp_lahir", "Tmp.Lahir", 0},
        {"tgl_lahir", "Tgl.Lahir", 0},
        {"jk", "J.K.", 0},
        {"no_tlp", "No.Telp", 0},
        {"suku_bangsa", "Suku/Bangsa", 0},
        {"stts_nikah", "Status Nikah", 0},
        {"doe", "Date/Year of Employment", 0},
        {"yoe", "Year of Employment", 0},
        {"job_title", "Job Title", 0},
        {"activities", "Activities", 0},
        {"hobby", "Hobby", 0},
        {"other_job", "Moonlight Working", 0},
        {"posisi_kerja", "Position Applied For", 0},
        {"job_involves_driving_or_operating_mobile_equipment", "Driving/Mobile Equipment", 0},
        {"job_involves_working_at_heights", "Working at Heights", 0},
        {"job_involves_clerical_office_based_or_administrative", "Clerical/Administrative", 0},
        {"job_involves_requires_colour_vision", "Requires Colour Vision", 0},
        {"job_involves_potential_dust_exposure", "Potential Dust Exposure", 0},
        {"job_involves_catering_staff_including_food_handlers", "Catering/Food Handler", 0},
        {"job_involves_exposing_to_other_potential_dangerous", "Other Potential Dangerous", 0},
        {"med_hist_head_injury_or_contussion", "Head Injury/Contussion", 0},
        {"med_hist_fainting_blackouts_epilepsy", "Fainting/Blackouts/Epilepsy", 0},
        {"med_hist_visual_changes", "Visual Changes", 0},
        {"med_hist_hearing_loss", "Hearing Loss", 0},
        {"med_hist_nose_sinus_throat_trouble_more_4_weeks", "Nose/Sinus/Throat >4 Weeks", 0},
        {"med_hist_gynaecological_problems", "Gynaecological Problems", 0},
        {"med_hist_chronic_skin_problem", "Chronic Skin Problem", 0},
        {"med_hist_chronic_diarrhea", "Chronic Diarrhea", 0},
        {"med_hist_anorexia_more_4_weeks", "Anorexia >4 Weeks", 0},
        {"med_hist_gastritis", "Gastritis", 0},
        {"med_hist_jaundice_hepatitis", "Jaundice/Hepatitis", 0},
        {"med_hist_chronic_cough_more_4_weeks", "Chronic Cough >4 Weeks", 0},
        {"med_hist_haemorhoid", "Haemorhoid", 0},
        {"med_hist_chronic_abdominal_pain", "Chronic Abdominal Pain", 0},
        {"med_hist_diabetes", "Diabetes", 0},
        {"med_hist_asthma", "Asthma", 0},
        {"med_hist_allergies", "Allergies", 0},
        {"med_hist_tuberculosis_bronchitis", "Tuberculosis/Bronchitis", 0},
        {"med_hist_psychiatric_disorder", "Psychiatric Disorder", 0},
        {"med_hist_sexual_transmitted_diseases", "Sexual Transmitted Diseases", 0},
        {"med_hist_unusual_change_of_weight_more_5kg_per_month", "Weight Change >5kg/Month", 0},
        {"med_hist_hypertension", "Hypertension", 0},
        {"med_hist_chest_pain_heart_disease", "Chest Pain/Heart Disease", 0},
        {"med_hist_malaria_tropical_disease", "Malaria/Tropical Disease", 0},
        {"med_hist_surgery_operation", "Surgery/Operation", 0},
        {"med_hist_back_pain_more_4_weeks", "Back Pain >4 Weeks", 0},
        {"med_hist_thypoid_fever", "Thypoid Fever", 0},
        {"med_hist_swollen_or_painful_joints", "Swollen/Painful Joints", 0},
        {"med_hist_kidney_problem_urinary_stones", "Kidney Problem/Urinary Stones", 0},
        {"med_hist_other_chronical_diseases", "Other Chronical Diseases", 0},
        {"family_history_father", "Family History Father", 0},
        {"family_history_mother", "Family History Mother", 0},
        {"family_history_siblings", "Family History Siblings", 0},
        {"family_history_other", "Family History Others", 0},
        {"cigarettes_perday", "Cigarettes Perday", 0},
        {"alcohol_gr_week", "Alcohol Gr/Week", 0},
        {"prescribed_medication", "Prescribed Medication 1", 0},
        {"prescribed_medication_2", "Prescribed Medication 2", 0},
        {"any_allergies", "Any Allergies", 0},
        {"hb", "Hb", 0},
        {"wbc", "WBC", 0},
        {"esr", "ESR", 0},
        {"bl_group", "Bl.Gr", 0},
        {"gamaa_gt", "GT", 0},
        {"sgot", "SGOT", 0},
        {"sgpt", "SGPT", 0},
        {"urea", "Urea", 0},
        {"creatinin", "Crea", 0},
        {"glucose", "Gluco", 0},
        {"random_glucose", "Random Glucose", 0},
        {"total_cholestrol", "Total Cholestrol", 0},
        {"protein", "Protein", 0},
        {"blood", "Blood", 0},
        {"bilirubin", "Bilirubin", 0},
        {"malaria", "Malaria", 0},
        {"tpha", "TPHA", 0},
        {"mantoux_test", "Mantoux Test", 0},
        {"leukosit", "Leukosit", 0},
        {"lab_others", "Lab Others", 0},
        {"ova", "OVA", 0},
        {"culture", "Culture", 0},
        {"cysta", "Cysta", 0},
        {"parasites1", "Parasites", 0},
        {"pnemunosicosis", "Pneumoconiosis", 0},
        {"pnemunosicosis2", "Pneumoconiosis Detail", 0},
        {"ILO_clasification", "ILO Classification", 0},
        {"ILO_clasification2", "ILO Classification Detail", 0},
        {"oth_abnormal", "Other Abnormalities", 0},
        {"tb1", "Evidence of TB", 0},
        {"tb2", "Evidence of TB Detail", 0},
        {"page3_comment", "Comment", 0},
        {"td", "T.D.", 0},
        {"nadi", "Nadi", 0},
        {"rr", "R.R.", 0},
        {"tb", "T.B.", 0},
        {"bb", "B.B.", 0},
        {"bmi", "B.M.I.", 0},
        {"kasifikasi_bmi", "Klasifikasi BMI", 0},
        {"laborat", "Pemeriksaan Laboratorium", 0},
        {"radiologi", "Rontgen Thorax", 0},
        {"ekg", "ECG Abnormal", 180},
        {"spirometri_vc_1", "VC (1)", 0},
        {"spirometri_vc_2", "VC (2)", 0},
        {"spirometri_vc_3", "VC (3)", 0},
        {"spirometri_vc_4", "VC (4)", 0},
        {"spirometri_fvc_1", "FVC (1)", 0},
        {"spirometri_fvc_2", "FVC (2)", 0},
        {"spirometri_fvc_3", "FVC (3)", 0},
        {"spirometri_fvc_4", "FVC (4)", 0},
        {"spirometri_fev_1_1", "FEV (1)", 0},
        {"spirometri_fev_1_2", "FEV (2)", 0},
        {"spirometri_fev_1_3", "FEV (3)", 0},
        {"spirometri_fev_1_4", "FEV (4)", 0},
        {"spirometri_fev_1_fvc_1", "FEV 1/FVC (1)", 0},
        {"spirometri_fev_1_fvc_2", "FEV 1/FVC (2)", 0},
        {"spirometri_fev_1_fvc_3", "FEV 1/FVC (3)", 0},
        {"spirometri_fev_1_fvc_4", "FEV 1/FVC (4)", 0},
        {"audiometri_tinitus_never", "Tinitus Never", 0},
        {"audiometri_tinitus_previously", "Tinitus Previously", 0},
        {"audiometri_tinitus_rarely", "Tinitus Rarely", 0},
        {"audiometri_tinitus_often", "Tinitus Often", 0},
        {"audiometri_tinitus_always", "Tinitus Always", 0},
        {"audiometri_ear_protection_worn_never", "Ear Protection Never", 0},
        {"audiometri_ear_protection_worn_previously", "Ear Protection Previously", 0},
        {"audiometri_ear_protection_worn_rarely", "Ear Protection Rarely", 0},
        {"audiometri_ear_protection_worn_often", "Ear Protection Often", 0},
        {"audiometri_ear_protection_worn_always", "Ear Protection Always", 0},
        {"type_of_hearing", "Type of Hearing Protection", 180},
        {"audiometri_left_ear_500_AB", "Left Ear AB 500", 0},
        {"audiometri_left_ear_1000_AB", "Left Ear AB 1000", 0},
        {"audiometri_left_ear_1500_AB", "Left Ear AB 1500", 0},
        {"audiometri_left_ear_2000_AB", "Left Ear AB 2000", 0},
        {"audiometri_left_ear_3000_AB", "Left Ear AB 3000", 0},
        {"audiometri_left_ear_4000_AB", "Left Ear AB 4000", 0},
        {"audiometri_left_ear_5000_AB", "Left Ear AB 5000", 0},
        {"audiometri_left_ear_6000_AB", "Left Ear AB 6000", 0},
        {"audiometri_left_ear_500_AC", "Left Ear AC 500", 0},
        {"audiometri_left_ear_1000_AC", "Left Ear AC 1000", 0},
        {"audiometri_left_ear_1500_AC", "Left Ear AC 1500", 0},
        {"audiometri_left_ear_2000_AC", "Left Ear AC 2000", 0},
        {"audiometri_left_ear_3000_AC", "Left Ear AC 3000", 0},
        {"audiometri_left_ear_4000_AC", "Left Ear AC 4000", 0},
        {"audiometri_left_ear_5000_AC", "Left Ear AC 5000", 0},
        {"audiometri_left_ear_6000_AC", "Left Ear AC 6000", 0},
        {"audiometri_right_ear_500_ab", "Right Ear AB 500", 0},
        {"audiometri_right_ear_1000_ab", "Right Ear AB 1000", 0},
        {"audiometri_right_ear_1500_ab", "Right Ear AB 1500", 0},
        {"audiometri_right_ear_2000_ab", "Right Ear AB 2000", 0},
        {"audiometri_right_ear_3000_ab", "Right Ear AB 3000", 0},
        {"audiometri_right_ear_4000_ab", "Right Ear AB 4000", 0},
        {"audiometri_right_ear_5000_ab", "Right Ear AB 5000", 0},
        {"audiometri_right_ear_6000_ab", "Right Ear AB 6000", 0},
        {"audiometri_right_ear_500_ac", "Right Ear AC 500", 0},
        {"audiometri_right_ear_1000_ac", "Right Ear AC 1000", 0},
        {"audiometri_right_ear_1500_ac", "Right Ear AC 1500", 0},
        {"audiometri_right_ear_2000_ac", "Right Ear AC 2000", 0},
        {"audiometri_right_ear_3000_ac", "Right Ear AC 3000", 0},
        {"audiometri_right_ear_4000_ac", "Right Ear AC 4000", 0},
        {"audiometri_right_ear_5000_ac", "Right Ear AC 5000", 0},
        {"audiometri_right_ear_6000_ac", "Right Ear AC 6000", 0},
        {"eye_unaided_distant_r", "Unaided distant R", 0},
        {"eye_unaided_distant_l", "Unaided distant L", 0},
        {"eye_glasses_distant_r", "Glasses distant R", 0},
        {"eye_glasses_distant_l", "Glasses distant L", 0},
        {"eye_unaided_near_r", "Unaided near R", 0},
        {"eye_unaided_near_l", "Unaided near L", 0},
        {"eye_glasses_near_r", "Glasses near R", 0},
        {"eye_glasses_near_l", "Glasses near L", 0},
        {"eye_night_vision_1", "Night Vision 1", 0},
        {"eye_night_vision_2", "Night Vision 2", 0},
        {"eye_brake_test_1", "Brake Test 1", 0},
        {"eye_brake_test_2", "Brake Test 2", 0},
        {"eye_color_blindless", "Buta Warna", 0},
        {"visual_fields_left", "Lapang Pandang L", 0},
        {"visual_fields_right", "Lapang Pandang R", 0},
        {"fundi", "Fundi", 0},
        {"imunisasi_bcg", "BCG", 0},
        {"imunisasi_dpt", "DPT", 0},
        {"imunisasi_polio", "Polio", 0},
        {"imunisasi_morbili", "Morbili", 0},
        {"imunisasi_thyphoid", "Thyphoid", 0},
        {"imunisasi_hep_a", "HEP A", 0},
        {"imunisasi_hep_b", "HEP B", 0},
        {"imunisasi_tetanus", "Tetanus", 0},
        {"imunisasi_others", "Imunisasi Other", 0},
        {"vertebra_scoliosis", "Scoliosis", 0},
        {"vertebra_kyphosis", "Kyphosis", 0},
        {"vertebra_lordosis", "Lordosis", 0},
        {"vertebra_forward_flexion_0_80", "Forward Flexion 0-80", 0},
        {"vertebra_hyperextensi_0_25", "Hyperextensi 0-25", 0},
        {"vertebra_lateral_flexion_0_20", "Lateral Flexion 0-20", 0},
        {"vertebra_heel_walking", "Heel Walking", 0},
        {"vertebra_toe_walking", "Toe Walking", 0},
        {"vertebra_squats_x3", "Squats x3", 0},
        {"exam_ent_comments", "ENT", 0},
        {"exam_cardio_vascular_system_comments", "Cardio Vascular System", 0},
        {"exam_respiratory_system_comments", "Respiratory System", 0},
        {"exam_abdomen_comments", "Abdomen", 0},
        {"exam_genito_urinary_system_comments", "Genito Urinary System", 0},
        {"exam_central_peripheral_nervous_system_comments", "Central & Peripheral Nervous Sys", 0},
        {"exam_skin_comments", "Skin", 0},
        {"exam_lymph_nodes_comments", "Lymph Nodes", 0},
        {"exam_dental_comments", "Dental", 0},
        {"conclusion_requires_spectacles", "Requires Spectacles", 0},
        {"conclusion_colour_blindness", "Colour Blindness", 0},
        {"conclusion_respiratory_problem", "Respiratory Problem", 0},
        {"conclusion_impaired_hearing", "Impaired Hearing", 0},
        {"conclusion_vertigo", "Vertigo", 0},
        {"blood_group", "Gol. Darah", 0},
        {"medically_fit", "Medically Fit", 0},
        {"fit_with_restrictions", "Fit With Restrictions", 0},
        {"specify", "Specify", 200},
        {"unfit_comment_1", "Saran", 200}
    };
    private static final String[] TABEL_PENILAIAN_MCU_HEADERS = headerTabelPenilaianMcu();
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public RMMCU(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        aturInputKesimpulanMcu();
        aturTabTraversal(internalFrame1);
        aturKeypressRiwayatPenyakit();
        aturKeypressLaboratoriumRontgen();
        aturKeypressPemeriksaanKhusus();
        
        tabMode=new DefaultTableModel(null,TABEL_PENILAIAN_MCU_HEADERS){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < tbObat.getColumnModel().getColumnCount(); i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(aturLebarKolomTabel(column, TABEL_PENILAIAN_MCU_COLUMNS[i])){
                continue;
            }
            if("kd_dokter".equals(TABEL_PENILAIAN_MCU_COLUMNS[i])||"kd_petugas".equals(TABEL_PENILAIAN_MCU_COLUMNS[i])){
                column.setMinWidth(0);
                column.setMaxWidth(0);
                column.setPreferredWidth(0);
                column.setWidth(0);
                continue;
            }
            if(i==0){
                column.setPreferredWidth(40);
            }else if(i==1){
                column.setPreferredWidth(140);
            }else if(i==2){
                column.setPreferredWidth(35);
            }else if(i==3){
                column.setPreferredWidth(160);
            }else if(i==4){
                column.setPreferredWidth(60);
            }else if(i==5){
                column.setPreferredWidth(120);
            }else if(i==6){
                column.setPreferredWidth(80);
            }else if(i==7){
                column.setPreferredWidth(120);
            }else if(i==8){
                column.setPreferredWidth(200);
            }else if(i==9){
                column.setPreferredWidth(200);
            }else if(i==10){
                column.setPreferredWidth(150);
            }else if(i==11){
                column.setPreferredWidth(85);
            }else if(i==12){
                column.setPreferredWidth(77);
            }else if(i==13){
                column.setPreferredWidth(45);
            }else if(i==14){
                column.setPreferredWidth(30);
            }else if(i==15){
                column.setPreferredWidth(30);
            }else if(i==16){
                column.setPreferredWidth(30);
            }else if(i==17){
                column.setPreferredWidth(30);
            }else if(i==18){
                column.setPreferredWidth(35);
            }else if(i==19){
                column.setPreferredWidth(45);
            }else if(i==20){
                column.setPreferredWidth(120);
            }else if(i==21){
                column.setPreferredWidth(30);
            }else if(i==22){
                column.setPreferredWidth(120);
            }else if(i==23){
                column.setPreferredWidth(90);
            }else if(i==24){
                column.setPreferredWidth(90);
            }else if(i==25){
                column.setPreferredWidth(90);
            }else if(i==26){
                column.setPreferredWidth(90);
            }else if(i==27){
                column.setPreferredWidth(90);
            }else if(i==28){
                column.setPreferredWidth(60);
            }else if(i==29){
                column.setPreferredWidth(60);
            }else if(i==30){
                column.setPreferredWidth(60);
            }else if(i==31){
                column.setPreferredWidth(160);
            }else if(i==32){
                column.setPreferredWidth(70);
            }else if(i==33){
                column.setPreferredWidth(70);
            }else if(i==34){
                column.setPreferredWidth(70);
            }else if(i==35){
                column.setPreferredWidth(100);
            }else if(i==36){
                column.setPreferredWidth(68);
            }else if(i==37){
                column.setPreferredWidth(60);
            }else if(i==38){
                column.setPreferredWidth(55);
            }else if(i==39){
                column.setPreferredWidth(60);
            }else if(i==40){
                column.setPreferredWidth(150);
            }else if(i==41){
                column.setPreferredWidth(90);
            }else if(i==42){
                column.setPreferredWidth(172);
            }else if(i==43){
                column.setPreferredWidth(82);
            }else if(i==44){
                column.setPreferredWidth(75);
            }else if(i==45){
                column.setPreferredWidth(112);
            }else if(i==46){
                column.setPreferredWidth(86);
            }else if(i==47){
                column.setPreferredWidth(70);
            }else if(i==48){
                column.setPreferredWidth(80);
            }else if(i==49){
                column.setPreferredWidth(70);
            }else if(i==50){
                column.setPreferredWidth(48);
            }else if(i==51){
                column.setPreferredWidth(70);
            }else if(i==52){
                column.setPreferredWidth(70);
            }else if(i==53){
                column.setPreferredWidth(55);
            }else if(i==54){
                column.setPreferredWidth(46);
            }else if(i==55){
                column.setPreferredWidth(58);
            }else if(i==56){
                column.setPreferredWidth(42);
            }else if(i==57){
                column.setPreferredWidth(90);
            }else if(i==58){
                column.setPreferredWidth(90);
            }else if(i==59){
                column.setPreferredWidth(75);
            }else if(i==60){
                column.setPreferredWidth(85);
            }else if(i==61){
                column.setPreferredWidth(70);
            }else if(i==62){
                column.setPreferredWidth(68);
            }else if(i==63){
                column.setPreferredWidth(90);
            }else if(i==64){
                column.setPreferredWidth(75);
            }else if(i==65){
                column.setPreferredWidth(78);
            }else if(i==66){
                column.setPreferredWidth(48);
            }else if(i==67){
                column.setPreferredWidth(70);
            }else if(i==68){
                column.setPreferredWidth(150);
            }else if(i==69){
                column.setPreferredWidth(53);
            }else if(i==70){
                column.setPreferredWidth(172);
            }else if(i==71){
                column.setPreferredWidth(87);
            }else if(i==72){
                column.setPreferredWidth(93);
            }else if(i==73){
                column.setPreferredWidth(112);
            }else if(i==74){
                column.setPreferredWidth(85);
            }else if(i==75){
                column.setPreferredWidth(80);
            }else if(i==76){
                column.setPreferredWidth(55);
            }else if(i==77){
                column.setPreferredWidth(73);
            }else if(i==78){
                column.setPreferredWidth(150);
            }else if(i==79){
                column.setPreferredWidth(83);
            }else if(i==80){
                column.setPreferredWidth(142);
            }else if(i==81){
                column.setPreferredWidth(92);
            }else if(i==82){
                column.setPreferredWidth(151);
            }else if(i==83){
                column.setPreferredWidth(98);
            }else if(i==84){
                column.setPreferredWidth(151);
            }else if(i==85){
                column.setPreferredWidth(87);
            }else if(i==86){
                column.setPreferredWidth(151);
            }else if(i==87){
                column.setPreferredWidth(200);
            }else if(i==88){
                column.setPreferredWidth(200);
            }else if(i==89){
                column.setPreferredWidth(200);
            }else if(i==90){
                column.setPreferredWidth(200);
            }else if(i==91){
                column.setPreferredWidth(200);
            }else if(i==92){
                column.setPreferredWidth(200);
            }else if(i==93){
                column.setPreferredWidth(200);
            }else if(i==94){
                column.setPreferredWidth(200);
            }else if(i==95){
                column.setPreferredWidth(180);
            }else if(i==96){
                column.setPreferredWidth(180);
            }else if(i==97){
                column.setPreferredWidth(180);
            }else if(i==98){
                column.setPreferredWidth(180);
            }else if(i==99){
                column.setPreferredWidth(200);
            }else if(i==100){
                column.setPreferredWidth(120);
            }else if(i==101){
                column.setPreferredWidth(120);
            }else if(i==102){
                column.setPreferredWidth(200);
            }else if(i==103){
                column.setPreferredWidth(200);
            }else if(i==104){
                column.setPreferredWidth(90);
            }else if(i==105){
                column.setPreferredWidth(170);
            }else{
                column.setPreferredWidth(110);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());
        
        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        surname.setDocument(new batasInput((int)100).getKata(surname));
        kesimpulan.setDocument(new batasInput((int)2000).getKata(kesimpulan));
        Kesimpulan1.setDocument(new batasInput((int)1000).getKata(Kesimpulan1));
        Note1.setDocument(new batasInput((int)1000).getKata(Note1));
//        RiwayatAlergiMakanan.setDocument(new batasInput((int)150).getKata(RiwayatAlergiMakanan));
        TD.setDocument(new batasInput((byte)8).getKata(TD));
        Nadi.setDocument(new batasInput((byte)5).getKata(Nadi));
        RR.setDocument(new batasInput((byte)5).getKata(RR));
        TB.setDocument(new batasInput((byte)5).getKata(TB));
        BB.setDocument(new batasInput((byte)5).getKata(BB));
//        Suhu.setDocument(new batasInput((byte)5).getKata(Suhu));
        IMT.setDocument(new batasInput((byte)6).getKata(IMT));
//        LP.setDocument(new batasInput((byte)6).getKata(LP));
//        Rambut.setDocument(new batasInput((int)100).getKata(Rambut));
//        Visus.setDocument(new batasInput((int)50).getKata(Visus));
//        KeteranganLuasLapangPandang.setDocument(new batasInput((int)50).getKata(KeteranganLuasLapangPandang));
        FamilyHistoryFather.setDocument(new batasInput((int)100).getKata(FamilyHistoryFather));
//        PenyakitKulit.setDocument(new batasInput((int)100).getKata(PenyakitKulit));
//        KeteranganAreaGenitalia.setDocument(new batasInput((int)100).getKata(KeteranganAreaGenitalia));
//        KeteranganAnus.setDocument(new batasInput((int)100).getKata(KeteranganAnus));
//        KetExtremitasAtas.setDocument(new batasInput((byte)50).getKata(KetExtremitasAtas));
//        KetExtremitasBawah.setDocument(new batasInput((byte)50).getKata(KetExtremitasBawah));
        PemeriksaanLaboratorium.setDocument(new batasInput((int)1000).getKata(PemeriksaanLaboratorium));
        RongsenThorax.setDocument(new batasInput((int)1000).getKata(RongsenThorax));
        ConcRadiologi.setDocument(new batasInput((int)1000).getKata(ConcRadiologi));
        ConcEcg.setDocument(new batasInput((int)1000).getKata(ConcEcg));
        ecg_abnormal.setDocument(new batasInput((int)255).getKata(ecg_abnormal));
        type_of_hearing.setDocument(new batasInput((int)255).getKata(type_of_hearing));
        eye_unaided_distant_l.setDocument(new batasInput((int)20).getKata(eye_unaided_distant_l));
        eye_unaided_distant_r.setDocument(new batasInput((int)20).getKata(eye_unaided_distant_r));
        ConcSpirometry.setDocument(new batasInput((int)1000).getKata(ConcSpirometry));
        ConcAudiometry.setDocument(new batasInput((int)1000).getKata(ConcAudiometry));
        sinkronConcEcg();
        bl_group.setDocument(new batasInput((byte)3).getKata(bl_group));
        sinkronBloodGroup();
//        Treadmill.setDocument(new batasInput((int)1000).getKata(Treadmill));
//        Romberg.setDocument(new batasInput((int)1000).getKata(Romberg));
//        Backstrength.setDocument(new batasInput((int)1000).getKata(Backstrength));
//        AbiTanganKanan.setDocument(new batasInput((int)1000).getKata(AbiTanganKanan));
//        AbiTanganKiri.setDocument(new batasInput((int)1000).getKata(AbiTanganKiri));
//        AbiKakiKanan.setDocument(new batasInput((int)1000).getKata(AbiKakiKanan));
//        AbiKakiKiri.setDocument(new batasInput((int)1000).getKata(AbiKakiKiri));
//        Lainlain.setDocument(new batasInput((int)1000).getKata(Lainlain));
//        Merokok.setDocument(new batasInput((int)100).getKata(Merokok));
//        Alkohol.setDocument(new batasInput((int)100).getKata(Alkohol));
        PhyExam.setDocument(new batasInput((int)1000).getKata(PhyExam));
        ConcLab.setDocument(new batasInput((int)1000).getKata(ConcLab));
        Dass21.setDocument(new batasInput((int)1000).getKata(Dass21));
        TCari.setDocument(new batasInput((int)100).getKata(TCari));
        
        BB.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {
                isBMI();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                isBMI();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                isBMI();
            }
        });
        
        TB.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {
                isBMI();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                isBMI();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                isBMI();
            }
        });
        
        IMT.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {
                isLP();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                isLP();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                isLP();
            }
        });
        
//        LP.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
//            @Override
//            public void insertUpdate(DocumentEvent e) {
//                isLP();
//            }
//            @Override
//            public void removeUpdate(DocumentEvent e) {
//                isLP();
//            }
//            @Override
//            public void changedUpdate(DocumentEvent e) {
//                isLP();
//            }
//        });
        
        HTMLEditorKit kit = new HTMLEditorKit();
        LoadHTML.setEditable(true);
        LoadHTML.setEditorKit(kit);
        StyleSheet styleSheet = kit.getStyleSheet();
        styleSheet.addRule(
                ".isi td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-bottom: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                ".isi2 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#323232;}"+
                ".isi3 td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                ".isi4 td{font: 11px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                ".isi5 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#AA0000;}"+
                ".isi6 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#FF0000;}"+
                ".isi7 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#C8C800;}"+
                ".isi8 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#00AA00;}"+
                ".isi9 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#969696;}"
        );
        Document doc = kit.createDefaultDocument();
        LoadHTML.setDocument(doc);
        
        try {
            TANGGALMUNDUR=koneksiDB.TANGGALMUNDUR();
        } catch (Exception e) {
            TANGGALMUNDUR="yes";
        }
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        LoadHTML = new widget.editorpane();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnPenilaianMCU = new javax.swing.JMenuItem();
        TanggalRegistrasi = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        BtnPrint = new widget.Button();
        BtnAll = new widget.Button();
        BtnKeluar = new widget.Button();
        TabRawat = new javax.swing.JTabbedPane();
        internalFrame2 = new widget.InternalFrame();
        scrollInput = new widget.ScrollPane();
        FormInput = new widget.PanelBiasa();
        TNoRw = new widget.TextBox();
        surname = new widget.TextBox();
        TNoRM = new widget.TextBox();
        label14 = new widget.Label();
        NmDokter = new widget.TextBox();
        BtnDokter = new widget.Button();
        TglLahir = new widget.TextBox();
        Jk = new widget.TextBox();
        jLabel10 = new widget.Label();
        label11 = new widget.Label();
        jLabel53 = new widget.Label();
        TglAsuhan = new widget.Tanggal();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel59 = new widget.Label();
        scrollPane10 = new widget.ScrollPane();
        PemeriksaanLaboratorium = new widget.TextArea();
        jLabel61 = new widget.Label();
        jLabel62 = new widget.Label();
        scrollPane11 = new widget.ScrollPane();
        RongsenThorax = new widget.TextArea();
        jLabel102 = new widget.Label();
        scrollPane15 = new widget.ScrollPane();
        specify = new widget.TextArea();
        jLabel103 = new widget.Label();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        jSeparator7 = new javax.swing.JSeparator();
        jSeparator8 = new javax.swing.JSeparator();
        jSeparator15 = new javax.swing.JSeparator();
        jLabel107 = new widget.Label();
        BtnDokter3 = new widget.Button();
        BtnDokter2 = new widget.Button();
        FamilyHistoryFather = new widget.TextBox();
        jSeparator24 = new javax.swing.JSeparator();
        SttsNikah = new widget.ComboBox();
        jLabel29 = new widget.Label();
        TmpLahir = new widget.TextBox();
        jLabel32 = new widget.Label();
        jLabel33 = new widget.Label();
        NoTlp = new widget.TextBox();
        jLabel34 = new widget.Label();
        jLabel35 = new widget.Label();
        Perusahaan = new widget.TextBox();
        jLabel38 = new widget.Label();
        Doe = new widget.TextBox();
        jLabel37 = new widget.Label();
        Yoe = new widget.TextBox();
        jLabel41 = new widget.Label();
        JobTitle = new widget.TextBox();
        Activities = new widget.TextBox();
        jLabel45 = new widget.Label();
        jLabel46 = new widget.Label();
        Hobby = new widget.TextBox();
        OtherJob = new widget.TextBox();
        jLabel47 = new widget.Label();
        jLabel30 = new widget.Label();
        jLabel31 = new widget.Label();
        JobInvolvesExposingToOtherPotentialDangerous = new widget.CekBox();
        JobInvolvesDrivingOrOperatingMobileEquipment = new widget.CekBox();
        JobInvolvesWorkingAtHeights = new widget.CekBox();
        JobInvolvesClericalOfficeBasedOrAdministrative = new widget.CekBox();
        JobInvolvesRequiresColourVision = new widget.CekBox();
        JobInvolvesPotentialDustExposure = new widget.CekBox();
        JobInvolvesCateringStaffIncludingFoodHandlers = new widget.CekBox();
        McuGroup = new widget.ComboBox();
        jLabel36 = new widget.Label();
        RWP1 = new widget.CekBox();
        RWP2 = new widget.CekBox();
        RWP3 = new widget.CekBox();
        RWP4 = new widget.CekBox();
        RWP5 = new widget.CekBox();
        RWP8 = new widget.CekBox();
        RWP7 = new widget.CekBox();
        RWP6 = new widget.CekBox();
        RWP10 = new widget.CekBox();
        RWP9 = new widget.CekBox();
        RWP11 = new widget.CekBox();
        RWP12 = new widget.CekBox();
        RWP13 = new widget.CekBox();
        RWP14 = new widget.CekBox();
        RWP15 = new widget.CekBox();
        RWP18 = new widget.CekBox();
        RWP17 = new widget.CekBox();
        RWP16 = new widget.CekBox();
        RWP20 = new widget.CekBox();
        RWP19 = new widget.CekBox();
        RWP24 = new widget.CekBox();
        RWP26 = new widget.CekBox();
        RWP28 = new widget.CekBox();
        RWP25 = new widget.CekBox();
        RWP22 = new widget.CekBox();
        RWP27 = new widget.CekBox();
        RWP23 = new widget.CekBox();
        RWP29 = new widget.CekBox();
        RWP21 = new widget.CekBox();
        RWP30 = new widget.CekBox();
        jSeparator25 = new javax.swing.JSeparator();
        jLabel54 = new widget.Label();
        jSeparator26 = new javax.swing.JSeparator();
        jLabel55 = new widget.Label();
        jLabel48 = new widget.Label();
        FamilyHistoryMother = new widget.TextBox();
        jLabel49 = new widget.Label();
        FamilyHistorySiblings = new widget.TextBox();
        jLabel50 = new widget.Label();
        FamilyHistoryOther = new widget.TextBox();
        jLabel51 = new widget.Label();
        jLabel56 = new widget.Label();
        jSeparator27 = new javax.swing.JSeparator();
        CigarettesPerday = new widget.TextBox();
        jLabel52 = new widget.Label();
        AlcoholGrWeek = new widget.TextBox();
        jLabel57 = new widget.Label();
        PrescribedMedication = new widget.TextBox();
        jLabel58 = new widget.Label();
        PrescribedMedication2 = new widget.TextBox();
        jLabel60 = new widget.Label();
        spirometri_vc_1 = new widget.TextBox();
        jLabel63 = new widget.Label();
        PosisiKerja = new widget.ComboBox();
        AnyAllergies = new widget.TextBox();
        jLabel64 = new widget.Label();
        spirometri_vc_2 = new widget.TextBox();
        jLabel65 = new widget.Label();
        spirometri_vc_3 = new widget.TextBox();
        jLabel66 = new widget.Label();
        jLabel67 = new widget.Label();
        spirometri_vc_4 = new widget.TextBox();
        hb = new widget.TextBox();
        jLabel68 = new widget.Label();
        spirometri_fvc_2 = new widget.TextBox();
        jLabel69 = new widget.Label();
        spirometri_fvc_3 = new widget.TextBox();
        jLabel70 = new widget.Label();
        jLabel71 = new widget.Label();
        spirometri_fvc_4 = new widget.TextBox();
        spirometri_fev_1_3 = new widget.TextBox();
        spirometri_fev_1_2 = new widget.TextBox();
        jLabel72 = new widget.Label();
        jLabel73 = new widget.Label();
        spirometri_fev_1_4 = new widget.TextBox();
        jLabel74 = new widget.Label();
        jLabel75 = new widget.Label();
        spirometri_fev_1_1 = new widget.TextBox();
        spirometri_fev_1_fvc_2 = new widget.TextBox();
        spirometri_fev_1_fvc_4 = new widget.TextBox();
        jLabel76 = new widget.Label();
        jLabel77 = new widget.Label();
        spirometri_fev_1_fvc_3 = new widget.TextBox();
        jLabel78 = new widget.Label();
        jLabel79 = new widget.Label();
        spirometri_fev_1_fvc_1 = new widget.TextBox();
        jLabel111 = new widget.Label();
        jSeparator19 = new javax.swing.JSeparator();
        jLabel112 = new widget.Label();
        audiometri_tinitus_always = new widget.CekBox();
        audiometri_tinitus_never = new widget.CekBox();
        audiometri_tinitus_previously = new widget.CekBox();
        audiometri_tinitus_rarely = new widget.CekBox();
        audiometri_tinitus_often = new widget.CekBox();
        jLabel113 = new widget.Label();
        audiometri_ear_protection_worn_always = new widget.CekBox();
        audiometri_ear_protection_worn_never = new widget.CekBox();
        audiometri_ear_protection_worn_previously = new widget.CekBox();
        audiometri_ear_protection_worn_rarely = new widget.CekBox();
        audiometri_ear_protection_worn_often = new widget.CekBox();
        jLabel12 = new widget.Label();
        BB = new widget.TextBox();
        jLabel16 = new widget.Label();
        Nadi = new widget.TextBox();
        jLabel17 = new widget.Label();
        jLabel22 = new widget.Label();
        TD = new widget.TextBox();
        jLabel23 = new widget.Label();
        jLabel25 = new widget.Label();
        RR = new widget.TextBox();
        jLabel26 = new widget.Label();
        jLabel28 = new widget.Label();
        TB = new widget.TextBox();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel80 = new widget.Label();
        jLabel24 = new widget.Label();
        jLabel13 = new widget.Label();
        jSeparator9 = new javax.swing.JSeparator();
        jLabel27 = new widget.Label();
        IMT = new widget.TextBox();
        jLabel42 = new widget.Label();
        visual_fields_right = new widget.TextBox();
        jLabel14 = new widget.Label();
        jLabel114 = new widget.Label();
        jLabel119 = new widget.Label();
        audiometri_left_ear_1000 = new widget.TextBox();
        audiometri_left_ear_2000 = new widget.TextBox();
        jLabel82 = new widget.Label();
        jLabel83 = new widget.Label();
        audiometri_left_ear_1500 = new widget.TextBox();
        jLabel84 = new widget.Label();
        jLabel85 = new widget.Label();
        audiometri_left_ear_500 = new widget.TextBox();
        audiometri_left_ear_3000 = new widget.TextBox();
        jLabel86 = new widget.Label();
        audiometri_left_ear_4000 = new widget.TextBox();
        jLabel87 = new widget.Label();
        audiometri_left_ear_6000 = new widget.TextBox();
        jLabel89 = new widget.Label();
        jLabel91 = new widget.Label();
        audiometri_left_ear_5000 = new widget.TextBox();
        jLabel120 = new widget.Label();
        jLabel81 = new widget.Label();
        jLabel122 = new widget.Label();
        jLabel108 = new widget.Label();
        jLabel109 = new widget.Label();
        jLabel115 = new widget.Label();
        jLabel116 = new widget.Label();
        jLabel117 = new widget.Label();
        jLabel123 = new widget.Label();
        cbConcEcg = new widget.ComboBox();
        eye_glasses_distant_l = new widget.ComboBox();
        eye_glasses_distant_r = new widget.ComboBox();
        eye_unaided_near_l = new widget.ComboBox();
        eye_color_blindless = new widget.ComboBox();
        eye_glasses_near_l = new widget.ComboBox();
        jLabel125 = new widget.Label();
        jLabel126 = new widget.Label();
        jLabel127 = new widget.Label();
        jLabel128 = new widget.Label();
        jLabel129 = new widget.Label();
        jLabel130 = new widget.Label();
        eye_glasses_near_r = new widget.ComboBox();
        eye_night_vision_2 = new widget.ComboBox();
        eye_night_vision_1 = new widget.ComboBox();
        eye_brake_test_2 = new widget.ComboBox();
        eye_brake_test_1 = new widget.ComboBox();
        jLabel131 = new widget.Label();
        eye_unaided_near_r = new widget.ComboBox();
        jLabel132 = new widget.Label();
        KlasifikasiIMT1 = new widget.TextBox();
        fundi = new widget.ComboBox();
        jLabel133 = new widget.Label();
        jLabel134 = new widget.Label();
        jSeparator28 = new javax.swing.JSeparator();
        imunisasi_bcg = new widget.ComboBox();
        jLabel135 = new widget.Label();
        jLabel136 = new widget.Label();
        jLabel137 = new widget.Label();
        imunisasi_dpt = new widget.ComboBox();
        imunisasi_polio = new widget.ComboBox();
        imunisasi_hep_b = new widget.ComboBox();
        jLabel138 = new widget.Label();
        jLabel139 = new widget.Label();
        jLabel140 = new widget.Label();
        jLabel141 = new widget.Label();
        jLabel142 = new widget.Label();
        jLabel143 = new widget.Label();
        imunisasi_morbili = new widget.ComboBox();
        imunisasi_tetanus = new widget.ComboBox();
        imunisasi_thyphoid = new widget.ComboBox();
        imunisasi_others = new widget.ComboBox();
        imunisasi_hep_a = new widget.ComboBox();
        jLabel144 = new widget.Label();
        vertebra_scoliosis = new widget.ComboBox();
        jLabel145 = new widget.Label();
        jLabel146 = new widget.Label();
        jLabel147 = new widget.Label();
        vertebra_lordosis = new widget.ComboBox();
        vertebra_hyperextensi_0_25 = new widget.ComboBox();
        vertebra_forward_flexion_0_80 = new widget.ComboBox();
        jLabel148 = new widget.Label();
        jLabel149 = new widget.Label();
        jLabel150 = new widget.Label();
        jLabel151 = new widget.Label();
        jLabel152 = new widget.Label();
        jLabel153 = new widget.Label();
        vertebra_heel_walking = new widget.ComboBox();
        vertebra_lateral_flexion_0_20 = new widget.ComboBox();
        vertebra_squats_x3 = new widget.ComboBox();
        vertebra_toe_walking = new widget.ComboBox();
        vertebra_kyphosis = new widget.ComboBox();
        jSeparator29 = new javax.swing.JSeparator();
        jLabel154 = new widget.Label();
        exam_ent_comments = new widget.TextBox();
        jLabel160 = new widget.Label();
        exam_cardio_vascular_system_comments = new widget.TextBox();
        jLabel161 = new widget.Label();
        exam_respiratory_system_comments = new widget.TextBox();
        jLabel162 = new widget.Label();
        exam_abdomen_comments = new widget.TextBox();
        jLabel163 = new widget.Label();
        exam_genito_urinary_system_comments = new widget.TextBox();
        jLabel164 = new widget.Label();
        exam_central_peripheral_nervous_system_comments = new widget.TextBox();
        jLabel165 = new widget.Label();
        exam_skin_comments = new widget.TextBox();
        jLabel166 = new widget.Label();
        exam_lymph_nodes_comments = new widget.TextBox();
        jLabel167 = new widget.Label();
        jLabel168 = new widget.Label();
        jLabel110 = new widget.Label();
        jLabel118 = new widget.Label();
        jLabel40 = new widget.Label();
        SukuBangsa = new widget.TextBox();
        jLabel43 = new widget.Label();
        NIP = new widget.TextBox();
        KdDokter1 = new widget.TextBox();
        Year = new widget.TextBox();
        jSeparator30 = new javax.swing.JSeparator();
        visual_fields_left = new widget.TextBox();
        jLabel172 = new widget.Label();
        jLabel173 = new widget.Label();
        jLabel174 = new widget.Label();
        conclusion_requires_spectacles = new widget.ComboBox();
        jLabel175 = new widget.Label();
        jLabel176 = new widget.Label();
        jLabel177 = new widget.Label();
        jLabel178 = new widget.Label();
        conclusion_respiratory_problem = new widget.ComboBox();
        fit = new widget.ComboBox();
        conclusion_vertigo = new widget.ComboBox();
        conclusion_impaired_hearing = new widget.ComboBox();
        jLabel179 = new widget.Label();
        blood_group = new widget.ComboBox();
        jLabel180 = new widget.Label();
        conclusion_colour_blindness = new widget.ComboBox();
        jLabel181 = new widget.Label();
        fit_with_restrictions = new widget.ComboBox();
        exam_dental_comments = new widget.TextBox();
        scrollPane18 = new widget.ScrollPane();
        saran = new widget.TextArea();
        audiometri_left_ear_1001 = new widget.TextBox();
        audiometri_left_ear_2001 = new widget.TextBox();
        jLabel182 = new widget.Label();
        jLabel183 = new widget.Label();
        audiometri_left_ear_1501 = new widget.TextBox();
        jLabel184 = new widget.Label();
        jLabel185 = new widget.Label();
        audiometri_left_ear_501 = new widget.TextBox();
        audiometri_left_ear_3001 = new widget.TextBox();
        jLabel186 = new widget.Label();
        audiometri_left_ear_4001 = new widget.TextBox();
        jLabel187 = new widget.Label();
        audiometri_left_ear_6001 = new widget.TextBox();
        jLabel188 = new widget.Label();
        jLabel189 = new widget.Label();
        audiometri_left_ear_5001 = new widget.TextBox();
        audiometri_left_ear_1002 = new widget.TextBox();
        audiometri_left_ear_2002 = new widget.TextBox();
        audiometri_left_ear_1502 = new widget.TextBox();
        audiometri_left_ear_502 = new widget.TextBox();
        audiometri_left_ear_3002 = new widget.TextBox();
        audiometri_left_ear_4002 = new widget.TextBox();
        audiometri_left_ear_6002 = new widget.TextBox();
        audiometri_left_ear_5002 = new widget.TextBox();
        audiometri_left_ear_1003 = new widget.TextBox();
        audiometri_left_ear_2003 = new widget.TextBox();
        audiometri_left_ear_1503 = new widget.TextBox();
        audiometri_left_ear_503 = new widget.TextBox();
        audiometri_left_ear_3003 = new widget.TextBox();
        audiometri_left_ear_4003 = new widget.TextBox();
        audiometri_left_ear_6003 = new widget.TextBox();
        audiometri_left_ear_5003 = new widget.TextBox();
        label15 = new widget.Label();
        KdPetugas = new widget.TextBox();
        NmPetugas = new widget.TextBox();
        BtnPetugas = new widget.Button();
        ConcRadiologi = new widget.TextBox();
        jLabel101 = new widget.Label();
        jLabel104 = new widget.Label();
        jLabel105 = new widget.Label();
        scrollPane12 = new widget.ScrollPane();
        PhyExam = new widget.TextArea();
        scrollPane13 = new widget.ScrollPane();
        ConcLab = new widget.TextArea();
        jLabel155 = new widget.Label();
        jLabel156 = new widget.Label();
        scrollPane5 = new widget.ScrollPane();
        kesimpulan = new widget.TextArea();
        scrollPane6 = new widget.ScrollPane();
        rekomendasi2 = new widget.TextArea();
        scrollPane7 = new widget.ScrollPane();
        Dass21 = new widget.TextArea();
        jLabel158 = new widget.Label();
        ConcEcg = new widget.TextBox();
        jLabel157 = new widget.Label();
        ConcSpirometry = new widget.TextBox();
        jLabel159 = new widget.Label();
        ConcAudiometry = new widget.TextBox();
        jLabel169 = new widget.Label();
        jLabel170 = new widget.Label();
        scrollPane16 = new widget.ScrollPane();
        Kesimpulan1 = new widget.TextArea();
        scrollPane17 = new widget.ScrollPane();
        Note1 = new widget.TextArea();
        jLabel171 = new widget.Label();
        jSeparator31 = new javax.swing.JSeparator();
        jLabel39 = new widget.Label();
        TPasien = new widget.TextBox();
        spirometri_fvc_1 = new widget.TextBox();
        jLabel88 = new widget.Label();
        wbc = new widget.TextBox();
        jLabel90 = new widget.Label();
        jLabel92 = new widget.Label();
        esr = new widget.TextBox();
        jLabel93 = new widget.Label();
        bl_group = new widget.TextBox();
        sgot = new widget.TextBox();
        jLabel94 = new widget.Label();
        sgpt = new widget.TextBox();
        jLabel95 = new widget.Label();
        jLabel96 = new widget.Label();
        urea = new widget.TextBox();
        jLabel97 = new widget.Label();
        creatinin = new widget.TextBox();
        jLabel98 = new widget.Label();
        gamaa_gt = new widget.TextBox();
        jLabel99 = new widget.Label();
        glucose = new widget.TextBox();
        random_glucose = new widget.TextBox();
        jLabel100 = new widget.Label();
        total_cholestrol = new widget.TextBox();
        jLabel106 = new widget.Label();
        jLabel121 = new widget.Label();
        protein = new widget.TextBox();
        jLabel124 = new widget.Label();
        blood = new widget.TextBox();
        bilirubin = new widget.TextBox();
        jLabel206 = new widget.Label();
        malaria = new widget.TextBox();
        jLabel207 = new widget.Label();
        jLabel208 = new widget.Label();
        tpha = new widget.TextBox();
        jLabel209 = new widget.Label();
        mantoux_test = new widget.TextBox();
        jLabel210 = new widget.Label();
        leukosit = new widget.TextBox();
        jLabel211 = new widget.Label();
        lab_others = new widget.TextBox();
        ova = new widget.TextBox();
        jLabel212 = new widget.Label();
        culture = new widget.TextBox();
        jLabel213 = new widget.Label();
        jLabel214 = new widget.Label();
        cysta = new widget.TextBox();
        jLabel215 = new widget.Label();
        pnemunosicosis2 = new widget.TextBox();
        jLabel216 = new widget.Label();
        jLabel217 = new widget.Label();
        jLabel218 = new widget.Label();
        jLabel219 = new widget.Label();
        parasites1 = new widget.TextBox();
        pnemunosicosis = new widget.TextBox();
        ILO_clasification = new widget.TextBox();
        ILO_clasification2 = new widget.TextBox();
        oth_abnormal = new widget.TextBox();
        tb2 = new widget.TextBox();
        tb1 = new widget.TextBox();
        page3_comment = new widget.TextBox();
        jLabel220 = new widget.Label();
        jLabel221 = new widget.Label();
        jLabel222 = new widget.Label();
        jLabel223 = new widget.Label();
        jLabel224 = new widget.Label();
        jLabel225 = new widget.Label();
        jLabel226 = new widget.Label();
        jLabel227 = new widget.Label();
        jLabel228 = new widget.Label();
        jLabel229 = new widget.Label();
        jLabel230 = new widget.Label();
        jLabel231 = new widget.Label();
        jLabel232 = new widget.Label();
        jLabel233 = new widget.Label();
        jLabel234 = new widget.Label();
        jLabel235 = new widget.Label();
        jLabel236 = new widget.Label();
        ecg_abnormal = new widget.TextBox();
        jLabel190 = new widget.Label();
        type_of_hearing = new widget.TextBox();
        eye_unaided_distant_l = new widget.TextBox();
        eye_unaided_distant_r = new widget.TextBox();
        internalFrame3 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbObat = new widget.Table();
        panelGlass9 = new widget.panelisi();
        jLabel19 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel21 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnPenilaianMCU.setBackground(new java.awt.Color(255, 255, 254));
        MnPenilaianMCU.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPenilaianMCU.setForeground(new java.awt.Color(50, 50, 50));
        MnPenilaianMCU.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPenilaianMCU.setText("Laporan Pengkajian MCU");
        MnPenilaianMCU.setName("MnPenilaianMCU"); // NOI18N
        MnPenilaianMCU.setPreferredSize(new java.awt.Dimension(180, 26));
        MnPenilaianMCU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPenilaianMCUActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnPenilaianMCU);

        TanggalRegistrasi.setHighlighter(null);
        TanggalRegistrasi.setName("TanggalRegistrasi"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Pengkajian Medical Check Up (MCU) ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 54));
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan.setMnemonic('S');
        BtnSimpan.setText("Simpan");
        BtnSimpan.setToolTipText("Alt+S");
        BtnSimpan.setName("BtnSimpan"); // NOI18N
        BtnSimpan.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpanActionPerformed(evt);
            }
        });
        BtnSimpan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSimpanKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnSimpan);

        BtnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Cancel-2-16x16.png"))); // NOI18N
        BtnBatal.setMnemonic('B');
        BtnBatal.setText("Baru");
        BtnBatal.setToolTipText("Alt+B");
        BtnBatal.setName("BtnBatal"); // NOI18N
        BtnBatal.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBatalActionPerformed(evt);
            }
        });
        BtnBatal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnBatalKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnBatal);

        BtnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); // NOI18N
        BtnHapus.setMnemonic('H');
        BtnHapus.setText("Hapus");
        BtnHapus.setToolTipText("Alt+H");
        BtnHapus.setName("BtnHapus"); // NOI18N
        BtnHapus.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnHapusActionPerformed(evt);
            }
        });
        BtnHapus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnHapusKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnHapus);

        BtnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/inventaris.png"))); // NOI18N
        BtnEdit.setMnemonic('G');
        BtnEdit.setText("Ganti");
        BtnEdit.setToolTipText("Alt+G");
        BtnEdit.setName("BtnEdit"); // NOI18N
        BtnEdit.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEditActionPerformed(evt);
            }
        });
        BtnEdit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnEditKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnEdit);

        BtnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png"))); // NOI18N
        BtnPrint.setMnemonic('T');
        BtnPrint.setText("Cetak");
        BtnPrint.setToolTipText("Alt+T");
        BtnPrint.setName("BtnPrint"); // NOI18N
        BtnPrint.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPrintActionPerformed(evt);
            }
        });
        BtnPrint.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPrintKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnPrint);

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('M');
        BtnAll.setText("Semua");
        BtnAll.setToolTipText("Alt+M");
        BtnAll.setName("BtnAll"); // NOI18N
        BtnAll.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAllActionPerformed(evt);
            }
        });
        BtnAll.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnAllKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnAll);

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar.setMnemonic('K');
        BtnKeluar.setText("Keluar");
        BtnKeluar.setToolTipText("Alt+K");
        BtnKeluar.setName("BtnKeluar"); // NOI18N
        BtnKeluar.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluarActionPerformed(evt);
            }
        });
        BtnKeluar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnKeluarKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnKeluar);

        internalFrame1.add(panelGlass8, java.awt.BorderLayout.PAGE_END);

        TabRawat.setBackground(new java.awt.Color(254, 255, 254));
        TabRawat.setForeground(new java.awt.Color(50, 50, 50));
        TabRawat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabRawat.setName("TabRawat"); // NOI18N

        internalFrame2.setBorder(null);
        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setLayout(new java.awt.BorderLayout(1, 1));

        scrollInput.setName("scrollInput"); // NOI18N
        scrollInput.setPreferredSize(new java.awt.Dimension(102, 557));

        FormInput.setBackground(new java.awt.Color(255, 255, 255));
        FormInput.setBorder(null);
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(870, 3024));
        FormInput.setLayout(null);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(74, 10, 131, 23);

        surname.setHighlighter(null);
        surname.setName("surname"); // NOI18N
        FormInput.add(surname);
        surname.setBounds(570, 110, 150, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        FormInput.add(TNoRM);
        TNoRM.setBounds(390, 110, 100, 23);

        label14.setText("Petugas :");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label14);
        label14.setBounds(360, 50, 70, 23);

        NmDokter.setEditable(false);
        NmDokter.setName("NmDokter"); // NOI18N
        NmDokter.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmDokter);
        NmDokter.setBounds(390, 10, 180, 23);

        BtnDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter.setMnemonic('2');
        BtnDokter.setToolTipText("Alt+2");
        BtnDokter.setName("BtnDokter"); // NOI18N
        BtnDokter.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokterActionPerformed(evt);
            }
        });
        FormInput.add(BtnDokter);
        BtnDokter.setBounds(580, 10, 28, 23);

        TglLahir.setEditable(false);
        TglLahir.setHighlighter(null);
        TglLahir.setName("TglLahir"); // NOI18N
        FormInput.add(TglLahir);
        TglLahir.setBounds(660, 140, 80, 23);

        Jk.setEditable(false);
        Jk.setHighlighter(null);
        Jk.setName("Jk"); // NOI18N
        FormInput.add(Jk);
        Jk.setBounds(790, 140, 80, 23);

        jLabel10.setText("No.Rawat :");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(0, 10, 70, 23);

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label11);
        label11.setBounds(620, 10, 57, 23);

        jLabel53.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel53.setText("D. CONSUMPTION");
        jLabel53.setName("jLabel53"); // NOI18N
        FormInput.add(jLabel53);
        jLabel53.setBounds(10, 750, 180, 23);

        TglAsuhan.setForeground(new java.awt.Color(50, 70, 50));
        TglAsuhan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "08-06-2026 15:05:36" }));
        TglAsuhan.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        TglAsuhan.setName("TglAsuhan"); // NOI18N
        TglAsuhan.setOpaque(false);
        FormInput.add(TglAsuhan);
        TglAsuhan.setBounds(690, 10, 130, 23);

        jSeparator1.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator1.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator1.setName("jSeparator1"); // NOI18N
        FormInput.add(jSeparator1);
        jSeparator1.setBounds(0, 41, 880, 1);

        jLabel59.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel59.setText("E. PEMERIKSAAN LABORATORIUM (TERLAMPIR)");
        jLabel59.setName("jLabel59"); // NOI18N
        FormInput.add(jLabel59);
        jLabel59.setBounds(10, 940, 280, 23);

        scrollPane10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane10.setName("scrollPane10"); // NOI18N
        scrollPane10.setPreferredSize(new java.awt.Dimension(100, 74));

        PemeriksaanLaboratorium.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        PemeriksaanLaboratorium.setColumns(20);
        PemeriksaanLaboratorium.setRows(5);
        PemeriksaanLaboratorium.setName("PemeriksaanLaboratorium"); // NOI18N
        PemeriksaanLaboratorium.setPreferredSize(new java.awt.Dimension(100, 52));
        scrollPane10.setViewportView(PemeriksaanLaboratorium);

        FormInput.add(scrollPane10);
        scrollPane10.setBounds(40, 960, 220, 53);

        jLabel61.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel61.setText("G. EKG");
        jLabel61.setName("jLabel61"); // NOI18N
        FormInput.add(jLabel61);
        jLabel61.setBounds(10, 1110, 40, 23);

        jLabel62.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel62.setText("F. RONTGEN THORAX");
        jLabel62.setName("jLabel62"); // NOI18N
        FormInput.add(jLabel62);
        jLabel62.setBounds(10, 1020, 182, 23);

        scrollPane11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane11.setName("scrollPane11"); // NOI18N

        RongsenThorax.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        RongsenThorax.setColumns(20);
        RongsenThorax.setRows(5);
        RongsenThorax.setName("RongsenThorax"); // NOI18N
        RongsenThorax.setPreferredSize(new java.awt.Dimension(182, 52));
        scrollPane11.setViewportView(RongsenThorax);

        FormInput.add(scrollPane11);
        scrollPane11.setBounds(40, 1040, 220, 53);

        jLabel102.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel102.setText("N. KESIMPULAN");
        jLabel102.setName("jLabel102"); // NOI18N
        FormInput.add(jLabel102);
        jLabel102.setBounds(10, 2360, 190, 23);

        scrollPane15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane15.setName("scrollPane15"); // NOI18N

        specify.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        specify.setColumns(20);
        specify.setRows(5);
        specify.setName("specify"); // NOI18N
        specify.setPreferredSize(new java.awt.Dimension(102, 52));
        specify.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                specifyKeyPressed(evt);
            }
        });
        scrollPane15.setViewportView(specify);

        FormInput.add(scrollPane15);
        scrollPane15.setBounds(430, 2480, 420, 53);

        jLabel103.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel103.setText("O. SARAN");
        jLabel103.setName("jLabel103"); // NOI18N
        FormInput.add(jLabel103);
        jLabel103.setBounds(10, 2930, 330, 23);

        jSeparator3.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator3.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator3.setName("jSeparator3"); // NOI18N
        FormInput.add(jSeparator3);
        jSeparator3.setBounds(0, 940, 880, 1);

        jSeparator4.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator4.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator4.setName("jSeparator4"); // NOI18N
        FormInput.add(jSeparator4);
        jSeparator4.setBounds(0, 1020, 880, 1);

        jSeparator5.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator5.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator5.setName("jSeparator5"); // NOI18N
        FormInput.add(jSeparator5);
        jSeparator5.setBounds(0, 1100, 880, 1);

        jSeparator7.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator7.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator7.setName("jSeparator7"); // NOI18N
        FormInput.add(jSeparator7);
        jSeparator7.setBounds(0, 2360, 880, 1);

        jSeparator8.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator8.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator8.setName("jSeparator8"); // NOI18N
        FormInput.add(jSeparator8);
        jSeparator8.setBounds(0, 2550, 880, 1);

        jSeparator15.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator15.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator15.setName("jSeparator15"); // NOI18N
        FormInput.add(jSeparator15);
        jSeparator15.setBounds(0, 1140, 880, 1);

        jLabel107.setText("Tinitus :");
        jLabel107.setName("jLabel107"); // NOI18N
        FormInput.add(jLabel107);
        jLabel107.setBounds(10, 1320, 120, 23);

        BtnDokter3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter3.setMnemonic('2');
        BtnDokter3.setToolTipText("Alt+2");
        BtnDokter3.setName("BtnDokter3"); // NOI18N
        BtnDokter3.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDokter3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokter3ActionPerformed(evt);
            }
        });
        FormInput.add(BtnDokter3);
        BtnDokter3.setBounds(10, 960, 28, 23);

        BtnDokter2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter2.setMnemonic('2');
        BtnDokter2.setToolTipText("Alt+2");
        BtnDokter2.setName("BtnDokter2"); // NOI18N
        BtnDokter2.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDokter2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokter2ActionPerformed(evt);
            }
        });
        FormInput.add(BtnDokter2);
        BtnDokter2.setBounds(10, 1040, 28, 23);

        FamilyHistoryFather.setFocusTraversalPolicyProvider(true);
        FamilyHistoryFather.setName("FamilyHistoryFather"); // NOI18N
        FormInput.add(FamilyHistoryFather);
        FamilyHistoryFather.setBounds(110, 620, 730, 23);

        jSeparator24.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator24.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator24.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator24.setName("jSeparator24"); // NOI18N
        FormInput.add(jSeparator24);
        jSeparator24.setBounds(0, 2040, 880, 1);

        SttsNikah.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "BELUM MENIKAH", "MENIKAH", "JANDA", "DUDHA", "JOMBLO" }));
        SttsNikah.setName("SttsNikah"); // NOI18N
        FormInput.add(SttsNikah);
        SttsNikah.setBounds(510, 170, 120, 23);

        jLabel29.setText("Nama Pasien :");
        jLabel29.setName("jLabel29"); // NOI18N
        FormInput.add(jLabel29);
        jLabel29.setBounds(20, 110, 90, 23);

        TmpLahir.setHighlighter(null);
        TmpLahir.setName("TmpLahir"); // NOI18N
        FormInput.add(TmpLahir);
        TmpLahir.setBounds(480, 140, 170, 23);

        jLabel32.setText("Surname :");
        jLabel32.setName("jLabel32"); // NOI18N
        FormInput.add(jLabel32);
        jLabel32.setBounds(490, 110, 70, 23);

        jLabel33.setText("JK :");
        jLabel33.setName("jLabel33"); // NOI18N
        FormInput.add(jLabel33);
        jLabel33.setBounds(750, 140, 30, 23);

        NoTlp.setHighlighter(null);
        NoTlp.setName("NoTlp"); // NOI18N
        NoTlp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoTlpKeyPressed(evt);
            }
        });
        FormInput.add(NoTlp);
        NoTlp.setBounds(700, 170, 170, 23);

        jLabel34.setText("No. Telp :");
        jLabel34.setName("jLabel34"); // NOI18N
        FormInput.add(jLabel34);
        jLabel34.setBounds(630, 170, 60, 23);

        jLabel35.setText("Suku :");
        jLabel35.setName("jLabel35"); // NOI18N
        FormInput.add(jLabel35);
        jLabel35.setBounds(70, 170, 40, 23);

        Perusahaan.setHighlighter(null);
        Perusahaan.setName("Perusahaan"); // NOI18N
        FormInput.add(Perusahaan);
        Perusahaan.setBounds(120, 140, 260, 23);

        jLabel38.setText("Status Nikah :");
        jLabel38.setName("jLabel38"); // NOI18N
        FormInput.add(jLabel38);
        jLabel38.setBounds(420, 170, 80, 23);

        Doe.setHighlighter(null);
        Doe.setName("Doe"); // NOI18N
        Doe.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DoeKeyPressed(evt);
            }
        });
        FormInput.add(Doe);
        Doe.setBounds(120, 200, 120, 23);

        jLabel37.setText("D/Y of Employment :");
        jLabel37.setName("jLabel37"); // NOI18N
        FormInput.add(jLabel37);
        jLabel37.setBounds(0, 200, 110, 23);

        Yoe.setHighlighter(null);
        Yoe.setName("Yoe"); // NOI18N
        Yoe.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                YoeKeyPressed(evt);
            }
        });
        FormInput.add(Yoe);
        Yoe.setBounds(250, 200, 80, 23);

        jLabel41.setText("Job Title :");
        jLabel41.setName("jLabel41"); // NOI18N
        FormInput.add(jLabel41);
        jLabel41.setBounds(320, 200, 80, 23);

        JobTitle.setHighlighter(null);
        JobTitle.setName("JobTitle"); // NOI18N
        JobTitle.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JobTitleKeyPressed(evt);
            }
        });
        FormInput.add(JobTitle);
        JobTitle.setBounds(410, 200, 170, 23);

        Activities.setHighlighter(null);
        Activities.setName("Activities"); // NOI18N
        Activities.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ActivitiesKeyPressed(evt);
            }
        });
        FormInput.add(Activities);
        Activities.setBounds(700, 200, 170, 23);

        jLabel45.setText("Kebiasaan Olahraga :");
        jLabel45.setName("jLabel45"); // NOI18N
        FormInput.add(jLabel45);
        jLabel45.setBounds(580, 200, 110, 23);

        jLabel46.setText("Hobby :");
        jLabel46.setName("jLabel46"); // NOI18N
        FormInput.add(jLabel46);
        jLabel46.setBounds(20, 230, 90, 23);

        Hobby.setHighlighter(null);
        Hobby.setName("Hobby"); // NOI18N
        Hobby.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HobbyKeyPressed(evt);
            }
        });
        FormInput.add(Hobby);
        Hobby.setBounds(120, 230, 170, 23);

        OtherJob.setHighlighter(null);
        OtherJob.setName("OtherJob"); // NOI18N
        OtherJob.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                OtherJobKeyPressed(evt);
            }
        });
        FormInput.add(OtherJob);
        OtherJob.setBounds(410, 230, 170, 23);

        jLabel47.setText("Moonlight Working :");
        jLabel47.setName("jLabel47"); // NOI18N
        FormInput.add(jLabel47);
        jLabel47.setBounds(300, 230, 100, 23);

        jLabel30.setText("Position applied For :");
        jLabel30.setName("jLabel30"); // NOI18N
        FormInput.add(jLabel30);
        jLabel30.setBounds(570, 230, 120, 23);

        jLabel31.setText("Cakupan Pekerjaan :");
        jLabel31.setName("jLabel31"); // NOI18N
        FormInput.add(jLabel31);
        jLabel31.setBounds(0, 260, 110, 23);

        JobInvolvesExposingToOtherPotentialDangerous.setText(" Exposing to other potential dangerous");
        JobInvolvesExposingToOtherPotentialDangerous.setName("JobInvolvesExposingToOtherPotentialDangerous"); // NOI18N
        FormInput.add(JobInvolvesExposingToOtherPotentialDangerous);
        JobInvolvesExposingToOtherPotentialDangerous.setBounds(370, 300, 230, 20);

        JobInvolvesDrivingOrOperatingMobileEquipment.setText(" Driving or operating mobile equipment ");
        JobInvolvesDrivingOrOperatingMobileEquipment.setName("JobInvolvesDrivingOrOperatingMobileEquipment"); // NOI18N
        FormInput.add(JobInvolvesDrivingOrOperatingMobileEquipment);
        JobInvolvesDrivingOrOperatingMobileEquipment.setBounds(120, 260, 240, 20);

        JobInvolvesWorkingAtHeights.setText(" Working at heights");
        JobInvolvesWorkingAtHeights.setName("JobInvolvesWorkingAtHeights"); // NOI18N
        FormInput.add(JobInvolvesWorkingAtHeights);
        JobInvolvesWorkingAtHeights.setBounds(120, 280, 210, 20);

        JobInvolvesClericalOfficeBasedOrAdministrative.setText(" Clerical, office based or administrative");
        JobInvolvesClericalOfficeBasedOrAdministrative.setName("JobInvolvesClericalOfficeBasedOrAdministrative"); // NOI18N
        FormInput.add(JobInvolvesClericalOfficeBasedOrAdministrative);
        JobInvolvesClericalOfficeBasedOrAdministrative.setBounds(120, 300, 230, 20);

        JobInvolvesRequiresColourVision.setText(" Requires colour vision (colour coded)");
        JobInvolvesRequiresColourVision.setName("JobInvolvesRequiresColourVision"); // NOI18N
        FormInput.add(JobInvolvesRequiresColourVision);
        JobInvolvesRequiresColourVision.setBounds(610, 260, 270, 20);

        JobInvolvesPotentialDustExposure.setText(" Potential dust exposure");
        JobInvolvesPotentialDustExposure.setName("JobInvolvesPotentialDustExposure"); // NOI18N
        FormInput.add(JobInvolvesPotentialDustExposure);
        JobInvolvesPotentialDustExposure.setBounds(370, 260, 210, 20);

        JobInvolvesCateringStaffIncludingFoodHandlers.setText(" Catering staff including all food handlers");
        JobInvolvesCateringStaffIncludingFoodHandlers.setName("JobInvolvesCateringStaffIncludingFoodHandlers"); // NOI18N
        FormInput.add(JobInvolvesCateringStaffIncludingFoodHandlers);
        JobInvolvesCateringStaffIncludingFoodHandlers.setBounds(370, 280, 230, 20);

        McuGroup.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Grup A", "Grup B", "Grup C" }));
        McuGroup.setName("McuGroup"); // NOI18N
        FormInput.add(McuGroup);
        McuGroup.setBounds(800, 110, 70, 23);

        jLabel36.setText("Perusahaan :");
        jLabel36.setName("jLabel36"); // NOI18N
        FormInput.add(jLabel36);
        jLabel36.setBounds(40, 140, 70, 23);

        RWP1.setText(" Head injury or contussion ");
        RWP1.setName("RWP1"); // NOI18N
        FormInput.add(RWP1);
        RWP1.setBounds(120, 380, 230, 20);

        RWP2.setText(" Fainting, blackouts, epilepsy ");
        RWP2.setName("RWP2"); // NOI18N
        FormInput.add(RWP2);
        RWP2.setBounds(120, 400, 230, 20);

        RWP3.setText(" Visual changes ");
        RWP3.setName("RWP3"); // NOI18N
        FormInput.add(RWP3);
        RWP3.setBounds(120, 420, 230, 20);

        RWP4.setText(" Hearing loss ");
        RWP4.setName("RWP4"); // NOI18N
        FormInput.add(RWP4);
        RWP4.setBounds(120, 440, 230, 20);

        RWP5.setText(" Nose, sinus, throut traouble more 4 weeks ");
        RWP5.setName("RWP5"); // NOI18N
        FormInput.add(RWP5);
        RWP5.setBounds(120, 460, 240, 20);

        RWP8.setText(" Chronic diarrhea ");
        RWP8.setName("RWP8"); // NOI18N
        FormInput.add(RWP8);
        RWP8.setBounds(120, 520, 230, 20);

        RWP7.setText(" Chronic skin problem ");
        RWP7.setName("RWP7"); // NOI18N
        FormInput.add(RWP7);
        RWP7.setBounds(120, 500, 230, 20);

        RWP6.setText(" Gynaecological problems ");
        RWP6.setName("RWP6"); // NOI18N
        FormInput.add(RWP6);
        RWP6.setBounds(120, 480, 230, 20);

        RWP10.setText(" Gastritis ");
        RWP10.setName("RWP10"); // NOI18N
        FormInput.add(RWP10);
        RWP10.setBounds(120, 560, 230, 20);

        RWP9.setText(" Anorexia more 4 weeks ");
        RWP9.setName("RWP9"); // NOI18N
        FormInput.add(RWP9);
        RWP9.setBounds(120, 540, 230, 20);

        RWP11.setText(" Jaundice / hepatitis ");
        RWP11.setName("RWP11"); // NOI18N
        FormInput.add(RWP11);
        RWP11.setBounds(380, 380, 230, 20);

        RWP12.setText(" Chronic cough >4weeks ");
        RWP12.setName("RWP12"); // NOI18N
        FormInput.add(RWP12);
        RWP12.setBounds(380, 400, 230, 20);

        RWP13.setText(" Haemorhoid ");
        RWP13.setName("RWP13"); // NOI18N
        FormInput.add(RWP13);
        RWP13.setBounds(380, 420, 230, 20);

        RWP14.setText(" Chronic abdomenal pain ");
        RWP14.setName("RWP14"); // NOI18N
        FormInput.add(RWP14);
        RWP14.setBounds(380, 440, 230, 20);

        RWP15.setText(" Diabetes ");
        RWP15.setName("RWP15"); // NOI18N
        FormInput.add(RWP15);
        RWP15.setBounds(380, 460, 230, 20);

        RWP18.setText(" Tuberculosis/Bronchitis ");
        RWP18.setName("RWP18"); // NOI18N
        FormInput.add(RWP18);
        RWP18.setBounds(380, 520, 230, 20);

        RWP17.setText(" Allergies ");
        RWP17.setName("RWP17"); // NOI18N
        FormInput.add(RWP17);
        RWP17.setBounds(380, 500, 230, 20);

        RWP16.setText(" Asthma ");
        RWP16.setName("RWP16"); // NOI18N
        FormInput.add(RWP16);
        RWP16.setBounds(380, 480, 230, 20);

        RWP20.setText(" Sexual transmitted diseases ");
        RWP20.setName("RWP20"); // NOI18N
        FormInput.add(RWP20);
        RWP20.setBounds(380, 560, 230, 20);

        RWP19.setText(" Psychiatric disorder ");
        RWP19.setName("RWP19"); // NOI18N
        FormInput.add(RWP19);
        RWP19.setBounds(380, 540, 230, 20);

        RWP24.setText(" Malaria / tropical disease ");
        RWP24.setName("RWP24"); // NOI18N
        FormInput.add(RWP24);
        RWP24.setBounds(650, 440, 230, 20);

        RWP26.setText(" Back pain> 4 weeks ");
        RWP26.setName("RWP26"); // NOI18N
        FormInput.add(RWP26);
        RWP26.setBounds(650, 480, 230, 20);

        RWP28.setText(" Swollen or painful joints ");
        RWP28.setName("RWP28"); // NOI18N
        RWP28.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RWP28ActionPerformed(evt);
            }
        });
        FormInput.add(RWP28);
        RWP28.setBounds(650, 520, 230, 20);

        RWP25.setText(" Surgery / operation ");
        RWP25.setName("RWP25"); // NOI18N
        RWP25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RWP25ActionPerformed(evt);
            }
        });
        FormInput.add(RWP25);
        RWP25.setBounds(650, 460, 230, 20);

        RWP22.setText(" Hypertension ");
        RWP22.setName("RWP22"); // NOI18N
        FormInput.add(RWP22);
        RWP22.setBounds(650, 400, 230, 20);

        RWP27.setText(" Thypoid fever ");
        RWP27.setName("RWP27"); // NOI18N
        FormInput.add(RWP27);
        RWP27.setBounds(650, 500, 230, 20);

        RWP23.setText(" Chest pain/ heart disease ");
        RWP23.setName("RWP23"); // NOI18N
        FormInput.add(RWP23);
        RWP23.setBounds(650, 420, 230, 20);

        RWP29.setText(" Kidney problem/ Urinary stones ");
        RWP29.setName("RWP29"); // NOI18N
        RWP29.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RWP29ActionPerformed(evt);
            }
        });
        FormInput.add(RWP29);
        RWP29.setBounds(650, 540, 230, 20);

        RWP21.setText(" Unusual change of weight >5 kg/month ");
        RWP21.setName("RWP21"); // NOI18N
        FormInput.add(RWP21);
        RWP21.setBounds(650, 380, 230, 20);

        RWP30.setText(" Other chronical diseases ");
        RWP30.setName("RWP30"); // NOI18N
        FormInput.add(RWP30);
        RWP30.setBounds(650, 560, 230, 20);

        jSeparator25.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator25.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator25.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator25.setName("jSeparator25"); // NOI18N
        FormInput.add(jSeparator25);
        jSeparator25.setBounds(0, 590, 836, 1);

        jLabel54.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel54.setText("ASSESSMENT FINDINGS AND ACTION PLAN");
        jLabel54.setName("jLabel54"); // NOI18N
        FormInput.add(jLabel54);
        jLabel54.setBounds(10, 50, 270, 23);

        jSeparator26.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator26.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator26.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator26.setName("jSeparator26"); // NOI18N
        FormInput.add(jSeparator26);
        jSeparator26.setBounds(0, 340, 836, 1);

        jLabel55.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel55.setText("B. RIWAYAT PENYAKIT");
        jLabel55.setName("jLabel55"); // NOI18N
        FormInput.add(jLabel55);
        jLabel55.setBounds(10, 350, 180, 23);

        jLabel48.setText("Father :");
        jLabel48.setName("jLabel48"); // NOI18N
        FormInput.add(jLabel48);
        jLabel48.setBounds(0, 620, 100, 23);

        FamilyHistoryMother.setFocusTraversalPolicyProvider(true);
        FamilyHistoryMother.setName("FamilyHistoryMother"); // NOI18N
        FormInput.add(FamilyHistoryMother);
        FamilyHistoryMother.setBounds(110, 650, 730, 23);

        jLabel49.setText("Mother :");
        jLabel49.setName("jLabel49"); // NOI18N
        FormInput.add(jLabel49);
        jLabel49.setBounds(0, 650, 100, 23);

        FamilyHistorySiblings.setFocusTraversalPolicyProvider(true);
        FamilyHistorySiblings.setName("FamilyHistorySiblings"); // NOI18N
        FormInput.add(FamilyHistorySiblings);
        FamilyHistorySiblings.setBounds(110, 680, 730, 23);

        jLabel50.setText("Siblings :");
        jLabel50.setName("jLabel50"); // NOI18N
        FormInput.add(jLabel50);
        jLabel50.setBounds(0, 680, 100, 23);

        FamilyHistoryOther.setFocusTraversalPolicyProvider(true);
        FamilyHistoryOther.setName("FamilyHistoryOther"); // NOI18N
        FormInput.add(FamilyHistoryOther);
        FamilyHistoryOther.setBounds(110, 710, 730, 23);

        jLabel51.setText("Others :");
        jLabel51.setName("jLabel51"); // NOI18N
        FormInput.add(jLabel51);
        jLabel51.setBounds(0, 710, 100, 23);

        jLabel56.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel56.setText("C. RIWAYAT PENYAKIT KELUARGA");
        jLabel56.setName("jLabel56"); // NOI18N
        FormInput.add(jLabel56);
        jLabel56.setBounds(10, 600, 180, 23);

        jSeparator27.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator27.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator27.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator27.setName("jSeparator27"); // NOI18N
        FormInput.add(jSeparator27);
        jSeparator27.setBounds(0, 740, 836, 1);

        CigarettesPerday.setFocusTraversalPolicyProvider(true);
        CigarettesPerday.setName("CigarettesPerday"); // NOI18N
        FormInput.add(CigarettesPerday);
        CigarettesPerday.setBounds(140, 780, 700, 23);

        jLabel52.setText("Cigarettes (perday) :");
        jLabel52.setName("jLabel52"); // NOI18N
        FormInput.add(jLabel52);
        jLabel52.setBounds(0, 780, 130, 23);

        AlcoholGrWeek.setFocusTraversalPolicyProvider(true);
        AlcoholGrWeek.setName("AlcoholGrWeek"); // NOI18N
        FormInput.add(AlcoholGrWeek);
        AlcoholGrWeek.setBounds(140, 810, 700, 23);

        jLabel57.setText("Alcohol (gr/week) :");
        jLabel57.setName("jLabel57"); // NOI18N
        FormInput.add(jLabel57);
        jLabel57.setBounds(0, 810, 130, 23);

        PrescribedMedication.setFocusTraversalPolicyProvider(true);
        PrescribedMedication.setName("PrescribedMedication"); // NOI18N
        FormInput.add(PrescribedMedication);
        PrescribedMedication.setBounds(140, 840, 700, 23);

        jLabel58.setText("Prescribced Medication :");
        jLabel58.setName("jLabel58"); // NOI18N
        FormInput.add(jLabel58);
        jLabel58.setBounds(0, 840, 130, 23);

        PrescribedMedication2.setFocusTraversalPolicyProvider(true);
        PrescribedMedication2.setName("PrescribedMedication2"); // NOI18N
        FormInput.add(PrescribedMedication2);
        PrescribedMedication2.setBounds(140, 870, 700, 23);

        jLabel60.setText("Prescribced Medication :");
        jLabel60.setName("jLabel60"); // NOI18N
        FormInput.add(jLabel60);
        jLabel60.setBounds(0, 870, 130, 23);

        spirometri_vc_1.setFocusTraversalPolicyProvider(true);
        spirometri_vc_1.setName("spirometri_vc_1"); // NOI18N
        FormInput.add(spirometri_vc_1);
        spirometri_vc_1.setBounds(80, 1170, 50, 23);

        jLabel63.setText("VC (1) :");
        jLabel63.setName("jLabel63"); // NOI18N
        FormInput.add(jLabel63);
        jLabel63.setBounds(20, 1170, 50, 23);

        PosisiKerja.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Pre employment", "Preplacement", "Periodic" }));
        PosisiKerja.setName("PosisiKerja"); // NOI18N
        FormInput.add(PosisiKerja);
        PosisiKerja.setBounds(700, 230, 170, 23);

        AnyAllergies.setFocusTraversalPolicyProvider(true);
        AnyAllergies.setName("AnyAllergies"); // NOI18N
        FormInput.add(AnyAllergies);
        AnyAllergies.setBounds(140, 900, 700, 23);

        jLabel64.setText("Any allergiecs :");
        jLabel64.setName("jLabel64"); // NOI18N
        FormInput.add(jLabel64);
        jLabel64.setBounds(0, 900, 130, 23);

        spirometri_vc_2.setFocusTraversalPolicyProvider(true);
        spirometri_vc_2.setName("spirometri_vc_2"); // NOI18N
        FormInput.add(spirometri_vc_2);
        spirometri_vc_2.setBounds(80, 1200, 50, 23);

        jLabel65.setText("VC (2) :");
        jLabel65.setName("jLabel65"); // NOI18N
        FormInput.add(jLabel65);
        jLabel65.setBounds(20, 1200, 50, 23);

        spirometri_vc_3.setFocusTraversalPolicyProvider(true);
        spirometri_vc_3.setName("spirometri_vc_3"); // NOI18N
        FormInput.add(spirometri_vc_3);
        spirometri_vc_3.setBounds(80, 1230, 50, 23);

        jLabel66.setText("VC (3) :");
        jLabel66.setName("jLabel66"); // NOI18N
        FormInput.add(jLabel66);
        jLabel66.setBounds(20, 1230, 50, 23);

        jLabel67.setText("VC (4) :");
        jLabel67.setName("jLabel67"); // NOI18N
        FormInput.add(jLabel67);
        jLabel67.setBounds(20, 1260, 50, 23);

        spirometri_vc_4.setFocusTraversalPolicyProvider(true);
        spirometri_vc_4.setName("spirometri_vc_4"); // NOI18N
        FormInput.add(spirometri_vc_4);
        spirometri_vc_4.setBounds(80, 1260, 50, 23);

        hb.setFocusTraversalPolicyProvider(true);
        hb.setName("hb"); // NOI18N
        FormInput.add(hb);
        hb.setBounds(440, 960, 50, 23);

        jLabel68.setText("Hb :");
        jLabel68.setName("jLabel68"); // NOI18N
        FormInput.add(jLabel68);
        jLabel68.setBounds(400, 960, 40, 23);

        spirometri_fvc_2.setFocusTraversalPolicyProvider(true);
        spirometri_fvc_2.setName("spirometri_fvc_2"); // NOI18N
        FormInput.add(spirometri_fvc_2);
        spirometri_fvc_2.setBounds(220, 1200, 50, 23);

        jLabel69.setText("FVC (2) :");
        jLabel69.setName("jLabel69"); // NOI18N
        FormInput.add(jLabel69);
        jLabel69.setBounds(150, 1200, 60, 23);

        spirometri_fvc_3.setFocusTraversalPolicyProvider(true);
        spirometri_fvc_3.setName("spirometri_fvc_3"); // NOI18N
        FormInput.add(spirometri_fvc_3);
        spirometri_fvc_3.setBounds(220, 1230, 50, 23);

        jLabel70.setText("FVC (3) :");
        jLabel70.setName("jLabel70"); // NOI18N
        FormInput.add(jLabel70);
        jLabel70.setBounds(150, 1230, 60, 23);

        jLabel71.setText("FVC (4) :");
        jLabel71.setName("jLabel71"); // NOI18N
        FormInput.add(jLabel71);
        jLabel71.setBounds(150, 1260, 60, 23);

        spirometri_fvc_4.setFocusTraversalPolicyProvider(true);
        spirometri_fvc_4.setName("spirometri_fvc_4"); // NOI18N
        FormInput.add(spirometri_fvc_4);
        spirometri_fvc_4.setBounds(220, 1260, 50, 23);

        spirometri_fev_1_3.setFocusTraversalPolicyProvider(true);
        spirometri_fev_1_3.setName("spirometri_fev_1_3"); // NOI18N
        FormInput.add(spirometri_fev_1_3);
        spirometri_fev_1_3.setBounds(360, 1230, 50, 23);

        spirometri_fev_1_2.setFocusTraversalPolicyProvider(true);
        spirometri_fev_1_2.setName("spirometri_fev_1_2"); // NOI18N
        FormInput.add(spirometri_fev_1_2);
        spirometri_fev_1_2.setBounds(360, 1200, 50, 23);

        jLabel72.setText("FEV 1 (4) :");
        jLabel72.setName("jLabel72"); // NOI18N
        FormInput.add(jLabel72);
        jLabel72.setBounds(290, 1260, 60, 23);

        jLabel73.setText("FEV 1 (3) :");
        jLabel73.setName("jLabel73"); // NOI18N
        FormInput.add(jLabel73);
        jLabel73.setBounds(290, 1230, 60, 23);

        spirometri_fev_1_4.setFocusTraversalPolicyProvider(true);
        spirometri_fev_1_4.setName("spirometri_fev_1_4"); // NOI18N
        FormInput.add(spirometri_fev_1_4);
        spirometri_fev_1_4.setBounds(360, 1260, 50, 23);

        jLabel74.setText("FEV 1 (2) :");
        jLabel74.setName("jLabel74"); // NOI18N
        FormInput.add(jLabel74);
        jLabel74.setBounds(290, 1200, 60, 23);

        jLabel75.setText("FEV 1 (1) :");
        jLabel75.setName("jLabel75"); // NOI18N
        FormInput.add(jLabel75);
        jLabel75.setBounds(290, 1170, 60, 23);

        spirometri_fev_1_1.setFocusTraversalPolicyProvider(true);
        spirometri_fev_1_1.setName("spirometri_fev_1_1"); // NOI18N
        FormInput.add(spirometri_fev_1_1);
        spirometri_fev_1_1.setBounds(360, 1170, 50, 23);

        spirometri_fev_1_fvc_2.setFocusTraversalPolicyProvider(true);
        spirometri_fev_1_fvc_2.setName("spirometri_fev_1_fvc_2"); // NOI18N
        FormInput.add(spirometri_fev_1_fvc_2);
        spirometri_fev_1_fvc_2.setBounds(500, 1200, 50, 23);

        spirometri_fev_1_fvc_4.setFocusTraversalPolicyProvider(true);
        spirometri_fev_1_fvc_4.setName("spirometri_fev_1_fvc_4"); // NOI18N
        FormInput.add(spirometri_fev_1_fvc_4);
        spirometri_fev_1_fvc_4.setBounds(500, 1260, 50, 23);

        jLabel76.setText("FEV 1/FVC (3)");
        jLabel76.setName("jLabel76"); // NOI18N
        FormInput.add(jLabel76);
        jLabel76.setBounds(420, 1230, 70, 23);

        jLabel77.setText("FEV 1/FVC (2)");
        jLabel77.setName("jLabel77"); // NOI18N
        FormInput.add(jLabel77);
        jLabel77.setBounds(410, 1200, 80, 23);

        spirometri_fev_1_fvc_3.setFocusTraversalPolicyProvider(true);
        spirometri_fev_1_fvc_3.setName("spirometri_fev_1_fvc_3"); // NOI18N
        FormInput.add(spirometri_fev_1_fvc_3);
        spirometri_fev_1_fvc_3.setBounds(500, 1230, 50, 23);

        jLabel78.setText("FEV 1/FVC (4)");
        jLabel78.setName("jLabel78"); // NOI18N
        FormInput.add(jLabel78);
        jLabel78.setBounds(420, 1260, 70, 23);

        jLabel79.setText("FEV 1/FVC (1)");
        jLabel79.setName("jLabel79"); // NOI18N
        FormInput.add(jLabel79);
        jLabel79.setBounds(400, 1170, 90, 23);

        spirometri_fev_1_fvc_1.setFocusTraversalPolicyProvider(true);
        spirometri_fev_1_fvc_1.setName("spirometri_fev_1_fvc_1"); // NOI18N
        FormInput.add(spirometri_fev_1_fvc_1);
        spirometri_fev_1_fvc_1.setBounds(500, 1170, 50, 23);

        jLabel111.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel111.setText("H. SPIROMETRI");
        jLabel111.setName("jLabel111"); // NOI18N
        FormInput.add(jLabel111);
        jLabel111.setBounds(10, 1140, 182, 23);

        jSeparator19.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator19.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator19.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator19.setName("jSeparator19"); // NOI18N
        FormInput.add(jSeparator19);
        jSeparator19.setBounds(0, 1290, 550, 1);

        jLabel112.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel112.setText("I. AUDIOMETRI");
        jLabel112.setName("jLabel112"); // NOI18N
        FormInput.add(jLabel112);
        jLabel112.setBounds(10, 1300, 182, 23);

        audiometri_tinitus_always.setText("Always");
        audiometri_tinitus_always.setName("audiometri_tinitus_always"); // NOI18N
        FormInput.add(audiometri_tinitus_always);
        audiometri_tinitus_always.setBounds(480, 1320, 80, 23);

        audiometri_tinitus_never.setText("Never");
        audiometri_tinitus_never.setName("audiometri_tinitus_never"); // NOI18N
        FormInput.add(audiometri_tinitus_never);
        audiometri_tinitus_never.setBounds(150, 1320, 80, 23);

        audiometri_tinitus_previously.setText("Previously");
        audiometri_tinitus_previously.setName("audiometri_tinitus_previously"); // NOI18N
        FormInput.add(audiometri_tinitus_previously);
        audiometri_tinitus_previously.setBounds(230, 1320, 80, 23);

        audiometri_tinitus_rarely.setText("Rarely");
        audiometri_tinitus_rarely.setName("audiometri_tinitus_rarely"); // NOI18N
        FormInput.add(audiometri_tinitus_rarely);
        audiometri_tinitus_rarely.setBounds(320, 1320, 80, 23);

        audiometri_tinitus_often.setText("Often");
        audiometri_tinitus_often.setName("audiometri_tinitus_often"); // NOI18N
        FormInput.add(audiometri_tinitus_often);
        audiometri_tinitus_often.setBounds(400, 1320, 80, 23);

        jLabel113.setText("Left Ear :");
        jLabel113.setName("jLabel113"); // NOI18N
        FormInput.add(jLabel113);
        jLabel113.setBounds(20, 1400, 70, 23);

        audiometri_ear_protection_worn_always.setText("Always");
        audiometri_ear_protection_worn_always.setName("audiometri_ear_protection_worn_always"); // NOI18N
        FormInput.add(audiometri_ear_protection_worn_always);
        audiometri_ear_protection_worn_always.setBounds(480, 1350, 80, 23);

        audiometri_ear_protection_worn_never.setText("Never");
        audiometri_ear_protection_worn_never.setName("audiometri_ear_protection_worn_never"); // NOI18N
        FormInput.add(audiometri_ear_protection_worn_never);
        audiometri_ear_protection_worn_never.setBounds(150, 1350, 80, 23);

        audiometri_ear_protection_worn_previously.setText("Previously");
        audiometri_ear_protection_worn_previously.setName("audiometri_ear_protection_worn_previously"); // NOI18N
        FormInput.add(audiometri_ear_protection_worn_previously);
        audiometri_ear_protection_worn_previously.setBounds(230, 1350, 80, 23);

        audiometri_ear_protection_worn_rarely.setText("Rarely");
        audiometri_ear_protection_worn_rarely.setName("audiometri_ear_protection_worn_rarely"); // NOI18N
        FormInput.add(audiometri_ear_protection_worn_rarely);
        audiometri_ear_protection_worn_rarely.setBounds(320, 1350, 80, 23);

        audiometri_ear_protection_worn_often.setText("Often");
        audiometri_ear_protection_worn_often.setName("audiometri_ear_protection_worn_often"); // NOI18N
        FormInput.add(audiometri_ear_protection_worn_often);
        audiometri_ear_protection_worn_often.setBounds(400, 1350, 80, 23);

        jLabel12.setText("Berat Badan :");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(720, 1660, 80, 23);

        BB.setFocusTraversalPolicyProvider(true);
        BB.setName("BB"); // NOI18N
        FormInput.add(BB);
        BB.setBounds(800, 1660, 50, 23);

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel16.setText("x/menit");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput.add(jLabel16);
        jLabel16.setBounds(330, 1660, 40, 23);

        Nadi.setFocusTraversalPolicyProvider(true);
        Nadi.setName("Nadi"); // NOI18N
        Nadi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NadiKeyPressed(evt);
            }
        });
        FormInput.add(Nadi);
        Nadi.setBounds(270, 1660, 50, 23);

        jLabel17.setText("HR/Nadi :");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(200, 1660, 60, 23);

        jLabel22.setText("TD :");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(40, 1660, 30, 23);

        TD.setFocusTraversalPolicyProvider(true);
        TD.setName("TD"); // NOI18N
        FormInput.add(TD);
        TD.setBounds(80, 1660, 65, 23);

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel23.setText("mmHg");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(150, 1660, 50, 23);

        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel25.setText("x/menit");
        jLabel25.setName("jLabel25"); // NOI18N
        FormInput.add(jLabel25);
        jLabel25.setBounds(510, 1660, 50, 23);

        RR.setFocusTraversalPolicyProvider(true);
        RR.setName("RR"); // NOI18N
        RR.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RRKeyPressed(evt);
            }
        });
        FormInput.add(RR);
        RR.setBounds(450, 1660, 50, 23);

        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel26.setText("RR/Nafas :");
        jLabel26.setName("jLabel26"); // NOI18N
        FormInput.add(jLabel26);
        jLabel26.setBounds(390, 1660, 70, 23);

        jLabel28.setText("Tinggi Badan :");
        jLabel28.setName("jLabel28"); // NOI18N
        FormInput.add(jLabel28);
        jLabel28.setBounds(560, 1660, 80, 23);

        TB.setFocusTraversalPolicyProvider(true);
        TB.setName("TB"); // NOI18N
        TB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TBKeyPressed(evt);
            }
        });
        FormInput.add(TB);
        TB.setBounds(640, 1660, 50, 23);

        jSeparator2.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator2.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator2.setName("jSeparator2"); // NOI18N
        FormInput.add(jSeparator2);
        jSeparator2.setBounds(0, 1640, 880, 1);

        jLabel80.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel80.setText("K. IMUNISASI");
        jLabel80.setName("jLabel80"); // NOI18N
        FormInput.add(jLabel80);
        jLabel80.setBounds(10, 1880, 180, 23);

        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel24.setText("cm");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(690, 1660, 30, 23);

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel13.setText("Kg");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(860, 1660, 20, 23);

        jSeparator9.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator9.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator9.setName("jSeparator9"); // NOI18N
        FormInput.add(jSeparator9);
        jSeparator9.setBounds(0, 1720, 836, 1);

        jLabel27.setText("BMI(BB/TB²) :");
        jLabel27.setName("jLabel27"); // NOI18N
        FormInput.add(jLabel27);
        jLabel27.setBounds(10, 1690, 80, 23);

        IMT.setEditable(false);
        IMT.setFocusTraversalPolicyProvider(true);
        IMT.setName("IMT"); // NOI18N
        FormInput.add(IMT);
        IMT.setBounds(100, 1690, 50, 23);

        jLabel42.setText("Klasifikasi BMI :");
        jLabel42.setName("jLabel42"); // NOI18N
        FormInput.add(jLabel42);
        jLabel42.setBounds(170, 1690, 90, 23);

        visual_fields_right.setFocusTraversalPolicyProvider(true);
        visual_fields_right.setName("visual_fields_right"); // NOI18N
        FormInput.add(visual_fields_right);
        visual_fields_right.setBounds(480, 1840, 100, 23);

        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel14.setText("BMI Ideal : 18.5 – 24.99");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(450, 1690, 140, 23);

        jLabel114.setText(" Type of hearing Protection Worm (Muffs/Plugs) :");
        jLabel114.setName("jLabel114"); // NOI18N
        FormInput.add(jLabel114);
        jLabel114.setBounds(550, 1320, 250, 23);

        jLabel119.setText("R :");
        jLabel119.setName("jLabel119"); // NOI18N
        FormInput.add(jLabel119);
        jLabel119.setBounds(460, 1840, 20, 23);

        audiometri_left_ear_1000.setFocusTraversalPolicyProvider(true);
        audiometri_left_ear_1000.setName("audiometri_left_ear_1000"); // NOI18N
        FormInput.add(audiometri_left_ear_1000);
        audiometri_left_ear_1000.setBounds(150, 1430, 50, 23);

        audiometri_left_ear_2000.setFocusTraversalPolicyProvider(true);
        audiometri_left_ear_2000.setName("audiometri_left_ear_2000"); // NOI18N
        FormInput.add(audiometri_left_ear_2000);
        audiometri_left_ear_2000.setBounds(150, 1490, 50, 23);

        jLabel82.setText("AC 1500 :");
        jLabel82.setName("jLabel82"); // NOI18N
        FormInput.add(jLabel82);
        jLabel82.setBounds(70, 1460, 70, 23);

        jLabel83.setText("AC 1000 :");
        jLabel83.setName("jLabel83"); // NOI18N
        FormInput.add(jLabel83);
        jLabel83.setBounds(70, 1430, 70, 23);

        audiometri_left_ear_1500.setFocusTraversalPolicyProvider(true);
        audiometri_left_ear_1500.setName("audiometri_left_ear_1500"); // NOI18N
        FormInput.add(audiometri_left_ear_1500);
        audiometri_left_ear_1500.setBounds(150, 1460, 50, 23);

        jLabel84.setText("AC 2000 :");
        jLabel84.setName("jLabel84"); // NOI18N
        FormInput.add(jLabel84);
        jLabel84.setBounds(70, 1490, 70, 23);

        jLabel85.setText("AC 500 :");
        jLabel85.setName("jLabel85"); // NOI18N
        FormInput.add(jLabel85);
        jLabel85.setBounds(70, 1400, 70, 23);

        audiometri_left_ear_500.setFocusTraversalPolicyProvider(true);
        audiometri_left_ear_500.setName("audiometri_left_ear_500"); // NOI18N
        FormInput.add(audiometri_left_ear_500);
        audiometri_left_ear_500.setBounds(150, 1400, 50, 23);

        audiometri_left_ear_3000.setFocusTraversalPolicyProvider(true);
        audiometri_left_ear_3000.setName("audiometri_left_ear_3000"); // NOI18N
        FormInput.add(audiometri_left_ear_3000);
        audiometri_left_ear_3000.setBounds(150, 1520, 50, 23);

        jLabel86.setText("AC 3000 :");
        jLabel86.setName("jLabel86"); // NOI18N
        FormInput.add(jLabel86);
        jLabel86.setBounds(70, 1520, 70, 23);

        audiometri_left_ear_4000.setFocusTraversalPolicyProvider(true);
        audiometri_left_ear_4000.setName("audiometri_left_ear_4000"); // NOI18N
        FormInput.add(audiometri_left_ear_4000);
        audiometri_left_ear_4000.setBounds(150, 1550, 50, 23);

        jLabel87.setText("AC 4000 :");
        jLabel87.setName("jLabel87"); // NOI18N
        FormInput.add(jLabel87);
        jLabel87.setBounds(70, 1550, 70, 23);

        audiometri_left_ear_6000.setFocusTraversalPolicyProvider(true);
        audiometri_left_ear_6000.setName("audiometri_left_ear_6000"); // NOI18N
        FormInput.add(audiometri_left_ear_6000);
        audiometri_left_ear_6000.setBounds(150, 1610, 50, 23);

        jLabel89.setText("AC 6000 :");
        jLabel89.setName("jLabel89"); // NOI18N
        FormInput.add(jLabel89);
        jLabel89.setBounds(70, 1610, 70, 23);

        jLabel91.setText("AC 5000 :");
        jLabel91.setName("jLabel91"); // NOI18N
        FormInput.add(jLabel91);
        jLabel91.setBounds(70, 1580, 70, 23);

        audiometri_left_ear_5000.setFocusTraversalPolicyProvider(true);
        audiometri_left_ear_5000.setName("audiometri_left_ear_5000"); // NOI18N
        FormInput.add(audiometri_left_ear_5000);
        audiometri_left_ear_5000.setBounds(150, 1580, 50, 23);

        jLabel120.setText("Right Ear :");
        jLabel120.setName("jLabel120"); // NOI18N
        FormInput.add(jLabel120);
        jLabel120.setBounds(430, 1400, 70, 23);

        jLabel81.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel81.setText("J. PEMERIKSAAN FISIK");
        jLabel81.setName("jLabel81"); // NOI18N
        FormInput.add(jLabel81);
        jLabel81.setBounds(10, 1640, 180, 23);

        jLabel122.setText("LEVEL DECIBEL");
        jLabel122.setName("jLabel122"); // NOI18N
        FormInput.add(jLabel122);
        jLabel122.setBounds(10, 1380, 90, 23);

        jLabel108.setText("Unaided near R :");
        jLabel108.setName("jLabel108"); // NOI18N
        FormInput.add(jLabel108);
        jLabel108.setBounds(300, 1810, 100, 23);

        jLabel109.setText("Unaided near L :");
        jLabel109.setName("jLabel109"); // NOI18N
        FormInput.add(jLabel109);
        jLabel109.setBounds(100, 1810, 100, 23);

        jLabel115.setText("Glasses distant R :");
        jLabel115.setName("jLabel115"); // NOI18N
        FormInput.add(jLabel115);
        jLabel115.setBounds(300, 1780, 100, 23);

        jLabel116.setText("Glasses distant L :");
        jLabel116.setName("jLabel116"); // NOI18N
        FormInput.add(jLabel116);
        jLabel116.setBounds(100, 1780, 100, 23);

        jLabel117.setText("Unaided distant R :");
        jLabel117.setName("jLabel117"); // NOI18N
        FormInput.add(jLabel117);
        jLabel117.setBounds(300, 1750, 100, 23);

        jLabel123.setText("Unaided distant L :");
        jLabel123.setName("jLabel123"); // NOI18N
        FormInput.add(jLabel123);
        jLabel123.setBounds(100, 1750, 100, 23);

        cbConcEcg.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal" }));
        cbConcEcg.setName("cbConcEcg"); // NOI18N
        FormInput.add(cbConcEcg);
        cbConcEcg.setBounds(60, 1110, 80, 23);

        eye_glasses_distant_l.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "No", "Yes" }));
        eye_glasses_distant_l.setName("eye_glasses_distant_l"); // NOI18N
        FormInput.add(eye_glasses_distant_l);
        eye_glasses_distant_l.setBounds(210, 1780, 60, 23);

        eye_glasses_distant_r.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "No", "Yes" }));
        eye_glasses_distant_r.setName("eye_glasses_distant_r"); // NOI18N
        FormInput.add(eye_glasses_distant_r);
        eye_glasses_distant_r.setBounds(410, 1780, 60, 23);

        eye_unaided_near_l.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "No", "Yes" }));
        eye_unaided_near_l.setName("eye_unaided_near_l"); // NOI18N
        FormInput.add(eye_unaided_near_l);
        eye_unaided_near_l.setBounds(210, 1810, 60, 23);

        eye_color_blindless.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Color Blind", "Normal", "Partial Color Blind" }));
        eye_color_blindless.setName("eye_color_blindless"); // NOI18N
        FormInput.add(eye_color_blindless);
        eye_color_blindless.setBounds(100, 1840, 140, 23);

        eye_glasses_near_l.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "No", "Yes" }));
        eye_glasses_near_l.setName("eye_glasses_near_l"); // NOI18N
        FormInput.add(eye_glasses_near_l);
        eye_glasses_near_l.setBounds(610, 1750, 60, 23);

        jLabel125.setText("Brake test 2 :");
        jLabel125.setName("jLabel125"); // NOI18N
        FormInput.add(jLabel125);
        jLabel125.setBounds(700, 1810, 100, 23);

        jLabel126.setText("Brake test 1 :");
        jLabel126.setName("jLabel126"); // NOI18N
        FormInput.add(jLabel126);
        jLabel126.setBounds(500, 1810, 100, 23);

        jLabel127.setText("Night Vision 2 :");
        jLabel127.setName("jLabel127"); // NOI18N
        FormInput.add(jLabel127);
        jLabel127.setBounds(700, 1780, 100, 23);

        jLabel128.setText("Night Vision 1 :");
        jLabel128.setName("jLabel128"); // NOI18N
        FormInput.add(jLabel128);
        jLabel128.setBounds(500, 1780, 100, 23);

        jLabel129.setText("Glasses near R :");
        jLabel129.setName("jLabel129"); // NOI18N
        FormInput.add(jLabel129);
        jLabel129.setBounds(700, 1750, 100, 23);

        jLabel130.setText("Glasses near L :");
        jLabel130.setName("jLabel130"); // NOI18N
        FormInput.add(jLabel130);
        jLabel130.setBounds(500, 1750, 100, 23);

        eye_glasses_near_r.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "No", "Yes" }));
        eye_glasses_near_r.setName("eye_glasses_near_r"); // NOI18N
        FormInput.add(eye_glasses_near_r);
        eye_glasses_near_r.setBounds(810, 1750, 60, 23);

        eye_night_vision_2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "No", "Yes" }));
        eye_night_vision_2.setName("eye_night_vision_2"); // NOI18N
        FormInput.add(eye_night_vision_2);
        eye_night_vision_2.setBounds(810, 1780, 60, 23);

        eye_night_vision_1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "No", "Yes" }));
        eye_night_vision_1.setName("eye_night_vision_1"); // NOI18N
        FormInput.add(eye_night_vision_1);
        eye_night_vision_1.setBounds(610, 1780, 60, 23);

        eye_brake_test_2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "No", "Yes" }));
        eye_brake_test_2.setName("eye_brake_test_2"); // NOI18N
        FormInput.add(eye_brake_test_2);
        eye_brake_test_2.setBounds(810, 1810, 60, 23);

        eye_brake_test_1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "No", "Yes" }));
        eye_brake_test_1.setName("eye_brake_test_1"); // NOI18N
        FormInput.add(eye_brake_test_1);
        eye_brake_test_1.setBounds(610, 1810, 60, 23);

        jLabel131.setText("EYE :");
        jLabel131.setName("jLabel131"); // NOI18N
        FormInput.add(jLabel131);
        jLabel131.setBounds(20, 1750, 60, 23);

        eye_unaided_near_r.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "No", "Yes" }));
        eye_unaided_near_r.setName("eye_unaided_near_r"); // NOI18N
        FormInput.add(eye_unaided_near_r);
        eye_unaided_near_r.setBounds(410, 1810, 60, 23);

        jLabel132.setText("Color Blinds :");
        jLabel132.setName("jLabel132"); // NOI18N
        FormInput.add(jLabel132);
        jLabel132.setBounds(10, 1840, 80, 23);

        KlasifikasiIMT1.setEditable(false);
        KlasifikasiIMT1.setFocusTraversalPolicyProvider(true);
        KlasifikasiIMT1.setName("KlasifikasiIMT1"); // NOI18N
        FormInput.add(KlasifikasiIMT1);
        KlasifikasiIMT1.setBounds(270, 1690, 170, 23);

        fundi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal" }));
        fundi.setName("fundi"); // NOI18N
        FormInput.add(fundi);
        fundi.setBounds(640, 1840, 140, 23);

        jLabel133.setText("Fundi :");
        jLabel133.setName("jLabel133"); // NOI18N
        FormInput.add(jLabel133);
        jLabel133.setBounds(550, 1840, 80, 23);

        jLabel134.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel134.setText("J. PEMERIKSAAN MATA");
        jLabel134.setName("jLabel134"); // NOI18N
        FormInput.add(jLabel134);
        jLabel134.setBounds(10, 1730, 180, 23);

        jSeparator28.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator28.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator28.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator28.setName("jSeparator28"); // NOI18N
        FormInput.add(jSeparator28);
        jSeparator28.setBounds(0, 1870, 880, 1);

        imunisasi_bcg.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "No", "Yes" }));
        imunisasi_bcg.setName("imunisasi_bcg"); // NOI18N
        FormInput.add(imunisasi_bcg);
        imunisasi_bcg.setBounds(110, 1900, 60, 23);

        jLabel135.setText("POLIO :");
        jLabel135.setName("jLabel135"); // NOI18N
        FormInput.add(jLabel135);
        jLabel135.setBounds(330, 1900, 70, 23);

        jLabel136.setText("DPT :");
        jLabel136.setName("jLabel136"); // NOI18N
        FormInput.add(jLabel136);
        jLabel136.setBounds(180, 1900, 70, 23);

        jLabel137.setText("BCG :");
        jLabel137.setName("jLabel137"); // NOI18N
        FormInput.add(jLabel137);
        jLabel137.setBounds(30, 1900, 70, 23);

        imunisasi_dpt.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "No", "Yes" }));
        imunisasi_dpt.setName("imunisasi_dpt"); // NOI18N
        FormInput.add(imunisasi_dpt);
        imunisasi_dpt.setBounds(260, 1900, 60, 23);

        imunisasi_polio.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "No", "Yes" }));
        imunisasi_polio.setName("imunisasi_polio"); // NOI18N
        FormInput.add(imunisasi_polio);
        imunisasi_polio.setBounds(410, 1900, 60, 23);

        imunisasi_hep_b.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "No", "Yes" }));
        imunisasi_hep_b.setName("imunisasi_hep_b"); // NOI18N
        FormInput.add(imunisasi_hep_b);
        imunisasi_hep_b.setBounds(260, 1930, 60, 23);

        jLabel138.setText("Other :");
        jLabel138.setName("jLabel138"); // NOI18N
        FormInput.add(jLabel138);
        jLabel138.setBounds(480, 1930, 70, 23);

        jLabel139.setText("HEP A :");
        jLabel139.setName("jLabel139"); // NOI18N
        FormInput.add(jLabel139);
        jLabel139.setBounds(30, 1930, 70, 23);

        jLabel140.setText("TETANUS :");
        jLabel140.setName("jLabel140"); // NOI18N
        FormInput.add(jLabel140);
        jLabel140.setBounds(330, 1930, 70, 23);

        jLabel141.setText("THYPOID :");
        jLabel141.setName("jLabel141"); // NOI18N
        FormInput.add(jLabel141);
        jLabel141.setBounds(630, 1900, 70, 23);

        jLabel142.setText("HEP B :");
        jLabel142.setName("jLabel142"); // NOI18N
        FormInput.add(jLabel142);
        jLabel142.setBounds(180, 1930, 70, 23);

        jLabel143.setText("MORBILI :");
        jLabel143.setName("jLabel143"); // NOI18N
        FormInput.add(jLabel143);
        jLabel143.setBounds(480, 1900, 70, 23);

        imunisasi_morbili.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "No", "Yes" }));
        imunisasi_morbili.setName("imunisasi_morbili"); // NOI18N
        FormInput.add(imunisasi_morbili);
        imunisasi_morbili.setBounds(560, 1900, 60, 23);

        imunisasi_tetanus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "No", "Yes" }));
        imunisasi_tetanus.setName("imunisasi_tetanus"); // NOI18N
        FormInput.add(imunisasi_tetanus);
        imunisasi_tetanus.setBounds(410, 1930, 60, 23);

        imunisasi_thyphoid.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "No", "Yes" }));
        imunisasi_thyphoid.setName("imunisasi_thyphoid"); // NOI18N
        FormInput.add(imunisasi_thyphoid);
        imunisasi_thyphoid.setBounds(710, 1900, 60, 23);

        imunisasi_others.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "No", "Yes" }));
        imunisasi_others.setName("imunisasi_others"); // NOI18N
        FormInput.add(imunisasi_others);
        imunisasi_others.setBounds(560, 1930, 60, 23);

        imunisasi_hep_a.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "No", "Yes" }));
        imunisasi_hep_a.setName("imunisasi_hep_a"); // NOI18N
        FormInput.add(imunisasi_hep_a);
        imunisasi_hep_a.setBounds(110, 1930, 60, 23);

        jLabel144.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel144.setText("L. VERTEBRA");
        jLabel144.setName("jLabel144"); // NOI18N
        FormInput.add(jLabel144);
        jLabel144.setBounds(10, 1960, 180, 23);

        vertebra_scoliosis.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Yes", "No" }));
        vertebra_scoliosis.setName("vertebra_scoliosis"); // NOI18N
        FormInput.add(vertebra_scoliosis);
        vertebra_scoliosis.setBounds(110, 1980, 70, 23);

        jLabel145.setText("Hyperextensi 0-25 :");
        jLabel145.setName("jLabel145"); // NOI18N
        FormInput.add(jLabel145);
        jLabel145.setBounds(370, 1980, 120, 23);

        jLabel146.setText("Lordosis :");
        jLabel146.setName("jLabel146"); // NOI18N
        FormInput.add(jLabel146);
        jLabel146.setBounds(180, 1980, 120, 23);

        jLabel147.setText("Scoliosis :");
        jLabel147.setName("jLabel147"); // NOI18N
        FormInput.add(jLabel147);
        jLabel147.setBounds(10, 1980, 90, 23);

        vertebra_lordosis.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Yes", "No" }));
        vertebra_lordosis.setName("vertebra_lordosis"); // NOI18N
        FormInput.add(vertebra_lordosis);
        vertebra_lordosis.setBounds(310, 1980, 70, 23);

        vertebra_hyperextensi_0_25.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Yes", "No" }));
        vertebra_hyperextensi_0_25.setName("vertebra_hyperextensi_0_25"); // NOI18N
        FormInput.add(vertebra_hyperextensi_0_25);
        vertebra_hyperextensi_0_25.setBounds(500, 1980, 70, 23);

        vertebra_forward_flexion_0_80.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Yes", "No" }));
        vertebra_forward_flexion_0_80.setName("vertebra_forward_flexion_0_80"); // NOI18N
        FormInput.add(vertebra_forward_flexion_0_80);
        vertebra_forward_flexion_0_80.setBounds(310, 2010, 70, 23);

        jLabel148.setText("Toe Walking :");
        jLabel148.setName("jLabel148"); // NOI18N
        FormInput.add(jLabel148);
        jLabel148.setBounds(590, 2010, 80, 23);

        jLabel149.setText("Kyphosis :");
        jLabel149.setName("jLabel149"); // NOI18N
        FormInput.add(jLabel149);
        jLabel149.setBounds(10, 2010, 90, 23);

        jLabel150.setText("Lateral Flexion 0-20 :");
        jLabel150.setName("jLabel150"); // NOI18N
        FormInput.add(jLabel150);
        jLabel150.setBounds(370, 2010, 120, 23);

        jLabel151.setText("Squats x3 :");
        jLabel151.setName("jLabel151"); // NOI18N
        FormInput.add(jLabel151);
        jLabel151.setBounds(760, 1980, 60, 23);

        jLabel152.setText("Rorward Flexion 0-80 :");
        jLabel152.setName("jLabel152"); // NOI18N
        FormInput.add(jLabel152);
        jLabel152.setBounds(180, 2010, 120, 23);

        jLabel153.setText("Heel Walking :");
        jLabel153.setName("jLabel153"); // NOI18N
        FormInput.add(jLabel153);
        jLabel153.setBounds(590, 1980, 80, 23);

        vertebra_heel_walking.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Yes", "No" }));
        vertebra_heel_walking.setName("vertebra_heel_walking"); // NOI18N
        FormInput.add(vertebra_heel_walking);
        vertebra_heel_walking.setBounds(680, 1980, 70, 23);

        vertebra_lateral_flexion_0_20.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Yes", "No" }));
        vertebra_lateral_flexion_0_20.setName("vertebra_lateral_flexion_0_20"); // NOI18N
        FormInput.add(vertebra_lateral_flexion_0_20);
        vertebra_lateral_flexion_0_20.setBounds(500, 2010, 70, 23);

        vertebra_squats_x3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Yes", "No" }));
        vertebra_squats_x3.setName("vertebra_squats_x3"); // NOI18N
        FormInput.add(vertebra_squats_x3);
        vertebra_squats_x3.setBounds(830, 1980, 70, 23);

        vertebra_toe_walking.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Yes", "No" }));
        vertebra_toe_walking.setName("vertebra_toe_walking"); // NOI18N
        FormInput.add(vertebra_toe_walking);
        vertebra_toe_walking.setBounds(680, 2010, 70, 23);

        vertebra_kyphosis.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Yes", "No" }));
        vertebra_kyphosis.setName("vertebra_kyphosis"); // NOI18N
        FormInput.add(vertebra_kyphosis);
        vertebra_kyphosis.setBounds(110, 2010, 70, 23);

        jSeparator29.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator29.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator29.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator29.setName("jSeparator29"); // NOI18N
        FormInput.add(jSeparator29);
        jSeparator29.setBounds(0, 1960, 880, 1);

        jLabel154.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel154.setText("M. HASIL PEMERIKSAAN");
        jLabel154.setName("jLabel154"); // NOI18N
        FormInput.add(jLabel154);
        jLabel154.setBounds(10, 2050, 180, 23);

        exam_ent_comments.setFocusTraversalPolicyProvider(true);
        exam_ent_comments.setName("exam_ent_comments"); // NOI18N
        FormInput.add(exam_ent_comments);
        exam_ent_comments.setBounds(210, 2080, 670, 23);

        jLabel160.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel160.setText("1. ENT :");
        jLabel160.setName("jLabel160"); // NOI18N
        FormInput.add(jLabel160);
        jLabel160.setBounds(20, 2080, 190, 23);

        exam_cardio_vascular_system_comments.setFocusTraversalPolicyProvider(true);
        exam_cardio_vascular_system_comments.setName("exam_cardio_vascular_system_comments"); // NOI18N
        FormInput.add(exam_cardio_vascular_system_comments);
        exam_cardio_vascular_system_comments.setBounds(210, 2110, 670, 23);

        jLabel161.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel161.setText("2. Cardio Vascular System :");
        jLabel161.setName("jLabel161"); // NOI18N
        FormInput.add(jLabel161);
        jLabel161.setBounds(20, 2110, 190, 23);

        exam_respiratory_system_comments.setFocusTraversalPolicyProvider(true);
        exam_respiratory_system_comments.setName("exam_respiratory_system_comments"); // NOI18N
        FormInput.add(exam_respiratory_system_comments);
        exam_respiratory_system_comments.setBounds(210, 2140, 670, 23);

        jLabel162.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel162.setText("3. Respiratory System :");
        jLabel162.setName("jLabel162"); // NOI18N
        FormInput.add(jLabel162);
        jLabel162.setBounds(20, 2140, 190, 23);

        exam_abdomen_comments.setFocusTraversalPolicyProvider(true);
        exam_abdomen_comments.setName("exam_abdomen_comments"); // NOI18N
        FormInput.add(exam_abdomen_comments);
        exam_abdomen_comments.setBounds(210, 2170, 670, 23);

        jLabel163.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel163.setText("4. Abdomen :");
        jLabel163.setName("jLabel163"); // NOI18N
        FormInput.add(jLabel163);
        jLabel163.setBounds(20, 2170, 190, 23);

        exam_genito_urinary_system_comments.setFocusTraversalPolicyProvider(true);
        exam_genito_urinary_system_comments.setName("exam_genito_urinary_system_comments"); // NOI18N
        FormInput.add(exam_genito_urinary_system_comments);
        exam_genito_urinary_system_comments.setBounds(210, 2200, 670, 23);

        jLabel164.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel164.setText("5. Genito Urinary System :");
        jLabel164.setName("jLabel164"); // NOI18N
        FormInput.add(jLabel164);
        jLabel164.setBounds(20, 2200, 190, 23);

        exam_central_peripheral_nervous_system_comments.setFocusTraversalPolicyProvider(true);
        exam_central_peripheral_nervous_system_comments.setName("exam_central_peripheral_nervous_system_comments"); // NOI18N
        FormInput.add(exam_central_peripheral_nervous_system_comments);
        exam_central_peripheral_nervous_system_comments.setBounds(210, 2230, 670, 23);

        jLabel165.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel165.setText("6. Central & peripheral Nervous sys :");
        jLabel165.setName("jLabel165"); // NOI18N
        FormInput.add(jLabel165);
        jLabel165.setBounds(20, 2230, 190, 23);

        exam_skin_comments.setFocusTraversalPolicyProvider(true);
        exam_skin_comments.setName("exam_skin_comments"); // NOI18N
        FormInput.add(exam_skin_comments);
        exam_skin_comments.setBounds(210, 2260, 670, 23);

        jLabel166.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel166.setText("7. Skin :");
        jLabel166.setName("jLabel166"); // NOI18N
        FormInput.add(jLabel166);
        jLabel166.setBounds(20, 2260, 190, 23);

        exam_lymph_nodes_comments.setFocusTraversalPolicyProvider(true);
        exam_lymph_nodes_comments.setName("exam_lymph_nodes_comments"); // NOI18N
        FormInput.add(exam_lymph_nodes_comments);
        exam_lymph_nodes_comments.setBounds(210, 2290, 670, 23);

        jLabel167.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel167.setText("8. Lymph Nodes :");
        jLabel167.setName("jLabel167"); // NOI18N
        FormInput.add(jLabel167);
        jLabel167.setBounds(20, 2290, 190, 23);

        jLabel168.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel168.setText("9. Dental :");
        jLabel168.setName("jLabel168"); // NOI18N
        FormInput.add(jLabel168);
        jLabel168.setBounds(20, 2320, 190, 23);

        jLabel110.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel110.setText("A. INFORMASI UMUM");
        jLabel110.setName("jLabel110"); // NOI18N
        FormInput.add(jLabel110);
        jLabel110.setBounds(10, 90, 180, 23);

        jLabel118.setText("Year :");
        jLabel118.setName("jLabel118"); // NOI18N
        FormInput.add(jLabel118);
        jLabel118.setBounds(240, 50, 50, 23);

        jLabel40.setText("MCU Grup :");
        jLabel40.setName("jLabel40"); // NOI18N
        FormInput.add(jLabel40);
        jLabel40.setBounds(720, 110, 70, 23);

        SukuBangsa.setHighlighter(null);
        SukuBangsa.setName("SukuBangsa"); // NOI18N
        FormInput.add(SukuBangsa);
        SukuBangsa.setBounds(120, 170, 140, 23);

        jLabel43.setText("Badge :");
        jLabel43.setName("jLabel43"); // NOI18N
        FormInput.add(jLabel43);
        jLabel43.setBounds(270, 170, 40, 23);

        NIP.setHighlighter(null);
        NIP.setName("NIP"); // NOI18N
        FormInput.add(NIP);
        NIP.setBounds(320, 170, 100, 23);

        KdDokter1.setEditable(false);
        KdDokter1.setName("KdDokter1"); // NOI18N
        KdDokter1.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput.add(KdDokter1);
        KdDokter1.setBounds(290, 10, 100, 23);

        Year.setFocusTraversalPolicyProvider(true);
        Year.setName("Year"); // NOI18N
        Year.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput.add(Year);
        Year.setBounds(300, 50, 50, 23);

        jSeparator30.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator30.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator30.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator30.setName("jSeparator30"); // NOI18N
        FormInput.add(jSeparator30);
        jSeparator30.setBounds(0, 80, 836, 1);

        visual_fields_left.setFocusTraversalPolicyProvider(true);
        visual_fields_left.setName("visual_fields_left"); // NOI18N
        FormInput.add(visual_fields_left);
        visual_fields_left.setBounds(360, 1840, 100, 23);

        jLabel172.setText("Visual Fields :");
        jLabel172.setName("jLabel172"); // NOI18N
        FormInput.add(jLabel172);
        jLabel172.setBounds(250, 1840, 80, 23);

        jLabel173.setText("L :");
        jLabel173.setName("jLabel173"); // NOI18N
        FormInput.add(jLabel173);
        jLabel173.setBounds(340, 1840, 20, 23);

        jLabel174.setText("Requires Spectacles :");
        jLabel174.setName("jLabel174"); // NOI18N
        FormInput.add(jLabel174);
        jLabel174.setBounds(30, 2390, 120, 23);

        conclusion_requires_spectacles.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "No", "Yes" }));
        conclusion_requires_spectacles.setName("conclusion_requires_spectacles"); // NOI18N
        FormInput.add(conclusion_requires_spectacles);
        conclusion_requires_spectacles.setBounds(160, 2390, 60, 23);

        jLabel175.setText("Impaired Hearing :");
        jLabel175.setName("jLabel175"); // NOI18N
        FormInput.add(jLabel175);
        jLabel175.setBounds(280, 2420, 150, 23);

        jLabel176.setText("Fit :");
        jLabel176.setName("jLabel176"); // NOI18N
        FormInput.add(jLabel176);
        jLabel176.setBounds(30, 2450, 120, 23);

        jLabel177.setText("Vertigo :");
        jLabel177.setName("jLabel177"); // NOI18N
        FormInput.add(jLabel177);
        jLabel177.setBounds(620, 2390, 90, 23);

        jLabel178.setText("Respiratory Problem :");
        jLabel178.setName("jLabel178"); // NOI18N
        FormInput.add(jLabel178);
        jLabel178.setBounds(280, 2390, 150, 23);

        conclusion_respiratory_problem.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "No", "Yes" }));
        conclusion_respiratory_problem.setName("conclusion_respiratory_problem"); // NOI18N
        FormInput.add(conclusion_respiratory_problem);
        conclusion_respiratory_problem.setBounds(430, 2390, 60, 23);

        fit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Medically Fit", "Presently has minor medical problem", "Unfit" }));
        fit.setName("fit"); // NOI18N
        FormInput.add(fit);
        fit.setBounds(160, 2450, 200, 23);

        conclusion_vertigo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "No", "Yes" }));
        conclusion_vertigo.setName("conclusion_vertigo"); // NOI18N
        FormInput.add(conclusion_vertigo);
        conclusion_vertigo.setBounds(720, 2390, 60, 23);

        conclusion_impaired_hearing.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "No", "Yes" }));
        conclusion_impaired_hearing.setName("conclusion_impaired_hearing"); // NOI18N
        FormInput.add(conclusion_impaired_hearing);
        conclusion_impaired_hearing.setBounds(430, 2420, 60, 23);

        jLabel179.setText("Blood Group :");
        jLabel179.setName("jLabel179"); // NOI18N
        FormInput.add(jLabel179);
        jLabel179.setBounds(620, 2420, 90, 23);

        blood_group.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-" }));
        blood_group.setName("blood_group"); // NOI18N
        FormInput.add(blood_group);
        blood_group.setBounds(720, 2420, 60, 23);

        jLabel180.setText("Colour Blindness :");
        jLabel180.setName("jLabel180"); // NOI18N
        FormInput.add(jLabel180);
        jLabel180.setBounds(30, 2420, 120, 23);

        conclusion_colour_blindness.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "No", "Yes" }));
        conclusion_colour_blindness.setName("conclusion_colour_blindness"); // NOI18N
        FormInput.add(conclusion_colour_blindness);
        conclusion_colour_blindness.setBounds(160, 2420, 60, 23);

        jLabel181.setText("Fit with Restrictions :");
        jLabel181.setName("jLabel181"); // NOI18N
        FormInput.add(jLabel181);
        jLabel181.setBounds(20, 2480, 130, 23);

        fit_with_restrictions.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Medically fit but has following restrictions", "Work duties will be restricted", "Specify" }));
        fit_with_restrictions.setName("fit_with_restrictions"); // NOI18N
        FormInput.add(fit_with_restrictions);
        fit_with_restrictions.setBounds(160, 2480, 260, 23);

        exam_dental_comments.setFocusTraversalPolicyProvider(true);
        exam_dental_comments.setName("exam_dental_comments"); // NOI18N
        FormInput.add(exam_dental_comments);
        exam_dental_comments.setBounds(210, 2320, 670, 23);

        scrollPane18.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane18.setName("scrollPane18"); // NOI18N

        saran.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        saran.setColumns(20);
        saran.setRows(5);
        saran.setName("saran"); // NOI18N
        saran.setPreferredSize(new java.awt.Dimension(102, 52));
        scrollPane18.setViewportView(saran);

        FormInput.add(scrollPane18);
        scrollPane18.setBounds(50, 2950, 810, 53);

        audiometri_left_ear_1001.setFocusTraversalPolicyProvider(true);
        audiometri_left_ear_1001.setName("audiometri_left_ear_1001"); // NOI18N
        FormInput.add(audiometri_left_ear_1001);
        audiometri_left_ear_1001.setBounds(300, 1430, 50, 23);

        audiometri_left_ear_2001.setFocusTraversalPolicyProvider(true);
        audiometri_left_ear_2001.setName("audiometri_left_ear_2001"); // NOI18N
        FormInput.add(audiometri_left_ear_2001);
        audiometri_left_ear_2001.setBounds(300, 1490, 50, 23);

        jLabel182.setText("BC 1500 :");
        jLabel182.setName("jLabel182"); // NOI18N
        FormInput.add(jLabel182);
        jLabel182.setBounds(220, 1460, 70, 23);

        jLabel183.setText("BC 1000 :");
        jLabel183.setName("jLabel183"); // NOI18N
        FormInput.add(jLabel183);
        jLabel183.setBounds(220, 1430, 70, 23);

        audiometri_left_ear_1501.setFocusTraversalPolicyProvider(true);
        audiometri_left_ear_1501.setName("audiometri_left_ear_1501"); // NOI18N
        FormInput.add(audiometri_left_ear_1501);
        audiometri_left_ear_1501.setBounds(300, 1460, 50, 23);

        jLabel184.setText("BC 2000 :");
        jLabel184.setName("jLabel184"); // NOI18N
        FormInput.add(jLabel184);
        jLabel184.setBounds(220, 1490, 70, 23);

        jLabel185.setText(" BC 500 :");
        jLabel185.setName("jLabel185"); // NOI18N
        FormInput.add(jLabel185);
        jLabel185.setBounds(220, 1400, 70, 23);

        audiometri_left_ear_501.setFocusTraversalPolicyProvider(true);
        audiometri_left_ear_501.setName("audiometri_left_ear_501"); // NOI18N
        FormInput.add(audiometri_left_ear_501);
        audiometri_left_ear_501.setBounds(300, 1400, 50, 23);

        audiometri_left_ear_3001.setFocusTraversalPolicyProvider(true);
        audiometri_left_ear_3001.setName("audiometri_left_ear_3001"); // NOI18N
        FormInput.add(audiometri_left_ear_3001);
        audiometri_left_ear_3001.setBounds(300, 1520, 50, 23);

        jLabel186.setText("BC 3000 :");
        jLabel186.setName("jLabel186"); // NOI18N
        FormInput.add(jLabel186);
        jLabel186.setBounds(220, 1520, 70, 23);

        audiometri_left_ear_4001.setFocusTraversalPolicyProvider(true);
        audiometri_left_ear_4001.setName("audiometri_left_ear_4001"); // NOI18N
        FormInput.add(audiometri_left_ear_4001);
        audiometri_left_ear_4001.setBounds(300, 1550, 50, 23);

        jLabel187.setText("BC 4000 :");
        jLabel187.setName("jLabel187"); // NOI18N
        FormInput.add(jLabel187);
        jLabel187.setBounds(220, 1550, 70, 23);

        audiometri_left_ear_6001.setFocusTraversalPolicyProvider(true);
        audiometri_left_ear_6001.setName("audiometri_left_ear_6001"); // NOI18N
        FormInput.add(audiometri_left_ear_6001);
        audiometri_left_ear_6001.setBounds(300, 1610, 50, 23);

        jLabel188.setText("BC 6000 :");
        jLabel188.setName("jLabel188"); // NOI18N
        FormInput.add(jLabel188);
        jLabel188.setBounds(220, 1610, 70, 23);

        jLabel189.setText("BC 5000 :");
        jLabel189.setName("jLabel189"); // NOI18N
        FormInput.add(jLabel189);
        jLabel189.setBounds(220, 1580, 70, 23);

        audiometri_left_ear_5001.setFocusTraversalPolicyProvider(true);
        audiometri_left_ear_5001.setName("audiometri_left_ear_5001"); // NOI18N
        FormInput.add(audiometri_left_ear_5001);
        audiometri_left_ear_5001.setBounds(300, 1580, 50, 23);

        audiometri_left_ear_1002.setFocusTraversalPolicyProvider(true);
        audiometri_left_ear_1002.setName("audiometri_left_ear_1002"); // NOI18N
        FormInput.add(audiometri_left_ear_1002);
        audiometri_left_ear_1002.setBounds(580, 1430, 50, 23);

        audiometri_left_ear_2002.setFocusTraversalPolicyProvider(true);
        audiometri_left_ear_2002.setName("audiometri_left_ear_2002"); // NOI18N
        FormInput.add(audiometri_left_ear_2002);
        audiometri_left_ear_2002.setBounds(580, 1490, 50, 23);

        audiometri_left_ear_1502.setFocusTraversalPolicyProvider(true);
        audiometri_left_ear_1502.setName("audiometri_left_ear_1502"); // NOI18N
        FormInput.add(audiometri_left_ear_1502);
        audiometri_left_ear_1502.setBounds(580, 1460, 50, 23);

        audiometri_left_ear_502.setFocusTraversalPolicyProvider(true);
        audiometri_left_ear_502.setName("audiometri_left_ear_502"); // NOI18N
        FormInput.add(audiometri_left_ear_502);
        audiometri_left_ear_502.setBounds(580, 1400, 50, 23);

        audiometri_left_ear_3002.setFocusTraversalPolicyProvider(true);
        audiometri_left_ear_3002.setName("audiometri_left_ear_3002"); // NOI18N
        FormInput.add(audiometri_left_ear_3002);
        audiometri_left_ear_3002.setBounds(580, 1520, 50, 23);

        audiometri_left_ear_4002.setFocusTraversalPolicyProvider(true);
        audiometri_left_ear_4002.setName("audiometri_left_ear_4002"); // NOI18N
        FormInput.add(audiometri_left_ear_4002);
        audiometri_left_ear_4002.setBounds(580, 1550, 50, 23);

        audiometri_left_ear_6002.setFocusTraversalPolicyProvider(true);
        audiometri_left_ear_6002.setName("audiometri_left_ear_6002"); // NOI18N
        FormInput.add(audiometri_left_ear_6002);
        audiometri_left_ear_6002.setBounds(580, 1610, 50, 23);

        audiometri_left_ear_5002.setFocusTraversalPolicyProvider(true);
        audiometri_left_ear_5002.setName("audiometri_left_ear_5002"); // NOI18N
        FormInput.add(audiometri_left_ear_5002);
        audiometri_left_ear_5002.setBounds(580, 1580, 50, 23);

        audiometri_left_ear_1003.setFocusTraversalPolicyProvider(true);
        audiometri_left_ear_1003.setName("audiometri_left_ear_1003"); // NOI18N
        FormInput.add(audiometri_left_ear_1003);
        audiometri_left_ear_1003.setBounds(730, 1430, 50, 23);

        audiometri_left_ear_2003.setFocusTraversalPolicyProvider(true);
        audiometri_left_ear_2003.setName("audiometri_left_ear_2003"); // NOI18N
        FormInput.add(audiometri_left_ear_2003);
        audiometri_left_ear_2003.setBounds(730, 1490, 50, 23);

        audiometri_left_ear_1503.setFocusTraversalPolicyProvider(true);
        audiometri_left_ear_1503.setName("audiometri_left_ear_1503"); // NOI18N
        FormInput.add(audiometri_left_ear_1503);
        audiometri_left_ear_1503.setBounds(730, 1460, 50, 23);

        audiometri_left_ear_503.setFocusTraversalPolicyProvider(true);
        audiometri_left_ear_503.setName("audiometri_left_ear_503"); // NOI18N
        FormInput.add(audiometri_left_ear_503);
        audiometri_left_ear_503.setBounds(730, 1400, 50, 23);

        audiometri_left_ear_3003.setFocusTraversalPolicyProvider(true);
        audiometri_left_ear_3003.setName("audiometri_left_ear_3003"); // NOI18N
        FormInput.add(audiometri_left_ear_3003);
        audiometri_left_ear_3003.setBounds(730, 1520, 50, 23);

        audiometri_left_ear_4003.setFocusTraversalPolicyProvider(true);
        audiometri_left_ear_4003.setName("audiometri_left_ear_4003"); // NOI18N
        FormInput.add(audiometri_left_ear_4003);
        audiometri_left_ear_4003.setBounds(730, 1550, 50, 23);

        audiometri_left_ear_6003.setFocusTraversalPolicyProvider(true);
        audiometri_left_ear_6003.setName("audiometri_left_ear_6003"); // NOI18N
        FormInput.add(audiometri_left_ear_6003);
        audiometri_left_ear_6003.setBounds(730, 1610, 50, 23);

        audiometri_left_ear_5003.setFocusTraversalPolicyProvider(true);
        audiometri_left_ear_5003.setName("audiometri_left_ear_5003"); // NOI18N
        FormInput.add(audiometri_left_ear_5003);
        audiometri_left_ear_5003.setBounds(730, 1580, 50, 23);

        label15.setText("Dokter P.J. :");
        label15.setName("label15"); // NOI18N
        label15.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label15);
        label15.setBounds(210, 10, 70, 23);

        KdPetugas.setEditable(false);
        KdPetugas.setName("KdPetugas"); // NOI18N
        KdPetugas.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput.add(KdPetugas);
        KdPetugas.setBounds(440, 50, 100, 23);

        NmPetugas.setEditable(false);
        NmPetugas.setName("NmPetugas"); // NOI18N
        NmPetugas.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmPetugas);
        NmPetugas.setBounds(540, 50, 180, 23);

        BtnPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPetugas.setMnemonic('2');
        BtnPetugas.setToolTipText("Alt+2");
        BtnPetugas.setName("BtnPetugas"); // NOI18N
        BtnPetugas.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnPetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPetugasActionPerformed(evt);
            }
        });
        FormInput.add(BtnPetugas);
        BtnPetugas.setBounds(730, 50, 28, 23);

        ConcRadiologi.setName("ConcRadiologi"); // NOI18N
        ConcRadiologi.setPreferredSize(new java.awt.Dimension(80, 23));
        ConcRadiologi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ConcRadiologiKeyPressed(evt);
            }
        });
        FormInput.add(ConcRadiologi);
        ConcRadiologi.setBounds(130, 2760, 300, 23);

        jLabel101.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel101.setText("Phsycal Examination");
        jLabel101.setName("jLabel101"); // NOI18N
        FormInput.add(jLabel101);
        jLabel101.setBounds(50, 2670, 240, 20);

        jLabel104.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel104.setText("Conclusion");
        jLabel104.setName("jLabel104"); // NOI18N
        FormInput.add(jLabel104);
        jLabel104.setBounds(50, 2560, 160, 23);

        jLabel105.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel105.setText("Recommendation");
        jLabel105.setName("jLabel105"); // NOI18N
        FormInput.add(jLabel105);
        jLabel105.setBounds(330, 2560, 150, 23);

        scrollPane12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane12.setName("scrollPane12"); // NOI18N

        PhyExam.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        PhyExam.setColumns(20);
        PhyExam.setRows(5);
        PhyExam.setName("PhyExam"); // NOI18N
        PhyExam.setPreferredSize(new java.awt.Dimension(182, 52));
        scrollPane12.setViewportView(PhyExam);

        FormInput.add(scrollPane12);
        scrollPane12.setBounds(50, 2690, 390, 53);

        scrollPane13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane13.setName("scrollPane13"); // NOI18N

        ConcLab.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        ConcLab.setColumns(20);
        ConcLab.setRows(5);
        ConcLab.setName("ConcLab"); // NOI18N
        ConcLab.setPreferredSize(new java.awt.Dimension(182, 52));
        scrollPane13.setViewportView(ConcLab);

        FormInput.add(scrollPane13);
        scrollPane13.setBounds(480, 2690, 390, 53);

        jLabel155.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel155.setText("Medical History and General Information");
        jLabel155.setName("jLabel155"); // NOI18N
        FormInput.add(jLabel155);
        jLabel155.setBounds(610, 2560, 240, 20);

        jLabel156.setText("Chest x Ray :");
        jLabel156.setName("jLabel156"); // NOI18N
        FormInput.add(jLabel156);
        jLabel156.setBounds(40, 2760, 80, 23);

        scrollPane5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane5.setName("scrollPane5"); // NOI18N

        kesimpulan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        kesimpulan.setColumns(20);
        kesimpulan.setRows(5);
        kesimpulan.setName("kesimpulan"); // NOI18N
        scrollPane5.setViewportView(kesimpulan);

        FormInput.add(scrollPane5);
        scrollPane5.setBounds(50, 2580, 260, 70);

        scrollPane6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane6.setName("scrollPane6"); // NOI18N

        rekomendasi2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        rekomendasi2.setColumns(20);
        rekomendasi2.setRows(5);
        rekomendasi2.setName("rekomendasi2"); // NOI18N
        scrollPane6.setViewportView(rekomendasi2);

        FormInput.add(scrollPane6);
        scrollPane6.setBounds(330, 2580, 260, 70);

        scrollPane7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane7.setName("scrollPane7"); // NOI18N

        Dass21.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Dass21.setColumns(20);
        Dass21.setRows(5);
        Dass21.setName("Dass21"); // NOI18N
        scrollPane7.setViewportView(Dass21);

        FormInput.add(scrollPane7);
        scrollPane7.setBounds(610, 2580, 260, 70);

        jLabel158.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel158.setText("Laboratorium");
        jLabel158.setName("jLabel158"); // NOI18N
        FormInput.add(jLabel158);
        jLabel158.setBounds(480, 2670, 182, 23);

        ConcEcg.setName("ConcEcg"); // NOI18N
        ConcEcg.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput.add(ConcEcg);
        ConcEcg.setBounds(510, 2760, 350, 23);

        jLabel157.setText("EKG :");
        jLabel157.setName("jLabel157"); // NOI18N
        FormInput.add(jLabel157);
        jLabel157.setBounds(420, 2760, 80, 23);

        ConcSpirometry.setName("ConcSpirometry"); // NOI18N
        ConcSpirometry.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput.add(ConcSpirometry);
        ConcSpirometry.setBounds(130, 2800, 300, 23);

        jLabel159.setText("Spirometry :");
        jLabel159.setName("jLabel159"); // NOI18N
        FormInput.add(jLabel159);
        jLabel159.setBounds(40, 2800, 80, 23);

        ConcAudiometry.setName("ConcAudiometry"); // NOI18N
        ConcAudiometry.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput.add(ConcAudiometry);
        ConcAudiometry.setBounds(510, 2800, 350, 23);

        jLabel169.setText("Audiometry :");
        jLabel169.setName("jLabel169"); // NOI18N
        FormInput.add(jLabel169);
        jLabel169.setBounds(420, 2800, 80, 23);

        jLabel170.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel170.setText("Conclution :");
        jLabel170.setName("jLabel170"); // NOI18N
        FormInput.add(jLabel170);
        jLabel170.setBounds(50, 2830, 240, 20);

        scrollPane16.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane16.setName("scrollPane16"); // NOI18N

        Kesimpulan1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Kesimpulan1.setColumns(20);
        Kesimpulan1.setRows(5);
        Kesimpulan1.setName("Kesimpulan1"); // NOI18N
        Kesimpulan1.setPreferredSize(new java.awt.Dimension(182, 52));
        scrollPane16.setViewportView(Kesimpulan1);

        FormInput.add(scrollPane16);
        scrollPane16.setBounds(50, 2850, 390, 53);

        scrollPane17.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane17.setName("scrollPane17"); // NOI18N

        Note1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Note1.setColumns(20);
        Note1.setRows(5);
        Note1.setName("Note1"); // NOI18N
        Note1.setPreferredSize(new java.awt.Dimension(182, 52));
        scrollPane17.setViewportView(Note1);

        FormInput.add(scrollPane17);
        scrollPane17.setBounds(480, 2850, 390, 53);

        jLabel171.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel171.setText("Recommendation :");
        jLabel171.setName("jLabel171"); // NOI18N
        FormInput.add(jLabel171);
        jLabel171.setBounds(480, 2830, 182, 23);

        jSeparator31.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator31.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator31.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator31.setName("jSeparator31"); // NOI18N
        FormInput.add(jSeparator31);
        jSeparator31.setBounds(0, 2920, 836, 1);

        jLabel39.setText("Temp/Tgl Lahir :");
        jLabel39.setName("jLabel39"); // NOI18N
        FormInput.add(jLabel39);
        jLabel39.setBounds(370, 140, 100, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        FormInput.add(TPasien);
        TPasien.setBounds(120, 110, 260, 23);

        spirometri_fvc_1.setFocusTraversalPolicyProvider(true);
        spirometri_fvc_1.setName("spirometri_fvc_1"); // NOI18N
        FormInput.add(spirometri_fvc_1);
        spirometri_fvc_1.setBounds(220, 1170, 50, 23);

        jLabel88.setText("FVC (1) :");
        jLabel88.setName("jLabel88"); // NOI18N
        FormInput.add(jLabel88);
        jLabel88.setBounds(150, 1170, 60, 23);

        wbc.setFocusTraversalPolicyProvider(true);
        wbc.setName("wbc"); // NOI18N
        FormInput.add(wbc);
        wbc.setBounds(530, 960, 50, 23);

        jLabel90.setText("WBC :");
        jLabel90.setName("jLabel90"); // NOI18N
        FormInput.add(jLabel90);
        jLabel90.setBounds(490, 960, 40, 23);

        jLabel92.setText("ESR :");
        jLabel92.setName("jLabel92"); // NOI18N
        FormInput.add(jLabel92);
        jLabel92.setBounds(580, 960, 40, 23);

        esr.setFocusTraversalPolicyProvider(true);
        esr.setName("esr"); // NOI18N
        FormInput.add(esr);
        esr.setBounds(620, 960, 50, 23);

        jLabel93.setText("Bl.Gr :");
        jLabel93.setName("jLabel93"); // NOI18N
        FormInput.add(jLabel93);
        jLabel93.setBounds(670, 960, 40, 23);

        bl_group.setFocusTraversalPolicyProvider(true);
        bl_group.setName("bl_group"); // NOI18N
        FormInput.add(bl_group);
        bl_group.setBounds(710, 960, 50, 23);

        sgot.setFocusTraversalPolicyProvider(true);
        sgot.setName("sgot"); // NOI18N
        FormInput.add(sgot);
        sgot.setBounds(440, 990, 50, 23);

        jLabel94.setText("SGOT :");
        jLabel94.setName("jLabel94"); // NOI18N
        FormInput.add(jLabel94);
        jLabel94.setBounds(400, 990, 40, 23);

        sgpt.setFocusTraversalPolicyProvider(true);
        sgpt.setName("sgpt"); // NOI18N
        FormInput.add(sgpt);
        sgpt.setBounds(530, 990, 50, 23);

        jLabel95.setText("SGPT :");
        jLabel95.setName("jLabel95"); // NOI18N
        FormInput.add(jLabel95);
        jLabel95.setBounds(490, 990, 40, 23);

        jLabel96.setText("Urea :");
        jLabel96.setName("jLabel96"); // NOI18N
        FormInput.add(jLabel96);
        jLabel96.setBounds(580, 990, 40, 23);

        urea.setFocusTraversalPolicyProvider(true);
        urea.setName("urea"); // NOI18N
        FormInput.add(urea);
        urea.setBounds(620, 990, 50, 23);

        jLabel97.setText("Crea :");
        jLabel97.setName("jLabel97"); // NOI18N
        FormInput.add(jLabel97);
        jLabel97.setBounds(670, 990, 40, 23);

        creatinin.setFocusTraversalPolicyProvider(true);
        creatinin.setName("creatinin"); // NOI18N
        FormInput.add(creatinin);
        creatinin.setBounds(710, 990, 50, 23);

        jLabel98.setText("GT :");
        jLabel98.setName("jLabel98"); // NOI18N
        FormInput.add(jLabel98);
        jLabel98.setBounds(760, 960, 40, 23);

        gamaa_gt.setFocusTraversalPolicyProvider(true);
        gamaa_gt.setName("gamaa_gt"); // NOI18N
        FormInput.add(gamaa_gt);
        gamaa_gt.setBounds(800, 960, 50, 23);

        jLabel99.setText("Gluco :");
        jLabel99.setName("jLabel99"); // NOI18N
        FormInput.add(jLabel99);
        jLabel99.setBounds(760, 990, 40, 23);

        glucose.setFocusTraversalPolicyProvider(true);
        glucose.setName("glucose"); // NOI18N
        FormInput.add(glucose);
        glucose.setBounds(800, 990, 50, 23);

        random_glucose.setFocusTraversalPolicyProvider(true);
        random_glucose.setName("random_glucose"); // NOI18N
        FormInput.add(random_glucose);
        random_glucose.setBounds(340, 1040, 70, 23);

        jLabel100.setText("Rand Glucose :");
        jLabel100.setName("jLabel100"); // NOI18N
        FormInput.add(jLabel100);
        jLabel100.setBounds(250, 1040, 90, 23);

        total_cholestrol.setFocusTraversalPolicyProvider(true);
        total_cholestrol.setName("total_cholestrol"); // NOI18N
        FormInput.add(total_cholestrol);
        total_cholestrol.setBounds(480, 1040, 70, 23);

        jLabel106.setText("Ttl Cholest :");
        jLabel106.setName("jLabel106"); // NOI18N
        FormInput.add(jLabel106);
        jLabel106.setBounds(400, 1040, 80, 23);

        jLabel121.setText("Protein :");
        jLabel121.setName("jLabel121"); // NOI18N
        FormInput.add(jLabel121);
        jLabel121.setBounds(540, 1040, 50, 23);

        protein.setFocusTraversalPolicyProvider(true);
        protein.setName("protein"); // NOI18N
        FormInput.add(protein);
        protein.setBounds(590, 1040, 70, 23);

        jLabel124.setText("Blood :");
        jLabel124.setName("jLabel124"); // NOI18N
        FormInput.add(jLabel124);
        jLabel124.setBounds(660, 1040, 40, 23);

        blood.setFocusTraversalPolicyProvider(true);
        blood.setName("blood"); // NOI18N
        FormInput.add(blood);
        blood.setBounds(700, 1040, 70, 23);

        bilirubin.setFocusTraversalPolicyProvider(true);
        bilirubin.setName("bilirubin"); // NOI18N
        FormInput.add(bilirubin);
        bilirubin.setBounds(340, 1070, 70, 23);

        jLabel206.setText("Bilirubin :");
        jLabel206.setName("jLabel206"); // NOI18N
        FormInput.add(jLabel206);
        jLabel206.setBounds(280, 1070, 60, 23);

        malaria.setFocusTraversalPolicyProvider(true);
        malaria.setName("malaria"); // NOI18N
        FormInput.add(malaria);
        malaria.setBounds(480, 1070, 70, 23);

        jLabel207.setText("Malaria :");
        jLabel207.setName("jLabel207"); // NOI18N
        FormInput.add(jLabel207);
        jLabel207.setBounds(410, 1070, 60, 23);

        jLabel208.setText("TPHA :");
        jLabel208.setName("jLabel208"); // NOI18N
        FormInput.add(jLabel208);
        jLabel208.setBounds(550, 1070, 40, 23);

        tpha.setFocusTraversalPolicyProvider(true);
        tpha.setName("tpha"); // NOI18N
        FormInput.add(tpha);
        tpha.setBounds(590, 1070, 70, 23);

        jLabel209.setText("Mrd Test :");
        jLabel209.setName("jLabel209"); // NOI18N
        FormInput.add(jLabel209);
        jLabel209.setBounds(660, 1070, 40, 23);

        mantoux_test.setFocusTraversalPolicyProvider(true);
        mantoux_test.setName("mantoux_test"); // NOI18N
        FormInput.add(mantoux_test);
        mantoux_test.setBounds(700, 1070, 70, 23);

        jLabel210.setText("Leuco :");
        jLabel210.setName("jLabel210"); // NOI18N
        FormInput.add(jLabel210);
        jLabel210.setBounds(770, 1040, 40, 23);

        leukosit.setFocusTraversalPolicyProvider(true);
        leukosit.setName("leukosit"); // NOI18N
        FormInput.add(leukosit);
        leukosit.setBounds(810, 1040, 70, 23);

        jLabel211.setText("Others :");
        jLabel211.setName("jLabel211"); // NOI18N
        FormInput.add(jLabel211);
        jLabel211.setBounds(770, 1070, 40, 23);

        lab_others.setFocusTraversalPolicyProvider(true);
        lab_others.setName("lab_others"); // NOI18N
        FormInput.add(lab_others);
        lab_others.setBounds(810, 1070, 70, 23);

        ova.setFocusTraversalPolicyProvider(true);
        ova.setName("ova"); // NOI18N
        FormInput.add(ova);
        ova.setBounds(300, 1110, 100, 23);

        jLabel212.setText("OVA :");
        jLabel212.setName("jLabel212"); // NOI18N
        FormInput.add(jLabel212);
        jLabel212.setBounds(240, 1110, 60, 23);

        culture.setFocusTraversalPolicyProvider(true);
        culture.setName("culture"); // NOI18N
        FormInput.add(culture);
        culture.setBounds(450, 1110, 100, 23);

        jLabel213.setText("Culture :");
        jLabel213.setName("jLabel213"); // NOI18N
        FormInput.add(jLabel213);
        jLabel213.setBounds(390, 1110, 60, 23);

        jLabel214.setText("Cysta :");
        jLabel214.setName("jLabel214"); // NOI18N
        FormInput.add(jLabel214);
        jLabel214.setBounds(550, 1110, 40, 23);

        cysta.setFocusTraversalPolicyProvider(true);
        cysta.setName("cysta"); // NOI18N
        FormInput.add(cysta);
        cysta.setBounds(590, 1110, 100, 23);

        jLabel215.setText("Parasites :");
        jLabel215.setName("jLabel215"); // NOI18N
        FormInput.add(jLabel215);
        jLabel215.setBounds(690, 1110, 60, 23);

        pnemunosicosis2.setFocusTraversalPolicyProvider(true);
        pnemunosicosis2.setName("pnemunosicosis2"); // NOI18N
        FormInput.add(pnemunosicosis2);
        pnemunosicosis2.setBounds(790, 1170, 100, 23);

        jLabel216.setText("Pneumocosiosis :");
        jLabel216.setName("jLabel216"); // NOI18N
        FormInput.add(jLabel216);
        jLabel216.setBounds(540, 1170, 140, 23);

        jLabel217.setText("If yes-ILO Classification :");
        jLabel217.setName("jLabel217"); // NOI18N
        FormInput.add(jLabel217);
        jLabel217.setBounds(550, 1200, 130, 23);

        jLabel218.setText("Epidence of TB :");
        jLabel218.setName("jLabel218"); // NOI18N
        FormInput.add(jLabel218);
        jLabel218.setBounds(560, 1230, 120, 23);

        jLabel219.setText("Other Abnormalities :");
        jLabel219.setName("jLabel219"); // NOI18N
        FormInput.add(jLabel219);
        jLabel219.setBounds(560, 1260, 120, 23);

        parasites1.setFocusTraversalPolicyProvider(true);
        parasites1.setName("parasites1"); // NOI18N
        FormInput.add(parasites1);
        parasites1.setBounds(750, 1110, 100, 23);

        pnemunosicosis.setFocusTraversalPolicyProvider(true);
        pnemunosicosis.setName("pnemunosicosis"); // NOI18N
        FormInput.add(pnemunosicosis);
        pnemunosicosis.setBounds(680, 1170, 100, 23);

        ILO_clasification.setFocusTraversalPolicyProvider(true);
        ILO_clasification.setName("ILO_clasification"); // NOI18N
        FormInput.add(ILO_clasification);
        ILO_clasification.setBounds(680, 1200, 100, 23);

        ILO_clasification2.setFocusTraversalPolicyProvider(true);
        ILO_clasification2.setName("ILO_clasification2"); // NOI18N
        FormInput.add(ILO_clasification2);
        ILO_clasification2.setBounds(790, 1200, 100, 23);

        oth_abnormal.setFocusTraversalPolicyProvider(true);
        oth_abnormal.setName("oth_abnormal"); // NOI18N
        FormInput.add(oth_abnormal);
        oth_abnormal.setBounds(680, 1260, 210, 23);

        tb2.setFocusTraversalPolicyProvider(true);
        tb2.setName("tb2"); // NOI18N
        FormInput.add(tb2);
        tb2.setBounds(790, 1230, 100, 23);

        tb1.setFocusTraversalPolicyProvider(true);
        tb1.setName("tb1"); // NOI18N
        FormInput.add(tb1);
        tb1.setBounds(680, 1230, 100, 23);

        page3_comment.setFocusTraversalPolicyProvider(true);
        page3_comment.setName("page3_comment"); // NOI18N
        FormInput.add(page3_comment);
        page3_comment.setBounds(680, 1290, 210, 23);

        jLabel220.setText("Comment :");
        jLabel220.setName("jLabel220"); // NOI18N
        FormInput.add(jLabel220);
        jLabel220.setBounds(560, 1290, 120, 23);

        jLabel221.setText("AC 6000 :");
        jLabel221.setName("jLabel221"); // NOI18N
        FormInput.add(jLabel221);
        jLabel221.setBounds(500, 1610, 70, 23);

        jLabel222.setText("AC 5000 :");
        jLabel222.setName("jLabel222"); // NOI18N
        FormInput.add(jLabel222);
        jLabel222.setBounds(500, 1580, 70, 23);

        jLabel223.setText("AC 4000 :");
        jLabel223.setName("jLabel223"); // NOI18N
        FormInput.add(jLabel223);
        jLabel223.setBounds(500, 1550, 70, 23);

        jLabel224.setText("AC 3000 :");
        jLabel224.setName("jLabel224"); // NOI18N
        FormInput.add(jLabel224);
        jLabel224.setBounds(500, 1520, 70, 23);

        jLabel225.setText("AC 2000 :");
        jLabel225.setName("jLabel225"); // NOI18N
        FormInput.add(jLabel225);
        jLabel225.setBounds(500, 1490, 70, 23);

        jLabel226.setText("AC 1500 :");
        jLabel226.setName("jLabel226"); // NOI18N
        FormInput.add(jLabel226);
        jLabel226.setBounds(500, 1460, 70, 23);

        jLabel227.setText("AC 1000 :");
        jLabel227.setName("jLabel227"); // NOI18N
        FormInput.add(jLabel227);
        jLabel227.setBounds(500, 1430, 70, 23);

        jLabel228.setText("AC 500 :");
        jLabel228.setName("jLabel228"); // NOI18N
        FormInput.add(jLabel228);
        jLabel228.setBounds(500, 1400, 70, 23);

        jLabel229.setText(" BC 500 :");
        jLabel229.setName("jLabel229"); // NOI18N
        FormInput.add(jLabel229);
        jLabel229.setBounds(650, 1400, 70, 23);

        jLabel230.setText("BC 1000 :");
        jLabel230.setName("jLabel230"); // NOI18N
        FormInput.add(jLabel230);
        jLabel230.setBounds(650, 1430, 70, 23);

        jLabel231.setText("BC 1500 :");
        jLabel231.setName("jLabel231"); // NOI18N
        FormInput.add(jLabel231);
        jLabel231.setBounds(650, 1460, 70, 23);

        jLabel232.setText("BC 2000 :");
        jLabel232.setName("jLabel232"); // NOI18N
        FormInput.add(jLabel232);
        jLabel232.setBounds(650, 1490, 70, 23);

        jLabel233.setText("BC 3000 :");
        jLabel233.setName("jLabel233"); // NOI18N
        FormInput.add(jLabel233);
        jLabel233.setBounds(650, 1520, 70, 23);

        jLabel234.setText("BC 4000 :");
        jLabel234.setName("jLabel234"); // NOI18N
        FormInput.add(jLabel234);
        jLabel234.setBounds(650, 1550, 70, 23);

        jLabel235.setText("BC 5000 :");
        jLabel235.setName("jLabel235"); // NOI18N
        FormInput.add(jLabel235);
        jLabel235.setBounds(650, 1580, 70, 23);

        jLabel236.setText("BC 6000 :");
        jLabel236.setName("jLabel236"); // NOI18N
        FormInput.add(jLabel236);
        jLabel236.setBounds(650, 1610, 70, 23);

        ecg_abnormal.setFocusTraversalPolicyProvider(true);
        ecg_abnormal.setName("ecg_abnormal"); // NOI18N
        FormInput.add(ecg_abnormal);
        ecg_abnormal.setBounds(150, 1110, 100, 23);

        jLabel190.setText("Ear protection Wom :");
        jLabel190.setName("jLabel190"); // NOI18N
        FormInput.add(jLabel190);
        jLabel190.setBounds(10, 1350, 120, 23);

        type_of_hearing.setFocusTraversalPolicyProvider(true);
        type_of_hearing.setName("type_of_hearing"); // NOI18N
        FormInput.add(type_of_hearing);
        type_of_hearing.setBounds(570, 1350, 240, 23);

        eye_unaided_distant_l.setFocusTraversalPolicyProvider(true);
        eye_unaided_distant_l.setName("eye_unaided_distant_l"); // NOI18N
        eye_unaided_distant_l.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                eye_unaided_distant_lKeyPressed(evt);
            }
        });
        FormInput.add(eye_unaided_distant_l);
        eye_unaided_distant_l.setBounds(210, 1750, 70, 23);

        eye_unaided_distant_r.setFocusTraversalPolicyProvider(true);
        eye_unaided_distant_r.setName("eye_unaided_distant_r"); // NOI18N
        eye_unaided_distant_r.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                eye_unaided_distant_rKeyPressed(evt);
            }
        });
        FormInput.add(eye_unaided_distant_r);
        eye_unaided_distant_r.setBounds(410, 1750, 70, 23);

        scrollInput.setViewportView(FormInput);

        internalFrame2.add(scrollInput, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Input Pengkajian", internalFrame2);

        internalFrame3.setBorder(null);
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);
        Scroll.setPreferredSize(new java.awt.Dimension(452, 200));

        tbObat.setAutoCreateRowSorter(true);
        tbObat.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbObat.setComponentPopupMenu(jPopupMenu1);
        tbObat.setName("tbObat"); // NOI18N
        tbObat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbObatMouseClicked(evt);
            }
        });
        tbObat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbObatKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbObat);

        internalFrame3.add(Scroll, java.awt.BorderLayout.CENTER);

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel19.setText("Tgl.Asuhan :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "08-06-2026" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(DTPCari1);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("s.d.");
        jLabel21.setName("jLabel21"); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(23, 23));
        panelGlass9.add(jLabel21);

        DTPCari2.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "08-06-2026" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(DTPCari2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(195, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass9.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('3');
        BtnCari.setToolTipText("Alt+3");
        BtnCari.setName("BtnCari"); // NOI18N
        BtnCari.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariActionPerformed(evt);
            }
        });
        BtnCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCariKeyPressed(evt);
            }
        });
        panelGlass9.add(BtnCari);

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(60, 23));
        panelGlass9.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(LCount);

        internalFrame3.add(panelGlass9, java.awt.BorderLayout.PAGE_END);

        TabRawat.addTab("Data Pengkajian", internalFrame3);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        sinkronFieldMcu();
        if(TNoRM.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"Nama Pasien");
        }else if(comboTerpilih(cbConcEcg,"Abnormal")&&ecg_abnormal.getText().trim().equals("")){
            Valid.textKosong(ecg_abnormal,"Keterangan ECG Abnormal");
        }else if(Kesimpulan1.getText().trim().equals("")){
            Valid.textKosong(Kesimpulan1,"Kesimpulan");
        }else if(saran.getText().trim().equals("")){
            Valid.textKosong(saran,"Saran");
        }else if(NmDokter.getText().trim().equals("")){
            Valid.textKosong(BtnDokter,"Dokter");
        }else if(KdDokter1.getText().trim().equals("")){
            Valid.textKosong(BtnDokter,"Dokter");
//        }else if(RisikoLP.getText().trim().equals("")){
//            Valid.textKosong(RisikoLP,"Risiko Berdasar LP");
        }else{
            if(akses.getkode().equals("Admin Utama")){
                simpan();
            }else{
                if(TanggalRegistrasi.getText().equals("")){
                    TanggalRegistrasi.setText(Sequel.cariIsi("select concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) from reg_periksa where reg_periksa.no_rawat=?",TNoRw.getText()));
                }
                setTanggalAsuhanMinimalRegistrasi();
                if(Sequel.cekTanggalRegistrasi(TanggalRegistrasi.getText(),getTanggalJamAsuhan())==true){
                    simpan();
                }
            }
        }
    
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,Note1,BtnBatal);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void surnameKeyPressed(java.awt.event.KeyEvent evt) {
        Valid.pindah(evt,TPasien,TmpLahir);
    }

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        emptTeks();
}//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            emptTeks();
        }else{Valid.pindah(evt, BtnSimpan, BtnHapus);}
}//GEN-LAST:event_BtnBatalKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if(tbObat.getSelectedRow()>-1){
            if(akses.getkode().equals("Admin Utama")){
                hapus();
            }else{
                if(bolehUbahDataTerpilih()){
                    if(Sequel.cekTanggal48jam(getTabelValue("tanggal"),Sequel.ambiltanggalsekarang())==true){
                        hapus();
                    }
                }else{
                    JOptionPane.showMessageDialog(null,"Hanya bisa dihapus oleh dokter atau petugas penginput yang bersangkutan..!!");
                }
            }
        }else{
            JOptionPane.showMessageDialog(rootPane,"Silahkan anda pilih data terlebih dahulu..!!");
        }            
            
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnBatal, BtnEdit);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
        sinkronFieldMcu();
        if(TNoRM.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"Nama Pasien");
        }else if(comboTerpilih(cbConcEcg,"Abnormal")&&ecg_abnormal.getText().trim().equals("")){
            Valid.textKosong(ecg_abnormal,"Keterangan ECG Abnormal");
        }else if(Kesimpulan1.getText().trim().equals("")){
            Valid.textKosong(Kesimpulan1,"Kesimpulan");
        }else if(saran.getText().trim().equals("")){
            Valid.textKosong(saran,"Saran");
        }else if(NmDokter.getText().trim().equals("")){
            Valid.textKosong(BtnDokter,"Dokter");
        }else if(KdDokter1.getText().trim().equals("")){
            Valid.textKosong(BtnDokter,"Dokter");
//        }else if(RisikoLP.getText().trim().equals("")){
//            Valid.textKosong(RisikoLP,"Risiko Berdasar LP");
        }else{
            if(tbObat.getSelectedRow()>-1){
                if(akses.getkode().equals("Admin Utama")){
                    ganti();
                }else{
                    if(bolehUbahDataTerpilih()){
                        if(Sequel.cekTanggal48jam(getTabelValue("tanggal"),Sequel.ambiltanggalsekarang())==true){
                            if(TanggalRegistrasi.getText().equals("")){
                                TanggalRegistrasi.setText(Sequel.cariIsi("select concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) from reg_periksa where reg_periksa.no_rawat=?",TNoRw.getText()));
                            }
                            setTanggalAsuhanMinimalRegistrasi();
                            if(Sequel.cekTanggalRegistrasi(TanggalRegistrasi.getText(),getTanggalJamAsuhan())==true){
                                ganti();
                            }
                        }
                    }else{
                        JOptionPane.showMessageDialog(null,"Hanya bisa diganti oleh dokter atau petugas penginput yang bersangkutan..!!");
                    }
                }
            }else{
                JOptionPane.showMessageDialog(rootPane,"Silahkan anda pilih data terlebih dahulu..!!");
            }   
        }
}//GEN-LAST:event_BtnEditActionPerformed

    private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnEditActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnPrint);
        }
}//GEN-LAST:event_BtnEditKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnKeluarActionPerformed(null);
        }else{Valid.pindah(evt,BtnEdit,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            try{
                File g = new File("file2.css");            
                BufferedWriter bg = new BufferedWriter(new FileWriter(g));
                bg.write(
                    ".isi td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-bottom: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                    ".isi2 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#323232;}"+
                    ".isi3 td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                    ".isi4 td{font: 11px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                    ".isi5 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#AA0000;}"+
                    ".isi6 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#FF0000;}"+
                    ".isi7 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#C8C800;}"+
                    ".isi8 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#00AA00;}"+
                    ".isi9 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#969696;}"
                );
                bg.close();

                File f;            
                BufferedWriter bw;
                StringBuilder htmlContent;
                
                String pilihan =(String) JOptionPane.showInputDialog(null,"Silahkan pilih laporan..!","Pilihan Cetak",JOptionPane.QUESTION_MESSAGE,null,new Object[]{"Laporan 1 (HTML)","Laporan 2 (WPS)","Laporan 3 (CSV)","Laporan 4 (Jasper)"},"Laporan 1 (HTML)");
                if(cetakPilihanLaporan(pilihan)){
                    this.setCursor(Cursor.getDefaultCursor());
                    return;
                }
                switch (pilihan) {
                    case "Laporan 1 (HTML)":
                            if(System.currentTimeMillis() >= 0){
                                htmlContent = new StringBuilder();
                                htmlContent.append(headerExportHtml()).append(barisExportHtml());
                                LoadHTML.setText(
                                    "<html>"+
                                      "<table width='10000px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
                                       htmlContent.toString()+
                                      "</table>"+
                                    "</html>"
                                );

                                f = new File("DataMCU.wps");
                                bw = new BufferedWriter(new FileWriter(f));
                                bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                                            "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                                            "<table width='10000px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                                "<tr class='isi2'>"+
                                                    "<td valign='top' align='center'>"+
                                                        "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                                        akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                                        akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                                        "<font size='2' face='Tahoma'>DATA PEMERIKSAAN MCU<br><br></font>"+
                                                    "</td>"+
                                               "</tr>"+
                                            "</table>")
                                );
                                bw.close();
                                Desktop.getDesktop().browse(f.toURI());
                                break;
                            }
                            htmlContent = new StringBuilder();
                            htmlContent.append(                             
                                "<tr class='isi'>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Rawat</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.RM</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Pasien</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>J.K.</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tgl.Lahir</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tanggal</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Informasi</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Riwayat Peyakit Sekarang</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Riwayat Penyakit Keluarga</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Riwayat Penyakit Dahulu</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Alergi Makan & Obat</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keadaan Umum</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kesadaran</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>T.D.</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nadi</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>R.R.</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>T.B.</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>B.B.</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Suhu</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>B.M.I.</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Klasifikasi B.M.I.</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>L.P.</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Risiko Berdasar L.P.</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Submandibula</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Axilla</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Supraklavikula</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Leher</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Inguinal</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Oedema</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Frontalis</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Maxilaris</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Rambut</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Palpebra</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Sklera</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Cornea</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Buta Warna</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Konjungtiva</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Lensa</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Pupil</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Menggunakan Kacamata</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Visus</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Luas Lapang Pandang</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Luas Lapang Pandang</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Lubang Telinga</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Daun Telinga</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Selaput Pendengaran</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Proc.Mastoideus</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Septum Nasi</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Lubang Hidung</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Sinus</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Bibir</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Gusi</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Gigi</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Caries</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Lidah</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Faring</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tonsil</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kelenjar Limfe</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kelenjar Gondok</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Gerakan Dada</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Vocal Fremitus</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Perkusi Dada</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Bunyi Napas</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Bunyi Tambahan</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Ictus Cordis</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Bunyi Jantung</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Batas</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Mamae</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Mamae</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Inspeksi</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Palpasi</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Hepar</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Perkusi Abdomen</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Auskultasi</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Limpa</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Costovertebral</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Scoliosis</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kondisi Kulit</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Penyakit Kulit</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Extrimitas Atas</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Extrimitas Atas</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Extrimitas Bawah</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Extrimitas Bawah</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Area Genitalia</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Area Genitalia</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Anus & Perianal</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Anus & Perianal</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Pemeriksaan Laboratorium</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Rontgen Thorax</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>EKG</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Spirometri</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Audiometri</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Treadmill</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Romberg Test</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Back Strength</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>ABI Tangan Kanan</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>ABI Tangan Kiri</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>ABI Kaki Kanan</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>ABI Kaki Kiri</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Lain-lain</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Merokok</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Alkohol</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kesimpulan</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Anjuran</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kode Dokter</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Dokter Penanggung Jawab</b></td>").append(
                                "</tr>"
                            );
                            for (i = 0; i < tabMode.getRowCount(); i++) {
                                htmlContent.append(
                                    "<tr class='isi'>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,0).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,1).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,2).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,3).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,4).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,5).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,6).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,7).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,8).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,9).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,10).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,11).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,12).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,13).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,14).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,15).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,16).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,17).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,18).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,19).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,20).toString()).append("</td>").append( 
                                        "<td valign='top'>").append(tbObat.getValueAt(i,21).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,22).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,23).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,24).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,25).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,26).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,27).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,28).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,29).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,30).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,31).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,32).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,33).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,34).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,35).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,36).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,37).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,38).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,39).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,40).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,41).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,42).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,43).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,44).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,45).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,46).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,47).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,48).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,49).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,50).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,51).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,52).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,53).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,54).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,55).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,56).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,57).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,58).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,59).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,60).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,61).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,62).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,63).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,64).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,65).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,66).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,67).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,68).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,69).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,70).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,71).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,72).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,73).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,74).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,75).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,76).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,77).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,78).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,79).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,80).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,81).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,82).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,83).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,84).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,85).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,86).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,87).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,88).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,89).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,90).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,91).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,92).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,93).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,94).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,95).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,96).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,97).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,98).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,99).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,100).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,101).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,102).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,103).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,104).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,105).toString()).append("</td>").append(
                                    "</tr>");
                            }
                            LoadHTML.setText(
                                "<html>"+
                                  "<table width='10000px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
                                   htmlContent.toString()+
                                  "</table>"+
                                "</html>"
                            );

                            f = new File("DataMCU.html");            
                            bw = new BufferedWriter(new FileWriter(f));            
                            bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                                        "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                                        "<table width='10000px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                            "<tr class='isi2'>"+
                                                "<td valign='top' align='center'>"+
                                                    "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                                    akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                                    akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                                    "<font size='2' face='Tahoma'>DATA PEMERIKSAAN MCU<br><br></font>"+        
                                                "</td>"+
                                           "</tr>"+
                                        "</table>")
                            );
                            bw.close();                         
                            Desktop.getDesktop().browse(f.toURI());
                        break;
                    case "Laporan 2 (WPS)":
                            htmlContent = new StringBuilder();
                            htmlContent.append(                             
                                "<tr class='isi'>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Rawat</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.RM</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Pasien</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>J.K.</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tgl.Lahir</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tanggal</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Informasi</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Riwayat Peyakit Sekarang</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Riwayat Penyakit Keluarga</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Riwayat Penyakit Dahulu</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Alergi Makan & Obat</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keadaan Umum</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kesadaran</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>T.D.</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nadi</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>R.R.</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>T.B.</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>B.B.</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Suhu</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>B.M.I.</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Klasifikasi B.M.I.</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>L.P.</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Risiko Berdasar L.P.</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Submandibula</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Axilla</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Supraklavikula</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Leher</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Inguinal</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Oedema</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Frontalis</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Maxilaris</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Rambut</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Palpebra</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Sklera</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Cornea</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Buta Warna</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Konjungtiva</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Lensa</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Pupil</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Menggunakan Kacamata</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Visus</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Luas Lapang Pandang</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Luas Lapang Pandang</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Lubang Telinga</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Daun Telinga</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Selaput Pendengaran</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Proc.Mastoideus</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Septum Nasi</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Lubang Hidung</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Sinus</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Bibir</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Gusi</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Gigi</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Caries</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Lidah</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Faring</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tonsil</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kelenjar Limfe</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kelenjar Gondok</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Gerakan Dada</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Vocal Fremitus</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Perkusi Dada</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Bunyi Napas</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Bunyi Tambahan</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Ictus Cordis</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Bunyi Jantung</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Batas</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Mamae</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Mamae</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Inspeksi</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Palpasi</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Hepar</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Perkusi Abdomen</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Auskultasi</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Limpa</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Costovertebral</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Scoliosis</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kondisi Kulit</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Penyakit Kulit</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Extrimitas Atas</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Extrimitas Atas</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Extrimitas Bawah</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Extrimitas Bawah</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Area Genitalia</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Area Genitalia</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Anus & Perianal</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Anus & Perianal</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Pemeriksaan Laboratorium</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Rontgen Thorax</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>EKG</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Spirometri</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Audiometri</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Treadmill</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Romberg Test</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Back Strength</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>ABI Tangan Kanan</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>ABI Tangan Kiri</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>ABI Kaki Kanan</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>ABI Kaki Kiri</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Lain-lain</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Merokok</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Alkohol</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kesimpulan</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Anjuran</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kode Dokter</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Dokter Penanggung Jawab</b></td>").append(
                                "</tr>"
                            );
                            for (i = 0; i < tabMode.getRowCount(); i++) {
                                htmlContent.append(
                                    "<tr class='isi'>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,0).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,1).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,2).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,3).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,4).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,5).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,6).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,7).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,8).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,9).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,10).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,11).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,12).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,13).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,14).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,15).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,16).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,17).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,18).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,19).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,20).toString()).append("</td>").append( 
                                        "<td valign='top'>").append(tbObat.getValueAt(i,21).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,22).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,23).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,24).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,25).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,26).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,27).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,28).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,29).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,30).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,31).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,32).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,33).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,34).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,35).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,36).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,37).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,38).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,39).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,40).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,41).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,42).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,43).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,44).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,45).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,46).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,47).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,48).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,49).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,50).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,51).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,52).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,53).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,54).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,55).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,56).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,57).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,58).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,59).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,60).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,61).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,62).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,63).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,64).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,65).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,66).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,67).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,68).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,69).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,70).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,71).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,72).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,73).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,74).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,75).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,76).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,77).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,78).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,79).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,80).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,81).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,82).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,83).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,84).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,85).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,86).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,87).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,88).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,89).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,90).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,91).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,92).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,93).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,94).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,95).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,96).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,97).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,98).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,99).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,100).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,101).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,102).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,103).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,104).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,105).toString()).append("</td>").append(
                                    "</tr>");
                            }
                            LoadHTML.setText(
                                "<html>"+
                                  "<table width='10000px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
                                   htmlContent.toString()+
                                  "</table>"+
                                "</html>"
                            );

                            f = new File("DataMCU.wps");            
                            bw = new BufferedWriter(new FileWriter(f));            
                            bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                                        "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                                        "<table width='10000px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                            "<tr class='isi2'>"+
                                                "<td valign='top' align='center'>"+
                                                    "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                                    akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                                    akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                                    "<font size='2' face='Tahoma'>DATA PEMERIKSAAN MCU<br><br></font>"+        
                                                "</td>"+
                                           "</tr>"+
                                        "</table>")
                            );
                            bw.close();                         
                            Desktop.getDesktop().browse(f.toURI());
                        break;
                    case "Laporan 3 (CSV)":
                            if(System.currentTimeMillis() >= 0){
                                f = new File("DataMCU.csv");
                                bw = new BufferedWriter(new FileWriter(f));
                                bw.write(exportCsv().toString());
                                bw.close();
                                Desktop.getDesktop().browse(f.toURI());
                                break;
                            }
                            htmlContent = new StringBuilder();
                            htmlContent.append(                             
                                "\"No.Rawat\";\"No.RM\";\"Nama Pasien\";\"J.K.\";\"Tgl.Lahir\";\"Tanggal\";\"Informasi\";\"Riwayat Peyakit Sekarang\";\"Riwayat Penyakit Keluargan\";\"Riwayat Penyakit Dahulu\";\"Alergi Makan & Obat\";\"Keadaan Umum\";\"Kesadaran\";\"T.D.n\";\"Nadin\";\"R.R.\";\"T.B.\";\"B.B.\";\"Suhu\";\"B.M.I.\";\"Klasifikasi B.M.I.\";\"L.P.\";\"Risiko Berdasar L.P.\";\"Submandibula\";\"Axilla\";\"Supraklavikula\";\"Leher\";\"Inguinal\";\"Oedema\";\"Frontalis\";\"Maxilaris\";\"Rambut\";\"Palpebra\";\"Sklera\";\"Cornea\";\"Buta Warna\";\"Konjungtiva\";\"Lensa\";\"Pupil\";\"Menggunakan Kacamata\";\"Visus\";\"Luas Lapang Pandang\";\"Keterangan Luas Lapang Pandang\";\"Lubang Telinga\";\"Daun Telinga\";\"Selaput Pendengaran\";\"Proc.Mastoideus\";\"Septum Nasi\";\"Lubang Hidung\";\"Sinus\";\"Bibir\";\"Gusi\";\"Gigi\";\"Caries\";\"Lidah\";\"Faring\";\"Tonsil\";\"Kelenjar Limfe\";\"Kelenjar Gondok\";\"Gerakan Dada\";\"Vocal Fremitus\";\"Perkusi Dada\";\"Bunyi Napas\";\"Bunyi Tambahan\";\"Ictus Cordis\";\"Bunyi Jantung\";\"Batas\";\"Mamae\";\"Keterangan Mamae\";\"Inspeksi\";\"Palpasi\";\"Hepar\";\"Perkusi Abdomen\";\"Auskultasi\";\"Limpa\";\"Costovertebral\";\"Scoliosis\";\"Kondisi Kulit\";\"Penyakit Kulit\";\"Extrimitas Atas\";\"Keterangan Extrimitas Atas\";\"Extrimitas Bawah\";\"Keterangan Extrimitas Bawah\";\"Area Genitalia\";\"Keterangan Area Genitalia\";\"Anus & Perianal\";\"Keterangan Anus & Perianal\";\"Pemeriksaan Laboratoriumn\";\"Rontgen Thorax\";\"EKG\";\"Spirometri\";\"Audiometri\";\"Treadmill\";\"Romberg Test\";\"Back Strength\";\"ABI Tangan Kanan\";\"ABI Tangan Kiri\";\"ABI Kaki Kanan\";\"ABI Kaki Kiri\";\"Lain-lainn\";\"Merokokn\";\"Alkohol\";\"Kesimpulann\";\"Anjuran\";\"Kode Dokter\";\"Nama Dokter Penanggung Jawab\"\n"
                            ); 
                            for (i = 0; i < tabMode.getRowCount(); i++) {
                                htmlContent.append("\"").append(tbObat.getValueAt(i,0).toString()).append("\";\"").append(tbObat.getValueAt(i,1).toString()).append("\";\"").append(tbObat.getValueAt(i,2).toString()).append("\";\"").append(tbObat.getValueAt(i,3).toString()).append("\";\"").append(tbObat.getValueAt(i,4).toString()).append("\";\"").append(tbObat.getValueAt(i,5).toString()).append("\";\"").append(tbObat.getValueAt(i,6).toString()).append("\";\"").append(tbObat.getValueAt(i,7).toString()).append("\";\"").append(tbObat.getValueAt(i,8).toString()).append("\";\"").append(tbObat.getValueAt(i,9).toString()).append("\";\"").append(tbObat.getValueAt(i,10).toString()).append("\";\"").append(tbObat.getValueAt(i,11).toString()).append("\";\"").append(tbObat.getValueAt(i,12).toString()).append("\";\"").append(tbObat.getValueAt(i,13).toString()).append("\";\"").append(tbObat.getValueAt(i,14).toString()).append("\";\"").append(tbObat.getValueAt(i,15).toString()).append("\";\"").append(tbObat.getValueAt(i,16).toString()).append("\";\"").append(tbObat.getValueAt(i,17).toString()).append("\";\"").append(tbObat.getValueAt(i,18).toString()).append("\";\"").append(tbObat.getValueAt(i,19).toString()).append("\";\"").append(tbObat.getValueAt(i,20).toString()).append("\";\"").append(tbObat.getValueAt(i,21).toString()).append("\";\"").append(tbObat.getValueAt(i,22).toString()).append("\";\"").append(tbObat.getValueAt(i,23).toString()).append("\";\"").append(tbObat.getValueAt(i,24).toString()).append("\";\"").append(tbObat.getValueAt(i,25).toString()).append("\";\"").append(tbObat.getValueAt(i,26).toString()).append("\";\"").append(tbObat.getValueAt(i,27).toString()).append("\";\"").append(tbObat.getValueAt(i,28).toString()).append("\";\"").append(tbObat.getValueAt(i,29).toString()).append("\";\"").append(tbObat.getValueAt(i,30).toString()).append("\";\"").append(tbObat.getValueAt(i,31).toString()).append("\";\"").append(tbObat.getValueAt(i,32).toString()).append("\";\"").append(tbObat.getValueAt(i,33).toString()).append("\";\"").append(tbObat.getValueAt(i,34).toString()).append("\";\"").append(tbObat.getValueAt(i,35).toString()).append("\";\"").append(tbObat.getValueAt(i,36).toString()).append("\";\"").append(tbObat.getValueAt(i,37).toString()).append("\";\"").append(tbObat.getValueAt(i,38).toString()).append("\";\"").append(tbObat.getValueAt(i,39).toString()).append("\";\"").append(tbObat.getValueAt(i,40).toString()).append("\";\"").append(tbObat.getValueAt(i,41).toString()).append("\";\"").append(tbObat.getValueAt(i,42).toString()).append("\";\"").append(tbObat.getValueAt(i,43).toString()).append("\";\"").append(tbObat.getValueAt(i,44).toString()).append("\";\"").append(tbObat.getValueAt(i,45).toString()).append("\";\"").append(tbObat.getValueAt(i,46).toString()).append("\";\"").append(tbObat.getValueAt(i,47).toString()).append("\";\"").append(tbObat.getValueAt(i,48).toString()).append("\";\"").append(tbObat.getValueAt(i,49).toString()).append("\";\"").append(tbObat.getValueAt(i,50).toString()).append("\";\"").append(tbObat.getValueAt(i,51).toString()).append("\";\"").append(tbObat.getValueAt(i,52).toString()).append("\";\"").append(tbObat.getValueAt(i,53).toString()).append("\";\"").append(tbObat.getValueAt(i,54).toString()).append("\";\"").append(tbObat.getValueAt(i,55).toString()).append("\";\"").append(tbObat.getValueAt(i,56).toString()).append("\";\"").append(tbObat.getValueAt(i,57).toString()).append("\";\"").append(tbObat.getValueAt(i,58).toString()).append("\";\"").append(tbObat.getValueAt(i,59).toString()).append("\";\"").append(tbObat.getValueAt(i,60).toString()).append("\";\"").append(tbObat.getValueAt(i,61).toString()).append("\";\"").append(tbObat.getValueAt(i,62).toString()).append("\";\"").append(tbObat.getValueAt(i,63).toString()).append("\";\"").append(tbObat.getValueAt(i,64).toString()).append("\";\"").append(tbObat.getValueAt(i,65).toString()).append("\";\"").append(tbObat.getValueAt(i,66).toString()).append("\";\"").append(tbObat.getValueAt(i,67).toString()).append("\";\"").append(tbObat.getValueAt(i,68).toString()).append("\";\"").append(tbObat.getValueAt(i,69).toString()).append("\";\"").append(tbObat.getValueAt(i,70).toString()).append("\";\"").append(tbObat.getValueAt(i,71).toString()).append("\";\"").append(tbObat.getValueAt(i,72).toString()).append("\";\"").append(tbObat.getValueAt(i,73).toString()).append("\";\"").append(tbObat.getValueAt(i,74).toString()).append("\";\"").append(tbObat.getValueAt(i,75).toString()).append("\";\"").append(tbObat.getValueAt(i,76).toString()).append("\";\"").append(tbObat.getValueAt(i,77).toString()).append("\";\"").append(tbObat.getValueAt(i,78).toString()).append("\";\"").append(tbObat.getValueAt(i,79).toString()).append("\";\"").append(tbObat.getValueAt(i,80).toString()).append("\";\"").append(tbObat.getValueAt(i,81).toString()).append("\";\"").append(tbObat.getValueAt(i,82).toString()).append("\";\"").append(tbObat.getValueAt(i,83).toString()).append("\";\"").append(tbObat.getValueAt(i,84).toString()).append("\";\"").append(tbObat.getValueAt(i,85).toString()).append("\";\"").append(tbObat.getValueAt(i,86).toString()).append("\";\"").append(tbObat.getValueAt(i,87).toString()).append("\";\"").append(tbObat.getValueAt(i,88).toString()).append("\";\"").append(tbObat.getValueAt(i,89).toString()).append("\";\"").append(tbObat.getValueAt(i,90).toString()).append("\";\"").append(tbObat.getValueAt(i,91).toString()).append("\";\"").append(tbObat.getValueAt(i,92).toString()).append("\";\"").append(tbObat.getValueAt(i,93).toString()).append("\";\"").append(tbObat.getValueAt(i,94).toString()).append("\";\"").append(tbObat.getValueAt(i,95).toString()).append("\";\"").append(tbObat.getValueAt(i,96).toString()).append("\";\"").append(tbObat.getValueAt(i,97).toString()).append("\";\"").append(tbObat.getValueAt(i,98).toString()).append("\";\"").append(tbObat.getValueAt(i,99).toString()).append("\";\"").append(tbObat.getValueAt(i,100).toString()).append("\";\"").append(tbObat.getValueAt(i,101).toString()).append("\";\"").append(tbObat.getValueAt(i,102).toString()).append("\";\"").append(tbObat.getValueAt(i,103).toString()).append("\";\"").append(tbObat.getValueAt(i,104).toString()).append("\";\"").append(tbObat.getValueAt(i,104).toString()).append("\"\n");
                            }
                            f = new File("DataMCU.csv");            
                            bw = new BufferedWriter(new FileWriter(f));            
                            bw.write(htmlContent.toString());
                            bw.close();                         
                            Desktop.getDesktop().browse(f.toURI());
                        break; 
                }   
            }catch(Exception e){
                System.out.println("Notifikasi : "+e);
            }
        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnEdit, BtnKeluar);
        }
}//GEN-LAST:event_BtnPrintKeyPressed

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }
}//GEN-LAST:event_TCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        runBackground(() ->tampil());
}//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari, BtnAll);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        runBackground(() ->tampil());
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            TCari.setText("");
            runBackground(() ->tampil());
        }else{
            Valid.pindah(evt, BtnCari, surname);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void tbObatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbObatMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
            if((evt.getClickCount()==2)&&(tbObat.getSelectedColumn()==0)){
                TabRawat.setSelectedIndex(0);
            }
        }
}//GEN-LAST:event_tbObatMouseClicked

    private void tbObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbObatKeyPressed
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }else if(evt.getKeyCode()==KeyEvent.VK_SPACE){
                try {
                    getData();
                    TabRawat.setSelectedIndex(0);
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
}//GEN-LAST:event_tbObatKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        if(koneksiDB.CARICEPAT().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        runBackground(() ->tampil());
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        runBackground(() ->tampil());
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        runBackground(() ->tampil());
                    }
                }
            });
        }
    }//GEN-LAST:event_formWindowOpened

    private void MnPenilaianMCUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPenilaianMCUActionPerformed
        cetakJasperPenilaianMcuTerpilih();
    }//GEN-LAST:event_MnPenilaianMCUActionPerformed

    private void ConcRadiologiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ConcRadiologiKeyPressed
        Valid.pindah(evt,ConcRadiologi,ConcEcg);
    }//GEN-LAST:event_ConcRadiologiKeyPressed

    private void BtnPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPetugasActionPerformed
        if (petugas == null || !petugas.isDisplayable()) {
            petugas=new DlgCariPetugas(null,false);
            petugas.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            petugas.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    if(petugas.getTable().getSelectedRow()!= -1){
                        KdPetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                        NmPetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                    }
                    BtnPetugas.requestFocus();
                    petugas=null;
                }
            });

            petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            petugas.setLocationRelativeTo(internalFrame1);
        }
        if (petugas == null) return;
        if (!petugas.isVisible()) {
            petugas.isCek();
            petugas.emptTeks();
        }
        if (petugas.isVisible()) {
            petugas.toFront();
            return;
        }
        petugas.setVisible(true);
    }//GEN-LAST:event_BtnPetugasActionPerformed

    private void TBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TBKeyPressed
        //        Valid.pindah(evt,RR,BB);
    }//GEN-LAST:event_TBKeyPressed

    private void RRKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RRKeyPressed
        //        Valid.pindah(evt,Nadi,TB);
    }//GEN-LAST:event_RRKeyPressed

    private void NadiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NadiKeyPressed
        //Valid.pindah(evt,TD,RR);
    }//GEN-LAST:event_NadiKeyPressed

    private void RWP29ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RWP29ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_RWP29ActionPerformed

    private void RWP25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RWP25ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_RWP25ActionPerformed

    private void RWP28ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RWP28ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_RWP28ActionPerformed

    private void OtherJobKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_OtherJobKeyPressed
        Valid.pindah(evt,Hobby,PosisiKerja);
    }//GEN-LAST:event_OtherJobKeyPressed

    private void HobbyKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HobbyKeyPressed
        Valid.pindah(evt,Activities,OtherJob);
    }//GEN-LAST:event_HobbyKeyPressed

    private void ActivitiesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ActivitiesKeyPressed
        Valid.pindah(evt,JobTitle,Hobby);
    }//GEN-LAST:event_ActivitiesKeyPressed

    private void JobTitleKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JobTitleKeyPressed
        Valid.pindah(evt,Yoe,Activities);
    }//GEN-LAST:event_JobTitleKeyPressed

    private void YoeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_YoeKeyPressed
        Valid.pindah(evt,Doe,JobTitle);
    }//GEN-LAST:event_YoeKeyPressed

    private void DoeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DoeKeyPressed
        Valid.pindah(evt,NoTlp,Yoe);
    }//GEN-LAST:event_DoeKeyPressed

    private void NoTlpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoTlpKeyPressed
        Valid.pindah(evt,SttsNikah,Doe);
    }//GEN-LAST:event_NoTlpKeyPressed

    private void BtnDokter2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokter2ActionPerformed
        if(TNoRw.getText().equals("")&&TNoRM.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Pasien masih kosong...!!!");
        }else{
            RMCariHasilRadiologi cariradiologi=new RMCariHasilRadiologi(null,false);
            cariradiologi.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    if(cariradiologi.getTable().getSelectedRow()!= -1){
                        RongsenThorax.append(cariradiologi.getTable().getValueAt(cariradiologi.getTable().getSelectedRow(),2).toString()+", ");
                        RongsenThorax.requestFocus();
                    }
                }
            });
            cariradiologi.setNoRawat(TNoRw.getText());
            cariradiologi.tampil();
            cariradiologi.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            cariradiologi.setLocationRelativeTo(internalFrame1);
            cariradiologi.setVisible(true);
        }
    }//GEN-LAST:event_BtnDokter2ActionPerformed

    private void BtnDokter3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokter3ActionPerformed
        if(TNoRw.getText().equals("")&&TNoRM.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Pasien masih kosong...!!!");
        }else{
            RMCariHasilLaborat carilaborat=new RMCariHasilLaborat(null,false);
            carilaborat.getTable().addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {}
                @Override
                public void keyPressed(KeyEvent e) {
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        if(carilaborat.getTable().getSelectedRow()!= -1){
                            PemeriksaanLaboratorium.append(carilaborat.getTable().getValueAt(carilaborat.getTable().getSelectedRow(),3).toString()+", ");
                            PemeriksaanLaboratorium.requestFocus();
                        }
                    }
                }
                @Override
                public void keyReleased(KeyEvent e) {}
            });

            carilaborat.BtnKeluar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    for (i= 0; i < carilaborat.getTable().getRowCount(); i++) {
                        if(carilaborat.getTable().getValueAt(i,0).toString().equals("true")){
                            PemeriksaanLaboratorium.append(carilaborat.getTable().getValueAt(i,3).toString()+", ");
                        }
                    }
                    PemeriksaanLaboratorium.requestFocus();
                }
            });
            carilaborat.setNoRawat(TNoRw.getText());
            carilaborat.tampil();
            carilaborat.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            carilaborat.setLocationRelativeTo(internalFrame1);
            carilaborat.setVisible(true);
        }
    }//GEN-LAST:event_BtnDokter3ActionPerformed

    private void specifyKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_specifyKeyPressed
        Valid.pindah2(evt,saran,BtnSimpan);
    }//GEN-LAST:event_specifyKeyPressed

    private void BtnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokterActionPerformed
        if (dokter == null || !dokter.isDisplayable()) {
            dokter=new DlgCariDokter(null,false);
            dokter.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            dokter.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    if(dokter.getTable().getSelectedRow()!= -1){
                        KdDokter1.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                        NmDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                    }
                    BtnDokter.requestFocus();
                    dokter=null;
                }
            });
            dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            dokter.setLocationRelativeTo(internalFrame1);
        }
        if (dokter == null) return;
        dokter.isCek();
        if (dokter.isVisible()) {
            dokter.toFront();
            return;
        }
        dokter.setVisible(true);
    }//GEN-LAST:event_BtnDokterActionPerformed

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isRawat();
        }else{
            Valid.pindah(evt,TCari,BtnDokter);
        }
    }//GEN-LAST:event_TNoRwKeyPressed

    private void eye_unaided_distant_lKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_eye_unaided_distant_lKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_eye_unaided_distant_lKeyPressed

    private void eye_unaided_distant_rKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_eye_unaided_distant_rKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_eye_unaided_distant_rKeyPressed

    private void cetakJasperPenilaianMcuTerpilih() {
        if(tbObat.getSelectedRow()>-1){
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
            param.put("foto_pasien",getFotoPasien(getTabelValue("no_rawat")));
            String kdDokter=getTabelValue("kd_dokter");
            String nmDokter=getTabelValue("nm_dokter");
            String tanggal=getTabelValue("tanggal");
            String nmPetugas=getTabelValue("nm_petugas");
            if(nmPetugas.equals("")){
                nmPetugas=Sequel.cariIsi("select ifnull(petugas.nama,'') from penilaian_mcu "+
                        "left join petugas on penilaian_mcu.kd_petugas=petugas.nip "+
                        "where penilaian_mcu.no_rawat='"+escapeSql(getTabelValue("no_rawat"))+"'");
            }
            String finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",kdDokter);
            param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+nmDokter+"\nID "+(finger.equals("")?kdDokter:finger)+"\n"+Valid.SetTgl3(tanggal));
            param.put("petugas_penginput",nmPetugas);
            Valid.MyReportqry("RSPKrptCetakPenilaianAwalMCU.jasper","report","::[ Laporan Pengkajian Awal MCU ]::",
                queryCetakPenilaianMcu(getTabelValue("no_rawat")),param);
        }else{
            JOptionPane.showMessageDialog(rootPane,"Silahkan anda pilih data terlebih dahulu..!!");
        }
    }

    private String queryCetakPenilaianMcu(String noRawat) {
        if(gunakanQueryCetakPenilaianMcuDinamis()){
            return selectPenilaianMcu()+"where penilaian_mcu.no_rawat='"+escapeSql(noRawat)+"'";
        }
        return "select reg_periksa.no_rawat,ifnull(penilaian_mcu.no_rkm_medis,pasien.no_rkm_medis) as no_rkm_medis,"+
                "ifnull(penilaian_mcu.nama_pasien,pasien.nm_pasien) as nama_pasien,ifnull(penilaian_mcu.surname,'') as surname,"+
                "ifnull(penilaian_mcu.nama_pasien,pasien.nm_pasien) as nm_pasien,"+selectNamaPenanggungJawab()+" as perusahaan_pasien,pasien.nip,"+
                "ifnull(penilaian_mcu.tmp_lahir,pasien.tmp_lahir) as tmp_lahir,ifnull(penilaian_mcu.tgl_lahir,pasien.tgl_lahir) as tgl_lahir,"+
                "if(ifnull(penilaian_mcu.jk,pasien.jk) in ('L','Laki-laki','Laki laki'),'Laki laki','Perempuan') as jk,ifnull(penilaian_mcu.no_tlp,pasien.no_tlp) as no_tlp,"+
                "ifnull(nullif(penilaian_mcu.suku_bangsa,''),ifnull(suku_bangsa.nama_suku_bangsa,pasien.suku_bangsa)) as suku_bangsa,ifnull(penilaian_mcu.stts_nikah,pasien.stts_nikah) as stts_nikah,"+
                "concat(pasien.alamat,', ',ifnull(kelurahan.nm_kel,''),', ',ifnull(kecamatan.nm_kec,''),', ',ifnull(kabupaten.nm_kab,''),', ',ifnull(propinsi.nm_prop,'')) as alamat,"+
                "penilaian_mcu.tanggal,penilaian_mcu.`year`,penilaian_mcu.kd_dokter,dokter.nm_dokter,penilaian_mcu.kd_petugas,petugas.nama as nm_petugas,ifnull(nullif(penilaian_mcu.note1,''),penilaian_mcu.unfit_comment_1) as note1,penilaian_mcu.mcu_group,penilaian_mcu.dass_21,penilaian_mcu.phy_exam,"+
                "ifnull(nullif(penilaian_mcu.laborat,''),penilaian_mcu.conc_lab) as conc_lab,penilaian_mcu.conc_radiologi,penilaian_mcu.conc_ecg,penilaian_mcu.conc_spirometry,penilaian_mcu.conc_audiometry,penilaian_mcu.kesimpulan1,"+
                "penilaian_mcu.doe,penilaian_mcu.yoe,penilaian_mcu.job_title,penilaian_mcu.activities,penilaian_mcu.hobby,penilaian_mcu.other_job,penilaian_mcu.posisi_kerja,"+
                "penilaian_mcu.job_involves_driving_or_operating_mobile_equipment,penilaian_mcu.job_involves_working_at_heights,penilaian_mcu.job_involves_clerical_office_based_or_administrative,"+
                "penilaian_mcu.job_involves_requires_colour_vision,penilaian_mcu.job_involves_potential_dust_exposure,penilaian_mcu.job_involves_catering_staff_including_food_handlers,"+
                "penilaian_mcu.job_involves_exposing_to_other_potential_dangerous,penilaian_mcu.med_hist_head_injury_or_contussion,penilaian_mcu.med_hist_fainting_blackouts_epilepsy,"+
                "penilaian_mcu.med_hist_visual_changes,penilaian_mcu.med_hist_hearing_loss,penilaian_mcu.med_hist_nose_sinus_throat_trouble_more_4_weeks,"+
                "penilaian_mcu.med_hist_gynaecological_problems,penilaian_mcu.med_hist_chronic_skin_problem,penilaian_mcu.med_hist_chronic_diarrhea,"+
                "penilaian_mcu.med_hist_anorexia_more_4_weeks,penilaian_mcu.med_hist_gastritis,penilaian_mcu.med_hist_jaundice_hepatitis,"+
                "penilaian_mcu.med_hist_chronic_cough_more_4_weeks,penilaian_mcu.med_hist_haemorhoid,penilaian_mcu.med_hist_chronic_abdominal_pain,"+
                "penilaian_mcu.med_hist_diabetes,penilaian_mcu.med_hist_asthma,penilaian_mcu.med_hist_allergies,penilaian_mcu.med_hist_tuberculosis_bronchitis,"+
                "penilaian_mcu.med_hist_psychiatric_disorder,penilaian_mcu.med_hist_sexual_transmitted_diseases,penilaian_mcu.med_hist_unusual_change_of_weight_more_5kg_per_month,"+
                "penilaian_mcu.med_hist_hypertension,penilaian_mcu.med_hist_chest_pain_heart_disease,penilaian_mcu.med_hist_malaria_tropical_disease,"+
                "penilaian_mcu.med_hist_surgery_operation,penilaian_mcu.med_hist_back_pain_more_4_weeks,penilaian_mcu.med_hist_thypoid_fever,"+
                "penilaian_mcu.med_hist_swollen_or_painful_joints,penilaian_mcu.med_hist_kidney_problem_urinary_stones,penilaian_mcu.med_hist_other_chronical_diseases,"+
                "penilaian_mcu.hb,penilaian_mcu.wbc,penilaian_mcu.esr,penilaian_mcu.bl_group,penilaian_mcu.gamaa_gt,penilaian_mcu.sgot,penilaian_mcu.sgpt,penilaian_mcu.urea,penilaian_mcu.creatinin,penilaian_mcu.glucose,"+
                "penilaian_mcu.td,penilaian_mcu.nadi,penilaian_mcu.rr,penilaian_mcu.tb,penilaian_mcu.bb,penilaian_mcu.bmi,penilaian_mcu.laborat,penilaian_mcu.radiologi,penilaian_mcu.ekg,"+
                "penilaian_mcu.spirometri_vc_1,penilaian_mcu.spirometri_vc_2,penilaian_mcu.spirometri_vc_3,penilaian_mcu.spirometri_vc_4,penilaian_mcu.spirometri_fvc_1,penilaian_mcu.spirometri_fvc_2,"+
                "penilaian_mcu.spirometri_fvc_3,penilaian_mcu.spirometri_fvc_4,penilaian_mcu.spirometri_fev_1_1,penilaian_mcu.spirometri_fev_1_2,penilaian_mcu.spirometri_fev_1_3,penilaian_mcu.spirometri_fev_1_4,"+
                "penilaian_mcu.spirometri_fev_1_fvc_1,penilaian_mcu.spirometri_fev_1_fvc_2,penilaian_mcu.spirometri_fev_1_fvc_3,penilaian_mcu.spirometri_fev_1_fvc_4,penilaian_mcu.type_of_hearing,"+
                "penilaian_mcu.audiometri_left_ear_500_AB,penilaian_mcu.audiometri_left_ear_1000_AB,penilaian_mcu.audiometri_left_ear_1500_AB,penilaian_mcu.audiometri_left_ear_2000_AB,"+
                "penilaian_mcu.audiometri_left_ear_3000_AB,penilaian_mcu.audiometri_left_ear_4000_AB,penilaian_mcu.audiometri_left_ear_5000_AB,penilaian_mcu.audiometri_left_ear_6000_AB,"+
                "penilaian_mcu.audiometri_left_ear_500_AC,penilaian_mcu.audiometri_left_ear_1000_AC,penilaian_mcu.audiometri_left_ear_1500_AC,penilaian_mcu.audiometri_left_ear_2000_AC,"+
                "penilaian_mcu.audiometri_left_ear_3000_AC,penilaian_mcu.audiometri_left_ear_4000_AC,penilaian_mcu.audiometri_left_ear_5000_AC,penilaian_mcu.audiometri_left_ear_6000_AC,"+
                "penilaian_mcu.audiometri_right_ear_500_ab,penilaian_mcu.audiometri_right_ear_1000_ab,penilaian_mcu.audiometri_right_ear_1500_ab,penilaian_mcu.audiometri_right_ear_2000_ab,"+
                "penilaian_mcu.audiometri_right_ear_3000_ab,penilaian_mcu.audiometri_right_ear_4000_ab,penilaian_mcu.audiometri_right_ear_5000_ab,penilaian_mcu.audiometri_right_ear_6000_ab,"+
                "penilaian_mcu.audiometri_right_ear_500_ac,penilaian_mcu.audiometri_right_ear_1000_ac,penilaian_mcu.audiometri_right_ear_1500_ac,penilaian_mcu.audiometri_right_ear_2000_ac,"+
                "penilaian_mcu.audiometri_right_ear_3000_ac,penilaian_mcu.audiometri_right_ear_4000_ac,penilaian_mcu.audiometri_right_ear_5000_ac,penilaian_mcu.audiometri_right_ear_6000_ac,"+
                "penilaian_mcu.eye_color_blindless,penilaian_mcu.visual_fields_left,penilaian_mcu.visual_fields_right,penilaian_mcu.fundi,penilaian_mcu.blood_group,penilaian_mcu.medically_fit,"+
                "penilaian_mcu.fit_with_restrictions,penilaian_mcu.specify,penilaian_mcu.unfit_comment_1 "+
                "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join penilaian_mcu on reg_periksa.no_rawat=penilaian_mcu.no_rawat "+
                "left join suku_bangsa on suku_bangsa.id=pasien.suku_bangsa "+
                "left join kelurahan on pasien.kd_kel=kelurahan.kd_kel "+
                "left join kecamatan on pasien.kd_kec=kecamatan.kd_kec "+
                "left join kabupaten on pasien.kd_kab=kabupaten.kd_kab "+
                "left join propinsi on pasien.kd_prop=propinsi.kd_prop "+
                "left join perusahaan_pasien on perusahaan_pasien.kode_perusahaan=pasien.perusahaan_pasien "+
                "left join dokter on penilaian_mcu.kd_dokter=dokter.kd_dokter "+
                "left join petugas on penilaian_mcu.kd_petugas=petugas.nip "+
                "where reg_periksa.no_rawat='"+escapeSql(noRawat)+"'";
    }

    private boolean gunakanQueryCetakPenilaianMcuDinamis() {
        return true;
    }

    private String escapeSql(String nilai) {
        return nilai == null ? "" : nilai.replace("'", "''");
    }

    private java.awt.Image getFotoPasien(String noRawat) {
        String namaFoto=Sequel.cariIsi(
                "select ifnull(personal_pasien.gambar,'') from reg_periksa "+
                "inner join personal_pasien on personal_pasien.no_rkm_medis=reg_periksa.no_rkm_medis "+
                "where reg_periksa.no_rawat='"+escapeSql(noRawat)+"'");
        if(namaFoto.equals("")||namaFoto.equals("-")){
            return null;
        }
        try {
            return javax.imageio.ImageIO.read(new java.net.URL("http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/photopasien/"+namaFoto));
        } catch (Exception e) {
            System.out.println("Notif Foto Pasien MCU : "+e);
            return null;
        }
    }

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMMCU dialog = new RMMCU(new javax.swing.JFrame(), true);
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private widget.TextBox Activities;
    private widget.TextBox AlcoholGrWeek;
    private widget.TextBox AnyAllergies;
    private widget.TextBox BB;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnDokter;
    private widget.Button BtnDokter2;
    private widget.Button BtnDokter3;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPetugas;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.TextBox CigarettesPerday;
    private widget.TextBox ConcAudiometry;
    private widget.TextBox ConcEcg;
    private widget.TextArea ConcLab;
    private widget.TextBox ConcRadiologi;
    private widget.TextBox ConcSpirometry;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.TextArea Dass21;
    private widget.TextBox Doe;
    private widget.TextBox FamilyHistoryFather;
    private widget.TextBox FamilyHistoryMother;
    private widget.TextBox FamilyHistoryOther;
    private widget.TextBox FamilyHistorySiblings;
    private widget.PanelBiasa FormInput;
    private widget.TextBox Hobby;
    private widget.TextBox ILO_clasification;
    private widget.TextBox ILO_clasification2;
    private widget.TextBox IMT;
    private widget.TextBox Jk;
    private widget.CekBox JobInvolvesCateringStaffIncludingFoodHandlers;
    private widget.CekBox JobInvolvesClericalOfficeBasedOrAdministrative;
    private widget.CekBox JobInvolvesDrivingOrOperatingMobileEquipment;
    private widget.CekBox JobInvolvesExposingToOtherPotentialDangerous;
    private widget.CekBox JobInvolvesPotentialDustExposure;
    private widget.CekBox JobInvolvesRequiresColourVision;
    private widget.CekBox JobInvolvesWorkingAtHeights;
    private widget.TextBox JobTitle;
    private widget.TextBox KdDokter1;
    private widget.TextBox KdPetugas;
    private widget.TextArea Kesimpulan1;
    private widget.TextBox KlasifikasiIMT1;
    private widget.Label LCount;
    private widget.editorpane LoadHTML;
    private widget.ComboBox McuGroup;
    private javax.swing.JMenuItem MnPenilaianMCU;
    private widget.TextBox NIP;
    private widget.TextBox Nadi;
    private widget.TextBox NmDokter;
    private widget.TextBox NmPetugas;
    private widget.TextBox NoTlp;
    private widget.TextArea Note1;
    private widget.TextBox OtherJob;
    private widget.TextArea PemeriksaanLaboratorium;
    private widget.TextBox Perusahaan;
    private widget.TextArea PhyExam;
    private widget.ComboBox PosisiKerja;
    private widget.TextBox PrescribedMedication;
    private widget.TextBox PrescribedMedication2;
    private widget.TextBox RR;
    private widget.CekBox RWP1;
    private widget.CekBox RWP10;
    private widget.CekBox RWP11;
    private widget.CekBox RWP12;
    private widget.CekBox RWP13;
    private widget.CekBox RWP14;
    private widget.CekBox RWP15;
    private widget.CekBox RWP16;
    private widget.CekBox RWP17;
    private widget.CekBox RWP18;
    private widget.CekBox RWP19;
    private widget.CekBox RWP2;
    private widget.CekBox RWP20;
    private widget.CekBox RWP21;
    private widget.CekBox RWP22;
    private widget.CekBox RWP23;
    private widget.CekBox RWP24;
    private widget.CekBox RWP25;
    private widget.CekBox RWP26;
    private widget.CekBox RWP27;
    private widget.CekBox RWP28;
    private widget.CekBox RWP29;
    private widget.CekBox RWP3;
    private widget.CekBox RWP30;
    private widget.CekBox RWP4;
    private widget.CekBox RWP5;
    private widget.CekBox RWP6;
    private widget.CekBox RWP7;
    private widget.CekBox RWP8;
    private widget.CekBox RWP9;
    private widget.TextArea RongsenThorax;
    private widget.ScrollPane Scroll;
    private widget.ComboBox SttsNikah;
    private widget.TextBox SukuBangsa;
    private widget.TextBox TB;
    private widget.TextBox TCari;
    private widget.TextBox TD;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private javax.swing.JTabbedPane TabRawat;
    private widget.TextBox TanggalRegistrasi;
    private widget.Tanggal TglAsuhan;
    private widget.TextBox TglLahir;
    private widget.TextBox TmpLahir;
    private widget.TextBox Year;
    private widget.TextBox Yoe;
    private widget.CekBox audiometri_ear_protection_worn_always;
    private widget.CekBox audiometri_ear_protection_worn_never;
    private widget.CekBox audiometri_ear_protection_worn_often;
    private widget.CekBox audiometri_ear_protection_worn_previously;
    private widget.CekBox audiometri_ear_protection_worn_rarely;
    private widget.TextBox audiometri_left_ear_1000;
    private widget.TextBox audiometri_left_ear_1001;
    private widget.TextBox audiometri_left_ear_1002;
    private widget.TextBox audiometri_left_ear_1003;
    private widget.TextBox audiometri_left_ear_1500;
    private widget.TextBox audiometri_left_ear_1501;
    private widget.TextBox audiometri_left_ear_1502;
    private widget.TextBox audiometri_left_ear_1503;
    private widget.TextBox audiometri_left_ear_2000;
    private widget.TextBox audiometri_left_ear_2001;
    private widget.TextBox audiometri_left_ear_2002;
    private widget.TextBox audiometri_left_ear_2003;
    private widget.TextBox audiometri_left_ear_3000;
    private widget.TextBox audiometri_left_ear_3001;
    private widget.TextBox audiometri_left_ear_3002;
    private widget.TextBox audiometri_left_ear_3003;
    private widget.TextBox audiometri_left_ear_4000;
    private widget.TextBox audiometri_left_ear_4001;
    private widget.TextBox audiometri_left_ear_4002;
    private widget.TextBox audiometri_left_ear_4003;
    private widget.TextBox audiometri_left_ear_500;
    private widget.TextBox audiometri_left_ear_5000;
    private widget.TextBox audiometri_left_ear_5001;
    private widget.TextBox audiometri_left_ear_5002;
    private widget.TextBox audiometri_left_ear_5003;
    private widget.TextBox audiometri_left_ear_501;
    private widget.TextBox audiometri_left_ear_502;
    private widget.TextBox audiometri_left_ear_503;
    private widget.TextBox audiometri_left_ear_6000;
    private widget.TextBox audiometri_left_ear_6001;
    private widget.TextBox audiometri_left_ear_6002;
    private widget.TextBox audiometri_left_ear_6003;
    private widget.CekBox audiometri_tinitus_always;
    private widget.CekBox audiometri_tinitus_never;
    private widget.CekBox audiometri_tinitus_often;
    private widget.CekBox audiometri_tinitus_previously;
    private widget.CekBox audiometri_tinitus_rarely;
    private widget.TextBox bilirubin;
    private widget.TextBox bl_group;
    private widget.TextBox blood;
    private widget.ComboBox blood_group;
    private widget.ComboBox cbConcEcg;
    private widget.ComboBox conclusion_colour_blindness;
    private widget.ComboBox conclusion_impaired_hearing;
    private widget.ComboBox conclusion_requires_spectacles;
    private widget.ComboBox conclusion_respiratory_problem;
    private widget.ComboBox conclusion_vertigo;
    private widget.TextBox creatinin;
    private widget.TextBox culture;
    private widget.TextBox cysta;
    private widget.TextBox ecg_abnormal;
    private widget.TextBox esr;
    private widget.TextBox exam_abdomen_comments;
    private widget.TextBox exam_cardio_vascular_system_comments;
    private widget.TextBox exam_central_peripheral_nervous_system_comments;
    private widget.TextBox exam_dental_comments;
    private widget.TextBox exam_ent_comments;
    private widget.TextBox exam_genito_urinary_system_comments;
    private widget.TextBox exam_lymph_nodes_comments;
    private widget.TextBox exam_respiratory_system_comments;
    private widget.TextBox exam_skin_comments;
    private widget.ComboBox eye_brake_test_1;
    private widget.ComboBox eye_brake_test_2;
    private widget.ComboBox eye_color_blindless;
    private widget.ComboBox eye_glasses_distant_l;
    private widget.ComboBox eye_glasses_distant_r;
    private widget.ComboBox eye_glasses_near_l;
    private widget.ComboBox eye_glasses_near_r;
    private widget.ComboBox eye_night_vision_1;
    private widget.ComboBox eye_night_vision_2;
    private widget.TextBox eye_unaided_distant_l;
    private widget.TextBox eye_unaided_distant_r;
    private widget.ComboBox eye_unaided_near_l;
    private widget.ComboBox eye_unaided_near_r;
    private widget.ComboBox fit;
    private widget.ComboBox fit_with_restrictions;
    private widget.ComboBox fundi;
    private widget.TextBox gamaa_gt;
    private widget.TextBox glucose;
    private widget.TextBox hb;
    private widget.ComboBox imunisasi_bcg;
    private widget.ComboBox imunisasi_dpt;
    private widget.ComboBox imunisasi_hep_a;
    private widget.ComboBox imunisasi_hep_b;
    private widget.ComboBox imunisasi_morbili;
    private widget.ComboBox imunisasi_others;
    private widget.ComboBox imunisasi_polio;
    private widget.ComboBox imunisasi_tetanus;
    private widget.ComboBox imunisasi_thyphoid;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.Label jLabel10;
    private widget.Label jLabel100;
    private widget.Label jLabel101;
    private widget.Label jLabel102;
    private widget.Label jLabel103;
    private widget.Label jLabel104;
    private widget.Label jLabel105;
    private widget.Label jLabel106;
    private widget.Label jLabel107;
    private widget.Label jLabel108;
    private widget.Label jLabel109;
    private widget.Label jLabel110;
    private widget.Label jLabel111;
    private widget.Label jLabel112;
    private widget.Label jLabel113;
    private widget.Label jLabel114;
    private widget.Label jLabel115;
    private widget.Label jLabel116;
    private widget.Label jLabel117;
    private widget.Label jLabel118;
    private widget.Label jLabel119;
    private widget.Label jLabel12;
    private widget.Label jLabel120;
    private widget.Label jLabel121;
    private widget.Label jLabel122;
    private widget.Label jLabel123;
    private widget.Label jLabel124;
    private widget.Label jLabel125;
    private widget.Label jLabel126;
    private widget.Label jLabel127;
    private widget.Label jLabel128;
    private widget.Label jLabel129;
    private widget.Label jLabel13;
    private widget.Label jLabel130;
    private widget.Label jLabel131;
    private widget.Label jLabel132;
    private widget.Label jLabel133;
    private widget.Label jLabel134;
    private widget.Label jLabel135;
    private widget.Label jLabel136;
    private widget.Label jLabel137;
    private widget.Label jLabel138;
    private widget.Label jLabel139;
    private widget.Label jLabel14;
    private widget.Label jLabel140;
    private widget.Label jLabel141;
    private widget.Label jLabel142;
    private widget.Label jLabel143;
    private widget.Label jLabel144;
    private widget.Label jLabel145;
    private widget.Label jLabel146;
    private widget.Label jLabel147;
    private widget.Label jLabel148;
    private widget.Label jLabel149;
    private widget.Label jLabel150;
    private widget.Label jLabel151;
    private widget.Label jLabel152;
    private widget.Label jLabel153;
    private widget.Label jLabel154;
    private widget.Label jLabel155;
    private widget.Label jLabel156;
    private widget.Label jLabel157;
    private widget.Label jLabel158;
    private widget.Label jLabel159;
    private widget.Label jLabel16;
    private widget.Label jLabel160;
    private widget.Label jLabel161;
    private widget.Label jLabel162;
    private widget.Label jLabel163;
    private widget.Label jLabel164;
    private widget.Label jLabel165;
    private widget.Label jLabel166;
    private widget.Label jLabel167;
    private widget.Label jLabel168;
    private widget.Label jLabel169;
    private widget.Label jLabel17;
    private widget.Label jLabel170;
    private widget.Label jLabel171;
    private widget.Label jLabel172;
    private widget.Label jLabel173;
    private widget.Label jLabel174;
    private widget.Label jLabel175;
    private widget.Label jLabel176;
    private widget.Label jLabel177;
    private widget.Label jLabel178;
    private widget.Label jLabel179;
    private widget.Label jLabel180;
    private widget.Label jLabel181;
    private widget.Label jLabel182;
    private widget.Label jLabel183;
    private widget.Label jLabel184;
    private widget.Label jLabel185;
    private widget.Label jLabel186;
    private widget.Label jLabel187;
    private widget.Label jLabel188;
    private widget.Label jLabel189;
    private widget.Label jLabel19;
    private widget.Label jLabel190;
    private widget.Label jLabel206;
    private widget.Label jLabel207;
    private widget.Label jLabel208;
    private widget.Label jLabel209;
    private widget.Label jLabel21;
    private widget.Label jLabel210;
    private widget.Label jLabel211;
    private widget.Label jLabel212;
    private widget.Label jLabel213;
    private widget.Label jLabel214;
    private widget.Label jLabel215;
    private widget.Label jLabel216;
    private widget.Label jLabel217;
    private widget.Label jLabel218;
    private widget.Label jLabel219;
    private widget.Label jLabel22;
    private widget.Label jLabel220;
    private widget.Label jLabel221;
    private widget.Label jLabel222;
    private widget.Label jLabel223;
    private widget.Label jLabel224;
    private widget.Label jLabel225;
    private widget.Label jLabel226;
    private widget.Label jLabel227;
    private widget.Label jLabel228;
    private widget.Label jLabel229;
    private widget.Label jLabel23;
    private widget.Label jLabel230;
    private widget.Label jLabel231;
    private widget.Label jLabel232;
    private widget.Label jLabel233;
    private widget.Label jLabel234;
    private widget.Label jLabel235;
    private widget.Label jLabel236;
    private widget.Label jLabel24;
    private widget.Label jLabel25;
    private widget.Label jLabel26;
    private widget.Label jLabel27;
    private widget.Label jLabel28;
    private widget.Label jLabel29;
    private widget.Label jLabel30;
    private widget.Label jLabel31;
    private widget.Label jLabel32;
    private widget.Label jLabel33;
    private widget.Label jLabel34;
    private widget.Label jLabel35;
    private widget.Label jLabel36;
    private widget.Label jLabel37;
    private widget.Label jLabel38;
    private widget.Label jLabel39;
    private widget.Label jLabel40;
    private widget.Label jLabel41;
    private widget.Label jLabel42;
    private widget.Label jLabel43;
    private widget.Label jLabel45;
    private widget.Label jLabel46;
    private widget.Label jLabel47;
    private widget.Label jLabel48;
    private widget.Label jLabel49;
    private widget.Label jLabel50;
    private widget.Label jLabel51;
    private widget.Label jLabel52;
    private widget.Label jLabel53;
    private widget.Label jLabel54;
    private widget.Label jLabel55;
    private widget.Label jLabel56;
    private widget.Label jLabel57;
    private widget.Label jLabel58;
    private widget.Label jLabel59;
    private widget.Label jLabel6;
    private widget.Label jLabel60;
    private widget.Label jLabel61;
    private widget.Label jLabel62;
    private widget.Label jLabel63;
    private widget.Label jLabel64;
    private widget.Label jLabel65;
    private widget.Label jLabel66;
    private widget.Label jLabel67;
    private widget.Label jLabel68;
    private widget.Label jLabel69;
    private widget.Label jLabel7;
    private widget.Label jLabel70;
    private widget.Label jLabel71;
    private widget.Label jLabel72;
    private widget.Label jLabel73;
    private widget.Label jLabel74;
    private widget.Label jLabel75;
    private widget.Label jLabel76;
    private widget.Label jLabel77;
    private widget.Label jLabel78;
    private widget.Label jLabel79;
    private widget.Label jLabel80;
    private widget.Label jLabel81;
    private widget.Label jLabel82;
    private widget.Label jLabel83;
    private widget.Label jLabel84;
    private widget.Label jLabel85;
    private widget.Label jLabel86;
    private widget.Label jLabel87;
    private widget.Label jLabel88;
    private widget.Label jLabel89;
    private widget.Label jLabel90;
    private widget.Label jLabel91;
    private widget.Label jLabel92;
    private widget.Label jLabel93;
    private widget.Label jLabel94;
    private widget.Label jLabel95;
    private widget.Label jLabel96;
    private widget.Label jLabel97;
    private widget.Label jLabel98;
    private widget.Label jLabel99;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator15;
    private javax.swing.JSeparator jSeparator19;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator24;
    private javax.swing.JSeparator jSeparator25;
    private javax.swing.JSeparator jSeparator26;
    private javax.swing.JSeparator jSeparator27;
    private javax.swing.JSeparator jSeparator28;
    private javax.swing.JSeparator jSeparator29;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator30;
    private javax.swing.JSeparator jSeparator31;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private widget.TextArea kesimpulan;
    private widget.TextBox lab_others;
    private widget.Label label11;
    private widget.Label label14;
    private widget.Label label15;
    private widget.TextBox leukosit;
    private widget.TextBox malaria;
    private widget.TextBox mantoux_test;
    private widget.TextBox oth_abnormal;
    private widget.TextBox ova;
    private widget.TextBox page3_comment;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.TextBox parasites1;
    private widget.TextBox pnemunosicosis;
    private widget.TextBox pnemunosicosis2;
    private widget.TextBox protein;
    private widget.TextBox random_glucose;
    private widget.TextArea rekomendasi2;
    private widget.TextArea saran;
    private widget.ScrollPane scrollInput;
    private widget.ScrollPane scrollPane10;
    private widget.ScrollPane scrollPane11;
    private widget.ScrollPane scrollPane12;
    private widget.ScrollPane scrollPane13;
    private widget.ScrollPane scrollPane15;
    private widget.ScrollPane scrollPane16;
    private widget.ScrollPane scrollPane17;
    private widget.ScrollPane scrollPane18;
    private widget.ScrollPane scrollPane5;
    private widget.ScrollPane scrollPane6;
    private widget.ScrollPane scrollPane7;
    private widget.TextBox sgot;
    private widget.TextBox sgpt;
    private widget.TextArea specify;
    private widget.TextBox spirometri_fev_1_1;
    private widget.TextBox spirometri_fev_1_2;
    private widget.TextBox spirometri_fev_1_3;
    private widget.TextBox spirometri_fev_1_4;
    private widget.TextBox spirometri_fev_1_fvc_1;
    private widget.TextBox spirometri_fev_1_fvc_2;
    private widget.TextBox spirometri_fev_1_fvc_3;
    private widget.TextBox spirometri_fev_1_fvc_4;
    private widget.TextBox spirometri_fvc_1;
    private widget.TextBox spirometri_fvc_2;
    private widget.TextBox spirometri_fvc_3;
    private widget.TextBox spirometri_fvc_4;
    private widget.TextBox spirometri_vc_1;
    private widget.TextBox spirometri_vc_2;
    private widget.TextBox spirometri_vc_3;
    private widget.TextBox spirometri_vc_4;
    private widget.TextBox surname;
    private widget.TextBox tb1;
    private widget.TextBox tb2;
    private widget.Table tbObat;
    private widget.TextBox total_cholestrol;
    private widget.TextBox tpha;
    private widget.TextBox type_of_hearing;
    private widget.TextBox urea;
    private widget.ComboBox vertebra_forward_flexion_0_80;
    private widget.ComboBox vertebra_heel_walking;
    private widget.ComboBox vertebra_hyperextensi_0_25;
    private widget.ComboBox vertebra_kyphosis;
    private widget.ComboBox vertebra_lateral_flexion_0_20;
    private widget.ComboBox vertebra_lordosis;
    private widget.ComboBox vertebra_scoliosis;
    private widget.ComboBox vertebra_squats_x3;
    private widget.ComboBox vertebra_toe_walking;
    private widget.TextBox visual_fields_left;
    private widget.TextBox visual_fields_right;
    private widget.TextBox wbc;
    // End of variables declaration//GEN-END:variables

    private void aturInputKesimpulanMcu() {
        ConcRadiologi.setEditable(true);
        ConcSpirometry.setEditable(true);
        ConcAudiometry.setEditable(true);
        ConcEcg.setEditable(false);
        cbConcEcg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                sinkronConcEcg();
            }
        });
        sinkronConcEcg();
    }

    private void aturTabTraversal(Container container) {
        for (Component component : container.getComponents()) {
            if(component instanceof JComponent){
                JComponent komponen = (JComponent) component;
                komponen.setFocusTraversalKeysEnabled(false);
                komponen.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0), "fokusBerikutnya");
                komponen.getActionMap().put("fokusBerikutnya", new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        ((Component) evt.getSource()).transferFocus();
                    }
                });
                komponen.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, KeyEvent.SHIFT_DOWN_MASK), "fokusSebelumnya");
                komponen.getActionMap().put("fokusSebelumnya", new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        ((Component) evt.getSource()).transferFocusBackward();
                    }
                });
            }
            if(component instanceof Container){
                aturTabTraversal((Container) component);
            }
        }
    }

    private void aturKeypressRiwayatPenyakit() {
        pasangKeypress(PosisiKerja,FamilyHistoryFather,
            RWP1,RWP2,RWP3,RWP4,RWP5,RWP6,RWP7,RWP8,RWP9,RWP10,
            RWP11,RWP12,RWP13,RWP14,RWP15,RWP16,RWP17,RWP18,RWP19,RWP20,
            RWP21,RWP22,RWP23,RWP24,RWP25,RWP26,RWP27,RWP28,RWP29,RWP30
        );

        pasangKeypress(RWP30,CigarettesPerday,
            FamilyHistoryFather,FamilyHistoryMother,FamilyHistorySiblings,FamilyHistoryOther
        );

        pasangKeypress(FamilyHistoryOther,spirometri_vc_1,
            CigarettesPerday,AlcoholGrWeek,PrescribedMedication,PrescribedMedication2,AnyAllergies
        );
    }

    private void aturKeypressPemeriksaanKhusus() {
        pasangKeypress(cbConcEcg,audiometri_left_ear_500,
            ecg_abnormal,spirometri_vc_1,spirometri_vc_2,spirometri_vc_3,spirometri_vc_4,
            spirometri_fvc_1,spirometri_fvc_2,spirometri_fvc_3,spirometri_fvc_4,
            spirometri_fev_1_1,spirometri_fev_1_2,spirometri_fev_1_3,spirometri_fev_1_4,
            spirometri_fev_1_fvc_1,spirometri_fev_1_fvc_2,spirometri_fev_1_fvc_3,spirometri_fev_1_fvc_4
        );

        pasangKeypress(spirometri_fev_1_fvc_4,TD,
            type_of_hearing,audiometri_left_ear_500,audiometri_left_ear_1000,audiometri_left_ear_1500,audiometri_left_ear_2000,
            audiometri_left_ear_3000,audiometri_left_ear_4000,audiometri_left_ear_5000,audiometri_left_ear_6000,
            audiometri_left_ear_501,audiometri_left_ear_1001,audiometri_left_ear_1501,audiometri_left_ear_2001,
            audiometri_left_ear_3001,audiometri_left_ear_4001,audiometri_left_ear_5001,audiometri_left_ear_6001,
            audiometri_left_ear_502,audiometri_left_ear_1002,audiometri_left_ear_1502,audiometri_left_ear_2002,
            audiometri_left_ear_3002,audiometri_left_ear_4002,audiometri_left_ear_5002,audiometri_left_ear_6002,
            audiometri_left_ear_503,audiometri_left_ear_1003,audiometri_left_ear_1503,audiometri_left_ear_2003,
            audiometri_left_ear_3003,audiometri_left_ear_4003,audiometri_left_ear_5003,audiometri_left_ear_6003
        );

        pasangKeypress(audiometri_left_ear_6003,eye_unaided_distant_r,
            TD,Nadi,RR,TB,BB,IMT,KlasifikasiIMT1
        );

        pasangKeypress(KlasifikasiIMT1,imunisasi_bcg,
            eye_unaided_distant_l,eye_unaided_distant_r,eye_glasses_near_r,eye_glasses_near_l,
            eye_glasses_distant_r,eye_glasses_distant_l,eye_night_vision_1,eye_night_vision_2,
            eye_unaided_near_r,eye_unaided_near_l,eye_brake_test_1,eye_brake_test_2,
            eye_color_blindless,visual_fields_left,visual_fields_right,fundi
        );

        pasangKeypress(fundi,vertebra_scoliosis,
            imunisasi_bcg,imunisasi_dpt,imunisasi_polio,imunisasi_morbili,imunisasi_thyphoid,
            imunisasi_hep_a,imunisasi_hep_b,imunisasi_tetanus,imunisasi_others
        );

        pasangKeypress(imunisasi_others,exam_ent_comments,
            vertebra_scoliosis,vertebra_lordosis,vertebra_hyperextensi_0_25,vertebra_heel_walking,vertebra_squats_x3,
            vertebra_kyphosis,vertebra_forward_flexion_0_80,vertebra_lateral_flexion_0_20,vertebra_toe_walking
        );

        pasangKeypress(vertebra_toe_walking,conclusion_requires_spectacles,
            exam_ent_comments,exam_cardio_vascular_system_comments,exam_respiratory_system_comments,
            exam_abdomen_comments,exam_genito_urinary_system_comments,exam_central_peripheral_nervous_system_comments,
            exam_skin_comments,exam_lymph_nodes_comments,exam_dental_comments
        );
    }

    private void aturKeypressLaboratoriumRontgen() {
        pasangKeypress(AnyAllergies,spirometri_vc_1,
            hb,wbc,esr,bl_group,gamaa_gt,sgot,sgpt,urea,creatinin,glucose,
            random_glucose,total_cholestrol,protein,blood,bilirubin,malaria,tpha,mantoux_test,lab_others,
            ova,culture,cysta,parasites1,pnemunosicosis,pnemunosicosis2,
            ILO_clasification,ILO_clasification2,tb1,tb2,oth_abnormal,page3_comment
        );
    }

    private void pasangKeypress(Component kiriAwal, Component kananAkhir, Component... daftarKomponen) {
        for (int index = 0; index < daftarKomponen.length; index++) {
            final Component kiri = index == 0 ? kiriAwal : daftarKomponen[index - 1];
            final Component kanan = index == daftarKomponen.length - 1 ? kananAkhir : daftarKomponen[index + 1];
            daftarKomponen[index].addKeyListener(new java.awt.event.KeyAdapter() {
                @Override
                public void keyPressed(java.awt.event.KeyEvent evt) {
                    pindahKomponen(evt,kiri,kanan);
                }
            });
        }
    }

    private void pindahKomponen(java.awt.event.KeyEvent evt, Component kiri, Component kanan) {
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            kanan.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            kiri.requestFocus();
        }
    }

    private static String[] kolomTabelPenilaianMcu() {
        String[] tambahanAwal = new String[]{"year","no_rawat","no_rkm_medis","nama_pasien","surname","perusahaan_pasien","nip","tanggal","kd_dokter","nm_dokter","kd_petugas","nm_petugas"};
        java.util.List<String> hasil = new java.util.ArrayList<>();
        for (String kolom : tambahanAwal) {
            hasil.add(kolom);
        }
        for (String kolom : PENILAIAN_MCU_COLUMNS) {
            if(!kolomTabelAwal(kolom)&&!kolomTabelTidakDipakai(kolom)){
                hasil.add(kolom);
            }
        }
        return hasil.toArray(new String[0]);
    }

    private static boolean kolomTabelAwal(String kolom) {
        return "year".equals(kolom)||"no_rawat".equals(kolom)||"no_rkm_medis".equals(kolom)||"nama_pasien".equals(kolom)||"surname".equals(kolom)
                ||"tanggal".equals(kolom)||"kd_dokter".equals(kolom)||"kd_petugas".equals(kolom);
    }

    private static boolean kolomTabelTidakDipakai(String kolom) {
        return false;
    }

    private static String[] headerTabelPenilaianMcu() {
        String[] hasil = new String[TABEL_PENILAIAN_MCU_COLUMNS.length];
        for (int index = 0; index < TABEL_PENILAIAN_MCU_COLUMNS.length; index++) {
            hasil[index] = judulKolomTabel(TABEL_PENILAIAN_MCU_COLUMNS[index]);
        }
        return hasil;
    }

    private static String judulKolomTabel(String kolom) {
        Object[] style = styleKolomTabel(kolom);
        if(style != null){
            return style[1].toString();
        }

        if("year".equals(kolom)){
            return "Tahun";
        }else if("no_rawat".equals(kolom)){
            return "No.Rawat";
        }else if("no_rkm_medis".equals(kolom)){
            return "No.RM";
        }else if("nama_pasien".equals(kolom)){
            return "Nama Pasien";
        }else if("surname".equals(kolom)){
            return "Surname";
        }else if("perusahaan_pasien".equals(kolom)){
            return "Perusahaan";
        }else if("nip".equals(kolom)){
            return "NIP";
        }else if("tanggal".equals(kolom)){
            return "Tanggal";
        }else if("kd_dokter".equals(kolom)){
            return "Kode Dokter";
        }else if("nm_dokter".equals(kolom)){
            return "Nama Dokter Penanggung Jawab";
        }else if("kd_petugas".equals(kolom)){
            return "Kode Petugas";
        }else if("nm_petugas".equals(kolom)){
            return "Nama Petugas";
        }else if("note1".equals(kolom)){
            return "Saran";
        }else if("mcu_group".equals(kolom)){
            return "Grup MCU";
        }else if("dass_21".equals(kolom)){
            return "DASS 21";
        }else if("phy_exam".equals(kolom)){
            return "Pemeriksaan Fisik";
        }else if("conc_lab".equals(kolom)){
            return "Laboratorium";
        }else if("conc_radiologi".equals(kolom)){
            return "Kesimpulan Radiologi";
        }else if("conc_ecg".equals(kolom)){
            return "Kesimpulan ECG";
        }else if("conc_spirometry".equals(kolom)){
            return "Kesimpulan Spirometri";
        }else if("conc_audiometry".equals(kolom)){
            return "Kesimpulan Audiometri";
        }else if("kesimpulan1".equals(kolom)){
            return "Kesimpulan";
        }else if("tmp_lahir".equals(kolom)){
            return "Tmp.Lahir";
        }else if("tgl_lahir".equals(kolom)){
            return "Tgl.Lahir";
        }else if("jk".equals(kolom)){
            return "J.K.";
        }else if("no_tlp".equals(kolom)){
            return "No.Telp";
        }else if("suku_bangsa".equals(kolom)){
            return "Suku/Bangsa";
        }else if("stts_nikah".equals(kolom)){
            return "Status Nikah";
        }else if("doe".equals(kolom)){
            return "Date/Year of Employment";
        }else if("yoe".equals(kolom)){
            return "Year of Employment";
        }else if("td".equals(kolom)){
            return "T.D.";
        }else if("rr".equals(kolom)){
            return "R.R.";
        }else if("tb".equals(kolom)){
            return "T.B.";
        }else if("bb".equals(kolom)){
            return "B.B.";
        }else if("bmi".equals(kolom)){
            return "B.M.I.";
        }else if("laborat".equals(kolom)){
            return "Pemeriksaan Laboratorium";
        }else if("radiologi".equals(kolom)){
            return "Rontgen Thorax";
        }else if("ekg".equals(kolom)){
            return "EKG";
        }else if("blood_group".equals(kolom)){
            return "Gol. Darah";
        }else if("unfit_comment_1".equals(kolom)){
            return "Saran";
        }

        String[] kata = kolom.replace('_',' ').split(" ");
        StringBuilder judul = new StringBuilder();
        for (String bagian : kata) {
            if(bagian.length() > 0){
                if(judul.length() > 0){
                    judul.append(" ");
                }
                judul.append(bagian.substring(0,1).toUpperCase()).append(bagian.substring(1));
            }
        }
        return judul.toString();
    }

    private static Object[] styleKolomTabel(String kolom) {
        for (Object[] style : TABEL_PENILAIAN_MCU_STYLE) {
            if(style[0].equals(kolom)){
                return style;
            }
        }
        return null;
    }

    private static boolean aturLebarKolomTabel(TableColumn column, String kolom) {
        Object[] style = styleKolomTabel(kolom);
        if(style == null){
            return false;
        }

        int lebar = ((Number) style[2]).intValue();
        column.setMinWidth(15);
        column.setMaxWidth(Integer.MAX_VALUE);
        column.setPreferredWidth(lebar > 0 ? lebar : 110);
        return true;
    }

    private String kolomSelectPenilaianMcu(String kolom) {
        if("nama_pasien".equals(kolom)){
            return "ifnull(penilaian_mcu.nama_pasien,pasien.nm_pasien) as nama_pasien";
        }else if("surname".equals(kolom)){
            return "ifnull(penilaian_mcu.surname,'') as surname";
        }else if("nm_pasien".equals(kolom)){
            return "ifnull(penilaian_mcu.nama_pasien,pasien.nm_pasien) as nm_pasien";
        }else if("no_rkm_medis".equals(kolom)){
            return "ifnull(penilaian_mcu.no_rkm_medis,pasien.no_rkm_medis) as no_rkm_medis";
        }else if("tmp_lahir".equals(kolom)){
            return "ifnull(penilaian_mcu.tmp_lahir,pasien.tmp_lahir) as tmp_lahir";
        }else if("tgl_lahir".equals(kolom)){
            return "ifnull(penilaian_mcu.tgl_lahir,pasien.tgl_lahir) as tgl_lahir";
        }else if("jk".equals(kolom)){
            return "ifnull(penilaian_mcu.jk,pasien.jk) as jk";
        }else if("no_tlp".equals(kolom)){
            return "ifnull(penilaian_mcu.no_tlp,pasien.no_tlp) as no_tlp";
        }else if("suku_bangsa".equals(kolom)){
            return "ifnull((select sb.nama_suku_bangsa from suku_bangsa sb where cast(sb.id as char)=penilaian_mcu.suku_bangsa),ifnull(nullif(penilaian_mcu.suku_bangsa,''),ifnull(suku_bangsa_pasien.nama_suku_bangsa,pasien.suku_bangsa))) as suku_bangsa";
        }else if("stts_nikah".equals(kolom)){
            return "ifnull(penilaian_mcu.stts_nikah,pasien.stts_nikah) as stts_nikah";
        }else if("year".equals(kolom)){
            return "penilaian_mcu.`year` as year";
        }else if("note1".equals(kolom)){
            return "ifnull(nullif(penilaian_mcu.note1,''),penilaian_mcu.unfit_comment_1) as note1";
        }else if("unfit_comment_1".equals(kolom)){
            return "ifnull(nullif(penilaian_mcu.note1,''),penilaian_mcu.unfit_comment_1) as unfit_comment_1";
        }else if("conc_lab".equals(kolom)){
            return "ifnull(nullif(penilaian_mcu.laborat,''),penilaian_mcu.conc_lab) as conc_lab";
        }else if("nm_petugas".equals(kolom)){
            return "petugas.nama as nm_petugas";
        }
        return "penilaian_mcu."+kolom;
    }

    private String namaKolomSqlPenilaianMcu(String kolom) {
        return "year".equals(kolom) ? "`year`" : kolom;
    }

    private String selectPenilaianMcu() {
        StringBuilder sql = new StringBuilder("select ");
        for (int index = 0; index < PENILAIAN_MCU_COLUMNS.length; index++) {
            if(index > 0){
                sql.append(",");
            }
            sql.append(kolomSelectPenilaianMcu(PENILAIAN_MCU_COLUMNS[index]));
        }
        sql.append(",dokter.nm_dokter,petugas.nama as nm_petugas,").append(selectNamaPenanggungJawab()).append(" as perusahaan_pasien,pasien.nip,");
        sql.append(kolomSelectPenilaianMcu("nm_pasien")).append(" ");
        sql.append("from penilaian_mcu ");
        sql.append("inner join reg_periksa on penilaian_mcu.no_rawat=reg_periksa.no_rawat ");
        sql.append("inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis ");
        sql.append("left join suku_bangsa suku_bangsa_pasien on suku_bangsa_pasien.id=pasien.suku_bangsa ");
        sql.append("left join perusahaan_pasien on perusahaan_pasien.kode_perusahaan=pasien.perusahaan_pasien ");
        sql.append("left join dokter on penilaian_mcu.kd_dokter=dokter.kd_dokter ");
        sql.append("left join petugas on penilaian_mcu.kd_petugas=petugas.nip ");
        return sql.toString();
    }

    private String kolomInsertPenilaianMcu() {
        StringBuilder kolom = new StringBuilder();
        for (int index = 0; index < PENILAIAN_MCU_COLUMNS.length; index++) {
            if(index > 0){
                kolom.append(",");
            }
            kolom.append(namaKolomSqlPenilaianMcu(PENILAIAN_MCU_COLUMNS[index]));
        }
        return kolom.toString();
    }

    private String placeholderPenilaianMcu() {
        StringBuilder placeholder = new StringBuilder();
        for (int index = 0; index < PENILAIAN_MCU_COLUMNS.length; index++) {
            if(index > 0){
                placeholder.append(",");
            }
            placeholder.append("?");
        }
        return placeholder.toString();
    }

    private String updatePenilaianMcu() {
        StringBuilder update = new StringBuilder();
        for (int index = 1; index < PENILAIAN_MCU_COLUMNS.length; index++) {
            if(index > 1){
                update.append(",");
            }
            update.append(namaKolomSqlPenilaianMcu(PENILAIAN_MCU_COLUMNS[index])).append("=?");
        }
        return update.toString();
    }

    private String getTanggalAsuhan() {
        return Valid.SetTgl(TglAsuhan.getSelectedItem()+"");
    }

    private String getTanggalJamAsuhan() {
        String nilai = TglAsuhan.getSelectedItem() == null ? "" : TglAsuhan.getSelectedItem().toString();
        return Valid.SetTgl(nilai)+" "+(nilai.length() >= 19 ? nilai.substring(11,19) : "00:00:00");
    }

    private void setTanggalAsuhanMinimalRegistrasi() {
        String tanggalRegistrasi = TanggalRegistrasi.getText().trim();
        if(tanggalRegistrasi.equals("")){
            return;
        }
        try {
            java.text.SimpleDateFormat formatTanggal = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date registrasi = formatTanggal.parse(tanggalRegistrasi);
            Date asuhan = formatTanggal.parse(getTanggalJamAsuhan());
            if(asuhan.before(registrasi)){
                TglAsuhan.setDate(registrasi);
            }
        } catch (Exception e) {
            System.out.println("Notif : "+e);
        }
    }

    private String getTahunDariTanggal(String tanggal) {
        String nilai = tanggal == null ? "" : tanggal.trim();
        return nilai.length() >= 4 ? nilai.substring(0,4) : "";
    }

    private String getTahunAsuhan() {
        return getTahunDariTanggal(getTanggalAsuhan());
    }

    private String getYearMcu() {
        String nilaiYear = Year.getText().trim();
        return nilaiYear.equals("") ? getTahunAsuhan() : nilaiYear;
    }

    private String nilai(javax.swing.text.JTextComponent teks) {
        return teks.getText();
    }

    private String nilaiCombo(javax.swing.JComboBox combo) {
        Object nilai = combo.getSelectedItem();
        return nilai == null ? "" : nilai.toString();
    }

    private String nilaiComboLower(javax.swing.JComboBox combo) {
        return nilaiCombo(combo).toLowerCase();
    }

    private boolean comboTerpilih(javax.swing.JComboBox combo, String nilai) {
        return nilaiCombo(combo).equalsIgnoreCase(nilai);
    }

    private String nilaiComboYesNo(javax.swing.JComboBox combo) {
        return comboTerpilih(combo,"Yes") ? "Yes" : "No";
    }

    private String nilaiBloodGroupMcu() {
        String nilai = normalisasiBloodGroup(bl_group.getText());
        return nilai.equals("") ? nilaiCombo(blood_group) : nilai;
    }

    private String normalisasiBloodGroup(String nilai) {
        String hasil = nilai == null ? "" : nilai.trim().toUpperCase();
        return hasil.equals("A+")||hasil.equals("A-")||
               hasil.equals("B+")||hasil.equals("B-")||
               hasil.equals("AB+")||hasil.equals("AB-")||
               hasil.equals("O+")||hasil.equals("O-") ? hasil : "";
    }

    private String nilaiPertamaTerisi(javax.swing.text.JTextComponent... daftarTeks) {
        for (javax.swing.text.JTextComponent teks : daftarTeks) {
            if(teks != null && !teks.getText().trim().equals("")){
                return teks.getText();
            }
        }
        return "";
    }

    private String nilaiPertamaTerisiValue(String... daftarNilai) {
        for (String nilai : daftarNilai) {
            if(nilai != null && !nilai.trim().equals("")){
                return nilai;
            }
        }
        return "";
    }

    private String nilaiLaboratMcu() {
        return nilaiPertamaTerisi(PemeriksaanLaboratorium,ConcLab);
    }

    private String nilaiKesimpulanMcu() {
        return nilaiPertamaTerisi(kesimpulan,Kesimpulan1);
    }

    private String nilaiSaranMcu() {
        return nilai(saran);
    }

    private void sinkronFieldMcu() {
        String nilaiLaborat = nilaiLaboratMcu();
        setText(PemeriksaanLaboratorium,nilaiLaborat);
        setText(ConcLab,nilaiLaborat);

        String nilaiKesimpulan = nilaiKesimpulanMcu();
        setText(kesimpulan,nilaiKesimpulan);
        setText(Kesimpulan1,nilaiKesimpulan);

        String nilaiSaran = nilaiSaranMcu();
        setText(rekomendasi2,nilaiSaran);
        setText(Note1,nilaiSaran);
    }

    private void sinkronBloodGroup() {
        bl_group.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {
                setBloodGroupDariBlGroup();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                setBloodGroupDariBlGroup();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                setBloodGroupDariBlGroup();
            }
        });
        setBloodGroupDariBlGroup();
    }

    private void setBloodGroupDariBlGroup() {
        String nilai = normalisasiBloodGroup(bl_group.getText());
        if(!nilai.equals("")){
            setCombo(blood_group,nilai);
        }
    }

    private String nilaiTabel(int baris, int kolom) {
        Object nilai = tbObat.getValueAt(baris, kolom);
        return nilai == null ? "" : nilai.toString();
    }

    private String escapeHtml(String nilai) {
        return nilai == null ? "" : nilai.replace("&","&amp;").replace("<","&lt;").replace(">","&gt;").replace("\"","&quot;");
    }

    private String escapeCsv(String nilai) {
        return nilai == null ? "" : nilai.replace("\"","\"\"");
    }

    private StringBuilder headerExportHtml() {
        StringBuilder html = new StringBuilder("<tr class='isi'>");
        for (String header : TABEL_PENILAIAN_MCU_HEADERS) {
            html.append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>")
                    .append(escapeHtml(header)).append("</b></td>");
        }
        return html.append("</tr>");
    }

    private StringBuilder barisExportHtml() {
        StringBuilder html = new StringBuilder();
        for (int baris = 0; baris < tabMode.getRowCount(); baris++) {
            html.append("<tr class='isi'>");
            for (int kolom = 0; kolom < tabMode.getColumnCount(); kolom++) {
                html.append("<td valign='top'>").append(escapeHtml(nilaiTabel(baris, kolom))).append("</td>");
            }
            html.append("</tr>");
        }
        return html;
    }

    private String htmlLaporanMcu() {
        return "<html><head>"+
                "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                "</head><body>"+
                "<table width='10000px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                    "<tr class='isi2'>"+
                        "<td valign='top' align='center'>"+
                            "<font size='4' face='Tahoma'>"+escapeHtml(akses.getnamars())+"</font><br>"+
                            escapeHtml(akses.getalamatrs())+", "+escapeHtml(akses.getkabupatenrs())+", "+escapeHtml(akses.getpropinsirs())+"<br>"+
                            escapeHtml(akses.getkontakrs())+", E-mail : "+escapeHtml(akses.getemailrs())+"<br><br>"+
                            "<font size='2' face='Tahoma'>DATA PEMERIKSAAN MCU<br><br></font>"+
                        "</td>"+
                   "</tr>"+
                "</table>"+
                "<table width='10000px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
                    headerExportHtml().toString()+barisExportHtml().toString()+
                "</table>"+
                "</body></html>";
    }

    private void tulisDanBukaFile(String namaFile, String isi) throws Exception {
        File file = new File(namaFile);
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(isi);
        writer.close();
        Desktop.getDesktop().browse(file.toURI());
    }

    private boolean cetakPilihanLaporan(String pilihan) throws Exception {
        if(pilihan == null){
            return true;
        }else if("Laporan 1 (HTML)".equals(pilihan)){
            LoadHTML.setText(htmlLaporanMcu());
            tulisDanBukaFile("DataMCU.html",LoadHTML.getText());
            return true;
        }else if("Laporan 2 (WPS)".equals(pilihan)){
            LoadHTML.setText(htmlLaporanMcu());
            tulisDanBukaFile("DataMCU.wps",LoadHTML.getText());
            return true;
        }else if("Laporan 3 (CSV)".equals(pilihan)){
            tulisDanBukaFile("DataMCU.csv",exportCsv().toString());
            return true;
        }else if("Laporan 4 (Jasper)".equals(pilihan)){
            cetakJasperPenilaianMcuTerpilih();
            return true;
        }
        return false;
    }

    private StringBuilder exportCsv() {
        StringBuilder csv = new StringBuilder();
        for (int kolom = 0; kolom < TABEL_PENILAIAN_MCU_HEADERS.length; kolom++) {
            if(kolom > 0){
                csv.append(";");
            }
            csv.append("\"").append(escapeCsv(TABEL_PENILAIAN_MCU_HEADERS[kolom])).append("\"");
        }
        csv.append("\n");
        for (int baris = 0; baris < tabMode.getRowCount(); baris++) {
            for (int kolom = 0; kolom < tabMode.getColumnCount(); kolom++) {
                if(kolom > 0){
                    csv.append(";");
                }
                csv.append("\"").append(escapeCsv(nilaiTabel(baris, kolom))).append("\"");
            }
            csv.append("\n");
        }
        return csv;
    }

    private String nilaiFitWithRestrictions() {
        return nilaiCombo(fit_with_restrictions);
    }

    private String nilaiSpecify() {
        return comboTerpilih(fit_with_restrictions,"Specify") ? nilai(specify) : "";
    }

    private String nilaiNormalAbnormal(String nilai) {
        String nilaiBersih = nilai == null ? "" : nilai.trim();
        if(nilaiBersih.equalsIgnoreCase("normal")){
            return "Normal";
        }else if(nilaiBersih.equalsIgnoreCase("abnormal")){
            return "Abnormal";
        }
        return "";
    }

    private String nilaiEyeColorBlindless(String nilai) {
        String nilaiBersih = nilai == null ? "" : nilai.trim();
        if(nilaiBersih.equalsIgnoreCase("normal")){
            return "Normal";
        }else if(nilaiBersih.equalsIgnoreCase("color blind")){
            return "Color Blind";
        }else if(nilaiBersih.equalsIgnoreCase("partial color blind")
                ||nilaiBersih.equalsIgnoreCase("partial colod blind")
                ||nilaiBersih.equalsIgnoreCase("red green absen")){
            return "Partial Color Blind";
        }
        return "Normal";
    }

    private String nilaiConcEcgTabel() {
        String nilai = nilaiNormalAbnormal(getTabelValue("conc_ecg"));
        return nilai.equals("") ? nilaiNormalAbnormal(getTabelValue("ekg")) : nilai;
    }

    private void setConcEcgDariTabel() {
        String nilai = nilaiConcEcgTabel();
        if(nilai.equals("")){
            if(cbConcEcg.getItemCount() > 0){
                cbConcEcg.setSelectedIndex(0);
            }
        }else{
            setCombo(cbConcEcg,nilai);
        }
        sinkronConcEcg();
    }

    private void sinkronConcEcg() {
        boolean abnormal = comboTerpilih(cbConcEcg,"Abnormal");
        setText(ConcEcg,nilaiCombo(cbConcEcg));
        ecg_abnormal.setEditable(abnormal);
        ecg_abnormal.setEnabled(abnormal);
    }

    private String nilaiEcgAbnormal() {
        return comboTerpilih(cbConcEcg,"Abnormal") ? nilai(ecg_abnormal) : "";
    }

    private String nilaiCek(javax.swing.AbstractButton cek) {
        return cek.isSelected() ? "Yes" : "No";
    }

    private String tampilJenisKelamin(String nilai) {
        String nilaiBersih = nilai == null ? "" : nilai.trim();
        if(nilaiBersih.equalsIgnoreCase("L")||nilaiBersih.equalsIgnoreCase("Laki-laki")||nilaiBersih.equalsIgnoreCase("Laki-Laki")||nilaiBersih.equalsIgnoreCase("Laki laki")){
            return "Laki laki";
        }else if(nilaiBersih.equalsIgnoreCase("P")||nilaiBersih.equalsIgnoreCase("Perempuan")){
            return "Perempuan";
        }
        return nilaiBersih;
    }

    private String getKodeJenisKelamin() {
        return tampilJenisKelamin(Jk.getText());
    }

    private void setText(javax.swing.text.JTextComponent teks, String nilai) {
        teks.setText(nilai == null ? "" : nilai);
    }

    private void setCombo(javax.swing.JComboBox combo, String nilai) {
        if(nilai == null || nilai.equals("")){
            return;
        }
        for (int index = 0; index < combo.getItemCount(); index++) {
            Object item = combo.getItemAt(index);
            if(item != null && item.toString().equalsIgnoreCase(nilai)){
                combo.setSelectedIndex(index);
                return;
            }
        }
        combo.setSelectedItem(nilai);
    }

    private void setFitWithRestrictions(String nilai, String keterangan) {
        String nilaiBersih = nilai == null ? "" : nilai.trim();
        setText(specify,"");
        if(nilaiBersih.equals("")){
            if(fit_with_restrictions.getItemCount() > 0){
                fit_with_restrictions.setSelectedIndex(0);
            }
            return;
        }
        for (int index = 0; index < fit_with_restrictions.getItemCount(); index++) {
            Object item = fit_with_restrictions.getItemAt(index);
            if(item != null && item.toString().equalsIgnoreCase(nilaiBersih)){
                fit_with_restrictions.setSelectedIndex(index);
                if("Specify".equalsIgnoreCase(nilaiBersih)){
                    setText(specify,keterangan);
                }
                return;
            }
        }
        // Kompatibilitas data lama: teks bebas pernah disimpan langsung di fit_with_restrictions.
        setCombo(fit_with_restrictions,"Specify");
        setText(specify,nilaiBersih);
    }

    private void setCek(javax.swing.AbstractButton cek, String nilai) {
        cek.setSelected("yes".equalsIgnoreCase(nilai)||"true".equalsIgnoreCase(nilai));
    }

    private void kosongkanText(javax.swing.text.JTextComponent... daftarTeks) {
        for (javax.swing.text.JTextComponent teks : daftarTeks) {
            teks.setText("");
        }
    }

    private void resetCombo(javax.swing.JComboBox... daftarCombo) {
        for (javax.swing.JComboBox combo : daftarCombo) {
            if(combo.getItemCount() > 0){
                combo.setSelectedIndex(0);
            }
        }
    }

    private void resetCek(javax.swing.AbstractButton... daftarCek) {
        for (javax.swing.AbstractButton cek : daftarCek) {
            cek.setSelected(false);
        }
    }

    private String getTabelValue(String kolom) {
        if(tbObat.getSelectedRow() < 0){
            return "";
        }
        int index = indexKolomTabel(kolom);
        if(index < 0){
            return "";
        }
        int viewColumn = tbObat.convertColumnIndexToView(index);
        if(viewColumn < 0){
            return "";
        }
        Object nilai = tbObat.getValueAt(tbObat.getSelectedRow(), viewColumn);
        return nilai == null ? "" : nilai.toString();
    }

    private int indexKolomTabel(String kolom) {
        for (int index = 0; index < TABEL_PENILAIAN_MCU_COLUMNS.length; index++) {
            if(TABEL_PENILAIAN_MCU_COLUMNS[index].equals(kolom)){
                return index;
            }
        }
        return -1;
    }

    private String selectNamaPenanggungJawab() {
        return "ifnull(nullif(perusahaan_pasien.nama_perusahaan,''),pasien.perusahaan_pasien)";
    }

    private String[] getNilaiPenilaianMcu() {
        return new String[]{
            TNoRw.getText(),getTanggalAsuhan(),getYearMcu(),KdDokter1.getText(),KdPetugas.getText(),nilaiSaranMcu(),TPasien.getText(),surname.getText(),nilaiCombo(McuGroup),nilai(Dass21),nilai(PhyExam),nilaiLaboratMcu(),nilai(ConcRadiologi),
            nilaiCombo(cbConcEcg),nilai(ConcSpirometry),nilai(ConcAudiometry),nilaiKesimpulanMcu(),TNoRM.getText(),TmpLahir.getText(),TglLahir.getText(),getKodeJenisKelamin(),
            NoTlp.getText(),SukuBangsa.getText(),nilaiCombo(SttsNikah),Doe.getText(),Yoe.getText(),JobTitle.getText(),Activities.getText(),Hobby.getText(),OtherJob.getText(),nilaiCombo(PosisiKerja),
            nilaiCek(JobInvolvesDrivingOrOperatingMobileEquipment),nilaiCek(JobInvolvesWorkingAtHeights),nilaiCek(JobInvolvesClericalOfficeBasedOrAdministrative),
            nilaiCek(JobInvolvesRequiresColourVision),nilaiCek(JobInvolvesPotentialDustExposure),nilaiCek(JobInvolvesCateringStaffIncludingFoodHandlers),
            nilaiCek(JobInvolvesExposingToOtherPotentialDangerous),nilaiCek(RWP1),nilaiCek(RWP2),nilaiCek(RWP3),nilaiCek(RWP4),nilaiCek(RWP5),nilaiCek(RWP6),
            nilaiCek(RWP7),nilaiCek(RWP8),nilaiCek(RWP9),nilaiCek(RWP10),nilaiCek(RWP11),nilaiCek(RWP12),nilaiCek(RWP13),nilaiCek(RWP14),
            nilaiCek(RWP15),nilaiCek(RWP16),nilaiCek(RWP17),nilaiCek(RWP18),nilaiCek(RWP19),nilaiCek(RWP20),nilaiCek(RWP21),nilaiCek(RWP22),
            nilaiCek(RWP23),nilaiCek(RWP24),nilaiCek(RWP25),nilaiCek(RWP26),nilaiCek(RWP27),nilaiCek(RWP28),nilaiCek(RWP29),nilaiCek(RWP30),
            FamilyHistoryFather.getText(),FamilyHistoryMother.getText(),FamilyHistorySiblings.getText(),FamilyHistoryOther.getText(),CigarettesPerday.getText(),AlcoholGrWeek.getText(),
            PrescribedMedication.getText(),PrescribedMedication2.getText(),AnyAllergies.getText(),hb.getText(),wbc.getText(),esr.getText(),bl_group.getText(),gamaa_gt.getText(),
            sgot.getText(),sgpt.getText(),urea.getText(),creatinin.getText(),glucose.getText(),random_glucose.getText(),total_cholestrol.getText(),protein.getText(),blood.getText(),
            bilirubin.getText(),malaria.getText(),tpha.getText(),mantoux_test.getText(),leukosit.getText(),lab_others.getText(),ova.getText(),culture.getText(),cysta.getText(),
            parasites1.getText(),pnemunosicosis.getText(),pnemunosicosis2.getText(),ILO_clasification.getText(),ILO_clasification2.getText(),oth_abnormal.getText(),tb1.getText(),tb2.getText(),page3_comment.getText(),
            TD.getText(),Nadi.getText(),RR.getText(),TB.getText(),BB.getText(),IMT.getText(),KlasifikasiIMT1.getText(),
            nilaiLaboratMcu(),nilai(RongsenThorax),nilaiEcgAbnormal(),spirometri_vc_1.getText(),spirometri_vc_2.getText(),spirometri_vc_3.getText(),
            spirometri_vc_4.getText(),spirometri_fvc_1.getText(),spirometri_fvc_2.getText(),spirometri_fvc_3.getText(),spirometri_fvc_4.getText(),spirometri_fev_1_1.getText(),
            spirometri_fev_1_2.getText(),spirometri_fev_1_3.getText(),spirometri_fev_1_4.getText(),spirometri_fev_1_fvc_1.getText(),spirometri_fev_1_fvc_2.getText(),spirometri_fev_1_fvc_3.getText(),
            spirometri_fev_1_fvc_4.getText(),nilaiCek(audiometri_tinitus_never),nilaiCek(audiometri_tinitus_previously),nilaiCek(audiometri_tinitus_rarely),nilaiCek(audiometri_tinitus_often),nilaiCek(audiometri_tinitus_always),nilaiCek(audiometri_ear_protection_worn_never),
            nilaiCek(audiometri_ear_protection_worn_previously),nilaiCek(audiometri_ear_protection_worn_rarely),nilaiCek(audiometri_ear_protection_worn_often),nilaiCek(audiometri_ear_protection_worn_always),type_of_hearing.getText(),audiometri_left_ear_500.getText(),audiometri_left_ear_1000.getText(),
            audiometri_left_ear_1500.getText(),audiometri_left_ear_2000.getText(),audiometri_left_ear_3000.getText(),audiometri_left_ear_4000.getText(),audiometri_left_ear_5000.getText(),
            audiometri_left_ear_6000.getText(),audiometri_left_ear_501.getText(),audiometri_left_ear_1001.getText(),audiometri_left_ear_1501.getText(),audiometri_left_ear_2001.getText(),
            audiometri_left_ear_3001.getText(),audiometri_left_ear_4001.getText(),audiometri_left_ear_5001.getText(),audiometri_left_ear_6001.getText(),
            audiometri_left_ear_502.getText(),audiometri_left_ear_1002.getText(),audiometri_left_ear_1502.getText(),audiometri_left_ear_2002.getText(),
            audiometri_left_ear_3002.getText(),audiometri_left_ear_4002.getText(),audiometri_left_ear_5002.getText(),audiometri_left_ear_6002.getText(),
            audiometri_left_ear_503.getText(),audiometri_left_ear_1003.getText(),audiometri_left_ear_1503.getText(),audiometri_left_ear_2003.getText(),
            audiometri_left_ear_3003.getText(),audiometri_left_ear_4003.getText(),audiometri_left_ear_5003.getText(),audiometri_left_ear_6003.getText(),
            eye_unaided_distant_r.getText(),eye_unaided_distant_l.getText(),nilaiCombo(eye_glasses_distant_r),nilaiCombo(eye_glasses_distant_l),nilaiCombo(eye_unaided_near_r),
            nilaiCombo(eye_unaided_near_l),nilaiCombo(eye_glasses_near_r),nilaiCombo(eye_glasses_near_l),nilaiCombo(eye_night_vision_1),nilaiCombo(eye_night_vision_2),
            nilaiCombo(eye_brake_test_1),nilaiCombo(eye_brake_test_2),nilaiEyeColorBlindless(nilaiCombo(eye_color_blindless)),visual_fields_left.getText(),visual_fields_right.getText(),nilaiCombo(fundi),nilaiCombo(imunisasi_bcg),
            nilaiCombo(imunisasi_dpt),nilaiCombo(imunisasi_polio),nilaiCombo(imunisasi_morbili),nilaiCombo(imunisasi_thyphoid),nilaiCombo(imunisasi_hep_a),
            nilaiCombo(imunisasi_hep_b),nilaiCombo(imunisasi_tetanus),nilaiCombo(imunisasi_others),nilaiCombo(vertebra_scoliosis),nilaiCombo(vertebra_kyphosis),
            nilaiCombo(vertebra_lordosis),nilaiCombo(vertebra_forward_flexion_0_80),nilaiCombo(vertebra_hyperextensi_0_25),nilaiCombo(vertebra_lateral_flexion_0_20),nilaiCombo(vertebra_heel_walking),
            nilaiCombo(vertebra_toe_walking),nilaiCombo(vertebra_squats_x3),exam_ent_comments.getText(),exam_cardio_vascular_system_comments.getText(),exam_respiratory_system_comments.getText(),
            exam_abdomen_comments.getText(),exam_genito_urinary_system_comments.getText(),exam_central_peripheral_nervous_system_comments.getText(),exam_skin_comments.getText(),exam_lymph_nodes_comments.getText(),exam_dental_comments.getText(),
            nilaiComboYesNo(conclusion_requires_spectacles),nilaiComboYesNo(conclusion_colour_blindness),nilaiComboYesNo(conclusion_respiratory_problem),
            nilaiComboYesNo(conclusion_impaired_hearing),nilaiComboYesNo(conclusion_vertigo),nilaiBloodGroupMcu(),
            nilaiCombo(fit),nilaiFitWithRestrictions(),nilaiSpecify(),nilaiSaranMcu()
        };
    }

    private String[] getNilaiUbahPenilaianMcu() {
        String[] nilai = getNilaiPenilaianMcu();
        String[] ubah = new String[nilai.length];
        System.arraycopy(nilai, 1, ubah, 0, nilai.length - 1);
        ubah[ubah.length - 1] = getTabelValue("no_rawat").equals("") ? TNoRw.getText() : getTabelValue("no_rawat");
        return ubah;
    }

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            String sql = selectPenilaianMcu()+"where penilaian_mcu.tanggal between ? and ? ";
            if(!TCari.getText().equals("")){
                sql = sql+"and (penilaian_mcu.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or penilaian_mcu.surname like ? or pasien.perusahaan_pasien like ? or perusahaan_pasien.nama_perusahaan like ? or pasien.nip like ? or penilaian_mcu.kd_dokter like ? or dokter.nm_dokter like ? or penilaian_mcu.kd_petugas like ? or petugas.nama like ?) ";
            }
            sql = sql+"order by penilaian_mcu.tanggal";
            ps=koneksi.prepareStatement(sql);
            try {
                ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                if(!TCari.getText().equals("")){
                    for (int index = 3; index <= 13; index++) {
                        ps.setString(index,"%"+TCari.getText()+"%");
                    }
                }
                rs=ps.executeQuery();
                while(rs.next()){
                    Object[] baris = new Object[TABEL_PENILAIAN_MCU_COLUMNS.length];
                    for (int index = 0; index < TABEL_PENILAIAN_MCU_COLUMNS.length; index++) {
                        baris[index] = rs.getString(TABEL_PENILAIAN_MCU_COLUMNS[index]);
                    }
                    tabMode.addRow(baris);
                }
            } catch (Exception e) {
                System.out.println("Notif : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        LCount.setText(""+tabMode.getRowCount());
    }

    public void emptTeks() {
        TglAsuhan.setDate(new Date());
        setText(Year,getTahunAsuhan());
        kosongkanText(kesimpulan,rekomendasi2,Dass21,PhyExam,ConcLab,ConcRadiologi,ConcEcg,ConcSpirometry,ConcAudiometry,
                Kesimpulan1,Note1,Doe,Yoe,JobTitle,Activities,Hobby,OtherJob,FamilyHistoryFather,FamilyHistoryMother,FamilyHistorySiblings,
                FamilyHistoryOther,CigarettesPerday,AlcoholGrWeek,PrescribedMedication,PrescribedMedication2,AnyAllergies,TD,Nadi,RR,TB,BB,
                IMT,KlasifikasiIMT1,visual_fields_left,visual_fields_right,PemeriksaanLaboratorium,RongsenThorax,ecg_abnormal,type_of_hearing,eye_unaided_distant_l,eye_unaided_distant_r,spirometri_vc_1,spirometri_vc_2,spirometri_vc_3,
                spirometri_vc_4,spirometri_fvc_1,hb,wbc,esr,bl_group,gamaa_gt,sgot,sgpt,urea,creatinin,glucose,random_glucose,total_cholestrol,protein,blood,bilirubin,
                malaria,tpha,mantoux_test,leukosit,lab_others,ova,culture,cysta,parasites1,pnemunosicosis,pnemunosicosis2,ILO_clasification,ILO_clasification2,oth_abnormal,tb1,tb2,page3_comment,
                spirometri_fvc_2,spirometri_fvc_3,spirometri_fvc_4,spirometri_fev_1_3,spirometri_fev_1_2,
                spirometri_fev_1_4,spirometri_fev_1_1,spirometri_fev_1_fvc_2,spirometri_fev_1_fvc_4,spirometri_fev_1_fvc_3,spirometri_fev_1_fvc_1,audiometri_left_ear_1000,
                audiometri_left_ear_2000,audiometri_left_ear_1500,audiometri_left_ear_500,audiometri_left_ear_3000,audiometri_left_ear_4000,audiometri_left_ear_6000,
                audiometri_left_ear_5000,audiometri_left_ear_1001,audiometri_left_ear_2001,audiometri_left_ear_1501,audiometri_left_ear_501,audiometri_left_ear_3001,
                audiometri_left_ear_4001,audiometri_left_ear_6001,audiometri_left_ear_5001,audiometri_left_ear_1002,audiometri_left_ear_2002,audiometri_left_ear_1502,
                audiometri_left_ear_502,audiometri_left_ear_3002,audiometri_left_ear_4002,audiometri_left_ear_6002,audiometri_left_ear_5002,audiometri_left_ear_1003,
                audiometri_left_ear_2003,audiometri_left_ear_1503,audiometri_left_ear_503,audiometri_left_ear_3003,audiometri_left_ear_4003,audiometri_left_ear_6003,audiometri_left_ear_5003,
                exam_ent_comments,exam_cardio_vascular_system_comments,exam_respiratory_system_comments,exam_abdomen_comments,exam_genito_urinary_system_comments,exam_central_peripheral_nervous_system_comments,exam_skin_comments,
                exam_lymph_nodes_comments,exam_dental_comments,specify,saran,surname,KdPetugas,NmPetugas);
        resetCombo(McuGroup,PosisiKerja,cbConcEcg,eye_glasses_distant_l,eye_glasses_distant_r,eye_unaided_near_l,eye_color_blindless,
                eye_glasses_near_l,eye_glasses_near_r,eye_night_vision_2,eye_night_vision_1,eye_brake_test_2,eye_brake_test_1,eye_unaided_near_r,fundi,
                imunisasi_bcg,imunisasi_dpt,imunisasi_polio,imunisasi_hep_b,imunisasi_morbili,imunisasi_tetanus,imunisasi_thyphoid,imunisasi_others,
                imunisasi_hep_a,vertebra_scoliosis,vertebra_lordosis,vertebra_hyperextensi_0_25,vertebra_forward_flexion_0_80,vertebra_heel_walking,vertebra_lateral_flexion_0_20,vertebra_squats_x3,
                vertebra_toe_walking,vertebra_kyphosis,conclusion_requires_spectacles,conclusion_colour_blindness,conclusion_respiratory_problem,
                conclusion_impaired_hearing,conclusion_vertigo,blood_group,fit,fit_with_restrictions);
        sinkronConcEcg();
        resetCek(JobInvolvesDrivingOrOperatingMobileEquipment,JobInvolvesWorkingAtHeights,JobInvolvesClericalOfficeBasedOrAdministrative,
                JobInvolvesRequiresColourVision,JobInvolvesPotentialDustExposure,JobInvolvesCateringStaffIncludingFoodHandlers,
                JobInvolvesExposingToOtherPotentialDangerous,RWP1,RWP2,RWP3,RWP4,RWP5,RWP6,RWP7,RWP8,RWP9,RWP10,RWP11,RWP12,RWP13,
                RWP14,RWP15,RWP16,RWP17,RWP18,RWP19,RWP20,RWP21,RWP22,RWP23,RWP24,RWP25,RWP26,RWP27,RWP28,RWP29,RWP30,
                audiometri_tinitus_always,audiometri_tinitus_never,audiometri_tinitus_previously,audiometri_tinitus_rarely,audiometri_tinitus_often,audiometri_ear_protection_worn_always,audiometri_ear_protection_worn_never,audiometri_ear_protection_worn_previously,audiometri_ear_protection_worn_rarely,audiometri_ear_protection_worn_often);
        isiPetugasLogin();
        TabRawat.setSelectedIndex(0);
    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            setText(TNoRw,getTabelValue("no_rawat"));
            setText(Year,getTabelValue("year"));
            if(Year.getText().trim().equals("")){
                setText(Year,getTahunDariTanggal(getTabelValue("tanggal")));
            }
            setText(TNoRM,getTabelValue("no_rkm_medis"));
            setText(TPasien,getTabelValue("nama_pasien"));
            setText(surname,getTabelValue("surname"));
            setText(TmpLahir,getTabelValue("tmp_lahir"));
            setText(TglLahir,getTabelValue("tgl_lahir"));
            setText(Jk,tampilJenisKelamin(getTabelValue("jk")));
            setText(NoTlp,getTabelValue("no_tlp"));
            setText(SukuBangsa,getTabelValue("suku_bangsa"));
            setCombo(SttsNikah,getTabelValue("stts_nikah"));
            setText(Perusahaan,getTabelValue("perusahaan_pasien"));
            setText(NIP,getTabelValue("nip"));
            setText(KdDokter1,getTabelValue("kd_dokter"));
            setText(NmDokter,getTabelValue("nm_dokter"));
            setText(KdPetugas,getTabelValue("kd_petugas"));
            setText(NmPetugas,getTabelValue("nm_petugas"));
            setCombo(McuGroup,getTabelValue("mcu_group"));
            String nilaiSaran = nilaiPertamaTerisiValue(getTabelValue("note1"),getTabelValue("unfit_comment_1"));
            setText(Note1,nilaiSaran);
            setText(rekomendasi2,nilaiSaran);
            setText(saran,nilaiSaran);
            setText(Dass21,getTabelValue("dass_21"));
            setText(PhyExam,getTabelValue("phy_exam"));
            setText(ConcLab,nilaiPertamaTerisiValue(getTabelValue("laborat"),getTabelValue("conc_lab")));
            setText(ConcRadiologi,getTabelValue("conc_radiologi"));
            setText(ConcEcg,nilaiConcEcgTabel());
            setText(ConcSpirometry,getTabelValue("conc_spirometry"));
            setText(ConcAudiometry,getTabelValue("conc_audiometry"));
            setText(Kesimpulan1,getTabelValue("kesimpulan1"));
            setText(kesimpulan,getTabelValue("kesimpulan1"));
            setText(Doe,getTabelValue("doe"));
            setText(Yoe,getTabelValue("yoe"));
            setText(JobTitle,getTabelValue("job_title"));
            setText(Activities,getTabelValue("activities"));
            setText(Hobby,getTabelValue("hobby"));
            setText(OtherJob,getTabelValue("other_job"));
            setCombo(PosisiKerja,getTabelValue("posisi_kerja"));
            setCek(JobInvolvesDrivingOrOperatingMobileEquipment,getTabelValue("job_involves_driving_or_operating_mobile_equipment"));
            setCek(JobInvolvesWorkingAtHeights,getTabelValue("job_involves_working_at_heights"));
            setCek(JobInvolvesClericalOfficeBasedOrAdministrative,getTabelValue("job_involves_clerical_office_based_or_administrative"));
            setCek(JobInvolvesRequiresColourVision,getTabelValue("job_involves_requires_colour_vision"));
            setCek(JobInvolvesPotentialDustExposure,getTabelValue("job_involves_potential_dust_exposure"));
            setCek(JobInvolvesCateringStaffIncludingFoodHandlers,getTabelValue("job_involves_catering_staff_including_food_handlers"));
            setCek(JobInvolvesExposingToOtherPotentialDangerous,getTabelValue("job_involves_exposing_to_other_potential_dangerous"));
            setCek(RWP1,getTabelValue("med_hist_head_injury_or_contussion"));
            setCek(RWP2,getTabelValue("med_hist_fainting_blackouts_epilepsy"));
            setCek(RWP3,getTabelValue("med_hist_visual_changes"));
            setCek(RWP4,getTabelValue("med_hist_hearing_loss"));
            setCek(RWP5,getTabelValue("med_hist_nose_sinus_throat_trouble_more_4_weeks"));
            setCek(RWP6,getTabelValue("med_hist_gynaecological_problems"));
            setCek(RWP7,getTabelValue("med_hist_chronic_skin_problem"));
            setCek(RWP8,getTabelValue("med_hist_chronic_diarrhea"));
            setCek(RWP9,getTabelValue("med_hist_anorexia_more_4_weeks"));
            setCek(RWP10,getTabelValue("med_hist_gastritis"));
            setCek(RWP11,getTabelValue("med_hist_jaundice_hepatitis"));
            setCek(RWP12,getTabelValue("med_hist_chronic_cough_more_4_weeks"));
            setCek(RWP13,getTabelValue("med_hist_haemorhoid"));
            setCek(RWP14,getTabelValue("med_hist_chronic_abdominal_pain"));
            setCek(RWP15,getTabelValue("med_hist_diabetes"));
            setCek(RWP16,getTabelValue("med_hist_asthma"));
            setCek(RWP17,getTabelValue("med_hist_allergies"));
            setCek(RWP18,getTabelValue("med_hist_tuberculosis_bronchitis"));
            setCek(RWP19,getTabelValue("med_hist_psychiatric_disorder"));
            setCek(RWP20,getTabelValue("med_hist_sexual_transmitted_diseases"));
            setCek(RWP21,getTabelValue("med_hist_unusual_change_of_weight_more_5kg_per_month"));
            setCek(RWP22,getTabelValue("med_hist_hypertension"));
            setCek(RWP23,getTabelValue("med_hist_chest_pain_heart_disease"));
            setCek(RWP24,getTabelValue("med_hist_malaria_tropical_disease"));
            setCek(RWP25,getTabelValue("med_hist_surgery_operation"));
            setCek(RWP26,getTabelValue("med_hist_back_pain_more_4_weeks"));
            setCek(RWP27,getTabelValue("med_hist_thypoid_fever"));
            setCek(RWP28,getTabelValue("med_hist_swollen_or_painful_joints"));
            setCek(RWP29,getTabelValue("med_hist_kidney_problem_urinary_stones"));
            setCek(RWP30,getTabelValue("med_hist_other_chronical_diseases"));
            setText(FamilyHistoryFather,getTabelValue("family_history_father"));
            setText(FamilyHistoryMother,getTabelValue("family_history_mother"));
            setText(FamilyHistorySiblings,getTabelValue("family_history_siblings"));
            setText(FamilyHistoryOther,getTabelValue("family_history_other"));
            setText(CigarettesPerday,getTabelValue("cigarettes_perday"));
            setText(AlcoholGrWeek,getTabelValue("alcohol_gr_week"));
            setText(PrescribedMedication,getTabelValue("prescribed_medication"));
            setText(PrescribedMedication2,getTabelValue("prescribed_medication_2"));
            setText(AnyAllergies,getTabelValue("any_allergies"));
            setText(hb,getTabelValue("hb"));
            setText(wbc,getTabelValue("wbc"));
            setText(esr,getTabelValue("esr"));
            setText(bl_group,getTabelValue("bl_group"));
            setText(gamaa_gt,getTabelValue("gamaa_gt"));
            setText(sgot,getTabelValue("sgot"));
            setText(sgpt,getTabelValue("sgpt"));
            setText(urea,getTabelValue("urea"));
            setText(creatinin,getTabelValue("creatinin"));
            setText(glucose,getTabelValue("glucose"));
            setText(random_glucose,getTabelValue("random_glucose"));
            setText(total_cholestrol,getTabelValue("total_cholestrol"));
            setText(protein,getTabelValue("protein"));
            setText(blood,getTabelValue("blood"));
            setText(bilirubin,getTabelValue("bilirubin"));
            setText(malaria,getTabelValue("malaria"));
            setText(tpha,getTabelValue("tpha"));
            setText(mantoux_test,getTabelValue("mantoux_test"));
            setText(leukosit,getTabelValue("leukosit"));
            setText(lab_others,getTabelValue("lab_others"));
            setText(ova,getTabelValue("ova"));
            setText(culture,getTabelValue("culture"));
            setText(cysta,getTabelValue("cysta"));
            setText(parasites1,getTabelValue("parasites1"));
            setText(pnemunosicosis,getTabelValue("pnemunosicosis"));
            setText(pnemunosicosis2,getTabelValue("pnemunosicosis2"));
            setText(ILO_clasification,getTabelValue("ILO_clasification"));
            setText(ILO_clasification2,getTabelValue("ILO_clasification2"));
            setText(oth_abnormal,getTabelValue("oth_abnormal"));
            setText(tb1,getTabelValue("tb1"));
            setText(tb2,getTabelValue("tb2"));
            setText(page3_comment,getTabelValue("page3_comment"));
            setText(TD,getTabelValue("td"));
            setText(Nadi,getTabelValue("nadi"));
            setText(RR,getTabelValue("rr"));
            setText(TB,getTabelValue("tb"));
            setText(BB,getTabelValue("bb"));
            setText(IMT,getTabelValue("bmi"));
            setText(KlasifikasiIMT1,getTabelValue("kasifikasi_bmi"));
            setText(PemeriksaanLaboratorium,nilaiPertamaTerisiValue(getTabelValue("laborat"),getTabelValue("conc_lab")));
            setText(RongsenThorax,getTabelValue("radiologi"));
            setConcEcgDariTabel();
            setText(ecg_abnormal,comboTerpilih(cbConcEcg,"Abnormal") ? getTabelValue("ekg") : "");
            setText(spirometri_vc_1,getTabelValue("spirometri_vc_1"));
            setText(spirometri_vc_2,getTabelValue("spirometri_vc_2"));
            setText(spirometri_vc_3,getTabelValue("spirometri_vc_3"));
            setText(spirometri_vc_4,getTabelValue("spirometri_vc_4"));
            setText(spirometri_fvc_1,getTabelValue("spirometri_fvc_1"));
            setText(spirometri_fvc_2,getTabelValue("spirometri_fvc_2"));
            setText(spirometri_fvc_3,getTabelValue("spirometri_fvc_3"));
            setText(spirometri_fvc_4,getTabelValue("spirometri_fvc_4"));
            setText(spirometri_fev_1_1,getTabelValue("spirometri_fev_1_1"));
            setText(spirometri_fev_1_2,getTabelValue("spirometri_fev_1_2"));
            setText(spirometri_fev_1_3,getTabelValue("spirometri_fev_1_3"));
            setText(spirometri_fev_1_4,getTabelValue("spirometri_fev_1_4"));
            setText(spirometri_fev_1_fvc_1,getTabelValue("spirometri_fev_1_fvc_1"));
            setText(spirometri_fev_1_fvc_2,getTabelValue("spirometri_fev_1_fvc_2"));
            setText(spirometri_fev_1_fvc_3,getTabelValue("spirometri_fev_1_fvc_3"));
            setText(spirometri_fev_1_fvc_4,getTabelValue("spirometri_fev_1_fvc_4"));
            setCek(audiometri_tinitus_never,getTabelValue("audiometri_tinitus_never"));
            setCek(audiometri_tinitus_previously,getTabelValue("audiometri_tinitus_previously"));
            setCek(audiometri_tinitus_rarely,getTabelValue("audiometri_tinitus_rarely"));
            setCek(audiometri_tinitus_often,getTabelValue("audiometri_tinitus_often"));
            setCek(audiometri_tinitus_always,getTabelValue("audiometri_tinitus_always"));
            setCek(audiometri_ear_protection_worn_never,getTabelValue("audiometri_ear_protection_worn_never"));
            setCek(audiometri_ear_protection_worn_previously,getTabelValue("audiometri_ear_protection_worn_previously"));
            setCek(audiometri_ear_protection_worn_rarely,getTabelValue("audiometri_ear_protection_worn_rarely"));
            setCek(audiometri_ear_protection_worn_often,getTabelValue("audiometri_ear_protection_worn_often"));
            setCek(audiometri_ear_protection_worn_always,getTabelValue("audiometri_ear_protection_worn_always"));
            setText(type_of_hearing,getTabelValue("type_of_hearing"));
            setText(audiometri_left_ear_500,getTabelValue("audiometri_left_ear_500_AB"));
            setText(audiometri_left_ear_1000,getTabelValue("audiometri_left_ear_1000_AB"));
            setText(audiometri_left_ear_1500,getTabelValue("audiometri_left_ear_1500_AB"));
            setText(audiometri_left_ear_2000,getTabelValue("audiometri_left_ear_2000_AB"));
            setText(audiometri_left_ear_3000,getTabelValue("audiometri_left_ear_3000_AB"));
            setText(audiometri_left_ear_4000,getTabelValue("audiometri_left_ear_4000_AB"));
            setText(audiometri_left_ear_5000,getTabelValue("audiometri_left_ear_5000_AB"));
            setText(audiometri_left_ear_6000,getTabelValue("audiometri_left_ear_6000_AB"));
            setText(audiometri_left_ear_501,getTabelValue("audiometri_left_ear_500_AC"));
            setText(audiometri_left_ear_1001,getTabelValue("audiometri_left_ear_1000_AC"));
            setText(audiometri_left_ear_1501,getTabelValue("audiometri_left_ear_1500_AC"));
            setText(audiometri_left_ear_2001,getTabelValue("audiometri_left_ear_2000_AC"));
            setText(audiometri_left_ear_3001,getTabelValue("audiometri_left_ear_3000_AC"));
            setText(audiometri_left_ear_4001,getTabelValue("audiometri_left_ear_4000_AC"));
            setText(audiometri_left_ear_5001,getTabelValue("audiometri_left_ear_5000_AC"));
            setText(audiometri_left_ear_6001,getTabelValue("audiometri_left_ear_6000_AC"));
            setText(audiometri_left_ear_502,getTabelValue("audiometri_right_ear_500_ab"));
            setText(audiometri_left_ear_1002,getTabelValue("audiometri_right_ear_1000_ab"));
            setText(audiometri_left_ear_1502,getTabelValue("audiometri_right_ear_1500_ab"));
            setText(audiometri_left_ear_2002,getTabelValue("audiometri_right_ear_2000_ab"));
            setText(audiometri_left_ear_3002,getTabelValue("audiometri_right_ear_3000_ab"));
            setText(audiometri_left_ear_4002,getTabelValue("audiometri_right_ear_4000_ab"));
            setText(audiometri_left_ear_5002,getTabelValue("audiometri_right_ear_5000_ab"));
            setText(audiometri_left_ear_6002,getTabelValue("audiometri_right_ear_6000_ab"));
            setText(audiometri_left_ear_503,getTabelValue("audiometri_right_ear_500_ac"));
            setText(audiometri_left_ear_1003,getTabelValue("audiometri_right_ear_1000_ac"));
            setText(audiometri_left_ear_1503,getTabelValue("audiometri_right_ear_1500_ac"));
            setText(audiometri_left_ear_2003,getTabelValue("audiometri_right_ear_2000_ac"));
            setText(audiometri_left_ear_3003,getTabelValue("audiometri_right_ear_3000_ac"));
            setText(audiometri_left_ear_4003,getTabelValue("audiometri_right_ear_4000_ac"));
            setText(audiometri_left_ear_5003,getTabelValue("audiometri_right_ear_5000_ac"));
            setText(audiometri_left_ear_6003,getTabelValue("audiometri_right_ear_6000_ac"));
            setText(eye_unaided_distant_r,getTabelValue("eye_unaided_distant_r"));
            setText(eye_unaided_distant_l,getTabelValue("eye_unaided_distant_l"));
            setCombo(eye_glasses_distant_r,getTabelValue("eye_glasses_distant_r"));
            setCombo(eye_glasses_distant_l,getTabelValue("eye_glasses_distant_l"));
            setCombo(eye_unaided_near_r,getTabelValue("eye_unaided_near_r"));
            setCombo(eye_unaided_near_l,getTabelValue("eye_unaided_near_l"));
            setCombo(eye_glasses_near_r,getTabelValue("eye_glasses_near_r"));
            setCombo(eye_glasses_near_l,getTabelValue("eye_glasses_near_l"));
            setCombo(eye_night_vision_1,getTabelValue("eye_night_vision_1"));
            setCombo(eye_night_vision_2,getTabelValue("eye_night_vision_2"));
            setCombo(eye_brake_test_1,getTabelValue("eye_brake_test_1"));
            setCombo(eye_brake_test_2,getTabelValue("eye_brake_test_2"));
            setCombo(eye_color_blindless,nilaiEyeColorBlindless(getTabelValue("eye_color_blindless")));
            setText(visual_fields_left,getTabelValue("visual_fields_left"));
            setText(visual_fields_right,getTabelValue("visual_fields_right"));
            setCombo(fundi,getTabelValue("fundi"));
            setCombo(imunisasi_bcg,getTabelValue("imunisasi_bcg"));
            setCombo(imunisasi_dpt,getTabelValue("imunisasi_dpt"));
            setCombo(imunisasi_polio,getTabelValue("imunisasi_polio"));
            setCombo(imunisasi_morbili,getTabelValue("imunisasi_morbili"));
            setCombo(imunisasi_thyphoid,getTabelValue("imunisasi_thyphoid"));
            setCombo(imunisasi_hep_a,getTabelValue("imunisasi_hep_a"));
            setCombo(imunisasi_hep_b,getTabelValue("imunisasi_hep_b"));
            setCombo(imunisasi_tetanus,getTabelValue("imunisasi_tetanus"));
            setCombo(imunisasi_others,getTabelValue("imunisasi_others"));
            setCombo(vertebra_scoliosis,getTabelValue("vertebra_scoliosis"));
            setCombo(vertebra_kyphosis,getTabelValue("vertebra_kyphosis"));
            setCombo(vertebra_lordosis,getTabelValue("vertebra_lordosis"));
            setCombo(vertebra_forward_flexion_0_80,getTabelValue("vertebra_forward_flexion_0_80"));
            setCombo(vertebra_hyperextensi_0_25,getTabelValue("vertebra_hyperextensi_0_25"));
            setCombo(vertebra_lateral_flexion_0_20,getTabelValue("vertebra_lateral_flexion_0_20"));
            setCombo(vertebra_heel_walking,getTabelValue("vertebra_heel_walking"));
            setCombo(vertebra_toe_walking,getTabelValue("vertebra_toe_walking"));
            setCombo(vertebra_squats_x3,getTabelValue("vertebra_squats_x3"));
            setText(exam_ent_comments,getTabelValue("exam_ent_comments"));
            setText(exam_cardio_vascular_system_comments,getTabelValue("exam_cardio_vascular_system_comments"));
            setText(exam_respiratory_system_comments,getTabelValue("exam_respiratory_system_comments"));
            setText(exam_abdomen_comments,getTabelValue("exam_abdomen_comments"));
            setText(exam_genito_urinary_system_comments,getTabelValue("exam_genito_urinary_system_comments"));
            setText(exam_central_peripheral_nervous_system_comments,getTabelValue("exam_central_peripheral_nervous_system_comments"));
            setText(exam_skin_comments,getTabelValue("exam_skin_comments"));
            setText(exam_lymph_nodes_comments,getTabelValue("exam_lymph_nodes_comments"));
            setText(exam_dental_comments,getTabelValue("exam_dental_comments"));
            setCombo(conclusion_requires_spectacles,getTabelValue("conclusion_requires_spectacles"));
            setCombo(conclusion_colour_blindness,getTabelValue("conclusion_colour_blindness"));
            setCombo(conclusion_respiratory_problem,getTabelValue("conclusion_respiratory_problem"));
            setCombo(conclusion_impaired_hearing,getTabelValue("conclusion_impaired_hearing"));
            setCombo(conclusion_vertigo,getTabelValue("conclusion_vertigo"));
            setCombo(blood_group,getTabelValue("blood_group"));
            setCombo(fit,getTabelValue("medically_fit"));
            setFitWithRestrictions(getTabelValue("fit_with_restrictions"),getTabelValue("specify"));
            if(!getTabelValue("tanggal").equals("")){
                Valid.SetTgl2(TglAsuhan,getTabelValue("tanggal"));
            }
        }
    }

    private void isRawat() {
        try {
            ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tmp_lahir,pasien.tgl_lahir,pasien.no_tlp,ifnull(suku_bangsa.nama_suku_bangsa,pasien.suku_bangsa) as suku_bangsa,pasien.stts_nikah,"+
                    selectNamaPenanggungJawab()+" as perusahaan_pasien,pasien.nip,reg_periksa.tgl_registrasi,reg_periksa.jam_reg "+
                    "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "left join suku_bangsa on suku_bangsa.id=pasien.suku_bangsa "+
                    "left join perusahaan_pasien on perusahaan_pasien.kode_perusahaan=pasien.perusahaan_pasien where reg_periksa.no_rawat=?");
            try {
                ps.setString(1,TNoRw.getText());
                rs=ps.executeQuery();
                if(rs.next()){
                    setText(TNoRM,rs.getString("no_rkm_medis"));
                    setText(TPasien,rs.getString("nm_pasien"));
                    setText(surname,"");
                    setText(TmpLahir,rs.getString("tmp_lahir"));
                    setText(Jk,tampilJenisKelamin(rs.getString("jk")));
                    setText(TglLahir,rs.getString("tgl_lahir"));
                    setText(NoTlp,rs.getString("no_tlp"));
                    setText(SukuBangsa,rs.getString("suku_bangsa"));
                    setCombo(SttsNikah,rs.getString("stts_nikah"));
                    setText(Perusahaan,rs.getString("perusahaan_pasien"));
                    setText(NIP,rs.getString("nip"));
                    DTPCari1.setDate(rs.getDate("tgl_registrasi"));
                    setText(Year,getTahunDariTanggal(rs.getString("tgl_registrasi")));
                    TanggalRegistrasi.setText(rs.getString("tgl_registrasi")+" "+rs.getString("jam_reg"));
                    setTanggalAsuhanMinimalRegistrasi();
                }
            } catch (Exception e) {
                System.out.println("Notif : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notif : "+e);
        }
    }
    
    public void setNoRm(String norwt, Date tgl2) {
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        DTPCari2.setDate(tgl2);    
        isRawat(); 
    }
    
    public void isCek(){
        boolean bolehAkses=akses.getpenilaian_mcu();
        BtnSimpan.setEnabled(bolehAkses);
        BtnHapus.setEnabled(bolehAkses);
        BtnEdit.setEnabled(bolehAkses);
        isiPetugasLogin();

        if(akses.getjml2()>=1){
            KdDokter1.setEditable(false);
            KdDokter1.setText(akses.getkode());
            NmDokter.setText(Sequel.CariDokter(KdDokter1.getText()));
            if(NmDokter.getText().equals("")){
                // User non-dokter yang diberi akses penilaian_mcu tetap boleh membuka form dan memilih dokter penanggung jawab.
                KdDokter1.setText("");
                BtnDokter.setEnabled(bolehAkses);
            }else{
                // Jika user login adalah dokter, dokter penanggung jawab dikunci sesuai akun login.
                BtnDokter.setEnabled(false);
            }
        }else{
            BtnDokter.setEnabled(bolehAkses);
        }
        
        if(TANGGALMUNDUR.equals("no")){
            if(!akses.getkode().equals("Admin Utama")){
                TglAsuhan.setEditable(false);
                TglAsuhan.setEnabled(false);
            }
        }
    }

    private void isiPetugasLogin() {
        if(akses.getkode().equals("Admin Utama")){
            BtnPetugas.setEnabled(akses.getpenilaian_mcu());
            return;
        }

        KdPetugas.setEditable(false);
        KdPetugas.setText(akses.getkode());
        NmPetugas.setText(Sequel.CariPetugas(KdPetugas.getText()));
        if(NmPetugas.getText().equals("")){
            // Bila akun login belum terdaftar di tabel petugas, petugas bisa dipilih manual selama punya akses penilaian_mcu.
            KdPetugas.setText("");
            BtnPetugas.setEnabled(akses.getpenilaian_mcu());
        }else{
            // Petugas penginput diambil dari akun login agar data MCU tetap mencatat siapa yang mengisi.
            BtnPetugas.setEnabled(false);
        }
    }

    private boolean bolehUbahDataTerpilih() {
        String kodeLogin = akses.getkode();
        return kodeLogin.equals(getTabelValue("kd_dokter"))||kodeLogin.equals(getTabelValue("kd_petugas"));
    }

    public void setTampil(){
       TabRawat.setSelectedIndex(1);
    }
    
    private void hapus() {
        if(Sequel.queryu2tf("delete from penilaian_mcu where no_rawat=?",1,new String[]{
            getTabelValue("no_rawat")
        })==true){
            tampil();
            emptTeks();
        }else{
            JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
        }
    }

    private void ganti() {
        sinkronFieldMcu();
        String[] nilai = getNilaiUbahPenilaianMcu();
        if(Sequel.mengedittf("penilaian_mcu","no_rawat=?",updatePenilaianMcu(),nilai.length,nilai)==true){
            updateIdentitasPasienMcu();
            tampil();
            emptTeks();
            TabRawat.setSelectedIndex(1);
        }
    }

    private void simpan() {
        sinkronFieldMcu();
        if(Sequel.menyimpantf("penilaian_mcu("+kolomInsertPenilaianMcu()+")",placeholderPenilaianMcu(),"No.Rawat",PENILAIAN_MCU_COLUMNS.length,getNilaiPenilaianMcu())==true){
            updateIdentitasPasienMcu();
            tampil();
            emptTeks();
        }
    }

    private void updateIdentitasPasienMcu() {
        if(TNoRM.getText().trim().equals("")){
            return;
        }

        String kodePerusahaan = getKodePerusahaanPasienMcu();
        if(kodePerusahaan.equals("")){
            Sequel.queryu2tf("update pasien set nip=? where no_rkm_medis=?",2,new String[]{
                NIP.getText().trim(),TNoRM.getText().trim()
            });
            JOptionPane.showMessageDialog(null,"Perusahaan tidak ditemukan di master perusahaan. NIP pasien tetap diperbarui, tetapi perusahaan pasien belum diubah..!!");
            return;
        }

        if(Sequel.queryu2tf("update pasien set perusahaan_pasien=?,nip=? where no_rkm_medis=?",3,new String[]{
            kodePerusahaan,NIP.getText().trim(),TNoRM.getText().trim()
        })==false){
            JOptionPane.showMessageDialog(null,"Data Perusahaan/NIP pasien gagal diperbarui..!!");
        }
    }

    private String getKodePerusahaanPasienMcu() {
        String perusahaan = Perusahaan.getText().trim();
        if(perusahaan.equals("")){
            return "-";
        }

        String kodePerusahaan = Sequel.cariIsi("select kode_perusahaan from perusahaan_pasien where kode_perusahaan=?",perusahaan);
        if(kodePerusahaan.equals("")){
            kodePerusahaan = Sequel.cariIsi("select kode_perusahaan from perusahaan_pasien where nama_perusahaan=? order by kode_perusahaan limit 1",perusahaan);
        }
        if(kodePerusahaan.equals("")){
            buatMasterPerusahaanPasienMcu(perusahaan);
            kodePerusahaan = Sequel.cariIsi("select kode_perusahaan from perusahaan_pasien where nama_perusahaan=? order by kode_perusahaan desc limit 1",perusahaan);
        }
        return kodePerusahaan;
    }

    private void buatMasterPerusahaanPasienMcu(String namaPerusahaan) {
        Sequel.queryu2tf(
            "insert into perusahaan_pasien(kode_perusahaan,nama_perusahaan,alamat,kota,no_telp) "+
            "select concat('I',lpad(ifnull(max(cast(substr(kode_perusahaan,2) as unsigned)),0)+1,4,'0')),?,'-','-','0' "+
            "from perusahaan_pasien where kode_perusahaan regexp '^I[0-9]+$'",
            1,new String[]{namaPerusahaan}
        );
    }
    
    private void isBMI(){
        try {
            if((!TB.getText().equals(""))&&(!BB.getText().equals(""))){
                try {
                    IMT.setText(Valid.SetAngka8(Valid.SetAngka(BB.getText())/((Valid.SetAngka(TB.getText())/100)*(Valid.SetAngka(TB.getText())/100)),1)+"");
                } catch (Exception e) {
                    IMT.setText("");
                }
                if(Valid.SetAngka(IMT.getText())<18.5){
                    KlasifikasiIMT1.setText("Berat Badan Kurang");
                }else if((Valid.SetAngka(IMT.getText())>=18.5)&&(Valid.SetAngka(IMT.getText())<=22.9)){
                    KlasifikasiIMT1.setText("Berat Badan Normal");
                }else if((Valid.SetAngka(IMT.getText())>=23)&&(Valid.SetAngka(IMT.getText())<=24.9)){
                    KlasifikasiIMT1.setText("Kelebihan Berat Badan");
                }else if((Valid.SetAngka(IMT.getText())>=25)&&(Valid.SetAngka(IMT.getText())<=29.9)){
                    KlasifikasiIMT1.setText("Obesitas I");
                }else if(Valid.SetAngka(IMT.getText())>=30){
                    KlasifikasiIMT1.setText("Obesitas II");
                }else{
                    KlasifikasiIMT1.setText("");
                }
            }else{
                IMT.setText("");
                KlasifikasiIMT1.setText("");
            }
        } catch (Exception e) {
            IMT.setText("");
            KlasifikasiIMT1.setText("");
        }
    }
    
    private void isLP(){
        // LP dan RisikoLP sudah tidak dipakai di form RMMCURSPK baru.
    }
    
    private void runBackground(Runnable task) {
        if (ceksukses) return;
        if (executor.isShutdown() || executor.isTerminated()) return;
        if (!isDisplayable()) return;

        ceksukses = true;
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        try {
            executor.submit(() -> {
                try {
                    task.run();
                } finally {
                    ceksukses = false;
                    SwingUtilities.invokeLater(() -> {
                        if (isDisplayable()) {
                            setCursor(Cursor.getDefaultCursor());
                        }
                    });
                }
            });
        } catch (RejectedExecutionException ex) {
            ceksukses = false;
        }
    }
    
    @Override
    public void dispose() {
        executor.shutdownNow();
        super.dispose();
    }
}

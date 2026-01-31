/*
 * Kontribusi RSUD Prembun
 */


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
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
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
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import kepegawaian.DlgCariDokter;


/**
 *
 * @author perpustakaan
 */
public final class RMPenilaianAwalMedisHemodialisa extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;
    private DlgCariDokter dokter;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private volatile boolean ceksukses = false;
    private StringBuilder htmlContent;
    private String finger="";
    private String TANGGALMUNDUR="yes";
    
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public RMPenilaianAwalMedisHemodialisa(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.RM","Nama Pasien","Tgl.Lahir","J.K.","Kode Dokter","Nama Dokter","Tanggal","Anamnesis","Hubungan","Asal Poli/Ruangan","Riwayat Alergi Obat",
            "Skala Nyeri","Status Nutrisi","Hipertensi","Keterangan Hipertensi","Diabetes","Keterangan Diabetes","Batu Saluran Kemih","Keterangan Batu Saluran Kemih",
            "Operasi Saluran Kemih","Keterangan Operasi Saluran Kemih","Infeksi Saluran Kemih","Keterangan Infeksi Saluran Kemih","Bengkak Seluruh Tubuh",
            "Keterangan Bengkak Seluruh Tubuh","Urin Berdarah","Keterangan Urin Berdarah","Penyakit Ginjal Laom","Keterangan Penyakit Ginjal Laom","Penyakit Lain",
            "Keterangan Penyakit Lain","Konsumsi Obat Nefro","Keterangan Konsumsi Obat Nefro","Dialisis Pertama","Pernah CPAD","Tgl.CPAD","Pernah Transplantasi",
            "Tgl.Transplantasi","Keadaan Umum","Kesadaran","Nadi(x/menit)","BB(Kg)","TD(mmHg)","Suhu(°C)","Napas(x/menit)","TB(Cm)","Hepatomegali","Splenomegali",
            "Ascites","Edema","Whezzing","Ronchi","Ikterik","Tekanan Vena","Anemia","Kardiomegali","Bising","Thorax","Tgl.Thorax","EKG","Tgl.EKG","BNO","Tgl.BNO",
            "USG","Tgl.USG","Renogram","Tgl.Renogram","Biopsi","Tgl.Biopsi","CT Scan","Tgl.CT Scan","Arteriografi","Tgl.Arteriografi","Kultur Urin","Tgl.Kultur Urin",
            "Laborat","Tgl.Laborat","Hematokrit","Hemoglobin","Leukosit","Trombosit","Hitung Jenis","Ureum","Urin Lengkap","Kreatinin","CCT","SGOT","SGPT","CT/BT",
            "Asam Urat","HbsAg","Anti HCV","Edukasi"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        
        tbObat.setModel(tabMode);
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 94; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(105);
            }else if(i==1){
                column.setPreferredWidth(70);
            }else if(i==2){
                column.setPreferredWidth(150);
            }else if(i==3){
                column.setPreferredWidth(65);
            }else if(i==4){
                column.setPreferredWidth(55);
            }else if(i==5){
                column.setPreferredWidth(80);
            }else if(i==6){
                column.setPreferredWidth(150);
            }else if(i==7){
                column.setPreferredWidth(115);
            }else if(i==8){
                column.setPreferredWidth(80);
            }else if(i==9){
                column.setPreferredWidth(100);
            }else if(i==10){
                column.setPreferredWidth(150);
            }else if(i==11){
                column.setPreferredWidth(150);
            }else if(i==12){
                column.setPreferredWidth(115);
            }else if(i==13){
                column.setPreferredWidth(150);
            }else if(i==14){
                column.setPreferredWidth(59);
            }else if(i==15){
                column.setPreferredWidth(130);
            }else if(i==16){
                column.setPreferredWidth(53);
            }else if(i==17){
                column.setPreferredWidth(120);
            }else if(i==18){
                column.setPreferredWidth(105);
            }else if(i==19){
                column.setPreferredWidth(162);
            }else if(i==20){
                column.setPreferredWidth(120);
            }else if(i==21){
                column.setPreferredWidth(180);
            }else if(i==22){
                column.setPreferredWidth(116);
            }else if(i==23){
                column.setPreferredWidth(175);
            }else if(i==24){
                column.setPreferredWidth(121);
            }else if(i==25){
                column.setPreferredWidth(180);
            }else if(i==26){
                column.setPreferredWidth(75);
            }else if(i==27){
                column.setPreferredWidth(135);
            }else if(i==28){
                column.setPreferredWidth(110);
            }else if(i==29){
                column.setPreferredWidth(169);
            }else if(i==30){
                column.setPreferredWidth(72);
            }else if(i==31){
                column.setPreferredWidth(132);
            }else if(i==32){
                column.setPreferredWidth(111);
            }else if(i==33){
                column.setPreferredWidth(170);
            }else if(i==34){
                column.setPreferredWidth(86);
            }else if(i==35){
                column.setPreferredWidth(72);
            }else if(i==36){
                column.setPreferredWidth(65);
            }else if(i==37){
                column.setPreferredWidth(111);
            }else if(i==38){
                column.setPreferredWidth(93);
            }else if(i==39){
                column.setPreferredWidth(86);
            }else if(i==40){
                column.setPreferredWidth(82);
            }else if(i==41){
                column.setPreferredWidth(76);
            }else if(i==42){
                column.setPreferredWidth(40);
            }else if(i==43){
                column.setPreferredWidth(61);
            }else if(i==44){
                column.setPreferredWidth(51);
            }else if(i==45){
                column.setPreferredWidth(84);
            }else if(i==46){
                column.setPreferredWidth(45);
            }else if(i==47){
                column.setPreferredWidth(77);
            }else if(i==48){
                column.setPreferredWidth(75);
            }else if(i==49){
                column.setPreferredWidth(45);
            }else if(i==50){
                column.setPreferredWidth(43);
            }else if(i==51){
                column.setPreferredWidth(55);
            }else if(i==52){
                column.setPreferredWidth(42);
            }else if(i==53){
                column.setPreferredWidth(42);
            }else if(i==54){
                column.setPreferredWidth(79);
            }else if(i==55){
                column.setPreferredWidth(47);
            }else if(i==56){
                column.setPreferredWidth(73);
            }else if(i==57){
                column.setPreferredWidth(39);
            }else if(i==58){
                column.setPreferredWidth(42);
            }else if(i==59){
                column.setPreferredWidth(65);
            }else if(i==60){
                column.setPreferredWidth(42);
            }else if(i==61){
                column.setPreferredWidth(65);
            }else if(i==62){
                column.setPreferredWidth(42);
            }else if(i==63){
                column.setPreferredWidth(65);
            }else if(i==64){
                column.setPreferredWidth(42);
            }else if(i==65){
                column.setPreferredWidth(65);
            }else if(i==66){
                column.setPreferredWidth(59);
            }else if(i==67){
                column.setPreferredWidth(77);
            }else if(i==68){
                column.setPreferredWidth(42);
            }else if(i==69){
                column.setPreferredWidth(65);
            }else if(i==70){
                column.setPreferredWidth(46);
            }else if(i==71){
                column.setPreferredWidth(65);
            }else if(i==72){
                column.setPreferredWidth(65);
            }else if(i==73){
                column.setPreferredWidth(85);
            }else if(i==74){
                column.setPreferredWidth(62);
            }else if(i==75){
                column.setPreferredWidth(79);
            }else if(i==76){
                column.setPreferredWidth(46);
            }else if(i==77){
                column.setPreferredWidth(65);
            }else if(i==78){
                column.setPreferredWidth(70);
            }else if(i==79){
                column.setPreferredWidth(70);
            }else if(i==80){
                column.setPreferredWidth(70);
            }else if(i==81){
                column.setPreferredWidth(70);
            }else if(i==82){
                column.setPreferredWidth(70);
            }else if(i==83){
                column.setPreferredWidth(70);
            }else if(i==84){
                column.setPreferredWidth(73);
            }else if(i==85){
                column.setPreferredWidth(65);
            }else if(i==86){
                column.setPreferredWidth(65);
            }else if(i==87){
                column.setPreferredWidth(65);
            }else if(i==88){
                column.setPreferredWidth(65);
            }else if(i==89){
                column.setPreferredWidth(65);
            }else if(i==90){
                column.setPreferredWidth(65);
            }else if(i==91){
                column.setPreferredWidth(65);
            }else if(i==92){
                column.setPreferredWidth(65);
            }else if(i==93){
                column.setPreferredWidth(250);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());
        
        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        Hubungan.setDocument(new batasInput((byte)30).getKata(Hubungan));
        AsalRuangan.setDocument(new batasInput((byte)50).getKata(AsalRuangan));
        Alergi.setDocument(new batasInput((int)100).getKata(Alergi));
        StatusNutrisi.setDocument(new batasInput((int)100).getKata(StatusNutrisi));
        KeteranganHipertensi.setDocument(new batasInput((byte)30).getKata(KeteranganHipertensi));
        KeteranganDiabetesMelitus.setDocument(new batasInput((byte)30).getKata(KeteranganDiabetesMelitus));
        KeteranganBatuSaluranKemih.setDocument(new batasInput((byte)30).getKata(KeteranganBatuSaluranKemih));
        KeteranganOperasiSaluranKemih.setDocument(new batasInput((byte)30).getKata(KeteranganOperasiSaluranKemih));
        KeteranganInfeksiSaluranKemih.setDocument(new batasInput((byte)30).getKata(KeteranganInfeksiSaluranKemih));
        KeteranganBengkakSeluruhTubuh.setDocument(new batasInput((byte)30).getKata(KeteranganBengkakSeluruhTubuh));
        KeteranganUrinBerdarah.setDocument(new batasInput((byte)30).getKata(KeteranganUrinBerdarah));
        KeteranganPenyakitGinjalLaom.setDocument(new batasInput((byte)30).getKata(KeteranganPenyakitGinjalLaom));
        KeteranganPenyakitLain.setDocument(new batasInput((byte)30).getKata(KeteranganPenyakitLain));
        KeteranganKonsumsiObatNefrotoksis.setDocument(new batasInput((byte)30).getKata(KeteranganKonsumsiObatNefrotoksis));
        Nadi.setDocument(new batasInput((byte)5).getKata(Nadi));
        BB.setDocument(new batasInput((byte)5).getKata(BB));
        TD.setDocument(new batasInput((byte)8).getKata(TD));
        Suhu.setDocument(new batasInput((byte)5).getKata(Suhu));
        Napas.setDocument(new batasInput((byte)5).getKata(Napas));
        TB.setDocument(new batasInput((byte)5).getKata(TB));
        Hematokrit.setDocument(new batasInput((byte)30).getKata(Hematokrit));
        Hemoglobin.setDocument(new batasInput((byte)30).getKata(Hemoglobin));
        Leukosit.setDocument(new batasInput((byte)30).getKata(Leukosit));
        Trombosit.setDocument(new batasInput((byte)30).getKata(Trombosit));
        HitungJenis.setDocument(new batasInput((byte)30).getKata(HitungJenis));
        Ureum.setDocument(new batasInput((byte)30).getKata(Ureum));
        UrinLengkap.setDocument(new batasInput((byte)30).getKata(UrinLengkap));
        Kreatinin.setDocument(new batasInput((byte)30).getKata(Kreatinin));
        CCT.setDocument(new batasInput((byte)30).getKata(CCT));
        SGOT.setDocument(new batasInput((byte)30).getKata(SGOT));
        SGPT.setDocument(new batasInput((byte)30).getKata(SGPT));
        CT.setDocument(new batasInput((byte)30).getKata(CT));
        AsamUrat.setDocument(new batasInput((byte)30).getKata(AsamUrat));
        Edukasi.setDocument(new batasInput((int)1000).getKata(Edukasi));
        
        
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
        
        try {
            TANGGALMUNDUR=koneksiDB.TANGGALMUNDUR();
        } catch (Exception e) {
            TANGGALMUNDUR="yes";
        }
        
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
        MnPenilaianMedis = new javax.swing.JMenuItem();
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
        TPasien = new widget.TextBox();
        TNoRM = new widget.TextBox();
        label14 = new widget.Label();
        KdDokter = new widget.TextBox();
        NmDokter = new widget.TextBox();
        BtnDokter = new widget.Button();
        jLabel8 = new widget.Label();
        TglLahir = new widget.TextBox();
        jLabel9 = new widget.Label();
        Jk = new widget.TextBox();
        jLabel10 = new widget.Label();
        jLabel11 = new widget.Label();
        jLabel16 = new widget.Label();
        Nadi = new widget.TextBox();
        jLabel17 = new widget.Label();
        jLabel18 = new widget.Label();
        Suhu = new widget.TextBox();
        jLabel22 = new widget.Label();
        TD = new widget.TextBox();
        jLabel20 = new widget.Label();
        jLabel23 = new widget.Label();
        Anamnesis = new widget.ComboBox();
        jLabel94 = new widget.Label();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel38 = new widget.Label();
        Hubungan = new widget.TextBox();
        jLabel40 = new widget.Label();
        jLabel29 = new widget.Label();
        StatusNutrisi = new widget.TextBox();
        DiabetesMelitus = new widget.ComboBox();
        jLabel44 = new widget.Label();
        InfeksiSaluranKemih = new widget.ComboBox();
        jLabel45 = new widget.Label();
        BengkakSeluruhTubuh = new widget.ComboBox();
        jLabel46 = new widget.Label();
        Hipertensi = new widget.ComboBox();
        jLabel49 = new widget.Label();
        BatuSaluranKemih = new widget.ComboBox();
        jLabel50 = new widget.Label();
        OperasiSaluranKemih = new widget.ComboBox();
        jLabel51 = new widget.Label();
        PenyakitGinjalLaom = new widget.ComboBox();
        jLabel101 = new widget.Label();
        label11 = new widget.Label();
        TglAsuhan = new widget.Tanggal();
        jLabel41 = new widget.Label();
        Nyeri = new widget.ComboBox();
        jLabel95 = new widget.Label();
        scrollPane14 = new widget.ScrollPane();
        Edukasi = new widget.TextArea();
        jLabel47 = new widget.Label();
        UrinBerdarah = new widget.ComboBox();
        jLabel113 = new widget.Label();
        jLabel52 = new widget.Label();
        jLabel48 = new widget.Label();
        CAPD = new widget.ComboBox();
        Transplantasi = new widget.ComboBox();
        jLabel53 = new widget.Label();
        jLabel54 = new widget.Label();
        PenyakitLain = new widget.ComboBox();
        KonsumsiObatNefrotoksis = new widget.ComboBox();
        jLabel55 = new widget.Label();
        jLabel24 = new widget.Label();
        Napas = new widget.TextBox();
        jLabel56 = new widget.Label();
        Ikterik = new widget.ComboBox();
        jLabel57 = new widget.Label();
        jLabel58 = new widget.Label();
        jLabel59 = new widget.Label();
        Konjungtiva = new widget.ComboBox();
        TekananVenaJugularis = new widget.ComboBox();
        jLabel60 = new widget.Label();
        jLabel61 = new widget.Label();
        jLabel62 = new widget.Label();
        Kardiomegali = new widget.ComboBox();
        Bising = new widget.ComboBox();
        jLabel63 = new widget.Label();
        jLabel64 = new widget.Label();
        jLabel65 = new widget.Label();
        Whezzing = new widget.ComboBox();
        Ronchi = new widget.ComboBox();
        jLabel66 = new widget.Label();
        jLabel67 = new widget.Label();
        jLabel68 = new widget.Label();
        Hepatomegali = new widget.ComboBox();
        Splenomegali = new widget.ComboBox();
        Ascites = new widget.ComboBox();
        Edema = new widget.ComboBox();
        jLabel69 = new widget.Label();
        jLabel70 = new widget.Label();
        jLabel71 = new widget.Label();
        jLabel72 = new widget.Label();
        jLabel73 = new widget.Label();
        TglThorax = new widget.Tanggal();
        TglDialisis = new widget.Tanggal();
        jLabel74 = new widget.Label();
        TglEKG = new widget.Tanggal();
        jLabel75 = new widget.Label();
        TglBNO = new widget.Tanggal();
        TglUSG = new widget.Tanggal();
        jLabel76 = new widget.Label();
        jLabel77 = new widget.Label();
        TglRenogram = new widget.Tanggal();
        TglLaboratorium = new widget.Tanggal();
        jLabel78 = new widget.Label();
        jLabel79 = new widget.Label();
        jLabel80 = new widget.Label();
        TglKultururin = new widget.Tanggal();
        TglArteriografi = new widget.Tanggal();
        TglCTscan = new widget.Tanggal();
        TglBiopsi = new widget.Tanggal();
        jLabel81 = new widget.Label();
        jLabel82 = new widget.Label();
        jLabel26 = new widget.Label();
        Hematokrit = new widget.TextBox();
        jLabel27 = new widget.Label();
        Hemoglobin = new widget.TextBox();
        jLabel30 = new widget.Label();
        Leukosit = new widget.TextBox();
        Trombosit = new widget.TextBox();
        jLabel31 = new widget.Label();
        jLabel32 = new widget.Label();
        HitungJenis = new widget.TextBox();
        jLabel33 = new widget.Label();
        Ureum = new widget.TextBox();
        Kreatinin = new widget.TextBox();
        jLabel34 = new widget.Label();
        jLabel35 = new widget.Label();
        AsamUrat = new widget.TextBox();
        jLabel36 = new widget.Label();
        SGOT = new widget.TextBox();
        SGPT = new widget.TextBox();
        CT = new widget.TextBox();
        UrinLengkap = new widget.TextBox();
        CCT = new widget.TextBox();
        jLabel43 = new widget.Label();
        jLabel83 = new widget.Label();
        jLabel84 = new widget.Label();
        jLabel85 = new widget.Label();
        jLabel86 = new widget.Label();
        jLabel87 = new widget.Label();
        HbsAg = new widget.ComboBox();
        AntiHCV = new widget.ComboBox();
        jLabel88 = new widget.Label();
        AsalRuangan = new widget.TextBox();
        Alergi = new widget.TextBox();
        jLabel89 = new widget.Label();
        jLabel90 = new widget.Label();
        jSeparator2 = new javax.swing.JSeparator();
        KeteranganHipertensi = new widget.TextBox();
        KeteranganDiabetesMelitus = new widget.TextBox();
        KeteranganBatuSaluranKemih = new widget.TextBox();
        KeteranganOperasiSaluranKemih = new widget.TextBox();
        KeteranganInfeksiSaluranKemih = new widget.TextBox();
        KeteranganBengkakSeluruhTubuh = new widget.TextBox();
        KeteranganUrinBerdarah = new widget.TextBox();
        KeteranganPenyakitGinjalLaom = new widget.TextBox();
        KeteranganPenyakitLain = new widget.TextBox();
        KeteranganKonsumsiObatNefrotoksis = new widget.TextBox();
        jSeparator3 = new javax.swing.JSeparator();
        TglCAPD = new widget.Tanggal();
        TglTransplantasi = new widget.Tanggal();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel91 = new widget.Label();
        KeadaanUmum = new widget.ComboBox();
        jLabel42 = new widget.Label();
        jLabel39 = new widget.Label();
        Kesadaran = new widget.ComboBox();
        jLabel92 = new widget.Label();
        jLabel15 = new widget.Label();
        TB = new widget.TextBox();
        jLabel37 = new widget.Label();
        jLabel12 = new widget.Label();
        BB = new widget.TextBox();
        jLabel13 = new widget.Label();
        jLabel28 = new widget.Label();
        jLabel93 = new widget.Label();
        jSeparator5 = new javax.swing.JSeparator();
        ChkUSG = new widget.CekBox();
        ChkThorax = new widget.CekBox();
        ChkEKG = new widget.CekBox();
        ChkBNO = new widget.CekBox();
        ChkRenogram = new widget.CekBox();
        ChkBiopsiGinjal = new widget.CekBox();
        ChkCTScan = new widget.CekBox();
        ChkArteriografi = new widget.CekBox();
        ChkKulturUrin = new widget.CekBox();
        ChkLaborat = new widget.CekBox();
        jLabel96 = new widget.Label();
        jSeparator6 = new javax.swing.JSeparator();
        jLabel102 = new widget.Label();
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

        MnPenilaianMedis.setBackground(new java.awt.Color(255, 255, 254));
        MnPenilaianMedis.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPenilaianMedis.setForeground(new java.awt.Color(50, 50, 50));
        MnPenilaianMedis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPenilaianMedis.setText("Laporan Pengkajian Medis");
        MnPenilaianMedis.setName("MnPenilaianMedis"); // NOI18N
        MnPenilaianMedis.setPreferredSize(new java.awt.Dimension(220, 26));
        MnPenilaianMedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPenilaianMedisActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnPenilaianMedis);

        TanggalRegistrasi.setHighlighter(null);
        TanggalRegistrasi.setName("TanggalRegistrasi"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Pengkajian Awal Medis Pasien Hemodialisa ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setPreferredSize(new java.awt.Dimension(467, 500));
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
        TabRawat.setPreferredSize(new java.awt.Dimension(457, 480));

        internalFrame2.setBorder(null);
        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setPreferredSize(new java.awt.Dimension(102, 650));
        internalFrame2.setLayout(new java.awt.BorderLayout(1, 1));

        scrollInput.setName("scrollInput"); // NOI18N
        scrollInput.setPreferredSize(new java.awt.Dimension(102, 557));

        FormInput.setBackground(new java.awt.Color(255, 255, 255));
        FormInput.setBorder(null);
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(870, 973));
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

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        FormInput.add(TPasien);
        TPasien.setBounds(309, 10, 260, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        FormInput.add(TNoRM);
        TNoRM.setBounds(207, 10, 100, 23);

        label14.setText("Dokter :");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label14);
        label14.setBounds(0, 40, 70, 23);

        KdDokter.setEditable(false);
        KdDokter.setName("KdDokter"); // NOI18N
        KdDokter.setPreferredSize(new java.awt.Dimension(80, 23));
        KdDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdDokterKeyPressed(evt);
            }
        });
        FormInput.add(KdDokter);
        KdDokter.setBounds(74, 40, 90, 23);

        NmDokter.setEditable(false);
        NmDokter.setName("NmDokter"); // NOI18N
        NmDokter.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmDokter);
        NmDokter.setBounds(166, 40, 180, 23);

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
        BtnDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnDokterKeyPressed(evt);
            }
        });
        FormInput.add(BtnDokter);
        BtnDokter.setBounds(348, 40, 28, 23);

        jLabel8.setText("Tgl.Lahir :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(580, 10, 60, 23);

        TglLahir.setEditable(false);
        TglLahir.setHighlighter(null);
        TglLahir.setName("TglLahir"); // NOI18N
        FormInput.add(TglLahir);
        TglLahir.setBounds(644, 10, 80, 23);

        jLabel9.setText("Riwayat Alergi Obat :");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(350, 80, 130, 23);

        Jk.setEditable(false);
        Jk.setHighlighter(null);
        Jk.setName("Jk"); // NOI18N
        FormInput.add(Jk);
        Jk.setBounds(774, 10, 80, 23);

        jLabel10.setText("No.Rawat :");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(0, 10, 70, 23);

        jLabel11.setText("J.K. :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(740, 10, 30, 23);

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel16.setText("x/menit");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput.add(jLabel16);
        jLabel16.setBounds(662, 380, 50, 23);

        Nadi.setFocusTraversalPolicyProvider(true);
        Nadi.setName("Nadi"); // NOI18N
        Nadi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NadiKeyPressed(evt);
            }
        });
        FormInput.add(Nadi);
        Nadi.setBounds(614, 380, 45, 23);

        jLabel17.setText("Nadi :");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(560, 380, 50, 23);

        jLabel18.setText("Suhu :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(305, 410, 70, 23);

        Suhu.setFocusTraversalPolicyProvider(true);
        Suhu.setName("Suhu"); // NOI18N
        Suhu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SuhuKeyPressed(evt);
            }
        });
        FormInput.add(Suhu);
        Suhu.setBounds(379, 410, 45, 23);

        jLabel22.setText("TD :");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(0, 410, 127, 23);

        TD.setFocusTraversalPolicyProvider(true);
        TD.setName("TD"); // NOI18N
        TD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TDKeyPressed(evt);
            }
        });
        FormInput.add(TD);
        TD.setBounds(131, 410, 76, 23);

        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel20.setText("°C");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(427, 410, 30, 23);

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel23.setText("mmHg");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(210, 410, 50, 23);

        Anamnesis.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Autoanamnesis", "Alloanamnesis" }));
        Anamnesis.setName("Anamnesis"); // NOI18N
        Anamnesis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AnamnesisKeyPressed(evt);
            }
        });
        FormInput.add(Anamnesis);
        Anamnesis.setBounds(644, 40, 128, 23);

        jLabel94.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel94.setText("II. RIWAYAT DIALISIS/TRANSPLANTASI");
        jLabel94.setName("jLabel94"); // NOI18N
        FormInput.add(jLabel94);
        jLabel94.setBounds(10, 310, 220, 23);

        jSeparator1.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator1.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator1.setName("jSeparator1"); // NOI18N
        FormInput.add(jSeparator1);
        jSeparator1.setBounds(0, 70, 880, 1);

        jLabel38.setText("Anamnesis :");
        jLabel38.setName("jLabel38"); // NOI18N
        FormInput.add(jLabel38);
        jLabel38.setBounds(570, 40, 70, 23);

        Hubungan.setName("Hubungan"); // NOI18N
        Hubungan.setPreferredSize(new java.awt.Dimension(207, 23));
        Hubungan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HubunganKeyPressed(evt);
            }
        });
        FormInput.add(Hubungan);
        Hubungan.setBounds(774, 40, 80, 23);

        jLabel40.setText("Diabetes Melitus :");
        jLabel40.setName("jLabel40"); // NOI18N
        FormInput.add(jLabel40);
        jLabel40.setBounds(0, 190, 160, 23);

        jLabel29.setText("Status Nutrisi :");
        jLabel29.setName("jLabel29"); // NOI18N
        FormInput.add(jLabel29);
        jLabel29.setBounds(270, 110, 100, 23);

        StatusNutrisi.setFocusTraversalPolicyProvider(true);
        StatusNutrisi.setName("StatusNutrisi"); // NOI18N
        StatusNutrisi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                StatusNutrisiKeyPressed(evt);
            }
        });
        FormInput.add(StatusNutrisi);
        StatusNutrisi.setBounds(374, 110, 300, 23);

        DiabetesMelitus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        DiabetesMelitus.setName("DiabetesMelitus"); // NOI18N
        DiabetesMelitus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiabetesMelitusKeyPressed(evt);
            }
        });
        FormInput.add(DiabetesMelitus);
        DiabetesMelitus.setBounds(164, 190, 80, 23);

        jLabel44.setText("Infeksi Saluran Kemih :");
        jLabel44.setName("jLabel44"); // NOI18N
        FormInput.add(jLabel44);
        jLabel44.setBounds(0, 280, 160, 23);

        InfeksiSaluranKemih.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        InfeksiSaluranKemih.setName("InfeksiSaluranKemih"); // NOI18N
        InfeksiSaluranKemih.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                InfeksiSaluranKemihKeyPressed(evt);
            }
        });
        FormInput.add(InfeksiSaluranKemih);
        InfeksiSaluranKemih.setBounds(164, 280, 80, 23);

        jLabel45.setText("Bengkak Seluruh Tubuh :");
        jLabel45.setName("jLabel45"); // NOI18N
        FormInput.add(jLabel45);
        jLabel45.setBounds(430, 160, 160, 23);

        BengkakSeluruhTubuh.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        BengkakSeluruhTubuh.setName("BengkakSeluruhTubuh"); // NOI18N
        BengkakSeluruhTubuh.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BengkakSeluruhTubuhKeyPressed(evt);
            }
        });
        FormInput.add(BengkakSeluruhTubuh);
        BengkakSeluruhTubuh.setBounds(594, 160, 80, 23);

        jLabel46.setText("Mengalami Hipertensi :");
        jLabel46.setName("jLabel46"); // NOI18N
        FormInput.add(jLabel46);
        jLabel46.setBounds(0, 160, 160, 23);

        Hipertensi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Hipertensi.setName("Hipertensi"); // NOI18N
        Hipertensi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HipertensiKeyPressed(evt);
            }
        });
        FormInput.add(Hipertensi);
        Hipertensi.setBounds(164, 160, 80, 23);

        jLabel49.setText("Batu Saluran Kemih :");
        jLabel49.setName("jLabel49"); // NOI18N
        FormInput.add(jLabel49);
        jLabel49.setBounds(0, 220, 160, 23);

        BatuSaluranKemih.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        BatuSaluranKemih.setName("BatuSaluranKemih"); // NOI18N
        BatuSaluranKemih.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BatuSaluranKemihKeyPressed(evt);
            }
        });
        FormInput.add(BatuSaluranKemih);
        BatuSaluranKemih.setBounds(164, 220, 80, 23);

        jLabel50.setText("Operasi Saluran Kemih :");
        jLabel50.setName("jLabel50"); // NOI18N
        FormInput.add(jLabel50);
        jLabel50.setBounds(0, 250, 160, 23);

        OperasiSaluranKemih.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        OperasiSaluranKemih.setName("OperasiSaluranKemih"); // NOI18N
        OperasiSaluranKemih.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OperasiSaluranKemihActionPerformed(evt);
            }
        });
        OperasiSaluranKemih.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                OperasiSaluranKemihKeyPressed(evt);
            }
        });
        FormInput.add(OperasiSaluranKemih);
        OperasiSaluranKemih.setBounds(164, 250, 80, 23);

        jLabel51.setText("Penyakit Ginjal Laom :");
        jLabel51.setName("jLabel51"); // NOI18N
        FormInput.add(jLabel51);
        jLabel51.setBounds(430, 220, 160, 23);

        PenyakitGinjalLaom.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        PenyakitGinjalLaom.setName("PenyakitGinjalLaom"); // NOI18N
        PenyakitGinjalLaom.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenyakitGinjalLaomKeyPressed(evt);
            }
        });
        FormInput.add(PenyakitGinjalLaom);
        PenyakitGinjalLaom.setBounds(594, 220, 80, 23);

        jLabel101.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel101.setText("IV. PEMERIKSAAN PENUNJANG");
        jLabel101.setName("jLabel101"); // NOI18N
        FormInput.add(jLabel101);
        jLabel101.setBounds(10, 600, 190, 23);

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label11);
        label11.setBounds(380, 40, 52, 23);

        TglAsuhan.setForeground(new java.awt.Color(50, 70, 50));
        TglAsuhan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "03-05-2024 18:40:23" }));
        TglAsuhan.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        TglAsuhan.setName("TglAsuhan"); // NOI18N
        TglAsuhan.setOpaque(false);
        TglAsuhan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TglAsuhanActionPerformed(evt);
            }
        });
        TglAsuhan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglAsuhanKeyPressed(evt);
            }
        });
        FormInput.add(TglAsuhan);
        TglAsuhan.setBounds(436, 40, 130, 23);

        jLabel41.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel41.setText("Skala Nyeri");
        jLabel41.setName("jLabel41"); // NOI18N
        FormInput.add(jLabel41);
        jLabel41.setBounds(16, 110, 80, 23);

        Nyeri.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Nyeri", "Nyeri Ringan", "Nyeri Sedang", "Nyeri Berat", "Nyeri Sangat Berat", "Nyeri Tak Tertahankan" }));
        Nyeri.setName("Nyeri"); // NOI18N
        Nyeri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NyeriKeyPressed(evt);
            }
        });
        FormInput.add(Nyeri);
        Nyeri.setBounds(81, 110, 160, 23);

        jLabel95.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel95.setText("I. RIWAYAT PENYAKIT");
        jLabel95.setName("jLabel95"); // NOI18N
        FormInput.add(jLabel95);
        jLabel95.setBounds(10, 140, 180, 23);

        scrollPane14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane14.setName("scrollPane14"); // NOI18N

        Edukasi.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Edukasi.setColumns(20);
        Edukasi.setRows(5);
        Edukasi.setName("Edukasi"); // NOI18N
        Edukasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EdukasiKeyPressed(evt);
            }
        });
        scrollPane14.setViewportView(Edukasi);

        FormInput.add(scrollPane14);
        scrollPane14.setBounds(44, 900, 810, 63);

        jLabel47.setText("Urin Berdarah :");
        jLabel47.setName("jLabel47"); // NOI18N
        FormInput.add(jLabel47);
        jLabel47.setBounds(430, 190, 160, 23);

        UrinBerdarah.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        UrinBerdarah.setName("UrinBerdarah"); // NOI18N
        UrinBerdarah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                UrinBerdarahKeyPressed(evt);
            }
        });
        FormInput.add(UrinBerdarah);
        UrinBerdarah.setBounds(594, 190, 80, 23);

        jLabel113.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel113.setText("III. PEMERIKSAAN FISIK PADA HD PERTAMA");
        jLabel113.setName("jLabel113"); // NOI18N
        FormInput.add(jLabel113);
        jLabel113.setBounds(10, 360, 240, 23);

        jLabel52.setText("Pernah Transplantasi Ginjal :");
        jLabel52.setName("jLabel52"); // NOI18N
        FormInput.add(jLabel52);
        jLabel52.setBounds(510, 330, 166, 23);

        jLabel48.setText(":");
        jLabel48.setName("jLabel48"); // NOI18N
        FormInput.add(jLabel48);
        jLabel48.setBounds(0, 330, 130, 23);

        CAPD.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        CAPD.setName("CAPD"); // NOI18N
        CAPD.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                CAPDItemStateChanged(evt);
            }
        });
        CAPD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CAPDKeyPressed(evt);
            }
        });
        FormInput.add(CAPD);
        CAPD.setBounds(330, 330, 80, 23);

        Transplantasi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Transplantasi.setName("Transplantasi"); // NOI18N
        Transplantasi.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TransplantasiItemStateChanged(evt);
            }
        });
        Transplantasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TransplantasiKeyPressed(evt);
            }
        });
        FormInput.add(Transplantasi);
        Transplantasi.setBounds(680, 330, 80, 23);

        jLabel53.setText("Pernah CPAD :");
        jLabel53.setName("jLabel53"); // NOI18N
        FormInput.add(jLabel53);
        jLabel53.setBounds(230, 330, 96, 23);

        jLabel54.setText("Penyakit Lain :");
        jLabel54.setName("jLabel54"); // NOI18N
        FormInput.add(jLabel54);
        jLabel54.setBounds(430, 250, 160, 23);

        PenyakitLain.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        PenyakitLain.setName("PenyakitLain"); // NOI18N
        PenyakitLain.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenyakitLainKeyPressed(evt);
            }
        });
        FormInput.add(PenyakitLain);
        PenyakitLain.setBounds(594, 250, 80, 23);

        KonsumsiObatNefrotoksis.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        KonsumsiObatNefrotoksis.setName("KonsumsiObatNefrotoksis"); // NOI18N
        KonsumsiObatNefrotoksis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KonsumsiObatNefrotoksisKeyPressed(evt);
            }
        });
        FormInput.add(KonsumsiObatNefrotoksis);
        KonsumsiObatNefrotoksis.setBounds(594, 280, 80, 23);

        jLabel55.setText("Konsumsi Obat Nefrotoksis :");
        jLabel55.setName("jLabel55"); // NOI18N
        FormInput.add(jLabel55);
        jLabel55.setBounds(430, 280, 160, 23);

        jLabel24.setText("Napas :");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(560, 410, 50, 23);

        Napas.setFocusTraversalPolicyProvider(true);
        Napas.setName("Napas"); // NOI18N
        Napas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NapasKeyPressed(evt);
            }
        });
        FormInput.add(Napas);
        Napas.setBounds(614, 410, 45, 23);

        jLabel56.setText("Ikterik :");
        jLabel56.setName("jLabel56"); // NOI18N
        FormInput.add(jLabel56);
        jLabel56.setBounds(355, 540, 70, 23);

        Ikterik.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Ikterik.setName("Ikterik"); // NOI18N
        Ikterik.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                IkterikKeyPressed(evt);
            }
        });
        FormInput.add(Ikterik);
        Ikterik.setBounds(429, 540, 80, 23);

        jLabel57.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel57.setText("Jantung :");
        jLabel57.setToolTipText("");
        jLabel57.setName("jLabel57"); // NOI18N
        FormInput.add(jLabel57);
        jLabel57.setBounds(680, 490, 80, 23);

        jLabel58.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel58.setText("Konjungtiva :");
        jLabel58.setName("jLabel58"); // NOI18N
        FormInput.add(jLabel58);
        jLabel58.setBounds(680, 440, 100, 23);

        jLabel59.setText("Anemia :");
        jLabel59.setName("jLabel59"); // NOI18N
        FormInput.add(jLabel59);
        jLabel59.setBounds(660, 460, 110, 23);

        Konjungtiva.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Konjungtiva.setName("Konjungtiva"); // NOI18N
        Konjungtiva.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KonjungtivaKeyPressed(evt);
            }
        });
        FormInput.add(Konjungtiva);
        Konjungtiva.setBounds(774, 460, 80, 23);

        TekananVenaJugularis.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Meningkat" }));
        TekananVenaJugularis.setName("TekananVenaJugularis"); // NOI18N
        TekananVenaJugularis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TekananVenaJugularisKeyPressed(evt);
            }
        });
        FormInput.add(TekananVenaJugularis);
        TekananVenaJugularis.setBounds(506, 570, 90, 23);

        jLabel60.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel60.setText("Tekanan Vena Jugularis (JVP)");
        jLabel60.setName("jLabel60"); // NOI18N
        FormInput.add(jLabel60);
        jLabel60.setBounds(350, 570, 170, 23);

        jLabel61.setText("Bising :");
        jLabel61.setName("jLabel61"); // NOI18N
        FormInput.add(jLabel61);
        jLabel61.setBounds(660, 540, 110, 23);

        jLabel62.setText("Kardiomegali :");
        jLabel62.setName("jLabel62"); // NOI18N
        FormInput.add(jLabel62);
        jLabel62.setBounds(660, 510, 110, 23);

        Kardiomegali.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Kardiomegali.setName("Kardiomegali"); // NOI18N
        Kardiomegali.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KardiomegaliKeyPressed(evt);
            }
        });
        FormInput.add(Kardiomegali);
        Kardiomegali.setBounds(774, 510, 80, 23);

        Bising.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Bising.setName("Bising"); // NOI18N
        Bising.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BisingKeyPressed(evt);
            }
        });
        FormInput.add(Bising);
        Bising.setBounds(774, 540, 80, 23);

        jLabel63.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel63.setText("Sklera :");
        jLabel63.setName("jLabel63"); // NOI18N
        FormInput.add(jLabel63);
        jLabel63.setBounds(350, 520, 110, 23);

        jLabel64.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel64.setText("Paru :");
        jLabel64.setToolTipText("");
        jLabel64.setName("jLabel64"); // NOI18N
        FormInput.add(jLabel64);
        jLabel64.setBounds(350, 440, 60, 23);

        jLabel65.setText("Whezzing :");
        jLabel65.setName("jLabel65"); // NOI18N
        FormInput.add(jLabel65);
        jLabel65.setBounds(355, 460, 70, 23);

        Whezzing.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Whezzing.setName("Whezzing"); // NOI18N
        Whezzing.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                WhezzingKeyPressed(evt);
            }
        });
        FormInput.add(Whezzing);
        Whezzing.setBounds(429, 460, 80, 23);

        Ronchi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Ronchi.setName("Ronchi"); // NOI18N
        Ronchi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RonchiKeyPressed(evt);
            }
        });
        FormInput.add(Ronchi);
        Ronchi.setBounds(429, 490, 80, 23);

        jLabel66.setText("Ronchi :");
        jLabel66.setName("jLabel66"); // NOI18N
        FormInput.add(jLabel66);
        jLabel66.setBounds(355, 490, 70, 23);

        jLabel67.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel67.setText("Abdomen :");
        jLabel67.setToolTipText("");
        jLabel67.setName("jLabel67"); // NOI18N
        FormInput.add(jLabel67);
        jLabel67.setBounds(44, 440, 80, 23);

        jLabel68.setText("Hepatomegali :");
        jLabel68.setName("jLabel68"); // NOI18N
        FormInput.add(jLabel68);
        jLabel68.setBounds(0, 460, 135, 23);

        Hepatomegali.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Hepatomegali.setName("Hepatomegali"); // NOI18N
        Hepatomegali.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HepatomegaliKeyPressed(evt);
            }
        });
        FormInput.add(Hepatomegali);
        Hepatomegali.setBounds(139, 460, 80, 23);

        Splenomegali.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Splenomegali.setName("Splenomegali"); // NOI18N
        Splenomegali.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SplenomegaliKeyPressed(evt);
            }
        });
        FormInput.add(Splenomegali);
        Splenomegali.setBounds(139, 490, 80, 23);

        Ascites.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Ascites.setName("Ascites"); // NOI18N
        Ascites.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AscitesKeyPressed(evt);
            }
        });
        FormInput.add(Ascites);
        Ascites.setBounds(139, 520, 80, 23);

        Edema.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Edema.setName("Edema"); // NOI18N
        Edema.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EdemaKeyPressed(evt);
            }
        });
        FormInput.add(Edema);
        Edema.setBounds(139, 570, 80, 23);

        jLabel69.setText("Edema :");
        jLabel69.setName("jLabel69"); // NOI18N
        FormInput.add(jLabel69);
        jLabel69.setBounds(0, 570, 135, 23);

        jLabel70.setText("Ascites :");
        jLabel70.setName("jLabel70"); // NOI18N
        FormInput.add(jLabel70);
        jLabel70.setBounds(0, 520, 135, 23);

        jLabel71.setText("Splenomegali :");
        jLabel71.setName("jLabel71"); // NOI18N
        FormInput.add(jLabel71);
        jLabel71.setBounds(0, 490, 135, 23);

        jLabel72.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel72.setText("Ekstremitas :");
        jLabel72.setToolTipText("");
        jLabel72.setName("jLabel72"); // NOI18N
        FormInput.add(jLabel72);
        jLabel72.setBounds(44, 550, 140, 23);

        jLabel73.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel73.setText("1. Foto Thorax ");
        jLabel73.setName("jLabel73"); // NOI18N
        FormInput.add(jLabel73);
        jLabel73.setBounds(44, 620, 100, 23);

        TglThorax.setForeground(new java.awt.Color(50, 70, 50));
        TglThorax.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "03-05-2024" }));
        TglThorax.setDisplayFormat("dd-MM-yyyy");
        TglThorax.setEnabled(false);
        TglThorax.setName("TglThorax"); // NOI18N
        TglThorax.setOpaque(false);
        TglThorax.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglThoraxKeyPressed(evt);
            }
        });
        FormInput.add(TglThorax);
        TglThorax.setBounds(161, 620, 90, 23);

        TglDialisis.setForeground(new java.awt.Color(50, 70, 50));
        TglDialisis.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "03-05-2024" }));
        TglDialisis.setDisplayFormat("dd-MM-yyyy");
        TglDialisis.setName("TglDialisis"); // NOI18N
        TglDialisis.setOpaque(false);
        TglDialisis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglDialisisKeyPressed(evt);
            }
        });
        FormInput.add(TglDialisis);
        TglDialisis.setBounds(134, 330, 90, 23);

        jLabel74.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel74.setText("2. EKG ");
        jLabel74.setName("jLabel74"); // NOI18N
        FormInput.add(jLabel74);
        jLabel74.setBounds(44, 650, 100, 23);

        TglEKG.setForeground(new java.awt.Color(50, 70, 50));
        TglEKG.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "03-05-2024" }));
        TglEKG.setDisplayFormat("dd-MM-yyyy");
        TglEKG.setEnabled(false);
        TglEKG.setName("TglEKG"); // NOI18N
        TglEKG.setOpaque(false);
        TglEKG.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglEKGKeyPressed(evt);
            }
        });
        FormInput.add(TglEKG);
        TglEKG.setBounds(161, 650, 90, 23);

        jLabel75.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel75.setText("3. BNO/IVP");
        jLabel75.setName("jLabel75"); // NOI18N
        FormInput.add(jLabel75);
        jLabel75.setBounds(44, 680, 100, 23);

        TglBNO.setForeground(new java.awt.Color(50, 70, 50));
        TglBNO.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "03-05-2024" }));
        TglBNO.setDisplayFormat("dd-MM-yyyy");
        TglBNO.setEnabled(false);
        TglBNO.setName("TglBNO"); // NOI18N
        TglBNO.setOpaque(false);
        TglBNO.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglBNOKeyPressed(evt);
            }
        });
        FormInput.add(TglBNO);
        TglBNO.setBounds(161, 680, 90, 23);

        TglUSG.setForeground(new java.awt.Color(50, 70, 50));
        TglUSG.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "03-05-2024" }));
        TglUSG.setDisplayFormat("dd-MM-yyyy");
        TglUSG.setEnabled(false);
        TglUSG.setName("TglUSG"); // NOI18N
        TglUSG.setOpaque(false);
        TglUSG.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglUSGKeyPressed(evt);
            }
        });
        FormInput.add(TglUSG);
        TglUSG.setBounds(161, 710, 90, 23);

        jLabel76.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel76.setText("4. USG");
        jLabel76.setName("jLabel76"); // NOI18N
        FormInput.add(jLabel76);
        jLabel76.setBounds(44, 710, 100, 23);

        jLabel77.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel77.setText("5. Renogram ");
        jLabel77.setName("jLabel77"); // NOI18N
        FormInput.add(jLabel77);
        jLabel77.setBounds(345, 620, 105, 23);

        TglRenogram.setForeground(new java.awt.Color(50, 70, 50));
        TglRenogram.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "03-05-2024" }));
        TglRenogram.setDisplayFormat("dd-MM-yyyy");
        TglRenogram.setEnabled(false);
        TglRenogram.setName("TglRenogram"); // NOI18N
        TglRenogram.setOpaque(false);
        TglRenogram.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglRenogramKeyPressed(evt);
            }
        });
        FormInput.add(TglRenogram);
        TglRenogram.setBounds(470, 620, 90, 23);

        TglLaboratorium.setForeground(new java.awt.Color(50, 70, 50));
        TglLaboratorium.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "03-05-2024" }));
        TglLaboratorium.setDisplayFormat("dd-MM-yyyy");
        TglLaboratorium.setEnabled(false);
        TglLaboratorium.setName("TglLaboratorium"); // NOI18N
        TglLaboratorium.setOpaque(false);
        TglLaboratorium.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglLaboratoriumKeyPressed(evt);
            }
        });
        FormInput.add(TglLaboratorium);
        TglLaboratorium.setBounds(764, 650, 90, 23);

        jLabel78.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel78.setText("10. Laboratorium");
        jLabel78.setName("jLabel78"); // NOI18N
        FormInput.add(jLabel78);
        jLabel78.setBounds(645, 650, 100, 23);

        jLabel79.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel79.setText("9. Kultur Urin");
        jLabel79.setName("jLabel79"); // NOI18N
        FormInput.add(jLabel79);
        jLabel79.setBounds(645, 620, 100, 23);

        jLabel80.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel80.setText("8. Arteriografi");
        jLabel80.setName("jLabel80"); // NOI18N
        FormInput.add(jLabel80);
        jLabel80.setBounds(345, 710, 105, 23);

        TglKultururin.setForeground(new java.awt.Color(50, 70, 50));
        TglKultururin.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "03-05-2024" }));
        TglKultururin.setDisplayFormat("dd-MM-yyyy");
        TglKultururin.setEnabled(false);
        TglKultururin.setName("TglKultururin"); // NOI18N
        TglKultururin.setOpaque(false);
        TglKultururin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglKultururinKeyPressed(evt);
            }
        });
        FormInput.add(TglKultururin);
        TglKultururin.setBounds(764, 620, 90, 23);

        TglArteriografi.setForeground(new java.awt.Color(50, 70, 50));
        TglArteriografi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "03-05-2024" }));
        TglArteriografi.setDisplayFormat("dd-MM-yyyy");
        TglArteriografi.setEnabled(false);
        TglArteriografi.setName("TglArteriografi"); // NOI18N
        TglArteriografi.setOpaque(false);
        TglArteriografi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglArteriografiKeyPressed(evt);
            }
        });
        FormInput.add(TglArteriografi);
        TglArteriografi.setBounds(470, 710, 90, 23);

        TglCTscan.setForeground(new java.awt.Color(50, 70, 50));
        TglCTscan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "03-05-2024" }));
        TglCTscan.setDisplayFormat("dd-MM-yyyy");
        TglCTscan.setEnabled(false);
        TglCTscan.setName("TglCTscan"); // NOI18N
        TglCTscan.setOpaque(false);
        TglCTscan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglCTscanKeyPressed(evt);
            }
        });
        FormInput.add(TglCTscan);
        TglCTscan.setBounds(470, 680, 90, 23);

        TglBiopsi.setForeground(new java.awt.Color(50, 70, 50));
        TglBiopsi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "03-05-2024" }));
        TglBiopsi.setDisplayFormat("dd-MM-yyyy");
        TglBiopsi.setEnabled(false);
        TglBiopsi.setName("TglBiopsi"); // NOI18N
        TglBiopsi.setOpaque(false);
        TglBiopsi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglBiopsiKeyPressed(evt);
            }
        });
        FormInput.add(TglBiopsi);
        TglBiopsi.setBounds(470, 650, 90, 23);

        jLabel81.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel81.setText("7. CT Scan");
        jLabel81.setName("jLabel81"); // NOI18N
        FormInput.add(jLabel81);
        jLabel81.setBounds(345, 680, 105, 23);

        jLabel82.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel82.setText("6. PA Biopsi Ginjal");
        jLabel82.setName("jLabel82"); // NOI18N
        FormInput.add(jLabel82);
        jLabel82.setBounds(345, 650, 105, 23);

        jLabel26.setText("Hematokrit :");
        jLabel26.setName("jLabel26"); // NOI18N
        FormInput.add(jLabel26);
        jLabel26.setBounds(0, 760, 135, 23);

        Hematokrit.setFocusTraversalPolicyProvider(true);
        Hematokrit.setName("Hematokrit"); // NOI18N
        Hematokrit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HematokritKeyPressed(evt);
            }
        });
        FormInput.add(Hematokrit);
        Hematokrit.setBounds(139, 760, 110, 23);

        jLabel27.setText("Hemoglobin :");
        jLabel27.setName("jLabel27"); // NOI18N
        FormInput.add(jLabel27);
        jLabel27.setBounds(0, 790, 135, 23);

        Hemoglobin.setFocusTraversalPolicyProvider(true);
        Hemoglobin.setName("Hemoglobin"); // NOI18N
        Hemoglobin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HemoglobinKeyPressed(evt);
            }
        });
        FormInput.add(Hemoglobin);
        Hemoglobin.setBounds(139, 790, 110, 23);

        jLabel30.setText("Leukosit :");
        jLabel30.setName("jLabel30"); // NOI18N
        FormInput.add(jLabel30);
        jLabel30.setBounds(0, 820, 135, 23);

        Leukosit.setFocusTraversalPolicyProvider(true);
        Leukosit.setName("Leukosit"); // NOI18N
        Leukosit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LeukositKeyPressed(evt);
            }
        });
        FormInput.add(Leukosit);
        Leukosit.setBounds(139, 820, 110, 23);

        Trombosit.setFocusTraversalPolicyProvider(true);
        Trombosit.setName("Trombosit"); // NOI18N
        Trombosit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TrombositKeyPressed(evt);
            }
        });
        FormInput.add(Trombosit);
        Trombosit.setBounds(139, 850, 110, 23);

        jLabel31.setText("Trombosit :");
        jLabel31.setName("jLabel31"); // NOI18N
        FormInput.add(jLabel31);
        jLabel31.setBounds(0, 850, 135, 23);

        jLabel32.setText("Hitung Jenis :");
        jLabel32.setName("jLabel32"); // NOI18N
        FormInput.add(jLabel32);
        jLabel32.setBounds(255, 760, 95, 23);

        HitungJenis.setFocusTraversalPolicyProvider(true);
        HitungJenis.setName("HitungJenis"); // NOI18N
        HitungJenis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HitungJenisKeyPressed(evt);
            }
        });
        FormInput.add(HitungJenis);
        HitungJenis.setBounds(354, 760, 110, 23);

        jLabel33.setText("Ureum :");
        jLabel33.setName("jLabel33"); // NOI18N
        FormInput.add(jLabel33);
        jLabel33.setBounds(255, 790, 95, 23);

        Ureum.setFocusTraversalPolicyProvider(true);
        Ureum.setName("Ureum"); // NOI18N
        Ureum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                UreumKeyPressed(evt);
            }
        });
        FormInput.add(Ureum);
        Ureum.setBounds(354, 790, 110, 23);

        Kreatinin.setFocusTraversalPolicyProvider(true);
        Kreatinin.setName("Kreatinin"); // NOI18N
        Kreatinin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KreatininKeyPressed(evt);
            }
        });
        FormInput.add(Kreatinin);
        Kreatinin.setBounds(354, 850, 110, 23);

        jLabel34.setText("Kreatinin :");
        jLabel34.setName("jLabel34"); // NOI18N
        FormInput.add(jLabel34);
        jLabel34.setBounds(255, 850, 95, 23);

        jLabel35.setText("Asam Urat :");
        jLabel35.setName("jLabel35"); // NOI18N
        FormInput.add(jLabel35);
        jLabel35.setBounds(660, 760, 80, 23);

        AsamUrat.setFocusTraversalPolicyProvider(true);
        AsamUrat.setName("AsamUrat"); // NOI18N
        AsamUrat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AsamUratKeyPressed(evt);
            }
        });
        FormInput.add(AsamUrat);
        AsamUrat.setBounds(744, 760, 110, 23);

        jLabel36.setText("SGOT :");
        jLabel36.setName("jLabel36"); // NOI18N
        FormInput.add(jLabel36);
        jLabel36.setBounds(460, 790, 75, 23);

        SGOT.setFocusTraversalPolicyProvider(true);
        SGOT.setName("SGOT"); // NOI18N
        SGOT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SGOTKeyPressed(evt);
            }
        });
        FormInput.add(SGOT);
        SGOT.setBounds(539, 790, 110, 23);

        SGPT.setFocusTraversalPolicyProvider(true);
        SGPT.setName("SGPT"); // NOI18N
        SGPT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SGPTKeyPressed(evt);
            }
        });
        FormInput.add(SGPT);
        SGPT.setBounds(539, 820, 110, 23);

        CT.setFocusTraversalPolicyProvider(true);
        CT.setName("CT"); // NOI18N
        CT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CTKeyPressed(evt);
            }
        });
        FormInput.add(CT);
        CT.setBounds(539, 850, 110, 23);

        UrinLengkap.setFocusTraversalPolicyProvider(true);
        UrinLengkap.setName("UrinLengkap"); // NOI18N
        UrinLengkap.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                UrinLengkapKeyPressed(evt);
            }
        });
        FormInput.add(UrinLengkap);
        UrinLengkap.setBounds(354, 820, 110, 23);

        CCT.setFocusTraversalPolicyProvider(true);
        CCT.setName("CCT"); // NOI18N
        CCT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CCTKeyPressed(evt);
            }
        });
        FormInput.add(CCT);
        CCT.setBounds(539, 760, 110, 23);

        jLabel43.setText("Anti HCV :");
        jLabel43.setName("jLabel43"); // NOI18N
        FormInput.add(jLabel43);
        jLabel43.setBounds(660, 820, 80, 23);

        jLabel83.setText("CCT :");
        jLabel83.setName("jLabel83"); // NOI18N
        FormInput.add(jLabel83);
        jLabel83.setBounds(460, 760, 75, 23);

        jLabel84.setText("Urin Lengkap :");
        jLabel84.setName("jLabel84"); // NOI18N
        FormInput.add(jLabel84);
        jLabel84.setBounds(255, 820, 95, 23);

        jLabel85.setText("CT/BT :");
        jLabel85.setName("jLabel85"); // NOI18N
        FormInput.add(jLabel85);
        jLabel85.setBounds(460, 850, 75, 23);

        jLabel86.setText("HbsAg :");
        jLabel86.setName("jLabel86"); // NOI18N
        FormInput.add(jLabel86);
        jLabel86.setBounds(660, 790, 80, 23);

        jLabel87.setText("SGPT :");
        jLabel87.setName("jLabel87"); // NOI18N
        FormInput.add(jLabel87);
        jLabel87.setBounds(460, 820, 75, 23);

        HbsAg.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Non Reaktif", "Reaktif" }));
        HbsAg.setName("HbsAg"); // NOI18N
        HbsAg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HbsAgKeyPressed(evt);
            }
        });
        FormInput.add(HbsAg);
        HbsAg.setBounds(744, 790, 110, 23);

        AntiHCV.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Non Reaktif", "Reaktif" }));
        AntiHCV.setName("AntiHCV"); // NOI18N
        AntiHCV.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AntiHCVKeyPressed(evt);
            }
        });
        FormInput.add(AntiHCV);
        AntiHCV.setBounds(744, 820, 110, 23);

        jLabel88.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel88.setText("Asal Poli/Ruangan");
        jLabel88.setName("jLabel88"); // NOI18N
        FormInput.add(jLabel88);
        jLabel88.setBounds(16, 80, 110, 23);

        AsalRuangan.setFocusTraversalPolicyProvider(true);
        AsalRuangan.setName("AsalRuangan"); // NOI18N
        AsalRuangan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AsalRuanganKeyPressed(evt);
            }
        });
        FormInput.add(AsalRuangan);
        AsalRuangan.setBounds(117, 80, 200, 23);
        AsalRuangan.getAccessibleContext().setAccessibleDescription("");

        Alergi.setFocusTraversalPolicyProvider(true);
        Alergi.setName("Alergi"); // NOI18N
        Alergi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlergiKeyPressed(evt);
            }
        });
        FormInput.add(Alergi);
        Alergi.setBounds(484, 80, 370, 23);

        jLabel89.setText(":");
        jLabel89.setName("jLabel89"); // NOI18N
        FormInput.add(jLabel89);
        jLabel89.setBounds(0, 80, 113, 23);

        jLabel90.setText(":");
        jLabel90.setName("jLabel90"); // NOI18N
        FormInput.add(jLabel90);
        jLabel90.setBounds(0, 110, 77, 23);

        jSeparator2.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator2.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator2.setName("jSeparator2"); // NOI18N
        FormInput.add(jSeparator2);
        jSeparator2.setBounds(0, 140, 880, 1);

        KeteranganHipertensi.setFocusTraversalPolicyProvider(true);
        KeteranganHipertensi.setName("KeteranganHipertensi"); // NOI18N
        KeteranganHipertensi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganHipertensiKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganHipertensi);
        KeteranganHipertensi.setBounds(246, 160, 178, 23);

        KeteranganDiabetesMelitus.setFocusTraversalPolicyProvider(true);
        KeteranganDiabetesMelitus.setName("KeteranganDiabetesMelitus"); // NOI18N
        KeteranganDiabetesMelitus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganDiabetesMelitusKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganDiabetesMelitus);
        KeteranganDiabetesMelitus.setBounds(246, 190, 178, 23);

        KeteranganBatuSaluranKemih.setFocusTraversalPolicyProvider(true);
        KeteranganBatuSaluranKemih.setName("KeteranganBatuSaluranKemih"); // NOI18N
        KeteranganBatuSaluranKemih.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganBatuSaluranKemihKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganBatuSaluranKemih);
        KeteranganBatuSaluranKemih.setBounds(246, 220, 178, 23);

        KeteranganOperasiSaluranKemih.setFocusTraversalPolicyProvider(true);
        KeteranganOperasiSaluranKemih.setName("KeteranganOperasiSaluranKemih"); // NOI18N
        KeteranganOperasiSaluranKemih.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganOperasiSaluranKemihKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganOperasiSaluranKemih);
        KeteranganOperasiSaluranKemih.setBounds(246, 250, 178, 23);

        KeteranganInfeksiSaluranKemih.setFocusTraversalPolicyProvider(true);
        KeteranganInfeksiSaluranKemih.setName("KeteranganInfeksiSaluranKemih"); // NOI18N
        KeteranganInfeksiSaluranKemih.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganInfeksiSaluranKemihKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganInfeksiSaluranKemih);
        KeteranganInfeksiSaluranKemih.setBounds(246, 280, 178, 23);

        KeteranganBengkakSeluruhTubuh.setFocusTraversalPolicyProvider(true);
        KeteranganBengkakSeluruhTubuh.setName("KeteranganBengkakSeluruhTubuh"); // NOI18N
        KeteranganBengkakSeluruhTubuh.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganBengkakSeluruhTubuhKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganBengkakSeluruhTubuh);
        KeteranganBengkakSeluruhTubuh.setBounds(676, 160, 178, 23);

        KeteranganUrinBerdarah.setFocusTraversalPolicyProvider(true);
        KeteranganUrinBerdarah.setName("KeteranganUrinBerdarah"); // NOI18N
        KeteranganUrinBerdarah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganUrinBerdarahKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganUrinBerdarah);
        KeteranganUrinBerdarah.setBounds(676, 190, 178, 23);

        KeteranganPenyakitGinjalLaom.setFocusTraversalPolicyProvider(true);
        KeteranganPenyakitGinjalLaom.setName("KeteranganPenyakitGinjalLaom"); // NOI18N
        KeteranganPenyakitGinjalLaom.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganPenyakitGinjalLaomKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganPenyakitGinjalLaom);
        KeteranganPenyakitGinjalLaom.setBounds(676, 220, 178, 23);

        KeteranganPenyakitLain.setFocusTraversalPolicyProvider(true);
        KeteranganPenyakitLain.setName("KeteranganPenyakitLain"); // NOI18N
        KeteranganPenyakitLain.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganPenyakitLainKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganPenyakitLain);
        KeteranganPenyakitLain.setBounds(676, 250, 178, 23);

        KeteranganKonsumsiObatNefrotoksis.setFocusTraversalPolicyProvider(true);
        KeteranganKonsumsiObatNefrotoksis.setName("KeteranganKonsumsiObatNefrotoksis"); // NOI18N
        KeteranganKonsumsiObatNefrotoksis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKonsumsiObatNefrotoksisKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganKonsumsiObatNefrotoksis);
        KeteranganKonsumsiObatNefrotoksis.setBounds(676, 280, 178, 23);

        jSeparator3.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator3.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator3.setName("jSeparator3"); // NOI18N
        FormInput.add(jSeparator3);
        jSeparator3.setBounds(0, 310, 880, 1);

        TglCAPD.setForeground(new java.awt.Color(50, 70, 50));
        TglCAPD.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "03-05-2024" }));
        TglCAPD.setDisplayFormat("dd-MM-yyyy");
        TglCAPD.setEnabled(false);
        TglCAPD.setName("TglCAPD"); // NOI18N
        TglCAPD.setOpaque(false);
        TglCAPD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglCAPDKeyPressed(evt);
            }
        });
        FormInput.add(TglCAPD);
        TglCAPD.setBounds(414, 330, 90, 23);

        TglTransplantasi.setForeground(new java.awt.Color(50, 70, 50));
        TglTransplantasi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "03-05-2024" }));
        TglTransplantasi.setDisplayFormat("dd-MM-yyyy");
        TglTransplantasi.setEnabled(false);
        TglTransplantasi.setName("TglTransplantasi"); // NOI18N
        TglTransplantasi.setOpaque(false);
        TglTransplantasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglTransplantasiKeyPressed(evt);
            }
        });
        FormInput.add(TglTransplantasi);
        TglTransplantasi.setBounds(764, 330, 90, 23);

        jSeparator4.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator4.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator4.setName("jSeparator4"); // NOI18N
        FormInput.add(jSeparator4);
        jSeparator4.setBounds(0, 360, 880, 1);

        jLabel91.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel91.setText("Keadaan Umum");
        jLabel91.setName("jLabel91"); // NOI18N
        FormInput.add(jLabel91);
        jLabel91.setBounds(44, 380, 90, 23);

        KeadaanUmum.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Sehat", "Sakit Ringan", "Sakit Sedang", "Sakit Berat" }));
        KeadaanUmum.setName("KeadaanUmum"); // NOI18N
        KeadaanUmum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeadaanUmumKeyPressed(evt);
            }
        });
        FormInput.add(KeadaanUmum);
        KeadaanUmum.setBounds(131, 380, 118, 23);

        jLabel42.setText(":");
        jLabel42.setName("jLabel42"); // NOI18N
        FormInput.add(jLabel42);
        jLabel42.setBounds(0, 380, 127, 23);

        jLabel39.setText("Kesadaran :");
        jLabel39.setName("jLabel39"); // NOI18N
        FormInput.add(jLabel39);
        jLabel39.setBounds(305, 380, 70, 23);

        Kesadaran.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Compos Mentis", "Apatis", "Somnolen", "Sopor", "Koma" }));
        Kesadaran.setName("Kesadaran"); // NOI18N
        Kesadaran.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KesadaranKeyPressed(evt);
            }
        });
        FormInput.add(Kesadaran);
        Kesadaran.setBounds(379, 380, 130, 23);

        jLabel92.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel92.setText("Dialisis Pertama");
        jLabel92.setName("jLabel92"); // NOI18N
        FormInput.add(jLabel92);
        jLabel92.setBounds(44, 330, 100, 23);

        jLabel15.setText("TB :");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(730, 410, 50, 23);

        TB.setFocusTraversalPolicyProvider(true);
        TB.setName("TB"); // NOI18N
        TB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TBKeyPressed(evt);
            }
        });
        FormInput.add(TB);
        TB.setBounds(784, 410, 45, 23);

        jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel37.setText("Cm");
        jLabel37.setName("jLabel37"); // NOI18N
        FormInput.add(jLabel37);
        jLabel37.setBounds(832, 410, 30, 23);

        jLabel12.setText("BB :");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(730, 380, 50, 23);

        BB.setFocusTraversalPolicyProvider(true);
        BB.setName("BB"); // NOI18N
        BB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BBKeyPressed(evt);
            }
        });
        FormInput.add(BB);
        BB.setBounds(784, 380, 45, 23);

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel13.setText("Kg");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(832, 380, 30, 23);

        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel28.setText("x/menit");
        jLabel28.setName("jLabel28"); // NOI18N
        FormInput.add(jLabel28);
        jLabel28.setBounds(662, 410, 50, 23);

        jLabel93.setText(":");
        jLabel93.setName("jLabel93"); // NOI18N
        FormInput.add(jLabel93);
        jLabel93.setBounds(352, 570, 150, 23);

        jSeparator5.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator5.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator5.setName("jSeparator5"); // NOI18N
        FormInput.add(jSeparator5);
        jSeparator5.setBounds(0, 600, 880, 1);

        ChkUSG.setBorder(null);
        ChkUSG.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkUSG.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkUSG.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkUSG.setName("ChkUSG"); // NOI18N
        ChkUSG.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ChkUSGItemStateChanged(evt);
            }
        });
        FormInput.add(ChkUSG);
        ChkUSG.setBounds(135, 710, 23, 23);

        ChkThorax.setBorder(null);
        ChkThorax.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkThorax.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkThorax.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkThorax.setName("ChkThorax"); // NOI18N
        ChkThorax.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ChkThoraxItemStateChanged(evt);
            }
        });
        FormInput.add(ChkThorax);
        ChkThorax.setBounds(135, 620, 23, 23);

        ChkEKG.setBorder(null);
        ChkEKG.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkEKG.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkEKG.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkEKG.setName("ChkEKG"); // NOI18N
        ChkEKG.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ChkEKGItemStateChanged(evt);
            }
        });
        FormInput.add(ChkEKG);
        ChkEKG.setBounds(135, 650, 23, 23);

        ChkBNO.setBorder(null);
        ChkBNO.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkBNO.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkBNO.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkBNO.setName("ChkBNO"); // NOI18N
        ChkBNO.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ChkBNOItemStateChanged(evt);
            }
        });
        FormInput.add(ChkBNO);
        ChkBNO.setBounds(135, 680, 23, 23);

        ChkRenogram.setBorder(null);
        ChkRenogram.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkRenogram.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkRenogram.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkRenogram.setName("ChkRenogram"); // NOI18N
        ChkRenogram.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ChkRenogramItemStateChanged(evt);
            }
        });
        FormInput.add(ChkRenogram);
        ChkRenogram.setBounds(444, 620, 23, 23);

        ChkBiopsiGinjal.setBorder(null);
        ChkBiopsiGinjal.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkBiopsiGinjal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkBiopsiGinjal.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkBiopsiGinjal.setName("ChkBiopsiGinjal"); // NOI18N
        ChkBiopsiGinjal.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ChkBiopsiGinjalItemStateChanged(evt);
            }
        });
        FormInput.add(ChkBiopsiGinjal);
        ChkBiopsiGinjal.setBounds(444, 650, 23, 23);

        ChkCTScan.setBorder(null);
        ChkCTScan.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkCTScan.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkCTScan.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkCTScan.setName("ChkCTScan"); // NOI18N
        ChkCTScan.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ChkCTScanItemStateChanged(evt);
            }
        });
        FormInput.add(ChkCTScan);
        ChkCTScan.setBounds(444, 680, 23, 23);

        ChkArteriografi.setBorder(null);
        ChkArteriografi.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkArteriografi.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkArteriografi.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkArteriografi.setName("ChkArteriografi"); // NOI18N
        ChkArteriografi.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ChkArteriografiItemStateChanged(evt);
            }
        });
        FormInput.add(ChkArteriografi);
        ChkArteriografi.setBounds(444, 710, 23, 23);

        ChkKulturUrin.setBorder(null);
        ChkKulturUrin.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkKulturUrin.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkKulturUrin.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkKulturUrin.setName("ChkKulturUrin"); // NOI18N
        ChkKulturUrin.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ChkKulturUrinItemStateChanged(evt);
            }
        });
        FormInput.add(ChkKulturUrin);
        ChkKulturUrin.setBounds(738, 620, 23, 23);

        ChkLaborat.setBorder(null);
        ChkLaborat.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkLaborat.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkLaborat.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkLaborat.setName("ChkLaborat"); // NOI18N
        ChkLaborat.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ChkLaboratItemStateChanged(evt);
            }
        });
        FormInput.add(ChkLaborat);
        ChkLaborat.setBounds(738, 650, 23, 23);

        jLabel96.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel96.setText("Hasil Pemeriksaan Laboratorium :");
        jLabel96.setName("jLabel96"); // NOI18N
        FormInput.add(jLabel96);
        jLabel96.setBounds(44, 740, 210, 23);

        jSeparator6.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator6.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator6.setName("jSeparator6"); // NOI18N
        FormInput.add(jSeparator6);
        jSeparator6.setBounds(0, 880, 880, 1);

        jLabel102.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel102.setText("V. EDUKASI");
        jLabel102.setName("jLabel102"); // NOI18N
        FormInput.add(jLabel102);
        jLabel102.setBounds(10, 880, 190, 23);

        scrollInput.setViewportView(FormInput);

        internalFrame2.add(scrollInput, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Input Pengkajian", internalFrame2);

        internalFrame3.setBorder(null);
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);
        Scroll.setPreferredSize(new java.awt.Dimension(452, 200));

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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "03-05-2024" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "03-05-2024" }));
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
        if(TNoRM.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"Nama Pasien");
        }else if(NmDokter.getText().trim().equals("")){
            Valid.textKosong(BtnDokter,"Dokter");
        }else if(AsalRuangan.getText().trim().equals("")){
            Valid.textKosong(AsalRuangan,"Asal Ruangan");
        }else{
            if(akses.getkode().equals("Admin Utama")){
                simpan();
            }else{
                if(TanggalRegistrasi.getText().equals("")){
                    TanggalRegistrasi.setText(Sequel.cariIsi("select concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) from reg_periksa where reg_periksa.no_rawat=?",TNoRw.getText()));
                }
                if(Sequel.cekTanggalRegistrasi(TanggalRegistrasi.getText(),Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19))==true){
                    simpan();
                }
            }
        }
    
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,Edukasi,BtnBatal);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

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
                if(KdDokter.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString())){
                    if(Sequel.cekTanggal48jam(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString(),Sequel.ambiltanggalsekarang())==true){
                        hapus();
                    }
                }else{
                    JOptionPane.showMessageDialog(null,"Hanya bisa dihapus oleh dokter yang bersangkutan..!!");
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
        if(TNoRM.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"Nama Pasien");
        }else if(NmDokter.getText().trim().equals("")){
            Valid.textKosong(BtnDokter,"Dokter");
        }else if(AsalRuangan.getText().trim().equals("")){
            Valid.textKosong(AsalRuangan,"Asal Ruangan");
        }else{
            if(tbObat.getSelectedRow()>-1){
                if(akses.getkode().equals("Admin Utama")){
                    ganti();
                }else{
                    if(KdDokter.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString())){
                        if(Sequel.cekTanggal48jam(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString(),Sequel.ambiltanggalsekarang())==true){
                            if(TanggalRegistrasi.getText().equals("")){
                                TanggalRegistrasi.setText(Sequel.cariIsi("select concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) from reg_periksa where reg_periksa.no_rawat=?",TNoRw.getText()));
                            }
                            if(Sequel.cekTanggalRegistrasi(TanggalRegistrasi.getText(),Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19))==true){
                                ganti();
                            }
                        }
                    }else{
                        JOptionPane.showMessageDialog(null,"Hanya bisa diganti oleh dokter yang bersangkutan..!!");
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
                htmlContent = new StringBuilder();
                htmlContent.append(                             
                    "<tr class='isi'>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>No.Rawat</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>No.RM</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Nama Pasien</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Tgl.Lahir</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>J.K.</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>NIP</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Nama Dokter</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Tanggal</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Anamnesis</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Hubungan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Asal Poli/Ruangan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Riwayat Alergi Obat</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Skala Nyeri</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Status Nutrisi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Hipertensi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Keterangan Hipertensi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Diabetes</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Keterangan Diabetes</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Batu Saluran Kemih</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Keterangan Batu Saluran Kemih</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Operasi Saluran Kemih</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Keterangan Operasi Saluran Kemih</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Infeksi Saluran Kemih</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Keterangan Infeksi Saluran Kemih</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Bengkak Seluruh Tubuh</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Keterangan Bengkak Seluruh Tubuh</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Urin Berdarah</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Keterangan Urin Berdarah</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Penyakit Ginjal Laom</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Keterangan Penyakit Ginjal Laom</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Penyakit Lain</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Keterangan Penyakit Lain</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Konsumsi Obat Nefro</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Keterangan Konsumsi Obat Nefro</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Dialisis Pertama</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Pernah CPAD</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Tgl.CPAD</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Pernah Transplantasi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Tgl.Transplantasi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Keadaan Umum</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Kesadaran</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Nadi(x/menit)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>BB(Kg)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>TD(mmHg)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Suhu(°C)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Napas(x/menit)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>TB(Cm)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Hepatomegali</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Splenomegali</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Ascites</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Edema</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Whezzing</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Ronchi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Ikterik</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Tekanan Vena</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Anemia</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Kardiomegali</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Bising</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Thorax</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Tgl.Thorax</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>EKG</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Tgl.EKG</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>BNO</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Tgl.BNO</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>USG</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Tgl.USG</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Renogram</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Tgl.Renogram</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Biopsi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Tgl.Biopsi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>CT Scan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Tgl.CT Scan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Arteriografi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Tgl.Arteriografi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Kultur Urin</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Tgl.Kultur Urin</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Laborat</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Tgl.Laborat</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Hematokrit</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Hemoglobin</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Leukosit</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Trombosit</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Hitung Jenis</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Ureum</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Urin Lengkap</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Kreatinin</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>CCT</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>SGOT</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>SGPT</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>CT/BT</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Asam Urat</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>HbsAg</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Anti HCV</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Edukasi</b></td>"+
                    "</tr>"
                );
                for (i = 0; i < tabMode.getRowCount(); i++) {
                    htmlContent.append(
                        "<tr class='isi'>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,0).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,1).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,2).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,3).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,4).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,5).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,6).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,7).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,8).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,9).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,10).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,11).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,12).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,13).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,14).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,15).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,16).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,17).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,18).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,19).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,20).toString()+"</td>"+ 
                            "<td valign='top'>"+tbObat.getValueAt(i,21).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,22).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,23).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,24).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,25).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,26).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,27).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,28).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,29).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,30).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,31).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,32).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,33).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,34).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,35).toString()+"</td>"+
                            "<td valign='top'>"+(tbObat.getValueAt(i,35).toString().equals("Ya")?tbObat.getValueAt(i,36).toString():"")+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,37).toString()+"</td>"+
                            "<td valign='top'>"+(tbObat.getValueAt(i,37).toString().equals("Ya")?tbObat.getValueAt(i,38).toString():"")+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,39).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,40).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,41).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,42).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,43).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,44).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,45).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,46).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,47).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,48).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,49).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,50).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,51).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,52).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,53).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,54).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,55).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,56).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,57).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,58).toString()+"</td>"+
                            "<td valign='top'>"+(tbObat.getValueAt(i,58).toString().equals("Ya")?tbObat.getValueAt(i,59).toString():"")+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,60).toString()+"</td>"+
                            "<td valign='top'>"+(tbObat.getValueAt(i,60).toString().equals("Ya")?tbObat.getValueAt(i,61).toString():"")+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,62).toString()+"</td>"+
                            "<td valign='top'>"+(tbObat.getValueAt(i,62).toString().equals("Ya")?tbObat.getValueAt(i,63).toString():"")+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,64).toString()+"</td>"+
                            "<td valign='top'>"+(tbObat.getValueAt(i,64).toString().equals("Ya")?tbObat.getValueAt(i,65).toString():"")+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,66).toString()+"</td>"+
                            "<td valign='top'>"+(tbObat.getValueAt(i,66).toString().equals("Ya")?tbObat.getValueAt(i,67).toString():"")+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,68).toString()+"</td>"+
                            "<td valign='top'>"+(tbObat.getValueAt(i,68).toString().equals("Ya")?tbObat.getValueAt(i,69).toString():"")+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,70).toString()+"</td>"+
                            "<td valign='top'>"+(tbObat.getValueAt(i,70).toString().equals("Ya")?tbObat.getValueAt(i,71).toString():"")+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,72).toString()+"</td>"+
                            "<td valign='top'>"+(tbObat.getValueAt(i,72).toString().equals("Ya")?tbObat.getValueAt(i,73).toString():"")+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,74).toString()+"</td>"+
                            "<td valign='top'>"+(tbObat.getValueAt(i,74).toString().equals("Ya")?tbObat.getValueAt(i,75).toString():"")+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,76).toString()+"</td>"+
                            "<td valign='top'>"+(tbObat.getValueAt(i,76).toString().equals("Ya")?tbObat.getValueAt(i,77).toString():"")+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,78).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,79).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,80).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,81).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,82).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,83).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,84).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,85).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,86).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,87).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,88).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,89).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,90).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,91).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,92).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,93).toString()+"</td>"+
                        "</tr>");
                }
                LoadHTML.setText(
                    "<html>"+
                      "<table width='7500px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
                       htmlContent.toString()+
                      "</table>"+
                    "</html>"
                );

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

                File f = new File("PenilaianAwalMdisHemodialisa.html");            
                BufferedWriter bw = new BufferedWriter(new FileWriter(f));            
                bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                            "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                            "<table width='7500px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                "<tr class='isi2'>"+
                                    "<td valign='top' align='center'>"+
                                        "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                        akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                        akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                        "<font size='2' face='Tahoma'>DATA PENGKAJIAN AWAL HEMODIALISA<br><br></font>"+        
                                    "</td>"+
                               "</tr>"+
                            "</table>")
                );
                bw.close();                         
                Desktop.getDesktop().browse(f.toURI());
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
            Valid.pindah(evt, BtnCari, TPasien);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void tbObatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbObatMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
                System.out.println("Notif : "+e);
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

    private void MnPenilaianMedisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPenilaianMedisActionPerformed
        if(tbObat.getSelectedRow()>-1){
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());          
            param.put("logo",Sequel.cariGambar("select logo from setting")); 
            finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
            param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbObat.getValueAt(tbObat.getSelectedRow(),6).toString()+"\nID "+(finger.equals("")?tbObat.getValueAt(tbObat.getSelectedRow(),5).toString():finger)+"\n"+Valid.SetTgl3(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString())); 
            Valid.MyReportqry("rptCetakPenilaianAwalMedisHemodialisa.jasper","report","::[ Laporan Pengkajian Awal Medis Hemodialisa]::",
                "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penilaian_medis_hemodialisa.tanggal,"+
                "penilaian_medis_hemodialisa.kd_dokter,penilaian_medis_hemodialisa.anamnesis,penilaian_medis_hemodialisa.hubungan,penilaian_medis_hemodialisa.ruangan,"+
                "penilaian_medis_hemodialisa.alergi,penilaian_medis_hemodialisa.nyeri,penilaian_medis_hemodialisa.status_nutrisi,penilaian_medis_hemodialisa.hipertensi,"+
                "penilaian_medis_hemodialisa.keterangan_hipertensi,penilaian_medis_hemodialisa.diabetes,penilaian_medis_hemodialisa.keterangan_diabetes,"+
                "penilaian_medis_hemodialisa.batu_saluran_kemih,penilaian_medis_hemodialisa.keterangan_batu_saluran_kemih,penilaian_medis_hemodialisa.operasi_saluran_kemih,"+
                "penilaian_medis_hemodialisa.keterangan_operasi_saluran_kemih,penilaian_medis_hemodialisa.infeksi_saluran_kemih,penilaian_medis_hemodialisa.keterangan_infeksi_saluran_kemih,"+
                "penilaian_medis_hemodialisa.bengkak_seluruh_tubuh,penilaian_medis_hemodialisa.keterangan_bengkak_seluruh_tubuh,penilaian_medis_hemodialisa.urin_berdarah,"+
                "penilaian_medis_hemodialisa.keterangan_urin_berdarah,penilaian_medis_hemodialisa.penyakit_ginjal_laom,penilaian_medis_hemodialisa.keterangan_penyakit_ginjal_laom,"+
                "penilaian_medis_hemodialisa.penyakit_lain,penilaian_medis_hemodialisa.keterangan_penyakit_lain,penilaian_medis_hemodialisa.konsumsi_obat_nefro,"+
                "penilaian_medis_hemodialisa.keterangan_konsumsi_obat_nefro,penilaian_medis_hemodialisa.dialisis_pertama,penilaian_medis_hemodialisa.pernah_cpad,"+
                "penilaian_medis_hemodialisa.tanggal_cpad,penilaian_medis_hemodialisa.pernah_transplantasi,penilaian_medis_hemodialisa.tanggal_transplantasi,"+
                "penilaian_medis_hemodialisa.keadaan_umum,penilaian_medis_hemodialisa.kesadaran,penilaian_medis_hemodialisa.nadi,penilaian_medis_hemodialisa.bb,"+
                "penilaian_medis_hemodialisa.td,penilaian_medis_hemodialisa.suhu,penilaian_medis_hemodialisa.napas,penilaian_medis_hemodialisa.tb,"+
                "penilaian_medis_hemodialisa.hepatomegali,penilaian_medis_hemodialisa.splenomegali,penilaian_medis_hemodialisa.ascites,penilaian_medis_hemodialisa.edema,"+
                "penilaian_medis_hemodialisa.whezzing,penilaian_medis_hemodialisa.ronchi,penilaian_medis_hemodialisa.ikterik,penilaian_medis_hemodialisa.tekanan_vena,"+
                "penilaian_medis_hemodialisa.anemia,penilaian_medis_hemodialisa.kardiomegali,penilaian_medis_hemodialisa.bising,penilaian_medis_hemodialisa.thorax,"+
                "penilaian_medis_hemodialisa.tanggal_thorax,penilaian_medis_hemodialisa.ekg,penilaian_medis_hemodialisa.tanggal_ekg,penilaian_medis_hemodialisa.bno,"+
                "penilaian_medis_hemodialisa.tanggal_bno,penilaian_medis_hemodialisa.usg,penilaian_medis_hemodialisa.tanggal_usg,penilaian_medis_hemodialisa.renogram,"+
                "penilaian_medis_hemodialisa.tanggal_renogram,penilaian_medis_hemodialisa.biopsi,penilaian_medis_hemodialisa.tanggal_biopsi,penilaian_medis_hemodialisa.ctscan,"+
                "penilaian_medis_hemodialisa.tanggal_ctscan,penilaian_medis_hemodialisa.arteriografi,penilaian_medis_hemodialisa.tanggal_arteriografi,"+
                "penilaian_medis_hemodialisa.kultur_urin,penilaian_medis_hemodialisa.tanggal_kultur_urin,penilaian_medis_hemodialisa.laborat,penilaian_medis_hemodialisa.tanggal_laborat,"+
                "penilaian_medis_hemodialisa.hematokrit,penilaian_medis_hemodialisa.hemoglobin,penilaian_medis_hemodialisa.leukosit,penilaian_medis_hemodialisa.trombosit,"+
                "penilaian_medis_hemodialisa.hitung_jenis,penilaian_medis_hemodialisa.ureum,penilaian_medis_hemodialisa.urin_lengkap,penilaian_medis_hemodialisa.kreatinin,"+
                "penilaian_medis_hemodialisa.cct,penilaian_medis_hemodialisa.sgot,penilaian_medis_hemodialisa.sgpt,penilaian_medis_hemodialisa.ct,penilaian_medis_hemodialisa.asam_urat,"+
                "penilaian_medis_hemodialisa.hbsag,penilaian_medis_hemodialisa.anti_hcv,penilaian_medis_hemodialisa.edukasi,dokter.nm_dokter "+
                "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join penilaian_medis_hemodialisa on reg_periksa.no_rawat=penilaian_medis_hemodialisa.no_rawat "+
                "inner join dokter on penilaian_medis_hemodialisa.kd_dokter=dokter.kd_dokter where penilaian_medis_hemodialisa.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'",param);
        }
    }//GEN-LAST:event_MnPenilaianMedisActionPerformed

    private void BBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BBKeyPressed
        Valid.pindah(evt,Nadi,TD);
    }//GEN-LAST:event_BBKeyPressed

    private void TBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TBKeyPressed
        Valid.pindah(evt,Napas,Hepatomegali);
    }//GEN-LAST:event_TBKeyPressed

    private void KesadaranKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KesadaranKeyPressed
        Valid.pindah(evt,KeadaanUmum,Nadi);
    }//GEN-LAST:event_KesadaranKeyPressed

    private void KeadaanUmumKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeadaanUmumKeyPressed
        Valid.pindah(evt,TglTransplantasi,Kesadaran);
    }//GEN-LAST:event_KeadaanUmumKeyPressed

    private void TglTransplantasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglTransplantasiKeyPressed
        Valid.pindah2(evt,Transplantasi,KeadaanUmum);
    }//GEN-LAST:event_TglTransplantasiKeyPressed

    private void TglCAPDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglCAPDKeyPressed
        Valid.pindah2(evt,CAPD,Transplantasi);
    }//GEN-LAST:event_TglCAPDKeyPressed

    private void KeteranganKonsumsiObatNefrotoksisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKonsumsiObatNefrotoksisKeyPressed
        Valid.pindah(evt,KonsumsiObatNefrotoksis,TglDialisis);
    }//GEN-LAST:event_KeteranganKonsumsiObatNefrotoksisKeyPressed

    private void KeteranganPenyakitLainKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganPenyakitLainKeyPressed
        Valid.pindah(evt,PenyakitLain,KonsumsiObatNefrotoksis);
    }//GEN-LAST:event_KeteranganPenyakitLainKeyPressed

    private void KeteranganPenyakitGinjalLaomKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganPenyakitGinjalLaomKeyPressed
        Valid.pindah(evt,PenyakitGinjalLaom,PenyakitLain);
    }//GEN-LAST:event_KeteranganPenyakitGinjalLaomKeyPressed

    private void KeteranganUrinBerdarahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganUrinBerdarahKeyPressed
        Valid.pindah(evt,UrinBerdarah,PenyakitGinjalLaom);
    }//GEN-LAST:event_KeteranganUrinBerdarahKeyPressed

    private void KeteranganBengkakSeluruhTubuhKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganBengkakSeluruhTubuhKeyPressed
        Valid.pindah(evt,BengkakSeluruhTubuh,UrinBerdarah);
    }//GEN-LAST:event_KeteranganBengkakSeluruhTubuhKeyPressed

    private void KeteranganInfeksiSaluranKemihKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganInfeksiSaluranKemihKeyPressed
        Valid.pindah(evt,InfeksiSaluranKemih,BengkakSeluruhTubuh);
    }//GEN-LAST:event_KeteranganInfeksiSaluranKemihKeyPressed

    private void KeteranganOperasiSaluranKemihKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganOperasiSaluranKemihKeyPressed
        Valid.pindah(evt,OperasiSaluranKemih,InfeksiSaluranKemih);
    }//GEN-LAST:event_KeteranganOperasiSaluranKemihKeyPressed

    private void KeteranganBatuSaluranKemihKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganBatuSaluranKemihKeyPressed
        Valid.pindah(evt,BatuSaluranKemih,OperasiSaluranKemih);
    }//GEN-LAST:event_KeteranganBatuSaluranKemihKeyPressed

    private void KeteranganDiabetesMelitusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganDiabetesMelitusKeyPressed
        Valid.pindah(evt,DiabetesMelitus,BatuSaluranKemih);
    }//GEN-LAST:event_KeteranganDiabetesMelitusKeyPressed

    private void KeteranganHipertensiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganHipertensiKeyPressed
        Valid.pindah(evt,Hipertensi,DiabetesMelitus);
    }//GEN-LAST:event_KeteranganHipertensiKeyPressed

    private void AlergiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlergiKeyPressed
        Valid.pindah(evt,AsalRuangan,Nyeri);
    }//GEN-LAST:event_AlergiKeyPressed

    private void AsalRuanganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AsalRuanganKeyPressed
        Valid.pindah(evt,Hubungan,Alergi);
    }//GEN-LAST:event_AsalRuanganKeyPressed

    private void AntiHCVKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AntiHCVKeyPressed
        Valid.pindah(evt,HbsAg,Edukasi);
    }//GEN-LAST:event_AntiHCVKeyPressed

    private void HbsAgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HbsAgKeyPressed
        Valid.pindah(evt,AsamUrat,AntiHCV);
    }//GEN-LAST:event_HbsAgKeyPressed

    private void CCTKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CCTKeyPressed
        Valid.pindah(evt,Kreatinin,SGOT);
    }//GEN-LAST:event_CCTKeyPressed

    private void UrinLengkapKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_UrinLengkapKeyPressed
        Valid.pindah(evt,Ureum,Kreatinin);
    }//GEN-LAST:event_UrinLengkapKeyPressed

    private void CTKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CTKeyPressed
        Valid.pindah(evt,SGPT,AsamUrat);
    }//GEN-LAST:event_CTKeyPressed

    private void SGPTKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SGPTKeyPressed
        Valid.pindah(evt,SGOT,CT);
    }//GEN-LAST:event_SGPTKeyPressed

    private void SGOTKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SGOTKeyPressed
        Valid.pindah(evt,CCT,SGPT);
    }//GEN-LAST:event_SGOTKeyPressed

    private void AsamUratKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AsamUratKeyPressed
        Valid.pindah(evt,CT,HbsAg);
    }//GEN-LAST:event_AsamUratKeyPressed

    private void KreatininKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KreatininKeyPressed
        Valid.pindah(evt,UrinLengkap,CCT);
    }//GEN-LAST:event_KreatininKeyPressed

    private void UreumKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_UreumKeyPressed
        Valid.pindah(evt,HitungJenis,UrinLengkap);
    }//GEN-LAST:event_UreumKeyPressed

    private void HitungJenisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HitungJenisKeyPressed
        Valid.pindah(evt,Trombosit,Ureum);
    }//GEN-LAST:event_HitungJenisKeyPressed

    private void TrombositKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TrombositKeyPressed
        Valid.pindah(evt,Leukosit,HitungJenis);
    }//GEN-LAST:event_TrombositKeyPressed

    private void LeukositKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LeukositKeyPressed
        Valid.pindah(evt,Hemoglobin,Trombosit);
    }//GEN-LAST:event_LeukositKeyPressed

    private void HemoglobinKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HemoglobinKeyPressed
        Valid.pindah(evt,Hematokrit,Leukosit);
    }//GEN-LAST:event_HemoglobinKeyPressed

    private void HematokritKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HematokritKeyPressed
        Valid.pindah(evt,TglLaboratorium,Hemoglobin);
    }//GEN-LAST:event_HematokritKeyPressed

    private void TglBiopsiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglBiopsiKeyPressed
        Valid.pindah2(evt,TglRenogram,TglCTscan);
    }//GEN-LAST:event_TglBiopsiKeyPressed

    private void TglCTscanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglCTscanKeyPressed
        Valid.pindah2(evt,TglBiopsi,TglArteriografi);
    }//GEN-LAST:event_TglCTscanKeyPressed

    private void TglArteriografiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglArteriografiKeyPressed
        Valid.pindah2(evt,TglCTscan,TglKultururin);
    }//GEN-LAST:event_TglArteriografiKeyPressed

    private void TglKultururinKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglKultururinKeyPressed
        Valid.pindah2(evt,TglArteriografi,TglLaboratorium);
    }//GEN-LAST:event_TglKultururinKeyPressed

    private void TglLaboratoriumKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglLaboratoriumKeyPressed
        Valid.pindah2(evt,TglKultururin,Hematokrit);
    }//GEN-LAST:event_TglLaboratoriumKeyPressed

    private void TglRenogramKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglRenogramKeyPressed
        Valid.pindah2(evt,TglUSG,TglBiopsi);
    }//GEN-LAST:event_TglRenogramKeyPressed

    private void TglUSGKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglUSGKeyPressed
        Valid.pindah2(evt,TglBNO,TglRenogram);
    }//GEN-LAST:event_TglUSGKeyPressed

    private void TglBNOKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglBNOKeyPressed
        Valid.pindah2(evt,TglEKG,TglUSG);
    }//GEN-LAST:event_TglBNOKeyPressed

    private void TglEKGKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglEKGKeyPressed
        Valid.pindah2(evt,TglThorax,TglBNO);
    }//GEN-LAST:event_TglEKGKeyPressed

    private void TglDialisisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglDialisisKeyPressed
        Valid.pindah2(evt,KeteranganKonsumsiObatNefrotoksis,CAPD);
    }//GEN-LAST:event_TglDialisisKeyPressed

    private void TglThoraxKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglThoraxKeyPressed
        Valid.pindah2(evt,Bising,TglEKG);
    }//GEN-LAST:event_TglThoraxKeyPressed

    private void EdemaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EdemaKeyPressed
        Valid.pindah(evt,Ascites,Whezzing);
    }//GEN-LAST:event_EdemaKeyPressed

    private void AscitesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AscitesKeyPressed
        Valid.pindah(evt,Splenomegali,Edema);
    }//GEN-LAST:event_AscitesKeyPressed

    private void SplenomegaliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SplenomegaliKeyPressed
        Valid.pindah(evt,Hepatomegali,Ascites);
    }//GEN-LAST:event_SplenomegaliKeyPressed

    private void HepatomegaliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HepatomegaliKeyPressed
        Valid.pindah(evt,TB,Splenomegali);
    }//GEN-LAST:event_HepatomegaliKeyPressed

    private void RonchiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RonchiKeyPressed
        Valid.pindah(evt,Whezzing,Ikterik);
    }//GEN-LAST:event_RonchiKeyPressed

    private void WhezzingKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_WhezzingKeyPressed
        Valid.pindah(evt,Edema,Ronchi);
    }//GEN-LAST:event_WhezzingKeyPressed

    private void BisingKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BisingKeyPressed
        Valid.pindah(evt,Kardiomegali,TglThorax);
    }//GEN-LAST:event_BisingKeyPressed

    private void KardiomegaliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KardiomegaliKeyPressed
        Valid.pindah(evt,Konjungtiva,Bising);
    }//GEN-LAST:event_KardiomegaliKeyPressed

    private void TekananVenaJugularisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TekananVenaJugularisKeyPressed
        Valid.pindah(evt,Ikterik,Konjungtiva);
    }//GEN-LAST:event_TekananVenaJugularisKeyPressed

    private void KonjungtivaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KonjungtivaKeyPressed
        Valid.pindah(evt,TekananVenaJugularis,Kardiomegali);
    }//GEN-LAST:event_KonjungtivaKeyPressed

    private void IkterikKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_IkterikKeyPressed
        Valid.pindah(evt,Ronchi,TekananVenaJugularis);
    }//GEN-LAST:event_IkterikKeyPressed

    private void NapasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NapasKeyPressed
        Valid.pindah(evt,Suhu,TB);
    }//GEN-LAST:event_NapasKeyPressed

    private void KonsumsiObatNefrotoksisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KonsumsiObatNefrotoksisKeyPressed
        Valid.pindah(evt,KeteranganPenyakitLain,KeteranganKonsumsiObatNefrotoksis);
    }//GEN-LAST:event_KonsumsiObatNefrotoksisKeyPressed

    private void PenyakitLainKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenyakitLainKeyPressed
        Valid.pindah(evt,KeteranganPenyakitGinjalLaom,KeteranganPenyakitLain);
    }//GEN-LAST:event_PenyakitLainKeyPressed

    private void TransplantasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TransplantasiKeyPressed
        Valid.pindah(evt,TglCAPD,TglTransplantasi);
    }//GEN-LAST:event_TransplantasiKeyPressed

    private void CAPDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CAPDKeyPressed
        Valid.pindah(evt,TglDialisis,TglCAPD);
    }//GEN-LAST:event_CAPDKeyPressed

    private void UrinBerdarahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_UrinBerdarahKeyPressed
        Valid.pindah(evt,KeteranganBengkakSeluruhTubuh,KeteranganUrinBerdarah);
    }//GEN-LAST:event_UrinBerdarahKeyPressed

    private void EdukasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EdukasiKeyPressed
        Valid.pindah2(evt,AntiHCV,BtnSimpan);
    }//GEN-LAST:event_EdukasiKeyPressed

    private void NyeriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NyeriKeyPressed
        Valid.pindah(evt,Alergi,StatusNutrisi);
    }//GEN-LAST:event_NyeriKeyPressed

    private void TglAsuhanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglAsuhanKeyPressed
        Valid.pindah(evt,Edukasi,Anamnesis);
    }//GEN-LAST:event_TglAsuhanKeyPressed

    private void TglAsuhanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TglAsuhanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglAsuhanActionPerformed

    private void PenyakitGinjalLaomKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenyakitGinjalLaomKeyPressed
        Valid.pindah(evt,KeteranganUrinBerdarah,KeteranganPenyakitGinjalLaom);
    }//GEN-LAST:event_PenyakitGinjalLaomKeyPressed

    private void OperasiSaluranKemihKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_OperasiSaluranKemihKeyPressed
        Valid.pindah(evt,KeteranganBatuSaluranKemih,KeteranganOperasiSaluranKemih);
    }//GEN-LAST:event_OperasiSaluranKemihKeyPressed

    private void OperasiSaluranKemihActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OperasiSaluranKemihActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_OperasiSaluranKemihActionPerformed

    private void BatuSaluranKemihKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BatuSaluranKemihKeyPressed
        Valid.pindah(evt,KeteranganDiabetesMelitus,KeteranganBatuSaluranKemih);
    }//GEN-LAST:event_BatuSaluranKemihKeyPressed

    private void HipertensiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HipertensiKeyPressed
        Valid.pindah(evt,Nyeri,KeteranganHipertensi);
    }//GEN-LAST:event_HipertensiKeyPressed

    private void BengkakSeluruhTubuhKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BengkakSeluruhTubuhKeyPressed
        Valid.pindah(evt,KeteranganInfeksiSaluranKemih,KeteranganBengkakSeluruhTubuh);
    }//GEN-LAST:event_BengkakSeluruhTubuhKeyPressed

    private void InfeksiSaluranKemihKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_InfeksiSaluranKemihKeyPressed
        Valid.pindah(evt,KeteranganOperasiSaluranKemih,KeteranganInfeksiSaluranKemih);
    }//GEN-LAST:event_InfeksiSaluranKemihKeyPressed

    private void DiabetesMelitusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiabetesMelitusKeyPressed
        Valid.pindah(evt,KeteranganHipertensi,KeteranganDiabetesMelitus);
    }//GEN-LAST:event_DiabetesMelitusKeyPressed

    private void StatusNutrisiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_StatusNutrisiKeyPressed
        Valid.pindah(evt,Nyeri,Hipertensi);
    }//GEN-LAST:event_StatusNutrisiKeyPressed

    private void HubunganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HubunganKeyPressed
        Valid.pindah(evt,Anamnesis,AsalRuangan);
    }//GEN-LAST:event_HubunganKeyPressed

    private void AnamnesisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AnamnesisKeyPressed
        Valid.pindah(evt,TglAsuhan,Hubungan);
    }//GEN-LAST:event_AnamnesisKeyPressed

    private void TDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TDKeyPressed
        Valid.pindah(evt,BB,Suhu);
    }//GEN-LAST:event_TDKeyPressed

    private void SuhuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SuhuKeyPressed
        Valid.pindah(evt,TD,Napas);
    }//GEN-LAST:event_SuhuKeyPressed

    private void NadiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NadiKeyPressed
        Valid.pindah(evt,Kesadaran,BB);
    }//GEN-LAST:event_NadiKeyPressed

    private void BtnDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokterKeyPressed
        //Valid.pindah(evt,Monitoring,BtnSimpan);
    }//GEN-LAST:event_BtnDokterKeyPressed

    private void BtnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokterActionPerformed
        if (dokter == null || !dokter.isDisplayable()) {
            dokter=new DlgCariDokter(null,false);
            dokter.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            dokter.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    if(dokter.getTable().getSelectedRow()!= -1){        
                         KdDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
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

    private void KdDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdDokterKeyPressed

    }//GEN-LAST:event_KdDokterKeyPressed

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isRawat();
        }else{
            Valid.pindah(evt,TCari,BtnDokter);
        }
    }//GEN-LAST:event_TNoRwKeyPressed

    private void CAPDItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_CAPDItemStateChanged
        if(CAPD.getSelectedIndex()==0){
            TglCAPD.setEnabled(false);
        }else{
            TglCAPD.setEnabled(true);
        }
    }//GEN-LAST:event_CAPDItemStateChanged

    private void TransplantasiItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TransplantasiItemStateChanged
        if(Transplantasi.getSelectedIndex()==0){
            TglTransplantasi.setEnabled(false);
        }else{
            TglTransplantasi.setEnabled(true);
        }
    }//GEN-LAST:event_TransplantasiItemStateChanged

    private void ChkThoraxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ChkThoraxItemStateChanged
        if(ChkThorax.isSelected()==true){
            TglThorax.setEnabled(true);
        }else{
            TglThorax.setEnabled(false);
        }
    }//GEN-LAST:event_ChkThoraxItemStateChanged

    private void ChkEKGItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ChkEKGItemStateChanged
        if(ChkEKG.isSelected()==true){
            TglEKG.setEnabled(true);
        }else{
            TglEKG.setEnabled(false);
        }
    }//GEN-LAST:event_ChkEKGItemStateChanged

    private void ChkBNOItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ChkBNOItemStateChanged
        if(ChkBNO.isSelected()==true){
            TglBNO.setEnabled(true);
        }else{
            TglBNO.setEnabled(false);
        }
    }//GEN-LAST:event_ChkBNOItemStateChanged

    private void ChkUSGItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ChkUSGItemStateChanged
        if(ChkUSG.isSelected()==true){
            TglUSG.setEnabled(true);
        }else{
            TglUSG.setEnabled(false);
        }
    }//GEN-LAST:event_ChkUSGItemStateChanged

    private void ChkRenogramItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ChkRenogramItemStateChanged
        if(ChkRenogram.isSelected()==true){
            TglRenogram.setEnabled(true);
        }else{
            TglRenogram.setEnabled(false);
        }
    }//GEN-LAST:event_ChkRenogramItemStateChanged

    private void ChkBiopsiGinjalItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ChkBiopsiGinjalItemStateChanged
        if(ChkBiopsiGinjal.isSelected()==true){
            TglBiopsi.setEnabled(true);
        }else{
            TglBiopsi.setEnabled(false);
        }
    }//GEN-LAST:event_ChkBiopsiGinjalItemStateChanged

    private void ChkCTScanItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ChkCTScanItemStateChanged
        if(ChkCTScan.isSelected()==true){
            TglCTscan.setEnabled(true);
        }else{
            TglCTscan.setEnabled(false);
        }
    }//GEN-LAST:event_ChkCTScanItemStateChanged

    private void ChkArteriografiItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ChkArteriografiItemStateChanged
        if(ChkArteriografi.isSelected()==true){
            TglArteriografi.setEnabled(true);
        }else{
            TglArteriografi.setEnabled(false);
        }
    }//GEN-LAST:event_ChkArteriografiItemStateChanged

    private void ChkKulturUrinItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ChkKulturUrinItemStateChanged
        if(ChkKulturUrin.isSelected()==true){
            TglKultururin.setEnabled(true);
        }else{
            TglKultururin.setEnabled(false);
        }
    }//GEN-LAST:event_ChkKulturUrinItemStateChanged

    private void ChkLaboratItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ChkLaboratItemStateChanged
        if(ChkLaborat.isSelected()==true){
            TglLaboratorium.setEnabled(true);
        }else{
            TglLaboratorium.setEnabled(false);
        }
    }//GEN-LAST:event_ChkLaboratItemStateChanged

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMPenilaianAwalMedisHemodialisa dialog = new RMPenilaianAwalMedisHemodialisa(new javax.swing.JFrame(), true);
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
    private widget.TextBox Alergi;
    private widget.ComboBox Anamnesis;
    private widget.ComboBox AntiHCV;
    private widget.TextBox AsalRuangan;
    private widget.TextBox AsamUrat;
    private widget.ComboBox Ascites;
    private widget.TextBox BB;
    private widget.ComboBox BatuSaluranKemih;
    private widget.ComboBox BengkakSeluruhTubuh;
    private widget.ComboBox Bising;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnDokter;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.ComboBox CAPD;
    private widget.TextBox CCT;
    private widget.TextBox CT;
    private widget.CekBox ChkArteriografi;
    private widget.CekBox ChkBNO;
    private widget.CekBox ChkBiopsiGinjal;
    private widget.CekBox ChkCTScan;
    private widget.CekBox ChkEKG;
    private widget.CekBox ChkKulturUrin;
    private widget.CekBox ChkLaborat;
    private widget.CekBox ChkRenogram;
    private widget.CekBox ChkThorax;
    private widget.CekBox ChkUSG;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.ComboBox DiabetesMelitus;
    private widget.ComboBox Edema;
    private widget.TextArea Edukasi;
    private widget.PanelBiasa FormInput;
    private widget.ComboBox HbsAg;
    private widget.TextBox Hematokrit;
    private widget.TextBox Hemoglobin;
    private widget.ComboBox Hepatomegali;
    private widget.ComboBox Hipertensi;
    private widget.TextBox HitungJenis;
    private widget.TextBox Hubungan;
    private widget.ComboBox Ikterik;
    private widget.ComboBox InfeksiSaluranKemih;
    private widget.TextBox Jk;
    private widget.ComboBox Kardiomegali;
    private widget.TextBox KdDokter;
    private widget.ComboBox KeadaanUmum;
    private widget.ComboBox Kesadaran;
    private widget.TextBox KeteranganBatuSaluranKemih;
    private widget.TextBox KeteranganBengkakSeluruhTubuh;
    private widget.TextBox KeteranganDiabetesMelitus;
    private widget.TextBox KeteranganHipertensi;
    private widget.TextBox KeteranganInfeksiSaluranKemih;
    private widget.TextBox KeteranganKonsumsiObatNefrotoksis;
    private widget.TextBox KeteranganOperasiSaluranKemih;
    private widget.TextBox KeteranganPenyakitGinjalLaom;
    private widget.TextBox KeteranganPenyakitLain;
    private widget.TextBox KeteranganUrinBerdarah;
    private widget.ComboBox Konjungtiva;
    private widget.ComboBox KonsumsiObatNefrotoksis;
    private widget.TextBox Kreatinin;
    private widget.Label LCount;
    private widget.TextBox Leukosit;
    private widget.editorpane LoadHTML;
    private javax.swing.JMenuItem MnPenilaianMedis;
    private widget.TextBox Nadi;
    private widget.TextBox Napas;
    private widget.TextBox NmDokter;
    private widget.ComboBox Nyeri;
    private widget.ComboBox OperasiSaluranKemih;
    private widget.ComboBox PenyakitGinjalLaom;
    private widget.ComboBox PenyakitLain;
    private widget.ComboBox Ronchi;
    private widget.TextBox SGOT;
    private widget.TextBox SGPT;
    private widget.ScrollPane Scroll;
    private widget.ComboBox Splenomegali;
    private widget.TextBox StatusNutrisi;
    private widget.TextBox Suhu;
    private widget.TextBox TB;
    private widget.TextBox TCari;
    private widget.TextBox TD;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private javax.swing.JTabbedPane TabRawat;
    private widget.TextBox TanggalRegistrasi;
    private widget.ComboBox TekananVenaJugularis;
    private widget.Tanggal TglArteriografi;
    private widget.Tanggal TglAsuhan;
    private widget.Tanggal TglBNO;
    private widget.Tanggal TglBiopsi;
    private widget.Tanggal TglCAPD;
    private widget.Tanggal TglCTscan;
    private widget.Tanggal TglDialisis;
    private widget.Tanggal TglEKG;
    private widget.Tanggal TglKultururin;
    private widget.Tanggal TglLaboratorium;
    private widget.TextBox TglLahir;
    private widget.Tanggal TglRenogram;
    private widget.Tanggal TglThorax;
    private widget.Tanggal TglTransplantasi;
    private widget.Tanggal TglUSG;
    private widget.ComboBox Transplantasi;
    private widget.TextBox Trombosit;
    private widget.TextBox Ureum;
    private widget.ComboBox UrinBerdarah;
    private widget.TextBox UrinLengkap;
    private widget.ComboBox Whezzing;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.Label jLabel10;
    private widget.Label jLabel101;
    private widget.Label jLabel102;
    private widget.Label jLabel11;
    private widget.Label jLabel113;
    private widget.Label jLabel12;
    private widget.Label jLabel13;
    private widget.Label jLabel15;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
    private widget.Label jLabel18;
    private widget.Label jLabel19;
    private widget.Label jLabel20;
    private widget.Label jLabel21;
    private widget.Label jLabel22;
    private widget.Label jLabel23;
    private widget.Label jLabel24;
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
    private widget.Label jLabel44;
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
    private widget.Label jLabel8;
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
    private widget.Label jLabel9;
    private widget.Label jLabel90;
    private widget.Label jLabel91;
    private widget.Label jLabel92;
    private widget.Label jLabel93;
    private widget.Label jLabel94;
    private widget.Label jLabel95;
    private widget.Label jLabel96;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private widget.Label label11;
    private widget.Label label14;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollInput;
    private widget.ScrollPane scrollPane14;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            if(TCari.getText().trim().equals("")){
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penilaian_medis_hemodialisa.tanggal,"+
                        "penilaian_medis_hemodialisa.kd_dokter,penilaian_medis_hemodialisa.anamnesis,penilaian_medis_hemodialisa.hubungan,penilaian_medis_hemodialisa.ruangan,"+
                        "penilaian_medis_hemodialisa.alergi,penilaian_medis_hemodialisa.nyeri,penilaian_medis_hemodialisa.status_nutrisi,penilaian_medis_hemodialisa.hipertensi,"+
                        "penilaian_medis_hemodialisa.keterangan_hipertensi,penilaian_medis_hemodialisa.diabetes,penilaian_medis_hemodialisa.keterangan_diabetes,"+
                        "penilaian_medis_hemodialisa.batu_saluran_kemih,penilaian_medis_hemodialisa.keterangan_batu_saluran_kemih,penilaian_medis_hemodialisa.operasi_saluran_kemih,"+
                        "penilaian_medis_hemodialisa.keterangan_operasi_saluran_kemih,penilaian_medis_hemodialisa.infeksi_saluran_kemih,penilaian_medis_hemodialisa.keterangan_infeksi_saluran_kemih,"+
                        "penilaian_medis_hemodialisa.bengkak_seluruh_tubuh,penilaian_medis_hemodialisa.keterangan_bengkak_seluruh_tubuh,penilaian_medis_hemodialisa.urin_berdarah,"+
                        "penilaian_medis_hemodialisa.keterangan_urin_berdarah,penilaian_medis_hemodialisa.penyakit_ginjal_laom,penilaian_medis_hemodialisa.keterangan_penyakit_ginjal_laom,"+
                        "penilaian_medis_hemodialisa.penyakit_lain,penilaian_medis_hemodialisa.keterangan_penyakit_lain,penilaian_medis_hemodialisa.konsumsi_obat_nefro,"+
                        "penilaian_medis_hemodialisa.keterangan_konsumsi_obat_nefro,penilaian_medis_hemodialisa.dialisis_pertama,penilaian_medis_hemodialisa.pernah_cpad,"+
                        "penilaian_medis_hemodialisa.tanggal_cpad,penilaian_medis_hemodialisa.pernah_transplantasi,penilaian_medis_hemodialisa.tanggal_transplantasi,"+
                        "penilaian_medis_hemodialisa.keadaan_umum,penilaian_medis_hemodialisa.kesadaran,penilaian_medis_hemodialisa.nadi,penilaian_medis_hemodialisa.bb,"+
                        "penilaian_medis_hemodialisa.td,penilaian_medis_hemodialisa.suhu,penilaian_medis_hemodialisa.napas,penilaian_medis_hemodialisa.tb,"+
                        "penilaian_medis_hemodialisa.hepatomegali,penilaian_medis_hemodialisa.splenomegali,penilaian_medis_hemodialisa.ascites,penilaian_medis_hemodialisa.edema,"+
                        "penilaian_medis_hemodialisa.whezzing,penilaian_medis_hemodialisa.ronchi,penilaian_medis_hemodialisa.ikterik,penilaian_medis_hemodialisa.tekanan_vena,"+
                        "penilaian_medis_hemodialisa.anemia,penilaian_medis_hemodialisa.kardiomegali,penilaian_medis_hemodialisa.bising,penilaian_medis_hemodialisa.thorax,"+
                        "penilaian_medis_hemodialisa.tanggal_thorax,penilaian_medis_hemodialisa.ekg,penilaian_medis_hemodialisa.tanggal_ekg,penilaian_medis_hemodialisa.bno,"+
                        "penilaian_medis_hemodialisa.tanggal_bno,penilaian_medis_hemodialisa.usg,penilaian_medis_hemodialisa.tanggal_usg,penilaian_medis_hemodialisa.renogram,"+
                        "penilaian_medis_hemodialisa.tanggal_renogram,penilaian_medis_hemodialisa.biopsi,penilaian_medis_hemodialisa.tanggal_biopsi,penilaian_medis_hemodialisa.ctscan,"+
                        "penilaian_medis_hemodialisa.tanggal_ctscan,penilaian_medis_hemodialisa.arteriografi,penilaian_medis_hemodialisa.tanggal_arteriografi,"+
                        "penilaian_medis_hemodialisa.kultur_urin,penilaian_medis_hemodialisa.tanggal_kultur_urin,penilaian_medis_hemodialisa.laborat,penilaian_medis_hemodialisa.tanggal_laborat,"+
                        "penilaian_medis_hemodialisa.hematokrit,penilaian_medis_hemodialisa.hemoglobin,penilaian_medis_hemodialisa.leukosit,penilaian_medis_hemodialisa.trombosit,"+
                        "penilaian_medis_hemodialisa.hitung_jenis,penilaian_medis_hemodialisa.ureum,penilaian_medis_hemodialisa.urin_lengkap,penilaian_medis_hemodialisa.kreatinin,"+
                        "penilaian_medis_hemodialisa.cct,penilaian_medis_hemodialisa.sgot,penilaian_medis_hemodialisa.sgpt,penilaian_medis_hemodialisa.ct,penilaian_medis_hemodialisa.asam_urat,"+
                        "penilaian_medis_hemodialisa.hbsag,penilaian_medis_hemodialisa.anti_hcv,penilaian_medis_hemodialisa.edukasi,dokter.nm_dokter "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join penilaian_medis_hemodialisa on reg_periksa.no_rawat=penilaian_medis_hemodialisa.no_rawat "+
                        "inner join dokter on penilaian_medis_hemodialisa.kd_dokter=dokter.kd_dokter where "+
                        "penilaian_medis_hemodialisa.tanggal between ? and ? order by penilaian_medis_hemodialisa.tanggal");
            }else{
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penilaian_medis_hemodialisa.tanggal,"+
                        "penilaian_medis_hemodialisa.kd_dokter,penilaian_medis_hemodialisa.anamnesis,penilaian_medis_hemodialisa.hubungan,penilaian_medis_hemodialisa.ruangan,"+
                        "penilaian_medis_hemodialisa.alergi,penilaian_medis_hemodialisa.nyeri,penilaian_medis_hemodialisa.status_nutrisi,penilaian_medis_hemodialisa.hipertensi,"+
                        "penilaian_medis_hemodialisa.keterangan_hipertensi,penilaian_medis_hemodialisa.diabetes,penilaian_medis_hemodialisa.keterangan_diabetes,"+
                        "penilaian_medis_hemodialisa.batu_saluran_kemih,penilaian_medis_hemodialisa.keterangan_batu_saluran_kemih,penilaian_medis_hemodialisa.operasi_saluran_kemih,"+
                        "penilaian_medis_hemodialisa.keterangan_operasi_saluran_kemih,penilaian_medis_hemodialisa.infeksi_saluran_kemih,penilaian_medis_hemodialisa.keterangan_infeksi_saluran_kemih,"+
                        "penilaian_medis_hemodialisa.bengkak_seluruh_tubuh,penilaian_medis_hemodialisa.keterangan_bengkak_seluruh_tubuh,penilaian_medis_hemodialisa.urin_berdarah,"+
                        "penilaian_medis_hemodialisa.keterangan_urin_berdarah,penilaian_medis_hemodialisa.penyakit_ginjal_laom,penilaian_medis_hemodialisa.keterangan_penyakit_ginjal_laom,"+
                        "penilaian_medis_hemodialisa.penyakit_lain,penilaian_medis_hemodialisa.keterangan_penyakit_lain,penilaian_medis_hemodialisa.konsumsi_obat_nefro,"+
                        "penilaian_medis_hemodialisa.keterangan_konsumsi_obat_nefro,penilaian_medis_hemodialisa.dialisis_pertama,penilaian_medis_hemodialisa.pernah_cpad,"+
                        "penilaian_medis_hemodialisa.tanggal_cpad,penilaian_medis_hemodialisa.pernah_transplantasi,penilaian_medis_hemodialisa.tanggal_transplantasi,"+
                        "penilaian_medis_hemodialisa.keadaan_umum,penilaian_medis_hemodialisa.kesadaran,penilaian_medis_hemodialisa.nadi,penilaian_medis_hemodialisa.bb,"+
                        "penilaian_medis_hemodialisa.td,penilaian_medis_hemodialisa.suhu,penilaian_medis_hemodialisa.napas,penilaian_medis_hemodialisa.tb,"+
                        "penilaian_medis_hemodialisa.hepatomegali,penilaian_medis_hemodialisa.splenomegali,penilaian_medis_hemodialisa.ascites,penilaian_medis_hemodialisa.edema,"+
                        "penilaian_medis_hemodialisa.whezzing,penilaian_medis_hemodialisa.ronchi,penilaian_medis_hemodialisa.ikterik,penilaian_medis_hemodialisa.tekanan_vena,"+
                        "penilaian_medis_hemodialisa.anemia,penilaian_medis_hemodialisa.kardiomegali,penilaian_medis_hemodialisa.bising,penilaian_medis_hemodialisa.thorax,"+
                        "penilaian_medis_hemodialisa.tanggal_thorax,penilaian_medis_hemodialisa.ekg,penilaian_medis_hemodialisa.tanggal_ekg,penilaian_medis_hemodialisa.bno,"+
                        "penilaian_medis_hemodialisa.tanggal_bno,penilaian_medis_hemodialisa.usg,penilaian_medis_hemodialisa.tanggal_usg,penilaian_medis_hemodialisa.renogram,"+
                        "penilaian_medis_hemodialisa.tanggal_renogram,penilaian_medis_hemodialisa.biopsi,penilaian_medis_hemodialisa.tanggal_biopsi,penilaian_medis_hemodialisa.ctscan,"+
                        "penilaian_medis_hemodialisa.tanggal_ctscan,penilaian_medis_hemodialisa.arteriografi,penilaian_medis_hemodialisa.tanggal_arteriografi,"+
                        "penilaian_medis_hemodialisa.kultur_urin,penilaian_medis_hemodialisa.tanggal_kultur_urin,penilaian_medis_hemodialisa.laborat,penilaian_medis_hemodialisa.tanggal_laborat,"+
                        "penilaian_medis_hemodialisa.hematokrit,penilaian_medis_hemodialisa.hemoglobin,penilaian_medis_hemodialisa.leukosit,penilaian_medis_hemodialisa.trombosit,"+
                        "penilaian_medis_hemodialisa.hitung_jenis,penilaian_medis_hemodialisa.ureum,penilaian_medis_hemodialisa.urin_lengkap,penilaian_medis_hemodialisa.kreatinin,"+
                        "penilaian_medis_hemodialisa.cct,penilaian_medis_hemodialisa.sgot,penilaian_medis_hemodialisa.sgpt,penilaian_medis_hemodialisa.ct,penilaian_medis_hemodialisa.asam_urat,"+
                        "penilaian_medis_hemodialisa.hbsag,penilaian_medis_hemodialisa.anti_hcv,penilaian_medis_hemodialisa.edukasi,dokter.nm_dokter "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join penilaian_medis_hemodialisa on reg_periksa.no_rawat=penilaian_medis_hemodialisa.no_rawat "+
                        "inner join dokter on penilaian_medis_hemodialisa.kd_dokter=dokter.kd_dokter where "+
                        "penilaian_medis_hemodialisa.tanggal between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or "+
                        "penilaian_medis_hemodialisa.kd_dokter like ? or dokter.nm_dokter like ?) order by penilaian_medis_hemodialisa.tanggal");
            }
                
            try {
                if(TCari.getText().trim().equals("")){
                    ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                    ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                }else{
                    ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                    ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                    ps.setString(3,"%"+TCari.getText()+"%");
                    ps.setString(4,"%"+TCari.getText()+"%");
                    ps.setString(5,"%"+TCari.getText()+"%");
                    ps.setString(6,"%"+TCari.getText()+"%");
                    ps.setString(7,"%"+TCari.getText()+"%");
                }   
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new Object[]{
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getDate("tgl_lahir"),rs.getString("jk"),rs.getString("kd_dokter"),rs.getString("nm_dokter"),rs.getString("tanggal"),
                        rs.getString("anamnesis"),rs.getString("hubungan"),rs.getString("ruangan"),rs.getString("alergi"),rs.getString("nyeri"),rs.getString("status_nutrisi"),rs.getString("hipertensi"),rs.getString("keterangan_hipertensi"),
                        rs.getString("diabetes"),rs.getString("keterangan_diabetes"),rs.getString("batu_saluran_kemih"),rs.getString("keterangan_batu_saluran_kemih"),rs.getString("operasi_saluran_kemih"),rs.getString("keterangan_operasi_saluran_kemih"),
                        rs.getString("infeksi_saluran_kemih"),rs.getString("keterangan_infeksi_saluran_kemih"),rs.getString("bengkak_seluruh_tubuh"),rs.getString("keterangan_bengkak_seluruh_tubuh"),rs.getString("urin_berdarah"),
                        rs.getString("keterangan_urin_berdarah"),rs.getString("penyakit_ginjal_laom"),rs.getString("keterangan_penyakit_ginjal_laom"),rs.getString("penyakit_lain"),rs.getString("keterangan_penyakit_lain"),
                        rs.getString("konsumsi_obat_nefro"),rs.getString("keterangan_konsumsi_obat_nefro"),rs.getString("dialisis_pertama"),rs.getString("pernah_cpad"),rs.getString("tanggal_cpad"),rs.getString("pernah_transplantasi"),
                        rs.getString("tanggal_transplantasi"),rs.getString("keadaan_umum"),rs.getString("kesadaran"),rs.getString("nadi"),rs.getString("bb"),rs.getString("td"),rs.getString("suhu"),rs.getString("napas"),rs.getString("tb"),
                        rs.getString("hepatomegali"),rs.getString("splenomegali"),rs.getString("ascites"),rs.getString("edema"),rs.getString("whezzing"),rs.getString("ronchi"),rs.getString("ikterik"),rs.getString("tekanan_vena"),
                        rs.getString("anemia"),rs.getString("kardiomegali"),rs.getString("bising"),rs.getString("thorax"),rs.getString("tanggal_thorax"),rs.getString("ekg"),rs.getString("tanggal_ekg"),rs.getString("bno"),rs.getString("tanggal_bno"),
                        rs.getString("usg"),rs.getString("tanggal_usg"),rs.getString("renogram"),rs.getString("tanggal_renogram"),rs.getString("biopsi"),rs.getString("tanggal_biopsi"),rs.getString("ctscan"),rs.getString("tanggal_ctscan"),
                        rs.getString("arteriografi"),rs.getString("tanggal_arteriografi"),rs.getString("kultur_urin"),rs.getString("tanggal_kultur_urin"),rs.getString("laborat"),rs.getString("tanggal_laborat"),rs.getString("hematokrit"),
                        rs.getString("hemoglobin"),rs.getString("leukosit"),rs.getString("trombosit"),rs.getString("hitung_jenis"),rs.getString("ureum"),rs.getString("urin_lengkap"),rs.getString("kreatinin"),rs.getString("cct"),rs.getString("sgot"),
                        rs.getString("sgpt"),rs.getString("ct"),rs.getString("asam_urat"),rs.getString("hbsag"),rs.getString("anti_hcv"),rs.getString("edukasi")
                    });
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
        Anamnesis.setSelectedIndex(0);
        Hubungan.setText("");
        AsalRuangan.setText("");
        Alergi.setText("");
        Nyeri.setSelectedIndex(0);
        StatusNutrisi.setText("");
        Hipertensi.setSelectedIndex(0);
        KeteranganHipertensi.setText("");
        DiabetesMelitus.setSelectedIndex(0);
        KeteranganDiabetesMelitus.setText("");
        BatuSaluranKemih.setSelectedIndex(0);
        KeteranganBatuSaluranKemih.setText("");
        OperasiSaluranKemih.setSelectedIndex(0);
        KeteranganOperasiSaluranKemih.setText("");
        InfeksiSaluranKemih.setSelectedIndex(0);
        KeteranganInfeksiSaluranKemih.setText("");
        BengkakSeluruhTubuh.setSelectedIndex(0);
        KeteranganBengkakSeluruhTubuh.setText("");
        UrinBerdarah.setSelectedIndex(0);
        KeteranganUrinBerdarah.setText("");
        PenyakitGinjalLaom.setSelectedIndex(0);
        KeteranganPenyakitGinjalLaom.setText("");
        PenyakitLain.setSelectedIndex(0);
        KeteranganPenyakitLain.setText("");
        KonsumsiObatNefrotoksis.setSelectedIndex(0);
        KeteranganKonsumsiObatNefrotoksis.setText("");
        TglDialisis.setDate(new Date());
        CAPD.setSelectedIndex(0);
        TglCAPD.setDate(new Date());
        Transplantasi.setSelectedIndex(0);
        TglTransplantasi.setDate(new Date());
        KeadaanUmum.setSelectedIndex(0);
        Kesadaran.setSelectedIndex(0);
        Nadi.setText("");
        BB.setText("");
        TD.setText("");
        Suhu.setText("");
        Napas.setText("");
        TB.setText("");
        Hepatomegali.setSelectedIndex(0);
        Splenomegali.setSelectedIndex(0);
        Ascites.setSelectedIndex(0);
        Edema.setSelectedIndex(0);
        Whezzing.setSelectedIndex(0);
        Ronchi.setSelectedIndex(0);
        Ikterik.setSelectedIndex(0);
        TekananVenaJugularis.setSelectedIndex(0);
        Konjungtiva.setSelectedIndex(0);
        Kardiomegali.setSelectedIndex(0);
        Bising.setSelectedIndex(0);
        ChkThorax.setSelected(false);
        TglThorax.setDate(new Date());
        ChkEKG.setSelected(false);
        TglEKG.setDate(new Date());
        ChkBNO.setSelected(false);
        TglBNO.setDate(new Date());
        ChkUSG.setSelected(false);
        TglUSG.setDate(new Date());
        ChkRenogram.setSelected(false);
        TglRenogram.setDate(new Date());
        ChkBiopsiGinjal.setSelected(false);
        TglBiopsi.setDate(new Date());
        ChkCTScan.setSelected(false);
        TglCTscan.setDate(new Date());
        ChkArteriografi.setSelected(false);
        TglArteriografi.setDate(new Date());
        ChkKulturUrin.setSelected(false);
        TglKultururin.setDate(new Date());
        ChkLaborat.setSelected(false);
        TglLaboratorium.setDate(new Date());
        Hematokrit.setText("");
        Hemoglobin.setText("");
        Leukosit.setText("");
        Trombosit.setText("");
        HitungJenis.setText("");
        Ureum.setText("");
        UrinLengkap.setText("");
        Kreatinin.setText("");
        CCT.setText("");
        SGOT.setText("");
        SGPT.setText("");
        CT.setText("");
        AsamUrat.setText("");
        HbsAg.setSelectedIndex(0);
        AntiHCV.setSelectedIndex(0);
        Edukasi.setText("");
        TabRawat.setSelectedIndex(0);
        Anamnesis.requestFocus();
    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()); 
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            Jk.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString()); 
            KdDokter.setText(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
            NmDokter.setText(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString());
            Valid.SetTgl2(TglAsuhan,tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());
            Anamnesis.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            Hubungan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            AsalRuangan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            Alergi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            Nyeri.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            StatusNutrisi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            Hipertensi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            KeteranganHipertensi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
            DiabetesMelitus.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            KeteranganDiabetesMelitus.setText(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());
            BatuSaluranKemih.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
            KeteranganBatuSaluranKemih.setText(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
            OperasiSaluranKemih.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString());
            KeteranganOperasiSaluranKemih.setText(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString());
            InfeksiSaluranKemih.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString());
            KeteranganInfeksiSaluranKemih.setText(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString());
            BengkakSeluruhTubuh.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),24).toString());
            KeteranganBengkakSeluruhTubuh.setText(tbObat.getValueAt(tbObat.getSelectedRow(),25).toString());
            UrinBerdarah.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),26).toString());
            KeteranganUrinBerdarah.setText(tbObat.getValueAt(tbObat.getSelectedRow(),27).toString());
            PenyakitGinjalLaom.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),28).toString());
            KeteranganPenyakitGinjalLaom.setText(tbObat.getValueAt(tbObat.getSelectedRow(),29).toString());
            PenyakitLain.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),30).toString());
            KeteranganPenyakitLain.setText(tbObat.getValueAt(tbObat.getSelectedRow(),31).toString());
            KonsumsiObatNefrotoksis.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),32).toString());
            KeteranganKonsumsiObatNefrotoksis.setText(tbObat.getValueAt(tbObat.getSelectedRow(),33).toString());
            Valid.SetTgl(TglDialisis,tbObat.getValueAt(tbObat.getSelectedRow(),34).toString());
            CAPD.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),35).toString());
            if(tbObat.getValueAt(tbObat.getSelectedRow(),35).toString().equals("Ya")){
                Valid.SetTgl(TglCAPD,tbObat.getValueAt(tbObat.getSelectedRow(),36).toString());
            }
            Transplantasi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),37).toString());
            if(tbObat.getValueAt(tbObat.getSelectedRow(),37).toString().equals("Ya")){
                Valid.SetTgl(TglTransplantasi,tbObat.getValueAt(tbObat.getSelectedRow(),38).toString());
            }
            KeadaanUmum.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),39).toString());
            Kesadaran.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),40).toString());
            Nadi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),41).toString());
            BB.setText(tbObat.getValueAt(tbObat.getSelectedRow(),42).toString());
            TD.setText(tbObat.getValueAt(tbObat.getSelectedRow(),43).toString());
            Suhu.setText(tbObat.getValueAt(tbObat.getSelectedRow(),44).toString());
            Napas.setText(tbObat.getValueAt(tbObat.getSelectedRow(),45).toString());
            TB.setText(tbObat.getValueAt(tbObat.getSelectedRow(),46).toString());
            Hepatomegali.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),47).toString());
            Splenomegali.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),48).toString());
            Ascites.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),49).toString());
            Edema.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),50).toString());
            Whezzing.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),51).toString());
            Ronchi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),52).toString());
            Ikterik.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),53).toString());
            TekananVenaJugularis.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),54).toString());
            Konjungtiva.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),55).toString());
            Kardiomegali.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),56).toString());
            Bising.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),57).toString());
            ChkThorax.setSelected((tbObat.getValueAt(tbObat.getSelectedRow(),58).toString().equals("Ya")?true:false));
            if(tbObat.getValueAt(tbObat.getSelectedRow(),58).toString().equals("Ya")){
                Valid.SetTgl(TglThorax,tbObat.getValueAt(tbObat.getSelectedRow(),59).toString());
            }  
            ChkEKG.setSelected((tbObat.getValueAt(tbObat.getSelectedRow(),60).toString().equals("Ya")?true:false));
            if(tbObat.getValueAt(tbObat.getSelectedRow(),60).toString().equals("Ya")){
                Valid.SetTgl(TglEKG,tbObat.getValueAt(tbObat.getSelectedRow(),61).toString());
            }  
            ChkBNO.setSelected((tbObat.getValueAt(tbObat.getSelectedRow(),62).toString().equals("Ya")?true:false));
            if(tbObat.getValueAt(tbObat.getSelectedRow(),62).toString().equals("Ya")){
                Valid.SetTgl(TglBNO,tbObat.getValueAt(tbObat.getSelectedRow(),63).toString());
            } 
            ChkUSG.setSelected((tbObat.getValueAt(tbObat.getSelectedRow(),64).toString().equals("Ya")?true:false));
            if(tbObat.getValueAt(tbObat.getSelectedRow(),64).toString().equals("Ya")){
                Valid.SetTgl(TglUSG,tbObat.getValueAt(tbObat.getSelectedRow(),65).toString());
            }   
            ChkRenogram.setSelected((tbObat.getValueAt(tbObat.getSelectedRow(),66).toString().equals("Ya")?true:false));
            if(tbObat.getValueAt(tbObat.getSelectedRow(),66).toString().equals("Ya")){
                Valid.SetTgl(TglRenogram,tbObat.getValueAt(tbObat.getSelectedRow(),67).toString());
            }
            ChkBiopsiGinjal.setSelected((tbObat.getValueAt(tbObat.getSelectedRow(),68).toString().equals("Ya")?true:false));
            if(tbObat.getValueAt(tbObat.getSelectedRow(),68).toString().equals("Ya")){
                Valid.SetTgl(TglBiopsi,tbObat.getValueAt(tbObat.getSelectedRow(),69).toString());
            }
            ChkCTScan.setSelected((tbObat.getValueAt(tbObat.getSelectedRow(),70).toString().equals("Ya")?true:false));
            if(tbObat.getValueAt(tbObat.getSelectedRow(),70).toString().equals("Ya")){
                Valid.SetTgl(TglCTscan,tbObat.getValueAt(tbObat.getSelectedRow(),71).toString());
            }
            ChkArteriografi.setSelected((tbObat.getValueAt(tbObat.getSelectedRow(),72).toString().equals("Ya")?true:false));
            if(tbObat.getValueAt(tbObat.getSelectedRow(),72).toString().equals("Ya")){
                Valid.SetTgl(TglArteriografi,tbObat.getValueAt(tbObat.getSelectedRow(),73).toString());
            }  
            ChkKulturUrin.setSelected((tbObat.getValueAt(tbObat.getSelectedRow(),74).toString().equals("Ya")?true:false));
            if(tbObat.getValueAt(tbObat.getSelectedRow(),74).toString().equals("Ya")){
                Valid.SetTgl(TglKultururin,tbObat.getValueAt(tbObat.getSelectedRow(),75).toString());
            }
            ChkLaborat.setSelected((tbObat.getValueAt(tbObat.getSelectedRow(),76).toString().equals("Ya")?true:false));
            if(tbObat.getValueAt(tbObat.getSelectedRow(),76).toString().equals("Ya")){
                Valid.SetTgl(TglLaboratorium,tbObat.getValueAt(tbObat.getSelectedRow(),77).toString());
            }
            Hematokrit.setText(tbObat.getValueAt(tbObat.getSelectedRow(),78).toString());
            Hemoglobin.setText(tbObat.getValueAt(tbObat.getSelectedRow(),79).toString());
            Leukosit.setText(tbObat.getValueAt(tbObat.getSelectedRow(),80).toString());
            Trombosit.setText(tbObat.getValueAt(tbObat.getSelectedRow(),81).toString());
            HitungJenis.setText(tbObat.getValueAt(tbObat.getSelectedRow(),82).toString());
            Ureum.setText(tbObat.getValueAt(tbObat.getSelectedRow(),83).toString());
            UrinLengkap.setText(tbObat.getValueAt(tbObat.getSelectedRow(),84).toString());
            Kreatinin.setText(tbObat.getValueAt(tbObat.getSelectedRow(),85).toString());
            CCT.setText(tbObat.getValueAt(tbObat.getSelectedRow(),86).toString());
            SGOT.setText(tbObat.getValueAt(tbObat.getSelectedRow(),87).toString());
            SGPT.setText(tbObat.getValueAt(tbObat.getSelectedRow(),88).toString());
            CT.setText(tbObat.getValueAt(tbObat.getSelectedRow(),89).toString());
            AsamUrat.setText(tbObat.getValueAt(tbObat.getSelectedRow(),90).toString());
            HbsAg.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),91).toString());
            AntiHCV.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),92).toString());
            Edukasi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),93).toString());
        }
    }

    private void isRawat() {
        try {
            ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rkm_medis,pasien.nm_pasien, if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,reg_periksa.tgl_registrasi,"+
                    "reg_periksa.jam_reg from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis where reg_periksa.no_rawat=?");
            try {
                ps.setString(1,TNoRw.getText());
                rs=ps.executeQuery();
                if(rs.next()){
                    TNoRM.setText(rs.getString("no_rkm_medis"));
                    DTPCari1.setDate(rs.getDate("tgl_registrasi"));
                    TPasien.setText(rs.getString("nm_pasien"));
                    Jk.setText(rs.getString("jk"));
                    TglLahir.setText(rs.getString("tgl_lahir"));
                    TanggalRegistrasi.setText(rs.getString("tgl_registrasi")+" "+rs.getString("jam_reg"));
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
 
    public void setNoRm(String norwt,Date tgl2,String asalruangan) {
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        DTPCari2.setDate(tgl2);    
        AsalRuangan.setText(asalruangan);
        isRawat(); 
    }
    
    public void isCek(){
        BtnSimpan.setEnabled(akses.getpenilaian_medis_ralan_hemodialisa());
        BtnHapus.setEnabled(akses.getpenilaian_medis_ralan_hemodialisa());
        BtnEdit.setEnabled(akses.getpenilaian_medis_ralan_hemodialisa());
        BtnEdit.setEnabled(akses.getpenilaian_medis_ralan_hemodialisa());
        if(akses.getjml2()>=1){
            KdDokter.setEditable(false);
            BtnDokter.setEnabled(false);
            KdDokter.setText(akses.getkode());
            NmDokter.setText(Sequel.CariDokter(KdDokter.getText()));
            if(NmDokter.getText().equals("")){
                KdDokter.setText("");
                JOptionPane.showMessageDialog(null,"User login bukan Dokter...!!");
            }
        }  
        
        if(TANGGALMUNDUR.equals("no")){
            if(!akses.getkode().equals("Admin Utama")){
                TglAsuhan.setEditable(false);
                TglAsuhan.setEnabled(false);
            }
        }
    }
    
    public void setTampil(){
       TabRawat.setSelectedIndex(1);
       runBackground(() ->tampil());
    }

    private void hapus() {
        if(Sequel.queryu2tf("delete from penilaian_medis_hemodialisa where no_rawat=?",1,new String[]{
            tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
        })==true){
            tabMode.removeRow(tbObat.getSelectedRow());
            LCount.setText(""+tabMode.getRowCount());
            TabRawat.setSelectedIndex(1);
        }else{
            JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
        }
    }

    private void ganti() {
        if(Sequel.mengedittf("penilaian_medis_hemodialisa","no_rawat=?","no_rawat=?,tanggal=?,kd_dokter=?,anamnesis=?,hubungan=?,ruangan=?,alergi=?,nyeri=?,status_nutrisi=?,hipertensi=?,keterangan_hipertensi=?,diabetes=?,"+
                "keterangan_diabetes=?,batu_saluran_kemih=?,keterangan_batu_saluran_kemih=?,operasi_saluran_kemih=?,keterangan_operasi_saluran_kemih=?,infeksi_saluran_kemih=?,keterangan_infeksi_saluran_kemih=?,bengkak_seluruh_tubuh=?,"+
                "keterangan_bengkak_seluruh_tubuh=?,urin_berdarah=?,keterangan_urin_berdarah=?,penyakit_ginjal_laom=?,keterangan_penyakit_ginjal_laom=?,penyakit_lain=?,keterangan_penyakit_lain=?,konsumsi_obat_nefro=?,"+
                "keterangan_konsumsi_obat_nefro=?,dialisis_pertama=?,pernah_cpad=?,tanggal_cpad=?,pernah_transplantasi=?,tanggal_transplantasi=?,keadaan_umum=?,kesadaran=?,nadi=?,bb=?,td=?,suhu=?,napas=?,tb=?,hepatomegali=?,"+
                "splenomegali=?,ascites=?,edema=?,whezzing=?,ronchi=?,ikterik=?,tekanan_vena=?,anemia=?,kardiomegali=?,bising=?,thorax=?,tanggal_thorax=?,ekg=?,tanggal_ekg=?,bno=?,tanggal_bno=?,usg=?,tanggal_usg=?,renogram=?,"+
                "tanggal_renogram=?,biopsi=?,tanggal_biopsi=?,ctscan=?,tanggal_ctscan=?,arteriografi=?,tanggal_arteriografi=?,kultur_urin=?,tanggal_kultur_urin=?,laborat=?,tanggal_laborat=?,hematokrit=?,hemoglobin=?,leukosit=?,"+
                "trombosit=?,hitung_jenis=?,ureum=?,urin_lengkap=?,kreatinin=?,cct=?,sgot=?,sgpt=?,ct=?,asam_urat=?,hbsag=?,anti_hcv=?,edukasi=?",90,new String[]{
                TNoRw.getText(),Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),KdDokter.getText(),Anamnesis.getSelectedItem().toString(),Hubungan.getText(),AsalRuangan.getText(),Alergi.getText(),Nyeri.getSelectedItem().toString(), 
                StatusNutrisi.getText(),Hipertensi.getSelectedItem().toString(),KeteranganHipertensi.getText(),DiabetesMelitus.getSelectedItem().toString(),KeteranganDiabetesMelitus.getText(),BatuSaluranKemih.getSelectedItem().toString(),KeteranganBatuSaluranKemih.getText(),
                OperasiSaluranKemih.getSelectedItem().toString(),KeteranganOperasiSaluranKemih.getText(),InfeksiSaluranKemih.getSelectedItem().toString(),KeteranganInfeksiSaluranKemih.getText(),BengkakSeluruhTubuh.getSelectedItem().toString(),KeteranganBengkakSeluruhTubuh.getText(), 
                UrinBerdarah.getSelectedItem().toString(),KeteranganUrinBerdarah.getText(),PenyakitGinjalLaom.getSelectedItem().toString(),KeteranganPenyakitGinjalLaom.getText(),PenyakitLain.getSelectedItem().toString(),KeteranganPenyakitLain.getText(),KonsumsiObatNefrotoksis.getSelectedItem().toString(), 
                KeteranganKonsumsiObatNefrotoksis.getText(),Valid.SetTgl(TglDialisis.getSelectedItem()+""),CAPD.getSelectedItem().toString(),Valid.SetTgl(TglCAPD.getSelectedItem()+""),Transplantasi.getSelectedItem().toString(),Valid.SetTgl(TglTransplantasi.getSelectedItem()+""), 
                KeadaanUmum.getSelectedItem().toString(),Kesadaran.getSelectedItem().toString(),Nadi.getText(),BB.getText(),TD.getText(),Suhu.getText(),Napas.getText(),TB.getText(),Hepatomegali.getSelectedItem().toString(),Splenomegali.getSelectedItem().toString(),Ascites.getSelectedItem().toString(), 
                Edema.getSelectedItem().toString(),Whezzing.getSelectedItem().toString(),Ronchi.getSelectedItem().toString(),Ikterik.getSelectedItem().toString(),TekananVenaJugularis.getSelectedItem().toString(),Konjungtiva.getSelectedItem().toString(),Kardiomegali.getSelectedItem().toString(),
                Bising.getSelectedItem().toString(),(ChkThorax.isSelected()==true?"Ya":"Tidak"),(ChkThorax.isSelected()==true?Valid.SetTgl(TglThorax.getSelectedItem()+""):"0000-00-00"),(ChkEKG.isSelected()==true?"Ya":"Tidak"),(ChkEKG.isSelected()==true?Valid.SetTgl(TglEKG.getSelectedItem()+""):"0000-00-00"), 
                (ChkBNO.isSelected()==true?"Ya":"Tidak"),(ChkBNO.isSelected()==true?Valid.SetTgl(TglBNO.getSelectedItem()+""):"0000-00-00"),(ChkUSG.isSelected()==true?"Ya":"Tidak"),(ChkUSG.isSelected()==true?Valid.SetTgl(TglUSG.getSelectedItem()+""):"0000-00-00"),(ChkRenogram.isSelected()==true?"Ya":"Tidak"), 
                (ChkRenogram.isSelected()==true?Valid.SetTgl(TglRenogram.getSelectedItem()+""):"0000-00-00"),(ChkBiopsiGinjal.isSelected()==true?"Ya":"Tidak"),(ChkBiopsiGinjal.isSelected()==true?Valid.SetTgl(TglBiopsi.getSelectedItem()+""):"0000-00-00"),(ChkCTScan.isSelected()==true?"Ya":"Tidak"), 
                (ChkCTScan.isSelected()==true?Valid.SetTgl(TglCTscan.getSelectedItem()+""):"0000-00-00"),(ChkArteriografi.isSelected()==true?"Ya":"Tidak"),(ChkArteriografi.isSelected()==true?Valid.SetTgl(TglArteriografi.getSelectedItem()+""):"0000-00-00"),(ChkKulturUrin.isSelected()==true?"Ya":"Tidak"), 
                (ChkKulturUrin.isSelected()==true?Valid.SetTgl(TglKultururin.getSelectedItem()+""):"0000-00-00"),(ChkLaborat.isSelected()==true?"Ya":"Tidak"),(ChkLaborat.isSelected()==true?Valid.SetTgl(TglLaboratorium.getSelectedItem()+""):"0000-00-00"),Hematokrit.getText(),Hemoglobin.getText(), 
                Leukosit.getText(),Trombosit.getText(),HitungJenis.getText(),Ureum.getText(),UrinLengkap.getText(),Kreatinin.getText(),CCT.getText(),SGOT.getText(),SGPT.getText(),CT.getText(),AsamUrat.getText(),HbsAg.getSelectedItem().toString(),AntiHCV.getSelectedItem().toString(),Edukasi.getText(),
                tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
            })==true){
                tbObat.setValueAt(TNoRw.getText(),tbObat.getSelectedRow(),0);                
                tbObat.setValueAt(TNoRM.getText(),tbObat.getSelectedRow(),1);                
                tbObat.setValueAt(TPasien.getText(),tbObat.getSelectedRow(),2);                
                tbObat.setValueAt(TglLahir.getText(),tbObat.getSelectedRow(),3);                
                tbObat.setValueAt(Jk.getText(),tbObat.getSelectedRow(),4);                
                tbObat.setValueAt(KdDokter.getText(),tbObat.getSelectedRow(),5);                
                tbObat.setValueAt(NmDokter.getText(),tbObat.getSelectedRow(),6);                
                tbObat.setValueAt(Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),tbObat.getSelectedRow(),7);                
                tbObat.setValueAt(Anamnesis.getSelectedItem().toString(),tbObat.getSelectedRow(),8);                
                tbObat.setValueAt(Hubungan.getText(),tbObat.getSelectedRow(),9);                
                tbObat.setValueAt(AsalRuangan.getText(),tbObat.getSelectedRow(),10);                
                tbObat.setValueAt(Alergi.getText(),tbObat.getSelectedRow(),11);                
                tbObat.setValueAt(Nyeri.getSelectedItem().toString(),tbObat.getSelectedRow(),12);                
                tbObat.setValueAt(StatusNutrisi.getText(),tbObat.getSelectedRow(),13);                
                tbObat.setValueAt(Hipertensi.getSelectedItem().toString(),tbObat.getSelectedRow(),14);                
                tbObat.setValueAt(KeteranganHipertensi.getText(),tbObat.getSelectedRow(),15);                
                tbObat.setValueAt(DiabetesMelitus.getSelectedItem().toString(),tbObat.getSelectedRow(),16);                
                tbObat.setValueAt(KeteranganDiabetesMelitus.getText(),tbObat.getSelectedRow(),17);                
                tbObat.setValueAt(BatuSaluranKemih.getSelectedItem().toString(),tbObat.getSelectedRow(),18);                
                tbObat.setValueAt(KeteranganBatuSaluranKemih.getText(),tbObat.getSelectedRow(),19);                
                tbObat.setValueAt(OperasiSaluranKemih.getSelectedItem().toString(),tbObat.getSelectedRow(),20);                
                tbObat.setValueAt(KeteranganOperasiSaluranKemih.getText(),tbObat.getSelectedRow(),21);                
                tbObat.setValueAt(InfeksiSaluranKemih.getSelectedItem().toString(),tbObat.getSelectedRow(),22);                
                tbObat.setValueAt(KeteranganInfeksiSaluranKemih.getText(),tbObat.getSelectedRow(),23);                
                tbObat.setValueAt(BengkakSeluruhTubuh.getSelectedItem().toString(),tbObat.getSelectedRow(),24);                
                tbObat.setValueAt(KeteranganBengkakSeluruhTubuh.getText(),tbObat.getSelectedRow(),25);                
                tbObat.setValueAt(UrinBerdarah.getSelectedItem().toString(),tbObat.getSelectedRow(),26);                
                tbObat.setValueAt(KeteranganUrinBerdarah.getText(),tbObat.getSelectedRow(),27);                
                tbObat.setValueAt(PenyakitGinjalLaom.getSelectedItem().toString(),tbObat.getSelectedRow(),28);                
                tbObat.setValueAt(KeteranganPenyakitGinjalLaom.getText(),tbObat.getSelectedRow(),29);                
                tbObat.setValueAt(PenyakitLain.getSelectedItem().toString(),tbObat.getSelectedRow(),30);                
                tbObat.setValueAt(KeteranganPenyakitLain.getText(),tbObat.getSelectedRow(),31);                
                tbObat.setValueAt(KonsumsiObatNefrotoksis.getSelectedItem().toString(),tbObat.getSelectedRow(),32);                
                tbObat.setValueAt(KeteranganKonsumsiObatNefrotoksis.getText(),tbObat.getSelectedRow(),33);                
                tbObat.setValueAt(Valid.SetTgl(TglDialisis.getSelectedItem()+""),tbObat.getSelectedRow(),34);                
                tbObat.setValueAt(CAPD.getSelectedItem().toString(),tbObat.getSelectedRow(),35);                
                tbObat.setValueAt(Valid.SetTgl(TglCAPD.getSelectedItem()+""),tbObat.getSelectedRow(),36);                
                tbObat.setValueAt(Transplantasi.getSelectedItem().toString(),tbObat.getSelectedRow(),37);                
                tbObat.setValueAt(Valid.SetTgl(TglTransplantasi.getSelectedItem()+""),tbObat.getSelectedRow(),38);                
                tbObat.setValueAt(KeadaanUmum.getSelectedItem().toString(),tbObat.getSelectedRow(),39);                
                tbObat.setValueAt(Kesadaran.getSelectedItem().toString(),tbObat.getSelectedRow(),40);                
                tbObat.setValueAt(Nadi.getText(),tbObat.getSelectedRow(),41);                
                tbObat.setValueAt(BB.getText(),tbObat.getSelectedRow(),42);                
                tbObat.setValueAt(TD.getText(),tbObat.getSelectedRow(),43);                
                tbObat.setValueAt(Suhu.getText(),tbObat.getSelectedRow(),44);                
                tbObat.setValueAt(Napas.getText(),tbObat.getSelectedRow(),45);                
                tbObat.setValueAt(TB.getText(),tbObat.getSelectedRow(),46);                
                tbObat.setValueAt(Hepatomegali.getSelectedItem().toString(),tbObat.getSelectedRow(),47);                
                tbObat.setValueAt(Splenomegali.getSelectedItem().toString(),tbObat.getSelectedRow(),48);                
                tbObat.setValueAt(Ascites.getSelectedItem().toString(),tbObat.getSelectedRow(),49);                
                tbObat.setValueAt(Edema.getSelectedItem().toString(),tbObat.getSelectedRow(),50);                
                tbObat.setValueAt(Whezzing.getSelectedItem().toString(),tbObat.getSelectedRow(),51);                
                tbObat.setValueAt(Ronchi.getSelectedItem().toString(),tbObat.getSelectedRow(),52);                
                tbObat.setValueAt(Ikterik.getSelectedItem().toString(),tbObat.getSelectedRow(),53);                
                tbObat.setValueAt(TekananVenaJugularis.getSelectedItem().toString(),tbObat.getSelectedRow(),54);                
                tbObat.setValueAt(Konjungtiva.getSelectedItem().toString(),tbObat.getSelectedRow(),55);                
                tbObat.setValueAt(Kardiomegali.getSelectedItem().toString(),tbObat.getSelectedRow(),56);                
                tbObat.setValueAt(Bising.getSelectedItem().toString(),tbObat.getSelectedRow(),57);                
                tbObat.setValueAt((ChkThorax.isSelected()==true?"Ya":"Tidak"),tbObat.getSelectedRow(),58);                
                tbObat.setValueAt((ChkThorax.isSelected()==true?Valid.SetTgl(TglThorax.getSelectedItem()+""):""),tbObat.getSelectedRow(),59);                
                tbObat.setValueAt((ChkEKG.isSelected()==true?"Ya":"Tidak"),tbObat.getSelectedRow(),60);                
                tbObat.setValueAt((ChkEKG.isSelected()==true?Valid.SetTgl(TglEKG.getSelectedItem()+""):""),tbObat.getSelectedRow(),61);                
                tbObat.setValueAt((ChkBNO.isSelected()==true?"Ya":"Tidak"),tbObat.getSelectedRow(),62);                
                tbObat.setValueAt((ChkBNO.isSelected()==true?Valid.SetTgl(TglBNO.getSelectedItem()+""):""),tbObat.getSelectedRow(),63);                
                tbObat.setValueAt((ChkUSG.isSelected()==true?"Ya":"Tidak"),tbObat.getSelectedRow(),64);                
                tbObat.setValueAt((ChkUSG.isSelected()==true?Valid.SetTgl(TglUSG.getSelectedItem()+""):""),tbObat.getSelectedRow(),65);                
                tbObat.setValueAt((ChkRenogram.isSelected()==true?"Ya":"Tidak"),tbObat.getSelectedRow(),66); 
                tbObat.setValueAt((ChkRenogram.isSelected()==true?Valid.SetTgl(TglRenogram.getSelectedItem()+""):""),tbObat.getSelectedRow(),67);                
                tbObat.setValueAt((ChkBiopsiGinjal.isSelected()==true?"Ya":"Tidak"),tbObat.getSelectedRow(),68);                
                tbObat.setValueAt((ChkBiopsiGinjal.isSelected()==true?Valid.SetTgl(TglBiopsi.getSelectedItem()+""):""),tbObat.getSelectedRow(),69);                
                tbObat.setValueAt((ChkCTScan.isSelected()==true?"Ya":"Tidak"),tbObat.getSelectedRow(),70);                
                tbObat.setValueAt((ChkCTScan.isSelected()==true?Valid.SetTgl(TglCTscan.getSelectedItem()+""):""),tbObat.getSelectedRow(),71);                
                tbObat.setValueAt((ChkArteriografi.isSelected()==true?"Ya":"Tidak"),tbObat.getSelectedRow(),72);                
                tbObat.setValueAt((ChkArteriografi.isSelected()==true?Valid.SetTgl(TglArteriografi.getSelectedItem()+""):""),tbObat.getSelectedRow(),73);                
                tbObat.setValueAt((ChkKulturUrin.isSelected()==true?"Ya":"Tidak"),tbObat.getSelectedRow(),74);                
                tbObat.setValueAt((ChkKulturUrin.isSelected()==true?Valid.SetTgl(TglKultururin.getSelectedItem()+""):""),tbObat.getSelectedRow(),75);                
                tbObat.setValueAt((ChkLaborat.isSelected()==true?"Ya":"Tidak"),tbObat.getSelectedRow(),76);                
                tbObat.setValueAt((ChkLaborat.isSelected()==true?Valid.SetTgl(TglLaboratorium.getSelectedItem()+""):""),tbObat.getSelectedRow(),77);                
                tbObat.setValueAt(Hematokrit.getText(),tbObat.getSelectedRow(),78);                
                tbObat.setValueAt(Hemoglobin.getText(),tbObat.getSelectedRow(),79);                
                tbObat.setValueAt(Leukosit.getText(),tbObat.getSelectedRow(),80);                
                tbObat.setValueAt(Trombosit.getText(),tbObat.getSelectedRow(),81);                
                tbObat.setValueAt(HitungJenis.getText(),tbObat.getSelectedRow(),82);                
                tbObat.setValueAt(Ureum.getText(),tbObat.getSelectedRow(),83);                
                tbObat.setValueAt(UrinLengkap.getText(),tbObat.getSelectedRow(),84);                
                tbObat.setValueAt(Kreatinin.getText(),tbObat.getSelectedRow(),85);                
                tbObat.setValueAt(CCT.getText(),tbObat.getSelectedRow(),86);                
                tbObat.setValueAt(SGOT.getText(),tbObat.getSelectedRow(),87);                
                tbObat.setValueAt(SGPT.getText(),tbObat.getSelectedRow(),88);                
                tbObat.setValueAt(CT.getText(),tbObat.getSelectedRow(),89);                
                tbObat.setValueAt(AsamUrat.getText(),tbObat.getSelectedRow(),90);                
                tbObat.setValueAt(HbsAg.getSelectedItem().toString(),tbObat.getSelectedRow(),91);                
                tbObat.setValueAt(AntiHCV.getSelectedItem().toString(),tbObat.getSelectedRow(),92);                
                tbObat.setValueAt(Edukasi.getText(),tbObat.getSelectedRow(),93);   
                emptTeks();
                TabRawat.setSelectedIndex(1);
        }
    }

    private void simpan() {
        if(Sequel.menyimpantf("penilaian_medis_hemodialisa","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","Data",89,new String[]{
            TNoRw.getText(),Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),KdDokter.getText(),Anamnesis.getSelectedItem().toString(),Hubungan.getText(),AsalRuangan.getText(),Alergi.getText(),Nyeri.getSelectedItem().toString(), 
            StatusNutrisi.getText(),Hipertensi.getSelectedItem().toString(),KeteranganHipertensi.getText(),DiabetesMelitus.getSelectedItem().toString(),KeteranganDiabetesMelitus.getText(),BatuSaluranKemih.getSelectedItem().toString(),KeteranganBatuSaluranKemih.getText(),
            OperasiSaluranKemih.getSelectedItem().toString(),KeteranganOperasiSaluranKemih.getText(),InfeksiSaluranKemih.getSelectedItem().toString(),KeteranganInfeksiSaluranKemih.getText(),BengkakSeluruhTubuh.getSelectedItem().toString(),KeteranganBengkakSeluruhTubuh.getText(), 
            UrinBerdarah.getSelectedItem().toString(),KeteranganUrinBerdarah.getText(),PenyakitGinjalLaom.getSelectedItem().toString(),KeteranganPenyakitGinjalLaom.getText(),PenyakitLain.getSelectedItem().toString(),KeteranganPenyakitLain.getText(),KonsumsiObatNefrotoksis.getSelectedItem().toString(), 
            KeteranganKonsumsiObatNefrotoksis.getText(),Valid.SetTgl(TglDialisis.getSelectedItem()+""),CAPD.getSelectedItem().toString(),Valid.SetTgl(TglCAPD.getSelectedItem()+""),Transplantasi.getSelectedItem().toString(),Valid.SetTgl(TglTransplantasi.getSelectedItem()+""), 
            KeadaanUmum.getSelectedItem().toString(),Kesadaran.getSelectedItem().toString(),Nadi.getText(),BB.getText(),TD.getText(),Suhu.getText(),Napas.getText(),TB.getText(),Hepatomegali.getSelectedItem().toString(),Splenomegali.getSelectedItem().toString(),Ascites.getSelectedItem().toString(), 
            Edema.getSelectedItem().toString(),Whezzing.getSelectedItem().toString(),Ronchi.getSelectedItem().toString(),Ikterik.getSelectedItem().toString(),TekananVenaJugularis.getSelectedItem().toString(),Konjungtiva.getSelectedItem().toString(),Kardiomegali.getSelectedItem().toString(),
            Bising.getSelectedItem().toString(),(ChkThorax.isSelected()==true?"Ya":"Tidak"),(ChkThorax.isSelected()==true?Valid.SetTgl(TglThorax.getSelectedItem()+""):"0000-00-00"),(ChkEKG.isSelected()==true?"Ya":"Tidak"),(ChkEKG.isSelected()==true?Valid.SetTgl(TglEKG.getSelectedItem()+""):"0000-00-00"), 
            (ChkBNO.isSelected()==true?"Ya":"Tidak"),(ChkBNO.isSelected()==true?Valid.SetTgl(TglBNO.getSelectedItem()+""):"0000-00-00"),(ChkUSG.isSelected()==true?"Ya":"Tidak"),(ChkUSG.isSelected()==true?Valid.SetTgl(TglUSG.getSelectedItem()+""):"0000-00-00"),(ChkRenogram.isSelected()==true?"Ya":"Tidak"), 
            (ChkRenogram.isSelected()==true?Valid.SetTgl(TglRenogram.getSelectedItem()+""):"0000-00-00"),(ChkBiopsiGinjal.isSelected()==true?"Ya":"Tidak"),(ChkBiopsiGinjal.isSelected()==true?Valid.SetTgl(TglBiopsi.getSelectedItem()+""):"0000-00-00"),(ChkCTScan.isSelected()==true?"Ya":"Tidak"), 
            (ChkCTScan.isSelected()==true?Valid.SetTgl(TglCTscan.getSelectedItem()+""):"0000-00-00"),(ChkArteriografi.isSelected()==true?"Ya":"Tidak"),(ChkArteriografi.isSelected()==true?Valid.SetTgl(TglArteriografi.getSelectedItem()+""):"0000-00-00"),(ChkKulturUrin.isSelected()==true?"Ya":"Tidak"), 
            (ChkKulturUrin.isSelected()==true?Valid.SetTgl(TglKultururin.getSelectedItem()+""):"0000-00-00"),(ChkLaborat.isSelected()==true?"Ya":"Tidak"),(ChkLaborat.isSelected()==true?Valid.SetTgl(TglLaboratorium.getSelectedItem()+""):"0000-00-00"),Hematokrit.getText(),Hemoglobin.getText(), 
            Leukosit.getText(),Trombosit.getText(),HitungJenis.getText(),Ureum.getText(),UrinLengkap.getText(),Kreatinin.getText(),CCT.getText(),SGOT.getText(),SGPT.getText(),CT.getText(),AsamUrat.getText(),HbsAg.getSelectedItem().toString(),AntiHCV.getSelectedItem().toString(),Edukasi.getText()
        })==true){
            tabMode.addRow(new Object[]{
                TNoRw.getText(),TNoRM.getText(),TPasien.getText(),TglLahir.getText(),Jk.getText(),KdDokter.getText(),NmDokter.getText(),Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),Anamnesis.getSelectedItem().toString(),Hubungan.getText(),
                AsalRuangan.getText(),Alergi.getText(),Nyeri.getSelectedItem().toString(),StatusNutrisi.getText(),Hipertensi.getSelectedItem().toString(),KeteranganHipertensi.getText(),DiabetesMelitus.getSelectedItem().toString(),KeteranganDiabetesMelitus.getText(),BatuSaluranKemih.getSelectedItem().toString(),
                KeteranganBatuSaluranKemih.getText(),OperasiSaluranKemih.getSelectedItem().toString(),KeteranganOperasiSaluranKemih.getText(),InfeksiSaluranKemih.getSelectedItem().toString(),KeteranganInfeksiSaluranKemih.getText(),BengkakSeluruhTubuh.getSelectedItem().toString(),KeteranganBengkakSeluruhTubuh.getText(),
                UrinBerdarah.getSelectedItem().toString(),KeteranganUrinBerdarah.getText(),PenyakitGinjalLaom.getSelectedItem().toString(),KeteranganPenyakitGinjalLaom.getText(),PenyakitLain.getSelectedItem().toString(),KeteranganPenyakitLain.getText(),KonsumsiObatNefrotoksis.getSelectedItem().toString(),
                KeteranganKonsumsiObatNefrotoksis.getText(),Valid.SetTgl(TglDialisis.getSelectedItem()+""),CAPD.getSelectedItem().toString(),Valid.SetTgl(TglCAPD.getSelectedItem()+""),Transplantasi.getSelectedItem().toString(),Valid.SetTgl(TglTransplantasi.getSelectedItem()+""),KeadaanUmum.getSelectedItem().toString(),
                Kesadaran.getSelectedItem().toString(),Nadi.getText(),BB.getText(),TD.getText(),Suhu.getText(),Napas.getText(),TB.getText(),Hepatomegali.getSelectedItem().toString(),Splenomegali.getSelectedItem().toString(),Ascites.getSelectedItem().toString(),Edema.getSelectedItem().toString(),
                Whezzing.getSelectedItem().toString(),Ronchi.getSelectedItem().toString(),Ikterik.getSelectedItem().toString(),TekananVenaJugularis.getSelectedItem().toString(),Konjungtiva.getSelectedItem().toString(),Kardiomegali.getSelectedItem().toString(),Bising.getSelectedItem().toString(),
                (ChkThorax.isSelected()==true?"Ya":"Tidak"),(ChkThorax.isSelected()==true?Valid.SetTgl(TglThorax.getSelectedItem()+""):""),(ChkEKG.isSelected()==true?"Ya":"Tidak"),(ChkEKG.isSelected()==true?Valid.SetTgl(TglEKG.getSelectedItem()+""):""),(ChkBNO.isSelected()==true?"Ya":"Tidak"),
                (ChkBNO.isSelected()==true?Valid.SetTgl(TglBNO.getSelectedItem()+""):""),(ChkUSG.isSelected()==true?"Ya":"Tidak"),(ChkUSG.isSelected()==true?Valid.SetTgl(TglUSG.getSelectedItem()+""):""),(ChkRenogram.isSelected()==true?"Ya":"Tidak"),
                (ChkRenogram.isSelected()==true?Valid.SetTgl(TglRenogram.getSelectedItem()+""):""),(ChkBiopsiGinjal.isSelected()==true?"Ya":"Tidak"),(ChkBiopsiGinjal.isSelected()==true?Valid.SetTgl(TglBiopsi.getSelectedItem()+""):""),(ChkCTScan.isSelected()==true?"Ya":"Tidak"),
                (ChkCTScan.isSelected()==true?Valid.SetTgl(TglCTscan.getSelectedItem()+""):""),(ChkArteriografi.isSelected()==true?"Ya":"Tidak"),(ChkArteriografi.isSelected()==true?Valid.SetTgl(TglArteriografi.getSelectedItem()+""):""),(ChkKulturUrin.isSelected()==true?"Ya":"Tidak"),
                (ChkKulturUrin.isSelected()==true?Valid.SetTgl(TglKultururin.getSelectedItem()+""):""),(ChkLaborat.isSelected()==true?"Ya":"Tidak"),(ChkLaborat.isSelected()==true?Valid.SetTgl(TglLaboratorium.getSelectedItem()+""):""),Hematokrit.getText(),Hemoglobin.getText(),Leukosit.getText(),
                Trombosit.getText(),HitungJenis.getText(),Ureum.getText(),UrinLengkap.getText(),Kreatinin.getText(),CCT.getText(),SGOT.getText(),SGPT.getText(),CT.getText(),AsamUrat.getText(),HbsAg.getSelectedItem().toString(),AntiHCV.getSelectedItem().toString(),Edukasi.getText()
            });
            LCount.setText(""+tabMode.getRowCount());
            emptTeks();
        } 
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

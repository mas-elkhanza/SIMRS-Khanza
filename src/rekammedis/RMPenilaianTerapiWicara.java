/*
 * By Mas Elkhanza
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
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import java.util.concurrent.RejectedExecutionException;
import javax.swing.SwingUtilities;
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
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import kepegawaian.DlgCariPetugas;


/**
 *
 * @author perpustakaan
 */
public final class RMPenilaianTerapiWicara extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;
    private DlgCariPetugas petugas;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private volatile boolean ceksukses = false;
    private String finger=""; 
    private StringBuilder htmlContent;
    
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public RMPenilaianTerapiWicara(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.RM","Nama Pasien","J.K.","Tgl.Lahir","Tanggal","Diagnosa Terapi Wicara","Diagnosa Medis","Anamnesa","Suhu(°C)","RR(x/menit)","Nadi(x/menit)",
            "TD(mmHg)","Kontak Mata","Atensi","Perilaku","Bicara Spontan","Pemahaman Bahasa","Pengujaran","Membaca","Penamaan","Anatomis Lip", "Anatomis Tongue", 
            "Anatomis Hard Palate","Anatomis Soft Palate","Anatomis Uvula","Anatomis Mandibula","Anatomis Maxila","Anatomis Dental","Anatomis Faring", 
            "Fisiologis Lip","Fisiologis Tongue","Fisiologis Hard Palate","Fisiologis Soft Palate","Fisiologis Uvula","Fisiologis Mandibula","Fisiologis Maxilla", 
            "Fisiologis Dental","Fisiologis Faring","Menghisap","Mengunyah","Meniup","Subtitusi","Omisi","Distorsi","Adisi","Resonasi","Nada Suara", 
            "Kualitas Suara","Kenyaringan Suara","Kemampuan Irama Kelancaran","Kemampuan Menelan","Pernapasan","Dekoding Pendengaran","Dekoding Penglihatan", 
            "Dekoding Kinesik","Enkoding Bicara","Enkoding Tulisan","Enkoding Mimik","Enkoding Gesture","Penunjang Medis","Tujuan Terapi Wicara",
            "Program Terapi Wicara","Edukasi","Tindak Lanjut","NIP","Nama Petugas"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 67; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(105);
            }else if(i==1){
                column.setPreferredWidth(65);
            }else if(i==2){
                column.setPreferredWidth(160);
            }else if(i==3){
                column.setPreferredWidth(50);
            }else if(i==4){
                column.setPreferredWidth(60);
            }else if(i==5){
                column.setPreferredWidth(120);
            }else if(i==6){
                column.setPreferredWidth(250);
            }else if(i==7){
                column.setPreferredWidth(250);
            }else if(i==8){
                column.setPreferredWidth(250);
            }else if(i==9){
                column.setPreferredWidth(53);
            }else if(i==10){
                column.setPreferredWidth(68);
            }else if(i==11){
                column.setPreferredWidth(75);
            }else if(i==12){
                column.setPreferredWidth(62);
            }else if(i==13){
                column.setPreferredWidth(150);
            }else if(i==14){
                column.setPreferredWidth(150);
            }else if(i==15){
                column.setPreferredWidth(150);
            }else if(i==16){
                column.setPreferredWidth(150);
            }else if(i==17){
                column.setPreferredWidth(150);
            }else if(i==18){
                column.setPreferredWidth(150);
            }else if(i==19){
                column.setPreferredWidth(150);
            }else if(i==20){
                column.setPreferredWidth(150);
            }else if(i==21){
                column.setPreferredWidth(120);
            }else if(i==22){
                column.setPreferredWidth(120);
            }else if(i==23){
                column.setPreferredWidth(120);
            }else if(i==24){
                column.setPreferredWidth(120);
            }else if(i==25){
                column.setPreferredWidth(120);
            }else if(i==26){
                column.setPreferredWidth(120);
            }else if(i==27){
                column.setPreferredWidth(120);
            }else if(i==28){
                column.setPreferredWidth(120);
            }else if(i==29){
                column.setPreferredWidth(120);
            }else if(i==30){
                column.setPreferredWidth(120);
            }else if(i==31){
                column.setPreferredWidth(120);
            }else if(i==32){
                column.setPreferredWidth(120);
            }else if(i==33){
                column.setPreferredWidth(120);
            }else if(i==34){
                column.setPreferredWidth(120);
            }else if(i==35){
                column.setPreferredWidth(120);
            }else if(i==36){
                column.setPreferredWidth(120);
            }else if(i==37){
                column.setPreferredWidth(120);
            }else if(i==38){
                column.setPreferredWidth(120);
            }else if(i==39){
                column.setPreferredWidth(200);
            }else if(i==40){
                column.setPreferredWidth(200);
            }else if(i==41){
                column.setPreferredWidth(200);
            }else if(i==42){
                column.setPreferredWidth(200);
            }else if(i==43){
                column.setPreferredWidth(200);
            }else if(i==44){
                column.setPreferredWidth(200);
            }else if(i==45){
                column.setPreferredWidth(200);
            }else if(i==46){
                column.setPreferredWidth(55);
            }else if(i==47){
                column.setPreferredWidth(66);
            }else if(i==48){
                column.setPreferredWidth(80);
            }else if(i==49){
                column.setPreferredWidth(101);
            }else if(i==50){
                column.setPreferredWidth(160);
            }else if(i==51){
                column.setPreferredWidth(200);
            }else if(i==52){
                column.setPreferredWidth(200);
            }else if(i==53){
                column.setPreferredWidth(150);
            }else if(i==54){
                column.setPreferredWidth(150);
            }else if(i==55){
                column.setPreferredWidth(150);
            }else if(i==56){
                column.setPreferredWidth(150);
            }else if(i==57){
                column.setPreferredWidth(150);
            }else if(i==58){
                column.setPreferredWidth(150);
            }else if(i==59){
                column.setPreferredWidth(150);
            }else if(i==60){
                column.setPreferredWidth(200);
            }else if(i==61){
                column.setPreferredWidth(200);
            }else if(i==62){
                column.setPreferredWidth(200);
            }else if(i==63){
                column.setPreferredWidth(200);
            }else if(i==64){
                column.setPreferredWidth(200);
            }else if(i==65){
                column.setPreferredWidth(80);
            }else if(i==66){
                column.setPreferredWidth(150);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());
        
        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        DiagnosaTerapiWicara.setDocument(new batasInput((int)100).getKata(DiagnosaTerapiWicara));
        DiagnosaMedis.setDocument(new batasInput((int)100).getKata(DiagnosaMedis));
        Anamnesa.setDocument(new batasInput((int)300).getKata(Anamnesa));
        Suhu.setDocument(new batasInput((int)5).getKata(Suhu));
        RR.setDocument(new batasInput((int)5).getKata(RR));
        Nadi.setDocument(new batasInput((int)5).getKata(Nadi));
        TD.setDocument(new batasInput((int)8).getKata(TD));
        KontakMata.setDocument(new batasInput((int)50).getKata(KontakMata));
        Atensi.setDocument(new batasInput((int)50).getKata(Atensi));
        Perilaku.setDocument(new batasInput((int)50).getKata(Perilaku));
        BicaraSpontan.setDocument(new batasInput((int)50).getKata(BicaraSpontan));
        PemahamanBahasa.setDocument(new batasInput((int)50).getKata(PemahamanBahasa));
        Pengujaran.setDocument(new batasInput((int)50).getKata(Pengujaran));
        Membaca.setDocument(new batasInput((int)50).getKata(Membaca));
        Penamaan.setDocument(new batasInput((int)50).getKata(Penamaan));
        LipAnatomis.setDocument(new batasInput((int)30).getKata(LipAnatomis));
        TongueAnatomis.setDocument(new batasInput((int)30).getKata(TongueAnatomis));
        HardPalateAnatomis.setDocument(new batasInput((int)30).getKata(HardPalateAnatomis));
        SoftPalateAnatomis.setDocument(new batasInput((int)30).getKata(SoftPalateAnatomis));
        UvulaAnatomis.setDocument(new batasInput((int)30).getKata(UvulaAnatomis));
        MandibulaAnatomis.setDocument(new batasInput((int)30).getKata(MandibulaAnatomis));
        MaxillaAnatomis.setDocument(new batasInput((int)30).getKata(MaxillaAnatomis));
        DentalAnatomis.setDocument(new batasInput((int)30).getKata(DentalAnatomis));
        FaringAnatomis.setDocument(new batasInput((int)30).getKata(FaringAnatomis));
        LipFisiologis.setDocument(new batasInput((int)30).getKata(LipFisiologis));
        TongueFisiologis.setDocument(new batasInput((int)30).getKata(TongueFisiologis));
        HardPalateFisiologis.setDocument(new batasInput((int)30).getKata(HardPalateFisiologis));
        SoftPalateFisiologis.setDocument(new batasInput((int)30).getKata(SoftPalateFisiologis));
        UvulaFisiologis.setDocument(new batasInput((int)30).getKata(UvulaFisiologis));
        MandibulaFisiologis.setDocument(new batasInput((int)30).getKata(MandibulaFisiologis));
        MaxillaFisiologis.setDocument(new batasInput((int)30).getKata(MaxillaFisiologis));
        DentalFisiologis.setDocument(new batasInput((int)30).getKata(DentalFisiologis));
        FaringFisiologis.setDocument(new batasInput((int)30).getKata(FaringFisiologis));
        Menghisap.setDocument(new batasInput((int)150).getKata(Menghisap));
        Mengunyah.setDocument(new batasInput((int)150).getKata(Mengunyah));
        Meniup.setDocument(new batasInput((int)150).getKata(Meniup));
        Subtitusi.setDocument(new batasInput((int)150).getKata(Subtitusi));
        Omisi.setDocument(new batasInput((int)150).getKata(Omisi));
        Distorsi.setDocument(new batasInput((int)150).getKata(Distorsi));
        Adisi.setDocument(new batasInput((int)150).getKata(Adisi));
        KemampuanMenelan.setDocument(new batasInput((int)150).getKata(KemampuanMenelan));
        Pernapasan.setDocument(new batasInput((int)150).getKata(Pernapasan));
        Pendengaran.setDocument(new batasInput((int)30).getKata(Pendengaran));
        Penglihatan.setDocument(new batasInput((int)30).getKata(Penglihatan));
        Kinesek.setDocument(new batasInput((int)30).getKata(Kinesek));
        Bicara.setDocument(new batasInput((int)30).getKata(Bicara));
        Tulisan.setDocument(new batasInput((int)30).getKata(Tulisan));
        Mimik.setDocument(new batasInput((int)30).getKata(Mimik));
        Gesture.setDocument(new batasInput((int)30).getKata(Gesture));
        PenunjangMedis.setDocument(new batasInput((int)150).getKata(PenunjangMedis));
        TujuanTerapiWicara.setDocument(new batasInput((int)150).getKata(TujuanTerapiWicara));
        ProgramTerapiWicara.setDocument(new batasInput((int)150).getKata(ProgramTerapiWicara));
        Edukasi.setDocument(new batasInput((int)150).getKata(Edukasi));
        TindakLanjut.setDocument(new batasInput((int)150).getKata(TindakLanjut));
        
        TCari.setDocument(new batasInput((int)100).getKata(TCari));
        
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
        MnPenilaianTerapiWicara = new javax.swing.JMenuItem();
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
        KdPetugas = new widget.TextBox();
        NmPetugas = new widget.TextBox();
        BtnPetugas = new widget.Button();
        jLabel8 = new widget.Label();
        TglLahir = new widget.TextBox();
        Jk = new widget.TextBox();
        jLabel10 = new widget.Label();
        label11 = new widget.Label();
        jLabel11 = new widget.Label();
        TglAsuhan = new widget.Tanggal();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel9 = new widget.Label();
        scrollPane1 = new widget.ScrollPane();
        DiagnosaTerapiWicara = new widget.TextArea();
        jLabel30 = new widget.Label();
        scrollPane2 = new widget.ScrollPane();
        Anamnesa = new widget.TextArea();
        jLabel31 = new widget.Label();
        scrollPane4 = new widget.ScrollPane();
        Menghisap = new widget.TextArea();
        scrollPane3 = new widget.ScrollPane();
        DiagnosaMedis = new widget.TextArea();
        jLabel32 = new widget.Label();
        jLabel22 = new widget.Label();
        TD = new widget.TextBox();
        jLabel23 = new widget.Label();
        jLabel18 = new widget.Label();
        Suhu = new widget.TextBox();
        jLabel20 = new widget.Label();
        jLabel17 = new widget.Label();
        Nadi = new widget.TextBox();
        jLabel16 = new widget.Label();
        jLabel26 = new widget.Label();
        RR = new widget.TextBox();
        jLabel25 = new widget.Label();
        jLabel33 = new widget.Label();
        jLabel34 = new widget.Label();
        jLabel29 = new widget.Label();
        KontakMata = new widget.TextBox();
        jLabel35 = new widget.Label();
        Perilaku = new widget.TextBox();
        jLabel36 = new widget.Label();
        Atensi = new widget.TextBox();
        jLabel37 = new widget.Label();
        jLabel38 = new widget.Label();
        Pengujaran = new widget.TextBox();
        jLabel39 = new widget.Label();
        BicaraSpontan = new widget.TextBox();
        jLabel40 = new widget.Label();
        Membaca = new widget.TextBox();
        jLabel41 = new widget.Label();
        PemahamanBahasa = new widget.TextBox();
        jLabel42 = new widget.Label();
        Penamaan = new widget.TextBox();
        jLabel43 = new widget.Label();
        jLabel44 = new widget.Label();
        jLabel45 = new widget.Label();
        LipAnatomis = new widget.TextBox();
        jLabel46 = new widget.Label();
        TongueAnatomis = new widget.TextBox();
        jLabel47 = new widget.Label();
        HardPalateAnatomis = new widget.TextBox();
        jLabel48 = new widget.Label();
        MandibulaAnatomis = new widget.TextBox();
        UvulaAnatomis = new widget.TextBox();
        jLabel49 = new widget.Label();
        SoftPalateAnatomis = new widget.TextBox();
        jLabel50 = new widget.Label();
        jLabel51 = new widget.Label();
        FaringAnatomis = new widget.TextBox();
        DentalAnatomis = new widget.TextBox();
        jLabel52 = new widget.Label();
        MaxillaAnatomis = new widget.TextBox();
        jLabel53 = new widget.Label();
        jLabel54 = new widget.Label();
        jLabel55 = new widget.Label();
        LipFisiologis = new widget.TextBox();
        jLabel56 = new widget.Label();
        TongueFisiologis = new widget.TextBox();
        jLabel57 = new widget.Label();
        HardPalateFisiologis = new widget.TextBox();
        jLabel58 = new widget.Label();
        SoftPalateFisiologis = new widget.TextBox();
        jLabel59 = new widget.Label();
        UvulaFisiologis = new widget.TextBox();
        jLabel60 = new widget.Label();
        MandibulaFisiologis = new widget.TextBox();
        jLabel61 = new widget.Label();
        MaxillaFisiologis = new widget.TextBox();
        jLabel62 = new widget.Label();
        DentalFisiologis = new widget.TextBox();
        jLabel63 = new widget.Label();
        FaringFisiologis = new widget.TextBox();
        jLabel64 = new widget.Label();
        jLabel12 = new widget.Label();
        scrollPane5 = new widget.ScrollPane();
        Mengunyah = new widget.TextArea();
        scrollPane6 = new widget.ScrollPane();
        Meniup = new widget.TextArea();
        jLabel13 = new widget.Label();
        jLabel65 = new widget.Label();
        jLabel14 = new widget.Label();
        jLabel15 = new widget.Label();
        scrollPane7 = new widget.ScrollPane();
        Subtitusi = new widget.TextArea();
        scrollPane8 = new widget.ScrollPane();
        Omisi = new widget.TextArea();
        jLabel24 = new widget.Label();
        jLabel27 = new widget.Label();
        scrollPane9 = new widget.ScrollPane();
        Distorsi = new widget.TextArea();
        scrollPane10 = new widget.ScrollPane();
        Adisi = new widget.TextArea();
        jLabel67 = new widget.Label();
        Resonasi = new widget.ComboBox();
        jLabel66 = new widget.Label();
        jLabel68 = new widget.Label();
        jLabel69 = new widget.Label();
        jLabel70 = new widget.Label();
        Nada = new widget.ComboBox();
        jLabel71 = new widget.Label();
        Kualitas = new widget.ComboBox();
        KemampuanIramaKelancaran = new widget.ComboBox();
        jLabel72 = new widget.Label();
        jLabel73 = new widget.Label();
        Kenyaringan = new widget.ComboBox();
        jLabel74 = new widget.Label();
        scrollPane11 = new widget.ScrollPane();
        KemampuanMenelan = new widget.TextArea();
        jLabel75 = new widget.Label();
        scrollPane12 = new widget.ScrollPane();
        Pernapasan = new widget.TextArea();
        jLabel76 = new widget.Label();
        jLabel77 = new widget.Label();
        jLabel78 = new widget.Label();
        Pendengaran = new widget.TextBox();
        jLabel79 = new widget.Label();
        Penglihatan = new widget.TextBox();
        jLabel80 = new widget.Label();
        Kinesek = new widget.TextBox();
        jLabel81 = new widget.Label();
        jLabel82 = new widget.Label();
        Bicara = new widget.TextBox();
        jLabel83 = new widget.Label();
        Tulisan = new widget.TextBox();
        Gesture = new widget.TextBox();
        jLabel84 = new widget.Label();
        Mimik = new widget.TextBox();
        jLabel85 = new widget.Label();
        jLabel86 = new widget.Label();
        scrollPane13 = new widget.ScrollPane();
        PenunjangMedis = new widget.TextArea();
        jLabel87 = new widget.Label();
        jLabel88 = new widget.Label();
        scrollPane14 = new widget.ScrollPane();
        TujuanTerapiWicara = new widget.TextArea();
        jLabel89 = new widget.Label();
        scrollPane15 = new widget.ScrollPane();
        ProgramTerapiWicara = new widget.TextArea();
        jLabel90 = new widget.Label();
        scrollPane16 = new widget.ScrollPane();
        Edukasi = new widget.TextArea();
        jLabel91 = new widget.Label();
        scrollPane17 = new widget.ScrollPane();
        TindakLanjut = new widget.TextArea();
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

        MnPenilaianTerapiWicara.setBackground(new java.awt.Color(255, 255, 254));
        MnPenilaianTerapiWicara.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPenilaianTerapiWicara.setForeground(new java.awt.Color(50, 50, 50));
        MnPenilaianTerapiWicara.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPenilaianTerapiWicara.setText("Laporan Pengkajian Terapi Wicara");
        MnPenilaianTerapiWicara.setName("MnPenilaianTerapiWicara"); // NOI18N
        MnPenilaianTerapiWicara.setPreferredSize(new java.awt.Dimension(220, 26));
        MnPenilaianTerapiWicara.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPenilaianTerapiWicaraActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnPenilaianTerapiWicara);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Pengkajian Terapi Wicara ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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
        FormInput.setPreferredSize(new java.awt.Dimension(870, 1693));
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

        label14.setText("Petugas :");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label14);
        label14.setBounds(0, 40, 70, 23);

        KdPetugas.setEditable(false);
        KdPetugas.setName("KdPetugas"); // NOI18N
        KdPetugas.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput.add(KdPetugas);
        KdPetugas.setBounds(74, 40, 130, 23);

        NmPetugas.setEditable(false);
        NmPetugas.setName("NmPetugas"); // NOI18N
        NmPetugas.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmPetugas);
        NmPetugas.setBounds(207, 40, 331, 23);

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
        BtnPetugas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPetugasKeyPressed(evt);
            }
        });
        FormInput.add(BtnPetugas);
        BtnPetugas.setBounds(541, 40, 28, 23);

        jLabel8.setText("Tgl.Lahir :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(580, 10, 60, 23);

        TglLahir.setEditable(false);
        TglLahir.setHighlighter(null);
        TglLahir.setName("TglLahir"); // NOI18N
        FormInput.add(TglLahir);
        TglLahir.setBounds(644, 10, 80, 23);

        Jk.setEditable(false);
        Jk.setHighlighter(null);
        Jk.setName("Jk"); // NOI18N
        FormInput.add(Jk);
        Jk.setBounds(774, 10, 80, 23);

        jLabel10.setText("No.Rawat :");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(0, 10, 70, 23);

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label11);
        label11.setBounds(663, 40, 57, 23);

        jLabel11.setText("J.K. :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(740, 10, 30, 23);

        TglAsuhan.setForeground(new java.awt.Color(50, 70, 50));
        TglAsuhan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "28-01-2026 04:43:07" }));
        TglAsuhan.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        TglAsuhan.setName("TglAsuhan"); // NOI18N
        TglAsuhan.setOpaque(false);
        TglAsuhan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglAsuhanKeyPressed(evt);
            }
        });
        FormInput.add(TglAsuhan);
        TglAsuhan.setBounds(724, 40, 130, 23);

        jSeparator1.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator1.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator1.setName("jSeparator1"); // NOI18N
        FormInput.add(jSeparator1);
        jSeparator1.setBounds(0, 70, 880, 1);

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel9.setText("Menghisap :");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(80, 730, 200, 23);

        scrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane1.setName("scrollPane1"); // NOI18N

        DiagnosaTerapiWicara.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        DiagnosaTerapiWicara.setColumns(20);
        DiagnosaTerapiWicara.setRows(5);
        DiagnosaTerapiWicara.setName("DiagnosaTerapiWicara"); // NOI18N
        DiagnosaTerapiWicara.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosaTerapiWicaraKeyPressed(evt);
            }
        });
        scrollPane1.setViewportView(DiagnosaTerapiWicara);

        FormInput.add(scrollPane1);
        scrollPane1.setBounds(20, 100, 400, 43);

        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel30.setText("Diagnosa Terapi Wicara :");
        jLabel30.setName("jLabel30"); // NOI18N
        FormInput.add(jLabel30);
        jLabel30.setBounds(20, 80, 140, 23);

        scrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane2.setName("scrollPane2"); // NOI18N

        Anamnesa.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Anamnesa.setColumns(20);
        Anamnesa.setRows(5);
        Anamnesa.setName("Anamnesa"); // NOI18N
        Anamnesa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AnamnesaKeyPressed(evt);
            }
        });
        scrollPane2.setViewportView(Anamnesa);

        FormInput.add(scrollPane2);
        scrollPane2.setBounds(20, 170, 834, 53);

        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel31.setText("Anamnesa :");
        jLabel31.setName("jLabel31"); // NOI18N
        FormInput.add(jLabel31);
        jLabel31.setBounds(20, 150, 182, 23);

        scrollPane4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane4.setName("scrollPane4"); // NOI18N

        Menghisap.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Menghisap.setColumns(20);
        Menghisap.setRows(5);
        Menghisap.setName("Menghisap"); // NOI18N
        Menghisap.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MenghisapKeyPressed(evt);
            }
        });
        scrollPane4.setViewportView(Menghisap);

        FormInput.add(scrollPane4);
        scrollPane4.setBounds(80, 750, 245, 63);

        scrollPane3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane3.setName("scrollPane3"); // NOI18N

        DiagnosaMedis.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        DiagnosaMedis.setColumns(20);
        DiagnosaMedis.setRows(5);
        DiagnosaMedis.setName("DiagnosaMedis"); // NOI18N
        DiagnosaMedis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosaMedisKeyPressed(evt);
            }
        });
        scrollPane3.setViewportView(DiagnosaMedis);

        FormInput.add(scrollPane3);
        scrollPane3.setBounds(454, 100, 400, 43);

        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel32.setText("Diagnosa Medis :");
        jLabel32.setName("jLabel32"); // NOI18N
        FormInput.add(jLabel32);
        jLabel32.setBounds(454, 80, 140, 23);

        jLabel22.setText("Tensi Darah :");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(0, 250, 114, 23);

        TD.setFocusTraversalPolicyProvider(true);
        TD.setName("TD"); // NOI18N
        TD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TDKeyPressed(evt);
            }
        });
        FormInput.add(TD);
        TD.setBounds(118, 250, 86, 23);

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel23.setText("mmHg");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(207, 250, 50, 23);

        jLabel18.setText("Suhu :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(308, 250, 40, 23);

        Suhu.setFocusTraversalPolicyProvider(true);
        Suhu.setName("Suhu"); // NOI18N
        Suhu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SuhuKeyPressed(evt);
            }
        });
        FormInput.add(Suhu);
        Suhu.setBounds(352, 250, 65, 23);

        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel20.setText("°C");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(420, 250, 30, 23);

        jLabel17.setText("Nadi :");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(498, 250, 40, 23);

        Nadi.setFocusTraversalPolicyProvider(true);
        Nadi.setName("Nadi"); // NOI18N
        Nadi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NadiKeyPressed(evt);
            }
        });
        FormInput.add(Nadi);
        Nadi.setBounds(542, 250, 65, 23);

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel16.setText("x/menit");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput.add(jLabel16);
        jLabel16.setBounds(610, 250, 50, 23);

        jLabel26.setText("RR :");
        jLabel26.setName("jLabel26"); // NOI18N
        FormInput.add(jLabel26);
        jLabel26.setBounds(678, 250, 65, 23);

        RR.setFocusTraversalPolicyProvider(true);
        RR.setName("RR"); // NOI18N
        RR.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RRKeyPressed(evt);
            }
        });
        FormInput.add(RR);
        RR.setBounds(747, 250, 65, 23);

        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel25.setText("x/menit");
        jLabel25.setName("jLabel25"); // NOI18N
        FormInput.add(jLabel25);
        jLabel25.setBounds(815, 250, 50, 23);

        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel33.setText("Tanda-tanda Vital :");
        jLabel33.setName("jLabel33"); // NOI18N
        FormInput.add(jLabel33);
        jLabel33.setBounds(20, 230, 182, 23);

        jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel34.setText("Perilaku Adaptif :");
        jLabel34.setName("jLabel34"); // NOI18N
        FormInput.add(jLabel34);
        jLabel34.setBounds(20, 280, 182, 23);

        jLabel29.setText("Kontak Mata :");
        jLabel29.setName("jLabel29"); // NOI18N
        FormInput.add(jLabel29);
        jLabel29.setBounds(0, 300, 114, 23);

        KontakMata.setFocusTraversalPolicyProvider(true);
        KontakMata.setName("KontakMata"); // NOI18N
        KontakMata.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KontakMataKeyPressed(evt);
            }
        });
        FormInput.add(KontakMata);
        KontakMata.setBounds(118, 300, 325, 23);

        jLabel35.setText("Perilaku :");
        jLabel35.setName("jLabel35"); // NOI18N
        FormInput.add(jLabel35);
        jLabel35.setBounds(0, 330, 114, 23);

        Perilaku.setFocusTraversalPolicyProvider(true);
        Perilaku.setName("Perilaku"); // NOI18N
        Perilaku.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PerilakuKeyPressed(evt);
            }
        });
        FormInput.add(Perilaku);
        Perilaku.setBounds(118, 330, 325, 23);

        jLabel36.setText("Atensi :");
        jLabel36.setName("jLabel36"); // NOI18N
        FormInput.add(jLabel36);
        jLabel36.setBounds(445, 300, 80, 23);

        Atensi.setFocusTraversalPolicyProvider(true);
        Atensi.setName("Atensi"); // NOI18N
        Atensi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AtensiKeyPressed(evt);
            }
        });
        FormInput.add(Atensi);
        Atensi.setBounds(529, 300, 325, 23);

        jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel37.setText("Kemampuan Bahasa :");
        jLabel37.setName("jLabel37"); // NOI18N
        FormInput.add(jLabel37);
        jLabel37.setBounds(20, 360, 182, 23);

        jLabel38.setText("Pengujaran :");
        jLabel38.setName("jLabel38"); // NOI18N
        FormInput.add(jLabel38);
        jLabel38.setBounds(0, 380, 114, 23);

        Pengujaran.setFocusTraversalPolicyProvider(true);
        Pengujaran.setName("Pengujaran"); // NOI18N
        Pengujaran.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PengujaranKeyPressed(evt);
            }
        });
        FormInput.add(Pengujaran);
        Pengujaran.setBounds(118, 380, 300, 23);

        jLabel39.setText("Bicara Spontan :");
        jLabel39.setName("jLabel39"); // NOI18N
        FormInput.add(jLabel39);
        jLabel39.setBounds(430, 380, 120, 23);

        BicaraSpontan.setFocusTraversalPolicyProvider(true);
        BicaraSpontan.setName("BicaraSpontan"); // NOI18N
        BicaraSpontan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BicaraSpontanKeyPressed(evt);
            }
        });
        FormInput.add(BicaraSpontan);
        BicaraSpontan.setBounds(554, 380, 300, 23);

        jLabel40.setText("Membaca :");
        jLabel40.setName("jLabel40"); // NOI18N
        FormInput.add(jLabel40);
        jLabel40.setBounds(0, 410, 114, 23);

        Membaca.setFocusTraversalPolicyProvider(true);
        Membaca.setName("Membaca"); // NOI18N
        Membaca.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MembacaKeyPressed(evt);
            }
        });
        FormInput.add(Membaca);
        Membaca.setBounds(118, 410, 300, 23);

        jLabel41.setText("Pemahaman Bahasa :");
        jLabel41.setName("jLabel41"); // NOI18N
        FormInput.add(jLabel41);
        jLabel41.setBounds(430, 410, 120, 23);

        PemahamanBahasa.setFocusTraversalPolicyProvider(true);
        PemahamanBahasa.setName("PemahamanBahasa"); // NOI18N
        PemahamanBahasa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PemahamanBahasaKeyPressed(evt);
            }
        });
        FormInput.add(PemahamanBahasa);
        PemahamanBahasa.setBounds(554, 410, 300, 23);

        jLabel42.setText("Penamaan :");
        jLabel42.setName("jLabel42"); // NOI18N
        FormInput.add(jLabel42);
        jLabel42.setBounds(0, 440, 114, 23);

        Penamaan.setFocusTraversalPolicyProvider(true);
        Penamaan.setName("Penamaan"); // NOI18N
        Penamaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenamaanKeyPressed(evt);
            }
        });
        FormInput.add(Penamaan);
        Penamaan.setBounds(118, 440, 300, 23);

        jLabel43.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel43.setText("Wicara :");
        jLabel43.setName("jLabel43"); // NOI18N
        FormInput.add(jLabel43);
        jLabel43.setBounds(20, 470, 182, 23);

        jLabel44.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel44.setText("Organ Wicara Anatomis :");
        jLabel44.setName("jLabel44"); // NOI18N
        FormInput.add(jLabel44);
        jLabel44.setBounds(50, 490, 182, 23);

        jLabel45.setText("Lip :");
        jLabel45.setName("jLabel45"); // NOI18N
        FormInput.add(jLabel45);
        jLabel45.setBounds(0, 510, 142, 23);

        LipAnatomis.setFocusTraversalPolicyProvider(true);
        LipAnatomis.setName("LipAnatomis"); // NOI18N
        LipAnatomis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LipAnatomisKeyPressed(evt);
            }
        });
        FormInput.add(LipAnatomis);
        LipAnatomis.setBounds(146, 510, 180, 23);

        jLabel46.setText("Tongue :");
        jLabel46.setName("jLabel46"); // NOI18N
        FormInput.add(jLabel46);
        jLabel46.setBounds(326, 510, 70, 23);

        TongueAnatomis.setFocusTraversalPolicyProvider(true);
        TongueAnatomis.setName("TongueAnatomis"); // NOI18N
        TongueAnatomis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TongueAnatomisKeyPressed(evt);
            }
        });
        FormInput.add(TongueAnatomis);
        TongueAnatomis.setBounds(400, 510, 180, 23);

        jLabel47.setText("Hard Palate :");
        jLabel47.setName("jLabel47"); // NOI18N
        FormInput.add(jLabel47);
        jLabel47.setBounds(580, 510, 90, 23);

        HardPalateAnatomis.setFocusTraversalPolicyProvider(true);
        HardPalateAnatomis.setName("HardPalateAnatomis"); // NOI18N
        HardPalateAnatomis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HardPalateAnatomisKeyPressed(evt);
            }
        });
        FormInput.add(HardPalateAnatomis);
        HardPalateAnatomis.setBounds(674, 510, 180, 23);

        jLabel48.setText("Mandibula :");
        jLabel48.setName("jLabel48"); // NOI18N
        FormInput.add(jLabel48);
        jLabel48.setBounds(580, 540, 90, 23);

        MandibulaAnatomis.setFocusTraversalPolicyProvider(true);
        MandibulaAnatomis.setName("MandibulaAnatomis"); // NOI18N
        MandibulaAnatomis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MandibulaAnatomisKeyPressed(evt);
            }
        });
        FormInput.add(MandibulaAnatomis);
        MandibulaAnatomis.setBounds(674, 540, 180, 23);

        UvulaAnatomis.setFocusTraversalPolicyProvider(true);
        UvulaAnatomis.setName("UvulaAnatomis"); // NOI18N
        UvulaAnatomis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                UvulaAnatomisKeyPressed(evt);
            }
        });
        FormInput.add(UvulaAnatomis);
        UvulaAnatomis.setBounds(400, 540, 180, 23);

        jLabel49.setText("Uvula :");
        jLabel49.setName("jLabel49"); // NOI18N
        FormInput.add(jLabel49);
        jLabel49.setBounds(326, 540, 70, 23);

        SoftPalateAnatomis.setFocusTraversalPolicyProvider(true);
        SoftPalateAnatomis.setName("SoftPalateAnatomis"); // NOI18N
        SoftPalateAnatomis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SoftPalateAnatomisKeyPressed(evt);
            }
        });
        FormInput.add(SoftPalateAnatomis);
        SoftPalateAnatomis.setBounds(146, 540, 180, 23);

        jLabel50.setText("Soft Palate :");
        jLabel50.setName("jLabel50"); // NOI18N
        FormInput.add(jLabel50);
        jLabel50.setBounds(0, 540, 142, 23);

        jLabel51.setText("Faring :");
        jLabel51.setName("jLabel51"); // NOI18N
        FormInput.add(jLabel51);
        jLabel51.setBounds(580, 570, 90, 23);

        FaringAnatomis.setFocusTraversalPolicyProvider(true);
        FaringAnatomis.setName("FaringAnatomis"); // NOI18N
        FaringAnatomis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FaringAnatomisKeyPressed(evt);
            }
        });
        FormInput.add(FaringAnatomis);
        FaringAnatomis.setBounds(674, 570, 180, 23);

        DentalAnatomis.setFocusTraversalPolicyProvider(true);
        DentalAnatomis.setName("DentalAnatomis"); // NOI18N
        DentalAnatomis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DentalAnatomisKeyPressed(evt);
            }
        });
        FormInput.add(DentalAnatomis);
        DentalAnatomis.setBounds(400, 570, 180, 23);

        jLabel52.setText("Dental :");
        jLabel52.setName("jLabel52"); // NOI18N
        FormInput.add(jLabel52);
        jLabel52.setBounds(326, 570, 70, 23);

        MaxillaAnatomis.setFocusTraversalPolicyProvider(true);
        MaxillaAnatomis.setName("MaxillaAnatomis"); // NOI18N
        MaxillaAnatomis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MaxillaAnatomisKeyPressed(evt);
            }
        });
        FormInput.add(MaxillaAnatomis);
        MaxillaAnatomis.setBounds(146, 570, 180, 23);

        jLabel53.setText("Maxilla :");
        jLabel53.setName("jLabel53"); // NOI18N
        FormInput.add(jLabel53);
        jLabel53.setBounds(0, 570, 142, 23);

        jLabel54.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel54.setText("Organ Wicara Fisiologis :");
        jLabel54.setName("jLabel54"); // NOI18N
        FormInput.add(jLabel54);
        jLabel54.setBounds(50, 600, 182, 23);

        jLabel55.setText("Lip :");
        jLabel55.setName("jLabel55"); // NOI18N
        FormInput.add(jLabel55);
        jLabel55.setBounds(0, 620, 142, 23);

        LipFisiologis.setFocusTraversalPolicyProvider(true);
        LipFisiologis.setName("LipFisiologis"); // NOI18N
        LipFisiologis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LipFisiologisKeyPressed(evt);
            }
        });
        FormInput.add(LipFisiologis);
        LipFisiologis.setBounds(146, 620, 180, 23);

        jLabel56.setText("Tongue :");
        jLabel56.setName("jLabel56"); // NOI18N
        FormInput.add(jLabel56);
        jLabel56.setBounds(326, 620, 70, 23);

        TongueFisiologis.setFocusTraversalPolicyProvider(true);
        TongueFisiologis.setName("TongueFisiologis"); // NOI18N
        TongueFisiologis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TongueFisiologisKeyPressed(evt);
            }
        });
        FormInput.add(TongueFisiologis);
        TongueFisiologis.setBounds(400, 620, 180, 23);

        jLabel57.setText("Hard Palate :");
        jLabel57.setName("jLabel57"); // NOI18N
        FormInput.add(jLabel57);
        jLabel57.setBounds(580, 620, 90, 23);

        HardPalateFisiologis.setFocusTraversalPolicyProvider(true);
        HardPalateFisiologis.setName("HardPalateFisiologis"); // NOI18N
        HardPalateFisiologis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HardPalateFisiologisKeyPressed(evt);
            }
        });
        FormInput.add(HardPalateFisiologis);
        HardPalateFisiologis.setBounds(674, 620, 180, 23);

        jLabel58.setText("Soft Palate :");
        jLabel58.setName("jLabel58"); // NOI18N
        FormInput.add(jLabel58);
        jLabel58.setBounds(0, 650, 142, 23);

        SoftPalateFisiologis.setFocusTraversalPolicyProvider(true);
        SoftPalateFisiologis.setName("SoftPalateFisiologis"); // NOI18N
        SoftPalateFisiologis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SoftPalateFisiologisKeyPressed(evt);
            }
        });
        FormInput.add(SoftPalateFisiologis);
        SoftPalateFisiologis.setBounds(146, 650, 180, 23);

        jLabel59.setText("Uvula :");
        jLabel59.setName("jLabel59"); // NOI18N
        FormInput.add(jLabel59);
        jLabel59.setBounds(326, 650, 70, 23);

        UvulaFisiologis.setFocusTraversalPolicyProvider(true);
        UvulaFisiologis.setName("UvulaFisiologis"); // NOI18N
        UvulaFisiologis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                UvulaFisiologisKeyPressed(evt);
            }
        });
        FormInput.add(UvulaFisiologis);
        UvulaFisiologis.setBounds(400, 650, 180, 23);

        jLabel60.setText("Mandibula :");
        jLabel60.setName("jLabel60"); // NOI18N
        FormInput.add(jLabel60);
        jLabel60.setBounds(580, 650, 90, 23);

        MandibulaFisiologis.setFocusTraversalPolicyProvider(true);
        MandibulaFisiologis.setName("MandibulaFisiologis"); // NOI18N
        MandibulaFisiologis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MandibulaFisiologisKeyPressed(evt);
            }
        });
        FormInput.add(MandibulaFisiologis);
        MandibulaFisiologis.setBounds(674, 650, 180, 23);

        jLabel61.setText("Maxilla :");
        jLabel61.setName("jLabel61"); // NOI18N
        FormInput.add(jLabel61);
        jLabel61.setBounds(0, 680, 142, 23);

        MaxillaFisiologis.setFocusTraversalPolicyProvider(true);
        MaxillaFisiologis.setName("MaxillaFisiologis"); // NOI18N
        MaxillaFisiologis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MaxillaFisiologisKeyPressed(evt);
            }
        });
        FormInput.add(MaxillaFisiologis);
        MaxillaFisiologis.setBounds(146, 680, 180, 23);

        jLabel62.setText("Dental :");
        jLabel62.setName("jLabel62"); // NOI18N
        FormInput.add(jLabel62);
        jLabel62.setBounds(326, 680, 70, 23);

        DentalFisiologis.setFocusTraversalPolicyProvider(true);
        DentalFisiologis.setName("DentalFisiologis"); // NOI18N
        DentalFisiologis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DentalFisiologisKeyPressed(evt);
            }
        });
        FormInput.add(DentalFisiologis);
        DentalFisiologis.setBounds(400, 680, 180, 23);

        jLabel63.setText("Faring :");
        jLabel63.setName("jLabel63"); // NOI18N
        FormInput.add(jLabel63);
        jLabel63.setBounds(580, 680, 90, 23);

        FaringFisiologis.setFocusTraversalPolicyProvider(true);
        FaringFisiologis.setName("FaringFisiologis"); // NOI18N
        FaringFisiologis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FaringFisiologisKeyPressed(evt);
            }
        });
        FormInput.add(FaringFisiologis);
        FaringFisiologis.setBounds(674, 680, 180, 23);

        jLabel64.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel64.setText("Aktifitas Oral :");
        jLabel64.setName("jLabel64"); // NOI18N
        FormInput.add(jLabel64);
        jLabel64.setBounds(50, 710, 182, 23);

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel12.setText("Mengunyah :");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(345, 730, 200, 23);

        scrollPane5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane5.setName("scrollPane5"); // NOI18N

        Mengunyah.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Mengunyah.setColumns(20);
        Mengunyah.setRows(5);
        Mengunyah.setName("Mengunyah"); // NOI18N
        Mengunyah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MengunyahKeyPressed(evt);
            }
        });
        scrollPane5.setViewportView(Mengunyah);

        FormInput.add(scrollPane5);
        scrollPane5.setBounds(345, 750, 245, 63);

        scrollPane6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane6.setName("scrollPane6"); // NOI18N

        Meniup.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Meniup.setColumns(20);
        Meniup.setRows(5);
        Meniup.setName("Meniup"); // NOI18N
        Meniup.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MeniupKeyPressed(evt);
            }
        });
        scrollPane6.setViewportView(Meniup);

        FormInput.add(scrollPane6);
        scrollPane6.setBounds(609, 750, 245, 63);

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel13.setText("Meniup :");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(609, 730, 200, 23);

        jLabel65.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel65.setText("Kemampuan Artikulasi :");
        jLabel65.setName("jLabel65"); // NOI18N
        FormInput.add(jLabel65);
        jLabel65.setBounds(50, 820, 182, 23);

        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel14.setText("Subtitusi :");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(80, 840, 200, 23);

        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel15.setText("Omisi :");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(479, 840, 200, 23);

        scrollPane7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane7.setName("scrollPane7"); // NOI18N

        Subtitusi.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Subtitusi.setColumns(20);
        Subtitusi.setRows(5);
        Subtitusi.setName("Subtitusi"); // NOI18N
        Subtitusi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SubtitusiKeyPressed(evt);
            }
        });
        scrollPane7.setViewportView(Subtitusi);

        FormInput.add(scrollPane7);
        scrollPane7.setBounds(80, 860, 375, 43);

        scrollPane8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane8.setName("scrollPane8"); // NOI18N

        Omisi.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Omisi.setColumns(20);
        Omisi.setRows(5);
        Omisi.setName("Omisi"); // NOI18N
        Omisi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                OmisiKeyPressed(evt);
            }
        });
        scrollPane8.setViewportView(Omisi);

        FormInput.add(scrollPane8);
        scrollPane8.setBounds(479, 860, 375, 43);

        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel24.setText("Distorsi :");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(80, 910, 200, 23);

        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel27.setText("Adisi :");
        jLabel27.setName("jLabel27"); // NOI18N
        FormInput.add(jLabel27);
        jLabel27.setBounds(479, 910, 200, 23);

        scrollPane9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane9.setName("scrollPane9"); // NOI18N

        Distorsi.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Distorsi.setColumns(20);
        Distorsi.setRows(5);
        Distorsi.setName("Distorsi"); // NOI18N
        Distorsi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DistorsiKeyPressed(evt);
            }
        });
        scrollPane9.setViewportView(Distorsi);

        FormInput.add(scrollPane9);
        scrollPane9.setBounds(80, 930, 375, 43);

        scrollPane10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane10.setName("scrollPane10"); // NOI18N

        Adisi.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Adisi.setColumns(20);
        Adisi.setRows(5);
        Adisi.setName("Adisi"); // NOI18N
        Adisi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AdisiKeyPressed(evt);
            }
        });
        scrollPane10.setViewportView(Adisi);

        FormInput.add(scrollPane10);
        scrollPane10.setBounds(479, 930, 375, 43);

        jLabel67.setText(":");
        jLabel67.setName("jLabel67"); // NOI18N
        FormInput.add(jLabel67);
        jLabel67.setBounds(0, 980, 107, 23);

        Resonasi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Hiponasal", "Hipernasal", "Normal" }));
        Resonasi.setName("Resonasi"); // NOI18N
        Resonasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ResonasiKeyPressed(evt);
            }
        });
        FormInput.add(Resonasi);
        Resonasi.setBounds(111, 980, 110, 23);

        jLabel66.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel66.setText("Resonansi :");
        jLabel66.setName("jLabel66"); // NOI18N
        FormInput.add(jLabel66);
        jLabel66.setBounds(50, 980, 70, 23);

        jLabel68.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel68.setText("Kemampuan Suara :");
        jLabel68.setName("jLabel68"); // NOI18N
        FormInput.add(jLabel68);
        jLabel68.setBounds(20, 1010, 182, 23);

        jLabel69.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel69.setText("Nada");
        jLabel69.setName("jLabel69"); // NOI18N
        FormInput.add(jLabel69);
        jLabel69.setBounds(50, 1030, 40, 23);

        jLabel70.setText(":");
        jLabel70.setName("jLabel70"); // NOI18N
        FormInput.add(jLabel70);
        jLabel70.setBounds(0, 1030, 82, 23);

        Nada.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Resonansi", "Rendah", "Monoton", "Normal" }));
        Nada.setName("Nada"); // NOI18N
        Nada.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NadaKeyPressed(evt);
            }
        });
        FormInput.add(Nada);
        Nada.setBounds(86, 1030, 105, 23);

        jLabel71.setText("Kualitas :");
        jLabel71.setName("jLabel71"); // NOI18N
        FormInput.add(jLabel71);
        jLabel71.setBounds(190, 1030, 60, 23);

        Kualitas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Hoarssness", "Hassness", "Normal" }));
        Kualitas.setName("Kualitas"); // NOI18N
        Kualitas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KualitasKeyPressed(evt);
            }
        });
        FormInput.add(Kualitas);
        Kualitas.setBounds(254, 1030, 110, 23);

        KemampuanIramaKelancaran.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Gagap Primer", "Gagap Sekunder" }));
        KemampuanIramaKelancaran.setName("KemampuanIramaKelancaran"); // NOI18N
        KemampuanIramaKelancaran.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KemampuanIramaKelancaranKeyPressed(evt);
            }
        });
        FormInput.add(KemampuanIramaKelancaran);
        KemampuanIramaKelancaran.setBounds(724, 1010, 130, 23);

        jLabel72.setText("Kenyaringan :");
        jLabel72.setName("jLabel72"); // NOI18N
        FormInput.add(jLabel72);
        jLabel72.setBounds(362, 1030, 80, 23);

        jLabel73.setText("Kemampuan Irama Kelancaran :");
        jLabel73.setName("jLabel73"); // NOI18N
        FormInput.add(jLabel73);
        jLabel73.setBounds(520, 1010, 200, 23);

        Kenyaringan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Nyaring", "Tidak Nyaring" }));
        Kenyaringan.setName("Kenyaringan"); // NOI18N
        Kenyaringan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KenyaringanKeyPressed(evt);
            }
        });
        FormInput.add(Kenyaringan);
        Kenyaringan.setBounds(446, 1030, 120, 23);

        jLabel74.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel74.setText("Kemampuan Menelan :");
        jLabel74.setName("jLabel74"); // NOI18N
        FormInput.add(jLabel74);
        jLabel74.setBounds(20, 1060, 182, 23);

        scrollPane11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane11.setName("scrollPane11"); // NOI18N

        KemampuanMenelan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        KemampuanMenelan.setColumns(20);
        KemampuanMenelan.setRows(5);
        KemampuanMenelan.setName("KemampuanMenelan"); // NOI18N
        KemampuanMenelan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KemampuanMenelanKeyPressed(evt);
            }
        });
        scrollPane11.setViewportView(KemampuanMenelan);

        FormInput.add(scrollPane11);
        scrollPane11.setBounds(20, 1080, 834, 43);

        jLabel75.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel75.setText("Pernapasan :");
        jLabel75.setName("jLabel75"); // NOI18N
        FormInput.add(jLabel75);
        jLabel75.setBounds(20, 1130, 182, 23);

        scrollPane12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane12.setName("scrollPane12"); // NOI18N

        Pernapasan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Pernapasan.setColumns(20);
        Pernapasan.setRows(5);
        Pernapasan.setName("Pernapasan"); // NOI18N
        Pernapasan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PernapasanKeyPressed(evt);
            }
        });
        scrollPane12.setViewportView(Pernapasan);

        FormInput.add(scrollPane12);
        scrollPane12.setBounds(20, 1150, 834, 43);

        jLabel76.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel76.setText("Tingkat Komunikasi :");
        jLabel76.setName("jLabel76"); // NOI18N
        FormInput.add(jLabel76);
        jLabel76.setBounds(20, 1200, 182, 23);

        jLabel77.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel77.setText("Dekoding :");
        jLabel77.setName("jLabel77"); // NOI18N
        FormInput.add(jLabel77);
        jLabel77.setBounds(50, 1220, 182, 23);

        jLabel78.setText("S1 : Pendengaran");
        jLabel78.setName("jLabel78"); // NOI18N
        FormInput.add(jLabel78);
        jLabel78.setBounds(0, 1240, 166, 23);

        Pendengaran.setFocusTraversalPolicyProvider(true);
        Pendengaran.setName("Pendengaran"); // NOI18N
        Pendengaran.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PendengaranKeyPressed(evt);
            }
        });
        FormInput.add(Pendengaran);
        Pendengaran.setBounds(170, 1240, 290, 23);

        jLabel79.setText("S2 : Penglihatan");
        jLabel79.setName("jLabel79"); // NOI18N
        FormInput.add(jLabel79);
        jLabel79.setBounds(470, 1240, 90, 23);

        Penglihatan.setFocusTraversalPolicyProvider(true);
        Penglihatan.setName("Penglihatan"); // NOI18N
        Penglihatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenglihatanKeyPressed(evt);
            }
        });
        FormInput.add(Penglihatan);
        Penglihatan.setBounds(564, 1240, 290, 23);

        jLabel80.setText("S3 : Tak | Kinesek");
        jLabel80.setName("jLabel80"); // NOI18N
        FormInput.add(jLabel80);
        jLabel80.setBounds(0, 1270, 166, 23);

        Kinesek.setFocusTraversalPolicyProvider(true);
        Kinesek.setName("Kinesek"); // NOI18N
        Kinesek.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KinesekKeyPressed(evt);
            }
        });
        FormInput.add(Kinesek);
        Kinesek.setBounds(170, 1270, 290, 23);

        jLabel81.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel81.setText("Enkoding :");
        jLabel81.setName("jLabel81"); // NOI18N
        FormInput.add(jLabel81);
        jLabel81.setBounds(50, 1300, 182, 23);

        jLabel82.setText("T1 : Bicara");
        jLabel82.setName("jLabel82"); // NOI18N
        FormInput.add(jLabel82);
        jLabel82.setBounds(0, 1320, 136, 23);

        Bicara.setFocusTraversalPolicyProvider(true);
        Bicara.setName("Bicara"); // NOI18N
        Bicara.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BicaraKeyPressed(evt);
            }
        });
        FormInput.add(Bicara);
        Bicara.setBounds(140, 1320, 310, 23);

        jLabel83.setText("T2 : Tulisan");
        jLabel83.setName("jLabel83"); // NOI18N
        FormInput.add(jLabel83);
        jLabel83.setBounds(450, 1320, 90, 23);

        Tulisan.setFocusTraversalPolicyProvider(true);
        Tulisan.setName("Tulisan"); // NOI18N
        Tulisan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TulisanKeyPressed(evt);
            }
        });
        FormInput.add(Tulisan);
        Tulisan.setBounds(544, 1320, 310, 23);

        Gesture.setFocusTraversalPolicyProvider(true);
        Gesture.setName("Gesture"); // NOI18N
        Gesture.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GestureKeyPressed(evt);
            }
        });
        FormInput.add(Gesture);
        Gesture.setBounds(544, 1350, 310, 23);

        jLabel84.setText("T4 : Gesture");
        jLabel84.setName("jLabel84"); // NOI18N
        FormInput.add(jLabel84);
        jLabel84.setBounds(450, 1350, 90, 23);

        Mimik.setFocusTraversalPolicyProvider(true);
        Mimik.setName("Mimik"); // NOI18N
        Mimik.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MimikKeyPressed(evt);
            }
        });
        FormInput.add(Mimik);
        Mimik.setBounds(140, 1350, 310, 23);

        jLabel85.setText("T3 : Mimik");
        jLabel85.setName("jLabel85"); // NOI18N
        FormInput.add(jLabel85);
        jLabel85.setBounds(0, 1350, 136, 23);

        jLabel86.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel86.setText("Penunjang Medis :");
        jLabel86.setName("jLabel86"); // NOI18N
        FormInput.add(jLabel86);
        jLabel86.setBounds(20, 1380, 182, 23);

        scrollPane13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane13.setName("scrollPane13"); // NOI18N

        PenunjangMedis.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        PenunjangMedis.setColumns(20);
        PenunjangMedis.setRows(5);
        PenunjangMedis.setName("PenunjangMedis"); // NOI18N
        PenunjangMedis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenunjangMedisKeyPressed(evt);
            }
        });
        scrollPane13.setViewportView(PenunjangMedis);

        FormInput.add(scrollPane13);
        scrollPane13.setBounds(20, 1400, 834, 43);

        jLabel87.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel87.setText("Perencanaan Terapi Wicara :");
        jLabel87.setName("jLabel87"); // NOI18N
        FormInput.add(jLabel87);
        jLabel87.setBounds(20, 1450, 182, 23);

        jLabel88.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel88.setText("Tujuan Terapi Wicara :");
        jLabel88.setName("jLabel88"); // NOI18N
        FormInput.add(jLabel88);
        jLabel88.setBounds(50, 1470, 140, 23);

        scrollPane14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane14.setName("scrollPane14"); // NOI18N

        TujuanTerapiWicara.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        TujuanTerapiWicara.setColumns(20);
        TujuanTerapiWicara.setRows(5);
        TujuanTerapiWicara.setName("TujuanTerapiWicara"); // NOI18N
        TujuanTerapiWicara.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TujuanTerapiWicaraKeyPressed(evt);
            }
        });
        scrollPane14.setViewportView(TujuanTerapiWicara);

        FormInput.add(scrollPane14);
        scrollPane14.setBounds(50, 1490, 390, 53);

        jLabel89.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel89.setText("Program Terapi Wicara :");
        jLabel89.setName("jLabel89"); // NOI18N
        FormInput.add(jLabel89);
        jLabel89.setBounds(464, 1470, 140, 23);

        scrollPane15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane15.setName("scrollPane15"); // NOI18N

        ProgramTerapiWicara.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        ProgramTerapiWicara.setColumns(20);
        ProgramTerapiWicara.setRows(5);
        ProgramTerapiWicara.setName("ProgramTerapiWicara"); // NOI18N
        ProgramTerapiWicara.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ProgramTerapiWicaraKeyPressed(evt);
            }
        });
        scrollPane15.setViewportView(ProgramTerapiWicara);

        FormInput.add(scrollPane15);
        scrollPane15.setBounds(464, 1490, 390, 53);

        jLabel90.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel90.setText("Edukasi :");
        jLabel90.setName("jLabel90"); // NOI18N
        FormInput.add(jLabel90);
        jLabel90.setBounds(20, 1550, 182, 23);

        scrollPane16.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane16.setName("scrollPane16"); // NOI18N

        Edukasi.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Edukasi.setColumns(20);
        Edukasi.setRows(5);
        Edukasi.setName("Edukasi"); // NOI18N
        Edukasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EdukasiKeyPressed(evt);
            }
        });
        scrollPane16.setViewportView(Edukasi);

        FormInput.add(scrollPane16);
        scrollPane16.setBounds(20, 1570, 834, 43);

        jLabel91.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel91.setText("Tidak Lanjut :");
        jLabel91.setName("jLabel91"); // NOI18N
        FormInput.add(jLabel91);
        jLabel91.setBounds(20, 1620, 182, 23);

        scrollPane17.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane17.setName("scrollPane17"); // NOI18N

        TindakLanjut.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        TindakLanjut.setColumns(20);
        TindakLanjut.setRows(5);
        TindakLanjut.setName("TindakLanjut"); // NOI18N
        TindakLanjut.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TindakLanjutKeyPressed(evt);
            }
        });
        scrollPane17.setViewportView(TindakLanjut);

        FormInput.add(scrollPane17);
        scrollPane17.setBounds(20, 1640, 834, 43);

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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "28-01-2026" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "28-01-2026" }));
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

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isRawat();
        }else{            
            Valid.pindah(evt,TCari,BtnPetugas);
        }
}//GEN-LAST:event_TNoRwKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TNoRM.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"Nama Pasien");
        }else if(DiagnosaTerapiWicara.getText().trim().equals("")){
            Valid.textKosong(DiagnosaTerapiWicara,"Diagnosa Terapi Wicara");
        }else if(DiagnosaMedis.getText().trim().equals("")){
            Valid.textKosong(DiagnosaMedis,"Diagnosa Medis");
        }else if(Anamnesa.getText().trim().equals("")){
            Valid.textKosong(Anamnesa,"Anamnesa");
        }else if(NmPetugas.getText().trim().equals("")){
            Valid.textKosong(BtnPetugas,"Petugas");
        }else{
           if(Sequel.menyimpantf("penilaian_terapi_wicara","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat",62,new String[]{
                    TNoRw.getText(),Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),DiagnosaTerapiWicara.getText(),DiagnosaMedis.getText(),Anamnesa.getText(), 
                    Suhu.getText(),RR.getText(),Nadi.getText(),TD.getText(),KontakMata.getText(),Atensi.getText(),Perilaku.getText(),BicaraSpontan.getText(),PemahamanBahasa.getText(),Pengujaran.getText(),Membaca.getText(), 
                    Penamaan.getText(),LipAnatomis.getText(),TongueAnatomis.getText(),HardPalateAnatomis.getText(),SoftPalateAnatomis.getText(),UvulaAnatomis.getText(),MandibulaAnatomis.getText(),MaxillaAnatomis.getText(), 
                    DentalAnatomis.getText(),FaringAnatomis.getText(),LipFisiologis.getText(),TongueFisiologis.getText(),HardPalateFisiologis.getText(),SoftPalateFisiologis.getText(),UvulaFisiologis.getText(),
                    MandibulaFisiologis.getText(),MaxillaFisiologis.getText(),DentalFisiologis.getText(),FaringFisiologis.getText(),Menghisap.getText(),Mengunyah.getText(),Meniup.getText(),Subtitusi.getText(),Omisi.getText(),
                    Distorsi.getText(),Adisi.getText(),Resonasi.getSelectedItem().toString(),Nada.getSelectedItem().toString(),Kualitas.getSelectedItem().toString(),Kenyaringan.getSelectedItem().toString(),
                    KemampuanIramaKelancaran.getSelectedItem().toString(),KemampuanMenelan.getText(),Pernapasan.getText(),Pendengaran.getText(),Penglihatan.getText(),Kinesek.getText(),Bicara.getText(),Tulisan.getText(), 
                    Mimik.getText(),Gesture.getText(),PenunjangMedis.getText(),TujuanTerapiWicara.getText(),ProgramTerapiWicara.getText(),Edukasi.getText(),TindakLanjut.getText(),KdPetugas.getText()
                })==true){
                    tabMode.addRow(new Object[]{
                        TNoRw.getText(),TNoRM.getText(),TPasien.getText(),Jk.getText(),TglLahir.getText(),Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),
                        DiagnosaTerapiWicara.getText(),DiagnosaMedis.getText(),Anamnesa.getText(),Suhu.getText(),RR.getText(),Nadi.getText(),TD.getText(),KontakMata.getText(),Atensi.getText(),Perilaku.getText(),BicaraSpontan.getText(),
                        PemahamanBahasa.getText(),Pengujaran.getText(),Membaca.getText(),Penamaan.getText(),LipAnatomis.getText(),TongueAnatomis.getText(),HardPalateAnatomis.getText(),SoftPalateAnatomis.getText(),UvulaAnatomis.getText(),
                        MandibulaAnatomis.getText(),MaxillaAnatomis.getText(),DentalAnatomis.getText(),FaringAnatomis.getText(),LipFisiologis.getText(),TongueFisiologis.getText(),HardPalateFisiologis.getText(),SoftPalateFisiologis.getText(),
                        UvulaFisiologis.getText(),MandibulaFisiologis.getText(),MaxillaFisiologis.getText(),DentalFisiologis.getText(),FaringFisiologis.getText(),Menghisap.getText(),Mengunyah.getText(),Meniup.getText(),Subtitusi.getText(),
                        Omisi.getText(),Distorsi.getText(),Adisi.getText(),Resonasi.getSelectedItem().toString(),Nada.getSelectedItem().toString(),Kualitas.getSelectedItem().toString(),Kenyaringan.getSelectedItem().toString(),
                        KemampuanIramaKelancaran.getSelectedItem().toString(),KemampuanMenelan.getText(),Pernapasan.getText(),Pendengaran.getText(),Penglihatan.getText(),Kinesek.getText(),Bicara.getText(),Tulisan.getText(),Mimik.getText(),
                        Gesture.getText(),PenunjangMedis.getText(),TujuanTerapiWicara.getText(),ProgramTerapiWicara.getText(),Edukasi.getText(),TindakLanjut.getText(),KdPetugas.getText(),NmPetugas.getText()
                    });
                    LCount.setText(""+tabMode.getRowCount());
                    emptTeks();
            }
        }
    
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            //Valid.pindah(evt,Rencana,BtnBatal);
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
                if(KdPetugas.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),65).toString())){
                    hapus();
                }else{
                    JOptionPane.showMessageDialog(null,"Hanya bisa dihapus oleh petugas yang bersangkutan..!!");
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
        }else if(DiagnosaTerapiWicara.getText().trim().equals("")){
            Valid.textKosong(DiagnosaTerapiWicara,"Diagnosa Terapi Wicara");
        }else if(DiagnosaMedis.getText().trim().equals("")){
            Valid.textKosong(DiagnosaMedis,"Diagnosa Medis");
        }else if(Anamnesa.getText().trim().equals("")){
            Valid.textKosong(Anamnesa,"Anamnesa");
        }else if(NmPetugas.getText().trim().equals("")){
            Valid.textKosong(BtnPetugas,"Petugas");
        }else{
            if(tbObat.getSelectedRow()>-1){
                if(akses.getkode().equals("Admin Utama")){
                    ganti();
                }else{
                    if(KdPetugas.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),65).toString())){
                        ganti();
                    }else{
                        JOptionPane.showMessageDialog(null,"Hanya bisa diganti oleh petugas yang bersangkutan..!!");
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
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>J.K.</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Tgl.Lahir</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Tanggal</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Diagnosa Terapi Wicara</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Diagnosa Medis</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Anamnesa</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Suhu(°C)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>RR(x/menit)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Nadi(x/menit)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>TD(mmHg)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Kontak Mata</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Atensi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Perilaku</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Bicara Spontan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Pemahaman Bahasa</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Pengujaran</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Membaca</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Penamaan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Anatomis Lip</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Anatomis Tongue</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Anatomis Hard Palate</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Anatomis Soft Palate</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Anatomis Uvula</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Anatomis Mandibula</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Anatomis Maxila</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Anatomis Dental</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Anatomis Faring</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Fisiologis Lip</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Fisiologis Tongue</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Fisiologis Hard Palate</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Fisiologis Soft Palate</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Fisiologis Uvula</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Fisiologis Mandibula</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Fisiologis Maxilla</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Fisiologis Dental</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Fisiologis Faring</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Menghisap</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Mengunyah</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Meniup</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Subtitusi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Omisi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Distorsi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Adisi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Resonasi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Nada Suara</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Kualitas Suara</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Kenyaringan Suara</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Kemampuan Irama Kelancaran</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Kemampuan Menelan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Pernapasan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Dekoding Pendengaran</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Dekoding Penglihatan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Dekoding Kinesik</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Enkoding Bicara</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Enkoding Tulisan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Enkoding Mimik</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Enkoding Gesture</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Penunjang Medis</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Tujuan Terapi Wicara</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Program Terapi Wicara</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Edukasi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Tindak Lanjut</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>NIP</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Nama Petugas</b></td>"+
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
                            "<td valign='top'>"+tbObat.getValueAt(i,36).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,37).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,38).toString()+"</td>"+
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
                            "<td valign='top'>"+tbObat.getValueAt(i,59).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,60).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,61).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,62).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,63).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,64).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,65).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,66).toString()+"</td>"+
                        "</tr>");
                }

                LoadHTML.setText(
                    "<html>"+
                      "<table width='6000px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
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

                File f = new File("DataPenilaianTerapiWicara.html");            
                BufferedWriter bw = new BufferedWriter(new FileWriter(f));            
                bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                            "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                            "<table width='6000px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                "<tr class='isi2'>"+
                                    "<td valign='top' align='center'>"+
                                        "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                        akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                        akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                        "<font size='2' face='Tahoma'>DATA PENGKAJIAN TERAPI WICARA<br><br></font>"+        
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

    private void BtnPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPetugasKeyPressed
        //Valid.pindah(evt,Rencana,Informasi);
    }//GEN-LAST:event_BtnPetugasKeyPressed

    private void TglAsuhanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglAsuhanKeyPressed
        //Valid.pindah(evt,Rencana,Informasi);
    }//GEN-LAST:event_TglAsuhanKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        
    }//GEN-LAST:event_formWindowOpened

    private void DiagnosaTerapiWicaraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosaTerapiWicaraKeyPressed
        Valid.pindah2(evt,TglAsuhan,DiagnosaMedis);
    }//GEN-LAST:event_DiagnosaTerapiWicaraKeyPressed

    private void AnamnesaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AnamnesaKeyPressed
        Valid.pindah2(evt,DiagnosaMedis,TD);
    }//GEN-LAST:event_AnamnesaKeyPressed

    private void MenghisapKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MenghisapKeyPressed
        Valid.pindah2(evt,FaringFisiologis,Mengunyah);
    }//GEN-LAST:event_MenghisapKeyPressed

    private void MnPenilaianTerapiWicaraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPenilaianTerapiWicaraActionPerformed
        if(tbObat.getSelectedRow()>-1){
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
            finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",tbObat.getValueAt(tbObat.getSelectedRow(),46).toString());
            param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbObat.getValueAt(tbObat.getSelectedRow(),47).toString()+"\nID "+(finger.equals("")?tbObat.getValueAt(tbObat.getSelectedRow(),46).toString():finger)+"\n"+Valid.SetTgl3(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString()));

            Valid.MyReportqry("rptCetakPenilaianTerapiWicara.jasper","report","::[ Laporan Pengkajian Terapi Wicara ]::",
                "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penilaian_terapi_wicara.tanggal,penilaian_terapi_wicara.diagnosa_terapi_wicara,penilaian_terapi_wicara.diagnosa_medis,"+
                "penilaian_terapi_wicara.anamnesa,penilaian_terapi_wicara.suhu,penilaian_terapi_wicara.rr,penilaian_terapi_wicara.nadi,penilaian_terapi_wicara.td,penilaian_terapi_wicara.perilaku_adaptif_kontak_mata,penilaian_terapi_wicara.perilaku_adaptif_atensi,"+
                "penilaian_terapi_wicara.perilaku_adaptif_perilaku,penilaian_terapi_wicara.kemampuan_bahasa_bicara_spontan,penilaian_terapi_wicara.kemampuan_bahasa_pemahaman_bahasa,penilaian_terapi_wicara.kemampuan_bahasa_pengujaran,penilaian_terapi_wicara.kemampuan_bahasa_membaca,"+
                "penilaian_terapi_wicara.kemampuan_bahasa_penamaan,penilaian_terapi_wicara.organ_wicara_anatomis_lip,penilaian_terapi_wicara.organ_wicara_anatomis_tongue,penilaian_terapi_wicara.organ_wicara_anatomis_hard_palate,penilaian_terapi_wicara.organ_wicara_anatomis_soft_palate,"+
                "penilaian_terapi_wicara.organ_wicara_anatomis_uvula,penilaian_terapi_wicara.organ_wicara_anatomis_mandibula,penilaian_terapi_wicara.organ_wicara_anatomis_maxila,penilaian_terapi_wicara.organ_wicara_anatomis_dental,penilaian_terapi_wicara.organ_wicara_anatomis_faring,"+
                "penilaian_terapi_wicara.organ_wicara_fisiologis_lip,penilaian_terapi_wicara.organ_wicara_fisiologis_tongue,penilaian_terapi_wicara.organ_wicara_fisiologis_hard_palate,penilaian_terapi_wicara.organ_wicara_fisiologis_soft_palate,penilaian_terapi_wicara.organ_wicara_fisiologis_uvula,"+
                "penilaian_terapi_wicara.organ_wicara_fisiologis_mandibula,penilaian_terapi_wicara.organ_wicara_fisiologis_maxilla,penilaian_terapi_wicara.organ_wicara_fisiologis_dental,penilaian_terapi_wicara.organ_wicara_fisiologis_faring,penilaian_terapi_wicara.aktifitas_oral_menghisap,"+
                "penilaian_terapi_wicara.aktifitas_oral_mengunyah,penilaian_terapi_wicara.aktifitas_oral_meniup,penilaian_terapi_wicara.kemampuan_artikulasi_subtitusi,penilaian_terapi_wicara.kemampuan_artikulasi_omisi,penilaian_terapi_wicara.kemampuan_artikulasi_distorsi,"+
                "penilaian_terapi_wicara.kemampuan_artikulasi_adisi,penilaian_terapi_wicara.resonasi,penilaian_terapi_wicara.kemampuan_suara_nada,penilaian_terapi_wicara.kemampuan_suara_kualitas,penilaian_terapi_wicara.kemampuan_suara_kenyaringan,penilaian_terapi_wicara.kemampuan_irama_kelancaran,"+
                "penilaian_terapi_wicara.kemampuan_menelan,penilaian_terapi_wicara.pernafasan,penilaian_terapi_wicara.tingkat_komunikasi_dekoding_pendengaran,penilaian_terapi_wicara.tingkat_komunikasi_dekoding_penglihatan,penilaian_terapi_wicara.tingkat_komunikasi_dekoding_kinesik,"+
                "penilaian_terapi_wicara.tingkat_komunikasi_enkoding_bicara,penilaian_terapi_wicara.tingkat_komunikasi_enkoding_tulisan,penilaian_terapi_wicara.tingkat_komunikasi_enkoding_mimik,penilaian_terapi_wicara.tingkat_komunikasi_enkoding_gesture,penilaian_terapi_wicara.penunjang_medis,"+
                "penilaian_terapi_wicara.perencanaan_terapi_tujuan,penilaian_terapi_wicara.perencanaan_terapi_program,penilaian_terapi_wicara.edukasi,penilaian_terapi_wicara.tindak_lanjut,penilaian_terapi_wicara.nip,petugas.nama "+
                "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join penilaian_terapi_wicara on reg_periksa.no_rawat=penilaian_terapi_wicara.no_rawat "+
                "inner join petugas on penilaian_terapi_wicara.nip=petugas.nip where reg_periksa.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'",param);
        }
    }//GEN-LAST:event_MnPenilaianTerapiWicaraActionPerformed

    private void DiagnosaMedisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosaMedisKeyPressed
        Valid.pindah2(evt,DiagnosaTerapiWicara,Anamnesa);
    }//GEN-LAST:event_DiagnosaMedisKeyPressed

    private void TDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TDKeyPressed
        Valid.pindah(evt,Anamnesa,Suhu);
    }//GEN-LAST:event_TDKeyPressed

    private void SuhuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SuhuKeyPressed
        Valid.pindah(evt,TD,Nadi);
    }//GEN-LAST:event_SuhuKeyPressed

    private void NadiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NadiKeyPressed
        Valid.pindah(evt,Suhu,RR);
    }//GEN-LAST:event_NadiKeyPressed

    private void RRKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RRKeyPressed
        Valid.pindah(evt,Nadi,KontakMata);
    }//GEN-LAST:event_RRKeyPressed

    private void KontakMataKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KontakMataKeyPressed
        Valid.pindah(evt,RR,Atensi);
    }//GEN-LAST:event_KontakMataKeyPressed

    private void PerilakuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PerilakuKeyPressed
        Valid.pindah(evt,Atensi,Pengujaran);
    }//GEN-LAST:event_PerilakuKeyPressed

    private void AtensiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AtensiKeyPressed
        Valid.pindah(evt,KontakMata,Perilaku);
    }//GEN-LAST:event_AtensiKeyPressed

    private void PengujaranKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PengujaranKeyPressed
        Valid.pindah(evt,Perilaku,BicaraSpontan);
    }//GEN-LAST:event_PengujaranKeyPressed

    private void BicaraSpontanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BicaraSpontanKeyPressed
        Valid.pindah(evt,Pengujaran,Membaca);
    }//GEN-LAST:event_BicaraSpontanKeyPressed

    private void MembacaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MembacaKeyPressed
        Valid.pindah(evt,BicaraSpontan,PemahamanBahasa);
    }//GEN-LAST:event_MembacaKeyPressed

    private void PemahamanBahasaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PemahamanBahasaKeyPressed
        Valid.pindah(evt,Membaca,Penamaan);
    }//GEN-LAST:event_PemahamanBahasaKeyPressed

    private void PenamaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenamaanKeyPressed
        Valid.pindah(evt,PemahamanBahasa,LipAnatomis);
    }//GEN-LAST:event_PenamaanKeyPressed

    private void LipAnatomisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LipAnatomisKeyPressed
        Valid.pindah(evt,Penamaan,TongueAnatomis);
    }//GEN-LAST:event_LipAnatomisKeyPressed

    private void TongueAnatomisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TongueAnatomisKeyPressed
        Valid.pindah(evt,LipAnatomis,HardPalateAnatomis);
    }//GEN-LAST:event_TongueAnatomisKeyPressed

    private void HardPalateAnatomisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HardPalateAnatomisKeyPressed
        Valid.pindah(evt,TongueAnatomis,SoftPalateAnatomis);
    }//GEN-LAST:event_HardPalateAnatomisKeyPressed

    private void MandibulaAnatomisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MandibulaAnatomisKeyPressed
        Valid.pindah(evt,UvulaAnatomis,MaxillaAnatomis);
    }//GEN-LAST:event_MandibulaAnatomisKeyPressed

    private void UvulaAnatomisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_UvulaAnatomisKeyPressed
        Valid.pindah(evt,SoftPalateAnatomis,MandibulaAnatomis);
    }//GEN-LAST:event_UvulaAnatomisKeyPressed

    private void SoftPalateAnatomisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SoftPalateAnatomisKeyPressed
        Valid.pindah(evt,HardPalateAnatomis,UvulaAnatomis);
    }//GEN-LAST:event_SoftPalateAnatomisKeyPressed

    private void FaringAnatomisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FaringAnatomisKeyPressed
        Valid.pindah(evt,DentalAnatomis,LipFisiologis);
    }//GEN-LAST:event_FaringAnatomisKeyPressed

    private void DentalAnatomisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DentalAnatomisKeyPressed
        Valid.pindah(evt,MaxillaAnatomis,FaringAnatomis);
    }//GEN-LAST:event_DentalAnatomisKeyPressed

    private void MaxillaAnatomisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MaxillaAnatomisKeyPressed
        Valid.pindah(evt,MandibulaAnatomis,DentalAnatomis);
    }//GEN-LAST:event_MaxillaAnatomisKeyPressed

    private void LipFisiologisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LipFisiologisKeyPressed
        Valid.pindah(evt,FaringAnatomis,TongueFisiologis);
    }//GEN-LAST:event_LipFisiologisKeyPressed

    private void TongueFisiologisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TongueFisiologisKeyPressed
        Valid.pindah(evt,LipFisiologis,HardPalateFisiologis);
    }//GEN-LAST:event_TongueFisiologisKeyPressed

    private void HardPalateFisiologisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HardPalateFisiologisKeyPressed
        Valid.pindah(evt,TongueFisiologis,SoftPalateFisiologis);
    }//GEN-LAST:event_HardPalateFisiologisKeyPressed

    private void SoftPalateFisiologisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SoftPalateFisiologisKeyPressed
        Valid.pindah(evt,HardPalateFisiologis,UvulaFisiologis);
    }//GEN-LAST:event_SoftPalateFisiologisKeyPressed

    private void UvulaFisiologisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_UvulaFisiologisKeyPressed
        Valid.pindah(evt,SoftPalateFisiologis,MandibulaFisiologis);
    }//GEN-LAST:event_UvulaFisiologisKeyPressed

    private void MandibulaFisiologisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MandibulaFisiologisKeyPressed
        Valid.pindah(evt,UvulaFisiologis,MaxillaFisiologis);
    }//GEN-LAST:event_MandibulaFisiologisKeyPressed

    private void MaxillaFisiologisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MaxillaFisiologisKeyPressed
        Valid.pindah(evt,MandibulaFisiologis,DentalFisiologis);
    }//GEN-LAST:event_MaxillaFisiologisKeyPressed

    private void DentalFisiologisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DentalFisiologisKeyPressed
        Valid.pindah(evt,MaxillaFisiologis,FaringFisiologis);
    }//GEN-LAST:event_DentalFisiologisKeyPressed

    private void FaringFisiologisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FaringFisiologisKeyPressed
        Valid.pindah(evt,DentalFisiologis,Menghisap);
    }//GEN-LAST:event_FaringFisiologisKeyPressed

    private void MengunyahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MengunyahKeyPressed
        Valid.pindah2(evt,Menghisap,Meniup);
    }//GEN-LAST:event_MengunyahKeyPressed

    private void MeniupKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MeniupKeyPressed
        Valid.pindah2(evt,Mengunyah,Subtitusi);
    }//GEN-LAST:event_MeniupKeyPressed

    private void SubtitusiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SubtitusiKeyPressed
        Valid.pindah2(evt,Meniup,Omisi);
    }//GEN-LAST:event_SubtitusiKeyPressed

    private void OmisiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_OmisiKeyPressed
        Valid.pindah2(evt,Subtitusi,Distorsi);
    }//GEN-LAST:event_OmisiKeyPressed

    private void DistorsiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DistorsiKeyPressed
        Valid.pindah2(evt,Omisi,Adisi);
    }//GEN-LAST:event_DistorsiKeyPressed

    private void AdisiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AdisiKeyPressed
        Valid.pindah2(evt,Distorsi,Resonasi);
    }//GEN-LAST:event_AdisiKeyPressed

    private void ResonasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ResonasiKeyPressed
        Valid.pindah(evt,Adisi,Nada);
    }//GEN-LAST:event_ResonasiKeyPressed

    private void NadaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NadaKeyPressed
        Valid.pindah(evt,Resonasi,Kualitas);
    }//GEN-LAST:event_NadaKeyPressed

    private void KualitasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KualitasKeyPressed
        Valid.pindah(evt,Nada,Kenyaringan);
    }//GEN-LAST:event_KualitasKeyPressed

    private void KemampuanIramaKelancaranKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KemampuanIramaKelancaranKeyPressed
        Valid.pindah(evt,Kenyaringan,KemampuanMenelan);
    }//GEN-LAST:event_KemampuanIramaKelancaranKeyPressed

    private void KenyaringanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KenyaringanKeyPressed
        Valid.pindah(evt,Kualitas,KemampuanIramaKelancaran);
    }//GEN-LAST:event_KenyaringanKeyPressed

    private void KemampuanMenelanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KemampuanMenelanKeyPressed
        Valid.pindah2(evt,KemampuanIramaKelancaran,Pernapasan);
    }//GEN-LAST:event_KemampuanMenelanKeyPressed

    private void PernapasanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PernapasanKeyPressed
        Valid.pindah2(evt,KemampuanMenelan,Pendengaran);
    }//GEN-LAST:event_PernapasanKeyPressed

    private void PendengaranKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PendengaranKeyPressed
        Valid.pindah(evt,Pernapasan,Penglihatan);
    }//GEN-LAST:event_PendengaranKeyPressed

    private void PenglihatanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenglihatanKeyPressed
        Valid.pindah(evt,Pendengaran,Kinesek);
    }//GEN-LAST:event_PenglihatanKeyPressed

    private void KinesekKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KinesekKeyPressed
        Valid.pindah(evt,Penglihatan,Bicara);
    }//GEN-LAST:event_KinesekKeyPressed

    private void BicaraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BicaraKeyPressed
        Valid.pindah(evt,Kinesek,Tulisan);
    }//GEN-LAST:event_BicaraKeyPressed

    private void TulisanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TulisanKeyPressed
        Valid.pindah(evt,Bicara,Mimik);
    }//GEN-LAST:event_TulisanKeyPressed

    private void GestureKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GestureKeyPressed
        Valid.pindah(evt,Mimik,PenunjangMedis);
    }//GEN-LAST:event_GestureKeyPressed

    private void MimikKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MimikKeyPressed
        Valid.pindah(evt,Tulisan,Gesture);
    }//GEN-LAST:event_MimikKeyPressed

    private void PenunjangMedisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenunjangMedisKeyPressed
        Valid.pindah2(evt,Gesture,TujuanTerapiWicara);
    }//GEN-LAST:event_PenunjangMedisKeyPressed

    private void TujuanTerapiWicaraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TujuanTerapiWicaraKeyPressed
        Valid.pindah2(evt,PenunjangMedis,ProgramTerapiWicara);
    }//GEN-LAST:event_TujuanTerapiWicaraKeyPressed

    private void ProgramTerapiWicaraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ProgramTerapiWicaraKeyPressed
        Valid.pindah2(evt,TujuanTerapiWicara,Edukasi);
    }//GEN-LAST:event_ProgramTerapiWicaraKeyPressed

    private void EdukasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EdukasiKeyPressed
        Valid.pindah2(evt,ProgramTerapiWicara,TindakLanjut);
    }//GEN-LAST:event_EdukasiKeyPressed

    private void TindakLanjutKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TindakLanjutKeyPressed
        Valid.pindah2(evt,Edukasi,BtnSimpan);
    }//GEN-LAST:event_TindakLanjutKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMPenilaianTerapiWicara dialog = new RMPenilaianTerapiWicara(new javax.swing.JFrame(), true);
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
    private widget.TextArea Adisi;
    private widget.TextArea Anamnesa;
    private widget.TextBox Atensi;
    private widget.TextBox Bicara;
    private widget.TextBox BicaraSpontan;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPetugas;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.TextBox DentalAnatomis;
    private widget.TextBox DentalFisiologis;
    private widget.TextArea DiagnosaMedis;
    private widget.TextArea DiagnosaTerapiWicara;
    private widget.TextArea Distorsi;
    private widget.TextArea Edukasi;
    private widget.TextBox FaringAnatomis;
    private widget.TextBox FaringFisiologis;
    private widget.PanelBiasa FormInput;
    private widget.TextBox Gesture;
    private widget.TextBox HardPalateAnatomis;
    private widget.TextBox HardPalateFisiologis;
    private widget.TextBox Jk;
    private widget.TextBox KdPetugas;
    private widget.ComboBox KemampuanIramaKelancaran;
    private widget.TextArea KemampuanMenelan;
    private widget.ComboBox Kenyaringan;
    private widget.TextBox Kinesek;
    private widget.TextBox KontakMata;
    private widget.ComboBox Kualitas;
    private widget.Label LCount;
    private widget.TextBox LipAnatomis;
    private widget.TextBox LipFisiologis;
    private widget.editorpane LoadHTML;
    private widget.TextBox MandibulaAnatomis;
    private widget.TextBox MandibulaFisiologis;
    private widget.TextBox MaxillaAnatomis;
    private widget.TextBox MaxillaFisiologis;
    private widget.TextBox Membaca;
    private widget.TextArea Menghisap;
    private widget.TextArea Mengunyah;
    private widget.TextArea Meniup;
    private widget.TextBox Mimik;
    private javax.swing.JMenuItem MnPenilaianTerapiWicara;
    private widget.ComboBox Nada;
    private widget.TextBox Nadi;
    private widget.TextBox NmPetugas;
    private widget.TextArea Omisi;
    private widget.TextBox PemahamanBahasa;
    private widget.TextBox Penamaan;
    private widget.TextBox Pendengaran;
    private widget.TextBox Penglihatan;
    private widget.TextBox Pengujaran;
    private widget.TextArea PenunjangMedis;
    private widget.TextBox Perilaku;
    private widget.TextArea Pernapasan;
    private widget.TextArea ProgramTerapiWicara;
    private widget.TextBox RR;
    private widget.ComboBox Resonasi;
    private widget.ScrollPane Scroll;
    private widget.TextBox SoftPalateAnatomis;
    private widget.TextBox SoftPalateFisiologis;
    private widget.TextArea Subtitusi;
    private widget.TextBox Suhu;
    private widget.TextBox TCari;
    private widget.TextBox TD;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private javax.swing.JTabbedPane TabRawat;
    private widget.Tanggal TglAsuhan;
    private widget.TextBox TglLahir;
    private widget.TextArea TindakLanjut;
    private widget.TextBox TongueAnatomis;
    private widget.TextBox TongueFisiologis;
    private widget.TextArea TujuanTerapiWicara;
    private widget.TextBox Tulisan;
    private widget.TextBox UvulaAnatomis;
    private widget.TextBox UvulaFisiologis;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel12;
    private widget.Label jLabel13;
    private widget.Label jLabel14;
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
    private widget.Label jLabel25;
    private widget.Label jLabel26;
    private widget.Label jLabel27;
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
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JSeparator jSeparator1;
    private widget.Label label11;
    private widget.Label label14;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollInput;
    private widget.ScrollPane scrollPane1;
    private widget.ScrollPane scrollPane10;
    private widget.ScrollPane scrollPane11;
    private widget.ScrollPane scrollPane12;
    private widget.ScrollPane scrollPane13;
    private widget.ScrollPane scrollPane14;
    private widget.ScrollPane scrollPane15;
    private widget.ScrollPane scrollPane16;
    private widget.ScrollPane scrollPane17;
    private widget.ScrollPane scrollPane2;
    private widget.ScrollPane scrollPane3;
    private widget.ScrollPane scrollPane4;
    private widget.ScrollPane scrollPane5;
    private widget.ScrollPane scrollPane6;
    private widget.ScrollPane scrollPane7;
    private widget.ScrollPane scrollPane8;
    private widget.ScrollPane scrollPane9;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            if(TCari.getText().equals("")){
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penilaian_terapi_wicara.tanggal,penilaian_terapi_wicara.diagnosa_terapi_wicara,penilaian_terapi_wicara.diagnosa_medis,"+
                        "penilaian_terapi_wicara.anamnesa,penilaian_terapi_wicara.suhu,penilaian_terapi_wicara.rr,penilaian_terapi_wicara.nadi,penilaian_terapi_wicara.td,penilaian_terapi_wicara.perilaku_adaptif_kontak_mata,penilaian_terapi_wicara.perilaku_adaptif_atensi,"+
                        "penilaian_terapi_wicara.perilaku_adaptif_perilaku,penilaian_terapi_wicara.kemampuan_bahasa_bicara_spontan,penilaian_terapi_wicara.kemampuan_bahasa_pemahaman_bahasa,penilaian_terapi_wicara.kemampuan_bahasa_pengujaran,penilaian_terapi_wicara.kemampuan_bahasa_membaca,"+
                        "penilaian_terapi_wicara.kemampuan_bahasa_penamaan,penilaian_terapi_wicara.organ_wicara_anatomis_lip,penilaian_terapi_wicara.organ_wicara_anatomis_tongue,penilaian_terapi_wicara.organ_wicara_anatomis_hard_palate,penilaian_terapi_wicara.organ_wicara_anatomis_soft_palate,"+
                        "penilaian_terapi_wicara.organ_wicara_anatomis_uvula,penilaian_terapi_wicara.organ_wicara_anatomis_mandibula,penilaian_terapi_wicara.organ_wicara_anatomis_maxila,penilaian_terapi_wicara.organ_wicara_anatomis_dental,penilaian_terapi_wicara.organ_wicara_anatomis_faring,"+
                        "penilaian_terapi_wicara.organ_wicara_fisiologis_lip,penilaian_terapi_wicara.organ_wicara_fisiologis_tongue,penilaian_terapi_wicara.organ_wicara_fisiologis_hard_palate,penilaian_terapi_wicara.organ_wicara_fisiologis_soft_palate,penilaian_terapi_wicara.organ_wicara_fisiologis_uvula,"+
                        "penilaian_terapi_wicara.organ_wicara_fisiologis_mandibula,penilaian_terapi_wicara.organ_wicara_fisiologis_maxilla,penilaian_terapi_wicara.organ_wicara_fisiologis_dental,penilaian_terapi_wicara.organ_wicara_fisiologis_faring,penilaian_terapi_wicara.aktifitas_oral_menghisap,"+
                        "penilaian_terapi_wicara.aktifitas_oral_mengunyah,penilaian_terapi_wicara.aktifitas_oral_meniup,penilaian_terapi_wicara.kemampuan_artikulasi_subtitusi,penilaian_terapi_wicara.kemampuan_artikulasi_omisi,penilaian_terapi_wicara.kemampuan_artikulasi_distorsi,"+
                        "penilaian_terapi_wicara.kemampuan_artikulasi_adisi,penilaian_terapi_wicara.resonasi,penilaian_terapi_wicara.kemampuan_suara_nada,penilaian_terapi_wicara.kemampuan_suara_kualitas,penilaian_terapi_wicara.kemampuan_suara_kenyaringan,penilaian_terapi_wicara.kemampuan_irama_kelancaran,"+
                        "penilaian_terapi_wicara.kemampuan_menelan,penilaian_terapi_wicara.pernafasan,penilaian_terapi_wicara.tingkat_komunikasi_dekoding_pendengaran,penilaian_terapi_wicara.tingkat_komunikasi_dekoding_penglihatan,penilaian_terapi_wicara.tingkat_komunikasi_dekoding_kinesik,"+
                        "penilaian_terapi_wicara.tingkat_komunikasi_enkoding_bicara,penilaian_terapi_wicara.tingkat_komunikasi_enkoding_tulisan,penilaian_terapi_wicara.tingkat_komunikasi_enkoding_mimik,penilaian_terapi_wicara.tingkat_komunikasi_enkoding_gesture,penilaian_terapi_wicara.penunjang_medis,"+
                        "penilaian_terapi_wicara.perencanaan_terapi_tujuan,penilaian_terapi_wicara.perencanaan_terapi_program,penilaian_terapi_wicara.edukasi,penilaian_terapi_wicara.tindak_lanjut,penilaian_terapi_wicara.nip,petugas.nama "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join penilaian_terapi_wicara on reg_periksa.no_rawat=penilaian_terapi_wicara.no_rawat "+
                        "inner join petugas on penilaian_terapi_wicara.nip=petugas.nip where "+
                        "penilaian_terapi_wicara.tanggal between ? and ? order by penilaian_terapi_wicara.tanggal");
            }else{
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penilaian_terapi_wicara.tanggal,penilaian_terapi_wicara.diagnosa_terapi_wicara,penilaian_terapi_wicara.diagnosa_medis,"+
                        "penilaian_terapi_wicara.anamnesa,penilaian_terapi_wicara.suhu,penilaian_terapi_wicara.rr,penilaian_terapi_wicara.nadi,penilaian_terapi_wicara.td,penilaian_terapi_wicara.perilaku_adaptif_kontak_mata,penilaian_terapi_wicara.perilaku_adaptif_atensi,"+
                        "penilaian_terapi_wicara.perilaku_adaptif_perilaku,penilaian_terapi_wicara.kemampuan_bahasa_bicara_spontan,penilaian_terapi_wicara.kemampuan_bahasa_pemahaman_bahasa,penilaian_terapi_wicara.kemampuan_bahasa_pengujaran,penilaian_terapi_wicara.kemampuan_bahasa_membaca,"+
                        "penilaian_terapi_wicara.kemampuan_bahasa_penamaan,penilaian_terapi_wicara.organ_wicara_anatomis_lip,penilaian_terapi_wicara.organ_wicara_anatomis_tongue,penilaian_terapi_wicara.organ_wicara_anatomis_hard_palate,penilaian_terapi_wicara.organ_wicara_anatomis_soft_palate,"+
                        "penilaian_terapi_wicara.organ_wicara_anatomis_uvula,penilaian_terapi_wicara.organ_wicara_anatomis_mandibula,penilaian_terapi_wicara.organ_wicara_anatomis_maxila,penilaian_terapi_wicara.organ_wicara_anatomis_dental,penilaian_terapi_wicara.organ_wicara_anatomis_faring,"+
                        "penilaian_terapi_wicara.organ_wicara_fisiologis_lip,penilaian_terapi_wicara.organ_wicara_fisiologis_tongue,penilaian_terapi_wicara.organ_wicara_fisiologis_hard_palate,penilaian_terapi_wicara.organ_wicara_fisiologis_soft_palate,penilaian_terapi_wicara.organ_wicara_fisiologis_uvula,"+
                        "penilaian_terapi_wicara.organ_wicara_fisiologis_mandibula,penilaian_terapi_wicara.organ_wicara_fisiologis_maxilla,penilaian_terapi_wicara.organ_wicara_fisiologis_dental,penilaian_terapi_wicara.organ_wicara_fisiologis_faring,penilaian_terapi_wicara.aktifitas_oral_menghisap,"+
                        "penilaian_terapi_wicara.aktifitas_oral_mengunyah,penilaian_terapi_wicara.aktifitas_oral_meniup,penilaian_terapi_wicara.kemampuan_artikulasi_subtitusi,penilaian_terapi_wicara.kemampuan_artikulasi_omisi,penilaian_terapi_wicara.kemampuan_artikulasi_distorsi,"+
                        "penilaian_terapi_wicara.kemampuan_artikulasi_adisi,penilaian_terapi_wicara.resonasi,penilaian_terapi_wicara.kemampuan_suara_nada,penilaian_terapi_wicara.kemampuan_suara_kualitas,penilaian_terapi_wicara.kemampuan_suara_kenyaringan,penilaian_terapi_wicara.kemampuan_irama_kelancaran,"+
                        "penilaian_terapi_wicara.kemampuan_menelan,penilaian_terapi_wicara.pernafasan,penilaian_terapi_wicara.tingkat_komunikasi_dekoding_pendengaran,penilaian_terapi_wicara.tingkat_komunikasi_dekoding_penglihatan,penilaian_terapi_wicara.tingkat_komunikasi_dekoding_kinesik,"+
                        "penilaian_terapi_wicara.tingkat_komunikasi_enkoding_bicara,penilaian_terapi_wicara.tingkat_komunikasi_enkoding_tulisan,penilaian_terapi_wicara.tingkat_komunikasi_enkoding_mimik,penilaian_terapi_wicara.tingkat_komunikasi_enkoding_gesture,penilaian_terapi_wicara.penunjang_medis,"+
                        "penilaian_terapi_wicara.perencanaan_terapi_tujuan,penilaian_terapi_wicara.perencanaan_terapi_program,penilaian_terapi_wicara.edukasi,penilaian_terapi_wicara.tindak_lanjut,penilaian_terapi_wicara.nip,petugas.nama "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join penilaian_terapi_wicara on reg_periksa.no_rawat=penilaian_terapi_wicara.no_rawat "+
                        "inner join petugas on penilaian_terapi_wicara.nip=petugas.nip where penilaian_terapi_wicara.tanggal between ? and ? and "+
                        "(reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or "+
                        "penilaian_terapi_wicara.nip like ? or petugas.nama like ?) order by penilaian_terapi_wicara.tanggal");
            }
                
            try {
                if(TCari.getText().equals("")){
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
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("jk"),rs.getDate("tgl_lahir"),
                        rs.getString("tanggal"),rs.getString("diagnosa_terapi_wicara"),rs.getString("diagnosa_medis"),rs.getString("anamnesa"),rs.getString("suhu"),
                        rs.getString("rr"),rs.getString("nadi"),rs.getString("td"),rs.getString("perilaku_adaptif_kontak_mata"),rs.getString("perilaku_adaptif_atensi"),
                        rs.getString("perilaku_adaptif_perilaku"),rs.getString("kemampuan_bahasa_bicara_spontan"),rs.getString("kemampuan_bahasa_pemahaman_bahasa"),
                        rs.getString("kemampuan_bahasa_pengujaran"),rs.getString("kemampuan_bahasa_membaca"),rs.getString("kemampuan_bahasa_penamaan"),
                        rs.getString("organ_wicara_anatomis_lip"),rs.getString("organ_wicara_anatomis_tongue"),rs.getString("organ_wicara_anatomis_hard_palate"),
                        rs.getString("organ_wicara_anatomis_soft_palate"),rs.getString("organ_wicara_anatomis_uvula"),rs.getString("organ_wicara_anatomis_mandibula"),
                        rs.getString("organ_wicara_anatomis_maxila"),rs.getString("organ_wicara_anatomis_dental"),rs.getString("organ_wicara_anatomis_faring"),
                        rs.getString("organ_wicara_fisiologis_lip"),rs.getString("organ_wicara_fisiologis_tongue"),rs.getString("organ_wicara_fisiologis_hard_palate"),
                        rs.getString("organ_wicara_fisiologis_soft_palate"),rs.getString("organ_wicara_fisiologis_uvula"),rs.getString("organ_wicara_fisiologis_mandibula"),
                        rs.getString("organ_wicara_fisiologis_maxilla"),rs.getString("organ_wicara_fisiologis_dental"),rs.getString("organ_wicara_fisiologis_faring"),
                        rs.getString("aktifitas_oral_menghisap"),rs.getString("aktifitas_oral_mengunyah"),rs.getString("aktifitas_oral_meniup"),
                        rs.getString("kemampuan_artikulasi_subtitusi"),rs.getString("kemampuan_artikulasi_omisi"),rs.getString("kemampuan_artikulasi_distorsi"),
                        rs.getString("kemampuan_artikulasi_adisi"),rs.getString("resonasi"),rs.getString("kemampuan_suara_nada"),rs.getString("kemampuan_suara_kualitas"),
                        rs.getString("kemampuan_suara_kenyaringan"),rs.getString("kemampuan_irama_kelancaran"),rs.getString("kemampuan_menelan"),rs.getString("pernafasan"),
                        rs.getString("tingkat_komunikasi_dekoding_pendengaran"),rs.getString("tingkat_komunikasi_dekoding_penglihatan"),rs.getString("tingkat_komunikasi_dekoding_kinesik"),
                        rs.getString("tingkat_komunikasi_enkoding_bicara"),rs.getString("tingkat_komunikasi_enkoding_tulisan"),rs.getString("tingkat_komunikasi_enkoding_mimik"),
                        rs.getString("tingkat_komunikasi_enkoding_gesture"),rs.getString("penunjang_medis"),rs.getString("perencanaan_terapi_tujuan"),
                        rs.getString("perencanaan_terapi_program"),rs.getString("edukasi"),rs.getString("tindak_lanjut"),rs.getString("nip"),rs.getString("nama")
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
        DiagnosaTerapiWicara.setText("");
        DiagnosaMedis.setText("");
        Anamnesa.setText("");
        TD.setText("");
        Suhu.setText("");
        Nadi.setText("");
        RR.setText("");
        KontakMata.setText("");
        Atensi.setText("");
        Perilaku.setText("");
        Pengujaran.setText("");
        BicaraSpontan.setText("");
        Membaca.setText("");
        PemahamanBahasa.setText("");
        Penamaan.setText("");
        LipAnatomis.setText("");
        TongueAnatomis.setText("");
        HardPalateAnatomis.setText("");
        SoftPalateAnatomis.setText("");
        UvulaAnatomis.setText("");
        MandibulaAnatomis.setText("");
        MaxillaAnatomis.setText("");
        DentalAnatomis.setText("");
        FaringAnatomis.setText("");
        LipFisiologis.setText("");
        TongueFisiologis.setText("");
        HardPalateFisiologis.setText("");
        SoftPalateFisiologis.setText("");
        UvulaFisiologis.setText("");
        MandibulaFisiologis.setText("");
        MaxillaFisiologis.setText("");
        DentalFisiologis.setText("");
        Menghisap.setText("");
        Mengunyah.setText("");
        Meniup.setText("");
        Subtitusi.setText("");
        Omisi.setText("");
        Distorsi.setText("");
        Adisi.setText("");
        Resonasi.setSelectedItem(0);
        Nada.setSelectedIndex(0);
        Kualitas.setSelectedIndex(0);
        Kenyaringan.setSelectedIndex(0);
        KemampuanIramaKelancaran.setSelectedIndex(0);
        KemampuanMenelan.setText("");
        Pernapasan.setText("");
        Pendengaran.setText("");
        Penglihatan.setText("");
        Kinesek.setText("");
        Bicara.setText("");
        Tulisan.setText("");
        Mimik.setText("");
        Gesture.setText("");
        PenunjangMedis.setText("");
        TujuanTerapiWicara.setText("");
        ProgramTerapiWicara.setText("");
        Edukasi.setText("");
        TindakLanjut.setText("");
        TabRawat.setSelectedIndex(0);
        DiagnosaTerapiWicara.requestFocus();
    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()); 
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString()); 
            Jk.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString()); 
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString()); 
            DiagnosaTerapiWicara.setText(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString()); 
            DiagnosaMedis.setText(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString()); 
            Anamnesa.setText(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString()); 
            Suhu.setText(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString()); 
            RR.setText(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());  
            Nadi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());  
            TD.setText(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString()); 
            KontakMata.setText(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString()); 
            Atensi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString()); 
            Perilaku.setText(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString()); 
            BicaraSpontan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString()); 
            PemahamanBahasa.setText(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString()); 
            Pengujaran.setText(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString()); 
            Membaca.setText(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString()); 
            Penamaan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString()); 
            LipAnatomis.setText(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString()); 
            TongueAnatomis.setText(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString()); 
            HardPalateAnatomis.setText(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString()); 
            SoftPalateAnatomis.setText(tbObat.getValueAt(tbObat.getSelectedRow(),24).toString()); 
            UvulaAnatomis.setText(tbObat.getValueAt(tbObat.getSelectedRow(),25).toString()); 
            MandibulaAnatomis.setText(tbObat.getValueAt(tbObat.getSelectedRow(),26).toString()); 
            MaxillaAnatomis.setText(tbObat.getValueAt(tbObat.getSelectedRow(),27).toString()); 
            DentalAnatomis.setText(tbObat.getValueAt(tbObat.getSelectedRow(),28).toString()); 
            FaringAnatomis.setText(tbObat.getValueAt(tbObat.getSelectedRow(),29).toString()); 
            LipFisiologis.setText(tbObat.getValueAt(tbObat.getSelectedRow(),30).toString()); 
            TongueFisiologis.setText(tbObat.getValueAt(tbObat.getSelectedRow(),31).toString()); 
            HardPalateFisiologis.setText(tbObat.getValueAt(tbObat.getSelectedRow(),32).toString()); 
            SoftPalateFisiologis.setText(tbObat.getValueAt(tbObat.getSelectedRow(),33).toString()); 
            UvulaFisiologis.setText(tbObat.getValueAt(tbObat.getSelectedRow(),34).toString()); 
            MandibulaFisiologis.setText(tbObat.getValueAt(tbObat.getSelectedRow(),35).toString()); 
            MaxillaFisiologis.setText(tbObat.getValueAt(tbObat.getSelectedRow(),36).toString()); 
            DentalFisiologis.setText(tbObat.getValueAt(tbObat.getSelectedRow(),37).toString()); 
            FaringFisiologis.setText(tbObat.getValueAt(tbObat.getSelectedRow(),38).toString()); 
            Menghisap.setText(tbObat.getValueAt(tbObat.getSelectedRow(),39).toString()); 
            Mengunyah.setText(tbObat.getValueAt(tbObat.getSelectedRow(),40).toString()); 
            Meniup.setText(tbObat.getValueAt(tbObat.getSelectedRow(),41).toString()); 
            Subtitusi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),42).toString()); 
            Omisi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),43).toString()); 
            Distorsi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),44).toString()); 
            Adisi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),45).toString()); 
            Resonasi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),46).toString()); 
            Nada.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),47).toString()); 
            Kualitas.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),48).toString()); 
            Kenyaringan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),49).toString()); 
            KemampuanIramaKelancaran.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),50).toString()); 
            KemampuanMenelan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),51).toString()); 
            Pernapasan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),52).toString()); 
            Pendengaran.setText(tbObat.getValueAt(tbObat.getSelectedRow(),53).toString()); 
            Penglihatan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),54).toString()); 
            Kinesek.setText(tbObat.getValueAt(tbObat.getSelectedRow(),55).toString()); 
            Bicara.setText(tbObat.getValueAt(tbObat.getSelectedRow(),56).toString()); 
            Tulisan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),57).toString()); 
            Mimik.setText(tbObat.getValueAt(tbObat.getSelectedRow(),58).toString()); 
            Gesture.setText(tbObat.getValueAt(tbObat.getSelectedRow(),59).toString()); 
            PenunjangMedis.setText(tbObat.getValueAt(tbObat.getSelectedRow(),60).toString()); 
            TujuanTerapiWicara.setText(tbObat.getValueAt(tbObat.getSelectedRow(),61).toString()); 
            ProgramTerapiWicara.setText(tbObat.getValueAt(tbObat.getSelectedRow(),62).toString()); 
            Edukasi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),63).toString()); 
            TindakLanjut.setText(tbObat.getValueAt(tbObat.getSelectedRow(),64).toString()); 
            Valid.SetTgl2(TglAsuhan,tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
        }
    }

    private void isRawat() {
        try {
            ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rkm_medis,pasien.nm_pasien, if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,reg_periksa.tgl_registrasi "+
                    "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis where reg_periksa.no_rawat=?");
            try {
                ps.setString(1,TNoRw.getText());
                rs=ps.executeQuery();
                if(rs.next()){
                    TNoRM.setText(rs.getString("no_rkm_medis"));
                    TPasien.setText(rs.getString("nm_pasien"));
                    DTPCari1.setDate(rs.getDate("tgl_registrasi"));
                    Jk.setText(rs.getString("jk"));
                    TglLahir.setText(rs.getString("tgl_lahir"));
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
        BtnSimpan.setEnabled(akses.getpenilaian_terapi_wicara());
        BtnHapus.setEnabled(akses.getpenilaian_terapi_wicara());
        BtnEdit.setEnabled(akses.getpenilaian_terapi_wicara());
        BtnEdit.setEnabled(akses.getpenilaian_terapi_wicara());
        if(akses.getjml2()>=1){
            KdPetugas.setEditable(false);
            BtnPetugas.setEnabled(false);
            KdPetugas.setText(akses.getkode());
            NmPetugas.setText(Sequel.CariPetugas(KdPetugas.getText()));
            if(NmPetugas.getText().equals("")){
                KdPetugas.setText("");
                JOptionPane.showMessageDialog(null,"User login bukan petugas...!!");
            }
        }            
    }

    public void setTampil(){
       TabRawat.setSelectedIndex(1);
    }
    
    private void hapus() {
        if(Sequel.queryu2tf("delete from penilaian_terapi_wicara where no_rawat=?",1,new String[]{
            tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
        })==true){
            tabMode.removeRow(tbObat.getSelectedRow());
            LCount.setText(""+tabMode.getRowCount());
        }else{
            JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
        }
    }

    private void ganti() {
        if(Sequel.mengedittf("penilaian_terapi_wicara","no_rawat=?","no_rawat=?,tanggal=?,diagnosa_terapi_wicara=?,diagnosa_medis=?,anamnesa=?,suhu=?,rr=?,nadi=?,td=?,perilaku_adaptif_kontak_mata=?,perilaku_adaptif_atensi=?,"+
                "perilaku_adaptif_perilaku=?,kemampuan_bahasa_bicara_spontan=?,kemampuan_bahasa_pemahaman_bahasa=?,kemampuan_bahasa_pengujaran=?,kemampuan_bahasa_membaca=?,kemampuan_bahasa_penamaan=?,organ_wicara_anatomis_lip=?,"+
                "organ_wicara_anatomis_tongue=?,organ_wicara_anatomis_hard_palate=?,organ_wicara_anatomis_soft_palate=?,organ_wicara_anatomis_uvula=?,organ_wicara_anatomis_mandibula=?,organ_wicara_anatomis_maxila=?,"+
                "organ_wicara_anatomis_dental=?,organ_wicara_anatomis_faring=?,organ_wicara_fisiologis_lip=?,organ_wicara_fisiologis_tongue=?,organ_wicara_fisiologis_hard_palate=?,organ_wicara_fisiologis_soft_palate=?,"+
                "organ_wicara_fisiologis_uvula=?,organ_wicara_fisiologis_mandibula=?,organ_wicara_fisiologis_maxilla=?,organ_wicara_fisiologis_dental=?,organ_wicara_fisiologis_faring=?,aktifitas_oral_menghisap=?,"+
                "aktifitas_oral_mengunyah=?,aktifitas_oral_meniup=?,kemampuan_artikulasi_subtitusi=?,kemampuan_artikulasi_omisi=?,kemampuan_artikulasi_distorsi=?,kemampuan_artikulasi_adisi=?,resonasi=?,kemampuan_suara_nada=?,"+
                "kemampuan_suara_kualitas=?,kemampuan_suara_kenyaringan=?,kemampuan_irama_kelancaran=?,kemampuan_menelan=?,pernafasan=?,tingkat_komunikasi_dekoding_pendengaran=?,tingkat_komunikasi_dekoding_penglihatan=?,"+
                "tingkat_komunikasi_dekoding_kinesik=?,tingkat_komunikasi_enkoding_bicara=?,tingkat_komunikasi_enkoding_tulisan=?,tingkat_komunikasi_enkoding_mimik=?,tingkat_komunikasi_enkoding_gesture=?,penunjang_medis=?,"+
                "perencanaan_terapi_tujuan=?,perencanaan_terapi_program=?,edukasi=?,tindak_lanjut=?,nip=?",63,new String[]{
                TNoRw.getText(),Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),DiagnosaTerapiWicara.getText(),DiagnosaMedis.getText(),Anamnesa.getText(), 
                    Suhu.getText(),RR.getText(),Nadi.getText(),TD.getText(),KontakMata.getText(),Atensi.getText(),Perilaku.getText(),BicaraSpontan.getText(),PemahamanBahasa.getText(),Pengujaran.getText(),Membaca.getText(), 
                    Penamaan.getText(),LipAnatomis.getText(),TongueAnatomis.getText(),HardPalateAnatomis.getText(),SoftPalateAnatomis.getText(),UvulaAnatomis.getText(),MandibulaAnatomis.getText(),MaxillaAnatomis.getText(), 
                    DentalAnatomis.getText(),FaringAnatomis.getText(),LipFisiologis.getText(),TongueFisiologis.getText(),HardPalateFisiologis.getText(),SoftPalateFisiologis.getText(),UvulaFisiologis.getText(),
                    MandibulaFisiologis.getText(),MaxillaFisiologis.getText(),DentalFisiologis.getText(),FaringFisiologis.getText(),Menghisap.getText(),Mengunyah.getText(),Meniup.getText(),Subtitusi.getText(),Omisi.getText(),
                    Distorsi.getText(),Adisi.getText(),Resonasi.getSelectedItem().toString(),Nada.getSelectedItem().toString(),Kualitas.getSelectedItem().toString(),Kenyaringan.getSelectedItem().toString(),
                    KemampuanIramaKelancaran.getSelectedItem().toString(),KemampuanMenelan.getText(),Pernapasan.getText(),Pendengaran.getText(),Penglihatan.getText(),Kinesek.getText(),Bicara.getText(),Tulisan.getText(), 
                    Mimik.getText(),Gesture.getText(),PenunjangMedis.getText(),TujuanTerapiWicara.getText(),ProgramTerapiWicara.getText(),Edukasi.getText(),TindakLanjut.getText(),KdPetugas.getText(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
             })==true){
                tbObat.setValueAt(TNoRw.getText(),tbObat.getSelectedRow(),0);
                tbObat.setValueAt(TNoRM.getText(),tbObat.getSelectedRow(),1);
                tbObat.setValueAt(TPasien.getText(),tbObat.getSelectedRow(),2);
                tbObat.setValueAt(Jk.getText(),tbObat.getSelectedRow(),3);
                tbObat.setValueAt(TglLahir.getText(),tbObat.getSelectedRow(),4);
                tbObat.setValueAt(Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),tbObat.getSelectedRow(),5);
                tbObat.setValueAt(DiagnosaTerapiWicara.getText(),tbObat.getSelectedRow(),6);
                tbObat.setValueAt(DiagnosaMedis.getText(),tbObat.getSelectedRow(),7);
                tbObat.setValueAt(Anamnesa.getText(),tbObat.getSelectedRow(),8);
                tbObat.setValueAt(Suhu.getText(),tbObat.getSelectedRow(),9);
                tbObat.setValueAt(RR.getText(),tbObat.getSelectedRow(),10);
                tbObat.setValueAt(Nadi.getText(),tbObat.getSelectedRow(),11);
                tbObat.setValueAt(TD.getText(),tbObat.getSelectedRow(),12);
                tbObat.setValueAt(KontakMata.getText(),tbObat.getSelectedRow(),13);
                tbObat.setValueAt(Atensi.getText(),tbObat.getSelectedRow(),14);
                tbObat.setValueAt(Perilaku.getText(),tbObat.getSelectedRow(),15);
                tbObat.setValueAt(BicaraSpontan.getText(),tbObat.getSelectedRow(),16);
                tbObat.setValueAt(PemahamanBahasa.getText(),tbObat.getSelectedRow(),17);
                tbObat.setValueAt(Pengujaran.getText(),tbObat.getSelectedRow(),18);
                tbObat.setValueAt(Membaca.getText(),tbObat.getSelectedRow(),19);
                tbObat.setValueAt(Penamaan.getText(),tbObat.getSelectedRow(),20);
                tbObat.setValueAt(LipAnatomis.getText(),tbObat.getSelectedRow(),21);
                tbObat.setValueAt(TongueAnatomis.getText(),tbObat.getSelectedRow(),22);
                tbObat.setValueAt(HardPalateAnatomis.getText(),tbObat.getSelectedRow(),23);
                tbObat.setValueAt(SoftPalateAnatomis.getText(),tbObat.getSelectedRow(),24);
                tbObat.setValueAt(UvulaAnatomis.getText(),tbObat.getSelectedRow(),25);
                tbObat.setValueAt(MandibulaAnatomis.getText(),tbObat.getSelectedRow(),26);
                tbObat.setValueAt(MaxillaAnatomis.getText(),tbObat.getSelectedRow(),27);
                tbObat.setValueAt(DentalAnatomis.getText(),tbObat.getSelectedRow(),28);
                tbObat.setValueAt(FaringAnatomis.getText(),tbObat.getSelectedRow(),29);
                tbObat.setValueAt(LipFisiologis.getText(),tbObat.getSelectedRow(),30);
                tbObat.setValueAt(TongueFisiologis.getText(),tbObat.getSelectedRow(),31);
                tbObat.setValueAt(HardPalateFisiologis.getText(),tbObat.getSelectedRow(),32);
                tbObat.setValueAt(SoftPalateFisiologis.getText(),tbObat.getSelectedRow(),33);
                tbObat.setValueAt(UvulaFisiologis.getText(),tbObat.getSelectedRow(),34);
                tbObat.setValueAt(MandibulaFisiologis.getText(),tbObat.getSelectedRow(),35);
                tbObat.setValueAt(MaxillaFisiologis.getText(),tbObat.getSelectedRow(),36);
                tbObat.setValueAt(DentalFisiologis.getText(),tbObat.getSelectedRow(),37);
                tbObat.setValueAt(FaringFisiologis.getText(),tbObat.getSelectedRow(),38);
                tbObat.setValueAt(Menghisap.getText(),tbObat.getSelectedRow(),39);
                tbObat.setValueAt(Mengunyah.getText(),tbObat.getSelectedRow(),40);
                tbObat.setValueAt(Meniup.getText(),tbObat.getSelectedRow(),41);
                tbObat.setValueAt(Subtitusi.getText(),tbObat.getSelectedRow(),42);
                tbObat.setValueAt(Omisi.getText(),tbObat.getSelectedRow(),43);
                tbObat.setValueAt(Distorsi.getText(),tbObat.getSelectedRow(),44);
                tbObat.setValueAt(Adisi.getText(),tbObat.getSelectedRow(),45);
                tbObat.setValueAt(Resonasi.getSelectedItem().toString(),tbObat.getSelectedRow(),46);
                tbObat.setValueAt(Nada.getSelectedItem().toString(),tbObat.getSelectedRow(),47);
                tbObat.setValueAt(Kualitas.getSelectedItem().toString(),tbObat.getSelectedRow(),48);
                tbObat.setValueAt(Kenyaringan.getSelectedItem().toString(),tbObat.getSelectedRow(),49);
                tbObat.setValueAt(KemampuanIramaKelancaran.getSelectedItem().toString(),tbObat.getSelectedRow(),50);
                tbObat.setValueAt(KemampuanMenelan.getText(),tbObat.getSelectedRow(),51);
                tbObat.setValueAt(Pernapasan.getText(),tbObat.getSelectedRow(),52);
                tbObat.setValueAt(Pendengaran.getText(),tbObat.getSelectedRow(),53);
                tbObat.setValueAt(Penglihatan.getText(),tbObat.getSelectedRow(),54);
                tbObat.setValueAt(Kinesek.getText(),tbObat.getSelectedRow(),55);
                tbObat.setValueAt(Bicara.getText(),tbObat.getSelectedRow(),56);
                tbObat.setValueAt(Tulisan.getText(),tbObat.getSelectedRow(),57);
                tbObat.setValueAt(Mimik.getText(),tbObat.getSelectedRow(),58);
                tbObat.setValueAt(Gesture.getText(),tbObat.getSelectedRow(),59);
                tbObat.setValueAt(PenunjangMedis.getText(),tbObat.getSelectedRow(),60);
                tbObat.setValueAt(TujuanTerapiWicara.getText(),tbObat.getSelectedRow(),61);
                tbObat.setValueAt(ProgramTerapiWicara.getText(),tbObat.getSelectedRow(),62);
                tbObat.setValueAt(Edukasi.getText(),tbObat.getSelectedRow(),63);
                tbObat.setValueAt(TindakLanjut.getText(),tbObat.getSelectedRow(),64);
                tbObat.setValueAt(KdPetugas.getText(),tbObat.getSelectedRow(),65);
                tbObat.setValueAt(NmPetugas.getText(),tbObat.getSelectedRow(),66);
                emptTeks();
                TabRawat.setSelectedIndex(1);
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

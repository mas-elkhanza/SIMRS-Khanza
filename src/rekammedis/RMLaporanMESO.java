/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
import kepegawaian.DlgCariPetugas;


/**
 * 
 * @author windiartohugroho
 */
public final class RMLaporanMESO extends javax.swing.JDialog {
    private final DefaultTableModel tabMode,tabModeObat,tabModeObatData;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;
    private DlgCariPetugas petugas;
    private StringBuilder htmlContent;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private volatile boolean ceksukses = false;
    
    public RMLaporanMESO(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(628,674);
        
        tabMode=new DefaultTableModel(null,new Object[]{
                "No.Laporan","No.Rawat","No.R.M.","Nama Pasien","Tgl.Laporan","NIP","Pelapor","Tgl.Lahir","J.K.","Suku",
                "Pekerjaan","BB (kg)","Anamnesa","Penyakit Utama","Kesudahan Penyakit","Gg.Ginjal","Gg.Hati","Alergi",
                "Kondisi Medis Lain","Faktor Industri","Lain-lain","Ket.Lain-lain","Manifestasi ESO","Masalah Mutu Obat",
                "Tgl.Mula ESO","Kesudahan ESO","Tgl.Kesudahan ESO","Riwayat ESO","Keterangan Tambahan","Data Laboratorium",
                "Tgl.Lab","Naranjo 1","Skor 1","Naranjo 2","Skor 2","Naranjo 3","Skor 3","Naranjo 4","Skor 4","Naranjo 5",
                "Skor 5","Naranjo 6","Skor 6","Naranjo 7","Skor 7","Naranjo 8","Skor 8","Naranjo 9","Skor 9","Naranjo 10",
                "Skor 10","Skor Naranjo","Kategori Naranjo"
            }){
            @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 53; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(105);
            }else if(i==1){
                column.setPreferredWidth(105);
            }else if(i==2){
                column.setPreferredWidth(70);
            }else if(i==3){
                column.setPreferredWidth(150);
            }else if(i==4){
                column.setPreferredWidth(115);
            }else if(i==5){
                column.setPreferredWidth(80);
            }else if(i==6){
                column.setPreferredWidth(150);
            }else if(i==7){
                column.setPreferredWidth(65);
            }else if(i==8){
                column.setPreferredWidth(30);
            }else if(i==9){
                column.setPreferredWidth(100);
            }else if(i==10){
                column.setPreferredWidth(120);
            }else if(i==11){
                column.setPreferredWidth(45);
            }else if(i==12){
                column.setPreferredWidth(250);
            }else if(i==13){
                column.setPreferredWidth(200);
            }else if(i==14){
                column.setPreferredWidth(140);
            }else if(i==15){
                column.setPreferredWidth(60);
            }else if(i==16){
                column.setPreferredWidth(60);
            }else if(i==17){
                column.setPreferredWidth(50);
            }else if(i==18){
                column.setPreferredWidth(110);
            }else if(i==19){
                column.setPreferredWidth(90);
            }else if(i==20){
                column.setPreferredWidth(60);
            }else if(i==21){
                column.setPreferredWidth(150);
            }else if(i==22){
                column.setPreferredWidth(300);
            }else if(i==23){
                column.setPreferredWidth(200);
            }else if(i==24){
                column.setPreferredWidth(75);
            }else if(i==25){
                column.setPreferredWidth(140);
            }else if(i==26){
                column.setPreferredWidth(100);
            }else if(i==27){
                column.setPreferredWidth(200);
            }else if(i==28){
                column.setPreferredWidth(200);
            }else if(i==29){
                column.setPreferredWidth(200);
            }else if(i==30){
                column.setPreferredWidth(65);
            }else if(i==31){
                column.setPreferredWidth(85);
            }else if(i==32){
                column.setPreferredWidth(45);
            }else if(i==33){
                column.setPreferredWidth(85);
            }else if(i==34){
                column.setPreferredWidth(45);
            }else if(i==35){
                column.setPreferredWidth(85);
            }else if(i==36){
                column.setPreferredWidth(45);
            }else if(i==37){
                column.setPreferredWidth(85);
            }else if(i==38){
                column.setPreferredWidth(45);
            }else if(i==39){
                column.setPreferredWidth(85);
            }else if(i==40){
                column.setPreferredWidth(45);
            }else if(i==41){
                column.setPreferredWidth(85);
            }else if(i==42){
                column.setPreferredWidth(45);
            }else if(i==43){
                column.setPreferredWidth(85);
            }else if(i==44){
                column.setPreferredWidth(45);
            }else if(i==45){
                column.setPreferredWidth(85);
            }else if(i==46){
                column.setPreferredWidth(45);
            }else if(i==47){
                column.setPreferredWidth(85);
            }else if(i==48){
                column.setPreferredWidth(45);
            }else if(i==49){
                column.setPreferredWidth(85);
            }else if(i==50){
                column.setPreferredWidth(45);
            }else if(i==51){
                column.setPreferredWidth(70);
            }else if(i==52){
                column.setPreferredWidth(110);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());

        tabModeObatData=new DefaultTableModel(null,new Object[]{
                "Nama Obat","Bentuk Sediaan","JKN","No.Batch","Dicurigai","Cara","Dosis/Waktu","Tgl.Mulai","Tgl.Akhir","Indikasi"
            }){
            @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObatData.setModel(tabModeObatData);
        tbObatData.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObatData.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (i = 0; i < 10; i++) {
            TableColumn column = tbObatData.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(200);
            }else if(i==1){
                column.setPreferredWidth(100);
            }else if(i==2){
                column.setPreferredWidth(40);
            }else if(i==3){
                column.setPreferredWidth(80);
            }else if(i==4){
                column.setPreferredWidth(60);
            }else if(i==5){
                column.setPreferredWidth(80);
            }else if(i==6){
                column.setPreferredWidth(110);
            }else if(i==7){
                column.setPreferredWidth(75);
            }else if(i==8){
                column.setPreferredWidth(75);
            }else if(i==9){
                column.setPreferredWidth(200);
            }
        }
        tbObatData.setDefaultRenderer(Object.class, new WarnaTable());
        ChkAccor.setSelected(false);
        isMenu();
        
        tabModeObat=new DefaultTableModel(null,new Object[]{
                "Nama Obat","Bentuk Sediaan","JKN","No.Batch","Dicurigai","Cara","Dosis/Waktu","Tgl.Mulai","Tgl.Akhir","Indikasi"
            }){
            @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObatMeso.setModel(tabModeObat);
        tbObatMeso.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObatMeso.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 10; i++) {
            TableColumn column = tbObatMeso.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(200);
            }else if(i==1){
                column.setPreferredWidth(100);
            }else if(i==2){
                column.setPreferredWidth(40);
            }else if(i==3){
                column.setPreferredWidth(80);
            }else if(i==4){
                column.setPreferredWidth(60);
            }else if(i==5){
                column.setPreferredWidth(80);
            }else if(i==6){
                column.setPreferredWidth(110);
            }else if(i==7){
                column.setPreferredWidth(75);
            }else if(i==8){
                column.setPreferredWidth(75);
            }else if(i==9){
                column.setPreferredWidth(200);
            }
        }
        tbObatMeso.setDefaultRenderer(Object.class, new WarnaTable());
        
        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));    
        KdPetugas.setDocument(new batasInput((byte)20).getKata(KdPetugas));  
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        NoLaporan.setDocument(new batasInput((int)20).getKata(NoLaporan));
        BeratBadan.setDocument(new batasInput((int)6).getOnlyAngka(BeratBadan));
        Anamnesa.setDocument(new batasInput((int)500).getKata(Anamnesa));
        PenyakitUtama.setDocument(new batasInput((int)300).getKata(PenyakitUtama));
        KetLainLain.setDocument(new batasInput((int)100).getKata(KetLainLain));
        ManifestasiESO.setDocument(new batasInput((int)500).getKata(ManifestasiESO));
        MasalahMutu.setDocument(new batasInput((int)200).getKata(MasalahMutu));
        RiwayatESO.setDocument(new batasInput((int)300).getKata(RiwayatESO));
        NamaObat.setDocument(new batasInput((int)100).getKata(NamaObat));
        BentukSediaan.setDocument(new batasInput((int)30).getKata(BentukSediaan));
        NoBatch.setDocument(new batasInput((int)30).getKata(NoBatch));
        Cara.setDocument(new batasInput((int)30).getKata(Cara));
        DosisWaktu.setDocument(new batasInput((int)50).getKata(DosisWaktu));
        Indikasi.setDocument(new batasInput((int)100).getKata(Indikasi));
        KeteranganTambahan.setDocument(new batasInput((int)500).getKata(KeteranganTambahan));
        DataLab.setDocument(new batasInput((int)300).getKata(DataLab));

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
        jPopupMenu1 = new widget.PopupMenu();
        MnFormulir = new widget.MenuItem();
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
        TabRawat = new widget.TabPane();
        internalFrame2 = new widget.InternalFrame();
        scrollInput = new widget.ScrollPane();
        FormInput = new widget.PanelBiasa();
        jLabelRawat = new widget.Label();
        TNoRw = new widget.TextBox();
        TNoRM = new widget.TextBox();
        TPasien = new widget.TextBox();
        jLabelTgl = new widget.Label();
        Tanggal = new widget.Tanggal();
        jLabelPelapor = new widget.Label();
        KdPetugas = new widget.TextBox();
        NmPetugas = new widget.TextBox();
        BtnPetugas = new widget.Button();
        jLabelNo = new widget.Label();
        NoLaporan = new widget.TextBox();
        jSeparatorMeso1 = new javax.swing.JSeparator();
        jLabelH1 = new widget.Label();
        jLabelTglLahir = new widget.Label();
        TglLahir = new widget.TextBox();
        jLabelSuku = new widget.Label();
        Suku = new widget.TextBox();
        jLabelBB = new widget.Label();
        BeratBadan = new widget.TextBox();
        jLabelPekerjaan = new widget.Label();
        Pekerjaan = new widget.TextBox();
        jLabelKelamin = new widget.Label();
        Kelamin = new widget.TextBox();
        jLabelAnamnesa = new widget.Label();
        scroll_Anamnesa = new widget.ScrollPane();
        Anamnesa = new widget.TextArea();
        jLabelPenyakit = new widget.Label();
        scroll_PenyakitUtama = new widget.ScrollPane();
        PenyakitUtama = new widget.TextArea();
        jLabelKesPenyakit = new widget.Label();
        KesudahanPenyakit = new widget.ComboBox();
        GangguanGinjal = new widget.CekBox();
        jLabelLain = new widget.Label();
        GangguanHati = new widget.CekBox();
        Alergi = new widget.CekBox();
        KondisiMedisLain = new widget.CekBox();
        FaktorIndustri = new widget.CekBox();
        KetLainLain = new widget.TextBox();
        jSeparatorMeso2 = new javax.swing.JSeparator();
        jLabelH2 = new widget.Label();
        LainLain = new widget.CekBox();
        jLabelManifestasi = new widget.Label();
        scroll_ManifestasiESO = new widget.ScrollPane();
        ManifestasiESO = new widget.TextArea();
        jLabelMutu = new widget.Label();
        scroll_MasalahMutu = new widget.ScrollPane();
        MasalahMutu = new widget.TextArea();
        jLabelMulaESO = new widget.Label();
        TglMulaESO = new widget.Tanggal();
        jLabelKesESO = new widget.Label();
        KesudahanESO = new widget.ComboBox();
        jLabelTglKesESO = new widget.Label();
        TglKesudahanESO = new widget.Tanggal();
        jLabelRiwayat = new widget.Label();
        scroll_RiwayatESO = new widget.ScrollPane();
        RiwayatESO = new widget.TextArea();
        jSeparatorMeso3 = new javax.swing.JSeparator();
        jLabelH3 = new widget.Label();
        jLabelNamaObat = new widget.Label();
        NamaObat = new widget.TextBox();
        jLabelBentuk = new widget.Label();
        BentukSediaan = new widget.TextBox();
        jLabelBatch = new widget.Label();
        NoBatch = new widget.TextBox();
        jLabelObatJKN = new widget.Label();
        ObatJKN = new widget.ComboBox();
        ObatDicurigai = new widget.ComboBox();
        jLabelObatDicurigai = new widget.Label();
        jLabelCara = new widget.Label();
        Cara = new widget.TextBox();
        jLabelDosis = new widget.Label();
        DosisWaktu = new widget.TextBox();
        jLabelMulaObat = new widget.Label();
        TglMulaObat = new widget.Tanggal();
        jLabelAkhirObat = new widget.Label();
        TglAkhirObat = new widget.Tanggal();
        jLabelIndikasi = new widget.Label();
        Indikasi = new widget.TextBox();
        BtnTambahObat = new widget.Button();
        BtnHapusObat = new widget.Button();
        scrollObatMeso = new widget.ScrollPane();
        tbObatMeso = new widget.Table();
        jLabelKet = new widget.Label();
        scroll_KeteranganTambahan = new widget.ScrollPane();
        KeteranganTambahan = new widget.TextArea();
        jLabelLab = new widget.Label();
        scroll_DataLab = new widget.ScrollPane();
        DataLab = new widget.TextArea();
        jLabelTglLab = new widget.Label();
        TglLab = new widget.Tanggal();
        jSeparatorMeso4 = new javax.swing.JSeparator();
        jLabelH4 = new widget.Label();
        jLabelQ1 = new widget.Label();
        Naranjo1 = new widget.ComboBox();
        SkorN1 = new widget.TextBox();
        jLabelQ2 = new widget.Label();
        Naranjo2 = new widget.ComboBox();
        SkorN2 = new widget.TextBox();
        jLabelQ3 = new widget.Label();
        Naranjo3 = new widget.ComboBox();
        SkorN3 = new widget.TextBox();
        jLabelQ4 = new widget.Label();
        Naranjo4 = new widget.ComboBox();
        SkorN4 = new widget.TextBox();
        jLabelQ5 = new widget.Label();
        Naranjo5 = new widget.ComboBox();
        SkorN5 = new widget.TextBox();
        jLabelQ6 = new widget.Label();
        Naranjo6 = new widget.ComboBox();
        SkorN6 = new widget.TextBox();
        jLabelQ7 = new widget.Label();
        Naranjo7 = new widget.ComboBox();
        SkorN7 = new widget.TextBox();
        jLabelQ8 = new widget.Label();
        Naranjo8 = new widget.ComboBox();
        SkorN8 = new widget.TextBox();
        jLabelQ9 = new widget.Label();
        Naranjo9 = new widget.ComboBox();
        SkorN9 = new widget.TextBox();
        jLabelQ10 = new widget.Label();
        Naranjo10 = new widget.ComboBox();
        SkorN10 = new widget.TextBox();
        jLabelSkor = new widget.Label();
        SkorNaranjo = new widget.TextBox();
        jLabelKategori = new widget.Label();
        KategoriNaranjo = new widget.TextBox();
        jLabel33 = new widget.Label();
        jLabel34 = new widget.Label();
        jLabel35 = new widget.Label();
        jLabel36 = new widget.Label();
        jLabel37 = new widget.Label();
        jLabel38 = new widget.Label();
        jLabel39 = new widget.Label();
        jLabel40 = new widget.Label();
        jLabel41 = new widget.Label();
        jLabel42 = new widget.Label();
        jLabel43 = new widget.Label();
        jLabel45 = new widget.Label();
        jLabel46 = new widget.Label();
        jLabel47 = new widget.Label();
        jLabel44 = new widget.Label();
        jLabel48 = new widget.Label();
        internalFrame3 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbObat = new widget.Table();
        PanelAccor = new widget.PanelBiasa();
        ChkAccor = new widget.CekBox();
        FormMenu = new widget.PanelBiasa();
        jLabelPasienData = new widget.Label();
        TNoRMData = new widget.TextBox();
        TPasienData = new widget.TextBox();
        FormObatData = new widget.PanelBiasa();
        scrollObatData = new widget.ScrollPane();
        tbObatData = new widget.Table();
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

        MnFormulir.setBackground(new java.awt.Color(255, 255, 254));
        MnFormulir.setForeground(new java.awt.Color(50, 50, 50));
        MnFormulir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnFormulir.setText("Formulir Pelaporan MESO");
        MnFormulir.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnFormulir.setName("MnFormulir"); // NOI18N
        MnFormulir.setPreferredSize(new java.awt.Dimension(180, 26));
        MnFormulir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnFormulirActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnFormulir);

        TanggalRegistrasi.setName("TanggalRegistrasi"); // NOI18N
        TanggalRegistrasi.setHighlighter(null);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Laporan Monitoring Efek Samping Obat (MESO) ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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
        FormInput.setPreferredSize(new java.awt.Dimension(870, 1223));
        FormInput.setLayout(null);

        jLabelRawat.setText("No.Rawat :");
        jLabelRawat.setName("jLabelRawat"); // NOI18N
        FormInput.add(jLabelRawat);
        jLabelRawat.setBounds(0, 10, 70, 23);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(74, 10, 131, 23);

        TNoRM.setEditable(false);
        TNoRM.setName("TNoRM"); // NOI18N
        FormInput.add(TNoRM);
        TNoRM.setBounds(207, 10, 100, 23);

        TPasien.setEditable(false);
        TPasien.setName("TPasien"); // NOI18N
        FormInput.add(TPasien);
        TPasien.setBounds(309, 10, 260, 23);

        jLabelTgl.setText("Tanggal :");
        jLabelTgl.setName("jLabelTgl"); // NOI18N
        FormInput.add(jLabelTgl);
        jLabelTgl.setBounds(640, 40, 70, 23);

        Tanggal.setForeground(new java.awt.Color(50, 70, 50));
        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "23-11-2026 15:43:01" }));
        Tanggal.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeyPressed(evt);
            }
        });
        FormInput.add(Tanggal);
        Tanggal.setBounds(714, 40, 140, 23);

        jLabelPelapor.setText("Pelapor :");
        jLabelPelapor.setName("jLabelPelapor"); // NOI18N
        FormInput.add(jLabelPelapor);
        jLabelPelapor.setBounds(0, 70, 70, 23);

        KdPetugas.setEditable(false);
        KdPetugas.setName("KdPetugas"); // NOI18N
        KdPetugas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdPetugasKeyPressed(evt);
            }
        });
        FormInput.add(KdPetugas);
        KdPetugas.setBounds(74, 70, 125, 23);

        NmPetugas.setEditable(false);
        NmPetugas.setName("NmPetugas"); // NOI18N
        FormInput.add(NmPetugas);
        NmPetugas.setBounds(201, 70, 377, 23);

        BtnPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPetugas.setMnemonic('2');
        BtnPetugas.setToolTipText("ALt+2");
        BtnPetugas.setName("BtnPetugas"); // NOI18N
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
        BtnPetugas.setBounds(580, 70, 28, 23);

        jLabelNo.setText("No.Laporan :");
        jLabelNo.setName("jLabelNo"); // NOI18N
        FormInput.add(jLabelNo);
        jLabelNo.setBounds(640, 70, 70, 23);

        NoLaporan.setName("NoLaporan"); // NOI18N
        NoLaporan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoLaporanKeyPressed(evt);
            }
        });
        FormInput.add(NoLaporan);
        NoLaporan.setBounds(714, 70, 140, 23);

        jSeparatorMeso1.setBackground(new java.awt.Color(239, 244, 234));
        jSeparatorMeso1.setForeground(new java.awt.Color(239, 244, 234));
        jSeparatorMeso1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparatorMeso1.setName("jSeparatorMeso1"); // NOI18N
        FormInput.add(jSeparatorMeso1);
        jSeparatorMeso1.setBounds(0, 100, 880, 1);

        jLabelH1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelH1.setText("I. HASIL PEMERIKSAAN");
        jLabelH1.setName("jLabelH1"); // NOI18N
        FormInput.add(jLabelH1);
        jLabelH1.setBounds(10, 100, 180, 23);

        jLabelTglLahir.setText("Tgl.Lahir :");
        jLabelTglLahir.setName("jLabelTglLahir"); // NOI18N
        FormInput.add(jLabelTglLahir);
        jLabelTglLahir.setBounds(580, 10, 60, 23);

        TglLahir.setEditable(false);
        TglLahir.setName("TglLahir"); // NOI18N
        FormInput.add(TglLahir);
        TglLahir.setBounds(644, 10, 80, 23);

        jLabelSuku.setText("Suku :");
        jLabelSuku.setName("jLabelSuku"); // NOI18N
        FormInput.add(jLabelSuku);
        jLabelSuku.setBounds(316, 40, 40, 23);

        Suku.setEditable(false);
        Suku.setName("Suku"); // NOI18N
        FormInput.add(Suku);
        Suku.setBounds(360, 40, 150, 23);

        jLabelBB.setText("BB (kg) :");
        jLabelBB.setName("jLabelBB"); // NOI18N
        FormInput.add(jLabelBB);
        jLabelBB.setBounds(526, 40, 55, 23);

        BeratBadan.setName("BeratBadan"); // NOI18N
        BeratBadan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BeratBadanKeyPressed(evt);
            }
        });
        FormInput.add(BeratBadan);
        BeratBadan.setBounds(585, 40, 50, 23);

        jLabelPekerjaan.setText("Pekerjaan :");
        jLabelPekerjaan.setName("jLabelPekerjaan"); // NOI18N
        FormInput.add(jLabelPekerjaan);
        jLabelPekerjaan.setBounds(0, 40, 70, 23);

        Pekerjaan.setEditable(false);
        Pekerjaan.setName("Pekerjaan"); // NOI18N
        FormInput.add(Pekerjaan);
        Pekerjaan.setBounds(74, 40, 216, 23);

        jLabelKelamin.setText("J.K. :");
        jLabelKelamin.setName("jLabelKelamin"); // NOI18N
        FormInput.add(jLabelKelamin);
        jLabelKelamin.setBounds(740, 10, 30, 23);

        Kelamin.setEditable(false);
        Kelamin.setName("Kelamin"); // NOI18N
        FormInput.add(Kelamin);
        Kelamin.setBounds(774, 10, 80, 23);

        jLabelAnamnesa.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelAnamnesa.setText("Anamnesa");
        jLabelAnamnesa.setName("jLabelAnamnesa"); // NOI18N
        FormInput.add(jLabelAnamnesa);
        jLabelAnamnesa.setBounds(44, 120, 146, 23);

        scroll_Anamnesa.setName("scroll_Anamnesa"); // NOI18N

        Anamnesa.setColumns(20);
        Anamnesa.setRows(5);
        Anamnesa.setName("Anamnesa"); // NOI18N
        Anamnesa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AnamnesaKeyPressed(evt);
            }
        });
        scroll_Anamnesa.setViewportView(Anamnesa);

        FormInput.add(scroll_Anamnesa);
        scroll_Anamnesa.setBounds(106, 120, 748, 43);

        jLabelPenyakit.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelPenyakit.setText("Penyakit Utama");
        jLabelPenyakit.setName("jLabelPenyakit"); // NOI18N
        FormInput.add(jLabelPenyakit);
        jLabelPenyakit.setBounds(44, 170, 130, 23);

        scroll_PenyakitUtama.setName("scroll_PenyakitUtama"); // NOI18N

        PenyakitUtama.setColumns(20);
        PenyakitUtama.setRows(5);
        PenyakitUtama.setName("PenyakitUtama"); // NOI18N
        PenyakitUtama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenyakitUtamaKeyPressed(evt);
            }
        });
        scroll_PenyakitUtama.setViewportView(PenyakitUtama);

        FormInput.add(scroll_PenyakitUtama);
        scroll_PenyakitUtama.setBounds(131, 170, 723, 43);

        jLabelKesPenyakit.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelKesPenyakit.setText("Kesudahan Penyakit Utama");
        jLabelKesPenyakit.setName("jLabelKesPenyakit"); // NOI18N
        FormInput.add(jLabelKesPenyakit);
        jLabelKesPenyakit.setBounds(44, 280, 146, 23);

        KesudahanPenyakit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Sembuh", "Sembuh Dengan Gejala Sisa", "Belum Sembuh", "Meninggal", "Tidak Tahu" }));
        KesudahanPenyakit.setName("KesudahanPenyakit"); // NOI18N
        KesudahanPenyakit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KesudahanPenyakitKeyPressed(evt);
            }
        });
        FormInput.add(KesudahanPenyakit);
        KesudahanPenyakit.setBounds(187, 280, 180, 23);

        GangguanGinjal.setText("Gangguan Ginjal");
        GangguanGinjal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        GangguanGinjal.setName("GangguanGinjal"); // NOI18N
        GangguanGinjal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GangguanGinjalKeyPressed(evt);
            }
        });
        FormInput.add(GangguanGinjal);
        GangguanGinjal.setBounds(242, 220, 110, 23);

        jLabelLain.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelLain.setText("Penyakit / Kondisi Lain Yang Menyertai");
        jLabelLain.setName("jLabelLain"); // NOI18N
        FormInput.add(jLabelLain);
        jLabelLain.setBounds(44, 220, 210, 23);

        GangguanHati.setText("Gangguan Hati");
        GangguanHati.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        GangguanHati.setName("GangguanHati"); // NOI18N
        GangguanHati.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GangguanHatiKeyPressed(evt);
            }
        });
        FormInput.add(GangguanHati);
        GangguanHati.setBounds(358, 220, 102, 23);

        Alergi.setText("Alergi");
        Alergi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Alergi.setName("Alergi"); // NOI18N
        Alergi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlergiKeyPressed(evt);
            }
        });
        FormInput.add(Alergi);
        Alergi.setBounds(466, 220, 60, 23);

        KondisiMedisLain.setText("Kondisi Medis Lainnya");
        KondisiMedisLain.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        KondisiMedisLain.setName("KondisiMedisLain"); // NOI18N
        KondisiMedisLain.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KondisiMedisLainKeyPressed(evt);
            }
        });
        FormInput.add(KondisiMedisLain);
        KondisiMedisLain.setBounds(531, 220, 135, 23);

        FaktorIndustri.setText("Faktor Industri, Pertanian, Kimia");
        FaktorIndustri.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        FaktorIndustri.setName("FaktorIndustri"); // NOI18N
        FaktorIndustri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FaktorIndustriKeyPressed(evt);
            }
        });
        FormInput.add(FaktorIndustri);
        FaktorIndustri.setBounds(673, 220, 200, 23);

        KetLainLain.setName("KetLainLain"); // NOI18N
        KetLainLain.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetLainLainKeyPressed(evt);
            }
        });
        FormInput.add(KetLainLain);
        KetLainLain.setBounds(315, 250, 539, 23);

        jSeparatorMeso2.setBackground(new java.awt.Color(239, 244, 234));
        jSeparatorMeso2.setForeground(new java.awt.Color(239, 244, 234));
        jSeparatorMeso2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparatorMeso2.setName("jSeparatorMeso2"); // NOI18N
        FormInput.add(jSeparatorMeso2);
        jSeparatorMeso2.setBounds(0, 310, 880, 1);

        jLabelH2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelH2.setText("II. EFEK SAMPING OBAT");
        jLabelH2.setName("jLabelH2"); // NOI18N
        FormInput.add(jLabelH2);
        jLabelH2.setBounds(10, 310, 180, 23);

        LainLain.setText("Lain-lain");
        LainLain.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LainLain.setName("LainLain"); // NOI18N
        LainLain.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LainLainKeyPressed(evt);
            }
        });
        FormInput.add(LainLain);
        LainLain.setBounds(242, 250, 71, 23);

        jLabelManifestasi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelManifestasi.setText("Manifestasi ESO");
        jLabelManifestasi.setName("jLabelManifestasi"); // NOI18N
        FormInput.add(jLabelManifestasi);
        jLabelManifestasi.setBounds(44, 330, 101, 23);

        scroll_ManifestasiESO.setName("scroll_ManifestasiESO"); // NOI18N

        ManifestasiESO.setColumns(20);
        ManifestasiESO.setRows(5);
        ManifestasiESO.setName("ManifestasiESO"); // NOI18N
        ManifestasiESO.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ManifestasiESOKeyPressed(evt);
            }
        });
        scroll_ManifestasiESO.setViewportView(ManifestasiESO);

        FormInput.add(scroll_ManifestasiESO);
        scroll_ManifestasiESO.setBounds(134, 330, 720, 43);

        jLabelMutu.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelMutu.setText("Masalah Mutu Obat");
        jLabelMutu.setName("jLabelMutu"); // NOI18N
        FormInput.add(jLabelMutu);
        jLabelMutu.setBounds(44, 380, 113, 23);

        scroll_MasalahMutu.setName("scroll_MasalahMutu"); // NOI18N

        MasalahMutu.setColumns(20);
        MasalahMutu.setRows(5);
        MasalahMutu.setName("MasalahMutu"); // NOI18N
        MasalahMutu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MasalahMutuKeyPressed(evt);
            }
        });
        scroll_MasalahMutu.setViewportView(MasalahMutu);

        FormInput.add(scroll_MasalahMutu);
        scroll_MasalahMutu.setBounds(149, 380, 705, 43);

        jLabelMulaESO.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelMulaESO.setText("Saat/Tanggal Mula Terjadi");
        jLabelMulaESO.setName("jLabelMulaESO"); // NOI18N
        FormInput.add(jLabelMulaESO);
        jLabelMulaESO.setBounds(44, 430, 130, 23);

        TglMulaESO.setForeground(new java.awt.Color(50, 70, 50));
        TglMulaESO.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "23-11-2026" }));
        TglMulaESO.setDisplayFormat("dd-MM-yyyy");
        TglMulaESO.setName("TglMulaESO"); // NOI18N
        TglMulaESO.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglMulaESOKeyPressed(evt);
            }
        });
        FormInput.add(TglMulaESO);
        TglMulaESO.setBounds(183, 430, 90, 23);

        jLabelKesESO.setText("Kesudahan ESO :");
        jLabelKesESO.setName("jLabelKesESO"); // NOI18N
        FormInput.add(jLabelKesESO);
        jLabelKesESO.setBounds(570, 430, 100, 23);

        KesudahanESO.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Sembuh", "Sembuh Dengan Gejala Sisa", "Belum Sembuh", "Meninggal", "Tidak Tahu" }));
        KesudahanESO.setName("KesudahanESO"); // NOI18N
        KesudahanESO.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KesudahanESOKeyPressed(evt);
            }
        });
        FormInput.add(KesudahanESO);
        KesudahanESO.setBounds(674, 430, 180, 23);

        jLabelTglKesESO.setText("Tanggal Kesudahan ESO :");
        jLabelTglKesESO.setName("jLabelTglKesESO"); // NOI18N
        FormInput.add(jLabelTglKesESO);
        jLabelTglKesESO.setBounds(296, 430, 150, 23);

        TglKesudahanESO.setForeground(new java.awt.Color(50, 70, 50));
        TglKesudahanESO.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "23-11-2026" }));
        TglKesudahanESO.setDisplayFormat("dd-MM-yyyy");
        TglKesudahanESO.setName("TglKesudahanESO"); // NOI18N
        TglKesudahanESO.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglKesudahanESOKeyPressed(evt);
            }
        });
        FormInput.add(TglKesudahanESO);
        TglKesudahanESO.setBounds(450, 430, 90, 23);

        jLabelRiwayat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelRiwayat.setText("Riwayat ESO Yang Pernah Dialami");
        jLabelRiwayat.setName("jLabelRiwayat"); // NOI18N
        FormInput.add(jLabelRiwayat);
        jLabelRiwayat.setBounds(44, 460, 180, 23);

        scroll_RiwayatESO.setName("scroll_RiwayatESO"); // NOI18N

        RiwayatESO.setColumns(20);
        RiwayatESO.setRows(5);
        RiwayatESO.setName("RiwayatESO"); // NOI18N
        RiwayatESO.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RiwayatESOKeyPressed(evt);
            }
        });
        scroll_RiwayatESO.setViewportView(RiwayatESO);

        FormInput.add(scroll_RiwayatESO);
        scroll_RiwayatESO.setBounds(221, 460, 633, 43);

        jSeparatorMeso3.setBackground(new java.awt.Color(239, 244, 234));
        jSeparatorMeso3.setForeground(new java.awt.Color(239, 244, 234));
        jSeparatorMeso3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparatorMeso3.setName("jSeparatorMeso3"); // NOI18N
        FormInput.add(jSeparatorMeso3);
        jSeparatorMeso3.setBounds(0, 510, 880, 1);

        jLabelH3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelH3.setText("III. OBAT");
        jLabelH3.setName("jLabelH3"); // NOI18N
        FormInput.add(jLabelH3);
        jLabelH3.setBounds(10, 510, 180, 23);

        jLabelNamaObat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelNamaObat.setText("Nama Obat");
        jLabelNamaObat.setName("jLabelNamaObat"); // NOI18N
        FormInput.add(jLabelNamaObat);
        jLabelNamaObat.setBounds(44, 530, 64, 23);

        NamaObat.setName("NamaObat"); // NOI18N
        NamaObat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NamaObatKeyPressed(evt);
            }
        });
        FormInput.add(NamaObat);
        NamaObat.setBounds(110, 530, 250, 23);

        jLabelBentuk.setText("Bentuk Sediaan :");
        jLabelBentuk.setName("jLabelBentuk"); // NOI18N
        FormInput.add(jLabelBentuk);
        jLabelBentuk.setBounds(368, 530, 98, 23);

        BentukSediaan.setName("BentukSediaan"); // NOI18N
        BentukSediaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BentukSediaanKeyPressed(evt);
            }
        });
        FormInput.add(BentukSediaan);
        BentukSediaan.setBounds(470, 530, 180, 23);

        jLabelBatch.setText("No. Batch :");
        jLabelBatch.setName("jLabelBatch"); // NOI18N
        FormInput.add(jLabelBatch);
        jLabelBatch.setBounds(661, 530, 64, 23);

        NoBatch.setName("NoBatch"); // NOI18N
        NoBatch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoBatchKeyPressed(evt);
            }
        });
        FormInput.add(NoBatch);
        NoBatch.setBounds(729, 530, 125, 23);

        jLabelObatJKN.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelObatJKN.setText("Obat JKN");
        jLabelObatJKN.setName("jLabelObatJKN"); // NOI18N
        FormInput.add(jLabelObatJKN);
        jLabelObatJKN.setBounds(44, 560, 60, 23);

        ObatJKN.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        ObatJKN.setName("ObatJKN"); // NOI18N
        ObatJKN.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ObatJKNKeyPressed(evt);
            }
        });
        FormInput.add(ObatJKN);
        ObatJKN.setBounds(101, 560, 70, 23);

        ObatDicurigai.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        ObatDicurigai.setName("ObatDicurigai"); // NOI18N
        ObatDicurigai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ObatDicurigaiKeyPressed(evt);
            }
        });
        FormInput.add(ObatDicurigai);
        ObatDicurigai.setBounds(276, 560, 70, 23);

        jLabelObatDicurigai.setText("Obat Dicurigai :");
        jLabelObatDicurigai.setName("jLabelObatDicurigai"); // NOI18N
        FormInput.add(jLabelObatDicurigai);
        jLabelObatDicurigai.setBounds(172, 560, 100, 23);

        jLabelCara.setText("Cara Pemberian :");
        jLabelCara.setName("jLabelCara"); // NOI18N
        FormInput.add(jLabelCara);
        jLabelCara.setBounds(354, 560, 100, 23);

        Cara.setName("Cara"); // NOI18N
        Cara.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CaraKeyPressed(evt);
            }
        });
        FormInput.add(Cara);
        Cara.setBounds(458, 560, 176, 23);

        jLabelDosis.setText("Dosis/Waktu :");
        jLabelDosis.setName("jLabelDosis"); // NOI18N
        FormInput.add(jLabelDosis);
        jLabelDosis.setBounds(649, 560, 76, 23);

        DosisWaktu.setName("DosisWaktu"); // NOI18N
        DosisWaktu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DosisWaktuKeyPressed(evt);
            }
        });
        FormInput.add(DosisWaktu);
        DosisWaktu.setBounds(729, 560, 125, 23);

        jLabelMulaObat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelMulaObat.setText("Tanggal Mula");
        jLabelMulaObat.setName("jLabelMulaObat"); // NOI18N
        FormInput.add(jLabelMulaObat);
        jLabelMulaObat.setBounds(44, 590, 70, 23);

        TglMulaObat.setForeground(new java.awt.Color(50, 70, 50));
        TglMulaObat.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "23-11-2026" }));
        TglMulaObat.setDisplayFormat("dd-MM-yyyy");
        TglMulaObat.setName("TglMulaObat"); // NOI18N
        TglMulaObat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglMulaObatKeyPressed(evt);
            }
        });
        FormInput.add(TglMulaObat);
        TglMulaObat.setBounds(120, 590, 90, 23);

        jLabelAkhirObat.setText("Tanggal Akhir :");
        jLabelAkhirObat.setName("jLabelAkhirObat"); // NOI18N
        FormInput.add(jLabelAkhirObat);
        jLabelAkhirObat.setBounds(211, 590, 95, 23);

        TglAkhirObat.setForeground(new java.awt.Color(50, 70, 50));
        TglAkhirObat.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "23-11-2026" }));
        TglAkhirObat.setDisplayFormat("dd-MM-yyyy");
        TglAkhirObat.setName("TglAkhirObat"); // NOI18N
        TglAkhirObat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglAkhirObatKeyPressed(evt);
            }
        });
        FormInput.add(TglAkhirObat);
        TglAkhirObat.setBounds(310, 590, 90, 23);

        jLabelIndikasi.setText("Indikasi :");
        jLabelIndikasi.setName("jLabelIndikasi"); // NOI18N
        FormInput.add(jLabelIndikasi);
        jLabelIndikasi.setBounds(410, 590, 58, 23);

        Indikasi.setName("Indikasi"); // NOI18N
        Indikasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                IndikasiKeyPressed(evt);
            }
        });
        FormInput.add(Indikasi);
        Indikasi.setBounds(472, 590, 230, 23);

        BtnTambahObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/add-file-16x16.png"))); // NOI18N
        BtnTambahObat.setName("BtnTambahObat"); // NOI18N
        BtnTambahObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTambahObatActionPerformed(evt);
            }
        });
        BtnTambahObat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnTambahObatKeyPressed(evt);
            }
        });
        FormInput.add(BtnTambahObat);
        BtnTambahObat.setBounds(722, 590, 28, 23);

        BtnHapusObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); // NOI18N
        BtnHapusObat.setName("BtnHapusObat"); // NOI18N
        BtnHapusObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnHapusObatActionPerformed(evt);
            }
        });
        BtnHapusObat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnHapusObatKeyPressed(evt);
            }
        });
        FormInput.add(BtnHapusObat);
        BtnHapusObat.setBounds(755, 590, 28, 23);

        scrollObatMeso.setName("scrollObatMeso"); // NOI18N
        scrollObatMeso.setOpaque(true);

        tbObatMeso.setToolTipText("Silahkan klik untuk menampilkan data obat ke isian di atas");
        tbObatMeso.setName("tbObatMeso"); // NOI18N
        tbObatMeso.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbObatMesoMouseClicked(evt);
            }
        });
        tbObatMeso.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbObatMesoKeyPressed(evt);
            }
        });
        scrollObatMeso.setViewportView(tbObatMeso);

        FormInput.add(scrollObatMeso);
        scrollObatMeso.setBounds(44, 620, 810, 113);

        jLabelKet.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelKet.setText("Keterangan Tambahan");
        jLabelKet.setName("jLabelKet"); // NOI18N
        FormInput.add(jLabelKet);
        jLabelKet.setBounds(44, 740, 126, 23);

        scroll_KeteranganTambahan.setName("scroll_KeteranganTambahan"); // NOI18N

        KeteranganTambahan.setColumns(20);
        KeteranganTambahan.setRows(5);
        KeteranganTambahan.setName("KeteranganTambahan"); // NOI18N
        KeteranganTambahan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganTambahanKeyPressed(evt);
            }
        });
        scroll_KeteranganTambahan.setViewportView(KeteranganTambahan);

        FormInput.add(scroll_KeteranganTambahan);
        scroll_KeteranganTambahan.setBounds(166, 740, 688, 43);

        jLabelLab.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelLab.setText("Data Laboratorium");
        jLabelLab.setName("jLabelLab"); // NOI18N
        FormInput.add(jLabelLab);
        jLabelLab.setBounds(44, 790, 113, 23);

        scroll_DataLab.setName("scroll_DataLab"); // NOI18N

        DataLab.setColumns(20);
        DataLab.setRows(5);
        DataLab.setName("DataLab"); // NOI18N
        DataLab.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DataLabKeyPressed(evt);
            }
        });
        scroll_DataLab.setViewportView(DataLab);

        FormInput.add(scroll_DataLab);
        scroll_DataLab.setBounds(147, 790, 707, 43);

        jLabelTglLab.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelTglLab.setText("Tanggal Pemeriksaan Laboratorium");
        jLabelTglLab.setName("jLabelTglLab"); // NOI18N
        FormInput.add(jLabelTglLab);
        jLabelTglLab.setBounds(44, 840, 190, 23);

        TglLab.setForeground(new java.awt.Color(50, 70, 50));
        TglLab.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "23-11-2026" }));
        TglLab.setDisplayFormat("dd-MM-yyyy");
        TglLab.setName("TglLab"); // NOI18N
        TglLab.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglLabKeyPressed(evt);
            }
        });
        FormInput.add(TglLab);
        TglLab.setBounds(227, 840, 90, 23);

        jSeparatorMeso4.setBackground(new java.awt.Color(239, 244, 234));
        jSeparatorMeso4.setForeground(new java.awt.Color(239, 244, 234));
        jSeparatorMeso4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparatorMeso4.setName("jSeparatorMeso4"); // NOI18N
        FormInput.add(jSeparatorMeso4);
        jSeparatorMeso4.setBounds(0, 870, 880, 1);

        jLabelH4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelH4.setText("IV. ALGORITMA NARANJO (Tidak Wajib Diisi)");
        jLabelH4.setName("jLabelH4"); // NOI18N
        FormInput.add(jLabelH4);
        jLabelH4.setBounds(10, 870, 420, 23);

        jLabelQ1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelQ1.setText("1. Apakah ada laporan efek samping obat yang serupa ?");
        jLabelQ1.setName("jLabelQ1"); // NOI18N
        FormInput.add(jLabelQ1);
        jLabelQ1.setBounds(44, 890, 650, 23);

        Naranjo1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Ya", "Tidak", "Tidak Diketahui" }));
        Naranjo1.setToolTipText("Ya = +1, Tidak = +0, Tidak Diketahui = 0");
        Naranjo1.setName("Naranjo1"); // NOI18N
        Naranjo1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Naranjo1ActionPerformed(evt);
            }
        });
        Naranjo1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Naranjo1KeyPressed(evt);
            }
        });
        FormInput.add(Naranjo1);
        Naranjo1.setBounds(698, 890, 112, 23);

        SkorN1.setEditable(false);
        SkorN1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        SkorN1.setName("SkorN1"); // NOI18N
        FormInput.add(SkorN1);
        SkorN1.setBounds(814, 890, 40, 23);

        jLabelQ2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelQ2.setText("2. Apakah efek samping obat terjadi setelah pemberian obat yang dicurigai ?");
        jLabelQ2.setName("jLabelQ2"); // NOI18N
        FormInput.add(jLabelQ2);
        jLabelQ2.setBounds(44, 920, 650, 23);

        Naranjo2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Ya", "Tidak", "Tidak Diketahui" }));
        Naranjo2.setToolTipText("Ya = +2, Tidak = -1, Tidak Diketahui = 0");
        Naranjo2.setName("Naranjo2"); // NOI18N
        Naranjo2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Naranjo2ActionPerformed(evt);
            }
        });
        Naranjo2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Naranjo2KeyPressed(evt);
            }
        });
        FormInput.add(Naranjo2);
        Naranjo2.setBounds(698, 920, 112, 23);

        SkorN2.setEditable(false);
        SkorN2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        SkorN2.setName("SkorN2"); // NOI18N
        FormInput.add(SkorN2);
        SkorN2.setBounds(814, 920, 40, 23);

        jLabelQ3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelQ3.setText("3. Apakah efek samping obat membaik setelah obat dihentikan atau obat antagonis khusus diberikan ?");
        jLabelQ3.setName("jLabelQ3"); // NOI18N
        FormInput.add(jLabelQ3);
        jLabelQ3.setBounds(44, 950, 650, 23);

        Naranjo3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Ya", "Tidak", "Tidak Diketahui" }));
        Naranjo3.setToolTipText("Ya = +1, Tidak = +0, Tidak Diketahui = 0");
        Naranjo3.setName("Naranjo3"); // NOI18N
        Naranjo3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Naranjo3ActionPerformed(evt);
            }
        });
        Naranjo3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Naranjo3KeyPressed(evt);
            }
        });
        FormInput.add(Naranjo3);
        Naranjo3.setBounds(698, 950, 112, 23);

        SkorN3.setEditable(false);
        SkorN3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        SkorN3.setName("SkorN3"); // NOI18N
        FormInput.add(SkorN3);
        SkorN3.setBounds(814, 950, 40, 23);

        jLabelQ4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelQ4.setText("4. Apakah Efek Samping Obat terjadi berulang setelah obat diberikan kembali ?");
        jLabelQ4.setName("jLabelQ4"); // NOI18N
        FormInput.add(jLabelQ4);
        jLabelQ4.setBounds(44, 980, 650, 23);

        Naranjo4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Ya", "Tidak", "Tidak Diketahui" }));
        Naranjo4.setToolTipText("Ya = +2, Tidak = -1, Tidak Diketahui = 0");
        Naranjo4.setName("Naranjo4"); // NOI18N
        Naranjo4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Naranjo4ActionPerformed(evt);
            }
        });
        Naranjo4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Naranjo4KeyPressed(evt);
            }
        });
        FormInput.add(Naranjo4);
        Naranjo4.setBounds(698, 980, 112, 23);

        SkorN4.setEditable(false);
        SkorN4.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        SkorN4.setName("SkorN4"); // NOI18N
        FormInput.add(SkorN4);
        SkorN4.setBounds(814, 980, 40, 23);

        jLabelQ5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelQ5.setText("5. Apakah ada alternative penyebab yang dapat menjelaskan kemungkinan terjadinya efek samping obat?");
        jLabelQ5.setName("jLabelQ5"); // NOI18N
        FormInput.add(jLabelQ5);
        jLabelQ5.setBounds(44, 1010, 650, 23);

        Naranjo5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Ya", "Tidak", "Tidak Diketahui" }));
        Naranjo5.setToolTipText("Ya = -1, Tidak = +2, Tidak Diketahui = 0");
        Naranjo5.setName("Naranjo5"); // NOI18N
        Naranjo5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Naranjo5ActionPerformed(evt);
            }
        });
        Naranjo5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Naranjo5KeyPressed(evt);
            }
        });
        FormInput.add(Naranjo5);
        Naranjo5.setBounds(698, 1010, 112, 23);

        SkorN5.setEditable(false);
        SkorN5.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        SkorN5.setName("SkorN5"); // NOI18N
        FormInput.add(SkorN5);
        SkorN5.setBounds(814, 1010, 40, 23);

        jLabelQ6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelQ6.setText("6. Apakah efek samping obat muncul kembali ketika plasebo diberikan ?");
        jLabelQ6.setName("jLabelQ6"); // NOI18N
        FormInput.add(jLabelQ6);
        jLabelQ6.setBounds(44, 1040, 650, 23);

        Naranjo6.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Ya", "Tidak", "Tidak Diketahui" }));
        Naranjo6.setToolTipText("Ya = -1, Tidak = +1, Tidak Diketahui = 0");
        Naranjo6.setName("Naranjo6"); // NOI18N
        Naranjo6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Naranjo6ActionPerformed(evt);
            }
        });
        Naranjo6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Naranjo6KeyPressed(evt);
            }
        });
        FormInput.add(Naranjo6);
        Naranjo6.setBounds(698, 1040, 112, 23);

        SkorN6.setEditable(false);
        SkorN6.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        SkorN6.setName("SkorN6"); // NOI18N
        FormInput.add(SkorN6);
        SkorN6.setBounds(814, 1040, 40, 23);

        jLabelQ7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelQ7.setText("7. Apakah obat yang dicurigai terdeteksi di dalam darah atau cairan tubuh lainnya dengan konsentrasi yang toksik ?");
        jLabelQ7.setName("jLabelQ7"); // NOI18N
        FormInput.add(jLabelQ7);
        jLabelQ7.setBounds(44, 1070, 650, 23);

        Naranjo7.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Ya", "Tidak", "Tidak Diketahui" }));
        Naranjo7.setToolTipText("Ya = +1, Tidak = +0, Tidak Diketahui = 0");
        Naranjo7.setName("Naranjo7"); // NOI18N
        Naranjo7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Naranjo7ActionPerformed(evt);
            }
        });
        Naranjo7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Naranjo7KeyPressed(evt);
            }
        });
        FormInput.add(Naranjo7);
        Naranjo7.setBounds(698, 1070, 112, 23);

        SkorN7.setEditable(false);
        SkorN7.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        SkorN7.setName("SkorN7"); // NOI18N
        FormInput.add(SkorN7);
        SkorN7.setBounds(814, 1070, 40, 23);

        jLabelQ8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelQ8.setText("8. Apakah efek samping obat bertambah parah ketika dosis obat ditingkatkan atau bertambah ringan ketika dosisnya diturunkan ?");
        jLabelQ8.setName("jLabelQ8"); // NOI18N
        FormInput.add(jLabelQ8);
        jLabelQ8.setBounds(44, 1100, 650, 23);

        Naranjo8.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Ya", "Tidak", "Tidak Diketahui" }));
        Naranjo8.setToolTipText("Ya = +1, Tidak = +0, Tidak Diketahui = 0");
        Naranjo8.setName("Naranjo8"); // NOI18N
        Naranjo8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Naranjo8ActionPerformed(evt);
            }
        });
        Naranjo8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Naranjo8KeyPressed(evt);
            }
        });
        FormInput.add(Naranjo8);
        Naranjo8.setBounds(698, 1100, 112, 23);

        SkorN8.setEditable(false);
        SkorN8.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        SkorN8.setName("SkorN8"); // NOI18N
        FormInput.add(SkorN8);
        SkorN8.setBounds(814, 1100, 40, 23);

        jLabelQ9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelQ9.setText("9. Apakah pasien pernah mengalami efek samping obat yang sama atau dengan obat yang mirip sebelumnya ?");
        jLabelQ9.setName("jLabelQ9"); // NOI18N
        FormInput.add(jLabelQ9);
        jLabelQ9.setBounds(44, 1130, 650, 23);

        Naranjo9.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Ya", "Tidak", "Tidak Diketahui" }));
        Naranjo9.setToolTipText("Ya = +1, Tidak = +0, Tidak Diketahui = 0");
        Naranjo9.setName("Naranjo9"); // NOI18N
        Naranjo9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Naranjo9ActionPerformed(evt);
            }
        });
        Naranjo9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Naranjo9KeyPressed(evt);
            }
        });
        FormInput.add(Naranjo9);
        Naranjo9.setBounds(698, 1130, 112, 23);

        SkorN9.setEditable(false);
        SkorN9.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        SkorN9.setName("SkorN9"); // NOI18N
        FormInput.add(SkorN9);
        SkorN9.setBounds(814, 1130, 40, 23);

        jLabelQ10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelQ10.setText("10. Apakah efek samping obat dapat dikonfirmasi dengan bukti yang obyektif ?");
        jLabelQ10.setName("jLabelQ10"); // NOI18N
        FormInput.add(jLabelQ10);
        jLabelQ10.setBounds(44, 1160, 650, 23);

        Naranjo10.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Ya", "Tidak", "Tidak Diketahui" }));
        Naranjo10.setToolTipText("Ya = +1, Tidak = +0, Tidak Diketahui = 0");
        Naranjo10.setName("Naranjo10"); // NOI18N
        Naranjo10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Naranjo10ActionPerformed(evt);
            }
        });
        Naranjo10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Naranjo10KeyPressed(evt);
            }
        });
        FormInput.add(Naranjo10);
        Naranjo10.setBounds(698, 1160, 112, 23);

        SkorN10.setEditable(false);
        SkorN10.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        SkorN10.setName("SkorN10"); // NOI18N
        FormInput.add(SkorN10);
        SkorN10.setBounds(814, 1160, 40, 23);

        jLabelSkor.setText("Total Skor :");
        jLabelSkor.setName("jLabelSkor"); // NOI18N
        FormInput.add(jLabelSkor);
        jLabelSkor.setBounds(720, 1190, 70, 23);

        SkorNaranjo.setEditable(false);
        SkorNaranjo.setName("SkorNaranjo"); // NOI18N
        FormInput.add(SkorNaranjo);
        SkorNaranjo.setBounds(794, 1190, 60, 23);

        jLabelKategori.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelKategori.setText("Kategori");
        jLabelKategori.setName("jLabelKategori"); // NOI18N
        FormInput.add(jLabelKategori);
        jLabelKategori.setBounds(44, 1190, 70, 23);

        KategoriNaranjo.setEditable(false);
        KategoriNaranjo.setName("KategoriNaranjo"); // NOI18N
        FormInput.add(KategoriNaranjo);
        KategoriNaranjo.setBounds(96, 1190, 180, 23);

        jLabel33.setText(":");
        jLabel33.setName("jLabel33"); // NOI18N
        FormInput.add(jLabel33);
        jLabel33.setBounds(0, 120, 102, 23);

        jLabel34.setText(":");
        jLabel34.setName("jLabel34"); // NOI18N
        FormInput.add(jLabel34);
        jLabel34.setBounds(0, 170, 127, 23);

        jLabel35.setText(":");
        jLabel35.setName("jLabel35"); // NOI18N
        FormInput.add(jLabel35);
        jLabel35.setBounds(0, 220, 238, 23);

        jLabel36.setText(":");
        jLabel36.setName("jLabel36"); // NOI18N
        FormInput.add(jLabel36);
        jLabel36.setBounds(0, 280, 183, 23);

        jLabel37.setText(":");
        jLabel37.setName("jLabel37"); // NOI18N
        FormInput.add(jLabel37);
        jLabel37.setBounds(0, 330, 130, 23);

        jLabel38.setText(":");
        jLabel38.setName("jLabel38"); // NOI18N
        FormInput.add(jLabel38);
        jLabel38.setBounds(0, 380, 145, 23);

        jLabel39.setText(":");
        jLabel39.setName("jLabel39"); // NOI18N
        FormInput.add(jLabel39);
        jLabel39.setBounds(0, 430, 179, 23);

        jLabel40.setText(":");
        jLabel40.setName("jLabel40"); // NOI18N
        FormInput.add(jLabel40);
        jLabel40.setBounds(0, 460, 217, 23);

        jLabel41.setText(":");
        jLabel41.setName("jLabel41"); // NOI18N
        FormInput.add(jLabel41);
        jLabel41.setBounds(0, 560, 97, 23);

        jLabel42.setText(":");
        jLabel42.setName("jLabel42"); // NOI18N
        FormInput.add(jLabel42);
        jLabel42.setBounds(0, 560, 121, 23);

        jLabel43.setText(":");
        jLabel43.setName("jLabel43"); // NOI18N
        FormInput.add(jLabel43);
        jLabel43.setBounds(0, 590, 116, 23);

        jLabel45.setText(":");
        jLabel45.setName("jLabel45"); // NOI18N
        FormInput.add(jLabel45);
        jLabel45.setBounds(0, 740, 162, 23);

        jLabel46.setText(":");
        jLabel46.setName("jLabel46"); // NOI18N
        FormInput.add(jLabel46);
        jLabel46.setBounds(0, 790, 143, 23);

        jLabel47.setText(":");
        jLabel47.setName("jLabel47"); // NOI18N
        FormInput.add(jLabel47);
        jLabel47.setBounds(0, 840, 223, 23);

        jLabel44.setText(":");
        jLabel44.setName("jLabel44"); // NOI18N
        FormInput.add(jLabel44);
        jLabel44.setBounds(0, 530, 106, 23);

        jLabel48.setText(":");
        jLabel48.setName("jLabel48"); // NOI18N
        FormInput.add(jLabel48);
        jLabel48.setBounds(0, 1190, 92, 23);

        scrollInput.setViewportView(FormInput);

        internalFrame2.add(scrollInput, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Input Laporan", internalFrame2);

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

        PanelAccor.setBackground(new java.awt.Color(255, 255, 255));
        PanelAccor.setName("PanelAccor"); // NOI18N
        PanelAccor.setPreferredSize(new java.awt.Dimension(470, 43));
        PanelAccor.setLayout(new java.awt.BorderLayout(1, 1));

        ChkAccor.setBackground(new java.awt.Color(255, 250, 250));
        ChkAccor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kiri.png"))); // NOI18N
        ChkAccor.setSelected(true);
        ChkAccor.setFocusable(false);
        ChkAccor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkAccor.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkAccor.setName("ChkAccor"); // NOI18N
        ChkAccor.setPreferredSize(new java.awt.Dimension(15, 20));
        ChkAccor.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kiri.png"))); // NOI18N
        ChkAccor.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kanan.png"))); // NOI18N
        ChkAccor.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kanan.png"))); // NOI18N
        ChkAccor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkAccorActionPerformed(evt);
            }
        });
        PanelAccor.add(ChkAccor, java.awt.BorderLayout.WEST);

        FormMenu.setBackground(new java.awt.Color(255, 255, 255));
        FormMenu.setBorder(null);
        FormMenu.setName("FormMenu"); // NOI18N
        FormMenu.setPreferredSize(new java.awt.Dimension(115, 43));
        FormMenu.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        jLabelPasienData.setText("Pasien :");
        jLabelPasienData.setName("jLabelPasienData"); // NOI18N
        jLabelPasienData.setPreferredSize(new java.awt.Dimension(55, 23));
        FormMenu.add(jLabelPasienData);

        TNoRMData.setEditable(false);
        TNoRMData.setHighlighter(null);
        TNoRMData.setName("TNoRMData"); // NOI18N
        TNoRMData.setPreferredSize(new java.awt.Dimension(100, 23));
        FormMenu.add(TNoRMData);

        TPasienData.setEditable(false);
        TPasienData.setBackground(new java.awt.Color(245, 250, 240));
        TPasienData.setHighlighter(null);
        TPasienData.setName("TPasienData"); // NOI18N
        TPasienData.setPreferredSize(new java.awt.Dimension(285, 23));
        FormMenu.add(TPasienData);

        PanelAccor.add(FormMenu, java.awt.BorderLayout.NORTH);

        FormObatData.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 254)));
        FormObatData.setName("FormObatData"); // NOI18N
        FormObatData.setLayout(new java.awt.BorderLayout(1, 1));

        scrollObatData.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 254)));
        scrollObatData.setName("scrollObatData"); // NOI18N
        scrollObatData.setOpaque(true);

        tbObatData.setName("tbObatData"); // NOI18N
        scrollObatData.setViewportView(tbObatData);

        FormObatData.add(scrollObatData, java.awt.BorderLayout.CENTER);

        PanelAccor.add(FormObatData, java.awt.BorderLayout.CENTER);

        internalFrame3.add(PanelAccor, java.awt.BorderLayout.EAST);

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel19.setText("Tgl.Laporan :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "23-11-2026" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(DTPCari1);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("s.d.");
        jLabel21.setName("jLabel21"); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(23, 23));
        panelGlass9.add(jLabel21);

        DTPCari2.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "23-11-2026" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
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

        TabRawat.addTab("Data Laporan", internalFrame3);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        boolean obatDicurigai=false;
        for(i=0;i<tabModeObat.getRowCount();i++){
            if(tabModeObat.getValueAt(i,4).toString().equals("Ya")){
                obatDicurigai=true;
            }
        }
        if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"pasien");
        }else if(NmPetugas.getText().trim().equals("")){
            Valid.textKosong(NmPetugas,"Pelapor");
        }else if(NoLaporan.getText().trim().equals("")){
            Valid.textKosong(NoLaporan,"No.Laporan");
        }else if(ManifestasiESO.getText().trim().equals("")){
            Valid.textKosong(ManifestasiESO,"Manifestasi ESO");
        }else if(tabModeObat.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Silahkan tambahkan minimal satu obat..!!");
            NamaObat.requestFocus();
        }else if(!obatDicurigai){
            JOptionPane.showMessageDialog(null,"Silahkan tandai minimal satu obat yang dicurigai..!!");
            NamaObat.requestFocus();
        }else{
            if(akses.getkode().equals("Admin Utama")){
                simpan();
            }else{
                if(TanggalRegistrasi.getText().equals("")){
                    TanggalRegistrasi.setText(Sequel.cariIsi("select concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) from reg_periksa where reg_periksa.no_rawat=?",TNoRw.getText()));
                }
                if(Sequel.cekTanggalRegistrasi(TanggalRegistrasi.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19))==true){
                    simpan();
                }
            }
        }
    }//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,Naranjo10,BtnBatal);
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
                if(akses.getkode().equals(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString())){
                    if(Sequel.cekTanggal48jam(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString(),Sequel.ambiltanggalsekarang())==true){
                        hapus();
                    }
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
        boolean obatDicurigai=false;
        for(i=0;i<tabModeObat.getRowCount();i++){
            if(tabModeObat.getValueAt(i,4).toString().equals("Ya")){
                obatDicurigai=true;
            }
        }
        if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"pasien");
        }else if(NmPetugas.getText().trim().equals("")){
            Valid.textKosong(NmPetugas,"Pelapor");
        }else if(NoLaporan.getText().trim().equals("")){
            Valid.textKosong(NoLaporan,"No.Laporan");
        }else if(ManifestasiESO.getText().trim().equals("")){
            Valid.textKosong(ManifestasiESO,"Manifestasi ESO");
        }else if(tabModeObat.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Silahkan tambahkan minimal satu obat..!!");
            NamaObat.requestFocus();
        }else if(!obatDicurigai){
            JOptionPane.showMessageDialog(null,"Silahkan tandai minimal satu obat yang dicurigai..!!");
            NamaObat.requestFocus();
        }else{
            if(tbObat.getSelectedRow()>-1){
                if(akses.getkode().equals("Admin Utama")){
                    ganti();
                }else{
                    if(akses.getkode().equals(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString())){
                        if(Sequel.cekTanggal48jam(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString(),Sequel.ambiltanggalsekarang())==true){
                            if(TanggalRegistrasi.getText().equals("")){
                                TanggalRegistrasi.setText(Sequel.cariIsi("select concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) from reg_periksa where reg_periksa.no_rawat=?",TNoRw.getText()));
                            }
                            if(Sequel.cekTanggalRegistrasi(TanggalRegistrasi.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19))==true){
                                ganti();
                            }
                        }
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
            try {
                htmlContent = new StringBuilder();
                htmlContent.append(
                    "<tr class='isi'>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='105px'><b>No.Laporan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='105px'><b>No.Rawat</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='70px'><b>No.R.M.</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='150px'><b>Nama Pasien</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='115px'><b>Tgl.Laporan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='80px'><b>NIP</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='150px'><b>Pelapor</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='65px'><b>Tgl.Lahir</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='30px'><b>J.K.</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='100px'><b>Suku</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='120px'><b>Pekerjaan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='45px'><b>BB (kg)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='250px'><b>Anamnesa</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='200px'><b>Penyakit Utama</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='140px'><b>Kesudahan Penyakit</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='60px'><b>Gg.Ginjal</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='60px'><b>Gg.Hati</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='50px'><b>Alergi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='110px'><b>Kondisi Medis Lain</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='90px'><b>Faktor Industri</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='60px'><b>Lain-lain</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='150px'><b>Ket.Lain-lain</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='300px'><b>Manifestasi ESO</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='200px'><b>Masalah Mutu Obat</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='75px'><b>Tgl.Mula ESO</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='140px'><b>Kesudahan ESO</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='100px'><b>Tgl.Kesudahan ESO</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='200px'><b>Riwayat ESO</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='200px'><b>Keterangan Tambahan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='200px'><b>Data Laboratorium</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='65px'><b>Tgl.Lab</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='85px'><b>Naranjo 1</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='45px'><b>Skor 1</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='85px'><b>Naranjo 2</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='45px'><b>Skor 2</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='85px'><b>Naranjo 3</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='45px'><b>Skor 3</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='85px'><b>Naranjo 4</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='45px'><b>Skor 4</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='85px'><b>Naranjo 5</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='45px'><b>Skor 5</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='85px'><b>Naranjo 6</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='45px'><b>Skor 6</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='85px'><b>Naranjo 7</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='45px'><b>Skor 7</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='85px'><b>Naranjo 8</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='45px'><b>Skor 8</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='85px'><b>Naranjo 9</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='45px'><b>Skor 9</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='85px'><b>Naranjo 10</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='45px'><b>Skor 10</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='70px'><b>Skor Naranjo</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='110px'><b>Kategori Naranjo</b></td>"+
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
                        "</tr>");
                }
                LoadHTML.setText(
                    "<html>"+
                      "<table width='5371px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
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

                File f = new File("DataLaporanMESO.html");
                BufferedWriter bw = new BufferedWriter(new FileWriter(f));
                bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                            "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                            "<table width='5371px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                "<tr class='isi2'>"+
                                    "<td valign='top' align='center'>"+
                                        "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                        akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                        akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                        "<font size='2' face='Tahoma'>DATA LAPORAN MONITORING EFEK SAMPING OBAT (MESO)<br><br></font>"+
                                    "</td>"+
                               "</tr>"+
                            "</table>")
                );
                bw.close();
                Desktop.getDesktop().browse(f.toURI());
            } catch (Exception e) {
                System.out.println("Notif : "+e);
            }
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnPrintActionPerformed

    private void MnFormulirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnFormulirActionPerformed
        if(tbObat.getSelectedRow()>-1){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>(); 
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptFormulirLaporanMESO.jasper","report","::[ Formulir Pelaporan Efek Samping Obat (ESO) ]::",
                "select laporan_meso.no_laporan,laporan_meso.tanggal,pasien.nm_pasien,date_format(pasien.tgl_lahir,'%d-%m-%Y') as tgl_lahir,suku_bangsa.nama_suku_bangsa as suku,"+
                "laporan_meso.berat_badan,pasien.pekerjaan,pasien.jk,laporan_meso.anamnesa,laporan_meso.penyakit_utama,laporan_meso.kesudahan_penyakit,laporan_meso.gangguan_ginjal,"+
                "laporan_meso.gangguan_hati,laporan_meso.alergi,laporan_meso.kondisi_medis_lain,laporan_meso.faktor_industri,laporan_meso.lain_lain,laporan_meso.ket_lain_lain,"+
                "laporan_meso.manifestasi_eso,laporan_meso.masalah_mutu,laporan_meso.tgl_mula_eso,laporan_meso.kesudahan_eso,laporan_meso.tgl_kesudahan_eso,laporan_meso.riwayat_eso,"+
                "laporan_meso.keterangan_tambahan,laporan_meso.data_laboratorium,laporan_meso.tgl_pemeriksaan_lab,laporan_meso.naranjo_1,laporan_meso.skor_naranjo_1,laporan_meso.naranjo_2,"+
                "laporan_meso.skor_naranjo_2,laporan_meso.naranjo_3,laporan_meso.skor_naranjo_3,laporan_meso.naranjo_4,laporan_meso.skor_naranjo_4,laporan_meso.naranjo_5,"+
                "laporan_meso.skor_naranjo_5,laporan_meso.naranjo_6,laporan_meso.skor_naranjo_6,laporan_meso.naranjo_7,laporan_meso.skor_naranjo_7,laporan_meso.naranjo_8,"+
                "laporan_meso.skor_naranjo_8,laporan_meso.naranjo_9,laporan_meso.skor_naranjo_9,laporan_meso.naranjo_10,laporan_meso.skor_naranjo_10,laporan_meso.skor_naranjo,"+
                "laporan_meso.kategori_naranjo,laporan_meso_obat.nama_obat,laporan_meso_obat.bentuk_sediaan,laporan_meso_obat.obat_jkn,laporan_meso_obat.no_batch,"+
                "laporan_meso_obat.obat_dicurigai,laporan_meso_obat.cara,laporan_meso_obat.dosis_waktu,laporan_meso_obat.tgl_mulai,laporan_meso_obat.tgl_akhir,laporan_meso_obat.indikasi,"+
                "petugas.nama,jabatan.nm_jbtn from laporan_meso inner join reg_periksa on laporan_meso.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join suku_bangsa on pasien.suku_bangsa=suku_bangsa.id "+
                "inner join petugas on laporan_meso.nip=petugas.nip inner join jabatan on petugas.kd_jbtn=jabatan.kd_jbtn "+
                "inner join laporan_meso_obat on laporan_meso.no_laporan=laporan_meso_obat.no_laporan "+
                "where laporan_meso.no_laporan='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"' order by laporan_meso_obat.nama_obat",param
            );
            this.setCursor(Cursor.getDefaultCursor());
        }else{
            JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data terlebih dahulu..!!!!");
        }
    }//GEN-LAST:event_MnFormulirActionPerformed

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
                ChkAccor.setSelected(true);
                isMenu();
                tampilObatPanel();
            } catch (java.lang.NullPointerException e) {
            }
            if((evt.getClickCount()==2)&&(tbObat.getSelectedColumn()==0)){
                getData();
                TabRawat.setSelectedIndex(0);
            }
        }
}//GEN-LAST:event_tbObatMouseClicked

    private void tbObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbObatKeyPressed
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    ChkAccor.setSelected(true);
                    isMenu();
                    tampilObatPanel();
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

    private void BtnPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPetugasKeyPressed
        Valid.pindah(evt,Tanggal,NoLaporan);
    }//GEN-LAST:event_BtnPetugasKeyPressed

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

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isRawat();
        }else{
            Valid.pindah(evt,TCari,BeratBadan);
        }
    }//GEN-LAST:event_TNoRwKeyPressed

    private void NoLaporanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoLaporanKeyPressed
        Valid.pindah(evt,BtnPetugas,Anamnesa);
    }//GEN-LAST:event_NoLaporanKeyPressed

    private void BeratBadanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BeratBadanKeyPressed
        Valid.pindah(evt,TNoRw,Tanggal);
    }//GEN-LAST:event_BeratBadanKeyPressed

    private void AnamnesaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AnamnesaKeyPressed
        Valid.pindah2(evt,NoLaporan,PenyakitUtama);
    }//GEN-LAST:event_AnamnesaKeyPressed

    private void PenyakitUtamaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenyakitUtamaKeyPressed
        Valid.pindah2(evt,Anamnesa,GangguanGinjal);
    }//GEN-LAST:event_PenyakitUtamaKeyPressed

    private void KesudahanPenyakitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KesudahanPenyakitKeyPressed
        Valid.pindah(evt,KetLainLain,ManifestasiESO);
    }//GEN-LAST:event_KesudahanPenyakitKeyPressed

    private void KetLainLainKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetLainLainKeyPressed
        Valid.pindah(evt,LainLain,KesudahanPenyakit);
    }//GEN-LAST:event_KetLainLainKeyPressed

    private void ManifestasiESOKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ManifestasiESOKeyPressed
        Valid.pindah2(evt,KesudahanPenyakit,MasalahMutu);
    }//GEN-LAST:event_ManifestasiESOKeyPressed

    private void MasalahMutuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MasalahMutuKeyPressed
        Valid.pindah2(evt,ManifestasiESO,TglMulaESO);
    }//GEN-LAST:event_MasalahMutuKeyPressed

    private void KesudahanESOKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KesudahanESOKeyPressed
        Valid.pindah(evt,TglKesudahanESO,RiwayatESO);
    }//GEN-LAST:event_KesudahanESOKeyPressed

    private void RiwayatESOKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RiwayatESOKeyPressed
        Valid.pindah2(evt,KesudahanESO,NamaObat);
    }//GEN-LAST:event_RiwayatESOKeyPressed

    private void NamaObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NamaObatKeyPressed
        Valid.pindah(evt,RiwayatESO,BentukSediaan);
    }//GEN-LAST:event_NamaObatKeyPressed

    private void BentukSediaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BentukSediaanKeyPressed
        Valid.pindah(evt,NamaObat,NoBatch);
    }//GEN-LAST:event_BentukSediaanKeyPressed

    private void NoBatchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoBatchKeyPressed
        Valid.pindah(evt,BentukSediaan,ObatJKN);
    }//GEN-LAST:event_NoBatchKeyPressed

    private void CaraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CaraKeyPressed
        Valid.pindah(evt,ObatDicurigai,DosisWaktu);
    }//GEN-LAST:event_CaraKeyPressed

    private void DosisWaktuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DosisWaktuKeyPressed
        Valid.pindah(evt,Cara,TglMulaObat);
    }//GEN-LAST:event_DosisWaktuKeyPressed

    private void IndikasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_IndikasiKeyPressed
        Valid.pindah(evt,TglAkhirObat,BtnTambahObat);
    }//GEN-LAST:event_IndikasiKeyPressed

    private void KeteranganTambahanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganTambahanKeyPressed
        Valid.pindah2(evt,BtnHapusObat,DataLab);
    }//GEN-LAST:event_KeteranganTambahanKeyPressed

    private void DataLabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DataLabKeyPressed
        Valid.pindah2(evt,KeteranganTambahan,TglLab);
    }//GEN-LAST:event_DataLabKeyPressed

    private void Naranjo1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Naranjo1KeyPressed
        Valid.pindah(evt,TglLab,Naranjo2);
    }//GEN-LAST:event_Naranjo1KeyPressed

    private void Naranjo2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Naranjo2KeyPressed
        Valid.pindah(evt,Naranjo1,Naranjo3);
    }//GEN-LAST:event_Naranjo2KeyPressed

    private void Naranjo3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Naranjo3KeyPressed
        Valid.pindah(evt,Naranjo2,Naranjo4);
    }//GEN-LAST:event_Naranjo3KeyPressed

    private void Naranjo4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Naranjo4KeyPressed
        Valid.pindah(evt,Naranjo3,Naranjo5);
    }//GEN-LAST:event_Naranjo4KeyPressed

    private void Naranjo5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Naranjo5KeyPressed
        Valid.pindah(evt,Naranjo4,Naranjo6);
    }//GEN-LAST:event_Naranjo5KeyPressed

    private void Naranjo6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Naranjo6KeyPressed
        Valid.pindah(evt,Naranjo5,Naranjo7);
    }//GEN-LAST:event_Naranjo6KeyPressed

    private void Naranjo7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Naranjo7KeyPressed
        Valid.pindah(evt,Naranjo6,Naranjo8);
    }//GEN-LAST:event_Naranjo7KeyPressed

    private void Naranjo8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Naranjo8KeyPressed
        Valid.pindah(evt,Naranjo7,Naranjo9);
    }//GEN-LAST:event_Naranjo8KeyPressed

    private void Naranjo9KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Naranjo9KeyPressed
        Valid.pindah(evt,Naranjo8,Naranjo10);
    }//GEN-LAST:event_Naranjo9KeyPressed

    private void Naranjo10KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Naranjo10KeyPressed
        Valid.pindah(evt,Naranjo9,BtnSimpan);
    }//GEN-LAST:event_Naranjo10KeyPressed

    private void Naranjo1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Naranjo1ActionPerformed
        hitungSkor();
    }//GEN-LAST:event_Naranjo1ActionPerformed

    private void Naranjo2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Naranjo2ActionPerformed
        hitungSkor();
    }//GEN-LAST:event_Naranjo2ActionPerformed

    private void Naranjo3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Naranjo3ActionPerformed
        hitungSkor();
    }//GEN-LAST:event_Naranjo3ActionPerformed

    private void Naranjo4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Naranjo4ActionPerformed
        hitungSkor();
    }//GEN-LAST:event_Naranjo4ActionPerformed

    private void Naranjo5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Naranjo5ActionPerformed
        hitungSkor();
    }//GEN-LAST:event_Naranjo5ActionPerformed

    private void Naranjo6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Naranjo6ActionPerformed
        hitungSkor();
    }//GEN-LAST:event_Naranjo6ActionPerformed

    private void Naranjo7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Naranjo7ActionPerformed
        hitungSkor();
    }//GEN-LAST:event_Naranjo7ActionPerformed

    private void Naranjo8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Naranjo8ActionPerformed
        hitungSkor();
    }//GEN-LAST:event_Naranjo8ActionPerformed

    private void Naranjo9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Naranjo9ActionPerformed
        hitungSkor();
    }//GEN-LAST:event_Naranjo9ActionPerformed

    private void Naranjo10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Naranjo10ActionPerformed
        hitungSkor();
    }//GEN-LAST:event_Naranjo10ActionPerformed

    private void BtnTambahObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahObatActionPerformed
        if(NamaObat.getText().trim().equals("")){
            Valid.textKosong(NamaObat,"Nama Obat");
        }else{
            tabModeObat.addRow(new Object[]{
                NamaObat.getText(),BentukSediaan.getText(),ObatJKN.getSelectedItem().toString(),NoBatch.getText(),ObatDicurigai.getSelectedItem().toString(),
                Cara.getText(),DosisWaktu.getText(),Valid.SetTgl(TglMulaObat.getSelectedItem()+""),Valid.SetTgl(TglAkhirObat.getSelectedItem()+""),Indikasi.getText()
            });
            NamaObat.setText("");
            BentukSediaan.setText("");
            ObatJKN.setSelectedIndex(0);
            NoBatch.setText("");
            ObatDicurigai.setSelectedIndex(0);
            Cara.setText("");
            DosisWaktu.setText("");
            Indikasi.setText("");
            NamaObat.requestFocus();
        }
    }//GEN-LAST:event_BtnTambahObatActionPerformed

    private void BtnHapusObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusObatActionPerformed
        if(tbObatMeso.getSelectedRow()>-1){
            tabModeObat.removeRow(tbObatMeso.getSelectedRow());
        }else{
            JOptionPane.showMessageDialog(rootPane,"Silahkan pilih obat yang mau dihapus pada tabel obat..!!");
        }
    }//GEN-LAST:event_BtnHapusObatActionPerformed

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

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMLaporanMESO dialog = new RMLaporanMESO(new javax.swing.JFrame(), true);
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setVisible(true);
        });
    }

    private void TanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKeyPressed
        Valid.pindah2(evt,BeratBadan,BtnPetugas);
    }//GEN-LAST:event_TanggalKeyPressed

    private void GangguanGinjalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GangguanGinjalKeyPressed
        Valid.pindah(evt,PenyakitUtama,GangguanHati);
    }//GEN-LAST:event_GangguanGinjalKeyPressed

    private void GangguanHatiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GangguanHatiKeyPressed
        Valid.pindah(evt,GangguanGinjal,Alergi);
    }//GEN-LAST:event_GangguanHatiKeyPressed

    private void AlergiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlergiKeyPressed
        Valid.pindah(evt,GangguanHati,KondisiMedisLain);
    }//GEN-LAST:event_AlergiKeyPressed

    private void KondisiMedisLainKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KondisiMedisLainKeyPressed
        Valid.pindah(evt,Alergi,FaktorIndustri);
    }//GEN-LAST:event_KondisiMedisLainKeyPressed

    private void FaktorIndustriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FaktorIndustriKeyPressed
        Valid.pindah(evt,KondisiMedisLain,LainLain);
    }//GEN-LAST:event_FaktorIndustriKeyPressed

    private void LainLainKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LainLainKeyPressed
        Valid.pindah(evt,FaktorIndustri,KetLainLain);
    }//GEN-LAST:event_LainLainKeyPressed

    private void TglMulaESOKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglMulaESOKeyPressed
        Valid.pindah2(evt,MasalahMutu,TglKesudahanESO);
    }//GEN-LAST:event_TglMulaESOKeyPressed

    private void TglKesudahanESOKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglKesudahanESOKeyPressed
        Valid.pindah2(evt,TglMulaESO,KesudahanESO);
    }//GEN-LAST:event_TglKesudahanESOKeyPressed

    private void ObatJKNKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ObatJKNKeyPressed
        Valid.pindah(evt,NoBatch,ObatDicurigai);
    }//GEN-LAST:event_ObatJKNKeyPressed

    private void ObatDicurigaiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ObatDicurigaiKeyPressed
        Valid.pindah(evt,ObatJKN,Cara);
    }//GEN-LAST:event_ObatDicurigaiKeyPressed

    private void TglMulaObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglMulaObatKeyPressed
        Valid.pindah2(evt,DosisWaktu,TglAkhirObat);
    }//GEN-LAST:event_TglMulaObatKeyPressed

    private void TglAkhirObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglAkhirObatKeyPressed
        Valid.pindah2(evt,TglMulaObat,Indikasi);
    }//GEN-LAST:event_TglAkhirObatKeyPressed

    private void BtnTambahObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnTambahObatKeyPressed
        Valid.pindah(evt,Indikasi,BtnHapusObat);
    }//GEN-LAST:event_BtnTambahObatKeyPressed

    private void BtnHapusObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusObatKeyPressed
        Valid.pindah(evt,BtnTambahObat,KeteranganTambahan);
    }//GEN-LAST:event_BtnHapusObatKeyPressed

    private void TglLabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglLabKeyPressed
        Valid.pindah2(evt,DataLab,Naranjo1);
    }//GEN-LAST:event_TglLabKeyPressed

    private void KdPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdPetugasKeyPressed
        Valid.pindah2(evt,Tanggal,BtnPetugas);
    }//GEN-LAST:event_KdPetugasKeyPressed

    private void tbObatMesoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbObatMesoMouseClicked
        if(tabModeObat.getRowCount()!=0){
            try {
                getDataObat();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbObatMesoMouseClicked

    private void tbObatMesoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbObatMesoKeyPressed
        if(tabModeObat.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getDataObat();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }//GEN-LAST:event_tbObatMesoKeyPressed

    private void ChkAccorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkAccorActionPerformed
        if(tbObat.getSelectedRow()!= -1){
            isMenu();
        }else{
            ChkAccor.setSelected(false);
            JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data yang mau ditampilkan...!!!!");
        }
    }//GEN-LAST:event_ChkAccorActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private widget.CekBox Alergi;
    private widget.TextArea Anamnesa;
    private widget.TextBox BentukSediaan;
    private widget.TextBox BeratBadan;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnHapusObat;
    private widget.Button BtnKeluar;
    private widget.Button BtnPetugas;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.Button BtnTambahObat;
    private widget.TextBox Cara;
    private widget.CekBox ChkAccor;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.TextArea DataLab;
    private widget.TextBox DosisWaktu;
    private widget.CekBox FaktorIndustri;
    private widget.PanelBiasa FormInput;
    private widget.PanelBiasa FormMenu;
    private widget.PanelBiasa FormObatData;
    private widget.CekBox GangguanGinjal;
    private widget.CekBox GangguanHati;
    private widget.TextBox Indikasi;
    private widget.TextBox KategoriNaranjo;
    private widget.TextBox KdPetugas;
    private widget.TextBox Kelamin;
    private widget.ComboBox KesudahanESO;
    private widget.ComboBox KesudahanPenyakit;
    private widget.TextBox KetLainLain;
    private widget.TextArea KeteranganTambahan;
    private widget.CekBox KondisiMedisLain;
    private widget.Label LCount;
    private widget.CekBox LainLain;
    private widget.editorpane LoadHTML;
    private widget.TextArea ManifestasiESO;
    private widget.TextArea MasalahMutu;
    private widget.MenuItem MnFormulir;
    private widget.TextBox NamaObat;
    private widget.ComboBox Naranjo1;
    private widget.ComboBox Naranjo10;
    private widget.ComboBox Naranjo2;
    private widget.ComboBox Naranjo3;
    private widget.ComboBox Naranjo4;
    private widget.ComboBox Naranjo5;
    private widget.ComboBox Naranjo6;
    private widget.ComboBox Naranjo7;
    private widget.ComboBox Naranjo8;
    private widget.ComboBox Naranjo9;
    private widget.TextBox NmPetugas;
    private widget.TextBox NoBatch;
    private widget.TextBox NoLaporan;
    private widget.ComboBox ObatDicurigai;
    private widget.ComboBox ObatJKN;
    private widget.PanelBiasa PanelAccor;
    private widget.TextBox Pekerjaan;
    private widget.TextArea PenyakitUtama;
    private widget.TextArea RiwayatESO;
    private widget.ScrollPane Scroll;
    private widget.TextBox SkorN1;
    private widget.TextBox SkorN10;
    private widget.TextBox SkorN2;
    private widget.TextBox SkorN3;
    private widget.TextBox SkorN4;
    private widget.TextBox SkorN5;
    private widget.TextBox SkorN6;
    private widget.TextBox SkorN7;
    private widget.TextBox SkorN8;
    private widget.TextBox SkorN9;
    private widget.TextBox SkorNaranjo;
    private widget.TextBox Suku;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRMData;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.TextBox TPasienData;
    private widget.TabPane TabRawat;
    private widget.Tanggal Tanggal;
    private widget.TextBox TanggalRegistrasi;
    private widget.Tanggal TglAkhirObat;
    private widget.Tanggal TglKesudahanESO;
    private widget.Tanggal TglLab;
    private widget.TextBox TglLahir;
    private widget.Tanggal TglMulaESO;
    private widget.Tanggal TglMulaObat;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.Label jLabel19;
    private widget.Label jLabel21;
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
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabelAkhirObat;
    private widget.Label jLabelAnamnesa;
    private widget.Label jLabelBB;
    private widget.Label jLabelBatch;
    private widget.Label jLabelBentuk;
    private widget.Label jLabelCara;
    private widget.Label jLabelDosis;
    private widget.Label jLabelH1;
    private widget.Label jLabelH2;
    private widget.Label jLabelH3;
    private widget.Label jLabelH4;
    private widget.Label jLabelIndikasi;
    private widget.Label jLabelKategori;
    private widget.Label jLabelKelamin;
    private widget.Label jLabelKesESO;
    private widget.Label jLabelKesPenyakit;
    private widget.Label jLabelKet;
    private widget.Label jLabelLab;
    private widget.Label jLabelLain;
    private widget.Label jLabelManifestasi;
    private widget.Label jLabelMulaESO;
    private widget.Label jLabelMulaObat;
    private widget.Label jLabelMutu;
    private widget.Label jLabelNamaObat;
    private widget.Label jLabelNo;
    private widget.Label jLabelObatDicurigai;
    private widget.Label jLabelObatJKN;
    private widget.Label jLabelPasienData;
    private widget.Label jLabelPekerjaan;
    private widget.Label jLabelPelapor;
    private widget.Label jLabelPenyakit;
    private widget.Label jLabelQ1;
    private widget.Label jLabelQ10;
    private widget.Label jLabelQ2;
    private widget.Label jLabelQ3;
    private widget.Label jLabelQ4;
    private widget.Label jLabelQ5;
    private widget.Label jLabelQ6;
    private widget.Label jLabelQ7;
    private widget.Label jLabelQ8;
    private widget.Label jLabelQ9;
    private widget.Label jLabelRawat;
    private widget.Label jLabelRiwayat;
    private widget.Label jLabelSkor;
    private widget.Label jLabelSuku;
    private widget.Label jLabelTgl;
    private widget.Label jLabelTglKesESO;
    private widget.Label jLabelTglLab;
    private widget.Label jLabelTglLahir;
    private widget.PopupMenu jPopupMenu1;
    private javax.swing.JSeparator jSeparatorMeso1;
    private javax.swing.JSeparator jSeparatorMeso2;
    private javax.swing.JSeparator jSeparatorMeso3;
    private javax.swing.JSeparator jSeparatorMeso4;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollInput;
    private widget.ScrollPane scrollObatData;
    private widget.ScrollPane scrollObatMeso;
    private widget.ScrollPane scroll_Anamnesa;
    private widget.ScrollPane scroll_DataLab;
    private widget.ScrollPane scroll_KeteranganTambahan;
    private widget.ScrollPane scroll_ManifestasiESO;
    private widget.ScrollPane scroll_MasalahMutu;
    private widget.ScrollPane scroll_PenyakitUtama;
    private widget.ScrollPane scroll_RiwayatESO;
    private widget.Table tbObat;
    private widget.Table tbObatData;
    private widget.Table tbObatMeso;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            if(TCari.getText().trim().equals("")){
                ps=koneksi.prepareStatement(
                    "select laporan_meso.no_laporan,laporan_meso.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,laporan_meso.tanggal,"+
                    "laporan_meso.nip,petugas.nama,pasien.tgl_lahir,pasien.jk,suku_bangsa.nama_suku_bangsa,pasien.pekerjaan,laporan_meso.berat_badan,"+
                    "laporan_meso.anamnesa,laporan_meso.penyakit_utama,laporan_meso.kesudahan_penyakit,laporan_meso.gangguan_ginjal,"+
                    "laporan_meso.gangguan_hati,laporan_meso.alergi,laporan_meso.kondisi_medis_lain,laporan_meso.faktor_industri,"+
                    "laporan_meso.lain_lain,laporan_meso.ket_lain_lain,laporan_meso.manifestasi_eso,laporan_meso.masalah_mutu,"+
                    "laporan_meso.tgl_mula_eso,laporan_meso.kesudahan_eso,laporan_meso.tgl_kesudahan_eso,laporan_meso.riwayat_eso,"+
                    "laporan_meso.keterangan_tambahan,laporan_meso.data_laboratorium,laporan_meso.tgl_pemeriksaan_lab,laporan_meso.naranjo_1,"+
                    "laporan_meso.skor_naranjo_1,laporan_meso.naranjo_2,laporan_meso.skor_naranjo_2,laporan_meso.naranjo_3,"+
                    "laporan_meso.skor_naranjo_3,laporan_meso.naranjo_4,laporan_meso.skor_naranjo_4,laporan_meso.naranjo_5,"+
                    "laporan_meso.skor_naranjo_5,laporan_meso.naranjo_6,laporan_meso.skor_naranjo_6,laporan_meso.naranjo_7,"+
                    "laporan_meso.skor_naranjo_7,laporan_meso.naranjo_8,laporan_meso.skor_naranjo_8,laporan_meso.naranjo_9,"+
                    "laporan_meso.skor_naranjo_9,laporan_meso.naranjo_10,laporan_meso.skor_naranjo_10,laporan_meso.skor_naranjo,"+
                    "laporan_meso.kategori_naranjo from laporan_meso inner join reg_periksa on laporan_meso.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join suku_bangsa on pasien.suku_bangsa=suku_bangsa.id "+
                    "inner join petugas on laporan_meso.nip=petugas.nip where laporan_meso.tanggal between ? and ? order by laporan_meso.tanggal"
                );
            }else{
                ps=koneksi.prepareStatement(
                    "select laporan_meso.no_laporan,laporan_meso.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,laporan_meso.tanggal,"+
                    "laporan_meso.nip,petugas.nama,pasien.tgl_lahir,pasien.jk,suku_bangsa.nama_suku_bangsa,pasien.pekerjaan,laporan_meso.berat_badan,"+
                    "laporan_meso.anamnesa,laporan_meso.penyakit_utama,laporan_meso.kesudahan_penyakit,laporan_meso.gangguan_ginjal,"+
                    "laporan_meso.gangguan_hati,laporan_meso.alergi,laporan_meso.kondisi_medis_lain,laporan_meso.faktor_industri,"+
                    "laporan_meso.lain_lain,laporan_meso.ket_lain_lain,laporan_meso.manifestasi_eso,laporan_meso.masalah_mutu,"+
                    "laporan_meso.tgl_mula_eso,laporan_meso.kesudahan_eso,laporan_meso.tgl_kesudahan_eso,laporan_meso.riwayat_eso,"+
                    "laporan_meso.keterangan_tambahan,laporan_meso.data_laboratorium,laporan_meso.tgl_pemeriksaan_lab,laporan_meso.naranjo_1,"+
                    "laporan_meso.skor_naranjo_1,laporan_meso.naranjo_2,laporan_meso.skor_naranjo_2,laporan_meso.naranjo_3,"+
                    "laporan_meso.skor_naranjo_3,laporan_meso.naranjo_4,laporan_meso.skor_naranjo_4,laporan_meso.naranjo_5,"+
                    "laporan_meso.skor_naranjo_5,laporan_meso.naranjo_6,laporan_meso.skor_naranjo_6,laporan_meso.naranjo_7,"+
                    "laporan_meso.skor_naranjo_7,laporan_meso.naranjo_8,laporan_meso.skor_naranjo_8,laporan_meso.naranjo_9,"+
                    "laporan_meso.skor_naranjo_9,laporan_meso.naranjo_10,laporan_meso.skor_naranjo_10,laporan_meso.skor_naranjo,"+
                    "laporan_meso.kategori_naranjo from laporan_meso inner join reg_periksa on laporan_meso.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join suku_bangsa on pasien.suku_bangsa=suku_bangsa.id "+
                    "inner join petugas on laporan_meso.nip=petugas.nip where laporan_meso.tanggal between ? and ? and "+
                    "(reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or laporan_meso.no_laporan like ? "+
                    "or laporan_meso.manifestasi_eso like ? or laporan_meso.nip like ? or petugas.nama like ?) order by laporan_meso.tanggal"
                );
            }
                
            try {
                ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                if(!TCari.getText().trim().equals("")){
                    ps.setString(3,"%"+TCari.getText()+"%");
                    ps.setString(4,"%"+TCari.getText()+"%");
                    ps.setString(5,"%"+TCari.getText()+"%");
                    ps.setString(6,"%"+TCari.getText()+"%");
                    ps.setString(7,"%"+TCari.getText()+"%");
                    ps.setString(8,"%"+TCari.getText()+"%");
                    ps.setString(9,"%"+TCari.getText()+"%");
                }
                  
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new Object[]{
                        rs.getString("no_laporan"),rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("tanggal"),
                        rs.getString("nip"),rs.getString("nama"),rs.getString("tgl_lahir"),rs.getString("jk"),rs.getString("nama_suku_bangsa"),
                        rs.getString("pekerjaan"),rs.getString("berat_badan"),rs.getString("anamnesa"),rs.getString("penyakit_utama"),
                        rs.getString("kesudahan_penyakit"),rs.getString("gangguan_ginjal"),rs.getString("gangguan_hati"),rs.getString("alergi"),
                        rs.getString("kondisi_medis_lain"),rs.getString("faktor_industri"),rs.getString("lain_lain"),rs.getString("ket_lain_lain"),
                        rs.getString("manifestasi_eso"),rs.getString("masalah_mutu"),rs.getString("tgl_mula_eso"),rs.getString("kesudahan_eso"),
                        rs.getString("tgl_kesudahan_eso"),rs.getString("riwayat_eso"),rs.getString("keterangan_tambahan"),rs.getString("data_laboratorium"),
                        rs.getString("tgl_pemeriksaan_lab"),rs.getString("naranjo_1"),rs.getString("skor_naranjo_1"),rs.getString("naranjo_2"),
                        rs.getString("skor_naranjo_2"),rs.getString("naranjo_3"),rs.getString("skor_naranjo_3"),rs.getString("naranjo_4"),
                        rs.getString("skor_naranjo_4"),rs.getString("naranjo_5"),rs.getString("skor_naranjo_5"),rs.getString("naranjo_6"),
                        rs.getString("skor_naranjo_6"),rs.getString("naranjo_7"),rs.getString("skor_naranjo_7"),rs.getString("naranjo_8"),
                        rs.getString("skor_naranjo_8"),rs.getString("naranjo_9"),rs.getString("skor_naranjo_9"),rs.getString("naranjo_10"),
                        rs.getString("skor_naranjo_10"),rs.getString("skor_naranjo"),rs.getString("kategori_naranjo")
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

    private void isMenu(){
        if(ChkAccor.isSelected()==true){
            ChkAccor.setVisible(false);
            PanelAccor.setPreferredSize(new Dimension(470,HEIGHT));
            FormMenu.setVisible(true);
            FormObatData.setVisible(true);
            ChkAccor.setVisible(true);
        }else if(ChkAccor.isSelected()==false){
            ChkAccor.setVisible(false);
            PanelAccor.setPreferredSize(new Dimension(15,HEIGHT));
            FormMenu.setVisible(false);
            FormObatData.setVisible(false);
            ChkAccor.setVisible(true);
        }
    }

    private void tampilObatPanel() {
        Valid.tabelKosong(tabModeObatData);
        if(tbObat.getSelectedRow()!= -1){
            TNoRMData.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            TPasienData.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            try {
                ps=koneksi.prepareStatement(
                    "select laporan_meso_obat.nama_obat,laporan_meso_obat.bentuk_sediaan,laporan_meso_obat.obat_jkn,laporan_meso_obat.no_batch,laporan_meso_obat.obat_dicurigai,laporan_meso_obat.cara,"+
                    "laporan_meso_obat.dosis_waktu,laporan_meso_obat.tgl_mulai,laporan_meso_obat.tgl_akhir,laporan_meso_obat.indikasi from laporan_meso_obat where laporan_meso_obat.no_laporan=? "+
                    "order by laporan_meso_obat.nama_obat"
                );
                try {
                    ps.setString(1,tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
                    rs=ps.executeQuery();
                    while(rs.next()){
                        tabModeObatData.addRow(new Object[]{
                            rs.getString("nama_obat"),rs.getString("bentuk_sediaan"),rs.getString("obat_jkn"),rs.getString("no_batch"),rs.getString("obat_dicurigai"),
                            rs.getString("cara"),rs.getString("dosis_waktu"),rs.getString("tgl_mulai"),rs.getString("tgl_akhir"),rs.getString("indikasi")
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
            } catch (Exception e) {
                System.out.println("Notif : "+e);
            }
        }
    }

    public void emptTeks() {
        Tanggal.setDate(new Date());
        BeratBadan.setText("");
        Anamnesa.setText("");
        PenyakitUtama.setText("");
        KesudahanPenyakit.setSelectedIndex(0);
        GangguanGinjal.setSelected(false);
        GangguanHati.setSelected(false);
        Alergi.setSelected(false);
        KondisiMedisLain.setSelected(false);
        FaktorIndustri.setSelected(false);
        LainLain.setSelected(false);
        KetLainLain.setText("");
        ManifestasiESO.setText("");
        MasalahMutu.setText("");
        TglMulaESO.setDate(new Date());
        KesudahanESO.setSelectedIndex(0);
        TglKesudahanESO.setDate(new Date());
        RiwayatESO.setText("");
        KeteranganTambahan.setText("");
        DataLab.setText("");
        TglLab.setDate(new Date());
        Naranjo1.setSelectedIndex(0);
        Naranjo2.setSelectedIndex(0);
        Naranjo3.setSelectedIndex(0);
        Naranjo4.setSelectedIndex(0);
        Naranjo5.setSelectedIndex(0);
        Naranjo6.setSelectedIndex(0);
        Naranjo7.setSelectedIndex(0);
        Naranjo8.setSelectedIndex(0);
        Naranjo9.setSelectedIndex(0);
        Naranjo10.setSelectedIndex(0);
        Valid.tabelKosong(tabModeObat);
        SkorN1.setText("0");
        SkorN2.setText("0");
        SkorN3.setText("0");
        SkorN4.setText("0");
        SkorN5.setText("0");
        SkorN6.setText("0");
        SkorN7.setText("0");
        SkorN8.setText("0");
        SkorN9.setText("p");
        SkorN10.setText("0");
        TabRawat.setSelectedIndex(0);
        BeratBadan.requestFocus();
        Valid.autoNomer3(
            "select ifnull(MAX(CONVERT(RIGHT(laporan_meso.no_laporan,3),signed)),0) from laporan_meso where date(laporan_meso.tanggal)='"+Valid.SetTgl(Tanggal.getSelectedItem()+"")+"' ",
            "ESO"+Tanggal.getSelectedItem().toString().substring(6,10)+Tanggal.getSelectedItem().toString().substring(3,5)+Tanggal.getSelectedItem().toString().substring(0,2),3,NoLaporan
        );
    }

    private void hitungSkor() {
        int sN1=Naranjo1.getSelectedIndex()==1?1:Naranjo1.getSelectedIndex()==2?0:0;
        int sN2=Naranjo2.getSelectedIndex()==1?2:Naranjo2.getSelectedIndex()==2?-1:0;
        int sN3=Naranjo3.getSelectedIndex()==1?1:Naranjo3.getSelectedIndex()==2?0:0;
        int sN4=Naranjo4.getSelectedIndex()==1?2:Naranjo4.getSelectedIndex()==2?-1:0;
        int sN5=Naranjo5.getSelectedIndex()==1?-1:Naranjo5.getSelectedIndex()==2?2:0;
        int sN6=Naranjo6.getSelectedIndex()==1?-1:Naranjo6.getSelectedIndex()==2?1:0;
        int sN7=Naranjo7.getSelectedIndex()==1?1:Naranjo7.getSelectedIndex()==2?0:0;
        int sN8=Naranjo8.getSelectedIndex()==1?1:Naranjo8.getSelectedIndex()==2?0:0;
        int sN9=Naranjo9.getSelectedIndex()==1?1:Naranjo9.getSelectedIndex()==2?0:0;
        int sN10=Naranjo10.getSelectedIndex()==1?1:Naranjo10.getSelectedIndex()==2?0:0;
        SkorN1.setText(""+sN1);
        SkorN2.setText(""+sN2);
        SkorN3.setText(""+sN3);
        SkorN4.setText(""+sN4);
        SkorN5.setText(""+sN5);
        SkorN6.setText(""+sN6);
        SkorN7.setText(""+sN7);
        SkorN8.setText(""+sN8);
        SkorN9.setText(""+sN9);
        SkorN10.setText(""+sN10);
        
        int skor=sN1+sN2+sN3+sN4+sN5+sN6+sN7+sN8+sN9+sN10;
        SkorNaranjo.setText(""+skor);
        if(Naranjo1.getSelectedIndex()==0&&Naranjo2.getSelectedIndex()==0&&Naranjo3.getSelectedIndex()==0&&Naranjo4.getSelectedIndex()==0&&Naranjo5.getSelectedIndex()==0&&Naranjo6.getSelectedIndex()==0&&Naranjo7.getSelectedIndex()==0&&Naranjo8.getSelectedIndex()==0&&Naranjo9.getSelectedIndex()==0&&Naranjo10.getSelectedIndex()==0){
            KategoriNaranjo.setText("-");
        }else{
            KategoriNaranjo.setText(skor>=9?"Highly Probable":skor>=5?"Probable":skor>=1?"Possible":"Doubtful");
        }
    }

    private void getDataObat() {
        if(tbObatMeso.getSelectedRow()!= -1){
            NamaObat.setText(tabModeObat.getValueAt(tbObatMeso.getSelectedRow(),0).toString());
            BentukSediaan.setText(tabModeObat.getValueAt(tbObatMeso.getSelectedRow(),1).toString());
            ObatJKN.setSelectedItem(tabModeObat.getValueAt(tbObatMeso.getSelectedRow(),2).toString());
            NoBatch.setText(tabModeObat.getValueAt(tbObatMeso.getSelectedRow(),3).toString());
            ObatDicurigai.setSelectedItem(tabModeObat.getValueAt(tbObatMeso.getSelectedRow(),4).toString());
            Cara.setText(tabModeObat.getValueAt(tbObatMeso.getSelectedRow(),5).toString());
            DosisWaktu.setText(tabModeObat.getValueAt(tbObatMeso.getSelectedRow(),6).toString());
            Valid.SetTgl(TglMulaObat,tabModeObat.getValueAt(tbObatMeso.getSelectedRow(),7).toString());
            Valid.SetTgl(TglAkhirObat,tabModeObat.getValueAt(tbObatMeso.getSelectedRow(),8).toString());
            Indikasi.setText(tabModeObat.getValueAt(tbObatMeso.getSelectedRow(),9).toString());
        }
    }

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            NoLaporan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            Valid.SetTgl2(Tanggal,tbObat.getValueAt(tbObat.getSelectedRow(),4).toString());
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());
            Kelamin.setText(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString().replaceAll("L","Laki-laki").replaceAll("P","Perempuan"));
            Suku.setText(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            Pekerjaan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            BeratBadan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            Anamnesa.setText(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            PenyakitUtama.setText(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            KesudahanPenyakit.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            GangguanGinjal.setSelected(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString().equals("Ya"));
            GangguanHati.setSelected(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString().equals("Ya"));
            Alergi.setSelected(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString().equals("Ya"));
            KondisiMedisLain.setSelected(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString().equals("Ya"));
            FaktorIndustri.setSelected(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString().equals("Ya"));
            LainLain.setSelected(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString().equals("Ya"));
            KetLainLain.setText(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString());
            ManifestasiESO.setText(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString());
            MasalahMutu.setText(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString());
            Valid.SetTgl(TglMulaESO,tbObat.getValueAt(tbObat.getSelectedRow(),24).toString());
            KesudahanESO.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),25).toString());
            Valid.SetTgl(TglKesudahanESO,tbObat.getValueAt(tbObat.getSelectedRow(),26).toString());
            RiwayatESO.setText(tbObat.getValueAt(tbObat.getSelectedRow(),27).toString());
            KeteranganTambahan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),28).toString());
            DataLab.setText(tbObat.getValueAt(tbObat.getSelectedRow(),29).toString());
            Valid.SetTgl(TglLab,tbObat.getValueAt(tbObat.getSelectedRow(),30).toString());
            Naranjo1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),31).toString());
            SkorN1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),32).toString());
            Naranjo2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),33).toString());
            SkorN2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),34).toString());
            Naranjo3.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),35).toString());
            SkorN3.setText(tbObat.getValueAt(tbObat.getSelectedRow(),36).toString());
            Naranjo4.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),37).toString());
            SkorN4.setText(tbObat.getValueAt(tbObat.getSelectedRow(),38).toString());
            Naranjo5.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),39).toString());
            SkorN5.setText(tbObat.getValueAt(tbObat.getSelectedRow(),40).toString());
            Naranjo6.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),41).toString());
            SkorN6.setText(tbObat.getValueAt(tbObat.getSelectedRow(),42).toString());
            Naranjo7.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),43).toString());
            SkorN7.setText(tbObat.getValueAt(tbObat.getSelectedRow(),44).toString());
            Naranjo8.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),45).toString());
            SkorN8.setText(tbObat.getValueAt(tbObat.getSelectedRow(),46).toString());
            Naranjo9.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),47).toString());
            SkorN9.setText(tbObat.getValueAt(tbObat.getSelectedRow(),48).toString());
            Naranjo10.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),49).toString());
            SkorN10.setText(tbObat.getValueAt(tbObat.getSelectedRow(),50).toString());
            SkorNaranjo.setText(tbObat.getValueAt(tbObat.getSelectedRow(),51).toString());
            KategoriNaranjo.setText(tbObat.getValueAt(tbObat.getSelectedRow(),52).toString());
            Valid.tabelKosong(tabModeObat);
            for(i=0;i<tabModeObatData.getRowCount();i++){
                tabModeObat.addRow(new Object[]{
                    tabModeObatData.getValueAt(i,0),tabModeObatData.getValueAt(i,1),tabModeObatData.getValueAt(i,2),tabModeObatData.getValueAt(i,3),tabModeObatData.getValueAt(i,4),
                    tabModeObatData.getValueAt(i,5),tabModeObatData.getValueAt(i,6),tabModeObatData.getValueAt(i,7),tabModeObatData.getValueAt(i,8),tabModeObatData.getValueAt(i,9)
                });
            }
        }
    }

    private void isRawat() {
        try {
            ps=koneksi.prepareStatement(
                "select reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.pekerjaan,suku_bangsa.nama_suku_bangsa,pasien.tgl_lahir,"+
                "reg_periksa.tgl_registrasi,reg_periksa.jam_reg from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join suku_bangsa on pasien.suku_bangsa=suku_bangsa.id where reg_periksa.no_rawat=?"
            );
            try {
                ps.setString(1,TNoRw.getText());
                rs=ps.executeQuery();
                if(rs.next()){
                    TNoRM.setText(rs.getString("no_rkm_medis"));
                    DTPCari1.setDate(rs.getDate("tgl_registrasi"));
                    TPasien.setText(rs.getString("nm_pasien"));
                    TglLahir.setText(rs.getString("tgl_lahir"));
                    Kelamin.setText(rs.getString("jk").replaceAll("L","Laki-laki").replaceAll("P","Perempuan"));
                    Suku.setText(rs.getString("nama_suku_bangsa"));
                    Pekerjaan.setText(rs.getString("pekerjaan"));
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
    
    public void setNoRm(String norwt,Date tgl2) {
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        DTPCari2.setDate(tgl2);
        isRawat(); 
        runBackground(() ->tampil());
    }
    public void setTampil(){
       TabRawat.setSelectedIndex(1);
    }

    public void isCek(){
        BtnSimpan.setEnabled(akses.getlaporan_meso());
        BtnHapus.setEnabled(akses.getlaporan_meso());
        BtnEdit.setEnabled(akses.getlaporan_meso());
        BtnPrint.setEnabled(akses.getlaporan_meso()); 
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
  
    private void simpan() {
        if(Sequel.menyimpantf2("laporan_meso","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?",46,new String[]{
            NoLaporan.getText(),TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19),KdPetugas.getText(),
            BeratBadan.getText(),Anamnesa.getText(),PenyakitUtama.getText(),KesudahanPenyakit.getSelectedItem().toString(),(GangguanGinjal.isSelected()?"Ya":"Tidak"),
            (GangguanHati.isSelected()?"Ya":"Tidak"),(Alergi.isSelected()?"Ya":"Tidak"),(KondisiMedisLain.isSelected()?"Ya":"Tidak"),(FaktorIndustri.isSelected()?"Ya":"Tidak"),
            (LainLain.isSelected()?"Ya":"Tidak"),KetLainLain.getText(),ManifestasiESO.getText(),MasalahMutu.getText(),Valid.SetTgl(TglMulaESO.getSelectedItem()+""),
            KesudahanESO.getSelectedItem().toString(),Valid.SetTgl(TglKesudahanESO.getSelectedItem()+""),RiwayatESO.getText(),KeteranganTambahan.getText(),DataLab.getText(),
            Valid.SetTgl(TglLab.getSelectedItem()+""),Naranjo1.getSelectedItem().toString(),SkorN1.getText(),Naranjo2.getSelectedItem().toString(),SkorN2.getText(),
            Naranjo3.getSelectedItem().toString(),SkorN3.getText(),Naranjo4.getSelectedItem().toString(),SkorN4.getText(),Naranjo5.getSelectedItem().toString(),SkorN5.getText(),
            Naranjo6.getSelectedItem().toString(),SkorN6.getText(),Naranjo7.getSelectedItem().toString(),SkorN7.getText(),Naranjo8.getSelectedItem().toString(),SkorN8.getText(),
            Naranjo9.getSelectedItem().toString(),SkorN9.getText(),Naranjo10.getSelectedItem().toString(),SkorN10.getText(),SkorNaranjo.getText(),KategoriNaranjo.getText()
        })==true){
                tabMode.addRow(new Object[]{
                    NoLaporan.getText(),TNoRw.getText(),TNoRM.getText(),TPasien.getText(),
                    Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19),KdPetugas.getText(),NmPetugas.getText(),TglLahir.getText(),
                    Kelamin.getText().replaceAll("Laki-laki","L").replaceAll("Perempuan","P"),Suku.getText(),Pekerjaan.getText(),BeratBadan.getText(),Anamnesa.getText(),
                    PenyakitUtama.getText(),KesudahanPenyakit.getSelectedItem().toString(),(GangguanGinjal.isSelected()?"Ya":"Tidak"),(GangguanHati.isSelected()?"Ya":"Tidak"),
                    (Alergi.isSelected()?"Ya":"Tidak"),(KondisiMedisLain.isSelected()?"Ya":"Tidak"),(FaktorIndustri.isSelected()?"Ya":"Tidak"),(LainLain.isSelected()?"Ya":"Tidak"),
                    KetLainLain.getText(),ManifestasiESO.getText(),MasalahMutu.getText(),Valid.SetTgl(TglMulaESO.getSelectedItem()+""),KesudahanESO.getSelectedItem().toString(),
                    Valid.SetTgl(TglKesudahanESO.getSelectedItem()+""),RiwayatESO.getText(),KeteranganTambahan.getText(),DataLab.getText(),Valid.SetTgl(TglLab.getSelectedItem()+""),
                    Naranjo1.getSelectedItem().toString(),SkorN1.getText(),Naranjo2.getSelectedItem().toString(),SkorN2.getText(),Naranjo3.getSelectedItem().toString(),
                    SkorN3.getText(),Naranjo4.getSelectedItem().toString(),SkorN4.getText(),Naranjo5.getSelectedItem().toString(),SkorN5.getText(),Naranjo6.getSelectedItem().toString(),
                    SkorN6.getText(),Naranjo7.getSelectedItem().toString(),SkorN7.getText(),Naranjo8.getSelectedItem().toString(),SkorN8.getText(),Naranjo9.getSelectedItem().toString(),
                    SkorN9.getText(),Naranjo10.getSelectedItem().toString(),SkorN10.getText(),SkorNaranjo.getText(),KategoriNaranjo.getText()
                });
                LCount.setText(""+tabMode.getRowCount());
                Valid.tabelKosong(tabModeObatData);
                for(i=0;i<tabModeObat.getRowCount();i++){
                    if(Sequel.menyimpantf2("laporan_meso_obat","?,?,?,?,?,?,?,?,?,?,?",11,new String[]{
                            NoLaporan.getText(),tabModeObat.getValueAt(i,0).toString(),tabModeObat.getValueAt(i,1).toString(),tabModeObat.getValueAt(i,2).toString(),
                            tabModeObat.getValueAt(i,3).toString(),tabModeObat.getValueAt(i,4).toString(),tabModeObat.getValueAt(i,5).toString(),tabModeObat.getValueAt(i,6).toString(),
                            tabModeObat.getValueAt(i,7).toString(),tabModeObat.getValueAt(i,8).toString(),tabModeObat.getValueAt(i,9).toString()
                        })==true){
                        tabModeObatData.addRow(new Object[]{
                            tabModeObat.getValueAt(i,0).toString(),tabModeObat.getValueAt(i,1).toString(),tabModeObat.getValueAt(i,2).toString(),tabModeObat.getValueAt(i,3).toString(),
                            tabModeObat.getValueAt(i,4).toString(),tabModeObat.getValueAt(i,5).toString(),tabModeObat.getValueAt(i,6).toString(),tabModeObat.getValueAt(i,7).toString(),
                            tabModeObat.getValueAt(i,8).toString(),tabModeObat.getValueAt(i,9).toString()
                        });
                    }
                }
                TNoRMData.setText(TNoRM.getText());
                TPasienData.setText(TPasien.getText());
                emptTeks();
        }
    }

    private void ganti() {
        if(Sequel.mengedittf2("laporan_meso","no_laporan=?","no_laporan=?,no_rawat=?,tanggal=?,nip=?,berat_badan=?,anamnesa=?,penyakit_utama=?,kesudahan_penyakit=?,gangguan_ginjal=?,"+
            "gangguan_hati=?,alergi=?,kondisi_medis_lain=?,faktor_industri=?,lain_lain=?,ket_lain_lain=?,manifestasi_eso=?,masalah_mutu=?,tgl_mula_eso=?,kesudahan_eso=?,tgl_kesudahan_eso=?,"+
            "riwayat_eso=?,keterangan_tambahan=?,data_laboratorium=?,tgl_pemeriksaan_lab=?,naranjo_1=?,skor_naranjo_1=?,naranjo_2=?,skor_naranjo_2=?,naranjo_3=?,skor_naranjo_3=?,naranjo_4=?,"+
            "skor_naranjo_4=?,naranjo_5=?,skor_naranjo_5=?,naranjo_6=?,skor_naranjo_6=?,naranjo_7=?,skor_naranjo_7=?,naranjo_8=?,skor_naranjo_8=?,naranjo_9=?,skor_naranjo_9=?,naranjo_10=?,"+
            "skor_naranjo_10=?,skor_naranjo=?,kategori_naranjo=?",47,new String[]{
            NoLaporan.getText(),TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19),KdPetugas.getText(),BeratBadan.getText(),
            Anamnesa.getText(),PenyakitUtama.getText(),KesudahanPenyakit.getSelectedItem().toString(),(GangguanGinjal.isSelected()?"Ya":"Tidak"),(GangguanHati.isSelected()?"Ya":"Tidak"),
            (Alergi.isSelected()?"Ya":"Tidak"),(KondisiMedisLain.isSelected()?"Ya":"Tidak"),(FaktorIndustri.isSelected()?"Ya":"Tidak"),(LainLain.isSelected()?"Ya":"Tidak"),KetLainLain.getText(),
            ManifestasiESO.getText(),MasalahMutu.getText(),Valid.SetTgl(TglMulaESO.getSelectedItem()+""),KesudahanESO.getSelectedItem().toString(),Valid.SetTgl(TglKesudahanESO.getSelectedItem()+""),
            RiwayatESO.getText(),KeteranganTambahan.getText(),DataLab.getText(),Valid.SetTgl(TglLab.getSelectedItem()+""),Naranjo1.getSelectedItem().toString(),SkorN1.getText(),
            Naranjo2.getSelectedItem().toString(),SkorN2.getText(),Naranjo3.getSelectedItem().toString(),SkorN3.getText(),Naranjo4.getSelectedItem().toString(),SkorN4.getText(),
            Naranjo5.getSelectedItem().toString(),SkorN5.getText(),Naranjo6.getSelectedItem().toString(),SkorN6.getText(),Naranjo7.getSelectedItem().toString(),SkorN7.getText(),
            Naranjo8.getSelectedItem().toString(),SkorN8.getText(),Naranjo9.getSelectedItem().toString(),SkorN9.getText(),Naranjo10.getSelectedItem().toString(),SkorN10.getText(),
            SkorNaranjo.getText(),KategoriNaranjo.getText(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
        })==true){
            tbObat.setValueAt(NoLaporan.getText(),tbObat.getSelectedRow(),0);
            tbObat.setValueAt(TNoRw.getText(),tbObat.getSelectedRow(),1);
            tbObat.setValueAt(TNoRM.getText(),tbObat.getSelectedRow(),2);
            tbObat.setValueAt(TPasien.getText(),tbObat.getSelectedRow(),3);
            tbObat.setValueAt(Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19),tbObat.getSelectedRow(),4);
            tbObat.setValueAt(KdPetugas.getText(),tbObat.getSelectedRow(),5);
            tbObat.setValueAt(NmPetugas.getText(),tbObat.getSelectedRow(),6);
            tbObat.setValueAt(TglLahir.getText(),tbObat.getSelectedRow(),7);
            tbObat.setValueAt(Kelamin.getText().replaceAll("Laki-laki","L").replaceAll("Perempuan","P"),tbObat.getSelectedRow(),8);
            tbObat.setValueAt(Suku.getText(),tbObat.getSelectedRow(),9);
            tbObat.setValueAt(Pekerjaan.getText(),tbObat.getSelectedRow(),10);
            tbObat.setValueAt(BeratBadan.getText(),tbObat.getSelectedRow(),11);
            tbObat.setValueAt(Anamnesa.getText(),tbObat.getSelectedRow(),12);
            tbObat.setValueAt(PenyakitUtama.getText(),tbObat.getSelectedRow(),13);
            tbObat.setValueAt(KesudahanPenyakit.getSelectedItem().toString(),tbObat.getSelectedRow(),14);
            tbObat.setValueAt((GangguanGinjal.isSelected()?"Ya":"Tidak"),tbObat.getSelectedRow(),15);
            tbObat.setValueAt((GangguanHati.isSelected()?"Ya":"Tidak"),tbObat.getSelectedRow(),16);
            tbObat.setValueAt((Alergi.isSelected()?"Ya":"Tidak"),tbObat.getSelectedRow(),17);
            tbObat.setValueAt((KondisiMedisLain.isSelected()?"Ya":"Tidak"),tbObat.getSelectedRow(),18);
            tbObat.setValueAt((FaktorIndustri.isSelected()?"Ya":"Tidak"),tbObat.getSelectedRow(),19);
            tbObat.setValueAt((LainLain.isSelected()?"Ya":"Tidak"),tbObat.getSelectedRow(),20);
            tbObat.setValueAt(KetLainLain.getText(),tbObat.getSelectedRow(),21);
            tbObat.setValueAt(ManifestasiESO.getText(),tbObat.getSelectedRow(),22);
            tbObat.setValueAt(MasalahMutu.getText(),tbObat.getSelectedRow(),23);
            tbObat.setValueAt(Valid.SetTgl(TglMulaESO.getSelectedItem()+""),tbObat.getSelectedRow(),24);
            tbObat.setValueAt(KesudahanESO.getSelectedItem().toString(),tbObat.getSelectedRow(),25);
            tbObat.setValueAt(Valid.SetTgl(TglKesudahanESO.getSelectedItem()+""),tbObat.getSelectedRow(),26);
            tbObat.setValueAt(RiwayatESO.getText(),tbObat.getSelectedRow(),27);
            tbObat.setValueAt(KeteranganTambahan.getText(),tbObat.getSelectedRow(),28);
            tbObat.setValueAt(DataLab.getText(),tbObat.getSelectedRow(),29);
            tbObat.setValueAt(Valid.SetTgl(TglLab.getSelectedItem()+""),tbObat.getSelectedRow(),30);
            tbObat.setValueAt(Naranjo1.getSelectedItem().toString(),tbObat.getSelectedRow(),31);
            tbObat.setValueAt(SkorN1.getText(),tbObat.getSelectedRow(),32);
            tbObat.setValueAt(Naranjo2.getSelectedItem().toString(),tbObat.getSelectedRow(),33);
            tbObat.setValueAt(SkorN2.getText(),tbObat.getSelectedRow(),34);
            tbObat.setValueAt(Naranjo3.getSelectedItem().toString(),tbObat.getSelectedRow(),35);
            tbObat.setValueAt(SkorN3.getText(),tbObat.getSelectedRow(),36);
            tbObat.setValueAt(Naranjo4.getSelectedItem().toString(),tbObat.getSelectedRow(),37);
            tbObat.setValueAt(SkorN4.getText(),tbObat.getSelectedRow(),38);
            tbObat.setValueAt(Naranjo5.getSelectedItem().toString(),tbObat.getSelectedRow(),39);
            tbObat.setValueAt(SkorN5.getText(),tbObat.getSelectedRow(),40);
            tbObat.setValueAt(Naranjo6.getSelectedItem().toString(),tbObat.getSelectedRow(),41);
            tbObat.setValueAt(SkorN6.getText(),tbObat.getSelectedRow(),42);
            tbObat.setValueAt(Naranjo7.getSelectedItem().toString(),tbObat.getSelectedRow(),43);
            tbObat.setValueAt(SkorN7.getText(),tbObat.getSelectedRow(),44);
            tbObat.setValueAt(Naranjo8.getSelectedItem().toString(),tbObat.getSelectedRow(),45);
            tbObat.setValueAt(SkorN8.getText(),tbObat.getSelectedRow(),46);
            tbObat.setValueAt(Naranjo9.getSelectedItem().toString(),tbObat.getSelectedRow(),47);
            tbObat.setValueAt(SkorN9.getText(),tbObat.getSelectedRow(),48);
            tbObat.setValueAt(Naranjo10.getSelectedItem().toString(),tbObat.getSelectedRow(),49);
            tbObat.setValueAt(SkorN10.getText(),tbObat.getSelectedRow(),50);
            tbObat.setValueAt(SkorNaranjo.getText(),tbObat.getSelectedRow(),51);
            tbObat.setValueAt(KategoriNaranjo.getText(),tbObat.getSelectedRow(),52);
            Sequel.meghapus("laporan_meso_obat","no_laporan",tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
            Valid.tabelKosong(tabModeObatData);
            for(i=0;i<tabModeObat.getRowCount();i++){
                if(Sequel.menyimpantf2("laporan_meso_obat","?,?,?,?,?,?,?,?,?,?,?",11,new String[]{
                        NoLaporan.getText(),tabModeObat.getValueAt(i,0).toString(),tabModeObat.getValueAt(i,1).toString(),tabModeObat.getValueAt(i,2).toString(),
                        tabModeObat.getValueAt(i,3).toString(),tabModeObat.getValueAt(i,4).toString(),tabModeObat.getValueAt(i,5).toString(),tabModeObat.getValueAt(i,6).toString(),
                        tabModeObat.getValueAt(i,7).toString(),tabModeObat.getValueAt(i,8).toString(),tabModeObat.getValueAt(i,9).toString()
                    })==true){
                    tabModeObatData.addRow(new Object[]{
                        tabModeObat.getValueAt(i,0).toString(),tabModeObat.getValueAt(i,1).toString(),tabModeObat.getValueAt(i,2).toString(),tabModeObat.getValueAt(i,3).toString(),
                        tabModeObat.getValueAt(i,4).toString(),tabModeObat.getValueAt(i,5).toString(),tabModeObat.getValueAt(i,6).toString(),tabModeObat.getValueAt(i,7).toString(),
                        tabModeObat.getValueAt(i,8).toString(),tabModeObat.getValueAt(i,9).toString()
                    });
                }
            }
            TNoRMData.setText(TNoRM.getText());
            TPasienData.setText(TPasien.getText());
            emptTeks();
            TabRawat.setSelectedIndex(1);
        }
    }

    private void hapus() {
        if(Sequel.queryu2tf("delete from laporan_meso where no_laporan=?",1,new String[]{
            tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
        })==true){
            tabMode.removeRow(tbObat.getSelectedRow());
            LCount.setText(""+tabMode.getRowCount());
            TNoRMData.setText("");
            TPasienData.setText("");
            Valid.tabelKosong(tabModeObatData);
            TabRawat.setSelectedIndex(1);
        }else{
            JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
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




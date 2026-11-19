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
import kepegawaian.DlgCariDokter;


/**
 * 
 * @author windiartohugroho
 */
public final class RMPenilaianAwalMedisRalanGigi extends javax.swing.JDialog {
    private final DefaultTableModel tabMode,tabModeGigi,tabModeGigiDetail;
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
    
    public RMPenilaianAwalMedisRalanGigi(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(628,674);
        
        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.RM","Nama Pasien","Tgl.Lahir","J.K.","Kode Dokter","Nama Dokter","Tanggal","Anamnesis","Hubungan","Keluhan Utama",
            "Riwayat Penyakit Sekarang","Riwayat Penyakit Dahulu","Riwayat Pengobatan","Alergi","Riwayat Penyakit Keluarga","Occlusi",
            "Torus Palatinus","Torus Mandibularis","Supernumerary Teeth","Gigi Anomali","Diastema","Lain-lain","Data Penunjang","Diagnosa",
            "Masalah Keperawatan","Tatalaksana/Terapi"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 27; i++) {
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
                column.setPreferredWidth(75);
            }else if(i==5){
                column.setPreferredWidth(80);
            }else if(i==6){
                column.setPreferredWidth(150);
            }else if(i==7){
                column.setPreferredWidth(115);
            }else if(i==8){
                column.setPreferredWidth(90);
            }else if(i==9){
                column.setPreferredWidth(100);
            }else if(i==10){
                column.setPreferredWidth(200);
            }else if(i==11){
                column.setPreferredWidth(200);
            }else if(i==12){
                column.setPreferredWidth(200);
            }else if(i==13){
                column.setPreferredWidth(200);
            }else if(i==14){
                column.setPreferredWidth(150);
            }else if(i==15){
                column.setPreferredWidth(200);
            }else if(i==16){
                column.setPreferredWidth(90);
            }else if(i==17){
                column.setPreferredWidth(100);
            }else if(i==18){
                column.setPreferredWidth(110);
            }else if(i==19){
                column.setPreferredWidth(150);
            }else if(i==20){
                column.setPreferredWidth(150);
            }else if(i==21){
                column.setPreferredWidth(150);
            }else if(i==22){
                column.setPreferredWidth(200);
            }else if(i==23){
                column.setPreferredWidth(200);
            }else if(i==24){
                column.setPreferredWidth(200);
            }else if(i==25){
                column.setPreferredWidth(200);
            }else if(i==26){
                column.setPreferredWidth(200);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeGigi=new DefaultTableModel(null,new Object[]{"No.Gigi","Kondisi","Keterangan"}){
            @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbOdontogram.setModel(tabModeGigi);

        tbOdontogram.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbOdontogram.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 3; i++) {
            TableColumn column = tbOdontogram.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(45);
            }else if(i==1){
                column.setPreferredWidth(220);
            }else if(i==2){
                column.setPreferredWidth(450);
            }
        }
        tbOdontogram.setDefaultRenderer(Object.class, new WarnaTable());
        PanelGigi.setModel(tabModeGigi);

        tabModeGigiDetail=new DefaultTableModel(null,new Object[]{"No.Gigi","Kondisi","Keterangan"}){
            @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbOdontogram1.setModel(tabModeGigiDetail);

        tbOdontogram1.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbOdontogram1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 3; i++) {
            TableColumn column = tbOdontogram1.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(45);
            }else if(i==1){
                column.setPreferredWidth(220);
            }else if(i==2){
                column.setPreferredWidth(450);
            }
        }
        tbOdontogram1.setDefaultRenderer(Object.class, new WarnaTable());
        PanelGigi1.setModel(tabModeGigiDetail);
        
        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));    
        KdDokter.setDocument(new batasInput((byte)20).getKata(KdDokter));  
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        KeluhanUtama.setDocument(new batasInput((int)300).getKata(KeluhanUtama));
        RPS.setDocument(new batasInput((int)500).getKata(RPS));
        RPD.setDocument(new batasInput((int)500).getKata(RPD));
        RPO.setDocument(new batasInput((int)500).getKata(RPO));
        Alergi.setDocument(new batasInput((int)100).getKata(Alergi));
        RiwayatKeluarga.setDocument(new batasInput((int)300).getKata(RiwayatKeluarga));
        Hubungan.setDocument(new batasInput((int)30).getKata(Hubungan));
        KetGigi.setDocument(new batasInput((int)100).getKata(KetGigi));
        KetSupernumerary.setDocument(new batasInput((int)100).getKata(KetSupernumerary));
        KetGigiAnomali.setDocument(new batasInput((int)100).getKata(KetGigiAnomali));
        KetDiastema.setDocument(new batasInput((int)100).getKata(KetDiastema));
        LainLain.setDocument(new batasInput((int)200).getKata(LainLain));
        DataPenunjang.setDocument(new batasInput((int)500).getKata(DataPenunjang));
        Diagnosa.setDocument(new batasInput((int)500).getKata(Diagnosa));
        MasalahKeperawatan.setDocument(new batasInput((int)300).getKata(MasalahKeperawatan));
        Terapi.setDocument(new batasInput((int)500).getKata(Terapi));

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
        
        ChkAccor.setSelected(false);
        isMenu();
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
        MnPenilaianMedis = new widget.MenuItem();
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
        jLabelDokter = new widget.Label();
        KdDokter = new widget.TextBox();
        NmDokter = new widget.TextBox();
        BtnDokter = new widget.Button();
        jLabelTglLahir = new widget.Label();
        TglLahir = new widget.TextBox();
        jLabelJk = new widget.Label();
        Jk = new widget.TextBox();
        jLabelAnamnesis = new widget.Label();
        Anamnesis = new widget.ComboBox();
        Hubungan = new widget.TextBox();
        jSeparator1 = new javax.swing.JSeparator();
        jLabelH1 = new widget.Label();
        Alergi = new widget.TextBox();
        jSeparator2 = new javax.swing.JSeparator();
        jLabelH2 = new widget.Label();
        PanelGigi = new rekammedis.PanelOdontogram();
        jLabelGigi = new widget.Label();
        NoGigi = new widget.ComboBox();
        jLabelKondisi = new widget.Label();
        Kondisi = new widget.ComboBox();
        jLabelKetGigi = new widget.Label();
        KetGigi = new widget.TextBox();
        BtnTambahGigi = new widget.Button();
        BtnHapusGigi = new widget.Button();
        scrollOdontogram = new widget.ScrollPane();
        tbOdontogram = new widget.Table();
        jSeparator3 = new javax.swing.JSeparator();
        jLabelH3 = new widget.Label();
        Occlusi = new widget.ComboBox();
        jLabelOclusi = new widget.Label();
        TorusPalatinus = new widget.ComboBox();
        jLabelTorusP = new widget.Label();
        TorusMandibularis = new widget.ComboBox();
        jLabelTorusM = new widget.Label();
        jLabelSupernumerary = new widget.Label();
        Supernumerary = new widget.ComboBox();
        KetSupernumerary = new widget.TextBox();
        jLabelGigiAnomali = new widget.Label();
        GigiAnomali = new widget.ComboBox();
        KetGigiAnomali = new widget.TextBox();
        jLabelDiastema = new widget.Label();
        Diastema = new widget.ComboBox();
        KetDiastema = new widget.TextBox();
        jLabelLainLain = new widget.Label();
        scroll_LainLain = new widget.ScrollPane();
        LainLain = new widget.TextArea();
        jSeparator4 = new javax.swing.JSeparator();
        jLabelH4 = new widget.Label();
        scroll_DataPenunjang = new widget.ScrollPane();
        DataPenunjang = new widget.TextArea();
        jSeparator5 = new javax.swing.JSeparator();
        jLabelH5 = new widget.Label();
        jLabelDiagnosa = new widget.Label();
        scroll_Diagnosa = new widget.ScrollPane();
        Diagnosa = new widget.TextArea();
        jLabelMasalahKeperawatan = new widget.Label();
        scroll_MasalahKeperawatan = new widget.ScrollPane();
        MasalahKeperawatan = new widget.TextArea();
        jSeparator6 = new javax.swing.JSeparator();
        jLabelH6 = new widget.Label();
        scroll_Terapi = new widget.ScrollPane();
        Terapi = new widget.TextArea();
        jLabelRPS = new widget.Label();
        scroll_RPS = new widget.ScrollPane();
        RPS = new widget.TextArea();
        jLabelRPD = new widget.Label();
        scroll_RPD = new widget.ScrollPane();
        RPD = new widget.TextArea();
        scroll_RPO = new widget.ScrollPane();
        RPO = new widget.TextArea();
        jLabelAlergi1 = new widget.Label();
        scroll_RiwayatKeluarga = new widget.ScrollPane();
        RiwayatKeluarga = new widget.TextArea();
        scroll_KeluhanUtama = new widget.ScrollPane();
        KeluhanUtama = new widget.TextArea();
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
        jLabel44 = new widget.Label();
        jLabel45 = new widget.Label();
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
        PanelAccor = new widget.PanelBiasa();
        ChkAccor = new widget.CekBox();
        FormMenu = new widget.PanelBiasa();
        jLabelPasien1 = new widget.Label();
        TNoRM1 = new widget.TextBox();
        TPasien1 = new widget.TextBox();
        FormOdontogram = new widget.PanelBiasa();
        scrollOdontogram1 = new widget.ScrollPane();
        PanelGigi1 = new rekammedis.PanelOdontogram();
        scrollTabelOdontogram1 = new widget.ScrollPane();
        tbOdontogram1 = new widget.Table();

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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Pengkajian Awal Medis Rawat Jalan Gigi ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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
        FormInput.setPreferredSize(new java.awt.Dimension(870, 1033));
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

        TNoRM.setName("TNoRM"); // NOI18N
        TNoRM.setEditable(false);
        FormInput.add(TNoRM);
        TNoRM.setBounds(207, 10, 100, 23);

        TPasien.setEditable(false);
        TPasien.setName("TPasien"); // NOI18N
        FormInput.add(TPasien);
        TPasien.setBounds(309, 10, 260, 23);

        jLabelTgl.setText("Tanggal :");
        jLabelTgl.setName("jLabelTgl"); // NOI18N
        FormInput.add(jLabelTgl);
        jLabelTgl.setBounds(378, 40, 52, 23);

        Tanggal.setForeground(new java.awt.Color(50, 70, 50));
        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "18-11-2026 08:36:13" }));
        Tanggal.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        Tanggal.setName("Tanggal"); // NOI18N
        FormInput.add(Tanggal);
        Tanggal.setBounds(433, 40, 136, 23);

        jLabelDokter.setText("Dokter :");
        jLabelDokter.setName("jLabelDokter"); // NOI18N
        FormInput.add(jLabelDokter);
        jLabelDokter.setBounds(0, 40, 70, 23);

        KdDokter.setName("KdDokter"); // NOI18N
        FormInput.add(KdDokter);
        KdDokter.setBounds(74, 40, 90, 23);

        NmDokter.setEditable(false);
        NmDokter.setName("NmDokter"); // NOI18N
        FormInput.add(NmDokter);
        NmDokter.setBounds(166, 40, 178, 23);

        BtnDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter.setMnemonic('2');
        BtnDokter.setToolTipText("ALt+2");
        BtnDokter.setName("BtnDokter"); // NOI18N
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
        BtnDokter.setBounds(346, 40, 28, 23);

        jLabelTglLahir.setText("Tgl.Lahir :");
        jLabelTglLahir.setName("jLabelTglLahir"); // NOI18N
        FormInput.add(jLabelTglLahir);
        jLabelTglLahir.setBounds(580, 10, 60, 23);

        TglLahir.setEditable(false);
        TglLahir.setHighlighter(null);
        TglLahir.setName("TglLahir"); // NOI18N
        FormInput.add(TglLahir);
        TglLahir.setBounds(644, 10, 80, 23);

        jLabelJk.setText("J.K. :");
        jLabelJk.setName("jLabelJk"); // NOI18N
        FormInput.add(jLabelJk);
        jLabelJk.setBounds(740, 10, 30, 23);

        Jk.setEditable(false);
        Jk.setHighlighter(null);
        Jk.setName("Jk"); // NOI18N
        FormInput.add(Jk);
        Jk.setBounds(774, 10, 80, 23);

        jLabelAnamnesis.setText("Anamnesis :");
        jLabelAnamnesis.setName("jLabelAnamnesis"); // NOI18N
        FormInput.add(jLabelAnamnesis);
        jLabelAnamnesis.setBounds(575, 40, 70, 23);

        Anamnesis.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Autoanamnesis", "Alloanamnesis" }));
        Anamnesis.setName("Anamnesis"); // NOI18N
        Anamnesis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AnamnesisKeyPressed(evt);
            }
        });
        FormInput.add(Anamnesis);
        Anamnesis.setBounds(649, 40, 123, 23);

        Hubungan.setName("Hubungan"); // NOI18N
        Hubungan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HubunganKeyPressed(evt);
            }
        });
        FormInput.add(Hubungan);
        Hubungan.setBounds(774, 40, 80, 23);

        jSeparator1.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator1.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator1.setName("jSeparator1"); // NOI18N
        FormInput.add(jSeparator1);
        jSeparator1.setBounds(0, 70, 880, 1);

        jLabelH1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelH1.setText("I. RIWAYAT KESEHATAN");
        jLabelH1.setName("jLabelH1"); // NOI18N
        FormInput.add(jLabelH1);
        jLabelH1.setBounds(10, 70, 250, 23);

        Alergi.setName("Alergi"); // NOI18N
        Alergi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlergiKeyPressed(evt);
            }
        });
        FormInput.add(Alergi);
        Alergi.setBounds(594, 190, 260, 23);

        jSeparator2.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator2.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator2.setName("jSeparator2"); // NOI18N
        FormInput.add(jSeparator2);
        jSeparator2.setBounds(0, 240, 880, 1);

        jLabelH2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelH2.setText("II. STATUS ODONTOGRAM");
        jLabelH2.setName("jLabelH2"); // NOI18N
        FormInput.add(jLabelH2);
        jLabelH2.setBounds(10, 240, 300, 23);

        PanelGigi.setName("PanelGigi"); // NOI18N
        FormInput.add(PanelGigi);
        PanelGigi.setBounds(44, 260, 810, 183);

        jLabelGigi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelGigi.setText("No. Gigi");
        jLabelGigi.setName("jLabelGigi"); // NOI18N
        FormInput.add(jLabelGigi);
        jLabelGigi.setBounds(44, 450, 70, 23);

        NoGigi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "11", "12", "13", "14", "15", "16", "17", "18", "21", "22", "23", "24", "25", "26", "27", "28", "31", "32", "33", "34", "35", "36", "37", "38", "41", "42", "43", "44", "45", "46", "47", "48", "51", "52", "53", "54", "55", "61", "62", "63", "64", "65", "71", "72", "73", "74", "75", "81", "82", "83", "84", "85" }));
        NoGigi.setToolTipText("Notasi FDI: permanen 11-48, sulung 51-85");
        NoGigi.setName("NoGigi"); // NOI18N
        NoGigi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoGigiKeyPressed(evt);
            }
        });
        FormInput.add(NoGigi);
        NoGigi.setBounds(93, 450, 55, 23);

        jLabelKondisi.setText("Kondisi :");
        jLabelKondisi.setName("jLabelKondisi"); // NOI18N
        FormInput.add(jLabelKondisi);
        jLabelKondisi.setBounds(164, 450, 60, 23);

        Kondisi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "sou - Gigi Sehat (Normal)", "car - Karies", "non - Gigi Non-vital", "une - Gigi Belum Erupsi", "pre - Erupsi Sebagian", "ano - Gigi Anomali", "mis - Gigi Hilang", "rrx - Sisa Akar", "cfr - Fraktur Mahkota", "amf - Tambalan Amalgam", "cof - Tambalan Komposit", "gic - Tambalan Glass Ionomer", "fmc - Mahkota Logam Penuh", "poc - Mahkota Porselen", "mpc - Mahkota Porselen Fusi Logam", "rct - Perawatan Saluran Akar", "ipx - Implan", "meb - Jembatan Logam", "pob - Jembatan Porselen", "gtl - Gigi Tiruan Sebagian Lepasan", "gtp - Gigi Tiruan Penuh", "lai - Lainnya (Isi Keterangan)" }));
        Kondisi.setName("Kondisi"); // NOI18N
        Kondisi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KondisiKeyPressed(evt);
            }
        });
        FormInput.add(Kondisi);
        Kondisi.setBounds(228, 450, 215, 23);

        jLabelKetGigi.setText("Keterangan :");
        jLabelKetGigi.setName("jLabelKetGigi"); // NOI18N
        FormInput.add(jLabelKetGigi);
        jLabelKetGigi.setBounds(439, 450, 100, 23);

        KetGigi.setName("KetGigi"); // NOI18N
        KetGigi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetGigiKeyPressed(evt);
            }
        });
        FormInput.add(KetGigi);
        KetGigi.setBounds(542, 450, 312, 23);

        BtnTambahGigi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/add-file-16x16.png"))); // NOI18N
        BtnTambahGigi.setName("BtnTambahGigi"); // NOI18N
        BtnTambahGigi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTambahGigiActionPerformed(evt);
            }
        });
        FormInput.add(BtnTambahGigi);
        BtnTambahGigi.setBounds(44, 480, 28, 23);

        BtnHapusGigi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); // NOI18N
        BtnHapusGigi.setName("BtnHapusGigi"); // NOI18N
        BtnHapusGigi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnHapusGigiActionPerformed(evt);
            }
        });
        FormInput.add(BtnHapusGigi);
        BtnHapusGigi.setBounds(44, 510, 28, 23);

        scrollOdontogram.setName("scrollOdontogram"); // NOI18N
        scrollOdontogram.setOpaque(true);

        tbOdontogram.setName("tbOdontogram"); // NOI18N
        scrollOdontogram.setViewportView(tbOdontogram);

        FormInput.add(scrollOdontogram);
        scrollOdontogram.setBounds(76, 480, 778, 123);

        jSeparator3.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator3.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator3.setName("jSeparator3"); // NOI18N
        FormInput.add(jSeparator3);
        jSeparator3.setBounds(0, 610, 880, 1);

        jLabelH3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelH3.setText("III. PEMERIKSAAN GIGI DAN MULUT");
        jLabelH3.setName("jLabelH3"); // NOI18N
        FormInput.add(jLabelH3);
        jLabelH3.setBounds(10, 610, 300, 23);

        Occlusi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Cross Bite", "Deep Bite" }));
        Occlusi.setName("Occlusi"); // NOI18N
        Occlusi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                OcclusiKeyPressed(evt);
            }
        });
        FormInput.add(Occlusi);
        Occlusi.setBounds(90, 630, 95, 23);

        jLabelOclusi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelOclusi.setText("Occlusi");
        jLabelOclusi.setName("jLabelOclusi"); // NOI18N
        FormInput.add(jLabelOclusi);
        jLabelOclusi.setBounds(44, 630, 70, 23);

        TorusPalatinus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada", "Kecil", "Sedang", "Besar", "Multiple" }));
        TorusPalatinus.setName("TorusPalatinus"); // NOI18N
        TorusPalatinus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TorusPalatinusKeyPressed(evt);
            }
        });
        FormInput.add(TorusPalatinus);
        TorusPalatinus.setBounds(130, 660, 95, 23);

        jLabelTorusP.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelTorusP.setText("Torus Palatinus");
        jLabelTorusP.setName("jLabelTorusP"); // NOI18N
        FormInput.add(jLabelTorusP);
        jLabelTorusP.setBounds(44, 660, 146, 23);

        TorusMandibularis.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada", "Sisi Kanan", "Sisi Kiri", "Kedua Sisi" }));
        TorusMandibularis.setName("TorusMandibularis"); // NOI18N
        TorusMandibularis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TorusMandibularisKeyPressed(evt);
            }
        });
        FormInput.add(TorusMandibularis);
        TorusMandibularis.setBounds(147, 690, 95, 23);

        jLabelTorusM.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelTorusM.setText("Torus Mandibularis");
        jLabelTorusM.setName("jLabelTorusM"); // NOI18N
        FormInput.add(jLabelTorusM);
        jLabelTorusM.setBounds(44, 690, 146, 23);

        jLabelSupernumerary.setText("Supernumerary Teeth :");
        jLabelSupernumerary.setName("jLabelSupernumerary"); // NOI18N
        FormInput.add(jLabelSupernumerary);
        jLabelSupernumerary.setBounds(190, 630, 143, 23);

        Supernumerary.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada", "Ada" }));
        Supernumerary.setName("Supernumerary"); // NOI18N
        Supernumerary.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SupernumeraryKeyPressed(evt);
            }
        });
        FormInput.add(Supernumerary);
        Supernumerary.setBounds(337, 630, 95, 23);

        KetSupernumerary.setName("KetSupernumerary"); // NOI18N
        KetSupernumerary.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetSupernumeraryKeyPressed(evt);
            }
        });
        FormInput.add(KetSupernumerary);
        KetSupernumerary.setBounds(434, 630, 420, 23);

        jLabelGigiAnomali.setText("Gigi Anomali :");
        jLabelGigiAnomali.setName("jLabelGigiAnomali"); // NOI18N
        FormInput.add(jLabelGigiAnomali);
        jLabelGigiAnomali.setBounds(233, 660, 100, 23);

        GigiAnomali.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada", "Ada" }));
        GigiAnomali.setName("GigiAnomali"); // NOI18N
        GigiAnomali.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GigiAnomaliKeyPressed(evt);
            }
        });
        FormInput.add(GigiAnomali);
        GigiAnomali.setBounds(337, 660, 95, 23);

        KetGigiAnomali.setName("KetGigiAnomali"); // NOI18N
        KetGigiAnomali.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetGigiAnomaliKeyPressed(evt);
            }
        });
        FormInput.add(KetGigiAnomali);
        KetGigiAnomali.setBounds(434, 660, 420, 23);

        jLabelDiastema.setText("Diastema :");
        jLabelDiastema.setName("jLabelDiastema"); // NOI18N
        FormInput.add(jLabelDiastema);
        jLabelDiastema.setBounds(247, 690, 86, 23);

        Diastema.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada", "Ada" }));
        Diastema.setName("Diastema"); // NOI18N
        Diastema.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiastemaKeyPressed(evt);
            }
        });
        FormInput.add(Diastema);
        Diastema.setBounds(337, 690, 95, 23);

        KetDiastema.setName("KetDiastema"); // NOI18N
        KetDiastema.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetDiastemaKeyPressed(evt);
            }
        });
        FormInput.add(KetDiastema);
        KetDiastema.setBounds(434, 690, 420, 23);

        jLabelLainLain.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelLainLain.setText("Lain-lain");
        jLabelLainLain.setName("jLabelLainLain"); // NOI18N
        FormInput.add(jLabelLainLain);
        jLabelLainLain.setBounds(44, 720, 146, 23);

        scroll_LainLain.setName("scroll_LainLain"); // NOI18N

        LainLain.setColumns(20);
        LainLain.setRows(5);
        LainLain.setName("LainLain"); // NOI18N
        LainLain.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LainLainKeyPressed(evt);
            }
        });
        scroll_LainLain.setViewportView(LainLain);

        FormInput.add(scroll_LainLain);
        scroll_LainLain.setBounds(94, 720, 760, 43);

        jSeparator4.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator4.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator4.setName("jSeparator4"); // NOI18N
        FormInput.add(jSeparator4);
        jSeparator4.setBounds(0, 770, 880, 1);

        jLabelH4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelH4.setText("IV. PEMERIKSAAN/DATA PENUNJANG");
        jLabelH4.setName("jLabelH4"); // NOI18N
        FormInput.add(jLabelH4);
        jLabelH4.setBounds(10, 770, 300, 23);

        scroll_DataPenunjang.setName("scroll_DataPenunjang"); // NOI18N

        DataPenunjang.setColumns(20);
        DataPenunjang.setRows(5);
        DataPenunjang.setName("DataPenunjang"); // NOI18N
        DataPenunjang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DataPenunjangKeyPressed(evt);
            }
        });
        scroll_DataPenunjang.setViewportView(DataPenunjang);

        FormInput.add(scroll_DataPenunjang);
        scroll_DataPenunjang.setBounds(44, 790, 810, 43);

        jSeparator5.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator5.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator5.setName("jSeparator5"); // NOI18N
        FormInput.add(jSeparator5);
        jSeparator5.setBounds(0, 840, 880, 1);

        jLabelH5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelH5.setText("V. DIAGNOSIS/ASESMEN");
        jLabelH5.setName("jLabelH5"); // NOI18N
        FormInput.add(jLabelH5);
        jLabelH5.setBounds(10, 840, 300, 23);

        jLabelDiagnosa.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelDiagnosa.setText("Diagnosa");
        jLabelDiagnosa.setName("jLabelDiagnosa"); // NOI18N
        FormInput.add(jLabelDiagnosa);
        jLabelDiagnosa.setBounds(44, 860, 146, 23);

        scroll_Diagnosa.setName("scroll_Diagnosa"); // NOI18N

        Diagnosa.setColumns(20);
        Diagnosa.setRows(5);
        Diagnosa.setName("Diagnosa"); // NOI18N
        Diagnosa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosaKeyPressed(evt);
            }
        });
        scroll_Diagnosa.setViewportView(Diagnosa);

        FormInput.add(scroll_Diagnosa);
        scroll_Diagnosa.setBounds(100, 860, 754, 43);

        jLabelMasalahKeperawatan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelMasalahKeperawatan.setText("Masalah Keperawatan");
        jLabelMasalahKeperawatan.setName("jLabelMasalahKeperawatan"); // NOI18N
        FormInput.add(jLabelMasalahKeperawatan);
        jLabelMasalahKeperawatan.setBounds(44, 910, 146, 23);

        scroll_MasalahKeperawatan.setName("scroll_MasalahKeperawatan"); // NOI18N

        MasalahKeperawatan.setColumns(20);
        MasalahKeperawatan.setRows(5);
        MasalahKeperawatan.setName("MasalahKeperawatan"); // NOI18N
        MasalahKeperawatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MasalahKeperawatanKeyPressed(evt);
            }
        });
        scroll_MasalahKeperawatan.setViewportView(MasalahKeperawatan);

        FormInput.add(scroll_MasalahKeperawatan);
        scroll_MasalahKeperawatan.setBounds(162, 910, 692, 43);

        jSeparator6.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator6.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator6.setName("jSeparator6"); // NOI18N
        FormInput.add(jSeparator6);
        jSeparator6.setBounds(0, 960, 880, 1);

        jLabelH6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelH6.setText("VI. TATALAKSANA/TERAPI");
        jLabelH6.setName("jLabelH6"); // NOI18N
        FormInput.add(jLabelH6);
        jLabelH6.setBounds(10, 960, 300, 23);

        scroll_Terapi.setName("scroll_Terapi"); // NOI18N

        Terapi.setColumns(20);
        Terapi.setRows(5);
        Terapi.setName("Terapi"); // NOI18N
        Terapi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TerapiKeyPressed(evt);
            }
        });
        scroll_Terapi.setViewportView(Terapi);

        FormInput.add(scroll_Terapi);
        scroll_Terapi.setBounds(44, 980, 810, 43);

        jLabelRPS.setText("Riwayat Penyakit Sekarang :");
        jLabelRPS.setName("jLabelRPS"); // NOI18N
        FormInput.add(jLabelRPS);
        jLabelRPS.setBounds(440, 90, 150, 23);

        scroll_RPS.setName("scroll_RPS"); // NOI18N

        RPS.setColumns(20);
        RPS.setRows(5);
        RPS.setName("RPS"); // NOI18N
        RPS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RPSKeyPressed(evt);
            }
        });
        scroll_RPS.setViewportView(RPS);

        FormInput.add(scroll_RPS);
        scroll_RPS.setBounds(594, 90, 260, 43);

        jLabelRPD.setText("Riwayat Penyakit Dahulu :");
        jLabelRPD.setName("jLabelRPD"); // NOI18N
        FormInput.add(jLabelRPD);
        jLabelRPD.setBounds(440, 140, 150, 23);

        scroll_RPD.setName("scroll_RPD"); // NOI18N

        RPD.setColumns(20);
        RPD.setRows(5);
        RPD.setName("RPD"); // NOI18N
        RPD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RPDKeyPressed(evt);
            }
        });
        scroll_RPD.setViewportView(RPD);

        FormInput.add(scroll_RPD);
        scroll_RPD.setBounds(594, 140, 260, 43);

        scroll_RPO.setName("scroll_RPO"); // NOI18N

        RPO.setColumns(20);
        RPO.setRows(5);
        RPO.setName("RPO"); // NOI18N
        RPO.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RPOKeyPressed(evt);
            }
        });
        scroll_RPO.setViewportView(RPO);

        FormInput.add(scroll_RPO);
        scroll_RPO.setBounds(184, 190, 255, 43);

        jLabelAlergi1.setText("Riwayat Alergi :");
        jLabelAlergi1.setName("jLabelAlergi1"); // NOI18N
        FormInput.add(jLabelAlergi1);
        jLabelAlergi1.setBounds(440, 190, 150, 23);

        scroll_RiwayatKeluarga.setName("scroll_RiwayatKeluarga"); // NOI18N

        RiwayatKeluarga.setColumns(20);
        RiwayatKeluarga.setRows(5);
        RiwayatKeluarga.setName("RiwayatKeluarga"); // NOI18N
        RiwayatKeluarga.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RiwayatKeluargaKeyPressed(evt);
            }
        });
        scroll_RiwayatKeluarga.setViewportView(RiwayatKeluarga);

        FormInput.add(scroll_RiwayatKeluarga);
        scroll_RiwayatKeluarga.setBounds(184, 140, 255, 43);

        scroll_KeluhanUtama.setName("scroll_KeluhanUtama"); // NOI18N

        KeluhanUtama.setColumns(20);
        KeluhanUtama.setRows(5);
        KeluhanUtama.setName("KeluhanUtama"); // NOI18N
        KeluhanUtama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeluhanUtamaKeyPressed(evt);
            }
        });
        scroll_KeluhanUtama.setViewportView(KeluhanUtama);

        FormInput.add(scroll_KeluhanUtama);
        scroll_KeluhanUtama.setBounds(129, 90, 310, 43);

        jLabel33.setText(":");
        jLabel33.setName("jLabel33"); // NOI18N
        FormInput.add(jLabel33);
        jLabel33.setBounds(0, 90, 125, 23);

        jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel34.setText("Keluhan Utama");
        jLabel34.setName("jLabel34"); // NOI18N
        FormInput.add(jLabel34);
        jLabel34.setBounds(44, 90, 125, 23);

        jLabel35.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel35.setText("Riwayat Penyakit Keluarga");
        jLabel35.setName("jLabel35"); // NOI18N
        FormInput.add(jLabel35);
        jLabel35.setBounds(44, 140, 160, 23);

        jLabel36.setText(":");
        jLabel36.setName("jLabel36"); // NOI18N
        FormInput.add(jLabel36);
        jLabel36.setBounds(0, 140, 180, 23);

        jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel37.setText("Riwayat Penggunaan Obat");
        jLabel37.setName("jLabel37"); // NOI18N
        FormInput.add(jLabel37);
        jLabel37.setBounds(44, 190, 160, 23);

        jLabel38.setText(":");
        jLabel38.setName("jLabel38"); // NOI18N
        FormInput.add(jLabel38);
        jLabel38.setBounds(0, 190, 180, 23);

        jLabel39.setText(":");
        jLabel39.setName("jLabel39"); // NOI18N
        FormInput.add(jLabel39);
        jLabel39.setBounds(0, 450, 89, 23);

        jLabel40.setText(":");
        jLabel40.setName("jLabel40"); // NOI18N
        FormInput.add(jLabel40);
        jLabel40.setBounds(0, 630, 86, 23);

        jLabel41.setText(":");
        jLabel41.setName("jLabel41"); // NOI18N
        FormInput.add(jLabel41);
        jLabel41.setBounds(0, 660, 126, 23);

        jLabel42.setText(":");
        jLabel42.setName("jLabel42"); // NOI18N
        FormInput.add(jLabel42);
        jLabel42.setBounds(0, 690, 143, 23);

        jLabel43.setText(":");
        jLabel43.setName("jLabel43"); // NOI18N
        FormInput.add(jLabel43);
        jLabel43.setBounds(0, 720, 93, 23);

        jLabel44.setText(":");
        jLabel44.setName("jLabel44"); // NOI18N
        FormInput.add(jLabel44);
        jLabel44.setBounds(0, 860, 96, 23);

        jLabel45.setText(":");
        jLabel45.setName("jLabel45"); // NOI18N
        FormInput.add(jLabel45);
        jLabel45.setBounds(0, 910, 158, 23);

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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "18-11-2026" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "18-11-2026" }));
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

        jLabelPasien1.setText("Pasien :");
        jLabelPasien1.setName("jLabelPasien1"); // NOI18N
        jLabelPasien1.setPreferredSize(new java.awt.Dimension(55, 23));
        FormMenu.add(jLabelPasien1);

        TNoRM1.setEditable(false);
        TNoRM1.setHighlighter(null);
        TNoRM1.setName("TNoRM1"); // NOI18N
        TNoRM1.setPreferredSize(new java.awt.Dimension(100, 23));
        FormMenu.add(TNoRM1);

        TPasien1.setEditable(false);
        TPasien1.setBackground(new java.awt.Color(245, 250, 240));
        TPasien1.setHighlighter(null);
        TPasien1.setName("TPasien1"); // NOI18N
        TPasien1.setPreferredSize(new java.awt.Dimension(285, 23));
        FormMenu.add(TPasien1);

        PanelAccor.add(FormMenu, java.awt.BorderLayout.NORTH);

        FormOdontogram.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 254)));
        FormOdontogram.setName("FormOdontogram"); // NOI18N
        FormOdontogram.setLayout(new java.awt.BorderLayout(1, 1));

        scrollOdontogram1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 254)));
        scrollOdontogram1.setName("scrollOdontogram1"); // NOI18N
        scrollOdontogram1.setOpaque(true);
        scrollOdontogram1.setPreferredSize(new java.awt.Dimension(100, 206));

        PanelGigi1.setName("PanelGigi1"); // NOI18N
        scrollOdontogram1.setViewportView(PanelGigi1);

        FormOdontogram.add(scrollOdontogram1, java.awt.BorderLayout.NORTH);

        scrollTabelOdontogram1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 254)));
        scrollTabelOdontogram1.setName("scrollTabelOdontogram1"); // NOI18N
        scrollTabelOdontogram1.setOpaque(true);

        tbOdontogram1.setName("tbOdontogram1"); // NOI18N
        scrollTabelOdontogram1.setViewportView(tbOdontogram1);

        FormOdontogram.add(scrollTabelOdontogram1, java.awt.BorderLayout.CENTER);

        PanelAccor.add(FormOdontogram, java.awt.BorderLayout.CENTER);

        internalFrame3.add(PanelAccor, java.awt.BorderLayout.EAST);

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
        }else if(KeluhanUtama.getText().trim().equals("")){
            Valid.textKosong(KeluhanUtama,"Keluhan Utama");
        }else if(RPS.getText().trim().equals("")){
            Valid.textKosong(RPS,"Riwayat Penyakit Sekarang");
        }else if(RiwayatKeluarga.getText().trim().equals("")){
            Valid.textKosong(RiwayatKeluarga,"Riwayat Penyakit Keluarga");
        }else if(RPD.getText().trim().equals("")){
            Valid.textKosong(RPD,"Riwayat Penyakit Dahulu");
        }else if(RPO.getText().trim().equals("")){
            Valid.textKosong(RPO,"Riwayat Pengunaan obat");
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
            Valid.pindah(evt,Terapi,BtnBatal);
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
        }else if(KeluhanUtama.getText().trim().equals("")){
            Valid.textKosong(KeluhanUtama,"Keluhan Utama");
        }else if(RPS.getText().trim().equals("")){
            Valid.textKosong(RPS,"Riwayat Penyakit Sekarang");
        }else if(RiwayatKeluarga.getText().trim().equals("")){
            Valid.textKosong(RiwayatKeluarga,"Riwayat Penyakit Keluarga");
        }else if(RPD.getText().trim().equals("")){
            Valid.textKosong(RPD,"Riwayat Penyakit Dahulu");
        }else if(RPO.getText().trim().equals("")){
            Valid.textKosong(RPO,"Riwayat Pengunaan obat");
        }else{
            if(tbObat.getSelectedRow()>-1){
                if(akses.getkode().equals("Admin Utama")){
                    ganti();
                }else{
                    if(akses.getkode().equals(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString())){
                        if(Sequel.cekTanggal48jam(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString(),Sequel.ambiltanggalsekarang())==true){
                            if(TanggalRegistrasi.getText().equals("")){
                                TanggalRegistrasi.setText(Sequel.cariIsi("select concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) from reg_periksa where reg_periksa.no_rawat=?",TNoRw.getText()));
                            }
                            if(Sequel.cekTanggalRegistrasi(TanggalRegistrasi.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19))==true){
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
            try {
                htmlContent = new StringBuilder();
                htmlContent.append(
                    "<tr class='isi'>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='105px'><b>No.Rawat</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='70px'><b>No.RM</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='150px'><b>Nama Pasien</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='65px'><b>Tgl.Lahir</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='75px'><b>J.K.</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='80px'><b>Kode Dokter</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='150px'><b>Nama Dokter</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='115px'><b>Tanggal</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='90px'><b>Anamnesis</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='100px'><b>Hubungan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='200px'><b>Keluhan Utama</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='200px'><b>Riwayat Penyakit Sekarang</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='200px'><b>Riwayat Penyakit Dahulu</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='200px'><b>Riwayat Pengobatan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='150px'><b>Alergi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='200px'><b>Riwayat Penyakit Keluarga</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='90px'><b>Occlusi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='100px'><b>Torus Palatinus</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='110px'><b>Torus Mandibularis</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='150px'><b>Supernumerary Teeth</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='150px'><b>Gigi Anomali</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='150px'><b>Diastema</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='200px'><b>Lain - lain</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='200px'><b>Data Penunjang</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='200px'><b>Diagnosa</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='200px'><b>Masalah Keperawatan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='200px'><b>Terapi</b></td>"+
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
                        "</tr>");
                }
                LoadHTML.setText(
                    "<html>"+
                      "<table width='3900px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
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

                File f = new File("DataPengkajianAwalMedisRalanGigi.html");
                BufferedWriter bw = new BufferedWriter(new FileWriter(f));
                bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                            "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                            "<table width='3900px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                "<tr class='isi2'>"+
                                    "<td valign='top' align='center'>"+
                                        "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                        akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                        akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                        "<font size='2' face='Tahoma'>DATA PENGKAJIAN AWAL MEDIS RAWAT JALAN GIGI<br><br></font>"+
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

    private void MnPenilaianMedisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPenilaianMedisActionPerformed
        if(tbObat.getSelectedRow()>-1){
            Map<String, Object> param = new HashMap<>(); 
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());   
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
            param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbObat.getValueAt(tbObat.getSelectedRow(),6).toString()+"\nID "+(finger.equals("")?tbObat.getValueAt(tbObat.getSelectedRow(),5).toString():finger)+"\n"+Valid.SetTgl3(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString()));
            Valid.MyReportqry("rptCetakPengkajianAwalMedisGigiMulut.jasper","report","::[ Laporan Pengkajian Awal Medis Rawat Jalan Gigi dan Mulut ]::",
                "select penilaian_awal_medis_gigi_mulut.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,pasien.jk,penilaian_awal_medis_gigi_mulut.tanggal,penilaian_awal_medis_gigi_mulut.kd_dokter,dokter.nm_dokter,penilaian_awal_medis_gigi_mulut.anamnesis,penilaian_awal_medis_gigi_mulut.hubungan,penilaian_awal_medis_gigi_mulut.keluhan_utama,penilaian_awal_medis_gigi_mulut.rps,"+
                "penilaian_awal_medis_gigi_mulut.rpd,penilaian_awal_medis_gigi_mulut.rpo,penilaian_awal_medis_gigi_mulut.alergi,penilaian_awal_medis_gigi_mulut.riwayat_keluarga,penilaian_awal_medis_gigi_mulut.occlusi,penilaian_awal_medis_gigi_mulut.torus_palatinus,penilaian_awal_medis_gigi_mulut.torus_mandibularis,penilaian_awal_medis_gigi_mulut.supernumerary,penilaian_awal_medis_gigi_mulut.ket_supernumerary,"+
                "penilaian_awal_medis_gigi_mulut.gigi_anomali,penilaian_awal_medis_gigi_mulut.ket_gigi_anomali,penilaian_awal_medis_gigi_mulut.diastema,penilaian_awal_medis_gigi_mulut.ket_diastema,penilaian_awal_medis_gigi_mulut.lain_lain,penilaian_awal_medis_gigi_mulut.data_penunjang,penilaian_awal_medis_gigi_mulut.diagnosa,penilaian_awal_medis_gigi_mulut.masalah_keperawatan,penilaian_awal_medis_gigi_mulut.terapi,"+
                "(select left(penilaian_awal_medis_gigi_odontogram.kondisi,3) from penilaian_awal_medis_gigi_odontogram where penilaian_awal_medis_gigi_odontogram.no_rawat=penilaian_awal_medis_gigi_mulut.no_rawat and penilaian_awal_medis_gigi_odontogram.no_gigi='11') as g11,"+
                "(select left(penilaian_awal_medis_gigi_odontogram.kondisi,3) from penilaian_awal_medis_gigi_odontogram where penilaian_awal_medis_gigi_odontogram.no_rawat=penilaian_awal_medis_gigi_mulut.no_rawat and penilaian_awal_medis_gigi_odontogram.no_gigi='12') as g12,"+
                "(select left(penilaian_awal_medis_gigi_odontogram.kondisi,3) from penilaian_awal_medis_gigi_odontogram where penilaian_awal_medis_gigi_odontogram.no_rawat=penilaian_awal_medis_gigi_mulut.no_rawat and penilaian_awal_medis_gigi_odontogram.no_gigi='13') as g13,"+
                "(select left(penilaian_awal_medis_gigi_odontogram.kondisi,3) from penilaian_awal_medis_gigi_odontogram where penilaian_awal_medis_gigi_odontogram.no_rawat=penilaian_awal_medis_gigi_mulut.no_rawat and penilaian_awal_medis_gigi_odontogram.no_gigi='14') as g14,"+
                "(select left(penilaian_awal_medis_gigi_odontogram.kondisi,3) from penilaian_awal_medis_gigi_odontogram where penilaian_awal_medis_gigi_odontogram.no_rawat=penilaian_awal_medis_gigi_mulut.no_rawat and penilaian_awal_medis_gigi_odontogram.no_gigi='15') as g15,"+
                "(select left(penilaian_awal_medis_gigi_odontogram.kondisi,3) from penilaian_awal_medis_gigi_odontogram where penilaian_awal_medis_gigi_odontogram.no_rawat=penilaian_awal_medis_gigi_mulut.no_rawat and penilaian_awal_medis_gigi_odontogram.no_gigi='16') as g16,"+
                "(select left(penilaian_awal_medis_gigi_odontogram.kondisi,3) from penilaian_awal_medis_gigi_odontogram where penilaian_awal_medis_gigi_odontogram.no_rawat=penilaian_awal_medis_gigi_mulut.no_rawat and penilaian_awal_medis_gigi_odontogram.no_gigi='17') as g17,"+
                "(select left(penilaian_awal_medis_gigi_odontogram.kondisi,3) from penilaian_awal_medis_gigi_odontogram where penilaian_awal_medis_gigi_odontogram.no_rawat=penilaian_awal_medis_gigi_mulut.no_rawat and penilaian_awal_medis_gigi_odontogram.no_gigi='18') as g18,"+
                "(select left(penilaian_awal_medis_gigi_odontogram.kondisi,3) from penilaian_awal_medis_gigi_odontogram where penilaian_awal_medis_gigi_odontogram.no_rawat=penilaian_awal_medis_gigi_mulut.no_rawat and penilaian_awal_medis_gigi_odontogram.no_gigi='21') as g21,"+
                "(select left(penilaian_awal_medis_gigi_odontogram.kondisi,3) from penilaian_awal_medis_gigi_odontogram where penilaian_awal_medis_gigi_odontogram.no_rawat=penilaian_awal_medis_gigi_mulut.no_rawat and penilaian_awal_medis_gigi_odontogram.no_gigi='22') as g22,"+
                "(select left(penilaian_awal_medis_gigi_odontogram.kondisi,3) from penilaian_awal_medis_gigi_odontogram where penilaian_awal_medis_gigi_odontogram.no_rawat=penilaian_awal_medis_gigi_mulut.no_rawat and penilaian_awal_medis_gigi_odontogram.no_gigi='23') as g23,"+
                "(select left(penilaian_awal_medis_gigi_odontogram.kondisi,3) from penilaian_awal_medis_gigi_odontogram where penilaian_awal_medis_gigi_odontogram.no_rawat=penilaian_awal_medis_gigi_mulut.no_rawat and penilaian_awal_medis_gigi_odontogram.no_gigi='24') as g24,"+
                "(select left(penilaian_awal_medis_gigi_odontogram.kondisi,3) from penilaian_awal_medis_gigi_odontogram where penilaian_awal_medis_gigi_odontogram.no_rawat=penilaian_awal_medis_gigi_mulut.no_rawat and penilaian_awal_medis_gigi_odontogram.no_gigi='25') as g25,"+
                "(select left(penilaian_awal_medis_gigi_odontogram.kondisi,3) from penilaian_awal_medis_gigi_odontogram where penilaian_awal_medis_gigi_odontogram.no_rawat=penilaian_awal_medis_gigi_mulut.no_rawat and penilaian_awal_medis_gigi_odontogram.no_gigi='26') as g26,"+
                "(select left(penilaian_awal_medis_gigi_odontogram.kondisi,3) from penilaian_awal_medis_gigi_odontogram where penilaian_awal_medis_gigi_odontogram.no_rawat=penilaian_awal_medis_gigi_mulut.no_rawat and penilaian_awal_medis_gigi_odontogram.no_gigi='27') as g27,"+
                "(select left(penilaian_awal_medis_gigi_odontogram.kondisi,3) from penilaian_awal_medis_gigi_odontogram where penilaian_awal_medis_gigi_odontogram.no_rawat=penilaian_awal_medis_gigi_mulut.no_rawat and penilaian_awal_medis_gigi_odontogram.no_gigi='28') as g28,"+
                "(select left(penilaian_awal_medis_gigi_odontogram.kondisi,3) from penilaian_awal_medis_gigi_odontogram where penilaian_awal_medis_gigi_odontogram.no_rawat=penilaian_awal_medis_gigi_mulut.no_rawat and penilaian_awal_medis_gigi_odontogram.no_gigi='31') as g31,"+
                "(select left(penilaian_awal_medis_gigi_odontogram.kondisi,3) from penilaian_awal_medis_gigi_odontogram where penilaian_awal_medis_gigi_odontogram.no_rawat=penilaian_awal_medis_gigi_mulut.no_rawat and penilaian_awal_medis_gigi_odontogram.no_gigi='32') as g32,"+
                "(select left(penilaian_awal_medis_gigi_odontogram.kondisi,3) from penilaian_awal_medis_gigi_odontogram where penilaian_awal_medis_gigi_odontogram.no_rawat=penilaian_awal_medis_gigi_mulut.no_rawat and penilaian_awal_medis_gigi_odontogram.no_gigi='33') as g33,"+
                "(select left(penilaian_awal_medis_gigi_odontogram.kondisi,3) from penilaian_awal_medis_gigi_odontogram where penilaian_awal_medis_gigi_odontogram.no_rawat=penilaian_awal_medis_gigi_mulut.no_rawat and penilaian_awal_medis_gigi_odontogram.no_gigi='34') as g34,"+
                "(select left(penilaian_awal_medis_gigi_odontogram.kondisi,3) from penilaian_awal_medis_gigi_odontogram where penilaian_awal_medis_gigi_odontogram.no_rawat=penilaian_awal_medis_gigi_mulut.no_rawat and penilaian_awal_medis_gigi_odontogram.no_gigi='35') as g35,"+
                "(select left(penilaian_awal_medis_gigi_odontogram.kondisi,3) from penilaian_awal_medis_gigi_odontogram where penilaian_awal_medis_gigi_odontogram.no_rawat=penilaian_awal_medis_gigi_mulut.no_rawat and penilaian_awal_medis_gigi_odontogram.no_gigi='36') as g36,"+
                "(select left(penilaian_awal_medis_gigi_odontogram.kondisi,3) from penilaian_awal_medis_gigi_odontogram where penilaian_awal_medis_gigi_odontogram.no_rawat=penilaian_awal_medis_gigi_mulut.no_rawat and penilaian_awal_medis_gigi_odontogram.no_gigi='37') as g37,"+
                "(select left(penilaian_awal_medis_gigi_odontogram.kondisi,3) from penilaian_awal_medis_gigi_odontogram where penilaian_awal_medis_gigi_odontogram.no_rawat=penilaian_awal_medis_gigi_mulut.no_rawat and penilaian_awal_medis_gigi_odontogram.no_gigi='38') as g38,"+
                "(select left(penilaian_awal_medis_gigi_odontogram.kondisi,3) from penilaian_awal_medis_gigi_odontogram where penilaian_awal_medis_gigi_odontogram.no_rawat=penilaian_awal_medis_gigi_mulut.no_rawat and penilaian_awal_medis_gigi_odontogram.no_gigi='41') as g41,"+
                "(select left(penilaian_awal_medis_gigi_odontogram.kondisi,3) from penilaian_awal_medis_gigi_odontogram where penilaian_awal_medis_gigi_odontogram.no_rawat=penilaian_awal_medis_gigi_mulut.no_rawat and penilaian_awal_medis_gigi_odontogram.no_gigi='42') as g42,"+
                "(select left(penilaian_awal_medis_gigi_odontogram.kondisi,3) from penilaian_awal_medis_gigi_odontogram where penilaian_awal_medis_gigi_odontogram.no_rawat=penilaian_awal_medis_gigi_mulut.no_rawat and penilaian_awal_medis_gigi_odontogram.no_gigi='43') as g43,"+
                "(select left(penilaian_awal_medis_gigi_odontogram.kondisi,3) from penilaian_awal_medis_gigi_odontogram where penilaian_awal_medis_gigi_odontogram.no_rawat=penilaian_awal_medis_gigi_mulut.no_rawat and penilaian_awal_medis_gigi_odontogram.no_gigi='44') as g44,"+
                "(select left(penilaian_awal_medis_gigi_odontogram.kondisi,3) from penilaian_awal_medis_gigi_odontogram where penilaian_awal_medis_gigi_odontogram.no_rawat=penilaian_awal_medis_gigi_mulut.no_rawat and penilaian_awal_medis_gigi_odontogram.no_gigi='45') as g45,"+
                "(select left(penilaian_awal_medis_gigi_odontogram.kondisi,3) from penilaian_awal_medis_gigi_odontogram where penilaian_awal_medis_gigi_odontogram.no_rawat=penilaian_awal_medis_gigi_mulut.no_rawat and penilaian_awal_medis_gigi_odontogram.no_gigi='46') as g46,"+
                "(select left(penilaian_awal_medis_gigi_odontogram.kondisi,3) from penilaian_awal_medis_gigi_odontogram where penilaian_awal_medis_gigi_odontogram.no_rawat=penilaian_awal_medis_gigi_mulut.no_rawat and penilaian_awal_medis_gigi_odontogram.no_gigi='47') as g47,"+
                "(select left(penilaian_awal_medis_gigi_odontogram.kondisi,3) from penilaian_awal_medis_gigi_odontogram where penilaian_awal_medis_gigi_odontogram.no_rawat=penilaian_awal_medis_gigi_mulut.no_rawat and penilaian_awal_medis_gigi_odontogram.no_gigi='48') as g48,"+
                "(select left(penilaian_awal_medis_gigi_odontogram.kondisi,3) from penilaian_awal_medis_gigi_odontogram where penilaian_awal_medis_gigi_odontogram.no_rawat=penilaian_awal_medis_gigi_mulut.no_rawat and penilaian_awal_medis_gigi_odontogram.no_gigi='51') as g51,"+
                "(select left(penilaian_awal_medis_gigi_odontogram.kondisi,3) from penilaian_awal_medis_gigi_odontogram where penilaian_awal_medis_gigi_odontogram.no_rawat=penilaian_awal_medis_gigi_mulut.no_rawat and penilaian_awal_medis_gigi_odontogram.no_gigi='52') as g52,"+
                "(select left(penilaian_awal_medis_gigi_odontogram.kondisi,3) from penilaian_awal_medis_gigi_odontogram where penilaian_awal_medis_gigi_odontogram.no_rawat=penilaian_awal_medis_gigi_mulut.no_rawat and penilaian_awal_medis_gigi_odontogram.no_gigi='53') as g53,"+
                "(select left(penilaian_awal_medis_gigi_odontogram.kondisi,3) from penilaian_awal_medis_gigi_odontogram where penilaian_awal_medis_gigi_odontogram.no_rawat=penilaian_awal_medis_gigi_mulut.no_rawat and penilaian_awal_medis_gigi_odontogram.no_gigi='54') as g54,"+
                "(select left(penilaian_awal_medis_gigi_odontogram.kondisi,3) from penilaian_awal_medis_gigi_odontogram where penilaian_awal_medis_gigi_odontogram.no_rawat=penilaian_awal_medis_gigi_mulut.no_rawat and penilaian_awal_medis_gigi_odontogram.no_gigi='55') as g55,"+
                "(select left(penilaian_awal_medis_gigi_odontogram.kondisi,3) from penilaian_awal_medis_gigi_odontogram where penilaian_awal_medis_gigi_odontogram.no_rawat=penilaian_awal_medis_gigi_mulut.no_rawat and penilaian_awal_medis_gigi_odontogram.no_gigi='61') as g61,"+
                "(select left(penilaian_awal_medis_gigi_odontogram.kondisi,3) from penilaian_awal_medis_gigi_odontogram where penilaian_awal_medis_gigi_odontogram.no_rawat=penilaian_awal_medis_gigi_mulut.no_rawat and penilaian_awal_medis_gigi_odontogram.no_gigi='62') as g62,"+
                "(select left(penilaian_awal_medis_gigi_odontogram.kondisi,3) from penilaian_awal_medis_gigi_odontogram where penilaian_awal_medis_gigi_odontogram.no_rawat=penilaian_awal_medis_gigi_mulut.no_rawat and penilaian_awal_medis_gigi_odontogram.no_gigi='63') as g63,"+
                "(select left(penilaian_awal_medis_gigi_odontogram.kondisi,3) from penilaian_awal_medis_gigi_odontogram where penilaian_awal_medis_gigi_odontogram.no_rawat=penilaian_awal_medis_gigi_mulut.no_rawat and penilaian_awal_medis_gigi_odontogram.no_gigi='64') as g64,"+
                "(select left(penilaian_awal_medis_gigi_odontogram.kondisi,3) from penilaian_awal_medis_gigi_odontogram where penilaian_awal_medis_gigi_odontogram.no_rawat=penilaian_awal_medis_gigi_mulut.no_rawat and penilaian_awal_medis_gigi_odontogram.no_gigi='65') as g65,"+
                "(select left(penilaian_awal_medis_gigi_odontogram.kondisi,3) from penilaian_awal_medis_gigi_odontogram where penilaian_awal_medis_gigi_odontogram.no_rawat=penilaian_awal_medis_gigi_mulut.no_rawat and penilaian_awal_medis_gigi_odontogram.no_gigi='71') as g71,"+
                "(select left(penilaian_awal_medis_gigi_odontogram.kondisi,3) from penilaian_awal_medis_gigi_odontogram where penilaian_awal_medis_gigi_odontogram.no_rawat=penilaian_awal_medis_gigi_mulut.no_rawat and penilaian_awal_medis_gigi_odontogram.no_gigi='72') as g72,"+
                "(select left(penilaian_awal_medis_gigi_odontogram.kondisi,3) from penilaian_awal_medis_gigi_odontogram where penilaian_awal_medis_gigi_odontogram.no_rawat=penilaian_awal_medis_gigi_mulut.no_rawat and penilaian_awal_medis_gigi_odontogram.no_gigi='73') as g73,"+
                "(select left(penilaian_awal_medis_gigi_odontogram.kondisi,3) from penilaian_awal_medis_gigi_odontogram where penilaian_awal_medis_gigi_odontogram.no_rawat=penilaian_awal_medis_gigi_mulut.no_rawat and penilaian_awal_medis_gigi_odontogram.no_gigi='74') as g74,"+
                "(select left(penilaian_awal_medis_gigi_odontogram.kondisi,3) from penilaian_awal_medis_gigi_odontogram where penilaian_awal_medis_gigi_odontogram.no_rawat=penilaian_awal_medis_gigi_mulut.no_rawat and penilaian_awal_medis_gigi_odontogram.no_gigi='75') as g75,"+
                "(select left(penilaian_awal_medis_gigi_odontogram.kondisi,3) from penilaian_awal_medis_gigi_odontogram where penilaian_awal_medis_gigi_odontogram.no_rawat=penilaian_awal_medis_gigi_mulut.no_rawat and penilaian_awal_medis_gigi_odontogram.no_gigi='81') as g81,"+
                "(select left(penilaian_awal_medis_gigi_odontogram.kondisi,3) from penilaian_awal_medis_gigi_odontogram where penilaian_awal_medis_gigi_odontogram.no_rawat=penilaian_awal_medis_gigi_mulut.no_rawat and penilaian_awal_medis_gigi_odontogram.no_gigi='82') as g82,"+
                "(select left(penilaian_awal_medis_gigi_odontogram.kondisi,3) from penilaian_awal_medis_gigi_odontogram where penilaian_awal_medis_gigi_odontogram.no_rawat=penilaian_awal_medis_gigi_mulut.no_rawat and penilaian_awal_medis_gigi_odontogram.no_gigi='83') as g83,"+
                "(select left(penilaian_awal_medis_gigi_odontogram.kondisi,3) from penilaian_awal_medis_gigi_odontogram where penilaian_awal_medis_gigi_odontogram.no_rawat=penilaian_awal_medis_gigi_mulut.no_rawat and penilaian_awal_medis_gigi_odontogram.no_gigi='84') as g84,"+
                "(select left(penilaian_awal_medis_gigi_odontogram.kondisi,3) from penilaian_awal_medis_gigi_odontogram where penilaian_awal_medis_gigi_odontogram.no_rawat=penilaian_awal_medis_gigi_mulut.no_rawat and penilaian_awal_medis_gigi_odontogram.no_gigi='85') as g85,"+
                "(select group_concat(concat(penilaian_awal_medis_gigi_odontogram.no_gigi,' : ',penilaian_awal_medis_gigi_odontogram.keterangan) order by penilaian_awal_medis_gigi_odontogram.no_gigi separator '; ') from penilaian_awal_medis_gigi_odontogram where penilaian_awal_medis_gigi_odontogram.no_rawat=penilaian_awal_medis_gigi_mulut.no_rawat and penilaian_awal_medis_gigi_odontogram.keterangan<>'') as ket_gigi "+
                "from penilaian_awal_medis_gigi_mulut inner join reg_periksa on penilaian_awal_medis_gigi_mulut.no_rawat=reg_periksa.no_rawat inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join dokter on penilaian_awal_medis_gigi_mulut.kd_dokter=dokter.kd_dokter where penilaian_awal_medis_gigi_mulut.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'",param
            );
        }
    }//GEN-LAST:event_MnPenilaianMedisActionPerformed

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
                getOdontogram();
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
                    ChkAccor.setSelected(true);
                    isMenu();
                    getOdontogram();
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

    private void ChkAccorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkAccorActionPerformed
        if(tbObat.getSelectedRow()!= -1){
            isMenu();
        }else{
            ChkAccor.setSelected(false);
            JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data yang mau ditampilkan...!!!!");
        }
    }//GEN-LAST:event_ChkAccorActionPerformed

    private void BtnDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokterKeyPressed
        Valid.pindah(evt,TCari,Anamnesis);
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
        if (!dokter.isVisible()) {
            dokter.isCek();    
            dokter.emptTeks();
        }
        
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
            Valid.pindah(evt,TCari,Anamnesis);
        }
    }//GEN-LAST:event_TNoRwKeyPressed

    private void AnamnesisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AnamnesisKeyPressed
        Valid.pindah(evt,Tanggal,Hubungan);
    }//GEN-LAST:event_AnamnesisKeyPressed

    private void HubunganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HubunganKeyPressed
        Valid.pindah(evt,Anamnesis,KeluhanUtama);
    }//GEN-LAST:event_HubunganKeyPressed

    private void NoGigiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoGigiKeyPressed
        Valid.pindah(evt,RiwayatKeluarga,Kondisi);
    }//GEN-LAST:event_NoGigiKeyPressed

    private void KondisiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KondisiKeyPressed
        Valid.pindah(evt,NoGigi,KetGigi);
    }//GEN-LAST:event_KondisiKeyPressed

    private void KetGigiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetGigiKeyPressed
        Valid.pindah(evt,Kondisi,BtnTambahGigi);
    }//GEN-LAST:event_KetGigiKeyPressed

    private void OcclusiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_OcclusiKeyPressed
        Valid.pindah(evt,BtnHapusGigi,TorusPalatinus);
    }//GEN-LAST:event_OcclusiKeyPressed

    private void TorusPalatinusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TorusPalatinusKeyPressed
        Valid.pindah(evt,Occlusi,TorusMandibularis);
    }//GEN-LAST:event_TorusPalatinusKeyPressed

    private void TorusMandibularisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TorusMandibularisKeyPressed
        Valid.pindah(evt,TorusPalatinus,Supernumerary);
    }//GEN-LAST:event_TorusMandibularisKeyPressed

    private void SupernumeraryKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SupernumeraryKeyPressed
        Valid.pindah(evt,TorusMandibularis,KetSupernumerary);
    }//GEN-LAST:event_SupernumeraryKeyPressed

    private void KetSupernumeraryKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetSupernumeraryKeyPressed
        Valid.pindah(evt,Supernumerary,GigiAnomali);
    }//GEN-LAST:event_KetSupernumeraryKeyPressed

    private void GigiAnomaliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GigiAnomaliKeyPressed
        Valid.pindah(evt,KetSupernumerary,KetGigiAnomali);
    }//GEN-LAST:event_GigiAnomaliKeyPressed

    private void KetGigiAnomaliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetGigiAnomaliKeyPressed
        Valid.pindah(evt,GigiAnomali,Diastema);
    }//GEN-LAST:event_KetGigiAnomaliKeyPressed

    private void DiastemaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiastemaKeyPressed
        Valid.pindah(evt,KetGigiAnomali,KetDiastema);
    }//GEN-LAST:event_DiastemaKeyPressed

    private void KetDiastemaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetDiastemaKeyPressed
        Valid.pindah(evt,Diastema,LainLain);
    }//GEN-LAST:event_KetDiastemaKeyPressed

    private void LainLainKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LainLainKeyPressed
        Valid.pindah2(evt,KetDiastema,DataPenunjang);
    }//GEN-LAST:event_LainLainKeyPressed

    private void DataPenunjangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DataPenunjangKeyPressed
        Valid.pindah2(evt,LainLain,Diagnosa);
    }//GEN-LAST:event_DataPenunjangKeyPressed

    private void DiagnosaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosaKeyPressed
        Valid.pindah2(evt,DataPenunjang,MasalahKeperawatan);
    }//GEN-LAST:event_DiagnosaKeyPressed

    private void MasalahKeperawatanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MasalahKeperawatanKeyPressed
        Valid.pindah2(evt,Diagnosa,Terapi);
    }//GEN-LAST:event_MasalahKeperawatanKeyPressed

    private void TerapiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TerapiKeyPressed
        Valid.pindah2(evt,MasalahKeperawatan,BtnSimpan);
    }//GEN-LAST:event_TerapiKeyPressed

    private void BtnTambahGigiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahGigiActionPerformed
        boolean ada=false;
        for(i=0;i<tabModeGigi.getRowCount();i++){
            if(tabModeGigi.getValueAt(i,0).toString().equals(NoGigi.getSelectedItem().toString())){
                tabModeGigi.setValueAt(Kondisi.getSelectedItem().toString(),i,1);
                tabModeGigi.setValueAt(KetGigi.getText(),i,2);
                ada=true;
            }
        }
        if(ada==false){
            tabModeGigi.addRow(new Object[]{NoGigi.getSelectedItem().toString(),Kondisi.getSelectedItem().toString(),KetGigi.getText()});
        }
        KetGigi.setText("");
        NoGigi.requestFocus();
    }//GEN-LAST:event_BtnTambahGigiActionPerformed

    private void BtnHapusGigiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusGigiActionPerformed
        if(tbOdontogram.getSelectedRow()>-1){
            tabModeGigi.removeRow(tbOdontogram.getSelectedRow());
        }else{
            JOptionPane.showMessageDialog(rootPane,"Silahkan pilih gigi yang mau dihapus pada tabel odontogram..!!");
        }
    }//GEN-LAST:event_BtnHapusGigiActionPerformed

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

    private void AlergiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlergiKeyPressed
        Valid.pindah(evt,RPO,NoGigi);
    }//GEN-LAST:event_AlergiKeyPressed

    private void RPSKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RPSKeyPressed
        Valid.pindah2(evt,KeluhanUtama,RiwayatKeluarga);
    }//GEN-LAST:event_RPSKeyPressed

    private void RPDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RPDKeyPressed
        Valid.pindah2(evt,RiwayatKeluarga,RPO);
    }//GEN-LAST:event_RPDKeyPressed

    private void RPOKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RPOKeyPressed
        Valid.pindah2(evt,RPD,Alergi);
    }//GEN-LAST:event_RPOKeyPressed

    private void RiwayatKeluargaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RiwayatKeluargaKeyPressed
        Valid.pindah2(evt,RPS,RPD);
    }//GEN-LAST:event_RiwayatKeluargaKeyPressed

    private void KeluhanUtamaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeluhanUtamaKeyPressed
        Valid.pindah2(evt,Hubungan,RPS);
    }//GEN-LAST:event_KeluhanUtamaKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMPenilaianAwalMedisRalanGigi dialog = new RMPenilaianAwalMedisRalanGigi(new javax.swing.JFrame(), true);
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
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnDokter;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnHapusGigi;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.Button BtnTambahGigi;
    private widget.CekBox ChkAccor;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.TextArea DataPenunjang;
    private widget.TextArea Diagnosa;
    private widget.ComboBox Diastema;
    private widget.PanelBiasa FormInput;
    private widget.PanelBiasa FormMenu;
    private widget.PanelBiasa FormOdontogram;
    private widget.ComboBox GigiAnomali;
    private widget.TextBox Hubungan;
    private widget.TextBox Jk;
    private widget.TextBox KdDokter;
    private widget.TextArea KeluhanUtama;
    private widget.TextBox KetDiastema;
    private widget.TextBox KetGigi;
    private widget.TextBox KetGigiAnomali;
    private widget.TextBox KetSupernumerary;
    private widget.ComboBox Kondisi;
    private widget.Label LCount;
    private widget.TextArea LainLain;
    private widget.editorpane LoadHTML;
    private widget.TextArea MasalahKeperawatan;
    private widget.MenuItem MnPenilaianMedis;
    private widget.TextBox NmDokter;
    private widget.ComboBox NoGigi;
    private widget.ComboBox Occlusi;
    private widget.PanelBiasa PanelAccor;
    private rekammedis.PanelOdontogram PanelGigi;
    private rekammedis.PanelOdontogram PanelGigi1;
    private widget.TextArea RPD;
    private widget.TextArea RPO;
    private widget.TextArea RPS;
    private widget.TextArea RiwayatKeluarga;
    private widget.ScrollPane Scroll;
    private widget.ComboBox Supernumerary;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRM1;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.TextBox TPasien1;
    private widget.TabPane TabRawat;
    private widget.Tanggal Tanggal;
    private widget.TextBox TanggalRegistrasi;
    private widget.TextArea Terapi;
    private widget.TextBox TglLahir;
    private widget.ComboBox TorusMandibularis;
    private widget.ComboBox TorusPalatinus;
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
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabelAlergi1;
    private widget.Label jLabelAnamnesis;
    private widget.Label jLabelDiagnosa;
    private widget.Label jLabelDiastema;
    private widget.Label jLabelDokter;
    private widget.Label jLabelGigi;
    private widget.Label jLabelGigiAnomali;
    private widget.Label jLabelH1;
    private widget.Label jLabelH2;
    private widget.Label jLabelH3;
    private widget.Label jLabelH4;
    private widget.Label jLabelH5;
    private widget.Label jLabelH6;
    private widget.Label jLabelJk;
    private widget.Label jLabelKetGigi;
    private widget.Label jLabelKondisi;
    private widget.Label jLabelLainLain;
    private widget.Label jLabelMasalahKeperawatan;
    private widget.Label jLabelOclusi;
    private widget.Label jLabelPasien1;
    private widget.Label jLabelRPD;
    private widget.Label jLabelRPS;
    private widget.Label jLabelRawat;
    private widget.Label jLabelSupernumerary;
    private widget.Label jLabelTgl;
    private widget.Label jLabelTglLahir;
    private widget.Label jLabelTorusM;
    private widget.Label jLabelTorusP;
    private widget.PopupMenu jPopupMenu1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollInput;
    private widget.ScrollPane scrollOdontogram;
    private widget.ScrollPane scrollOdontogram1;
    private widget.ScrollPane scrollTabelOdontogram1;
    private widget.ScrollPane scroll_DataPenunjang;
    private widget.ScrollPane scroll_Diagnosa;
    private widget.ScrollPane scroll_KeluhanUtama;
    private widget.ScrollPane scroll_LainLain;
    private widget.ScrollPane scroll_MasalahKeperawatan;
    private widget.ScrollPane scroll_RPD;
    private widget.ScrollPane scroll_RPO;
    private widget.ScrollPane scroll_RPS;
    private widget.ScrollPane scroll_RiwayatKeluarga;
    private widget.ScrollPane scroll_Terapi;
    private widget.Table tbObat;
    private widget.Table tbOdontogram;
    private widget.Table tbOdontogram1;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            ps=koneksi.prepareStatement(
                "select penilaian_awal_medis_gigi_mulut.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,"+
                "penilaian_awal_medis_gigi_mulut.kd_dokter,dokter.nm_dokter,penilaian_awal_medis_gigi_mulut.tanggal,penilaian_awal_medis_gigi_mulut.anamnesis,penilaian_awal_medis_gigi_mulut.hubungan,"+
                "penilaian_awal_medis_gigi_mulut.keluhan_utama,penilaian_awal_medis_gigi_mulut.rps,penilaian_awal_medis_gigi_mulut.rpd,penilaian_awal_medis_gigi_mulut.rpo,"+
                "penilaian_awal_medis_gigi_mulut.alergi,penilaian_awal_medis_gigi_mulut.riwayat_keluarga,penilaian_awal_medis_gigi_mulut.occlusi,penilaian_awal_medis_gigi_mulut.torus_palatinus,"+
                "penilaian_awal_medis_gigi_mulut.torus_mandibularis,if(penilaian_awal_medis_gigi_mulut.supernumerary='Ada',concat('Ada : ',penilaian_awal_medis_gigi_mulut.ket_supernumerary),'Tidak Ada') as supernumerary,"+
                "if(penilaian_awal_medis_gigi_mulut.gigi_anomali='Ada',concat('Ada : ',penilaian_awal_medis_gigi_mulut.ket_gigi_anomali),'Tidak Ada') as gigi_anomali,"+
                "if(penilaian_awal_medis_gigi_mulut.diastema='Ada',concat('Ada : ',penilaian_awal_medis_gigi_mulut.ket_diastema),'Tidak Ada') as diastema,penilaian_awal_medis_gigi_mulut.lain_lain,"+
                "penilaian_awal_medis_gigi_mulut.data_penunjang,penilaian_awal_medis_gigi_mulut.diagnosa,penilaian_awal_medis_gigi_mulut.masalah_keperawatan,penilaian_awal_medis_gigi_mulut.terapi "+
                "from penilaian_awal_medis_gigi_mulut inner join reg_periksa on penilaian_awal_medis_gigi_mulut.no_rawat=reg_periksa.no_rawat inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join dokter on penilaian_awal_medis_gigi_mulut.kd_dokter=dokter.kd_dokter where penilaian_awal_medis_gigi_mulut.tanggal between ? and ? "+
                (TCari.getText().trim().equals("")?"":"and (penilaian_awal_medis_gigi_mulut.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or "+
                "penilaian_awal_medis_gigi_mulut.kd_dokter like ? or dokter.nm_dokter like ? or penilaian_awal_medis_gigi_mulut.keluhan_utama like ? or penilaian_awal_medis_gigi_mulut.diagnosa like ?) ")+
                "order by penilaian_awal_medis_gigi_mulut.tanggal"
            );
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
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("tgl_lahir"),rs.getString("jk"),rs.getString("kd_dokter"),rs.getString("nm_dokter"),rs.getString("tanggal"),
                        rs.getString("anamnesis"),rs.getString("hubungan"),rs.getString("keluhan_utama"),rs.getString("rps"),rs.getString("rpd"),rs.getString("rpo"),rs.getString("alergi"),rs.getString("riwayat_keluarga"),
                        rs.getString("occlusi"),rs.getString("torus_palatinus"),rs.getString("torus_mandibularis"),rs.getString("supernumerary"),rs.getString("gigi_anomali"),rs.getString("diastema"),rs.getString("lain_lain"),
                        rs.getString("data_penunjang"),rs.getString("diagnosa"),rs.getString("masalah_keperawatan"),rs.getString("terapi")
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
        Anamnesis.setSelectedIndex(0);
        Hubungan.setText("");
        KeluhanUtama.setText("");
        RPS.setText("");
        RPD.setText("");
        RPO.setText("");
        Alergi.setText("");
        RiwayatKeluarga.setText("");
        NoGigi.setSelectedIndex(0);
        Kondisi.setSelectedIndex(0);
        KetGigi.setText("");
        Occlusi.setSelectedIndex(0);
        TorusPalatinus.setSelectedIndex(0);
        TorusMandibularis.setSelectedIndex(0);
        Supernumerary.setSelectedIndex(0);
        KetSupernumerary.setText("");
        GigiAnomali.setSelectedIndex(0);
        KetGigiAnomali.setText("");
        Diastema.setSelectedIndex(0);
        KetDiastema.setText("");
        LainLain.setText("");
        DataPenunjang.setText("");
        Diagnosa.setText("");
        MasalahKeperawatan.setText("");
        Terapi.setText("");
        Valid.tabelKosong(tabModeGigi);
        Tanggal.setDate(new Date());
        TabRawat.setSelectedIndex(0);
        KeluhanUtama.requestFocus();
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
            Valid.SetTgl2(Tanggal,tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());
            Anamnesis.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            Hubungan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            KeluhanUtama.setText(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            RPS.setText(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            RPD.setText(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            RPO.setText(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            Alergi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            RiwayatKeluarga.setText(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
            Occlusi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            TorusPalatinus.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());
            TorusMandibularis.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
            Supernumerary.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString().startsWith("Ada")?"Ada":"Tidak Ada");
            KetSupernumerary.setText(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString().startsWith("Ada : ")?tbObat.getValueAt(tbObat.getSelectedRow(),19).toString().substring(6):"");
            GigiAnomali.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString().startsWith("Ada")?"Ada":"Tidak Ada");
            KetGigiAnomali.setText(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString().startsWith("Ada : ")?tbObat.getValueAt(tbObat.getSelectedRow(),20).toString().substring(6):"");
            Diastema.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString().startsWith("Ada")?"Ada":"Tidak Ada");
            KetDiastema.setText(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString().startsWith("Ada : ")?tbObat.getValueAt(tbObat.getSelectedRow(),21).toString().substring(6):"");
            LainLain.setText(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString());
            DataPenunjang.setText(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString());
            Diagnosa.setText(tbObat.getValueAt(tbObat.getSelectedRow(),24).toString());
            MasalahKeperawatan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),25).toString());
            Terapi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),26).toString());
            Valid.tabelKosong(tabModeGigi);
            for(i=0;i<tabModeGigiDetail.getRowCount();i++){
                tabModeGigi.addRow(new Object[]{tabModeGigiDetail.getValueAt(i,0),tabModeGigiDetail.getValueAt(i,1),tabModeGigiDetail.getValueAt(i,2)});
            }
        }
    }

    private void isRawat() {
        try {
            ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,reg_periksa.tgl_registrasi,reg_periksa.jam_reg "+
                    "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis where reg_periksa.no_rawat=?");
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
        BtnSimpan.setEnabled(akses.getpenilaian_awal_medis_gigi_mulut());
        BtnHapus.setEnabled(akses.getpenilaian_awal_medis_gigi_mulut());
        BtnEdit.setEnabled(akses.getpenilaian_awal_medis_gigi_mulut());
        BtnPrint.setEnabled(akses.getpenilaian_awal_medis_gigi_mulut()); 
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
    }
  
    private void simpan() {
        if(Sequel.menyimpantf2("penilaian_awal_medis_gigi_mulut","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?",25,new String[]{
                TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19),KdDokter.getText(),Anamnesis.getSelectedItem().toString(),
                Hubungan.getText(),KeluhanUtama.getText(),RPS.getText(),RPD.getText(),RPO.getText(),Alergi.getText(),RiwayatKeluarga.getText(),Occlusi.getSelectedItem().toString(),
                TorusPalatinus.getSelectedItem().toString(),TorusMandibularis.getSelectedItem().toString(),Supernumerary.getSelectedItem().toString(),KetSupernumerary.getText(),
                GigiAnomali.getSelectedItem().toString(),KetGigiAnomali.getText(),Diastema.getSelectedItem().toString(),KetDiastema.getText(),LainLain.getText(),DataPenunjang.getText(),
                Diagnosa.getText(),MasalahKeperawatan.getText(),Terapi.getText()
            })==true){
                tabMode.addRow(new Object[]{
                    TNoRw.getText(),TNoRM.getText(),TPasien.getText(),TglLahir.getText(),Jk.getText(),KdDokter.getText(),NmDokter.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19),
                    Anamnesis.getSelectedItem().toString(),Hubungan.getText(),KeluhanUtama.getText(),RPS.getText(),RPD.getText(),RPO.getText(),Alergi.getText(),RiwayatKeluarga.getText(),Occlusi.getSelectedItem().toString(),
                    TorusPalatinus.getSelectedItem().toString(),TorusMandibularis.getSelectedItem().toString(),Supernumerary.getSelectedItem().toString().equals("Ada")?"Ada : "+KetSupernumerary.getText():"Tidak Ada",
                    GigiAnomali.getSelectedItem().toString().equals("Ada")?"Ada : "+KetGigiAnomali.getText():"Tidak Ada",Diastema.getSelectedItem().toString().equals("Ada")?"Ada : "+KetDiastema.getText():"Tidak Ada",
                    LainLain.getText(),DataPenunjang.getText(),Diagnosa.getText(),MasalahKeperawatan.getText(),Terapi.getText()
                });
                LCount.setText(""+tabMode.getRowCount());
                Valid.tabelKosong(tabModeGigiDetail);
                for(i=0;i<tabModeGigi.getRowCount();i++){
                    if(Sequel.menyimpantf2("penilaian_awal_medis_gigi_odontogram","?,?,?,?",4,new String[]{
                            TNoRw.getText(),tabModeGigi.getValueAt(i,0).toString(),tabModeGigi.getValueAt(i,1).toString(),tabModeGigi.getValueAt(i,2).toString()
                        })==true){
                        tabModeGigiDetail.addRow(new Object[]{
                            tabModeGigi.getValueAt(i,0).toString(),tabModeGigi.getValueAt(i,1).toString(),tabModeGigi.getValueAt(i,2).toString()
                        });
                    }
                }
                TNoRM1.setText(TNoRM.getText());
                TPasien1.setText(TPasien.getText());
                emptTeks();
        }
    }

    private void ganti() {
        if(Sequel.mengedittf2("penilaian_awal_medis_gigi_mulut","no_rawat=?","no_rawat=?,tanggal=?,kd_dokter=?,anamnesis=?,hubungan=?,keluhan_utama=?,rps=?,rpd=?,rpo=?,alergi=?,riwayat_keluarga=?,occlusi=?,torus_palatinus=?,torus_mandibularis=?,supernumerary=?,ket_supernumerary=?,gigi_anomali=?,ket_gigi_anomali=?,diastema=?,ket_diastema=?,lain_lain=?,data_penunjang=?,diagnosa=?,masalah_keperawatan=?,terapi=?",26,new String[]{
                TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19),KdDokter.getText(),Anamnesis.getSelectedItem().toString(),Hubungan.getText(),KeluhanUtama.getText(),RPS.getText(),RPD.getText(),RPO.getText(),Alergi.getText(),RiwayatKeluarga.getText(),Occlusi.getSelectedItem().toString(),TorusPalatinus.getSelectedItem().toString(),TorusMandibularis.getSelectedItem().toString(),
                Supernumerary.getSelectedItem().toString(),KetSupernumerary.getText(),GigiAnomali.getSelectedItem().toString(),KetGigiAnomali.getText(),Diastema.getSelectedItem().toString(),KetDiastema.getText(),LainLain.getText(),DataPenunjang.getText(),Diagnosa.getText(),MasalahKeperawatan.getText(),Terapi.getText(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
            })==true){
                tbObat.setValueAt(TNoRw.getText(),tbObat.getSelectedRow(),0);
                tbObat.setValueAt(TNoRM.getText(),tbObat.getSelectedRow(),1);
                tbObat.setValueAt(TPasien.getText(),tbObat.getSelectedRow(),2);
                tbObat.setValueAt(TglLahir.getText(),tbObat.getSelectedRow(),3);
                tbObat.setValueAt(Jk.getText(),tbObat.getSelectedRow(),4);
                tbObat.setValueAt(KdDokter.getText(),tbObat.getSelectedRow(),5);
                tbObat.setValueAt(NmDokter.getText(),tbObat.getSelectedRow(),6);
                tbObat.setValueAt(Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19),tbObat.getSelectedRow(),7);
                tbObat.setValueAt(Anamnesis.getSelectedItem().toString(),tbObat.getSelectedRow(),8);
                tbObat.setValueAt(Hubungan.getText(),tbObat.getSelectedRow(),9);
                tbObat.setValueAt(KeluhanUtama.getText(),tbObat.getSelectedRow(),10);
                tbObat.setValueAt(RPS.getText(),tbObat.getSelectedRow(),11);
                tbObat.setValueAt(RPD.getText(),tbObat.getSelectedRow(),12);
                tbObat.setValueAt(RPO.getText(),tbObat.getSelectedRow(),13);
                tbObat.setValueAt(Alergi.getText(),tbObat.getSelectedRow(),14);
                tbObat.setValueAt(RiwayatKeluarga.getText(),tbObat.getSelectedRow(),15);
                tbObat.setValueAt(Occlusi.getSelectedItem().toString(),tbObat.getSelectedRow(),16);
                tbObat.setValueAt(TorusPalatinus.getSelectedItem().toString(),tbObat.getSelectedRow(),17);
                tbObat.setValueAt(TorusMandibularis.getSelectedItem().toString(),tbObat.getSelectedRow(),18);
                tbObat.setValueAt(Supernumerary.getSelectedItem().toString().equals("Ada")?"Ada : "+KetSupernumerary.getText():"Tidak Ada",tbObat.getSelectedRow(),19);
                tbObat.setValueAt(GigiAnomali.getSelectedItem().toString().equals("Ada")?"Ada : "+KetGigiAnomali.getText():"Tidak Ada",tbObat.getSelectedRow(),20);
                tbObat.setValueAt(Diastema.getSelectedItem().toString().equals("Ada")?"Ada : "+KetDiastema.getText():"Tidak Ada",tbObat.getSelectedRow(),21);
                tbObat.setValueAt(LainLain.getText(),tbObat.getSelectedRow(),22);
                tbObat.setValueAt(DataPenunjang.getText(),tbObat.getSelectedRow(),23);
                tbObat.setValueAt(Diagnosa.getText(),tbObat.getSelectedRow(),24);
                tbObat.setValueAt(MasalahKeperawatan.getText(),tbObat.getSelectedRow(),25);
                tbObat.setValueAt(Terapi.getText(),tbObat.getSelectedRow(),26);
                Sequel.meghapus("penilaian_awal_medis_gigi_odontogram","no_rawat",tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
                Valid.tabelKosong(tabModeGigiDetail);
                for(i=0;i<tabModeGigi.getRowCount();i++){
                    if(Sequel.menyimpantf2("penilaian_awal_medis_gigi_odontogram","?,?,?,?",4,new String[]{
                            TNoRw.getText(),tabModeGigi.getValueAt(i,0).toString(),tabModeGigi.getValueAt(i,1).toString(),tabModeGigi.getValueAt(i,2).toString()
                        })==true){
                        tabModeGigiDetail.addRow(new Object[]{
                            tabModeGigi.getValueAt(i,0).toString(),tabModeGigi.getValueAt(i,1).toString(),tabModeGigi.getValueAt(i,2).toString()
                        });
                    }
                }
                TNoRM1.setText(TNoRM.getText());
                TPasien1.setText(TPasien.getText());
                emptTeks();
                TabRawat.setSelectedIndex(1);
        }
    }

    private void hapus() {
        if(Sequel.queryu2tf("delete from penilaian_awal_medis_gigi_mulut where no_rawat=?",1,new String[]{
            tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
        })==true){
            tabMode.removeRow(tbObat.getSelectedRow());
            LCount.setText(""+tabMode.getRowCount());
            TNoRM1.setText("");
            TPasien1.setText("");
            Valid.tabelKosong(tabModeGigiDetail);
            TabRawat.setSelectedIndex(1);
        }else{
            JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
        }
    }

    private void isMenu(){
        if(ChkAccor.isSelected()==true){
            ChkAccor.setVisible(false);
            PanelAccor.setPreferredSize(new Dimension(470,HEIGHT));
            FormMenu.setVisible(true);
            FormOdontogram.setVisible(true);
            ChkAccor.setVisible(true);
        }else if(ChkAccor.isSelected()==false){
            ChkAccor.setVisible(false);
            PanelAccor.setPreferredSize(new Dimension(15,HEIGHT));
            FormMenu.setVisible(false);
            FormOdontogram.setVisible(false);
            ChkAccor.setVisible(true);
        }
    }

    private void getOdontogram() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRM1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TPasien1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            try {
                Valid.tabelKosong(tabModeGigiDetail);
                ps=koneksi.prepareStatement(
                    "select penilaian_awal_medis_gigi_odontogram.no_gigi,penilaian_awal_medis_gigi_odontogram.kondisi,penilaian_awal_medis_gigi_odontogram.keterangan "+
                    "from penilaian_awal_medis_gigi_odontogram where penilaian_awal_medis_gigi_odontogram.no_rawat=? order by penilaian_awal_medis_gigi_odontogram.no_gigi"
                );
                try {
                    ps.setString(1,tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
                    rs=ps.executeQuery();
                    while(rs.next()){
                        tabModeGigiDetail.addRow(new Object[]{rs.getString("no_gigi"),rs.getString("kondisi"),rs.getString("keterangan")});
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




/*
 * By Mas Khanza
 */


package surat;

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
import kepegawaian.DlgCariPetugas;


/**
 *
 * @author windiartohugroho
 */
public final class SuratPermintaanSecondOpinion extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;
    private DlgCariPetugas petugas;
    private String finger="",finger2="",lokasifile="",lokasifile2="";
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private volatile boolean ceksukses = false;
    
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public SuratPermintaanSecondOpinion(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Surat","No.Rawat","No.RM","Nama Pasien","Tgl.Lahir","J.K.","Tanggal","Pemohon","Alamat Pemohon",
            "Tgl.Lahir","J.K.P.P.","Hubungan","Saksi I Keluarga","Kode Dokter","Nama Dokter","Nip","Saksi II Petugas"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 17; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(95);
            }else if(i==1){
                column.setPreferredWidth(105);
            }else if(i==2){
                column.setPreferredWidth(70);
            }else if(i==3){
                column.setPreferredWidth(150);
            }else if(i==4){
                column.setPreferredWidth(65);
            }else if(i==5){
                column.setPreferredWidth(25);
            }else if(i==6){
                column.setPreferredWidth(65);
            }else if(i==7){
                column.setPreferredWidth(160);
            }else if(i==8){
                column.setPreferredWidth(210);
            }else if(i==9){
                column.setPreferredWidth(65);
            }else if(i==10){
                column.setPreferredWidth(45);
            }else if(i==11){
                column.setPreferredWidth(85);
            }else if(i==12){
                column.setPreferredWidth(160);
            }else if(i==13){
                column.setPreferredWidth(90);
            }else if(i==14){
                column.setPreferredWidth(160);
            }else if(i==15){
                column.setPreferredWidth(90);
            }else if(i==16){
                column.setPreferredWidth(160);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());
        
        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        NoSurat.setDocument(new batasInput((byte)20).getKata(NoSurat));
        Pemohon.setDocument(new batasInput((int)50).getKata(Pemohon));
        AlamatPemohon.setDocument(new batasInput((int)100).getKata(AlamatPemohon));
        SaksiKeluarga.setDocument(new batasInput((int)50).getKata(SaksiKeluarga));
        TCari.setDocument(new batasInput((int)100).getKata(TCari));
        
        ChkInput.setSelected(false);
        isForm();
        
        ChkAccor.setSelected(false);
        isPhoto();
        
        HTMLEditorKit kit = new HTMLEditorKit();
        LoadHTML.setContentType("text/html");
        LoadHTML.setEditable(true);
        LoadHTML.setEditorKit(kit);
        LoadHTML2.setContentType("text/html");
        LoadHTML2.setEditable(true);
        LoadHTML2.setEditorKit(kit);
        LoadHTML3.setContentType("text/html");
        LoadHTML3.setEditable(true);
        LoadHTML3.setEditorKit(kit);
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
        Document doc2 = kit.createDefaultDocument();
        Document doc3 = kit.createDefaultDocument();
        LoadHTML.setDocument(doc);
        LoadHTML2.setDocument(doc2);
        LoadHTML3.setDocument(doc3);
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
        internalFrame1 = new widget.InternalFrame();
        jPanel3 = new javax.swing.JPanel();
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
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        BtnPrint = new widget.Button();
        BtnAll = new widget.Button();
        BtnKeluar = new widget.Button();
        Scroll = new widget.ScrollPane();
        tbObat = new widget.Table();
        PanelAccor = new widget.PanelBiasa();
        ChkAccor = new widget.CekBox();
        FormPhoto = new widget.PanelBiasa();
        FormPass3 = new widget.PanelBiasa();
        btnAmbil = new widget.Button();
        BtnRefreshPhoto1 = new widget.Button();
        BtnPrint1 = new widget.Button();
        TabData = new javax.swing.JTabbedPane();
        Scroll5 = new widget.ScrollPane();
        LoadHTML2 = new widget.editorpane();
        Scroll6 = new widget.ScrollPane();
        LoadHTML3 = new widget.editorpane();
        PanelInput = new javax.swing.JPanel();
        ChkInput = new widget.CekBox();
        FormInput = new widget.PanelBiasa();
        jSeparator14 = new javax.swing.JSeparator();
        TNoRw = new widget.TextBox();
        TPasien = new widget.TextBox();
        TNoRM = new widget.TextBox();
        label14 = new widget.Label();
        KdDokter = new widget.TextBox();
        NmDokter = new widget.TextBox();
        BtnDokter = new widget.Button();
        jLabel8 = new widget.Label();
        TglLahir = new widget.TextBox();
        Jk = new widget.TextBox();
        jLabel10 = new widget.Label();
        label11 = new widget.Label();
        jLabel11 = new widget.Label();
        HubunganPemohon = new widget.ComboBox();
        TglSurat = new widget.Tanggal();
        jLabel9 = new widget.Label();
        NoSurat = new widget.TextBox();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel100 = new widget.Label();
        jLabel13 = new widget.Label();
        Pemohon = new widget.TextBox();
        jLabel37 = new widget.Label();
        AlamatPemohon = new widget.TextBox();
        jLabel38 = new widget.Label();
        jLabel39 = new widget.Label();
        TanggalLahir = new widget.Tanggal();
        jLabel14 = new widget.Label();
        JKPemohon = new widget.ComboBox();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel101 = new widget.Label();
        label15 = new widget.Label();
        KdPerawat = new widget.TextBox();
        NmPerawat = new widget.TextBox();
        BtnPerawat = new widget.Button();
        jLabel42 = new widget.Label();
        SaksiKeluarga = new widget.TextBox();

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Permintaan Second Opinion Pasien ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(44, 100));
        jPanel3.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel19.setText("Tanggal :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(60, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "01-05-2026" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "01-05-2026" }));
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
        TCari.setPreferredSize(new java.awt.Dimension(205, 23));
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

        jPanel3.add(panelGlass9, java.awt.BorderLayout.CENTER);

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

        jPanel3.add(panelGlass8, java.awt.BorderLayout.PAGE_END);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);
        Scroll.setPreferredSize(new java.awt.Dimension(452, 200));

        tbObat.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
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

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        PanelAccor.setBackground(new java.awt.Color(255, 255, 255));
        PanelAccor.setName("PanelAccor"); // NOI18N
        PanelAccor.setPreferredSize(new java.awt.Dimension(430, 43));
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

        FormPhoto.setBackground(new java.awt.Color(255, 255, 255));
        FormPhoto.setBorder(null);
        FormPhoto.setName("FormPhoto"); // NOI18N
        FormPhoto.setPreferredSize(new java.awt.Dimension(115, 73));
        FormPhoto.setLayout(new java.awt.BorderLayout());

        FormPass3.setBackground(new java.awt.Color(255, 255, 255));
        FormPass3.setBorder(null);
        FormPass3.setName("FormPass3"); // NOI18N
        FormPass3.setPreferredSize(new java.awt.Dimension(115, 40));

        btnAmbil.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/plus_16.png"))); // NOI18N
        btnAmbil.setMnemonic('U');
        btnAmbil.setText("Ambil");
        btnAmbil.setToolTipText("Alt+U");
        btnAmbil.setName("btnAmbil"); // NOI18N
        btnAmbil.setPreferredSize(new java.awt.Dimension(100, 30));
        btnAmbil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAmbilActionPerformed(evt);
            }
        });
        FormPass3.add(btnAmbil);

        BtnRefreshPhoto1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/refresh.png"))); // NOI18N
        BtnRefreshPhoto1.setMnemonic('U');
        BtnRefreshPhoto1.setText("Refresh");
        BtnRefreshPhoto1.setToolTipText("Alt+U");
        BtnRefreshPhoto1.setName("BtnRefreshPhoto1"); // NOI18N
        BtnRefreshPhoto1.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnRefreshPhoto1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRefreshPhoto1ActionPerformed(evt);
            }
        });
        FormPass3.add(BtnRefreshPhoto1);

        BtnPrint1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item (copy).png"))); // NOI18N
        BtnPrint1.setMnemonic('T');
        BtnPrint1.setText("Surat");
        BtnPrint1.setToolTipText("Alt+T");
        BtnPrint1.setName("BtnPrint1"); // NOI18N
        BtnPrint1.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnPrint1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPrint1ActionPerformed(evt);
            }
        });
        FormPass3.add(BtnPrint1);

        FormPhoto.add(FormPass3, java.awt.BorderLayout.PAGE_END);

        TabData.setBackground(new java.awt.Color(254, 255, 254));
        TabData.setForeground(new java.awt.Color(50, 50, 50));
        TabData.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabData.setName("TabData"); // NOI18N
        TabData.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabDataMouseClicked(evt);
            }
        });

        Scroll5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll5.setName("Scroll5"); // NOI18N
        Scroll5.setOpaque(true);
        Scroll5.setPreferredSize(new java.awt.Dimension(200, 200));

        LoadHTML2.setBorder(null);
        LoadHTML2.setName("LoadHTML2"); // NOI18N
        Scroll5.setViewportView(LoadHTML2);

        TabData.addTab("Bukti Pemohon", Scroll5);

        Scroll6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll6.setName("Scroll6"); // NOI18N
        Scroll6.setOpaque(true);
        Scroll6.setPreferredSize(new java.awt.Dimension(200, 200));

        LoadHTML3.setBorder(null);
        LoadHTML3.setName("LoadHTML3"); // NOI18N
        Scroll6.setViewportView(LoadHTML3);

        TabData.addTab("Bukti Saksi I Keluarga", Scroll6);

        FormPhoto.add(TabData, java.awt.BorderLayout.CENTER);

        PanelAccor.add(FormPhoto, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelAccor, java.awt.BorderLayout.EAST);

        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setPreferredSize(new java.awt.Dimension(292, 225));
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        ChkInput.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput.setMnemonic('I');
        ChkInput.setText(".: Input Data");
        ChkInput.setToolTipText("Alt+I");
        ChkInput.setBorderPainted(true);
        ChkInput.setBorderPaintedFlat(true);
        ChkInput.setFocusable(false);
        ChkInput.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkInput.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkInput.setName("ChkInput"); // NOI18N
        ChkInput.setPreferredSize(new java.awt.Dimension(192, 20));
        ChkInput.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkInputActionPerformed(evt);
            }
        });
        PanelInput.add(ChkInput, java.awt.BorderLayout.PAGE_END);

        FormInput.setBackground(new java.awt.Color(255, 255, 255));
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(870, 203));
        FormInput.setLayout(null);

        jSeparator14.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator14.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator14.setName("jSeparator14"); // NOI18N
        FormInput.add(jSeparator14);
        jSeparator14.setBounds(0, 861, 880, 0);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(84, 10, 131, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        FormInput.add(TPasien);
        TPasien.setBounds(319, 10, 250, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        FormInput.add(TNoRM);
        TNoRM.setBounds(217, 10, 100, 23);

        label14.setText("Dokter :");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label14);
        label14.setBounds(0, 40, 80, 23);

        KdDokter.setEditable(false);
        KdDokter.setName("KdDokter"); // NOI18N
        KdDokter.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput.add(KdDokter);
        KdDokter.setBounds(84, 40, 120, 23);

        NmDokter.setEditable(false);
        NmDokter.setName("NmDokter"); // NOI18N
        NmDokter.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmDokter);
        NmDokter.setBounds(206, 40, 215, 23);

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
        BtnDokter.setBounds(423, 40, 28, 23);

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
        jLabel10.setBounds(0, 10, 80, 23);

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label11);
        label11.setBounds(465, 40, 57, 23);

        jLabel11.setText("J.K. :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(740, 10, 30, 23);

        HubunganPemohon.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Diri Sendiri", "Istri", "Suami", "Kerabat", "Orang Tua", "Anak", "Saudara Kandung", "Teman", "Lain-lain" }));
        HubunganPemohon.setName("HubunganPemohon"); // NOI18N
        HubunganPemohon.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                HubunganPemohonItemStateChanged(evt);
            }
        });
        HubunganPemohon.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HubunganPemohonKeyPressed(evt);
            }
        });
        FormInput.add(HubunganPemohon);
        HubunganPemohon.setBounds(105, 90, 135, 23);

        TglSurat.setForeground(new java.awt.Color(50, 70, 50));
        TglSurat.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "01-05-2026" }));
        TglSurat.setDisplayFormat("dd-MM-yyyy");
        TglSurat.setName("TglSurat"); // NOI18N
        TglSurat.setOpaque(false);
        TglSurat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglSuratKeyPressed(evt);
            }
        });
        FormInput.add(TglSurat);
        TglSurat.setBounds(526, 40, 90, 23);

        jLabel9.setText("No.Pernyataan :");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(630, 40, 90, 23);

        NoSurat.setHighlighter(null);
        NoSurat.setName("NoSurat"); // NOI18N
        NoSurat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoSuratKeyPressed(evt);
            }
        });
        FormInput.add(NoSurat);
        NoSurat.setBounds(724, 40, 130, 23);

        jSeparator1.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator1.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator1.setName("jSeparator1"); // NOI18N
        FormInput.add(jSeparator1);
        jSeparator1.setBounds(0, 70, 880, 1);

        jLabel100.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel100.setText("A. PEMBUAT PERNYATAAN");
        jLabel100.setName("jLabel100"); // NOI18N
        FormInput.add(jLabel100);
        jLabel100.setBounds(10, 70, 180, 23);

        jLabel13.setText("Hubungan :");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(0, 90, 101, 23);

        Pemohon.setHighlighter(null);
        Pemohon.setName("Pemohon"); // NOI18N
        Pemohon.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PemohonKeyPressed(evt);
            }
        });
        FormInput.add(Pemohon);
        Pemohon.setBounds(301, 90, 238, 23);

        jLabel37.setText("Nama :");
        jLabel37.setName("jLabel37"); // NOI18N
        FormInput.add(jLabel37);
        jLabel37.setBounds(237, 90, 60, 23);

        AlamatPemohon.setHighlighter(null);
        AlamatPemohon.setName("AlamatPemohon"); // NOI18N
        AlamatPemohon.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlamatPemohonKeyPressed(evt);
            }
        });
        FormInput.add(AlamatPemohon);
        AlamatPemohon.setBounds(105, 120, 749, 23);

        jLabel38.setText("Tgl.Lahir :");
        jLabel38.setName("jLabel38"); // NOI18N
        FormInput.add(jLabel38);
        jLabel38.setBounds(516, 90, 90, 23);

        jLabel39.setText("Alamat :");
        jLabel39.setName("jLabel39"); // NOI18N
        FormInput.add(jLabel39);
        jLabel39.setBounds(0, 120, 101, 23);

        TanggalLahir.setForeground(new java.awt.Color(50, 70, 50));
        TanggalLahir.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "01-05-2026" }));
        TanggalLahir.setDisplayFormat("dd-MM-yyyy");
        TanggalLahir.setName("TanggalLahir"); // NOI18N
        TanggalLahir.setOpaque(false);
        TanggalLahir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalLahirKeyPressed(evt);
            }
        });
        FormInput.add(TanggalLahir);
        TanggalLahir.setBounds(610, 90, 90, 23);

        jLabel14.setText("J.K. :");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(705, 90, 40, 23);

        JKPemohon.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Laki-laki", "Perempuan" }));
        JKPemohon.setName("JKPemohon"); // NOI18N
        JKPemohon.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JKPemohonKeyPressed(evt);
            }
        });
        FormInput.add(JKPemohon);
        JKPemohon.setBounds(749, 90, 105, 23);

        jSeparator3.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator3.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator3.setName("jSeparator3"); // NOI18N
        FormInput.add(jSeparator3);
        jSeparator3.setBounds(0, 150, 880, 1);

        jLabel101.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel101.setText("B. SAKSI-SAKSI");
        jLabel101.setName("jLabel101"); // NOI18N
        FormInput.add(jLabel101);
        jLabel101.setBounds(10, 150, 180, 23);

        label15.setText("Saksi II Petugas :");
        label15.setName("label15"); // NOI18N
        label15.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label15);
        label15.setBounds(0, 170, 130, 23);

        KdPerawat.setEditable(false);
        KdPerawat.setName("KdPerawat"); // NOI18N
        KdPerawat.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput.add(KdPerawat);
        KdPerawat.setBounds(134, 170, 100, 23);

        NmPerawat.setEditable(false);
        NmPerawat.setName("NmPerawat"); // NOI18N
        NmPerawat.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmPerawat);
        NmPerawat.setBounds(236, 170, 193, 23);

        BtnPerawat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPerawat.setMnemonic('2');
        BtnPerawat.setToolTipText("Alt+2");
        BtnPerawat.setName("BtnPerawat"); // NOI18N
        BtnPerawat.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnPerawat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPerawatActionPerformed(evt);
            }
        });
        BtnPerawat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPerawatKeyPressed(evt);
            }
        });
        FormInput.add(BtnPerawat);
        BtnPerawat.setBounds(431, 170, 28, 23);

        jLabel42.setText("Saksi I Keluarga :");
        jLabel42.setName("jLabel42"); // NOI18N
        FormInput.add(jLabel42);
        jLabel42.setBounds(460, 170, 110, 23);

        SaksiKeluarga.setHighlighter(null);
        SaksiKeluarga.setName("SaksiKeluarga"); // NOI18N
        SaksiKeluarga.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SaksiKeluargaKeyPressed(evt);
            }
        });
        FormInput.add(SaksiKeluarga);
        SaksiKeluarga.setBounds(574, 170, 280, 23);

        PanelInput.add(FormInput, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TNoRM.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"Nama Pasien");
        }else if(NmDokter.getText().trim().equals("")){
            Valid.textKosong(BtnDokter,"Dokter");
        }else if(Pemohon.getText().trim().equals("")){
            Valid.textKosong(Pemohon,"Pemohon");
        }else if(NmPerawat.getText().trim().equals("")){
            Valid.textKosong(NmPerawat,"Saksi II Perawat");
        }else if(SaksiKeluarga.getText().trim().equals("")){
            Valid.textKosong(SaksiKeluarga,"Saksi I Keluarga");
        }else{
            if(Sequel.menyimpantf("surat_permintaan_second_opinion","?,?,?,?,?,?,?,?,?,?,?","Data",11,new String[]{
                    NoSurat.getText(),TNoRw.getText(),Valid.SetTgl(TglSurat.getSelectedItem()+""),KdDokter.getText(),KdPerawat.getText(),Pemohon.getText(), 
                    AlamatPemohon.getText(),Valid.SetTgl(TanggalLahir.getSelectedItem()+""),JKPemohon.getSelectedItem().toString().substring(0,1), 
                    HubunganPemohon.getSelectedItem().toString(),SaksiKeluarga.getText()
                })==true){
                Sequel.menyimpan2("dpjp_ranap","?,?","Dokter",2,new String[]{TNoRw.getText(),KdDokter.getText()});
                tabMode.addRow(new Object[]{
                    NoSurat.getText(),TNoRw.getText(),TNoRM.getText(),TPasien.getText(),TglLahir.getText(),Jk.getText().substring(0,1),
                    Valid.SetTgl(TglSurat.getSelectedItem()+""),Pemohon.getText(),AlamatPemohon.getText(),
                    Valid.SetTgl(TanggalLahir.getSelectedItem()+""),JKPemohon.getSelectedItem().toString().substring(0,1),
                    HubunganPemohon.getSelectedItem().toString(),SaksiKeluarga.getText(),KdDokter.getText(),NmDokter.getText(),
                    KdPerawat.getText(),NmPerawat.getText()
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
            Valid.pindah(evt,SaksiKeluarga,BtnBatal);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        emptTeks();
        ChkInput.setSelected(true);
        isForm(); 
}//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            emptTeks();
            ChkInput.setSelected(true);
            isForm(); 
        }else{Valid.pindah(evt, BtnSimpan, BtnHapus);}
}//GEN-LAST:event_BtnBatalKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if(tbObat.getSelectedRow()>-1){
            if(akses.getkode().equals("Admin Utama")){
                hapus();
            }else{
                if(KdPerawat.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString())){
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
        }else if(NmDokter.getText().trim().equals("")){
            Valid.textKosong(BtnDokter,"Dokter");
        }else if(Pemohon.getText().trim().equals("")){
            Valid.textKosong(Pemohon,"Pemohon");
        }else if(NmPerawat.getText().trim().equals("")){
            Valid.textKosong(NmPerawat,"Saksi II Petugas");
        }else if(SaksiKeluarga.getText().trim().equals("")){
            Valid.textKosong(SaksiKeluarga,"Saksi I Keluarga");
        }else{
            if(tbObat.getSelectedRow()>-1){
                if(akses.getkode().equals("Admin Utama")){
                    ganti();
                }else{
                    if(KdPerawat.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString())){
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
                
                String pilihan =(String) JOptionPane.showInputDialog(null,"Silahkan pilih laporan..!","Pilihan Cetak",JOptionPane.QUESTION_MESSAGE,null,new Object[]{"Laporan 1 (HTML)","Laporan 2 (WPS)","Laporan 3 (CSV)"},"Laporan 1 (HTML)");
                switch (pilihan) {
                    case "Laporan 1 (HTML)":
                            htmlContent = new StringBuilder();
                            htmlContent.append("<tr class='isi'>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Surat</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Rawat</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.RM</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Pasien</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tgl.Lahir</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>J.K.</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tanggal</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Pemohon</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Alamat Pemohon</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tgl.Lahir</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>J.K.P.P.</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Hubungan</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Saksi I Keluarga</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kode Dokter</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Dokter</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nip</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Saksi II Petugas</b></td>").
                                        append("</tr>");
                            for (i = 0; i < tabMode.getRowCount(); i++) {
                                htmlContent.append("<tr class='isi'>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,0).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,1).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,2).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,3).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,4).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,5).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,6).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,7).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,8).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,9).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,10).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,11).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,12).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,13).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,14).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,15).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,16).toString()).append("</td>").
                                            append("</tr>");
                            }
                            LoadHTML.setText(
                                "<html>"+
                                  "<table width='100%' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
                                   htmlContent.toString()+
                                  "</table>"+
                                "</html>"
                            );
                            
                            f = new File("DataSuratPermintaanSecondOpinion.html");            
                            bw = new BufferedWriter(new FileWriter(f));            
                            bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                                        "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                                        "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                            "<tr class='isi2'>"+
                                                "<td valign='top' align='center'>"+
                                                    "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                                    akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                                    akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                                    "<font size='2' face='Tahoma'>DATA SURAT PERMINTAAN SECOND OPINION<br><br></font>"+        
                                                "</td>"+
                                           "</tr>"+
                                        "</table>")
                            );
                            bw.close();                         
                            Desktop.getDesktop().browse(f.toURI());
                        break;
                    case "Laporan 2 (WPS)":
                            htmlContent = new StringBuilder();
                            htmlContent.append("<tr class='isi'>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Surat</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Rawat</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.RM</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Pasien</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tgl.Lahir</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>J.K.</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tanggal</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Pemohon</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Alamat Pemohon</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tgl.Lahir</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>J.K.P.P.</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Hubungan</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Saksi I Keluarga</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kode Dokter</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Dokter</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nip</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Saksi II Petugas</b></td>").
                                        append("</tr>");
                            for (i = 0; i < tabMode.getRowCount(); i++) {
                                htmlContent.append("<tr class='isi'>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,0).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,1).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,2).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,3).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,4).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,5).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,6).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,7).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,8).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,9).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,10).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,11).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,12).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,13).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,14).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,15).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,16).toString()).append("</td>").
                                            append("</tr>");
                            }
                            LoadHTML.setText(
                                "<html>"+
                                  "<table width='100%' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
                                   htmlContent.toString()+
                                  "</table>"+
                                "</html>"
                            );
                            
                            f = new File("DataSuratPermintaanSecondOpinion.wps");            
                            bw = new BufferedWriter(new FileWriter(f));            
                            bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                                        "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                                        "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                            "<tr class='isi2'>"+
                                                "<td valign='top' align='center'>"+
                                                    "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                                    akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                                    akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                                    "<font size='2' face='Tahoma'>DATA SURAT PERMINTAAN SECOND OPINION<br><br></font>"+        
                                                "</td>"+
                                           "</tr>"+
                                        "</table>")
                            );
                            bw.close();                         
                            Desktop.getDesktop().browse(f.toURI());
                        break;
                    case "Laporan 3 (CSV)":
                            htmlContent = new StringBuilder();
                            htmlContent.append(                             
                                "\"No.Surat\";\"No.Rawat\";\"No.RM\";\"Nama Pasien\";\"Tgl.Lahir\";\"J.K.\";\"Tanggal\";\"Pemohon\";\"Alamat Pemohon\";\"Tgl.Lahir\";\"J.K.P.P.\";\"Hubungan\";\"Saksi Keluarga\";\"Kode Dokter\";\"Nama Dokter\";\"Nip\";\"Saksi II Petugas\"\n"
                            ); 
                            for (i = 0; i < tabMode.getRowCount(); i++) {
                                htmlContent.append("\"").append(tbObat.getValueAt(i,0).toString()).append("\";\"").append(tbObat.getValueAt(i,1).toString()).append("\";\"").append(tbObat.getValueAt(i,2).toString()).append("\";\"").append(tbObat.getValueAt(i,3).toString()).append("\";\"").append(tbObat.getValueAt(i,4).toString()).append("\";\"").append(tbObat.getValueAt(i,5).toString()).append("\";\"").append(tbObat.getValueAt(i,6).toString()).append("\";\"").append(tbObat.getValueAt(i,7).toString()).append("\";\"").append(tbObat.getValueAt(i,8).toString()).append("\";\"").append(tbObat.getValueAt(i,9).toString()).append("\";\"").
                                                         append(tbObat.getValueAt(i,10).toString()).append("\";\"").append(tbObat.getValueAt(i,11).toString()).append("\";\"").append(tbObat.getValueAt(i,12).toString()).append("\";\"").append(tbObat.getValueAt(i,13).toString()).append("\";\"").append(tbObat.getValueAt(i,14).toString()).append("\";\"").append(tbObat.getValueAt(i,15).toString()).append("\";\"").append(tbObat.getValueAt(i,16).toString()).append("\"\n");
                            }
                            f = new File("DataSuratPermintaanSecondOpinion.csv");            
                            bw = new BufferedWriter(new FileWriter(f));            
                            bw.write(htmlContent.toString());
                            bw.close();                         
                            Desktop.getDesktop().browse(f.toURI());
                        break; 
                }   
                htmlContent=null;
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
                isPhoto();
                panggilPhoto();
            } catch (java.lang.NullPointerException e) {
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
            }
        }
}//GEN-LAST:event_tbObatKeyPressed

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isRawat();
        }else{
            Valid.pindah(evt,TCari,BtnDokter);
        }
    }//GEN-LAST:event_TNoRwKeyPressed

    private void BtnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokterActionPerformed
        DlgCariDokter dokter=new DlgCariDokter(null,false);
        dokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(dokter.getTable().getSelectedRow()!= -1){
                    KdDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                    NmDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                    KdDokter.requestFocus();
                }
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setAlwaysOnTop(false);
        dokter.setVisible(true);
    }//GEN-LAST:event_BtnDokterActionPerformed

    private void BtnDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokterKeyPressed
        //Valid.pindah(evt,Kesimpulandokter,TglAsuhan);
    }//GEN-LAST:event_BtnDokterKeyPressed

    private void HubunganPemohonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HubunganPemohonKeyPressed
        //Valid.pindah(evt,Biaya,AlasanDiwakilkan);
    }//GEN-LAST:event_HubunganPemohonKeyPressed

    private void TglSuratKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglSuratKeyPressed
        Valid.pindah(evt,BtnDokter,HubunganPemohon);
    }//GEN-LAST:event_TglSuratKeyPressed

    private void TanggalLahirKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalLahirKeyPressed
        //Valid.pindah(evt,AlamatPenerima,NoHPPenerima);
    }//GEN-LAST:event_TanggalLahirKeyPressed

    private void JKPemohonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JKPemohonKeyPressed
        //Valid.pindah(evt,NoHPPenerima,UmurPenerima);
    }//GEN-LAST:event_JKPemohonKeyPressed

    private void BtnPerawatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPerawatActionPerformed
        if (petugas == null || !petugas.isDisplayable()) {
            petugas=new DlgCariPetugas(null,false);
            petugas.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            petugas.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    if(petugas.getTable().getSelectedRow()!= -1){
                        KdPerawat.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                        NmPerawat.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                        KdPerawat.requestFocus();
                    }
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
    }//GEN-LAST:event_BtnPerawatActionPerformed

    private void BtnPerawatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPerawatKeyPressed
        //Valid.pindah(evt,UmurPenerima,SaksiKeluarga);
    }//GEN-LAST:event_BtnPerawatKeyPressed

    private void NoSuratKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoSuratKeyPressed
        //Valid.pindah(evt,TglSurat,Diagnosa);
    }//GEN-LAST:event_NoSuratKeyPressed

    private void PemohonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PemohonKeyPressed
        Valid.pindah(evt,AlamatPemohon,AlamatPemohon);
    }//GEN-LAST:event_PemohonKeyPressed

    private void AlamatPemohonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlamatPemohonKeyPressed
        //Valid.pindah(evt,PenerimaInformasi,NoHPPenerima);
    }//GEN-LAST:event_AlamatPemohonKeyPressed

    private void SaksiKeluargaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SaksiKeluargaKeyPressed
        Valid.pindah(evt,BtnPerawat,BtnSimpan);
    }//GEN-LAST:event_SaksiKeluargaKeyPressed

    private void HubunganPemohonItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_HubunganPemohonItemStateChanged
        if(HubunganPemohon.getSelectedIndex()==0){
            try {
                ps=koneksi.prepareStatement("select concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) asal,"+
                            "TIMESTAMPDIFF(YEAR, pasien.tgl_lahir, CURDATE()) as tahun,pasien.tgl_lahir "+
                            "from pasien inner join kelurahan on pasien.kd_kel=kelurahan.kd_kel "+
                            "inner join kecamatan on pasien.kd_kec=kecamatan.kd_kec "+
                            "inner join kabupaten on pasien.kd_kab=kabupaten.kd_kab "+
                            "inner join penjab on pasien.kd_pj=penjab.kd_pj "+
                            "where pasien.no_rkm_medis=?");
                try {            
                    ps.setString(1,TNoRM.getText());
                    rs=ps.executeQuery();
                    while(rs.next()){
                        Pemohon.setText(TPasien.getText());
                        AlamatPemohon.setText(rs.getString("asal"));
                        TanggalLahir.setDate(rs.getDate("tgl_lahir"));
                        JKPemohon.setSelectedItem(Jk.getText());
                    }
                } catch (Exception ex) {
                    System.out.println(ex);
                }finally{
                    if(rs != null ){
                        rs.close();
                    }

                    if(ps != null ){
                        ps.close();
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }else{
            Pemohon.setText("");
            AlamatPemohon.setText("");
            TanggalLahir.setDate(new Date());
            JKPemohon.setSelectedIndex(0);
        }
    }//GEN-LAST:event_HubunganPemohonItemStateChanged

    private void ChkAccorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkAccorActionPerformed
        if(tbObat.getSelectedRow()!= -1){
            isPhoto();
            panggilPhoto();
        }else{
            ChkAccor.setSelected(false);
            JOptionPane.showMessageDialog(null,"Silahkan pilih No.Surat..!!!");
        }
    }//GEN-LAST:event_ChkAccorActionPerformed

    private void btnAmbilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAmbilActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        }else{
            if(tbObat.getSelectedRow()>-1){
                Sequel.queryu("insert into antripermintaansecondopinion values('"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"','"+tbObat.getValueAt(tbObat.getSelectedRow(),1).toString()+"')");
                Sequel.queryu("delete from bukti_surat_permintaan_second_opinion where no_pernyataan='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'");
                Sequel.queryu("delete from bukti_surat_permintaan_second_opinion_saksikeluarga where no_pernyataan='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'");
            }else{
                JOptionPane.showMessageDialog(rootPane,"Silahkan anda pilih No.Surat terlebih dahulu..!!");
            }   
        }
    }//GEN-LAST:event_btnAmbilActionPerformed

    private void BtnRefreshPhoto1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRefreshPhoto1ActionPerformed
        if(tbObat.getSelectedRow()>-1){
            panggilPhoto();
        }else{
            JOptionPane.showMessageDialog(rootPane,"Silahkan anda pilih No.Surat terlebih dahulu..!!");
        }
    }//GEN-LAST:event_BtnRefreshPhoto1ActionPerformed

    private void BtnPrint1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrint1ActionPerformed
        if(tbObat.getSelectedRow()>-1){
            if(lokasifile.equals("")){
                JOptionPane.showMessageDialog(null,"Maaf, Silahkan ambil photo bukti permintaan second opinion terlebih dahulu..!!!!");
            }else{
                Map<String, Object> param = new HashMap<>();
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("emailrs",akses.getemailrs());
                param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
                param.put("photo_penerima",getSecondOpinionPhotoURL(lokasifile));
                param.put("photo_saksi",getSecondOpinionPhotoURL(lokasifile2));
                finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
                finger2=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
                param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbObat.getValueAt(tbObat.getSelectedRow(),14).toString()+"\nID "+(finger.equals("")?tbObat.getValueAt(tbObat.getSelectedRow(),13).toString():finger)+"\n"+Valid.SetTgl3(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString()));
                param.put("finger2","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbObat.getValueAt(tbObat.getSelectedRow(),16).toString()+"\nID "+(finger2.equals("")?tbObat.getValueAt(tbObat.getSelectedRow(),15).toString():finger2)+"\n"+Valid.SetTgl3(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString()));
                Valid.MyReportqry("rptSuratPermintaanSecondOpinion.jasper","report","::[ Surat Permintaan Second Opinion ]::",
                    "select surat_permintaan_second_opinion.no_pernyataan as no_surat,reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,pasien.tmp_lahir,concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab,', ',propinsi.nm_prop) as alamat,surat_permintaan_second_opinion.tanggal,"+
                    "surat_permintaan_second_opinion.pembuat_pernyataan as pemohon,surat_permintaan_second_opinion.alamat_pembuat_pernyataan as alamat_pemohon,pasien.umur,pasien.no_tlp,"+
                    "surat_permintaan_second_opinion.tgl_lahir_pembuat_pernyataan as tgl_lahir_pemohon,surat_permintaan_second_opinion.jk_pembuat_pernyataan as jk_pemohon,surat_permintaan_second_opinion.hubungan_pembuat_pernyataan as hubungan_pemohon,surat_permintaan_second_opinion.saksi_keluarga,surat_permintaan_second_opinion.kd_dokter,dokter.nm_dokter,surat_permintaan_second_opinion.nip,petugas.nama from surat_permintaan_second_opinion inner join reg_periksa on surat_permintaan_second_opinion.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on pasien.no_rkm_medis=reg_periksa.no_rkm_medis inner join dokter on dokter.kd_dokter=surat_permintaan_second_opinion.kd_dokter inner join petugas on petugas.nip=surat_permintaan_second_opinion.nip inner join kelurahan on pasien.kd_kel=kelurahan.kd_kel inner join kecamatan on pasien.kd_kec=kecamatan.kd_kec inner join kabupaten on pasien.kd_kab=kabupaten.kd_kab inner join propinsi on pasien.kd_prop=propinsi.kd_prop "+
                    "where surat_permintaan_second_opinion.no_pernyataan='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'",param);
            }
        }else{
            JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data terlebih dahulu..!!!!");
        }
    }//GEN-LAST:event_BtnPrint1ActionPerformed

    private void TabDataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabDataMouseClicked
        if(TabData.getSelectedIndex()==0){
            if(lokasifile.equals("")){
                setPreviewPhoto(LoadHTML2,"");
            }else{
                setPreviewPhoto(LoadHTML2,lokasifile);
            } 
        }else{
            if(lokasifile2.equals("")){
                setPreviewPhoto(LoadHTML3,"");
            }else{
                setPreviewPhoto(LoadHTML3,lokasifile2);
            } 
        }
    }//GEN-LAST:event_TabDataMouseClicked

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

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
            SuratPermintaanSecondOpinion dialog = new SuratPermintaanSecondOpinion(new javax.swing.JFrame(), true);
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
    private widget.TextBox AlamatPemohon;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnDokter;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPerawat;
    private widget.Button BtnPrint;
    private widget.Button BtnPrint1;
    private widget.Button BtnRefreshPhoto1;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkAccor;
    private widget.CekBox ChkInput;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.PanelBiasa FormInput;
    private widget.PanelBiasa FormPass3;
    private widget.PanelBiasa FormPhoto;
    private widget.ComboBox HubunganPemohon;
    private widget.ComboBox JKPemohon;
    private widget.TextBox Jk;
    private widget.TextBox KdDokter;
    private widget.TextBox KdPerawat;
    private widget.Label LCount;
    private widget.editorpane LoadHTML;
    private widget.editorpane LoadHTML2;
    private widget.editorpane LoadHTML3;
    private widget.TextBox NmDokter;
    private widget.TextBox NmPerawat;
    private widget.TextBox NoSurat;
    private widget.PanelBiasa PanelAccor;
    private javax.swing.JPanel PanelInput;
    private widget.TextBox Pemohon;
    private widget.TextBox SaksiKeluarga;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll5;
    private widget.ScrollPane Scroll6;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private javax.swing.JTabbedPane TabData;
    private widget.Tanggal TanggalLahir;
    private widget.TextBox TglLahir;
    private widget.Tanggal TglSurat;
    private widget.Button btnAmbil;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel10;
    private widget.Label jLabel100;
    private widget.Label jLabel101;
    private widget.Label jLabel11;
    private widget.Label jLabel13;
    private widget.Label jLabel14;
    private widget.Label jLabel19;
    private widget.Label jLabel21;
    private widget.Label jLabel37;
    private widget.Label jLabel38;
    private widget.Label jLabel39;
    private widget.Label jLabel42;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator3;
    private widget.Label label11;
    private widget.Label label14;
    private widget.Label label15;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            if(TCari.getText().trim().equals("")){
                ps=koneksi.prepareStatement(
                        "select surat_permintaan_second_opinion.no_pernyataan as no_surat,reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,"+
                        "surat_permintaan_second_opinion.tanggal as tanggal_surat,surat_permintaan_second_opinion.pembuat_pernyataan as pemohon,surat_permintaan_second_opinion.alamat_pembuat_pernyataan as alamat_pemohon,"+
                        "surat_permintaan_second_opinion.tgl_lahir_pembuat_pernyataan as tgl_lahir_pemohon,surat_permintaan_second_opinion.jk_pembuat_pernyataan as jk_pemohon,surat_permintaan_second_opinion.hubungan_pembuat_pernyataan as hubungan_pemohon,"+
                        "surat_permintaan_second_opinion.saksi_keluarga,surat_permintaan_second_opinion.kd_dokter,dokter.nm_dokter,surat_permintaan_second_opinion.nip,petugas.nama "+
                        "from surat_permintaan_second_opinion inner join reg_periksa on surat_permintaan_second_opinion.no_rawat=reg_periksa.no_rawat "+
                        "inner join pasien on pasien.no_rkm_medis=reg_periksa.no_rkm_medis "+
                        "inner join dokter on dokter.kd_dokter=surat_permintaan_second_opinion.kd_dokter "+
                        "inner join petugas on petugas.nip=surat_permintaan_second_opinion.nip where "+
                        "surat_permintaan_second_opinion.tanggal between ? and ? order by surat_permintaan_second_opinion.tanggal");
            }else{
                ps=koneksi.prepareStatement(
                        "select surat_permintaan_second_opinion.no_pernyataan as no_surat,reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,"+
                        "surat_permintaan_second_opinion.tanggal as tanggal_surat,surat_permintaan_second_opinion.pembuat_pernyataan as pemohon,surat_permintaan_second_opinion.alamat_pembuat_pernyataan as alamat_pemohon,"+
                        "surat_permintaan_second_opinion.tgl_lahir_pembuat_pernyataan as tgl_lahir_pemohon,surat_permintaan_second_opinion.jk_pembuat_pernyataan as jk_pemohon,surat_permintaan_second_opinion.hubungan_pembuat_pernyataan as hubungan_pemohon,"+
                        "surat_permintaan_second_opinion.saksi_keluarga,surat_permintaan_second_opinion.kd_dokter,dokter.nm_dokter,surat_permintaan_second_opinion.nip,petugas.nama "+
                        "from surat_permintaan_second_opinion inner join reg_periksa on surat_permintaan_second_opinion.no_rawat=reg_periksa.no_rawat "+
                        "inner join pasien on pasien.no_rkm_medis=reg_periksa.no_rkm_medis "+
                        "inner join dokter on dokter.kd_dokter=surat_permintaan_second_opinion.kd_dokter "+
                        "inner join petugas on petugas.nip=surat_permintaan_second_opinion.nip where "+
                        "surat_permintaan_second_opinion.tanggal between ? and ? and (surat_permintaan_second_opinion.no_pernyataan like ? or reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or "+
                        "surat_permintaan_second_opinion.kd_dokter like ? or dokter.nm_dokter like ?) order by surat_permintaan_second_opinion.tanggal");
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
                    ps.setString(8,"%"+TCari.getText()+"%");
                }   
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new Object[]{
                        rs.getString("no_surat"),rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getDate("tgl_lahir"),rs.getString("jk"),rs.getString("tanggal_surat"),
                        rs.getString("pemohon"),rs.getString("alamat_pemohon"),rs.getString("tgl_lahir_pemohon"),rs.getString("jk_pemohon"),
                        rs.getString("hubungan_pemohon"),rs.getString("saksi_keluarga"),rs.getString("kd_dokter"),rs.getString("nm_dokter"),rs.getString("nip"),rs.getString("nama")                
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
        HubunganPemohon.setSelectedIndex(0);
        TglSurat.setDate(new Date());
        HubunganPemohon.setSelectedIndex(0);
        Pemohon.setText("");
        AlamatPemohon.setText("");
        TanggalLahir.setDate(new Date());
        JKPemohon.setSelectedIndex(0);
        SaksiKeluarga.setText("");
        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(surat_permintaan_second_opinion.no_pernyataan,3),signed)),0) from surat_permintaan_second_opinion where surat_permintaan_second_opinion.tanggal='"+Valid.SetTgl(TglSurat.getSelectedItem()+"")+"' ",
                "SO"+TglSurat.getSelectedItem().toString().substring(6,10)+TglSurat.getSelectedItem().toString().substring(3,5)+TglSurat.getSelectedItem().toString().substring(0,2),3,NoSurat);
        NoSurat.requestFocus();
    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            NoSurat.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()); 
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString()); 
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString());
            Jk.setText(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString().replaceAll("L","Laki-laki").replaceAll("P","Perempuan")); 
            Pemohon.setText(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());
            AlamatPemohon.setText(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            if(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString().equals("L")){
                JKPemohon.setSelectedIndex(0);
            }else{
                JKPemohon.setSelectedIndex(1);
            }
            HubunganPemohon.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            SaksiKeluarga.setText(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            KdDokter.setText(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            NmDokter.setText(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            Valid.SetTgl2(TglSurat,tbObat.getValueAt(tbObat.getSelectedRow(),6).toString());
            Valid.SetTgl2(TanggalLahir,tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
        }
    }

    private void isRawat() {
        try {
            ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rkm_medis,pasien.nm_pasien, if(pasien.jk='L','Laki-laki','Perempuan') as jk,pasien.tgl_lahir,reg_periksa.tgl_registrasi, "+
                    "concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) asal,TIMESTAMPDIFF(YEAR, pasien.tgl_lahir, CURDATE()) as tahun "+
                    "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join kelurahan on pasien.kd_kel=kelurahan.kd_kel "+
                    "inner join kecamatan on pasien.kd_kec=kecamatan.kd_kec "+
                    "inner join kabupaten on pasien.kd_kab=kabupaten.kd_kab "+
                    "where reg_periksa.no_rawat=?");
            try {
                ps.setString(1,TNoRw.getText());
                rs=ps.executeQuery();
                if(rs.next()){
                    TNoRM.setText(rs.getString("no_rkm_medis"));
                    DTPCari1.setDate(rs.getDate("tgl_registrasi"));
                    TPasien.setText(rs.getString("nm_pasien"));
                    Jk.setText(rs.getString("jk"));
                    TglLahir.setText(rs.getString("tgl_lahir"));
                    Pemohon.setText(rs.getString("nm_pasien"));
                    AlamatPemohon.setText(rs.getString("asal"));
                    TanggalLahir.setDate(rs.getDate("tgl_lahir"));
                    JKPemohon.setSelectedItem(rs.getString("jk"));
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
        ChkInput.setSelected(true);
        isForm();
        runBackground(() ->tampil());
    }
    
    public void isCek(){
        BtnSimpan.setEnabled(akses.getsurat_permintaan_second_opinion());
        BtnHapus.setEnabled(akses.getsurat_permintaan_second_opinion());
        BtnEdit.setEnabled(akses.getsurat_permintaan_second_opinion());
        BtnEdit.setEnabled(akses.getsurat_permintaan_second_opinion());
        if(akses.getjml2()>=1){
            KdPerawat.setEditable(false);
            BtnPerawat.setEnabled(false);
            KdPerawat.setText(akses.getkode());
            NmPerawat.setText(Sequel.CariPetugas(akses.getkode()));
            if(NmPerawat.getText().equals("")){
                KdPerawat.setText("");
                JOptionPane.showMessageDialog(null,"User login bukan Petugas...!!");
            }
        }            
    }

    private void hapus() {
        if(Sequel.queryu2tf("delete from surat_permintaan_second_opinion where no_pernyataan=?",1,new String[]{
            tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
        })==true){
            Sequel.queryu2("delete from dpjp_ranap where no_rawat=? and kd_dokter=?",2,new String[]{TNoRw.getText(),tbObat.getValueAt(tbObat.getSelectedRow(),13).toString()});
            tabMode.removeRow(tbObat.getSelectedRow());
            LCount.setText(""+tabMode.getRowCount());
        }else{
            JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
        }
    }

    private void ganti() {
        if(Sequel.mengedittf("surat_permintaan_second_opinion","no_pernyataan=?","no_pernyataan=?,no_rawat=?,tanggal=?,kd_dokter=?,nip=?,pembuat_pernyataan=?,alamat_pembuat_pernyataan=?,"+
            "tgl_lahir_pembuat_pernyataan=?,jk_pembuat_pernyataan=?,hubungan_pembuat_pernyataan=?,saksi_keluarga=?",12,new String[]{
            NoSurat.getText(),TNoRw.getText(),Valid.SetTgl(TglSurat.getSelectedItem()+""),KdDokter.getText(),KdPerawat.getText(),Pemohon.getText(), 
                    AlamatPemohon.getText(),Valid.SetTgl(TanggalLahir.getSelectedItem()+""),JKPemohon.getSelectedItem().toString().substring(0,1), 
                    HubunganPemohon.getSelectedItem().toString(),SaksiKeluarga.getText(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
        })==true){
            Sequel.queryu2("delete from dpjp_ranap where no_rawat=? and kd_dokter=?",2,new String[]{TNoRw.getText(),tbObat.getValueAt(tbObat.getSelectedRow(),13).toString()});
            Sequel.menyimpan2("dpjp_ranap","?,?","Dokter",2,new String[]{TNoRw.getText(),KdDokter.getText()});
            tbObat.setValueAt(NoSurat.getText(),tbObat.getSelectedRow(),0);
            tbObat.setValueAt(TNoRw.getText(),tbObat.getSelectedRow(),1);
            tbObat.setValueAt(TNoRM.getText(),tbObat.getSelectedRow(),2);
            tbObat.setValueAt(TPasien.getText(),tbObat.getSelectedRow(),3);
            tbObat.setValueAt(TglLahir.getText(),tbObat.getSelectedRow(),4);
            tbObat.setValueAt(Jk.getText().substring(0,1),tbObat.getSelectedRow(),5);
            tbObat.setValueAt(Valid.SetTgl(TglSurat.getSelectedItem()+""),tbObat.getSelectedRow(),6);
            tbObat.setValueAt(Pemohon.getText(),tbObat.getSelectedRow(),7);
            tbObat.setValueAt(AlamatPemohon.getText(),tbObat.getSelectedRow(),8);
            tbObat.setValueAt(Valid.SetTgl(TanggalLahir.getSelectedItem()+""),tbObat.getSelectedRow(),9);
            tbObat.setValueAt(JKPemohon.getSelectedItem().toString().substring(0,1),tbObat.getSelectedRow(),10);
            tbObat.setValueAt(HubunganPemohon.getSelectedItem().toString(),tbObat.getSelectedRow(),11);
            tbObat.setValueAt(SaksiKeluarga.getText(),tbObat.getSelectedRow(),12);
            tbObat.setValueAt(KdDokter.getText(),tbObat.getSelectedRow(),13);
            tbObat.setValueAt(NmDokter.getText(),tbObat.getSelectedRow(),14);
            tbObat.setValueAt(KdPerawat.getText(),tbObat.getSelectedRow(),15);
            tbObat.setValueAt(NmPerawat.getText(),tbObat.getSelectedRow(),16);
            emptTeks();
        }
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,225));
            FormInput.setVisible(true);      
            ChkInput.setVisible(true);
        }else if(ChkInput.isSelected()==false){           
            ChkInput.setVisible(false);            
            PanelInput.setPreferredSize(new Dimension(WIDTH,20));
            FormInput.setVisible(false);      
            ChkInput.setVisible(true);
        }
    }
    
    private void isPhoto(){
        if(ChkAccor.isSelected()==true){
            ChkAccor.setVisible(false);
            PanelAccor.setPreferredSize(new Dimension(430,HEIGHT));
            FormPhoto.setVisible(true);  
            ChkAccor.setVisible(true);
        }else if(ChkAccor.isSelected()==false){    
            ChkAccor.setVisible(false);
            PanelAccor.setPreferredSize(new Dimension(15,HEIGHT));
            FormPhoto.setVisible(false);  
            ChkAccor.setVisible(true);
        }
    }

    private String getSecondOpinionPhotoURL(String relativePath){
        if(relativePath==null || relativePath.trim().equals("")){
            return "";
        }

        String cleanedPath = relativePath.replace("\\", "/");
        String[] localRoots = {
            "/Applications/XAMPP/xamppfiles/htdocs",
            "/opt/lampp/htdocs",
            "C:/xampp/htdocs",
            "D:/xampp/htdocs"
        };

        for(String root : localRoots){
            File localFile = new File(root, koneksiDB.HYBRIDWEB()+"/secondopinion/"+cleanedPath);
            if(localFile.exists()){
                return localFile.toURI().toString();
            }
        }

        return "http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/secondopinion/"+cleanedPath;
    }

    private String getSecondOpinionPhotoHTML(String relativePath){
        String photoURL = getSecondOpinionPhotoURL(relativePath);
        if(photoURL.equals("")){
            return "<html><body><center><br><br><font face='tahoma' size='2' color='#434343'>Kosong</font></center></body></html>";
        }
        return "<html><body><center><img src='"+photoURL+"' alt='photo' width='450' height='550'/></center></body></html>";
    }

    private void setPreviewPhoto(widget.editorpane targetPane, String relativePath){
        targetPane.setText(getSecondOpinionPhotoHTML(relativePath));
        targetPane.setCaretPosition(0);
    }

    private void panggilPhoto() {
        if(FormPhoto.isVisible()==true){
            lokasifile="";
            try {
                ps=koneksi.prepareStatement("select bukti_surat_permintaan_second_opinion.photo from bukti_surat_permintaan_second_opinion where bukti_surat_permintaan_second_opinion.no_pernyataan=?");
                try {
                    ps.setString(1,tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
                    rs=ps.executeQuery();
                    if(rs.next()){
                        if(rs.getString("photo").equals("")||rs.getString("photo").equals("-")){
                            lokasifile="";
                        }else{
                            lokasifile=rs.getString("photo");
                        }  
                    }else{
                        lokasifile="";
                    }
                } catch (Exception e) {
                    lokasifile="";
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

            lokasifile2="";
            try {
                ps=koneksi.prepareStatement("select bukti_surat_permintaan_second_opinion_saksikeluarga.photo from bukti_surat_permintaan_second_opinion_saksikeluarga where bukti_surat_permintaan_second_opinion_saksikeluarga.no_pernyataan=?");
                try {
                    ps.setString(1,tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
                    rs=ps.executeQuery();
                    if(rs.next()){
                        if(rs.getString("photo").equals("")||rs.getString("photo").equals("-")){
                            lokasifile2="";
                        }else{
                            lokasifile2=rs.getString("photo");
                        }  
                    }else{
                        lokasifile2="";
                    }
                } catch (Exception e) {
                    lokasifile2="";
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
            
            if(TabData.getSelectedIndex()==0){
                if(lokasifile.equals("")){
                    setPreviewPhoto(LoadHTML2,"");
                }else{
                    setPreviewPhoto(LoadHTML2,lokasifile);
                } 
            }else{
                if(lokasifile2.equals("")){
                    setPreviewPhoto(LoadHTML3,"");
                }else{
                    setPreviewPhoto(LoadHTML3,lokasifile2);
                } 
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

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


package rekammedis;

import fungsi.WarnaTable;
import fungsi.akses;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
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
public final class RMHasilPersalinanTOLAC extends javax.swing.JDialog {
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
    private String finger="";
    private StringBuilder htmlContent;
    private String TANGGALMUNDUR="yes",pilihan="";
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public RMHasilPersalinanTOLAC(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(628,674);

        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.RM","Nama Pasien","Tgl.Lahir","Kode Dokter","Nama Dokter","Tgl/Jam Lahir Bayi",
            "Awitan","Augmentasi","CTG Kontinu","Cara Persalinan","Indikasi Gagal","Kala II (menit)","Ruptur Uteri",
            "Perdarahan (ml)","Transfusi","Histerektomi","Komplikasi Lain","Luaran Perinatal","BB Lahir (gram)","APGAR 1",
            "APGAR 5","HIE","Masuk NICU","Keterangan"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 25; i++) {
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
                column.setPreferredWidth(80);
            }else if(i==5){
                column.setPreferredWidth(150);
            }else if(i==6){
                column.setPreferredWidth(120);
            }else if(i==7){
                column.setPreferredWidth(70);
            }else if(i==8){
                column.setPreferredWidth(75);
            }else if(i==9){
                column.setPreferredWidth(75);
            }else if(i==10){
                column.setPreferredWidth(140);
            }else if(i==11){
                column.setPreferredWidth(200);
            }else if(i==12){
                column.setPreferredWidth(75);
            }else if(i==13){
                column.setPreferredWidth(95);
            }else if(i==14){
                column.setPreferredWidth(80);
            }else if(i==15){
                column.setPreferredWidth(65);
            }else if(i==16){
                column.setPreferredWidth(80);
            }else if(i==17){
                column.setPreferredWidth(200);
            }else if(i==18){
                column.setPreferredWidth(120);
            }else if(i==19){
                column.setPreferredWidth(65);
            }else if(i==20){
                column.setPreferredWidth(50);
            }else if(i==21){
                column.setPreferredWidth(50);
            }else if(i==22){
                column.setPreferredWidth(35);
            }else if(i==23){
                column.setPreferredWidth(75);
            }else if(i==24){
                column.setPreferredWidth(170);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());

        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        KdDokter.setDocument(new batasInput((byte)20).getKata(KdDokter));
        TCari.setDocument(new batasInput((int)100).getKata(TCari));
        IndikasiGagal.setDocument(new batasInput((int)255).getKata(IndikasiGagal));
        LamaKala2.setDocument(new batasInput((byte)3).getOnlyAngka(LamaKala2));
        JumlahPerdarahan.setDocument(new batasInput((byte)5).getOnlyAngka(JumlahPerdarahan));
        KomplikasiLain.setDocument(new batasInput((int)255).getKata(KomplikasiLain));
        BBLahir.setDocument(new batasInput((byte)4).getOnlyAngka(BBLahir));
        APGAR1.setDocument(new batasInput((byte)2).getOnlyAngka(APGAR1));
        APGAR5.setDocument(new batasInput((byte)2).getOnlyAngka(APGAR5));
        Keterangan.setDocument(new batasInput((int)200).getKata(Keterangan));
        
        ChkInput.setSelected(false);
        isForm();
        
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
        
        jam();
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new widget.PopupMenu();
        MnHasilPersalinanTOLAC = new widget.MenuItem();
        LoadHTML = new widget.editorpane();
        TanggalRegistrasi = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbObat = new widget.Table();
        jPanel3 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        BtnPrint = new widget.Button();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();
        BtnKeluar = new widget.Button();
        panelGlass9 = new widget.panelisi();
        jLabel19 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel21 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        PanelInput = new javax.swing.JPanel();
        ChkInput = new widget.CekBox();
        scrollInput = new widget.ScrollPane();
        FormInput = new widget.PanelBiasa();
        jLabel4 = new widget.Label();
        TNoRw = new widget.TextBox();
        TPasien = new widget.TextBox();
        Tanggal = new widget.Tanggal();
        TNoRM = new widget.TextBox();
        jLabel16 = new widget.Label();
        Jam = new widget.ComboBox();
        Menit = new widget.ComboBox();
        Detik = new widget.ComboBox();
        ChkKejadian = new widget.CekBox();
        jLabel18 = new widget.Label();
        KdDokter = new widget.TextBox();
        NmDokter = new widget.TextBox();
        BtnDokter = new widget.Button();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel8 = new widget.Label();
        TglLahir = new widget.TextBox();
        jLabel27 = new widget.Label();
        jLabel5 = new widget.Label();
        CTGContinu = new widget.ComboBox();
        Awitan = new widget.ComboBox();
        Augmentasi = new widget.ComboBox();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel109 = new widget.Label();
        jLabel13 = new widget.Label();
        LuaranPerinatal = new widget.ComboBox();
        jLabel36 = new widget.Label();
        IndikasiGagal = new widget.TextBox();
        jLabel28 = new widget.Label();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel110 = new widget.Label();
        jLabel20 = new widget.Label();
        jLabel37 = new widget.Label();
        BBLahir = new widget.TextBox();
        jLabel33 = new widget.Label();
        jLabel9 = new widget.Label();
        jLabel10 = new widget.Label();
        jLabel11 = new widget.Label();
        CaraPersalinan = new widget.ComboBox();
        jLabel12 = new widget.Label();
        jLabel22 = new widget.Label();
        jLabel38 = new widget.Label();
        LamaKala2 = new widget.TextBox();
        jLabel14 = new widget.Label();
        jLabel111 = new widget.Label();
        jLabel15 = new widget.Label();
        jLabel23 = new widget.Label();
        RupturUteri = new widget.ComboBox();
        jLabel39 = new widget.Label();
        JumlahPerdarahan = new widget.TextBox();
        jLabel17 = new widget.Label();
        jLabel24 = new widget.Label();
        Tranfusi = new widget.ComboBox();
        jLabel25 = new widget.Label();
        HisterokTomi = new widget.ComboBox();
        jLabel26 = new widget.Label();
        jLabel29 = new widget.Label();
        KomplikasiLain = new widget.TextBox();
        jLabel30 = new widget.Label();
        APGAR5 = new widget.TextBox();
        APGAR1 = new widget.TextBox();
        jLabel31 = new widget.Label();
        MasukNICU = new widget.ComboBox();
        Keterangan = new widget.TextBox();
        jLabel40 = new widget.Label();
        jLabel34 = new widget.Label();
        HIE = new widget.ComboBox();
        jLabel32 = new widget.Label();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnHasilPersalinanTOLAC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHasilPersalinanTOLAC.setText("Formulir Resume Hasil Persalinan TOLAC");
        MnHasilPersalinanTOLAC.setName("MnHasilPersalinanTOLAC"); // NOI18N
        MnHasilPersalinanTOLAC.setPreferredSize(new java.awt.Dimension(260, 26));
        MnHasilPersalinanTOLAC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHasilPersalinanTOLACActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnHasilPersalinanTOLAC);

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N

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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Resume Hasil Persalinan TOLAC ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setPreferredSize(new java.awt.Dimension(462, 1300));
        internalFrame1.setRequestFocusEnabled(false);
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

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

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(44, 100));
        jPanel3.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 44));
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

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass8.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass8.add(LCount);

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

        jPanel3.add(panelGlass8, java.awt.BorderLayout.CENTER);

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel19.setText("Tanggal :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(60, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "12-10-2026" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass9.add(DTPCari1);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("s.d.");
        jLabel21.setName("jLabel21"); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(23, 23));
        panelGlass9.add(jLabel21);

        DTPCari2.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "12-10-2026" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass9.add(DTPCari2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(310, 23));
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

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('M');
        BtnAll.setToolTipText("Alt+M");
        BtnAll.setName("BtnAll"); // NOI18N
        BtnAll.setPreferredSize(new java.awt.Dimension(28, 23));
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
        panelGlass9.add(BtnAll);

        jPanel3.add(panelGlass9, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 336));
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

        scrollInput.setName("scrollInput"); // NOI18N
        scrollInput.setPreferredSize(new java.awt.Dimension(102, 557));

        FormInput.setBackground(new java.awt.Color(250, 255, 245));
        FormInput.setBorder(null);
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(100, 313));
        FormInput.setLayout(null);

        jLabel4.setText("No.Rawat :");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(0, 10, 75, 23);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(79, 10, 141, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        TPasien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPasienKeyPressed(evt);
            }
        });
        FormInput.add(TPasien);
        TPasien.setBounds(336, 10, 285, 23);

        Tanggal.setForeground(new java.awt.Color(50, 70, 50));
        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "12-10-2026" }));
        Tanggal.setDisplayFormat("dd-MM-yyyy");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeyPressed(evt);
            }
        });
        FormInput.add(Tanggal);
        Tanggal.setBounds(79, 40, 90, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        FormInput.add(TNoRM);
        TNoRM.setBounds(222, 10, 112, 23);

        jLabel16.setText("Tanggal :");
        jLabel16.setName("jLabel16"); // NOI18N
        jLabel16.setVerifyInputWhenFocusTarget(false);
        FormInput.add(jLabel16);
        jLabel16.setBounds(0, 40, 75, 23);

        Jam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        Jam.setName("Jam"); // NOI18N
        Jam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JamKeyPressed(evt);
            }
        });
        FormInput.add(Jam);
        Jam.setBounds(173, 40, 62, 23);

        Menit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        Menit.setName("Menit"); // NOI18N
        Menit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MenitKeyPressed(evt);
            }
        });
        FormInput.add(Menit);
        Menit.setBounds(238, 40, 62, 23);

        Detik.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        Detik.setName("Detik"); // NOI18N
        Detik.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DetikKeyPressed(evt);
            }
        });
        FormInput.add(Detik);
        Detik.setBounds(303, 40, 62, 23);

        ChkKejadian.setBorder(null);
        ChkKejadian.setSelected(true);
        ChkKejadian.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkKejadian.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkKejadian.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkKejadian.setName("ChkKejadian"); // NOI18N
        FormInput.add(ChkKejadian);
        ChkKejadian.setBounds(368, 40, 23, 23);

        jLabel18.setText("Dokter :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(400, 40, 70, 23);

        KdDokter.setEditable(false);
        KdDokter.setHighlighter(null);
        KdDokter.setName("KdDokter"); // NOI18N
        FormInput.add(KdDokter);
        KdDokter.setBounds(474, 40, 94, 23);

        NmDokter.setEditable(false);
        NmDokter.setName("NmDokter"); // NOI18N
        FormInput.add(NmDokter);
        NmDokter.setBounds(570, 40, 187, 23);

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
        BtnDokter.setBounds(761, 40, 28, 23);

        jSeparator1.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator1.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator1.setName("jSeparator1"); // NOI18N
        FormInput.add(jSeparator1);
        jSeparator1.setBounds(0, 150, 807, 1);

        jLabel8.setText("Tgl.Lahir :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(625, 10, 60, 23);

        TglLahir.setEditable(false);
        TglLahir.setHighlighter(null);
        TglLahir.setName("TglLahir"); // NOI18N
        FormInput.add(TglLahir);
        TglLahir.setBounds(689, 10, 100, 23);

        jLabel27.setText(":");
        jLabel27.setName("jLabel27"); // NOI18N
        FormInput.add(jLabel27);
        jLabel27.setBounds(0, 700, 117, 23);

        jLabel5.setText("CTG Kontinu :");
        jLabel5.setName("jLabel5"); // NOI18N
        FormInput.add(jLabel5);
        jLabel5.setBounds(353, 90, 80, 23);

        CTGContinu.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        CTGContinu.setName("CTGContinu"); // NOI18N
        CTGContinu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CTGContinuKeyPressed(evt);
            }
        });
        FormInput.add(CTGContinu);
        CTGContinu.setBounds(437, 90, 70, 23);

        Awitan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Spontan", "Induksi" }));
        Awitan.setName("Awitan"); // NOI18N
        Awitan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AwitanKeyPressed(evt);
            }
        });
        FormInput.add(Awitan);
        Awitan.setBounds(75, 90, 85, 23);

        Augmentasi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Augmentasi.setName("Augmentasi"); // NOI18N
        Augmentasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AugmentasiKeyPressed(evt);
            }
        });
        FormInput.add(Augmentasi);
        Augmentasi.setBounds(264, 90, 70, 23);

        jSeparator5.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator5.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator5.setName("jSeparator5"); // NOI18N
        FormInput.add(jSeparator5);
        jSeparator5.setBounds(0, 230, 807, 1);

        jLabel109.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel109.setText("KONDISI BAYI");
        jLabel109.setName("jLabel109"); // NOI18N
        FormInput.add(jLabel109);
        jLabel109.setBounds(10, 230, 490, 23);

        jLabel13.setText("Keterangan :");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(205, 280, 70, 23);

        LuaranPerinatal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Hidup", "Lahir Mati", "Meninggal Neonatal" }));
        LuaranPerinatal.setName("LuaranPerinatal"); // NOI18N
        LuaranPerinatal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LuaranPerinatalKeyPressed(evt);
            }
        });
        FormInput.add(LuaranPerinatal);
        LuaranPerinatal.setBounds(121, 250, 140, 23);

        jLabel36.setText("APGAR Score :");
        jLabel36.setName("jLabel36"); // NOI18N
        FormInput.add(jLabel36);
        jLabel36.setBounds(461, 250, 90, 23);

        IndikasiGagal.setHighlighter(null);
        IndikasiGagal.setName("IndikasiGagal"); // NOI18N
        IndikasiGagal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                IndikasiGagalKeyPressed(evt);
            }
        });
        FormInput.add(IndikasiGagal);
        IndikasiGagal.setBounds(110, 120, 490, 23);

        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel28.setText("/");
        jLabel28.setName("jLabel28"); // NOI18N
        FormInput.add(jLabel28);
        jLabel28.setBounds(597, 250, 11, 23);

        jSeparator2.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator2.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator2.setName("jSeparator2"); // NOI18N
        FormInput.add(jSeparator2);
        jSeparator2.setBounds(0, 70, 807, 1);

        jLabel110.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel110.setText("PERSALINAN");
        jLabel110.setName("jLabel110"); // NOI18N
        FormInput.add(jLabel110);
        jLabel110.setBounds(10, 70, 200, 23);

        jLabel20.setText(":");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(0, 90, 71, 23);

        jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel37.setText("Luaran Perinatal");
        jLabel37.setName("jLabel37"); // NOI18N
        FormInput.add(jLabel37);
        jLabel37.setBounds(30, 250, 90, 23);

        BBLahir.setHighlighter(null);
        BBLahir.setName("BBLahir"); // NOI18N
        BBLahir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BBLahirKeyPressed(evt);
            }
        });
        FormInput.add(BBLahir);
        BBLahir.setBounds(359, 250, 45, 23);

        jLabel33.setText(":");
        jLabel33.setName("jLabel33"); // NOI18N
        FormInput.add(jLabel33);
        jLabel33.setBounds(0, 250, 117, 23);

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel9.setText("menit");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(760, 120, 50, 23);

        jLabel10.setText("Augmentasi :");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(180, 90, 80, 23);

        jLabel11.setText("Cara Persalinan :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(515, 90, 110, 23);

        CaraPersalinan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "VBAC Berhasil", "Gagal TOLAC - SC Ulang" }));
        CaraPersalinan.setName("CaraPersalinan"); // NOI18N
        CaraPersalinan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CaraPersalinanKeyPressed(evt);
            }
        });
        FormInput.add(CaraPersalinan);
        CaraPersalinan.setBounds(629, 90, 160, 23);

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel12.setText("Indikasi Gagal");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(30, 120, 90, 23);

        jLabel22.setText(":");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(0, 120, 106, 23);

        jLabel38.setText("Lama Kala II :");
        jLabel38.setName("jLabel38"); // NOI18N
        FormInput.add(jLabel38);
        jLabel38.setBounds(628, 120, 80, 23);

        LamaKala2.setHighlighter(null);
        LamaKala2.setName("LamaKala2"); // NOI18N
        LamaKala2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LamaKala2KeyPressed(evt);
            }
        });
        FormInput.add(LamaKala2);
        LamaKala2.setBounds(712, 120, 45, 23);

        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel14.setText("Awitan");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(30, 90, 50, 23);

        jLabel111.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel111.setText("KOMPLIKASI IBU");
        jLabel111.setName("jLabel111"); // NOI18N
        FormInput.add(jLabel111);
        jLabel111.setBounds(10, 150, 200, 23);

        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel15.setText("Ruptur Uteri");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(30, 170, 70, 23);

        jLabel23.setText(":");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(0, 170, 97, 23);

        RupturUteri.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada", "Dehisensi", "Ruptur Komplit" }));
        RupturUteri.setName("RupturUteri"); // NOI18N
        RupturUteri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RupturUteriKeyPressed(evt);
            }
        });
        FormInput.add(RupturUteri);
        RupturUteri.setBounds(101, 170, 120, 23);

        jLabel39.setText("Jumlah Perdarahan :");
        jLabel39.setName("jLabel39"); // NOI18N
        FormInput.add(jLabel39);
        jLabel39.setBounds(233, 170, 130, 23);

        JumlahPerdarahan.setHighlighter(null);
        JumlahPerdarahan.setName("JumlahPerdarahan"); // NOI18N
        JumlahPerdarahan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JumlahPerdarahanKeyPressed(evt);
            }
        });
        FormInput.add(JumlahPerdarahan);
        JumlahPerdarahan.setBounds(367, 170, 55, 23);

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel17.setText("ml");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(425, 170, 30, 23);

        jLabel24.setText("Transfusi :");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(452, 170, 80, 23);

        Tranfusi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Tranfusi.setName("Tranfusi"); // NOI18N
        Tranfusi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TranfusiKeyPressed(evt);
            }
        });
        FormInput.add(Tranfusi);
        Tranfusi.setBounds(536, 170, 70, 23);

        jLabel25.setText("Histerektomi :");
        jLabel25.setName("jLabel25"); // NOI18N
        FormInput.add(jLabel25);
        jLabel25.setBounds(635, 170, 80, 23);

        HisterokTomi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        HisterokTomi.setName("HisterokTomi"); // NOI18N
        HisterokTomi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HisterokTomiKeyPressed(evt);
            }
        });
        FormInput.add(HisterokTomi);
        HisterokTomi.setBounds(719, 170, 70, 23);

        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel26.setText("Komplikasi Lain");
        jLabel26.setName("jLabel26"); // NOI18N
        FormInput.add(jLabel26);
        jLabel26.setBounds(30, 200, 90, 23);

        jLabel29.setText(":");
        jLabel29.setName("jLabel29"); // NOI18N
        FormInput.add(jLabel29);
        jLabel29.setBounds(0, 200, 113, 23);

        KomplikasiLain.setHighlighter(null);
        KomplikasiLain.setName("KomplikasiLain"); // NOI18N
        KomplikasiLain.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KomplikasiLainKeyPressed(evt);
            }
        });
        FormInput.add(KomplikasiLain);
        KomplikasiLain.setBounds(117, 200, 672, 23);

        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel30.setText("gram");
        jLabel30.setName("jLabel30"); // NOI18N
        FormInput.add(jLabel30);
        jLabel30.setBounds(407, 250, 30, 23);

        APGAR5.setHighlighter(null);
        APGAR5.setName("APGAR5"); // NOI18N
        APGAR5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                APGAR5KeyPressed(evt);
            }
        });
        FormInput.add(APGAR5);
        APGAR5.setBounds(608, 250, 40, 23);

        APGAR1.setHighlighter(null);
        APGAR1.setName("APGAR1"); // NOI18N
        APGAR1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                APGAR1KeyPressed(evt);
            }
        });
        FormInput.add(APGAR1);
        APGAR1.setBounds(555, 250, 40, 23);

        jLabel31.setText("HIE :");
        jLabel31.setName("jLabel31"); // NOI18N
        FormInput.add(jLabel31);
        jLabel31.setBounds(665, 250, 50, 23);

        MasukNICU.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        MasukNICU.setName("MasukNICU"); // NOI18N
        MasukNICU.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MasukNICUKeyPressed(evt);
            }
        });
        FormInput.add(MasukNICU);
        MasukNICU.setBounds(99, 280, 70, 23);

        Keterangan.setHighlighter(null);
        Keterangan.setName("Keterangan"); // NOI18N
        Keterangan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKeyPressed(evt);
            }
        });
        FormInput.add(Keterangan);
        Keterangan.setBounds(279, 280, 510, 23);

        jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel40.setText("Masuk NICU");
        jLabel40.setName("jLabel40"); // NOI18N
        FormInput.add(jLabel40);
        jLabel40.setBounds(30, 280, 90, 23);

        jLabel34.setText(":");
        jLabel34.setName("jLabel34"); // NOI18N
        FormInput.add(jLabel34);
        jLabel34.setBounds(0, 280, 95, 23);

        HIE.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        HIE.setName("HIE"); // NOI18N
        HIE.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HIEKeyPressed(evt);
            }
        });
        FormInput.add(HIE);
        HIE.setBounds(719, 250, 70, 23);

        jLabel32.setText("BB Lahir :");
        jLabel32.setName("jLabel32"); // NOI18N
        FormInput.add(jLabel32);
        jLabel32.setBounds(285, 250, 70, 23);

        scrollInput.setViewportView(FormInput);

        PanelInput.add(scrollInput, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isRawat();
        }else{            
            Valid.pindah(evt,TCari,Tanggal);
        }
}//GEN-LAST:event_TNoRwKeyPressed

    private void TPasienKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TPasienKeyPressed
        Valid.pindah(evt,TCari,BtnSimpan);
}//GEN-LAST:event_TPasienKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"Pasien");
        }else if(KdDokter.getText().trim().equals("")||NmDokter.getText().trim().equals("")){
            Valid.textKosong(KdDokter,"Dokter");
        }else if(CaraPersalinan.getSelectedItem().toString().equals("Gagal TOLAC - SC Ulang")&&IndikasiGagal.getText().trim().equals("")){
            Valid.textKosong(IndikasiGagal,"Indikasi Gagal TOLAC");
        }else if(LuaranPerinatal.getSelectedItem().toString().equals("Hidup")&&BBLahir.getText().trim().equals("")){
            Valid.textKosong(BBLahir,"BB Lahir");
        }else if(LuaranPerinatal.getSelectedItem().toString().equals("Hidup")&&APGAR1.getText().trim().equals("")){
            Valid.textKosong(APGAR1,"APGAR 1 menit");
        }else if(LuaranPerinatal.getSelectedItem().toString().equals("Hidup")&&APGAR5.getText().trim().equals("")){
            Valid.textKosong(APGAR5,"APGAR 5 menit");
        }else if((!APGAR1.getText().trim().equals("")&&Integer.parseInt(APGAR1.getText().trim())>10)||(!APGAR5.getText().trim().equals("")&&Integer.parseInt(APGAR5.getText().trim())>10)){
            JOptionPane.showMessageDialog(rootPane,"Nilai APGAR hanya 0 sampai 10..!!");
            APGAR1.requestFocus();
        }else{
            if(akses.getkode().equals("Admin Utama")){
                simpan();
            }else{
                if(TanggalRegistrasi.getText().equals("")){
                    TanggalRegistrasi.setText(Sequel.cariIsi("select concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) from reg_periksa where reg_periksa.no_rawat=?",TNoRw.getText()));
                }
                if(Sequel.cekTanggalRegistrasi(TanggalRegistrasi.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem())==true){
                    simpan();
                }
            } 
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
           Valid.pindah(evt,Keterangan,BtnBatal);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        ChkInput.setSelected(true);
        isForm();  
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
                if(KdDokter.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString())){
                    if(Sequel.cekTanggal48jam(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString(),Sequel.ambiltanggalsekarang())==true){
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
        if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"Pasien");
        }else if(KdDokter.getText().trim().equals("")||NmDokter.getText().trim().equals("")){
            Valid.textKosong(KdDokter,"Dokter");
        }else if(CaraPersalinan.getSelectedItem().toString().equals("Gagal TOLAC - SC Ulang")&&IndikasiGagal.getText().trim().equals("")){
            Valid.textKosong(IndikasiGagal,"Indikasi Gagal TOLAC");
        }else if(LuaranPerinatal.getSelectedItem().toString().equals("Hidup")&&BBLahir.getText().trim().equals("")){
            Valid.textKosong(BBLahir,"BB Lahir");
        }else if(LuaranPerinatal.getSelectedItem().toString().equals("Hidup")&&APGAR1.getText().trim().equals("")){
            Valid.textKosong(APGAR1,"APGAR 1 menit");
        }else if(LuaranPerinatal.getSelectedItem().toString().equals("Hidup")&&APGAR5.getText().trim().equals("")){
            Valid.textKosong(APGAR5,"APGAR 5 menit");
        }else if((!APGAR1.getText().trim().equals("")&&Integer.parseInt(APGAR1.getText().trim())>10)||(!APGAR5.getText().trim().equals("")&&Integer.parseInt(APGAR5.getText().trim())>10)){
            JOptionPane.showMessageDialog(rootPane,"Nilai APGAR hanya 0 sampai 10..!!");
            APGAR1.requestFocus();
        }else{
            if(tbObat.getSelectedRow()>-1){
                if(akses.getkode().equals("Admin Utama")){
                    ganti();
                }else{
                    if(KdDokter.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString())){
                        if(Sequel.cekTanggal48jam(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString(),Sequel.ambiltanggalsekarang())==true){
                            if(TanggalRegistrasi.getText().equals("")){
                                TanggalRegistrasi.setText(Sequel.cariIsi("select concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) from reg_periksa where reg_periksa.no_rawat=?",TNoRw.getText()));
                            }
                            if(Sequel.cekTanggalRegistrasi(TanggalRegistrasi.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem())==true){
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
                
                pilihan =(String) JOptionPane.showInputDialog(null,"Silahkan pilih laporan..!","Pilihan Cetak",JOptionPane.QUESTION_MESSAGE,null,new Object[]{"Laporan 1 (HTML)","Laporan 2 (WPS)","Laporan 3 (CSV)"},"Laporan 1 (HTML)");
                switch (pilihan) {
                    case "Laporan 1 (HTML)":
                            htmlContent = new StringBuilder();
                            htmlContent.append(                             
                                "<tr class='isi'>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Rawat</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.RM</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Pasien</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tgl.Lahir</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kode Dokter</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Dokter</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tgl/Jam Lahir Bayi</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Awitan</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Augmentasi</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>CTG Kontinu</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Cara Persalinan</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Indikasi Gagal</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kala II (menit)</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Ruptur Uteri</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Perdarahan (ml)</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Transfusi</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Histerektomi</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Komplikasi Lain</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Luaran Perinatal</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>BB Lahir (gram)</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>APGAR 1</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>APGAR 5</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>HIE</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Masuk NICU</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan</b></td>"+
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
                                    "</tr>");
                            }
                            LoadHTML.setText(
                                "<html>"+
                                  "<table width='2700px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
                                   htmlContent.toString()+
                                  "</table>"+
                                "</html>"
                            );

                            f = new File("DataHasilPersalinanTOLAC.html");            
                            bw = new BufferedWriter(new FileWriter(f));            
                            bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                                        "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                                        "<table width='2700px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                            "<tr class='isi2'>"+
                                                "<td valign='top' align='center'>"+
                                                    "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                                    akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                                    akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                                    "<font size='2' face='Tahoma'>DATA HASIL PERSALINAN TOLAC<br><br></font>"+        
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
                                "<tr class='isi'>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Rawat</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.RM</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Pasien</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tgl.Lahir</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kode Dokter</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Dokter</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tgl/Jam Lahir Bayi</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Awitan</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Augmentasi</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>CTG Kontinu</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Cara Persalinan</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Indikasi Gagal</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kala II (menit)</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Ruptur Uteri</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Perdarahan (ml)</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Transfusi</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Histerektomi</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Komplikasi Lain</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Luaran Perinatal</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>BB Lahir (gram)</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>APGAR 1</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>APGAR 5</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>HIE</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Masuk NICU</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan</b></td>"+
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
                                    "</tr>");
                            }
                            LoadHTML.setText(
                                "<html>"+
                                  "<table width='2700px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
                                   htmlContent.toString()+
                                  "</table>"+
                                "</html>"
                            );

                            f = new File("DataHasilPersalinanTOLAC.wps");            
                            bw = new BufferedWriter(new FileWriter(f));            
                            bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                                        "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                                        "<table width='2700px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                            "<tr class='isi2'>"+
                                                "<td valign='top' align='center'>"+
                                                    "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                                    akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                                    akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                                    "<font size='2' face='Tahoma'>DATA HASIL PERSALINAN TOLAC<br><br></font>"+        
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
                                "\"No.Rawat\";\"No.RM\";\"Nama Pasien\";\"Tgl.Lahir\";\"Kode Dokter\";\"Nama Dokter\";\"Tgl/Jam Lahir Bayi\";\"Awitan\";\"Augmentasi\";\"CTG Kontinu\";\"Cara Persalinan\";\"Indikasi Gagal\";\"Kala II (menit)\";\"Ruptur Uteri\";\"Perdarahan (ml)\";\"Transfusi\";\"Histerektomi\";\"Komplikasi Lain\";\"Luaran Perinatal\";\"BB Lahir (gram)\";\"APGAR 1\";\"APGAR 5\";\"HIE\";\"Masuk NICU\";\"Keterangan\"\n"
                            ); 
                            for (i = 0; i < tabMode.getRowCount(); i++) {
                                htmlContent.append(
                                    "\""+tbObat.getValueAt(i,0).toString()+"\";\""+tbObat.getValueAt(i,1).toString()+"\";\""+tbObat.getValueAt(i,2).toString()+"\";\""+tbObat.getValueAt(i,3).toString()+"\";\""+tbObat.getValueAt(i,4).toString()+"\";\""+tbObat.getValueAt(i,5).toString()+"\";\""+tbObat.getValueAt(i,6).toString()+"\";\""+tbObat.getValueAt(i,7).toString()+"\";\""+tbObat.getValueAt(i,8).toString()+"\";\""+tbObat.getValueAt(i,9).toString()+"\";\""+tbObat.getValueAt(i,10).toString()+"\";\""+tbObat.getValueAt(i,11).toString()+"\";\""+tbObat.getValueAt(i,12).toString()+"\";\""+tbObat.getValueAt(i,13).toString()+"\";\""+tbObat.getValueAt(i,14).toString()+"\";\""+tbObat.getValueAt(i,15).toString()+"\";\""+tbObat.getValueAt(i,16).toString()+"\";\""+tbObat.getValueAt(i,17).toString()+"\";\""+tbObat.getValueAt(i,18).toString()+"\";\""+tbObat.getValueAt(i,19).toString()+"\";\""+tbObat.getValueAt(i,20).toString()+"\";\""+tbObat.getValueAt(i,21).toString()+"\";\""+tbObat.getValueAt(i,22).toString()+"\";\""+tbObat.getValueAt(i,23).toString()+"\";\""+tbObat.getValueAt(i,24).toString()+"\"\n"
                                );
                            }
                            f = new File("DataHasilPersalinanTOLAC.csv");            
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
            runBackground(() ->tampil());
            TCari.setText("");
        }else{
            Valid.pindah(evt, BtnCari, TPasien);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void TanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKeyPressed
        Valid.pindah(evt,TCari,Jam);
}//GEN-LAST:event_TanggalKeyPressed

    private void tbObatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbObatMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
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

    private void JamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JamKeyPressed
        Valid.pindah(evt,Tanggal,Menit);
    }//GEN-LAST:event_JamKeyPressed

    private void MenitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MenitKeyPressed
        Valid.pindah(evt,Jam,Detik);
    }//GEN-LAST:event_MenitKeyPressed

    private void DetikKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DetikKeyPressed
        Valid.pindah(evt,Menit,BtnDokter);
    }//GEN-LAST:event_DetikKeyPressed

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

    private void BtnDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokterKeyPressed
        Valid.pindah(evt,Detik,Awitan);
    }//GEN-LAST:event_BtnDokterKeyPressed

    private void MnHasilPersalinanTOLACActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHasilPersalinanTOLACActionPerformed
        if(tbObat.getSelectedRow()>-1){
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());   
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",tbObat.getValueAt(tbObat.getSelectedRow(),4).toString());
            param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbObat.getValueAt(tbObat.getSelectedRow(),5).toString()+"\nID "+(finger.equals("")?tbObat.getValueAt(tbObat.getSelectedRow(),4).toString():finger)+"\n"+Tanggal.getSelectedItem()); 
            Valid.MyReportqry("rptFormulirHasilPersalinanTOLAC.jasper","report","::[ Formulir Hasil Persalinan TOLAC ]::",
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,reg_periksa.umurdaftar,reg_periksa.sttsumur,"+
                    "hasil_persalinan_tolac.kd_dokter,dokter.nm_dokter,hasil_persalinan_tolac.tanggal,hasil_persalinan_tolac.awitan_persalinan,"+
                    "hasil_persalinan_tolac.augmentasi,hasil_persalinan_tolac.ctg_kontinu,hasil_persalinan_tolac.cara_persalinan,hasil_persalinan_tolac.indikasi_gagal,"+
                    "hasil_persalinan_tolac.lama_kala2_menit,hasil_persalinan_tolac.ruptur_uteri,hasil_persalinan_tolac.jumlah_perdarahan_ml,"+
                    "hasil_persalinan_tolac.transfusi,hasil_persalinan_tolac.histerektomi,hasil_persalinan_tolac.komplikasi_lain,hasil_persalinan_tolac.luaran_perinatal,"+
                    "hasil_persalinan_tolac.bb_lahir_gram,hasil_persalinan_tolac.apgar_1,hasil_persalinan_tolac.apgar_5,hasil_persalinan_tolac.hie,"+
                    "hasil_persalinan_tolac.masuk_nicu,hasil_persalinan_tolac.keterangan from hasil_persalinan_tolac "+
                    "inner join reg_periksa on hasil_persalinan_tolac.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join dokter on hasil_persalinan_tolac.kd_dokter=dokter.kd_dokter "+
                    "where reg_periksa.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'",param);
        }
    }//GEN-LAST:event_MnHasilPersalinanTOLACActionPerformed

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

    private void CTGContinuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CTGContinuKeyPressed
        Valid.pindah(evt,Augmentasi,CaraPersalinan);
    }//GEN-LAST:event_CTGContinuKeyPressed

    private void AwitanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AwitanKeyPressed
        Valid.pindah(evt,BtnDokter,Augmentasi);
    }//GEN-LAST:event_AwitanKeyPressed

    private void AugmentasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AugmentasiKeyPressed
        Valid.pindah(evt,Awitan,CTGContinu);
    }//GEN-LAST:event_AugmentasiKeyPressed

    private void LuaranPerinatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LuaranPerinatalKeyPressed
        Valid.pindah(evt,KomplikasiLain,BBLahir);
    }//GEN-LAST:event_LuaranPerinatalKeyPressed

    private void IndikasiGagalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_IndikasiGagalKeyPressed
        Valid.pindah(evt,CaraPersalinan,LamaKala2);
    }//GEN-LAST:event_IndikasiGagalKeyPressed

    private void CaraPersalinanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CaraPersalinanKeyPressed
        Valid.pindah(evt,CTGContinu,IndikasiGagal);
    }//GEN-LAST:event_CaraPersalinanKeyPressed

    private void LamaKala2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LamaKala2KeyPressed
        Valid.pindah(evt,IndikasiGagal,RupturUteri);
    }//GEN-LAST:event_LamaKala2KeyPressed

    private void RupturUteriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RupturUteriKeyPressed
        Valid.pindah(evt,LamaKala2,JumlahPerdarahan);
    }//GEN-LAST:event_RupturUteriKeyPressed

    private void JumlahPerdarahanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JumlahPerdarahanKeyPressed
        Valid.pindah(evt,RupturUteri,Tranfusi);
    }//GEN-LAST:event_JumlahPerdarahanKeyPressed

    private void TranfusiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TranfusiKeyPressed
        Valid.pindah(evt,JumlahPerdarahan,HisterokTomi);
    }//GEN-LAST:event_TranfusiKeyPressed

    private void HisterokTomiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HisterokTomiKeyPressed
        Valid.pindah(evt,Tranfusi,KomplikasiLain);
    }//GEN-LAST:event_HisterokTomiKeyPressed

    private void KomplikasiLainKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KomplikasiLainKeyPressed
        Valid.pindah(evt,HisterokTomi,LuaranPerinatal);
    }//GEN-LAST:event_KomplikasiLainKeyPressed

    private void APGAR5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_APGAR5KeyPressed
        Valid.pindah(evt,APGAR1,HIE);
    }//GEN-LAST:event_APGAR5KeyPressed

    private void APGAR1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_APGAR1KeyPressed
        Valid.pindah(evt,BBLahir,APGAR5);
    }//GEN-LAST:event_APGAR1KeyPressed

    private void MasukNICUKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MasukNICUKeyPressed
        Valid.pindah(evt,HIE,Keterangan);
    }//GEN-LAST:event_MasukNICUKeyPressed

    private void KeteranganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKeyPressed
        Valid.pindah(evt,MasukNICU,BtnSimpan);
    }//GEN-LAST:event_KeteranganKeyPressed

    private void HIEKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HIEKeyPressed
        Valid.pindah(evt,APGAR5,MasukNICU);
    }//GEN-LAST:event_HIEKeyPressed

    private void BBLahirKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BBLahirKeyPressed
        Valid.pindah(evt,LuaranPerinatal,APGAR1);
    }//GEN-LAST:event_BBLahirKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMHasilPersalinanTOLAC dialog = new RMHasilPersalinanTOLAC(new javax.swing.JFrame(), true);
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
    private widget.TextBox APGAR1;
    private widget.TextBox APGAR5;
    private widget.ComboBox Augmentasi;
    private widget.ComboBox Awitan;
    private widget.TextBox BBLahir;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnDokter;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.ComboBox CTGContinu;
    private widget.ComboBox CaraPersalinan;
    private widget.CekBox ChkInput;
    private widget.CekBox ChkKejadian;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.ComboBox Detik;
    private widget.PanelBiasa FormInput;
    private widget.ComboBox HIE;
    private widget.ComboBox HisterokTomi;
    private widget.TextBox IndikasiGagal;
    private widget.ComboBox Jam;
    private widget.TextBox JumlahPerdarahan;
    private widget.TextBox KdDokter;
    private widget.TextBox Keterangan;
    private widget.TextBox KomplikasiLain;
    private widget.Label LCount;
    private widget.TextBox LamaKala2;
    private widget.editorpane LoadHTML;
    private widget.ComboBox LuaranPerinatal;
    private widget.ComboBox MasukNICU;
    private widget.ComboBox Menit;
    private widget.MenuItem MnHasilPersalinanTOLAC;
    private widget.TextBox NmDokter;
    private javax.swing.JPanel PanelInput;
    private widget.ComboBox RupturUteri;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.Tanggal Tanggal;
    private widget.TextBox TanggalRegistrasi;
    private widget.TextBox TglLahir;
    private widget.ComboBox Tranfusi;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel10;
    private widget.Label jLabel109;
    private widget.Label jLabel11;
    private widget.Label jLabel110;
    private widget.Label jLabel111;
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
    private widget.Label jLabel28;
    private widget.Label jLabel29;
    private widget.Label jLabel30;
    private widget.Label jLabel31;
    private widget.Label jLabel32;
    private widget.Label jLabel33;
    private widget.Label jLabel34;
    private widget.Label jLabel36;
    private widget.Label jLabel37;
    private widget.Label jLabel38;
    private widget.Label jLabel39;
    private widget.Label jLabel4;
    private widget.Label jLabel40;
    private widget.Label jLabel5;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private javax.swing.JPanel jPanel3;
    private widget.PopupMenu jPopupMenu1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator5;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollInput;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables
    
    private void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            if(TCari.getText().trim().equals("")){
                ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,reg_periksa.umurdaftar,reg_periksa.sttsumur,hasil_persalinan_tolac.kd_dokter,dokter.nm_dokter,hasil_persalinan_tolac.tanggal,hasil_persalinan_tolac.awitan_persalinan,"+
                    "hasil_persalinan_tolac.augmentasi,hasil_persalinan_tolac.ctg_kontinu,hasil_persalinan_tolac.cara_persalinan,hasil_persalinan_tolac.indikasi_gagal,hasil_persalinan_tolac.lama_kala2_menit,hasil_persalinan_tolac.ruptur_uteri,hasil_persalinan_tolac.jumlah_perdarahan_ml,"+
                    "hasil_persalinan_tolac.transfusi,hasil_persalinan_tolac.histerektomi,hasil_persalinan_tolac.komplikasi_lain,hasil_persalinan_tolac.luaran_perinatal,hasil_persalinan_tolac.bb_lahir_gram,hasil_persalinan_tolac.apgar_1,hasil_persalinan_tolac.apgar_5,hasil_persalinan_tolac.hie,"+
                    "hasil_persalinan_tolac.masuk_nicu,hasil_persalinan_tolac.keterangan from hasil_persalinan_tolac inner join reg_periksa on hasil_persalinan_tolac.no_rawat=reg_periksa.no_rawat inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join dokter on hasil_persalinan_tolac.kd_dokter=dokter.kd_dokter where hasil_persalinan_tolac.tanggal between ? and ? order by hasil_persalinan_tolac.tanggal"
                );
            }else{
                ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,reg_periksa.umurdaftar,reg_periksa.sttsumur,hasil_persalinan_tolac.kd_dokter,dokter.nm_dokter,hasil_persalinan_tolac.tanggal,hasil_persalinan_tolac.awitan_persalinan,"+
                    "hasil_persalinan_tolac.augmentasi,hasil_persalinan_tolac.ctg_kontinu,hasil_persalinan_tolac.cara_persalinan,hasil_persalinan_tolac.indikasi_gagal,hasil_persalinan_tolac.lama_kala2_menit,hasil_persalinan_tolac.ruptur_uteri,hasil_persalinan_tolac.jumlah_perdarahan_ml,"+
                    "hasil_persalinan_tolac.transfusi,hasil_persalinan_tolac.histerektomi,hasil_persalinan_tolac.komplikasi_lain,hasil_persalinan_tolac.luaran_perinatal,hasil_persalinan_tolac.bb_lahir_gram,hasil_persalinan_tolac.apgar_1,hasil_persalinan_tolac.apgar_5,hasil_persalinan_tolac.hie,"+
                    "hasil_persalinan_tolac.masuk_nicu,hasil_persalinan_tolac.keterangan from hasil_persalinan_tolac inner join reg_periksa on hasil_persalinan_tolac.no_rawat=reg_periksa.no_rawat inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join dokter on hasil_persalinan_tolac.kd_dokter=dokter.kd_dokter where hasil_persalinan_tolac.tanggal between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or hasil_persalinan_tolac.kd_dokter like ? or "+
                    "dokter.nm_dokter like ? or hasil_persalinan_tolac.cara_persalinan like ? or hasil_persalinan_tolac.luaran_perinatal like ? or hasil_persalinan_tolac.keterangan like ?) order by hasil_persalinan_tolac.tanggal"
                );
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
                    ps.setString(9,"%"+TCari.getText()+"%");
                    ps.setString(10,"%"+TCari.getText()+"%");
                }
                    
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new Object[]{
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien")+" ("+rs.getString("umurdaftar")+" "+rs.getString("sttsumur")+")",rs.getDate("tgl_lahir"),
                        rs.getString("kd_dokter"),rs.getString("nm_dokter"),rs.getString("tanggal"),rs.getString("awitan_persalinan"),rs.getString("augmentasi"),rs.getString("ctg_kontinu"),
                        rs.getString("cara_persalinan"),rs.getString("indikasi_gagal"),rs.getString("lama_kala2_menit"),rs.getString("ruptur_uteri"),rs.getString("jumlah_perdarahan_ml"),
                        rs.getString("transfusi"),rs.getString("histerektomi"),rs.getString("komplikasi_lain"),rs.getString("luaran_perinatal"),rs.getString("bb_lahir_gram"),
                        rs.getString("apgar_1"),rs.getString("apgar_5"),rs.getString("hie"),rs.getString("masuk_nicu"),rs.getString("keterangan")
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
        Awitan.setSelectedIndex(0);
        Augmentasi.setSelectedIndex(0);
        CTGContinu.setSelectedIndex(0);
        CaraPersalinan.setSelectedIndex(0);
        IndikasiGagal.setText("");
        LamaKala2.setText("");
        RupturUteri.setSelectedIndex(0);
        JumlahPerdarahan.setText("");
        Tranfusi.setSelectedIndex(0);
        HisterokTomi.setSelectedIndex(0);
        KomplikasiLain.setText("");
        LuaranPerinatal.setSelectedIndex(0);
        BBLahir.setText("");
        APGAR1.setText("");
        APGAR5.setText("");
        HIE.setSelectedIndex(0);
        MasukNICU.setSelectedIndex(0);
        Keterangan.setText("");
        Tanggal.setDate(new Date());
        Awitan.requestFocus();
    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            Awitan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());
            Augmentasi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            CTGContinu.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            CaraPersalinan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            IndikasiGagal.setText(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            LamaKala2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            RupturUteri.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            JumlahPerdarahan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            Tranfusi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
            HisterokTomi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            KomplikasiLain.setText(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());
            LuaranPerinatal.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
            BBLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
            APGAR1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString());
            APGAR5.setText(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString());
            HIE.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString());
            MasukNICU.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString());
            Keterangan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),24).toString());
            Jam.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString().substring(11,13));
            Menit.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString().substring(14,16));
            Detik.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString().substring(17,19));
            Valid.SetTgl(Tanggal,tbObat.getValueAt(tbObat.getSelectedRow(),6).toString());  
        }
    }
    
    private void isRawat() {
        try {
            ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,"+
                    "reg_periksa.tgl_registrasi,reg_periksa.jam_reg,reg_periksa.umurdaftar,reg_periksa.sttsumur "+
                    "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "where reg_periksa.no_rawat=?");
            try {
                ps.setString(1,TNoRw.getText());
                rs=ps.executeQuery();
                if(rs.next()){
                    TNoRM.setText(rs.getString("no_rkm_medis"));
                    DTPCari1.setDate(rs.getDate("tgl_registrasi"));
                    TPasien.setText(rs.getString("nm_pasien")+" ("+rs.getString("umurdaftar")+" "+rs.getString("sttsumur")+")");
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
        ChkInput.setSelected(true);
        isForm();
        runBackground(() ->tampil());
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            if(internalFrame1.getHeight()>508){
                ChkInput.setVisible(false);
                PanelInput.setPreferredSize(new Dimension(WIDTH,336));
                FormInput.setVisible(true);      
                ChkInput.setVisible(true);
            }else{
                ChkInput.setVisible(false);
                PanelInput.setPreferredSize(new Dimension(WIDTH,internalFrame1.getHeight()-175));
                FormInput.setVisible(true);      
                ChkInput.setVisible(true);
            }
        }else if(ChkInput.isSelected()==false){           
            ChkInput.setVisible(false);            
            PanelInput.setPreferredSize(new Dimension(WIDTH,20));
            FormInput.setVisible(false);      
            ChkInput.setVisible(true);
        }
    }
    
    public void isCek(){
        BtnSimpan.setEnabled(akses.gethasil_persalinan_tolac());
        BtnHapus.setEnabled(akses.gethasil_persalinan_tolac());
        BtnEdit.setEnabled(akses.gethasil_persalinan_tolac());
        BtnPrint.setEnabled(akses.gethasil_persalinan_tolac()); 
        if(akses.getjml2()>=1){
            KdDokter.setEditable(false);
            BtnDokter.setEnabled(false);
            KdDokter.setText(akses.getkode());
            NmDokter.setText(Sequel.CariDokter(KdDokter.getText()));
            if(NmDokter.getText().equals("")){
                KdDokter.setText("");
                JOptionPane.showMessageDialog(null,"User login bukan dokter...!!");
            }
        }    

        if(TANGGALMUNDUR.equals("no")){
            if(!akses.getkode().equals("Admin Utama")){
                Tanggal.setEditable(false);
                Tanggal.setEnabled(false);
                ChkKejadian.setEnabled(false);
                Jam.setEnabled(false);
                Menit.setEnabled(false);
                Detik.setEnabled(false);
            }
        }
    }

    private void jam(){
        ActionListener taskPerformer = new ActionListener(){
            private int nilai_jam;
            private int nilai_menit;
            private int nilai_detik;
            public void actionPerformed(ActionEvent e) {
                String nol_jam = "";
                String nol_menit = "";
                String nol_detik = "";
                
                Date now = Calendar.getInstance().getTime();

                // Mengambil nilaj JAM, MENIT, dan DETIK Sekarang
                if(ChkKejadian.isSelected()==true){
                    nilai_jam = now.getHours();
                    nilai_menit = now.getMinutes();
                    nilai_detik = now.getSeconds();
                }else if(ChkKejadian.isSelected()==false){
                    nilai_jam =Jam.getSelectedIndex();
                    nilai_menit =Menit.getSelectedIndex();
                    nilai_detik =Detik.getSelectedIndex();
                }

                // Jika nilai JAM lebih kecil dari 10 (hanya 1 digit)
                if (nilai_jam <= 9) {
                    // Tambahkan "0" didepannya
                    nol_jam = "0";
                }
                // Jika nilai MENIT lebih kecil dari 10 (hanya 1 digit)
                if (nilai_menit <= 9) {
                    // Tambahkan "0" didepannya
                    nol_menit = "0";
                }
                // Jika nilai DETIK lebih kecil dari 10 (hanya 1 digit)
                if (nilai_detik <= 9) {
                    // Tambahkan "0" didepannya
                    nol_detik = "0";
                }
                // Membuat String JAM, MENIT, DETIK
                String jam = nol_jam + Integer.toString(nilai_jam);
                String menit = nol_menit + Integer.toString(nilai_menit);
                String detik = nol_detik + Integer.toString(nilai_detik);
                // Menampilkan pada Layar
                //tampil_jam.setText("  " + jam + " : " + menit + " : " + detik + "  ");
                Jam.setSelectedItem(jam);
                Menit.setSelectedItem(menit);
                Detik.setSelectedItem(detik);
            }
        };
        // Timer
        new Timer(1000, taskPerformer).start();
    }

    private void ganti() {
        if(Sequel.mengedittf("hasil_persalinan_tolac","no_rawat=?","no_rawat=?,tanggal=?,awitan_persalinan=?,augmentasi=?,ctg_kontinu=?,cara_persalinan=?,indikasi_gagal=?,lama_kala2_menit=?,ruptur_uteri=?,jumlah_perdarahan_ml=?,transfusi=?,histerektomi=?,komplikasi_lain=?,luaran_perinatal=?,bb_lahir_gram=?,apgar_1=?,apgar_5=?,hie=?,masuk_nicu=?,keterangan=?,kd_dokter=?",22,new String[]{
            TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),Awitan.getSelectedItem().toString(),Augmentasi.getSelectedItem().toString(),CTGContinu.getSelectedItem().toString(),CaraPersalinan.getSelectedItem().toString(),IndikasiGagal.getText(),LamaKala2.getText(),RupturUteri.getSelectedItem().toString(),
            JumlahPerdarahan.getText(),Tranfusi.getSelectedItem().toString(),HisterokTomi.getSelectedItem().toString(),KomplikasiLain.getText(),LuaranPerinatal.getSelectedItem().toString(),BBLahir.getText(),APGAR1.getText(),APGAR5.getText(),HIE.getSelectedItem().toString(),MasukNICU.getSelectedItem().toString(),Keterangan.getText(),KdDokter.getText(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
            })==true){
               tbObat.setValueAt(TNoRw.getText(),tbObat.getSelectedRow(),0);
               tbObat.setValueAt(TNoRM.getText(),tbObat.getSelectedRow(),1);
               tbObat.setValueAt(TPasien.getText(),tbObat.getSelectedRow(),2);
               tbObat.setValueAt(TglLahir.getText(),tbObat.getSelectedRow(),3);
               tbObat.setValueAt(KdDokter.getText(),tbObat.getSelectedRow(),4);
               tbObat.setValueAt(NmDokter.getText(),tbObat.getSelectedRow(),5);
               tbObat.setValueAt(Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),tbObat.getSelectedRow(),6);
               tbObat.setValueAt(Awitan.getSelectedItem().toString(),tbObat.getSelectedRow(),7);
               tbObat.setValueAt(Augmentasi.getSelectedItem().toString(),tbObat.getSelectedRow(),8);
               tbObat.setValueAt(CTGContinu.getSelectedItem().toString(),tbObat.getSelectedRow(),9);
               tbObat.setValueAt(CaraPersalinan.getSelectedItem().toString(),tbObat.getSelectedRow(),10);
               tbObat.setValueAt(IndikasiGagal.getText(),tbObat.getSelectedRow(),11);
               tbObat.setValueAt(LamaKala2.getText(),tbObat.getSelectedRow(),12);
               tbObat.setValueAt(RupturUteri.getSelectedItem().toString(),tbObat.getSelectedRow(),13);
               tbObat.setValueAt(JumlahPerdarahan.getText(),tbObat.getSelectedRow(),14);
               tbObat.setValueAt(Tranfusi.getSelectedItem().toString(),tbObat.getSelectedRow(),15);
               tbObat.setValueAt(HisterokTomi.getSelectedItem().toString(),tbObat.getSelectedRow(),16);
               tbObat.setValueAt(KomplikasiLain.getText(),tbObat.getSelectedRow(),17);
               tbObat.setValueAt(LuaranPerinatal.getSelectedItem().toString(),tbObat.getSelectedRow(),18);
               tbObat.setValueAt(BBLahir.getText(),tbObat.getSelectedRow(),19);
               tbObat.setValueAt(APGAR1.getText(),tbObat.getSelectedRow(),20);
               tbObat.setValueAt(APGAR5.getText(),tbObat.getSelectedRow(),21);
               tbObat.setValueAt(HIE.getSelectedItem().toString(),tbObat.getSelectedRow(),22);
               tbObat.setValueAt(MasukNICU.getSelectedItem().toString(),tbObat.getSelectedRow(),23);
               tbObat.setValueAt(Keterangan.getText(),tbObat.getSelectedRow(),24);
               emptTeks();
        }
    }

    private void hapus() {
        if(Sequel.queryu2tf("delete from hasil_persalinan_tolac where no_rawat=?",1,new String[]{
            tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
        })==true){
            tabMode.removeRow(tbObat.getSelectedRow());
            LCount.setText(""+tabMode.getRowCount());
            emptTeks();
        }else{
            JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
        }
    }
    
    private void simpan() {
        if(Sequel.menyimpantf("hasil_persalinan_tolac","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","Data",21,new String[]{
            TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),Awitan.getSelectedItem().toString(),
            Augmentasi.getSelectedItem().toString(),CTGContinu.getSelectedItem().toString(),CaraPersalinan.getSelectedItem().toString(),IndikasiGagal.getText(),LamaKala2.getText(),RupturUteri.getSelectedItem().toString(),
            JumlahPerdarahan.getText(),Tranfusi.getSelectedItem().toString(),HisterokTomi.getSelectedItem().toString(),KomplikasiLain.getText(),LuaranPerinatal.getSelectedItem().toString(),BBLahir.getText(),
            APGAR1.getText(),APGAR5.getText(),HIE.getSelectedItem().toString(),MasukNICU.getSelectedItem().toString(),Keterangan.getText(),KdDokter.getText()
        })==true){
            tabMode.addRow(new Object[]{
                TNoRw.getText(),TNoRM.getText(),TPasien.getText(),TglLahir.getText(),KdDokter.getText(),NmDokter.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),
                Awitan.getSelectedItem().toString(),Augmentasi.getSelectedItem().toString(),CTGContinu.getSelectedItem().toString(),CaraPersalinan.getSelectedItem().toString(),IndikasiGagal.getText(),LamaKala2.getText(),RupturUteri.getSelectedItem().toString(),
                JumlahPerdarahan.getText(),Tranfusi.getSelectedItem().toString(),HisterokTomi.getSelectedItem().toString(),KomplikasiLain.getText(),LuaranPerinatal.getSelectedItem().toString(),BBLahir.getText(),APGAR1.getText(),APGAR5.getText(),
                HIE.getSelectedItem().toString(),MasukNICU.getSelectedItem().toString(),Keterangan.getText()
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

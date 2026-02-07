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
import kepegawaian.DlgCariPetugas;


/**
 *
 * @author perpustakaan
 */
public final class RMSkriningNutrisiLansia extends javax.swing.JDialog {
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
    public RMSkriningNutrisiLansia(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(628,674);

        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.RM","Nama Pasien","Tgl.Lahir","J.K.","Kode Petugas","Nama Petugas","Tanggal",
            "BB(Kg)","TB/PB(Cm)","TD(mmHg)","HR(x/menit)","RR(x/menit)","Suhu","SpO2","Alergi",
            "Skrining Gizi 1","Nilai 1","Skrining Gizi 2","Nilai 2","Skrining Gizi 3","Nilai 3",
            "Skrining Gizi 4","Nilai 4","Skrining Gizi 5","Nilai 5","Skrining Gizi 6","Nilai 6",
            "Total Skor","Hasil Skrining"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 30; i++) {
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
                column.setPreferredWidth(35);
            }else if(i==5){
                column.setPreferredWidth(80);
            }else if(i==6){
                column.setPreferredWidth(150);
            }else if(i==7){
                column.setPreferredWidth(115);
            }else if(i==8){
                column.setPreferredWidth(40);
            }else if(i==9){
                column.setPreferredWidth(58);
            }else if(i==10){
                column.setPreferredWidth(60);
            }else if(i==11){
                column.setPreferredWidth(68);
            }else if(i==12){
                column.setPreferredWidth(68);
            }else if(i==13){
                column.setPreferredWidth(37);
            }else if(i==14){
                column.setPreferredWidth(37);
            }else if(i==15){
                column.setPreferredWidth(150);
            }else if(i==16){
                column.setPreferredWidth(160);
            }else if(i==17){
                column.setPreferredWidth(39);
            }else if(i==18){
                column.setPreferredWidth(160);
            }else if(i==19){
                column.setPreferredWidth(39);
            }else if(i==20){
                column.setPreferredWidth(160);
            }else if(i==21){
                column.setPreferredWidth(39);
            }else if(i==22){
                column.setPreferredWidth(78);
            }else if(i==23){
                column.setPreferredWidth(39);
            }else if(i==24){
                column.setPreferredWidth(160);
            }else if(i==25){
                column.setPreferredWidth(39);
            }else if(i==26){
                column.setPreferredWidth(100);
            }else if(i==27){
                column.setPreferredWidth(39);
            }else if(i==28){
                column.setPreferredWidth(57);
            }else if(i==29){
                column.setPreferredWidth(95);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());

        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        KdPetugas.setDocument(new batasInput((byte)20).getKata(KdPetugas));
        TBPB.setDocument(new batasInput((byte)5).getKata(TBPB));
        BB.setDocument(new batasInput((byte)5).getKata(BB));
        TD.setDocument(new batasInput((byte)8).getKata(TD));
        HR.setDocument(new batasInput((byte)5).getKata(HR));
        RR.setDocument(new batasInput((byte)5).getKata(RR));
        Suhu.setDocument(new batasInput((byte)5).getKata(Suhu));
        SpO2.setDocument(new batasInput((byte)5).getKata(SpO2));
        Alergi.setDocument(new batasInput((int)100).getKata(Alergi));
        TCari.setDocument(new batasInput((int)100).getKata(TCari));
        
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

        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnSkriningNutrisi = new javax.swing.JMenuItem();
        buttonGroup1 = new javax.swing.ButtonGroup();
        LoadHTML = new widget.editorpane();
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
        KdPetugas = new widget.TextBox();
        NmPetugas = new widget.TextBox();
        BtnPetugas = new widget.Button();
        jLabel24 = new widget.Label();
        Alergi = new widget.TextBox();
        jLabel8 = new widget.Label();
        TglLahir = new widget.TextBox();
        jLabel12 = new widget.Label();
        BB = new widget.TextBox();
        jLabel13 = new widget.Label();
        jLabel15 = new widget.Label();
        TBPB = new widget.TextBox();
        jLabel25 = new widget.Label();
        jLabel29 = new widget.Label();
        TD = new widget.TextBox();
        jLabel30 = new widget.Label();
        jLabel31 = new widget.Label();
        HR = new widget.TextBox();
        jLabel32 = new widget.Label();
        jLabel33 = new widget.Label();
        RR = new widget.TextBox();
        jLabel34 = new widget.Label();
        jLabel35 = new widget.Label();
        Suhu = new widget.TextBox();
        jLabel36 = new widget.Label();
        jLabel37 = new widget.Label();
        SpO2 = new widget.TextBox();
        jLabel38 = new widget.Label();
        jLabel39 = new widget.Label();
        SG1 = new widget.ComboBox();
        jLabel75 = new widget.Label();
        jLabel92 = new widget.Label();
        jLabel43 = new widget.Label();
        SG2 = new widget.ComboBox();
        jLabel69 = new widget.Label();
        jLabel73 = new widget.Label();
        TotalHasil = new widget.TextBox();
        jLabel44 = new widget.Label();
        Nilai1 = new widget.TextBox();
        Nilai2 = new widget.TextBox();
        jLabel45 = new widget.Label();
        SG3 = new widget.ComboBox();
        jLabel70 = new widget.Label();
        Nilai3 = new widget.TextBox();
        jLabel71 = new widget.Label();
        Nilai4 = new widget.TextBox();
        SG4 = new widget.ComboBox();
        jLabel46 = new widget.Label();
        jLabel47 = new widget.Label();
        SG6 = new widget.ComboBox();
        jLabel72 = new widget.Label();
        Nilai5 = new widget.TextBox();
        R1 = new widget.RadioButton();
        SG5 = new widget.ComboBox();
        jLabel74 = new widget.Label();
        Nilai6 = new widget.TextBox();
        Nilai7 = new widget.TextBox();
        jLabel76 = new widget.Label();
        SG7 = new widget.ComboBox();
        R2 = new widget.RadioButton();
        jLabel48 = new widget.Label();
        jLabel49 = new widget.Label();
        LabelSkrining = new widget.Label();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnSkriningNutrisi.setBackground(new java.awt.Color(255, 255, 254));
        MnSkriningNutrisi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSkriningNutrisi.setForeground(new java.awt.Color(50, 50, 50));
        MnSkriningNutrisi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnSkriningNutrisi.setText("Formulir Skrining Nutrisi Pasien Lansia");
        MnSkriningNutrisi.setName("MnSkriningNutrisi"); // NOI18N
        MnSkriningNutrisi.setPreferredSize(new java.awt.Dimension(260, 26));
        MnSkriningNutrisi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSkriningNutrisiActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnSkriningNutrisi);

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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Skrining Nutrisi Pasien Lansia ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "07-02-2026" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass9.add(DTPCari1);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("s.d.");
        jLabel21.setName("jLabel21"); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(23, 23));
        panelGlass9.add(jLabel21);

        DTPCari2.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "07-02-2026" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
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
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 416));
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
        FormInput.setPreferredSize(new java.awt.Dimension(100, 393));
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
        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "07-02-2026" }));
        Tanggal.setDisplayFormat("dd-MM-yyyy");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.setOpaque(false);
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
        TNoRM.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRMKeyPressed(evt);
            }
        });
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

        jLabel18.setText("Petugas :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(400, 40, 70, 23);

        KdPetugas.setEditable(false);
        KdPetugas.setHighlighter(null);
        KdPetugas.setName("KdPetugas"); // NOI18N
        KdPetugas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdPetugasKeyPressed(evt);
            }
        });
        FormInput.add(KdPetugas);
        KdPetugas.setBounds(474, 40, 94, 23);

        NmPetugas.setEditable(false);
        NmPetugas.setName("NmPetugas"); // NOI18N
        FormInput.add(NmPetugas);
        NmPetugas.setBounds(570, 40, 187, 23);

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
        BtnPetugas.setBounds(761, 40, 28, 23);

        jLabel24.setText("Alergi :");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(316, 100, 50, 23);

        Alergi.setHighlighter(null);
        Alergi.setName("Alergi"); // NOI18N
        Alergi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlergiKeyPressed(evt);
            }
        });
        FormInput.add(Alergi);
        Alergi.setBounds(370, 100, 419, 23);

        jLabel8.setText("Tgl.Lahir :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(625, 10, 60, 23);

        TglLahir.setHighlighter(null);
        TglLahir.setName("TglLahir"); // NOI18N
        FormInput.add(TglLahir);
        TglLahir.setBounds(689, 10, 100, 23);

        jLabel12.setText("BB :");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(0, 70, 75, 23);

        BB.setFocusTraversalPolicyProvider(true);
        BB.setName("BB"); // NOI18N
        BB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BBKeyPressed(evt);
            }
        });
        FormInput.add(BB);
        BB.setBounds(79, 70, 45, 23);

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel13.setText("Kg");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(126, 70, 30, 23);

        jLabel15.setText("TB/PB :");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(176, 70, 50, 23);

        TBPB.setFocusTraversalPolicyProvider(true);
        TBPB.setName("TBPB"); // NOI18N
        TBPB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TBPBKeyPressed(evt);
            }
        });
        FormInput.add(TBPB);
        TBPB.setBounds(230, 70, 45, 23);

        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel25.setText("Cm");
        jLabel25.setName("jLabel25"); // NOI18N
        FormInput.add(jLabel25);
        jLabel25.setBounds(277, 70, 30, 23);

        jLabel29.setText("TD :");
        jLabel29.setName("jLabel29"); // NOI18N
        FormInput.add(jLabel29);
        jLabel29.setBounds(324, 70, 42, 23);

        TD.setFocusTraversalPolicyProvider(true);
        TD.setName("TD"); // NOI18N
        TD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TDKeyPressed(evt);
            }
        });
        FormInput.add(TD);
        TD.setBounds(370, 70, 60, 23);

        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel30.setText("mmHg");
        jLabel30.setName("jLabel30"); // NOI18N
        FormInput.add(jLabel30);
        jLabel30.setBounds(432, 70, 50, 23);

        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel31.setText("x/menit");
        jLabel31.setName("jLabel31"); // NOI18N
        FormInput.add(jLabel31);
        jLabel31.setBounds(585, 70, 50, 23);

        HR.setFocusTraversalPolicyProvider(true);
        HR.setName("HR"); // NOI18N
        HR.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HRKeyPressed(evt);
            }
        });
        FormInput.add(HR);
        HR.setBounds(538, 70, 45, 23);

        jLabel32.setText("HR :");
        jLabel32.setName("jLabel32"); // NOI18N
        FormInput.add(jLabel32);
        jLabel32.setBounds(492, 70, 42, 23);

        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel33.setText("x/menit");
        jLabel33.setName("jLabel33"); // NOI18N
        FormInput.add(jLabel33);
        jLabel33.setBounds(744, 70, 50, 23);

        RR.setFocusTraversalPolicyProvider(true);
        RR.setName("RR"); // NOI18N
        RR.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RRKeyPressed(evt);
            }
        });
        FormInput.add(RR);
        RR.setBounds(697, 70, 45, 23);

        jLabel34.setText("RR :");
        jLabel34.setName("jLabel34"); // NOI18N
        FormInput.add(jLabel34);
        jLabel34.setBounds(651, 70, 42, 23);

        jLabel35.setText("Suhu :");
        jLabel35.setName("jLabel35"); // NOI18N
        FormInput.add(jLabel35);
        jLabel35.setBounds(0, 100, 75, 23);

        Suhu.setFocusTraversalPolicyProvider(true);
        Suhu.setName("Suhu"); // NOI18N
        Suhu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SuhuKeyPressed(evt);
            }
        });
        FormInput.add(Suhu);
        Suhu.setBounds(79, 100, 45, 23);

        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel36.setText("°C");
        jLabel36.setName("jLabel36"); // NOI18N
        FormInput.add(jLabel36);
        jLabel36.setBounds(126, 100, 50, 23);

        jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel37.setText("%");
        jLabel37.setName("jLabel37"); // NOI18N
        FormInput.add(jLabel37);
        jLabel37.setBounds(277, 100, 50, 23);

        SpO2.setFocusTraversalPolicyProvider(true);
        SpO2.setName("SpO2"); // NOI18N
        SpO2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SpO2KeyPressed(evt);
            }
        });
        FormInput.add(SpO2);
        SpO2.setBounds(230, 100, 45, 23);

        jLabel38.setText("SpO2 :");
        jLabel38.setName("jLabel38"); // NOI18N
        FormInput.add(jLabel38);
        jLabel38.setBounds(176, 100, 50, 23);

        jLabel39.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel39.setText("Skrining Gizi Awal Dengan MNA (Mini Nutritional Assesment) :");
        jLabel39.setName("jLabel39"); // NOI18N
        FormInput.add(jLabel39);
        jLabel39.setBounds(20, 130, 460, 23);

        SG1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Asupan Makan Sangat Berkurang", "Asupan Makan Agak Berkurang", "Asupan Makan Tidak Berkurang" }));
        SG1.setName("SG1"); // NOI18N
        SG1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SG1ItemStateChanged(evt);
            }
        });
        SG1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SG1KeyPressed(evt);
            }
        });
        FormInput.add(SG1);
        SG1.setBounds(450, 150, 230, 23);

        jLabel75.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel75.setText("A. Apakah Asupan Makan Berkurang Selama 3 Bulan Terakhir ?");
        jLabel75.setName("jLabel75"); // NOI18N
        FormInput.add(jLabel75);
        jLabel75.setBounds(44, 150, 310, 23);

        jLabel92.setText("Nilai :");
        jLabel92.setName("jLabel92"); // NOI18N
        FormInput.add(jLabel92);
        jLabel92.setBounds(675, 150, 50, 23);

        jLabel43.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel43.setText("B. Penurunan Berat Badan Selama 3 Bulan Terakhir");
        jLabel43.setName("jLabel43"); // NOI18N
        FormInput.add(jLabel43);
        jLabel43.setBounds(44, 180, 260, 23);

        SG2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Penurunan Berat Badan Lebih Dari 3 Kg", "Tidak Tahu", "Penurunan Berat Badan Antara 1 Hingga 3 Kg", "Tidak Ada Penurunan Berat Badan" }));
        SG2.setName("SG2"); // NOI18N
        SG2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SG2ItemStateChanged(evt);
            }
        });
        SG2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SG2KeyPressed(evt);
            }
        });
        FormInput.add(SG2);
        SG2.setBounds(410, 180, 270, 23);

        jLabel69.setText("Nilai :");
        jLabel69.setName("jLabel69"); // NOI18N
        FormInput.add(jLabel69);
        jLabel69.setBounds(675, 180, 50, 23);

        jLabel73.setText("Total Skor :");
        jLabel73.setName("jLabel73"); // NOI18N
        FormInput.add(jLabel73);
        jLabel73.setBounds(655, 360, 70, 23);

        TotalHasil.setEditable(false);
        TotalHasil.setText("0");
        TotalHasil.setFocusTraversalPolicyProvider(true);
        TotalHasil.setName("TotalHasil"); // NOI18N
        FormInput.add(TotalHasil);
        TotalHasil.setBounds(729, 360, 60, 23);

        jLabel44.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel44.setText("Hasil Skrining :");
        jLabel44.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        jLabel44.setName("jLabel44"); // NOI18N
        FormInput.add(jLabel44);
        jLabel44.setBounds(44, 360, 100, 23);

        Nilai1.setEditable(false);
        Nilai1.setText("0");
        Nilai1.setFocusTraversalPolicyProvider(true);
        Nilai1.setName("Nilai1"); // NOI18N
        FormInput.add(Nilai1);
        Nilai1.setBounds(729, 150, 60, 23);

        Nilai2.setEditable(false);
        Nilai2.setText("0");
        Nilai2.setFocusTraversalPolicyProvider(true);
        Nilai2.setName("Nilai2"); // NOI18N
        FormInput.add(Nilai2);
        Nilai2.setBounds(729, 180, 60, 23);

        jLabel45.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel45.setText("C. Mobilitas");
        jLabel45.setName("jLabel45"); // NOI18N
        FormInput.add(jLabel45);
        jLabel45.setBounds(44, 210, 80, 23);

        SG3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Terbatas Dari Tempat Tidur Atau Kursi", "Mampu Bangun Dari Tempat Tidur/Kursi Tetapi Tidak Bepergian Keluar Rumah", "Dapat Bepergian Keluar Rumah" }));
        SG3.setName("SG3"); // NOI18N
        SG3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SG3ItemStateChanged(evt);
            }
        });
        SG3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SG3KeyPressed(evt);
            }
        });
        FormInput.add(SG3);
        SG3.setBounds(240, 210, 440, 23);

        jLabel70.setText("Nilai :");
        jLabel70.setName("jLabel70"); // NOI18N
        FormInput.add(jLabel70);
        jLabel70.setBounds(675, 210, 50, 23);

        Nilai3.setEditable(false);
        Nilai3.setText("0");
        Nilai3.setFocusTraversalPolicyProvider(true);
        Nilai3.setName("Nilai3"); // NOI18N
        FormInput.add(Nilai3);
        Nilai3.setBounds(729, 210, 60, 23);

        jLabel71.setText("Nilai :");
        jLabel71.setName("jLabel71"); // NOI18N
        FormInput.add(jLabel71);
        jLabel71.setBounds(675, 240, 50, 23);

        Nilai4.setEditable(false);
        Nilai4.setText("0");
        Nilai4.setFocusTraversalPolicyProvider(true);
        Nilai4.setName("Nilai4"); // NOI18N
        FormInput.add(Nilai4);
        Nilai4.setBounds(729, 240, 60, 23);

        SG4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        SG4.setName("SG4"); // NOI18N
        SG4.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SG4ItemStateChanged(evt);
            }
        });
        SG4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SG4KeyPressed(evt);
            }
        });
        FormInput.add(SG4);
        SG4.setBounds(450, 240, 230, 23);

        jLabel46.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel46.setText("D. Menderita Tekanan Psikologis Atau Penyakit Berat Dalam 3 Bulan Terakhir");
        jLabel46.setName("jLabel46"); // NOI18N
        FormInput.add(jLabel46);
        jLabel46.setBounds(44, 240, 440, 23);

        jLabel47.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel47.setText("F. Lingkar Betis (cm)");
        jLabel47.setName("jLabel47"); // NOI18N
        FormInput.add(jLabel47);
        jLabel47.setBounds(44, 330, 130, 23);

        SG6.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "IMT < 19", "19 Hingga < 21", "21 Hingga < 23", "IMT >= 23" }));
        SG6.setName("SG6"); // NOI18N
        SG6.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SG6ItemStateChanged(evt);
            }
        });
        SG6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SG6KeyPressed(evt);
            }
        });
        FormInput.add(SG6);
        SG6.setBounds(450, 300, 230, 23);

        jLabel72.setText("Nilai :");
        jLabel72.setName("jLabel72"); // NOI18N
        FormInput.add(jLabel72);
        jLabel72.setBounds(675, 270, 50, 23);

        Nilai5.setEditable(false);
        Nilai5.setText("0");
        Nilai5.setFocusTraversalPolicyProvider(true);
        Nilai5.setName("Nilai5"); // NOI18N
        FormInput.add(Nilai5);
        Nilai5.setBounds(729, 270, 60, 23);

        buttonGroup1.add(R1);
        R1.setSelected(true);
        R1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        R1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        R1.setMargin(new java.awt.Insets(2, 0, 2, 2));
        R1.setName("R1"); // NOI18N
        R1.setPreferredSize(new java.awt.Dimension(95, 23));
        FormInput.add(R1);
        R1.setBounds(15, 300, 26, 23);

        SG5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Depresi Berat Atau Kepikunan Berat", "Kepikunan Ringan", "Tidak Ada Gangguan Psikologis" }));
        SG5.setName("SG5"); // NOI18N
        SG5.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SG5ItemStateChanged(evt);
            }
        });
        SG5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SG5KeyPressed(evt);
            }
        });
        FormInput.add(SG5);
        SG5.setBounds(450, 270, 230, 23);

        jLabel74.setText("Nilai :");
        jLabel74.setName("jLabel74"); // NOI18N
        FormInput.add(jLabel74);
        jLabel74.setBounds(675, 300, 50, 23);

        Nilai6.setEditable(false);
        Nilai6.setText("0");
        Nilai6.setFocusTraversalPolicyProvider(true);
        Nilai6.setName("Nilai6"); // NOI18N
        FormInput.add(Nilai6);
        Nilai6.setBounds(729, 300, 60, 23);

        Nilai7.setEditable(false);
        Nilai7.setText("0");
        Nilai7.setFocusTraversalPolicyProvider(true);
        Nilai7.setName("Nilai7"); // NOI18N
        FormInput.add(Nilai7);
        Nilai7.setBounds(729, 330, 60, 23);

        jLabel76.setText("Nilai :");
        jLabel76.setName("jLabel76"); // NOI18N
        FormInput.add(jLabel76);
        jLabel76.setBounds(675, 330, 50, 23);

        SG7.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Lingkar Betis < 31", "Lingkar Betis >= 31" }));
        SG7.setName("SG7"); // NOI18N
        SG7.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SG7ItemStateChanged(evt);
            }
        });
        SG7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SG7KeyPressed(evt);
            }
        });
        FormInput.add(SG7);
        SG7.setBounds(450, 330, 230, 23);

        buttonGroup1.add(R2);
        R2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        R2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        R2.setMargin(new java.awt.Insets(2, 0, 2, 2));
        R2.setName("R2"); // NOI18N
        R2.setPreferredSize(new java.awt.Dimension(95, 23));
        FormInput.add(R2);
        R2.setBounds(15, 330, 26, 23);

        jLabel48.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel48.setText("E. Gangguan Neuropsikologis");
        jLabel48.setName("jLabel48"); // NOI18N
        FormInput.add(jLabel48);
        jLabel48.setBounds(44, 270, 180, 23);

        jLabel49.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel49.setText("F. Indeks Masa Tubuh (IMT)");
        jLabel49.setName("jLabel49"); // NOI18N
        FormInput.add(jLabel49);
        jLabel49.setBounds(44, 300, 400, 23);

        LabelSkrining.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LabelSkrining.setText("Status Gizi Normal");
        LabelSkrining.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        LabelSkrining.setName("LabelSkrining"); // NOI18N
        FormInput.add(LabelSkrining);
        LabelSkrining.setBounds(124, 360, 380, 23);

        scrollInput.setViewportView(FormInput);

        PanelInput.add(scrollInput, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isRawat();
            isPsien();
        }else{            
            Valid.pindah(evt,TCari,Tanggal);
        }
}//GEN-LAST:event_TNoRwKeyPressed

    private void TPasienKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TPasienKeyPressed
        Valid.pindah(evt,TCari,BtnSimpan);
}//GEN-LAST:event_TPasienKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"pasien");
        }else if(KdPetugas.getText().trim().equals("")||NmPetugas.getText().trim().equals("")){
            Valid.textKosong(KdPetugas,"Petugas");
        }else if(BB.getText().trim().equals("")){
            Valid.textKosong(BB,"Berat Badan");
        }else if(TBPB.getText().trim().equals("")){
            Valid.textKosong(TBPB,"TB/PB");
        }else if(TD.getText().trim().equals("")){
            Valid.textKosong(TD,"TD");
        }else if(HR.getText().trim().equals("")){
            Valid.textKosong(HR,"HR");
        }else if(RR.getText().trim().equals("")){
            Valid.textKosong(RR,"RR");
        }else if(Suhu.getText().trim().equals("")){
            Valid.textKosong(Suhu,"Suhu");
        }else if(SpO2.getText().trim().equals("")){
            Valid.textKosong(SpO2,"SpO2");
        }else{
            if(R1.isSelected()==true){
                if(Sequel.menyimpantf("skrining_nutrisi_lansia","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","Data",25,new String[]{
                    TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),
                    TD.getText(),HR.getText(),RR.getText(),Suhu.getText(),BB.getText(),TBPB.getText(),SpO2.getText(),Alergi.getText(),SG1.getSelectedItem().toString(),
                    Nilai1.getText(),SG2.getSelectedItem().toString(),Nilai2.getText(),SG3.getSelectedItem().toString(),Nilai3.getText(),SG4.getSelectedItem().toString(),
                    Nilai4.getText(),SG5.getSelectedItem().toString(),Nilai5.getText(),SG6.getSelectedItem().toString(),Nilai6.getText(),TotalHasil.getText(),
                    LabelSkrining.getText(),KdPetugas.getText()
                })==true){
                    runBackground(() ->tampil());
                    emptTeks();
                } 
            }else{
                if(Sequel.menyimpantf("skrining_nutrisi_lansia","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","Data",25,new String[]{
                    TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),
                    TD.getText(),HR.getText(),RR.getText(),Suhu.getText(),BB.getText(),TBPB.getText(),SpO2.getText(),Alergi.getText(),SG1.getSelectedItem().toString(),
                    Nilai1.getText(),SG2.getSelectedItem().toString(),Nilai2.getText(),SG3.getSelectedItem().toString(),Nilai3.getText(),SG4.getSelectedItem().toString(),
                    Nilai4.getText(),SG5.getSelectedItem().toString(),Nilai5.getText(),SG7.getSelectedItem().toString(),Nilai7.getText(),TotalHasil.getText(),
                    LabelSkrining.getText(),KdPetugas.getText()
                })==true){
                    runBackground(() ->tampil());
                    emptTeks();
                }
            } 
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            //Valid.pindah(evt,cmbSkor3,BtnBatal);
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
        }else{Valid.pindah(evt, BtnSimpan, BtnHapus);}
}//GEN-LAST:event_BtnBatalKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if(tbObat.getSelectedRow()>-1){
            if(akses.getkode().equals("Admin Utama")){
                hapus();
            }else{
                if(KdPetugas.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString())){
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
        if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"pasien");
        }else if(KdPetugas.getText().trim().equals("")||NmPetugas.getText().trim().equals("")){
            Valid.textKosong(KdPetugas,"Petugas");
        }else if(BB.getText().trim().equals("")){
            Valid.textKosong(BB,"Berat Badan");
        }else if(TBPB.getText().trim().equals("")){
            Valid.textKosong(TBPB,"TB/PB");
        }else if(TD.getText().trim().equals("")){
            Valid.textKosong(TD,"TD");
        }else if(HR.getText().trim().equals("")){
            Valid.textKosong(HR,"HR");
        }else if(RR.getText().trim().equals("")){
            Valid.textKosong(RR,"RR");
        }else if(Suhu.getText().trim().equals("")){
            Valid.textKosong(Suhu,"Suhu");
        }else if(SpO2.getText().trim().equals("")){
            Valid.textKosong(SpO2,"SpO2");
        }else{  
            if(tbObat.getSelectedRow()>-1){
                if(akses.getkode().equals("Admin Utama")){
                    ganti();
                }else{
                    if(KdPetugas.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString())){
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
                if(TCari.getText().trim().equals("")){
                    ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,skrining_nutrisi_lansia.tanggal,"+
                        "skrining_nutrisi_lansia.td,skrining_nutrisi_lansia.hr,skrining_nutrisi_lansia.rr,skrining_nutrisi_lansia.suhu,"+
                        "skrining_nutrisi_lansia.bb,skrining_nutrisi_lansia.tbpb,skrining_nutrisi_lansia.spo2,skrining_nutrisi_lansia.alergi,"+
                        "skrining_nutrisi_lansia.sg1,skrining_nutrisi_lansia.nilai1,skrining_nutrisi_lansia.sg2,skrining_nutrisi_lansia.nilai2,"+
                        "skrining_nutrisi_lansia.sg3,skrining_nutrisi_lansia.nilai3,skrining_nutrisi_lansia.sg4,skrining_nutrisi_lansia.nilai4,"+
                        "skrining_nutrisi_lansia.sg5,skrining_nutrisi_lansia.nilai5,skrining_nutrisi_lansia.sg6,skrining_nutrisi_lansia.nilai6,"+
                        "skrining_nutrisi_lansia.total_hasil,skrining_nutrisi_lansia.skor_nutrisi,skrining_nutrisi_lansia.nip,petugas.nama,"+
                        "pasien.jk from skrining_nutrisi_lansia inner join reg_periksa on skrining_nutrisi_lansia.no_rawat=reg_periksa.no_rawat "+
                        "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join petugas on skrining_nutrisi_lansia.nip=petugas.nip "+
                        "where skrining_nutrisi_lansia.tanggal between ? and ? order by skrining_nutrisi_lansia.tanggal ");
                }else{
                    ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,skrining_nutrisi_lansia.tanggal,"+
                        "skrining_nutrisi_lansia.td,skrining_nutrisi_lansia.hr,skrining_nutrisi_lansia.rr,skrining_nutrisi_lansia.suhu,"+
                        "skrining_nutrisi_lansia.bb,skrining_nutrisi_lansia.tbpb,skrining_nutrisi_lansia.spo2,skrining_nutrisi_lansia.alergi,"+
                        "skrining_nutrisi_lansia.sg1,skrining_nutrisi_lansia.nilai1,skrining_nutrisi_lansia.sg2,skrining_nutrisi_lansia.nilai2,"+
                        "skrining_nutrisi_lansia.sg3,skrining_nutrisi_lansia.nilai3,skrining_nutrisi_lansia.sg4,skrining_nutrisi_lansia.nilai4,"+
                        "skrining_nutrisi_lansia.sg5,skrining_nutrisi_lansia.nilai5,skrining_nutrisi_lansia.sg6,skrining_nutrisi_lansia.nilai6,"+
                        "skrining_nutrisi_lansia.total_hasil,skrining_nutrisi_lansia.skor_nutrisi,skrining_nutrisi_lansia.nip,petugas.nama,"+
                        "pasien.jk from skrining_nutrisi_lansia inner join reg_periksa on skrining_nutrisi_lansia.no_rawat=reg_periksa.no_rawat "+
                        "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join petugas on skrining_nutrisi_lansia.nip=petugas.nip "+
                        "where skrining_nutrisi_lansia.tanggal between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or "+
                        "pasien.nm_pasien like ? or skrining_nutrisi_lansia.alergi like ? or skrining_nutrisi_lansia.nip like ? or petugas.nama like ?) "+
                        "order by skrining_nutrisi_lansia.tanggal ");
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
                    htmlContent = new StringBuilder();
                    htmlContent.append(                             
                        "<tr class='isi'>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>No.Rawat</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>No.RM</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Nama Pasien</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Tgl.Lahir</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>J.K.</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Kode Petugas</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Nama Petugas</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Tanggal</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>BB(Kg)</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>TB/PB(Cm)</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>TD(mmHg)</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>HR(x/menit)</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>RR(x/menit)</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Suhu</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>SpO2</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Alergi</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Skrining Gizi 1</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Nilai 1</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Skrining Gizi 2</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Nilai 2</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Skrining Gizi 3</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Nilai 3</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Skrining Gizi 4</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Nilai 4</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Skrining Gizi 5</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Nilai 5</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Skrining Gizi 6</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Nilai 6</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Total Skor</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Hasil Skrining</b></td>"+
                        "</tr>"
                    );
                    while(rs.next()){
                        htmlContent.append(
                            "<tr class='isi'>"+
                               "<td valign='top'>"+rs.getString("no_rawat")+"</td>"+
                               "<td valign='top'>"+rs.getString("no_rkm_medis")+"</td>"+
                               "<td valign='top'>"+rs.getString("nm_pasien")+"</td>"+
                               "<td valign='top'>"+rs.getString("tgl_lahir")+"</td>"+
                               "<td valign='top'>"+rs.getString("jk")+"</td>"+
                               "<td valign='top'>"+rs.getString("nip")+"</td>"+
                               "<td valign='top'>"+rs.getString("nama")+"</td>"+
                               "<td valign='top'>"+rs.getString("tanggal")+"</td>"+
                               "<td valign='top'>"+rs.getString("bb")+"</td>"+
                               "<td valign='top'>"+rs.getString("tbpb")+"</td>"+
                               "<td valign='top'>"+rs.getString("td")+"</td>"+
                               "<td valign='top'>"+rs.getString("hr")+"</td>"+
                               "<td valign='top'>"+rs.getString("rr")+"</td>"+
                               "<td valign='top'>"+rs.getString("suhu")+"</td>"+
                               "<td valign='top'>"+rs.getString("spo2")+"</td>"+
                               "<td valign='top'>"+rs.getString("alergi")+"</td>"+
                               "<td valign='top'>"+rs.getString("sg1")+"</td>"+
                               "<td valign='top'>"+rs.getString("nilai1")+"</td>"+
                               "<td valign='top'>"+rs.getString("sg2")+"</td>"+
                               "<td valign='top'>"+rs.getString("nilai2")+"</td>"+
                               "<td valign='top'>"+rs.getString("sg3")+"</td>"+
                               "<td valign='top'>"+rs.getString("nilai3")+"</td>"+
                               "<td valign='top'>"+rs.getString("sg4")+"</td>"+
                               "<td valign='top'>"+rs.getString("nilai4")+"</td>"+
                               "<td valign='top'>"+rs.getString("sg5")+"</td>"+
                               "<td valign='top'>"+rs.getString("nilai5")+"</td>"+
                               "<td valign='top'>"+rs.getString("sg6")+"</td>"+
                               "<td valign='top'>"+rs.getString("nilai6")+"</td>"+
                               "<td valign='top'>"+rs.getString("total_hasil")+"</td>"+
                               "<td valign='top'>"+rs.getString("skor_nutrisi")+"</td>"+
                            "</tr>");
                    }
                    LoadHTML.setText(
                        "<html>"+
                          "<table width='2200px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
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

                    File f = new File("DataSkriningNutrisiLansia.html");            
                    BufferedWriter bw = new BufferedWriter(new FileWriter(f));            
                    bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                                "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                                "<table width='2200px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                    "<tr class='isi2'>"+
                                        "<td valign='top' align='center'>"+
                                            "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                            akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                            akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                            "<font size='2' face='Tahoma'>DATA SKRINING NUTRISI PASIEN LANSIA<br><br></font>"+        
                                        "</td>"+
                                   "</tr>"+
                                "</table>")
                    );
                    bw.close();                         
                    Desktop.getDesktop().browse(f.toURI());
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

    private void TNoRMKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRMKeyPressed
        // Valid.pindah(evt, TNm, BtnSimpan);
}//GEN-LAST:event_TNoRMKeyPressed

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
        Valid.pindah(evt,Menit,BtnPetugas);
    }//GEN-LAST:event_DetikKeyPressed

    private void KdPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdPetugasKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            NmPetugas.setText(Sequel.CariPetugas(KdPetugas.getText()));
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Detik.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Alergi.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnPetugasActionPerformed(null);
        }
    }//GEN-LAST:event_KdPetugasKeyPressed

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

    private void AlergiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlergiKeyPressed
        Valid.pindah(evt,SpO2,SG1);
    }//GEN-LAST:event_AlergiKeyPressed

    private void BtnPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPetugasKeyPressed
        Valid.pindah(evt,Detik,BB);
    }//GEN-LAST:event_BtnPetugasKeyPressed

    private void MnSkriningNutrisiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnSkriningNutrisiActionPerformed
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
            param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbObat.getValueAt(tbObat.getSelectedRow(),6).toString()+"\nID "+(finger.equals("")?tbObat.getValueAt(tbObat.getSelectedRow(),5).toString():finger)+"\n"+Tanggal.getSelectedItem()); 
            Valid.MyReportqry("rptFormulirSkriningNutrisiLansia.jasper","report","::[ Formulir Skrining Nutrisi Pasien Lansia ]::",
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,skrining_nutrisi_lansia.tanggal,"+
                    "skrining_nutrisi_lansia.td,skrining_nutrisi_lansia.hr,skrining_nutrisi_lansia.rr,skrining_nutrisi_lansia.suhu,"+
                    "skrining_nutrisi_lansia.bb,skrining_nutrisi_lansia.tbpb,skrining_nutrisi_lansia.spo2,skrining_nutrisi_lansia.alergi,"+
                    "skrining_nutrisi_lansia.sg1,skrining_nutrisi_lansia.nilai1,skrining_nutrisi_lansia.sg2,skrining_nutrisi_lansia.nilai2,"+
                    "skrining_nutrisi_lansia.sg3,skrining_nutrisi_lansia.nilai3,skrining_nutrisi_lansia.sg4,skrining_nutrisi_lansia.nilai4,"+
                    "skrining_nutrisi_lansia.sg5,skrining_nutrisi_lansia.nilai5,skrining_nutrisi_lansia.sg6,skrining_nutrisi_lansia.nilai6,"+
                    "skrining_nutrisi_lansia.total_hasil,skrining_nutrisi_lansia.skor_nutrisi,skrining_nutrisi_lansia.nip,petugas.nama,"+
                    "pasien.jk from skrining_nutrisi_lansia inner join reg_periksa on skrining_nutrisi_lansia.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join petugas on skrining_nutrisi_lansia.nip=petugas.nip "+
                    "where reg_periksa.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'",param);
        }
    }//GEN-LAST:event_MnSkriningNutrisiActionPerformed

    private void BBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BBKeyPressed
        Valid.pindah(evt,BtnPetugas,TBPB);
    }//GEN-LAST:event_BBKeyPressed

    private void TBPBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TBPBKeyPressed
        Valid.pindah(evt,BB,TD);
    }//GEN-LAST:event_TBPBKeyPressed

    private void SG1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SG1ItemStateChanged
        Nilai1.setText(SG1.getSelectedIndex()+"");
        isTotal();
    }//GEN-LAST:event_SG1ItemStateChanged

    private void SG1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SG1KeyPressed
        Valid.pindah(evt,Alergi,SG2);
    }//GEN-LAST:event_SG1KeyPressed

    private void SG2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SG2ItemStateChanged
        Nilai2.setText(SG2.getSelectedIndex()+"");
        isTotal();
    }//GEN-LAST:event_SG2ItemStateChanged

    private void SG2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SG2KeyPressed
        Valid.pindah(evt,SG1,SG3);
    }//GEN-LAST:event_SG2KeyPressed

    private void TDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TDKeyPressed
        Valid.pindah(evt,TBPB,HR);
    }//GEN-LAST:event_TDKeyPressed

    private void HRKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HRKeyPressed
        Valid.pindah(evt,TD,RR);
    }//GEN-LAST:event_HRKeyPressed

    private void RRKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RRKeyPressed
        Valid.pindah(evt,HR,Suhu);
    }//GEN-LAST:event_RRKeyPressed

    private void SuhuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SuhuKeyPressed
        Valid.pindah(evt,RR,SpO2);
    }//GEN-LAST:event_SuhuKeyPressed

    private void SpO2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SpO2KeyPressed
        Valid.pindah(evt,Suhu,Alergi);
    }//GEN-LAST:event_SpO2KeyPressed

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void SG3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SG3ItemStateChanged
        Nilai3.setText(SG3.getSelectedIndex()+"");
        isTotal();
    }//GEN-LAST:event_SG3ItemStateChanged

    private void SG3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SG3KeyPressed
        Valid.pindah(evt,SG2,SG4);
    }//GEN-LAST:event_SG3KeyPressed

    private void SG4ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SG4ItemStateChanged
        Nilai4.setText(SG4.getSelectedIndex()+"");
        isTotal();
    }//GEN-LAST:event_SG4ItemStateChanged

    private void SG4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SG4KeyPressed
        Valid.pindah(evt,SG3,SG5);
    }//GEN-LAST:event_SG4KeyPressed

    private void SG6ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SG6ItemStateChanged
        Nilai6.setText(SG6.getSelectedIndex()+"");
        isTotal();
    }//GEN-LAST:event_SG6ItemStateChanged

    private void SG6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SG6KeyPressed
       Valid.pindah(evt,SG5,SG7);
    }//GEN-LAST:event_SG6KeyPressed

    private void SG5ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SG5ItemStateChanged
        Nilai5.setText(SG5.getSelectedIndex()+"");
        isTotal();
    }//GEN-LAST:event_SG5ItemStateChanged

    private void SG5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SG5KeyPressed
        Valid.pindah(evt,SG4,SG6);
    }//GEN-LAST:event_SG5KeyPressed

    private void SG7ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SG7ItemStateChanged
        if(SG7.getSelectedIndex()==0){
            Nilai7.setText("0");
        }else{
            Nilai7.setText("3");
        }
        isTotal();
    }//GEN-LAST:event_SG7ItemStateChanged

    private void SG7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SG7KeyPressed
        Valid.pindah(evt,SG6,BtnSimpan);
    }//GEN-LAST:event_SG7KeyPressed

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
            RMSkriningNutrisiLansia dialog = new RMSkriningNutrisiLansia(new javax.swing.JFrame(), true);
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
    private widget.TextBox BB;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPetugas;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkInput;
    private widget.CekBox ChkKejadian;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.ComboBox Detik;
    private widget.PanelBiasa FormInput;
    private widget.TextBox HR;
    private widget.ComboBox Jam;
    private widget.TextBox KdPetugas;
    private widget.Label LCount;
    private widget.Label LabelSkrining;
    private widget.editorpane LoadHTML;
    private widget.ComboBox Menit;
    private javax.swing.JMenuItem MnSkriningNutrisi;
    private widget.TextBox Nilai1;
    private widget.TextBox Nilai2;
    private widget.TextBox Nilai3;
    private widget.TextBox Nilai4;
    private widget.TextBox Nilai5;
    private widget.TextBox Nilai6;
    private widget.TextBox Nilai7;
    private widget.TextBox NmPetugas;
    private javax.swing.JPanel PanelInput;
    private widget.RadioButton R1;
    private widget.RadioButton R2;
    private widget.TextBox RR;
    private widget.ComboBox SG1;
    private widget.ComboBox SG2;
    private widget.ComboBox SG3;
    private widget.ComboBox SG4;
    private widget.ComboBox SG5;
    private widget.ComboBox SG6;
    private widget.ComboBox SG7;
    private widget.ScrollPane Scroll;
    private widget.TextBox SpO2;
    private widget.TextBox Suhu;
    private widget.TextBox TBPB;
    private widget.TextBox TCari;
    private widget.TextBox TD;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.Tanggal Tanggal;
    private widget.TextBox TglLahir;
    private widget.TextBox TotalHasil;
    private javax.swing.ButtonGroup buttonGroup1;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel12;
    private widget.Label jLabel13;
    private widget.Label jLabel15;
    private widget.Label jLabel16;
    private widget.Label jLabel18;
    private widget.Label jLabel19;
    private widget.Label jLabel21;
    private widget.Label jLabel24;
    private widget.Label jLabel25;
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
    private widget.Label jLabel4;
    private widget.Label jLabel43;
    private widget.Label jLabel44;
    private widget.Label jLabel45;
    private widget.Label jLabel46;
    private widget.Label jLabel47;
    private widget.Label jLabel48;
    private widget.Label jLabel49;
    private widget.Label jLabel6;
    private widget.Label jLabel69;
    private widget.Label jLabel7;
    private widget.Label jLabel70;
    private widget.Label jLabel71;
    private widget.Label jLabel72;
    private widget.Label jLabel73;
    private widget.Label jLabel74;
    private widget.Label jLabel75;
    private widget.Label jLabel76;
    private widget.Label jLabel8;
    private widget.Label jLabel92;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
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
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,skrining_nutrisi_lansia.tanggal,"+
                    "skrining_nutrisi_lansia.td,skrining_nutrisi_lansia.hr,skrining_nutrisi_lansia.rr,skrining_nutrisi_lansia.suhu,"+
                    "skrining_nutrisi_lansia.bb,skrining_nutrisi_lansia.tbpb,skrining_nutrisi_lansia.spo2,skrining_nutrisi_lansia.alergi,"+
                    "skrining_nutrisi_lansia.sg1,skrining_nutrisi_lansia.nilai1,skrining_nutrisi_lansia.sg2,skrining_nutrisi_lansia.nilai2,"+
                    "skrining_nutrisi_lansia.sg3,skrining_nutrisi_lansia.nilai3,skrining_nutrisi_lansia.sg4,skrining_nutrisi_lansia.nilai4,"+
                    "skrining_nutrisi_lansia.sg5,skrining_nutrisi_lansia.nilai5,skrining_nutrisi_lansia.sg6,skrining_nutrisi_lansia.nilai6,"+
                    "skrining_nutrisi_lansia.total_hasil,skrining_nutrisi_lansia.skor_nutrisi,skrining_nutrisi_lansia.nip,petugas.nama,"+
                    "pasien.jk from skrining_nutrisi_lansia inner join reg_periksa on skrining_nutrisi_lansia.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join petugas on skrining_nutrisi_lansia.nip=petugas.nip "+
                    "where skrining_nutrisi_lansia.tanggal between ? and ? order by skrining_nutrisi_lansia.tanggal ");
            }else{
                ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,skrining_nutrisi_lansia.tanggal,"+
                    "skrining_nutrisi_lansia.td,skrining_nutrisi_lansia.hr,skrining_nutrisi_lansia.rr,skrining_nutrisi_lansia.suhu,"+
                    "skrining_nutrisi_lansia.bb,skrining_nutrisi_lansia.tbpb,skrining_nutrisi_lansia.spo2,skrining_nutrisi_lansia.alergi,"+
                    "skrining_nutrisi_lansia.sg1,skrining_nutrisi_lansia.nilai1,skrining_nutrisi_lansia.sg2,skrining_nutrisi_lansia.nilai2,"+
                    "skrining_nutrisi_lansia.sg3,skrining_nutrisi_lansia.nilai3,skrining_nutrisi_lansia.sg4,skrining_nutrisi_lansia.nilai4,"+
                    "skrining_nutrisi_lansia.sg5,skrining_nutrisi_lansia.nilai5,skrining_nutrisi_lansia.sg6,skrining_nutrisi_lansia.nilai6,"+
                    "skrining_nutrisi_lansia.total_hasil,skrining_nutrisi_lansia.skor_nutrisi,skrining_nutrisi_lansia.nip,petugas.nama,"+
                    "pasien.jk from skrining_nutrisi_lansia inner join reg_periksa on skrining_nutrisi_lansia.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join petugas on skrining_nutrisi_lansia.nip=petugas.nip "+
                    "where skrining_nutrisi_lansia.tanggal between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or "+
                    "pasien.nm_pasien like ? or skrining_nutrisi_lansia.alergi like ? or skrining_nutrisi_lansia.nip like ? or petugas.nama like ?) "+
                    "order by skrining_nutrisi_lansia.tanggal ");
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
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getDate("tgl_lahir"),rs.getString("jk"),
                        rs.getString("nip"),rs.getString("nama"),rs.getString("tanggal"),rs.getString("bb"),rs.getString("tbpb"),rs.getString("td"),
                        rs.getString("hr"),rs.getString("rr"),rs.getString("suhu"),rs.getString("spo2"),rs.getString("alergi"),rs.getString("sg1"),
                        rs.getString("nilai1"),rs.getString("sg2"),rs.getString("nilai2"),rs.getString("sg3"),rs.getString("nilai3"),rs.getString("sg4"),
                        rs.getString("nilai4"),rs.getString("sg5"),rs.getString("nilai5"),rs.getString("sg6"),rs.getString("nilai6"),rs.getString("total_hasil"),
                        rs.getString("skor_nutrisi")
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
        BB.setText("");
        TBPB.setText("");
        TD.setText("");
        HR.setText("");
        RR.setText("");
        Suhu.setText("");
        SpO2.setText("");
        Alergi.setText("");
        Tanggal.setDate(new Date());
        SG1.setSelectedIndex(0);
        Nilai1.setText("0");
        SG2.setSelectedIndex(0);
        Nilai2.setText("0");
        SG3.setSelectedIndex(0);
        Nilai3.setText("0");
        SG4.setSelectedIndex(0);
        Nilai4.setText("0");
        SG6.setSelectedIndex(0);
        Nilai5.setText("0");
        SG5.setSelectedIndex(0);
        Nilai6.setText("0");
        SG7.setSelectedIndex(0);
        Nilai7.setText("0");
        TotalHasil.setText("0");
        LabelSkrining.setText("Malnutrisi");
        BB.requestFocus();
    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            Jam.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString().substring(11,13));
            Menit.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString().substring(14,15));
            Detik.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString().substring(17,19));
            BB.setText(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            TBPB.setText(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            TD.setText(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            HR.setText(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            RR.setText(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            Suhu.setText(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            SpO2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            Alergi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
            SG1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            Nilai1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());
            SG2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
            Nilai2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
            SG3.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString());
            Nilai3.setText(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString());
            SG4.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString());
            Nilai4.setText(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString());
            SG5.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),24).toString());
            Nilai5.setText(tbObat.getValueAt(tbObat.getSelectedRow(),25).toString());
            if(tbObat.getValueAt(tbObat.getSelectedRow(),26).toString().toLowerCase().contains("lingkar")){
                R2.setSelected(true);
                SG7.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),26).toString());
                Nilai7.setText(tbObat.getValueAt(tbObat.getSelectedRow(),27).toString());
            }else{
                R1.setSelected(true);
                SG6.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),26).toString());
                Nilai6.setText(tbObat.getValueAt(tbObat.getSelectedRow(),27).toString());
            }
                
            TotalHasil.setText(tbObat.getValueAt(tbObat.getSelectedRow(),28).toString());
            LabelSkrining.setText(tbObat.getValueAt(tbObat.getSelectedRow(),29).toString());
            Valid.SetTgl(Tanggal,tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());  
        }
    }
    private void isRawat() {
         Sequel.cariIsi("select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat='"+TNoRw.getText()+"' ",TNoRM);
    }

    private void isPsien() {
        Sequel.cariIsi("select pasien.nm_pasien from pasien where pasien.no_rkm_medis='"+TNoRM.getText()+"' ",TPasien);
        Sequel.cariIsi("select DATE_FORMAT(pasien.tgl_lahir,'%d-%m-%Y') from pasien where pasien.no_rkm_medis=? ",TglLahir,TNoRM.getText());
    }
    
    public void setNoRm(String norwt, Date tgl2) {
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        Sequel.cariIsi("select reg_periksa.tgl_registrasi from reg_periksa where reg_periksa.no_rawat='"+norwt+"'", DTPCari1);
        DTPCari2.setDate(tgl2);
        isRawat();
        isPsien();
        ChkInput.setSelected(true);
        isForm();
        runBackground(() ->tampil());
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            if(internalFrame1.getHeight()>588){
                ChkInput.setVisible(false);
                PanelInput.setPreferredSize(new Dimension(WIDTH,416));
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
        BtnSimpan.setEnabled(akses.getskrining_nutrisi_lansia());
        BtnHapus.setEnabled(akses.getskrining_nutrisi_lansia());
        BtnEdit.setEnabled(akses.getskrining_nutrisi_lansia());
        BtnPrint.setEnabled(akses.getskrining_nutrisi_lansia()); 
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
        if(R1.isSelected()==true){
            Sequel.mengedit("skrining_nutrisi_lansia","no_rawat=?","no_rawat=?,tanggal=?,td=?,hr=?,rr=?,suhu=?,bb=?,tbpb=?,spo2=?,alergi=?,sg1=?,nilai1=?,sg2=?,"+
                    "nilai2=?,sg3=?,nilai3=?,sg4=?,nilai4=?,sg5=?,nilai5=?,sg6=?,nilai6=?,total_hasil=?,skor_nutrisi=?,nip=?",26,new String[]{
                    TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),
                    TD.getText(),HR.getText(),RR.getText(),Suhu.getText(),BB.getText(),TBPB.getText(),SpO2.getText(),Alergi.getText(),SG1.getSelectedItem().toString(),
                    Nilai1.getText(),SG2.getSelectedItem().toString(),Nilai2.getText(),SG3.getSelectedItem().toString(),Nilai3.getText(),SG4.getSelectedItem().toString(),
                    Nilai4.getText(),SG5.getSelectedItem().toString(),Nilai5.getText(),SG6.getSelectedItem().toString(),Nilai6.getText(),TotalHasil.getText(),
                    LabelSkrining.getText(),KdPetugas.getText(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
            });
        }else{
            Sequel.mengedit("skrining_nutrisi_lansia","no_rawat=?","no_rawat=?,tanggal=?,td=?,hr=?,rr=?,suhu=?,bb=?,tbpb=?,spo2=?,alergi=?,sg1=?,nilai1=?,sg2=?,"+
                    "nilai2=?,sg3=?,nilai3=?,sg4=?,nilai4=?,sg5=?,nilai5=?,sg6=?,nilai6=?,total_hasil=?,skor_nutrisi=?,nip=?",26,new String[]{
                    TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),
                    TD.getText(),HR.getText(),RR.getText(),Suhu.getText(),BB.getText(),TBPB.getText(),SpO2.getText(),Alergi.getText(),SG1.getSelectedItem().toString(),
                    Nilai1.getText(),SG2.getSelectedItem().toString(),Nilai2.getText(),SG3.getSelectedItem().toString(),Nilai3.getText(),SG4.getSelectedItem().toString(),
                    Nilai4.getText(),SG5.getSelectedItem().toString(),Nilai5.getText(),SG7.getSelectedItem().toString(),Nilai7.getText(),TotalHasil.getText(),
                    LabelSkrining.getText(),KdPetugas.getText(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
            });
        }
            
        if(tabMode.getRowCount()!=0){runBackground(() ->tampil());}
        emptTeks();
    }

    private void hapus() {
        if(Sequel.queryu2tf("delete from skrining_nutrisi_lansia where no_rawat=?",1,new String[]{
            tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
        })==true){
            tabMode.removeRow(tbObat.getSelectedRow());
            LCount.setText(""+tabMode.getRowCount());
            emptTeks();
        }else{
            JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
        }
    }

    private void isTotal() {
        try {
            if(R1.isSelected()==true){
                TotalHasil.setText(""+(Integer.parseInt(Nilai1.getText())+Integer.parseInt(Nilai2.getText())+Integer.parseInt(Nilai3.getText())+Integer.parseInt(Nilai4.getText())+Integer.parseInt(Nilai5.getText())+Integer.parseInt(Nilai6.getText())));
            }else{
                TotalHasil.setText(""+(Integer.parseInt(Nilai1.getText())+Integer.parseInt(Nilai2.getText())+Integer.parseInt(Nilai3.getText())+Integer.parseInt(Nilai4.getText())+Integer.parseInt(Nilai5.getText())+Integer.parseInt(Nilai7.getText())));
            }
            if((Integer.parseInt(TotalHasil.getText())>=12)&&(Integer.parseInt(TotalHasil.getText())<=14)){
                LabelSkrining.setText("Status Gizi Normal");
            }else if((Integer.parseInt(TotalHasil.getText())>=8)&&(Integer.parseInt(TotalHasil.getText())<=11)){
                LabelSkrining.setText("Beresiko Malnutrisi");
            }else if((Integer.parseInt(TotalHasil.getText())>=0)&&(Integer.parseInt(TotalHasil.getText())<=7)){
                LabelSkrining.setText("Malnutrisi");
            }
        } catch (Exception e) {
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

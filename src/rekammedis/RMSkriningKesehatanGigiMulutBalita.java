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
import java.awt.event.WindowListener;
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
public final class RMSkriningKesehatanGigiMulutBalita extends javax.swing.JDialog {
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
    private String TANGGALMUNDUR="yes";
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public RMSkriningKesehatanGigiMulutBalita(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(628,674);

        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.RM","Nama Pasien","Tgl.Lahir","J.K.","Kode Petugas","Nama Petugas","Tanggal",
            "Pemeriksaan Gigi Mulut","Ada Gigi Tumbuh","Jumlah Gigi","Kebersihan Gigi","Minum Susu Botol",
            "Mengemil Manis","Menyikat Gigi","Mengemut Makanan","Lidah Kotor","Punya Celah Bibir",
            "Hasil Skrining","Keterangan"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 20; i++) {
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
                column.setPreferredWidth(123);
            }else if(i==9){
                column.setPreferredWidth(91);
            }else if(i==10){
                column.setPreferredWidth(66);
            }else if(i==11){
                column.setPreferredWidth(84);
            }else if(i==12){
                column.setPreferredWidth(95);
            }else if(i==13){
                column.setPreferredWidth(86);
            }else if(i==14){
                column.setPreferredWidth(72);
            }else if(i==15){
                column.setPreferredWidth(110);
            }else if(i==16){
                column.setPreferredWidth(64);
            }else if(i==17){
                column.setPreferredWidth(94);
            }else if(i==18){
                column.setPreferredWidth(200);
            }else if(i==19){
                column.setPreferredWidth(200);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());

        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        KdPetugas.setDocument(new batasInput((byte)20).getKata(KdPetugas));
        Keterangan.setDocument(new batasInput((int)100).getKata(Keterangan));
        TCari.setDocument(new batasInput((int)100).getKata(TCari));
        HasilSkrining.setDocument(new batasInput((int)50).getKata(HasilSkrining));
        
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

        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnSkriningKesehatanGigiMulutBalita = new javax.swing.JMenuItem();
        LoadHTML = new widget.editorpane();
        Jk = new widget.TextBox();
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
        KdPetugas = new widget.TextBox();
        NmPetugas = new widget.TextBox();
        BtnPetugas = new widget.Button();
        jLabel8 = new widget.Label();
        TglLahir = new widget.TextBox();
        PernahPemeriksaanGigi = new widget.ComboBox();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel99 = new widget.Label();
        jLabel77 = new widget.Label();
        jLabel78 = new widget.Label();
        jLabel79 = new widget.Label();
        jLabel80 = new widget.Label();
        SudahAdaGigiTumbuh = new widget.ComboBox();
        JumlahGigiSudahTumbuh = new widget.ComboBox();
        jLabel81 = new widget.Label();
        jLabel82 = new widget.Label();
        jLabel83 = new widget.Label();
        jLabel84 = new widget.Label();
        jLabel85 = new widget.Label();
        KebersihanGigiMulut = new widget.ComboBox();
        jLabel86 = new widget.Label();
        jLabel87 = new widget.Label();
        jLabel88 = new widget.Label();
        MengemilManis = new widget.ComboBox();
        KebiasaanSusuBotol = new widget.ComboBox();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel101 = new widget.Label();
        HasilSkrining = new widget.TextBox();
        jLabel22 = new widget.Label();
        jLabel93 = new widget.Label();
        Keterangan = new widget.TextBox();
        jLabel27 = new widget.Label();
        jLabel89 = new widget.Label();
        jLabel90 = new widget.Label();
        MenyikatGigi = new widget.ComboBox();
        jLabel91 = new widget.Label();
        jLabel95 = new widget.Label();
        MengemutMakanan = new widget.ComboBox();
        LidahAnak = new widget.ComboBox();
        jLabel96 = new widget.Label();
        jLabel97 = new widget.Label();
        jLabel98 = new widget.Label();
        jLabel103 = new widget.Label();
        CelahBibir = new widget.ComboBox();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnSkriningKesehatanGigiMulutBalita.setBackground(new java.awt.Color(255, 255, 254));
        MnSkriningKesehatanGigiMulutBalita.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSkriningKesehatanGigiMulutBalita.setForeground(new java.awt.Color(50, 50, 50));
        MnSkriningKesehatanGigiMulutBalita.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnSkriningKesehatanGigiMulutBalita.setText("Formulir Skrining Kesehatan Gigi & Mulut Balita");
        MnSkriningKesehatanGigiMulutBalita.setName("MnSkriningKesehatanGigiMulutBalita"); // NOI18N
        MnSkriningKesehatanGigiMulutBalita.setPreferredSize(new java.awt.Dimension(300, 26));
        MnSkriningKesehatanGigiMulutBalita.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSkriningKesehatanGigiMulutBalitaActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnSkriningKesehatanGigiMulutBalita);

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N

        Jk.setEditable(false);
        Jk.setFocusTraversalPolicyProvider(true);
        Jk.setName("Jk"); // NOI18N

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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Skrining Kesehatan Gigi & Mulut Balita ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 466));
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
        scrollInput.setPreferredSize(new java.awt.Dimension(102, 957));

        FormInput.setBackground(new java.awt.Color(250, 255, 245));
        FormInput.setBorder(null);
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(800, 443));
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

        jLabel8.setText("Tgl.Lahir :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(625, 10, 60, 23);

        TglLahir.setHighlighter(null);
        TglLahir.setName("TglLahir"); // NOI18N
        FormInput.add(TglLahir);
        TglLahir.setBounds(689, 10, 100, 23);

        PernahPemeriksaanGigi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Pernah", "Tidak Pernah" }));
        PernahPemeriksaanGigi.setName("PernahPemeriksaanGigi"); // NOI18N
        PernahPemeriksaanGigi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PernahPemeriksaanGigiKeyPressed(evt);
            }
        });
        FormInput.add(PernahPemeriksaanGigi);
        PernahPemeriksaanGigi.setBounds(674, 90, 115, 23);

        jSeparator1.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator1.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator1.setName("jSeparator1"); // NOI18N
        FormInput.add(jSeparator1);
        jSeparator1.setBounds(0, 70, 807, 1);

        jLabel99.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel99.setText("I. ANAMNESIS");
        jLabel99.setName("jLabel99"); // NOI18N
        FormInput.add(jLabel99);
        jLabel99.setBounds(10, 70, 200, 23);

        jLabel77.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel77.setText("1.");
        jLabel77.setName("jLabel77"); // NOI18N
        FormInput.add(jLabel77);
        jLabel77.setBounds(44, 90, 20, 23);

        jLabel78.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel78.setText("Apakah Pernah Melakukan Pemeriksaan Gigi & Mulut ?");
        jLabel78.setName("jLabel78"); // NOI18N
        FormInput.add(jLabel78);
        jLabel78.setBounds(61, 90, 460, 23);

        jLabel79.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel79.setText("2.");
        jLabel79.setName("jLabel79"); // NOI18N
        FormInput.add(jLabel79);
        jLabel79.setBounds(44, 120, 20, 23);

        jLabel80.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel80.setText("Apakah Sudah Ada Gigi Yang Tumbuh ?");
        jLabel80.setName("jLabel80"); // NOI18N
        FormInput.add(jLabel80);
        jLabel80.setBounds(61, 120, 370, 23);

        SudahAdaGigiTumbuh.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Sudah", "Belum" }));
        SudahAdaGigiTumbuh.setName("SudahAdaGigiTumbuh"); // NOI18N
        SudahAdaGigiTumbuh.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SudahAdaGigiTumbuhItemStateChanged(evt);
            }
        });
        SudahAdaGigiTumbuh.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SudahAdaGigiTumbuhKeyPressed(evt);
            }
        });
        FormInput.add(SudahAdaGigiTumbuh);
        SudahAdaGigiTumbuh.setBounds(709, 120, 80, 23);

        JumlahGigiSudahTumbuh.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "<20", ">20", "0" }));
        JumlahGigiSudahTumbuh.setName("JumlahGigiSudahTumbuh"); // NOI18N
        JumlahGigiSudahTumbuh.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JumlahGigiSudahTumbuhKeyPressed(evt);
            }
        });
        FormInput.add(JumlahGigiSudahTumbuh);
        JumlahGigiSudahTumbuh.setBounds(709, 150, 80, 23);

        jLabel81.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel81.setText("Berapa Jumlah Gigi Yang Sudah Tumbuh ?");
        jLabel81.setName("jLabel81"); // NOI18N
        FormInput.add(jLabel81);
        jLabel81.setBounds(61, 150, 460, 23);

        jLabel82.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel82.setText("3.");
        jLabel82.setName("jLabel82"); // NOI18N
        FormInput.add(jLabel82);
        jLabel82.setBounds(44, 150, 20, 23);

        jLabel83.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel83.setText("Bagaimana Kondisi Kebersihan Gigi & Mulut ?");
        jLabel83.setName("jLabel83"); // NOI18N
        FormInput.add(jLabel83);
        jLabel83.setBounds(61, 180, 240, 23);

        jLabel84.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel84.setText("4.");
        jLabel84.setName("jLabel84"); // NOI18N
        FormInput.add(jLabel84);
        jLabel84.setBounds(44, 180, 20, 23);

        jLabel85.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel85.setText("Apakah Anak Mempunyai Kebiasaan Minum Susu Botol Saat Tidur Malam ?");
        jLabel85.setName("jLabel85"); // NOI18N
        FormInput.add(jLabel85);
        jLabel85.setBounds(61, 210, 410, 23);

        KebersihanGigiMulut.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Bersih", "Kotor" }));
        KebersihanGigiMulut.setName("KebersihanGigiMulut"); // NOI18N
        KebersihanGigiMulut.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                KebersihanGigiMulutItemStateChanged(evt);
            }
        });
        KebersihanGigiMulut.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KebersihanGigiMulutKeyPressed(evt);
            }
        });
        FormInput.add(KebersihanGigiMulut);
        KebersihanGigiMulut.setBounds(709, 180, 80, 23);

        jLabel86.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel86.setText("5.");
        jLabel86.setName("jLabel86"); // NOI18N
        FormInput.add(jLabel86);
        jLabel86.setBounds(44, 210, 20, 23);

        jLabel87.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel87.setText("7.");
        jLabel87.setName("jLabel87"); // NOI18N
        FormInput.add(jLabel87);
        jLabel87.setBounds(44, 270, 20, 23);

        jLabel88.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel88.setText("Apakah Anak Mempunyai Kebiasaan Menyikat Gigi Sebelum Tidur ?");
        jLabel88.setName("jLabel88"); // NOI18N
        FormInput.add(jLabel88);
        jLabel88.setBounds(61, 270, 420, 23);

        MengemilManis.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        MengemilManis.setName("MengemilManis"); // NOI18N
        MengemilManis.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                MengemilManisItemStateChanged(evt);
            }
        });
        MengemilManis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MengemilManisKeyPressed(evt);
            }
        });
        FormInput.add(MengemilManis);
        MengemilManis.setBounds(709, 240, 80, 23);

        KebiasaanSusuBotol.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        KebiasaanSusuBotol.setName("KebiasaanSusuBotol"); // NOI18N
        KebiasaanSusuBotol.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                KebiasaanSusuBotolItemStateChanged(evt);
            }
        });
        KebiasaanSusuBotol.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KebiasaanSusuBotolKeyPressed(evt);
            }
        });
        FormInput.add(KebiasaanSusuBotol);
        KebiasaanSusuBotol.setBounds(709, 210, 80, 23);

        jSeparator3.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator3.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator3.setName("jSeparator3"); // NOI18N
        FormInput.add(jSeparator3);
        jSeparator3.setBounds(0, 390, 807, 1);

        jLabel101.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel101.setText("II. INTERPRETASI");
        jLabel101.setName("jLabel101"); // NOI18N
        FormInput.add(jLabel101);
        jLabel101.setBounds(10, 390, 200, 23);

        HasilSkrining.setFocusTraversalPolicyProvider(true);
        HasilSkrining.setName("HasilSkrining"); // NOI18N
        HasilSkrining.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HasilSkriningKeyPressed(evt);
            }
        });
        FormInput.add(HasilSkrining);
        HasilSkrining.setBounds(121, 410, 195, 23);

        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel22.setText("Hasil Skrining");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(44, 410, 100, 23);

        jLabel93.setText("Keterangan :");
        jLabel93.setName("jLabel93"); // NOI18N
        FormInput.add(jLabel93);
        jLabel93.setBounds(320, 410, 75, 23);

        Keterangan.setFocusTraversalPolicyProvider(true);
        Keterangan.setName("Keterangan"); // NOI18N
        Keterangan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKeyPressed(evt);
            }
        });
        FormInput.add(Keterangan);
        Keterangan.setBounds(399, 410, 390, 23);

        jLabel27.setText(":");
        jLabel27.setName("jLabel27"); // NOI18N
        FormInput.add(jLabel27);
        jLabel27.setBounds(0, 410, 117, 23);

        jLabel89.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel89.setText("Apakah Anak Mempunyai Kebiasaan Mengemil Makanan Manis Diantara Waktu Makan ?");
        jLabel89.setName("jLabel89"); // NOI18N
        FormInput.add(jLabel89);
        jLabel89.setBounds(61, 240, 470, 23);

        jLabel90.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel90.setText("6.");
        jLabel90.setName("jLabel90"); // NOI18N
        FormInput.add(jLabel90);
        jLabel90.setBounds(44, 240, 20, 23);

        MenyikatGigi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        MenyikatGigi.setName("MenyikatGigi"); // NOI18N
        MenyikatGigi.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                MenyikatGigiItemStateChanged(evt);
            }
        });
        MenyikatGigi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MenyikatGigiKeyPressed(evt);
            }
        });
        FormInput.add(MenyikatGigi);
        MenyikatGigi.setBounds(709, 270, 80, 23);

        jLabel91.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel91.setText("Apakah Anak Mempunyai Kebiasaan Mengemut Makanan ?");
        jLabel91.setName("jLabel91"); // NOI18N
        FormInput.add(jLabel91);
        jLabel91.setBounds(61, 300, 390, 23);

        jLabel95.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel95.setText("8.");
        jLabel95.setName("jLabel95"); // NOI18N
        FormInput.add(jLabel95);
        jLabel95.setBounds(44, 300, 20, 23);

        MengemutMakanan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        MengemutMakanan.setName("MengemutMakanan"); // NOI18N
        MengemutMakanan.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                MengemutMakananItemStateChanged(evt);
            }
        });
        MengemutMakanan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MengemutMakananKeyPressed(evt);
            }
        });
        FormInput.add(MengemutMakanan);
        MengemutMakanan.setBounds(709, 300, 80, 23);

        LidahAnak.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        LidahAnak.setName("LidahAnak"); // NOI18N
        LidahAnak.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                LidahAnakItemStateChanged(evt);
            }
        });
        LidahAnak.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LidahAnakKeyPressed(evt);
            }
        });
        FormInput.add(LidahAnak);
        LidahAnak.setBounds(709, 330, 80, 23);

        jLabel96.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel96.setText("Apakah Lidah Anak Kotor ?");
        jLabel96.setName("jLabel96"); // NOI18N
        FormInput.add(jLabel96);
        jLabel96.setBounds(61, 330, 330, 23);

        jLabel97.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel97.setText("9.");
        jLabel97.setName("jLabel97"); // NOI18N
        FormInput.add(jLabel97);
        jLabel97.setBounds(44, 330, 20, 23);

        jLabel98.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel98.setText("10.");
        jLabel98.setName("jLabel98"); // NOI18N
        FormInput.add(jLabel98);
        jLabel98.setBounds(44, 360, 20, 23);

        jLabel103.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel103.setText("Apakah Anak Mempunyai Celah Bibir Atau Langit-langit ?");
        jLabel103.setName("jLabel103"); // NOI18N
        FormInput.add(jLabel103);
        jLabel103.setBounds(61, 360, 330, 23);

        CelahBibir.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada", "Ada" }));
        CelahBibir.setName("CelahBibir"); // NOI18N
        CelahBibir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CelahBibirKeyPressed(evt);
            }
        });
        FormInput.add(CelahBibir);
        CelahBibir.setBounds(689, 360, 100, 23);

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
            Valid.textKosong(TNoRw,"pasien");
        }else if(KdPetugas.getText().trim().equals("")||NmPetugas.getText().trim().equals("")){
            Valid.textKosong(KdPetugas,"Petugas");
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
                if(KdPetugas.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString())){
                    if(Sequel.cekTanggal48jam(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString(),Sequel.ambiltanggalsekarang())==true){
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
        if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"pasien");
        }else if(KdPetugas.getText().trim().equals("")||NmPetugas.getText().trim().equals("")){
            Valid.textKosong(KdPetugas,"Petugas");
        }else{ 
            if(tbObat.getSelectedRow()>-1){
                if(akses.getkode().equals("Admin Utama")){
                    ganti();
                }else{
                    if(KdPetugas.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString())){
                        if(Sequel.cekTanggal48jam(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString(),Sequel.ambiltanggalsekarang())==true){
                            if(TanggalRegistrasi.getText().equals("")){
                                TanggalRegistrasi.setText(Sequel.cariIsi("select concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) from reg_periksa where reg_periksa.no_rawat=?",TNoRw.getText()));
                            }
                            if(Sequel.cekTanggalRegistrasi(TanggalRegistrasi.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem())==true){
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
            try{
                StringBuilder htmlContent;
                htmlContent = new StringBuilder();
                htmlContent.append(                             
                    "<tr class='isi'>"+
                         "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Rawat</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.RM</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Pasien</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tgl.Lahir</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>J.K.</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kode Petugas</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Petugas</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tanggal</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Pemeriksaan Gigi Mulut</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Ada Gigi Tumbuh</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Jumlah Gigi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kebersihan Gigi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Minum Susu Botol</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Mengemil Manis</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Menyikat Gigi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Mengemut Makanan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Lidah Kotor</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Punya Celah Bibir</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Hasil Skrining</b></td>"+
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
                        "</tr>");
                }
                LoadHTML.setText(
                    "<html>"+
                      "<table width='1600px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
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

                File f = new File("DataSkriningKesehatanGigiMulutBalita.html");            
                BufferedWriter bw = new BufferedWriter(new FileWriter(f));            
                bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                            "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                            "<table width='1600px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                "<tr class='isi2'>"+
                                    "<td valign='top' align='center'>"+
                                        "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                        akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                        akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                        "<font size='2' face='Tahoma'>DATA SEKRINING KESEHATAN GIGI MULUT BALITA<br><br></font>"+        
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
        /*if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            NmPetugas.setText(Sequel.CariPetugas(KdPetugas.getText()));
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Detik.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Alergi.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnPetugasActionPerformed(null);
        }*/
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

    private void BtnPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPetugasKeyPressed
        //Valid.pindah(evt,Detik,BB);
    }//GEN-LAST:event_BtnPetugasKeyPressed

    private void MnSkriningKesehatanGigiMulutBalitaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnSkriningKesehatanGigiMulutBalitaActionPerformed
        if(tbObat.getSelectedRow()>-1){
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());   
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            String finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
            param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbObat.getValueAt(tbObat.getSelectedRow(),6).toString()+"\nID "+(finger.equals("")?tbObat.getValueAt(tbObat.getSelectedRow(),5).toString():finger)+"\n"+Tanggal.getSelectedItem()); 
            Valid.MyReportqry("rptFormulirSkriningKesehatanGigiMulutBalita.jasper","report","::[ Formulir Skrining Kesehatan Gigi & Mulut Balita ]::",
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,pasien.jk,skrining_kesehatan_gigi_mulut_balita.nip,petugas.nama,skrining_kesehatan_gigi_mulut_balita.tanggal,"+
                    "skrining_kesehatan_gigi_mulut_balita.pernah_pemeriksaan_gigimulut,skrining_kesehatan_gigi_mulut_balita.sudah_tumbuh_gigi,skrining_kesehatan_gigi_mulut_balita.jumlah_gigi_tumbuh,"+
                    "skrining_kesehatan_gigi_mulut_balita.kondisi_kebersihan_gigimulut,skrining_kesehatan_gigi_mulut_balita.kebiasaan_susu_botol,skrining_kesehatan_gigi_mulut_balita.mengemil_manis,"+
                    "skrining_kesehatan_gigi_mulut_balita.menyikat_gigi_sebelum_tidur,skrining_kesehatan_gigi_mulut_balita.mengemut_makanan,skrining_kesehatan_gigi_mulut_balita.lidah_kotor,"+
                    "skrining_kesehatan_gigi_mulut_balita.celah_bibir,skrining_kesehatan_gigi_mulut_balita.hasil_skrining,skrining_kesehatan_gigi_mulut_balita.keterangan from skrining_kesehatan_gigi_mulut_balita "+
                    "inner join reg_periksa on skrining_kesehatan_gigi_mulut_balita.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join petugas on skrining_kesehatan_gigi_mulut_balita.nip=petugas.nip "+
                    "where reg_periksa.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'",param);
        }
    }//GEN-LAST:event_MnSkriningKesehatanGigiMulutBalitaActionPerformed

    private void PernahPemeriksaanGigiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PernahPemeriksaanGigiKeyPressed
        Valid.pindah(evt,BtnPetugas,SudahAdaGigiTumbuh);
    }//GEN-LAST:event_PernahPemeriksaanGigiKeyPressed

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void SudahAdaGigiTumbuhKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SudahAdaGigiTumbuhKeyPressed
        Valid.pindah(evt,PernahPemeriksaanGigi,JumlahGigiSudahTumbuh);
    }//GEN-LAST:event_SudahAdaGigiTumbuhKeyPressed

    private void JumlahGigiSudahTumbuhKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JumlahGigiSudahTumbuhKeyPressed
        Valid.pindah(evt,SudahAdaGigiTumbuh,KebersihanGigiMulut);
    }//GEN-LAST:event_JumlahGigiSudahTumbuhKeyPressed

    private void KebersihanGigiMulutKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KebersihanGigiMulutKeyPressed
        Valid.pindah(evt,JumlahGigiSudahTumbuh,KebiasaanSusuBotol);
    }//GEN-LAST:event_KebersihanGigiMulutKeyPressed

    private void MengemilManisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MengemilManisKeyPressed
        Valid.pindah(evt,KebiasaanSusuBotol,MenyikatGigi);
    }//GEN-LAST:event_MengemilManisKeyPressed

    private void KebiasaanSusuBotolKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KebiasaanSusuBotolKeyPressed
        Valid.pindah(evt,KebersihanGigiMulut,MengemilManis);
    }//GEN-LAST:event_KebiasaanSusuBotolKeyPressed

    private void KeteranganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKeyPressed
        Valid.pindah(evt,HasilSkrining,BtnSimpan);
    }//GEN-LAST:event_KeteranganKeyPressed

    private void MenyikatGigiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MenyikatGigiKeyPressed
        Valid.pindah(evt,MengemilManis,MengemutMakanan);
    }//GEN-LAST:event_MenyikatGigiKeyPressed

    private void MengemutMakananKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MengemutMakananKeyPressed
        Valid.pindah(evt,MenyikatGigi,LidahAnak);
    }//GEN-LAST:event_MengemutMakananKeyPressed

    private void LidahAnakKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LidahAnakKeyPressed
        Valid.pindah(evt,MengemutMakanan,CelahBibir);
    }//GEN-LAST:event_LidahAnakKeyPressed

    private void HasilSkriningKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HasilSkriningKeyPressed
        Valid.pindah(evt,CelahBibir,Keterangan);
    }//GEN-LAST:event_HasilSkriningKeyPressed

    private void CelahBibirKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CelahBibirKeyPressed
        Valid.pindah(evt,LidahAnak,HasilSkrining);
    }//GEN-LAST:event_CelahBibirKeyPressed

    private void KebersihanGigiMulutItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_KebersihanGigiMulutItemStateChanged
        hasilInterpretasi();
    }//GEN-LAST:event_KebersihanGigiMulutItemStateChanged

    private void KebiasaanSusuBotolItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_KebiasaanSusuBotolItemStateChanged
        hasilInterpretasi();
    }//GEN-LAST:event_KebiasaanSusuBotolItemStateChanged

    private void MengemilManisItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_MengemilManisItemStateChanged
        hasilInterpretasi();
    }//GEN-LAST:event_MengemilManisItemStateChanged

    private void MenyikatGigiItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_MenyikatGigiItemStateChanged
        hasilInterpretasi();
    }//GEN-LAST:event_MenyikatGigiItemStateChanged

    private void MengemutMakananItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_MengemutMakananItemStateChanged
        hasilInterpretasi();
    }//GEN-LAST:event_MengemutMakananItemStateChanged

    private void LidahAnakItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_LidahAnakItemStateChanged
        hasilInterpretasi();
    }//GEN-LAST:event_LidahAnakItemStateChanged

    private void SudahAdaGigiTumbuhItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SudahAdaGigiTumbuhItemStateChanged
        if(SudahAdaGigiTumbuh.getSelectedIndex()==1){
            JumlahGigiSudahTumbuh.setSelectedIndex(2);
            JumlahGigiSudahTumbuh.setEnabled(false);
        }else{
            JumlahGigiSudahTumbuh.setSelectedIndex(0);
            JumlahGigiSudahTumbuh.setEnabled(true);
        }
    }//GEN-LAST:event_SudahAdaGigiTumbuhItemStateChanged

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
            RMSkriningKesehatanGigiMulutBalita dialog = new RMSkriningKesehatanGigiMulutBalita(new javax.swing.JFrame(), true);
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
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPetugas;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.ComboBox CelahBibir;
    private widget.CekBox ChkInput;
    private widget.CekBox ChkKejadian;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.ComboBox Detik;
    private widget.PanelBiasa FormInput;
    private widget.TextBox HasilSkrining;
    private widget.ComboBox Jam;
    private widget.TextBox Jk;
    private widget.ComboBox JumlahGigiSudahTumbuh;
    private widget.TextBox KdPetugas;
    private widget.ComboBox KebersihanGigiMulut;
    private widget.ComboBox KebiasaanSusuBotol;
    private widget.TextBox Keterangan;
    private widget.Label LCount;
    private widget.ComboBox LidahAnak;
    private widget.editorpane LoadHTML;
    private widget.ComboBox MengemilManis;
    private widget.ComboBox MengemutMakanan;
    private widget.ComboBox Menit;
    private widget.ComboBox MenyikatGigi;
    private javax.swing.JMenuItem MnSkriningKesehatanGigiMulutBalita;
    private widget.TextBox NmPetugas;
    private javax.swing.JPanel PanelInput;
    private widget.ComboBox PernahPemeriksaanGigi;
    private widget.ScrollPane Scroll;
    private widget.ComboBox SudahAdaGigiTumbuh;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.Tanggal Tanggal;
    private widget.TextBox TanggalRegistrasi;
    private widget.TextBox TglLahir;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel101;
    private widget.Label jLabel103;
    private widget.Label jLabel16;
    private widget.Label jLabel18;
    private widget.Label jLabel19;
    private widget.Label jLabel21;
    private widget.Label jLabel22;
    private widget.Label jLabel27;
    private widget.Label jLabel4;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
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
    private widget.Label jLabel90;
    private widget.Label jLabel91;
    private widget.Label jLabel93;
    private widget.Label jLabel95;
    private widget.Label jLabel96;
    private widget.Label jLabel97;
    private widget.Label jLabel98;
    private widget.Label jLabel99;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator3;
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
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,pasien.jk,skrining_kesehatan_gigi_mulut_balita.nip,petugas.nama,skrining_kesehatan_gigi_mulut_balita.tanggal,"+
                    "skrining_kesehatan_gigi_mulut_balita.pernah_pemeriksaan_gigimulut,skrining_kesehatan_gigi_mulut_balita.sudah_tumbuh_gigi,skrining_kesehatan_gigi_mulut_balita.jumlah_gigi_tumbuh,"+
                    "skrining_kesehatan_gigi_mulut_balita.kondisi_kebersihan_gigimulut,skrining_kesehatan_gigi_mulut_balita.kebiasaan_susu_botol,skrining_kesehatan_gigi_mulut_balita.mengemil_manis,"+
                    "skrining_kesehatan_gigi_mulut_balita.menyikat_gigi_sebelum_tidur,skrining_kesehatan_gigi_mulut_balita.mengemut_makanan,skrining_kesehatan_gigi_mulut_balita.lidah_kotor,"+
                    "skrining_kesehatan_gigi_mulut_balita.celah_bibir,skrining_kesehatan_gigi_mulut_balita.hasil_skrining,skrining_kesehatan_gigi_mulut_balita.keterangan from skrining_kesehatan_gigi_mulut_balita "+
                    "inner join reg_periksa on skrining_kesehatan_gigi_mulut_balita.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join petugas on skrining_kesehatan_gigi_mulut_balita.nip=petugas.nip "+
                    "where skrining_kesehatan_gigi_mulut_balita.tanggal between ? and ? order by skrining_kesehatan_gigi_mulut_balita.tanggal ");
            }else{
                ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,pasien.jk,skrining_kesehatan_gigi_mulut_balita.nip,petugas.nama,skrining_kesehatan_gigi_mulut_balita.tanggal,"+
                    "skrining_kesehatan_gigi_mulut_balita.pernah_pemeriksaan_gigimulut,skrining_kesehatan_gigi_mulut_balita.sudah_tumbuh_gigi,skrining_kesehatan_gigi_mulut_balita.jumlah_gigi_tumbuh,"+
                    "skrining_kesehatan_gigi_mulut_balita.kondisi_kebersihan_gigimulut,skrining_kesehatan_gigi_mulut_balita.kebiasaan_susu_botol,skrining_kesehatan_gigi_mulut_balita.mengemil_manis,"+
                    "skrining_kesehatan_gigi_mulut_balita.menyikat_gigi_sebelum_tidur,skrining_kesehatan_gigi_mulut_balita.mengemut_makanan,skrining_kesehatan_gigi_mulut_balita.lidah_kotor,"+
                    "skrining_kesehatan_gigi_mulut_balita.celah_bibir,skrining_kesehatan_gigi_mulut_balita.hasil_skrining,skrining_kesehatan_gigi_mulut_balita.keterangan from skrining_kesehatan_gigi_mulut_balita "+
                    "inner join reg_periksa on skrining_kesehatan_gigi_mulut_balita.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join petugas on skrining_kesehatan_gigi_mulut_balita.nip=petugas.nip "+
                    "where skrining_kesehatan_gigi_mulut_balita.tanggal between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or "+
                    "pasien.nm_pasien like ? or skrining_kesehatan_gigi_mulut_balita.nip like ? or petugas.nama like ?) "+
                    "order by skrining_kesehatan_gigi_mulut_balita.tanggal ");
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
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getDate("tgl_lahir"),rs.getString("jk"),rs.getString("nip"),rs.getString("nama"),rs.getString("tanggal"),
                        rs.getString("pernah_pemeriksaan_gigimulut"),rs.getString("sudah_tumbuh_gigi"),rs.getString("jumlah_gigi_tumbuh"),rs.getString("kondisi_kebersihan_gigimulut"),rs.getString("kebiasaan_susu_botol"),
                        rs.getString("mengemil_manis"),rs.getString("menyikat_gigi_sebelum_tidur"),rs.getString("mengemut_makanan"),rs.getString("lidah_kotor"),rs.getString("celah_bibir"),rs.getString("hasil_skrining"),
                        rs.getString("keterangan")
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
        PernahPemeriksaanGigi.setSelectedIndex(0);
        SudahAdaGigiTumbuh.setSelectedIndex(0);
        JumlahGigiSudahTumbuh.setSelectedIndex(0);
        KebersihanGigiMulut.setSelectedIndex(0);
        KebiasaanSusuBotol.setSelectedIndex(0);
        MengemilManis.setSelectedIndex(0);
        MenyikatGigi.setSelectedIndex(0);
        MengemutMakanan.setSelectedIndex(0);
        LidahAnak.setSelectedIndex(0);
        CelahBibir.setSelectedIndex(0);
        HasilSkrining.setText("");
        Keterangan.setText("");
        Tanggal.setDate(new Date());
        PernahPemeriksaanGigi.requestFocus();
        HasilSkrining.setText("Perlu Dirujuk ke Poli Gigi");
    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            Jk.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString());
            Jam.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString().substring(11,13));
            Menit.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString().substring(14,15));
            Detik.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString().substring(17,19));
            PernahPemeriksaanGigi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            SudahAdaGigiTumbuh.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            JumlahGigiSudahTumbuh.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            KebersihanGigiMulut.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            KebiasaanSusuBotol.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            MengemilManis.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            MenyikatGigi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            MengemutMakanan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
            LidahAnak.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            CelahBibir.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());
            HasilSkrining.setText(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
            Keterangan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
            Valid.SetTgl(Tanggal,tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());  
        }
    }
    
    private void isRawat() {
        try {
            ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,"+
                    "reg_periksa.tgl_registrasi,reg_periksa.jam_reg "+
                    "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
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
            if(internalFrame1.getHeight()>638){
                ChkInput.setVisible(false);
                PanelInput.setPreferredSize(new Dimension(WIDTH,466));
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
        BtnSimpan.setEnabled(akses.getskrining_kesehatan_gigi_mulut_balita());
        BtnHapus.setEnabled(akses.getskrining_kesehatan_gigi_mulut_balita());
        BtnEdit.setEnabled(akses.getskrining_kesehatan_gigi_mulut_balita());
        BtnPrint.setEnabled(akses.getskrining_kesehatan_gigi_mulut_balita()); 
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
        if(Sequel.mengedittf("skrining_kesehatan_gigi_mulut_balita","no_rawat=?","no_rawat=?,tanggal=?,pernah_pemeriksaan_gigimulut=?,sudah_tumbuh_gigi=?,jumlah_gigi_tumbuh=?,kondisi_kebersihan_gigimulut=?,"+
                "kebiasaan_susu_botol=?,mengemil_manis=?,menyikat_gigi_sebelum_tidur=?,mengemut_makanan=?,lidah_kotor=?,celah_bibir=?,hasil_skrining=?,keterangan=?,nip=?",16,new String[]{
                TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),
                PernahPemeriksaanGigi.getSelectedItem().toString(),SudahAdaGigiTumbuh.getSelectedItem().toString(),JumlahGigiSudahTumbuh.getSelectedItem().toString(), 
                KebersihanGigiMulut.getSelectedItem().toString(),KebiasaanSusuBotol.getSelectedItem().toString(),MengemilManis.getSelectedItem().toString(), 
                MenyikatGigi.getSelectedItem().toString(),MengemutMakanan.getSelectedItem().toString(),LidahAnak.getSelectedItem().toString(),CelahBibir.getSelectedItem().toString(), 
                HasilSkrining.getText(),Keterangan.getText(),KdPetugas.getText(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
            })==true){
               tbObat.setValueAt(TNoRw.getText(),tbObat.getSelectedRow(),0);
               tbObat.setValueAt(TNoRM.getText(),tbObat.getSelectedRow(),1);
               tbObat.setValueAt(TPasien.getText(),tbObat.getSelectedRow(),2);
               tbObat.setValueAt(TglLahir.getText(),tbObat.getSelectedRow(),3);
               tbObat.setValueAt(Jk.getText(),tbObat.getSelectedRow(),4);
               tbObat.setValueAt(KdPetugas.getText(),tbObat.getSelectedRow(),5);
               tbObat.setValueAt(NmPetugas.getText(),tbObat.getSelectedRow(),6);
               tbObat.setValueAt(Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),tbObat.getSelectedRow(),7);
               tbObat.setValueAt(PernahPemeriksaanGigi.getSelectedItem().toString(),tbObat.getSelectedRow(),8);
               tbObat.setValueAt(SudahAdaGigiTumbuh.getSelectedItem().toString(),tbObat.getSelectedRow(),9);
               tbObat.setValueAt(JumlahGigiSudahTumbuh.getSelectedItem().toString(),tbObat.getSelectedRow(),10);
               tbObat.setValueAt(KebersihanGigiMulut.getSelectedItem().toString(),tbObat.getSelectedRow(),11);
               tbObat.setValueAt(KebiasaanSusuBotol.getSelectedItem().toString(),tbObat.getSelectedRow(),12);
               tbObat.setValueAt(MengemilManis.getSelectedItem().toString(),tbObat.getSelectedRow(),13);
               tbObat.setValueAt(MenyikatGigi.getSelectedItem().toString(),tbObat.getSelectedRow(),14);
               tbObat.setValueAt(MengemutMakanan.getSelectedItem().toString(),tbObat.getSelectedRow(),15);
               tbObat.setValueAt(LidahAnak.getSelectedItem().toString(),tbObat.getSelectedRow(),16);
               tbObat.setValueAt(CelahBibir.getSelectedItem().toString(),tbObat.getSelectedRow(),17);
               tbObat.setValueAt(HasilSkrining.getText(),tbObat.getSelectedRow(),18);
               tbObat.setValueAt(Keterangan.getText(),tbObat.getSelectedRow(),19);
               emptTeks();
        }
    }

    private void hapus() {
        if(Sequel.queryu2tf("delete from skrining_kesehatan_gigi_mulut_balita where no_rawat=?",1,new String[]{
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
        if(Sequel.menyimpantf("skrining_kesehatan_gigi_mulut_balita","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","Data",15,new String[]{
            TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),
            PernahPemeriksaanGigi.getSelectedItem().toString(),SudahAdaGigiTumbuh.getSelectedItem().toString(),JumlahGigiSudahTumbuh.getSelectedItem().toString(), 
            KebersihanGigiMulut.getSelectedItem().toString(),KebiasaanSusuBotol.getSelectedItem().toString(),MengemilManis.getSelectedItem().toString(), 
            MenyikatGigi.getSelectedItem().toString(),MengemutMakanan.getSelectedItem().toString(),LidahAnak.getSelectedItem().toString(),CelahBibir.getSelectedItem().toString(), 
            HasilSkrining.getText(),Keterangan.getText(),KdPetugas.getText()
        })==true){
            tabMode.addRow(new Object[]{
                TNoRw.getText(),TNoRM.getText(),TPasien.getText(),TglLahir.getText(),Jk.getText(),KdPetugas.getText(),NmPetugas.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),
                PernahPemeriksaanGigi.getSelectedItem().toString(),SudahAdaGigiTumbuh.getSelectedItem().toString(),JumlahGigiSudahTumbuh.getSelectedItem().toString(), 
                KebersihanGigiMulut.getSelectedItem().toString(),KebiasaanSusuBotol.getSelectedItem().toString(),MengemilManis.getSelectedItem().toString(), 
                MenyikatGigi.getSelectedItem().toString(),MengemutMakanan.getSelectedItem().toString(),LidahAnak.getSelectedItem().toString(),CelahBibir.getSelectedItem().toString(), 
                HasilSkrining.getText(),Keterangan.getText()
            });
            LCount.setText(""+tabMode.getRowCount());
            emptTeks();
        }
    }
    
    private void hasilInterpretasi(){
        if(KebiasaanSusuBotol.getSelectedItem().toString().equals("Tidak")&&MengemilManis.getSelectedItem().toString().equals("Tidak")&&MenyikatGigi.getSelectedItem().toString().equals("Ya")&&MengemutMakanan.getSelectedItem().toString().equals("Tidak")&&LidahAnak.getSelectedItem().toString().equals("Tidak")){
            HasilSkrining.setText("Tidak Perlu Dirujuk ke Poli Gigi");
        }else{
            HasilSkrining.setText("Perlu Dirujuk ke Poli Gigi");
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

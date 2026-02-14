package viabarcode;
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
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.concurrent.RejectedExecutionException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import kepegawaian.DlgCariPetugas;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.SwingUtilities;

public class LabKeslingCariVerifikasiPengujianSampel extends javax.swing.JDialog {
    private final DefaultTableModel tabModeVerifikasi,tabModeDetailVerifikasi,tabModeRekapVerifikasi;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;
    private File file;
    private FileWriter fileWriter;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private volatile boolean ceksukses = false;

    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public LabKeslingCariVerifikasiPengujianSampel(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        tabModeVerifikasi=new DefaultTableModel(null,new Object[]{
                "Tgl.Verifikasi","No.Verifikasi","NIP PJ","Nama PJ Verifikasi","No.Permintaan","No.Pelanggan","Nama Pelanggan","Kode Sampel","Nama Sampel","Status Validasi","Catatan","Mulai Pengujian","Selesai Pengujian"
            }){
                @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbVerifikasi.setModel(tabModeVerifikasi);

        tbVerifikasi.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbVerifikasi.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 13; i++) {
            TableColumn column = tbVerifikasi.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(118);
            }else if(i==1){
                column.setPreferredWidth(120);
            }else if(i==2){
                column.setPreferredWidth(90);
            }else if(i==3){
                column.setPreferredWidth(150);
            }else if(i==4){
                column.setPreferredWidth(120);
            }else if(i==5){
                column.setPreferredWidth(90);
            }else if(i==6){
                column.setPreferredWidth(150);
            }else if(i==7){
                column.setPreferredWidth(70);
            }else if(i==8){
                column.setPreferredWidth(130);
            }else if(i==9){
                column.setPreferredWidth(88);
            }else if(i==10){
                column.setPreferredWidth(200);
            }else if(i==11){
                column.setPreferredWidth(118);
            }else if(i==12){
                column.setPreferredWidth(118);
            }
        }
        tbVerifikasi.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeDetailVerifikasi=new DefaultTableModel(null,new Object[]{
                "Kode","Nama Parameter","Satuan","Hasil Pemeriksaan","Keterangan","Nilai Normal","Metode Pengujian","Kategori","No.Penugasan","NIP Analis","Nama Analis","NIP PJ","Nama PJ Pengujian",
                "Jasa Sarana","Paket BHP","Jasa PJ Lab","Jasa PJ Pengujian","Jasa Verifikator","Jasa Petugas","KSO","Jasa Menejemen","Total"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbDetailVerifikasi.setModel(tabModeDetailVerifikasi);
        tbDetailVerifikasi.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 22; i++) {
            TableColumn column = tbDetailVerifikasi.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(55);
            }else if(i==1){
                column.setPreferredWidth(160);
            }else if(i==2){
                column.setPreferredWidth(60);
            }else if(i==3){
                column.setPreferredWidth(97);
            }else if(i==4){
                column.setPreferredWidth(140);
            }else if(i==5){
                column.setPreferredWidth(70);
            }else if(i==6){
                column.setPreferredWidth(130);
            }else if(i==7){
                column.setPreferredWidth(80);
            }else if(i==8){
                column.setPreferredWidth(120);
            }else if(i==9){
                column.setPreferredWidth(90);
            }else if(i==10){
                column.setPreferredWidth(150);
            }else if(i==11){
                column.setPreferredWidth(90);
            }else if(i==12){
                column.setPreferredWidth(150);
            }else {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }
        }
        tbDetailVerifikasi.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeRekapVerifikasi=new DefaultTableModel(null,new Object[]{
                "Tgl.Verifikasi","No.Verifikasi","NIP PJ","Nama PJ Verifikasi","No.Permintaan","No.Pelanggan","Nama Pelanggan","Kode Sampel","Nama Sampel","Status Validasi","Catatan","Mulai Pengujian",
                "Selesai Pengujian","Kode","Nama Parameter","Satuan","Hasil Pemeriksaan","Keterangan","Nilai Normal","Metode Pengujian","Kategori","No.Penugasan","NIP Analis","Nama Analis","NIP PJ","Nama PJ Pengujian"
            }){
                @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbRekapVerifikasi.setModel(tabModeRekapVerifikasi);

        tbRekapVerifikasi.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbRekapVerifikasi.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 26; i++) {
            TableColumn column = tbRekapVerifikasi.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(118);
            }else if(i==1){
                column.setPreferredWidth(120);
            }else if(i==2){
                column.setPreferredWidth(90);
            }else if(i==3){
                column.setPreferredWidth(150);
            }else if(i==4){
                column.setPreferredWidth(120);
            }else if(i==5){
                column.setPreferredWidth(90);
            }else if(i==6){
                column.setPreferredWidth(150);
            }else if(i==7){
                column.setPreferredWidth(70);
            }else if(i==8){
                column.setPreferredWidth(130);
            }else if(i==9){
                column.setPreferredWidth(88);
            }else if(i==10){
                column.setPreferredWidth(200);
            }else if(i==11){
                column.setPreferredWidth(118);
            }else if(i==12){
                column.setPreferredWidth(118);
            }else if(i==13){
                column.setPreferredWidth(55);
            }else if(i==14){
                column.setPreferredWidth(160);
            }else if(i==15){
                column.setPreferredWidth(60);
            }else if(i==16){
                column.setPreferredWidth(97);
            }else if(i==17){
                column.setPreferredWidth(140);
            }else if(i==18){
                column.setPreferredWidth(70);
            }else if(i==19){
                column.setPreferredWidth(130);
            }else if(i==20){
                column.setPreferredWidth(80);
            }else if(i==21){
                column.setPreferredWidth(120);
            }else if(i==22){
                column.setPreferredWidth(90);
            }else if(i==23){
                column.setPreferredWidth(150);
            }else if(i==24){
                column.setPreferredWidth(90);
            }else if(i==25){
                column.setPreferredWidth(150);
            }
        }
        tbRekapVerifikasi.setDefaultRenderer(Object.class, new WarnaTable());
        
        NoPermintaan.setDocument(new batasInput((byte)20).getKata(NoPermintaan));
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));          
        
        ChkAccor.setSelected(false);
        PanelAccor.setPreferredSize(new Dimension(15,HEIGHT));
        scrollPaneDetail.setVisible(false); 
        
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

        jPopupMenu1 = new javax.swing.JPopupMenu();
        ppSuratVerifikasi = new javax.swing.JMenuItem();
        ppHapusVerifikasiPengujian = new javax.swing.JMenuItem();
        ppValidasiPengujian = new javax.swing.JMenuItem();
        KodeSampel = new widget.TextBox();
        KodePelanggan = new widget.TextBox();
        KodePetugas = new widget.TextBox();
        LoadHTML = new widget.editorpane();
        internalFrame1 = new widget.InternalFrame();
        jPanel1 = new javax.swing.JPanel();
        panelisi1 = new widget.panelisi();
        label10 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        label9 = new widget.Label();
        LTotal = new widget.Label();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();
        panelisi4 = new widget.panelisi();
        label11 = new widget.Label();
        Tanggal1 = new widget.Tanggal();
        label12 = new widget.Label();
        Tanggal2 = new widget.Tanggal();
        label14 = new widget.Label();
        Status = new widget.ComboBox();
        label15 = new widget.Label();
        NoPermintaan = new widget.TextBox();
        panelisi3 = new widget.panelisi();
        label13 = new widget.Label();
        NamaPetugas = new widget.TextBox();
        btnPetugas = new widget.Button();
        label17 = new widget.Label();
        NamaPelanggan = new widget.TextBox();
        btnPelanggan = new widget.Button();
        label7 = new widget.Label();
        NamaSampel = new widget.TextBox();
        btnSampel = new widget.Button();
        TabData = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        scrollPane1 = new widget.ScrollPane();
        tbVerifikasi = new widget.Table();
        PanelAccor = new widget.PanelBiasa();
        ChkAccor = new widget.CekBox();
        scrollPaneDetail = new widget.ScrollPane();
        tbDetailVerifikasi = new widget.Table();
        scrollPane2 = new widget.ScrollPane();
        tbRekapVerifikasi = new widget.Table();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        ppSuratVerifikasi.setBackground(new java.awt.Color(255, 255, 254));
        ppSuratVerifikasi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppSuratVerifikasi.setForeground(new java.awt.Color(50, 50, 50));
        ppSuratVerifikasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppSuratVerifikasi.setText("Cetak Hasil Uji Sementara");
        ppSuratVerifikasi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppSuratVerifikasi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppSuratVerifikasi.setName("ppSuratVerifikasi"); // NOI18N
        ppSuratVerifikasi.setPreferredSize(new java.awt.Dimension(200, 25));
        ppSuratVerifikasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppSuratVerifikasiActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppSuratVerifikasi);

        ppHapusVerifikasiPengujian.setBackground(new java.awt.Color(255, 255, 254));
        ppHapusVerifikasiPengujian.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppHapusVerifikasiPengujian.setForeground(new java.awt.Color(50, 50, 50));
        ppHapusVerifikasiPengujian.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppHapusVerifikasiPengujian.setText("Hapus Verifikasi Pengujian");
        ppHapusVerifikasiPengujian.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppHapusVerifikasiPengujian.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppHapusVerifikasiPengujian.setName("ppHapusVerifikasiPengujian"); // NOI18N
        ppHapusVerifikasiPengujian.setPreferredSize(new java.awt.Dimension(200, 25));
        ppHapusVerifikasiPengujian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppHapusVerifikasiPengujianActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppHapusVerifikasiPengujian);

        ppValidasiPengujian.setBackground(new java.awt.Color(255, 255, 254));
        ppValidasiPengujian.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppValidasiPengujian.setForeground(new java.awt.Color(50, 50, 50));
        ppValidasiPengujian.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppValidasiPengujian.setText("Validasi Pengujian");
        ppValidasiPengujian.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppValidasiPengujian.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppValidasiPengujian.setName("ppValidasiPengujian"); // NOI18N
        ppValidasiPengujian.setPreferredSize(new java.awt.Dimension(200, 25));
        ppValidasiPengujian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppValidasiPengujianActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppValidasiPengujian);

        KodeSampel.setName("KodeSampel"); // NOI18N
        KodeSampel.setPreferredSize(new java.awt.Dimension(207, 23));

        KodePelanggan.setName("KodePelanggan"); // NOI18N
        KodePelanggan.setPreferredSize(new java.awt.Dimension(80, 23));

        KodePetugas.setName("KodePetugas"); // NOI18N
        KodePetugas.setPreferredSize(new java.awt.Dimension(80, 23));

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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Verifikasi Pengujian Sampel Laboratorium Kesehatan Lingkungan ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(816, 100));
        jPanel1.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 56));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label10.setText("Key Word :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi1.add(label10);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(282, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelisi1.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('5');
        BtnCari.setToolTipText("Alt+5");
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
        panelisi1.add(BtnCari);

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
        panelisi1.add(BtnAll);

        label9.setText("Record :");
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(75, 23));
        panelisi1.add(label9);

        LTotal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LTotal.setText("0");
        LTotal.setName("LTotal"); // NOI18N
        LTotal.setPreferredSize(new java.awt.Dimension(67, 23));
        panelisi1.add(LTotal);

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
        panelisi1.add(BtnPrint);

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
        panelisi1.add(BtnKeluar);

        jPanel1.add(panelisi1, java.awt.BorderLayout.PAGE_END);

        panelisi4.setName("panelisi4"); // NOI18N
        panelisi4.setPreferredSize(new java.awt.Dimension(100, 44));
        panelisi4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label11.setText("Tgl.Verifikasi :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(78, 23));
        panelisi4.add(label11);

        Tanggal1.setDisplayFormat("dd-MM-yyyy");
        Tanggal1.setName("Tanggal1"); // NOI18N
        Tanggal1.setPreferredSize(new java.awt.Dimension(90, 23));
        Tanggal1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Tanggal1KeyPressed(evt);
            }
        });
        panelisi4.add(Tanggal1);

        label12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label12.setText("s.d.");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(26, 23));
        panelisi4.add(label12);

        Tanggal2.setDisplayFormat("dd-MM-yyyy");
        Tanggal2.setName("Tanggal2"); // NOI18N
        Tanggal2.setPreferredSize(new java.awt.Dimension(90, 23));
        Tanggal2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Tanggal2KeyPressed(evt);
            }
        });
        panelisi4.add(Tanggal2);

        label14.setText("Status :");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(62, 23));
        panelisi4.add(label14);

        Status.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Semua", "Sudah Divalidasi", "Belum Divalidasi" }));
        Status.setName("Status"); // NOI18N
        Status.setPreferredSize(new java.awt.Dimension(135, 23));
        panelisi4.add(Status);

        label15.setText("No.Permintaan :");
        label15.setName("label15"); // NOI18N
        label15.setPreferredSize(new java.awt.Dimension(101, 23));
        panelisi4.add(label15);

        NoPermintaan.setName("NoPermintaan"); // NOI18N
        NoPermintaan.setPreferredSize(new java.awt.Dimension(156, 23));
        NoPermintaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoPermintaanKeyPressed(evt);
            }
        });
        panelisi4.add(NoPermintaan);

        jPanel1.add(panelisi4, java.awt.BorderLayout.CENTER);

        internalFrame1.add(jPanel1, java.awt.BorderLayout.PAGE_END);

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(100, 44));
        panelisi3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label13.setText("Verifikator :");
        label13.setName("label13"); // NOI18N
        label13.setPreferredSize(new java.awt.Dimension(66, 23));
        panelisi3.add(label13);

        NamaPetugas.setEditable(false);
        NamaPetugas.setName("NamaPetugas"); // NOI18N
        NamaPetugas.setPreferredSize(new java.awt.Dimension(160, 23));
        panelisi3.add(NamaPetugas);

        btnPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPetugas.setMnemonic('2');
        btnPetugas.setToolTipText("Alt+2");
        btnPetugas.setName("btnPetugas"); // NOI18N
        btnPetugas.setPreferredSize(new java.awt.Dimension(28, 23));
        btnPetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPetugasActionPerformed(evt);
            }
        });
        panelisi3.add(btnPetugas);

        label17.setText("Pelanggan :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label17);

        NamaPelanggan.setEditable(false);
        NamaPelanggan.setName("NamaPelanggan"); // NOI18N
        NamaPelanggan.setPreferredSize(new java.awt.Dimension(164, 23));
        panelisi3.add(NamaPelanggan);

        btnPelanggan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPelanggan.setMnemonic('4');
        btnPelanggan.setToolTipText("Alt+4");
        btnPelanggan.setName("btnPelanggan"); // NOI18N
        btnPelanggan.setPreferredSize(new java.awt.Dimension(28, 23));
        btnPelanggan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPelangganActionPerformed(evt);
            }
        });
        panelisi3.add(btnPelanggan);

        label7.setText("Sampel :");
        label7.setName("label7"); // NOI18N
        label7.setPreferredSize(new java.awt.Dimension(55, 23));
        panelisi3.add(label7);

        NamaSampel.setEditable(false);
        NamaSampel.setName("NamaSampel"); // NOI18N
        NamaSampel.setPreferredSize(new java.awt.Dimension(134, 23));
        panelisi3.add(NamaSampel);

        btnSampel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnSampel.setMnemonic('1');
        btnSampel.setToolTipText("Alt+1");
        btnSampel.setName("btnSampel"); // NOI18N
        btnSampel.setPreferredSize(new java.awt.Dimension(28, 23));
        btnSampel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSampelActionPerformed(evt);
            }
        });
        panelisi3.add(btnSampel);

        internalFrame1.add(panelisi3, java.awt.BorderLayout.PAGE_START);

        TabData.setBackground(new java.awt.Color(255, 255, 253));
        TabData.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(241, 246, 236)));
        TabData.setForeground(new java.awt.Color(50, 50, 50));
        TabData.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabData.setName("TabData"); // NOI18N
        TabData.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabDataMouseClicked(evt);
            }
        });

        jPanel2.setName("jPanel2"); // NOI18N
        jPanel2.setOpaque(false);
        jPanel2.setPreferredSize(new java.awt.Dimension(816, 100));
        jPanel2.setLayout(new java.awt.BorderLayout(1, 1));

        scrollPane1.setComponentPopupMenu(jPopupMenu1);
        scrollPane1.setName("scrollPane1"); // NOI18N
        scrollPane1.setOpaque(true);

        tbVerifikasi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbVerifikasi.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbVerifikasi.setComponentPopupMenu(jPopupMenu1);
        tbVerifikasi.setName("tbVerifikasi"); // NOI18N
        tbVerifikasi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbVerifikasiMouseClicked(evt);
            }
        });
        scrollPane1.setViewportView(tbVerifikasi);

        jPanel2.add(scrollPane1, java.awt.BorderLayout.CENTER);

        PanelAccor.setBackground(new java.awt.Color(255, 255, 255));
        PanelAccor.setName("PanelAccor"); // NOI18N
        PanelAccor.setPreferredSize(new java.awt.Dimension(445, 43));
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

        scrollPaneDetail.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1), "Detail Verifikasi :", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        scrollPaneDetail.setComponentPopupMenu(jPopupMenu1);
        scrollPaneDetail.setName("scrollPaneDetail"); // NOI18N
        scrollPaneDetail.setOpaque(true);

        tbDetailVerifikasi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbDetailVerifikasi.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbDetailVerifikasi.setComponentPopupMenu(jPopupMenu1);
        tbDetailVerifikasi.setName("tbDetailVerifikasi"); // NOI18N
        scrollPaneDetail.setViewportView(tbDetailVerifikasi);

        PanelAccor.add(scrollPaneDetail, java.awt.BorderLayout.CENTER);

        jPanel2.add(PanelAccor, java.awt.BorderLayout.EAST);

        TabData.addTab("Data Verifikasi", jPanel2);

        scrollPane2.setComponentPopupMenu(jPopupMenu1);
        scrollPane2.setName("scrollPane2"); // NOI18N
        scrollPane2.setOpaque(true);

        tbRekapVerifikasi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbRekapVerifikasi.setToolTipText("Silahkan klik untuk memilih data yang mau dihapus");
        tbRekapVerifikasi.setName("tbRekapVerifikasi"); // NOI18N
        scrollPane2.setViewportView(tbRekapVerifikasi);

        TabData.addTab("Rekap Verifikasi", scrollPane2);

        internalFrame1.add(TabData, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();  
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){            
            dispose();              
        }else{Valid.pindah(evt,BtnPrint,KodePelanggan);}
}//GEN-LAST:event_BtnKeluarKeyPressed
/*
private void KdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdKeyPressed
    Valid.pindah(evt,BtnCari,Nm);
}//GEN-LAST:event_TKdKeyPressed
*/

    private void btnPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPetugasActionPerformed
        DlgCariPetugas petugas=new DlgCariPetugas(null,false);
        petugas.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(petugas.getTable().getSelectedRow()!= -1){                   
                    KodePetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                    NamaPetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                }  
                btnPetugas.requestFocus();
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
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setVisible(true);
    }//GEN-LAST:event_btnPetugasActionPerformed

    private void Tanggal1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tanggal1KeyPressed
        Valid.pindah(evt,NoPermintaan,Status);
    }//GEN-LAST:event_Tanggal1KeyPressed

    private void btnPelangganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPelangganActionPerformed
        LabKeslingPelanggan pelanggan=new LabKeslingPelanggan(null,false);
        pelanggan.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(pelanggan.getTable().getSelectedRow()!= -1){
                    KodePelanggan.setText(pelanggan.getTable().getValueAt(pelanggan.getTable().getSelectedRow(),0).toString());
                    NamaPelanggan.setText(pelanggan.getTable().getValueAt(pelanggan.getTable().getSelectedRow(),1).toString());
                }  
                btnPelanggan.requestFocus();
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
        
        pelanggan.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    pelanggan.dispose();
                }                
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        pelanggan.isCek();
        pelanggan.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        pelanggan.setLocationRelativeTo(internalFrame1);
        pelanggan.setVisible(true);
    }//GEN-LAST:event_btnPelangganActionPerformed

    private void NoPermintaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoPermintaanKeyPressed
        Valid.pindah(evt, BtnKeluar,Status);
    }//GEN-LAST:event_NoPermintaanKeyPressed

    private void Tanggal2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tanggal2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Tanggal2KeyPressed

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            TabDataMouseClicked(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            tbVerifikasi.requestFocus();
        }
    }//GEN-LAST:event_TCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        TabDataMouseClicked(null);
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
        NoPermintaan.setText("");
        KodeSampel.setText("");
        NamaSampel.setText("");
        KodePelanggan.setText("");
        NamaPelanggan.setText("");
        KodePetugas.setText("");
        NamaPetugas.setText("");
        Status.setSelectedIndex(0);
        TabDataMouseClicked(null);
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnPrint, BtnKeluar);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        if(TabData.getSelectedIndex()==0){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            if(tabModeVerifikasi.getRowCount()==0){
                JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                TCari.requestFocus();
            }else if(tabModeVerifikasi.getRowCount()!=0){
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
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tgl.Verifikasi</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Verifikasi</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>NIP PJ</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama PJ Verifikasi</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Permintaan</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Pelanggan</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Pelanggan</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kode Sampel</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Sampel</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Status Validasi</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Catatan</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Mulai Pengujian</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Selesai Pengujian</b></td>").
                                            append("</tr>");
                                for (int i = 0; i < tabModeVerifikasi.getRowCount(); i++) {
                                    htmlContent.append("<tr class='isi'>").
                                                    append("<td valign='top'>").append(tbVerifikasi.getValueAt(i,0).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbVerifikasi.getValueAt(i,1).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbVerifikasi.getValueAt(i,2).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbVerifikasi.getValueAt(i,3).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbVerifikasi.getValueAt(i,4).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbVerifikasi.getValueAt(i,5).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbVerifikasi.getValueAt(i,6).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbVerifikasi.getValueAt(i,7).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbVerifikasi.getValueAt(i,8).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbVerifikasi.getValueAt(i,9).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbVerifikasi.getValueAt(i,10).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbVerifikasi.getValueAt(i,11).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbVerifikasi.getValueAt(i,12).toString()).append("</td>").
                                                append("</tr>");
                                }
                                LoadHTML.setText(
                                    "<html>"+
                                      "<table width='100%' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
                                       htmlContent.toString()+
                                      "</table>"+
                                    "</html>"
                                );

                                f = new File("DataVerifikasiPengujianSampel.html");            
                                bw = new BufferedWriter(new FileWriter(f));            
                                bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                                            "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                                            "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                                "<tr class='isi2'>"+
                                                    "<td valign='top' align='center'>"+
                                                        "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                                        akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                                        akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                                        "<font size='2' face='Tahoma'>DATA VERIFIKASI PENGUJIAN SAMPEL<br><br></font>"+        
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
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tgl.Verifikasi</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Verifikasi</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>NIP PJ</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama PJ Verifikasi</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Permintaan</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Pelanggan</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Pelanggan</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kode Sampel</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Sampel</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Status Validasi</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Catatan</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Mulai Pengujian</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Selesai Pengujian</b></td>").
                                            append("</tr>");
                                for (int i = 0; i < tabModeVerifikasi.getRowCount(); i++) {
                                    htmlContent.append("<tr class='isi'>").
                                                    append("<td valign='top'>").append(tbVerifikasi.getValueAt(i,0).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbVerifikasi.getValueAt(i,1).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbVerifikasi.getValueAt(i,2).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbVerifikasi.getValueAt(i,3).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbVerifikasi.getValueAt(i,4).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbVerifikasi.getValueAt(i,5).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbVerifikasi.getValueAt(i,6).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbVerifikasi.getValueAt(i,7).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbVerifikasi.getValueAt(i,8).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbVerifikasi.getValueAt(i,9).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbVerifikasi.getValueAt(i,10).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbVerifikasi.getValueAt(i,11).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbVerifikasi.getValueAt(i,12).toString()).append("</td>").
                                                append("</tr>");
                                }
                                LoadHTML.setText(
                                    "<html>"+
                                      "<table width='100%' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
                                       htmlContent.toString()+
                                      "</table>"+
                                    "</html>"
                                );

                                f = new File("DataVerifikasiPengujianSampel.wps");            
                                bw = new BufferedWriter(new FileWriter(f));            
                                bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                                            "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                                            "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                                "<tr class='isi2'>"+
                                                    "<td valign='top' align='center'>"+
                                                        "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                                        akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                                        akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                                        "<font size='2' face='Tahoma'>DATA VERIFIKASI PENGUJIAN SAMPEL<br><br></font>"+        
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
                                    "\"Tgl.Verifikasi\";\"No.Verifikasi\";\"NIP PJ\";\"Nama PJ Verifikasi\";\"No.Permintaan\";\"No.Pelanggan\";\"Nama Pelanggan\";\"Kode Sampel\";\"Nama Sampel\";\"Status Validasi\";\"Catatan\";\"Mulai Pengujian\";\"Selesai Pengujian\"\n"
                                ); 
                                for (int i = 0; i < tabModeVerifikasi.getRowCount(); i++) {
                                    htmlContent.append("\"").append(tbVerifikasi.getValueAt(i,0).toString()).append("\";\"").append(tbVerifikasi.getValueAt(i,1).toString()).append("\";\"").append(tbVerifikasi.getValueAt(i,2).toString()).append("\";\"").append(tbVerifikasi.getValueAt(i,3).toString()).append("\";\"").append(tbVerifikasi.getValueAt(i,4).toString()).append("\";\"").append(tbVerifikasi.getValueAt(i,5).toString()).append("\";\"").append(tbVerifikasi.getValueAt(i,6).toString()).append("\";\"").append(tbVerifikasi.getValueAt(i,7).toString()).append("\";\"").append(tbVerifikasi.getValueAt(i,8).toString()).append("\";\"").append(tbVerifikasi.getValueAt(i,9).toString()).append("\";\"").append(tbVerifikasi.getValueAt(i,10).toString()).append("\";\"").append(tbVerifikasi.getValueAt(i,11).toString()).append("\";\"").append(tbVerifikasi.getValueAt(i,12).toString()).append("\"\n");
                                }
                                f = new File("DataVerifikasiPengujianSampel.csv");            
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
        }else if(TabData.getSelectedIndex()==1){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            if(tabModeRekapVerifikasi.getRowCount()==0){
                JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                TCari.requestFocus();
            }else if(tabModeRekapVerifikasi.getRowCount()!=0){
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
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tgl.Verifikasi</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Verifikasi</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>NIP PJ</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama PJ Verifikasi</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Permintaan</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Pelanggan</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Pelanggan</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kode Sampel</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Sampel</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Status Validasi</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Catatan</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Mulai Pengujian</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Selesai Pengujian</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kode</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Parameter</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Satuan</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Hasil Pemeriksaan</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nilai Normal</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Metode Pengujian</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kategori</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Penugasan</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>NIP Analis</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Analis</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>NIP PJ Pengujian</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama PJ Pengujian</b></td>").
                                            append("</tr>");
                                for (int i = 0; i < tabModeRekapVerifikasi.getRowCount(); i++) {
                                    htmlContent.append("<tr class='isi'>").
                                                    append("<td valign='top'>").append(tbRekapVerifikasi.getValueAt(i,0).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapVerifikasi.getValueAt(i,1).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapVerifikasi.getValueAt(i,2).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapVerifikasi.getValueAt(i,3).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapVerifikasi.getValueAt(i,4).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapVerifikasi.getValueAt(i,5).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapVerifikasi.getValueAt(i,6).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapVerifikasi.getValueAt(i,7).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapVerifikasi.getValueAt(i,8).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapVerifikasi.getValueAt(i,9).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapVerifikasi.getValueAt(i,10).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapVerifikasi.getValueAt(i,11).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapVerifikasi.getValueAt(i,12).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapVerifikasi.getValueAt(i,13).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapVerifikasi.getValueAt(i,14).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapVerifikasi.getValueAt(i,15).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapVerifikasi.getValueAt(i,16).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapVerifikasi.getValueAt(i,17).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapVerifikasi.getValueAt(i,18).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapVerifikasi.getValueAt(i,19).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapVerifikasi.getValueAt(i,20).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapVerifikasi.getValueAt(i,21).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapVerifikasi.getValueAt(i,22).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapVerifikasi.getValueAt(i,23).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapVerifikasi.getValueAt(i,24).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapVerifikasi.getValueAt(i,25).toString()).append("</td>").
                                                append("</tr>");
                                }
                                LoadHTML.setText(
                                    "<html>"+
                                      "<table width='1900px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
                                       htmlContent.toString()+
                                      "</table>"+
                                    "</html>"
                                );

                                f = new File("DataRekapVerifikasiPengujianSampel.html");            
                                bw = new BufferedWriter(new FileWriter(f));            
                                bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                                            "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                                            "<table width='1900px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                                "<tr class='isi2'>"+
                                                    "<td valign='top' align='center'>"+
                                                        "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                                        akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                                        akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                                        "<font size='2' face='Tahoma'>DATA REKAP VERIFIKASI PENGUJIAN SAMPEL<br><br></font>"+        
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
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tgl.Verifikasi</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Verifikasi</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>NIP PJ</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama PJ Verifikasi</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Permintaan</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Pelanggan</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Pelanggan</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kode Sampel</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Sampel</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Status Validasi</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Catatan</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Mulai Pengujian</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Selesai Pengujian</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kode</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Parameter</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Satuan</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Hasil Pemeriksaan</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nilai Normal</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Metode Pengujian</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kategori</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Penugasan</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>NIP Analis</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Analis</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>NIP PJ Pengujian</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama PJ Pengujian</b></td>").
                                            append("</tr>");
                                for (int i = 0; i < tabModeRekapVerifikasi.getRowCount(); i++) {
                                    htmlContent.append("<tr class='isi'>").
                                                    append("<td valign='top'>").append(tbRekapVerifikasi.getValueAt(i,0).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapVerifikasi.getValueAt(i,1).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapVerifikasi.getValueAt(i,2).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapVerifikasi.getValueAt(i,3).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapVerifikasi.getValueAt(i,4).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapVerifikasi.getValueAt(i,5).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapVerifikasi.getValueAt(i,6).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapVerifikasi.getValueAt(i,7).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapVerifikasi.getValueAt(i,8).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapVerifikasi.getValueAt(i,9).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapVerifikasi.getValueAt(i,10).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapVerifikasi.getValueAt(i,11).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapVerifikasi.getValueAt(i,12).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapVerifikasi.getValueAt(i,13).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapVerifikasi.getValueAt(i,14).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapVerifikasi.getValueAt(i,15).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapVerifikasi.getValueAt(i,16).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapVerifikasi.getValueAt(i,17).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapVerifikasi.getValueAt(i,18).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapVerifikasi.getValueAt(i,19).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapVerifikasi.getValueAt(i,20).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapVerifikasi.getValueAt(i,21).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapVerifikasi.getValueAt(i,22).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapVerifikasi.getValueAt(i,23).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapVerifikasi.getValueAt(i,24).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapVerifikasi.getValueAt(i,25).toString()).append("</td>").
                                                append("</tr>");
                                }
                                LoadHTML.setText(
                                    "<html>"+
                                      "<table width='1900px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
                                       htmlContent.toString()+
                                      "</table>"+
                                    "</html>"
                                );

                                f = new File("DataRekapVerifikasiPengujianSampel.wps");            
                                bw = new BufferedWriter(new FileWriter(f));            
                                bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                                            "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                                            "<table width='1900px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                                "<tr class='isi2'>"+
                                                    "<td valign='top' align='center'>"+
                                                        "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                                        akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                                        akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                                        "<font size='2' face='Tahoma'>DATA REKAP VERIFIKASI PENGUJIAN SAMPEL<br><br></font>"+        
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
                                    "\"Tgl.Verifikasi\";\"No.Verifikasi\";\"NIP PJ\";\"Nama PJ Verifikasi\";\"No.Permintaan\";\"No.Pelanggan\";\"Nama Pelanggan\";\"Kode Sampel\";\"Nama Sampel\";\"Status Validasi\";\"Catatan\";\"Mulai Pengujian\";\"Selesai Pengujian\";\"Kode\";\"Nama Parameter\";\"Satuan\";\"Hasil Pemeriksaan\";\"Keterangan\";\"Nilai Normal\";\"Metode Pengujian\";\"Kategori\";\"No.Penugasan\";\"NIP Analis\";\"Nama Analis\";\"NIP PJ Pengujian\";\"Nama PJ Pengujian\"\n"
                                ); 
                                for (int i = 0; i < tabModeRekapVerifikasi.getRowCount(); i++) {
                                    htmlContent.append("\"").append(tbRekapVerifikasi.getValueAt(i,0).toString()).append("\";\"").append(tbRekapVerifikasi.getValueAt(i,1).toString()).append("\";\"").append(tbRekapVerifikasi.getValueAt(i,2).toString()).append("\";\"").append(tbRekapVerifikasi.getValueAt(i,3).toString()).append("\";\"").append(tbRekapVerifikasi.getValueAt(i,4).toString()).append("\";\"").append(tbRekapVerifikasi.getValueAt(i,5).toString()).append("\";\"").append(tbRekapVerifikasi.getValueAt(i,6).toString()).append("\";\"").append(tbRekapVerifikasi.getValueAt(i,7).toString()).append("\";\"").append(tbRekapVerifikasi.getValueAt(i,8).toString()).append("\";\"").append(tbRekapVerifikasi.getValueAt(i,9).toString()).append("\";\"").append(tbRekapVerifikasi.getValueAt(i,10).toString()).append("\";\"").append(tbRekapVerifikasi.getValueAt(i,11).toString()).append("\";\"").append(tbRekapVerifikasi.getValueAt(i,12).toString()).append("\";\"").append(tbRekapVerifikasi.getValueAt(i,13).toString()).append("\";\"").append(tbRekapVerifikasi.getValueAt(i,14).toString()).append("\";\"").append(tbRekapVerifikasi.getValueAt(i,15).toString()).append("\";\"").append(tbRekapVerifikasi.getValueAt(i,16).toString()).append("\";\"").append(tbRekapVerifikasi.getValueAt(i,17).toString()).append("\";\"").append(tbRekapVerifikasi.getValueAt(i,18).toString()).append("\";\"").append(tbRekapVerifikasi.getValueAt(i,19).toString()).append("\";\"").append(tbRekapVerifikasi.getValueAt(i,20).toString()).append("\";\"").append(tbRekapVerifikasi.getValueAt(i,21).toString()).append("\";\"").append(tbRekapVerifikasi.getValueAt(i,22).toString()).append("\";\"").append(tbRekapVerifikasi.getValueAt(i,23).toString()).append("\";\"").append(tbRekapVerifikasi.getValueAt(i,24).toString()).append("\";\"").append(tbRekapVerifikasi.getValueAt(i,25).toString()).append("\"\n");
                                }
                                f = new File("DataRekapVerifikasiPengujianSampel.csv");            
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
        }
    }//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt,BtnAll,BtnAll);
        }
    }//GEN-LAST:event_BtnPrintKeyPressed

    private void btnSampelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSampelActionPerformed
        LabKeslingCariMasterSampelBakuMutu sampel=new LabKeslingCariMasterSampelBakuMutu(null,false);
        sampel.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(sampel.getTable().getSelectedRow()!= -1){
                    KodeSampel.setText(sampel.getTable().getValueAt(sampel.getTable().getSelectedRow(),0).toString());
                    NamaSampel.setText(sampel.getTable().getValueAt(sampel.getTable().getSelectedRow(),1).toString());
                }  
                btnSampel.requestFocus();
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
        
        sampel.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    sampel.dispose();
                }                
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        sampel.isCek();
        sampel.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        sampel.setLocationRelativeTo(internalFrame1);
        sampel.setVisible(true);
    }//GEN-LAST:event_btnSampelActionPerformed

    private void ppValidasiPengujianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppValidasiPengujianActionPerformed
        if(tbVerifikasi.getSelectedRow()!= -1){
            if(ChkAccor.isSelected()==false){
                JOptionPane.showMessageDialog(null,"Silahkan tampilkan data detail verifikasi terlebih dahulu...!!!");
            }else{
                if(tbVerifikasi.getValueAt(tbVerifikasi.getSelectedRow(),9).toString().equals("Belum Divalidasi")){
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));  
                    try {
                        file=new File("./cache/validasipengujiansampellabkesling.iyem");
                        file.createNewFile();
                        fileWriter = new FileWriter(file);
                        StringBuilder iyembuilder = new StringBuilder();

                        for(i=0;i<tbDetailVerifikasi.getRowCount();i++){
                            iyembuilder.append("{\"Kode\":\"").append(tbDetailVerifikasi.getValueAt(i,0).toString()).append("\",\"NamaParameter\":\"").append(tbDetailVerifikasi.getValueAt(i,1).toString()).append("\",\"Satuan\":\"").append(tbDetailVerifikasi.getValueAt(i,2).toString()).append("\",\"HasilPemeriksaan\":\"").append(tbDetailVerifikasi.getValueAt(i,3).toString()).append("\",\"Keterangan\":\"").append(tbDetailVerifikasi.getValueAt(i,4).toString()).append("\",\"NilaiNormal\":\"").append(tbDetailVerifikasi.getValueAt(i,5).toString()).append("\",\"MetodePengujian\":\"").append(tbDetailVerifikasi.getValueAt(i,6).toString()).append("\",\"Kategori\":\"").append(tbDetailVerifikasi.getValueAt(i,7).toString()).append("\",\"NoPenugasan\":\"").append(tbDetailVerifikasi.getValueAt(i,8).toString()).append("\",\"NIPAnalis\":\"").append(tbDetailVerifikasi.getValueAt(i,9).toString()).append("\",\"NamaAnalis\":\"").append(tbDetailVerifikasi.getValueAt(i,10).toString()).append("\",\"NIPPJPengujian\":\"").append(tbDetailVerifikasi.getValueAt(i,11).toString()).append("\",\"NamaPJPengujian\":\"").append(tbDetailVerifikasi.getValueAt(i,12).toString()).append("\",\"JasaSarana\":\"").append(tbDetailVerifikasi.getValueAt(i,13).toString()).append("\",\"PaketBHP\":\"").append(tbDetailVerifikasi.getValueAt(i,14).toString()).append("\",\"JasaPJLab\":\"").append(tbDetailVerifikasi.getValueAt(i,15).toString()).append("\",\"JasaPJPengujian\":\"").append(tbDetailVerifikasi.getValueAt(i,16).toString()).append("\",\"JasaVerifikator\":\"").append(tbDetailVerifikasi.getValueAt(i,17).toString()).append("\",\"JasaPetugas\":\"").append(tbDetailVerifikasi.getValueAt(i,18).toString()).append("\",\"KSO\":\"").append(tbDetailVerifikasi.getValueAt(i,19).toString()).append("\",\"JasaMenejemen\":\"").append(tbDetailVerifikasi.getValueAt(i,20).toString()).append("\",\"Total\":\"").append(tbDetailVerifikasi.getValueAt(i,21).toString()).append("\"},");
                        }

                        if (iyembuilder.length() > 0) {
                            iyembuilder.setLength(iyembuilder.length() - 1);
                            fileWriter.write("{\"validasipengujiansampellabkesling\":["+iyembuilder+"]}");
                            fileWriter.flush();
                        }

                        fileWriter.close();
                        iyembuilder=null;
                    } catch (Exception e) {
                        System.out.println("Notifikasi : "+e);
                    }
                    LabKeslingValidasiPengujianSampel form=new LabKeslingValidasiPengujianSampel(null,false);
                    form.addWindowListener(new WindowListener() {
                        @Override
                        public void windowOpened(WindowEvent e) {}
                        @Override
                        public void windowClosing(WindowEvent e) {}
                        @Override
                        public void windowClosed(WindowEvent e) {
                            if(form.berhasil==true){
                                tbVerifikasi.setValueAt("Sudah Divalidasi",tbVerifikasi.getSelectedRow(),9);
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
                    form.isCek();
                    form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                    form.setLocationRelativeTo(this);
                    form.setData(tbVerifikasi.getValueAt(tbVerifikasi.getSelectedRow(),4).toString(),tbVerifikasi.getValueAt(tbVerifikasi.getSelectedRow(),5).toString(),tbVerifikasi.getValueAt(tbVerifikasi.getSelectedRow(),6).toString(),tbVerifikasi.getValueAt(tbVerifikasi.getSelectedRow(),7).toString(),tbVerifikasi.getValueAt(tbVerifikasi.getSelectedRow(),8).toString(),tbVerifikasi.getValueAt(tbVerifikasi.getSelectedRow(),2).toString(),tbVerifikasi.getValueAt(tbVerifikasi.getSelectedRow(),3).toString(),Valid.SetTgl5(tbVerifikasi.getValueAt(tbVerifikasi.getSelectedRow(),11).toString())+" s.d."+Valid.SetTgl5(tbVerifikasi.getValueAt(tbVerifikasi.getSelectedRow(),12).toString()),tbVerifikasi.getValueAt(tbVerifikasi.getSelectedRow(),1).toString());
                    form.setVisible(true);
                    this.setCursor(Cursor.getDefaultCursor());
                }else{
                    JOptionPane.showMessageDialog(null,"Sudah divalidasi...!");
                }
            }
        }else{
            JOptionPane.showMessageDialog(null,"Silahkan pilih data verifikasi...!!!");
        }
    }//GEN-LAST:event_ppValidasiPengujianActionPerformed

    private void TabDataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabDataMouseClicked
        if(TabData.getSelectedIndex()==0){
            runBackground(() ->tampil());
        }else if(TabData.getSelectedIndex()==1){
            runBackground(() ->tampil2());
        }
    }//GEN-LAST:event_TabDataMouseClicked

    private void ChkAccorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkAccorActionPerformed
        if(tbVerifikasi.getSelectedRow()!= -1){
            if(ChkAccor.isSelected()==true){
                ChkAccor.setVisible(false);
                PanelAccor.setPreferredSize(new Dimension(670,HEIGHT));
                scrollPaneDetail.setVisible(true);  
                ChkAccor.setVisible(true);
                Valid.tabelKosong(tabModeDetailVerifikasi);
                try {
                    ps=koneksi.prepareStatement(
                        "select labkesling_detail_verifikasi_pengujian_sampel.kode_parameter,labkesling_parameter_pengujian.nama_parameter,labkesling_parameter_pengujian.satuan,labkesling_detail_verifikasi_pengujian_sampel.hasil_pengujian,"+
                        "labkesling_detail_verifikasi_pengujian_sampel.keterangan,labkesling_detail_verifikasi_pengujian_sampel.nilai_normal,labkesling_parameter_pengujian.metode_pengujian,labkesling_parameter_pengujian.kategori,"+
                        "labkesling_detail_verifikasi_pengujian_sampel.no_penugasan,labkesling_penugasan_pengujian_sampel.nip_pelaksana,analis.nama as analis,labkesling_penugasan_pengujian_sampel.nip_pj,pjpengujian.nama as pjpengujian,"+
                        "labkesling_parameter_pengujian.jasa_sarana,labkesling_parameter_pengujian.paket_bhp,labkesling_parameter_pengujian.jasa_pj_lab,labkesling_parameter_pengujian.jasa_pj_pengujian,labkesling_parameter_pengujian.jasa_verifikator,"+
                        "labkesling_parameter_pengujian.jasa_petugas,labkesling_parameter_pengujian.kso,labkesling_parameter_pengujian.jasa_menejemen,labkesling_parameter_pengujian.total "+
                        "from labkesling_detail_verifikasi_pengujian_sampel inner join labkesling_parameter_pengujian on labkesling_detail_verifikasi_pengujian_sampel.kode_parameter=labkesling_parameter_pengujian.kode_parameter "+
                        "inner join labkesling_penugasan_pengujian_sampel on labkesling_detail_verifikasi_pengujian_sampel.no_penugasan=labkesling_penugasan_pengujian_sampel.no_penugasan "+
                        "inner join petugas as analis on labkesling_penugasan_pengujian_sampel.nip_pelaksana=analis.nip "+
                        "inner join petugas as pjpengujian on labkesling_penugasan_pengujian_sampel.nip_pj=pjpengujian.nip "+
                        "where labkesling_detail_verifikasi_pengujian_sampel.no_verifikasi=? order by labkesling_detail_verifikasi_pengujian_sampel.kode_parameter"
                    );
                    try {
                        ps.setString(1,tbVerifikasi.getValueAt(tbVerifikasi.getSelectedRow(),1).toString());
                        rs=ps.executeQuery();
                        while(rs.next()){
                            tabModeDetailVerifikasi.addRow(new Object[]{
                                rs.getString("kode_parameter"),rs.getString("nama_parameter"),rs.getString("satuan"),rs.getString("hasil_pengujian"),rs.getString("keterangan"),rs.getString("nilai_normal"),
                                rs.getString("metode_pengujian"),rs.getString("kategori"),rs.getString("no_penugasan"),rs.getString("nip_pelaksana"),rs.getString("analis"),rs.getString("nip_pj"),rs.getString("pjpengujian"),
                                rs.getString("jasa_sarana"),rs.getString("paket_bhp"),rs.getString("jasa_pj_lab"),rs.getString("jasa_pj_pengujian"),rs.getString("jasa_verifikator"),rs.getString("jasa_petugas"),
                                rs.getString("kso"),rs.getString("jasa_menejemen"),rs.getString("total")
                            });
                        } 
                    } catch (Exception e) {
                        System.out.println(e);
                    } finally{
                        if(rs!=null){
                            rs.close();
                        }
                        if(ps!=null){
                            ps.close();
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Notifikasi : "+e);
                }
            }else if(ChkAccor.isSelected()==false){    
                ChkAccor.setVisible(false);
                PanelAccor.setPreferredSize(new Dimension(15,HEIGHT));
                scrollPaneDetail.setVisible(false);  
                ChkAccor.setVisible(true);
            }
        }else{
            ChkAccor.setSelected(false);
            JOptionPane.showMessageDialog(null,"Silahkan pilih data verifikasi...!!!");
        }
    }//GEN-LAST:event_ChkAccorActionPerformed

    private void tbVerifikasiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbVerifikasiMouseClicked
        ChkAccorActionPerformed(null);
    }//GEN-LAST:event_tbVerifikasiMouseClicked

    private void ppSuratVerifikasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppSuratVerifikasiActionPerformed
        if(tbVerifikasi.getSelectedRow()!= -1){
            if(ChkAccor.isSelected()==false){
                JOptionPane.showMessageDialog(null,"Silahkan tampilkan data detail verifikasi terlebih dahulu...!!!");
            }else{
                if(tbDetailVerifikasi.getRowCount()!=0){
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    Sequel.queryu("delete from temporary");
                    for(i=0;i<tbDetailVerifikasi.getRowCount();i++){ 
                        Sequel.menyimpan("temporary","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?",38,new String[]{
                            "0",tbDetailVerifikasi.getValueAt(i,0).toString(),tbDetailVerifikasi.getValueAt(i,1).toString(),tbDetailVerifikasi.getValueAt(i,2).toString(),tbDetailVerifikasi.getValueAt(i,6).toString(),tbDetailVerifikasi.getValueAt(i,3).toString(),"","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","",""
                        });               
                    }

                    Map<String, Object> param = new HashMap<>();
                    param.put("pjlaborat",tbVerifikasi.getValueAt(tbVerifikasi.getSelectedRow(),3).toString());
                    param.put("kodepjlaborat",tbVerifikasi.getValueAt(tbVerifikasi.getSelectedRow(),2).toString());
                    param.put("noverifikasi",tbVerifikasi.getValueAt(tbVerifikasi.getSelectedRow(),1).toString());
                    param.put("nomorpermintaan",tbVerifikasi.getValueAt(tbVerifikasi.getSelectedRow(),4).toString());
                    param.put("jenisampel",tbVerifikasi.getValueAt(tbVerifikasi.getSelectedRow(),8).toString());
                    param.put("kodesampel",tbVerifikasi.getValueAt(tbVerifikasi.getSelectedRow(),7).toString());
                    param.put("kodepelanggan",tbVerifikasi.getValueAt(tbVerifikasi.getSelectedRow(),5).toString());
                    param.put("namapelanggan",tbVerifikasi.getValueAt(tbVerifikasi.getSelectedRow(),6).toString());
                    param.put("tanggalpengujian",Valid.SetTgl5(tbVerifikasi.getValueAt(tbVerifikasi.getSelectedRow(),11).toString())+" s.d. "+Valid.SetTgl5(tbVerifikasi.getValueAt(tbVerifikasi.getSelectedRow(),12).toString()));
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());   
                    param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                    String finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",tbVerifikasi.getValueAt(tbVerifikasi.getSelectedRow(),2).toString());
                    param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbVerifikasi.getValueAt(tbVerifikasi.getSelectedRow(),3).toString()+"\nID "+(finger.equals("")?tbVerifikasi.getValueAt(tbVerifikasi.getSelectedRow(),2).toString():finger)+"\n"+Valid.SetTgl5(tbVerifikasi.getValueAt(tbVerifikasi.getSelectedRow(),0).toString())); 
                    Valid.MyReport("rptVerifikasiPengujianSampelLaboratKesling.jasper","report","::[ Laporan Hasil Uji Sementara ]::",param);      
                    this.setCursor(Cursor.getDefaultCursor());
                }else{
                    JOptionPane.showMessageDialog(null,"Silahkan tampilkan data detail verifikasi terlebih dahulu...!!!");
                }
            }
        }else{
            JOptionPane.showMessageDialog(null,"Silahkan pilih data verifikasi...!!!");
        }
    }//GEN-LAST:event_ppSuratVerifikasiActionPerformed

    private void ppHapusVerifikasiPengujianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppHapusVerifikasiPengujianActionPerformed
        if(tbVerifikasi.getSelectedRow()!= -1){
            if(ChkAccor.isSelected()==false){
                JOptionPane.showMessageDialog(null,"Silahkan tampilkan data detail verifikasi terlebih dahulu...!!!");
            }else{
                if(tbVerifikasi.getValueAt(tbVerifikasi.getSelectedRow(),9).toString().equals("Belum Divalidasi")){
                    if(Sequel.queryutf("delete from labkesling_verifikasi_pengujian_sampel where no_verifikasi='"+tbVerifikasi.getValueAt(tbVerifikasi.getSelectedRow(),1).toString()+"'")==true){
                        for(i=0;i<tbDetailVerifikasi.getRowCount();i++){
                            Sequel.queryu("update labkesling_hasil_pengujian_sampel set status='Belum Diverifikasi' where no_penugasan='"+tbDetailVerifikasi.getValueAt(i,8).toString()+"' and kode_parameter='"+tbDetailVerifikasi.getValueAt(i,0).toString()+"'");               
                        } 
                        tabModeVerifikasi.removeRow(tbVerifikasi.getSelectedRow());
                        Valid.tabelKosong(tabModeDetailVerifikasi);
                        LTotal.setText(tbVerifikasi.getRowCount()+"");
                    }
                }else{
                    JOptionPane.showMessageDialog(null,"Sudah divalidasi, tidak bisa dihapus...!");
                }
            }
        }else{
            JOptionPane.showMessageDialog(null,"Silahkan pilih data verifikasi...!!!");
        }
    }//GEN-LAST:event_ppHapusVerifikasiPengujianActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        if(koneksiDB.CARICEPAT().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        TabDataMouseClicked(null);
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        TabDataMouseClicked(null);
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        TabDataMouseClicked(null);
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
            LabKeslingCariVerifikasiPengujianSampel dialog = new LabKeslingCariVerifikasiPengujianSampel(new javax.swing.JFrame(), true);
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
    private widget.Button BtnCari;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.CekBox ChkAccor;
    private widget.TextBox KodePelanggan;
    private widget.TextBox KodePetugas;
    private widget.TextBox KodeSampel;
    private widget.Label LTotal;
    private widget.editorpane LoadHTML;
    private widget.TextBox NamaPelanggan;
    private widget.TextBox NamaPetugas;
    private widget.TextBox NamaSampel;
    private widget.TextBox NoPermintaan;
    private widget.PanelBiasa PanelAccor;
    private widget.ComboBox Status;
    private widget.TextBox TCari;
    private javax.swing.JTabbedPane TabData;
    private widget.Tanggal Tanggal1;
    private widget.Tanggal Tanggal2;
    private widget.Button btnPelanggan;
    private widget.Button btnPetugas;
    private widget.Button btnSampel;
    private widget.InternalFrame internalFrame1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.Label label10;
    private widget.Label label11;
    private widget.Label label12;
    private widget.Label label13;
    private widget.Label label14;
    private widget.Label label15;
    private widget.Label label17;
    private widget.Label label7;
    private widget.Label label9;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi3;
    private widget.panelisi panelisi4;
    private javax.swing.JMenuItem ppHapusVerifikasiPengujian;
    private javax.swing.JMenuItem ppSuratVerifikasi;
    private javax.swing.JMenuItem ppValidasiPengujian;
    private widget.ScrollPane scrollPane1;
    private widget.ScrollPane scrollPane2;
    private widget.ScrollPane scrollPaneDetail;
    private widget.Table tbDetailVerifikasi;
    private widget.Table tbRekapVerifikasi;
    private widget.Table tbVerifikasi;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabModeVerifikasi);
        try{  
            ps=koneksi.prepareStatement(
                        "select labkesling_verifikasi_pengujian_sampel.tanggal,labkesling_verifikasi_pengujian_sampel.no_verifikasi,labkesling_verifikasi_pengujian_sampel.nip_pj,petugas.nama,"+
                        "labkesling_verifikasi_pengujian_sampel.no_permintaan,labkesling_permintaan_pengujian_sampel.kode_pelanggan,labkesling_pelanggan.nama_pelanggan,labkesling_permintaan_pengujian_sampel.kode_sampel,"+
                        "labkesling_master_sampel.nama_sampel,labkesling_verifikasi_pengujian_sampel.status,labkesling_verifikasi_pengujian_sampel.catatan,labkesling_verifikasi_pengujian_sampel.mulai_pengujian,"+
                        "labkesling_verifikasi_pengujian_sampel.selesai_pengujian from labkesling_verifikasi_pengujian_sampel "+
                        "inner join petugas on petugas.nip=labkesling_verifikasi_pengujian_sampel.nip_pj "+
                        "inner join labkesling_permintaan_pengujian_sampel on labkesling_verifikasi_pengujian_sampel.no_permintaan=labkesling_permintaan_pengujian_sampel.no_permintaan "+
                        "inner join labkesling_pelanggan on labkesling_permintaan_pengujian_sampel.kode_pelanggan=labkesling_pelanggan.kode_pelanggan "+
                        "inner join labkesling_master_sampel on labkesling_permintaan_pengujian_sampel.kode_sampel=labkesling_master_sampel.kode_sampel "+
                        "where labkesling_verifikasi_pengujian_sampel.tanggal between ? and ? "+(NoPermintaan.getText().trim().equals("")?"":" and labkesling_verifikasi_pengujian_sampel.no_permintaan='"+NoPermintaan.getText()+"' ")+
                        (Status.getSelectedItem().toString().equals("Semua")?"":" and labkesling_verifikasi_pengujian_sampel.status='"+Status.getSelectedItem().toString()+"' ")+
                        (NamaPetugas.getText().trim().equals("")?"":" and labkesling_verifikasi_pengujian_sampel.nip_pj='"+KodePetugas.getText()+"' ")+
                        (NamaPelanggan.getText().trim().equals("")?"":" and labkesling_permintaan_pengujian_sampel.kode_pelanggan='"+KodePelanggan.getText()+"' ")+
                        (NamaSampel.getText().trim().equals("")?"":" and labkesling_permintaan_pengujian_sampel.kode_sampel='"+KodeSampel.getText()+"' ")+
                        (TCari.getText().trim().equals("")?"":" and (labkesling_verifikasi_pengujian_sampel.no_verifikasi like ? or labkesling_verifikasi_pengujian_sampel.catatan like ? or "+
                        "labkesling_verifikasi_pengujian_sampel.mulai_pengujian like ?) ")+
                        "order by labkesling_verifikasi_pengujian_sampel.tanggal,labkesling_verifikasi_pengujian_sampel.no_verifikasi,labkesling_verifikasi_pengujian_sampel.no_permintaan");
                
            try {
                ps.setString(1,Valid.SetTgl(Tanggal1.getSelectedItem()+"")+" 00:00:00");
                ps.setString(2,Valid.SetTgl(Tanggal2.getSelectedItem()+"")+" 23:59:59");
                if(!TCari.getText().trim().equals("")){
                    ps.setString(3,"%"+TCari.getText()+"%");
                    ps.setString(4,"%"+TCari.getText()+"%");
                    ps.setString(5,"%"+TCari.getText()+"%");
                }
                    
                rs=ps.executeQuery();
                while(rs.next()){
                    tabModeVerifikasi.addRow(new Object[]{
                        rs.getString("tanggal"),rs.getString("no_verifikasi"),rs.getString("nip_pj"),rs.getString("nama"),rs.getString("no_permintaan"),rs.getString("kode_pelanggan"),rs.getString("nama_pelanggan"),
                        rs.getString("kode_sampel"),rs.getString("nama_sampel"),rs.getString("status"),rs.getString("catatan"),rs.getString("mulai_pengujian"),rs.getString("selesai_pengujian")
                    }); 
                }        
                LTotal.setText(tabModeVerifikasi.getRowCount()+"");
            } catch (Exception e) {
                System.out.println("Note : "+e);
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
    
    private void tampil2() {
        Valid.tabelKosong(tabModeRekapVerifikasi);
        try{  
            ps=koneksi.prepareStatement(
                "select labkesling_verifikasi_pengujian_sampel.tanggal,labkesling_verifikasi_pengujian_sampel.no_verifikasi,labkesling_verifikasi_pengujian_sampel.nip_pj,petugas.nama,"+
                "labkesling_verifikasi_pengujian_sampel.no_permintaan,labkesling_permintaan_pengujian_sampel.kode_pelanggan,labkesling_pelanggan.nama_pelanggan,labkesling_permintaan_pengujian_sampel.kode_sampel,"+
                "labkesling_master_sampel.nama_sampel,labkesling_verifikasi_pengujian_sampel.status,labkesling_verifikasi_pengujian_sampel.catatan,labkesling_verifikasi_pengujian_sampel.mulai_pengujian,"+
                "labkesling_verifikasi_pengujian_sampel.selesai_pengujian,labkesling_detail_verifikasi_pengujian_sampel.kode_parameter,labkesling_parameter_pengujian.nama_parameter,"+
                "labkesling_parameter_pengujian.satuan,labkesling_detail_verifikasi_pengujian_sampel.hasil_pengujian,labkesling_detail_verifikasi_pengujian_sampel.keterangan,"+
                "labkesling_detail_verifikasi_pengujian_sampel.nilai_normal,labkesling_parameter_pengujian.metode_pengujian,labkesling_parameter_pengujian.kategori,"+
                "labkesling_detail_verifikasi_pengujian_sampel.no_penugasan,labkesling_penugasan_pengujian_sampel.nip_pelaksana,analis.nama as analis,labkesling_penugasan_pengujian_sampel.nip_pj as nippengujian,"+
                "pjpengujian.nama as pjpengujian from labkesling_verifikasi_pengujian_sampel inner join petugas on petugas.nip=labkesling_verifikasi_pengujian_sampel.nip_pj "+
                "inner join labkesling_permintaan_pengujian_sampel on labkesling_verifikasi_pengujian_sampel.no_permintaan=labkesling_permintaan_pengujian_sampel.no_permintaan "+
                "inner join labkesling_pelanggan on labkesling_permintaan_pengujian_sampel.kode_pelanggan=labkesling_pelanggan.kode_pelanggan "+
                "inner join labkesling_master_sampel on labkesling_permintaan_pengujian_sampel.kode_sampel=labkesling_master_sampel.kode_sampel "+
                "inner join labkesling_detail_verifikasi_pengujian_sampel on labkesling_verifikasi_pengujian_sampel.no_verifikasi=labkesling_detail_verifikasi_pengujian_sampel.no_verifikasi "+
                "inner join labkesling_parameter_pengujian on labkesling_detail_verifikasi_pengujian_sampel.kode_parameter=labkesling_parameter_pengujian.kode_parameter "+
                "inner join labkesling_penugasan_pengujian_sampel on labkesling_detail_verifikasi_pengujian_sampel.no_penugasan=labkesling_penugasan_pengujian_sampel.no_penugasan "+
                "inner join petugas as analis on labkesling_penugasan_pengujian_sampel.nip_pelaksana=analis.nip "+
                "inner join petugas as pjpengujian on labkesling_penugasan_pengujian_sampel.nip_pj=pjpengujian.nip "+
                "where labkesling_verifikasi_pengujian_sampel.tanggal between ? and ? "+(NoPermintaan.getText().trim().equals("")?"":" and labkesling_verifikasi_pengujian_sampel.no_permintaan='"+NoPermintaan.getText()+"' ")+
                (Status.getSelectedItem().toString().equals("Semua")?"":" and labkesling_verifikasi_pengujian_sampel.status='"+Status.getSelectedItem().toString()+"' ")+
                (NamaPetugas.getText().trim().equals("")?"":" and labkesling_verifikasi_pengujian_sampel.nip_pj='"+KodePetugas.getText()+"' ")+
                (NamaPelanggan.getText().trim().equals("")?"":" and labkesling_permintaan_pengujian_sampel.kode_pelanggan='"+KodePelanggan.getText()+"' ")+
                (NamaSampel.getText().trim().equals("")?"":" and labkesling_permintaan_pengujian_sampel.kode_sampel='"+KodeSampel.getText()+"' ")+
                (TCari.getText().trim().equals("")?"":" and (labkesling_verifikasi_pengujian_sampel.no_verifikasi like ? or labkesling_verifikasi_pengujian_sampel.catatan like ? or "+
                "labkesling_verifikasi_pengujian_sampel.mulai_pengujian like ? or labkesling_detail_verifikasi_pengujian_sampel.kode_parameter like ? or "+
                "labkesling_parameter_pengujian.nama_parameter like ? or labkesling_parameter_pengujian.metode_pengujian like ? or labkesling_parameter_pengujian.kategori like ? or "+
                "labkesling_detail_verifikasi_pengujian_sampel.no_penugasan like ? or labkesling_penugasan_pengujian_sampel.nip_pelaksana like ? or analis.nama like ?) ")+
                "order by labkesling_verifikasi_pengujian_sampel.tanggal,labkesling_verifikasi_pengujian_sampel.no_verifikasi,labkesling_verifikasi_pengujian_sampel.no_permintaan"
            );
                
            try {
                ps.setString(1,Valid.SetTgl(Tanggal1.getSelectedItem()+"")+" 00:00:00");
                ps.setString(2,Valid.SetTgl(Tanggal2.getSelectedItem()+"")+" 23:59:59");
                if(!TCari.getText().trim().equals("")){
                    ps.setString(3,"%"+TCari.getText()+"%");
                    ps.setString(4,"%"+TCari.getText()+"%");
                    ps.setString(5,"%"+TCari.getText()+"%");
                    ps.setString(6,"%"+TCari.getText()+"%");
                    ps.setString(7,"%"+TCari.getText()+"%");
                    ps.setString(8,"%"+TCari.getText()+"%");
                    ps.setString(9,"%"+TCari.getText()+"%");
                    ps.setString(10,"%"+TCari.getText()+"%");
                    ps.setString(11,"%"+TCari.getText()+"%");
                    ps.setString(12,"%"+TCari.getText()+"%");
                }
                    
                rs=ps.executeQuery();
                while(rs.next()){
                    tabModeRekapVerifikasi.addRow(new Object[]{
                        rs.getString("tanggal"),rs.getString("no_verifikasi"),rs.getString("nip_pj"),rs.getString("nama"),rs.getString("no_permintaan"),rs.getString("kode_pelanggan"),rs.getString("nama_pelanggan"),
                        rs.getString("kode_sampel"),rs.getString("nama_sampel"),rs.getString("status"),rs.getString("catatan"),rs.getString("mulai_pengujian"),rs.getString("selesai_pengujian"),rs.getString("kode_parameter"),
                        rs.getString("nama_parameter"),rs.getString("satuan"),rs.getString("hasil_pengujian"),rs.getString("keterangan"),rs.getString("nilai_normal"),rs.getString("metode_pengujian"),rs.getString("kategori"),
                        rs.getString("no_penugasan"),rs.getString("nip_pelaksana"),rs.getString("analis"),rs.getString("nippengujian"),rs.getString("pjpengujian")
                    }); 
                }        
                LTotal.setText(tabModeRekapVerifikasi.getRowCount()+"");
            } catch (Exception e) {
                System.out.println("Note : "+e);
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
    
    public void isCek(){
        TCari.requestFocus();
        BtnPrint.setEnabled(akses.getverifikasi_pengujian_sampel_lab_kesehatan_lingkungan());
        ppValidasiPengujian.setEnabled(akses.getvalidasi_pengujian_sampel_lab_kesehatan_lingkungan());
        ppSuratVerifikasi.setEnabled(akses.getverifikasi_pengujian_sampel_lab_kesehatan_lingkungan());
        ppHapusVerifikasiPengujian.setEnabled(akses.getverifikasi_pengujian_sampel_lab_kesehatan_lingkungan());
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

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

public class LabKeslingCariPenugasanPengujianSampel extends javax.swing.JDialog {
    private final DefaultTableModel tabModePenugasan,tabModeDetailPenugasan,tabModeRekapPenugasan;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;
    private File file;
    private FileWriter fileWriter;

    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public LabKeslingCariPenugasanPengujianSampel(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        tabModePenugasan=new DefaultTableModel(null,new Object[]{
                "Tgl.Penugasan","No.Penugasan","NIP PJ","Nama PJ","NIP Analis","Nama Analis","No.Permintaan","No.Pelanggan","Nama Pelanggan","Kode Sampel","Nama Sampel","Status Hasil","Catatan"
            }){
                @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbPenugasan.setModel(tabModePenugasan);

        tbPenugasan.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbPenugasan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 13; i++) {
            TableColumn column = tbPenugasan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(118);
            }else if(i==1){
                column.setPreferredWidth(120);
            }else if(i==2){
                column.setPreferredWidth(90);
            }else if(i==3){
                column.setPreferredWidth(150);
            }else if(i==4){
                column.setPreferredWidth(90);
            }else if(i==5){
                column.setPreferredWidth(150);
            }else if(i==6){
                column.setPreferredWidth(120);
            }else if(i==7){
                column.setPreferredWidth(76);
            }else if(i==8){
                column.setPreferredWidth(150);
            }else if(i==9){
                column.setPreferredWidth(70);
            }else if(i==10){
                column.setPreferredWidth(130);
            }else if(i==11){
                column.setPreferredWidth(98);
            }else if(i==12){
                column.setPreferredWidth(150);
            }
        }
        tbPenugasan.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeDetailPenugasan=new DefaultTableModel(null,new Object[]{
                "Kode","Nama Parameter","Metode Pengujian","Satuan","Kategori","Nilai Normal"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbDetailPenugasan.setModel(tabModeDetailPenugasan);
        tbDetailPenugasan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 6; i++) {
            TableColumn column = tbDetailPenugasan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(70);
            }else if(i==1){
                column.setPreferredWidth(220);
            }else if(i==2){
                column.setPreferredWidth(140);
            }else if(i==3){
                column.setPreferredWidth(70);
            }else if(i==4){
                column.setPreferredWidth(70);
            }else if(i==5){
                column.setPreferredWidth(80);
            }
        }
        tbDetailPenugasan.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeRekapPenugasan=new DefaultTableModel(null,new Object[]{
                "Tgl.Penugasan","No.Penugasan","NIP PJ","Nama PJ","NIP Analis","Nama Analis","No.Permintaan","No.Pelanggan","Nama Pelanggan","Kode Sampel",
                "Nama Sampel","Status Hasil","Catatan","Kode Param","Nama Parameter","Metode Pengujian","Satuan","Kategori","Nilai Normal"
            }){
                @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbRekapPenugasan.setModel(tabModeRekapPenugasan);

        tbRekapPenugasan.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbRekapPenugasan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 19; i++) {
            TableColumn column = tbRekapPenugasan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(118);
            }else if(i==1){
                column.setPreferredWidth(120);
            }else if(i==2){
                column.setPreferredWidth(90);
            }else if(i==3){
                column.setPreferredWidth(150);
            }else if(i==4){
                column.setPreferredWidth(90);
            }else if(i==5){
                column.setPreferredWidth(150);
            }else if(i==6){
                column.setPreferredWidth(120);
            }else if(i==7){
                column.setPreferredWidth(76);
            }else if(i==8){
                column.setPreferredWidth(150);
            }else if(i==9){
                column.setPreferredWidth(70);
            }else if(i==10){
                column.setPreferredWidth(130);
            }else if(i==11){
                column.setPreferredWidth(98);
            }else if(i==12){
                column.setPreferredWidth(150);
            }else if(i==13){
                column.setPreferredWidth(70);
            }else if(i==14){
                column.setPreferredWidth(220);
            }else if(i==15){
                column.setPreferredWidth(140);
            }else if(i==16){
                column.setPreferredWidth(70);
            }else if(i==17){
                column.setPreferredWidth(70);
            }else if(i==18){
                column.setPreferredWidth(80);
            }
        }
        tbRekapPenugasan.setDefaultRenderer(Object.class, new WarnaTable());
        
        NoPermintaan.setDocument(new batasInput((byte)20).getKata(NoPermintaan));
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));          
        if(koneksiDB.CARICEPAT().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampil();
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampil();
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampil();
                    }
                }
            });
        }  
        
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
        ppSuratPenugasan = new javax.swing.JMenuItem();
        ppHasilPengujian = new javax.swing.JMenuItem();
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
        BtnHapus = new widget.Button();
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
        tbPenugasan = new widget.Table();
        PanelAccor = new widget.PanelBiasa();
        ChkAccor = new widget.CekBox();
        scrollPaneDetail = new widget.ScrollPane();
        tbDetailPenugasan = new widget.Table();
        scrollPane2 = new widget.ScrollPane();
        tbRekapPenugasan = new widget.Table();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        ppSuratPenugasan.setBackground(new java.awt.Color(255, 255, 254));
        ppSuratPenugasan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppSuratPenugasan.setForeground(new java.awt.Color(50, 50, 50));
        ppSuratPenugasan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppSuratPenugasan.setText("Surat Penugasan");
        ppSuratPenugasan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppSuratPenugasan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppSuratPenugasan.setName("ppSuratPenugasan"); // NOI18N
        ppSuratPenugasan.setPreferredSize(new java.awt.Dimension(200, 25));
        ppSuratPenugasan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppSuratPenugasanActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppSuratPenugasan);

        ppHasilPengujian.setBackground(new java.awt.Color(255, 255, 254));
        ppHasilPengujian.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppHasilPengujian.setForeground(new java.awt.Color(50, 50, 50));
        ppHasilPengujian.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppHasilPengujian.setText("Hasil Pengujian");
        ppHasilPengujian.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppHasilPengujian.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppHasilPengujian.setName("ppHasilPengujian"); // NOI18N
        ppHasilPengujian.setPreferredSize(new java.awt.Dimension(200, 25));
        ppHasilPengujian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppHasilPengujianActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppHasilPengujian);

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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Penugasan Pengujian Sampel Laboratorium Kesehatan Lingkungan ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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
        TCari.setPreferredSize(new java.awt.Dimension(200, 23));
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
        label9.setPreferredSize(new java.awt.Dimension(65, 23));
        panelisi1.add(label9);

        LTotal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LTotal.setText("0");
        LTotal.setName("LTotal"); // NOI18N
        LTotal.setPreferredSize(new java.awt.Dimension(55, 23));
        panelisi1.add(LTotal);

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
        panelisi1.add(BtnHapus);

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

        label11.setText("Tgl.Penugasan :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(85, 23));
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
        label14.setPreferredSize(new java.awt.Dimension(55, 23));
        panelisi4.add(label14);

        Status.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Semua", "Sudah Keluar Hasil", "Belum Keluar Hasil" }));
        Status.setName("Status"); // NOI18N
        Status.setPreferredSize(new java.awt.Dimension(150, 23));
        panelisi4.add(Status);

        label15.setText("No.Permintaan :");
        label15.setName("label15"); // NOI18N
        label15.setPreferredSize(new java.awt.Dimension(97, 23));
        panelisi4.add(label15);

        NoPermintaan.setName("NoPermintaan"); // NOI18N
        NoPermintaan.setPreferredSize(new java.awt.Dimension(145, 23));
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

        label13.setText("Analis :");
        label13.setName("label13"); // NOI18N
        label13.setPreferredSize(new java.awt.Dimension(44, 23));
        panelisi3.add(label13);

        NamaPetugas.setEditable(false);
        NamaPetugas.setName("NamaPetugas"); // NOI18N
        NamaPetugas.setPreferredSize(new java.awt.Dimension(170, 23));
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
        NamaPelanggan.setPreferredSize(new java.awt.Dimension(170, 23));
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
        NamaSampel.setPreferredSize(new java.awt.Dimension(140, 23));
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

        tbPenugasan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbPenugasan.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbPenugasan.setComponentPopupMenu(jPopupMenu1);
        tbPenugasan.setName("tbPenugasan"); // NOI18N
        tbPenugasan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPenugasanMouseClicked(evt);
            }
        });
        scrollPane1.setViewportView(tbPenugasan);

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

        scrollPaneDetail.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1), "Detail Penugasan :", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        scrollPaneDetail.setComponentPopupMenu(jPopupMenu1);
        scrollPaneDetail.setName("scrollPaneDetail"); // NOI18N
        scrollPaneDetail.setOpaque(true);

        tbDetailPenugasan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbDetailPenugasan.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbDetailPenugasan.setComponentPopupMenu(jPopupMenu1);
        tbDetailPenugasan.setName("tbDetailPenugasan"); // NOI18N
        scrollPaneDetail.setViewportView(tbDetailPenugasan);

        PanelAccor.add(scrollPaneDetail, java.awt.BorderLayout.CENTER);

        jPanel2.add(PanelAccor, java.awt.BorderLayout.EAST);

        TabData.addTab("Data Penugasan", jPanel2);

        scrollPane2.setComponentPopupMenu(jPopupMenu1);
        scrollPane2.setName("scrollPane2"); // NOI18N
        scrollPane2.setOpaque(true);

        tbRekapPenugasan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbRekapPenugasan.setToolTipText("Silahkan klik untuk memilih data yang mau dihapus");
        tbRekapPenugasan.setName("tbRekapPenugasan"); // NOI18N
        scrollPane2.setViewportView(tbRekapPenugasan);

        TabData.addTab("Rekap Penugasan", scrollPane2);

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
            tbPenugasan.requestFocus();
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
            if(tabModePenugasan.getRowCount()==0){
                JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                TCari.requestFocus();
            }else if(tabModePenugasan.getRowCount()!=0){
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
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tgl.Penugasan</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Penugasan</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>NIP PJ</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama PJ</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>NIP Analis</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Analis</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Permintaan</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Pelanggan</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Pelanggan</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kode Sampel</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Sampel</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Status Hasil</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Catatan</b></td>").
                                            append("</tr>");
                                for (int i = 0; i < tabModePenugasan.getRowCount(); i++) {
                                    htmlContent.append("<tr class='isi'>").
                                                    append("<td valign='top'>").append(tbPenugasan.getValueAt(i,0).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbPenugasan.getValueAt(i,1).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbPenugasan.getValueAt(i,2).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbPenugasan.getValueAt(i,3).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbPenugasan.getValueAt(i,4).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbPenugasan.getValueAt(i,5).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbPenugasan.getValueAt(i,6).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbPenugasan.getValueAt(i,7).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbPenugasan.getValueAt(i,8).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbPenugasan.getValueAt(i,9).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbPenugasan.getValueAt(i,10).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbPenugasan.getValueAt(i,11).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbPenugasan.getValueAt(i,12).toString()).append("</td>").
                                                append("</tr>");
                                }
                                LoadHTML.setText(
                                    "<html>"+
                                      "<table width='100%' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
                                       htmlContent.toString()+
                                      "</table>"+
                                    "</html>"
                                );

                                f = new File("DataPenugasanPengujianSampel.html");            
                                bw = new BufferedWriter(new FileWriter(f));            
                                bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                                            "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                                            "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                                "<tr class='isi2'>"+
                                                    "<td valign='top' align='center'>"+
                                                        "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                                        akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                                        akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                                        "<font size='2' face='Tahoma'>DATA PENUGASAN PENGUJIAN SAMPEL<br><br></font>"+        
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
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tgl.Penugasan</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Penugasan</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>NIP PJ</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama PJ</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>NIP Analis</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Analis</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Permintaan</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Pelanggan</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Pelanggan</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kode Sampel</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Sampel</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Status Hasil</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Catatan</b></td>").
                                            append("</tr>");
                                for (int i = 0; i < tabModePenugasan.getRowCount(); i++) {
                                    htmlContent.append("<tr class='isi'>").
                                                    append("<td valign='top'>").append(tbPenugasan.getValueAt(i,0).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbPenugasan.getValueAt(i,1).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbPenugasan.getValueAt(i,2).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbPenugasan.getValueAt(i,3).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbPenugasan.getValueAt(i,4).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbPenugasan.getValueAt(i,5).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbPenugasan.getValueAt(i,6).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbPenugasan.getValueAt(i,7).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbPenugasan.getValueAt(i,8).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbPenugasan.getValueAt(i,9).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbPenugasan.getValueAt(i,10).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbPenugasan.getValueAt(i,11).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbPenugasan.getValueAt(i,12).toString()).append("</td>").
                                                append("</tr>");
                                }
                                LoadHTML.setText(
                                    "<html>"+
                                      "<table width='100%' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
                                       htmlContent.toString()+
                                      "</table>"+
                                    "</html>"
                                );

                                f = new File("DataPenugasanPengujianSampel.wps");            
                                bw = new BufferedWriter(new FileWriter(f));            
                                bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                                            "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                                            "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                                "<tr class='isi2'>"+
                                                    "<td valign='top' align='center'>"+
                                                        "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                                        akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                                        akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                                        "<font size='2' face='Tahoma'>DATA PENUGASAN PENGUJIAN SAMPEL<br><br></font>"+        
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
                                    "\"Tgl.Penugasan\";\"No.Penugasan\";\"NIP PJ\";\"Nama PJ\";\"NIP Analis\";\"Nama Analis\";\"No.Permintaan\";\"No.Pelanggan\";\"Nama Pelanggan\";\"Kode Sampel\";\"Nama Sampel\";\"Status Hasil\";\"Catatan\"\n"
                                ); 
                                for (int i = 0; i < tabModePenugasan.getRowCount(); i++) {
                                    htmlContent.append("\"").append(tbPenugasan.getValueAt(i,0).toString()).append("\";\"").append(tbPenugasan.getValueAt(i,1).toString()).append("\";\"").append(tbPenugasan.getValueAt(i,2).toString()).append("\";\"").append(tbPenugasan.getValueAt(i,3).toString()).append("\";\"").append(tbPenugasan.getValueAt(i,4).toString()).append("\";\"").append(tbPenugasan.getValueAt(i,5).toString()).append("\";\"").append(tbPenugasan.getValueAt(i,6).toString()).append("\";\"").append(tbPenugasan.getValueAt(i,7).toString()).append("\";\"").append(tbPenugasan.getValueAt(i,8).toString()).append("\";\"").append(tbPenugasan.getValueAt(i,9).toString()).append("\";\"").append(tbPenugasan.getValueAt(i,10).toString()).append("\";\"").append(tbPenugasan.getValueAt(i,11).toString()).append("\";\"").append(tbPenugasan.getValueAt(i,12).toString()).append("\"\n");
                                }
                                f = new File("DataPenugasanPengujianSampel.csv");            
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
            if(tabModeRekapPenugasan.getRowCount()==0){
                JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                TCari.requestFocus();
            }else if(tabModeRekapPenugasan.getRowCount()!=0){
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
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tgl.Penugasan</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Penugasan</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>NIP PJ</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama PJ</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>NIP Analis</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Analis</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Permintaan</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Pelanggan</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Pelanggan</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kode Sampel</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Sampel</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Status Hasil</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Catatan</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kode Param</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Parameter</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Metode Pengujian</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Satuan</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kategori</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nilai Normal</b></td>").
                                            append("</tr>");
                                for (int i = 0; i < tabModeRekapPenugasan.getRowCount(); i++) {
                                    htmlContent.append("<tr class='isi'>").
                                                    append("<td valign='top'>").append(tbRekapPenugasan.getValueAt(i,0).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapPenugasan.getValueAt(i,1).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapPenugasan.getValueAt(i,2).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapPenugasan.getValueAt(i,3).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapPenugasan.getValueAt(i,4).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapPenugasan.getValueAt(i,5).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapPenugasan.getValueAt(i,6).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapPenugasan.getValueAt(i,7).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapPenugasan.getValueAt(i,8).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapPenugasan.getValueAt(i,9).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapPenugasan.getValueAt(i,10).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapPenugasan.getValueAt(i,11).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapPenugasan.getValueAt(i,12).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapPenugasan.getValueAt(i,13).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapPenugasan.getValueAt(i,14).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapPenugasan.getValueAt(i,15).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapPenugasan.getValueAt(i,16).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapPenugasan.getValueAt(i,17).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapPenugasan.getValueAt(i,18).toString()).append("</td>").
                                                append("</tr>");
                                }
                                LoadHTML.setText(
                                    "<html>"+
                                      "<table width='1500px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
                                       htmlContent.toString()+
                                      "</table>"+
                                    "</html>"
                                );

                                f = new File("DataRekapPenugasanPengujianSampel.html");            
                                bw = new BufferedWriter(new FileWriter(f));            
                                bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                                            "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                                            "<table width='1500px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                                "<tr class='isi2'>"+
                                                    "<td valign='top' align='center'>"+
                                                        "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                                        akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                                        akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                                        "<font size='2' face='Tahoma'>DATA REKAP PENUGASAN PENGUJIAN SAMPEL<br><br></font>"+        
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
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tgl.Penugasan</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Penugasan</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>NIP PJ</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama PJ</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>NIP Analis</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Analis</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Permintaan</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Pelanggan</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Pelanggan</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kode Sampel</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Sampel</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Status Hasil</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Catatan</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kode Param</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Parameter</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Metode Pengujian</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Satuan</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kategori</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nilai Normal</b></td>").
                                            append("</tr>");
                                for (int i = 0; i < tabModeRekapPenugasan.getRowCount(); i++) {
                                    htmlContent.append("<tr class='isi'>").
                                                    append("<td valign='top'>").append(tbRekapPenugasan.getValueAt(i,0).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapPenugasan.getValueAt(i,1).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapPenugasan.getValueAt(i,2).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapPenugasan.getValueAt(i,3).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapPenugasan.getValueAt(i,4).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapPenugasan.getValueAt(i,5).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapPenugasan.getValueAt(i,6).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapPenugasan.getValueAt(i,7).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapPenugasan.getValueAt(i,8).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapPenugasan.getValueAt(i,9).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapPenugasan.getValueAt(i,10).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapPenugasan.getValueAt(i,11).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapPenugasan.getValueAt(i,12).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapPenugasan.getValueAt(i,13).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapPenugasan.getValueAt(i,14).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapPenugasan.getValueAt(i,15).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapPenugasan.getValueAt(i,16).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapPenugasan.getValueAt(i,17).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapPenugasan.getValueAt(i,18).toString()).append("</td>").
                                                append("</tr>");
                                }
                                LoadHTML.setText(
                                    "<html>"+
                                      "<table width='1500px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
                                       htmlContent.toString()+
                                      "</table>"+
                                    "</html>"
                                );

                                f = new File("DataRekapPenugasanPengujianSampel.wps");            
                                bw = new BufferedWriter(new FileWriter(f));            
                                bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                                            "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                                            "<table width='1500px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                                "<tr class='isi2'>"+
                                                    "<td valign='top' align='center'>"+
                                                        "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                                        akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                                        akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                                        "<font size='2' face='Tahoma'>DATA REKAP PENUGASAN PENGUJIAN SAMPEL<br><br></font>"+        
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
                                    "\"Tgl.Penugasan\";\"No.Penugasan\";\"NIP PJ\";\"Nama PJ\";\"NIP Analis\";\"Nama Analis\";\"No.Permintaan\";\"No.Pelanggan\";\"Nama Pelanggan\";\"Kode Sampel\";\"Nama Sampel\";\"Status Hasil\";\"Catatan\";\"Kode Param\";\"Nama Parameter\";\"Metode Pengujian\";\"Satuan\";\"Kategori\";\"Nilai Normal\"\n"
                                ); 
                                for (int i = 0; i < tabModeRekapPenugasan.getRowCount(); i++) {
                                    htmlContent.append("\"").append(tbRekapPenugasan.getValueAt(i,0).toString()).append("\";\"").append(tbRekapPenugasan.getValueAt(i,1).toString()).append("\";\"").append(tbRekapPenugasan.getValueAt(i,2).toString()).append("\";\"").append(tbRekapPenugasan.getValueAt(i,3).toString()).append("\";\"").append(tbRekapPenugasan.getValueAt(i,4).toString()).append("\";\"").append(tbRekapPenugasan.getValueAt(i,5).toString()).append("\";\"").append(tbRekapPenugasan.getValueAt(i,6).toString()).append("\";\"").append(tbRekapPenugasan.getValueAt(i,7).toString()).append("\";\"").append(tbRekapPenugasan.getValueAt(i,8).toString()).append("\";\"").append(tbRekapPenugasan.getValueAt(i,9).toString()).append("\";\"").append(tbRekapPenugasan.getValueAt(i,10).toString()).append("\";\"").append(tbRekapPenugasan.getValueAt(i,11).toString()).append("\";\"").append(tbRekapPenugasan.getValueAt(i,12).toString()).append("\";\"").append(tbRekapPenugasan.getValueAt(i,13).toString()).append("\";\"").append(tbRekapPenugasan.getValueAt(i,14).toString()).append("\";\"").append(tbRekapPenugasan.getValueAt(i,15).toString()).append("\";\"").append(tbRekapPenugasan.getValueAt(i,16).toString()).append("\";\"").append(tbRekapPenugasan.getValueAt(i,17).toString()).append("\";\"").append(tbRekapPenugasan.getValueAt(i,18).toString()).append("\"\n");
                                }
                                f = new File("DataRekapPenugasanPengujianSampel.csv");            
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

    private void ppHasilPengujianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppHasilPengujianActionPerformed
        if(tbPenugasan.getSelectedRow()!= -1){
            if(ChkAccor.isSelected()==true){
                if(akses.getkode().equals("Admin Utama")){
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));  
                    try {
                        file=new File("./cache/hasilpengujiansampellabkesling.iyem");
                        file.createNewFile();
                        fileWriter = new FileWriter(file);
                        StringBuilder iyembuilder = new StringBuilder();

                        for(i=0;i<tbDetailPenugasan.getRowCount();i++){
                            iyembuilder.append("{\"Kode\":\"").append(tbDetailPenugasan.getValueAt(i,0).toString()).append("\",\"NamaParameter\":\"").append(tbDetailPenugasan.getValueAt(i,1).toString()).append("\",\"MetodePengujian\":\"").append(tbDetailPenugasan.getValueAt(i,2).toString()).append("\",\"Satuan\":\"").append(tbDetailPenugasan.getValueAt(i,3).toString()).append("\",\"Kategori\":\"").append(tbDetailPenugasan.getValueAt(i,4).toString()).append("\",\"Normal\":\"").append(tbDetailPenugasan.getValueAt(i,5).toString()).append("\"},");
                        }

                        if (iyembuilder.length() > 0) {
                            iyembuilder.setLength(iyembuilder.length() - 1);
                            fileWriter.write("{\"hasilpengujiansampellabkesling\":["+iyembuilder+"]}");
                            fileWriter.flush();
                        }

                        fileWriter.close();
                        iyembuilder=null;
                    } catch (Exception e) {
                        System.out.println("Notifikasi : "+e);
                    }
                    LabKeslingHasilPengujianSampel form=new LabKeslingHasilPengujianSampel(null,false);
                    form.addWindowListener(new WindowListener() {
                        @Override
                        public void windowOpened(WindowEvent e) {}
                        @Override
                        public void windowClosing(WindowEvent e) {}
                        @Override
                        public void windowClosed(WindowEvent e) {
                            if(form.berhasil==true){
                                tbPenugasan.setValueAt("Sudah Keluar Hasil",tbPenugasan.getSelectedRow(),11);
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
                    form.setData(tbPenugasan.getValueAt(tbPenugasan.getSelectedRow(),1).toString(),tbPenugasan.getValueAt(tbPenugasan.getSelectedRow(),6).toString(),tbPenugasan.getValueAt(tbPenugasan.getSelectedRow(),7).toString(),tbPenugasan.getValueAt(tbPenugasan.getSelectedRow(),8).toString(),tbPenugasan.getValueAt(tbPenugasan.getSelectedRow(),9).toString(),tbPenugasan.getValueAt(tbPenugasan.getSelectedRow(),10).toString(),tbPenugasan.getValueAt(tbPenugasan.getSelectedRow(),4).toString(),tbPenugasan.getValueAt(tbPenugasan.getSelectedRow(),5).toString());
                    form.setVisible(true);
                    this.setCursor(Cursor.getDefaultCursor());
                }else{
                    if(akses.getkode().equals(tbPenugasan.getValueAt(tbPenugasan.getSelectedRow(),4).toString())){
                        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));  
                        try {
                            file=new File("./cache/hasilpengujiansampellabkesling.iyem");
                            file.createNewFile();
                            fileWriter = new FileWriter(file);
                            StringBuilder iyembuilder = new StringBuilder();

                            for(i=0;i<tbDetailPenugasan.getRowCount();i++){
                                iyembuilder.append("{\"Kode\":\"").append(tbDetailPenugasan.getValueAt(i,0).toString()).append("\",\"NamaParameter\":\"").append(tbDetailPenugasan.getValueAt(i,1).toString()).append("\",\"MetodePengujian\":\"").append(tbDetailPenugasan.getValueAt(i,2).toString()).append("\",\"Satuan\":\"").append(tbDetailPenugasan.getValueAt(i,3).toString()).append("\",\"Kategori\":\"").append(tbDetailPenugasan.getValueAt(i,4).toString()).append("\",\"Sampel\":\"").append(tbDetailPenugasan.getValueAt(i,5).toString()).append("\"},");
                            }

                            if (iyembuilder.length() > 0) {
                                iyembuilder.setLength(iyembuilder.length() - 1);
                                fileWriter.write("{\"hasilpengujiansampellabkesling\":["+iyembuilder+"]}");
                                fileWriter.flush();
                            }

                            fileWriter.close();
                            iyembuilder=null;
                        } catch (Exception e) {
                            System.out.println("Notifikasi : "+e);
                        }
                        LabKeslingHasilPengujianSampel form=new LabKeslingHasilPengujianSampel(null,false);
                        form.addWindowListener(new WindowListener() {
                            @Override
                            public void windowOpened(WindowEvent e) {}
                            @Override
                            public void windowClosing(WindowEvent e) {}
                            @Override
                            public void windowClosed(WindowEvent e) {
                                if(form.berhasil==true){
                                    tbPenugasan.setValueAt("Sudah Keluar Hasil",tbPenugasan.getSelectedRow(),11);
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
                        form.setData(tbPenugasan.getValueAt(tbPenugasan.getSelectedRow(),1).toString(),tbPenugasan.getValueAt(tbPenugasan.getSelectedRow(),6).toString(),tbPenugasan.getValueAt(tbPenugasan.getSelectedRow(),7).toString(),tbPenugasan.getValueAt(tbPenugasan.getSelectedRow(),8).toString(),tbPenugasan.getValueAt(tbPenugasan.getSelectedRow(),9).toString(),tbPenugasan.getValueAt(tbPenugasan.getSelectedRow(),10).toString(),tbPenugasan.getValueAt(tbPenugasan.getSelectedRow(),4).toString(),tbPenugasan.getValueAt(tbPenugasan.getSelectedRow(),5).toString());
                        form.setVisible(true);
                        this.setCursor(Cursor.getDefaultCursor());
                    }else{
                        JOptionPane.showMessageDialog(null,"Hasil hanya bisa dikeluarkan oleh analis yang ditugaskan..!!");
                    }
                }
            }else if(ChkAccor.isSelected()==false){
                JOptionPane.showMessageDialog(null,"Silahkan tampilkan detail penugasan...!!!");
            }
        }else{
            JOptionPane.showMessageDialog(null,"Silahkan pilih data penugasan...!!!");
        }
    }//GEN-LAST:event_ppHasilPengujianActionPerformed

    private void TabDataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabDataMouseClicked
        if(TabData.getSelectedIndex()==0){
            tampil();
        }else if(TabData.getSelectedIndex()==1){
            tampil2();
        }
    }//GEN-LAST:event_TabDataMouseClicked

    private void ChkAccorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkAccorActionPerformed
        if(tbPenugasan.getSelectedRow()!= -1){
            if(ChkAccor.isSelected()==true){
                ChkAccor.setVisible(false);
                PanelAccor.setPreferredSize(new Dimension(670,HEIGHT));
                scrollPaneDetail.setVisible(true);  
                ChkAccor.setVisible(true);
                Valid.tabelKosong(tabModeDetailPenugasan);
                try {
                    ps=koneksi.prepareStatement(
                        "select labkesling_detail_penugasan_pengujian_sampel.kode_parameter,labkesling_parameter_pengujian.nama_parameter,labkesling_parameter_pengujian.metode_pengujian,labkesling_parameter_pengujian.satuan,"+
                        "labkesling_parameter_pengujian.kategori,labkesling_nilai_normal_baku_mutu.nilai_normal from labkesling_detail_penugasan_pengujian_sampel inner join labkesling_parameter_pengujian "+
                        "on labkesling_detail_penugasan_pengujian_sampel.kode_parameter=labkesling_parameter_pengujian.kode_parameter inner join labkesling_nilai_normal_baku_mutu "+
                        "on labkesling_nilai_normal_baku_mutu.kode_parameter=labkesling_parameter_pengujian.kode_parameter where labkesling_detail_penugasan_pengujian_sampel.no_penugasan=? "+
                        "and labkesling_nilai_normal_baku_mutu.kode_sampel=? order by labkesling_detail_penugasan_pengujian_sampel.kode_parameter"
                    );
                    try {
                        ps.setString(1,tbPenugasan.getValueAt(tbPenugasan.getSelectedRow(),1).toString());
                        ps.setString(2,tbPenugasan.getValueAt(tbPenugasan.getSelectedRow(),9).toString());
                        rs=ps.executeQuery();
                        while(rs.next()){
                            tabModeDetailPenugasan.addRow(new Object[]{
                                rs.getString("kode_parameter"),rs.getString("nama_parameter"),rs.getString("metode_pengujian"),rs.getString("satuan"),rs.getString("kategori"),rs.getString("nilai_normal")
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
            JOptionPane.showMessageDialog(null,"Silahkan pilih data penugasan...!!!");
        }
    }//GEN-LAST:event_ChkAccorActionPerformed

    private void tbPenugasanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPenugasanMouseClicked
        ChkAccorActionPerformed(null);
    }//GEN-LAST:event_tbPenugasanMouseClicked

    private void ppSuratPenugasanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppSuratPenugasanActionPerformed
        if(tbPenugasan.getSelectedRow()!= -1){
            if(ChkAccor.isSelected()==false){
                JOptionPane.showMessageDialog(null,"Silahkan tampilkan data detail penugasan terlebih dahulu...!!!");
            }else{
                if(tbDetailPenugasan.getRowCount()!=0){
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    Sequel.queryu("delete from temporary");
                    for(i=0;i<tbDetailPenugasan.getRowCount();i++){ 
                        Sequel.menyimpan("temporary","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?",38,new String[]{
                            "0",tbDetailPenugasan.getValueAt(i,0).toString(),tbDetailPenugasan.getValueAt(i,1).toString(),tbDetailPenugasan.getValueAt(i,2).toString(),tbDetailPenugasan.getValueAt(i,4).toString(),"","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","",""
                        });               
                    }
                    //"Tgl.Penugasan"0,"No.Penugasan"1,"NIP PJ"2,"Nama PJ"3,"NIP Analis"4,"Nama Analis"5,"No.Permintaan"6,"No.Pelanggan"7,"Nama Pelanggan"8,"Kode Sampel"9,"Nama Sampel"10,"Status Hasil"11,"Catatan"12
                    Map<String, Object> param = new HashMap<>();
                    param.put("pjlaborat",tbPenugasan.getValueAt(tbPenugasan.getSelectedRow(),3).toString());
                    param.put("kodepjlaborat",tbPenugasan.getValueAt(tbPenugasan.getSelectedRow(),2).toString());
                    param.put("analis",tbPenugasan.getValueAt(tbPenugasan.getSelectedRow(),5).toString()+" NIP : "+tbPenugasan.getValueAt(tbPenugasan.getSelectedRow(),4).toString());
                    param.put("nopenugasan",tbPenugasan.getValueAt(tbPenugasan.getSelectedRow(),1).toString());
                    param.put("nomorpermintaan",tbPenugasan.getValueAt(tbPenugasan.getSelectedRow(),6).toString());
                    param.put("jenisampel",tbPenugasan.getValueAt(tbPenugasan.getSelectedRow(),9).toString());
                    param.put("kodesampel",tbPenugasan.getValueAt(tbPenugasan.getSelectedRow(),10).toString());
                    param.put("catatan",tbPenugasan.getValueAt(tbPenugasan.getSelectedRow(),12).toString());
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());   
                    param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                    String finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",tbPenugasan.getValueAt(tbPenugasan.getSelectedRow(),2).toString());
                    param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbPenugasan.getValueAt(tbPenugasan.getSelectedRow(),3).toString()+"\nID "+(finger.equals("")?tbPenugasan.getValueAt(tbPenugasan.getSelectedRow(),2).toString():finger)+"\n"+Valid.SetTgl3(tbPenugasan.getValueAt(tbPenugasan.getSelectedRow(),0).toString())); 
                    Valid.MyReport("rptPenugasanPengujianSampelLaboratKesling.jasper","report","::[ Penugasan Pengujian Sampel Laborat Kesehatan Lingkungan ]::",param);       
                    this.setCursor(Cursor.getDefaultCursor());
                }else{
                    JOptionPane.showMessageDialog(null,"Silahkan tampilkan data detail penugasan terlebih dahulu...!!!");
                }
            }
        }else{
            JOptionPane.showMessageDialog(null,"Silahkan pilih data penugasan...!!!");
        }
    }//GEN-LAST:event_ppSuratPenugasanActionPerformed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if(TabData.getSelectedIndex()==0){
            if(tbPenugasan.getSelectedRow()!= -1){
                if(tbPenugasan.getValueAt(tbPenugasan.getSelectedRow(),11).toString().equals("Belum Keluar Hasil")){
                    if(Sequel.queryutf("delete from labkesling_penugasan_pengujian_sampel where no_penugasan='"+tbPenugasan.getValueAt(tbPenugasan.getSelectedRow(),1).toString()+"'")==true){
                        if(Sequel.cariInteger("select count(labkesling_penugasan_pengujian_sampel.no_permintaan) from labkesling_penugasan_pengujian_sampel where labkesling_penugasan_pengujian_sampel.no_permintaan=?",tbPenugasan.getValueAt(tbPenugasan.getSelectedRow(),6).toString())==0){
                            Sequel.queryu("update labkesling_permintaan_pengujian_sampel_dilayani set status='Belum Ada Penugasan' where no_permintaan='"+tbPenugasan.getValueAt(tbPenugasan.getSelectedRow(),6).toString()+"'");
                        }
                        tabModePenugasan.removeRow(tbPenugasan.getSelectedRow());
                        LTotal.setText(tabModePenugasan.getRowCount()+"");
                    }
                }else{
                    JOptionPane.showMessageDialog(null,"Sudah keluar hasil, tidak bisa dihapus...!");
                }
            }else{
                JOptionPane.showMessageDialog(null,"Silahkan pilih data penugasan...!!!");
            }
        }else if(TabData.getSelectedIndex()==1){
            if(tbRekapPenugasan.getSelectedRow()!= -1){
                if(tbRekapPenugasan.getValueAt(tbRekapPenugasan.getSelectedRow(),11).toString().equals("Belum Keluar Hasil")){
                    if(Sequel.queryutf("delete from labkesling_detail_penugasan_pengujian_sampel where no_penugasan='"+tbRekapPenugasan.getValueAt(tbRekapPenugasan.getSelectedRow(),1).toString()+"' and kode_parameter='"+tbRekapPenugasan.getValueAt(tbRekapPenugasan.getSelectedRow(),13).toString()+"'")==true){
                        tabModeRekapPenugasan.removeRow(tbRekapPenugasan.getSelectedRow());
                        LTotal.setText(tabModeRekapPenugasan.getRowCount()+"");
                    }
                }else{
                    JOptionPane.showMessageDialog(null,"Sudah keluar hasil, tidak bisa dihapus...!");
                }
            }else{
                JOptionPane.showMessageDialog(null,"Silahkan pilih data rekap penugasan...!!!");
            }
        }
    }//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari, BtnPrint);
        }
    }//GEN-LAST:event_BtnHapusKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            LabKeslingCariPenugasanPengujianSampel dialog = new LabKeslingCariPenugasanPengujianSampel(new javax.swing.JFrame(), true);
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
    private widget.Button BtnHapus;
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
    private javax.swing.JMenuItem ppHasilPengujian;
    private javax.swing.JMenuItem ppSuratPenugasan;
    private widget.ScrollPane scrollPane1;
    private widget.ScrollPane scrollPane2;
    private widget.ScrollPane scrollPaneDetail;
    private widget.Table tbDetailPenugasan;
    private widget.Table tbPenugasan;
    private widget.Table tbRekapPenugasan;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabModePenugasan);
        try{  
            ps=koneksi.prepareStatement(
                        "select labkesling_penugasan_pengujian_sampel.tanggal,labkesling_penugasan_pengujian_sampel.no_penugasan,labkesling_penugasan_pengujian_sampel.nip_pj,"+
                        "pjlab.nama as namapj,labkesling_penugasan_pengujian_sampel.nip_pelaksana,pelaksana.nama as namapelaksana,labkesling_penugasan_pengujian_sampel.no_permintaan,"+
                        "labkesling_permintaan_pengujian_sampel.kode_pelanggan,labkesling_pelanggan.nama_pelanggan,labkesling_permintaan_pengujian_sampel.kode_sampel,"+
                        "labkesling_master_sampel.nama_sampel,labkesling_penugasan_pengujian_sampel.status,labkesling_penugasan_pengujian_sampel.catatan from labkesling_penugasan_pengujian_sampel "+
                        "inner join petugas as pjlab on labkesling_penugasan_pengujian_sampel.nip_pj=pjlab.nip "+
                        "inner join petugas as pelaksana on labkesling_penugasan_pengujian_sampel.nip_pelaksana=pelaksana.nip "+
                        "inner join labkesling_permintaan_pengujian_sampel on labkesling_permintaan_pengujian_sampel.no_permintaan=labkesling_penugasan_pengujian_sampel.no_permintaan "+
                        "inner join labkesling_pelanggan on labkesling_permintaan_pengujian_sampel.kode_pelanggan=labkesling_pelanggan.kode_pelanggan "+
                        "inner join labkesling_master_sampel on labkesling_master_sampel.kode_sampel=labkesling_permintaan_pengujian_sampel.kode_sampel "+
                        "where labkesling_penugasan_pengujian_sampel.tanggal between ? and ? "+(NoPermintaan.getText().trim().equals("")?"":" and labkesling_permintaan_pengujian_sampel.no_permintaan='"+NoPermintaan.getText()+"' ")+
                        (Status.getSelectedItem().toString().equals("Semua")?"":" and labkesling_penugasan_pengujian_sampel.status='"+Status.getSelectedItem().toString()+"' ")+
                        (NamaPetugas.getText().trim().equals("")?"":" and labkesling_penugasan_pengujian_sampel.nip_pelaksana='"+KodePetugas.getText()+"' ")+
                        (NamaPelanggan.getText().trim().equals("")?"":" and labkesling_permintaan_pengujian_sampel.kode_pelanggan='"+KodePelanggan.getText()+"' ")+
                        (NamaSampel.getText().trim().equals("")?"":" and labkesling_permintaan_pengujian_sampel.kode_sampel='"+KodeSampel.getText()+"' ")+
                        (TCari.getText().trim().equals("")?"":" and (labkesling_penugasan_pengujian_sampel.no_penugasan like ? or labkesling_penugasan_pengujian_sampel.catatan like ? or "+
                        "labkesling_penugasan_pengujian_sampel.nip_pj like ? or pjlab.nama like ?) ")+
                        "order by labkesling_penugasan_pengujian_sampel.tanggal,labkesling_penugasan_pengujian_sampel.no_permintaan,labkesling_penugasan_pengujian_sampel.no_penugasan");
                
            try {
                ps.setString(1,Valid.SetTgl(Tanggal1.getSelectedItem()+"")+" 00:00:00");
                ps.setString(2,Valid.SetTgl(Tanggal2.getSelectedItem()+"")+" 23:59:59");
                if(!TCari.getText().trim().equals("")){
                    ps.setString(3,"%"+TCari.getText()+"%");
                    ps.setString(4,"%"+TCari.getText()+"%");
                    ps.setString(5,"%"+TCari.getText()+"%");
                    ps.setString(6,"%"+TCari.getText()+"%");
                }
                    
                rs=ps.executeQuery();
                while(rs.next()){
                    tabModePenugasan.addRow(new Object[]{
                        rs.getString("tanggal"),rs.getString("no_penugasan"),rs.getString("nip_pj"),rs.getString("namapj"),rs.getString("nip_pelaksana"),rs.getString("namapelaksana"),
                        rs.getString("no_permintaan"),rs.getString("kode_pelanggan"),rs.getString("nama_pelanggan"),rs.getString("kode_sampel"),rs.getString("nama_sampel"),rs.getString("status"),
                        rs.getString("catatan")
                    }); 
                }        
                LTotal.setText(tabModePenugasan.getRowCount()+"");
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
        Valid.tabelKosong(tabModeRekapPenugasan);
        try{  
            ps=koneksi.prepareStatement(
                        "select labkesling_penugasan_pengujian_sampel.tanggal,labkesling_penugasan_pengujian_sampel.no_penugasan,labkesling_penugasan_pengujian_sampel.nip_pj,"+
                        "pjlab.nama as namapj,labkesling_penugasan_pengujian_sampel.nip_pelaksana,pelaksana.nama as namapelaksana,labkesling_penugasan_pengujian_sampel.no_permintaan,"+
                        "labkesling_permintaan_pengujian_sampel.kode_pelanggan,labkesling_pelanggan.nama_pelanggan,labkesling_permintaan_pengujian_sampel.kode_sampel,"+
                        "labkesling_master_sampel.nama_sampel,labkesling_penugasan_pengujian_sampel.status,labkesling_penugasan_pengujian_sampel.catatan,"+
                        "labkesling_detail_penugasan_pengujian_sampel.kode_parameter,labkesling_parameter_pengujian.nama_parameter,labkesling_parameter_pengujian.metode_pengujian,"+
                        "labkesling_parameter_pengujian.satuan,labkesling_parameter_pengujian.kategori,labkesling_nilai_normal_baku_mutu.nilai_normal "+
                        "from labkesling_penugasan_pengujian_sampel inner join petugas as pjlab on labkesling_penugasan_pengujian_sampel.nip_pj=pjlab.nip "+
                        "inner join petugas as pelaksana on labkesling_penugasan_pengujian_sampel.nip_pelaksana=pelaksana.nip "+
                        "inner join labkesling_permintaan_pengujian_sampel on labkesling_permintaan_pengujian_sampel.no_permintaan=labkesling_penugasan_pengujian_sampel.no_permintaan "+
                        "inner join labkesling_pelanggan on labkesling_permintaan_pengujian_sampel.kode_pelanggan=labkesling_pelanggan.kode_pelanggan "+
                        "inner join labkesling_master_sampel on labkesling_master_sampel.kode_sampel=labkesling_permintaan_pengujian_sampel.kode_sampel "+
                        "inner join labkesling_detail_penugasan_pengujian_sampel on labkesling_penugasan_pengujian_sampel.no_penugasan=labkesling_detail_penugasan_pengujian_sampel.no_penugasan "+
                        "inner join labkesling_parameter_pengujian on labkesling_detail_penugasan_pengujian_sampel.kode_parameter=labkesling_parameter_pengujian.kode_parameter "+
                        "inner join labkesling_nilai_normal_baku_mutu on labkesling_nilai_normal_baku_mutu.kode_parameter=labkesling_parameter_pengujian.kode_parameter "+
                        "and labkesling_nilai_normal_baku_mutu.kode_sampel=labkesling_permintaan_pengujian_sampel.kode_sampel "+
                        "where labkesling_penugasan_pengujian_sampel.tanggal between ? and ? "+(NoPermintaan.getText().trim().equals("")?"":" and labkesling_permintaan_pengujian_sampel.no_permintaan='"+NoPermintaan.getText()+"' ")+
                        (Status.getSelectedItem().toString().equals("Semua")?"":" and labkesling_penugasan_pengujian_sampel.status='"+Status.getSelectedItem().toString()+"' ")+
                        (NamaPetugas.getText().trim().equals("")?"":" and labkesling_penugasan_pengujian_sampel.nip_pelaksana='"+KodePetugas.getText()+"' ")+
                        (NamaPelanggan.getText().trim().equals("")?"":" and labkesling_permintaan_pengujian_sampel.kode_pelanggan='"+KodePelanggan.getText()+"' ")+
                        (NamaSampel.getText().trim().equals("")?"":" and labkesling_permintaan_pengujian_sampel.kode_sampel='"+KodeSampel.getText()+"' ")+
                        (TCari.getText().trim().equals("")?"":" and (labkesling_penugasan_pengujian_sampel.no_penugasan like ? or labkesling_penugasan_pengujian_sampel.catatan like ? or "+
                        "labkesling_penugasan_pengujian_sampel.nip_pj like ? or pjlab.nama like ? or labkesling_detail_penugasan_pengujian_sampel.kode_parameter like ? or "+
                        "labkesling_parameter_pengujian.nama_parameter like ? or labkesling_parameter_pengujian.metode_pengujian like ? or labkesling_parameter_pengujian.kategori like ?) ")+
                        "order by labkesling_penugasan_pengujian_sampel.tanggal,labkesling_penugasan_pengujian_sampel.no_permintaan,labkesling_penugasan_pengujian_sampel.no_penugasan");
                
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
                }
                    
                rs=ps.executeQuery();
                while(rs.next()){
                    tabModeRekapPenugasan.addRow(new Object[]{
                        rs.getString("tanggal"),rs.getString("no_penugasan"),rs.getString("nip_pj"),rs.getString("namapj"),rs.getString("nip_pelaksana"),rs.getString("namapelaksana"),
                        rs.getString("no_permintaan"),rs.getString("kode_pelanggan"),rs.getString("nama_pelanggan"),rs.getString("kode_sampel"),rs.getString("nama_sampel"),rs.getString("status"),
                        rs.getString("catatan"),rs.getString("kode_parameter"),rs.getString("nama_parameter"),rs.getString("metode_pengujian"),rs.getString("satuan"),rs.getString("kategori"),
                        rs.getString("nilai_normal")
                    }); 
                }        
                LTotal.setText(tabModeRekapPenugasan.getRowCount()+"");
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
        BtnPrint.setEnabled(akses.getpenugasan_pengujian_sampel_lab_kesehatan_lingkungan());
        ppHasilPengujian.setEnabled(akses.gethasil_pengujian_sampel_lab_kesehatan_lingkungan());
        ppSuratPenugasan.setEnabled(akses.getpenugasan_pengujian_sampel_lab_kesehatan_lingkungan());
        BtnHapus.setEnabled(akses.getpenugasan_pengujian_sampel_lab_kesehatan_lingkungan());
    }
}

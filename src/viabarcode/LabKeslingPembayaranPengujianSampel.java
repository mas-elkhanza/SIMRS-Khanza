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

public class LabKeslingPembayaranPengujianSampel extends javax.swing.JDialog {
    private final DefaultTableModel tabModeValidasi,tabModeDetailValidasi;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;

    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public LabKeslingPembayaranPengujianSampel(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        tabModeValidasi=new DefaultTableModel(null,new Object[]{
                "Tgl.Validasi","No.Validasi","NIP PJ Validasi","Nama PJ Validasi","No.Permintaan","No.Pelanggan","Nama Pelanggan","Kode Sampel","Nama Sampel","Titik Sampel","Penerimaan Sampel","Status Bayar","Catatan","NIP PJ Verifikasi","Nama PJ Verifikasi"
            }){
                @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbValidasi.setModel(tabModeValidasi);

        tbValidasi.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbValidasi.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 15; i++) {
            TableColumn column = tbValidasi.getColumnModel().getColumn(i);
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
                column.setPreferredWidth(78);
            }else if(i==10){
                column.setPreferredWidth(150);
            }else if(i==11){
                column.setPreferredWidth(118);
            }else if(i==12){
                column.setPreferredWidth(200);
            }else if(i==13){
                column.setPreferredWidth(90);
            }else if(i==14){
                column.setPreferredWidth(150);
            }
        }
        tbValidasi.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeDetailValidasi=new DefaultTableModel(null,new Object[]{
                "Kode","Nama Parameter","Satuan","Hasil Pemeriksaan","Keterangan","Nilai Normal","Metode Pengujian","Kategori","NIP Analis","Nama Analis","NIP PJ Pengujian","Nama PJ Pengujian",
                "Jasa Sarana","Paket BHP","Jasa PJ Lab","Jasa PJ Pengujian","Jasa Verifikator","Jasa Petugas","KSO","Jasa Menejemen","Biaya"
            }){
            @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
             Class[] types = new Class[] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                java.lang.Object.class, java.lang.Object.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, 
                java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, 
                java.lang.Double.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbDetailValidasi.setModel(tabModeDetailValidasi);
        tbDetailValidasi.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 21; i++) {
            TableColumn column = tbDetailValidasi.getColumnModel().getColumn(i);
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
                column.setPreferredWidth(90);
            }else if(i==9){
                column.setPreferredWidth(150);
            }else if(i==10){
                column.setPreferredWidth(90);
            }else if(i==11){
                column.setPreferredWidth(150);
            }else if(i==20){
                column.setPreferredWidth(100);
            }else {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }
        }
        tbDetailValidasi.setDefaultRenderer(Object.class, new WarnaTable());

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
        PanelAccor.setPreferredSize(new Dimension(15,HEIGHT));
        scrollPaneDetail.setVisible(false); 
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        KodeSampel = new widget.TextBox();
        KodePelanggan = new widget.TextBox();
        KodePetugas = new widget.TextBox();
        LoadHTML = new widget.editorpane();
        internalFrame1 = new widget.InternalFrame();
        jPanel1 = new javax.swing.JPanel();
        panelisi1 = new widget.panelisi();
        label9 = new widget.Label();
        LTotal = new widget.Label();
        BtnAll = new widget.Button();
        BtnPrint = new widget.Button();
        BtnBayarTagihan = new widget.Button();
        BtnRekapPembayaran = new widget.Button();
        BtnKeluar = new widget.Button();
        panelisi4 = new widget.panelisi();
        label11 = new widget.Label();
        Tanggal1 = new widget.Tanggal();
        label12 = new widget.Label();
        Tanggal2 = new widget.Tanggal();
        label14 = new widget.Label();
        Status = new widget.ComboBox();
        label10 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        panelisi3 = new widget.panelisi();
        label17 = new widget.Label();
        NamaPelanggan = new widget.TextBox();
        btnPelanggan = new widget.Button();
        label7 = new widget.Label();
        NamaSampel = new widget.TextBox();
        btnSampel = new widget.Button();
        label15 = new widget.Label();
        NoPermintaan = new widget.TextBox();
        scrollPane3 = new widget.ScrollPane();
        tbValidasi = new widget.Table();
        PanelAccor = new widget.PanelBiasa();
        ChkAccor = new widget.CekBox();
        scrollPaneDetail = new widget.ScrollPane();
        tbDetailValidasi = new widget.Table();

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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Pembayaran Pengujian Sampel Laboratorium Kesehatan Lingkungan ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(816, 100));
        jPanel1.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 56));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label9.setText("Record :");
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(48, 23));
        panelisi1.add(label9);

        LTotal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LTotal.setText("0");
        LTotal.setName("LTotal"); // NOI18N
        LTotal.setPreferredSize(new java.awt.Dimension(85, 23));
        panelisi1.add(LTotal);

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
        panelisi1.add(BtnAll);

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

        BtnBayarTagihan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/gaji.png"))); // NOI18N
        BtnBayarTagihan.setMnemonic('H');
        BtnBayarTagihan.setText("Bayar Tagihan");
        BtnBayarTagihan.setToolTipText("Alt+H");
        BtnBayarTagihan.setName("BtnBayarTagihan"); // NOI18N
        BtnBayarTagihan.setPreferredSize(new java.awt.Dimension(145, 30));
        BtnBayarTagihan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBayarTagihanActionPerformed(evt);
            }
        });
        BtnBayarTagihan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnBayarTagihanKeyPressed(evt);
            }
        });
        panelisi1.add(BtnBayarTagihan);

        BtnRekapPembayaran.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/EDIT2.png"))); // NOI18N
        BtnRekapPembayaran.setMnemonic('R');
        BtnRekapPembayaran.setText("Rekap Pembayaran");
        BtnRekapPembayaran.setToolTipText("Alt+R");
        BtnRekapPembayaran.setName("BtnRekapPembayaran"); // NOI18N
        BtnRekapPembayaran.setPreferredSize(new java.awt.Dimension(165, 30));
        BtnRekapPembayaran.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRekapPembayaranActionPerformed(evt);
            }
        });
        BtnRekapPembayaran.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnRekapPembayaranKeyPressed(evt);
            }
        });
        panelisi1.add(BtnRekapPembayaran);

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

        label11.setText("Tgl.Validasi :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(71, 23));
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
        label14.setPreferredSize(new java.awt.Dimension(50, 23));
        panelisi4.add(label14);

        Status.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Semua", "Sudah Bayar", "Belum Bayar" }));
        Status.setName("Status"); // NOI18N
        Status.setPreferredSize(new java.awt.Dimension(113, 23));
        panelisi4.add(Status);

        label10.setText("Key Word :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(65, 23));
        panelisi4.add(label10);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(199, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelisi4.add(TCari);

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
        panelisi4.add(BtnCari);

        jPanel1.add(panelisi4, java.awt.BorderLayout.CENTER);

        internalFrame1.add(jPanel1, java.awt.BorderLayout.PAGE_END);

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(100, 44));
        panelisi3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label17.setText("Pelanggan :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(65, 23));
        panelisi3.add(label17);

        NamaPelanggan.setEditable(false);
        NamaPelanggan.setName("NamaPelanggan"); // NOI18N
        NamaPelanggan.setPreferredSize(new java.awt.Dimension(184, 23));
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
        NamaSampel.setPreferredSize(new java.awt.Dimension(144, 23));
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

        label15.setText("No.Permintaan :");
        label15.setName("label15"); // NOI18N
        label15.setPreferredSize(new java.awt.Dimension(91, 23));
        panelisi3.add(label15);

        NoPermintaan.setName("NoPermintaan"); // NOI18N
        NoPermintaan.setPreferredSize(new java.awt.Dimension(142, 23));
        NoPermintaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoPermintaanKeyPressed(evt);
            }
        });
        panelisi3.add(NoPermintaan);

        internalFrame1.add(panelisi3, java.awt.BorderLayout.PAGE_START);

        scrollPane3.setName("scrollPane3"); // NOI18N
        scrollPane3.setOpaque(true);

        tbValidasi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbValidasi.setToolTipText("Silahkan klik untuk memilih data yang mau dihapus");
        tbValidasi.setName("tbValidasi"); // NOI18N
        tbValidasi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbValidasiMouseClicked(evt);
            }
        });
        scrollPane3.setViewportView(tbValidasi);

        internalFrame1.add(scrollPane3, java.awt.BorderLayout.CENTER);

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

        scrollPaneDetail.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1), "Detail Pemeriksaan :", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        scrollPaneDetail.setName("scrollPaneDetail"); // NOI18N
        scrollPaneDetail.setOpaque(true);

        tbDetailValidasi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbDetailValidasi.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbDetailValidasi.setName("tbDetailValidasi"); // NOI18N
        scrollPaneDetail.setViewportView(tbDetailValidasi);

        PanelAccor.add(scrollPaneDetail, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelAccor, java.awt.BorderLayout.EAST);

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

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            tampil();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            Tanggal1.requestFocus();
        }
    }//GEN-LAST:event_TCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        tampil();
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
        Status.setSelectedIndex(0);
        tampil();
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnPrint, BtnKeluar);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabModeValidasi.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            TCari.requestFocus();
        }else if(tabModeValidasi.getRowCount()!=0){
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
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tgl.Validasi</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Validasi</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>NIP PJ Validasi</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama PJ Validasi</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Permintaan</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Pelanggan</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Pelanggan</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kode Sampel</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Sampel</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Titik Sampel</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Penerimaan Sampel</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Status Bayar</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Catatan</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>NIP PJ Verifikasi</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama PJ Verifikasi</b></td>").
                                        append("</tr>");
                            for (int i = 0; i < tabModeValidasi.getRowCount(); i++) {
                                htmlContent.append("<tr class='isi'>").
                                                append("<td valign='top'>").append(tbValidasi.getValueAt(i,0).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbValidasi.getValueAt(i,1).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbValidasi.getValueAt(i,2).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbValidasi.getValueAt(i,3).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbValidasi.getValueAt(i,4).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbValidasi.getValueAt(i,5).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbValidasi.getValueAt(i,6).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbValidasi.getValueAt(i,7).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbValidasi.getValueAt(i,8).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbValidasi.getValueAt(i,9).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbValidasi.getValueAt(i,10).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbValidasi.getValueAt(i,11).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbValidasi.getValueAt(i,12).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbValidasi.getValueAt(i,13).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbValidasi.getValueAt(i,14).toString()).append("</td>").
                                            append("</tr>");
                            }
                            LoadHTML.setText(
                                "<html>"+
                                  "<table width='100%' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
                                   htmlContent.toString()+
                                  "</table>"+
                                "</html>"
                            );

                            f = new File("DataValidasiPengujianSampel.html");            
                            bw = new BufferedWriter(new FileWriter(f));            
                            bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                                        "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                                        "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                            "<tr class='isi2'>"+
                                                "<td valign='top' align='center'>"+
                                                    "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                                    akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                                    akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                                    "<font size='2' face='Tahoma'>DATA VALIDASI PENGUJIAN SAMPEL<br><br></font>"+        
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
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tgl.Validasi</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Validasi</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>NIP PJ Validasi</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama PJ Validasi</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Permintaan</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Pelanggan</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Pelanggan</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kode Sampel</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Sampel</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Titik Sampel</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Penerimaan Sampel</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Status Bayar</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Catatan</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>NIP PJ Verifikasi</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama PJ Verifikasi</b></td>").
                                        append("</tr>");
                            for (int i = 0; i < tabModeValidasi.getRowCount(); i++) {
                                htmlContent.append("<tr class='isi'>").
                                                append("<td valign='top'>").append(tbValidasi.getValueAt(i,0).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbValidasi.getValueAt(i,1).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbValidasi.getValueAt(i,2).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbValidasi.getValueAt(i,3).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbValidasi.getValueAt(i,4).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbValidasi.getValueAt(i,5).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbValidasi.getValueAt(i,6).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbValidasi.getValueAt(i,7).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbValidasi.getValueAt(i,8).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbValidasi.getValueAt(i,9).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbValidasi.getValueAt(i,10).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbValidasi.getValueAt(i,11).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbValidasi.getValueAt(i,12).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbValidasi.getValueAt(i,13).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbValidasi.getValueAt(i,14).toString()).append("</td>").
                                            append("</tr>");
                            }
                            LoadHTML.setText(
                                "<html>"+
                                  "<table width='100%' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
                                   htmlContent.toString()+
                                  "</table>"+
                                "</html>"
                            );

                            f = new File("DataValidasiPengujianSampel.wps");            
                            bw = new BufferedWriter(new FileWriter(f));            
                            bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                                        "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                                        "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                            "<tr class='isi2'>"+
                                                "<td valign='top' align='center'>"+
                                                    "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                                    akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                                    akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                                    "<font size='2' face='Tahoma'>DATA VALIDASI PENGUJIAN SAMPEL<br><br></font>"+        
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
                                "\"Tgl.Validasi\";\"No.Validasi\";\"NIP PJ Validasi\";\"Nama PJ Validasi\";\"No.Permintaan\";\"No.Pelanggan\";\"Nama Pelanggan\";\"Kode Sampel\";\"Nama Sampel\";\"Titik Sampel\";\"Penerimaan Sampel\";\"Status Bayar\";\"Catatan\";\"NIP PJ Verifikasi\";\"Nama PJ Verifikasi\"\n"
                            ); 
                            for (int i = 0; i < tabModeValidasi.getRowCount(); i++) {
                                htmlContent.append("\"").append(tbValidasi.getValueAt(i,0).toString()).append("\";\"").append(tbValidasi.getValueAt(i,1).toString()).append("\";\"").append(tbValidasi.getValueAt(i,2).toString()).append("\";\"").append(tbValidasi.getValueAt(i,3).toString()).append("\";\"").append(tbValidasi.getValueAt(i,4).toString()).append("\";\"").append(tbValidasi.getValueAt(i,5).toString()).append("\";\"").append(tbValidasi.getValueAt(i,6).toString()).append("\";\"").append(tbValidasi.getValueAt(i,7).toString()).append("\";\"").append(tbValidasi.getValueAt(i,8).toString()).append("\";\"").append(tbValidasi.getValueAt(i,9).toString()).append("\";\"").append(tbValidasi.getValueAt(i,10).toString()).append("\";\"").append(tbValidasi.getValueAt(i,11).toString()).append("\";\"").append(tbValidasi.getValueAt(i,12).toString()).append("\";\"").append(tbValidasi.getValueAt(i,13).toString()).append("\";\"").append(tbValidasi.getValueAt(i,14).toString()).append("\"\n");
                            }
                            f = new File("DataValidasiPengujianSampel.csv");            
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
            Valid.pindah(evt,BtnAll,BtnAll);
        }
    }//GEN-LAST:event_BtnPrintKeyPressed

    private void BtnBayarTagihanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBayarTagihanActionPerformed
        if(tbValidasi.getSelectedRow()!= -1){
            if(Sequel.queryutf("delete from labkesling_permintaan_pengujian_sampel_tidak_dilayani where no_permintaan='"+tbValidasi.getValueAt(tbValidasi.getSelectedRow(),1).toString()+"'")==true){
                Sequel.queryu("update labkesling_permintaan_pengujian_sampel set status='Permintaan Baru' where no_permintaan='"+tbValidasi.getValueAt(tbValidasi.getSelectedRow(),1).toString()+"'");
                tabModeValidasi.removeRow(tbValidasi.getSelectedRow());
                Valid.tabelKosong(tabModeDetailValidasi);
                LTotal.setText(tabModeValidasi.getRowCount()+"");
            }
        }else{
            JOptionPane.showMessageDialog(null,"Silahkan pilih data tidak dapat dilayani...!!!");
        }
    }//GEN-LAST:event_BtnBayarTagihanActionPerformed

    private void BtnBayarTagihanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBayarTagihanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            //BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari, BtnPrint);
        }
    }//GEN-LAST:event_BtnBayarTagihanKeyPressed

    private void ChkAccorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkAccorActionPerformed
        if(tbValidasi.getSelectedRow()!= -1){
            if(ChkAccor.isSelected()==true){
                ChkAccor.setVisible(false);
                PanelAccor.setPreferredSize(new Dimension(670,HEIGHT));
                scrollPaneDetail.setVisible(true);  
                ChkAccor.setVisible(true);
                Valid.tabelKosong(tabModeDetailValidasi);
                try {
                    ps=koneksi.prepareStatement(
                        "select labkesling_detail_validasi_pengujian_sampel.kode_parameter,labkesling_parameter_pengujian.nama_parameter,labkesling_parameter_pengujian.satuan,labkesling_detail_validasi_pengujian_sampel.hasil_pengujian,"+
                        "labkesling_detail_validasi_pengujian_sampel.keterangan,labkesling_detail_validasi_pengujian_sampel.nilai_normal,labkesling_parameter_pengujian.metode_pengujian,labkesling_parameter_pengujian.kategori,"+
                        "labkesling_detail_validasi_pengujian_sampel.nip_analis,analis.nama as analis,labkesling_detail_validasi_pengujian_sampel.nip_pjpengujian,pjpengujian.nama as pjpengujian,labkesling_detail_validasi_pengujian_sampel.jasa_sarana,"+
                        "labkesling_detail_validasi_pengujian_sampel.paket_bhp,labkesling_detail_validasi_pengujian_sampel.jasa_pj_lab,labkesling_detail_validasi_pengujian_sampel.jasa_pj_pengujian,labkesling_detail_validasi_pengujian_sampel.jasa_verifikator,"+
                        "labkesling_detail_validasi_pengujian_sampel.jasa_petugas,labkesling_detail_validasi_pengujian_sampel.kso,labkesling_detail_validasi_pengujian_sampel.jasa_menejemen,labkesling_detail_validasi_pengujian_sampel.total "+
                        "from labkesling_detail_validasi_pengujian_sampel inner join labkesling_parameter_pengujian on labkesling_detail_validasi_pengujian_sampel.kode_parameter=labkesling_parameter_pengujian.kode_parameter "+
                        "inner join petugas as analis on labkesling_detail_validasi_pengujian_sampel.nip_analis=analis.nip "+
                        "inner join petugas as pjpengujian on labkesling_detail_validasi_pengujian_sampel.nip_pjpengujian=pjpengujian.nip "+
                        "where labkesling_detail_validasi_pengujian_sampel.no_validasi=? order by labkesling_detail_validasi_pengujian_sampel.kode_parameter"
                    );
                    try {
                        ps.setString(1,tbValidasi.getValueAt(tbValidasi.getSelectedRow(),1).toString());
                        rs=ps.executeQuery();
                        while(rs.next()){
                            tabModeDetailValidasi.addRow(new Object[]{
                                rs.getString("kode_parameter"),rs.getString("nama_parameter"),rs.getString("satuan"),rs.getString("hasil_pengujian"),rs.getString("keterangan"),rs.getString("nilai_normal"),
                                rs.getString("metode_pengujian"),rs.getString("kategori"),rs.getString("nip_analis"),rs.getString("analis"),rs.getString("nip_pjpengujian"),rs.getString("pjpengujian"),
                                rs.getDouble("jasa_sarana"),rs.getDouble("paket_bhp"),rs.getDouble("jasa_pj_lab"),rs.getDouble("jasa_pj_pengujian"),rs.getDouble("jasa_verifikator"),rs.getDouble("jasa_petugas"),
                                rs.getDouble("kso"),rs.getDouble("jasa_menejemen"),rs.getDouble("total")
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
            JOptionPane.showMessageDialog(null,"Silahkan pilih data pemeriksaan...!!!");
        }
    }//GEN-LAST:event_ChkAccorActionPerformed

    private void Tanggal1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tanggal1KeyPressed
        Valid.pindah(evt,NoPermintaan,Status);
    }//GEN-LAST:event_Tanggal1KeyPressed

    private void Tanggal2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tanggal2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Tanggal2KeyPressed

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

    private void NoPermintaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoPermintaanKeyPressed
        Valid.pindah(evt, BtnKeluar,Status);
    }//GEN-LAST:event_NoPermintaanKeyPressed

    private void BtnRekapPembayaranActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRekapPembayaranActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnRekapPembayaranActionPerformed

    private void BtnRekapPembayaranKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnRekapPembayaranKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnRekapPembayaranKeyPressed

    private void tbValidasiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbValidasiMouseClicked
        ChkAccorActionPerformed(null);
    }//GEN-LAST:event_tbValidasiMouseClicked

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            LabKeslingPembayaranPengujianSampel dialog = new LabKeslingPembayaranPengujianSampel(new javax.swing.JFrame(), true);
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
    private widget.Button BtnBayarTagihan;
    private widget.Button BtnCari;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnRekapPembayaran;
    private widget.CekBox ChkAccor;
    private widget.TextBox KodePelanggan;
    private widget.TextBox KodePetugas;
    private widget.TextBox KodeSampel;
    private widget.Label LTotal;
    private widget.editorpane LoadHTML;
    private widget.TextBox NamaPelanggan;
    private widget.TextBox NamaSampel;
    private widget.TextBox NoPermintaan;
    private widget.PanelBiasa PanelAccor;
    private widget.ComboBox Status;
    private widget.TextBox TCari;
    private widget.Tanggal Tanggal1;
    private widget.Tanggal Tanggal2;
    private widget.Button btnPelanggan;
    private widget.Button btnSampel;
    private widget.InternalFrame internalFrame1;
    private javax.swing.JPanel jPanel1;
    private widget.Label label10;
    private widget.Label label11;
    private widget.Label label12;
    private widget.Label label14;
    private widget.Label label15;
    private widget.Label label17;
    private widget.Label label7;
    private widget.Label label9;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi3;
    private widget.panelisi panelisi4;
    private widget.ScrollPane scrollPane3;
    private widget.ScrollPane scrollPaneDetail;
    private widget.Table tbDetailValidasi;
    private widget.Table tbValidasi;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabModeValidasi);
        try{  
            ps=koneksi.prepareStatement(
                "select labkesling_validasi_pengujian_sampel.tanggal,labkesling_validasi_pengujian_sampel.no_validasi,labkesling_validasi_pengujian_sampel.nip_pj,pjvalidasi.nama as pjvalidasi,labkesling_validasi_pengujian_sampel.no_permintaan,"+
                "labkesling_permintaan_pengujian_sampel.kode_pelanggan,labkesling_pelanggan.nama_pelanggan,labkesling_permintaan_pengujian_sampel.kode_sampel,labkesling_master_sampel.nama_sampel,labkesling_validasi_pengujian_sampel.status,"+
                "labkesling_validasi_pengujian_sampel.catatan,labkesling_validasi_pengujian_sampel.nip_verifikator,pjverifikasi.nama as pjverifikasi,labkesling_permintaan_pengujian_sampel.lokasi_sampling,labkesling_permintaan_pengujian_sampel.waktu_diterima "+
                "from labkesling_validasi_pengujian_sampel inner join petugas as pjvalidasi on pjvalidasi.nip=labkesling_validasi_pengujian_sampel.nip_pj "+
                "inner join labkesling_permintaan_pengujian_sampel on labkesling_permintaan_pengujian_sampel.no_permintaan=labkesling_validasi_pengujian_sampel.no_permintaan "+
                "inner join labkesling_pelanggan on labkesling_pelanggan.kode_pelanggan=labkesling_permintaan_pengujian_sampel.kode_pelanggan "+
                "inner join labkesling_master_sampel on labkesling_master_sampel.kode_sampel=labkesling_permintaan_pengujian_sampel.kode_sampel "+
                "inner join petugas as pjverifikasi on pjverifikasi.nip=labkesling_validasi_pengujian_sampel.nip_verifikator "+
                "where labkesling_validasi_pengujian_sampel.tanggal between ? and ? "+(NoPermintaan.getText().trim().equals("")?"":" and labkesling_validasi_pengujian_sampel.no_permintaan='"+NoPermintaan.getText()+"' ")+
                (Status.getSelectedItem().toString().equals("Semua")?"":" and labkesling_validasi_pengujian_sampel.status='"+Status.getSelectedItem().toString()+"' ")+
                (NamaPelanggan.getText().trim().equals("")?"":" and labkesling_permintaan_pengujian_sampel.kode_pelanggan='"+KodePelanggan.getText()+"' ")+
                (NamaSampel.getText().trim().equals("")?"":" and labkesling_permintaan_pengujian_sampel.kode_sampel='"+KodeSampel.getText()+"' ")+
                (TCari.getText().trim().equals("")?"":" and (labkesling_validasi_pengujian_sampel.no_validasi like ? or labkesling_validasi_pengujian_sampel.catatan like ? or "+
                "pjvalidasi.nama like ?) ")+"order by labkesling_validasi_pengujian_sampel.tanggal,labkesling_validasi_pengujian_sampel.no_validasi,labkesling_validasi_pengujian_sampel.no_permintaan"
            );
                
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
                    tabModeValidasi.addRow(new Object[]{
                        rs.getString("tanggal"),rs.getString("no_validasi"),rs.getString("nip_pj"),rs.getString("pjvalidasi"),rs.getString("no_permintaan"),rs.getString("kode_pelanggan"),rs.getString("nama_pelanggan"),
                        rs.getString("kode_sampel"),rs.getString("nama_sampel"),rs.getString("lokasi_sampling"),rs.getString("waktu_diterima"),rs.getString("status"),rs.getString("catatan"),rs.getString("nip_verifikator"),
                        rs.getString("pjverifikasi")
                    }); 
                }        
                LTotal.setText(tabModeValidasi.getRowCount()+"");
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
        BtnPrint.setEnabled(akses.getpembayaran_pengujian_sampel_lab_kesehatan_lingkungan());
        BtnBayarTagihan.setEnabled(akses.getpembayaran_pengujian_sampel_lab_kesehatan_lingkungan());
        BtnRekapPembayaran.setEnabled(akses.getpembayaran_pengujian_sampel_lab_kesehatan_lingkungan());
    }
}

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

public class LabKeslingCariPermintaanPengujianSampel extends javax.swing.JDialog {
    private final DefaultTableModel tabModePermintaan,tabModeDetailPermintaan,tabModeRekapPermintaan;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;

    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public LabKeslingCariPermintaanPengujianSampel(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        tabModePermintaan=new DefaultTableModel(null,new Object[]{
                "Waktu Diterima","No.Permintaan","No.Pelanggan","Nama Pelanggan","Alamat Pelanggan","Kegiatan Usaha","Personal Dihubungi","Kontak Pelanggan",
                "NIP","Sampel Diterima Oleh","Waktu Sampling","Lokasi Sampling","Deskripsi Sampel","Jenis Sampel","Jml.Sampel","Sampling Dilakukan Oleh",
                "Volume Sampel","Wadah Sampel","Kondisi Wadah Sampel","Kode Sampel","Nama Sampel","Baku Mutu","Status"
            }){
                @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbPermintaan.setModel(tabModePermintaan);

        tbPermintaan.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbPermintaan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 23; i++) {
            TableColumn column = tbPermintaan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(118);
            }else if(i==1){
                column.setPreferredWidth(120);
            }else if(i==2){
                column.setPreferredWidth(76);
            }else if(i==3){
                column.setPreferredWidth(150);
            }else if(i==4){
                column.setPreferredWidth(150);
            }else if(i==5){
                column.setPreferredWidth(150);
            }else if(i==6){
                column.setPreferredWidth(150);
            }else if(i==7){
                column.setPreferredWidth(100);
            }else if(i==8){
                column.setPreferredWidth(90);
            }else if(i==9){
                column.setPreferredWidth(150);
            }else if(i==10){
                column.setPreferredWidth(118);
            }else if(i==11){
                column.setPreferredWidth(160);
            }else if(i==12){
                column.setPreferredWidth(160);
            }else if(i==13){
                column.setPreferredWidth(120);
            }else if(i==14){
                column.setPreferredWidth(64);
            }else if(i==15){
                column.setPreferredWidth(129);
            }else if(i==16){
                column.setPreferredWidth(84);
            }else if(i==17){
                column.setPreferredWidth(110);
            }else if(i==18){
                column.setPreferredWidth(130);
            }else if(i==19){
                column.setPreferredWidth(70);
            }else if(i==20){
                column.setPreferredWidth(130);
            }else if(i==21){
                column.setPreferredWidth(200);
            }else if(i==22){
                column.setPreferredWidth(105);
            }
        }
        tbPermintaan.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeDetailPermintaan=new DefaultTableModel(null,new Object[]{
                "Kode","Nama Parameter","Metode Pengujian","Satuan","Kategori","Nilai Normal"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbDetailPermintaan.setModel(tabModeDetailPermintaan);
        tbDetailPermintaan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 6; i++) {
            TableColumn column = tbDetailPermintaan.getColumnModel().getColumn(i);
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
        tbDetailPermintaan.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeRekapPermintaan=new DefaultTableModel(null,new Object[]{
                "Waktu Diterima","No.Permintaan","No.Pelanggan","Nama Pelanggan","Alamat Pelanggan","Kegiatan Usaha","Personal Dihubungi","Kontak Pelanggan",
                "NIP","Sampel Diterima Oleh","Waktu Sampling","Lokasi Sampling","Deskripsi Sampel","Jenis Sampel","Jml.Sampel","Sampling Dilakukan Oleh",
                "Volume Sampel","Wadah Sampel","Kondisi Wadah Sampel","Kode Sampel","Nama Sampel","Baku Mutu","Status","Kode Param","Nama Parameter",
                "Metode Pengujian","Satuan","Kategori","Nilai Normal"
            }){
                @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbRekapPermintaan.setModel(tabModeRekapPermintaan);

        tbRekapPermintaan.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbRekapPermintaan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 29; i++) {
            TableColumn column = tbRekapPermintaan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(118);
            }else if(i==1){
                column.setPreferredWidth(120);
            }else if(i==2){
                column.setPreferredWidth(87);
            }else if(i==3){
                column.setPreferredWidth(150);
            }else if(i==4){
                column.setPreferredWidth(150);
            }else if(i==5){
                column.setPreferredWidth(150);
            }else if(i==6){
                column.setPreferredWidth(150);
            }else if(i==7){
                column.setPreferredWidth(100);
            }else if(i==8){
                column.setPreferredWidth(90);
            }else if(i==9){
                column.setPreferredWidth(150);
            }else if(i==10){
                column.setPreferredWidth(118);
            }else if(i==11){
                column.setPreferredWidth(160);
            }else if(i==12){
                column.setPreferredWidth(160);
            }else if(i==13){
                column.setPreferredWidth(120);
            }else if(i==14){
                column.setPreferredWidth(64);
            }else if(i==15){
                column.setPreferredWidth(129);
            }else if(i==16){
                column.setPreferredWidth(84);
            }else if(i==17){
                column.setPreferredWidth(110);
            }else if(i==18){
                column.setPreferredWidth(130);
            }else if(i==19){
                column.setPreferredWidth(70);
            }else if(i==20){
                column.setPreferredWidth(130);
            }else if(i==21){
                column.setPreferredWidth(200);
            }else if(i==22){
                column.setPreferredWidth(105);
            }else if(i==23){
                column.setPreferredWidth(70);
            }else if(i==24){
                column.setPreferredWidth(220);
            }else if(i==25){
                column.setPreferredWidth(140);
            }else if(i==26){
                column.setPreferredWidth(70);
            }else if(i==27){
                column.setPreferredWidth(70);
            }else if(i==28){
                column.setPreferredWidth(80);
            }
        }
        tbRekapPermintaan.setDefaultRenderer(Object.class, new WarnaTable());
        
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
        
        WindowInput.setSize(735,245);
        WindowInput.setLocationRelativeTo(null); 
        WindowInput2.setSize(735,245);
        WindowInput2.setLocationRelativeTo(null); 
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
        ppSuratPermintaan = new javax.swing.JMenuItem();
        ppDapatDilayani = new javax.swing.JMenuItem();
        ppTidakDapatDilayani = new javax.swing.JMenuItem();
        KodeSampel = new widget.TextBox();
        KodePelanggan = new widget.TextBox();
        KodePetugas = new widget.TextBox();
        LoadHTML = new widget.editorpane();
        WindowInput = new javax.swing.JDialog();
        internalFrame2 = new widget.InternalFrame();
        BtnTutup = new widget.Button();
        jLabel19 = new widget.Label();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        NoPermintaanVerifikasi = new widget.TextBox();
        jLabel42 = new widget.Label();
        scrollPane4 = new widget.ScrollPane();
        Keterangan = new widget.TextArea();
        label2 = new widget.Label();
        AsalPermintaan = new widget.TextBox();
        label3 = new widget.Label();
        label4 = new widget.Label();
        WindowInput2 = new javax.swing.JDialog();
        internalFrame3 = new widget.InternalFrame();
        BtnTutup2 = new widget.Button();
        jLabel20 = new widget.Label();
        BtnSimpan2 = new widget.Button();
        BtnBatal2 = new widget.Button();
        NoPermintaanVerifikasi2 = new widget.TextBox();
        jLabel43 = new widget.Label();
        scrollPane5 = new widget.ScrollPane();
        Keterangan2 = new widget.TextArea();
        label5 = new widget.Label();
        AsalPermintaan2 = new widget.TextBox();
        label6 = new widget.Label();
        label8 = new widget.Label();
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
        tbPermintaan = new widget.Table();
        PanelAccor = new widget.PanelBiasa();
        ChkAccor = new widget.CekBox();
        scrollPaneDetail = new widget.ScrollPane();
        tbDetailPermintaan = new widget.Table();
        scrollPane2 = new widget.ScrollPane();
        tbRekapPermintaan = new widget.Table();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        ppSuratPermintaan.setBackground(new java.awt.Color(255, 255, 254));
        ppSuratPermintaan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppSuratPermintaan.setForeground(new java.awt.Color(50, 50, 50));
        ppSuratPermintaan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppSuratPermintaan.setText("Surat Permintaan");
        ppSuratPermintaan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppSuratPermintaan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppSuratPermintaan.setName("ppSuratPermintaan"); // NOI18N
        ppSuratPermintaan.setPreferredSize(new java.awt.Dimension(200, 25));
        ppSuratPermintaan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppSuratPermintaanActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppSuratPermintaan);

        ppDapatDilayani.setBackground(new java.awt.Color(255, 255, 254));
        ppDapatDilayani.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppDapatDilayani.setForeground(new java.awt.Color(50, 50, 50));
        ppDapatDilayani.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppDapatDilayani.setText("Dapat Dilayani");
        ppDapatDilayani.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppDapatDilayani.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppDapatDilayani.setName("ppDapatDilayani"); // NOI18N
        ppDapatDilayani.setPreferredSize(new java.awt.Dimension(200, 25));
        ppDapatDilayani.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppDapatDilayaniActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppDapatDilayani);

        ppTidakDapatDilayani.setBackground(new java.awt.Color(255, 255, 254));
        ppTidakDapatDilayani.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppTidakDapatDilayani.setForeground(new java.awt.Color(50, 50, 50));
        ppTidakDapatDilayani.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppTidakDapatDilayani.setText("Tidak Dapat Dilayani");
        ppTidakDapatDilayani.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppTidakDapatDilayani.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppTidakDapatDilayani.setName("ppTidakDapatDilayani"); // NOI18N
        ppTidakDapatDilayani.setPreferredSize(new java.awt.Dimension(200, 25));
        ppTidakDapatDilayani.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppTidakDapatDilayaniActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppTidakDapatDilayani);

        KodeSampel.setName("KodeSampel"); // NOI18N
        KodeSampel.setPreferredSize(new java.awt.Dimension(207, 23));

        KodePelanggan.setName("KodePelanggan"); // NOI18N
        KodePelanggan.setPreferredSize(new java.awt.Dimension(80, 23));

        KodePetugas.setName("KodePetugas"); // NOI18N
        KodePetugas.setPreferredSize(new java.awt.Dimension(80, 23));

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N

        WindowInput.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowInput.setName("WindowInput"); // NOI18N
        WindowInput.setUndecorated(true);
        WindowInput.setResizable(false);

        internalFrame2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(230, 235, 225)), "::[ Verifikasi Tidak Dapat Dilayani ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 40))); // NOI18N
        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setLayout(null);

        BtnTutup.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/cross.png"))); // NOI18N
        BtnTutup.setMnemonic('U');
        BtnTutup.setText("Tutup");
        BtnTutup.setToolTipText("Alt+U");
        BtnTutup.setName("BtnTutup"); // NOI18N
        BtnTutup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTutupActionPerformed(evt);
            }
        });
        BtnTutup.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnTutupKeyPressed(evt);
            }
        });
        internalFrame2.add(BtnTutup);
        BtnTutup.setBounds(620, 195, 100, 30);

        jLabel19.setText("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        jLabel19.setName("jLabel19"); // NOI18N
        internalFrame2.add(jLabel19);
        jLabel19.setBounds(-10, 175, 850, 14);

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan.setMnemonic('S');
        BtnSimpan.setText("Simpan");
        BtnSimpan.setToolTipText("Alt+S");
        BtnSimpan.setName("BtnSimpan"); // NOI18N
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
        internalFrame2.add(BtnSimpan);
        BtnSimpan.setBounds(14, 195, 100, 30);

        BtnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Cancel-2-16x16.png"))); // NOI18N
        BtnBatal.setMnemonic('B');
        BtnBatal.setText("Batal");
        BtnBatal.setToolTipText("Alt+B");
        BtnBatal.setName("BtnBatal"); // NOI18N
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
        internalFrame2.add(BtnBatal);
        BtnBatal.setBounds(117, 195, 100, 30);

        NoPermintaanVerifikasi.setEditable(false);
        NoPermintaanVerifikasi.setName("NoPermintaanVerifikasi"); // NOI18N
        internalFrame2.add(NoPermintaanVerifikasi);
        NoPermintaanVerifikasi.setBounds(99, 20, 150, 23);

        jLabel42.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel42.setText("Unsur Kaji Ulang/Abnornalitas Sampel/Keterangan Lainnya :");
        jLabel42.setName("jLabel42"); // NOI18N
        internalFrame2.add(jLabel42);
        jLabel42.setBounds(16, 50, 310, 23);

        scrollPane4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane4.setName("scrollPane4"); // NOI18N

        Keterangan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Keterangan.setColumns(20);
        Keterangan.setRows(9);
        Keterangan.setTabSize(20);
        Keterangan.setName("Keterangan"); // NOI18N
        Keterangan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKeyPressed(evt);
            }
        });
        scrollPane4.setViewportView(Keterangan);

        internalFrame2.add(scrollPane4);
        scrollPane4.setBounds(99, 70, 621, 103);

        label2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label2.setText("No.Permintaan");
        label2.setName("label2"); // NOI18N
        internalFrame2.add(label2);
        label2.setBounds(16, 20, 95, 23);

        AsalPermintaan.setEditable(false);
        AsalPermintaan.setName("AsalPermintaan"); // NOI18N
        internalFrame2.add(AsalPermintaan);
        AsalPermintaan.setBounds(375, 20, 345, 23);

        label3.setText("Asal Permintaan :");
        label3.setName("label3"); // NOI18N
        internalFrame2.add(label3);
        label3.setBounds(261, 20, 110, 23);

        label4.setText(":");
        label4.setName("label4"); // NOI18N
        internalFrame2.add(label4);
        label4.setBounds(0, 20, 95, 23);

        WindowInput.getContentPane().add(internalFrame2, java.awt.BorderLayout.CENTER);

        WindowInput2.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowInput2.setName("WindowInput2"); // NOI18N
        WindowInput2.setUndecorated(true);
        WindowInput2.setResizable(false);

        internalFrame3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(230, 235, 225)), "::[ Verifikasi Dapat Dilayani ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 40))); // NOI18N
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setLayout(null);

        BtnTutup2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/cross.png"))); // NOI18N
        BtnTutup2.setMnemonic('U');
        BtnTutup2.setText("Tutup");
        BtnTutup2.setToolTipText("Alt+U");
        BtnTutup2.setName("BtnTutup2"); // NOI18N
        BtnTutup2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTutup2ActionPerformed(evt);
            }
        });
        BtnTutup2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnTutup2KeyPressed(evt);
            }
        });
        internalFrame3.add(BtnTutup2);
        BtnTutup2.setBounds(620, 195, 100, 30);

        jLabel20.setText("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        jLabel20.setName("jLabel20"); // NOI18N
        internalFrame3.add(jLabel20);
        jLabel20.setBounds(-10, 175, 850, 14);

        BtnSimpan2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan2.setMnemonic('S');
        BtnSimpan2.setText("Simpan");
        BtnSimpan2.setToolTipText("Alt+S");
        BtnSimpan2.setName("BtnSimpan2"); // NOI18N
        BtnSimpan2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpan2ActionPerformed(evt);
            }
        });
        BtnSimpan2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSimpan2KeyPressed(evt);
            }
        });
        internalFrame3.add(BtnSimpan2);
        BtnSimpan2.setBounds(14, 195, 100, 30);

        BtnBatal2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Cancel-2-16x16.png"))); // NOI18N
        BtnBatal2.setMnemonic('B');
        BtnBatal2.setText("Batal");
        BtnBatal2.setToolTipText("Alt+B");
        BtnBatal2.setName("BtnBatal2"); // NOI18N
        BtnBatal2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBatal2ActionPerformed(evt);
            }
        });
        BtnBatal2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnBatal2KeyPressed(evt);
            }
        });
        internalFrame3.add(BtnBatal2);
        BtnBatal2.setBounds(117, 195, 100, 30);

        NoPermintaanVerifikasi2.setEditable(false);
        NoPermintaanVerifikasi2.setName("NoPermintaanVerifikasi2"); // NOI18N
        internalFrame3.add(NoPermintaanVerifikasi2);
        NoPermintaanVerifikasi2.setBounds(99, 20, 150, 23);

        jLabel43.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel43.setText("Kebutuhan Sub Kontrak Pengujian/Keterangan Lainnya :");
        jLabel43.setName("jLabel43"); // NOI18N
        internalFrame3.add(jLabel43);
        jLabel43.setBounds(16, 50, 310, 23);

        scrollPane5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane5.setName("scrollPane5"); // NOI18N

        Keterangan2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Keterangan2.setColumns(20);
        Keterangan2.setRows(9);
        Keterangan2.setTabSize(20);
        Keterangan2.setName("Keterangan2"); // NOI18N
        Keterangan2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Keterangan2KeyPressed(evt);
            }
        });
        scrollPane5.setViewportView(Keterangan2);

        internalFrame3.add(scrollPane5);
        scrollPane5.setBounds(99, 70, 621, 103);

        label5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label5.setText("No.Permintaan");
        label5.setName("label5"); // NOI18N
        internalFrame3.add(label5);
        label5.setBounds(16, 20, 95, 23);

        AsalPermintaan2.setEditable(false);
        AsalPermintaan2.setName("AsalPermintaan2"); // NOI18N
        internalFrame3.add(AsalPermintaan2);
        AsalPermintaan2.setBounds(375, 20, 345, 23);

        label6.setText("Asal Permintaan :");
        label6.setName("label6"); // NOI18N
        internalFrame3.add(label6);
        label6.setBounds(261, 20, 110, 23);

        label8.setText(":");
        label8.setName("label8"); // NOI18N
        internalFrame3.add(label8);
        label8.setBounds(0, 20, 95, 23);

        WindowInput2.getContentPane().add(internalFrame3, java.awt.BorderLayout.CENTER);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Permintaan Pengujian Sampel Laboratorium Kesehatan Lingkungan ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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

        label11.setText("Waktu Diterima :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(90, 23));
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
        label14.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi4.add(label14);

        Status.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Semua", "Permintaan Baru", "Tidak Dapat Dilayani", "Dapat Dilayani" }));
        Status.setName("Status"); // NOI18N
        Status.setPreferredSize(new java.awt.Dimension(150, 23));
        panelisi4.add(Status);

        label15.setText("Nomor :");
        label15.setName("label15"); // NOI18N
        label15.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi4.add(label15);

        NoPermintaan.setName("NoPermintaan"); // NOI18N
        NoPermintaan.setPreferredSize(new java.awt.Dimension(170, 23));
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

        label13.setText("Diterima Oleh :");
        label13.setName("label13"); // NOI18N
        label13.setPreferredSize(new java.awt.Dimension(84, 23));
        panelisi3.add(label13);

        NamaPetugas.setEditable(false);
        NamaPetugas.setName("NamaPetugas"); // NOI18N
        NamaPetugas.setPreferredSize(new java.awt.Dimension(150, 23));
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
        NamaPelanggan.setPreferredSize(new java.awt.Dimension(150, 23));
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

        tbPermintaan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbPermintaan.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbPermintaan.setComponentPopupMenu(jPopupMenu1);
        tbPermintaan.setName("tbPermintaan"); // NOI18N
        tbPermintaan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPermintaanMouseClicked(evt);
            }
        });
        scrollPane1.setViewportView(tbPermintaan);

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

        scrollPaneDetail.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1), "Detail Permintaan :", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        scrollPaneDetail.setComponentPopupMenu(jPopupMenu1);
        scrollPaneDetail.setName("scrollPaneDetail"); // NOI18N
        scrollPaneDetail.setOpaque(true);

        tbDetailPermintaan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbDetailPermintaan.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbDetailPermintaan.setComponentPopupMenu(jPopupMenu1);
        tbDetailPermintaan.setName("tbDetailPermintaan"); // NOI18N
        scrollPaneDetail.setViewportView(tbDetailPermintaan);

        PanelAccor.add(scrollPaneDetail, java.awt.BorderLayout.CENTER);

        jPanel2.add(PanelAccor, java.awt.BorderLayout.EAST);

        TabData.addTab("Data Permintaan", jPanel2);

        scrollPane2.setComponentPopupMenu(jPopupMenu1);
        scrollPane2.setName("scrollPane2"); // NOI18N
        scrollPane2.setOpaque(true);

        tbRekapPermintaan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbRekapPermintaan.setToolTipText("Silahkan klik untuk memilih data yang mau dihapus");
        tbRekapPermintaan.setName("tbRekapPermintaan"); // NOI18N
        scrollPane2.setViewportView(tbRekapPermintaan);

        TabData.addTab("Rekap Permintaan", scrollPane2);

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
            tbPermintaan.requestFocus();
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
            if(tabModePermintaan.getRowCount()==0){
                JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                TCari.requestFocus();
            }else if(tabModePermintaan.getRowCount()!=0){
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
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Waktu Diterima</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Permintaan</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Pelanggan</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Pelanggan</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Alamat Pelanggan</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kegiatan Usaha</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Personal Dihubungi</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kontak Pelanggan</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>NIP</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Sampel Diterima Oleh</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Waktu Sampling</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Lokasi Sampling</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Deskripsi Sampel</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Jenis Sampel</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Jml.Sampel</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Sampling Dilakukan Oleh</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Volume Sampel</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Wadah Sampel</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kondisi Wadah Sampel</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kode Sampel</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Sampel</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Baku Mutu</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Status</b></td>").
                                            append("</tr>");
                                for (int i = 0; i < tabModePermintaan.getRowCount(); i++) {
                                    htmlContent.append("<tr class='isi'>").
                                                    append("<td valign='top'>").append(tbPermintaan.getValueAt(i,0).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbPermintaan.getValueAt(i,1).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbPermintaan.getValueAt(i,2).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbPermintaan.getValueAt(i,3).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbPermintaan.getValueAt(i,4).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbPermintaan.getValueAt(i,5).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbPermintaan.getValueAt(i,6).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbPermintaan.getValueAt(i,7).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbPermintaan.getValueAt(i,8).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbPermintaan.getValueAt(i,9).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbPermintaan.getValueAt(i,10).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbPermintaan.getValueAt(i,11).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbPermintaan.getValueAt(i,12).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbPermintaan.getValueAt(i,13).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbPermintaan.getValueAt(i,14).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbPermintaan.getValueAt(i,15).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbPermintaan.getValueAt(i,16).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbPermintaan.getValueAt(i,17).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbPermintaan.getValueAt(i,18).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbPermintaan.getValueAt(i,19).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbPermintaan.getValueAt(i,20).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbPermintaan.getValueAt(i,21).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbPermintaan.getValueAt(i,22).toString()).append("</td>").
                                                append("</tr>");
                                }
                                LoadHTML.setText(
                                    "<html>"+
                                      "<table width='2000px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
                                       htmlContent.toString()+
                                      "</table>"+
                                    "</html>"
                                );

                                f = new File("DataPermintaanPengujianSampel.html");            
                                bw = new BufferedWriter(new FileWriter(f));            
                                bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                                            "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                                            "<table width='2000px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                                "<tr class='isi2'>"+
                                                    "<td valign='top' align='center'>"+
                                                        "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                                        akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                                        akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                                        "<font size='2' face='Tahoma'>DATA PERMINTAAN PENGUJIAN SAMPEL<br><br></font>"+        
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
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Waktu Diterima</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Permintaan</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Pelanggan</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Pelanggan</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Alamat Pelanggan</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kegiatan Usaha</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Personal Dihubungi</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kontak Pelanggan</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>NIP</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Sampel Diterima Oleh</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Waktu Sampling</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Lokasi Sampling</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Deskripsi Sampel</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Jenis Sampel</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Jml.Sampel</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Sampling Dilakukan Oleh</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Volume Sampel</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Wadah Sampel</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kondisi Wadah Sampel</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kode Sampel</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Sampel</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Baku Mutu</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Status</b></td>").
                                            append("</tr>");
                                for (int i = 0; i < tabModePermintaan.getRowCount(); i++) {
                                    htmlContent.append("<tr class='isi'>").
                                                    append("<td valign='top'>").append(tbPermintaan.getValueAt(i,0).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbPermintaan.getValueAt(i,1).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbPermintaan.getValueAt(i,2).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbPermintaan.getValueAt(i,3).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbPermintaan.getValueAt(i,4).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbPermintaan.getValueAt(i,5).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbPermintaan.getValueAt(i,6).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbPermintaan.getValueAt(i,7).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbPermintaan.getValueAt(i,8).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbPermintaan.getValueAt(i,9).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbPermintaan.getValueAt(i,10).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbPermintaan.getValueAt(i,11).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbPermintaan.getValueAt(i,12).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbPermintaan.getValueAt(i,13).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbPermintaan.getValueAt(i,14).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbPermintaan.getValueAt(i,15).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbPermintaan.getValueAt(i,16).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbPermintaan.getValueAt(i,17).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbPermintaan.getValueAt(i,18).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbPermintaan.getValueAt(i,19).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbPermintaan.getValueAt(i,20).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbPermintaan.getValueAt(i,21).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbPermintaan.getValueAt(i,22).toString()).append("</td>").
                                                append("</tr>");
                                }
                                LoadHTML.setText(
                                    "<html>"+
                                      "<table width='2000px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
                                       htmlContent.toString()+
                                      "</table>"+
                                    "</html>"
                                );

                                f = new File("DataPermintaanPengujianSampel.wps");            
                                bw = new BufferedWriter(new FileWriter(f));            
                                bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                                            "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                                            "<table width='2000px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                                "<tr class='isi2'>"+
                                                    "<td valign='top' align='center'>"+
                                                        "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                                        akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                                        akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                                        "<font size='2' face='Tahoma'>DATA PERMINTAAN PENGUJIAN SAMPEL<br><br></font>"+        
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
                                    "\"Waktu Diterima\";\"No.Permintaan\";\"No.Pelanggan\";\"Nama Pelanggan\";\"Alamat Pelanggan\";\"Kegiatan Usaha\";\"Personal Dihubungi\";\"Kontak Pelanggan\";\"NIP\";\"Sampel Diterima Oleh\";\"Waktu Sampling\";\"Lokasi Sampling\";\"Deskripsi Sampel\";\"Jenis Sampel\";\"Jml.Sampel\";\"Sampling Dilakukan Oleh\";\"Volume Sampel\";\"Wadah Sampel\";\"Kondisi Wadah Sampel\";\"Kode Sampel\";\"Nama Sampel\";\"Baku Mutu\";\"Status\"\n"
                                ); 
                                for (int i = 0; i < tabModePermintaan.getRowCount(); i++) {
                                    htmlContent.append("\"").append(tbPermintaan.getValueAt(i,0).toString()).append("\";\"").append(tbPermintaan.getValueAt(i,1).toString()).append("\";\"").append(tbPermintaan.getValueAt(i,2).toString()).append("\";\"").append(tbPermintaan.getValueAt(i,3).toString()).append("\";\"").append(tbPermintaan.getValueAt(i,4).toString()).append("\";\"").append(tbPermintaan.getValueAt(i,5).toString()).append("\";\"").append(tbPermintaan.getValueAt(i,6).toString()).append("\";\"").append(tbPermintaan.getValueAt(i,7).toString()).append("\";\"").append(tbPermintaan.getValueAt(i,8).toString()).append("\";\"").append(tbPermintaan.getValueAt(i,9).toString()).append("\";\"").append(tbPermintaan.getValueAt(i,10).toString()).append("\";\"").append(tbPermintaan.getValueAt(i,11).toString()).append("\";\"").append(tbPermintaan.getValueAt(i,12).toString()).append("\";\"").append(tbPermintaan.getValueAt(i,13).toString()).append("\";\"").append(tbPermintaan.getValueAt(i,14).toString()).append("\";\"").append(tbPermintaan.getValueAt(i,15).toString()).append("\";\"").append(tbPermintaan.getValueAt(i,16).toString()).append("\";\"").append(tbPermintaan.getValueAt(i,17).toString()).append("\";\"").append(tbPermintaan.getValueAt(i,18).toString()).append("\";\"").append(tbPermintaan.getValueAt(i,19).toString()).append("\";\"").append(tbPermintaan.getValueAt(i,20).toString()).append("\";\"").append(tbPermintaan.getValueAt(i,21).toString()).append("\";\"").append(tbPermintaan.getValueAt(i,22).toString()).append("\"\n");
                                }
                                f = new File("DataPermintaanPengujianSampel.csv");            
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
            if(tabModeRekapPermintaan.getRowCount()==0){
                JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                TCari.requestFocus();
            }else if(tabModeRekapPermintaan.getRowCount()!=0){
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
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Waktu Diterima</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Permintaan</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Pelanggan</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Pelanggan</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Alamat Pelanggan</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kegiatan Usaha</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Personal Dihubungi</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kontak Pelanggan</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>NIP</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Sampel Diterima Oleh</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Waktu Sampling</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Lokasi Sampling</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Deskripsi Sampel</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Jenis Sampel</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Jml.Sampel</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Sampling Dilakukan Oleh</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Volume Sampel</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Wadah Sampel</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kondisi Wadah Sampel</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kode Sampel</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Sampel</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Baku Mutu</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Status</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kode Param</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Parameter</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Metode Pengujian</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Satuan</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kategori</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nilai Normal</b></td>").
                                            append("</tr>");
                                for (int i = 0; i < tabModeRekapPermintaan.getRowCount(); i++) {
                                    htmlContent.append("<tr class='isi'>").
                                                    append("<td valign='top'>").append(tbRekapPermintaan.getValueAt(i,0).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapPermintaan.getValueAt(i,1).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapPermintaan.getValueAt(i,2).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapPermintaan.getValueAt(i,3).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapPermintaan.getValueAt(i,4).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapPermintaan.getValueAt(i,5).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapPermintaan.getValueAt(i,6).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapPermintaan.getValueAt(i,7).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapPermintaan.getValueAt(i,8).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapPermintaan.getValueAt(i,9).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapPermintaan.getValueAt(i,10).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapPermintaan.getValueAt(i,11).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapPermintaan.getValueAt(i,12).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapPermintaan.getValueAt(i,13).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapPermintaan.getValueAt(i,14).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapPermintaan.getValueAt(i,15).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapPermintaan.getValueAt(i,16).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapPermintaan.getValueAt(i,17).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapPermintaan.getValueAt(i,18).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapPermintaan.getValueAt(i,19).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapPermintaan.getValueAt(i,20).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapPermintaan.getValueAt(i,21).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapPermintaan.getValueAt(i,22).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapPermintaan.getValueAt(i,23).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapPermintaan.getValueAt(i,24).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapPermintaan.getValueAt(i,25).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapPermintaan.getValueAt(i,26).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapPermintaan.getValueAt(i,27).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapPermintaan.getValueAt(i,28).toString()).append("</td>").
                                                append("</tr>");
                                }
                                LoadHTML.setText(
                                    "<html>"+
                                      "<table width='2500px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
                                       htmlContent.toString()+
                                      "</table>"+
                                    "</html>"
                                );

                                f = new File("DataRekapPermintaanPengujianSampel.html");            
                                bw = new BufferedWriter(new FileWriter(f));            
                                bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                                            "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                                            "<table width='2500px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                                "<tr class='isi2'>"+
                                                    "<td valign='top' align='center'>"+
                                                        "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                                        akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                                        akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                                        "<font size='2' face='Tahoma'>DATA REKAP PERMINTAAN PENGUJIAN SAMPEL<br><br></font>"+        
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
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Waktu Diterima</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Permintaan</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Pelanggan</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Pelanggan</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Alamat Pelanggan</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kegiatan Usaha</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Personal Dihubungi</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kontak Pelanggan</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>NIP</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Sampel Diterima Oleh</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Waktu Sampling</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Lokasi Sampling</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Deskripsi Sampel</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Jenis Sampel</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Jml.Sampel</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Sampling Dilakukan Oleh</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Volume Sampel</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Wadah Sampel</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kondisi Wadah Sampel</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kode Sampel</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Sampel</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Baku Mutu</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Status</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kode Param</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Parameter</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Metode Pengujian</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Satuan</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kategori</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nilai Normal</b></td>").
                                            append("</tr>");
                                for (int i = 0; i < tabModeRekapPermintaan.getRowCount(); i++) {
                                    htmlContent.append("<tr class='isi'>").
                                                    append("<td valign='top'>").append(tbRekapPermintaan.getValueAt(i,0).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapPermintaan.getValueAt(i,1).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapPermintaan.getValueAt(i,2).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapPermintaan.getValueAt(i,3).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapPermintaan.getValueAt(i,4).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapPermintaan.getValueAt(i,5).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapPermintaan.getValueAt(i,6).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapPermintaan.getValueAt(i,7).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapPermintaan.getValueAt(i,8).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapPermintaan.getValueAt(i,9).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapPermintaan.getValueAt(i,10).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapPermintaan.getValueAt(i,11).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapPermintaan.getValueAt(i,12).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapPermintaan.getValueAt(i,13).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapPermintaan.getValueAt(i,14).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapPermintaan.getValueAt(i,15).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapPermintaan.getValueAt(i,16).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapPermintaan.getValueAt(i,17).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapPermintaan.getValueAt(i,18).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapPermintaan.getValueAt(i,19).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapPermintaan.getValueAt(i,20).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapPermintaan.getValueAt(i,21).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapPermintaan.getValueAt(i,22).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapPermintaan.getValueAt(i,23).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapPermintaan.getValueAt(i,24).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapPermintaan.getValueAt(i,25).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapPermintaan.getValueAt(i,26).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapPermintaan.getValueAt(i,27).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapPermintaan.getValueAt(i,28).toString()).append("</td>").
                                                append("</tr>");
                                }
                                LoadHTML.setText(
                                    "<html>"+
                                      "<table width='2500px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
                                       htmlContent.toString()+
                                      "</table>"+
                                    "</html>"
                                );

                                f = new File("DataRekapPermintaanPengujianSampel.wps");            
                                bw = new BufferedWriter(new FileWriter(f));            
                                bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                                            "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                                            "<table width='2500px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                                "<tr class='isi2'>"+
                                                    "<td valign='top' align='center'>"+
                                                        "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                                        akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                                        akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                                        "<font size='2' face='Tahoma'>DATA REKAP PERMINTAAN PENGUJIAN SAMPEL<br><br></font>"+        
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
                                    "\"Waktu Diterima\";\"No.Permintaan\";\"No.Pelanggan\";\"Nama Pelanggan\";\"Alamat Pelanggan\";\"Kegiatan Usaha\";\"Personal Dihubungi\";\"Kontak Pelanggan\";\"NIP\";\"Sampel Diterima Oleh\";\"Waktu Sampling\";\"Lokasi Sampling\";\"Deskripsi Sampel\";\"Jenis Sampel\";\"Jml.Sampel\";\"Sampling Dilakukan Oleh\";\"Volume Sampel\";\"Wadah Sampel\";\"Kondisi Wadah Sampel\";\"Kode Sampel\";\"Nama Sampel\";\"Baku Mutu\";\"Status\";\"Kode Param\";\"Nama Parameter\";\"Metode Pengujian\";\"Satuan\";\"Kategori\";\"Nilai Normal\"\n"
                                ); 
                                for (int i = 0; i < tabModeRekapPermintaan.getRowCount(); i++) {
                                    htmlContent.append("\"").append(tbRekapPermintaan.getValueAt(i,0).toString()).append("\";\"").append(tbRekapPermintaan.getValueAt(i,1).toString()).append("\";\"").append(tbRekapPermintaan.getValueAt(i,2).toString()).append("\";\"").append(tbRekapPermintaan.getValueAt(i,3).toString()).append("\";\"").append(tbRekapPermintaan.getValueAt(i,4).toString()).append("\";\"").append(tbRekapPermintaan.getValueAt(i,5).toString()).append("\";\"").append(tbRekapPermintaan.getValueAt(i,6).toString()).append("\";\"").append(tbRekapPermintaan.getValueAt(i,7).toString()).append("\";\"").append(tbRekapPermintaan.getValueAt(i,8).toString()).append("\";\"").append(tbRekapPermintaan.getValueAt(i,9).toString()).append("\";\"").append(tbRekapPermintaan.getValueAt(i,10).toString()).append("\";\"").append(tbRekapPermintaan.getValueAt(i,11).toString()).append("\";\"").append(tbRekapPermintaan.getValueAt(i,12).toString()).append("\";\"").append(tbRekapPermintaan.getValueAt(i,13).toString()).append("\";\"").append(tbRekapPermintaan.getValueAt(i,14).toString()).append("\";\"").append(tbRekapPermintaan.getValueAt(i,15).toString()).append("\";\"").append(tbRekapPermintaan.getValueAt(i,16).toString()).append("\";\"").append(tbRekapPermintaan.getValueAt(i,17).toString()).append("\";\"").append(tbRekapPermintaan.getValueAt(i,18).toString()).append("\";\"").append(tbRekapPermintaan.getValueAt(i,19).toString()).append("\";\"").append(tbRekapPermintaan.getValueAt(i,20).toString()).append("\";\"").append(tbRekapPermintaan.getValueAt(i,21).toString()).append("\";\"").append(tbRekapPermintaan.getValueAt(i,22).toString()).append("\";\"").append(tbRekapPermintaan.getValueAt(i,23).toString()).append("\";\"").append(tbRekapPermintaan.getValueAt(i,24).toString()).append("\";\"").append(tbRekapPermintaan.getValueAt(i,25).toString()).append("\";\"").append(tbRekapPermintaan.getValueAt(i,26).toString()).append("\";\"").append(tbRekapPermintaan.getValueAt(i,27).toString()).append("\";\"").append(tbRekapPermintaan.getValueAt(i,28).toString()).append("\"\n");
                                }
                                f = new File("DataRekapPermintaanPengujianSampel.csv");            
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

    private void ppDapatDilayaniActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppDapatDilayaniActionPerformed
        if(tbPermintaan.getSelectedRow()!= -1){
            if(tbPermintaan.getValueAt(tbPermintaan.getSelectedRow(),22).toString().equals("Permintaan Baru")){
                NoPermintaanVerifikasi2.setText(tbPermintaan.getValueAt(tbPermintaan.getSelectedRow(),1).toString());
                AsalPermintaan2.setText(tbPermintaan.getValueAt(tbPermintaan.getSelectedRow(),2).toString()+" "+tbPermintaan.getValueAt(tbPermintaan.getSelectedRow(),3).toString());
                WindowInput2.setVisible(true);
                Keterangan2.requestFocus();
            }else {
                JOptionPane.showMessageDialog(null,"Status permintaan sudah "+tbPermintaan.getValueAt(tbPermintaan.getSelectedRow(),22).toString()+"...!");
            }
        }else{
            JOptionPane.showMessageDialog(null,"Silahkan pilih data permintaan...!!!");
        }
    }//GEN-LAST:event_ppDapatDilayaniActionPerformed

    private void TabDataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabDataMouseClicked
        if(TabData.getSelectedIndex()==0){
            tampil();
        }else if(TabData.getSelectedIndex()==1){
            tampil2();
        }
    }//GEN-LAST:event_TabDataMouseClicked

    private void ChkAccorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkAccorActionPerformed
        if(tbPermintaan.getSelectedRow()!= -1){
            if(ChkAccor.isSelected()==true){
                ChkAccor.setVisible(false);
                PanelAccor.setPreferredSize(new Dimension(670,HEIGHT));
                scrollPaneDetail.setVisible(true);  
                ChkAccor.setVisible(true);
                Valid.tabelKosong(tabModeDetailPermintaan);
                try {
                    ps=koneksi.prepareStatement(
                        "select laborat_kesling_detail_permintaan_pengujian_sampel.kode_parameter,laborat_kesling_parameter_pengujian.nama_parameter,laborat_kesling_parameter_pengujian.metode_pengujian,laborat_kesling_parameter_pengujian.satuan,"+
                        "laborat_kesling_parameter_pengujian.kategori,laborat_kesling_nilai_normal_baku_mutu.nilai_normal from laborat_kesling_detail_permintaan_pengujian_sampel inner join laborat_kesling_parameter_pengujian "+
                        "on laborat_kesling_detail_permintaan_pengujian_sampel.kode_parameter=laborat_kesling_parameter_pengujian.kode_parameter inner join laborat_kesling_nilai_normal_baku_mutu "+
                        "on laborat_kesling_nilai_normal_baku_mutu.kode_parameter=laborat_kesling_parameter_pengujian.kode_parameter where laborat_kesling_detail_permintaan_pengujian_sampel.no_permintaan=? "+
                        "and laborat_kesling_nilai_normal_baku_mutu.kode_sampel=? order by laborat_kesling_detail_permintaan_pengujian_sampel.kode_parameter"
                    );
                    try {
                        ps.setString(1,tbPermintaan.getValueAt(tbPermintaan.getSelectedRow(),1).toString());
                        ps.setString(2,tbPermintaan.getValueAt(tbPermintaan.getSelectedRow(),19).toString());
                        rs=ps.executeQuery();
                        while(rs.next()){
                            tabModeDetailPermintaan.addRow(new Object[]{
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
            JOptionPane.showMessageDialog(null,"Silahkan pilih data permintaan...!!!");
        }
    }//GEN-LAST:event_ChkAccorActionPerformed

    private void tbPermintaanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPermintaanMouseClicked
        ChkAccorActionPerformed(null);
    }//GEN-LAST:event_tbPermintaanMouseClicked

    private void ppSuratPermintaanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppSuratPermintaanActionPerformed
        if(tbPermintaan.getSelectedRow()!= -1){
            if(ChkAccor.isSelected()==false){
                JOptionPane.showMessageDialog(null,"Silahkan tampilkan data detail permintaan terlebih dahulu...!!!");
            }else{
                if(tbDetailPermintaan.getRowCount()!=0){
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    Sequel.queryu("delete from temporary");
                    for(i=0;i<tbDetailPermintaan.getRowCount();i++){ 
                        Sequel.menyimpan("temporary","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?",38,new String[]{
                            "0",tbDetailPermintaan.getValueAt(i,0).toString(),tbDetailPermintaan.getValueAt(i,1).toString(),tbDetailPermintaan.getValueAt(i,2).toString(),tbDetailPermintaan.getValueAt(i,4).toString(),"","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","",""
                        });               
                    }
                    Map<String, Object> param = new HashMap<>();
                    param.put("namapelanggan",tbPermintaan.getValueAt(tbPermintaan.getSelectedRow(),3).toString().trim());
                    param.put("alamatpelanggan",tbPermintaan.getValueAt(tbPermintaan.getSelectedRow(),4).toString().trim());
                    param.put("kegiatanusaha",tbPermintaan.getValueAt(tbPermintaan.getSelectedRow(),5).toString().trim());
                    param.put("personaldihubungi",tbPermintaan.getValueAt(tbPermintaan.getSelectedRow(),6).toString().trim());
                    param.put("kontakpelanggan",tbPermintaan.getValueAt(tbPermintaan.getSelectedRow(),7).toString().trim());
                    param.put("lokasisampling",tbPermintaan.getValueAt(tbPermintaan.getSelectedRow(),11).toString().trim());
                    param.put("waktusampling",Valid.SetTgl5(tbPermintaan.getValueAt(tbPermintaan.getSelectedRow(),10).toString().trim()));
                    param.put("jenissampel",tbPermintaan.getValueAt(tbPermintaan.getSelectedRow(),13).toString().trim());
                    param.put("jumlahsampel",tbPermintaan.getValueAt(tbPermintaan.getSelectedRow(),14).toString().trim());
                    param.put("volumesampel",tbPermintaan.getValueAt(tbPermintaan.getSelectedRow(),16).toString().trim());
                    param.put("wadahsampel",tbPermintaan.getValueAt(tbPermintaan.getSelectedRow(),17).toString().trim());
                    param.put("kondisiwadah",tbPermintaan.getValueAt(tbPermintaan.getSelectedRow(),18).toString().trim());
                    param.put("kodesampel",tbPermintaan.getValueAt(tbPermintaan.getSelectedRow(),19).toString().trim()+" "+tbPermintaan.getValueAt(tbPermintaan.getSelectedRow(),20).toString().trim());
                    param.put("petugassampling",tbPermintaan.getValueAt(tbPermintaan.getSelectedRow(),15).toString().trim());
                    param.put("waktupenerimaan",Valid.SetTgl5(tbPermintaan.getValueAt(tbPermintaan.getSelectedRow(),0).toString().trim()));
                    param.put("deskripsisampel",tbPermintaan.getValueAt(tbPermintaan.getSelectedRow(),12).toString().trim());
                    param.put("bakumutu",tbPermintaan.getValueAt(tbPermintaan.getSelectedRow(),21).toString().trim());
                    param.put("petugaspelayanan",tbPermintaan.getValueAt(tbPermintaan.getSelectedRow(),9).toString().trim());
                    param.put("nomorpermintaan",tbPermintaan.getValueAt(tbPermintaan.getSelectedRow(),1).toString().trim());
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());   
                    param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                    String finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",tbPermintaan.getValueAt(tbPermintaan.getSelectedRow(),8).toString().trim());
                    param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbPermintaan.getValueAt(tbPermintaan.getSelectedRow(),9).toString().trim()+"\nID "+(finger.equals("")?tbPermintaan.getValueAt(tbPermintaan.getSelectedRow(),8).toString().trim():finger)+"\n"+Valid.SetTgl5(tbPermintaan.getValueAt(tbPermintaan.getSelectedRow(),0).toString().trim())); 
                    Valid.MyReport("rptPermintaanPengujianSampelLaboratKesling.jasper","report","::[ Permintaan Pengujian Sampel Laborat Kesehatan Lingkungan ]::",param);   
                    this.setCursor(Cursor.getDefaultCursor());
                }else{
                    JOptionPane.showMessageDialog(null,"Silahkan tampilkan data detail permintaan terlebih dahulu...!!!");
                }
            }
        }else{
            JOptionPane.showMessageDialog(null,"Silahkan pilih data permintaan...!!!");
        }
    }//GEN-LAST:event_ppSuratPermintaanActionPerformed

    private void ppTidakDapatDilayaniActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppTidakDapatDilayaniActionPerformed
        if(tbPermintaan.getSelectedRow()!= -1){
            if(tbPermintaan.getValueAt(tbPermintaan.getSelectedRow(),22).toString().equals("Permintaan Baru")){
                NoPermintaanVerifikasi.setText(tbPermintaan.getValueAt(tbPermintaan.getSelectedRow(),1).toString());
                AsalPermintaan.setText(tbPermintaan.getValueAt(tbPermintaan.getSelectedRow(),2).toString()+" "+tbPermintaan.getValueAt(tbPermintaan.getSelectedRow(),3).toString());
                WindowInput.setVisible(true);
                Keterangan.requestFocus();
            }else {
                JOptionPane.showMessageDialog(null,"Status permintaan sudah "+tbPermintaan.getValueAt(tbPermintaan.getSelectedRow(),22).toString()+"...!");
            }
        }else{
            JOptionPane.showMessageDialog(null,"Silahkan pilih data permintaan...!!!");
        }
    }//GEN-LAST:event_ppTidakDapatDilayaniActionPerformed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if(TabData.getSelectedIndex()==0){
            if(tbPermintaan.getSelectedRow()!= -1){
                if(tbPermintaan.getValueAt(tbPermintaan.getSelectedRow(),22).toString().equals("Permintaan Baru")){
                    if(Sequel.queryutf("delete from laborat_kesling_permintaan_pengujian_sampel where no_permintaan='"+tbPermintaan.getValueAt(tbPermintaan.getSelectedRow(),1).toString()+"'")==true){
                        tabModePermintaan.removeRow(tbPermintaan.getSelectedRow());
                        Valid.tabelKosong(tabModeDetailPermintaan);
                        LTotal.setText(tabModePermintaan.getRowCount()+"");
                    }
                }else{
                    JOptionPane.showMessageDialog(null,"Bukan permintaan baru, tidak bisa dihapus...!");
                }
            }else{
                JOptionPane.showMessageDialog(null,"Silahkan pilih data permintaan...!!!");
            }
        }else if(TabData.getSelectedIndex()==1){
            if(tbRekapPermintaan.getSelectedRow()!= -1){
                if(tbRekapPermintaan.getValueAt(tbRekapPermintaan.getSelectedRow(),22).toString().equals("Permintaan Baru")){
                    if(Sequel.queryutf("delete from laborat_kesling_detail_permintaan_pengujian_sampel where no_permintaan='"+tbRekapPermintaan.getValueAt(tbRekapPermintaan.getSelectedRow(),1).toString()+"' and kode_parameter='"+tbRekapPermintaan.getValueAt(tbRekapPermintaan.getSelectedRow(),23).toString()+"'")==true){
                        tabModeRekapPermintaan.removeRow(tbRekapPermintaan.getSelectedRow());
                        LTotal.setText(tabModeRekapPermintaan.getRowCount()+"");
                    }
                }else{
                    JOptionPane.showMessageDialog(null,"Bukan permintaan baru, tidak bisa dihapus...!");
                }
            }else{
                JOptionPane.showMessageDialog(null,"Silahkan pilih data rekap permintaan...!!!");
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

    private void BtnTutupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTutupActionPerformed
        WindowInput.dispose();
    }//GEN-LAST:event_BtnTutupActionPerformed

    private void BtnTutupKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnTutupKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            WindowInput.dispose();
        }else{Valid.pindah(evt, BtnBatal, NoPermintaanVerifikasi);}
    }//GEN-LAST:event_BtnTutupKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(NoPermintaanVerifikasi.getText().trim().equals("")){
            Valid.textKosong(NoPermintaanVerifikasi,"No.Permintaan");
        }else if(AsalPermintaan.getText().trim().equals("")){
            Valid.textKosong(AsalPermintaan,"Asal Permintaan");
        }else if(Keterangan.getText().trim().equals("")){
            Valid.textKosong(Keterangan,"Unsur Kaji Ulang/Abnornalitas Sampel/Keterangan");
        }else{
            if(Sequel.menyimpantf("laborat_kesling_permintaan_pengujian_sampel_tidak_dilayani","?,?","No.Permintaan",2,new String[]{
                NoPermintaanVerifikasi.getText(),Keterangan.getText()
            })==true){
                Sequel.queryu("update laborat_kesling_permintaan_pengujian_sampel set status='Tidak Dapat Dilayani' where no_permintaan='"+tbPermintaan.getValueAt(tbPermintaan.getSelectedRow(),1).toString().trim()+"'");
                tbPermintaan.setValueAt("Tidak Dapat Dilayani",tbPermintaan.getSelectedRow(),22);
                Keterangan.setText("");
                WindowInput.dispose();
            }
        }
    }//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        Valid.pindah(evt,Keterangan,BtnBatal);
    }//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        Keterangan.setText("");
    }//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            Keterangan.setText("");
        }else{Valid.pindah(evt, BtnSimpan, BtnTutup);}
    }//GEN-LAST:event_BtnBatalKeyPressed

    private void KeteranganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKeyPressed
        Valid.pindah2(evt,BtnTutup,BtnSimpan);
    }//GEN-LAST:event_KeteranganKeyPressed

    private void BtnTutup2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTutup2ActionPerformed
        WindowInput.dispose();
    }//GEN-LAST:event_BtnTutup2ActionPerformed

    private void BtnTutup2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnTutup2KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            WindowInput2.dispose();
        }else{Valid.pindah(evt, BtnBatal2, NoPermintaanVerifikasi2);}
    }//GEN-LAST:event_BtnTutup2KeyPressed

    private void BtnSimpan2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpan2ActionPerformed
        if(NoPermintaanVerifikasi2.getText().trim().equals("")){
            Valid.textKosong(NoPermintaanVerifikasi2,"No.Permintaan");
        }else if(AsalPermintaan2.getText().trim().equals("")){
            Valid.textKosong(AsalPermintaan2,"Asal Permintaan");
        }else if(Keterangan2.getText().trim().equals("")){
            Valid.textKosong(Keterangan2,"Kebutuhan Sub Kontrak Pengujian/Keterangan Lainnya");
        }else{
            if(Sequel.menyimpantf("laborat_kesling_permintaan_pengujian_sampel_dilayani","?,?,'Belum Ada Penugasan'","No.Permintaan",2,new String[]{
                NoPermintaanVerifikasi2.getText(),Keterangan2.getText()
            })==true){
                Sequel.queryu("update laborat_kesling_permintaan_pengujian_sampel set status='Dapat Dilayani' where no_permintaan='"+tbPermintaan.getValueAt(tbPermintaan.getSelectedRow(),1).toString()+"'");
                tbPermintaan.setValueAt("Dapat Dilayani",tbPermintaan.getSelectedRow(),22);
                Keterangan2.setText("");
                WindowInput2.dispose();
            }
        }
    }//GEN-LAST:event_BtnSimpan2ActionPerformed

    private void BtnSimpan2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpan2KeyPressed
        Valid.pindah(evt,Keterangan2,BtnBatal2);
    }//GEN-LAST:event_BtnSimpan2KeyPressed

    private void BtnBatal2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatal2ActionPerformed
        Keterangan2.setText("");
    }//GEN-LAST:event_BtnBatal2ActionPerformed

    private void BtnBatal2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatal2KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            Keterangan2.setText("");
        }else{Valid.pindah(evt, BtnSimpan2, BtnTutup2);}
    }//GEN-LAST:event_BtnBatal2KeyPressed

    private void Keterangan2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Keterangan2KeyPressed
        Valid.pindah2(evt,BtnTutup2,BtnSimpan2);
    }//GEN-LAST:event_Keterangan2KeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            LabKeslingCariPermintaanPengujianSampel dialog = new LabKeslingCariPermintaanPengujianSampel(new javax.swing.JFrame(), true);
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
    private widget.TextBox AsalPermintaan;
    private widget.TextBox AsalPermintaan2;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnBatal2;
    private widget.Button BtnCari;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.Button BtnSimpan2;
    private widget.Button BtnTutup;
    private widget.Button BtnTutup2;
    private widget.CekBox ChkAccor;
    private widget.TextArea Keterangan;
    private widget.TextArea Keterangan2;
    private widget.TextBox KodePelanggan;
    private widget.TextBox KodePetugas;
    private widget.TextBox KodeSampel;
    private widget.Label LTotal;
    private widget.editorpane LoadHTML;
    private widget.TextBox NamaPelanggan;
    private widget.TextBox NamaPetugas;
    private widget.TextBox NamaSampel;
    private widget.TextBox NoPermintaan;
    private widget.TextBox NoPermintaanVerifikasi;
    private widget.TextBox NoPermintaanVerifikasi2;
    private widget.PanelBiasa PanelAccor;
    private widget.ComboBox Status;
    private widget.TextBox TCari;
    private javax.swing.JTabbedPane TabData;
    private widget.Tanggal Tanggal1;
    private widget.Tanggal Tanggal2;
    private javax.swing.JDialog WindowInput;
    private javax.swing.JDialog WindowInput2;
    private widget.Button btnPelanggan;
    private widget.Button btnPetugas;
    private widget.Button btnSampel;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.Label jLabel19;
    private widget.Label jLabel20;
    private widget.Label jLabel42;
    private widget.Label jLabel43;
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
    private widget.Label label2;
    private widget.Label label3;
    private widget.Label label4;
    private widget.Label label5;
    private widget.Label label6;
    private widget.Label label7;
    private widget.Label label8;
    private widget.Label label9;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi3;
    private widget.panelisi panelisi4;
    private javax.swing.JMenuItem ppDapatDilayani;
    private javax.swing.JMenuItem ppSuratPermintaan;
    private javax.swing.JMenuItem ppTidakDapatDilayani;
    private widget.ScrollPane scrollPane1;
    private widget.ScrollPane scrollPane2;
    private widget.ScrollPane scrollPane4;
    private widget.ScrollPane scrollPane5;
    private widget.ScrollPane scrollPaneDetail;
    private widget.Table tbDetailPermintaan;
    private widget.Table tbPermintaan;
    private widget.Table tbRekapPermintaan;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabModePermintaan);
        try{  
            ps=koneksi.prepareStatement(
                        "select laborat_kesling_permintaan_pengujian_sampel.no_permintaan,laborat_kesling_permintaan_pengujian_sampel.kode_pelanggan,laborat_kesling_pelanggan.nama_pelanggan,laborat_kesling_pelanggan.alamat,laborat_kesling_pelanggan.no_telp,"+
                        "laborat_kesling_pelanggan.kegiatan_usaha,laborat_kesling_pelanggan.personal_dihubungi,laborat_kesling_permintaan_pengujian_sampel.nip,petugas.nama,laborat_kesling_permintaan_pengujian_sampel.waktu_sampling,"+
                        "laborat_kesling_permintaan_pengujian_sampel.waktu_diterima,laborat_kesling_permintaan_pengujian_sampel.lokasi_sampling,laborat_kesling_permintaan_pengujian_sampel.deskripsi_sampel,laborat_kesling_permintaan_pengujian_sampel.jenis_sampel,"+
                        "laborat_kesling_permintaan_pengujian_sampel.jumlah_sampel,laborat_kesling_permintaan_pengujian_sampel.sampling_dilakukan_oleh,laborat_kesling_permintaan_pengujian_sampel.volume_sampel,laborat_kesling_permintaan_pengujian_sampel.wadah_sampel,"+
                        "laborat_kesling_permintaan_pengujian_sampel.kondisi_wadah_sampel,laborat_kesling_permintaan_pengujian_sampel.kode_sampel,laborat_kesling_master_sampel.nama_sampel,laborat_kesling_master_sampel.baku_mutu,laborat_kesling_permintaan_pengujian_sampel.status "+
                        "from laborat_kesling_permintaan_pengujian_sampel inner join laborat_kesling_pelanggan on laborat_kesling_permintaan_pengujian_sampel.kode_pelanggan=laborat_kesling_pelanggan.kode_pelanggan "+
                        "inner join petugas on laborat_kesling_permintaan_pengujian_sampel.nip=petugas.nip inner join laborat_kesling_master_sampel on laborat_kesling_permintaan_pengujian_sampel.kode_sampel=laborat_kesling_master_sampel.kode_sampel "+
                        "where laborat_kesling_permintaan_pengujian_sampel.waktu_diterima between ? and ? "+(NoPermintaan.getText().trim().equals("")?"":" and laborat_kesling_permintaan_pengujian_sampel.no_permintaan='"+NoPermintaan.getText()+"' ")+
                        (Status.getSelectedItem().toString().equals("Semua")?"":" and laborat_kesling_permintaan_pengujian_sampel.status='"+Status.getSelectedItem().toString()+"' ")+
                        (NamaPetugas.getText().trim().equals("")?"":" and laborat_kesling_permintaan_pengujian_sampel.nip='"+KodePetugas.getText()+"' ")+
                        (NamaPelanggan.getText().trim().equals("")?"":" and laborat_kesling_permintaan_pengujian_sampel.kode_pelanggan='"+KodePelanggan.getText()+"' ")+
                        (NamaSampel.getText().trim().equals("")?"":" and laborat_kesling_permintaan_pengujian_sampel.kode_sampel='"+KodeSampel.getText()+"' ")+
                        (TCari.getText().trim().equals("")?"":" and (laborat_kesling_permintaan_pengujian_sampel.lokasi_sampling like ? or laborat_kesling_permintaan_pengujian_sampel.deskripsi_sampel like ? or laborat_kesling_permintaan_pengujian_sampel.jenis_sampel like ? "+
                        "or laborat_kesling_permintaan_pengujian_sampel.sampling_dilakukan_oleh like ? or laborat_kesling_permintaan_pengujian_sampel.wadah_sampel like ? or laborat_kesling_permintaan_pengujian_sampel.kondisi_wadah_sampel like ?) ")+
                        "order by laborat_kesling_permintaan_pengujian_sampel.waktu_diterima,laborat_kesling_permintaan_pengujian_sampel.no_permintaan");
                
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
                }
                    
                rs=ps.executeQuery();
                while(rs.next()){
                    tabModePermintaan.addRow(new Object[]{
                        rs.getString("waktu_diterima"),rs.getString("no_permintaan"),rs.getString("kode_pelanggan"),rs.getString("nama_pelanggan"),rs.getString("alamat"),rs.getString("kegiatan_usaha"),
                        rs.getString("personal_dihubungi"),rs.getString("no_telp"),rs.getString("nip"),rs.getString("nama"),rs.getString("waktu_sampling"),rs.getString("lokasi_sampling"),rs.getString("deskripsi_sampel"),
                        rs.getString("jenis_sampel"),rs.getString("jumlah_sampel"),rs.getString("sampling_dilakukan_oleh"),rs.getString("volume_sampel"),rs.getString("wadah_sampel"),rs.getString("kondisi_wadah_sampel"),
                        rs.getString("kode_sampel"),rs.getString("nama_sampel"),rs.getString("baku_mutu"),rs.getString("status")
                    }); 
                }        
                LTotal.setText(tabModePermintaan.getRowCount()+"");
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
        Valid.tabelKosong(tabModeRekapPermintaan);
        try{  
            ps=koneksi.prepareStatement(
                        "select laborat_kesling_permintaan_pengujian_sampel.no_permintaan,laborat_kesling_permintaan_pengujian_sampel.kode_pelanggan,laborat_kesling_pelanggan.nama_pelanggan,laborat_kesling_pelanggan.alamat,laborat_kesling_pelanggan.no_telp,"+
                        "laborat_kesling_pelanggan.kegiatan_usaha,laborat_kesling_pelanggan.personal_dihubungi,laborat_kesling_permintaan_pengujian_sampel.nip,petugas.nama,laborat_kesling_permintaan_pengujian_sampel.waktu_sampling,"+
                        "laborat_kesling_permintaan_pengujian_sampel.waktu_diterima,laborat_kesling_permintaan_pengujian_sampel.lokasi_sampling,laborat_kesling_permintaan_pengujian_sampel.deskripsi_sampel,laborat_kesling_permintaan_pengujian_sampel.jenis_sampel,"+
                        "laborat_kesling_permintaan_pengujian_sampel.jumlah_sampel,laborat_kesling_permintaan_pengujian_sampel.sampling_dilakukan_oleh,laborat_kesling_permintaan_pengujian_sampel.volume_sampel,laborat_kesling_permintaan_pengujian_sampel.wadah_sampel,"+
                        "laborat_kesling_permintaan_pengujian_sampel.kondisi_wadah_sampel,laborat_kesling_permintaan_pengujian_sampel.kode_sampel,laborat_kesling_master_sampel.nama_sampel,laborat_kesling_master_sampel.baku_mutu,laborat_kesling_permintaan_pengujian_sampel.status,"+
                        "laborat_kesling_detail_permintaan_pengujian_sampel.kode_parameter,laborat_kesling_parameter_pengujian.nama_parameter,laborat_kesling_parameter_pengujian.metode_pengujian,laborat_kesling_parameter_pengujian.satuan,"+
                        "laborat_kesling_parameter_pengujian.kategori,laborat_kesling_nilai_normal_baku_mutu.nilai_normal "+
                        "from laborat_kesling_permintaan_pengujian_sampel inner join laborat_kesling_pelanggan on laborat_kesling_permintaan_pengujian_sampel.kode_pelanggan=laborat_kesling_pelanggan.kode_pelanggan "+
                        "inner join petugas on laborat_kesling_permintaan_pengujian_sampel.nip=petugas.nip inner join laborat_kesling_master_sampel on laborat_kesling_permintaan_pengujian_sampel.kode_sampel=laborat_kesling_master_sampel.kode_sampel "+
                        "inner join laborat_kesling_detail_permintaan_pengujian_sampel on laborat_kesling_detail_permintaan_pengujian_sampel.no_permintaan=laborat_kesling_permintaan_pengujian_sampel.no_permintaan "+
                        "inner join laborat_kesling_parameter_pengujian on laborat_kesling_detail_permintaan_pengujian_sampel.kode_parameter=laborat_kesling_parameter_pengujian.kode_parameter "+
                        "inner join laborat_kesling_nilai_normal_baku_mutu on laborat_kesling_nilai_normal_baku_mutu.kode_parameter=laborat_kesling_parameter_pengujian.kode_parameter and laborat_kesling_nilai_normal_baku_mutu.kode_sampel=laborat_kesling_permintaan_pengujian_sampel.kode_sampel "+
                        "where laborat_kesling_permintaan_pengujian_sampel.waktu_diterima between ? and ? "+(NoPermintaan.getText().trim().equals("")?"":" and laborat_kesling_permintaan_pengujian_sampel.no_permintaan='"+NoPermintaan.getText()+"' ")+
                        (Status.getSelectedItem().toString().equals("Semua")?"":" and laborat_kesling_permintaan_pengujian_sampel.status='"+Status.getSelectedItem().toString()+"' ")+
                        (NamaPetugas.getText().trim().equals("")?"":" and laborat_kesling_permintaan_pengujian_sampel.nip='"+KodePetugas.getText()+"' ")+
                        (NamaPelanggan.getText().trim().equals("")?"":" and laborat_kesling_permintaan_pengujian_sampel.kode_pelanggan='"+KodePelanggan.getText()+"' ")+
                        (NamaSampel.getText().trim().equals("")?"":" and laborat_kesling_permintaan_pengujian_sampel.kode_sampel='"+KodeSampel.getText()+"' ")+
                        (TCari.getText().trim().equals("")?"":" and (laborat_kesling_permintaan_pengujian_sampel.lokasi_sampling like ? or laborat_kesling_permintaan_pengujian_sampel.deskripsi_sampel like ? or laborat_kesling_permintaan_pengujian_sampel.jenis_sampel like ? "+
                        "or laborat_kesling_permintaan_pengujian_sampel.sampling_dilakukan_oleh like ? or laborat_kesling_permintaan_pengujian_sampel.wadah_sampel like ? or laborat_kesling_permintaan_pengujian_sampel.kondisi_wadah_sampel like ? "+
                        "or laborat_kesling_detail_permintaan_pengujian_sampel.kode_parameter like ? or laborat_kesling_parameter_pengujian.nama_parameter like ? or laborat_kesling_parameter_pengujian.metode_pengujian like ? or laborat_kesling_parameter_pengujian.kategori like ?) ")+
                        "order by laborat_kesling_permintaan_pengujian_sampel.waktu_diterima,laborat_kesling_permintaan_pengujian_sampel.no_permintaan");
                
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
                    tabModeRekapPermintaan.addRow(new Object[]{
                        rs.getString("waktu_diterima"),rs.getString("no_permintaan"),rs.getString("kode_pelanggan"),rs.getString("nama_pelanggan"),rs.getString("alamat"),rs.getString("kegiatan_usaha"),
                        rs.getString("personal_dihubungi"),rs.getString("no_telp"),rs.getString("nip"),rs.getString("nama"),rs.getString("waktu_sampling"),rs.getString("lokasi_sampling"),rs.getString("deskripsi_sampel"),
                        rs.getString("jenis_sampel"),rs.getString("jumlah_sampel"),rs.getString("sampling_dilakukan_oleh"),rs.getString("volume_sampel"),rs.getString("wadah_sampel"),rs.getString("kondisi_wadah_sampel"),
                        rs.getString("kode_sampel"),rs.getString("nama_sampel"),rs.getString("baku_mutu"),rs.getString("status"),rs.getString("kode_parameter"),rs.getString("nama_parameter"),rs.getString("metode_pengujian"),
                        rs.getString("satuan"),rs.getString("kategori"),rs.getString("nilai_normal")
                    }); 
                }        
                LTotal.setText(tabModeRekapPermintaan.getRowCount()+"");
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
        BtnPrint.setEnabled(akses.getpermintaan_pengujian_sampel_lab_kesehatan_lingkungan());
        ppDapatDilayani.setEnabled(akses.getpermintaan_pengujian_sampel_lab_kesehatan_lingkungan());
        ppTidakDapatDilayani.setEnabled(akses.getpermintaan_pengujian_sampel_lab_kesehatan_lingkungan());
        ppSuratPermintaan.setEnabled(akses.getpermintaan_pengujian_sampel_lab_kesehatan_lingkungan());
        BtnHapus.setEnabled(akses.getpermintaan_pengujian_sampel_lab_kesehatan_lingkungan());
    }
}

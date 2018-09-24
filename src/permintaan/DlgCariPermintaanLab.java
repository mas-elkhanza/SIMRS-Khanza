package permintaan;
import keuangan.Jurnal;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.var;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import simrskhanza.DlgPeriksaLaboratorium;

public class DlgCariPermintaanLab extends javax.swing.JDialog {
    private final DefaultTableModel tabMode,tabMode2;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Jurnal jur=new Jurnal();
    private Connection koneksi=koneksiDB.condb();
    private Dimension screen=Toolkit.getDefaultToolkit().getScreenSize();
    private int i;
    private PreparedStatement ps,ps2,ps3;
    private ResultSet rs,rs2,rs3;
    private String tglsampel="",tglhasil="",norm="",kamar="",namakamar="",diagnosa="",la="",ld="",pa="",pd="";
    
    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public DlgCariPermintaanLab(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        WindowAmbilSampel.setSize(530,80);
        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Permintaan","No.Rawat","Pasien","Permintaan","Jam",
            "Sampel","Jam","Hasil","Jam","Kode Dokter","Dokter Perujuk"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbDokter.setModel(tabMode);

        tbDokter.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 11; i++) {
            TableColumn column = tbDokter.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(90);
            }else if(i==1){
                column.setPreferredWidth(105);
            }else if(i==2){
                column.setPreferredWidth(400);
            }else if(i==3){
                column.setPreferredWidth(65);
            }else if(i==4){
                column.setPreferredWidth(50);
            }else if(i==5){
                column.setPreferredWidth(65);
            }else if(i==6){
                column.setPreferredWidth(50);
            }else if(i==7){
                column.setPreferredWidth(65);
            }else if(i==8){
                column.setPreferredWidth(50);
            }else if(i==9){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==10){
                column.setPreferredWidth(150);
            }
        }
        tbDokter.setDefaultRenderer(Object.class, new WarnaTable());
            tabMode2=new DefaultTableModel(null,new Object[]{
                "No.Permintaan","No.Rawat","Pasien","Pemeriksaan","Detail Pemeriksaan",
                "Satuan","Nilai Rujukan","Permintaan","Jam","Sampel","Jam","Hasil",
                "Jam","Kode Dokter","Dokter Perujuk"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbDokter2.setModel(tabMode2);

        tbDokter2.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbDokter2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 15; i++) {
            TableColumn column = tbDokter2.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(90);
            }else if(i==1){
                column.setPreferredWidth(105);
            }else if(i==2){
                column.setPreferredWidth(250);
            }else if(i==3){
                column.setPreferredWidth(150);
            }else if(i==4){
                column.setPreferredWidth(170);
            }else if(i==5){
                column.setPreferredWidth(50);
            }else if(i==6){
                column.setPreferredWidth(200);
            }else if(i==7){
                column.setPreferredWidth(65);
            }else if(i==8){
                column.setPreferredWidth(50);
            }else if(i==9){
                column.setPreferredWidth(65);
            }else if(i==10){
                column.setPreferredWidth(50);
            }else if(i==11){
                column.setPreferredWidth(65);
            }else if(i==12){
                column.setPreferredWidth(50);
            }else if(i==13){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==14){
                column.setPreferredWidth(150);
            }
        }
        tbDokter2.setDefaultRenderer(Object.class, new WarnaTable());
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        if(koneksiDB.cariCepat().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {tampil();}
                @Override
                public void removeUpdate(DocumentEvent e) {tampil();}
                @Override
                public void changedUpdate(DocumentEvent e) {tampil();}
            });
        } 
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Kd2 = new widget.TextBox();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnCetakHasilLab = new javax.swing.JMenuItem();
        MnBarcodePermintaan = new javax.swing.JMenuItem();
        MnBarcodePermintaan1 = new javax.swing.JMenuItem();
        WindowAmbilSampel = new javax.swing.JDialog();
        internalFrame5 = new widget.InternalFrame();
        BtnCloseIn4 = new widget.Button();
        BtnSimpan4 = new widget.Button();
        jLabel26 = new widget.Label();
        TanggalPulang = new widget.Tanggal();
        internalFrame1 = new widget.InternalFrame();
        TabRawat = new javax.swing.JTabbedPane();
        scrollPane1 = new widget.ScrollPane();
        tbDokter = new widget.Table();
        scrollPane2 = new widget.ScrollPane();
        tbDokter2 = new widget.Table();
        jPanel2 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        label11 = new widget.Label();
        Tgl1 = new widget.Tanggal();
        label18 = new widget.Label();
        Tgl2 = new widget.Tanggal();
        label10 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        jLabel10 = new widget.Label();
        LCount = new widget.Label();
        panelisi1 = new widget.panelisi();
        BtnHapus = new widget.Button();
        BtnHasil = new widget.Button();
        BtnSampel = new widget.Button();
        BtnAll = new widget.Button();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();

        Kd2.setName("Kd2"); // NOI18N
        Kd2.setPreferredSize(new java.awt.Dimension(207, 23));

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnCetakHasilLab.setBackground(new java.awt.Color(255, 255, 255));
        MnCetakHasilLab.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakHasilLab.setForeground(new java.awt.Color(130, 100, 100));
        MnCetakHasilLab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakHasilLab.setText("Cetak Permintaan Lab");
        MnCetakHasilLab.setName("MnCetakHasilLab"); // NOI18N
        MnCetakHasilLab.setPreferredSize(new java.awt.Dimension(200, 28));
        MnCetakHasilLab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakHasilLabActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnCetakHasilLab);

        MnBarcodePermintaan.setBackground(new java.awt.Color(255, 255, 255));
        MnBarcodePermintaan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnBarcodePermintaan.setForeground(new java.awt.Color(130, 100, 100));
        MnBarcodePermintaan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnBarcodePermintaan.setText("Barcode No.Permintaan");
        MnBarcodePermintaan.setName("MnBarcodePermintaan"); // NOI18N
        MnBarcodePermintaan.setPreferredSize(new java.awt.Dimension(200, 28));
        MnBarcodePermintaan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnBarcodePermintaanActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnBarcodePermintaan);

        MnBarcodePermintaan1.setBackground(new java.awt.Color(255, 255, 255));
        MnBarcodePermintaan1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnBarcodePermintaan1.setForeground(new java.awt.Color(130, 100, 100));
        MnBarcodePermintaan1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnBarcodePermintaan1.setText("Barcode No.Permintaan 2");
        MnBarcodePermintaan1.setName("MnBarcodePermintaan1"); // NOI18N
        MnBarcodePermintaan1.setPreferredSize(new java.awt.Dimension(200, 28));
        MnBarcodePermintaan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnBarcodePermintaan1ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnBarcodePermintaan1);

        WindowAmbilSampel.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowAmbilSampel.setName("WindowAmbilSampel"); // NOI18N
        WindowAmbilSampel.setUndecorated(true);
        WindowAmbilSampel.setResizable(false);

        internalFrame5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Update Waktu Pengambilan Sampel ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(130, 100, 100))); // NOI18N
        internalFrame5.setName("internalFrame5"); // NOI18N
        internalFrame5.setWarnaBawah(new java.awt.Color(245, 250, 240));
        internalFrame5.setLayout(null);

        BtnCloseIn4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/cross.png"))); // NOI18N
        BtnCloseIn4.setMnemonic('U');
        BtnCloseIn4.setText("Tutup");
        BtnCloseIn4.setToolTipText("Alt+U");
        BtnCloseIn4.setName("BtnCloseIn4"); // NOI18N
        BtnCloseIn4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCloseIn4ActionPerformed(evt);
            }
        });
        internalFrame5.add(BtnCloseIn4);
        BtnCloseIn4.setBounds(410, 30, 100, 30);

        BtnSimpan4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan4.setMnemonic('S');
        BtnSimpan4.setText("Simpan");
        BtnSimpan4.setToolTipText("Alt+S");
        BtnSimpan4.setName("BtnSimpan4"); // NOI18N
        BtnSimpan4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpan4ActionPerformed(evt);
            }
        });
        internalFrame5.add(BtnSimpan4);
        BtnSimpan4.setBounds(305, 30, 100, 30);

        jLabel26.setText("Tanggal & Jam :");
        jLabel26.setName("jLabel26"); // NOI18N
        internalFrame5.add(jLabel26);
        jLabel26.setBounds(6, 32, 100, 23);

        TanggalPulang.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "03-09-2018 11:45:44" }));
        TanggalPulang.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        TanggalPulang.setName("TanggalPulang"); // NOI18N
        TanggalPulang.setOpaque(false);
        TanggalPulang.setPreferredSize(new java.awt.Dimension(95, 23));
        internalFrame5.add(TanggalPulang);
        TanggalPulang.setBounds(110, 32, 150, 23);

        WindowAmbilSampel.getContentPane().add(internalFrame5, java.awt.BorderLayout.CENTER);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Permintaan Laboratorium ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(130, 100, 100))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        TabRawat.setBackground(new java.awt.Color(255, 255, 253));
        TabRawat.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(241, 246, 236)));
        TabRawat.setForeground(new java.awt.Color(130, 100, 100));
        TabRawat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabRawat.setName("TabRawat"); // NOI18N
        TabRawat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatMouseClicked(evt);
            }
        });

        scrollPane1.setName("scrollPane1"); // NOI18N
        scrollPane1.setOpaque(true);

        tbDokter.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbDokter.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbDokter.setComponentPopupMenu(jPopupMenu1);
        tbDokter.setName("tbDokter"); // NOI18N
        tbDokter.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDokterMouseClicked(evt);
            }
        });
        tbDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbDokterKeyPressed(evt);
            }
        });
        scrollPane1.setViewportView(tbDokter);

        TabRawat.addTab("Data Permintaan", scrollPane1);

        scrollPane2.setName("scrollPane2"); // NOI18N
        scrollPane2.setOpaque(true);

        tbDokter2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbDokter2.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbDokter2.setName("tbDokter2"); // NOI18N
        tbDokter2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDokter2MouseClicked(evt);
            }
        });
        tbDokter2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbDokter2KeyPressed(evt);
            }
        });
        scrollPane2.setViewportView(tbDokter2);

        TabRawat.addTab("Item Permintaan", scrollPane2);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        jPanel2.setName("jPanel2"); // NOI18N
        jPanel2.setOpaque(false);
        jPanel2.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 3, 9));

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(55, 23));
        panelGlass8.add(label11);

        Tgl1.setEditable(false);
        Tgl1.setDisplayFormat("dd-MM-yyyy");
        Tgl1.setName("Tgl1"); // NOI18N
        Tgl1.setPreferredSize(new java.awt.Dimension(90, 23));
        Tgl1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Tgl1KeyPressed(evt);
            }
        });
        panelGlass8.add(Tgl1);

        label18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label18.setText("s.d.");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(25, 23));
        panelGlass8.add(label18);

        Tgl2.setEditable(false);
        Tgl2.setDisplayFormat("dd-MM-yyyy");
        Tgl2.setName("Tgl2"); // NOI18N
        Tgl2.setPreferredSize(new java.awt.Dimension(90, 23));
        Tgl2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Tgl2KeyPressed(evt);
            }
        });
        panelGlass8.add(Tgl2);

        label10.setText("Key Word :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(62, 23));
        panelGlass8.add(label10);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(160, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass8.add(TCari);

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
        panelGlass8.add(BtnCari);

        jLabel10.setText("Record :");
        jLabel10.setName("jLabel10"); // NOI18N
        jLabel10.setPreferredSize(new java.awt.Dimension(55, 23));
        panelGlass8.add(jLabel10);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(100, 23));
        panelGlass8.add(LCount);

        jPanel2.add(panelGlass8, java.awt.BorderLayout.CENTER);

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 56));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

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

        BtnHasil.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/file-edit-16x16.png"))); // NOI18N
        BtnHasil.setMnemonic('P');
        BtnHasil.setText("Sampel");
        BtnHasil.setToolTipText("Alt+P");
        BtnHasil.setName("BtnHasil"); // NOI18N
        BtnHasil.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnHasil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnHasilActionPerformed(evt);
            }
        });
        BtnHasil.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnHasilKeyPressed(evt);
            }
        });
        panelisi1.add(BtnHasil);

        BtnSampel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Agenda-1-16x16.png"))); // NOI18N
        BtnSampel.setMnemonic('I');
        BtnSampel.setText("Hasil");
        BtnSampel.setToolTipText("Alt+I");
        BtnSampel.setName("BtnSampel"); // NOI18N
        BtnSampel.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnSampel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSampelActionPerformed(evt);
            }
        });
        BtnSampel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSampelKeyPressed(evt);
            }
        });
        panelisi1.add(BtnSampel);

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

        jPanel2.add(panelisi1, java.awt.BorderLayout.PAGE_END);

        internalFrame1.add(jPanel2, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents
/*
private void KdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdKeyPressed
    Valid.pindah(evt,BtnCari,Nm);
}//GEN-LAST:event_TKdKeyPressed
*/

    private void Tgl1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tgl1KeyPressed
        Valid.pindah(evt,BtnKeluar,Tgl2);
    }//GEN-LAST:event_Tgl1KeyPressed

    private void Tgl2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tgl2KeyPressed
        Valid.pindah(evt, Tgl1,TCari);
    }//GEN-LAST:event_Tgl2KeyPressed

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
        TabRawatMouseClicked(null);
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
        TabRawatMouseClicked(null);
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnKeluar);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));        
        if(TabRawat.getSelectedIndex()==0){
            if(tabMode.getRowCount()==0){
                JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                TCari.requestFocus();
            }else if(tabMode.getRowCount()!=0){
                Sequel.AutoComitFalse();
                Sequel.queryu("delete from temporary_permintaan_lab");
                int row=tabMode.getRowCount();
                for(i=0;i<row;i++){  
                    tglsampel="";
                    try {
                        tglsampel=tabMode.getValueAt(i,5).toString();
                    } catch (Exception e) {
                        tglsampel="";
                    }
                    tglhasil="";
                    try {
                        tglhasil=tabMode.getValueAt(i,7).toString();
                    } catch (Exception e) {
                        tglhasil="";
                    }
                    Sequel.menyimpan("temporary_permintaan_lab","'0','"+
                        tabMode.getValueAt(i,0).toString()+"','"+
                        tabMode.getValueAt(i,1).toString()+"','"+
                        tabMode.getValueAt(i,2).toString()+"','"+
                        tabMode.getValueAt(i,3).toString()+"','"+
                        tabMode.getValueAt(i,4).toString()+"','"+
                        tglsampel+"','"+
                        tabMode.getValueAt(i,6).toString()+"','"+
                        tglhasil+"','"+
                        tabMode.getValueAt(i,8).toString()+"','"+
                        tabMode.getValueAt(i,9).toString()+"','"+
                        tabMode.getValueAt(i,10).toString()+"',"+
                        "'','','','','','','','','','','','','','','','','','','','','','','','','',''","Periksa Lab"); 
                }
                Sequel.AutoComitTrue();
                Map<String, Object> param = new HashMap<>();
                param.put("namars",var.getnamars());
                param.put("alamatrs",var.getalamatrs());
                param.put("kotars",var.getkabupatenrs());
                param.put("propinsirs",var.getpropinsirs());
                param.put("kontakrs",var.getkontakrs());
                param.put("emailrs",var.getemailrs());
                param.put("logo",Sequel.cariGambar("select logo from setting")); 
                Valid.MyReport("rptLapPermintaanLab.jrxml","report","::[ Data Permintaan Laboratorium ]::",
                    "select no, temp1, temp2, temp3, temp4, temp5, temp6, temp7, temp8, temp9, temp10, temp11, temp12, temp13, temp14, temp14, temp15, temp16 from temporary_permintaan_lab order by no asc",param);
            }
        }else if(TabRawat.getSelectedIndex()==1){
            if(tabMode2.getRowCount()==0){
                JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                TCari.requestFocus();
            }else if(tabMode2.getRowCount()!=0){
                Sequel.AutoComitFalse();
                Sequel.queryu("delete from temporary_permintaan_lab");
                int row=tabMode2.getRowCount();
                for(i=0;i<row;i++){  
                    tglsampel="";
                    try {
                        tglsampel=tabMode2.getValueAt(i,9).toString();
                    } catch (Exception e) {
                        tglsampel="";
                    }
                    tglhasil="";
                    try {
                        tglhasil=tabMode2.getValueAt(i,11).toString();
                    } catch (Exception e) {
                        tglhasil="";
                    }
                    Sequel.menyimpan("temporary_permintaan_lab","'0','"+
                                    tabMode2.getValueAt(i,0).toString()+"','"+
                                    tabMode2.getValueAt(i,1).toString()+"','"+
                                    tabMode2.getValueAt(i,2).toString()+"','"+
                                    tabMode2.getValueAt(i,3).toString()+"','"+
                                    tabMode2.getValueAt(i,4).toString()+"','"+
                                    tabMode2.getValueAt(i,5).toString()+"','"+
                                    tabMode2.getValueAt(i,6).toString()+"','"+
                                    tabMode2.getValueAt(i,7).toString()+"','"+
                                    tabMode2.getValueAt(i,8).toString()+"','"+
                                    tglsampel+"','"+
                                    tabMode2.getValueAt(i,10).toString()+"','"+
                                    tglhasil+"','"+
                                    tabMode2.getValueAt(i,12).toString()+"','"+
                                    tabMode2.getValueAt(i,13).toString()+"','"+
                                    tabMode2.getValueAt(i,14).toString()+"','','','','','','','','','','','','','','','','','','','','','',''","Periksa Lab"); 
                }
                Sequel.AutoComitTrue();
                Map<String, Object> param = new HashMap<>();
                param.put("namars",var.getnamars());
                param.put("alamatrs",var.getalamatrs());
                param.put("kotars",var.getkabupatenrs());
                param.put("propinsirs",var.getpropinsirs());
                param.put("kontakrs",var.getkontakrs());
                param.put("emailrs",var.getemailrs());
                param.put("logo",Sequel.cariGambar("select logo from setting")); 
                Valid.MyReport("rptLapPermintaanLab2.jrxml","report","::[ Data Detail Permintaan Laboratorium ]::",
                    "select no, temp1, temp2, temp3, temp4, temp5, temp6, temp7, temp8, temp9, temp10, temp11, temp12, temp13, temp14, temp14, temp15, temp16 from temporary_permintaan_lab order by no asc",param);
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

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        WindowAmbilSampel.dispose();
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            WindowAmbilSampel.dispose();
            dispose();
        }else{Valid.pindah(evt,BtnPrint,BtnHapus);}
    }//GEN-LAST:event_BtnKeluarKeyPressed

private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
    if(TabRawat.getSelectedIndex()==0){
        if(tbDokter.getSelectedRow()!= -1){
            if(tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString().trim().equals("")){
                Valid.textKosong(TCari,"No.Permintaan");
            }else{
                Sequel.meghapus("permintaan_lab","noorder",tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString());
                tampil();
            }
        }else{            
            JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data permintaan...!!!!");
            TCari.requestFocus();
        }
    }else if(TabRawat.getSelectedIndex()==1){
        JOptionPane.showMessageDialog(null,"Hanya bisa dilakukan hapus di Data Permintaan..!!!");
        TabRawat.setSelectedIndex(0);
        TCari.requestFocus();
    }         
}//GEN-LAST:event_BtnHapusActionPerformed

private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari,BtnAll);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

private void tbDokterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDokterMouseClicked
    if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbDokterMouseClicked

private void tbDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbDokterKeyPressed
   if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
}//GEN-LAST:event_tbDokterKeyPressed

    private void MnCetakHasilLabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCetakHasilLabActionPerformed
        if(tbDokter.getSelectedRow()!= -1){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            if(tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString().trim().equals("")){
                Valid.textKosong(TCari,"No.Permintaan");
            }else{   
                Sequel.AutoComitFalse();
                Sequel.queryu("delete from temporary_permintaan_lab");
                try {
                    ps2=koneksi.prepareStatement(
                            "select permintaan_pemeriksaan_lab.kd_jenis_prw,jns_perawatan_lab.nm_perawatan "+
                            "from permintaan_pemeriksaan_lab inner join jns_perawatan_lab on "+
                            "permintaan_pemeriksaan_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw "+
                            "where permintaan_pemeriksaan_lab.noorder=?");
                    try {
                        ps2.setString(1,tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString());
                        rs2=ps2.executeQuery();
                        while(rs2.next()){
                            Sequel.menyimpan("temporary_permintaan_lab","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?",38,new String[]{
                                "0",rs2.getString("nm_perawatan"),"","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","",""
                            });
                            ps3=koneksi.prepareStatement(
                                    "select permintaan_detail_permintaan_lab.id_template,template_laboratorium.Pemeriksaan,"+
                                    "template_laboratorium.satuan,template_laboratorium.nilai_rujukan_ld,template_laboratorium.nilai_rujukan_la,"+
                                    "template_laboratorium.nilai_rujukan_pd,template_laboratorium.nilai_rujukan_pa from permintaan_detail_permintaan_lab "+
                                    "inner join template_laboratorium on permintaan_detail_permintaan_lab.id_template=template_laboratorium.id_template "+
                                    "where permintaan_detail_permintaan_lab.kd_jenis_prw=? and permintaan_detail_permintaan_lab.noorder=? order by template_laboratorium.urut");
                            try {
                                ps3.setString(1,rs2.getString("kd_jenis_prw"));
                                ps3.setString(2,tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString());
                                rs3=ps3.executeQuery();
                                while(rs3.next()){
                                    la="";ld="";pa="";pd="";
                                    if(!rs3.getString("nilai_rujukan_ld").equals("")){
                                        ld="LD : "+rs3.getString("nilai_rujukan_ld");
                                    }
                                    if(!rs3.getString("nilai_rujukan_la").equals("")){
                                        la=", LA : "+rs3.getString("nilai_rujukan_la");
                                    }
                                    if(!rs3.getString("nilai_rujukan_pa").equals("")){
                                        pd=", PD : "+rs3.getString("nilai_rujukan_pd");
                                    }
                                    if(!rs3.getString("nilai_rujukan_pd").equals("")){
                                        pa=" PA : "+rs3.getString("nilai_rujukan_pa");
                                    }
                                    Sequel.menyimpan("temporary_permintaan_lab","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?",38,new String[]{
                                        "0","  "+rs3.getString("Pemeriksaan"),rs3.getString("satuan"),ld+la+pd+pa,"","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","",""
                                    });
                                }
                            } catch (Exception e) {
                                System.out.println("Notif 3 : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                                if(ps3!=null){
                                    ps3.close();
                                }
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Notif 2 : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Notif : "+e);
                }            
                Sequel.AutoComitTrue();
                Map<String, Object> param = new HashMap<>();
                param.put("noperiksa",tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString());
                norm=Sequel.cariIsi("select no_rkm_medis from reg_periksa where no_rawat=? ",tbDokter.getValueAt(tbDokter.getSelectedRow(),1).toString());
                param.put("norm",norm);
                param.put("pekerjaan",Sequel.cariIsi("select pekerjaan from pasien where no_rkm_medis=?",norm));
                param.put("noktp",Sequel.cariIsi("select no_ktp from pasien where no_rkm_medis=?",norm));
                param.put("namapasien",Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=? ",norm));
                param.put("jkel",Sequel.cariIsi("select jk from pasien where no_rkm_medis=? ",norm));
                param.put("umur",Sequel.cariIsi("select umur from pasien where no_rkm_medis=?",norm));
                param.put("lahir",Sequel.cariIsi("select DATE_FORMAT(tgl_lahir,'%d-%m-%Y') from pasien where no_rkm_medis=? ",norm));
                param.put("pengirim",tbDokter.getValueAt(tbDokter.getSelectedRow(),10).toString());
                param.put("tanggal",Valid.SetTgl3(tbDokter.getValueAt(tbDokter.getSelectedRow(),3).toString()));
                param.put("alamat",Sequel.cariIsi("select concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat from pasien inner join kelurahan inner join kecamatan inner join kabupaten on pasien.kd_kel=kelurahan.kd_kel and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab where no_rkm_medis=? ",norm));
                kamar=Sequel.cariIsi("select ifnull(kd_kamar,'') from kamar_inap where no_rawat=? order by tgl_masuk desc limit 1",tbDokter.getValueAt(tbDokter.getSelectedRow(),1).toString());
                if(!kamar.equals("")){
                    namakamar=kamar+", "+Sequel.cariIsi("select nm_bangsal from bangsal inner join kamar on bangsal.kd_bangsal=kamar.kd_bangsal "+
                            " where kamar.kd_kamar=? ",kamar);            
                    kamar="Kamar";  
                }else if(kamar.equals("")){
                    kamar="Poli";
                    namakamar=Sequel.cariIsi("select nm_poli from poliklinik inner join reg_periksa on poliklinik.kd_poli=reg_periksa.kd_poli "+
                            "where reg_periksa.no_rawat=?",tbDokter.getValueAt(tbDokter.getSelectedRow(),1).toString());
                }
                diagnosa=Sequel.cariIsi("select diagnosa_awal from kamar_inap where no_rawat=?",tbDokter.getValueAt(tbDokter.getSelectedRow(),1).toString());
                param.put("kamar",kamar);
                param.put("namakamar",namakamar);
                param.put("diagnosa",diagnosa);
                param.put("jam",tbDokter.getValueAt(tbDokter.getSelectedRow(),4).toString());
                param.put("namars",var.getnamars());
                param.put("alamatrs",var.getalamatrs());
                param.put("kotars",var.getkabupatenrs());
                param.put("propinsirs",var.getpropinsirs());
                param.put("kontakrs",var.getkontakrs());
                param.put("emailrs",var.getemailrs());   
                param.put("logo",Sequel.cariGambar("select logo from setting")); 

                Valid.MyReport("rptPermintaanLab.jrxml","report","::[ Permintaan Laboratorium ]::",
                    "select no, temp1, temp2, temp3, temp4, temp5, temp6, temp7, temp8, temp9, temp10, temp11, temp12, temp13, temp14, temp14, temp15, temp16 from temporary_permintaan_lab order by no asc",param);            
            }
            this.setCursor(Cursor.getDefaultCursor());
        }else{            
            JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data permintaan...!!!!");
            TCari.requestFocus();
        } 
    }//GEN-LAST:event_MnCetakHasilLabActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if(TabRawat.getSelectedIndex()==0){
            tampil();
        }else if(TabRawat.getSelectedIndex()==1){
            tampil2();
        }
    }//GEN-LAST:event_TabRawatMouseClicked

    private void tbDokter2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDokter2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbDokter2MouseClicked

    private void tbDokter2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbDokter2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbDokter2KeyPressed

    private void BtnSampelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSampelActionPerformed
        if(TabRawat.getSelectedIndex()==0){
            if(tbDokter.getSelectedRow()!= -1){
                if(tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString().trim().equals("")){
                    Valid.textKosong(TCari,"No.Permintaan");
                }else{ 
                    if(Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?",tbDokter.getValueAt(tbDokter.getSelectedRow(),1).toString())>0){
                        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                        DlgPeriksaLaboratorium dlgro=new DlgPeriksaLaboratorium(null,false);
                        dlgro.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                        dlgro.setLocationRelativeTo(internalFrame1);
                        dlgro.emptTeks();
                        dlgro.isCek();
                        dlgro.setNoRm(tbDokter.getValueAt(tbDokter.getSelectedRow(),1).toString(),"Ranap"); 
                        dlgro.setOrder(tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString());
                        dlgro.setVisible(true);
                        this.setCursor(Cursor.getDefaultCursor());
                    }else {
                        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                        DlgPeriksaLaboratorium dlgro=new DlgPeriksaLaboratorium(null,false);
                        dlgro.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                        dlgro.setLocationRelativeTo(internalFrame1);
                        dlgro.emptTeks();
                        dlgro.isCek();
                        dlgro.setNoRm(tbDokter.getValueAt(tbDokter.getSelectedRow(),1).toString(),"Ralan"); 
                        dlgro.setOrder(tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString());
                        dlgro.setVisible(true);
                        this.setCursor(Cursor.getDefaultCursor());
                    }
                }
            }else{            
                JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data permintaan...!!!!");
                TCari.requestFocus();
            } 
        }else if(TabRawat.getSelectedIndex()==1){
            JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih Data Permintaan...!!!!");
            TabRawat.setSelectedIndex(0);
            TCari.requestFocus();
        }
    }//GEN-LAST:event_BtnSampelActionPerformed

    private void BtnSampelKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSampelKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnSampelKeyPressed

    private void BtnHasilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHasilActionPerformed
        if(TabRawat.getSelectedIndex()==0){
            if(tbDokter.getSelectedRow()!= -1){
                if(tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString().trim().equals("")){
                    Valid.textKosong(TCari,"No.Permintaan");
                }else{ 
                    TanggalPulang.setDate(new Date());
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));        
                    WindowAmbilSampel.setLocationRelativeTo(internalFrame1);
                    WindowAmbilSampel.setVisible(true);
                    this.setCursor(Cursor.getDefaultCursor());
                }
            }else{            
                JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data permintaan...!!!!");
                TCari.requestFocus();
            }   
        }else if(TabRawat.getSelectedIndex()==1){
            JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih Data Permintaan...!!!!");
            TabRawat.setSelectedIndex(0);
            TCari.requestFocus();
        }            
    }//GEN-LAST:event_BtnHasilActionPerformed

    private void BtnHasilKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHasilKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnHasilKeyPressed

    private void MnBarcodePermintaanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnBarcodePermintaanActionPerformed
        if(tbDokter.getSelectedRow()!= -1){
            if(tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString().trim().equals("")){
                Valid.textKosong(TCari,"No.Permintaan");
            }else{ 
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                Map<String, Object> param = new HashMap<>();
                norm=Sequel.cariIsi("select no_rkm_medis from reg_periksa where no_rawat=? ",tbDokter.getValueAt(tbDokter.getSelectedRow(),1).toString());
                param.put("nama",Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=? ",norm));
                param.put("alamat",Sequel.cariIsi("select date_format(tgl_lahir,'%d/%m/%Y') from pasien where no_rkm_medis=?",norm));
                param.put("norm",norm);
                param.put("parameter","%"+TCari.getText().trim()+"%");     
                param.put("namars",var.getnamars());
                param.put("alamatrs",var.getalamatrs());
                param.put("kotars",var.getkabupatenrs());
                param.put("propinsirs",var.getpropinsirs());
                param.put("kontakrs",var.getkontakrs());
                param.put("emailrs",var.getemailrs());   
                Valid.MyReport("rptBarcodePermintaanLab.jrxml","report","::[ Barcode No.Permintaan Lab ]::",
                        "select noorder from permintaan_lab where no_rawat='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),1).toString()+"'",param); 
                this.setCursor(Cursor.getDefaultCursor());
            } 
        }else{            
            JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data permintaan...!!!!");
            TCari.requestFocus();
        } 
    }//GEN-LAST:event_MnBarcodePermintaanActionPerformed

    private void MnBarcodePermintaan1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnBarcodePermintaan1ActionPerformed
        if(tbDokter.getSelectedRow()!= -1){
            if(tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString().trim().equals("")){
                Valid.textKosong(TCari,"No.Permintaan");
            }else{ 
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                Map<String, Object> param = new HashMap<>();
                norm=Sequel.cariIsi("select no_rkm_medis from reg_periksa where no_rawat=? ",tbDokter.getValueAt(tbDokter.getSelectedRow(),1).toString());
                param.put("nama",Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=? ",norm));
                param.put("alamat",Sequel.cariIsi("select date_format(tgl_lahir,'%d/%m/%Y') from pasien where no_rkm_medis=?",norm));
                param.put("norm",norm);
                param.put("parameter","%"+TCari.getText().trim()+"%");     
                param.put("namars",var.getnamars());
                param.put("alamatrs",var.getalamatrs());
                param.put("kotars",var.getkabupatenrs());
                param.put("propinsirs",var.getpropinsirs());
                param.put("kontakrs",var.getkontakrs());
                param.put("emailrs",var.getemailrs());   
                Valid.MyReport("rptBarcodePermintaanLab2.jrxml","report","::[ Barcode No.Permintaan Lab ]::",
                        "select noorder from permintaan_lab where no_rawat='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),1).toString()+"'",param); 
                this.setCursor(Cursor.getDefaultCursor());
            } 
        }else{            
            JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data permintaan...!!!!");
            TCari.requestFocus();
        } 
    }//GEN-LAST:event_MnBarcodePermintaan1ActionPerformed

    private void BtnCloseIn4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCloseIn4ActionPerformed
        WindowAmbilSampel.dispose();
    }//GEN-LAST:event_BtnCloseIn4ActionPerformed

    private void BtnSimpan4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpan4ActionPerformed
        if(tbDokter.getSelectedRow()!= -1){
            if(tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString().trim().equals("")){
                Valid.textKosong(TanggalPulang,"No.Permintaan");
            }else{
                Sequel.mengedit("permintaan_lab","noorder=?","tgl_sampel=?,jam_sampel=?",3,new String[]{
                    Valid.SetTgl(TanggalPulang.getSelectedItem()+""),TanggalPulang.getSelectedItem().toString().substring(11,19),tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString()
                });
                tampil();
                WindowAmbilSampel.dispose();
            }
        }else{            
            JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data permintaan...!!!!");
            TCari.requestFocus();
        }  
    }//GEN-LAST:event_BtnSimpan4ActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgCariPermintaanLab dialog = new DlgCariPermintaanLab(new javax.swing.JFrame(), true);
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
    private widget.Button BtnCloseIn4;
    private widget.Button BtnHapus;
    private widget.Button BtnHasil;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSampel;
    private widget.Button BtnSimpan4;
    private widget.TextBox Kd2;
    private widget.Label LCount;
    private javax.swing.JMenuItem MnBarcodePermintaan;
    private javax.swing.JMenuItem MnBarcodePermintaan1;
    private javax.swing.JMenuItem MnCetakHasilLab;
    private widget.TextBox TCari;
    private javax.swing.JTabbedPane TabRawat;
    private widget.Tanggal TanggalPulang;
    private widget.Tanggal Tgl1;
    private widget.Tanggal Tgl2;
    private javax.swing.JDialog WindowAmbilSampel;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame5;
    private widget.Label jLabel10;
    private widget.Label jLabel26;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.Label label10;
    private widget.Label label11;
    private widget.Label label18;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelisi1;
    private widget.ScrollPane scrollPane1;
    private widget.ScrollPane scrollPane2;
    private widget.Table tbDokter;
    private widget.Table tbDokter2;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try {
            ps=koneksi.prepareStatement(
                    "select permintaan_lab.noorder,permintaan_lab.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,permintaan_lab.tgl_permintaan,"+
                    "if(permintaan_lab.jam_permintaan='00:00:00','',permintaan_lab.jam_permintaan) as jam_permintaan,"+
                    "permintaan_lab.tgl_sampel,if(permintaan_lab.jam_sampel='00:00:00','',permintaan_lab.jam_sampel) as jam_sampel,"+
                    "permintaan_lab.tgl_hasil,if(permintaan_lab.jam_hasil='00:00:00','',permintaan_lab.jam_hasil) as jam_hasil,"+
                    "permintaan_lab.dokter_perujuk,dokter.nm_dokter from permintaan_lab inner join reg_periksa inner join pasien inner join dokter "+
                    "on permintaan_lab.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "and permintaan_lab.dokter_perujuk=dokter.kd_dokter where "+
                    "permintaan_lab.tgl_permintaan between ? and ? and permintaan_lab.noorder like ? or "+
                    "permintaan_lab.tgl_permintaan between ? and ? and permintaan_lab.no_rawat like ? or "+
                    "permintaan_lab.tgl_permintaan between ? and ? and reg_periksa.no_rkm_medis like ? or "+
                    "permintaan_lab.tgl_permintaan between ? and ? and pasien.nm_pasien like ? or "+
                    "permintaan_lab.tgl_permintaan between ? and ? and dokter.nm_dokter like ? order by "+
                    "permintaan_lab.tgl_permintaan,permintaan_lab.jam_permintaan");
            try {
                ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(3,"%"+TCari.getText()+"%");
                ps.setString(4,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(5,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(6,"%"+TCari.getText()+"%");
                ps.setString(7,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(8,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(9,"%"+TCari.getText()+"%");
                ps.setString(10,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(11,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(12,"%"+TCari.getText()+"%");
                ps.setString(13,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(14,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(15,"%"+TCari.getText()+"%");
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new String[]{
                        rs.getString("noorder"),rs.getString("no_rawat"),rs.getString("no_rkm_medis")+" "+
                        rs.getString("nm_pasien"),rs.getString("tgl_permintaan"),rs.getString("jam_permintaan"),
                        rs.getString("tgl_sampel"),rs.getString("jam_sampel"),rs.getString("tgl_hasil"),
                        rs.getString("jam_hasil"),rs.getString("dokter_perujuk"),rs.getString("nm_dokter")
                    });
                    ps2=koneksi.prepareStatement(
                            "select permintaan_pemeriksaan_lab.kd_jenis_prw,jns_perawatan_lab.nm_perawatan "+
                            "from permintaan_pemeriksaan_lab inner join jns_perawatan_lab on "+
                            "permintaan_pemeriksaan_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw "+
                            "where permintaan_pemeriksaan_lab.noorder=?");
                    try {
                        ps2.setString(1,rs.getString("noorder"));
                        rs2=ps2.executeQuery();
                        while(rs2.next()){
                            tabMode.addRow(new Object[]{
                                "","",rs2.getString("nm_perawatan"),"","","","","","","",""
                            });
                            ps3=koneksi.prepareStatement(
                                    "select permintaan_detail_permintaan_lab.id_template,template_laboratorium.Pemeriksaan,"+
                                    "template_laboratorium.satuan,template_laboratorium.nilai_rujukan_ld,template_laboratorium.nilai_rujukan_la,"+
                                    "template_laboratorium.nilai_rujukan_pd,template_laboratorium.nilai_rujukan_pa from permintaan_detail_permintaan_lab "+
                                    "inner join template_laboratorium on permintaan_detail_permintaan_lab.id_template=template_laboratorium.id_template "+
                                    "where permintaan_detail_permintaan_lab.kd_jenis_prw=? and permintaan_detail_permintaan_lab.noorder=? order by template_laboratorium.urut");
                            try {
                                ps3.setString(1,rs2.getString("kd_jenis_prw"));
                                ps3.setString(2,rs.getString("noorder"));
                                rs3=ps3.executeQuery();
                                while(rs3.next()){
                                    tabMode.addRow(new Object[]{
                                        "","","  "+rs3.getString("Pemeriksaan"),rs3.getString("satuan"),"","","","","","",""
                                    });
                                }
                            } catch (Exception e) {
                                System.out.println("Notif 3 : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                                if(ps3!=null){
                                    ps3.close();
                                }
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Notif 2 : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                }
                rs.last();
                LCount.setText(""+rs.getRow());
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
    
    private void tampil2() {
        Valid.tabelKosong(tabMode2);  
        try {
            ps=koneksi.prepareStatement(
                    "select permintaan_lab.noorder,permintaan_lab.no_rawat,reg_periksa.no_rkm_medis,"+
                    "pasien.nm_pasien,jns_perawatan_lab.nm_perawatan,template_laboratorium.Pemeriksaan,"+
                    "template_laboratorium.satuan,template_laboratorium.nilai_rujukan_ld,"+
                    "template_laboratorium.nilai_rujukan_la,template_laboratorium.nilai_rujukan_pd,"+
                    "template_laboratorium.nilai_rujukan_pa,permintaan_lab.tgl_permintaan,"+
                    "if(permintaan_lab.jam_permintaan='00:00:00','',permintaan_lab.jam_permintaan) as jam_permintaan,permintaan_lab.tgl_sampel,"+
                    "if(permintaan_lab.jam_sampel='00:00:00','',permintaan_lab.jam_sampel) as jam_sampel, permintaan_lab.tgl_hasil,"+
                    "if(permintaan_lab.jam_hasil='00:00:00','',permintaan_lab.jam_hasil) as jam_hasil,"+
                    "permintaan_lab.dokter_perujuk,dokter.nm_dokter "+
                    "from permintaan_lab inner join reg_periksa inner join pasien inner join permintaan_pemeriksaan_lab "+
                    "inner join permintaan_detail_permintaan_lab inner join jns_perawatan_lab inner join template_laboratorium "+
                    "inner join dokter on permintaan_lab.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and "+
                    "permintaan_lab.noorder=permintaan_pemeriksaan_lab.noorder and jns_perawatan_lab.kd_jenis_prw=permintaan_pemeriksaan_lab.kd_jenis_prw "+
                    "and permintaan_lab.noorder=permintaan_detail_permintaan_lab.noorder and permintaan_lab.dokter_perujuk=dokter.kd_dokter "+
                    "and permintaan_detail_permintaan_lab.kd_jenis_prw=permintaan_pemeriksaan_lab.kd_jenis_prw and "+
                    "template_laboratorium.id_template=permintaan_detail_permintaan_lab.id_template where "+
                    "permintaan_lab.tgl_permintaan between ? and ? and permintaan_lab.noorder like ? or "+
                    "permintaan_lab.tgl_permintaan between ? and ? and permintaan_lab.no_rawat like ? or "+
                    "permintaan_lab.tgl_permintaan between ? and ? and reg_periksa.no_rkm_medis like ? or "+
                    "permintaan_lab.tgl_permintaan between ? and ? and pasien.nm_pasien like ? or "+
                    "permintaan_lab.tgl_permintaan between ? and ? and jns_perawatan_lab.nm_perawatan like ? or "+
                    "permintaan_lab.tgl_permintaan between ? and ? and template_laboratorium.Pemeriksaan like ? or "+
                    "permintaan_lab.tgl_permintaan between ? and ? and dokter.nm_dokter like ? order by "+
                    "permintaan_lab.tgl_permintaan,permintaan_lab.jam_permintaan");
            try {
                ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(3,"%"+TCari.getText()+"%");
                ps.setString(4,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(5,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(6,"%"+TCari.getText()+"%");
                ps.setString(7,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(8,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(9,"%"+TCari.getText()+"%");
                ps.setString(10,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(11,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(12,"%"+TCari.getText()+"%");
                ps.setString(13,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(14,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(15,"%"+TCari.getText()+"%");
                ps.setString(16,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(17,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(18,"%"+TCari.getText()+"%");
                ps.setString(19,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(20,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(21,"%"+TCari.getText()+"%");
                rs=ps.executeQuery();
                while(rs.next()){
                    la="";ld="";pa="";pd="";
                    if(!rs.getString("nilai_rujukan_ld").equals("")){
                        ld="LD : "+rs.getString("nilai_rujukan_ld");
                    }
                    if(!rs.getString("nilai_rujukan_la").equals("")){
                        la=", LA : "+rs.getString("nilai_rujukan_la");
                    }
                    if(!rs.getString("nilai_rujukan_pa").equals("")){
                        pd=", PD : "+rs.getString("nilai_rujukan_pd");
                    }
                    if(!rs.getString("nilai_rujukan_pd").equals("")){
                        pa=" PA : "+rs.getString("nilai_rujukan_pa");
                    }
                    tabMode2.addRow(new String[]{
                        rs.getString("noorder"),rs.getString("no_rawat"),rs.getString("no_rkm_medis")+" "+rs.getString("nm_pasien"),
                        rs.getString("nm_perawatan"),rs.getString("Pemeriksaan"),rs.getString("satuan"),ld+la+pd+pa,
                        rs.getString("tgl_permintaan"),rs.getString("jam_permintaan"),
                        rs.getString("tgl_sampel"),rs.getString("jam_sampel"),rs.getString("tgl_hasil"),
                        rs.getString("jam_hasil"),rs.getString("dokter_perujuk"),rs.getString("nm_dokter")
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
            LCount.setText(""+tabMode2.getRowCount());
        } catch (Exception e) {
            System.out.println("Notif : "+e);
        }            
    }
    
    
    private void getData() {
        Kd2.setText("");
        if(tbDokter.getSelectedRow()!= -1){
            Kd2.setText(tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString());
        }
    }
    
    public void isCek(){
        MnCetakHasilLab.setEnabled(var.getpermintaan_lab());
        BtnSampel.setEnabled(var.getpermintaan_lab());
        BtnHasil.setEnabled(var.getperiksa_lab());
        BtnHapus.setEnabled(var.getpermintaan_lab());
        BtnPrint.setEnabled(var.getpermintaan_lab());
    }
    
    public void setPasien(String pasien){
        TCari.setText(pasien);
    }

 
}

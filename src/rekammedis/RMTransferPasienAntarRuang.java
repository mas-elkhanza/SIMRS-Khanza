/*
 * Kontribusi dari M. Syukur RS. Jiwa Prov Sultra
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
import kepegawaian.DlgCariPetugas;


/**
 *
 * @author perpustakaan
 */
public final class RMTransferPasienAntarRuang extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0,pilihan=0;
    private DlgCariPetugas petugas;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private volatile boolean ceksukses = false;
    private StringBuilder htmlContent;
    
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public RMTransferPasienAntarRuang(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.RM","Nama Pasien","Tgl.Lahir","J.K.","Tanggal Masuk","Tanggal Pindah","Indikasi Pindah","Keterangan Indikasi Pindah",
            "Asal Ruang Rawat / Poliklinik","Ruang Rawat Selanjutnya","Metode Pemindahan","Diagnosa Utama","Diagnosa Sekunder","Prosedur Yang Sudah Dilakukan",
            "Obat Yang Telah Diberikan","Pemeriksaan Penunjang Yang Sudah Dilakukan","Peralatan Yang Menyertai","Keterangan Peralatan Menyertai",
            "Menyetujui Pemindahan","Nama Keluarga/Penanggung Jawab","Hubungan","Keadaan Umum SbT","TD SbT","Nadi SbT","RR SbT","Suhu Sbt",
            "Keluhan Utama Sebelum Transfer","Keadaan Umum StT","TD StT","Nadi StT","RR StT","Suhu Stt","Keluhan Utama Setelah Transfer","NIP Menyerahkan",
            "Petugas Yang Menyerahkan","NIP Menerima","Petugas Yang Menerima"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        
        tbObat.setModel(tabMode);
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 38; i++) {
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
                column.setPreferredWidth(55);
            }else if(i==5){
                column.setPreferredWidth(115);
            }else if(i==6){
                column.setPreferredWidth(115);
            }else if(i==7){
                column.setPreferredWidth(190);
            }else if(i==8){
                column.setPreferredWidth(140);
            }else if(i==9){
                column.setPreferredWidth(150);
            }else if(i==10){
                column.setPreferredWidth(150);
            }else if(i==11){
                column.setPreferredWidth(108);
            }else if(i==12){
                column.setPreferredWidth(160);
            }else if(i==13){
                column.setPreferredWidth(160);
            }else if(i==14){
                column.setPreferredWidth(200);
            }else if(i==15){
                column.setPreferredWidth(230);
            }else if(i==16){
                column.setPreferredWidth(236);
            }else if(i==17){
                column.setPreferredWidth(134);
            }else if(i==18){
                column.setPreferredWidth(165);
            }else if(i==19){
                column.setPreferredWidth(127);
            }else if(i==20){
                column.setPreferredWidth(180);
            }else if(i==21){
                column.setPreferredWidth(100);
            }else if(i==22){
                column.setPreferredWidth(107);
            }else if(i==23){
                column.setPreferredWidth(50);
            }else if(i==24){
                column.setPreferredWidth(50);
            }else if(i==25){
                column.setPreferredWidth(45);
            }else if(i==26){
                column.setPreferredWidth(50);
            }else if(i==27){
                column.setPreferredWidth(200);
            }else if(i==28){
                column.setPreferredWidth(107);
            }else if(i==29){
                column.setPreferredWidth(50);
            }else if(i==30){
                column.setPreferredWidth(50);
            }else if(i==31){
                column.setPreferredWidth(45);
            }else if(i==32){
                column.setPreferredWidth(50);
            }else if(i==33){
                column.setPreferredWidth(200);
            }else if(i==34){
                column.setPreferredWidth(95);
            }else if(i==35){
                column.setPreferredWidth(150);
            }else if(i==36){
                column.setPreferredWidth(95);
            }else if(i==37){
                column.setPreferredWidth(150);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());
        
        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        AsalRuang.setDocument(new batasInput((byte)30).getKata(AsalRuang));
        RuangSelanjutnya.setDocument(new batasInput((byte)30).getKata(RuangSelanjutnya));
        DiagnosaUtama.setDocument(new batasInput((int)50).getKata(DiagnosaUtama));
        DiagnosaSekunder.setDocument(new batasInput((int)100).getKata(DiagnosaSekunder));
        KeteranganIndikasiPindahRuang.setDocument(new batasInput((int)50).getKata(KeteranganIndikasiPindahRuang));
        ProsedurDilakukan.setDocument(new batasInput((int)800).getKata(ProsedurDilakukan));
        ObatYangDiberikan.setDocument(new batasInput((int)800).getKata(ObatYangDiberikan));
        KeteranganPeralatan.setDocument(new batasInput((int)50).getKata(KeteranganPeralatan));
        PemeriksaanPenunjang.setDocument(new batasInput((int)500).getKata(PemeriksaanPenunjang));
        NamaMenyetujui.setDocument(new batasInput((int)50).getKata(NamaMenyetujui));
        KeluhanUtamaSebelumTransfer.setDocument(new batasInput((int)200).getKata(KeluhanUtamaSebelumTransfer));
        TDSebelumTransfer.setDocument(new batasInput((int)7).getKata(TDSebelumTransfer));
        NadiSebelumTransfer.setDocument(new batasInput((int)5).getKata(NadiSebelumTransfer));
        RRSebelumTransfer.setDocument(new batasInput((int)5).getKata(RRSebelumTransfer));
        SuhuSebelumTransfer.setDocument(new batasInput((int)5).getKata(SuhuSebelumTransfer));
        KeluhanUtamaSetelahTransfer.setDocument(new batasInput((int)200).getKata(KeluhanUtamaSetelahTransfer));
        TDSetelahTransfer.setDocument(new batasInput((int)7).getKata(TDSetelahTransfer));
        NadiSetelahTransfer.setDocument(new batasInput((int)5).getKata(NadiSetelahTransfer));
        RRSetelahTransfer.setDocument(new batasInput((int)5).getKata(RRSetelahTransfer));
        SuhuSetelahTransfer.setDocument(new batasInput((int)5).getKata(SuhuSetelahTransfer));
        TCari.setDocument(new batasInput((int)100).getKata(TCari));
        
        HTMLEditorKit kit = new HTMLEditorKit();
        LoadHTML.setEditable(true);
        LoadHTML.setEditorKit(kit);
        LoadHTML2.setEditable(true);
        LoadHTML2.setEditorKit(kit);
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
        LoadHTML2.setDocument(doc);
        
        ChkAccor.setSelected(false);
        isPhoto();
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
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        BtnPrint = new widget.Button();
        BtnAll = new widget.Button();
        BtnKeluar = new widget.Button();
        TabRawat = new javax.swing.JTabbedPane();
        internalFrame2 = new widget.InternalFrame();
        scrollInput = new widget.ScrollPane();
        FormInput = new widget.PanelBiasa();
        jSeparator14 = new javax.swing.JSeparator();
        MenyetujuiPemindahan = new widget.ComboBox();
        jLabel57 = new widget.Label();
        TNoRw = new widget.TextBox();
        TPasien = new widget.TextBox();
        TNoRM = new widget.TextBox();
        jLabel8 = new widget.Label();
        TglLahir = new widget.TextBox();
        Jk = new widget.TextBox();
        jLabel10 = new widget.Label();
        label11 = new widget.Label();
        jLabel11 = new widget.Label();
        TanggalMasuk = new widget.Tanggal();
        label12 = new widget.Label();
        TanggalPindah = new widget.Tanggal();
        KeteranganIndikasiPindahRuang = new widget.TextBox();
        jSeparator1 = new javax.swing.JSeparator();
        KeteranganPeralatan = new widget.TextBox();
        jLabel13 = new widget.Label();
        RuangSelanjutnya = new widget.TextBox();
        jLabel14 = new widget.Label();
        jLabel15 = new widget.Label();
        PeralatanMenyertai = new widget.ComboBox();
        jLabel16 = new widget.Label();
        DiagnosaUtama = new widget.TextBox();
        jLabel18 = new widget.Label();
        jLabel20 = new widget.Label();
        jLabel30 = new widget.Label();
        scrollPane1 = new widget.ScrollPane();
        DiagnosaSekunder = new widget.TextArea();
        jLabel31 = new widget.Label();
        scrollPane2 = new widget.ScrollPane();
        ProsedurDilakukan = new widget.TextArea();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel32 = new widget.Label();
        jLabel33 = new widget.Label();
        scrollPane3 = new widget.ScrollPane();
        ObatYangDiberikan = new widget.TextArea();
        scrollPane4 = new widget.ScrollPane();
        PemeriksaanPenunjang = new widget.TextArea();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel34 = new widget.Label();
        IndikasiPindah = new widget.ComboBox();
        AsalRuang = new widget.TextBox();
        jLabel35 = new widget.Label();
        MetodePemindahan = new widget.ComboBox();
        jLabel36 = new widget.Label();
        jLabel37 = new widget.Label();
        NamaMenyetujui = new widget.TextBox();
        jLabel38 = new widget.Label();
        HubunganMenyetujui = new widget.ComboBox();
        jLabel39 = new widget.Label();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel40 = new widget.Label();
        jLabel41 = new widget.Label();
        scrollPane5 = new widget.ScrollPane();
        KeluhanUtamaSebelumTransfer = new widget.TextArea();
        jLabel42 = new widget.Label();
        KeadaanUmumSebelumTransfer = new widget.ComboBox();
        TDSebelumTransfer = new widget.TextBox();
        jLabel23 = new widget.Label();
        jLabel17 = new widget.Label();
        jLabel22 = new widget.Label();
        NadiSebelumTransfer = new widget.TextBox();
        jLabel26 = new widget.Label();
        RRSebelumTransfer = new widget.TextBox();
        jLabel25 = new widget.Label();
        jLabel24 = new widget.Label();
        SuhuSebelumTransfer = new widget.TextBox();
        jLabel27 = new widget.Label();
        jLabel28 = new widget.Label();
        jSeparator6 = new javax.swing.JSeparator();
        jLabel43 = new widget.Label();
        jLabel44 = new widget.Label();
        KeadaanUmumSetelahTransfer = new widget.ComboBox();
        jLabel29 = new widget.Label();
        TDSetelahTransfer = new widget.TextBox();
        jLabel45 = new widget.Label();
        jLabel46 = new widget.Label();
        scrollPane6 = new widget.ScrollPane();
        KeluhanUtamaSetelahTransfer = new widget.TextArea();
        jLabel47 = new widget.Label();
        NadiSetelahTransfer = new widget.TextBox();
        jLabel48 = new widget.Label();
        jLabel49 = new widget.Label();
        RRSetelahTransfer = new widget.TextBox();
        jLabel50 = new widget.Label();
        jLabel51 = new widget.Label();
        SuhuSetelahTransfer = new widget.TextBox();
        jLabel52 = new widget.Label();
        jSeparator7 = new javax.swing.JSeparator();
        label14 = new widget.Label();
        KdPetugasMenyerahkan = new widget.TextBox();
        NmPetugasMenyerahkan = new widget.TextBox();
        BtnDokter = new widget.Button();
        label15 = new widget.Label();
        KdPetugasMenerima = new widget.TextBox();
        NmPetugasMenerima = new widget.TextBox();
        BtnMenerima = new widget.Button();
        label16 = new widget.Label();
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
        FormPhoto = new widget.PanelBiasa();
        FormPass3 = new widget.PanelBiasa();
        btnAmbil = new widget.Button();
        BtnRefreshPhoto1 = new widget.Button();
        Scroll5 = new widget.ScrollPane();
        LoadHTML2 = new widget.editorpane();

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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Transfer Pasien Antar Ruang ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
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
        TabRawat.setForeground(new java.awt.Color(50, 50, 50));
        TabRawat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabRawat.setName("TabRawat"); // NOI18N
        TabRawat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatMouseClicked(evt);
            }
        });

        internalFrame2.setBorder(null);
        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setLayout(new java.awt.BorderLayout(1, 1));

        scrollInput.setName("scrollInput"); // NOI18N
        scrollInput.setPreferredSize(new java.awt.Dimension(102, 557));

        FormInput.setBackground(new java.awt.Color(255, 255, 255));
        FormInput.setBorder(null);
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(870, 663));
        FormInput.setLayout(null);

        jSeparator14.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator14.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator14.setName("jSeparator14"); // NOI18N
        FormInput.add(jSeparator14);
        jSeparator14.setBounds(0, 861, 880, 0);

        MenyetujuiPemindahan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        MenyetujuiPemindahan.setName("MenyetujuiPemindahan"); // NOI18N
        MenyetujuiPemindahan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MenyetujuiPemindahanKeyPressed(evt);
            }
        });
        FormInput.add(MenyetujuiPemindahan);
        MenyetujuiPemindahan.setBounds(775, 330, 80, 23);

        jLabel57.setText("Indikasi Pindah :");
        jLabel57.setName("jLabel57"); // NOI18N
        FormInput.add(jLabel57);
        jLabel57.setBounds(390, 40, 92, 23);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(74, 10, 131, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        FormInput.add(TPasien);
        TPasien.setBounds(309, 10, 260, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        FormInput.add(TNoRM);
        TNoRM.setBounds(207, 10, 100, 23);

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
        jLabel10.setBounds(0, 10, 70, 23);

        label11.setText("Masuk :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label11);
        label11.setBounds(0, 40, 70, 23);

        jLabel11.setText("J.K. :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(740, 10, 30, 23);

        TanggalMasuk.setForeground(new java.awt.Color(50, 70, 50));
        TanggalMasuk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "07-02-2026 13:32:18" }));
        TanggalMasuk.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        TanggalMasuk.setName("TanggalMasuk"); // NOI18N
        TanggalMasuk.setOpaque(false);
        TanggalMasuk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalMasukKeyPressed(evt);
            }
        });
        FormInput.add(TanggalMasuk);
        TanggalMasuk.setBounds(74, 40, 130, 23);

        label12.setText("Pindah :");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label12);
        label12.setBounds(201, 40, 55, 23);

        TanggalPindah.setForeground(new java.awt.Color(50, 70, 50));
        TanggalPindah.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "07-02-2026 13:32:18" }));
        TanggalPindah.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        TanggalPindah.setName("TanggalPindah"); // NOI18N
        TanggalPindah.setOpaque(false);
        TanggalPindah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalPindahKeyPressed(evt);
            }
        });
        FormInput.add(TanggalPindah);
        TanggalPindah.setBounds(260, 40, 130, 23);

        KeteranganIndikasiPindahRuang.setHighlighter(null);
        KeteranganIndikasiPindahRuang.setName("KeteranganIndikasiPindahRuang"); // NOI18N
        KeteranganIndikasiPindahRuang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganIndikasiPindahRuangKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganIndikasiPindahRuang);
        KeteranganIndikasiPindahRuang.setBounds(724, 40, 130, 23);

        jSeparator1.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator1.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator1.setName("jSeparator1"); // NOI18N
        FormInput.add(jSeparator1);
        jSeparator1.setBounds(0, 70, 880, 1);

        KeteranganPeralatan.setHighlighter(null);
        KeteranganPeralatan.setName("KeteranganPeralatan"); // NOI18N
        KeteranganPeralatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganPeralatanKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganPeralatan);
        KeteranganPeralatan.setBounds(288, 330, 160, 23);

        jLabel13.setText(":");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(91, 120, 10, 23);

        RuangSelanjutnya.setHighlighter(null);
        RuangSelanjutnya.setName("RuangSelanjutnya"); // NOI18N
        RuangSelanjutnya.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RuangSelanjutnyaKeyPressed(evt);
            }
        });
        FormInput.add(RuangSelanjutnya);
        RuangSelanjutnya.setBounds(468, 80, 150, 23);

        jLabel14.setText("Metode Pemindahan :");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(629, 80, 106, 23);

        jLabel15.setText("Ruang Rawat Selanjutnya :");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(324, 80, 140, 23);

        PeralatanMenyertai.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Oksigen Portable", "Infus", "NGT", "Syringe Pump", "Suction", "Kateter Urin", "Tidak Ada" }));
        PeralatanMenyertai.setName("PeralatanMenyertai"); // NOI18N
        PeralatanMenyertai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PeralatanMenyertaiKeyPressed(evt);
            }
        });
        FormInput.add(PeralatanMenyertai);
        PeralatanMenyertai.setBounds(150, 330, 135, 23);

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel16.setText("Diagnosa Utama");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput.add(jLabel16);
        jLabel16.setBounds(15, 120, 90, 23);

        DiagnosaUtama.setHighlighter(null);
        DiagnosaUtama.setName("DiagnosaUtama"); // NOI18N
        DiagnosaUtama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosaUtamaKeyPressed(evt);
            }
        });
        FormInput.add(DiagnosaUtama);
        DiagnosaUtama.setBounds(105, 120, 750, 23);

        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel18.setText("Asal Ruang Rawat / Poliklinik");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(15, 80, 149, 23);

        jLabel20.setText(":");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(155, 80, 10, 23);

        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel30.setText("Diagnosa Sekunder :");
        jLabel30.setName("jLabel30"); // NOI18N
        FormInput.add(jLabel30);
        jLabel30.setBounds(15, 150, 170, 23);

        scrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane1.setName("scrollPane1"); // NOI18N

        DiagnosaSekunder.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        DiagnosaSekunder.setColumns(20);
        DiagnosaSekunder.setRows(5);
        DiagnosaSekunder.setName("DiagnosaSekunder"); // NOI18N
        DiagnosaSekunder.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosaSekunderKeyPressed(evt);
            }
        });
        scrollPane1.setViewportView(DiagnosaSekunder);

        FormInput.add(scrollPane1);
        scrollPane1.setBounds(15, 170, 410, 43);

        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel31.setText("Prosedur Yang Sudah Dilakukan :");
        jLabel31.setName("jLabel31"); // NOI18N
        FormInput.add(jLabel31);
        jLabel31.setBounds(445, 150, 170, 23);

        scrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane2.setName("scrollPane2"); // NOI18N

        ProsedurDilakukan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        ProsedurDilakukan.setColumns(20);
        ProsedurDilakukan.setRows(5);
        ProsedurDilakukan.setName("ProsedurDilakukan"); // NOI18N
        ProsedurDilakukan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ProsedurDilakukanKeyPressed(evt);
            }
        });
        scrollPane2.setViewportView(ProsedurDilakukan);

        FormInput.add(scrollPane2);
        scrollPane2.setBounds(445, 170, 410, 43);

        jSeparator2.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator2.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator2.setName("jSeparator2"); // NOI18N
        FormInput.add(jSeparator2);
        jSeparator2.setBounds(0, 110, 880, 1);

        jSeparator3.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator3.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator3.setName("jSeparator3"); // NOI18N
        FormInput.add(jSeparator3);
        jSeparator3.setBounds(0, 220, 880, 1);

        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel32.setText("Obat Yang Telah Diberikan :");
        jLabel32.setName("jLabel32"); // NOI18N
        FormInput.add(jLabel32);
        jLabel32.setBounds(15, 220, 170, 23);

        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel33.setText("Pemeriksaan Penunjang Yang Sudah Dilakukan :");
        jLabel33.setName("jLabel33"); // NOI18N
        FormInput.add(jLabel33);
        jLabel33.setBounds(445, 220, 370, 23);

        scrollPane3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane3.setName("scrollPane3"); // NOI18N

        ObatYangDiberikan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        ObatYangDiberikan.setColumns(20);
        ObatYangDiberikan.setRows(5);
        ObatYangDiberikan.setName("ObatYangDiberikan"); // NOI18N
        ObatYangDiberikan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ObatYangDiberikanKeyPressed(evt);
            }
        });
        scrollPane3.setViewportView(ObatYangDiberikan);

        FormInput.add(scrollPane3);
        scrollPane3.setBounds(15, 240, 410, 73);

        scrollPane4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane4.setName("scrollPane4"); // NOI18N

        PemeriksaanPenunjang.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        PemeriksaanPenunjang.setColumns(20);
        PemeriksaanPenunjang.setRows(5);
        PemeriksaanPenunjang.setName("PemeriksaanPenunjang"); // NOI18N
        PemeriksaanPenunjang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PemeriksaanPenunjangKeyPressed(evt);
            }
        });
        scrollPane4.setViewportView(PemeriksaanPenunjang);

        FormInput.add(scrollPane4);
        scrollPane4.setBounds(445, 240, 410, 73);

        jSeparator4.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator4.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator4.setName("jSeparator4"); // NOI18N
        FormInput.add(jSeparator4);
        jSeparator4.setBounds(0, 320, 880, 1);

        jLabel34.setText("Pasien/Keluarga Mengetahui & Menyetujui Alasan Pemindahan :");
        jLabel34.setName("jLabel34"); // NOI18N
        FormInput.add(jLabel34);
        jLabel34.setBounds(451, 330, 320, 23);

        IndikasiPindah.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Kondisi Pasien Stabil", "Kondisi Pasien Tidak Ada Perubahan", "Kondisi Pasien Memburuk", "Fasilitas Kurang Memadai", "Fasilitas Butuh Lebih Baik", "Tenaga Membutuhkan Yang Lebih Ahli", "Tenaga Kurang", "Lain-lain" }));
        IndikasiPindah.setName("IndikasiPindah"); // NOI18N
        IndikasiPindah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                IndikasiPindahKeyPressed(evt);
            }
        });
        FormInput.add(IndikasiPindah);
        IndikasiPindah.setBounds(486, 40, 235, 23);

        AsalRuang.setHighlighter(null);
        AsalRuang.setName("AsalRuang"); // NOI18N
        AsalRuang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AsalRuangKeyPressed(evt);
            }
        });
        FormInput.add(AsalRuang);
        AsalRuang.setBounds(169, 80, 150, 23);

        jLabel35.setText(":");
        jLabel35.setName("jLabel35"); // NOI18N
        FormInput.add(jLabel35);
        jLabel35.setBounds(131, 330, 15, 23);

        MetodePemindahan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Kursi Roda", "Tempat Tidur", "Brankar", "Jalan Sendiri", "-" }));
        MetodePemindahan.setName("MetodePemindahan"); // NOI18N
        MetodePemindahan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MetodePemindahanKeyPressed(evt);
            }
        });
        FormInput.add(MetodePemindahan);
        MetodePemindahan.setBounds(739, 80, 115, 23);

        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel36.setText("Peralatan Yang Menyertai");
        jLabel36.setName("jLabel36"); // NOI18N
        FormInput.add(jLabel36);
        jLabel36.setBounds(15, 330, 130, 23);

        jLabel37.setText(":");
        jLabel37.setName("jLabel37"); // NOI18N
        FormInput.add(jLabel37);
        jLabel37.setBounds(374, 360, 20, 23);

        NamaMenyetujui.setHighlighter(null);
        NamaMenyetujui.setName("NamaMenyetujui"); // NOI18N
        NamaMenyetujui.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NamaMenyetujuiKeyPressed(evt);
            }
        });
        FormInput.add(NamaMenyetujui);
        NamaMenyetujui.setBounds(398, 360, 230, 23);

        jLabel38.setText("Hubungan :");
        jLabel38.setName("jLabel38"); // NOI18N
        FormInput.add(jLabel38);
        jLabel38.setBounds(621, 360, 80, 23);

        HubunganMenyetujui.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Kakak", "Adik", "Saudara", "Keluarga", "Kakek", "Nenek", "Orang Tua", "Suami", "Istri", "Penanggung Jawab", "Menantu", "Ipar", "Mertua", "-" }));
        HubunganMenyetujui.setName("HubunganMenyetujui"); // NOI18N
        HubunganMenyetujui.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HubunganMenyetujuiKeyPressed(evt);
            }
        });
        FormInput.add(HubunganMenyetujui);
        HubunganMenyetujui.setBounds(705, 360, 150, 23);

        jLabel39.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel39.setText("Bila Pemberi Persetujuan Adalah Keluarga/Penanggung Jawab Pasien, Nama");
        jLabel39.setName("jLabel39"); // NOI18N
        FormInput.add(jLabel39);
        jLabel39.setBounds(15, 360, 380, 23);

        jSeparator5.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator5.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator5.setName("jSeparator5"); // NOI18N
        FormInput.add(jSeparator5);
        jSeparator5.setBounds(0, 390, 880, 1);

        jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel40.setText("Keadaan Pasien Saat Pindah Sebelum Transfer :");
        jLabel40.setName("jLabel40"); // NOI18N
        FormInput.add(jLabel40);
        jLabel40.setBounds(15, 390, 320, 23);

        jLabel41.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel41.setText("Keluhan Utama :");
        jLabel41.setName("jLabel41"); // NOI18N
        FormInput.add(jLabel41);
        jLabel41.setBounds(445, 410, 170, 23);

        scrollPane5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane5.setName("scrollPane5"); // NOI18N

        KeluhanUtamaSebelumTransfer.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        KeluhanUtamaSebelumTransfer.setColumns(20);
        KeluhanUtamaSebelumTransfer.setRows(5);
        KeluhanUtamaSebelumTransfer.setName("KeluhanUtamaSebelumTransfer"); // NOI18N
        KeluhanUtamaSebelumTransfer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeluhanUtamaSebelumTransferKeyPressed(evt);
            }
        });
        scrollPane5.setViewportView(KeluhanUtamaSebelumTransfer);

        FormInput.add(scrollPane5);
        scrollPane5.setBounds(445, 430, 410, 63);

        jLabel42.setText("Keadaan Umum :");
        jLabel42.setName("jLabel42"); // NOI18N
        FormInput.add(jLabel42);
        jLabel42.setBounds(46, 410, 90, 23);

        KeadaanUmumSebelumTransfer.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Compos Mentis", "Gelisah", "Delirium", "Koma" }));
        KeadaanUmumSebelumTransfer.setName("KeadaanUmumSebelumTransfer"); // NOI18N
        KeadaanUmumSebelumTransfer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeadaanUmumSebelumTransferKeyPressed(evt);
            }
        });
        FormInput.add(KeadaanUmumSebelumTransfer);
        KeadaanUmumSebelumTransfer.setBounds(140, 410, 130, 23);

        TDSebelumTransfer.setFocusTraversalPolicyProvider(true);
        TDSebelumTransfer.setName("TDSebelumTransfer"); // NOI18N
        TDSebelumTransfer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TDSebelumTransferKeyPressed(evt);
            }
        });
        FormInput.add(TDSebelumTransfer);
        TDSebelumTransfer.setBounds(317, 410, 60, 23);

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel23.setText("mmHg");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(380, 410, 50, 23);

        jLabel17.setText("Nadi :");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(46, 440, 90, 23);

        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel22.setText("x/menit");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(203, 440, 50, 23);

        NadiSebelumTransfer.setFocusTraversalPolicyProvider(true);
        NadiSebelumTransfer.setName("NadiSebelumTransfer"); // NOI18N
        NadiSebelumTransfer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NadiSebelumTransferKeyPressed(evt);
            }
        });
        FormInput.add(NadiSebelumTransfer);
        NadiSebelumTransfer.setBounds(140, 440, 60, 23);

        jLabel26.setText("RR :");
        jLabel26.setName("jLabel26"); // NOI18N
        FormInput.add(jLabel26);
        jLabel26.setBounds(283, 440, 30, 23);

        RRSebelumTransfer.setFocusTraversalPolicyProvider(true);
        RRSebelumTransfer.setName("RRSebelumTransfer"); // NOI18N
        RRSebelumTransfer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RRSebelumTransferKeyPressed(evt);
            }
        });
        FormInput.add(RRSebelumTransfer);
        RRSebelumTransfer.setBounds(317, 440, 60, 23);

        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel25.setText("x/menit");
        jLabel25.setName("jLabel25"); // NOI18N
        FormInput.add(jLabel25);
        jLabel25.setBounds(380, 440, 50, 23);

        jLabel24.setText("Suhu :");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(46, 470, 90, 23);

        SuhuSebelumTransfer.setFocusTraversalPolicyProvider(true);
        SuhuSebelumTransfer.setName("SuhuSebelumTransfer"); // NOI18N
        SuhuSebelumTransfer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SuhuSebelumTransferKeyPressed(evt);
            }
        });
        FormInput.add(SuhuSebelumTransfer);
        SuhuSebelumTransfer.setBounds(140, 470, 60, 23);

        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel27.setText("°C");
        jLabel27.setName("jLabel27"); // NOI18N
        FormInput.add(jLabel27);
        jLabel27.setBounds(203, 470, 30, 23);

        jLabel28.setText("TD :");
        jLabel28.setName("jLabel28"); // NOI18N
        FormInput.add(jLabel28);
        jLabel28.setBounds(283, 410, 30, 23);

        jSeparator6.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator6.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator6.setName("jSeparator6"); // NOI18N
        FormInput.add(jSeparator6);
        jSeparator6.setBounds(0, 500, 880, 1);

        jLabel43.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel43.setText("Keadaan Pasien Saat Pindah Setelah Transfer :");
        jLabel43.setName("jLabel43"); // NOI18N
        FormInput.add(jLabel43);
        jLabel43.setBounds(15, 500, 320, 23);

        jLabel44.setText("Keadaan Umum :");
        jLabel44.setName("jLabel44"); // NOI18N
        FormInput.add(jLabel44);
        jLabel44.setBounds(46, 520, 90, 23);

        KeadaanUmumSetelahTransfer.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Compos Mentis", "Gelisah", "Delirium", "Koma" }));
        KeadaanUmumSetelahTransfer.setName("KeadaanUmumSetelahTransfer"); // NOI18N
        KeadaanUmumSetelahTransfer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeadaanUmumSetelahTransferKeyPressed(evt);
            }
        });
        FormInput.add(KeadaanUmumSetelahTransfer);
        KeadaanUmumSetelahTransfer.setBounds(140, 520, 130, 23);

        jLabel29.setText("TD :");
        jLabel29.setName("jLabel29"); // NOI18N
        FormInput.add(jLabel29);
        jLabel29.setBounds(283, 520, 30, 23);

        TDSetelahTransfer.setFocusTraversalPolicyProvider(true);
        TDSetelahTransfer.setName("TDSetelahTransfer"); // NOI18N
        TDSetelahTransfer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TDSetelahTransferKeyPressed(evt);
            }
        });
        FormInput.add(TDSetelahTransfer);
        TDSetelahTransfer.setBounds(317, 520, 60, 23);

        jLabel45.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel45.setText("mmHg");
        jLabel45.setName("jLabel45"); // NOI18N
        FormInput.add(jLabel45);
        jLabel45.setBounds(380, 520, 50, 23);

        jLabel46.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel46.setText("Keluhan Utama :");
        jLabel46.setName("jLabel46"); // NOI18N
        FormInput.add(jLabel46);
        jLabel46.setBounds(445, 520, 170, 23);

        scrollPane6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane6.setName("scrollPane6"); // NOI18N

        KeluhanUtamaSetelahTransfer.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        KeluhanUtamaSetelahTransfer.setColumns(20);
        KeluhanUtamaSetelahTransfer.setRows(5);
        KeluhanUtamaSetelahTransfer.setName("KeluhanUtamaSetelahTransfer"); // NOI18N
        KeluhanUtamaSetelahTransfer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeluhanUtamaSetelahTransferKeyPressed(evt);
            }
        });
        scrollPane6.setViewportView(KeluhanUtamaSetelahTransfer);

        FormInput.add(scrollPane6);
        scrollPane6.setBounds(445, 540, 410, 63);

        jLabel47.setText("Nadi :");
        jLabel47.setName("jLabel47"); // NOI18N
        FormInput.add(jLabel47);
        jLabel47.setBounds(46, 550, 90, 23);

        NadiSetelahTransfer.setFocusTraversalPolicyProvider(true);
        NadiSetelahTransfer.setName("NadiSetelahTransfer"); // NOI18N
        NadiSetelahTransfer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NadiSetelahTransferKeyPressed(evt);
            }
        });
        FormInput.add(NadiSetelahTransfer);
        NadiSetelahTransfer.setBounds(140, 550, 60, 23);

        jLabel48.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel48.setText("x/menit");
        jLabel48.setName("jLabel48"); // NOI18N
        FormInput.add(jLabel48);
        jLabel48.setBounds(203, 550, 50, 23);

        jLabel49.setText("RR :");
        jLabel49.setName("jLabel49"); // NOI18N
        FormInput.add(jLabel49);
        jLabel49.setBounds(283, 550, 30, 23);

        RRSetelahTransfer.setFocusTraversalPolicyProvider(true);
        RRSetelahTransfer.setName("RRSetelahTransfer"); // NOI18N
        RRSetelahTransfer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RRSetelahTransferKeyPressed(evt);
            }
        });
        FormInput.add(RRSetelahTransfer);
        RRSetelahTransfer.setBounds(317, 550, 60, 23);

        jLabel50.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel50.setText("x/menit");
        jLabel50.setName("jLabel50"); // NOI18N
        FormInput.add(jLabel50);
        jLabel50.setBounds(380, 550, 50, 23);

        jLabel51.setText("Suhu :");
        jLabel51.setName("jLabel51"); // NOI18N
        FormInput.add(jLabel51);
        jLabel51.setBounds(46, 580, 90, 23);

        SuhuSetelahTransfer.setFocusTraversalPolicyProvider(true);
        SuhuSetelahTransfer.setName("SuhuSetelahTransfer"); // NOI18N
        SuhuSetelahTransfer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SuhuSetelahTransferKeyPressed(evt);
            }
        });
        FormInput.add(SuhuSetelahTransfer);
        SuhuSetelahTransfer.setBounds(140, 580, 60, 23);

        jLabel52.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel52.setText("°C");
        jLabel52.setName("jLabel52"); // NOI18N
        FormInput.add(jLabel52);
        jLabel52.setBounds(203, 580, 30, 23);

        jSeparator7.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator7.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator7.setName("jSeparator7"); // NOI18N
        FormInput.add(jSeparator7);
        jSeparator7.setBounds(0, 610, 880, 1);

        label14.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label14.setText("Petugas / Perawat :");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label14);
        label14.setBounds(15, 610, 130, 23);

        KdPetugasMenyerahkan.setEditable(false);
        KdPetugasMenyerahkan.setName("KdPetugasMenyerahkan"); // NOI18N
        KdPetugasMenyerahkan.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput.add(KdPetugasMenyerahkan);
        KdPetugasMenyerahkan.setBounds(140, 630, 100, 23);

        NmPetugasMenyerahkan.setEditable(false);
        NmPetugasMenyerahkan.setName("NmPetugasMenyerahkan"); // NOI18N
        NmPetugasMenyerahkan.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmPetugasMenyerahkan);
        NmPetugasMenyerahkan.setBounds(242, 630, 180, 23);

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
        BtnDokter.setBounds(424, 630, 28, 23);

        label15.setText("Menerima :");
        label15.setName("label15"); // NOI18N
        label15.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label15);
        label15.setBounds(469, 630, 70, 23);

        KdPetugasMenerima.setEditable(false);
        KdPetugasMenerima.setName("KdPetugasMenerima"); // NOI18N
        KdPetugasMenerima.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput.add(KdPetugasMenerima);
        KdPetugasMenerima.setBounds(543, 630, 100, 23);

        NmPetugasMenerima.setEditable(false);
        NmPetugasMenerima.setName("NmPetugasMenerima"); // NOI18N
        NmPetugasMenerima.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmPetugasMenerima);
        NmPetugasMenerima.setBounds(645, 630, 180, 23);

        BtnMenerima.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnMenerima.setMnemonic('2');
        BtnMenerima.setToolTipText("Alt+2");
        BtnMenerima.setName("BtnMenerima"); // NOI18N
        BtnMenerima.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnMenerima.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnMenerimaActionPerformed(evt);
            }
        });
        BtnMenerima.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnMenerimaKeyPressed(evt);
            }
        });
        FormInput.add(BtnMenerima);
        BtnMenerima.setBounds(827, 630, 28, 23);

        label16.setText("Menyerahkan :");
        label16.setName("label16"); // NOI18N
        label16.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label16);
        label16.setBounds(46, 630, 90, 23);

        scrollInput.setViewportView(FormInput);

        internalFrame2.add(scrollInput, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Input Transfer Pasien", internalFrame2);

        internalFrame3.setBorder(null);
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setLayout(new java.awt.BorderLayout(1, 1));

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

        internalFrame3.add(Scroll, java.awt.BorderLayout.CENTER);

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel19.setText("Tgl.Pindah :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(68, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "07-02-2026" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "07-02-2026" }));
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
        TCari.setPreferredSize(new java.awt.Dimension(197, 23));
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
        FormPhoto.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1), " Bukti Pengambilan Persetujuan : ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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

        FormPhoto.add(FormPass3, java.awt.BorderLayout.PAGE_END);

        Scroll5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll5.setName("Scroll5"); // NOI18N
        Scroll5.setOpaque(true);
        Scroll5.setPreferredSize(new java.awt.Dimension(200, 200));

        LoadHTML2.setBorder(null);
        LoadHTML2.setName("LoadHTML2"); // NOI18N
        Scroll5.setViewportView(LoadHTML2);

        FormPhoto.add(Scroll5, java.awt.BorderLayout.CENTER);

        PanelAccor.add(FormPhoto, java.awt.BorderLayout.CENTER);

        internalFrame3.add(PanelAccor, java.awt.BorderLayout.EAST);

        TabRawat.addTab("Data Transfer Pasien", internalFrame3);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TNoRM.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"Nama Pasien");
        }else if(NmPetugasMenyerahkan.getText().trim().equals("")){
            Valid.textKosong(BtnDokter,"Petugas Yang Menyerahkan");
        }else if(NmPetugasMenerima.getText().trim().equals("")){
            Valid.textKosong(BtnDokter,"Petugas Yang Menerima");
        }else if(AsalRuang.getText().trim().equals("")){
            Valid.textKosong(BtnMenerima,"Petugas Yang Menerima");
        }else if(AsalRuang.getText().trim().equals("")){
            Valid.textKosong(AsalRuang,"Asal Ruang");
        }else if(RuangSelanjutnya.getText().trim().equals("")){
            Valid.textKosong(RuangSelanjutnya,"Ruang Selanjutnya");
        }else if(DiagnosaUtama.getText().trim().equals("")){
            Valid.textKosong(DiagnosaUtama,"Diagnosa Utama");
        }else if(ProsedurDilakukan.getText().trim().equals("")){
            Valid.textKosong(ProsedurDilakukan,"Prosedur Dilakukan");
        }else if(ObatYangDiberikan.getText().trim().equals("")){
            Valid.textKosong(ObatYangDiberikan,"Obat Yang Diberikan");
        }else if(TDSebelumTransfer.getText().trim().equals("")){
            Valid.textKosong(TDSebelumTransfer,"TD Sebelum Transfer");
        }else if(NadiSebelumTransfer.getText().trim().equals("")){
            Valid.textKosong(NadiSebelumTransfer,"Nadi Sebelum Transfer");
        }else if(RRSebelumTransfer.getText().trim().equals("")){
            Valid.textKosong(RRSebelumTransfer,"RR Sebelum Transfer");
        }else if(SuhuSebelumTransfer.getText().trim().equals("")){
            Valid.textKosong(SuhuSebelumTransfer,"Suhu Sebelum Transfer");
        }else if(KeluhanUtamaSebelumTransfer.getText().trim().equals("")){
            Valid.textKosong(KeluhanUtamaSebelumTransfer,"Keluhan Utama Sebelum Transfer");
        }else if(TDSetelahTransfer.getText().trim().equals("")){
            Valid.textKosong(TDSetelahTransfer,"TD Setelah Transfer");
        }else if(NadiSetelahTransfer.getText().trim().equals("")){
            Valid.textKosong(NadiSetelahTransfer,"Nadi Setelah Transfer");
        }else if(RRSetelahTransfer.getText().trim().equals("")){
            Valid.textKosong(RRSetelahTransfer,"RR Setelah Transfer");
        }else if(SuhuSetelahTransfer.getText().trim().equals("")){
            Valid.textKosong(SuhuSetelahTransfer,"Suhu Setelah Transfer");
        }else if(KeluhanUtamaSetelahTransfer.getText().trim().equals("")){
            Valid.textKosong(KeluhanUtamaSetelahTransfer,"Keluhan Utama Setelah Transfer");
        }else{
            if(akses.getkode().equals("Admin Utama")){
                simpan();
            }else {
                if(akses.getkode().equals(KdPetugasMenerima.getText())||akses.getkode().equals(KdPetugasMenyerahkan.getText())){
                    simpan();
                }else{
                    JOptionPane.showMessageDialog(null,"Harus salah satu petugas sesuai user login..!!");
                }
            }
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,BtnMenerima,BtnBatal);
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
            }else {
                if(akses.getkode().equals(tbObat.getValueAt(tbObat.getSelectedRow(),34).toString())||akses.getkode().equals(tbObat.getValueAt(tbObat.getSelectedRow(),36).toString())){
                    hapus();
                }else{
                    JOptionPane.showMessageDialog(null,"Harus salah satu petugas sesuai user login..!!");
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
        }else if(NmPetugasMenyerahkan.getText().trim().equals("")){
            Valid.textKosong(BtnDokter,"Petugas Yang Menyerahkan");
        }else if(NmPetugasMenerima.getText().trim().equals("")){
            Valid.textKosong(BtnDokter,"Petugas Yang Menerima");
        }else if(AsalRuang.getText().trim().equals("")){
            Valid.textKosong(BtnMenerima,"Petugas Yang Menerima");
        }else if(AsalRuang.getText().trim().equals("")){
            Valid.textKosong(AsalRuang,"Asal Ruang");
        }else if(RuangSelanjutnya.getText().trim().equals("")){
            Valid.textKosong(RuangSelanjutnya,"Ruang Selanjutnya");
        }else if(DiagnosaUtama.getText().trim().equals("")){
            Valid.textKosong(DiagnosaUtama,"Diagnosa Utama");
        }else if(ProsedurDilakukan.getText().trim().equals("")){
            Valid.textKosong(ProsedurDilakukan,"Prosedur Dilakukan");
        }else if(ObatYangDiberikan.getText().trim().equals("")){
            Valid.textKosong(ObatYangDiberikan,"Obat Yang Diberikan");
        }else if(TDSebelumTransfer.getText().trim().equals("")){
            Valid.textKosong(TDSebelumTransfer,"TD Sebelum Transfer");
        }else if(NadiSebelumTransfer.getText().trim().equals("")){
            Valid.textKosong(NadiSebelumTransfer,"Nadi Sebelum Transfer");
        }else if(RRSebelumTransfer.getText().trim().equals("")){
            Valid.textKosong(RRSebelumTransfer,"RR Sebelum Transfer");
        }else if(SuhuSebelumTransfer.getText().trim().equals("")){
            Valid.textKosong(SuhuSebelumTransfer,"Suhu Sebelum Transfer");
        }else if(KeluhanUtamaSebelumTransfer.getText().trim().equals("")){
            Valid.textKosong(KeluhanUtamaSebelumTransfer,"Keluhan Utama Sebelum Transfer");
        }else if(TDSetelahTransfer.getText().trim().equals("")){
            Valid.textKosong(TDSetelahTransfer,"TD Setelah Transfer");
        }else if(NadiSetelahTransfer.getText().trim().equals("")){
            Valid.textKosong(NadiSetelahTransfer,"Nadi Setelah Transfer");
        }else if(RRSetelahTransfer.getText().trim().equals("")){
            Valid.textKosong(RRSetelahTransfer,"RR Setelah Transfer");
        }else if(SuhuSetelahTransfer.getText().trim().equals("")){
            Valid.textKosong(SuhuSetelahTransfer,"Suhu Setelah Transfer");
        }else if(KeluhanUtamaSetelahTransfer.getText().trim().equals("")){
            Valid.textKosong(KeluhanUtamaSetelahTransfer,"Keluhan Utama Setelah Transfer");
        }else{
            if(tbObat.getSelectedRow()>-1){
                if(akses.getkode().equals("Admin Utama")){
                    ganti();
                }else {
                    if(akses.getkode().equals(tbObat.getValueAt(tbObat.getSelectedRow(),34).toString())||akses.getkode().equals(tbObat.getValueAt(tbObat.getSelectedRow(),36).toString())){
                        ganti();
                    }else{
                        JOptionPane.showMessageDialog(null,"Harus salah satu petugas sesuai user login..!!");
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
                            "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,"+
                            "transfer_pasien_antar_ruang.tanggal_masuk,transfer_pasien_antar_ruang.tanggal_pindah,transfer_pasien_antar_ruang.asal_ruang,"+
                            "transfer_pasien_antar_ruang.ruang_selanjutnya,transfer_pasien_antar_ruang.diagnosa_utama,transfer_pasien_antar_ruang.diagnosa_sekunder,"+
                            "transfer_pasien_antar_ruang.indikasi_pindah_ruang,transfer_pasien_antar_ruang.keterangan_indikasi_pindah_ruang,"+
                            "transfer_pasien_antar_ruang.prosedur_yang_sudah_dilakukan,transfer_pasien_antar_ruang.obat_yang_telah_diberikan,"+
                            "transfer_pasien_antar_ruang.metode_pemindahan_pasien,transfer_pasien_antar_ruang.peralatan_yang_menyertai,"+
                            "transfer_pasien_antar_ruang.keterangan_peralatan_yang_menyertai,transfer_pasien_antar_ruang.pemeriksaan_penunjang_yang_dilakukan,"+
                            "transfer_pasien_antar_ruang.pasien_keluarga_menyetujui,transfer_pasien_antar_ruang.nama_menyetujui,transfer_pasien_antar_ruang.hubungan_menyetujui,"+
                            "transfer_pasien_antar_ruang.keluhan_utama_sebelum_transfer,transfer_pasien_antar_ruang.keadaan_umum_sebelum_transfer,"+
                            "transfer_pasien_antar_ruang.td_sebelum_transfer,transfer_pasien_antar_ruang.nadi_sebelum_transfer,transfer_pasien_antar_ruang.rr_sebelum_transfer,"+
                            "transfer_pasien_antar_ruang.suhu_sebelum_transfer,transfer_pasien_antar_ruang.keluhan_utama_sesudah_transfer,"+
                            "transfer_pasien_antar_ruang.keadaan_umum_sesudah_transfer,transfer_pasien_antar_ruang.td_sesudah_transfer,"+
                            "transfer_pasien_antar_ruang.nadi_sesudah_transfer,transfer_pasien_antar_ruang.rr_sesudah_transfer,transfer_pasien_antar_ruang.suhu_sesudah_transfer,"+
                            "transfer_pasien_antar_ruang.nip_menyerahkan,petugasmenyerahkan.nama as petugasmenyerahkan,transfer_pasien_antar_ruang.nip_menerima,"+
                            "petugasmenerima.nama as petugasmenerima "+
                            "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                            "inner join transfer_pasien_antar_ruang on reg_periksa.no_rawat=transfer_pasien_antar_ruang.no_rawat "+
                            "inner join petugas as petugasmenyerahkan on transfer_pasien_antar_ruang.nip_menyerahkan=petugasmenyerahkan.nip "+
                            "inner join petugas as petugasmenerima on transfer_pasien_antar_ruang.nip_menerima=petugasmenerima.nip where "+
                            "transfer_pasien_antar_ruang.tanggal_pindah between ? and ? order by transfer_pasien_antar_ruang.tanggal_pindah");
                }else{
                    ps=koneksi.prepareStatement(
                            "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,"+
                            "transfer_pasien_antar_ruang.tanggal_masuk,transfer_pasien_antar_ruang.tanggal_pindah,transfer_pasien_antar_ruang.asal_ruang,"+
                            "transfer_pasien_antar_ruang.ruang_selanjutnya,transfer_pasien_antar_ruang.diagnosa_utama,transfer_pasien_antar_ruang.diagnosa_sekunder,"+
                            "transfer_pasien_antar_ruang.indikasi_pindah_ruang,transfer_pasien_antar_ruang.keterangan_indikasi_pindah_ruang,"+
                            "transfer_pasien_antar_ruang.prosedur_yang_sudah_dilakukan,transfer_pasien_antar_ruang.obat_yang_telah_diberikan,"+
                            "transfer_pasien_antar_ruang.metode_pemindahan_pasien,transfer_pasien_antar_ruang.peralatan_yang_menyertai,"+
                            "transfer_pasien_antar_ruang.keterangan_peralatan_yang_menyertai,transfer_pasien_antar_ruang.pemeriksaan_penunjang_yang_dilakukan,"+
                            "transfer_pasien_antar_ruang.pasien_keluarga_menyetujui,transfer_pasien_antar_ruang.nama_menyetujui,transfer_pasien_antar_ruang.hubungan_menyetujui,"+
                            "transfer_pasien_antar_ruang.keluhan_utama_sebelum_transfer,transfer_pasien_antar_ruang.keadaan_umum_sebelum_transfer,"+
                            "transfer_pasien_antar_ruang.td_sebelum_transfer,transfer_pasien_antar_ruang.nadi_sebelum_transfer,transfer_pasien_antar_ruang.rr_sebelum_transfer,"+
                            "transfer_pasien_antar_ruang.suhu_sebelum_transfer,transfer_pasien_antar_ruang.keluhan_utama_sesudah_transfer,"+
                            "transfer_pasien_antar_ruang.keadaan_umum_sesudah_transfer,transfer_pasien_antar_ruang.td_sesudah_transfer,"+
                            "transfer_pasien_antar_ruang.nadi_sesudah_transfer,transfer_pasien_antar_ruang.rr_sesudah_transfer,transfer_pasien_antar_ruang.suhu_sesudah_transfer,"+
                            "transfer_pasien_antar_ruang.nip_menyerahkan,petugasmenyerahkan.nama as petugasmenyerahkan,transfer_pasien_antar_ruang.nip_menerima,"+
                            "petugasmenerima.nama as petugasmenerima "+
                            "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                            "inner join transfer_pasien_antar_ruang on reg_periksa.no_rawat=transfer_pasien_antar_ruang.no_rawat "+
                            "inner join petugas as petugasmenyerahkan on transfer_pasien_antar_ruang.nip_menyerahkan=petugasmenyerahkan.nip "+
                            "inner join petugas as petugasmenerima on transfer_pasien_antar_ruang.nip_menerima=petugasmenerima.nip where "+
                            "transfer_pasien_antar_ruang.tanggal_pindah between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or "+
                            "transfer_pasien_antar_ruang.nip_menyerahkan like ? or petugasmenyerahkan.nama like ?) order by transfer_pasien_antar_ruang.tanggal_pindah");
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
                    htmlContent = new StringBuilder();
                    htmlContent.append(                             
                        "<tr class='isi'>"+
                           "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>No.Rawat</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>No.RM</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Nama Pasien</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Tgl.Lahir</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>J.K.</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Tanggal Masuk</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Tanggal Pindah</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Indikasi Pindah</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Keterangan Indikasi Pindah</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Asal Ruang Rawat / Poliklinik</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Ruang Rawat Selanjutnya</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Metode Pemindahan</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Diagnosa Utama</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Diagnosa Sekunder</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Prosedur Yang Sudah Dilakukan</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Obat Yang Telah Diberikan</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Pemeriksaan Penunjang Yang Sudah Dilakukan</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Peralatan Yang Menyertai</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Keterangan Peralatan Menyertai</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Menyetujui Pemindahan</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Nama Keluarga/Penanggung Jawab</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Hubungan</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Keadaan Umum SbT</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>TD SbT</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Nadi SbT</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>RR SbT</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Suhu Sbt</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Keluhan Utama Sebelum Transfer</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Keadaan Umum StT</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>TD StT</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Nadi StT</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>RR StT</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Suhu Stt</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Keluhan Utama Setelah Transfer</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>NIP Menyerahkan</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Petugas Yang Menyerahkan</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>NIP Menerima</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Petugas Yang Menerima</b></td>"+
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
                               "<td valign='top'>"+rs.getString("tanggal_masuk")+"</td>"+
                               "<td valign='top'>"+rs.getString("tanggal_pindah")+"</td>"+
                               "<td valign='top'>"+rs.getString("indikasi_pindah_ruang")+"</td>"+
                               "<td valign='top'>"+rs.getString("keterangan_indikasi_pindah_ruang")+"</td>"+
                               "<td valign='top'>"+rs.getString("asal_ruang")+"</td>"+
                               "<td valign='top'>"+rs.getString("ruang_selanjutnya")+"</td>"+
                               "<td valign='top'>"+rs.getString("metode_pemindahan_pasien")+"</td>"+
                               "<td valign='top'>"+rs.getString("diagnosa_utama")+"</td>"+
                               "<td valign='top'>"+rs.getString("diagnosa_sekunder")+"</td>"+
                               "<td valign='top'>"+rs.getString("prosedur_yang_sudah_dilakukan")+"</td>"+
                               "<td valign='top'>"+rs.getString("obat_yang_telah_diberikan")+"</td>"+
                               "<td valign='top'>"+rs.getString("pemeriksaan_penunjang_yang_dilakukan")+"</td>"+
                               "<td valign='top'>"+rs.getString("peralatan_yang_menyertai")+"</td>"+
                               "<td valign='top'>"+rs.getString("keterangan_peralatan_yang_menyertai")+"</td>"+
                               "<td valign='top'>"+rs.getString("pasien_keluarga_menyetujui")+"</td>"+
                               "<td valign='top'>"+rs.getString("nama_menyetujui")+"</td>"+
                               "<td valign='top'>"+rs.getString("hubungan_menyetujui")+"</td>"+
                               "<td valign='top'>"+rs.getString("keadaan_umum_sebelum_transfer")+"</td>"+
                               "<td valign='top'>"+rs.getString("td_sebelum_transfer")+"</td>"+
                               "<td valign='top'>"+rs.getString("nadi_sebelum_transfer")+"</td>"+
                               "<td valign='top'>"+rs.getString("rr_sebelum_transfer")+"</td>"+
                               "<td valign='top'>"+rs.getString("suhu_sebelum_transfer")+"</td>"+
                               "<td valign='top'>"+rs.getString("keluhan_utama_sebelum_transfer")+"</td>"+
                               "<td valign='top'>"+rs.getString("keadaan_umum_sesudah_transfer")+"</td>"+
                               "<td valign='top'>"+rs.getString("td_sesudah_transfer")+"</td>"+
                               "<td valign='top'>"+rs.getString("nadi_sesudah_transfer")+"</td>"+
                               "<td valign='top'>"+rs.getString("rr_sesudah_transfer")+"</td>"+
                               "<td valign='top'>"+rs.getString("suhu_sesudah_transfer")+"</td>"+
                               "<td valign='top'>"+rs.getString("keluhan_utama_sesudah_transfer")+"</td>"+
                               "<td valign='top'>"+rs.getString("nip_menyerahkan")+"</td>"+
                               "<td valign='top'>"+rs.getString("petugasmenyerahkan")+"</td>"+
                               "<td valign='top'>"+rs.getString("nip_menerima")+"</td>"+
                               "<td valign='top'>"+rs.getString("petugasmenerima")+"</td>"+
                            "</tr>");
                    }
                    LoadHTML.setText(
                        "<html>"+
                          "<table width='4000' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
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

                    File f = new File("TransferPasienAntarRuang.html");            
                    BufferedWriter bw = new BufferedWriter(new FileWriter(f));            
                    bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                                "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                                "<table width='4000px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                    "<tr class='isi2'>"+
                                        "<td valign='top' align='center'>"+
                                            "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                            akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                            akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                            "<font size='2' face='Tahoma'>DATA TRANSFER PASIEN ANTAR RUANG<br><br></font>"+        
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
            TCari.setText("");
            runBackground(() ->tampil());
        }else{
            Valid.pindah(evt, BtnCari, TPasien);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void tbObatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbObatMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                isPhoto();
                panggilPhoto();
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
                    getData();
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

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if(TabRawat.getSelectedIndex()==1){
            runBackground(() ->tampil());
        }
    }//GEN-LAST:event_TabRawatMouseClicked

    private void MenyetujuiPemindahanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MenyetujuiPemindahanKeyPressed
        Valid.pindah(evt,KeteranganPeralatan,NamaMenyetujui);
    }//GEN-LAST:event_MenyetujuiPemindahanKeyPressed

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isRawat();
        }else{
            //Valid.pindah(evt,TCari,BtnDokter);
        }
    }//GEN-LAST:event_TNoRwKeyPressed

    private void TanggalMasukKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalMasukKeyPressed
        Valid.pindah2(evt,TNoRw,TanggalPindah);
    }//GEN-LAST:event_TanggalMasukKeyPressed

    private void TanggalPindahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalPindahKeyPressed
        Valid.pindah2(evt,TanggalMasuk,IndikasiPindah);
    }//GEN-LAST:event_TanggalPindahKeyPressed

    private void KeteranganIndikasiPindahRuangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganIndikasiPindahRuangKeyPressed
        Valid.pindah(evt,IndikasiPindah,AsalRuang);
    }//GEN-LAST:event_KeteranganIndikasiPindahRuangKeyPressed

    private void KeteranganPeralatanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganPeralatanKeyPressed
        Valid.pindah(evt,PeralatanMenyertai,MenyetujuiPemindahan);
    }//GEN-LAST:event_KeteranganPeralatanKeyPressed

    private void RuangSelanjutnyaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RuangSelanjutnyaKeyPressed
        Valid.pindah(evt,AsalRuang,MetodePemindahan);
    }//GEN-LAST:event_RuangSelanjutnyaKeyPressed

    private void PeralatanMenyertaiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PeralatanMenyertaiKeyPressed
        Valid.pindah(evt,PemeriksaanPenunjang,KeteranganPeralatan);
    }//GEN-LAST:event_PeralatanMenyertaiKeyPressed

    private void DiagnosaSekunderKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosaSekunderKeyPressed
        Valid.pindah2(evt,DiagnosaUtama,ProsedurDilakukan);
    }//GEN-LAST:event_DiagnosaSekunderKeyPressed

    private void DiagnosaUtamaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosaUtamaKeyPressed
        Valid.pindah(evt,MetodePemindahan,DiagnosaSekunder);
    }//GEN-LAST:event_DiagnosaUtamaKeyPressed

    private void ProsedurDilakukanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ProsedurDilakukanKeyPressed
        Valid.pindah2(evt,DiagnosaSekunder,ObatYangDiberikan);
    }//GEN-LAST:event_ProsedurDilakukanKeyPressed

    private void ObatYangDiberikanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ObatYangDiberikanKeyPressed
        Valid.pindah2(evt,ProsedurDilakukan,PemeriksaanPenunjang);
    }//GEN-LAST:event_ObatYangDiberikanKeyPressed

    private void PemeriksaanPenunjangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PemeriksaanPenunjangKeyPressed
        Valid.pindah2(evt,ObatYangDiberikan,PeralatanMenyertai);
    }//GEN-LAST:event_PemeriksaanPenunjangKeyPressed

    private void IndikasiPindahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_IndikasiPindahKeyPressed
        Valid.pindah(evt,TanggalPindah,KeteranganIndikasiPindahRuang);
    }//GEN-LAST:event_IndikasiPindahKeyPressed

    private void AsalRuangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AsalRuangKeyPressed
        Valid.pindah(evt,KeteranganIndikasiPindahRuang,RuangSelanjutnya);
    }//GEN-LAST:event_AsalRuangKeyPressed

    private void MetodePemindahanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MetodePemindahanKeyPressed
        Valid.pindah(evt,RuangSelanjutnya,DiagnosaUtama);
    }//GEN-LAST:event_MetodePemindahanKeyPressed

    private void NamaMenyetujuiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NamaMenyetujuiKeyPressed
        Valid.pindah(evt,MenyetujuiPemindahan,HubunganMenyetujui);
    }//GEN-LAST:event_NamaMenyetujuiKeyPressed

    private void HubunganMenyetujuiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HubunganMenyetujuiKeyPressed
        Valid.pindah(evt,NamaMenyetujui,KeadaanUmumSebelumTransfer);
    }//GEN-LAST:event_HubunganMenyetujuiKeyPressed

    private void KeluhanUtamaSebelumTransferKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeluhanUtamaSebelumTransferKeyPressed
        Valid.pindah2(evt,SuhuSebelumTransfer,KeadaanUmumSetelahTransfer);
    }//GEN-LAST:event_KeluhanUtamaSebelumTransferKeyPressed

    private void KeadaanUmumSebelumTransferKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeadaanUmumSebelumTransferKeyPressed
        Valid.pindah(evt,HubunganMenyetujui,TDSebelumTransfer);
    }//GEN-LAST:event_KeadaanUmumSebelumTransferKeyPressed

    private void TDSebelumTransferKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TDSebelumTransferKeyPressed
        Valid.pindah(evt,KeadaanUmumSebelumTransfer,NadiSebelumTransfer);
    }//GEN-LAST:event_TDSebelumTransferKeyPressed

    private void NadiSebelumTransferKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NadiSebelumTransferKeyPressed
        Valid.pindah(evt,TDSebelumTransfer,RRSebelumTransfer);
    }//GEN-LAST:event_NadiSebelumTransferKeyPressed

    private void RRSebelumTransferKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RRSebelumTransferKeyPressed
        Valid.pindah(evt,NadiSebelumTransfer,SuhuSebelumTransfer);
    }//GEN-LAST:event_RRSebelumTransferKeyPressed

    private void SuhuSebelumTransferKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SuhuSebelumTransferKeyPressed
        Valid.pindah(evt,RRSebelumTransfer,KeluhanUtamaSebelumTransfer);
    }//GEN-LAST:event_SuhuSebelumTransferKeyPressed

    private void KeadaanUmumSetelahTransferKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeadaanUmumSetelahTransferKeyPressed
        Valid.pindah(evt,KeluhanUtamaSebelumTransfer,TDSetelahTransfer);
    }//GEN-LAST:event_KeadaanUmumSetelahTransferKeyPressed

    private void TDSetelahTransferKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TDSetelahTransferKeyPressed
        Valid.pindah(evt,KeadaanUmumSetelahTransfer,NadiSetelahTransfer);
    }//GEN-LAST:event_TDSetelahTransferKeyPressed

    private void KeluhanUtamaSetelahTransferKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeluhanUtamaSetelahTransferKeyPressed
        Valid.pindah2(evt,SuhuSetelahTransfer,BtnDokter);
    }//GEN-LAST:event_KeluhanUtamaSetelahTransferKeyPressed

    private void NadiSetelahTransferKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NadiSetelahTransferKeyPressed
        Valid.pindah(evt,TDSetelahTransfer,RRSetelahTransfer);
    }//GEN-LAST:event_NadiSetelahTransferKeyPressed

    private void RRSetelahTransferKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RRSetelahTransferKeyPressed
        Valid.pindah(evt,NadiSetelahTransfer,SuhuSetelahTransfer);
    }//GEN-LAST:event_RRSetelahTransferKeyPressed

    private void SuhuSetelahTransferKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SuhuSetelahTransferKeyPressed
        Valid.pindah(evt,RRSetelahTransfer,KeluhanUtamaSetelahTransfer);
    }//GEN-LAST:event_SuhuSetelahTransferKeyPressed

    private void BtnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokterActionPerformed
        if (petugas == null || !petugas.isDisplayable()) {
            petugas=new DlgCariPetugas(null,false);
            petugas.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            petugas.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    if(petugas.getTable().getSelectedRow()!= -1){
                        KdPetugasMenyerahkan.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                        NmPetugasMenyerahkan.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                        KdPetugasMenyerahkan.requestFocus();
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
    }//GEN-LAST:event_BtnDokterActionPerformed

    private void BtnDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokterKeyPressed
        Valid.pindah(evt,KeluhanUtamaSetelahTransfer,BtnMenerima);
    }//GEN-LAST:event_BtnDokterKeyPressed

    private void BtnMenerimaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnMenerimaActionPerformed
        pilihan=2;
        if (petugas == null || !petugas.isDisplayable()) {
            petugas=new DlgCariPetugas(null,false);
            petugas.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            petugas.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    if(petugas.getTable().getSelectedRow()!= -1){
                        KdPetugasMenerima.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                        NmPetugasMenerima.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                        KdPetugasMenerima.requestFocus();
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
    }//GEN-LAST:event_BtnMenerimaActionPerformed

    private void BtnMenerimaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnMenerimaKeyPressed
        Valid.pindah(evt,KeluhanUtamaSetelahTransfer,BtnSimpan);
    }//GEN-LAST:event_BtnMenerimaKeyPressed

    private void ChkAccorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkAccorActionPerformed
        if(tbObat.getSelectedRow()!= -1){
            isPhoto();
            panggilPhoto();
        }else{
            ChkAccor.setSelected(false);
            JOptionPane.showMessageDialog(null,"Silahkan pilih No.Pernyataan..!!!");
        }
    }//GEN-LAST:event_ChkAccorActionPerformed

    private void btnAmbilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAmbilActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        }else{
            if(tbObat.getSelectedRow()>-1){
                Sequel.queryu("delete from antripersetujuantransferantarruang");
                Sequel.queryu("insert into antripersetujuantransferantarruang values('"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"','"+tbObat.getValueAt(tbObat.getSelectedRow(),5).toString()+"')");
                Sequel.queryu("delete from bukti_persetujuan_transfer_pasien_antar_ruang where no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"' and tanggal_masuk='"+tbObat.getValueAt(tbObat.getSelectedRow(),5).toString()+"'");
            }else{
                JOptionPane.showMessageDialog(rootPane,"Silahkan anda pilih data terlebih dahulu..!!");
            }
        }
    }//GEN-LAST:event_btnAmbilActionPerformed

    private void BtnRefreshPhoto1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRefreshPhoto1ActionPerformed
        if(tbObat.getSelectedRow()>-1){
            panggilPhoto();
        }else{
            JOptionPane.showMessageDialog(rootPane,"Silahkan anda pilih No.Pernyataan terlebih dahulu..!!");
        }
    }//GEN-LAST:event_BtnRefreshPhoto1ActionPerformed

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
            RMTransferPasienAntarRuang dialog = new RMTransferPasienAntarRuang(new javax.swing.JFrame(), true);
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
    private widget.TextBox AsalRuang;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnDokter;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnMenerima;
    private widget.Button BtnPrint;
    private widget.Button BtnRefreshPhoto1;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkAccor;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.TextArea DiagnosaSekunder;
    private widget.TextBox DiagnosaUtama;
    private widget.PanelBiasa FormInput;
    private widget.PanelBiasa FormPass3;
    private widget.PanelBiasa FormPhoto;
    private widget.ComboBox HubunganMenyetujui;
    private widget.ComboBox IndikasiPindah;
    private widget.TextBox Jk;
    private widget.TextBox KdPetugasMenerima;
    private widget.TextBox KdPetugasMenyerahkan;
    private widget.ComboBox KeadaanUmumSebelumTransfer;
    private widget.ComboBox KeadaanUmumSetelahTransfer;
    private widget.TextArea KeluhanUtamaSebelumTransfer;
    private widget.TextArea KeluhanUtamaSetelahTransfer;
    private widget.TextBox KeteranganIndikasiPindahRuang;
    private widget.TextBox KeteranganPeralatan;
    private widget.Label LCount;
    private widget.editorpane LoadHTML;
    private widget.editorpane LoadHTML2;
    private widget.ComboBox MenyetujuiPemindahan;
    private widget.ComboBox MetodePemindahan;
    private widget.TextBox NadiSebelumTransfer;
    private widget.TextBox NadiSetelahTransfer;
    private widget.TextBox NamaMenyetujui;
    private widget.TextBox NmPetugasMenerima;
    private widget.TextBox NmPetugasMenyerahkan;
    private widget.TextArea ObatYangDiberikan;
    private widget.PanelBiasa PanelAccor;
    private widget.TextArea PemeriksaanPenunjang;
    private widget.ComboBox PeralatanMenyertai;
    private widget.TextArea ProsedurDilakukan;
    private widget.TextBox RRSebelumTransfer;
    private widget.TextBox RRSetelahTransfer;
    private widget.TextBox RuangSelanjutnya;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll5;
    private widget.TextBox SuhuSebelumTransfer;
    private widget.TextBox SuhuSetelahTransfer;
    private widget.TextBox TCari;
    private widget.TextBox TDSebelumTransfer;
    private widget.TextBox TDSetelahTransfer;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private javax.swing.JTabbedPane TabRawat;
    private widget.Tanggal TanggalMasuk;
    private widget.Tanggal TanggalPindah;
    private widget.TextBox TglLahir;
    private widget.Button btnAmbil;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
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
    private widget.Label jLabel46;
    private widget.Label jLabel47;
    private widget.Label jLabel48;
    private widget.Label jLabel49;
    private widget.Label jLabel50;
    private widget.Label jLabel51;
    private widget.Label jLabel52;
    private widget.Label jLabel57;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private widget.Label label11;
    private widget.Label label12;
    private widget.Label label14;
    private widget.Label label15;
    private widget.Label label16;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollInput;
    private widget.ScrollPane scrollPane1;
    private widget.ScrollPane scrollPane2;
    private widget.ScrollPane scrollPane3;
    private widget.ScrollPane scrollPane4;
    private widget.ScrollPane scrollPane5;
    private widget.ScrollPane scrollPane6;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            if(TCari.getText().trim().equals("")){
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,"+
                        "transfer_pasien_antar_ruang.tanggal_masuk,transfer_pasien_antar_ruang.tanggal_pindah,transfer_pasien_antar_ruang.asal_ruang,"+
                        "transfer_pasien_antar_ruang.ruang_selanjutnya,transfer_pasien_antar_ruang.diagnosa_utama,transfer_pasien_antar_ruang.diagnosa_sekunder,"+
                        "transfer_pasien_antar_ruang.indikasi_pindah_ruang,transfer_pasien_antar_ruang.keterangan_indikasi_pindah_ruang,"+
                        "transfer_pasien_antar_ruang.prosedur_yang_sudah_dilakukan,transfer_pasien_antar_ruang.obat_yang_telah_diberikan,"+
                        "transfer_pasien_antar_ruang.metode_pemindahan_pasien,transfer_pasien_antar_ruang.peralatan_yang_menyertai,"+
                        "transfer_pasien_antar_ruang.keterangan_peralatan_yang_menyertai,transfer_pasien_antar_ruang.pemeriksaan_penunjang_yang_dilakukan,"+
                        "transfer_pasien_antar_ruang.pasien_keluarga_menyetujui,transfer_pasien_antar_ruang.nama_menyetujui,transfer_pasien_antar_ruang.hubungan_menyetujui,"+
                        "transfer_pasien_antar_ruang.keluhan_utama_sebelum_transfer,transfer_pasien_antar_ruang.keadaan_umum_sebelum_transfer,"+
                        "transfer_pasien_antar_ruang.td_sebelum_transfer,transfer_pasien_antar_ruang.nadi_sebelum_transfer,transfer_pasien_antar_ruang.rr_sebelum_transfer,"+
                        "transfer_pasien_antar_ruang.suhu_sebelum_transfer,transfer_pasien_antar_ruang.keluhan_utama_sesudah_transfer,"+
                        "transfer_pasien_antar_ruang.keadaan_umum_sesudah_transfer,transfer_pasien_antar_ruang.td_sesudah_transfer,"+
                        "transfer_pasien_antar_ruang.nadi_sesudah_transfer,transfer_pasien_antar_ruang.rr_sesudah_transfer,transfer_pasien_antar_ruang.suhu_sesudah_transfer,"+
                        "transfer_pasien_antar_ruang.nip_menyerahkan,petugasmenyerahkan.nama as petugasmenyerahkan,transfer_pasien_antar_ruang.nip_menerima,"+
                        "petugasmenerima.nama as petugasmenerima "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join transfer_pasien_antar_ruang on reg_periksa.no_rawat=transfer_pasien_antar_ruang.no_rawat "+
                        "inner join petugas as petugasmenyerahkan on transfer_pasien_antar_ruang.nip_menyerahkan=petugasmenyerahkan.nip "+
                        "inner join petugas as petugasmenerima on transfer_pasien_antar_ruang.nip_menerima=petugasmenerima.nip where "+
                        "transfer_pasien_antar_ruang.tanggal_pindah between ? and ? order by transfer_pasien_antar_ruang.tanggal_pindah");
            }else{
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,"+
                        "transfer_pasien_antar_ruang.tanggal_masuk,transfer_pasien_antar_ruang.tanggal_pindah,transfer_pasien_antar_ruang.asal_ruang,"+
                        "transfer_pasien_antar_ruang.ruang_selanjutnya,transfer_pasien_antar_ruang.diagnosa_utama,transfer_pasien_antar_ruang.diagnosa_sekunder,"+
                        "transfer_pasien_antar_ruang.indikasi_pindah_ruang,transfer_pasien_antar_ruang.keterangan_indikasi_pindah_ruang,"+
                        "transfer_pasien_antar_ruang.prosedur_yang_sudah_dilakukan,transfer_pasien_antar_ruang.obat_yang_telah_diberikan,"+
                        "transfer_pasien_antar_ruang.metode_pemindahan_pasien,transfer_pasien_antar_ruang.peralatan_yang_menyertai,"+
                        "transfer_pasien_antar_ruang.keterangan_peralatan_yang_menyertai,transfer_pasien_antar_ruang.pemeriksaan_penunjang_yang_dilakukan,"+
                        "transfer_pasien_antar_ruang.pasien_keluarga_menyetujui,transfer_pasien_antar_ruang.nama_menyetujui,transfer_pasien_antar_ruang.hubungan_menyetujui,"+
                        "transfer_pasien_antar_ruang.keluhan_utama_sebelum_transfer,transfer_pasien_antar_ruang.keadaan_umum_sebelum_transfer,"+
                        "transfer_pasien_antar_ruang.td_sebelum_transfer,transfer_pasien_antar_ruang.nadi_sebelum_transfer,transfer_pasien_antar_ruang.rr_sebelum_transfer,"+
                        "transfer_pasien_antar_ruang.suhu_sebelum_transfer,transfer_pasien_antar_ruang.keluhan_utama_sesudah_transfer,"+
                        "transfer_pasien_antar_ruang.keadaan_umum_sesudah_transfer,transfer_pasien_antar_ruang.td_sesudah_transfer,"+
                        "transfer_pasien_antar_ruang.nadi_sesudah_transfer,transfer_pasien_antar_ruang.rr_sesudah_transfer,transfer_pasien_antar_ruang.suhu_sesudah_transfer,"+
                        "transfer_pasien_antar_ruang.nip_menyerahkan,petugasmenyerahkan.nama as petugasmenyerahkan,transfer_pasien_antar_ruang.nip_menerima,"+
                        "petugasmenerima.nama as petugasmenerima "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join transfer_pasien_antar_ruang on reg_periksa.no_rawat=transfer_pasien_antar_ruang.no_rawat "+
                        "inner join petugas as petugasmenyerahkan on transfer_pasien_antar_ruang.nip_menyerahkan=petugasmenyerahkan.nip "+
                        "inner join petugas as petugasmenerima on transfer_pasien_antar_ruang.nip_menerima=petugasmenerima.nip where "+
                        "transfer_pasien_antar_ruang.tanggal_pindah between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or "+
                        "transfer_pasien_antar_ruang.nip_menyerahkan like ? or petugasmenyerahkan.nama like ?) order by transfer_pasien_antar_ruang.tanggal_pindah");
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
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getDate("tgl_lahir"),rs.getString("jk"),rs.getString("tanggal_masuk"),
                        rs.getString("tanggal_pindah"),rs.getString("indikasi_pindah_ruang"),rs.getString("keterangan_indikasi_pindah_ruang"),rs.getString("asal_ruang"),rs.getString("ruang_selanjutnya"),
                        rs.getString("metode_pemindahan_pasien"),rs.getString("diagnosa_utama"),rs.getString("diagnosa_sekunder"),rs.getString("prosedur_yang_sudah_dilakukan"),rs.getString("obat_yang_telah_diberikan"),
                        rs.getString("pemeriksaan_penunjang_yang_dilakukan"),rs.getString("peralatan_yang_menyertai"),rs.getString("keterangan_peralatan_yang_menyertai"),rs.getString("pasien_keluarga_menyetujui"),
                        rs.getString("nama_menyetujui"),rs.getString("hubungan_menyetujui"),rs.getString("keadaan_umum_sebelum_transfer"),rs.getString("td_sebelum_transfer"),rs.getString("nadi_sebelum_transfer"),
                        rs.getString("rr_sebelum_transfer"),rs.getString("suhu_sebelum_transfer"),rs.getString("keluhan_utama_sebelum_transfer"),rs.getString("keadaan_umum_sesudah_transfer"),
                        rs.getString("td_sesudah_transfer"),rs.getString("nadi_sesudah_transfer"),rs.getString("rr_sesudah_transfer"),rs.getString("suhu_sesudah_transfer"),rs.getString("keluhan_utama_sesudah_transfer"),
                        rs.getString("nip_menyerahkan"),rs.getString("petugasmenyerahkan"),rs.getString("nip_menerima"),rs.getString("petugasmenerima")
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
        TanggalMasuk.setDate(new Date());
        TanggalPindah.setDate(new Date());
        IndikasiPindah.setSelectedIndex(0);
        KeteranganIndikasiPindahRuang.setText("");
        AsalRuang.setText("");
        RuangSelanjutnya.setText("");
        MetodePemindahan.setSelectedIndex(0);
        DiagnosaUtama.setText("");
        DiagnosaSekunder.setText("");
        ProsedurDilakukan.setText("");
        ObatYangDiberikan.setText("");
        PemeriksaanPenunjang.setText("");
        PeralatanMenyertai.setSelectedIndex(0);
        KeteranganPeralatan.setText("");
        MenyetujuiPemindahan.setSelectedIndex(0);
        NamaMenyetujui.setText("");
        HubunganMenyetujui.setSelectedIndex(0);
        KeadaanUmumSebelumTransfer.setSelectedIndex(0);
        TDSebelumTransfer.setText("");
        NadiSebelumTransfer.setText("");
        RRSebelumTransfer.setText("");
        SuhuSebelumTransfer.setText("");
        KeluhanUtamaSebelumTransfer.setText("");
        KeadaanUmumSetelahTransfer.setSelectedIndex(0);
        TDSetelahTransfer.setText("");
        NadiSetelahTransfer.setText("");
        RRSetelahTransfer.setText("");
        SuhuSetelahTransfer.setText("");
        KeluhanUtamaSetelahTransfer.setText("");
        TabRawat.setSelectedIndex(0);
        IndikasiPindah.requestFocus();
    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()); 
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            Jk.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString()); 
            IndikasiPindah.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString()); 
            KeteranganIndikasiPindahRuang.setText(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            AsalRuang.setText(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            RuangSelanjutnya.setText(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString()); 
            MetodePemindahan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString()); 
            DiagnosaUtama.setText(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString()); 
            DiagnosaSekunder.setText(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString()); 
            ProsedurDilakukan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString()); 
            ObatYangDiberikan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString()); 
            PemeriksaanPenunjang.setText(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString()); 
            PeralatanMenyertai.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString()); 
            KeteranganPeralatan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString()); 
            MenyetujuiPemindahan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
            NamaMenyetujui.setText(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString());  
            HubunganMenyetujui.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString());
            KeadaanUmumSebelumTransfer.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString());
            TDSebelumTransfer.setText(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString()); 
            NadiSebelumTransfer.setText(tbObat.getValueAt(tbObat.getSelectedRow(),24).toString()); 
            RRSebelumTransfer.setText(tbObat.getValueAt(tbObat.getSelectedRow(),25).toString()); 
            SuhuSebelumTransfer.setText(tbObat.getValueAt(tbObat.getSelectedRow(),26).toString()); 
            KeluhanUtamaSebelumTransfer.setText(tbObat.getValueAt(tbObat.getSelectedRow(),27).toString()); 
            KeadaanUmumSetelahTransfer.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),28).toString());
            TDSetelahTransfer.setText(tbObat.getValueAt(tbObat.getSelectedRow(),29).toString()); 
            NadiSetelahTransfer.setText(tbObat.getValueAt(tbObat.getSelectedRow(),30).toString()); 
            RRSetelahTransfer.setText(tbObat.getValueAt(tbObat.getSelectedRow(),31).toString()); 
            SuhuSetelahTransfer.setText(tbObat.getValueAt(tbObat.getSelectedRow(),32).toString()); 
            KeluhanUtamaSetelahTransfer.setText(tbObat.getValueAt(tbObat.getSelectedRow(),33).toString()); 
            KdPetugasMenyerahkan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),34).toString()); 
            NmPetugasMenyerahkan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),35).toString()); 
            KdPetugasMenerima.setText(tbObat.getValueAt(tbObat.getSelectedRow(),36).toString()); 
            NmPetugasMenerima.setText(tbObat.getValueAt(tbObat.getSelectedRow(),37).toString()); 
            
            Valid.SetTgl2(TanggalMasuk,tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
            Valid.SetTgl2(TanggalPindah,tbObat.getValueAt(tbObat.getSelectedRow(),6).toString());
        }
    }

    private void isRawat() {
        try {
            ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rkm_medis,pasien.nm_pasien, if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,reg_periksa.tgl_registrasi "+
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
    
    public void isCek(){
        BtnSimpan.setEnabled(akses.gettransfer_pasien_antar_ruang());
        BtnHapus.setEnabled(akses.gettransfer_pasien_antar_ruang());
        BtnEdit.setEnabled(akses.gettransfer_pasien_antar_ruang());
        BtnPrint.setEnabled(akses.gettransfer_pasien_antar_ruang());
    }
    
    public void setTampil(){
       TabRawat.setSelectedIndex(1);
    }

    private void hapus() {
        if(Sequel.queryu2tf("delete from transfer_pasien_antar_ruang where no_rawat=? and tanggal_masuk=?",2,new String[]{
            tbObat.getValueAt(tbObat.getSelectedRow(),0).toString(),tbObat.getValueAt(tbObat.getSelectedRow(),5).toString()
        })==true){
            tabMode.removeRow(tbObat.getSelectedRow());
            LCount.setText(""+tabMode.getRowCount());
            TabRawat.setSelectedIndex(1);
        }else{
            JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
        }
    }

    private void ganti() {
        if(Sequel.mengedittf("transfer_pasien_antar_ruang","no_rawat=? and tanggal_masuk=?","no_rawat=?,tanggal_masuk=?,tanggal_pindah=?,asal_ruang=?,ruang_selanjutnya=?,diagnosa_utama=?,"+
                "diagnosa_sekunder=?,indikasi_pindah_ruang=?,keterangan_indikasi_pindah_ruang=?,prosedur_yang_sudah_dilakukan=?,obat_yang_telah_diberikan=?,metode_pemindahan_pasien=?,"+
                "peralatan_yang_menyertai=?,keterangan_peralatan_yang_menyertai=?,pemeriksaan_penunjang_yang_dilakukan=?,pasien_keluarga_menyetujui=?,nama_menyetujui=?,hubungan_menyetujui=?,"+
                "keluhan_utama_sebelum_transfer=?,keadaan_umum_sebelum_transfer=?,td_sebelum_transfer=?,nadi_sebelum_transfer=?,rr_sebelum_transfer=?,suhu_sebelum_transfer=?,"+
                "keluhan_utama_sesudah_transfer=?,keadaan_umum_sesudah_transfer=?,td_sesudah_transfer=?,nadi_sesudah_transfer=?,rr_sesudah_transfer=?,suhu_sesudah_transfer=?,"+
                "nip_menyerahkan=?,nip_menerima=?",34,new String[]{
                TNoRw.getText(),Valid.SetTgl(TanggalMasuk.getSelectedItem()+"")+" "+TanggalMasuk.getSelectedItem().toString().substring(11,19),
                Valid.SetTgl(TanggalPindah.getSelectedItem()+"")+" "+TanggalPindah.getSelectedItem().toString().substring(11,19),AsalRuang.getText(), 
                RuangSelanjutnya.getText(),DiagnosaUtama.getText(),DiagnosaSekunder.getText(),IndikasiPindah.getSelectedItem().toString(),KeteranganIndikasiPindahRuang.getText(), 
                ProsedurDilakukan.getText(),ObatYangDiberikan.getText(),MetodePemindahan.getSelectedItem().toString(),PeralatanMenyertai.getSelectedItem().toString(),
                KeteranganPeralatan.getText(),PemeriksaanPenunjang.getText(),MenyetujuiPemindahan.getSelectedItem().toString(),NamaMenyetujui.getText(),
                HubunganMenyetujui.getSelectedItem().toString(),KeluhanUtamaSebelumTransfer.getText(),KeadaanUmumSebelumTransfer.getSelectedItem().toString(),
                TDSebelumTransfer.getText(),NadiSebelumTransfer.getText(),RRSebelumTransfer.getText(),SuhuSebelumTransfer.getText(),KeluhanUtamaSetelahTransfer.getText(),
                KeadaanUmumSetelahTransfer.getSelectedItem().toString(),TDSetelahTransfer.getText(),NadiSetelahTransfer.getText(),RRSetelahTransfer.getText(), 
                SuhuSetelahTransfer.getText(),KdPetugasMenyerahkan.getText(),KdPetugasMenerima.getText(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString(),
                tbObat.getValueAt(tbObat.getSelectedRow(),5).toString()
            })==true){
                tbObat.setValueAt(TNoRw.getText(),tbObat.getSelectedRow(),0);
                tbObat.setValueAt(TNoRM.getText(),tbObat.getSelectedRow(),1);
                tbObat.setValueAt(TPasien.getText(),tbObat.getSelectedRow(),2);
                tbObat.setValueAt(TglLahir.getText(),tbObat.getSelectedRow(),3);
                tbObat.setValueAt(Jk.getText(),tbObat.getSelectedRow(),4);
                tbObat.setValueAt(Valid.SetTgl(TanggalMasuk.getSelectedItem()+"")+" "+TanggalMasuk.getSelectedItem().toString().substring(11,19),tbObat.getSelectedRow(),5);
                tbObat.setValueAt(Valid.SetTgl(TanggalPindah.getSelectedItem()+"")+" "+TanggalPindah.getSelectedItem().toString().substring(11,19),tbObat.getSelectedRow(),6);
                tbObat.setValueAt(IndikasiPindah.getSelectedItem().toString(),tbObat.getSelectedRow(),7);
                tbObat.setValueAt(KeteranganIndikasiPindahRuang.getText(),tbObat.getSelectedRow(),8);
                tbObat.setValueAt(AsalRuang.getText(),tbObat.getSelectedRow(),9);
                tbObat.setValueAt(RuangSelanjutnya.getText(),tbObat.getSelectedRow(),10);
                tbObat.setValueAt(MetodePemindahan.getSelectedItem().toString(),tbObat.getSelectedRow(),11);
                tbObat.setValueAt(DiagnosaUtama.getText(),tbObat.getSelectedRow(),12);
                tbObat.setValueAt(DiagnosaSekunder.getText(),tbObat.getSelectedRow(),13);
                tbObat.setValueAt(ProsedurDilakukan.getText(),tbObat.getSelectedRow(),14);
                tbObat.setValueAt(ObatYangDiberikan.getText(),tbObat.getSelectedRow(),15);
                tbObat.setValueAt(PemeriksaanPenunjang.getText(),tbObat.getSelectedRow(),16);
                tbObat.setValueAt(PeralatanMenyertai.getSelectedItem().toString(),tbObat.getSelectedRow(),17);
                tbObat.setValueAt(KeteranganPeralatan.getText(),tbObat.getSelectedRow(),18);
                tbObat.setValueAt(MenyetujuiPemindahan.getSelectedItem().toString(),tbObat.getSelectedRow(),19);
                tbObat.setValueAt(NamaMenyetujui.getText(),tbObat.getSelectedRow(),20);
                tbObat.setValueAt(HubunganMenyetujui.getSelectedItem().toString(),tbObat.getSelectedRow(),21);
                tbObat.setValueAt(KeadaanUmumSebelumTransfer.getSelectedItem().toString(),tbObat.getSelectedRow(),22);
                tbObat.setValueAt(TDSebelumTransfer.getText(),tbObat.getSelectedRow(),23);
                tbObat.setValueAt(NadiSebelumTransfer.getText(),tbObat.getSelectedRow(),24);
                tbObat.setValueAt(RRSebelumTransfer.getText(),tbObat.getSelectedRow(),25);
                tbObat.setValueAt(SuhuSebelumTransfer.getText(),tbObat.getSelectedRow(),26);
                tbObat.setValueAt(KeluhanUtamaSebelumTransfer.getText(),tbObat.getSelectedRow(),27);
                tbObat.setValueAt(KeadaanUmumSetelahTransfer.getSelectedItem().toString(),tbObat.getSelectedRow(),28);
                tbObat.setValueAt(TDSetelahTransfer.getText(),tbObat.getSelectedRow(),29);
                tbObat.setValueAt(NadiSetelahTransfer.getText(),tbObat.getSelectedRow(),30);
                tbObat.setValueAt(RRSetelahTransfer.getText(),tbObat.getSelectedRow(),31);
                tbObat.setValueAt(SuhuSetelahTransfer.getText(),tbObat.getSelectedRow(),32);
                tbObat.setValueAt(KeluhanUtamaSetelahTransfer.getText(),tbObat.getSelectedRow(),33);
                tbObat.setValueAt(KdPetugasMenyerahkan.getText(),tbObat.getSelectedRow(),34);
                tbObat.setValueAt(NmPetugasMenyerahkan.getText(),tbObat.getSelectedRow(),35);
                tbObat.setValueAt(KdPetugasMenerima.getText(),tbObat.getSelectedRow(),36);
                tbObat.setValueAt(NmPetugasMenerima.getText(),tbObat.getSelectedRow(),37);
                emptTeks();
                TabRawat.setSelectedIndex(1);
        }
    }

    private void simpan() {
        if(Sequel.menyimpantf("transfer_pasien_antar_ruang","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat & Tanggal Masuk",32,new String[]{
                TNoRw.getText(),Valid.SetTgl(TanggalMasuk.getSelectedItem()+"")+" "+TanggalMasuk.getSelectedItem().toString().substring(11,19),
                Valid.SetTgl(TanggalPindah.getSelectedItem()+"")+" "+TanggalPindah.getSelectedItem().toString().substring(11,19),AsalRuang.getText(), 
                RuangSelanjutnya.getText(),DiagnosaUtama.getText(),DiagnosaSekunder.getText(),IndikasiPindah.getSelectedItem().toString(),KeteranganIndikasiPindahRuang.getText(), 
                ProsedurDilakukan.getText(),ObatYangDiberikan.getText(),MetodePemindahan.getSelectedItem().toString(),PeralatanMenyertai.getSelectedItem().toString(),
                KeteranganPeralatan.getText(),PemeriksaanPenunjang.getText(),MenyetujuiPemindahan.getSelectedItem().toString(),NamaMenyetujui.getText(),
                HubunganMenyetujui.getSelectedItem().toString(),KeluhanUtamaSebelumTransfer.getText(),KeadaanUmumSebelumTransfer.getSelectedItem().toString(),
                TDSebelumTransfer.getText(),NadiSebelumTransfer.getText(),RRSebelumTransfer.getText(),SuhuSebelumTransfer.getText(),KeluhanUtamaSetelahTransfer.getText(),
                KeadaanUmumSetelahTransfer.getSelectedItem().toString(),TDSetelahTransfer.getText(),NadiSetelahTransfer.getText(),RRSetelahTransfer.getText(), 
                SuhuSetelahTransfer.getText(),KdPetugasMenyerahkan.getText(),KdPetugasMenerima.getText()
            })==true){
                emptTeks();
        }
    }
    
    private void isPhoto(){
        if(ChkAccor.isSelected()==true){
            ChkAccor.setVisible(false);
            PanelAccor.setPreferredSize(new Dimension(480,HEIGHT));
            FormPhoto.setVisible(true);  
            ChkAccor.setVisible(true);
        }else if(ChkAccor.isSelected()==false){    
            ChkAccor.setVisible(false);
            PanelAccor.setPreferredSize(new Dimension(15,HEIGHT));
            FormPhoto.setVisible(false);  
            ChkAccor.setVisible(true);
        }
    }

    private void panggilPhoto() {
        if(FormPhoto.isVisible()==true){
            try {
                ps=koneksi.prepareStatement("select bukti_persetujuan_transfer_pasien_antar_ruang.photo from bukti_persetujuan_transfer_pasien_antar_ruang where bukti_persetujuan_transfer_pasien_antar_ruang.no_rawat=? and bukti_persetujuan_transfer_pasien_antar_ruang.tanggal_masuk=?");
                try {
                    ps.setString(1,tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
                    ps.setString(2,tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
                    rs=ps.executeQuery();
                    if(rs.next()){
                        if(rs.getString("photo").equals("")||rs.getString("photo").equals("-")){
                            LoadHTML2.setText("<html><body><center><br><br><font face='tahoma' size='2' color='#434343'>Kosong</font></center></body></html>");
                        }else{
                            LoadHTML2.setText("<html><body><center><img src='http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/persetujuantransferruang/"+rs.getString("photo")+"' alt='photo' width='500' height='500'/></center></body></html>");
                        }  
                    }else{
                        LoadHTML2.setText("<html><body><center><br><br><font face='tahoma' size='2' color='#434343'>Kosong</font></center></body></html>");
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

/*
 * Kontribusi dari M. Syukur RS. Jiwa Prov Sultra
 * FULL CUSTOM RS ISLAM LUMAJANG
 * LAST Rabu, 25 Februari 2026
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
import java.util.HashMap;
import java.util.Locale;
import java.util.Date;
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
import kepegawaian.DlgCariPegawai;
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
    private int i=0;
    private DlgCariPetugas petugas;
    private DlgCariPegawai pegawai;
    private DlgCariDokter dokter;
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
            //tambah kewaspadaan transmisi, keadaan saat transfer, DPJP, resiko jatuh, riwayat alergi, catatan khusus 
            "No.Rawat","No.RM","Nama Pasien","Tgl.Lahir","J.K.","Tanggal Masuk","Tanggal Pindah","Indikasi Pindah","Keterangan Indikasi Pindah",
            "Asal Ruang Rawat / Poliklinik","Ruang Rawat Selanjutnya","Metode Pemindahan","Diagnosa Utama","KD Dokter","DPJP","Prosedur Yang Sudah Dilakukan",
            "Obat Yang Telah Diberikan","Pemeriksaan Penunjang Yang Sudah Dilakukan","Peralatan Yang Menyertai","Keterangan Peralatan Menyertai",
            "Menyetujui Pemindahan","Nama Keluarga/Penanggung Jawab","Hubungan","Kewaspadaan transmisi","Perawatan Isolasi","Keadaan Umum SbT","TD SbT","Nadi SbT","RR SbT","Suhu Sbt", "GDA SbT","SpO2 SbT","Skala Nyeri SbT","Berat SbT","Tinggi Sbt",
            "Keluhan Utama Sebelum Transfer","Keadaan Saat Transfer","Keadaan Umum StT","TD StT","Nadi StT","RR StT","Suhu Stt","GDA StT","SpO2 StT","Skala Nyeri StT","Berat StT","Tinggi StT","Keluhan Utama Setelah Transfer",
            "Resiko Jatuh","Riwayat Alergi","Catatan Khusus",
            "NIP Menyerahkan","Petugas Yang Menyerahkan","NIP Menerima","Petugas Yang Menerima", "NIP Menyetujui", "Petugas Yang Menyetujui"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        
        tbObat.setModel(tabMode);
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 54; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(120);
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
                column.setPreferredWidth(50);
            }else if(i==28){
                column.setPreferredWidth(50);
            }else if(i==29){
                column.setPreferredWidth(50);
            }else if(i==30){
                column.setPreferredWidth(50);
            }else if(i==31){
                column.setPreferredWidth(50);
            }else if(i==32){
                column.setPreferredWidth(80);
            }else if(i==33){
                column.setPreferredWidth(50);
            }else if(i==34){
                column.setPreferredWidth(50);
            }else if(i==35){
                column.setPreferredWidth(150);
            }else if(i==36){
                column.setPreferredWidth(50);
            }else if(i==37){
                column.setPreferredWidth(50);
            }else if(i==38){
                column.setPreferredWidth(50);
            }else if(i==39){
                column.setPreferredWidth(50);
            }else if(i==40){
                column.setPreferredWidth(50);
            }else if(i==41){
                column.setPreferredWidth(50);
            }else if(i==42){
                column.setPreferredWidth(80);
            }else if(i==43){
                column.setPreferredWidth(150);
            }else if(i==44){
                column.setPreferredWidth(150);
            }else if(i==45){
                column.setPreferredWidth(150);
            }else if(i==46){
                column.setPreferredWidth(150);
            }else if(i==47){
                column.setPreferredWidth(150);
            }else if(i==48){
                column.setPreferredWidth(150);
            }else if(i==49){
                column.setPreferredWidth(150);
            }else if(i==50){
                column.setPreferredWidth(150);
            }else if(i==51){
                column.setPreferredWidth(250);
            }else if(i==52){
                column.setPreferredWidth(250);
            }else if(i==53){
                column.setPreferredWidth(250);
            }else if(i==54){
                column.setPreferredWidth(250);
            }else if(i==55){
                column.setPreferredWidth(250);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());
        
        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        AsalRuang.setDocument(new batasInput((byte)30).getKata(AsalRuang));
        RuangSelanjutnya.setDocument(new batasInput((byte)30).getKata(RuangSelanjutnya));
        DiagnosaUtama.setDocument(new batasInput((int)100).getKata(DiagnosaUtama));
        KdDokter.setDocument(new batasInput((int)100).getKata(KdDokter));
        KeteranganIndikasiPindahRuang.setDocument(new batasInput((int)50).getKata(KeteranganIndikasiPindahRuang));
        ProsedurDilakukan.setDocument(new batasInput((int)800).getKata(ProsedurDilakukan));
        ObatYangDiberikan.setDocument(new batasInput((int)800).getKata(ObatYangDiberikan));
        KeteranganPeralatan.setDocument(new batasInput((int)50).getKata(KeteranganPeralatan));
        PemeriksaanPenunjang.setDocument(new batasInput((int)500).getKata(PemeriksaanPenunjang));
        NamaMenyetujui.setDocument(new batasInput((int)50).getKata(NamaMenyetujui));
        //BATAS INPUT DARI 200 KE 400
        KeluhanUtamaSebelumTransfer.setDocument(new batasInput((int)400).getKata(KeluhanUtamaSebelumTransfer));
        
        TDSebelumTransfer.setDocument(new batasInput((int)7).getKata(TDSebelumTransfer));
        NadiSebelumTransfer.setDocument(new batasInput((int)5).getKata(NadiSebelumTransfer));
        RRSebelumTransfer.setDocument(new batasInput((int)5).getKata(RRSebelumTransfer));
        SuhuSebelumTransfer.setDocument(new batasInput((int)5).getKata(SuhuSebelumTransfer));        
        GDASebelumTransfer.setDocument(new batasInput((int)5).getKata(GDASebelumTransfer));
        SPOSebelumTransfer.setDocument(new batasInput((int)5).getKata(SPOSebelumTransfer));
        //BATAS INPUT DARI 200 KE 400
        KeluhanUtamaSetelahTransfer.setDocument(new batasInput((int)400).getKata(KeluhanUtamaSetelahTransfer));
        
        TDSetelahTransfer.setDocument(new batasInput((int)7).getKata(TDSetelahTransfer));
        NadiSetelahTransfer.setDocument(new batasInput((int)5).getKata(NadiSetelahTransfer));
        RRSetelahTransfer.setDocument(new batasInput((int)5).getKata(RRSetelahTransfer));
        SuhuSetelahTransfer.setDocument(new batasInput((int)5).getKata(SuhuSetelahTransfer));
        GDASetelahTransfer.setDocument(new batasInput((int)5).getKata(GDASetelahTransfer));
        SPOSetelahTransfer.setDocument(new batasInput((int)5).getKata(SPOSetelahTransfer));

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
        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnCetakTransferPasienAntarRuang = new javax.swing.JMenuItem();
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
        jLabel31 = new widget.Label();
        scrollPane2 = new widget.ScrollPane();
        ProsedurDilakukan = new widget.TextArea();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel32 = new widget.Label();
        jLabel33 = new widget.Label();
        scrollPane3 = new widget.ScrollPane();
        ObatYangDiberikan = new widget.TextArea();
        scrollPane4 = new widget.ScrollPane();
        PemeriksaanPenunjang = new widget.TextArea();
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
        jLabel40 = new widget.Label();
        jLabel41 = new widget.Label();
        scrollPane5 = new widget.ScrollPane();
        KeluhanUtamaSebelumTransfer = new widget.TextArea();
        jLabel42 = new widget.Label();
        KeadaanUmumSebelumTransfer = new widget.ComboBox();
        TDSebelumTransfer = new widget.TextBox();
        jLabel17 = new widget.Label();
        NadiSebelumTransfer = new widget.TextBox();
        jLabel26 = new widget.Label();
        RRSebelumTransfer = new widget.TextBox();
        jLabel24 = new widget.Label();
        SuhuSebelumTransfer = new widget.TextBox();
        jLabel28 = new widget.Label();
        jLabel43 = new widget.Label();
        jLabel44 = new widget.Label();
        KeadaanUmumSetelahTransfer = new widget.ComboBox();
        jLabel29 = new widget.Label();
        TDSetelahTransfer = new widget.TextBox();
        jLabel46 = new widget.Label();
        scrollPane6 = new widget.ScrollPane();
        KeluhanUtamaSetelahTransfer = new widget.TextArea();
        jLabel47 = new widget.Label();
        NadiSetelahTransfer = new widget.TextBox();
        jLabel49 = new widget.Label();
        RRSetelahTransfer = new widget.TextBox();
        jLabel51 = new widget.Label();
        SuhuSetelahTransfer = new widget.TextBox();
        label14 = new widget.Label();
        KdPetugasMenyerahkan = new widget.TextBox();
        NmPetugasMenyerahkan = new widget.TextBox();
        BtnDokter = new widget.Button();
        KdPetugasMenerima = new widget.TextBox();
        NmPetugasMenerima = new widget.TextBox();
        BtnMenerima = new widget.Button();
        label16 = new widget.Label();
        jLabel53 = new widget.Label();
        ResikoJatuh = new javax.swing.JComboBox<>();
        jLabel54 = new widget.Label();
        TDSaatTransfer = new javax.swing.JComboBox<>();
        jLabel55 = new widget.Label();
        jLabel56 = new widget.Label();
        PerawatanIsolasi = new javax.swing.JComboBox<>();
        jLabel58 = new widget.Label();
        RiwayatAlergi = new widget.TextBox();
        jLabel59 = new widget.Label();
        CatatanKhusus = new widget.TextBox();
        KewaspadaanTransmisi = new widget.ComboBox();
        KdPetugasMenyetujui = new widget.TextBox();
        label17 = new widget.Label();
        NmPetugasMenyetujui = new widget.TextBox();
        NmDokter = new widget.TextBox();
        KdDokter = new widget.TextBox();
        BtnDpjp = new widget.Button();
        jSeparator8 = new javax.swing.JSeparator();
        label18 = new widget.Label();
        BtnMenyetujuiPetugas = new widget.Button();
        jLabel60 = new widget.Label();
        GDASebelumTransfer = new widget.TextBox();
        SPOSebelumTransfer = new widget.TextBox();
        jLabel27 = new widget.Label();
        SkalaNyeriSebelumTransfer = new widget.ComboBox();
        jLabel62 = new widget.Label();
        GDASetelahTransfer = new widget.TextBox();
        jLabel63 = new widget.Label();
        jLabel64 = new widget.Label();
        SPOSetelahTransfer = new widget.TextBox();
        jLabel65 = new widget.Label();
        SkalaNyeriSetelahTransfer = new widget.ComboBox();
        jSeparator15 = new javax.swing.JSeparator();
        jSeparator16 = new javax.swing.JSeparator();
        jSeparator17 = new javax.swing.JSeparator();
        TinggiSbT = new widget.TextBox();
        jLabel25 = new widget.Label();
        BeratStT = new widget.TextBox();
        jLabel45 = new widget.Label();
        BeratSbT = new widget.TextBox();
        jLabel48 = new widget.Label();
        jLabel50 = new widget.Label();
        TinggiStT = new widget.TextBox();
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

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnCetakTransferPasienAntarRuang.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        MnCetakTransferPasienAntarRuang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakTransferPasienAntarRuang.setText("Cetak Transfer Pasien Antar Ruang");
        MnCetakTransferPasienAntarRuang.setComponentPopupMenu(jPopupMenu1);
        MnCetakTransferPasienAntarRuang.setName("MnCetakTransferPasienAntarRuang"); // NOI18N
        MnCetakTransferPasienAntarRuang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakTransferPasienAntarRuangActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnCetakTransferPasienAntarRuang);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

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
        FormInput.setPreferredSize(new java.awt.Dimension(870, 780));
        FormInput.setLayout(null);

        jSeparator14.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator14.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator14.setName("jSeparator14"); // NOI18N
        FormInput.add(jSeparator14);
        jSeparator14.setBounds(0, 408, 890, 1);

        MenyetujuiPemindahan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        MenyetujuiPemindahan.setName("MenyetujuiPemindahan"); // NOI18N
        MenyetujuiPemindahan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MenyetujuiPemindahanKeyPressed(evt);
            }
        });
        FormInput.add(MenyetujuiPemindahan);
        MenyetujuiPemindahan.setBounds(770, 320, 80, 23);

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
        TNoRw.setBounds(70, 10, 131, 23);

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
        TanggalMasuk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "18-09-2024 14:32:02" }));
        TanggalMasuk.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        TanggalMasuk.setName("TanggalMasuk"); // NOI18N
        TanggalMasuk.setOpaque(false);
        TanggalMasuk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TanggalMasukActionPerformed(evt);
            }
        });
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
        TanggalPindah.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "18-09-2024 14:32:02" }));
        TanggalPindah.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        TanggalPindah.setName("TanggalPindah"); // NOI18N
        TanggalPindah.setOpaque(false);
        TanggalPindah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TanggalPindahActionPerformed(evt);
            }
        });
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
        KeteranganPeralatan.setBounds(290, 320, 160, 23);

        jLabel13.setText(":");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(70, 120, 10, 23);

        RuangSelanjutnya.setHighlighter(null);
        RuangSelanjutnya.setName("RuangSelanjutnya"); // NOI18N
        RuangSelanjutnya.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RuangSelanjutnyaActionPerformed(evt);
            }
        });
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
        jLabel14.setBounds(629, 80, 104, 23);

        jLabel15.setText("Ruang Rawat Selanjutnya :");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(320, 80, 140, 23);

        PeralatanMenyertai.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Oksigen Portable", "Infus", "NGT", "Syringe Pump", "Suction", "Kateter Urin", "Tidak Ada" }));
        PeralatanMenyertai.setName("PeralatanMenyertai"); // NOI18N
        PeralatanMenyertai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PeralatanMenyertaiActionPerformed(evt);
            }
        });
        PeralatanMenyertai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PeralatanMenyertaiKeyPressed(evt);
            }
        });
        FormInput.add(PeralatanMenyertai);
        PeralatanMenyertai.setBounds(150, 320, 135, 23);

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel16.setText("Diagnosa");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput.add(jLabel16);
        jLabel16.setBounds(15, 120, 60, 23);

        DiagnosaUtama.setHighlighter(null);
        DiagnosaUtama.setName("DiagnosaUtama"); // NOI18N
        DiagnosaUtama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosaUtamaKeyPressed(evt);
            }
        });
        FormInput.add(DiagnosaUtama);
        DiagnosaUtama.setBounds(90, 120, 770, 24);

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
        jLabel30.setText("DPJP            :");
        jLabel30.setName("jLabel30"); // NOI18N
        FormInput.add(jLabel30);
        jLabel30.setBounds(20, 160, 70, 23);

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

        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel32.setText("Obat Yang Telah Diberikan :");
        jLabel32.setName("jLabel32"); // NOI18N
        FormInput.add(jLabel32);
        jLabel32.setBounds(20, 200, 170, 23);

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
        scrollPane3.setBounds(20, 230, 410, 73);

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

        jLabel34.setText("Pasien/Keluarga Mengetahui & Menyetujui Alasan Pemindahan :");
        jLabel34.setName("jLabel34"); // NOI18N
        FormInput.add(jLabel34);
        jLabel34.setBounds(450, 320, 320, 23);

        IndikasiPindah.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Kondisi Pasien Stabil", "Kondisi Pasien Tidak Ada Perubahan", "Kondisi Pasien Memburuk", "Fasilitas Kurang Memadai", "Fasilitas Butuh Lebih Baik", "Tenaga Membutuhkan Yang Lebih Ahli", "Tenaga Kurang", "Lain-lain" }));
        IndikasiPindah.setName("IndikasiPindah"); // NOI18N
        IndikasiPindah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IndikasiPindahActionPerformed(evt);
            }
        });
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
        AsalRuang.setBounds(170, 80, 150, 23);

        jLabel35.setText(":");
        jLabel35.setName("jLabel35"); // NOI18N
        FormInput.add(jLabel35);
        jLabel35.setBounds(130, 320, 15, 23);

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
        jLabel36.setBounds(10, 320, 130, 23);

        jLabel37.setText(":");
        jLabel37.setName("jLabel37"); // NOI18N
        FormInput.add(jLabel37);
        jLabel37.setBounds(370, 350, 20, 23);

        NamaMenyetujui.setHighlighter(null);
        NamaMenyetujui.setName("NamaMenyetujui"); // NOI18N
        NamaMenyetujui.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NamaMenyetujuiKeyPressed(evt);
            }
        });
        FormInput.add(NamaMenyetujui);
        NamaMenyetujui.setBounds(400, 350, 230, 23);

        jLabel38.setText("Hubungan :");
        jLabel38.setName("jLabel38"); // NOI18N
        FormInput.add(jLabel38);
        jLabel38.setBounds(620, 350, 80, 23);

        HubunganMenyetujui.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Kakak", "Adik", "Saudara", "Keluarga", "Kakek", "Nenek", "Orang Tua", "Suami", "Istri", "Penanggung Jawab", "Menantu", "Ipar", "Mertua", "-" }));
        HubunganMenyetujui.setName("HubunganMenyetujui"); // NOI18N
        HubunganMenyetujui.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HubunganMenyetujuiKeyPressed(evt);
            }
        });
        FormInput.add(HubunganMenyetujui);
        HubunganMenyetujui.setBounds(700, 350, 150, 23);

        jLabel39.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel39.setText("Bila Pemberi Persetujuan Adalah Keluarga/Penanggung Jawab Pasien, Nama");
        jLabel39.setName("jLabel39"); // NOI18N
        FormInput.add(jLabel39);
        jLabel39.setBounds(10, 350, 380, 23);

        jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel40.setText("Keadaan Pasien Saat Pindah Sebelum Transfer :");
        jLabel40.setName("jLabel40"); // NOI18N
        FormInput.add(jLabel40);
        jLabel40.setBounds(10, 410, 320, 20);

        jLabel41.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel41.setText("Keluhan Utama :");
        jLabel41.setName("jLabel41"); // NOI18N
        FormInput.add(jLabel41);
        jLabel41.setBounds(580, 410, 100, 23);

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
        scrollPane5.setBounds(580, 430, 290, 70);

        jLabel42.setText("Keadaan Umum :");
        jLabel42.setName("jLabel42"); // NOI18N
        FormInput.add(jLabel42);
        jLabel42.setBounds(10, 430, 90, 23);

        KeadaanUmumSebelumTransfer.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Compos Mentis", "Gelisah", "Delirium", "Koma" }));
        KeadaanUmumSebelumTransfer.setName("KeadaanUmumSebelumTransfer"); // NOI18N
        KeadaanUmumSebelumTransfer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeadaanUmumSebelumTransferKeyPressed(evt);
            }
        });
        FormInput.add(KeadaanUmumSebelumTransfer);
        KeadaanUmumSebelumTransfer.setBounds(110, 430, 110, 23);

        TDSebelumTransfer.setFocusTraversalPolicyProvider(true);
        TDSebelumTransfer.setName("TDSebelumTransfer"); // NOI18N
        TDSebelumTransfer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TDSebelumTransferActionPerformed(evt);
            }
        });
        TDSebelumTransfer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TDSebelumTransferKeyPressed(evt);
            }
        });
        FormInput.add(TDSebelumTransfer);
        TDSebelumTransfer.setBounds(70, 460, 60, 23);

        jLabel17.setText("Nadi (/menit) :");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(130, 460, 80, 23);

        NadiSebelumTransfer.setFocusTraversalPolicyProvider(true);
        NadiSebelumTransfer.setName("NadiSebelumTransfer"); // NOI18N
        NadiSebelumTransfer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NadiSebelumTransferKeyPressed(evt);
            }
        });
        FormInput.add(NadiSebelumTransfer);
        NadiSebelumTransfer.setBounds(210, 460, 50, 23);

        jLabel26.setText("Skala Nyeri : ");
        jLabel26.setName("jLabel26"); // NOI18N
        FormInput.add(jLabel26);
        jLabel26.setBounds(220, 430, 70, 23);

        RRSebelumTransfer.setFocusTraversalPolicyProvider(true);
        RRSebelumTransfer.setName("RRSebelumTransfer"); // NOI18N
        RRSebelumTransfer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RRSebelumTransferActionPerformed(evt);
            }
        });
        RRSebelumTransfer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RRSebelumTransferKeyPressed(evt);
            }
        });
        FormInput.add(RRSebelumTransfer);
        RRSebelumTransfer.setBounds(330, 460, 60, 23);

        jLabel24.setText("T.Badan (cm) :");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(390, 490, 80, 23);

        SuhuSebelumTransfer.setFocusTraversalPolicyProvider(true);
        SuhuSebelumTransfer.setName("SuhuSebelumTransfer"); // NOI18N
        SuhuSebelumTransfer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SuhuSebelumTransferKeyPressed(evt);
            }
        });
        FormInput.add(SuhuSebelumTransfer);
        SuhuSebelumTransfer.setBounds(70, 490, 60, 23);

        jLabel28.setText("TD (mmHg) :");
        jLabel28.setName("jLabel28"); // NOI18N
        FormInput.add(jLabel28);
        jLabel28.setBounds(10, 600, 70, 23);

        jLabel43.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel43.setText("Keadaan Pasien Saat Pindah Setelah Transfer :");
        jLabel43.setName("jLabel43"); // NOI18N
        FormInput.add(jLabel43);
        jLabel43.setBounds(10, 550, 320, 23);

        jLabel44.setText("Keadaan Umum :");
        jLabel44.setName("jLabel44"); // NOI18N
        FormInput.add(jLabel44);
        jLabel44.setBounds(10, 570, 90, 23);

        KeadaanUmumSetelahTransfer.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Compos Mentis", "Gelisah", "Delirium", "Koma" }));
        KeadaanUmumSetelahTransfer.setName("KeadaanUmumSetelahTransfer"); // NOI18N
        KeadaanUmumSetelahTransfer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeadaanUmumSetelahTransferKeyPressed(evt);
            }
        });
        FormInput.add(KeadaanUmumSetelahTransfer);
        KeadaanUmumSetelahTransfer.setBounds(110, 570, 110, 23);

        jLabel29.setText("TD (mmHg) :");
        jLabel29.setName("jLabel29"); // NOI18N
        FormInput.add(jLabel29);
        jLabel29.setBounds(0, 460, 70, 23);

        TDSetelahTransfer.setFocusTraversalPolicyProvider(true);
        TDSetelahTransfer.setName("TDSetelahTransfer"); // NOI18N
        TDSetelahTransfer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TDSetelahTransferKeyPressed(evt);
            }
        });
        FormInput.add(TDSetelahTransfer);
        TDSetelahTransfer.setBounds(80, 600, 60, 23);

        jLabel46.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel46.setText("Keluhan Utama :");
        jLabel46.setName("jLabel46"); // NOI18N
        FormInput.add(jLabel46);
        jLabel46.setBounds(580, 550, 100, 23);

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
        scrollPane6.setBounds(580, 570, 290, 70);

        jLabel47.setText("Nadi (/menit) :");
        jLabel47.setName("jLabel47"); // NOI18N
        FormInput.add(jLabel47);
        jLabel47.setBounds(150, 600, 70, 23);

        NadiSetelahTransfer.setFocusTraversalPolicyProvider(true);
        NadiSetelahTransfer.setName("NadiSetelahTransfer"); // NOI18N
        NadiSetelahTransfer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NadiSetelahTransferKeyPressed(evt);
            }
        });
        FormInput.add(NadiSetelahTransfer);
        NadiSetelahTransfer.setBounds(220, 600, 50, 23);

        jLabel49.setText("RR (/menit) :");
        jLabel49.setName("jLabel49"); // NOI18N
        FormInput.add(jLabel49);
        jLabel49.setBounds(270, 600, 70, 23);

        RRSetelahTransfer.setFocusTraversalPolicyProvider(true);
        RRSetelahTransfer.setName("RRSetelahTransfer"); // NOI18N
        RRSetelahTransfer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RRSetelahTransferActionPerformed(evt);
            }
        });
        RRSetelahTransfer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RRSetelahTransferKeyPressed(evt);
            }
        });
        FormInput.add(RRSetelahTransfer);
        RRSetelahTransfer.setBounds(340, 600, 60, 23);

        jLabel51.setText("Suhu(°C) :");
        jLabel51.setName("jLabel51"); // NOI18N
        FormInput.add(jLabel51);
        jLabel51.setBounds(20, 630, 60, 23);

        SuhuSetelahTransfer.setFocusTraversalPolicyProvider(true);
        SuhuSetelahTransfer.setName("SuhuSetelahTransfer"); // NOI18N
        SuhuSetelahTransfer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SuhuSetelahTransferActionPerformed(evt);
            }
        });
        SuhuSetelahTransfer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SuhuSetelahTransferKeyPressed(evt);
            }
        });
        FormInput.add(SuhuSetelahTransfer);
        SuhuSetelahTransfer.setBounds(80, 630, 60, 23);

        label14.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label14.setText("Petugas / Perawat :");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label14);
        label14.setBounds(15, 700, 130, 23);

        KdPetugasMenyerahkan.setEditable(false);
        KdPetugasMenyerahkan.setName("KdPetugasMenyerahkan"); // NOI18N
        KdPetugasMenyerahkan.setPreferredSize(new java.awt.Dimension(80, 23));
        KdPetugasMenyerahkan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                KdPetugasMenyerahkanActionPerformed(evt);
            }
        });
        FormInput.add(KdPetugasMenyerahkan);
        KdPetugasMenyerahkan.setBounds(120, 720, 100, 23);

        NmPetugasMenyerahkan.setEditable(false);
        NmPetugasMenyerahkan.setName("NmPetugasMenyerahkan"); // NOI18N
        NmPetugasMenyerahkan.setPreferredSize(new java.awt.Dimension(207, 23));
        NmPetugasMenyerahkan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NmPetugasMenyerahkanActionPerformed(evt);
            }
        });
        FormInput.add(NmPetugasMenyerahkan);
        NmPetugasMenyerahkan.setBounds(220, 720, 160, 23);

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
        BtnDokter.setBounds(390, 720, 28, 23);

        KdPetugasMenerima.setEditable(false);
        KdPetugasMenerima.setName("KdPetugasMenerima"); // NOI18N
        KdPetugasMenerima.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput.add(KdPetugasMenerima);
        KdPetugasMenerima.setBounds(580, 720, 100, 23);

        NmPetugasMenerima.setEditable(false);
        NmPetugasMenerima.setName("NmPetugasMenerima"); // NOI18N
        NmPetugasMenerima.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmPetugasMenerima);
        NmPetugasMenerima.setBounds(680, 720, 180, 23);

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
        BtnMenerima.setBounds(860, 720, 28, 23);

        label16.setText("Menyerahkan :");
        label16.setName("label16"); // NOI18N
        label16.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label16);
        label16.setBounds(20, 720, 90, 23);

        jLabel53.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel53.setText("Resiko Jatuh :");
        jLabel53.setName("jLabel53"); // NOI18N
        FormInput.add(jLabel53);
        jLabel53.setBounds(10, 670, 80, 20);

        ResikoJatuh.setEditable(true);
        ResikoJatuh.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ResikoJatuh.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ringan", "Sedang", "Tinggi" }));
        ResikoJatuh.setName("ResikoJatuh"); // NOI18N
        ResikoJatuh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ResikoJatuhActionPerformed(evt);
            }
        });
        FormInput.add(ResikoJatuh);
        ResikoJatuh.setBounds(90, 670, 120, 20);

        jLabel54.setText("Keadaan Saat Transfer     :");
        jLabel54.setName("jLabel54"); // NOI18N
        FormInput.add(jLabel54);
        jLabel54.setBounds(0, 520, 140, 23);

        TDSaatTransfer.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TDSaatTransfer.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Stabil", "Tidak Stabil" }));
        TDSaatTransfer.setName("TDSaatTransfer"); // NOI18N
        TDSaatTransfer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TDSaatTransferActionPerformed(evt);
            }
        });
        FormInput.add(TDSaatTransfer);
        TDSaatTransfer.setBounds(150, 523, 100, 20);

        jLabel55.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel55.setText("Kewaspadaan Transmisi  :");
        jLabel55.setName("jLabel55"); // NOI18N
        FormInput.add(jLabel55);
        jLabel55.setBounds(10, 380, 150, 23);

        jLabel56.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel56.setText("Memerlukan Perawatan Isolasi   :");
        jLabel56.setName("jLabel56"); // NOI18N
        FormInput.add(jLabel56);
        jLabel56.setBounds(450, 380, 160, 23);

        PerawatanIsolasi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        PerawatanIsolasi.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ya", "Tidak" }));
        PerawatanIsolasi.setName("PerawatanIsolasi"); // NOI18N
        FormInput.add(PerawatanIsolasi);
        PerawatanIsolasi.setBounds(620, 380, 230, 20);

        jLabel58.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel58.setText("Riwayat Alergi :");
        jLabel58.setName("jLabel58"); // NOI18N
        FormInput.add(jLabel58);
        jLabel58.setBounds(230, 670, 90, 20);

        RiwayatAlergi.setHighlighter(null);
        RiwayatAlergi.setName("RiwayatAlergi"); // NOI18N
        RiwayatAlergi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RiwayatAlergiKeyPressed(evt);
            }
        });
        FormInput.add(RiwayatAlergi);
        RiwayatAlergi.setBounds(320, 670, 210, 23);

        jLabel59.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel59.setText("Catatan Khusus  :");
        jLabel59.setName("jLabel59"); // NOI18N
        FormInput.add(jLabel59);
        jLabel59.setBounds(560, 670, 100, 20);

        CatatanKhusus.setHighlighter(null);
        CatatanKhusus.setName("CatatanKhusus"); // NOI18N
        CatatanKhusus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CatatanKhususActionPerformed(evt);
            }
        });
        CatatanKhusus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CatatanKhususKeyPressed(evt);
            }
        });
        FormInput.add(CatatanKhusus);
        CatatanKhusus.setBounds(650, 670, 220, 23);

        KewaspadaanTransmisi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Droplet", "Kontak", "Udara" }));
        KewaspadaanTransmisi.setName("KewaspadaanTransmisi"); // NOI18N
        KewaspadaanTransmisi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KewaspadaanTransmisiKeyPressed(evt);
            }
        });
        FormInput.add(KewaspadaanTransmisi);
        KewaspadaanTransmisi.setBounds(140, 380, 180, 23);

        KdPetugasMenyetujui.setEditable(false);
        KdPetugasMenyetujui.setName("KdPetugasMenyetujui"); // NOI18N
        KdPetugasMenyetujui.setPreferredSize(new java.awt.Dimension(80, 23));
        KdPetugasMenyetujui.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                KdPetugasMenyetujuiActionPerformed(evt);
            }
        });
        FormInput.add(KdPetugasMenyetujui);
        KdPetugasMenyetujui.setBounds(120, 750, 100, 23);

        label17.setText("Menyetujui :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label17);
        label17.setBounds(20, 750, 90, 23);

        NmPetugasMenyetujui.setEditable(false);
        NmPetugasMenyetujui.setName("NmPetugasMenyetujui"); // NOI18N
        NmPetugasMenyetujui.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmPetugasMenyetujui);
        NmPetugasMenyetujui.setBounds(220, 750, 160, 23);

        NmDokter.setEditable(false);
        NmDokter.setHighlighter(null);
        NmDokter.setName("NmDokter"); // NOI18N
        NmDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NmDokterKeyPressed(evt);
            }
        });
        FormInput.add(NmDokter);
        NmDokter.setBounds(180, 160, 210, 23);

        KdDokter.setEditable(false);
        KdDokter.setHighlighter(null);
        KdDokter.setName("KdDokter"); // NOI18N
        KdDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                KdDokterActionPerformed(evt);
            }
        });
        KdDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdDokterKeyPressed(evt);
            }
        });
        FormInput.add(KdDokter);
        KdDokter.setBounds(90, 160, 90, 23);

        BtnDpjp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDpjp.setMnemonic('2');
        BtnDpjp.setToolTipText("Alt+2");
        BtnDpjp.setName("BtnDpjp"); // NOI18N
        BtnDpjp.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDpjp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDpjpActionPerformed(evt);
            }
        });
        BtnDpjp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnDpjpKeyPressed(evt);
            }
        });
        FormInput.add(BtnDpjp);
        BtnDpjp.setBounds(390, 160, 28, 23);

        jSeparator8.setName("jSeparator8"); // NOI18N
        FormInput.add(jSeparator8);
        jSeparator8.setBounds(10, 680, 0, 3);

        label18.setText("Menerima :");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label18);
        label18.setBounds(500, 720, 70, 23);

        BtnMenyetujuiPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnMenyetujuiPetugas.setMnemonic('2');
        BtnMenyetujuiPetugas.setToolTipText("Alt+2");
        BtnMenyetujuiPetugas.setName("BtnMenyetujuiPetugas"); // NOI18N
        BtnMenyetujuiPetugas.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnMenyetujuiPetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnMenyetujuiPetugasActionPerformed(evt);
            }
        });
        BtnMenyetujuiPetugas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnMenyetujuiPetugasKeyPressed(evt);
            }
        });
        FormInput.add(BtnMenyetujuiPetugas);
        BtnMenyetujuiPetugas.setBounds(390, 750, 28, 23);

        jLabel60.setText("Suhu(°C) :");
        jLabel60.setName("jLabel60"); // NOI18N
        FormInput.add(jLabel60);
        jLabel60.setBounds(10, 490, 60, 23);

        GDASebelumTransfer.setFocusTraversalPolicyProvider(true);
        GDASebelumTransfer.setName("GDASebelumTransfer"); // NOI18N
        GDASebelumTransfer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GDASebelumTransferKeyPressed(evt);
            }
        });
        FormInput.add(GDASebelumTransfer);
        GDASebelumTransfer.setBounds(210, 490, 50, 23);

        SPOSebelumTransfer.setFocusTraversalPolicyProvider(true);
        SPOSebelumTransfer.setName("SPOSebelumTransfer"); // NOI18N
        SPOSebelumTransfer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SPOSebelumTransferActionPerformed(evt);
            }
        });
        SPOSebelumTransfer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SPOSebelumTransferKeyPressed(evt);
            }
        });
        FormInput.add(SPOSebelumTransfer);
        SPOSebelumTransfer.setBounds(330, 490, 60, 23);

        jLabel27.setText("RR (/menit) :");
        jLabel27.setName("jLabel27"); // NOI18N
        FormInput.add(jLabel27);
        jLabel27.setBounds(260, 460, 70, 23);

        SkalaNyeriSebelumTransfer.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));
        SkalaNyeriSebelumTransfer.setName("SkalaNyeriSebelumTransfer"); // NOI18N
        SkalaNyeriSebelumTransfer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkalaNyeriSebelumTransferKeyPressed(evt);
            }
        });
        FormInput.add(SkalaNyeriSebelumTransfer);
        SkalaNyeriSebelumTransfer.setBounds(290, 430, 50, 23);

        jLabel62.setText("GDA (mg/dl) :");
        jLabel62.setName("jLabel62"); // NOI18N
        FormInput.add(jLabel62);
        jLabel62.setBounds(140, 490, 70, 23);

        GDASetelahTransfer.setFocusTraversalPolicyProvider(true);
        GDASetelahTransfer.setName("GDASetelahTransfer"); // NOI18N
        GDASetelahTransfer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GDASetelahTransferKeyPressed(evt);
            }
        });
        FormInput.add(GDASetelahTransfer);
        GDASetelahTransfer.setBounds(220, 630, 50, 23);

        jLabel63.setText("GDA (mg/dl) :");
        jLabel63.setName("jLabel63"); // NOI18N
        FormInput.add(jLabel63);
        jLabel63.setBounds(150, 630, 70, 23);

        jLabel64.setText("SpO2 (%) :");
        jLabel64.setName("jLabel64"); // NOI18N
        FormInput.add(jLabel64);
        jLabel64.setBounds(280, 630, 60, 23);

        SPOSetelahTransfer.setFocusTraversalPolicyProvider(true);
        SPOSetelahTransfer.setName("SPOSetelahTransfer"); // NOI18N
        SPOSetelahTransfer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SPOSetelahTransferKeyPressed(evt);
            }
        });
        FormInput.add(SPOSetelahTransfer);
        SPOSetelahTransfer.setBounds(340, 630, 60, 23);

        jLabel65.setText("Skala Nyeri : ");
        jLabel65.setName("jLabel65"); // NOI18N
        FormInput.add(jLabel65);
        jLabel65.setBounds(230, 570, 70, 23);

        SkalaNyeriSetelahTransfer.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));
        SkalaNyeriSetelahTransfer.setName("SkalaNyeriSetelahTransfer"); // NOI18N
        SkalaNyeriSetelahTransfer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkalaNyeriSetelahTransferKeyPressed(evt);
            }
        });
        FormInput.add(SkalaNyeriSetelahTransfer);
        SkalaNyeriSetelahTransfer.setBounds(300, 570, 60, 23);

        jSeparator15.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator15.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator15.setName("jSeparator15"); // NOI18N
        FormInput.add(jSeparator15);
        jSeparator15.setBounds(0, 518, 890, 1);

        jSeparator16.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator16.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator16.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator16.setName("jSeparator16"); // NOI18N
        FormInput.add(jSeparator16);
        jSeparator16.setBounds(0, 546, 890, 1);

        jSeparator17.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator17.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator17.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator17.setName("jSeparator17"); // NOI18N
        FormInput.add(jSeparator17);
        jSeparator17.setBounds(0, 663, 890, 1);

        TinggiSbT.setFocusTraversalPolicyProvider(true);
        TinggiSbT.setName("TinggiSbT"); // NOI18N
        TinggiSbT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TinggiSbTActionPerformed(evt);
            }
        });
        TinggiSbT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TinggiSbTKeyPressed(evt);
            }
        });
        FormInput.add(TinggiSbT);
        TinggiSbT.setBounds(470, 490, 60, 23);

        jLabel25.setText("SpO2(%)  : ");
        jLabel25.setName("jLabel25"); // NOI18N
        FormInput.add(jLabel25);
        jLabel25.setBounds(260, 490, 70, 23);

        BeratStT.setFocusTraversalPolicyProvider(true);
        BeratStT.setName("BeratStT"); // NOI18N
        BeratStT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BeratStTKeyPressed(evt);
            }
        });
        FormInput.add(BeratStT);
        BeratStT.setBounds(480, 600, 50, 23);

        jLabel45.setText("Berat (Kg) :");
        jLabel45.setName("jLabel45"); // NOI18N
        FormInput.add(jLabel45);
        jLabel45.setBounds(420, 600, 60, 23);

        BeratSbT.setFocusTraversalPolicyProvider(true);
        BeratSbT.setName("BeratSbT"); // NOI18N
        BeratSbT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BeratSbTActionPerformed(evt);
            }
        });
        BeratSbT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BeratSbTKeyPressed(evt);
            }
        });
        FormInput.add(BeratSbT);
        BeratSbT.setBounds(470, 460, 60, 23);

        jLabel48.setText("Berat (Kg) :");
        jLabel48.setName("jLabel48"); // NOI18N
        FormInput.add(jLabel48);
        jLabel48.setBounds(410, 460, 60, 23);

        jLabel50.setText("T.Badan (cm) :");
        jLabel50.setName("jLabel50"); // NOI18N
        FormInput.add(jLabel50);
        jLabel50.setBounds(400, 630, 80, 23);

        TinggiStT.setFocusTraversalPolicyProvider(true);
        TinggiStT.setName("TinggiStT"); // NOI18N
        TinggiStT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TinggiStTActionPerformed(evt);
            }
        });
        TinggiStT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TinggiStTKeyPressed(evt);
            }
        });
        FormInput.add(TinggiStT);
        TinggiStT.setBounds(480, 630, 50, 23);

        scrollInput.setViewportView(FormInput);

        internalFrame2.add(scrollInput, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Input Transfer Pasien", internalFrame2);

        internalFrame3.setBorder(null);
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setComponentPopupMenu(jPopupMenu1);
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);
        Scroll.setPreferredSize(new java.awt.Dimension(452, 200));
        Scroll.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ScrollMouseClicked(evt);
            }
        });

        tbObat.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
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

        internalFrame3.add(Scroll, java.awt.BorderLayout.CENTER);

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel19.setText("Tgl.Pindah :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(68, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "18-09-2024" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "18-09-2024" }));
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
        }else if(GDASebelumTransfer.getText().trim().equals("")){
            Valid.textKosong(GDASebelumTransfer, "GDA Sebelum Transfer");
        }else if(SPOSebelumTransfer.getText().trim().equals("")){
            Valid.textKosong(SPOSebelumTransfer, "SpO2 Sebelum Transfer");
        }else if(KdDokter.getText().trim().equals("")){
            Valid.textKosong(KdDokter, "DPJP");
        }else{
            if(akses.getkode().equals("Admin Utama")){
                    i = JOptionPane.showConfirmDialog(null, "Yakin mau menyimpan?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
                    if (i == JOptionPane.YES_OPTION) {
                        simpan();
                    }
            }else {
                if(akses.getkode().equals(KdPetugasMenerima.getText())||akses.getkode().equals(KdPetugasMenyerahkan.getText())){
                    i = JOptionPane.showConfirmDialog(null, "Yakin mau menyimpan?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
                    if (i == JOptionPane.YES_OPTION) {
                        simpan();
                    }
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
                if(akses.getkode().equals(KdPetugasMenyerahkan.getText())||akses.getkode().equals(KdPetugasMenerima.getText())){
                    i = JOptionPane.showConfirmDialog(null, "Yakin ingin menghapus data ini ???", "Konfirmasi", JOptionPane.YES_NO_OPTION);
                    if (i == JOptionPane.YES_OPTION) {
                        hapus();
                    }
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
        if (KdPetugasMenerima.getText().equals("-") && akses.getkode().equals(tbObat.getValueAt(tbObat.getSelectedRow(), 51).toString())) {
            if (TNoRM.getText().trim().equals("")) {
                Valid.textKosong(TNoRw, "Nama Pasien");
            } else if (NmPetugasMenyerahkan.getText().trim().equals("")) {
                Valid.textKosong(BtnDokter, "Petugas Yang Menyerahkan");
            } else if (NmPetugasMenerima.getText().trim().equals("")) {
                Valid.textKosong(BtnDokter, "Petugas Yang Menerima");
            } else if (AsalRuang.getText().trim().equals("")) {
                Valid.textKosong(BtnMenerima, "Petugas Yang Menerima");
            } else if (AsalRuang.getText().trim().equals("")) {
                Valid.textKosong(AsalRuang, "Asal Ruang");
            } else if (RuangSelanjutnya.getText().trim().equals("")) {
                Valid.textKosong(RuangSelanjutnya, "Ruang Selanjutnya");
            } else if (DiagnosaUtama.getText().trim().equals("")) {
                Valid.textKosong(DiagnosaUtama, "Diagnosa Utama");
            } else if (ProsedurDilakukan.getText().trim().equals("")) {
                Valid.textKosong(ProsedurDilakukan, "Prosedur Dilakukan");
            } else if (ObatYangDiberikan.getText().trim().equals("")) {
                Valid.textKosong(ObatYangDiberikan, "Obat Yang Diberikan");
            } else if (TDSebelumTransfer.getText().trim().equals("")) {
                Valid.textKosong(TDSebelumTransfer, "TD Sebelum Transfer");
            } else if (NadiSebelumTransfer.getText().trim().equals("")) {
                Valid.textKosong(NadiSebelumTransfer, "Nadi Sebelum Transfer");
            } else if (RRSebelumTransfer.getText().trim().equals("")) {
                Valid.textKosong(RRSebelumTransfer, "RR Sebelum Transfer");
            } else if (SuhuSebelumTransfer.getText().trim().equals("")) {
                Valid.textKosong(SuhuSebelumTransfer, "Suhu Sebelum Transfer");
            } else if (GDASebelumTransfer.getText().trim().equals("")) {
                Valid.textKosong(GDASebelumTransfer, "GDA Sebelum Transfer");
            } else if (SPOSebelumTransfer.getText().trim().equals("")) {
                Valid.textKosong(SPOSebelumTransfer, "SpO2 Sebelum Transfer");
            } else if (KeluhanUtamaSebelumTransfer.getText().trim().equals("")) {
                Valid.textKosong(KeluhanUtamaSebelumTransfer, "Keluhan Utama Sebelum Transfer");
            } else if (KdDokter.getText().trim().equals("")) {
                Valid.textKosong(KdDokter, "DPJP");
            } else {
                if (tbObat.getSelectedRow() > -1) {
                    if (akses.getkode().equals("Admin Utama")) {
                        ganti();
                    } else {
                        if (KdPetugasMenyerahkan.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(), 51).toString()) && tbObat.getValueAt(tbObat.getSelectedRow(), 53).toString().equals("-")) {
                            //JOptionPane.showMessageDialog(null,"Ini IF pertama");
                            ganti();
                        } //41 Menyerahkan 43 Menerima
                        else if (KdPetugasMenyerahkan.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(), 51).toString()) && akses.getkode().equals(tbObat.getValueAt(tbObat.getSelectedRow(), 53).toString())) {
                            //JOptionPane.showMessageDialog(null,"Ini IF kedua");
                            ganti();
                        } else if (KdPetugasMenerima.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(), 53).toString()) && akses.getkode().equals(tbObat.getValueAt(tbObat.getSelectedRow(), 51).toString())) {
                            //JOptionPane.showMessageDialog(null,"Ini IF ketiga");
                            ganti();

                        } else {
                            JOptionPane.showMessageDialog(null, "Harus salah satu petugas sesuai user login..!!");
                        }
                    }
                }
            }
        } else {
       
            if (TNoRM.getText().trim().equals("")) {
                Valid.textKosong(TNoRw, "Nama Pasien");
            } else if (NmPetugasMenyerahkan.getText().trim().equals("")) {
                Valid.textKosong(BtnDokter, "Petugas Yang Menyerahkan");
            } else if (NmPetugasMenerima.getText().trim().equals("")) {
                Valid.textKosong(BtnDokter, "Petugas Yang Menerima");
            } else if (AsalRuang.getText().trim().equals("")) {
                Valid.textKosong(BtnMenerima, "Petugas Yang Menerima");
            } else if (AsalRuang.getText().trim().equals("")) {
                Valid.textKosong(AsalRuang, "Asal Ruang");
            } else if (RuangSelanjutnya.getText().trim().equals("")) {
                Valid.textKosong(RuangSelanjutnya, "Ruang Selanjutnya");
            } else if (DiagnosaUtama.getText().trim().equals("")) {
                Valid.textKosong(DiagnosaUtama, "Diagnosa Utama");
            } else if (ProsedurDilakukan.getText().trim().equals("")) {
                Valid.textKosong(ProsedurDilakukan, "Prosedur Dilakukan");
            } else if (ObatYangDiberikan.getText().trim().equals("")) {
                Valid.textKosong(ObatYangDiberikan, "Obat Yang Diberikan");
            } else if (TDSebelumTransfer.getText().trim().equals("")) {
                Valid.textKosong(TDSebelumTransfer, "TD Sebelum Transfer");
            } else if (NadiSebelumTransfer.getText().trim().equals("")) {
                Valid.textKosong(NadiSebelumTransfer, "Nadi Sebelum Transfer");
            } else if (RRSebelumTransfer.getText().trim().equals("")) {
                Valid.textKosong(RRSebelumTransfer, "RR Sebelum Transfer");
            } else if (SuhuSebelumTransfer.getText().trim().equals("")) {
                Valid.textKosong(SuhuSebelumTransfer, "Suhu Sebelum Transfer");
            } else if (GDASebelumTransfer.getText().trim().equals("")) {
                Valid.textKosong(GDASebelumTransfer, "GDA Sebelum Transfer");
            } else if (SPOSebelumTransfer.getText().trim().equals("")) {
                Valid.textKosong(SPOSebelumTransfer, "SpO2 Sebelum Transfer");
            } else if (BeratSbT.getText().trim().equals("")) {
                Valid.textKosong(BeratSbT, "Berat Badan Sebelum Transfer");
            }else if (TinggiSbT.getText().trim().equals("")) {
                Valid.textKosong(TinggiSbT, "Tinggi Badan Sebelum Transfer");
            }else if (KeluhanUtamaSebelumTransfer.getText().trim().equals("")) {
                Valid.textKosong(KeluhanUtamaSebelumTransfer, "Keluhan Utama Sebelum Transfer");
            } else if (TDSetelahTransfer.getText().trim().equals("")) {
                Valid.textKosong(TDSetelahTransfer, "TD Setelah Transfer");
            } else if (NadiSetelahTransfer.getText().trim().equals("")) {
                Valid.textKosong(NadiSetelahTransfer, "Nadi Setelah Transfer");
            } else if (RRSetelahTransfer.getText().trim().equals("")) {
                Valid.textKosong(RRSetelahTransfer, "RR Setelah Transfer");
            } else if (SuhuSetelahTransfer.getText().trim().equals("")) {
                Valid.textKosong(SuhuSetelahTransfer, "Suhu Setelah Transfer");
            } else if (GDASetelahTransfer.getText().trim().equals("")) {
                Valid.textKosong(GDASetelahTransfer, "GDA Setelah Transfer");
            } else if (SPOSetelahTransfer.getText().trim().equals("")) {
                Valid.textKosong(SPOSetelahTransfer, "SpO2 Setelah Transfer");
            } else if (BeratStT.getText().trim().equals("")) {
                Valid.textKosong(BeratStT, "Berat Badan Setelah Transfer");
            }else if (TinggiStT.getText().trim().equals("")) {
                Valid.textKosong(TinggiStT, "Tinggi Badan Setelah Transfer");
            }else if (KeluhanUtamaSetelahTransfer.getText().trim().equals("")) {
                Valid.textKosong(KeluhanUtamaSetelahTransfer, "Keluhan Utama Setelah Transfer");
            } else if (KdDokter.getText().trim().equals("")) {
                Valid.textKosong(KdDokter, "DPJP");
            } else if(TanggalMasuk.getSelectedItem().equals(TanggalPindah.getSelectedItem())){
                //Valid.textKosong(KdDokter, "DPJP");
                JOptionPane.showMessageDialog(null,"Hayolohh... Jam Tanggal Pindah tidak boleh sama dengan Tanggal Masuk!!!");
            } else {
                if (tbObat.getSelectedRow() > -1) {
                    if (akses.getkode().equals("Admin Utama")) {
                        ganti();
                    } else {
                        if (KdPetugasMenyerahkan.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(), 51).toString()) && tbObat.getValueAt(tbObat.getSelectedRow(), 53).toString().equals("-")) {
                            //JOptionPane.showMessageDialog(null,"Ini IF pertama");
                            ganti();
                        } //41 Menyerahkan 43 Menerima
                        else if (KdPetugasMenyerahkan.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(), 51).toString()) && akses.getkode().equals(tbObat.getValueAt(tbObat.getSelectedRow(), 53).toString())) {
                            //JOptionPane.showMessageDialog(null,"Ini IF kedua");
                            ganti();
                        } else if (KdPetugasMenerima.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(), 53).toString()) && akses.getkode().equals(tbObat.getValueAt(tbObat.getSelectedRow(), 51).toString())) {
                            //JOptionPane.showMessageDialog(null,"Ini IF ketiga");
                            ganti();

                        } else {
                            JOptionPane.showMessageDialog(null, "Harus salah satu petugas sesuai user login..!!");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Silahkan anda pilih data terlebih dahulu..!!");
                }
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
                            "transfer_pasien_antar_ruang.ruang_selanjutnya,transfer_pasien_antar_ruang.diagnosa_utama,transfer_pasien_antar_ruang.dpjp,"+
                            "transfer_pasien_antar_ruang.indikasi_pindah_ruang,transfer_pasien_antar_ruang.keterangan_indikasi_pindah_ruang,"+
                            "transfer_pasien_antar_ruang.prosedur_yang_sudah_dilakukan,transfer_pasien_antar_ruang.obat_yang_telah_diberikan,"+
                            "transfer_pasien_antar_ruang.metode_pemindahan_pasien,transfer_pasien_antar_ruang.peralatan_yang_menyertai,"+
                            "transfer_pasien_antar_ruang.keterangan_peralatan_yang_menyertai,transfer_pasien_antar_ruang.pemeriksaan_penunjang_yang_dilakukan,"+
                            "transfer_pasien_antar_ruang.pasien_keluarga_menyetujui,transfer_pasien_antar_ruang.nama_menyetujui,transfer_pasien_antar_ruang.hubungan_menyetujui,"+
                            //tambah kewaspadaan_transmisi, perawatan_isolasi,keadaan_saat_transfer,resiko_jatuh,riwayat_alergi,catatan_khusus
                             "transfer_pasien_antar_ruang.kewaspadaan_transmisi,transfer_pasien_antar_ruang.perawatan_isolasi,transfer_pasien_antar_ruang.keadaan_saat_transfer,"+
                             "transfer_pasien_antar_ruang.resiko_jatuh,transfer_pasien_antar_ruang.riwayat_alergi,transfer_pasien_antar_ruang.catatan_khusus,"+
                            //end tambah
                            "transfer_pasien_antar_ruang.keluhan_utama_sebelum_transfer,transfer_pasien_antar_ruang.keadaan_umum_sebelum_transfer,"+
                            "transfer_pasien_antar_ruang.td_sebelum_transfer,transfer_pasien_antar_ruang.nadi_sebelum_transfer,transfer_pasien_antar_ruang.rr_sebelum_transfer,"+
                            "transfer_pasien_antar_ruang.suhu_sebelum_transfer,transfer_pasien_antar_ruang.keluhan_utama_sesudah_transfer,"+
                            "transfer_pasien_antar_ruang.keadaan_umum_sesudah_transfer,transfer_pasien_antar_ruang.td_sesudah_transfer,"+
                            "transfer_pasien_antar_ruang.nadi_sesudah_transfer,transfer_pasien_antar_ruang.rr_sesudah_transfer,transfer_pasien_antar_ruang.suhu_sesudah_transfer,"+
                            "transfer_pasien_antar_ruang.nip_menyerahkan,petugasmenyerahkan.nama as petugasmenyerahkan,transfer_pasien_antar_ruang.nip_menerima,"+
                            "petugasmenerima.nama as petugasmenerima, petugasmenyetujui.nama as menyetujui, transfer_pasien_antar_ruang.nip_menyetujui, dpj.nama as nm_dpjp "+
                            "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                            "inner join transfer_pasien_antar_ruang on reg_periksa.no_rawat=transfer_pasien_antar_ruang.no_rawat "+
                            "inner join petugas as petugasmenyerahkan on transfer_pasien_antar_ruang.nip_menyerahkan=petugasmenyerahkan.nip "+
                            "inner join petugas as petugasmenyetujui on petugasmenyetujui.nip=transfer_pasien_antar_ruang.nip_menyetujui "+
                            "inner join petugas as dpj on dpj.nip=transfer_pasien_antar_ruang.dpjp "+
                            "inner join petugas as petugasmenerima on transfer_pasien_antar_ruang.nip_menerima=petugasmenerima.nip where "+
                            "transfer_pasien_antar_ruang.tanggal_pindah between ? and ? order by transfer_pasien_antar_ruang.tanggal_pindah");
                }else{
                    ps=koneksi.prepareStatement(
                            "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,"+
                            "transfer_pasien_antar_ruang.tanggal_masuk,transfer_pasien_antar_ruang.tanggal_pindah,transfer_pasien_antar_ruang.asal_ruang,"+
                            "transfer_pasien_antar_ruang.ruang_selanjutnya,transfer_pasien_antar_ruang.diagnosa_utama,transfer_pasien_antar_ruang.dpjp,"+
                            "transfer_pasien_antar_ruang.indikasi_pindah_ruang,transfer_pasien_antar_ruang.keterangan_indikasi_pindah_ruang,"+
                            "transfer_pasien_antar_ruang.prosedur_yang_sudah_dilakukan,transfer_pasien_antar_ruang.obat_yang_telah_diberikan,"+
                            "transfer_pasien_antar_ruang.metode_pemindahan_pasien,transfer_pasien_antar_ruang.peralatan_yang_menyertai,"+
                            "transfer_pasien_antar_ruang.keterangan_peralatan_yang_menyertai,transfer_pasien_antar_ruang.pemeriksaan_penunjang_yang_dilakukan,"+
                            "transfer_pasien_antar_ruang.pasien_keluarga_menyetujui,transfer_pasien_antar_ruang.nama_menyetujui,transfer_pasien_antar_ruang.hubungan_menyetujui, "+
                            //tambah kewaspadaan_transmisi, perawatan_isolasi,keadaan_saat_transfer,resiko_jatuh,riwayat_alergi,catatan_khusus
                             "transfer_pasien_antar_ruang.kewaspadaan_transmisi,transfer_pasien_antar_ruang.perawatan_isolasi,transfer_pasien_antar_ruang.keadaan_saat_transfer,"+
                             "transfer_pasien_antar_ruang.resiko_jatuh,transfer_pasien_antar_ruang.riwayat_alergi,transfer_pasien_antar_ruang.catatan_khusus,"+
                            //end tambah
                            "transfer_pasien_antar_ruang.keluhan_utama_sebelum_transfer,transfer_pasien_antar_ruang.keadaan_umum_sebelum_transfer,"+
                            "transfer_pasien_antar_ruang.td_sebelum_transfer,transfer_pasien_antar_ruang.nadi_sebelum_transfer,transfer_pasien_antar_ruang.rr_sebelum_transfer,"+
                            "transfer_pasien_antar_ruang.suhu_sebelum_transfer,transfer_pasien_antar_ruang.keluhan_utama_sesudah_transfer,"+
                            "transfer_pasien_antar_ruang.keadaan_umum_sesudah_transfer,transfer_pasien_antar_ruang.td_sesudah_transfer,"+
                            "transfer_pasien_antar_ruang.nadi_sesudah_transfer,transfer_pasien_antar_ruang.rr_sesudah_transfer,transfer_pasien_antar_ruang.suhu_sesudah_transfer,"+
                            "transfer_pasien_antar_ruang.nip_menyerahkan,petugasmenyerahkan.nama as petugasmenyerahkan,transfer_pasien_antar_ruang.nip_menerima,"+
                            "petugasmenerima.nama as petugasmenerima, petugasmenyetujui.nama as menyetujui, transfer_pasien_antar_ruang.nip_menyetujui,dpj.nama as nm_dpjp"+
                            "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                            "inner join transfer_pasien_antar_ruang on reg_periksa.no_rawat=transfer_pasien_antar_ruang.no_rawat "+
                            "inner join petugas as petugasmenyerahkan on transfer_pasien_antar_ruang.nip_menyerahkan=petugasmenyerahkan.nip "+
                            "inner join petugas as petugasmenyetujui on petugasmenyetujui.nip=transfer_pasien_antar_ruang.nip_menyetujui "+
                            "inner join petugas as dpj on dpj.nip=transfer_pasien_antar_ruang.dpjp "+
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
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>DPJP</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Prosedur Yang Sudah Dilakukan</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Obat Yang Telah Diberikan</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Pemeriksaan Penunjang Yang Sudah Dilakukan</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Peralatan Yang Menyertai</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Keterangan Peralatan Menyertai</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Menyetujui Pemindahan</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Nama Keluarga/Penanggung Jawab</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Hubungan</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Kewaspadaan Transmisi</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Memerlukan Perawatan Isolasi</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Keadaan Umum SbT</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>TD SbT</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Nadi SbT</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>RR SbT</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Suhu Sbt</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Keluhan Utama Sebelum Transfer</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Keluhan Utama Setelah Transfer</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Keadaan Umum StT</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>TD StT</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Nadi StT</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>RR StT</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Suhu Stt</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Keadaan Saat Transfer</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Resiko Jatuh</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Riwayat Alergi</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Catatan Khusus</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>NIP Menyerahkan</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Petugas Yang Menyerahkan</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>NIP Menerima</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Petugas Yang Menerima</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>NIP Menyetujui</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Petugas Yang Menyetujui</b></td>"+
                            
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
                               "<td valign='top'>"+rs.getString("nm_dpjp")+"</td>"+
                               "<td valign='top'>"+rs.getString("prosedur_yang_sudah_dilakukan")+"</td>"+
                               "<td valign='top'>"+rs.getString("obat_yang_telah_diberikan")+"</td>"+
                               "<td valign='top'>"+rs.getString("pemeriksaan_penunjang_yang_dilakukan")+"</td>"+
                               "<td valign='top'>"+rs.getString("peralatan_yang_menyertai")+"</td>"+
                               "<td valign='top'>"+rs.getString("keterangan_peralatan_yang_menyertai")+"</td>"+
                               "<td valign='top'>"+rs.getString("pasien_keluarga_menyetujui")+"</td>"+
                               "<td valign='top'>"+rs.getString("nama_menyetujui")+"</td>"+
                               "<td valign='top'>"+rs.getString("hubungan_menyetujui")+"</td>"+
                               "<td valign='top'>"+rs.getString("kewaspadaan_transmisi")+"</td>"+
                               "<td valign='top'>"+rs.getString("perawatan_isolasi")+"</td>"+
                               "<td valign='top'>"+rs.getString("keadaan_umum_sebelum_transfer")+"</td>"+
                               "<td valign='top'>"+rs.getString("td_sebelum_transfer")+"</td>"+
                               "<td valign='top'>"+rs.getString("nadi_sebelum_transfer")+"</td>"+
                               "<td valign='top'>"+rs.getString("rr_sebelum_transfer")+"</td>"+
                               "<td valign='top'>"+rs.getString("suhu_sebelum_transfer")+"</td>"+
                               "<td valign='top'>"+rs.getString("keluhan_utama_sebelum_transfer")+"</td>"+
                               "<td valign='top'>"+rs.getString("keadaan_saat_transfer")+"</td>"+
                               "<td valign='top'>"+rs.getString("keadaan_umum_sesudah_transfer")+"</td>"+
                               "<td valign='top'>"+rs.getString("td_sesudah_transfer")+"</td>"+
                               "<td valign='top'>"+rs.getString("nadi_sesudah_transfer")+"</td>"+
                               "<td valign='top'>"+rs.getString("rr_sesudah_transfer")+"</td>"+
                               "<td valign='top'>"+rs.getString("suhu_sesudah_transfer")+"</td>"+
                               "<td valign='top'>"+rs.getString("keluhan_utama_sesudah_transfer")+"</td>"+
                               "<td valign='top'>"+rs.getString("resiko_jatuh")+"</td>"+
                               "<td valign='top'>"+rs.getString("riwayat_alergi")+"</td>"+
                               "<td valign='top'>"+rs.getString("catatan_khusus")+"</td>"+
                               "<td valign='top'>"+rs.getString("nip_menyerahkan")+"</td>"+
                               "<td valign='top'>"+rs.getString("petugasmenyerahkan")+"</td>"+
                               "<td valign='top'>"+rs.getString("nip_menerima")+"</td>"+
                               "<td valign='top'>"+rs.getString("petugasmenerima")+"</td>"+
                               "<td valign='top'>"+rs.getString("nip_menyetujui")+"</td>"+
                               "<td valign='top'>"+rs.getString("menyetujui")+"</td>"+
                               
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
            tampil();
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
                JOptionPane.showMessageDialog(rootPane,"Silahkan anda pilih No.Pernyataan terlebih dahulu..!!");
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

    private void ScrollMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ScrollMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
            if((evt.getClickCount()==2)&&(tbObat.getSelectedColumn()==0)){
                TabRawat.setSelectedIndex(0);
            }
        }
    }//GEN-LAST:event_ScrollMouseClicked

    private void MnCetakTransferPasienAntarRuangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCetakTransferPasienAntarRuangActionPerformed

            if(tbObat.getSelectedRow()>-1){
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
            param.put("tanggal_pindah",tbObat.getValueAt(tbObat.getSelectedRow(),6).toString());
            param.put("no_rawat",TNoRw.getText());
            
            //finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
            //param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbObat.getValueAt(tbObat.getSelectedRow(),6).toString()+"\nID "+(finger.equals("")?tbObat.getValueAt(tbObat.getSelectedRow(),5).toString():finger)+"\n"+Valid.SetTgl3(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString()));

            Valid.MyReportqry("rptTransferAntarRuang.jasper","report","::[ Lembar Transfer Pasien Antar Ruang ]::",
                "SELECT petugas_menerima.nama as menerima, petugas_menyerahkan.nama as menyerahkan, pasien.no_rkm_medis, pasien.nm_pasien, pasien.tgl_lahir ,reg_periksa.no_rawat, transfer_pasien_antar_ruang.asal_ruang, transfer_pasien_antar_ruang.diagnosa_utama, transfer_pasien_antar_ruang.indikasi_pindah_ruang, "
              + "transfer_pasien_antar_ruang.keadaan_umum_sebelum_transfer AS ku_sbtf, transfer_pasien_antar_ruang.keadaan_umum_sesudah_transfer as ku_sttf, transfer_pasien_antar_ruang.keluhan_utama_sebelum_transfer as ku_sb_tf,transfer_pasien_antar_ruang.keluhan_utama_sesudah_transfer as ku_st_tf, transfer_pasien_antar_ruang.metode_pemindahan_pasien,transfer_pasien_antar_ruang.nadi_sebelum_transfer as nadi_sb_tf,transfer_pasien_antar_ruang.nadi_sesudah_transfer as nadi_st_tf, "
              + "transfer_pasien_antar_ruang.obat_yang_telah_diberikan,transfer_pasien_antar_ruang.pemeriksaan_penunjang_yang_dilakukan,transfer_pasien_antar_ruang.prosedur_yang_sudah_dilakukan, transfer_pasien_antar_ruang.rr_sebelum_transfer,transfer_pasien_antar_ruang.rr_sesudah_transfer, transfer_pasien_antar_ruang.ruang_selanjutnya, transfer_pasien_antar_ruang.suhu_sebelum_transfer as suhu_sbtf,transfer_pasien_antar_ruang.suhu_sesudah_transfer as suhu_sttf,transfer_pasien_antar_ruang.berat_badan_sbt,transfer_pasien_antar_ruang.tinggi_badan_sbt,transfer_pasien_antar_ruang.berat_badan_stt,transfer_pasien_antar_ruang.tinggi_badan_stt, "
              + "transfer_pasien_antar_ruang.tanggal_masuk, transfer_pasien_antar_ruang.tanggal_pindah, transfer_pasien_antar_ruang.td_sebelum_transfer as td_sb_tf, transfer_pasien_antar_ruang.td_sesudah_transfer as td_st_tf,transfer_pasien_antar_ruang.dpjp,transfer_pasien_antar_ruang.ruang_selanjutnya, transfer_pasien_antar_ruang.peralatan_yang_menyertai as alat, transfer_pasien_antar_ruang.perawatan_isolasi as iso, transfer_pasien_antar_ruang.kewaspadaan_transmisi as transmisi, "
              + "transfer_pasien_antar_ruang.keadaan_saat_transfer as saat_tf, transfer_pasien_antar_ruang.rr_sebelum_transfer as rr_sb_tf, transfer_pasien_antar_ruang.rr_sesudah_transfer as rr_st_tf,petugas_menyetujui.nama as menyetujui,dpj.nm_dokter as nm_dpjp,transfer_pasien_antar_ruang.keterangan_peralatan_yang_menyertai as keterangan_alat, "
              + "transfer_pasien_antar_ruang.suhu_sebelum_transfer as suhu_sb_tf,transfer_pasien_antar_ruang.suhu_sesudah_transfer as suhu_st_tf,transfer_pasien_antar_ruang.resiko_jatuh, transfer_pasien_antar_ruang.riwayat_alergi,transfer_pasien_antar_ruang.gda_sebelum_transfer as gda_SbT, transfer_pasien_antar_ruang.spo_sebelum_transfer as spo_SbT,transfer_pasien_antar_ruang.skala_nyeri_sebelum_transfer as sn_SbT,transfer_pasien_antar_ruang.gda_sesudah_transfer as gda_Stt,transfer_pasien_antar_ruang.spo_sesudah_transfer as spo_Stt,transfer_pasien_antar_ruang.skala_nyeri_sesudah_transfer as sn_Stt, "
              + "transfer_pasien_antar_ruang.catatan_khusus, transfer_pasien_antar_ruang.nip_menerima,transfer_pasien_antar_ruang.nip_menyerahkan FROM transfer_pasien_antar_ruang INNER JOIN reg_periksa ON reg_periksa.no_rawat = transfer_pasien_antar_ruang.no_rawat INNER JOIN pasien ON pasien.no_rkm_medis = reg_periksa.no_rkm_medis inner join petugas as petugas_menerima on petugas_menerima.nip = transfer_pasien_antar_ruang.nip_menerima inner join petugas as petugas_menyerahkan on petugas_menyerahkan.nip=transfer_pasien_antar_ruang.nip_menyerahkan "
              + "inner join pegawai as petugas_menyetujui on petugas_menyetujui.nik=transfer_pasien_antar_ruang.nip_menyetujui inner join dokter as dpj on dpj.kd_dokter=transfer_pasien_antar_ruang.dpjp "
              //+ "WHERE transfer_pasien_antar_ruang.no_rawat='"+TNoRw.getText()+"' and transfer_pasien_antar_ruang.tanggal_pindah='"+Tgl.getText()+"' ",param); 
              + "WHERE transfer_pasien_antar_ruang.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"' and transfer_pasien_antar_ruang.tanggal_pindah='"+tbObat.getValueAt(tbObat.getSelectedRow(),6).toString()+"'" ,param);
        }
    }//GEN-LAST:event_MnCetakTransferPasienAntarRuangActionPerformed

    private void BtnDpjpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDpjpKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnDpjpKeyPressed

    private void BtnDpjpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDpjpActionPerformed
//        pilihan=1;
//        dokter.isCek();
//        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
//        dokter.setLocationRelativeTo(internalFrame1);
//        dokter.setAlwaysOnTop(false);
//        dokter.setVisible(true);
    if (dokter == null || !dokter.isDisplayable()) {
            dokter=new DlgCariDokter(null,false);
            dokter.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            dokter.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    if(dokter.getTable().getSelectedRow()!= -1){
                        KdDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                        NmDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                        KdDokter.requestFocus();
                    }   
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
    }//GEN-LAST:event_BtnDpjpActionPerformed

    private void KdDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdDokterKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KdDokterKeyPressed

    private void KdDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_KdDokterActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_KdDokterActionPerformed

    private void NmDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NmDokterKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NmDokterKeyPressed

    private void KdPetugasMenyetujuiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_KdPetugasMenyetujuiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_KdPetugasMenyetujuiActionPerformed

    private void KewaspadaanTransmisiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KewaspadaanTransmisiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KewaspadaanTransmisiKeyPressed

    private void CatatanKhususKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CatatanKhususKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_CatatanKhususKeyPressed

    private void CatatanKhususActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CatatanKhususActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CatatanKhususActionPerformed

    private void RiwayatAlergiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RiwayatAlergiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_RiwayatAlergiKeyPressed

    private void TDSaatTransferActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TDSaatTransferActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TDSaatTransferActionPerformed

    private void ResikoJatuhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ResikoJatuhActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ResikoJatuhActionPerformed

    private void BtnMenerimaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnMenerimaKeyPressed
        Valid.pindah(evt,KeluhanUtamaSetelahTransfer,BtnSimpan);
    }//GEN-LAST:event_BtnMenerimaKeyPressed

    private void BtnMenerimaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnMenerimaActionPerformed
//        pilihan=2;
//        petugas.isCek();
//        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
//        petugas.setLocationRelativeTo(internalFrame1);
//        petugas.setAlwaysOnTop(false);
//        petugas.setVisible(true);
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

    private void BtnDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokterKeyPressed
        Valid.pindah(evt,KeluhanUtamaSetelahTransfer,BtnMenerima);
    }//GEN-LAST:event_BtnDokterKeyPressed

    private void BtnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokterActionPerformed
//        pilihan=1;
//        petugas.isCek();
//        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
//        petugas.setLocationRelativeTo(internalFrame1);
//        petugas.setAlwaysOnTop(false);
//        petugas.setVisible(true);
    pilihan=2;
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

    private void NmPetugasMenyerahkanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NmPetugasMenyerahkanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NmPetugasMenyerahkanActionPerformed

<<<<<<< HEAD
    private void KdPetugasMenyerahkanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_KdPetugasMenyerahkanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_KdPetugasMenyerahkanActionPerformed

    private void SuhuSetelahTransferKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SuhuSetelahTransferKeyPressed
        Valid.pindah(evt,RRSetelahTransfer,KeluhanUtamaSetelahTransfer);
    }//GEN-LAST:event_SuhuSetelahTransferKeyPressed

    private void RRSetelahTransferKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RRSetelahTransferKeyPressed
        Valid.pindah(evt,NadiSetelahTransfer,SuhuSetelahTransfer);
    }//GEN-LAST:event_RRSetelahTransferKeyPressed

    private void NadiSetelahTransferKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NadiSetelahTransferKeyPressed
        Valid.pindah(evt,TDSetelahTransfer,RRSetelahTransfer);
    }//GEN-LAST:event_NadiSetelahTransferKeyPressed

    private void KeluhanUtamaSetelahTransferKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeluhanUtamaSetelahTransferKeyPressed
        Valid.pindah2(evt,SuhuSetelahTransfer,BtnDokter);
    }//GEN-LAST:event_KeluhanUtamaSetelahTransferKeyPressed

    private void TDSetelahTransferKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TDSetelahTransferKeyPressed
        Valid.pindah(evt,KeadaanUmumSetelahTransfer,NadiSetelahTransfer);
    }//GEN-LAST:event_TDSetelahTransferKeyPressed

    private void KeadaanUmumSetelahTransferKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeadaanUmumSetelahTransferKeyPressed
        Valid.pindah(evt,KeluhanUtamaSebelumTransfer,TDSetelahTransfer);
    }//GEN-LAST:event_KeadaanUmumSetelahTransferKeyPressed

    private void SuhuSebelumTransferKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SuhuSebelumTransferKeyPressed
        Valid.pindah(evt,RRSebelumTransfer,KeluhanUtamaSebelumTransfer);
    }//GEN-LAST:event_SuhuSebelumTransferKeyPressed

    private void RRSebelumTransferKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RRSebelumTransferKeyPressed
        Valid.pindah(evt,NadiSebelumTransfer,SuhuSebelumTransfer);
    }//GEN-LAST:event_RRSebelumTransferKeyPressed

    private void NadiSebelumTransferKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NadiSebelumTransferKeyPressed
        Valid.pindah(evt,TDSebelumTransfer,RRSebelumTransfer);
    }//GEN-LAST:event_NadiSebelumTransferKeyPressed

    private void TDSebelumTransferKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TDSebelumTransferKeyPressed
        Valid.pindah(evt,KeadaanUmumSebelumTransfer,NadiSebelumTransfer);
    }//GEN-LAST:event_TDSebelumTransferKeyPressed

    private void KeadaanUmumSebelumTransferKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeadaanUmumSebelumTransferKeyPressed
        Valid.pindah(evt,HubunganMenyetujui,TDSebelumTransfer);
    }//GEN-LAST:event_KeadaanUmumSebelumTransferKeyPressed

    private void KeluhanUtamaSebelumTransferKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeluhanUtamaSebelumTransferKeyPressed
        Valid.pindah2(evt,SuhuSebelumTransfer,KeadaanUmumSetelahTransfer);
    }//GEN-LAST:event_KeluhanUtamaSebelumTransferKeyPressed

    private void HubunganMenyetujuiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HubunganMenyetujuiKeyPressed
        Valid.pindah(evt,NamaMenyetujui,KeadaanUmumSebelumTransfer);
    }//GEN-LAST:event_HubunganMenyetujuiKeyPressed

    private void NamaMenyetujuiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NamaMenyetujuiKeyPressed
        Valid.pindah(evt,MenyetujuiPemindahan,HubunganMenyetujui);
    }//GEN-LAST:event_NamaMenyetujuiKeyPressed

    private void MetodePemindahanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MetodePemindahanKeyPressed
        Valid.pindah(evt,RuangSelanjutnya,DiagnosaUtama);
    }//GEN-LAST:event_MetodePemindahanKeyPressed

    private void AsalRuangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AsalRuangKeyPressed
        Valid.pindah(evt,KeteranganIndikasiPindahRuang,RuangSelanjutnya);
    }//GEN-LAST:event_AsalRuangKeyPressed

    private void IndikasiPindahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_IndikasiPindahKeyPressed
        Valid.pindah(evt,TanggalPindah,KeteranganIndikasiPindahRuang);
    }//GEN-LAST:event_IndikasiPindahKeyPressed

    private void IndikasiPindahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IndikasiPindahActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_IndikasiPindahActionPerformed

    private void PemeriksaanPenunjangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PemeriksaanPenunjangKeyPressed
        Valid.pindah2(evt,ObatYangDiberikan,PeralatanMenyertai);
    }//GEN-LAST:event_PemeriksaanPenunjangKeyPressed

    private void ObatYangDiberikanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ObatYangDiberikanKeyPressed
        Valid.pindah2(evt,ProsedurDilakukan,PemeriksaanPenunjang);
    }//GEN-LAST:event_ObatYangDiberikanKeyPressed

    private void ProsedurDilakukanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ProsedurDilakukanKeyPressed
        Valid.pindah2(evt,KdDokter,ObatYangDiberikan);
    }//GEN-LAST:event_ProsedurDilakukanKeyPressed

    private void DiagnosaUtamaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosaUtamaKeyPressed
        Valid.pindah(evt,MetodePemindahan,KdDokter);
    }//GEN-LAST:event_DiagnosaUtamaKeyPressed

    private void PeralatanMenyertaiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PeralatanMenyertaiKeyPressed
        //        Valid.pindah(evt,PemeriksaanPenunjang,KeteranganPeralatan);
    }//GEN-LAST:event_PeralatanMenyertaiKeyPressed

    private void PeralatanMenyertaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PeralatanMenyertaiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PeralatanMenyertaiActionPerformed

    private void RuangSelanjutnyaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RuangSelanjutnyaKeyPressed
        Valid.pindah(evt,AsalRuang,MetodePemindahan);
    }//GEN-LAST:event_RuangSelanjutnyaKeyPressed

    private void RuangSelanjutnyaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RuangSelanjutnyaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_RuangSelanjutnyaActionPerformed

    private void KeteranganPeralatanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganPeralatanKeyPressed
        Valid.pindah(evt,PeralatanMenyertai,MenyetujuiPemindahan);
    }//GEN-LAST:event_KeteranganPeralatanKeyPressed

    private void KeteranganIndikasiPindahRuangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganIndikasiPindahRuangKeyPressed
        Valid.pindah(evt,IndikasiPindah,AsalRuang);
    }//GEN-LAST:event_KeteranganIndikasiPindahRuangKeyPressed

    private void TanggalPindahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalPindahKeyPressed
        Valid.pindah2(evt,TanggalMasuk,IndikasiPindah);
    }//GEN-LAST:event_TanggalPindahKeyPressed

    private void TanggalPindahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TanggalPindahActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TanggalPindahActionPerformed

    private void TanggalMasukKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalMasukKeyPressed
        Valid.pindah2(evt,TNoRw,TanggalPindah);
    }//GEN-LAST:event_TanggalMasukKeyPressed

    private void TanggalMasukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TanggalMasukActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TanggalMasukActionPerformed

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isRawat();
        }else{
            //Valid.pindah(evt,TCari,BtnDokter);
        }
    }//GEN-LAST:event_TNoRwKeyPressed

    private void MenyetujuiPemindahanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MenyetujuiPemindahanKeyPressed
        //        Valid.pindah(evt,KeteranganPeralatan,NamaMenyetujui);
    }//GEN-LAST:event_MenyetujuiPemindahanKeyPressed

    private void GDASebelumTransferKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GDASebelumTransferKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_GDASebelumTransferKeyPressed

    private void SPOSebelumTransferKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SPOSebelumTransferKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SPOSebelumTransferKeyPressed

    private void SkalaNyeriSebelumTransferKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkalaNyeriSebelumTransferKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SkalaNyeriSebelumTransferKeyPressed

    private void GDASetelahTransferKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GDASetelahTransferKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_GDASetelahTransferKeyPressed

    private void SPOSetelahTransferKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SPOSetelahTransferKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SPOSetelahTransferKeyPressed

    private void SkalaNyeriSetelahTransferKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkalaNyeriSetelahTransferKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SkalaNyeriSetelahTransferKeyPressed

    private void TinggiSbTKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TinggiSbTKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TinggiSbTKeyPressed

    private void SPOSebelumTransferActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SPOSebelumTransferActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SPOSebelumTransferActionPerformed

    private void RRSebelumTransferActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RRSebelumTransferActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_RRSebelumTransferActionPerformed

    private void BeratStTKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BeratStTKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BeratStTKeyPressed

    private void TinggiSbTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TinggiSbTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TinggiSbTActionPerformed

    private void BeratSbTKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BeratSbTKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BeratSbTKeyPressed

    private void SuhuSetelahTransferActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SuhuSetelahTransferActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SuhuSetelahTransferActionPerformed

    private void TinggiStTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TinggiStTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TinggiStTActionPerformed

    private void TinggiStTKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TinggiStTKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TinggiStTKeyPressed

    private void BeratSbTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BeratSbTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BeratSbTActionPerformed

    private void BtnMenyetujuiPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnMenyetujuiPetugasKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnMenyetujuiPetugasKeyPressed

    private void BtnMenyetujuiPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnMenyetujuiPetugasActionPerformed
        if (pegawai == null || !pegawai.isDisplayable()) {
            pegawai=new DlgCariPegawai(null,false);
            pegawai.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            pegawai.addWindowListener(new WindowAdapter() {
=======
    private void BtnMenerimaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnMenerimaActionPerformed
        if (petugas == null || !petugas.isDisplayable()) {
            petugas=new DlgCariPetugas(null,false);
            petugas.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            petugas.addWindowListener(new WindowAdapter() {
>>>>>>> upstream/master
                @Override
                public void windowClosed(WindowEvent e) {
                    if(pegawai.getTable().getSelectedRow()!= -1){
                        KdPetugasMenyetujui.setText(pegawai.getTable().getValueAt(pegawai.getTable().getSelectedRow(), 0).toString());
                        NmPetugasMenyetujui.setText(pegawai.getTable().getValueAt(pegawai.getTable().getSelectedRow(), 1).toString());
                        KdPetugasMenyetujui.requestFocus();
                    }   
                    pegawai=null;
                }
            });

            pegawai.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            pegawai.setLocationRelativeTo(internalFrame1);
        }
            
        if (pegawai == null) return;
        if (!pegawai.isVisible()) {
            //pegawai.isCek();    
            pegawai.emptTeks();
        }  
        if (pegawai.isVisible()) {
            pegawai.toFront();
            return;
        }    
        pegawai.setVisible(true);
    }//GEN-LAST:event_BtnMenyetujuiPetugasActionPerformed

    private void RRSetelahTransferActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RRSetelahTransferActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_RRSetelahTransferActionPerformed

    private void TDSebelumTransferActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TDSebelumTransferActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TDSebelumTransferActionPerformed

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
                

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private widget.TextBox AsalRuang;
    private widget.TextBox BeratSbT;
    private widget.TextBox BeratStT;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnDokter;
    private widget.Button BtnDpjp;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnMenerima;
    private widget.Button BtnMenyetujuiPetugas;
    private widget.Button BtnPrint;
    private widget.Button BtnRefreshPhoto1;
    private widget.Button BtnSimpan;
    private widget.TextBox CatatanKhusus;
    private widget.CekBox ChkAccor;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.TextBox DiagnosaUtama;
    private widget.PanelBiasa FormInput;
    private widget.PanelBiasa FormPass3;
    private widget.PanelBiasa FormPhoto;
    private widget.TextBox GDASebelumTransfer;
    private widget.TextBox GDASetelahTransfer;
    private widget.ComboBox HubunganMenyetujui;
    private widget.ComboBox IndikasiPindah;
    private widget.TextBox Jk;
    private widget.TextBox KdDokter;
    private widget.TextBox KdPetugasMenerima;
    private widget.TextBox KdPetugasMenyerahkan;
    private widget.TextBox KdPetugasMenyetujui;
    private widget.ComboBox KeadaanUmumSebelumTransfer;
    private widget.ComboBox KeadaanUmumSetelahTransfer;
    private widget.TextArea KeluhanUtamaSebelumTransfer;
    private widget.TextArea KeluhanUtamaSetelahTransfer;
    private widget.TextBox KeteranganIndikasiPindahRuang;
    private widget.TextBox KeteranganPeralatan;
    private widget.ComboBox KewaspadaanTransmisi;
    private widget.Label LCount;
    private widget.editorpane LoadHTML;
    private widget.editorpane LoadHTML2;
    private widget.ComboBox MenyetujuiPemindahan;
    private widget.ComboBox MetodePemindahan;
    private javax.swing.JMenuItem MnCetakTransferPasienAntarRuang;
    private widget.TextBox NadiSebelumTransfer;
    private widget.TextBox NadiSetelahTransfer;
    private widget.TextBox NamaMenyetujui;
    private widget.TextBox NmDokter;
    private widget.TextBox NmPetugasMenerima;
    private widget.TextBox NmPetugasMenyerahkan;
    private widget.TextBox NmPetugasMenyetujui;
    private widget.TextArea ObatYangDiberikan;
    private widget.PanelBiasa PanelAccor;
    private widget.TextArea PemeriksaanPenunjang;
    private widget.ComboBox PeralatanMenyertai;
    private javax.swing.JComboBox<String> PerawatanIsolasi;
    private widget.TextArea ProsedurDilakukan;
    private widget.TextBox RRSebelumTransfer;
    private widget.TextBox RRSetelahTransfer;
    private javax.swing.JComboBox<String> ResikoJatuh;
    private widget.TextBox RiwayatAlergi;
    private widget.TextBox RuangSelanjutnya;
    private widget.TextBox SPOSebelumTransfer;
    private widget.TextBox SPOSetelahTransfer;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll5;
    private widget.ComboBox SkalaNyeriSebelumTransfer;
    private widget.ComboBox SkalaNyeriSetelahTransfer;
    private widget.TextBox SuhuSebelumTransfer;
    private widget.TextBox SuhuSetelahTransfer;
    private widget.TextBox TCari;
    private javax.swing.JComboBox<String> TDSaatTransfer;
    private widget.TextBox TDSebelumTransfer;
    private widget.TextBox TDSetelahTransfer;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private javax.swing.JTabbedPane TabRawat;
    private widget.Tanggal TanggalMasuk;
    private widget.Tanggal TanggalPindah;
    private widget.TextBox TglLahir;
    private widget.TextBox TinggiSbT;
    private widget.TextBox TinggiStT;
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
    private widget.Label jLabel53;
    private widget.Label jLabel54;
    private widget.Label jLabel55;
    private widget.Label jLabel56;
    private widget.Label jLabel57;
    private widget.Label jLabel58;
    private widget.Label jLabel59;
    private widget.Label jLabel6;
    private widget.Label jLabel60;
    private widget.Label jLabel62;
    private widget.Label jLabel63;
    private widget.Label jLabel64;
    private widget.Label jLabel65;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator15;
    private javax.swing.JSeparator jSeparator16;
    private javax.swing.JSeparator jSeparator17;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator8;
    private widget.Label label11;
    private widget.Label label12;
    private widget.Label label14;
    private widget.Label label16;
    private widget.Label label17;
    private widget.Label label18;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollInput;
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
                        //rubah diagnosa_sekunder menjadi dpjp
                        "transfer_pasien_antar_ruang.ruang_selanjutnya,transfer_pasien_antar_ruang.diagnosa_utama,transfer_pasien_antar_ruang.dpjp,"+
                        //end rubah
                        "transfer_pasien_antar_ruang.indikasi_pindah_ruang,transfer_pasien_antar_ruang.keterangan_indikasi_pindah_ruang,"+
                        "transfer_pasien_antar_ruang.prosedur_yang_sudah_dilakukan,transfer_pasien_antar_ruang.obat_yang_telah_diberikan,"+
                        "transfer_pasien_antar_ruang.metode_pemindahan_pasien,transfer_pasien_antar_ruang.peralatan_yang_menyertai,"+
                        "transfer_pasien_antar_ruang.keterangan_peralatan_yang_menyertai,transfer_pasien_antar_ruang.pemeriksaan_penunjang_yang_dilakukan,"+
                        "transfer_pasien_antar_ruang.pasien_keluarga_menyetujui,transfer_pasien_antar_ruang.nama_menyetujui,transfer_pasien_antar_ruang.hubungan_menyetujui,"+
                        "transfer_pasien_antar_ruang.keluhan_utama_sebelum_transfer,transfer_pasien_antar_ruang.keadaan_umum_sebelum_transfer,"+
                        "transfer_pasien_antar_ruang.td_sebelum_transfer,transfer_pasien_antar_ruang.nadi_sebelum_transfer,transfer_pasien_antar_ruang.rr_sebelum_transfer,"+
                        //tambah gda_sebelum_transfer, spo_sebelum_transfer, gda_sesudah_transfer, spo_sesudah_transfer, skala_nyeri_sebelum_transfer,skala_nyeri_sesudah_transfer,berat_badan_sbt,tinggi_badan_sbt,berat_badan_stt,tinggi_badan_stt
                        "transfer_pasien_antar_ruang.suhu_sebelum_transfer,transfer_pasien_antar_ruang.gda_sebelum_transfer,transfer_pasien_antar_ruang.spo_sebelum_transfer,transfer_pasien_antar_ruang.skala_nyeri_sebelum_transfer,transfer_pasien_antar_ruang.berat_badan_sbt,transfer_pasien_antar_ruang.tinggi_badan_sbt,transfer_pasien_antar_ruang.keluhan_utama_sesudah_transfer,"+
                        "transfer_pasien_antar_ruang.keadaan_umum_sesudah_transfer,transfer_pasien_antar_ruang.td_sesudah_transfer,transfer_pasien_antar_ruang.berat_badan_stt,tinggi_badan_stt,"+
                        "transfer_pasien_antar_ruang.nadi_sesudah_transfer,transfer_pasien_antar_ruang.rr_sesudah_transfer,transfer_pasien_antar_ruang.suhu_sesudah_transfer,transfer_pasien_antar_ruang.gda_sesudah_transfer,transfer_pasien_antar_ruang.spo_sesudah_transfer,transfer_pasien_antar_ruang.skala_nyeri_sesudah_transfer,"+
                        //tambah resiko_jatuh,riwayat_alergi,catatan_khusus
                        "transfer_pasien_antar_ruang.resiko_jatuh,transfer_pasien_antar_ruang.riwayat_alergi,transfer_pasien_antar_ruang.catatan_khusus,transfer_pasien_antar_ruang.kewaspadaan_transmisi, "+
                        "transfer_pasien_antar_ruang.keadaan_saat_transfer,transfer_pasien_antar_ruang.perawatan_isolasi, "+
                        //end tambah
                        //tambah petugas menyetujui dan dokter dpjp
                        "transfer_pasien_antar_ruang.nip_menyerahkan,petugasmenyerahkan.nama as petugasmenyerahkan,transfer_pasien_antar_ruang.nip_menerima, "+
                        "petugasmenerima.nama as petugasmenerima, petugasmenyetujui.nama as petugasmenyetujui, transfer_pasien_antar_ruang.nip_menyetujui, "+
                        "dpj.nm_dokter as dpj, transfer_pasien_antar_ruang.dpjp "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join transfer_pasien_antar_ruang on reg_periksa.no_rawat=transfer_pasien_antar_ruang.no_rawat "+
                        "inner join petugas as petugasmenyerahkan on transfer_pasien_antar_ruang.nip_menyerahkan=petugasmenyerahkan.nip "+
                        "inner join pegawai as petugasmenyetujui on transfer_pasien_antar_ruang.nip_menyetujui=petugasmenyetujui.nik "+
                        "inner join dokter as dpj on transfer_pasien_antar_ruang.dpjp=dpj.kd_dokter "+
//                        "inner join pegawai on transfer_pasien_antar_ruang.nip_menyetujui=pegawai.nik "+
                        "inner join petugas as petugasmenerima on transfer_pasien_antar_ruang.nip_menerima=petugasmenerima.nip where "+
                        "transfer_pasien_antar_ruang.tanggal_pindah between ? and ? order by transfer_pasien_antar_ruang.tanggal_pindah");
            }else{
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,"+
                        "transfer_pasien_antar_ruang.tanggal_masuk,transfer_pasien_antar_ruang.tanggal_pindah,transfer_pasien_antar_ruang.asal_ruang,"+
                        //rubah diagnosa_sekunder menjadi dpjp
                        "transfer_pasien_antar_ruang.ruang_selanjutnya,transfer_pasien_antar_ruang.diagnosa_utama,transfer_pasien_antar_ruang.dpjp,"+
                        //end rubah
                        "transfer_pasien_antar_ruang.indikasi_pindah_ruang,transfer_pasien_antar_ruang.keterangan_indikasi_pindah_ruang,"+
                        "transfer_pasien_antar_ruang.prosedur_yang_sudah_dilakukan,transfer_pasien_antar_ruang.obat_yang_telah_diberikan,"+
                        "transfer_pasien_antar_ruang.metode_pemindahan_pasien,transfer_pasien_antar_ruang.peralatan_yang_menyertai,"+
                        "transfer_pasien_antar_ruang.keterangan_peralatan_yang_menyertai,transfer_pasien_antar_ruang.pemeriksaan_penunjang_yang_dilakukan,"+
                        "transfer_pasien_antar_ruang.pasien_keluarga_menyetujui,transfer_pasien_antar_ruang.nama_menyetujui,transfer_pasien_antar_ruang.hubungan_menyetujui,"+
                        "transfer_pasien_antar_ruang.keluhan_utama_sebelum_transfer,transfer_pasien_antar_ruang.keadaan_umum_sebelum_transfer,"+
                        "transfer_pasien_antar_ruang.td_sebelum_transfer,transfer_pasien_antar_ruang.nadi_sebelum_transfer,transfer_pasien_antar_ruang.rr_sebelum_transfer,"+
                        //tambah gda_sebelum_transfer, spo_sebelum_transfer, gda_sesudah_transfer, spo_sesudah_transfer, skala_nyeri_sebelum_transfer,skala_nyeri_sesudah_transfer,berat_badan_sbt,tinggi_badan_sbt,berat_badan_stt,tinggi_bada_stt
                        "transfer_pasien_antar_ruang.suhu_sebelum_transfer,transfer_pasien_antar_ruang.gda_sebelum_transfer,transfer_pasien_antar_ruang.spo_sebelum_transfer,transfer_pasien_antar_ruang.skala_nyeri_sebelum_transfer,transfer_pasien_antar_ruang.berat_badan_sbt,tinggi_badan_sbt,transfer_pasien_antar_ruang.keluhan_utama_sesudah_transfer,"+
                        "transfer_pasien_antar_ruang.keadaan_umum_sesudah_transfer,transfer_pasien_antar_ruang.td_sesudah_transfer,transfer_pasien_antar_ruang.berat_badan_stt,transfer_pasien_antar_ruang.tinggi_badan_stt,"+
                        "transfer_pasien_antar_ruang.nadi_sesudah_transfer,transfer_pasien_antar_ruang.rr_sesudah_transfer,transfer_pasien_antar_ruang.suhu_sesudah_transfer,transfer_pasien_antar_ruang.gda_sesudah_transfer,spo_sesudah_transfer,transfer_pasien_antar_ruang.skala_nyeri_sesudah_transfer,"+
                        //tambah resiko_jatuh,riwayat_alergi,catatan_khusus, keadaan_saat_transfer,perawatan_isolasi,kewaspadaan_transmisi
                        "transfer_pasien_antar_ruang.resiko_jatuh,transfer_pasien_antar_ruang.riwayat_alergi,transfer_pasien_antar_ruang.catatan_khusus,transfer_pasien_antar_ruang.kewaspadaan_transmisi, "+
                        "transfer_pasien_antar_ruang.keadaan_saat_transfer,transfer_pasien_antar_ruang.perawatan_isolasi, "+
                        //end tambah
                        //tambah petugas menyetujui dan dpjp
                        "transfer_pasien_antar_ruang.nip_menyerahkan,petugasmenyerahkan.nama as petugasmenyerahkan,transfer_pasien_antar_ruang.nip_menerima, "+
                        "petugasmenerima.nama as petugasmenerima, petugasmenyetujui.nama as petugasmenyetujui, transfer_pasien_antar_ruang.nip_menyetujui, "+
                        "dpj.nm_dokter as dpj, transfer_pasien_antar_ruang.dpjp "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join transfer_pasien_antar_ruang on reg_periksa.no_rawat=transfer_pasien_antar_ruang.no_rawat "+
                        "inner join petugas as petugasmenyerahkan on transfer_pasien_antar_ruang.nip_menyerahkan=petugasmenyerahkan.nip "+
                        "inner join pegawai as petugasmenyetujui on transfer_pasien_antar_ruang.nip_menyetujui=petugasmenyetujui.nik "+
                        "inner join dokter as dpj on transfer_pasien_antar_ruang.dpjp=dpj.kd_dokter "+
//                        "inner join pegawai on transfer_pasien_antar_ruang.nip_menyetujui=pegawai.nik "+
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
                    tabMode.addRow(new String[]{
                         //tambah kewaspadaan_transmisi, perawatan_isolasi, keadaan_saat_transfer, resiko_jatuh, riwayat_alergi, catatan_khusus
                        //ganti diagnosa_skunder menjadi dpjp
                        rs.getString("no_rawat"), rs.getString("no_rkm_medis"), rs.getString("nm_pasien"), rs.getString("tgl_lahir"), rs.getString("jk"), rs.getString("tanggal_masuk"),
                        rs.getString("tanggal_pindah"), rs.getString("indikasi_pindah_ruang"), rs.getString("keterangan_indikasi_pindah_ruang"), rs.getString("asal_ruang"), rs.getString("ruang_selanjutnya"),
                        rs.getString("metode_pemindahan_pasien"), rs.getString("diagnosa_utama"), rs.getString("dpjp"),rs.getString("dpj"), rs.getString("prosedur_yang_sudah_dilakukan"), rs.getString("obat_yang_telah_diberikan"),
                        rs.getString("pemeriksaan_penunjang_yang_dilakukan"), rs.getString("peralatan_yang_menyertai"), rs.getString("keterangan_peralatan_yang_menyertai"), rs.getString("pasien_keluarga_menyetujui"),
                        rs.getString("nama_menyetujui"), rs.getString("hubungan_menyetujui"),
                        //tambah kewaspadaan_transmisi, perawatan_isolasi
                        rs.getString("kewaspadaan_transmisi"), rs.getString("perawatan_isolasi"),
                        //end tambah kewaspadaan_transmisi,perawatan_isolasi,gda_sebelu_transfer,spo_sebelum_transfer,skala_nyeri_sebelum_transfer
                        rs.getString("keadaan_umum_sebelum_transfer"), rs.getString("td_sebelum_transfer"), rs.getString("nadi_sebelum_transfer"),
                        rs.getString("rr_sebelum_transfer"), rs.getString("suhu_sebelum_transfer"),rs.getString("gda_sebelum_transfer"),rs.getString("spo_sebelum_transfer"),rs.getString("skala_nyeri_sebelum_transfer"),rs.getString("berat_badan_sbt"),rs.getString("tinggi_badan_sbt"),rs.getString("keluhan_utama_sebelum_transfer") ,
                        //tambah keadaan_saat_transfer
                        rs.getString("keadaan_saat_transfer"),
                        //end keadaan_saat_transfer,gda_sesudah_transfer,spo_sesudah_transfer,skala_nyeri_sesudah_transfer
                        rs.getString("keadaan_umum_sesudah_transfer"),
                        rs.getString("td_sesudah_transfer"), rs.getString("nadi_sesudah_transfer"), rs.getString("rr_sesudah_transfer"), rs.getString("suhu_sesudah_transfer"),rs.getString("gda_sesudah_transfer"),rs.getString("spo_sesudah_transfer"),rs.getString("skala_nyeri_sesudah_transfer"),rs.getString("berat_badan_stt"),rs.getString("tinggi_badan_stt"), rs.getString("keluhan_utama_sesudah_transfer"),
                        //tambah resiko_jatuh,riwayat_alergi,catatan_khusus
                        rs.getString("resiko_jatuh"), rs.getString("riwayat_alergi"), rs.getString("catatan_khusus"),
                        //end tambah
                        rs.getString("nip_menyerahkan"), rs.getString("petugasmenyerahkan"), rs.getString("nip_menerima"), rs.getString("petugasmenerima"),
                        rs.getString("nip_menyetujui"),rs.getString("petugasmenyetujui")
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
        //tambah kewaspadaan transmisi, perawatan isolasi, keadaan saat transfer, resiko jatuh, riwayat alergi, catatan khusus
        TanggalMasuk.setDate(new Date());
        TanggalPindah.setDate(new Date());
        IndikasiPindah.setSelectedIndex(0);
        KeteranganIndikasiPindahRuang.setText("");
        AsalRuang.setText("");
        RuangSelanjutnya.setText("");
        MetodePemindahan.setSelectedIndex(0);
        DiagnosaUtama.setText("");
        KdDokter.setText("");//inputan untuk dokter DPJP
        ProsedurDilakukan.setText("");
        ObatYangDiberikan.setText("");
        PemeriksaanPenunjang.setText("");
        PeralatanMenyertai.setSelectedIndex(0);
        KeteranganPeralatan.setText("");
        MenyetujuiPemindahan.setSelectedIndex(0);
        NamaMenyetujui.setText("");
        HubunganMenyetujui.setSelectedIndex(0);
        KewaspadaanTransmisi.setSelectedIndex(0);
        PerawatanIsolasi.setSelectedIndex(0);
        KeadaanUmumSebelumTransfer.setSelectedIndex(0);
        SkalaNyeriSebelumTransfer.setSelectedIndex(0);
        BeratSbT.setText("");
        TinggiSbT.setText("");
        TDSebelumTransfer.setText("");
        NadiSebelumTransfer.setText("");
        RRSebelumTransfer.setText("");
        SuhuSebelumTransfer.setText("");
        GDASebelumTransfer.setText("");
        SPOSebelumTransfer.setText("");
        KeluhanUtamaSebelumTransfer.setText("");
        TDSaatTransfer.setSelectedIndex(0);
        KeadaanUmumSetelahTransfer.setSelectedIndex(0);
        SkalaNyeriSetelahTransfer.setSelectedIndex(0);
        BeratStT.setText("");
        TinggiStT.setText("");
        TDSetelahTransfer.setText("");
        NadiSetelahTransfer.setText("");
        RRSetelahTransfer.setText("");
        SuhuSetelahTransfer.setText("");
        GDASetelahTransfer.setText("");
        SPOSetelahTransfer.setText("");
        KeluhanUtamaSetelahTransfer.setText("");
        TabRawat.setSelectedIndex(0);
        IndikasiPindah.requestFocus();
        ResikoJatuh.setSelectedIndex(0);
        RiwayatAlergi.setText("");
        CatatanKhusus.setText("");
        KdPetugasMenyerahkan.setText("");
        NmPetugasMenyerahkan.setText("");
        KdPetugasMenyetujui.setText("");
        NmPetugasMenyetujui.setText("");
        KdPetugasMenerima.setText("");
        NmPetugasMenerima.setText("");
        NmDokter.setText("");
    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            //tambah kewaspadaan transmisi, perawatan isolasi, keadaan saat transfer, resiko jatuh, riwayat alergi, catatan khusus
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
            KdDokter.setText(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());//inputan untuk dokter DPJP
            NmDokter.setText(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());//inputan untuk dokter DPJP
            ProsedurDilakukan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString()); 
            ObatYangDiberikan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString()); 
            PemeriksaanPenunjang.setText(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString()); 
            PeralatanMenyertai.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString()); 
            KeteranganPeralatan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString()); 
            MenyetujuiPemindahan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString());
            NamaMenyetujui.setText(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString());  
            HubunganMenyetujui.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString());
            KewaspadaanTransmisi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString());
            PerawatanIsolasi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 24).toString());
            KeadaanUmumSebelumTransfer.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),25).toString());
            TDSebelumTransfer.setText(tbObat.getValueAt(tbObat.getSelectedRow(),26).toString()); 
            NadiSebelumTransfer.setText(tbObat.getValueAt(tbObat.getSelectedRow(),27).toString()); 
            RRSebelumTransfer.setText(tbObat.getValueAt(tbObat.getSelectedRow(),28).toString()); 
            SuhuSebelumTransfer.setText(tbObat.getValueAt(tbObat.getSelectedRow(),29).toString()); 
            GDASebelumTransfer.setText(tbObat.getValueAt(tbObat.getSelectedRow(),30).toString()); 
            SPOSebelumTransfer.setText(tbObat.getValueAt(tbObat.getSelectedRow(),31).toString());
            SkalaNyeriSebelumTransfer.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),32).toString());
            BeratSbT.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 33).toString());
            TinggiSbT.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 34).toString());
            KeluhanUtamaSebelumTransfer.setText(tbObat.getValueAt(tbObat.getSelectedRow(),35).toString());
            TDSaatTransfer.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 36).toString());
            KeadaanUmumSetelahTransfer.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),37).toString());
            TDSetelahTransfer.setText(tbObat.getValueAt(tbObat.getSelectedRow(),38).toString()); 
            NadiSetelahTransfer.setText(tbObat.getValueAt(tbObat.getSelectedRow(),39).toString()); 
            RRSetelahTransfer.setText(tbObat.getValueAt(tbObat.getSelectedRow(),40).toString()); 
            SuhuSetelahTransfer.setText(tbObat.getValueAt(tbObat.getSelectedRow(),41).toString()); 
            GDASetelahTransfer.setText(tbObat.getValueAt(tbObat.getSelectedRow(),42).toString()); 
            SPOSetelahTransfer.setText(tbObat.getValueAt(tbObat.getSelectedRow(),43).toString());
            SkalaNyeriSetelahTransfer.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),44).toString());
            BeratStT.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 45).toString());
            TinggiStT.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 46).toString());
            KeluhanUtamaSetelahTransfer.setText(tbObat.getValueAt(tbObat.getSelectedRow(),47).toString());
            ResikoJatuh.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),48).toString());
            RiwayatAlergi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),49).toString());
            CatatanKhusus.setText(tbObat.getValueAt(tbObat.getSelectedRow(),50).toString());
            KdPetugasMenyerahkan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),51).toString()); 
            NmPetugasMenyerahkan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),52).toString()); 
            KdPetugasMenerima.setText(tbObat.getValueAt(tbObat.getSelectedRow(),53).toString()); 
            NmPetugasMenerima.setText(tbObat.getValueAt(tbObat.getSelectedRow(),54).toString());
            KdPetugasMenyetujui.setText(tbObat.getValueAt(tbObat.getSelectedRow(),55).toString());
            NmPetugasMenyetujui.setText(tbObat.getValueAt(tbObat.getSelectedRow(),56).toString());
            //Tgl.setText(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString());
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

    //CUSTOM RS ISLAM LMJ - Senin, 23 Februari 2026
    //[FEAT] KEWASPADAAN TRANSMISI, PERAWATAN ISOLASI, KEADAAN SAAT TRANSFER, RESIKO JATUH, RIWAYAT ALERGI, CATATAN KHUSUS
    //GANTI DIAGNOSA SKUNDER MENJADI DPJP
    private void ganti() {
        if(Sequel.mengedittf("transfer_pasien_antar_ruang","no_rawat=? and tanggal_masuk=?","no_rawat=?,tanggal_masuk=?,tanggal_pindah=?,asal_ruang=?,ruang_selanjutnya=?,diagnosa_utama=?,"+
                "dpjp=?,indikasi_pindah_ruang=?,keterangan_indikasi_pindah_ruang=?,prosedur_yang_sudah_dilakukan=?,obat_yang_telah_diberikan=?,metode_pemindahan_pasien=?,"+
                "peralatan_yang_menyertai=?,keterangan_peralatan_yang_menyertai=?,pemeriksaan_penunjang_yang_dilakukan=?,pasien_keluarga_menyetujui=?,nama_menyetujui=?,hubungan_menyetujui=?,"+
                "keluhan_utama_sebelum_transfer=?,keadaan_umum_sebelum_transfer=?,td_sebelum_transfer=?,nadi_sebelum_transfer=?,rr_sebelum_transfer=?,suhu_sebelum_transfer=?,gda_sebelum_transfer=?,spo_sebelum_transfer=?,skala_nyeri_sebelum_transfer=?,berat_badan_sbt=?,tinggi_badan_sbt=?,"+
                "keluhan_utama_sesudah_transfer=?,keadaan_umum_sesudah_transfer=?,td_sesudah_transfer=?,nadi_sesudah_transfer=?,rr_sesudah_transfer=?,suhu_sesudah_transfer=?,gda_sesudah_transfer=?,spo_sesudah_transfer=?,skala_nyeri_sesudah_transfer=?,berat_badan_stt=?,tinggi_badan_stt=?,"+
                "nip_menyerahkan=?,nip_menerima=?,keadaan_saat_transfer=?,kewaspadaan_transmisi=?,perawatan_isolasi=?,resiko_jatuh=?,riwayat_alergi=?,catatan_khusus=?,nip_menyetujui=?",51,new String[]{
                    //tambah KewaspadaanTransmisi, PerawatanIsolasi, TDSaatTransfer, ResikoJatuh, RiwayatAlergi, Catatankhusus
                    TNoRw.getText(), Valid.SetTgl(TanggalMasuk.getSelectedItem() + "") + " " + TanggalMasuk.getSelectedItem().toString().substring(11, 19),
                    Valid.SetTgl(TanggalPindah.getSelectedItem() + "") + " " + TanggalPindah.getSelectedItem().toString().substring(11, 19), AsalRuang.getText(),
                    RuangSelanjutnya.getText(), DiagnosaUtama.getText(), KdDokter.getText(), IndikasiPindah.getSelectedItem().toString(), KeteranganIndikasiPindahRuang.getText(),
                    ProsedurDilakukan.getText(), ObatYangDiberikan.getText(), MetodePemindahan.getSelectedItem().toString(), PeralatanMenyertai.getSelectedItem().toString(),
                    KeteranganPeralatan.getText(), PemeriksaanPenunjang.getText(), MenyetujuiPemindahan.getSelectedItem().toString(), NamaMenyetujui.getText(),
                    HubunganMenyetujui.getSelectedItem().toString(), KeluhanUtamaSebelumTransfer.getText(), KeadaanUmumSebelumTransfer.getSelectedItem().toString(),
                    TDSebelumTransfer.getText(), NadiSebelumTransfer.getText(), RRSebelumTransfer.getText(), SuhuSebelumTransfer.getText(),GDASebelumTransfer.getText(),SPOSebelumTransfer.getText(),SkalaNyeriSebelumTransfer.getSelectedItem().toString(),BeratSbT.getText(),TinggiSbT.getText(), KeluhanUtamaSetelahTransfer.getText(),
                    KeadaanUmumSetelahTransfer.getSelectedItem().toString(), TDSetelahTransfer.getText(), NadiSetelahTransfer.getText(), RRSetelahTransfer.getText(),
                    SuhuSetelahTransfer.getText(),GDASetelahTransfer.getText(),SPOSetelahTransfer.getText(),SkalaNyeriSetelahTransfer.getSelectedItem().toString(),BeratStT.getText(),TinggiStT.getText(), KdPetugasMenyerahkan.getText(), KdPetugasMenerima.getText(), TDSaatTransfer.getSelectedItem().toString(), KewaspadaanTransmisi.getSelectedItem().toString(),
                    PerawatanIsolasi.getSelectedItem().toString(), ResikoJatuh.getSelectedItem().toString(), RiwayatAlergi.getText(), CatatanKhusus.getText(),  KdPetugasMenyetujui.getText(), tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString(),
                    tbObat.getValueAt(tbObat.getSelectedRow(), 5).toString()
                
            })==true){
            //tambah KewaspadaanTransmisi, PerawatanIsolasi, TDSaatTransfer, ResikoJatuh, RiwayatAlergi, Catatankhusus
                tbObat.setValueAt(TNoRw.getText(),tbObat.getSelectedRow(),0);
                tbObat.setValueAt(TNoRM.getText(),tbObat.getSelectedRow(),1);
                tbObat.setValueAt(TPasien.getText(),tbObat.getSelectedRow(),2);
                tbObat.setValueAt(TglLahir.getText(),tbObat.getSelectedRow(),3);
                tbObat.setValueAt(Jk.getText(),tbObat.getSelectedRow(),4);
                tbObat.setValueAt(Valid.SetTgl(TanggalMasuk.getSelectedItem()+"")+" "+TanggalMasuk.getSelectedItem().toString().substring(11,19),tbObat.getSelectedRow(),5);
                tbObat.setValueAt(Valid.SetTgl(TanggalPindah.getSelectedItem()+"")+" "+TanggalPindah.getSelectedItem().toString().substring(11,19),tbObat.getSelectedRow(),6);
                IndikasiPindah.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString()); 
                KeteranganIndikasiPindahRuang.setText(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
                AsalRuang.setText(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
                RuangSelanjutnya.setText(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString()); 
                MetodePemindahan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString()); 
                DiagnosaUtama.setText(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString()); 
                KdDokter.setText(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString()); 
                ProsedurDilakukan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString()); 
                ObatYangDiberikan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString()); 
                PemeriksaanPenunjang.setText(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString()); 
                PeralatanMenyertai.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString()); 
                KeteranganPeralatan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString()); 
                MenyetujuiPemindahan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
                NamaMenyetujui.setText(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString());  
                HubunganMenyetujui.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString());
                KewaspadaanTransmisi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString());
                PerawatanIsolasi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 23).toString());
                KeadaanUmumSebelumTransfer.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),24).toString());
                TDSebelumTransfer.setText(tbObat.getValueAt(tbObat.getSelectedRow(),25).toString()); 
                NadiSebelumTransfer.setText(tbObat.getValueAt(tbObat.getSelectedRow(),26).toString()); 
                RRSebelumTransfer.setText(tbObat.getValueAt(tbObat.getSelectedRow(),27).toString()); 
                SuhuSebelumTransfer.setText(tbObat.getValueAt(tbObat.getSelectedRow(),28).toString()); 
                GDASebelumTransfer.setText(tbObat.getValueAt(tbObat.getSelectedRow(),29).toString()); 
                SPOSebelumTransfer.setText(tbObat.getValueAt(tbObat.getSelectedRow(),30).toString());
                SkalaNyeriSebelumTransfer.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 31).toString());
                BeratSbT.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 32).toString());
                TinggiSbT.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 33).toString());
                KeluhanUtamaSebelumTransfer.setText(tbObat.getValueAt(tbObat.getSelectedRow(),34).toString());
                TDSaatTransfer.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 35).toString());
                KeadaanUmumSetelahTransfer.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),36).toString());
                TDSetelahTransfer.setText(tbObat.getValueAt(tbObat.getSelectedRow(),37).toString()); 
                NadiSetelahTransfer.setText(tbObat.getValueAt(tbObat.getSelectedRow(),38).toString()); 
                RRSetelahTransfer.setText(tbObat.getValueAt(tbObat.getSelectedRow(),39).toString()); 
                SuhuSetelahTransfer.setText(tbObat.getValueAt(tbObat.getSelectedRow(),40).toString()); 
                GDASetelahTransfer.setText(tbObat.getValueAt(tbObat.getSelectedRow(),41).toString()); 
                SPOSetelahTransfer.setText(tbObat.getValueAt(tbObat.getSelectedRow(),42).toString());
                SkalaNyeriSetelahTransfer.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),43).toString());
                BeratStT.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 44).toString());
                TinggiStT.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 45).toString());
                KeluhanUtamaSetelahTransfer.setText(tbObat.getValueAt(tbObat.getSelectedRow(),46).toString());
                ResikoJatuh.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 47).toString());
                RiwayatAlergi.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 48).toString());
                CatatanKhusus.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 49).toString());
                KdPetugasMenyerahkan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),50).toString()); 
                NmPetugasMenyerahkan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),51).toString()); 
                KdPetugasMenerima.setText(tbObat.getValueAt(tbObat.getSelectedRow(),52).toString()); 
                NmPetugasMenerima.setText(tbObat.getValueAt(tbObat.getSelectedRow(),53).toString()); 
                KdPetugasMenyetujui.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 54).toString());
                NmPetugasMenyetujui.setText(tbObat.getValueAt(tbObat.getSelectedRow(),55).toString());
                emptTeks();
                TabRawat.setSelectedIndex(1);
        }
    }

    //CUSTOM RS ISLAM LMJ - Senin, 23 Februari 2026
    //[FEAT] KEADAAN SAAT TRANSFER, KEWASPADAAN TRANSMISI, PERAWATAN ISOLASI, RESIKO JATUH, RIWAYAT ALERFI, CATATAN KHUSUS, DPJP
    //DIAGNOSASEKUNDER UNTUK AMBIL INPUTAN DARI DOKTER DPJP
    private void simpan() {
        if(Sequel.menyimpantf("transfer_pasien_antar_ruang","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat & Tanggal Masuk",50,new String[]{
                TNoRw.getText(),Valid.SetTgl(TanggalMasuk.getSelectedItem()+"")+" "+TanggalMasuk.getSelectedItem().toString().substring(11,19),
                Valid.SetTgl(TanggalPindah.getSelectedItem()+"")+" "+TanggalPindah.getSelectedItem().toString().substring(11,19),AsalRuang.getText(), 
                RuangSelanjutnya.getText(),DiagnosaUtama.getText(),"-",IndikasiPindah.getSelectedItem().toString(),KeteranganIndikasiPindahRuang.getText(), 
                ProsedurDilakukan.getText(),ObatYangDiberikan.getText(),MetodePemindahan.getSelectedItem().toString(),PeralatanMenyertai.getSelectedItem().toString(),
                KeteranganPeralatan.getText(),PemeriksaanPenunjang.getText(),MenyetujuiPemindahan.getSelectedItem().toString(),NamaMenyetujui.getText(),
                HubunganMenyetujui.getSelectedItem().toString(),KeluhanUtamaSebelumTransfer.getText(),KeadaanUmumSebelumTransfer.getSelectedItem().toString(),
                TDSebelumTransfer.getText(),NadiSebelumTransfer.getText(),RRSebelumTransfer.getText(),SuhuSebelumTransfer.getText(),GDASebelumTransfer.getText(),SPOSebelumTransfer.getText(),SkalaNyeriSebelumTransfer.getSelectedItem().toString(),BeratSbT.getText(),TinggiSbT.getText(),KeluhanUtamaSetelahTransfer.getText(),
                KeadaanUmumSetelahTransfer.getSelectedItem().toString(),TDSetelahTransfer.getText(),NadiSetelahTransfer.getText(),RRSetelahTransfer.getText(), 
                SuhuSetelahTransfer.getText(),GDASetelahTransfer.getText(),SPOSetelahTransfer.getText(),SkalaNyeriSetelahTransfer.getSelectedItem().toString(),BeratStT.getText(),TinggiStT.getText(),KdPetugasMenyerahkan.getText(),KdPetugasMenerima.getText(), TDSaatTransfer.getSelectedItem().toString() ,KewaspadaanTransmisi.getSelectedItem().toString(),
                PerawatanIsolasi.getSelectedItem().toString(), ResikoJatuh.getSelectedItem().toString(), RiwayatAlergi.getText(), CatatanKhusus.getText(), KdDokter.getText(),//isian untuk dpjp
                KdPetugasMenyetujui.getText()
                
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

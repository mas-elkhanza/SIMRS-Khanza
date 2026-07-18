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
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import kepegawaian.DlgCariPegawai;


/**
 *
 * @author perpustakaan
 */
public final class RMChecklistKriteriaMasukIsolasi extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private volatile boolean ceksukses = false;
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;    
    private String finger="";
    private StringBuilder htmlContent;
    private String TANGGALMUNDUR="yes";
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public RMChecklistKriteriaMasukIsolasi(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(628,674);

        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.RM","Nama Pasien","Tgl.Lahir","J.K.","Tanggal","TB Paru","Campak","Varisela",
            "Herpes Zoster","Airborne Lainnya","Covid19","Influenza","Difteri","Pertusis","Maningitis",
            "Droplet Lainnya","MDRO (MRSA/VRE/CRE/ESBL)","Clostridioides Difficile","Skabies","Luka Dengan Drainase",
            "Diare Infeksius","Kontak Lainnya","Risiko Kontak Erat","Riwayat Daerah Wabah","Riwayat MDRO",
            "Lab & Rontgen Positif","Gejala Klinis","Imunokompromis Berat","Instruksi DPJP","Ada Persetujuan Isolasi",
            "Kelengkapan Jaminan","Ketersediaan APD","Fas Cuci Tangan Siap","Tekanan Negatif Berfungsi",
            "Pintu Otomatis Berfungsi","Keputusan","Jenis Isolasi","Diagnosa Isolasi","Keterangan/Catatn",
            "NIP/Kode Dokter","DPJP/Dokter Jaga/IGD"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 42; i++) {
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
                column.setPreferredWidth(25);
            }else if(i==5){
                column.setPreferredWidth(115);
            }else if(i==6){
                column.setPreferredWidth(45);
            }else if(i==7){
                column.setPreferredWidth(48);
            }else if(i==8){
                column.setPreferredWidth(48);
            }else if(i==9){
                column.setPreferredWidth(76);
            }else if(i==10){
                column.setPreferredWidth(90);
            }else if(i==11){
                column.setPreferredWidth(47);
            }else if(i==12){
                column.setPreferredWidth(54);
            }else if(i==13){
                column.setPreferredWidth(39);
            }else if(i==14){
                column.setPreferredWidth(47);
            }else if(i==15){
                column.setPreferredWidth(58);
            }else if(i==16){
                column.setPreferredWidth(84);
            }else if(i==17){
                column.setPreferredWidth(151);
            }else if(i==18){
                column.setPreferredWidth(116);
            }else if(i==19){
                column.setPreferredWidth(45);
            }else if(i==20){
                column.setPreferredWidth(116);
            }else if(i==21){
                column.setPreferredWidth(80);
            }else if(i==22){
                column.setPreferredWidth(80);
            }else if(i==23){
                column.setPreferredWidth(96);
            }else if(i==24){
                column.setPreferredWidth(121);
            }else if(i==25){
                column.setPreferredWidth(79);
            }else if(i==26){
                column.setPreferredWidth(112);
            }else if(i==27){
                column.setPreferredWidth(68);
            }else if(i==28){
                column.setPreferredWidth(120);
            }else if(i==29){
                column.setPreferredWidth(77);
            }else if(i==30){
                column.setPreferredWidth(123);
            }else if(i==31){
                column.setPreferredWidth(114);
            }else if(i==32){
                column.setPreferredWidth(95);
            }else if(i==33){
                column.setPreferredWidth(112);
            }else if(i==34){
                column.setPreferredWidth(137);
            }else if(i==35){
                column.setPreferredWidth(130);
            }else if(i==36){
                column.setPreferredWidth(128);
            }else if(i==37){
                column.setPreferredWidth(90);
            }else if(i==38){
                column.setPreferredWidth(200);
            }else if(i==39){
                column.setPreferredWidth(200);
            }else if(i==40){
                column.setPreferredWidth(90);
            }else if(i==41){
                column.setPreferredWidth(150);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());

        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        TCari.setDocument(new batasInput((int)100).getKata(TCari));
        DiagnosaIsolasi.setDocument(new batasInput((int)100).getKata(DiagnosaIsolasi));
        Keterangan.setDocument(new batasInput((int)100).getKata(Keterangan));
        
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
        MnKriteriaMasukICU = new javax.swing.JMenuItem();
        LoadHTML = new widget.editorpane();
        JK = new widget.TextBox();
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
        TNoRM = new widget.TextBox();
        jLabel16 = new widget.Label();
        jLabel8 = new widget.Label();
        TglLahir = new widget.TextBox();
        Tanggal = new widget.Tanggal();
        jLabel23 = new widget.Label();
        KodePetugas = new widget.TextBox();
        NamaPetugas = new widget.TextBox();
        btnPetugas = new widget.Button();
        AirboneVarisela = new widget.ComboBox();
        jLabel58 = new widget.Label();
        jLabel5 = new widget.Label();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel53 = new widget.Label();
        jLabel59 = new widget.Label();
        jLabel61 = new widget.Label();
        AirboneHerpes = new widget.ComboBox();
        AirboneCampak = new widget.ComboBox();
        jLabel63 = new widget.Label();
        jLabel64 = new widget.Label();
        AirboneTBParu = new widget.ComboBox();
        jLabel65 = new widget.Label();
        jLabel66 = new widget.Label();
        AirboneLainnya = new widget.ComboBox();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel54 = new widget.Label();
        jLabel67 = new widget.Label();
        DropletCovid = new widget.ComboBox();
        jLabel68 = new widget.Label();
        jLabel70 = new widget.Label();
        DropletPertusis = new widget.ComboBox();
        jLabel71 = new widget.Label();
        jLabel72 = new widget.Label();
        DropletInfluenza = new widget.ComboBox();
        jLabel73 = new widget.Label();
        DropletMeningitis = new widget.ComboBox();
        jLabel75 = new widget.Label();
        DropletLainnya = new widget.ComboBox();
        jLabel76 = new widget.Label();
        DropletDifteri = new widget.ComboBox();
        jSeparator5 = new javax.swing.JSeparator();
        jSeparator6 = new javax.swing.JSeparator();
        jLabel55 = new widget.Label();
        jLabel78 = new widget.Label();
        jLabel79 = new widget.Label();
        KontakMDRO = new widget.ComboBox();
        jLabel80 = new widget.Label();
        KontakSkabies = new widget.ComboBox();
        jLabel81 = new widget.Label();
        jLabel82 = new widget.Label();
        KontakClostridioides = new widget.ComboBox();
        jLabel83 = new widget.Label();
        KontakLainnya = new widget.ComboBox();
        jSeparator7 = new javax.swing.JSeparator();
        jSeparator8 = new javax.swing.JSeparator();
        jLabel56 = new widget.Label();
        jLabel84 = new widget.Label();
        RisikoKontakErat = new widget.ComboBox();
        jLabel85 = new widget.Label();
        jLabel86 = new widget.Label();
        RisikoRiwayatDaerahWabah = new widget.ComboBox();
        jLabel87 = new widget.Label();
        RisikoRiwayatMDRO = new widget.ComboBox();
        jSeparator9 = new javax.swing.JSeparator();
        jSeparator10 = new javax.swing.JSeparator();
        Keputusan = new widget.ComboBox();
        jLabel57 = new widget.Label();
        jLabel91 = new widget.Label();
        jLabel92 = new widget.Label();
        jLabel97 = new widget.Label();
        JenisIsolasi = new widget.ComboBox();
        jLabel116 = new widget.Label();
        KontakDiareInfeksius = new widget.ComboBox();
        jLabel117 = new widget.Label();
        KontakLukaDrainase = new widget.ComboBox();
        jLabel9 = new widget.Label();
        Keterangan = new widget.TextBox();
        jLabel10 = new widget.Label();
        DiagnosaIsolasi = new widget.TextBox();
        jSeparator11 = new javax.swing.JSeparator();
        jSeparator12 = new javax.swing.JSeparator();
        jLabel60 = new widget.Label();
        jLabel88 = new widget.Label();
        jLabel89 = new widget.Label();
        RisikoHasilLabRadPositif = new widget.ComboBox();
        jLabel90 = new widget.Label();
        RisikoGejalaKlinis = new widget.ComboBox();
        jLabel93 = new widget.Label();
        jLabel94 = new widget.Label();
        RisikoPasienImunokompromis = new widget.ComboBox();
        jLabel95 = new widget.Label();
        jLabel96 = new widget.Label();
        InstruksiDPJP = new widget.ComboBox();
        jLabel98 = new widget.Label();
        AdaPersetujuanTindakan = new widget.ComboBox();
        jLabel99 = new widget.Label();
        jLabel100 = new widget.Label();
        KelengkapanJaminan = new widget.ComboBox();
        jSeparator13 = new javax.swing.JSeparator();
        jSeparator14 = new javax.swing.JSeparator();
        jLabel62 = new widget.Label();
        jLabel101 = new widget.Label();
        KetersediaanAPD = new widget.ComboBox();
        jLabel102 = new widget.Label();
        FasilitasCuciTangan = new widget.ComboBox();
        jLabel103 = new widget.Label();
        jLabel104 = new widget.Label();
        TekananUdaraNegatif = new widget.ComboBox();
        jLabel105 = new widget.Label();
        PintuKamarOtomatis = new widget.ComboBox();
        jLabel106 = new widget.Label();
        jLabel107 = new widget.Label();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnKriteriaMasukICU.setBackground(new java.awt.Color(255, 255, 254));
        MnKriteriaMasukICU.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnKriteriaMasukICU.setForeground(new java.awt.Color(50, 50, 50));
        MnKriteriaMasukICU.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnKriteriaMasukICU.setText("Formulir Checklist Kriteria Masuk ICU");
        MnKriteriaMasukICU.setName("MnKriteriaMasukICU"); // NOI18N
        MnKriteriaMasukICU.setPreferredSize(new java.awt.Dimension(260, 26));
        MnKriteriaMasukICU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnKriteriaMasukICUActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnKriteriaMasukICU);

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N

        JK.setHighlighter(null);
        JK.setName("JK"); // NOI18N

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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Check List Kriteria Masuk Isolasi ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "12-07-2026" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "12-07-2026" }));
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
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 386));
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
        FormInput.setPreferredSize(new java.awt.Dimension(100, 663));
        FormInput.setLayout(null);

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel4.setText("No.Rawat");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(21, 10, 75, 23);

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

        jLabel8.setText("Tgl.Lahir :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(625, 10, 60, 23);

        TglLahir.setHighlighter(null);
        TglLahir.setName("TglLahir"); // NOI18N
        FormInput.add(TglLahir);
        TglLahir.setBounds(689, 10, 100, 23);

        Tanggal.setForeground(new java.awt.Color(50, 70, 50));
        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "12-07-2026 23:03:46" }));
        Tanggal.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.setOpaque(false);
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeyPressed(evt);
            }
        });
        FormInput.add(Tanggal);
        Tanggal.setBounds(79, 40, 130, 23);

        jLabel23.setText("DPJP / Dokter Jaga / IGD :");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(221, 40, 160, 23);

        KodePetugas.setEditable(false);
        KodePetugas.setHighlighter(null);
        KodePetugas.setName("KodePetugas"); // NOI18N
        FormInput.add(KodePetugas);
        KodePetugas.setBounds(385, 40, 127, 23);

        NamaPetugas.setEditable(false);
        NamaPetugas.setName("NamaPetugas"); // NOI18N
        FormInput.add(NamaPetugas);
        NamaPetugas.setBounds(514, 40, 245, 23);

        btnPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPetugas.setMnemonic('2');
        btnPetugas.setToolTipText("ALt+2");
        btnPetugas.setName("btnPetugas"); // NOI18N
        btnPetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPetugasActionPerformed(evt);
            }
        });
        btnPetugas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnPetugasKeyPressed(evt);
            }
        });
        FormInput.add(btnPetugas);
        btnPetugas.setBounds(761, 40, 28, 23);

        AirboneVarisela.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        AirboneVarisela.setSelectedIndex(1);
        AirboneVarisela.setName("AirboneVarisela"); // NOI18N
        AirboneVarisela.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AirboneVariselaKeyPressed(evt);
            }
        });
        FormInput.add(AirboneVarisela);
        AirboneVarisela.setBounds(90, 120, 90, 23);

        jLabel58.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel58.setText("Varisela");
        jLabel58.setName("jLabel58"); // NOI18N
        FormInput.add(jLabel58);
        jLabel58.setBounds(40, 120, 70, 23);

        jLabel5.setText(":");
        jLabel5.setName("jLabel5"); // NOI18N
        FormInput.add(jLabel5);
        jLabel5.setBounds(0, 10, 75, 23);

        jSeparator1.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator1.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator1.setName("jSeparator1"); // NOI18N
        FormInput.add(jSeparator1);
        jSeparator1.setBounds(0, 70, 810, 1);

        jSeparator2.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator2.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator2.setName("jSeparator2"); // NOI18N
        FormInput.add(jSeparator2);
        jSeparator2.setBounds(0, 70, 810, 1);

        jLabel53.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel53.setText("I. AIRBONE DISEASE");
        jLabel53.setName("jLabel53"); // NOI18N
        FormInput.add(jLabel53);
        jLabel53.setBounds(10, 70, 180, 23);

        jLabel59.setText(":");
        jLabel59.setName("jLabel59"); // NOI18N
        FormInput.add(jLabel59);
        jLabel59.setBounds(0, 120, 86, 23);

        jLabel61.setText("Herpes Zoster Diseminata :");
        jLabel61.setName("jLabel61"); // NOI18N
        FormInput.add(jLabel61);
        jLabel61.setBounds(535, 90, 160, 23);

        AirboneHerpes.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        AirboneHerpes.setSelectedIndex(1);
        AirboneHerpes.setName("AirboneHerpes"); // NOI18N
        AirboneHerpes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AirboneHerpesKeyPressed(evt);
            }
        });
        FormInput.add(AirboneHerpes);
        AirboneHerpes.setBounds(699, 90, 90, 23);

        AirboneCampak.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        AirboneCampak.setSelectedIndex(1);
        AirboneCampak.setName("AirboneCampak"); // NOI18N
        AirboneCampak.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AirboneCampakKeyPressed(evt);
            }
        });
        FormInput.add(AirboneCampak);
        AirboneCampak.setBounds(379, 90, 90, 23);

        jLabel63.setText("Campak :");
        jLabel63.setName("jLabel63"); // NOI18N
        FormInput.add(jLabel63);
        jLabel63.setBounds(305, 90, 70, 23);

        jLabel64.setText(":");
        jLabel64.setName("jLabel64"); // NOI18N
        FormInput.add(jLabel64);
        jLabel64.setBounds(0, 90, 149, 23);

        AirboneTBParu.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        AirboneTBParu.setSelectedIndex(1);
        AirboneTBParu.setName("AirboneTBParu"); // NOI18N
        AirboneTBParu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AirboneTBParuKeyPressed(evt);
            }
        });
        FormInput.add(AirboneTBParu);
        AirboneTBParu.setBounds(153, 90, 90, 23);

        jLabel65.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel65.setText("TB Paru Aktif/Suspek");
        jLabel65.setName("jLabel65"); // NOI18N
        FormInput.add(jLabel65);
        jLabel65.setBounds(40, 90, 110, 23);

        jLabel66.setText("Airbone Lainnya :");
        jLabel66.setName("jLabel66"); // NOI18N
        FormInput.add(jLabel66);
        jLabel66.setBounds(221, 120, 130, 23);

        AirboneLainnya.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        AirboneLainnya.setSelectedIndex(1);
        AirboneLainnya.setName("AirboneLainnya"); // NOI18N
        AirboneLainnya.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AirboneLainnyaKeyPressed(evt);
            }
        });
        FormInput.add(AirboneLainnya);
        AirboneLainnya.setBounds(355, 120, 90, 23);

        jSeparator3.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator3.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator3.setName("jSeparator3"); // NOI18N
        FormInput.add(jSeparator3);
        jSeparator3.setBounds(0, 150, 810, 1);

        jSeparator4.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator4.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator4.setName("jSeparator4"); // NOI18N
        FormInput.add(jSeparator4);
        jSeparator4.setBounds(0, 150, 810, 1);

        jLabel54.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel54.setText("II. DROPLET DISEASE");
        jLabel54.setName("jLabel54"); // NOI18N
        FormInput.add(jLabel54);
        jLabel54.setBounds(10, 150, 180, 23);

        jLabel67.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel67.setText("COVID-19");
        jLabel67.setName("jLabel67"); // NOI18N
        FormInput.add(jLabel67);
        jLabel67.setBounds(40, 170, 70, 23);

        DropletCovid.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        DropletCovid.setSelectedIndex(1);
        DropletCovid.setName("DropletCovid"); // NOI18N
        DropletCovid.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DropletCovidKeyPressed(evt);
            }
        });
        FormInput.add(DropletCovid);
        DropletCovid.setBounds(100, 170, 90, 23);

        jLabel68.setText(":");
        jLabel68.setName("jLabel68"); // NOI18N
        FormInput.add(jLabel68);
        jLabel68.setBounds(0, 170, 96, 23);

        jLabel70.setText("Pertusis :");
        jLabel70.setName("jLabel70"); // NOI18N
        FormInput.add(jLabel70);
        jLabel70.setBounds(221, 170, 80, 23);

        DropletPertusis.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        DropletPertusis.setSelectedIndex(1);
        DropletPertusis.setName("DropletPertusis"); // NOI18N
        DropletPertusis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DropletPertusisKeyPressed(evt);
            }
        });
        FormInput.add(DropletPertusis);
        DropletPertusis.setBounds(305, 170, 90, 23);

        jLabel71.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel71.setText("Influenza");
        jLabel71.setName("jLabel71"); // NOI18N
        FormInput.add(jLabel71);
        jLabel71.setBounds(40, 200, 60, 23);

        jLabel72.setText(":");
        jLabel72.setName("jLabel72"); // NOI18N
        FormInput.add(jLabel72);
        jLabel72.setBounds(0, 200, 93, 23);

        DropletInfluenza.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        DropletInfluenza.setSelectedIndex(1);
        DropletInfluenza.setName("DropletInfluenza"); // NOI18N
        DropletInfluenza.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DropletInfluenzaKeyPressed(evt);
            }
        });
        FormInput.add(DropletInfluenza);
        DropletInfluenza.setBounds(97, 200, 90, 23);

        jLabel73.setText("Meningitis :");
        jLabel73.setName("jLabel73"); // NOI18N
        FormInput.add(jLabel73);
        jLabel73.setBounds(440, 170, 70, 23);

        DropletMeningitis.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        DropletMeningitis.setSelectedIndex(1);
        DropletMeningitis.setName("DropletMeningitis"); // NOI18N
        DropletMeningitis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DropletMeningitisKeyPressed(evt);
            }
        });
        FormInput.add(DropletMeningitis);
        DropletMeningitis.setBounds(514, 170, 90, 23);

        jLabel75.setText("Droplet Lainnya :");
        jLabel75.setName("jLabel75"); // NOI18N
        FormInput.add(jLabel75);
        jLabel75.setBounds(197, 200, 140, 23);

        DropletLainnya.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        DropletLainnya.setSelectedIndex(1);
        DropletLainnya.setName("DropletLainnya"); // NOI18N
        DropletLainnya.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DropletLainnyaKeyPressed(evt);
            }
        });
        FormInput.add(DropletLainnya);
        DropletLainnya.setBounds(341, 200, 90, 23);

        jLabel76.setText("Difteri :");
        jLabel76.setName("jLabel76"); // NOI18N
        FormInput.add(jLabel76);
        jLabel76.setBounds(610, 170, 85, 23);

        DropletDifteri.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        DropletDifteri.setSelectedIndex(1);
        DropletDifteri.setName("DropletDifteri"); // NOI18N
        DropletDifteri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DropletDifteriKeyPressed(evt);
            }
        });
        FormInput.add(DropletDifteri);
        DropletDifteri.setBounds(699, 170, 90, 23);

        jSeparator5.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator5.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator5.setName("jSeparator5"); // NOI18N
        FormInput.add(jSeparator5);
        jSeparator5.setBounds(0, 230, 810, 1);

        jSeparator6.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator6.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator6.setName("jSeparator6"); // NOI18N
        FormInput.add(jSeparator6);
        jSeparator6.setBounds(0, 230, 810, 1);

        jLabel55.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel55.setText("III. KONTAK");
        jLabel55.setName("jLabel55"); // NOI18N
        FormInput.add(jLabel55);
        jLabel55.setBounds(10, 230, 180, 23);

        jLabel78.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel78.setText("MDRO (MRSA/VRE/CRE/ESBL)");
        jLabel78.setName("jLabel78"); // NOI18N
        FormInput.add(jLabel78);
        jLabel78.setBounds(40, 250, 160, 23);

        jLabel79.setText(":");
        jLabel79.setName("jLabel79"); // NOI18N
        FormInput.add(jLabel79);
        jLabel79.setBounds(0, 250, 191, 23);

        KontakMDRO.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        KontakMDRO.setSelectedIndex(1);
        KontakMDRO.setName("KontakMDRO"); // NOI18N
        KontakMDRO.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KontakMDROKeyPressed(evt);
            }
        });
        FormInput.add(KontakMDRO);
        KontakMDRO.setBounds(195, 250, 90, 23);

        jLabel80.setText("Skabies :");
        jLabel80.setName("jLabel80"); // NOI18N
        FormInput.add(jLabel80);
        jLabel80.setBounds(595, 250, 100, 23);

        KontakSkabies.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        KontakSkabies.setSelectedIndex(1);
        KontakSkabies.setName("KontakSkabies"); // NOI18N
        KontakSkabies.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KontakSkabiesKeyPressed(evt);
            }
        });
        FormInput.add(KontakSkabies);
        KontakSkabies.setBounds(699, 250, 90, 23);

        jLabel81.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel81.setText("Clostridioides Difficile");
        jLabel81.setName("jLabel81"); // NOI18N
        FormInput.add(jLabel81);
        jLabel81.setBounds(40, 280, 120, 23);

        jLabel82.setText(":");
        jLabel82.setName("jLabel82"); // NOI18N
        FormInput.add(jLabel82);
        jLabel82.setBounds(0, 280, 155, 23);

        KontakClostridioides.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        KontakClostridioides.setSelectedIndex(1);
        KontakClostridioides.setName("KontakClostridioides"); // NOI18N
        KontakClostridioides.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KontakClostridioidesKeyPressed(evt);
            }
        });
        FormInput.add(KontakClostridioides);
        KontakClostridioides.setBounds(159, 280, 90, 23);

        jLabel83.setText("Luka Dengan Drainase :");
        jLabel83.setName("jLabel83"); // NOI18N
        FormInput.add(jLabel83);
        jLabel83.setBounds(300, 250, 170, 23);

        KontakLainnya.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        KontakLainnya.setSelectedIndex(1);
        KontakLainnya.setName("KontakLainnya"); // NOI18N
        KontakLainnya.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KontakLainnyaKeyPressed(evt);
            }
        });
        FormInput.add(KontakLainnya);
        KontakLainnya.setBounds(699, 280, 90, 23);

        jSeparator7.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator7.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator7.setName("jSeparator7"); // NOI18N
        FormInput.add(jSeparator7);
        jSeparator7.setBounds(0, 310, 810, 1);

        jSeparator8.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator8.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator8.setName("jSeparator8"); // NOI18N
        FormInput.add(jSeparator8);
        jSeparator8.setBounds(0, 310, 810, 1);

        jLabel56.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel56.setText("IV. FAKTOR RISIKO");
        jLabel56.setName("jLabel56"); // NOI18N
        FormInput.add(jLabel56);
        jLabel56.setBounds(10, 310, 350, 23);

        jLabel84.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel84.setText("Kontak Erat");
        jLabel84.setName("jLabel84"); // NOI18N
        FormInput.add(jLabel84);
        jLabel84.setBounds(40, 330, 70, 23);

        RisikoKontakErat.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        RisikoKontakErat.setSelectedIndex(1);
        RisikoKontakErat.setName("RisikoKontakErat"); // NOI18N
        RisikoKontakErat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RisikoKontakEratKeyPressed(evt);
            }
        });
        FormInput.add(RisikoKontakErat);
        RisikoKontakErat.setBounds(107, 330, 90, 23);

        jLabel85.setText(":");
        jLabel85.setName("jLabel85"); // NOI18N
        FormInput.add(jLabel85);
        jLabel85.setBounds(0, 330, 103, 23);

        jLabel86.setText("Riwayat Daerah Wabah :");
        jLabel86.setName("jLabel86"); // NOI18N
        FormInput.add(jLabel86);
        jLabel86.setBounds(555, 330, 140, 23);

        RisikoRiwayatDaerahWabah.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        RisikoRiwayatDaerahWabah.setSelectedIndex(1);
        RisikoRiwayatDaerahWabah.setName("RisikoRiwayatDaerahWabah"); // NOI18N
        RisikoRiwayatDaerahWabah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RisikoRiwayatDaerahWabahKeyPressed(evt);
            }
        });
        FormInput.add(RisikoRiwayatDaerahWabah);
        RisikoRiwayatDaerahWabah.setBounds(699, 330, 90, 23);

        jLabel87.setText("Riwayat MDRO :");
        jLabel87.setName("jLabel87"); // NOI18N
        FormInput.add(jLabel87);
        jLabel87.setBounds(286, 330, 100, 23);

        RisikoRiwayatMDRO.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        RisikoRiwayatMDRO.setSelectedIndex(1);
        RisikoRiwayatMDRO.setName("RisikoRiwayatMDRO"); // NOI18N
        RisikoRiwayatMDRO.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RisikoRiwayatMDROKeyPressed(evt);
            }
        });
        FormInput.add(RisikoRiwayatMDRO);
        RisikoRiwayatMDRO.setBounds(390, 330, 90, 23);

        jSeparator9.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator9.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator9.setName("jSeparator9"); // NOI18N
        FormInput.add(jSeparator9);
        jSeparator9.setBounds(0, 580, 810, 1);

        jSeparator10.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator10.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator10.setName("jSeparator10"); // NOI18N
        FormInput.add(jSeparator10);
        jSeparator10.setBounds(0, 580, 810, 1);

        Keputusan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Masuk Di Ruangan Isolasi", "Tetap Di Ruangan Biasa" }));
        Keputusan.setName("Keputusan"); // NOI18N
        Keputusan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeputusanKeyPressed(evt);
            }
        });
        FormInput.add(Keputusan);
        Keputusan.setBounds(614, 600, 175, 23);

        jLabel57.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel57.setText("VII. KEPUTUSAN & KETERANGAN");
        jLabel57.setName("jLabel57"); // NOI18N
        FormInput.add(jLabel57);
        jLabel57.setBounds(10, 580, 350, 23);

        jLabel91.setText("Keputusan :");
        jLabel91.setName("jLabel91"); // NOI18N
        FormInput.add(jLabel91);
        jLabel91.setBounds(510, 600, 100, 23);

        jLabel92.setText(":");
        jLabel92.setName("jLabel92"); // NOI18N
        FormInput.add(jLabel92);
        jLabel92.setBounds(0, 600, 127, 23);

        jLabel97.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel97.setText("Jenis Isolasi");
        jLabel97.setName("jLabel97"); // NOI18N
        FormInput.add(jLabel97);
        jLabel97.setBounds(40, 630, 80, 23);

        JenisIsolasi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Standar", "Kontak", "Droplet", "Airborne", "Kontak+Droplet", "Airborne+Kontak", "Protective" }));
        JenisIsolasi.setName("JenisIsolasi"); // NOI18N
        JenisIsolasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JenisIsolasiKeyPressed(evt);
            }
        });
        FormInput.add(JenisIsolasi);
        JenisIsolasi.setBounds(110, 630, 135, 23);

        jLabel116.setText("Diare Infeksius :");
        jLabel116.setName("jLabel116"); // NOI18N
        FormInput.add(jLabel116);
        jLabel116.setBounds(283, 280, 140, 23);

        KontakDiareInfeksius.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        KontakDiareInfeksius.setSelectedIndex(1);
        KontakDiareInfeksius.setName("KontakDiareInfeksius"); // NOI18N
        KontakDiareInfeksius.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KontakDiareInfeksiusKeyPressed(evt);
            }
        });
        FormInput.add(KontakDiareInfeksius);
        KontakDiareInfeksius.setBounds(427, 280, 90, 23);

        jLabel117.setText("Kontak Lainnya :");
        jLabel117.setName("jLabel117"); // NOI18N
        FormInput.add(jLabel117);
        jLabel117.setBounds(555, 280, 140, 23);

        KontakLukaDrainase.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        KontakLukaDrainase.setSelectedIndex(1);
        KontakLukaDrainase.setName("KontakLukaDrainase"); // NOI18N
        KontakLukaDrainase.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KontakLukaDrainaseKeyPressed(evt);
            }
        });
        FormInput.add(KontakLukaDrainase);
        KontakLukaDrainase.setBounds(474, 250, 90, 23);

        jLabel9.setText("Keterangan/Catatan :");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(265, 630, 130, 23);

        Keterangan.setHighlighter(null);
        Keterangan.setName("Keterangan"); // NOI18N
        Keterangan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKeyPressed(evt);
            }
        });
        FormInput.add(Keterangan);
        Keterangan.setBounds(399, 630, 390, 23);

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel10.setText("Diagnosa Isolasi");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(40, 600, 130, 23);

        DiagnosaIsolasi.setHighlighter(null);
        DiagnosaIsolasi.setName("DiagnosaIsolasi"); // NOI18N
        DiagnosaIsolasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosaIsolasiKeyPressed(evt);
            }
        });
        FormInput.add(DiagnosaIsolasi);
        DiagnosaIsolasi.setBounds(131, 600, 370, 23);

        jSeparator11.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator11.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator11.setName("jSeparator11"); // NOI18N
        FormInput.add(jSeparator11);
        jSeparator11.setBounds(0, 420, 810, 1);

        jSeparator12.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator12.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator12.setName("jSeparator12"); // NOI18N
        FormInput.add(jSeparator12);
        jSeparator12.setBounds(0, 420, 810, 1);

        jLabel60.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel60.setText("V. KRITERIA ADMINISTRASI & PERSETUJUAN");
        jLabel60.setName("jLabel60"); // NOI18N
        FormInput.add(jLabel60);
        jLabel60.setBounds(10, 420, 350, 23);

        jLabel88.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel88.setText("Hasil Pemeriksaan Lab & Rontgen Positif Penyakit Infeksi Menular");
        jLabel88.setName("jLabel88"); // NOI18N
        FormInput.add(jLabel88);
        jLabel88.setBounds(40, 360, 340, 23);

        jLabel89.setText(":");
        jLabel89.setName("jLabel89"); // NOI18N
        FormInput.add(jLabel89);
        jLabel89.setBounds(0, 360, 367, 23);

        RisikoHasilLabRadPositif.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        RisikoHasilLabRadPositif.setSelectedIndex(1);
        RisikoHasilLabRadPositif.setName("RisikoHasilLabRadPositif"); // NOI18N
        RisikoHasilLabRadPositif.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RisikoHasilLabRadPositifKeyPressed(evt);
            }
        });
        FormInput.add(RisikoHasilLabRadPositif);
        RisikoHasilLabRadPositif.setBounds(371, 360, 90, 23);

        jLabel90.setText("Gejala Klinis Infeksi Menular :");
        jLabel90.setName("jLabel90"); // NOI18N
        FormInput.add(jLabel90);
        jLabel90.setBounds(475, 360, 220, 23);

        RisikoGejalaKlinis.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        RisikoGejalaKlinis.setSelectedIndex(1);
        RisikoGejalaKlinis.setName("RisikoGejalaKlinis"); // NOI18N
        RisikoGejalaKlinis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RisikoGejalaKlinisKeyPressed(evt);
            }
        });
        FormInput.add(RisikoGejalaKlinis);
        RisikoGejalaKlinis.setBounds(699, 360, 90, 23);

        jLabel93.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel93.setText("Pasien Imunokompromis Berat Butuh Isolasi Protektif");
        jLabel93.setName("jLabel93"); // NOI18N
        FormInput.add(jLabel93);
        jLabel93.setBounds(40, 390, 270, 23);

        jLabel94.setText(":");
        jLabel94.setName("jLabel94"); // NOI18N
        FormInput.add(jLabel94);
        jLabel94.setBounds(0, 390, 306, 23);

        RisikoPasienImunokompromis.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        RisikoPasienImunokompromis.setSelectedIndex(1);
        RisikoPasienImunokompromis.setName("RisikoPasienImunokompromis"); // NOI18N
        RisikoPasienImunokompromis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RisikoPasienImunokompromisKeyPressed(evt);
            }
        });
        FormInput.add(RisikoPasienImunokompromis);
        RisikoPasienImunokompromis.setBounds(310, 390, 90, 23);

        jLabel95.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel95.setText("Intruksi Rawat Isolasi Dari DPJP");
        jLabel95.setName("jLabel95"); // NOI18N
        FormInput.add(jLabel95);
        jLabel95.setBounds(40, 440, 180, 23);

        jLabel96.setText(":");
        jLabel96.setName("jLabel96"); // NOI18N
        FormInput.add(jLabel96);
        jLabel96.setBounds(0, 440, 204, 23);

        InstruksiDPJP.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        InstruksiDPJP.setSelectedIndex(1);
        InstruksiDPJP.setName("InstruksiDPJP"); // NOI18N
        InstruksiDPJP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                InstruksiDPJPKeyPressed(evt);
            }
        });
        FormInput.add(InstruksiDPJP);
        InstruksiDPJP.setBounds(208, 440, 90, 23);

        jLabel98.setText("Informed Consent (Persetujuan Isolasi Oleh Keluarga/Pasien) :");
        jLabel98.setName("jLabel98"); // NOI18N
        FormInput.add(jLabel98);
        jLabel98.setBounds(345, 440, 350, 23);

        AdaPersetujuanTindakan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        AdaPersetujuanTindakan.setSelectedIndex(1);
        AdaPersetujuanTindakan.setName("AdaPersetujuanTindakan"); // NOI18N
        AdaPersetujuanTindakan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AdaPersetujuanTindakanKeyPressed(evt);
            }
        });
        FormInput.add(AdaPersetujuanTindakan);
        AdaPersetujuanTindakan.setBounds(699, 440, 90, 23);

        jLabel99.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel99.setText("Kelengkapan Jaminan (BPJS/Asuransi/Umum)");
        jLabel99.setName("jLabel99"); // NOI18N
        FormInput.add(jLabel99);
        jLabel99.setBounds(40, 470, 230, 23);

        jLabel100.setText(":");
        jLabel100.setName("jLabel100"); // NOI18N
        FormInput.add(jLabel100);
        jLabel100.setBounds(0, 470, 268, 23);

        KelengkapanJaminan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        KelengkapanJaminan.setSelectedIndex(1);
        KelengkapanJaminan.setName("KelengkapanJaminan"); // NOI18N
        KelengkapanJaminan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KelengkapanJaminanKeyPressed(evt);
            }
        });
        FormInput.add(KelengkapanJaminan);
        KelengkapanJaminan.setBounds(272, 470, 90, 23);

        jSeparator13.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator13.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator13.setName("jSeparator13"); // NOI18N
        FormInput.add(jSeparator13);
        jSeparator13.setBounds(0, 500, 810, 1);

        jSeparator14.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator14.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator14.setName("jSeparator14"); // NOI18N
        FormInput.add(jSeparator14);
        jSeparator14.setBounds(0, 500, 810, 1);

        jLabel62.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel62.setText("VI. KESIAPAN FASILITAS KAMAR ISOLASI/PARAMETER KAMAR ISOLASI");
        jLabel62.setName("jLabel62"); // NOI18N
        FormInput.add(jLabel62);
        jLabel62.setBounds(10, 500, 480, 23);

        jLabel101.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel101.setText("Ketersediaan APD Lengkap Di Anteroom");
        jLabel101.setName("jLabel101"); // NOI18N
        FormInput.add(jLabel101);
        jLabel101.setBounds(40, 520, 210, 23);

        KetersediaanAPD.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        KetersediaanAPD.setSelectedIndex(1);
        KetersediaanAPD.setName("KetersediaanAPD"); // NOI18N
        KetersediaanAPD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetersediaanAPDKeyPressed(evt);
            }
        });
        FormInput.add(KetersediaanAPD);
        KetersediaanAPD.setBounds(245, 520, 90, 23);

        jLabel102.setText(":");
        jLabel102.setName("jLabel102"); // NOI18N
        FormInput.add(jLabel102);
        jLabel102.setBounds(0, 520, 241, 23);

        FasilitasCuciTangan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        FasilitasCuciTangan.setSelectedIndex(1);
        FasilitasCuciTangan.setName("FasilitasCuciTangan"); // NOI18N
        FasilitasCuciTangan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FasilitasCuciTanganKeyPressed(evt);
            }
        });
        FormInput.add(FasilitasCuciTangan);
        FasilitasCuciTangan.setBounds(699, 520, 90, 23);

        jLabel103.setText("Fasilitas Cuci Tangan (Wastafel, Sabun, Handrub) Siap Pakai :");
        jLabel103.setName("jLabel103"); // NOI18N
        FormInput.add(jLabel103);
        jLabel103.setBounds(385, 520, 310, 23);

        jLabel104.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel104.setText("Sistem Tekanan Udara Negatif Berfungsi Baik");
        jLabel104.setName("jLabel104"); // NOI18N
        FormInput.add(jLabel104);
        jLabel104.setBounds(40, 550, 240, 23);

        TekananUdaraNegatif.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        TekananUdaraNegatif.setSelectedIndex(1);
        TekananUdaraNegatif.setName("TekananUdaraNegatif"); // NOI18N
        TekananUdaraNegatif.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TekananUdaraNegatifKeyPressed(evt);
            }
        });
        FormInput.add(TekananUdaraNegatif);
        TekananUdaraNegatif.setBounds(272, 550, 90, 23);

        jLabel105.setText(":");
        jLabel105.setName("jLabel105"); // NOI18N
        FormInput.add(jLabel105);
        jLabel105.setBounds(0, 550, 268, 23);

        PintuKamarOtomatis.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        PintuKamarOtomatis.setSelectedIndex(1);
        PintuKamarOtomatis.setName("PintuKamarOtomatis"); // NOI18N
        PintuKamarOtomatis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PintuKamarOtomatisKeyPressed(evt);
            }
        });
        FormInput.add(PintuKamarOtomatis);
        PintuKamarOtomatis.setBounds(699, 550, 90, 23);

        jLabel106.setText("Pintu Kamar Penutup Otomatis Bekerja Dengan Baik :");
        jLabel106.setName("jLabel106"); // NOI18N
        FormInput.add(jLabel106);
        jLabel106.setBounds(385, 550, 310, 23);

        jLabel107.setText(":");
        jLabel107.setName("jLabel107"); // NOI18N
        FormInput.add(jLabel107);
        jLabel107.setBounds(0, 630, 106, 23);

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
        }else if(KodePetugas.getText().trim().equals("")||NamaPetugas.getText().trim().equals("")){
            Valid.textKosong(btnPetugas,"DPJP/Dokter Jaga/IGD");
        }else{
            if(akses.getkode().equals("Admin Utama")){
                simpan();
            }else{
                if(TanggalRegistrasi.getText().equals("")){
                    TanggalRegistrasi.setText(Sequel.cariIsi("select concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) from reg_periksa where reg_periksa.no_rawat=?",TNoRw.getText()));
                }
                if(Sequel.cekTanggalRegistrasi(TanggalRegistrasi.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19))==true){
                    simpan();
                }
            }
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            //Valid.pindah(evt,Infeksi,BtnBatal);
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
            }else {
                if(akses.getkode().equals(tbObat.getValueAt(tbObat.getSelectedRow(),40).toString())){
                    if(Sequel.cekTanggal48jam(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString(),Sequel.ambiltanggalsekarang())==true){
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
        if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"pasien");
        }else if(KodePetugas.getText().trim().equals("")||NamaPetugas.getText().trim().equals("")){
            Valid.textKosong(btnPetugas,"DPJP/Dokter Jaga/IGD");
        }else{  
            if(tbObat.getSelectedRow()>-1){
                if(akses.getkode().equals("Admin Utama")){
                    ganti();
                }else {
                    if(akses.getkode().equals(tbObat.getValueAt(tbObat.getSelectedRow(),40).toString())){
                        if(Sequel.cekTanggal48jam(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString(),Sequel.ambiltanggalsekarang())==true){
                            if(TanggalRegistrasi.getText().equals("")){
                                TanggalRegistrasi.setText(Sequel.cariIsi("select concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) from reg_periksa where reg_periksa.no_rawat=?",TNoRw.getText()));
                            }
                            if(Sequel.cekTanggalRegistrasi(TanggalRegistrasi.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19))==true){
                                ganti();
                            }
                        }
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
                htmlContent = new StringBuilder();
                htmlContent.append(                             
                    "<tr class='isi'>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Rawat</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.RM</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Pasien</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tgl.Lahir</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>J.K.</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tanggal</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Pasca Operasi Dengan Gangguan Nafas Atau Hipotensi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Gagal Nafas</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Gagal Jantung Dengan Tanda Bendungan Paru</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Gangguan Asam Basa / Elektrolit</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Gagal Ginjal Dengan Tanda Bendungan Paru</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Syok Karena Perdarahan Anafilaksis</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Pasca Operasi Besar</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kejang Berulang</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Gangguan Kesadaran</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Dehidrasi Berat</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Gangguan Jalan Nafas</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Arimia Jantung</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Asma Akut Berat</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Diabetes Yang Memerlukan Terapi Insulin Kontinyu</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Penyakit Keganasan Dengan Metastasis</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Pasien Geriatrik Dengan Fungsi Hidup Sebelumnya Minimal</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Pasien Dengan GCS 3</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Pasien Jantung, Penyakit Paru Terminal Disertai Komplikasi Penyakit Akut Berat</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nadi < 40 atau >150 (x/menit)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>SBP < 80 mmHg Atau 20 mmHg Di Bawah SBP Pasien</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>MAP < 60 mmHg</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>DBP > 120 mmHg</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>R > 35 x/menit</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Na < 110 meq/L Atau > 170 meq/L</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Ca > 15 mg/dl</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>GDS > 800 mg/dl</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>K < 2 meq/L Atau 7meq/L</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>PaO2 < 50 mmHg</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>PH < 7,1 Atau 7,7</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Perbedaan Cerebrovaskuler, SAH, Atau Contusion Dengan Gangguan Kesadaran Atau Neorologi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Ruptor Organ Dalam, Kandung Kemih, Hati, Varices Esophagus Atau Uterus Dengan Gangguan Hemodinamik</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Pupil Anisokor</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Obstruksi Jalan Nafas</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Anuria</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kejang Berulang</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tamponade Jantung</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Coma</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Sianosis</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Luka Bakar > 10 % BSA</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>NIP/Kode Dokter</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>DPJP/Dokter Jaga/IGD</b></td>"+
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
                            "<td valign='top'>"+tbObat.getValueAt(i,25).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,26).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,27).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,28).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,29).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,30).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,31).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,32).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,33).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,34).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,35).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,36).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,37).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,38).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,39).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,40).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,41).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,42).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,43).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,44).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,45).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,46).toString()+"</td>"+
                        "</tr>");
                }
                
                LoadHTML.setText(
                    "<html>"+
                      "<table width='5100px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
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

                File f = new File("DataChecklistKriteriaMasukICU.html");            
                BufferedWriter bw = new BufferedWriter(new FileWriter(f));            
                bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                            "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                            "<table width='5100px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                "<tr class='isi2'>"+
                                    "<td valign='top' align='center'>"+
                                        "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                        akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                        akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                        "<font size='2' face='Tahoma'>DATA CHECK LIST KRITERIA MASUK ICU<br><br></font>"+        
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

    private void MnKriteriaMasukICUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnKriteriaMasukICUActionPerformed
        if(tbObat.getSelectedRow()>-1){
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());   
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",tbObat.getValueAt(tbObat.getSelectedRow(),40).toString());
            param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbObat.getValueAt(tbObat.getSelectedRow(),41).toString()+"\nID "+(finger.equals("")?tbObat.getValueAt(tbObat.getSelectedRow(),40).toString():finger)+"\n"+Tanggal.getSelectedItem()); 
            Valid.MyReportqry("rptFormulirChecklistKriteriaMasukICU.jasper","report","::[ Formulir Check List Kriteria Masuk ICU ]::",
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,pasien.jk,checklist_kriteria_masuk_isolasi.tanggal,"+
                    "checklist_kriteria_masuk_isolasi.prioritas1_1,checklist_kriteria_masuk_isolasi.prioritas1_2,checklist_kriteria_masuk_isolasi.prioritas1_3,"+
                    "checklist_kriteria_masuk_isolasi.prioritas1_4,checklist_kriteria_masuk_isolasi.prioritas1_5,checklist_kriteria_masuk_isolasi.prioritas1_6,"+
                    "checklist_kriteria_masuk_isolasi.prioritas2_1,checklist_kriteria_masuk_isolasi.prioritas2_2,checklist_kriteria_masuk_isolasi.prioritas2_3,"+
                    "checklist_kriteria_masuk_isolasi.prioritas2_4,checklist_kriteria_masuk_isolasi.prioritas2_5,checklist_kriteria_masuk_isolasi.prioritas2_6,"+
                    "checklist_kriteria_masuk_isolasi.prioritas2_7,checklist_kriteria_masuk_isolasi.prioritas2_8,checklist_kriteria_masuk_isolasi.prioritas3_1,"+
                    "checklist_kriteria_masuk_isolasi.prioritas3_2,checklist_kriteria_masuk_isolasi.prioritas3_3,checklist_kriteria_masuk_isolasi.prioritas3_4,"+
                    "checklist_kriteria_masuk_isolasi.kriteria_fisiologis_tanda_vital_1,checklist_kriteria_masuk_isolasi.kriteria_fisiologis_tanda_vital_2,"+
                    "checklist_kriteria_masuk_isolasi.kriteria_fisiologis_tanda_vital_3,checklist_kriteria_masuk_isolasi.kriteria_fisiologis_tanda_vital_4,"+
                    "checklist_kriteria_masuk_isolasi.kriteria_fisiologis_tanda_vital_5,checklist_kriteria_masuk_isolasi.kriteria_fisiologis_laborat_1,"+
                    "checklist_kriteria_masuk_isolasi.kriteria_fisiologis_laborat_2,checklist_kriteria_masuk_isolasi.kriteria_fisiologis_laborat_3,"+
                    "checklist_kriteria_masuk_isolasi.kriteria_fisiologis_laborat_4,checklist_kriteria_masuk_isolasi.kriteria_fisiologis_laborat_5,"+
                    "checklist_kriteria_masuk_isolasi.kriteria_fisiologis_laborat_6,checklist_kriteria_masuk_isolasi.kriteria_fisiologis_radiologi_1,"+
                    "checklist_kriteria_masuk_isolasi.kriteria_fisiologis_radiologi_2,checklist_kriteria_masuk_isolasi.kriteria_fisiologis_klinis_1,"+
                    "checklist_kriteria_masuk_isolasi.kriteria_fisiologis_klinis_2,checklist_kriteria_masuk_isolasi.kriteria_fisiologis_klinis_3,"+
                    "checklist_kriteria_masuk_isolasi.kriteria_fisiologis_klinis_4,checklist_kriteria_masuk_isolasi.kriteria_fisiologis_klinis_5,"+
                    "checklist_kriteria_masuk_isolasi.kriteria_fisiologis_klinis_6,checklist_kriteria_masuk_isolasi.kriteria_fisiologis_klinis_7,"+
                    "checklist_kriteria_masuk_isolasi.kriteria_fisiologis_klinis_8,checklist_kriteria_masuk_isolasi.nik,pegawai.nama "+
                    "from checklist_kriteria_masuk_isolasi inner join reg_periksa on checklist_kriteria_masuk_isolasi.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join pegawai on pegawai.nik=checklist_kriteria_masuk_isolasi.nik "+
                    "where checklist_kriteria_masuk_isolasi.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"' and checklist_kriteria_masuk_isolasi.tanggal='"+tbObat.getValueAt(tbObat.getSelectedRow(),5).toString()+"' ",param);
        }
    }//GEN-LAST:event_MnKriteriaMasukICUActionPerformed

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void TanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKeyPressed
       Valid.pindah(evt,TCari,btnPetugas);
    }//GEN-LAST:event_TanggalKeyPressed

    private void btnPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPetugasActionPerformed
        DlgCariPegawai pegawai=new DlgCariPegawai(null,false);
        pegawai.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(pegawai.getTable().getSelectedRow()!= -1){  
                    KodePetugas.setText(pegawai.getTable().getValueAt(pegawai.getTable().getSelectedRow(),0).toString());
                    NamaPetugas.setText(pegawai.getTable().getValueAt(pegawai.getTable().getSelectedRow(),1).toString());
                    btnPetugas.requestFocus();
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
        pegawai.emptTeks();
        pegawai.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        pegawai.setLocationRelativeTo(internalFrame1);
        pegawai.setVisible(true);
    }//GEN-LAST:event_btnPetugasActionPerformed

    private void btnPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPetugasKeyPressed
       Valid.pindah(evt,Tanggal,AirboneTBParu);
    }//GEN-LAST:event_btnPetugasKeyPressed

    private void AirboneVariselaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AirboneVariselaKeyPressed
       Valid.pindah(evt,AirboneHerpes,AirboneLainnya);
    }//GEN-LAST:event_AirboneVariselaKeyPressed

    private void AirboneHerpesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AirboneHerpesKeyPressed
        Valid.pindah(evt,AirboneCampak,AirboneVarisela);
    }//GEN-LAST:event_AirboneHerpesKeyPressed

    private void AirboneCampakKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AirboneCampakKeyPressed
        Valid.pindah(evt,AirboneTBParu,AirboneHerpes);
    }//GEN-LAST:event_AirboneCampakKeyPressed

    private void AirboneTBParuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AirboneTBParuKeyPressed
        Valid.pindah(evt,btnPetugas,AirboneCampak);
    }//GEN-LAST:event_AirboneTBParuKeyPressed

    private void AirboneLainnyaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AirboneLainnyaKeyPressed
        Valid.pindah(evt,AirboneVarisela,DropletCovid);
    }//GEN-LAST:event_AirboneLainnyaKeyPressed

    private void DropletCovidKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DropletCovidKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DropletCovidKeyPressed

    private void DropletPertusisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DropletPertusisKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DropletPertusisKeyPressed

    private void DropletInfluenzaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DropletInfluenzaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DropletInfluenzaKeyPressed

    private void DropletMeningitisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DropletMeningitisKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DropletMeningitisKeyPressed

    private void DropletLainnyaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DropletLainnyaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DropletLainnyaKeyPressed

    private void DropletDifteriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DropletDifteriKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DropletDifteriKeyPressed

    private void KontakMDROKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KontakMDROKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KontakMDROKeyPressed

    private void KontakSkabiesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KontakSkabiesKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KontakSkabiesKeyPressed

    private void KontakClostridioidesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KontakClostridioidesKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KontakClostridioidesKeyPressed

    private void KontakLainnyaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KontakLainnyaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KontakLainnyaKeyPressed

    private void RisikoKontakEratKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RisikoKontakEratKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_RisikoKontakEratKeyPressed

    private void RisikoRiwayatDaerahWabahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RisikoRiwayatDaerahWabahKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_RisikoRiwayatDaerahWabahKeyPressed

    private void RisikoRiwayatMDROKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RisikoRiwayatMDROKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_RisikoRiwayatMDROKeyPressed

    private void KeputusanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeputusanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeputusanKeyPressed

    private void JenisIsolasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JenisIsolasiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_JenisIsolasiKeyPressed

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

    private void KontakDiareInfeksiusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KontakDiareInfeksiusKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KontakDiareInfeksiusKeyPressed

    private void KontakLukaDrainaseKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KontakLukaDrainaseKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KontakLukaDrainaseKeyPressed

    private void KeteranganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKeyPressed
        //Valid.pindah(evt,Keputusan,BtnSimpan);
    }//GEN-LAST:event_KeteranganKeyPressed

    private void DiagnosaIsolasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosaIsolasiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DiagnosaIsolasiKeyPressed

    private void RisikoHasilLabRadPositifKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RisikoHasilLabRadPositifKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_RisikoHasilLabRadPositifKeyPressed

    private void RisikoGejalaKlinisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RisikoGejalaKlinisKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_RisikoGejalaKlinisKeyPressed

    private void RisikoPasienImunokompromisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RisikoPasienImunokompromisKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_RisikoPasienImunokompromisKeyPressed

    private void InstruksiDPJPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_InstruksiDPJPKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_InstruksiDPJPKeyPressed

    private void AdaPersetujuanTindakanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AdaPersetujuanTindakanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_AdaPersetujuanTindakanKeyPressed

    private void KelengkapanJaminanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KelengkapanJaminanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KelengkapanJaminanKeyPressed

    private void KetersediaanAPDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetersediaanAPDKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KetersediaanAPDKeyPressed

    private void FasilitasCuciTanganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FasilitasCuciTanganKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_FasilitasCuciTanganKeyPressed

    private void TekananUdaraNegatifKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TekananUdaraNegatifKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TekananUdaraNegatifKeyPressed

    private void PintuKamarOtomatisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PintuKamarOtomatisKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PintuKamarOtomatisKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMChecklistKriteriaMasukIsolasi dialog = new RMChecklistKriteriaMasukIsolasi(new javax.swing.JFrame(), true);
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
    private widget.ComboBox AdaPersetujuanTindakan;
    private widget.ComboBox AirboneCampak;
    private widget.ComboBox AirboneHerpes;
    private widget.ComboBox AirboneLainnya;
    private widget.ComboBox AirboneTBParu;
    private widget.ComboBox AirboneVarisela;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkInput;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.TextBox DiagnosaIsolasi;
    private widget.ComboBox DropletCovid;
    private widget.ComboBox DropletDifteri;
    private widget.ComboBox DropletInfluenza;
    private widget.ComboBox DropletLainnya;
    private widget.ComboBox DropletMeningitis;
    private widget.ComboBox DropletPertusis;
    private widget.ComboBox FasilitasCuciTangan;
    private widget.PanelBiasa FormInput;
    private widget.ComboBox InstruksiDPJP;
    private widget.TextBox JK;
    private widget.ComboBox JenisIsolasi;
    private widget.ComboBox KelengkapanJaminan;
    private widget.ComboBox Keputusan;
    private widget.TextBox Keterangan;
    private widget.ComboBox KetersediaanAPD;
    private widget.TextBox KodePetugas;
    private widget.ComboBox KontakClostridioides;
    private widget.ComboBox KontakDiareInfeksius;
    private widget.ComboBox KontakLainnya;
    private widget.ComboBox KontakLukaDrainase;
    private widget.ComboBox KontakMDRO;
    private widget.ComboBox KontakSkabies;
    private widget.Label LCount;
    private widget.editorpane LoadHTML;
    private javax.swing.JMenuItem MnKriteriaMasukICU;
    private widget.TextBox NamaPetugas;
    private javax.swing.JPanel PanelInput;
    private widget.ComboBox PintuKamarOtomatis;
    private widget.ComboBox RisikoGejalaKlinis;
    private widget.ComboBox RisikoHasilLabRadPositif;
    private widget.ComboBox RisikoKontakErat;
    private widget.ComboBox RisikoPasienImunokompromis;
    private widget.ComboBox RisikoRiwayatDaerahWabah;
    private widget.ComboBox RisikoRiwayatMDRO;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.Tanggal Tanggal;
    private widget.TextBox TanggalRegistrasi;
    private widget.ComboBox TekananUdaraNegatif;
    private widget.TextBox TglLahir;
    private widget.Button btnPetugas;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel10;
    private widget.Label jLabel100;
    private widget.Label jLabel101;
    private widget.Label jLabel102;
    private widget.Label jLabel103;
    private widget.Label jLabel104;
    private widget.Label jLabel105;
    private widget.Label jLabel106;
    private widget.Label jLabel107;
    private widget.Label jLabel116;
    private widget.Label jLabel117;
    private widget.Label jLabel16;
    private widget.Label jLabel19;
    private widget.Label jLabel21;
    private widget.Label jLabel23;
    private widget.Label jLabel4;
    private widget.Label jLabel5;
    private widget.Label jLabel53;
    private widget.Label jLabel54;
    private widget.Label jLabel55;
    private widget.Label jLabel56;
    private widget.Label jLabel57;
    private widget.Label jLabel58;
    private widget.Label jLabel59;
    private widget.Label jLabel6;
    private widget.Label jLabel60;
    private widget.Label jLabel61;
    private widget.Label jLabel62;
    private widget.Label jLabel63;
    private widget.Label jLabel64;
    private widget.Label jLabel65;
    private widget.Label jLabel66;
    private widget.Label jLabel67;
    private widget.Label jLabel68;
    private widget.Label jLabel7;
    private widget.Label jLabel70;
    private widget.Label jLabel71;
    private widget.Label jLabel72;
    private widget.Label jLabel73;
    private widget.Label jLabel75;
    private widget.Label jLabel76;
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
    private widget.Label jLabel9;
    private widget.Label jLabel90;
    private widget.Label jLabel91;
    private widget.Label jLabel92;
    private widget.Label jLabel93;
    private widget.Label jLabel94;
    private widget.Label jLabel95;
    private widget.Label jLabel96;
    private widget.Label jLabel97;
    private widget.Label jLabel98;
    private widget.Label jLabel99;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollInput;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables
    
    public void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            if(TCari.getText().trim().equals("")){
                ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,pasien.jk,checklist_kriteria_masuk_isolasi.tanggal,"+
                    "checklist_kriteria_masuk_isolasi.airborne_tb,checklist_kriteria_masuk_isolasi.airborne_campak,checklist_kriteria_masuk_isolasi.airborne_varisela,"+
                    "checklist_kriteria_masuk_isolasi.airborne_zoster_diseminata,checklist_kriteria_masuk_isolasi.airborne_lainnya,checklist_kriteria_masuk_isolasi.droplet_covid19,"+
                    "checklist_kriteria_masuk_isolasi.droplet_influenza,checklist_kriteria_masuk_isolasi.droplet_difteri,checklist_kriteria_masuk_isolasi.droplet_pertusis,"+
                    "checklist_kriteria_masuk_isolasi.droplet_meningitis,checklist_kriteria_masuk_isolasi.droplet_lainnya,checklist_kriteria_masuk_isolasi.kontak_mdro,"+
                    "checklist_kriteria_masuk_isolasi.kontak_clostridium,checklist_kriteria_masuk_isolasi.kontak_scabies,checklist_kriteria_masuk_isolasi.kontak_luka_drainase,"+
                    "checklist_kriteria_masuk_isolasi.kontak_diare_infeksi,checklist_kriteria_masuk_isolasi.kontak_lainnya,checklist_kriteria_masuk_isolasi.kontak_erat,"+
                    "checklist_kriteria_masuk_isolasi.riwayat_perjalanan_wabah,checklist_kriteria_masuk_isolasi.riwayat_mdro,checklist_kriteria_masuk_isolasi.hasil_lab_radiologi_positif,"+
                    "checklist_kriteria_masuk_isolasi.gejala_klinis_menular,checklist_kriteria_masuk_isolasi.pasien_imunokompromis,checklist_kriteria_masuk_isolasi.instruksi_dpjp,"+
                    "checklist_kriteria_masuk_isolasi.persetujuan_isolasi,checklist_kriteria_masuk_isolasi.kelengkapan_jaminan,checklist_kriteria_masuk_isolasi.ketersediaan_apd,"+
                    "checklist_kriteria_masuk_isolasi.fasilitas_cuci_tangan,checklist_kriteria_masuk_isolasi.tekanan_negatif_berfungsi,checklist_kriteria_masuk_isolasi.pintu_otomatis_berfungsi,"+
                    "checklist_kriteria_masuk_isolasi.indikasi_isolasi,checklist_kriteria_masuk_isolasi.jenis_isolasi,checklist_kriteria_masuk_isolasi.diagnosa_isolasi,"+
                    "checklist_kriteria_masuk_isolasi.keterangan,checklist_kriteria_masuk_isolasi.nik,pegawai.nama "+
                    "from checklist_kriteria_masuk_isolasi inner join reg_periksa on checklist_kriteria_masuk_isolasi.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join pegawai on pegawai.nik=checklist_kriteria_masuk_isolasi.nik "+
                    "where checklist_kriteria_masuk_isolasi.tanggal between ? and ? order by checklist_kriteria_masuk_isolasi.tanggal ");
            }else{
                ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,pasien.jk,checklist_kriteria_masuk_isolasi.tanggal,"+
                    "checklist_kriteria_masuk_isolasi.airborne_tb,checklist_kriteria_masuk_isolasi.airborne_campak,checklist_kriteria_masuk_isolasi.airborne_varisela,"+
                    "checklist_kriteria_masuk_isolasi.airborne_zoster_diseminata,checklist_kriteria_masuk_isolasi.airborne_lainnya,checklist_kriteria_masuk_isolasi.droplet_covid19,"+
                    "checklist_kriteria_masuk_isolasi.droplet_influenza,checklist_kriteria_masuk_isolasi.droplet_difteri,checklist_kriteria_masuk_isolasi.droplet_pertusis,"+
                    "checklist_kriteria_masuk_isolasi.droplet_meningitis,checklist_kriteria_masuk_isolasi.droplet_lainnya,checklist_kriteria_masuk_isolasi.kontak_mdro,"+
                    "checklist_kriteria_masuk_isolasi.kontak_clostridium,checklist_kriteria_masuk_isolasi.kontak_scabies,checklist_kriteria_masuk_isolasi.kontak_luka_drainase,"+
                    "checklist_kriteria_masuk_isolasi.kontak_diare_infeksi,checklist_kriteria_masuk_isolasi.kontak_lainnya,checklist_kriteria_masuk_isolasi.kontak_erat,"+
                    "checklist_kriteria_masuk_isolasi.riwayat_perjalanan_wabah,checklist_kriteria_masuk_isolasi.riwayat_mdro,checklist_kriteria_masuk_isolasi.hasil_lab_radiologi_positif,"+
                    "checklist_kriteria_masuk_isolasi.gejala_klinis_menular,checklist_kriteria_masuk_isolasi.pasien_imunokompromis,checklist_kriteria_masuk_isolasi.instruksi_dpjp,"+
                    "checklist_kriteria_masuk_isolasi.persetujuan_isolasi,checklist_kriteria_masuk_isolasi.kelengkapan_jaminan,checklist_kriteria_masuk_isolasi.ketersediaan_apd,"+
                    "checklist_kriteria_masuk_isolasi.fasilitas_cuci_tangan,checklist_kriteria_masuk_isolasi.tekanan_negatif_berfungsi,checklist_kriteria_masuk_isolasi.pintu_otomatis_berfungsi,"+
                    "checklist_kriteria_masuk_isolasi.indikasi_isolasi,checklist_kriteria_masuk_isolasi.jenis_isolasi,checklist_kriteria_masuk_isolasi.diagnosa_isolasi,"+
                    "checklist_kriteria_masuk_isolasi.keterangan,checklist_kriteria_masuk_isolasi.nik,pegawai.nama "+
                    "from checklist_kriteria_masuk_isolasi inner join reg_periksa on checklist_kriteria_masuk_isolasi.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join pegawai on pegawai.nik=checklist_kriteria_masuk_isolasi.nik "+
                    "where checklist_kriteria_masuk_isolasi.tanggal between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or "+
                    "pasien.nm_pasien like ? or pegawai.nama like ? or checklist_kriteria_masuk_isolasi.nik like ?) order by checklist_kriteria_masuk_isolasi.tanggal ");
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
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getDate("tgl_lahir"),rs.getString("jk"),
                        rs.getString("tanggal"),rs.getString("airborne_tb"),rs.getString("airborne_campak"),rs.getString("airborne_varisela"),rs.getString("airborne_zoster_diseminata"),
                        rs.getString("airborne_lainnya"),rs.getString("droplet_covid19"),rs.getString("droplet_influenza"),rs.getString("droplet_difteri"),rs.getString("droplet_pertusis"),
                        rs.getString("droplet_meningitis"),rs.getString("droplet_lainnya"),rs.getString("kontak_mdro"),rs.getString("kontak_clostridium"),rs.getString("kontak_scabies"),
                        rs.getString("kontak_luka_drainase"),rs.getString("kontak_diare_infeksi"),rs.getString("kontak_lainnya"),rs.getString("kontak_erat"),rs.getString("riwayat_perjalanan_wabah"),
                        rs.getString("riwayat_mdro"),rs.getString("hasil_lab_radiologi_positif"),rs.getString("gejala_klinis_menular"),rs.getString("pasien_imunokompromis"),
                        rs.getString("instruksi_dpjp"),rs.getString("persetujuan_isolasi"),rs.getString("kelengkapan_jaminan"),rs.getString("ketersediaan_apd"),rs.getString("fasilitas_cuci_tangan"),
                        rs.getString("tekanan_negatif_berfungsi"),rs.getString("pintu_otomatis_berfungsi"),rs.getString("indikasi_isolasi"),rs.getString("jenis_isolasi"),
                        rs.getString("diagnosa_isolasi"),rs.getString("keterangan"),rs.getString("nik"),rs.getString("nama")
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
        AirboneCampak.setSelectedIndex(0);
        AirboneVarisela.setSelectedIndex(0); 
        AirboneHerpes.setSelectedIndex(0);
        AirboneLainnya.setSelectedIndex(0); 
        DropletCovid.setSelectedIndex(0);
        DropletInfluenza.setSelectedIndex(0);
        DropletDifteri.setSelectedIndex(0);
        DropletPertusis.setSelectedIndex(0);
        DropletMeningitis.setSelectedIndex(0);
        DropletLainnya.setSelectedIndex(0);
        KontakMDRO.setSelectedIndex(0);
        KontakClostridioides.setSelectedIndex(0); 
        KontakSkabies.setSelectedIndex(0);
        KontakLukaDrainase.setSelectedIndex(0);
        KontakDiareInfeksius.setSelectedIndex(0);
        KontakLainnya.setSelectedIndex(0); 
        RisikoKontakErat.setSelectedIndex(0);
        RisikoRiwayatDaerahWabah.setSelectedIndex(0);
        RisikoRiwayatMDRO.setSelectedIndex(0);
        RisikoHasilLabRadPositif.setSelectedIndex(0); 
        RisikoGejalaKlinis.setSelectedIndex(0);
        RisikoPasienImunokompromis.setSelectedIndex(0);
        InstruksiDPJP.setSelectedIndex(0);
        AdaPersetujuanTindakan.setSelectedIndex(0); 
        KelengkapanJaminan.setSelectedIndex(0);
        KetersediaanAPD.setSelectedIndex(0);
        FasilitasCuciTangan.setSelectedIndex(0);
        TekananUdaraNegatif.setSelectedIndex(0); 
        PintuKamarOtomatis.setSelectedIndex(0);
        Keputusan.setSelectedIndex(0);
        JenisIsolasi.setSelectedIndex(0);
        DiagnosaIsolasi.setText("");
        Keterangan.setText("");
        Tanggal.setDate(new Date());
        AirboneTBParu.requestFocus();
    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            JK.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString());
            Valid.SetTgl2(Tanggal,tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
        }
    }
    
    private void isRawat() {
        try {
            ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,reg_periksa.tgl_registrasi,"+
                    "reg_periksa.jam_reg from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "where reg_periksa.no_rawat=?");
            try {
                ps.setString(1,TNoRw.getText());
                rs=ps.executeQuery();
                if(rs.next()){
                    TNoRM.setText(rs.getString("no_rkm_medis"));
                    DTPCari1.setDate(rs.getDate("tgl_registrasi"));
                    TPasien.setText(rs.getString("nm_pasien"));
                    JK.setText(rs.getString("jk"));
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
    
    public void setNoRm(String norwt, Date tgl2) {
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        DTPCari2.setDate(tgl2);
        isRawat();
        ChkInput.setSelected(true);
        isForm();
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,internalFrame1.getHeight()-182));
            FormInput.setVisible(true);      
            ChkInput.setVisible(true);
        }else if(ChkInput.isSelected()==false){           
            ChkInput.setVisible(false);            
            PanelInput.setPreferredSize(new Dimension(WIDTH,20));
            FormInput.setVisible(false);      
            ChkInput.setVisible(true);
        }
    }
    
    public void isCek(){
        BtnSimpan.setEnabled(akses.getchecklist_kriteria_masuk_isolasi());
        BtnHapus.setEnabled(akses.getchecklist_kriteria_masuk_isolasi());
        BtnEdit.setEnabled(akses.getchecklist_kriteria_masuk_isolasi());
        BtnPrint.setEnabled(akses.getchecklist_kriteria_masuk_isolasi()); 
        if(akses.getjml2()>=1){
            btnPetugas.setEnabled(false);
            KodePetugas.setText(akses.getkode());
            NamaPetugas.setText(Sequel.CariPegawai(akses.getkode()));
        }
        if(TANGGALMUNDUR.equals("no")){
            if(!akses.getkode().equals("Admin Utama")){
                Tanggal.setEditable(false);
                Tanggal.setEnabled(false);
            }
        }
    }

    private void ganti() {
        /*if(Sequel.mengedittf("checklist_kriteria_masuk_isolasi","no_rawat=? and tanggal=?","no_rawat=?,tanggal=?,prioritas1_1=?,prioritas1_2=?,prioritas1_3=?,prioritas1_4=?,prioritas1_5=?,prioritas1_6=?,"+
                "prioritas2_1=?,prioritas2_2=?,prioritas2_3=?,prioritas2_4=?,prioritas2_5=?,prioritas2_6=?,prioritas2_7=?,prioritas2_8=?,prioritas3_1=?,prioritas3_2=?,prioritas3_3=?,prioritas3_4=?,"+
                "kriteria_fisiologis_tanda_vital_1=?,kriteria_fisiologis_tanda_vital_2=?,kriteria_fisiologis_tanda_vital_3=?,kriteria_fisiologis_tanda_vital_4=?,kriteria_fisiologis_tanda_vital_5=?,"+
                "kriteria_fisiologis_laborat_1=?,kriteria_fisiologis_laborat_2=?,kriteria_fisiologis_laborat_3=?,kriteria_fisiologis_laborat_4=?,kriteria_fisiologis_laborat_5=?,kriteria_fisiologis_laborat_6=?,"+
                "kriteria_fisiologis_radiologi_1=?,kriteria_fisiologis_radiologi_2=?,kriteria_fisiologis_klinis_1=?,kriteria_fisiologis_klinis_2=?,kriteria_fisiologis_klinis_3=?,kriteria_fisiologis_klinis_4=?,"+
                "kriteria_fisiologis_klinis_5=?,kriteria_fisiologis_klinis_6=?,kriteria_fisiologis_klinis_7=?,kriteria_fisiologis_klinis_8=?,nik=?",44,new String[]{
                TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19),Prioritas1_1.getSelectedItem().toString(),
                Prioritas1_2.getSelectedItem().toString(),Prioritas1_3.getSelectedItem().toString(),Prioritas1_4.getSelectedItem().toString(),Prioritas1_5.getSelectedItem().toString(),
                Prioritas1_6.getSelectedItem().toString(),Prioritas2_1.getSelectedItem().toString(),Prioritas2_2.getSelectedItem().toString(),Prioritas2_3.getSelectedItem().toString(),
                Prioritas2_4.getSelectedItem().toString(),Prioritas2_5.getSelectedItem().toString(),Prioritas2_6.getSelectedItem().toString(),Prioritas2_7.getSelectedItem().toString(),
                Prioritas2_8.getSelectedItem().toString(),Prioritas3_1.getSelectedItem().toString(),Prioritas3_2.getSelectedItem().toString(),Prioritas3_3.getSelectedItem().toString(),
                Prioritas3_4.getSelectedItem().toString(),TandaVital1.getSelectedItem().toString(),TandaVital2.getSelectedItem().toString(),TandaVital3.getSelectedItem().toString(),
                TandaVital4.getSelectedItem().toString(),TandaVital5.getSelectedItem().toString(),Laborat1.getSelectedItem().toString(),Laborat2.getSelectedItem().toString(),
                Laborat3.getSelectedItem().toString(),Laborat4.getSelectedItem().toString(),Laborat5.getSelectedItem().toString(),Laborat6.getSelectedItem().toString(),
                Radiologi1.getSelectedItem().toString(),Radiologi2.getSelectedItem().toString(),Kinis1.getSelectedItem().toString(),Kinis2.getSelectedItem().toString(),
                Kinis3.getSelectedItem().toString(),Kinis4.getSelectedItem().toString(),Kinis5.getSelectedItem().toString(),Kinis6.getSelectedItem().toString(),Kinis7.getSelectedItem().toString(),
                Kinis8.getSelectedItem().toString(),KodePetugas.getText(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString(),tbObat.getValueAt(tbObat.getSelectedRow(),5).toString()
        })==true){
            tbObat.setValueAt(TNoRw.getText(),tbObat.getSelectedRow(),0);
            tbObat.setValueAt(TNoRM.getText(),tbObat.getSelectedRow(),1);
            tbObat.setValueAt(TPasien.getText(),tbObat.getSelectedRow(),2);
            tbObat.setValueAt(TglLahir.getText(),tbObat.getSelectedRow(),3);
            tbObat.setValueAt(JK.getText(),tbObat.getSelectedRow(),4);
            tbObat.setValueAt(Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19),tbObat.getSelectedRow(),5);
            tbObat.setValueAt(Prioritas1_1.getSelectedItem().toString(),tbObat.getSelectedRow(),6);
            tbObat.setValueAt(Prioritas1_2.getSelectedItem().toString(),tbObat.getSelectedRow(),7);
            tbObat.setValueAt(Prioritas1_3.getSelectedItem().toString(),tbObat.getSelectedRow(),8);
            tbObat.setValueAt(Prioritas1_4.getSelectedItem().toString(),tbObat.getSelectedRow(),9);
            tbObat.setValueAt(Prioritas1_5.getSelectedItem().toString(),tbObat.getSelectedRow(),10);
            tbObat.setValueAt(Prioritas1_6.getSelectedItem().toString(),tbObat.getSelectedRow(),11);
            tbObat.setValueAt(Prioritas2_1.getSelectedItem().toString(),tbObat.getSelectedRow(),12);
            tbObat.setValueAt(Prioritas2_2.getSelectedItem().toString(),tbObat.getSelectedRow(),13);
            tbObat.setValueAt(Prioritas2_3.getSelectedItem().toString(),tbObat.getSelectedRow(),14);
            tbObat.setValueAt(Prioritas2_4.getSelectedItem().toString(),tbObat.getSelectedRow(),15);
            tbObat.setValueAt(Prioritas2_5.getSelectedItem().toString(),tbObat.getSelectedRow(),16);
            tbObat.setValueAt(Prioritas2_6.getSelectedItem().toString(),tbObat.getSelectedRow(),17);
            tbObat.setValueAt(Prioritas2_7.getSelectedItem().toString(),tbObat.getSelectedRow(),18);
            tbObat.setValueAt(Prioritas2_8.getSelectedItem().toString(),tbObat.getSelectedRow(),19);
            tbObat.setValueAt(Prioritas3_1.getSelectedItem().toString(),tbObat.getSelectedRow(),20);
            tbObat.setValueAt(Prioritas3_2.getSelectedItem().toString(),tbObat.getSelectedRow(),21);
            tbObat.setValueAt(Prioritas3_3.getSelectedItem().toString(),tbObat.getSelectedRow(),22);
            tbObat.setValueAt(Prioritas3_4.getSelectedItem().toString(),tbObat.getSelectedRow(),23);
            tbObat.setValueAt(TandaVital1.getSelectedItem().toString(),tbObat.getSelectedRow(),24);
            tbObat.setValueAt(TandaVital2.getSelectedItem().toString(),tbObat.getSelectedRow(),25);
            tbObat.setValueAt(TandaVital3.getSelectedItem().toString(),tbObat.getSelectedRow(),26);
            tbObat.setValueAt(TandaVital4.getSelectedItem().toString(),tbObat.getSelectedRow(),27);
            tbObat.setValueAt(TandaVital5.getSelectedItem().toString(),tbObat.getSelectedRow(),28);
            tbObat.setValueAt(Laborat1.getSelectedItem().toString(),tbObat.getSelectedRow(),29);
            tbObat.setValueAt(Laborat2.getSelectedItem().toString(),tbObat.getSelectedRow(),30);
            tbObat.setValueAt(Laborat3.getSelectedItem().toString(),tbObat.getSelectedRow(),31);
            tbObat.setValueAt(Laborat4.getSelectedItem().toString(),tbObat.getSelectedRow(),32);
            tbObat.setValueAt(Laborat5.getSelectedItem().toString(),tbObat.getSelectedRow(),33);
            tbObat.setValueAt(Laborat6.getSelectedItem().toString(),tbObat.getSelectedRow(),34);
            tbObat.setValueAt(Radiologi1.getSelectedItem().toString(),tbObat.getSelectedRow(),35);
            tbObat.setValueAt(Radiologi2.getSelectedItem().toString(),tbObat.getSelectedRow(),36);
            tbObat.setValueAt(Kinis1.getSelectedItem().toString(),tbObat.getSelectedRow(),37);
            tbObat.setValueAt(Kinis2.getSelectedItem().toString(),tbObat.getSelectedRow(),38);
            tbObat.setValueAt(Kinis3.getSelectedItem().toString(),tbObat.getSelectedRow(),39);
            tbObat.setValueAt(Kinis4.getSelectedItem().toString(),tbObat.getSelectedRow(),40);
            tbObat.setValueAt(Kinis5.getSelectedItem().toString(),tbObat.getSelectedRow(),41);
            tbObat.setValueAt(Kinis6.getSelectedItem().toString(),tbObat.getSelectedRow(),42);
            tbObat.setValueAt(Kinis7.getSelectedItem().toString(),tbObat.getSelectedRow(),43);
            tbObat.setValueAt(Kinis8.getSelectedItem().toString(),tbObat.getSelectedRow(),44);
            tbObat.setValueAt(KodePetugas.getText(),tbObat.getSelectedRow(),45);
            tbObat.setValueAt(NamaPetugas.getText(),tbObat.getSelectedRow(),46);
            emptTeks();
        }*/
    }

    private void hapus() {
        if(Sequel.queryu2tf("delete from checklist_kriteria_masuk_isolasi where no_rawat=? and tanggal=?",2,new String[]{
            tbObat.getValueAt(tbObat.getSelectedRow(),0).toString(),tbObat.getValueAt(tbObat.getSelectedRow(),5).toString()
        })==true){
            tabMode.removeRow(tbObat.getSelectedRow());
            LCount.setText(""+tabMode.getRowCount());
            emptTeks();
        }else{
            JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
        }
    }

    private void simpan() {
        if(Sequel.menyimpantf("checklist_kriteria_masuk_isolasi","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","Data",37,new String[]{
            TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19),AirboneTBParu.getSelectedItem().toString(), 
            AirboneCampak.getSelectedItem().toString(),AirboneVarisela.getSelectedItem().toString(), AirboneHerpes.getSelectedItem().toString(),AirboneLainnya.getSelectedItem().toString(), 
            DropletCovid.getSelectedItem().toString(),DropletInfluenza.getSelectedItem().toString(),DropletDifteri.getSelectedItem().toString(),DropletPertusis.getSelectedItem().toString(),
            DropletMeningitis.getSelectedItem().toString(),DropletLainnya.getSelectedItem().toString(),KontakMDRO.getSelectedItem().toString(),KontakClostridioides.getSelectedItem().toString(), 
            KontakSkabies.getSelectedItem().toString(),KontakLukaDrainase.getSelectedItem().toString(),KontakDiareInfeksius.getSelectedItem().toString(),KontakLainnya.getSelectedItem().toString(), 
            RisikoKontakErat.getSelectedItem().toString(),RisikoRiwayatDaerahWabah.getSelectedItem().toString(),RisikoRiwayatMDRO.getSelectedItem().toString(),RisikoHasilLabRadPositif.getSelectedItem().toString(), 
            RisikoGejalaKlinis.getSelectedItem().toString(),RisikoPasienImunokompromis.getSelectedItem().toString(),InstruksiDPJP.getSelectedItem().toString(),AdaPersetujuanTindakan.getSelectedItem().toString(), 
            KelengkapanJaminan.getSelectedItem().toString(),KetersediaanAPD.getSelectedItem().toString(),FasilitasCuciTangan.getSelectedItem().toString(),TekananUdaraNegatif.getSelectedItem().toString(), 
            PintuKamarOtomatis.getSelectedItem().toString(),Keputusan.getSelectedItem().toString(),JenisIsolasi.getSelectedItem().toString(),DiagnosaIsolasi.getText(),Keterangan.getText(),KodePetugas.getText()
        })==true){
            tabMode.addRow(new Object[]{
                TNoRw.getText(),TNoRM.getText(),TPasien.getText(),TglLahir.getText(),JK.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19),
                AirboneTBParu.getSelectedItem().toString(),AirboneCampak.getSelectedItem().toString(),AirboneVarisela.getSelectedItem().toString(), AirboneHerpes.getSelectedItem().toString(),
                AirboneLainnya.getSelectedItem().toString(),DropletCovid.getSelectedItem().toString(),DropletInfluenza.getSelectedItem().toString(),DropletDifteri.getSelectedItem().toString(),
                DropletPertusis.getSelectedItem().toString(),DropletMeningitis.getSelectedItem().toString(),DropletLainnya.getSelectedItem().toString(),KontakMDRO.getSelectedItem().toString(),
                KontakClostridioides.getSelectedItem().toString(),KontakSkabies.getSelectedItem().toString(),KontakLukaDrainase.getSelectedItem().toString(),KontakDiareInfeksius.getSelectedItem().toString(),
                KontakLainnya.getSelectedItem().toString(),RisikoKontakErat.getSelectedItem().toString(),RisikoRiwayatDaerahWabah.getSelectedItem().toString(),RisikoRiwayatMDRO.getSelectedItem().toString(),
                RisikoHasilLabRadPositif.getSelectedItem().toString(),RisikoGejalaKlinis.getSelectedItem().toString(),RisikoPasienImunokompromis.getSelectedItem().toString(),InstruksiDPJP.getSelectedItem().toString(),
                AdaPersetujuanTindakan.getSelectedItem().toString(),KelengkapanJaminan.getSelectedItem().toString(),KetersediaanAPD.getSelectedItem().toString(),FasilitasCuciTangan.getSelectedItem().toString(),
                TekananUdaraNegatif.getSelectedItem().toString(),PintuKamarOtomatis.getSelectedItem().toString(),Keputusan.getSelectedItem().toString(),JenisIsolasi.getSelectedItem().toString(),DiagnosaIsolasi.getText(),
                Keterangan.getText(),KodePetugas.getText(),NamaPetugas.getText()
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

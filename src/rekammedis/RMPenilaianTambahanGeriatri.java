/*
 * Kontribusi dari tim IT RSUD Prembun
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
public final class RMPenilaianTambahanGeriatri extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private volatile boolean ceksukses = false;
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;
    private StringBuilder htmlContent;
    private String finger="";
    
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public RMPenilaianTambahanGeriatri(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.RM","Nama Pasien","Tgl.Lahir","J.K.","Kode Petugas","Dokter/Petugas","Tanggal","Kondisi Masuk","Keterangan Kondisi Saat Masuk",
            "Anamnesis","Diagnosa Masuk","Asal Masuk","Infeksi Telinga Baru","Infeksi Berat Sinus","Antibiotik Tanpa Dampak","Pneumonia Sebanyak 2",
            "Abses Berulang","Sariawan Yang Menetap","Antibiotika Intravena","Infeksi Dalam","Immunodefisiensi Primer","Jenis Kanker","Infeksi Oportunistik",
            "Perawatan Diri Sendiri","Mobilitas","Aktivitas Sehari-hari","Nyeri/Tidak Nyaman","Skala Nyeri Dengan FPS-hv","Istirahat/Tidur",
            "Keterangan Istirahat/Tidur","Penggunaan Obat Tidur","Keterangan Penggunaan Obat Tidur","Olahraga","Keterangan Olahraga"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        
        tbObat.setModel(tabMode);
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 35; i++) {
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
                column.setPreferredWidth(65);
            }else if(i==5){
                column.setPreferredWidth(80);
            }else if(i==6){
                column.setPreferredWidth(150);
            }else if(i==7){
                column.setPreferredWidth(115);
            }else if(i==8){
                column.setPreferredWidth(77);
            }else if(i==9){
                column.setPreferredWidth(160);
            }else if(i==10){
                column.setPreferredWidth(80);
            }else if(i==11){
                column.setPreferredWidth(150);
            }else if(i==12){
                column.setPreferredWidth(79);
            }else if(i==13){
                column.setPreferredWidth(105);
            }else if(i==14){
                column.setPreferredWidth(100);
            }else if(i==15){
                column.setPreferredWidth(131);
            }else if(i==16){
                column.setPreferredWidth(120);
            }else if(i==17){
                column.setPreferredWidth(82);
            }else if(i==18){
                column.setPreferredWidth(125);
            }else if(i==19){
                column.setPreferredWidth(112);
            }else if(i==20){
                column.setPreferredWidth(77);
            }else if(i==21){
                column.setPreferredWidth(131);
            }else if(i==22){
                column.setPreferredWidth(70);
            }else if(i==23){
                column.setPreferredWidth(106);
            }else if(i==24){
                column.setPreferredWidth(180);
            }else if(i==25){
                column.setPreferredWidth(180);
            }else if(i==26){
                column.setPreferredWidth(180);
            }else if(i==27){
                column.setPreferredWidth(180);
            }else if(i==28){
                column.setPreferredWidth(180);
            }else if(i==29){
                column.setPreferredWidth(80);
            }else if(i==30){
                column.setPreferredWidth(140);
            }else if(i==31){
                column.setPreferredWidth(123);
            }else if(i==32){
                column.setPreferredWidth(182);
            }else if(i==33){
                column.setPreferredWidth(55);
            }else if(i==34){
                column.setPreferredWidth(112);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());
        
        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        KeteranganKondisiMasuk.setDocument(new batasInput((int)50).getKata(KeteranganKondisiMasuk));
        DiagnosaMedis.setDocument(new batasInput((int)100).getKata(DiagnosaMedis));
        KeteranganIstirahat.setDocument(new batasInput((int)50).getKata(KeteranganIstirahat));
        KeteranganPenggunaanObatTidur.setDocument(new batasInput((int)50).getKata(KeteranganPenggunaanObatTidur));
        KeteranganOlahraga.setDocument(new batasInput((int)50).getKata(KeteranganOlahraga));
        DiagnosaMedis.setDocument(new batasInput((byte)100).getKata(DiagnosaMedis));
        TCari.setDocument(new batasInput((int)100).getKata(TCari));
        
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

        LoadHTML = new widget.editorpane();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnPenilaianMedis = new javax.swing.JMenuItem();
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
        TNoRw = new widget.TextBox();
        TPasien = new widget.TextBox();
        TNoRM = new widget.TextBox();
        label14 = new widget.Label();
        KdPetugas = new widget.TextBox();
        NmPetugas = new widget.TextBox();
        BtnPetugas = new widget.Button();
        jLabel8 = new widget.Label();
        TglLahir = new widget.TextBox();
        Jk = new widget.TextBox();
        jLabel10 = new widget.Label();
        jLabel11 = new widget.Label();
        jSeparator1 = new javax.swing.JSeparator();
        label11 = new widget.Label();
        TglAsuhan = new widget.Tanggal();
        jLabel12 = new widget.Label();
        DiagnosaMedis = new widget.TextBox();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel99 = new widget.Label();
        jLabel38 = new widget.Label();
        KondisiMasuk = new widget.ComboBox();
        KeteranganKondisiMasuk = new widget.TextBox();
        jLabel39 = new widget.Label();
        Anamnesis = new widget.ComboBox();
        jLabel40 = new widget.Label();
        AsalMasuk = new widget.ComboBox();
        jLabel13 = new widget.Label();
        jLabel14 = new widget.Label();
        jLabel15 = new widget.Label();
        InfeksiTelinga = new widget.ComboBox();
        Sinus = new widget.ComboBox();
        jLabel16 = new widget.Label();
        Antibiotik = new widget.ComboBox();
        jLabel17 = new widget.Label();
        Pneumonia = new widget.ComboBox();
        jLabel18 = new widget.Label();
        Abses = new widget.ComboBox();
        jLabel20 = new widget.Label();
        Sariawan = new widget.ComboBox();
        jLabel22 = new widget.Label();
        MemerlukanAntibitotika = new widget.ComboBox();
        jLabel23 = new widget.Label();
        InfeksiDalam = new widget.ComboBox();
        jLabel24 = new widget.Label();
        Immunodefisiensi = new widget.ComboBox();
        jLabel25 = new widget.Label();
        JenisKanker = new widget.ComboBox();
        jLabel26 = new widget.Label();
        InfeksiOportunistik = new widget.ComboBox();
        jLabel41 = new widget.Label();
        jSeparator12 = new javax.swing.JSeparator();
        jLabel95 = new widget.Label();
        jLabel27 = new widget.Label();
        jLabel28 = new widget.Label();
        Mobilitas = new widget.ComboBox();
        PerawatanDiri = new widget.ComboBox();
        jLabel29 = new widget.Label();
        jLabel30 = new widget.Label();
        AktivitasSeharihari = new widget.ComboBox();
        jLabel31 = new widget.Label();
        NyeriTidakNyaman = new widget.ComboBox();
        jLabel32 = new widget.Label();
        jLabel42 = new widget.Label();
        IstirahatTidur = new widget.ComboBox();
        KeteranganIstirahat = new widget.TextBox();
        KeteranganPenggunaanObatTidur = new widget.TextBox();
        PenggunaanObatTidur = new widget.ComboBox();
        jLabel43 = new widget.Label();
        jLabel44 = new widget.Label();
        Olahraga = new widget.ComboBox();
        KeteranganOlahraga = new widget.TextBox();
        jLabel45 = new widget.Label();
        SkalaNyeri = new widget.ComboBox();
        jLabel46 = new widget.Label();
        jLabel47 = new widget.Label();
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

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnPenilaianMedis.setBackground(new java.awt.Color(255, 255, 254));
        MnPenilaianMedis.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPenilaianMedis.setForeground(new java.awt.Color(50, 50, 50));
        MnPenilaianMedis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPenilaianMedis.setText("Laporan Pengkajian Tambahan Pasien Geriatri");
        MnPenilaianMedis.setName("MnPenilaianMedis"); // NOI18N
        MnPenilaianMedis.setPreferredSize(new java.awt.Dimension(270, 26));
        MnPenilaianMedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPenilaianMedisActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnPenilaianMedis);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Pengkajian Tambahan Pasien Geriatri ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setPreferredSize(new java.awt.Dimension(467, 500));
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
        TabRawat.setPreferredSize(new java.awt.Dimension(457, 480));
        TabRawat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatMouseClicked(evt);
            }
        });

        internalFrame2.setBorder(null);
        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setPreferredSize(new java.awt.Dimension(102, 480));
        internalFrame2.setLayout(new java.awt.BorderLayout(1, 1));

        scrollInput.setName("scrollInput"); // NOI18N
        scrollInput.setPreferredSize(new java.awt.Dimension(102, 557));

        FormInput.setBackground(new java.awt.Color(255, 255, 255));
        FormInput.setBorder(null);
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(750, 803));
        FormInput.setLayout(null);

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

        label14.setText("Dokter/Petugas :");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label14);
        label14.setBounds(146, 40, 100, 23);

        KdPetugas.setEditable(false);
        KdPetugas.setName("KdPetugas"); // NOI18N
        KdPetugas.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput.add(KdPetugas);
        KdPetugas.setBounds(250, 40, 90, 23);

        NmPetugas.setEditable(false);
        NmPetugas.setName("NmPetugas"); // NOI18N
        NmPetugas.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmPetugas);
        NmPetugas.setBounds(342, 40, 160, 23);

        BtnPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPetugas.setMnemonic('2');
        BtnPetugas.setToolTipText("Alt+2");
        BtnPetugas.setName("BtnPetugas"); // NOI18N
        BtnPetugas.setPreferredSize(new java.awt.Dimension(28, 23));
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
        BtnPetugas.setBounds(504, 40, 28, 23);

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
        Jk.setBounds(74, 40, 80, 23);

        jLabel10.setText("No.Rawat :");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(0, 10, 70, 23);

        jLabel11.setText("J.K. :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(0, 40, 70, 23);

        jSeparator1.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator1.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator1.setName("jSeparator1"); // NOI18N
        FormInput.add(jSeparator1);
        jSeparator1.setBounds(0, 70, 750, 1);

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label11);
        label11.setBounds(538, 40, 52, 23);

        TglAsuhan.setForeground(new java.awt.Color(50, 70, 50));
        TglAsuhan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "06-11-2022 06:50:04" }));
        TglAsuhan.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        TglAsuhan.setName("TglAsuhan"); // NOI18N
        TglAsuhan.setOpaque(false);
        TglAsuhan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglAsuhanKeyPressed(evt);
            }
        });
        FormInput.add(TglAsuhan);
        TglAsuhan.setBounds(594, 40, 130, 23);

        jLabel12.setText("Diagnosa Medis :");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(0, 120, 145, 23);

        DiagnosaMedis.setHighlighter(null);
        DiagnosaMedis.setName("DiagnosaMedis"); // NOI18N
        DiagnosaMedis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosaMedisKeyPressed(evt);
            }
        });
        FormInput.add(DiagnosaMedis);
        DiagnosaMedis.setBounds(149, 120, 350, 23);

        jSeparator2.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator2.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator2.setName("jSeparator2"); // NOI18N
        FormInput.add(jSeparator2);
        jSeparator2.setBounds(0, 70, 750, 1);

        jLabel99.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel99.setText("I. RIWAYAT KESEHATAN");
        jLabel99.setName("jLabel99"); // NOI18N
        FormInput.add(jLabel99);
        jLabel99.setBounds(10, 70, 140, 23);

        jLabel38.setText(":");
        jLabel38.setName("jLabel38"); // NOI18N
        FormInput.add(jLabel38);
        jLabel38.setBounds(135, 90, 10, 23);

        KondisiMasuk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Mandiri", "Kursi Roda", "Dipapah", "Tempat Tidur" }));
        KondisiMasuk.setName("KondisiMasuk"); // NOI18N
        KondisiMasuk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KondisiMasukKeyPressed(evt);
            }
        });
        FormInput.add(KondisiMasuk);
        KondisiMasuk.setBounds(149, 90, 115, 23);

        KeteranganKondisiMasuk.setName("KeteranganKondisiMasuk"); // NOI18N
        KeteranganKondisiMasuk.setPreferredSize(new java.awt.Dimension(207, 23));
        KeteranganKondisiMasuk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKondisiMasukKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganKondisiMasuk);
        KeteranganKondisiMasuk.setBounds(267, 90, 232, 23);

        jLabel39.setText("Anamnesis :");
        jLabel39.setName("jLabel39"); // NOI18N
        FormInput.add(jLabel39);
        jLabel39.setBounds(521, 90, 71, 23);

        Anamnesis.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Autoanamnesis", "Alloanamnesis" }));
        Anamnesis.setName("Anamnesis"); // NOI18N
        Anamnesis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AnamnesisKeyPressed(evt);
            }
        });
        FormInput.add(Anamnesis);
        Anamnesis.setBounds(596, 90, 128, 23);

        jLabel40.setText("Asal Masuk :");
        jLabel40.setName("jLabel40"); // NOI18N
        FormInput.add(jLabel40);
        jLabel40.setBounds(521, 120, 71, 23);

        AsalMasuk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "IGD", "Kamar Bersalin", "Klinik" }));
        AsalMasuk.setName("AsalMasuk"); // NOI18N
        AsalMasuk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AsalMasukKeyPressed(evt);
            }
        });
        FormInput.add(AsalMasuk);
        AsalMasuk.setBounds(596, 120, 128, 23);

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel13.setText("Riwayat Immunokompromais :");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(45, 150, 180, 23);

        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel14.setText("-  Infeksi Telinga Baru Sebanyak 8 (Delapan) Kali Atau Lebih Dalam Setahun");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(65, 170, 570, 23);

        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel15.setText("-  Infeksi Berat Pada Sinus Sebanyak 2 (Dua) Kali Atau Lebih Dalam Setahun");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(65, 200, 570, 23);

        InfeksiTelinga.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        InfeksiTelinga.setName("InfeksiTelinga"); // NOI18N
        InfeksiTelinga.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                InfeksiTelingaKeyPressed(evt);
            }
        });
        FormInput.add(InfeksiTelinga);
        InfeksiTelinga.setBounds(644, 170, 80, 23);

        Sinus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Sinus.setName("Sinus"); // NOI18N
        Sinus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SinusKeyPressed(evt);
            }
        });
        FormInput.add(Sinus);
        Sinus.setBounds(644, 200, 80, 23);

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel16.setText("-  Penggunaan Antibiotik Tanpa Dampak Selama 2 (Dua) Bulan Atau Lebih");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput.add(jLabel16);
        jLabel16.setBounds(65, 230, 570, 23);

        Antibiotik.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Antibiotik.setName("Antibiotik"); // NOI18N
        Antibiotik.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AntibiotikKeyPressed(evt);
            }
        });
        FormInput.add(Antibiotik);
        Antibiotik.setBounds(644, 230, 80, 23);

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel17.setText("-  Pneumonia Sebanyak 2 (Dua) Kali Atau Lebih Dalam Setahun");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(65, 260, 570, 23);

        Pneumonia.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Pneumonia.setName("Pneumonia"); // NOI18N
        Pneumonia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PneumoniaKeyPressed(evt);
            }
        });
        FormInput.add(Pneumonia);
        Pneumonia.setBounds(644, 260, 80, 23);

        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel18.setText("-  Adanya Abses Berulang Pada Kulit Atau Organ Lainnya");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(65, 290, 570, 23);

        Abses.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Abses.setName("Abses"); // NOI18N
        Abses.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AbsesKeyPressed(evt);
            }
        });
        FormInput.add(Abses);
        Abses.setBounds(644, 290, 80, 23);

        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel20.setText("-  Adanya Sariawan Yang Menetap Atau Luka Pada Kulit");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(65, 320, 570, 23);

        Sariawan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Sariawan.setName("Sariawan"); // NOI18N
        Sariawan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SariawanKeyPressed(evt);
            }
        });
        FormInput.add(Sariawan);
        Sariawan.setBounds(644, 320, 80, 23);

        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel22.setText("-  Memerlukan Antibiotika Intravena Untuk Infeksi");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(65, 350, 570, 23);

        MemerlukanAntibitotika.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        MemerlukanAntibitotika.setName("MemerlukanAntibitotika"); // NOI18N
        MemerlukanAntibitotika.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MemerlukanAntibitotikaKeyPressed(evt);
            }
        });
        FormInput.add(MemerlukanAntibitotika);
        MemerlukanAntibitotika.setBounds(644, 350, 80, 23);

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel23.setText("-  Terdapat 2 (Dua) Atau Lebih Infeksi Dalam (Misalnya : Meningitis, Osteomielitis, Selulitis, Sepsis)");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(65, 380, 570, 23);

        InfeksiDalam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        InfeksiDalam.setName("InfeksiDalam"); // NOI18N
        InfeksiDalam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                InfeksiDalamKeyPressed(evt);
            }
        });
        FormInput.add(InfeksiDalam);
        InfeksiDalam.setBounds(644, 380, 80, 23);

        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel24.setText("-  Adanya Riwayat Keluarga Terhadap Immunodefisiensi Primer");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(65, 410, 570, 23);

        Immunodefisiensi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Immunodefisiensi.setName("Immunodefisiensi"); // NOI18N
        Immunodefisiensi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ImmunodefisiensiKeyPressed(evt);
            }
        });
        FormInput.add(Immunodefisiensi);
        Immunodefisiensi.setBounds(644, 410, 80, 23);

        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel25.setText("-  Adanya Jenis Kanker (Misalnya : Sarkoma Kaposi Atau Limfoma Non-Hodgins)");
        jLabel25.setName("jLabel25"); // NOI18N
        FormInput.add(jLabel25);
        jLabel25.setBounds(65, 440, 570, 23);

        JenisKanker.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        JenisKanker.setName("JenisKanker"); // NOI18N
        JenisKanker.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JenisKankerKeyPressed(evt);
            }
        });
        FormInput.add(JenisKanker);
        JenisKanker.setBounds(644, 440, 80, 23);

        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel26.setText("-  Adanya Infeksi Oportunistik (Misalnya : Pneumonia \"Pneumovystis Carinii\" Atau Infeksi Jamur Berulang)");
        jLabel26.setName("jLabel26"); // NOI18N
        FormInput.add(jLabel26);
        jLabel26.setBounds(65, 470, 570, 23);

        InfeksiOportunistik.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        InfeksiOportunistik.setName("InfeksiOportunistik"); // NOI18N
        InfeksiOportunistik.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                InfeksiOportunistikKeyPressed(evt);
            }
        });
        FormInput.add(InfeksiOportunistik);
        InfeksiOportunistik.setBounds(644, 470, 80, 23);

        jLabel41.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel41.setText("Kondisi Saat Masuk");
        jLabel41.setName("jLabel41"); // NOI18N
        FormInput.add(jLabel41);
        jLabel41.setBounds(45, 90, 100, 23);

        jSeparator12.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator12.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator12.setName("jSeparator12"); // NOI18N
        FormInput.add(jSeparator12);
        jSeparator12.setBounds(0, 500, 750, 1);

        jLabel95.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel95.setText("II. PENGKAJIAN KUALITAS HIDUP – EQ5D, POLA AKTIFITAS DAN ISTIRAHAT");
        jLabel95.setName("jLabel95"); // NOI18N
        FormInput.add(jLabel95);
        jLabel95.setBounds(10, 500, 430, 23);

        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel27.setText("Kualitas Hidup Dengan EQ5D :");
        jLabel27.setName("jLabel27"); // NOI18N
        FormInput.add(jLabel27);
        jLabel27.setBounds(45, 520, 180, 23);

        jLabel28.setText("Mobilitas :");
        jLabel28.setName("jLabel28"); // NOI18N
        FormInput.add(jLabel28);
        jLabel28.setBounds(45, 570, 165, 23);

        Mobilitas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Mempunyai Masalah Untuk Berjalan", "Ada Masalah Untuk Berjalan", "Hanya Mampu Berbaring" }));
        Mobilitas.setName("Mobilitas"); // NOI18N
        Mobilitas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MobilitasKeyPressed(evt);
            }
        });
        FormInput.add(Mobilitas);
        Mobilitas.setBounds(214, 570, 510, 23);

        PerawatanDiri.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Mempunyai Kesulitan Dalam Perawatan Diri Sendiri", "Mengalami Kesulitan Untuk Membasuh Badan, Mandi Atau Berpakaian", "Tidak Mampu Membasuh Badan, Mandi/Berpakaian Sendiri" }));
        PerawatanDiri.setName("PerawatanDiri"); // NOI18N
        PerawatanDiri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PerawatanDiriKeyPressed(evt);
            }
        });
        FormInput.add(PerawatanDiri);
        PerawatanDiri.setBounds(214, 540, 510, 23);

        jLabel29.setText("Perawatan Diri Sendiri :");
        jLabel29.setName("jLabel29"); // NOI18N
        FormInput.add(jLabel29);
        jLabel29.setBounds(45, 540, 165, 23);

        jLabel30.setText("Aktivitas Sehari-hari :");
        jLabel30.setName("jLabel30"); // NOI18N
        FormInput.add(jLabel30);
        jLabel30.setBounds(45, 600, 165, 23);

        AktivitasSeharihari.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tak Mempunyai Kesulitan Dalam Melaksanakan Kegiatan Sehari-hari", "Mempunyai Keterbatasan Dalam Melaksanakan Kegiatan Sehari-hari", "Tak Mampu Melaksanakan Kegiatan Sehari-hari" }));
        AktivitasSeharihari.setName("AktivitasSeharihari"); // NOI18N
        AktivitasSeharihari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AktivitasSeharihariKeyPressed(evt);
            }
        });
        FormInput.add(AktivitasSeharihari);
        AktivitasSeharihari.setBounds(214, 600, 510, 23);

        jLabel31.setText("Nyeri/Tidak Nyaman :");
        jLabel31.setName("jLabel31"); // NOI18N
        FormInput.add(jLabel31);
        jLabel31.setBounds(45, 630, 165, 23);

        NyeriTidakNyaman.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Mempunyai Keluhan Rasa Nyeri Atau Rasa Tak Nyaman", "Suka Merasakan Agak Nyeri/Agak Kurang Nyaman", "Menderita Karena Keluhan Rasa Nyeri/Tidak Nyaman" }));
        NyeriTidakNyaman.setName("NyeriTidakNyaman"); // NOI18N
        NyeriTidakNyaman.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NyeriTidakNyamanKeyPressed(evt);
            }
        });
        FormInput.add(NyeriTidakNyaman);
        NyeriTidakNyaman.setBounds(214, 630, 510, 23);

        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel32.setText("Pola Aktifitas Dan Istirahat");
        jLabel32.setName("jLabel32"); // NOI18N
        FormInput.add(jLabel32);
        jLabel32.setBounds(45, 690, 200, 23);

        jLabel42.setText("Istirahat/Tidur :");
        jLabel42.setName("jLabel42"); // NOI18N
        FormInput.add(jLabel42);
        jLabel42.setBounds(45, 710, 165, 23);

        IstirahatTidur.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "TAK", "Insomnia", "Lainnya" }));
        IstirahatTidur.setName("IstirahatTidur"); // NOI18N
        IstirahatTidur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                IstirahatTidurKeyPressed(evt);
            }
        });
        FormInput.add(IstirahatTidur);
        IstirahatTidur.setBounds(214, 710, 100, 23);

        KeteranganIstirahat.setName("KeteranganIstirahat"); // NOI18N
        KeteranganIstirahat.setPreferredSize(new java.awt.Dimension(207, 23));
        KeteranganIstirahat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganIstirahatKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganIstirahat);
        KeteranganIstirahat.setBounds(317, 710, 407, 23);

        KeteranganPenggunaanObatTidur.setName("KeteranganPenggunaanObatTidur"); // NOI18N
        KeteranganPenggunaanObatTidur.setPreferredSize(new java.awt.Dimension(207, 23));
        KeteranganPenggunaanObatTidur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganPenggunaanObatTidurKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganPenggunaanObatTidur);
        KeteranganPenggunaanObatTidur.setBounds(297, 740, 427, 23);

        PenggunaanObatTidur.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        PenggunaanObatTidur.setName("PenggunaanObatTidur"); // NOI18N
        PenggunaanObatTidur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenggunaanObatTidurKeyPressed(evt);
            }
        });
        FormInput.add(PenggunaanObatTidur);
        PenggunaanObatTidur.setBounds(214, 740, 80, 23);

        jLabel43.setText("Penggunaan Obat Tidur :");
        jLabel43.setName("jLabel43"); // NOI18N
        FormInput.add(jLabel43);
        jLabel43.setBounds(45, 740, 165, 23);

        jLabel44.setText("Olahraga :");
        jLabel44.setName("jLabel44"); // NOI18N
        FormInput.add(jLabel44);
        jLabel44.setBounds(45, 770, 165, 23);

        Olahraga.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Olahraga.setName("Olahraga"); // NOI18N
        Olahraga.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                OlahragaKeyPressed(evt);
            }
        });
        FormInput.add(Olahraga);
        Olahraga.setBounds(214, 770, 80, 23);

        KeteranganOlahraga.setName("KeteranganOlahraga"); // NOI18N
        KeteranganOlahraga.setPreferredSize(new java.awt.Dimension(207, 23));
        KeteranganOlahraga.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganOlahragaKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganOlahraga);
        KeteranganOlahraga.setBounds(297, 770, 427, 23);

        jLabel45.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel45.setText("Skala Nyeri Dengan FPS-hv");
        jLabel45.setName("jLabel45"); // NOI18N
        FormInput.add(jLabel45);
        jLabel45.setBounds(45, 660, 140, 23);

        SkalaNyeri.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0 - Tidak Nyeri", "2 - Dapat Ditoleransi(Aktifitas Tidak Tergangu)", "4 - Dapat Ditoleransi(Beberapa Aktifitas Sedikit Terganggu)", "5 - Tidak Dapat Ditoleransi(Masih Bisa Menggunakan Telp, Menonton TV/Membaca)", "6 - Tidak Dapat Ditoleransi(Tidak Bisa Menggunakan Telp, Menonton TV/Membaca)", "8 - Tidak Dapat Ditoleransi(Masih Bisa Berbicara Kerenya Nyeri)", "10 - Tidak Dapat Ditoleransi(Tidak Bisa Berbicara Kerenya Nyeri)" }));
        SkalaNyeri.setName("SkalaNyeri"); // NOI18N
        SkalaNyeri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkalaNyeriKeyPressed(evt);
            }
        });
        FormInput.add(SkalaNyeri);
        SkalaNyeri.setBounds(189, 660, 535, 23);

        jLabel46.setText(":");
        jLabel46.setName("jLabel46"); // NOI18N
        FormInput.add(jLabel46);
        jLabel46.setBounds(175, 660, 10, 23);

        jLabel47.setText(":");
        jLabel47.setName("jLabel47"); // NOI18N
        FormInput.add(jLabel47);
        jLabel47.setBounds(175, 690, 10, 23);

        scrollInput.setViewportView(FormInput);

        internalFrame2.add(scrollInput, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Input Pengkajian", internalFrame2);

        internalFrame3.setBorder(null);
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setLayout(new java.awt.BorderLayout(1, 1));

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

        internalFrame3.add(Scroll, java.awt.BorderLayout.CENTER);

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel19.setText("Tgl.Asuhan :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "06-11-2022" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "06-11-2022" }));
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
        TCari.setPreferredSize(new java.awt.Dimension(195, 23));
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

        TabRawat.addTab("Data Pengkajian", internalFrame3);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isRawat();
        }else{            
            Valid.pindah(evt,TCari,BtnPetugas);
        }
}//GEN-LAST:event_TNoRwKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TNoRM.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"Nama Pasien");
        }else if(NmPetugas.getText().trim().equals("")){
            Valid.textKosong(BtnPetugas,"Dokter/Petugas");
        }else if(DiagnosaMedis.getText().trim().equals("")){
            Valid.textKosong(DiagnosaMedis,"Diagnosa Medis");
        }else{
            if(Sequel.menyimpantf("penilaian_tambahan_geriatri","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat",30,new String[]{
                    TNoRw.getText(),Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),KdPetugas.getText(),
                    AsalMasuk.getSelectedItem().toString(),KondisiMasuk.getSelectedItem().toString(),KeteranganKondisiMasuk.getText(),Anamnesis.getSelectedItem().toString(),
                    DiagnosaMedis.getText(),InfeksiTelinga.getSelectedItem().toString(),Sinus.getSelectedItem().toString(),Antibiotik.getSelectedItem().toString(),
                    Pneumonia.getSelectedItem().toString(),Abses.getSelectedItem().toString(),Sariawan.getSelectedItem().toString(),MemerlukanAntibitotika.getSelectedItem().toString(),
                    InfeksiDalam.getSelectedItem().toString(),Immunodefisiensi.getSelectedItem().toString(),JenisKanker.getSelectedItem().toString(),InfeksiOportunistik.getSelectedItem().toString(),
                    IstirahatTidur.getSelectedItem().toString(),KeteranganIstirahat.getText(),PenggunaanObatTidur.getSelectedItem().toString(),KeteranganPenggunaanObatTidur.getText(),
                    Olahraga.getSelectedItem().toString(),KeteranganOlahraga.getText(),Mobilitas.getSelectedItem().toString(),PerawatanDiri.getSelectedItem().toString(),
                    AktivitasSeharihari.getSelectedItem().toString(),NyeriTidakNyaman.getSelectedItem().toString(),SkalaNyeri.getSelectedItem().toString()
                })==true){
                    emptTeks();
            }
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,KeteranganOlahraga,BtnBatal);
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
            }else{
                if(KdPetugas.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString())){
                    hapus();
                }else{
                    JOptionPane.showMessageDialog(null,"Hanya bisa dihapus oleh pegawai yang bersangkutan..!!");
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
        }else if(NmPetugas.getText().trim().equals("")){
            Valid.textKosong(BtnPetugas,"Dokter/Petugas");
        }else if(DiagnosaMedis.getText().trim().equals("")){
            Valid.textKosong(DiagnosaMedis,"Diagnosa Medis");
        }else{
            if(tbObat.getSelectedRow()>-1){
                if(akses.getkode().equals("Admin Utama")){
                    ganti();
                }else{
                    if(KdPetugas.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString())){
                        ganti();
                    }else{
                        JOptionPane.showMessageDialog(null,"Hanya bisa diganti oleh pegawai yang bersangkutan..!!");
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
                            "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penilaian_tambahan_geriatri.tanggal,"+
                            "penilaian_tambahan_geriatri.nik,pegawai.nama,penilaian_tambahan_geriatri.asal_masuk,penilaian_tambahan_geriatri.kondisi_masuk,penilaian_tambahan_geriatri.keterangan_kondisi_masuk,"+
                            "penilaian_tambahan_geriatri.anamnesis,penilaian_tambahan_geriatri.diagnosa_medis,penilaian_tambahan_geriatri.riwayat_immuno_telinga,penilaian_tambahan_geriatri.riwayat_immuno_sinus,"+
                            "penilaian_tambahan_geriatri.riwayat_immuno_antibiotik,penilaian_tambahan_geriatri.riwayat_immuno_pneumonia,penilaian_tambahan_geriatri.riwayat_immuno_abses,"+
                            "penilaian_tambahan_geriatri.riwayat_immuno_sariawan,penilaian_tambahan_geriatri.riwayat_immuno_memerlukan_antibiotik,penilaian_tambahan_geriatri.riwayat_immuno_infeksi_dalam,"+
                            "penilaian_tambahan_geriatri.riwayat_immuno_immunodefisiensi_primer,penilaian_tambahan_geriatri.riwayat_immuno_jenis_kangker,penilaian_tambahan_geriatri.riwayat_immuno_infeksi_oportunistik,"+
                            "penilaian_tambahan_geriatri.pola_aktifitas_tidur,penilaian_tambahan_geriatri.keterangan_pola_aktifitas_tidur,penilaian_tambahan_geriatri.pola_aktifitas_obat_tidur,"+
                            "penilaian_tambahan_geriatri.keterangan_pola_aktifitas_obat_tidur,penilaian_tambahan_geriatri.pola_aktifitas_olahraga,penilaian_tambahan_geriatri.keterangan_pola_aktifitas_olahraga,"+
                            "penilaian_tambahan_geriatri.kualitas_hidup_mobilitas,penilaian_tambahan_geriatri.kualitas_hidup_perawatan_diri,penilaian_tambahan_geriatri.kualitas_hidup_aktifitas_seharihari,"+
                            "penilaian_tambahan_geriatri.kualitas_hidup_rasa_nyeri,penilaian_tambahan_geriatri.skala_nyeri "+
                            "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                            "inner join penilaian_tambahan_geriatri on reg_periksa.no_rawat=penilaian_tambahan_geriatri.no_rawat "+
                            "inner join pegawai on penilaian_tambahan_geriatri.nik=pegawai.nik where "+
                            "penilaian_tambahan_geriatri.tanggal between ? and ? order by penilaian_tambahan_geriatri.tanggal");
                }else{
                    ps=koneksi.prepareStatement(
                            "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penilaian_tambahan_geriatri.tanggal,"+
                            "penilaian_tambahan_geriatri.nik,pegawai.nama,penilaian_tambahan_geriatri.asal_masuk,penilaian_tambahan_geriatri.kondisi_masuk,penilaian_tambahan_geriatri.keterangan_kondisi_masuk,"+
                            "penilaian_tambahan_geriatri.anamnesis,penilaian_tambahan_geriatri.diagnosa_medis,penilaian_tambahan_geriatri.riwayat_immuno_telinga,penilaian_tambahan_geriatri.riwayat_immuno_sinus,"+
                            "penilaian_tambahan_geriatri.riwayat_immuno_antibiotik,penilaian_tambahan_geriatri.riwayat_immuno_pneumonia,penilaian_tambahan_geriatri.riwayat_immuno_abses,"+
                            "penilaian_tambahan_geriatri.riwayat_immuno_sariawan,penilaian_tambahan_geriatri.riwayat_immuno_memerlukan_antibiotik,penilaian_tambahan_geriatri.riwayat_immuno_infeksi_dalam,"+
                            "penilaian_tambahan_geriatri.riwayat_immuno_immunodefisiensi_primer,penilaian_tambahan_geriatri.riwayat_immuno_jenis_kangker,penilaian_tambahan_geriatri.riwayat_immuno_infeksi_oportunistik,"+
                            "penilaian_tambahan_geriatri.pola_aktifitas_tidur,penilaian_tambahan_geriatri.keterangan_pola_aktifitas_tidur,penilaian_tambahan_geriatri.pola_aktifitas_obat_tidur,"+
                            "penilaian_tambahan_geriatri.keterangan_pola_aktifitas_obat_tidur,penilaian_tambahan_geriatri.pola_aktifitas_olahraga,penilaian_tambahan_geriatri.keterangan_pola_aktifitas_olahraga,"+
                            "penilaian_tambahan_geriatri.kualitas_hidup_mobilitas,penilaian_tambahan_geriatri.kualitas_hidup_perawatan_diri,penilaian_tambahan_geriatri.kualitas_hidup_aktifitas_seharihari,"+
                            "penilaian_tambahan_geriatri.kualitas_hidup_rasa_nyeri,penilaian_tambahan_geriatri.skala_nyeri "+
                            "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                            "inner join penilaian_tambahan_geriatri on reg_periksa.no_rawat=penilaian_tambahan_geriatri.no_rawat "+
                            "inner join pegawai on penilaian_tambahan_geriatri.nik=pegawai.nik where "+
                            "penilaian_tambahan_geriatri.tanggal between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or "+
                            "penilaian_tambahan_geriatri.nik like ? or pegawai.nama like ?) order by penilaian_tambahan_geriatri.tanggal");
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
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Kode Petugas</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Dokter/Petugas</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Tanggal</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Kondisi Masuk</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Keterangan Kondisi Saat Masuk</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Anamnesis</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Diagnosa Masuk</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Asal Masuk</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Infeksi Telinga Baru</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Infeksi Berat Sinus</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Antibiotik Tanpa Dampak</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Pneumonia Sebanyak 2</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Abses Berulang</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Sariawan Yang Menetap</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Antibiotika Intravena</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Infeksi Dalam</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Immunodefisiensi Primer</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Jenis Kanker</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Infeksi Oportunistik</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Perawatan Diri Sendiri</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Mobilitas</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Aktivitas Sehari-hari</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Nyeri/Tidak Nyaman</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Skala Nyeri Dengan FPS-hv</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Istirahat/Tidur</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Keterangan Istirahat/Tidur</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Penggunaan Obat Tidur</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Keterangan Penggunaan Obat Tidur</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Olahraga</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Keterangan Olahraga</b></td>"+
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
                               "<td valign='top'>"+rs.getString("nik")+"</td>"+
                               "<td valign='top'>"+rs.getString("nama")+"</td>"+
                               "<td valign='top'>"+rs.getString("tanggal")+"</td>"+
                               "<td valign='top'>"+rs.getString("kondisi_masuk")+"</td>"+
                               "<td valign='top'>"+rs.getString("keterangan_kondisi_masuk")+"</td>"+
                               "<td valign='top'>"+rs.getString("anamnesis")+"</td>"+
                               "<td valign='top'>"+rs.getString("diagnosa_medis")+"</td>"+
                               "<td valign='top'>"+rs.getString("asal_masuk")+"</td>"+
                               "<td valign='top'>"+rs.getString("riwayat_immuno_telinga")+"</td>"+
                               "<td valign='top'>"+rs.getString("riwayat_immuno_sinus")+"</td>"+
                               "<td valign='top'>"+rs.getString("riwayat_immuno_antibiotik")+"</td>"+
                               "<td valign='top'>"+rs.getString("riwayat_immuno_pneumonia")+"</td>"+
                               "<td valign='top'>"+rs.getString("riwayat_immuno_abses")+"</td>"+
                               "<td valign='top'>"+rs.getString("riwayat_immuno_sariawan")+"</td>"+
                               "<td valign='top'>"+rs.getString("riwayat_immuno_memerlukan_antibiotik")+"</td>"+
                               "<td valign='top'>"+rs.getString("riwayat_immuno_infeksi_dalam")+"</td>"+
                               "<td valign='top'>"+rs.getString("riwayat_immuno_immunodefisiensi_primer")+"</td>"+
                               "<td valign='top'>"+rs.getString("riwayat_immuno_jenis_kangker")+"</td>"+
                               "<td valign='top'>"+rs.getString("riwayat_immuno_infeksi_oportunistik")+"</td>"+
                               "<td valign='top'>"+rs.getString("kualitas_hidup_perawatan_diri")+"</td>"+
                               "<td valign='top'>"+rs.getString("kualitas_hidup_mobilitas")+"</td>"+
                               "<td valign='top'>"+rs.getString("kualitas_hidup_aktifitas_seharihari")+"</td>"+
                               "<td valign='top'>"+rs.getString("kualitas_hidup_rasa_nyeri")+"</td>"+
                               "<td valign='top'>"+rs.getString("skala_nyeri")+"</td>"+
                               "<td valign='top'>"+rs.getString("pola_aktifitas_tidur")+"</td>"+
                               "<td valign='top'>"+rs.getString("keterangan_pola_aktifitas_tidur")+"</td>"+
                               "<td valign='top'>"+rs.getString("pola_aktifitas_obat_tidur")+"</td>"+
                               "<td valign='top'>"+rs.getString("keterangan_pola_aktifitas_obat_tidur")+"</td>"+
                               "<td valign='top'>"+rs.getString("pola_aktifitas_olahraga")+"</td>"+
                               "<td valign='top'>"+rs.getString("keterangan_pola_aktifitas_olahraga")+"</td>"+
                            "</tr>");
                    }
                    LoadHTML.setText(
                        "<html>"+
                          "<table width='4500px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
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

                    File f = new File("DataPenilaianAwalMedisRalan.html");            
                    BufferedWriter bw = new BufferedWriter(new FileWriter(f));            
                    bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                                "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                                "<table width='4500px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                    "<tr class='isi2'>"+
                                        "<td valign='top' align='center'>"+
                                            "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                            akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                            akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                            "<font size='2' face='Tahoma'>DATA PENGKAJIAN TAMBAHAN PASIEN GERIATRI<br><br></font>"+        
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
                getData();
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

    private void BtnPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPetugasActionPerformed
        DlgCariPegawai pegawai=new DlgCariPegawai(null,false);
        pegawai.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(pegawai.getTable().getSelectedRow()!= -1){     
                    KdPetugas.setText(pegawai.getTable().getValueAt(pegawai.getTable().getSelectedRow(),0).toString());
                    NmPetugas.setText(pegawai.getTable().getValueAt(pegawai.getTable().getSelectedRow(),1).toString());
                    BtnPetugas.requestFocus();
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
        pegawai.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        pegawai.setLocationRelativeTo(internalFrame1);
        pegawai.setAlwaysOnTop(false);
        pegawai.setVisible(true);
    }//GEN-LAST:event_BtnPetugasActionPerformed

    private void BtnPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPetugasKeyPressed
        //Valid.pindah(evt,Edukasi,Hubungan);
    }//GEN-LAST:event_BtnPetugasKeyPressed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if(TabRawat.getSelectedIndex()==1){
            runBackground(() ->tampil());
        }
    }//GEN-LAST:event_TabRawatMouseClicked

    private void TglAsuhanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglAsuhanKeyPressed
        Valid.pindah(evt,BtnPetugas,DiagnosaMedis);
    }//GEN-LAST:event_TglAsuhanKeyPressed

    private void MnPenilaianMedisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPenilaianMedisActionPerformed
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
            param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbObat.getValueAt(tbObat.getSelectedRow(),6).toString()+"\nID "+(finger.equals("")?tbObat.getValueAt(tbObat.getSelectedRow(),5).toString():finger)+"\n"+Valid.SetTgl3(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString())); 
            Valid.MyReportqry("rptCetakPenilaianTambahanGeriatri.jasper","report","::[ Laporan Pengkajian Tambahan Geriatri ]::",
                "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penilaian_tambahan_geriatri.tanggal,"+
                "penilaian_tambahan_geriatri.nik,pegawai.nama,penilaian_tambahan_geriatri.asal_masuk,penilaian_tambahan_geriatri.kondisi_masuk,penilaian_tambahan_geriatri.keterangan_kondisi_masuk,"+
                "penilaian_tambahan_geriatri.anamnesis,penilaian_tambahan_geriatri.diagnosa_medis,penilaian_tambahan_geriatri.riwayat_immuno_telinga,penilaian_tambahan_geriatri.riwayat_immuno_sinus,"+
                "penilaian_tambahan_geriatri.riwayat_immuno_antibiotik,penilaian_tambahan_geriatri.riwayat_immuno_pneumonia,penilaian_tambahan_geriatri.riwayat_immuno_abses,"+
                "penilaian_tambahan_geriatri.riwayat_immuno_sariawan,penilaian_tambahan_geriatri.riwayat_immuno_memerlukan_antibiotik,penilaian_tambahan_geriatri.riwayat_immuno_infeksi_dalam,"+
                "penilaian_tambahan_geriatri.riwayat_immuno_immunodefisiensi_primer,penilaian_tambahan_geriatri.riwayat_immuno_jenis_kangker,penilaian_tambahan_geriatri.riwayat_immuno_infeksi_oportunistik,"+
                "penilaian_tambahan_geriatri.pola_aktifitas_tidur,penilaian_tambahan_geriatri.keterangan_pola_aktifitas_tidur,penilaian_tambahan_geriatri.pola_aktifitas_obat_tidur,"+
                "penilaian_tambahan_geriatri.keterangan_pola_aktifitas_obat_tidur,penilaian_tambahan_geriatri.pola_aktifitas_olahraga,penilaian_tambahan_geriatri.keterangan_pola_aktifitas_olahraga,"+
                "penilaian_tambahan_geriatri.kualitas_hidup_mobilitas,penilaian_tambahan_geriatri.kualitas_hidup_perawatan_diri,penilaian_tambahan_geriatri.kualitas_hidup_aktifitas_seharihari,"+
                "penilaian_tambahan_geriatri.kualitas_hidup_rasa_nyeri,penilaian_tambahan_geriatri.skala_nyeri from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join penilaian_tambahan_geriatri on reg_periksa.no_rawat=penilaian_tambahan_geriatri.no_rawat inner join pegawai on penilaian_tambahan_geriatri.nik=pegawai.nik "+
                "where penilaian_tambahan_geriatri.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'",param);
        }
    }//GEN-LAST:event_MnPenilaianMedisActionPerformed

    private void DiagnosaMedisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosaMedisKeyPressed
        Valid.pindah(evt,Anamnesis,AsalMasuk);
    }//GEN-LAST:event_DiagnosaMedisKeyPressed

    private void KondisiMasukKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KondisiMasukKeyPressed
        Valid.pindah(evt,TglAsuhan,KeteranganKondisiMasuk);
    }//GEN-LAST:event_KondisiMasukKeyPressed

    private void KeteranganKondisiMasukKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKondisiMasukKeyPressed
        Valid.pindah(evt,KondisiMasuk,Anamnesis);
    }//GEN-LAST:event_KeteranganKondisiMasukKeyPressed

    private void AnamnesisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AnamnesisKeyPressed
        Valid.pindah(evt,KeteranganKondisiMasuk,DiagnosaMedis);
    }//GEN-LAST:event_AnamnesisKeyPressed

    private void AsalMasukKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AsalMasukKeyPressed
        Valid.pindah(evt,DiagnosaMedis,InfeksiTelinga);
    }//GEN-LAST:event_AsalMasukKeyPressed

    private void InfeksiTelingaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_InfeksiTelingaKeyPressed
        Valid.pindah(evt,AsalMasuk,Sinus);
    }//GEN-LAST:event_InfeksiTelingaKeyPressed

    private void SinusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SinusKeyPressed
        Valid.pindah(evt,InfeksiDalam,Antibiotik);
    }//GEN-LAST:event_SinusKeyPressed

    private void AntibiotikKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AntibiotikKeyPressed
        Valid.pindah(evt,Sinus,Pneumonia);
    }//GEN-LAST:event_AntibiotikKeyPressed

    private void PneumoniaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PneumoniaKeyPressed
        Valid.pindah(evt,Antibiotik,Abses);
    }//GEN-LAST:event_PneumoniaKeyPressed

    private void AbsesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AbsesKeyPressed
        Valid.pindah(evt,Pneumonia,Sariawan);
    }//GEN-LAST:event_AbsesKeyPressed

    private void SariawanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SariawanKeyPressed
        Valid.pindah(evt,Abses,MemerlukanAntibitotika);
    }//GEN-LAST:event_SariawanKeyPressed

    private void MemerlukanAntibitotikaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MemerlukanAntibitotikaKeyPressed
        Valid.pindah(evt,Sariawan,InfeksiDalam);
    }//GEN-LAST:event_MemerlukanAntibitotikaKeyPressed

    private void InfeksiDalamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_InfeksiDalamKeyPressed
        Valid.pindah(evt,MemerlukanAntibitotika,Immunodefisiensi);
    }//GEN-LAST:event_InfeksiDalamKeyPressed

    private void ImmunodefisiensiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ImmunodefisiensiKeyPressed
        Valid.pindah(evt,InfeksiDalam,JenisKanker);
    }//GEN-LAST:event_ImmunodefisiensiKeyPressed

    private void JenisKankerKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JenisKankerKeyPressed
        Valid.pindah(evt,Immunodefisiensi,InfeksiOportunistik);
    }//GEN-LAST:event_JenisKankerKeyPressed

    private void InfeksiOportunistikKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_InfeksiOportunistikKeyPressed
        Valid.pindah(evt,JenisKanker,PerawatanDiri);
    }//GEN-LAST:event_InfeksiOportunistikKeyPressed

    private void MobilitasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MobilitasKeyPressed
        Valid.pindah(evt,PerawatanDiri,AktivitasSeharihari);
    }//GEN-LAST:event_MobilitasKeyPressed

    private void PerawatanDiriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PerawatanDiriKeyPressed
        Valid.pindah(evt,InfeksiOportunistik,Mobilitas);
    }//GEN-LAST:event_PerawatanDiriKeyPressed

    private void AktivitasSeharihariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AktivitasSeharihariKeyPressed
        Valid.pindah(evt,Mobilitas,NyeriTidakNyaman);
    }//GEN-LAST:event_AktivitasSeharihariKeyPressed

    private void NyeriTidakNyamanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NyeriTidakNyamanKeyPressed
        Valid.pindah(evt,AktivitasSeharihari,SkalaNyeri);
    }//GEN-LAST:event_NyeriTidakNyamanKeyPressed

    private void IstirahatTidurKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_IstirahatTidurKeyPressed
        Valid.pindah(evt,SkalaNyeri,KeteranganIstirahat);
    }//GEN-LAST:event_IstirahatTidurKeyPressed

    private void KeteranganIstirahatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganIstirahatKeyPressed
        Valid.pindah(evt,IstirahatTidur,PenggunaanObatTidur);
    }//GEN-LAST:event_KeteranganIstirahatKeyPressed

    private void KeteranganPenggunaanObatTidurKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganPenggunaanObatTidurKeyPressed
        Valid.pindah(evt,PenggunaanObatTidur,Olahraga);
    }//GEN-LAST:event_KeteranganPenggunaanObatTidurKeyPressed

    private void PenggunaanObatTidurKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenggunaanObatTidurKeyPressed
        Valid.pindah(evt,KeteranganIstirahat,KeteranganPenggunaanObatTidur);
    }//GEN-LAST:event_PenggunaanObatTidurKeyPressed

    private void OlahragaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_OlahragaKeyPressed
        Valid.pindah(evt,KeteranganPenggunaanObatTidur,KeteranganOlahraga);
    }//GEN-LAST:event_OlahragaKeyPressed

    private void KeteranganOlahragaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganOlahragaKeyPressed
        Valid.pindah(evt,Olahraga,BtnSimpan);
    }//GEN-LAST:event_KeteranganOlahragaKeyPressed

    private void SkalaNyeriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkalaNyeriKeyPressed
        Valid.pindah(evt,NyeriTidakNyaman,IstirahatTidur);
    }//GEN-LAST:event_SkalaNyeriKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMPenilaianTambahanGeriatri dialog = new RMPenilaianTambahanGeriatri(new javax.swing.JFrame(), true);
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
    private widget.ComboBox Abses;
    private widget.ComboBox AktivitasSeharihari;
    private widget.ComboBox Anamnesis;
    private widget.ComboBox Antibiotik;
    private widget.ComboBox AsalMasuk;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPetugas;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.TextBox DiagnosaMedis;
    private widget.PanelBiasa FormInput;
    private widget.ComboBox Immunodefisiensi;
    private widget.ComboBox InfeksiDalam;
    private widget.ComboBox InfeksiOportunistik;
    private widget.ComboBox InfeksiTelinga;
    private widget.ComboBox IstirahatTidur;
    private widget.ComboBox JenisKanker;
    private widget.TextBox Jk;
    private widget.TextBox KdPetugas;
    private widget.TextBox KeteranganIstirahat;
    private widget.TextBox KeteranganKondisiMasuk;
    private widget.TextBox KeteranganOlahraga;
    private widget.TextBox KeteranganPenggunaanObatTidur;
    private widget.ComboBox KondisiMasuk;
    private widget.Label LCount;
    private widget.editorpane LoadHTML;
    private widget.ComboBox MemerlukanAntibitotika;
    private javax.swing.JMenuItem MnPenilaianMedis;
    private widget.ComboBox Mobilitas;
    private widget.TextBox NmPetugas;
    private widget.ComboBox NyeriTidakNyaman;
    private widget.ComboBox Olahraga;
    private widget.ComboBox PenggunaanObatTidur;
    private widget.ComboBox PerawatanDiri;
    private widget.ComboBox Pneumonia;
    private widget.ComboBox Sariawan;
    private widget.ScrollPane Scroll;
    private widget.ComboBox Sinus;
    private widget.ComboBox SkalaNyeri;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private javax.swing.JTabbedPane TabRawat;
    private widget.Tanggal TglAsuhan;
    private widget.TextBox TglLahir;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel12;
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
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel95;
    private widget.Label jLabel99;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator2;
    private widget.Label label11;
    private widget.Label label14;
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
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penilaian_tambahan_geriatri.tanggal,"+
                        "penilaian_tambahan_geriatri.nik,pegawai.nama,penilaian_tambahan_geriatri.asal_masuk,penilaian_tambahan_geriatri.kondisi_masuk,penilaian_tambahan_geriatri.keterangan_kondisi_masuk,"+
                        "penilaian_tambahan_geriatri.anamnesis,penilaian_tambahan_geriatri.diagnosa_medis,penilaian_tambahan_geriatri.riwayat_immuno_telinga,penilaian_tambahan_geriatri.riwayat_immuno_sinus,"+
                        "penilaian_tambahan_geriatri.riwayat_immuno_antibiotik,penilaian_tambahan_geriatri.riwayat_immuno_pneumonia,penilaian_tambahan_geriatri.riwayat_immuno_abses,"+
                        "penilaian_tambahan_geriatri.riwayat_immuno_sariawan,penilaian_tambahan_geriatri.riwayat_immuno_memerlukan_antibiotik,penilaian_tambahan_geriatri.riwayat_immuno_infeksi_dalam,"+
                        "penilaian_tambahan_geriatri.riwayat_immuno_immunodefisiensi_primer,penilaian_tambahan_geriatri.riwayat_immuno_jenis_kangker,penilaian_tambahan_geriatri.riwayat_immuno_infeksi_oportunistik,"+
                        "penilaian_tambahan_geriatri.pola_aktifitas_tidur,penilaian_tambahan_geriatri.keterangan_pola_aktifitas_tidur,penilaian_tambahan_geriatri.pola_aktifitas_obat_tidur,"+
                        "penilaian_tambahan_geriatri.keterangan_pola_aktifitas_obat_tidur,penilaian_tambahan_geriatri.pola_aktifitas_olahraga,penilaian_tambahan_geriatri.keterangan_pola_aktifitas_olahraga,"+
                        "penilaian_tambahan_geriatri.kualitas_hidup_mobilitas,penilaian_tambahan_geriatri.kualitas_hidup_perawatan_diri,penilaian_tambahan_geriatri.kualitas_hidup_aktifitas_seharihari,"+
                        "penilaian_tambahan_geriatri.kualitas_hidup_rasa_nyeri,penilaian_tambahan_geriatri.skala_nyeri "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join penilaian_tambahan_geriatri on reg_periksa.no_rawat=penilaian_tambahan_geriatri.no_rawat "+
                        "inner join pegawai on penilaian_tambahan_geriatri.nik=pegawai.nik where "+
                        "penilaian_tambahan_geriatri.tanggal between ? and ? order by penilaian_tambahan_geriatri.tanggal");
            }else{
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penilaian_tambahan_geriatri.tanggal,"+
                        "penilaian_tambahan_geriatri.nik,pegawai.nama,penilaian_tambahan_geriatri.asal_masuk,penilaian_tambahan_geriatri.kondisi_masuk,penilaian_tambahan_geriatri.keterangan_kondisi_masuk,"+
                        "penilaian_tambahan_geriatri.anamnesis,penilaian_tambahan_geriatri.diagnosa_medis,penilaian_tambahan_geriatri.riwayat_immuno_telinga,penilaian_tambahan_geriatri.riwayat_immuno_sinus,"+
                        "penilaian_tambahan_geriatri.riwayat_immuno_antibiotik,penilaian_tambahan_geriatri.riwayat_immuno_pneumonia,penilaian_tambahan_geriatri.riwayat_immuno_abses,"+
                        "penilaian_tambahan_geriatri.riwayat_immuno_sariawan,penilaian_tambahan_geriatri.riwayat_immuno_memerlukan_antibiotik,penilaian_tambahan_geriatri.riwayat_immuno_infeksi_dalam,"+
                        "penilaian_tambahan_geriatri.riwayat_immuno_immunodefisiensi_primer,penilaian_tambahan_geriatri.riwayat_immuno_jenis_kangker,penilaian_tambahan_geriatri.riwayat_immuno_infeksi_oportunistik,"+
                        "penilaian_tambahan_geriatri.pola_aktifitas_tidur,penilaian_tambahan_geriatri.keterangan_pola_aktifitas_tidur,penilaian_tambahan_geriatri.pola_aktifitas_obat_tidur,"+
                        "penilaian_tambahan_geriatri.keterangan_pola_aktifitas_obat_tidur,penilaian_tambahan_geriatri.pola_aktifitas_olahraga,penilaian_tambahan_geriatri.keterangan_pola_aktifitas_olahraga,"+
                        "penilaian_tambahan_geriatri.kualitas_hidup_mobilitas,penilaian_tambahan_geriatri.kualitas_hidup_perawatan_diri,penilaian_tambahan_geriatri.kualitas_hidup_aktifitas_seharihari,"+
                        "penilaian_tambahan_geriatri.kualitas_hidup_rasa_nyeri,penilaian_tambahan_geriatri.skala_nyeri "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join penilaian_tambahan_geriatri on reg_periksa.no_rawat=penilaian_tambahan_geriatri.no_rawat "+
                        "inner join pegawai on penilaian_tambahan_geriatri.nik=pegawai.nik where "+
                        "penilaian_tambahan_geriatri.tanggal between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or "+
                        "penilaian_tambahan_geriatri.nik like ? or pegawai.nama like ?) order by penilaian_tambahan_geriatri.tanggal");
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
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getDate("tgl_lahir"),rs.getString("jk"),rs.getString("nik"),rs.getString("nama"),rs.getString("tanggal"),
                        rs.getString("kondisi_masuk"),rs.getString("keterangan_kondisi_masuk"),rs.getString("anamnesis"),rs.getString("diagnosa_medis"),rs.getString("asal_masuk"),rs.getString("riwayat_immuno_telinga"),
                        rs.getString("riwayat_immuno_sinus"),rs.getString("riwayat_immuno_antibiotik"),rs.getString("riwayat_immuno_pneumonia"),rs.getString("riwayat_immuno_abses"),rs.getString("riwayat_immuno_sariawan"),
                        rs.getString("riwayat_immuno_memerlukan_antibiotik"),rs.getString("riwayat_immuno_infeksi_dalam"),rs.getString("riwayat_immuno_immunodefisiensi_primer"),rs.getString("riwayat_immuno_jenis_kangker"),
                        rs.getString("riwayat_immuno_infeksi_oportunistik"),rs.getString("kualitas_hidup_perawatan_diri"),rs.getString("kualitas_hidup_mobilitas"),rs.getString("kualitas_hidup_aktifitas_seharihari"),
                        rs.getString("kualitas_hidup_rasa_nyeri"),rs.getString("skala_nyeri"),rs.getString("pola_aktifitas_tidur"),rs.getString("keterangan_pola_aktifitas_tidur"),rs.getString("pola_aktifitas_obat_tidur"),
                        rs.getString("keterangan_pola_aktifitas_obat_tidur"),rs.getString("pola_aktifitas_olahraga"),rs.getString("keterangan_pola_aktifitas_olahraga")
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
        TglAsuhan.setDate(new Date());
        DiagnosaMedis.setText("");
        KondisiMasuk.setSelectedIndex(0);
        KeteranganKondisiMasuk.setText("");
        Anamnesis.setSelectedIndex(0);
        DiagnosaMedis.setText("");
        AsalMasuk.setSelectedIndex(0);
        InfeksiTelinga.setSelectedIndex(0);
        Sinus.setSelectedIndex(0);
        Antibiotik.setSelectedIndex(0);
        Pneumonia.setSelectedIndex(0);
        Abses.setSelectedIndex(0);
        Sariawan.setSelectedIndex(0);
        MemerlukanAntibitotika.setSelectedIndex(0);
        InfeksiDalam.setSelectedIndex(0);
        Immunodefisiensi.setSelectedIndex(0);
        JenisKanker.setSelectedIndex(0);
        InfeksiOportunistik.setSelectedIndex(0);
        PerawatanDiri.setSelectedIndex(0);
        Mobilitas.setSelectedIndex(0);
        AktivitasSeharihari.setSelectedIndex(0);
        NyeriTidakNyaman.setSelectedIndex(0);
        SkalaNyeri.setSelectedIndex(0);
        IstirahatTidur.setSelectedIndex(0);
        KeteranganIstirahat.setText("");
        PenggunaanObatTidur.setSelectedIndex(0);
        KeteranganPenggunaanObatTidur.setText("");
        Olahraga.setSelectedIndex(0);
        KeteranganOlahraga.setText("");
        TabRawat.setSelectedIndex(0);
        KondisiMasuk.requestFocus();
    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()); 
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            Jk.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString()); 
            KondisiMasuk.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            KeteranganKondisiMasuk.setText(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            Anamnesis.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            DiagnosaMedis.setText(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            AsalMasuk.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            InfeksiTelinga.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            Sinus.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            Antibiotik.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
            Pneumonia.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            Abses.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());
            Sariawan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
            MemerlukanAntibitotika.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
            InfeksiDalam.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString());
            Immunodefisiensi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString());
            JenisKanker.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString());
            InfeksiOportunistik.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString());
            PerawatanDiri.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),24).toString());
            Mobilitas.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),25).toString());
            AktivitasSeharihari.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),26).toString());
            NyeriTidakNyaman.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),27).toString());
            SkalaNyeri.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),28).toString());
            IstirahatTidur.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),29).toString());
            KeteranganIstirahat.setText(tbObat.getValueAt(tbObat.getSelectedRow(),30).toString());
            PenggunaanObatTidur.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),31).toString());
            KeteranganPenggunaanObatTidur.setText(tbObat.getValueAt(tbObat.getSelectedRow(),32).toString());
            Olahraga.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),33).toString());
            KeteranganOlahraga.setText(tbObat.getValueAt(tbObat.getSelectedRow(),34).toString());
            Valid.SetTgl2(TglAsuhan,tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());
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
    }
    
    public void isCek(){
        BtnSimpan.setEnabled(akses.getpenilaian_tambahan_pasien_geriatri());
        BtnHapus.setEnabled(akses.getpenilaian_tambahan_pasien_geriatri());
        BtnEdit.setEnabled(akses.getpenilaian_tambahan_pasien_geriatri());
        BtnEdit.setEnabled(akses.getpenilaian_tambahan_pasien_geriatri());
        if(akses.getjml2()>=1){
            BtnPetugas.setEnabled(false);
            KdPetugas.setText(akses.getkode());
            NmPetugas.setText(Sequel.CariPegawai(KdPetugas.getText()));
        }            
    }
    
    public void setTampil(){
       TabRawat.setSelectedIndex(1);
    }

    private void hapus() {
        if(Sequel.queryu2tf("delete from penilaian_tambahan_geriatri where no_rawat=? and tanggal=?",2,new String[]{
            tbObat.getValueAt(tbObat.getSelectedRow(),0).toString(),tbObat.getValueAt(tbObat.getSelectedRow(),7).toString()
        })==true){
            tabMode.removeRow(tbObat.getSelectedRow());
            LCount.setText(""+tabMode.getRowCount());
            TabRawat.setSelectedIndex(1);
        }else{
            JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
        }
    }

    private void ganti() {
        if(Sequel.mengedittf("penilaian_tambahan_geriatri","no_rawat=?","no_rawat=?,tanggal=?,nik=?,asal_masuk=?,kondisi_masuk=?,keterangan_kondisi_masuk=?,anamnesis=?,diagnosa_medis=?,"+
                "riwayat_immuno_telinga=?,riwayat_immuno_sinus=?,riwayat_immuno_antibiotik=?,riwayat_immuno_pneumonia=?,riwayat_immuno_abses=?,riwayat_immuno_sariawan=?,"+
                "riwayat_immuno_memerlukan_antibiotik=?,riwayat_immuno_infeksi_dalam=?,riwayat_immuno_immunodefisiensi_primer=?,riwayat_immuno_jenis_kangker=?,riwayat_immuno_infeksi_oportunistik=?,"+
                "pola_aktifitas_tidur=?,keterangan_pola_aktifitas_tidur=?,pola_aktifitas_obat_tidur=?,keterangan_pola_aktifitas_obat_tidur=?,pola_aktifitas_olahraga=?,"+
                "keterangan_pola_aktifitas_olahraga=?,kualitas_hidup_mobilitas=?,kualitas_hidup_perawatan_diri=?,kualitas_hidup_aktifitas_seharihari=?,kualitas_hidup_rasa_nyeri=?,"+
                "skala_nyeri=?",31,new String[]{
                TNoRw.getText(),Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),KdPetugas.getText(),
                AsalMasuk.getSelectedItem().toString(),KondisiMasuk.getSelectedItem().toString(),KeteranganKondisiMasuk.getText(),Anamnesis.getSelectedItem().toString(),
                DiagnosaMedis.getText(),InfeksiTelinga.getSelectedItem().toString(),Sinus.getSelectedItem().toString(),Antibiotik.getSelectedItem().toString(),
                Pneumonia.getSelectedItem().toString(),Abses.getSelectedItem().toString(),Sariawan.getSelectedItem().toString(),MemerlukanAntibitotika.getSelectedItem().toString(),
                InfeksiDalam.getSelectedItem().toString(),Immunodefisiensi.getSelectedItem().toString(),JenisKanker.getSelectedItem().toString(),InfeksiOportunistik.getSelectedItem().toString(),
                IstirahatTidur.getSelectedItem().toString(),KeteranganIstirahat.getText(),PenggunaanObatTidur.getSelectedItem().toString(),KeteranganPenggunaanObatTidur.getText(),
                Olahraga.getSelectedItem().toString(),KeteranganOlahraga.getText(),Mobilitas.getSelectedItem().toString(),PerawatanDiri.getSelectedItem().toString(),
                AktivitasSeharihari.getSelectedItem().toString(),NyeriTidakNyaman.getSelectedItem().toString(),SkalaNyeri.getSelectedItem().toString(),
                tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
            })==true){
               runBackground(() ->tampil());
               emptTeks();
               TabRawat.setSelectedIndex(1);
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

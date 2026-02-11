/*
 * By Mas Elkhanza
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
import javax.swing.WindowConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import kepegawaian.DlgCariDokter;


/**
 *
 * @author perpustakaan
 */
public final class RMCatatanPengkajianPaskaOperasi extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;
    private DlgCariDokter dokter;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private volatile boolean ceksukses = false;
    private String TANGGALMUNDUR="yes";
    
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public RMCatatanPengkajianPaskaOperasi(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.RM","Nama Pasien","Tgl.Lahir","J.K.","Kode Dokter","Nama Dokter","Tanggal","I. Rawat Paska Operasi/Tindakan","II. Cairan",
            "III. Antibiotika","IV. Analgetika","V. Medikamentosa Lain","VI. Diet","VII. Pemeriksaan Laboratorium","VIII. Tranfusi","IX. Lain-lain"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        
        tbObat.setModel(tabMode);
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 17; i++) {
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
            }else{
                column.setPreferredWidth(300);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());
        
        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        
        RawatPaskaOperasi.setDocument(new batasInput((int)250).getKata(RawatPaskaOperasi));
        Cairan.setDocument(new batasInput((int)500).getKata(Cairan));
        Antibiotika.setDocument(new batasInput((int)500).getKata(Antibiotika));
        Analgetika.setDocument(new batasInput((int)500).getKata(Analgetika));
        MedikamentosaLain.setDocument(new batasInput((int)500).getKata(MedikamentosaLain));
        Diet.setDocument(new batasInput((int)500).getKata(Diet));
        PemeriksaanLaborat.setDocument(new batasInput((int)500).getKata(PemeriksaanLaborat));
        Transfusi.setDocument(new batasInput((int)250).getKata(Transfusi));
        Lainlain.setDocument(new batasInput((int)500).getKata(Lainlain));
        TCari.setDocument(new batasInput((int)100).getKata(TCari));
        
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

        LoadHTML = new widget.editorpane();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnPenilaianMedis = new javax.swing.JMenuItem();
        TanggalRegistrasi = new widget.TextBox();
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
        KdDokter = new widget.TextBox();
        NmDokter = new widget.TextBox();
        BtnDokter = new widget.Button();
        jLabel8 = new widget.Label();
        TglLahir = new widget.TextBox();
        Jk = new widget.TextBox();
        jLabel10 = new widget.Label();
        jLabel11 = new widget.Label();
        jSeparator1 = new javax.swing.JSeparator();
        label11 = new widget.Label();
        TglAsuhan = new widget.Tanggal();
        jLabel109 = new widget.Label();
        scrollPane14 = new widget.ScrollPane();
        RawatPaskaOperasi = new widget.TextArea();
        jLabel110 = new widget.Label();
        jSeparator2 = new javax.swing.JSeparator();
        scrollPane15 = new widget.ScrollPane();
        Cairan = new widget.TextArea();
        jLabel111 = new widget.Label();
        jSeparator3 = new javax.swing.JSeparator();
        scrollPane16 = new widget.ScrollPane();
        Antibiotika = new widget.TextArea();
        jLabel112 = new widget.Label();
        jSeparator4 = new javax.swing.JSeparator();
        scrollPane17 = new widget.ScrollPane();
        Analgetika = new widget.TextArea();
        jLabel113 = new widget.Label();
        jSeparator5 = new javax.swing.JSeparator();
        scrollPane18 = new widget.ScrollPane();
        MedikamentosaLain = new widget.TextArea();
        jLabel114 = new widget.Label();
        jSeparator6 = new javax.swing.JSeparator();
        scrollPane19 = new widget.ScrollPane();
        Diet = new widget.TextArea();
        jLabel115 = new widget.Label();
        jSeparator7 = new javax.swing.JSeparator();
        scrollPane20 = new widget.ScrollPane();
        PemeriksaanLaborat = new widget.TextArea();
        jLabel116 = new widget.Label();
        jSeparator8 = new javax.swing.JSeparator();
        scrollPane21 = new widget.ScrollPane();
        Transfusi = new widget.TextArea();
        jLabel117 = new widget.Label();
        jSeparator9 = new javax.swing.JSeparator();
        scrollPane22 = new widget.ScrollPane();
        Lainlain = new widget.TextArea();
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
        MnPenilaianMedis.setText("Laporan Catatan Pengkajian Paska Operasi");
        MnPenilaianMedis.setName("MnPenilaianMedis"); // NOI18N
        MnPenilaianMedis.setPreferredSize(new java.awt.Dimension(260, 26));
        MnPenilaianMedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPenilaianMedisActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnPenilaianMedis);

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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Catatan Pengkajian Paska Operasi ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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

        internalFrame2.setBorder(null);
        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setPreferredSize(new java.awt.Dimension(102, 480));
        internalFrame2.setLayout(new java.awt.BorderLayout(1, 1));

        scrollInput.setName("scrollInput"); // NOI18N
        scrollInput.setPreferredSize(new java.awt.Dimension(102, 557));

        FormInput.setBackground(new java.awt.Color(255, 255, 255));
        FormInput.setBorder(null);
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(750, 703));
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

        label14.setText("Dokter :");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label14);
        label14.setBounds(166, 40, 50, 23);

        KdDokter.setEditable(false);
        KdDokter.setName("KdDokter"); // NOI18N
        KdDokter.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput.add(KdDokter);
        KdDokter.setBounds(220, 40, 90, 23);

        NmDokter.setEditable(false);
        NmDokter.setName("NmDokter"); // NOI18N
        NmDokter.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmDokter);
        NmDokter.setBounds(312, 40, 180, 23);

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
        BtnDokter.setBounds(494, 40, 28, 23);

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
        TglAsuhan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "29-01-2026 17:53:42" }));
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

        jLabel109.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel109.setText("I. Rawat Paska Operasi/Tindakan :");
        jLabel109.setName("jLabel109"); // NOI18N
        FormInput.add(jLabel109);
        jLabel109.setBounds(10, 70, 190, 23);

        scrollPane14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane14.setName("scrollPane14"); // NOI18N
        scrollPane14.setPreferredSize(new java.awt.Dimension(184, 54));

        RawatPaskaOperasi.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        RawatPaskaOperasi.setColumns(20);
        RawatPaskaOperasi.setRows(5);
        RawatPaskaOperasi.setName("RawatPaskaOperasi"); // NOI18N
        RawatPaskaOperasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RawatPaskaOperasiKeyPressed(evt);
            }
        });
        scrollPane14.setViewportView(RawatPaskaOperasi);

        FormInput.add(scrollPane14);
        scrollPane14.setBounds(44, 90, 680, 43);

        jLabel110.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel110.setText("II. Cairan :");
        jLabel110.setName("jLabel110"); // NOI18N
        FormInput.add(jLabel110);
        jLabel110.setBounds(10, 140, 190, 23);

        jSeparator2.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator2.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator2.setName("jSeparator2"); // NOI18N
        FormInput.add(jSeparator2);
        jSeparator2.setBounds(0, 140, 750, 1);

        scrollPane15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane15.setName("scrollPane15"); // NOI18N
        scrollPane15.setPreferredSize(new java.awt.Dimension(184, 54));

        Cairan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Cairan.setColumns(20);
        Cairan.setRows(5);
        Cairan.setName("Cairan"); // NOI18N
        Cairan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CairanKeyPressed(evt);
            }
        });
        scrollPane15.setViewportView(Cairan);

        FormInput.add(scrollPane15);
        scrollPane15.setBounds(44, 160, 680, 43);

        jLabel111.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel111.setText("III. Antibiotika :");
        jLabel111.setName("jLabel111"); // NOI18N
        FormInput.add(jLabel111);
        jLabel111.setBounds(10, 210, 190, 23);

        jSeparator3.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator3.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator3.setName("jSeparator3"); // NOI18N
        FormInput.add(jSeparator3);
        jSeparator3.setBounds(0, 210, 750, 1);

        scrollPane16.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane16.setName("scrollPane16"); // NOI18N
        scrollPane16.setPreferredSize(new java.awt.Dimension(184, 54));

        Antibiotika.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Antibiotika.setColumns(20);
        Antibiotika.setRows(5);
        Antibiotika.setName("Antibiotika"); // NOI18N
        Antibiotika.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AntibiotikaKeyPressed(evt);
            }
        });
        scrollPane16.setViewportView(Antibiotika);

        FormInput.add(scrollPane16);
        scrollPane16.setBounds(44, 230, 680, 43);

        jLabel112.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel112.setText("IV. Analgetika :");
        jLabel112.setName("jLabel112"); // NOI18N
        FormInput.add(jLabel112);
        jLabel112.setBounds(10, 280, 190, 23);

        jSeparator4.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator4.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator4.setName("jSeparator4"); // NOI18N
        FormInput.add(jSeparator4);
        jSeparator4.setBounds(0, 280, 750, 1);

        scrollPane17.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane17.setName("scrollPane17"); // NOI18N
        scrollPane17.setPreferredSize(new java.awt.Dimension(184, 54));

        Analgetika.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Analgetika.setColumns(20);
        Analgetika.setRows(5);
        Analgetika.setName("Analgetika"); // NOI18N
        Analgetika.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AnalgetikaKeyPressed(evt);
            }
        });
        scrollPane17.setViewportView(Analgetika);

        FormInput.add(scrollPane17);
        scrollPane17.setBounds(44, 300, 680, 43);

        jLabel113.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel113.setText("V. Medikamentosa Lain :");
        jLabel113.setName("jLabel113"); // NOI18N
        FormInput.add(jLabel113);
        jLabel113.setBounds(10, 350, 190, 23);

        jSeparator5.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator5.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator5.setName("jSeparator5"); // NOI18N
        FormInput.add(jSeparator5);
        jSeparator5.setBounds(0, 350, 750, 1);

        scrollPane18.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane18.setName("scrollPane18"); // NOI18N
        scrollPane18.setPreferredSize(new java.awt.Dimension(184, 54));

        MedikamentosaLain.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        MedikamentosaLain.setColumns(20);
        MedikamentosaLain.setRows(5);
        MedikamentosaLain.setName("MedikamentosaLain"); // NOI18N
        MedikamentosaLain.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MedikamentosaLainKeyPressed(evt);
            }
        });
        scrollPane18.setViewportView(MedikamentosaLain);

        FormInput.add(scrollPane18);
        scrollPane18.setBounds(44, 370, 680, 43);

        jLabel114.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel114.setText("VI. Diet :");
        jLabel114.setName("jLabel114"); // NOI18N
        FormInput.add(jLabel114);
        jLabel114.setBounds(10, 420, 190, 23);

        jSeparator6.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator6.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator6.setName("jSeparator6"); // NOI18N
        FormInput.add(jSeparator6);
        jSeparator6.setBounds(0, 420, 750, 1);

        scrollPane19.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane19.setName("scrollPane19"); // NOI18N
        scrollPane19.setPreferredSize(new java.awt.Dimension(184, 54));

        Diet.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Diet.setColumns(20);
        Diet.setRows(5);
        Diet.setName("Diet"); // NOI18N
        Diet.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DietKeyPressed(evt);
            }
        });
        scrollPane19.setViewportView(Diet);

        FormInput.add(scrollPane19);
        scrollPane19.setBounds(44, 440, 680, 43);

        jLabel115.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel115.setText("VII. Pemeriksaan Laboratorium Paska Operasi/Tindakan Invasif :");
        jLabel115.setName("jLabel115"); // NOI18N
        FormInput.add(jLabel115);
        jLabel115.setBounds(10, 490, 390, 23);

        jSeparator7.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator7.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator7.setName("jSeparator7"); // NOI18N
        FormInput.add(jSeparator7);
        jSeparator7.setBounds(0, 490, 750, 1);

        scrollPane20.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane20.setName("scrollPane20"); // NOI18N
        scrollPane20.setPreferredSize(new java.awt.Dimension(184, 54));

        PemeriksaanLaborat.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        PemeriksaanLaborat.setColumns(20);
        PemeriksaanLaborat.setRows(5);
        PemeriksaanLaborat.setName("PemeriksaanLaborat"); // NOI18N
        PemeriksaanLaborat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PemeriksaanLaboratKeyPressed(evt);
            }
        });
        scrollPane20.setViewportView(PemeriksaanLaborat);

        FormInput.add(scrollPane20);
        scrollPane20.setBounds(44, 510, 680, 43);

        jLabel116.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel116.setText("VIII. Tranfusi :");
        jLabel116.setName("jLabel116"); // NOI18N
        FormInput.add(jLabel116);
        jLabel116.setBounds(10, 560, 390, 23);

        jSeparator8.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator8.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator8.setName("jSeparator8"); // NOI18N
        FormInput.add(jSeparator8);
        jSeparator8.setBounds(0, 560, 750, 1);

        scrollPane21.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane21.setName("scrollPane21"); // NOI18N
        scrollPane21.setPreferredSize(new java.awt.Dimension(184, 54));

        Transfusi.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Transfusi.setColumns(20);
        Transfusi.setRows(5);
        Transfusi.setName("Transfusi"); // NOI18N
        Transfusi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TransfusiKeyPressed(evt);
            }
        });
        scrollPane21.setViewportView(Transfusi);

        FormInput.add(scrollPane21);
        scrollPane21.setBounds(44, 580, 680, 43);

        jLabel117.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel117.setText("IX. Lain-lain :");
        jLabel117.setName("jLabel117"); // NOI18N
        FormInput.add(jLabel117);
        jLabel117.setBounds(10, 630, 390, 23);

        jSeparator9.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator9.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator9.setName("jSeparator9"); // NOI18N
        FormInput.add(jSeparator9);
        jSeparator9.setBounds(0, 630, 750, 1);

        scrollPane22.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane22.setName("scrollPane22"); // NOI18N
        scrollPane22.setPreferredSize(new java.awt.Dimension(184, 54));

        Lainlain.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Lainlain.setColumns(20);
        Lainlain.setRows(5);
        Lainlain.setName("Lainlain"); // NOI18N
        Lainlain.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LainlainKeyPressed(evt);
            }
        });
        scrollPane22.setViewportView(Lainlain);

        FormInput.add(scrollPane22);
        scrollPane22.setBounds(44, 650, 680, 43);

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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "29-01-2026" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "29-01-2026" }));
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
            Valid.pindah(evt,TCari,BtnDokter);
        }
}//GEN-LAST:event_TNoRwKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TNoRM.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"Nama Pasien");
        }else if(NmDokter.getText().trim().equals("")){
            Valid.textKosong(BtnDokter,"Dokter");
        }else if(RawatPaskaOperasi.getText().trim().equals("")){
            Valid.textKosong(RawatPaskaOperasi,"Rawat Paska Operasi/Tindakan");
        }else{
            if(akses.getkode().equals("Admin Utama")){
                simpan();
            }else{
                if(TanggalRegistrasi.getText().equals("")){
                    TanggalRegistrasi.setText(Sequel.cariIsi("select concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) from reg_periksa where reg_periksa.no_rawat=?",TNoRw.getText()));
                }
                if(Sequel.cekTanggalRegistrasi(TanggalRegistrasi.getText(),Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19))==true){
                    simpan();
                }
            }
        }
    
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,Lainlain,BtnBatal);
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
                if(KdDokter.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString())){
                    if(Sequel.cekTanggal48jam(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString(),Sequel.ambiltanggalsekarang())==true){
                        hapus();
                    }
                }else{
                    JOptionPane.showMessageDialog(null,"Hanya bisa dihapus oleh dokter yang bersangkutan..!!");
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
        }else if(NmDokter.getText().trim().equals("")){
            Valid.textKosong(BtnDokter,"Dokter");
        }else if(RawatPaskaOperasi.getText().trim().equals("")){
            Valid.textKosong(RawatPaskaOperasi,"Rawat Paska Operasi/Tindakan");
        }else{
            if(tbObat.getSelectedRow()>-1){
                if(akses.getkode().equals("Admin Utama")){
                    ganti();
                }else{
                    if(KdDokter.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString())){
                        if(Sequel.cekTanggal48jam(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString(),Sequel.ambiltanggalsekarang())==true){
                            if(TanggalRegistrasi.getText().equals("")){
                                TanggalRegistrasi.setText(Sequel.cariIsi("select concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) from reg_periksa where reg_periksa.no_rawat=?",TNoRw.getText()));
                            }
                            if(Sequel.cekTanggalRegistrasi(TanggalRegistrasi.getText(),Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19))==true){
                                ganti();
                            }
                        }
                    }else{
                        JOptionPane.showMessageDialog(null,"Hanya bisa diganti oleh dokter yang bersangkutan..!!");
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
                            htmlContent.append(                             
                                "<tr class='isi'>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Rawat</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.RM</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Pasien</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tgl.Lahir</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>J.K.</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kode Dokter</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Dokter</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tanggal</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>I. Rawat Paska Operasi/Tindakan</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>II. Cairan</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>III. Antibiotika</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>IV. Analgetika</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>V. Medikamentosa Lain</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>VI. Diet</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>VII. Pemeriksaan Laboratorium</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>VIII. Tranfusi</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>IX. Lain-lain</b></td>"+
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
                                    "</tr>");
                            }
                            LoadHTML.setText(
                                "<html>"+
                                  "<table width='1900px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
                                   htmlContent.toString()+
                                  "</table>"+
                                "</html>"
                            );

                            f = new File("DataCatatanPengkajianPaskaOperasi.html");            
                            bw = new BufferedWriter(new FileWriter(f));            
                            bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                                        "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                                        "<table width='1900px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                            "<tr class='isi2'>"+
                                                "<td valign='top' align='center'>"+
                                                    "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                                    akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                                    akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                                    "<font size='2' face='Tahoma'>DATA CATATAN PENGKAJIAN PASKA OPERASI<br><br></font>"+        
                                                "</td>"+
                                           "</tr>"+
                                        "</table>")
                            );
                            bw.close();                         
                            Desktop.getDesktop().browse(f.toURI());
                        break;
                    case "Laporan 2 (WPS)":
                            htmlContent = new StringBuilder();
                            htmlContent.append(                             
                                "<tr class='isi'>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Rawat</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.RM</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Pasien</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tgl.Lahir</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>J.K.</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kode Dokter</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Dokter</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tanggal</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>I. Rawat Paska Operasi/Tindakan</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>II. Cairan</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>III. Antibiotika</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>IV. Analgetika</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>V. Medikamentosa Lain</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>VI. Diet</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>VII. Pemeriksaan Laboratorium</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>VIII. Tranfusi</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>IX. Lain-lain</b></td>"+
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
                                    "</tr>");
                            }
                            LoadHTML.setText(
                                "<html>"+
                                  "<table width='1900px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
                                   htmlContent.toString()+
                                  "</table>"+
                                "</html>"
                            );

                            f = new File("DataCatatanPengkajianPaskaOperasi.wps");            
                            bw = new BufferedWriter(new FileWriter(f));            
                            bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                                        "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                                        "<table width='1900px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                            "<tr class='isi2'>"+
                                                "<td valign='top' align='center'>"+
                                                    "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                                    akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                                    akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                                    "<font size='2' face='Tahoma'>DATA CATATAN PENGKAJIAN PASKA OPERASI<br><br></font>"+        
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
                                "\"No.Rawat\";\"No.RM\";\"Nama Pasien\";\"Tgl.Lahir\";\"J.K.\";\"Kode Dokter\";\"Nama Dokter\";\"Tanggal\";\"I. Rawat Paska Operasi/Tindakan\";\"II. Cairan\";\"III. Antibiotika\";\"IV. Analgetika\";\"V. Medikamentosa Lain\";\"VI. Diet\";\"VII. Pemeriksaan Laboratorium\";\"VIII. Tranfusi\";\"IX. Lain-lain\"\n"
                            ); 
                            for (i = 0; i < tabMode.getRowCount(); i++) {
                                htmlContent.append(
                                    "\""+tbObat.getValueAt(i,0).toString()+"\";\""+tbObat.getValueAt(i,1).toString()+"\";\""+tbObat.getValueAt(i,2).toString()+"\";\""+tbObat.getValueAt(i,3).toString()+"\";\""+tbObat.getValueAt(i,4).toString()+"\";\""+tbObat.getValueAt(i,5).toString()+"\";\""+tbObat.getValueAt(i,6).toString()+"\";\""+tbObat.getValueAt(i,7).toString()+"\";\""+tbObat.getValueAt(i,8).toString()+"\";\""+tbObat.getValueAt(i,9).toString()+"\";\""+tbObat.getValueAt(i,10).toString()+"\";\""+tbObat.getValueAt(i,11).toString()+"\";\""+tbObat.getValueAt(i,12).toString()+"\";\""+tbObat.getValueAt(i,13).toString()+"\";\""+tbObat.getValueAt(i,14).toString()+"\";\""+tbObat.getValueAt(i,15).toString()+"\";\""+tbObat.getValueAt(i,16).toString()+"\"\n"
                                );
                            }
                            f = new File("DataCatatanPengkajianPaskaOperasi.csv");            
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

    private void BtnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokterActionPerformed
        if (dokter == null || !dokter.isDisplayable()) {
            dokter=new DlgCariDokter(null,false);
            dokter.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            dokter.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    if(dokter.getTable().getSelectedRow()!= -1){        
                         KdDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                         NmDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                    }  
                    BtnDokter.requestFocus();
                    dokter=null;
                }
            });
            dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            dokter.setLocationRelativeTo(internalFrame1);
        }   
        if (dokter == null) return;
        dokter.isCek();
        if (dokter.isVisible()) {
            dokter.toFront();
            return;
        }
        dokter.setVisible(true);
    }//GEN-LAST:event_BtnDokterActionPerformed

    private void BtnDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokterKeyPressed
        //Valid.pindah(evt,Edukasi,Hubungan);
    }//GEN-LAST:event_BtnDokterKeyPressed

    private void TglAsuhanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglAsuhanKeyPressed
        //Valid.pindah(evt,Edukasi,Anamnesis);
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
            String finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
            param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbObat.getValueAt(tbObat.getSelectedRow(),6).toString()+"\nID "+(finger.equals("")?tbObat.getValueAt(tbObat.getSelectedRow(),5).toString():finger)+"\n"+Valid.SetTgl3(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString())); 
            
            Valid.MyReportqry("rptCetakCatatanPengkajianPaskaOperasi.jasper","report","::[ Laporan Catatan Pengkajian Paska Operasi ]::",
                "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,catatan_pengkajian_paska_operasi.tanggal,"+
                "catatan_pengkajian_paska_operasi.kd_dokter,catatan_pengkajian_paska_operasi.rawat_paska_operasi,catatan_pengkajian_paska_operasi.cairan,catatan_pengkajian_paska_operasi.antibiotika,"+
                "catatan_pengkajian_paska_operasi.analgetika,catatan_pengkajian_paska_operasi.medikamentosa_lain,catatan_pengkajian_paska_operasi.diet,catatan_pengkajian_paska_operasi.pemeriksaan_laborat,"+
                "catatan_pengkajian_paska_operasi.tranfusi,catatan_pengkajian_paska_operasi.lainlain,dokter.nm_dokter from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join catatan_pengkajian_paska_operasi on reg_periksa.no_rawat=catatan_pengkajian_paska_operasi.no_rawat "+
                "inner join dokter on catatan_pengkajian_paska_operasi.kd_dokter=dokter.kd_dokter where catatan_pengkajian_paska_operasi.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"' "+
                "and catatan_pengkajian_paska_operasi.tanggal='"+tbObat.getValueAt(tbObat.getSelectedRow(),7).toString()+"'",param);
        }
    }//GEN-LAST:event_MnPenilaianMedisActionPerformed

    private void RawatPaskaOperasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RawatPaskaOperasiKeyPressed
        Valid.pindah2(evt,TglAsuhan,Cairan);
    }//GEN-LAST:event_RawatPaskaOperasiKeyPressed

    private void CairanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CairanKeyPressed
        Valid.pindah2(evt,RawatPaskaOperasi,Antibiotika);
    }//GEN-LAST:event_CairanKeyPressed

    private void AntibiotikaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AntibiotikaKeyPressed
        Valid.pindah2(evt,Cairan,Analgetika);
    }//GEN-LAST:event_AntibiotikaKeyPressed

    private void AnalgetikaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AnalgetikaKeyPressed
        Valid.pindah2(evt,Antibiotika,MedikamentosaLain);
    }//GEN-LAST:event_AnalgetikaKeyPressed

    private void MedikamentosaLainKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MedikamentosaLainKeyPressed
        Valid.pindah2(evt,Analgetika,Diet);
    }//GEN-LAST:event_MedikamentosaLainKeyPressed

    private void DietKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DietKeyPressed
        Valid.pindah2(evt,MedikamentosaLain,PemeriksaanLaborat);
    }//GEN-LAST:event_DietKeyPressed

    private void PemeriksaanLaboratKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PemeriksaanLaboratKeyPressed
        Valid.pindah2(evt,Diet,Transfusi);
    }//GEN-LAST:event_PemeriksaanLaboratKeyPressed

    private void TransfusiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TransfusiKeyPressed
        Valid.pindah2(evt,PemeriksaanLaborat,Lainlain);
    }//GEN-LAST:event_TransfusiKeyPressed

    private void LainlainKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LainlainKeyPressed
        Valid.pindah2(evt,Transfusi,BtnSimpan);
    }//GEN-LAST:event_LainlainKeyPressed

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
            RMCatatanPengkajianPaskaOperasi dialog = new RMCatatanPengkajianPaskaOperasi(new javax.swing.JFrame(), true);
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
    private widget.TextArea Analgetika;
    private widget.TextArea Antibiotika;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnDokter;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.TextArea Cairan;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.TextArea Diet;
    private widget.PanelBiasa FormInput;
    private widget.TextBox Jk;
    private widget.TextBox KdDokter;
    private widget.Label LCount;
    private widget.TextArea Lainlain;
    private widget.editorpane LoadHTML;
    private widget.TextArea MedikamentosaLain;
    private javax.swing.JMenuItem MnPenilaianMedis;
    private widget.TextBox NmDokter;
    private widget.TextArea PemeriksaanLaborat;
    private widget.TextArea RawatPaskaOperasi;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private javax.swing.JTabbedPane TabRawat;
    private widget.TextBox TanggalRegistrasi;
    private widget.Tanggal TglAsuhan;
    private widget.TextBox TglLahir;
    private widget.TextArea Transfusi;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.Label jLabel10;
    private widget.Label jLabel109;
    private widget.Label jLabel11;
    private widget.Label jLabel110;
    private widget.Label jLabel111;
    private widget.Label jLabel112;
    private widget.Label jLabel113;
    private widget.Label jLabel114;
    private widget.Label jLabel115;
    private widget.Label jLabel116;
    private widget.Label jLabel117;
    private widget.Label jLabel19;
    private widget.Label jLabel21;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private widget.Label label11;
    private widget.Label label14;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollInput;
    private widget.ScrollPane scrollPane14;
    private widget.ScrollPane scrollPane15;
    private widget.ScrollPane scrollPane16;
    private widget.ScrollPane scrollPane17;
    private widget.ScrollPane scrollPane18;
    private widget.ScrollPane scrollPane19;
    private widget.ScrollPane scrollPane20;
    private widget.ScrollPane scrollPane21;
    private widget.ScrollPane scrollPane22;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            if(TCari.getText().trim().equals("")){
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,catatan_pengkajian_paska_operasi.tanggal,"+
                        "catatan_pengkajian_paska_operasi.kd_dokter,catatan_pengkajian_paska_operasi.rawat_paska_operasi,catatan_pengkajian_paska_operasi.cairan,catatan_pengkajian_paska_operasi.antibiotika,"+
                        "catatan_pengkajian_paska_operasi.analgetika,catatan_pengkajian_paska_operasi.medikamentosa_lain,catatan_pengkajian_paska_operasi.diet,catatan_pengkajian_paska_operasi.pemeriksaan_laborat,"+
                        "catatan_pengkajian_paska_operasi.tranfusi,catatan_pengkajian_paska_operasi.lainlain,dokter.nm_dokter from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join catatan_pengkajian_paska_operasi on reg_periksa.no_rawat=catatan_pengkajian_paska_operasi.no_rawat "+
                        "inner join dokter on catatan_pengkajian_paska_operasi.kd_dokter=dokter.kd_dokter where "+
                        "catatan_pengkajian_paska_operasi.tanggal between ? and ? order by catatan_pengkajian_paska_operasi.tanggal");
            }else{
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,catatan_pengkajian_paska_operasi.tanggal,"+
                        "catatan_pengkajian_paska_operasi.kd_dokter,catatan_pengkajian_paska_operasi.rawat_paska_operasi,catatan_pengkajian_paska_operasi.cairan,catatan_pengkajian_paska_operasi.antibiotika,"+
                        "catatan_pengkajian_paska_operasi.analgetika,catatan_pengkajian_paska_operasi.medikamentosa_lain,catatan_pengkajian_paska_operasi.diet,catatan_pengkajian_paska_operasi.pemeriksaan_laborat,"+
                        "catatan_pengkajian_paska_operasi.tranfusi,catatan_pengkajian_paska_operasi.lainlain,dokter.nm_dokter from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join catatan_pengkajian_paska_operasi on reg_periksa.no_rawat=catatan_pengkajian_paska_operasi.no_rawat "+
                        "inner join dokter on catatan_pengkajian_paska_operasi.kd_dokter=dokter.kd_dokter where "+
                        "catatan_pengkajian_paska_operasi.tanggal between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or "+
                        "catatan_pengkajian_paska_operasi.kd_dokter like ? or dokter.nm_dokter like ?) order by catatan_pengkajian_paska_operasi.tanggal");
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
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getDate("tgl_lahir"),rs.getString("jk"),rs.getString("kd_dokter"),rs.getString("nm_dokter"),rs.getString("tanggal"),
                        rs.getString("rawat_paska_operasi"),rs.getString("cairan"),rs.getString("antibiotika"),rs.getString("analgetika"),rs.getString("medikamentosa_lain"),rs.getString("diet"),
                        rs.getString("pemeriksaan_laborat"),rs.getString("tranfusi"),rs.getString("lainlain")
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
        RawatPaskaOperasi.setText("");
        Cairan.setText("");
        Antibiotika.setText("");
        Analgetika.setText("");
        MedikamentosaLain.setText("");
        Diet.setText("");
        PemeriksaanLaborat.setText("");
        Transfusi.setText("");
        Lainlain.setText("");
        TglAsuhan.setDate(new Date());
        TabRawat.setSelectedIndex(0);
        RawatPaskaOperasi.requestFocus();
    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()); 
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            Jk.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString()); 
            RawatPaskaOperasi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            Cairan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            Antibiotika.setText(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            Analgetika.setText(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            MedikamentosaLain.setText(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            Diet.setText(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            PemeriksaanLaborat.setText(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            Transfusi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
            Lainlain.setText(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            Valid.SetTgl2(TglAsuhan,tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());
        }
    }

    private void isRawat() {
        try {
            ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rkm_medis,pasien.nm_pasien, if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,reg_periksa.tgl_registrasi,reg_periksa.jam_reg "+
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
        runBackground(() ->tampil());
    }
    
    public void setNoRm(String norwt,Date tgl2,String KodeDokter,String NamaDokter,String Operasi) {
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        DTPCari2.setDate(tgl2);    
        isRawat(); 
        KdDokter.setText(KodeDokter);
        NmDokter.setText(NamaDokter);
        MedikamentosaLain.setText(Operasi);
    }
    
    public void isCek(){
        BtnSimpan.setEnabled(akses.getcatatan_pengkajian_paska_operasi());
        BtnHapus.setEnabled(akses.getcatatan_pengkajian_paska_operasi());
        BtnEdit.setEnabled(akses.getcatatan_pengkajian_paska_operasi());
        BtnEdit.setEnabled(akses.getcatatan_pengkajian_paska_operasi());
        if(akses.getjml2()>=1){
            KdDokter.setEditable(false);
            BtnDokter.setEnabled(false);
            KdDokter.setText(akses.getkode());
            NmDokter.setText(Sequel.CariDokter(KdDokter.getText()));
            if(NmDokter.getText().equals("")){
                KdDokter.setText("");
                JOptionPane.showMessageDialog(null,"User login bukan Dokter...!!");
            }
        }  
        
        if(TANGGALMUNDUR.equals("no")){
            if(!akses.getkode().equals("Admin Utama")){
                TglAsuhan.setEditable(false);
                TglAsuhan.setEnabled(false);
            }
        }
    }
    
    public void setTampil(){
       TabRawat.setSelectedIndex(1);
    }

    private void hapus() {
        if(Sequel.queryu2tf("delete from catatan_pengkajian_paska_operasi where no_rawat=? and tanggal=?",2,new String[]{
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
        if(Sequel.mengedittf("catatan_pengkajian_paska_operasi","no_rawat=? and tanggal=?","no_rawat=?,tanggal=?,kd_dokter=?,rawat_paska_operasi=?,cairan=?,antibiotika=?,analgetika=?,medikamentosa_lain=?,diet=?,pemeriksaan_laborat=?,tranfusi=?,lainlain=?",14,new String[]{
                TNoRw.getText(),Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),KdDokter.getText(),RawatPaskaOperasi.getText(),Cairan.getText(),Antibiotika.getText(),Analgetika.getText(),MedikamentosaLain.getText(),
                Diet.getText(),PemeriksaanLaborat.getText(),Transfusi.getText(),Lainlain.getText(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString(),tbObat.getValueAt(tbObat.getSelectedRow(),7).toString()
            })==true){
                tbObat.setValueAt(TNoRw.getText(),tbObat.getSelectedRow(),0);
                tbObat.setValueAt(TNoRM.getText(),tbObat.getSelectedRow(),1);
                tbObat.setValueAt(TPasien.getText(),tbObat.getSelectedRow(),2);
                tbObat.setValueAt(TglLahir.getText(),tbObat.getSelectedRow(),3);
                tbObat.setValueAt(Jk.getText(),tbObat.getSelectedRow(),4);
                tbObat.setValueAt(KdDokter.getText(),tbObat.getSelectedRow(),5);
                tbObat.setValueAt(NmDokter.getText(),tbObat.getSelectedRow(),6);
                tbObat.setValueAt(Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),tbObat.getSelectedRow(),7);
                tbObat.setValueAt(RawatPaskaOperasi.getText(),tbObat.getSelectedRow(),8);
                tbObat.setValueAt(Cairan.getText(),tbObat.getSelectedRow(),9);
                tbObat.setValueAt(Antibiotika.getText(),tbObat.getSelectedRow(),10);
                tbObat.setValueAt(Analgetika.getText(),tbObat.getSelectedRow(),11);
                tbObat.setValueAt(MedikamentosaLain.getText(),tbObat.getSelectedRow(),12);
                tbObat.setValueAt(Diet.getText(),tbObat.getSelectedRow(),13);
                tbObat.setValueAt(PemeriksaanLaborat.getText(),tbObat.getSelectedRow(),14);
                tbObat.setValueAt(Transfusi.getText(),tbObat.getSelectedRow(),15);
                tbObat.setValueAt(Lainlain.getText(),tbObat.getSelectedRow(),16);
               emptTeks();
               TabRawat.setSelectedIndex(1);
        }
    }

    private void simpan() {
        if(Sequel.menyimpantf("catatan_pengkajian_paska_operasi","?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat",12,new String[]{
                TNoRw.getText(),Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),KdDokter.getText(),
                RawatPaskaOperasi.getText(),Cairan.getText(),Antibiotika.getText(),Analgetika.getText(),MedikamentosaLain.getText(),Diet.getText(),
                PemeriksaanLaborat.getText(),Transfusi.getText(),Lainlain.getText()
            })==true){
                tabMode.addRow(new Object[]{
                    TNoRw.getText(),TNoRM.getText(),TPasien.getText(),TglLahir.getText(),Jk.getText(),KdDokter.getText(),NmDokter.getText(),Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),
                    RawatPaskaOperasi.getText(),Cairan.getText(),Antibiotika.getText(),Analgetika.getText(),MedikamentosaLain.getText(),Diet.getText(),PemeriksaanLaborat.getText(),Transfusi.getText(),Lainlain.getText()
                });
                emptTeks();
                LCount.setText(""+tabMode.getRowCount());
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

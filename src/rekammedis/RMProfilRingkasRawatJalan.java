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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
public final class RMProfilRingkasRawatJalan extends javax.swing.JDialog {
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
    private String finger="";
    private StringBuilder htmlContent;
    private String TANGGALMUNDUR="yes",pilihan="";
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public RMProfilRingkasRawatJalan(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(628,674);

        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.RM","Nama Pasien","J.K.","Tgl.Lahir","Kode Dokter","Nama Dokter DPJP","Tanggal",
            "Alergi","Diagnosis Utama","Diagnosis Sekunder","Riwayat Penyakit","Anamnesis","Pemeriksaan Fisik",
            "Pemeriksaan Penunjang","Terapi/Obat","Tindakan/Prosedur","Rencana Tindak Lanjut","Keterangan"
        }){
            @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 19; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(105);
            }else if(i==1){
                column.setPreferredWidth(70);
            }else if(i==2){
                column.setPreferredWidth(150);
            }else if(i==3){
                column.setPreferredWidth(30);
            }else if(i==4){
                column.setPreferredWidth(65);
            }else if(i==5){
                column.setPreferredWidth(80);
            }else if(i==6){
                column.setPreferredWidth(150);
            }else if(i==7){
                column.setPreferredWidth(115);
            }else if(i==8){
                column.setPreferredWidth(150);
            }else if(i==9){
                column.setPreferredWidth(170);
            }else if(i==10){
                column.setPreferredWidth(170);
            }else if(i==11){
                column.setPreferredWidth(220);
            }else if(i==12){
                column.setPreferredWidth(220);
            }else if(i==13){
                column.setPreferredWidth(220);
            }else if(i==14){
                column.setPreferredWidth(220);
            }else if(i==15){
                column.setPreferredWidth(220);
            }else if(i==16){
                column.setPreferredWidth(150);
            }else if(i==17){
                column.setPreferredWidth(130);
            }else if(i==18){
                column.setPreferredWidth(240);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());

        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        KdDokter.setDocument(new batasInput((byte)20).getKata(KdDokter));
        TCari.setDocument(new batasInput((int)100).getKata(TCari));
        Alergi.setDocument(new batasInput((int)255).getKata(Alergi));
        DiagnosisUtama.setDocument(new batasInput((int)255).getKata(DiagnosisUtama));
        DiagnosisSekunder.setDocument(new batasInput((int)255).getKata(DiagnosisSekunder));
        RiwayatPenyakit.setDocument(new batasInput((int)500).getKata(RiwayatPenyakit));
        Anamnesis.setDocument(new batasInput((int)500).getKata(Anamnesis));
        PemeriksaanFisik.setDocument(new batasInput((int)500).getKata(PemeriksaanFisik));
        PemeriksaanPenunjang.setDocument(new batasInput((int)500).getKata(PemeriksaanPenunjang));
        Terapi.setDocument(new batasInput((int)500).getKata(Terapi));
        Tindakan.setDocument(new batasInput((int)255).getKata(Tindakan));
        Keterangan.setDocument(new batasInput((int)255).getKata(Keterangan));
        
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

        jPopupMenu1 = new widget.PopupMenu();
        MnFormulir = new widget.MenuItem();
        LoadHTML = new widget.editorpane();
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
        jLabelSd = new widget.Label();
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
        TNoRM = new widget.TextBox();
        TPasien = new widget.TextBox();
        jLabel8 = new widget.Label();
        JK = new widget.TextBox();
        jLabel9 = new widget.Label();
        TglLahir = new widget.TextBox();
        Tanggal = new widget.Tanggal();
        jLabel23 = new widget.Label();
        KdDokter = new widget.TextBox();
        NmDokter = new widget.TextBox();
        BtnDokter = new widget.Button();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel_Alergi = new widget.Label();
        Alergi = new widget.TextBox();
        jLabel_DiagnosisUtama = new widget.Label();
        DiagnosisUtama = new widget.TextBox();
        jLabel_DiagnosisSekunder = new widget.Label();
        DiagnosisSekunder = new widget.TextBox();
        jLabel_RiwayatPenyakit = new widget.Label();
        scrollRiwayatPenyakit = new widget.ScrollPane();
        RiwayatPenyakit = new widget.TextArea();
        jLabel_Anamnesis = new widget.Label();
        scrollAnamnesis = new widget.ScrollPane();
        Anamnesis = new widget.TextArea();
        jLabel_PemeriksaanFisik = new widget.Label();
        scrollPemeriksaanFisik = new widget.ScrollPane();
        PemeriksaanFisik = new widget.TextArea();
        jLabel_PemeriksaanPenunjang = new widget.Label();
        scrollPemeriksaanPenunjang = new widget.ScrollPane();
        PemeriksaanPenunjang = new widget.TextArea();
        jLabel_Terapi = new widget.Label();
        scrollTerapi = new widget.ScrollPane();
        Terapi = new widget.TextArea();
        jLabel_Tindakan = new widget.Label();
        Tindakan = new widget.TextBox();
        jLabel_TindakLanjut = new widget.Label();
        TindakLanjut = new widget.ComboBox();
        jLabel_Keterangan = new widget.Label();
        Keterangan = new widget.TextBox();
        jLabel39 = new widget.Label();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnFormulir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnFormulir.setText("Formulir Profil Ringkas Rekam Medis Rawat Jalan");
        MnFormulir.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnFormulir.setName("MnFormulir"); // NOI18N
        MnFormulir.setPreferredSize(new java.awt.Dimension(290, 26));
        MnFormulir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnFormulirActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnFormulir);

        LoadHTML.setName("LoadHTML"); // NOI18N
        LoadHTML.setBorder(null);

        TanggalRegistrasi.setName("TanggalRegistrasi"); // NOI18N
        TanggalRegistrasi.setHighlighter(null);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Profil Ringkas Rekam Medis Rawat Jalan ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setPreferredSize(new java.awt.Dimension(462, 1300));
        internalFrame1.setRequestFocusEnabled(false);
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);
        Scroll.setPreferredSize(new java.awt.Dimension(452, 200));

        tbObat.setName("tbObat"); // NOI18N
        tbObat.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbObat.setComponentPopupMenu(jPopupMenu1);
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

        panelGlass8.setBackground(new java.awt.Color(255, 255, 255));
        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        BtnSimpan.setName("BtnSimpan"); // NOI18N
        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan.setMnemonic('S');
        BtnSimpan.setText("Simpan");
        BtnSimpan.setToolTipText("Alt+S");
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

        BtnBatal.setName("BtnBatal"); // NOI18N
        BtnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Cancel-2-16x16.png"))); // NOI18N
        BtnBatal.setMnemonic('B');
        BtnBatal.setText("Baru");
        BtnBatal.setToolTipText("Alt+B");
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

        BtnHapus.setName("BtnHapus"); // NOI18N
        BtnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); // NOI18N
        BtnHapus.setMnemonic('H');
        BtnHapus.setText("Hapus");
        BtnHapus.setToolTipText("Alt+H");
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

        BtnEdit.setName("BtnEdit"); // NOI18N
        BtnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/inventaris.png"))); // NOI18N
        BtnEdit.setMnemonic('G');
        BtnEdit.setText("Ganti");
        BtnEdit.setToolTipText("Alt+G");
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

        BtnPrint.setName("BtnPrint"); // NOI18N
        BtnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png"))); // NOI18N
        BtnPrint.setMnemonic('T');
        BtnPrint.setText("Cetak");
        BtnPrint.setToolTipText("Alt+T");
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

        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setText("Record :");
        jLabel7.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass8.add(jLabel7);

        LCount.setName("LCount"); // NOI18N
        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "13-11-2026" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass9.add(DTPCari1);

        jLabelSd.setName("jLabelSd"); // NOI18N
        jLabelSd.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelSd.setText("s.d.");
        jLabelSd.setPreferredSize(new java.awt.Dimension(23, 23));
        panelGlass9.add(jLabelSd);

        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "13-11-2026" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass9.add(DTPCari2);

        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setText("Key Word :");
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

        BtnCari.setName("BtnCari"); // NOI18N
        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('3');
        BtnCari.setToolTipText("Alt+3");
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

        BtnAll.setName("BtnAll"); // NOI18N
        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('M');
        BtnAll.setToolTipText("Alt+M");
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
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 506));
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

        FormInput.setBackground(new java.awt.Color(255, 255, 255));
        FormInput.setBorder(null);
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(100, 483));
        FormInput.setLayout(null);

        jLabel4.setText("No.Rawat :");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(0, 10, 75, 23);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(79, 10, 130, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        FormInput.add(TNoRM);
        TNoRM.setBounds(211, 10, 90, 23);

        TPasien.setName("TPasien"); // NOI18N
        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPasienKeyPressed(evt);
            }
        });
        FormInput.add(TPasien);
        TPasien.setBounds(303, 10, 281, 23);

        jLabel8.setName("jLabel8"); // NOI18N
        jLabel8.setText("J.K. :");
        FormInput.add(jLabel8);
        jLabel8.setBounds(580, 10, 40, 23);

        JK.setName("JK"); // NOI18N
        JK.setEditable(false);
        JK.setHighlighter(null);
        FormInput.add(JK);
        JK.setBounds(624, 10, 30, 23);

        jLabel9.setName("jLabel9"); // NOI18N
        jLabel9.setText("Lahir :");
        FormInput.add(jLabel9);
        jLabel9.setBounds(656, 10, 45, 23);

        TglLahir.setEditable(false);
        TglLahir.setHighlighter(null);
        TglLahir.setName("TglLahir"); // NOI18N
        FormInput.add(TglLahir);
        TglLahir.setBounds(705, 10, 84, 23);

        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.setForeground(new java.awt.Color(50, 70, 50));
        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "13-11-2026 08:26:24" }));
        Tanggal.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeyPressed(evt);
            }
        });
        FormInput.add(Tanggal);
        Tanggal.setBounds(79, 40, 140, 23);

        jLabel23.setText("Dokter DPJP :");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(232, 40, 100, 23);

        KdDokter.setName("KdDokter"); // NOI18N
        KdDokter.setEditable(false);
        KdDokter.setHighlighter(null);
        FormInput.add(KdDokter);
        KdDokter.setBounds(336, 40, 110, 23);

        NmDokter.setName("NmDokter"); // NOI18N
        NmDokter.setEditable(false);
        NmDokter.setHighlighter(null);
        FormInput.add(NmDokter);
        NmDokter.setBounds(448, 40, 311, 23);

        BtnDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter.setMnemonic('2');
        BtnDokter.setToolTipText("Alt+2");
        BtnDokter.setName("BtnDokter"); // NOI18N
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
        BtnDokter.setBounds(761, 40, 28, 23);

        jSeparator1.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator1.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator1.setName("jSeparator1"); // NOI18N
        FormInput.add(jSeparator1);
        jSeparator1.setBounds(0, 70, 807, 1);

        jLabel_Alergi.setText("Alergi :");
        jLabel_Alergi.setName("jLabel_Alergi"); // NOI18N
        FormInput.add(jLabel_Alergi);
        jLabel_Alergi.setBounds(0, 80, 140, 23);

        Alergi.setHighlighter(null);
        Alergi.setName("Alergi"); // NOI18N
        Alergi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlergiKeyPressed(evt);
            }
        });
        FormInput.add(Alergi);
        Alergi.setBounds(144, 80, 645, 23);

        jLabel_DiagnosisUtama.setText("Diagnosis Utama :");
        jLabel_DiagnosisUtama.setName("jLabel_DiagnosisUtama"); // NOI18N
        FormInput.add(jLabel_DiagnosisUtama);
        jLabel_DiagnosisUtama.setBounds(0, 110, 140, 23);

        DiagnosisUtama.setHighlighter(null);
        DiagnosisUtama.setName("DiagnosisUtama"); // NOI18N
        DiagnosisUtama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosisUtamaKeyPressed(evt);
            }
        });
        FormInput.add(DiagnosisUtama);
        DiagnosisUtama.setBounds(144, 110, 645, 23);

        jLabel_DiagnosisSekunder.setText("Diagnosis Sekunder :");
        jLabel_DiagnosisSekunder.setName("jLabel_DiagnosisSekunder"); // NOI18N
        FormInput.add(jLabel_DiagnosisSekunder);
        jLabel_DiagnosisSekunder.setBounds(0, 140, 140, 23);

        DiagnosisSekunder.setHighlighter(null);
        DiagnosisSekunder.setName("DiagnosisSekunder"); // NOI18N
        DiagnosisSekunder.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosisSekunderKeyPressed(evt);
            }
        });
        FormInput.add(DiagnosisSekunder);
        DiagnosisSekunder.setBounds(144, 140, 645, 23);

        jLabel_RiwayatPenyakit.setText("Riwayat Penyakit :");
        jLabel_RiwayatPenyakit.setName("jLabel_RiwayatPenyakit"); // NOI18N
        FormInput.add(jLabel_RiwayatPenyakit);
        jLabel_RiwayatPenyakit.setBounds(0, 170, 140, 23);

        scrollRiwayatPenyakit.setName("scrollRiwayatPenyakit"); // NOI18N

        RiwayatPenyakit.setColumns(20);
        RiwayatPenyakit.setRows(5);
        RiwayatPenyakit.setName("RiwayatPenyakit"); // NOI18N
        RiwayatPenyakit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RiwayatPenyakitKeyPressed(evt);
            }
        });
        scrollRiwayatPenyakit.setViewportView(RiwayatPenyakit);

        FormInput.add(scrollRiwayatPenyakit);
        scrollRiwayatPenyakit.setBounds(144, 170, 645, 43);

        jLabel_Anamnesis.setText("Anamnesis :");
        jLabel_Anamnesis.setName("jLabel_Anamnesis"); // NOI18N
        FormInput.add(jLabel_Anamnesis);
        jLabel_Anamnesis.setBounds(0, 220, 140, 23);

        scrollAnamnesis.setName("scrollAnamnesis"); // NOI18N

        Anamnesis.setColumns(20);
        Anamnesis.setRows(5);
        Anamnesis.setName("Anamnesis"); // NOI18N
        Anamnesis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AnamnesisKeyPressed(evt);
            }
        });
        scrollAnamnesis.setViewportView(Anamnesis);

        FormInput.add(scrollAnamnesis);
        scrollAnamnesis.setBounds(144, 220, 645, 43);

        jLabel_PemeriksaanFisik.setText("Pemeriksaan Fisik :");
        jLabel_PemeriksaanFisik.setName("jLabel_PemeriksaanFisik"); // NOI18N
        FormInput.add(jLabel_PemeriksaanFisik);
        jLabel_PemeriksaanFisik.setBounds(0, 270, 140, 23);

        scrollPemeriksaanFisik.setName("scrollPemeriksaanFisik"); // NOI18N

        PemeriksaanFisik.setColumns(20);
        PemeriksaanFisik.setRows(5);
        PemeriksaanFisik.setName("PemeriksaanFisik"); // NOI18N
        PemeriksaanFisik.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PemeriksaanFisikKeyPressed(evt);
            }
        });
        scrollPemeriksaanFisik.setViewportView(PemeriksaanFisik);

        FormInput.add(scrollPemeriksaanFisik);
        scrollPemeriksaanFisik.setBounds(144, 270, 645, 43);

        jLabel_PemeriksaanPenunjang.setText("Pemeriksaan Penunjang :");
        jLabel_PemeriksaanPenunjang.setName("jLabel_PemeriksaanPenunjang"); // NOI18N
        FormInput.add(jLabel_PemeriksaanPenunjang);
        jLabel_PemeriksaanPenunjang.setBounds(0, 320, 140, 23);

        scrollPemeriksaanPenunjang.setName("scrollPemeriksaanPenunjang"); // NOI18N

        PemeriksaanPenunjang.setColumns(20);
        PemeriksaanPenunjang.setRows(5);
        PemeriksaanPenunjang.setName("PemeriksaanPenunjang"); // NOI18N
        PemeriksaanPenunjang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PemeriksaanPenunjangKeyPressed(evt);
            }
        });
        scrollPemeriksaanPenunjang.setViewportView(PemeriksaanPenunjang);

        FormInput.add(scrollPemeriksaanPenunjang);
        scrollPemeriksaanPenunjang.setBounds(144, 320, 645, 43);

        jLabel_Terapi.setText("Terapi / Obat :");
        jLabel_Terapi.setName("jLabel_Terapi"); // NOI18N
        FormInput.add(jLabel_Terapi);
        jLabel_Terapi.setBounds(0, 370, 140, 23);

        scrollTerapi.setName("scrollTerapi"); // NOI18N

        Terapi.setColumns(20);
        Terapi.setRows(5);
        Terapi.setName("Terapi"); // NOI18N
        Terapi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TerapiKeyPressed(evt);
            }
        });
        scrollTerapi.setViewportView(Terapi);

        FormInput.add(scrollTerapi);
        scrollTerapi.setBounds(144, 370, 645, 43);

        jLabel_Tindakan.setText("Tindakan / Prosedur :");
        jLabel_Tindakan.setName("jLabel_Tindakan"); // NOI18N
        FormInput.add(jLabel_Tindakan);
        jLabel_Tindakan.setBounds(0, 420, 140, 23);

        Tindakan.setHighlighter(null);
        Tindakan.setName("Tindakan"); // NOI18N
        Tindakan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TindakanKeyPressed(evt);
            }
        });
        FormInput.add(Tindakan);
        Tindakan.setBounds(144, 420, 645, 23);

        jLabel_TindakLanjut.setText("Rencana Tindak Lanjut :");
        jLabel_TindakLanjut.setName("jLabel_TindakLanjut"); // NOI18N
        FormInput.add(jLabel_TindakLanjut);
        jLabel_TindakLanjut.setBounds(0, 450, 140, 23);

        TindakLanjut.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Kontrol Ulang", "Dirujuk", "Rawat Inap", "Selesai" }));
        TindakLanjut.setName("TindakLanjut"); // NOI18N
        TindakLanjut.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TindakLanjutKeyPressed(evt);
            }
        });
        FormInput.add(TindakLanjut);
        TindakLanjut.setBounds(144, 450, 110, 23);

        jLabel_Keterangan.setText("Keterangan :");
        jLabel_Keterangan.setName("jLabel_Keterangan"); // NOI18N
        FormInput.add(jLabel_Keterangan);
        jLabel_Keterangan.setBounds(260, 450, 80, 23);

        Keterangan.setHighlighter(null);
        Keterangan.setName("Keterangan"); // NOI18N
        Keterangan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKeyPressed(evt);
            }
        });
        FormInput.add(Keterangan);
        Keterangan.setBounds(344, 450, 445, 23);

        jLabel39.setText("Tanggal :");
        jLabel39.setName("jLabel39"); // NOI18N
        FormInput.add(jLabel39);
        jLabel39.setBounds(0, 40, 75, 23);

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

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"Pasien");
        }else if(KdDokter.getText().trim().equals("")||NmDokter.getText().trim().equals("")){
            Valid.textKosong(BtnDokter,"Dokter");
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
           Valid.pindah(evt,Keterangan,BtnBatal);
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
            }else{
                if(akses.getkode().equals(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString())){
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
        if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"Pasien");
        }else if(KdDokter.getText().trim().equals("")||NmDokter.getText().trim().equals("")){
            Valid.textKosong(BtnDokter,"Dokter");
        }else{
            if(tbObat.getSelectedRow()>-1){
                if(akses.getkode().equals("Admin Utama")){
                    ganti();
                }else{
                    if(akses.getkode().equals(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString())){
                        if(Sequel.cekTanggal48jam(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString(),Sequel.ambiltanggalsekarang())==true){
                            if(TanggalRegistrasi.getText().equals("")){
                                TanggalRegistrasi.setText(Sequel.cariIsi("select concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) from reg_periksa where reg_periksa.no_rawat=?",TNoRw.getText()));
                            }
                            if(Sequel.cekTanggalRegistrasi(TanggalRegistrasi.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19))==true){
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
                
                pilihan =(String) JOptionPane.showInputDialog(null,"Silahkan pilih laporan..!","Pilihan Cetak",JOptionPane.QUESTION_MESSAGE,null,new Object[]{"Laporan 1 (HTML)","Laporan 2 (WPS)","Laporan 3 (CSV)"},"Laporan 1 (HTML)");
                switch (pilihan) {
                    case "Laporan 1 (HTML)":
                            htmlContent = new StringBuilder();
                            htmlContent.append(                             
                                "<tr class='isi'>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Rawat</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.RM</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Pasien</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>J.K.</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tgl.Lahir</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kode Dokter</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>DPJP</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tgl/Jam Profil</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Alergi</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Diagnosis Utama</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Diagnosis Sekunder</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Riwayat Penyakit</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Anamnesis</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Pemeriksaan Fisik</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Pemeriksaan Penunjang</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Terapi / Obat</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tindakan / Prosedur</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Rencana Tindak Lanjut</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Dokter</b></td>"+
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
                                    "</tr>");
                            }
                            LoadHTML.setText(
                                "<html>"+
                                  "<table width='2875px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
                                   htmlContent.toString()+
                                  "</table>"+
                                "</html>"
                            );

                            f = new File("DataProfilRingkasRawatJalan.wps");            
                            bw = new BufferedWriter(new FileWriter(f));            
                            bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                                        "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                                        "<table width='2875px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                            "<tr class='isi2'>"+
                                                "<td valign='top' align='center'>"+
                                                    "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                                    akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                                    akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                                    "<font size='2' face='Tahoma'>DATA PROFIL RINGKAS REKAM MEDIS RAWAT JALAN<br><br></font>"+        
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
                                "\"No.Rawat\";\"No.RM\";\"Nama Pasien\";\"J.K.\";\"Tgl.Lahir\";\"Kode Dokter\";\"DPJP\";\"Tgl/Jam Profil\";\"Alergi\";\"Diagnosis Utama\";\"Diagnosis Sekunder\";\"Riwayat Penyakit\";\"Anamnesis\";\"Pemeriksaan Fisik\";\"Pemeriksaan Penunjang\";\"Terapi / Obat\";\"Tindakan / Prosedur\";\"Rencana Tindak Lanjut\";\"Keterangan Dokter\"\n"
                            ); 
                            for (i = 0; i < tabMode.getRowCount(); i++) {
                                htmlContent.append(
                                    "\""+tbObat.getValueAt(i,0).toString()+"\";\""+tbObat.getValueAt(i,1).toString()+"\";\""+tbObat.getValueAt(i,2).toString()+"\";\""+tbObat.getValueAt(i,3).toString()+"\";\""+tbObat.getValueAt(i,4).toString()+"\";\""+tbObat.getValueAt(i,5).toString()+"\";\""+tbObat.getValueAt(i,6).toString()+"\";\""+tbObat.getValueAt(i,7).toString()+"\";\""+tbObat.getValueAt(i,8).toString()+"\";\""+tbObat.getValueAt(i,9).toString()+"\";\""+tbObat.getValueAt(i,10).toString()+"\";\""+tbObat.getValueAt(i,11).toString()+"\";\""+tbObat.getValueAt(i,12).toString()+"\";\""+tbObat.getValueAt(i,13).toString()+"\";\""+tbObat.getValueAt(i,14).toString()+"\";\""+tbObat.getValueAt(i,15).toString()+"\";\""+tbObat.getValueAt(i,16).toString()+"\";\""+tbObat.getValueAt(i,17).toString()+"\";\""+tbObat.getValueAt(i,18).toString()+"\"\n"
                                );
                            }
                            f = new File("DataProfilRingkasRawatJalan.csv");            
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
            runBackground(() ->tampil());
            TCari.setText("");
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
        if (!dokter.isVisible()) {
            dokter.isCek();    
            dokter.emptTeks();
        }
        
        if (dokter.isVisible()) {
            dokter.toFront();
            return;
        }
        dokter.setVisible(true); 
    }//GEN-LAST:event_BtnDokterActionPerformed

    private void MnFormulirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnFormulirActionPerformed
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
            param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbObat.getValueAt(tbObat.getSelectedRow(),6).toString()+"\nID "+(finger.equals("")?tbObat.getValueAt(tbObat.getSelectedRow(),5).toString():finger)+"\n"+tbObat.getValueAt(tbObat.getSelectedRow(),7).toString().substring(0,10)); 
            Valid.MyReportqry(
                "rptFormulirProfilRingkasRawatJalan.jasper","report","::[ Formulir Profil Ringkas Rekam Medis Rawat Jalan ]::",
                "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,profil_ringkas_rawat_jalan.kd_dokter,dokter.nm_dokter,"+
                "profil_ringkas_rawat_jalan.tanggal,profil_ringkas_rawat_jalan.alergi,profil_ringkas_rawat_jalan.diagnosis_utama,profil_ringkas_rawat_jalan.diagnosis_sekunder,"+
                "profil_ringkas_rawat_jalan.riwayat_penyakit,profil_ringkas_rawat_jalan.anamnesis,profil_ringkas_rawat_jalan.pemeriksaan_fisik,profil_ringkas_rawat_jalan.pemeriksaan_penunjang,"+
                "profil_ringkas_rawat_jalan.terapi,profil_ringkas_rawat_jalan.tindakan,profil_ringkas_rawat_jalan.tindak_lanjut,profil_ringkas_rawat_jalan.keterangan "+
                "from profil_ringkas_rawat_jalan inner join reg_periksa on profil_ringkas_rawat_jalan.no_rawat=reg_periksa.no_rawat inner join pasien "+
                "on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join dokter on profil_ringkas_rawat_jalan.kd_dokter=dokter.kd_dokter "+
                "where profil_ringkas_rawat_jalan.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'",param
            );
        }else{
            JOptionPane.showMessageDialog(rootPane,"Silahkan anda pilih data terlebih dahulu..!!");
        }
    }//GEN-LAST:event_MnFormulirActionPerformed

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

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

    private void TPasienKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TPasienKeyPressed
        Valid.pindah(evt,TCari,BtnSimpan);
    }//GEN-LAST:event_TPasienKeyPressed

    private void TanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKeyPressed
        Valid.pindah(evt,TCari,BtnDokter);
    }//GEN-LAST:event_TanggalKeyPressed

    private void AlergiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlergiKeyPressed
        Valid.pindah(evt,BtnDokter,DiagnosisUtama);
    }//GEN-LAST:event_AlergiKeyPressed

    private void DiagnosisUtamaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosisUtamaKeyPressed
        Valid.pindah(evt,Alergi,DiagnosisSekunder);
    }//GEN-LAST:event_DiagnosisUtamaKeyPressed

    private void DiagnosisSekunderKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosisSekunderKeyPressed
        Valid.pindah(evt,DiagnosisUtama,RiwayatPenyakit);
    }//GEN-LAST:event_DiagnosisSekunderKeyPressed

    private void RiwayatPenyakitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RiwayatPenyakitKeyPressed
        Valid.pindah2(evt,DiagnosisSekunder,Anamnesis);
    }//GEN-LAST:event_RiwayatPenyakitKeyPressed

    private void AnamnesisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AnamnesisKeyPressed
        Valid.pindah2(evt,RiwayatPenyakit,PemeriksaanFisik);
    }//GEN-LAST:event_AnamnesisKeyPressed

    private void PemeriksaanFisikKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PemeriksaanFisikKeyPressed
        Valid.pindah2(evt,Anamnesis,PemeriksaanPenunjang);
    }//GEN-LAST:event_PemeriksaanFisikKeyPressed

    private void PemeriksaanPenunjangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PemeriksaanPenunjangKeyPressed
        Valid.pindah2(evt,PemeriksaanFisik,Terapi);
    }//GEN-LAST:event_PemeriksaanPenunjangKeyPressed

    private void TerapiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TerapiKeyPressed
        Valid.pindah2(evt,PemeriksaanPenunjang,Tindakan);
    }//GEN-LAST:event_TerapiKeyPressed

    private void TindakanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TindakanKeyPressed
        Valid.pindah(evt,Terapi,TindakLanjut);
    }//GEN-LAST:event_TindakanKeyPressed

    private void TindakLanjutKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TindakLanjutKeyPressed
        Valid.pindah(evt,Tindakan,Keterangan);
    }//GEN-LAST:event_TindakLanjutKeyPressed

    private void KeteranganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKeyPressed
        Valid.pindah(evt,TindakLanjut,BtnSimpan);
    }//GEN-LAST:event_KeteranganKeyPressed

    private void BtnDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokterKeyPressed
        Valid.pindah(evt,Tanggal,Alergi);
    }//GEN-LAST:event_BtnDokterKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMProfilRingkasRawatJalan dialog = new RMProfilRingkasRawatJalan(new javax.swing.JFrame(), true);
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
    private widget.TextBox Alergi;
    private widget.TextArea Anamnesis;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnDokter;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkInput;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.TextBox DiagnosisSekunder;
    private widget.TextBox DiagnosisUtama;
    private widget.PanelBiasa FormInput;
    private widget.TextBox JK;
    private widget.TextBox KdDokter;
    private widget.TextBox Keterangan;
    private widget.Label LCount;
    private widget.editorpane LoadHTML;
    private widget.MenuItem MnFormulir;
    private widget.TextBox NmDokter;
    private javax.swing.JPanel PanelInput;
    private widget.TextArea PemeriksaanFisik;
    private widget.TextArea PemeriksaanPenunjang;
    private widget.TextArea RiwayatPenyakit;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.Tanggal Tanggal;
    private widget.TextBox TanggalRegistrasi;
    private widget.TextArea Terapi;
    private widget.TextBox TglLahir;
    private widget.ComboBox TindakLanjut;
    private widget.TextBox Tindakan;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel19;
    private widget.Label jLabel23;
    private widget.Label jLabel39;
    private widget.Label jLabel4;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private widget.Label jLabelSd;
    private widget.Label jLabel_Alergi;
    private widget.Label jLabel_Anamnesis;
    private widget.Label jLabel_DiagnosisSekunder;
    private widget.Label jLabel_DiagnosisUtama;
    private widget.Label jLabel_Keterangan;
    private widget.Label jLabel_PemeriksaanFisik;
    private widget.Label jLabel_PemeriksaanPenunjang;
    private widget.Label jLabel_RiwayatPenyakit;
    private widget.Label jLabel_Terapi;
    private widget.Label jLabel_TindakLanjut;
    private widget.Label jLabel_Tindakan;
    private javax.swing.JPanel jPanel3;
    private widget.PopupMenu jPopupMenu1;
    private javax.swing.JSeparator jSeparator1;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollAnamnesis;
    private widget.ScrollPane scrollInput;
    private widget.ScrollPane scrollPemeriksaanFisik;
    private widget.ScrollPane scrollPemeriksaanPenunjang;
    private widget.ScrollPane scrollRiwayatPenyakit;
    private widget.ScrollPane scrollTerapi;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables
    
    private void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            if(TCari.getText().trim().equals("")){
                ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,profil_ringkas_rawat_jalan.kd_dokter,dokter.nm_dokter,"+
                    "profil_ringkas_rawat_jalan.tanggal,profil_ringkas_rawat_jalan.alergi,profil_ringkas_rawat_jalan.diagnosis_utama,profil_ringkas_rawat_jalan.diagnosis_sekunder,"+
                    "profil_ringkas_rawat_jalan.riwayat_penyakit,profil_ringkas_rawat_jalan.anamnesis,profil_ringkas_rawat_jalan.pemeriksaan_fisik,profil_ringkas_rawat_jalan.pemeriksaan_penunjang,"+
                    "profil_ringkas_rawat_jalan.terapi,profil_ringkas_rawat_jalan.tindakan,profil_ringkas_rawat_jalan.tindak_lanjut,profil_ringkas_rawat_jalan.keterangan "+
                    "from profil_ringkas_rawat_jalan inner join reg_periksa on profil_ringkas_rawat_jalan.no_rawat=reg_periksa.no_rawat inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join dokter on profil_ringkas_rawat_jalan.kd_dokter=dokter.kd_dokter where profil_ringkas_rawat_jalan.tanggal between ? and ? order by profil_ringkas_rawat_jalan.tanggal"
                );
            }else{
                ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,profil_ringkas_rawat_jalan.kd_dokter,dokter.nm_dokter,"+
                    "profil_ringkas_rawat_jalan.tanggal,profil_ringkas_rawat_jalan.alergi,profil_ringkas_rawat_jalan.diagnosis_utama,profil_ringkas_rawat_jalan.diagnosis_sekunder,"+
                    "profil_ringkas_rawat_jalan.riwayat_penyakit,profil_ringkas_rawat_jalan.anamnesis,profil_ringkas_rawat_jalan.pemeriksaan_fisik,profil_ringkas_rawat_jalan.pemeriksaan_penunjang,"+
                    "profil_ringkas_rawat_jalan.terapi,profil_ringkas_rawat_jalan.tindakan,profil_ringkas_rawat_jalan.tindak_lanjut,profil_ringkas_rawat_jalan.keterangan "+
                    "from profil_ringkas_rawat_jalan inner join reg_periksa on profil_ringkas_rawat_jalan.no_rawat=reg_periksa.no_rawat inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join dokter on profil_ringkas_rawat_jalan.kd_dokter=dokter.kd_dokter where profil_ringkas_rawat_jalan.tanggal between ? and ? and "+
                    "(reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or profil_ringkas_rawat_jalan.kd_dokter like ? or dokter.nm_dokter like ? or "+
                    "profil_ringkas_rawat_jalan.diagnosis_utama like ? or profil_ringkas_rawat_jalan.tindak_lanjut like ?) order by profil_ringkas_rawat_jalan.tanggal"
                );
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
                    ps.setString(8,"%"+TCari.getText()+"%");
                    ps.setString(9,"%"+TCari.getText()+"%");
                }
                    
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new Object[]{
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("jk"),rs.getString("tgl_lahir"),rs.getString("kd_dokter"),rs.getString("nm_dokter"),rs.getString("tanggal"),
                        rs.getString("alergi"),rs.getString("diagnosis_utama"),rs.getString("diagnosis_sekunder"),rs.getString("riwayat_penyakit"),rs.getString("anamnesis"),rs.getString("pemeriksaan_fisik"),
                        rs.getString("pemeriksaan_penunjang"),rs.getString("terapi"),rs.getString("tindakan"),rs.getString("tindak_lanjut"),rs.getString("keterangan")
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
        Alergi.setText("");
        DiagnosisUtama.setText("");
        DiagnosisSekunder.setText("");
        RiwayatPenyakit.setText("");
        Anamnesis.setText("");
        PemeriksaanFisik.setText("");
        PemeriksaanPenunjang.setText("");
        Terapi.setText("");
        Tindakan.setText("");
        TindakLanjut.setSelectedIndex(0);
        Keterangan.setText("");
        Tanggal.setDate(new Date());
        Alergi.requestFocus();
    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            JK.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString());
            Alergi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            DiagnosisUtama.setText(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            DiagnosisSekunder.setText(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            RiwayatPenyakit.setText(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            Anamnesis.setText(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            PemeriksaanFisik.setText(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            PemeriksaanPenunjang.setText(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            Terapi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
            Tindakan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            TindakLanjut.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());
            Keterangan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
            Valid.SetTgl2(Tanggal,tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());
        }
    }
    
    private void isRawat() {
        try {
            ps=koneksi.prepareStatement(
                "select reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,reg_periksa.tgl_registrasi,reg_periksa.jam_reg "+
                "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis where reg_periksa.no_rawat=?"
            );
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
 
    public void setNoRm(String norwt,Date tgl2) {
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        DTPCari2.setDate(tgl2);    
        isRawat(); 
        ChkInput.setSelected(true);
        isForm();
        runBackground(() ->tampil());
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            if(internalFrame1.getHeight()>678){
                ChkInput.setVisible(false);
                PanelInput.setPreferredSize(new Dimension(WIDTH,506));
                FormInput.setVisible(true);      
                ChkInput.setVisible(true);
            }else{
                ChkInput.setVisible(false);
                PanelInput.setPreferredSize(new Dimension(WIDTH,internalFrame1.getHeight()-175));
                FormInput.setVisible(true);      
                ChkInput.setVisible(true);
            }
        }else if(ChkInput.isSelected()==false){           
            ChkInput.setVisible(false);            
            PanelInput.setPreferredSize(new Dimension(WIDTH,20));
            FormInput.setVisible(false);      
            ChkInput.setVisible(true);
        }
    }
    
    public void isCek(){
        BtnSimpan.setEnabled(akses.getprofil_ringkas_rawat_jalan());
        BtnHapus.setEnabled(akses.getprofil_ringkas_rawat_jalan());
        BtnEdit.setEnabled(akses.getprofil_ringkas_rawat_jalan());
        BtnPrint.setEnabled(akses.getprofil_ringkas_rawat_jalan()); 
        
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
                Tanggal.setEditable(false);
                Tanggal.setEnabled(false);
            }
        }
    }

    private void ganti() {
        if(Sequel.mengedittf("profil_ringkas_rawat_jalan","no_rawat=?","no_rawat=?,tanggal=?,alergi=?,diagnosis_utama=?,diagnosis_sekunder=?,riwayat_penyakit=?,anamnesis=?,pemeriksaan_fisik=?,pemeriksaan_penunjang=?,terapi=?,tindakan=?,tindak_lanjut=?,keterangan=?,kd_dokter=?",15,new String[]{
            TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19),Alergi.getText(),DiagnosisUtama.getText(),DiagnosisSekunder.getText(),RiwayatPenyakit.getText(),Anamnesis.getText(),PemeriksaanFisik.getText(),PemeriksaanPenunjang.getText(),
            Terapi.getText(),Tindakan.getText(),TindakLanjut.getSelectedItem().toString(),Keterangan.getText(),KdDokter.getText(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
        })==true){
               tbObat.setValueAt(TNoRw.getText(),tbObat.getSelectedRow(),0);
               tbObat.setValueAt(TNoRM.getText(),tbObat.getSelectedRow(),1);
               tbObat.setValueAt(TPasien.getText(),tbObat.getSelectedRow(),2);
               tbObat.setValueAt(JK.getText(),tbObat.getSelectedRow(),3);
               tbObat.setValueAt(TglLahir.getText(),tbObat.getSelectedRow(),4);
               tbObat.setValueAt(KdDokter.getText(),tbObat.getSelectedRow(),5);
               tbObat.setValueAt(NmDokter.getText(),tbObat.getSelectedRow(),6);
               tbObat.setValueAt(Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19),tbObat.getSelectedRow(),7);
               tbObat.setValueAt(Alergi.getText(),tbObat.getSelectedRow(),8);
               tbObat.setValueAt(DiagnosisUtama.getText(),tbObat.getSelectedRow(),9);
               tbObat.setValueAt(DiagnosisSekunder.getText(),tbObat.getSelectedRow(),10);
               tbObat.setValueAt(RiwayatPenyakit.getText(),tbObat.getSelectedRow(),11);
               tbObat.setValueAt(Anamnesis.getText(),tbObat.getSelectedRow(),12);
               tbObat.setValueAt(PemeriksaanFisik.getText(),tbObat.getSelectedRow(),13);
               tbObat.setValueAt(PemeriksaanPenunjang.getText(),tbObat.getSelectedRow(),14);
               tbObat.setValueAt(Terapi.getText(),tbObat.getSelectedRow(),15);
               tbObat.setValueAt(Tindakan.getText(),tbObat.getSelectedRow(),16);
               tbObat.setValueAt(TindakLanjut.getSelectedItem().toString(),tbObat.getSelectedRow(),17);
               tbObat.setValueAt(Keterangan.getText(),tbObat.getSelectedRow(),18);
               emptTeks();
        }
    }

    private void hapus() {
        if(Sequel.queryu2tf("delete from profil_ringkas_rawat_jalan where no_rawat=?",1,new String[]{
            tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
        })==true){
            tabMode.removeRow(tbObat.getSelectedRow());
            LCount.setText(""+tabMode.getRowCount());
            emptTeks();
        }else{
            JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
        }
    }
    
    private void simpan() {
        if(Sequel.menyimpantf("profil_ringkas_rawat_jalan","?,?,?,?,?,?,?,?,?,?,?,?,?,?","Data",14,new String[]{
            TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19),Alergi.getText(),DiagnosisUtama.getText(),DiagnosisSekunder.getText(),
            RiwayatPenyakit.getText(),Anamnesis.getText(),PemeriksaanFisik.getText(),PemeriksaanPenunjang.getText(),Terapi.getText(),Tindakan.getText(),TindakLanjut.getSelectedItem().toString(),
            Keterangan.getText(),KdDokter.getText()
        })==true){
            tabMode.addRow(new Object[]{
                TNoRw.getText(),TNoRM.getText(),TPasien.getText(),JK.getText(),TglLahir.getText(),KdDokter.getText(),NmDokter.getText(),
                Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19),Alergi.getText(),
                DiagnosisUtama.getText(),DiagnosisSekunder.getText(),RiwayatPenyakit.getText(),Anamnesis.getText(),PemeriksaanFisik.getText(),
                PemeriksaanPenunjang.getText(),Terapi.getText(),Tindakan.getText(),TindakLanjut.getSelectedItem().toString(),Keterangan.getText()
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

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
import kepegawaian.DlgCariPetugas;


/**
 *
 * @author perpustakaan
 */
public final class RMChecklistPreOperasi extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;  
    private DlgCariPetugas petugas;
    private DlgCariDokter dokter;
    private String finger="",finger2="";
    private StringBuilder htmlContent;
    private String TANGGALMUNDUR="yes";
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private volatile boolean ceksukses = false;
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public RMChecklistPreOperasi(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(628,674);

        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.RM","Nama Pasien","Tgl.Lahir","J.K.","Tanggal","SN/CN","Tindakan","Kode Dokter Bedah","Nama Dokter Bedah",
            "Kode Dokter Anest","Nama Dokter Anestesi","Identitas","Keadaan Umum","Penandaan Area Operasi","Surat Ijin Bedah","Surat Ijin Anestesi",
            "Surat Ijin Transfusi","Persiapan Darah","Keterangan Persiapan Darah","Perlengkapan Khusus","Radiologi","Keterangan Radiologi",
            "EKG","Keterangan EKG","USG","Keterangan USG","CT Scan","Keterangan CT Scan","MRI","Keterangan MRI","NIP Ruangan","Petugas Ruangan",
            "NIP OK","Petugas Ruang OK"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
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
                column.setPreferredWidth(25);
            }else if(i==5){
                column.setPreferredWidth(115);
            }else if(i==6){
                column.setPreferredWidth(90);
            }else if(i==7){
                column.setPreferredWidth(160);
            }else if(i==8){
                column.setPreferredWidth(100);
            }else if(i==9){
                column.setPreferredWidth(160);
            }else if(i==10){
                column.setPreferredWidth(100);
            }else if(i==11){
                column.setPreferredWidth(150);
            }else if(i==12){
                column.setPreferredWidth(53);
            }else if(i==13){
                column.setPreferredWidth(85);
            }else if(i==14){
                column.setPreferredWidth(130);
            }else if(i==15){
                column.setPreferredWidth(88);
            }else if(i==16){
                column.setPreferredWidth(98);
            }else if(i==17){
                column.setPreferredWidth(102);
            }else if(i==18){
                column.setPreferredWidth(89);
            }else if(i==19){
                column.setPreferredWidth(149);
            }else if(i==20){
                column.setPreferredWidth(109);
            }else if(i==21){
                column.setPreferredWidth(90);
            }else if(i==22){
                column.setPreferredWidth(120);
            }else if(i==23){
                column.setPreferredWidth(90);
            }else if(i==24){
                column.setPreferredWidth(120);
            }else if(i==25){
                column.setPreferredWidth(90);
            }else if(i==26){
                column.setPreferredWidth(120);
            }else if(i==27){
                column.setPreferredWidth(90);
            }else if(i==28){
                column.setPreferredWidth(120);
            }else if(i==29){
                column.setPreferredWidth(90);
            }else if(i==30){
                column.setPreferredWidth(120);
            }else if(i==31){
                column.setPreferredWidth(90);
            }else if(i==32){
                column.setPreferredWidth(150);
            }else if(i==33){
                column.setPreferredWidth(90);
            }else if(i==34){
                column.setPreferredWidth(150);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());

        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        SNCN.setDocument(new batasInput((byte)25).getKata(SNCN));
        Tindakan.setDocument(new batasInput((byte)50).getKata(Tindakan));
        KeteranganPersiapanDarah.setDocument(new batasInput((byte)20).getKata(KeteranganPersiapanDarah));
        KeteranganRadiologi.setDocument(new batasInput((byte)20).getKata(KeteranganRadiologi));
        KeteranganEKG.setDocument(new batasInput((byte)20).getKata(KeteranganEKG));
        KeteranganUSG.setDocument(new batasInput((byte)20).getKata(KeteranganUSG));
        KeteranganCTScan.setDocument(new batasInput((byte)20).getKata(KeteranganCTScan));
        KeteranganMRI.setDocument(new batasInput((byte)20).getKata(KeteranganMRI));
        TCari.setDocument(new batasInput((int)100).getKata(TCari));
        
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
        MnSkriningNutrisi = new javax.swing.JMenuItem();
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
        jLabel18 = new widget.Label();
        KdPetugasRuangan = new widget.TextBox();
        NmPetugasRuangan = new widget.TextBox();
        btnPetugasRuangan = new widget.Button();
        jLabel8 = new widget.Label();
        TglLahir = new widget.TextBox();
        Tanggal = new widget.Tanggal();
        jLabel20 = new widget.Label();
        KeteranganPersiapanDarah = new widget.TextBox();
        SNCN = new widget.TextBox();
        jLabel22 = new widget.Label();
        jLabel23 = new widget.Label();
        KodeDokterBedah = new widget.TextBox();
        NamaDokterBedah = new widget.TextBox();
        btnDokterBedah = new widget.Button();
        btnDokterAnestesi = new widget.Button();
        NamaDokterAnestesi = new widget.TextBox();
        KodeDokterAnestesi = new widget.TextBox();
        jLabel24 = new widget.Label();
        jLabel25 = new widget.Label();
        jLabel50 = new widget.Label();
        Identitas = new widget.ComboBox();
        jLabel51 = new widget.Label();
        AreaOperasi = new widget.ComboBox();
        KeadaanUmum = new widget.ComboBox();
        jLabel52 = new widget.Label();
        IjinBedah = new widget.ComboBox();
        IjinAnestesi = new widget.ComboBox();
        jLabel54 = new widget.Label();
        IjinTransfusi = new widget.ComboBox();
        jLabel55 = new widget.Label();
        jLabel56 = new widget.Label();
        PersiapanDarah = new widget.ComboBox();
        Tindakan = new widget.TextBox();
        jLabel57 = new widget.Label();
        PerlengkapanKhusus = new widget.ComboBox();
        jLabel58 = new widget.Label();
        jLabel59 = new widget.Label();
        Radiologi = new widget.ComboBox();
        KeteranganRadiologi = new widget.TextBox();
        jLabel60 = new widget.Label();
        KeteranganEKG = new widget.TextBox();
        EKG = new widget.ComboBox();
        jLabel61 = new widget.Label();
        CTScan = new widget.ComboBox();
        KeteranganCTScan = new widget.TextBox();
        jLabel62 = new widget.Label();
        USG = new widget.ComboBox();
        KeteranganUSG = new widget.TextBox();
        jLabel63 = new widget.Label();
        MRI = new widget.ComboBox();
        KeteranganMRI = new widget.TextBox();
        jLabel26 = new widget.Label();
        KdPetugasOK = new widget.TextBox();
        NmPetugasOK = new widget.TextBox();
        btnPetugasOK = new widget.Button();
        jLabel5 = new widget.Label();
        jLabel64 = new widget.Label();
        jLabel65 = new widget.Label();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel27 = new widget.Label();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnSkriningNutrisi.setBackground(new java.awt.Color(255, 255, 254));
        MnSkriningNutrisi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSkriningNutrisi.setForeground(new java.awt.Color(50, 50, 50));
        MnSkriningNutrisi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnSkriningNutrisi.setText("Formulir Checklist Pre Operasi");
        MnSkriningNutrisi.setName("MnSkriningNutrisi"); // NOI18N
        MnSkriningNutrisi.setPreferredSize(new java.awt.Dimension(260, 26));
        MnSkriningNutrisi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSkriningNutrisiActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnSkriningNutrisi);

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N

        JK.setHighlighter(null);
        JK.setName("JK"); // NOI18N
        JK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JKKeyPressed(evt);
            }
        });

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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Check List Pre Operasi ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "06-02-2026" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "06-02-2026" }));
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
        FormInput.setPreferredSize(new java.awt.Dimension(100, 363));
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
        FormInput.add(TNoRM);
        TNoRM.setBounds(222, 10, 112, 23);

        jLabel16.setText("Tanggal :");
        jLabel16.setName("jLabel16"); // NOI18N
        jLabel16.setVerifyInputWhenFocusTarget(false);
        FormInput.add(jLabel16);
        jLabel16.setBounds(0, 40, 75, 23);

        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel18.setText("Petugas Ruangan");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(21, 330, 103, 23);

        KdPetugasRuangan.setEditable(false);
        KdPetugasRuangan.setHighlighter(null);
        KdPetugasRuangan.setName("KdPetugasRuangan"); // NOI18N
        FormInput.add(KdPetugasRuangan);
        KdPetugasRuangan.setBounds(117, 330, 95, 23);

        NmPetugasRuangan.setEditable(false);
        NmPetugasRuangan.setName("NmPetugasRuangan"); // NOI18N
        FormInput.add(NmPetugasRuangan);
        NmPetugasRuangan.setBounds(214, 330, 165, 23);

        btnPetugasRuangan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPetugasRuangan.setMnemonic('2');
        btnPetugasRuangan.setToolTipText("ALt+2");
        btnPetugasRuangan.setName("btnPetugasRuangan"); // NOI18N
        btnPetugasRuangan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPetugasRuanganActionPerformed(evt);
            }
        });
        btnPetugasRuangan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnPetugasRuanganKeyPressed(evt);
            }
        });
        FormInput.add(btnPetugasRuangan);
        btnPetugasRuangan.setBounds(381, 330, 28, 23);

        jLabel8.setText("Tgl.Lahir :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(625, 10, 60, 23);

        TglLahir.setHighlighter(null);
        TglLahir.setName("TglLahir"); // NOI18N
        FormInput.add(TglLahir);
        TglLahir.setBounds(689, 10, 100, 23);

        Tanggal.setForeground(new java.awt.Color(50, 70, 50));
        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "06-02-2026 13:51:20" }));
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

        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel20.setText("Perawat Melakukan Konfirmasi :");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(21, 100, 190, 23);

        KeteranganPersiapanDarah.setHighlighter(null);
        KeteranganPersiapanDarah.setName("KeteranganPersiapanDarah"); // NOI18N
        KeteranganPersiapanDarah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganPersiapanDarahKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganPersiapanDarah);
        KeteranganPersiapanDarah.setBounds(283, 180, 150, 23);

        SNCN.setHighlighter(null);
        SNCN.setName("SNCN"); // NOI18N
        SNCN.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SNCNKeyPressed(evt);
            }
        });
        FormInput.add(SNCN);
        SNCN.setBounds(264, 40, 120, 23);

        jLabel22.setText("SN/CN :");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(210, 40, 50, 23);

        jLabel23.setText("Dokter Bedah :");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(390, 40, 91, 23);

        KodeDokterBedah.setEditable(false);
        KodeDokterBedah.setHighlighter(null);
        KodeDokterBedah.setName("KodeDokterBedah"); // NOI18N
        FormInput.add(KodeDokterBedah);
        KodeDokterBedah.setBounds(485, 40, 97, 23);

        NamaDokterBedah.setEditable(false);
        NamaDokterBedah.setName("NamaDokterBedah"); // NOI18N
        FormInput.add(NamaDokterBedah);
        NamaDokterBedah.setBounds(584, 40, 175, 23);

        btnDokterBedah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnDokterBedah.setMnemonic('2');
        btnDokterBedah.setToolTipText("ALt+2");
        btnDokterBedah.setName("btnDokterBedah"); // NOI18N
        btnDokterBedah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDokterBedahActionPerformed(evt);
            }
        });
        btnDokterBedah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnDokterBedahKeyPressed(evt);
            }
        });
        FormInput.add(btnDokterBedah);
        btnDokterBedah.setBounds(761, 40, 28, 23);

        btnDokterAnestesi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnDokterAnestesi.setMnemonic('2');
        btnDokterAnestesi.setToolTipText("ALt+2");
        btnDokterAnestesi.setName("btnDokterAnestesi"); // NOI18N
        btnDokterAnestesi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDokterAnestesiActionPerformed(evt);
            }
        });
        btnDokterAnestesi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnDokterAnestesiKeyPressed(evt);
            }
        });
        FormInput.add(btnDokterAnestesi);
        btnDokterAnestesi.setBounds(761, 70, 28, 23);

        NamaDokterAnestesi.setEditable(false);
        NamaDokterAnestesi.setName("NamaDokterAnestesi"); // NOI18N
        FormInput.add(NamaDokterAnestesi);
        NamaDokterAnestesi.setBounds(584, 70, 175, 23);

        KodeDokterAnestesi.setEditable(false);
        KodeDokterAnestesi.setHighlighter(null);
        KodeDokterAnestesi.setName("KodeDokterAnestesi"); // NOI18N
        FormInput.add(KodeDokterAnestesi);
        KodeDokterAnestesi.setBounds(485, 70, 97, 23);

        jLabel24.setText("Dokter Anestesi :");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(390, 70, 91, 23);

        jLabel25.setText("Tindakan :");
        jLabel25.setName("jLabel25"); // NOI18N
        FormInput.add(jLabel25);
        jLabel25.setBounds(0, 70, 75, 23);

        jLabel50.setText("Identitas :");
        jLabel50.setName("jLabel50"); // NOI18N
        FormInput.add(jLabel50);
        jLabel50.setBounds(0, 120, 140, 23);

        Identitas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Identitas.setName("Identitas"); // NOI18N
        Identitas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                IdentitasKeyPressed(evt);
            }
        });
        FormInput.add(Identitas);
        Identitas.setBounds(144, 120, 80, 23);

        jLabel51.setText("Penandaan Area Operasi :");
        jLabel51.setName("jLabel51"); // NOI18N
        FormInput.add(jLabel51);
        jLabel51.setBounds(510, 120, 140, 23);

        AreaOperasi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ada", "Tidak Ada", "Tidak Diperlukan" }));
        AreaOperasi.setName("AreaOperasi"); // NOI18N
        AreaOperasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AreaOperasiKeyPressed(evt);
            }
        });
        FormInput.add(AreaOperasi);
        AreaOperasi.setBounds(654, 120, 135, 23);

        KeadaanUmum.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Baik", "Sedang", "Lemah" }));
        KeadaanUmum.setName("KeadaanUmum"); // NOI18N
        KeadaanUmum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeadaanUmumKeyPressed(evt);
            }
        });
        FormInput.add(KeadaanUmum);
        KeadaanUmum.setBounds(394, 120, 90, 23);

        jLabel52.setText("Keadaan Umum Pasien :");
        jLabel52.setName("jLabel52"); // NOI18N
        FormInput.add(jLabel52);
        jLabel52.setBounds(250, 120, 140, 23);

        IjinBedah.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ada", "Tidak Ada" }));
        IjinBedah.setName("IjinBedah"); // NOI18N
        IjinBedah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                IjinBedahKeyPressed(evt);
            }
        });
        FormInput.add(IjinBedah);
        IjinBedah.setBounds(144, 150, 100, 23);

        IjinAnestesi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ada", "Tidak Ada" }));
        IjinAnestesi.setName("IjinAnestesi"); // NOI18N
        IjinAnestesi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                IjinAnestesiKeyPressed(evt);
            }
        });
        FormInput.add(IjinAnestesi);
        IjinAnestesi.setBounds(394, 150, 100, 23);

        jLabel54.setText("Surat Ijin Anestesi :");
        jLabel54.setName("jLabel54"); // NOI18N
        FormInput.add(jLabel54);
        jLabel54.setBounds(280, 150, 110, 23);

        IjinTransfusi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ada", "Tidak Ada", "Tidak Diperlukan" }));
        IjinTransfusi.setName("IjinTransfusi"); // NOI18N
        IjinTransfusi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                IjinTransfusiKeyPressed(evt);
            }
        });
        FormInput.add(IjinTransfusi);
        IjinTransfusi.setBounds(654, 150, 135, 23);

        jLabel55.setText("Surat Ijin Tranfusi :");
        jLabel55.setName("jLabel55"); // NOI18N
        FormInput.add(jLabel55);
        jLabel55.setBounds(520, 150, 130, 23);

        jLabel56.setText("Perlengkapan Khusus, Alat/Implan :");
        jLabel56.setName("jLabel56"); // NOI18N
        FormInput.add(jLabel56);
        jLabel56.setBounds(460, 180, 190, 23);

        PersiapanDarah.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ada", "Tidak Ada", "Tidak Diperlukan" }));
        PersiapanDarah.setName("PersiapanDarah"); // NOI18N
        PersiapanDarah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PersiapanDarahKeyPressed(evt);
            }
        });
        FormInput.add(PersiapanDarah);
        PersiapanDarah.setBounds(144, 180, 135, 23);

        Tindakan.setHighlighter(null);
        Tindakan.setName("Tindakan"); // NOI18N
        Tindakan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TindakanKeyPressed(evt);
            }
        });
        FormInput.add(Tindakan);
        Tindakan.setBounds(79, 70, 305, 23);

        jLabel57.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel57.setText("Hasil Pemeriksaan Penunjang :");
        jLabel57.setName("jLabel57"); // NOI18N
        FormInput.add(jLabel57);
        jLabel57.setBounds(53, 210, 170, 23);

        PerlengkapanKhusus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ada", "Tidak Ada", "Tidak Diperlukan" }));
        PerlengkapanKhusus.setName("PerlengkapanKhusus"); // NOI18N
        PerlengkapanKhusus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PerlengkapanKhususKeyPressed(evt);
            }
        });
        FormInput.add(PerlengkapanKhusus);
        PerlengkapanKhusus.setBounds(654, 180, 135, 23);

        jLabel58.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel58.setText("Persiapan Darah");
        jLabel58.setName("jLabel58"); // NOI18N
        FormInput.add(jLabel58);
        jLabel58.setBounds(53, 180, 90, 23);

        jLabel59.setText("Radiologi :");
        jLabel59.setName("jLabel59"); // NOI18N
        FormInput.add(jLabel59);
        jLabel59.setBounds(66, 230, 100, 23);

        Radiologi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ada", "Tidak Ada", "Tidak Diperlukan" }));
        Radiologi.setName("Radiologi"); // NOI18N
        Radiologi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RadiologiKeyPressed(evt);
            }
        });
        FormInput.add(Radiologi);
        Radiologi.setBounds(170, 230, 135, 23);

        KeteranganRadiologi.setHighlighter(null);
        KeteranganRadiologi.setName("KeteranganRadiologi"); // NOI18N
        KeteranganRadiologi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganRadiologiKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganRadiologi);
        KeteranganRadiologi.setBounds(309, 230, 120, 23);

        jLabel60.setText("EKG :");
        jLabel60.setName("jLabel60"); // NOI18N
        FormInput.add(jLabel60);
        jLabel60.setBounds(456, 230, 70, 23);

        KeteranganEKG.setHighlighter(null);
        KeteranganEKG.setName("KeteranganEKG"); // NOI18N
        KeteranganEKG.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganEKGKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganEKG);
        KeteranganEKG.setBounds(669, 230, 120, 23);

        EKG.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ada", "Tidak Ada", "Tidak Diperlukan" }));
        EKG.setName("EKG"); // NOI18N
        EKG.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EKGKeyPressed(evt);
            }
        });
        FormInput.add(EKG);
        EKG.setBounds(530, 230, 135, 23);

        jLabel61.setText("CT Scan :");
        jLabel61.setName("jLabel61"); // NOI18N
        FormInput.add(jLabel61);
        jLabel61.setBounds(456, 260, 70, 23);

        CTScan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ada", "Tidak Ada", "Tidak Diperlukan" }));
        CTScan.setName("CTScan"); // NOI18N
        CTScan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CTScanKeyPressed(evt);
            }
        });
        FormInput.add(CTScan);
        CTScan.setBounds(530, 260, 135, 23);

        KeteranganCTScan.setHighlighter(null);
        KeteranganCTScan.setName("KeteranganCTScan"); // NOI18N
        KeteranganCTScan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganCTScanKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganCTScan);
        KeteranganCTScan.setBounds(669, 260, 120, 23);

        jLabel62.setText("USG :");
        jLabel62.setName("jLabel62"); // NOI18N
        FormInput.add(jLabel62);
        jLabel62.setBounds(66, 260, 100, 23);

        USG.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ada", "Tidak Ada", "Tidak Diperlukan" }));
        USG.setName("USG"); // NOI18N
        USG.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                USGKeyPressed(evt);
            }
        });
        FormInput.add(USG);
        USG.setBounds(170, 260, 135, 23);

        KeteranganUSG.setHighlighter(null);
        KeteranganUSG.setName("KeteranganUSG"); // NOI18N
        KeteranganUSG.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganUSGKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganUSG);
        KeteranganUSG.setBounds(309, 260, 120, 23);

        jLabel63.setText("MRI :");
        jLabel63.setName("jLabel63"); // NOI18N
        FormInput.add(jLabel63);
        jLabel63.setBounds(66, 290, 100, 23);

        MRI.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ada", "Tidak Ada", "Tidak Diperlukan" }));
        MRI.setName("MRI"); // NOI18N
        MRI.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MRIKeyPressed(evt);
            }
        });
        FormInput.add(MRI);
        MRI.setBounds(170, 290, 135, 23);

        KeteranganMRI.setHighlighter(null);
        KeteranganMRI.setName("KeteranganMRI"); // NOI18N
        KeteranganMRI.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganMRIKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganMRI);
        KeteranganMRI.setBounds(309, 290, 120, 23);

        jLabel26.setText("Petugas OK :");
        jLabel26.setName("jLabel26"); // NOI18N
        FormInput.add(jLabel26);
        jLabel26.setBounds(423, 330, 70, 23);

        KdPetugasOK.setEditable(false);
        KdPetugasOK.setHighlighter(null);
        KdPetugasOK.setName("KdPetugasOK"); // NOI18N
        FormInput.add(KdPetugasOK);
        KdPetugasOK.setBounds(497, 330, 95, 23);

        NmPetugasOK.setEditable(false);
        NmPetugasOK.setName("NmPetugasOK"); // NOI18N
        FormInput.add(NmPetugasOK);
        NmPetugasOK.setBounds(594, 330, 165, 23);

        btnPetugasOK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPetugasOK.setMnemonic('2');
        btnPetugasOK.setToolTipText("ALt+2");
        btnPetugasOK.setName("btnPetugasOK"); // NOI18N
        btnPetugasOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPetugasOKActionPerformed(evt);
            }
        });
        btnPetugasOK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnPetugasOKKeyPressed(evt);
            }
        });
        FormInput.add(btnPetugasOK);
        btnPetugasOK.setBounds(761, 330, 28, 23);

        jLabel5.setText(":");
        jLabel5.setName("jLabel5"); // NOI18N
        FormInput.add(jLabel5);
        jLabel5.setBounds(0, 10, 75, 23);

        jLabel64.setText("Surat Ijin Bedah :");
        jLabel64.setName("jLabel64"); // NOI18N
        FormInput.add(jLabel64);
        jLabel64.setBounds(0, 150, 140, 23);

        jLabel65.setText(":");
        jLabel65.setName("jLabel65"); // NOI18N
        FormInput.add(jLabel65);
        jLabel65.setBounds(0, 180, 140, 23);

        jSeparator1.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator1.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator1.setName("jSeparator1"); // NOI18N
        FormInput.add(jSeparator1);
        jSeparator1.setBounds(0, 100, 810, 1);

        jSeparator2.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator2.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator2.setName("jSeparator2"); // NOI18N
        FormInput.add(jSeparator2);
        jSeparator2.setBounds(0, 100, 810, 1);

        jSeparator3.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator3.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator3.setName("jSeparator3"); // NOI18N
        FormInput.add(jSeparator3);
        jSeparator3.setBounds(0, 320, 810, 1);

        jSeparator4.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator4.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator4.setName("jSeparator4"); // NOI18N
        FormInput.add(jSeparator4);
        jSeparator4.setBounds(0, 320, 810, 1);

        jLabel27.setText(":");
        jLabel27.setName("jLabel27"); // NOI18N
        FormInput.add(jLabel27);
        jLabel27.setBounds(3, 330, 110, 23);

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
        }else if(KodeDokterBedah.getText().trim().equals("")||NamaDokterBedah.getText().trim().equals("")){
            Valid.textKosong(btnDokterBedah,"Dokter Bedah");
        }else if(KodeDokterAnestesi.getText().trim().equals("")||NamaDokterAnestesi.getText().trim().equals("")){
            Valid.textKosong(KodeDokterAnestesi,"Dokter Anestesi");
        }else if(Tindakan.getText().trim().equals("")){
            Valid.textKosong(Tindakan,"Tindakan");
        }else if(SNCN.getText().trim().equals("")){
            Valid.textKosong(SNCN,"SN/CN");
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
            Valid.pindah(evt,btnPetugasOK,BtnBatal);
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
                if(akses.getkode().equals(tbObat.getValueAt(tbObat.getSelectedRow(),31).toString())||akses.getkode().equals(tbObat.getValueAt(tbObat.getSelectedRow(),33).toString())){
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
        }else if(KodeDokterBedah.getText().trim().equals("")||NamaDokterBedah.getText().trim().equals("")){
            Valid.textKosong(btnDokterBedah,"Dokter Bedah");
        }else if(KodeDokterAnestesi.getText().trim().equals("")||NamaDokterAnestesi.getText().trim().equals("")){
            Valid.textKosong(KodeDokterAnestesi,"Dokter Anestesi");
        }else if(Tindakan.getText().trim().equals("")){
            Valid.textKosong(Tindakan,"Tindakan");
        }else if(SNCN.getText().trim().equals("")){
            Valid.textKosong(SNCN,"SN/CN");
        }else{  
            if(tbObat.getSelectedRow()>-1){
                if(akses.getkode().equals("Admin Utama")){
                    ganti();
                }else {
                    if(akses.getkode().equals(tbObat.getValueAt(tbObat.getSelectedRow(),31).toString())||akses.getkode().equals(tbObat.getValueAt(tbObat.getSelectedRow(),33).toString())){
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
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>SN/CN</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tindakan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kode Dokter Bedah</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Dokter Bedah</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kode Dokter Anest</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Dokter Anestesi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Identitas</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keadaan Umum</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Penandaan Area Operasi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Surat Ijin Bedah</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Surat Ijin Anestesi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Surat Ijin Transfusi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Persiapan Darah</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Persiapan Darah</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Perlengkapan Khusus</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Radiologi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Radiologi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>EKG</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan EKG</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>USG</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan USG</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>CT Scan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan CT Scan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>MRI</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan MRI</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>NIP Ruangan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Petugas Ruangan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>NIP OK</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Petugas Ruang OK</b></td>"+
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
                        "</tr>");
                }
                
                LoadHTML.setText(
                    "<html>"+
                      "<table width='3500px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
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

                File f = new File("DataChecklistPreOperasi.html");            
                BufferedWriter bw = new BufferedWriter(new FileWriter(f));            
                bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                            "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                            "<table width='3500px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                "<tr class='isi2'>"+
                                    "<td valign='top' align='center'>"+
                                        "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                        akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                        akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                        "<font size='2' face='Tahoma'>DATA CHECK LIST PRE OPERASI<br><br></font>"+        
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

    private void btnPetugasRuanganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPetugasRuanganActionPerformed
        if (petugas == null || !petugas.isDisplayable()) {
            petugas=new DlgCariPetugas(null,false);
            petugas.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            petugas.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    if(petugas.getTable().getSelectedRow()!= -1){                   
                        KdPetugasRuangan.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                        NmPetugasRuangan.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                    }  
                    btnPetugasRuangan.requestFocus();
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
    }//GEN-LAST:event_btnPetugasRuanganActionPerformed

    private void btnPetugasRuanganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPetugasRuanganKeyPressed
        Valid.pindah(evt,KeteranganMRI,btnPetugasOK);
    }//GEN-LAST:event_btnPetugasRuanganKeyPressed

    private void MnSkriningNutrisiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnSkriningNutrisiActionPerformed
        if(tbObat.getSelectedRow()>-1){
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());   
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",tbObat.getValueAt(tbObat.getSelectedRow(),31).toString());
            param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbObat.getValueAt(tbObat.getSelectedRow(),32).toString()+"\nID "+(finger.equals("")?tbObat.getValueAt(tbObat.getSelectedRow(),31).toString():finger)+"\n"+Tanggal.getSelectedItem()); 
            finger2=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",tbObat.getValueAt(tbObat.getSelectedRow(),33).toString());
            param.put("finger2","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbObat.getValueAt(tbObat.getSelectedRow(),34).toString()+"\nID "+(finger2.equals("")?tbObat.getValueAt(tbObat.getSelectedRow(),33).toString():finger2)+"\n"+Tanggal.getSelectedItem()); 
            Valid.MyReportqry("rptFormulirChecklistPreOperasi.jasper","report","::[ Formulir Check List Pre Operasi ]::",
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,pasien.jk,checklist_pre_operasi.tanggal,"+
                    "checklist_pre_operasi.sncn,checklist_pre_operasi.tindakan,checklist_pre_operasi.kd_dokter_bedah,dokterbedah.nm_dokter as dokterbedah,"+
                    "checklist_pre_operasi.kd_dokter_anestesi,dokteranestesi.nm_dokter as dokteranestesi,checklist_pre_operasi.identitas,"+
                    "checklist_pre_operasi.surat_ijin_bedah,checklist_pre_operasi.surat_ijin_anestesi,checklist_pre_operasi.surat_ijin_transfusi,"+
                    "checklist_pre_operasi.penandaan_area_operasi,checklist_pre_operasi.keadaan_umum,checklist_pre_operasi.pemeriksaan_penunjang_rontgen,"+
                    "checklist_pre_operasi.keterangan_pemeriksaan_penunjang_rontgen,checklist_pre_operasi.pemeriksaan_penunjang_ekg,"+
                    "checklist_pre_operasi.keterangan_pemeriksaan_penunjang_ekg,checklist_pre_operasi.pemeriksaan_penunjang_usg,"+
                    "checklist_pre_operasi.keterangan_pemeriksaan_penunjang_usg,checklist_pre_operasi.pemeriksaan_penunjang_ctscan,"+
                    "checklist_pre_operasi.keterangan_pemeriksaan_penunjang_ctscan,checklist_pre_operasi.pemeriksaan_penunjang_mri,"+
                    "checklist_pre_operasi.keterangan_pemeriksaan_penunjang_mri,checklist_pre_operasi.persiapan_darah,checklist_pre_operasi.keterangan_persiapan_darah,"+
                    "checklist_pre_operasi.perlengkapan_khusus,checklist_pre_operasi.nip_petugas_ruangan,petugasruangan.nama as petugasruangan,"+
                    "checklist_pre_operasi.nip_perawat_ok,petugasok.nama as petugasok "+
                    "from checklist_pre_operasi inner join reg_periksa on checklist_pre_operasi.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join dokter as dokterbedah on dokterbedah.kd_dokter=checklist_pre_operasi.kd_dokter_bedah "+
                    "inner join dokter as dokteranestesi on dokteranestesi.kd_dokter=checklist_pre_operasi.kd_dokter_anestesi "+
                    "inner join petugas as petugasruangan on petugasruangan.nip=checklist_pre_operasi.nip_petugas_ruangan "+
                    "inner join petugas as petugasok on petugasok.nip=checklist_pre_operasi.nip_perawat_ok "+
                    "where checklist_pre_operasi.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"' and checklist_pre_operasi.tanggal='"+tbObat.getValueAt(tbObat.getSelectedRow(),5).toString()+"' ",param);
        }
    }//GEN-LAST:event_MnSkriningNutrisiActionPerformed

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void TanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKeyPressed
       // Valid.pindah(evt,Rencana,Informasi);
    }//GEN-LAST:event_TanggalKeyPressed

    private void btnDokterBedahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDokterBedahActionPerformed
        if (dokter == null || !dokter.isDisplayable()) {
            dokter=new DlgCariDokter(null,false);
            dokter.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            dokter.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    if(dokter.getTable().getSelectedRow()!= -1){        
                         KodeDokterBedah.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                         NamaDokterBedah.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                    }  
                    btnDokterBedah.requestFocus();
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
    }//GEN-LAST:event_btnDokterBedahActionPerformed

    private void btnDokterBedahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnDokterBedahKeyPressed
        Valid.pindah(evt,Tindakan,btnDokterAnestesi);
    }//GEN-LAST:event_btnDokterBedahKeyPressed

    private void btnDokterAnestesiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDokterAnestesiActionPerformed
        if (dokter == null || !dokter.isDisplayable()) {
            dokter=new DlgCariDokter(null,false);
            dokter.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            dokter.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    if(dokter.getTable().getSelectedRow()!= -1){        
                         KodeDokterAnestesi.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                         NamaDokterAnestesi.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                    }  
                    btnDokterAnestesi.requestFocus();
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
    }//GEN-LAST:event_btnDokterAnestesiActionPerformed

    private void btnDokterAnestesiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnDokterAnestesiKeyPressed
        Valid.pindah(evt,btnDokterBedah,Identitas);
    }//GEN-LAST:event_btnDokterAnestesiKeyPressed

    private void IdentitasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_IdentitasKeyPressed
        Valid.pindah(evt,btnDokterAnestesi,KeadaanUmum);
    }//GEN-LAST:event_IdentitasKeyPressed

    private void AreaOperasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AreaOperasiKeyPressed
        Valid.pindah(evt,KeadaanUmum,IjinBedah);
    }//GEN-LAST:event_AreaOperasiKeyPressed

    private void KeadaanUmumKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeadaanUmumKeyPressed
        Valid.pindah(evt,Identitas,AreaOperasi);
    }//GEN-LAST:event_KeadaanUmumKeyPressed

    private void IjinBedahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_IjinBedahKeyPressed
        Valid.pindah(evt,AreaOperasi,IjinAnestesi);
    }//GEN-LAST:event_IjinBedahKeyPressed

    private void IjinAnestesiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_IjinAnestesiKeyPressed
        Valid.pindah(evt,IjinBedah,IjinTransfusi);
    }//GEN-LAST:event_IjinAnestesiKeyPressed

    private void IjinTransfusiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_IjinTransfusiKeyPressed
        Valid.pindah(evt,IjinAnestesi,PersiapanDarah);
    }//GEN-LAST:event_IjinTransfusiKeyPressed

    private void PersiapanDarahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PersiapanDarahKeyPressed
       Valid.pindah(evt,IjinTransfusi,KeteranganPersiapanDarah);
    }//GEN-LAST:event_PersiapanDarahKeyPressed

    private void PerlengkapanKhususKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PerlengkapanKhususKeyPressed
        Valid.pindah(evt,KeteranganPersiapanDarah,Radiologi);
    }//GEN-LAST:event_PerlengkapanKhususKeyPressed

    private void RadiologiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RadiologiKeyPressed
        Valid.pindah(evt,PerlengkapanKhusus,KeteranganRadiologi);
    }//GEN-LAST:event_RadiologiKeyPressed

    private void EKGKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EKGKeyPressed
        Valid.pindah(evt,KeteranganRadiologi,KeteranganEKG);
    }//GEN-LAST:event_EKGKeyPressed

    private void CTScanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CTScanKeyPressed
        Valid.pindah(evt,KeteranganUSG,KeteranganCTScan);
    }//GEN-LAST:event_CTScanKeyPressed

    private void USGKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_USGKeyPressed
        Valid.pindah(evt,KeteranganEKG,KeteranganUSG);
    }//GEN-LAST:event_USGKeyPressed

    private void MRIKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MRIKeyPressed
        Valid.pindah(evt,KeteranganCTScan,KeteranganMRI);
    }//GEN-LAST:event_MRIKeyPressed

    private void btnPetugasOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPetugasOKActionPerformed
        if (petugas == null || !petugas.isDisplayable()) {
            petugas=new DlgCariPetugas(null,false);
            petugas.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            petugas.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    if(petugas.getTable().getSelectedRow()!= -1){                   
                        KdPetugasOK.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                        NmPetugasOK.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                    }  
                    btnPetugasOK.requestFocus();
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
    }//GEN-LAST:event_btnPetugasOKActionPerformed

    private void btnPetugasOKKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPetugasOKKeyPressed
        Valid.pindah(evt,btnPetugasRuangan,BtnSimpan);
    }//GEN-LAST:event_btnPetugasOKKeyPressed

    private void SNCNKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SNCNKeyPressed
        Valid.pindah(evt,Tanggal,Tindakan);
    }//GEN-LAST:event_SNCNKeyPressed

    private void TindakanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TindakanKeyPressed
        Valid.pindah(evt,SNCN,btnDokterBedah);
    }//GEN-LAST:event_TindakanKeyPressed

    private void KeteranganPersiapanDarahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganPersiapanDarahKeyPressed
        Valid.pindah(evt,PersiapanDarah,PerlengkapanKhusus);
    }//GEN-LAST:event_KeteranganPersiapanDarahKeyPressed

    private void KeteranganRadiologiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganRadiologiKeyPressed
        Valid.pindah(evt,Radiologi,EKG);
    }//GEN-LAST:event_KeteranganRadiologiKeyPressed

    private void KeteranganEKGKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganEKGKeyPressed
        Valid.pindah(evt,EKG,USG);
    }//GEN-LAST:event_KeteranganEKGKeyPressed

    private void KeteranganUSGKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganUSGKeyPressed
        Valid.pindah(evt,USG,CTScan);
    }//GEN-LAST:event_KeteranganUSGKeyPressed

    private void KeteranganCTScanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganCTScanKeyPressed
        Valid.pindah(evt,CTScan,MRI);
    }//GEN-LAST:event_KeteranganCTScanKeyPressed

    private void KeteranganMRIKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganMRIKeyPressed
        Valid.pindah(evt,MRI,btnPetugasRuangan);
    }//GEN-LAST:event_KeteranganMRIKeyPressed

    private void JKKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JKKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_JKKeyPressed

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
            RMChecklistPreOperasi dialog = new RMChecklistPreOperasi(new javax.swing.JFrame(), true);
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
    private widget.ComboBox AreaOperasi;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.ComboBox CTScan;
    private widget.CekBox ChkInput;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.ComboBox EKG;
    private widget.PanelBiasa FormInput;
    private widget.ComboBox Identitas;
    private widget.ComboBox IjinAnestesi;
    private widget.ComboBox IjinBedah;
    private widget.ComboBox IjinTransfusi;
    private widget.TextBox JK;
    private widget.TextBox KdPetugasOK;
    private widget.TextBox KdPetugasRuangan;
    private widget.ComboBox KeadaanUmum;
    private widget.TextBox KeteranganCTScan;
    private widget.TextBox KeteranganEKG;
    private widget.TextBox KeteranganMRI;
    private widget.TextBox KeteranganPersiapanDarah;
    private widget.TextBox KeteranganRadiologi;
    private widget.TextBox KeteranganUSG;
    private widget.TextBox KodeDokterAnestesi;
    private widget.TextBox KodeDokterBedah;
    private widget.Label LCount;
    private widget.editorpane LoadHTML;
    private widget.ComboBox MRI;
    private javax.swing.JMenuItem MnSkriningNutrisi;
    private widget.TextBox NamaDokterAnestesi;
    private widget.TextBox NamaDokterBedah;
    private widget.TextBox NmPetugasOK;
    private widget.TextBox NmPetugasRuangan;
    private javax.swing.JPanel PanelInput;
    private widget.ComboBox PerlengkapanKhusus;
    private widget.ComboBox PersiapanDarah;
    private widget.ComboBox Radiologi;
    private widget.TextBox SNCN;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.Tanggal Tanggal;
    private widget.TextBox TanggalRegistrasi;
    private widget.TextBox TglLahir;
    private widget.TextBox Tindakan;
    private widget.ComboBox USG;
    private widget.Button btnDokterAnestesi;
    private widget.Button btnDokterBedah;
    private widget.Button btnPetugasOK;
    private widget.Button btnPetugasRuangan;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel16;
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
    private widget.Label jLabel4;
    private widget.Label jLabel5;
    private widget.Label jLabel50;
    private widget.Label jLabel51;
    private widget.Label jLabel52;
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
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollInput;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables
    
    private void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            if(TCari.getText().trim().equals("")){
                ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,pasien.jk,checklist_pre_operasi.tanggal,"+
                    "checklist_pre_operasi.sncn,checklist_pre_operasi.tindakan,checklist_pre_operasi.kd_dokter_bedah,dokterbedah.nm_dokter as dokterbedah,"+
                    "checklist_pre_operasi.kd_dokter_anestesi,dokteranestesi.nm_dokter as dokteranestesi,checklist_pre_operasi.identitas,"+
                    "checklist_pre_operasi.surat_ijin_bedah,checklist_pre_operasi.surat_ijin_anestesi,checklist_pre_operasi.surat_ijin_transfusi,"+
                    "checklist_pre_operasi.penandaan_area_operasi,checklist_pre_operasi.keadaan_umum,checklist_pre_operasi.pemeriksaan_penunjang_rontgen,"+
                    "checklist_pre_operasi.keterangan_pemeriksaan_penunjang_rontgen,checklist_pre_operasi.pemeriksaan_penunjang_ekg,"+
                    "checklist_pre_operasi.keterangan_pemeriksaan_penunjang_ekg,checklist_pre_operasi.pemeriksaan_penunjang_usg,"+
                    "checklist_pre_operasi.keterangan_pemeriksaan_penunjang_usg,checklist_pre_operasi.pemeriksaan_penunjang_ctscan,"+
                    "checklist_pre_operasi.keterangan_pemeriksaan_penunjang_ctscan,checklist_pre_operasi.pemeriksaan_penunjang_mri,"+
                    "checklist_pre_operasi.keterangan_pemeriksaan_penunjang_mri,checklist_pre_operasi.persiapan_darah,checklist_pre_operasi.keterangan_persiapan_darah,"+
                    "checklist_pre_operasi.perlengkapan_khusus,checklist_pre_operasi.nip_petugas_ruangan,petugasruangan.nama as petugasruangan,"+
                    "checklist_pre_operasi.nip_perawat_ok,petugasok.nama as petugasok "+
                    "from checklist_pre_operasi inner join reg_periksa on checklist_pre_operasi.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join dokter as dokterbedah on dokterbedah.kd_dokter=checklist_pre_operasi.kd_dokter_bedah "+
                    "inner join dokter as dokteranestesi on dokteranestesi.kd_dokter=checklist_pre_operasi.kd_dokter_anestesi "+
                    "inner join petugas as petugasruangan on petugasruangan.nip=checklist_pre_operasi.nip_petugas_ruangan "+
                    "inner join petugas as petugasok on petugasok.nip=checklist_pre_operasi.nip_perawat_ok "+
                    "where checklist_pre_operasi.tanggal between ? and ? order by checklist_pre_operasi.tanggal ");
            }else{
                ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,pasien.jk,checklist_pre_operasi.tanggal,"+
                    "checklist_pre_operasi.sncn,checklist_pre_operasi.tindakan,checklist_pre_operasi.kd_dokter_bedah,dokterbedah.nm_dokter as dokterbedah,"+
                    "checklist_pre_operasi.kd_dokter_anestesi,dokteranestesi.nm_dokter as dokteranestesi,checklist_pre_operasi.identitas,"+
                    "checklist_pre_operasi.surat_ijin_bedah,checklist_pre_operasi.surat_ijin_anestesi,checklist_pre_operasi.surat_ijin_transfusi,"+
                    "checklist_pre_operasi.penandaan_area_operasi,checklist_pre_operasi.keadaan_umum,checklist_pre_operasi.pemeriksaan_penunjang_rontgen,"+
                    "checklist_pre_operasi.keterangan_pemeriksaan_penunjang_rontgen,checklist_pre_operasi.pemeriksaan_penunjang_ekg,"+
                    "checklist_pre_operasi.keterangan_pemeriksaan_penunjang_ekg,checklist_pre_operasi.pemeriksaan_penunjang_usg,"+
                    "checklist_pre_operasi.keterangan_pemeriksaan_penunjang_usg,checklist_pre_operasi.pemeriksaan_penunjang_ctscan,"+
                    "checklist_pre_operasi.keterangan_pemeriksaan_penunjang_ctscan,checklist_pre_operasi.pemeriksaan_penunjang_mri,"+
                    "checklist_pre_operasi.keterangan_pemeriksaan_penunjang_mri,checklist_pre_operasi.persiapan_darah,checklist_pre_operasi.keterangan_persiapan_darah,"+
                    "checklist_pre_operasi.perlengkapan_khusus,checklist_pre_operasi.nip_petugas_ruangan,petugasruangan.nama as petugasruangan,"+
                    "checklist_pre_operasi.nip_perawat_ok,petugasok.nama as petugasok "+
                    "from checklist_pre_operasi inner join reg_periksa on checklist_pre_operasi.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join dokter as dokterbedah on dokterbedah.kd_dokter=checklist_pre_operasi.kd_dokter_bedah "+
                    "inner join dokter as dokteranestesi on dokteranestesi.kd_dokter=checklist_pre_operasi.kd_dokter_anestesi "+
                    "inner join petugas as petugasruangan on petugasruangan.nip=checklist_pre_operasi.nip_petugas_ruangan "+
                    "inner join petugas as petugasok on petugasok.nip=checklist_pre_operasi.nip_perawat_ok "+
                    "where checklist_pre_operasi.tanggal between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or "+
                    "pasien.nm_pasien like ? or dokterbedah.nm_dokter like ? or dokteranestesi.nm_dokter like ? or petugasruangan.nama like ?) "+
                    "order by checklist_pre_operasi.tanggal ");
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
                }
                    
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new Object[]{
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getDate("tgl_lahir"),rs.getString("jk"),
                        rs.getString("tanggal"),rs.getString("sncn"),rs.getString("tindakan"),rs.getString("kd_dokter_bedah"),rs.getString("dokterbedah"),
                        rs.getString("kd_dokter_anestesi"),rs.getString("dokteranestesi"),rs.getString("identitas"),rs.getString("keadaan_umum"),
                        rs.getString("penandaan_area_operasi"),rs.getString("surat_ijin_bedah"),rs.getString("surat_ijin_anestesi"),rs.getString("surat_ijin_transfusi"),
                        rs.getString("persiapan_darah"),rs.getString("keterangan_persiapan_darah"),rs.getString("perlengkapan_khusus"),rs.getString("pemeriksaan_penunjang_rontgen"),
                        rs.getString("keterangan_pemeriksaan_penunjang_rontgen"),rs.getString("pemeriksaan_penunjang_ekg"),rs.getString("keterangan_pemeriksaan_penunjang_ekg"),
                        rs.getString("pemeriksaan_penunjang_usg"),rs.getString("keterangan_pemeriksaan_penunjang_usg"),rs.getString("pemeriksaan_penunjang_ctscan"),
                        rs.getString("keterangan_pemeriksaan_penunjang_ctscan"),rs.getString("pemeriksaan_penunjang_mri"),rs.getString("keterangan_pemeriksaan_penunjang_mri"),
                        rs.getString("nip_petugas_ruangan"),rs.getString("petugasruangan"),rs.getString("nip_perawat_ok"),rs.getString("petugasok")
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
        SNCN.setText("");
        Tindakan.setText("");
        KodeDokterBedah.setText("");
        NamaDokterBedah.setText("");
        KodeDokterAnestesi.setText("");
        NamaDokterAnestesi.setText("");
        Identitas.setSelectedIndex(0);
        KeadaanUmum.setSelectedIndex(0);
        AreaOperasi.setSelectedIndex(0);
        IjinBedah.setSelectedIndex(0);
        IjinAnestesi.setSelectedIndex(0);
        IjinTransfusi.setSelectedIndex(0);
        PersiapanDarah.setSelectedIndex(0);
        KeteranganPersiapanDarah.setText("");
        PerlengkapanKhusus.setSelectedIndex(0);
        Radiologi.setSelectedIndex(0);
        KeteranganRadiologi.setText("");
        EKG.setSelectedIndex(0);
        KeteranganEKG.setText("");
        USG.setSelectedIndex(0);
        KeteranganUSG.setText("");
        CTScan.setSelectedIndex(0);
        KeteranganCTScan.setText("");
        MRI.setSelectedIndex(0);
        KeteranganMRI.setText("");
        SNCN.requestFocus();
    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            JK.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString());
            SNCN.setText(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString());
            Tindakan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());
            KodeDokterBedah.setText(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            NamaDokterBedah.setText(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            KodeDokterAnestesi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            NamaDokterAnestesi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            Identitas.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            KeadaanUmum.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            AreaOperasi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            IjinBedah.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
            IjinAnestesi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            IjinTransfusi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());
            PersiapanDarah.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
            KeteranganPersiapanDarah.setText(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
            PerlengkapanKhusus.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString());
            Radiologi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString());
            KeteranganRadiologi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString());
            EKG.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString());
            KeteranganEKG.setText(tbObat.getValueAt(tbObat.getSelectedRow(),24).toString());
            USG.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),25).toString());
            KeteranganUSG.setText(tbObat.getValueAt(tbObat.getSelectedRow(),26).toString());
            CTScan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),27).toString());
            KeteranganCTScan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),28).toString());
            MRI.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),29).toString());
            KeteranganMRI.setText(tbObat.getValueAt(tbObat.getSelectedRow(),30).toString());
            KdPetugasRuangan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),31).toString());
            NmPetugasRuangan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),32).toString());
            KdPetugasOK.setText(tbObat.getValueAt(tbObat.getSelectedRow(),33).toString());
            NmPetugasOK.setText(tbObat.getValueAt(tbObat.getSelectedRow(),34).toString());
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
        runBackground(() ->tampil());
    }
    
    public void setNoRm(String norwt, Date tgl2,String KodeDokter,String NamaDokter,String Operasi) {
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        DTPCari2.setDate(tgl2);
        isRawat();
        ChkInput.setSelected(true);
        isForm();
        KodeDokterBedah.setText(KodeDokter);
        NamaDokterBedah.setText(NamaDokter);
        Tindakan.setText(Operasi);
        runBackground(() ->tampil());
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            if(internalFrame1.getHeight()>558){
                ChkInput.setVisible(false);
                PanelInput.setPreferredSize(new Dimension(WIDTH,386));
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
        BtnSimpan.setEnabled(akses.getchecklist_pre_operasi());
        BtnHapus.setEnabled(akses.getchecklist_pre_operasi());
        BtnEdit.setEnabled(akses.getchecklist_pre_operasi());
        BtnPrint.setEnabled(akses.getchecklist_pre_operasi()); 
        if(TANGGALMUNDUR.equals("no")){
            if(!akses.getkode().equals("Admin Utama")){
                Tanggal.setEditable(false);
                Tanggal.setEnabled(false);
            }
        }
    }

    private void ganti() {
        if(Sequel.mengedittf("checklist_pre_operasi","no_rawat=? and tanggal=?","no_rawat=?,tanggal=?,sncn=?,tindakan=?,kd_dokter_bedah=?,kd_dokter_anestesi=?,identitas=?,"+
            "surat_ijin_bedah=?,surat_ijin_anestesi=?,surat_ijin_transfusi=?,penandaan_area_operasi=?,keadaan_umum=?,pemeriksaan_penunjang_rontgen=?,keterangan_pemeriksaan_penunjang_rontgen=?,"+
            "pemeriksaan_penunjang_ekg=?,keterangan_pemeriksaan_penunjang_ekg=?,pemeriksaan_penunjang_usg=?,keterangan_pemeriksaan_penunjang_usg=?,pemeriksaan_penunjang_ctscan=?,"+
            "keterangan_pemeriksaan_penunjang_ctscan=?,pemeriksaan_penunjang_mri=?,keterangan_pemeriksaan_penunjang_mri=?,persiapan_darah=?,keterangan_persiapan_darah=?,perlengkapan_khusus=?,"+
            "nip_petugas_ruangan=?,nip_perawat_ok=?",29,new String[]{
                TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19),SNCN.getText(),Tindakan.getText(),
                KodeDokterBedah.getText(),KodeDokterAnestesi.getText(),Identitas.getSelectedItem().toString(),IjinBedah.getSelectedItem().toString(), 
                IjinAnestesi.getSelectedItem().toString(),IjinTransfusi.getSelectedItem().toString(),AreaOperasi.getSelectedItem().toString(), 
                KeadaanUmum.getSelectedItem().toString(),Radiologi.getSelectedItem().toString(),KeteranganRadiologi.getText(),EKG.getSelectedItem().toString(), 
                KeteranganEKG.getText(),USG.getSelectedItem().toString(),KeteranganUSG.getText(),CTScan.getSelectedItem().toString(),KeteranganCTScan.getText(), 
                MRI.getSelectedItem().toString(),KeteranganMRI.getText(),PersiapanDarah.getSelectedItem().toString(),KeteranganPersiapanDarah.getText(), 
                PerlengkapanKhusus.getSelectedItem().toString(),KdPetugasRuangan.getText(),KdPetugasOK.getText(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString(),
                tbObat.getValueAt(tbObat.getSelectedRow(),5).toString()
        })==true){
            tbObat.setValueAt(TNoRw.getText(),tbObat.getSelectedRow(),0);
            tbObat.setValueAt(TNoRM.getText(),tbObat.getSelectedRow(),1);
            tbObat.setValueAt(TPasien.getText(),tbObat.getSelectedRow(),2);
            tbObat.setValueAt(TglLahir.getText(),tbObat.getSelectedRow(),3);
            tbObat.setValueAt(JK.getText(),tbObat.getSelectedRow(),4);
            tbObat.setValueAt(Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19),tbObat.getSelectedRow(),5);
            tbObat.setValueAt(SNCN.getText(),tbObat.getSelectedRow(),6);
            tbObat.setValueAt(Tindakan.getText(),tbObat.getSelectedRow(),7);
            tbObat.setValueAt(KodeDokterBedah.getText(),tbObat.getSelectedRow(),8);
            tbObat.setValueAt(NamaDokterBedah.getText(),tbObat.getSelectedRow(),9);
            tbObat.setValueAt(KodeDokterAnestesi.getText(),tbObat.getSelectedRow(),10);
            tbObat.setValueAt(NamaDokterAnestesi.getText(),tbObat.getSelectedRow(),11);
            tbObat.setValueAt(Identitas.getSelectedItem().toString(),tbObat.getSelectedRow(),12);
            tbObat.setValueAt(KeadaanUmum.getSelectedItem().toString(),tbObat.getSelectedRow(),13);
            tbObat.setValueAt(AreaOperasi.getSelectedItem().toString(),tbObat.getSelectedRow(),14);
            tbObat.setValueAt(IjinBedah.getSelectedItem().toString(),tbObat.getSelectedRow(),15);
            tbObat.setValueAt(IjinAnestesi.getSelectedItem().toString(),tbObat.getSelectedRow(),16);
            tbObat.setValueAt(IjinTransfusi.getSelectedItem().toString(),tbObat.getSelectedRow(),17);
            tbObat.setValueAt(PersiapanDarah.getSelectedItem().toString(),tbObat.getSelectedRow(),18);
            tbObat.setValueAt(KeteranganPersiapanDarah.getText(),tbObat.getSelectedRow(),19);
            tbObat.setValueAt(PerlengkapanKhusus.getSelectedItem().toString(),tbObat.getSelectedRow(),20);
            tbObat.setValueAt(Radiologi.getSelectedItem().toString(),tbObat.getSelectedRow(),21);
            tbObat.setValueAt(KeteranganRadiologi.getText(),tbObat.getSelectedRow(),22);
            tbObat.setValueAt(EKG.getSelectedItem().toString(),tbObat.getSelectedRow(),23);
            tbObat.setValueAt(KeteranganEKG.getText(),tbObat.getSelectedRow(),24);
            tbObat.setValueAt(USG.getSelectedItem().toString(),tbObat.getSelectedRow(),25);
            tbObat.setValueAt(KeteranganUSG.getText(),tbObat.getSelectedRow(),26);
            tbObat.setValueAt(CTScan.getSelectedItem().toString(),tbObat.getSelectedRow(),27);
            tbObat.setValueAt(KeteranganCTScan.getText(),tbObat.getSelectedRow(),28);
            tbObat.setValueAt(MRI.getSelectedItem().toString(),tbObat.getSelectedRow(),29);
            tbObat.setValueAt(KeteranganMRI.getText(),tbObat.getSelectedRow(),30);
            tbObat.setValueAt(KdPetugasRuangan.getText(),tbObat.getSelectedRow(),31);
            tbObat.setValueAt(NmPetugasRuangan.getText(),tbObat.getSelectedRow(),32);
            tbObat.setValueAt(KdPetugasOK.getText(),tbObat.getSelectedRow(),33);
            tbObat.setValueAt(NmPetugasOK.getText(),tbObat.getSelectedRow(),34);
            emptTeks();
        }
    }

    private void hapus() {
        if(Sequel.queryu2tf("delete from checklist_pre_operasi where no_rawat=? and tanggal=?",2,new String[]{
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
        if(Sequel.menyimpantf("checklist_pre_operasi","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","Data",27,new String[]{
            TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19),SNCN.getText(),Tindakan.getText(),
            KodeDokterBedah.getText(),KodeDokterAnestesi.getText(),Identitas.getSelectedItem().toString(),IjinBedah.getSelectedItem().toString(), 
            IjinAnestesi.getSelectedItem().toString(),IjinTransfusi.getSelectedItem().toString(),AreaOperasi.getSelectedItem().toString(), 
            KeadaanUmum.getSelectedItem().toString(),Radiologi.getSelectedItem().toString(),KeteranganRadiologi.getText(),EKG.getSelectedItem().toString(), 
            KeteranganEKG.getText(),USG.getSelectedItem().toString(),KeteranganUSG.getText(),CTScan.getSelectedItem().toString(),KeteranganCTScan.getText(), 
            MRI.getSelectedItem().toString(),KeteranganMRI.getText(),PersiapanDarah.getSelectedItem().toString(),KeteranganPersiapanDarah.getText(), 
            PerlengkapanKhusus.getSelectedItem().toString(),KdPetugasRuangan.getText(),KdPetugasOK.getText()
        })==true){
            tabMode.addRow(new Object[]{
                TNoRw.getText(),TNoRM.getText(),TPasien.getText(),TglLahir.getText(),JK.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19),
                SNCN.getText(),Tindakan.getText(),KodeDokterBedah.getText(),NamaDokterBedah.getText(),KodeDokterAnestesi.getText(),NamaDokterAnestesi.getText(),Identitas.getSelectedItem().toString(),
                KeadaanUmum.getSelectedItem().toString(),AreaOperasi.getSelectedItem().toString(),IjinBedah.getSelectedItem().toString(),IjinAnestesi.getSelectedItem().toString(),IjinTransfusi.getSelectedItem().toString(),
                PersiapanDarah.getSelectedItem().toString(),KeteranganPersiapanDarah.getText(),PerlengkapanKhusus.getSelectedItem().toString(),Radiologi.getSelectedItem().toString(),KeteranganRadiologi.getText(),
                EKG.getSelectedItem().toString(),KeteranganEKG.getText(),USG.getSelectedItem().toString(),KeteranganUSG.getText(),CTScan.getSelectedItem().toString(),KeteranganCTScan.getText(),
                MRI.getSelectedItem().toString(),KeteranganMRI.getText(),KdPetugasRuangan.getText(),NmPetugasRuangan.getText(),KdPetugasOK.getText(),NmPetugasOK.getText()
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

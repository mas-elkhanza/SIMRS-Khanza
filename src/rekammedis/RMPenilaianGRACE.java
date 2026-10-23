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
public final class RMPenilaianGRACE extends javax.swing.JDialog {
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
    public RMPenilaianGRACE(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(628,674);

        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat", "No.RM", "Nama Pasien", "J.K.", "Tgl.Lahir", "Kode Dokter", "Dokter", "Tgl/Jam Penilaian", "Usia", "Skor Usia", 
            "HR(bpm)", "Skor HR", "SBP (mmHg)", "Skor SBP", "Creatinine (mg/dl)", "Skor Creatinine", "Killip Class", 
            "Skor Killip", "ST Deviation", "Skor ST Deviation", "Elevated Troponin", "Skor Troponin", "Cardiac Arrest", "Skor Cardiac Arrest", 
            "Total GRACE Score", "Kategori Risiko", "Keterangan Dokter"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 27; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(105);
            }else if(i==1){
                column.setPreferredWidth(70);
            }else if(i==2){
                column.setPreferredWidth(150);
            }else if(i==3){
                column.setPreferredWidth(35);
            }else if(i==4){
                column.setPreferredWidth(80);
            }else if(i==5){
                column.setPreferredWidth(80);
            }else if(i==6){
                column.setPreferredWidth(150);
            }else if(i==7){
                column.setPreferredWidth(115);
            }else if(i==8){
                column.setPreferredWidth(35);
            }else if(i==9){
                column.setPreferredWidth(55);
            }else if(i==10){
                column.setPreferredWidth(52);
            }else if(i==11){
                column.setPreferredWidth(48);
            }else if(i==12){
                column.setPreferredWidth(70);
            }else if(i==13){
                column.setPreferredWidth(55);
            }else if(i==14){
                column.setPreferredWidth(100);
            }else if(i==15){
                column.setPreferredWidth(90);
            }else if(i==16){
                column.setPreferredWidth(195);
            }else if(i==17){
                column.setPreferredWidth(60);
            }else if(i==18){
                column.setPreferredWidth(155);
            }else if(i==19){
                column.setPreferredWidth(95);
            }else if(i==20){
                column.setPreferredWidth(99);
            }else if(i==21){
                column.setPreferredWidth(80);
            }else if(i==22){
                column.setPreferredWidth(77);
            }else if(i==23){
                column.setPreferredWidth(104);
            }else if(i==24){
                column.setPreferredWidth(100);
            }else if(i==25){
                column.setPreferredWidth(95);
            }else if(i==26){
                column.setPreferredWidth(220);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());

        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        KdDokter.setDocument(new batasInput((byte)20).getKata(KdDokter));
        TCari.setDocument(new batasInput((int)100).getKata(TCari));
        DenyutJantung.setDocument(new batasInput((byte)3).getOnlyAngka(DenyutJantung));
        Sistolik.setDocument(new batasInput((byte)3).getOnlyAngka(Sistolik));
        Kreatinin.setDocument(new batasInput((byte)5).getOnlyAngka(Kreatinin));
        Keterangan.setDocument(new batasInput((int)255).getKata(Keterangan));
        
        Usia.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {
                hitungSkor();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                hitungSkor();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                hitungSkor();
            }
        });
        
        DenyutJantung.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {
                hitungSkor();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                hitungSkor();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                hitungSkor();
            }
        });
        
        Sistolik.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {
                hitungSkor();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                hitungSkor();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                hitungSkor();
            }
        });
        
        Kreatinin.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {
                hitungSkor();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                hitungSkor();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                hitungSkor();
            }
        });
        
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
        jLabel16 = new widget.Label();
        Tanggal = new widget.Tanggal();
        jLabel23 = new widget.Label();
        KdDokter = new widget.TextBox();
        NmDokter = new widget.TextBox();
        BtnDokter = new widget.Button();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel10 = new widget.Label();
        Usia = new widget.TextBox();
        SkorUsia = new widget.TextBox();
        jLabel13 = new widget.Label();
        DenyutJantung = new widget.TextBox();
        SkorDenyut = new widget.TextBox();
        jLabel17 = new widget.Label();
        Sistolik = new widget.TextBox();
        SkorSistolik = new widget.TextBox();
        jLabel20 = new widget.Label();
        Kreatinin = new widget.TextBox();
        SkorKreatinin = new widget.TextBox();
        jLabel24 = new widget.Label();
        Killip = new widget.ComboBox();
        SkorKillip = new widget.TextBox();
        jLabel26 = new widget.Label();
        DeviasiST = new widget.ComboBox();
        SkorST = new widget.TextBox();
        jLabel28 = new widget.Label();
        Troponin = new widget.ComboBox();
        SkorTroponin = new widget.TextBox();
        jLabel30 = new widget.Label();
        HentiJantung = new widget.ComboBox();
        SkorHenti = new widget.TextBox();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel32 = new widget.Label();
        TotalSkor = new widget.TextBox();
        jLabel33 = new widget.Label();
        Kategori = new widget.TextBox();
        jLabel34 = new widget.Label();
        Keterangan = new widget.TextBox();
        jLabel39 = new widget.Label();
        jLabel18 = new widget.Label();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnFormulir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnFormulir.setText("Formulir Penilaian GRACE Score");
        MnFormulir.setName("MnFormulir"); // NOI18N
        MnFormulir.setPreferredSize(new java.awt.Dimension(205, 26));
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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Penilaian GRACE Score (Acute Coronary Syndrome) ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "24-10-2026" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "24-10-2026" }));
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
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 316));
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
        FormInput.setPreferredSize(new java.awt.Dimension(100, 293));
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

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
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

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel16.setText("I. PENILAIAN");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput.add(jLabel16);
        jLabel16.setBounds(10, 70, 150, 23);

        Tanggal.setForeground(new java.awt.Color(50, 70, 50));
        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "24-10-2026 04:43:37" }));
        Tanggal.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeyPressed(evt);
            }
        });
        FormInput.add(Tanggal);
        Tanggal.setBounds(79, 40, 140, 23);

        jLabel23.setName("jLabel23"); // NOI18N
        jLabel23.setText("Dokter :");
        FormInput.add(jLabel23);
        jLabel23.setBounds(262, 40, 70, 23);

        KdDokter.setEditable(false);
        KdDokter.setHighlighter(null);
        KdDokter.setName("KdDokter"); // NOI18N
        FormInput.add(KdDokter);
        KdDokter.setBounds(336, 40, 110, 23);

        NmDokter.setEditable(false);
        NmDokter.setHighlighter(null);
        NmDokter.setName("NmDokter"); // NOI18N
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

        jSeparator1.setName("jSeparator1"); // NOI18N
        jSeparator1.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator1.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        FormInput.add(jSeparator1);
        jSeparator1.setBounds(0, 70, 807, 1);

        jLabel10.setText("Umur (tahun) :");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(0, 90, 140, 23);

        Usia.setEditable(false);
        Usia.setHighlighter(null);
        Usia.setName("Usia"); // NOI18N
        FormInput.add(Usia);
        Usia.setBounds(144, 90, 50, 23);

        SkorUsia.setEditable(false);
        SkorUsia.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        SkorUsia.setHighlighter(null);
        SkorUsia.setName("SkorUsia"); // NOI18N
        FormInput.add(SkorUsia);
        SkorUsia.setBounds(197, 90, 40, 23);

        jLabel13.setText("Heart Rate (bpm) :");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(0, 120, 140, 23);

        DenyutJantung.setHighlighter(null);
        DenyutJantung.setName("DenyutJantung"); // NOI18N
        DenyutJantung.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DenyutJantungKeyPressed(evt);
            }
        });
        FormInput.add(DenyutJantung);
        DenyutJantung.setBounds(144, 120, 50, 23);

        SkorDenyut.setEditable(false);
        SkorDenyut.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        SkorDenyut.setHighlighter(null);
        SkorDenyut.setName("SkorDenyut"); // NOI18N
        FormInput.add(SkorDenyut);
        SkorDenyut.setBounds(197, 120, 40, 23);

        jLabel17.setText("SBP (mmHg) :");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(0, 150, 140, 23);

        Sistolik.setHighlighter(null);
        Sistolik.setName("Sistolik"); // NOI18N
        Sistolik.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SistolikKeyPressed(evt);
            }
        });
        FormInput.add(Sistolik);
        Sistolik.setBounds(144, 150, 50, 23);

        SkorSistolik.setEditable(false);
        SkorSistolik.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        SkorSistolik.setHighlighter(null);
        SkorSistolik.setName("SkorSistolik"); // NOI18N
        FormInput.add(SkorSistolik);
        SkorSistolik.setBounds(197, 150, 40, 23);

        jLabel20.setText("Creatinine (mg/dl) :");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(0, 180, 140, 23);

        Kreatinin.setHighlighter(null);
        Kreatinin.setName("Kreatinin"); // NOI18N
        Kreatinin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KreatininKeyPressed(evt);
            }
        });
        FormInput.add(Kreatinin);
        Kreatinin.setBounds(144, 180, 50, 23);

        SkorKreatinin.setEditable(false);
        SkorKreatinin.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        SkorKreatinin.setHighlighter(null);
        SkorKreatinin.setName("SkorKreatinin"); // NOI18N
        FormInput.add(SkorKreatinin);
        SkorKreatinin.setBounds(197, 180, 40, 23);

        jLabel24.setText("Killip Class :");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(246, 90, 160, 23);

        Killip.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "I : Tidak Ada Ronkhi Di Paru", "II : Ronkhi Di Setengah Bawah Paru", "III : Ronkhi Hingga Setengah Atas Paru Disertai Edema Paru", "IV : Syok Kardiogenik (TD < 90 mmHg)" }));
        Killip.setName("Killip"); // NOI18N
        Killip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                KillipActionPerformed(evt);
            }
        });
        Killip.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KillipKeyPressed(evt);
            }
        });
        FormInput.add(Killip);
        Killip.setBounds(410, 90, 336, 23);

        SkorKillip.setEditable(false);
        SkorKillip.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        SkorKillip.setHighlighter(null);
        SkorKillip.setName("SkorKillip"); // NOI18N
        FormInput.add(SkorKillip);
        SkorKillip.setBounds(749, 90, 40, 23);

        jLabel26.setText("ST Deviation :");
        jLabel26.setName("jLabel26"); // NOI18N
        FormInput.add(jLabel26);
        jLabel26.setBounds(246, 120, 160, 23);

        DeviasiST.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada Deviasi Signifikan", "ST Elevasi Atau ST Depresi > 1 mm" }));
        DeviasiST.setName("DeviasiST"); // NOI18N
        DeviasiST.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeviasiSTActionPerformed(evt);
            }
        });
        DeviasiST.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DeviasiSTKeyPressed(evt);
            }
        });
        FormInput.add(DeviasiST);
        DeviasiST.setBounds(410, 120, 336, 23);

        SkorST.setEditable(false);
        SkorST.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        SkorST.setHighlighter(null);
        SkorST.setName("SkorST"); // NOI18N
        FormInput.add(SkorST);
        SkorST.setBounds(749, 120, 40, 23);

        jLabel28.setText("Elevated Troponin :");
        jLabel28.setName("jLabel28"); // NOI18N
        FormInput.add(jLabel28);
        jLabel28.setBounds(246, 150, 160, 23);

        Troponin.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Troponin.setName("Troponin"); // NOI18N
        Troponin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TroponinActionPerformed(evt);
            }
        });
        Troponin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TroponinKeyPressed(evt);
            }
        });
        FormInput.add(Troponin);
        Troponin.setBounds(410, 150, 336, 23);

        SkorTroponin.setEditable(false);
        SkorTroponin.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        SkorTroponin.setHighlighter(null);
        SkorTroponin.setName("SkorTroponin"); // NOI18N
        FormInput.add(SkorTroponin);
        SkorTroponin.setBounds(749, 150, 40, 23);

        jLabel30.setText("Cardiac Arrest At Admission :");
        jLabel30.setName("jLabel30"); // NOI18N
        FormInput.add(jLabel30);
        jLabel30.setBounds(246, 180, 160, 23);

        HentiJantung.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        HentiJantung.setName("HentiJantung"); // NOI18N
        HentiJantung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HentiJantungActionPerformed(evt);
            }
        });
        HentiJantung.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HentiJantungKeyPressed(evt);
            }
        });
        FormInput.add(HentiJantung);
        HentiJantung.setBounds(410, 180, 336, 23);

        SkorHenti.setEditable(false);
        SkorHenti.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        SkorHenti.setHighlighter(null);
        SkorHenti.setName("SkorHenti"); // NOI18N
        FormInput.add(SkorHenti);
        SkorHenti.setBounds(749, 180, 40, 23);

        jSeparator2.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator2.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator2.setName("jSeparator2"); // NOI18N
        FormInput.add(jSeparator2);
        jSeparator2.setBounds(0, 240, 807, 1);

        jLabel32.setText("Total Skor :");
        jLabel32.setName("jLabel32"); // NOI18N
        FormInput.add(jLabel32);
        jLabel32.setBounds(600, 210, 120, 23);

        TotalSkor.setEditable(false);
        TotalSkor.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        TotalSkor.setHighlighter(null);
        TotalSkor.setName("TotalSkor"); // NOI18N
        FormInput.add(TotalSkor);
        TotalSkor.setBounds(729, 210, 60, 23);

        jLabel33.setText("Kategori Risiko :");
        jLabel33.setName("jLabel33"); // NOI18N
        FormInput.add(jLabel33);
        jLabel33.setBounds(0, 260, 140, 23);

        Kategori.setEditable(false);
        Kategori.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Kategori.setHighlighter(null);
        Kategori.setName("Kategori"); // NOI18N
        FormInput.add(Kategori);
        Kategori.setBounds(144, 260, 120, 23);

        jLabel34.setText("Keterangan :");
        jLabel34.setName("jLabel34"); // NOI18N
        FormInput.add(jLabel34);
        jLabel34.setBounds(260, 260, 95, 23);

        Keterangan.setHighlighter(null);
        Keterangan.setName("Keterangan"); // NOI18N
        Keterangan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKeyPressed(evt);
            }
        });
        FormInput.add(Keterangan);
        Keterangan.setBounds(359, 260, 430, 23);

        jLabel39.setText("Tanggal :");
        jLabel39.setName("jLabel39"); // NOI18N
        FormInput.add(jLabel39);
        jLabel39.setBounds(0, 40, 75, 23);

        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel18.setText("II. INTERPRETASI");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(10, 240, 240, 23);

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
        }else if(DenyutJantung.getText().trim().equals("")){
            Valid.textKosong(DenyutJantung,"Heart Rate");
        }else if(Sistolik.getText().trim().equals("")){
            Valid.textKosong(Sistolik,"SBP");
        }else if(Kreatinin.getText().trim().equals("")||TotalSkor.getText().trim().equals("")){
            Valid.textKosong(Kreatinin,"Creatinine (angka, contoh 1.2)");
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
        }else if(DenyutJantung.getText().trim().equals("")){
            Valid.textKosong(DenyutJantung,"Heart Rate");
        }else if(Sistolik.getText().trim().equals("")){
            Valid.textKosong(Sistolik,"SBP");
        }else if(Kreatinin.getText().trim().equals("")||TotalSkor.getText().trim().equals("")){
            Valid.textKosong(Kreatinin,"Creatinine (angka, contoh 1.2)");
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
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Dokter</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tgl/Jam Penilaian</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Usia</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Skor Usia</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Heart Rate (bpm)</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Skor Heart Rate</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>SBP (mmHg)</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Skor SBP</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Creatinine (mg/dl)</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Skor Creatinine</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Killip Class</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Skor Killip</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>ST Deviation</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Skor ST Deviation</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Elevated Troponin</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Skor Troponin</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Cardiac Arrest</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Skor Cardiac Arrest</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Total GRACE Score</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kategori Risiko</b></td>"+
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
                                       "<td valign='top'>"+tbObat.getValueAt(i,19).toString()+"</td>"+
                                       "<td valign='top'>"+tbObat.getValueAt(i,20).toString()+"</td>"+
                                       "<td valign='top'>"+tbObat.getValueAt(i,21).toString()+"</td>"+
                                       "<td valign='top'>"+tbObat.getValueAt(i,22).toString()+"</td>"+
                                       "<td valign='top'>"+tbObat.getValueAt(i,23).toString()+"</td>"+
                                       "<td valign='top'>"+tbObat.getValueAt(i,24).toString()+"</td>"+
                                       "<td valign='top'>"+tbObat.getValueAt(i,25).toString()+"</td>"+
                                       "<td valign='top'>"+tbObat.getValueAt(i,26).toString()+"</td>"+
                                    "</tr>");
                            }
                            LoadHTML.setText(
                                "<html>"+
                                  "<table width='2510px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
                                   htmlContent.toString()+
                                  "</table>"+
                                "</html>"
                            );

                            f = new File("DataPenilaianGRACE.html");            
                            bw = new BufferedWriter(new FileWriter(f));            
                            bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                                        "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                                        "<table width='2510px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                            "<tr class='isi2'>"+
                                                "<td valign='top' align='center'>"+
                                                    "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                                    akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                                    akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                                    "<font size='2' face='Tahoma'>DATA PENILAIAN GRACE<br><br></font>"+        
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
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>J.K.</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tgl.Lahir</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kode Dokter</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Dokter</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tgl/Jam Penilaian</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Usia</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Skor Usia</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Heart Rate (bpm)</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Skor Heart Rate</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>SBP (mmHg)</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Skor SBP</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Creatinine (mg/dl)</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Skor Creatinine</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Killip Class</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Skor Killip</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>ST Deviation</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Skor ST Deviation</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Elevated Troponin</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Skor Troponin</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Cardiac Arrest</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Skor Cardiac Arrest</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Total GRACE Score</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kategori Risiko</b></td>"+
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
                                       "<td valign='top'>"+tbObat.getValueAt(i,19).toString()+"</td>"+
                                       "<td valign='top'>"+tbObat.getValueAt(i,20).toString()+"</td>"+
                                       "<td valign='top'>"+tbObat.getValueAt(i,21).toString()+"</td>"+
                                       "<td valign='top'>"+tbObat.getValueAt(i,22).toString()+"</td>"+
                                       "<td valign='top'>"+tbObat.getValueAt(i,23).toString()+"</td>"+
                                       "<td valign='top'>"+tbObat.getValueAt(i,24).toString()+"</td>"+
                                       "<td valign='top'>"+tbObat.getValueAt(i,25).toString()+"</td>"+
                                       "<td valign='top'>"+tbObat.getValueAt(i,26).toString()+"</td>"+
                                    "</tr>");
                            }
                            LoadHTML.setText(
                                "<html>"+
                                  "<table width='2510px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
                                   htmlContent.toString()+
                                  "</table>"+
                                "</html>"
                            );

                            f = new File("DataPenilaianGRACE.wps");            
                            bw = new BufferedWriter(new FileWriter(f));            
                            bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                                        "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                                        "<table width='2510px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                            "<tr class='isi2'>"+
                                                "<td valign='top' align='center'>"+
                                                    "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                                    akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                                    akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                                    "<font size='2' face='Tahoma'>DATA PENILAIAN GRACE<br><br></font>"+        
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
                                "\"No.Rawat\";\"No.RM\";\"Nama Pasien\";\"J.K.\";\"Tgl.Lahir\";\"Kode Dokter\";\"Dokter\";\"Tgl/Jam Penilaian\";\"Usia\";\"Skor Usia\";\"Heart Rate (bpm)\";\"Skor Heart Rate\";\"SBP (mmHg)\";\"Skor SBP\";\"Creatinine (mg/dl)\";\"Skor Creatinine\";\"Killip Class\";\"Skor Killip\";\"ST Deviation\";\"Skor ST Deviation\";\"Elevated Troponin\";\"Skor Troponin\";\"Cardiac Arrest\";\"Skor Cardiac Arrest\";\"Total GRACE Score\";\"Kategori Risiko\";\"Keterangan Dokter\"\n"
                            ); 
                            for (i = 0; i < tabMode.getRowCount(); i++) {
                                htmlContent.append(
                                    "\""+tbObat.getValueAt(i,0).toString()+"\";\""+tbObat.getValueAt(i,1).toString()+"\";\""+tbObat.getValueAt(i,2).toString()+"\";\""+tbObat.getValueAt(i,3).toString()+"\";\""+tbObat.getValueAt(i,4).toString()+"\";\""+tbObat.getValueAt(i,5).toString()+"\";\""+tbObat.getValueAt(i,6).toString()+"\";\""+tbObat.getValueAt(i,7).toString()+"\";\""+tbObat.getValueAt(i,8).toString()+"\";\""+tbObat.getValueAt(i,9).toString()+"\";\""+tbObat.getValueAt(i,10).toString()+"\";\""+tbObat.getValueAt(i,11).toString()+"\";\""+tbObat.getValueAt(i,12).toString()+"\";\""+tbObat.getValueAt(i,13).toString()+"\";\""+tbObat.getValueAt(i,14).toString()+"\";\""+tbObat.getValueAt(i,15).toString()+"\";\""+tbObat.getValueAt(i,16).toString()+"\";\""+tbObat.getValueAt(i,17).toString()+"\";\""+tbObat.getValueAt(i,18).toString()+"\";\""+tbObat.getValueAt(i,19).toString()+"\";\""+tbObat.getValueAt(i,20).toString()+"\";\""+tbObat.getValueAt(i,21).toString()+"\";\""+tbObat.getValueAt(i,22).toString()+"\";\""+tbObat.getValueAt(i,23).toString()+"\";\""+tbObat.getValueAt(i,24).toString()+"\";\""+tbObat.getValueAt(i,25).toString()+"\";\""+tbObat.getValueAt(i,26).toString()+"\"\n"
                                );
                            }
                            f = new File("DataPenilaianGRACE.csv");            
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
                "rptFormulirPenilaianGRACE.jasper","report","::[ Formulir Penilaian GRACE ]::",
                "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,penilaian_grace_scor.kd_dokter,dokter.nm_dokter,penilaian_grace_scor.tanggal,"+
                "penilaian_grace_scor.usia,penilaian_grace_scor.skor_usia,penilaian_grace_scor.denyut_jantung,penilaian_grace_scor.skor_denyut_jantung,penilaian_grace_scor.sistolik,penilaian_grace_scor.skor_sistolik,"+
                "penilaian_grace_scor.kreatinin,penilaian_grace_scor.skor_kreatinin,penilaian_grace_scor.killip,penilaian_grace_scor.skor_killip,penilaian_grace_scor.deviasi_st,penilaian_grace_scor.skor_deviasi_st,"+
                "penilaian_grace_scor.troponin,penilaian_grace_scor.skor_troponin,penilaian_grace_scor.henti_jantung,penilaian_grace_scor.skor_henti_jantung,penilaian_grace_scor.skor,penilaian_grace_scor.kategori,"+
                "penilaian_grace_scor.keterangan from penilaian_grace_scor inner join reg_periksa on penilaian_grace_scor.no_rawat=reg_periksa.no_rawat inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join dokter on penilaian_grace_scor.kd_dokter=dokter.kd_dokter where penilaian_grace_scor.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'",
                param
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

    private void KillipActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_KillipActionPerformed
        hitungSkor();
    }//GEN-LAST:event_KillipActionPerformed

    private void DeviasiSTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeviasiSTActionPerformed
        hitungSkor();
    }//GEN-LAST:event_DeviasiSTActionPerformed

    private void TroponinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TroponinActionPerformed
        hitungSkor();
    }//GEN-LAST:event_TroponinActionPerformed

    private void HentiJantungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HentiJantungActionPerformed
        hitungSkor();
    }//GEN-LAST:event_HentiJantungActionPerformed

    private void TPasienKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TPasienKeyPressed
        Valid.pindah(evt,TCari,BtnSimpan);
    }//GEN-LAST:event_TPasienKeyPressed

    private void TanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKeyPressed
        Valid.pindah(evt,TCari,BtnDokter);
    }//GEN-LAST:event_TanggalKeyPressed

    private void DenyutJantungKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DenyutJantungKeyPressed
        Valid.pindah(evt,BtnDokter,Sistolik);
    }//GEN-LAST:event_DenyutJantungKeyPressed

    private void SistolikKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SistolikKeyPressed
        Valid.pindah(evt,DenyutJantung,Kreatinin);
    }//GEN-LAST:event_SistolikKeyPressed

    private void KreatininKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KreatininKeyPressed
        Valid.pindah(evt,Sistolik,Killip);
    }//GEN-LAST:event_KreatininKeyPressed

    private void KillipKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KillipKeyPressed
        Valid.pindah(evt,Kreatinin,DeviasiST);
    }//GEN-LAST:event_KillipKeyPressed

    private void DeviasiSTKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DeviasiSTKeyPressed
        Valid.pindah(evt,Killip,Troponin);
    }//GEN-LAST:event_DeviasiSTKeyPressed

    private void TroponinKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TroponinKeyPressed
        Valid.pindah(evt,DeviasiST,HentiJantung);
    }//GEN-LAST:event_TroponinKeyPressed

    private void HentiJantungKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HentiJantungKeyPressed
        Valid.pindah(evt,Troponin,Keterangan);
    }//GEN-LAST:event_HentiJantungKeyPressed

    private void KeteranganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKeyPressed
        Valid.pindah(evt,HentiJantung,BtnSimpan);
    }//GEN-LAST:event_KeteranganKeyPressed

    private void BtnDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokterKeyPressed
        Valid.pindah(evt,Tanggal,DenyutJantung);
    }//GEN-LAST:event_BtnDokterKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMPenilaianGRACE dialog = new RMPenilaianGRACE(new javax.swing.JFrame(), true);
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
    private widget.TextBox DenyutJantung;
    private widget.ComboBox DeviasiST;
    private widget.PanelBiasa FormInput;
    private widget.ComboBox HentiJantung;
    private widget.TextBox JK;
    private widget.TextBox Kategori;
    private widget.TextBox KdDokter;
    private widget.TextBox Keterangan;
    private widget.ComboBox Killip;
    private widget.TextBox Kreatinin;
    private widget.Label LCount;
    private widget.editorpane LoadHTML;
    private widget.MenuItem MnFormulir;
    private widget.TextBox NmDokter;
    private javax.swing.JPanel PanelInput;
    private widget.ScrollPane Scroll;
    private widget.TextBox Sistolik;
    private widget.TextBox SkorDenyut;
    private widget.TextBox SkorHenti;
    private widget.TextBox SkorKillip;
    private widget.TextBox SkorKreatinin;
    private widget.TextBox SkorST;
    private widget.TextBox SkorSistolik;
    private widget.TextBox SkorTroponin;
    private widget.TextBox SkorUsia;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.Tanggal Tanggal;
    private widget.TextBox TanggalRegistrasi;
    private widget.TextBox TglLahir;
    private widget.TextBox TotalSkor;
    private widget.ComboBox Troponin;
    private widget.TextBox Usia;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel10;
    private widget.Label jLabel13;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
    private widget.Label jLabel18;
    private widget.Label jLabel19;
    private widget.Label jLabel20;
    private widget.Label jLabel23;
    private widget.Label jLabel24;
    private widget.Label jLabel26;
    private widget.Label jLabel28;
    private widget.Label jLabel30;
    private widget.Label jLabel32;
    private widget.Label jLabel33;
    private widget.Label jLabel34;
    private widget.Label jLabel39;
    private widget.Label jLabel4;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private widget.Label jLabelSd;
    private javax.swing.JPanel jPanel3;
    private widget.PopupMenu jPopupMenu1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
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
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,penilaian_grace_scor.kd_dokter,dokter.nm_dokter,penilaian_grace_scor.tanggal,penilaian_grace_scor.usia,penilaian_grace_scor.skor_usia,"+
                    "penilaian_grace_scor.denyut_jantung,penilaian_grace_scor.skor_denyut_jantung,penilaian_grace_scor.sistolik,penilaian_grace_scor.skor_sistolik,penilaian_grace_scor.kreatinin,penilaian_grace_scor.skor_kreatinin,"+
                    "penilaian_grace_scor.killip,penilaian_grace_scor.skor_killip,penilaian_grace_scor.deviasi_st,penilaian_grace_scor.skor_deviasi_st,penilaian_grace_scor.troponin,penilaian_grace_scor.skor_troponin,penilaian_grace_scor.henti_jantung,"+
                    "penilaian_grace_scor.skor_henti_jantung,penilaian_grace_scor.skor,penilaian_grace_scor.kategori,penilaian_grace_scor.keterangan from penilaian_grace_scor inner join reg_periksa on penilaian_grace_scor.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join dokter on penilaian_grace_scor.kd_dokter=dokter.kd_dokter where penilaian_grace_scor.tanggal between ? and ? order by penilaian_grace_scor.tanggal"
                );
            }else{
                ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,penilaian_grace_scor.kd_dokter,dokter.nm_dokter,penilaian_grace_scor.tanggal,penilaian_grace_scor.usia,penilaian_grace_scor.skor_usia,"+
                    "penilaian_grace_scor.denyut_jantung,penilaian_grace_scor.skor_denyut_jantung,penilaian_grace_scor.sistolik,penilaian_grace_scor.skor_sistolik,penilaian_grace_scor.kreatinin,penilaian_grace_scor.skor_kreatinin,"+
                    "penilaian_grace_scor.killip,penilaian_grace_scor.skor_killip,penilaian_grace_scor.deviasi_st,penilaian_grace_scor.skor_deviasi_st,penilaian_grace_scor.troponin,penilaian_grace_scor.skor_troponin,penilaian_grace_scor.henti_jantung,"+
                    "penilaian_grace_scor.skor_henti_jantung,penilaian_grace_scor.skor,penilaian_grace_scor.kategori,penilaian_grace_scor.keterangan from penilaian_grace_scor inner join reg_periksa on penilaian_grace_scor.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join dokter on penilaian_grace_scor.kd_dokter=dokter.kd_dokter where penilaian_grace_scor.tanggal between ? and ? and (reg_periksa.no_rawat like ? or "+
                    "pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or penilaian_grace_scor.kd_dokter like ? or dokter.nm_dokter like ? or penilaian_grace_scor.kategori like ?) order by penilaian_grace_scor.tanggal"
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
                }
                    
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new Object[]{
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("jk"),rs.getString("tgl_lahir"),rs.getString("kd_dokter"),rs.getString("nm_dokter"),
                        rs.getString("tanggal"),rs.getString("usia"),rs.getString("skor_usia"),rs.getString("denyut_jantung"),rs.getString("skor_denyut_jantung"),rs.getString("sistolik"),rs.getString("skor_sistolik"),
                        rs.getString("kreatinin"),rs.getString("skor_kreatinin"),rs.getString("killip"),rs.getString("skor_killip"),rs.getString("deviasi_st"),rs.getString("skor_deviasi_st"),rs.getString("troponin"),
                        rs.getString("skor_troponin"),rs.getString("henti_jantung"),rs.getString("skor_henti_jantung"),rs.getString("skor"),rs.getString("kategori"),rs.getString("keterangan")
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
        DenyutJantung.setText("");
        Sistolik.setText("");
        Kreatinin.setText("");
        Killip.setSelectedIndex(0);
        DeviasiST.setSelectedIndex(0);
        Troponin.setSelectedIndex(0);
        HentiJantung.setSelectedIndex(0);
        Keterangan.setText("");
        hitungSkor();
        Tanggal.setDate(new Date());
        DenyutJantung.requestFocus();
    } 

    private void hitungSkor() {
        int sKillip=Killip.getSelectedIndex()==0?0:Killip.getSelectedIndex()==1?21:Killip.getSelectedIndex()==2?43:64;
        int sST=DeviasiST.getSelectedIndex()==1?30:0;
        int sTroponin=Troponin.getSelectedIndex()==1?15:0;
        int sHenti=HentiJantung.getSelectedIndex()==1?43:0;
        SkorKillip.setText(""+sKillip);
        SkorST.setText(""+sST);
        SkorTroponin.setText(""+sTroponin);
        SkorHenti.setText(""+sHenti);
        
        Integer sUsia=0,sDenyut=0,sSistolik=0,sKreatinin=0;
        try {
            int umur=Integer.parseInt(Usia.getText().trim());
            sUsia=umur<40?0:umur<50?18:umur<60?36:umur<70?55:umur<80?73:91;
        } catch (Exception e) {
            sUsia=0;
        }
        try {
            int nadi=Integer.parseInt(DenyutJantung.getText().trim());
            sDenyut=nadi<70?0:nadi<90?7:nadi<110?13:nadi<150?23:nadi<200?36:46;
        } catch (Exception e) {
            sDenyut=0;
        }
        try {
            int td=Integer.parseInt(Sistolik.getText().trim());
            sSistolik=td<80?63:td<100?58:td<120?47:td<140?37:td<160?26:td<200?11:0;
        } catch (Exception e) {
            sSistolik=0;
        }
        try {
            double kreat=Double.parseDouble(Kreatinin.getText());
            sKreatinin=kreat<0.4?2:kreat<0.8?5:kreat<1.2?8:kreat<1.6?11:kreat<2.0?14:kreat<4.0?23:31;
        } catch (Exception e) {
            sKreatinin=0;
        }
        SkorUsia.setText(""+sUsia);
        SkorDenyut.setText(""+sDenyut);
        SkorSistolik.setText(""+sSistolik);
        SkorKreatinin.setText(""+sKreatinin);
        
        try {
            int skor=sUsia+sDenyut+sSistolik+sKreatinin+sKillip+sST+sTroponin+sHenti;
            TotalSkor.setText(""+skor);
            Kategori.setText(skor<109?"Low Risk":skor<=140?"Intermediate Risk":"High Risk");
        } catch (Exception e) {
            TotalSkor.setText("0");
            Kategori.setText("Low Risk");
        }
    }

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            JK.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString());
            Usia.setText(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            SkorUsia.setText(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            DenyutJantung.setText(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            SkorDenyut.setText(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            Sistolik.setText(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            SkorSistolik.setText(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            Kreatinin.setText(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            SkorKreatinin.setText(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
            Killip.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            SkorKillip.setText(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());
            DeviasiST.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
            SkorST.setText(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
            Troponin.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString());
            SkorTroponin.setText(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString());
            HentiJantung.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString());
            SkorHenti.setText(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString());
            TotalSkor.setText(tbObat.getValueAt(tbObat.getSelectedRow(),24).toString());
            Kategori.setText(tbObat.getValueAt(tbObat.getSelectedRow(),25).toString());
            Keterangan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),26).toString());
            Valid.SetTgl2(Tanggal,tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());
        }
    }
    
    private void isRawat() {
        try {
            ps=koneksi.prepareStatement(
                "select reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,timestampdiff(year,pasien.tgl_lahir,curdate()) as usia,"+
                "reg_periksa.tgl_registrasi,reg_periksa.jam_reg from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "where reg_periksa.no_rawat=?"
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
                    Usia.setText(rs.getString("usia"));
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
            if(internalFrame1.getHeight()>488){
                ChkInput.setVisible(false);
                PanelInput.setPreferredSize(new Dimension(WIDTH,316));
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
        BtnSimpan.setEnabled(akses.getpenilaian_grace_scor());
        BtnHapus.setEnabled(akses.getpenilaian_grace_scor());
        BtnEdit.setEnabled(akses.getpenilaian_grace_scor());
        BtnPrint.setEnabled(akses.getpenilaian_grace_scor()); 
        
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
        if(Sequel.mengedittf("penilaian_grace_scor","no_rawat=?","no_rawat=?,tanggal=?,usia=?,skor_usia=?,denyut_jantung=?,skor_denyut_jantung=?,sistolik=?,skor_sistolik=?,kreatinin=?,"+
                "skor_kreatinin=?,killip=?,skor_killip=?,deviasi_st=?,skor_deviasi_st=?,troponin=?,skor_troponin=?,henti_jantung=?,skor_henti_jantung=?,skor=?,kategori=?,keterangan=?,kd_dokter=?",23,new String[]{
                TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19),Usia.getText(),SkorUsia.getText(),DenyutJantung.getText(),SkorDenyut.getText(),
                Sistolik.getText(),SkorSistolik.getText(),Kreatinin.getText(),SkorKreatinin.getText(),Killip.getSelectedItem().toString(),SkorKillip.getText(),DeviasiST.getSelectedItem().toString(),SkorST.getText(),
                Troponin.getSelectedItem().toString(),SkorTroponin.getText(),HentiJantung.getSelectedItem().toString(),SkorHenti.getText(),TotalSkor.getText(),Kategori.getText(),Keterangan.getText(),KdDokter.getText(),
                tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
            })==true){
               tbObat.setValueAt(TNoRw.getText(),tbObat.getSelectedRow(),0);
               tbObat.setValueAt(TNoRM.getText(),tbObat.getSelectedRow(),1);
               tbObat.setValueAt(TPasien.getText(),tbObat.getSelectedRow(),2);
               tbObat.setValueAt(JK.getText(),tbObat.getSelectedRow(),3);
               tbObat.setValueAt(TglLahir.getText(),tbObat.getSelectedRow(),4);
               tbObat.setValueAt(KdDokter.getText(),tbObat.getSelectedRow(),5);
               tbObat.setValueAt(NmDokter.getText(),tbObat.getSelectedRow(),6);
               tbObat.setValueAt(Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19),tbObat.getSelectedRow(),7);
               tbObat.setValueAt(Usia.getText(),tbObat.getSelectedRow(),8);
               tbObat.setValueAt(SkorUsia.getText(),tbObat.getSelectedRow(),9);
               tbObat.setValueAt(DenyutJantung.getText(),tbObat.getSelectedRow(),10);
               tbObat.setValueAt(SkorDenyut.getText(),tbObat.getSelectedRow(),11);
               tbObat.setValueAt(Sistolik.getText(),tbObat.getSelectedRow(),12);
               tbObat.setValueAt(SkorSistolik.getText(),tbObat.getSelectedRow(),13);
               tbObat.setValueAt(Kreatinin.getText().replace(",","."),tbObat.getSelectedRow(),14);
               tbObat.setValueAt(SkorKreatinin.getText(),tbObat.getSelectedRow(),15);
               tbObat.setValueAt(Killip.getSelectedItem().toString(),tbObat.getSelectedRow(),16);
               tbObat.setValueAt(SkorKillip.getText(),tbObat.getSelectedRow(),17);
               tbObat.setValueAt(DeviasiST.getSelectedItem().toString(),tbObat.getSelectedRow(),18);
               tbObat.setValueAt(SkorST.getText(),tbObat.getSelectedRow(),19);
               tbObat.setValueAt(Troponin.getSelectedItem().toString(),tbObat.getSelectedRow(),20);
               tbObat.setValueAt(SkorTroponin.getText(),tbObat.getSelectedRow(),21);
               tbObat.setValueAt(HentiJantung.getSelectedItem().toString(),tbObat.getSelectedRow(),22);
               tbObat.setValueAt(SkorHenti.getText(),tbObat.getSelectedRow(),23);
               tbObat.setValueAt(TotalSkor.getText(),tbObat.getSelectedRow(),24);
               tbObat.setValueAt(Kategori.getText(),tbObat.getSelectedRow(),25);
               tbObat.setValueAt(Keterangan.getText(),tbObat.getSelectedRow(),26);
               emptTeks();
        }
    }

    private void hapus() {
        if(Sequel.queryu2tf("delete from penilaian_grace_scor where no_rawat=?",1,new String[]{
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
        if(Sequel.menyimpantf("penilaian_grace_scor","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","Data",22,new String[]{
            TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19),
            Usia.getText(),SkorUsia.getText(),DenyutJantung.getText(),SkorDenyut.getText(),Sistolik.getText(),SkorSistolik.getText(),
            Kreatinin.getText(),SkorKreatinin.getText(),Killip.getSelectedItem().toString(),SkorKillip.getText(),DeviasiST.getSelectedItem().toString(),
            SkorST.getText(),Troponin.getSelectedItem().toString(),SkorTroponin.getText(),HentiJantung.getSelectedItem().toString(),SkorHenti.getText(),
            TotalSkor.getText(),Kategori.getText(),Keterangan.getText(),KdDokter.getText()
        })==true){
            tabMode.addRow(new Object[]{
                TNoRw.getText(),TNoRM.getText(),TPasien.getText(),JK.getText(),TglLahir.getText(),KdDokter.getText(),NmDokter.getText(),
                Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19),Usia.getText(),
                SkorUsia.getText(),DenyutJantung.getText(),SkorDenyut.getText(),Sistolik.getText(),SkorSistolik.getText(),Kreatinin.getText(),
                SkorKreatinin.getText(),Killip.getSelectedItem().toString(),SkorKillip.getText(),DeviasiST.getSelectedItem().toString(),
                SkorST.getText(),Troponin.getSelectedItem().toString(),SkorTroponin.getText(),HentiJantung.getSelectedItem().toString(),
                SkorHenti.getText(),TotalSkor.getText(),Kategori.getText(),Keterangan.getText()
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

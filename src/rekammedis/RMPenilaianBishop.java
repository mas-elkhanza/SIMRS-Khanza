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
public final class RMPenilaianBishop extends javax.swing.JDialog {
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
    public RMPenilaianBishop(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(628,674);

        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat", "No.RM", "Nama Pasien", "J.K.", "Tgl.Lahir", "Kode Dokter", "DPJP", "Tanggal", "Pembukaan (Dilatasi)", 
            "Skor Pembukaan", "Pendataran (Effacement)", "Skor Pendataran", "Station", "Skor Station", "Konsistensi Serviks", "Skor Konsistensi", 
            "Posisi Serviks", "Skor Posisi", "Total Skor Bishop", "Kategori", "Keterangan Dokter"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 21; i++) {
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
                column.setPreferredWidth(110);
            }else if(i==9){
                column.setPreferredWidth(90);
            }else if(i==10){
                column.setPreferredWidth(125);
            }else if(i==11){
                column.setPreferredWidth(90);
            }else if(i==12){
                column.setPreferredWidth(90);
            }else if(i==13){
                column.setPreferredWidth(70);
            }else if(i==14){
                column.setPreferredWidth(115);
            }else if(i==15){
                column.setPreferredWidth(90);
            }else if(i==16){
                column.setPreferredWidth(80);
            }else if(i==17){
                column.setPreferredWidth(65);
            }else if(i==18){
                column.setPreferredWidth(95);
            }else if(i==19){
                column.setPreferredWidth(80);
            }else if(i==20){
                column.setPreferredWidth(240);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());

        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        KdDokter.setDocument(new batasInput((byte)20).getKata(KdDokter));
        TCari.setDocument(new batasInput((int)100).getKata(TCari));
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
        jLabel16 = new widget.Label();
        Tanggal = new widget.Tanggal();
        jLabel23 = new widget.Label();
        KdDokter = new widget.TextBox();
        NmDokter = new widget.TextBox();
        BtnDokter = new widget.Button();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel10 = new widget.Label();
        Pembukaan = new widget.ComboBox();
        SkorPembukaan = new widget.TextBox();
        jLabel13 = new widget.Label();
        Pendataran = new widget.ComboBox();
        SkorPendataran = new widget.TextBox();
        jLabel17 = new widget.Label();
        Station = new widget.ComboBox();
        SkorStation = new widget.TextBox();
        jLabel20 = new widget.Label();
        Konsistensi = new widget.ComboBox();
        SkorKonsistensi = new widget.TextBox();
        jLabel24 = new widget.Label();
        Posisi = new widget.ComboBox();
        SkorPosisi = new widget.TextBox();
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

        MnFormulir.setName("MnFormulir"); // NOI18N
        MnFormulir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnFormulir.setText("Formulir Penilaian Skor Bishop");
        MnFormulir.setPreferredSize(new java.awt.Dimension(230, 26));
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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Penilaian Skor BISHOP (Kesiapan Serviks) ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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

        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "02-11-2026" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass9.add(DTPCari1);

        jLabelSd.setName("jLabelSd"); // NOI18N
        jLabelSd.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelSd.setText("s.d.");
        jLabelSd.setPreferredSize(new java.awt.Dimension(23, 23));
        panelGlass9.add(jLabelSd);

        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "02-11-2026" }));
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
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 256));
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
        FormInput.setPreferredSize(new java.awt.Dimension(100, 233));
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

        TNoRM.setName("TNoRM"); // NOI18N
        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
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

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel16.setText("I. PENILAIAN SERVIKS");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput.add(jLabel16);
        jLabel16.setBounds(10, 70, 250, 23);

        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.setForeground(new java.awt.Color(50, 70, 50));
        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "02-11-2026 12:37:23" }));
        Tanggal.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeyPressed(evt);
            }
        });
        FormInput.add(Tanggal);
        Tanggal.setBounds(79, 40, 140, 23);

        jLabel23.setName("jLabel23"); // NOI18N
        jLabel23.setText("DPJP :");
        FormInput.add(jLabel23);
        jLabel23.setBounds(262, 40, 70, 23);

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

        jSeparator1.setName("jSeparator1"); // NOI18N
        jSeparator1.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator1.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        FormInput.add(jSeparator1);
        jSeparator1.setBounds(0, 70, 807, 1);

        jLabel10.setText("Pembukaan (Dilatasi) :");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(0, 90, 180, 23);

        Pembukaan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0 cm (Tertutup)", "1-2 cm", "3-4 cm", ">= 5 cm" }));
        Pembukaan.setName("Pembukaan"); // NOI18N
        Pembukaan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PembukaanActionPerformed(evt);
            }
        });
        Pembukaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PembukaanKeyPressed(evt);
            }
        });
        FormInput.add(Pembukaan);
        Pembukaan.setBounds(184, 90, 150, 23);

        SkorPembukaan.setEditable(false);
        SkorPembukaan.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        SkorPembukaan.setHighlighter(null);
        SkorPembukaan.setName("SkorPembukaan"); // NOI18N
        FormInput.add(SkorPembukaan);
        SkorPembukaan.setBounds(337, 90, 40, 23);

        jLabel13.setText("Pendataran (Effacement) :");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(0, 120, 180, 23);

        Pendataran.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0-30 %", "40-50 %", "60-70 %", ">= 80 %" }));
        Pendataran.setName("Pendataran"); // NOI18N
        Pendataran.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PendataranActionPerformed(evt);
            }
        });
        Pendataran.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PendataranKeyPressed(evt);
            }
        });
        FormInput.add(Pendataran);
        Pendataran.setBounds(184, 120, 150, 23);

        SkorPendataran.setEditable(false);
        SkorPendataran.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        SkorPendataran.setHighlighter(null);
        SkorPendataran.setName("SkorPendataran"); // NOI18N
        FormInput.add(SkorPendataran);
        SkorPendataran.setBounds(337, 120, 40, 23);

        jLabel17.setText("Station (Penurunan Kepala) :");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(0, 150, 180, 23);

        Station.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-3", "-2", "-1 Atau 0", "+1 Atau +2" }));
        Station.setName("Station"); // NOI18N
        Station.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StationActionPerformed(evt);
            }
        });
        Station.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                StationKeyPressed(evt);
            }
        });
        FormInput.add(Station);
        Station.setBounds(184, 150, 150, 23);

        SkorStation.setEditable(false);
        SkorStation.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        SkorStation.setHighlighter(null);
        SkorStation.setName("SkorStation"); // NOI18N
        FormInput.add(SkorStation);
        SkorStation.setBounds(337, 150, 40, 23);

        jLabel20.setText("Konsistensi Serviks :");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(412, 90, 180, 23);

        Konsistensi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Keras", "Sedang", "Lunak" }));
        Konsistensi.setName("Konsistensi"); // NOI18N
        Konsistensi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                KonsistensiActionPerformed(evt);
            }
        });
        Konsistensi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KonsistensiKeyPressed(evt);
            }
        });
        FormInput.add(Konsistensi);
        Konsistensi.setBounds(596, 90, 150, 23);

        SkorKonsistensi.setEditable(false);
        SkorKonsistensi.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        SkorKonsistensi.setHighlighter(null);
        SkorKonsistensi.setName("SkorKonsistensi"); // NOI18N
        FormInput.add(SkorKonsistensi);
        SkorKonsistensi.setBounds(749, 90, 40, 23);

        jLabel24.setText("Posisi Serviks :");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(412, 120, 180, 23);

        Posisi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Posterior", "Tengah", "Anterior" }));
        Posisi.setName("Posisi"); // NOI18N
        Posisi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PosisiActionPerformed(evt);
            }
        });
        Posisi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PosisiKeyPressed(evt);
            }
        });
        FormInput.add(Posisi);
        Posisi.setBounds(596, 120, 150, 23);

        SkorPosisi.setEditable(false);
        SkorPosisi.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        SkorPosisi.setHighlighter(null);
        SkorPosisi.setName("SkorPosisi"); // NOI18N
        FormInput.add(SkorPosisi);
        SkorPosisi.setBounds(749, 120, 40, 23);

        jSeparator2.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator2.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator2.setName("jSeparator2"); // NOI18N
        FormInput.add(jSeparator2);
        jSeparator2.setBounds(0, 180, 807, 1);

        jLabel32.setText("Total Skor :");
        jLabel32.setName("jLabel32"); // NOI18N
        FormInput.add(jLabel32);
        jLabel32.setBounds(605, 150, 120, 23);

        TotalSkor.setEditable(false);
        TotalSkor.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        TotalSkor.setHighlighter(null);
        TotalSkor.setName("TotalSkor"); // NOI18N
        FormInput.add(TotalSkor);
        TotalSkor.setBounds(729, 150, 60, 23);

        jLabel33.setText("Kategori Serviks :");
        jLabel33.setName("jLabel33"); // NOI18N
        FormInput.add(jLabel33);
        jLabel33.setBounds(0, 200, 125, 23);

        Kategori.setEditable(false);
        Kategori.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Kategori.setHighlighter(null);
        Kategori.setName("Kategori"); // NOI18N
        FormInput.add(Kategori);
        Kategori.setBounds(129, 200, 120, 23);

        jLabel34.setText("Keterangan :");
        jLabel34.setName("jLabel34"); // NOI18N
        FormInput.add(jLabel34);
        jLabel34.setBounds(260, 200, 95, 23);

        Keterangan.setHighlighter(null);
        Keterangan.setName("Keterangan"); // NOI18N
        Keterangan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKeyPressed(evt);
            }
        });
        FormInput.add(Keterangan);
        Keterangan.setBounds(359, 200, 430, 23);

        jLabel39.setName("jLabel39"); // NOI18N
        jLabel39.setText("Tanggal :");
        FormInput.add(jLabel39);
        jLabel39.setBounds(0, 40, 75, 23);

        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel18.setText("II. KATEGORI & KETERANGAN");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(10, 180, 200, 23);

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
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tgl/Jam Penilaian</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Pembukaan (Dilatasi)</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Skor Pembukaan</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Pendataran (Effacement)</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Skor Pendataran</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Station</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Skor Station</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Konsistensi Serviks</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Skor Konsistensi</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Posisi Serviks</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Skor Posisi</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Total Skor Bishop</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kategori</b></td>"+
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
                                    "</tr>");
                            }
                            LoadHTML.setText(
                                "<html>"+
                                  "<table width='2265px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
                                   htmlContent.toString()+
                                  "</table>"+
                                "</html>"
                            );

                            f = new File("DataPenilaianBishop.html");            
                            bw = new BufferedWriter(new FileWriter(f));            
                            bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                                        "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                                        "<table width='2265px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                            "<tr class='isi2'>"+
                                                "<td valign='top' align='center'>"+
                                                    "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                                    akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                                    akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                                    "<font size='2' face='Tahoma'>DATA PENILAIAN SKOR BISHOP<br><br></font>"+        
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
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>DPJP</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tgl/Jam Penilaian</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Pembukaan (Dilatasi)</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Skor Pembukaan</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Pendataran (Effacement)</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Skor Pendataran</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Station</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Skor Station</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Konsistensi Serviks</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Skor Konsistensi</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Posisi Serviks</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Skor Posisi</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Total Skor Bishop</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kategori</b></td>"+
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
                                    "</tr>");
                            }
                            LoadHTML.setText(
                                "<html>"+
                                  "<table width='2265px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
                                   htmlContent.toString()+
                                  "</table>"+
                                "</html>"
                            );

                            f = new File("DataPenilaianBishop.wps");            
                            bw = new BufferedWriter(new FileWriter(f));            
                            bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                                        "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                                        "<table width='2265px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                            "<tr class='isi2'>"+
                                                "<td valign='top' align='center'>"+
                                                    "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                                    akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                                    akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                                    "<font size='2' face='Tahoma'>DATA PENILAIAN SKOR BISHOP<br><br></font>"+        
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
                                "\"No.Rawat\";\"No.RM\";\"Nama Pasien\";\"J.K.\";\"Tgl.Lahir\";\"Kode Dokter\";\"DPJP\";\"Tgl/Jam Penilaian\";\"Pembukaan (Dilatasi)\";\"Skor Pembukaan\";\"Pendataran (Effacement)\";\"Skor Pendataran\";\"Station\";\"Skor Station\";\"Konsistensi Serviks\";\"Skor Konsistensi\";\"Posisi Serviks\";\"Skor Posisi\";\"Total Skor Bishop\";\"Kategori\";\"Keterangan Dokter\"\n"
                            ); 
                            for (i = 0; i < tabMode.getRowCount(); i++) {
                                htmlContent.append(
                                    "\""+tbObat.getValueAt(i,0).toString()+"\";\""+tbObat.getValueAt(i,1).toString()+"\";\""+tbObat.getValueAt(i,2).toString()+"\";\""+tbObat.getValueAt(i,3).toString()+"\";\""+tbObat.getValueAt(i,4).toString()+"\";\""+tbObat.getValueAt(i,5).toString()+"\";\""+tbObat.getValueAt(i,6).toString()+"\";\""+tbObat.getValueAt(i,7).toString()+"\";\""+tbObat.getValueAt(i,8).toString()+"\";\""+tbObat.getValueAt(i,9).toString()+"\";\""+tbObat.getValueAt(i,10).toString()+"\";\""+tbObat.getValueAt(i,11).toString()+"\";\""+tbObat.getValueAt(i,12).toString()+"\";\""+tbObat.getValueAt(i,13).toString()+"\";\""+tbObat.getValueAt(i,14).toString()+"\";\""+tbObat.getValueAt(i,15).toString()+"\";\""+tbObat.getValueAt(i,16).toString()+"\";\""+tbObat.getValueAt(i,17).toString()+"\";\""+tbObat.getValueAt(i,18).toString()+"\";\""+tbObat.getValueAt(i,19).toString()+"\";\""+tbObat.getValueAt(i,20).toString()+"\"\n"
                                );
                            }
                            f = new File("DataPenilaianBishop.csv");            
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
                "rptFormulirPenilaianBishop.jasper","report","::[ Formulir Penilaian Skor Bishop ]::","select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,penilaian_bishop_scor.kd_dokter,dokter.nm_dokter,penilaian_bishop_scor.tanggal,"+
                "penilaian_bishop_scor.pembukaan,penilaian_bishop_scor.skor_pembukaan,penilaian_bishop_scor.pendataran,penilaian_bishop_scor.skor_pendataran,penilaian_bishop_scor.station,penilaian_bishop_scor.skor_station,penilaian_bishop_scor.konsistensi,penilaian_bishop_scor.skor_konsistensi,"+
                "penilaian_bishop_scor.posisi,penilaian_bishop_scor.skor_posisi,penilaian_bishop_scor.skor,penilaian_bishop_scor.kategori,penilaian_bishop_scor.keterangan from penilaian_bishop_scor inner join reg_periksa on penilaian_bishop_scor.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join dokter on penilaian_bishop_scor.kd_dokter=dokter.kd_dokter where penilaian_bishop_scor.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'",param
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

    private void PembukaanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PembukaanActionPerformed
        hitungSkor();
    }//GEN-LAST:event_PembukaanActionPerformed

    private void PendataranActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PendataranActionPerformed
        hitungSkor();
    }//GEN-LAST:event_PendataranActionPerformed

    private void StationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StationActionPerformed
        hitungSkor();
    }//GEN-LAST:event_StationActionPerformed

    private void KonsistensiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_KonsistensiActionPerformed
        hitungSkor();
    }//GEN-LAST:event_KonsistensiActionPerformed

    private void PosisiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PosisiActionPerformed
        hitungSkor();
    }//GEN-LAST:event_PosisiActionPerformed

    private void TPasienKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TPasienKeyPressed
        Valid.pindah(evt,TCari,BtnSimpan);
    }//GEN-LAST:event_TPasienKeyPressed

    private void TanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKeyPressed
        Valid.pindah(evt,TCari,BtnDokter);
    }//GEN-LAST:event_TanggalKeyPressed

    private void PembukaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PembukaanKeyPressed
        Valid.pindah(evt,BtnDokter,Pendataran);
    }//GEN-LAST:event_PembukaanKeyPressed

    private void PendataranKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PendataranKeyPressed
        Valid.pindah(evt,Pembukaan,Station);
    }//GEN-LAST:event_PendataranKeyPressed

    private void StationKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_StationKeyPressed
        Valid.pindah(evt,Pendataran,Konsistensi);
    }//GEN-LAST:event_StationKeyPressed

    private void KonsistensiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KonsistensiKeyPressed
        Valid.pindah(evt,Station,Posisi);
    }//GEN-LAST:event_KonsistensiKeyPressed

    private void PosisiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PosisiKeyPressed
        Valid.pindah(evt,Konsistensi,Keterangan);
    }//GEN-LAST:event_PosisiKeyPressed

    private void KeteranganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKeyPressed
        Valid.pindah(evt,Posisi,BtnSimpan);
    }//GEN-LAST:event_KeteranganKeyPressed

    private void BtnDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokterKeyPressed
        Valid.pindah(evt,Tanggal,Pembukaan);
    }//GEN-LAST:event_BtnDokterKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMPenilaianBishop dialog = new RMPenilaianBishop(new javax.swing.JFrame(), true);
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
    private widget.PanelBiasa FormInput;
    private widget.TextBox JK;
    private widget.TextBox Kategori;
    private widget.TextBox KdDokter;
    private widget.TextBox Keterangan;
    private widget.ComboBox Konsistensi;
    private widget.Label LCount;
    private widget.editorpane LoadHTML;
    private widget.MenuItem MnFormulir;
    private widget.TextBox NmDokter;
    private javax.swing.JPanel PanelInput;
    private widget.ComboBox Pembukaan;
    private widget.ComboBox Pendataran;
    private widget.ComboBox Posisi;
    private widget.ScrollPane Scroll;
    private widget.TextBox SkorKonsistensi;
    private widget.TextBox SkorPembukaan;
    private widget.TextBox SkorPendataran;
    private widget.TextBox SkorPosisi;
    private widget.TextBox SkorStation;
    private widget.ComboBox Station;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.Tanggal Tanggal;
    private widget.TextBox TanggalRegistrasi;
    private widget.TextBox TglLahir;
    private widget.TextBox TotalSkor;
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
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,penilaian_bishop_scor.kd_dokter,dokter.nm_dokter,penilaian_bishop_scor.tanggal,penilaian_bishop_scor.pembukaan,"+
                    "penilaian_bishop_scor.skor_pembukaan,penilaian_bishop_scor.pendataran,penilaian_bishop_scor.skor_pendataran,penilaian_bishop_scor.station,penilaian_bishop_scor.skor_station,penilaian_bishop_scor.konsistensi,"+
                    "penilaian_bishop_scor.skor_konsistensi,penilaian_bishop_scor.posisi,penilaian_bishop_scor.skor_posisi,penilaian_bishop_scor.skor,penilaian_bishop_scor.kategori,penilaian_bishop_scor.keterangan from penilaian_bishop_scor "+
                    "inner join reg_periksa on penilaian_bishop_scor.no_rawat=reg_periksa.no_rawat inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join dokter on penilaian_bishop_scor.kd_dokter=dokter.kd_dokter "+
                    "where penilaian_bishop_scor.tanggal between ? and ? order by penilaian_bishop_scor.tanggal"
                );
            }else{
                ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,penilaian_bishop_scor.kd_dokter,dokter.nm_dokter,penilaian_bishop_scor.tanggal,penilaian_bishop_scor.pembukaan,"+
                    "penilaian_bishop_scor.skor_pembukaan,penilaian_bishop_scor.pendataran,penilaian_bishop_scor.skor_pendataran,penilaian_bishop_scor.station,penilaian_bishop_scor.skor_station,penilaian_bishop_scor.konsistensi,"+
                    "penilaian_bishop_scor.skor_konsistensi,penilaian_bishop_scor.posisi,penilaian_bishop_scor.skor_posisi,penilaian_bishop_scor.skor,penilaian_bishop_scor.kategori,penilaian_bishop_scor.keterangan from penilaian_bishop_scor "+
                    "inner join reg_periksa on penilaian_bishop_scor.no_rawat=reg_periksa.no_rawat inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join dokter on penilaian_bishop_scor.kd_dokter=dokter.kd_dokter "+
                    "where penilaian_bishop_scor.tanggal between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or penilaian_bishop_scor.kd_dokter like ? or dokter.nm_dokter like ? or "+
                    "penilaian_bishop_scor.kategori like ?) order by penilaian_bishop_scor.tanggal"
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
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("jk"),rs.getString("tgl_lahir"),
                        rs.getString("kd_dokter"),rs.getString("nm_dokter"),rs.getString("tanggal"),rs.getString("pembukaan"),rs.getString("skor_pembukaan"),
                        rs.getString("pendataran"),rs.getString("skor_pendataran"),rs.getString("station"),rs.getString("skor_station"),rs.getString("konsistensi"),
                        rs.getString("skor_konsistensi"),rs.getString("posisi"),rs.getString("skor_posisi"),rs.getString("skor"),rs.getString("kategori"),
                        rs.getString("keterangan")
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
        Pembukaan.setSelectedIndex(0);
        Pendataran.setSelectedIndex(0);
        Station.setSelectedIndex(0);
        Konsistensi.setSelectedIndex(0);
        Posisi.setSelectedIndex(0);
        Keterangan.setText("");
        hitungSkor();
        Tanggal.setDate(new Date());
        Pembukaan.requestFocus();
    } 

    private void hitungSkor() {
        SkorPembukaan.setText(""+Pembukaan.getSelectedIndex());
        SkorPendataran.setText(""+Pendataran.getSelectedIndex());
        SkorStation.setText(""+Station.getSelectedIndex());
        SkorKonsistensi.setText(""+Konsistensi.getSelectedIndex());
        SkorPosisi.setText(""+Posisi.getSelectedIndex());
        TotalSkor.setText(
            ""+(Pembukaan.getSelectedIndex()+Pendataran.getSelectedIndex()+Station.getSelectedIndex()+Konsistensi.getSelectedIndex()+Posisi.getSelectedIndex())
        );
        Kategori.setText(
            Integer.parseInt(TotalSkor.getText())<6?"Belum Matang":Integer.parseInt(TotalSkor.getText())<8?"Menengah":"Matang"
        );
    }

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            JK.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString());
            Pembukaan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            SkorPembukaan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            Pendataran.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            SkorPendataran.setText(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            Station.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            SkorStation.setText(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            Konsistensi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            SkorKonsistensi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
            Posisi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            SkorPosisi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());
            TotalSkor.setText(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
            Kategori.setText(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
            Keterangan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString());
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
            if(internalFrame1.getHeight()>428){
                ChkInput.setVisible(false);
                PanelInput.setPreferredSize(new Dimension(WIDTH,256));
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
        BtnSimpan.setEnabled(akses.getpenilaian_bishop_scor());
        BtnHapus.setEnabled(akses.getpenilaian_bishop_scor());
        BtnEdit.setEnabled(akses.getpenilaian_bishop_scor());
        BtnPrint.setEnabled(akses.getpenilaian_bishop_scor()); 
        
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
        if(Sequel.mengedittf("penilaian_bishop_scor","no_rawat=?","no_rawat=?,tanggal=?,pembukaan=?,skor_pembukaan=?,pendataran=?,skor_pendataran=?,station=?,skor_station=?,konsistensi=?,skor_konsistensi=?,posisi=?,skor_posisi=?,skor=?,kategori=?,keterangan=?,kd_dokter=?",17,new String[]{
            TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19),Pembukaan.getSelectedItem().toString(),SkorPembukaan.getText(),Pendataran.getSelectedItem().toString(),SkorPendataran.getText(),Station.getSelectedItem().toString(),
            SkorStation.getText(),Konsistensi.getSelectedItem().toString(),SkorKonsistensi.getText(),Posisi.getSelectedItem().toString(),SkorPosisi.getText(),TotalSkor.getText(),Kategori.getText(),Keterangan.getText(),KdDokter.getText(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
        })==true){
               tbObat.setValueAt(TNoRw.getText(),tbObat.getSelectedRow(),0);
               tbObat.setValueAt(TNoRM.getText(),tbObat.getSelectedRow(),1);
               tbObat.setValueAt(TPasien.getText(),tbObat.getSelectedRow(),2);
               tbObat.setValueAt(JK.getText(),tbObat.getSelectedRow(),3);
               tbObat.setValueAt(TglLahir.getText(),tbObat.getSelectedRow(),4);
               tbObat.setValueAt(KdDokter.getText(),tbObat.getSelectedRow(),5);
               tbObat.setValueAt(NmDokter.getText(),tbObat.getSelectedRow(),6);
               tbObat.setValueAt(Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19),tbObat.getSelectedRow(),7);
               tbObat.setValueAt(Pembukaan.getSelectedItem().toString(),tbObat.getSelectedRow(),8);
               tbObat.setValueAt(SkorPembukaan.getText(),tbObat.getSelectedRow(),9);
               tbObat.setValueAt(Pendataran.getSelectedItem().toString(),tbObat.getSelectedRow(),10);
               tbObat.setValueAt(SkorPendataran.getText(),tbObat.getSelectedRow(),11);
               tbObat.setValueAt(Station.getSelectedItem().toString(),tbObat.getSelectedRow(),12);
               tbObat.setValueAt(SkorStation.getText(),tbObat.getSelectedRow(),13);
               tbObat.setValueAt(Konsistensi.getSelectedItem().toString(),tbObat.getSelectedRow(),14);
               tbObat.setValueAt(SkorKonsistensi.getText(),tbObat.getSelectedRow(),15);
               tbObat.setValueAt(Posisi.getSelectedItem().toString(),tbObat.getSelectedRow(),16);
               tbObat.setValueAt(SkorPosisi.getText(),tbObat.getSelectedRow(),17);
               tbObat.setValueAt(TotalSkor.getText(),tbObat.getSelectedRow(),18);
               tbObat.setValueAt(Kategori.getText(),tbObat.getSelectedRow(),19);
               tbObat.setValueAt(Keterangan.getText(),tbObat.getSelectedRow(),20);
               emptTeks();
        }
    }

    private void hapus() {
        if(Sequel.queryu2tf("delete from penilaian_bishop_scor where no_rawat=?",1,new String[]{
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
        if(Sequel.menyimpantf("penilaian_bishop_scor","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","Data",16,new String[]{
            TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19),
            Pembukaan.getSelectedItem().toString(),SkorPembukaan.getText(),Pendataran.getSelectedItem().toString(),SkorPendataran.getText(),
            Station.getSelectedItem().toString(),SkorStation.getText(),Konsistensi.getSelectedItem().toString(),SkorKonsistensi.getText(),
            Posisi.getSelectedItem().toString(),SkorPosisi.getText(),TotalSkor.getText(),Kategori.getText(),Keterangan.getText(),KdDokter.getText()
        })==true){
            tabMode.addRow(new Object[]{
                TNoRw.getText(),TNoRM.getText(),TPasien.getText(),JK.getText(),TglLahir.getText(),KdDokter.getText(),NmDokter.getText(),
                Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19),Pembukaan.getSelectedItem().toString(),
                SkorPembukaan.getText(),Pendataran.getSelectedItem().toString(),SkorPendataran.getText(),Station.getSelectedItem().toString(),
                SkorStation.getText(),Konsistensi.getSelectedItem().toString(),SkorKonsistensi.getText(),Posisi.getSelectedItem().toString(),
                SkorPosisi.getText(),TotalSkor.getText(),Kategori.getText(),Keterangan.getText()
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

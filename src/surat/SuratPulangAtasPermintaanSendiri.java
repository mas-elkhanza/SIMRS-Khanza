/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package surat;

import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
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
import kepegawaian.DlgCariPetugas;


/**
 * 
 * @author windiartohugroho
 */
public final class SuratPulangAtasPermintaanSendiri extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;
    private DlgCariPetugas petugas;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private volatile boolean ceksukses = false;
    private Date lahir;
    private LocalDate today=LocalDate.now();
    private LocalDate birthday;
    private Period p;
    private String finger="",lokasifile="",lokasifile2="";
    
    public SuratPulangAtasPermintaanSendiri(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(628,674);
        
        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Surat","No.Rawat","No.R.M.","Nama Pasien","Umur","J.K.","Tgl.Lahir","Tgl.Pulang","Faskes Alternatif/Pindah Rawat/Alasan",
            "Nama Penanggung Jawab","Lahir","Umur","J.K.","Alamat","Hubungan","Saksi I Keluarga","NIP","Nama Petugas"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 18; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(105);
            }else if(i==1){
                column.setPreferredWidth(105);
            }else if(i==2){
                column.setPreferredWidth(70);
            }else if(i==3){
                column.setPreferredWidth(150);
            }else if(i==4){
                column.setPreferredWidth(45);
            }else if(i==5){
                column.setPreferredWidth(25);
            }else if(i==6){
                column.setPreferredWidth(65);
            }else if(i==7){
                column.setPreferredWidth(65);
            }else if(i==8){
                column.setPreferredWidth(200);
            }else if(i==9){
                column.setPreferredWidth(150);
            }else if(i==10){
                column.setPreferredWidth(65);
            }else if(i==11){
                column.setPreferredWidth(45);
            }else if(i==12){
                column.setPreferredWidth(25);
            }else if(i==13){
                column.setPreferredWidth(170);
            }else if(i==14){
                column.setPreferredWidth(58);
            }else if(i==15){
                column.setPreferredWidth(150);
            }else if(i==16){
                column.setPreferredWidth(80);
            }else if(i==17){
                column.setPreferredWidth(150);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());
        
        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));    
        KdPetugas.setDocument(new batasInput((byte)20).getKata(KdPetugas));  
        NoSurat.setDocument(new batasInput((byte)20).getKata(NoSurat));
        Pilihan.setDocument(new batasInput((byte)50).getKata(Pilihan));
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        NamaPihak2.setDocument(new batasInput((byte)50).getKata(NamaPihak2));
        SaksiKeluarga.setDocument(new batasInput((byte)50).getKata(SaksiKeluarga));
        AlamatPj.setDocument(new batasInput((int)100).getKata(AlamatPj));  
        UmurPJ.setDocument(new batasInput((byte)3).getKata(UmurPJ));    
        
        ChkInput.setSelected(false);
        isForm();
        
        ChkAccor.setSelected(false);
        isPhoto();
        
        HTMLEditorKit kit = new HTMLEditorKit();
        LoadHTML2.setEditable(true);
        LoadHTML2.setEditorKit(kit);
        LoadHTML3.setEditable(true);
        LoadHTML3.setEditorKit(kit);
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
        LoadHTML2.setDocument(doc);
        LoadHTML3.setDocument(doc);
    }
    
    
    
    
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        JK = new widget.TextBox();
        Umur = new widget.TextBox();
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
        BtnAll = new widget.Button();
        BtnKeluar = new widget.Button();
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
        PanelInput = new javax.swing.JPanel();
        FormInput = new widget.PanelBiasa();
        jLabel4 = new widget.Label();
        TNoRw = new widget.TextBox();
        TPasien = new widget.TextBox();
        TNoRM = new widget.TextBox();
        jLabel8 = new widget.Label();
        NamaPihak2 = new widget.TextBox();
        jLabel9 = new widget.Label();
        JKPJ = new widget.ComboBox();
        jLabel10 = new widget.Label();
        jLabel11 = new widget.Label();
        Hubungan = new widget.ComboBox();
        AlamatPj = new widget.TextBox();
        jLabel17 = new widget.Label();
        LahirPasien = new widget.TextBox();
        jLabel18 = new widget.Label();
        KdPetugas = new widget.TextBox();
        NmPetugas = new widget.TextBox();
        BtnPetugas = new widget.Button();
        jLabel16 = new widget.Label();
        jLabel13 = new widget.Label();
        TglLahir = new widget.Tanggal();
        jLabel44 = new widget.Label();
        UmurPJ = new widget.TextBox();
        jLabel31 = new widget.Label();
        jLabel12 = new widget.Label();
        Pilihan = new widget.TextBox();
        Tanggal = new widget.Tanggal();
        jLabel14 = new widget.Label();
        jLabel3 = new widget.Label();
        NoSurat = new widget.TextBox();
        jLabel15 = new widget.Label();
        SaksiKeluarga = new widget.TextBox();
        ChkInput = new widget.CekBox();
        PanelAccor = new widget.PanelBiasa();
        ChkAccor = new widget.CekBox();
        FormPhoto = new widget.PanelBiasa();
        FormPass3 = new widget.PanelBiasa();
        btnAmbil = new widget.Button();
        BtnRefreshPhoto1 = new widget.Button();
        BtnPrint1 = new widget.Button();
        TabData = new javax.swing.JTabbedPane();
        Scroll5 = new widget.ScrollPane();
        LoadHTML2 = new widget.editorpane();
        Scroll6 = new widget.ScrollPane();
        LoadHTML3 = new widget.editorpane();

        JK.setHighlighter(null);
        JK.setName("JK"); // NOI18N

        Umur.setHighlighter(null);
        Umur.setName("Umur"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Penyataan Pulang Atas Permintaan Sendiri ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);
        Scroll.setPreferredSize(new java.awt.Dimension(452, 200));

        tbObat.setAutoCreateRowSorter(true);
        tbObat.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbObat.setName("tbObat"); // NOI18N
        tbObat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbObatMouseClicked(evt);
            }
        });
        tbObat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbObatKeyReleased(evt);
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

        jPanel3.add(panelGlass8, java.awt.BorderLayout.CENTER);

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel19.setText("Tgl. Surat :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(67, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "11-02-2026" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "11-02-2026" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(DTPCari2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(205, 23));
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
        jLabel7.setPreferredSize(new java.awt.Dimension(65, 23));
        panelGlass9.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(50, 23));
        panelGlass9.add(LCount);

        jPanel3.add(panelGlass9, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 235));
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(100, 165));
        FormInput.setLayout(null);

        jLabel4.setText("No.Rawat :");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(0, 10, 70, 23);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(74, 10, 136, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        TPasien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPasienKeyPressed(evt);
            }
        });
        FormInput.add(TPasien);
        TPasien.setBounds(325, 10, 255, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        TNoRM.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRMKeyPressed(evt);
            }
        });
        FormInput.add(TNoRM);
        TNoRM.setBounds(212, 10, 111, 23);

        jLabel8.setText("Hubungan :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(0, 150, 130, 23);

        NamaPihak2.setName("NamaPihak2"); // NOI18N
        NamaPihak2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NamaPihak2KeyPressed(evt);
            }
        });
        FormInput.add(NamaPihak2);
        NamaPihak2.setBounds(134, 120, 290, 23);

        jLabel9.setText("J.K. :");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(246, 150, 30, 23);

        JKPJ.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Laki-laki", "Perempuan" }));
        JKPJ.setName("JKPJ"); // NOI18N
        JKPJ.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JKPJKeyPressed(evt);
            }
        });
        FormInput.add(JKPJ);
        JKPJ.setBounds(280, 150, 105, 23);

        jLabel10.setText("Nama :");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(0, 120, 130, 23);

        jLabel11.setText("Alamat :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(394, 150, 50, 23);

        Hubungan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Suami", "Istri", "Anak", "Ayah", "Saudara", "Keponakan", "Ibu", "Diri Sendiri" }));
        Hubungan.setName("Hubungan"); // NOI18N
        Hubungan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HubunganKeyPressed(evt);
            }
        });
        FormInput.add(Hubungan);
        Hubungan.setBounds(134, 150, 105, 23);

        AlamatPj.setName("AlamatPj"); // NOI18N
        AlamatPj.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlamatPjKeyPressed(evt);
            }
        });
        FormInput.add(AlamatPj);
        AlamatPj.setBounds(448, 150, 285, 23);

        jLabel17.setText("Tgl.Lahir :");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(584, 10, 60, 23);

        LahirPasien.setHighlighter(null);
        LahirPasien.setName("LahirPasien"); // NOI18N
        FormInput.add(LahirPasien);
        LahirPasien.setBounds(648, 10, 85, 23);

        jLabel18.setText("Petugas/Saksi II Perawat :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(180, 40, 140, 23);

        KdPetugas.setEditable(false);
        KdPetugas.setHighlighter(null);
        KdPetugas.setName("KdPetugas"); // NOI18N
        FormInput.add(KdPetugas);
        KdPetugas.setBounds(324, 40, 120, 23);

        NmPetugas.setEditable(false);
        NmPetugas.setName("NmPetugas"); // NOI18N
        FormInput.add(NmPetugas);
        NmPetugas.setBounds(446, 40, 257, 23);

        BtnPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPetugas.setMnemonic('2');
        BtnPetugas.setToolTipText("ALt+2");
        BtnPetugas.setName("BtnPetugas"); // NOI18N
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
        BtnPetugas.setBounds(705, 40, 28, 23);

        jLabel16.setText("Tanggal :");
        jLabel16.setName("jLabel16"); // NOI18N
        jLabel16.setVerifyInputWhenFocusTarget(false);
        FormInput.add(jLabel16);
        jLabel16.setBounds(0, 40, 70, 23);

        jLabel13.setText("Tanggal Lahir :");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(436, 120, 85, 23);

        TglLahir.setForeground(new java.awt.Color(50, 70, 50));
        TglLahir.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "11-02-2026" }));
        TglLahir.setDisplayFormat("dd-MM-yyyy");
        TglLahir.setName("TglLahir"); // NOI18N
        TglLahir.setOpaque(false);
        TglLahir.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TglLahirItemStateChanged(evt);
            }
        });
        TglLahir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglLahirKeyPressed(evt);
            }
        });
        FormInput.add(TglLahir);
        TglLahir.setBounds(525, 120, 90, 23);

        jLabel44.setText("Umur :");
        jLabel44.setName("jLabel44"); // NOI18N
        FormInput.add(jLabel44);
        jLabel44.setBounds(628, 120, 40, 23);

        UmurPJ.setText("0");
        UmurPJ.setName("UmurPJ"); // NOI18N
        UmurPJ.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                UmurPJKeyPressed(evt);
            }
        });
        FormInput.add(UmurPJ);
        UmurPJ.setBounds(672, 120, 45, 23);

        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel31.setText("Th");
        jLabel31.setName("jLabel31"); // NOI18N
        FormInput.add(jLabel31);
        jLabel31.setBounds(720, 120, 40, 23);

        jLabel12.setText("Faskes Alternatif/Pindah Rawat/Alasan :");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(219, 70, 210, 23);

        Pilihan.setName("Pilihan"); // NOI18N
        Pilihan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PilihanKeyPressed(evt);
            }
        });
        FormInput.add(Pilihan);
        Pilihan.setBounds(433, 70, 300, 23);

        Tanggal.setForeground(new java.awt.Color(50, 70, 50));
        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "11-02-2026" }));
        Tanggal.setDisplayFormat("dd-MM-yyyy");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.setOpaque(false);
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeyPressed(evt);
            }
        });
        FormInput.add(Tanggal);
        Tanggal.setBounds(74, 40, 90, 23);

        jLabel14.setText("Penanggung Jawab Pasien :");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(0, 100, 154, 23);

        jLabel3.setText("No. Surat :");
        jLabel3.setName("jLabel3"); // NOI18N
        FormInput.add(jLabel3);
        jLabel3.setBounds(0, 70, 70, 23);

        NoSurat.setHighlighter(null);
        NoSurat.setName("NoSurat"); // NOI18N
        NoSurat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoSuratKeyPressed(evt);
            }
        });
        FormInput.add(NoSurat);
        NoSurat.setBounds(74, 70, 141, 23);

        jLabel15.setText("Saksi I Keluarga :");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(0, 180, 130, 23);

        SaksiKeluarga.setName("SaksiKeluarga"); // NOI18N
        SaksiKeluarga.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SaksiKeluargaKeyPressed(evt);
            }
        });
        FormInput.add(SaksiKeluarga);
        SaksiKeluarga.setBounds(134, 180, 290, 23);

        PanelInput.add(FormInput, java.awt.BorderLayout.CENTER);

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

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

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
        FormPhoto.setBorder(null);
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

        BtnPrint1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item (copy).png"))); // NOI18N
        BtnPrint1.setMnemonic('T');
        BtnPrint1.setText("Surat");
        BtnPrint1.setToolTipText("Alt+T");
        BtnPrint1.setName("BtnPrint1"); // NOI18N
        BtnPrint1.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnPrint1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPrint1ActionPerformed(evt);
            }
        });
        FormPass3.add(BtnPrint1);

        FormPhoto.add(FormPass3, java.awt.BorderLayout.PAGE_END);

        TabData.setBackground(new java.awt.Color(254, 255, 254));
        TabData.setForeground(new java.awt.Color(50, 50, 50));
        TabData.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabData.setName("TabData"); // NOI18N
        TabData.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabDataMouseClicked(evt);
            }
        });

        Scroll5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll5.setName("Scroll5"); // NOI18N
        Scroll5.setOpaque(true);
        Scroll5.setPreferredSize(new java.awt.Dimension(200, 200));

        LoadHTML2.setBorder(null);
        LoadHTML2.setName("LoadHTML2"); // NOI18N
        Scroll5.setViewportView(LoadHTML2);

        TabData.addTab("Tanda Tangan Pembuat Pernyataan", Scroll5);

        Scroll6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll6.setName("Scroll6"); // NOI18N
        Scroll6.setOpaque(true);
        Scroll6.setPreferredSize(new java.awt.Dimension(200, 200));

        LoadHTML3.setBorder(null);
        LoadHTML3.setName("LoadHTML3"); // NOI18N
        Scroll6.setViewportView(LoadHTML3);

        TabData.addTab("Tanda Tangan Saksi I Keluarga", Scroll6);

        FormPhoto.add(TabData, java.awt.BorderLayout.CENTER);

        PanelAccor.add(FormPhoto, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelAccor, java.awt.BorderLayout.EAST);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);
        internalFrame1.getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"pasien");
        }else if(NamaPihak2.getText().trim().equals("")){
            Valid.textKosong(NamaPihak2,"Nama Pihak Ke 2");
        }else if(AlamatPj.getText().trim().equals("")){
            Valid.textKosong(AlamatPj,"Alamat Pihak Ke 2");
        }else if(SaksiKeluarga.getText().trim().equals("")){
            Valid.textKosong(SaksiKeluarga,"Saksi I Keluarga");
        }else if(NmPetugas.getText().trim().equals("")){
            Valid.textKosong(NmPetugas,"Petugas/Saksi II Perawat");
        }else if(NoSurat.getText().trim().equals("")){
            Valid.textKosong(NoSurat,"No.Surat Pernyataan");
        }else{
            if(Sequel.menyimpantf("surat_pulang_atas_permintaan_sendiri","?,?,?,?,?,?,?,?,?,?,?,?","Data",12,new String[]{
                    NoSurat.getText(),TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+""),Pilihan.getText(),NamaPihak2.getText(),
                    Valid.SetTgl(TglLahir.getSelectedItem()+""),UmurPJ.getText(),JKPJ.getSelectedItem().toString().substring(0,1),
                    AlamatPj.getText(),Hubungan.getSelectedItem().toString(),SaksiKeluarga.getText(),KdPetugas.getText()
                })==true){
                tabMode.addRow(new Object[]{
                    NoSurat.getText(),TNoRw.getText(),TNoRM.getText(),TPasien.getText(),Umur.getText(),JK.getText(),LahirPasien.getText(),
                    Valid.SetTgl(Tanggal.getSelectedItem()+""),Pilihan.getText(),NamaPihak2.getText(),Valid.SetTgl(TglLahir.getSelectedItem()+""),
                    UmurPJ.getText(),JKPJ.getSelectedItem().toString().substring(0,1),AlamatPj.getText(),Hubungan.getSelectedItem().toString(),SaksiKeluarga.getText(),
                    KdPetugas.getText(),NmPetugas.getText()
                });
                LCount.setText(""+tabMode.getRowCount());
                emptTeks();
            }
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,SaksiKeluarga,BtnBatal);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        emptTeks();
        ChkInput.setSelected(true);
        isForm();  
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
                if(KdPetugas.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString())){
                    hapus();
                }else{
                    JOptionPane.showMessageDialog(null,"Hanya bisa dihapus oleh petugas yang bersangkutan..!!");
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
        }else if(NamaPihak2.getText().trim().equals("")){
            Valid.textKosong(NamaPihak2,"Nama Pihak Ke 2");
        }else if(AlamatPj.getText().trim().equals("")){
            Valid.textKosong(AlamatPj,"Alamat Pihak Ke 2");
        }else if(SaksiKeluarga.getText().trim().equals("")){
            Valid.textKosong(SaksiKeluarga,"Saksi I Keluarga");
        }else if(NmPetugas.getText().trim().equals("")){
            Valid.textKosong(NmPetugas,"Petugas/Saksi II Perawat");
        }else if(NoSurat.getText().trim().equals("")){
            Valid.textKosong(NoSurat,"No.Surat Pernyataan");
        }else{
            if(tbObat.getSelectedRow()>-1){
                if(akses.getkode().equals("Admin Utama")){
                    ganti();
                }else{
                    if(KdPetugas.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString())){
                        ganti();
                    }else{
                        JOptionPane.showMessageDialog(null,"Hanya bisa diganti oleh petugas yang bersangkutan..!!");
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
            Map<String, Object> param = new HashMap<>(); 
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());   
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            
            if(TCari.getText().trim().equals("")){
                Valid.MyReportqry("rptDataFormulirPulangAtasPermintaanSendiri.jasper","report","::[ Data Formulir Pulang Atas Permintaan Sendiri ]::",
                    "select surat_pulang_atas_permintaan_sendiri.no_surat,reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,reg_periksa.umurdaftar,"+
                    "reg_periksa.sttsumur,pasien.jk,pasien.tgl_lahir,surat_pulang_atas_permintaan_sendiri.tgl_pulang,surat_pulang_atas_permintaan_sendiri.rs_pilihan,"+
                    "surat_pulang_atas_permintaan_sendiri.nama_pj,surat_pulang_atas_permintaan_sendiri.lahir,surat_pulang_atas_permintaan_sendiri.umur,"+
                    "surat_pulang_atas_permintaan_sendiri.jkpj,surat_pulang_atas_permintaan_sendiri.alamat,surat_pulang_atas_permintaan_sendiri.hubungan,"+
                    "surat_pulang_atas_permintaan_sendiri.saksi_keluarga,surat_pulang_atas_permintaan_sendiri.nip,petugas.nama from surat_pulang_atas_permintaan_sendiri "+
                    "inner join reg_periksa on surat_pulang_atas_permintaan_sendiri.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join petugas on surat_pulang_atas_permintaan_sendiri.nip=petugas.nip where "+
                    "surat_pulang_atas_permintaan_sendiri.tgl_pulang between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' order by surat_pulang_atas_permintaan_sendiri.tgl_pulang",param);
            }else{
                Valid.MyReportqry("rptDataFormulirPulangAtasPermintaanSendiri.jasper","report","::[ Data Formulir Pulang Atas Permintaan Sendiri ]::",
                    "select surat_pulang_atas_permintaan_sendiri.no_surat,reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,reg_periksa.umurdaftar,"+
                    "reg_periksa.sttsumur,pasien.jk,pasien.tgl_lahir,surat_pulang_atas_permintaan_sendiri.tgl_pulang,surat_pulang_atas_permintaan_sendiri.rs_pilihan,"+
                    "surat_pulang_atas_permintaan_sendiri.nama_pj,surat_pulang_atas_permintaan_sendiri.lahir,surat_pulang_atas_permintaan_sendiri.umur,"+
                    "surat_pulang_atas_permintaan_sendiri.jkpj,surat_pulang_atas_permintaan_sendiri.alamat,surat_pulang_atas_permintaan_sendiri.hubungan,"+
                    "surat_pulang_atas_permintaan_sendiri.saksi_keluarga,surat_pulang_atas_permintaan_sendiri.nip,petugas.nama from surat_pulang_atas_permintaan_sendiri "+
                    "inner join reg_periksa on surat_pulang_atas_permintaan_sendiri.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join petugas on surat_pulang_atas_permintaan_sendiri.nip=petugas.nip where "+
                    "surat_pulang_atas_permintaan_sendiri.tgl_pulang between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and "+
                    "(reg_periksa.no_rawat like '%"+TCari.getText().trim()+"%' or pasien.no_rkm_medis like '%"+TCari.getText().trim()+"%' or pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or "+
                    "surat_pulang_atas_permintaan_sendiri.alamat like '%"+TCari.getText().trim()+"%' or surat_pulang_atas_permintaan_sendiri.nama_pj like '%"+TCari.getText().trim()+"%' or "+
                    "surat_pulang_atas_permintaan_sendiri.nip like '%"+TCari.getText().trim()+"%' or petugas.nama like '%"+TCari.getText().trim()+"%') "+
                    "order by surat_pulang_atas_permintaan_sendiri.tgl_pulang",param);
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
            try {
                isPhoto();
                panggilPhoto();
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbObatMouseClicked

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
       isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void tbObatKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbObatKeyReleased
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }//GEN-LAST:event_tbObatKeyReleased

    private void BtnPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPetugasKeyPressed
        Valid.pindah(evt,Tanggal,NamaPihak2);
    }//GEN-LAST:event_BtnPetugasKeyPressed

    private void BtnPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPetugasActionPerformed
        if (petugas == null || !petugas.isDisplayable()) {
            petugas=new DlgCariPetugas(null,false);
            petugas.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            petugas.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    if(petugas.getTable().getSelectedRow()!= -1){                   
                        KdPetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                        NmPetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                    }  
                    BtnPetugas.requestFocus();
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
    }//GEN-LAST:event_BtnPetugasActionPerformed

    private void AlamatPjKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlamatPjKeyPressed
        Valid.pindah(evt,JKPJ,SaksiKeluarga);
    }//GEN-LAST:event_AlamatPjKeyPressed

    private void HubunganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HubunganKeyPressed
        Valid.pindah(evt,TglLahir,JKPJ);
    }//GEN-LAST:event_HubunganKeyPressed

    private void JKPJKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JKPJKeyPressed
        Valid.pindah(evt,Hubungan,AlamatPj);
    }//GEN-LAST:event_JKPJKeyPressed

    private void NamaPihak2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NamaPihak2KeyPressed
        Valid.pindah(evt,Pilihan,TglLahir);
    }//GEN-LAST:event_NamaPihak2KeyPressed

    private void TNoRMKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRMKeyPressed
    // Valid.pindah(evt, TNm, BtnSimpan);
    }//GEN-LAST:event_TNoRMKeyPressed

    private void TPasienKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TPasienKeyPressed
        Valid.pindah(evt,TCari,BtnSimpan);
    }//GEN-LAST:event_TPasienKeyPressed

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isRawat();
        }else{
            Valid.pindah(evt,TCari,Tanggal);
        }
    }//GEN-LAST:event_TNoRwKeyPressed

    private void TglLahirItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TglLahirItemStateChanged
        lahir = TglLahir.getDate();
        birthday = lahir.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        p = Period.between(birthday,today);
        UmurPJ.setText(String.valueOf(p.getYears()));
    }//GEN-LAST:event_TglLahirItemStateChanged

    private void TglLahirKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglLahirKeyPressed
        Valid.pindah2(evt,NamaPihak2,JKPJ);
    }//GEN-LAST:event_TglLahirKeyPressed

    private void PilihanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PilihanKeyPressed
         Valid.pindah(evt,NoSurat,NamaPihak2);
    }//GEN-LAST:event_PilihanKeyPressed

    private void TanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKeyPressed
        //Valid.pindah(evt,TCari,Jam);
    }//GEN-LAST:event_TanggalKeyPressed

    private void NoSuratKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoSuratKeyPressed
        Valid.pindah(evt,BtnPetugas,Pilihan);
    }//GEN-LAST:event_NoSuratKeyPressed

    private void SaksiKeluargaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SaksiKeluargaKeyPressed
        Valid.pindah(evt,AlamatPj,BtnSimpan);
    }//GEN-LAST:event_SaksiKeluargaKeyPressed

    private void UmurPJKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_UmurPJKeyPressed
        Valid.pindah(evt,TglLahir,Hubungan);
    }//GEN-LAST:event_UmurPJKeyPressed

    private void ChkAccorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkAccorActionPerformed
        if(tbObat.getSelectedRow()!= -1){
            isPhoto();
            TabDataMouseClicked(null);
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
                Sequel.queryu("delete from antriaps");
                Sequel.queryu("insert into antriaps values('"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"','"+tbObat.getValueAt(tbObat.getSelectedRow(),1).toString()+"')");
                Sequel.queryu("delete from surat_pulang_atas_permintaan_sendiri_pembuat_pernyataan where no_surat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'");
                Sequel.queryu("delete from surat_pulang_atas_permintaan_sendiri_saksi_keluarga where no_surat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'");
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

    private void TabDataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabDataMouseClicked
        if(tbObat.getSelectedRow()>-1){
            panggilPhoto();
        }else{
            JOptionPane.showMessageDialog(rootPane,"Silahkan anda pilih No.Pernyataan terlebih dahulu..!!");
        }
    }//GEN-LAST:event_TabDataMouseClicked

    private void BtnPrint1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrint1ActionPerformed
        if(tbObat.getSelectedRow()>-1){
            if(lokasifile.equals("")){
                JOptionPane.showMessageDialog(null,"Maaf, Silahkan ambil photo bukti pulang atas permintaan sendiri terlebih dahulu..!!!!");
            }else{
                Map<String, Object> param = new HashMap<>();
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("emailrs",akses.getemailrs());
                param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
                param.put("photo_penerima","http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/pulangaps/"+lokasifile);
                param.put("photo_saksi","http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/pulangaps/"+lokasifile2);
                finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
                param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbObat.getValueAt(tbObat.getSelectedRow(),17).toString()+"\nID "+(finger.equals("")?tbObat.getValueAt(tbObat.getSelectedRow(),16).toString():finger)+"\n"+Valid.SetTgl3(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString()));
                Valid.MyReportqry("rptSuratPulangAtasPermintaanSendiri.jasper","report","::[ Surat Pulang Atas Permintaan Sendiri ]::",
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,reg_periksa.umurdaftar,reg_periksa.sttsumur,pasien.tmp_lahir,concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab,', ',propinsi.nm_prop) as alamat_pasien,surat_pulang_atas_permintaan_sendiri.no_surat,surat_pulang_atas_permintaan_sendiri.tgl_pulang,surat_pulang_atas_permintaan_sendiri.rs_pilihan,"+
		    "surat_pulang_atas_permintaan_sendiri.nama_pj,surat_pulang_atas_permintaan_sendiri.lahir,surat_pulang_atas_permintaan_sendiri.umur,surat_pulang_atas_permintaan_sendiri.jkpj,surat_pulang_atas_permintaan_sendiri.alamat,surat_pulang_atas_permintaan_sendiri.hubungan,surat_pulang_atas_permintaan_sendiri.saksi_keluarga,surat_pulang_atas_permintaan_sendiri.nip,petugas.nama,pasien.pekerjaan from surat_pulang_atas_permintaan_sendiri "+
                    "inner join reg_periksa on surat_pulang_atas_permintaan_sendiri.no_rawat=reg_periksa.no_rawat inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join petugas on surat_pulang_atas_permintaan_sendiri.nip=petugas.nip inner join kelurahan on pasien.kd_kel=kelurahan.kd_kel inner join kecamatan on pasien.kd_kec=kecamatan.kd_kec inner join kabupaten on pasien.kd_kab=kabupaten.kd_kab "+
		    "inner join propinsi on pasien.kd_prop=propinsi.kd_prop where surat_pulang_atas_permintaan_sendiri.no_surat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'",param);
            }
        }else{
            JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data terlebih dahulu..!!!!");
        }
    }//GEN-LAST:event_BtnPrint1ActionPerformed

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
            SuratPulangAtasPermintaanSendiri dialog = new SuratPulangAtasPermintaanSendiri(new javax.swing.JFrame(), true);
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
    private widget.TextBox AlamatPj;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPetugas;
    private widget.Button BtnPrint;
    private widget.Button BtnPrint1;
    private widget.Button BtnRefreshPhoto1;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkAccor;
    private widget.CekBox ChkInput;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.PanelBiasa FormInput;
    private widget.PanelBiasa FormPass3;
    private widget.PanelBiasa FormPhoto;
    private widget.ComboBox Hubungan;
    private widget.TextBox JK;
    private widget.ComboBox JKPJ;
    private widget.TextBox KdPetugas;
    private widget.Label LCount;
    private widget.TextBox LahirPasien;
    private widget.editorpane LoadHTML2;
    private widget.editorpane LoadHTML3;
    private widget.TextBox NamaPihak2;
    private widget.TextBox NmPetugas;
    private widget.TextBox NoSurat;
    private widget.PanelBiasa PanelAccor;
    private javax.swing.JPanel PanelInput;
    private widget.TextBox Pilihan;
    private widget.TextBox SaksiKeluarga;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll5;
    private widget.ScrollPane Scroll6;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private javax.swing.JTabbedPane TabData;
    private widget.Tanggal Tanggal;
    private widget.Tanggal TglLahir;
    private widget.TextBox Umur;
    private widget.TextBox UmurPJ;
    private widget.Button btnAmbil;
    private widget.InternalFrame internalFrame1;
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
    private widget.Label jLabel21;
    private widget.Label jLabel3;
    private widget.Label jLabel31;
    private widget.Label jLabel4;
    private widget.Label jLabel44;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private javax.swing.JPanel jPanel3;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            if(TCari.getText().trim().equals("")){
                ps=koneksi.prepareStatement(
                    "select surat_pulang_atas_permintaan_sendiri.no_surat,reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,reg_periksa.umurdaftar,"+
                    "reg_periksa.sttsumur,pasien.jk,pasien.tgl_lahir,surat_pulang_atas_permintaan_sendiri.tgl_pulang,surat_pulang_atas_permintaan_sendiri.rs_pilihan,"+
                    "surat_pulang_atas_permintaan_sendiri.nama_pj,surat_pulang_atas_permintaan_sendiri.lahir,surat_pulang_atas_permintaan_sendiri.umur,"+
                    "surat_pulang_atas_permintaan_sendiri.jkpj,surat_pulang_atas_permintaan_sendiri.alamat,surat_pulang_atas_permintaan_sendiri.hubungan,"+
                    "surat_pulang_atas_permintaan_sendiri.saksi_keluarga,surat_pulang_atas_permintaan_sendiri.nip,petugas.nama from surat_pulang_atas_permintaan_sendiri "+
                    "inner join reg_periksa on surat_pulang_atas_permintaan_sendiri.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join petugas on surat_pulang_atas_permintaan_sendiri.nip=petugas.nip where "+
                    "surat_pulang_atas_permintaan_sendiri.tgl_pulang between ? and ? order by surat_pulang_atas_permintaan_sendiri.tgl_pulang");
            }else{
                ps=koneksi.prepareStatement(
                    "select surat_pulang_atas_permintaan_sendiri.no_surat,reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,reg_periksa.umurdaftar,"+
                    "reg_periksa.sttsumur,pasien.jk,pasien.tgl_lahir,surat_pulang_atas_permintaan_sendiri.tgl_pulang,surat_pulang_atas_permintaan_sendiri.rs_pilihan,"+
                    "surat_pulang_atas_permintaan_sendiri.nama_pj,surat_pulang_atas_permintaan_sendiri.lahir,surat_pulang_atas_permintaan_sendiri.umur,"+
                    "surat_pulang_atas_permintaan_sendiri.jkpj,surat_pulang_atas_permintaan_sendiri.alamat,surat_pulang_atas_permintaan_sendiri.hubungan,"+
                    "surat_pulang_atas_permintaan_sendiri.saksi_keluarga,surat_pulang_atas_permintaan_sendiri.nip,petugas.nama from surat_pulang_atas_permintaan_sendiri "+
                    "inner join reg_periksa on surat_pulang_atas_permintaan_sendiri.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join petugas on surat_pulang_atas_permintaan_sendiri.nip=petugas.nip where "+
                    "surat_pulang_atas_permintaan_sendiri.tgl_pulang between ? and ? and "+
                    "(reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or "+
                    "surat_pulang_atas_permintaan_sendiri.alamat like ? or surat_pulang_atas_permintaan_sendiri.nama_pj like ? or "+
                    "surat_pulang_atas_permintaan_sendiri.nip like ? or petugas.nama like ?) "+
                    "order by surat_pulang_atas_permintaan_sendiri.tgl_pulang");
            }
                
            try {
                if(TCari.getText().toString().trim().equals("")){
                    ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                    ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                }else{
                    ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                    ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
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
                        rs.getString("no_surat"),rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),
                        rs.getString("umurdaftar")+" "+rs.getString("sttsumur"),rs.getString("jk"),rs.getDate("tgl_lahir"),
                        rs.getString("tgl_pulang"),rs.getString("rs_pilihan"),rs.getString("nama_pj"),rs.getString("lahir"),
                        rs.getString("umur"),rs.getString("jkpj"),rs.getString("alamat"),rs.getString("hubungan"),
                        rs.getString("saksi_keluarga"),rs.getString("nip"),rs.getString("nama") 
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
        Pilihan.setText("");
        NamaPihak2.setText("");
        UmurPJ.setText("0");
        AlamatPj.setText("");
        Hubungan.setSelectedIndex(0);
        JKPJ.setSelectedIndex(0);
        SaksiKeluarga.setText("");
        Pilihan.requestFocus();
        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(surat_pulang_atas_permintaan_sendiri.no_surat,3),signed)),0) from surat_pulang_atas_permintaan_sendiri where surat_pulang_atas_permintaan_sendiri.tgl_pulang='"+Valid.SetTgl(Tanggal.getSelectedItem()+"")+"' ",
                "APS"+Tanggal.getSelectedItem().toString().substring(6,10)+Tanggal.getSelectedItem().toString().substring(3,5)+Tanggal.getSelectedItem().toString().substring(0,2),3,NoSurat);
    }

 
    private void getData() {
         if(tbObat.getSelectedRow()!= -1){
            NoSurat.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            Umur.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString());
            JK.setText(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
            LahirPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString());
            Pilihan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            NamaPihak2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString()); 
            UmurPJ.setText(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString()); 
            JKPJ.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString().replaceAll("L","Laki-laki").replaceAll("P","Perempuan")); 
            AlamatPj.setText(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString()); 
            Hubungan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString()); 
            SaksiKeluarga.setText(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString()); 
            Valid.SetTgl(Tanggal,tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());
            Valid.SetTgl(TglLahir,tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
        }
    }

    private void isRawat() {
        try {
            ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,reg_periksa.tgl_registrasi,"+
                    "reg_periksa.umurdaftar,reg_periksa.sttsumur from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "where reg_periksa.no_rawat=?");
            try {
                ps.setString(1,TNoRw.getText());
                rs=ps.executeQuery();
                if(rs.next()){
                    TNoRM.setText(rs.getString("no_rkm_medis"));
                    DTPCari1.setDate(rs.getDate("tgl_registrasi"));
                    TPasien.setText(rs.getString("nm_pasien"));
                    JK.setText(rs.getString("jk"));
                    LahirPasien.setText(rs.getString("tgl_lahir"));
                    Umur.setText(rs.getString("umurdaftar")+" "+rs.getString("sttsumur"));
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
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,235));
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
        BtnSimpan.setEnabled(akses.getsurat_pulang_atas_permintaan_sendiri());
        BtnHapus.setEnabled(akses.getsurat_pulang_atas_permintaan_sendiri());
        BtnEdit.setEnabled(akses.getsurat_pulang_atas_permintaan_sendiri());
        BtnPrint.setEnabled(akses.getsurat_pulang_atas_permintaan_sendiri()); 
        if(akses.getjml2()>=1){
            KdPetugas.setEditable(false);
            BtnPetugas.setEnabled(false);
            KdPetugas.setText(akses.getkode());
            NmPetugas.setText(Sequel.CariPetugas(KdPetugas.getText()));
            if(NmPetugas.getText().equals("")){
                KdPetugas.setText("");
                JOptionPane.showMessageDialog(null,"User login bukan petugas...!!");
            }
        }            
    }
  
    private void ganti() {
        if(Sequel.mengedittf("surat_pulang_atas_permintaan_sendiri","no_surat=?","no_surat=?,no_rawat=?,tgl_pulang=?,rs_pilihan=?,nama_pj=?,lahir=?,umur=?,jkpj=?,alamat=?,hubungan=?,saksi_keluarga=?,nip=?",13,new String[]{
            NoSurat.getText(),TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+""),Pilihan.getText(),NamaPihak2.getText(),
            Valid.SetTgl(TglLahir.getSelectedItem()+""),UmurPJ.getText(),JKPJ.getSelectedItem().toString().substring(0,1),
            AlamatPj.getText(),Hubungan.getSelectedItem().toString(),SaksiKeluarga.getText(),KdPetugas.getText(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
        })==true){
            tbObat.setValueAt(NoSurat.getText(),tbObat.getSelectedRow(),0);
            tbObat.setValueAt(TNoRw.getText(),tbObat.getSelectedRow(),1);
            tbObat.setValueAt(TNoRM.getText(),tbObat.getSelectedRow(),2);
            tbObat.setValueAt(TPasien.getText(),tbObat.getSelectedRow(),3);
            tbObat.setValueAt(Umur.getText(),tbObat.getSelectedRow(),4);
            tbObat.setValueAt(JK.getText(),tbObat.getSelectedRow(),5);
            tbObat.setValueAt(LahirPasien.getText(),tbObat.getSelectedRow(),6);
            tbObat.setValueAt(Valid.SetTgl(Tanggal.getSelectedItem()+""),tbObat.getSelectedRow(),7);
            tbObat.setValueAt(Pilihan.getText(),tbObat.getSelectedRow(),8);
            tbObat.setValueAt(NamaPihak2.getText(),tbObat.getSelectedRow(),9);
            tbObat.setValueAt(Valid.SetTgl(TglLahir.getSelectedItem()+""),tbObat.getSelectedRow(),10);
            tbObat.setValueAt(UmurPJ.getText(),tbObat.getSelectedRow(),11);
            tbObat.setValueAt(JKPJ.getSelectedItem().toString().substring(0,1),tbObat.getSelectedRow(),12);
            tbObat.setValueAt(AlamatPj.getText(),tbObat.getSelectedRow(),13);
            tbObat.setValueAt(Hubungan.getSelectedItem().toString(),tbObat.getSelectedRow(),14);
            tbObat.setValueAt(SaksiKeluarga.getText(),tbObat.getSelectedRow(),15);
            tbObat.setValueAt(KdPetugas.getText(),tbObat.getSelectedRow(),16);
            tbObat.setValueAt(NmPetugas.getText(),tbObat.getSelectedRow(),17);
            emptTeks();
        }
    }

    private void hapus() {
        if(Sequel.queryu2tf("delete from surat_pulang_atas_permintaan_sendiri where no_surat=?",1,new String[]{
            tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
        })==true){
            tabMode.removeRow(tbObat.getSelectedRow());
            LCount.setText(""+tabMode.getRowCount());
            emptTeks();
        }else{
            JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
        }
    }
    
    private void isPhoto(){
        if(ChkAccor.isSelected()==true){
            ChkAccor.setVisible(false);
            PanelAccor.setPreferredSize(new Dimension(430,HEIGHT));
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
            lokasifile="";
            try {
                ps=koneksi.prepareStatement("select surat_pulang_atas_permintaan_sendiri_pembuat_pernyataan.photo from surat_pulang_atas_permintaan_sendiri_pembuat_pernyataan where surat_pulang_atas_permintaan_sendiri_pembuat_pernyataan.no_surat=?");
                try {
                    ps.setString(1,tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
                    rs=ps.executeQuery();
                    if(rs.next()){
                        if(rs.getString("photo").equals("")||rs.getString("photo").equals("-")){
                            lokasifile="";
                        }else{
                            lokasifile=rs.getString("photo");
                        }  
                    }else{
                        lokasifile="";
                    }
                } catch (Exception e) {
                    lokasifile="";
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
            
            lokasifile2="";
            try {
                ps=koneksi.prepareStatement("select surat_pulang_atas_permintaan_sendiri_saksi_keluarga.photo from surat_pulang_atas_permintaan_sendiri_saksi_keluarga where surat_pulang_atas_permintaan_sendiri_saksi_keluarga.no_surat=?");
                try {
                    ps.setString(1,tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
                    rs=ps.executeQuery();
                    if(rs.next()){
                        if(rs.getString("photo").equals("")||rs.getString("photo").equals("-")){
                            lokasifile2="";
                        }else{
                            lokasifile2=rs.getString("photo");
                        }  
                    }else{
                        lokasifile2="";
                    }
                } catch (Exception e) {
                    lokasifile2="";
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
            
            if(TabData.getSelectedIndex()==0){
                if(lokasifile.equals("")){
                    LoadHTML2.setText("<html><body><center><br><br><font face='tahoma' size='2' color='#434343'>Kosong</font></center></body></html>");
                }else{
                    LoadHTML2.setText("<html><body><center><img src='http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/pulangaps/"+lokasifile+"' alt='photo' width='450' height='550'/></center></body></html>");
                }  
            }else{
                if(lokasifile2.equals("")){
                    LoadHTML3.setText("<html><body><center><br><br><font face='tahoma' size='2' color='#434343'>Kosong</font></center></body></html>");
                }else{
                    LoadHTML3.setText("<html><body><center><img src='http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/pulangaps/"+lokasifile2+"' alt='photo' width='450' height='550'/></center></body></html>");
                } 
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




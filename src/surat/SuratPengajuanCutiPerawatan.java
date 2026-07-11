package surat;

import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.KeyAdapter;
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
import kepegawaian.DlgCariPetugas;
import kepegawaian.DlgCariDokter;
import simrskhanza.DlgCariBangsal;


public final class SuratPengajuanCutiPerawatan extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;
    private DlgCariPetugas petugas;
    private DlgCariDokter dokter;
    private DlgCariBangsal kamar;
    private StringBuilder htmlContent;
    private String finger="";
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private volatile boolean ceksukses = false;
    
    public SuratPengajuanCutiPerawatan(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(628,674);
        
        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Pernyataan","No.Rawat","No.R.M.","Nama Pasien","Umur","J.K.","Tgl.Lahir","Tanggal",
            "Kode Ruang","Nama Ruang","Pembuat Pengajuan","Tgl.Lahir P.P.","J.K.P.P.","No.HP P.P",
            "Hubungan P.P.","Alamat P.P.","Mulai Cuti","Alasan Cuti","Kembali","Alamat Selama Cuti",
            "NIP","Pegawai","Kode Dokter","Dokter Penanggung Jawab"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 24; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(100);
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
                column.setPreferredWidth(115);
            }else if(i==8){
                column.setPreferredWidth(67);
            }else if(i==9){
                column.setPreferredWidth(130);
            }else if(i==10){
                column.setPreferredWidth(150);
            }else if(i==11){
                column.setPreferredWidth(70);
            }else if(i==12){
                column.setPreferredWidth(45);
            }else if(i==13){
                column.setPreferredWidth(90);
            }else if(i==14){
                column.setPreferredWidth(90);
            }else if(i==15){
                column.setPreferredWidth(150);
            }else if(i==16){
                column.setPreferredWidth(115);
            }else if(i==17){
                column.setPreferredWidth(150);
            }else if(i==18){
                column.setPreferredWidth(115);
            }else if(i==19){
                column.setPreferredWidth(150);
            }else if(i==20){
                column.setPreferredWidth(90);
            }else if(i==21){
                column.setPreferredWidth(150);
            }else if(i==22){
                column.setPreferredWidth(90);
            }else if(i==23){
                column.setPreferredWidth(150);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());
        
        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));    
        NIK.setDocument(new batasInput((byte)20).getKata(NIK));  
        NoSurat.setDocument(new batasInput((byte)20).getKata(NoSurat));
        PembuatPengajuan.setDocument(new batasInput((int)50).getKata(PembuatPengajuan));
        AlamatPembuatPengajuan.setDocument(new batasInput((int)100).getKata(AlamatPembuatPengajuan));
        NoHpPembuatPengajuan.setDocument(new batasInput((int)30).getKata(NoHpPembuatPengajuan));
        AlasanCuti.setDocument(new batasInput((int)100).getKata(AlasanCuti));
        AlamatSelamaCuti.setDocument(new batasInput((int)100).getKata(AlamatSelamaCuti));
        TCari.setDocument(new batasInput((int)100).getKata(TCari));  
        
        ChkInput.setSelected(false);
        isForm();
        
        ChkAccor.setSelected(false);
        isPhoto();
        
        HTMLEditorKit kit = new HTMLEditorKit();
        LoadHTML2.setEditable(true);
        LoadHTML2.setEditorKit(kit);
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
        LoadHTML2.setDocument(doc);
        
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Jk = new widget.TextBox();
        Umur = new widget.TextBox();
        LoadHTML = new widget.editorpane();
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
        jLabel17 = new widget.Label();
        LahirPasien = new widget.TextBox();
        jLabel18 = new widget.Label();
        NIK = new widget.TextBox();
        NamaPegawai = new widget.TextBox();
        BtnPegawai = new widget.Button();
        jLabel16 = new widget.Label();
        Tanggal = new widget.Tanggal();
        jLabel3 = new widget.Label();
        NoSurat = new widget.TextBox();
        jLabel22 = new widget.Label();
        KodeDokter = new widget.TextBox();
        NamaDokter = new widget.TextBox();
        BtnDokter = new widget.Button();
        KodeRuang = new widget.TextBox();
        NamaRuang = new widget.TextBox();
        BtnRuang = new widget.Button();
        jLabel5 = new widget.Label();
        jSeparator1 = new javax.swing.JSeparator();
        HubunganDenganPasien = new widget.ComboBox();
        jLabel13 = new widget.Label();
        PembuatPengajuan = new widget.TextBox();
        jLabel37 = new widget.Label();
        jLabel38 = new widget.Label();
        TanggalLahirPembuatPengajuan = new widget.Tanggal();
        jLabel14 = new widget.Label();
        JKPembuatPengajuan = new widget.ComboBox();
        jLabel39 = new widget.Label();
        AlamatPembuatPengajuan = new widget.TextBox();
        jLabel8 = new widget.Label();
        NoHpPembuatPengajuan = new widget.TextBox();
        jLabel40 = new widget.Label();
        AlasanCuti = new widget.TextBox();
        jLabel20 = new widget.Label();
        MulaiCuti = new widget.Tanggal();
        jLabel41 = new widget.Label();
        AlamatSelamaCuti = new widget.TextBox();
        jLabel23 = new widget.Label();
        Kembali = new widget.Tanggal();
        ChkInput = new widget.CekBox();
        PanelAccor = new widget.PanelBiasa();
        ChkAccor = new widget.CekBox();
        FormPhoto = new widget.PanelBiasa();
        FormPass3 = new widget.PanelBiasa();
        btnAmbil = new widget.Button();
        BtnRefreshPhoto1 = new widget.Button();
        btnCetakLembar = new widget.Button();
        Scroll5 = new widget.ScrollPane();
        LoadHTML2 = new widget.editorpane();

        Jk.setEditable(false);
        Jk.setHighlighter(null);
        Jk.setName("Jk"); // NOI18N

        Umur.setEditable(false);
        Umur.setHighlighter(null);
        Umur.setName("Umur"); // NOI18N

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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Pengajuan Cuti Perawatan Pasien ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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

        jLabel19.setText("Tanggal :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(57, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "11-07-2026" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "11-07-2026" }));
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
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 255));
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(100, 165));
        FormInput.setLayout(null);

        jLabel4.setText("No.Rawat :");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(0, 10, 65, 23);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(69, 10, 136, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        TPasien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPasienKeyPressed(evt);
            }
        });
        FormInput.add(TPasien);
        TPasien.setBounds(320, 10, 290, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        FormInput.add(TNoRM);
        TNoRM.setBounds(207, 10, 111, 23);

        jLabel17.setText("Tgl.Lahir :");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(616, 10, 60, 23);

        LahirPasien.setHighlighter(null);
        LahirPasien.setName("LahirPasien"); // NOI18N
        FormInput.add(LahirPasien);
        LahirPasien.setBounds(680, 10, 85, 23);

        jLabel18.setText("Petugas :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(389, 70, 60, 23);

        NIK.setEditable(false);
        NIK.setHighlighter(null);
        NIK.setName("NIK"); // NOI18N
        FormInput.add(NIK);
        NIK.setBounds(453, 70, 100, 23);

        NamaPegawai.setEditable(false);
        NamaPegawai.setName("NamaPegawai"); // NOI18N
        FormInput.add(NamaPegawai);
        NamaPegawai.setBounds(555, 70, 180, 23);

        BtnPegawai.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPegawai.setMnemonic('2');
        BtnPegawai.setToolTipText("ALt+2");
        BtnPegawai.setName("BtnPegawai"); // NOI18N
        BtnPegawai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPegawaiActionPerformed(evt);
            }
        });
        BtnPegawai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPegawaiKeyPressed(evt);
            }
        });
        FormInput.add(BtnPegawai);
        BtnPegawai.setBounds(737, 70, 28, 23);

        jLabel16.setText("Tanggal :");
        jLabel16.setName("jLabel16"); // NOI18N
        jLabel16.setVerifyInputWhenFocusTarget(false);
        FormInput.add(jLabel16);
        jLabel16.setBounds(0, 40, 65, 23);

        Tanggal.setForeground(new java.awt.Color(50, 70, 50));
        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "11-07-2026 06:47:02" }));
        Tanggal.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.setOpaque(false);
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeyPressed(evt);
            }
        });
        FormInput.add(Tanggal);
        Tanggal.setBounds(69, 40, 130, 23);

        jLabel3.setText("Nomor :");
        jLabel3.setName("jLabel3"); // NOI18N
        FormInput.add(jLabel3);
        jLabel3.setBounds(575, 40, 56, 23);

        NoSurat.setHighlighter(null);
        NoSurat.setName("NoSurat"); // NOI18N
        NoSurat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoSuratKeyPressed(evt);
            }
        });
        FormInput.add(NoSurat);
        NoSurat.setBounds(635, 40, 130, 23);

        jLabel22.setText("D.P.J.P :");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(0, 70, 65, 23);

        KodeDokter.setEditable(false);
        KodeDokter.setHighlighter(null);
        KodeDokter.setName("KodeDokter"); // NOI18N
        FormInput.add(KodeDokter);
        KodeDokter.setBounds(69, 70, 100, 23);

        NamaDokter.setEditable(false);
        NamaDokter.setName("NamaDokter"); // NOI18N
        FormInput.add(NamaDokter);
        NamaDokter.setBounds(171, 70, 180, 23);

        BtnDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter.setMnemonic('2');
        BtnDokter.setToolTipText("ALt+2");
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
        BtnDokter.setBounds(353, 70, 28, 23);

        KodeRuang.setEditable(false);
        KodeRuang.setName("KodeRuang"); // NOI18N
        KodeRuang.setPreferredSize(new java.awt.Dimension(75, 23));
        FormInput.add(KodeRuang);
        KodeRuang.setBounds(259, 40, 70, 23);

        NamaRuang.setEditable(false);
        NamaRuang.setName("NamaRuang"); // NOI18N
        NamaRuang.setPreferredSize(new java.awt.Dimension(215, 23));
        FormInput.add(NamaRuang);
        NamaRuang.setBounds(331, 40, 214, 23);

        BtnRuang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnRuang.setMnemonic('3');
        BtnRuang.setToolTipText("Alt+3");
        BtnRuang.setName("BtnRuang"); // NOI18N
        BtnRuang.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnRuang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRuangActionPerformed(evt);
            }
        });
        BtnRuang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnRuangKeyPressed(evt);
            }
        });
        FormInput.add(BtnRuang);
        BtnRuang.setBounds(547, 40, 28, 23);

        jLabel5.setText("Ruang :");
        jLabel5.setName("jLabel5"); // NOI18N
        FormInput.add(jLabel5);
        jLabel5.setBounds(200, 40, 55, 23);

        jSeparator1.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator1.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator1.setName("jSeparator1"); // NOI18N
        FormInput.add(jSeparator1);
        jSeparator1.setBounds(0, 100, 880, 1);

        HubunganDenganPasien.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Diri Sendiri", "Istri", "Suami", "Kerabat", "Orang Tua", "Anak", "Saudara Kandung", "Teman", "Lain-lain" }));
        HubunganDenganPasien.setName("HubunganDenganPasien"); // NOI18N
        HubunganDenganPasien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HubunganDenganPasienKeyPressed(evt);
            }
        });
        FormInput.add(HubunganDenganPasien);
        HubunganDenganPasien.setBounds(91, 140, 135, 23);

        jLabel13.setText("Hubungan :");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(0, 140, 87, 23);

        PembuatPengajuan.setHighlighter(null);
        PembuatPengajuan.setName("PembuatPengajuan"); // NOI18N
        PembuatPengajuan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PembuatPengajuanKeyPressed(evt);
            }
        });
        FormInput.add(PembuatPengajuan);
        PembuatPengajuan.setBounds(91, 110, 183, 23);

        jLabel37.setText("Diajukan Oleh :");
        jLabel37.setName("jLabel37"); // NOI18N
        FormInput.add(jLabel37);
        jLabel37.setBounds(0, 110, 87, 23);

        jLabel38.setText("Tgl.Lahir :");
        jLabel38.setName("jLabel38"); // NOI18N
        FormInput.add(jLabel38);
        jLabel38.setBounds(271, 110, 70, 23);

        TanggalLahirPembuatPengajuan.setForeground(new java.awt.Color(50, 70, 50));
        TanggalLahirPembuatPengajuan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "11-07-2026" }));
        TanggalLahirPembuatPengajuan.setDisplayFormat("dd-MM-yyyy");
        TanggalLahirPembuatPengajuan.setName("TanggalLahirPembuatPengajuan"); // NOI18N
        TanggalLahirPembuatPengajuan.setOpaque(false);
        TanggalLahirPembuatPengajuan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalLahirPembuatPengajuanKeyPressed(evt);
            }
        });
        FormInput.add(TanggalLahirPembuatPengajuan);
        TanggalLahirPembuatPengajuan.setBounds(345, 110, 90, 23);

        jLabel14.setText("J.K. :");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(436, 110, 40, 23);

        JKPembuatPengajuan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Laki-laki", "Perempuan" }));
        JKPembuatPengajuan.setName("JKPembuatPengajuan"); // NOI18N
        JKPembuatPengajuan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JKPembuatPengajuanKeyPressed(evt);
            }
        });
        FormInput.add(JKPembuatPengajuan);
        JKPembuatPengajuan.setBounds(480, 110, 105, 23);

        jLabel39.setText("Alamat :");
        jLabel39.setName("jLabel39"); // NOI18N
        FormInput.add(jLabel39);
        jLabel39.setBounds(231, 140, 55, 23);

        AlamatPembuatPengajuan.setHighlighter(null);
        AlamatPembuatPengajuan.setName("AlamatPembuatPengajuan"); // NOI18N
        AlamatPembuatPengajuan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlamatPembuatPengajuanKeyPressed(evt);
            }
        });
        FormInput.add(AlamatPembuatPengajuan);
        AlamatPembuatPengajuan.setBounds(290, 140, 475, 23);

        jLabel8.setText("Phone :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(591, 110, 50, 23);

        NoHpPembuatPengajuan.setHighlighter(null);
        NoHpPembuatPengajuan.setName("NoHpPembuatPengajuan"); // NOI18N
        NoHpPembuatPengajuan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoHpPembuatPengajuanKeyPressed(evt);
            }
        });
        FormInput.add(NoHpPembuatPengajuan);
        NoHpPembuatPengajuan.setBounds(645, 110, 120, 23);

        jLabel40.setText("Alasan Cuti :");
        jLabel40.setName("jLabel40"); // NOI18N
        FormInput.add(jLabel40);
        jLabel40.setBounds(217, 170, 84, 23);

        AlasanCuti.setHighlighter(null);
        AlasanCuti.setName("AlasanCuti"); // NOI18N
        AlasanCuti.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlasanCutiKeyPressed(evt);
            }
        });
        FormInput.add(AlasanCuti);
        AlasanCuti.setBounds(305, 170, 460, 23);

        jLabel20.setText("Mulai Cuti :");
        jLabel20.setName("jLabel20"); // NOI18N
        jLabel20.setVerifyInputWhenFocusTarget(false);
        FormInput.add(jLabel20);
        jLabel20.setBounds(0, 170, 87, 23);

        MulaiCuti.setForeground(new java.awt.Color(50, 70, 50));
        MulaiCuti.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "11-07-2026 06:47:02" }));
        MulaiCuti.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        MulaiCuti.setName("MulaiCuti"); // NOI18N
        MulaiCuti.setOpaque(false);
        MulaiCuti.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MulaiCutiKeyPressed(evt);
            }
        });
        FormInput.add(MulaiCuti);
        MulaiCuti.setBounds(91, 170, 130, 23);

        jLabel41.setText("Alamat Selama Cuti :");
        jLabel41.setName("jLabel41"); // NOI18N
        FormInput.add(jLabel41);
        jLabel41.setBounds(220, 200, 121, 23);

        AlamatSelamaCuti.setHighlighter(null);
        AlamatSelamaCuti.setName("AlamatSelamaCuti"); // NOI18N
        AlamatSelamaCuti.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlamatSelamaCutiKeyPressed(evt);
            }
        });
        FormInput.add(AlamatSelamaCuti);
        AlamatSelamaCuti.setBounds(345, 200, 420, 23);

        jLabel23.setText("Kembali :");
        jLabel23.setName("jLabel23"); // NOI18N
        jLabel23.setVerifyInputWhenFocusTarget(false);
        FormInput.add(jLabel23);
        jLabel23.setBounds(0, 200, 87, 23);

        Kembali.setForeground(new java.awt.Color(50, 70, 50));
        Kembali.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "11-07-2026 06:47:02" }));
        Kembali.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        Kembali.setName("Kembali"); // NOI18N
        Kembali.setOpaque(false);
        Kembali.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KembaliKeyPressed(evt);
            }
        });
        FormInput.add(Kembali);
        Kembali.setBounds(91, 200, 130, 23);

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
        FormPhoto.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1), " Bukti Pengajuan : ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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

        btnCetakLembar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item (copy).png"))); // NOI18N
        btnCetakLembar.setMnemonic('U');
        btnCetakLembar.setText("Cetak");
        btnCetakLembar.setToolTipText("Alt+P");
        btnCetakLembar.setName("btnCetakLembar"); // NOI18N
        btnCetakLembar.setPreferredSize(new java.awt.Dimension(100, 30));
        btnCetakLembar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCetakLembarActionPerformed(evt);
            }
        });
        FormPass3.add(btnCetakLembar);

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

        internalFrame1.add(PanelAccor, java.awt.BorderLayout.EAST);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);
        internalFrame1.getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"Pasien");
        }else if(NamaPegawai.getText().trim().equals("")){
            Valid.textKosong(BtnPegawai,"Petugas");
        }else if(NamaDokter.getText().trim().equals("")){
            Valid.textKosong(BtnDokter,"Dokter Penanggung Jawab");
        }else if(NoSurat.getText().trim().equals("")){
            Valid.textKosong(NoSurat,"No.Pengajuan");
        }else if(KodeRuang.getText().trim().equals("")||NamaRuang.getText().trim().equals("")){
            Valid.textKosong(KodeRuang,"Ruang");
        }else if(AlasanCuti.getText().trim().equals("")){
            Valid.textKosong(AlasanCuti,"Alasan Cuti");
        }else if(PembuatPengajuan.getText().trim().equals("")){
            Valid.textKosong(PembuatPengajuan,"Pembuat Pengajuan");
        }else if(NoHpPembuatPengajuan.getText().trim().equals("")){
            Valid.textKosong(NoHpPembuatPengajuan,"No.HP Pembuat Pengajuan");
        }else if(AlamatPembuatPengajuan.getText().trim().equals("")){
            Valid.textKosong(AlamatPembuatPengajuan,"Alamat Pembuat Pengajuan");
        }else{
            if(Sequel.menyimpantf("surat_pengajuan_cuti_pasien","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","Data",16,new String[]{
                    NoSurat.getText(),TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19),
                    KodeRuang.getText(),PembuatPengajuan.getText(),AlamatPembuatPengajuan.getText(),Valid.SetTgl(TanggalLahirPembuatPengajuan.getSelectedItem()+""), 
                    JKPembuatPengajuan.getSelectedItem().toString().substring(0,1),HubunganDenganPasien.getSelectedItem().toString(),NoHpPembuatPengajuan.getText(), 
                    AlasanCuti.getText(),Valid.SetTgl(MulaiCuti.getSelectedItem()+"")+" "+MulaiCuti.getSelectedItem().toString().substring(11,19),AlamatSelamaCuti.getText(), 
                    Valid.SetTgl(Kembali.getSelectedItem()+"")+" "+Kembali.getSelectedItem().toString().substring(11,19),NIK.getText(),KodeDokter.getText()
                })==true){
                tabMode.addRow(new String[]{
                    NoSurat.getText(),TNoRw.getText(),TNoRM.getText(),TPasien.getText(),Umur.getText(),Jk.getText(),LahirPasien.getText(),
                    Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19),KodeRuang.getText(),
                    NamaRuang.getText(),PembuatPengajuan.getText(),Valid.SetTgl(TanggalLahirPembuatPengajuan.getSelectedItem()+""),
                    JKPembuatPengajuan.getSelectedItem().toString().substring(0,1),NoHpPembuatPengajuan.getText(),HubunganDenganPasien.getSelectedItem().toString(),
                    AlamatPembuatPengajuan.getText(),Valid.SetTgl(MulaiCuti.getSelectedItem()+"")+" "+MulaiCuti.getSelectedItem().toString().substring(11,19),
                    AlasanCuti.getText(),Valid.SetTgl(Kembali.getSelectedItem()+"")+" "+Kembali.getSelectedItem().toString().substring(11,19),
                    AlamatSelamaCuti.getText(),NIK.getText(),NamaPegawai.getText(),KodeDokter.getText(),NamaDokter.getText()
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
            Valid.pindah(evt,AlamatSelamaCuti,BtnBatal);
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
                if(NIK.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString())){
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
            Valid.textKosong(TNoRw,"Pasien");
        }else if(NamaPegawai.getText().trim().equals("")){
            Valid.textKosong(BtnPegawai,"Petugas");
        }else if(NamaDokter.getText().trim().equals("")){
            Valid.textKosong(BtnDokter,"Dokter Penanggung Jawab");
        }else if(NoSurat.getText().trim().equals("")){
            Valid.textKosong(NoSurat,"No.Pengajuan");
        }else if(KodeRuang.getText().trim().equals("")||NamaRuang.getText().trim().equals("")){
            Valid.textKosong(KodeRuang,"Ruang");
        }else if(AlasanCuti.getText().trim().equals("")){
            Valid.textKosong(AlasanCuti,"Alasan Cuti");
        }else if(PembuatPengajuan.getText().trim().equals("")){
            Valid.textKosong(PembuatPengajuan,"Pembuat Pengajuan");
        }else if(NoHpPembuatPengajuan.getText().trim().equals("")){
            Valid.textKosong(NoHpPembuatPengajuan,"No.HP Pembuat Pengajuan");
        }else if(AlamatPembuatPengajuan.getText().trim().equals("")){
            Valid.textKosong(AlamatPembuatPengajuan,"Alamat Pembuat Pengajuan");
        }else{
            if(tbObat.getSelectedRow()>-1){
                if(akses.getkode().equals("Admin Utama")){
                    ganti();
                }else{
                    if(NIK.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString())){
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
            try{
                htmlContent = new StringBuilder();
                htmlContent.append(                             
                    "<tr class='isi'>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>No.Pernyataan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>No.Rawat</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>No.R.M.</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Nama Pasien</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Umur</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>J.K.</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Tgl.Lahir</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Tanggal</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Kode Ruang</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Nama Ruang</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Pembuat Pengajuan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Tgl.Lahir P.P.</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>J.K.P.P.</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>No.HP P.P</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Hubungan P.P.</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Alamat P.P.</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Mulai Cuti</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Alasan Cuti</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Kembali</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Alamat Selama Cuti</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>NIP</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Pegawai</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Kode Dokter</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Dokter Penanggung Jawab</b></td>"+
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
                        "</tr>");
                }
                LoadHTML.setText(
                    "<html>"+
                      "<table width='2400px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
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

                File f = new File("DataPengajuanCutiPerawatanPasien.html");            
                BufferedWriter bw = new BufferedWriter(new FileWriter(f));            
                bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                            "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                            "<table width='2400px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                "<tr class='isi2'>"+
                                    "<td valign='top' align='center'>"+
                                        "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                        akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                        akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                        "<font size='2' face='Tahoma'>DATA PENGAJUAN CUTI PERAWATAN PASIEN<br><br></font>"+        
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

    private void BtnPegawaiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPegawaiKeyPressed
        Valid.pindah(evt,BtnDokter,PembuatPengajuan);
    }//GEN-LAST:event_BtnPegawaiKeyPressed

    private void BtnPegawaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPegawaiActionPerformed
        if (petugas == null || !petugas.isDisplayable()) {
            petugas=new DlgCariPetugas(null,false);
            petugas.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            petugas.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    if(petugas.getTable().getSelectedRow()!= -1){                   
                        NIK.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                        NamaPegawai.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                    }  
                    NIK.requestFocus();
                    petugas=null;
                }
            });
            
            petugas.getTable().addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        petugas.dispose();
                    }
                }
            });

            petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            petugas.setLocationRelativeTo(internalFrame1);
        }
        if (petugas == null) return;
        if (!petugas.isVisible()) {
            petugas.emptTeks();
        }
        
        if (petugas.isVisible()) {
            petugas.toFront();
            return;
        }
        petugas.setVisible(true);
    }//GEN-LAST:event_BtnPegawaiActionPerformed

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

    private void TanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKeyPressed
        Valid.pindah2(evt,TCari,BtnPegawai);
    }//GEN-LAST:event_TanggalKeyPressed

    private void NoSuratKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoSuratKeyPressed
        Valid.pindah(evt,BtnRuang,BtnDokter);
    }//GEN-LAST:event_NoSuratKeyPressed

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
                Sequel.queryu("delete from antripengajuancutiperawatan");
                Sequel.queryu("insert into antripengajuancutiperawatan values('"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"','"+tbObat.getValueAt(tbObat.getSelectedRow(),1).toString()+"')");
                Sequel.queryu("delete from bukti_surat_pengajuan_cuti_pasien where no_surat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'");
            }else{
                JOptionPane.showMessageDialog(rootPane,"Silahkan anda pilih No.Pengajuan terlebih dahulu..!!");
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

    private void BtnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokterActionPerformed
        if (dokter == null || !dokter.isDisplayable()) {
            dokter=new DlgCariDokter(null,false);
            dokter.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            dokter.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    if(dokter.getTable().getSelectedRow()!= -1){
                        KodeDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                        NamaDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                        KodeDokter.requestFocus();
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
    }//GEN-LAST:event_BtnDokterActionPerformed

    private void BtnDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokterKeyPressed
        Valid.pindah(evt,NoSurat,PembuatPengajuan);
    }//GEN-LAST:event_BtnDokterKeyPressed

    private void btnCetakLembarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCetakLembarActionPerformed
        if(tbObat.getSelectedRow()>-1){
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
            finger=Sequel.cariIsi("select sha1(sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",NIK.getText());
            param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+NamaPegawai.getText()+"\nID "+(finger.equals("")?TPasien.getText():finger)+"\n"+Tanggal.getSelectedItem());
            System.out.println("http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/pengajuancutiperawatan/"+Sequel.cariIsi("select bukti_surat_pengajuan_cuti_pasien.photo from bukti_surat_pengajuan_cuti_pasien where bukti_surat_pengajuan_cuti_pasien.no_surat=?",tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()));
            param.put("photo","http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/pengajuancutiperawatan/"+Sequel.cariIsi("select bukti_surat_pengajuan_cuti_pasien.photo from bukti_surat_pengajuan_cuti_pasien where bukti_surat_pengajuan_cuti_pasien.no_surat=?",tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()));
            Valid.MyReportqry("rptSuratPengajuanCutiPerawatan.jasper","report","::[ Lembar Pengajuan Cuti Perawatan Pasien ]::",
                "select surat_pengajuan_cuti_pasien.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,reg_periksa.umurdaftar,reg_periksa.sttsumur,"+
                "concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab,', ',propinsi.nm_prop) as alamat_pasien," +
                "surat_pengajuan_cuti_pasien.no_surat,surat_pengajuan_cuti_pasien.tanggal,surat_pengajuan_cuti_pasien.kd_bangsal,bangsal.nm_bangsal,"+
                "surat_pengajuan_cuti_pasien.pembuat_pengajuan,surat_pengajuan_cuti_pasien.alamat_pembuat_pengajuan,surat_pengajuan_cuti_pasien.tgl_lahir_pembuat_pengajuan,"+
                "surat_pengajuan_cuti_pasien.jk_pembuat_pengajuan,surat_pengajuan_cuti_pasien.hubungan_pembuat_pengajuan,surat_pengajuan_cuti_pasien.notelp as telppembuatpengajuan,"+
                "surat_pengajuan_cuti_pasien.alasan_cuti,surat_pengajuan_cuti_pasien.mulai_cuti,surat_pengajuan_cuti_pasien.alamat_selama_cuti,"+
                "surat_pengajuan_cuti_pasien.kembali_dari_cuti,surat_pengajuan_cuti_pasien.nip,petugas.nama,pasien.no_tlp,"+
                "surat_pengajuan_cuti_pasien.kd_dokter,dokter.nm_dokter,bukti_surat_pengajuan_cuti_pasien.photo as photo_bukti "+
                "from surat_pengajuan_cuti_pasien "+
                "inner join reg_periksa on surat_pengajuan_cuti_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join petugas on surat_pengajuan_cuti_pasien.nip=petugas.nip "+
                "inner join bangsal on surat_pengajuan_cuti_pasien.kd_bangsal=bangsal.kd_bangsal "+
                "inner join dokter on surat_pengajuan_cuti_pasien.kd_dokter=dokter.kd_dokter "+
                "left join bukti_surat_pengajuan_cuti_pasien on bukti_surat_pengajuan_cuti_pasien.no_surat=surat_pengajuan_cuti_pasien.no_surat "+
                "inner join kelurahan on pasien.kd_kel=kelurahan.kd_kel "+
                "inner join kecamatan on pasien.kd_kec=kecamatan.kd_kec "+
                "inner join kabupaten on pasien.kd_kab=kabupaten.kd_kab "+
                "inner join propinsi on pasien.kd_prop=propinsi.kd_prop "+
                "where surat_pengajuan_cuti_pasien.no_surat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'",param);
        }else{
            JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data terlebih dahulu..!!!!");
        }
    }//GEN-LAST:event_btnCetakLembarActionPerformed

    private void BtnRuangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRuangActionPerformed
        if (kamar == null || !kamar.isDisplayable()) {
            kamar=new DlgCariBangsal(null,false);
            kamar.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            kamar.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    if(kamar.getTable().getSelectedRow()!= -1){
                        KodeRuang.setText(kamar.getTable().getValueAt(kamar.getTable().getSelectedRow(),0).toString());
                        NamaRuang.setText(kamar.getTable().getValueAt(kamar.getTable().getSelectedRow(),1).toString());
                        KodeRuang.requestFocus();
                    }  
                    kamar=null;
                }
            });
            kamar.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            kamar.setLocationRelativeTo(internalFrame1);
        }
            
        if (kamar == null) return;
        if (!kamar.isVisible()) {
            kamar.isCek();    
            kamar.emptTeks();
        }  
        if (kamar.isVisible()) {
            kamar.toFront();
            return;
        }    
        kamar.setVisible(true);
    }//GEN-LAST:event_BtnRuangActionPerformed

    private void BtnRuangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnRuangKeyPressed
        Valid.pindah(evt,TCari,NoSurat);
    }//GEN-LAST:event_BtnRuangKeyPressed

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

    private void HubunganDenganPasienKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HubunganDenganPasienKeyPressed
        Valid.pindah(evt,NoHpPembuatPengajuan,AlamatPembuatPengajuan);
    }//GEN-LAST:event_HubunganDenganPasienKeyPressed

    private void PembuatPengajuanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PembuatPengajuanKeyPressed
        Valid.pindah(evt,BtnDokter,TanggalLahirPembuatPengajuan);
    }//GEN-LAST:event_PembuatPengajuanKeyPressed

    private void TanggalLahirPembuatPengajuanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalLahirPembuatPengajuanKeyPressed
        Valid.pindah2(evt,PembuatPengajuan,JKPembuatPengajuan);
    }//GEN-LAST:event_TanggalLahirPembuatPengajuanKeyPressed

    private void JKPembuatPengajuanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JKPembuatPengajuanKeyPressed
        Valid.pindah(evt,TanggalLahirPembuatPengajuan,NoHpPembuatPengajuan);
    }//GEN-LAST:event_JKPembuatPengajuanKeyPressed

    private void AlamatPembuatPengajuanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlamatPembuatPengajuanKeyPressed
        Valid.pindah(evt,HubunganDenganPasien,MulaiCuti);
    }//GEN-LAST:event_AlamatPembuatPengajuanKeyPressed

    private void NoHpPembuatPengajuanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoHpPembuatPengajuanKeyPressed
        Valid.pindah(evt,JKPembuatPengajuan,HubunganDenganPasien);
    }//GEN-LAST:event_NoHpPembuatPengajuanKeyPressed

    private void AlasanCutiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlasanCutiKeyPressed
        Valid.pindah(evt,MulaiCuti,Kembali);
    }//GEN-LAST:event_AlasanCutiKeyPressed

    private void MulaiCutiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MulaiCutiKeyPressed
        Valid.pindah2(evt,AlamatPembuatPengajuan,AlasanCuti);
    }//GEN-LAST:event_MulaiCutiKeyPressed

    private void AlamatSelamaCutiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlamatSelamaCutiKeyPressed
        Valid.pindah(evt,Kembali,BtnSimpan);
    }//GEN-LAST:event_AlamatSelamaCutiKeyPressed

    private void KembaliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KembaliKeyPressed
        Valid.pindah2(evt,AlasanCuti,AlamatSelamaCuti);
    }//GEN-LAST:event_KembaliKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            SuratPengajuanCutiPerawatan dialog = new SuratPengajuanCutiPerawatan(new javax.swing.JFrame(), true);
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
    private widget.TextBox AlamatPembuatPengajuan;
    private widget.TextBox AlamatSelamaCuti;
    private widget.TextBox AlasanCuti;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnDokter;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPegawai;
    private widget.Button BtnPrint;
    private widget.Button BtnRefreshPhoto1;
    private widget.Button BtnRuang;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkAccor;
    private widget.CekBox ChkInput;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.PanelBiasa FormInput;
    private widget.PanelBiasa FormPass3;
    private widget.PanelBiasa FormPhoto;
    private widget.ComboBox HubunganDenganPasien;
    private widget.ComboBox JKPembuatPengajuan;
    private widget.TextBox Jk;
    private widget.Tanggal Kembali;
    private widget.TextBox KodeDokter;
    private widget.TextBox KodeRuang;
    private widget.Label LCount;
    private widget.TextBox LahirPasien;
    private widget.editorpane LoadHTML;
    private widget.editorpane LoadHTML2;
    private widget.Tanggal MulaiCuti;
    private widget.TextBox NIK;
    private widget.TextBox NamaDokter;
    private widget.TextBox NamaPegawai;
    private widget.TextBox NamaRuang;
    private widget.TextBox NoHpPembuatPengajuan;
    private widget.TextBox NoSurat;
    private widget.PanelBiasa PanelAccor;
    private javax.swing.JPanel PanelInput;
    private widget.TextBox PembuatPengajuan;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll5;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.Tanggal Tanggal;
    private widget.Tanggal TanggalLahirPembuatPengajuan;
    private widget.TextBox Umur;
    private widget.Button btnAmbil;
    private widget.Button btnCetakLembar;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel13;
    private widget.Label jLabel14;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
    private widget.Label jLabel18;
    private widget.Label jLabel19;
    private widget.Label jLabel20;
    private widget.Label jLabel21;
    private widget.Label jLabel22;
    private widget.Label jLabel23;
    private widget.Label jLabel3;
    private widget.Label jLabel37;
    private widget.Label jLabel38;
    private widget.Label jLabel39;
    private widget.Label jLabel4;
    private widget.Label jLabel40;
    private widget.Label jLabel41;
    private widget.Label jLabel5;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JSeparator jSeparator1;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            if(TCari.getText().trim().equals("")){
                ps=koneksi.prepareStatement(
                    "select surat_pengajuan_cuti_pasien.no_surat,reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,reg_periksa.umurdaftar,"+
                    "reg_periksa.sttsumur,pasien.jk,pasien.tgl_lahir,surat_pengajuan_cuti_pasien.tanggal,surat_pengajuan_cuti_pasien.kd_bangsal,bangsal.nm_bangsal,"+
                    "surat_pengajuan_cuti_pasien.pembuat_pengajuan,surat_pengajuan_cuti_pasien.alamat_pembuat_pengajuan,surat_pengajuan_cuti_pasien.tgl_lahir_pembuat_pengajuan,"+
                    "surat_pengajuan_cuti_pasien.jk_pembuat_pengajuan,surat_pengajuan_cuti_pasien.hubungan_pembuat_pengajuan,surat_pengajuan_cuti_pasien.notelp,"+
                    "surat_pengajuan_cuti_pasien.alasan_cuti,surat_pengajuan_cuti_pasien.mulai_cuti,surat_pengajuan_cuti_pasien.alamat_selama_cuti,"+
                    "surat_pengajuan_cuti_pasien.kembali_dari_cuti,surat_pengajuan_cuti_pasien.nip,petugas.nama,"+
                    "surat_pengajuan_cuti_pasien.kd_dokter,dokter.nm_dokter from surat_pengajuan_cuti_pasien "+
                    "inner join reg_periksa on surat_pengajuan_cuti_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join petugas on surat_pengajuan_cuti_pasien.nip=petugas.nip "+
                    "inner join bangsal on surat_pengajuan_cuti_pasien.kd_bangsal=bangsal.kd_bangsal "+
                    "inner join dokter on surat_pengajuan_cuti_pasien.kd_dokter=dokter.kd_dokter where "+
                    "surat_pengajuan_cuti_pasien.tanggal between ? and ? order by surat_pengajuan_cuti_pasien.tanggal");
            }else{
                ps=koneksi.prepareStatement(
                    "select surat_pengajuan_cuti_pasien.no_surat,reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,reg_periksa.umurdaftar,"+
                    "reg_periksa.sttsumur,pasien.jk,pasien.tgl_lahir,surat_pengajuan_cuti_pasien.tanggal,surat_pengajuan_cuti_pasien.kd_bangsal,bangsal.nm_bangsal,"+
                    "surat_pengajuan_cuti_pasien.pembuat_pengajuan,surat_pengajuan_cuti_pasien.alamat_pembuat_pengajuan,surat_pengajuan_cuti_pasien.tgl_lahir_pembuat_pengajuan,"+
                    "surat_pengajuan_cuti_pasien.jk_pembuat_pengajuan,surat_pengajuan_cuti_pasien.hubungan_pembuat_pengajuan,surat_pengajuan_cuti_pasien.notelp,"+
                    "surat_pengajuan_cuti_pasien.alasan_cuti,surat_pengajuan_cuti_pasien.mulai_cuti,surat_pengajuan_cuti_pasien.alamat_selama_cuti,"+
                    "surat_pengajuan_cuti_pasien.kembali_dari_cuti,surat_pengajuan_cuti_pasien.nip,petugas.nama,"+
                    "surat_pengajuan_cuti_pasien.kd_dokter,dokter.nm_dokter from surat_pengajuan_cuti_pasien "+
                    "inner join reg_periksa on surat_pengajuan_cuti_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join petugas on surat_pengajuan_cuti_pasien.nip=petugas.nip "+
                    "inner join bangsal on surat_pengajuan_cuti_pasien.kd_bangsal=bangsal.kd_bangsal "+
                    "inner join dokter on surat_pengajuan_cuti_pasien.kd_dokter=dokter.kd_dokter where "+
                    "surat_pengajuan_cuti_pasien.tanggal between ? and ? and "+
                    "(reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or "+
                    "surat_pengajuan_cuti_pasien.alasan_cuti like ? or surat_pengajuan_cuti_pasien.kd_bangsal like ? or bangsal.nm_bangsal like ? or "+
                    "surat_pengajuan_cuti_pasien.nip like ? or petugas.nama like ? or surat_pengajuan_cuti_pasien.kd_dokter like ? or dokter.nm_dokter like ?) "+
                    "order by surat_pengajuan_cuti_pasien.tanggal");
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
                    ps.setString(10,"%"+TCari.getText()+"%");
                    ps.setString(11,"%"+TCari.getText()+"%");
                    ps.setString(12,"%"+TCari.getText()+"%");
                }
                  
                rs=ps.executeQuery();
                while(rs.next()){
                     tabMode.addRow(new String[]{
                        rs.getString("no_surat"),rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),
                        rs.getString("umurdaftar")+" "+rs.getString("sttsumur"),rs.getString("jk"),rs.getString("tgl_lahir"),
                        rs.getString("tanggal"),rs.getString("kd_bangsal"),rs.getString("nm_bangsal"),rs.getString("pembuat_pengajuan"),
                        rs.getString("tgl_lahir_pembuat_pengajuan"),rs.getString("jk_pembuat_pengajuan"),rs.getString("notelp"),
                        rs.getString("hubungan_pembuat_pengajuan"),rs.getString("alamat_pembuat_pengajuan"),rs.getString("mulai_cuti"),
                        rs.getString("alasan_cuti"),rs.getString("kembali_dari_cuti"),rs.getString("alamat_selama_cuti"),rs.getString("nip"),
                        rs.getString("nama"),rs.getString("kd_dokter"),rs.getString("nm_dokter")
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
        Tanggal.setDate(new Date());
        KodeDokter.setText("");
        NamaDokter.setText("");
        PembuatPengajuan.setText("");
        TanggalLahirPembuatPengajuan.setDate(new Date());
        JKPembuatPengajuan.setSelectedIndex(0);
        NoHpPembuatPengajuan.setText("");
        HubunganDenganPasien.setSelectedIndex(0);
        AlamatPembuatPengajuan.setText("");
        MulaiCuti.setDate(new Date());
        AlasanCuti.setText("");
        Kembali.setDate(new Date());
        AlamatSelamaCuti.setText("");
        BtnRuang.requestFocus();
        autonomor();
    }

 
    private void getData() {
         if(tbObat.getSelectedRow()!= -1){
            NoSurat.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            Umur.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString());
            Jk.setText(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
            LahirPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString());
            KodeRuang.setText(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            NamaRuang.setText(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            PembuatPengajuan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            JKPembuatPengajuan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString().equals("L")?"Laki-laki":"Perempuan");
            NoHpPembuatPengajuan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            HubunganDenganPasien.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            AlamatPembuatPengajuan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
            AlasanCuti.setText(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());
            AlamatSelamaCuti.setText(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
            KodeDokter.setText(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString());
            NamaDokter.setText(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString());
            Valid.SetTgl2(Tanggal,tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());
            Valid.SetTgl(TanggalLahirPembuatPengajuan,tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            Valid.SetTgl2(MulaiCuti,tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            Valid.SetTgl2(Kembali,tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
        }
    }

    private void isRawat() {
        try {
            ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,reg_periksa.tgl_registrasi,reg_periksa.umurdaftar,"+
                    "reg_periksa.sttsumur,pasien.tgl_lahir from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "where reg_periksa.no_rawat=?");
            try {
                ps.setString(1,TNoRw.getText());
                rs=ps.executeQuery();
                if(rs.next()){
                    TNoRM.setText(rs.getString("no_rkm_medis"));
                    DTPCari1.setDate(rs.getDate("tgl_registrasi"));
                    TPasien.setText(rs.getString("nm_pasien"));
                    Jk.setText(rs.getString("jk"));
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
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,255));
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
        BtnSimpan.setEnabled(akses.getsurat_pengajuan_cuti_pasien());
        BtnHapus.setEnabled(akses.getsurat_pengajuan_cuti_pasien());
        BtnEdit.setEnabled(akses.getsurat_pengajuan_cuti_pasien());
        BtnPrint.setEnabled(akses.getsurat_pengajuan_cuti_pasien()); 
        if(akses.getjml2()>=1){
            NIK.setEditable(false);
            BtnPegawai.setEnabled(false);
            NIK.setText(akses.getkode());
            NamaPegawai.setText(Sequel.CariPegawai(NIK.getText()));
        }           
    }
  
    private void ganti() {
        if(Sequel.mengedittf("surat_pengajuan_cuti_pasien","no_surat=?","no_surat=?,no_rawat=?,tanggal=?,kd_bangsal=?,pembuat_pengajuan=?,alamat_pembuat_pengajuan=?,tgl_lahir_pembuat_pengajuan=?,jk_pembuat_pengajuan=?,hubungan_pembuat_pengajuan=?,"+
            "notelp=?,alasan_cuti=?,mulai_cuti=?,alamat_selama_cuti=?,kembali_dari_cuti=?,nip=?,kd_dokter=?",17,new String[]{
            NoSurat.getText(),TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19),
            KodeRuang.getText(),PembuatPengajuan.getText(),AlamatPembuatPengajuan.getText(),Valid.SetTgl(TanggalLahirPembuatPengajuan.getSelectedItem()+""), 
            JKPembuatPengajuan.getSelectedItem().toString().substring(0,1),HubunganDenganPasien.getSelectedItem().toString(),NoHpPembuatPengajuan.getText(), 
            AlasanCuti.getText(),Valid.SetTgl(MulaiCuti.getSelectedItem()+"")+" "+MulaiCuti.getSelectedItem().toString().substring(11,19),AlamatSelamaCuti.getText(), 
            Valid.SetTgl(Kembali.getSelectedItem()+"")+" "+Kembali.getSelectedItem().toString().substring(11,19),NIK.getText(),KodeDokter.getText(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
        })==true){
            tbObat.setValueAt(NoSurat.getText(),tbObat.getSelectedRow(),0);
            tbObat.setValueAt(TNoRw.getText(),tbObat.getSelectedRow(),1);
            tbObat.setValueAt(TNoRM.getText(),tbObat.getSelectedRow(),2);
            tbObat.setValueAt(TPasien.getText(),tbObat.getSelectedRow(),3);
            tbObat.setValueAt(Umur.getText(),tbObat.getSelectedRow(),4);
            tbObat.setValueAt(Jk.getText(),tbObat.getSelectedRow(),5);
            tbObat.setValueAt(LahirPasien.getText(),tbObat.getSelectedRow(),6);
            tbObat.setValueAt(Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19),tbObat.getSelectedRow(),7);
            tbObat.setValueAt(KodeRuang.getText(),tbObat.getSelectedRow(),8);
            tbObat.setValueAt(NamaRuang.getText(),tbObat.getSelectedRow(),9);
            tbObat.setValueAt(PembuatPengajuan.getText(),tbObat.getSelectedRow(),10);
            tbObat.setValueAt(Valid.SetTgl(TanggalLahirPembuatPengajuan.getSelectedItem()+""),tbObat.getSelectedRow(),11);
            tbObat.setValueAt(JKPembuatPengajuan.getSelectedItem().toString().substring(0,1),tbObat.getSelectedRow(),12);
            tbObat.setValueAt(NoHpPembuatPengajuan.getText(),tbObat.getSelectedRow(),13);
            tbObat.setValueAt(HubunganDenganPasien.getSelectedItem().toString(),tbObat.getSelectedRow(),14);
            tbObat.setValueAt(AlamatPembuatPengajuan.getText(),tbObat.getSelectedRow(),15);
            tbObat.setValueAt(Valid.SetTgl(MulaiCuti.getSelectedItem()+"")+" "+MulaiCuti.getSelectedItem().toString().substring(11,19),tbObat.getSelectedRow(),16);
            tbObat.setValueAt(AlasanCuti.getText(),tbObat.getSelectedRow(),17);
            tbObat.setValueAt(Valid.SetTgl(Kembali.getSelectedItem()+"")+" "+Kembali.getSelectedItem().toString().substring(11,19),tbObat.getSelectedRow(),18);
            tbObat.setValueAt(AlamatSelamaCuti.getText(),tbObat.getSelectedRow(),19);
            tbObat.setValueAt(NIK.getText(),tbObat.getSelectedRow(),20);
            tbObat.setValueAt(NamaPegawai.getText(),tbObat.getSelectedRow(),21);
            tbObat.setValueAt(KodeDokter.getText(),tbObat.getSelectedRow(),22);
            tbObat.setValueAt(NamaDokter.getText(),tbObat.getSelectedRow(),23);
            emptTeks();
        }
    }

    private void hapus() {
        if(Sequel.queryu2tf("delete from surat_pengajuan_cuti_pasien where no_surat=?",1,new String[]{
            tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
        })==true){
            tabMode.removeRow(tbObat.getSelectedRow());
            LCount.setText(""+tabMode.getRowCount());
            emptTeks();
        }else{
            JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
        }
    }
    
    private void autonomor(){
        Valid.autoNomer3(
            "select ifnull(MAX(CONVERT(RIGHT(surat_pengajuan_cuti_pasien.no_surat,4),signed)),0) from surat_pengajuan_cuti_pasien where date_format(surat_pengajuan_cuti_pasien.tanggal,'%Y-%m-%d')='"+Valid.SetTgl(Tanggal.getSelectedItem()+"")+"' ",
            "PCP"+Tanggal.getSelectedItem().toString().substring(6,10)+Tanggal.getSelectedItem().toString().substring(3,5)+Tanggal.getSelectedItem().toString().substring(0,2),4,NoSurat
        );  
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
                ps=koneksi.prepareStatement("select bukti_surat_pengajuan_cuti_pasien.photo from bukti_surat_pengajuan_cuti_pasien where bukti_surat_pengajuan_cuti_pasien.no_surat=?");
                try {
                    ps.setString(1,tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
                    rs=ps.executeQuery();
                    if(rs.next()){
                        if(rs.getString("photo").equals("")||rs.getString("photo").equals("-")){
                            LoadHTML2.setText("<html><body><center><br><br><font face='tahoma' size='2' color='#434343'>Kosong</font></center></body></html>");
                        }else{
                            LoadHTML2.setText("<html><body><center><img src='http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/pengajuancutiperawatan/"+rs.getString("photo")+"' alt='photo' width='500' height='500'/></center></body></html>");
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

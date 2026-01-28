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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
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
 * @author perpustakaan
 */
public final class RMSkriningIndraPendengaran extends javax.swing.JDialog {
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
    private String TANGGALMUNDUR="yes";
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public RMSkriningIndraPendengaran(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(628,674);

        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.RM","Nama Pasien","Tgl.Lahir","J.K.","Kode Petugas","Nama Petugas","Tanggal",
            "Curiga Tuli Kiri","Curiga Tuli Kanan","Rujuk Curiga Tuli","Penurunan Pendengaran Kiri",
            "Penurunan Pendengaran Kanan","Mendengar Bisikan Kiri","Mendengar Bisikan Kanan","Congek Kiri",
            "Congek Kanan","Rujuk Congek","Sumbatan Serumen Kiri","Sumbatan Serumen Kanan","Rujuk Sumbatan Serumen",
            "Hasil Skrining","Keterangan"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 23; i++) {
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
                column.setPreferredWidth(35);
            }else if(i==5){
                column.setPreferredWidth(80);
            }else if(i==6){
                column.setPreferredWidth(150);
            }else if(i==7){
                column.setPreferredWidth(120);
            }else if(i==8){
                column.setPreferredWidth(80);
            }else if(i==9){
                column.setPreferredWidth(95);
            }else if(i==10){
                column.setPreferredWidth(90);
            }else if(i==11){
                column.setPreferredWidth(145);
            }else if(i==12){
                column.setPreferredWidth(160);
            }else if(i==13){
                column.setPreferredWidth(118);
            }else if(i==14){
                column.setPreferredWidth(131);
            }else if(i==15){
                column.setPreferredWidth(63);
            }else if(i==16){
                column.setPreferredWidth(77);
            }else if(i==17){
                column.setPreferredWidth(74);
            }else if(i==18){
                column.setPreferredWidth(121);
            }else if(i==19){
                column.setPreferredWidth(136);
            }else if(i==20){
                column.setPreferredWidth(133);
            }else if(i==21){
                column.setPreferredWidth(150);
            }else if(i==22){
                column.setPreferredWidth(150);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());

        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        KdPetugas.setDocument(new batasInput((byte)20).getKata(KdPetugas));
        TCari.setDocument(new batasInput((int)100).getKata(TCari));
        HasilSkrining.setDocument(new batasInput((int)40).getKata(HasilSkrining));
        Keterangan.setDocument(new batasInput((int)100).getKata(Keterangan));
        
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
        
        jam();
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
        MnSkriningIndraPendengaran = new javax.swing.JMenuItem();
        LoadHTML = new widget.editorpane();
        Jk = new widget.TextBox();
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
        Tanggal = new widget.Tanggal();
        TNoRM = new widget.TextBox();
        jLabel16 = new widget.Label();
        Jam = new widget.ComboBox();
        Menit = new widget.ComboBox();
        Detik = new widget.ComboBox();
        ChkKejadian = new widget.CekBox();
        jLabel18 = new widget.Label();
        KdPetugas = new widget.TextBox();
        NmPetugas = new widget.TextBox();
        BtnPetugas = new widget.Button();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel100 = new widget.Label();
        jLabel8 = new widget.Label();
        TglLahir = new widget.TextBox();
        jLabel78 = new widget.Label();
        jLabel79 = new widget.Label();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel107 = new widget.Label();
        jLabel24 = new widget.Label();
        jLabel27 = new widget.Label();
        HasilSkrining = new widget.TextBox();
        jLabel108 = new widget.Label();
        Keterangan = new widget.TextBox();
        jLabel86 = new widget.Label();
        CurigaTuliTelingaKiri = new widget.ComboBox();
        CurigaTuliTelingaKanan = new widget.ComboBox();
        jLabel87 = new widget.Label();
        jLabel84 = new widget.Label();
        jLabel85 = new widget.Label();
        jLabel90 = new widget.Label();
        CongekTelingaKiri = new widget.ComboBox();
        jLabel91 = new widget.Label();
        CongekTelingaKanan = new widget.ComboBox();
        jLabel88 = new widget.Label();
        CurigaTuliTelingaRujuk = new widget.ComboBox();
        jLabel80 = new widget.Label();
        jLabel81 = new widget.Label();
        jLabel89 = new widget.Label();
        SumbatanSerumenTelingaKiri = new widget.ComboBox();
        jLabel92 = new widget.Label();
        SumbatanSerumenTelingaKanan = new widget.ComboBox();
        jLabel93 = new widget.Label();
        SumbatanSerumenTelingaRujuk = new widget.ComboBox();
        jLabel94 = new widget.Label();
        CongekTelingaRujuk = new widget.ComboBox();
        jLabel82 = new widget.Label();
        jLabel83 = new widget.Label();
        jLabel95 = new widget.Label();
        PenurunanPendengaranTelingaKiri = new widget.ComboBox();
        jLabel96 = new widget.Label();
        PenurunanPendengaranTelingaKanan = new widget.ComboBox();
        jLabel97 = new widget.Label();
        jLabel98 = new widget.Label();
        jLabel99 = new widget.Label();
        MendengarBisikanTelingaKiri = new widget.ComboBox();
        jLabel101 = new widget.Label();
        MendengarBisikanTelingaKanan = new widget.ComboBox();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnSkriningIndraPendengaran.setBackground(new java.awt.Color(255, 255, 254));
        MnSkriningIndraPendengaran.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSkriningIndraPendengaran.setForeground(new java.awt.Color(50, 50, 50));
        MnSkriningIndraPendengaran.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnSkriningIndraPendengaran.setText("Formulir Skrining Indra Pendengaran");
        MnSkriningIndraPendengaran.setName("MnSkriningIndraPendengaran"); // NOI18N
        MnSkriningIndraPendengaran.setPreferredSize(new java.awt.Dimension(230, 26));
        MnSkriningIndraPendengaran.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSkriningIndraPendengaranActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnSkriningIndraPendengaran);

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N

        Jk.setEditable(false);
        Jk.setFocusTraversalPolicyProvider(true);
        Jk.setName("Jk"); // NOI18N

        TanggalRegistrasi.setHighlighter(null);
        TanggalRegistrasi.setName("TanggalRegistrasi"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Skrining Indra Pendengaran ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setPreferredSize(new java.awt.Dimension(462, 1300));
        internalFrame1.setRequestFocusEnabled(false);
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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "06-04-2025" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "06-04-2025" }));
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
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 436));
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
        FormInput.setPreferredSize(new java.awt.Dimension(100, 413));
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

        Tanggal.setForeground(new java.awt.Color(50, 70, 50));
        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "06-04-2025" }));
        Tanggal.setDisplayFormat("dd-MM-yyyy");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.setOpaque(false);
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeyPressed(evt);
            }
        });
        FormInput.add(Tanggal);
        Tanggal.setBounds(80, 40, 90, 23);

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

        Jam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        Jam.setName("Jam"); // NOI18N
        Jam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JamKeyPressed(evt);
            }
        });
        FormInput.add(Jam);
        Jam.setBounds(173, 40, 62, 23);

        Menit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        Menit.setName("Menit"); // NOI18N
        Menit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MenitKeyPressed(evt);
            }
        });
        FormInput.add(Menit);
        Menit.setBounds(238, 40, 62, 23);

        Detik.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        Detik.setName("Detik"); // NOI18N
        Detik.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DetikKeyPressed(evt);
            }
        });
        FormInput.add(Detik);
        Detik.setBounds(303, 40, 62, 23);

        ChkKejadian.setBorder(null);
        ChkKejadian.setSelected(true);
        ChkKejadian.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkKejadian.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkKejadian.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkKejadian.setName("ChkKejadian"); // NOI18N
        FormInput.add(ChkKejadian);
        ChkKejadian.setBounds(368, 40, 23, 23);

        jLabel18.setText("Petugas :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(400, 40, 70, 23);

        KdPetugas.setEditable(false);
        KdPetugas.setHighlighter(null);
        KdPetugas.setName("KdPetugas"); // NOI18N
        FormInput.add(KdPetugas);
        KdPetugas.setBounds(474, 40, 94, 23);

        NmPetugas.setEditable(false);
        NmPetugas.setName("NmPetugas"); // NOI18N
        FormInput.add(NmPetugas);
        NmPetugas.setBounds(570, 40, 187, 23);

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
        BtnPetugas.setBounds(761, 40, 28, 23);

        jSeparator1.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator1.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator1.setName("jSeparator1"); // NOI18N
        FormInput.add(jSeparator1);
        jSeparator1.setBounds(0, 70, 807, 1);

        jLabel100.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel100.setText("I. PEMERIKSAAN/ANAMNESA");
        jLabel100.setName("jLabel100"); // NOI18N
        FormInput.add(jLabel100);
        jLabel100.setBounds(10, 70, 490, 23);

        jLabel8.setText("Tgl.Lahir :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(625, 10, 60, 23);

        TglLahir.setHighlighter(null);
        TglLahir.setName("TglLahir"); // NOI18N
        FormInput.add(TglLahir);
        TglLahir.setBounds(689, 10, 100, 23);

        jLabel78.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel78.setText("1.");
        jLabel78.setName("jLabel78"); // NOI18N
        FormInput.add(jLabel78);
        jLabel78.setBounds(44, 90, 20, 23);

        jLabel79.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel79.setText("Curiga Tuli Kongentia");
        jLabel79.setName("jLabel79"); // NOI18N
        FormInput.add(jLabel79);
        jLabel79.setBounds(62, 90, 250, 23);

        jSeparator4.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator4.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator4.setName("jSeparator4"); // NOI18N
        FormInput.add(jSeparator4);
        jSeparator4.setBounds(0, 360, 807, 1);

        jLabel107.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel107.setText("II. INTERPRETASI");
        jLabel107.setName("jLabel107"); // NOI18N
        FormInput.add(jLabel107);
        jLabel107.setBounds(10, 360, 200, 23);

        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel24.setText("Hasil Skrining");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(44, 380, 100, 23);

        jLabel27.setText(":");
        jLabel27.setName("jLabel27"); // NOI18N
        FormInput.add(jLabel27);
        jLabel27.setBounds(0, 380, 117, 23);

        HasilSkrining.setFocusTraversalPolicyProvider(true);
        HasilSkrining.setName("HasilSkrining"); // NOI18N
        HasilSkrining.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HasilSkriningKeyPressed(evt);
            }
        });
        FormInput.add(HasilSkrining);
        HasilSkrining.setBounds(121, 380, 240, 23);

        jLabel108.setText("Keterangan :");
        jLabel108.setName("jLabel108"); // NOI18N
        FormInput.add(jLabel108);
        jLabel108.setBounds(375, 380, 100, 23);

        Keterangan.setFocusTraversalPolicyProvider(true);
        Keterangan.setName("Keterangan"); // NOI18N
        Keterangan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKeyPressed(evt);
            }
        });
        FormInput.add(Keterangan);
        Keterangan.setBounds(479, 380, 310, 23);

        jLabel86.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel86.setText("- Telinga Kiri");
        jLabel86.setName("jLabel86"); // NOI18N
        FormInput.add(jLabel86);
        jLabel86.setBounds(72, 110, 100, 23);

        CurigaTuliTelingaKiri.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        CurigaTuliTelingaKiri.setName("CurigaTuliTelingaKiri"); // NOI18N
        CurigaTuliTelingaKiri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CurigaTuliTelingaKiriKeyPressed(evt);
            }
        });
        FormInput.add(CurigaTuliTelingaKiri);
        CurigaTuliTelingaKiri.setBounds(190, 110, 80, 23);

        CurigaTuliTelingaKanan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        CurigaTuliTelingaKanan.setName("CurigaTuliTelingaKanan"); // NOI18N
        CurigaTuliTelingaKanan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CurigaTuliTelingaKananKeyPressed(evt);
            }
        });
        FormInput.add(CurigaTuliTelingaKanan);
        CurigaTuliTelingaKanan.setBounds(190, 140, 80, 23);

        jLabel87.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel87.setText("- Telinga Kanan");
        jLabel87.setName("jLabel87"); // NOI18N
        FormInput.add(jLabel87);
        jLabel87.setBounds(72, 140, 110, 23);

        jLabel84.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel84.setText("OMSK/Congek");
        jLabel84.setName("jLabel84"); // NOI18N
        FormInput.add(jLabel84);
        jLabel84.setBounds(580, 90, 230, 23);

        jLabel85.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel85.setText("4.");
        jLabel85.setName("jLabel85"); // NOI18N
        FormInput.add(jLabel85);
        jLabel85.setBounds(562, 90, 20, 23);

        jLabel90.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel90.setText("- Telinga Kiri");
        jLabel90.setName("jLabel90"); // NOI18N
        FormInput.add(jLabel90);
        jLabel90.setBounds(590, 110, 90, 23);

        CongekTelingaKiri.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        CongekTelingaKiri.setName("CongekTelingaKiri"); // NOI18N
        CongekTelingaKiri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CongekTelingaKiriKeyPressed(evt);
            }
        });
        FormInput.add(CongekTelingaKiri);
        CongekTelingaKiri.setBounds(709, 110, 80, 23);

        jLabel91.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel91.setText("- Telinga Kanan");
        jLabel91.setName("jLabel91"); // NOI18N
        FormInput.add(jLabel91);
        jLabel91.setBounds(590, 140, 90, 23);

        CongekTelingaKanan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        CongekTelingaKanan.setName("CongekTelingaKanan"); // NOI18N
        CongekTelingaKanan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CongekTelingaKananKeyPressed(evt);
            }
        });
        FormInput.add(CongekTelingaKanan);
        CongekTelingaKanan.setBounds(709, 140, 80, 23);

        jLabel88.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel88.setText("- Rujuk Spesialis");
        jLabel88.setName("jLabel88"); // NOI18N
        FormInput.add(jLabel88);
        jLabel88.setBounds(72, 170, 110, 23);

        CurigaTuliTelingaRujuk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        CurigaTuliTelingaRujuk.setName("CurigaTuliTelingaRujuk"); // NOI18N
        CurigaTuliTelingaRujuk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CurigaTuliTelingaRujukKeyPressed(evt);
            }
        });
        FormInput.add(CurigaTuliTelingaRujuk);
        CurigaTuliTelingaRujuk.setBounds(190, 170, 80, 23);

        jLabel80.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel80.setText("Sumbatan Serumen");
        jLabel80.setName("jLabel80"); // NOI18N
        FormInput.add(jLabel80);
        jLabel80.setBounds(580, 200, 250, 23);

        jLabel81.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel81.setText("5.");
        jLabel81.setName("jLabel81"); // NOI18N
        FormInput.add(jLabel81);
        jLabel81.setBounds(562, 200, 20, 23);

        jLabel89.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel89.setText("- Telinga Kiri");
        jLabel89.setName("jLabel89"); // NOI18N
        FormInput.add(jLabel89);
        jLabel89.setBounds(590, 220, 100, 23);

        SumbatanSerumenTelingaKiri.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        SumbatanSerumenTelingaKiri.setName("SumbatanSerumenTelingaKiri"); // NOI18N
        SumbatanSerumenTelingaKiri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SumbatanSerumenTelingaKiriKeyPressed(evt);
            }
        });
        FormInput.add(SumbatanSerumenTelingaKiri);
        SumbatanSerumenTelingaKiri.setBounds(709, 220, 80, 23);

        jLabel92.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel92.setText("- Telinga Kanan");
        jLabel92.setName("jLabel92"); // NOI18N
        FormInput.add(jLabel92);
        jLabel92.setBounds(590, 250, 110, 23);

        SumbatanSerumenTelingaKanan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        SumbatanSerumenTelingaKanan.setName("SumbatanSerumenTelingaKanan"); // NOI18N
        SumbatanSerumenTelingaKanan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SumbatanSerumenTelingaKananKeyPressed(evt);
            }
        });
        FormInput.add(SumbatanSerumenTelingaKanan);
        SumbatanSerumenTelingaKanan.setBounds(709, 250, 80, 23);

        jLabel93.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel93.setText("- Rujuk Spesialis");
        jLabel93.setName("jLabel93"); // NOI18N
        FormInput.add(jLabel93);
        jLabel93.setBounds(590, 280, 110, 23);

        SumbatanSerumenTelingaRujuk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        SumbatanSerumenTelingaRujuk.setName("SumbatanSerumenTelingaRujuk"); // NOI18N
        SumbatanSerumenTelingaRujuk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SumbatanSerumenTelingaRujukKeyPressed(evt);
            }
        });
        FormInput.add(SumbatanSerumenTelingaRujuk);
        SumbatanSerumenTelingaRujuk.setBounds(709, 280, 80, 23);

        jLabel94.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel94.setText("- Rujuk Spesialis");
        jLabel94.setName("jLabel94"); // NOI18N
        FormInput.add(jLabel94);
        jLabel94.setBounds(590, 170, 90, 23);

        CongekTelingaRujuk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        CongekTelingaRujuk.setName("CongekTelingaRujuk"); // NOI18N
        CongekTelingaRujuk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CongekTelingaRujukKeyPressed(evt);
            }
        });
        FormInput.add(CongekTelingaRujuk);
        CongekTelingaRujuk.setBounds(709, 170, 80, 23);

        jLabel82.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel82.setText("Penurunan Pendengaran Karena Usia Lanjut (Presbikusis)");
        jLabel82.setName("jLabel82"); // NOI18N
        FormInput.add(jLabel82);
        jLabel82.setBounds(62, 200, 340, 23);

        jLabel83.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel83.setText("2.");
        jLabel83.setName("jLabel83"); // NOI18N
        FormInput.add(jLabel83);
        jLabel83.setBounds(44, 200, 20, 23);

        jLabel95.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel95.setText("- Telinga Kiri");
        jLabel95.setName("jLabel95"); // NOI18N
        FormInput.add(jLabel95);
        jLabel95.setBounds(72, 220, 100, 23);

        PenurunanPendengaranTelingaKiri.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        PenurunanPendengaranTelingaKiri.setName("PenurunanPendengaranTelingaKiri"); // NOI18N
        PenurunanPendengaranTelingaKiri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenurunanPendengaranTelingaKiriKeyPressed(evt);
            }
        });
        FormInput.add(PenurunanPendengaranTelingaKiri);
        PenurunanPendengaranTelingaKiri.setBounds(190, 220, 80, 23);

        jLabel96.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel96.setText("- Telinga Kanan");
        jLabel96.setName("jLabel96"); // NOI18N
        FormInput.add(jLabel96);
        jLabel96.setBounds(72, 250, 110, 23);

        PenurunanPendengaranTelingaKanan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        PenurunanPendengaranTelingaKanan.setName("PenurunanPendengaranTelingaKanan"); // NOI18N
        PenurunanPendengaranTelingaKanan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenurunanPendengaranTelingaKananKeyPressed(evt);
            }
        });
        FormInput.add(PenurunanPendengaranTelingaKanan);
        PenurunanPendengaranTelingaKanan.setBounds(190, 250, 80, 23);

        jLabel97.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel97.setText("3.");
        jLabel97.setName("jLabel97"); // NOI18N
        FormInput.add(jLabel97);
        jLabel97.setBounds(44, 280, 20, 23);

        jLabel98.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel98.setText("Mendengar Bisikan Saat TES BISIK");
        jLabel98.setName("jLabel98"); // NOI18N
        FormInput.add(jLabel98);
        jLabel98.setBounds(62, 280, 340, 23);

        jLabel99.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel99.setText("- Telinga Kiri");
        jLabel99.setName("jLabel99"); // NOI18N
        FormInput.add(jLabel99);
        jLabel99.setBounds(72, 300, 100, 23);

        MendengarBisikanTelingaKiri.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Gangguan Pendengaran" }));
        MendengarBisikanTelingaKiri.setName("MendengarBisikanTelingaKiri"); // NOI18N
        MendengarBisikanTelingaKiri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MendengarBisikanTelingaKiriKeyPressed(evt);
            }
        });
        FormInput.add(MendengarBisikanTelingaKiri);
        MendengarBisikanTelingaKiri.setBounds(190, 300, 170, 23);

        jLabel101.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel101.setText("- Telinga Kanan");
        jLabel101.setName("jLabel101"); // NOI18N
        FormInput.add(jLabel101);
        jLabel101.setBounds(72, 330, 110, 23);

        MendengarBisikanTelingaKanan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Gangguan Pendengaran" }));
        MendengarBisikanTelingaKanan.setName("MendengarBisikanTelingaKanan"); // NOI18N
        MendengarBisikanTelingaKanan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MendengarBisikanTelingaKananKeyPressed(evt);
            }
        });
        FormInput.add(MendengarBisikanTelingaKanan);
        MendengarBisikanTelingaKanan.setBounds(190, 330, 170, 23);

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
        }else if(KdPetugas.getText().trim().equals("")||NmPetugas.getText().trim().equals("")){
            Valid.textKosong(KdPetugas,"Petugas");
        }else if(HasilSkrining.getText().trim().equals("")){
            Valid.textKosong(HasilSkrining,"Hasil Skrining");
        }else if(Keterangan.getText().trim().equals("")){
            Valid.textKosong(Keterangan,"Keterangan");
        }else{
            if(akses.getkode().equals("Admin Utama")){
                simpan();
            }else{
                if(TanggalRegistrasi.getText().equals("")){
                    TanggalRegistrasi.setText(Sequel.cariIsi("select concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) from reg_periksa where reg_periksa.no_rawat=?",TNoRw.getText()));
                }
                if(Sequel.cekTanggalRegistrasi(TanggalRegistrasi.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem())==true){
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
                if(KdPetugas.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString())){
                    if(Sequel.cekTanggal48jam(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString(),Sequel.ambiltanggalsekarang())==true){
                        hapus();
                    }
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
        }else if(KdPetugas.getText().trim().equals("")||NmPetugas.getText().trim().equals("")){
            Valid.textKosong(KdPetugas,"Petugas");
        }else if(HasilSkrining.getText().trim().equals("")){
            Valid.textKosong(HasilSkrining,"Hasil Skrining");
        }else if(Keterangan.getText().trim().equals("")){
            Valid.textKosong(Keterangan,"Keterangan");
        }else{
            if(tbObat.getSelectedRow()>-1){
                if(akses.getkode().equals("Admin Utama")){
                    ganti();
                }else{
                    if(KdPetugas.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString())){
                        if(Sequel.cekTanggal48jam(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString(),Sequel.ambiltanggalsekarang())==true){
                            if(TanggalRegistrasi.getText().equals("")){
                                TanggalRegistrasi.setText(Sequel.cariIsi("select concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) from reg_periksa where reg_periksa.no_rawat=?",TNoRw.getText()));
                            }
                            if(Sequel.cekTanggalRegistrasi(TanggalRegistrasi.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem())==true){
                                ganti();
                            }
                        }
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
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kode Petugas</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Petugas</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tanggal</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Curiga Tuli Kiri</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Curiga Tuli Kanan</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Rujuk Curiga Tuli</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Penurunan Pendengaran Kiri</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Penurunan Pendengaran Kanan</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Mendengar Bisikan Kiri</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Mendengar Bisikan Kanan</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Congek Kiri</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Congek Kanan</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Rujuk Congek</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Sumbatan Serumen Kiri</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Sumbatan Serumen Kanan</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Rujuk Sumbatan Serumen</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Hasil Skrining</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan</b></td>"+
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
                                    "</tr>");
                            }
                            LoadHTML.setText(
                                "<html>"+
                                  "<table width='1900px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
                                   htmlContent.toString()+
                                  "</table>"+
                                "</html>"
                            );

                            f = new File("DataSkriningIndraPendengaran.html");            
                            bw = new BufferedWriter(new FileWriter(f));            
                            bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                                        "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                                        "<table width='1900px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                            "<tr class='isi2'>"+
                                                "<td valign='top' align='center'>"+
                                                    "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                                    akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                                    akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                                    "<font size='2' face='Tahoma'>DATA SEKRINING INDRA PENDENGARAN<br><br></font>"+        
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
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kode Petugas</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Petugas</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tanggal</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Curiga Tuli Kiri</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Curiga Tuli Kanan</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Rujuk Curiga Tuli</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Penurunan Pendengaran Kiri</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Penurunan Pendengaran Kanan</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Mendengar Bisikan Kiri</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Mendengar Bisikan Kanan</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Congek Kiri</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Congek Kanan</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Rujuk Congek</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Sumbatan Serumen Kiri</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Sumbatan Serumen Kanan</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Rujuk Sumbatan Serumen</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Hasil Skrining</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan</b></td>"+
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
                                    "</tr>");
                            }
                            LoadHTML.setText(
                                "<html>"+
                                  "<table width='1900px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
                                   htmlContent.toString()+
                                  "</table>"+
                                "</html>"
                            );

                            f = new File("DataSkriningIndraPendengaran.wps");            
                            bw = new BufferedWriter(new FileWriter(f));            
                            bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                                        "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                                        "<table width='1900px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                            "<tr class='isi2'>"+
                                                "<td valign='top' align='center'>"+
                                                    "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                                    akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                                    akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                                    "<font size='2' face='Tahoma'>DATA SEKRINING INDRA PENDENGARAN<br><br></font>"+        
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
                                "\"No.Rawat\";\"No.RM\";\"Nama Pasien\";\"Tgl.Lahir\";\"J.K.\";\"Kode Petugas\";\"Nama Petugas\";\"Tanggal\";\"Curiga Tuli Kiri\";\"Curiga Tuli Kanan\";\"Rujuk Curiga Tuli\";\"Penurunan Pendengaran Kiri\";\"Penurunan Pendengaran Kanan\";\"Mendengar Bisikan Kiri\";\"Mendengar Bisikan Kanan\";\"Congek Kiri\";\"Congek Kanan\";\"Rujuk Congek\";\"Sumbatan Serumen Kiri\";\"Sumbatan Serumen Kanan\";\"Rujuk Sumbatan Serumen\";\"Hasil Skrining\";\"Keterangan\"\n"
                            ); 
                            for (i = 0; i < tabMode.getRowCount(); i++) {
                                htmlContent.append(
                                    "\""+tbObat.getValueAt(i,0).toString()+"\";\""+tbObat.getValueAt(i,1).toString()+"\";\""+tbObat.getValueAt(i,2).toString()+"\";\""+tbObat.getValueAt(i,3).toString()+"\";\""+tbObat.getValueAt(i,4).toString()+"\";\""+tbObat.getValueAt(i,5).toString()+"\";\""+tbObat.getValueAt(i,6).toString()+"\";\""+tbObat.getValueAt(i,7).toString()+"\";\""+tbObat.getValueAt(i,8).toString()+"\";\""+tbObat.getValueAt(i,9).toString()+"\";\""+tbObat.getValueAt(i,10).toString()+"\";\""+tbObat.getValueAt(i,11).toString()+"\";\""+tbObat.getValueAt(i,12).toString()+"\";\""+tbObat.getValueAt(i,13).toString()+"\";\""+tbObat.getValueAt(i,14).toString()+"\";\""+tbObat.getValueAt(i,15).toString()+"\";\""+tbObat.getValueAt(i,16).toString()+"\";\""+tbObat.getValueAt(i,17).toString()+"\";\""+tbObat.getValueAt(i,18).toString()+"\";\""+tbObat.getValueAt(i,19).toString()+"\";\""+tbObat.getValueAt(i,20).toString()+"\";\""+tbObat.getValueAt(i,21).toString()+"\";\""+tbObat.getValueAt(i,22).toString()+"\"\n"
                                );
                            }
                            f = new File("DataSkriningIndraPendengaran.csv");            
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

    private void TanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKeyPressed
        Valid.pindah(evt,TCari,Jam);
}//GEN-LAST:event_TanggalKeyPressed

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

    private void JamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JamKeyPressed
        Valid.pindah(evt,Tanggal,Menit);
    }//GEN-LAST:event_JamKeyPressed

    private void MenitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MenitKeyPressed
        Valid.pindah(evt,Jam,Detik);
    }//GEN-LAST:event_MenitKeyPressed

    private void DetikKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DetikKeyPressed
        Valid.pindah(evt,Menit,BtnPetugas);
    }//GEN-LAST:event_DetikKeyPressed

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

    private void BtnPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPetugasKeyPressed
        Valid.pindah(evt,Detik,CurigaTuliTelingaKiri);
    }//GEN-LAST:event_BtnPetugasKeyPressed

    private void MnSkriningIndraPendengaranActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnSkriningIndraPendengaranActionPerformed
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
            param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbObat.getValueAt(tbObat.getSelectedRow(),6).toString()+"\nID "+(finger.equals("")?tbObat.getValueAt(tbObat.getSelectedRow(),5).toString():finger)+"\n"+Tanggal.getSelectedItem()); 
            Valid.MyReportqry("rptFormulirSkriningIndraPendengaran.jasper","report","::[ Formulir Skrining Indra Pendengaran ]::",
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,pasien.jk,skrining_indra_pendengaran.nip,petugas.nama,skrining_indra_pendengaran.tanggal,"+
                    "skrining_indra_pendengaran.curiga_tuli_telinga_kiri,skrining_indra_pendengaran.curiga_tuli_telinga_kanan,skrining_indra_pendengaran.curiga_tuli_telinga_rujuk,skrining_indra_pendengaran.penurunan_pendengaran_telinga_kiri,"+
                    "skrining_indra_pendengaran.penurunan_pendengaran_telinga_kanan,skrining_indra_pendengaran.mendengar_bisikan_telinga_kiri,skrining_indra_pendengaran.mendengar_bisikan_telinga_kanan,skrining_indra_pendengaran.congek_telinga_kiri,"+
                    "skrining_indra_pendengaran.congek_telinga_kanan,skrining_indra_pendengaran.congek_telinga_rujuk,skrining_indra_pendengaran.sumbatan_serumen_telinga_kiri,skrining_indra_pendengaran.sumbatan_serumen_telinga_kanan,"+
                    "skrining_indra_pendengaran.sumbatan_serumen_telinga_rujuk,skrining_indra_pendengaran.hasil_skrining,skrining_indra_pendengaran.keterangan "+
                    "from skrining_indra_pendengaran inner join reg_periksa on skrining_indra_pendengaran.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join petugas on skrining_indra_pendengaran.nip=petugas.nip "+
                    "where reg_periksa.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'",param);
        }
    }//GEN-LAST:event_MnSkriningIndraPendengaranActionPerformed

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void KeteranganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKeyPressed
        Valid.pindah(evt,HasilSkrining,BtnSimpan);
    }//GEN-LAST:event_KeteranganKeyPressed

    private void HasilSkriningKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HasilSkriningKeyPressed
        Valid.pindah(evt,SumbatanSerumenTelingaRujuk,Keterangan);
    }//GEN-LAST:event_HasilSkriningKeyPressed

    private void CurigaTuliTelingaKiriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CurigaTuliTelingaKiriKeyPressed
        Valid.pindah(evt,BtnPetugas,CurigaTuliTelingaKanan);
    }//GEN-LAST:event_CurigaTuliTelingaKiriKeyPressed

    private void CurigaTuliTelingaKananKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CurigaTuliTelingaKananKeyPressed
        Valid.pindah(evt,CurigaTuliTelingaKiri,CurigaTuliTelingaRujuk);
    }//GEN-LAST:event_CurigaTuliTelingaKananKeyPressed

    private void CongekTelingaKiriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CongekTelingaKiriKeyPressed
        Valid.pindah(evt,MendengarBisikanTelingaKanan,CongekTelingaKanan);
    }//GEN-LAST:event_CongekTelingaKiriKeyPressed

    private void CongekTelingaKananKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CongekTelingaKananKeyPressed
        Valid.pindah(evt,CongekTelingaKiri,CongekTelingaRujuk);
    }//GEN-LAST:event_CongekTelingaKananKeyPressed

    private void CurigaTuliTelingaRujukKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CurigaTuliTelingaRujukKeyPressed
        Valid.pindah(evt,CurigaTuliTelingaKanan,PenurunanPendengaranTelingaKiri);
    }//GEN-LAST:event_CurigaTuliTelingaRujukKeyPressed

    private void SumbatanSerumenTelingaKiriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SumbatanSerumenTelingaKiriKeyPressed
        Valid.pindah(evt,CongekTelingaRujuk,SumbatanSerumenTelingaKanan);
    }//GEN-LAST:event_SumbatanSerumenTelingaKiriKeyPressed

    private void SumbatanSerumenTelingaKananKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SumbatanSerumenTelingaKananKeyPressed
        Valid.pindah(evt,SumbatanSerumenTelingaKiri,SumbatanSerumenTelingaRujuk);
    }//GEN-LAST:event_SumbatanSerumenTelingaKananKeyPressed

    private void SumbatanSerumenTelingaRujukKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SumbatanSerumenTelingaRujukKeyPressed
        Valid.pindah(evt,SumbatanSerumenTelingaKanan,HasilSkrining);
    }//GEN-LAST:event_SumbatanSerumenTelingaRujukKeyPressed

    private void CongekTelingaRujukKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CongekTelingaRujukKeyPressed
        Valid.pindah(evt,CongekTelingaKanan,SumbatanSerumenTelingaKiri);
    }//GEN-LAST:event_CongekTelingaRujukKeyPressed

    private void PenurunanPendengaranTelingaKiriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenurunanPendengaranTelingaKiriKeyPressed
        Valid.pindah(evt,CurigaTuliTelingaRujuk,PenurunanPendengaranTelingaKanan);
    }//GEN-LAST:event_PenurunanPendengaranTelingaKiriKeyPressed

    private void PenurunanPendengaranTelingaKananKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenurunanPendengaranTelingaKananKeyPressed
        Valid.pindah(evt,PenurunanPendengaranTelingaKiri,MendengarBisikanTelingaKiri);
    }//GEN-LAST:event_PenurunanPendengaranTelingaKananKeyPressed

    private void MendengarBisikanTelingaKiriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MendengarBisikanTelingaKiriKeyPressed
        Valid.pindah(evt,PenurunanPendengaranTelingaKanan,MendengarBisikanTelingaKanan);
    }//GEN-LAST:event_MendengarBisikanTelingaKiriKeyPressed

    private void MendengarBisikanTelingaKananKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MendengarBisikanTelingaKananKeyPressed
        Valid.pindah(evt,MendengarBisikanTelingaKiri,CongekTelingaKiri);
    }//GEN-LAST:event_MendengarBisikanTelingaKananKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMSkriningIndraPendengaran dialog = new RMSkriningIndraPendengaran(new javax.swing.JFrame(), true);
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
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPetugas;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkInput;
    private widget.CekBox ChkKejadian;
    private widget.ComboBox CongekTelingaKanan;
    private widget.ComboBox CongekTelingaKiri;
    private widget.ComboBox CongekTelingaRujuk;
    private widget.ComboBox CurigaTuliTelingaKanan;
    private widget.ComboBox CurigaTuliTelingaKiri;
    private widget.ComboBox CurigaTuliTelingaRujuk;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.ComboBox Detik;
    private widget.PanelBiasa FormInput;
    private widget.TextBox HasilSkrining;
    private widget.ComboBox Jam;
    private widget.TextBox Jk;
    private widget.TextBox KdPetugas;
    private widget.TextBox Keterangan;
    private widget.Label LCount;
    private widget.editorpane LoadHTML;
    private widget.ComboBox MendengarBisikanTelingaKanan;
    private widget.ComboBox MendengarBisikanTelingaKiri;
    private widget.ComboBox Menit;
    private javax.swing.JMenuItem MnSkriningIndraPendengaran;
    private widget.TextBox NmPetugas;
    private javax.swing.JPanel PanelInput;
    private widget.ComboBox PenurunanPendengaranTelingaKanan;
    private widget.ComboBox PenurunanPendengaranTelingaKiri;
    private widget.ScrollPane Scroll;
    private widget.ComboBox SumbatanSerumenTelingaKanan;
    private widget.ComboBox SumbatanSerumenTelingaKiri;
    private widget.ComboBox SumbatanSerumenTelingaRujuk;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.Tanggal Tanggal;
    private widget.TextBox TanggalRegistrasi;
    private widget.TextBox TglLahir;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel100;
    private widget.Label jLabel101;
    private widget.Label jLabel107;
    private widget.Label jLabel108;
    private widget.Label jLabel16;
    private widget.Label jLabel18;
    private widget.Label jLabel19;
    private widget.Label jLabel21;
    private widget.Label jLabel24;
    private widget.Label jLabel27;
    private widget.Label jLabel4;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
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
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,pasien.jk,skrining_indra_pendengaran.nip,petugas.nama,skrining_indra_pendengaran.tanggal,"+
                    "skrining_indra_pendengaran.curiga_tuli_telinga_kiri,skrining_indra_pendengaran.curiga_tuli_telinga_kanan,skrining_indra_pendengaran.curiga_tuli_telinga_rujuk,skrining_indra_pendengaran.penurunan_pendengaran_telinga_kiri,"+
                    "skrining_indra_pendengaran.penurunan_pendengaran_telinga_kanan,skrining_indra_pendengaran.mendengar_bisikan_telinga_kiri,skrining_indra_pendengaran.mendengar_bisikan_telinga_kanan,skrining_indra_pendengaran.congek_telinga_kiri,"+
                    "skrining_indra_pendengaran.congek_telinga_kanan,skrining_indra_pendengaran.congek_telinga_rujuk,skrining_indra_pendengaran.sumbatan_serumen_telinga_kiri,skrining_indra_pendengaran.sumbatan_serumen_telinga_kanan,"+
                    "skrining_indra_pendengaran.sumbatan_serumen_telinga_rujuk,skrining_indra_pendengaran.hasil_skrining,skrining_indra_pendengaran.keterangan "+
                    "from skrining_indra_pendengaran inner join reg_periksa on skrining_indra_pendengaran.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join petugas on skrining_indra_pendengaran.nip=petugas.nip "+
                    "where skrining_indra_pendengaran.tanggal between ? and ? order by skrining_indra_pendengaran.tanggal ");
            }else{
                ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,pasien.jk,skrining_indra_pendengaran.nip,petugas.nama,skrining_indra_pendengaran.tanggal,"+
                    "skrining_indra_pendengaran.curiga_tuli_telinga_kiri,skrining_indra_pendengaran.curiga_tuli_telinga_kanan,skrining_indra_pendengaran.curiga_tuli_telinga_rujuk,skrining_indra_pendengaran.penurunan_pendengaran_telinga_kiri,"+
                    "skrining_indra_pendengaran.penurunan_pendengaran_telinga_kanan,skrining_indra_pendengaran.mendengar_bisikan_telinga_kiri,skrining_indra_pendengaran.mendengar_bisikan_telinga_kanan,skrining_indra_pendengaran.congek_telinga_kiri,"+
                    "skrining_indra_pendengaran.congek_telinga_kanan,skrining_indra_pendengaran.congek_telinga_rujuk,skrining_indra_pendengaran.sumbatan_serumen_telinga_kiri,skrining_indra_pendengaran.sumbatan_serumen_telinga_kanan,"+
                    "skrining_indra_pendengaran.sumbatan_serumen_telinga_rujuk,skrining_indra_pendengaran.hasil_skrining,skrining_indra_pendengaran.keterangan "+
                    "from skrining_indra_pendengaran inner join reg_periksa on skrining_indra_pendengaran.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join petugas on skrining_indra_pendengaran.nip=petugas.nip "+
                    "where skrining_indra_pendengaran.tanggal between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or "+
                    "pasien.nm_pasien like ? or skrining_indra_pendengaran.nip like ? or petugas.nama like ? or skrining_indra_pendengaran.hasil_skrining like ? or skrining_indra_pendengaran.keterangan like ?) "+
                    "order by skrining_indra_pendengaran.tanggal ");
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
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getDate("tgl_lahir"),rs.getString("jk"),rs.getString("nip"),rs.getString("nama"),rs.getString("tanggal"),
                        rs.getString("curiga_tuli_telinga_kiri"),rs.getString("curiga_tuli_telinga_kanan"),rs.getString("curiga_tuli_telinga_rujuk"),rs.getString("penurunan_pendengaran_telinga_kiri"),rs.getString("penurunan_pendengaran_telinga_kanan"),rs.getString("mendengar_bisikan_telinga_kiri"),rs.getString("mendengar_bisikan_telinga_kanan"),
                        rs.getString("congek_telinga_kiri"),rs.getString("congek_telinga_kanan"),rs.getString("congek_telinga_rujuk"),rs.getString("sumbatan_serumen_telinga_kiri"),rs.getString("sumbatan_serumen_telinga_kanan"),rs.getString("sumbatan_serumen_telinga_rujuk"),
                        rs.getString("hasil_skrining"),rs.getString("keterangan"),
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
        CurigaTuliTelingaKiri.setSelectedIndex(0);
        CurigaTuliTelingaKanan.setSelectedIndex(0);
        CurigaTuliTelingaRujuk.setSelectedIndex(0);
        PenurunanPendengaranTelingaKiri.setSelectedIndex(0);
        PenurunanPendengaranTelingaKanan.setSelectedIndex(0);
        MendengarBisikanTelingaKiri.setSelectedIndex(0);
        MendengarBisikanTelingaKanan.setSelectedIndex(0);
        CongekTelingaKiri.setSelectedIndex(0);
        CongekTelingaKanan.setSelectedIndex(0);
        CongekTelingaRujuk.setSelectedIndex(0);
        SumbatanSerumenTelingaKiri.setSelectedIndex(0);
        SumbatanSerumenTelingaKanan.setSelectedIndex(0);
        SumbatanSerumenTelingaRujuk.setSelectedIndex(0);
        HasilSkrining.setText("");
        Keterangan.setText("");
        CurigaTuliTelingaKiri.requestFocus();
    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            Jk.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString());
            CurigaTuliTelingaKiri.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            CurigaTuliTelingaKanan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            CurigaTuliTelingaRujuk.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            PenurunanPendengaranTelingaKiri.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            PenurunanPendengaranTelingaKanan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            MendengarBisikanTelingaKiri.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            MendengarBisikanTelingaKanan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            CongekTelingaKiri.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
            CongekTelingaKanan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            CongekTelingaRujuk.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());
            SumbatanSerumenTelingaKiri.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
            SumbatanSerumenTelingaKanan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
            SumbatanSerumenTelingaRujuk.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString());
            HasilSkrining.setText(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString());
            Keterangan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString());
            Jam.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString().substring(11,13));
            Menit.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString().substring(14,15));
            Detik.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString().substring(17,19));
            Valid.SetTgl(Tanggal,tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());  
        }
    }
    
    private void isRawat() {
        try {
            ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,"+
                    "reg_periksa.tgl_registrasi,reg_periksa.jam_reg "+
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
        ChkInput.setSelected(true);
        isForm();
        runBackground(() ->tampil());
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            if(internalFrame1.getHeight()>608){
                ChkInput.setVisible(false);
                PanelInput.setPreferredSize(new Dimension(WIDTH,436));
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
        BtnSimpan.setEnabled(akses.getskrining_indra_pendengaran());
        BtnHapus.setEnabled(akses.getskrining_indra_pendengaran());
        BtnEdit.setEnabled(akses.getskrining_indra_pendengaran());
        BtnPrint.setEnabled(akses.getskrining_indra_pendengaran()); 
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

        if(TANGGALMUNDUR.equals("no")){
            if(!akses.getkode().equals("Admin Utama")){
                Tanggal.setEditable(false);
                Tanggal.setEnabled(false);
                ChkKejadian.setEnabled(false);
                Jam.setEnabled(false);
                Menit.setEnabled(false);
                Detik.setEnabled(false);
            }
        }
    }

    private void jam(){
        ActionListener taskPerformer = new ActionListener(){
            private int nilai_jam;
            private int nilai_menit;
            private int nilai_detik;
            public void actionPerformed(ActionEvent e) {
                String nol_jam = "";
                String nol_menit = "";
                String nol_detik = "";
                
                Date now = Calendar.getInstance().getTime();

                // Mengambil nilaj JAM, MENIT, dan DETIK Sekarang
                if(ChkKejadian.isSelected()==true){
                    nilai_jam = now.getHours();
                    nilai_menit = now.getMinutes();
                    nilai_detik = now.getSeconds();
                }else if(ChkKejadian.isSelected()==false){
                    nilai_jam =Jam.getSelectedIndex();
                    nilai_menit =Menit.getSelectedIndex();
                    nilai_detik =Detik.getSelectedIndex();
                }

                // Jika nilai JAM lebih kecil dari 10 (hanya 1 digit)
                if (nilai_jam <= 9) {
                    // Tambahkan "0" didepannya
                    nol_jam = "0";
                }
                // Jika nilai MENIT lebih kecil dari 10 (hanya 1 digit)
                if (nilai_menit <= 9) {
                    // Tambahkan "0" didepannya
                    nol_menit = "0";
                }
                // Jika nilai DETIK lebih kecil dari 10 (hanya 1 digit)
                if (nilai_detik <= 9) {
                    // Tambahkan "0" didepannya
                    nol_detik = "0";
                }
                // Membuat String JAM, MENIT, DETIK
                String jam = nol_jam + Integer.toString(nilai_jam);
                String menit = nol_menit + Integer.toString(nilai_menit);
                String detik = nol_detik + Integer.toString(nilai_detik);
                // Menampilkan pada Layar
                //tampil_jam.setText("  " + jam + " : " + menit + " : " + detik + "  ");
                Jam.setSelectedItem(jam);
                Menit.setSelectedItem(menit);
                Detik.setSelectedItem(detik);
            }
        };
        // Timer
        new Timer(1000, taskPerformer).start();
    }

    private void ganti() {
        if(Sequel.mengedittf("skrining_indra_pendengaran","no_rawat=?","no_rawat=?,tanggal=?,curiga_tuli_telinga_kiri=?,curiga_tuli_telinga_kanan=?,curiga_tuli_telinga_rujuk=?,penurunan_pendengaran_telinga_kiri=?,penurunan_pendengaran_telinga_kanan=?,"+
                "mendengar_bisikan_telinga_kiri=?,mendengar_bisikan_telinga_kanan=?,congek_telinga_kiri=?,congek_telinga_kanan=?,congek_telinga_rujuk=?,sumbatan_serumen_telinga_kiri=?,sumbatan_serumen_telinga_kanan=?,sumbatan_serumen_telinga_rujuk=?,"+
                "hasil_skrining=?,keterangan=?,nip=?",19,new String[]{
                TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),
                CurigaTuliTelingaKiri.getSelectedItem().toString(),CurigaTuliTelingaKanan.getSelectedItem().toString(),CurigaTuliTelingaRujuk.getSelectedItem().toString(),PenurunanPendengaranTelingaKiri.getSelectedItem().toString(), 
                PenurunanPendengaranTelingaKanan.getSelectedItem().toString(),MendengarBisikanTelingaKiri.getSelectedItem().toString(),MendengarBisikanTelingaKanan.getSelectedItem().toString(),CongekTelingaKiri.getSelectedItem().toString(), 
                CongekTelingaKanan.getSelectedItem().toString(),CongekTelingaRujuk.getSelectedItem().toString(),SumbatanSerumenTelingaKiri.getSelectedItem().toString(),SumbatanSerumenTelingaKanan.getSelectedItem().toString(), 
                SumbatanSerumenTelingaRujuk.getSelectedItem().toString(),HasilSkrining.getText(),Keterangan.getText(),KdPetugas.getText(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
            })==true){
               tbObat.setValueAt(TNoRw.getText(),tbObat.getSelectedRow(),0);
               tbObat.setValueAt(TNoRM.getText(),tbObat.getSelectedRow(),1);
               tbObat.setValueAt(TPasien.getText(),tbObat.getSelectedRow(),2);
               tbObat.setValueAt(TglLahir.getText(),tbObat.getSelectedRow(),3);
               tbObat.setValueAt(Jk.getText(),tbObat.getSelectedRow(),4);
               tbObat.setValueAt(KdPetugas.getText(),tbObat.getSelectedRow(),5);
               tbObat.setValueAt(NmPetugas.getText(),tbObat.getSelectedRow(),6);
               tbObat.setValueAt(Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),tbObat.getSelectedRow(),7);
               tbObat.setValueAt(CurigaTuliTelingaKiri.getSelectedItem().toString(),tbObat.getSelectedRow(),8);
               tbObat.setValueAt(CurigaTuliTelingaKanan.getSelectedItem().toString(),tbObat.getSelectedRow(),9);
               tbObat.setValueAt(CurigaTuliTelingaRujuk.getSelectedItem().toString(),tbObat.getSelectedRow(),10);
               tbObat.setValueAt(PenurunanPendengaranTelingaKiri.getSelectedItem().toString(),tbObat.getSelectedRow(),11);
               tbObat.setValueAt(PenurunanPendengaranTelingaKanan.getSelectedItem().toString(),tbObat.getSelectedRow(),12);
               tbObat.setValueAt(MendengarBisikanTelingaKiri.getSelectedItem().toString(),tbObat.getSelectedRow(),13);
               tbObat.setValueAt(MendengarBisikanTelingaKanan.getSelectedItem().toString(),tbObat.getSelectedRow(),14);
               tbObat.setValueAt(CongekTelingaKiri.getSelectedItem().toString(),tbObat.getSelectedRow(),15);
               tbObat.setValueAt(CongekTelingaKanan.getSelectedItem().toString(),tbObat.getSelectedRow(),16);
               tbObat.setValueAt(CongekTelingaRujuk.getSelectedItem().toString(),tbObat.getSelectedRow(),17);
               tbObat.setValueAt(SumbatanSerumenTelingaKiri.getSelectedItem().toString(),tbObat.getSelectedRow(),18);
               tbObat.setValueAt(SumbatanSerumenTelingaKanan.getSelectedItem().toString(),tbObat.getSelectedRow(),19);
               tbObat.setValueAt(SumbatanSerumenTelingaRujuk.getSelectedItem().toString(),tbObat.getSelectedRow(),20);
               tbObat.setValueAt(HasilSkrining.getText(),tbObat.getSelectedRow(),21);
               tbObat.setValueAt(Keterangan.getText(),tbObat.getSelectedRow(),22);
               emptTeks();
        }
    }

    private void hapus() {
        if(Sequel.queryu2tf("delete from skrining_indra_pendengaran where no_rawat=?",1,new String[]{
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
        if(Sequel.menyimpantf("skrining_indra_pendengaran","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","Data",18,new String[]{
            TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),
            CurigaTuliTelingaKiri.getSelectedItem().toString(),CurigaTuliTelingaKanan.getSelectedItem().toString(),CurigaTuliTelingaRujuk.getSelectedItem().toString(),PenurunanPendengaranTelingaKiri.getSelectedItem().toString(), 
            PenurunanPendengaranTelingaKanan.getSelectedItem().toString(),MendengarBisikanTelingaKiri.getSelectedItem().toString(),MendengarBisikanTelingaKanan.getSelectedItem().toString(),CongekTelingaKiri.getSelectedItem().toString(), 
            CongekTelingaKanan.getSelectedItem().toString(),CongekTelingaRujuk.getSelectedItem().toString(),SumbatanSerumenTelingaKiri.getSelectedItem().toString(),SumbatanSerumenTelingaKanan.getSelectedItem().toString(), 
            SumbatanSerumenTelingaRujuk.getSelectedItem().toString(),HasilSkrining.getText(),Keterangan.getText(),KdPetugas.getText()
        })==true){
            tabMode.addRow(new Object[]{
                TNoRw.getText(),TNoRM.getText(),TPasien.getText(),TglLahir.getText(),Jk.getText(),KdPetugas.getText(),NmPetugas.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),
                CurigaTuliTelingaKiri.getSelectedItem().toString(),CurigaTuliTelingaKanan.getSelectedItem().toString(),CurigaTuliTelingaRujuk.getSelectedItem().toString(),PenurunanPendengaranTelingaKiri.getSelectedItem().toString(), 
                PenurunanPendengaranTelingaKanan.getSelectedItem().toString(),MendengarBisikanTelingaKiri.getSelectedItem().toString(),MendengarBisikanTelingaKanan.getSelectedItem().toString(),CongekTelingaKiri.getSelectedItem().toString(), 
                CongekTelingaKanan.getSelectedItem().toString(),CongekTelingaRujuk.getSelectedItem().toString(),SumbatanSerumenTelingaKiri.getSelectedItem().toString(),SumbatanSerumenTelingaKanan.getSelectedItem().toString(), 
                SumbatanSerumenTelingaRujuk.getSelectedItem().toString(),HasilSkrining.getText(),Keterangan.getText()
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

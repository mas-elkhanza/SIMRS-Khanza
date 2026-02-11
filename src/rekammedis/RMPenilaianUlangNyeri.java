/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


package rekammedis;

import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import kepegawaian.DlgCariPetugas;


/**
 *
 * @author perpustakaan
 */
public final class RMPenilaianUlangNyeri extends javax.swing.JDialog {
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
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public RMPenilaianUlangNyeri(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(628,674);

        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.R.M.","Nama Pasien","Tgl.Lahir","JK","Tanggal","Tingkat Nyeri","Provokes","Ket. Provokes","Kualitas",
            "Ket. Kualitas","Lokasi","Menyebar","Skala Nyeri","Durasi","Nyeri Hilang","Ket. Hilang Nyeri","NIP","Petugas"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 19; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(105);
            }else if(i==1){
                column.setPreferredWidth(65);
            }else if(i==2){
                column.setPreferredWidth(160);
            }else if(i==3){
                column.setPreferredWidth(65);
            }else if(i==4){
                column.setPreferredWidth(25);
            }else if(i==5){
                column.setPreferredWidth(115);
            }else if(i==6){
                column.setPreferredWidth(81);
            }else if(i==7){
                column.setPreferredWidth(82);
            }else if(i==8){
                column.setPreferredWidth(120);
            }else if(i==9){
                column.setPreferredWidth(87);
            }else if(i==10){
                column.setPreferredWidth(120);
            }else if(i==11){
                column.setPreferredWidth(120);
            }else if(i==12){
                column.setPreferredWidth(57);
            }else if(i==13){
                column.setPreferredWidth(61);
            }else if(i==14){
                column.setPreferredWidth(60);
            }else if(i==15){
                column.setPreferredWidth(85);
            }else if(i==16){
                column.setPreferredWidth(120);
            }else if(i==17){
                column.setPreferredWidth(90);
            }else if(i==18){
                column.setPreferredWidth(150);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());

        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        KdPetugas.setDocument(new batasInput((byte)20).getKata(KdPetugas));
        TCari.setDocument(new batasInput((int)100).getKata(TCari));
        KetProvokes.setDocument(new batasInput((int)40).getKata(KetProvokes));
        KetQuality.setDocument(new batasInput((int)50).getKata(KetQuality));
        Lokasi.setDocument(new batasInput((int)50).getKata(Lokasi));
        Durasi.setDocument(new batasInput((int)25).getKata(Durasi));
        KetNyeri.setDocument(new batasInput((int)40).getKata(KetNyeri));
        
        ChkInput.setSelected(false);
        isForm();
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
        MnPenilaianUlangNyeri = new javax.swing.JMenuItem();
        JK = new widget.TextBox();
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
        jLabel8 = new widget.Label();
        TglLahir = new widget.TextBox();
        jSeparator2 = new javax.swing.JSeparator();
        PanelWall = new usu.widget.glass.PanelGlass();
        jSeparator9 = new javax.swing.JSeparator();
        Nyeri = new widget.ComboBox();
        jLabel80 = new widget.Label();
        Provokes = new widget.ComboBox();
        KetProvokes = new widget.TextBox();
        jLabel81 = new widget.Label();
        Quality = new widget.ComboBox();
        KetQuality = new widget.TextBox();
        jLabel82 = new widget.Label();
        jLabel83 = new widget.Label();
        Lokasi = new widget.TextBox();
        jLabel67 = new widget.Label();
        Menyebar = new widget.ComboBox();
        jLabel88 = new widget.Label();
        jLabel85 = new widget.Label();
        SkalaNyeri = new widget.ComboBox();
        jLabel87 = new widget.Label();
        Durasi = new widget.TextBox();
        jLabel84 = new widget.Label();
        jLabel89 = new widget.Label();
        NyeriHilang = new widget.ComboBox();
        KetNyeri = new widget.TextBox();
        jLabel86 = new widget.Label();
        jLabel90 = new widget.Label();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnPenilaianUlangNyeri.setBackground(new java.awt.Color(255, 255, 254));
        MnPenilaianUlangNyeri.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPenilaianUlangNyeri.setForeground(new java.awt.Color(50, 50, 50));
        MnPenilaianUlangNyeri.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPenilaianUlangNyeri.setText("Formulir Pengkajian Ulang Nyeri");
        MnPenilaianUlangNyeri.setName("MnPenilaianUlangNyeri"); // NOI18N
        MnPenilaianUlangNyeri.setPreferredSize(new java.awt.Dimension(230, 26));
        MnPenilaianUlangNyeri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPenilaianUlangNyeriActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnPenilaianUlangNyeri);

        JK.setHighlighter(null);
        JK.setName("JK"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Pengkajian Ulang Nyeri ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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
        jLabel7.setPreferredSize(new java.awt.Dimension(100, 23));
        panelGlass8.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(85, 23));
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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "07-02-2026" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "07-02-2026" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass9.add(DTPCari2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(350, 23));
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
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 276));
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
        FormInput.setPreferredSize(new java.awt.Dimension(130, 223));
        FormInput.setLayout(null);

        jLabel4.setText("No.Rawat :");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(0, 10, 80, 23);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(84, 10, 136, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        TPasien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPasienKeyPressed(evt);
            }
        });
        FormInput.add(TPasien);
        TPasien.setBounds(336, 10, 310, 23);

        Tanggal.setForeground(new java.awt.Color(50, 70, 50));
        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "07-02-2026" }));
        Tanggal.setDisplayFormat("dd-MM-yyyy");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.setOpaque(false);
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeyPressed(evt);
            }
        });
        FormInput.add(Tanggal);
        Tanggal.setBounds(84, 40, 90, 23);

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
        jLabel16.setBounds(0, 40, 80, 23);

        Jam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        Jam.setName("Jam"); // NOI18N
        Jam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JamKeyPressed(evt);
            }
        });
        FormInput.add(Jam);
        Jam.setBounds(178, 40, 62, 23);

        Menit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        Menit.setName("Menit"); // NOI18N
        Menit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MenitKeyPressed(evt);
            }
        });
        FormInput.add(Menit);
        Menit.setBounds(243, 40, 62, 23);

        Detik.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        Detik.setName("Detik"); // NOI18N
        Detik.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DetikKeyPressed(evt);
            }
        });
        FormInput.add(Detik);
        Detik.setBounds(308, 40, 62, 23);

        ChkKejadian.setBorder(null);
        ChkKejadian.setSelected(true);
        ChkKejadian.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkKejadian.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkKejadian.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkKejadian.setName("ChkKejadian"); // NOI18N
        FormInput.add(ChkKejadian);
        ChkKejadian.setBounds(373, 40, 23, 23);

        jLabel18.setText("Petugas :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(400, 40, 70, 23);

        KdPetugas.setEditable(false);
        KdPetugas.setHighlighter(null);
        KdPetugas.setName("KdPetugas"); // NOI18N
        FormInput.add(KdPetugas);
        KdPetugas.setBounds(474, 40, 104, 23);

        NmPetugas.setEditable(false);
        NmPetugas.setName("NmPetugas"); // NOI18N
        FormInput.add(NmPetugas);
        NmPetugas.setBounds(580, 40, 214, 23);

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
        BtnPetugas.setBounds(796, 40, 28, 23);

        jLabel8.setText("Tgl.Lahir :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(660, 10, 60, 23);

        TglLahir.setHighlighter(null);
        TglLahir.setName("TglLahir"); // NOI18N
        FormInput.add(TglLahir);
        TglLahir.setBounds(724, 10, 100, 23);

        jSeparator2.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator2.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator2.setName("jSeparator2"); // NOI18N
        FormInput.add(jSeparator2);
        jSeparator2.setBounds(0, 70, 830, 1);

        PanelWall.setBackground(new java.awt.Color(29, 29, 29));
        PanelWall.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/picture/nyeri.png"))); // NOI18N
        PanelWall.setBackgroundImageType(usu.widget.constan.BackgroundConstan.BACKGROUND_IMAGE_STRECT);
        PanelWall.setPreferredSize(new java.awt.Dimension(200, 200));
        PanelWall.setRound(false);
        PanelWall.setWarna(new java.awt.Color(110, 110, 110));
        PanelWall.setLayout(null);
        FormInput.add(PanelWall);
        PanelWall.setBounds(5, 80, 330, 160);

        jSeparator9.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator9.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator9.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator9.setName("jSeparator9"); // NOI18N
        FormInput.add(jSeparator9);
        jSeparator9.setBounds(335, 75, 1, 172);

        Nyeri.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada Nyeri", "Nyeri Akut", "Nyeri Kronis" }));
        Nyeri.setName("Nyeri"); // NOI18N
        Nyeri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NyeriKeyPressed(evt);
            }
        });
        FormInput.add(Nyeri);
        Nyeri.setBounds(345, 80, 130, 23);

        jLabel80.setText("Penyebab :");
        jLabel80.setName("jLabel80"); // NOI18N
        FormInput.add(jLabel80);
        jLabel80.setBounds(480, 80, 60, 23);

        Provokes.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Proses Penyakit", "Benturan", "Lain-lain", "-" }));
        Provokes.setName("Provokes"); // NOI18N
        Provokes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ProvokesKeyPressed(evt);
            }
        });
        FormInput.add(Provokes);
        Provokes.setBounds(544, 80, 130, 23);

        KetProvokes.setFocusTraversalPolicyProvider(true);
        KetProvokes.setName("KetProvokes"); // NOI18N
        KetProvokes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetProvokesKeyPressed(evt);
            }
        });
        FormInput.add(KetProvokes);
        KetProvokes.setBounds(678, 80, 146, 23);

        jLabel81.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel81.setText("Kualitas");
        jLabel81.setName("jLabel81"); // NOI18N
        FormInput.add(jLabel81);
        jLabel81.setBounds(345, 110, 60, 23);

        Quality.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seperti Tertusuk", "Berdenyut", "Teriris", "Tertindih", "Tertiban", "Lain-lain", "-" }));
        Quality.setName("Quality"); // NOI18N
        Quality.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                QualityKeyPressed(evt);
            }
        });
        FormInput.add(Quality);
        Quality.setBounds(397, 110, 140, 23);

        KetQuality.setFocusTraversalPolicyProvider(true);
        KetQuality.setName("KetQuality"); // NOI18N
        KetQuality.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetQualityKeyPressed(evt);
            }
        });
        FormInput.add(KetQuality);
        KetQuality.setBounds(541, 110, 283, 23);

        jLabel82.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel82.setText("Wilayah :");
        jLabel82.setName("jLabel82"); // NOI18N
        FormInput.add(jLabel82);
        jLabel82.setBounds(345, 140, 55, 23);

        jLabel83.setText("Lokasi :");
        jLabel83.setName("jLabel83"); // NOI18N
        FormInput.add(jLabel83);
        jLabel83.setBounds(359, 160, 60, 23);

        Lokasi.setFocusTraversalPolicyProvider(true);
        Lokasi.setName("Lokasi"); // NOI18N
        Lokasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LokasiKeyPressed(evt);
            }
        });
        FormInput.add(Lokasi);
        Lokasi.setBounds(423, 160, 225, 23);

        jLabel67.setText("Menyebar :");
        jLabel67.setName("jLabel67"); // NOI18N
        FormInput.add(jLabel67);
        jLabel67.setBounds(661, 160, 79, 23);

        Menyebar.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Menyebar.setName("Menyebar"); // NOI18N
        Menyebar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MenyebarKeyPressed(evt);
            }
        });
        FormInput.add(Menyebar);
        Menyebar.setBounds(744, 160, 80, 23);

        jLabel88.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel88.setText("Severity :");
        jLabel88.setName("jLabel88"); // NOI18N
        FormInput.add(jLabel88);
        jLabel88.setBounds(345, 190, 55, 23);

        jLabel85.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel85.setText("Skala Nyeri");
        jLabel85.setName("jLabel85"); // NOI18N
        FormInput.add(jLabel85);
        jLabel85.setBounds(390, 190, 70, 23);

        SkalaNyeri.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));
        SkalaNyeri.setName("SkalaNyeri"); // NOI18N
        SkalaNyeri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkalaNyeriKeyPressed(evt);
            }
        });
        FormInput.add(SkalaNyeri);
        SkalaNyeri.setBounds(457, 190, 70, 23);

        jLabel87.setText("Waktu / Durasi :");
        jLabel87.setName("jLabel87"); // NOI18N
        FormInput.add(jLabel87);
        jLabel87.setBounds(597, 190, 90, 23);

        Durasi.setFocusTraversalPolicyProvider(true);
        Durasi.setName("Durasi"); // NOI18N
        Durasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DurasiKeyPressed(evt);
            }
        });
        FormInput.add(Durasi);
        Durasi.setBounds(691, 190, 95, 23);

        jLabel84.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel84.setText("Menit");
        jLabel84.setName("jLabel84"); // NOI18N
        FormInput.add(jLabel84);
        jLabel84.setBounds(789, 190, 35, 23);

        jLabel89.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel89.setText("Nyeri Hilang Bila");
        jLabel89.setName("jLabel89"); // NOI18N
        FormInput.add(jLabel89);
        jLabel89.setBounds(345, 220, 110, 23);

        NyeriHilang.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Istirahat", "Medengar Musik", "Minum Obat", "-" }));
        NyeriHilang.setName("NyeriHilang"); // NOI18N
        NyeriHilang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NyeriHilangKeyPressed(evt);
            }
        });
        FormInput.add(NyeriHilang);
        NyeriHilang.setBounds(436, 220, 130, 23);

        KetNyeri.setFocusTraversalPolicyProvider(true);
        KetNyeri.setName("KetNyeri"); // NOI18N
        KetNyeri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetNyeriKeyPressed(evt);
            }
        });
        FormInput.add(KetNyeri);
        KetNyeri.setBounds(570, 220, 254, 23);

        jLabel86.setText(":");
        jLabel86.setName("jLabel86"); // NOI18N
        FormInput.add(jLabel86);
        jLabel86.setBounds(352, 110, 40, 23);

        jLabel90.setText(":");
        jLabel90.setName("jLabel90"); // NOI18N
        FormInput.add(jLabel90);
        jLabel90.setBounds(345, 220, 87, 23);

        scrollInput.setViewportView(FormInput);

        PanelInput.add(scrollInput, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isRawat();
            isPsien();
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
        }else{
            if(Sequel.menyimpantf("penilaian_ulang_nyeri","?,?,?,?,?,?,?,?,?,?,?,?,?,?","Data",14,new String[]{
                TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),
                Nyeri.getSelectedItem().toString(),Provokes.getSelectedItem().toString(),KetProvokes.getText(),Quality.getSelectedItem().toString(),KetQuality.getText(),
                Lokasi.getText(),Menyebar.getSelectedItem().toString(),SkalaNyeri.getSelectedItem().toString(),Durasi.getText(),NyeriHilang.getSelectedItem().toString(),
                KetNyeri.getText(),KdPetugas.getText()
            })==true){
                tabMode.addRow(new Object[]{
                    TNoRw.getText(),TNoRM.getText(),TPasien.getText(),TglLahir.getText(),JK.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),
                    Nyeri.getSelectedItem().toString(),Provokes.getSelectedItem().toString(),KetProvokes.getText(),Quality.getSelectedItem().toString(),KetQuality.getText(),Lokasi.getText(),Menyebar.getSelectedItem().toString(),
                    SkalaNyeri.getSelectedItem().toString(),Durasi.getText(),NyeriHilang.getSelectedItem().toString(),KetNyeri.getText(),KdPetugas.getText(),NmPetugas.getText()
                });
                emptTeks();
                LCount.setText(""+tabMode.getRowCount());
            }  
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,KetNyeri,BtnBatal);
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
                if(KdPetugas.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString())){
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
        }else if(KdPetugas.getText().trim().equals("")||NmPetugas.getText().trim().equals("")){
            Valid.textKosong(KdPetugas,"Petugas");
        }else{
            if(tbObat.getSelectedRow()>-1){
                if(akses.getkode().equals("Admin Utama")){
                    ganti();
                }else{
                    if(KdPetugas.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString())){
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
                Valid.MyReportqry("rptPenilaianUlangNyeri.jasper","report","::[ Data Pengkajian Ulang Nyeri ]::",
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,penilaian_ulang_nyeri.tanggal,"+
                    "penilaian_ulang_nyeri.nyeri,penilaian_ulang_nyeri.provokes,penilaian_ulang_nyeri.ket_provokes,penilaian_ulang_nyeri.quality,"+
                    "penilaian_ulang_nyeri.ket_quality,penilaian_ulang_nyeri.lokasi,penilaian_ulang_nyeri.menyebar,penilaian_ulang_nyeri.skala_nyeri,"+
                    "penilaian_ulang_nyeri.durasi,penilaian_ulang_nyeri.nyeri_hilang,penilaian_ulang_nyeri.ket_nyeri,penilaian_ulang_nyeri.nip,petugas.nama "+
                    "from penilaian_ulang_nyeri inner join reg_periksa on penilaian_ulang_nyeri.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join petugas on penilaian_ulang_nyeri.nip=petugas.nip where "+
                    "penilaian_ulang_nyeri.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59' "+
                    "order by penilaian_ulang_nyeri.tanggal",param);
            }else{
                Valid.MyReportqry("rptPenilaianUlangNyeri.jasper","report","::[ Data Pengkajian Ulang Nyeri ]::",
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,penilaian_ulang_nyeri.tanggal,"+
                    "penilaian_ulang_nyeri.nyeri,penilaian_ulang_nyeri.provokes,penilaian_ulang_nyeri.ket_provokes,penilaian_ulang_nyeri.quality,"+
                    "penilaian_ulang_nyeri.ket_quality,penilaian_ulang_nyeri.lokasi,penilaian_ulang_nyeri.menyebar,penilaian_ulang_nyeri.skala_nyeri,"+
                    "penilaian_ulang_nyeri.durasi,penilaian_ulang_nyeri.nyeri_hilang,penilaian_ulang_nyeri.ket_nyeri,penilaian_ulang_nyeri.nip,petugas.nama "+
                    "from penilaian_ulang_nyeri inner join reg_periksa on penilaian_ulang_nyeri.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join petugas on penilaian_ulang_nyeri.nip=petugas.nip where "+
                    "penilaian_ulang_nyeri.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59' and "+
                    "(reg_periksa.no_rawat like '%"+TCari.getText().trim()+"%' or pasien.no_rkm_medis like '%"+TCari.getText().trim()+"%' or pasien.nm_pasien like '%"+TCari.getText().trim()+"%' "+
                    "or penilaian_ulang_nyeri.nip like '%"+TCari.getText().trim()+"%' or petugas.nama like '%"+TCari.getText().trim()+"%') "+
                    "order by penilaian_ulang_nyeri.tanggal ",param);
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

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

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
        Valid.pindah(evt,Detik,Nyeri);
    }//GEN-LAST:event_BtnPetugasKeyPressed

    private void MnPenilaianUlangNyeriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPenilaianUlangNyeriActionPerformed
        if(tbObat.getSelectedRow()>-1){
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());   
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            param.put("nyeri",Sequel.cariGambar("select gambar.nyeri from gambar")); 
            Valid.MyReportqry("rptFormulirPenilaianUlangNyeri.jasper","report","::[ Formulir Pengkajian Ulang Nyeri ]::",
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,penilaian_ulang_nyeri.tanggal,"+
                    "penilaian_ulang_nyeri.nyeri,penilaian_ulang_nyeri.provokes,penilaian_ulang_nyeri.ket_provokes,penilaian_ulang_nyeri.quality,"+
                    "penilaian_ulang_nyeri.ket_quality,penilaian_ulang_nyeri.lokasi,penilaian_ulang_nyeri.menyebar,penilaian_ulang_nyeri.skala_nyeri,"+
                    "penilaian_ulang_nyeri.durasi,penilaian_ulang_nyeri.nyeri_hilang,penilaian_ulang_nyeri.ket_nyeri,penilaian_ulang_nyeri.nip,petugas.nama "+
                    "from penilaian_ulang_nyeri inner join reg_periksa on penilaian_ulang_nyeri.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join petugas on penilaian_ulang_nyeri.nip=petugas.nip where reg_periksa.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'",param);
        }
    }//GEN-LAST:event_MnPenilaianUlangNyeriActionPerformed

    private void NyeriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NyeriKeyPressed
        Valid.pindah(evt,BtnPetugas,Provokes);
    }//GEN-LAST:event_NyeriKeyPressed

    private void ProvokesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ProvokesKeyPressed
        Valid.pindah(evt,Nyeri,KetProvokes);
    }//GEN-LAST:event_ProvokesKeyPressed

    private void KetProvokesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetProvokesKeyPressed
        Valid.pindah(evt,Provokes,Quality);
    }//GEN-LAST:event_KetProvokesKeyPressed

    private void QualityKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_QualityKeyPressed
        Valid.pindah(evt,KetProvokes,KetQuality);
    }//GEN-LAST:event_QualityKeyPressed

    private void KetQualityKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetQualityKeyPressed
        Valid.pindah(evt,Quality,Lokasi);
    }//GEN-LAST:event_KetQualityKeyPressed

    private void LokasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LokasiKeyPressed
        Valid.pindah(evt,KetQuality,Menyebar);
    }//GEN-LAST:event_LokasiKeyPressed

    private void MenyebarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MenyebarKeyPressed
        Valid.pindah(evt,Lokasi,SkalaNyeri);
    }//GEN-LAST:event_MenyebarKeyPressed

    private void SkalaNyeriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkalaNyeriKeyPressed
        Valid.pindah(evt,Menyebar,Durasi);
    }//GEN-LAST:event_SkalaNyeriKeyPressed

    private void DurasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DurasiKeyPressed
        Valid.pindah(evt,SkalaNyeri,NyeriHilang);
    }//GEN-LAST:event_DurasiKeyPressed

    private void NyeriHilangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NyeriHilangKeyPressed
        Valid.pindah(evt,Durasi,KetNyeri);
    }//GEN-LAST:event_NyeriHilangKeyPressed

    private void KetNyeriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetNyeriKeyPressed
        Valid.pindah(evt,NyeriHilang,BtnSimpan);
    }//GEN-LAST:event_KetNyeriKeyPressed

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
            RMPenilaianUlangNyeri dialog = new RMPenilaianUlangNyeri(new javax.swing.JFrame(), true);
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
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.ComboBox Detik;
    private widget.TextBox Durasi;
    private widget.PanelBiasa FormInput;
    private widget.TextBox JK;
    private widget.ComboBox Jam;
    private widget.TextBox KdPetugas;
    private widget.TextBox KetNyeri;
    private widget.TextBox KetProvokes;
    private widget.TextBox KetQuality;
    private widget.Label LCount;
    private widget.TextBox Lokasi;
    private widget.ComboBox Menit;
    private widget.ComboBox Menyebar;
    private javax.swing.JMenuItem MnPenilaianUlangNyeri;
    private widget.TextBox NmPetugas;
    private widget.ComboBox Nyeri;
    private widget.ComboBox NyeriHilang;
    private javax.swing.JPanel PanelInput;
    private usu.widget.glass.PanelGlass PanelWall;
    private widget.ComboBox Provokes;
    private widget.ComboBox Quality;
    private widget.ScrollPane Scroll;
    private widget.ComboBox SkalaNyeri;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.Tanggal Tanggal;
    private widget.TextBox TglLahir;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel16;
    private widget.Label jLabel18;
    private widget.Label jLabel19;
    private widget.Label jLabel21;
    private widget.Label jLabel4;
    private widget.Label jLabel6;
    private widget.Label jLabel67;
    private widget.Label jLabel7;
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
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator9;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollInput;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables
    
    public void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            if(TCari.getText().toString().trim().equals("")){
                ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,penilaian_ulang_nyeri.tanggal,"+
                    "penilaian_ulang_nyeri.nyeri,penilaian_ulang_nyeri.provokes,penilaian_ulang_nyeri.ket_provokes,penilaian_ulang_nyeri.quality,"+
                    "penilaian_ulang_nyeri.ket_quality,penilaian_ulang_nyeri.lokasi,penilaian_ulang_nyeri.menyebar,penilaian_ulang_nyeri.skala_nyeri,"+
                    "penilaian_ulang_nyeri.durasi,penilaian_ulang_nyeri.nyeri_hilang,penilaian_ulang_nyeri.ket_nyeri,penilaian_ulang_nyeri.nip,petugas.nama "+
                    "from penilaian_ulang_nyeri inner join reg_periksa on penilaian_ulang_nyeri.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join petugas on penilaian_ulang_nyeri.nip=petugas.nip where "+
                    "penilaian_ulang_nyeri.tanggal between ? and ? order by penilaian_ulang_nyeri.tanggal");
            }else{
                ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,penilaian_ulang_nyeri.tanggal,"+
                    "penilaian_ulang_nyeri.nyeri,penilaian_ulang_nyeri.provokes,penilaian_ulang_nyeri.ket_provokes,penilaian_ulang_nyeri.quality,"+
                    "penilaian_ulang_nyeri.ket_quality,penilaian_ulang_nyeri.lokasi,penilaian_ulang_nyeri.menyebar,penilaian_ulang_nyeri.skala_nyeri,"+
                    "penilaian_ulang_nyeri.durasi,penilaian_ulang_nyeri.nyeri_hilang,penilaian_ulang_nyeri.ket_nyeri,penilaian_ulang_nyeri.nip,petugas.nama "+
                    "from penilaian_ulang_nyeri inner join reg_periksa on penilaian_ulang_nyeri.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join petugas on penilaian_ulang_nyeri.nip=petugas.nip where "+
                    "penilaian_ulang_nyeri.tanggal between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or penilaian_ulang_nyeri.nip like ? or petugas.nama like ?) "+
                    "order by penilaian_ulang_nyeri.tanggal ");
            }
                
            try {
                if(TCari.getText().toString().trim().equals("")){
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
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getDate("tgl_lahir"),rs.getString("jk"),rs.getString("tanggal"),
                        rs.getString("nyeri"),rs.getString("provokes"),rs.getString("ket_provokes"),rs.getString("quality"),rs.getString("ket_quality"),rs.getString("lokasi"),
                        rs.getString("menyebar"),rs.getString("skala_nyeri"),rs.getString("durasi"),rs.getString("nyeri_hilang"),rs.getString("ket_nyeri"),rs.getString("nip"),
                        rs.getString("nama")
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
        }catch(SQLException e){
            System.out.println("Notifikasi : "+e);
        }
        LCount.setText(""+tabMode.getRowCount());
    }
    
    public void emptTeks() {
        Tanggal.setDate(new Date());
        Nyeri.setSelectedIndex(0);
        Provokes.setSelectedIndex(0);
        KetProvokes.setText("");
        Quality.setSelectedIndex(0);
        KetQuality.setText("");
        Lokasi.setText("");
        Menyebar.setSelectedIndex(0);
        SkalaNyeri.setSelectedIndex(0);
        Durasi.setText("");
        NyeriHilang.setSelectedIndex(0);
        KetNyeri.setText("");
        Nyeri.requestFocus();
    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()); 
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            JK.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString());
            Nyeri.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString());
            Provokes.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());
            KetProvokes.setText(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            Quality.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            KetQuality.setText(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            Lokasi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            Menyebar.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            SkalaNyeri.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            Durasi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            NyeriHilang.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
            KetNyeri.setText(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            Jam.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString().substring(11,13));
            Menit.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString().substring(14,16));
            Detik.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString().substring(17,19));
            Valid.SetTgl(Tanggal,tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
        }
    }
    private void isRawat() {
         Sequel.cariIsi("select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat='"+TNoRw.getText()+"' ",TNoRM);
    }

    private void isPsien() {
        try {
            ps=koneksi.prepareStatement("select pasien.nm_pasien,pasien.jk,date_format(pasien.tgl_lahir,'%d-%m-%Y') as lahir from pasien where pasien.no_rkm_medis=?");
            try {
                ps.setString(1,TNoRM.getText());
                rs=ps.executeQuery();
                if(rs.next()){
                    TPasien.setText(rs.getString("nm_pasien"));
                    JK.setText(rs.getString("jk"));
                    TglLahir.setText(rs.getString("lahir"));
                }
            } catch (Exception e) {
                System.out.println("Notif : "+e);
            }finally {
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
        Sequel.cariIsi("select reg_periksa.tgl_registrasi from reg_periksa where reg_periksa.no_rawat='"+norwt+"'", DTPCari1);
        DTPCari2.setDate(tgl2);
        isRawat();
        isPsien();
        ChkInput.setSelected(true);
        isForm();
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,276));
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
        BtnSimpan.setEnabled(akses.getpenilaian_ulang_nyeri());
        BtnHapus.setEnabled(akses.getpenilaian_ulang_nyeri());
        BtnEdit.setEnabled(akses.getpenilaian_ulang_nyeri());
        BtnPrint.setEnabled(akses.getpenilaian_ulang_nyeri()); 
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
        if(Sequel.mengedittf("penilaian_ulang_nyeri","tanggal=? and no_rawat=?","no_rawat=?,tanggal=?,nyeri=?,provokes=?,ket_provokes=?,quality=?,ket_quality=?,lokasi=?,"+
                "menyebar=?,skala_nyeri=?,durasi=?,nyeri_hilang=?,ket_nyeri=?,nip=?",16,new String[]{
                TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),
                Nyeri.getSelectedItem().toString(),Provokes.getSelectedItem().toString(),KetProvokes.getText(),Quality.getSelectedItem().toString(),KetQuality.getText(),
                Lokasi.getText(),Menyebar.getSelectedItem().toString(),SkalaNyeri.getSelectedItem().toString(),Durasi.getText(),NyeriHilang.getSelectedItem().toString(),
                KetNyeri.getText(),KdPetugas.getText(),tbObat.getValueAt(tbObat.getSelectedRow(),5).toString(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
            })==true){
            tbObat.setValueAt(TNoRw.getText(),tbObat.getSelectedRow(),0);
            tbObat.setValueAt(TNoRM.getText(),tbObat.getSelectedRow(),1);
            tbObat.setValueAt(TPasien.getText(),tbObat.getSelectedRow(),2);
            tbObat.setValueAt(TglLahir.getText(),tbObat.getSelectedRow(),3);
            tbObat.setValueAt(JK.getText(),tbObat.getSelectedRow(),4);
            tbObat.setValueAt(Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),tbObat.getSelectedRow(),5);
            tbObat.setValueAt(Nyeri.getSelectedItem().toString(),tbObat.getSelectedRow(),6);
            tbObat.setValueAt(Provokes.getSelectedItem().toString(),tbObat.getSelectedRow(),7);
            tbObat.setValueAt(KetProvokes.getText(),tbObat.getSelectedRow(),8);
            tbObat.setValueAt(Quality.getSelectedItem().toString(),tbObat.getSelectedRow(),9);
            tbObat.setValueAt(KetQuality.getText(),tbObat.getSelectedRow(),10);
            tbObat.setValueAt(Lokasi.getText(),tbObat.getSelectedRow(),11);
            tbObat.setValueAt(Menyebar.getSelectedItem().toString(),tbObat.getSelectedRow(),12);
            tbObat.setValueAt(SkalaNyeri.getSelectedItem().toString(),tbObat.getSelectedRow(),13);
            tbObat.setValueAt(Durasi.getText(),tbObat.getSelectedRow(),14);
            tbObat.setValueAt(NyeriHilang.getSelectedItem().toString(),tbObat.getSelectedRow(),15);
            tbObat.setValueAt(KetNyeri.getText(),tbObat.getSelectedRow(),16);
            tbObat.setValueAt(KdPetugas.getText(),tbObat.getSelectedRow(),17);
            tbObat.setValueAt(NmPetugas.getText(),tbObat.getSelectedRow(),18);
            emptTeks();
        }
    }

    private void hapus() {
        if(Sequel.queryu2tf("delete from penilaian_ulang_nyeri where tanggal=? and no_rawat=?",2,new String[]{
            tbObat.getValueAt(tbObat.getSelectedRow(),5).toString(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
        })==true){
            tabMode.removeRow(tbObat.getSelectedRow());
            emptTeks();
            LCount.setText(""+tabMode.getRowCount());
        }else{
            JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
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

package simrskhanza;

import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import laporan.LaporanSisaDietPasien;
import setting.DlgCariJamDiet;

/**
 *
 * @author dosen
 */
public class DlgPemberianDiet extends javax.swing.JDialog {
    private final DefaultTableModel tabMode,tabMode2;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps,ps2;
    private ResultSet rs;
    private int i=0,pilih=0;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private volatile boolean ceksukses = false;

    /** Creates new form DlgPemberianInfus
     * @param parent
     * @param modal */
    public DlgPemberianDiet(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        tabMode=new DefaultTableModel(null,new Object[]{
                "No.Rawat","Nama Pasien","Kamar","Tanggal","Waktu","Jam","Diet","Diagnosa","Kode Kamar","Kode Diet"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbDataDiet.setModel(tabMode);
        tbDataDiet.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbDataDiet.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 10; i++) {
            TableColumn column = tbDataDiet.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(105);
            }else if(i==1){
                column.setPreferredWidth(230);
            }else if(i==2){
                column.setPreferredWidth(180);
            }else if(i==3){
                column.setPreferredWidth(65);
            }else if(i==4){
                column.setPreferredWidth(60);
            }else if(i==6){
                column.setPreferredWidth(170);
            }else if(i==5){
                column.setPreferredWidth(55);
            }else if(i==7){
                column.setPreferredWidth(170);
            }else if(i==8){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==9){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }
        }
        tbDataDiet.setDefaultRenderer(Object.class, new WarnaTable());

        tabMode2=new DefaultTableModel(null,new Object[]{
                "No","Nama Diet","Jumlah Diet"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbRekapDiet.setModel(tabMode2);
        tbRekapDiet.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbRekapDiet.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 3; i++) {
            TableColumn column = tbRekapDiet.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(30);
            }else if(i==1){
                column.setPreferredWidth(540);
            }else if(i==2){
                column.setPreferredWidth(110);
            }
        }
        tbRekapDiet.setDefaultRenderer(Object.class, new WarnaTable());

        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        KdDiet.setDocument(new batasInput((byte)3).getKata(KdDiet));
        if(koneksiDB.CARICEPAT().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        if(TabRawat.getSelectedIndex()==0){
                            runBackground(() ->tampil());
                        }else if(TabRawat.getSelectedIndex()==1){
                            runBackground(() ->tampil2());
                        }
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        if(TabRawat.getSelectedIndex()==0){
                            runBackground(() ->tampil());
                        }else if(TabRawat.getSelectedIndex()==1){
                            runBackground(() ->tampil2());
                        }
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        if(TabRawat.getSelectedIndex()==0){
                            runBackground(() ->tampil());
                        }else if(TabRawat.getSelectedIndex()==1){
                            runBackground(() ->tampil2());
                        }
                    }
                }
            });
        } 
        
        ChkInput.setSelected(false);
        isForm();
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
        MnLabelDiet = new javax.swing.JMenuItem();
        MnLabelDiet1 = new javax.swing.JMenuItem();
        MnSisaDietPasien = new javax.swing.JMenuItem();
        Ruang = new widget.TextBox();
        Diagnosa = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        jPanel3 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnPrint = new widget.Button();
        BtnAll = new widget.Button();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();
        BtnKeluar = new widget.Button();
        panelGlass9 = new widget.panelisi();
        jLabel11 = new widget.Label();
        WaktuDiet2 = new widget.TextBox();
        JamDiet2 = new widget.TextBox();
        BtnJam2 = new widget.Button();
        jLabel12 = new widget.Label();
        NmBangsalCari = new widget.TextBox();
        BtnSeek2 = new widget.Button();
        panelGlass10 = new widget.panelisi();
        jLabel19 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel21 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        PanelInput = new javax.swing.JPanel();
        ChkInput = new widget.CekBox();
        FormInput = new widget.PanelBiasa();
        jLabel4 = new widget.Label();
        TNoRw = new widget.TextBox();
        jLabel9 = new widget.Label();
        NmDiet = new widget.TextBox();
        TPasien = new widget.TextBox();
        DTPTgl = new widget.Tanggal();
        jLabel10 = new widget.Label();
        KdDiet = new widget.TextBox();
        BtnSeek1 = new widget.Button();
        Kamar = new widget.TextBox();
        WaktuDiet = new widget.TextBox();
        JamDiet = new widget.TextBox();
        BtnJam = new widget.Button();
        TabRawat = new javax.swing.JTabbedPane();
        Scroll = new widget.ScrollPane();
        tbDataDiet = new widget.Table();
        Scroll1 = new widget.ScrollPane();
        tbRekapDiet = new widget.Table();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnLabelDiet.setBackground(new java.awt.Color(255, 255, 254));
        MnLabelDiet.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLabelDiet.setForeground(java.awt.Color.darkGray);
        MnLabelDiet.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLabelDiet.setText("Label Diet");
        MnLabelDiet.setName("MnLabelDiet"); // NOI18N
        MnLabelDiet.setPreferredSize(new java.awt.Dimension(150, 28));
        MnLabelDiet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLabelDietActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnLabelDiet);

        MnLabelDiet1.setBackground(new java.awt.Color(255, 255, 254));
        MnLabelDiet1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLabelDiet1.setForeground(java.awt.Color.darkGray);
        MnLabelDiet1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLabelDiet1.setText("Semua Label Diet");
        MnLabelDiet1.setName("MnLabelDiet1"); // NOI18N
        MnLabelDiet1.setPreferredSize(new java.awt.Dimension(150, 28));
        MnLabelDiet1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLabelDiet1ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnLabelDiet1);

        MnSisaDietPasien.setBackground(new java.awt.Color(255, 255, 254));
        MnSisaDietPasien.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSisaDietPasien.setForeground(java.awt.Color.darkGray);
        MnSisaDietPasien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnSisaDietPasien.setText("Sisa Diet Pasien");
        MnSisaDietPasien.setName("MnSisaDietPasien"); // NOI18N
        MnSisaDietPasien.setPreferredSize(new java.awt.Dimension(150, 28));
        MnSisaDietPasien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSisaDietPasienActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnSisaDietPasien);

        Ruang.setEditable(false);
        Ruang.setHighlighter(null);
        Ruang.setName("Ruang"); // NOI18N

        Diagnosa.setEditable(false);
        Diagnosa.setHighlighter(null);
        Diagnosa.setName("Diagnosa"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Diet Harian Pasien ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(44, 144));
        jPanel3.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(55, 55));
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

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(65, 23));
        panelGlass8.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(50, 23));
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

        jPanel3.add(panelGlass8, java.awt.BorderLayout.PAGE_END);

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel11.setText("Waktu Diet :");
        jLabel11.setName("jLabel11"); // NOI18N
        jLabel11.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(jLabel11);

        WaktuDiet2.setEditable(false);
        WaktuDiet2.setHighlighter(null);
        WaktuDiet2.setName("WaktuDiet2"); // NOI18N
        WaktuDiet2.setPreferredSize(new java.awt.Dimension(70, 23));
        WaktuDiet2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                WaktuDiet2KeyPressed(evt);
            }
        });
        panelGlass9.add(WaktuDiet2);

        JamDiet2.setEditable(false);
        JamDiet2.setHighlighter(null);
        JamDiet2.setName("JamDiet2"); // NOI18N
        JamDiet2.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(JamDiet2);

        BtnJam2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnJam2.setMnemonic('X');
        BtnJam2.setToolTipText("Alt+X");
        BtnJam2.setName("BtnJam2"); // NOI18N
        BtnJam2.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnJam2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnJam2ActionPerformed(evt);
            }
        });
        BtnJam2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnJam2KeyPressed(evt);
            }
        });
        panelGlass9.add(BtnJam2);

        jLabel12.setText("Bangsal :");
        jLabel12.setName("jLabel12"); // NOI18N
        jLabel12.setPreferredSize(new java.awt.Dimension(133, 23));
        panelGlass9.add(jLabel12);

        NmBangsalCari.setEditable(false);
        NmBangsalCari.setHighlighter(null);
        NmBangsalCari.setName("NmBangsalCari"); // NOI18N
        NmBangsalCari.setPreferredSize(new java.awt.Dimension(310, 23));
        panelGlass9.add(NmBangsalCari);

        BtnSeek2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek2.setMnemonic('X');
        BtnSeek2.setToolTipText("Alt+X");
        BtnSeek2.setName("BtnSeek2"); // NOI18N
        BtnSeek2.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSeek2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek2ActionPerformed(evt);
            }
        });
        BtnSeek2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSeek2KeyPressed(evt);
            }
        });
        panelGlass9.add(BtnSeek2);

        jPanel3.add(panelGlass9, java.awt.BorderLayout.PAGE_START);

        panelGlass10.setName("panelGlass10"); // NOI18N
        panelGlass10.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass10.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel19.setText("Tgl.Rawat :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(67, 23));
        panelGlass10.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "07-05-2023" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass10.add(DTPCari1);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("s.d.");
        jLabel21.setName("jLabel21"); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(23, 23));
        panelGlass10.add(jLabel21);

        DTPCari2.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "07-05-2023" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass10.add(DTPCari2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(100, 23));
        panelGlass10.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(310, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass10.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('2');
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
        panelGlass10.add(BtnCari);

        jPanel3.add(panelGlass10, java.awt.BorderLayout.CENTER);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        PanelInput.setBackground(new java.awt.Color(255, 255, 255));
        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        ChkInput.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput.setMnemonic('M');
        ChkInput.setText(".: Input Data");
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

        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(160, 77));
        FormInput.setLayout(null);

        jLabel4.setText("No.Rawat :");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(0, 12, 74, 23);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(78, 12, 125, 23);

        jLabel9.setText("Diet :");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(367, 42, 39, 23);

        NmDiet.setEditable(false);
        NmDiet.setHighlighter(null);
        NmDiet.setName("NmDiet"); // NOI18N
        FormInput.add(NmDiet);
        NmDiet.setBounds(497, 42, 212, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        FormInput.add(TPasien);
        TPasien.setBounds(205, 12, 290, 23);

        DTPTgl.setForeground(new java.awt.Color(50, 70, 50));
        DTPTgl.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "07-05-2023" }));
        DTPTgl.setDisplayFormat("dd-MM-yyyy");
        DTPTgl.setName("DTPTgl"); // NOI18N
        DTPTgl.setOpaque(false);
        DTPTgl.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPTglKeyPressed(evt);
            }
        });
        FormInput.add(DTPTgl);
        DTPTgl.setBounds(78, 42, 90, 23);

        jLabel10.setText("Tanggal :");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(0, 42, 74, 23);

        KdDiet.setHighlighter(null);
        KdDiet.setName("KdDiet"); // NOI18N
        KdDiet.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdDietKeyPressed(evt);
            }
        });
        FormInput.add(KdDiet);
        KdDiet.setBounds(410, 42, 85, 23);

        BtnSeek1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek1.setMnemonic('X');
        BtnSeek1.setToolTipText("Alt+X");
        BtnSeek1.setName("BtnSeek1"); // NOI18N
        BtnSeek1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek1ActionPerformed(evt);
            }
        });
        BtnSeek1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSeek1KeyPressed(evt);
            }
        });
        FormInput.add(BtnSeek1);
        BtnSeek1.setBounds(712, 42, 28, 23);

        Kamar.setEditable(false);
        Kamar.setHighlighter(null);
        Kamar.setName("Kamar"); // NOI18N
        FormInput.add(Kamar);
        Kamar.setBounds(497, 12, 243, 23);

        WaktuDiet.setEditable(false);
        WaktuDiet.setHighlighter(null);
        WaktuDiet.setName("WaktuDiet"); // NOI18N
        WaktuDiet.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                WaktuDietKeyPressed(evt);
            }
        });
        FormInput.add(WaktuDiet);
        WaktuDiet.setBounds(171, 42, 70, 23);

        JamDiet.setEditable(false);
        JamDiet.setHighlighter(null);
        JamDiet.setName("JamDiet"); // NOI18N
        FormInput.add(JamDiet);
        JamDiet.setBounds(243, 42, 70, 23);

        BtnJam.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnJam.setMnemonic('X');
        BtnJam.setToolTipText("Alt+X");
        BtnJam.setName("BtnJam"); // NOI18N
        BtnJam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnJamActionPerformed(evt);
            }
        });
        BtnJam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnJamKeyPressed(evt);
            }
        });
        FormInput.add(BtnJam);
        BtnJam.setBounds(315, 42, 28, 23);

        PanelInput.add(FormInput, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        TabRawat.setBackground(new java.awt.Color(255, 255, 254));
        TabRawat.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(241, 246, 236)));
        TabRawat.setForeground(new java.awt.Color(50, 50, 50));
        TabRawat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabRawat.setName("TabRawat"); // NOI18N
        TabRawat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatMouseClicked(evt);
            }
        });

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbDataDiet.setAutoCreateRowSorter(true);
        tbDataDiet.setComponentPopupMenu(jPopupMenu1);
        tbDataDiet.setName("tbDataDiet"); // NOI18N
        tbDataDiet.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDataDietMouseClicked(evt);
            }
        });
        tbDataDiet.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbDataDietKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbDataDiet);

        TabRawat.addTab("Data Diet Pasien", Scroll);

        Scroll1.setName("Scroll1"); // NOI18N
        Scroll1.setOpaque(true);

        tbRekapDiet.setAutoCreateRowSorter(true);
        tbRekapDiet.setComponentPopupMenu(jPopupMenu1);
        tbRekapDiet.setName("tbRekapDiet"); // NOI18N
        tbRekapDiet.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbRekapDietMouseClicked(evt);
            }
        });
        tbRekapDiet.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbRekapDietKeyPressed(evt);
            }
        });
        Scroll1.setViewportView(tbRekapDiet);

        TabRawat.addTab("Rekap Diet Pasien", Scroll1);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isRawat();
        }else{            
            Valid.pindah(evt,WaktuDiet,KdDiet);
        }
}//GEN-LAST:event_TNoRwKeyPressed

    private void DTPTglKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPTglKeyPressed
        Valid.pindah(evt,TCari,WaktuDiet);
}//GEN-LAST:event_DTPTglKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TabRawat.getSelectedIndex()==0){
            if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
                Valid.textKosong(TNoRw,"pasien");
            }else if(NmDiet.getText().trim().equals("")){
                Valid.textKosong(KdDiet,"diet");
            }else if(JamDiet.getText().trim().equals("")){
                Valid.textKosong(WaktuDiet,"Waktu Diet");
            }else{
                if(Sequel.menyimpantf("detail_beri_diet","'"+TNoRw.getText()+"','"+Kamar.getText()+"','"+
                        Valid.SetTgl(DTPTgl.getSelectedItem()+"")+"','"+
                        WaktuDiet.getText()+"','"+
                        KdDiet.getText()+"'","data")==true){
                    tabMode.addRow(new Object[]{
                        TNoRw.getText(),TPasien.getText(),Ruang.getText(),Valid.SetTgl(DTPTgl.getSelectedItem()+""),WaktuDiet.getText(),JamDiet.getText(),NmDiet.getText(),"-",Kamar.getText(),KdDiet.getText()
                    });
                    LCount.setText(""+tabMode.getRowCount());
                    emptTeks();
                }
            }
        }else{
            JOptionPane.showMessageDialog(null,"Silahkan buka data diet pasien");
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,TPasien,BtnBatal);
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
        if(TabRawat.getSelectedIndex()==0){
            if(tabMode.getRowCount()==0){
                JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
                DTPTgl.requestFocus();
            }else if(TPasien.getText().trim().equals("")){
                JOptionPane.showMessageDialog(null,"Maaf, Gagal menghapus. Pilih dulu data yang mau dihapus.\nKlik data pada table untuk memilih...!!!!");
            }else if(!(TPasien.getText().trim().equals(""))){
                if(tbDataDiet.getSelectedRow()!= -1){
                    if(Sequel.queryutf("delete from detail_beri_diet " +
                            "where no_rawat='"+TNoRw.getText()+"' " +
                            "and tanggal='"+Valid.SetTgl(DTPTgl.getSelectedItem()+"")+"' " +
                            "and waktu='"+WaktuDiet.getText()+"' " +
                            "and kd_diet='"+KdDiet.getText()+"'")==true){
                        tabMode.removeRow(tbDataDiet.getSelectedRow());
                        LCount.setText(""+tabMode.getRowCount());
                    }
                }
            }
        }else{
            JOptionPane.showMessageDialog(null,"Silahkan buka data diet pasien");
        }
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnBatal, BtnPrint);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnPrint,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        if(TabRawat.getSelectedIndex()==0){
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
                Valid.MyReportqry("rptBrDiet.jasper","report","::[ Data Pemberian Diet ]::",
                    "select detail_beri_diet.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,concat(detail_beri_diet.kd_kamar,', ',bangsal.nm_bangsal) as namakamar,"+
                    "detail_beri_diet.tanggal,detail_beri_diet.waktu,jam_diet_pasien.jam,diet.nama_diet,detail_beri_diet.kd_kamar,detail_beri_diet.kd_diet " +
                    "from detail_beri_diet inner join reg_periksa on detail_beri_diet.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join diet on detail_beri_diet.kd_diet=diet.kd_diet "+
                    "inner join kamar on detail_beri_diet.kd_kamar=kamar.kd_kamar "+
                    "inner join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal "+
                    "inner join jam_diet_pasien on detail_beri_diet.waktu=jam_diet_pasien.waktu " +
                    "where detail_beri_diet.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and detail_beri_diet.waktu like '%"+WaktuDiet2.getText().trim()+"%' and bangsal.nm_bangsal like '%"+NmBangsalCari.getText().trim()+"%' and "+
                    "(detail_beri_diet.no_rawat like '%"+TCari.getText().trim()+"%' or reg_periksa.no_rkm_medis like '%"+TCari.getText().trim()+"%' or pasien.nm_pasien like '%"+TCari.getText().trim()+"%') order by bangsal.nm_bangsal,diet.nama_diet",param);
            }
            this.setCursor(Cursor.getDefaultCursor());
        }else{
            JOptionPane.showMessageDialog(null,"Silahkan buka data diet pasien");
        }
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnKeluar);
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
        if(TabRawat.getSelectedIndex()==0){
            runBackground(() ->tampil());
        }else if(TabRawat.getSelectedIndex()==1){
            runBackground(() ->tampil2());
        }
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
        NmBangsalCari.setText("");
        KdDiet.setText("");
        NmDiet.setText("");
        WaktuDiet2.setText("");
        JamDiet2.setText("");
        if(TabRawat.getSelectedIndex()==0){
            runBackground(() ->tampil());
        }else if(TabRawat.getSelectedIndex()==1){
            runBackground(() ->tampil2());
        }
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            TCari.setText("");
            runBackground(() ->tampil());
        }else{
            Valid.pindah(evt, BtnCari, TPasien);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void tbDataDietMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDataDietMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbDataDietMouseClicked

    private void tbDataDietKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbDataDietKeyPressed
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
}//GEN-LAST:event_tbDataDietKeyPressed

private void KdDietKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdDietKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnSeek1ActionPerformed(null);
        }else{
            Valid.pindah(evt,WaktuDiet,BtnSimpan);
        }
}//GEN-LAST:event_KdDietKeyPressed

private void BtnSeek1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek1ActionPerformed
        akses.setform("DlgPemberianDiet");  
        DlgCariDiet diet=new DlgCariDiet(null,false);
        diet.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgPemberianDiet")){
                    if(diet.getTable().getSelectedRow()!= -1){  
                        KdDiet.setText(diet.getTable().getValueAt(diet.getTable().getSelectedRow(),0).toString());
                        NmDiet.setText(diet.getTable().getValueAt(diet.getTable().getSelectedRow(),01).toString());
                        KdDiet.requestFocus();                           
                    }                         
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
        diet.emptTeks();
        diet.isCek();
        diet.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        diet.setLocationRelativeTo(internalFrame1);
        diet.setVisible(true);
}//GEN-LAST:event_BtnSeek1ActionPerformed

private void BtnSeek1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeek1KeyPressed
        Valid.pindah(evt,KdDiet,BtnSimpan);
}//GEN-LAST:event_BtnSeek1KeyPressed

private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
  isForm();                
}//GEN-LAST:event_ChkInputActionPerformed

    private void BtnSeek2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek2ActionPerformed
        akses.setform("DlgPemberianDiet");
        DlgCariBangsal bangsal=new DlgCariBangsal(null,false);
        bangsal.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgPemberianDiet")){
                    if(bangsal.getTable().getSelectedRow()!= -1){                          
                        NmBangsalCari.setText(bangsal.getTable().getValueAt(bangsal.getTable().getSelectedRow(),1).toString());
                        NmBangsalCari.requestFocus();                           
                    }                         
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
        bangsal.emptTeks();
        bangsal.isCek();
        bangsal.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        bangsal.setLocationRelativeTo(internalFrame1);
        bangsal.setVisible(true);
    }//GEN-LAST:event_BtnSeek2ActionPerformed

    private void BtnSeek2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeek2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnSeek2KeyPressed

    private void MnLabelDietActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnLabelDietActionPerformed
        if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
        }else{
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
            Valid.MyReportqry("rptLabelDiet.jasper","report","::[ Label Diet ]::",
                "select detail_beri_diet.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir, " +
                "concat(detail_beri_diet.kd_kamar,', ',bangsal.nm_bangsal) as kamar,detail_beri_diet.tanggal,detail_beri_diet.waktu,jam_diet_pasien.jam,diet.nama_diet " +
                "from detail_beri_diet inner join reg_periksa inner join pasien inner join diet inner join kamar inner join bangsal inner join jam_diet_pasien " +
                "on detail_beri_diet.no_rawat=reg_periksa.no_rawat " +
                "and detail_beri_diet.kd_kamar=kamar.kd_kamar "+
                "and kamar.kd_bangsal=bangsal.kd_bangsal "+
                "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "and detail_beri_diet.kd_diet=diet.kd_diet " +
                "and detail_beri_diet.waktu=jam_diet_pasien.waktu " +
                "where detail_beri_diet.tanggal='"+Valid.SetTgl(DTPTgl.getSelectedItem()+"")+"' and detail_beri_diet.waktu='"+WaktuDiet.getText()+"' "+
                "and detail_beri_diet.no_rawat='"+TNoRw.getText()+"' and diet.nama_diet='"+NmDiet.getText()+"'",param);
        }
    }//GEN-LAST:event_MnLabelDietActionPerformed

    private void MnLabelDiet1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnLabelDiet1ActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
            Valid.MyReportqry("rptLabelDiet.jasper","report","::[ Label Diet ]::",
                "select detail_beri_diet.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir, " +
                "concat(detail_beri_diet.kd_kamar,', ',bangsal.nm_bangsal) as kamar,detail_beri_diet.tanggal,detail_beri_diet.waktu,jam_diet_pasien.jam,diet.nama_diet " +
                "from detail_beri_diet inner join reg_periksa inner join pasien inner join diet inner join kamar inner join bangsal inner join jam_diet_pasien " +
                "on detail_beri_diet.no_rawat=reg_periksa.no_rawat " +
                "and detail_beri_diet.kd_kamar=kamar.kd_kamar "+
                "and kamar.kd_bangsal=bangsal.kd_bangsal "+
                "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "and detail_beri_diet.kd_diet=diet.kd_diet " +
                "and detail_beri_diet.waktu=jam_diet_pasien.waktu " +
                "where detail_beri_diet.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and detail_beri_diet.waktu like '%"+WaktuDiet2.getText().trim()+"%' and bangsal.nm_bangsal like '%"+NmBangsalCari.getText().trim()+"%' and detail_beri_diet.no_rawat like '%"+TCari.getText().trim()+"%' or "+
                "detail_beri_diet.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and detail_beri_diet.waktu like '%"+WaktuDiet2.getText().trim()+"%' and bangsal.nm_bangsal like '%"+NmBangsalCari.getText().trim()+"%' and reg_periksa.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                "detail_beri_diet.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and detail_beri_diet.waktu like '%"+WaktuDiet2.getText().trim()+"%' and bangsal.nm_bangsal like '%"+NmBangsalCari.getText().trim()+"%' and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' "+
                "order by bangsal.nm_bangsal,diet.nama_diet",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnLabelDiet1ActionPerformed

    private void WaktuDietKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_WaktuDietKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select jam from jam_diet_pasien where waktu=? ",JamDiet,WaktuDiet.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnJamActionPerformed(null);
        }else{
            Valid.pindah(evt,DTPTgl,KdDiet);
        }
    }//GEN-LAST:event_WaktuDietKeyPressed

    private void BtnJamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnJamActionPerformed
        akses.setform("DlgPemberianDiet");  
        pilih=1;
        DlgCariJamDiet jamdiet=new DlgCariJamDiet(null,false);
        jamdiet.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgPemberianDiet")){
                    if(jamdiet.getTable().getSelectedRow()!= -1){  
                        if(pilih==1){
                            WaktuDiet.setText(jamdiet.getTable().getValueAt(jamdiet.getTable().getSelectedRow(),0).toString());
                            JamDiet.setText(jamdiet.getTable().getValueAt(jamdiet.getTable().getSelectedRow(),1).toString());
                            BtnJam.requestFocus(); 
                        }else if(pilih==2){
                            WaktuDiet2.setText(jamdiet.getTable().getValueAt(jamdiet.getTable().getSelectedRow(),0).toString());
                            JamDiet2.setText(jamdiet.getTable().getValueAt(jamdiet.getTable().getSelectedRow(),1).toString());
                            BtnJam2.requestFocus(); 
                        }                             
                    }                         
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
        jamdiet.emptTeks();
        jamdiet.isCek();
        jamdiet.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        jamdiet.setLocationRelativeTo(internalFrame1);
        jamdiet.setVisible(true);
    }//GEN-LAST:event_BtnJamActionPerformed

    private void BtnJamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnJamKeyPressed
        Valid.pindah(evt,DTPTgl,BtnSeek1);
    }//GEN-LAST:event_BtnJamKeyPressed

    private void BtnJam2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnJam2ActionPerformed
        akses.setform("DlgPemberianDiet");  
        pilih=2;
        DlgCariJamDiet jamdiet=new DlgCariJamDiet(null,false);
        jamdiet.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgPemberianDiet")){
                    if(jamdiet.getTable().getSelectedRow()!= -1){  
                        if(pilih==1){
                            WaktuDiet.setText(jamdiet.getTable().getValueAt(jamdiet.getTable().getSelectedRow(),0).toString());
                            JamDiet.setText(jamdiet.getTable().getValueAt(jamdiet.getTable().getSelectedRow(),1).toString());
                            BtnJam.requestFocus(); 
                        }else if(pilih==2){
                            WaktuDiet2.setText(jamdiet.getTable().getValueAt(jamdiet.getTable().getSelectedRow(),0).toString());
                            JamDiet2.setText(jamdiet.getTable().getValueAt(jamdiet.getTable().getSelectedRow(),1).toString());
                            BtnJam2.requestFocus(); 
                        }                             
                    }                         
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
        jamdiet.emptTeks();
        jamdiet.isCek();
        jamdiet.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        jamdiet.setLocationRelativeTo(internalFrame1);
        jamdiet.setVisible(true);
    }//GEN-LAST:event_BtnJam2ActionPerformed

    private void BtnJam2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnJam2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnJam2KeyPressed

    private void WaktuDiet2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_WaktuDiet2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_WaktuDiet2KeyPressed

    private void tbRekapDietMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbRekapDietMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbRekapDietMouseClicked

    private void tbRekapDietKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbRekapDietKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbRekapDietKeyPressed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if(TabRawat.getSelectedIndex()==1){
            runBackground(() ->tampil2());
        }
    }//GEN-LAST:event_TabRawatMouseClicked

    private void MnSisaDietPasienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnSisaDietPasienActionPerformed
        if(tbDataDiet.getSelectedRow()!= -1){
            LaporanSisaDietPasien form=new LaporanSisaDietPasien(null,false);
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.emptTeks(); 
            form.isCek();
            form.setNoRm(TNoRw.getText(),TPasien.getText(),Kamar.getText(),Ruang.getText(),DTPCari1.getDate(),DTPCari2.getDate(),DTPTgl.getSelectedItem().toString(),WaktuDiet.getText(),JamDiet.getText());
            form.setVisible(true);
        }else{
            JOptionPane.showMessageDialog(null,"Silahkan pilih data diet pasien");
        }
    }//GEN-LAST:event_MnSisaDietPasienActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgPemberianDiet dialog = new DlgPemberianDiet(new javax.swing.JFrame(), true);
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
    private widget.Button BtnHapus;
    private widget.Button BtnJam;
    private widget.Button BtnJam2;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSeek1;
    private widget.Button BtnSeek2;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkInput;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.Tanggal DTPTgl;
    private widget.TextBox Diagnosa;
    private widget.PanelBiasa FormInput;
    private widget.TextBox JamDiet;
    private widget.TextBox JamDiet2;
    private widget.TextBox Kamar;
    private widget.TextBox KdDiet;
    private widget.Label LCount;
    private javax.swing.JMenuItem MnLabelDiet;
    private javax.swing.JMenuItem MnLabelDiet1;
    private javax.swing.JMenuItem MnSisaDietPasien;
    private widget.TextBox NmBangsalCari;
    private widget.TextBox NmDiet;
    private javax.swing.JPanel PanelInput;
    private widget.TextBox Ruang;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll1;
    private widget.TextBox TCari;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private javax.swing.JTabbedPane TabRawat;
    private widget.TextBox WaktuDiet;
    private widget.TextBox WaktuDiet2;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel12;
    private widget.Label jLabel19;
    private widget.Label jLabel21;
    private widget.Label jLabel4;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.panelisi panelGlass10;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.Table tbDataDiet;
    private widget.Table tbRekapDiet;
    // End of variables declaration//GEN-END:variables

    public void tampil() {   
        try{
            Valid.tabelKosong(tabMode);
            ps=koneksi.prepareStatement(
                "select detail_beri_diet.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,concat(detail_beri_diet.kd_kamar,', ',bangsal.nm_bangsal),"+
                "detail_beri_diet.tanggal,detail_beri_diet.waktu,jam_diet_pasien.jam,diet.nama_diet,detail_beri_diet.kd_kamar,detail_beri_diet.kd_diet " +
                "from detail_beri_diet inner join reg_periksa on detail_beri_diet.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join diet on detail_beri_diet.kd_diet=diet.kd_diet "+
                "inner join kamar on detail_beri_diet.kd_kamar=kamar.kd_kamar "+
                "inner join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal "+
                "inner join jam_diet_pasien on detail_beri_diet.waktu=jam_diet_pasien.waktu " +
                "where detail_beri_diet.tanggal between ? and ? and detail_beri_diet.waktu like ? and bangsal.nm_bangsal like ? "+
                (TCari.getText().trim().equals("")?"":"and (detail_beri_diet.no_rawat like ? or reg_periksa.no_rkm_medis like ? or pasien.nm_pasien like ?) ")+
                "order by bangsal.nm_bangsal,diet.nama_diet");
            try {
                ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(3,"%"+WaktuDiet2.getText().trim()+"%");
                ps.setString(4,"%"+NmBangsalCari.getText().trim()+"%");
                if(!TCari.getText().trim().equals("")){
                    ps.setString(5,"%"+TCari.getText().trim()+"%");
                    ps.setString(6,"%"+TCari.getText().trim()+"%");
                    ps.setString(7,"%"+TCari.getText().trim()+"%");
                }
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new Object[]{
                        rs.getString(1),rs.getString(2)+" "+rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),
                        Sequel.cariIsi("select kamar_inap.diagnosa_awal from kamar_inap where kamar_inap.no_rawat=? order by kamar_inap.tgl_masuk desc",rs.getString(1)),
                        rs.getString("kd_kamar"),rs.getString("kd_diet")
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
            LCount.setText(""+tabMode.getRowCount());
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }
    
    public void tampil2() {   
        try{
            Valid.tabelKosong(tabMode2);  
            ps2=koneksi.prepareStatement(
                "select diet.nama_diet, count(diet.nama_diet) as jumlah " +
                "from detail_beri_diet inner join reg_periksa on detail_beri_diet.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join diet on detail_beri_diet.kd_diet=diet.kd_diet "+
                "inner join kamar on detail_beri_diet.kd_kamar=kamar.kd_kamar "+
                "inner join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal " +
                "where detail_beri_diet.tanggal between ? and ? and detail_beri_diet.waktu like ? and bangsal.nm_bangsal like ? "+
                (TCari.getText().trim().equals("")?"":"and (detail_beri_diet.no_rawat like ? or reg_periksa.no_rkm_medis like ? or pasien.nm_pasien like ?) ")+
                "group by diet.nama_diet order by bangsal.nm_bangsal,diet.nama_diet");
            try {
                ps2.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps2.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps2.setString(3,"%"+WaktuDiet2.getText().trim()+"%");
                ps2.setString(4,"%"+NmBangsalCari.getText().trim()+"%");
                if(!TCari.getText().trim().equals("")){
                    ps2.setString(5,"%"+TCari.getText().trim()+"%");
                    ps2.setString(6,"%"+TCari.getText().trim()+"%");
                    ps2.setString(7,"%"+TCari.getText().trim()+"%");
                }
                rs=ps2.executeQuery();
                i=1;
                while(rs.next()){
                     tabMode2.addRow(new Object[]{i+"",rs.getString(1),rs.getString(2)});i++;                   
                }
            } catch (Exception e) {
                System.out.println("Notif : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps2!=null){
                    ps2.close();
                }
            }
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }


    public void emptTeks() {
        KdDiet.setText("");
        NmDiet.setText("");
        WaktuDiet.setText("");
        JamDiet.setText("");
        DTPTgl.setDate(new Date());
        DTPTgl.requestFocus();
    }

    private void getData() {
        if(tbDataDiet.getSelectedRow()!= -1){
            TNoRw.setText(tbDataDiet.getValueAt(tbDataDiet.getSelectedRow(),0).toString()); 
            TPasien.setText(tbDataDiet.getValueAt(tbDataDiet.getSelectedRow(),1).toString());    
            Ruang.setText(tbDataDiet.getValueAt(tbDataDiet.getSelectedRow(),2).toString());          
            WaktuDiet.setText(tbDataDiet.getValueAt(tbDataDiet.getSelectedRow(),4).toString());
            JamDiet.setText(tbDataDiet.getValueAt(tbDataDiet.getSelectedRow(),5).toString());
            NmDiet.setText(tbDataDiet.getValueAt(tbDataDiet.getSelectedRow(),6).toString());
            Kamar.setText(tbDataDiet.getValueAt(tbDataDiet.getSelectedRow(),8).toString());
            KdDiet.setText(tbDataDiet.getValueAt(tbDataDiet.getSelectedRow(),9).toString());
            Valid.SetTgl(DTPTgl,tbDataDiet.getValueAt(tbDataDiet.getSelectedRow(),3).toString());
        }
    }
    
    private void isRawat() {
         Sequel.cariIsi("select pasien.nm_pasien from reg_periksa inner join pasien on pasien.no_rkm_medis=reg_periksa.no_rkm_medis where reg_periksa.no_rawat=? ",TPasien,TNoRw.getText());
         Sequel.cariIsi("select kamar_inap.kd_kamar from kamar_inap where kamar_inap.no_rawat=? order by kamar_inap.tgl_masuk desc limit 1",Kamar,TNoRw.getText());
    }

    public void setNoRm(String norwt,String pasien,String kamar,String bangsal,String diagnosa,Date tgl1,Date tgl2) {
        TNoRw.setText(norwt);
        TPasien.setText(pasien);
        Kamar.setText(kamar);
        Ruang.setText(bangsal);
        Diagnosa.setText(diagnosa);
        TCari.setText(norwt);
        DTPCari1.setDate(tgl1);
        DTPCari2.setDate(tgl2);
        ChkInput.setSelected(true);
        isForm();
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,99));
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
        BtnSimpan.setEnabled(akses.getdiet_pasien());
        BtnHapus.setEnabled(akses.getdiet_pasien());
        BtnPrint.setEnabled(akses.getdiet_pasien());
        MnSisaDietPasien.setEnabled(akses.getsisa_diet_pasien());
    }
    
    private void runBackground(Runnable task) {
        if (ceksukses) return;
        ceksukses = true;

        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        executor.submit(() -> {
            try {
                task.run();
            } finally {
                ceksukses = false;
                SwingUtilities.invokeLater(() -> {
                    this.setCursor(Cursor.getDefaultCursor());
                });
            }
        });
    }
}

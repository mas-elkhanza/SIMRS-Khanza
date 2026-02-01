/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgRujuk.java
 *
 * Created on 31 Mei 10, 20:19:56
 */

package keuangan;

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
import java.util.concurrent.RejectedExecutionException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariPegawai;


/**
 *
 * @author perpustakaan
 */
public final class KeuanganPengajuanBiaya extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0,pilihan=0;
    private double total=0;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private volatile boolean ceksukses = false;
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public KeuanganPengajuanBiaya(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(628,674);

        tabMode=new DefaultTableModel(null,new Object[]{
                "No.Pengajuan","Tanggal","NIP","Diajukan Oleh","Bidang","Departemen","Urgensi","Uraian","Tujuan",
                "Target Sasaran","Lokasi","Jml","Harga", "Total", "Keterangan", "NIP P.J.","P.J. Terkait", "Status"
            }){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
             Class[] types = new Class[] {
                  java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,
                  java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,
                  java.lang.String.class,java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,java.lang.String.class,
                  java.lang.String.class,java.lang.String.class,java.lang.String.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 18; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(85);
            }else if(i==1){
                column.setPreferredWidth(65);
            }else if(i==2){
                column.setPreferredWidth(85);
            }else if(i==3){
                column.setPreferredWidth(160);
            }else if(i==4){
                column.setPreferredWidth(70);
            }else if(i==5){
                column.setPreferredWidth(70);
            }else if(i==6){
                column.setPreferredWidth(70);
            }else if(i==7){
                column.setPreferredWidth(180);
            }else if(i==8){
                column.setPreferredWidth(180);
            }else if(i==9){
                column.setPreferredWidth(150);
            }else if(i==10){
                column.setPreferredWidth(150);
            }else if(i==11){
                column.setPreferredWidth(35);
            }else if(i==12){
                column.setPreferredWidth(80);
            }else if(i==13){
                column.setPreferredWidth(90);
            }else if(i==14){
                column.setPreferredWidth(140);
            }else if(i==15){
                column.setPreferredWidth(85);
            }else if(i==16){
                column.setPreferredWidth(160);
            }else if(i==17){
                column.setPreferredWidth(95);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());

        Uraian.setDocument(new batasInput((int)200).getKata(Uraian));
        Tujuan.setDocument(new batasInput((int)200).getKata(Tujuan));
        Lokasi.setDocument(new batasInput((int)70).getKata(Lokasi));
        TargetSasaran.setDocument(new batasInput((int)70).getKata(TargetSasaran));
        Keterangan.setDocument(new batasInput((int)70).getKata(Keterangan));
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        Jumlah.setDocument(new batasInput((byte)4).getOnlyAngka(Jumlah));
        Harga.setDocument(new batasInput((byte)20).getOnlyAngka(Harga));
        
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
        
        Harga.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                isHitung();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                isHitung();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                isHitung();
            }
        });
        
        Jumlah.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                isHitung();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                isHitung();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                isHitung();
            }
        });
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

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
        jLabel18 = new widget.Label();
        LCount1 = new widget.Label();
        PanelInput = new javax.swing.JPanel();
        FormInput = new widget.PanelBiasa();
        jLabel8 = new widget.Label();
        Tanggal = new widget.Tanggal();
        jLabel5 = new widget.Label();
        KdPetugas = new widget.TextBox();
        btnPetugas = new widget.Button();
        NmPetugas = new widget.TextBox();
        scrollPane1 = new widget.ScrollPane();
        Uraian = new widget.TextArea();
        jLabel9 = new widget.Label();
        scrollPane2 = new widget.ScrollPane();
        Tujuan = new widget.TextArea();
        jLabel10 = new widget.Label();
        jLabel3 = new widget.Label();
        NoPengajuan = new widget.TextBox();
        jLabel20 = new widget.Label();
        Urgensi = new widget.ComboBox();
        jLabel11 = new widget.Label();
        Jumlah = new widget.TextBox();
        Harga = new widget.TextBox();
        jLabel12 = new widget.Label();
        jLabel13 = new widget.Label();
        Total = new widget.TextBox();
        jLabel14 = new widget.Label();
        Keterangan = new widget.TextBox();
        jLabel15 = new widget.Label();
        Bidang = new widget.TextBox();
        jLabel16 = new widget.Label();
        Departemen = new widget.TextBox();
        jLabel17 = new widget.Label();
        KdPetugasPJ = new widget.TextBox();
        NmPetugasPJ = new widget.TextBox();
        btnPetugasPJ = new widget.Button();
        jLabel22 = new widget.Label();
        Lokasi = new widget.TextBox();
        jLabel23 = new widget.Label();
        TargetSasaran = new widget.TextBox();
        ChkInput = new widget.CekBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Pengajuan Biaya/Anggaran ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(70, 23));
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
        jLabel19.setPreferredSize(new java.awt.Dimension(55, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "19-07-2023" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "19-07-2023" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(DTPCari2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(65, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(180, 23));
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

        jLabel18.setText("Total :");
        jLabel18.setName("jLabel18"); // NOI18N
        jLabel18.setPreferredSize(new java.awt.Dimension(45, 23));
        panelGlass9.add(jLabel18);

        LCount1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount1.setText("0");
        LCount1.setName("LCount1"); // NOI18N
        LCount1.setPreferredSize(new java.awt.Dimension(110, 23));
        panelGlass9.add(LCount1);

        jPanel3.add(panelGlass9, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setPreferredSize(new java.awt.Dimension(72, 205));
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(70, 165));
        FormInput.setLayout(null);

        jLabel8.setText("Tanggal :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(233, 10, 55, 23);

        Tanggal.setForeground(new java.awt.Color(50, 70, 50));
        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "19-07-2023" }));
        Tanggal.setDisplayFormat("dd-MM-yyyy");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.setOpaque(false);
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeyPressed(evt);
            }
        });
        FormInput.add(Tanggal);
        Tanggal.setBounds(292, 10, 90, 23);

        jLabel5.setText("Uraian :");
        jLabel5.setName("jLabel5"); // NOI18N
        FormInput.add(jLabel5);
        jLabel5.setBounds(0, 100, 95, 23);

        KdPetugas.setEditable(false);
        KdPetugas.setHighlighter(null);
        KdPetugas.setName("KdPetugas"); // NOI18N
        KdPetugas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdPetugasKeyPressed(evt);
            }
        });
        FormInput.add(KdPetugas);
        KdPetugas.setBounds(99, 40, 110, 23);

        btnPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPetugas.setMnemonic('2');
        btnPetugas.setToolTipText("Alt+2");
        btnPetugas.setName("btnPetugas"); // NOI18N
        btnPetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPetugasActionPerformed(evt);
            }
        });
        btnPetugas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnPetugasKeyPressed(evt);
            }
        });
        FormInput.add(btnPetugas);
        btnPetugas.setBounds(400, 40, 28, 23);

        NmPetugas.setEditable(false);
        NmPetugas.setHighlighter(null);
        NmPetugas.setName("NmPetugas"); // NOI18N
        NmPetugas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NmPetugasKeyPressed(evt);
            }
        });
        FormInput.add(NmPetugas);
        NmPetugas.setBounds(212, 40, 185, 23);

        scrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane1.setName("scrollPane1"); // NOI18N

        Uraian.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Uraian.setColumns(20);
        Uraian.setRows(5);
        Uraian.setName("Uraian"); // NOI18N
        Uraian.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                UraianKeyPressed(evt);
            }
        });
        scrollPane1.setViewportView(Uraian);

        FormInput.add(scrollPane1);
        scrollPane1.setBounds(99, 100, 306, 42);

        jLabel9.setText("Diajukan Oleh :");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(0, 40, 95, 23);

        scrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane2.setName("scrollPane2"); // NOI18N

        Tujuan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Tujuan.setColumns(20);
        Tujuan.setRows(5);
        Tujuan.setName("Tujuan"); // NOI18N
        Tujuan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TujuanKeyPressed(evt);
            }
        });
        scrollPane2.setViewportView(Tujuan);

        FormInput.add(scrollPane2);
        scrollPane2.setBounds(464, 100, 306, 42);

        jLabel10.setText("Tujuan :");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(410, 100, 50, 23);

        jLabel3.setText("No.Pengajuan :");
        jLabel3.setName("jLabel3"); // NOI18N
        FormInput.add(jLabel3);
        jLabel3.setBounds(0, 10, 95, 23);

        NoPengajuan.setHighlighter(null);
        NoPengajuan.setName("NoPengajuan"); // NOI18N
        FormInput.add(NoPengajuan);
        NoPengajuan.setBounds(99, 10, 130, 23);

        jLabel20.setText("Urgensi :");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(0, 70, 95, 23);

        Urgensi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Cito", "Emergensi", "Biasa" }));
        Urgensi.setName("Urgensi"); // NOI18N
        Urgensi.setPreferredSize(new java.awt.Dimension(55, 28));
        Urgensi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                UrgensiKeyPressed(evt);
            }
        });
        FormInput.add(Urgensi);
        Urgensi.setBounds(99, 70, 115, 23);

        jLabel11.setText("Volume :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(222, 70, 52, 23);

        Jumlah.setHighlighter(null);
        Jumlah.setName("Jumlah"); // NOI18N
        Jumlah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JumlahKeyPressed(evt);
            }
        });
        FormInput.add(Jumlah);
        Jumlah.setBounds(278, 70, 55, 23);

        Harga.setHighlighter(null);
        Harga.setName("Harga"); // NOI18N
        Harga.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HargaKeyPressed(evt);
            }
        });
        FormInput.add(Harga);
        Harga.setBounds(388, 70, 120, 23);

        jLabel12.setText("Harga :");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(334, 70, 50, 23);

        jLabel13.setText("Total Pengajuan :");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(516, 70, 90, 23);

        Total.setEditable(false);
        Total.setHighlighter(null);
        Total.setName("Total"); // NOI18N
        FormInput.add(Total);
        Total.setBounds(610, 70, 160, 23);

        jLabel14.setText("Keterangan :");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(510, 150, 80, 23);

        Keterangan.setHighlighter(null);
        Keterangan.setName("Keterangan"); // NOI18N
        Keterangan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKeyPressed(evt);
            }
        });
        FormInput.add(Keterangan);
        Keterangan.setBounds(595, 150, 175, 23);

        jLabel15.setText("Bidang :");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(430, 40, 50, 23);

        Bidang.setEditable(false);
        Bidang.setHighlighter(null);
        Bidang.setName("Bidang"); // NOI18N
        FormInput.add(Bidang);
        Bidang.setBounds(484, 40, 100, 23);

        jLabel16.setText("Departemen :");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput.add(jLabel16);
        jLabel16.setBounds(586, 40, 80, 23);

        Departemen.setEditable(false);
        Departemen.setHighlighter(null);
        Departemen.setName("Departemen"); // NOI18N
        FormInput.add(Departemen);
        Departemen.setBounds(670, 40, 100, 23);

        jLabel17.setText("P.J.Terkait :");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(385, 10, 70, 23);

        KdPetugasPJ.setEditable(false);
        KdPetugasPJ.setHighlighter(null);
        KdPetugasPJ.setName("KdPetugasPJ"); // NOI18N
        FormInput.add(KdPetugasPJ);
        KdPetugasPJ.setBounds(459, 10, 110, 23);

        NmPetugasPJ.setEditable(false);
        NmPetugasPJ.setHighlighter(null);
        NmPetugasPJ.setName("NmPetugasPJ"); // NOI18N
        FormInput.add(NmPetugasPJ);
        NmPetugasPJ.setBounds(572, 10, 167, 23);

        btnPetugasPJ.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPetugasPJ.setMnemonic('2');
        btnPetugasPJ.setToolTipText("Alt+2");
        btnPetugasPJ.setName("btnPetugasPJ"); // NOI18N
        btnPetugasPJ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPetugasPJActionPerformed(evt);
            }
        });
        btnPetugasPJ.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnPetugasPJKeyPressed(evt);
            }
        });
        FormInput.add(btnPetugasPJ);
        btnPetugasPJ.setBounds(742, 10, 28, 23);

        jLabel22.setText("Lokasi :");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(281, 150, 50, 23);

        Lokasi.setHighlighter(null);
        Lokasi.setName("Lokasi"); // NOI18N
        Lokasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LokasiKeyPressed(evt);
            }
        });
        FormInput.add(Lokasi);
        Lokasi.setBounds(335, 150, 175, 23);

        jLabel23.setText("Target Sasaran :");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(0, 150, 95, 23);

        TargetSasaran.setHighlighter(null);
        TargetSasaran.setName("TargetSasaran"); // NOI18N
        TargetSasaran.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TargetSasaranKeyPressed(evt);
            }
        });
        FormInput.add(TargetSasaran);
        TargetSasaran.setBounds(99, 150, 175, 23);

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

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(Tujuan.getText().trim().equals("")){
            Valid.textKosong(Tujuan,"Tujuan");
        }else if(Uraian.getText().trim().equals("")){
            Valid.textKosong(Uraian,"Uraian");
        }else if(TargetSasaran.getText().trim().equals("")){
            Valid.textKosong(TargetSasaran,"Target Sasaran");
        }else if(Lokasi.getText().trim().equals("")){
            Valid.textKosong(Lokasi,"Lokasi");
        }else if(NmPetugas.getText().trim().equals("")){
            Valid.textKosong(KdPetugas,"Yang Mengajukan");
        }else if(NoPengajuan.getText().trim().equals("")){
            Valid.textKosong(NoPengajuan,"No.Pengajuan");
        }else if(NmPetugasPJ.getText().trim().equals("")){
            Valid.textKosong(KdPetugasPJ,"P.J. Terkait Pengajuan");
        }else if(Jumlah.getText().trim().equals("")||Jumlah.getText().trim().equals("0")){
            Valid.textKosong(Jumlah,"Jumlah");
        }else if(Harga.getText().trim().equals("")||Harga.getText().trim().equals("0")){
            Valid.textKosong(Harga,"Harga");
        }else if(Total.getText().trim().equals("")||Total.getText().trim().equals("0")){
            Valid.textKosong(Harga,"Total");
        }else if(Keterangan.getText().trim().equals("")){
            Valid.textKosong(Keterangan,"Keterangan");
        }else{
            if(Sequel.menyimpantf("pengajuan_biaya","?,?,?,?,?,?,?,?,?,?,?,?,?,?","Data",14,new String[]{
                    NoPengajuan.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+""),KdPetugas.getText(),Urgensi.getSelectedItem().toString(), 
                    Uraian.getText(),Tujuan.getText(),TargetSasaran.getText(),Lokasi.getText(),Jumlah.getText(),Harga.getText(),
                    Double.toString(Double.parseDouble(Harga.getText())*Double.parseDouble(Jumlah.getText())),Keterangan.getText(),
                    KdPetugasPJ.getText(),"Proses Pengajuan"
                })==true){
                    tabMode.addRow(new Object[]{
                        NoPengajuan.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+""),KdPetugas.getText(),NmPetugas.getText(),Bidang.getText(),Departemen.getText(),
                        Urgensi.getSelectedItem().toString(),Uraian.getText(),Tujuan.getText(),TargetSasaran.getText(),Lokasi.getText(),Double.parseDouble(Jumlah.getText()),
                        Double.parseDouble(Harga.getText()),(Double.parseDouble(Harga.getText())*Double.parseDouble(Jumlah.getText())),Keterangan.getText(),KdPetugasPJ.getText(),
                        NmPetugasPJ.getText(),"Proses Pengajuan"
                    });
                    emptTeks();
                    hitung();
            }
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,Tujuan,BtnBatal);
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
        if(tbObat.getSelectedRow()> -1){
            if(akses.getkode().equals("Admin Utama")){
                hapus();
            }else {
                if(akses.getkode().equals(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString())||akses.getkode().equals(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString())){
                    hapus();
                }else{
                    JOptionPane.showMessageDialog(null,"Harus oleh yang mengajukan/P.J.Terkait sesuai user login..!!");
                }
            }
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
        if(Tujuan.getText().trim().equals("")){
            Valid.textKosong(Tujuan,"Tujuan");
        }else if(Uraian.getText().trim().equals("")){
            Valid.textKosong(Uraian,"Uraian");
        }else if(TargetSasaran.getText().trim().equals("")){
            Valid.textKosong(TargetSasaran,"Target Sasaran");
        }else if(Lokasi.getText().trim().equals("")){
            Valid.textKosong(Lokasi,"Lokasi");
        }else if(NmPetugas.getText().trim().equals("")){
            Valid.textKosong(KdPetugas,"Yang Mengajukan");
        }else if(NoPengajuan.getText().trim().equals("")){
            Valid.textKosong(NoPengajuan,"No.Pengajuan");
        }else if(NmPetugasPJ.getText().trim().equals("")){
            Valid.textKosong(KdPetugasPJ,"P.J. Terkait Pengajuan");
        }else if(Jumlah.getText().trim().equals("")||Jumlah.getText().trim().equals("0")){
            Valid.textKosong(Jumlah,"Jumlah");
        }else if(Harga.getText().trim().equals("")||Harga.getText().trim().equals("0")){
            Valid.textKosong(Harga,"Harga");
        }else if(Total.getText().trim().equals("")||Total.getText().trim().equals("0")){
            Valid.textKosong(Harga,"Total");
        }else if(Keterangan.getText().trim().equals("")){
            Valid.textKosong(Keterangan,"Keterangan");
        }else{
            if(tbObat.getSelectedRow()> -1){
                if(akses.getkode().equals("Admin Utama")){
                    ganti();
                }else {
                    if(akses.getkode().equals(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString())||akses.getkode().equals(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString())){
                        ganti();
                    }else{
                        JOptionPane.showMessageDialog(null,"Harus oleh yang mengajukan/P.J.Terkait sesuai user login..!!");
                    }
                }
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
            dispose();
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
            Valid.MyReportqry("rptPengajuanBiaya.jasper","report","::[ Data Pengajuan Biaya ]::",
                   "select pengajuan_biaya.no_pengajuan,pengajuan_biaya.tanggal,pengajuan_biaya.nik,peg1.nama as namapengaju,"+
                   "peg1.bidang,peg1.departemen,pengajuan_biaya.urgensi,pengajuan_biaya.uraian_latar_belakang,pengajuan_biaya.tujuan_pengajuan,"+
                   "pengajuan_biaya.target_sasaran,pengajuan_biaya.lokasi_kegiatan,pengajuan_biaya.jumlah,pengajuan_biaya.harga,"+
                   "pengajuan_biaya.total,pengajuan_biaya.keterangan,pengajuan_biaya.nik_pj,peg2.nama as namapj,pengajuan_biaya.status "+
                   "from pengajuan_biaya inner join pegawai as peg1 inner join pegawai as peg2 on pengajuan_biaya.nik=peg1.nik "+
                   "and pengajuan_biaya.nik_pj=peg2.nik where pengajuan_biaya.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' "+
                   (TCari.getText().trim().equals("")?"":"and (pengajuan_biaya.no_pengajuan like '%"+TCari.getText().trim()+"%' or pengajuan_biaya.nik like '%"+TCari.getText().trim()+"%' or peg1.nama like '%"+TCari.getText().trim()+"%' or "+
                   "peg1.bidang like '%"+TCari.getText().trim()+"%' or peg1.departemen like '%"+TCari.getText().trim()+"%' or pengajuan_biaya.urgensi like '%"+TCari.getText().trim()+"%' or pengajuan_biaya.uraian_latar_belakang like '%"+TCari.getText().trim()+"%' or "+
                   "pengajuan_biaya.tujuan_pengajuan like '%"+TCari.getText().trim()+"%' or pengajuan_biaya.lokasi_kegiatan like '%"+TCari.getText().trim()+"%' or pengajuan_biaya.keterangan like '%"+TCari.getText().trim()+"%' or "+
                   "pengajuan_biaya.nik_pj like '%"+TCari.getText().trim()+"%' or peg2.nama like '%"+TCari.getText().trim()+"%' or pengajuan_biaya.status like '%"+TCari.getText().trim()+"%')")+" order by pengajuan_biaya.tanggal",param);
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
            Valid.pindah(evt, BtnCari,BtnKeluar);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void TanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKeyPressed
        Valid.pindah(evt,TCari,btnPetugasPJ);
}//GEN-LAST:event_TanggalKeyPressed

    private void tbObatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbObatMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbObatMouseClicked

private void KdPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdPetugasKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            NmPetugas.setText(Sequel.CariPetugas(KdPetugas.getText()));
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnPetugasActionPerformed(null);
        }else{            
            Valid.pindah(evt,TCari,Tanggal);
        }
}//GEN-LAST:event_KdPetugasKeyPressed

private void btnPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPetugasActionPerformed
        DlgCariPegawai petugas=new DlgCariPegawai(null,false);
        petugas.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(petugas.getTable().getSelectedRow()!= -1){
                    KdPetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                    NmPetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                    Bidang.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),6).toString());
                    Departemen.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),5).toString());
                    btnPetugas.requestFocus();
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
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setVisible(true);
}//GEN-LAST:event_btnPetugasActionPerformed

private void NmPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NmPetugasKeyPressed
        //Valid.pindah(evt,TKd,TSpek);
}//GEN-LAST:event_NmPetugasKeyPressed

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

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        emptTeks();
    }//GEN-LAST:event_formWindowOpened

    private void btnPetugasPJActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPetugasPJActionPerformed
        DlgCariPegawai petugas=new DlgCariPegawai(null,false);
        petugas.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(petugas.getTable().getSelectedRow()!= -1){
                    KdPetugasPJ.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                    NmPetugasPJ.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                    btnPetugasPJ.requestFocus();
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
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setVisible(true);
    }//GEN-LAST:event_btnPetugasPJActionPerformed

    private void btnPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPetugasKeyPressed
        Valid.pindah(evt,btnPetugasPJ,Urgensi);
    }//GEN-LAST:event_btnPetugasKeyPressed

    private void UrgensiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_UrgensiKeyPressed
        Valid.pindah(evt,btnPetugasPJ,Jumlah);
    }//GEN-LAST:event_UrgensiKeyPressed

    private void JumlahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JumlahKeyPressed
        Valid.pindah(evt,Urgensi,Harga);
    }//GEN-LAST:event_JumlahKeyPressed

    private void HargaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HargaKeyPressed
        Valid.pindah(evt,Jumlah,Uraian);
    }//GEN-LAST:event_HargaKeyPressed

    private void UraianKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_UraianKeyPressed
        Valid.pindah2(evt,Harga,Tujuan);
    }//GEN-LAST:event_UraianKeyPressed

    private void TujuanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TujuanKeyPressed
        Valid.pindah2(evt,Uraian,TargetSasaran);
    }//GEN-LAST:event_TujuanKeyPressed

    private void btnPetugasPJKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPetugasPJKeyPressed
        Valid.pindah(evt,Tanggal,Urgensi);
    }//GEN-LAST:event_btnPetugasPJKeyPressed

    private void KeteranganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKeyPressed
        Valid.pindah(evt,Lokasi,BtnSimpan);
    }//GEN-LAST:event_KeteranganKeyPressed

    private void LokasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LokasiKeyPressed
        Valid.pindah(evt,TargetSasaran,Keterangan);
    }//GEN-LAST:event_LokasiKeyPressed

    private void TargetSasaranKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TargetSasaranKeyPressed
        Valid.pindah(evt,Tujuan,Lokasi);
    }//GEN-LAST:event_TargetSasaranKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            KeuanganPengajuanBiaya dialog = new KeuanganPengajuanBiaya(new javax.swing.JFrame(), true);
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
    private widget.TextBox Bidang;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkInput;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.TextBox Departemen;
    private widget.PanelBiasa FormInput;
    private widget.TextBox Harga;
    private widget.TextBox Jumlah;
    private widget.TextBox KdPetugas;
    private widget.TextBox KdPetugasPJ;
    private widget.TextBox Keterangan;
    private widget.Label LCount;
    private widget.Label LCount1;
    private widget.TextBox Lokasi;
    private widget.TextBox NmPetugas;
    private widget.TextBox NmPetugasPJ;
    private widget.TextBox NoPengajuan;
    private javax.swing.JPanel PanelInput;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.Tanggal Tanggal;
    private widget.TextBox TargetSasaran;
    private widget.TextBox Total;
    private widget.TextArea Tujuan;
    private widget.TextArea Uraian;
    private widget.ComboBox Urgensi;
    private widget.Button btnPetugas;
    private widget.Button btnPetugasPJ;
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
    private widget.Label jLabel20;
    private widget.Label jLabel21;
    private widget.Label jLabel22;
    private widget.Label jLabel23;
    private widget.Label jLabel3;
    private widget.Label jLabel5;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private javax.swing.JPanel jPanel3;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollPane1;
    private widget.ScrollPane scrollPane2;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            ps=koneksi.prepareStatement(
                   "select pengajuan_biaya.no_pengajuan,pengajuan_biaya.tanggal,pengajuan_biaya.nik,peg1.nama as namapengaju,"+
                   "peg1.bidang,peg1.departemen,pengajuan_biaya.urgensi,pengajuan_biaya.uraian_latar_belakang,pengajuan_biaya.tujuan_pengajuan,"+
                   "pengajuan_biaya.target_sasaran,pengajuan_biaya.lokasi_kegiatan,pengajuan_biaya.jumlah,pengajuan_biaya.harga,"+
                   "pengajuan_biaya.total,pengajuan_biaya.keterangan,pengajuan_biaya.nik_pj,peg2.nama as namapj,pengajuan_biaya.status "+
                   "from pengajuan_biaya inner join pegawai as peg1 on pengajuan_biaya.nik=peg1.nik "+
                   "inner join pegawai as peg2 on pengajuan_biaya.nik_pj=peg2.nik where pengajuan_biaya.tanggal between ? and ? "+
                   (TCari.getText().trim().equals("")?"":"and (pengajuan_biaya.no_pengajuan like ? or pengajuan_biaya.nik like ? or peg1.nama like ? or "+
                   "peg1.bidang like ? or peg1.departemen like ? or pengajuan_biaya.urgensi like ? or pengajuan_biaya.uraian_latar_belakang like ? or "+
                   "pengajuan_biaya.tujuan_pengajuan like ? or pengajuan_biaya.lokasi_kegiatan like ? or pengajuan_biaya.keterangan like ? or "+
                   "pengajuan_biaya.nik_pj like ? or peg2.nama like ? or pengajuan_biaya.status like ?)")+" order by pengajuan_biaya.tanggal");
            try {
                ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                if(!TCari.getText().trim().equals("")){
                    ps.setString(3,"%"+TCari.getText().trim()+"%");
                    ps.setString(4,"%"+TCari.getText().trim()+"%");
                    ps.setString(5,"%"+TCari.getText().trim()+"%");
                    ps.setString(6,"%"+TCari.getText().trim()+"%");
                    ps.setString(7,"%"+TCari.getText().trim()+"%");
                    ps.setString(8,"%"+TCari.getText().trim()+"%");
                    ps.setString(9,"%"+TCari.getText().trim()+"%");
                    ps.setString(10,"%"+TCari.getText().trim()+"%");
                    ps.setString(11,"%"+TCari.getText().trim()+"%");
                    ps.setString(12,"%"+TCari.getText().trim()+"%");
                    ps.setString(13,"%"+TCari.getText().trim()+"%");
                    ps.setString(14,"%"+TCari.getText().trim()+"%");
                    ps.setString(15,"%"+TCari.getText().trim()+"%");
                }   
                rs=ps.executeQuery();
                i=1;
                while(rs.next()){
                    tabMode.addRow(new Object[]{
                        rs.getString("no_pengajuan"),rs.getString("tanggal"),rs.getString("nik"),rs.getString("namapengaju"),rs.getString("bidang"),
                        rs.getString("departemen"),rs.getString("urgensi"),rs.getString("uraian_latar_belakang"),rs.getString("tujuan_pengajuan"),rs.getString("target_sasaran"),
                        rs.getString("lokasi_kegiatan"),rs.getDouble("jumlah"),rs.getDouble("harga"),rs.getDouble("total"),rs.getString("keterangan"),
                        rs.getString("nik_pj"),rs.getString("namapj"),rs.getString("status")
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
            hitung();
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }

    private void emptTeks() {
        Tanggal.setDate(new Date());
        Tujuan.setText("");
        Uraian.setText("");
        TargetSasaran.setText("");
        Lokasi.setText("");
        Jumlah.setText("0");
        Harga.setText("0");
        Total.setText("0");
        Keterangan.setText("");
        KdPetugasPJ.setText("");
        NmPetugasPJ.setText("");
        autoNomor();
        Urgensi.requestFocus();
    }


    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            NoPengajuan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
            Valid.SetTgl(Tanggal,tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            KdPetugas.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            NmPetugas.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            Bidang.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString());
            Departemen.setText(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
            Urgensi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString());
            Uraian.setText(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());
            Tujuan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            TargetSasaran.setText(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            Lokasi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            Jumlah.setText(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            Harga.setText(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            Total.setText(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            Keterangan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            KdPetugasPJ.setText(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
            NmPetugasPJ.setText(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
        }
    }

    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,205));
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
        BtnSimpan.setEnabled(akses.getpengajuan_biaya());
        BtnHapus.setEnabled(akses.getpengajuan_biaya());
        BtnEdit.setEnabled(akses.getpengajuan_biaya());
        if(akses.getjml2()>=1){
            KdPetugas.setEditable(false);
            btnPetugas.setEnabled(false);
            KdPetugas.setText(akses.getkode());
            NmPetugas.setText(Sequel.CariPetugas(KdPetugas.getText()));
        }  
    }
    
    private void autoNomor() {
        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(pengajuan_biaya.no_pengajuan,3),signed)),0) from pengajuan_biaya where pengajuan_biaya.tanggal='"+Valid.SetTgl(Tanggal.getSelectedItem()+"")+"' ",
                "PK"+Tanggal.getSelectedItem().toString().substring(6,10)+Tanggal.getSelectedItem().toString().substring(3,5)+Tanggal.getSelectedItem().toString().substring(0,2),3,NoPengajuan); 
    }
    
    private void isHitung(){
        if((!Harga.getText().equals(""))&&(!Jumlah.getText().equals(""))){
            Total.setText(Valid.SetAngka(Double.parseDouble(Harga.getText())*Double.parseDouble(Jumlah.getText())));
        }
    }

    private void ganti() {
        if(Sequel.mengedittf("pengajuan_biaya","no_pengajuan=?","no_pengajuan=?,tanggal=?,nik=?,urgensi=?,uraian_latar_belakang=?,tujuan_pengajuan=?,"+
                "target_sasaran=?,lokasi_kegiatan=?,jumlah=?,harga=?,total=?,keterangan=?,nik_pj=?",14,new String[]{
                NoPengajuan.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+""),KdPetugas.getText(),Urgensi.getSelectedItem().toString(), 
                Uraian.getText(),Tujuan.getText(),TargetSasaran.getText(),Lokasi.getText(),Jumlah.getText(),Harga.getText(),
                Double.toString(Double.parseDouble(Harga.getText())*Double.parseDouble(Jumlah.getText())),Keterangan.getText(),KdPetugasPJ.getText(),
                tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
            })==true){
                tbObat.setValueAt(NoPengajuan.getText(),tbObat.getSelectedRow(),0);
                tbObat.setValueAt(Valid.SetTgl(Tanggal.getSelectedItem()+""),tbObat.getSelectedRow(),1);
                tbObat.setValueAt(KdPetugas.getText(),tbObat.getSelectedRow(),2);
                tbObat.setValueAt(NmPetugas.getText(),tbObat.getSelectedRow(),3);
                tbObat.setValueAt(Bidang.getText(),tbObat.getSelectedRow(),4);
                tbObat.setValueAt(Departemen.getText(),tbObat.getSelectedRow(),5);
                tbObat.setValueAt(Urgensi.getSelectedItem().toString(),tbObat.getSelectedRow(),6);
                tbObat.setValueAt(Uraian.getText(),tbObat.getSelectedRow(),7);
                tbObat.setValueAt(Tujuan.getText(),tbObat.getSelectedRow(),8);
                tbObat.setValueAt(TargetSasaran.getText(),tbObat.getSelectedRow(),9);
                tbObat.setValueAt(Lokasi.getText(),tbObat.getSelectedRow(),10);
                tbObat.setValueAt(Double.parseDouble(Jumlah.getText()),tbObat.getSelectedRow(),11);
                tbObat.setValueAt(Double.parseDouble(Harga.getText()),tbObat.getSelectedRow(),12);
                tbObat.setValueAt((Double.parseDouble(Harga.getText())*Double.parseDouble(Jumlah.getText())),tbObat.getSelectedRow(),13);
                tbObat.setValueAt(Keterangan.getText(),tbObat.getSelectedRow(),14);
                tbObat.setValueAt(KdPetugasPJ.getText(),tbObat.getSelectedRow(),15);
                tbObat.setValueAt(NmPetugasPJ.getText(),tbObat.getSelectedRow(),16);
                emptTeks();
                hitung();
        }
    }
    
    private void hitung(){
        total=0;
        for(i=0;i<tabMode.getRowCount();i++){
            total=total+Valid.SetAngka(tbObat.getValueAt(i,13).toString());
        }
        LCount.setText(""+tabMode.getRowCount());
        LCount1.setText(Valid.SetAngka(total));
    }

    private void hapus() {
        if(Sequel.meghapustf("pengajuan_biaya","no_pengajuan",tbObat.getValueAt(tbObat.getSelectedRow(),0).toString())==true){
            tabMode.removeRow(tbObat.getSelectedRow());
            LCount.setText(""+tabMode.getRowCount());
            emptTeks();
            hitung();
        }else{
            JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
        }
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

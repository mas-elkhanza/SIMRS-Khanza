/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgPenyakit.java
 *
 * Created on May 23, 2010, 12:57:16 AM
 */

package simrskhanza;

import keuangan.DlgJnsPerawatanRanap;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.var;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author dosen
 */
public final class DlgCariPerawatanRanap extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement psinputrawatdr,psinputrawatpr,psinputrawatdrpr,pscari,pscari2,pscari3,pscari4,pstarif;
    private ResultSet rs,rstarif;
    private String pilihtable="",kd_pj="",kd_bangsal="",ruang_ranap="Yes", cara_bayar_ranap="Yes";
    private boolean[] pilih; 
    private String[] kode,nama,kategori;
    private double[] totaltnd,bagianrs,bhp,jmdokter,jmperawat,kso,menejemen;
    private int jml=0,i=0,index=0;
    public  DlgCariDokter dokter=new DlgCariDokter(null,false);
    public  DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    public  DlgJnsPerawatanRanap perawatan=new DlgJnsPerawatanRanap(null,false);
    /** Creates new form DlgPenyakit
     * @param parent
     * @param modal */
    public DlgCariPerawatanRanap(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(10,2);
        setSize(656,250);

        Object[] row={"P","Kode","Nama Perawatan","Kategori Perawatan","Tarif/Biaya",
                      "Bagian RS","BHP","JM Dokter","JM Perawat","KSO","Menejemen"};
        tabMode=new DefaultTableModel(null,row){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class,  
                java.lang.Object.class,java.lang.Double.class,java.lang.Double.class,
                java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,
                java.lang.Double.class,java.lang.Double.class
             };
             /*Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
             };*/
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbKamar.setModel(tabMode);
        //tbPenyakit.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbPenyakit.getBackground()));
        tbKamar.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbKamar.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        /*for (i = 0; i < 4; i++) {
            TableColumn column = tbKamar.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(40);
            }else if(i==1){
                column.setPreferredWidth(90);
            }else if(i==2){
                column.setPreferredWidth(450);
            }else if(i==3){
                column.setPreferredWidth(150);
            }
        }*/
        for (i = 0; i < 11; i++) {
            TableColumn column = tbKamar.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(95);
            }else if(i==2){
                column.setPreferredWidth(420);
            }else if(i==3){
                column.setPreferredWidth(150);
            }else if(i==5){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==6){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==7){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==8){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==9){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==10){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else{
                column.setPreferredWidth(120);
            }
        }
        tbKamar.setDefaultRenderer(Object.class, new WarnaTable());
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        TKeluhan.setDocument(new batasInput((byte)400).getKata(TKeluhan));
        kddokter.setDocument(new batasInput((byte)20).getKata(kddokter));
        KdPtg2.setDocument(new batasInput((byte)20).getKata(KdPtg2));
        TPemeriksaan.setDocument(new batasInput((byte)400).getKata(TPemeriksaan));
        TSuhu.setDocument(new batasInput((byte)3).getKata(TSuhu));
        TTensi.setDocument(new batasInput((byte)7).getKata(TTensi));
        if(koneksiDB.cariCepat().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {tampil();}
                @Override
                public void removeUpdate(DocumentEvent e) {tampil();}
                @Override
                public void changedUpdate(DocumentEvent e) {tampil();}
            });
        }    
        jam();
        
        dokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(dokter.getTable().getSelectedRow()!= -1){
                    kddokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                    nmdokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                }  
                kddokter.requestFocus();
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
        
        petugas.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(petugas.getTable().getSelectedRow()!= -1){                   
                    switch (pilihtable) {
                        case "rawat_inap_pr":
                            kddokter.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                            nmdokter.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                            kddokter.requestFocus();
                            break;
                        case "rawat_inap_drpr":
                            KdPtg2.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                            NmPtg2.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());                        
                            KdPtg2.requestFocus();
                            break;
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
        
        TCari.requestFocus();
    }    

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Popup = new javax.swing.JPopupMenu();
        ppBersihkan = new javax.swing.JMenuItem();
        ppDokter = new javax.swing.JMenuItem();
        ppPetugas = new javax.swing.JMenuItem();
        ppPetugasDokter = new javax.swing.JMenuItem();
        TNoRw = new widget.TextBox();
        TSuhu = new widget.TextBox();
        TTensi = new widget.TextBox();
        TKeluhan = new widget.TextBox();
        TPemeriksaan = new widget.TextBox();
        TBerat = new widget.TextBox();
        TTinggi = new widget.TextBox();
        TRespirasi = new widget.TextBox();
        TNadi = new widget.TextBox();
        TGCS = new widget.TextBox();
        TAlergi = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbKamar = new widget.Table();
        panelisi3 = new widget.panelisi();
        label9 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        BtnTambah = new widget.Button();
        BtnSimpan = new widget.Button();
        label10 = new widget.Label();
        LCount = new widget.Label();
        BtnKeluar = new widget.Button();
        FormInput = new widget.PanelBiasa();
        DTPTgl = new widget.Tanggal();
        cmbJam = new widget.ComboBox();
        cmbMnt = new widget.ComboBox();
        cmbDtk = new widget.ComboBox();
        ChkJln = new widget.CekBox();
        jLabel5 = new widget.Label();
        kddokter = new widget.TextBox();
        nmdokter = new widget.TextBox();
        btnDokter = new widget.Button();
        LblPetugas = new widget.Label();
        KdPtg2 = new widget.TextBox();
        NmPtg2 = new widget.TextBox();
        btnPetugas = new widget.Button();
        jLabel6 = new widget.Label();

        Popup.setName("Popup"); // NOI18N

        ppBersihkan.setBackground(new java.awt.Color(255, 255, 255));
        ppBersihkan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppBersihkan.setForeground(new java.awt.Color(102, 51, 0));
        ppBersihkan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); // NOI18N
        ppBersihkan.setText("Hilangkan Centang/Tindakan Terpilih");
        ppBersihkan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppBersihkan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppBersihkan.setIconTextGap(8);
        ppBersihkan.setName("ppBersihkan"); // NOI18N
        ppBersihkan.setPreferredSize(new java.awt.Dimension(250, 25));
        ppBersihkan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppBersihkanActionPerformed(evt);
            }
        });
        Popup.add(ppBersihkan);

        ppDokter.setBackground(new java.awt.Color(255, 255, 255));
        ppDokter.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppDokter.setForeground(new java.awt.Color(102, 51, 0));
        ppDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/inventaris.png"))); // NOI18N
        ppDokter.setText("Ubah Ke Tindakan Dokter");
        ppDokter.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppDokter.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppDokter.setIconTextGap(8);
        ppDokter.setName("ppDokter"); // NOI18N
        ppDokter.setPreferredSize(new java.awt.Dimension(250, 25));
        ppDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppDokterActionPerformed(evt);
            }
        });
        Popup.add(ppDokter);

        ppPetugas.setBackground(new java.awt.Color(255, 255, 255));
        ppPetugas.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppPetugas.setForeground(new java.awt.Color(102, 51, 0));
        ppPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/inventaris.png"))); // NOI18N
        ppPetugas.setText("Ubah Ke Tindakan Petugas");
        ppPetugas.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppPetugas.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppPetugas.setIconTextGap(8);
        ppPetugas.setName("ppPetugas"); // NOI18N
        ppPetugas.setPreferredSize(new java.awt.Dimension(250, 25));
        ppPetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppPetugasActionPerformed(evt);
            }
        });
        Popup.add(ppPetugas);

        ppPetugasDokter.setBackground(new java.awt.Color(255, 255, 255));
        ppPetugasDokter.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppPetugasDokter.setForeground(new java.awt.Color(102, 51, 0));
        ppPetugasDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/inventaris.png"))); // NOI18N
        ppPetugasDokter.setText("Ubah Ke Tindakan Dokter & Petugas");
        ppPetugasDokter.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppPetugasDokter.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppPetugasDokter.setIconTextGap(8);
        ppPetugasDokter.setName("ppPetugasDokter"); // NOI18N
        ppPetugasDokter.setPreferredSize(new java.awt.Dimension(250, 25));
        ppPetugasDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppPetugasDokterActionPerformed(evt);
            }
        });
        Popup.add(ppPetugasDokter);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.setSelectionColor(new java.awt.Color(255, 255, 255));

        TSuhu.setFocusTraversalPolicyProvider(true);
        TSuhu.setName("TSuhu"); // NOI18N
        TSuhu.setSelectionColor(new java.awt.Color(255, 255, 255));

        TTensi.setHighlighter(null);
        TTensi.setName("TTensi"); // NOI18N
        TTensi.setSelectionColor(new java.awt.Color(255, 255, 255));

        TKeluhan.setHighlighter(null);
        TKeluhan.setName("TKeluhan"); // NOI18N
        TKeluhan.setSelectionColor(new java.awt.Color(255, 255, 255));

        TPemeriksaan.setHighlighter(null);
        TPemeriksaan.setName("TPemeriksaan"); // NOI18N
        TPemeriksaan.setSelectionColor(new java.awt.Color(255, 255, 255));

        TBerat.setFocusTraversalPolicyProvider(true);
        TBerat.setName("TBerat"); // NOI18N
        TBerat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TBeratKeyPressed(evt);
            }
        });

        TTinggi.setHighlighter(null);
        TTinggi.setName("TTinggi"); // NOI18N
        TTinggi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TTinggiKeyPressed(evt);
            }
        });

        TRespirasi.setHighlighter(null);
        TRespirasi.setName("TRespirasi"); // NOI18N
        TRespirasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TRespirasiKeyPressed(evt);
            }
        });

        TNadi.setFocusTraversalPolicyProvider(true);
        TNadi.setName("TNadi"); // NOI18N
        TNadi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNadiKeyPressed(evt);
            }
        });

        TGCS.setFocusTraversalPolicyProvider(true);
        TGCS.setName("TGCS"); // NOI18N
        TGCS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TGCSKeyPressed(evt);
            }
        });

        TAlergi.setHighlighter(null);
        TAlergi.setName("TAlergi"); // NOI18N
        TAlergi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TAlergiKeyPressed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Tarif Tagihan/Perawatan/Tindakan Rawat Inap ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 40))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setComponentPopupMenu(Popup);
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbKamar.setAutoCreateRowSorter(true);
        tbKamar.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbKamar.setComponentPopupMenu(Popup);
        tbKamar.setName("tbKamar"); // NOI18N
        tbKamar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbKamarMouseClicked(evt);
            }
        });
        tbKamar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbKamarKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbKamar);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(100, 43));
        panelisi3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        label9.setText("Key Word :");
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(68, 23));
        panelisi3.add(label9);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(450, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelisi3.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('1');
        BtnCari.setToolTipText("Alt+1");
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
        panelisi3.add(BtnCari);

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('2');
        BtnAll.setToolTipText("2Alt+2");
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
        panelisi3.add(BtnAll);

        BtnTambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/plus_16.png"))); // NOI18N
        BtnTambah.setMnemonic('3');
        BtnTambah.setToolTipText("Alt+3");
        BtnTambah.setName("BtnTambah"); // NOI18N
        BtnTambah.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTambahActionPerformed(evt);
            }
        });
        panelisi3.add(BtnTambah);

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan.setMnemonic('S');
        BtnSimpan.setToolTipText("Alt+S");
        BtnSimpan.setName("BtnSimpan"); // NOI18N
        BtnSimpan.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpanActionPerformed(evt);
            }
        });
        panelisi3.add(BtnSimpan);

        label10.setText("Record :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(label10);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(50, 23));
        panelisi3.add(LCount);

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar.setMnemonic('4');
        BtnKeluar.setToolTipText("Alt+4");
        BtnKeluar.setName("BtnKeluar"); // NOI18N
        BtnKeluar.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluarActionPerformed(evt);
            }
        });
        panelisi3.add(BtnKeluar);

        internalFrame1.add(panelisi3, java.awt.BorderLayout.PAGE_END);

        FormInput.setBackground(new java.awt.Color(215, 225, 215));
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(710, 74));
        FormInput.setLayout(null);

        DTPTgl.setEditable(false);
        DTPTgl.setForeground(new java.awt.Color(50, 70, 50));
        DTPTgl.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "23-02-2017" }));
        DTPTgl.setDisplayFormat("dd-MM-yyyy");
        DTPTgl.setName("DTPTgl"); // NOI18N
        DTPTgl.setOpaque(false);
        DTPTgl.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPTglKeyPressed(evt);
            }
        });
        FormInput.add(DTPTgl);
        DTPTgl.setBounds(522, 10, 100, 23);

        cmbJam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        cmbJam.setName("cmbJam"); // NOI18N
        cmbJam.setOpaque(false);
        cmbJam.setPreferredSize(new java.awt.Dimension(55, 28));
        cmbJam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbJamKeyPressed(evt);
            }
        });
        FormInput.add(cmbJam);
        cmbJam.setBounds(625, 10, 50, 23);

        cmbMnt.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        cmbMnt.setName("cmbMnt"); // NOI18N
        cmbMnt.setOpaque(false);
        cmbMnt.setPreferredSize(new java.awt.Dimension(55, 28));
        cmbMnt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbMntKeyPressed(evt);
            }
        });
        FormInput.add(cmbMnt);
        cmbMnt.setBounds(677, 10, 50, 23);

        cmbDtk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        cmbDtk.setName("cmbDtk"); // NOI18N
        cmbDtk.setOpaque(false);
        cmbDtk.setPreferredSize(new java.awt.Dimension(55, 28));
        cmbDtk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbDtkKeyPressed(evt);
            }
        });
        FormInput.add(cmbDtk);
        cmbDtk.setBounds(729, 10, 50, 23);

        ChkJln.setBackground(new java.awt.Color(235, 255, 235));
        ChkJln.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(195, 215, 195)));
        ChkJln.setForeground(new java.awt.Color(153, 0, 51));
        ChkJln.setSelected(true);
        ChkJln.setBorderPainted(true);
        ChkJln.setBorderPaintedFlat(true);
        ChkJln.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkJln.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkJln.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkJln.setName("ChkJln"); // NOI18N
        ChkJln.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkJlnActionPerformed(evt);
            }
        });
        FormInput.add(ChkJln);
        ChkJln.setBounds(781, 10, 23, 23);

        jLabel5.setText("Dokter :");
        jLabel5.setName("jLabel5"); // NOI18N
        FormInput.add(jLabel5);
        jLabel5.setBounds(0, 10, 60, 23);

        kddokter.setHighlighter(null);
        kddokter.setName("kddokter"); // NOI18N
        kddokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kddokterKeyPressed(evt);
            }
        });
        FormInput.add(kddokter);
        kddokter.setBounds(63, 10, 100, 23);

        nmdokter.setEditable(false);
        nmdokter.setHighlighter(null);
        nmdokter.setName("nmdokter"); // NOI18N
        FormInput.add(nmdokter);
        nmdokter.setBounds(165, 10, 250, 23);

        btnDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnDokter.setMnemonic('4');
        btnDokter.setToolTipText("ALt+4");
        btnDokter.setName("btnDokter"); // NOI18N
        btnDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDokterActionPerformed(evt);
            }
        });
        FormInput.add(btnDokter);
        btnDokter.setBounds(417, 10, 28, 23);

        LblPetugas.setText("Petugas :");
        LblPetugas.setName("LblPetugas"); // NOI18N
        FormInput.add(LblPetugas);
        LblPetugas.setBounds(0, 40, 60, 23);

        KdPtg2.setHighlighter(null);
        KdPtg2.setName("KdPtg2"); // NOI18N
        KdPtg2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdPtg2KeyPressed(evt);
            }
        });
        FormInput.add(KdPtg2);
        KdPtg2.setBounds(63, 40, 100, 23);

        NmPtg2.setEditable(false);
        NmPtg2.setHighlighter(null);
        NmPtg2.setName("NmPtg2"); // NOI18N
        FormInput.add(NmPtg2);
        NmPtg2.setBounds(165, 40, 250, 23);

        btnPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPetugas.setMnemonic('4');
        btnPetugas.setToolTipText("ALt+4");
        btnPetugas.setName("btnPetugas"); // NOI18N
        btnPetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPetugasActionPerformed(evt);
            }
        });
        FormInput.add(btnPetugas);
        btnPetugas.setBounds(417, 40, 28, 23);

        jLabel6.setText("Tanggal :");
        jLabel6.setName("jLabel6"); // NOI18N
        FormInput.add(jLabel6);
        jLabel6.setBounds(450, 10, 70, 23);

        internalFrame1.add(FormInput, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            tbKamar.requestFocus();
        }
}//GEN-LAST:event_TCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        tampil();
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
        tampil();
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnCari, TCari);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void tbKamarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbKamarMouseClicked
        if(tbKamar.getRowCount()!=0){
            if(evt.getClickCount()==2){
                dispose();
            }
        }
}//GEN-LAST:event_tbKamarMouseClicked

    private void tbKamarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbKamarKeyPressed
        if(tbKamar.getRowCount()!=0){
            if(evt.getKeyCode()==KeyEvent.VK_ENTER){
                try {
                    i=tbKamar.getSelectedColumn();
                    if(i==1){
                        int baris=tbKamar.getSelectedRow();
                        if(baris>-1){
                          tbKamar.setValueAt(true,tbKamar.getSelectedRow(),0);   
                        }                               
                        TCari.setText("");
                        TCari.requestFocus();
                    }            
                } catch (java.lang.NullPointerException e) {
                }
            }else if(evt.getKeyCode()==KeyEvent.VK_SPACE){
                dispose();
            }else if(evt.getKeyCode()==KeyEvent.VK_SHIFT){
                TCari.setText("");
                TCari.requestFocus();
            }
        }
}//GEN-LAST:event_tbKamarKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        
        //perawatan.setModal(true);
        perawatan.emptTeks();
        perawatan.isCek();
        perawatan.setSize(internalFrame1.getWidth()+40,internalFrame1.getHeight()+40);
        perawatan.setLocationRelativeTo(internalFrame1);
        perawatan.setAlwaysOnTop(false);
        perawatan.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());           
    }//GEN-LAST:event_BtnTambahActionPerformed

private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TNoRw.getText().trim().equals("")||kddokter.getText().trim().equals("")){
            Valid.textKosong(TCari,"Penyakit & Dokter");
        }else{
            try {          
                if(pilihtable.equals("rawat_inap_dr")||pilihtable.equals("rawat_inap_pr")||pilihtable.equals("rawat_inap_drpr")){
                    koneksi.setAutoCommit(false);                              
                    for(i=0;i<tbKamar.getRowCount();i++){ 
                        if(tbKamar.getValueAt(i,0).toString().equals("true")){
                                try {
                                    switch (pilihtable) {
                                        case "rawat_inap_dr":
                                            psinputrawatdr=koneksi.prepareStatement("insert into rawat_inap_dr values(?,?,?,?,?,?,?,?,?,?,?)");
                                            try {
                                                psinputrawatdr.setString(1,TNoRw.getText());
                                                psinputrawatdr.setString(2,tbKamar.getValueAt(i,1).toString());
                                                psinputrawatdr.setString(3,kddokter.getText());
                                                psinputrawatdr.setString(4,Valid.SetTgl(DTPTgl.getSelectedItem()+""));
                                                psinputrawatdr.setString(5,cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem());
                                                psinputrawatdr.setString(6,tbKamar.getValueAt(i,5).toString());
                                                psinputrawatdr.setString(7,tbKamar.getValueAt(i,6).toString());
                                                psinputrawatdr.setString(8,tbKamar.getValueAt(i,7).toString());
                                                psinputrawatdr.setString(9,tbKamar.getValueAt(i,9).toString());
                                                psinputrawatdr.setString(10,tbKamar.getValueAt(i,10).toString());
                                                psinputrawatdr.setString(11,tbKamar.getValueAt(i,4).toString());
                                                psinputrawatdr.executeUpdate();
                                            } catch (Exception e) {
                                                System.out.println("Notifikasi : "+e);
                                            } finally{
                                                if(psinputrawatdr != null){
                                                    psinputrawatdr.close();
                                                }
                                            }
                                            break;
                                        case "rawat_inap_pr":
                                            psinputrawatpr=koneksi.prepareStatement("insert into rawat_inap_pr values(?,?,?,?,?,?,?,?,?,?,?)");
                                            try {
                                                psinputrawatpr.setString(1,TNoRw.getText());
                                                psinputrawatpr.setString(2,tbKamar.getValueAt(i,1).toString());
                                                psinputrawatpr.setString(3,kddokter.getText());
                                                psinputrawatpr.setString(4,Valid.SetTgl(DTPTgl.getSelectedItem()+""));
                                                psinputrawatpr.setString(5,cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem());
                                                psinputrawatpr.setString(6,tbKamar.getValueAt(i,5).toString());
                                                psinputrawatpr.setString(7,tbKamar.getValueAt(i,6).toString());
                                                psinputrawatpr.setString(8,tbKamar.getValueAt(i,8).toString());
                                                psinputrawatpr.setString(9,tbKamar.getValueAt(i,9).toString());
                                                psinputrawatpr.setString(10,tbKamar.getValueAt(i,10).toString());
                                                psinputrawatpr.setString(11,tbKamar.getValueAt(i,4).toString());
                                                psinputrawatpr.executeUpdate();
                                            } catch (Exception e) {
                                                System.out.println("Notifikasi : "+e);
                                            } finally{
                                                if(psinputrawatpr != null){
                                                    psinputrawatpr.close();
                                                }
                                            }
                                            break;
                                        case "rawat_inap_drpr":
                                            psinputrawatdrpr=koneksi.prepareStatement("insert into rawat_inap_drpr values(?,?,?,?,?,?,?,?,?,?,?,?,?)");
                                            try {
                                                psinputrawatdrpr.setString(1,TNoRw.getText());
                                                psinputrawatdrpr.setString(2,tbKamar.getValueAt(i,1).toString());
                                                psinputrawatdrpr.setString(3,kddokter.getText());
                                                psinputrawatdrpr.setString(4,KdPtg2.getText());
                                                psinputrawatdrpr.setString(5,Valid.SetTgl(DTPTgl.getSelectedItem()+""));
                                                psinputrawatdrpr.setString(6,cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem());
                                                psinputrawatdrpr.setString(7,tbKamar.getValueAt(i,5).toString());
                                                psinputrawatdrpr.setString(8,tbKamar.getValueAt(i,6).toString());
                                                psinputrawatdrpr.setString(9,tbKamar.getValueAt(i,7).toString());
                                                psinputrawatdrpr.setString(10,tbKamar.getValueAt(i,8).toString());
                                                psinputrawatdrpr.setString(11,tbKamar.getValueAt(i,9).toString());
                                                psinputrawatdrpr.setString(12,tbKamar.getValueAt(i,10).toString());
                                                psinputrawatdrpr.setString(13,tbKamar.getValueAt(i,4).toString());
                                                psinputrawatdrpr.executeUpdate();
                                            } catch (Exception e) {
                                                System.out.println("Notifikasi : "+e);
                                            } finally{
                                                if(psinputrawatdrpr != null){
                                                    psinputrawatdrpr.close();
                                                }
                                            }
                                            break;
                                    }                                    
                                } catch (Exception e) { 
                                    JOptionPane.showMessageDialog(null,"Maaf, gagal menyimpan tindakan "+tbKamar.getValueAt(i,2).toString()+". Kemungkinan ada data yang sama dimasukkan sebelumnya...!");
                                }    
                                tbKamar.setValueAt(false,i,0); 
                            }                          
                    } 
                    if((!TKeluhan.getText().trim().equals(""))||(!TPemeriksaan.getText().trim().equals(""))||
                        (!TSuhu.getText().trim().equals(""))||(!TTensi.getText().trim().equals(""))||
                        (!TAlergi.getText().trim().equals(""))||(!TTinggi.getText().trim().equals(""))||
                        (!TBerat.getText().trim().equals(""))||(!TRespirasi.getText().trim().equals(""))||
                        (!TNadi.getText().trim().equals(""))||(!TGCS.getText().trim().equals(""))){
                        Sequel.menyimpan("pemeriksaan_ranap","?,?,?,?,?,?,?,?,?,?,?,?,?","Data",13,new String[]{
                                TNoRw.getText(),Valid.SetTgl(DTPTgl.getSelectedItem()+""),cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),                      
                                TSuhu.getText(),TTensi.getText(),TNadi.getText(),TRespirasi.getText(),TTinggi.getText(),
                                TBerat.getText(),TGCS.getText(),TKeluhan.getText(),TPemeriksaan.getText(),TAlergi.getText()
                        });
                    }
                    koneksi.setAutoCommit(true);
                }  
                dispose();
            } catch (Exception ex) {
                System.out.println(ex);                
            }
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

private void ppBersihkanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBersihkanActionPerformed
            for(i=0;i<tbKamar.getRowCount();i++){ 
                tbKamar.setValueAt(false,i,0);
            }
}//GEN-LAST:event_ppBersihkanActionPerformed

private void DTPTglKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPTglKeyPressed
       Valid.pindah(evt,TTensi,cmbJam);
}//GEN-LAST:event_DTPTglKeyPressed

private void cmbJamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbJamKeyPressed
        Valid.pindah(evt,DTPTgl,cmbMnt);
}//GEN-LAST:event_cmbJamKeyPressed

private void cmbMntKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbMntKeyPressed
        Valid.pindah(evt,cmbJam,cmbDtk);
}//GEN-LAST:event_cmbMntKeyPressed

private void cmbDtkKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbDtkKeyPressed
        Valid.pindah(evt,cmbMnt,BtnSimpan);
}//GEN-LAST:event_cmbDtkKeyPressed

private void ChkJlnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkJlnActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_ChkJlnActionPerformed

private void kddokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kddokterKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            switch (pilihtable) {
                case "rawat_inap_dr":
                    Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",nmdokter,kddokter.getText());
                    break;
                case "rawat_inap_pr":
                    Sequel.cariIsi("select nama from petugas where nip=?",nmdokter,kddokter.getText());
                    break;
                case "rawat_inap_drpr":
                    Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",nmdokter,kddokter.getText());
                    break;
            }
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnDokterActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_DOWN){
            TCari.requestFocus();
        }else{            
            Valid.pindah(evt,BtnKeluar,TCari);
        }
}//GEN-LAST:event_kddokterKeyPressed

private void btnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDokterActionPerformed
        switch (pilihtable) {
            case "rawat_inap_dr":
                dokter.isCek();
                dokter.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
                dokter.setLocationRelativeTo(internalFrame1);
                dokter.setAlwaysOnTop(false);
                dokter.setVisible(true);
                break;
            case "rawat_inap_pr":
                petugas.isCek();
                petugas.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
                petugas.setLocationRelativeTo(internalFrame1);
                petugas.setAlwaysOnTop(false);
                petugas.setVisible(true);
                break;
            case "rawat_inap_drpr":
                dokter.isCek();
                dokter.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
                dokter.setLocationRelativeTo(internalFrame1);
                dokter.setAlwaysOnTop(false);
                dokter.setVisible(true);
                break;      
        }        
}//GEN-LAST:event_btnDokterActionPerformed

private void ppDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppDokterActionPerformed
    pilihtable="rawat_inap_dr";
    jLabel5.setText("Dokter :");
    kddokter.setText("");
    nmdokter.setText("");
    LblPetugas.setVisible(false);
    KdPtg2.setVisible(false);
    NmPtg2.setVisible(false);
    btnPetugas.setVisible(false);
    FormInput.setPreferredSize(new Dimension(WIDTH, 44));
    tampil();
}//GEN-LAST:event_ppDokterActionPerformed

private void ppPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppPetugasActionPerformed
    pilihtable="rawat_inap_pr";
    jLabel5.setText("Petugas :");
    kddokter.setText("");
    nmdokter.setText(""); 
    LblPetugas.setVisible(false);
    KdPtg2.setVisible(false);
    NmPtg2.setVisible(false);
    btnPetugas.setVisible(false);  
    FormInput.setPreferredSize(new Dimension(WIDTH, 44));
    tampil();
}//GEN-LAST:event_ppPetugasActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        TCari.requestFocus();        
    }//GEN-LAST:event_formWindowActivated

    private void KdPtg2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdPtg2KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select nama from petugas where nip=?",NmPtg2,KdPtg2.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnPetugasActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_DOWN){
            TCari.requestFocus();
        }else{            
            Valid.pindah(evt,BtnKeluar,BtnSimpan);
        }
    }//GEN-LAST:event_KdPtg2KeyPressed

    private void btnPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPetugasActionPerformed
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setAlwaysOnTop(false);
        petugas.setVisible(true);
    }//GEN-LAST:event_btnPetugasActionPerformed

    private void ppPetugasDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppPetugasDokterActionPerformed
        pilihtable="rawat_inap_drpr";
        jLabel5.setText(" Dokter :");
        kddokter.setText("");
        nmdokter.setText("");
        LblPetugas.setVisible(true);
        KdPtg2.setVisible(true);
        NmPtg2.setVisible(true);
        btnPetugas.setVisible(true);
        FormInput.setPreferredSize(new Dimension(WIDTH, 74));
        tampil();
    }//GEN-LAST:event_ppPetugasDokterActionPerformed

    private void TBeratKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TBeratKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TBeratKeyPressed

    private void TTinggiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TTinggiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TTinggiKeyPressed

    private void TRespirasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TRespirasiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TRespirasiKeyPressed

    private void TNadiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNadiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TNadiKeyPressed

    private void TGCSKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TGCSKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TGCSKeyPressed

    private void TAlergiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TAlergiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TAlergiKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgCariPerawatanRanap dialog = new DlgCariPerawatanRanap(new javax.swing.JFrame(), true);
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
    private widget.Button BtnCari;
    private widget.Button BtnKeluar;
    private widget.Button BtnSimpan;
    private widget.Button BtnTambah;
    private widget.CekBox ChkJln;
    private widget.Tanggal DTPTgl;
    private widget.PanelBiasa FormInput;
    private widget.TextBox KdPtg2;
    private widget.Label LCount;
    private widget.Label LblPetugas;
    private widget.TextBox NmPtg2;
    private javax.swing.JPopupMenu Popup;
    private widget.ScrollPane Scroll;
    private widget.TextBox TAlergi;
    private widget.TextBox TBerat;
    private widget.TextBox TCari;
    private widget.TextBox TGCS;
    private widget.TextBox TKeluhan;
    private widget.TextBox TNadi;
    private widget.TextBox TNoRw;
    private widget.TextBox TPemeriksaan;
    private widget.TextBox TRespirasi;
    private widget.TextBox TSuhu;
    private widget.TextBox TTensi;
    private widget.TextBox TTinggi;
    private widget.Button btnDokter;
    private widget.Button btnPetugas;
    private widget.ComboBox cmbDtk;
    private widget.ComboBox cmbJam;
    private widget.ComboBox cmbMnt;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel5;
    private widget.Label jLabel6;
    private widget.TextBox kddokter;
    private widget.Label label10;
    private widget.Label label9;
    private widget.TextBox nmdokter;
    private widget.panelisi panelisi3;
    private javax.swing.JMenuItem ppBersihkan;
    private javax.swing.JMenuItem ppDokter;
    private javax.swing.JMenuItem ppPetugas;
    private javax.swing.JMenuItem ppPetugasDokter;
    private widget.Table tbKamar;
    // End of variables declaration//GEN-END:variables

    public void tampil() { 
        try{  
            jml=0;
            for(i=0;i<tbKamar.getRowCount();i++){
                if(tbKamar.getValueAt(i,0).toString().equals("true")){
                    jml++;
                }
            }

            pilih=null;
            pilih=new boolean[jml]; 
            kode=null;
            kode=new String[jml];
            nama=null;
            nama=new String[jml];
            kategori=null;
            kategori=new String[jml];
            totaltnd=null;
            totaltnd=new double[jml];  
            bagianrs=null;
            bagianrs=new double[jml];
            bhp=null;
            bhp=new double[jml];
            jmdokter=null;
            jmdokter=new double[jml];
            jmperawat=null;
            jmperawat=new double[jml];
            kso=null;
            kso=new double[jml];
            menejemen=null;
            menejemen=new double[jml];

            index=0;        
            for(i=0;i<tbKamar.getRowCount();i++){
                if(tbKamar.getValueAt(i,0).toString().equals("true")){
                    pilih[index]=true;
                    kode[index]=tbKamar.getValueAt(i,1).toString();
                    nama[index]=tbKamar.getValueAt(i,2).toString();
                    kategori[index]=tbKamar.getValueAt(i,3).toString();
                    totaltnd[index]=Double.parseDouble(tbKamar.getValueAt(i,4).toString());
                    bagianrs[index]=Double.parseDouble(tbKamar.getValueAt(i,5).toString());
                    bhp[index]=Double.parseDouble(tbKamar.getValueAt(i,6).toString());
                    jmdokter[index]=Double.parseDouble(tbKamar.getValueAt(i,7).toString());
                    jmperawat[index]=Double.parseDouble(tbKamar.getValueAt(i,8).toString());  
                    kso[index]=Double.parseDouble(tbKamar.getValueAt(i,9).toString());  
                    menejemen[index]=Double.parseDouble(tbKamar.getValueAt(i,10).toString());    
                    index++;
                }
            }       
            
            Valid.tabelKosong(tabMode);

            for(i=0;i<jml;i++){
                tabMode.addRow(new Object[] {
                    pilih[i],kode[i],nama[i],kategori[i],totaltnd[i],bagianrs[i],bhp[i],jmdokter[i],jmperawat[i],kso[i],menejemen[i]
                });
            }
            
            pscari=koneksi.prepareStatement("select jns_perawatan_inap.kd_jenis_prw,jns_perawatan_inap.nm_perawatan,kategori_perawatan.nm_kategori,"+
                   "jns_perawatan_inap.total_byrdr,jns_perawatan_inap.total_byrpr,jns_perawatan_inap.total_byrdrpr,jns_perawatan_inap.bhp,jns_perawatan_inap.material,jns_perawatan_inap.kso,jns_perawatan_inap.menejemen," +
                   "jns_perawatan_inap.tarif_tindakandr,jns_perawatan_inap.tarif_tindakanpr from jns_perawatan_inap inner join kategori_perawatan "+
                   "on jns_perawatan_inap.kd_kategori=kategori_perawatan.kd_kategori  "+
                   "where jns_perawatan_inap.status='1' and (jns_perawatan_inap.kd_pj=? or jns_perawatan_inap.kd_pj='-') and (jns_perawatan_inap.kd_bangsal=? or jns_perawatan_inap.kd_bangsal='-') and jns_perawatan_inap.kd_jenis_prw like ? or "+
                    " jns_perawatan_inap.status='1' and (jns_perawatan_inap.kd_pj=? or jns_perawatan_inap.kd_pj='-') and (jns_perawatan_inap.kd_bangsal=? or jns_perawatan_inap.kd_bangsal='-') and jns_perawatan_inap.nm_perawatan like ? or "+
                    " jns_perawatan_inap.status='1' and (jns_perawatan_inap.kd_pj=? or jns_perawatan_inap.kd_pj='-') and (jns_perawatan_inap.kd_bangsal=? or jns_perawatan_inap.kd_bangsal='-') and kategori_perawatan.nm_kategori like ? order by jns_perawatan_inap.nm_perawatan");
            pscari2=koneksi.prepareStatement("select jns_perawatan_inap.kd_jenis_prw,jns_perawatan_inap.nm_perawatan,kategori_perawatan.nm_kategori,"+
                   "jns_perawatan_inap.total_byrdr,jns_perawatan_inap.total_byrpr,jns_perawatan_inap.total_byrdrpr,jns_perawatan_inap.bhp,jns_perawatan_inap.material,jns_perawatan_inap.kso,jns_perawatan_inap.menejemen," +
                   "jns_perawatan_inap.tarif_tindakandr,jns_perawatan_inap.tarif_tindakanpr from jns_perawatan_inap inner join kategori_perawatan "+
                   "on jns_perawatan_inap.kd_kategori=kategori_perawatan.kd_kategori  "+
                   "where jns_perawatan_inap.status='1' and (jns_perawatan_inap.kd_pj=? or jns_perawatan_inap.kd_pj='-') and jns_perawatan_inap.kd_jenis_prw like ? or "+
                    " jns_perawatan_inap.status='1' and (jns_perawatan_inap.kd_pj=? or jns_perawatan_inap.kd_pj='-') and jns_perawatan_inap.nm_perawatan like ? or "+
                    " jns_perawatan_inap.status='1' and (jns_perawatan_inap.kd_pj=? or jns_perawatan_inap.kd_pj='-') and kategori_perawatan.nm_kategori like ? order by jns_perawatan_inap.nm_perawatan");
            pscari3=koneksi.prepareStatement("select jns_perawatan_inap.kd_jenis_prw,jns_perawatan_inap.nm_perawatan,kategori_perawatan.nm_kategori,"+
                   "jns_perawatan_inap.total_byrdr,jns_perawatan_inap.total_byrpr,jns_perawatan_inap.total_byrdrpr,jns_perawatan_inap.bhp,jns_perawatan_inap.material,jns_perawatan_inap.kso,jns_perawatan_inap.menejemen," +
                   "jns_perawatan_inap.tarif_tindakandr,jns_perawatan_inap.tarif_tindakanpr from jns_perawatan_inap inner join kategori_perawatan "+
                   "on jns_perawatan_inap.kd_kategori=kategori_perawatan.kd_kategori  "+
                   "where jns_perawatan_inap.status='1' and (jns_perawatan_inap.kd_bangsal=? or jns_perawatan_inap.kd_bangsal='-') and jns_perawatan_inap.kd_jenis_prw like ? or "+
                    " jns_perawatan_inap.status='1' and (jns_perawatan_inap.kd_bangsal=? or jns_perawatan_inap.kd_bangsal='-') and jns_perawatan_inap.nm_perawatan like ? or "+
                    " jns_perawatan_inap.status='1' and (jns_perawatan_inap.kd_bangsal=? or jns_perawatan_inap.kd_bangsal='-') and kategori_perawatan.nm_kategori like ? order by jns_perawatan_inap.nm_perawatan");
            pscari4=koneksi.prepareStatement("select jns_perawatan_inap.kd_jenis_prw,jns_perawatan_inap.nm_perawatan,kategori_perawatan.nm_kategori,"+
                   "jns_perawatan_inap.total_byrdr,jns_perawatan_inap.total_byrpr,jns_perawatan_inap.total_byrdrpr,jns_perawatan_inap.bhp,jns_perawatan_inap.material,jns_perawatan_inap.kso,jns_perawatan_inap.menejemen," +
                   "jns_perawatan_inap.tarif_tindakandr,jns_perawatan_inap.tarif_tindakanpr from jns_perawatan_inap inner join kategori_perawatan "+
                   "on jns_perawatan_inap.kd_kategori=kategori_perawatan.kd_kategori  "+
                   "where jns_perawatan_inap.status='1' and jns_perawatan_inap.kd_jenis_prw like ? or "+
                    " jns_perawatan_inap.status='1' and jns_perawatan_inap.nm_perawatan like ? or "+
                    " jns_perawatan_inap.status='1' and kategori_perawatan.nm_kategori like ? order by jns_perawatan_inap.nm_perawatan");
            try {
                if(ruang_ranap.equals("Yes")&&cara_bayar_ranap.equals("Yes")){
                    pscari.setString(1,kd_pj.trim());
                    pscari.setString(2,kd_bangsal.trim());
                    pscari.setString(3,"%"+TCari.getText().trim()+"%");
                    pscari.setString(4,kd_pj.trim());
                    pscari.setString(5,kd_bangsal.trim());
                    pscari.setString(6,"%"+TCari.getText().trim()+"%");
                    pscari.setString(7,kd_pj.trim());
                    pscari.setString(8,kd_bangsal.trim());
                    pscari.setString(9,"%"+TCari.getText().trim()+"%");
                    rs=pscari.executeQuery();
                }else if(ruang_ranap.equals("No")&&cara_bayar_ranap.equals("Yes")){
                    pscari2.setString(1,kd_pj.trim());
                    pscari2.setString(2,"%"+TCari.getText().trim()+"%");
                    pscari2.setString(3,kd_pj.trim());
                    pscari2.setString(4,"%"+TCari.getText().trim()+"%");
                    pscari2.setString(5,kd_pj.trim());
                    pscari2.setString(6,"%"+TCari.getText().trim()+"%");
                    rs=pscari2.executeQuery();
                }else if(ruang_ranap.equals("Yes")&&cara_bayar_ranap.equals("No")){
                    pscari3.setString(1,kd_bangsal.trim());
                    pscari3.setString(2,"%"+TCari.getText().trim()+"%");
                    pscari3.setString(3,kd_bangsal.trim());
                    pscari3.setString(4,"%"+TCari.getText().trim()+"%");
                    pscari3.setString(5,kd_bangsal.trim());
                    pscari3.setString(6,"%"+TCari.getText().trim()+"%");
                    rs=pscari3.executeQuery();
                }else if(ruang_ranap.equals("No")&&cara_bayar_ranap.equals("No")){
                    pscari4.setString(1,"%"+TCari.getText().trim()+"%");
                    pscari4.setString(2,"%"+TCari.getText().trim()+"%");
                    pscari4.setString(3,"%"+TCari.getText().trim()+"%");
                    rs=pscari4.executeQuery();
                }

                switch (pilihtable) {
                    case "rawat_inap_dr":
                        while(rs.next()){
                            if(rs.getDouble("total_byrdr")>0){
                                tabMode.addRow(new Object[] {
                                    false,rs.getString(1),rs.getString(2),rs.getString(3),
                                    rs.getDouble("total_byrdr"),rs.getDouble("material"),
                                    rs.getDouble("bhp"),rs.getDouble("tarif_tindakandr"),
                                    rs.getDouble("tarif_tindakanpr"),rs.getDouble("kso"),
                                    rs.getDouble("menejemen")
                                });
                            }                        
                        }   
                        break;
                    case "rawat_inap_pr":
                        while(rs.next()){
                            if(rs.getDouble("total_byrpr")>0){
                                tabMode.addRow(new Object[] {
                                    false,rs.getString(1),rs.getString(2),rs.getString(3),
                                    rs.getDouble("total_byrpr"),rs.getDouble("material"),
                                    rs.getDouble("bhp"),rs.getDouble("tarif_tindakandr"),
                                    rs.getDouble("tarif_tindakanpr"),rs.getDouble("kso"),
                                    rs.getDouble("menejemen")
                                });
                            }                            
                        }   
                        break;
                    case "rawat_inap_drpr":
                        while(rs.next()){
                            if(rs.getDouble("total_byrdrpr")>0){
                                tabMode.addRow(new Object[] {
                                    false,rs.getString(1),rs.getString(2),rs.getString(3),
                                    rs.getDouble("total_byrdrpr"),rs.getDouble("material"),
                                    rs.getDouble("bhp"),rs.getDouble("tarif_tindakandr"),
                                    rs.getDouble("tarif_tindakanpr"),rs.getDouble("kso"),
                                    rs.getDouble("menejemen")
                                });
                            }                        
                        }   
                        break;
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rs != null){
                    rs.close();
                }                
                if(pscari != null){
                    pscari.close();
                }
                if(pscari2 != null){
                    pscari2.close();
                }
                if(pscari3 != null){
                    pscari3.close();
                }
                if(pscari4 != null){
                    pscari4.close();
                }
            }
        }catch(SQLException e){
            System.out.println("Notifikasi : "+e);
        }
        LCount.setText(""+tbKamar.getRowCount());
    }

    public void emptTeks() { 
        TCari.setText("");
    }

    public JTable getTable(){
        return tbKamar;
    }
    
    public void isCek(){
        BtnTambah.setEnabled(var.gettarif_ranap());
    }
    
    public void setNoRm(String norwt,String pilihtable,Date tanggal,String jam,String menit,String detik,boolean status) {
        for(i=0;i<tbKamar.getRowCount();i++){ 
            tbKamar.setValueAt(false,i,0);
        }
        TNoRw.setText(norwt);
        kddokter.setText("");
        this.kd_pj=Sequel.cariIsi("select kd_pj from reg_periksa where no_rawat=?",TNoRw.getText());
        this.kd_bangsal=Sequel.cariIsi(
                "select bangsal.kd_bangsal from bangsal inner join kamar inner join kamar_inap "+
                "on bangsal.kd_bangsal=kamar.kd_bangsal and kamar.kd_kamar=kamar_inap.kd_kamar "+
                "where no_rawat=? and stts_pulang='-' order by STR_TO_DATE(concat(kamar_inap.tgl_masuk,' ',jam_masuk),'%Y-%m-%d %H:%i:%s') desc limit 1",TNoRw.getText());
        this.pilihtable=pilihtable;
        switch (pilihtable) {
            case "rawat_inap_dr":
                jLabel5.setText("Dokter :");                
                LblPetugas.setVisible(false);
                KdPtg2.setVisible(false);
                NmPtg2.setVisible(false);
                btnPetugas.setVisible(false);
                FormInput.setPreferredSize(new Dimension(WIDTH, 44));
                break;
            case "rawat_inap_pr":
                jLabel5.setText("Perawat :");                
                LblPetugas.setVisible(false);
                KdPtg2.setVisible(false);
                NmPtg2.setVisible(false);
                btnPetugas.setVisible(false);
                FormInput.setPreferredSize(new Dimension(WIDTH, 44));
                break;
            case "rawat_inap_drpr":
                jLabel5.setText(" Dokter :");                
                LblPetugas.setVisible(true);
                KdPtg2.setVisible(true);
                NmPtg2.setVisible(true);
                btnPetugas.setVisible(true); 
                FormInput.setPreferredSize(new Dimension(WIDTH, 74));
                break;
        }
        try {
            pstarif=koneksi.prepareStatement("select * from set_tarif");
            try {
                rstarif=pstarif.executeQuery();
                if(rstarif.next()){
                    ruang_ranap=rstarif.getString("ruang_ranap");
                    cara_bayar_ranap=rstarif.getString("cara_bayar_ranap");
                }else{
                    ruang_ranap="Yes";
                    cara_bayar_ranap="Yes";
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rstarif != null){
                    rstarif.close();
                }
                if(pstarif != null){
                    pstarif.close();
                }
            }                 
        } catch (Exception e) {
            System.out.println("Notifikasi : "+e);
        }
        DTPTgl.setDate(tanggal);
        cmbJam.setSelectedItem(jam);
        cmbMnt.setSelectedItem(menit);
        cmbDtk.setSelectedItem(detik);
        ChkJln.setSelected(status);
    }
    
    public void setPetugas(String kode, String nama,String suhu,String tensi, String Hasil, 
            String perkembangan, String kode2, String nama2,String berat,String tinggi, 
            String nadi,String respirasi,String gcs,String alergi){
        kddokter.setText(kode);
        nmdokter.setText(nama);
        KdPtg2.setText(kode2);
        NmPtg2.setText(nama2);
        TSuhu.setText(suhu);
        TTensi.setText(tensi);
        TKeluhan.setText(Hasil);
        TPemeriksaan.setText(perkembangan);
        TBerat.setText(berat);
        TTinggi.setText(tinggi);
        TRespirasi.setText(respirasi);
        TNadi.setText(nadi);
        TGCS.setText(gcs);
        TAlergi.setText(alergi);
    }
    
    private void jam(){
        ActionListener taskPerformer = new ActionListener(){
            private int nilai_jam;
            private int nilai_menit;
            private int nilai_detik;
            @Override
            public void actionPerformed(ActionEvent e) {
                String nol_jam = "";
                String nol_menit = "";
                String nol_detik = "";
                // Membuat Date
                //Date dt = new Date();
                Date now = Calendar.getInstance().getTime();

                // Mengambil nilaj JAM, MENIT, dan DETIK Sekarang
                if(ChkJln.isSelected()==true){
                    nilai_jam = now.getHours();
                    nilai_menit = now.getMinutes();
                    nilai_detik = now.getSeconds();
                }else if(ChkJln.isSelected()==false){
                    nilai_jam =cmbJam.getSelectedIndex();
                    nilai_menit =cmbMnt.getSelectedIndex();
                    nilai_detik =cmbDtk.getSelectedIndex();
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
                cmbJam.setSelectedItem(jam);
                cmbMnt.setSelectedItem(menit);
                cmbDtk.setSelectedItem(detik);
            }
        };
        // Timer
        new Timer(1000, taskPerformer).start();
    }
}

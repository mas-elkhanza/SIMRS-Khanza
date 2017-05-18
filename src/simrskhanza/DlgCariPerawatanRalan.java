/*
  Dilarang keras menggandakan/mengcopy/menyebarkan/membajak/mendecompile 
  Software ini dalam bentuk apapun tanpa seijin pembuat software
  (Khanza.Soft Media). Bagi yang sengaja membajak softaware ini ta
  npa ijin, kami sumpahi sial 1000 turunan, miskin sampai 500 turu
  nan. Selalu mendapat kecelakaan sampai 400 turunan. Anak pertama
  nya cacat tidak punya kaki sampai 300 turunan. Susah cari jodoh
  sampai umur 50 tahun sampai 200 turunan. Ya Alloh maafkan kami 
  karena telah berdoa buruk, semua ini kami lakukan karena kami ti
  dak pernah rela karya kami dibajak tanpa ijin.
 */

package simrskhanza;

import keuangan.DlgJnsPerawatanRalan;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.var;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author dosen
 */
public final class DlgCariPerawatanRalan extends javax.swing.JDialog {
    private final DefaultTableModel TabModeTindakan;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement pstindakan,pstindakan2,pstindakan3,pstindakan4,
            pssimpandokter,pssimpanperawat,pssimpandokterperawat,psset_tarif;
    private ResultSet rstindakan,rsset_tarif;
    private String pilihtable="",kd_pj="",kd_poli="",poli_ralan="Yes",cara_bayar_ralan="Yes";
    private boolean[] pilih; 
    private String[] kode,nama,kategori;
    private double[] totaltnd,bagianrs,bhp,jmdokter,jmperawat,kso,menejemen;
    private int jml=0,i=0,index=0;
    public  DlgJnsPerawatanRalan perawatan=new DlgJnsPerawatanRalan(null,false);
    public  DlgCariDokter dokter=new DlgCariDokter(null,false);
    public  DlgCariPetugas petugas=new DlgCariPetugas(null,false);

    /**
     * Creates new form DlgPenyakit
     * @param parent
     * @param modal
     */
    public DlgCariPerawatanRalan(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(10,2);
        setSize(656,250);

        Object[] row={"P","Kode","Nama Perawatan","Kategori Perawatan","Tarif/Biaya","Bagian RS","BHP","JM Dokter","JM Perawat","KSO","Menejemen"};
        TabModeTindakan=new DefaultTableModel(null,row){
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
        tbTindakan.setModel(TabModeTindakan);
        tbTindakan.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbTindakan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (i = 0; i < 11; i++) {
            TableColumn column = tbTindakan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(90);
            }else if(i==2){
                column.setPreferredWidth(310);
            }else if(i==3){
                column.setPreferredWidth(110);
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
                column.setPreferredWidth(90);
            }
        }
        tbTindakan.setDefaultRenderer(Object.class, new WarnaTable());
        TCariTindakan.setDocument(new batasInput((byte)100).getKata(TCariTindakan));    
        if(koneksiDB.cariCepat().equals("aktif")){
            TCariTindakan.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {tampil();}
                @Override
                public void removeUpdate(DocumentEvent e) {tampil();}
                @Override
                public void changedUpdate(DocumentEvent e) {tampil();}
            });
        } 
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
                        case "rawat_jl_pr":
                            kddokter.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                            nmdokter.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                            kddokter.requestFocus();
                            break;
                        case "rawat_jl_drpr":
                            Nip2.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                            NmPetugas2.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());                        
                            Nip2.requestFocus();
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
        Tanggal = new widget.TextBox();
        Jam = new widget.TextBox();
        TBerat = new widget.TextBox();
        TTinggi = new widget.TextBox();
        TRespirasi = new widget.TextBox();
        TNadi = new widget.TextBox();
        TGCS = new widget.TextBox();
        TAlergi = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbTindakan = new widget.Table();
        panelisi3 = new widget.panelisi();
        label9 = new widget.Label();
        TCariTindakan = new widget.TextBox();
        BtnCariTindakan = new widget.Button();
        BtnAllTindakan = new widget.Button();
        BtnTambahTindakan = new widget.Button();
        BtnSimpanTindakan = new widget.Button();
        label10 = new widget.Label();
        LCount = new widget.Label();
        BtnKeluar = new widget.Button();
        FormInput = new widget.PanelBiasa();
        jLabel5 = new widget.Label();
        kddokter = new widget.TextBox();
        nmdokter = new widget.TextBox();
        btnDokter = new widget.Button();
        jLabel6 = new widget.Label();
        Nip2 = new widget.TextBox();
        NmPetugas2 = new widget.TextBox();
        btnPetugas = new widget.Button();

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

        Tanggal.setHighlighter(null);
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.setSelectionColor(new java.awt.Color(255, 255, 255));

        Jam.setHighlighter(null);
        Jam.setName("Jam"); // NOI18N
        Jam.setSelectionColor(new java.awt.Color(255, 255, 255));

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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Tarif Tagihan/Perawatan/Tindakan Rawat Jalan ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 40))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setComponentPopupMenu(Popup);
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbTindakan.setAutoCreateRowSorter(true);
        tbTindakan.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbTindakan.setComponentPopupMenu(Popup);
        tbTindakan.setName("tbTindakan"); // NOI18N
        tbTindakan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbTindakanMouseClicked(evt);
            }
        });
        tbTindakan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbTindakanKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbTindakan);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(100, 43));
        panelisi3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        label9.setText("Key Word :");
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(68, 23));
        panelisi3.add(label9);

        TCariTindakan.setName("TCariTindakan"); // NOI18N
        TCariTindakan.setPreferredSize(new java.awt.Dimension(260, 24));
        TCariTindakan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariTindakanKeyPressed(evt);
            }
        });
        panelisi3.add(TCariTindakan);

        BtnCariTindakan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCariTindakan.setMnemonic('1');
        BtnCariTindakan.setToolTipText("Alt+1");
        BtnCariTindakan.setName("BtnCariTindakan"); // NOI18N
        BtnCariTindakan.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCariTindakan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariTindakanActionPerformed(evt);
            }
        });
        BtnCariTindakan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCariTindakanKeyPressed(evt);
            }
        });
        panelisi3.add(BtnCariTindakan);

        BtnAllTindakan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAllTindakan.setMnemonic('2');
        BtnAllTindakan.setToolTipText("Alt+2");
        BtnAllTindakan.setName("BtnAllTindakan"); // NOI18N
        BtnAllTindakan.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnAllTindakan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAllTindakanActionPerformed(evt);
            }
        });
        BtnAllTindakan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnAllTindakanKeyPressed(evt);
            }
        });
        panelisi3.add(BtnAllTindakan);

        BtnTambahTindakan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/plus_16.png"))); // NOI18N
        BtnTambahTindakan.setMnemonic('3');
        BtnTambahTindakan.setToolTipText("Alt+3");
        BtnTambahTindakan.setName("BtnTambahTindakan"); // NOI18N
        BtnTambahTindakan.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnTambahTindakan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTambahTindakanActionPerformed(evt);
            }
        });
        panelisi3.add(BtnTambahTindakan);

        BtnSimpanTindakan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpanTindakan.setMnemonic('S');
        BtnSimpanTindakan.setToolTipText("Alt+S");
        BtnSimpanTindakan.setName("BtnSimpanTindakan"); // NOI18N
        BtnSimpanTindakan.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSimpanTindakan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpanTindakanActionPerformed(evt);
            }
        });
        panelisi3.add(BtnSimpanTindakan);

        label10.setText("Record :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(label10);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(55, 23));
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

        jLabel5.setText("Dokter :");
        jLabel5.setName("jLabel5"); // NOI18N
        jLabel5.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel5);
        jLabel5.setBounds(3, 10, 55, 23);

        kddokter.setHighlighter(null);
        kddokter.setName("kddokter"); // NOI18N
        kddokter.setPreferredSize(new java.awt.Dimension(120, 23));
        kddokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kddokterKeyPressed(evt);
            }
        });
        FormInput.add(kddokter);
        kddokter.setBounds(60, 10, 120, 23);

        nmdokter.setEditable(false);
        nmdokter.setHighlighter(null);
        nmdokter.setName("nmdokter"); // NOI18N
        nmdokter.setPreferredSize(new java.awt.Dimension(400, 23));
        FormInput.add(nmdokter);
        nmdokter.setBounds(182, 10, 400, 23);

        btnDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnDokter.setMnemonic('4');
        btnDokter.setToolTipText("ALt+4");
        btnDokter.setName("btnDokter"); // NOI18N
        btnDokter.setPreferredSize(new java.awt.Dimension(28, 23));
        btnDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDokterActionPerformed(evt);
            }
        });
        FormInput.add(btnDokter);
        btnDokter.setBounds(584, 10, 28, 23);

        jLabel6.setText("Petugas :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel6);
        jLabel6.setBounds(3, 40, 55, 23);

        Nip2.setHighlighter(null);
        Nip2.setName("Nip2"); // NOI18N
        Nip2.setPreferredSize(new java.awt.Dimension(120, 23));
        Nip2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Nip2KeyPressed(evt);
            }
        });
        FormInput.add(Nip2);
        Nip2.setBounds(60, 40, 120, 23);

        NmPetugas2.setEditable(false);
        NmPetugas2.setHighlighter(null);
        NmPetugas2.setName("NmPetugas2"); // NOI18N
        NmPetugas2.setPreferredSize(new java.awt.Dimension(400, 23));
        FormInput.add(NmPetugas2);
        NmPetugas2.setBounds(182, 40, 400, 23);

        btnPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPetugas.setMnemonic('4');
        btnPetugas.setToolTipText("ALt+4");
        btnPetugas.setName("btnPetugas"); // NOI18N
        btnPetugas.setPreferredSize(new java.awt.Dimension(28, 23));
        btnPetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPetugasActionPerformed(evt);
            }
        });
        FormInput.add(btnPetugas);
        btnPetugas.setBounds(584, 40, 28, 23);

        internalFrame1.add(FormInput, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void TCariTindakanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariTindakanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariTindakanActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            tbTindakan.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCariTindakan.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            kddokter.requestFocus();
        }
}//GEN-LAST:event_TCariTindakanKeyPressed

    private void BtnCariTindakanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariTindakanActionPerformed
        tampil();
}//GEN-LAST:event_BtnCariTindakanActionPerformed

    private void BtnCariTindakanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariTindakanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariTindakanActionPerformed(null);
        }else{
            Valid.pindah(evt, TCariTindakan, BtnAllTindakan);
        }
}//GEN-LAST:event_BtnCariTindakanKeyPressed

    private void BtnAllTindakanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllTindakanActionPerformed
        TCariTindakan.setText("");
        tampil();
}//GEN-LAST:event_BtnAllTindakanActionPerformed

    private void BtnAllTindakanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllTindakanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllTindakanActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnCariTindakan, TCariTindakan);
        }
}//GEN-LAST:event_BtnAllTindakanKeyPressed

    private void tbTindakanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbTindakanMouseClicked
        if(tbTindakan.getRowCount()!=0){
            if(evt.getClickCount()==2){
                dispose();
            }
        }
}//GEN-LAST:event_tbTindakanMouseClicked

    private void tbTindakanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbTindakanKeyPressed
        if(tbTindakan.getRowCount()!=0){
            if(evt.getKeyCode()==KeyEvent.VK_ENTER){
                try {
                    i=tbTindakan.getSelectedColumn();
                    if(i==1){
                        if(tbTindakan.getSelectedRow()>-1){
                          tbTindakan.setValueAt(true,tbTindakan.getSelectedRow(),0);   
                        }                               
                        TCariTindakan.setText("");
                        TCariTindakan.requestFocus();
                    }           
                } catch (java.lang.NullPointerException e) {
                }
            }else if(evt.getKeyCode()==KeyEvent.VK_SPACE){
                dispose();
            }else if(evt.getKeyCode()==KeyEvent.VK_SHIFT){
                TCariTindakan.setText("");
                TCariTindakan.requestFocus();
            }
        }
}//GEN-LAST:event_tbTindakanKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
             dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnTambahTindakanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahTindakanActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));        
        //perawatan.setModal(true);
        perawatan.emptTeks();
        perawatan.isCek();
        perawatan.setSize(internalFrame1.getWidth()+40,internalFrame1.getHeight()+40);
        perawatan.setLocationRelativeTo(internalFrame1);
        perawatan.setAlwaysOnTop(false);
        perawatan.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());           
    }//GEN-LAST:event_BtnTambahTindakanActionPerformed

private void BtnSimpanTindakanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanTindakanActionPerformed
        if(TNoRw.getText().trim().equals("")||kddokter.getText().trim().equals("")||nmdokter.getText().trim().equals("")){
            Valid.textKosong(TCariTindakan,"Dokter/Paramedis");            
        }else{              
            try {                
                koneksi.setAutoCommit(false);
                if(pilihtable.equals("rawat_jl_dr")||pilihtable.equals("rawat_jl_pr")||pilihtable.equals("rawat_jl_drpr")){                    
                    for(i=0;i<tbTindakan.getRowCount();i++){ 
                        if(tbTindakan.getValueAt(i,0).toString().equals("true")){
                                try {                                    
                                    switch (pilihtable) {
                                        case "rawat_jl_dr":
                                            pssimpandokter=koneksi.prepareStatement("insert into rawat_jl_dr values(?,?,?,?,?,?,?,?,?)");
                                            try {
                                                pssimpandokter.setString(1,TNoRw.getText());
                                                pssimpandokter.setString(2,tbTindakan.getValueAt(i,1).toString());
                                                pssimpandokter.setString(3,kddokter.getText());
                                                pssimpandokter.setString(4,tbTindakan.getValueAt(i,5).toString());
                                                pssimpandokter.setString(5,tbTindakan.getValueAt(i,6).toString());
                                                pssimpandokter.setString(6,tbTindakan.getValueAt(i,7).toString());
                                                pssimpandokter.setString(7,tbTindakan.getValueAt(i,9).toString());
                                                pssimpandokter.setString(8,tbTindakan.getValueAt(i,10).toString());
                                                pssimpandokter.setString(9,tbTindakan.getValueAt(i,4).toString());
                                                pssimpandokter.executeUpdate();
                                            } catch (Exception e) {
                                                System.out.println("Notifikasi : "+e);
                                            } finally{
                                                if(pssimpandokter != null){
                                                    pssimpandokter.close();
                                                }
                                            }
                                            break;
                                        case "rawat_jl_pr":
                                            pssimpanperawat=koneksi.prepareStatement("insert into rawat_jl_pr values(?,?,?,?,?,?,?,?,?)");
                                            try {
                                                pssimpanperawat.setString(1,TNoRw.getText());
                                                pssimpanperawat.setString(2,tbTindakan.getValueAt(i,1).toString());
                                                pssimpanperawat.setString(3,kddokter.getText());
                                                pssimpanperawat.setString(4,tbTindakan.getValueAt(i,5).toString());  
                                                pssimpanperawat.setString(5,tbTindakan.getValueAt(i,6).toString());  
                                                pssimpanperawat.setString(6,tbTindakan.getValueAt(i,8).toString());
                                                pssimpanperawat.setString(7,tbTindakan.getValueAt(i,9).toString());
                                                pssimpanperawat.setString(8,tbTindakan.getValueAt(i,10).toString());  
                                                pssimpanperawat.setString(9,tbTindakan.getValueAt(i,4).toString());  
                                                pssimpanperawat.executeUpdate();
                                            } catch (Exception e) {
                                                System.out.println("Notifikasi : "+e);
                                            } finally{
                                                if(pssimpanperawat != null){
                                                    pssimpanperawat.close();
                                                }
                                            }
                                            break;
                                        case "rawat_jl_drpr":                                            
                                            if(Nip2.getText().trim().equals("")||NmPetugas2.getText().trim().equals("")){
                                                Valid.textKosong(TCariTindakan,"Data Petugas");
                                            }else{
                                                pssimpandokterperawat=koneksi.prepareStatement("insert into rawat_jl_drpr values(?,?,?,?,?,?,?,?,?,?,?)");
                                                try {
                                                    pssimpandokterperawat.setString(1,TNoRw.getText());
                                                    pssimpandokterperawat.setString(2,tbTindakan.getValueAt(i,1).toString());
                                                    pssimpandokterperawat.setString(3,kddokter.getText());
                                                    pssimpandokterperawat.setString(4,Nip2.getText());
                                                    pssimpandokterperawat.setString(5,tbTindakan.getValueAt(i,5).toString());  
                                                    pssimpandokterperawat.setString(6,tbTindakan.getValueAt(i,6).toString()); 
                                                    pssimpandokterperawat.setString(7,tbTindakan.getValueAt(i,7).toString()); 
                                                    pssimpandokterperawat.setString(8,tbTindakan.getValueAt(i,8).toString()); 
                                                    pssimpandokterperawat.setString(9,tbTindakan.getValueAt(i,9).toString()); 
                                                    pssimpandokterperawat.setString(10,tbTindakan.getValueAt(i,10).toString()); 
                                                    pssimpandokterperawat.setString(11,tbTindakan.getValueAt(i,4).toString()); 
                                                    pssimpandokterperawat.executeUpdate();
                                                } catch (Exception e) {
                                                    System.out.println("Notifikasi : "+e);
                                                } finally{
                                                    if(pssimpandokterperawat != null){
                                                        pssimpandokterperawat.close();
                                                    }
                                                }
                                            }                                                
                                            break;
                                    }                                    
                                } catch (Exception e) {
                                    System.out.println(e);
                                    JOptionPane.showMessageDialog(null,"Maaf, gagal menyimpan tindakan "+tbTindakan.getValueAt(i,2).toString()+". Kemungkinan ada data yang sama dimasukkan sebelumnya...!");
                                }   
                            tbTindakan.setValueAt(false,i,0); 
                        }                           
                    }                      
                }   
                if((!TKeluhan.getText().trim().equals(""))||(!TPemeriksaan.getText().trim().equals(""))||
                        (!TSuhu.getText().trim().equals(""))||(!TTensi.getText().trim().equals(""))||
                        (!TAlergi.getText().trim().equals(""))||(!TTinggi.getText().trim().equals(""))||
                        (!TBerat.getText().trim().equals(""))||(!TRespirasi.getText().trim().equals(""))||
                        (!TNadi.getText().trim().equals(""))||(!TGCS.getText().trim().equals(""))){
                    Sequel.menyimpan("pemeriksaan_ralan","?,?,?,?,?,?,?,?,?,?,?",11,new String[]{
                        TNoRw.getText(),TSuhu.getText(),TTensi.getText(),TNadi.getText(),TRespirasi.getText(),TTinggi.getText(),
                        TBerat.getText(),TGCS.getText(),TKeluhan.getText(),TPemeriksaan.getText(),TAlergi.getText()
                    });
                }
                koneksi.setAutoCommit(true);
                dispose();
            } catch (SQLException ex) {
                System.out.println(ex);                
            }
        }
}//GEN-LAST:event_BtnSimpanTindakanActionPerformed

private void ppBersihkanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBersihkanActionPerformed
            for(i=0;i<tbTindakan.getRowCount();i++){ 
                tbTindakan.setValueAt(false,i,0);
            }
}//GEN-LAST:event_ppBersihkanActionPerformed

private void kddokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kddokterKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            switch (pilihtable) {
                case "rawat_jl_dr":
                    Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",nmdokter,kddokter.getText());
                    break;
                case "rawat_jl_pr":
                    Sequel.cariIsi("select nama from petugas where nip=?",nmdokter,kddokter.getText());
                    break;
                case "rawat_jl_drpr":
                    Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",nmdokter,kddokter.getText());
                    break;
            }
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnDokterActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_DOWN){
            TCariTindakan.requestFocus();
        }else{            
            Valid.pindah(evt,BtnKeluar,BtnSimpanTindakan);
        }
}//GEN-LAST:event_kddokterKeyPressed

private void btnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDokterActionPerformed
        switch (pilihtable) {
            case "rawat_jl_dr":
                dokter.isCek();
                dokter.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
                dokter.setLocationRelativeTo(internalFrame1);
                dokter.setAlwaysOnTop(false);
                dokter.setVisible(true);
                break;
            case "rawat_jl_pr":
                petugas.isCek();
                petugas.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
                petugas.setLocationRelativeTo(internalFrame1);
                petugas.setAlwaysOnTop(false);
                petugas.setVisible(true);
                break;
            case "rawat_jl_drpr":
                dokter.isCek();
                dokter.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
                dokter.setLocationRelativeTo(internalFrame1);
                dokter.setAlwaysOnTop(false);
                dokter.setVisible(true);
                break;      
        }
}//GEN-LAST:event_btnDokterActionPerformed

private void ppDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppDokterActionPerformed
    ppBersihkanActionPerformed(evt);
    FormInput.setPreferredSize(new Dimension(WIDTH, 44));
    pilihtable="rawat_jl_dr";
    jLabel5.setText("Dokter :");
    kddokter.setText("");
    nmdokter.setText("");
    jLabel6.setVisible(false);
    Nip2.setVisible(false);
    NmPetugas2.setVisible(false);
    btnPetugas.setVisible(false);
    tampil();
        
}//GEN-LAST:event_ppDokterActionPerformed

private void ppPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppPetugasActionPerformed
    ppBersihkanActionPerformed(evt);
    FormInput.setPreferredSize(new Dimension(WIDTH, 44));
    pilihtable="rawat_jl_pr";    
    jLabel5.setText("Petugas :");
    kddokter.setText("");
    nmdokter.setText(""); 
    jLabel6.setVisible(false);
    Nip2.setVisible(false);
    NmPetugas2.setVisible(false);
    btnPetugas.setVisible(false);
    tampil();
}//GEN-LAST:event_ppPetugasActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        TCariTindakan.requestFocus();
    }//GEN-LAST:event_formWindowActivated

    private void ppPetugasDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppPetugasDokterActionPerformed
        ppBersihkanActionPerformed(evt);
        FormInput.setPreferredSize(new Dimension(WIDTH, 74));
        pilihtable="rawat_jl_drpr";
        jLabel5.setText(" Dokter :");
        kddokter.setText("");
        nmdokter.setText("");
        Nip2.setText("");
        NmPetugas2.setText("");
        
        jLabel6.setVisible(true);
        Nip2.setVisible(true);
        NmPetugas2.setVisible(true);
        btnPetugas.setVisible(true);
        tampil();
    }//GEN-LAST:event_ppPetugasDokterActionPerformed

    private void Nip2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Nip2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Nip2KeyPressed

    private void btnPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPetugasActionPerformed
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setAlwaysOnTop(false);
        petugas.setVisible(true);
    }//GEN-LAST:event_btnPetugasActionPerformed

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
            DlgCariPerawatanRalan dialog = new DlgCariPerawatanRalan(new javax.swing.JFrame(), true);
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
    private widget.Button BtnAllTindakan;
    private widget.Button BtnCariTindakan;
    private widget.Button BtnKeluar;
    private widget.Button BtnSimpanTindakan;
    private widget.Button BtnTambahTindakan;
    private widget.PanelBiasa FormInput;
    private widget.TextBox Jam;
    private widget.Label LCount;
    private widget.TextBox Nip2;
    private widget.TextBox NmPetugas2;
    private javax.swing.JPopupMenu Popup;
    private widget.ScrollPane Scroll;
    private widget.TextBox TAlergi;
    private widget.TextBox TBerat;
    private widget.TextBox TCariTindakan;
    private widget.TextBox TGCS;
    private widget.TextBox TKeluhan;
    private widget.TextBox TNadi;
    private widget.TextBox TNoRw;
    private widget.TextBox TPemeriksaan;
    private widget.TextBox TRespirasi;
    private widget.TextBox TSuhu;
    private widget.TextBox TTensi;
    private widget.TextBox TTinggi;
    private widget.TextBox Tanggal;
    private widget.Button btnDokter;
    private widget.Button btnPetugas;
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
    private widget.Table tbTindakan;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        try{     
            jml=0;
            for(i=0;i<tbTindakan.getRowCount();i++){
                if(tbTindakan.getValueAt(i,0).toString().equals("true")){
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
            for(i=0;i<tbTindakan.getRowCount();i++){
                if(tbTindakan.getValueAt(i,0).toString().equals("true")){
                    pilih[index]=true;
                    kode[index]=tbTindakan.getValueAt(i,1).toString();
                    nama[index]=tbTindakan.getValueAt(i,2).toString();
                    kategori[index]=tbTindakan.getValueAt(i,3).toString();
                    totaltnd[index]=Double.parseDouble(tbTindakan.getValueAt(i,4).toString());
                    bagianrs[index]=Double.parseDouble(tbTindakan.getValueAt(i,5).toString());
                    bhp[index]=Double.parseDouble(tbTindakan.getValueAt(i,6).toString());
                    jmdokter[index]=Double.parseDouble(tbTindakan.getValueAt(i,7).toString());
                    jmperawat[index]=Double.parseDouble(tbTindakan.getValueAt(i,8).toString());  
                    kso[index]=Double.parseDouble(tbTindakan.getValueAt(i,9).toString());
                    menejemen[index]=Double.parseDouble(tbTindakan.getValueAt(i,10).toString());  
                    index++;
                }
            }       

            Valid.tabelKosong(TabModeTindakan);

            for(i=0;i<jml;i++){
                TabModeTindakan.addRow(new Object[] {
                    pilih[i],kode[i],nama[i],kategori[i],totaltnd[i],bagianrs[i],bhp[i],jmdokter[i],jmperawat[i],kso[i],menejemen[i]
                });
            }
            
            pstindakan=koneksi.prepareStatement("select jns_perawatan.kd_jenis_prw,jns_perawatan.nm_perawatan,kategori_perawatan.nm_kategori,"+
                   "jns_perawatan.total_byrdr,jns_perawatan.total_byrpr,jns_perawatan.total_byrdrpr,jns_perawatan.bhp,jns_perawatan.material,"+
                   "jns_perawatan.tarif_tindakandr,jns_perawatan.tarif_tindakanpr,jns_perawatan.kso,jns_perawatan.menejemen from jns_perawatan inner join kategori_perawatan "+
                   "on jns_perawatan.kd_kategori=kategori_perawatan.kd_kategori  "+
                   "where jns_perawatan.status='1' and (jns_perawatan.kd_pj=? or jns_perawatan.kd_pj='-') and (jns_perawatan.kd_poli=? or jns_perawatan.kd_poli='-') and jns_perawatan.kd_jenis_prw like ? or "+
                    "jns_perawatan.status='1' and (jns_perawatan.kd_pj=? or jns_perawatan.kd_pj='-') and (jns_perawatan.kd_poli=? or jns_perawatan.kd_poli='-') and jns_perawatan.nm_perawatan like ? or "+
                    "jns_perawatan.status='1' and (jns_perawatan.kd_pj=? or jns_perawatan.kd_pj='-') and (jns_perawatan.kd_poli=? or jns_perawatan.kd_poli='-') and kategori_perawatan.nm_kategori like ? order by jns_perawatan.nm_perawatan "); 
            pstindakan2=koneksi.prepareStatement("select jns_perawatan.kd_jenis_prw,jns_perawatan.nm_perawatan,kategori_perawatan.nm_kategori,"+
                   "jns_perawatan.total_byrdr,jns_perawatan.total_byrpr,jns_perawatan.total_byrdrpr,jns_perawatan.bhp,jns_perawatan.material,"+
                   "jns_perawatan.tarif_tindakandr,jns_perawatan.tarif_tindakanpr,jns_perawatan.kso,jns_perawatan.menejemen from jns_perawatan inner join kategori_perawatan "+
                   "on jns_perawatan.kd_kategori=kategori_perawatan.kd_kategori  "+
                   "where jns_perawatan.status='1' and (jns_perawatan.kd_pj=? or jns_perawatan.kd_pj='-') and jns_perawatan.kd_jenis_prw like ? or "+
                    "jns_perawatan.status='1' and (jns_perawatan.kd_pj=? or jns_perawatan.kd_pj='-') and jns_perawatan.nm_perawatan like ? or "+
                    "jns_perawatan.status='1' and (jns_perawatan.kd_pj=? or jns_perawatan.kd_pj='-') and kategori_perawatan.nm_kategori like ? order by jns_perawatan.nm_perawatan ");        
            pstindakan3=koneksi.prepareStatement("select jns_perawatan.kd_jenis_prw,jns_perawatan.nm_perawatan,kategori_perawatan.nm_kategori,"+
                   "jns_perawatan.total_byrdr,jns_perawatan.total_byrpr,jns_perawatan.total_byrdrpr,jns_perawatan.bhp,jns_perawatan.material,"+
                   "jns_perawatan.tarif_tindakandr,jns_perawatan.tarif_tindakanpr,jns_perawatan.kso,jns_perawatan.menejemen from jns_perawatan inner join kategori_perawatan "+
                   "on jns_perawatan.kd_kategori=kategori_perawatan.kd_kategori  "+
                   "where jns_perawatan.status='1' and (jns_perawatan.kd_poli=? or jns_perawatan.kd_poli='-') and jns_perawatan.kd_jenis_prw like ? or "+
                    "jns_perawatan.status='1' and (jns_perawatan.kd_poli=? or jns_perawatan.kd_poli='-') and jns_perawatan.nm_perawatan like ? or "+
                    "jns_perawatan.status='1' and (jns_perawatan.kd_poli=? or jns_perawatan.kd_poli='-') and kategori_perawatan.nm_kategori like ? order by jns_perawatan.nm_perawatan ");     
            pstindakan4=koneksi.prepareStatement("select jns_perawatan.kd_jenis_prw,jns_perawatan.nm_perawatan,kategori_perawatan.nm_kategori,"+
                   "jns_perawatan.total_byrdr,jns_perawatan.total_byrpr,jns_perawatan.total_byrdrpr,jns_perawatan.bhp,jns_perawatan.material,"+
                   "jns_perawatan.tarif_tindakandr,jns_perawatan.tarif_tindakanpr,jns_perawatan.kso,jns_perawatan.menejemen from jns_perawatan inner join kategori_perawatan "+
                   "on jns_perawatan.kd_kategori=kategori_perawatan.kd_kategori  "+
                   "where jns_perawatan.status='1' and jns_perawatan.kd_jenis_prw like ? or "+
                    "jns_perawatan.status='1' and jns_perawatan.nm_perawatan like ? or "+
                    "jns_perawatan.status='1' and kategori_perawatan.nm_kategori like ? order by jns_perawatan.nm_perawatan "); 
            try {
                if(poli_ralan.equals("Yes")&&cara_bayar_ralan.equals("Yes")){
                    pstindakan.setString(1,kd_pj.trim());
                    pstindakan.setString(2,kd_poli.trim());
                    pstindakan.setString(3,"%"+TCariTindakan.getText().trim()+"%");
                    pstindakan.setString(4,kd_pj.trim());
                    pstindakan.setString(5,kd_poli.trim());
                    pstindakan.setString(6,"%"+TCariTindakan.getText().trim()+"%");
                    pstindakan.setString(7,kd_pj.trim());
                    pstindakan.setString(8,kd_poli.trim());
                    pstindakan.setString(9,"%"+TCariTindakan.getText().trim()+"%");
                    rstindakan=pstindakan.executeQuery();
                }else if(poli_ralan.equals("No")&&cara_bayar_ralan.equals("Yes")){
                    pstindakan2.setString(1,kd_pj.trim());
                    pstindakan2.setString(2,"%"+TCariTindakan.getText().trim()+"%");
                    pstindakan2.setString(3,kd_pj.trim());
                    pstindakan2.setString(4,"%"+TCariTindakan.getText().trim()+"%");
                    pstindakan2.setString(5,kd_pj.trim());
                    pstindakan2.setString(6,"%"+TCariTindakan.getText().trim()+"%");
                    rstindakan=pstindakan2.executeQuery();
                }else if(poli_ralan.equals("Yes")&&cara_bayar_ralan.equals("No")){
                    pstindakan3.setString(1,kd_poli.trim());
                    pstindakan3.setString(2,"%"+TCariTindakan.getText().trim()+"%");
                    pstindakan3.setString(3,kd_poli.trim());
                    pstindakan3.setString(4,"%"+TCariTindakan.getText().trim()+"%");
                    pstindakan3.setString(5,kd_poli.trim());
                    pstindakan3.setString(6,"%"+TCariTindakan.getText().trim()+"%");
                    rstindakan=pstindakan3.executeQuery();
                }else if(poli_ralan.equals("No")&&cara_bayar_ralan.equals("No")){
                    pstindakan4.setString(1,"%"+TCariTindakan.getText().trim()+"%");
                    pstindakan4.setString(2,"%"+TCariTindakan.getText().trim()+"%");
                    pstindakan4.setString(3,"%"+TCariTindakan.getText().trim()+"%");
                    rstindakan=pstindakan4.executeQuery();
                }

                switch (pilihtable) {
                    case "rawat_jl_dr":
                        while(rstindakan.next()){
                            if(rstindakan.getDouble("total_byrdr")>0){
                                TabModeTindakan.addRow(new Object[] {
                                    false,rstindakan.getString(1),rstindakan.getString(2),rstindakan.getString(3),
                                    rstindakan.getDouble("total_byrdr"),rstindakan.getDouble("material"),
                                    rstindakan.getDouble("bhp"),rstindakan.getDouble("tarif_tindakandr"),
                                    rstindakan.getDouble("tarif_tindakanpr"),rstindakan.getDouble("kso"),
                                    rstindakan.getDouble("menejemen")
                                });
                            }                        
                        }   
                        break;
                    case "rawat_jl_pr":
                        while(rstindakan.next()){
                            if(rstindakan.getDouble("total_byrpr")>0){
                                TabModeTindakan.addRow(new Object[] {
                                    false,rstindakan.getString(1),rstindakan.getString(2),rstindakan.getString(3),
                                    rstindakan.getDouble("total_byrpr"),rstindakan.getDouble("material"),
                                    rstindakan.getDouble("bhp"),rstindakan.getDouble("tarif_tindakandr"),
                                    rstindakan.getDouble("tarif_tindakanpr"),rstindakan.getDouble("kso"),
                                    rstindakan.getDouble("menejemen")
                                });
                            }                            
                        }   
                        break;
                    case "rawat_jl_drpr":
                        while(rstindakan.next()){
                            if(rstindakan.getDouble("total_byrdrpr")>0){
                                TabModeTindakan.addRow(new Object[] {
                                    false,rstindakan.getString(1),rstindakan.getString(2),rstindakan.getString(3),
                                    rstindakan.getDouble("total_byrdrpr"),rstindakan.getDouble("material"),
                                    rstindakan.getDouble("bhp"),rstindakan.getDouble("tarif_tindakandr"),
                                    rstindakan.getDouble("tarif_tindakanpr"),rstindakan.getDouble("kso"),
                                    rstindakan.getDouble("menejemen")
                                });
                            }                        
                        }   
                        break;
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            }finally{
                if(rstindakan != null){
                    rstindakan.close();
                }
                if(pstindakan != null){
                    pstindakan.close();
                }
                if(pstindakan2 != null){
                    pstindakan2.close();
                }
                if(pstindakan3 != null){
                    pstindakan3.close();
                }
                if(pstindakan4 != null){
                    pstindakan4.close();
                }
            }
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        LCount.setText(""+tbTindakan.getRowCount());
    }

    private void emptTeks() {
        TCariTindakan.setText("");         
    }

    public JTable getTable(){
        return tbTindakan;
    }
    
    
    public void isCek(){
        BtnTambahTindakan.setEnabled(var.gettarif_ralan());
    }
    
    public void setNoRm(String norwt,String kdpetugas,String nmpetugas, String pilihtable,
            String suhu, String tensi,String hasil, String perkembangan,String tanggal, 
            String jam,String kdpetugas2,String nmpetugas2, String berat,String tinggi, 
            String nadi,String respirasi,String gcs,String alergi) {
        
        for(i=0;i<tbTindakan.getRowCount();i++){ 
            tbTindakan.setValueAt(false,i,0);
        }
        TNoRw.setText(norwt);
        kddokter.setText(kdpetugas);
        nmdokter.setText(nmpetugas);
        Nip2.setText(kdpetugas2);
        NmPetugas2.setText(nmpetugas2);
        this.pilihtable=pilihtable;
        this.kd_pj=Sequel.cariIsi("select kd_pj from reg_periksa where no_rawat=?",norwt);
        this.kd_poli=Sequel.cariIsi("select kd_poli from reg_periksa where no_rawat=?",norwt);
        switch (pilihtable) {
            case "rawat_jl_dr":
                FormInput.setPreferredSize(new Dimension(710, 44));
                jLabel5.setText("Dokter :");                
                jLabel6.setVisible(false);
                Nip2.setVisible(false);
                NmPetugas2.setVisible(false);
                btnPetugas.setVisible(false);
                break;
            case "rawat_jl_pr":
                FormInput.setPreferredSize(new Dimension(710, 44));
                
                jLabel6.setVisible(false);
                Nip2.setVisible(false);
                NmPetugas2.setVisible(false);
                btnPetugas.setVisible(false);
                jLabel5.setText("Perawat :");
                break;
            case "rawat_jl_drpr":
                FormInput.setPreferredSize(new Dimension(710, 74));
                jLabel5.setText(" Dokter :");
                
                jLabel6.setVisible(true);
                Nip2.setVisible(true);
                NmPetugas2.setVisible(true);
                btnPetugas.setVisible(true);
                break;
        }
        TSuhu.setText(suhu);
        TTensi.setText(tensi);
        TKeluhan.setText(hasil);
        TPemeriksaan.setText(perkembangan);
        Tanggal.setText(tanggal);
        Jam.setText(jam);
        TBerat.setText(berat);
        TTinggi.setText(tinggi);
        TRespirasi.setText(respirasi);
        TNadi.setText(nadi);
        TGCS.setText(gcs);
        TAlergi.setText(alergi);
        try {
            psset_tarif=koneksi.prepareStatement("select * from set_tarif");
            try {
                rsset_tarif=psset_tarif.executeQuery();
                if(rsset_tarif.next()){
                    poli_ralan=rsset_tarif.getString("poli_ralan");
                    cara_bayar_ralan=rsset_tarif.getString("cara_bayar_ralan");
                }else{
                    poli_ralan="Yes";
                    cara_bayar_ralan="Yes";
                }  
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            }finally{
                if(rsset_tarif != null){
                    rsset_tarif.close();
                }
                if(psset_tarif != null){
                    psset_tarif.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : "+e);
        } 
    }
}

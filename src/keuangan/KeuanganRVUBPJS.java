

package keuangan;

import fungsi.WarnaTable;
import fungsi.WarnaTable2;
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
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariPetugas;

/**
 *
 * @author perpustakaan
 */
public final class KeuanganRVUBPJS extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int row=0,i;
    private String koderekening="";
    private Jurnal jur=new Jurnal();
    private WarnaTable2 warna=new WarnaTable2();
    private String status="";
    private double total=0,sisapiutang=0,cicilan=0,rugi=0,lebih=0,selisih=0,materialralan=0,bhpralan=0,tarif_tindakandrralan=0,tarif_tindakanprralan=0,ksoralan=0,menejemenralan=0,biaya_rawatralan=0,
                   materialranap=0,bhpranap=0,tarif_tindakandrranap=0,tarif_tindakanprranap=0,ksoranap=0,menejemenranap=0,biaya_rawatranap=0,bagian_rslabralan=0,bhplabralan=0,tarif_perujuklabralan=0,
                   tarif_tindakan_dokterlabralan=0,tarif_tindakan_petugaslabralan=0,ksolabralan=0,menejemenlabralan=0,biayalabralan,bagian_rslabranap=0,bhplabranap=0,tarif_perujuklabranap=0,
                   tarif_tindakan_dokterlabranap=0,tarif_tindakan_petugaslabranap=0,ksolabranap=0,menejemenlabranap=0,biayalabranap;
    private boolean sukses=true;
    private DlgCariPetugas petugas=new DlgCariPetugas(null,false);

    /** Creates new form DlgLhtBiaya
     * @param parent
     * @param modal */
    public KeuanganRVUBPJS(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(885,674);

        tabMode=new DefaultTableModel(null,new Object[]{
                "P","No.Rawat/No.tagihan","No.SEP VClaim","Tgl.Piutang","Pasien","Total Piutang","Iur/Ekses",
                "Sudah Dibayar","Sisa Piutang","Tarif InaCBG","Dibayar BPJS","% Bayar","Kerugian","Lebih Bayar"
            }){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if ((colIndex==10)||(colIndex==0)) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class,
                java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbBangsal.setModel(tabMode);
        //tbBangsal.setDefaultRenderer(Object.class, new WarnaTable(jPanel2.getBackground(),tbBangsal.getBackground()));
        tbBangsal.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbBangsal.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 14; i++) {
            TableColumn column = tbBangsal.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(110);
            }else if(i==2){
                column.setPreferredWidth(140);
            }else if(i==3){
                column.setPreferredWidth(65);
            }else if(i==4){
                column.setPreferredWidth(180);
            }else if(i==5){
                column.setPreferredWidth(80);
            }else if(i==6){
                column.setPreferredWidth(70);
            }else if(i==7){
                column.setPreferredWidth(80);
            }else if(i==8){
                column.setPreferredWidth(80);
            }else if(i==9){
                column.setPreferredWidth(80);
            }else if(i==10){
                column.setPreferredWidth(80);
            }else if(i==11){
                column.setPreferredWidth(48);
            }else if(i==12){
                column.setPreferredWidth(75);
            }else if(i==13){
                column.setPreferredWidth(75);
            }
        }
        warna.kolom=10;
        tbBangsal.setDefaultRenderer(Object.class,warna);

        TKd.setDocument(new batasInput((byte)20).getKata(TKd));
        if(koneksiDB.CARICEPAT().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampil();
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampil();
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampil();
                    }
                }
            });
        }  
        
        petugas.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(petugas.getTable().getSelectedRow()!= -1){                   
                    kdptg.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                    nmptg.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                }  
                kdptg.requestFocus();
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

        
        Valid.loadCombo(nama_bayar,"nama_bayar","akun_bayar");

    }
   

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        TKd = new widget.TextBox();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnDetailPiutang = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbBangsal = new widget.Table();
        panelisi4 = new widget.panelisi();
        label32 = new widget.Label();
        Tanggal = new widget.Tanggal();
        jLabel11 = new widget.Label();
        nama_bayar = new widget.ComboBox();
        label19 = new widget.Label();
        kdptg = new widget.TextBox();
        nmptg = new widget.TextBox();
        btnPetugas = new widget.Button();
        jPanel1 = new javax.swing.JPanel();
        panelisi3 = new widget.panelisi();
        label17 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        jLabel13 = new javax.swing.JLabel();
        LCount2 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        LCount3 = new javax.swing.JLabel();
        panelisi1 = new widget.panelisi();
        jLabel10 = new javax.swing.JLabel();
        LCount = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        LCount1 = new javax.swing.JLabel();
        BtnBayar = new widget.Button();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();

        TKd.setForeground(new java.awt.Color(255, 255, 255));
        TKd.setName("TKd"); // NOI18N

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnDetailPiutang.setBackground(new java.awt.Color(255, 255, 254));
        MnDetailPiutang.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnDetailPiutang.setForeground(new java.awt.Color(50, 50, 50));
        MnDetailPiutang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnDetailPiutang.setText("Detail Piutang");
        MnDetailPiutang.setName("MnDetailPiutang"); // NOI18N
        MnDetailPiutang.setPreferredSize(new java.awt.Dimension(200, 28));
        MnDetailPiutang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnDetailPiutangActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnDetailPiutang);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ RVU Piutang BPJS ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setComponentPopupMenu(jPopupMenu1);
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbBangsal.setComponentPopupMenu(jPopupMenu1);
        tbBangsal.setName("tbBangsal"); // NOI18N
        tbBangsal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbBangsalMouseClicked(evt);
            }
        });
        tbBangsal.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tbBangsalPropertyChange(evt);
            }
        });
        tbBangsal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbBangsalKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbBangsal);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        panelisi4.setName("panelisi4"); // NOI18N
        panelisi4.setPreferredSize(new java.awt.Dimension(100, 44));
        panelisi4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label32.setText("Tanggal :");
        label32.setName("label32"); // NOI18N
        label32.setPreferredSize(new java.awt.Dimension(55, 23));
        panelisi4.add(label32);

        Tanggal.setDisplayFormat("dd-MM-yyyy");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.setPreferredSize(new java.awt.Dimension(90, 23));
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeyPressed(evt);
            }
        });
        panelisi4.add(Tanggal);

        jLabel11.setText("Akun Bayar :");
        jLabel11.setName("jLabel11"); // NOI18N
        jLabel11.setPreferredSize(new java.awt.Dimension(73, 23));
        panelisi4.add(jLabel11);

        nama_bayar.setName("nama_bayar"); // NOI18N
        nama_bayar.setPreferredSize(new java.awt.Dimension(170, 23));
        nama_bayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nama_bayarKeyPressed(evt);
            }
        });
        panelisi4.add(nama_bayar);

        label19.setText("Petugas :");
        label19.setName("label19"); // NOI18N
        label19.setPreferredSize(new java.awt.Dimension(58, 23));
        panelisi4.add(label19);

        kdptg.setName("kdptg"); // NOI18N
        kdptg.setPreferredSize(new java.awt.Dimension(100, 23));
        kdptg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdptgKeyPressed(evt);
            }
        });
        panelisi4.add(kdptg);

        nmptg.setEditable(false);
        nmptg.setName("nmptg"); // NOI18N
        nmptg.setPreferredSize(new java.awt.Dimension(160, 23));
        panelisi4.add(nmptg);

        btnPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPetugas.setMnemonic('3');
        btnPetugas.setToolTipText("Alt+3");
        btnPetugas.setName("btnPetugas"); // NOI18N
        btnPetugas.setPreferredSize(new java.awt.Dimension(28, 23));
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
        panelisi4.add(btnPetugas);

        internalFrame1.add(panelisi4, java.awt.BorderLayout.PAGE_START);

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(100, 100));
        jPanel1.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(99, 44));
        panelisi3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        label17.setText("Key Word :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(65, 23));
        panelisi3.add(label17);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(220, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelisi3.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('2');
        BtnCari.setToolTipText("Alt+2");
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
        panelisi3.add(BtnAll);

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(50, 50, 50));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel13.setText("Kerugian :");
        jLabel13.setName("jLabel13"); // NOI18N
        jLabel13.setPreferredSize(new java.awt.Dimension(80, 23));
        panelisi3.add(jLabel13);

        LCount2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        LCount2.setForeground(new java.awt.Color(50, 50, 50));
        LCount2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount2.setText("0");
        LCount2.setName("LCount2"); // NOI18N
        LCount2.setPreferredSize(new java.awt.Dimension(120, 23));
        panelisi3.add(LCount2);

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(50, 50, 50));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel14.setText("Lebih Bayar :");
        jLabel14.setName("jLabel14"); // NOI18N
        jLabel14.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(jLabel14);

        LCount3.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        LCount3.setForeground(new java.awt.Color(50, 50, 50));
        LCount3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount3.setText("0");
        LCount3.setName("LCount3"); // NOI18N
        LCount3.setPreferredSize(new java.awt.Dimension(120, 23));
        panelisi3.add(LCount3);

        jPanel1.add(panelisi3, java.awt.BorderLayout.CENTER);

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(55, 55));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(50, 50, 50));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Belum Dibayar :");
        jLabel10.setName("jLabel10"); // NOI18N
        jLabel10.setPreferredSize(new java.awt.Dimension(87, 23));
        panelisi1.add(jLabel10);

        LCount.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        LCount.setForeground(new java.awt.Color(50, 50, 50));
        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(160, 23));
        panelisi1.add(LCount);

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(50, 50, 50));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("Dipilih :");
        jLabel12.setName("jLabel12"); // NOI18N
        jLabel12.setPreferredSize(new java.awt.Dimension(45, 23));
        panelisi1.add(jLabel12);

        LCount1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        LCount1.setForeground(new java.awt.Color(50, 50, 50));
        LCount1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount1.setText("0");
        LCount1.setName("LCount1"); // NOI18N
        LCount1.setPreferredSize(new java.awt.Dimension(150, 23));
        panelisi1.add(LCount1);

        BtnBayar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnBayar.setMnemonic('B');
        BtnBayar.setText("Bayar");
        BtnBayar.setToolTipText("Alt+B");
        BtnBayar.setName("BtnBayar"); // NOI18N
        BtnBayar.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnBayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBayarActionPerformed(evt);
            }
        });
        BtnBayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnBayarKeyPressed(evt);
            }
        });
        panelisi1.add(BtnBayar);

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
        panelisi1.add(BtnPrint);

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
        panelisi1.add(BtnKeluar);

        jPanel1.add(panelisi1, java.awt.BorderLayout.PAGE_END);

        internalFrame1.add(jPanel1, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            //TCari.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            
            Sequel.queryu("truncate table temporary");
            int row=tabMode.getRowCount();
            for(int i=0;i<row;i++){  
                    Sequel.menyimpan("temporary","'0','"+
                                tabMode.getValueAt(i,0).toString()+"','"+
                                tabMode.getValueAt(i,1).toString()+"','"+
                                tabMode.getValueAt(i,2).toString()+"','"+
                                tabMode.getValueAt(i,3).toString()+"','"+
                                Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(i,4).toString()))+"','"+
                                Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(i,5).toString()))+"','"+
                                Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(i,6).toString()))+"','"+
                                Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(i,7).toString()))+"','"+
                                tabMode.getValueAt(i,8).toString()+"','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Piutang Pasien"); 
            }
            Sequel.menyimpan("temporary","'0','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Rekap Harian Tindakan Dokter"); 
            Sequel.menyimpan("temporary","'0','TOTAL PIUTANG','',':','','','','','"+LCount.getText()+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Rekap Harian Tindakan Dokter"); 
            
            
            Map<String, Object> param = new HashMap<>();                 
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());   
            param.put("logo",Sequel.cariGambar("select logo from setting")); 
            Valid.MyReport("rptRPiutangMasuk.jasper","report","::[ Rekap Piutang Masuk ]::",param);
        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnBayar, BtnAll);
        }
}//GEN-LAST:event_BtnPrintKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnAll,TKd);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        kdptg.setText("");
        nmptg.setText("");
        tampil();

}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnPrint, BtnKeluar);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void tbBangsalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbBangsalKeyPressed
        if(tabMode.getRowCount()!=0){
            if(evt.getKeyCode()==KeyEvent.VK_ENTER){
                getdata();
            }
        }
}//GEN-LAST:event_tbBangsalKeyPressed

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
        tampil();
}//GEN-LAST:event_BtnCariActionPerformed

private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        tampil();
}//GEN-LAST:event_BtnCariKeyPressed

private void MnDetailPiutangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnDetailPiutangActionPerformed
     if(tabMode.getRowCount()==0){
        JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
        TCari.requestFocus();
    }else{
         if(tbBangsal.getSelectedRow()!= -1){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            status=Sequel.cariIsi("select status_lanjut from reg_periksa where no_rawat=?",tbBangsal.getValueAt(tbBangsal.getSelectedRow(),1).toString());   
            if(status.equals("Ralan")){
                DlgBilingRalan billing=new DlgBilingRalan(null,false);
                billing.TNoRw.setText(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),1).toString());
                billing.isCek();
                billing.isRawat();
                if(Sequel.cariInteger("select count(no_rawat) from piutang_pasien where no_rawat=?",billing.TNoRw.getText())>0){
                    billing.setPiutang();
                }
                billing.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                billing.setLocationRelativeTo(internalFrame1);
                billing.setVisible(true); 
            }else if(status.equals("Ranap")){
                DlgBilingRanap billing=new DlgBilingRanap(null,false);
                billing.TNoRw.setText(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),1).toString());            
                billing.isCek();
                billing.isRawat();
                billing.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                billing.setLocationRelativeTo(internalFrame1);
                billing.setVisible(true); 
            }
            this.setCursor(Cursor.getDefaultCursor());
         }else{
             JOptionPane.showMessageDialog(null,"Silahkan pilih data terlebih dahulu...!!");
         }                   
    } 
}//GEN-LAST:event_MnDetailPiutangActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void BtnBayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBayarActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
        }else if(total<=0){
            JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih piutang yang mau dibayar..!!!!");
            TCari.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            Sequel.AutoComitFalse();
            sukses=true;
            koderekening=Sequel.cariIsi("select kd_rek from akun_bayar where nama_bayar=?",nama_bayar.getSelectedItem().toString());
            
            row=tabMode.getRowCount();
            for(i=0;i<row;i++){  
                if(tabMode.getValueAt(i,0).toString().equals("true")){
                    if(Sequel.menyimpantf("bayar_piutang","?,?,?,?,?,?,?","Data",7,new String[]{
                        Valid.SetTgl(Tanggal.getSelectedItem()+""),Sequel.cariIsi("select no_rkm_medis from reg_periksa where no_rawat=?",tabMode.getValueAt(i,1).toString()),
                        tabMode.getValueAt(i,11).toString(),"diverifikasi oleh "+akses.getkode(),tabMode.getValueAt(i,1).toString(),koderekening,kdptg.getText()
                    })==true){
                        sisapiutang=(Sequel.cariIsiAngka("SELECT ifnull(SUM(piutang_pasien.sisapiutang),0) FROM piutang_pasien where piutang_pasien.no_rawat=?",tabMode.getValueAt(i,1).toString())
                            -Sequel.cariIsiAngka("SELECT ifnull(SUM(bayar_piutang.besar_cicilan),0) FROM bayar_piutang where bayar_piutang.no_rawat=?",tabMode.getValueAt(i,1).toString())
                            -Double.parseDouble(tabMode.getValueAt(i,11).toString()));
                        if(sisapiutang<=1){
                            Sequel.mengedit("piutang_pasien","no_rawat='"+tabMode.getValueAt(i,1).toString()+"'","status='Lunas'");
                        }    
                        Sequel.mengedit("detail_piutang_pasien","no_rawat='"+tabMode.getValueAt(i,1).toString()+"' and nama_bayar='"+nmptg.getText()+"'","sisapiutang=sisapiutang-"+tabMode.getValueAt(i,11).toString());
                        Sequel.queryu("delete from tampjurnal");                    
                        Sequel.menyimpan("tampjurnal","'"+kdptg.getText()+"','BAYAR PIUTANG','0','"+tabMode.getValueAt(i,11).toString()+"'","Rekening");    
                        Sequel.menyimpan("tampjurnal","'"+koderekening+"','"+nama_bayar.getSelectedItem()+"','"+tabMode.getValueAt(i,11).toString()+"','0'","Rekening"); 
                        sukses=jur.simpanJurnal(tabMode.getValueAt(i,1).toString(),Valid.SetTgl(Tanggal.getSelectedItem()+""),"U","BAYAR PIUTANG"+", OLEH "+akses.getkode());                   
                    }else{
                        sukses=false;
                    }
                }
            }
            
            if(sukses==true){
                Sequel.Commit();
            }else{
                sukses=false;
                JOptionPane.showMessageDialog(null,"Terjadi kesalahan saat pemrosesan data, transaksi dibatalkan.\nPeriksa kembali data sebelum melanjutkan menyimpan..!!");
                Sequel.RollBack();
            }
            Sequel.AutoComitTrue();
            
            if(sukses==true){
                tampil();
            }
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnBayarActionPerformed

    private void BtnBayarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBayarKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnBayarKeyPressed

    private void nama_bayarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nama_bayarKeyPressed
        Valid.pindah(evt,Tanggal,TCari);
    }//GEN-LAST:event_nama_bayarKeyPressed

    private void TanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKeyPressed
        Valid.pindah(evt,kdptg,nama_bayar);
    }//GEN-LAST:event_TanggalKeyPressed

    private void tbBangsalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbBangsalMouseClicked
        if(tabMode.getRowCount()!=0){
            if(tbBangsal.getSelectedColumn()==0){
                getdata();
            }
        }
    }//GEN-LAST:event_tbBangsalMouseClicked

    private void tbBangsalPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tbBangsalPropertyChange
        if(this.isVisible()==true){
            try {
                if(tbBangsal.getSelectedRow()!= -1){
                    tbBangsal.setValueAt(((Valid.SetAngka(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),10).toString())/Valid.SetAngka(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),9).toString()))*100),tbBangsal.getSelectedRow(),11);
                    selisih=Valid.SetAngka(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),10).toString())-Valid.SetAngka(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),9).toString());
                    if(selisih<=0){
                        tbBangsal.setValueAt((selisih* -1),tbBangsal.getSelectedRow(),12);
                        tbBangsal.setValueAt(0,tbBangsal.getSelectedRow(),13);
                    }else{
                        tbBangsal.setValueAt(0,tbBangsal.getSelectedRow(),12);
                        tbBangsal.setValueAt(selisih,tbBangsal.getSelectedRow(),13);
                    }
                    
                    materialralan=0;bhpralan=0;tarif_tindakandrralan=0;tarif_tindakanprralan=0;ksoralan=0;menejemenralan=0;biaya_rawatralan=0;
                    materialranap=0;bhpranap=0;tarif_tindakandrranap=0;tarif_tindakanprranap=0;ksoranap=0;menejemenranap=0;biaya_rawatranap=0;
                    bagian_rslabralan=0;bhplabralan=0;tarif_perujuklabralan=0;tarif_tindakan_dokterlabralan=0;tarif_tindakan_petugaslabralan=0;ksolabralan=0;menejemenlabralan=0;biayalabralan=0;
                    bagian_rslabranap=0;bhplabranap=0;tarif_perujuklabranap=0;tarif_tindakan_dokterlabranap=0;tarif_tindakan_petugaslabranap=0;ksolabranap=0;menejemenlabranap=0;biayalabranap=0;
                    
                    //cek rawat jalan
                    ps=koneksi.prepareStatement(
                            "select sum(material) as material,sum(bhp) as bhp,sum(tarif_tindakandr) as tarif_tindakandr,sum(tarif_tindakanpr) as tarif_tindakanpr,"+
                            "sum(kso) as kso,sum(menejemen) as menejemen,sum(biaya_rawat) as biaya_rawat from rawat_jl_drpr where no_rawat=?");
                    try {
                        ps.setString(1,tbBangsal.getValueAt(tbBangsal.getSelectedRow(),1).toString());
                        rs=ps.executeQuery();
                        if(rs.next()){
                            materialralan=rs.getDouble("material");
                            bhpralan=rs.getDouble("bhp");
                            tarif_tindakandrralan=rs.getDouble("tarif_tindakandr");
                            tarif_tindakanprralan=rs.getDouble("tarif_tindakanpr");
                            ksoralan=rs.getDouble("kso");
                            menejemenralan=rs.getDouble("menejemen");
                            biaya_rawatralan=rs.getDouble("biaya_rawat");
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
                    
                    ps=koneksi.prepareStatement(
                            "select sum(material) as material,sum(bhp) as bhp,sum(tarif_tindakandr) as tarif_tindakandr,"+
                            "sum(kso) as kso,sum(menejemen) as menejemen,sum(biaya_rawat) as biaya_rawat from rawat_jl_dr where no_rawat=?");
                    try {
                        ps.setString(1,tbBangsal.getValueAt(tbBangsal.getSelectedRow(),1).toString());
                        rs=ps.executeQuery();
                        if(rs.next()){
                            materialralan=+materialralan+rs.getDouble("material");
                            bhpralan=bhpralan+rs.getDouble("bhp");
                            tarif_tindakandrralan=tarif_tindakandrralan+rs.getDouble("tarif_tindakandr");
                            ksoralan=ksoralan+rs.getDouble("kso");
                            menejemenralan=menejemenralan+rs.getDouble("menejemen");
                            biaya_rawatralan=biaya_rawatralan+rs.getDouble("biaya_rawat");
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
                    
                    ps=koneksi.prepareStatement(
                            "select sum(material) as material,sum(bhp) as bhp,sum(tarif_tindakanpr) as tarif_tindakanpr,"+
                            "sum(kso) as kso,sum(menejemen) as menejemen,sum(biaya_rawat) as biaya_rawat from rawat_jl_pr where no_rawat=?");
                    try {
                        ps.setString(1,tbBangsal.getValueAt(tbBangsal.getSelectedRow(),1).toString());
                        rs=ps.executeQuery();
                        if(rs.next()){
                            materialralan=+materialralan+rs.getDouble("material");
                            bhpralan=bhpralan+rs.getDouble("bhp");
                            tarif_tindakanprralan=tarif_tindakanprralan+rs.getDouble("tarif_tindakanpr");
                            ksoralan=ksoralan+rs.getDouble("kso");
                            menejemenralan=menejemenralan+rs.getDouble("menejemen");
                            biaya_rawatralan=biaya_rawatralan+rs.getDouble("biaya_rawat");
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
                    
                    //cek rawat inap
                    ps=koneksi.prepareStatement(
                            "select sum(material) as material,sum(bhp) as bhp,sum(tarif_tindakandr) as tarif_tindakandr,sum(tarif_tindakanpr) as tarif_tindakanpr,"+
                            "sum(kso) as kso,sum(menejemen) as menejemen,sum(biaya_rawat) as biaya_rawat from rawat_inap_drpr where no_rawat=?");
                    try {
                        ps.setString(1,tbBangsal.getValueAt(tbBangsal.getSelectedRow(),1).toString());
                        rs=ps.executeQuery();
                        if(rs.next()){
                            materialranap=rs.getDouble("material");
                            bhpranap=rs.getDouble("bhp");
                            tarif_tindakandrranap=rs.getDouble("tarif_tindakandr");
                            tarif_tindakanprranap=rs.getDouble("tarif_tindakanpr");
                            ksoranap=rs.getDouble("kso");
                            menejemenranap=rs.getDouble("menejemen");
                            biaya_rawatranap=rs.getDouble("biaya_rawat");
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
                    
                    ps=koneksi.prepareStatement(
                            "select sum(material) as material,sum(bhp) as bhp,sum(tarif_tindakandr) as tarif_tindakandr,"+
                            "sum(kso) as kso,sum(menejemen) as menejemen,sum(biaya_rawat) as biaya_rawat from rawat_inap_dr where no_rawat=?");
                    try {
                        ps.setString(1,tbBangsal.getValueAt(tbBangsal.getSelectedRow(),1).toString());
                        rs=ps.executeQuery();
                        if(rs.next()){
                            materialranap=+materialranap+rs.getDouble("material");
                            bhpranap=bhpranap+rs.getDouble("bhp");
                            tarif_tindakandrranap=tarif_tindakandrranap+rs.getDouble("tarif_tindakandr");
                            ksoranap=ksoranap+rs.getDouble("kso");
                            menejemenranap=menejemenranap+rs.getDouble("menejemen");
                            biaya_rawatranap=biaya_rawatranap+rs.getDouble("biaya_rawat");
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
                    
                    ps=koneksi.prepareStatement(
                            "select sum(material) as material,sum(bhp) as bhp,sum(tarif_tindakanpr) as tarif_tindakanpr,"+
                            "sum(kso) as kso,sum(menejemen) as menejemen,sum(biaya_rawat) as biaya_rawat from rawat_inap_pr where no_rawat=?");
                    try {
                        ps.setString(1,tbBangsal.getValueAt(tbBangsal.getSelectedRow(),1).toString());
                        rs=ps.executeQuery();
                        if(rs.next()){
                            materialranap=+materialranap+rs.getDouble("material");
                            bhpranap=bhpranap+rs.getDouble("bhp");
                            tarif_tindakanprranap=tarif_tindakanprranap+rs.getDouble("tarif_tindakanpr");
                            ksoranap=ksoranap+rs.getDouble("kso");
                            menejemenranap=menejemenranap+rs.getDouble("menejemen");
                            biaya_rawatranap=biaya_rawatranap+rs.getDouble("biaya_rawat");
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
                    
                    //cek lab ralan
                    ps=koneksi.prepareStatement(
                            "select sum(bagian_rs) as bagian_rs,sum(bhp) as bhp,sum(tarif_perujuk) as tarif_perujuk,sum(tarif_tindakan_dokter) as tarif_tindakan_dokter,"+
                            "sum(tarif_tindakan_petugas) as tarif_tindakan_petugas,sum(kso) as kso,sum(menejemen) as menejemen,sum(biaya) as biaya from periksa_lab "+
                            "where no_rawat=? and status='Ralan'");
                    try {
                        ps.setString(1,tbBangsal.getValueAt(tbBangsal.getSelectedRow(),1).toString());
                        rs=ps.executeQuery();
                        if(rs.next()){
                            bagian_rslabralan=0;
                            bhplabralan=0;
                            tarif_perujuklabralan=0;
                            tarif_tindakan_dokterlabralan=0;
                            tarif_tindakan_petugaslabralan=0;
                            ksolabralan=0;
                            menejemenlabralan=0;
                            biayalabralan=0;
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
                }  
            } catch (Exception e) {
            }
            row=tbBangsal.getRowCount();
            total=0;
            lebih=0;
            rugi=0;
            for(i=0;i<row;i++){  
                if(tbBangsal.getValueAt(i,0).toString().equals("true")){
                    try {
                        total=total+Valid.SetAngka(tbBangsal.getValueAt(i,10).toString()); 
                    } catch (Exception e) {
                    }

                    try {
                       rugi=rugi+Valid.SetAngka(tbBangsal.getValueAt(i,12).toString());
                    } catch (Exception e) {
                    }

                    try {
                       lebih=lebih+Valid.SetAngka(tbBangsal.getValueAt(i,13).toString());
                    } catch (Exception e) {
                    }
                }
            }
            LCount1.setText(Valid.SetAngka(total));
            LCount2.setText(Valid.SetAngka(rugi));
            LCount3.setText(Valid.SetAngka(lebih));
        }
    }//GEN-LAST:event_tbBangsalPropertyChange

    private void btnPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPetugasKeyPressed
        //Valid.pindah(evt,DTPCari2,TCari);
    }//GEN-LAST:event_btnPetugasKeyPressed

    private void btnPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPetugasActionPerformed
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setAlwaysOnTop(false);
        petugas.setVisible(true);
    }//GEN-LAST:event_btnPetugasActionPerformed

    private void kdptgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdptgKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama from petugas where nip=?", nmptg,kdptg.getText());           
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select nama from petugas where nip=?", nmptg,kdptg.getText()); 
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select nama from petugas where nip=?", nmptg,kdptg.getText()); 
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnPetugasActionPerformed(null);
        }
    }//GEN-LAST:event_kdptgKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            KeuanganRVUBPJS dialog = new KeuanganRVUBPJS(new javax.swing.JFrame(), true);
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
    private widget.Button BtnBayar;
    private widget.Button BtnCari;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private javax.swing.JLabel LCount;
    private javax.swing.JLabel LCount1;
    private javax.swing.JLabel LCount2;
    private javax.swing.JLabel LCount3;
    private javax.swing.JMenuItem MnDetailPiutang;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.TextBox TKd;
    private widget.Tanggal Tanggal;
    private widget.Button btnPetugas;
    private widget.InternalFrame internalFrame1;
    private javax.swing.JLabel jLabel10;
    private widget.Label jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.TextBox kdptg;
    private widget.Label label17;
    private widget.Label label19;
    private widget.Label label32;
    private widget.ComboBox nama_bayar;
    private widget.TextBox nmptg;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi3;
    private widget.panelisi panelisi4;
    private widget.Table tbBangsal;
    // End of variables declaration//GEN-END:variables

    public void tampil(){
        Valid.tabelKosong(tabMode);
        try{
            sisapiutang=0;
            ps=koneksi.prepareStatement(
                   "select piutang_pasien.no_rawat,piutang_pasien.tgl_piutang, concat(piutang_pasien.no_rkm_medis,' ',pasien.nm_pasien) as namapasien, "+
                   "piutang_pasien.totalpiutang,piutang_pasien.uangmuka,piutang_pasien.sisapiutang,bridging_sep.no_sep,inacbg_grouping_stage1.tarif "+
                   "from piutang_pasien inner join pasien on piutang_pasien.no_rkm_medis=pasien.no_rkm_medis "+
                   "inner join reg_periksa on piutang_pasien.no_rawat=reg_periksa.no_rawat "+
                   "inner join bridging_sep on bridging_sep.no_rawat=reg_periksa.no_rawat "+
                   "inner join inacbg_grouping_stage1 on bridging_sep.no_sep=inacbg_grouping_stage1.no_sep "+
                   "where piutang_pasien.status='Belum Lunas' "+
                   (TCari.getText().trim().equals("")?"":"and (piutang_pasien.no_rawat like ? or piutang_pasien.no_rkm_medis like ? "+
                   "or pasien.nm_pasien like ? or bridging_sep.no_sep like ?)")+" order by piutang_pasien.tgl_piutang");
            try {
                if(!TCari.getText().equals("")){
                    ps.setString(1,"%"+TCari.getText()+"%");
                    ps.setString(2,"%"+TCari.getText()+"%");
                    ps.setString(3,"%"+TCari.getText()+"%");
                    ps.setString(4,"%"+TCari.getText()+"%");
                }
                    
                rs=ps.executeQuery();
                while(rs.next()){
                    cicilan=Sequel.cariIsiAngka("SELECT ifnull(SUM(bayar_piutang.besar_cicilan),0) FROM bayar_piutang where bayar_piutang.no_rawat=?",rs.getString("no_rawat"));
                    tabMode.addRow(new Object[]{
                        false,rs.getString("no_rawat"),rs.getString("no_sep"),rs.getString("tgl_piutang"),rs.getString("namapasien"),
                        rs.getDouble("totalpiutang"),rs.getDouble("uangmuka"),cicilan,(rs.getDouble("sisapiutang")-cicilan),
                        rs.getDouble("tarif"),null,0,0,0
                    });
                    sisapiutang=sisapiutang+rs.getDouble("sisapiutang")-cicilan;
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
            
            ps=koneksi.prepareStatement(
                   "select piutang_pasien.no_rawat,piutang_pasien.tgl_piutang, concat(piutang_pasien.no_rkm_medis,' ',pasien.nm_pasien) as namapasien, "+
                   "piutang_pasien.totalpiutang,piutang_pasien.uangmuka,piutang_pasien.sisapiutang,inacbg_klaim_baru2.no_sep,inacbg_grouping_stage12.tarif "+
                   "from piutang_pasien inner join pasien on piutang_pasien.no_rkm_medis=pasien.no_rkm_medis "+
                   "inner join reg_periksa on piutang_pasien.no_rawat=reg_periksa.no_rawat "+
                   "inner join inacbg_klaim_baru2 on inacbg_klaim_baru2.no_rawat=reg_periksa.no_rawat "+
                   "inner join inacbg_grouping_stage12 on inacbg_klaim_baru2.no_sep=inacbg_grouping_stage12.no_sep "+
                   "where piutang_pasien.status='Belum Lunas' "+
                   (TCari.getText().trim().equals("")?"":"and (piutang_pasien.no_rawat like ? or piutang_pasien.no_rkm_medis like ? "+
                   "or pasien.nm_pasien like ? or bridging_sep.no_sep like ?)")+" order by piutang_pasien.tgl_piutang");
            try {
                if(!TCari.getText().equals("")){
                    ps.setString(1,"%"+TCari.getText()+"%");
                    ps.setString(2,"%"+TCari.getText()+"%");
                    ps.setString(3,"%"+TCari.getText()+"%");
                    ps.setString(4,"%"+TCari.getText()+"%");
                }
                    
                rs=ps.executeQuery();
                while(rs.next()){
                    cicilan=Sequel.cariIsiAngka("SELECT ifnull(SUM(bayar_piutang.besar_cicilan),0) FROM bayar_piutang where bayar_piutang.no_rawat=?",rs.getString("no_rawat"));
                    tabMode.addRow(new Object[]{
                        false,rs.getString("no_rawat"),rs.getString("no_sep"),rs.getString("tgl_piutang"),rs.getString("namapasien"),
                        rs.getDouble("totalpiutang"),rs.getDouble("uangmuka"),cicilan,(rs.getDouble("sisapiutang")-cicilan),
                        rs.getDouble("tarif"),null,0,0,0
                    });
                    sisapiutang=sisapiutang+rs.getDouble("sisapiutang")-cicilan;
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
            LCount.setText(Valid.SetAngka(sisapiutang));
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }
    
    private void getdata() {
        try {
            if(tbBangsal.getSelectedRow()!= -1){
                if(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),0).toString().equals("true")){
                    tbBangsal.setValueAt(Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),9).toString()),tbBangsal.getSelectedRow(),10);
                }else if(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),0).toString().equals("false")){
                    tbBangsal.setValueAt(null,tbBangsal.getSelectedRow(),10);
                    tbBangsal.setValueAt(0,tbBangsal.getSelectedRow(),11);
                }
                tbBangsal.setValueAt(((Valid.SetAngka(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),10).toString())/Valid.SetAngka(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),9).toString()))*100),tbBangsal.getSelectedRow(),11);
                tbBangsal.setValueAt(0,tbBangsal.getSelectedRow(),12);
                tbBangsal.setValueAt(0,tbBangsal.getSelectedRow(),13);
            }  
        } catch (Exception e) {
        }
        row=tbBangsal.getRowCount();
        total=0;
        lebih=0;
        rugi=0;
        for(i=0;i<row;i++){  
            if(tbBangsal.getValueAt(i,0).toString().equals("true")){
                 try {
                     total=total+Valid.SetAngka(tbBangsal.getValueAt(i,10).toString()); 
                 } catch (Exception e) {
                 }
                  
                 try {
                    rugi=rugi+Valid.SetAngka(tbBangsal.getValueAt(i,12).toString());
                 } catch (Exception e) {
                 }
                 
                 try {
                    lebih=lebih+Valid.SetAngka(tbBangsal.getValueAt(i,13).toString());
                 } catch (Exception e) {
                 } 
            }
        }
        LCount1.setText(Valid.SetAngka(total));
        LCount2.setText(Valid.SetAngka(rugi));
        LCount3.setText(Valid.SetAngka(lebih));
    }
    
    public void isCek(){
        TCari.requestFocus();
        if(akses.getjml2()>=1){
            kdptg.setEditable(false);
            BtnBayar.setEnabled(akses.getbayar_piutang());
            kdptg.setText(akses.getkode());
            Sequel.cariIsi("select nama from petugas where nip=?", nmptg,kdptg.getText());
        } 
    }
}

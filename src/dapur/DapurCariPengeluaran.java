package dapur;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import keuangan.Jurnal;
import kepegawaian.DlgCariPetugas;

public class DapurCariPengeluaran extends javax.swing.JDialog {
    private final DefaultTableModel tabMode,tabMode2;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private riwayatdapur Trackbarang=new riwayatdapur();
    private PreparedStatement ps,ps2,psdetailpengeluaran;
    private Jurnal jur=new Jurnal();
    private ResultSet rs,rs2;
    private double tagihan=0,ttltagihan=0,total=0;
    private int i=0;
    private boolean sukses=false;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private volatile boolean ceksukses = false;

    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public DapurCariPengeluaran(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        tabMode=new DefaultTableModel(null,new Object[]{"Tgl.Keluar","No.Keluar","Keterangan","Petugas","Barang","Satuan","Jml","Harga(Rp)","Total(Rp)"}){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbDokter.setModel(tabMode);

        tbDokter.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 9; i++) {
            TableColumn column = tbDokter.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(70);
            }else if(i==1){
                column.setPreferredWidth(110);
            }else if(i==2){
                column.setPreferredWidth(150);
            }else if(i==3){
                column.setPreferredWidth(180);
            }else if(i==4){
                column.setPreferredWidth(260);
            }else if(i==5){
                column.setPreferredWidth(80);
            }else if(i==6){
                column.setPreferredWidth(40);
            }else if(i==7){
                column.setPreferredWidth(90);
            }else if(i==8){
                column.setPreferredWidth(100);
            }
        }
        tbDokter.setDefaultRenderer(Object.class, new WarnaTable());

        tabMode2=new DefaultTableModel(null,new Object[]{
                "No.Keluar","Tanggal","NIP","Petugas","Keterangan","Kode Barang",
                "Nama Barang","Satuan","Jml","Harga(Rp)","Total(Rp)"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
             Class[] types = new Class[] {
                java.lang.String.class,java.lang.String.class,java.lang.String.class,
                java.lang.String.class,java.lang.String.class,java.lang.String.class,
                java.lang.String.class,java.lang.String.class,java.lang.Double.class,
                java.lang.Double.class,java.lang.Double.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbDokter2.setModel(tabMode2);

        tbDokter2.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbDokter2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 11; i++) {
            TableColumn column = tbDokter2.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(100);
            }else if(i==1){
                column.setPreferredWidth(70);
            }else if(i==2){
                column.setPreferredWidth(90);
            }else if(i==3){
                column.setPreferredWidth(160);
            }else if(i==4){
                column.setPreferredWidth(130);
            }else if(i==5){
                column.setPreferredWidth(80);
            }else if(i==6){
                column.setPreferredWidth(160);
            }else if(i==7){
                column.setPreferredWidth(60);
            }else if(i==8){
                column.setPreferredWidth(30);
            }else if(i==9){
                column.setPreferredWidth(90);
            }else if(i==10){
                column.setPreferredWidth(105);
            }
        }
        tbDokter2.setDefaultRenderer(Object.class, new WarnaTable());
        
        NoKeluar.setDocument(new batasInput((byte)15).getKata(NoKeluar));
        kdptg.setDocument(new batasInput((byte)25).getKata(kdptg));
        kdbar.setDocument(new batasInput((byte)15).getKata(kdbar));
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));  
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
        ppHapus = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        jPanel1 = new javax.swing.JPanel();
        panelisi1 = new widget.panelisi();
        label10 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        label9 = new widget.Label();
        LTotal = new widget.Label();
        BtnAll = new widget.Button();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();
        panelisi4 = new widget.panelisi();
        kdbar = new widget.TextBox();
        nmbar = new widget.TextBox();
        btnBarang = new widget.Button();
        label17 = new widget.Label();
        jLabel9 = new widget.Label();
        Jenis = new widget.ComboBox();
        panelisi3 = new widget.panelisi();
        label15 = new widget.Label();
        NoKeluar = new widget.TextBox();
        label11 = new widget.Label();
        TglBeli1 = new widget.Tanggal();
        label13 = new widget.Label();
        kdptg = new widget.TextBox();
        nmptg = new widget.TextBox();
        btnPetugas = new widget.Button();
        label12 = new widget.Label();
        TglBeli2 = new widget.Tanggal();
        TabRawat = new javax.swing.JTabbedPane();
        scrollPane1 = new widget.ScrollPane();
        tbDokter = new widget.Table();
        scrollPane2 = new widget.ScrollPane();
        tbDokter2 = new widget.Table();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        ppHapus.setBackground(new java.awt.Color(255, 255, 254));
        ppHapus.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppHapus.setForeground(new java.awt.Color(50, 50, 50));
        ppHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppHapus.setText("Hapus Data Stok Keluar");
        ppHapus.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppHapus.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppHapus.setName("ppHapus"); // NOI18N
        ppHapus.setPreferredSize(new java.awt.Dimension(200, 25));
        ppHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppHapusActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppHapus);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Cari Stok Keluar Barang Dapur Kering & Basah ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(816, 100));
        jPanel1.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 56));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label10.setText("Key Word :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi1.add(label10);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(170, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelisi1.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('5');
        BtnCari.setToolTipText("Alt+5");
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
        panelisi1.add(BtnCari);

        label9.setText("Total :");
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(45, 30));
        panelisi1.add(label9);

        LTotal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LTotal.setText("0");
        LTotal.setName("LTotal"); // NOI18N
        LTotal.setPreferredSize(new java.awt.Dimension(120, 30));
        panelisi1.add(LTotal);

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
        panelisi1.add(BtnAll);

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

        panelisi4.setName("panelisi4"); // NOI18N
        panelisi4.setPreferredSize(new java.awt.Dimension(100, 44));
        panelisi4.setLayout(null);

        kdbar.setName("kdbar"); // NOI18N
        kdbar.setPreferredSize(new java.awt.Dimension(80, 23));
        kdbar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdbarKeyPressed(evt);
            }
        });
        panelisi4.add(kdbar);
        kdbar.setBounds(389, 10, 110, 23);

        nmbar.setEditable(false);
        nmbar.setName("nmbar"); // NOI18N
        nmbar.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi4.add(nmbar);
        nmbar.setBounds(501, 10, 230, 23);

        btnBarang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnBarang.setMnemonic('4');
        btnBarang.setToolTipText("Alt+4");
        btnBarang.setName("btnBarang"); // NOI18N
        btnBarang.setPreferredSize(new java.awt.Dimension(28, 23));
        btnBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBarangActionPerformed(evt);
            }
        });
        panelisi4.add(btnBarang);
        btnBarang.setBounds(734, 10, 28, 23);

        label17.setText("Barang :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(65, 23));
        panelisi4.add(label17);
        label17.setBounds(325, 10, 60, 23);

        jLabel9.setText("Jenis Barang :");
        jLabel9.setName("jLabel9"); // NOI18N
        panelisi4.add(jLabel9);
        jLabel9.setBounds(0, 10, 90, 23);

        Jenis.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Semua", "Kering", "Basah" }));
        Jenis.setName("Jenis"); // NOI18N
        panelisi4.add(Jenis);
        Jenis.setBounds(94, 10, 120, 23);

        jPanel1.add(panelisi4, java.awt.BorderLayout.CENTER);

        internalFrame1.add(jPanel1, java.awt.BorderLayout.PAGE_END);

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(100, 73));
        panelisi3.setLayout(null);

        label15.setText("No.Keluar :");
        label15.setName("label15"); // NOI18N
        label15.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(label15);
        label15.setBounds(0, 10, 80, 23);

        NoKeluar.setName("NoKeluar"); // NOI18N
        NoKeluar.setPreferredSize(new java.awt.Dimension(207, 23));
        NoKeluar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoKeluarKeyPressed(evt);
            }
        });
        panelisi3.add(NoKeluar);
        NoKeluar.setBounds(84, 10, 219, 23);

        label11.setText("Tgl.Keluar :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label11);
        label11.setBounds(0, 40, 80, 23);

        TglBeli1.setDisplayFormat("dd-MM-yyyy");
        TglBeli1.setName("TglBeli1"); // NOI18N
        TglBeli1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglBeli1KeyPressed(evt);
            }
        });
        panelisi3.add(TglBeli1);
        TglBeli1.setBounds(84, 40, 95, 23);

        label13.setText("Petugas :");
        label13.setName("label13"); // NOI18N
        label13.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label13);
        label13.setBounds(305, 10, 80, 23);

        kdptg.setName("kdptg"); // NOI18N
        kdptg.setPreferredSize(new java.awt.Dimension(80, 23));
        kdptg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdptgKeyPressed(evt);
            }
        });
        panelisi3.add(kdptg);
        kdptg.setBounds(389, 10, 80, 23);

        nmptg.setEditable(false);
        nmptg.setName("nmptg"); // NOI18N
        nmptg.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(nmptg);
        nmptg.setBounds(471, 10, 260, 23);

        btnPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPetugas.setMnemonic('2');
        btnPetugas.setToolTipText("Alt+2");
        btnPetugas.setName("btnPetugas"); // NOI18N
        btnPetugas.setPreferredSize(new java.awt.Dimension(28, 23));
        btnPetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPetugasActionPerformed(evt);
            }
        });
        panelisi3.add(btnPetugas);
        btnPetugas.setBounds(734, 10, 28, 23);

        label12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label12.setText("s.d.");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label12);
        label12.setBounds(179, 40, 27, 23);

        TglBeli2.setDisplayFormat("dd-MM-yyyy");
        TglBeli2.setName("TglBeli2"); // NOI18N
        TglBeli2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglBeli2KeyPressed(evt);
            }
        });
        panelisi3.add(TglBeli2);
        TglBeli2.setBounds(208, 40, 95, 23);

        internalFrame1.add(panelisi3, java.awt.BorderLayout.PAGE_START);

        TabRawat.setBackground(new java.awt.Color(255, 255, 253));
        TabRawat.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        TabRawat.setForeground(new java.awt.Color(50, 50, 50));
        TabRawat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabRawat.setName("TabRawat"); // NOI18N
        TabRawat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatMouseClicked(evt);
            }
        });

        scrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        scrollPane1.setComponentPopupMenu(jPopupMenu1);
        scrollPane1.setName("scrollPane1"); // NOI18N
        scrollPane1.setOpaque(true);

        tbDokter.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbDokter.setComponentPopupMenu(jPopupMenu1);
        tbDokter.setName("tbDokter"); // NOI18N
        scrollPane1.setViewportView(tbDokter);

        TabRawat.addTab("Stok Keluar", scrollPane1);

        scrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        scrollPane2.setComponentPopupMenu(jPopupMenu1);
        scrollPane2.setName("scrollPane2"); // NOI18N
        scrollPane2.setOpaque(true);

        tbDokter2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbDokter2.setComponentPopupMenu(jPopupMenu1);
        tbDokter2.setName("tbDokter2"); // NOI18N
        scrollPane2.setViewportView(tbDokter2);

        TabRawat.addTab("Detail Stok Keluar", scrollPane2);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
            dispose();  
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){            
            dispose();              
        }else{Valid.pindah(evt,BtnPrint,kdbar);}
}//GEN-LAST:event_BtnKeluarKeyPressed
/*
private void KdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdKeyPressed
    Valid.pindah(evt,BtnCari,Nm);
}//GEN-LAST:event_TKdKeyPressed
*/

    private void btnPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPetugasActionPerformed
        DlgCariPetugas petugas=new DlgCariPetugas(null,false);
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
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setAlwaysOnTop(false);
        petugas.setVisible(true);
    }//GEN-LAST:event_btnPetugasActionPerformed

    private void TglBeli1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglBeli1KeyPressed
        Valid.pindah(evt,NoKeluar,TglBeli2);
    }//GEN-LAST:event_TglBeli1KeyPressed

    private void btnBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBarangActionPerformed
        DapurBarang barang=new DapurBarang(null,false);
        barang.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(barang.getTable().getSelectedRow()!= -1){                   
                    kdbar.setText(barang.getTable().getValueAt(barang.getTable().getSelectedRow(),0).toString());                    
                    nmbar.setText(barang.getTable().getValueAt(barang.getTable().getSelectedRow(),1).toString());
                }   
                kdbar.requestFocus();
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
        
        barang.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    barang.dispose();
                } 
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        }); 
        barang.emptTeks();
        barang.isCek();
        barang.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        barang.setLocationRelativeTo(internalFrame1);
        barang.setAlwaysOnTop(false);
        barang.setVisible(true);
    }//GEN-LAST:event_btnBarangActionPerformed

    private void NoKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoKeluarKeyPressed
        Valid.pindah(evt, BtnKeluar, TglBeli1);
    }//GEN-LAST:event_NoKeluarKeyPressed

    private void kdptgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdptgKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            nmptg.setText(Sequel.CariPetugas(kdptg.getText()));   
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            nmptg.setText(Sequel.CariPetugas(kdptg.getText()));
            TglBeli2.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            nmptg.setText(Sequel.CariPetugas(kdptg.getText()));
            kdbar.requestFocus();       
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnPetugasActionPerformed(null);
        }
    }//GEN-LAST:event_kdptgKeyPressed

    private void kdbarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdbarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select dapurbarang.nama_brng from dapurbarang where dapurbarang.kode_brng=?", nmbar,kdbar.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){            
            Sequel.cariIsi("select dapurbarang.nama_brng from dapurbarang where dapurbarang.kode_brng=?", nmbar,kdbar.getText());
            Jenis.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){            
            Sequel.cariIsi("select dapurbarang.nama_brng from dapurbarang where dapurbarang.kode_brng=?", nmbar,kdbar.getText());
            TCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnBarangActionPerformed(null);
        }
    }//GEN-LAST:event_kdbarKeyPressed

    private void TglBeli2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglBeli2KeyPressed
        Valid.pindah(evt, TglBeli1, kdptg);
    }//GEN-LAST:event_TglBeli2KeyPressed

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            tbDokter.requestFocus();
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
        NoKeluar.setText("");
        kdbar.setText("");
        nmbar.setText("");
        Jenis.setSelectedIndex(0);
        kdptg.setText("");
        nmptg.setText("");
        if(TabRawat.getSelectedIndex()==0){
            runBackground(() ->tampil());
        }else if(TabRawat.getSelectedIndex()==1){
            runBackground(() ->tampil2());
        }
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnPrint, BtnKeluar);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        if(TabRawat.getSelectedIndex()==0){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            if(tabMode.getRowCount()==0){
                JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                TCari.requestFocus();
            }else if(tabMode.getRowCount()!=0){
                Sequel.queryu("delete from temporary where temp37='"+akses.getalamatip()+"'");
                int row=tabMode.getRowCount();
                for(i=0;i<row;i++){  
                    Sequel.menyimpan("temporary","'"+i+"','"+
                                    tabMode.getValueAt(i,0).toString()+"','"+
                                    tabMode.getValueAt(i,1).toString()+"','"+
                                    tabMode.getValueAt(i,2).toString()+"','"+
                                    tabMode.getValueAt(i,3).toString()+"','"+
                                    tabMode.getValueAt(i,4).toString()+"','"+
                                    tabMode.getValueAt(i,5).toString()+"','"+
                                    tabMode.getValueAt(i,6).toString()+"','"+
                                    tabMode.getValueAt(i,8).toString()+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','"+akses.getalamatip()+"'","Transaksi Pengeluaran"); 
                }
                i++;
                Sequel.menyimpan("temporary","'"+i+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','"+akses.getalamatip()+"'","Transaksi Pembelian"); 
                i++;
                Sequel.menyimpan("temporary","'"+i+"','Jml.Total :','','','','','','','"+LTotal.getText()+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','"+akses.getalamatip()+"'","Transaksi Pembelian"); 
                
                Map<String, Object> param = new HashMap<>();    
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());   
                    param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptPengeluaranDapur.jasper","report","::[ Transaksi Pengeluaran Barang ]::","select * from temporary where temporary.temp37='"+akses.getalamatip()+"' order by temporary.no",param);
            }
            this.setCursor(Cursor.getDefaultCursor());
        }else if(TabRawat.getSelectedIndex()==1){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            if(tabMode2.getRowCount()==0){
                JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                TCari.requestFocus();
            }else if(tabMode2.getRowCount()!=0){
                Map<String, Object> param = new HashMap<>();    
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());   
                    param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptPengeluaranDapur2.jasper","report","::[ Transaksi Pengeluaran Barang ]::",
                    "select dapurpengeluaran.no_keluar,dapurpengeluaran.tanggal,dapurpengeluaran.nip,"+
                    "dapurpengeluaran.keterangan,petugas.nama,dapurdetailpengeluaran.kode_brng,"+
                    "dapurbarang.nama_brng,kodesatuan.satuan,dapurdetailpengeluaran.jumlah,"+
                    "dapurdetailpengeluaran.harga,dapurdetailpengeluaran.total from dapurpengeluaran "+
                    "inner join dapurdetailpengeluaran on dapurpengeluaran.no_keluar=dapurdetailpengeluaran.no_keluar "+
                    "inner join petugas on dapurpengeluaran.nip=petugas.nip "+
                    "inner join dapurbarang on dapurdetailpengeluaran.kode_brng=dapurbarang.kode_brng "+
                    "inner join kodesatuan on dapurdetailpengeluaran.kode_sat=kodesatuan.kode_sat "+
                    "where dapurpengeluaran.tanggal between '"+Valid.SetTgl(TglBeli1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(TglBeli2.getSelectedItem()+"")+"' and dapurpengeluaran.no_keluar like '%"+NoKeluar.getText()+"%' and petugas.nama like '%"+nmptg.getText()+"%'  and dapurbarang.jenis like '%"+Jenis.getSelectedItem().toString().replaceAll("Semua","")+"%' and dapurbarang.nama_brng like '%"+nmbar.getText()+"%' and "+
                    "(dapurpengeluaran.no_keluar like '%"+TCari.getText()+"%' or dapurpengeluaran.keterangan like '%"+TCari.getText()+"%' or dapurpengeluaran.nip like '%"+TCari.getText()+"%' or petugas.nama like '%"+TCari.getText()+"%' or dapurbarang.jenis like '%"+TCari.getText()+"%' or dapurdetailpengeluaran.kode_brng like '%"+TCari.getText()+"%' or dapurbarang.nama_brng like '%"+TCari.getText()+"%' or "+
                    "dapurdetailpengeluaran.kode_sat like '%"+TCari.getText()+"%' or kodesatuan.satuan like '%"+TCari.getText()+"%') order by dapurpengeluaran.tanggal,dapurpengeluaran.no_keluar ",param);
            }
            this.setCursor(Cursor.getDefaultCursor());
        }
            
    }//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt,BtnAll,BtnAll);
        }
    }//GEN-LAST:event_BtnPrintKeyPressed

private void ppHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppHapusActionPerformed
  if(tbDokter.getValueAt(tbDokter.getSelectedRow(),1).toString().trim().equals("")){
      Valid.textKosong(TCari,tbDokter.getValueAt(tbDokter.getSelectedRow(),1).toString());
  }else{
     try {
         psdetailpengeluaran=koneksi.prepareStatement("select kode_brng,jumlah,total from dapurdetailpengeluaran where no_keluar=? ");
         try {
            Sequel.AutoComitFalse();
            sukses=true;
            psdetailpengeluaran.setString(1,tbDokter.getValueAt(tbDokter.getSelectedRow(),1).toString());
            rs2=psdetailpengeluaran.executeQuery();
            total=0;
            while(rs2.next()){
               Trackbarang.catatRiwayat(rs2.getString("kode_brng"),rs2.getDouble("jumlah"),0,"Stok Keluar", akses.getkode(),"Hapus");
               Sequel.mengedit("dapurbarang","kode_brng=?","stok=stok+?",2,new String[]{
                   rs2.getString("jumlah"),rs2.getString("kode_brng")
               });
               total=total+rs2.getDouble("total");
            }         
            Sequel.queryu("delete from tampjurnal");
            if(Sequel.menyimpantf2("tampjurnal","?,?,?,?",4,new String[]{Sequel.cariIsi("select Stok_Keluar_Dapur from set_akun"),"PERSEDIAAN BARANG","0",""+total})==false){
                sukses=false;
            }
            if(Sequel.menyimpantf2("tampjurnal","?,?,?,?",4,new String[]{Sequel.cariIsi("select Kontra_Stok_Keluar_Dapur from set_akun"),"KAS DI TANGAN",""+total,"0"})==false){
                sukses=false;
            } 
            if(sukses==true){
                sukses=jur.simpanJurnal(tbDokter.getValueAt(tbDokter.getSelectedRow(),1).toString(),"U","PEMBATALAN PENGGUNAAN BARANG DAPUR KERING DAN BASAH"+", OLEH "+akses.getkode());
            }
            if(sukses==true){
                Sequel.queryu2("delete from dapurpengeluaran where no_keluar=?",1,new String[]{tbDokter.getValueAt(tbDokter.getSelectedRow(),1).toString()});
                Sequel.Commit();
                runBackground(() ->tampil());
            }else{
                JOptionPane.showMessageDialog(null,"Terjadi kesalahan saat pemrosesan data, transaksi dibatalkan.\nPeriksa kembali data sebelum melanjutkan menyimpan..!!");
                Sequel.RollBack();
            }

            Sequel.AutoComitTrue();
         } catch (Exception e) {
             System.out.println("Notif : "+e);
         } finally{
             if(rs2!=null){
                 rs2.close();
             }
             if(psdetailpengeluaran!=null){
                 psdetailpengeluaran.close();
             }
         }
     } catch (SQLException ex) {
         System.out.println(ex);
     }      
  }     
}//GEN-LAST:event_ppHapusActionPerformed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if(TabRawat.getSelectedIndex()==0){
            runBackground(() ->tampil());
        }else if(TabRawat.getSelectedIndex()==1){
            runBackground(() ->tampil2());
        }
    }//GEN-LAST:event_TabRawatMouseClicked

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        if(koneksiDB.CARICEPAT().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(TabRawat.getSelectedIndex()==0){
                        if(TCari.getText().length()>2){
                            runBackground(() ->tampil());
                        }
                    }else if(TabRawat.getSelectedIndex()==1){
                        if(TCari.getText().length()>2){
                            runBackground(() ->tampil2());
                        }
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(TabRawat.getSelectedIndex()==0){
                        if(TCari.getText().length()>2){
                            runBackground(() ->tampil());
                        }
                    }else if(TabRawat.getSelectedIndex()==1){
                        if(TCari.getText().length()>2){
                            runBackground(() ->tampil2());
                        }
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(TabRawat.getSelectedIndex()==0){
                        if(TCari.getText().length()>2){
                            runBackground(() ->tampil());
                        }
                    }else if(TabRawat.getSelectedIndex()==1){
                        if(TCari.getText().length()>2){
                            runBackground(() ->tampil2());
                        }
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
            DapurCariPengeluaran dialog = new DapurCariPengeluaran(new javax.swing.JFrame(), true);
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
    private widget.Button BtnPrint;
    private widget.ComboBox Jenis;
    private widget.Label LTotal;
    private widget.TextBox NoKeluar;
    private widget.TextBox TCari;
    private javax.swing.JTabbedPane TabRawat;
    private widget.Tanggal TglBeli1;
    private widget.Tanggal TglBeli2;
    private widget.Button btnBarang;
    private widget.Button btnPetugas;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.TextBox kdbar;
    private widget.TextBox kdptg;
    private widget.Label label10;
    private widget.Label label11;
    private widget.Label label12;
    private widget.Label label13;
    private widget.Label label15;
    private widget.Label label17;
    private widget.Label label9;
    private widget.TextBox nmbar;
    private widget.TextBox nmptg;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi3;
    private widget.panelisi panelisi4;
    private javax.swing.JMenuItem ppHapus;
    private widget.ScrollPane scrollPane1;
    private widget.ScrollPane scrollPane2;
    private widget.Table tbDokter;
    private widget.Table tbDokter2;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
       Valid.tabelKosong(tabMode);
        try{     
            ps=koneksi.prepareStatement(
                    "select dapurpengeluaran.tanggal,dapurpengeluaran.no_keluar,dapurpengeluaran.keterangan,dapurpengeluaran.nip,petugas.nama "+
                    " from dapurpengeluaran inner join petugas on dapurpengeluaran.nip=petugas.nip "+
                    " inner join dapurdetailpengeluaran on dapurpengeluaran.no_keluar=dapurdetailpengeluaran.no_keluar "+
                    " inner join dapurbarang on dapurdetailpengeluaran.kode_brng=dapurbarang.kode_brng "+
                    " inner join kodesatuan on dapurbarang.kode_sat=kodesatuan.kode_sat "+
                    " where dapurpengeluaran.tanggal between ? and ? and dapurpengeluaran.no_keluar like ? and petugas.nama like ?  and dapurbarang.jenis like ? and dapurbarang.nama_brng like ? and "+
                    " (dapurpengeluaran.no_keluar like ? or dapurpengeluaran.keterangan like ? or dapurpengeluaran.nip like ? or petugas.nama like ? or dapurbarang.jenis like ? or "+
                    " dapurdetailpengeluaran.kode_brng like ? or dapurbarang.nama_brng like ? or dapurdetailpengeluaran.kode_sat like ? or kodesatuan.satuan like ?) "+
                    " group by dapurpengeluaran.no_keluar order by dapurpengeluaran.tanggal,dapurpengeluaran.no_keluar ");
            try {
                ps.setString(1,Valid.SetTgl(TglBeli1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(TglBeli2.getSelectedItem()+""));
                ps.setString(3,"%"+NoKeluar.getText()+"%");
                ps.setString(4,"%"+nmptg.getText()+"%");
                ps.setString(5,"%"+Jenis.getSelectedItem().toString().replaceAll("Semua","")+"%");
                ps.setString(6,"%"+nmbar.getText()+"%");
                ps.setString(7,"%"+TCari.getText()+"%");
                ps.setString(8,"%"+TCari.getText()+"%");
                ps.setString(9,"%"+TCari.getText()+"%");
                ps.setString(10,"%"+TCari.getText()+"%");
                ps.setString(11,"%"+TCari.getText()+"%");
                ps.setString(12,"%"+TCari.getText()+"%");
                ps.setString(13,"%"+TCari.getText()+"%");
                ps.setString(14,"%"+TCari.getText()+"%");
                ps.setString(15,"%"+TCari.getText()+"%");
                rs=ps.executeQuery();
                ttltagihan=0;
                while(rs.next()){
                    tabMode.addRow(new Object[]{rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4)+", "+rs.getString(5),"Stok Keluar :","","","","",""});    
                    ps2=koneksi.prepareStatement("select dapurdetailpengeluaran.kode_brng,dapurbarang.nama_brng, "+
                        "dapurdetailpengeluaran.kode_sat,kodesatuan.satuan,dapurdetailpengeluaran.jumlah,dapurdetailpengeluaran.harga,dapurdetailpengeluaran.total "+
                        "from dapurdetailpengeluaran inner join dapurbarang on dapurdetailpengeluaran.kode_brng=dapurbarang.kode_brng "+
                        "inner join kodesatuan on dapurdetailpengeluaran.kode_sat=kodesatuan.kode_sat "+
                        "where dapurdetailpengeluaran.no_keluar=? and dapurbarang.nama_brng like ? and dapurbarang.jenis like ? and "+
                        " (dapurdetailpengeluaran.kode_brng like ? or dapurbarang.nama_brng like ? or dapurdetailpengeluaran.kode_sat like ? or "+
                        " dapurbarang.jenis like ?) order by dapurdetailpengeluaran.kode_brng  ");
                    try {
                        ps2.setString(1,rs.getString(2));
                        ps2.setString(2,"%"+nmbar.getText()+"%");
                        ps2.setString(3,"%"+Jenis.getSelectedItem().toString().replaceAll("Semua","")+"%");
                        ps2.setString(4,"%"+TCari.getText()+"%");
                        ps2.setString(5,"%"+TCari.getText()+"%");
                        ps2.setString(6,"%"+TCari.getText()+"%");
                        ps2.setString(7,"%"+TCari.getText()+"%");
                        rs2=ps2.executeQuery();
                        int no=1;
                        tagihan=0;
                        while(rs2.next()){
                            tagihan=tagihan+rs2.getDouble(7);
                            ttltagihan=ttltagihan+rs2.getDouble(7);
                            tabMode.addRow(new Object[]{"","","","",no+". "+rs2.getString(1)+", "+rs2.getString(2),
                                            rs2.getString(3)+", "+rs2.getString(4),rs2.getString(5),Valid.SetAngka(rs2.getDouble(6)),Valid.SetAngka(rs2.getDouble(7))});
                            no++;
                        }
                        tabMode.addRow(new Object[]{"","","","","Total ","","","",Valid.SetAngka(tagihan)});
                    } catch (Exception e) {
                        System.out.println("Notif2 : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                }
                LTotal.setText(""+Valid.SetAngka(ttltagihan));
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
    }

    public void emptTeks() {
        kdbar.setText("");
        nmbar.setText("");
        kdbar.requestFocus();        
    }
    
    public void isCek(){
        BtnPrint.setEnabled(akses.getdapur_stok_keluar());
    }
    
    private void tampil2(){
        Valid.tabelKosong(tabMode2);
        try {
            ps=koneksi.prepareStatement(
                    "select dapurpengeluaran.no_keluar,dapurpengeluaran.tanggal,dapurpengeluaran.nip,"+
                    "dapurpengeluaran.keterangan,petugas.nama,dapurdetailpengeluaran.kode_brng,"+
                    "dapurbarang.nama_brng,kodesatuan.satuan,dapurdetailpengeluaran.jumlah,"+
                    "dapurdetailpengeluaran.harga,dapurdetailpengeluaran.total from dapurpengeluaran "+
                    "inner join dapurdetailpengeluaran on dapurpengeluaran.no_keluar=dapurdetailpengeluaran.no_keluar "+
                    "inner join petugas on dapurpengeluaran.nip=petugas.nip "+
                    "inner join dapurbarang on dapurdetailpengeluaran.kode_brng=dapurbarang.kode_brng "+
                    "inner join kodesatuan on dapurdetailpengeluaran.kode_sat=kodesatuan.kode_sat "+
                    "where dapurpengeluaran.tanggal between ? and ? and dapurpengeluaran.no_keluar like ? and petugas.nama like ?  and dapurbarang.jenis like ? and dapurbarang.nama_brng like ? and "+
                    "(dapurpengeluaran.no_keluar like ? or dapurpengeluaran.keterangan like ? or dapurpengeluaran.nip like ? or petugas.nama like ? or dapurbarang.jenis like ? or "+
                    "dapurdetailpengeluaran.kode_brng like ? or dapurbarang.nama_brng like ? or dapurdetailpengeluaran.kode_sat like ? or kodesatuan.satuan like ?) "+
                    " order by dapurpengeluaran.tanggal,dapurpengeluaran.no_keluar ");
            try {
                ps.setString(1,Valid.SetTgl(TglBeli1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(TglBeli2.getSelectedItem()+""));
                ps.setString(3,"%"+NoKeluar.getText()+"%");
                ps.setString(4,"%"+nmptg.getText()+"%");
                ps.setString(5,"%"+Jenis.getSelectedItem().toString().replaceAll("Semua","")+"%");
                ps.setString(6,"%"+nmbar.getText()+"%");
                ps.setString(7,"%"+TCari.getText()+"%");
                ps.setString(8,"%"+TCari.getText()+"%");
                ps.setString(9,"%"+TCari.getText()+"%");
                ps.setString(10,"%"+TCari.getText()+"%");
                ps.setString(11,"%"+TCari.getText()+"%");
                ps.setString(12,"%"+TCari.getText()+"%");
                ps.setString(13,"%"+TCari.getText()+"%");
                ps.setString(14,"%"+TCari.getText()+"%");
                ps.setString(15,"%"+TCari.getText()+"%");
                rs=ps.executeQuery();
                ttltagihan=0;
                while(rs.next()){
                    ttltagihan=ttltagihan+rs.getDouble("total");
                    tabMode2.addRow(new Object[]{
                        rs.getString("no_keluar"),rs.getString("tanggal"),rs.getString("nip"),
                        rs.getString("nama"),rs.getString("keterangan"),rs.getString("kode_brng"),
                        rs.getString("nama_brng"),rs.getString("satuan"),rs.getDouble("jumlah"),
                        rs.getDouble("harga"),rs.getDouble("total")
                    });
                }
                LTotal.setText(""+Valid.SetAngka(ttltagihan));
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

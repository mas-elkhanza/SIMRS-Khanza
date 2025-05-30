/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgLhtBiaya.java
 *
 * Created on 12 Jul 10, 16:21:34
 */

package inventory;

import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
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

/**
 *
 * @author perpustakaan
 */
public final class DlgCariPPNObat extends javax.swing.JDialog {
    private final DefaultTableModel tabMode,tabMode2,tabMode3,tabMode4,tabMode5,tabMode6;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;   
    private double total,totalppn,totalsemua,obat,ppnobat,obatdibayar;
    /** Creates new form DlgLhtBiaya
     * @param parent
     * @param modal */
    public DlgCariPPNObat(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(885,674);
        tabMode=new DefaultTableModel(null,new String[]{
                "Tgl.Beli","No.Faktur","Suplier","Petugas","Total","PPN","Total+PPN"
            }){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
             Class[] types = new Class[] {
                java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,
                java.lang.Double.class,java.lang.Double.class,java.lang.Double.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbPengadaan.setModel(tabMode);
        //tbBangsal.setDefaultRenderer(Object.class, new WarnaTable(jPanel2.getBackground(),tbBangsal.getBackground()));
        tbPengadaan.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbPengadaan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 7; i++) {
            TableColumn column = tbPengadaan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(70);
            }else if(i==1){
                column.setPreferredWidth(100);
            }else if(i==2){
                column.setPreferredWidth(260);
            }else if(i==3){
                column.setPreferredWidth(260);
            }else if(i==4){
                column.setPreferredWidth(80);
            }else if(i==5){
                column.setPreferredWidth(70);
            }else if(i==6){
                column.setPreferredWidth(90);
            }
        }
        tbPengadaan.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabMode2=new DefaultTableModel(null,new String[]{
                "Tgl.Terima","No.Faktur","Suplier","Petugas","Total","PPN","Total+PPN"
            }){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
             Class[] types = new Class[] {
                java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,
                java.lang.Double.class,java.lang.Double.class,java.lang.Double.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbPembelian.setModel(tabMode2);
        //tbBangsal2.setDefaultRenderer(Object.class, new WarnaTable(jPanel2.getBackground(),tbBangsal2.getBackground()));
        tbPembelian.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbPembelian.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 7; i++) {
            TableColumn column = tbPembelian.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(70);
            }else if(i==1){
                column.setPreferredWidth(100);
            }else if(i==2){
                column.setPreferredWidth(260);
            }else if(i==3){
                column.setPreferredWidth(260);
            }else if(i==4){
                column.setPreferredWidth(80);
            }else if(i==5){
                column.setPreferredWidth(70);
            }else if(i==6){
                column.setPreferredWidth(90);
            }
        }
        tbPembelian.setDefaultRenderer(Object.class, new WarnaTable());

        tabMode3=new DefaultTableModel(null,new String[]{
                "Tgl.Nota","No.Nota","Nama Pasien","Total","PPN","Total+PPN"
            }){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
             Class[] types = new Class[] {
                java.lang.String.class,java.lang.String.class,java.lang.String.class,
                java.lang.Double.class,java.lang.Double.class,java.lang.Double.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbRawatJalan.setModel(tabMode3);
        //tbBangsal.setDefaultRenderer(Object.class, new WarnaTable(jPanel2.getBackground(),tbBangsal.getBackground()));
        tbRawatJalan.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbRawatJalan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 6; i++) {
            TableColumn column = tbRawatJalan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(70);
            }else if(i==1){
                column.setPreferredWidth(110);
            }else if(i==2){
                column.setPreferredWidth(270);
            }else if(i==3){
                column.setPreferredWidth(80);
            }else if(i==4){
                column.setPreferredWidth(70);
            }else if(i==5){
                column.setPreferredWidth(90);
            }
        }
        tbRawatJalan.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabMode4=new DefaultTableModel(null,new String[]{
                "Tgl.Jual","No.Nota","Pasien","Petugas","Total","PPN","Total+PPN"
            }){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
             Class[] types = new Class[] {
                java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,
                java.lang.Double.class,java.lang.Double.class,java.lang.Double.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbObatJualBebas.setModel(tabMode4);
        //tbBangsal.setDefaultRenderer(Object.class, new WarnaTable(jPanel2.getBackground(),tbBangsal.getBackground()));
        tbObatJualBebas.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObatJualBebas.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 7; i++) {
            TableColumn column = tbObatJualBebas.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(70);
            }else if(i==1){
                column.setPreferredWidth(100);
            }else if(i==2){
                column.setPreferredWidth(260);
            }else if(i==3){
                column.setPreferredWidth(260);
            }else if(i==4){
                column.setPreferredWidth(80);
            }else if(i==5){
                column.setPreferredWidth(70);
            }else if(i==6){
                column.setPreferredWidth(90);
            }
        }
        tbObatJualBebas.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabMode5=new DefaultTableModel(null,new String[]{
                "Tgl.Nota","No.Nota","Nama Pasien","Total","PPN","Total+PPN"
            }){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
             Class[] types = new Class[] {
                java.lang.String.class,java.lang.String.class,java.lang.String.class,
                java.lang.Double.class,java.lang.Double.class,java.lang.Double.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbRawatInap.setModel(tabMode5);
        //tbBangsal.setDefaultRenderer(Object.class, new WarnaTable(jPanel2.getBackground(),tbBangsal.getBackground()));
        tbRawatInap.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbRawatInap.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 6; i++) {
            TableColumn column = tbRawatInap.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(70);
            }else if(i==1){
                column.setPreferredWidth(110);
            }else if(i==2){
                column.setPreferredWidth(270);
            }else if(i==3){
                column.setPreferredWidth(80);
            }else if(i==4){
                column.setPreferredWidth(70);
            }else if(i==5){
                column.setPreferredWidth(90);
            }
        }
        tbRawatInap.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabMode6=new DefaultTableModel(null,new String[]{
                "Tgl.Jual","No.Nota","Pasien","Petugas","Total","PPN","Total+PPN"
            }){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
             Class[] types = new Class[] {
                java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,
                java.lang.Double.class,java.lang.Double.class,java.lang.Double.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbPiutangObat.setModel(tabMode6);
        //tbBangsal.setDefaultRenderer(Object.class, new WarnaTable(jPanel2.getBackground(),tbBangsal.getBackground()));
        tbPiutangObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbPiutangObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 7; i++) {
            TableColumn column = tbPiutangObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(70);
            }else if(i==1){
                column.setPreferredWidth(100);
            }else if(i==2){
                column.setPreferredWidth(260);
            }else if(i==3){
                column.setPreferredWidth(260);
            }else if(i==4){
                column.setPreferredWidth(80);
            }else if(i==5){
                column.setPreferredWidth(70);
            }else if(i==6){
                column.setPreferredWidth(90);
            }
        }
        tbPiutangObat.setDefaultRenderer(Object.class, new WarnaTable());
        
        TKd.setDocument(new batasInput((byte)20).getKata(TKd));
        if(koneksiDB.CARICEPAT().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(TabRawat.getSelectedIndex()==0){
                        if(TCari.getText().length()>2){
                            tampil();
                        }
                    }else if(TabRawat.getSelectedIndex()==1){
                        if(TCari.getText().length()>2){
                            tampil2();
                        }
                    }else if(TabRawat.getSelectedIndex()==2){
                        if(TCari.getText().length()>2){
                            tampil3();
                        }
                    }else if(TabRawat.getSelectedIndex()==3){
                        if(TCari.getText().length()>2){
                            tampil4();
                        }
                    }else if(TabRawat.getSelectedIndex()==4){
                        if(TCari.getText().length()>2){
                            tampil5();
                        }
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(TabRawat.getSelectedIndex()==0){
                        if(TCari.getText().length()>2){
                            tampil();
                        }
                    }else if(TabRawat.getSelectedIndex()==1){
                        if(TCari.getText().length()>2){
                            tampil2();
                        }
                    }else if(TabRawat.getSelectedIndex()==2){
                        if(TCari.getText().length()>2){
                            tampil3();
                        }
                    }else if(TabRawat.getSelectedIndex()==3){
                        if(TCari.getText().length()>2){
                            tampil4();
                        }
                    }else if(TabRawat.getSelectedIndex()==4){
                        if(TCari.getText().length()>2){
                            tampil5();
                        }
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(TabRawat.getSelectedIndex()==0){
                        if(TCari.getText().length()>2){
                            tampil();
                        }
                    }else if(TabRawat.getSelectedIndex()==1){
                        if(TCari.getText().length()>2){
                            tampil2();
                        }
                    }else if(TabRawat.getSelectedIndex()==2){
                        if(TCari.getText().length()>2){
                            tampil3();
                        }
                    }else if(TabRawat.getSelectedIndex()==3){
                        if(TCari.getText().length()>2){
                            tampil4();
                        }
                    }else if(TabRawat.getSelectedIndex()==4){
                        if(TCari.getText().length()>2){
                            tampil5();
                        }
                    }
                }
            });
        }
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
        internalFrame1 = new widget.InternalFrame();
        TabRawat = new javax.swing.JTabbedPane();
        Scroll = new widget.ScrollPane();
        tbPengadaan = new widget.Table();
        Scroll2 = new widget.ScrollPane();
        tbPembelian = new widget.Table();
        Scroll3 = new widget.ScrollPane();
        tbRawatJalan = new widget.Table();
        Scroll4 = new widget.ScrollPane();
        tbObatJualBebas = new widget.Table();
        Scroll5 = new widget.ScrollPane();
        tbRawatInap = new widget.Table();
        Scroll6 = new widget.ScrollPane();
        tbPiutangObat = new widget.Table();
        panelGlass5 = new widget.panelisi();
        label11 = new widget.Label();
        Tgl1 = new widget.Tanggal();
        label18 = new widget.Label();
        Tgl2 = new widget.Tanggal();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        jLabel7 = new widget.Label();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();

        TKd.setForeground(new java.awt.Color(255, 255, 255));
        TKd.setName("TKd"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data PPN Obat ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        TabRawat.setBackground(new java.awt.Color(255, 255, 253));
        TabRawat.setForeground(new java.awt.Color(50, 50, 50));
        TabRawat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabRawat.setName("TabRawat"); // NOI18N
        TabRawat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatMouseClicked(evt);
            }
        });

        Scroll.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbPengadaan.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbPengadaan.setName("tbPengadaan"); // NOI18N
        Scroll.setViewportView(tbPengadaan);

        TabRawat.addTab("PPN Pengadaan Obat", Scroll);

        Scroll2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll2.setName("Scroll2"); // NOI18N
        Scroll2.setOpaque(true);

        tbPembelian.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbPembelian.setName("tbPembelian"); // NOI18N
        Scroll2.setViewportView(tbPembelian);

        TabRawat.addTab("PPN Penerimaan Obat", Scroll2);

        Scroll3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll3.setName("Scroll3"); // NOI18N
        Scroll3.setOpaque(true);

        tbRawatJalan.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbRawatJalan.setName("tbRawatJalan"); // NOI18N
        Scroll3.setViewportView(tbRawatJalan);

        TabRawat.addTab("PPN Obat Rawat Jalan", Scroll3);

        Scroll4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll4.setName("Scroll4"); // NOI18N
        Scroll4.setOpaque(true);

        tbObatJualBebas.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbObatJualBebas.setName("tbObatJualBebas"); // NOI18N
        Scroll4.setViewportView(tbObatJualBebas);

        TabRawat.addTab("PPN Obat Jual Bebas", Scroll4);

        Scroll5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll5.setName("Scroll5"); // NOI18N
        Scroll5.setOpaque(true);

        tbRawatInap.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbRawatInap.setName("tbRawatInap"); // NOI18N
        Scroll5.setViewportView(tbRawatInap);

        TabRawat.addTab("PPN Obat Rawat Inap", Scroll5);

        Scroll6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll6.setName("Scroll6"); // NOI18N
        Scroll6.setOpaque(true);

        tbPiutangObat.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbPiutangObat.setName("tbPiutangObat"); // NOI18N
        Scroll6.setViewportView(tbPiutangObat);

        TabRawat.addTab("PPN Piutang Obat", Scroll6);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        panelGlass5.setName("panelGlass5"); // NOI18N
        panelGlass5.setPreferredSize(new java.awt.Dimension(55, 55));
        panelGlass5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(55, 23));
        panelGlass5.add(label11);

        Tgl1.setDisplayFormat("dd-MM-yyyy");
        Tgl1.setName("Tgl1"); // NOI18N
        Tgl1.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass5.add(Tgl1);

        label18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label18.setText("s.d.");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(25, 23));
        panelGlass5.add(label18);

        Tgl2.setDisplayFormat("dd-MM-yyyy");
        Tgl2.setName("Tgl2"); // NOI18N
        Tgl2.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass5.add(Tgl2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(60, 23));
        panelGlass5.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(205, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass5.add(TCari);

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
        panelGlass5.add(BtnCari);

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
        panelGlass5.add(BtnAll);

        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(35, 23));
        panelGlass5.add(jLabel7);

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
        panelGlass5.add(BtnPrint);

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
        panelGlass5.add(BtnKeluar);

        internalFrame1.add(panelGlass5, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));        
        Map<String, Object> param = new HashMap<>();         
        param.put("namars",akses.getnamars());
        param.put("alamatrs",akses.getalamatrs());
        param.put("kotars",akses.getkabupatenrs());
        param.put("propinsirs",akses.getpropinsirs());
        param.put("kontakrs",akses.getkontakrs());
        param.put("emailrs",akses.getemailrs());   
        param.put("periode",Tgl1.getSelectedItem()+" S.D. "+Tgl2.getSelectedItem()); 
        if(TabRawat.getSelectedIndex()==0){
            if(tabMode.getRowCount()==0){
                JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                //TCari.requestFocus();
            }else if(tabMode.getRowCount()!=0){
                Valid.MyReportqry("rptPPNPembelian.jasper","report","::[ PPN Pengadaan Barang ]::",
                    "select pembelian.tgl_beli,pembelian.no_faktur, "+
                    " pembelian.kode_suplier,datasuplier.nama_suplier, "+
                    " pembelian.nip,petugas.nama,pembelian.total1,"+
                    " pembelian.potongan,pembelian.total2,pembelian.ppn,pembelian.tagihan "+
                    " from pembelian inner join datasuplier inner join petugas on "+
                    " pembelian.kode_suplier=datasuplier.kode_suplier and pembelian.nip=petugas.nip "+
                    " where pembelian.tgl_beli between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and pembelian.no_faktur like '%"+TCari.getText()+"%' or "+
                    " pembelian.tgl_beli between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and pembelian.kode_suplier like '%"+TCari.getText()+"%' or "+
                    " pembelian.tgl_beli between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and datasuplier.nama_suplier like '%"+TCari.getText()+"%' or "+
                    " pembelian.tgl_beli between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and pembelian.nip like '%"+TCari.getText()+"%' or "+
                    " pembelian.tgl_beli between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and petugas.nama like '%"+TCari.getText()+"%' order by pembelian.tgl_beli,pembelian.no_faktur ",param);
            }
                
        }else if(TabRawat.getSelectedIndex()==1){
            if(tabMode2.getRowCount()==0){
                JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                //TCari.requestFocus();
            }else if(tabMode2.getRowCount()!=0){
                Valid.MyReportqry("rptPPNPemesanan.jasper","report","::[ PPN Penerimaan Barang ]::",
                    "select pemesanan.tgl_pesan,pemesanan.no_faktur, "+
                    " pemesanan.kode_suplier,datasuplier.nama_suplier, "+
                    " pemesanan.nip,petugas.nama,pemesanan.total1,"+
                    " pemesanan.potongan,pemesanan.total2,pemesanan.ppn,pemesanan.tagihan "+
                    " from pemesanan inner join datasuplier inner join petugas on "+
                    " pemesanan.kode_suplier=datasuplier.kode_suplier and pemesanan.nip=petugas.nip "+
                    " where pemesanan.tgl_pesan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and pemesanan.no_faktur like '%"+TCari.getText()+"%' or "+
                    " pemesanan.tgl_pesan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and pemesanan.kode_suplier like '%"+TCari.getText()+"%' or "+
                    " pemesanan.tgl_pesan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and datasuplier.nama_suplier like '%"+TCari.getText()+"%' or "+
                    " pemesanan.tgl_pesan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and pemesanan.nip like '%"+TCari.getText()+"%' or "+
                    " pemesanan.tgl_pesan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and petugas.nama like '%"+TCari.getText()+"%' order by pemesanan.tgl_pesan,pemesanan.no_faktur ",param);
            }                
        }else if(TabRawat.getSelectedIndex()==2){
            if(tabMode3.getRowCount()==0){
                JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                //TCari.requestFocus();
            }else if(tabMode3.getRowCount()!=0){
                Sequel.queryu("delete from temporary where temp37='"+akses.getalamatip()+"'");
            
                for(int r=0;r<tabMode3.getRowCount();r++){ 
                        Sequel.menyimpan("temporary","'"+r+"','"+
                                    tabMode3.getValueAt(r,0).toString()+"','"+
                                    tabMode3.getValueAt(r,1).toString()+"','"+
                                    tabMode3.getValueAt(r,2).toString()+"','"+
                                    Valid.SetAngka(Double.parseDouble(tabMode3.getValueAt(r,3).toString()))+"','"+
                                    Valid.SetAngka(Double.parseDouble(tabMode3.getValueAt(r,4).toString()))+"','"+
                                    Valid.SetAngka(Double.parseDouble(tabMode3.getValueAt(r,5).toString()))+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','"+akses.getalamatip()+"'","Rekap Nota Pembayaran");
                }
                
                Valid.MyReportqry("rptPPNRalan.jasper","report","::[ Laporan PPN Obat Ralan ]::","select * from temporary where temporary.temp37='"+akses.getalamatip()+"' order by temporary.no",param);
            }                
        }else if(TabRawat.getSelectedIndex()==3){
            if(tabMode4.getRowCount()==0){
                JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                //TCari.requestFocus();
            }else if(tabMode4.getRowCount()!=0){
                Valid.MyReportqry("rptPPNJualBebas.jasper","report","::[ PPN Obat Jual Bebas ]::",
                    "select penjualan.tgl_jual,penjualan.nota_jual, "+
                    " penjualan.no_rkm_medis,pasien.nm_pasien, "+
                    " penjualan.nip,petugas.nama,penjualan.ppn as ppn,sum(detailjual.total) as total "+
                    " from penjualan inner join pasien on penjualan.no_rkm_medis=pasien.no_rkm_medis "+
                    " inner join petugas on penjualan.nip=petugas.nip inner join detailjual on penjualan.nota_jual=detailjual.nota_jual "+
                    " where penjualan.status='Sudah Dibayar' and penjualan.tgl_jual between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                    (TCari.getText().trim().equals("")?"":" and (penjualan.nota_jual like '%"+TCari.getText()+"%' or "+
                    " penjualan.no_rkm_medis like '%"+TCari.getText()+"%' or "+
                    " penjualan.nm_pasien like '%"+TCari.getText()+"%' or "+
                    " penjualan.nip like '%"+TCari.getText()+"%' or "+
                    " petugas.nama like '%"+TCari.getText()+"%') ")+" group by penjualan.nota_jual "+
                    " order by penjualan.tgl_jual,penjualan.nota_jual ",param);
            }                
        }else if(TabRawat.getSelectedIndex()==4){
            if(tabMode5.getRowCount()==0){
                JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                //TCari.requestFocus();
            }else if(tabMode5.getRowCount()!=0){
                Sequel.queryu("delete from temporary where temp37='"+akses.getalamatip()+"'");
            
                for(int r=0;r<tabMode5.getRowCount();r++){ 
                        Sequel.menyimpan("temporary","'"+r+"','"+
                                    tabMode5.getValueAt(r,0).toString()+"','"+
                                    tabMode5.getValueAt(r,1).toString()+"','"+
                                    tabMode5.getValueAt(r,2).toString()+"','"+
                                    Valid.SetAngka(Double.parseDouble(tabMode5.getValueAt(r,3).toString()))+"','"+
                                    Valid.SetAngka(Double.parseDouble(tabMode5.getValueAt(r,4).toString()))+"','"+
                                    Valid.SetAngka(Double.parseDouble(tabMode5.getValueAt(r,5).toString()))+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','"+akses.getalamatip()+"'","Rekap Nota Pembayaran");
                }
                
                Valid.MyReportqry("rptPPNRanap.jasper","report","::[ Laporan PPN Obat Ranap ]::","select * from temporary where temporary.temp37='"+akses.getalamatip()+"' order by temporary.no",param);
            }                
        }else if(TabRawat.getSelectedIndex()==5){
            if(tabMode6.getRowCount()==0){
                JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                //TCari.requestFocus();
            }else if(tabMode6.getRowCount()!=0){
                Valid.MyReportqry("rptPPNPiutangObat.jasper","report","::[ PPN Piutang Obat & BHP ]::",
                    "select piutang.tgl_piutang,piutang.nota_piutang,piutang.no_rkm_medis,pasien.nm_pasien, "+
                    "piutang.nip,petugas.nama,round(piutang.ppn) as ppn,sum(detailpiutang.total) as total "+
                    "from piutang inner join pasien on piutang.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join petugas on piutang.nip=petugas.nip inner join detailpiutang on piutang.nota_piutang=detailpiutang.nota_piutang "+
                    "where piutang.tgl_piutang between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                    (TCari.getText().trim().equals("")?"":" and (piutang.nota_piutang like '%"+TCari.getText()+"%' or "+
                    "piutang.no_rkm_medis like '%"+TCari.getText()+"%' or piutang.nm_pasien like '%"+TCari.getText()+"%' or "+
                    "piutang.nip like '%"+TCari.getText()+"%' or petugas.nama like '%"+TCari.getText()+"%') ")+
                    "group by piutang.nota_piutang order by piutang.tgl_piutang,piutang.nota_piutang ",param);
            }                
        }
        
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            //Valid.pindah(evt, BtnHapus, BtnAll);
        }
}//GEN-LAST:event_BtnPrintKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnKeluar,TKd);}
}//GEN-LAST:event_BtnKeluarKeyPressed

private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        if(TabRawat.getSelectedIndex()==0){
            tampil();
        }else if(TabRawat.getSelectedIndex()==1){
            tampil2();
        }else if(TabRawat.getSelectedIndex()==2){
            tampil3();
        }else if(TabRawat.getSelectedIndex()==3){
            tampil4();
        }else if(TabRawat.getSelectedIndex()==4){
            tampil5();
        }else if(TabRawat.getSelectedIndex()==5){
            tampil6();
        }
}//GEN-LAST:event_BtnCariActionPerformed

private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
            BtnCariActionPerformed(null);
            this.setCursor(Cursor.getDefaultCursor());
        }else{
            Valid.pindah(evt, TKd, BtnPrint);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }
    }//GEN-LAST:event_TCariKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
           TCari.setText("");
           if(TabRawat.getSelectedIndex()==0){
               tampil();
           }else if(TabRawat.getSelectedIndex()==1){
               tampil2();
           }else if(TabRawat.getSelectedIndex()==2){
                tampil3();
           }else if(TabRawat.getSelectedIndex()==3){
                tampil4();
           }else if(TabRawat.getSelectedIndex()==4){
                tampil5();
           }else if(TabRawat.getSelectedIndex()==5){
                tampil6();
           }
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            
        }
    }//GEN-LAST:event_BtnAllKeyPressed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if(TabRawat.getSelectedIndex()==0){
            tampil();
        }else if(TabRawat.getSelectedIndex()==1){
            tampil2();
        }else if(TabRawat.getSelectedIndex()==2){
            tampil3();
        }else if(TabRawat.getSelectedIndex()==3){
            tampil4();
        }else if(TabRawat.getSelectedIndex()==4){
            tampil5();
        }else if(TabRawat.getSelectedIndex()==5){
            tampil6();
        }
    }//GEN-LAST:event_TabRawatMouseClicked

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgCariPPNObat dialog = new DlgCariPPNObat(new javax.swing.JFrame(), true);
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
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll2;
    private widget.ScrollPane Scroll3;
    private widget.ScrollPane Scroll4;
    private widget.ScrollPane Scroll5;
    private widget.ScrollPane Scroll6;
    private widget.TextBox TCari;
    private widget.TextBox TKd;
    private javax.swing.JTabbedPane TabRawat;
    private widget.Tanggal Tgl1;
    private widget.Tanggal Tgl2;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label label11;
    private widget.Label label18;
    private widget.panelisi panelGlass5;
    private widget.Table tbObatJualBebas;
    private widget.Table tbPembelian;
    private widget.Table tbPengadaan;
    private widget.Table tbPiutangObat;
    private widget.Table tbRawatInap;
    private widget.Table tbRawatJalan;
    // End of variables declaration//GEN-END:variables

    public void tampil(){        
        try {
            Valid.tabelKosong(tabMode);
            ps=koneksi.prepareStatement(
                    "select pembelian.tgl_beli,pembelian.no_faktur,pembelian.kode_suplier,datasuplier.nama_suplier, "+
                    "pembelian.nip,petugas.nama,pembelian.total1,pembelian.potongan,pembelian.total2,pembelian.ppn,pembelian.tagihan "+
                    "from pembelian inner join datasuplier on pembelian.kode_suplier=datasuplier.kode_suplier "+
                    "inner join petugas on pembelian.nip=petugas.nip where pembelian.tgl_beli between ? and ? "+
                    (!TCari.getText().trim().equals("")?"and (pembelian.no_faktur like ? or pembelian.kode_suplier like ? or "+
                    "datasuplier.nama_suplier like ? or pembelian.nip like ? or petugas.nama like ?) ":"")+
                    "order by pembelian.tgl_beli,pembelian.no_faktur ");
            try {
                ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                if(!TCari.getText().trim().equals("")){
                    ps.setString(3,"%"+TCari.getText()+"%");
                    ps.setString(4,"%"+TCari.getText()+"%");
                    ps.setString(5,"%"+TCari.getText()+"%");
                    ps.setString(6,"%"+TCari.getText()+"%");
                    ps.setString(7,"%"+TCari.getText()+"%");
                }
                rs=ps.executeQuery();
                total=0;
                totalppn=0;
                totalsemua=0;
                while(rs.next()){
                    total=total+rs.getDouble("total2");
                    totalppn=totalppn+rs.getDouble("ppn");
                    totalsemua=totalsemua+rs.getDouble("tagihan");
                    tabMode.addRow(new Object[]{
                        rs.getString("tgl_beli"),rs.getString("no_faktur"),rs.getString("kode_suplier")+" "+rs.getString("nama_suplier"),
                        rs.getString("nip")+" "+rs.getString("nama"),rs.getDouble("total2"),rs.getDouble("ppn"),rs.getDouble("tagihan")
                    });
                }
                if(total>0){
                    tabMode.addRow(new Object[]{
                        "","","Total :","",total,totalppn,totalsemua
                    });
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
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

    public void tampil2(){        
        try {
            Valid.tabelKosong(tabMode2);
            ps=koneksi.prepareStatement(
                    "select pemesanan.tgl_pesan,pemesanan.no_faktur, pemesanan.kode_suplier,datasuplier.nama_suplier, "+
                    "pemesanan.nip,petugas.nama,pemesanan.total1,pemesanan.potongan,pemesanan.total2,pemesanan.ppn,pemesanan.tagihan "+
                    "from pemesanan inner join datasuplier on pemesanan.kode_suplier=datasuplier.kode_suplier "+
                    "inner join petugas on pemesanan.nip=petugas.nip where pemesanan.tgl_pesan between ? and ? "+
                    (!TCari.getText().trim().equals("")?"and (pemesanan.no_faktur like ? or pemesanan.kode_suplier like ? or "+
                    "datasuplier.nama_suplier like ? or pemesanan.nip like ? or petugas.nama like ?) ":"")+
                    "order by pemesanan.tgl_pesan,pemesanan.no_faktur ");
            try {
                ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                if(!TCari.getText().trim().equals("")){
                    ps.setString(3,"%"+TCari.getText()+"%");
                    ps.setString(4,"%"+TCari.getText()+"%");
                    ps.setString(5,"%"+TCari.getText()+"%");
                    ps.setString(6,"%"+TCari.getText()+"%");
                    ps.setString(7,"%"+TCari.getText()+"%");
                }
                rs=ps.executeQuery();
                total=0;
                totalppn=0;
                totalsemua=0;
                while(rs.next()){
                    total=total+rs.getDouble("total2");
                    totalppn=totalppn+rs.getDouble("ppn");
                    totalsemua=totalsemua+rs.getDouble("tagihan");
                    tabMode2.addRow(new Object[]{
                        rs.getString("tgl_pesan"),rs.getString("no_faktur"),rs.getString("kode_suplier")+" "+rs.getString("nama_suplier"),
                        rs.getString("nip")+" "+rs.getString("nama"),rs.getDouble("total2"),rs.getDouble("ppn"),rs.getDouble("tagihan")
                    });
                }
                if(total>0){
                    tabMode2.addRow(new Object[]{
                        "","","Total :","",total,totalppn,totalsemua
                    });
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
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
    
    public void tampil3(){        
        try {
            Valid.tabelKosong(tabMode3);
            ps=koneksi.prepareStatement(
                    "select nota_jalan.tanggal,nota_jalan.no_nota,pasien.no_rkm_medis,pasien.nm_pasien,nota_jalan.no_rawat "+
                    "from nota_jalan inner join reg_periksa on nota_jalan.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis where nota_jalan.tanggal between ? and ? "+
                    (!TCari.getText().trim().equals("")?"and (nota_jalan.no_nota like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ?) ":"")+
                    "order by nota_jalan.tanggal,nota_jalan.no_nota ");
            try {
                ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                if(!TCari.getText().trim().equals("")){
                    ps.setString(3,"%"+TCari.getText()+"%");
                    ps.setString(4,"%"+TCari.getText()+"%");
                    ps.setString(5,"%"+TCari.getText()+"%");
                }
                rs=ps.executeQuery();
                total=0;
                totalppn=0;
                totalsemua=0;
                while(rs.next()){
                    ppnobat=Sequel.cariIsiAngka("select sum(billing.totalbiaya) from billing where billing.no_rawat=? and billing.status='Obat' and billing.nm_perawatan='PPN Obat' ",rs.getString("no_rawat"));
                    obatdibayar=Sequel.cariIsiAngka("select sum(billing.totalbiaya) from billing where billing.no_rawat=? and billing.status='Obat' ",rs.getString("no_rawat"));
                    obat=obatdibayar-ppnobat;
                    total=total+obat;
                    totalppn=totalppn+ppnobat;
                    totalsemua=totalsemua+obatdibayar;
                    tabMode3.addRow(new Object[]{
                        rs.getString("tanggal"),rs.getString("no_nota"),rs.getString("no_rkm_medis")+" "+rs.getString("nm_pasien"),
                        obat,ppnobat,obatdibayar
                    });
                }
                if(total>0){
                    tabMode3.addRow(new Object[]{
                        "","","Total :",total,totalppn,totalsemua
                    });
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
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
    
    public void tampil4(){        
        try {
            Valid.tabelKosong(tabMode4);
            ps=koneksi.prepareStatement(
                    "select penjualan.tgl_jual,penjualan.nota_jual,penjualan.no_rkm_medis,pasien.nm_pasien, "+
                    "penjualan.nip,petugas.nama,round(penjualan.ppn) as ppn,sum(detailjual.total) as total "+
                    "from penjualan inner join pasien on penjualan.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join petugas on penjualan.nip=petugas.nip inner join detailjual on penjualan.nota_jual=detailjual.nota_jual "+
                    "where penjualan.status='Sudah Dibayar' and penjualan.tgl_jual between ? and ? "+
                    (TCari.getText().trim().equals("")?"":" and (penjualan.nota_jual like ? or "+
                    "penjualan.no_rkm_medis like ? or penjualan.nm_pasien like ? or "+
                    "penjualan.nip like ? or petugas.nama like ?) ")+" group by penjualan.nota_jual "+
                    " order by penjualan.tgl_jual,penjualan.nota_jual ");
            try {
                ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                if(!TCari.getText().trim().equals("")){
                    ps.setString(3,"%"+TCari.getText()+"%");
                    ps.setString(4,"%"+TCari.getText()+"%");
                    ps.setString(5,"%"+TCari.getText()+"%");
                    ps.setString(6,"%"+TCari.getText()+"%");
                    ps.setString(7,"%"+TCari.getText()+"%");
                }   
                rs=ps.executeQuery();
                total=0;
                totalppn=0;
                totalsemua=0;
                while(rs.next()){
                    total=total+rs.getDouble("total");
                    totalppn=totalppn+rs.getDouble("ppn");
                    totalsemua=totalsemua+rs.getDouble("total")+rs.getDouble("ppn");
                    tabMode4.addRow(new Object[]{
                        rs.getString("tgl_jual"),rs.getString("nota_jual"),rs.getString("no_rkm_medis")+" "+rs.getString("nm_pasien"),
                        rs.getString("nip")+" "+rs.getString("nama"),rs.getDouble("total"),rs.getDouble("ppn"),(rs.getDouble("total")+rs.getDouble("ppn"))
                    });
                }
                if(total>0){
                    tabMode4.addRow(new Object[]{
                        "","","Total :","",total,totalppn,totalsemua
                    });
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
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
    
    public void tampil5(){        
        try {
            Valid.tabelKosong(tabMode5);
            ps=koneksi.prepareStatement(
                    "select nota_inap.tanggal,nota_inap.no_nota,pasien.no_rkm_medis,pasien.nm_pasien,nota_inap.no_rawat "+
                    "from nota_inap inner join reg_periksa on nota_inap.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis where nota_inap.tanggal between ? and ? "+
                    (!TCari.getText().trim().equals("")?"and (nota_inap.no_nota like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ?) ":"")+
                    "order by nota_inap.tanggal,nota_inap.no_nota ");
            try {
                ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                if(!TCari.getText().trim().equals("")){
                    ps.setString(3,"%"+TCari.getText()+"%");
                    ps.setString(4,"%"+TCari.getText()+"%");
                    ps.setString(5,"%"+TCari.getText()+"%");
                }
                rs=ps.executeQuery();
                total=0;
                totalppn=0;
                totalsemua=0;
                while(rs.next()){
                    ppnobat=Sequel.cariIsiAngka("select sum(billing.totalbiaya) from billing where billing.no_rawat=? and billing.status='Obat' and billing.nm_perawatan='PPN Obat' ",rs.getString("no_rawat"));
                    obatdibayar=Sequel.cariIsiAngka("select sum(billing.totalbiaya) from billing where billing.no_rawat=? and billing.status='Obat' ",rs.getString("no_rawat"));
                    obat=obatdibayar-ppnobat;
                    total=total+obat;
                    totalppn=totalppn+ppnobat;
                    totalsemua=totalsemua+obatdibayar;
                    tabMode5.addRow(new Object[]{
                        rs.getString("tanggal"),rs.getString("no_nota"),rs.getString("no_rkm_medis")+" "+rs.getString("nm_pasien"),
                        obat,ppnobat,obatdibayar
                    });
                }
                if(total>0){
                    tabMode5.addRow(new Object[]{
                        "","","Total :",total,totalppn,totalsemua
                    });
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
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
    
    public void tampil6(){        
        try {
            Valid.tabelKosong(tabMode6);
            ps=koneksi.prepareStatement(
                    "select piutang.tgl_piutang,piutang.nota_piutang,piutang.no_rkm_medis,pasien.nm_pasien, "+
                    "piutang.nip,petugas.nama,round(piutang.ppn) as ppn,sum(detailpiutang.total) as total "+
                    "from piutang inner join pasien on piutang.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join petugas on piutang.nip=petugas.nip inner join detailpiutang on piutang.nota_piutang=detailpiutang.nota_piutang "+
                    "where piutang.tgl_piutang between ? and ? "+(TCari.getText().trim().equals("")?"":" and (piutang.nota_piutang like ? or "+
                    "piutang.no_rkm_medis like ? or piutang.nm_pasien like ? or piutang.nip like ? or petugas.nama like ?) ")+
                    "group by piutang.nota_piutang order by piutang.tgl_piutang,piutang.nota_piutang ");
            try {
                ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                if(!TCari.getText().trim().equals("")){
                    ps.setString(3,"%"+TCari.getText()+"%");
                    ps.setString(4,"%"+TCari.getText()+"%");
                    ps.setString(5,"%"+TCari.getText()+"%");
                    ps.setString(6,"%"+TCari.getText()+"%");
                    ps.setString(7,"%"+TCari.getText()+"%");
                }   
                rs=ps.executeQuery();
                total=0;
                totalppn=0;
                totalsemua=0;
                while(rs.next()){
                    total=total+rs.getDouble("total");
                    totalppn=totalppn+rs.getDouble("ppn");
                    totalsemua=totalsemua+rs.getDouble("total")+rs.getDouble("ppn");
                    tabMode6.addRow(new Object[]{
                        rs.getString("tgl_piutang"),rs.getString("nota_piutang"),rs.getString("no_rkm_medis")+" "+rs.getString("nm_pasien"),
                        rs.getString("nip")+" "+rs.getString("nama"),rs.getDouble("total"),rs.getDouble("ppn"),(rs.getDouble("total")+rs.getDouble("ppn"))
                    });
                }
                if(total>0){
                    tabMode6.addRow(new Object[]{
                        "","","Total :","",total,totalppn,totalsemua
                    });
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
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

package ipsrs;
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
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class IPSRSNilaiPenerimaanVendorNonMedisPerBulan extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    public  IPSRSBarang barang=new IPSRSBarang(null,false);
    private PreparedStatement ps;
    private ResultSet rs;
    private double tagihan=0,totaltagihan=0,januari=0,totaljanuari=0,februari=0,totalfebruari=0,maret=0,totalmaret=0,april=0,totalapril=0,mei=0,totalmei=0,
                  juni=0,totaljuni=0,juli=0,totaljuli=0,agustus=0,totalagustus=0,september=0,totalseptember=0,oktober=0,totaloktober=0,november=0,totalnovember=0,
                  desember=0,totaldesember=0;
    private int i=0;
    private String kodejenis="",kodebarang="";

    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public IPSRSNilaiPenerimaanVendorNonMedisPerBulan(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        Object[] row={"Kode Suplier","Nama Suplier","Januari","Februari","Maret","April","Mei","Juni","Juli","Agustus","September","Oktober","November","Desember","Total"};
        tabMode=new DefaultTableModel(null,row){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
             Class[] types = new Class[] {
                 java.lang.Object.class,java.lang.Object.class,java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,
                 java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,
                 java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,java.lang.Double.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbDokter.setModel(tabMode);

        tbDokter.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 15; i++) {
            TableColumn column = tbDokter.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(75);
            }else if(i==1){
                column.setPreferredWidth(180);
            }else {
                column.setPreferredWidth(90);
            }
        }
        tbDokter.setDefaultRenderer(Object.class, new WarnaTable());

        kdbar.setDocument(new batasInput((byte)15).getKata(kdbar));
        kdjenis.setDocument(new batasInput((byte)4).getKata(kdjenis));
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));  
        
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
        
        barang.jenis.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("InventoryNilaiPenerimaanVendorNonMedisPerBulan")){
                    if(barang.jenis.getTable().getSelectedRow()!= -1){                          
                        kdjenis.setText(barang.jenis.getTable().getValueAt(barang.jenis.getTable().getSelectedRow(),0).toString());                    
                        nmjenis.setText(barang.jenis.getTable().getValueAt(barang.jenis.getTable().getSelectedRow(),1).toString());                        
                    }                    
                    kdjenis.requestFocus();
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
        
        barang.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("InventoryNilaiPenerimaanVendorNonMedisPerBulan")){
                    if(barang.getTable().getSelectedRow()!= -1){                   
                        kdbar.setText(barang.getTable().getValueAt(barang.getTable().getSelectedRow(),1).toString());                    
                        nmbar.setText(barang.getTable().getValueAt(barang.getTable().getSelectedRow(),2).toString());
                    }   
                    kdbar.requestFocus();
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
        
        barang.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(akses.getform().equals("InventoryNilaiPenerimaanVendorNonMedisPerBulan")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        barang.dispose();
                    }
                }                                
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        }); 
        
        Valid.LoadTahun(ThnCari);
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
        scrollPane1 = new widget.ScrollPane();
        tbDokter = new widget.Table();
        jPanel1 = new javax.swing.JPanel();
        panelisi1 = new widget.panelisi();
        label11 = new widget.Label();
        ThnCari = new widget.ComboBox();
        label10 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        label9 = new widget.Label();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();
        panelisi4 = new widget.panelisi();
        label17 = new widget.Label();
        kdbar = new widget.TextBox();
        nmbar = new widget.TextBox();
        btnBarang = new widget.Button();
        label24 = new widget.Label();
        kdjenis = new widget.TextBox();
        btnSatuan = new widget.Button();
        nmjenis = new widget.TextBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Nilai Penerimaan Vendor Barang Non Medis dan Penunjang ( Lab & RO ) ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setAutoscrolls(true);
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

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
        tbDokter.setName("tbDokter"); // NOI18N
        scrollPane1.setViewportView(tbDokter);

        internalFrame1.add(scrollPane1, java.awt.BorderLayout.CENTER);

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(816, 100));
        jPanel1.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 56));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label11.setText("Tahun :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(45, 23));
        panelisi1.add(label11);

        ThnCari.setName("ThnCari"); // NOI18N
        ThnCari.setPreferredSize(new java.awt.Dimension(80, 23));
        panelisi1.add(ThnCari);

        label10.setText("Key Word :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi1.add(label10);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(235, 23));
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
        panelisi1.add(BtnAll);

        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(35, 23));
        panelisi1.add(label9);

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

        label17.setText("Barang :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(65, 23));
        panelisi4.add(label17);
        label17.setBounds(295, 10, 90, 23);

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

        label24.setText("Jenis :");
        label24.setName("label24"); // NOI18N
        label24.setPreferredSize(new java.awt.Dimension(48, 23));
        panelisi4.add(label24);
        label24.setBounds(0, 10, 45, 23);

        kdjenis.setName("kdjenis"); // NOI18N
        kdjenis.setPreferredSize(new java.awt.Dimension(80, 23));
        kdjenis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdjenisKeyPressed(evt);
            }
        });
        panelisi4.add(kdjenis);
        kdjenis.setBounds(48, 10, 53, 23);

        btnSatuan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnSatuan.setMnemonic('3');
        btnSatuan.setToolTipText("Alt+3");
        btnSatuan.setName("btnSatuan"); // NOI18N
        btnSatuan.setPreferredSize(new java.awt.Dimension(28, 23));
        btnSatuan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSatuanActionPerformed(evt);
            }
        });
        panelisi4.add(btnSatuan);
        btnSatuan.setBounds(273, 10, 28, 23);

        nmjenis.setName("nmjenis"); // NOI18N
        nmjenis.setPreferredSize(new java.awt.Dimension(80, 23));
        nmjenis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nmjenisKeyPressed(evt);
            }
        });
        panelisi4.add(nmjenis);
        nmjenis.setBounds(103, 10, 167, 23);

        jPanel1.add(panelisi4, java.awt.BorderLayout.CENTER);

        internalFrame1.add(jPanel1, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        barang.jenis.dispose();
        barang.dispose();
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

    private void btnBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBarangActionPerformed
        akses.setform("InventoryNilaiPenerimaanVendorNonMedisPerBulan");
        barang.emptTeks();
        barang.isCek();
        barang.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        barang.setLocationRelativeTo(internalFrame1);
        barang.setAlwaysOnTop(false);
        barang.setVisible(true);
    }//GEN-LAST:event_btnBarangActionPerformed

    private void btnSatuanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSatuanActionPerformed
        akses.setform("InventoryNilaiPenerimaanVendorNonMedisPerBulan");
        barang.jenis.emptTeks();
        barang.jenis.isCek();
        barang.jenis.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        barang.jenis.setLocationRelativeTo(internalFrame1);
        barang.jenis.setAlwaysOnTop(false);
        barang.jenis.setVisible(true);
    }//GEN-LAST:event_btnSatuanActionPerformed

    private void kdbarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdbarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select ipsrsbarang.nama_brng from ipsrsbarang where ipsrsbarang.kode_brng=?", nmbar,kdbar.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){            
            Sequel.cariIsi("select ipsrsbarang.nama_brng from ipsrsbarang where ipsrsbarang.kode_brng=?", nmbar,kdbar.getText());
            kdjenis.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){            
            Sequel.cariIsi("select ipsrsbarang.nama_brng from ipsrsbarang where ipsrsbarang.kode_brng=?", nmbar,kdbar.getText());
            TCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnBarangActionPerformed(null);
        }
    }//GEN-LAST:event_kdbarKeyPressed

    private void kdjenisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdjenisKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select ipsrsjenisbarang.nm_jenis from ipsrsjenisbarang where ipsrsjenisbarang.kd_jenis=?", nmjenis,kdjenis.getText());         
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select ipsrsjenisbarang.nm_jenis from ipsrsjenisbarang where ipsrsjenisbarang.kd_jenis=?", nmjenis,kdjenis.getText());
            kdbar.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select ipsrsjenisbarang.nm_jenis from ipsrsjenisbarang where ipsrsjenisbarang.kd_jenis=?", nmjenis,kdjenis.getText());
            kdbar.requestFocus();   
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnSatuanActionPerformed(null);
        }
    }//GEN-LAST:event_kdjenisKeyPressed

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
        kdbar.setText("");
        nmbar.setText("");
        kdjenis.setText("");
        nmjenis.setText("");
        tampil();
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnPrint, BtnKeluar);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        BtnCariActionPerformed(evt);
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
                                Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(i,2).toString()))+"','"+
                                Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(i,3).toString()))+"','"+
                                Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(i,4).toString()))+"','"+
                                Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(i,5).toString()))+"','"+
                                Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(i,6).toString()))+"','"+
                                Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(i,7).toString()))+"','"+
                                Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(i,8).toString()))+"','"+
                                Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(i,9).toString()))+"','"+
                                Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(i,10).toString()))+"','"+
                                Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(i,11).toString()))+"','"+
                                Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(i,12).toString()))+"','"+
                                Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(i,13).toString()))+"','"+
                                Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(i,14).toString()))+"','','','','','','','','','','','','','','','','','','','','','','"+akses.getalamatip()+"'","Transaksi Penerimaan"); 
            }
            Map<String, Object> param = new HashMap<>();    
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());   
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptIPSRSNilaiPenerimaanVendorNonMedisPerBulan.jasper","report","::[ Nilai Penerimaan Vendor Non Medis Per Bulan ]::","select * from temporary where temporary.temp37='"+akses.getalamatip()+"' order by temporary.no",param);
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt,BtnAll,BtnAll);
        }
    }//GEN-LAST:event_BtnPrintKeyPressed

    private void nmjenisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nmjenisKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_nmjenisKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            IPSRSNilaiPenerimaanVendorNonMedisPerBulan dialog = new IPSRSNilaiPenerimaanVendorNonMedisPerBulan(new javax.swing.JFrame(), true);
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
    private widget.TextBox TCari;
    private widget.ComboBox ThnCari;
    private widget.Button btnBarang;
    private widget.Button btnSatuan;
    private widget.InternalFrame internalFrame1;
    private javax.swing.JPanel jPanel1;
    private widget.TextBox kdbar;
    private widget.TextBox kdjenis;
    private widget.Label label10;
    private widget.Label label11;
    private widget.Label label17;
    private widget.Label label24;
    private widget.Label label9;
    private widget.TextBox nmbar;
    private widget.TextBox nmjenis;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi4;
    private widget.ScrollPane scrollPane1;
    private widget.Table tbDokter;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try{   
            ps=koneksi.prepareStatement(
                    "select ipsrssuplier.kode_suplier,ipsrssuplier.nama_suplier from ipsrssuplier where "+
                    "ipsrssuplier.kode_suplier like ? or ipsrssuplier.nama_suplier like ? order by ipsrssuplier.nama_suplier");
            try {
                ps.setString(1,"%"+TCari.getText().trim()+"%");
                ps.setString(2,"%"+TCari.getText().trim()+"%");
                rs=ps.executeQuery();
                kodejenis="";kodebarang="";
                if(!kdjenis.getText().equals("")){
                    kodejenis=" and ipsrsbarang.jenis='"+kdjenis.getText()+"' ";
                }
                if(!kdbar.getText().equals("")){
                    kodebarang=" and ipsrsbarang.kode_brng='"+kdbar.getText()+"' ";
                }
                
                totaltagihan=0;totaljanuari=0;totalfebruari=0;totalmaret=0;totalapril=0;totalmei=0;totaljuni=0;totaljuli=0;
                totalagustus=0;totalseptember=0;totaloktober=0;totalnovember=0;totaldesember=0;
                while(rs.next()){
                    januari=Sequel.cariIsiAngka("select sum(ipsrsdetailpesan.total) as total from ipsrspemesanan "+
                            "inner join ipsrsdetailpesan on ipsrspemesanan.no_faktur=ipsrsdetailpesan.no_faktur "+
                            "inner join ipsrsbarang on ipsrsdetailpesan.kode_brng=ipsrsbarang.kode_brng "+
                            "where ipsrspemesanan.kode_suplier='"+rs.getString("kode_suplier")+"' and "+
                            "left(ipsrspemesanan.tgl_pesan,7)='"+ThnCari.getSelectedItem().toString()+"-01' "+
                            kodejenis+kodebarang);
                    totaljanuari=totaljanuari+januari;
                    
                    februari=Sequel.cariIsiAngka("select sum(ipsrsdetailpesan.total) as total from ipsrspemesanan "+
                            "inner join ipsrsdetailpesan on ipsrspemesanan.no_faktur=ipsrsdetailpesan.no_faktur "+
                            "inner join ipsrsbarang on ipsrsdetailpesan.kode_brng=ipsrsbarang.kode_brng "+
                            "where ipsrspemesanan.kode_suplier='"+rs.getString("kode_suplier")+"' and "+
                            "left(ipsrspemesanan.tgl_pesan,7)='"+ThnCari.getSelectedItem().toString()+"-02' "+
                            kodejenis+kodebarang);
                    totalfebruari=totalfebruari+februari;
                    
                    maret=Sequel.cariIsiAngka("select sum(ipsrsdetailpesan.total) as total from ipsrspemesanan "+
                            "inner join ipsrsdetailpesan on ipsrspemesanan.no_faktur=ipsrsdetailpesan.no_faktur "+
                            "inner join ipsrsbarang on ipsrsdetailpesan.kode_brng=ipsrsbarang.kode_brng "+
                            "where ipsrspemesanan.kode_suplier='"+rs.getString("kode_suplier")+"' and "+
                            "left(ipsrspemesanan.tgl_pesan,7)='"+ThnCari.getSelectedItem().toString()+"-03' "+
                            kodejenis+kodebarang);
                    totalmaret=totalmaret+maret;
                    
                    april=Sequel.cariIsiAngka("select sum(ipsrsdetailpesan.total) as total from ipsrspemesanan "+
                            "inner join ipsrsdetailpesan on ipsrspemesanan.no_faktur=ipsrsdetailpesan.no_faktur "+
                            "inner join ipsrsbarang on ipsrsdetailpesan.kode_brng=ipsrsbarang.kode_brng "+
                            "where ipsrspemesanan.kode_suplier='"+rs.getString("kode_suplier")+"' and "+
                            "left(ipsrspemesanan.tgl_pesan,7)='"+ThnCari.getSelectedItem().toString()+"-04' "+
                            kodejenis+kodebarang);
                    totalapril=totalapril+april;
                    
                    mei=Sequel.cariIsiAngka("select sum(ipsrsdetailpesan.total) as total from ipsrspemesanan "+
                            "inner join ipsrsdetailpesan on ipsrspemesanan.no_faktur=ipsrsdetailpesan.no_faktur "+
                            "inner join ipsrsbarang on ipsrsdetailpesan.kode_brng=ipsrsbarang.kode_brng "+
                            "where ipsrspemesanan.kode_suplier='"+rs.getString("kode_suplier")+"' and "+
                            "left(ipsrspemesanan.tgl_pesan,7)='"+ThnCari.getSelectedItem().toString()+"-05' "+
                            kodejenis+kodebarang);
                    totalmei=totalmei+mei;
                    
                    juni=Sequel.cariIsiAngka("select sum(ipsrsdetailpesan.total) as total from ipsrspemesanan "+
                            "inner join ipsrsdetailpesan on ipsrspemesanan.no_faktur=ipsrsdetailpesan.no_faktur "+
                            "inner join ipsrsbarang on ipsrsdetailpesan.kode_brng=ipsrsbarang.kode_brng "+
                            "where ipsrspemesanan.kode_suplier='"+rs.getString("kode_suplier")+"' and "+
                            "left(ipsrspemesanan.tgl_pesan,7)='"+ThnCari.getSelectedItem().toString()+"-06' "+
                            kodejenis+kodebarang);
                    totaljuni=totaljuni+juni;
                    
                    juli=Sequel.cariIsiAngka("select sum(ipsrsdetailpesan.total) as total from ipsrspemesanan "+
                            "inner join ipsrsdetailpesan on ipsrspemesanan.no_faktur=ipsrsdetailpesan.no_faktur "+
                            "inner join ipsrsbarang on ipsrsdetailpesan.kode_brng=ipsrsbarang.kode_brng "+
                            "where ipsrspemesanan.kode_suplier='"+rs.getString("kode_suplier")+"' and "+
                            "left(ipsrspemesanan.tgl_pesan,7)='"+ThnCari.getSelectedItem().toString()+"-07' "+
                            kodejenis+kodebarang);
                    totaljuli=totaljuli+juli;
                    
                    agustus=Sequel.cariIsiAngka("select sum(ipsrsdetailpesan.total) as total from ipsrspemesanan "+
                            "inner join ipsrsdetailpesan on ipsrspemesanan.no_faktur=ipsrsdetailpesan.no_faktur "+
                            "inner join ipsrsbarang on ipsrsdetailpesan.kode_brng=ipsrsbarang.kode_brng "+
                            "where ipsrspemesanan.kode_suplier='"+rs.getString("kode_suplier")+"' and "+
                            "left(ipsrspemesanan.tgl_pesan,7)='"+ThnCari.getSelectedItem().toString()+"-08' "+
                            kodejenis+kodebarang);
                    totalagustus=totalagustus+agustus;
                    
                    september=Sequel.cariIsiAngka("select sum(ipsrsdetailpesan.total) as total from ipsrspemesanan "+
                            "inner join ipsrsdetailpesan on ipsrspemesanan.no_faktur=ipsrsdetailpesan.no_faktur "+
                            "inner join ipsrsbarang on ipsrsdetailpesan.kode_brng=ipsrsbarang.kode_brng "+
                            "where ipsrspemesanan.kode_suplier='"+rs.getString("kode_suplier")+"' and "+
                            "left(ipsrspemesanan.tgl_pesan,7)='"+ThnCari.getSelectedItem().toString()+"-09' "+
                            kodejenis+kodebarang);
                    totalseptember=totalseptember+september;
                    
                    oktober=Sequel.cariIsiAngka("select sum(ipsrsdetailpesan.total) as total from ipsrspemesanan "+
                            "inner join ipsrsdetailpesan on ipsrspemesanan.no_faktur=ipsrsdetailpesan.no_faktur "+
                            "inner join ipsrsbarang on ipsrsdetailpesan.kode_brng=ipsrsbarang.kode_brng "+
                            "where ipsrspemesanan.kode_suplier='"+rs.getString("kode_suplier")+"' and "+
                            "left(ipsrspemesanan.tgl_pesan,7)='"+ThnCari.getSelectedItem().toString()+"-10' "+
                            kodejenis+kodebarang);
                    totaloktober=totaloktober+oktober;
                    
                    november=Sequel.cariIsiAngka("select sum(ipsrsdetailpesan.total) as total from ipsrspemesanan "+
                            "inner join ipsrsdetailpesan on ipsrspemesanan.no_faktur=ipsrsdetailpesan.no_faktur "+
                            "inner join ipsrsbarang on ipsrsdetailpesan.kode_brng=ipsrsbarang.kode_brng "+
                            "where ipsrspemesanan.kode_suplier='"+rs.getString("kode_suplier")+"' and "+
                            "left(ipsrspemesanan.tgl_pesan,7)='"+ThnCari.getSelectedItem().toString()+"-11' "+
                            kodejenis+kodebarang);
                    totalnovember=totalnovember+november;
                    
                    desember=Sequel.cariIsiAngka("select sum(ipsrsdetailpesan.total) as total from ipsrspemesanan "+
                            "inner join ipsrsdetailpesan on ipsrspemesanan.no_faktur=ipsrsdetailpesan.no_faktur "+
                            "inner join ipsrsbarang on ipsrsdetailpesan.kode_brng=ipsrsbarang.kode_brng "+
                            "where ipsrspemesanan.kode_suplier='"+rs.getString("kode_suplier")+"' and "+
                            "left(ipsrspemesanan.tgl_pesan,7)='"+ThnCari.getSelectedItem().toString()+"-12' "+
                            kodejenis+kodebarang);
                    totaldesember=totaldesember+desember;
                    
                    tagihan=januari+februari+maret+april+mei+juni+juli+agustus+september+oktober+november+desember;
                    totaltagihan=totaltagihan+tagihan;
                    
                    tabMode.addRow(new Object[]{
                        rs.getString("kode_suplier"),rs.getString("nama_suplier"),januari,februari,maret,april,mei,juni,juli,agustus,september,oktober,november,desember,tagihan
                    });
                }
                if(tabMode.getRowCount()>0){
                    tabMode.addRow(new Object[]{
                        "Total :","",totaljanuari,totalfebruari,totalmaret,totalapril,totalmei,totaljuni,totaljuli,totalagustus,totalseptember,totaloktober,totalnovember,totaldesember,totaltagihan
                    });
                }
            } catch (Exception e) {
                System.out.println("Notif : "+e);
            } finally{
                
            }
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }        
    }

    public void emptTeks() {
        kdbar.setText("");
        nmbar.setText("");
        kdjenis.setText("");
        kdbar.requestFocus();        
    }
    
    public void isCek(){
        BtnPrint.setEnabled(akses.getnilai_penerimaan_vendor_nonmedis_perbulan());
    }
    
}

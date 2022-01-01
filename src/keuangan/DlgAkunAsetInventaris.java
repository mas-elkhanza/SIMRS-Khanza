/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgJadwal.java
 *
 * Created on May 22, 2010, 10:25:16 PM
 */

package keuangan;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import inventaris.InventarisJenis;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author dosen
 */
public class DlgAkunAsetInventaris extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private DlgRekeningTahun rekening=new DlgRekeningTahun(null,false);
    private InventarisJenis jenis=new InventarisJenis(null,false);

    /** Creates new form DlgJadwal
     * @param parent
     * @param modal */
    public DlgAkunAsetInventaris(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        Object[] row={"P","ID Jenis","Nama Jenis","Kode Akun","Nama Akun Rekening"};
        tabMode=new DefaultTableModel(null,row){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                 java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class                
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbJadwal.setModel(tabMode);

        tbJadwal.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbJadwal.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 5; i++) {
            TableColumn column = tbJadwal.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(80);
            }else if(i==2){
                column.setPreferredWidth(215);
            }else if(i==3){
                column.setPreferredWidth(85);
            }else if(i==4){
                column.setPreferredWidth(220);
            }
        }
        
        tbJadwal.setDefaultRenderer(Object.class, new WarnaTable());
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        kdrek.setDocument(new batasInput((byte)15).getKata(kdrek));
        kdjenis.setDocument(new batasInput((int)10).getKata(kdjenis));
        
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
        
        rekening.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgAkunAsetInventaris")){
                    if(rekening.getTabel().getSelectedRow()!= -1){      
                        kdrek.setText(rekening.getTabel().getValueAt(rekening.getTabel().getSelectedRow(),1).toString());
                        nmrek.setText(rekening.getTabel().getValueAt(rekening.getTabel().getSelectedRow(),2).toString()); 
                        kdrek.requestFocus();
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
        
        rekening.getTabel().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(akses.getform().equals("DlgAkunAsetInventaris")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        rekening.dispose();
                    }
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });  
        
        jenis.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(jenis.getTable().getSelectedRow()!= -1){
                    kdjenis.setText(jenis.getTable().getValueAt(jenis.getTable().getSelectedRow(),0).toString());
                    nmjenis.setText(jenis.getTable().getValueAt(jenis.getTable().getSelectedRow(),1).toString());
                } 
                kdjenis.requestFocus();
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
        
        jenis.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    jenis.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        emptTeks();        
        
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
        tbJadwal = new widget.Table();
        jPanel3 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        BtnKeluar = new widget.Button();
        BtnSemua = new widget.Button();
        panelGlass9 = new widget.panelisi();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();
        panelBiasa1 = new widget.PanelBiasa();
        jLabel10 = new widget.Label();
        nmrek = new widget.TextBox();
        kdrek = new widget.TextBox();
        BtnPoli = new widget.Button();
        jLabel18 = new widget.Label();
        kdjenis = new widget.TextBox();
        nmjenis = new widget.TextBox();
        BtnJenis = new widget.Button();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Akun Jenis Aset/Inventaris ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbJadwal.setAutoCreateRowSorter(true);
        tbJadwal.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbJadwal.setName("tbJadwal"); // NOI18N
        tbJadwal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbJadwalMouseClicked(evt);
            }
        });
        tbJadwal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbJadwalKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbJadwal);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(44, 100));
        jPanel3.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass8.setLayout(null);

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan.setMnemonic('S');
        BtnSimpan.setText("Simpan");
        BtnSimpan.setToolTipText("Alt+S");
        BtnSimpan.setName("BtnSimpan"); // NOI18N
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
        BtnSimpan.setBounds(6, 10, 100, 30);

        BtnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Cancel-2-16x16.png"))); // NOI18N
        BtnBatal.setMnemonic('B');
        BtnBatal.setText("Baru");
        BtnBatal.setToolTipText("Alt+B");
        BtnBatal.setName("BtnBatal"); // NOI18N
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
        BtnBatal.setBounds(108, 10, 100, 30);

        BtnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); // NOI18N
        BtnHapus.setMnemonic('H');
        BtnHapus.setText("Hapus");
        BtnHapus.setToolTipText("Alt+H");
        BtnHapus.setName("BtnHapus"); // NOI18N
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
        BtnHapus.setBounds(210, 10, 100, 30);

        BtnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/inventaris.png"))); // NOI18N
        BtnEdit.setMnemonic('G');
        BtnEdit.setText("Ganti");
        BtnEdit.setToolTipText("Alt+G");
        BtnEdit.setName("BtnEdit"); // NOI18N
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
        BtnEdit.setBounds(312, 10, 100, 30);

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar.setMnemonic('K');
        BtnKeluar.setText("Keluar");
        BtnKeluar.setToolTipText("Alt+K");
        BtnKeluar.setName("BtnKeluar"); // NOI18N
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
        BtnKeluar.setBounds(516, 10, 100, 30);

        BtnSemua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnSemua.setMnemonic('4');
        BtnSemua.setText("Semua");
        BtnSemua.setToolTipText("Alt+4");
        BtnSemua.setName("BtnSemua"); // NOI18N
        BtnSemua.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSemua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSemuaActionPerformed(evt);
            }
        });
        BtnSemua.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSemuaKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnSemua);
        BtnSemua.setBounds(414, 10, 100, 30);

        jPanel3.add(panelGlass8, java.awt.BorderLayout.CENTER);

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(340, 23));
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

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(65, 23));
        panelGlass9.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(50, 23));
        panelGlass9.add(LCount);

        jPanel3.add(panelGlass9, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        panelBiasa1.setName("panelBiasa1"); // NOI18N
        panelBiasa1.setPreferredSize(new java.awt.Dimension(1023, 77));
        panelBiasa1.setLayout(null);

        jLabel10.setText("Akun/Rekening/COA :");
        jLabel10.setName("jLabel10"); // NOI18N
        panelBiasa1.add(jLabel10);
        jLabel10.setBounds(0, 42, 125, 23);

        nmrek.setEditable(false);
        nmrek.setHighlighter(null);
        nmrek.setName("nmrek"); // NOI18N
        nmrek.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nmrekKeyPressed(evt);
            }
        });
        panelBiasa1.add(nmrek);
        nmrek.setBounds(232, 42, 342, 23);

        kdrek.setEditable(false);
        kdrek.setHighlighter(null);
        kdrek.setName("kdrek"); // NOI18N
        kdrek.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdrekKeyPressed(evt);
            }
        });
        panelBiasa1.add(kdrek);
        kdrek.setBounds(129, 42, 100, 23);

        BtnPoli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPoli.setMnemonic('2');
        BtnPoli.setToolTipText("ALt+2");
        BtnPoli.setName("BtnPoli"); // NOI18N
        BtnPoli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPoliActionPerformed(evt);
            }
        });
        panelBiasa1.add(BtnPoli);
        BtnPoli.setBounds(577, 42, 28, 23);

        jLabel18.setText("Jenis Aset/Inventaris :");
        jLabel18.setName("jLabel18"); // NOI18N
        panelBiasa1.add(jLabel18);
        jLabel18.setBounds(0, 12, 125, 23);

        kdjenis.setEditable(false);
        kdjenis.setToolTipText("Tekan ENTER untuk lanjut ke field berikutnya, tekan PAGE UP untuk ke field sebelumnya, Tekan UP untuk menampilkan data Jenis Pembayaran");
        kdjenis.setHighlighter(null);
        kdjenis.setName("kdjenis"); // NOI18N
        kdjenis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdjenisKeyPressed(evt);
            }
        });
        panelBiasa1.add(kdjenis);
        kdjenis.setBounds(129, 12, 100, 23);

        nmjenis.setEditable(false);
        nmjenis.setName("nmjenis"); // NOI18N
        panelBiasa1.add(nmjenis);
        nmjenis.setBounds(232, 12, 342, 23);

        BtnJenis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnJenis.setMnemonic('2');
        BtnJenis.setToolTipText("ALt+2");
        BtnJenis.setName("BtnJenis"); // NOI18N
        BtnJenis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnJenisActionPerformed(evt);
            }
        });
        panelBiasa1.add(BtnJenis);
        BtnJenis.setBounds(577, 12, 28, 23);

        internalFrame1.add(panelBiasa1, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void nmrekKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nmrekKeyPressed
        //Valid.pindah(evt,TJns,BtnSimpan);
}//GEN-LAST:event_nmrekKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(kdjenis.getText().trim().equals("")||nmjenis.getText().trim().equals("")){
            Valid.textKosong(kdjenis,"Jenis Inventaris");
        }else if(kdrek.getText().trim().equals("")||nmrek.getText().trim().equals("")){
            Valid.textKosong(kdrek,"Rekening");
        }else{
            Sequel.menyimpan("akun_aset_inventaris","?,?","Nama Jenis",2,new String[]{
                kdrek.getText(),kdjenis.getText()
            });
            tampil();
            emptTeks();
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,kdrek,BtnBatal);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        emptTeks();
}//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            emptTeks();
        }else{Valid.pindah(evt, BtnSimpan, BtnHapus);}
}//GEN-LAST:event_BtnBatalKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        for(int i=0;i<tbJadwal.getRowCount();i++){ 
            if(tbJadwal.getValueAt(i,0).toString().equals("true")){
                Sequel.meghapus("akun_aset_inventaris","id_jenis",tbJadwal.getValueAt(i,1).toString());
            }
        } 
        tampil();
        emptTeks();
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnBatal, BtnEdit);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
        if(kdjenis.getText().trim().equals("")||nmjenis.getText().trim().equals("")){
            Valid.textKosong(kdjenis,"Jenis Inventaris");
        }else if(kdrek.getText().trim().equals("")||nmrek.getText().trim().equals("")){
            Valid.textKosong(kdrek,"Rekening");
        }else{
            if(tbJadwal.getSelectedRow()>-1){
                Sequel.mengedit("akun_aset_inventaris","id_jenis=?","id_jenis=?,kd_rek=?",3,new String[]{
                    kdjenis.getText(),kdrek.getText(),tbJadwal.getValueAt(tbJadwal.getSelectedRow(),1).toString()
                });
                tampil();
                emptTeks();
            }else{
                JOptionPane.showMessageDialog(rootPane,"Silahkan pilih data pada tabel terlebih dahulu");
                tbJadwal.requestFocus();
            }            
        }
}//GEN-LAST:event_BtnEditActionPerformed

    private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnEditActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnSemua);
        }
}//GEN-LAST:event_BtnEditKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnSemua,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

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
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari, BtnSemua);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    private void BtnSemuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSemuaActionPerformed
        TCari.setText("");
        tampil();
}//GEN-LAST:event_BtnSemuaActionPerformed

    private void BtnSemuaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSemuaKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            tampil();
            TCari.setText("");
        }else{
           // Valid.pindah(evt, BtnCari,kddokter);
        }
}//GEN-LAST:event_BtnSemuaKeyPressed

    private void tbJadwalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbJadwalMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbJadwalMouseClicked

    private void tbJadwalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbJadwalKeyPressed
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
}//GEN-LAST:event_tbJadwalKeyPressed

private void kdrekKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdrekKeyPressed
        Valid.pindah(evt,kdrek,BtnSimpan);
}//GEN-LAST:event_kdrekKeyPressed

private void BtnPoliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPoliActionPerformed
        akses.setform("DlgAkunAsetInventaris");
        rekening.emptTeks();
        rekening.tampil();
        rekening.isCek();
        rekening.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        rekening.setLocationRelativeTo(internalFrame1);
        rekening.setVisible(true);
}//GEN-LAST:event_BtnPoliActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void kdjenisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdjenisKeyPressed
        Valid.pindah(evt,TCari,kdrek);
    }//GEN-LAST:event_kdjenisKeyPressed

    private void BtnJenisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnJenisActionPerformed
        jenis.isCek();
        jenis.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        jenis.setLocationRelativeTo(internalFrame1);
        jenis.setVisible(true);
    }//GEN-LAST:event_BtnJenisActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgAkunAsetInventaris dialog = new DlgAkunAsetInventaris(new javax.swing.JFrame(), true);
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
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnJenis;
    private widget.Button BtnKeluar;
    private widget.Button BtnPoli;
    private widget.Button BtnSemua;
    private widget.Button BtnSimpan;
    private widget.Label LCount;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel10;
    private widget.Label jLabel18;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private javax.swing.JPanel jPanel3;
    private widget.TextBox kdjenis;
    private widget.TextBox kdrek;
    private widget.TextBox nmjenis;
    private widget.TextBox nmrek;
    private widget.PanelBiasa panelBiasa1;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.Table tbJadwal;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            ps=koneksi.prepareStatement(
                    "select akun_aset_inventaris.id_jenis,inventaris_jenis.nama_jenis,akun_aset_inventaris.kd_rek,rekening.nm_rek "+
                    "from akun_aset_inventaris inner join rekening on akun_aset_inventaris.kd_rek=rekening.kd_rek "+
                    "inner join inventaris_jenis on akun_aset_inventaris.id_jenis=inventaris_jenis.id_jenis "+(TCari.getText().trim().equals("")?
                    "":"where akun_aset_inventaris.id_jenis like ? or inventaris_jenis.nama_jenis like ? "+
                    "or akun_aset_inventaris.kd_rek like ? or rekening.nm_rek like ? ")+
                    "order by akun_aset_inventaris.id_jenis");
            try {
                if(!TCari.getText().trim().equals("")){
                    ps.setString(1,"%"+TCari.getText().trim()+"%");
                    ps.setString(2,"%"+TCari.getText().trim()+"%");
                    ps.setString(3,"%"+TCari.getText().trim()+"%");
                    ps.setString(4,"%"+TCari.getText().trim()+"%");
                }
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new Object[]{
                        false,rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4)
                    });
                }
            } catch (Exception e) {
                System.out.println(e);
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
       nmrek.setText("");
       kdrek.setText("");
       kdjenis.setText("");
       nmjenis.setText("");
       BtnJenis.requestFocus();
    }

    private void getData() {
        int row=tbJadwal.getSelectedRow();
        if(row!= -1){
            kdjenis.setText(tabMode.getValueAt(row,1).toString());
            nmjenis.setText(tabMode.getValueAt(row,2).toString());
            kdrek.setText(tabMode.getValueAt(row,3).toString());
            nmrek.setText(tabMode.getValueAt(row,4).toString());
        }
    }
    
    public JTable getTable(){
        return tbJadwal;
    }
    
    public void isCek(){
        BtnSimpan.setEnabled(akses.getakun_aset_inventaris());
        BtnHapus.setEnabled(akses.getakun_aset_inventaris());
        BtnEdit.setEnabled(akses.getakun_aset_inventaris());
        BtnSemua.setEnabled(akses.getakun_aset_inventaris());
    }
    
    
}

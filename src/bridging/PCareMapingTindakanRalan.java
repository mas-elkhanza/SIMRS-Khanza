/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgJnsPerawatanRalan.java
 *
 * Created on May 22, 2010, 11:58:21 PM
 */

package bridging;
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
import keuangan.DlgJnsPerawatanRalan;

/**
 *
 * @author dosen
 */
public final class PCareMapingTindakanRalan extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement ps;
    private ResultSet rs;    
    private int i=0;
    private DlgJnsPerawatanRalan barang=new DlgJnsPerawatanRalan(null,false);
    private PCareCekReferensiTindakan barangpcare=new PCareCekReferensiTindakan(null,false);
    

    /** Creates new form DlgJnsPerawatanRalan
     * @param parent
     * @param modal */
    public PCareMapingTindakanRalan(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        this.setLocation(8,1);
        setSize(628,674);

        tabMode=new DefaultTableModel(null,new Object[]{
            "Kode RS","Nama Tindakan Ralan","Kode PCare","Nama Tindakan PCare"}){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbJnsPerawatan.setModel(tabMode);

        tbJnsPerawatan.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbJnsPerawatan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 4; i++) {
            TableColumn column = tbJnsPerawatan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(90);
            }else if(i==1){
                column.setPreferredWidth(300);
            }else if(i==2){
                column.setPreferredWidth(90);
            }else if(i==3){
                column.setPreferredWidth(300);
            }
        }
        tbJnsPerawatan.setDefaultRenderer(Object.class, new WarnaTable());

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
        
        barang.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(barang.getTable().getSelectedRow()!= -1){                    
                    kdtindakan.setText(barang.getTable().getValueAt(barang.getTable().getSelectedRow(),1).toString());
                    TTindakan.setText(barang.getTable().getValueAt(barang.getTable().getSelectedRow(),2).toString());
                }
                kdtindakan.requestFocus();
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
        
        barangpcare.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(barangpcare.getTable().getSelectedRow()!= -1){                   
                    KdTindakanPCare.setText(barangpcare.getTable().getValueAt(barangpcare.getTable().getSelectedRow(),1).toString());
                    NmTindakanPCare.setText(barangpcare.getTable().getValueAt(barangpcare.getTable().getSelectedRow(),2).toString());
                    KdTindakanPCare.requestFocus();
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
        
        barangpcare.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    barangpcare.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
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
        tbJnsPerawatan = new widget.Table();
        jPanel3 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        BtnPrint = new widget.Button();
        BtnAll = new widget.Button();
        BtnKeluar = new widget.Button();
        panelGlass9 = new widget.panelisi();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();
        FormInput = new widget.PanelBiasa();
        jLabel4 = new widget.Label();
        kdtindakan = new widget.TextBox();
        TTindakan = new widget.TextBox();
        btnPoliRS = new widget.Button();
        jLabel19 = new widget.Label();
        KdTindakanPCare = new widget.TextBox();
        NmTindakanPCare = new widget.TextBox();
        btnPoliBPJS = new widget.Button();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Mapping Tindakan Rawat Jalan di PCare ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50,50,50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbJnsPerawatan.setAutoCreateRowSorter(true);
        tbJnsPerawatan.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbJnsPerawatan.setName("tbJnsPerawatan"); // NOI18N
        tbJnsPerawatan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbJnsPerawatanMouseClicked(evt);
            }
        });
        tbJnsPerawatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbJnsPerawatanKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbJnsPerawatan);

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

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(450, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass9.add(TCari);

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
        panelGlass9.add(BtnCari);

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(75, 23));
        panelGlass9.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass9.add(LCount);

        jPanel3.add(panelGlass9, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(100, 74));
        FormInput.setLayout(null);

        jLabel4.setText("Tindakan Ralan :");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(0, 10, 95, 23);

        kdtindakan.setEditable(false);
        kdtindakan.setHighlighter(null);
        kdtindakan.setName("kdtindakan"); // NOI18N
        FormInput.add(kdtindakan);
        kdtindakan.setBounds(100, 10, 115, 23);

        TTindakan.setEditable(false);
        TTindakan.setHighlighter(null);
        TTindakan.setName("TTindakan"); // NOI18N
        FormInput.add(TTindakan);
        TTindakan.setBounds(217, 10, 480, 23);

        btnPoliRS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPoliRS.setMnemonic('1');
        btnPoliRS.setToolTipText("Alt+1");
        btnPoliRS.setName("btnPoliRS"); // NOI18N
        btnPoliRS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPoliRSActionPerformed(evt);
            }
        });
        btnPoliRS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnPoliRSKeyPressed(evt);
            }
        });
        FormInput.add(btnPoliRS);
        btnPoliRS.setBounds(700, 10, 28, 23);

        jLabel19.setText("Tindakan PCare :");
        jLabel19.setName("jLabel19"); // NOI18N
        FormInput.add(jLabel19);
        jLabel19.setBounds(0, 40, 95, 23);

        KdTindakanPCare.setEditable(false);
        KdTindakanPCare.setHighlighter(null);
        KdTindakanPCare.setName("KdTindakanPCare"); // NOI18N
        FormInput.add(KdTindakanPCare);
        KdTindakanPCare.setBounds(100, 40, 115, 23);

        NmTindakanPCare.setEditable(false);
        NmTindakanPCare.setName("NmTindakanPCare"); // NOI18N
        FormInput.add(NmTindakanPCare);
        NmTindakanPCare.setBounds(217, 40, 480, 23);

        btnPoliBPJS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPoliBPJS.setMnemonic('3');
        btnPoliBPJS.setToolTipText("ALt+3");
        btnPoliBPJS.setName("btnPoliBPJS"); // NOI18N
        btnPoliBPJS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPoliBPJSActionPerformed(evt);
            }
        });
        FormInput.add(btnPoliBPJS);
        btnPoliBPJS.setBounds(700, 40, 28, 23);

        internalFrame1.add(FormInput, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnPoliRSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPoliRSActionPerformed
        barang.isCek();        
        barang.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        barang.setLocationRelativeTo(internalFrame1);
        barang.setVisible(true);
}//GEN-LAST:event_btnPoliRSActionPerformed

    private void btnPoliRSKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPoliRSKeyPressed
        
}//GEN-LAST:event_btnPoliRSKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(kdtindakan.getText().trim().equals("")||TTindakan.getText().trim().equals("")){
            Valid.textKosong(kdtindakan,"Tindakan RS");
        }else if(KdTindakanPCare.getText().trim().equals("")||NmTindakanPCare.getText().trim().equals("")){
            Valid.textKosong(KdTindakanPCare,"Tindakan PCare");
        }else{
            if(Sequel.menyimpantf("maping_tindakan_pcare","?,?,?","Mapping Tindakan",3,new String[]{
                kdtindakan.getText(),KdTindakanPCare.getText(),NmTindakanPCare.getText()
            })==true){
                tampil();
                emptTeks();
            }                
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{Valid.pindah(evt,btnPoliBPJS, BtnBatal);}
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
        Valid.hapusTable(tabMode,kdtindakan,"maping_tindakan_pcare","kd_jenis_prw");
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
        if(kdtindakan.getText().trim().equals("")||TTindakan.getText().trim().equals("")){
            Valid.textKosong(kdtindakan,"Tindakan RS");
        }else if(KdTindakanPCare.getText().trim().equals("")||NmTindakanPCare.getText().trim().equals("")){
            Valid.textKosong(KdTindakanPCare,"Tindakan PCare");
        }else{
            if(tbJnsPerawatan.getSelectedRow()>-1){
                if(Sequel.mengedittf("maping_tindakan_pcare","kd_jenis_prw=?","kd_jenis_prw=?,kd_tindakan_pcare=?,nm_tindakan_pcare=?",4,new String[]{
                        kdtindakan.getText(),KdTindakanPCare.getText(),NmTindakanPCare.getText(),tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),0).toString()
                    })==true){
                    emptTeks();
                    tampil();
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
                param.put("parameter","%"+TCari.getText().trim()+"%");   
                Valid.MyReport("rptMapingTindakanPCare.jasper","report","::[ Mapping Tindakan PCare ]::",param);            
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
            tampil();
            TCari.setText("");
        }else{
            Valid.pindah(evt, BtnPrint, BtnKeluar);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void tbJnsPerawatanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbJnsPerawatanMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbJnsPerawatanMouseClicked

    private void tbJnsPerawatanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbJnsPerawatanKeyPressed
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
}//GEN-LAST:event_tbJnsPerawatanKeyPressed

private void btnPoliBPJSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPoliBPJSActionPerformed
    barangpcare.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
    barangpcare.setLocationRelativeTo(internalFrame1);
    barangpcare.setVisible(true);
}//GEN-LAST:event_btnPoliBPJSActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
        emptTeks();
    }//GEN-LAST:event_formWindowOpened

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            PCareMapingTindakanRalan dialog = new PCareMapingTindakanRalan(new javax.swing.JFrame(), true);
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
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.PanelBiasa FormInput;
    private widget.TextBox KdTindakanPCare;
    private widget.Label LCount;
    private widget.TextBox NmTindakanPCare;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.TextBox TTindakan;
    private widget.Button btnPoliBPJS;
    private widget.Button btnPoliRS;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel19;
    private widget.Label jLabel4;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private javax.swing.JPanel jPanel3;
    private widget.TextBox kdtindakan;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.Table tbJnsPerawatan;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try{
           ps=koneksi.prepareStatement(
                   "select maping_tindakan_pcare.kd_jenis_prw,jns_perawatan.nm_perawatan,maping_tindakan_pcare.kd_tindakan_pcare,maping_tindakan_pcare.nm_tindakan_pcare "+
                   "from maping_tindakan_pcare inner join jns_perawatan on maping_tindakan_pcare.kd_jenis_prw=jns_perawatan.kd_jenis_prw  where "+
                   "maping_tindakan_pcare.kd_jenis_prw like ? or jns_perawatan.nm_perawatan like ? or maping_tindakan_pcare.kd_tindakan_pcare like ? or maping_tindakan_pcare.nm_tindakan_pcare like ? order by jns_perawatan.nm_perawatan");
            try {
                ps.setString(1,"%"+TCari.getText()+"%");
                ps.setString(2,"%"+TCari.getText()+"%");
                ps.setString(3,"%"+TCari.getText()+"%");
                ps.setString(4,"%"+TCari.getText()+"%");
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new Object[]{
                        rs.getString("kd_jenis_prw"),rs.getString("nm_perawatan"),rs.getString("kd_tindakan_pcare"),rs.getString("nm_tindakan_pcare")
                    });
                }
            } catch (Exception e) {
                System.out.println("Notif Ketersediaan : "+e);
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
        kdtindakan.setText("");
        TTindakan.setText("");
        KdTindakanPCare.setText("");
        NmTindakanPCare.setText("");
        kdtindakan.requestFocus();
    }

    private void getData() {
       if(tbJnsPerawatan.getSelectedRow()!= -1){
           kdtindakan.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),0).toString());
           TTindakan.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),1).toString());
           KdTindakanPCare.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),2).toString());
           NmTindakanPCare.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),3).toString());
        }
    }

    
   
    
    
    public void isCek(){
        BtnSimpan.setEnabled(akses.getpcare_mapping_tindakan());
        BtnHapus.setEnabled(akses.getpcare_mapping_tindakan());
        BtnEdit.setEnabled(akses.getpcare_mapping_tindakan());
        BtnPrint.setEnabled(akses.getpcare_mapping_tindakan());
    }
    
    public JTable getTable(){
        return tbJnsPerawatan;
    }    

   
    
    
    

    
}

    /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgSpesialis.java
 *
 * Created on May 23, 2010, 1:25:13 AM
 */

package bridging;

import fungsi.WarnaTable;
import fungsi.akses;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import keuangan.DlgRekeningTahun;

/**
 *
 * @author dosen
 */
public class BRIApiIntegrasi extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private DlgRekeningTahun rekening=new DlgRekeningTahun(null,false);

    /** Creates new form DlgSpesialis
     * @param parent
     * @param modal */
    public BRIApiIntegrasi(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        Object[] row={"Kode Akun","Akun Rekening","Consumer Key","Consumer Secret","Institution Code","BRIVA No","URL API"};
        tabMode=new DefaultTableModel(null,row){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
             Class[] types = new Class[] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };

        tbSpesialis.setModel(tabMode);
        //tampil();
        //tbJabatan.setDefaultRenderer(Object.class, new WarnaTable(Scroll.getBackground(),Color.GREEN));
        tbSpesialis.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbSpesialis.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 7; i++) {
            TableColumn column = tbSpesialis.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(65);
            }else if(i==1){
                column.setPreferredWidth(200);
            }else if(i==2){
                column.setPreferredWidth(230);
            }else if(i==3){
                column.setPreferredWidth(130);
            }else if(i==4){
                column.setPreferredWidth(90);
            }else if(i==5){
                column.setPreferredWidth(70);
            }else if(i==6){
                column.setPreferredWidth(250);
            }
        }

        tbSpesialis.setDefaultRenderer(Object.class, new WarnaTable());

        kdrek.setDocument(new batasInput((byte)15).getKata(kdrek));
        ConsumerKey.setDocument(new batasInput((byte)35).getKata(ConsumerKey));
        ConsumerSecret.setDocument(new batasInput((byte)35).getKata(ConsumerSecret));
        InstitutionCode.setDocument(new batasInput((byte)15).getKata(InstitutionCode));
        BrivaNo.setDocument(new batasInput((byte)15).getKata(BrivaNo));
        UrlApi.setDocument(new batasInput((int)100).getKata(UrlApi));
        
        rekening.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("BRIApiIntegrasi")){
                    if(rekening.getTabel().getSelectedRow()!= -1){      
                        if(rekening.getTabel().getValueAt(rekening.getTabel().getSelectedRow(),3).toString().equals("N")&&
                                rekening.getTabel().getValueAt(rekening.getTabel().getSelectedRow(),4).toString().equals("D")){
                            kdrek.setText(rekening.getTabel().getValueAt(rekening.getTabel().getSelectedRow(),1).toString());
                            nmrek.setText(rekening.getTabel().getValueAt(rekening.getTabel().getSelectedRow(),2).toString()); 
                        }else{
                            JOptionPane.showMessageDialog(rootPane,"Rekening harus Tipe N dan Balance D..!!");
                        }
                                                                      
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
                if(akses.getform().equals("BRIApiIntegrasi")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        rekening.dispose();
                    }
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
        tbSpesialis = new widget.Table();
        panelGlass7 = new widget.panelisi();
        jLabel3 = new widget.Label();
        kdrek = new widget.TextBox();
        nmrek = new widget.TextBox();
        BtnPenjab = new widget.Button();
        jLabel4 = new widget.Label();
        ConsumerKey = new widget.TextBox();
        ConsumerSecret = new widget.TextBox();
        jLabel5 = new widget.Label();
        jLabel6 = new widget.Label();
        InstitutionCode = new widget.TextBox();
        jLabel7 = new widget.Label();
        BrivaNo = new widget.TextBox();
        jLabel8 = new widget.Label();
        UrlApi = new widget.TextBox();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        BtnKeluar = new widget.Button();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Setup Integrasi BRI Api & Akun Rekening Bank BRI ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbSpesialis.setAutoCreateRowSorter(true);
        tbSpesialis.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbSpesialis.setName("tbSpesialis"); // NOI18N
        tbSpesialis.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbSpesialisMouseClicked(evt);
            }
        });
        tbSpesialis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbSpesialisKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbSpesialis);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        panelGlass7.setName("panelGlass7"); // NOI18N
        panelGlass7.setPreferredSize(new java.awt.Dimension(74, 136));
        panelGlass7.setLayout(null);

        jLabel3.setText("Akun Rekening :");
        jLabel3.setName("jLabel3"); // NOI18N
        panelGlass7.add(jLabel3);
        jLabel3.setBounds(1, 10, 100, 23);

        kdrek.setEditable(false);
        kdrek.setHighlighter(null);
        kdrek.setName("kdrek"); // NOI18N
        panelGlass7.add(kdrek);
        kdrek.setBounds(104, 10, 108, 23);

        nmrek.setEditable(false);
        nmrek.setHighlighter(null);
        nmrek.setName("nmrek"); // NOI18N
        nmrek.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nmrekKeyPressed(evt);
            }
        });
        panelGlass7.add(nmrek);
        nmrek.setBounds(215, 10, 270, 23);

        BtnPenjab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPenjab.setMnemonic('1');
        BtnPenjab.setToolTipText("Alt+1");
        BtnPenjab.setName("BtnPenjab"); // NOI18N
        BtnPenjab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenjabActionPerformed(evt);
            }
        });
        BtnPenjab.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPenjabKeyPressed(evt);
            }
        });
        panelGlass7.add(BtnPenjab);
        BtnPenjab.setBounds(490, 10, 28, 23);

        jLabel4.setText("Consumer Key :");
        jLabel4.setName("jLabel4"); // NOI18N
        panelGlass7.add(jLabel4);
        jLabel4.setBounds(1, 42, 100, 23);

        ConsumerKey.setHighlighter(null);
        ConsumerKey.setName("ConsumerKey"); // NOI18N
        ConsumerKey.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ConsumerKeyKeyPressed(evt);
            }
        });
        panelGlass7.add(ConsumerKey);
        ConsumerKey.setBounds(104, 42, 150, 23);

        ConsumerSecret.setName("ConsumerSecret"); // NOI18N
        ConsumerSecret.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ConsumerSecretKeyPressed(evt);
            }
        });
        panelGlass7.add(ConsumerSecret);
        ConsumerSecret.setBounds(368, 42, 150, 23);

        jLabel5.setText("Consumer Secret :");
        jLabel5.setName("jLabel5"); // NOI18N
        panelGlass7.add(jLabel5);
        jLabel5.setBounds(255, 42, 110, 23);

        jLabel6.setText("Institution Code :");
        jLabel6.setName("jLabel6"); // NOI18N
        panelGlass7.add(jLabel6);
        jLabel6.setBounds(1, 72, 100, 23);

        InstitutionCode.setHighlighter(null);
        InstitutionCode.setName("InstitutionCode"); // NOI18N
        InstitutionCode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                InstitutionCodeKeyPressed(evt);
            }
        });
        panelGlass7.add(InstitutionCode);
        InstitutionCode.setBounds(104, 72, 150, 23);

        jLabel7.setText("BRIVA No :");
        jLabel7.setName("jLabel7"); // NOI18N
        panelGlass7.add(jLabel7);
        jLabel7.setBounds(255, 72, 110, 23);

        BrivaNo.setName("BrivaNo"); // NOI18N
        BrivaNo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BrivaNoKeyPressed(evt);
            }
        });
        panelGlass7.add(BrivaNo);
        BrivaNo.setBounds(368, 72, 150, 23);

        jLabel8.setText("URL API :");
        jLabel8.setName("jLabel8"); // NOI18N
        panelGlass7.add(jLabel8);
        jLabel8.setBounds(1, 102, 100, 23);

        UrlApi.setHighlighter(null);
        UrlApi.setName("UrlApi"); // NOI18N
        UrlApi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                UrlApiKeyPressed(evt);
            }
        });
        panelGlass7.add(UrlApi);
        UrlApi.setBounds(104, 102, 414, 23);

        internalFrame1.add(panelGlass7, java.awt.BorderLayout.PAGE_START);

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

        internalFrame1.add(panelGlass8, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void BtnPenjabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPenjabActionPerformed
        akses.setform("BRIApiIntegrasi");
        rekening.emptTeks();
        rekening.tampil();
        rekening.isCek();
        rekening.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        rekening.setLocationRelativeTo(internalFrame1);
        rekening.setVisible(true);
    }//GEN-LAST:event_BtnPenjabActionPerformed

    private void nmrekKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nmrekKeyPressed
        //Valid.pindah(evt,TCari,cmbBangsal);
    }//GEN-LAST:event_nmrekKeyPressed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnEdit,BtnKeluar);}
    }//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        rekening.dispose();
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnEditActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnKeluar);
        }
    }//GEN-LAST:event_BtnEditKeyPressed

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
        if(kdrek.getText().trim().equals("")||nmrek.getText().trim().equals("")){
            Valid.textKosong(kdrek,"Akun Rekening");
        }else if(ConsumerKey.getText().trim().equals("")){
            Valid.textKosong(ConsumerKey,"Consumer Key");
        }else if(ConsumerSecret.getText().trim().equals("")){
            Valid.textKosong(ConsumerSecret,"Consumer Secret");
        }else if(InstitutionCode.getText().trim().equals("")){
            Valid.textKosong(InstitutionCode,"Institution Code");
        }else if(BrivaNo.getText().trim().equals("")){
            Valid.textKosong(BrivaNo,"BIVA No");
        }else if(UrlApi.getText().trim().equals("")){
            Valid.textKosong(UrlApi,"URL API");
        }else{
            if(tbSpesialis.getSelectedRow()>-1){
                Valid.hapusTable(tabMode,kdrek,"set_akun_bankbri","kd_rek");
                if(Sequel.menyimpantf("set_akun_bankbri","?,aes_encrypt(?,'nur'),aes_encrypt(?,'windi'),aes_encrypt(?,'nur'),aes_encrypt(?,'windi'),aes_encrypt(?,'dewi')","Akun Rekening",6,new String[]{
                    kdrek.getText(),ConsumerKey.getText(),ConsumerSecret.getText(),InstitutionCode.getText(),BrivaNo.getText(),UrlApi.getText()
                })==true){
                    tampil();
                    emptTeks();
                }
            }
        }
    }//GEN-LAST:event_BtnEditActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnBatal, BtnEdit);
        }
    }//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        Valid.hapusTable(tabMode,kdrek,"set_akun_bankbri","kd_rek");
        tampil();
        emptTeks();
    }//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            emptTeks();
        }else{Valid.pindah(evt, BtnSimpan, BtnHapus);}
    }//GEN-LAST:event_BtnBatalKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        emptTeks();
    }//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,ConsumerSecret,BtnBatal);
        }
    }//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(kdrek.getText().trim().equals("")||nmrek.getText().trim().equals("")){
            Valid.textKosong(kdrek,"Akun Rekening");
        }else if(ConsumerKey.getText().trim().equals("")){
            Valid.textKosong(ConsumerKey,"Consumer Key");
        }else if(ConsumerSecret.getText().trim().equals("")){
            Valid.textKosong(ConsumerSecret,"Consumer Secret");
        }else if(InstitutionCode.getText().trim().equals("")){
            Valid.textKosong(InstitutionCode,"Institution Code");
        }else if(BrivaNo.getText().trim().equals("")){
            Valid.textKosong(BrivaNo,"BIVA No");
        }else if(UrlApi.getText().trim().equals("")){
            Valid.textKosong(UrlApi,"URL API");
        }else if(tabMode.getRowCount()==0){
            if(Sequel.menyimpantf("set_akun_bankbri","?,aes_encrypt(?,'nur'),aes_encrypt(?,'windi'),aes_encrypt(?,'nur'),aes_encrypt(?,'windi'),aes_encrypt(?,'dewi')","Akun Rekening",6,new String[]{
                kdrek.getText(),ConsumerKey.getText(),ConsumerSecret.getText(),InstitutionCode.getText(),BrivaNo.getText(),UrlApi.getText()
            })==true){
                tampil();
                emptTeks();
            }
        }else if(tabMode.getRowCount()>0){
            JOptionPane.showMessageDialog(null,"Maaf, Hanya diijinkan satu akun pengaturan ...!!!!");
            ConsumerKey.requestFocus();
        }
        
    }//GEN-LAST:event_BtnSimpanActionPerformed

    private void tbSpesialisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbSpesialisKeyPressed
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }//GEN-LAST:event_tbSpesialisKeyPressed

    private void tbSpesialisMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbSpesialisMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbSpesialisMouseClicked

    private void ConsumerKeyKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ConsumerKeyKeyPressed
        Valid.pindah(evt,BtnSimpan,ConsumerSecret);
    }//GEN-LAST:event_ConsumerKeyKeyPressed

    private void ConsumerSecretKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ConsumerSecretKeyPressed
        Valid.pindah(evt,ConsumerKey,BtnSimpan);
    }//GEN-LAST:event_ConsumerSecretKeyPressed

    private void BtnPenjabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPenjabKeyPressed
        Valid.pindah(evt,BtnKeluar,ConsumerKey);
    }//GEN-LAST:event_BtnPenjabKeyPressed

    private void InstitutionCodeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_InstitutionCodeKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_InstitutionCodeKeyPressed

    private void BrivaNoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BrivaNoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BrivaNoKeyPressed

    private void UrlApiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_UrlApiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_UrlApiKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            BRIApiIntegrasi dialog = new BRIApiIntegrasi(new javax.swing.JFrame(), true);
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
    private widget.TextBox BrivaNo;
    private widget.Button BtnBatal;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPenjab;
    private widget.Button BtnSimpan;
    private widget.TextBox ConsumerKey;
    private widget.TextBox ConsumerSecret;
    private widget.TextBox InstitutionCode;
    private widget.ScrollPane Scroll;
    private widget.TextBox UrlApi;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel3;
    private widget.Label jLabel4;
    private widget.Label jLabel5;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.TextBox kdrek;
    private widget.TextBox nmrek;
    private widget.panelisi panelGlass7;
    private widget.panelisi panelGlass8;
    private widget.Table tbSpesialis;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try {
            ps=koneksi.prepareStatement(
                   "select set_akun_bankbri.kd_rek,rekening.nm_rek,aes_decrypt(consumer_key,'nur'),aes_decrypt(consumer_secret,'windi'),aes_decrypt(institution_code,'nur'),aes_decrypt(briva_no,'windi'),aes_decrypt(urlapi,'dewi') "+
                   "from set_akun_bankbri inner join rekening on set_akun_bankbri.kd_rek=rekening.kd_rek"); 
            try{
                rs=ps.executeQuery();
                while(rs.next()){                
                    tabMode.addRow(new Object[]{
                        rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7)
                    });
                }
            }catch(Exception e){
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
            System.out.println("Notifikasi : "+e);
        }            
    }

    public void emptTeks() {
        kdrek.setText("");
        nmrek.setText("");
        ConsumerKey.setText("");
        ConsumerSecret.setText("");
        InstitutionCode.setText("");
        BrivaNo.setText("");
        UrlApi.setText("");
        BtnPenjab.requestFocus();
    }

    private void getData() {
        int row=tbSpesialis.getSelectedRow();
        if(row!= -1){
            kdrek.setText(tabMode.getValueAt(row,0).toString());
            nmrek.setText(tabMode.getValueAt(row,1).toString());
            ConsumerKey.setText(tabMode.getValueAt(row,2).toString());
            ConsumerSecret.setText(tabMode.getValueAt(row,3).toString());
            InstitutionCode.setText(tabMode.getValueAt(row,4).toString());
            BrivaNo.setText(tabMode.getValueAt(row,5).toString());
            UrlApi.setText(tabMode.getValueAt(row,6).toString());
        }
    }
    
    
    
    


}

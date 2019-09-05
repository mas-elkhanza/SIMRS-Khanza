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
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import keuangan.DlgKamar;

/**
 *
 * @author dosen
 */
public class InhealthReferensiJenpelRuang extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;

    /** Creates new form DlgSpesialis
     * @param parent
     * @param modal */
    public InhealthReferensiJenpelRuang(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        this.setLocation(10,10);
        setSize(459,539);

        Object[] row={"No.Bed/Kamar","Kode Bangsal","Nama Bangsal/Kamar","Kode Jenis","Jenis Pelayanan Ruang Rawat","Tarif"};
        tabMode=new DefaultTableModel(null,row){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
             Class[] types = new Class[] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                java.lang.Object.class, java.lang.Object.class, java.lang.Double.class
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

        for (int i = 0; i < 6; i++) {
            TableColumn column = tbSpesialis.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(90);
            }else if(i==1){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==2){
                column.setPreferredWidth(160);
            }else if(i==3){
                column.setPreferredWidth(105);
            }else if(i==4){
                column.setPreferredWidth(160);
            }else if(i==5){
                column.setPreferredWidth(80);
            }
        }

        tbSpesialis.setDefaultRenderer(Object.class, new WarnaTable());

        kdkamar.setDocument(new batasInput((byte)15).getKata(kdkamar));
        KdJenisPelayanan.setDocument(new batasInput((byte)20).getKata(KdJenisPelayanan));
        NmJenisPelayanan.setDocument(new batasInput((byte)100).getKata(NmJenisPelayanan));
        Tarif.setDocument(new batasInput((byte)15).getKata(Tarif));
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
        kamar.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(kamar.getTable().getSelectedRow()!= -1){                   
                    kdkamar.setText(kamar.getTable().getValueAt(kamar.getTable().getSelectedRow(),1).toString());
                    TKdBngsal.setText(kamar.getTable().getValueAt(kamar.getTable().getSelectedRow(),2).toString());
                    TBangsal.setText(kamar.getTable().getValueAt(kamar.getTable().getSelectedRow(),3).toString());
                }  
                kdkamar.requestFocus();
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
        
        kamar.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    kamar.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
    }
    
    private DlgKamar kamar=new DlgKamar(null,false);

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
        jPanel3 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        BtnKeluar = new widget.Button();
        panelGlass9 = new widget.panelisi();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();
        panelGlass7 = new widget.panelisi();
        jLabel4 = new widget.Label();
        Tarif = new widget.TextBox();
        jLabel12 = new widget.Label();
        kdkamar = new widget.TextBox();
        TKdBngsal = new widget.TextBox();
        TBangsal = new widget.TextBox();
        BtnKamar = new widget.Button();
        jLabel3 = new widget.Label();
        KdJenisPelayanan = new widget.TextBox();
        NmJenisPelayanan = new widget.TextBox();
        jLabel5 = new widget.Label();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Referensi Ruang Rawat Inhealth ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50,50,50))); // NOI18N
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
        TCari.setPreferredSize(new java.awt.Dimension(250, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass9.add(TCari);

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
            public void keyReleased(java.awt.event.KeyEvent evt) {
                BtnCariKeyReleased(evt);
            }
        });
        panelGlass9.add(BtnCari);

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('2');
        BtnAll.setToolTipText("Alt+2");
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

        panelGlass7.setName("panelGlass7"); // NOI18N
        panelGlass7.setPreferredSize(new java.awt.Dimension(74, 103));
        panelGlass7.setLayout(null);

        jLabel4.setText("Tarif : Rp");
        jLabel4.setName("jLabel4"); // NOI18N
        panelGlass7.add(jLabel4);
        jLabel4.setBounds(295, 40, 90, 23);

        Tarif.setText("0");
        Tarif.setName("Tarif"); // NOI18N
        Tarif.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TarifKeyPressed(evt);
            }
        });
        panelGlass7.add(Tarif);
        Tarif.setBounds(390, 40, 130, 23);

        jLabel12.setText("Kamar :");
        jLabel12.setName("jLabel12"); // NOI18N
        panelGlass7.add(jLabel12);
        jLabel12.setBounds(1, 10, 75, 23);

        kdkamar.setHighlighter(null);
        kdkamar.setName("kdkamar"); // NOI18N
        kdkamar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdkamarKeyPressed(evt);
            }
        });
        panelGlass7.add(kdkamar);
        kdkamar.setBounds(79, 10, 91, 23);

        TKdBngsal.setEditable(false);
        TKdBngsal.setName("TKdBngsal"); // NOI18N
        panelGlass7.add(TKdBngsal);
        TKdBngsal.setBounds(172, 10, 76, 23);

        TBangsal.setEditable(false);
        TBangsal.setHighlighter(null);
        TBangsal.setName("TBangsal"); // NOI18N
        TBangsal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TBangsalKeyPressed(evt);
            }
        });
        panelGlass7.add(TBangsal);
        TBangsal.setBounds(250, 10, 238, 23);

        BtnKamar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnKamar.setMnemonic('2');
        BtnKamar.setToolTipText("Alt+2");
        BtnKamar.setName("BtnKamar"); // NOI18N
        BtnKamar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKamarActionPerformed(evt);
            }
        });
        panelGlass7.add(BtnKamar);
        BtnKamar.setBounds(492, 10, 28, 23);

        jLabel3.setText("Ruang :");
        jLabel3.setName("jLabel3"); // NOI18N
        panelGlass7.add(jLabel3);
        jLabel3.setBounds(1, 70, 75, 23);

        KdJenisPelayanan.setHighlighter(null);
        KdJenisPelayanan.setName("KdJenisPelayanan"); // NOI18N
        KdJenisPelayanan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdJenisPelayananKeyPressed(evt);
            }
        });
        panelGlass7.add(KdJenisPelayanan);
        KdJenisPelayanan.setBounds(79, 40, 150, 23);

        NmJenisPelayanan.setHighlighter(null);
        NmJenisPelayanan.setName("NmJenisPelayanan"); // NOI18N
        NmJenisPelayanan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NmJenisPelayananActionPerformed(evt);
            }
        });
        NmJenisPelayanan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NmJenisPelayananKeyPressed(evt);
            }
        });
        panelGlass7.add(NmJenisPelayanan);
        NmJenisPelayanan.setBounds(79, 70, 441, 23);

        jLabel5.setText("Kode Jenis :");
        jLabel5.setName("jLabel5"); // NOI18N
        panelGlass7.add(jLabel5);
        jLabel5.setBounds(1, 40, 75, 23);

        internalFrame1.add(panelGlass7, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TarifKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TarifKeyPressed
        Valid.pindah(evt,KdJenisPelayanan,NmJenisPelayanan);
}//GEN-LAST:event_TarifKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(kdkamar.getText().trim().equals("")||TBangsal.getText().trim().equals("")){
            Valid.textKosong(kdkamar,"Kamar");
        }else if(Tarif.getText().trim().equals("")){
            Valid.textKosong(Tarif,"Tarif Kamar");
        }else if(KdJenisPelayanan.getText().trim().equals("")){
            Valid.textKosong(KdJenisPelayanan,"Kode Jenis Pelayanan");
        }else if(NmJenisPelayanan.getText().trim().equals("")){
            Valid.textKosong(NmJenisPelayanan,"Nama Jenis Pelayanan");
        }else{
            Sequel.menyimpan("inhealth_jenpel_ruang_rawat","?,?,?,?","Kode kamar",4,new String[]{
                kdkamar.getText(),KdJenisPelayanan.getText(),NmJenisPelayanan.getText(),Tarif.getText()
            });
            tampil();
            emptTeks();
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,NmJenisPelayanan,BtnBatal);
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
        Valid.hapusTable(tabMode,kdkamar,"inhealth_jenpel_ruang_rawat","kd_kamar");
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
        if(kdkamar.getText().trim().equals("")||TBangsal.getText().trim().equals("")){
            Valid.textKosong(kdkamar,"Kamar");
        }else if(Tarif.getText().trim().equals("")){
            Valid.textKosong(Tarif,"Tarif Kamar");
        }else if(KdJenisPelayanan.getText().trim().equals("")||NmJenisPelayanan.getText().trim().equals("")){
            Valid.textKosong(KdJenisPelayanan,"Cara Bayar");
        }else{
            if(tbSpesialis.getSelectedRow()>-1){
                Sequel.mengedit("inhealth_jenpel_ruang_rawat","kd_kamar=?","kd_kamar=?,kode_jenpel_ruang_rawat=?,nama_jenpel_ruang_rawat=?,tarif=?",5,new String[]{
                    kdkamar.getText(),KdJenisPelayanan.getText(),NmJenisPelayanan.getText(),Tarif.getText(),
                    tabMode.getValueAt(tbSpesialis.getSelectedRow(),0).toString()
                });
                tampil();
                emptTeks();
            }
        }
}//GEN-LAST:event_BtnEditActionPerformed

    private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnEditActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnKeluar);
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

    private void BtnCariKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyReleased
        // TODO add your handling code here:
}//GEN-LAST:event_BtnCariKeyReleased

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        tampil();
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnCari, kdkamar);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void tbSpesialisMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbSpesialisMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbSpesialisMouseClicked

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

private void kdkamarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdkamarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select kd_bangsal from kamar where kd_kamar=? ",TKdBngsal,kdkamar.getText());
            Sequel.cariIsi("select nm_bangsal from bangsal where kd_bangsal=? ",TBangsal,TKdBngsal.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnKamarActionPerformed(null);
        }else{
            Valid.pindah(evt,TCari,KdJenisPelayanan);
        }
}//GEN-LAST:event_kdkamarKeyPressed

private void TBangsalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TBangsalKeyPressed
        // TODO add your handling code here:
}//GEN-LAST:event_TBangsalKeyPressed

private void BtnKamarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKamarActionPerformed
        kamar.load();
        kamar.isCek();
        kamar.emptTeks();
        kamar.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        kamar.setLocationRelativeTo(internalFrame1);
        kamar.setAlwaysOnTop(false);
        kamar.setVisible(true);
}//GEN-LAST:event_BtnKamarActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void KdJenisPelayananKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdJenisPelayananKeyPressed
        Valid.pindah(evt,kdkamar,Tarif);        
    }//GEN-LAST:event_KdJenisPelayananKeyPressed

    private void NmJenisPelayananKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NmJenisPelayananKeyPressed
        Valid.pindah(evt,NmJenisPelayanan,BtnSimpan);
    }//GEN-LAST:event_NmJenisPelayananKeyPressed

    private void NmJenisPelayananActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NmJenisPelayananActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NmJenisPelayananActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            InhealthReferensiJenpelRuang dialog = new InhealthReferensiJenpelRuang(new javax.swing.JFrame(), true);
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
    private widget.Button BtnKamar;
    private widget.Button BtnKeluar;
    private widget.Button BtnSimpan;
    private widget.TextBox KdJenisPelayanan;
    private widget.Label LCount;
    private widget.TextBox NmJenisPelayanan;
    private widget.ScrollPane Scroll;
    private widget.TextBox TBangsal;
    private widget.TextBox TCari;
    private widget.TextBox TKdBngsal;
    private widget.TextBox Tarif;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel12;
    private widget.Label jLabel3;
    private widget.Label jLabel4;
    private widget.Label jLabel5;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private javax.swing.JPanel jPanel3;
    private widget.TextBox kdkamar;
    private widget.panelisi panelGlass7;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.Table tbSpesialis;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            ps=koneksi.prepareStatement(
                       "select inhealth_jenpel_ruang_rawat.kd_kamar,bangsal.kd_bangsal,bangsal.nm_bangsal,"+
                       "inhealth_jenpel_ruang_rawat.kode_jenpel_ruang_rawat,inhealth_jenpel_ruang_rawat.nama_jenpel_ruang_rawat,inhealth_jenpel_ruang_rawat.tarif "+
                       "from inhealth_jenpel_ruang_rawat inner join kamar inner join bangsal "+
                       "on inhealth_jenpel_ruang_rawat.kd_kamar=kamar.kd_kamar and kamar.kd_bangsal=bangsal.kd_bangsal "+
                       "where inhealth_jenpel_ruang_rawat.kd_kamar like ? or bangsal.nm_bangsal like ? "+
                       "or inhealth_jenpel_ruang_rawat.kode_jenpel_ruang_rawat like ? or inhealth_jenpel_ruang_rawat.nama_jenpel_ruang_rawat like ? order by inhealth_jenpel_ruang_rawat.kd_kamar"); 
            try {                
                ps.setString(1,"%"+TCari.getText().trim()+"%");
                ps.setString(2,"%"+TCari.getText().trim()+"%");
                ps.setString(3,"%"+TCari.getText().trim()+"%");
                ps.setString(4,"%"+TCari.getText().trim()+"%");
                rs=ps.executeQuery();
                while(rs.next()){                
                    tabMode.addRow(new Object[]{
                        rs.getString(1),rs.getString(2),rs.getString(3),
                        rs.getString(4),rs.getString(5),rs.getDouble(6)
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
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        LCount.setText(""+tabMode.getRowCount());
    }

    public void emptTeks() {
        kdkamar.setText("");
        TKdBngsal.setText("");
        TBangsal.setText("");
        Tarif.setText("0");
        TCari.setText("");
        kdkamar.requestFocus();
    }

    private void getData() {
        int row=tbSpesialis.getSelectedRow();
        if(row!= -1){
            kdkamar.setText(tabMode.getValueAt(row,0).toString());
            TKdBngsal.setText(tabMode.getValueAt(row,1).toString());
            TBangsal.setText(tabMode.getValueAt(row,2).toString());
            KdJenisPelayanan.setText(tabMode.getValueAt(row,3).toString());
            NmJenisPelayanan.setText(tabMode.getValueAt(row,4).toString());
            Tarif.setText(tabMode.getValueAt(row,5).toString());
        }
    }
    
    
    
    


}

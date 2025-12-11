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

import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.sql.PreparedStatement;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;


/**
 *
 * @author igos
 */
public class DlgCariPasien extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private int z=0;
    private PreparedStatement ps;
    private ResultSet rs;
    private String asalform="";
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private volatile boolean ceksukses = false;

    /** Creates new form DlgPas
     * @param parent
     * @param modal*/
    public DlgCariPasien(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        tabMode=new DefaultTableModel(null,new Object[]{
              "No.R.M","Nama Pasien","No.SIM/KTP","J.K.","Tmp.Lahir","Tgl.Lahir","Nama Ibu","Alamat",
              "G.D.","Pekerjaan","Stts.Nikah","Agama","Tgl.Daftar","No.Telp/HP","Umur","Pendidikan",
              "Keluarga","Nama Keluarga","Asuransi/Askes","No.Peserta","Daftar","Pekerjaan P.J.","Alamat P.J.",
              "ID Suku","Suku/Bangsa","ID Bahasa","Bahasa","ID Peru","Instansi/Perusahaan","NIP/NRP","Email",
              "Id Cacat","Cacat Fisik","kd_pj","alamat","nm_kel","nm_kec","nm_kab","nm_prop","alamatpj","kelurahanpj",
              "kecamatanpj","kabupatenpj","propinsipj"
            }){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                 java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                 java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                 java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                 java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                 java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                 java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                 java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                 java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                 java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                 java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                 java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                 java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbPasien.setModel(tabMode);

        //tbPetugas.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbPetugas.getBackground()));
        tbPasien.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbPasien.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (z = 0; z < 44; z++) {
            TableColumn column = tbPasien.getColumnModel().getColumn(z);
            if(z==0){
                column.setPreferredWidth(85);
            }else if(z==1){
                column.setPreferredWidth(190);
            }else if(z==2){
                column.setPreferredWidth(100);
            }else if(z==3){
                column.setPreferredWidth(25);
            }else if(z==4){
                column.setPreferredWidth(100);
            }else if(z==5){
                column.setPreferredWidth(70);
            }else if(z==6){
                column.setPreferredWidth(150);
            }else if(z==7){
                column.setPreferredWidth(190);
            }else if(z==8){
                column.setPreferredWidth(30);
            }else if(z==9){
                column.setPreferredWidth(100);
            }else if(z==10){
                column.setPreferredWidth(75);
            }else if(z==11){
                column.setPreferredWidth(75);
            }else if(z==12){
                column.setPreferredWidth(75);
            }else if(z==13){
                column.setPreferredWidth(80);
            }else if(z==14){
                column.setPreferredWidth(90);
            }else if(z==15){
                column.setPreferredWidth(80);
            }else if(z==16){
                column.setPreferredWidth(80);
            }else if(z==17){
                column.setPreferredWidth(150);
            }else if(z==18){
                column.setPreferredWidth(120);
            }else if(z==19){
                column.setPreferredWidth(100);
            }else if(z==20){
                column.setPreferredWidth(60);
            }else if(z==21){
                column.setPreferredWidth(85);
            }else if(z==22){
                column.setPreferredWidth(160);
            }else if(z==24){
                column.setPreferredWidth(100);
            }else if(z==26){
                column.setPreferredWidth(100);
            }else if(z==28){
                column.setPreferredWidth(140);
            }else if(z==29){
                column.setPreferredWidth(90);
            }else if(z==30){
                column.setPreferredWidth(120);
            }else if(z==32){
                column.setPreferredWidth(120);
            }else if((z==23)||(z==25)||(z==27)||(z==31)||(z==33)||(z==34)||(z==35)||(z==36)||(z==37)||(z==38)||(z==39)||(z==40)||(z==41)||(z==42)||(z==43)){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }
        }
        tbPasien.setDefaultRenderer(Object.class, new WarnaTable());

        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        if(koneksiDB.CARICEPAT().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        runBackground(() -> tampil());
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        runBackground(() -> tampil());
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        runBackground(() -> tampil());
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
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Kd2 = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbPasien = new widget.Table();
        panelGlass9 = new widget.panelisi();
        jLabel11 = new widget.Label();
        Carialamat = new widget.TextBox();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel7 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        jLabel6 = new widget.Label();
        cmbHlm = new widget.ComboBox();
        jLabel10 = new widget.Label();
        LCount = new widget.Label();
        BtnKeluar = new widget.Button();

        Kd2.setName("Kd2"); // NOI18N
        Kd2.setPreferredSize(new java.awt.Dimension(207, 23));

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Pasien ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbPasien.setAutoCreateRowSorter(true);
        tbPasien.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbPasien.setName("tbPasien"); // NOI18N
        tbPasien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPasienMouseClicked(evt);
            }
        });
        tbPasien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbPasienKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbPasienKeyReleased(evt);
            }
        });
        Scroll.setViewportView(tbPasien);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 10));

        jLabel11.setText("Alamat :");
        jLabel11.setName("jLabel11"); // NOI18N
        jLabel11.setPreferredSize(new java.awt.Dimension(48, 23));
        panelGlass9.add(jLabel11);

        Carialamat.setName("Carialamat"); // NOI18N
        Carialamat.setPreferredSize(new java.awt.Dimension(160, 23));
        Carialamat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CarialamatKeyPressed(evt);
            }
        });
        panelGlass9.add(Carialamat);

        jSeparator5.setBackground(new java.awt.Color(220, 225, 215));
        jSeparator5.setForeground(new java.awt.Color(220, 225, 215));
        jSeparator5.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator5.setName("jSeparator5"); // NOI18N
        jSeparator5.setOpaque(true);
        jSeparator5.setPreferredSize(new java.awt.Dimension(1, 23));
        panelGlass9.add(jSeparator5);

        jLabel7.setText("Key Word :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(60, 23));
        panelGlass9.add(jLabel7);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(150, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass9.add(TCari);

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
        panelGlass9.add(BtnCari);

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
        panelGlass9.add(BtnAll);

        jLabel6.setText("Limit Data :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(jLabel6);

        cmbHlm.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "100", "200", "300", "400", "500", "1000", "Semua" }));
        cmbHlm.setName("cmbHlm"); // NOI18N
        cmbHlm.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(cmbHlm);

        jLabel10.setText("Record :");
        jLabel10.setName("jLabel10"); // NOI18N
        jLabel10.setPreferredSize(new java.awt.Dimension(50, 23));
        panelGlass9.add(jLabel10);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(50, 23));
        panelGlass9.add(LCount);

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar.setMnemonic('K');
        BtnKeluar.setToolTipText("Alt+K");
        BtnKeluar.setName("BtnKeluar"); // NOI18N
        BtnKeluar.setPreferredSize(new java.awt.Dimension(28, 23));
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
        panelGlass9.add(BtnKeluar);

        internalFrame1.add(panelGlass9, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void tbPasienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPasienMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbPasienMouseClicked

private void tbPasienKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbPasienKeyPressed
       if(tabMode.getRowCount()!=0){
            if(evt.getKeyCode()==KeyEvent.VK_SPACE){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
                akses.setform(asalform);
            }else if(evt.getKeyCode()==KeyEvent.VK_SHIFT){
                TCari.setText("");
                TCari.requestFocus();
            }else if(evt.getKeyCode()==KeyEvent.VK_A){                
                for(z=0;z<tbPasien.getRowCount();z++){ 
                    tbPasien.setValueAt(true,z,0);
                }
            }
        }
}//GEN-LAST:event_tbPasienKeyPressed

private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        Carialamat.setText("");
        runBackground(() -> tampil());
}//GEN-LAST:event_BtnAllActionPerformed

private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnCari, BtnKeluar);
        }
}//GEN-LAST:event_BtnAllKeyPressed

private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
    dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnAll,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
       if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            tbPasien.requestFocus();
        }
}//GEN-LAST:event_TCariKeyPressed

private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
    runBackground(() -> tampil());
}//GEN-LAST:event_BtnCariActionPerformed

private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari, BtnAll);
        }
}//GEN-LAST:event_BtnCariKeyPressed

private void CarialamatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CarialamatKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
            TCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){          
            BtnCariActionPerformed(null);
            BtnKeluar.requestFocus();
        }
}//GEN-LAST:event_CarialamatKeyPressed

    private void tbPasienKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbPasienKeyReleased
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }//GEN-LAST:event_tbPasienKeyReleased

    /**
     * @data args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DlgCariPasien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DlgCariPasien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DlgCariPasien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DlgCariPasien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(() -> {
            DlgCariPasien dialog = new DlgCariPasien(new javax.swing.JFrame(), true);
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
    private widget.TextBox Carialamat;
    private widget.TextBox Kd2;
    private widget.Label LCount;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.ComboBox cmbHlm;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private javax.swing.JSeparator jSeparator5;
    private widget.panelisi panelGlass9;
    private widget.Table tbPasien;
    // End of variables declaration//GEN-END:variables
    
    private void tampil() {    
        Valid.tabelKosong(tabMode);
        try{
            if(cmbHlm.getSelectedItem().toString().equals("Semua")){
                 if(Carialamat.getText().trim().equals("")&&TCari.getText().trim().equals("")){
                      ps=koneksi.prepareStatement("select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "+
                           "pasien.tmp_lahir, pasien.tgl_lahir,pasien.nm_ibu, pasien.alamat,kelurahan.nm_kel,kecamatan.nm_kec,kabupaten.nm_kab,propinsi.nm_prop,"+
                           "pasien.gol_darah, pasien.pekerjaan,pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"+
                           "pasien.pnd, pasien.keluarga, pasien.namakeluarga,penjab.png_jawab,pasien.no_peserta,pasien.pekerjaanpj,"+
                           "pasien.alamatpj,pasien.kelurahanpj,pasien.kecamatanpj,pasien.kabupatenpj,pasien.propinsipj,"+
                           "perusahaan_pasien.kode_perusahaan,perusahaan_pasien.nama_perusahaan,pasien.bahasa_pasien,"+
                           "bahasa_pasien.nama_bahasa,pasien.suku_bangsa,suku_bangsa.nama_suku_bangsa,pasien.nip,pasien.email,cacat_fisik.nama_cacat,pasien.cacat_fisik,pasien.kd_pj from pasien "+
                           "inner join kelurahan on pasien.kd_kel=kelurahan.kd_kel inner join kecamatan on pasien.kd_kec=kecamatan.kd_kec "+
                           "inner join kabupaten on pasien.kd_kab=kabupaten.kd_kab inner join perusahaan_pasien on perusahaan_pasien.kode_perusahaan=pasien.perusahaan_pasien "+
                           "inner join cacat_fisik on pasien.cacat_fisik=cacat_fisik.id inner join propinsi on pasien.kd_prop=propinsi.kd_prop "+
                           "inner join bahasa_pasien on bahasa_pasien.id=pasien.bahasa_pasien inner join suku_bangsa on suku_bangsa.id=pasien.suku_bangsa "+
                           "inner join penjab on pasien.kd_pj=penjab.kd_pj order by pasien.no_rkm_medis desc");  
                 }else{
                      ps=koneksi.prepareStatement("select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "+
                           "pasien.tmp_lahir, pasien.tgl_lahir,pasien.nm_ibu, pasien.alamat,kelurahan.nm_kel,kecamatan.nm_kec,kabupaten.nm_kab,propinsi.nm_prop,"+
                           "pasien.gol_darah, pasien.pekerjaan,pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"+
                           "pasien.pnd, pasien.keluarga, pasien.namakeluarga,penjab.png_jawab,pasien.no_peserta,pasien.pekerjaanpj,"+
                           "pasien.alamatpj,pasien.kelurahanpj,pasien.kecamatanpj,pasien.kabupatenpj,pasien.propinsipj,"+
                           "perusahaan_pasien.kode_perusahaan,perusahaan_pasien.nama_perusahaan,pasien.bahasa_pasien,"+
                           "bahasa_pasien.nama_bahasa,pasien.suku_bangsa,suku_bangsa.nama_suku_bangsa,pasien.nip,pasien.email,cacat_fisik.nama_cacat,pasien.cacat_fisik,pasien.kd_pj from pasien "+
                           "inner join kelurahan on pasien.kd_kel=kelurahan.kd_kel inner join kecamatan on pasien.kd_kec=kecamatan.kd_kec "+
                           "inner join kabupaten on pasien.kd_kab=kabupaten.kd_kab inner join perusahaan_pasien on perusahaan_pasien.kode_perusahaan=pasien.perusahaan_pasien "+
                           "inner join cacat_fisik on pasien.cacat_fisik=cacat_fisik.id inner join propinsi on pasien.kd_prop=propinsi.kd_prop "+
                           "inner join bahasa_pasien on bahasa_pasien.id=pasien.bahasa_pasien inner join suku_bangsa on suku_bangsa.id=pasien.suku_bangsa "+
                           "inner join penjab on pasien.kd_pj=penjab.kd_pj "+
                           "where concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab,', ',propinsi.nm_prop) like ? "+
                           "and (pasien.no_rkm_medis like ? or  pasien.nm_pasien like ? or pasien.no_ktp like ? or pasien.no_peserta like ? or pasien.tmp_lahir like ? "+
                           "or pasien.tgl_lahir like ? or penjab.png_jawab like ? or pasien.alamat like ? or pasien.gol_darah like ? or pasien.pekerjaan like ? "+
                           "or pasien.stts_nikah like ? or pasien.nip like ? or cacat_fisik.nama_cacat like ? or pasien.namakeluarga like ? or perusahaan_pasien.nama_perusahaan like ? "+
                           "or bahasa_pasien.nama_bahasa like ? or suku_bangsa.nama_suku_bangsa like ? or pasien.agama like ? or pasien.nm_ibu like ? or pasien.tgl_daftar like ? "+
                           "or pasien.no_tlp like ?) order by pasien.no_rkm_medis desc");  
                 }    
            }else{
                if(Carialamat.getText().trim().equals("")&&TCari.getText().trim().equals("")){
                    ps=koneksi.prepareStatement("select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "+
                           "pasien.tmp_lahir, pasien.tgl_lahir,pasien.nm_ibu, pasien.alamat,kelurahan.nm_kel,kecamatan.nm_kec,kabupaten.nm_kab,propinsi.nm_prop,"+
                           "pasien.gol_darah, pasien.pekerjaan,pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"+
                           "pasien.pnd, pasien.keluarga, pasien.namakeluarga,penjab.png_jawab,pasien.no_peserta,pasien.pekerjaanpj,"+
                           "pasien.alamatpj,pasien.kelurahanpj,pasien.kecamatanpj,pasien.kabupatenpj,pasien.propinsipj,"+
                           "perusahaan_pasien.kode_perusahaan,perusahaan_pasien.nama_perusahaan,pasien.bahasa_pasien,"+
                           "bahasa_pasien.nama_bahasa,pasien.suku_bangsa,suku_bangsa.nama_suku_bangsa,pasien.nip,pasien.email,cacat_fisik.nama_cacat,pasien.cacat_fisik,pasien.kd_pj from pasien "+
                           "inner join kelurahan on pasien.kd_kel=kelurahan.kd_kel inner join kecamatan on pasien.kd_kec=kecamatan.kd_kec "+
                           "inner join kabupaten on pasien.kd_kab=kabupaten.kd_kab inner join perusahaan_pasien on perusahaan_pasien.kode_perusahaan=pasien.perusahaan_pasien "+
                           "inner join cacat_fisik on pasien.cacat_fisik=cacat_fisik.id inner join propinsi on pasien.kd_prop=propinsi.kd_prop "+
                           "inner join bahasa_pasien on bahasa_pasien.id=pasien.bahasa_pasien inner join suku_bangsa on suku_bangsa.id=pasien.suku_bangsa "+
                           "inner join penjab on pasien.kd_pj=penjab.kd_pj order by pasien.no_rkm_medis desc LIMIT ? ");    
                }else{
                    ps=koneksi.prepareStatement("select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "+
                           "pasien.tmp_lahir, pasien.tgl_lahir,pasien.nm_ibu, pasien.alamat,kelurahan.nm_kel,kecamatan.nm_kec,kabupaten.nm_kab,propinsi.nm_prop,"+
                           "pasien.gol_darah, pasien.pekerjaan,pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"+
                           "pasien.pnd, pasien.keluarga, pasien.namakeluarga,penjab.png_jawab,pasien.no_peserta,pasien.pekerjaanpj,"+
                           "pasien.alamatpj,pasien.kelurahanpj,pasien.kecamatanpj,pasien.kabupatenpj,pasien.propinsipj,"+
                           "perusahaan_pasien.kode_perusahaan,perusahaan_pasien.nama_perusahaan,pasien.bahasa_pasien,"+
                           "bahasa_pasien.nama_bahasa,pasien.suku_bangsa,suku_bangsa.nama_suku_bangsa,pasien.nip,pasien.email,cacat_fisik.nama_cacat,pasien.cacat_fisik,pasien.kd_pj from pasien "+
                           "inner join kelurahan on pasien.kd_kel=kelurahan.kd_kel inner join kecamatan on pasien.kd_kec=kecamatan.kd_kec "+
                           "inner join kabupaten on pasien.kd_kab=kabupaten.kd_kab inner join perusahaan_pasien on perusahaan_pasien.kode_perusahaan=pasien.perusahaan_pasien "+
                           "inner join cacat_fisik on pasien.cacat_fisik=cacat_fisik.id inner join propinsi on pasien.kd_prop=propinsi.kd_prop "+
                           "inner join bahasa_pasien on bahasa_pasien.id=pasien.bahasa_pasien inner join suku_bangsa on suku_bangsa.id=pasien.suku_bangsa "+
                           "inner join penjab on pasien.kd_pj=penjab.kd_pj "+
                           "where concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab,', ',propinsi.nm_prop) like ? "+
                           "and (pasien.no_rkm_medis like ? or  pasien.nm_pasien like ? or pasien.no_ktp like ? or pasien.no_peserta like ? or pasien.tmp_lahir like ? "+
                           "or pasien.tgl_lahir like ? or penjab.png_jawab like ? or pasien.alamat like ? or pasien.gol_darah like ? or pasien.pekerjaan like ? "+
                           "or pasien.stts_nikah like ? or pasien.nip like ? or cacat_fisik.nama_cacat like ? or pasien.namakeluarga like ? or perusahaan_pasien.nama_perusahaan like ? "+
                           "or bahasa_pasien.nama_bahasa like ? or suku_bangsa.nama_suku_bangsa like ? or pasien.agama like ? or pasien.nm_ibu like ? or pasien.tgl_daftar like ? "+
                           "or pasien.no_tlp like ?)  order by pasien.no_rkm_medis desc LIMIT ? ");    
                }       
            }          
            try{
                if(cmbHlm.getSelectedItem().toString().equals("Semua")){
                    if(Carialamat.getText().trim().equals("")&&TCari.getText().trim().equals("")){}else{
                        ps.setString(1,"%"+Carialamat.getText().trim()+"%");
                        ps.setString(2,"%"+TCari.getText().trim()+"%");
                        ps.setString(3, "%"+TCari.getText().trim()+"%");
                        ps.setString(4, "%"+TCari.getText().trim()+"%");
                        ps.setString(5, "%"+TCari.getText().trim()+"%");
                        ps.setString(6, "%"+TCari.getText().trim()+"%");
                        ps.setString(7, "%"+TCari.getText().trim()+"%");
                        ps.setString(8, "%"+TCari.getText().trim()+"%");
                        ps.setString(9, "%"+TCari.getText().trim()+"%");
                        ps.setString(10, "%"+TCari.getText().trim()+"%");
                        ps.setString(11, "%"+TCari.getText().trim()+"%");
                        ps.setString(12, "%"+TCari.getText().trim()+"%");
                        ps.setString(13, "%"+TCari.getText().trim()+"%");
                        ps.setString(14, "%"+TCari.getText().trim()+"%");
                        ps.setString(15, "%"+TCari.getText().trim()+"%");
                        ps.setString(16, "%"+TCari.getText().trim()+"%");
                        ps.setString(17, "%"+TCari.getText().trim()+"%");
                        ps.setString(18, "%"+TCari.getText().trim()+"%");
                        ps.setString(19, "%"+TCari.getText().trim()+"%");
                        ps.setString(20, "%"+TCari.getText().trim()+"%");
                        ps.setString(21, "%"+TCari.getText().trim()+"%");
                        ps.setString(22, "%"+TCari.getText().trim()+"%");
                    }   
                }else{
                    if(Carialamat.getText().trim().equals("")&&TCari.getText().trim().equals("")){
                        ps.setInt(1,Integer.parseInt(cmbHlm.getSelectedItem().toString()));
                    }else{
                        ps.setString(1,"%"+Carialamat.getText().trim()+"%");
                        ps.setString(2,"%"+TCari.getText().trim()+"%");
                        ps.setString(3, "%"+TCari.getText().trim()+"%");
                        ps.setString(4, "%"+TCari.getText().trim()+"%");
                        ps.setString(5, "%"+TCari.getText().trim()+"%");
                        ps.setString(6, "%"+TCari.getText().trim()+"%");
                        ps.setString(7, "%"+TCari.getText().trim()+"%");
                        ps.setString(8, "%"+TCari.getText().trim()+"%");
                        ps.setString(9, "%"+TCari.getText().trim()+"%");
                        ps.setString(10, "%"+TCari.getText().trim()+"%");
                        ps.setString(11, "%"+TCari.getText().trim()+"%");
                        ps.setString(12, "%"+TCari.getText().trim()+"%");
                        ps.setString(13, "%"+TCari.getText().trim()+"%");
                        ps.setString(14, "%"+TCari.getText().trim()+"%");
                        ps.setString(15, "%"+TCari.getText().trim()+"%");
                        ps.setString(16, "%"+TCari.getText().trim()+"%");
                        ps.setString(17, "%"+TCari.getText().trim()+"%");
                        ps.setString(18, "%"+TCari.getText().trim()+"%");
                        ps.setString(19, "%"+TCari.getText().trim()+"%");
                        ps.setString(20, "%"+TCari.getText().trim()+"%");
                        ps.setString(21, "%"+TCari.getText().trim()+"%");
                        ps.setString(22, "%"+TCari.getText().trim()+"%");
                        ps.setInt(23,Integer.parseInt(cmbHlm.getSelectedItem().toString()));
                    }
                }
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new Object[]{
                        rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("no_ktp"),rs.getString("jk"),rs.getString("tmp_lahir"),rs.getDate("tgl_lahir"),rs.getString("nm_ibu"),
                        rs.getString("alamat")+", "+rs.getString("nm_kel")+", "+rs.getString("nm_kec")+", "+rs.getString("nm_kab")+", "+rs.getString("nm_prop"),rs.getString("gol_darah"),rs.getString("pekerjaan"),
                        rs.getString("stts_nikah"),rs.getString("agama"),rs.getString("tgl_daftar"),rs.getString("no_tlp"),rs.getString("umur"),rs.getString("pnd"),rs.getString("keluarga"),
                        rs.getString("namakeluarga"),rs.getString("png_jawab"),rs.getString("no_peserta"),"Klik Kanan, Tampilkan Banyak Daftar",rs.getString("pekerjaanpj"),
                        rs.getString("alamatpj")+", "+rs.getString("kelurahanpj")+", "+rs.getString("kecamatanpj")+", "+rs.getString("kabupatenpj")+", "+rs.getString("propinsipj"),
                        rs.getString("suku_bangsa"),rs.getString("nama_suku_bangsa"),rs.getString("bahasa_pasien"),rs.getString("nama_bahasa"),
                        rs.getString("kode_perusahaan"),rs.getString("nama_perusahaan"),rs.getString("nip"),rs.getString("email"),rs.getString("cacat_fisik"),
                        rs.getString("nama_cacat"),rs.getString("kd_pj"),rs.getString("alamat"),rs.getString("nm_kel"),rs.getString("nm_kec"),
                        rs.getString("nm_kab"),rs.getString("nm_prop"),rs.getString("alamatpj"),rs.getString("kelurahanpj"),
                        rs.getString("kecamatanpj"),rs.getString("kabupatenpj"),rs.getString("propinsipj")
                    });
                }          
            }catch(Exception e){
                System.out.println("Notifikasi : "+e);
            }finally{
                if(rs != null){
                    rs.close();
                }
                
                if(ps != null){
                    ps.close();
                }
            }
        }catch(Exception e){
            System.out.println(e);
        }
            
        LCount.setText(""+tabMode.getRowCount());
    }
    
    public void emptTeks() {
        Kd2.setText("");
        TCari.requestFocus();
    }

    private void getData() {
        if(tbPasien.getSelectedRow()!= -1){                
            try {                
                Kd2.setText(tbPasien.getValueAt(tbPasien.getSelectedRow(),1).toString());
            } catch (Exception ex) {
            }   
        }
    }
    
    public JTable getTable(){
        return tbPasien;
    }
    
    public void isCek(){
        asalform=akses.getform();
    }
    
    private void runBackground(Runnable task) {
        if (ceksukses) return;
        ceksukses = true;

        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        executor.submit(() -> {
            try {
                task.run();
            } finally {
                ceksukses = false;
                SwingUtilities.invokeLater(() -> {
                    this.setCursor(Cursor.getDefaultCursor());
                });
            }
        });
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgBangsal.java
 *
 * Created on May 22, 2010, 9:58:42 PM
 */

package kepegawaian;
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
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author dosen
 */
public final class DlgKehadiran extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps,ps2,ps3,ps4,ps5,ps6,ps7,ps8,ps9;
    private ResultSet rs,rs2,rs3,rs4,rs5,rs6,rs7,rs8,rs9;
    private String hadir="0",siang="0",malam="0",tepatwaktu="0",toleransi="0",
            terlambat1="0",terlambat2="0",terlambat3="0",pagi="0",pilih="";
    
    /** Creates new form DlgBangsal
     * @param parent
     * @param modal */
    public DlgKehadiran(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        Object[] row={"NIP",
                      "Nama",
                      "Departemen",
                      "Kehadiran",
                      "Pagi",
                      "Siang",
                      "Malam",
                      "Tepat Waktu",
                      "Toleransi",
                      "Terlambat I",
                      "Terlambat II"};
        
        tabMode=new DefaultTableModel(null,row){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };

        tbBangsal.setModel(tabMode);
        tbBangsal.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbBangsal.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 11; i++) {
            TableColumn column = tbBangsal.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(100);
            }else if(i==1){
                column.setPreferredWidth(250);
            }else if(i==2){
                column.setPreferredWidth(100);
            }else{
                column.setPreferredWidth(100);
            }
        }
        tbBangsal.setDefaultRenderer(Object.class, new WarnaTable());
        TCari.setDocument(new batasInput((int)100).getKata(TCari));
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
        loadTahun();
        Valid.loadCombo(Departemen,"nama","departemen");
        Departemen.addItem("Semua");
        Departemen.setSelectedItem("Semua");
        
        try {
           ps=koneksi.prepareStatement(
                   "select pegawai.nik,pegawai.nama,departemen.nama,pegawai.id from pegawai inner join departemen on pegawai.departemen=departemen.dep_id where  "+
                   " pegawai.stts_aktif<>'KELUAR' and departemen.nama like ? and pegawai.nik like ? or  pegawai.stts_aktif<>'KELUAR' and departemen.nama like ? and pegawai.nama like ?  order by pegawai.nik ");    
           ps2=koneksi.prepareStatement(
                   "select count(rekap_presensi.id) from rekap_presensi where rekap_presensi.id=?  "+
                   "and rekap_presensi.jam_datang like ?");
           ps3=koneksi.prepareStatement(
                   "select count(rekap_presensi.id) from rekap_presensi where rekap_presensi.id=? and rekap_presensi.shift like '%Siang%' "+
                   "and rekap_presensi.jam_datang like ?");
           ps9=koneksi.prepareStatement(
                   "select count(rekap_presensi.id) from rekap_presensi where rekap_presensi.id=? and rekap_presensi.shift like '%Pagi%' "+
                   "and rekap_presensi.jam_datang like ?");
           ps4=koneksi.prepareStatement(
                   "select count(rekap_presensi.id) from rekap_presensi where rekap_presensi.id=? and rekap_presensi.shift like '%Malam%' "+
                   "and rekap_presensi.jam_datang like ?");
           ps5=koneksi.prepareStatement(
                   "select count(rekap_presensi.id) from rekap_presensi where rekap_presensi.id=? and rekap_presensi.status like '%Tepat Waktu%' "+
                   "and rekap_presensi.jam_datang like ?");
           ps6=koneksi.prepareStatement(
                   "select count(rekap_presensi.id) from rekap_presensi where rekap_presensi.id=? and rekap_presensi.status like '%Terlambat Toleransi%' "+
                   "and rekap_presensi.jam_datang like ?");
           ps7=koneksi.prepareStatement(
                   "select count(rekap_presensi.id) from rekap_presensi where rekap_presensi.id=? and rekap_presensi.status like '%Terlambat I%' "+
                   "and rekap_presensi.jam_datang like ?");
           ps8=koneksi.prepareStatement(
                   "select count(rekap_presensi.id) from rekap_presensi where rekap_presensi.id=? and rekap_presensi.status like '%Terlambat II%' "+
                   "and rekap_presensi.jam_datang like ?");
        } catch (SQLException e) {
            System.out.println(e);
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

        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbBangsal = new widget.Table();
        jPanel1 = new javax.swing.JPanel();
        panelGlass7 = new widget.panelisi();
        label11 = new widget.Label();
        ThnCari = new widget.ComboBox();
        BlnCari = new widget.ComboBox();
        label12 = new widget.Label();
        Departemen = new widget.ComboBox();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        panelGlass5 = new widget.panelisi();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();
        BtnPrint = new widget.Button();
        BtnAll = new widget.Button();
        BtnKeluar = new widget.Button();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setIconImage(null);
        setIconImages(null);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Rekap Kehadiran ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50,50,50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbBangsal.setAutoCreateRowSorter(true);
        tbBangsal.setName("tbBangsal"); // NOI18N
        Scroll.setViewportView(tbBangsal);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass7.setName("panelGlass7"); // NOI18N
        panelGlass7.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass7.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label11.setText("Tahun & Bulan :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass7.add(label11);

        ThnCari.setName("ThnCari"); // NOI18N
        ThnCari.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass7.add(ThnCari);

        BlnCari.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" }));
        BlnCari.setName("BlnCari"); // NOI18N
        BlnCari.setPreferredSize(new java.awt.Dimension(62, 23));
        panelGlass7.add(BlnCari);

        label12.setText("Departemen :");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(77, 23));
        panelGlass7.add(label12);

        Departemen.setName("Departemen"); // NOI18N
        Departemen.setPreferredSize(new java.awt.Dimension(130, 23));
        panelGlass7.add(Departemen);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(66, 23));
        jLabel6.setRequestFocusEnabled(false);
        panelGlass7.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(200, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass7.add(TCari);

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
        });
        panelGlass7.add(BtnCari);

        jPanel1.add(panelGlass7, java.awt.BorderLayout.PAGE_START);

        panelGlass5.setName("panelGlass5"); // NOI18N
        panelGlass5.setPreferredSize(new java.awt.Dimension(55, 55));
        panelGlass5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(57, 23));
        panelGlass5.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(68, 23));
        panelGlass5.add(LCount);

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

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('m');
        BtnAll.setText("Semua");
        BtnAll.setToolTipText("Alt+m");
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
        panelGlass5.add(BtnAll);

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

        jPanel1.add(panelGlass5, java.awt.BorderLayout.CENTER);

        internalFrame1.add(jPanel1, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            TCari.setText("");
            tampil();
        }else{
            Valid.pindah(evt, TCari, BtnAll);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(! TCari.getText().trim().equals("")){
            BtnCariActionPerformed(evt);
        }
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            TCari.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            
            Sequel.queryu("truncate table temporary");
            for(int r=0;r<tbBangsal.getRowCount();r++){  
                    Sequel.menyimpan("temporary","'0','"+
                                    tbBangsal.getValueAt(r,0).toString().replaceAll("'","`") +"','"+
                                    tbBangsal.getValueAt(r,1).toString().replaceAll("'","`")+"','"+
                                    tbBangsal.getValueAt(r,2).toString().replaceAll("'","`")+"','"+
                                    tbBangsal.getValueAt(r,3).toString().replaceAll("'","`")+"','"+
                                    tbBangsal.getValueAt(r,4).toString().replaceAll("'","`")+"','"+
                                    tbBangsal.getValueAt(r,5).toString().replaceAll("'","`")+"','"+
                                    tbBangsal.getValueAt(r,6).toString().replaceAll("'","`")+"','"+
                                    tbBangsal.getValueAt(r,7).toString().replaceAll("'","`")+"','"+
                                    tbBangsal.getValueAt(r,8).toString().replaceAll("'","`")+"','"+
                                    tbBangsal.getValueAt(r,9).toString().replaceAll("'","`")+"','"+
                                    tbBangsal.getValueAt(r,10).toString().replaceAll("'","`")+"','','','','','','','','','','','','','','','','','','','','','','','','','',''","Rekap Nota Pembayaran");
            }
            
            Map<String, Object> param = new HashMap<>();                 
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());   
            param.put("tahun","BULAN "+BlnCari.getSelectedItem()+" TAHUN "+ThnCari.getSelectedItem());   
            param.put("logo",Sequel.cariGambar("select logo from setting")); 
            Valid.MyReport("rptHadir.jasper","report","::[ Rekap Kehadiran Pegawai ]::",param);
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt,BtnPrint,BtnAll);
        }
    }//GEN-LAST:event_BtnPrintKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgKehadiran dialog = new DlgKehadiran(new javax.swing.JFrame(), true);
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
    private widget.ComboBox BlnCari;
    private widget.Button BtnAll;
    private widget.Button BtnCari;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.ComboBox Departemen;
    private widget.Label LCount;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.ComboBox ThnCari;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private javax.swing.JPanel jPanel1;
    private widget.Label label11;
    private widget.Label label12;
    private widget.panelisi panelGlass5;
    private widget.panelisi panelGlass7;
    private widget.Table tbBangsal;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            ps.setString(1,"%"+Departemen.getSelectedItem().toString().replaceAll("Semua","")+"%");
            ps.setString(2,"%"+TCari.getText().trim()+"%");
            ps.setString(3,"%"+Departemen.getSelectedItem().toString().replaceAll("Semua","")+"%");
            ps.setString(4,"%"+TCari.getText().trim()+"%");
            rs=ps.executeQuery();
            while(rs.next()){
                hadir="0";pagi="0";siang="0";malam="0";tepatwaktu="0";toleransi="0";terlambat1="0";terlambat2="0"; 
                
                ps2.setString(1,rs.getString(4));
                ps2.setString(2,"%"+ThnCari.getSelectedItem()+"-"+BlnCari.getSelectedItem()+"%");
                rs2=ps2.executeQuery();
                rs2.last();
                if(rs2.getRow()>0){
                    hadir=rs2.getString(1);
                }                
                
                ps3.setString(1,rs.getString(4));
                ps3.setString(2,"%"+ThnCari.getSelectedItem()+"-"+BlnCari.getSelectedItem()+"%");
                rs3=ps3.executeQuery();
                rs3.last();
                if(rs3.getRow()>0){
                    siang=rs3.getString(1);
                }
                
                ps9.setString(1,rs.getString(4));
                ps9.setString(2,"%"+ThnCari.getSelectedItem()+"-"+BlnCari.getSelectedItem()+"%");
                rs9=ps9.executeQuery();
                rs9.last();
                if(rs9.getRow()>0){
                    pagi=rs9.getString(1);
                } 
                
                ps4.setString(1,rs.getString(4));
                ps4.setString(2,"%"+ThnCari.getSelectedItem()+"-"+BlnCari.getSelectedItem()+"%");
                rs4=ps4.executeQuery();
                rs4.last();
                if(rs4.getRow()>0){
                    malam=rs4.getString(1);
                }                
                
                ps5.setString(1,rs.getString(4));
                ps5.setString(2,"%"+ThnCari.getSelectedItem()+"-"+BlnCari.getSelectedItem()+"%");                
                rs5=ps5.executeQuery();
                rs5.last();
                if(rs5.getRow()>0){
                    tepatwaktu=rs5.getString(1);
                }
                
                ps6.setString(1,rs.getString(4));
                ps6.setString(2,"%"+ThnCari.getSelectedItem()+"-"+BlnCari.getSelectedItem()+"%");                
                rs6=ps6.executeQuery();
                rs6.last();
                if(rs6.getRow()>0){
                    toleransi=rs6.getString(1);
                }
                
                ps7.setString(1,rs.getString(4));
                ps7.setString(2,"%"+ThnCari.getSelectedItem()+"-"+BlnCari.getSelectedItem()+"%");                
                rs7=ps7.executeQuery();
                rs7.last();
                if(rs7.getRow()>0){
                    terlambat1=rs7.getString(1);
                }
                
                ps8.setString(1,rs.getString(4));
                ps8.setString(2,"%"+ThnCari.getSelectedItem()+"-"+BlnCari.getSelectedItem()+"%");                
                rs8=ps8.executeQuery();
                rs8.last();
                if(rs8.getRow()>0){
                    terlambat2=rs8.getString(1);
                }
                
                terlambat3=Valid.SetAngka2(Double.parseDouble(terlambat1)-Double.parseDouble(terlambat2));
                
                tabMode.addRow(new Object[]{rs.getString(1),
                               rs.getString(2),
                               rs.getString(3),
                               hadir,
                               pagi,
                               siang,
                               malam,
                               tepatwaktu,
                               toleransi,
                               terlambat3,
                               terlambat2});
             }
        }catch(SQLException e){
            System.out.println("Notifikasi : "+e);
        }
        LCount.setText(""+tabMode.getRowCount());
    }
    
    private  void loadTahun(){
        Valid.LoadTahun(ThnCari);
    }

}

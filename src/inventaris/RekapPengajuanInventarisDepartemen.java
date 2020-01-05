/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgLhtBiaya.java
 *
 * Created on 12 Jul 10, 16:21:34
 */

package inventaris;

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
public final class RekapPengajuanInventarisDepartemen extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0,ttljan=0,ttlfeb=0,ttlmar=0,ttlapr=0,ttlmei=0,ttljun=0,ttljul=0,ttlagu=0,ttlsep=0,ttlokt=0,ttlnov=0,ttldes=0,
            jan=0,feb=0,mar=0,apr=0,mei=0,jun=0,jul=0,agu=0,sep=0,okt=0,nov=0,des=0,
            jmljan=0,jmlfeb=0,jmlmar=0,jmlapr=0,jmlmei=0,jmljun=0,jmljul=0,jmlagu=0,jmlsep=0,jmlokt=0,jmlnov=0,jmldes=0,
            ttljmljan=0,ttljmlfeb=0,ttljmlmar=0,ttljmlapr=0,ttljmlmei=0,ttljmljun=0,ttljmljul=0,ttljmlagu=0,ttljmlsep=0,ttljmlokt=0,ttljmlnov=0,ttljmldes=0,
            ttljanproses=0,ttlfebproses=0,ttlmarproses=0,ttlaprproses=0,ttlmeiproses=0,ttljunproses=0,ttljulproses=0,ttlaguproses=0,ttlsepproses=0,ttloktproses=0,ttlnovproses=0,ttldesproses=0,
            janproses=0,febproses=0,marproses=0,aprproses=0,meiproses=0,junproses=0,julproses=0,aguproses=0,sepproses=0,oktproses=0,novproses=0,desproses=0,
            jmljanproses=0,jmlfebproses=0,jmlmarproses=0,jmlaprproses=0,jmlmeiproses=0,jmljunproses=0,jmljulproses=0,jmlaguproses=0,jmlsepproses=0,jmloktproses=0,jmlnovproses=0,jmldesproses=0,
            ttljmljanproses=0,ttljmlfebproses=0,ttljmlmarproses=0,ttljmlaprproses=0,ttljmlmeiproses=0,ttljmljunproses=0,ttljmljulproses=0,ttljmlaguproses=0,ttljmlsepproses=0,ttljmloktproses=0,ttljmlnovproses=0,ttljmldesproses=0,
            ttljandisetujui=0,ttlfebdisetujui=0,ttlmardisetujui=0,ttlaprdisetujui=0,ttlmeidisetujui=0,ttljundisetujui=0,ttljuldisetujui=0,ttlagudisetujui=0,ttlsepdisetujui=0,ttloktdisetujui=0,ttlnovdisetujui=0,ttldesdisetujui=0,
            jandisetujui=0,febdisetujui=0,mardisetujui=0,aprdisetujui=0,meidisetujui=0,jundisetujui=0,juldisetujui=0,agudisetujui=0,sepdisetujui=0,oktdisetujui=0,novdisetujui=0,desdisetujui=0,
            jmljandisetujui=0,jmlfebdisetujui=0,jmlmardisetujui=0,jmlaprdisetujui=0,jmlmeidisetujui=0,jmljundisetujui=0,jmljuldisetujui=0,jmlagudisetujui=0,jmlsepdisetujui=0,jmloktdisetujui=0,jmlnovdisetujui=0,jmldesdisetujui=0,
            ttljmljandisetujui=0,ttljmlfebdisetujui=0,ttljmlmardisetujui=0,ttljmlaprdisetujui=0,ttljmlmeidisetujui=0,ttljmljundisetujui=0,ttljmljuldisetujui=0,ttljmlagudisetujui=0,ttljmlsepdisetujui=0,ttljmloktdisetujui=0,ttljmlnovdisetujui=0,ttljmldesdisetujui=0,
            ttljanditolak=0,ttlfebditolak=0,ttlmarditolak=0,ttlaprditolak=0,ttlmeiditolak=0,ttljunditolak=0,ttljulditolak=0,ttlaguditolak=0,ttlsepditolak=0,ttloktditolak=0,ttlnovditolak=0,ttldesditolak=0,
            janditolak=0,febditolak=0,marditolak=0,aprditolak=0,meiditolak=0,junditolak=0,julditolak=0,aguditolak=0,sepditolak=0,oktditolak=0,novditolak=0,desditolak=0,
            jmljanditolak=0,jmlfebditolak=0,jmlmarditolak=0,jmlaprditolak=0,jmlmeiditolak=0,jmljunditolak=0,jmljulditolak=0,jmlaguditolak=0,jmlsepditolak=0,jmloktditolak=0,jmlnovditolak=0,jmldesditolak=0,
            ttljmljanditolak=0,ttljmlfebditolak=0,ttljmlmarditolak=0,ttljmlaprditolak=0,ttljmlmeiditolak=0,ttljmljunditolak=0,ttljmljulditolak=0,ttljmlaguditolak=0,ttljmlsepditolak=0,ttljmloktditolak=0,ttljmlnovditolak=0,ttljmldesditolak=0;
            
    /** Creates new form DlgLhtBiaya
     * @param parent
     * @param modal */
    public RekapPengajuanInventarisDepartemen(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(885,674);

        tabMode=new DefaultTableModel(null,new Object[]{
                "No.","Departemen","Jan","Feb","Mar","Apr",
                "Mei","Jun","Jul","Agu","Sep","Okt","Nov","Des","Total"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbBangsal.setModel(tabMode);
        //tbBangsal.setDefaultRenderer(Object.class, new WarnaTable(jPanel2.getBackground(),tbBangsal.getBackground()));
        tbBangsal.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbBangsal.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 15; i++) {
            TableColumn column = tbBangsal.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(30);
            }else if(i==1){
                column.setPreferredWidth(180);
            }else if(i==14){
                column.setPreferredWidth(125);
            }else{
                column.setPreferredWidth(95);
            }
        }
        tbBangsal.setDefaultRenderer(Object.class, new WarnaTable());

        TCari.setDocument(new batasInput((int)200).getKata(TCari));
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
        Scroll = new widget.ScrollPane();
        tbBangsal = new widget.Table();
        panelGlass5 = new widget.panelisi();
        label11 = new widget.Label();
        ThnCari = new widget.ComboBox();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        jLabel7 = new widget.Label();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 253, 247)), "::[ Rekap Pengajuan Aset/Inventaris Per Departemen ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbBangsal.setName("tbBangsal"); // NOI18N
        Scroll.setViewportView(tbBangsal);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        panelGlass5.setName("panelGlass5"); // NOI18N
        panelGlass5.setPreferredSize(new java.awt.Dimension(55, 55));
        panelGlass5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label11.setText("Tahun :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(45, 23));
        panelGlass5.add(label11);

        ThnCari.setName("ThnCari"); // NOI18N
        ThnCari.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass5.add(ThnCari);

        jLabel6.setText("Keyword :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass5.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(250, 23));
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
        jLabel7.setPreferredSize(new java.awt.Dimension(30, 23));
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
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            //TCari.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            
            Map<String, Object> param = new HashMap<>();         
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());   
            Sequel.queryu("truncate table temporary");
            for(int r=0;r<tabMode.getRowCount();r++){ 
                if(!tbBangsal.getValueAt(r,0).toString().contains(">>")){
                    Sequel.menyimpan("temporary","'0','"+
                                    tabMode.getValueAt(r,0).toString()+"','"+
                                    tabMode.getValueAt(r,1).toString()+"','"+
                                    tabMode.getValueAt(r,2).toString()+"','"+
                                    tabMode.getValueAt(r,3).toString()+"','"+
                                    tabMode.getValueAt(r,4).toString()+"','"+
                                    tabMode.getValueAt(r,5).toString()+"','"+
                                    tabMode.getValueAt(r,6).toString()+"','"+
                                    tabMode.getValueAt(r,7).toString()+"','"+
                                    tabMode.getValueAt(r,8).toString()+"','"+
                                    tabMode.getValueAt(r,9).toString()+"','"+
                                    tabMode.getValueAt(r,10).toString()+"','"+
                                    tabMode.getValueAt(r,11).toString()+"','"+
                                    tabMode.getValueAt(r,12).toString()+"','"+
                                    tabMode.getValueAt(r,13).toString()+"','"+
                                    tabMode.getValueAt(r,14).toString()+"','','','','','','','','','','','','','','','','','','','','','',''","Jenis Cidera");
                }                    
            }
               
            Valid.MyReport("rptRekapPengajuanInventarisDepartemen.jasper","report","::[ Laporan Rekap Pengajuan Aset/Inventaris Per Departemen ]::",param);
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
        }else{Valid.pindah(evt,BtnKeluar,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
       tampil();
}//GEN-LAST:event_BtnCariActionPerformed

private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
            tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }else{
            Valid.pindah(evt, TCari, BtnPrint);
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
           tampil();
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            
        }
    }//GEN-LAST:event_BtnAllKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RekapPengajuanInventarisDepartemen dialog = new RekapPengajuanInventarisDepartemen(new javax.swing.JFrame(), true);
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
    private widget.TextBox TCari;
    private widget.ComboBox ThnCari;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label label11;
    private widget.panelisi panelGlass5;
    private widget.Table tbBangsal;
    // End of variables declaration//GEN-END:variables

    public void tampil(){        
        try{   
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
            Valid.tabelKosong(tabMode);
            ps=koneksi.prepareStatement(
                "select dep_id,nama from departemen where nama like ? order by nama");
            try {
                ps.setString(1,"%"+TCari.getText()+"%");
                rs=ps.executeQuery();
                i=1;
                ttljan=0;ttlfeb=0;ttlmar=0;ttlapr=0;ttlmei=0;ttljun=0;ttljul=0;ttlagu=0;ttlsep=0;ttlokt=0;ttlnov=0;ttldes=0;
                ttljmljan=0;ttljmlfeb=0;ttljmlmar=0;ttljmlapr=0;ttljmlmei=0;ttljmljun=0;ttljmljul=0;ttljmlagu=0;ttljmlsep=0;ttljmlokt=0;ttljmlnov=0;ttljmldes=0;
                ttljanproses=0;ttlfebproses=0;ttlmarproses=0;ttlaprproses=0;ttlmeiproses=0;ttljunproses=0;ttljulproses=0;ttlaguproses=0;ttlsepproses=0;ttloktproses=0;ttlnovproses=0;ttldesproses=0;
                ttljmljanproses=0;ttljmlfebproses=0;ttljmlmarproses=0;ttljmlaprproses=0;ttljmlmeiproses=0;ttljmljunproses=0;ttljmljulproses=0;ttljmlaguproses=0;ttljmlsepproses=0;ttljmloktproses=0;ttljmlnovproses=0;ttljmldesproses=0;
                ttljandisetujui=0;ttlfebdisetujui=0;ttlmardisetujui=0;ttlaprdisetujui=0;ttlmeidisetujui=0;ttljundisetujui=0;ttljuldisetujui=0;ttlagudisetujui=0;ttlsepdisetujui=0;ttloktdisetujui=0;ttlnovdisetujui=0;ttldesdisetujui=0;
                ttljmljandisetujui=0;ttljmlfebdisetujui=0;ttljmlmardisetujui=0;ttljmlaprdisetujui=0;ttljmlmeidisetujui=0;ttljmljundisetujui=0;ttljmljuldisetujui=0;ttljmlagudisetujui=0;ttljmlsepdisetujui=0;ttljmloktdisetujui=0;ttljmlnovdisetujui=0;ttljmldesdisetujui=0;
                ttljanditolak=0;ttlfebditolak=0;ttlmarditolak=0;ttlaprditolak=0;ttlmeiditolak=0;ttljunditolak=0;ttljulditolak=0;ttlaguditolak=0;ttlsepditolak=0;ttloktditolak=0;ttlnovditolak=0;ttldesditolak=0;
                ttljmljanditolak=0;ttljmlfebditolak=0;ttljmlmarditolak=0;ttljmlaprditolak=0;ttljmlmeiditolak=0;ttljmljunditolak=0;ttljmljulditolak=0;ttljmlaguditolak=0;ttljmlsepditolak=0;ttljmloktditolak=0;ttljmlnovditolak=0;ttljmldesditolak=0;
                while(rs.next()){
                    tabMode.addRow(new String[]{i+"",rs.getString("nama"),"","","","","","","","","","","","",""});
                    jan=Sequel.cariInteger("select sum(pengajuan_inventaris.total) as total from pengajuan_inventaris inner join pegawai inner join departemen on pengajuan_inventaris.nik=pegawai.nik and pegawai.departemen=departemen.dep_id  where departemen.dep_id=? and tanggal like ?",rs.getString("dep_id"),"%"+ThnCari.getSelectedItem()+"-01%");
                    feb=Sequel.cariInteger("select sum(pengajuan_inventaris.total) as total from pengajuan_inventaris inner join pegawai inner join departemen on pengajuan_inventaris.nik=pegawai.nik and pegawai.departemen=departemen.dep_id  where departemen.dep_id=? and tanggal like ?",rs.getString("dep_id"),"%"+ThnCari.getSelectedItem()+"-02%");
                    mar=Sequel.cariInteger("select sum(pengajuan_inventaris.total) as total from pengajuan_inventaris inner join pegawai inner join departemen on pengajuan_inventaris.nik=pegawai.nik and pegawai.departemen=departemen.dep_id  where departemen.dep_id=? and tanggal like ?",rs.getString("dep_id"),"%"+ThnCari.getSelectedItem()+"-03%");
                    apr=Sequel.cariInteger("select sum(pengajuan_inventaris.total) as total from pengajuan_inventaris inner join pegawai inner join departemen on pengajuan_inventaris.nik=pegawai.nik and pegawai.departemen=departemen.dep_id  where departemen.dep_id=? and tanggal like ?",rs.getString("dep_id"),"%"+ThnCari.getSelectedItem()+"-04%");
                    mei=Sequel.cariInteger("select sum(pengajuan_inventaris.total) as total from pengajuan_inventaris inner join pegawai inner join departemen on pengajuan_inventaris.nik=pegawai.nik and pegawai.departemen=departemen.dep_id  where departemen.dep_id=? and tanggal like ?",rs.getString("dep_id"),"%"+ThnCari.getSelectedItem()+"-05%");
                    jun=Sequel.cariInteger("select sum(pengajuan_inventaris.total) as total from pengajuan_inventaris inner join pegawai inner join departemen on pengajuan_inventaris.nik=pegawai.nik and pegawai.departemen=departemen.dep_id  where departemen.dep_id=? and tanggal like ?",rs.getString("dep_id"),"%"+ThnCari.getSelectedItem()+"-06%");
                    jul=Sequel.cariInteger("select sum(pengajuan_inventaris.total) as total from pengajuan_inventaris inner join pegawai inner join departemen on pengajuan_inventaris.nik=pegawai.nik and pegawai.departemen=departemen.dep_id  where departemen.dep_id=? and tanggal like ?",rs.getString("dep_id"),"%"+ThnCari.getSelectedItem()+"-07%");
                    agu=Sequel.cariInteger("select sum(pengajuan_inventaris.total) as total from pengajuan_inventaris inner join pegawai inner join departemen on pengajuan_inventaris.nik=pegawai.nik and pegawai.departemen=departemen.dep_id  where departemen.dep_id=? and tanggal like ?",rs.getString("dep_id"),"%"+ThnCari.getSelectedItem()+"-08%");
                    sep=Sequel.cariInteger("select sum(pengajuan_inventaris.total) as total from pengajuan_inventaris inner join pegawai inner join departemen on pengajuan_inventaris.nik=pegawai.nik and pegawai.departemen=departemen.dep_id  where departemen.dep_id=? and tanggal like ?",rs.getString("dep_id"),"%"+ThnCari.getSelectedItem()+"-09%");
                    okt=Sequel.cariInteger("select sum(pengajuan_inventaris.total) as total from pengajuan_inventaris inner join pegawai inner join departemen on pengajuan_inventaris.nik=pegawai.nik and pegawai.departemen=departemen.dep_id  where departemen.dep_id=? and tanggal like ?",rs.getString("dep_id"),"%"+ThnCari.getSelectedItem()+"-10%");
                    nov=Sequel.cariInteger("select sum(pengajuan_inventaris.total) as total from pengajuan_inventaris inner join pegawai inner join departemen on pengajuan_inventaris.nik=pegawai.nik and pegawai.departemen=departemen.dep_id  where departemen.dep_id=? and tanggal like ?",rs.getString("dep_id"),"%"+ThnCari.getSelectedItem()+"-11%");
                    des=Sequel.cariInteger("select sum(pengajuan_inventaris.total) as total from pengajuan_inventaris inner join pegawai inner join departemen on pengajuan_inventaris.nik=pegawai.nik and pegawai.departemen=departemen.dep_id  where departemen.dep_id=? and tanggal like ?",rs.getString("dep_id"),"%"+ThnCari.getSelectedItem()+"-12%");
                    jmljan=Sequel.cariInteger("select count(departemen.dep_id) as jumlah from pengajuan_inventaris inner join pegawai inner join departemen on pengajuan_inventaris.nik=pegawai.nik and pegawai.departemen=departemen.dep_id  where departemen.dep_id=? and tanggal like ?",rs.getString("dep_id"),"%"+ThnCari.getSelectedItem()+"-01%");
                    jmlfeb=Sequel.cariInteger("select count(departemen.dep_id) as jumlah from pengajuan_inventaris inner join pegawai inner join departemen on pengajuan_inventaris.nik=pegawai.nik and pegawai.departemen=departemen.dep_id  where departemen.dep_id=? and tanggal like ?",rs.getString("dep_id"),"%"+ThnCari.getSelectedItem()+"-02%");
                    jmlmar=Sequel.cariInteger("select count(departemen.dep_id) as jumlah from pengajuan_inventaris inner join pegawai inner join departemen on pengajuan_inventaris.nik=pegawai.nik and pegawai.departemen=departemen.dep_id  where departemen.dep_id=? and tanggal like ?",rs.getString("dep_id"),"%"+ThnCari.getSelectedItem()+"-03%");
                    jmlapr=Sequel.cariInteger("select count(departemen.dep_id) as jumlah from pengajuan_inventaris inner join pegawai inner join departemen on pengajuan_inventaris.nik=pegawai.nik and pegawai.departemen=departemen.dep_id  where departemen.dep_id=? and tanggal like ?",rs.getString("dep_id"),"%"+ThnCari.getSelectedItem()+"-04%");
                    jmlmei=Sequel.cariInteger("select count(departemen.dep_id) as jumlah from pengajuan_inventaris inner join pegawai inner join departemen on pengajuan_inventaris.nik=pegawai.nik and pegawai.departemen=departemen.dep_id  where departemen.dep_id=? and tanggal like ?",rs.getString("dep_id"),"%"+ThnCari.getSelectedItem()+"-05%");
                    jmljun=Sequel.cariInteger("select count(departemen.dep_id) as jumlah from pengajuan_inventaris inner join pegawai inner join departemen on pengajuan_inventaris.nik=pegawai.nik and pegawai.departemen=departemen.dep_id  where departemen.dep_id=? and tanggal like ?",rs.getString("dep_id"),"%"+ThnCari.getSelectedItem()+"-06%");
                    jmljul=Sequel.cariInteger("select count(departemen.dep_id) as jumlah from pengajuan_inventaris inner join pegawai inner join departemen on pengajuan_inventaris.nik=pegawai.nik and pegawai.departemen=departemen.dep_id  where departemen.dep_id=? and tanggal like ?",rs.getString("dep_id"),"%"+ThnCari.getSelectedItem()+"-07%");
                    jmlagu=Sequel.cariInteger("select count(departemen.dep_id) as jumlah from pengajuan_inventaris inner join pegawai inner join departemen on pengajuan_inventaris.nik=pegawai.nik and pegawai.departemen=departemen.dep_id  where departemen.dep_id=? and tanggal like ?",rs.getString("dep_id"),"%"+ThnCari.getSelectedItem()+"-08%");
                    jmlsep=Sequel.cariInteger("select count(departemen.dep_id) as jumlah from pengajuan_inventaris inner join pegawai inner join departemen on pengajuan_inventaris.nik=pegawai.nik and pegawai.departemen=departemen.dep_id  where departemen.dep_id=? and tanggal like ?",rs.getString("dep_id"),"%"+ThnCari.getSelectedItem()+"-09%");
                    jmlokt=Sequel.cariInteger("select count(departemen.dep_id) as jumlah from pengajuan_inventaris inner join pegawai inner join departemen on pengajuan_inventaris.nik=pegawai.nik and pegawai.departemen=departemen.dep_id  where departemen.dep_id=? and tanggal like ?",rs.getString("dep_id"),"%"+ThnCari.getSelectedItem()+"-10%");
                    jmlnov=Sequel.cariInteger("select count(departemen.dep_id) as jumlah from pengajuan_inventaris inner join pegawai inner join departemen on pengajuan_inventaris.nik=pegawai.nik and pegawai.departemen=departemen.dep_id  where departemen.dep_id=? and tanggal like ?",rs.getString("dep_id"),"%"+ThnCari.getSelectedItem()+"-11%");
                    jmldes=Sequel.cariInteger("select count(departemen.dep_id) as jumlah from pengajuan_inventaris inner join pegawai inner join departemen on pengajuan_inventaris.nik=pegawai.nik and pegawai.departemen=departemen.dep_id  where departemen.dep_id=? and tanggal like ?",rs.getString("dep_id"),"%"+ThnCari.getSelectedItem()+"-12%");
                    
                    ttljan=ttljan+jan;
                    ttlfeb=ttlfeb+feb;
                    ttlmar=ttlmar+mar;
                    ttlapr=ttlapr+apr;
                    ttlmei=ttlmei+mei;
                    ttljun=ttljun+jun;
                    ttljul=ttljul+jul;
                    ttlagu=ttlagu+agu;
                    ttlsep=ttlsep+sep;
                    ttlokt=ttlokt+okt;
                    ttlnov=ttlnov+nov;
                    ttldes=ttldes+des;
                    
                    ttljmljan=ttljmljan+jmljan;
                    ttljmlfeb=ttljmlfeb+jmlfeb;
                    ttljmlmar=ttljmlmar+jmlmar;
                    ttljmlapr=ttljmlapr+jmlapr;
                    ttljmlmei=ttljmlmei+jmlmei;
                    ttljmljun=ttljmljun+jmljun;
                    ttljmljul=ttljmljul+jmljul;
                    ttljmlagu=ttljmlagu+jmlagu;
                    ttljmlsep=ttljmlsep+jmlsep;
                    ttljmlokt=ttljmlokt+jmlokt;
                    ttljmlnov=ttljmlnov+jmlnov;
                    ttljmldes=ttljmldes+jmldes;
                    
                    tabMode.addRow(new String[]{
                        "","  Pengajuan",jmljan+"("+Valid.SetAngka(jan)+")",jmlfeb+"("+Valid.SetAngka(feb)+")",jmlmar+"("+Valid.SetAngka(mar)+")",jmlapr+"("+Valid.SetAngka(apr)+")",jmlmei+"("+Valid.SetAngka(mei)+")",jmljun+"("+Valid.SetAngka(jun)+")",jmljul+"("+Valid.SetAngka(jul)+")",jmlagu+"("+Valid.SetAngka(agu)+")",jmlsep+"("+Valid.SetAngka(sep)+")",jmlokt+"("+Valid.SetAngka(okt)+")",jmlnov+"("+Valid.SetAngka(nov)+")",jmldes+"("+Valid.SetAngka(des)+")",
                        (jmljan+jmlfeb+jmlmar+jmlapr+jmlmei+jmljun+jmljul+jmlagu+jmlsep+jmlokt+jmlnov+jmldes)+"("+Valid.SetAngka((jan+feb+mar+apr+mei+jun+jul+agu+sep+okt+nov+des))+")"
                    });
                    
                    janproses=Sequel.cariInteger("select sum(pengajuan_inventaris.total) as total from pengajuan_inventaris inner join pegawai inner join departemen on pengajuan_inventaris.nik=pegawai.nik and pegawai.departemen=departemen.dep_id  where pengajuan_inventaris.status='Proses Pengajuan' and departemen.dep_id=? and tanggal like ?",rs.getString("dep_id"),"%"+ThnCari.getSelectedItem()+"-01%");
                    febproses=Sequel.cariInteger("select sum(pengajuan_inventaris.total) as total from pengajuan_inventaris inner join pegawai inner join departemen on pengajuan_inventaris.nik=pegawai.nik and pegawai.departemen=departemen.dep_id  where pengajuan_inventaris.status='Proses Pengajuan' and departemen.dep_id=? and tanggal like ?",rs.getString("dep_id"),"%"+ThnCari.getSelectedItem()+"-02%");
                    marproses=Sequel.cariInteger("select sum(pengajuan_inventaris.total) as total from pengajuan_inventaris inner join pegawai inner join departemen on pengajuan_inventaris.nik=pegawai.nik and pegawai.departemen=departemen.dep_id  where pengajuan_inventaris.status='Proses Pengajuan' and departemen.dep_id=? and tanggal like ?",rs.getString("dep_id"),"%"+ThnCari.getSelectedItem()+"-03%");
                    aprproses=Sequel.cariInteger("select sum(pengajuan_inventaris.total) as total from pengajuan_inventaris inner join pegawai inner join departemen on pengajuan_inventaris.nik=pegawai.nik and pegawai.departemen=departemen.dep_id  where pengajuan_inventaris.status='Proses Pengajuan' and departemen.dep_id=? and tanggal like ?",rs.getString("dep_id"),"%"+ThnCari.getSelectedItem()+"-04%");
                    meiproses=Sequel.cariInteger("select sum(pengajuan_inventaris.total) as total from pengajuan_inventaris inner join pegawai inner join departemen on pengajuan_inventaris.nik=pegawai.nik and pegawai.departemen=departemen.dep_id  where pengajuan_inventaris.status='Proses Pengajuan' and departemen.dep_id=? and tanggal like ?",rs.getString("dep_id"),"%"+ThnCari.getSelectedItem()+"-05%");
                    junproses=Sequel.cariInteger("select sum(pengajuan_inventaris.total) as total from pengajuan_inventaris inner join pegawai inner join departemen on pengajuan_inventaris.nik=pegawai.nik and pegawai.departemen=departemen.dep_id  where pengajuan_inventaris.status='Proses Pengajuan' and departemen.dep_id=? and tanggal like ?",rs.getString("dep_id"),"%"+ThnCari.getSelectedItem()+"-06%");
                    julproses=Sequel.cariInteger("select sum(pengajuan_inventaris.total) as total from pengajuan_inventaris inner join pegawai inner join departemen on pengajuan_inventaris.nik=pegawai.nik and pegawai.departemen=departemen.dep_id  where pengajuan_inventaris.status='Proses Pengajuan' and departemen.dep_id=? and tanggal like ?",rs.getString("dep_id"),"%"+ThnCari.getSelectedItem()+"-07%");
                    aguproses=Sequel.cariInteger("select sum(pengajuan_inventaris.total) as total from pengajuan_inventaris inner join pegawai inner join departemen on pengajuan_inventaris.nik=pegawai.nik and pegawai.departemen=departemen.dep_id  where pengajuan_inventaris.status='Proses Pengajuan' and departemen.dep_id=? and tanggal like ?",rs.getString("dep_id"),"%"+ThnCari.getSelectedItem()+"-08%");
                    sepproses=Sequel.cariInteger("select sum(pengajuan_inventaris.total) as total from pengajuan_inventaris inner join pegawai inner join departemen on pengajuan_inventaris.nik=pegawai.nik and pegawai.departemen=departemen.dep_id  where pengajuan_inventaris.status='Proses Pengajuan' and departemen.dep_id=? and tanggal like ?",rs.getString("dep_id"),"%"+ThnCari.getSelectedItem()+"-09%");
                    oktproses=Sequel.cariInteger("select sum(pengajuan_inventaris.total) as total from pengajuan_inventaris inner join pegawai inner join departemen on pengajuan_inventaris.nik=pegawai.nik and pegawai.departemen=departemen.dep_id  where pengajuan_inventaris.status='Proses Pengajuan' and departemen.dep_id=? and tanggal like ?",rs.getString("dep_id"),"%"+ThnCari.getSelectedItem()+"-10%");
                    novproses=Sequel.cariInteger("select sum(pengajuan_inventaris.total) as total from pengajuan_inventaris inner join pegawai inner join departemen on pengajuan_inventaris.nik=pegawai.nik and pegawai.departemen=departemen.dep_id  where pengajuan_inventaris.status='Proses Pengajuan' and departemen.dep_id=? and tanggal like ?",rs.getString("dep_id"),"%"+ThnCari.getSelectedItem()+"-11%");
                    desproses=Sequel.cariInteger("select sum(pengajuan_inventaris.total) as total from pengajuan_inventaris inner join pegawai inner join departemen on pengajuan_inventaris.nik=pegawai.nik and pegawai.departemen=departemen.dep_id  where pengajuan_inventaris.status='Proses Pengajuan' and departemen.dep_id=? and tanggal like ?",rs.getString("dep_id"),"%"+ThnCari.getSelectedItem()+"-12%");
                    jmljanproses=Sequel.cariInteger("select count(departemen.dep_id) as jumlah from pengajuan_inventaris inner join pegawai inner join departemen on pengajuan_inventaris.nik=pegawai.nik and pegawai.departemen=departemen.dep_id  where pengajuan_inventaris.status='Proses Pengajuan' and departemen.dep_id=? and tanggal like ?",rs.getString("dep_id"),"%"+ThnCari.getSelectedItem()+"-01%");
                    jmlfebproses=Sequel.cariInteger("select count(departemen.dep_id) as jumlah from pengajuan_inventaris inner join pegawai inner join departemen on pengajuan_inventaris.nik=pegawai.nik and pegawai.departemen=departemen.dep_id  where pengajuan_inventaris.status='Proses Pengajuan' and departemen.dep_id=? and tanggal like ?",rs.getString("dep_id"),"%"+ThnCari.getSelectedItem()+"-02%");
                    jmlmarproses=Sequel.cariInteger("select count(departemen.dep_id) as jumlah from pengajuan_inventaris inner join pegawai inner join departemen on pengajuan_inventaris.nik=pegawai.nik and pegawai.departemen=departemen.dep_id  where pengajuan_inventaris.status='Proses Pengajuan' and departemen.dep_id=? and tanggal like ?",rs.getString("dep_id"),"%"+ThnCari.getSelectedItem()+"-03%");
                    jmlaprproses=Sequel.cariInteger("select count(departemen.dep_id) as jumlah from pengajuan_inventaris inner join pegawai inner join departemen on pengajuan_inventaris.nik=pegawai.nik and pegawai.departemen=departemen.dep_id  where pengajuan_inventaris.status='Proses Pengajuan' and departemen.dep_id=? and tanggal like ?",rs.getString("dep_id"),"%"+ThnCari.getSelectedItem()+"-04%");
                    jmlmeiproses=Sequel.cariInteger("select count(departemen.dep_id) as jumlah from pengajuan_inventaris inner join pegawai inner join departemen on pengajuan_inventaris.nik=pegawai.nik and pegawai.departemen=departemen.dep_id  where pengajuan_inventaris.status='Proses Pengajuan' and departemen.dep_id=? and tanggal like ?",rs.getString("dep_id"),"%"+ThnCari.getSelectedItem()+"-05%");
                    jmljunproses=Sequel.cariInteger("select count(departemen.dep_id) as jumlah from pengajuan_inventaris inner join pegawai inner join departemen on pengajuan_inventaris.nik=pegawai.nik and pegawai.departemen=departemen.dep_id  where pengajuan_inventaris.status='Proses Pengajuan' and departemen.dep_id=? and tanggal like ?",rs.getString("dep_id"),"%"+ThnCari.getSelectedItem()+"-06%");
                    jmljulproses=Sequel.cariInteger("select count(departemen.dep_id) as jumlah from pengajuan_inventaris inner join pegawai inner join departemen on pengajuan_inventaris.nik=pegawai.nik and pegawai.departemen=departemen.dep_id  where pengajuan_inventaris.status='Proses Pengajuan' and departemen.dep_id=? and tanggal like ?",rs.getString("dep_id"),"%"+ThnCari.getSelectedItem()+"-07%");
                    jmlaguproses=Sequel.cariInteger("select count(departemen.dep_id) as jumlah from pengajuan_inventaris inner join pegawai inner join departemen on pengajuan_inventaris.nik=pegawai.nik and pegawai.departemen=departemen.dep_id  where pengajuan_inventaris.status='Proses Pengajuan' and departemen.dep_id=? and tanggal like ?",rs.getString("dep_id"),"%"+ThnCari.getSelectedItem()+"-08%");
                    jmlsepproses=Sequel.cariInteger("select count(departemen.dep_id) as jumlah from pengajuan_inventaris inner join pegawai inner join departemen on pengajuan_inventaris.nik=pegawai.nik and pegawai.departemen=departemen.dep_id  where pengajuan_inventaris.status='Proses Pengajuan' and departemen.dep_id=? and tanggal like ?",rs.getString("dep_id"),"%"+ThnCari.getSelectedItem()+"-09%");
                    jmloktproses=Sequel.cariInteger("select count(departemen.dep_id) as jumlah from pengajuan_inventaris inner join pegawai inner join departemen on pengajuan_inventaris.nik=pegawai.nik and pegawai.departemen=departemen.dep_id  where pengajuan_inventaris.status='Proses Pengajuan' and departemen.dep_id=? and tanggal like ?",rs.getString("dep_id"),"%"+ThnCari.getSelectedItem()+"-10%");
                    jmlnovproses=Sequel.cariInteger("select count(departemen.dep_id) as jumlah from pengajuan_inventaris inner join pegawai inner join departemen on pengajuan_inventaris.nik=pegawai.nik and pegawai.departemen=departemen.dep_id  where pengajuan_inventaris.status='Proses Pengajuan' and departemen.dep_id=? and tanggal like ?",rs.getString("dep_id"),"%"+ThnCari.getSelectedItem()+"-11%");
                    jmldesproses=Sequel.cariInteger("select count(departemen.dep_id) as jumlah from pengajuan_inventaris inner join pegawai inner join departemen on pengajuan_inventaris.nik=pegawai.nik and pegawai.departemen=departemen.dep_id  where pengajuan_inventaris.status='Proses Pengajuan' and departemen.dep_id=? and tanggal like ?",rs.getString("dep_id"),"%"+ThnCari.getSelectedItem()+"-12%");
                    
                    ttljanproses=ttljanproses+janproses;
                    ttlfebproses=ttlfebproses+febproses;
                    ttlmarproses=ttlmarproses+marproses;
                    ttlaprproses=ttlaprproses+aprproses;
                    ttlmeiproses=ttlmeiproses+meiproses;
                    ttljunproses=ttljunproses+junproses;
                    ttljulproses=ttljulproses+julproses;
                    ttlaguproses=ttlaguproses+aguproses;
                    ttlsepproses=ttlsepproses+sepproses;
                    ttloktproses=ttloktproses+oktproses;
                    ttlnovproses=ttlnovproses+novproses;
                    ttldesproses=ttldesproses+desproses;
                    
                    ttljmljanproses=ttljmljanproses+jmljanproses;
                    ttljmlfebproses=ttljmlfebproses+jmlfebproses;
                    ttljmlmarproses=ttljmlmarproses+jmlmarproses;
                    ttljmlaprproses=ttljmlaprproses+jmlaprproses;
                    ttljmlmeiproses=ttljmlmeiproses+jmlmeiproses;
                    ttljmljunproses=ttljmljunproses+jmljunproses;
                    ttljmljulproses=ttljmljulproses+jmljulproses;
                    ttljmlaguproses=ttljmlaguproses+jmlaguproses;
                    ttljmlsepproses=ttljmlsepproses+jmlsepproses;
                    ttljmloktproses=ttljmloktproses+jmloktproses;
                    ttljmlnovproses=ttljmlnovproses+jmlnovproses;
                    ttljmldesproses=ttljmldesproses+jmldesproses;
                    
                    tabMode.addRow(new String[]{
                        "","  Proses",jmljanproses+"("+Valid.SetAngka(janproses)+")",jmlfebproses+"("+Valid.SetAngka(febproses)+")",jmlmarproses+"("+Valid.SetAngka(marproses)+")",jmlaprproses+"("+Valid.SetAngka(aprproses)+")",jmlmeiproses+"("+Valid.SetAngka(meiproses)+")",jmljunproses+"("+Valid.SetAngka(junproses)+")",jmljulproses+"("+Valid.SetAngka(julproses)+")",jmlaguproses+"("+Valid.SetAngka(aguproses)+")",jmlsepproses+"("+Valid.SetAngka(sepproses)+")",jmloktproses+"("+Valid.SetAngka(oktproses)+")",jmlnovproses+"("+Valid.SetAngka(novproses)+")",jmldesproses+"("+Valid.SetAngka(desproses)+")",
                        (jmljanproses+jmlfebproses+jmlmarproses+jmlaprproses+jmlmeiproses+jmljunproses+jmljulproses+jmlaguproses+jmlsepproses+jmloktproses+jmlnovproses+jmldesproses)+"("+Valid.SetAngka((janproses+febproses+marproses+aprproses+meiproses+junproses+julproses+aguproses+sepproses+oktproses+novproses+desproses))+")"
                    });
                    
                    jandisetujui=Sequel.cariInteger("select sum(pengajuan_inventaris.total) as total from pengajuan_inventaris inner join pegawai inner join departemen on pengajuan_inventaris.nik=pegawai.nik and pegawai.departemen=departemen.dep_id  where pengajuan_inventaris.status='Disetujui' and departemen.dep_id=? and tanggal like ?",rs.getString("dep_id"),"%"+ThnCari.getSelectedItem()+"-01%");
                    febdisetujui=Sequel.cariInteger("select sum(pengajuan_inventaris.total) as total from pengajuan_inventaris inner join pegawai inner join departemen on pengajuan_inventaris.nik=pegawai.nik and pegawai.departemen=departemen.dep_id  where pengajuan_inventaris.status='Disetujui' and departemen.dep_id=? and tanggal like ?",rs.getString("dep_id"),"%"+ThnCari.getSelectedItem()+"-02%");
                    mardisetujui=Sequel.cariInteger("select sum(pengajuan_inventaris.total) as total from pengajuan_inventaris inner join pegawai inner join departemen on pengajuan_inventaris.nik=pegawai.nik and pegawai.departemen=departemen.dep_id  where pengajuan_inventaris.status='Disetujui' and departemen.dep_id=? and tanggal like ?",rs.getString("dep_id"),"%"+ThnCari.getSelectedItem()+"-03%");
                    aprdisetujui=Sequel.cariInteger("select sum(pengajuan_inventaris.total) as total from pengajuan_inventaris inner join pegawai inner join departemen on pengajuan_inventaris.nik=pegawai.nik and pegawai.departemen=departemen.dep_id  where pengajuan_inventaris.status='Disetujui' and departemen.dep_id=? and tanggal like ?",rs.getString("dep_id"),"%"+ThnCari.getSelectedItem()+"-04%");
                    meidisetujui=Sequel.cariInteger("select sum(pengajuan_inventaris.total) as total from pengajuan_inventaris inner join pegawai inner join departemen on pengajuan_inventaris.nik=pegawai.nik and pegawai.departemen=departemen.dep_id  where pengajuan_inventaris.status='Disetujui' and departemen.dep_id=? and tanggal like ?",rs.getString("dep_id"),"%"+ThnCari.getSelectedItem()+"-05%");
                    jundisetujui=Sequel.cariInteger("select sum(pengajuan_inventaris.total) as total from pengajuan_inventaris inner join pegawai inner join departemen on pengajuan_inventaris.nik=pegawai.nik and pegawai.departemen=departemen.dep_id  where pengajuan_inventaris.status='Disetujui' and departemen.dep_id=? and tanggal like ?",rs.getString("dep_id"),"%"+ThnCari.getSelectedItem()+"-06%");
                    juldisetujui=Sequel.cariInteger("select sum(pengajuan_inventaris.total) as total from pengajuan_inventaris inner join pegawai inner join departemen on pengajuan_inventaris.nik=pegawai.nik and pegawai.departemen=departemen.dep_id  where pengajuan_inventaris.status='Disetujui' and departemen.dep_id=? and tanggal like ?",rs.getString("dep_id"),"%"+ThnCari.getSelectedItem()+"-07%");
                    agudisetujui=Sequel.cariInteger("select sum(pengajuan_inventaris.total) as total from pengajuan_inventaris inner join pegawai inner join departemen on pengajuan_inventaris.nik=pegawai.nik and pegawai.departemen=departemen.dep_id  where pengajuan_inventaris.status='Disetujui' and departemen.dep_id=? and tanggal like ?",rs.getString("dep_id"),"%"+ThnCari.getSelectedItem()+"-08%");
                    sepdisetujui=Sequel.cariInteger("select sum(pengajuan_inventaris.total) as total from pengajuan_inventaris inner join pegawai inner join departemen on pengajuan_inventaris.nik=pegawai.nik and pegawai.departemen=departemen.dep_id  where pengajuan_inventaris.status='Disetujui' and departemen.dep_id=? and tanggal like ?",rs.getString("dep_id"),"%"+ThnCari.getSelectedItem()+"-09%");
                    oktdisetujui=Sequel.cariInteger("select sum(pengajuan_inventaris.total) as total from pengajuan_inventaris inner join pegawai inner join departemen on pengajuan_inventaris.nik=pegawai.nik and pegawai.departemen=departemen.dep_id  where pengajuan_inventaris.status='Disetujui' and departemen.dep_id=? and tanggal like ?",rs.getString("dep_id"),"%"+ThnCari.getSelectedItem()+"-10%");
                    novdisetujui=Sequel.cariInteger("select sum(pengajuan_inventaris.total) as total from pengajuan_inventaris inner join pegawai inner join departemen on pengajuan_inventaris.nik=pegawai.nik and pegawai.departemen=departemen.dep_id  where pengajuan_inventaris.status='Disetujui' and departemen.dep_id=? and tanggal like ?",rs.getString("dep_id"),"%"+ThnCari.getSelectedItem()+"-11%");
                    desdisetujui=Sequel.cariInteger("select sum(pengajuan_inventaris.total) as total from pengajuan_inventaris inner join pegawai inner join departemen on pengajuan_inventaris.nik=pegawai.nik and pegawai.departemen=departemen.dep_id  where pengajuan_inventaris.status='Disetujui' and departemen.dep_id=? and tanggal like ?",rs.getString("dep_id"),"%"+ThnCari.getSelectedItem()+"-12%");
                    jmljandisetujui=Sequel.cariInteger("select count(departemen.dep_id) as jumlah from pengajuan_inventaris inner join pegawai inner join departemen on pengajuan_inventaris.nik=pegawai.nik and pegawai.departemen=departemen.dep_id  where pengajuan_inventaris.status='Disetujui' and departemen.dep_id=? and tanggal like ?",rs.getString("dep_id"),"%"+ThnCari.getSelectedItem()+"-01%");
                    jmlfebdisetujui=Sequel.cariInteger("select count(departemen.dep_id) as jumlah from pengajuan_inventaris inner join pegawai inner join departemen on pengajuan_inventaris.nik=pegawai.nik and pegawai.departemen=departemen.dep_id  where pengajuan_inventaris.status='Disetujui' and departemen.dep_id=? and tanggal like ?",rs.getString("dep_id"),"%"+ThnCari.getSelectedItem()+"-02%");
                    jmlmardisetujui=Sequel.cariInteger("select count(departemen.dep_id) as jumlah from pengajuan_inventaris inner join pegawai inner join departemen on pengajuan_inventaris.nik=pegawai.nik and pegawai.departemen=departemen.dep_id  where pengajuan_inventaris.status='Disetujui' and departemen.dep_id=? and tanggal like ?",rs.getString("dep_id"),"%"+ThnCari.getSelectedItem()+"-03%");
                    jmlaprdisetujui=Sequel.cariInteger("select count(departemen.dep_id) as jumlah from pengajuan_inventaris inner join pegawai inner join departemen on pengajuan_inventaris.nik=pegawai.nik and pegawai.departemen=departemen.dep_id  where pengajuan_inventaris.status='Disetujui' and departemen.dep_id=? and tanggal like ?",rs.getString("dep_id"),"%"+ThnCari.getSelectedItem()+"-04%");
                    jmlmeidisetujui=Sequel.cariInteger("select count(departemen.dep_id) as jumlah from pengajuan_inventaris inner join pegawai inner join departemen on pengajuan_inventaris.nik=pegawai.nik and pegawai.departemen=departemen.dep_id  where pengajuan_inventaris.status='Disetujui' and departemen.dep_id=? and tanggal like ?",rs.getString("dep_id"),"%"+ThnCari.getSelectedItem()+"-05%");
                    jmljundisetujui=Sequel.cariInteger("select count(departemen.dep_id) as jumlah from pengajuan_inventaris inner join pegawai inner join departemen on pengajuan_inventaris.nik=pegawai.nik and pegawai.departemen=departemen.dep_id  where pengajuan_inventaris.status='Disetujui' and departemen.dep_id=? and tanggal like ?",rs.getString("dep_id"),"%"+ThnCari.getSelectedItem()+"-06%");
                    jmljuldisetujui=Sequel.cariInteger("select count(departemen.dep_id) as jumlah from pengajuan_inventaris inner join pegawai inner join departemen on pengajuan_inventaris.nik=pegawai.nik and pegawai.departemen=departemen.dep_id  where pengajuan_inventaris.status='Disetujui' and departemen.dep_id=? and tanggal like ?",rs.getString("dep_id"),"%"+ThnCari.getSelectedItem()+"-07%");
                    jmlagudisetujui=Sequel.cariInteger("select count(departemen.dep_id) as jumlah from pengajuan_inventaris inner join pegawai inner join departemen on pengajuan_inventaris.nik=pegawai.nik and pegawai.departemen=departemen.dep_id  where pengajuan_inventaris.status='Disetujui' and departemen.dep_id=? and tanggal like ?",rs.getString("dep_id"),"%"+ThnCari.getSelectedItem()+"-08%");
                    jmlsepdisetujui=Sequel.cariInteger("select count(departemen.dep_id) as jumlah from pengajuan_inventaris inner join pegawai inner join departemen on pengajuan_inventaris.nik=pegawai.nik and pegawai.departemen=departemen.dep_id  where pengajuan_inventaris.status='Disetujui' and departemen.dep_id=? and tanggal like ?",rs.getString("dep_id"),"%"+ThnCari.getSelectedItem()+"-09%");
                    jmloktdisetujui=Sequel.cariInteger("select count(departemen.dep_id) as jumlah from pengajuan_inventaris inner join pegawai inner join departemen on pengajuan_inventaris.nik=pegawai.nik and pegawai.departemen=departemen.dep_id  where pengajuan_inventaris.status='Disetujui' and departemen.dep_id=? and tanggal like ?",rs.getString("dep_id"),"%"+ThnCari.getSelectedItem()+"-10%");
                    jmlnovdisetujui=Sequel.cariInteger("select count(departemen.dep_id) as jumlah from pengajuan_inventaris inner join pegawai inner join departemen on pengajuan_inventaris.nik=pegawai.nik and pegawai.departemen=departemen.dep_id  where pengajuan_inventaris.status='Disetujui' and departemen.dep_id=? and tanggal like ?",rs.getString("dep_id"),"%"+ThnCari.getSelectedItem()+"-11%");
                    jmldesdisetujui=Sequel.cariInteger("select count(departemen.dep_id) as jumlah from pengajuan_inventaris inner join pegawai inner join departemen on pengajuan_inventaris.nik=pegawai.nik and pegawai.departemen=departemen.dep_id  where pengajuan_inventaris.status='Disetujui' and departemen.dep_id=? and tanggal like ?",rs.getString("dep_id"),"%"+ThnCari.getSelectedItem()+"-12%");
                    
                    ttljandisetujui=ttljandisetujui+jandisetujui;
                    ttlfebdisetujui=ttlfebdisetujui+febdisetujui;
                    ttlmardisetujui=ttlmardisetujui+mardisetujui;
                    ttlaprdisetujui=ttlaprdisetujui+aprdisetujui;
                    ttlmeidisetujui=ttlmeidisetujui+meidisetujui;
                    ttljundisetujui=ttljundisetujui+jundisetujui;
                    ttljuldisetujui=ttljuldisetujui+juldisetujui;
                    ttlagudisetujui=ttlagudisetujui+agudisetujui;
                    ttlsepdisetujui=ttlsepdisetujui+sepdisetujui;
                    ttloktdisetujui=ttloktdisetujui+oktdisetujui;
                    ttlnovdisetujui=ttlnovdisetujui+novdisetujui;
                    ttldesdisetujui=ttldesdisetujui+desdisetujui;
                    
                    ttljmljandisetujui=ttljmljandisetujui+jmljandisetujui;
                    ttljmlfebdisetujui=ttljmlfebdisetujui+jmlfebdisetujui;
                    ttljmlmardisetujui=ttljmlmardisetujui+jmlmardisetujui;
                    ttljmlaprdisetujui=ttljmlaprdisetujui+jmlaprdisetujui;
                    ttljmlmeidisetujui=ttljmlmeidisetujui+jmlmeidisetujui;
                    ttljmljundisetujui=ttljmljundisetujui+jmljundisetujui;
                    ttljmljuldisetujui=ttljmljuldisetujui+jmljuldisetujui;
                    ttljmlagudisetujui=ttljmlagudisetujui+jmlagudisetujui;
                    ttljmlsepdisetujui=ttljmlsepdisetujui+jmlsepdisetujui;
                    ttljmloktdisetujui=ttljmloktdisetujui+jmloktdisetujui;
                    ttljmlnovdisetujui=ttljmlnovdisetujui+jmlnovdisetujui;
                    ttljmldesdisetujui=ttljmldesdisetujui+jmldesdisetujui;
                    
                    tabMode.addRow(new String[]{
                        "","  Disetujui",jmljandisetujui+"("+Valid.SetAngka(jandisetujui)+")",jmlfebdisetujui+"("+Valid.SetAngka(febdisetujui)+")",jmlmardisetujui+"("+Valid.SetAngka(mardisetujui)+")",jmlaprdisetujui+"("+Valid.SetAngka(aprdisetujui)+")",jmlmeidisetujui+"("+Valid.SetAngka(meidisetujui)+")",jmljundisetujui+"("+Valid.SetAngka(jundisetujui)+")",jmljuldisetujui+"("+Valid.SetAngka(juldisetujui)+")",jmlagudisetujui+"("+Valid.SetAngka(agudisetujui)+")",jmlsepdisetujui+"("+Valid.SetAngka(sepdisetujui)+")",jmloktdisetujui+"("+Valid.SetAngka(oktdisetujui)+")",jmlnovdisetujui+"("+Valid.SetAngka(novdisetujui)+")",jmldesdisetujui+"("+Valid.SetAngka(desdisetujui)+")",
                        (jmljandisetujui+jmlfebdisetujui+jmlmardisetujui+jmlaprdisetujui+jmlmeidisetujui+jmljundisetujui+jmljuldisetujui+jmlagudisetujui+jmlsepdisetujui+jmloktdisetujui+jmlnovdisetujui+jmldesdisetujui)+"("+Valid.SetAngka((jandisetujui+febdisetujui+mardisetujui+aprdisetujui+meidisetujui+jundisetujui+juldisetujui+agudisetujui+sepdisetujui+oktdisetujui+novdisetujui+desdisetujui))+")"
                    });
                    
                    janditolak=Sequel.cariInteger("select sum(pengajuan_inventaris.total) as total from pengajuan_inventaris inner join pegawai inner join departemen on pengajuan_inventaris.nik=pegawai.nik and pegawai.departemen=departemen.dep_id  where pengajuan_inventaris.status='Ditolak' and departemen.dep_id=? and tanggal like ?",rs.getString("dep_id"),"%"+ThnCari.getSelectedItem()+"-01%");
                    febditolak=Sequel.cariInteger("select sum(pengajuan_inventaris.total) as total from pengajuan_inventaris inner join pegawai inner join departemen on pengajuan_inventaris.nik=pegawai.nik and pegawai.departemen=departemen.dep_id  where pengajuan_inventaris.status='Ditolak' and departemen.dep_id=? and tanggal like ?",rs.getString("dep_id"),"%"+ThnCari.getSelectedItem()+"-02%");
                    marditolak=Sequel.cariInteger("select sum(pengajuan_inventaris.total) as total from pengajuan_inventaris inner join pegawai inner join departemen on pengajuan_inventaris.nik=pegawai.nik and pegawai.departemen=departemen.dep_id  where pengajuan_inventaris.status='Ditolak' and departemen.dep_id=? and tanggal like ?",rs.getString("dep_id"),"%"+ThnCari.getSelectedItem()+"-03%");
                    aprditolak=Sequel.cariInteger("select sum(pengajuan_inventaris.total) as total from pengajuan_inventaris inner join pegawai inner join departemen on pengajuan_inventaris.nik=pegawai.nik and pegawai.departemen=departemen.dep_id  where pengajuan_inventaris.status='Ditolak' and departemen.dep_id=? and tanggal like ?",rs.getString("dep_id"),"%"+ThnCari.getSelectedItem()+"-04%");
                    meiditolak=Sequel.cariInteger("select sum(pengajuan_inventaris.total) as total from pengajuan_inventaris inner join pegawai inner join departemen on pengajuan_inventaris.nik=pegawai.nik and pegawai.departemen=departemen.dep_id  where pengajuan_inventaris.status='Ditolak' and departemen.dep_id=? and tanggal like ?",rs.getString("dep_id"),"%"+ThnCari.getSelectedItem()+"-05%");
                    junditolak=Sequel.cariInteger("select sum(pengajuan_inventaris.total) as total from pengajuan_inventaris inner join pegawai inner join departemen on pengajuan_inventaris.nik=pegawai.nik and pegawai.departemen=departemen.dep_id  where pengajuan_inventaris.status='Ditolak' and departemen.dep_id=? and tanggal like ?",rs.getString("dep_id"),"%"+ThnCari.getSelectedItem()+"-06%");
                    julditolak=Sequel.cariInteger("select sum(pengajuan_inventaris.total) as total from pengajuan_inventaris inner join pegawai inner join departemen on pengajuan_inventaris.nik=pegawai.nik and pegawai.departemen=departemen.dep_id  where pengajuan_inventaris.status='Ditolak' and departemen.dep_id=? and tanggal like ?",rs.getString("dep_id"),"%"+ThnCari.getSelectedItem()+"-07%");
                    aguditolak=Sequel.cariInteger("select sum(pengajuan_inventaris.total) as total from pengajuan_inventaris inner join pegawai inner join departemen on pengajuan_inventaris.nik=pegawai.nik and pegawai.departemen=departemen.dep_id  where pengajuan_inventaris.status='Ditolak' and departemen.dep_id=? and tanggal like ?",rs.getString("dep_id"),"%"+ThnCari.getSelectedItem()+"-08%");
                    sepditolak=Sequel.cariInteger("select sum(pengajuan_inventaris.total) as total from pengajuan_inventaris inner join pegawai inner join departemen on pengajuan_inventaris.nik=pegawai.nik and pegawai.departemen=departemen.dep_id  where pengajuan_inventaris.status='Ditolak' and departemen.dep_id=? and tanggal like ?",rs.getString("dep_id"),"%"+ThnCari.getSelectedItem()+"-09%");
                    oktditolak=Sequel.cariInteger("select sum(pengajuan_inventaris.total) as total from pengajuan_inventaris inner join pegawai inner join departemen on pengajuan_inventaris.nik=pegawai.nik and pegawai.departemen=departemen.dep_id  where pengajuan_inventaris.status='Ditolak' and departemen.dep_id=? and tanggal like ?",rs.getString("dep_id"),"%"+ThnCari.getSelectedItem()+"-10%");
                    novditolak=Sequel.cariInteger("select sum(pengajuan_inventaris.total) as total from pengajuan_inventaris inner join pegawai inner join departemen on pengajuan_inventaris.nik=pegawai.nik and pegawai.departemen=departemen.dep_id  where pengajuan_inventaris.status='Ditolak' and departemen.dep_id=? and tanggal like ?",rs.getString("dep_id"),"%"+ThnCari.getSelectedItem()+"-11%");
                    desditolak=Sequel.cariInteger("select sum(pengajuan_inventaris.total) as total from pengajuan_inventaris inner join pegawai inner join departemen on pengajuan_inventaris.nik=pegawai.nik and pegawai.departemen=departemen.dep_id  where pengajuan_inventaris.status='Ditolak' and departemen.dep_id=? and tanggal like ?",rs.getString("dep_id"),"%"+ThnCari.getSelectedItem()+"-12%");
                    jmljanditolak=Sequel.cariInteger("select count(departemen.dep_id) as jumlah from pengajuan_inventaris inner join pegawai inner join departemen on pengajuan_inventaris.nik=pegawai.nik and pegawai.departemen=departemen.dep_id  where pengajuan_inventaris.status='Ditolak' and departemen.dep_id=? and tanggal like ?",rs.getString("dep_id"),"%"+ThnCari.getSelectedItem()+"-01%");
                    jmlfebditolak=Sequel.cariInteger("select count(departemen.dep_id) as jumlah from pengajuan_inventaris inner join pegawai inner join departemen on pengajuan_inventaris.nik=pegawai.nik and pegawai.departemen=departemen.dep_id  where pengajuan_inventaris.status='Ditolak' and departemen.dep_id=? and tanggal like ?",rs.getString("dep_id"),"%"+ThnCari.getSelectedItem()+"-02%");
                    jmlmarditolak=Sequel.cariInteger("select count(departemen.dep_id) as jumlah from pengajuan_inventaris inner join pegawai inner join departemen on pengajuan_inventaris.nik=pegawai.nik and pegawai.departemen=departemen.dep_id  where pengajuan_inventaris.status='Ditolak' and departemen.dep_id=? and tanggal like ?",rs.getString("dep_id"),"%"+ThnCari.getSelectedItem()+"-03%");
                    jmlaprditolak=Sequel.cariInteger("select count(departemen.dep_id) as jumlah from pengajuan_inventaris inner join pegawai inner join departemen on pengajuan_inventaris.nik=pegawai.nik and pegawai.departemen=departemen.dep_id  where pengajuan_inventaris.status='Ditolak' and departemen.dep_id=? and tanggal like ?",rs.getString("dep_id"),"%"+ThnCari.getSelectedItem()+"-04%");
                    jmlmeiditolak=Sequel.cariInteger("select count(departemen.dep_id) as jumlah from pengajuan_inventaris inner join pegawai inner join departemen on pengajuan_inventaris.nik=pegawai.nik and pegawai.departemen=departemen.dep_id  where pengajuan_inventaris.status='Ditolak' and departemen.dep_id=? and tanggal like ?",rs.getString("dep_id"),"%"+ThnCari.getSelectedItem()+"-05%");
                    jmljunditolak=Sequel.cariInteger("select count(departemen.dep_id) as jumlah from pengajuan_inventaris inner join pegawai inner join departemen on pengajuan_inventaris.nik=pegawai.nik and pegawai.departemen=departemen.dep_id  where pengajuan_inventaris.status='Ditolak' and departemen.dep_id=? and tanggal like ?",rs.getString("dep_id"),"%"+ThnCari.getSelectedItem()+"-06%");
                    jmljulditolak=Sequel.cariInteger("select count(departemen.dep_id) as jumlah from pengajuan_inventaris inner join pegawai inner join departemen on pengajuan_inventaris.nik=pegawai.nik and pegawai.departemen=departemen.dep_id  where pengajuan_inventaris.status='Ditolak' and departemen.dep_id=? and tanggal like ?",rs.getString("dep_id"),"%"+ThnCari.getSelectedItem()+"-07%");
                    jmlaguditolak=Sequel.cariInteger("select count(departemen.dep_id) as jumlah from pengajuan_inventaris inner join pegawai inner join departemen on pengajuan_inventaris.nik=pegawai.nik and pegawai.departemen=departemen.dep_id  where pengajuan_inventaris.status='Ditolak' and departemen.dep_id=? and tanggal like ?",rs.getString("dep_id"),"%"+ThnCari.getSelectedItem()+"-08%");
                    jmlsepditolak=Sequel.cariInteger("select count(departemen.dep_id) as jumlah from pengajuan_inventaris inner join pegawai inner join departemen on pengajuan_inventaris.nik=pegawai.nik and pegawai.departemen=departemen.dep_id  where pengajuan_inventaris.status='Ditolak' and departemen.dep_id=? and tanggal like ?",rs.getString("dep_id"),"%"+ThnCari.getSelectedItem()+"-09%");
                    jmloktditolak=Sequel.cariInteger("select count(departemen.dep_id) as jumlah from pengajuan_inventaris inner join pegawai inner join departemen on pengajuan_inventaris.nik=pegawai.nik and pegawai.departemen=departemen.dep_id  where pengajuan_inventaris.status='Ditolak' and departemen.dep_id=? and tanggal like ?",rs.getString("dep_id"),"%"+ThnCari.getSelectedItem()+"-10%");
                    jmlnovditolak=Sequel.cariInteger("select count(departemen.dep_id) as jumlah from pengajuan_inventaris inner join pegawai inner join departemen on pengajuan_inventaris.nik=pegawai.nik and pegawai.departemen=departemen.dep_id  where pengajuan_inventaris.status='Ditolak' and departemen.dep_id=? and tanggal like ?",rs.getString("dep_id"),"%"+ThnCari.getSelectedItem()+"-11%");
                    jmldesditolak=Sequel.cariInteger("select count(departemen.dep_id) as jumlah from pengajuan_inventaris inner join pegawai inner join departemen on pengajuan_inventaris.nik=pegawai.nik and pegawai.departemen=departemen.dep_id  where pengajuan_inventaris.status='Ditolak' and departemen.dep_id=? and tanggal like ?",rs.getString("dep_id"),"%"+ThnCari.getSelectedItem()+"-12%");
                    
                    ttljanditolak=ttljanditolak+janditolak;
                    ttlfebditolak=ttlfebditolak+febditolak;
                    ttlmarditolak=ttlmarditolak+marditolak;
                    ttlaprditolak=ttlaprditolak+aprditolak;
                    ttlmeiditolak=ttlmeiditolak+meiditolak;
                    ttljunditolak=ttljunditolak+junditolak;
                    ttljulditolak=ttljulditolak+julditolak;
                    ttlaguditolak=ttlaguditolak+aguditolak;
                    ttlsepditolak=ttlsepditolak+sepditolak;
                    ttloktditolak=ttloktditolak+oktditolak;
                    ttlnovditolak=ttlnovditolak+novditolak;
                    ttldesditolak=ttldesditolak+desditolak;
                    
                    ttljmljanditolak=ttljmljanditolak+jmljanditolak;
                    ttljmlfebditolak=ttljmlfebditolak+jmlfebditolak;
                    ttljmlmarditolak=ttljmlmarditolak+jmlmarditolak;
                    ttljmlaprditolak=ttljmlaprditolak+jmlaprditolak;
                    ttljmlmeiditolak=ttljmlmeiditolak+jmlmeiditolak;
                    ttljmljunditolak=ttljmljunditolak+jmljunditolak;
                    ttljmljulditolak=ttljmljulditolak+jmljulditolak;
                    ttljmlaguditolak=ttljmlaguditolak+jmlaguditolak;
                    ttljmlsepditolak=ttljmlsepditolak+jmlsepditolak;
                    ttljmloktditolak=ttljmloktditolak+jmloktditolak;
                    ttljmlnovditolak=ttljmlnovditolak+jmlnovditolak;
                    ttljmldesditolak=ttljmldesditolak+jmldesditolak;
                    
                    tabMode.addRow(new String[]{
                        "","  Ditolak",jmljanditolak+"("+Valid.SetAngka(janditolak)+")",jmlfebditolak+"("+Valid.SetAngka(febditolak)+")",jmlmarditolak+"("+Valid.SetAngka(marditolak)+")",jmlaprditolak+"("+Valid.SetAngka(aprditolak)+")",jmlmeiditolak+"("+Valid.SetAngka(meiditolak)+")",jmljunditolak+"("+Valid.SetAngka(junditolak)+")",jmljulditolak+"("+Valid.SetAngka(julditolak)+")",jmlaguditolak+"("+Valid.SetAngka(aguditolak)+")",jmlsepditolak+"("+Valid.SetAngka(sepditolak)+")",jmloktditolak+"("+Valid.SetAngka(oktditolak)+")",jmlnovditolak+"("+Valid.SetAngka(novditolak)+")",jmldesditolak+"("+Valid.SetAngka(desditolak)+")",
                        (jmljanditolak+jmlfebditolak+jmlmarditolak+jmlaprditolak+jmlmeiditolak+jmljunditolak+jmljulditolak+jmlaguditolak+jmlsepditolak+jmloktditolak+jmlnovditolak+jmldesditolak)+"("+Valid.SetAngka((janditolak+febditolak+marditolak+aprditolak+meiditolak+junditolak+julditolak+aguditolak+sepditolak+oktditolak+novditolak+desditolak))+")"
                    });
                    
                    i++;
                }
                if(i>1){
                    tabMode.addRow(new String[]{"","","","","","","","","","","","","","",""});
                    tabMode.addRow(new String[]{
                        "","Jumlah Pengajuan",ttljmljan+"("+Valid.SetAngka(ttljan)+")",ttljmlfeb+"("+Valid.SetAngka(ttlfeb)+")",ttljmlmar+"("+Valid.SetAngka(ttlmar)+")",ttljmlapr+"("+Valid.SetAngka(ttlapr)+")",ttljmlmei+"("+Valid.SetAngka(ttlmei)+")",ttljmljun+"("+Valid.SetAngka(ttljun)+")",
                        ttljmljul+"("+Valid.SetAngka(ttljul)+")",ttljmlagu+"("+Valid.SetAngka(ttlagu)+")",ttljmlsep+"("+Valid.SetAngka(ttlsep)+")",ttljmlokt+"("+Valid.SetAngka(ttlokt)+")",ttljmlnov+"("+Valid.SetAngka(ttlnov)+")",ttljmldes+"("+Valid.SetAngka(ttldes)+")",
                        (ttljmljan+ttljmlfeb+ttljmlmar+ttljmlapr+ttljmlmei+ttljmljun+ttljmljul+ttljmlagu+ttljmlsep+ttljmlokt+ttljmlnov+ttljmldes)+"("+Valid.SetAngka((ttljan+ttlfeb+ttlmar+ttlapr+ttlmei+ttljun+ttljul+ttlagu+ttlsep+ttlokt+ttlnov+ttldes))+")"
                    });
                    tabMode.addRow(new String[]{
                        "","Jumlah Proses",ttljmljanproses+"("+Valid.SetAngka(ttljanproses)+")",ttljmlfebproses+"("+Valid.SetAngka(ttlfebproses)+")",ttljmlmarproses+"("+Valid.SetAngka(ttlmarproses)+")",ttljmlaprproses+"("+Valid.SetAngka(ttlaprproses)+")",ttljmlmeiproses+"("+Valid.SetAngka(ttlmeiproses)+")",ttljmljunproses+"("+Valid.SetAngka(ttljunproses)+")",
                        ttljmljulproses+"("+Valid.SetAngka(ttljulproses)+")",ttljmlaguproses+"("+Valid.SetAngka(ttlaguproses)+")",ttljmlsepproses+"("+Valid.SetAngka(ttlsepproses)+")",ttljmloktproses+"("+Valid.SetAngka(ttloktproses)+")",ttljmlnovproses+"("+Valid.SetAngka(ttlnovproses)+")",ttljmldesproses+"("+Valid.SetAngka(ttldesproses)+")",
                        (ttljmljanproses+ttljmlfebproses+ttljmlmarproses+ttljmlaprproses+ttljmlmeiproses+ttljmljunproses+ttljmljulproses+ttljmlaguproses+ttljmlsepproses+ttljmloktproses+ttljmlnovproses+ttljmldesproses)+"("+Valid.SetAngka((ttljanproses+ttlfebproses+ttlmarproses+ttlaprproses+ttlmeiproses+ttljunproses+ttljulproses+ttlaguproses+ttlsepproses+ttloktproses+ttlnovproses+ttldesproses))+")"
                    });
                    tabMode.addRow(new String[]{
                        "","Jumlah Disetujui",ttljmljandisetujui+"("+Valid.SetAngka(ttljandisetujui)+")",ttljmlfebdisetujui+"("+Valid.SetAngka(ttlfebdisetujui)+")",ttljmlmardisetujui+"("+Valid.SetAngka(ttlmardisetujui)+")",ttljmlaprdisetujui+"("+Valid.SetAngka(ttlaprdisetujui)+")",ttljmlmeidisetujui+"("+Valid.SetAngka(ttlmeidisetujui)+")",ttljmljundisetujui+"("+Valid.SetAngka(ttljundisetujui)+")",
                        ttljmljuldisetujui+"("+Valid.SetAngka(ttljuldisetujui)+")",ttljmlagudisetujui+"("+Valid.SetAngka(ttlagudisetujui)+")",ttljmlsepdisetujui+"("+Valid.SetAngka(ttlsepdisetujui)+")",ttljmloktdisetujui+"("+Valid.SetAngka(ttloktdisetujui)+")",ttljmlnovdisetujui+"("+Valid.SetAngka(ttlnovdisetujui)+")",ttljmldesdisetujui+"("+Valid.SetAngka(ttldesdisetujui)+")",
                        (ttljmljandisetujui+ttljmlfebdisetujui+ttljmlmardisetujui+ttljmlaprdisetujui+ttljmlmeidisetujui+ttljmljundisetujui+ttljmljuldisetujui+ttljmlagudisetujui+ttljmlsepdisetujui+ttljmloktdisetujui+ttljmlnovdisetujui+ttljmldesdisetujui)+"("+Valid.SetAngka((ttljandisetujui+ttlfebdisetujui+ttlmardisetujui+ttlaprdisetujui+ttlmeidisetujui+ttljundisetujui+ttljuldisetujui+ttlagudisetujui+ttlsepdisetujui+ttloktdisetujui+ttlnovdisetujui+ttldesdisetujui))+")"
                    });
                    tabMode.addRow(new String[]{
                        "","Jumlah Ditolak",ttljmljanditolak+"("+Valid.SetAngka(ttljanditolak)+")",ttljmlfebditolak+"("+Valid.SetAngka(ttlfebditolak)+")",ttljmlmarditolak+"("+Valid.SetAngka(ttlmarditolak)+")",ttljmlaprditolak+"("+Valid.SetAngka(ttlaprditolak)+")",ttljmlmeiditolak+"("+Valid.SetAngka(ttlmeiditolak)+")",ttljmljunditolak+"("+Valid.SetAngka(ttljunditolak)+")",
                        ttljmljulditolak+"("+Valid.SetAngka(ttljulditolak)+")",ttljmlaguditolak+"("+Valid.SetAngka(ttlaguditolak)+")",ttljmlsepditolak+"("+Valid.SetAngka(ttlsepditolak)+")",ttljmloktditolak+"("+Valid.SetAngka(ttloktditolak)+")",ttljmlnovditolak+"("+Valid.SetAngka(ttlnovditolak)+")",ttljmldesditolak+"("+Valid.SetAngka(ttldesditolak)+")",
                        (ttljmljanditolak+ttljmlfebditolak+ttljmlmarditolak+ttljmlaprditolak+ttljmlmeiditolak+ttljmljunditolak+ttljmljulditolak+ttljmlaguditolak+ttljmlsepditolak+ttljmloktditolak+ttljmlnovditolak+ttljmldesditolak)+"("+Valid.SetAngka((ttljanditolak+ttlfebditolak+ttlmarditolak+ttlaprditolak+ttlmeiditolak+ttljunditolak+ttljulditolak+ttlaguditolak+ttlsepditolak+ttloktditolak+ttlnovditolak+ttldesditolak))+")"
                    });
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
            this.setCursor(Cursor.getDefaultCursor());
        }catch(Exception e){
            System.out.println("Notif : "+e);
        }    
    }

    

}

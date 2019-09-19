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
public final class DlgKegiatanFarmasi extends javax.swing.JDialog {
    private final DefaultTableModel tabMode,tabMode2,tabMode3;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps,ps2;
    private ResultSet rs,rs2;
    private int i=0;
    private double itempengadaan=0,jmlitempengadaan=0,itemtersedia=0,jmlitemtersedia=0;   
    /** Creates new form DlgLhtBiaya
     * @param parent
     * @param modal */
    public DlgKegiatanFarmasi(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(885,674);
        tabMode=new DefaultTableModel(null,new String[]{"No.","Golongan Obat","Jumlah Item Obat","Jumlah Item Obat Yang Tersedia di RS"}){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbBangsal.setModel(tabMode);
        //tbBangsal.setDefaultRenderer(Object.class, new WarnaTable(jPanel2.getBackground(),tbBangsal.getBackground()));
        tbBangsal.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbBangsal.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 4; i++) {
            TableColumn column = tbBangsal.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(35);
            }else if(i==1){
                column.setPreferredWidth(200);
            }else if(i==2){
                column.setPreferredWidth(200);
            }else if(i==3){
                column.setPreferredWidth(300);
            }
        }
        tbBangsal.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabMode2=new DefaultTableModel(null,new String[]{"No.","Jenis Obat","Jumlah Item Obat","Jumlah Item Obat Yang Tersedia di RS"}){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbBangsal2.setModel(tabMode2);
        //tbBangsal2.setDefaultRenderer(Object.class, new WarnaTable(jPanel2.getBackground(),tbBangsal2.getBackground()));
        tbBangsal2.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbBangsal2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 4; i++) {
            TableColumn column = tbBangsal2.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(35);
            }else if(i==1){
                column.setPreferredWidth(200);
            }else if(i==2){
                column.setPreferredWidth(200);
            }else if(i==3){
                column.setPreferredWidth(300);
            }
        }
        tbBangsal2.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabMode3=new DefaultTableModel(null,new String[]{"No.","Kategori Obat","Jumlah Item Obat","Jumlah Item Obat Yang Tersedia di RS"}){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbBangsal3.setModel(tabMode3);
        //tbBangsal3.setDefaultRenderer(Object.class, new WarnaTable(jPanel2.getBackground(),tbBangsal3.getBackground()));
        tbBangsal3.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbBangsal3.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 4; i++) {
            TableColumn column = tbBangsal3.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(35);
            }else if(i==1){
                column.setPreferredWidth(200);
            }else if(i==2){
                column.setPreferredWidth(200);
            }else if(i==3){
                column.setPreferredWidth(300);
            }
        }
        tbBangsal3.setDefaultRenderer(Object.class, new WarnaTable());

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
        tbBangsal = new widget.Table();
        Scroll2 = new widget.ScrollPane();
        tbBangsal2 = new widget.Table();
        Scroll3 = new widget.ScrollPane();
        tbBangsal3 = new widget.Table();
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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Kegiatan Farmasi ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50,50,50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        TabRawat.setBackground(new java.awt.Color(255, 255, 253));
        TabRawat.setForeground(new java.awt.Color(50,50,50));
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

        tbBangsal.setName("tbBangsal"); // NOI18N
        Scroll.setViewportView(tbBangsal);

        TabRawat.addTab("Penerimaan Obat Per Golongan", Scroll);

        Scroll2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll2.setName("Scroll2"); // NOI18N
        Scroll2.setOpaque(true);

        tbBangsal2.setName("tbBangsal2"); // NOI18N
        Scroll2.setViewportView(tbBangsal2);

        TabRawat.addTab("Penerimaan Obat Per Jenis", Scroll2);

        Scroll3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll3.setName("Scroll3"); // NOI18N
        Scroll3.setOpaque(true);

        tbBangsal3.setName("tbBangsal3"); // NOI18N
        Scroll3.setViewportView(tbBangsal3);

        TabRawat.addTab("Penerimaan Obat Per Kategori", Scroll3);

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
        TCari.setPreferredSize(new java.awt.Dimension(155, 23));
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
        jLabel7.setPreferredSize(new java.awt.Dimension(15, 23));
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
            param.put("periode",Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem()); 
            param.put("tanggal",Tgl2.getDate());   
            if(TabRawat.getSelectedIndex()==0){
                            
                Sequel.queryu("delete from temporary");
                for(int r=0;r<tabMode.getRowCount();r++){ 
                    if(!tbBangsal.getValueAt(r,0).toString().contains(">>")){
                        Sequel.menyimpan("temporary","'0','"+
                                    tabMode.getValueAt(r,0).toString()+"','"+
                                    tabMode.getValueAt(r,1).toString()+"','"+
                                    tabMode.getValueAt(r,2).toString()+"','"+
                                    tabMode.getValueAt(r,3).toString()+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Rekap Nota Pembayaran");
                    }                    
                }
                
                Valid.MyReport("rptKegiatanFarmasi1.jasper","report","::[ Laporan Kegiatan Farmasi ]::",param);
            }else if(TabRawat.getSelectedIndex()==1){
                            
                Sequel.queryu("delete from temporary");
                for(int r=0;r<tabMode2.getRowCount();r++){ 
                    if(!tbBangsal2.getValueAt(r,0).toString().contains(">>")){
                        Sequel.menyimpan("temporary","'0','"+
                                    tabMode2.getValueAt(r,0).toString()+"','"+
                                    tabMode2.getValueAt(r,1).toString()+"','"+
                                    tabMode2.getValueAt(r,2).toString()+"','"+
                                    tabMode2.getValueAt(r,3).toString()+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Rekap Nota Pembayaran");
                    }                    
                }
                
                Valid.MyReport("rptKegiatanFarmasi2.jasper","report","::[ Laporan Kegiatan Farmasi ]::",param);
            }else if(TabRawat.getSelectedIndex()==2){
                            
                Sequel.queryu("delete from temporary");
                for(int r=0;r<tabMode3.getRowCount();r++){ 
                    if(!tbBangsal3.getValueAt(r,0).toString().contains(">>")){
                        Sequel.menyimpan("temporary","'0','"+
                                    tabMode3.getValueAt(r,0).toString()+"','"+
                                    tabMode3.getValueAt(r,1).toString()+"','"+
                                    tabMode3.getValueAt(r,2).toString()+"','"+
                                    tabMode3.getValueAt(r,3).toString()+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Rekap Nota Pembayaran");
                    }                    
                }
                
                Valid.MyReport("rptKegiatanFarmasi3.jasper","report","::[ Laporan Kegiatan Farmasi ]::",param);
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
        }
}//GEN-LAST:event_BtnCariActionPerformed

private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
            tampil();
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
        }
    }//GEN-LAST:event_TabRawatMouseClicked

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgKegiatanFarmasi dialog = new DlgKegiatanFarmasi(new javax.swing.JFrame(), true);
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
    private widget.Table tbBangsal;
    private widget.Table tbBangsal2;
    private widget.Table tbBangsal3;
    // End of variables declaration//GEN-END:variables

    public void tampil(){        
        try{   
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
            Valid.tabelKosong(tabMode);   
            ps=koneksi.prepareStatement(
                    "select * from golongan_barang where nama like ? order by nama ");
            try {                
                ps.setString(1,"%"+TCari.getText().trim()+"%");
                rs=ps.executeQuery();
                i=1;jmlitempengadaan=0;jmlitemtersedia=0;   
                while(rs.next()){
                    itempengadaan=0;itemtersedia=0;
                    //pemesanan
                    ps2=koneksi.prepareStatement(
                        "select count(distinct detailpesan.kode_brng) as jumlah from pemesanan inner join detailpesan "+
                        " inner join databarang on pemesanan.no_faktur=detailpesan.no_faktur and databarang.kode_brng=detailpesan.kode_brng "+
                        " where databarang.kode_golongan=? and pemesanan.tgl_pesan between ? and ?");
                    try {
                        ps2.setString(1,rs.getString("kode"));
                        ps2.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                        ps2.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                        rs2=ps2.executeQuery();
                        if(rs2.next()){    
                            itempengadaan=rs2.getDouble("jumlah");
                            jmlitempengadaan=jmlitempengadaan+rs2.getDouble("jumlah");
                        }
                    } catch (Exception e) {
                        System.out.println("Pemesanan : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                       
                    ps2=koneksi.prepareStatement(
                            "select count(*) as jumlah from(select gudangbarang.kode_brng from gudangbarang inner join databarang on gudangbarang.kode_brng=databarang.kode_brng where databarang.kode_golongan=? and gudangbarang.stok>0 group by gudangbarang.kode_brng) as data");
                    try {
                        ps2.setString(1,rs.getString("kode"));
                        rs2=ps2.executeQuery();
                        if(rs2.next()){
                            itemtersedia=rs2.getDouble("jumlah");
                            jmlitemtersedia=jmlitemtersedia+rs2.getDouble("jumlah");
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi Stok : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                    
                    tabMode.addRow(new String[]{
                        i+"",rs.getString("nama"),itempengadaan+"",itemtersedia+""
                    });
                    i++;
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
            tabMode.addRow(new String[]{
                "","Total : ",jmlitempengadaan+"",jmlitemtersedia+""
            });
            this.setCursor(Cursor.getDefaultCursor());
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }

    public void tampil2(){        
        try{   
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
            Valid.tabelKosong(tabMode2);   
            ps=koneksi.prepareStatement(
                    "select * from jenis where nama like ? order by nama ");
            try {                
                ps.setString(1,"%"+TCari.getText().trim()+"%");
                rs=ps.executeQuery();
                i=1;jmlitempengadaan=0;jmlitemtersedia=0;   
                while(rs.next()){
                    itempengadaan=0;itemtersedia=0;
                    //pemesanan
                    ps2=koneksi.prepareStatement(
                        "select count(distinct detailpesan.kode_brng) as jumlah from pemesanan inner join detailpesan "+
                        " inner join databarang on pemesanan.no_faktur=detailpesan.no_faktur and databarang.kode_brng=detailpesan.kode_brng "+
                        " where databarang.kdjns=? and pemesanan.tgl_pesan between ? and ?");
                    try {
                        ps2.setString(1,rs.getString("kdjns"));
                        ps2.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                        ps2.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                        rs2=ps2.executeQuery();
                        if(rs2.next()){    
                            itempengadaan=rs2.getDouble("jumlah");
                            jmlitempengadaan=jmlitempengadaan+rs2.getDouble("jumlah");
                        }
                    } catch (Exception e) {
                        System.out.println("Pemesanan : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                       
                    ps2=koneksi.prepareStatement(
                            "select count(*) as jumlah from(select gudangbarang.kode_brng from gudangbarang inner join databarang on gudangbarang.kode_brng=databarang.kode_brng where databarang.kdjns=? and gudangbarang.stok>0 group by gudangbarang.kode_brng) as data");
                    try {
                        ps2.setString(1,rs.getString("kdjns"));
                        rs2=ps2.executeQuery();
                        if(rs2.next()){
                            itemtersedia=rs2.getDouble("jumlah");
                            jmlitemtersedia=jmlitemtersedia+rs2.getDouble("jumlah");
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi Stok : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                    
                    tabMode2.addRow(new String[]{
                        i+"",rs.getString("nama"),itempengadaan+"",itemtersedia+""
                    });
                    i++;
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
            tabMode2.addRow(new String[]{
                "","Total : ",jmlitempengadaan+"",jmlitemtersedia+""
            });
            this.setCursor(Cursor.getDefaultCursor());
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }
    
    public void tampil3(){        
        try{   
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
            Valid.tabelKosong(tabMode3);   
            ps=koneksi.prepareStatement(
                    "select * from kategori_barang where nama like ? order by nama ");
            try {                
                ps.setString(1,"%"+TCari.getText().trim()+"%");
                rs=ps.executeQuery();
                i=1;jmlitempengadaan=0;jmlitemtersedia=0;   
                while(rs.next()){
                    itempengadaan=0;itemtersedia=0;
                    //pemesanan
                    ps2=koneksi.prepareStatement(
                        "select count(distinct detailpesan.kode_brng) as jumlah from pemesanan inner join detailpesan "+
                        " inner join databarang on pemesanan.no_faktur=detailpesan.no_faktur and databarang.kode_brng=detailpesan.kode_brng "+
                        " where databarang.kode_kategori=? and pemesanan.tgl_pesan between ? and ?");
                    try {
                        ps2.setString(1,rs.getString("kode"));
                        ps2.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                        ps2.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                        rs2=ps2.executeQuery();
                        if(rs2.next()){    
                            itempengadaan=rs2.getDouble("jumlah");
                            jmlitempengadaan=jmlitempengadaan+rs2.getDouble("jumlah");
                        }
                    } catch (Exception e) {
                        System.out.println("Pemesanan : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                       
                    ps2=koneksi.prepareStatement(
                            "select count(*) as jumlah from(select gudangbarang.kode_brng from gudangbarang inner join databarang on gudangbarang.kode_brng=databarang.kode_brng where databarang.kode_kategori=? and gudangbarang.stok>0 group by gudangbarang.kode_brng) as data");
                    try {
                        ps2.setString(1,rs.getString("kode"));
                        rs2=ps2.executeQuery();
                        if(rs2.next()){
                            itemtersedia=rs2.getDouble("jumlah");
                            jmlitemtersedia=jmlitemtersedia+rs2.getDouble("jumlah");
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi Stok : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                    
                    tabMode3.addRow(new String[]{
                        i+"",rs.getString("nama"),itempengadaan+"",itemtersedia+""
                    });
                    i++;
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
            tabMode3.addRow(new String[]{
                "","Total : ",jmlitempengadaan+"",jmlitemtersedia+""
            });
            this.setCursor(Cursor.getDefaultCursor());
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }
    
    
}

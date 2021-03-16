/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgLhtBiaya.java
 *
 * Created on 12 Jul 10, 16:21:34
 */

package toko;

import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.validasi;
import fungsi.akses;
import fungsi.sekuel;
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
public final class TokoKeuntunganBarang extends javax.swing.JDialog {
    private final DefaultTableModel tabMode,tabMode2;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;   
    private double totalpenjualan,totalpiutang;
    /** Creates new form DlgLhtBiaya
     * @param parent
     * @param modal */
    public TokoKeuntunganBarang(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(885,674);
        tabMode=new DefaultTableModel(null,new String[]{
                "Tgl.Jual","No.Nota","Barang","Satuan","Harga Jual","Jml.Jual","Subtotal Jual","Disc(%)","Besar Disc","Tambahan","Total Jual","Harga Beli","Total Beli","Keuntungan"
            }){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
              Class[] types = new Class[] {
                  java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,
                  java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,
                  java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,
                  java.lang.Double.class,java.lang.Double.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbPenjualan.setModel(tabMode);
        //tbBangsal.setDefaultRenderer(Object.class, new WarnaTable(jPanel2.getBackground(),tbBangsal.getBackground()));
        tbPenjualan.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbPenjualan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 14; i++) {
            TableColumn column = tbPenjualan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(70);
            }else if(i==1){
                column.setPreferredWidth(100);
            }else if(i==2){
                column.setPreferredWidth(200);
            }else if(i==3){
                column.setPreferredWidth(90);
            }else if(i==4){
                column.setPreferredWidth(90);
            }else if(i==5){
                column.setPreferredWidth(50);
            }else if(i==6){
                column.setPreferredWidth(90);
            }else if(i==7){
                column.setPreferredWidth(50);
            }else if(i==8){
                column.setPreferredWidth(90);
            }else if(i==9){
                column.setPreferredWidth(90);
            }else if(i==10){
                column.setPreferredWidth(90);
            }else if(i==11){
                column.setPreferredWidth(90);
            }else if(i==12){
                column.setPreferredWidth(90);
            }else if(i==13){
                column.setPreferredWidth(90);
            }
        }
        tbPenjualan.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabMode2=new DefaultTableModel(null,new String[]{
                "Tgl.Piutang","No.Nota","Barang","Satuan","Harga Piutang","Jml.Piutang","Subtotal Piutang","Disc(%)","Besar Disc(Rp)","Total Piutang","Harga Beli","Total Beli","Keuntungan"
            }){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
              Class[] types = new Class[] {
                  java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,
                  java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,
                  java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,
                  java.lang.Double.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbPiutang.setModel(tabMode2);
        //tbBangsal2.setDefaultRenderer(Object.class, new WarnaTable(jPanel2.getBackground(),tbBangsal2.getBackground()));
        tbPiutang.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbPiutang.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 13; i++) {
            TableColumn column = tbPiutang.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(70);
            }else if(i==1){
                column.setPreferredWidth(100);
            }else if(i==2){
                column.setPreferredWidth(200);
            }else if(i==3){
                column.setPreferredWidth(90);
            }else if(i==4){
                column.setPreferredWidth(90);
            }else if(i==5){
                column.setPreferredWidth(50);
            }else if(i==6){
                column.setPreferredWidth(90);
            }else if(i==7){
                column.setPreferredWidth(50);
            }else if(i==8){
                column.setPreferredWidth(90);
            }else if(i==9){
                column.setPreferredWidth(90);
            }else if(i==10){
                column.setPreferredWidth(90);
            }else if(i==11){
                column.setPreferredWidth(90);
            }else if(i==12){
                column.setPreferredWidth(90);
            }
        }
        tbPiutang.setDefaultRenderer(Object.class, new WarnaTable());

        TKd.setDocument(new batasInput((byte)20).getKata(TKd));
        if(koneksiDB.CARICEPAT().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampil();
                        tampil2();
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampil();
                        tampil2();
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampil();
                        tampil2();
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
        tbPenjualan = new widget.Table();
        Scroll2 = new widget.ScrollPane();
        tbPiutang = new widget.Table();
        jPanel1 = new javax.swing.JPanel();
        panelisi5 = new widget.panelisi();
        Total = new widget.Label();
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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Keuntungan Barang Toko ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        TabRawat.setBackground(new java.awt.Color(255, 255, 253));
        TabRawat.setForeground(new java.awt.Color(50, 50, 50));
        TabRawat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabRawat.setName("TabRawat"); // NOI18N

        Scroll.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbPenjualan.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbPenjualan.setName("tbPenjualan"); // NOI18N
        Scroll.setViewportView(tbPenjualan);

        TabRawat.addTab("Keuntungan Barang Penjualan", Scroll);

        Scroll2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll2.setName("Scroll2"); // NOI18N
        Scroll2.setOpaque(true);

        tbPiutang.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbPiutang.setName("tbPiutang"); // NOI18N
        Scroll2.setViewportView(tbPiutang);

        TabRawat.addTab("Keuntungan Barang Piutang", Scroll2);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(816, 100));
        jPanel1.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi5.setName("panelisi5"); // NOI18N
        panelisi5.setPreferredSize(new java.awt.Dimension(100, 54));
        panelisi5.setLayout(null);

        Total.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Total.setText("Total Keuntungan = Keuntungan Barang Penjualan + Keuntungan Barang Piutang = 0 + 0 = 0");
        Total.setName("Total"); // NOI18N
        Total.setPreferredSize(new java.awt.Dimension(85, 23));
        panelisi5.add(Total);
        Total.setBounds(10, 10, 1110, 23);

        jPanel1.add(panelisi5, java.awt.BorderLayout.CENTER);

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

        jPanel1.add(panelGlass5, java.awt.BorderLayout.PAGE_END);

        internalFrame1.add(jPanel1, java.awt.BorderLayout.PAGE_END);

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
        param.put("logo",Sequel.cariGambar("select logo from setting")); 
        if(TabRawat.getSelectedIndex()==0){
            if(tabMode.getRowCount()==0){
                JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            }else if(tabMode.getRowCount()!=0){
                Valid.MyReportqry("rptKeuntunganBarangToko1.jasper","report","::[ Keuntungan Penjualan Barang Toko ]::",
                    "select tokopenjualan.tgl_jual,tokopenjualan.nota_jual,toko_detail_jual.kode_brng,tokobarang.nama_brng,kodesatuan.satuan,toko_detail_jual.h_jual,toko_detail_jual.jumlah, "+
                    "toko_detail_jual.subtotal,toko_detail_jual.dis,toko_detail_jual.bsr_dis,toko_detail_jual.tambahan,toko_detail_jual.total, toko_detail_jual.h_beli,(toko_detail_jual.h_beli * toko_detail_jual.jumlah) as total_asal, "+
                    "(toko_detail_jual.total-(toko_detail_jual.h_beli * toko_detail_jual.jumlah)) as keuntungan "+
                    "from tokopenjualan inner join toko_detail_jual on tokopenjualan.nota_jual=toko_detail_jual.nota_jual "+
                    "inner join tokobarang on toko_detail_jual.kode_brng=tokobarang.kode_brng "+
                    "inner join kodesatuan on toko_detail_jual.kode_sat=kodesatuan.kode_sat "+
                    "where tokopenjualan.tgl_jual between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                    (TCari.getText().trim().equals("")?"":" and (tokobarang.nama_brng like '%"+TCari.getText()+"%' or tokopenjualan.nota_jual like '%"+TCari.getText()+"%' or toko_detail_jual.kode_brng like '%"+TCari.getText()+"%' )")+
                    "order by tokopenjualan.tgl_jual,tokopenjualan.nota_jual",param);
            }   
        }else if(TabRawat.getSelectedIndex()==1){
            if(tabMode2.getRowCount()==0){
                JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            }else if(tabMode2.getRowCount()!=0){
                Valid.MyReportqry("rptKeuntunganBarangToko2.jasper","report","::[ Keuntungan Piutang Barang Toko ]::",
                    "select tokopiutang.tgl_piutang,tokopiutang.nota_piutang,toko_detail_piutang.kode_brng,tokobarang.nama_brng,kodesatuan.satuan,toko_detail_piutang.h_jual,toko_detail_piutang.jumlah, "+
                    "toko_detail_piutang.subtotal,toko_detail_piutang.dis,toko_detail_piutang.bsr_dis,toko_detail_piutang.total, toko_detail_piutang.h_beli,(toko_detail_piutang.h_beli * toko_detail_piutang.jumlah) as total_asal, "+
                    "(toko_detail_piutang.total-(toko_detail_piutang.h_beli * toko_detail_piutang.jumlah)) as keuntungan "+
                    "from tokopiutang inner join toko_detail_piutang on tokopiutang.nota_piutang=toko_detail_piutang.nota_piutang "+
                    "inner join tokobarang on toko_detail_piutang.kode_brng=tokobarang.kode_brng "+
                    "inner join kodesatuan on toko_detail_piutang.kode_sat=kodesatuan.kode_sat "+
                    "where tokopiutang.tgl_piutang between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                    (TCari.getText().trim().equals("")?"":" and (tokobarang.nama_brng like '%"+TCari.getText()+"%' or tokopiutang.nota_piutang like '%"+TCari.getText()+"%' or toko_detail_piutang.kode_brng like '%"+TCari.getText()+"%' )")+
                    "order by tokopiutang.tgl_piutang,tokopiutang.nota_piutang",param);
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
        tampil();
        tampil2();
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
           tampil();
           tampil2();
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
            TokoKeuntunganBarang dialog = new TokoKeuntunganBarang(new javax.swing.JFrame(), true);
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
    private widget.TextBox TCari;
    private widget.TextBox TKd;
    private javax.swing.JTabbedPane TabRawat;
    private widget.Tanggal Tgl1;
    private widget.Tanggal Tgl2;
    private widget.Label Total;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private javax.swing.JPanel jPanel1;
    private widget.Label label11;
    private widget.Label label18;
    private widget.panelisi panelGlass5;
    private widget.panelisi panelisi5;
    private widget.Table tbPenjualan;
    private widget.Table tbPiutang;
    // End of variables declaration//GEN-END:variables

    public void tampil(){        
        try {
            Valid.tabelKosong(tabMode);
            ps=koneksi.prepareStatement(
                "select tokopenjualan.tgl_jual,tokopenjualan.nota_jual,toko_detail_jual.kode_brng,tokobarang.nama_brng,kodesatuan.satuan,toko_detail_jual.h_jual,toko_detail_jual.jumlah, "+
                "toko_detail_jual.subtotal,toko_detail_jual.dis,toko_detail_jual.bsr_dis,toko_detail_jual.tambahan,toko_detail_jual.total, toko_detail_jual.h_beli,(toko_detail_jual.h_beli * toko_detail_jual.jumlah) as total_asal, "+
                "(toko_detail_jual.total-(toko_detail_jual.h_beli * toko_detail_jual.jumlah)) as keuntungan "+
                "from tokopenjualan inner join toko_detail_jual on tokopenjualan.nota_jual=toko_detail_jual.nota_jual "+
                "inner join tokobarang on toko_detail_jual.kode_brng=tokobarang.kode_brng "+
                "inner join kodesatuan on toko_detail_jual.kode_sat=kodesatuan.kode_sat "+
                "where tokopenjualan.tgl_jual between ? and ? "+(TCari.getText().trim().equals("")?"":" and (tokobarang.nama_brng like ? or tokopenjualan.nota_jual like ? or toko_detail_jual.kode_brng like ? )")+
                "order by tokopenjualan.tgl_jual,tokopenjualan.nota_jual");
            try {
                ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                if(!TCari.getText().trim().equals("")){
                    ps.setString(3,"%"+TCari.getText()+"%");
                    ps.setString(4,"%"+TCari.getText()+"%");
                    ps.setString(5,"%"+TCari.getText()+"%");
                }
                rs=ps.executeQuery();
                totalpenjualan=0;
                while(rs.next()){
                    totalpenjualan=totalpenjualan+rs.getDouble(15);
                    tabMode.addRow(new Object[]{
                        rs.getString(1),rs.getString(2),rs.getString(3)+", "+rs.getString(4),rs.getString(5),
                        rs.getDouble(6),rs.getDouble(7),rs.getDouble(8),rs.getDouble(9),rs.getDouble(10),
                        rs.getDouble(11),rs.getDouble(12),rs.getDouble(13),rs.getDouble(14),rs.getDouble(15)
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
        isHitung();
    }

    public void tampil2(){        
        try {
            Valid.tabelKosong(tabMode2);
            ps=koneksi.prepareStatement(
                "select tokopiutang.tgl_piutang,tokopiutang.nota_piutang,toko_detail_piutang.kode_brng,tokobarang.nama_brng,kodesatuan.satuan,toko_detail_piutang.h_jual,toko_detail_piutang.jumlah, "+
                "toko_detail_piutang.subtotal,toko_detail_piutang.dis,toko_detail_piutang.bsr_dis,toko_detail_piutang.total, toko_detail_piutang.h_beli,(toko_detail_piutang.h_beli * toko_detail_piutang.jumlah) as total_asal, "+
                "(toko_detail_piutang.total-(toko_detail_piutang.h_beli * toko_detail_piutang.jumlah)) as keuntungan "+
                "from tokopiutang inner join toko_detail_piutang on tokopiutang.nota_piutang=toko_detail_piutang.nota_piutang "+
                "inner join tokobarang on toko_detail_piutang.kode_brng=tokobarang.kode_brng "+
                "inner join kodesatuan on toko_detail_piutang.kode_sat=kodesatuan.kode_sat "+
                "where tokopiutang.tgl_piutang between ? and ? "+(TCari.getText().trim().equals("")?"":" and (tokobarang.nama_brng like ? or tokopiutang.nota_piutang like ? or toko_detail_piutang.kode_brng like ? )")+
                "order by tokopiutang.tgl_piutang,tokopiutang.nota_piutang");
            try {
                ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                if(!TCari.getText().trim().equals("")){
                    ps.setString(3,"%"+TCari.getText()+"%");
                    ps.setString(4,"%"+TCari.getText()+"%");
                    ps.setString(5,"%"+TCari.getText()+"%");
                }
                rs=ps.executeQuery();
                totalpiutang=0;
                while(rs.next()){
                    totalpiutang=totalpiutang+rs.getDouble(14);
                    tabMode2.addRow(new Object[]{
                        rs.getString(1),rs.getString(2),rs.getString(3)+", "+rs.getString(4),rs.getString(5),
                        rs.getDouble(6),rs.getDouble(7),rs.getDouble(8),rs.getDouble(9),rs.getDouble(10),
                        rs.getDouble(11),rs.getDouble(12),rs.getDouble(13),rs.getDouble(14)
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
        isHitung();
    }
    
    private void isHitung(){
        Total.setText("Total Keuntungan = Keuntungan Barang Penjualan + Keuntungan Barang Piutang = "+Valid.SetAngka(totalpenjualan)+" + "+Valid.SetAngka(totalpiutang)+" = "+Valid.SetAngka(totalpenjualan+totalpiutang));
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgAdmin.java
 *
 * Created on 21 Jun 10, 20:53:44
 */

package keuangan;

import fungsi.WarnaTable;
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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author perpustakaan
 */
public class DlgRekapPerShift extends javax.swing.JDialog {
    private final DefaultTableModel tabModeRalan,tabModeRanap,tabModePemasukan,tabModePengeluaran;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement psjamshift,pspasienralan,psbilling,pspasienranap,pspemasukan,pspengeluaran;
    private ResultSet rs,rspasien,rsbilling;
    private String tanggal2="",
            sqlpsjamshift="select * from closing_kasir where shift like ? ",
            sqlpsbilling="select billing.nm_perawatan,billing.totalbiaya,billing.status from billing where billing.no_rawat=? ",
            sqlpspasienranap="select reg_periksa.no_rawat,nota_inap.no_nota,pasien.nm_pasien,nota_inap.tanggal,nota_inap.jam,penjab.png_jawab "+
                        "from reg_periksa inner join pasien inner join penjab inner join nota_inap "+
                        "on reg_periksa.no_rkm_medis=pasien.no_rkm_medis and reg_periksa.kd_pj=penjab.kd_pj and "+
                        "reg_periksa.no_rawat=nota_inap.no_rawat where reg_periksa.status_lanjut='Ranap' and "+
                        "reg_periksa.no_rawat not in (select piutang_pasien.no_rawat from piutang_pasien where piutang_pasien.no_rawat=reg_periksa.no_rawat) and "+
                        "concat(nota_inap.tanggal,' ',nota_inap.jam) between ? and ? order by nota_inap.no_nota",
            sqlpspasienralan="select reg_periksa.no_rawat,nota_jalan.no_nota,pasien.nm_pasien,nota_jalan.tanggal,nota_jalan.jam,dokter.nm_dokter,penjab.png_jawab "+
                        "from reg_periksa inner join pasien inner join penjab inner join dokter inner join nota_jalan "+
                        "on reg_periksa.no_rkm_medis=pasien.no_rkm_medis and reg_periksa.kd_pj=penjab.kd_pj and "+
                        "reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.no_rawat=nota_jalan.no_rawat where reg_periksa.status_lanjut='Ralan' and "+
                        "reg_periksa.no_rawat not in (select piutang_pasien.no_rawat from piutang_pasien where piutang_pasien.no_rawat=reg_periksa.no_rawat) and "+
                        "concat(nota_jalan.tanggal,' ',nota_jalan.jam) between ? and ? order by nota_jalan.no_nota",
            sqlpspemasukan="select pemasukan_lain.tanggal, pemasukan_lain.keterangan, pemasukan_lain.besar, kategori_pemasukan_lain.nama_kategori "+
                        "from pemasukan_lain inner join kategori_pemasukan_lain on pemasukan_lain.kode_kategori=kategori_pemasukan_lain.kode_kategori "+
                        "where pemasukan_lain.tanggal between ? and ? order by pemasukan_lain.tanggal",
            sqlpspengeluaran="select pengeluaran_harian.tanggal, pengeluaran_harian.keterangan, pengeluaran_harian.biaya,  "+
                        "kategori_pengeluaran_harian.nama_kategori from pengeluaran_harian inner join kategori_pengeluaran_harian "+
                        "on pengeluaran_harian.kode_kategori=kategori_pengeluaran_harian.kode_kategori "+
                        "where pengeluaran_harian.tanggal between ? and ? order by pengeluaran_harian.tanggal";
    private int i;
    private double all=0,Laborat=0,Radiologi=0,Obat=0,Ralan_Dokter=0,Ralan_Dokter_Paramedis=0,Ralan_Paramedis=0,Tambahan=0,Potongan=0,Registrasi=0,Service=0,
                    ttlLaborat=0,ttlRadiologi=0,ttlObat=0,ttlRalan_Dokter=0,ttlRalan_Paramedis=0,ttlTambahan=0,ttlPotongan=0,ttlRegistrasi=0,ttlOperasi=0,
                    ttlRanap_Dokter=0,ttlRanap_Paramedis=0,ttlKamar=0,ttlHarian=0,ttlRetur_Obat=0,ttlResep_Pulang=0,ttlService=0,
                    Retur_Obat=0,Resep_Pulang=0,Harian=0,Kamar=0,Operasi=0,Ranap_Dokter=0,Ranap_Dokter_Paramedis=0,Ranap_Paramedis=0;
    /** Creates new form DlgAdmin
     * @param parent
     * @param modal */
    public DlgRekapPerShift(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(10,10);
        setSize(457,249);
        tabModeRalan=new DefaultTableModel(null,new Object[]{
            "Tanggal","No.Nota","Nama Pasien","Jenis Bayar","Perujuk","Registrasi","Obat+Emb+Tsl",
            "Paket Tindakan","Operasi","Laborat","Radiologi","Tambahan","Potongan","Total","Dokter"}){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };

        tbRalan.setModel(tabModeRalan);
        tbRalan.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbRalan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 15; i++) {
            TableColumn column = tbRalan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(130);
            }else if(i==1){
                column.setPreferredWidth(105);
            }else if(i==2){
                column.setPreferredWidth(170);
            }else if(i==3){
                column.setPreferredWidth(85);
            }else if(i==4){
                column.setPreferredWidth(90);
            }else{
                column.setPreferredWidth(85);
            }
        }

        tbRalan.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeRanap=new DefaultTableModel(null,new Object[]{
            "Tanggal","No.Nota","Nama Pasien","Jenis Bayar","Perujuk","Registrasi","Tindakan","Obt+Emb+Tsl",
            "Retur Obat","Resep Pulang","Laborat","Radiologi","Potongan","Tambahan","Kamar+Service","Operasi","Harian","Total"}){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbRanap.setModel(tabModeRanap);        
        tbRanap.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbRanap.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 18; i++) {
            TableColumn column = tbRanap.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(130);
            }else if(i==1){
                column.setPreferredWidth(105);
            }else if(i==2){
                column.setPreferredWidth(170);
            }else if(i==3){
                column.setPreferredWidth(85);
            }else if(i==4){
                column.setPreferredWidth(90);
            }else if(i==17){
                column.setPreferredWidth(100);
            }else{
                column.setPreferredWidth(75);
            }
        }
        tbRanap.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModePemasukan=new DefaultTableModel(null,new Object[]{
            "Tanggal","Kategori","Pemasukan","Keterangan"}){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };

        tbPemasukan.setModel(tabModePemasukan);
        tbPemasukan.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbPemasukan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 4; i++) {
            TableColumn column = tbPemasukan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(130);
            }else if(i==1){
                column.setPreferredWidth(230);
            }else if(i==2){
                column.setPreferredWidth(100);
            }else if(i==3){
                column.setPreferredWidth(190);
            }
        }

        tbPemasukan.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModePengeluaran=new DefaultTableModel(null,new Object[]{
            "Tanggal","Kategori","Pengeluaran","Keterangan"}){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };

        tbPengeluaran.setModel(tabModePengeluaran);
        tbPengeluaran.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbPengeluaran.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 4; i++) {
            TableColumn column = tbPengeluaran.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(130);
            }else if(i==1){
                column.setPreferredWidth(230);
            }else if(i==2){
                column.setPreferredWidth(100);
            }else if(i==3){
                column.setPreferredWidth(190);
            }
        }

        tbPengeluaran.setDefaultRenderer(Object.class, new WarnaTable());
        
        
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
        panelGlass5 = new widget.panelisi();
        label12 = new widget.Label();
        Tgl1 = new widget.Tanggal();
        jLabel9 = new widget.Label();
        CmbStatus = new widget.ComboBox();
        BtnCari1 = new widget.Button();
        label19 = new widget.Label();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();
        TabRawat = new javax.swing.JTabbedPane();
        internalFrame2 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbRalan = new widget.Table();
        internalFrame3 = new widget.InternalFrame();
        Scroll2 = new widget.ScrollPane();
        tbRanap = new widget.Table();
        internalFrame4 = new widget.InternalFrame();
        Scroll3 = new widget.ScrollPane();
        tbPemasukan = new widget.Table();
        internalFrame5 = new widget.InternalFrame();
        Scroll4 = new widget.ScrollPane();
        tbPengeluaran = new widget.Table();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 253, 247)), "::[ Rekap Uang Pershift ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass5.setName("panelGlass5"); // NOI18N
        panelGlass5.setPreferredSize(new java.awt.Dimension(55, 55));
        panelGlass5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label12.setText("Tgl.Rekap :");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(60, 23));
        panelGlass5.add(label12);

        Tgl1.setDisplayFormat("dd-MM-yyyy");
        Tgl1.setName("Tgl1"); // NOI18N
        Tgl1.setPreferredSize(new java.awt.Dimension(100, 23));
        panelGlass5.add(Tgl1);

        jLabel9.setText("Shift :");
        jLabel9.setName("jLabel9"); // NOI18N
        jLabel9.setPreferredSize(new java.awt.Dimension(50, 23));
        panelGlass5.add(jLabel9);

        CmbStatus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Semua", "Pagi", "Siang", "Sore", "Malam" }));
        CmbStatus.setName("CmbStatus"); // NOI18N
        CmbStatus.setPreferredSize(new java.awt.Dimension(100, 23));
        panelGlass5.add(CmbStatus);

        BtnCari1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari1.setMnemonic('2');
        BtnCari1.setToolTipText("Alt+2");
        BtnCari1.setName("BtnCari1"); // NOI18N
        BtnCari1.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCari1ActionPerformed(evt);
            }
        });
        BtnCari1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCari1KeyPressed(evt);
            }
        });
        panelGlass5.add(BtnCari1);

        label19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label19.setName("label19"); // NOI18N
        label19.setPreferredSize(new java.awt.Dimension(50, 23));
        panelGlass5.add(label19);

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

        TabRawat.setBackground(new java.awt.Color(255, 253, 247));
        TabRawat.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(241, 246, 236)));
        TabRawat.setForeground(new java.awt.Color(50, 50, 50));
        TabRawat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabRawat.setName("TabRawat"); // NOI18N
        TabRawat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatMouseClicked(evt);
            }
        });

        internalFrame2.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame2.setBorder(null);
        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbRalan.setToolTipText("");
        tbRalan.setName("tbRalan"); // NOI18N
        Scroll.setViewportView(tbRalan);

        internalFrame2.add(Scroll, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Rekap Pendapatan Ralan", internalFrame2);

        internalFrame3.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame3.setBorder(null);
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll2.setName("Scroll2"); // NOI18N
        Scroll2.setOpaque(true);

        tbRanap.setAutoCreateRowSorter(true);
        tbRanap.setToolTipText("");
        tbRanap.setName("tbRanap"); // NOI18N
        Scroll2.setViewportView(tbRanap);

        internalFrame3.add(Scroll2, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Rekap Pendapatan Ranap", internalFrame3);

        internalFrame4.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame4.setBorder(null);
        internalFrame4.setName("internalFrame4"); // NOI18N
        internalFrame4.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll3.setName("Scroll3"); // NOI18N
        Scroll3.setOpaque(true);

        tbPemasukan.setAutoCreateRowSorter(true);
        tbPemasukan.setToolTipText("");
        tbPemasukan.setName("tbPemasukan"); // NOI18N
        tbPemasukan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPemasukanMouseClicked(evt);
            }
        });
        tbPemasukan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbPemasukanKeyPressed(evt);
            }
        });
        Scroll3.setViewportView(tbPemasukan);

        internalFrame4.add(Scroll3, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Rekap Pemasukan Lain-Lain", internalFrame4);

        internalFrame5.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame5.setBorder(null);
        internalFrame5.setName("internalFrame5"); // NOI18N
        internalFrame5.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll4.setName("Scroll4"); // NOI18N
        Scroll4.setOpaque(true);

        tbPengeluaran.setAutoCreateRowSorter(true);
        tbPengeluaran.setToolTipText("");
        tbPengeluaran.setName("tbPengeluaran"); // NOI18N
        tbPengeluaran.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPengeluaranMouseClicked(evt);
            }
        });
        tbPengeluaran.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbPengeluaranKeyPressed(evt);
            }
        });
        Scroll4.setViewportView(tbPengeluaran);

        internalFrame5.add(Scroll4, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Rekap Pengeluaran Harian", internalFrame5);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();        
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
       if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnPrint,Tgl1);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        if(TabRawat.getSelectedIndex()==0){
           tampilralan();
        }else if(TabRawat.getSelectedIndex()==1){
           tampilranap();
        }else if(TabRawat.getSelectedIndex()==2){
           tampilpemasukan();
        }else if(TabRawat.getSelectedIndex()==3){
           tampilpengeluaran();
        }
    }//GEN-LAST:event_formWindowOpened

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if(TabRawat.getSelectedIndex()==0){
           tampilralan();
        }else if(TabRawat.getSelectedIndex()==1){
           tampilranap();
        }else if(TabRawat.getSelectedIndex()==2){
           tampilpemasukan();
        }else if(TabRawat.getSelectedIndex()==3){
           tampilpengeluaran();
        }
    }//GEN-LAST:event_TabRawatMouseClicked

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        //if(evt.getKeyCode()==KeyEvent.VK_SPACE){
        //    BtnPrintActionPerformed(null);
        //}else{
        //    Valid.pindah(evt,BtnAll,BtnKeluar);
       // }
    }//GEN-LAST:event_BtnPrintKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        if(TabRawat.getSelectedIndex()==0){
            if(tabModeRalan.getRowCount()==0){
                JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            }else if(tabModeRalan.getRowCount()!=0){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                
                Sequel.queryu("truncate table temporary");
                for(int r=0;r<tabModeRalan.getRowCount();r++){  
                        Sequel.menyimpan("temporary","'0','"+
                                        tabModeRalan.getValueAt(r,0).toString().replaceAll("'","`") +"','"+
                                        tabModeRalan.getValueAt(r,1).toString().replaceAll("'","`")+"','"+
                                        tabModeRalan.getValueAt(r,2).toString().replaceAll("'","`")+"','"+
                                        tabModeRalan.getValueAt(r,3).toString().replaceAll("'","`")+"','"+
                                        tabModeRalan.getValueAt(r,4).toString().replaceAll("'","`")+"','"+
                                        tabModeRalan.getValueAt(r,5).toString().replaceAll("'","`")+"','"+
                                        tabModeRalan.getValueAt(r,6).toString().replaceAll("'","`")+"','"+
                                        tabModeRalan.getValueAt(r,7).toString().replaceAll("'","`")+"','"+
                                        tabModeRalan.getValueAt(r,8).toString().replaceAll("'","`")+"','"+
                                        tabModeRalan.getValueAt(r,9).toString().replaceAll("'","`")+"','"+
                                        tabModeRalan.getValueAt(r,10).toString().replaceAll("'","`")+"','"+
                                        tabModeRalan.getValueAt(r,11).toString().replaceAll("'","`")+"','"+
                                        tabModeRalan.getValueAt(r,12).toString().replaceAll("'","`")+"','"+
                                        tabModeRalan.getValueAt(r,13).toString().replaceAll("'","`")+"','','','','','','','','','','','','','','','','','','','','','','',''","data");
                }
                
                Map<String, Object> param = new HashMap<>();                 
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("emailrs",akses.getemailrs());   
                param.put("logo",Sequel.cariGambar("select logo from setting")); 
                Valid.MyReport("rptRekapPendapatanRalan.jasper","report","::[ Rekap Pendapatan Ralan ]::",param);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }else if(TabRawat.getSelectedIndex()==1){
           if(tabModeRanap.getRowCount()==0){
                JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            }else if(tabModeRanap.getRowCount()!=0){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                
                Sequel.queryu("truncate table temporary");
                for(int r=0;r<tabModeRanap.getRowCount();r++){  
                        Sequel.menyimpan("temporary","'0','"+
                                        tabModeRanap.getValueAt(r,0).toString().replaceAll("'","`") +"','"+
                                        tabModeRanap.getValueAt(r,1).toString().replaceAll("'","`")+"','"+
                                        tabModeRanap.getValueAt(r,2).toString().replaceAll("'","`")+"','"+
                                        tabModeRanap.getValueAt(r,3).toString().replaceAll("'","`")+"','"+
                                        tabModeRanap.getValueAt(r,4).toString().replaceAll("'","`")+"','"+
                                        tabModeRanap.getValueAt(r,5).toString().replaceAll("'","`")+"','"+
                                        tabModeRanap.getValueAt(r,6).toString().replaceAll("'","`")+"','"+
                                        tabModeRanap.getValueAt(r,7).toString().replaceAll("'","`")+"','"+
                                        tabModeRanap.getValueAt(r,8).toString().replaceAll("'","`")+"','"+
                                        tabModeRanap.getValueAt(r,9).toString().replaceAll("'","`")+"','"+
                                        tabModeRanap.getValueAt(r,10).toString().replaceAll("'","`")+"','"+
                                        tabModeRanap.getValueAt(r,11).toString().replaceAll("'","`")+"','"+
                                        tabModeRanap.getValueAt(r,12).toString().replaceAll("'","`")+"','"+
                                        tabModeRanap.getValueAt(r,13).toString().replaceAll("'","`")+"','"+
                                        tabModeRanap.getValueAt(r,14).toString().replaceAll("'","`")+"','"+
                                        tabModeRanap.getValueAt(r,15).toString().replaceAll("'","`")+"','"+
                                        tabModeRanap.getValueAt(r,16).toString().replaceAll("'","`")+"','"+
                                        tabModeRanap.getValueAt(r,17).toString().replaceAll("'","`")+"','','','','','','','','','','','','','','','','','','',''","data");
                }
                
                Map<String, Object> param = new HashMap<>();                 
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("emailrs",akses.getemailrs());   
                param.put("logo",Sequel.cariGambar("select logo from setting")); 
                Valid.MyReport("rptRekapPendapatanRanap.jasper","report","::[ Rekap Pendapatan Ranap ]::",param);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }else if(TabRawat.getSelectedIndex()==2){
           if(tabModePemasukan.getRowCount()==0){
                JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            }else if(tabModePemasukan.getRowCount()!=0){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                
                Sequel.queryu("truncate table temporary");
                for(int r=0;r<tabModePemasukan.getRowCount();r++){  
                        Sequel.menyimpan("temporary","'0','"+
                                        tabModePemasukan.getValueAt(r,0).toString().replaceAll("'","`") +"','"+
                                        tabModePemasukan.getValueAt(r,1).toString().replaceAll("'","`")+"','"+
                                        tabModePemasukan.getValueAt(r,2).toString().replaceAll("'","`")+"','"+
                                        tabModePemasukan.getValueAt(r,3).toString().replaceAll("'","`")+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","data");
                }
                
                Map<String, Object> param = new HashMap<>();                 
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("emailrs",akses.getemailrs());   
                param.put("logo",Sequel.cariGambar("select logo from setting")); 
                Valid.MyReport("rptRekapPemasukanLain.jasper","report","::[ Rekap Pemasukan Lain ]::",param);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }else if(TabRawat.getSelectedIndex()==3){
           if(tabModePengeluaran.getRowCount()==0){
                JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            }else if(tabModePengeluaran.getRowCount()!=0){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                
                Sequel.queryu("truncate table temporary");
                for(int r=0;r<tabModePengeluaran.getRowCount();r++){  
                        Sequel.menyimpan("temporary","'0','"+
                                        tabModePengeluaran.getValueAt(r,0).toString().replaceAll("'","`") +"','"+
                                        tabModePengeluaran.getValueAt(r,1).toString().replaceAll("'","`")+"','"+
                                        tabModePengeluaran.getValueAt(r,2).toString().replaceAll("'","`")+"','"+
                                        tabModePengeluaran.getValueAt(r,3).toString().replaceAll("'","`")+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","data");
                }
                
                Map<String, Object> param = new HashMap<>();                 
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("emailrs",akses.getemailrs());   
                param.put("logo",Sequel.cariGambar("select logo from setting")); 
                Valid.MyReport("rptRekapPengeluaranHarian.jasper","report","::[ Rekap Pengeluaran Harian ]::",param);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }            
    }//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnCari1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCari1ActionPerformed
        TabRawatMouseClicked(null);
    }//GEN-LAST:event_BtnCari1ActionPerformed

    private void BtnCari1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCari1KeyPressed
        /*if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }else{
            Valid.pindah(evt, TKd, BtnPrint);
        }*/
    }//GEN-LAST:event_BtnCari1KeyPressed

    private void tbPemasukanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPemasukanMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbPemasukanMouseClicked

    private void tbPemasukanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbPemasukanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbPemasukanKeyPressed

    private void tbPengeluaranMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPengeluaranMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbPengeluaranMouseClicked

    private void tbPengeluaranKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbPengeluaranKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbPengeluaranKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgRekapPerShift dialog = new DlgRekapPerShift(new javax.swing.JFrame(), true);
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
    private widget.Button BtnCari1;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.ComboBox CmbStatus;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll2;
    private widget.ScrollPane Scroll3;
    private widget.ScrollPane Scroll4;
    private javax.swing.JTabbedPane TabRawat;
    private widget.Tanggal Tgl1;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.InternalFrame internalFrame4;
    private widget.InternalFrame internalFrame5;
    private widget.Label jLabel9;
    private widget.Label label12;
    private widget.Label label19;
    private widget.panelisi panelGlass5;
    private widget.Table tbPemasukan;
    private widget.Table tbPengeluaran;
    private widget.Table tbRalan;
    private widget.Table tbRanap;
    // End of variables declaration//GEN-END:variables

    private void tampilralan() {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
        Valid.tabelKosong(tabModeRalan);
        try{      
            psjamshift=koneksi.prepareStatement(sqlpsjamshift);
            try {
                psjamshift.setString(1,"%"+CmbStatus.getSelectedItem().toString().replaceAll("Semua","")+"%");
                rs=psjamshift.executeQuery();
                while(rs.next()){
                    tabModeRalan.addRow(new Object[]{
                        "Shift : "+rs.getString("shift"),rs.getString("jam_masuk")+" - "+rs.getString("jam_pulang"),"","","","","","","","","","","",""
                    });
                    pspasienralan= koneksi.prepareStatement(sqlpspasienralan);
                    try {
                        pspasienralan.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+rs.getString("jam_masuk"));
                        if(rs.getString("shift").equals("Malam")){
                            tanggal2=Sequel.cariIsi("select DATE_ADD('"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+rs.getString("jam_pulang")+"',INTERVAL 1 DAY)");
                            pspasienralan.setString(2,tanggal2);
                        }else{
                            pspasienralan.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+rs.getString("jam_pulang"));
                        }
                        rspasien=pspasienralan.executeQuery();
                        all=0;ttlLaborat=0;ttlRadiologi=0;ttlObat=0;ttlRalan_Dokter=0;ttlOperasi=0;
                        ttlRalan_Paramedis=0;ttlTambahan=0;ttlPotongan=0;ttlRegistrasi=0;                
                        i=1;
                        while(rspasien.next()){
                            Operasi=0;Laborat=0;Radiologi=0;Obat=0;Ralan_Dokter=0;Ralan_Dokter_Paramedis=0;Ralan_Paramedis=0;Tambahan=0;Potongan=0;Registrasi=0;
                            psbilling=koneksi.prepareStatement(sqlpsbilling);
                            try {
                                psbilling.setString(1,rspasien.getString("no_rawat"));
                                rsbilling=psbilling.executeQuery(); 
                                while(rsbilling.next()){
                                    switch (rsbilling.getString("status")) {
                                        case "Laborat":
                                            ttlLaborat=ttlLaborat+rsbilling.getDouble("totalbiaya");
                                            Laborat=Laborat+rsbilling.getDouble("totalbiaya");
                                            break;
                                        case "Radiologi":
                                            ttlRadiologi=ttlRadiologi+rsbilling.getDouble("totalbiaya");
                                            Radiologi=Radiologi+rsbilling.getDouble("totalbiaya");
                                            break;
                                        case "Operasi":
                                            ttlOperasi=ttlOperasi+rsbilling.getDouble("totalbiaya");
                                            Operasi=Operasi+rsbilling.getDouble("totalbiaya");
                                            break;
                                        case "Obat":
                                            ttlObat=ttlObat+rsbilling.getDouble("totalbiaya");
                                            Obat=Obat+rsbilling.getDouble("totalbiaya");
                                            break;
                                        case "Ralan Dokter":
                                            ttlRalan_Dokter=ttlRalan_Dokter+rsbilling.getDouble("totalbiaya");
                                            Ralan_Dokter=Ralan_Dokter+rsbilling.getDouble("totalbiaya");
                                            break;     
                                        case "Ralan Dokter Paramedis":
                                            ttlRalan_Dokter=ttlRalan_Dokter+rsbilling.getDouble("totalbiaya");
                                            Ralan_Dokter_Paramedis=Ralan_Dokter_Paramedis+rsbilling.getDouble("totalbiaya");
                                            break;    
                                        case "Ralan Paramedis":
                                            ttlRalan_Paramedis=ttlRalan_Paramedis+rsbilling.getDouble("totalbiaya");
                                            Ralan_Paramedis=Ralan_Paramedis+rsbilling.getDouble("totalbiaya");
                                            break;
                                        case "Tambahan":
                                            ttlTambahan=ttlTambahan+rsbilling.getDouble("totalbiaya");
                                            Tambahan=Tambahan+rsbilling.getDouble("totalbiaya");
                                            break;
                                        case "Potongan":
                                            ttlPotongan=ttlPotongan+rsbilling.getDouble("totalbiaya");
                                            Potongan=Potongan+rsbilling.getDouble("totalbiaya");
                                            break;
                                        case "Registrasi":
                                            ttlRegistrasi=ttlRegistrasi+rsbilling.getDouble("totalbiaya");
                                            Registrasi=Registrasi+rsbilling.getDouble("totalbiaya");
                                            break;
                                    }                        
                                }
                                all=all+Operasi+Laborat+Radiologi+Obat+Ralan_Dokter+Ralan_Dokter_Paramedis+Ralan_Paramedis+Tambahan+Potongan+Registrasi;
                                tabModeRalan.addRow(new Object[]{
                                    i+". "+rspasien.getString("tanggal")+" "+rspasien.getString("jam"),rspasien.getString("no_nota"),
                                    rspasien.getString("nm_pasien"),rspasien.getString("png_jawab"),Sequel.cariIsi("select perujuk from rujuk_masuk where no_rawat=?",rspasien.getString("no_rawat")),
                                    Valid.SetAngka(Registrasi),Valid.SetAngka(Obat),Valid.SetAngka(Ralan_Dokter+Ralan_Paramedis+Ralan_Dokter_Paramedis),
                                    Valid.SetAngka(Operasi),Valid.SetAngka(Laborat),Valid.SetAngka(Radiologi),Valid.SetAngka(Tambahan),Valid.SetAngka(Potongan),
                                    Valid.SetAngka(Operasi+Laborat+Radiologi+Obat+Ralan_Dokter+Ralan_Paramedis+Ralan_Dokter_Paramedis+Tambahan+Potongan+Registrasi),
                                    rspasien.getString("nm_dokter")                        
                                });
                                i++;
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rsbilling!=null){
                                    rsbilling.close();
                                }
                                if(psbilling!=null){
                                    psbilling.close();
                                }
                            } 
                        }
                        tabModeRalan.addRow(new Object[] {
                            "   >> Total",":","","","",
                            Valid.SetAngka(ttlRegistrasi),
                            Valid.SetAngka(ttlObat),
                            Valid.SetAngka(ttlRalan_Dokter+ttlRalan_Paramedis),
                            Valid.SetAngka(ttlOperasi),
                            Valid.SetAngka(ttlLaborat),
                            Valid.SetAngka(ttlRadiologi),
                            Valid.SetAngka(ttlTambahan),
                            Valid.SetAngka(ttlPotongan),
                            Valid.SetAngka(ttlOperasi+ttlLaborat+ttlRadiologi+ttlObat+ttlRalan_Dokter+ttlRalan_Paramedis+
                                    ttlTambahan+ttlPotongan+ttlRegistrasi),""
                        });
                    } catch (Exception e) {
                        System.out.println("Notifikasi : "+e);
                    } finally{
                        if(rspasien!=null){
                            rspasien.close();
                        }
                        if(pspasienralan!=null){
                            pspasienralan.close();
                        }
                    } 
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(psjamshift!=null){
                    psjamshift.close();
                }
            }  
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        this.setCursor(Cursor.getDefaultCursor());
    }
    
    private void tampilranap() {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
        Valid.tabelKosong(tabModeRanap);
        try{
            psjamshift=koneksi.prepareStatement(sqlpsjamshift);
            try{
                psjamshift.setString(1,"%"+CmbStatus.getSelectedItem().toString().replaceAll("Semua","")+"%");
                rs=psjamshift.executeQuery();
                while(rs.next()){
                    tabModeRanap.addRow(new Object[]{
                        "Shift : "+rs.getString("shift"),rs.getString("jam_masuk")+" - "+rs.getString("jam_pulang"),"","","","","","","","","","","","","","","",""
                    });
                    pspasienranap=koneksi.prepareStatement(sqlpspasienranap);
                    try{
                        pspasienranap.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+rs.getString("jam_masuk"));
                        if(rs.getString("shift").equals("Malam")){
                            tanggal2=Sequel.cariIsi("select DATE_ADD('"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+rs.getString("jam_pulang")+"',INTERVAL 1 DAY)");
                            pspasienranap.setString(2,tanggal2);
                        }else{
                            pspasienranap.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+rs.getString("jam_pulang"));
                        }
                        
                        rspasien=pspasienranap.executeQuery();
                        all=0;ttlLaborat=0;ttlRadiologi=0;ttlOperasi=0;ttlObat=0;
                        ttlRanap_Dokter=0;ttlRanap_Paramedis=0;ttlRalan_Dokter=0;
                        ttlRalan_Paramedis=0;ttlTambahan=0;ttlPotongan=0;ttlKamar=0;ttlService=0;
                        ttlRegistrasi=0;ttlHarian=0;ttlRetur_Obat=0;ttlResep_Pulang=0;
                        i=1;
                        while(rspasien.next()){
                            Ralan_Dokter=0;Laborat=0;Radiologi=0;Obat=0;Ralan_Paramedis=0;Tambahan=0;
                            Potongan=0;Registrasi=0;Retur_Obat=0;Resep_Pulang=0;Harian=0;Kamar=0;Service=0;
                            Ralan_Dokter_Paramedis=0;Operasi=0;Ranap_Dokter=0;Ranap_Dokter_Paramedis=0;
                            Ranap_Paramedis=0;
                            psbilling=koneksi.prepareStatement(sqlpsbilling);
                            try{
                                psbilling.setString(1,rspasien.getString("no_rawat"));
                                rsbilling=psbilling.executeQuery(); 
                                while(rsbilling.next()){
                                    switch (rsbilling.getString("status")) {
                                        case "Laborat":                    
                                            ttlLaborat=ttlLaborat+rsbilling.getDouble("totalbiaya");
                                            Laborat=Laborat+rsbilling.getDouble("totalbiaya");
                                            break;
                                        case "Radiologi":                    
                                            ttlRadiologi=ttlRadiologi+rsbilling.getDouble("totalbiaya");
                                            Radiologi=Radiologi+rsbilling.getDouble("totalbiaya");
                                            break;
                                        case "Operasi":                    
                                            ttlOperasi=ttlOperasi+rsbilling.getDouble("totalbiaya");
                                            Operasi=Operasi+rsbilling.getDouble("totalbiaya");
                                            break;
                                        case "Obat":                    
                                            ttlObat=ttlObat+rsbilling.getDouble("totalbiaya");
                                            Obat=Obat+rsbilling.getDouble("totalbiaya");
                                            break;
                                        case "Ranap Dokter":
                                            ttlRanap_Dokter=ttlRanap_Dokter+rsbilling.getDouble("totalbiaya");
                                            Ranap_Dokter=Ranap_Dokter+rsbilling.getDouble("totalbiaya");
                                            break;
                                        case "Ranap Dokter Paramedis":                    
                                            ttlRanap_Dokter=ttlRanap_Dokter+rsbilling.getDouble("totalbiaya");
                                            Ranap_Dokter_Paramedis=Ranap_Dokter_Paramedis+rsbilling.getDouble("totalbiaya");
                                            break;
                                        case "Ranap Paramedis":                    
                                            ttlRanap_Paramedis=ttlRanap_Paramedis+rsbilling.getDouble("totalbiaya");
                                            Ranap_Paramedis=Ranap_Paramedis+rsbilling.getDouble("totalbiaya");
                                            break;
                                        case "Ralan Dokter":                    
                                            ttlRalan_Dokter=ttlRalan_Dokter+rsbilling.getDouble("totalbiaya");
                                            Ralan_Dokter=Ralan_Dokter+rsbilling.getDouble("totalbiaya");
                                            break;
                                        case "Ralan Dokter Paramedis":                    
                                            ttlRalan_Dokter=ttlRalan_Dokter+rsbilling.getDouble("totalbiaya");
                                            Ralan_Dokter_Paramedis=Ralan_Dokter_Paramedis+rsbilling.getDouble("totalbiaya");
                                            break;
                                        case "Ralan Paramedis":
                                            ttlRalan_Paramedis=ttlRalan_Paramedis+rsbilling.getDouble("totalbiaya");
                                            Ralan_Paramedis=Ralan_Paramedis+rsbilling.getDouble("totalbiaya");
                                            break;
                                        case "Tambahan":                    
                                            ttlTambahan=ttlTambahan+rsbilling.getDouble("totalbiaya");
                                            Tambahan=Tambahan+rsbilling.getDouble("totalbiaya");
                                            break;
                                        case "Potongan":                    
                                            ttlPotongan=ttlPotongan+rsbilling.getDouble("totalbiaya");
                                            Potongan=Potongan+rsbilling.getDouble("totalbiaya");
                                            break;
                                        case "Kamar":                    
                                            ttlKamar=ttlKamar+rsbilling.getDouble("totalbiaya");
                                            Kamar=Kamar+rsbilling.getDouble("totalbiaya");
                                            break;
                                        case "Registrasi":                    
                                            ttlRegistrasi=ttlRegistrasi+rsbilling.getDouble("totalbiaya");
                                            Registrasi=Registrasi+rsbilling.getDouble("totalbiaya");
                                            break;
                                        case "Harian":                    
                                            ttlHarian=ttlHarian+rsbilling.getDouble("totalbiaya");
                                            Harian=Harian+rsbilling.getDouble("totalbiaya");
                                            break;
                                        case "Retur Obat":                    
                                            ttlRetur_Obat=ttlRetur_Obat+rsbilling.getDouble("totalbiaya");
                                            Retur_Obat=Retur_Obat+rsbilling.getDouble("totalbiaya");
                                            break;
                                        case "Resep Pulang":                    
                                            ttlResep_Pulang=ttlResep_Pulang+rsbilling.getDouble("totalbiaya");
                                            Resep_Pulang=Resep_Pulang+rsbilling.getDouble("totalbiaya");
                                            break;
                                        case "Service":                    
                                            ttlService=ttlService+rsbilling.getDouble("totalbiaya");
                                            Service=Service+rsbilling.getDouble("totalbiaya");
                                            break;
                                    }                        
                                }
                                tabModeRanap.addRow(new Object[]{
                                    i+". "+rspasien.getString("tanggal")+" "+rspasien.getString("jam"),rspasien.getString("no_nota"),
                                    rspasien.getString("nm_pasien"),rspasien.getString("png_jawab"),
                                    Sequel.cariIsi("select perujuk from rujuk_masuk where no_rawat=?",rspasien.getString("no_rawat")),Valid.SetAngka(Registrasi),
                                    Valid.SetAngka(Ranap_Dokter+Ranap_Dokter_Paramedis+Ranap_Paramedis+Ralan_Dokter+Ralan_Dokter_Paramedis+Ralan_Paramedis),
                                    Valid.SetAngka(Obat),Valid.SetAngka(Retur_Obat),Valid.SetAngka(Resep_Pulang),Valid.SetAngka(Laborat),Valid.SetAngka(Radiologi),Valid.SetAngka(Potongan),
                                    Valid.SetAngka(Tambahan),Valid.SetAngka(Kamar+Service),Valid.SetAngka(Operasi),Valid.SetAngka(Harian),Valid.SetAngka(Laborat+Radiologi+Operasi+Obat+Ranap_Dokter+
                                            Ranap_Dokter_Paramedis+Ranap_Paramedis+Ralan_Dokter+Ralan_Dokter_Paramedis+Ralan_Paramedis+Tambahan+Potongan+Kamar+Registrasi+Harian+Retur_Obat+Resep_Pulang+Service)
                                });
                                all=all+Laborat+Radiologi+Operasi+Obat+Ranap_Dokter+Ranap_Dokter_Paramedis+Ranap_Paramedis+Ralan_Dokter+Ralan_Dokter_Paramedis+Ralan_Paramedis+Tambahan+Potongan+Kamar+Registrasi+Harian+Retur_Obat+Resep_Pulang+Service;
                                i++;
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rsbilling!=null){
                                    rsbilling.close();
                                }
                                if(psbilling!=null){
                                    psbilling.close();
                                }
                            } 
                        }
                        tabModeRanap.addRow(new Object[]{
                            "   >> Total ",":","","","",Valid.SetAngka(ttlRegistrasi),Valid.SetAngka(ttlRanap_Dokter+ttlRanap_Paramedis+ttlRalan_Dokter+ttlRalan_Paramedis),
                            Valid.SetAngka(ttlObat),Valid.SetAngka(ttlRetur_Obat),Valid.SetAngka(ttlResep_Pulang),Valid.SetAngka(ttlLaborat),Valid.SetAngka(ttlRadiologi),Valid.SetAngka(ttlPotongan),
                            Valid.SetAngka(ttlTambahan),Valid.SetAngka(ttlKamar+ttlService),Valid.SetAngka(ttlOperasi),Valid.SetAngka(ttlHarian),Valid.SetAngka(all)
                        });
                    } catch (Exception e) {
                        System.out.println("Notifikasi : "+e);
                    } finally{
                        if(rspasien!=null){
                            rspasien.close();
                        }
                        if(pspasienranap!=null){
                            pspasienranap.close();
                        }
                    }  
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(psjamshift!=null){
                    psjamshift.close();
                }
            } 
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        this.setCursor(Cursor.getDefaultCursor());        
    }
    
    private void tampilpemasukan() {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
        Valid.tabelKosong(tabModePemasukan);
        try{
            psjamshift=koneksi.prepareStatement(sqlpsjamshift);
            try{
                psjamshift.setString(1,"%"+CmbStatus.getSelectedItem().toString().replaceAll("Semua","")+"%");
                rs=psjamshift.executeQuery();
                while(rs.next()){
                    tabModePemasukan.addRow(new Object[]{
                        "Shift : "+rs.getString("shift"),rs.getString("jam_masuk")+" - "+rs.getString("jam_pulang"),"",""
                    });
                    pspemasukan=koneksi.prepareStatement(sqlpspemasukan);
                    try{
                        pspemasukan.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+rs.getString("jam_masuk"));
                        if(rs.getString("shift").equals("Malam")){
                            tanggal2=Sequel.cariIsi("select DATE_ADD('"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+rs.getString("jam_pulang")+"',INTERVAL 1 DAY)");
                            pspemasukan.setString(2,tanggal2);
                        }else{
                            pspemasukan.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+rs.getString("jam_pulang"));
                        }
                        
                        rsbilling=pspemasukan.executeQuery();
                        i=1;
                        all=0;
                        while(rsbilling.next()){
                            all=all+rsbilling.getDouble("besar");
                            tabModePemasukan.addRow(new Object[]{
                                i+". "+rsbilling.getString("tanggal"),rsbilling.getString("nama_kategori"),
                                Valid.SetAngka(rsbilling.getDouble("besar")),rsbilling.getString("keterangan")
                            });
                            i++;
                        }
                        tabModePemasukan.addRow(new Object[]{
                            "   >> Total ",":",Valid.SetAngka(all),""
                        }); 
                    } catch (Exception e) {
                        System.out.println("Notifikasi : "+e);
                    } finally{
                        if(rsbilling!=null){
                            rsbilling.close();
                        }
                        if(pspemasukan!=null){
                            pspemasukan.close();
                        }
                    }  
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(psjamshift!=null){
                    psjamshift.close();
                }
            } 
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        this.setCursor(Cursor.getDefaultCursor());        
    }   
    
    private void tampilpengeluaran() {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
        Valid.tabelKosong(tabModePengeluaran);
        try{
            psjamshift=koneksi.prepareStatement(sqlpsjamshift);
            try{
                psjamshift.setString(1,"%"+CmbStatus.getSelectedItem().toString().replaceAll("Semua","")+"%");
                rs=psjamshift.executeQuery();
                while(rs.next()){
                    tabModePengeluaran.addRow(new Object[]{
                        "Shift : "+rs.getString("shift"),rs.getString("jam_masuk")+" - "+rs.getString("jam_pulang"),"",""
                    });
                    pspengeluaran=koneksi.prepareStatement(sqlpspengeluaran);
                    try{
                        pspengeluaran.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+rs.getString("jam_masuk"));
                        if(rs.getString("shift").equals("Malam")){
                            tanggal2=Sequel.cariIsi("select DATE_ADD('"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+rs.getString("jam_pulang")+"',INTERVAL 1 DAY)");
                            pspengeluaran.setString(2,tanggal2);
                        }else{
                            pspengeluaran.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+rs.getString("jam_pulang"));
                        }
                        
                        rsbilling=pspengeluaran.executeQuery();
                        i=1;
                        all=0;
                        while(rsbilling.next()){
                            all=all+rsbilling.getDouble("biaya");
                            tabModePengeluaran.addRow(new Object[]{
                                i+". "+rsbilling.getString("tanggal"),rsbilling.getString("nama_kategori"),
                                Valid.SetAngka(rsbilling.getDouble("biaya")),rsbilling.getString("keterangan")
                            });
                            i++;
                        }
                        tabModePengeluaran.addRow(new Object[]{
                            "   >> Total ",":",Valid.SetAngka(all),""
                        });
                    } catch (Exception e) {
                        System.out.println("Notifikasi : "+e);
                    } finally{
                        if(rsbilling!=null){
                            rsbilling.close();
                        }
                        if(pspengeluaran!=null){
                            pspengeluaran.close();
                        }
                    } 
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(psjamshift!=null){
                    psjamshift.close();
                }
            } 
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        this.setCursor(Cursor.getDefaultCursor());        
    }

}

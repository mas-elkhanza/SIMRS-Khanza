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

package inventory;

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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author dosen
 */
public final class DlgCariObat3 extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private final sekuel Sequel=new sekuel();
    private final validasi Valid=new validasi();
    private final Connection koneksi=koneksiDB.condb();
    private riwayatobat Trackobat=new riwayatobat();
    private int i=0,jml=0;
    private ResultSet rstampilbarang,rsstokmasuk,rspemberian,rskeluar,rsretur,rscariharga,rspasien;
    private PreparedStatement pstampilbarang,psstokmasuk,pspemberian,pskeluar,psretur,psimpanretur,pscariharga,
                              pshapusobat,pshapusretur,psobatsimpan,psupdategudang,psupdategudang2,pspasien;
    private double stokmasuk=0,pagi=0,siang=0,sore=0,malam=0,keluar=0,retur=0,harga=0,kapasitas=0,
                    kenaikan=0,returshs=0,hilang=0,beli=0,embalase=Sequel.cariIsiAngka("select embalase_per_obat from set_embalase"),
                    tuslah=Sequel.cariIsiAngka("select tuslah_per_obat from set_embalase");
    private String aktifkanbatch="no",hppfarmasi="";
    /** Creates new form DlgPenyakit
     * @param parent
     * @param modal */
    public DlgCariObat3(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(10,2);
        setSize(856,350);
        Object[] row={"K","Kode Barang","Nama Barang","Msk","Pg","Sg","Sr","Ml","Ttl.Msk","Ttl.Klr","Retur","Rtr.Sh","Ttl.Hlg","No.Batch","No.Faktur","Aturan Pakai"};
        tabMode=new DefaultTableModel(null,row){
            @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = true;
                if ((colIndex==1)||(colIndex==2)||(colIndex==3)||(colIndex==8)||(colIndex==9)||(colIndex==11)||(colIndex==12)||(colIndex==13)||(colIndex==14)||(colIndex==15)) {
                    a=false;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Double.class, java.lang.Double.class,
                java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class,
                java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Object.class, java.lang.Object.class, 
                java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbObat.setModel(tabMode);
        //tbPenyakit.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbPenyakit.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (i = 0; i < 16; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(90);
            }else if(i==2){
                column.setPreferredWidth(200);
            }else if(i==3){
                column.setPreferredWidth(35);
            }else if(i==4){
                column.setPreferredWidth(30);
            }else if(i==5){
                column.setPreferredWidth(30);
            }else if(i==6){
                column.setPreferredWidth(30);
            }else if(i==7){
                column.setPreferredWidth(30);
            }else if(i==8){
                column.setPreferredWidth(40);
            }else if(i==9){
                column.setPreferredWidth(40);
            }else if(i==10){
                column.setPreferredWidth(40);
            }else if(i==11){
                column.setPreferredWidth(40);
            }else if(i==12){
                column.setPreferredWidth(40);
            }else if(i==13){
                column.setPreferredWidth(70);
            }else if(i==14){
                column.setPreferredWidth(100);
            }else if(i==15){
                column.setPreferredWidth(120);
            }     
        }
        
        tbObat.setDefaultRenderer(Object.class, new WarnaTable()); 
        
        try {
            aktifkanbatch = koneksiDB.AKTIFKANBATCHOBAT();
        } catch (Exception e) {
            System.out.println("E : "+e);
            aktifkanbatch = "no";
        }
        
        try {
            hppfarmasi=koneksiDB.HPPFARMASI();
        } catch (Exception e) {
            hppfarmasi="dasar";
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

        TNoRw = new widget.TextBox();
        Kd2 = new widget.TextBox();
        KdPj = new widget.TextBox();
        kelas = new widget.TextBox();
        kdgudang = new widget.TextBox();
        TNoRM = new widget.TextBox();
        TPasien = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbObat = new widget.Table();
        panelisi3 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        btnCetak = new widget.Button();
        BtnKeluar = new widget.Button();
        FormInput = new widget.PanelBiasa();
        jLabel5 = new widget.Label();
        Tanggal = new widget.Tanggal();
        label12 = new widget.Label();
        Jeniskelas = new widget.ComboBox();
        BtnCari = new widget.Button();

        TNoRw.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N

        Kd2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Kd2.setHighlighter(null);
        Kd2.setName("Kd2"); // NOI18N
        Kd2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Kd2KeyPressed(evt);
            }
        });

        KdPj.setHighlighter(null);
        KdPj.setName("KdPj"); // NOI18N
        KdPj.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdPjKeyPressed(evt);
            }
        });

        kelas.setHighlighter(null);
        kelas.setName("kelas"); // NOI18N
        kelas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kelasKeyPressed(evt);
            }
        });

        kdgudang.setEditable(false);
        kdgudang.setName("kdgudang"); // NOI18N
        kdgudang.setPreferredSize(new java.awt.Dimension(80, 23));

        TNoRM.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N

        TPasien.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Obat, Alkes & BHP Medis ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbObat.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbObat.setName("tbObat"); // NOI18N
        tbObat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbObatMouseClicked(evt);
            }
        });
        tbObat.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tbObatPropertyChange(evt);
            }
        });
        tbObat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbObatKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbObat);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(100, 56));
        panelisi3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

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
        panelisi3.add(BtnSimpan);

        btnCetak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png"))); // NOI18N
        btnCetak.setMnemonic('T');
        btnCetak.setText("Cetak");
        btnCetak.setToolTipText("Alt+T");
        btnCetak.setName("btnCetak"); // NOI18N
        btnCetak.setPreferredSize(new java.awt.Dimension(100, 30));
        btnCetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCetakActionPerformed(evt);
            }
        });
        panelisi3.add(btnCetak);

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
        panelisi3.add(BtnKeluar);

        internalFrame1.add(panelisi3, java.awt.BorderLayout.PAGE_END);

        FormInput.setBackground(new java.awt.Color(215, 225, 215));
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(100, 43));
        FormInput.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 3, 9));

        jLabel5.setText("Tanggal :");
        jLabel5.setName("jLabel5"); // NOI18N
        jLabel5.setPreferredSize(new java.awt.Dimension(60, 23));
        FormInput.add(jLabel5);

        Tanggal.setForeground(new java.awt.Color(50, 70, 50));
        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "13-09-2020" }));
        Tanggal.setDisplayFormat("dd-MM-yyyy");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.setOpaque(false);
        Tanggal.setPreferredSize(new java.awt.Dimension(100, 23));
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeyPressed(evt);
            }
        });
        FormInput.add(Tanggal);

        label12.setText("Tarif :");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(50, 23));
        FormInput.add(label12);

        Jeniskelas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Kelas 1", "Kelas 2", "Kelas 3", "Utama/BPJS", "VIP", "VVIP", "Beli Luar", "Karyawan" }));
        Jeniskelas.setName("Jeniskelas"); // NOI18N
        Jeniskelas.setPreferredSize(new java.awt.Dimension(120, 23));
        FormInput.add(Jeniskelas);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('1');
        BtnCari.setToolTipText("Alt+1");
        BtnCari.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        BtnCari.setName("BtnCari"); // NOI18N
        BtnCari.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariActionPerformed(evt);
            }
        });
        FormInput.add(BtnCari);

        internalFrame1.add(FormInput, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
       dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TNoRw.getText().trim().equals("")){
            Valid.textKosong(Jeniskelas,"Data");
        }else if(kdgudang.getText().equals("")){
            Valid.textKosong(Jeniskelas,"Lokasi");
        }else{
            int reply = JOptionPane.showConfirmDialog(rootPane,"Eeiiiiiits, udah bener belum data yang mau disimpan..??","Konfirmasi",JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
                try {  
                    koneksi.setAutoCommit(false); 
                    jml=tbObat.getRowCount();
                    for(i=0;i<jml;i++){   
                        harga=0;
                        kapasitas=1;
                        beli=0;
                        pscariharga=koneksi.prepareStatement("select databarang.kelas1,databarang.kelas2,databarang.kelas3,"+
                            "databarang.utama,databarang.vip,databarang.vvip,databarang.beliluar,databarang.karyawan,databarang.h_beli,"+
                            "IFNULL(kapasitas,0) as kapasitas,databarang."+hppfarmasi+" as dasar from databarang "+
                            "where databarang.kode_brng=?");
                        try {
                            pscariharga.setString(1,tbObat.getValueAt(i,1).toString());
                            rscariharga=pscariharga.executeQuery();
                            while(rscariharga.next()){
                                beli=rscariharga.getDouble("dasar");
                                if(kenaikan>0){
                                    harga=Valid.roundUp(rscariharga.getDouble("h_beli")+(rscariharga.getDouble("h_beli")*kenaikan),100);
                                }else{
                                    if(Jeniskelas.getSelectedItem().equals("Kelas 1")){
                                        harga=Valid.roundUp(rscariharga.getDouble("kelas1"),100);
                                    }else if(Jeniskelas.getSelectedItem().equals("Kelas 2")){
                                        harga=Valid.roundUp(rscariharga.getDouble("kelas2"),100);
                                    }else if(Jeniskelas.getSelectedItem().equals("Kelas 3")){
                                        harga=Valid.roundUp(rscariharga.getDouble("kelas3"),100);
                                    }else if(Jeniskelas.getSelectedItem().equals("Utama/BPJS")){
                                        harga=Valid.roundUp(rscariharga.getDouble("utama"),100);
                                    }else if(Jeniskelas.getSelectedItem().equals("VIP")){
                                        harga=Valid.roundUp(rscariharga.getDouble("vip"),100);
                                    }else if(Jeniskelas.getSelectedItem().equals("VVIP")){
                                        harga=Valid.roundUp(rscariharga.getDouble("vvip"),100);
                                    }else if(Jeniskelas.getSelectedItem().equals("Beli Luar")){  
                                        harga=Valid.roundUp(rscariharga.getDouble("beliluar"),100);
                                    }else if(Jeniskelas.getSelectedItem().equals("Karyawan")){  
                                        harga=Valid.roundUp(rscariharga.getDouble("karyawan"),100);
                                    }
                                }

                                if(rscariharga.getDouble("kapasitas")>0){
                                   kapasitas=rscariharga.getDouble("kapasitas");                            
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("Notofikasi : "+e);
                        } finally{
                            if(rscariharga != null){
                                rscariharga.close();
                            }
                            if(pscariharga != null){
                                pscariharga.close();
                            }
                        }                    

                        pagi=0;
                        try {
                            pagi=Double.parseDouble(tbObat.getValueAt(i,4).toString()); 
                        } catch (Exception e) {
                            pagi=0;
                        }
                        siang=0;
                        try {
                            siang=Double.parseDouble(tbObat.getValueAt(i,5).toString()); 
                        } catch (Exception e) {
                            siang=0;
                        }
                        sore=0;
                        try {
                            sore=Double.parseDouble(tbObat.getValueAt(i,6).toString()); 
                        } catch (Exception e) {
                            sore=0;
                        }
                        malam=0;
                        try {
                            malam=Double.parseDouble(tbObat.getValueAt(i,7).toString()); 
                        } catch (Exception e) {
                            malam=0;
                        }
                        retur=0;
                        try {
                            retur=Double.parseDouble(tbObat.getValueAt(i,10).toString()); 
                        } catch (Exception e) {
                            retur=0;
                        } 

                        pshapusobat=koneksi.prepareStatement(
                            "delete from detail_pemberian_obat where detail_pemberian_obat.status='Ranap' and detail_pemberian_obat.no_rawat=? and "+
                            "detail_pemberian_obat.tgl_perawatan=? and detail_pemberian_obat.kode_brng=? and detail_pemberian_obat.no_batch=? and "+
                            "detail_pemberian_obat.no_faktur=?");
                        try {
                            pshapusobat.setString(1,TNoRw.getText());
                            pshapusobat.setString(2,Valid.SetTgl(Tanggal.getSelectedItem()+""));
                            pshapusobat.setString(3,tbObat.getValueAt(i,1).toString());
                            pshapusobat.setString(4,tbObat.getValueAt(i,13).toString());
                            pshapusobat.setString(5,tbObat.getValueAt(i,14).toString());
                            pshapusobat.executeUpdate();
                        } catch (Exception e) {
                            System.out.println("Notofikasi : "+e);
                        } finally{
                            if(pshapusobat != null){
                                pshapusobat.close();
                            }
                        }

                        if(retur>0){
                            psretur=koneksi.prepareStatement(
                                "select sum(returpasien.jml) as jml from returpasien where returpasien.no_rawat=? and "+
                                "returpasien.kode_brng=? and returpasien.no_batch=? and returpasien.no_faktur=?");
                            try {
                                psretur.setString(1,TNoRw.getText());
                                psretur.setString(2,tbObat.getValueAt(i,1).toString());
                                psretur.setString(3,tbObat.getValueAt(i,13).toString());
                                psretur.setString(4,tbObat.getValueAt(i,14).toString());
                                rsretur=psretur.executeQuery();
                                if(rsretur.next()){
                                    if(aktifkanbatch.equals("yes")){
                                        Trackobat.catatRiwayat(tbObat.getValueAt(i,1).toString(),0,rsretur.getDouble("jml"),"Retur Pasien",akses.getkode(),kdgudang.getText(),"Hapus",tbObat.getValueAt(i,13).toString(),tbObat.getValueAt(i,14).toString(),TNoRw.getText()+" "+TNoRM.getText()+" "+TPasien.getText());
                                        Sequel.mengedit("data_batch","no_batch=? and no_faktur=? and kode_brng=?","sisa=sisa-?",4,new String[]{
                                            ""+rsretur.getDouble("jml"),tbObat.getValueAt(i,13).toString(),tbObat.getValueAt(i,14).toString(),tbObat.getValueAt(i,1).toString()
                                        });
                                        psupdategudang= koneksi.prepareStatement("update gudangbarang set stok=stok-? where kode_brng=? and kd_bangsal=? and no_batch=? and no_faktur=?");           
                                        try {
                                            psupdategudang.setDouble(1,rsretur.getDouble("jml"));
                                            psupdategudang.setString(2,tbObat.getValueAt(i,1).toString());
                                            psupdategudang.setString(3,kdgudang.getText());
                                            psupdategudang.setString(4,tbObat.getValueAt(i,13).toString());
                                            psupdategudang.setString(5,tbObat.getValueAt(i,14).toString());
                                            psupdategudang.executeUpdate(); 
                                        } catch (Exception e) {
                                            System.out.println("Notofikasi : "+e);
                                        } finally{
                                            if(psupdategudang != null){
                                                psupdategudang.close();
                                            }
                                        }
                                    }else{
                                        Trackobat.catatRiwayat(tbObat.getValueAt(i,1).toString(),0,rsretur.getDouble("jml"),"Retur Pasien",akses.getkode(),kdgudang.getText(),"Hapus","","",TNoRw.getText()+" "+TNoRM.getText()+" "+TPasien.getText());
                                        psupdategudang= koneksi.prepareStatement("update gudangbarang set stok=stok-? where kode_brng=? and kd_bangsal=? and no_batch='' and no_faktur=''");           
                                        try {
                                            psupdategudang.setDouble(1,rsretur.getDouble("jml"));
                                            psupdategudang.setString(2,tbObat.getValueAt(i,1).toString());
                                            psupdategudang.setString(3,kdgudang.getText());
                                            psupdategudang.executeUpdate(); 
                                        } catch (Exception e) {
                                            System.out.println("Notofikasi : "+e);
                                        } finally{
                                            if(psupdategudang != null){
                                                psupdategudang.close();
                                            }
                                        }
                                    }
                                }  
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rsretur != null){
                                    rsretur.close();
                                }
                                if(psretur != null){
                                    psretur.close();
                                }
                            }

                            pshapusretur=koneksi.prepareStatement(
                                "delete from returpasien where returpasien.no_rawat=? and returpasien.kode_brng=? and returpasien.no_batch=? and returpasien.no_faktur=?");
                            try {
                                pshapusretur.setString(1,TNoRw.getText());
                                pshapusretur.setString(2,tbObat.getValueAt(i,1).toString());
                                pshapusretur.setString(3,tbObat.getValueAt(i,13).toString());
                                pshapusretur.setString(4,tbObat.getValueAt(i,14).toString());
                                pshapusretur.executeUpdate();  
                            } catch (Exception e) {
                                System.out.println("Notofikasi : "+e);
                            } finally{
                                if(pshapusretur != null){
                                    pshapusretur.close();
                                }
                            }

                            psimpanretur= koneksi.prepareStatement("insert into returpasien values(?,?,?,?,?,?)");
                            try {
                                psimpanretur.setString(1,Valid.SetTgl(Tanggal.getSelectedItem()+""));
                                psimpanretur.setString(2,TNoRw.getText());
                                psimpanretur.setString(3,tbObat.getValueAt(i,1).toString());
                                psimpanretur.setDouble(4,retur);
                                psimpanretur.setString(5,tbObat.getValueAt(i,13).toString());
                                psimpanretur.setString(6,tbObat.getValueAt(i,14).toString());
                                psimpanretur.executeUpdate();

                                if(aktifkanbatch.equals("yes")){
                                    Trackobat.catatRiwayat(tbObat.getValueAt(i,1).toString(),retur,0,"Retur Pasien",akses.getkode(),kdgudang.getText(),"Simpan",tbObat.getValueAt(i,13).toString(),tbObat.getValueAt(i,14).toString(),TNoRw.getText()+" "+TNoRM.getText()+" "+TPasien.getText());
                                    Sequel.mengedit("data_batch","no_batch=? and no_faktur=? and kode_brng=?","sisa=sisa+?",4,new String[]{
                                        ""+retur,tbObat.getValueAt(i,13).toString(),tbObat.getValueAt(i,14).toString(),tbObat.getValueAt(i,1).toString()
                                    });
                                    psupdategudang2= koneksi.prepareStatement("update gudangbarang set stok=stok+? where kode_brng=? and kd_bangsal=? and no_batch=? and no_faktur=?");
                                    try {
                                        psupdategudang2.setDouble(1,retur);
                                        psupdategudang2.setString(2,tbObat.getValueAt(i,1).toString());
                                        psupdategudang2.setString(3,kdgudang.getText());
                                        psupdategudang2.setString(4,tbObat.getValueAt(i,13).toString());
                                        psupdategudang2.setString(5,tbObat.getValueAt(i,14).toString());
                                        psupdategudang2.executeUpdate();
                                    } catch (Exception e) {
                                        System.out.println("Notofikasi : "+e);
                                    } finally{
                                        if(psupdategudang2 != null){
                                            psupdategudang2.close();
                                        }
                                    }
                                }else{
                                    Trackobat.catatRiwayat(tbObat.getValueAt(i,1).toString(),retur,0,"Retur Pasien",akses.getkode(),kdgudang.getText(),"Simpan","","",TNoRw.getText()+" "+TNoRM.getText()+" "+TPasien.getText());
                                    psupdategudang2= koneksi.prepareStatement("update gudangbarang set stok=stok+? where kode_brng=? and kd_bangsal=? and no_batch='' and no_faktur=''");
                                    try {
                                        psupdategudang2.setDouble(1,retur);
                                        psupdategudang2.setString(2,tbObat.getValueAt(i,1).toString());
                                        psupdategudang2.setString(3,kdgudang.getText());
                                        psupdategudang2.executeUpdate();
                                    } catch (Exception e) {
                                        System.out.println("Notofikasi : "+e);
                                    } finally{
                                        if(psupdategudang2 != null){
                                            psupdategudang2.close();
                                        }
                                    }
                                }
                            } catch (Exception e) {
                                System.out.println("Notofikasi : "+e);
                            } finally{
                                if(psimpanretur != null){
                                    psimpanretur.close();
                                }
                            }                        

                        }                  

                        psobatsimpan= koneksi.prepareStatement("insert into detail_pemberian_obat values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                        try {
                            if(pagi>0){
                                psobatsimpan.setString(1,Valid.SetTgl(Tanggal.getSelectedItem()+""));
                                psobatsimpan.setString(2,"07:00:00");
                                psobatsimpan.setString(3,TNoRw.getText());
                                psobatsimpan.setString(4,tbObat.getValueAt(i,1).toString());
                                psobatsimpan.setDouble(5,beli);
                                psobatsimpan.setDouble(6,harga);
                                if(tbObat.getValueAt(i,0).toString().equals("true")){                                
                                    psobatsimpan.setDouble(7,(pagi/kapasitas));
                                    psobatsimpan.setDouble(10,(harga*(pagi/kapasitas)));  
                                }else{                                
                                    psobatsimpan.setDouble(7,pagi);
                                    psobatsimpan.setDouble(10,(harga*pagi));
                                }
                                psobatsimpan.setString(8,"0");
                                psobatsimpan.setString(9,"0");
                                psobatsimpan.setString(11,"Ranap");
                                psobatsimpan.setString(12,kdgudang.getText());
                                psobatsimpan.setString(13,tbObat.getValueAt(i,13).toString());
                                psobatsimpan.setString(14,tbObat.getValueAt(i,14).toString());
                                psobatsimpan.executeUpdate();  
                            }

                            if(siang>0){
                                psobatsimpan.setString(1,Valid.SetTgl(Tanggal.getSelectedItem()+""));
                                psobatsimpan.setString(2,"12:00:00");
                                psobatsimpan.setString(3,TNoRw.getText());
                                psobatsimpan.setString(4,tbObat.getValueAt(i,1).toString());
                                psobatsimpan.setDouble(5,beli);
                                psobatsimpan.setDouble(6,harga);
                                if(tbObat.getValueAt(i,0).toString().equals("true")){                                
                                    psobatsimpan.setDouble(7,(siang/kapasitas));
                                    psobatsimpan.setDouble(10,(harga*(siang/kapasitas)));    
                                }else{                                
                                    psobatsimpan.setDouble(7,siang);
                                    psobatsimpan.setDouble(10,(harga*siang));
                                }
                                psobatsimpan.setString(8,"0");
                                psobatsimpan.setString(9,"0");
                                psobatsimpan.setString(11,"Ranap");
                                psobatsimpan.setString(12,kdgudang.getText());
                                psobatsimpan.setString(13,tbObat.getValueAt(i,13).toString());
                                psobatsimpan.setString(14,tbObat.getValueAt(i,14).toString());
                                psobatsimpan.executeUpdate();  
                            }

                            if(sore>0){
                                psobatsimpan.setString(1,Valid.SetTgl(Tanggal.getSelectedItem()+""));
                                psobatsimpan.setString(2,"16:00:00");
                                psobatsimpan.setString(3,TNoRw.getText());
                                psobatsimpan.setString(4,tbObat.getValueAt(i,1).toString());
                                psobatsimpan.setDouble(5,beli);
                                psobatsimpan.setDouble(6,harga);
                                if(tbObat.getValueAt(i,0).toString().equals("true")){                                
                                    psobatsimpan.setDouble(7,(sore/kapasitas));
                                    psobatsimpan.setDouble(10,(harga*(sore/kapasitas)));    
                                }else{                                
                                    psobatsimpan.setDouble(7,sore);
                                    psobatsimpan.setDouble(10,(harga*sore));
                                }
                                psobatsimpan.setString(8,"0");
                                psobatsimpan.setString(9,"0");
                                psobatsimpan.setString(11,"Ranap");
                                psobatsimpan.setString(12,kdgudang.getText());
                                psobatsimpan.setString(13,tbObat.getValueAt(i,13).toString());
                                psobatsimpan.setString(14,tbObat.getValueAt(i,14).toString());
                                psobatsimpan.executeUpdate();  
                            }

                            if(malam>0){
                                psobatsimpan.setString(1,Valid.SetTgl(Tanggal.getSelectedItem()+""));
                                psobatsimpan.setString(2,"20:00:00");
                                psobatsimpan.setString(3,TNoRw.getText());
                                psobatsimpan.setString(4,tbObat.getValueAt(i,1).toString());
                                psobatsimpan.setDouble(5,beli);
                                psobatsimpan.setDouble(6,harga);
                                if(tbObat.getValueAt(i,0).toString().equals("true")){                                
                                    psobatsimpan.setDouble(7,(malam/kapasitas));
                                    psobatsimpan.setDouble(10,(harga*(malam/kapasitas)));  
                                }else{                                
                                    psobatsimpan.setDouble(7,malam);
                                    psobatsimpan.setDouble(10,(harga*malam));
                                }
                                psobatsimpan.setString(8,"0");
                                psobatsimpan.setString(9,"0");
                                psobatsimpan.setString(11,"Ranap");
                                psobatsimpan.setString(12,kdgudang.getText());
                                psobatsimpan.setString(13,tbObat.getValueAt(i,13).toString());
                                psobatsimpan.setString(14,tbObat.getValueAt(i,14).toString());
                                psobatsimpan.executeUpdate();  
                            }
                        } catch (Exception e) {
                            System.out.println("Notofikasi : "+e);
                        } finally{
                            if(psobatsimpan != null){
                                psobatsimpan.close();
                            }
                        }                     
                    }    
                    koneksi.setAutoCommit(true);
                    tampil(); 
                }catch(Exception e){
                    System.out.println(e);
                }
            }                
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

private void TanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKeyPressed
    if(evt.getKeyCode()==KeyEvent.VK_DOWN){
        tbObat.requestFocus();
    }else{
        Valid.pindah(evt,BtnKeluar,btnCetak);
    }
}//GEN-LAST:event_TanggalKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        tampil();
    }//GEN-LAST:event_BtnCariActionPerformed

    private void btnCetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCetakActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(TNoRw.getText().equals("")){
            Valid.textKosong(TNoRw,"Pasien");
        }else if(tbObat.getRowCount()==0){
            Valid.textKosong(Tanggal,"Data Obat");
        }else{
            try {
                pspasien=koneksi.prepareStatement(
                        "select pasien.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.umur from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                                "where reg_periksa.no_rawat=?");
                pspasien.setString(1,TNoRw.getText());
                rspasien=pspasien.executeQuery();
                while(rspasien.next()){
                    Sequel.queryu("truncate table temporary");
                    for(i=0;i<tbObat.getRowCount();i++){
                        Sequel.menyimpan("temporary","'0','"+
                                tbObat.getValueAt(i,2).toString()+"','"+
                                tbObat.getValueAt(i,3).toString()+"','"+
                                tbObat.getValueAt(i,4).toString()+"','"+
                                tbObat.getValueAt(i,5).toString()+"','"+
                                tbObat.getValueAt(i,6).toString()+"','"+
                                tbObat.getValueAt(i,7).toString()+"','"+
                                tbObat.getValueAt(i,8).toString()+"','"+
                                tbObat.getValueAt(i,9).toString()+"','"+
                                tbObat.getValueAt(i,10).toString()+"','"+
                                tbObat.getValueAt(i,11).toString()+"','"+
                                tbObat.getValueAt(i,12).toString()+"','','','','','','','','','','','','','','','','','','','','','','','','','',''","Data User");
                    }
                    Map<String, Object> param = new HashMap<>();
                    param.put("norm",rspasien.getString("no_rkm_medis"));
                    param.put("namapasien",rspasien.getString("nm_pasien"));
                    param.put("jkel",rspasien.getString("jk"));
                    param.put("umur",rspasien.getString("umur"));
                    param.put("tanggal",Valid.SetTgl(Tanggal.getSelectedItem()+""));param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());   
                    param.put("logo",Sequel.cariGambar("select logo from setting")); 
                    Valid.MyReport("rptObatPasien.jasper","report","::[ Obat Keluar Masuk ]::",param);
                }                
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnCetakActionPerformed

    private void Kd2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kd2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Kd2KeyPressed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        tbObat.requestFocus();
    }//GEN-LAST:event_formWindowActivated

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void tbObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbObatKeyPressed
        if(tbObat.getRowCount()!=0){
            if(evt.getKeyCode()==KeyEvent.VK_DELETE){
                if(tbObat.getSelectedRow()!= -1){
                    if((tbObat.getSelectedColumn()==4)||(tbObat.getSelectedColumn()==5)||(tbObat.getSelectedColumn()==6)||(tbObat.getSelectedColumn()==7)||(tbObat.getSelectedColumn()==10)){
                        try {
                            tbObat.setValueAt(null,tbObat.getSelectedRow(),tbObat.getSelectedColumn());
                        } catch (Exception e) {
                        }
                    }
                }
            }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
                if(tbObat.getSelectedColumn()==13){
                    try {
                        if(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString().equals("0")||tbObat.getValueAt(tbObat.getSelectedRow(),13).toString().equals("")||tbObat.getValueAt(tbObat.getSelectedRow(),13).toString().equals("0.0")||tbObat.getValueAt(tbObat.getSelectedRow(),13).toString().equals("0,0")) {
                            tbObat.setValueAt(embalase,tbObat.getSelectedRow(),13);
                        }
                    } catch (Exception e) {
                        tbObat.setValueAt(0,tbObat.getSelectedRow(),13);
                    }
                }else if(tbObat.getSelectedColumn()==14){
                    try {
                        if(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString().equals("0")||tbObat.getValueAt(tbObat.getSelectedRow(),14).toString().equals("")||tbObat.getValueAt(tbObat.getSelectedRow(),14).toString().equals("0.0")||tbObat.getValueAt(tbObat.getSelectedRow(),14).toString().equals("0,0")) {
                            tbObat.setValueAt(tuslah,tbObat.getSelectedRow(),14);
                        }
                    } catch (Exception e) {
                        tbObat.setValueAt(0,tbObat.getSelectedRow(),14);
                    }
                }
            }
        }
    }//GEN-LAST:event_tbObatKeyPressed

    private void tbObatPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tbObatPropertyChange
        if(this.isVisible()==true){
              getData();
        }
    }//GEN-LAST:event_tbObatPropertyChange

    private void tbObatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbObatMouseClicked
        if(tbObat.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbObatMouseClicked

    private void KdPjKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdPjKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KdPjKeyPressed

    private void kelasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kelasKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_kelasKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgCariObat3 dialog = new DlgCariObat3(new javax.swing.JFrame(), true);
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
    private widget.Button BtnCari;
    private widget.Button BtnKeluar;
    private widget.Button BtnSimpan;
    private widget.PanelBiasa FormInput;
    private widget.ComboBox Jeniskelas;
    private widget.TextBox Kd2;
    private widget.TextBox KdPj;
    private widget.ScrollPane Scroll;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.Tanggal Tanggal;
    private widget.Button btnCetak;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel5;
    private widget.TextBox kdgudang;
    private widget.TextBox kelas;
    private widget.Label label12;
    private widget.panelisi panelisi3;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    
    public void tampil() {         
        try {             
            Valid.tabelKosong(tabMode);
            pstampilbarang=koneksi.prepareStatement(
                    "select stok_obat_pasien.kode_brng,databarang.nama_brng,sum(stok_obat_pasien.jumlah) as jumlah, "+
                    "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai from stok_obat_pasien inner join databarang "+
                    "on databarang.kode_brng=stok_obat_pasien.kode_brng where stok_obat_pasien.no_rawat=? "+
                    "group by stok_obat_pasien.kode_brng,stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur order by databarang.nama_brng");
            try {
                pstampilbarang.setString(1,TNoRw.getText());
                rstampilbarang=pstampilbarang.executeQuery();
                while(rstampilbarang.next()){
                    stokmasuk=0;
                    psstokmasuk=koneksi.prepareStatement(
                        "select sum(stok_obat_pasien.jumlah) as jumlah from stok_obat_pasien where "+
                        "stok_obat_pasien.no_rawat=? and stok_obat_pasien.tanggal=? and stok_obat_pasien.kode_brng=? "+
                        "and stok_obat_pasien.no_batch=? and stok_obat_pasien.no_faktur=?");
                    try {
                        psstokmasuk.setString(1,TNoRw.getText());
                        psstokmasuk.setString(2,Valid.SetTgl(Tanggal.getSelectedItem()+""));
                        psstokmasuk.setString(3,rstampilbarang.getString("kode_brng"));
                        psstokmasuk.setString(4,rstampilbarang.getString("no_batch"));
                        psstokmasuk.setString(5,rstampilbarang.getString("no_faktur"));
                        rsstokmasuk=psstokmasuk.executeQuery();
                        if(rsstokmasuk.next()){
                            stokmasuk=rsstokmasuk.getDouble("jumlah");
                        }
                    } catch (Exception e) {
                        stokmasuk=0;
                        System.out.println("Notofikasi : "+e);
                    } finally{
                        if(rsstokmasuk != null){
                            rsstokmasuk.close();
                        }
                        if(psstokmasuk != null){
                            psstokmasuk.close();
                        }
                    }

                    pagi=0;
                    siang=0;
                    sore=0;
                    malam=0;
                    pspemberian=koneksi.prepareStatement(
                        "select sum(detail_pemberian_obat.jml) as jml from detail_pemberian_obat where detail_pemberian_obat.status='Ranap' "+
                        "and detail_pemberian_obat.no_rawat=? and detail_pemberian_obat.tgl_perawatan=? and detail_pemberian_obat.kode_brng=? "+
                        "and detail_pemberian_obat.jam between ? and ? and detail_pemberian_obat.no_batch=? and detail_pemberian_obat.no_faktur=?");
                    try {
                        pspemberian.setString(1,TNoRw.getText());
                        pspemberian.setString(2,Valid.SetTgl(Tanggal.getSelectedItem()+""));
                        pspemberian.setString(3,rstampilbarang.getString("kode_brng"));
                        pspemberian.setString(4,"00:00:01");
                        pspemberian.setString(5,"10:00:00");
                        pspemberian.setString(6,rstampilbarang.getString("no_batch"));
                        pspemberian.setString(7,rstampilbarang.getString("no_faktur"));
                        rspemberian=pspemberian.executeQuery();
                        if(rspemberian.next()){
                            pagi=rspemberian.getDouble("jml");
                        }
                        
                        pspemberian.setString(1,TNoRw.getText());
                        pspemberian.setString(2,Valid.SetTgl(Tanggal.getSelectedItem()+""));
                        pspemberian.setString(3,rstampilbarang.getString("kode_brng"));
                        pspemberian.setString(4,"10:00:01");
                        pspemberian.setString(5,"15:00:00");
                        pspemberian.setString(6,rstampilbarang.getString("no_batch"));
                        pspemberian.setString(7,rstampilbarang.getString("no_faktur"));
                        rspemberian=pspemberian.executeQuery();
                        if(rspemberian.next()){
                            siang=rspemberian.getDouble("jml");
                        }
                        
                        pspemberian.setString(1,TNoRw.getText());
                        pspemberian.setString(2,Valid.SetTgl(Tanggal.getSelectedItem()+""));
                        pspemberian.setString(3,rstampilbarang.getString("kode_brng"));
                        pspemberian.setString(4,"15:00:01");
                        pspemberian.setString(5,"19:00:00");
                        pspemberian.setString(6,rstampilbarang.getString("no_batch"));
                        pspemberian.setString(7,rstampilbarang.getString("no_faktur"));
                        rspemberian=pspemberian.executeQuery();
                        if(rspemberian.next()){
                            sore=rspemberian.getDouble("jml");
                        }
                        
                        pspemberian.setString(1,TNoRw.getText());
                        pspemberian.setString(2,Valid.SetTgl(Tanggal.getSelectedItem()+""));
                        pspemberian.setString(3,rstampilbarang.getString("kode_brng"));
                        pspemberian.setString(4,"19:00:01");
                        pspemberian.setString(5,"23:59:59");
                        pspemberian.setString(6,rstampilbarang.getString("no_batch"));
                        pspemberian.setString(7,rstampilbarang.getString("no_faktur"));
                        rspemberian=pspemberian.executeQuery();
                        if(rspemberian.next()){
                            malam=rspemberian.getDouble("jml");
                        }
                    } catch (Exception e) {
                        pagi=0;
                        siang=0;
                        sore=0;
                        malam=0;
                        System.out.println("Notofikasi : "+e);
                    } finally{
                        if(rspemberian != null){
                            rspemberian.close();
                        }
                        if(pspemberian != null){
                            pspemberian.close();
                        }
                    }

                    keluar=0;
                    pskeluar=koneksi.prepareStatement(
                        "select sum(detail_pemberian_obat.jml) as jml from detail_pemberian_obat where detail_pemberian_obat.status='Ranap' and "+
                        "detail_pemberian_obat.no_rawat=? and detail_pemberian_obat.kode_brng=? and detail_pemberian_obat.no_batch=? and detail_pemberian_obat.no_faktur=?");
                    try {
                        pskeluar.setString(1,TNoRw.getText());
                        pskeluar.setString(2,rstampilbarang.getString("kode_brng"));
                        pskeluar.setString(3,rstampilbarang.getString("no_batch"));
                        pskeluar.setString(4,rstampilbarang.getString("no_faktur"));
                        rskeluar=pskeluar.executeQuery();
                        if(rskeluar.next()){
                            keluar=rskeluar.getDouble("jml");
                        }
                    } catch (Exception e) {
                        keluar=0;
                        System.out.println("Notofikasi : "+e);
                    } finally{
                        if(rskeluar != null){
                            rskeluar.close();
                        }
                        if(pskeluar != null){
                            pskeluar.close();
                        }
                    }
                        

                    retur=0;
                    psretur=koneksi.prepareStatement(
                            "select sum(returpasien.jml) as jml from returpasien where returpasien.no_rawat=? and "+
                            "returpasien.kode_brng=? and returpasien.no_batch=? and returpasien.no_faktur=?");
                    try {
                        psretur.setString(1,TNoRw.getText());
                        psretur.setString(2,rstampilbarang.getString("kode_brng"));
                        psretur.setString(3,rstampilbarang.getString("no_batch"));
                        psretur.setString(4,rstampilbarang.getString("no_faktur"));
                        rsretur=psretur.executeQuery();
                        if(rsretur.next()){
                            retur=rsretur.getDouble("jml");
                        }
                    } catch (Exception e) {
                        System.out.println("Notofikasi : "+e);
                    } finally{
                        if(rsretur != null){
                            rsretur.close();
                        }
                        if(psretur != null){
                            psretur.close();
                        }
                    }
                        

                    returshs=(rstampilbarang.getDouble("jumlah")-keluar);
                    if(returshs<0){
                        returshs=0;
                    }

                    hilang=((rstampilbarang.getDouble(3)-keluar)-retur);
                    if(hilang<0){
                        hilang=0;
                    }
                    tabMode.addRow(new Object[]{
                        false,rstampilbarang.getString("kode_brng"),rstampilbarang.getString("nama_brng"),stokmasuk,pagi,siang,sore,malam,
                        rstampilbarang.getDouble("jumlah"),keluar,retur,returshs,hilang,rstampilbarang.getString("no_batch"),rstampilbarang.getString("no_faktur"),
                        rstampilbarang.getString("aturan_pakai")
                    });
                }
            } catch (Exception e) {
                System.out.println(e);
            } finally{
                if(rstampilbarang != null){
                    rstampilbarang.close();
                }
                if(pstampilbarang != null){
                    pstampilbarang.close();
                }
            }                
        } catch (Exception e) {
            System.out.println(e);
        }       
    }
    
    public void setNoRm(String norwt,String norm,String pasien,Date tanggal) {        
        TNoRw.setText(norwt);
        TNoRM.setText(norm);
        TPasien.setText(pasien);
        
        Tanggal.setDate(tanggal);
        KdPj.setText(Sequel.cariIsi("select kd_pj from reg_periksa where no_rawat=?",norwt));
        kdgudang.setText(akses.getkdbangsal());
        kelas.setText(Sequel.cariIsi(
                "select kamar.kelas from kamar inner join kamar_inap on kamar.kd_kamar=kamar_inap.kd_kamar "+
                "where no_rawat=? and stts_pulang='-' order by STR_TO_DATE(concat(kamar_inap.tgl_masuk,' ',jam_masuk),'%Y-%m-%d %H:%i:%s') desc limit 1",norwt));
        if(kelas.getText().equals("Kelas 1")){
            Jeniskelas.setSelectedItem("Kelas 1");
        }else if(kelas.getText().equals("Kelas 2")){
            Jeniskelas.setSelectedItem("Kelas 2");
        }else if(kelas.getText().equals("Kelas 3")){
            Jeniskelas.setSelectedItem("Kelas 3");
        }else if(kelas.getText().equals("Kelas Utama")){
            Jeniskelas.setSelectedItem("Utama");
        }else if(kelas.getText().equals("Kelas VIP")){
            Jeniskelas.setSelectedItem("VIP");
        }else if(kelas.getText().equals("Kelas VVIP")){
            Jeniskelas.setSelectedItem("VVIP");
        } 
        kenaikan=Sequel.cariIsiAngka("select (hargajual/100) from set_harga_obat_ranap where kd_pj='"+KdPj.getText()+"' and kelas='"+kelas.getText()+"'");
    }  
    
    private void getData(){
        int row=tbObat.getSelectedRow();
        if(TNoRw.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"No. Rawat");
        }else if(row!= -1){ 
            Kd2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            int kolom=tbObat.getSelectedColumn();  
            if((kolom==4)||(kolom==5)||(kolom==6)||(kolom==7)){
                pagi=0;
                try {
                    pagi=Double.parseDouble(tbObat.getValueAt(row,4).toString()); 
                } catch (Exception e) {
                    pagi=0;
                }
                siang=0;
                try {
                    siang=Double.parseDouble(tbObat.getValueAt(row,5).toString()); 
                } catch (Exception e) {
                    siang=0;
                }
                sore=0;
                try {
                    sore=Double.parseDouble(tbObat.getValueAt(row,6).toString()); 
                } catch (Exception e) {
                    sore=0;
                }
                malam=0;
                try {
                    malam=Double.parseDouble(tbObat.getValueAt(row,7).toString()); 
                } catch (Exception e) {
                    malam=0;
                }
                stokmasuk=0;
                try {
                    stokmasuk=Double.parseDouble(tbObat.getValueAt(row,8).toString());
                } catch (Exception e) {
                    stokmasuk=0;
                }
                if(tbObat.getValueAt(row,0).toString().equals("false")){
                    if((stokmasuk-(pagi+siang+sore+malam))<0){
                        tbObat.setValueAt(0, row,kolom);  
                        JOptionPane.showMessageDialog(null,"Maaf, Stok tidak cukup....!!!");
                        tbObat.requestFocus();
                    }else{
                        tbObat.setValueAt((pagi+siang+sore+malam), row,9);  
                    }  
                }else{
                    try {
                        kapasitas=1;
                        pscariharga=koneksi.prepareStatement("select databarang.kelas1,databarang.kelas2,databarang.kelas3,"+
                            "databarang.utama,databarang.vip,databarang.vvip,databarang.beliluar,databarang.karyawan,databarang.h_beli,"+
                            "IFNULL(kapasitas,0) as kapasitas from databarang "+
                            "where databarang.kode_brng=?");
                        try {
                            pscariharga.setString(1,tbObat.getValueAt(row,1).toString());
                            rscariharga=pscariharga.executeQuery();
                            while(rscariharga.next()){
                                if(rscariharga.getDouble("kapasitas")>0){
                                    kapasitas=rscariharga.getDouble("kapasitas");                                
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("Notofikasi : "+e);
                        } finally{
                            if(rscariharga != null){
                                rscariharga.close();
                            }
                            if(pscariharga != null){
                                pscariharga.close();
                            }
                        }
                            
                        if((stokmasuk-((pagi/kapasitas)+(siang/kapasitas)+(sore/kapasitas)+(malam/kapasitas)))<0){
                            tbObat.setValueAt(0, row,kolom);  
                            JOptionPane.showMessageDialog(null,"Maaf, Stok tidak cukup....!!!");
                            tbObat.requestFocus();
                        }else{
                            tbObat.setValueAt(((pagi/kapasitas)+(siang/kapasitas)+(sore/kapasitas)+(malam/kapasitas)), row,9);  
                        }
                    } catch (Exception ex) {
                        System.out.println(ex);
                    }
                }
                                               
            }else if(kolom==10){
                retur=0;
                try {
                    retur=Double.parseDouble(tbObat.getValueAt(row,10).toString()); 
                } catch (Exception e) {
                    retur=0;
                }
                stokmasuk=0;
                try {
                    stokmasuk=Double.parseDouble(tbObat.getValueAt(row,11).toString());
                } catch (Exception e) {
                    stokmasuk=0;
                }
                if((stokmasuk-retur)<0){
                    tbObat.setValueAt(0, row,kolom);  
                    JOptionPane.showMessageDialog(null,"Maaf, Stok tidak cukup....!!!");
                    tbObat.requestFocus();
                }else{
                    tbObat.setValueAt((stokmasuk-retur), row,12);   
                }   
            }
        }
    }

    public JTable getTable(){
        return tbObat;
    }
}

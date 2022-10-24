/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgPenyakit.java
 *
 * Created on May 23, 2010, 12:57:16 AM
 */

package simrskhanza;

import inventory.DlgBarang;
import inventory.DlgCariKonversi;
import fungsi.WarnaTable2;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import inventory.DlgCariAturanPakai;
import inventory.riwayatobat;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
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

/**
 *
 * @author dosen
 */
public final class DlgInputResepPulang extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private riwayatobat Trackobat=new riwayatobat();
    private PreparedStatement psobat;
    private ResultSet rs;
    private WarnaTable2 warna=new WarnaTable2();
    private String aktifkanbatch="no",pilihanetiket="",nopermintaan="";
    private boolean sukses=true;
    private DlgCariBangsal bangsal=new DlgCariBangsal(null,false);
    private DlgCariAturanPakai aturan=new DlgCariAturanPakai(null,false);
    
    /** Creates new form DlgPenyakit
     * @param parent
     * @param modal */
    public DlgInputResepPulang(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(10,2);
        setSize(656,250);

        tabMode=new DefaultTableModel(null,new Object[]{"Jml","Kode Barang","Nama Barang","Satuan","Aturan Pakai","Kandungan","Harga(Rp)","Jenis Obat","No.Batch","No.Faktur","Stok"}){
            @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if ((colIndex==0)||(colIndex==4)||(colIndex==8)||(colIndex==9)) {
                    a=true;
                }
                return a;
             }
        };
        tbKamar.setModel(tabMode);
        //tbPenyakit.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbPenyakit.getBackground()));
        tbKamar.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbKamar.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (i = 0; i < 11; i++) {
            TableColumn column = tbKamar.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(45);
            }else if(i==1){
                column.setPreferredWidth(85);
            }else if(i==2){
                column.setPreferredWidth(200);
            }else if(i==3){
                column.setPreferredWidth(60);
            }else if(i==4){
                column.setPreferredWidth(120);
            }else if(i==5){
                column.setPreferredWidth(120);
            }else if(i==6){
                column.setPreferredWidth(90);
            }else if(i==7){
                column.setPreferredWidth(100);
            }else if(i==8){
                column.setPreferredWidth(70);
            }else if(i==9){
                column.setPreferredWidth(100);
            }else if(i==10){
                column.setPreferredWidth(40);
            }
        }
        warna.kolom=0;
        tbKamar.setDefaultRenderer(Object.class,warna);
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
        
        bangsal.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(bangsal.getTable().getSelectedRow()!= -1){                   
                    kdgudang.setText(bangsal.getTable().getValueAt(bangsal.getTable().getSelectedRow(),0).toString());
                    nmgudang.setText(bangsal.getTable().getValueAt(bangsal.getTable().getSelectedRow(),1).toString());
                }   
                kdgudang.requestFocus();
                tampil();
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
        
        aturan.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(aturan.getTable().getSelectedRow()!= -1){  
                    tbKamar.setValueAt(aturan.getTable().getValueAt(aturan.getTable().getSelectedRow(),0).toString(),tbKamar.getSelectedRow(),4);
                }   
                tbKamar.requestFocus();
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
        
        try {
            aktifkanbatch = koneksiDB.AKTIFKANBATCHOBAT();
        } catch (Exception e) {
            System.out.println("E : "+e);
            aktifkanbatch = "no";
        }
    }
    private DlgBarang barang=new DlgBarang(null,false);
    private double x=0,y=0,z=0,stokbarang=0,kenaikan=0;
    private int jml=0,i=0,index;
    private double[] jumlah,harga,stok;
    private String[] kodebarang,namabarang,kodesatuan,letakbarang,namajenis,dosis,nobatch,nofaktur;


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Popup = new javax.swing.JPopupMenu();
        ppBersihkan = new javax.swing.JMenuItem();
        TNoRw = new widget.TextBox();
        TKdPny = new widget.TextBox();
        Tanggal = new widget.TextBox();
        Jam = new widget.TextBox();
        KdPj = new widget.TextBox();
        kelas = new widget.TextBox();
        TNoRM = new widget.TextBox();
        TPasien = new widget.TextBox();
        kdgudang = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbKamar = new widget.Table();
        panelisi3 = new widget.panelisi();
        label21 = new widget.Label();
        nmgudang = new widget.TextBox();
        BtnGudang = new widget.Button();
        label9 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        BtnSeek5 = new widget.Button();
        BtnSimpan = new widget.Button();
        label12 = new widget.Label();
        Jeniskelas = new widget.ComboBox();
        BtnKeluar = new widget.Button();

        Popup.setName("Popup"); // NOI18N

        ppBersihkan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppBersihkan.setForeground(new java.awt.Color(50, 50, 50));
        ppBersihkan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); // NOI18N
        ppBersihkan.setText("Bersihkan Jumlah");
        ppBersihkan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppBersihkan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppBersihkan.setName("ppBersihkan"); // NOI18N
        ppBersihkan.setPreferredSize(new java.awt.Dimension(200, 25));
        ppBersihkan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppBersihkanActionPerformed(evt);
            }
        });
        Popup.add(ppBersihkan);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N

        TKdPny.setName("TKdPny"); // NOI18N

        Tanggal.setHighlighter(null);
        Tanggal.setName("Tanggal"); // NOI18N

        Jam.setHighlighter(null);
        Jam.setName("Jam"); // NOI18N

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

        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N

        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N

        kdgudang.setName("kdgudang"); // NOI18N
        kdgudang.setPreferredSize(new java.awt.Dimension(45, 23));
        kdgudang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdgudangKeyPressed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Input Resep Pulang ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setComponentPopupMenu(Popup);
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbKamar.setAutoCreateRowSorter(true);
        tbKamar.setComponentPopupMenu(Popup);
        tbKamar.setName("tbKamar"); // NOI18N
        tbKamar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbKamarMouseClicked(evt);
            }
        });
        tbKamar.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tbKamarPropertyChange(evt);
            }
        });
        tbKamar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbKamarKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbKamarKeyReleased(evt);
            }
        });
        Scroll.setViewportView(tbKamar);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(100, 43));
        panelisi3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        label21.setText("Asal Stok :");
        label21.setName("label21"); // NOI18N
        label21.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(label21);

        nmgudang.setEditable(false);
        nmgudang.setName("nmgudang"); // NOI18N
        nmgudang.setPreferredSize(new java.awt.Dimension(130, 23));
        panelisi3.add(nmgudang);

        BtnGudang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnGudang.setMnemonic('2');
        BtnGudang.setToolTipText("Alt+2");
        BtnGudang.setName("BtnGudang"); // NOI18N
        BtnGudang.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnGudang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnGudangActionPerformed(evt);
            }
        });
        panelisi3.add(BtnGudang);

        label9.setText("Key Word :");
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(68, 23));
        panelisi3.add(label9);

        TCari.setToolTipText("Alt+C");
        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(150, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelisi3.add(TCari);

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
        panelisi3.add(BtnCari);

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
        panelisi3.add(BtnAll);

        BtnSeek5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/011.png"))); // NOI18N
        BtnSeek5.setMnemonic('4');
        BtnSeek5.setToolTipText("Alt+4");
        BtnSeek5.setName("BtnSeek5"); // NOI18N
        BtnSeek5.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSeek5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek5ActionPerformed(evt);
            }
        });
        BtnSeek5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSeek5KeyPressed(evt);
            }
        });
        panelisi3.add(BtnSeek5);

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan.setMnemonic('S');
        BtnSimpan.setToolTipText("Alt+S");
        BtnSimpan.setName("BtnSimpan"); // NOI18N
        BtnSimpan.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpanActionPerformed(evt);
            }
        });
        panelisi3.add(BtnSimpan);

        label12.setText("Tarif :");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(40, 23));
        panelisi3.add(label12);

        Jeniskelas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Kelas 1", "Kelas 2", "Kelas 3", "Utama", "VIP", "VVIP", "Beli Luar", "Karyawan", "Harga Beli" }));
        Jeniskelas.setName("Jeniskelas"); // NOI18N
        Jeniskelas.setPreferredSize(new java.awt.Dimension(100, 23));
        Jeniskelas.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                JeniskelasItemStateChanged(evt);
            }
        });
        Jeniskelas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JeniskelasKeyPressed(evt);
            }
        });
        panelisi3.add(Jeniskelas);

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar.setMnemonic('5');
        BtnKeluar.setToolTipText("Alt+5");
        BtnKeluar.setName("BtnKeluar"); // NOI18N
        BtnKeluar.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluarActionPerformed(evt);
            }
        });
        panelisi3.add(BtnKeluar);

        internalFrame1.add(panelisi3, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            tbKamar.requestFocus();
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
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnCari, TCari);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
    if(aktifkanbatch.equals("yes")){
        index=0;
        jml=tbKamar.getRowCount();
        for(i=0;i<jml;i++){
            if((Valid.SetAngka(tbKamar.getValueAt(i,0).toString())>0)&&(tbKamar.getValueAt(i,8).toString().trim().equals("")||tbKamar.getValueAt(i,9).toString().trim().equals(""))){
                index++;
            }
        }
    }    
    if(TNoRw.getText().trim().equals("")||TKdPny.getText().trim().equals("")){
        Valid.textKosong(TCari,"Data");
    }else if(aktifkanbatch.equals("yes")&&(index>0)){
        Valid.textKosong(TCari,"No.Batch/No.Faktur");
    }else if(kdgudang.getText().equals("")){
        Valid.textKosong(TCari,"Lokasi");
    }else{  
        Sequel.AutoComitFalse();
        sukses=true;
        for(i=0;i<tbKamar.getRowCount();i++){ 
            if(Valid.SetAngka(tbKamar.getValueAt(i,0).toString())>0){
                if(Sequel.menyimpantf("resep_pulang","?,?,?,?,?,?,?,?,?,?,?","data",11,new String[]{
                        TNoRw.getText(),tbKamar.getValueAt(i,1).toString(),tbKamar.getValueAt(i,0).toString(),
                        tbKamar.getValueAt(i,6).toString(),""+Double.parseDouble(tbKamar.getValueAt(i,6).toString())*Double.parseDouble(tbKamar.getValueAt(i,0).toString()),
                        tbKamar.getValueAt(i,4).toString(),Tanggal.getText(),Jam.getText(),kdgudang.getText(),
                        tbKamar.getValueAt(i,8).toString(),tbKamar.getValueAt(i,9).toString()
                    })==true){
                        if(aktifkanbatch.equals("yes")){
                            Sequel.mengedit3("data_batch","no_batch=? and kode_brng=? and no_faktur=?","sisa=sisa-?",4,new String[]{
                                ""+tabMode.getValueAt(i,0).toString(),tabMode.getValueAt(i,8).toString(),tabMode.getValueAt(i,1).toString(),tabMode.getValueAt(i,9).toString()
                            });
                            Trackobat.catatRiwayat(tbKamar.getValueAt(i,1).toString(),0,Valid.SetAngka(tbKamar.getValueAt(i,0).toString()),"Resep Pulang",akses.getkode(),kdgudang.getText(),"Simpan",tbKamar.getValueAt(i,8).toString(),tbKamar.getValueAt(i,9).toString(),TNoRw.getText()+" "+TNoRM.getText()+" "+TPasien.getText());
                            Sequel.menyimpan("gudangbarang","'"+tbKamar.getValueAt(i,1).toString()+"','"+kdgudang.getText()+"','-"+tbKamar.getValueAt(i,0).toString()+"','"+tabMode.getValueAt(i,8).toString()+"','"+tabMode.getValueAt(i,9).toString()+"'", 
                                         "stok=stok-'"+tbKamar.getValueAt(i,0).toString()+"'","kode_brng='"+tbKamar.getValueAt(i,1).toString()+"' and kd_bangsal='"+kdgudang.getText()+"' and no_batch='"+tabMode.getValueAt(i,8).toString()+"' and no_faktur='"+tabMode.getValueAt(i,9).toString()+"'");           
                        }else{
                            Trackobat.catatRiwayat(tbKamar.getValueAt(i,1).toString(),0,Valid.SetAngka(tbKamar.getValueAt(i,0).toString()),"Resep Pulang",akses.getkode(),kdgudang.getText(),"Simpan","","",TNoRw.getText()+" "+TNoRM.getText()+" "+TPasien.getText());
                            Sequel.menyimpan("gudangbarang","'"+tbKamar.getValueAt(i,1).toString()+"','"+kdgudang.getText()+"','-"+tbKamar.getValueAt(i,0).toString()+"','',''", 
                                         "stok=stok-'"+tbKamar.getValueAt(i,0).toString()+"'","kode_brng='"+tbKamar.getValueAt(i,1).toString()+"' and kd_bangsal='"+kdgudang.getText()+"' and no_batch='' and no_faktur=''");           
                        }
                                            
                }else{
                    sukses=false;
                }
            }
        }  
        
        if(!nopermintaan.equals("")){
            Sequel.mengedit("permintaan_resep_pulang","no_permintaan='"+nopermintaan+"'","tgl_validasi='"+Tanggal.getText()+"',jam_validasi='"+Jam.getText()+"',status='Sudah'");
        }
        
        if(sukses==true){
            Sequel.Commit();
            
            Map<String, Object> param = new HashMap<>();  
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            pilihanetiket = (String)JOptionPane.showInputDialog(null,"Silahkan pilih cetak aturan pakai..!!","Cetak Aturan Pakai",JOptionPane.QUESTION_MESSAGE,null,new Object[]{"Cetak Aturan Pakai Model 1","Cetak Aturan Pakai Model 2","Cetak Aturan Pakai Model 3","Cetak Label Obat","Cetak Aturan Pakai Model 1 & Cetak Label Obat","Cetak Aturan Pakai Model 2 & Cetak Label Obat","Cetak Aturan Pakai Model 3 & Cetak Label Obat"},"Cetak Aturan Pakai Model 1");
            switch (pilihanetiket) {
                case "Cetak Aturan Pakai Model 1": 
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    if(Sequel.cariInteger(
                            "select count(*) from resep_pulang where resep_pulang.no_rawat=? and resep_pulang.dosis<>''",TNoRw.getText())>0){
                        param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                        Valid.MyReportqry("rptItemResepPulang.jasper","report","::[ Aturan Pakai Obat ]::",
                            "select resep_pulang.no_rawat,resep_pulang.tanggal, "+
                            "reg_periksa.no_rkm_medis,pasien.nm_pasien,databarang.nama_brng,"+
                            "resep_pulang.dosis,resep_pulang.jml_barang,kodesatuan.satuan "+
                            "from resep_pulang inner join reg_periksa on resep_pulang.no_rawat=reg_periksa.no_rawat "+
                            "inner join databarang on resep_pulang.kode_brng=databarang.kode_brng "+
                            "inner join kodesatuan on databarang.kode_sat=kodesatuan.kode_sat "+
                            "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                            "where resep_pulang.no_rawat='"+TNoRw.getText()+"' and resep_pulang.dosis<>''",param);
                    }            
                    this.setCursor(Cursor.getDefaultCursor());
                    break;
                case "Cetak Aturan Pakai Model 2": 
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    if(Sequel.cariInteger(
                            "select count(*) from resep_pulang where resep_pulang.no_rawat=? and resep_pulang.dosis<>''",TNoRw.getText())>0){
                        param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                        Valid.MyReportqry("rptItemResepPulang2.jasper","report","::[ Aturan Pakai Obat ]::",
                            "select resep_pulang.no_rawat,resep_pulang.tanggal,jenis.nama,"+
                            "reg_periksa.no_rkm_medis,pasien.nm_pasien,databarang.nama_brng,"+
                            "resep_pulang.dosis,resep_pulang.jml_barang,kodesatuan.satuan "+
                            "from resep_pulang inner join reg_periksa on resep_pulang.no_rawat=reg_periksa.no_rawat "+
                            "inner join databarang on resep_pulang.kode_brng=databarang.kode_brng "+
                            "inner join kodesatuan on databarang.kode_sat=kodesatuan.kode_sat "+
                            "inner join jenis on databarang.kdjns= jenis.kdjns "+
                            "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                            "where resep_pulang.no_rawat='"+TNoRw.getText()+"' and resep_pulang.dosis<>''",param);
                    }           
                    this.setCursor(Cursor.getDefaultCursor());
                    break;
                case "Cetak Aturan Pakai Model 3": 
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    if(Sequel.cariInteger(
                            "select count(*) from resep_pulang where resep_pulang.no_rawat=? and resep_pulang.dosis<>''",TNoRw.getText())>0){
                        param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                        Valid.MyReportqry("rptItemResepPulang3.jasper","report","::[ Aturan Pakai Obat ]::",
                            "select resep_pulang.no_rawat,resep_pulang.tanggal, "+
                            "reg_periksa.no_rkm_medis,pasien.nm_pasien,databarang.nama_brng,"+
                            "resep_pulang.dosis,resep_pulang.jml_barang,kodesatuan.satuan "+
                            "from resep_pulang inner join reg_periksa on resep_pulang.no_rawat=reg_periksa.no_rawat "+
                            "inner join databarang on resep_pulang.kode_brng=databarang.kode_brng "+
                            "inner join kodesatuan on databarang.kode_sat=kodesatuan.kode_sat "+
                            "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                            "where resep_pulang.no_rawat='"+TNoRw.getText()+"' and resep_pulang.dosis<>''",param);
                    }             
                    this.setCursor(Cursor.getDefaultCursor());
                    break;
                default:
                    break;
            }
        }else{
            sukses=false;
            JOptionPane.showMessageDialog(null,"Terjadi kesalahan saat pemrosesan data, transaksi dibatalkan.\nPeriksa kembali data sebelum melanjutkan menyimpan..!!");
            Sequel.RollBack();
        }
        Sequel.AutoComitTrue();
        if(sukses==true){
            dispose();
            ppBersihkanActionPerformed(null);
        }
    }
}//GEN-LAST:event_BtnSimpanActionPerformed

private void BtnSeek5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek5ActionPerformed
    DlgCariKonversi carikonversi=new DlgCariKonversi(null,false);
    carikonversi.setLocationRelativeTo(internalFrame1);
    carikonversi.setVisible(true);
}//GEN-LAST:event_BtnSeek5ActionPerformed

private void BtnSeek5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeek5KeyPressed
// TODO add your handling code here:
}//GEN-LAST:event_BtnSeek5KeyPressed

private void ppBersihkanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBersihkanActionPerformed
            for(i=0;i<tbKamar.getRowCount();i++){ 
                tbKamar.setValueAt("",i,0);
            }
}//GEN-LAST:event_ppBersihkanActionPerformed

private void JeniskelasItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_JeniskelasItemStateChanged
       tampil(); 
}//GEN-LAST:event_JeniskelasItemStateChanged

private void JeniskelasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JeniskelasKeyPressed
        Valid.pindah(evt, TCari,BtnKeluar);
}//GEN-LAST:event_JeniskelasKeyPressed

    private void tbKamarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbKamarKeyPressed
        if(tabMode.getRowCount()!=0){
            if(evt.getKeyCode()==KeyEvent.VK_SPACE){
                if(tbKamar.getSelectedColumn()!=4){
                    dispose();
                }
            }else if(evt.getKeyCode()==KeyEvent.VK_SHIFT){
                TCari.setText("");
                TCari.requestFocus();
            }else if(evt.getKeyCode()==KeyEvent.VK_RIGHT){
                i=tbKamar.getSelectedColumn();
                if(i==4){
                    aturan.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
                    aturan.setLocationRelativeTo(internalFrame1);
                    aturan.setVisible(true);
                }
            } 
        }
    }//GEN-LAST:event_tbKamarKeyPressed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        emptTeks();
    }//GEN-LAST:event_formWindowActivated

    private void KdPjKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdPjKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KdPjKeyPressed

    private void kelasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kelasKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_kelasKeyPressed

    private void tbKamarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbKamarKeyReleased
        if(tabMode.getRowCount()!=0){
            if(evt.getKeyCode()==KeyEvent.VK_ENTER){
                try {                                     
                    getData();                     
                    TCari.setText("");
                    TCari.requestFocus();
                } catch (java.lang.NullPointerException e) {
                }
            }else if((evt.getKeyCode()==KeyEvent.VK_RIGHT)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {                                     
                    getData();           
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }//GEN-LAST:event_tbKamarKeyReleased

    private void tbKamarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbKamarMouseClicked
        if(tabMode.getRowCount()!=0){
            try {                  
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbKamarMouseClicked

    private void tbKamarPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tbKamarPropertyChange
        if(this.isVisible()==true){
              getData();
        }
    }//GEN-LAST:event_tbKamarPropertyChange

    private void kdgudangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdgudangKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select bangsal.nm_bangsal from bangsal where bangsal.kd_bangsal=?",nmgudang,kdgudang.getText());
            tampil();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select bangsal.nm_bangsal from bangsal where bangsal.kd_bangsal=?",nmgudang,kdgudang.getText());
            tampil();
            BtnKeluar.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select bangsal.nm_bangsal from bangsal where bangsal.kd_bangsal=?",nmgudang,kdgudang.getText());
            tampil();
            BtnSimpan.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnGudangActionPerformed(null);
        }
    }//GEN-LAST:event_kdgudangKeyPressed

    private void BtnGudangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnGudangActionPerformed
        bangsal.isCek();
        bangsal.emptTeks();
        bangsal.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        bangsal.setLocationRelativeTo(internalFrame1);
        bangsal.setAlwaysOnTop(false);
        bangsal.setVisible(true);
    }//GEN-LAST:event_BtnGudangActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgInputResepPulang dialog = new DlgInputResepPulang(new javax.swing.JFrame(), true);
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
    private widget.Button BtnGudang;
    private widget.Button BtnKeluar;
    private widget.Button BtnSeek5;
    private widget.Button BtnSimpan;
    private widget.TextBox Jam;
    private widget.ComboBox Jeniskelas;
    private widget.TextBox KdPj;
    private javax.swing.JPopupMenu Popup;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.TextBox TKdPny;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.TextBox Tanggal;
    private widget.InternalFrame internalFrame1;
    private widget.TextBox kdgudang;
    private widget.TextBox kelas;
    private widget.Label label12;
    private widget.Label label21;
    private widget.Label label9;
    private widget.TextBox nmgudang;
    private widget.panelisi panelisi3;
    private javax.swing.JMenuItem ppBersihkan;
    private widget.Table tbKamar;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        jml=0;
        for(i=0;i<tbKamar.getRowCount();i++){
            if(!tbKamar.getValueAt(i,0).toString().equals("")){
                jml++;
            }
        }
        
        jumlah=null;
        jumlah=new double[jml];
        harga=null;
        harga=new double[jml];
        kodebarang=null;
        kodebarang=new String[jml];
        namabarang=null;
        namabarang=new String[jml];
        kodesatuan=null;
        kodesatuan=new String[jml];
        letakbarang=null;
        letakbarang=new String[jml];
        namajenis=null;
        namajenis=new String[jml];
        dosis=null;
        dosis=new String[jml];
        nobatch=null;
        nobatch=new String[jml];
        nofaktur=null;
        nofaktur=new String[jml];
        stok=null;
        stok=new double[jml];
        
        index=0;        
        for(i=0;i<tbKamar.getRowCount();i++){
            if(!tbKamar.getValueAt(i,0).toString().equals("")){
                jumlah[index]=Double.parseDouble(tbKamar.getValueAt(i,0).toString());
                kodebarang[index]=tbKamar.getValueAt(i,1).toString();
                namabarang[index]=tbKamar.getValueAt(i,2).toString();
                kodesatuan[index]=tbKamar.getValueAt(i,3).toString();
                dosis[index]=tbKamar.getValueAt(i,4).toString();
                letakbarang[index]=tbKamar.getValueAt(i,5).toString();
                harga[index]=Double.parseDouble(tbKamar.getValueAt(i,6).toString());
                namajenis[index]=tbKamar.getValueAt(i,7).toString();
                nobatch[index]=tbKamar.getValueAt(i,8).toString();
                nofaktur[index]=tbKamar.getValueAt(i,9).toString();
                stok[index]=Double.parseDouble(tbKamar.getValueAt(i,10).toString());
                index++;
            }
        }
        
        Valid.tabelKosong(tabMode);
        
        for(i=0;i<jml;i++){
            tabMode.addRow(new Object[]{jumlah[i],kodebarang[i],namabarang[i],kodesatuan[i],dosis[i],letakbarang[i],harga[i],namajenis[i],nobatch[i],nofaktur[i],stok[i]});
        }
        
        try{
            if(aktifkanbatch.equals("yes")){
                if(kenaikan>0){
                    psobat=koneksi.prepareStatement(
                            " select data_batch.kode_brng, databarang.nama_brng,jenis.nama, databarang.kode_sat,(data_batch.h_beli+(data_batch.h_beli*?)) as harga,gudangbarang.stok,"+
                            " databarang.letak_barang,data_batch.no_batch,data_batch.no_faktur from data_batch inner join databarang on data_batch.kode_brng=databarang.kode_brng inner join jenis on databarang.kdjns=jenis.kdjns "+
                            " inner join gudangbarang on gudangbarang.kode_brng=data_batch.kode_brng and gudangbarang.no_batch=data_batch.no_batch and gudangbarang.no_faktur=data_batch.no_faktur "+
                            " where gudangbarang.stok>0 and gudangbarang.kd_bangsal=? and databarang.kode_brng like ? or "+
                            " gudangbarang.stok>0 and gudangbarang.kd_bangsal=? and databarang.nama_brng like ? or "+
                            " gudangbarang.stok>0 and gudangbarang.kd_bangsal=? and jenis.nama like ? order by databarang.nama_brng");  
                }else{
                    psobat=koneksi.prepareStatement(
                            " select data_batch.kode_brng, databarang.nama_brng,jenis.nama, databarang.kode_sat,data_batch.kelas1,data_batch.kelas2,data_batch.kelas3,data_batch.utama,"+
                            " data_batch.vip,data_batch.vvip,data_batch.beliluar,data_batch.karyawan,data_batch.h_beli,databarang.letak_barang,gudangbarang.stok,data_batch.no_batch,data_batch.no_faktur "+
                            " from data_batch inner join databarang on data_batch.kode_brng=databarang.kode_brng inner join jenis on databarang.kdjns=jenis.kdjns "+
                            " inner join gudangbarang on gudangbarang.kode_brng=data_batch.kode_brng and gudangbarang.no_batch=data_batch.no_batch and gudangbarang.no_faktur=data_batch.no_faktur "+
                            " where gudangbarang.stok>0 and gudangbarang.kd_bangsal=? and databarang.kode_brng like ? or "+
                            " gudangbarang.stok>0 and gudangbarang.kd_bangsal=? and databarang.nama_brng like ? or "+
                            " gudangbarang.stok>0 and gudangbarang.kd_bangsal=? and jenis.nama like ? order by databarang.nama_brng"); 
                }   

                try {
                    if(kenaikan>0){
                        psobat.setDouble(1,kenaikan);
                        psobat.setString(2,kdgudang.getText());
                        psobat.setString(3,"%"+TCari.getText().trim()+"%");
                        psobat.setString(4,kdgudang.getText());
                        psobat.setString(5,"%"+TCari.getText().trim()+"%");
                        psobat.setString(6,kdgudang.getText());
                        psobat.setString(7,"%"+TCari.getText().trim()+"%");
                        rs=psobat.executeQuery();
                        while(rs.next()){
                            tabMode.addRow(new Object[]{"",rs.getString("kode_brng"),rs.getString("nama_brng"),rs.getString("kode_sat"),"",rs.getString("letak_barang"),Valid.SetAngka2(Valid.roundUp(rs.getDouble("harga"),100)),rs.getString("nama"),rs.getString("no_batch"),rs.getString("no_faktur"),rs.getDouble("stok")});
                        }
                    }else{
                        psobat.setString(1,kdgudang.getText());
                        psobat.setString(2,"%"+TCari.getText().trim()+"%");
                        psobat.setString(3,kdgudang.getText());
                        psobat.setString(4,"%"+TCari.getText().trim()+"%");
                        psobat.setString(5,kdgudang.getText());
                        psobat.setString(6,"%"+TCari.getText().trim()+"%");
                        rs=psobat.executeQuery();
                        if(Jeniskelas.getSelectedItem().equals("Kelas 1")){
                            while(rs.next()){
                                tabMode.addRow(new Object[]{"",rs.getString("kode_brng"),rs.getString("nama_brng"),rs.getString("kode_sat"),"",rs.getString("letak_barang"),Valid.SetAngka2(Valid.roundUp(rs.getDouble("kelas1"),100)),rs.getString("nama"),rs.getString("no_batch"),rs.getString("no_faktur"),rs.getDouble("stok")});
                            }
                        }else if(Jeniskelas.getSelectedItem().equals("Kelas 2")){
                            while(rs.next()){
                                tabMode.addRow(new Object[]{"",rs.getString("kode_brng"),rs.getString("nama_brng"),rs.getString("kode_sat"),"",rs.getString("letak_barang"),Valid.SetAngka2(Valid.roundUp(rs.getDouble("kelas2"),100)),rs.getString("nama"),rs.getString("no_batch"),rs.getString("no_faktur"),rs.getDouble("stok")});
                            }
                        }else if(Jeniskelas.getSelectedItem().equals("Kelas 3")){
                            while(rs.next()){
                                tabMode.addRow(new Object[]{"",rs.getString("kode_brng"),rs.getString("nama_brng"),rs.getString("kode_sat"),"",rs.getString("letak_barang"),Valid.SetAngka2(Valid.roundUp(rs.getDouble("kelas3"),100)),rs.getString("nama"),rs.getString("no_batch"),rs.getString("no_faktur"),rs.getDouble("stok")});
                            }
                        }else if(Jeniskelas.getSelectedItem().equals("Utama")){
                            while(rs.next()){
                                tabMode.addRow(new Object[]{"",rs.getString("kode_brng"),rs.getString("nama_brng"),rs.getString("kode_sat"),"",rs.getString("letak_barang"),Valid.SetAngka2(Valid.roundUp(rs.getDouble("utama"),100)),rs.getString("nama"),rs.getString("no_batch"),rs.getString("no_faktur"),rs.getDouble("stok")});
                            }
                        }else if(Jeniskelas.getSelectedItem().equals("VIP")){
                            while(rs.next()){
                                tabMode.addRow(new Object[]{"",rs.getString("kode_brng"),rs.getString("nama_brng"),rs.getString("kode_sat"),"",rs.getString("letak_barang"),Valid.SetAngka2(Valid.roundUp(rs.getDouble("vip"),100)),rs.getString("nama"),rs.getString("no_batch"),rs.getString("no_faktur"),rs.getDouble("stok")});
                            }
                         }else if(Jeniskelas.getSelectedItem().equals("VVIP")){
                            while(rs.next()){
                                tabMode.addRow(new Object[]{"",rs.getString("kode_brng"),rs.getString("nama_brng"),rs.getString("kode_sat"),"",rs.getString("letak_barang"),Valid.SetAngka2(Valid.roundUp(rs.getDouble("vvip"),100)),rs.getString("nama"),rs.getString("no_batch"),rs.getString("no_faktur"),rs.getDouble("stok")});
                            }
                        }else if(Jeniskelas.getSelectedItem().equals("Beli Luar")){
                            while(rs.next()){
                                tabMode.addRow(new Object[]{"",rs.getString("kode_brng"),rs.getString("nama_brng"),rs.getString("kode_sat"),"",rs.getString("letak_barang"),Valid.SetAngka2(Valid.roundUp(rs.getDouble("beliluar"),100)),rs.getString("nama"),rs.getString("no_batch"),rs.getString("no_faktur"),rs.getDouble("stok")});
                            }
                        }else if(Jeniskelas.getSelectedItem().equals("Karyawan")){
                            while(rs.next()){
                                tabMode.addRow(new Object[]{"",rs.getString("kode_brng"),rs.getString("nama_brng"),rs.getString("kode_sat"),"",rs.getString("letak_barang"),Valid.SetAngka2(Valid.roundUp(rs.getDouble("karyawan"),100)),rs.getString("nama"),rs.getString("no_batch"),rs.getString("no_faktur"),rs.getDouble("stok")});
                            }
                        }else if(Jeniskelas.getSelectedItem().equals("Harga Beli")){
                            while(rs.next()){
                                tabMode.addRow(new Object[]{"",rs.getString("kode_brng"),rs.getString("nama_brng"),rs.getString("kode_sat"),"",rs.getString("letak_barang"),Valid.SetAngka2(Valid.roundUp(rs.getDouble("h_beli"),100)),rs.getString("nama"),rs.getString("no_batch"),rs.getString("no_faktur"),rs.getDouble("stok")});
                            }
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Notif obat2 : "+e);
                } finally{
                    if(rs!=null){
                        rs.close();
                    }
                    if(psobat!=null){
                        psobat.close();
                    }
                }
            }else{
                if(kenaikan>0){
                    psobat=koneksi.prepareStatement(
                            " select databarang.kode_brng, databarang.nama_brng,jenis.nama, databarang.kode_sat,(databarang.h_beli+(databarang.h_beli*?)) as harga,gudangbarang.stok,"+
                            " databarang.letak_barang from databarang inner join jenis on databarang.kdjns=jenis.kdjns inner join gudangbarang on databarang.kode_brng=gudangbarang.kode_brng "+
                            " where gudangbarang.no_batch='' and gudangbarang.no_faktur='' and gudangbarang.stok>0 and gudangbarang.kd_bangsal=? and databarang.status='1' and databarang.kode_brng like ? or "+
                            " gudangbarang.no_batch='' and gudangbarang.no_faktur='' and gudangbarang.stok>0 and gudangbarang.kd_bangsal=? and databarang.status='1' and databarang.nama_brng like ? or "+
                            " gudangbarang.no_batch='' and gudangbarang.no_faktur='' and gudangbarang.stok>0 and gudangbarang.kd_bangsal=? and databarang.status='1' and jenis.nama like ? order by databarang.nama_brng");  
                }else{
                    psobat=koneksi.prepareStatement(
                            " select databarang.kode_brng, databarang.nama_brng,jenis.nama, databarang.kode_sat,databarang.kelas1,databarang.kelas2,databarang.kelas3,databarang.utama,"+
                            " databarang.vip,databarang.vvip,databarang.beliluar,databarang.karyawan,databarang.h_beli,databarang.letak_barang,gudangbarang.stok "+
                            " from databarang inner join jenis on databarang.kdjns=jenis.kdjns inner join gudangbarang on databarang.kode_brng=gudangbarang.kode_brng "+
                            " where gudangbarang.no_batch='' and gudangbarang.no_faktur='' and gudangbarang.stok>0 and gudangbarang.kd_bangsal=? and databarang.status='1' and databarang.kode_brng like ? or "+
                            " gudangbarang.no_batch='' and gudangbarang.no_faktur='' and gudangbarang.stok>0 and gudangbarang.kd_bangsal=? and databarang.status='1' and databarang.nama_brng like ? or "+
                            " gudangbarang.no_batch='' and gudangbarang.no_faktur='' and gudangbarang.stok>0 and gudangbarang.kd_bangsal=? and databarang.status='1' and jenis.nama like ? order by databarang.nama_brng"); 
                }   

                try {
                    if(kenaikan>0){
                        psobat.setDouble(1,kenaikan);
                        psobat.setString(2,kdgudang.getText());
                        psobat.setString(3,"%"+TCari.getText().trim()+"%");
                        psobat.setString(4,kdgudang.getText());
                        psobat.setString(5,"%"+TCari.getText().trim()+"%");
                        psobat.setString(6,kdgudang.getText());
                        psobat.setString(7,"%"+TCari.getText().trim()+"%");
                        rs=psobat.executeQuery();
                        while(rs.next()){
                            tabMode.addRow(new Object[]{"",rs.getString("kode_brng"),rs.getString("nama_brng"),rs.getString("kode_sat"),"",rs.getString("letak_barang"),Valid.SetAngka2(Valid.roundUp(rs.getDouble("harga"),100)),rs.getString("nama"),"","",rs.getDouble("stok")});
                        }
                    }else{
                        psobat.setString(1,kdgudang.getText());
                        psobat.setString(2,"%"+TCari.getText().trim()+"%");
                        psobat.setString(3,kdgudang.getText());
                        psobat.setString(4,"%"+TCari.getText().trim()+"%");
                        psobat.setString(5,kdgudang.getText());
                        psobat.setString(6,"%"+TCari.getText().trim()+"%");
                        rs=psobat.executeQuery();
                        if(Jeniskelas.getSelectedItem().equals("Kelas 1")){
                            while(rs.next()){
                                tabMode.addRow(new Object[]{"",rs.getString("kode_brng"),rs.getString("nama_brng"),rs.getString("kode_sat"),"",rs.getString("letak_barang"),Valid.SetAngka2(Valid.roundUp(rs.getDouble("kelas1"),100)),rs.getString("nama"),"","",rs.getDouble("stok")});
                            }
                        }else if(Jeniskelas.getSelectedItem().equals("Kelas 2")){
                            while(rs.next()){
                                tabMode.addRow(new Object[]{"",rs.getString("kode_brng"),rs.getString("nama_brng"),rs.getString("kode_sat"),"",rs.getString("letak_barang"),Valid.SetAngka2(Valid.roundUp(rs.getDouble("kelas2"),100)),rs.getString("nama"),"","",rs.getDouble("stok")});
                            }
                        }else if(Jeniskelas.getSelectedItem().equals("Kelas 3")){
                            while(rs.next()){
                                tabMode.addRow(new Object[]{"",rs.getString("kode_brng"),rs.getString("nama_brng"),rs.getString("kode_sat"),"",rs.getString("letak_barang"),Valid.SetAngka2(Valid.roundUp(rs.getDouble("kelas3"),100)),rs.getString("nama"),"","",rs.getDouble("stok")});
                            }
                        }else if(Jeniskelas.getSelectedItem().equals("Utama")){
                            while(rs.next()){
                                tabMode.addRow(new Object[]{"",rs.getString("kode_brng"),rs.getString("nama_brng"),rs.getString("kode_sat"),"",rs.getString("letak_barang"),Valid.SetAngka2(Valid.roundUp(rs.getDouble("utama"),100)),rs.getString("nama"),"","",rs.getDouble("stok")});
                            }
                        }else if(Jeniskelas.getSelectedItem().equals("VIP")){
                            while(rs.next()){
                                tabMode.addRow(new Object[]{"",rs.getString("kode_brng"),rs.getString("nama_brng"),rs.getString("kode_sat"),"",rs.getString("letak_barang"),Valid.SetAngka2(Valid.roundUp(rs.getDouble("vip"),100)),rs.getString("nama"),"","",rs.getDouble("stok")});
                            }
                         }else if(Jeniskelas.getSelectedItem().equals("VVIP")){
                            while(rs.next()){
                                tabMode.addRow(new Object[]{"",rs.getString("kode_brng"),rs.getString("nama_brng"),rs.getString("kode_sat"),"",rs.getString("letak_barang"),Valid.SetAngka2(Valid.roundUp(rs.getDouble("vvip"),100)),rs.getString("nama"),"","",rs.getDouble("stok")});
                            }
                        }else if(Jeniskelas.getSelectedItem().equals("Beli Luar")){
                            while(rs.next()){
                                tabMode.addRow(new Object[]{"",rs.getString("kode_brng"),rs.getString("nama_brng"),rs.getString("kode_sat"),"",rs.getString("letak_barang"),Valid.SetAngka2(Valid.roundUp(rs.getDouble("beliluar"),100)),rs.getString("nama"),"","",rs.getDouble("stok")});
                            }
                        }else if(Jeniskelas.getSelectedItem().equals("Karyawan")){
                            while(rs.next()){
                                tabMode.addRow(new Object[]{"",rs.getString("kode_brng"),rs.getString("nama_brng"),rs.getString("kode_sat"),"",rs.getString("letak_barang"),Valid.SetAngka2(Valid.roundUp(rs.getDouble("karyawan"),100)),rs.getString("nama"),"","",rs.getDouble("stok")});
                            }
                        }else if(Jeniskelas.getSelectedItem().equals("Harga Beli")){
                            while(rs.next()){
                                tabMode.addRow(new Object[]{"",rs.getString("kode_brng"),rs.getString("nama_brng"),rs.getString("kode_sat"),"",rs.getString("letak_barang"),Valid.SetAngka2(Valid.roundUp(rs.getDouble("h_beli"),100)),rs.getString("nama"),"","",rs.getDouble("stok")});
                            }
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Notif obat2 : "+e);
                } finally{
                    if(rs!=null){
                        rs.close();
                    }
                    if(psobat!=null){
                        psobat.close();
                    }
                }
            } 
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }

    public void emptTeks() {
        TCari.setText("");
        TCari.requestFocus();
    }

    public JTable getTable(){
        return tbKamar;
    }
    
    public void isCek(){        
        BtnSimpan.setEnabled(akses.getresep_pulang());
        if(akses.getakses_depo_obat()==true){
            kdgudang.setEditable(true);
            nmgudang.setEditable(true);
            BtnGudang.setEnabled(true);
        }else{
            kdgudang.setEditable(false);
            nmgudang.setEditable(false);
            BtnGudang.setEnabled(false);
        }
    }
    
    public void setNoRm(String norwt,String norm,String pasien,String penyakit, String tanggal, String jam) {    
        kdgudang.setText(akses.getkdbangsal());
        nmgudang.setText(Sequel.cariIsi("select bangsal.nm_bangsal from bangsal where bangsal.kd_bangsal=?",akses.getkdbangsal()));
        TKdPny.setText(penyakit);
        TNoRw.setText(norwt);
        TNoRM.setText(norm);
        TPasien.setText(pasien);
        Tanggal.setText(Valid.SetTgl(tanggal));
        Jam.setText(jam);
        KdPj.setText(Sequel.cariIsi("select kd_pj from reg_periksa where no_rawat=?",norwt));
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
        this.nopermintaan="";
    }
    
    public void tampil2(String nopermintaan) {
        this.nopermintaan=nopermintaan;
        Valid.tabelKosong(tabMode);
        try{
            if(aktifkanbatch.equals("yes")){
                if(kenaikan>0){
                    psobat=koneksi.prepareStatement(
                            " select data_batch.kode_brng, databarang.nama_brng,jenis.nama, databarang.kode_sat,(data_batch.h_beli+(data_batch.h_beli*?)) as harga,gudangbarang.stok,"+
                            " databarang.letak_barang,data_batch.no_batch,data_batch.no_faktur,detail_permintaan_resep_pulang.jml,detail_permintaan_resep_pulang.dosis, "+
                            " if(gudangbarang.stok>detail_permintaan_resep_pulang.jml,detail_permintaan_resep_pulang.jml,gudangbarang.stok) as sisa "+
                            " from data_batch inner join databarang on data_batch.kode_brng=databarang.kode_brng inner join jenis on databarang.kdjns=jenis.kdjns "+
                            " inner join gudangbarang on gudangbarang.kode_brng=data_batch.kode_brng and gudangbarang.no_batch=data_batch.no_batch and gudangbarang.no_faktur=data_batch.no_faktur "+
                            " inner join detail_permintaan_resep_pulang on detail_permintaan_resep_pulang.kode_brng=databarang.kode_brng "+
                            " where gudangbarang.stok>0 and gudangbarang.kd_bangsal=? and detail_permintaan_resep_pulang.no_permintaan=? order by databarang.nama_brng");  
                }else{
                    psobat=koneksi.prepareStatement(
                            " select data_batch.kode_brng, databarang.nama_brng,jenis.nama, databarang.kode_sat,data_batch.kelas1,data_batch.kelas2,data_batch.kelas3,data_batch.utama,"+
                            " data_batch.vip,data_batch.vvip,data_batch.beliluar,data_batch.karyawan,data_batch.h_beli,databarang.letak_barang,gudangbarang.stok,data_batch.no_batch,"+
                            " data_batch.no_faktur,detail_permintaan_resep_pulang.jml,detail_permintaan_resep_pulang.dosis, "+
                            " if(gudangbarang.stok>detail_permintaan_resep_pulang.jml,detail_permintaan_resep_pulang.jml,gudangbarang.stok) as sisa "+
                            " from data_batch inner join databarang on data_batch.kode_brng=databarang.kode_brng inner join jenis on databarang.kdjns=jenis.kdjns "+
                            " inner join gudangbarang on gudangbarang.kode_brng=data_batch.kode_brng and gudangbarang.no_batch=data_batch.no_batch and gudangbarang.no_faktur=data_batch.no_faktur "+
                            " inner join detail_permintaan_resep_pulang on detail_permintaan_resep_pulang.kode_brng=databarang.kode_brng "+
                            " where gudangbarang.stok>0 and gudangbarang.kd_bangsal=? and detail_permintaan_resep_pulang.no_permintaan=? order by databarang.nama_brng"); 
                }   

                try {
                    if(kenaikan>0){
                        psobat.setDouble(1,kenaikan);
                        psobat.setString(2,kdgudang.getText());
                        psobat.setString(3,nopermintaan);
                        rs=psobat.executeQuery();
                        while(rs.next()){
                            if(rs.getDouble("jml")>rs.getDouble("stok")){
                                JOptionPane.showMessageDialog(rootPane,"Maaf stok tidak mencukupi..!!");
                            }
                            tabMode.addRow(new Object[]{rs.getString("sisa"),rs.getString("kode_brng"),rs.getString("nama_brng"),rs.getString("kode_sat"),rs.getString("dosis"),rs.getString("letak_barang"),Valid.SetAngka2(Valid.roundUp(rs.getDouble("harga"),100)),rs.getString("nama"),rs.getString("no_batch"),rs.getString("no_faktur"),rs.getDouble("stok")});
                        }
                    }else{
                        psobat.setString(1,kdgudang.getText());
                        psobat.setString(2,nopermintaan);
                        rs=psobat.executeQuery();
                        if(Jeniskelas.getSelectedItem().equals("Kelas 1")){
                            while(rs.next()){
                                if(rs.getDouble("jml")>rs.getDouble("stok")){
                                    JOptionPane.showMessageDialog(rootPane,"Maaf stok tidak mencukupi..!!");
                                }
                                tabMode.addRow(new Object[]{rs.getString("sisa"),rs.getString("kode_brng"),rs.getString("nama_brng"),rs.getString("kode_sat"),rs.getString("dosis"),rs.getString("letak_barang"),Valid.SetAngka2(Valid.roundUp(rs.getDouble("kelas1"),100)),rs.getString("nama"),rs.getString("no_batch"),rs.getString("no_faktur"),rs.getDouble("stok")});
                            }
                        }else if(Jeniskelas.getSelectedItem().equals("Kelas 2")){
                            while(rs.next()){
                                if(rs.getDouble("jml")>rs.getDouble("stok")){
                                    JOptionPane.showMessageDialog(rootPane,"Maaf stok tidak mencukupi..!!");
                                }
                                tabMode.addRow(new Object[]{rs.getString("sisa"),rs.getString("kode_brng"),rs.getString("nama_brng"),rs.getString("kode_sat"),rs.getString("dosis"),rs.getString("letak_barang"),Valid.SetAngka2(Valid.roundUp(rs.getDouble("kelas2"),100)),rs.getString("nama"),rs.getString("no_batch"),rs.getString("no_faktur"),rs.getDouble("stok")});
                            }
                        }else if(Jeniskelas.getSelectedItem().equals("Kelas 3")){
                            while(rs.next()){
                                if(rs.getDouble("jml")>rs.getDouble("stok")){
                                    JOptionPane.showMessageDialog(rootPane,"Maaf stok tidak mencukupi..!!");
                                }
                                tabMode.addRow(new Object[]{rs.getString("sisa"),rs.getString("kode_brng"),rs.getString("nama_brng"),rs.getString("kode_sat"),rs.getString("dosis"),rs.getString("letak_barang"),Valid.SetAngka2(Valid.roundUp(rs.getDouble("kelas3"),100)),rs.getString("nama"),rs.getString("no_batch"),rs.getString("no_faktur"),rs.getDouble("stok")});
                            }
                        }else if(Jeniskelas.getSelectedItem().equals("Utama")){
                            while(rs.next()){
                                if(rs.getDouble("jml")>rs.getDouble("stok")){
                                    JOptionPane.showMessageDialog(rootPane,"Maaf stok tidak mencukupi..!!");
                                }
                                tabMode.addRow(new Object[]{rs.getString("sisa"),rs.getString("kode_brng"),rs.getString("nama_brng"),rs.getString("kode_sat"),rs.getString("dosis"),rs.getString("letak_barang"),Valid.SetAngka2(Valid.roundUp(rs.getDouble("utama"),100)),rs.getString("nama"),rs.getString("no_batch"),rs.getString("no_faktur"),rs.getDouble("stok")});
                            }
                        }else if(Jeniskelas.getSelectedItem().equals("VIP")){
                            while(rs.next()){
                                if(rs.getDouble("jml")>rs.getDouble("stok")){
                                    JOptionPane.showMessageDialog(rootPane,"Maaf stok tidak mencukupi..!!");
                                }
                                tabMode.addRow(new Object[]{rs.getString("sisa"),rs.getString("kode_brng"),rs.getString("nama_brng"),rs.getString("kode_sat"),rs.getString("dosis"),rs.getString("letak_barang"),Valid.SetAngka2(Valid.roundUp(rs.getDouble("vip"),100)),rs.getString("nama"),rs.getString("no_batch"),rs.getString("no_faktur"),rs.getDouble("stok")});
                            }
                         }else if(Jeniskelas.getSelectedItem().equals("VVIP")){
                            while(rs.next()){
                                if(rs.getDouble("jml")>rs.getDouble("stok")){
                                    JOptionPane.showMessageDialog(rootPane,"Maaf stok tidak mencukupi..!!");
                                }
                                tabMode.addRow(new Object[]{rs.getString("sisa"),rs.getString("kode_brng"),rs.getString("nama_brng"),rs.getString("kode_sat"),rs.getString("dosis"),rs.getString("letak_barang"),Valid.SetAngka2(Valid.roundUp(rs.getDouble("vvip"),100)),rs.getString("nama"),rs.getString("no_batch"),rs.getString("no_faktur"),rs.getDouble("stok")});
                            }
                        }else if(Jeniskelas.getSelectedItem().equals("Beli Luar")){
                            while(rs.next()){
                                if(rs.getDouble("jml")>rs.getDouble("stok")){
                                    JOptionPane.showMessageDialog(rootPane,"Maaf stok tidak mencukupi..!!");
                                }
                                tabMode.addRow(new Object[]{rs.getString("sisa"),rs.getString("kode_brng"),rs.getString("nama_brng"),rs.getString("kode_sat"),rs.getString("dosis"),rs.getString("letak_barang"),Valid.SetAngka2(Valid.roundUp(rs.getDouble("beliluar"),100)),rs.getString("nama"),rs.getString("no_batch"),rs.getString("no_faktur"),rs.getDouble("stok")});
                            }
                        }else if(Jeniskelas.getSelectedItem().equals("Karyawan")){
                            while(rs.next()){
                                if(rs.getDouble("jml")>rs.getDouble("stok")){
                                    JOptionPane.showMessageDialog(rootPane,"Maaf stok tidak mencukupi..!!");
                                }
                                tabMode.addRow(new Object[]{rs.getString("sisa"),rs.getString("kode_brng"),rs.getString("nama_brng"),rs.getString("kode_sat"),rs.getString("dosis"),rs.getString("letak_barang"),Valid.SetAngka2(Valid.roundUp(rs.getDouble("karyawan"),100)),rs.getString("nama"),rs.getString("no_batch"),rs.getString("no_faktur"),rs.getDouble("stok")});
                            }
                        }else if(Jeniskelas.getSelectedItem().equals("Harga Beli")){
                            while(rs.next()){
                                if(rs.getDouble("jml")>rs.getDouble("stok")){
                                    JOptionPane.showMessageDialog(rootPane,"Maaf stok tidak mencukupi..!!");
                                }
                                tabMode.addRow(new Object[]{rs.getString("sisa"),rs.getString("kode_brng"),rs.getString("nama_brng"),rs.getString("kode_sat"),rs.getString("dosis"),rs.getString("letak_barang"),Valid.SetAngka2(Valid.roundUp(rs.getDouble("h_beli"),100)),rs.getString("nama"),rs.getString("no_batch"),rs.getString("no_faktur"),rs.getDouble("stok")});
                            }
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Notif obat2 : "+e);
                } finally{
                    if(rs!=null){
                        rs.close();
                    }
                    if(psobat!=null){
                        psobat.close();
                    }
                }
            }else{
                if(kenaikan>0){
                    psobat=koneksi.prepareStatement(
                            " select databarang.kode_brng, databarang.nama_brng,jenis.nama, databarang.kode_sat,(databarang.h_beli+(databarang.h_beli*?)) as harga,gudangbarang.stok,"+
                            " databarang.letak_barang,detail_permintaan_resep_pulang.jml,detail_permintaan_resep_pulang.dosis, "+
                            " if(gudangbarang.stok>detail_permintaan_resep_pulang.jml,detail_permintaan_resep_pulang.jml,gudangbarang.stok) as sisa "+
                            " from databarang inner join jenis on databarang.kdjns=jenis.kdjns inner join gudangbarang on databarang.kode_brng=gudangbarang.kode_brng "+
                            " inner join detail_permintaan_resep_pulang on detail_permintaan_resep_pulang.kode_brng=databarang.kode_brng "+
                            " where gudangbarang.no_batch='' and gudangbarang.no_faktur='' and gudangbarang.stok>0 and gudangbarang.kd_bangsal=? "+
                            " and databarang.status='1' and detail_permintaan_resep_pulang.no_permintaan=? order by databarang.nama_brng");  
                }else{
                    psobat=koneksi.prepareStatement(
                            " select databarang.kode_brng, databarang.nama_brng,jenis.nama, databarang.kode_sat,databarang.kelas1,databarang.kelas2,databarang.kelas3,databarang.utama,"+
                            " databarang.vip,databarang.vvip,databarang.beliluar,databarang.karyawan,databarang.h_beli,databarang.letak_barang,gudangbarang.stok,"+
                            " detail_permintaan_resep_pulang.jml,detail_permintaan_resep_pulang.dosis, "+
                            " if(gudangbarang.stok>detail_permintaan_resep_pulang.jml,detail_permintaan_resep_pulang.jml,gudangbarang.stok) as sisa "+
                            " from databarang inner join jenis on databarang.kdjns=jenis.kdjns inner join gudangbarang on databarang.kode_brng=gudangbarang.kode_brng "+
                            " inner join detail_permintaan_resep_pulang on detail_permintaan_resep_pulang.kode_brng=databarang.kode_brng "+
                            " where gudangbarang.no_batch='' and gudangbarang.no_faktur='' and gudangbarang.stok>0 and gudangbarang.kd_bangsal=? and databarang.status='1' "+
                            " and detail_permintaan_resep_pulang.no_permintaan=? order by databarang.nama_brng"); 
                }   

                try {
                    if(kenaikan>0){
                        psobat.setDouble(1,kenaikan);
                        psobat.setString(2,kdgudang.getText());
                        psobat.setString(3,nopermintaan);
                        rs=psobat.executeQuery();
                        while(rs.next()){
                            tabMode.addRow(new Object[]{rs.getString("sisa"),rs.getString("kode_brng"),rs.getString("nama_brng"),rs.getString("kode_sat"),rs.getString("dosis"),rs.getString("letak_barang"),Valid.SetAngka2(Valid.roundUp(rs.getDouble("harga"),100)),rs.getString("nama"),"","",rs.getDouble("stok")});
                        }
                    }else{
                        psobat.setString(1,kdgudang.getText());
                        psobat.setString(2,nopermintaan);
                        rs=psobat.executeQuery();
                        if(Jeniskelas.getSelectedItem().equals("Kelas 1")){
                            while(rs.next()){
                                tabMode.addRow(new Object[]{rs.getString("sisa"),rs.getString("kode_brng"),rs.getString("nama_brng"),rs.getString("kode_sat"),rs.getString("dosis"),rs.getString("letak_barang"),Valid.SetAngka2(Valid.roundUp(rs.getDouble("kelas1"),100)),rs.getString("nama"),"","",rs.getDouble("stok")});
                            }
                        }else if(Jeniskelas.getSelectedItem().equals("Kelas 2")){
                            while(rs.next()){
                                tabMode.addRow(new Object[]{rs.getString("sisa"),rs.getString("kode_brng"),rs.getString("nama_brng"),rs.getString("kode_sat"),rs.getString("dosis"),rs.getString("letak_barang"),Valid.SetAngka2(Valid.roundUp(rs.getDouble("kelas2"),100)),rs.getString("nama"),"","",rs.getDouble("stok")});
                            }
                        }else if(Jeniskelas.getSelectedItem().equals("Kelas 3")){
                            while(rs.next()){
                                tabMode.addRow(new Object[]{rs.getString("sisa"),rs.getString("kode_brng"),rs.getString("nama_brng"),rs.getString("kode_sat"),rs.getString("dosis"),rs.getString("letak_barang"),Valid.SetAngka2(Valid.roundUp(rs.getDouble("kelas3"),100)),rs.getString("nama"),"","",rs.getDouble("stok")});
                            }
                        }else if(Jeniskelas.getSelectedItem().equals("Utama")){
                            while(rs.next()){
                                tabMode.addRow(new Object[]{rs.getString("sisa"),rs.getString("kode_brng"),rs.getString("nama_brng"),rs.getString("kode_sat"),rs.getString("dosis"),rs.getString("letak_barang"),Valid.SetAngka2(Valid.roundUp(rs.getDouble("utama"),100)),rs.getString("nama"),"","",rs.getDouble("stok")});
                            }
                        }else if(Jeniskelas.getSelectedItem().equals("VIP")){
                            while(rs.next()){
                                tabMode.addRow(new Object[]{rs.getString("sisa"),rs.getString("kode_brng"),rs.getString("nama_brng"),rs.getString("kode_sat"),rs.getString("dosis"),rs.getString("letak_barang"),Valid.SetAngka2(Valid.roundUp(rs.getDouble("vip"),100)),rs.getString("nama"),"","",rs.getDouble("stok")});
                            }
                         }else if(Jeniskelas.getSelectedItem().equals("VVIP")){
                            while(rs.next()){
                                tabMode.addRow(new Object[]{rs.getString("sisa"),rs.getString("kode_brng"),rs.getString("nama_brng"),rs.getString("kode_sat"),rs.getString("dosis"),rs.getString("letak_barang"),Valid.SetAngka2(Valid.roundUp(rs.getDouble("vvip"),100)),rs.getString("nama"),"","",rs.getDouble("stok")});
                            }
                        }else if(Jeniskelas.getSelectedItem().equals("Beli Luar")){
                            while(rs.next()){
                                tabMode.addRow(new Object[]{rs.getString("sisa"),rs.getString("kode_brng"),rs.getString("nama_brng"),rs.getString("kode_sat"),rs.getString("dosis"),rs.getString("letak_barang"),Valid.SetAngka2(Valid.roundUp(rs.getDouble("beliluar"),100)),rs.getString("nama"),"","",rs.getDouble("stok")});
                            }
                        }else if(Jeniskelas.getSelectedItem().equals("Karyawan")){
                            while(rs.next()){
                                tabMode.addRow(new Object[]{rs.getString("sisa"),rs.getString("kode_brng"),rs.getString("nama_brng"),rs.getString("kode_sat"),rs.getString("dosis"),rs.getString("letak_barang"),Valid.SetAngka2(Valid.roundUp(rs.getDouble("karyawan"),100)),rs.getString("nama"),"","",rs.getDouble("stok")});
                            }
                        }else if(Jeniskelas.getSelectedItem().equals("Harga Beli")){
                            while(rs.next()){
                                tabMode.addRow(new Object[]{rs.getString("sisa"),rs.getString("kode_brng"),rs.getString("nama_brng"),rs.getString("kode_sat"),rs.getString("dosis"),rs.getString("letak_barang"),Valid.SetAngka2(Valid.roundUp(rs.getDouble("h_beli"),100)),rs.getString("nama"),"","",rs.getDouble("stok")});
                            }
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Notif obat2 : "+e);
                } finally{
                    if(rs!=null){
                        rs.close();
                    }
                    if(psobat!=null){
                        psobat.close();
                    }
                }
            } 
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }
    
    private void getData() {
        int row=tbKamar.getSelectedRow();
        if(kdgudang.getText().trim().equals("")){
             Valid.textKosong(TCari,"Asal Stok");
        }else if(row!= -1){            
             if(!tabMode.getValueAt(row,0).toString().equals("")){
                try {
                    if(Double.parseDouble(tabMode.getValueAt(row,0).toString())>0){
                        stokbarang=0;   
                        if(aktifkanbatch.equals("yes")){
                            psobat=koneksi.prepareStatement("select ifnull(gudangbarang.stok,'0') from gudangbarang where gudangbarang.kd_bangsal=? and gudangbarang.kode_brng=? and gudangbarang.no_batch=? and gudangbarang.no_faktur=?");
                            try {
                                psobat.setString(1,kdgudang.getText());
                                psobat.setString(2,tbKamar.getValueAt(row,1).toString());
                                psobat.setString(3,tbKamar.getValueAt(row,8).toString());
                                psobat.setString(4,tbKamar.getValueAt(row,9).toString());
                                rs=psobat.executeQuery();
                                if(rs.next()){
                                    stokbarang=rs.getDouble(1);
                                }
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs!=null){
                                    rs.close();
                                }
                                if(psobat!=null){
                                    psobat.close();
                                }
                            }  
                        }else{
                            psobat=koneksi.prepareStatement("select ifnull(gudangbarang.stok,'0') from gudangbarang where gudangbarang.kd_bangsal=? and gudangbarang.kode_brng=? and gudangbarang.no_batch='' and gudangbarang.no_faktur=''");
                            try {
                                psobat.setString(1,kdgudang.getText());
                                psobat.setString(2,tbKamar.getValueAt(row,1).toString());
                                rs=psobat.executeQuery();
                                if(rs.next()){
                                    stokbarang=rs.getDouble(1);
                                }
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs!=null){
                                    rs.close();
                                }
                                if(psobat!=null){
                                    psobat.close();
                                }
                            }  
                        }   

                        tbKamar.setValueAt(stokbarang,row,10);
                        y=0;
                        try {
                            y=Double.parseDouble(tabMode.getValueAt(row,0).toString());
                        } catch (Exception e) {
                            y=0;
                        }
                        
                        if(stokbarang<y){
                              JOptionPane.showMessageDialog(null,"Maaf, Stok tidak cukup....!!!");
                              TCari.requestFocus();
                              tabMode.setValueAt("", row,0);
                        } 
                    }
                } catch (Exception e) {
                    tabMode.setValueAt("", row,0);
                }                                       
             } 
        }
    }
}

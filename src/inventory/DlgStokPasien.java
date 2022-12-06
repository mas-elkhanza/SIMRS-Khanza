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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author dosen
 */
public final class DlgStokPasien extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private riwayatobat Trackobat=new riwayatobat();
    private validasi Valid=new validasi();  
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement pstampil;
    private ResultSet rstampil;
    private String aktifkanbatch="no";
    private boolean sukses=true;
    /** Creates new form DlgPenyakit
     * @param parent
     * @param modal */
    public DlgStokPasien(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(10,2);
        setSize(628,674);

        Object[] row={
            "Tanggal","Jam","No.Rawat","Pasien","Barang","Jml","Asal Stok","Kode Barang","Kode Bangsal","No.Batch","No.Faktur","Aturan Pakai",
            "00","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23"
        };
        tabMode=new DefaultTableModel(null,row){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbKamar.setModel(tabMode);
        //tbPenyakit.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbPenyakit.getBackground()));
        tbKamar.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbKamar.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 36; i++) {
            TableColumn column = tbKamar.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(65);
            }else if(i==1){
                column.setPreferredWidth(55);
            }else if(i==2){
                column.setPreferredWidth(105);
            }else if(i==3){
                column.setPreferredWidth(200);
            }else if(i==4){
                column.setPreferredWidth(200);
            }else if(i==5){
                column.setPreferredWidth(35);
            }else if(i==6){
                column.setPreferredWidth(120);
            }else if(i==9){
                column.setPreferredWidth(70);
            }else if(i==10){
                column.setPreferredWidth(100);
            }else if(i==11){
                column.setPreferredWidth(100);
            }else if(i==12){
                column.setPreferredWidth(20);
            }else if(i==13){
                column.setPreferredWidth(20);
            }else if(i==14){
                column.setPreferredWidth(20);
            }else if(i==15){
                column.setPreferredWidth(20);
            }else if(i==16){
                column.setPreferredWidth(20);
            }else if(i==17){
                column.setPreferredWidth(20);
            }else if(i==18){
                column.setPreferredWidth(20);
            }else if(i==19){
                column.setPreferredWidth(20);
            }else if(i==20){
                column.setPreferredWidth(20);
            }else if(i==21){
                column.setPreferredWidth(20);
            }else if(i==22){
                column.setPreferredWidth(20);
            }else if(i==23){
                column.setPreferredWidth(20);
            }else if(i==24){
                column.setPreferredWidth(20);
            }else if(i==25){
                column.setPreferredWidth(20);
            }else if(i==26){
                column.setPreferredWidth(20);
            }else if(i==27){
                column.setPreferredWidth(20);
            }else if(i==28){
                column.setPreferredWidth(20);
            }else if(i==29){
                column.setPreferredWidth(20);
            }else if(i==30){
                column.setPreferredWidth(20);
            }else if(i==31){
                column.setPreferredWidth(20);
            }else if(i==32){
                column.setPreferredWidth(20);
            }else if(i==33){
                column.setPreferredWidth(20);
            }else if(i==34){
                column.setPreferredWidth(20);
            }else if(i==35){
                column.setPreferredWidth(20);
            }else{
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }
        }
        tbKamar.setDefaultRenderer(Object.class, new WarnaTable());
                
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
        
        try {
            aktifkanbatch = koneksiDB.AKTIFKANBATCHOBAT();
        } catch (Exception e) {
            System.out.println("E : "+e);
            aktifkanbatch = "no";
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

        Kd2 = new widget.TextBox();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        ppResepObat = new javax.swing.JMenuItem();
        ppResepObat1 = new javax.swing.JMenuItem();
        ppResepObat2 = new javax.swing.JMenuItem();
        ppLabelObat = new javax.swing.JMenuItem();
        ppLabelObat2 = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbKamar = new widget.Table();
        jPanel1 = new javax.swing.JPanel();
        panelisi3 = new widget.panelisi();
        label11 = new widget.Label();
        Tgl1 = new widget.Tanggal();
        label19 = new widget.Label();
        Tgl2 = new widget.Tanggal();
        label9 = new widget.Label();
        TCari = new widget.TextBox();
        BtnAll = new widget.Button();
        BtnCari = new widget.Button();
        panelisi1 = new widget.panelisi();
        BtnHapus = new widget.Button();
        BtnPrint = new widget.Button();
        label10 = new widget.Label();
        LCount = new widget.Label();
        BtnKeluar = new widget.Button();

        Kd2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Kd2.setHighlighter(null);
        Kd2.setName("Kd2"); // NOI18N
        Kd2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Kd2KeyPressed(evt);
            }
        });

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        ppResepObat.setBackground(new java.awt.Color(255, 255, 254));
        ppResepObat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppResepObat.setForeground(new java.awt.Color(50, 50, 50));
        ppResepObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppResepObat.setText("Cetak Aturan Pakai Model 1");
        ppResepObat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppResepObat.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppResepObat.setName("ppResepObat"); // NOI18N
        ppResepObat.setPreferredSize(new java.awt.Dimension(190, 25));
        ppResepObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppResepObatActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppResepObat);

        ppResepObat1.setBackground(new java.awt.Color(255, 255, 254));
        ppResepObat1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppResepObat1.setForeground(new java.awt.Color(50, 50, 50));
        ppResepObat1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppResepObat1.setText("Cetak Aturan Pakai Model 2");
        ppResepObat1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppResepObat1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppResepObat1.setName("ppResepObat1"); // NOI18N
        ppResepObat1.setPreferredSize(new java.awt.Dimension(190, 25));
        ppResepObat1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppResepObat1ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppResepObat1);

        ppResepObat2.setBackground(new java.awt.Color(255, 255, 254));
        ppResepObat2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppResepObat2.setForeground(new java.awt.Color(50, 50, 50));
        ppResepObat2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppResepObat2.setText("Cetak Aturan Pakai Model 3");
        ppResepObat2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppResepObat2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppResepObat2.setName("ppResepObat2"); // NOI18N
        ppResepObat2.setPreferredSize(new java.awt.Dimension(190, 25));
        ppResepObat2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppResepObat2ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppResepObat2);

        ppLabelObat.setBackground(new java.awt.Color(255, 255, 254));
        ppLabelObat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppLabelObat.setForeground(new java.awt.Color(50, 50, 50));
        ppLabelObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppLabelObat.setText("Cetak Label Obat");
        ppLabelObat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppLabelObat.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppLabelObat.setName("ppLabelObat"); // NOI18N
        ppLabelObat.setPreferredSize(new java.awt.Dimension(190, 25));
        ppLabelObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppLabelObatActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppLabelObat);

        ppLabelObat2.setBackground(new java.awt.Color(255, 255, 254));
        ppLabelObat2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppLabelObat2.setForeground(new java.awt.Color(50, 50, 50));
        ppLabelObat2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppLabelObat2.setText("Cetak Label Obat 2");
        ppLabelObat2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppLabelObat2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppLabelObat2.setName("ppLabelObat2"); // NOI18N
        ppLabelObat2.setPreferredSize(new java.awt.Dimension(190, 25));
        ppLabelObat2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppLabelObat2ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppLabelObat2);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Pemberian Stok Obat, Alkes & BHP Medis Pasien Di Ranap ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setComponentPopupMenu(jPopupMenu1);
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbKamar.setAutoCreateRowSorter(true);
        tbKamar.setComponentPopupMenu(jPopupMenu1);
        tbKamar.setName("tbKamar"); // NOI18N
        Scroll.setViewportView(tbKamar);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(816, 100));
        jPanel1.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(100, 44));
        panelisi3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(label11);

        Tgl1.setDisplayFormat("dd-MM-yyyy");
        Tgl1.setName("Tgl1"); // NOI18N
        Tgl1.setPreferredSize(new java.awt.Dimension(95, 23));
        panelisi3.add(Tgl1);

        label19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label19.setText("s.d.");
        label19.setName("label19"); // NOI18N
        label19.setPreferredSize(new java.awt.Dimension(30, 23));
        panelisi3.add(label19);

        Tgl2.setDisplayFormat("dd-MM-yyyy");
        Tgl2.setName("Tgl2"); // NOI18N
        Tgl2.setPreferredSize(new java.awt.Dimension(95, 23));
        panelisi3.add(Tgl2);

        label9.setText("Key Word :");
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(90, 23));
        panelisi3.add(label9);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(250, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelisi3.add(TCari);

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('3');
        BtnAll.setToolTipText("Alt+3");
        BtnAll.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
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

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('2');
        BtnCari.setToolTipText("Alt+2");
        BtnCari.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
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

        jPanel1.add(panelisi3, java.awt.BorderLayout.PAGE_START);

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

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
        panelisi1.add(BtnHapus);

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
        panelisi1.add(BtnPrint);

        label10.setText("Record :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(95, 30));
        panelisi1.add(label10);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(300, 30));
        panelisi1.add(LCount);

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
        panelisi1.add(BtnKeluar);

        jPanel1.add(panelisi1, java.awt.BorderLayout.CENTER);

        internalFrame1.add(jPanel1, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if(tbKamar.getSelectedRow()!= -1){
            if(Sequel.cariInteger(
                    "select count(kode_brng) from detail_pemberian_obat where status='Ranap' and "+
                    "kode_brng='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),7).toString()+"' and "+
                    "no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and "+
                    "no_batch='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),9).toString()+"' and "+
                    "no_faktur='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),10).toString()+"' ")>0){
                JOptionPane.showMessageDialog(null,"Maaf, data Obat/Alkes/BHP sudah digunakan dalam pemberian obat.\nSilahkan hapus data Obat/Alkes/BHP yang ada dalam pemberian obat terlebih dahulu..!!!");
            }else{            
                if(Sequel.cariRegistrasi(tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString())>0){
                    JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi, data tidak boleh dihapus.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                    TCari.requestFocus();
                }else{
                    Sequel.queryu("delete from stok_obat_pasien where tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' "+
                                  "and jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' "+
                                  "and no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' "+
                                  "and kode_brng='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),7).toString()+"' "+
                                  "and kd_bangsal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),8).toString()+"' "+
                                  "and no_batch='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),9).toString()+"' "+
                                  "and no_faktur='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),10).toString()+"' ");
                    if(aktifkanbatch.equals("yes")){
                        Sequel.mengedit("data_batch","no_batch=? and no_faktur=? and kode_brng=?","sisa=sisa+?",4,new String[]{
                            ""+tbKamar.getValueAt(tbKamar.getSelectedRow(),5).toString(),tbKamar.getValueAt(tbKamar.getSelectedRow(),9).toString(),
                            tbKamar.getValueAt(tbKamar.getSelectedRow(),10).toString(),tbKamar.getValueAt(tbKamar.getSelectedRow(),7).toString()
                        });
                        Trackobat.catatRiwayat(tbKamar.getValueAt(tbKamar.getSelectedRow(),7).toString(),Valid.SetAngka(tbKamar.getValueAt(tbKamar.getSelectedRow(),5).toString()),0,"Stok Pasien Ranap",akses.getkode(),tbKamar.getValueAt(tbKamar.getSelectedRow(),8).toString(),"Hapus",tbKamar.getValueAt(tbKamar.getSelectedRow(),9).toString(),tbKamar.getValueAt(tbKamar.getSelectedRow(),10).toString(),tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+" "+tbKamar.getValueAt(tbKamar.getSelectedRow(),3).toString());
                        Sequel.menyimpan("gudangbarang","'"+tbKamar.getValueAt(tbKamar.getSelectedRow(),7).toString()+"','"+tbKamar.getValueAt(tbKamar.getSelectedRow(),8).toString()+"','"+tbKamar.getValueAt(tbKamar.getSelectedRow(),5).toString()+"','"+tbKamar.getValueAt(tbKamar.getSelectedRow(),9).toString()+"','"+tbKamar.getValueAt(tbKamar.getSelectedRow(),10).toString()+"'", 
                                     "stok=stok+'"+tbKamar.getValueAt(tbKamar.getSelectedRow(),5).toString()+"'","kode_brng='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),7).toString()+"' and kd_bangsal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),8).toString()+"' and no_batch='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),9).toString()+"' and no_faktur='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),10).toString()+"'");
                    }else{
                        Trackobat.catatRiwayat(tbKamar.getValueAt(tbKamar.getSelectedRow(),7).toString(),Valid.SetAngka(tbKamar.getValueAt(tbKamar.getSelectedRow(),5).toString()),0,"Stok Pasien Ranap",akses.getkode(),tbKamar.getValueAt(tbKamar.getSelectedRow(),8).toString(),"Hapus","","",tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+" "+tbKamar.getValueAt(tbKamar.getSelectedRow(),3).toString());
                        Sequel.menyimpan("gudangbarang","'"+tbKamar.getValueAt(tbKamar.getSelectedRow(),7).toString()+"','"+tbKamar.getValueAt(tbKamar.getSelectedRow(),8).toString()+"','"+tbKamar.getValueAt(tbKamar.getSelectedRow(),5).toString()+"','',''", 
                                     "stok=stok+'"+tbKamar.getValueAt(tbKamar.getSelectedRow(),5).toString()+"'","kode_brng='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),7).toString()+"' and kd_bangsal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),8).toString()+"' and no_batch='' and no_faktur=''");
                    }
                    BtnCariActionPerformed(evt);
                }
            }    
        }            
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari, BtnAll);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnAll,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        BtnCariActionPerformed(evt);
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            TCari.requestFocus();
        }else if(tabMode.getRowCount()!=0){   
            Map<String, Object> param = new HashMap<>();    
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());   
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            
            if(TCari.getText().trim().equals("")){
                Valid.MyReportqry("rptStokPasien.jasper","report","::[ Pemberian Stok Obat, Alkes & BHP Medis ]::",
                      "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,concat(reg_periksa.no_rkm_medis,' ',pasien.nm_pasien) as namapasien,"+
                      "concat(stok_obat_pasien.kode_brng,' ',databarang.nama_brng) as namabarang, stok_obat_pasien.jumlah, concat(stok_obat_pasien.kd_bangsal,' ',bangsal.nm_bangsal) as namabangsal, "+
                      "stok_obat_pasien.kode_brng,stok_obat_pasien.kd_bangsal,stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai, "+
                      "stok_obat_pasien.jam00,stok_obat_pasien.jam01,stok_obat_pasien.jam02,stok_obat_pasien.jam03,stok_obat_pasien.jam04,stok_obat_pasien.jam05,"+
                      "stok_obat_pasien.jam06,stok_obat_pasien.jam07,stok_obat_pasien.jam08,stok_obat_pasien.jam09,stok_obat_pasien.jam10,stok_obat_pasien.jam11,"+
                      "stok_obat_pasien.jam12,stok_obat_pasien.jam13,stok_obat_pasien.jam14,stok_obat_pasien.jam15,stok_obat_pasien.jam16,stok_obat_pasien.jam17,"+
                      "stok_obat_pasien.jam18,stok_obat_pasien.jam19,stok_obat_pasien.jam20,stok_obat_pasien.jam21,stok_obat_pasien.jam22,stok_obat_pasien.jam23 "+
                      "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                      "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                      "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                      "inner join bangsal on stok_obat_pasien.kd_bangsal=bangsal.kd_bangsal "+
                      "where stok_obat_pasien.tanggal between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' order by stok_obat_pasien.tanggal",param);
            }else{
                Valid.MyReportqry("rptStokPasien.jasper","report","::[ Pemberian Stok Obat, Alkes & BHP Medis ]::",
                      "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,concat(reg_periksa.no_rkm_medis,' ',pasien.nm_pasien) as namapasien,"+
                      "concat(stok_obat_pasien.kode_brng,' ',databarang.nama_brng) as namabarang, stok_obat_pasien.jumlah, concat(stok_obat_pasien.kd_bangsal,' ',bangsal.nm_bangsal) as namabangsal, "+
                      "stok_obat_pasien.kode_brng,stok_obat_pasien.kd_bangsal,stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai, "+
                      "stok_obat_pasien.jam00,stok_obat_pasien.jam01,stok_obat_pasien.jam02,stok_obat_pasien.jam03,stok_obat_pasien.jam04,stok_obat_pasien.jam05,"+
                      "stok_obat_pasien.jam06,stok_obat_pasien.jam07,stok_obat_pasien.jam08,stok_obat_pasien.jam09,stok_obat_pasien.jam10,stok_obat_pasien.jam11,"+
                      "stok_obat_pasien.jam12,stok_obat_pasien.jam13,stok_obat_pasien.jam14,stok_obat_pasien.jam15,stok_obat_pasien.jam16,stok_obat_pasien.jam17,"+
                      "stok_obat_pasien.jam18,stok_obat_pasien.jam19,stok_obat_pasien.jam20,stok_obat_pasien.jam21,stok_obat_pasien.jam22,stok_obat_pasien.jam23 "+
                      "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                      "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                      "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                      "inner join bangsal on stok_obat_pasien.kd_bangsal=bangsal.kd_bangsal "+
                      "where stok_obat_pasien.tanggal between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and stok_obat_pasien.no_rawat like '%"+TCari.getText().trim()+"%' or "+
                      "stok_obat_pasien.tanggal between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and reg_periksa.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                      "stok_obat_pasien.tanggal between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or "+
                      "stok_obat_pasien.tanggal between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and databarang.nama_brng like '%"+TCari.getText().trim()+"%' or "+
                      "stok_obat_pasien.tanggal between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and stok_obat_pasien.no_batch like '%"+TCari.getText().trim()+"%' or "+
                      "stok_obat_pasien.tanggal between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and stok_obat_pasien.no_faktur like '%"+TCari.getText().trim()+"%' or "+
                      "stok_obat_pasien.tanggal between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and bangsal.nm_bangsal like '%"+TCari.getText().trim()+"%' order by stok_obat_pasien.tanggal",param);
            }   
        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnKeluar);
        }
}//GEN-LAST:event_BtnPrintKeyPressed

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            tbKamar.requestFocus();
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

    private void Kd2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kd2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Kd2KeyPressed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnCari, TCari);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        tampil();
    }//GEN-LAST:event_BtnAllActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void ppResepObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppResepObatActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        }else if(tbKamar.getSelectedRow()<= -1){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan pilih data..!!");
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            //"Tanggal","Jam","No.Rawat"
            if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam00='true'")>0){
                param.put("waktu","JAM 00");  
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptItemStokPasien.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                    "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                    "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                    "where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam00='true'",param);
            }   
            if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam01='true'")>0){
                param.put("waktu","JAM 01");  
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptItemStokPasien.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                    "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                    "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                    "where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam01='true'",param);
            }
            if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam02='true'")>0){
                param.put("waktu","JAM 02");  
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptItemStokPasien.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                    "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                    "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                    "where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam02='true'",param);
            }
            if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam03='true'")>0){
                param.put("waktu","JAM 03");  
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptItemStokPasien.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                    "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                    "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                    "where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam03='true'",param);
            }
            if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam04='true'")>0){
                param.put("waktu","JAM 04");  
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptItemStokPasien.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                    "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                    "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                    "where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam04='true'",param);
            }
            if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam05='true'")>0){
                param.put("waktu","JAM 05");  
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptItemStokPasien.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                    "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                    "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                    "where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam05='true'",param);
            }
            if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam06='true'")>0){
                param.put("waktu","JAM 06");  
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptItemStokPasien.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                    "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                    "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                    "where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam06='true'",param);
            }
            if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam07='true'")>0){
                param.put("waktu","JAM 07");  
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptItemStokPasien.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                    "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                    "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                    "where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam07='true'",param);
            }
            if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam08='true'")>0){
                param.put("waktu","JAM 08");  
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptItemStokPasien.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                    "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                    "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                    "where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam08='true'",param);
            }
            if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam09='true'")>0){
                param.put("waktu","JAM 09");  
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptItemStokPasien.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                    "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                    "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                    "where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam09='true'",param);
            }
            if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam10='true'")>0){
                param.put("waktu","JAM 10");  
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptItemStokPasien.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                    "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                    "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                    "where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam10='true'",param);
            }
            if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam11='true'")>0){
                param.put("waktu","JAM 11");  
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptItemStokPasien.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                    "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                    "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                    "where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam11='true'",param);
            }
            if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam12='true'")>0){
                param.put("waktu","JAM 12");  
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptItemStokPasien.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                    "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                    "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                    "where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam12='true'",param);
            }
            if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam13='true'")>0){
                param.put("waktu","JAM 13");  
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptItemStokPasien.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                    "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                    "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                    "where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam13='true'",param);
            }
            if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam14='true'")>0){
                param.put("waktu","JAM 14");  
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptItemStokPasien.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                    "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                    "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                    "where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam14='true'",param);
            }
            if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam15='true'")>0){
                param.put("waktu","JAM 15");  
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptItemStokPasien.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                    "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                    "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                    "where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam15='true'",param);
            }
            if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam16='true'")>0){
                param.put("waktu","JAM 16");  
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptItemStokPasien.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                    "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                    "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                    "where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam16='true'",param);
            }
            if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam17='true'")>0){
                param.put("waktu","JAM 17");  
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptItemStokPasien.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                    "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                    "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                    "where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam17='true'",param);
            }
            if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam18='true'")>0){
                param.put("waktu","JAM 18");  
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptItemStokPasien.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                    "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                    "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                    "where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam18='true'",param);
            }
            if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam19='true'")>0){
                param.put("waktu","JAM 19");  
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptItemStokPasien.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                    "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                    "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                    "where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam19='true'",param);
            }
            if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam20='true'")>0){
                param.put("waktu","JAM 20");  
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptItemStokPasien.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                    "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                    "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                    "where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam20='true'",param);
            }
            if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam21='true'")>0){
                param.put("waktu","JAM 21");  
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptItemStokPasien.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                    "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                    "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                    "where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam21='true'",param);
            }
            if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam22='true'")>0){
                param.put("waktu","JAM 22");  
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptItemStokPasien.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                    "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                    "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                    "where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam22='true'",param);
            }
            if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam23='true'")>0){
                param.put("waktu","JAM 23");  
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptItemStokPasien.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                    "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                    "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                    "where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam23='true'",param);
            }
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_ppResepObatActionPerformed

    private void ppResepObat1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppResepObat1ActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        }else if(tbKamar.getSelectedRow()<= -1){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan pilih data..!!");
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam00='true'")>0){
                param.put("waktu","JAM 00");  
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptItemStokPasien2.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                    "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                    "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                    "where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam00='true'",param);
            }   
            if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam01='true'")>0){
                param.put("waktu","JAM 01");  
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptItemStokPasien2.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                    "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                    "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                    "where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam01='true'",param);
            }
            if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam02='true'")>0){
                param.put("waktu","JAM 02");  
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptItemStokPasien2.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                    "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                    "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                    "where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam02='true'",param);
            }
            if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam03='true'")>0){
                param.put("waktu","JAM 03");  
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptItemStokPasien2.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                    "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                    "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                    "where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam03='true'",param);
            }
            if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam04='true'")>0){
                param.put("waktu","JAM 04");  
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptItemStokPasien2.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                    "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                    "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                    "where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam04='true'",param);
            }
            if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam05='true'")>0){
                param.put("waktu","JAM 05");  
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptItemStokPasien2.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                    "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                    "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                    "where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam05='true'",param);
            }
            if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam06='true'")>0){
                param.put("waktu","JAM 06");  
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptItemStokPasien2.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                    "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                    "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                    "where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam06='true'",param);
            }
            if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam07='true'")>0){
                param.put("waktu","JAM 07");  
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptItemStokPasien2.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                    "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                    "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                    "where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam07='true'",param);
            }
            if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam08='true'")>0){
                param.put("waktu","JAM 08");  
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptItemStokPasien2.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                    "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                    "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                    "where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam08='true'",param);
            }
            if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam09='true'")>0){
                param.put("waktu","JAM 09");  
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptItemStokPasien2.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                    "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                    "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                    "where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam09='true'",param);
            }
            if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam10='true'")>0){
                param.put("waktu","JAM 10");  
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptItemStokPasien2.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                    "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                    "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                    "where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam10='true'",param);
            }
            if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam11='true'")>0){
                param.put("waktu","JAM 11");  
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptItemStokPasien2.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                    "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                    "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                    "where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam11='true'",param);
            }
            if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam12='true'")>0){
                param.put("waktu","JAM 12");  
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptItemStokPasien2.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                    "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                    "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                    "where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam12='true'",param);
            }
            if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam13='true'")>0){
                param.put("waktu","JAM 13");  
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptItemStokPasien2.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                    "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                    "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                    "where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam13='true'",param);
            }
            if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam14='true'")>0){
                param.put("waktu","JAM 14");  
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptItemStokPasien2.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                    "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                    "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                    "where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam14='true'",param);
            }
            if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam15='true'")>0){
                param.put("waktu","JAM 15");  
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptItemStokPasien2.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                    "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                    "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                    "where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam15='true'",param);
            }
            if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam16='true'")>0){
                param.put("waktu","JAM 16");  
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptItemStokPasien2.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                    "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                    "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                    "where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam16='true'",param);
            }
            if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam17='true'")>0){
                param.put("waktu","JAM 17");  
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptItemStokPasien2.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                    "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                    "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                    "where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam17='true'",param);
            }
            if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam18='true'")>0){
                param.put("waktu","JAM 18");  
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptItemStokPasien2.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                    "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                    "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                    "where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam18='true'",param);
            }
            if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam19='true'")>0){
                param.put("waktu","JAM 19");  
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptItemStokPasien2.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                    "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                    "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                    "where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam19='true'",param);
            }
            if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam20='true'")>0){
                param.put("waktu","JAM 20");  
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptItemStokPasien2.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                    "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                    "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                    "where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam20='true'",param);
            }
            if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam21='true'")>0){
                param.put("waktu","JAM 21");  
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptItemStokPasien2.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                    "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                    "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                    "where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam21='true'",param);
            }
            if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam22='true'")>0){
                param.put("waktu","JAM 22");  
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptItemStokPasien2.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                    "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                    "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                    "where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam22='true'",param);
            }
            if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam23='true'")>0){
                param.put("waktu","JAM 23");  
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptItemStokPasien2.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                    "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                    "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                    "where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam23='true'",param);
            }
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_ppResepObat1ActionPerformed

    private void ppResepObat2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppResepObat2ActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        }else if(tbKamar.getSelectedRow()<= -1){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan pilih data..!!");
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam00='true'")>0){
                param.put("waktu","JAM 00");  
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptItemStokPasien3.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                    "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                    "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                    "where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam00='true'",param);
            }   
            if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam01='true'")>0){
                param.put("waktu","JAM 01");  
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptItemStokPasien3.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                    "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                    "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                    "where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam01='true'",param);
            }
            if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam02='true'")>0){
                param.put("waktu","JAM 02");  
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptItemStokPasien3.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                    "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                    "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                    "where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam02='true'",param);
            }
            if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam03='true'")>0){
                param.put("waktu","JAM 03");  
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptItemStokPasien3.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                    "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                    "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                    "where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam03='true'",param);
            }
            if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam04='true'")>0){
                param.put("waktu","JAM 04");  
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptItemStokPasien3.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                    "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                    "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                    "where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam04='true'",param);
            }
            if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam05='true'")>0){
                param.put("waktu","JAM 05");  
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptItemStokPasien3.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                    "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                    "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                    "where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam05='true'",param);
            }
            if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam06='true'")>0){
                param.put("waktu","JAM 06");  
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptItemStokPasien3.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                    "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                    "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                    "where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam06='true'",param);
            }
            if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam07='true'")>0){
                param.put("waktu","JAM 07");  
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptItemStokPasien3.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                    "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                    "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                    "where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam07='true'",param);
            }
            if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam08='true'")>0){
                param.put("waktu","JAM 08");  
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptItemStokPasien3.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                    "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                    "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                    "where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam08='true'",param);
            }
            if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam09='true'")>0){
                param.put("waktu","JAM 09");  
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptItemStokPasien3.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                    "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                    "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                    "where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam09='true'",param);
            }
            if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam10='true'")>0){
                param.put("waktu","JAM 10");  
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptItemStokPasien3.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                    "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                    "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                    "where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam10='true'",param);
            }
            if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam11='true'")>0){
                param.put("waktu","JAM 11");  
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptItemStokPasien3.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                    "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                    "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                    "where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam11='true'",param);
            }
            if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam12='true'")>0){
                param.put("waktu","JAM 12");  
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptItemStokPasien3.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                    "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                    "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                    "where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam12='true'",param);
            }
            if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam13='true'")>0){
                param.put("waktu","JAM 13");  
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptItemStokPasien3.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                    "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                    "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                    "where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam13='true'",param);
            }
            if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam14='true'")>0){
                param.put("waktu","JAM 14");  
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptItemStokPasien3.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                    "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                    "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                    "where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam14='true'",param);
            }
            if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam15='true'")>0){
                param.put("waktu","JAM 15");  
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptItemStokPasien3.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                    "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                    "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                    "where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam15='true'",param);
            }
            if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam16='true'")>0){
                param.put("waktu","JAM 16");  
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptItemStokPasien3.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                    "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                    "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                    "where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam16='true'",param);
            }
            if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam17='true'")>0){
                param.put("waktu","JAM 17");  
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptItemStokPasien3.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                    "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                    "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                    "where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam17='true'",param);
            }
            if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam18='true'")>0){
                param.put("waktu","JAM 18");  
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptItemStokPasien3.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                    "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                    "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                    "where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam18='true'",param);
            }
            if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam19='true'")>0){
                param.put("waktu","JAM 19");  
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptItemStokPasien3.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                    "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                    "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                    "where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam19='true'",param);
            }
            if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam20='true'")>0){
                param.put("waktu","JAM 20");  
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptItemStokPasien3.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                    "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                    "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                    "where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam20='true'",param);
            }
            if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam21='true'")>0){
                param.put("waktu","JAM 21");  
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptItemStokPasien3.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                    "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                    "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                    "where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam21='true'",param);
            }
            if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam22='true'")>0){
                param.put("waktu","JAM 22");  
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptItemStokPasien3.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                    "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                    "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                    "where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam22='true'",param);
            }
            if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam23='true'")>0){
                param.put("waktu","JAM 23");  
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptItemStokPasien3.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                    "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                    "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                    "where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam23='true'",param);
            }
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_ppResepObat2ActionPerformed

    private void ppLabelObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppLabelObatActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptLabelDaftarObatStokPasien.jasper","report","::[ Label Obat Stok Pasien ]::",
                    "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                    "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai,"+
                    "stok_obat_pasien.jam00,stok_obat_pasien.jam01,stok_obat_pasien.jam02,stok_obat_pasien.jam03,stok_obat_pasien.jam04,stok_obat_pasien.jam05,"+
                    "stok_obat_pasien.jam06,stok_obat_pasien.jam07,stok_obat_pasien.jam08,stok_obat_pasien.jam09,stok_obat_pasien.jam10,stok_obat_pasien.jam11,"+
                    "stok_obat_pasien.jam12,stok_obat_pasien.jam13,stok_obat_pasien.jam14,stok_obat_pasien.jam15,stok_obat_pasien.jam16,stok_obat_pasien.jam17,"+
                    "stok_obat_pasien.jam18,stok_obat_pasien.jam19,stok_obat_pasien.jam20,stok_obat_pasien.jam21,stok_obat_pasien.jam22,stok_obat_pasien.jam23 "+
                    "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                    "where  stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"'",param);
            this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_ppLabelObatActionPerformed

    private void ppLabelObat2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppLabelObat2ActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        }else if(tbKamar.getSelectedRow()<= -1){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan pilih data..!!");
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            //"Tanggal","Jam","No.Rawat"
            if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam00='true'")>0){
                param.put("waktu","JAM 00");  
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptLabelDaftarObatStokPasien2.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                    "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                    "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                    "where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam00='true'",param);
            }   
            if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam01='true'")>0){
                param.put("waktu","JAM 01");  
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptLabelDaftarObatStokPasien2.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                    "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                    "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                    "where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam01='true'",param);
            }
            if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam02='true'")>0){
                param.put("waktu","JAM 02");  
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptLabelDaftarObatStokPasien2.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                    "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                    "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                    "where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam02='true'",param);
            }
            if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam03='true'")>0){
                param.put("waktu","JAM 03");  
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptLabelDaftarObatStokPasien2.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                    "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                    "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                    "where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam03='true'",param);
            }
            if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam04='true'")>0){
                param.put("waktu","JAM 04");  
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptLabelDaftarObatStokPasien2.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                    "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                    "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                    "where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam04='true'",param);
            }
            if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam05='true'")>0){
                param.put("waktu","JAM 05");  
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptLabelDaftarObatStokPasien2.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                    "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                    "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                    "where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam05='true'",param);
            }
            if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam06='true'")>0){
                param.put("waktu","JAM 06");  
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptLabelDaftarObatStokPasien2.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                    "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                    "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                    "where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam06='true'",param);
            }
            if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam07='true'")>0){
                param.put("waktu","JAM 07");  
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptLabelDaftarObatStokPasien2.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                    "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                    "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                    "where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam07='true'",param);
            }
            if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam08='true'")>0){
                param.put("waktu","JAM 08");  
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptLabelDaftarObatStokPasien2.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                    "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                    "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                    "where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam08='true'",param);
            }
            if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam09='true'")>0){
                param.put("waktu","JAM 09");  
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptLabelDaftarObatStokPasien2.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                    "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                    "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                    "where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam09='true'",param);
            }
            if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam10='true'")>0){
                param.put("waktu","JAM 10");  
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptLabelDaftarObatStokPasien2.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                    "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                    "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                    "where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam10='true'",param);
            }
            if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam11='true'")>0){
                param.put("waktu","JAM 11");  
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptLabelDaftarObatStokPasien2.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                    "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                    "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                    "where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam11='true'",param);
            }
            if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam12='true'")>0){
                param.put("waktu","JAM 12");  
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptLabelDaftarObatStokPasien2.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                    "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                    "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                    "where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam12='true'",param);
            }
            if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam13='true'")>0){
                param.put("waktu","JAM 13");  
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptLabelDaftarObatStokPasien2.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                    "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                    "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                    "where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam13='true'",param);
            }
            if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam14='true'")>0){
                param.put("waktu","JAM 14");  
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptLabelDaftarObatStokPasien2.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                    "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                    "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                    "where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam14='true'",param);
            }
            if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam15='true'")>0){
                param.put("waktu","JAM 15");  
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptLabelDaftarObatStokPasien2.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                    "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                    "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                    "where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam15='true'",param);
            }
            if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam16='true'")>0){
                param.put("waktu","JAM 16");  
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptLabelDaftarObatStokPasien2.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                    "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                    "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                    "where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam16='true'",param);
            }
            if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam17='true'")>0){
                param.put("waktu","JAM 17");  
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptLabelDaftarObatStokPasien2.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                    "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                    "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                    "where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam17='true'",param);
            }
            if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam18='true'")>0){
                param.put("waktu","JAM 18");  
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptLabelDaftarObatStokPasien2.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                    "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                    "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                    "where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam18='true'",param);
            }
            if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam19='true'")>0){
                param.put("waktu","JAM 19");  
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptLabelDaftarObatStokPasien2.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                    "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                    "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                    "where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam19='true'",param);
            }
            if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam20='true'")>0){
                param.put("waktu","JAM 20");  
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptLabelDaftarObatStokPasien2.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                    "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                    "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                    "where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam20='true'",param);
            }
            if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam21='true'")>0){
                param.put("waktu","JAM 21");  
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptLabelDaftarObatStokPasien2.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                    "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                    "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                    "where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam21='true'",param);
            }
            if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam22='true'")>0){
                param.put("waktu","JAM 22");  
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptLabelDaftarObatStokPasien2.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                    "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                    "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                    "where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam22='true'",param);
            }
            if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam23='true'")>0){
                param.put("waktu","JAM 23");  
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptLabelDaftarObatStokPasien2.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                    "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                    "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                    "where stok_obat_pasien.no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"' and stok_obat_pasien.tanggal='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"' and stok_obat_pasien.jam='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam23='true'",param);
            }
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_ppLabelObat2ActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgStokPasien dialog = new DlgStokPasien(new javax.swing.JFrame(), true);
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
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.TextBox Kd2;
    private widget.Label LCount;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.Tanggal Tgl1;
    private widget.Tanggal Tgl2;
    private widget.InternalFrame internalFrame1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.Label label10;
    private widget.Label label11;
    private widget.Label label19;
    private widget.Label label9;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi3;
    private javax.swing.JMenuItem ppLabelObat;
    private javax.swing.JMenuItem ppLabelObat2;
    private javax.swing.JMenuItem ppResepObat;
    private javax.swing.JMenuItem ppResepObat1;
    private javax.swing.JMenuItem ppResepObat2;
    private widget.Table tbKamar;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try{    
            if(TCari.getText().trim().equals("")){
                pstampil=koneksi.prepareStatement(
                    "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,concat(reg_periksa.no_rkm_medis,' ',pasien.nm_pasien),"+
                    "concat(stok_obat_pasien.kode_brng,' ',databarang.nama_brng), stok_obat_pasien.jumlah, concat(stok_obat_pasien.kd_bangsal,' ',bangsal.nm_bangsal), "+
                    "stok_obat_pasien.kode_brng,stok_obat_pasien.kd_bangsal,stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai,"+
                    "stok_obat_pasien.jam00,stok_obat_pasien.jam01,stok_obat_pasien.jam02,stok_obat_pasien.jam03,stok_obat_pasien.jam04,stok_obat_pasien.jam05,"+
                    "stok_obat_pasien.jam06,stok_obat_pasien.jam07,stok_obat_pasien.jam08,stok_obat_pasien.jam09,stok_obat_pasien.jam10,stok_obat_pasien.jam11,"+
                    "stok_obat_pasien.jam12,stok_obat_pasien.jam13,stok_obat_pasien.jam14,stok_obat_pasien.jam15,stok_obat_pasien.jam16,stok_obat_pasien.jam17,"+
                    "stok_obat_pasien.jam18,stok_obat_pasien.jam19,stok_obat_pasien.jam20,stok_obat_pasien.jam21,stok_obat_pasien.jam22,stok_obat_pasien.jam23 "+
                    "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                    "inner join bangsal on stok_obat_pasien.kd_bangsal=bangsal.kd_bangsal "+
                    "where stok_obat_pasien.tanggal between ? and ? order by stok_obat_pasien.tanggal");    
            }else{
                pstampil=koneksi.prepareStatement(
                    "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,concat(reg_periksa.no_rkm_medis,' ',pasien.nm_pasien),"+
                    "concat(stok_obat_pasien.kode_brng,' ',databarang.nama_brng), stok_obat_pasien.jumlah, concat(stok_obat_pasien.kd_bangsal,' ',bangsal.nm_bangsal), "+
                    "stok_obat_pasien.kode_brng,stok_obat_pasien.kd_bangsal,stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai,"+
                    "stok_obat_pasien.jam00,stok_obat_pasien.jam01,stok_obat_pasien.jam02,stok_obat_pasien.jam03,stok_obat_pasien.jam04,stok_obat_pasien.jam05,"+
                    "stok_obat_pasien.jam06,stok_obat_pasien.jam07,stok_obat_pasien.jam08,stok_obat_pasien.jam09,stok_obat_pasien.jam10,stok_obat_pasien.jam11,"+
                    "stok_obat_pasien.jam12,stok_obat_pasien.jam13,stok_obat_pasien.jam14,stok_obat_pasien.jam15,stok_obat_pasien.jam16,stok_obat_pasien.jam17,"+
                    "stok_obat_pasien.jam18,stok_obat_pasien.jam19,stok_obat_pasien.jam20,stok_obat_pasien.jam21,stok_obat_pasien.jam22,stok_obat_pasien.jam23 "+
                    "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                    "inner join bangsal on stok_obat_pasien.kd_bangsal=bangsal.kd_bangsal "+
                    "where stok_obat_pasien.tanggal between ? and ? and stok_obat_pasien.no_rawat like ? or "+
                    "stok_obat_pasien.tanggal between ? and ? and reg_periksa.no_rkm_medis like ? or "+
                    "stok_obat_pasien.tanggal between ? and ? and pasien.nm_pasien like ? or "+
                    "stok_obat_pasien.tanggal between ? and ? and databarang.nama_brng like ? or "+
                    "stok_obat_pasien.tanggal between ? and ? and stok_obat_pasien.no_batch like ? or "+
                    "stok_obat_pasien.tanggal between ? and ? and stok_obat_pasien.no_faktur like ? or "+
                    "stok_obat_pasien.tanggal between ? and ? and bangsal.nm_bangsal like ? order by stok_obat_pasien.tanggal");
            }
            try {
                if(TCari.getText().trim().equals("")){
                    pstampil.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    pstampil.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                }else{
                    pstampil.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    pstampil.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    pstampil.setString(3,"%"+TCari.getText().trim()+"%");
                    pstampil.setString(4,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    pstampil.setString(5,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    pstampil.setString(6,"%"+TCari.getText().trim()+"%");
                    pstampil.setString(7,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    pstampil.setString(8,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    pstampil.setString(9,"%"+TCari.getText().trim()+"%");
                    pstampil.setString(10,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    pstampil.setString(11,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    pstampil.setString(12,"%"+TCari.getText().trim()+"%");
                    pstampil.setString(13,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    pstampil.setString(14,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    pstampil.setString(15,"%"+TCari.getText().trim()+"%");
                    pstampil.setString(16,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    pstampil.setString(17,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    pstampil.setString(18,"%"+TCari.getText().trim()+"%");
                    pstampil.setString(19,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    pstampil.setString(20,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    pstampil.setString(21,"%"+TCari.getText().trim()+"%");
                }
                    
                rstampil=pstampil.executeQuery();
                while(rstampil.next()){                
                    tabMode.addRow(new Object[]{
                        rstampil.getString(1),rstampil.getString(2),rstampil.getString(3),rstampil.getString(4),
                        rstampil.getString(5),rstampil.getString(6),rstampil.getString(7),rstampil.getString(8),
                        rstampil.getString(9),rstampil.getString(10),rstampil.getString(11),rstampil.getString(12),
                        rstampil.getString(13).replaceAll("true","✓").replaceAll("false","✕"),
                        rstampil.getString(14).replaceAll("true","✓").replaceAll("false","✕"),
                        rstampil.getString(15).replaceAll("true","✓").replaceAll("false","✕"),
                        rstampil.getString(16).replaceAll("true","✓").replaceAll("false","✕"),
                        rstampil.getString(17).replaceAll("true","✓").replaceAll("false","✕"),
                        rstampil.getString(18).replaceAll("true","✓").replaceAll("false","✕"),
                        rstampil.getString(19).replaceAll("true","✓").replaceAll("false","✕"),
                        rstampil.getString(20).replaceAll("true","✓").replaceAll("false","✕"),
                        rstampil.getString(21).replaceAll("true","✓").replaceAll("false","✕"),
                        rstampil.getString(22).replaceAll("true","✓").replaceAll("false","✕"),
                        rstampil.getString(23).replaceAll("true","✓").replaceAll("false","✕"),
                        rstampil.getString(24).replaceAll("true","✓").replaceAll("false","✕"),
                        rstampil.getString(25).replaceAll("true","✓").replaceAll("false","✕"),
                        rstampil.getString(26).replaceAll("true","✓").replaceAll("false","✕"),
                        rstampil.getString(27).replaceAll("true","✓").replaceAll("false","✕"),
                        rstampil.getString(28).replaceAll("true","✓").replaceAll("false","✕"),
                        rstampil.getString(29).replaceAll("true","✓").replaceAll("false","✕"),
                        rstampil.getString(30).replaceAll("true","✓").replaceAll("false","✕"),
                        rstampil.getString(31).replaceAll("true","✓").replaceAll("false","✕"),
                        rstampil.getString(32).replaceAll("true","✓").replaceAll("false","✕"),
                        rstampil.getString(33).replaceAll("true","✓").replaceAll("false","✕"),
                        rstampil.getString(34).replaceAll("true","✓").replaceAll("false","✕"),
                        rstampil.getString(35).replaceAll("true","✓").replaceAll("false","✕"),
                        rstampil.getString(36).replaceAll("true","✓").replaceAll("false","✕")
                    });
                }
            } catch (Exception e) {
                System.out.println(e);
            }finally{
                if(rstampil!=null){
                    rstampil.close();
                }
                if(pstampil!=null){
                    pstampil.close();
                }
            }
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        LCount.setText(""+tabMode.getRowCount());
    }

    public JButton getButton(){
        return BtnKeluar;
    }
    
    public void setNoRm2(String norwt, Date tgl1, Date tgl2) {
        TCari.setText(norwt);
        Tgl1.setDate(tgl1);
        Tgl2.setDate(tgl2);
        tampil();
    }
    
    public void isCek(){
        BtnHapus.setEnabled(akses.getstok_obat_pasien());
        BtnPrint.setEnabled(akses.getstok_obat_pasien());    
    }
}

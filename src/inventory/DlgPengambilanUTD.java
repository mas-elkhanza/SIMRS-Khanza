package inventory;


import fungsi.WarnaTable2;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import keuangan.Jurnal;
import simrskhanza.DlgCariBangsal;

public class DlgPengambilanUTD extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement ps,psstok,psstok2;
    private ResultSet rs,rsstok;
    private int jml=0,i=0,row=0,index=0;
    private String[] kodebarang,namabarang,jumlah,satuan,stokasal,stoktujuan,hbeli,total,nobatch,nofaktur;
    private DlgCariBangsal bangsal=new DlgCariBangsal(null,false);
    private double stok_asal,stok_tujuan,subtotal,y;
    private Jurnal jur=new Jurnal();
    private WarnaTable2 warna=new WarnaTable2();
    private riwayatobat Trackobat=new riwayatobat();
    private boolean sukses=true;
    private String aktifkanbatch="no";

    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public DlgPengambilanUTD(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        Object[] judul={"Jml","Kode Barang","Nama Barang","Harga","Subtotal","Satuan","Stok Asal","Stok Tujuan","No.Batch","No.Faktur"};
        tabMode=new DefaultTableModel(null,judul){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }

              Class[] types = new Class[] {
                 java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.Double.class,
                 java.lang.Double.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,
                 java.lang.String.class,java.lang.String.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbDokter.setModel(tabMode);

        tbDokter.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 10; i++) {
            TableColumn column = tbDokter.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(50);
            }else if(i==1){
                column.setPreferredWidth(90);
            }else if(i==2){
                column.setPreferredWidth(250);
            }else if(i==3){
                column.setPreferredWidth(70);
            }else if(i==4){
                column.setPreferredWidth(90);
            }else if(i==5){
                column.setPreferredWidth(60);
            }else if(i==6){
                column.setPreferredWidth(60);
            }else if(i==7){
                column.setPreferredWidth(68);
            }else if(i==8){
                column.setPreferredWidth(70);
            }else if(i==9){
                column.setPreferredWidth(100);
            }
        }
        warna.kolom=0;
        tbDokter.setDefaultRenderer(Object.class,warna);

        kddari.setDocument(new batasInput((byte)10).getKata(kddari));
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
                if(akses.getform().equals("DlgPengambilanUTD")){
                    if(bangsal.getTable().getSelectedRow()!= -1){
                        kddari.setText(bangsal.getTable().getValueAt(bangsal.getTable().getSelectedRow(),0).toString());
                        nmdari.setText(bangsal.getTable().getValueAt(bangsal.getTable().getSelectedRow(),1).toString());
                        kddari.requestFocus();
                        tampil();
                    }
                }
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

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Kd2 = new widget.TextBox();
        Popup = new javax.swing.JPopupMenu();
        ppBersihkan = new javax.swing.JMenuItem();
        ppStok = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        scrollPane1 = new widget.ScrollPane();
        tbDokter = new widget.Table();
        panelisi1 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        label10 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari1 = new widget.Button();
        BtnTambah = new widget.Button();
        label11 = new widget.Label();
        BtnCari = new widget.Button();
        BtnKeluar = new widget.Button();
        panelisi3 = new widget.panelisi();
        label17 = new widget.Label();
        kddari = new widget.TextBox();
        nmdari = new widget.TextBox();
        btnDari = new widget.Button();
        label32 = new widget.Label();
        Tanggal = new widget.Tanggal();
        Keterangan = new widget.TextBox();
        label39 = new widget.Label();
        label40 = new widget.Label();
        LTotal = new widget.Label();

        Kd2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Kd2.setName("Kd2"); // NOI18N
        Kd2.setPreferredSize(new java.awt.Dimension(207, 23));

        Popup.setName("Popup"); // NOI18N

        ppBersihkan.setBackground(new java.awt.Color(255, 255, 254));
        ppBersihkan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppBersihkan.setForeground(new java.awt.Color(50, 50, 50));
        ppBersihkan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
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

        ppStok.setBackground(new java.awt.Color(255, 255, 254));
        ppStok.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppStok.setForeground(new java.awt.Color(50, 50, 50));
        ppStok.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppStok.setText("Tampilkan Semua Stok");
        ppStok.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppStok.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppStok.setName("ppStok"); // NOI18N
        ppStok.setPreferredSize(new java.awt.Dimension(200, 25));
        ppStok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppStokActionPerformed(evt);
            }
        });
        Popup.add(ppStok);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 253, 247)), "::[ Pengambilan BHP Medis Unit Tranfusi Darah ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        scrollPane1.setComponentPopupMenu(Popup);
        scrollPane1.setName("scrollPane1"); // NOI18N
        scrollPane1.setOpaque(true);

        tbDokter.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbDokter.setComponentPopupMenu(Popup);
        tbDokter.setName("tbDokter"); // NOI18N
        tbDokter.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tbDokterPropertyChange(evt);
            }
        });
        tbDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbDokterKeyPressed(evt);
            }
        });
        scrollPane1.setViewportView(tbDokter);

        internalFrame1.add(scrollPane1, java.awt.BorderLayout.CENTER);

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(734, 56));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

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
        panelisi1.add(BtnSimpan);

        label10.setText("Keyword :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(77, 23));
        panelisi1.add(label10);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(240, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelisi1.add(TCari);

        BtnCari1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari1.setMnemonic('1');
        BtnCari1.setToolTipText("Alt+1");
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
        panelisi1.add(BtnCari1);

        BtnTambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/plus_16.png"))); // NOI18N
        BtnTambah.setMnemonic('3');
        BtnTambah.setToolTipText("Alt+3");
        BtnTambah.setName("BtnTambah"); // NOI18N
        BtnTambah.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTambahActionPerformed(evt);
            }
        });
        panelisi1.add(BtnTambah);

        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(25, 23));
        panelisi1.add(label11);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnCari.setMnemonic('C');
        BtnCari.setText("Cari");
        BtnCari.setToolTipText("Alt+C");
        BtnCari.setName("BtnCari"); // NOI18N
        BtnCari.setPreferredSize(new java.awt.Dimension(100, 30));
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
        panelisi1.add(BtnCari);

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

        internalFrame1.add(panelisi1, java.awt.BorderLayout.PAGE_END);

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(100, 73));
        panelisi3.setLayout(null);

        label17.setText("Dari :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(65, 23));
        panelisi3.add(label17);
        label17.setBounds(0, 10, 80, 23);

        kddari.setEditable(false);
        kddari.setName("kddari"); // NOI18N
        kddari.setPreferredSize(new java.awt.Dimension(80, 23));
        kddari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kddariKeyPressed(evt);
            }
        });
        panelisi3.add(kddari);
        kddari.setBounds(83, 10, 90, 23);

        nmdari.setEditable(false);
        nmdari.setName("nmdari"); // NOI18N
        nmdari.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(nmdari);
        nmdari.setBounds(175, 10, 300, 23);

        btnDari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnDari.setMnemonic('1');
        btnDari.setToolTipText("Alt+1");
        btnDari.setName("btnDari"); // NOI18N
        btnDari.setPreferredSize(new java.awt.Dimension(28, 23));
        btnDari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDariActionPerformed(evt);
            }
        });
        panelisi3.add(btnDari);
        btnDari.setBounds(478, 10, 28, 23);

        label32.setText("Tanggal :");
        label32.setName("label32"); // NOI18N
        label32.setPreferredSize(new java.awt.Dimension(35, 23));
        panelisi3.add(label32);
        label32.setBounds(510, 10, 70, 23);

        Tanggal.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeyPressed(evt);
            }
        });
        panelisi3.add(Tanggal);
        Tanggal.setBounds(583, 10, 140, 23);

        Keterangan.setHighlighter(null);
        Keterangan.setName("Keterangan"); // NOI18N
        Keterangan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKeyPressed(evt);
            }
        });
        panelisi3.add(Keterangan);
        Keterangan.setBounds(83, 40, 392, 23);

        label39.setText("Keterangan :");
        label39.setName("label39"); // NOI18N
        label39.setPreferredSize(new java.awt.Dimension(35, 23));
        panelisi3.add(label39);
        label39.setBounds(0, 40, 80, 23);

        label40.setText("Total :");
        label40.setName("label40"); // NOI18N
        label40.setPreferredSize(new java.awt.Dimension(35, 23));
        panelisi3.add(label40);
        label40.setBounds(510, 40, 70, 23);

        LTotal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LTotal.setText("0");
        LTotal.setName("LTotal"); // NOI18N
        LTotal.setPreferredSize(new java.awt.Dimension(35, 23));
        panelisi3.add(LTotal);
        LTotal.setBounds(583, 40, 140, 23);

        internalFrame1.add(panelisi3, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgCariPengambilanUTD pindah=new DlgCariPengambilanUTD(null,true);
        pindah.tampil();
        pindah.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        pindah.setLocationRelativeTo(internalFrame1);
        pindah.setAlwaysOnTop(false);
        pindah.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnCariActionPerformed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
            dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnSimpan,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed
/*
private void KdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdKeyPressed
    Valid.pindah(evt,BtnCari,Nm);
}//GEN-LAST:event_TKdKeyPressed
*/

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(aktifkanbatch.equals("yes")){
            index=0;
            jml=tbDokter.getRowCount();
            for(i=0;i<jml;i++){
                if((Valid.SetAngka(tbDokter.getValueAt(i,0).toString())>0)&&(tbDokter.getValueAt(i,8).toString().trim().equals("")||tbDokter.getValueAt(i,9).toString().trim().equals(""))){
                    index++;
                }
            }
        }

        if(nmdari.getText().trim().equals("")||kddari.getText().trim().equals("")){
            Valid.textKosong(kddari,"Stok Asal");
        }else if(Keterangan.getText().trim().equals("")){
            Valid.textKosong(Keterangan,"Keterangan");
        }else if(aktifkanbatch.equals("yes")&&(index>0)){
            Valid.textKosong(TCari,"No.Batch/No.Faktur");
        }else if(subtotal<=0){
            Valid.textKosong(Keterangan,"Pengambilan");
        }else{
            i= JOptionPane.showConfirmDialog(rootPane,"Eeiiiiiits, udah bener belum data yang hendak disimpan..??","Konfirmasi",JOptionPane.YES_NO_OPTION);
            if (i == JOptionPane.YES_OPTION) {
                Sequel.AutoComitFalse();
                sukses=true;
                for(i=0;i<tbDokter.getRowCount();i++){
                    try {
                        if(Valid.SetAngka(tbDokter.getValueAt(i,0).toString())>0){
                            if(Sequel.menyimpantf2("utd_pengambilan_medis","'"+tbDokter.getValueAt(i,1).toString()+"','"+tbDokter.getValueAt(i,0).toString()+"','"+
                                    tbDokter.getValueAt(i,3).toString()+"','"+tbDokter.getValueAt(i,4).toString()+"','"+kddari.getText()+"','"+
                                    Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19)+"','"+
                                    Keterangan.getText()+"','"+tbDokter.getValueAt(i,8).toString()+"','"+tbDokter.getValueAt(i,9).toString()+"'","Pengambilan BHP UTD")==true){
                                Trackobat.catatRiwayat(tbDokter.getValueAt(i,1).toString(),0,Valid.SetAngka(tbDokter.getValueAt(i,0).toString()),"Pengambilan Medis",akses.getkode(),kddari.getText(),"Simpan",tbDokter.getValueAt(i,8).toString(),tbDokter.getValueAt(i,9).toString());
                                Sequel.menyimpan("gudangbarang","'"+tbDokter.getValueAt(i,1).toString()+"','"+kddari.getText()+"','-"+tbDokter.getValueAt(i,0).toString()+"','"+tbDokter.getValueAt(i,8).toString()+"','"+tbDokter.getValueAt(i,9).toString()+"'",
                                            "stok=stok-"+tbDokter.getValueAt(i,0).toString()+"","kode_brng='"+tbDokter.getValueAt(i,1).toString()+"' and kd_bangsal='"+kddari.getText()+"' and no_batch='"+tbDokter.getValueAt(i,8).toString()+"' and no_faktur='"+tbDokter.getValueAt(i,9).toString()+"'");
                                Sequel.menyimpan("utd_stok_medis","'"+tbDokter.getValueAt(i,1).toString()+"','"+tbDokter.getValueAt(i,0).toString()+"','"+tbDokter.getValueAt(i,3).toString()+"'",
                                                    "stok=stok+"+tbDokter.getValueAt(i,0).toString()+",hargaterakhir='"+tbDokter.getValueAt(i,3).toString()+"'","kode_brng='"+tbDokter.getValueAt(i,1).toString()+"'");
                                if(aktifkanbatch.equals("yes")){
                                    Sequel.mengedit("data_batch","no_batch=? and kode_brng=? and no_faktur=?","sisa=sisa-?",4,new String[]{
                                        ""+tabMode.getValueAt(i,0).toString(),tabMode.getValueAt(i,8).toString(),tabMode.getValueAt(i,1).toString(),tabMode.getValueAt(i,9).toString()
                                    });
                                }
                            }else{
                                sukses=false;
                            }
                        }
                    } catch (Exception e) {
                    }
                }

                if(sukses==true){
                    Sequel.queryu("delete from tampjurnal");
                    Sequel.menyimpan2("tampjurnal","?,?,?,?",4,new String[]{Sequel.cariIsi("select Pengambilan_Utd from set_akun"),"PENGAMBILAN BHP MEDIS UTD",""+subtotal,"0"});
                    Sequel.menyimpan2("tampjurnal","?,?,?,?",4,new String[]{Sequel.cariIsi("select Kontra_Pengambilan_Utd from set_akun"),"PERSEDIAAN BARANG/OBAT/ALKES/BHP","0",""+subtotal});
                    sukses=jur.simpanJurnal(Valid.SetTgl(Tanggal.getSelectedItem()+"").replaceAll("-","/"),Valid.SetTgl(Tanggal.getSelectedItem()+""),"U","PENGAMBILAN BHP MEDIS UTD DARI "+nmdari.getText().toUpperCase()+", OLEH "+akses.getkode());
                }

                if(sukses==true){
                    Sequel.Commit();
                    for(index=0;index<tbDokter.getRowCount();index++){
                        tbDokter.setValueAt("",index,0);
                        tbDokter.setValueAt(0,index,4);
                        tbDokter.setValueAt(0,index,6);
                        tbDokter.setValueAt(0,index,7);
                    }
                    LTotal.setText("0");
                    Tanggal.setDate(new Date());
                }else{
                    JOptionPane.showMessageDialog(null,"Terjadi kesalahan saat pemrosesan data, transaksi dibatalkan.\nPeriksa kembali data sebelum melanjutkan menyimpan..!!");
                    Sequel.RollBack();
                }

                Sequel.AutoComitTrue();
                if(sukses==true){
                    tampil();
                }
            }
        }
    }//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,BtnKeluar,TCari);
        }
    }//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnSimpan,BtnKeluar);
        }
    }//GEN-LAST:event_BtnCariKeyPressed

private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            tampil();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari1.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            kddari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            tbDokter.requestFocus();
        }
}//GEN-LAST:event_TCariKeyPressed

private void BtnCari1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCari1ActionPerformed
        tampil();
        isTotal();
}//GEN-LAST:event_BtnCari1ActionPerformed

private void BtnCari1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCari1KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            tampil();
        }else{
            Valid.pindah(evt, BtnSimpan, BtnKeluar);
        }
}//GEN-LAST:event_BtnCari1KeyPressed

private void ppBersihkanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBersihkanActionPerformed
            for(i=0;i<tbDokter.getRowCount();i++){
                tbDokter.setValueAt("",i,0);
                tbDokter.setValueAt("0",i,4);
            }
            isTotal();
}//GEN-LAST:event_ppBersihkanActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void kddariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kddariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nm_bangsal from bangsal where kd_bangsal='"+kddari.getText()+"'",nmdari);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select nm_bangsal from bangsal where kd_bangsal='"+kddari.getText()+"'",nmdari);
            TCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select nm_bangsal from bangsal where kd_bangsal='"+kddari.getText()+"'",nmdari);
            //kdke.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnDariActionPerformed(null);
        }
    }//GEN-LAST:event_kddariKeyPressed

    private void btnDariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDariActionPerformed
        akses.setform("DlgPengambilanUTD");
        bangsal.emptTeks();
        bangsal.isCek();
        bangsal.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        bangsal.setLocationRelativeTo(internalFrame1);
        bangsal.setAlwaysOnTop(false);
        bangsal.setVisible(true);
    }//GEN-LAST:event_btnDariActionPerformed

    private void TanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKeyPressed
        Valid.pindah(evt,Keterangan,BtnSimpan);
    }//GEN-LAST:event_TanggalKeyPressed

    private void KeteranganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKeyPressed
        Valid.pindah(evt,Tanggal,TCari);
    }//GEN-LAST:event_KeteranganKeyPressed

    private void tbDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbDokterKeyPressed
        if(tabMode.getRowCount()!=0){
            if(evt.getKeyCode()==KeyEvent.VK_ENTER){
                if(tbDokter.getSelectedColumn()==1){
                    isTotal();
                    TCari.setText("");
                    TCari.requestFocus();
                }
            }else if(evt.getKeyCode()==KeyEvent.VK_RIGHT){
                if(tbDokter.getSelectedColumn()==1){
                    isTotal();
                }else if(tbDokter.getSelectedColumn()==2){
                    try {
                        tbDokter.setValueAt(Double.parseDouble(tabMode.getValueAt(tbDokter.getSelectedRow(),0).toString())*
                                            Double.parseDouble(tabMode.getValueAt(tbDokter.getSelectedRow(),3).toString()),tbDokter.getSelectedRow(),4);
                    } catch (Exception e) {
                        tbDokter.setValueAt(0,tbDokter.getSelectedRow(),4);
                    }
                }else if(tbDokter.getSelectedColumn()==3){
                    try {
                        tbDokter.setValueAt(Double.parseDouble(tabMode.getValueAt(tbDokter.getSelectedRow(),0).toString())*
                                            Double.parseDouble(tabMode.getValueAt(tbDokter.getSelectedRow(),3).toString()),tbDokter.getSelectedRow(),4);
                    } catch (Exception e) {
                        tbDokter.setValueAt(0,tbDokter.getSelectedRow(),4);
                    }
                }
            }else if(evt.getKeyCode()==KeyEvent.VK_DELETE){
                try {
                    if(tbDokter.getSelectedColumn()==0){
                        tbDokter.setValueAt("",tbDokter.getSelectedRow(),0);
                        tbDokter.setValueAt(0,tbDokter.getSelectedRow(),4);
                        tbDokter.setValueAt(0,tbDokter.getSelectedRow(),6);
                        tbDokter.setValueAt(0,tbDokter.getSelectedRow(),7);
                    }
                } catch (Exception e) {
                }
                isTotal();
            }else if(evt.getKeyCode()==KeyEvent.VK_SHIFT){
                TCari.requestFocus();
            }
        }
    }//GEN-LAST:event_tbDokterKeyPressed

    private void ppStokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppStokActionPerformed
        for(i=0;i<tbDokter.getRowCount();i++){
            try {
                stok_asal=0;
                psstok=koneksi.prepareStatement("select ifnull(stok,'0') from gudangbarang where kd_bangsal=? and kode_brng=?");
                try {
                    psstok.setString(1,kddari.getText());
                    psstok.setString(2,tabMode.getValueAt(i,1).toString());
                    rsstok=psstok.executeQuery();
                    if(rsstok.next()){
                        stok_asal=rsstok.getDouble(1);
                    }
                } catch (Exception e) {
                    System.out.println("Notifikasi : "+e);
                } finally{
                    if(rsstok!=null){
                        rsstok.close();
                    }
                    if(psstok!=null){
                        psstok.close();
                    }
                }

                stok_tujuan=0;
                psstok2=koneksi.prepareStatement("select ifnull(stok,'0') from utd_stok_medis where kode_brng=?");
                try {
                    psstok2.setString(1,tabMode.getValueAt(i,1).toString());
                    rsstok=psstok2.executeQuery();
                    if(rsstok.next()){
                        stok_tujuan=rsstok.getDouble(1);
                    }
                } catch (Exception e) {
                    System.out.println("Notifikasi : "+e);
                } finally{
                    if(rsstok!=null){
                        rsstok.close();
                    }
                    if(psstok2!=null){
                        psstok2.close();
                    }
                }
                tbDokter.setValueAt(stok_asal,i,6);
                tbDokter.setValueAt(stok_tujuan,i,7);
            } catch (Exception e) {
                tbDokter.setValueAt(0,i,6);
                tbDokter.setValueAt(0,i,7);
            }
        }
    }//GEN-LAST:event_ppStokActionPerformed

    private void BtnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgBarang barang=new DlgBarang(null,false);
        barang.emptTeks();
        barang.isCek();
        barang.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        barang.setLocationRelativeTo(internalFrame1);
        barang.setAlwaysOnTop(false);
        barang.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnTambahActionPerformed

    private void tbDokterPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tbDokterPropertyChange
        if(this.isVisible()==true){
            isTotal();
        }
    }//GEN-LAST:event_tbDokterPropertyChange

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgPengambilanUTD dialog = new DlgPengambilanUTD(new javax.swing.JFrame(), true);
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
    private widget.Button BtnCari1;
    private widget.Button BtnKeluar;
    private widget.Button BtnSimpan;
    private widget.Button BtnTambah;
    private widget.TextBox Kd2;
    private widget.TextBox Keterangan;
    private widget.Label LTotal;
    private javax.swing.JPopupMenu Popup;
    private widget.TextBox TCari;
    private widget.Tanggal Tanggal;
    private widget.Button btnDari;
    private widget.InternalFrame internalFrame1;
    private widget.TextBox kddari;
    private widget.Label label10;
    private widget.Label label11;
    private widget.Label label17;
    private widget.Label label32;
    private widget.Label label39;
    private widget.Label label40;
    private widget.TextBox nmdari;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi3;
    private javax.swing.JMenuItem ppBersihkan;
    private javax.swing.JMenuItem ppStok;
    private widget.ScrollPane scrollPane1;
    private widget.Table tbDokter;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        row=tbDokter.getRowCount();
        jml=0;
        for(i=0;i<row;i++){
            try {
                if(Double.parseDouble(tbDokter.getValueAt(i,0).toString())>0){
                    jml++;
                }
            } catch (Exception e) {
                jml=jml+0;
            }
        }

        kodebarang=new String[jml];
        namabarang=new String[jml];
        satuan=new String[jml];
        hbeli=new String[jml];
        total=new String[jml];
        jumlah=new String[jml];
        stokasal=new String[jml];
        stoktujuan=new String[jml];
        nobatch=new String[jml];
        nofaktur=new String[jml];
        index=0;
        for(i=0;i<row;i++){
            try {
                if(Double.parseDouble(tbDokter.getValueAt(i,0).toString())>0){
                    jumlah[index]=tbDokter.getValueAt(i,0).toString();
                    kodebarang[index]=tbDokter.getValueAt(i,1).toString();
                    namabarang[index]=tbDokter.getValueAt(i,2).toString();
                    hbeli[index]=tbDokter.getValueAt(i,3).toString();
                    total[index]=tbDokter.getValueAt(i,4).toString();
                    satuan[index]=tbDokter.getValueAt(i,5).toString();
                    stokasal[index]=tbDokter.getValueAt(i,6).toString();
                    stoktujuan[index]=tbDokter.getValueAt(i,7).toString();
                    nobatch[index]=tbDokter.getValueAt(i,8).toString();
                    nofaktur[index]=tbDokter.getValueAt(i,9).toString();
                    index++;
                }
            } catch (Exception e) {
            }
        }
        Valid.tabelKosong(tabMode);
        for(i=0;i<jml;i++){
            tabMode.addRow(new Object[]{jumlah[i],kodebarang[i],namabarang[i],hbeli[i],total[i],satuan[i],stokasal[i],stoktujuan[i],nobatch[i],nofaktur[i]});
        }
        try{
            if(aktifkanbatch.equals("yes")){
                ps=koneksi.prepareStatement(
                    "select data_batch.kode_brng, databarang.nama_brng,data_batch.dasar,databarang.kode_sat,data_batch.no_batch,data_batch.no_faktur, "+
                    " gudangbarang.stok from data_batch inner join databarang on data_batch.kode_brng=databarang.kode_brng "+
                    " inner join gudangbarang on gudangbarang.kode_brng=data_batch.kode_brng and gudangbarang.no_batch=data_batch.no_batch and gudangbarang.no_faktur=data_batch.no_faktur "+
                    " where gudangbarang.stok>0 and gudangbarang.kd_bangsal=? and data_batch.kode_brng like ? or "+
                    " gudangbarang.stok>0 and gudangbarang.kd_bangsal=? and databarang.nama_brng like ? order by databarang.nama_brng");
                try {
                    ps.setString(1,kddari.getText());
                    ps.setString(2,"%"+TCari.getText().trim()+"%");
                    ps.setString(3,kddari.getText());
                    ps.setString(4,"%"+TCari.getText().trim()+"%");
                    rs=ps.executeQuery();
                    while(rs.next()){
                        tabMode.addRow(new Object[]{"",rs.getString(1),rs.getString(2),rs.getDouble(3),0,rs.getString(4),rs.getDouble("stok"),0,rs.getString("no_batch"),rs.getString("no_faktur")});
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
            }else{
                ps=koneksi.prepareStatement(
                    "select databarang.kode_brng, databarang.nama_brng,databarang.dasar,databarang.kode_sat,gudangbarang.stok "+
                    " from databarang inner join gudangbarang on databarang.kode_brng=gudangbarang.kode_brng "+
                    " where gudangbarang.no_batch='' and gudangbarang.no_faktur='' and gudangbarang.stok>0 and databarang.status='1' and gudangbarang.kd_bangsal=? and databarang.kode_brng like ? or "+
                    " gudangbarang.no_batch='' and gudangbarang.no_faktur='' and gudangbarang.stok>0 and databarang.status='1' and gudangbarang.kd_bangsal=? and databarang.nama_brng like ? order by databarang.nama_brng");
                try {
                    ps.setString(1,kddari.getText());
                    ps.setString(2,"%"+TCari.getText().trim()+"%");
                    ps.setString(3,kddari.getText());
                    ps.setString(4,"%"+TCari.getText().trim()+"%");
                    rs=ps.executeQuery();
                    while(rs.next()){
                        tabMode.addRow(new Object[]{"",rs.getString(1),rs.getString(2),rs.getDouble(3),0,rs.getString(4),rs.getDouble("stok"),0,"",""});
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
            }
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }

    }

    private void isTotal(){
        row=tbDokter.getSelectedRow();
        if(nmdari.getText().trim().equals("")){
            for(index=0;index<tbDokter.getRowCount();index++){
                tbDokter.setValueAt("",index,0);
            }
        }else if(row!= -1){
            if(!tbDokter.getValueAt(row,0).toString().equals("")){
                try {
                    if(Double.parseDouble(tabMode.getValueAt(row,0).toString())>0){
                        stok_asal=0;
                        psstok=koneksi.prepareStatement("select ifnull(stok,'0') from gudangbarang where kd_bangsal=? and kode_brng=? and no_batch=? and no_faktur=?");
                        try {
                            psstok.setString(1,kddari.getText());
                            psstok.setString(2,tabMode.getValueAt(row,1).toString());
                            psstok.setString(3,tabMode.getValueAt(row,8).toString());
                            psstok.setString(4,tabMode.getValueAt(row,9).toString());
                            rsstok=psstok.executeQuery();
                            if(rsstok.next()){
                                stok_asal=rsstok.getDouble(1);
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi : "+e);
                        } finally{
                            if(rsstok!=null){
                                rsstok.close();
                            }
                            if(psstok!=null){
                                psstok.close();
                            }
                        }

                        stok_tujuan=0;
                        psstok2=koneksi.prepareStatement("select ifnull(stok,'0') from utd_stok_medis where kode_brng=?");
                        try {
                            psstok2.setString(1,tabMode.getValueAt(row,1).toString());
                            rsstok=psstok2.executeQuery();
                            if(rsstok.next()){
                                stok_tujuan=rsstok.getDouble(1);
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi : "+e);
                        } finally{
                            if(rsstok!=null){
                                rsstok.close();
                            }
                            if(psstok2!=null){
                                psstok2.close();
                            }
                        }

                        tbDokter.setValueAt(stok_asal,row,6);
                        tbDokter.setValueAt(stok_tujuan,row,7);
                        if(Double.parseDouble(tbDokter.getValueAt(row,0).toString())>stok_asal){
                             JOptionPane.showMessageDialog(null,"Maaf, Stok tidak cukup....!!!");
                             TCari.requestFocus();
                             tbDokter.setValueAt("",row,0);
                             tbDokter.setValueAt(0,row,4);
                        }else{
                             tbDokter.setValueAt(Double.parseDouble(tbDokter.getValueAt(row,0).toString())*Double.parseDouble(tbDokter.getValueAt(row,3).toString()),row,4);
                        }
                    }
                } catch (Exception e) {
                    tbDokter.setValueAt("",row,0);
                    tbDokter.setValueAt(0,row,4);
                    tbDokter.setValueAt(0,row,6);
                    tbDokter.setValueAt(0,row,7);
                }
            }else{
                tbDokter.setValueAt(0,row,4);
                tbDokter.setValueAt(0,row,6);
                tbDokter.setValueAt(0,row,7);
            }
        }

        subtotal=0;
        for(int r=0;r<tabMode.getRowCount();r++){
            y=0;
            try {
                y=Double.parseDouble(tabMode.getValueAt(r,4).toString());
            } catch (Exception e) {
                y=0;
            }
            subtotal=subtotal+y;
        }
        LTotal.setText(Valid.SetAngka(subtotal));
    }

    public void isCek(){
        BtnSimpan.setEnabled(akses.getpengambilan_utd());
        BtnTambah.setEnabled(akses.getobat());
    }


}

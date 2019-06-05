package ipsrs;


import fungsi.WarnaTable;
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
import kepegawaian.DlgCariPetugas;

public class DlgPengambilanPenunjangUTD extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement ps,psstok2;
    private ResultSet rs,rsstok;
    private int jml=0,i=0,row=0,index=0;
    private String[] kodebarang,namabarang,jumlah,satuan,stokasal,stoktujuan,hbeli,total;
    private DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    private double stok_tujuan,subtotal,y;
    private Jurnal jur=new Jurnal();

    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public DlgPengambilanPenunjangUTD(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        Object[] judul={"Jml","Kode Barang","Nama Barang","Harga","Subtotal","Satuan","Stok Asal","Stok Tujuan"};
        tabMode=new DefaultTableModel(null,judul){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
        };
        tbDokter.setModel(tabMode);

        tbDokter.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 8; i++) {
            TableColumn column = tbDokter.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(50);
            }else if(i==1){
                column.setPreferredWidth(90);
            }else if(i==2){
                column.setPreferredWidth(300);
            }else if(i==3){
                column.setPreferredWidth(70);
            }else if(i==4){
                column.setPreferredWidth(90);
            }else if(i==5){
                column.setPreferredWidth(60);
            }else if(i==6){
                column.setPreferredWidth(70);
            }else if(i==7){
                column.setPreferredWidth(70);
            }
        }
        tbDokter.setDefaultRenderer(Object.class, new WarnaTable());

        Nip.setDocument(new batasInput((byte)10).getKata(Nip));
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
        petugas.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgPengambilanUTD")){
                    if(petugas.getTable().getSelectedRow()!= -1){   
                        Nip.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                        Nama.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                        Nip.requestFocus();
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
        Nip = new widget.TextBox();
        Nama = new widget.TextBox();
        btnPetugas = new widget.Button();
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
        ppBersihkan.setForeground(new java.awt.Color(70, 70, 70));
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
        ppStok.setForeground(new java.awt.Color(70, 70, 70));
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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Pengambilan BHP Non Medis Unit Tranfusi Darah ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(70, 70, 70))); // NOI18N
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

        label10.setText("Key Word :");
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

        label17.setText("Petugas :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(65, 23));
        panelisi3.add(label17);
        label17.setBounds(0, 10, 80, 23);

        Nip.setName("Nip"); // NOI18N
        Nip.setPreferredSize(new java.awt.Dimension(80, 23));
        Nip.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NipKeyPressed(evt);
            }
        });
        panelisi3.add(Nip);
        Nip.setBounds(83, 10, 90, 23);

        Nama.setEditable(false);
        Nama.setName("Nama"); // NOI18N
        Nama.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(Nama);
        Nama.setBounds(175, 10, 300, 23);

        btnPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPetugas.setMnemonic('1');
        btnPetugas.setToolTipText("Alt+1");
        btnPetugas.setName("btnPetugas"); // NOI18N
        btnPetugas.setPreferredSize(new java.awt.Dimension(28, 23));
        btnPetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPetugasActionPerformed(evt);
            }
        });
        panelisi3.add(btnPetugas);
        btnPetugas.setBounds(478, 10, 28, 23);

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
        DlgCariPengambilanPenunjangUTD pindah=new DlgCariPengambilanPenunjangUTD(null,true);
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
        if(Nama.getText().trim().equals("")||Nip.getText().trim().equals("")){
            Valid.textKosong(Nip,"Petugas");
        }else if(Keterangan.getText().trim().equals("")){
            Valid.textKosong(Keterangan,"Keterangan");
        }else if(subtotal<=0){
            Valid.textKosong(Keterangan,"Pengambilan");
        }else{
            i= JOptionPane.showConfirmDialog(rootPane,"Eeiiiiiits, udah bener belum data yang mau disimpan..??","Konfirmasi",JOptionPane.YES_NO_OPTION);
            if (i == JOptionPane.YES_OPTION) {
                
                for(i=0;i<tbDokter.getRowCount();i++){  
                        try {
                            if(Valid.SetAngka(tbDokter.getValueAt(i,0).toString())>0){
                                if(Sequel.menyimpantf("utd_pengambilan_penunjang","'"+tbDokter.getValueAt(i,1).toString()+"','"+tbDokter.getValueAt(i,0).toString()+"','"+
                                        tbDokter.getValueAt(i,3).toString()+"','"+tbDokter.getValueAt(i,4).toString()+"','"+Nip.getText()+"','"+
                                        Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19)+"','"+Keterangan.getText()+"'","Pengambilan BHP UTD")==true){
                                    Sequel.mengedit("ipsrsbarang","kode_brng=?","stok=stok-?",2,new String[]{
                                        tbDokter.getValueAt(i,0).toString(),tbDokter.getValueAt(i,1).toString()
                                    });
                                    Sequel.menyimpan("utd_stok_penunjang","'"+tbDokter.getValueAt(i,1).toString()+"','"+tbDokter.getValueAt(i,0).toString()+"','"+tbDokter.getValueAt(i,3).toString()+"'", 
                                                        "stok=stok+"+tbDokter.getValueAt(i,0).toString()+",hargaterakhir='"+tbDokter.getValueAt(i,3).toString()+"'","kode_brng='"+tbDokter.getValueAt(i,1).toString()+"'");
                                }else{
                                    tbDokter.setValueAt(0,tbDokter.getSelectedRow(),4);
                                }                                  
                            }
                        } catch (Exception e) {
                        }                    
                }  
                isTotal();
                Sequel.queryu("delete from tampjurnal");
                Sequel.menyimpan("tampjurnal","?,?,?,?",4,new String[]{Sequel.cariIsi("select Pengambilan_Penunjang_Utd from set_akun"),"PENGAMBILAN BARANG NON MEDIS UTD",""+subtotal,"0"});
                Sequel.menyimpan("tampjurnal","?,?,?,?",4,new String[]{Sequel.cariIsi("select Kontra_Pengambilan_Penunjang_Utd from set_akun"),"PERSEDIAAN BARANG NON MEDIS","0",""+subtotal}); 
                jur.simpanJurnal(Valid.SetTgl(Tanggal.getSelectedItem()+"").replaceAll("-","/"),Valid.SetTgl(Tanggal.getSelectedItem()+""),"U","PENGAMBILAN BARANG NON MEDIS UTD, PETUGAS : "+Nama.getText().toUpperCase());
                
                for(index=0;index<tbDokter.getRowCount();index++){   
                    tbDokter.setValueAt(null,index,0);        
                    tbDokter.setValueAt(0,index,4);
                }
                LTotal.setText("0");
                Tanggal.setDate(new Date());
                tampil();
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
            Nip.requestFocus();
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

    private void NipKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NipKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nm_bangsal from petugas where kd_bangsal='"+Nip.getText()+"'",Nama);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select nm_bangsal from petugas where kd_bangsal='"+Nip.getText()+"'",Nama);
            TCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select nm_bangsal from petugas where kd_bangsal='"+Nip.getText()+"'",Nama);
            //kdke.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnPetugasActionPerformed(null);
        }
    }//GEN-LAST:event_NipKeyPressed

    private void btnPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPetugasActionPerformed
        akses.setform("DlgPengambilanUTD");
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setAlwaysOnTop(false);
        petugas.setVisible(true);
    }//GEN-LAST:event_btnPetugasActionPerformed

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
                    try {
                        if(Valid.SetAngka(tabMode.getValueAt(tbDokter.getSelectedRow(),0).toString())>0){
                            stok_tujuan=0;                
                            psstok2=koneksi.prepareStatement("select ifnull(stok,'0') from utd_stok_penunjang where kode_brng=?");
                            try {
                                psstok2.setString(1,tabMode.getValueAt(tbDokter.getSelectedRow(),1).toString());
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
                            tbDokter.setValueAt(
                                    Double.toString(
                                            Double.parseDouble(tabMode.getValueAt(tbDokter.getSelectedRow(),0).toString())*
                                            Double.parseDouble(tabMode.getValueAt(tbDokter.getSelectedRow(),3).toString())
                                    ),
                                    tbDokter.getSelectedRow(),4);
                            tbDokter.setValueAt(stok_tujuan,tbDokter.getSelectedRow(),7);
                        }                        
                    } catch (Exception e) {
                        tbDokter.setValueAt(0,tbDokter.getSelectedRow(),4);
                        tbDokter.setValueAt(0,tbDokter.getSelectedRow(),7);
                    }
                    isTotal();
                    TCari.setText("");
                    TCari.requestFocus();
                }                
            }else if(evt.getKeyCode()==KeyEvent.VK_RIGHT){
                if(tbDokter.getSelectedColumn()==1){                      
                    try {
                        if(Valid.SetAngka(tabMode.getValueAt(tbDokter.getSelectedRow(),0).toString())>0){
                            stok_tujuan=0;                
                            psstok2=koneksi.prepareStatement("select ifnull(stok,'0') from utd_stok_penunjang where kode_brng=?");
                            try {
                                psstok2.setString(1,tabMode.getValueAt(tbDokter.getSelectedRow(),1).toString());
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
                            tbDokter.setValueAt(
                                    Double.toString(
                                            Double.parseDouble(tabMode.getValueAt(tbDokter.getSelectedRow(),0).toString())*
                                            Double.parseDouble(tabMode.getValueAt(tbDokter.getSelectedRow(),3).toString())
                                    ),
                                    tbDokter.getSelectedRow(),4);    
                            tbDokter.setValueAt(stok_tujuan,tbDokter.getSelectedRow(),7);
                        }else{
                            tbDokter.setValueAt(0,tbDokter.getSelectedRow(),4);
                            tbDokter.setValueAt(0,tbDokter.getSelectedRow(),7);
                        }                        
                    } catch (Exception e) {
                        tbDokter.setValueAt(0,tbDokter.getSelectedRow(),4);
                        tbDokter.setValueAt(0,tbDokter.getSelectedRow(),7);
                    }
                    isTotal();
                }else if(tbDokter.getSelectedColumn()==2){  
                    try {
                        tbDokter.setValueAt(
                                    Double.toString(
                                            Double.parseDouble(tabMode.getValueAt(tbDokter.getSelectedRow(),0).toString())*
                                            Double.parseDouble(tabMode.getValueAt(tbDokter.getSelectedRow(),3).toString())
                                    ),
                                    tbDokter.getSelectedRow(),4); 
                    } catch (Exception e) {
                        tbDokter.setValueAt(0,tbDokter.getSelectedRow(),4); 
                    }
                }else if(tbDokter.getSelectedColumn()==3){  
                    try {
                        tbDokter.setValueAt(
                                    Double.toString(
                                            Double.parseDouble(tabMode.getValueAt(tbDokter.getSelectedRow(),0).toString())*
                                            Double.parseDouble(tabMode.getValueAt(tbDokter.getSelectedRow(),3).toString())
                                    ),
                                    tbDokter.getSelectedRow(),4); 
                    } catch (Exception e) {
                        tbDokter.setValueAt(0,tbDokter.getSelectedRow(),4); 
                    }
                }                  
            }else if(evt.getKeyCode()==KeyEvent.VK_DELETE){
                try {
                    if(tbDokter.getSelectedColumn()==0){
                        tbDokter.setValueAt(null,tbDokter.getSelectedRow(),0);
                        tbDokter.setValueAt(0,tbDokter.getSelectedRow(),4);
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
                stok_tujuan=0;                
                psstok2=koneksi.prepareStatement("select ifnull(stok,'0') from utd_stok_penunjang where kode_brng=?");
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
                tbDokter.setValueAt(stok_tujuan,i,7);
            } catch (Exception e) {
                tbDokter.setValueAt(0,i,7);
            }
        }
    }//GEN-LAST:event_ppStokActionPerformed

    private void BtnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgBarangIPSRS barang=new DlgBarangIPSRS(null,false);
        barang.emptTeks();
        barang.isCek();
        barang.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        barang.setLocationRelativeTo(internalFrame1);
        barang.setAlwaysOnTop(false);
        barang.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnTambahActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgPengambilanPenunjangUTD dialog = new DlgPengambilanPenunjangUTD(new javax.swing.JFrame(), true);
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
    private widget.TextBox Nama;
    private widget.TextBox Nip;
    private javax.swing.JPopupMenu Popup;
    private widget.TextBox TCari;
    private widget.Tanggal Tanggal;
    private widget.Button btnPetugas;
    private widget.InternalFrame internalFrame1;
    private widget.Label label10;
    private widget.Label label11;
    private widget.Label label17;
    private widget.Label label32;
    private widget.Label label39;
    private widget.Label label40;
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
                    index++;
                }
            } catch (Exception e) {
            }
        }
        Valid.tabelKosong(tabMode);
        for(i=0;i<jml;i++){
            tabMode.addRow(new Object[]{jumlah[i],kodebarang[i],namabarang[i],hbeli[i],total[i],satuan[i],stokasal[i],stoktujuan[i]});
        }
        try{
            ps=koneksi.prepareStatement(
                "select ipsrsbarang.kode_brng, ipsrsbarang.nama_brng,ipsrsbarang.harga,ipsrsbarang.kode_sat,ipsrsbarang.stok "+
                " from ipsrsbarang where ipsrsbarang.kode_brng like ? or "+
                " ipsrsbarang.nama_brng like ? order by ipsrsbarang.nama_brng");
            try {
                ps.setString(1,"%"+TCari.getText().trim()+"%");
                ps.setString(2,"%"+TCari.getText().trim()+"%");
                rs=ps.executeQuery();
                while(rs.next()){                
                    tabMode.addRow(new Object[]{null,rs.getString(1),rs.getString(2),rs.getString(3),0,rs.getString(4),rs.getString(5),0});
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
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        
    }
    
    private void isTotal(){
        subtotal=0;
        for(int r=0;r<tabMode.getRowCount();r++){ 
            y=0;            
            try {
                if(Double.parseDouble(tabMode.getValueAt(r,0).toString())>0){
                    y=Double.parseDouble(tabMode.getValueAt(r,4).toString());
                }                 
            } catch (Exception e) {
                y=0;
            }
            subtotal=subtotal+y;
        }
        LTotal.setText(Valid.SetAngka(subtotal));
    }

    public void isCek(){
        BtnSimpan.setEnabled(akses.getpengambilan_penunjang_utd());
        BtnTambah.setEnabled(akses.getipsrs_barang());
        if(akses.getjml2()>=1){
            Nip.setEditable(false);
            btnPetugas.setEnabled(false);
            Nip.setText(akses.getkode());
            Sequel.cariIsi("select nama from petugas where nip=?",Nama,Nip.getText());
        } 
    }

 
}

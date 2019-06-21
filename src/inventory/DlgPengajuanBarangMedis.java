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
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariPegawai;

public class DlgPengajuanBarangMedis extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement ps;
    private ResultSet rs;
    private int jml=0,i=0,row=0,index=0;
    private String[] jumlah,kodebarang,namabarang,satuan,jenis,kategori,golongan;
    private Double[] harga,subtotal;
    private WarnaTable2 warna=new WarnaTable2();
    private DlgCariPegawai pegawai=new DlgCariPegawai(null,false);
    private DlgBarang barang=new DlgBarang(null,false);
    private double total=0,y=0;
    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public DlgPengajuanBarangMedis(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        Object[] judul={"Jml","Kode Barang","Nama Barang","Satuan","Jenis Obat","Kategori","Golongan","H.Pengajuan","Sub Total"};
        tabMode=new DefaultTableModel(null,judul){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){
               boolean a = false;
               if ((colIndex==0)||(colIndex==7)) {
                   a=true;
               }
               return a;
             }
              
             Class[] types = new Class[] {
                 java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,
                 java.lang.String.class,java.lang.String.class,java.lang.Double.class,java.lang.Double.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbDokter.setModel(tabMode);

        tbDokter.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 9; i++) {
            TableColumn column = tbDokter.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(42);
            }else if(i==1){
                column.setPreferredWidth(90);
            }else if(i==2){
                column.setPreferredWidth(200);
            }else if(i==3){
                column.setPreferredWidth(50);
            }else if(i==4){
                column.setPreferredWidth(100);
            }else if(i==5){
                column.setPreferredWidth(100);
            }else if(i==6){
                column.setPreferredWidth(100);
            }else if(i==7){
                column.setPreferredWidth(90);
            }else if(i==8){
                column.setPreferredWidth(100);
            }
        }
        warna.kolom=0;
        tbDokter.setDefaultRenderer(Object.class,warna);

        NoPengajuan.setDocument(new batasInput((byte)15).getKata(NoPengajuan));
        kdptg.setDocument(new batasInput((byte)25).getKata(kdptg));        
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        Keterangan.setDocument(new batasInput((int)150).getKata(Keterangan));
        
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
        pegawai.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(pegawai.getTable().getSelectedRow()!= -1){                   
                    kdptg.setText(pegawai.tbKamar.getValueAt(pegawai.tbKamar.getSelectedRow(),0).toString());
                    nmptg.setText(pegawai.tbKamar.getValueAt(pegawai.tbKamar.getSelectedRow(),1).toString());
                    Departemen.setText(pegawai.tbKamar.getValueAt(pegawai.tbKamar.getSelectedRow(),5).toString());
                }   
                kdptg.requestFocus();
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
        
        pegawai.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    pegawai.dispose();
                }                
            }
            @Override
            public void keyReleased(KeyEvent e) {}
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

        Popup = new javax.swing.JPopupMenu();
        ppBersihkan = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        scrollPane1 = new widget.ScrollPane();
        tbDokter = new widget.Table();
        panelisi1 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        label10 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari1 = new widget.Button();
        BtnTambah = new widget.Button();
        label17 = new widget.Label();
        LTotal = new widget.Label();
        BtnCari = new widget.Button();
        BtnKeluar = new widget.Button();
        panelisi3 = new widget.panelisi();
        label15 = new widget.Label();
        NoPengajuan = new widget.TextBox();
        label11 = new widget.Label();
        Tanggal = new widget.Tanggal();
        label13 = new widget.Label();
        label16 = new widget.Label();
        kdptg = new widget.TextBox();
        nmptg = new widget.TextBox();
        btnPetugas = new widget.Button();
        label14 = new widget.Label();
        Departemen = new widget.TextBox();
        Keterangan = new widget.TextBox();

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

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Pengajuan Pengadaan/Pembelian Obat/Alkes/BHP Medis ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(70, 70, 70))); // NOI18N
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
        tbDokter.setToolTipText("Masukkan jumlah pengajuan di ujung paling kiri pada warna biru kemudian geser kanan");
        tbDokter.setComponentPopupMenu(Popup);
        tbDokter.setName("tbDokter"); // NOI18N
        tbDokter.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDokterMouseClicked(evt);
            }
        });
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
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 56));
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
        label10.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi1.add(label10);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(155, 23));
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

        label17.setText("Total :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(50, 26));
        panelisi1.add(label17);

        LTotal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LTotal.setText("0");
        LTotal.setName("LTotal"); // NOI18N
        LTotal.setPreferredSize(new java.awt.Dimension(120, 26));
        panelisi1.add(LTotal);

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

        label15.setText("No.Pengajuan :");
        label15.setName("label15"); // NOI18N
        label15.setPreferredSize(new java.awt.Dimension(80, 23));
        panelisi3.add(label15);
        label15.setBounds(0, 10, 92, 23);

        NoPengajuan.setName("NoPengajuan"); // NOI18N
        NoPengajuan.setPreferredSize(new java.awt.Dimension(207, 23));
        NoPengajuan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoPengajuanKeyPressed(evt);
            }
        });
        panelisi3.add(NoPengajuan);
        NoPengajuan.setBounds(95, 10, 120, 23);

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(80, 23));
        panelisi3.add(label11);
        label11.setBounds(220, 10, 55, 23);

        Tanggal.setDisplayFormat("dd-MM-yyyy");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TanggalItemStateChanged(evt);
            }
        });
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeyPressed(evt);
            }
        });
        panelisi3.add(Tanggal);
        Tanggal.setBounds(279, 10, 90, 23);

        label13.setText("Pegawai :");
        label13.setName("label13"); // NOI18N
        label13.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label13);
        label13.setBounds(415, 10, 75, 23);

        label16.setText("Keterangan :");
        label16.setName("label16"); // NOI18N
        label16.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(label16);
        label16.setBounds(0, 40, 92, 23);

        kdptg.setName("kdptg"); // NOI18N
        kdptg.setPreferredSize(new java.awt.Dimension(80, 23));
        kdptg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdptgKeyPressed(evt);
            }
        });
        panelisi3.add(kdptg);
        kdptg.setBounds(493, 10, 76, 23);

        nmptg.setEditable(false);
        nmptg.setName("nmptg"); // NOI18N
        nmptg.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(nmptg);
        nmptg.setBounds(571, 10, 190, 23);

        btnPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPetugas.setMnemonic('2');
        btnPetugas.setToolTipText("Alt+2");
        btnPetugas.setName("btnPetugas"); // NOI18N
        btnPetugas.setPreferredSize(new java.awt.Dimension(28, 23));
        btnPetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPetugasActionPerformed(evt);
            }
        });
        panelisi3.add(btnPetugas);
        btnPetugas.setBounds(764, 10, 28, 23);

        label14.setText("Departemen :");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label14);
        label14.setBounds(415, 40, 75, 23);

        Departemen.setEditable(false);
        Departemen.setName("Departemen"); // NOI18N
        Departemen.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(Departemen);
        Departemen.setBounds(493, 40, 299, 23);

        Keterangan.setName("Keterangan"); // NOI18N
        Keterangan.setPreferredSize(new java.awt.Dimension(275, 23));
        Keterangan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKeyPressed(evt);
            }
        });
        panelisi3.add(Keterangan);
        Keterangan.setBounds(95, 40, 273, 23);

        internalFrame1.add(panelisi3, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgCariPengajuanBarangMedis form=new DlgCariPengajuanBarangMedis(null,false);
        form.emptTeks(); 
        form.isCek();
        form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        form.setLocationRelativeTo(internalFrame1);
        form.setAlwaysOnTop(false);
        form.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnCariActionPerformed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        pegawai.dispose();
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
        jml=0;
        for(i=0;i<tbDokter.getRowCount();i++){
            if(!tbDokter.getValueAt(i,0).toString().equals("")){
                jml++;
            }
        }
        if(NoPengajuan.getText().trim().equals("")){
            Valid.textKosong(NoPengajuan,"No.Pengajuan");
        }else if(Keterangan.getText().trim().equals("")){
            Valid.textKosong(Keterangan,"Keterangan");
        }else if(nmptg.getText().trim().equals("")){
            Valid.textKosong(kdptg,"Petugas");
        }else if(tbDokter.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        }else if(jml<=0){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan masukkan permintaan...!!!!");
            tbDokter.requestFocus();
        }else{
            int reply = JOptionPane.showConfirmDialog(rootPane,"Eeiiiiiits, udah bener belum data yang mau disimpan..??","Konfirmasi",JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
                if(Sequel.menyimpantf("pengajuan_barang_medis","?,?,?,?,?","No.Pengajuan",5,new String[]{
                        NoPengajuan.getText(),kdptg.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+""),"Proses Pengajuan",Keterangan.getText()
                    })==true){
                        jml=tbDokter.getRowCount();
                        for(i=0;i<jml;i++){  
                            try {
                                if(Valid.SetAngka(tbDokter.getValueAt(i,0).toString())>0){
                                    Sequel.menyimpan("detail_pengajuan_barang_medis","?,?,?,?,?,?","Detail Pengajuan Barang Medis",6,new String[]{
                                         NoPengajuan.getText(),tbDokter.getValueAt(i,1).toString(),tbDokter.getValueAt(i,3).toString(),
                                         tbDokter.getValueAt(i,0).toString(),tbDokter.getValueAt(i,7).toString(),tbDokter.getValueAt(i,8).toString()
                                    });                                   
                                }
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            }                
                        }
                        
                        jml=tbDokter.getRowCount();
                        for(i=0;i<jml;i++){ 
                            tbDokter.setValueAt("",i,0);
                            tbDokter.setValueAt(0,i,8);
                        }
                }
                autoNomor();
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
            Keterangan.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            tbDokter.requestFocus();
        }
}//GEN-LAST:event_TCariKeyPressed

private void BtnCari1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCari1ActionPerformed
        tampil();
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
                tbDokter.setValueAt("",i,7);
            }
}//GEN-LAST:event_ppBersihkanActionPerformed

private void tbDokterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDokterMouseClicked
        if(tbDokter.getRowCount()!=0){
            getData();
        }
}//GEN-LAST:event_tbDokterMouseClicked

private void tbDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbDokterKeyPressed
        if(tbDokter.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                if(tbDokter.getSelectedColumn()==1){
                    TCari.setText("");
                    TCari.requestFocus();
                } 
                getData();
            }else if(evt.getKeyCode()==KeyEvent.VK_DELETE){
                i=tbDokter.getSelectedRow();
                if(i!= -1){
                    tbDokter.setValueAt("", i,0);
                }
            }else if(evt.getKeyCode()==KeyEvent.VK_SHIFT){
                TCari.setText("");
                TCari.requestFocus();
            }
        }
}//GEN-LAST:event_tbDokterKeyPressed

private void NoPengajuanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoPengajuanKeyPressed
        Valid.pindah(evt, BtnSimpan, Keterangan);
}//GEN-LAST:event_NoPengajuanKeyPressed

private void TanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKeyPressed
        Valid.pindah(evt,NoPengajuan,Keterangan);
}//GEN-LAST:event_TanggalKeyPressed

private void kdptgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdptgKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama from pegawai where nik=?", nmptg,kdptg.getText());          
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select nama from pegawai where nik=?", nmptg,kdptg.getText());
            Keterangan.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select nama from pegawai where nik=?", nmptg,kdptg.getText());
            BtnSimpan.requestFocus();  
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnPetugasActionPerformed(null);
        }
}//GEN-LAST:event_kdptgKeyPressed

private void btnPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPetugasActionPerformed
        pegawai.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        pegawai.setLocationRelativeTo(internalFrame1);
        pegawai.setAlwaysOnTop(false);
        pegawai.setVisible(true);
}//GEN-LAST:event_btnPetugasActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void BtnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        barang.emptTeks();
        barang.isCek();
        barang.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        barang.setLocationRelativeTo(internalFrame1);
        barang.setAlwaysOnTop(false);
        barang.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnTambahActionPerformed

    private void TanggalItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TanggalItemStateChanged
        try {
            autoNomor();
        } catch (Exception e) {
        }   
    }//GEN-LAST:event_TanggalItemStateChanged

    private void KeteranganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganKeyPressed

    private void tbDokterPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tbDokterPropertyChange
        if(this.isVisible()==true){
            getData();
        }
    }//GEN-LAST:event_tbDokterPropertyChange

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgPengajuanBarangMedis dialog = new DlgPengajuanBarangMedis(new javax.swing.JFrame(), true);
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
    private widget.TextBox Departemen;
    private widget.TextBox Keterangan;
    private widget.Label LTotal;
    private widget.TextBox NoPengajuan;
    private javax.swing.JPopupMenu Popup;
    private widget.TextBox TCari;
    private widget.Tanggal Tanggal;
    private widget.Button btnPetugas;
    private widget.InternalFrame internalFrame1;
    private widget.TextBox kdptg;
    private widget.Label label10;
    private widget.Label label11;
    private widget.Label label13;
    private widget.Label label14;
    private widget.Label label15;
    private widget.Label label16;
    private widget.Label label17;
    private widget.TextBox nmptg;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi3;
    private javax.swing.JMenuItem ppBersihkan;
    private widget.ScrollPane scrollPane1;
    private widget.Table tbDokter;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        try{
            row=tbDokter.getRowCount();
            jml=0;
            for(i=0;i<row;i++){
                if(!tbDokter.getValueAt(i,0).toString().equals("")){
                    jml++;
                }
            }

            kodebarang=null;
            kodebarang=new String[jml];
            namabarang=null;
            namabarang=new String[jml];
            satuan=null;
            satuan=new String[jml];
            jumlah=null;
            jumlah=new String[jml];
            jenis=null;
            jenis=new String[jml];
            kategori=null;
            kategori=new String[jml];
            golongan=null;
            golongan=new String[jml];
            harga=null;
            harga=new Double[jml];
            subtotal=null;
            subtotal=new Double[jml];
            
            index=0;        
            for(i=0;i<row;i++){
                if(!tbDokter.getValueAt(i,0).toString().equals("")){
                    jumlah[index]=tbDokter.getValueAt(i,0).toString();
                    kodebarang[index]=tbDokter.getValueAt(i,1).toString();
                    namabarang[index]=tbDokter.getValueAt(i,2).toString();
                    satuan[index]=tbDokter.getValueAt(i,3).toString();
                    jenis[index]=tbDokter.getValueAt(i,4).toString();
                    kategori[index]=tbDokter.getValueAt(i,5).toString();
                    golongan[index]=tbDokter.getValueAt(i,6).toString();
                    harga[index]=Double.parseDouble(tbDokter.getValueAt(i,7).toString());
                    subtotal[index]=Double.parseDouble(tbDokter.getValueAt(i,8).toString());
                    index++;
                }
            }
            Valid.tabelKosong(tabMode);
            for(i=0;i<jml;i++){
                tabMode.addRow(new Object[]{jumlah[i],kodebarang[i],namabarang[i],satuan[i],jenis[i],kategori[i],golongan[i],harga[i],subtotal[i]});
            }
            
            ps=koneksi.prepareStatement(
                "select databarang.kode_brng,databarang.nama_brng,databarang.kode_sat,jenis.nama,"+
                "kategori_barang.nama as kategori,golongan_barang.nama as golongan,databarang.h_beli "+
                " from databarang inner join jenis inner join golongan_barang "+
                " inner join kategori_barang on databarang.kdjns=jenis.kdjns "+
                " and databarang.kode_golongan=golongan_barang.kode "+
                " and databarang.kode_kategori=kategori_barang.kode "+
                " where databarang.status='1' and databarang.kode_brng like ? or "+
                " databarang.status='1' and databarang.nama_brng like ? or "+
                " databarang.status='1' and kategori_barang.nama like ? or "+
                " databarang.status='1' and golongan_barang.nama like ? or "+
                " databarang.status='1' and jenis.nama like ? order by databarang.nama_brng");
            try {
                ps.setString(1,"%"+TCari.getText().trim()+"%");
                ps.setString(2,"%"+TCari.getText().trim()+"%");
                ps.setString(3,"%"+TCari.getText().trim()+"%");
                ps.setString(4,"%"+TCari.getText().trim()+"%");
                ps.setString(5,"%"+TCari.getText().trim()+"%");
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new Object[]{
                        "",rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),
                        rs.getString(5),rs.getString(6),rs.getDouble(7),0
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
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        
    }

    
    
    public void isCek(){
        autoNomor();
        TCari.requestFocus();
        if(akses.getjml2()>=1){
            kdptg.setEditable(false);
            btnPetugas.setEnabled(false);
            kdptg.setText(akses.getkode());
            BtnSimpan.setEnabled(akses.getpengajuan_barang_medis());
            BtnTambah.setEnabled(akses.getobat());
            Sequel.cariIsi("select nama from pegawai where nik=?", nmptg,kdptg.getText());
            Sequel.cariIsi("select departemen from pegawai where nik=?",Departemen,kdptg.getText());
        }        
    }
    
    private void autoNomor() {
        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(no_pengajuan,3),signed)),0) from pengajuan_barang_medis where tanggal='"+Valid.SetTgl(Tanggal.getSelectedItem()+"")+"' ",
                "PBM"+Tanggal.getSelectedItem().toString().substring(6,10)+Tanggal.getSelectedItem().toString().substring(3,5)+Tanggal.getSelectedItem().toString().substring(0,2),3,NoPengajuan); 
    }
    
    private void getData(){        
        row=tbDokter.getSelectedRow();
        if(row!= -1){              
            try {
                if(Valid.SetAngka(tbDokter.getValueAt(row,0).toString())>0){                        
                    tbDokter.setValueAt(Double.parseDouble(tbDokter.getValueAt(row,0).toString())*Double.parseDouble(tbDokter.getValueAt(row,7).toString()), row,8);          
                } 
            } catch (Exception e) {
                tbDokter.setValueAt(0, row,8);           
            }
        }
        
        total=0;
        jml=tbDokter.getRowCount();
        for(i=0;i<jml;i++){     
            y=0;
            if(Valid.SetAngka(tbDokter.getValueAt(i,0).toString())>0){ 
                try {
                    y=Double.parseDouble(tbDokter.getValueAt(i,8).toString());
                } catch (Exception e) {
                    tbDokter.setValueAt(0,tbDokter.getSelectedRow(),8);
                    y=0;                
                }
            }
            total=total+y;   
        }

        LTotal.setText(Valid.SetAngka(total));
        
    }

 
}

package toko;


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
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import keuangan.Jurnal;

public class TokoReturBeli extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private riwayattoko Trackbarang=new riwayattoko();
    private Jurnal jur=new Jurnal();
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement ps;
    private ResultSet rs;
    private TokoCariReturBeli form=new TokoCariReturBeli(null,false);
    private double ttl=0,y=0,kolom,stokbarang;
    private int jml=0,i=0,row=0,index=0;
    private String[] kodebarang,namabarang,satuan,nofaktur;
    private double[] h_retur,jumlah,jmltotal,dasar,stok;
    private WarnaTable2 warna=new WarnaTable2();
    public boolean tampikan=true;
    private boolean sukses=true;
    private String hpptoko="",Retur_Beli_Toko=Sequel.cariIsi("select Retur_Beli_Toko from set_akun"),Kontra_Retur_Beli_Toko=Sequel.cariIsi("select Kontra_Retur_Beli_Toko from set_akun");

    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public TokoReturBeli(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        Object[] judul={"Jml","Kode Barang","Nama Barang","Satuan","Harga(Rp)","Total(Rp)","No.Faktur","Stok","Dasar"};
        tabMode=new DefaultTableModel(null,judul){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if ((colIndex==0)||(colIndex==4)||(colIndex==6)) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,
                java.lang.Double.class,java.lang.Double.class,java.lang.String.class,java.lang.Double.class,
                java.lang.Double.class
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
                column.setPreferredWidth(85);
            }else if(i==5){
                column.setPreferredWidth(95);
            }else if(i==6){
                column.setPreferredWidth(120);
            }else if(i==7){
                column.setPreferredWidth(35);
            }else if(i==8){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }
        }
        warna.kolom=0;
        tbDokter.setDefaultRenderer(Object.class,warna);

        NoRetur.setDocument(new batasInput((byte)20).getKata(NoRetur));
        kdsup.setDocument(new batasInput((byte)5).getKata(kdsup));
        kdptg.setDocument(new batasInput((byte)25).getKata(kdptg)); 
        Catatan.setDocument(new batasInput((int)40).getKata(Catatan));         
        TCari.setDocument(new batasInput((int)100).getKata(TCari));
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
        
        form.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                autoNomor();
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
        
        form.suplier.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("TokoReturBeli")){
                    if(form.suplier.getTable().getSelectedRow()!= -1){                   
                        kdsup.setText(form.suplier.getTable().getValueAt(form.suplier.getTable().getSelectedRow(),0).toString());                    
                        nmsup.setText(form.suplier.getTable().getValueAt(form.suplier.getTable().getSelectedRow(),1).toString());
                    } 
                    kdsup.requestFocus();
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
        
        form.suplier.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(akses.getform().equals("TokoReturBeli")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        form.suplier.dispose();
                    }                
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });            
        
        form.petugas.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("TokoReturBeli")){
                    if(form.petugas.getTable().getSelectedRow()!= -1){                   
                        kdptg.setText(form.petugas.getTable().getValueAt(form.petugas.getTable().getSelectedRow(),0).toString());
                        nmptg.setText(form.petugas.getTable().getValueAt(form.petugas.getTable().getSelectedRow(),1).toString());
                    }  
                    kdptg.requestFocus();
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
        internalFrame1 = new widget.InternalFrame();
        scrollPane1 = new widget.ScrollPane();
        tbDokter = new widget.Table();
        panelisi3 = new widget.panelisi();
        label15 = new widget.Label();
        NoRetur = new widget.TextBox();
        label13 = new widget.Label();
        kdsup = new widget.TextBox();
        label16 = new widget.Label();
        kdptg = new widget.TextBox();
        nmsup = new widget.TextBox();
        nmptg = new widget.TextBox();
        btnSuplier = new widget.Button();
        btnPetugas = new widget.Button();
        label11 = new widget.Label();
        TglRetur = new widget.Tanggal();
        label18 = new widget.Label();
        Catatan = new widget.TextBox();
        panelisi1 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        label10 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari1 = new widget.Button();
        BtnTambah = new widget.Button();
        label12 = new widget.Label();
        LTotal = new widget.Label();
        BtnCari = new widget.Button();
        BtnKeluar = new widget.Button();

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

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Transaksi Retur Barang Toko / Minimarket / Koperasi Ke Suplier ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(100, 73));
        panelisi3.setLayout(null);

        label15.setText("No.Retur :");
        label15.setName("label15"); // NOI18N
        label15.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(label15);
        label15.setBounds(0, 10, 63, 23);

        NoRetur.setName("NoRetur"); // NOI18N
        NoRetur.setPreferredSize(new java.awt.Dimension(207, 23));
        NoRetur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoReturKeyPressed(evt);
            }
        });
        panelisi3.add(NoRetur);
        NoRetur.setBounds(67, 10, 110, 23);

        label13.setText("Petugas :");
        label13.setName("label13"); // NOI18N
        label13.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label13);
        label13.setBounds(395, 40, 60, 23);

        kdsup.setName("kdsup"); // NOI18N
        kdsup.setPreferredSize(new java.awt.Dimension(80, 23));
        kdsup.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdsupKeyPressed(evt);
            }
        });
        panelisi3.add(kdsup);
        kdsup.setBounds(459, 10, 80, 23);

        label16.setText("Supplier :");
        label16.setName("label16"); // NOI18N
        label16.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(label16);
        label16.setBounds(395, 10, 60, 23);

        kdptg.setName("kdptg"); // NOI18N
        kdptg.setPreferredSize(new java.awt.Dimension(80, 23));
        kdptg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdptgKeyPressed(evt);
            }
        });
        panelisi3.add(kdptg);
        kdptg.setBounds(459, 40, 80, 23);

        nmsup.setEditable(false);
        nmsup.setName("nmsup"); // NOI18N
        nmsup.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(nmsup);
        nmsup.setBounds(541, 10, 210, 23);

        nmptg.setEditable(false);
        nmptg.setName("nmptg"); // NOI18N
        nmptg.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(nmptg);
        nmptg.setBounds(541, 40, 210, 23);

        btnSuplier.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnSuplier.setMnemonic('1');
        btnSuplier.setToolTipText("Alt+1");
        btnSuplier.setName("btnSuplier"); // NOI18N
        btnSuplier.setPreferredSize(new java.awt.Dimension(28, 23));
        btnSuplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuplierActionPerformed(evt);
            }
        });
        panelisi3.add(btnSuplier);
        btnSuplier.setBounds(753, 10, 28, 23);

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
        btnPetugas.setBounds(753, 40, 28, 23);

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label11);
        label11.setBounds(190, 10, 60, 23);

        TglRetur.setDisplayFormat("dd-MM-yyyy");
        TglRetur.setName("TglRetur"); // NOI18N
        TglRetur.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TglReturItemStateChanged(evt);
            }
        });
        TglRetur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglReturKeyPressed(evt);
            }
        });
        panelisi3.add(TglRetur);
        TglRetur.setBounds(254, 10, 90, 23);

        label18.setText("Catatan :");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(label18);
        label18.setBounds(0, 40, 63, 23);

        Catatan.setName("Catatan"); // NOI18N
        Catatan.setPreferredSize(new java.awt.Dimension(207, 23));
        Catatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CatatanKeyPressed(evt);
            }
        });
        panelisi3.add(Catatan);
        Catatan.setBounds(67, 40, 280, 23);

        internalFrame1.add(panelisi3, java.awt.BorderLayout.PAGE_START);

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 56));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

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
        label10.setPreferredSize(new java.awt.Dimension(65, 23));
        panelisi1.add(label10);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(170, 23));
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

        label12.setText("Total:");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(35, 23));
        panelisi1.add(label12);

        LTotal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LTotal.setText("0");
        LTotal.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        LTotal.setName("LTotal"); // NOI18N
        LTotal.setPreferredSize(new java.awt.Dimension(120, 23));
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

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents
/*
private void KdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdKeyPressed
    Valid.pindah(evt,BtnCari,Nm);
}//GEN-LAST:event_TKdKeyPressed
*/

private void ppBersihkanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBersihkanActionPerformed
            for(i=0;i<tbDokter.getRowCount();i++){ 
                tbDokter.setValueAt("",i,0);
                tbDokter.setValueAt(0,i,5);
            }
}//GEN-LAST:event_ppBersihkanActionPerformed

private void tbDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbDokterKeyPressed
        if(tbDokter.getRowCount()!=0){
            if(evt.getKeyCode()==KeyEvent.VK_ENTER){
                try {                  
                   if((tbDokter.getSelectedColumn()==1)||(tbDokter.getSelectedColumn()==2)||(tbDokter.getSelectedColumn()==5)||(tbDokter.getSelectedColumn()==6)||(tbDokter.getSelectedColumn()==8)){                       
                        getData();  
                        TCari.setText("");
                        TCari.requestFocus();
                   }
                } catch (java.lang.NullPointerException e) {
                }
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

private void NoReturKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoReturKeyPressed
        Valid.pindah(evt, BtnSimpan, kdsup);
}//GEN-LAST:event_NoReturKeyPressed

private void kdsupKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdsupKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama_suplier from tokosuplier where kode_suplier=?", nmsup,kdsup.getText());           
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select nama_suplier from tokosuplier where kode_suplier=?", nmsup,kdsup.getText());
            NoRetur.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select nama_suplier from tokosuplier where kode_suplier=?", nmsup,kdsup.getText());
            kdptg.requestFocus(); 
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnSuplierActionPerformed(null);
        }
}//GEN-LAST:event_kdsupKeyPressed

private void kdptgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdptgKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama from petugas where nip=?", nmptg,kdptg.getText());          
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select nama from petugas where nip=?", nmptg,kdptg.getText());
            kdsup.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select nama from petugas where nip=?", nmptg,kdptg.getText());
            BtnSimpan.requestFocus();  
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnPetugasActionPerformed(null);
        }
}//GEN-LAST:event_kdptgKeyPressed

private void btnSuplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuplierActionPerformed
        akses.setform("TokoReturBeli");
        form.suplier.emptTeks();
        form.suplier.isCek();
        form.suplier.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        form.suplier.setLocationRelativeTo(internalFrame1);
        form.suplier.setAlwaysOnTop(false);
        form.suplier.setVisible(true);
}//GEN-LAST:event_btnSuplierActionPerformed

private void btnPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPetugasActionPerformed
        akses.setform("TokoReturBeli");
        form.petugas.emptTeks();
        form.petugas.isCek();
        form.petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        form.petugas.setLocationRelativeTo(internalFrame1);
        form.petugas.setAlwaysOnTop(false);
        form.petugas.setVisible(true);
}//GEN-LAST:event_btnPetugasActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        if(tampikan==true){
            tampil();
        }
    }//GEN-LAST:event_formWindowOpened

    private void TglReturKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglReturKeyPressed
        Valid.pindah(evt,NoRetur,kdsup);
    }//GEN-LAST:event_TglReturKeyPressed

    private void tbDokterPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tbDokterPropertyChange
        if(this.isVisible()==true){
              getData();
        }
    }//GEN-LAST:event_tbDokterPropertyChange

    private void CatatanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CatatanKeyPressed
        Valid.pindah(evt,NoRetur,kdsup);
    }//GEN-LAST:event_CatatanKeyPressed

    private void BtnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        akses.setform("TokoReturBeli");
        form.barang.emptTeks();
        form.barang.isCek();
        form.barang.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        form.barang.setLocationRelativeTo(internalFrame1);
        form.barang.setAlwaysOnTop(false);
        form.barang.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnTambahActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnSimpan,TCari);}
    }//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnSimpan,BtnKeluar);
        }
    }//GEN-LAST:event_BtnCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        form.emptTeks();
        form.isCek();
        form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        form.setLocationRelativeTo(internalFrame1);
        form.setAlwaysOnTop(false);
        form.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCari1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCari1KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            tampil();
        }else{
            Valid.pindah(evt, BtnSimpan, BtnKeluar);
        }
    }//GEN-LAST:event_BtnCari1KeyPressed

    private void BtnCari1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCari1ActionPerformed
        tampil();
    }//GEN-LAST:event_BtnCari1ActionPerformed

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            tampil();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari1.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            kdsup.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            tbDokter.requestFocus();
        }
    }//GEN-LAST:event_TCariKeyPressed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,BtnKeluar,TCari);
        }
    }//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(NoRetur.getText().trim().equals("")){
            Valid.textKosong(NoRetur,"No.Retur");
        }else if(nmsup.getText().trim().equals("")){
            Valid.textKosong(kdsup,"Supplier");
        }else if(nmptg.getText().trim().equals("")){
            Valid.textKosong(kdptg,"Petugas");
        }else if(Catatan.getText().trim().equals("")){
            Valid.textKosong(Catatan,"Catatan");
        }else if(tbDokter.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        }else if(ttl<=0){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan masukkan retur...!!!!");
            tbDokter.requestFocus();
        }else{
            int reply = JOptionPane.showConfirmDialog(rootPane,"Eeiiiiiits, udah bener belum data yang mau disimpan..??","Konfirmasi",JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
                Sequel.AutoComitFalse();
                sukses=true;
                if(Sequel.menyimpantf2("tokoreturbeli","?,?,?,?,?,?","No.Retur",6,new String[]{
                    NoRetur.getText(),Valid.SetTgl(TglRetur.getSelectedItem()+""),kdptg.getText(),kdsup.getText(),Catatan.getText(),ttl+""
                })==true){
                    jml=tbDokter.getRowCount();
                    for(i=0;i<jml;i++){
                        if(Valid.SetAngka(tbDokter.getValueAt(i,0).toString())>0){
                            if(Sequel.menyimpantf2("toko_detail_returbeli","?,?,?,?,?,?,?,?","Transaksi Retur Beli",8,new String[]{
                                NoRetur.getText(),tbDokter.getValueAt(i,6).toString(),tbDokter.getValueAt(i,1).toString(),tbDokter.getValueAt(i,3).toString(), 
                                tbDokter.getValueAt(i,8).toString(),tbDokter.getValueAt(i,4).toString(),tbDokter.getValueAt(i,0).toString(),tbDokter.getValueAt(i,5).toString()
                            })==true){
                                Trackbarang.catatRiwayat(tbDokter.getValueAt(i,1).toString(),0,Valid.SetAngka(tbDokter.getValueAt(i,0).toString()),"Retur Beli", akses.getkode(),"Simpan");
                                Sequel.mengedit("tokobarang","kode_brng=?","stok=stok-?",2,new String[]{
                                    tbDokter.getValueAt(i,0).toString(),tbDokter.getValueAt(i,1).toString()
                                });
                            }else{
                                sukses=false;
                            }
                        }
                    }
                }else{
                    sukses=false;
                }

                if(sukses==true){
                    Sequel.queryu("delete from tampjurnal");
                    Sequel.menyimpan("tampjurnal","?,?,?,?",4,new String[]{Retur_Beli_Toko,"RETUR PEMBELIAN","0",""+ttl});
                    Sequel.menyimpan("tampjurnal","?,?,?,?",4,new String[]{Kontra_Retur_Beli_Toko,"KONTRA RETUR PEMBELIAN",""+ttl,"0"});
                    sukses=jur.simpanJurnal(NoRetur.getText(),"U","RETUR PEMBELIAN TOKO"+", OLEH "+akses.getkode());
                }

                if(sukses==true){
                    Sequel.Commit();
                    jml=tbDokter.getRowCount();
                    for(i=0;i<jml;i++){
                        tbDokter.setValueAt("",i,0);
                        tbDokter.setValueAt(0,i,5);
                        tbDokter.setValueAt("",i,6);
                    }
                    getData();
                }else{
                    JOptionPane.showMessageDialog(null,"Terjadi kesalahan saat pemrosesan data, transaksi dibatalkan.\nPeriksa kembali data sebelum melanjutkan menyimpan..!!");
                    Sequel.RollBack();
                }
                Sequel.AutoComitTrue();
                autoNomor();
            }
        }
    }//GEN-LAST:event_BtnSimpanActionPerformed

    private void TglReturItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TglReturItemStateChanged
        try {
            autoNomor();
        } catch (Exception e) {
        }
    }//GEN-LAST:event_TglReturItemStateChanged

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            TokoReturBeli dialog = new TokoReturBeli(new javax.swing.JFrame(), true);
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
    private widget.TextBox Catatan;
    private widget.TextBox Kd2;
    private widget.Label LTotal;
    private widget.TextBox NoRetur;
    private javax.swing.JPopupMenu Popup;
    private widget.TextBox TCari;
    private widget.Tanggal TglRetur;
    private widget.Button btnPetugas;
    private widget.Button btnSuplier;
    private widget.InternalFrame internalFrame1;
    private widget.TextBox kdptg;
    private widget.TextBox kdsup;
    private widget.Label label10;
    private widget.Label label11;
    private widget.Label label12;
    private widget.Label label13;
    private widget.Label label15;
    private widget.Label label16;
    private widget.Label label18;
    private widget.TextBox nmptg;
    private widget.TextBox nmsup;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi3;
    private javax.swing.JMenuItem ppBersihkan;
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
        nofaktur=new String[jml];
        h_retur=new double[jml];
        jumlah=new double[jml];
        jmltotal=new double[jml];
        dasar=new double[jml];
        stok=new double[jml];
        
        index=0;        
        for(i=0;i<row;i++){
            try {
                if(Double.parseDouble(tbDokter.getValueAt(i,0).toString())>0){
                    jumlah[index]=Double.parseDouble(tbDokter.getValueAt(i,0).toString());
                    kodebarang[index]=tbDokter.getValueAt(i,1).toString();
                    namabarang[index]=tbDokter.getValueAt(i,2).toString();
                    satuan[index]=tbDokter.getValueAt(i,3).toString();
                    h_retur[index]=Double.parseDouble(tbDokter.getValueAt(i,4).toString());
                    jmltotal[index]=Double.parseDouble(tbDokter.getValueAt(i,5).toString());
                    nofaktur[index]=tbDokter.getValueAt(i,6).toString();
                    stok[index]=Double.parseDouble(tbDokter.getValueAt(i,7).toString());
                    dasar[index]=Double.parseDouble(tbDokter.getValueAt(i,8).toString());
                    index++;
                }
            } catch (Exception e) {
            }
        }
        Valid.tabelKosong(tabMode);
        for(i=0;i<jml;i++){
            tabMode.addRow(new Object[]{jumlah[i],kodebarang[i],namabarang[i],satuan[i],h_retur[i],jmltotal[i],nofaktur[i],stok[i],dasar[i]});
        }
        try{
            ps=koneksi.prepareStatement(
                    "select tokobarang.kode_brng,tokobarang.nama_brng,tokobarang.kode_sat,tokobarang.dasar,tokobarang.stok "+
                    " from tokobarang where tokobarang.status='1' and tokobarang.kode_brng like ? or "+
                    " tokobarang.status='1' and tokobarang.nama_brng like ? or "+
                    " tokobarang.status='1' and tokobarang.jenis like ? order by tokobarang.nama_brng");
            try{   
                ps.setString(1,"%"+TCari.getText().trim()+"%");
                ps.setString(2,"%"+TCari.getText().trim()+"%");
                ps.setString(3,"%"+TCari.getText().trim()+"%");
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new Object[]{"",rs.getString("kode_brng"),rs.getString("nama_brng"),rs.getString("kode_sat"),rs.getDouble("dasar"),0,"",rs.getDouble("stok"),rs.getDouble("dasar")});
                }   
            }catch(Exception e){
                System.out.println(e);
            }finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }              
        }catch(SQLException e){
            System.out.println("Notifikasi : "+e);
        }
        
    }

    private void getData(){
        row=tbDokter.getSelectedRow();
        if(row!= -1){
            if(!tbDokter.getValueAt(row,0).toString().equals("")){
                kolom=tbDokter.getSelectedColumn();
                if(Valid.SetAngka(tabMode.getValueAt(row,0).toString())>0){
                    try {
                        stokbarang=Double.parseDouble(tabMode.getValueAt(row,7).toString());
                        y=Valid.SetAngka(tbDokter.getValueAt(row,0).toString());
                        if(stokbarang<y){
                            tabMode.setValueAt("",row,0);
                            JOptionPane.showMessageDialog(rootPane,"Maaf stok tidak mencukupi..!!");
                            tbDokter.requestFocus();
                        }else{
                            tbDokter.setValueAt(Double.parseDouble(tbDokter.getValueAt(row,0).toString())*Double.parseDouble(tbDokter.getValueAt(row,4).toString()), row,5);  
                        }
                    } catch (Exception e) {
                        tbDokter.setValueAt(0,row,5); 
                    }
                }
            }else{
                tbDokter.setValueAt(0,row,5);   
            }             
        }
        ttl=0;
        y=0;
        
        jml=tbDokter.getRowCount();
        for(i=0;i<jml;i++){                    
            try {
                y=Double.parseDouble(tbDokter.getValueAt(i,5).toString());                
            }catch (Exception e) {
                y=0;                
            }
            ttl=ttl+y;
        }
        LTotal.setText(Valid.SetAngka(ttl));
    }
    
    public void isCek(){
        autoNomor();
        TCari.requestFocus();
        if(akses.getjml2()>=1){
            kdptg.setEditable(false);
            btnPetugas.setEnabled(false);
            kdptg.setText(akses.getkode());
            BtnSimpan.setEnabled(akses.gettoko_retur_beli());
            BtnTambah.setEnabled(akses.gettoko_barang());
            Sequel.cariIsi("select nama from petugas where nip=?", nmptg,kdptg.getText());
        }        
    }
    
    private void autoNomor() {
        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(no_retur_beli,3),signed)),0) from tokoreturbeli where tgl_retur='"+Valid.SetTgl(TglRetur.getSelectedItem()+"")+"'","TRB"+TglRetur.getSelectedItem().toString().substring(6,10)+TglRetur.getSelectedItem().toString().substring(3,5)+TglRetur.getSelectedItem().toString().substring(0,2),3,NoRetur); 
    }
 
}

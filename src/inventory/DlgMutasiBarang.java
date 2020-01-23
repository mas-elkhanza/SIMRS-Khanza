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
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import simrskhanza.DlgCariBangsal;

public class DlgMutasiBarang extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private riwayatobat Trackobat=new riwayatobat();
    private PreparedStatement ps,psstok;
    private ResultSet rs,rsstok;
    private int jml=0,i=0,row=0,index=0,pilihan=0;
    private String[] kodebarang,namabarang,jumlah,satuan,nobatch,nofaktur;
    private double[] harga,total,stokasal,stoktujuan; 
    private DlgCariBangsal bangsal=new DlgCariBangsal(null,false);
    private double stok_asal,stok_tujuan;
    private WarnaTable2 warna=new WarnaTable2();
    public boolean tampilkanpermintaan=false;
    private boolean sukses=false;
    private String aktifkanbatch="no";
    private DlgPindahGudang pindah=new DlgPindahGudang(null,false);

    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public DlgMutasiBarang(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        tabMode=new DefaultTableModel(null,new Object[]{"Jml","Harga","Total","Kode Barang","Nama Barang","Satuan","Stok Asal","Stok Tujuan","No.Batch","No.Faktur"}){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if ((colIndex==0)||(colIndex==8)||(colIndex==9)) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                 java.lang.String.class,java.lang.Double.class,java.lang.Double.class,java.lang.String.class,
                 java.lang.String.class,java.lang.String.class,java.lang.Double.class,java.lang.Double.class,
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
                column.setPreferredWidth(40);
            }else if(i==1){
                column.setPreferredWidth(75);
            }else if(i==2){
                column.setPreferredWidth(85);
            }else if(i==3){
                column.setPreferredWidth(95);
            }else if(i==4){
                column.setPreferredWidth(300);
            }else if(i==5){
                column.setPreferredWidth(65);
            }else if(i==6){
                column.setPreferredWidth(65);
            }else if(i==7){
                column.setPreferredWidth(65);
            }else if(i==8){
                column.setPreferredWidth(70);
            }else if(i==9){
                column.setPreferredWidth(100);
            }
        }
        warna.kolom=0;
        tbDokter.setDefaultRenderer(Object.class,warna);

        kddari.setDocument(new batasInput((byte)10).getKata(kddari));
        kdke.setDocument(new batasInput((byte)10).getKata(kdke));
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
                if(akses.getform().equals("DlgMutasiBarang")){
                    if(bangsal.getTable().getSelectedRow()!= -1){   
                        if(pilihan==2){
                            kdke.setText(bangsal.getTable().getValueAt(bangsal.getTable().getSelectedRow(),0).toString());
                            nmke.setText(bangsal.getTable().getValueAt(bangsal.getTable().getSelectedRow(),1).toString());
                            kdke.requestFocus();
                        }else if(pilihan==1){
                            kddari.setText(bangsal.getTable().getValueAt(bangsal.getTable().getSelectedRow(),0).toString());
                            nmdari.setText(bangsal.getTable().getValueAt(bangsal.getTable().getSelectedRow(),1).toString());
                            kddari.requestFocus();
                        }                    
                        tampil();
                        if((!kdke.getText().equals(""))&&(!kddari.getText().equals(""))){
                            isCekStok();
                        }
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
        label11 = new widget.Label();
        BtnCari = new widget.Button();
        BtnKeluar = new widget.Button();
        panelisi3 = new widget.panelisi();
        label17 = new widget.Label();
        kddari = new widget.TextBox();
        nmdari = new widget.TextBox();
        btnDari = new widget.Button();
        btnKe = new widget.Button();
        nmke = new widget.TextBox();
        kdke = new widget.TextBox();
        label32 = new widget.Label();
        Tanggal = new widget.Tanggal();
        Keterangan = new widget.TextBox();
        label39 = new widget.Label();
        label18 = new widget.Label();

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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 253, 247)), "::[ Mutasi Antar Gudang Obat, Alkes & BHP Medis ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbDokterKeyReleased(evt);
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
        TCari.setPreferredSize(new java.awt.Dimension(270, 23));
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
        label17.setBounds(0, 10, 45, 23);

        kddari.setEditable(false);
        kddari.setName("kddari"); // NOI18N
        kddari.setPreferredSize(new java.awt.Dimension(80, 23));
        kddari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kddariKeyPressed(evt);
            }
        });
        panelisi3.add(kddari);
        kddari.setBounds(49, 10, 90, 23);

        nmdari.setEditable(false);
        nmdari.setName("nmdari"); // NOI18N
        nmdari.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(nmdari);
        nmdari.setBounds(141, 10, 257, 23);

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
        btnDari.setBounds(400, 10, 28, 23);

        btnKe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnKe.setMnemonic('1');
        btnKe.setToolTipText("Alt+1");
        btnKe.setName("btnKe"); // NOI18N
        btnKe.setPreferredSize(new java.awt.Dimension(28, 23));
        btnKe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKeActionPerformed(evt);
            }
        });
        panelisi3.add(btnKe);
        btnKe.setBounds(400, 40, 28, 23);

        nmke.setEditable(false);
        nmke.setName("nmke"); // NOI18N
        nmke.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(nmke);
        nmke.setBounds(141, 40, 257, 23);

        kdke.setEditable(false);
        kdke.setName("kdke"); // NOI18N
        kdke.setPreferredSize(new java.awt.Dimension(80, 23));
        kdke.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdkeKeyPressed(evt);
            }
        });
        panelisi3.add(kdke);
        kdke.setBounds(49, 40, 90, 23);

        label32.setText("Tanggal :");
        label32.setName("label32"); // NOI18N
        label32.setPreferredSize(new java.awt.Dimension(35, 23));
        panelisi3.add(label32);
        label32.setBounds(458, 10, 70, 23);

        Tanggal.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeyPressed(evt);
            }
        });
        panelisi3.add(Tanggal);
        Tanggal.setBounds(532, 10, 140, 23);

        Keterangan.setHighlighter(null);
        Keterangan.setName("Keterangan"); // NOI18N
        Keterangan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKeyPressed(evt);
            }
        });
        panelisi3.add(Keterangan);
        Keterangan.setBounds(532, 40, 190, 23);

        label39.setText("Keterangan :");
        label39.setName("label39"); // NOI18N
        label39.setPreferredSize(new java.awt.Dimension(35, 23));
        panelisi3.add(label39);
        label39.setBounds(458, 40, 70, 23);

        label18.setText("Ke :");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(65, 23));
        panelisi3.add(label18);
        label18.setBounds(0, 40, 45, 23);

        internalFrame1.add(panelisi3, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        pindah.tampil(" order by mutasibarang.tanggal");
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
        jml=tbDokter.getRowCount();
        index=0;
        for(i=0;i<jml;i++){
            if(Valid.SetAngka(tbDokter.getValueAt(i,0).toString())>0){
                index++;
            }
        }
        if(aktifkanbatch.equals("yes")){
            row=0;
            for(i=0;i<jml;i++){
                if((Valid.SetAngka(tbDokter.getValueAt(i,0).toString())>0)&&(tbDokter.getValueAt(i,8).toString().trim().equals("")||tbDokter.getValueAt(i,9).toString().trim().equals(""))){
                    row++;
                }
            }
        }
        
        if(nmdari.getText().trim().equals("")||kddari.getText().trim().equals("")){
            Valid.textKosong(kddari,"Stok Asal");
        }else if(nmke.getText().trim().equals("")||kdke.getText().trim().equals("")){
            Valid.textKosong(kdke,"Stok Tujuan");
        }else if(kdke.getText().trim().equals(kddari.getText().trim())){
            JOptionPane.showMessageDialog(null,"Dari dan Ke harus beda...!!!");
            kdke.requestFocus();
        }else if(aktifkanbatch.equals("yes")&&(row>0)){
            Valid.textKosong(TCari,"No.Batch/No.Faktur");
        }else if(index<=0){
            JOptionPane.showMessageDialog(null,"Maaf, silakan masukkan jumlah mutasi...!!!!");
            tbDokter.requestFocus();
        }else if(Keterangan.getText().trim().equals("")){
            Valid.textKosong(Keterangan,"Keterangan");
        }else{
            i= JOptionPane.showConfirmDialog(rootPane,"Eeiiiiiits, udah bener belum data yang hendak disimpan..??","Konfirmasi",JOptionPane.YES_NO_OPTION);
            if (i == JOptionPane.YES_OPTION) {
                Sequel.AutoComitFalse();
                sukses=true;
                for(i=0;i<tbDokter.getRowCount();i++){  
                    try {
                        if(Valid.SetAngka(tbDokter.getValueAt(i,0).toString())>0){
                            if(Sequel.menyimpantf2("mutasibarang","'"+tbDokter.getValueAt(i,3).toString()+"','"+Valid.SetAngka(tbDokter.getValueAt(i,0).toString())+"',"+
                                "'"+Valid.SetAngka(tbDokter.getValueAt(i,1).toString())+"','"+kddari.getText()+"','"+kdke.getText()+"',"+
                                "'"+Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19)+"',"+
                                "'"+Keterangan.getText()+"','"+tbDokter.getValueAt(i,8).toString()+"','"+tbDokter.getValueAt(i,9).toString()+"'",
                                "Mutasi Antar bangsal")==true){
                                    Trackobat.catatRiwayat(tbDokter.getValueAt(i,3).toString(),0,Valid.SetAngka(tbDokter.getValueAt(i,0).toString()),"Mutasi",akses.getkode(),kddari.getText(),"Simpan",tbDokter.getValueAt(i,8).toString(),tbDokter.getValueAt(i,9).toString());
                                    Sequel.menyimpan("gudangbarang","'"+tbDokter.getValueAt(i,3).toString()+"','"+kddari.getText()+"','-"+tbDokter.getValueAt(i,0).toString()+"','"+tbDokter.getValueAt(i,8).toString()+"','"+tbDokter.getValueAt(i,9).toString()+"'", 
                                        "stok=stok-"+tbDokter.getValueAt(i,0).toString()+"","kode_brng='"+tbDokter.getValueAt(i,3).toString()+"' and kd_bangsal='"+kddari.getText()+"' and no_batch='"+tbDokter.getValueAt(i,8).toString()+"' and no_faktur='"+tbDokter.getValueAt(i,9).toString()+"'");
                                    Trackobat.catatRiwayat(tbDokter.getValueAt(i,3).toString(),Valid.SetAngka(tbDokter.getValueAt(i,0).toString()),0,"Mutasi",akses.getkode(),kdke.getText(),"Simpan",tbDokter.getValueAt(i,8).toString(),tbDokter.getValueAt(i,9).toString());
                                    Sequel.menyimpan("gudangbarang","'"+tbDokter.getValueAt(i,3).toString()+"','"+kdke.getText()+"','"+tbDokter.getValueAt(i,0).toString()+"','"+tbDokter.getValueAt(i,8).toString()+"','"+tbDokter.getValueAt(i,9).toString()+"'", 
                                        "stok=stok+"+tbDokter.getValueAt(i,0).toString()+"","kode_brng='"+tbDokter.getValueAt(i,3).toString()+"' and kd_bangsal='"+kdke.getText()+"' and no_batch='"+tbDokter.getValueAt(i,8).toString()+"' and no_faktur='"+tbDokter.getValueAt(i,9).toString()+"'");
                            }else{
                                sukses=false;
                            }                                     
                        }
                    } catch (Exception e) {
                        System.out.println(e);
                    }                    
                }  
                if(sukses==true){
                    Sequel.Commit();
                    for(index=0;index<tbDokter.getRowCount();index++){   
                        tbDokter.setValueAt("",index,0); 
                        tbDokter.setValueAt(0,index,2); 
                        tbDokter.setValueAt(0,index,6);
                        tbDokter.setValueAt(0,index,7);
                    }
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
                tbDokter.setValueAt(0,i,2);
            }
}//GEN-LAST:event_ppBersihkanActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        if(tampilkanpermintaan==true){
            tampil();
        }           
    }//GEN-LAST:event_formWindowOpened

    private void kddariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kddariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nm_bangsal from bangsal where kd_bangsal='"+kddari.getText()+"'",nmdari);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select nm_bangsal from bangsal where kd_bangsal='"+kddari.getText()+"'",nmdari);
            TCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select nm_bangsal from bangsal where kd_bangsal='"+kddari.getText()+"'",nmdari);
            kdke.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnDariActionPerformed(null);
        }
    }//GEN-LAST:event_kddariKeyPressed

    private void btnDariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDariActionPerformed
        pilihan=1;
        akses.setform("DlgMutasiBarang");
        bangsal.emptTeks();
        bangsal.isCek();
        bangsal.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        bangsal.setLocationRelativeTo(internalFrame1);
        bangsal.setAlwaysOnTop(false);
        bangsal.setVisible(true);
    }//GEN-LAST:event_btnDariActionPerformed

    private void btnKeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKeActionPerformed
        akses.setform("DlgMutasiBarang");
        pilihan=2;
        bangsal.emptTeks();
        bangsal.isCek();
        bangsal.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        bangsal.setLocationRelativeTo(internalFrame1);
        bangsal.setAlwaysOnTop(false);
        bangsal.setVisible(true);
    }//GEN-LAST:event_btnKeActionPerformed

    private void kdkeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdkeKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nm_bangsal from bangsal where kd_bangsal='"+kdke.getText()+"'", nmke);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select nm_bangsal from bangsal where kd_bangsal='"+kdke.getText()+"'", nmke);
            kddari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select nm_bangsal from bangsal where kd_bangsal='"+kdke.getText()+"'", nmke);
            BtnSimpan.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnKeActionPerformed(null);
        }
    }//GEN-LAST:event_kdkeKeyPressed

    private void TanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKeyPressed
        Valid.pindah(evt,Keterangan,BtnSimpan);
    }//GEN-LAST:event_TanggalKeyPressed

    private void KeteranganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKeyPressed
        Valid.pindah(evt,Tanggal,TCari);
    }//GEN-LAST:event_KeteranganKeyPressed

    private void tbDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbDokterKeyPressed
        if(tabMode.getRowCount()!=0){
            if(evt.getKeyCode()==KeyEvent.VK_DELETE){
                try {
                    if(tbDokter.getSelectedColumn()==0){
                        tbDokter.setValueAt("",tbDokter.getSelectedRow(),0);
                        tbDokter.setValueAt(0,tbDokter.getSelectedRow(),6);
                        tbDokter.setValueAt(0,tbDokter.getSelectedRow(),2);
                        tbDokter.setValueAt(0,tbDokter.getSelectedRow(),7);
                    }
                } catch (Exception e) {
                } 
            }else if(evt.getKeyCode()==KeyEvent.VK_SHIFT){
                TCari.setText("");
                TCari.requestFocus();
            }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
                if(tbDokter.getSelectedColumn()==1){
                    TCari.setText("");
                    TCari.requestFocus();
                }
            }
        }
    }//GEN-LAST:event_tbDokterKeyPressed

    private void ppStokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppStokActionPerformed
        for(i=0;i<tbDokter.getRowCount();i++){
            try {
                stok_asal=0;   
                psstok=koneksi.prepareStatement("select ifnull(stok,'0') from gudangbarang where kd_bangsal=? and kode_brng=? and no_batch=? and no_faktur=?");
                try {
                    psstok.setString(1,kddari.getText());
                    psstok.setString(2,tabMode.getValueAt(i,3).toString());
                    psstok.setString(3,tabMode.getValueAt(i,8).toString());
                    psstok.setString(4,tabMode.getValueAt(i,9).toString());
                    rsstok=psstok.executeQuery();
                    if(rsstok.next()){
                        stok_asal=rsstok.getDouble(1);
                    }
                } catch (Exception e) {
                    System.out.println("Note : "+e);
                } finally{
                    if(rsstok!=null){
                        rsstok.close();
                    }
                    if(psstok!=null){
                        psstok.close();
                    }
                }

                stok_tujuan=0;  
                psstok=koneksi.prepareStatement("select ifnull(stok,'0') from gudangbarang where kd_bangsal=? and kode_brng=? and no_batch=? and no_faktur=?");
                try {
                    psstok.setString(1,kdke.getText());
                    psstok.setString(2,tabMode.getValueAt(i,3).toString());
                    psstok.setString(3,tabMode.getValueAt(i,8).toString());
                    psstok.setString(4,tabMode.getValueAt(i,9).toString());
                    rsstok=psstok.executeQuery();
                    if(rsstok.next()){
                        stok_tujuan=rsstok.getDouble(1);
                    } 
                } catch (Exception e) {
                    System.out.println("Note : "+e);
                } finally{
                    if(rsstok!=null){
                        rsstok.close();
                    }
                    if(psstok!=null){
                        psstok.close();
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

    private void tbDokterPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tbDokterPropertyChange
        if(this.isActive()==true){
            getData();
        }
    }//GEN-LAST:event_tbDokterPropertyChange

    private void tbDokterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDokterMouseClicked
        try {
            if(Valid.SetAngka(tabMode.getValueAt(tbDokter.getSelectedRow(),0).toString())>0){
                stok_asal=0;   
                psstok=koneksi.prepareStatement("select ifnull(stok,'0') from gudangbarang where kd_bangsal=? and kode_brng=? and no_batch=? and no_faktur=?");
                try {
                    psstok.setString(1,kddari.getText());
                    psstok.setString(2,tabMode.getValueAt(tbDokter.getSelectedRow(),3).toString());
                    psstok.setString(3,tabMode.getValueAt(tbDokter.getSelectedRow(),8).toString());
                    psstok.setString(4,tabMode.getValueAt(tbDokter.getSelectedRow(),9).toString());
                    rsstok=psstok.executeQuery();
                    if(rsstok.next()){
                        stok_asal=rsstok.getDouble(1);
                    }
                } catch (Exception e) {
                    System.out.println("Note : "+e);
                } finally{
                    if(rsstok!=null){
                        rsstok.close();
                    }
                    if(psstok!=null){
                        psstok.close();
                    }
                }

                stok_tujuan=0;  
                psstok=koneksi.prepareStatement("select ifnull(stok,'0') from gudangbarang where kd_bangsal=? and kode_brng=? and no_batch=? and no_faktur=?");
                try {
                    psstok.setString(1,kdke.getText());
                    psstok.setString(2,tabMode.getValueAt(tbDokter.getSelectedRow(),3).toString());
                    psstok.setString(3,tabMode.getValueAt(tbDokter.getSelectedRow(),8).toString());
                    psstok.setString(4,tabMode.getValueAt(tbDokter.getSelectedRow(),9).toString());
                    rsstok=psstok.executeQuery();
                    if(rsstok.next()){
                        stok_tujuan=rsstok.getDouble(1);
                    } 
                } catch (Exception e) {
                    System.out.println("Note : "+e);
                } finally{
                    if(rsstok!=null){
                        rsstok.close();
                    }
                    if(psstok!=null){
                        psstok.close();
                    }
                }

                tbDokter.setValueAt(stok_asal,tbDokter.getSelectedRow(),6);
                tbDokter.setValueAt(stok_tujuan,tbDokter.getSelectedRow(),7);
                if(Double.parseDouble(tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString())>stok_asal){
                    JOptionPane.showMessageDialog(null,"Eiiitsss, stok tidak mencukupi..!!");
                    tbDokter.setValueAt("",tbDokter.getSelectedRow(),0);
                    tbDokter.setValueAt(0,tbDokter.getSelectedRow(),2);
                }   
            }                        
        } catch (Exception e) {
        }
    }//GEN-LAST:event_tbDokterMouseClicked

    private void tbDokterKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbDokterKeyReleased
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_RIGHT)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                getData();            
            }
        }
    }//GEN-LAST:event_tbDokterKeyReleased

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgMutasiBarang dialog = new DlgMutasiBarang(new javax.swing.JFrame(), true);
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
    private widget.TextBox Kd2;
    private widget.TextBox Keterangan;
    private javax.swing.JPopupMenu Popup;
    private widget.TextBox TCari;
    private widget.Tanggal Tanggal;
    private widget.Button btnDari;
    private widget.Button btnKe;
    private widget.InternalFrame internalFrame1;
    private widget.TextBox kddari;
    private widget.TextBox kdke;
    private widget.Label label10;
    private widget.Label label11;
    private widget.Label label17;
    private widget.Label label18;
    private widget.Label label32;
    private widget.Label label39;
    private widget.TextBox nmdari;
    private widget.TextBox nmke;
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
        
        kodebarang=null;
        namabarang=null;
        satuan=null;
        jumlah=null;
        stokasal=null;
        stoktujuan=null;
        kodebarang=new String[jml];
        namabarang=new String[jml];
        satuan=new String[jml];
        jumlah=new String[jml];
        harga=new double[jml];
        total=new double[jml];
        stokasal=new double[jml];
        stoktujuan=new double[jml];
        nobatch=new String[jml];
        nofaktur=new String[jml];
        index=0;        
        for(i=0;i<row;i++){
            try {
                if(Valid.SetAngka(tbDokter.getValueAt(i,0).toString())>0){
                    jumlah[index]=tbDokter.getValueAt(i,0).toString();
                    harga[index]=Double.parseDouble(tbDokter.getValueAt(i,1).toString());
                    total[index]=Double.parseDouble(tbDokter.getValueAt(i,2).toString());
                    kodebarang[index]=tbDokter.getValueAt(i,3).toString();
                    namabarang[index]=tbDokter.getValueAt(i,4).toString();
                    satuan[index]=tbDokter.getValueAt(i,5).toString();
                    stokasal[index]=Double.parseDouble(tbDokter.getValueAt(i,6).toString());
                    stoktujuan[index]=Double.parseDouble(tbDokter.getValueAt(i,7).toString());
                    nobatch[index]=tbDokter.getValueAt(i,8).toString();
                    nofaktur[index]=tbDokter.getValueAt(i,9).toString();
                    index++;
                }
            } catch (Exception e) {
            }
        }
        Valid.tabelKosong(tabMode);
        for(i=0;i<jml;i++){
            tabMode.addRow(new Object[]{jumlah[i],harga[i],total[i],kodebarang[i],namabarang[i],satuan[i],stokasal[i],stoktujuan[i],nobatch[i],nofaktur[i]});
        }
        try{
            if(aktifkanbatch.equals("yes")){
                if(TCari.getText().trim().equals("")){
                    ps=koneksi.prepareStatement("select data_batch.kode_brng, databarang.nama_brng,databarang.kode_sat,data_batch.dasar,data_batch.no_batch,data_batch.no_faktur,  "+
                        " gudangbarang.stok from data_batch inner join databarang on data_batch.kode_brng=databarang.kode_brng "+
                        " inner join gudangbarang on data_batch.kode_brng=gudangbarang.kode_brng and data_batch.no_batch=gudangbarang.no_batch and data_batch.no_faktur=gudangbarang.no_faktur "+
                        " where gudangbarang.stok>0 and gudangbarang.kd_bangsal=? order by databarang.nama_brng");
                }else{
                    ps=koneksi.prepareStatement("select data_batch.kode_brng, databarang.nama_brng,databarang.kode_sat,data_batch.dasar,data_batch.no_batch,data_batch.no_faktur, "+
                        " gudangbarang.stok from data_batch inner join databarang on data_batch.kode_brng=databarang.kode_brng "+
                        " inner join gudangbarang on data_batch.kode_brng=gudangbarang.kode_brng and data_batch.no_batch=gudangbarang.no_batch and data_batch.no_faktur=gudangbarang.no_faktur "+
                        " where gudangbarang.stok>0 and gudangbarang.kd_bangsal=? and databarang.kode_brng like ? or "+
                        " gudangbarang.stok>0 and gudangbarang.kd_bangsal=? and databarang.nama_brng like ? order by databarang.nama_brng");
                }

                try {
                    if(TCari.getText().trim().equals("")){
                        ps.setString(1,kddari.getText());
                    }else{
                        ps.setString(1,kddari.getText());
                        ps.setString(2,"%"+TCari.getText().trim()+"%");
                        ps.setString(3,kddari.getText());
                        ps.setString(4,"%"+TCari.getText().trim()+"%");
                    }
                    rs=ps.executeQuery();
                    while(rs.next()){                
                        tabMode.addRow(new Object[]{"",rs.getDouble("dasar"),0,rs.getString("kode_brng"),rs.getString("nama_brng"),rs.getString("kode_sat"),rs.getDouble("stok"),0,rs.getString("no_batch"),rs.getString("no_faktur")});
                    } 
                } catch (Exception e) {
                    System.out.println("Note : "+e);
                } finally{
                    if(rs!=null){
                        rs.close();
                    }
                    if(ps!=null){
                        ps.close();
                    }
                }
            }else{
                if(TCari.getText().trim().equals("")){
                    ps=koneksi.prepareStatement("select databarang.kode_brng, databarang.nama_brng,databarang.kode_sat,databarang.dasar,gudangbarang.stok "+
                        " from databarang inner join gudangbarang on databarang.kode_brng=gudangbarang.kode_brng "+
                        " where gudangbarang.no_batch='' and gudangbarang.no_faktur='' and gudangbarang.stok>0 and databarang.status='1' and gudangbarang.kd_bangsal=? order by databarang.nama_brng");
                }else{
                    ps=koneksi.prepareStatement("select databarang.kode_brng, databarang.nama_brng,databarang.kode_sat,databarang.dasar,gudangbarang.stok "+
                        " from databarang inner join gudangbarang on databarang.kode_brng=gudangbarang.kode_brng "+
                        " where gudangbarang.no_batch='' and gudangbarang.no_faktur='' and gudangbarang.stok>0 and databarang.status='1' and gudangbarang.kd_bangsal=? and databarang.kode_brng like ? or "+
                        " gudangbarang.no_batch='' and gudangbarang.no_faktur='' and gudangbarang.stok>0 and databarang.status='1' and gudangbarang.kd_bangsal=? and databarang.nama_brng like ? order by databarang.nama_brng");
                }

                try {
                    if(TCari.getText().trim().equals("")){
                        ps.setString(1,kddari.getText());
                    }else{
                        ps.setString(1,kddari.getText());
                        ps.setString(2,"%"+TCari.getText().trim()+"%");
                        ps.setString(3,kddari.getText());
                        ps.setString(4,"%"+TCari.getText().trim()+"%");
                    }
                    rs=ps.executeQuery();
                    while(rs.next()){                
                        tabMode.addRow(new Object[]{"",rs.getDouble("dasar"),0,rs.getString("kode_brng"),rs.getString("nama_brng"),rs.getString("kode_sat"),rs.getDouble("stok"),0,"",""});
                    } 
                } catch (Exception e) {
                    System.out.println("Note : "+e);
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
    
    public void tampil(String nopermintaan) {
        Valid.tabelKosong(tabMode);
        try{
            kdke.setText(Sequel.cariIsi("select kd_bangsal from permintaan_medis where no_permintaan=?", nopermintaan));
            nmke.setText(Sequel.cariIsi("select nm_bangsal from bangsal where kd_bangsal=?",kdke.getText()));
            kddari.setText(Sequel.cariIsi("select kd_bangsaltujuan from permintaan_medis where no_permintaan=?", nopermintaan));
            nmdari.setText(Sequel.cariIsi("select nm_bangsal from bangsal where kd_bangsal=?",kddari.getText()));
            ps=koneksi.prepareStatement(
                "select databarang.kode_brng, databarang.nama_brng,detail_permintaan_medis.kode_sat, "+
                "detail_permintaan_medis.jumlah,databarang.dasar,(detail_permintaan_medis.jumlah*databarang.dasar) as total "+
                "from databarang inner join detail_permintaan_medis "+
                "on detail_permintaan_medis.kode_brng=databarang.kode_brng "+
                "where detail_permintaan_medis.no_permintaan=? order by databarang.nama_brng");
            try {
                ps.setString(1,nopermintaan);
                rs=ps.executeQuery();
                while(rs.next()){                
                    tabMode.addRow(new Object[]{rs.getString("jumlah"),rs.getDouble("dasar"),rs.getDouble("total"),rs.getString("kode_brng"),rs.getString("nama_brng"),rs.getString("kode_sat"),0,0,"",""});
                } 
            } catch (Exception e) {
                System.out.println("Note : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }    
            
            for(i=0;i<tbDokter.getRowCount();i++){
                if(Valid.SetAngka(tabMode.getValueAt(i,0).toString())>0){
                    if(aktifkanbatch.equals("yes")){
                        psstok=koneksi.prepareStatement(
                                "select ifnull(gudangbarang.stok,'0'),data_batch.dasar,gudangbarang.no_batch,gudangbarang.no_faktur "+
                                "from gudangbarang inner join data_batch on gudangbarang.kode_brng=data_batch.kode_brng "+
                                "where gudangbarang.stok>0 and gudangbarang.kd_bangsal=? and gudangbarang.kode_brng=? and "+
                                "gudangbarang.no_batch<>'' and gudangbarang.no_faktur<>''"+
                                "order by data_batch.tgl_kadaluarsa desc limit 1");
                        try {
                            psstok.setString(1,kddari.getText());
                            psstok.setString(2,tabMode.getValueAt(i,3).toString());
                            rsstok=psstok.executeQuery();
                            if(rsstok.next()){
                                tbDokter.setValueAt(rsstok.getDouble(1),i,6);
                                tbDokter.setValueAt(rsstok.getDouble("dasar"),i,1);
                                tbDokter.setValueAt(rsstok.getString("no_batch"),i,8);
                                tbDokter.setValueAt(rsstok.getString("no_faktur"),i,9);
                            } 
                        } catch (Exception e) {
                            System.out.println("Note : "+e);
                        } finally{
                            if(rsstok!=null){
                                rsstok.close();
                            }
                            if(psstok!=null){
                                psstok.close();
                            }
                        }
                    }
                }   
            }
            
            isCekStok2();
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        
    }

    public void isCek(){
        BtnSimpan.setEnabled(akses.getmutasi_barang());
    }

    private void getData() {
        if(tbDokter.getSelectedRow()>-1){  
            if(!tabMode.getValueAt(tbDokter.getSelectedRow(),0).toString().equals("")){
                try {
                    if(Double.parseDouble(tabMode.getValueAt(tbDokter.getSelectedRow(),0).toString())>0){
                        tbDokter.setValueAt((Double.parseDouble(tabMode.getValueAt(tbDokter.getSelectedRow(),0).toString())*Double.parseDouble(tabMode.getValueAt(tbDokter.getSelectedRow(),1).toString())),tbDokter.getSelectedRow(),2);
                        stok_asal=0;   
                        psstok=koneksi.prepareStatement("select ifnull(stok,'0') from gudangbarang where kd_bangsal=? and kode_brng=? and no_batch=? and no_faktur=?");
                        try {
                            psstok.setString(1,kddari.getText());
                            psstok.setString(2,tabMode.getValueAt(tbDokter.getSelectedRow(),3).toString());
                            psstok.setString(3,tabMode.getValueAt(tbDokter.getSelectedRow(),8).toString());
                            psstok.setString(4,tabMode.getValueAt(tbDokter.getSelectedRow(),9).toString());
                            rsstok=psstok.executeQuery();
                            if(rsstok.next()){
                                stok_asal=rsstok.getDouble(1);
                            }
                        } catch (Exception e) {
                            System.out.println("Note : "+e);
                        } finally{
                            if(rsstok!=null){
                                rsstok.close();
                            }
                            if(psstok!=null){
                                psstok.close();
                            }
                        }

                        stok_tujuan=0;  
                        psstok=koneksi.prepareStatement("select ifnull(stok,'0') from gudangbarang where kd_bangsal=? and kode_brng=? and no_batch=? and no_faktur=?");
                        try {
                            psstok.setString(1,kdke.getText());
                            psstok.setString(2,tabMode.getValueAt(tbDokter.getSelectedRow(),3).toString());
                            psstok.setString(3,tabMode.getValueAt(tbDokter.getSelectedRow(),8).toString());
                            psstok.setString(4,tabMode.getValueAt(tbDokter.getSelectedRow(),9).toString());
                            rsstok=psstok.executeQuery();
                            if(rsstok.next()){
                                stok_tujuan=rsstok.getDouble(1);
                            } 
                        } catch (Exception e) {
                            System.out.println("Note : "+e);
                        } finally{
                            if(rsstok!=null){
                                rsstok.close();
                            }
                            if(psstok!=null){
                                psstok.close();
                            }
                        }

                        tbDokter.setValueAt(stok_asal,tbDokter.getSelectedRow(),6);
                        tbDokter.setValueAt(stok_tujuan,tbDokter.getSelectedRow(),7);
                        if(Double.parseDouble(tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString())>stok_asal){
                            JOptionPane.showMessageDialog(null,"Eiiitsss, stok tidak mencukupi..!!");
                            tbDokter.setValueAt("",tbDokter.getSelectedRow(),0);
                        }  
                    }else{
                        tbDokter.setValueAt(0,tbDokter.getSelectedRow(),6);
                        tbDokter.setValueAt(0,tbDokter.getSelectedRow(),7);
                    }                        
                } catch (Exception e) {
                    tbDokter.setValueAt("",tbDokter.getSelectedRow(),0); 
                    tbDokter.setValueAt(0,tbDokter.getSelectedRow(),2); 
                    tbDokter.setValueAt(0,tbDokter.getSelectedRow(),6);
                    tbDokter.setValueAt(0,tbDokter.getSelectedRow(),7);
                }
            }
        }    
    }

    public void isCekStok(){
        for(i=0;i<tbDokter.getRowCount();i++){
            if(Valid.SetAngka(tabMode.getValueAt(i,0).toString())>0){
                try {
                    stok_asal=0;   
                    psstok=koneksi.prepareStatement("select ifnull(stok,'0') from gudangbarang where kd_bangsal=? and kode_brng=? and no_batch=? and no_faktur=?");
                    try {
                        psstok.setString(1,kddari.getText());
                        psstok.setString(2,tabMode.getValueAt(i,3).toString());
                        psstok.setString(3,tabMode.getValueAt(i,8).toString());
                        psstok.setString(4,tabMode.getValueAt(i,9).toString());
                        rsstok=psstok.executeQuery();
                        if(rsstok.next()){
                            stok_asal=rsstok.getDouble(1);
                        }
                    } catch (Exception e) {
                        System.out.println("Note : "+e);
                    } finally{
                        if(rsstok!=null){
                            rsstok.close();
                        }
                        if(psstok!=null){
                            psstok.close();
                        }
                    }

                    stok_tujuan=0;  
                    psstok=koneksi.prepareStatement("select ifnull(stok,'0') from gudangbarang where kd_bangsal=? and kode_brng=? and no_batch=? and no_faktur=?");
                    try {
                        psstok.setString(1,kdke.getText());
                        psstok.setString(2,tabMode.getValueAt(i,3).toString());
                        psstok.setString(3,tabMode.getValueAt(i,8).toString());
                        psstok.setString(4,tabMode.getValueAt(i,9).toString());
                        rsstok=psstok.executeQuery();
                        if(rsstok.next()){
                            stok_tujuan=rsstok.getDouble(1);
                        } 
                    } catch (Exception e) {
                        System.out.println("Note : "+e);
                    } finally{
                        if(rsstok!=null){
                            rsstok.close();
                        }
                        if(psstok!=null){
                            psstok.close();
                        }
                    }

                    tbDokter.setValueAt(stok_asal,i,6);
                    tbDokter.setValueAt(stok_tujuan,i,7);
                    if(Double.parseDouble(tbDokter.getValueAt(i,0).toString())>stok_asal){
                        JOptionPane.showMessageDialog(null,"Eiiitsss, stok tidak mencukupi..!!");
                        tbDokter.setValueAt("",i,0);
                    }
                } catch (Exception e) {
                    tbDokter.setValueAt(0,i,6);
                    tbDokter.setValueAt(0,i,7);
                }
            }
        }
    }
    
    public void isCekStok2(){
        for(i=0;i<tbDokter.getRowCount();i++){
            if(Valid.SetAngka(tabMode.getValueAt(i,0).toString())>0){
                try {
                    stok_asal=0;   
                    if(aktifkanbatch.equals("yes")){
                        psstok=koneksi.prepareStatement("select ifnull(stok,'0') from gudangbarang where kd_bangsal=? and kode_brng=? and no_batch=? and no_faktur=? and no_batch<>'' and no_faktur<>''");
                    }else{
                        psstok=koneksi.prepareStatement("select ifnull(stok,'0') from gudangbarang where kd_bangsal=? and kode_brng=? and no_batch=? and no_faktur=? and no_batch='' and no_faktur=''");
                    }
                        
                    try {
                        psstok.setString(1,kddari.getText());
                        psstok.setString(2,tabMode.getValueAt(i,3).toString());
                        psstok.setString(3,tabMode.getValueAt(i,8).toString());
                        psstok.setString(4,tabMode.getValueAt(i,9).toString());
                        rsstok=psstok.executeQuery();
                        if(rsstok.next()){
                            stok_asal=rsstok.getDouble(1);
                        }
                    } catch (Exception e) {
                        System.out.println("Note : "+e);
                    } finally{
                        if(rsstok!=null){
                            rsstok.close();
                        }
                        if(psstok!=null){
                            psstok.close();
                        }
                    }

                    stok_tujuan=0;  
                    if(aktifkanbatch.equals("yes")){
                        psstok=koneksi.prepareStatement("select ifnull(stok,'0') from gudangbarang where kd_bangsal=? and kode_brng=? and no_batch=? and no_faktur=? and no_batch<>'' and no_faktur<>''");
                    }else{
                        psstok=koneksi.prepareStatement("select ifnull(stok,'0') from gudangbarang where kd_bangsal=? and kode_brng=? and no_batch=? and no_faktur=? and no_batch='' and no_faktur=''");
                    }
                    try {
                        psstok.setString(1,kdke.getText());
                        psstok.setString(2,tabMode.getValueAt(i,3).toString());
                        psstok.setString(3,tabMode.getValueAt(i,8).toString());
                        psstok.setString(4,tabMode.getValueAt(i,9).toString());
                        rsstok=psstok.executeQuery();
                        if(rsstok.next()){
                            stok_tujuan=rsstok.getDouble(1);
                        } 
                    } catch (Exception e) {
                        System.out.println("Note : "+e);
                    } finally{
                        if(rsstok!=null){
                            rsstok.close();
                        }
                        if(psstok!=null){
                            psstok.close();
                        }
                    }

                    tbDokter.setValueAt(stok_asal,i,6);
                    tbDokter.setValueAt(stok_tujuan,i,7);
                    if(Double.parseDouble(tbDokter.getValueAt(i,0).toString())>stok_asal){
                        JOptionPane.showMessageDialog(null,"Eiiitsss, stok tidak mencukupi..!!");
                        tbDokter.setValueAt("",i,0);
                    }else{
                        tbDokter.setValueAt((Double.parseDouble(tabMode.getValueAt(i,0).toString())*Double.parseDouble(tabMode.getValueAt(i,1).toString())),i,2);
                    }
                } catch (Exception e) {
                    tbDokter.setValueAt(0,i,6);
                    tbDokter.setValueAt(0,i,7);
                }
            }
        }
    }
}

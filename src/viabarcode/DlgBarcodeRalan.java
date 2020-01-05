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

package viabarcode;

import fungsi.WarnaTable;
import fungsi.WarnaTable2;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import inventory.riwayatobat;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import simrskhanza.DlgRawatJalan;

/**
 *
 * @author dosen
 */
public final class DlgBarcodeRalan extends javax.swing.JDialog {
    private final DefaultTableModel TabModeTindakan,tabModeObat;
    private int jml=0,i=0,index=0,z=0;
    private PreparedStatement pstindakan,pstindakan2,pstindakan3,pstindakan4,psobat,psset_tarif,pscarikapasitas;
    private ResultSet rstindakan,rsset_tarif,rsobat,carikapasitas;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private boolean[] pilih; 
    private String[] kode,nama,kategori;
    private double[] totaltnd,bagianrs,bhp,jmdokter,jmperawat,kso,menejemen;
    private String kd_pj="",kd_poli="",kd_dokter="",poli_ralan="Yes", cara_bayar_ralan="Yes";    
    private double[] jumlah,harga,stok,eb,tsl,beli;
    private String[] kodebarang,namabarang,kodesatuan,letakbarang,namajenis,nobatch,nofaktur;
    private String bangsal="";
    private double embalase=0,tuslah=0,kenaikan=0,stokbarang=0,j=0;
    private WarnaTable2 warna=new WarnaTable2();
    private riwayatobat Trackobat=new riwayatobat();
    private String aktifkanbatch="no";
            
    /**
     * Creates new form DlgPenyakit
     * @param parent
     * @param modal
     */
    public DlgBarcodeRalan(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(10,2);
        setSize(656,250);
        TabModeTindakan=new DefaultTableModel(null,new Object[]{"P","Kode","Nama Perawatan","Kategori Perawatan","Tarif/Biaya","Bagian RS","BHP","JM Dokter","JM Perawat","KSO","Menejemen"}){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class,  
                java.lang.Object.class,java.lang.Double.class,java.lang.Double.class,
                java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,
                java.lang.Double.class,java.lang.Double.class
             };
             
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbTindakan.setModel(TabModeTindakan);
        tbTindakan.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbTindakan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        
        for (i = 0; i < 11; i++) {
            TableColumn column = tbTindakan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(90);
            }else if(i==2){
                column.setPreferredWidth(400);
            }else if(i==3){
                column.setPreferredWidth(150);
            }else if(i==5){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==6){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==7){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==8){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==9){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==10){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else{
                column.setPreferredWidth(120);
            }
        }
        
        tbTindakan.setDefaultRenderer(Object.class, new WarnaTable());
        TCariTindakan.setDocument(new batasInput((byte)100).getKata(TCariTindakan));
        
        tabModeObat=new DefaultTableModel(null,new Object[]{
            "K","Jml","Kode Barang","Nama Barang","Satuan","Kandungan","Harga(Rp)","Stok","Jenis Obat","Embalase","Tuslah","H.Beli","No.Batch","No.Faktur"
            }){
            @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if ((colIndex==0)||(colIndex==1)||(colIndex==9)||(colIndex==10)||(colIndex==12)||(colIndex==13)) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                java.lang.Object.class, java.lang.Object.class, java.lang.Double.class, java.lang.Double.class, 
                java.lang.Object.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class,
                java.lang.Object.class, java.lang.Object.class
             };
             /*Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
             };*/
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbObat.setModel(tabModeObat);
        //tbPenyakit.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbPenyakit.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (i = 0; i < 14; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(45);
            }else if(i==2){
                column.setPreferredWidth(70);
            }else if(i==3){
                column.setPreferredWidth(200);
            }else if(i==4){
                column.setPreferredWidth(80);
            }else if(i==5){
                column.setPreferredWidth(100);
            }else if(i==6){
                column.setPreferredWidth(80);
            }else if(i==7){
                column.setPreferredWidth(40);
            }else if(i==8){
                column.setPreferredWidth(80);
            }else if(i==9){
                column.setPreferredWidth(65);
            }else if(i==10){
                column.setPreferredWidth(65);
            }else if(i==11){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==12){
                column.setPreferredWidth(65);
            }else if(i==13){
                column.setPreferredWidth(100);
            }       
        }
        warna.kolom=1;
        tbObat.setDefaultRenderer(Object.class,warna);
        TCariObat.setDocument(new batasInput((byte)100).getKata(TCariObat));
        if(koneksiDB.CARICEPAT().equals("aktif")){
            TCariTindakan.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(TCariTindakan.getText().length()>2){
                        tampiltindakan();
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(TCariTindakan.getText().length()>2){
                        tampiltindakan();
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(TCariTindakan.getText().length()>2){
                        tampiltindakan();
                    }
                }
            });
            TCariObat.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(TCariObat.getText().length()>2){
                        tampilobat();
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(TCariObat.getText().length()>2){
                        tampilobat();
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(TCariObat.getText().length()>2){
                        tampilobat();
                    }if(TCariObat.getText().length()>2){
                        tampilobat();
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

        internalFrame1 = new widget.InternalFrame();
        FormInput = new widget.PanelBiasa();
        jLabel5 = new widget.Label();
        NoRawat = new widget.TextBox();
        panelisi4 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        jLabel1 = new javax.swing.JLabel();
        BtnCari = new widget.Button();
        BtnKeluar = new widget.Button();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        panelisi5 = new widget.panelisi();
        label11 = new widget.Label();
        TCariTindakan = new widget.TextBox();
        BtnAllPeriksa = new widget.Button();
        Scroll2 = new widget.ScrollPane();
        tbTindakan = new widget.Table();
        jPanel2 = new javax.swing.JPanel();
        panelisi6 = new widget.panelisi();
        label12 = new widget.Label();
        TCariObat = new widget.TextBox();
        BtnAllBhp = new widget.Button();
        Scroll1 = new widget.ScrollPane();
        tbObat = new widget.Table();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 253, 247)), "::[ Input Tindakan & Obat di Rawat Jalan Via Barcode No.Rawat ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        FormInput.setBackground(new java.awt.Color(215, 225, 215));
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(710, 60));
        FormInput.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 2, 9));

        jLabel5.setText("Barcode No.Rawat :");
        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N
        jLabel5.setPreferredSize(new java.awt.Dimension(330, 40));
        FormInput.add(jLabel5);

        NoRawat.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        NoRawat.setName("NoRawat"); // NOI18N
        NoRawat.setPreferredSize(new java.awt.Dimension(370, 40));
        NoRawat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoRawatKeyPressed(evt);
            }
        });
        FormInput.add(NoRawat);

        internalFrame1.add(FormInput, java.awt.BorderLayout.PAGE_START);

        panelisi4.setName("panelisi4"); // NOI18N
        panelisi4.setPreferredSize(new java.awt.Dimension(100, 55));
        panelisi4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

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
        panelisi4.add(BtnSimpan);

        jLabel1.setName("jLabel1"); // NOI18N
        jLabel1.setPreferredSize(new java.awt.Dimension(383, 23));
        panelisi4.add(jLabel1);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnCari.setMnemonic('E');
        BtnCari.setText("Cari");
        BtnCari.setToolTipText("Alt+E");
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
        panelisi4.add(BtnCari);

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
        panelisi4.add(BtnKeluar);

        internalFrame1.add(panelisi4, java.awt.BorderLayout.PAGE_END);

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(816, 102));
        jPanel1.setLayout(new java.awt.GridLayout(2, 1));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(241, 246, 236)), ".: Tindakan", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setPreferredSize(new java.awt.Dimension(300, 102));
        jPanel3.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi5.setBorder(null);
        panelisi5.setName("panelisi5"); // NOI18N
        panelisi5.setPreferredSize(new java.awt.Dimension(100, 43));
        panelisi5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        label11.setText("Keyword :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(75, 23));
        panelisi5.add(label11);

        TCariTindakan.setToolTipText("Alt+C");
        TCariTindakan.setName("TCariTindakan"); // NOI18N
        TCariTindakan.setPreferredSize(new java.awt.Dimension(560, 23));
        TCariTindakan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariTindakanKeyPressed(evt);
            }
        });
        panelisi5.add(TCariTindakan);

        BtnAllPeriksa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAllPeriksa.setMnemonic('2');
        BtnAllPeriksa.setToolTipText("Alt+2");
        BtnAllPeriksa.setName("BtnAllPeriksa"); // NOI18N
        BtnAllPeriksa.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnAllPeriksa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAllPeriksaActionPerformed(evt);
            }
        });
        BtnAllPeriksa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnAllPeriksaKeyPressed(evt);
            }
        });
        panelisi5.add(BtnAllPeriksa);

        jPanel3.add(panelisi5, java.awt.BorderLayout.PAGE_END);

        Scroll2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(245, 255, 235)));
        Scroll2.setName("Scroll2"); // NOI18N
        Scroll2.setOpaque(true);

        tbTindakan.setToolTipText("");
        tbTindakan.setName("tbTindakan"); // NOI18N
        tbTindakan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbTindakanKeyPressed(evt);
            }
        });
        Scroll2.setViewportView(tbTindakan);

        jPanel3.add(Scroll2, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel3);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(241, 246, 236)), ".: Obat & BHP", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        jPanel2.setName("jPanel2"); // NOI18N
        jPanel2.setPreferredSize(new java.awt.Dimension(350, 102));
        jPanel2.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi6.setBorder(null);
        panelisi6.setName("panelisi6"); // NOI18N
        panelisi6.setPreferredSize(new java.awt.Dimension(100, 43));
        panelisi6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        label12.setText("Keyword :");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(75, 23));
        panelisi6.add(label12);

        TCariObat.setToolTipText("Alt+C");
        TCariObat.setName("TCariObat"); // NOI18N
        TCariObat.setPreferredSize(new java.awt.Dimension(560, 23));
        TCariObat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariObatKeyPressed(evt);
            }
        });
        panelisi6.add(TCariObat);

        BtnAllBhp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAllBhp.setMnemonic('2');
        BtnAllBhp.setToolTipText("Alt+2");
        BtnAllBhp.setName("BtnAllBhp"); // NOI18N
        BtnAllBhp.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnAllBhp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAllBhpActionPerformed(evt);
            }
        });
        BtnAllBhp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnAllBhpKeyPressed(evt);
            }
        });
        panelisi6.add(BtnAllBhp);

        jPanel2.add(panelisi6, java.awt.BorderLayout.PAGE_END);

        Scroll1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(245, 255, 235)));
        Scroll1.setName("Scroll1"); // NOI18N
        Scroll1.setOpaque(true);

        tbObat.setToolTipText("");
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
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbObatKeyReleased(evt);
            }
        });
        Scroll1.setViewportView(tbObat);

        jPanel2.add(Scroll1, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel2);

        internalFrame1.add(jPanel1, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        
    }//GEN-LAST:event_formWindowClosed

    private void NoRawatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoRawatKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            setNoRm(NoRawat.getText());
            tampiltindakan();
            tampilobat();
        }
    }//GEN-LAST:event_NoRawatKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(aktifkanbatch.equals("yes")){
            index=0;
            jml=tbObat.getRowCount();
            for(i=0;i<jml;i++){
                if((Valid.SetAngka(tbObat.getValueAt(i,1).toString())>0)&&(tbObat.getValueAt(i,12).toString().trim().equals("")||tbObat.getValueAt(i,13).toString().trim().equals(""))){
                    index++;
                }
            }
        }   
        
        if(NoRawat.getText().trim().equals("")||kd_dokter.equals("")){
            Valid.textKosong(TCariTindakan,"Data");
        }else if(aktifkanbatch.equals("yes")&&(index>0)){
            Valid.textKosong(TCariObat,"No.Batch/No.Faktur");
        }else if(bangsal.equals("")){
            Valid.textKosong(TCariObat,"Lokasi");
        }else{
            if(Sequel.cariRegistrasi(NoRawat.getText())>0){
                JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi, silahkan hubungi bagian kasir/keuangan ..!!");
                NoRawat.requestFocus();
            }else{
                for(i=0;i<tbTindakan.getRowCount();i++){ 
                    if(tbTindakan.getValueAt(i,0).toString().equals("true")){
                        Sequel.menyimpan("rawat_jl_dr","?,?,?,?,?,?,?,?,?,?,?,'Belum'", "Tindakan",11,new String[]{
                            NoRawat.getText(),tbTindakan.getValueAt(i,1).toString(),kd_dokter,
                            Sequel.cariIsi("select current_date()"),Sequel.cariIsi("select current_time()"),
                            tbTindakan.getValueAt(i,5).toString(),tbTindakan.getValueAt(i,6).toString(),
                            tbTindakan.getValueAt(i,7).toString(),tbTindakan.getValueAt(i,8).toString(),
                            tbTindakan.getValueAt(i,9).toString(),tbTindakan.getValueAt(i,4).toString()
                        });
                    }                    
                }

                for(i=0;i<tbObat.getRowCount();i++){ 
                    if(Valid.SetAngka(tbObat.getValueAt(i,1).toString())>0){
                        try {
                            if(tbObat.getValueAt(i,0).toString().equals("true")){
                                pscarikapasitas= koneksi.prepareStatement("select IFNULL(kapasitas,1) from databarang where kode_brng=?");                                      
                                try {
                                    pscarikapasitas.setString(1,tbObat.getValueAt(i,2).toString());
                                    carikapasitas=pscarikapasitas.executeQuery();
                                    if(carikapasitas.next()){ 
                                        if(Sequel.menyimpantf2("detail_pemberian_obat","?,?,?,?,?,?,?,?,?,?,?,?,?,?","data",14,new String[]{
                                            Sequel.cariIsi("select current_date()"),Sequel.cariIsi("select current_time()"),NoRawat.getText(),
                                            tbObat.getValueAt(i,2).toString(),tbObat.getValueAt(i,11).toString(),tbObat.getValueAt(i,6).toString(),
                                            ""+(Double.parseDouble(tbObat.getValueAt(i,1).toString())/carikapasitas.getDouble(1)),
                                            tbObat.getValueAt(i,9).toString(),tbObat.getValueAt(i,10).toString(),
                                            ""+(Double.parseDouble(tbObat.getValueAt(i,9).toString())+Double.parseDouble(tbObat.getValueAt(i,10).toString())+(Double.parseDouble(tbObat.getValueAt(i,6).toString())*(Double.parseDouble(tbObat.getValueAt(i,1).toString())/carikapasitas.getDouble(1)))),"Ralan",bangsal,
                                            tbObat.getValueAt(i,12).toString(),tbObat.getValueAt(i,13).toString()
                                        })==true){
                                            Trackobat.catatRiwayat(tbObat.getValueAt(i,2).toString(),0,(Double.parseDouble(tbObat.getValueAt(i,1).toString())/carikapasitas.getDouble(1)),"Pemberian Obat",akses.getkode(),bangsal,"Simpan",tbObat.getValueAt(i,12).toString(),tbObat.getValueAt(i,13).toString());
                                            Sequel.menyimpan("gudangbarang","'"+tbObat.getValueAt(i,2).toString()+"','"+bangsal+"','-"+(Double.parseDouble(tbObat.getValueAt(i,1).toString())/carikapasitas.getDouble(1))+"','"+tbObat.getValueAt(i,12).toString()+"','"+tbObat.getValueAt(i,13).toString()+"'", 
                                                         "stok=stok-'"+(Double.parseDouble(tbObat.getValueAt(i,1).toString())/carikapasitas.getDouble(1))+"'","kode_brng='"+tbObat.getValueAt(i,2).toString()+"' and kd_bangsal='"+bangsal+"' and no_batch='"+tbObat.getValueAt(i,12).toString()+"' and no_faktur='"+tbObat.getValueAt(i,13).toString()+"'");  
                                            if(aktifkanbatch.equals("yes")){
                                                Sequel.mengedit3("data_batch","no_batch=? and kode_brng=? and no_faktur=?","sisa=sisa-?",4,new String[]{
                                                    ""+(Double.parseDouble(tbObat.getValueAt(i,1).toString())/carikapasitas.getDouble(1)),
                                                    tbObat.getValueAt(i,12).toString(),tbObat.getValueAt(i,2).toString(),tbObat.getValueAt(i,13).toString()
                                                });
                                            }
                                        }else{
                                            JOptionPane.showMessageDialog(null,"Gagal Menyimpan, Kemungkinan ada data sama/kapasitas tidak ditemukan..!!");
                                        }  
                                    }else{
                                        if(Sequel.menyimpantf("detail_pemberian_obat","?,?,?,?,?,?,?,?,?,?,?,?,?,?","data",14,new String[]{
                                            Sequel.cariIsi("select current_date()"),Sequel.cariIsi("select current_time()"),NoRawat.getText(),tbObat.getValueAt(i,2).toString(),tbObat.getValueAt(i,11).toString(),
                                            tbObat.getValueAt(i,6).toString(),""+Double.parseDouble(tbObat.getValueAt(i,1).toString()),tbObat.getValueAt(i,9).toString(),tbObat.getValueAt(i,10).toString(),
                                            ""+(Double.parseDouble(tbObat.getValueAt(i,9).toString())+Double.parseDouble(tbObat.getValueAt(i,10).toString())+(Double.parseDouble(tbObat.getValueAt(i,6).toString())*Double.parseDouble(tbObat.getValueAt(i,1).toString()))),
                                            "Ralan",bangsal,tbObat.getValueAt(i,12).toString(),tbObat.getValueAt(i,13).toString()
                                        })==true){
                                            Trackobat.catatRiwayat(tbObat.getValueAt(i,2).toString(),0,Double.parseDouble(tbObat.getValueAt(i,1).toString()),"Pemberian Obat",akses.getkode(),bangsal,"Simpan",tbObat.getValueAt(i,12).toString(),tbObat.getValueAt(i,13).toString());
                                            Sequel.menyimpan("gudangbarang","'"+tbObat.getValueAt(i,2).toString()+"','"+bangsal+"','-"+Double.parseDouble(tbObat.getValueAt(i,1).toString())+"','"+tbObat.getValueAt(i,12).toString()+"','"+tbObat.getValueAt(i,13).toString()+"'", 
                                                         "stok=stok-'"+Double.parseDouble(tbObat.getValueAt(i,1).toString())+"'","kode_brng='"+tbObat.getValueAt(i,2).toString()+"' and kd_bangsal='"+bangsal+"' and no_batch='"+tbObat.getValueAt(i,12).toString()+"' and no_faktur='"+tbObat.getValueAt(i,13).toString()+"'");   
                                            if(aktifkanbatch.equals("yes")){
                                                Sequel.mengedit3("data_batch","no_batch=? and kode_brng=? and no_faktur=?","sisa=sisa-?",4,new String[]{
                                                    ""+Double.parseDouble(tbObat.getValueAt(i,1).toString()),tbObat.getValueAt(i,12).toString(),tbObat.getValueAt(i,2).toString(),tbObat.getValueAt(i,13).toString()
                                                });
                                            }
                                        }                                   
                                    }
                                } catch (Exception e) {
                                    System.out.println("Notifikasi Kapasitas : "+e);
                                } finally{
                                    if(carikapasitas!=null){
                                        carikapasitas.close();
                                    }
                                    if(pscarikapasitas!=null){
                                        pscarikapasitas.close();
                                    }
                                }
                            }else{
                                if(Sequel.menyimpantf("detail_pemberian_obat","?,?,?,?,?,?,?,?,?,?,?,?,?,?","data",14,new String[]{
                                    Sequel.cariIsi("select current_date()"),Sequel.cariIsi("select current_time()"),NoRawat.getText(),tbObat.getValueAt(i,2).toString(),tbObat.getValueAt(i,11).toString(),
                                    tbObat.getValueAt(i,6).toString(),""+Double.parseDouble(tbObat.getValueAt(i,1).toString()),tbObat.getValueAt(i,9).toString(),tbObat.getValueAt(i,10).toString(),
                                    ""+(Double.parseDouble(tbObat.getValueAt(i,9).toString())+Double.parseDouble(tbObat.getValueAt(i,10).toString())+(Double.parseDouble(tbObat.getValueAt(i,6).toString())*Double.parseDouble(tbObat.getValueAt(i,1).toString()))),
                                    "Ralan",bangsal,tbObat.getValueAt(i,12).toString(),tbObat.getValueAt(i,13).toString()
                                })==true){
                                    Trackobat.catatRiwayat(tbObat.getValueAt(i,2).toString(),0,Double.parseDouble(tbObat.getValueAt(i,1).toString()),"Pemberian Obat",akses.getkode(),bangsal,"Simpan",tbObat.getValueAt(i,12).toString(),tbObat.getValueAt(i,13).toString());
                                    Sequel.menyimpan("gudangbarang","'"+tbObat.getValueAt(i,2).toString()+"','"+bangsal+"','-"+Double.parseDouble(tbObat.getValueAt(i,1).toString())+"','"+tbObat.getValueAt(i,12).toString()+"','"+tbObat.getValueAt(i,13).toString()+"'", 
                                                 "stok=stok-'"+Double.parseDouble(tbObat.getValueAt(i,1).toString())+"'","kode_brng='"+tbObat.getValueAt(i,2).toString()+"' and kd_bangsal='"+bangsal+"' and no_batch='"+tbObat.getValueAt(i,12).toString()+"' and no_faktur='"+tbObat.getValueAt(i,13).toString()+"'");   
                                    if(aktifkanbatch.equals("yes")){
                                        Sequel.mengedit3("data_batch","no_batch=? and kode_brng=? and no_faktur=?","sisa=sisa-?",4,new String[]{
                                            ""+Double.parseDouble(tbObat.getValueAt(i,1).toString()),tbObat.getValueAt(i,12).toString(),tbObat.getValueAt(i,2).toString(),tbObat.getValueAt(i,13).toString()
                                        });
                                    }
                                }                                  
                            }  
                        } catch (Exception e) {
                            System.out.println("Notif Simpan : "+e);
                        }
                    }
                }

                NoRawat.setText("");
                TCariObat.setText("");
                TCariTindakan.setText("");
                Valid.tabelKosong(TabModeTindakan);
                Valid.tabelKosong(tabModeObat);        
                NoRawat.requestFocus();
                JOptionPane.showMessageDialog(rootPane,"Proses Selesai");
            }
        }
    }//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        
    }//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        
    }//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnAllPeriksaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllPeriksaActionPerformed
        TCariTindakan.setText("");
        tampiltindakan();
    }//GEN-LAST:event_BtnAllPeriksaActionPerformed

    private void BtnAllPeriksaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllPeriksaKeyPressed
       
    }//GEN-LAST:event_BtnAllPeriksaKeyPressed

    private void TCariObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariObatKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnSimpan.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            TCariTindakan.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            tbObat.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            tampilobat();
        }
    }//GEN-LAST:event_TCariObatKeyPressed

    private void BtnAllBhpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllBhpActionPerformed
        TCariObat.setText("");
        tampilobat();
    }//GEN-LAST:event_BtnAllBhpActionPerformed

    private void BtnAllBhpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllBhpKeyPressed
        
    }//GEN-LAST:event_BtnAllBhpKeyPressed

    private void tbTindakanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbTindakanKeyPressed
        if(tbTindakan.getRowCount()!=0){
            if(evt.getKeyCode()==KeyEvent.VK_ENTER){
                try {
                    i=tbTindakan.getSelectedColumn();
                    if(i==1){
                        if(tbTindakan.getSelectedRow()>-1){
                          tbTindakan.setValueAt(true,tbTindakan.getSelectedRow(),0);   
                        }                               
                        TCariTindakan.setText("");
                        TCariTindakan.requestFocus();
                    }           
                } catch (java.lang.NullPointerException e) {
                }
            }else if(evt.getKeyCode()==KeyEvent.VK_SHIFT){
                TCariTindakan.setText("");
                TCariTindakan.requestFocus();
            }
        }
    }//GEN-LAST:event_tbTindakanKeyPressed

    private void tbObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbObatKeyPressed
        if(tbObat.getRowCount()!=0){
            if(evt.getKeyCode()==KeyEvent.VK_ENTER){
                try {
                    i=tbObat.getSelectedColumn();
                    if(i==2){
                        TCariObat.setText("");
                        TCariObat.requestFocus();
                    }else if(i==9){
                        if(tbObat.getValueAt(tbObat.getSelectedRow(),i).toString().equals("0")) {
                            tbObat.setValueAt(embalase,tbObat.getSelectedRow(),i);
                        }
                    }else if(i==10){
                        if(tbObat.getValueAt(tbObat.getSelectedRow(),i).toString().equals("0")) {
                            tbObat.setValueAt(tuslah,tbObat.getSelectedRow(),i);
                        }
                        TCariObat.setText("");
                        TCariObat.requestFocus();
                    }
                } catch (java.lang.NullPointerException e) {
                }
            }else if(evt.getKeyCode()==KeyEvent.VK_DELETE){
                i=tbObat.getSelectedRow();
                if(i!= -1){
                    tbObat.setValueAt("",i,1);
                }
            }else if(evt.getKeyCode()==KeyEvent.VK_SHIFT){
                TCariObat.requestFocus();
            }   
        }
    }//GEN-LAST:event_tbObatKeyPressed

    private void TCariTindakanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariTindakanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            TCariObat.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            NoRawat.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            tbTindakan.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            tampiltindakan();
        }
    }//GEN-LAST:event_TCariTindakanKeyPressed

    private void tbObatKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbObatKeyReleased
        if(tabModeObat.getRowCount()!=0){
            if(evt.getKeyCode()==KeyEvent.VK_ENTER){
                try {                                     
                    getDataObat();                     
                    TCariObat.setText("");
                    TCariObat.requestFocus();
                } catch (java.lang.NullPointerException e) {
                }
            }else if((evt.getKeyCode()==KeyEvent.VK_RIGHT)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {                                     
                    getDataObat();           
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }//GEN-LAST:event_tbObatKeyReleased

    private void tbObatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbObatMouseClicked
        if(tabModeObat.getRowCount()!=0){
            try {                  
                getDataObat();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbObatMouseClicked

    private void tbObatPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tbObatPropertyChange
        if((this.isVisible()==true)&&(!NoRawat.getText().equals(""))){
              getDataObat();
        }
    }//GEN-LAST:event_tbObatPropertyChange

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        NoRawat.setText("");
        TCariObat.setText("");
        TCariTindakan.setText("");
        Valid.tabelKosong(TabModeTindakan);
        Valid.tabelKosong(tabModeObat);        
        embalase=Sequel.cariIsiAngka("select embalase_per_obat from set_embalase");
        tuslah=Sequel.cariIsiAngka("select tuslah_per_obat from set_embalase");
        NoRawat.requestFocus();
    }//GEN-LAST:event_formWindowOpened

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        if(NoRawat.getText().trim().equals("")){
            Valid.textKosong(NoRawat,"Pasien");
        }else{
            DlgRawatJalan dlgrwjl2=new DlgRawatJalan(null,false);
            dlgrwjl2.isCek();
            dlgrwjl2.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            dlgrwjl2.setLocationRelativeTo(internalFrame1);
            dlgrwjl2.SetPoli(kd_poli);
            dlgrwjl2.SetPj(kd_pj);
            dlgrwjl2.setNoRm(NoRawat.getText(),new Date(),new Date());    
            dlgrwjl2.setVisible(true);
        }
    }//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            //BtnEditActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnSimpan,BtnKeluar);
        }
    }//GEN-LAST:event_BtnCariKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgBarcodeRalan dialog = new DlgBarcodeRalan(new javax.swing.JFrame(), true);
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
    private widget.Button BtnAllBhp;
    private widget.Button BtnAllPeriksa;
    private widget.Button BtnCari;
    private widget.Button BtnKeluar;
    private widget.Button BtnSimpan;
    private widget.PanelBiasa FormInput;
    private widget.TextBox NoRawat;
    private widget.ScrollPane Scroll1;
    private widget.ScrollPane Scroll2;
    private widget.TextBox TCariObat;
    private widget.TextBox TCariTindakan;
    private widget.InternalFrame internalFrame1;
    private javax.swing.JLabel jLabel1;
    private widget.Label jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private widget.Label label11;
    private widget.Label label12;
    private widget.panelisi panelisi4;
    private widget.panelisi panelisi5;
    private widget.panelisi panelisi6;
    private widget.Table tbObat;
    private widget.Table tbTindakan;
    // End of variables declaration//GEN-END:variables

    public void tampiltindakan() {
        try{     
            jml=0;
            for(i=0;i<tbTindakan.getRowCount();i++){
                if(tbTindakan.getValueAt(i,0).toString().equals("true")){
                    jml++;
                }
            }

            pilih=null;
            pilih=new boolean[jml]; 
            kode=null;
            kode=new String[jml];
            nama=null;
            nama=new String[jml];
            kategori=null;
            kategori=new String[jml];
            totaltnd=null;
            totaltnd=new double[jml];  
            bagianrs=null;
            bagianrs=new double[jml];
            bhp=null;
            bhp=new double[jml];
            jmdokter=null;
            jmdokter=new double[jml];
            jmperawat=null;
            jmperawat=new double[jml];
            kso=null;
            kso=new double[jml];
            menejemen=null;
            menejemen=new double[jml];

            index=0;        
            for(i=0;i<tbTindakan.getRowCount();i++){
                if(tbTindakan.getValueAt(i,0).toString().equals("true")){
                    pilih[index]=true;
                    kode[index]=tbTindakan.getValueAt(i,1).toString();
                    nama[index]=tbTindakan.getValueAt(i,2).toString();
                    kategori[index]=tbTindakan.getValueAt(i,3).toString();
                    totaltnd[index]=Double.parseDouble(tbTindakan.getValueAt(i,4).toString());
                    bagianrs[index]=Double.parseDouble(tbTindakan.getValueAt(i,5).toString());
                    bhp[index]=Double.parseDouble(tbTindakan.getValueAt(i,6).toString());
                    jmdokter[index]=Double.parseDouble(tbTindakan.getValueAt(i,7).toString());
                    jmperawat[index]=Double.parseDouble(tbTindakan.getValueAt(i,8).toString());    
                    kso[index]=Double.parseDouble(tbTindakan.getValueAt(i,9).toString());   
                    menejemen[index]=Double.parseDouble(tbTindakan.getValueAt(i,10).toString());                   
                    index++;
                }
            }       

            Valid.tabelKosong(TabModeTindakan);

            for(i=0;i<jml;i++){
                TabModeTindakan.addRow(new Object[] {
                    pilih[i],kode[i],nama[i],kategori[i],totaltnd[i],bagianrs[i],bhp[i],jmdokter[i],jmperawat[i],kso[i],menejemen[i]
                });
            }
            
            pstindakan=koneksi.prepareStatement("select jns_perawatan.kd_jenis_prw,jns_perawatan.nm_perawatan,kategori_perawatan.nm_kategori,"+
                   "jns_perawatan.total_byrdr,jns_perawatan.total_byrpr,jns_perawatan.total_byrdrpr,jns_perawatan.bhp,jns_perawatan.material,"+
                   "jns_perawatan.tarif_tindakandr,jns_perawatan.tarif_tindakanpr,jns_perawatan.kso,jns_perawatan.menejemen from jns_perawatan inner join kategori_perawatan "+
                   "on jns_perawatan.kd_kategori=kategori_perawatan.kd_kategori  "+
                   "where jns_perawatan.status='1' and (jns_perawatan.kd_pj=? or jns_perawatan.kd_pj='-') and (jns_perawatan.kd_poli=? or jns_perawatan.kd_poli='-') and jns_perawatan.kd_jenis_prw like ? or "+
                    " jns_perawatan.status='1' and (jns_perawatan.kd_pj=? or jns_perawatan.kd_pj='-') and (jns_perawatan.kd_poli=? or jns_perawatan.kd_poli='-') and jns_perawatan.nm_perawatan like ? or "+
                    " jns_perawatan.status='1' and (jns_perawatan.kd_pj=? or jns_perawatan.kd_pj='-') and (jns_perawatan.kd_poli=? or jns_perawatan.kd_poli='-') and kategori_perawatan.nm_kategori like ? order by jns_perawatan.nm_perawatan "); 
            pstindakan2=koneksi.prepareStatement("select jns_perawatan.kd_jenis_prw,jns_perawatan.nm_perawatan,kategori_perawatan.nm_kategori,"+
                   "jns_perawatan.total_byrdr,jns_perawatan.total_byrpr,jns_perawatan.total_byrdrpr,jns_perawatan.bhp,jns_perawatan.material,"+
                   "jns_perawatan.tarif_tindakandr,jns_perawatan.tarif_tindakanpr,jns_perawatan.kso,jns_perawatan.menejemen from jns_perawatan inner join kategori_perawatan "+
                   "on jns_perawatan.kd_kategori=kategori_perawatan.kd_kategori  "+
                   "where jns_perawatan.status='1' and (jns_perawatan.kd_pj=? or jns_perawatan.kd_pj='-') and jns_perawatan.kd_jenis_prw like ? or "+
                    " jns_perawatan.status='1' and (jns_perawatan.kd_pj=? or jns_perawatan.kd_pj='-') and jns_perawatan.nm_perawatan like ? or "+
                    " jns_perawatan.status='1' and (jns_perawatan.kd_pj=? or jns_perawatan.kd_pj='-') and kategori_perawatan.nm_kategori like ? order by jns_perawatan.nm_perawatan ");        
            pstindakan3=koneksi.prepareStatement("select jns_perawatan.kd_jenis_prw,jns_perawatan.nm_perawatan,kategori_perawatan.nm_kategori,"+
                   "jns_perawatan.total_byrdr,jns_perawatan.total_byrpr,jns_perawatan.total_byrdrpr,jns_perawatan.bhp,jns_perawatan.material,"+
                   "jns_perawatan.tarif_tindakandr,jns_perawatan.tarif_tindakanpr,jns_perawatan.kso,jns_perawatan.menejemen from jns_perawatan inner join kategori_perawatan "+
                   "on jns_perawatan.kd_kategori=kategori_perawatan.kd_kategori  "+
                   "where jns_perawatan.status='1' and (jns_perawatan.kd_poli=? or jns_perawatan.kd_poli='-') and jns_perawatan.kd_jenis_prw like ? or "+
                    " jns_perawatan.status='1' and (jns_perawatan.kd_poli=? or jns_perawatan.kd_poli='-') and jns_perawatan.nm_perawatan like ? or "+
                    " jns_perawatan.status='1' and (jns_perawatan.kd_poli=? or jns_perawatan.kd_poli='-') and kategori_perawatan.nm_kategori like ? order by jns_perawatan.nm_perawatan ");     
            pstindakan4=koneksi.prepareStatement("select jns_perawatan.kd_jenis_prw,jns_perawatan.nm_perawatan,kategori_perawatan.nm_kategori,"+
                   "jns_perawatan.total_byrdr,jns_perawatan.total_byrpr,jns_perawatan.total_byrdrpr,jns_perawatan.bhp,jns_perawatan.material,"+
                   "jns_perawatan.tarif_tindakandr,jns_perawatan.tarif_tindakanpr,jns_perawatan.kso,jns_perawatan.menejemen from jns_perawatan inner join kategori_perawatan "+
                   "on jns_perawatan.kd_kategori=kategori_perawatan.kd_kategori  "+
                   "where jns_perawatan.status='1' and jns_perawatan.kd_jenis_prw like ? or "+
                    " jns_perawatan.status='1' and jns_perawatan.nm_perawatan like ? or "+
                    " jns_perawatan.status='1' and kategori_perawatan.nm_kategori like ? order by jns_perawatan.nm_perawatan "); 
            try {
                if(poli_ralan.equals("Yes")&&cara_bayar_ralan.equals("Yes")){
                    pstindakan.setString(1,kd_pj.trim());
                    pstindakan.setString(2,kd_poli.trim());
                    pstindakan.setString(3,"%"+TCariTindakan.getText().trim()+"%");
                    pstindakan.setString(4,kd_pj.trim());
                    pstindakan.setString(5,kd_poli.trim());
                    pstindakan.setString(6,"%"+TCariTindakan.getText().trim()+"%");
                    pstindakan.setString(7,kd_pj.trim());
                    pstindakan.setString(8,kd_poli.trim());
                    pstindakan.setString(9,"%"+TCariTindakan.getText().trim()+"%");
                    rstindakan=pstindakan.executeQuery();
                }else if(poli_ralan.equals("No")&&cara_bayar_ralan.equals("Yes")){
                    pstindakan2.setString(1,kd_pj.trim());
                    pstindakan2.setString(2,"%"+TCariTindakan.getText().trim()+"%");
                    pstindakan2.setString(3,kd_pj.trim());
                    pstindakan2.setString(4,"%"+TCariTindakan.getText().trim()+"%");
                    pstindakan2.setString(5,kd_pj.trim());
                    pstindakan2.setString(6,"%"+TCariTindakan.getText().trim()+"%");
                    rstindakan=pstindakan2.executeQuery();
                }else if(poli_ralan.equals("Yes")&&cara_bayar_ralan.equals("No")){
                    pstindakan3.setString(1,kd_poli.trim());
                    pstindakan3.setString(2,"%"+TCariTindakan.getText().trim()+"%");
                    pstindakan3.setString(3,kd_poli.trim());
                    pstindakan3.setString(4,"%"+TCariTindakan.getText().trim()+"%");
                    pstindakan3.setString(5,kd_poli.trim());
                    pstindakan3.setString(6,"%"+TCariTindakan.getText().trim()+"%");
                    rstindakan=pstindakan3.executeQuery();
                }else if(poli_ralan.equals("No")&&cara_bayar_ralan.equals("No")){
                    pstindakan4.setString(1,"%"+TCariTindakan.getText().trim()+"%");
                    pstindakan4.setString(2,"%"+TCariTindakan.getText().trim()+"%");
                    pstindakan4.setString(3,"%"+TCariTindakan.getText().trim()+"%");
                    rstindakan=pstindakan4.executeQuery();
                }

                while(rstindakan.next()){
                    if(rstindakan.getDouble("total_byrdr")>0){
                        TabModeTindakan.addRow(new Object[] {
                            false,rstindakan.getString(1),rstindakan.getString(2),rstindakan.getString(3),
                            rstindakan.getDouble("total_byrdr"),rstindakan.getDouble("material"),
                            rstindakan.getDouble("bhp"),rstindakan.getDouble("tarif_tindakandr"),
                            rstindakan.getDouble("tarif_tindakanpr"),rstindakan.getDouble("kso"),
                            rstindakan.getDouble("menejemen")
                        });
                    }   
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rstindakan!=null){
                    rstindakan.close();
                }
                
                if(pstindakan!=null){
                    pstindakan.close();
                }
                
                if(pstindakan2!=null){
                    pstindakan2.close();
                }
                
                if(pstindakan3!=null){
                    pstindakan3.close();
                }
                
                if(pstindakan4!=null){
                    pstindakan4.close();
                }
            }
        }catch(SQLException e){
            System.out.println("Notifikasi : "+e);
        }
    }
    
    public void setNoRm(String norwt) {
        this.kd_pj=Sequel.cariIsi("select kd_pj from reg_periksa where no_rawat=?",norwt);
        this.kd_poli=Sequel.cariIsi("select kd_poli from reg_periksa where no_rawat=?",norwt);
        this.kd_dokter=Sequel.cariIsi("select kd_dokter from reg_periksa where no_rawat=?",norwt);
        kenaikan=Sequel.cariIsiAngka("select (hargajual/100) from set_harga_obat_ralan where kd_pj=?",this.kd_pj);
        TCariTindakan.requestFocus();
        try {
            psset_tarif=koneksi.prepareStatement("select * from set_tarif");
            try {
                rsset_tarif=psset_tarif.executeQuery();
                if(rsset_tarif.next()){
                    poli_ralan=rsset_tarif.getString("poli_ralan");
                    cara_bayar_ralan=rsset_tarif.getString("cara_bayar_ralan");
                }else{
                    poli_ralan="Yes";
                    cara_bayar_ralan="Yes";
                }  
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rsset_tarif!=null){
                    rsset_tarif.close();
                }
                if(psset_tarif!=null){
                    psset_tarif.close();
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        } 
        bangsal=Sequel.cariIsi("select kd_bangsal from set_depo_ralan where kd_poli=?",Sequel.cariIsi("select kd_poli from reg_periksa where no_rawat=?",norwt));
        if(bangsal.equals("")){
            bangsal=Sequel.cariIsi("select kd_bangsal from set_lokasi limit 1");
        }
    }
    
    
    public void tampilobat() {        
        z=0;
        for(i=0;i<tbObat.getRowCount();i++){
            if(!tbObat.getValueAt(i,0).toString().equals("")){
                z++;
            }
        }    
        
        pilih=null;
        pilih=new boolean[z]; 
        jumlah=null;
        jumlah=new double[z];
        stok=null;
        stok=new double[z];
        harga=null;
        harga=new double[z];
        eb=null;
        eb=new double[z];
        tsl=null;
        tsl=new double[z];
        beli=null;
        beli=new double[z];
        kodebarang=null;
        kodebarang=new String[z];
        namabarang=null;
        namabarang=new String[z];
        kodesatuan=null;
        kodesatuan=new String[z];
        letakbarang=null;
        letakbarang=new String[z];
        namajenis=null;
        namajenis=new String[z];  
        nobatch=null;
        nobatch=new String[z];  
        nofaktur=null;
        nofaktur=new String[z];                   
        
        z=0;        
        for(i=0;i<tbObat.getRowCount();i++){
            if(!tbObat.getValueAt(i,1).toString().equals("")){
                pilih[z]=Boolean.parseBoolean(tbObat.getValueAt(i,0).toString());
                jumlah[z]=Double.parseDouble(tbObat.getValueAt(i,1).toString());
                kodebarang[z]=tbObat.getValueAt(i,2).toString();
                namabarang[z]=tbObat.getValueAt(i,3).toString();
                kodesatuan[z]=tbObat.getValueAt(i,4).toString();
                letakbarang[z]=tbObat.getValueAt(i,5).toString();
                harga[z]=Double.parseDouble(tbObat.getValueAt(i,6).toString());
                stok[z]=Double.parseDouble(tbObat.getValueAt(i,7).toString());
                namajenis[z]=tbObat.getValueAt(i,8).toString();
                try {
                    eb[z]=Double.parseDouble(tbObat.getValueAt(i,9).toString());
                } catch (Exception e) {
                    eb[z]=0;
                }  
                try {
                    tsl[z]=Double.parseDouble(tbObat.getValueAt(i,10).toString());
                } catch (Exception e) {
                    tsl[z]=0;
                } 
                try {
                    beli[z]=Double.parseDouble(tbObat.getValueAt(i,11).toString());
                } catch (Exception e) {
                    beli[z]=0;
                } 
                nobatch[z]=tbObat.getValueAt(i,12).toString();
                nofaktur[z]=tbObat.getValueAt(i,13).toString();
                z++;
            }
        }
        
        Valid.tabelKosong(tabModeObat);             
        
        for(i=0;i<z;i++){
            tabModeObat.addRow(new Object[] {
                pilih[i],jumlah[i],kodebarang[i],namabarang[i],kodesatuan[i],letakbarang[i],harga[i],stok[i],namajenis[i],eb[i],tsl[i],beli[i],nobatch[i],nofaktur[i]
            });
        }
        try{   
            if(aktifkanbatch.equals("yes")){
                if(kenaikan>0){
                    psobat=koneksi.prepareStatement(
                            " select data_batch.kode_brng, databarang.nama_brng,jenis.nama, databarang.kode_sat,(data_batch.h_beli+(data_batch.h_beli*?)) as ralan,gudangbarang.stok,"+
                            " databarang.letak_barang,data_batch.no_batch,data_batch.no_faktur,data_batch.dasar from data_batch inner join databarang on data_batch.kode_brng=databarang.kode_brng inner join jenis on databarang.kdjns=jenis.kdjns "+
                            " inner join gudangbarang on gudangbarang.kode_brng=data_batch.kode_brng and gudangbarang.no_batch=data_batch.no_batch and gudangbarang.no_faktur=data_batch.no_faktur "+
                            " where gudangbarang.stok>0 and gudangbarang.kd_bangsal=? and databarang.kode_brng like ? or "+
                            " gudangbarang.stok>0 and gudangbarang.kd_bangsal=? and databarang.nama_brng like ? or "+
                            " gudangbarang.stok>0 and gudangbarang.kd_bangsal=? and jenis.nama like ? order by databarang.nama_brng");
                    try {
                        psobat.setDouble(1,kenaikan);
                        psobat.setString(2,bangsal);
                        psobat.setString(3,"%"+TCariObat.getText().trim()+"%");
                        psobat.setString(4,bangsal);
                        psobat.setString(5,"%"+TCariObat.getText().trim()+"%");
                        psobat.setString(6,bangsal);
                        psobat.setString(7,"%"+TCariObat.getText().trim()+"%");
                        rsobat=psobat.executeQuery();
                        while(rsobat.next()){   
                            tabModeObat.addRow(new Object[] {
                                false,"",rsobat.getString("kode_brng"),rsobat.getString("nama_brng"),
                                rsobat.getString("kode_sat"),rsobat.getString("letak_barang"),
                                rsobat.getDouble("ralan"),rsobat.getDouble("stok"),rsobat.getString("nama"),0,0,
                                rsobat.getDouble("dasar"),rsobat.getString("no_batch"),rsobat.getString("no_faktur")
                            });
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi : "+e);
                    } finally{
                        if(rsobat!=null){
                            rsobat.close();
                        }
                        if(psobat!=null){
                            psobat.close();
                        }
                    }

                }else{
                    psobat=koneksi.prepareStatement(
                            " select data_batch.kode_brng, databarang.nama_brng,jenis.nama, databarang.kode_sat,data_batch.ralan,data_batch.dasar,databarang.letak_barang,gudangbarang.stok,data_batch.no_batch,data_batch.no_faktur "+
                            " from data_batch inner join databarang on data_batch.kode_brng=databarang.kode_brng inner join jenis on databarang.kdjns=jenis.kdjns "+
                            " inner join gudangbarang on gudangbarang.kode_brng=data_batch.kode_brng and gudangbarang.no_batch=data_batch.no_batch and gudangbarang.no_faktur=data_batch.no_faktur "+
                            " where gudangbarang.stok>0 and gudangbarang.kd_bangsal=? and databarang.kode_brng like ? or "+
                            " gudangbarang.stok>0 and gudangbarang.kd_bangsal=? and databarang.nama_brng like ? or "+
                            " gudangbarang.stok>0 and gudangbarang.kd_bangsal=? and jenis.nama like ? order by databarang.nama_brng");
                    try {
                        psobat.setString(1,bangsal);
                        psobat.setString(2,"%"+TCariObat.getText().trim()+"%");
                        psobat.setString(3,bangsal);
                        psobat.setString(4,"%"+TCariObat.getText().trim()+"%");
                        psobat.setString(5,bangsal);
                        psobat.setString(6,"%"+TCariObat.getText().trim()+"%");
                        rsobat=psobat.executeQuery();
                        while(rsobat.next()){   
                            tabModeObat.addRow(new Object[] {
                                false,"",rsobat.getString("kode_brng"),rsobat.getString("nama_brng"),
                                rsobat.getString("kode_sat"),rsobat.getString("letak_barang"),
                                rsobat.getDouble("ralan"),rsobat.getDouble("stok"),rsobat.getString("nama"),0,0,
                                rsobat.getDouble("dasar"),rsobat.getString("no_batch"),rsobat.getString("no_faktur")
                            });
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi : "+e);
                    } finally{
                        if(rsobat!=null){
                            rsobat.close();
                        }
                        if(psobat!=null){
                            psobat.close();
                        }
                    }
                }  
            }else{
                if(kenaikan>0){
                    psobat=koneksi.prepareStatement(
                            " select databarang.kode_brng, databarang.nama_brng,jenis.nama, databarang.kode_sat,(databarang.h_beli+(databarang.h_beli*?)) as ralan,gudangbarang.stok,"+
                            " databarang.letak_barang,databarang.dasar from databarang inner join jenis on databarang.kdjns=jenis.kdjns inner join gudangbarang on databarang.kode_brng=gudangbarang.kode_brng "+
                            " where gudangbarang.no_batch='' and gudangbarang.no_faktur='' and gudangbarang.stok>0 and gudangbarang.kd_bangsal=? and databarang.status='1' and databarang.kode_brng like ? or "+
                            " gudangbarang.no_batch='' and gudangbarang.no_faktur='' and gudangbarang.stok>0 and gudangbarang.kd_bangsal=? and databarang.status='1' and databarang.nama_brng like ? or "+
                            " gudangbarang.no_batch='' and gudangbarang.no_faktur='' and gudangbarang.stok>0 and gudangbarang.kd_bangsal=? and databarang.status='1' and jenis.nama like ? order by databarang.nama_brng");
                    try {
                        psobat.setDouble(1,kenaikan);
                        psobat.setString(2,bangsal);
                        psobat.setString(3,"%"+TCariObat.getText().trim()+"%");
                        psobat.setString(4,bangsal);
                        psobat.setString(5,"%"+TCariObat.getText().trim()+"%");
                        psobat.setString(6,bangsal);
                        psobat.setString(7,"%"+TCariObat.getText().trim()+"%");
                        rsobat=psobat.executeQuery();
                        while(rsobat.next()){   
                            tabModeObat.addRow(new Object[] {
                                false,"",rsobat.getString("kode_brng"),rsobat.getString("nama_brng"),
                                rsobat.getString("kode_sat"),rsobat.getString("letak_barang"),
                                rsobat.getDouble("ralan"),rsobat.getDouble("stok"),rsobat.getString("nama"),0,0,rsobat.getDouble("dasar"),"",""
                            });
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi : "+e);
                    } finally{
                        if(rsobat!=null){
                            rsobat.close();
                        }
                        if(psobat!=null){
                            psobat.close();
                        }
                    }
                }else{
                    psobat=koneksi.prepareStatement(
                            " select databarang.kode_brng, databarang.nama_brng,jenis.nama, databarang.kode_sat,databarang.ralan,databarang.dasar,databarang.letak_barang,gudangbarang.stok "+
                            " from databarang inner join jenis on databarang.kdjns=jenis.kdjns inner join gudangbarang on databarang.kode_brng=gudangbarang.kode_brng "+
                            " where gudangbarang.no_batch='' and gudangbarang.no_faktur='' and gudangbarang.stok>0 and gudangbarang.kd_bangsal=? and databarang.status='1' and databarang.kode_brng like ? or "+
                            " gudangbarang.no_batch='' and gudangbarang.no_faktur='' and gudangbarang.stok>0 and gudangbarang.kd_bangsal=? and databarang.status='1' and databarang.nama_brng like ? or "+
                            " gudangbarang.no_batch='' and gudangbarang.no_faktur='' and gudangbarang.stok>0 and gudangbarang.kd_bangsal=? and databarang.status='1' and jenis.nama like ? order by databarang.nama_brng");
                    try {
                        psobat.setString(1,bangsal);
                        psobat.setString(2,"%"+TCariObat.getText().trim()+"%");
                        psobat.setString(3,bangsal);
                        psobat.setString(4,"%"+TCariObat.getText().trim()+"%");
                        psobat.setString(5,bangsal);
                        psobat.setString(6,"%"+TCariObat.getText().trim()+"%");
                        rsobat=psobat.executeQuery();
                        while(rsobat.next()){   
                            tabModeObat.addRow(new Object[] {
                                false,"",rsobat.getString("kode_brng"),rsobat.getString("nama_brng"),
                                rsobat.getString("kode_sat"),rsobat.getString("letak_barang"),
                                rsobat.getDouble("ralan"),rsobat.getDouble("stok"),rsobat.getString("nama"),0,0,rsobat.getDouble("dasar"),"",""
                            });
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi : "+e);
                    } finally{
                        if(rsobat!=null){
                            rsobat.close();
                        }
                        if(psobat!=null){
                            psobat.close();
                        }
                    }
                }  
            } 
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }
    
    private void getDataObat() {
        int row=tbObat.getSelectedRow();
        if(bangsal.equals("")){
             Valid.textKosong(TCariObat,"Asal Stok");
        }else if(row!= -1){            
             if(!tabModeObat.getValueAt(row,1).toString().equals("")){
                try {
                    if(Double.parseDouble(tabModeObat.getValueAt(row,1).toString())>0){
                        stokbarang=0;   
                        psobat=koneksi.prepareStatement("select ifnull(stok,'0') from gudangbarang where kd_bangsal=? and kode_brng=? and no_batch=? and no_faktur=?");
                        try {
                            psobat.setString(1,bangsal);
                            psobat.setString(2,tbObat.getValueAt(row,2).toString());
                            psobat.setString(3,tbObat.getValueAt(row,12).toString());
                            psobat.setString(4,tbObat.getValueAt(row,13).toString());
                            rsobat=psobat.executeQuery();
                            if(rsobat.next()){
                                stokbarang=rsobat.getDouble(1);
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi : "+e);
                        } finally{
                            if(rsobat!=null){
                                rsobat.close();
                            }
                            if(psobat!=null){
                                psobat.close();
                            }
                        }  

                        tbObat.setValueAt(stokbarang,row,7);
                        
                        j=0;
                        try {
                            if(tbObat.getValueAt(row,0).toString().equals("true")){
                                pscarikapasitas= koneksi.prepareStatement("select IFNULL(kapasitas,1) from databarang where kode_brng=?");                                      
                                try {
                                    pscarikapasitas.setString(1,tbObat.getValueAt(row,2).toString());
                                    carikapasitas=pscarikapasitas.executeQuery();
                                    if(carikapasitas.next()){ 
                                        j=Double.parseDouble(tbObat.getValueAt(row,1).toString())/carikapasitas.getDouble(1);
                                    }else{
                                        j=Double.parseDouble(tbObat.getValueAt(row,1).toString());
                                    }
                                } catch (Exception e) {
                                    j=Double.parseDouble(tbObat.getValueAt(row,1).toString());
                                    System.out.println("Kapasitasmu masih kosong broooh : "+e);
                                } finally{
                                    if(carikapasitas!=null){
                                        carikapasitas.close();
                                    }
                                    if(pscarikapasitas!=null){
                                        pscarikapasitas.close();
                                    }
                                }
                            }else{
                                j=Double.parseDouble(tbObat.getValueAt(row,1).toString());
                            } 
                        } catch (Exception e) {
                            j=0;
                        }
                        
                        if(stokbarang<j){
                              JOptionPane.showMessageDialog(null,"Maaf, Stok tidak cukup....!!!");
                              TCariObat.requestFocus();
                              tbObat.setValueAt("", row,1);
                        } 
                    }
                } catch (Exception e) {
                    tbObat.setValueAt("", row,1);
                }                                       
             } 
        }
    }

}

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
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import keuangan.Jurnal;
import simrskhanza.DlgRawatInap;

/**
 *
 * @author dosen
 */
public final class DlgBarcodeRanap extends javax.swing.JDialog {
    private final DefaultTableModel TabModeTindakan,tabModeObat;
    private int jml=0,i=0,index=0,z=0;
    private PreparedStatement pscari,pscari2,pscari3,pscari4,pstarif,psobat,pscarikapasitas,psrekening;
    private ResultSet rstindakan,rstarif,rsobat,carikapasitas,rsrekening;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private boolean[] pilih; 
    private String[] kode,nama,kategori;
    private double[] totaltnd,bagianrs,bhp,jmdokter,jmperawat;
    private String kd_pj="",kd_bangsal="",ruang_ranap="Yes", cara_bayar_ranap="Yes",kd_dokter,lokasistok="";   
    private double[] jumlah,harga,stok,eb,tsl,beli,kso,menejemen;
    private String[] kodebarang,namabarang,kodesatuan,letakbarang,namajenis,nobatch,nofaktur;
    private String kelas="";
    private double embalase=0,tuslah=0,kenaikan=0,j=0,stokbarang=0;
    private WarnaTable2 warna=new WarnaTable2();
    private riwayatobat Trackobat=new riwayatobat();
    private String aktifkanbatch="no";
    private boolean sukses=true;
    private Jurnal jur=new Jurnal();
    private double ttljmdokter=0,ttlkso=0,ttlpendapatan=0,ttlhpp=0,ttljual=0,ttljasasarana=0,ttlbhp=0,ttlmenejemen=0;
    private String Suspen_Piutang_Tindakan_Ranap="",Tindakan_Ranap="",Beban_Jasa_Medik_Dokter_Tindakan_Ranap="",Utang_Jasa_Medik_Dokter_Tindakan_Ranap="",
            Beban_KSO_Tindakan_Ranap="",Utang_KSO_Tindakan_Ranap="",Beban_Jasa_Sarana_Tindakan_Ranap="",Utang_Jasa_Sarana_Tindakan_Ranap="",
            Beban_Jasa_Menejemen_Tindakan_Ranap="",Utang_Jasa_Menejemen_Tindakan_Ranap="",HPP_BHP_Tindakan_Ranap="",Persediaan_BHP_Tindakan_Ranap="",
            Suspen_Piutang_Obat_Ranap="",Obat_Ranap="",HPP_Obat_Rawat_Inap="",Persediaan_Obat_Rawat_Inap="";
            
    /**
     * Creates new form DlgPenyakit
     * @param parent
     * @param modal
     */
    public DlgBarcodeRanap(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(10,2);
        setSize(656,250);
        Object[] row={"P","Kode","Nama Perawatan","Kategori Perawatan","Tarif/Biaya","Bagian RS","BHP","JM Dokter","JM Perawat","KSO","Menejemen"};
        TabModeTindakan=new DefaultTableModel(null,row){
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
                column.setPreferredWidth(80);
            }else if(i==2){
                column.setPreferredWidth(350);
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
            }else{
                column.setPreferredWidth(120);
            }
        }
        tbTindakan.setDefaultRenderer(Object.class, new WarnaTable());
        TCariTindakan.setDocument(new batasInput((byte)100).getKata(TCariTindakan));
        Object[] row2={"K","Jml","Kode Barang","Nama Barang","Satuan","Kandungan","Harga(Rp)","Stok","Jenis Obat","Embalase","Tuslah","Hbeli","No.Batch","No.Faktur"};
        tabModeObat=new DefaultTableModel(null,row2){
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
        
        try {
            psrekening=koneksi.prepareStatement("select * from set_akun_ranap");
            try {
                rsrekening=psrekening.executeQuery();
                while(rsrekening.next()){
                    Suspen_Piutang_Tindakan_Ranap=rsrekening.getString("Suspen_Piutang_Tindakan_Ranap");
                    Tindakan_Ranap=rsrekening.getString("Tindakan_Ranap");
                    Beban_Jasa_Medik_Dokter_Tindakan_Ranap=rsrekening.getString("Beban_Jasa_Medik_Dokter_Tindakan_Ranap");
                    Utang_Jasa_Medik_Dokter_Tindakan_Ranap=rsrekening.getString("Utang_Jasa_Medik_Dokter_Tindakan_Ranap");
                    Beban_KSO_Tindakan_Ranap=rsrekening.getString("Beban_KSO_Tindakan_Ranap");
                    Utang_KSO_Tindakan_Ranap=rsrekening.getString("Utang_KSO_Tindakan_Ranap");
                    Beban_Jasa_Sarana_Tindakan_Ranap=rsrekening.getString("Beban_Jasa_Sarana_Tindakan_Ranap");
                    Utang_Jasa_Sarana_Tindakan_Ranap=rsrekening.getString("Utang_Jasa_Sarana_Tindakan_Ranap");
                    Beban_Jasa_Menejemen_Tindakan_Ranap=rsrekening.getString("Beban_Jasa_Menejemen_Tindakan_Ranap");
                    Utang_Jasa_Menejemen_Tindakan_Ranap=rsrekening.getString("Utang_Jasa_Menejemen_Tindakan_Ranap");
                    HPP_BHP_Tindakan_Ranap=rsrekening.getString("HPP_BHP_Tindakan_Ranap");
                    Persediaan_BHP_Tindakan_Ranap=rsrekening.getString("Persediaan_BHP_Tindakan_Ranap");
                    Suspen_Piutang_Obat_Ranap=rsrekening.getString("Suspen_Piutang_Obat_Ranap");
                    Obat_Ranap=rsrekening.getString("Obat_Ranap");
                    HPP_Obat_Rawat_Inap=rsrekening.getString("HPP_Obat_Rawat_Inap");
                    Persediaan_Obat_Rawat_Inap=rsrekening.getString("Persediaan_Obat_Rawat_Inap");
                }
            } catch (Exception e) {
                System.out.println("Notif Rekening : "+e);
            } finally{
                if(rsrekening!=null){
                    rsrekening.close();
                }
                if(psrekening!=null){
                    psrekening.close();
                }
            }            
        } catch (Exception e) {
            System.out.println(e);
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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Input Tindakan & Obat di Rawat Inap Via Barcode No.Rawat ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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

        label11.setText("Key Word :");
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

        label12.setText("Key Word :");
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
        }else if(lokasistok.equals("")){
            Valid.textKosong(TCariObat,"Lokasi");
        }else{
            Sequel.AutoComitFalse();
            sukses=true;
            ttljmdokter=0;ttlkso=0;ttlpendapatan=0;ttljasasarana=0;ttlbhp=0;ttlmenejemen=0;
            for(i=0;i<tbTindakan.getRowCount();i++){ 
                if(tbTindakan.getValueAt(i,0).toString().equals("true")){
                    if(Sequel.menyimpantf2("rawat_inap_dr","?,?,?,?,?,?,?,?,?,?,?", "Tindakan",11,new String[]{
                        NoRawat.getText(),tbTindakan.getValueAt(i,1).toString(),kd_dokter,
                        Sequel.cariIsi("select current_date()"),Sequel.cariIsi("select current_time()"),
                        tbTindakan.getValueAt(i,5).toString(),tbTindakan.getValueAt(i,6).toString(),
                        tbTindakan.getValueAt(i,7).toString(),tbTindakan.getValueAt(i,9).toString(),
                        tbTindakan.getValueAt(i,10).toString(),tbTindakan.getValueAt(i,4).toString()
                    })==true){
                        //"P"0,"Kode"1,"Nama Perawatan"2,"Kategori Perawatan"3,"Tarif/Biaya"4,"Bagian RS"5,"BHP"6,"JM Dokter"7,"JM Perawat"8,"KSO"9,"Menejemen"10
                        ttljmdokter=ttljmdokter+Double.parseDouble(tbTindakan.getValueAt(i,7).toString());
                        ttlkso=ttlkso+Double.parseDouble(tbTindakan.getValueAt(i,9).toString());
                        ttlpendapatan=ttlpendapatan+Double.parseDouble(tbTindakan.getValueAt(i,4).toString());
                        ttljasasarana=ttljasasarana+Double.parseDouble(tbTindakan.getValueAt(i,5).toString());
                        ttlbhp=ttlbhp+Double.parseDouble(tbTindakan.getValueAt(i,6).toString());
                        ttlmenejemen=ttlmenejemen+Double.parseDouble(tbTindakan.getValueAt(i,10).toString());
                    }else{
                        sukses=false;
                    }
                }                    
            }
            
            if(sukses==true){
                Sequel.queryu("delete from tampjurnal");    
                if(ttlpendapatan>0){
                    Sequel.menyimpan("tampjurnal","'"+Suspen_Piutang_Tindakan_Ranap+"','Suspen Piutang Tindakan Ranap','"+ttlpendapatan+"','0'","debet=debet+'"+(ttlpendapatan)+"'","kd_rek='"+Suspen_Piutang_Tindakan_Ranap+"'");    
                    Sequel.menyimpan("tampjurnal","'"+Tindakan_Ranap+"','Pendapatan Tindakan Rawat Inap','0','"+ttlpendapatan+"'","kredit=kredit+'"+(ttlpendapatan)+"'","kd_rek='"+Tindakan_Ranap+"'");                             
                }
                if(ttljmdokter>0){
                    Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Dokter_Tindakan_Ranap+"','Beban Jasa Medik Dokter Tindakan Ranap','"+ttljmdokter+"','0'","debet=debet+'"+(ttljmdokter)+"'","kd_rek='"+Beban_Jasa_Medik_Dokter_Tindakan_Ranap+"'");       
                    Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Dokter_Tindakan_Ranap+"','Utang Jasa Medik Dokter Tindakan Ranap','0','"+ttljmdokter+"'","kredit=kredit+'"+(ttljmdokter)+"'","kd_rek='"+Utang_Jasa_Medik_Dokter_Tindakan_Ranap+"'");                               
                }
                if(ttlkso>0){
                    Sequel.menyimpan("tampjurnal","'"+Beban_KSO_Tindakan_Ranap+"','Beban KSO Tindakan Ranap','"+ttlkso+"','0'","debet=debet+'"+(ttlkso)+"'","kd_rek='"+Beban_KSO_Tindakan_Ranap+"'");       
                    Sequel.menyimpan("tampjurnal","'"+Utang_KSO_Tindakan_Ranap+"','Utang KSO Tindakan Ranap','0','"+ttlkso+"'","kredit=kredit+'"+(ttlkso)+"'","kd_rek='"+Utang_KSO_Tindakan_Ranap+"'");                              
                }
                if(ttljasasarana>0){
                    Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Sarana_Tindakan_Ranap+"','Beban Jasa Sarana Tindakan Ranap','"+ttljasasarana+"','0'","debet=debet+'"+(ttljasasarana)+"'","kd_rek='"+Beban_Jasa_Sarana_Tindakan_Ranap+"'");     
                    Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Sarana_Tindakan_Ranap+"','Utang Jasa Sarana Tindakan Ranap','0','"+ttljasasarana+"'","kredit=kredit+'"+(ttljasasarana)+"'","kd_rek='"+Utang_Jasa_Sarana_Tindakan_Ranap+"'");                              
                }
                if(ttlbhp>0){
                    Sequel.menyimpan("tampjurnal","'"+HPP_BHP_Tindakan_Ranap+"','HPP BHP Tindakan Ranap','"+ttlbhp+"','0'","debet=debet+'"+(ttlbhp)+"'","kd_rek='"+HPP_BHP_Tindakan_Ranap+"'");      
                    Sequel.menyimpan("tampjurnal","'"+Persediaan_BHP_Tindakan_Ranap+"','Persediaan BHP Tindakan Ranap','0','"+ttlbhp+"'","kredit=kredit+'"+(ttlbhp)+"'","kd_rek='"+Persediaan_BHP_Tindakan_Ranap+"'");                           
                }
                if(ttlmenejemen>0){
                    Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Menejemen_Tindakan_Ranap+"','Beban Jasa Menejemen Tindakan Ranap','"+ttlmenejemen+"','0'","debet=debet+'"+(ttlmenejemen)+"'","kd_rek='"+Beban_Jasa_Menejemen_Tindakan_Ranap+"'");       
                    Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Menejemen_Tindakan_Ranap+"','Utang Jasa Menejemen Tindakan Ranap','0','"+ttlmenejemen+"'","kredit=kredit+'"+(ttlmenejemen)+"'","kd_rek='"+Utang_Jasa_Menejemen_Tindakan_Ranap+"'");                            
                }
                sukses=jur.simpanJurnal(NoRawat.getText(),Sequel.cariIsi("select current_date()"),"U","TINDAKAN RAWAT INAP PASIEN "+NoRawat.getText()+" DIPOSTING OLEH "+akses.getkode());    
            }
                  
            if(sukses==true){
                ttlhpp=0;ttljual=0;
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
                                            ""+(Double.parseDouble(tbObat.getValueAt(i,9).toString())+Double.parseDouble(tbObat.getValueAt(i,10).toString())+(Double.parseDouble(tbObat.getValueAt(i,6).toString())*(Double.parseDouble(tbObat.getValueAt(i,1).toString())/carikapasitas.getDouble(1)))),"Ranap",lokasistok,
                                            tbObat.getValueAt(i,12).toString(),tbObat.getValueAt(i,13).toString()
                                        })==true){
                                            if(aktifkanbatch.equals("yes")){
                                                Sequel.mengedit3("data_batch","no_batch=? and kode_brng=? and no_faktur=?","sisa=sisa-?",4,new String[]{
                                                    ""+(Double.parseDouble(tbObat.getValueAt(i,1).toString())/carikapasitas.getDouble(1)),
                                                    tbObat.getValueAt(i,12).toString(),tbObat.getValueAt(i,2).toString(),tbObat.getValueAt(i,13).toString()
                                                });
                                                Trackobat.catatRiwayat(tbObat.getValueAt(i,2).toString(),0,(Double.parseDouble(tbObat.getValueAt(i,1).toString())/carikapasitas.getDouble(1)),"Pemberian Obat",akses.getkode(),lokasistok,"Simpan",tbObat.getValueAt(i,12).toString(),tbObat.getValueAt(i,13).toString());
                                                Sequel.menyimpan("gudangbarang","'"+tbObat.getValueAt(i,2).toString()+"','"+lokasistok+"','-"+(Double.parseDouble(tbObat.getValueAt(i,1).toString())/carikapasitas.getDouble(1))+"','"+tbObat.getValueAt(i,12).toString()+"','"+tbObat.getValueAt(i,13).toString()+"'", 
                                                         "stok=stok-'"+(Double.parseDouble(tbObat.getValueAt(i,1).toString())/carikapasitas.getDouble(1))+"'","kode_brng='"+tbObat.getValueAt(i,2).toString()+"' and kd_bangsal='"+lokasistok+"' and no_batch='"+tbObat.getValueAt(i,12).toString()+"' and no_faktur='"+tbObat.getValueAt(i,13).toString()+"'");  
                                            }else{
                                                Trackobat.catatRiwayat(tbObat.getValueAt(i,2).toString(),0,(Double.parseDouble(tbObat.getValueAt(i,1).toString())/carikapasitas.getDouble(1)),"Pemberian Obat",akses.getkode(),lokasistok,"Simpan","","");
                                                Sequel.menyimpan("gudangbarang","'"+tbObat.getValueAt(i,2).toString()+"','"+lokasistok+"','-"+(Double.parseDouble(tbObat.getValueAt(i,1).toString())/carikapasitas.getDouble(1))+"','',''", 
                                                         "stok=stok-'"+(Double.parseDouble(tbObat.getValueAt(i,1).toString())/carikapasitas.getDouble(1))+"'","kode_brng='"+tbObat.getValueAt(i,2).toString()+"' and kd_bangsal='"+lokasistok+"' and no_batch='' and no_faktur=''");  
                                            }
                                            ttljual=ttljual+Double.parseDouble(tbObat.getValueAt(i,9).toString())+Double.parseDouble(tbObat.getValueAt(i,10).toString())+
                                                    (Double.parseDouble(tbObat.getValueAt(i,6).toString())*(Double.parseDouble(tbObat.getValueAt(i,1).toString())/carikapasitas.getDouble(1)));
                                            ttlhpp=ttlhpp+(Double.parseDouble(tbObat.getValueAt(i,11).toString())*(Double.parseDouble(tbObat.getValueAt(i,1).toString())/carikapasitas.getDouble(1)));
                                        }else{
                                            sukses=false;
                                        }  
                                    }else{
                                        if(Sequel.menyimpantf("detail_pemberian_obat","?,?,?,?,?,?,?,?,?,?,?,?,?,?","data",14,new String[]{
                                            Sequel.cariIsi("select current_date()"),Sequel.cariIsi("select current_time()"),NoRawat.getText(),tbObat.getValueAt(i,2).toString(),tbObat.getValueAt(i,11).toString(),
                                            tbObat.getValueAt(i,6).toString(),""+Double.parseDouble(tbObat.getValueAt(i,1).toString()),tbObat.getValueAt(i,9).toString(),tbObat.getValueAt(i,10).toString(),
                                            ""+(Double.parseDouble(tbObat.getValueAt(i,9).toString())+Double.parseDouble(tbObat.getValueAt(i,10).toString())+(Double.parseDouble(tbObat.getValueAt(i,6).toString())*Double.parseDouble(tbObat.getValueAt(i,1).toString()))),
                                            "Ranap",lokasistok,tbObat.getValueAt(i,12).toString(),tbObat.getValueAt(i,13).toString()
                                        })==true){
                                            if(aktifkanbatch.equals("yes")){
                                                Sequel.mengedit3("data_batch","no_batch=? and kode_brng=? and no_faktur=?","sisa=sisa-?",4,new String[]{
                                                    ""+Double.parseDouble(tbObat.getValueAt(i,1).toString()),tbObat.getValueAt(i,12).toString(),tbObat.getValueAt(i,2).toString(),tbObat.getValueAt(i,13).toString()
                                                });
                                                Trackobat.catatRiwayat(tbObat.getValueAt(i,2).toString(),0,Double.parseDouble(tbObat.getValueAt(i,1).toString()),"Pemberian Obat",akses.getkode(),lokasistok,"Simpan",tbObat.getValueAt(i,12).toString(),tbObat.getValueAt(i,13).toString());
                                                Sequel.menyimpan("gudangbarang","'"+tbObat.getValueAt(i,2).toString()+"','"+lokasistok+"','-"+Double.parseDouble(tbObat.getValueAt(i,1).toString())+"','"+tbObat.getValueAt(i,12).toString()+"','"+tbObat.getValueAt(i,13).toString()+"'", 
                                                         "stok=stok-'"+Double.parseDouble(tbObat.getValueAt(i,1).toString())+"'","kode_brng='"+tbObat.getValueAt(i,2).toString()+"' and kd_bangsal='"+lokasistok+"' and no_batch='"+tbObat.getValueAt(i,12).toString()+"' and no_faktur='"+tbObat.getValueAt(i,13).toString()+"'");   
                                            }else{
                                                Trackobat.catatRiwayat(tbObat.getValueAt(i,2).toString(),0,Double.parseDouble(tbObat.getValueAt(i,1).toString()),"Pemberian Obat",akses.getkode(),lokasistok,"Simpan","","");
                                                Sequel.menyimpan("gudangbarang","'"+tbObat.getValueAt(i,2).toString()+"','"+lokasistok+"','-"+Double.parseDouble(tbObat.getValueAt(i,1).toString())+"','',''", 
                                                         "stok=stok-'"+Double.parseDouble(tbObat.getValueAt(i,1).toString())+"'","kode_brng='"+tbObat.getValueAt(i,2).toString()+"' and kd_bangsal='"+lokasistok+"' and no_batch='' and no_faktur=''");   
                                            }
                                            ttljual=ttljual+Double.parseDouble(tbObat.getValueAt(i,9).toString())+Double.parseDouble(tbObat.getValueAt(i,10).toString())+
                                                    (Double.parseDouble(tbObat.getValueAt(i,6).toString())*Double.parseDouble(tbObat.getValueAt(i,1).toString()));
                                            ttlhpp=ttlhpp+(Double.parseDouble(tbObat.getValueAt(i,11).toString())*Double.parseDouble(tbObat.getValueAt(i,1).toString()));
                                        }else{
                                            sukses=false;
                                        }                                   
                                    }
                                } catch (Exception e) {
                                    sukses=false;
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
                                    "Ranap",lokasistok,tbObat.getValueAt(i,12).toString(),tbObat.getValueAt(i,13).toString()
                                })==true){
                                    if(aktifkanbatch.equals("yes")){
                                        Sequel.mengedit3("data_batch","no_batch=? and kode_brng=? and no_faktur=?","sisa=sisa-?",4,new String[]{
                                            ""+Double.parseDouble(tbObat.getValueAt(i,1).toString()),tbObat.getValueAt(i,12).toString(),tbObat.getValueAt(i,2).toString(),tbObat.getValueAt(i,13).toString()
                                        });
                                        Trackobat.catatRiwayat(tbObat.getValueAt(i,2).toString(),0,Double.parseDouble(tbObat.getValueAt(i,1).toString()),"Pemberian Obat",akses.getkode(),lokasistok,"Simpan",tbObat.getValueAt(i,12).toString(),tbObat.getValueAt(i,13).toString());
                                        Sequel.menyimpan("gudangbarang","'"+tbObat.getValueAt(i,2).toString()+"','"+lokasistok+"','-"+Double.parseDouble(tbObat.getValueAt(i,1).toString())+"','"+tbObat.getValueAt(i,12).toString()+"','"+tbObat.getValueAt(i,13).toString()+"'", 
                                                 "stok=stok-'"+Double.parseDouble(tbObat.getValueAt(i,1).toString())+"'","kode_brng='"+tbObat.getValueAt(i,2).toString()+"' and kd_bangsal='"+lokasistok+"' and no_batch='"+tbObat.getValueAt(i,12).toString()+"' and no_faktur='"+tbObat.getValueAt(i,13).toString()+"'");   
                                    }else{
                                        Trackobat.catatRiwayat(tbObat.getValueAt(i,2).toString(),0,Double.parseDouble(tbObat.getValueAt(i,1).toString()),"Pemberian Obat",akses.getkode(),lokasistok,"Simpan","","");
                                        Sequel.menyimpan("gudangbarang","'"+tbObat.getValueAt(i,2).toString()+"','"+lokasistok+"','-"+Double.parseDouble(tbObat.getValueAt(i,1).toString())+"','',''", 
                                                 "stok=stok-'"+Double.parseDouble(tbObat.getValueAt(i,1).toString())+"'","kode_brng='"+tbObat.getValueAt(i,2).toString()+"' and kd_bangsal='"+lokasistok+"' and no_batch='' and no_faktur=''");   
                                    }
                                    ttljual=ttljual+Double.parseDouble(tbObat.getValueAt(i,9).toString())+Double.parseDouble(tbObat.getValueAt(i,10).toString())+
                                            (Double.parseDouble(tbObat.getValueAt(i,6).toString())*Double.parseDouble(tbObat.getValueAt(i,1).toString()));
                                    ttlhpp=ttlhpp+(Double.parseDouble(tbObat.getValueAt(i,11).toString())*Double.parseDouble(tbObat.getValueAt(i,1).toString()));
                                }else{
                                    sukses=false;
                                }                                  
                            }  
                        } catch (Exception e) {
                            sukses=false;
                            System.out.println("Notif Simpan : "+e);
                        }
                    }
                }
            }
            
            if(sukses==true){
                Sequel.queryu("delete from tampjurnal");    
                if(ttljual>0){
                    Sequel.menyimpan("tampjurnal","'"+Suspen_Piutang_Obat_Ranap+"','Suspen Piutang Obat Ranap','"+ttljual+"','0'","Rekening");    
                    Sequel.menyimpan("tampjurnal","'"+Obat_Ranap+"','Pendapatan Obat Rawat Inap','0','"+ttljual+"'","Rekening");                              
                }
                if(ttlhpp>0){
                    Sequel.menyimpan("tampjurnal","'"+HPP_Obat_Rawat_Inap+"','HPP Persediaan Obat Rawat Inap','"+ttlhpp+"','0'","Rekening");    
                    Sequel.menyimpan("tampjurnal","'"+Persediaan_Obat_Rawat_Inap+"','Persediaan Obat Rawat Inap','0','"+ttlhpp+"'","Rekening");                              
                }
                sukses=jur.simpanJurnal(NoRawat.getText(),Sequel.cariIsi("select current_date()"),"U","PEMBERIAN OBAT RAWAT INAP PASIEN, DIPOSTING OLEH "+akses.getkode());     
            }
            
            if(sukses==true){
                Sequel.Commit();
            }else{
                sukses=false;
                JOptionPane.showMessageDialog(null,"Terjadi kesalahan saat pemrosesan data, transaksi dibatalkan.\nPeriksa kembali data sebelum melanjutkan menyimpan..!!");
                Sequel.RollBack();
            }
            
            Sequel.AutoComitTrue();

            if(sukses==true){
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

    private void tbObatPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tbObatPropertyChange
        if((this.isVisible()==true)&&(!NoRawat.getText().equals(""))){
              getDataObat();
        }
    }//GEN-LAST:event_tbObatPropertyChange

    private void tbObatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbObatMouseClicked
        if(tabModeObat.getRowCount()!=0){
            try {                  
                getDataObat();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbObatMouseClicked

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        if(NoRawat.getText().trim().equals("")){
            Valid.textKosong(NoRawat,"Pasien");
        }else{
            DlgRawatInap rawatinap=new DlgRawatInap(null,false);
            rawatinap.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            rawatinap.setLocationRelativeTo(internalFrame1);
            rawatinap.isCek();
            rawatinap.setNoRm(NoRawat.getText(),new Date(),new Date());    
            rawatinap.tampilDr();
            rawatinap.setVisible(true);
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
            DlgBarcodeRanap dialog = new DlgBarcodeRanap(new javax.swing.JFrame(), true);
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
            pscari=koneksi.prepareStatement("select jns_perawatan_inap.kd_jenis_prw,jns_perawatan_inap.nm_perawatan,kategori_perawatan.nm_kategori,"+
                   "jns_perawatan_inap.total_byrdr,jns_perawatan_inap.total_byrpr,jns_perawatan_inap.total_byrdrpr,jns_perawatan_inap.bhp,jns_perawatan_inap.material,jns_perawatan_inap.kso,jns_perawatan_inap.menejemen," +
                   "jns_perawatan_inap.tarif_tindakandr,jns_perawatan_inap.tarif_tindakanpr from jns_perawatan_inap inner join kategori_perawatan "+
                   "on jns_perawatan_inap.kd_kategori=kategori_perawatan.kd_kategori  "+
                   "where jns_perawatan_inap.status='1' and (jns_perawatan_inap.kd_pj=? or jns_perawatan_inap.kd_pj='-') and (jns_perawatan_inap.kd_bangsal=? or jns_perawatan_inap.kd_bangsal='-') and jns_perawatan_inap.kd_jenis_prw like ? or "+
                    " jns_perawatan_inap.status='1' and (jns_perawatan_inap.kd_pj=? or jns_perawatan_inap.kd_pj='-') and (jns_perawatan_inap.kd_bangsal=? or jns_perawatan_inap.kd_bangsal='-') and jns_perawatan_inap.nm_perawatan like ? or "+
                    " jns_perawatan_inap.status='1' and (jns_perawatan_inap.kd_pj=? or jns_perawatan_inap.kd_pj='-') and (jns_perawatan_inap.kd_bangsal=? or jns_perawatan_inap.kd_bangsal='-') and kategori_perawatan.nm_kategori like ? order by jns_perawatan_inap.nm_perawatan");
            pscari2=koneksi.prepareStatement("select jns_perawatan_inap.kd_jenis_prw,jns_perawatan_inap.nm_perawatan,kategori_perawatan.nm_kategori,"+
                   "jns_perawatan_inap.total_byrdr,jns_perawatan_inap.total_byrpr,jns_perawatan_inap.total_byrdrpr,jns_perawatan_inap.bhp,jns_perawatan_inap.material,jns_perawatan_inap.kso,jns_perawatan_inap.menejemen," +
                   "jns_perawatan_inap.tarif_tindakandr,jns_perawatan_inap.tarif_tindakanpr from jns_perawatan_inap inner join kategori_perawatan "+
                   "on jns_perawatan_inap.kd_kategori=kategori_perawatan.kd_kategori  "+
                   "where jns_perawatan_inap.status='1' and (jns_perawatan_inap.kd_pj=? or jns_perawatan_inap.kd_pj='-') and jns_perawatan_inap.kd_jenis_prw like ? or "+
                    " jns_perawatan_inap.status='1' and (jns_perawatan_inap.kd_pj=? or jns_perawatan_inap.kd_pj='-') and jns_perawatan_inap.nm_perawatan like ? or "+
                    " jns_perawatan_inap.status='1' and (jns_perawatan_inap.kd_pj=? or jns_perawatan_inap.kd_pj='-') and kategori_perawatan.nm_kategori like ? order by jns_perawatan_inap.nm_perawatan");
            pscari3=koneksi.prepareStatement("select jns_perawatan_inap.kd_jenis_prw,jns_perawatan_inap.nm_perawatan,kategori_perawatan.nm_kategori,"+
                   "jns_perawatan_inap.total_byrdr,jns_perawatan_inap.total_byrpr,jns_perawatan_inap.total_byrdrpr,jns_perawatan_inap.bhp,jns_perawatan_inap.material,jns_perawatan_inap.kso,jns_perawatan_inap.menejemen," +
                   "jns_perawatan_inap.tarif_tindakandr,jns_perawatan_inap.tarif_tindakanpr from jns_perawatan_inap inner join kategori_perawatan "+
                   "on jns_perawatan_inap.kd_kategori=kategori_perawatan.kd_kategori  "+
                   "where jns_perawatan_inap.status='1' and (jns_perawatan_inap.kd_bangsal=? or jns_perawatan_inap.kd_bangsal='-') and jns_perawatan_inap.kd_jenis_prw like ? or "+
                    " jns_perawatan_inap.status='1' and (jns_perawatan_inap.kd_bangsal=? or jns_perawatan_inap.kd_bangsal='-') and jns_perawatan_inap.nm_perawatan like ? or "+
                    " jns_perawatan_inap.status='1' and (jns_perawatan_inap.kd_bangsal=? or jns_perawatan_inap.kd_bangsal='-') and kategori_perawatan.nm_kategori like ? order by jns_perawatan_inap.nm_perawatan");
            pscari4=koneksi.prepareStatement("select jns_perawatan_inap.kd_jenis_prw,jns_perawatan_inap.nm_perawatan,kategori_perawatan.nm_kategori,"+
                   "jns_perawatan_inap.total_byrdr,jns_perawatan_inap.total_byrpr,jns_perawatan_inap.total_byrdrpr,jns_perawatan_inap.bhp,jns_perawatan_inap.material,jns_perawatan_inap.kso,jns_perawatan_inap.menejemen," +
                   "jns_perawatan_inap.tarif_tindakandr,jns_perawatan_inap.tarif_tindakanpr from jns_perawatan_inap inner join kategori_perawatan "+
                   "on jns_perawatan_inap.kd_kategori=kategori_perawatan.kd_kategori  "+
                   "where jns_perawatan_inap.status='1' and jns_perawatan_inap.kd_jenis_prw like ? or "+
                    " jns_perawatan_inap.status='1' and jns_perawatan_inap.nm_perawatan like ? or "+
                    " jns_perawatan_inap.status='1' and kategori_perawatan.nm_kategori like ? order by jns_perawatan_inap.nm_perawatan");
            try {
                if(ruang_ranap.equals("Yes")&&cara_bayar_ranap.equals("Yes")){
                    pscari.setString(1,kd_pj.trim());
                    pscari.setString(2,kd_bangsal.trim());
                    pscari.setString(3,"%"+TCariTindakan.getText().trim()+"%");
                    pscari.setString(4,kd_pj.trim());
                    pscari.setString(5,kd_bangsal.trim());
                    pscari.setString(6,"%"+TCariTindakan.getText().trim()+"%");
                    pscari.setString(7,kd_pj.trim());
                    pscari.setString(8,kd_bangsal.trim());
                    pscari.setString(9,"%"+TCariTindakan.getText().trim()+"%");
                    rstindakan=pscari.executeQuery();
                }else if(ruang_ranap.equals("No")&&cara_bayar_ranap.equals("Yes")){
                    pscari2.setString(1,kd_pj.trim());
                    pscari2.setString(2,"%"+TCariTindakan.getText().trim()+"%");
                    pscari2.setString(3,kd_pj.trim());
                    pscari2.setString(4,"%"+TCariTindakan.getText().trim()+"%");
                    pscari2.setString(5,kd_pj.trim());
                    pscari2.setString(6,"%"+TCariTindakan.getText().trim()+"%");
                    rstindakan=pscari2.executeQuery();
                }else if(ruang_ranap.equals("Yes")&&cara_bayar_ranap.equals("No")){
                    pscari3.setString(1,kd_bangsal.trim());
                    pscari3.setString(2,"%"+TCariTindakan.getText().trim()+"%");
                    pscari3.setString(3,kd_bangsal.trim());
                    pscari3.setString(4,"%"+TCariTindakan.getText().trim()+"%");
                    pscari3.setString(5,kd_bangsal.trim());
                    pscari3.setString(6,"%"+TCariTindakan.getText().trim()+"%");
                    rstindakan=pscari3.executeQuery();
                }else if(ruang_ranap.equals("No")&&cara_bayar_ranap.equals("No")){
                    pscari4.setString(1,"%"+TCariTindakan.getText().trim()+"%");
                    pscari4.setString(2,"%"+TCariTindakan.getText().trim()+"%");
                    pscari4.setString(3,"%"+TCariTindakan.getText().trim()+"%");
                    rstindakan=pscari4.executeQuery();
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
                System.out.println("Notif Tindakan : "+e);
            } finally{
                if(rstindakan!=null){
                    rstindakan.close();
                }
                if(pscari!=null){
                    pscari.close();
                }
                if(pscari2!=null){
                    pscari2.close();
                }
                if(pscari3!=null){
                    pscari3.close();
                }
                if(pscari4!=null){
                    pscari4.close();
                }
            }
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }
    
    public void setNoRm(String norwt) {
        this.kd_pj=Sequel.cariIsi("select kd_pj from reg_periksa where no_rawat=?",norwt);
        this.kd_bangsal=Sequel.cariIsi(
                "select bangsal.kd_bangsal from bangsal inner join kamar inner join kamar_inap "+
                "on bangsal.kd_bangsal=kamar.kd_bangsal and kamar.kd_kamar=kamar_inap.kd_kamar "+
                "where kamar_inap.no_rawat=? and kamar_inap.stts_pulang='-' order by STR_TO_DATE(concat(kamar_inap.tgl_masuk,' ',jam_masuk),'%Y-%m-%d %H:%i:%s') desc limit 1",norwt);
        this.kelas=Sequel.cariIsi(
                "select kamar.kelas from kamar inner join kamar_inap on kamar.kd_kamar=kamar_inap.kd_kamar "+
                "where no_rawat=? and stts_pulang='-' order by STR_TO_DATE(concat(kamar_inap.tgl_masuk,' ',jam_masuk),'%Y-%m-%d %H:%i:%s') desc limit 1",norwt);
        this.kd_dokter=Sequel.cariIsi("select kd_dokter from reg_periksa where no_rawat=?",norwt);
        lokasistok=Sequel.cariIsi("select kd_depo from set_depo_ranap where kd_bangsal=?",Sequel.cariIsi("select kd_bangsal from kamar where kd_kamar=?",this.kd_bangsal));
        if(lokasistok.equals("")){
            if(Sequel.cariIsi("select asal_stok from set_lokasi").equals("Gunakan Stok Bangsal")){
                lokasistok=this.kd_bangsal;
            }else{
                lokasistok=Sequel.cariIsi("select kd_bangsal from set_lokasi");
            }
        }
            
        TCariTindakan.requestFocus();
        kenaikan=Sequel.cariIsiAngka("select (hargajual/100) from set_harga_obat_ranap where kd_pj='"+this.kd_pj+"' and kelas='"+this.kelas+"'");
        try {
            pstarif=koneksi.prepareStatement("select * from set_tarif");
            try {
                rstarif=pstarif.executeQuery();
                if(rstarif.next()){
                    ruang_ranap=rstarif.getString("ruang_ranap");
                    cara_bayar_ranap=rstarif.getString("cara_bayar_ranap");
                }else{
                    ruang_ranap="Yes";
                    cara_bayar_ranap="Yes";
                }  
            } catch (Exception e) {
                System.out.println("Notif Cek Tarif : "+e);
            } finally{
                if(rstarif!=null){
                    rstarif.close();
                }
                if(pstarif!=null){
                    pstarif.close();
                }
            }
        } catch (Exception e) {
            System.out.println(e);
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
                            " select data_batch.kode_brng, databarang.nama_brng,jenis.nama, databarang.kode_sat,(data_batch.h_beli+(data_batch.h_beli*?)) as harga,gudangbarang.stok,"+
                            " databarang.letak_barang,databarang.dasar,data_batch.no_batch,data_batch.no_faktur from data_batch inner join databarang on data_batch.kode_brng=databarang.kode_brng inner join jenis on databarang.kdjns=jenis.kdjns "+
                            " inner join gudangbarang on gudangbarang.kode_brng=data_batch.kode_brng and gudangbarang.no_batch=data_batch.no_batch and gudangbarang.no_faktur=data_batch.no_faktur "+
                            " where gudangbarang.stok>0 and gudangbarang.kd_bangsal=? and databarang.kode_brng like ? or "+
                            " gudangbarang.stok>0 and gudangbarang.kd_bangsal=? and databarang.nama_brng like ? or "+
                            " gudangbarang.stok>0 and gudangbarang.kd_bangsal=? and jenis.nama like ? order by databarang.nama_brng");
                    try {
                        psobat.setDouble(1,kenaikan);
                        psobat.setString(2,lokasistok);
                        psobat.setString(3,"%"+TCariObat.getText().trim()+"%");
                        psobat.setString(4,lokasistok);
                        psobat.setString(5,"%"+TCariObat.getText().trim()+"%");
                        psobat.setString(6,lokasistok);
                        psobat.setString(7,"%"+TCariObat.getText().trim()+"%");
                        rsobat=psobat.executeQuery();
                        while(rsobat.next()){   
                            tabModeObat.addRow(new Object[] {
                                false,"",rsobat.getString("kode_brng"),rsobat.getString("nama_brng"),
                                rsobat.getString("kode_sat"),rsobat.getString("letak_barang"),
                                rsobat.getDouble("harga"),rsobat.getDouble("stok"),rsobat.getString("nama"),0,0,
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
                            " select data_batch.kode_brng, databarang.nama_brng,jenis.nama, databarang.kode_sat,gudangbarang.stok,data_batch.kelas1,"+
                            " data_batch.kelas2,data_batch.kelas3,data_batch.utama,data_batch.vip,data_batch.vvip,"+
                            " databarang.letak_barang,data_batch.dasar,data_batch.no_batch,data_batch.no_faktur from data_batch inner join databarang on data_batch.kode_brng=databarang.kode_brng inner join jenis on databarang.kdjns=jenis.kdjns "+
                            " inner join gudangbarang on gudangbarang.kode_brng=data_batch.kode_brng and gudangbarang.no_batch=data_batch.no_batch and gudangbarang.no_faktur=data_batch.no_faktur "+
                            " where gudangbarang.stok>0 and gudangbarang.kd_bangsal=? and databarang.kode_brng like ? or "+
                            " gudangbarang.stok>0 and gudangbarang.kd_bangsal=? and databarang.nama_brng like ? or "+
                            " gudangbarang.stok>0 and gudangbarang.kd_bangsal=? and jenis.nama like ? order by databarang.nama_brng");
                    try {
                        psobat.setString(1,lokasistok);
                        psobat.setString(2,"%"+TCariObat.getText().trim()+"%");
                        psobat.setString(3,lokasistok);
                        psobat.setString(4,"%"+TCariObat.getText().trim()+"%");
                        psobat.setString(5,lokasistok);
                        psobat.setString(6,"%"+TCariObat.getText().trim()+"%");
                        rsobat=psobat.executeQuery();
                        if(kelas.equals("Kelas 1")){
                            while(rsobat.next()){   
                                tabModeObat.addRow(new Object[] {
                                    false,"",rsobat.getString("kode_brng"),rsobat.getString("nama_brng"),
                                    rsobat.getString("kode_sat"),rsobat.getString("letak_barang"),
                                    rsobat.getDouble("kelas1"),rsobat.getDouble("stok"),rsobat.getString("nama"),
                                    0,0,rsobat.getDouble("dasar"),rsobat.getString("no_batch"),rsobat.getString("no_faktur")
                                });
                            }
                        }else if(kelas.equals("Kelas 2")){
                            while(rsobat.next()){   
                                tabModeObat.addRow(new Object[] {
                                    false,"",rsobat.getString("kode_brng"),rsobat.getString("nama_brng"),
                                    rsobat.getString("kode_sat"),rsobat.getString("letak_barang"),
                                    rsobat.getDouble("kelas2"),rsobat.getDouble("stok"),rsobat.getString("nama"),
                                    0,0,rsobat.getDouble("dasar"),rsobat.getString("no_batch"),rsobat.getString("no_faktur")
                                });
                            }
                        }else if(kelas.equals("Kelas 3")){
                            while(rsobat.next()){   
                                tabModeObat.addRow(new Object[] {
                                    false,"",rsobat.getString("kode_brng"),rsobat.getString("nama_brng"),
                                    rsobat.getString("kode_sat"),rsobat.getString("letak_barang"),
                                    rsobat.getDouble("kelas3"),rsobat.getDouble("stok"),rsobat.getString("nama"),
                                    0,0,rsobat.getDouble("dasar"),rsobat.getString("no_batch"),rsobat.getString("no_faktur")
                                });
                            }
                        }else if(kelas.equals("Kelas Utama")){
                            while(rsobat.next()){   
                                tabModeObat.addRow(new Object[] {
                                    false,"",rsobat.getString("kode_brng"),rsobat.getString("nama_brng"),
                                    rsobat.getString("kode_sat"),rsobat.getString("letak_barang"),
                                    rsobat.getDouble("utama"),rsobat.getDouble("stok"),rsobat.getString("nama"),
                                    0,0,rsobat.getDouble("dasar"),rsobat.getString("no_batch"),rsobat.getString("no_faktur")
                                });
                            }
                        }else if(kelas.equals("Kelas VIP")){
                            while(rsobat.next()){   
                                tabModeObat.addRow(new Object[] {
                                    false,"",rsobat.getString("kode_brng"),rsobat.getString("nama_brng"),
                                    rsobat.getString("kode_sat"),rsobat.getString("letak_barang"),
                                    rsobat.getDouble("vip"),rsobat.getDouble("stok"),rsobat.getString("nama"),
                                    0,0,rsobat.getDouble("dasar"),rsobat.getString("no_batch"),rsobat.getString("no_faktur")
                                });
                            }
                        }else if(kelas.equals("Kelas VVIP")){
                            while(rsobat.next()){   
                                tabModeObat.addRow(new Object[] {
                                    false,"",rsobat.getString("kode_brng"),rsobat.getString("nama_brng"),
                                    rsobat.getString("kode_sat"),rsobat.getString("letak_barang"),
                                    rsobat.getDouble("vvip"),rsobat.getDouble("stok"),rsobat.getString("nama"),
                                    0,0,rsobat.getDouble("dasar"),rsobat.getString("no_batch"),rsobat.getString("no_faktur")
                                });
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Notif Obat "+e);
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
                            " select databarang.kode_brng, databarang.nama_brng,jenis.nama, databarang.kode_sat,(databarang.h_beli+(databarang.h_beli*?)) as harga,gudangbarang.stok,"+
                            " databarang.letak_barang,databarang.dasar from databarang inner join jenis on databarang.kdjns=jenis.kdjns inner join gudangbarang on databarang.kode_brng=gudangbarang.kode_brng "+
                            " where gudangbarang.no_batch='' and gudangbarang.no_faktur='' and gudangbarang.stok>0 and gudangbarang.kd_bangsal=? and databarang.status='1' and databarang.kode_brng like ? or "+
                            " gudangbarang.no_batch='' and gudangbarang.no_faktur='' and gudangbarang.stok>0 and gudangbarang.kd_bangsal=? and databarang.status='1' and databarang.nama_brng like ? or "+
                            " gudangbarang.no_batch='' and gudangbarang.no_faktur='' and gudangbarang.stok>0 and gudangbarang.kd_bangsal=? and databarang.status='1' and jenis.nama like ? order by databarang.nama_brng");
                    try {
                        psobat.setDouble(1,kenaikan);
                        psobat.setString(2,lokasistok);
                        psobat.setString(3,"%"+TCariObat.getText().trim()+"%");
                        psobat.setString(4,lokasistok);
                        psobat.setString(5,"%"+TCariObat.getText().trim()+"%");
                        psobat.setString(6,lokasistok);
                        psobat.setString(7,"%"+TCariObat.getText().trim()+"%");
                        rsobat=psobat.executeQuery();
                        while(rsobat.next()){   
                            tabModeObat.addRow(new Object[] {
                                false,"",rsobat.getString("kode_brng"),rsobat.getString("nama_brng"),
                                rsobat.getString("kode_sat"),rsobat.getString("letak_barang"),
                                rsobat.getDouble("harga"),rsobat.getDouble("stok"),rsobat.getString("nama"),0,0,rsobat.getDouble("dasar"),"",""
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
                            " select databarang.kode_brng, databarang.nama_brng,jenis.nama, databarang.kode_sat,gudangbarang.stok,databarang.kelas1,"+
                            " databarang.kelas2,databarang.kelas3,databarang.utama,databarang.vip,databarang.vvip,"+
                            " databarang.letak_barang,databarang.dasar from databarang inner join jenis on databarang.kdjns=jenis.kdjns inner join gudangbarang on databarang.kode_brng=gudangbarang.kode_brng "+
                            " where gudangbarang.no_batch='' and gudangbarang.no_faktur='' and gudangbarang.stok>0 and gudangbarang.kd_bangsal=? and databarang.status='1' and databarang.kode_brng like ? or "+
                            " gudangbarang.no_batch='' and gudangbarang.no_faktur='' and gudangbarang.stok>0 and gudangbarang.kd_bangsal=? and databarang.status='1' and databarang.nama_brng like ? or "+
                            " gudangbarang.no_batch='' and gudangbarang.no_faktur='' and gudangbarang.stok>0 and gudangbarang.kd_bangsal=? and databarang.status='1' and jenis.nama like ? order by databarang.nama_brng");
                    try {
                        psobat.setString(1,lokasistok);
                        psobat.setString(2,"%"+TCariObat.getText().trim()+"%");
                        psobat.setString(3,lokasistok);
                        psobat.setString(4,"%"+TCariObat.getText().trim()+"%");
                        psobat.setString(5,lokasistok);
                        psobat.setString(6,"%"+TCariObat.getText().trim()+"%");
                        rsobat=psobat.executeQuery();
                        if(kelas.equals("Kelas 1")){
                            while(rsobat.next()){   
                                tabModeObat.addRow(new Object[] {
                                    false,"",rsobat.getString("kode_brng"),rsobat.getString("nama_brng"),
                                    rsobat.getString("kode_sat"),rsobat.getString("letak_barang"),
                                    rsobat.getDouble("kelas1"),rsobat.getDouble("stok"),rsobat.getString("nama"),
                                    0,0,rsobat.getDouble("dasar"),"",""
                                });
                            }
                        }else if(kelas.equals("Kelas 2")){
                            while(rsobat.next()){   
                                tabModeObat.addRow(new Object[] {
                                    false,"",rsobat.getString("kode_brng"),rsobat.getString("nama_brng"),
                                    rsobat.getString("kode_sat"),rsobat.getString("letak_barang"),
                                    rsobat.getDouble("kelas2"),rsobat.getDouble("stok"),rsobat.getString("nama"),
                                    0,0,rsobat.getDouble("dasar"),"",""
                                });
                            }
                        }else if(kelas.equals("Kelas 3")){
                            while(rsobat.next()){   
                                tabModeObat.addRow(new Object[] {
                                    false,"",rsobat.getString("kode_brng"),rsobat.getString("nama_brng"),
                                    rsobat.getString("kode_sat"),rsobat.getString("letak_barang"),
                                    rsobat.getDouble("kelas3"),rsobat.getDouble("stok"),rsobat.getString("nama"),
                                    0,0,rsobat.getDouble("dasar"),"",""
                                });
                            }
                        }else if(kelas.equals("Kelas Utama")){
                            while(rsobat.next()){   
                                tabModeObat.addRow(new Object[] {
                                    false,"",rsobat.getString("kode_brng"),rsobat.getString("nama_brng"),
                                    rsobat.getString("kode_sat"),rsobat.getString("letak_barang"),
                                    rsobat.getDouble("utama"),rsobat.getDouble("stok"),rsobat.getString("nama"),
                                    0,0,rsobat.getDouble("dasar"),"",""
                                });
                            }
                        }else if(kelas.equals("Kelas VIP")){
                            while(rsobat.next()){   
                                tabModeObat.addRow(new Object[] {
                                    false,"",rsobat.getString("kode_brng"),rsobat.getString("nama_brng"),
                                    rsobat.getString("kode_sat"),rsobat.getString("letak_barang"),
                                    rsobat.getDouble("vip"),rsobat.getDouble("stok"),rsobat.getString("nama"),
                                    0,0,rsobat.getDouble("dasar"),"",""
                                });
                            }
                        }else if(kelas.equals("Kelas VVIP")){
                            while(rsobat.next()){   
                                tabModeObat.addRow(new Object[] {
                                    false,"",rsobat.getString("kode_brng"),rsobat.getString("nama_brng"),
                                    rsobat.getString("kode_sat"),rsobat.getString("letak_barang"),
                                    rsobat.getDouble("vvip"),rsobat.getDouble("stok"),rsobat.getString("nama"),
                                    0,0,rsobat.getDouble("dasar"),"",""
                                });
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Notif Obat "+e);
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
        if(lokasistok.equals("")){
             Valid.textKosong(TCariObat,"Asal Stok");
        }else if(row!= -1){            
             if(!tabModeObat.getValueAt(row,1).toString().equals("")){
                try {
                    if(Double.parseDouble(tabModeObat.getValueAt(row,1).toString())>0){
                        stokbarang=0;   
                        if(aktifkanbatch.equals("yes")){
                            psobat=koneksi.prepareStatement("select ifnull(stok,'0') from gudangbarang where kd_bangsal=? and kode_brng=? and no_batch=? and no_faktur=?");
                            try {
                                psobat.setString(1,lokasistok);
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
                        }else{
                            psobat=koneksi.prepareStatement("select ifnull(stok,'0') from gudangbarang where kd_bangsal=? and kode_brng=? and no_batch='' and no_faktur=''");
                            try {
                                psobat.setString(1,lokasistok);
                                psobat.setString(2,tbObat.getValueAt(row,2).toString());
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

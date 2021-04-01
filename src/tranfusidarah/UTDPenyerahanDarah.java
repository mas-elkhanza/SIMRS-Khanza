package tranfusidarah;
import fungsi.WarnaTable;
import fungsi.WarnaTable2;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import keuangan.Jurnal;
import kepegawaian.DlgCariPetugas;

public class UTDPenyerahanDarah extends javax.swing.JDialog {
    private final DefaultTableModel tabModeMedis,tabModeNonMedis,tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Jurnal jur=new Jurnal();
    private Connection koneksi=koneksiDB.condb();
    private SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd");
    private Dimension screen=Toolkit.getDefaultToolkit().getScreenSize();
    private double ttl=0,y=0,stokbarang=0,bayar=0,total2=0,ppn=0,besarppn=0,tagihanppn=0;;
    private int jml=0,i=0,index=0,row=0,pilih=0;
    private String verifikasi_penyerahan_darah_di_kasir="",status="Belum Dibayar";
    private PreparedStatement ps,ps2,psstok,psdarah;
    private ResultSet rs,rs2,rsstok,rsdarah;
    private boolean[] pilihan;
    private String[] kodebarang,namabarang,kategori,satuan,jumlah,stokasal,hbeli,total,
            nokantung,komponen,gd,resus,aftap,kadaluarsa,asaldarah,satatus,js,bhp,kso,menejemen;
    private double[] harga,biaya;
    private DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    private WarnaTable2 warna=new WarnaTable2();
    private UTDCariPenyerahanDarah carijual=new UTDCariPenyerahanDarah(null,false);
    private boolean sukses=false;

    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public UTDPenyerahanDarah(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        warna.kolom=0;
        
        Object[] row={
            "P","No.Kantung","Komponen","G.D.","Rhesus","Aftap","Kadaluarsa",
            "Asal Darah","Status","Jasa Sarana","Paket BHP",
            "KSO","Manajemen","Biaya"};
        tabMode=new DefaultTableModel(null,row){
            @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
            }
            
            Class[] types = new Class[] {
                java.lang.Boolean.class,java.lang.String.class,java.lang.String.class,
                java.lang.String.class,java.lang.String.class,java.lang.String.class,
                java.lang.String.class,java.lang.String.class,java.lang.String.class,
                java.lang.String.class,java.lang.String.class,java.lang.String.class,
                java.lang.String.class,java.lang.Double.class
            };
            @Override
            public Class getColumnClass(int columnIndex) {
               return types [columnIndex];
            }
        };
        tbDarah.setModel(tabMode);

        tbDarah.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbDarah.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 14; i++) {
            TableColumn column = tbDarah.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(22);
            }else if(i==1){
                column.setPreferredWidth(80);
            }else if(i==2){
                column.setPreferredWidth(220);
            }else if(i==3){
                column.setPreferredWidth(35);
            }else if(i==4){
                column.setPreferredWidth(50);
            }else if(i==5){
                column.setPreferredWidth(75);
            }else if(i==6){
                column.setPreferredWidth(75);
            }else if((i==7)||(i==13)){
                column.setPreferredWidth(90);
            }else if((i==8)||(i==9)||(i==10)||(i==11)||(i==12)){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }
        }
        tbDarah.setDefaultRenderer(Object.class, new WarnaTable());

        tabModeMedis=new DefaultTableModel(null,new Object[]{"Jml","Kode Barang","Nama Barang","Harga","Subtotal","Satuan","Stok"}){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
        };
        tbMedis.setModel(tabModeMedis);
        tbMedis.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbMedis.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 7; i++) {
            TableColumn column = tbMedis.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(35);
            }else if(i==1){
                column.setPreferredWidth(80);
            }else if(i==2){
                column.setPreferredWidth(200);
            }else if(i==3){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==4){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==5){
                column.setPreferredWidth(50);
            }else if(i==6){
                column.setPreferredWidth(40);
            }
        }
        tbMedis.setDefaultRenderer(Object.class,warna);
        //non medis
        tabModeNonMedis=new DefaultTableModel(null,new Object[]{"Jml","Kode Barang","Nama Barang","Harga","Subtotal","Satuan","Stok"}){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
        };
        tbNonMedis.setModel(tabModeNonMedis);
        tbNonMedis.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbNonMedis.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 7; i++) {
            TableColumn column = tbNonMedis.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(35);
            }else if(i==1){
                column.setPreferredWidth(80);
            }else if(i==2){
                column.setPreferredWidth(200);
            }else if(i==3){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==4){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==5){
                column.setPreferredWidth(50);
            }else if(i==6){
                column.setPreferredWidth(40);
            }
        }
        tbNonMedis.setDefaultRenderer(Object.class,warna);
        
        nopenyerahan.setDocument(new batasInput((byte)17).getKata(nopenyerahan));
        kdptgcross.setDocument(new batasInput((byte)20).getKata(kdptgcross));
        kdptgpj.setDocument(new batasInput((byte)20).getKata(kdptgpj));
        keterangan.setDocument(new batasInput((byte)40).getKata(keterangan));
        Bayar.setDocument(new batasInput((byte)14).getOnlyAngka(Bayar));   
        TCari.setDocument(new batasInput((byte)100).getKata(TCari)); 
        TCariMedis.setDocument(new batasInput((byte)100).getKata(TCariMedis));
        TCariNonMedis.setDocument(new batasInput((byte)100).getKata(TCariNonMedis)); 
        nmpengambil.setDocument(new batasInput((byte)70).getKata(nmpengambil));
        alamatpengambil.setDocument(new batasInput((byte)100).getKata(alamatpengambil));
        
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
        
        Bayar.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {isKembali();}
            @Override
            public void removeUpdate(DocumentEvent e) {isKembali();}
            @Override
            public void changedUpdate(DocumentEvent e) {isKembali();}
        });
        
        petugas.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(petugas.getTable().getSelectedRow()!= -1){ 
                    if(pilih==1){
                        kdptgcross.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                        nmptgcross.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                        kdptgcross.requestFocus();
                    }else if(pilih==2){
                        kdptgpj.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                        nmptgpj.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                        kdptgpj.requestFocus();
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
        
        Valid.loadCombo(CmbAkun,"nama_bayar","akun_bayar");
        try {            
            PPN.setText(Sequel.cariIsi("select ppn from akun_bayar where nama_bayar=?",CmbAkun.getSelectedItem().toString()));
        } catch (Exception e) {
            PPN.setText("0");
        }
        try {
            verifikasi_penyerahan_darah_di_kasir=Sequel.cariIsi("select verifikasi_penyerahan_darah_di_kasir from set_nota");
        } catch (Exception e) {
            verifikasi_penyerahan_darah_di_kasir="No";
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
        jPanel1 = new javax.swing.JPanel();
        panelisi1 = new widget.panelisi();
        jLabel17 = new widget.Label();
        CmbCariGd = new widget.ComboBox();
        jLabel13 = new widget.Label();
        CmbCariResus = new widget.ComboBox();
        label9 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari1 = new widget.Button();
        BtnTambah = new widget.Button();
        label22 = new widget.Label();
        BtnNota = new widget.Button();
        BtnSimpan = new widget.Button();
        BtnCari = new widget.Button();
        BtnKeluar = new widget.Button();
        panelisi5 = new widget.panelisi();
        label10 = new widget.Label();
        LTotal = new widget.Label();
        jLabel11 = new widget.Label();
        PPN = new widget.TextBox();
        BesarPPN = new widget.TextBox();
        jLabel12 = new widget.Label();
        TagihanPPn = new widget.Label();
        label19 = new widget.Label();
        Bayar = new widget.TextBox();
        label20 = new widget.Label();
        LKembali = new widget.Label();
        panelisi6 = new widget.panelisi();
        jPanel3 = new javax.swing.JPanel();
        panelisi7 = new widget.panelisi();
        label13 = new widget.Label();
        TCariMedis = new widget.TextBox();
        BtnCari2 = new widget.Button();
        BtnAll1 = new widget.Button();
        Scroll1 = new widget.ScrollPane();
        tbMedis = new widget.Table();
        jPanel4 = new javax.swing.JPanel();
        panelisi8 = new widget.panelisi();
        label17 = new widget.Label();
        TCariNonMedis = new widget.TextBox();
        BtnCari3 = new widget.Button();
        BtnAll2 = new widget.Button();
        Scroll2 = new widget.ScrollPane();
        tbNonMedis = new widget.Table();
        panelisi4 = new widget.panelisi();
        scrollPane1 = new widget.ScrollPane();
        tbDarah = new widget.Table();
        scrollPane2 = new widget.ScrollPane();
        panelisi3 = new widget.panelisi();
        label15 = new widget.Label();
        nopenyerahan = new widget.TextBox();
        label14 = new widget.Label();
        kdptgcross = new widget.TextBox();
        kdptgpj = new widget.TextBox();
        label16 = new widget.Label();
        nmptgcross = new widget.TextBox();
        nmptgpj = new widget.TextBox();
        btnPtgCross = new widget.Button();
        btnPtgPJ = new widget.Button();
        dinas = new widget.ComboBox();
        label18 = new widget.Label();
        keterangan = new widget.TextBox();
        label12 = new widget.Label();
        label11 = new widget.Label();
        tanggal = new widget.Tanggal();
        label21 = new widget.Label();
        nmpengambil = new widget.TextBox();
        label23 = new widget.Label();
        alamatpengambil = new widget.TextBox();
        jLabel10 = new widget.Label();
        CmbAkun = new widget.ComboBox();

        Kd2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Kd2.setName("Kd2"); // NOI18N
        Kd2.setPreferredSize(new java.awt.Dimension(207, 23));

        Popup.setName("Popup"); // NOI18N

        ppBersihkan.setBackground(new java.awt.Color(255, 255, 254));
        ppBersihkan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppBersihkan.setForeground(new java.awt.Color(50,50,50));
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

        ppStok.setBackground(new java.awt.Color(255, 255, 254));
        ppStok.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppStok.setForeground(new java.awt.Color(50,50,50));
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
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Transaksi Penyerahan Darah ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50,50,50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(816, 102));
        jPanel1.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 56));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel17.setText("G.D.:");
        jLabel17.setName("jLabel17"); // NOI18N
        jLabel17.setPreferredSize(new java.awt.Dimension(33, 23));
        panelisi1.add(jLabel17);

        CmbCariGd.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "A", "AB", "B", "O" }));
        CmbCariGd.setName("CmbCariGd"); // NOI18N
        CmbCariGd.setPreferredSize(new java.awt.Dimension(65, 23));
        CmbCariGd.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                CmbCariGdItemStateChanged(evt);
            }
        });
        CmbCariGd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CmbCariGdKeyPressed(evt);
            }
        });
        panelisi1.add(CmbCariGd);

        jLabel13.setText("Resus :");
        jLabel13.setName("jLabel13"); // NOI18N
        jLabel13.setPreferredSize(new java.awt.Dimension(45, 23));
        panelisi1.add(jLabel13);

        CmbCariResus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "(-)", "(+)" }));
        CmbCariResus.setName("CmbCariResus"); // NOI18N
        CmbCariResus.setPreferredSize(new java.awt.Dimension(65, 23));
        CmbCariResus.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                CmbCariResusItemStateChanged(evt);
            }
        });
        CmbCariResus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CmbCariResusKeyPressed(evt);
            }
        });
        panelisi1.add(CmbCariResus);

        label9.setText("Key Word :");
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(61, 23));
        panelisi1.add(label9);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(180, 23));
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

        label22.setName("label22"); // NOI18N
        label22.setPreferredSize(new java.awt.Dimension(15, 23));
        panelisi1.add(label22);

        BtnNota.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Agenda-1-16x16.png"))); // NOI18N
        BtnNota.setMnemonic('S');
        BtnNota.setText("Nota");
        BtnNota.setToolTipText("Alt+S");
        BtnNota.setName("BtnNota"); // NOI18N
        BtnNota.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnNota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnNotaActionPerformed(evt);
            }
        });
        BtnNota.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnNotaKeyPressed(evt);
            }
        });
        panelisi1.add(BtnNota);

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

        jPanel1.add(panelisi1, java.awt.BorderLayout.PAGE_END);

        panelisi5.setName("panelisi5"); // NOI18N
        panelisi5.setPreferredSize(new java.awt.Dimension(100, 44));
        panelisi5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 3, 9));

        label10.setText("Grand Total :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(75, 23));
        panelisi5.add(label10);

        LTotal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LTotal.setText("0");
        LTotal.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        LTotal.setName("LTotal"); // NOI18N
        LTotal.setPreferredSize(new java.awt.Dimension(100, 23));
        panelisi5.add(LTotal);

        jLabel11.setText("PPN(%) :");
        jLabel11.setName("jLabel11"); // NOI18N
        jLabel11.setPreferredSize(new java.awt.Dimension(55, 23));
        panelisi5.add(jLabel11);

        PPN.setText("0");
        PPN.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        PPN.setName("PPN"); // NOI18N
        PPN.setPreferredSize(new java.awt.Dimension(40, 23));
        PPN.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PPNKeyPressed(evt);
            }
        });
        panelisi5.add(PPN);

        BesarPPN.setText("0");
        BesarPPN.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        BesarPPN.setName("BesarPPN"); // NOI18N
        BesarPPN.setPreferredSize(new java.awt.Dimension(100, 23));
        BesarPPN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BesarPPNActionPerformed(evt);
            }
        });
        panelisi5.add(BesarPPN);

        jLabel12.setText("Tagihan + PPN :");
        jLabel12.setName("jLabel12"); // NOI18N
        jLabel12.setPreferredSize(new java.awt.Dimension(95, 23));
        panelisi5.add(jLabel12);

        TagihanPPn.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        TagihanPPn.setText("0");
        TagihanPPn.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        TagihanPPn.setName("TagihanPPn"); // NOI18N
        TagihanPPn.setPreferredSize(new java.awt.Dimension(100, 23));
        panelisi5.add(TagihanPPn);

        label19.setText("Bayar :");
        label19.setName("label19"); // NOI18N
        label19.setPreferredSize(new java.awt.Dimension(42, 23));
        panelisi5.add(label19);

        Bayar.setText("0");
        Bayar.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Bayar.setName("Bayar"); // NOI18N
        Bayar.setPreferredSize(new java.awt.Dimension(120, 23));
        panelisi5.add(Bayar);

        label20.setText("Kembali :");
        label20.setName("label20"); // NOI18N
        label20.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi5.add(label20);

        LKembali.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LKembali.setText("0");
        LKembali.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        LKembali.setName("LKembali"); // NOI18N
        LKembali.setPreferredSize(new java.awt.Dimension(100, 23));
        panelisi5.add(LKembali);

        jPanel1.add(panelisi5, java.awt.BorderLayout.CENTER);

        internalFrame1.add(jPanel1, java.awt.BorderLayout.PAGE_END);

        panelisi6.setBorder(null);
        panelisi6.setName("panelisi6"); // NOI18N
        panelisi6.setPreferredSize(new java.awt.Dimension(450, 77));
        panelisi6.setLayout(new java.awt.GridLayout(2, 0));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(237, 242, 232)), ".: Penggunaan BHP Medis ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50,50,50))); // NOI18N
        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(300, 102));
        jPanel3.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi7.setBorder(null);
        panelisi7.setName("panelisi7"); // NOI18N
        panelisi7.setPreferredSize(new java.awt.Dimension(100, 43));
        panelisi7.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        label13.setText("Key Word :");
        label13.setName("label13"); // NOI18N
        label13.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi7.add(label13);

        TCariMedis.setToolTipText("Alt+C");
        TCariMedis.setName("TCariMedis"); // NOI18N
        TCariMedis.setPreferredSize(new java.awt.Dimension(290, 23));
        TCariMedis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariMedisKeyPressed(evt);
            }
        });
        panelisi7.add(TCariMedis);

        BtnCari2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari2.setMnemonic('1');
        BtnCari2.setToolTipText("Alt+1");
        BtnCari2.setName("BtnCari2"); // NOI18N
        BtnCari2.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCari2ActionPerformed(evt);
            }
        });
        BtnCari2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCari2KeyPressed(evt);
            }
        });
        panelisi7.add(BtnCari2);

        BtnAll1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll1.setMnemonic('2');
        BtnAll1.setToolTipText("Alt+2");
        BtnAll1.setName("BtnAll1"); // NOI18N
        BtnAll1.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnAll1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAll1ActionPerformed(evt);
            }
        });
        BtnAll1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnAll1KeyPressed(evt);
            }
        });
        panelisi7.add(BtnAll1);

        jPanel3.add(panelisi7, java.awt.BorderLayout.PAGE_END);

        Scroll1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(245, 255, 235)));
        Scroll1.setName("Scroll1"); // NOI18N
        Scroll1.setOpaque(true);

        tbMedis.setToolTipText("Klik pada cell Jml untuk memasukkan jumlah penggunaan");
        tbMedis.setName("tbMedis"); // NOI18N
        tbMedis.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbMedisMouseClicked(evt);
            }
        });
        tbMedis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbMedisKeyPressed(evt);
            }
        });
        Scroll1.setViewportView(tbMedis);

        jPanel3.add(Scroll1, java.awt.BorderLayout.CENTER);

        panelisi6.add(jPanel3);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(237, 242, 232)), ".: Penggunaan BHP Non Medis ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50,50,50))); // NOI18N
        jPanel4.setName("jPanel4"); // NOI18N
        jPanel4.setOpaque(false);
        jPanel4.setPreferredSize(new java.awt.Dimension(300, 202));
        jPanel4.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi8.setBorder(null);
        panelisi8.setName("panelisi8"); // NOI18N
        panelisi8.setPreferredSize(new java.awt.Dimension(100, 43));
        panelisi8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        label17.setText("Key Word :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi8.add(label17);

        TCariNonMedis.setToolTipText("Alt+C");
        TCariNonMedis.setName("TCariNonMedis"); // NOI18N
        TCariNonMedis.setPreferredSize(new java.awt.Dimension(290, 23));
        TCariNonMedis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariNonMedisKeyPressed(evt);
            }
        });
        panelisi8.add(TCariNonMedis);

        BtnCari3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari3.setMnemonic('1');
        BtnCari3.setToolTipText("Alt+1");
        BtnCari3.setName("BtnCari3"); // NOI18N
        BtnCari3.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCari3ActionPerformed(evt);
            }
        });
        BtnCari3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCari3KeyPressed(evt);
            }
        });
        panelisi8.add(BtnCari3);

        BtnAll2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll2.setMnemonic('2');
        BtnAll2.setToolTipText("Alt+2");
        BtnAll2.setName("BtnAll2"); // NOI18N
        BtnAll2.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnAll2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAll2ActionPerformed(evt);
            }
        });
        BtnAll2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnAll2KeyPressed(evt);
            }
        });
        panelisi8.add(BtnAll2);

        jPanel4.add(panelisi8, java.awt.BorderLayout.PAGE_END);

        Scroll2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(245, 255, 235)));
        Scroll2.setName("Scroll2"); // NOI18N
        Scroll2.setOpaque(true);

        tbNonMedis.setToolTipText("Klik pada cell Jml untuk memasukkan jumlah penggunaan");
        tbNonMedis.setName("tbNonMedis"); // NOI18N
        tbNonMedis.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbNonMedisMouseClicked(evt);
            }
        });
        tbNonMedis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbNonMedisKeyPressed(evt);
            }
        });
        Scroll2.setViewportView(tbNonMedis);

        jPanel4.add(Scroll2, java.awt.BorderLayout.CENTER);

        panelisi6.add(jPanel4);

        internalFrame1.add(panelisi6, java.awt.BorderLayout.EAST);

        panelisi4.setBorder(null);
        panelisi4.setName("panelisi4"); // NOI18N
        panelisi4.setPreferredSize(new java.awt.Dimension(89, 134));
        panelisi4.setLayout(new java.awt.BorderLayout(1, 1));

        scrollPane1.setComponentPopupMenu(Popup);
        scrollPane1.setName("scrollPane1"); // NOI18N
        scrollPane1.setOpaque(true);

        tbDarah.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbDarah.setComponentPopupMenu(Popup);
        tbDarah.setName("tbDarah"); // NOI18N
        tbDarah.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDarahMouseClicked(evt);
            }
        });
        tbDarah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbDarahKeyPressed(evt);
            }
        });
        scrollPane1.setViewportView(tbDarah);

        panelisi4.add(scrollPane1, java.awt.BorderLayout.CENTER);

        scrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        scrollPane2.setComponentPopupMenu(Popup);
        scrollPane2.setName("scrollPane2"); // NOI18N
        scrollPane2.setOpaque(true);
        scrollPane2.setPreferredSize(new java.awt.Dimension(1093, 138));

        panelisi3.setBorder(null);
        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(89, 134));
        panelisi3.setLayout(null);

        label15.setText("No.Penyerahan :");
        label15.setName("label15"); // NOI18N
        label15.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(label15);
        label15.setBounds(0, 10, 96, 23);

        nopenyerahan.setName("nopenyerahan"); // NOI18N
        nopenyerahan.setPreferredSize(new java.awt.Dimension(207, 23));
        nopenyerahan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nopenyerahanKeyPressed(evt);
            }
        });
        panelisi3.add(nopenyerahan);
        nopenyerahan.setBounds(99, 10, 160, 23);

        label14.setText("Petugas P.J. :");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label14);
        label14.setBounds(366, 40, 90, 23);

        kdptgcross.setName("kdptgcross"); // NOI18N
        kdptgcross.setPreferredSize(new java.awt.Dimension(80, 23));
        kdptgcross.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdptgcrossKeyPressed(evt);
            }
        });
        panelisi3.add(kdptgcross);
        kdptgcross.setBounds(459, 10, 100, 23);

        kdptgpj.setName("kdptgpj"); // NOI18N
        kdptgpj.setPreferredSize(new java.awt.Dimension(80, 23));
        kdptgpj.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdptgpjKeyPressed(evt);
            }
        });
        panelisi3.add(kdptgpj);
        kdptgpj.setBounds(459, 40, 100, 23);

        label16.setText("Petugas Cross :");
        label16.setName("label16"); // NOI18N
        label16.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(label16);
        label16.setBounds(366, 10, 90, 23);

        nmptgcross.setName("nmptgcross"); // NOI18N
        nmptgcross.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(nmptgcross);
        nmptgcross.setBounds(560, 10, 200, 23);

        nmptgpj.setEditable(false);
        nmptgpj.setName("nmptgpj"); // NOI18N
        nmptgpj.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(nmptgpj);
        nmptgpj.setBounds(560, 40, 200, 23);

        btnPtgCross.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPtgCross.setMnemonic('1');
        btnPtgCross.setToolTipText("Alt+1");
        btnPtgCross.setName("btnPtgCross"); // NOI18N
        btnPtgCross.setPreferredSize(new java.awt.Dimension(28, 23));
        btnPtgCross.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPtgCrossActionPerformed(evt);
            }
        });
        panelisi3.add(btnPtgCross);
        btnPtgCross.setBounds(764, 10, 28, 23);

        btnPtgPJ.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPtgPJ.setMnemonic('2');
        btnPtgPJ.setToolTipText("Alt+2");
        btnPtgPJ.setName("btnPtgPJ"); // NOI18N
        btnPtgPJ.setPreferredSize(new java.awt.Dimension(28, 23));
        btnPtgPJ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPtgPJActionPerformed(evt);
            }
        });
        panelisi3.add(btnPtgPJ);
        btnPtgPJ.setBounds(764, 40, 28, 23);

        dinas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Pagi", "Siang", "Sore", "Malam" }));
        dinas.setName("dinas"); // NOI18N
        dinas.setPreferredSize(new java.awt.Dimension(40, 23));
        dinas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                dinasKeyPressed(evt);
            }
        });
        panelisi3.add(dinas);
        dinas.setBounds(239, 70, 90, 23);

        label18.setText("Keterangan :");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(label18);
        label18.setBounds(0, 40, 96, 23);

        keterangan.setName("keterangan"); // NOI18N
        keterangan.setPreferredSize(new java.awt.Dimension(207, 23));
        keterangan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                keteranganKeyPressed(evt);
            }
        });
        panelisi3.add(keterangan);
        keterangan.setBounds(99, 40, 230, 23);

        label12.setText("Dinas :");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label12);
        label12.setBounds(191, 70, 45, 23);

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label11);
        label11.setBounds(0, 70, 96, 23);

        tanggal.setDisplayFormat("dd-MM-yyyy");
        tanggal.setName("tanggal"); // NOI18N
        tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tanggalKeyPressed(evt);
            }
        });
        panelisi3.add(tanggal);
        tanggal.setBounds(99, 70, 95, 23);

        label21.setText("Pengambil Darah :");
        label21.setName("label21"); // NOI18N
        label21.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label21);
        label21.setBounds(366, 70, 148, 23);

        nmpengambil.setName("nmpengambil"); // NOI18N
        nmpengambil.setPreferredSize(new java.awt.Dimension(207, 23));
        nmpengambil.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nmpengambilKeyPressed(evt);
            }
        });
        panelisi3.add(nmpengambil);
        nmpengambil.setBounds(517, 70, 275, 23);

        label23.setText("Alamat Pengambil Darah :");
        label23.setName("label23"); // NOI18N
        label23.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label23);
        label23.setBounds(366, 100, 148, 23);

        alamatpengambil.setName("alamatpengambil"); // NOI18N
        alamatpengambil.setPreferredSize(new java.awt.Dimension(207, 23));
        alamatpengambil.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                alamatpengambilKeyPressed(evt);
            }
        });
        panelisi3.add(alamatpengambil);
        alamatpengambil.setBounds(517, 100, 275, 23);

        jLabel10.setText("Akun Bayar :");
        jLabel10.setName("jLabel10"); // NOI18N
        panelisi3.add(jLabel10);
        jLabel10.setBounds(0, 100, 96, 23);

        CmbAkun.setName("CmbAkun"); // NOI18N
        CmbAkun.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CmbAkunKeyPressed(evt);
            }
        });
        panelisi3.add(CmbAkun);
        CmbAkun.setBounds(99, 100, 230, 23);

        scrollPane2.setViewportView(panelisi3);

        panelisi4.add(scrollPane2, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(panelisi4, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tbDarahMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDarahMouseClicked
        if(tabMode.getRowCount()!=0){
            if(tbDarah.getSelectedColumn()==0){
                try {                  
                    getData();
                } catch (Exception e) {
                }
            }                
        }
}//GEN-LAST:event_tbDarahMouseClicked

    private void tbDarahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbDarahKeyPressed
        if(tabMode.getRowCount()!=0){
            if(evt.getKeyCode()==KeyEvent.VK_ENTER){
                try {                                     
                    getData();                     
                    TCari.setText("");
                    TCari.requestFocus();
                } catch (java.lang.NullPointerException e) {
                }
            }else if((evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)||(evt.getKeyCode()==KeyEvent.VK_RIGHT)){
                try {                                     
                    getData();           
                } catch (java.lang.NullPointerException e) {
                }
            }else if(evt.getKeyCode()==KeyEvent.VK_DELETE){
                try {
                    if(tbDarah.getSelectedColumn()==0){
                        tbDarah.setValueAt("", tbDarah.getSelectedRow(),0);
                    }else if(tbDarah.getSelectedColumn()==5){
                        tbDarah.setValueAt("", tbDarah.getSelectedRow(),5);
                    }else if(tbDarah.getSelectedColumn()==7){
                        tbDarah.setValueAt("", tbDarah.getSelectedRow(),7);
                    }else if(tbDarah.getSelectedColumn()==8){
                        tbDarah.setValueAt("", tbDarah.getSelectedRow(),8);
                    }else if(tbDarah.getSelectedColumn()==9){
                        tbDarah.setValueAt("", tbDarah.getSelectedRow(),9);
                    }
                } catch (Exception e) {
                } 
            }else if(evt.getKeyCode()==KeyEvent.VK_SHIFT){
                TCari.setText("");
                TCari.requestFocus();
            }else if(evt.getKeyCode()==KeyEvent.VK_BACK_SPACE){
                try {
                    if(tbDarah.getSelectedColumn()==0){
                        if(tbDarah.getValueAt(tbDarah.getSelectedRow(),0).toString().equals("0")||tbDarah.getValueAt(tbDarah.getSelectedRow(),0).toString().equals("0.0")||tbDarah.getValueAt(tbDarah.getSelectedRow(),0).toString().equals("0,0")){
                            tbDarah.setValueAt("", tbDarah.getSelectedRow(),0);
                        }
                    }else if(tbDarah.getSelectedColumn()==5){
                        if(tbDarah.getValueAt(tbDarah.getSelectedRow(),5).toString().equals("0")||tbDarah.getValueAt(tbDarah.getSelectedRow(),5).toString().equals("0.0")||tbDarah.getValueAt(tbDarah.getSelectedRow(),5).toString().equals("0,0")){
                            tbDarah.setValueAt("", tbDarah.getSelectedRow(),5);
                        }
                    }else if(tbDarah.getSelectedColumn()==7){
                        if(tbDarah.getValueAt(tbDarah.getSelectedRow(),7).toString().equals("0")||tbDarah.getValueAt(tbDarah.getSelectedRow(),7).toString().equals("0.0")||tbDarah.getValueAt(tbDarah.getSelectedRow(),7).toString().equals("0,0")){
                            tbDarah.setValueAt("", tbDarah.getSelectedRow(),7);
                        }
                    }else if(tbDarah.getSelectedColumn()==8){
                        if(tbDarah.getValueAt(tbDarah.getSelectedRow(),8).toString().equals("0")||tbDarah.getValueAt(tbDarah.getSelectedRow(),8).toString().equals("0.0")||tbDarah.getValueAt(tbDarah.getSelectedRow(),8).toString().equals("0,0")){
                            tbDarah.setValueAt("", tbDarah.getSelectedRow(),8);
                        }
                    }else if(tbDarah.getSelectedColumn()==9){
                        if(tbDarah.getValueAt(tbDarah.getSelectedRow(),9).toString().equals("0")||tbDarah.getValueAt(tbDarah.getSelectedRow(),9).toString().equals("0.0")||tbDarah.getValueAt(tbDarah.getSelectedRow(),9).toString().equals("0,0")){
                            tbDarah.setValueAt("", tbDarah.getSelectedRow(),9);
                        }
                    }
                } catch (Exception e) {
                }       
            }
        }
}//GEN-LAST:event_tbDarahKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        carijual.emptTeks();      
        carijual.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        carijual.setLocationRelativeTo(internalFrame1);
        carijual.setAlwaysOnTop(false);
        carijual.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnCariActionPerformed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
            dispose();  
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){            
            dispose();              
        }else{Valid.pindah(evt,BtnCari,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed
/*
private void KdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdKeyPressed
    Valid.pindah(evt,BtnCari,Nm);
}//GEN-LAST:event_TKdKeyPressed
*/

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(nopenyerahan.getText().trim().equals("")){
            Valid.textKosong(nopenyerahan,"No.Penyerahan");
        }else if(keterangan.getText().trim().equals("")){
            Valid.textKosong(keterangan,"Keterangan");
        }else if(CmbAkun.getSelectedItem().toString().trim().equals("")){
            Valid.textKosong(CmbAkun,"Akun Bayar");
        }else if(kdptgcross.getText().trim().equals("")||nmptgcross.getText().trim().equals("")){
            Valid.textKosong(kdptgcross,"Petugas Cross");
        }else if(kdptgpj.getText().trim().equals("")||nmptgcross.getText().trim().equals("")){
            Valid.textKosong(kdptgpj,"Petugas P.J.");
        }else if(nmpengambil.getText().trim().equals("")){
            Valid.textKosong(nmpengambil,"Pengambil Darah");
        }else if(alamatpengambil.getText().trim().equals("")){
            Valid.textKosong(alamatpengambil,"Alamat Pengambil Darah");
        }else if(ttl<=0){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan masukkan data penyerahan darah...!!!!");
            TCari.requestFocus();
        }else{
            int reply = JOptionPane.showConfirmDialog(rootPane,"Eeiiiiiits, udah bener belum data yang mau disimpan..??","Konfirmasi",JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
                if(verifikasi_penyerahan_darah_di_kasir.equals("No")){
                    status="Sudah Dibayar";
                }else{
                    status="Belum Dibayar";
                }
                Sequel.AutoComitFalse();
                sukses=true; 
                if(Sequel.menyimpantf("utd_penyerahan_darah","?,?,?,?,?,?,?,?,?,?,?","No.Penyerahan", 11,new String[]{
                    nopenyerahan.getText(),Valid.SetTgl(tanggal.getSelectedItem()+""),dinas.getSelectedItem().toString(),
                    kdptgcross.getText(),keterangan.getText(),status,Sequel.cariIsi("select kd_rek from akun_bayar where nama_bayar=?",CmbAkun.getSelectedItem().toString()),
                    nmpengambil.getText(),alamatpengambil.getText(),kdptgpj.getText(),""+besarppn
                   })==true){
                    
                    //simpan darah
                    for(i=0;i<tbDarah.getRowCount();i++){ 
                        if(tbDarah.getValueAt(i,0).toString().equals("true")){
                            if(Sequel.menyimpantf("utd_penyerahan_darah_detail","?,?,?,?,?,?,?","No.Kantung",7,new String[]{
                                nopenyerahan.getText(),tbDarah.getValueAt(i,1).toString(),tbDarah.getValueAt(i,9).toString(),
                                tbDarah.getValueAt(i,10).toString(),tbDarah.getValueAt(i,11).toString(),
                                tbDarah.getValueAt(i,12).toString(),tbDarah.getValueAt(i,13).toString()
                               })==false){
                                tbDarah.setValueAt(false,i,0); 
                                getData();
                            }else{
                                Sequel.mengedit("utd_stok_darah","no_kantong='"+tbDarah.getValueAt(i,1).toString()+"'","status='Diambil'");
                                tbDarah.setValueAt(false,i,0); 
                            }
                        }
                    }
                    //simpan bhp medis
                    for(i=0;i<tbMedis.getRowCount();i++){  
                        try {
                            if(Valid.SetAngka(tbMedis.getValueAt(i,0).toString())>0){
                                if(Sequel.menyimpantf2("utd_penggunaan_medis_penyerahan_darah","?,?,?,?,?","BHP Medis",5,new String[]{
                                    nopenyerahan.getText(),tbMedis.getValueAt(i,1).toString(),tbMedis.getValueAt(i,0).toString(),tbMedis.getValueAt(i,3).toString(),
                                    Double.toString(Double.parseDouble(tbMedis.getValueAt(i,0).toString())*Double.parseDouble(tbMedis.getValueAt(i,3).toString()))
                                  })==true){
                                    if(verifikasi_penyerahan_darah_di_kasir.equals("No")){
                                        Sequel.menyimpan("utd_stok_medis","'"+tbMedis.getValueAt(i,1).toString()+"','-"+tbMedis.getValueAt(i,0).toString()+"','"+tbMedis.getValueAt(i,3).toString()+"'", 
                                            "stok=stok-"+tbMedis.getValueAt(i,0).toString()+"","kode_brng='"+tbMedis.getValueAt(i,1).toString()+"'");
                                    }                                        
                                    tbMedis.setValueAt("",i,0);        
                                    tbMedis.setValueAt(0,i,4);
                                }else{
                                    sukses=false;
                                }   
                            }                                
                        } catch (Exception e) {
                        }                    
                    }
                    //simpan bhp non medis
                    for(i=0;i<tbNonMedis.getRowCount();i++){  
                        try {
                            if(Valid.SetAngka(tbNonMedis.getValueAt(i,0).toString())>0){
                                if(Sequel.menyimpantf2("utd_penggunaan_penunjang_penyerahan_darah","?,?,?,?,?","BHP Non Medis",5,new String[]{
                                     nopenyerahan.getText(),tbNonMedis.getValueAt(i,1).toString(),tbNonMedis.getValueAt(i,0).toString(),tbNonMedis.getValueAt(i,3).toString(),
                                     Double.toString(Double.parseDouble(tbNonMedis.getValueAt(i,0).toString())*Double.parseDouble(tbNonMedis.getValueAt(i,3).toString()))
                                 })==true){
                                    if(verifikasi_penyerahan_darah_di_kasir.equals("No")){
                                        Sequel.menyimpan("utd_stok_penunjang","'"+tbNonMedis.getValueAt(i,1).toString()+"','-"+tbNonMedis.getValueAt(i,0).toString()+"','"+tbNonMedis.getValueAt(i,3).toString()+"'", 
                                             "stok=stok-"+tbNonMedis.getValueAt(i,0).toString()+"","kode_brng='"+tbNonMedis.getValueAt(i,1).toString()+"'");
                                    }                                    
                                    tbNonMedis.setValueAt("",i,0);        
                                    tbNonMedis.setValueAt(0,i,4);
                                }else{
                                    sukses=false;
                                }   
                            }                                
                        } catch (Exception e) {
                        }                    
                    }

                    if(sukses==true){
                        if(verifikasi_penyerahan_darah_di_kasir.equals("No")){
                            Sequel.queryu("delete from tampjurnal");                    
                            Sequel.menyimpan("tampjurnal","'"+Sequel.cariIsi("select Penyerahan_Darah from set_akun")+"','PENJUALAN DARAH UTD','0','"+ttl+"'","Rekening");    
                            Sequel.menyimpan("tampjurnal","'"+Sequel.cariIsi("select kd_rek from akun_bayar where nama_bayar=?",CmbAkun.getSelectedItem().toString())+"','CARA BAYAR','"+ttl+"','0'","Rekening"); 
                            sukses=jur.simpanJurnal(nopenyerahan.getText(),Valid.SetTgl(tanggal.getSelectedItem()+""),"U","PENJUALAN DARAH DI UTD"+", OLEH "+akses.getkode());                                                
                            if(sukses==true){
                                Sequel.menyimpan("tagihan_sadewa","'"+nopenyerahan.getText()+"','-','"+nmpengambil.getText().replaceAll("'","")+"','-',concat('"+Valid.SetTgl(tanggal.getSelectedItem()+"")+
                                        "',' ',CURTIME()),'Pelunasan','"+ttl+"','"+ttl+"','Sudah','"+akses.getkode()+"'","No.Nota");
                            }
                        }
                    }
                        
                }else{
                    sukses=false;
                }
                
                if(sukses==true){
                    Sequel.Commit();
                    tampil();
                    tampilMedis();
                    tampilNonMedis();
                    emptTeks();
                }else{
                    JOptionPane.showMessageDialog(null,"Terjadi kesalahan saat pemrosesan data, transaksi dibatalkan.\nPeriksa kembali data sebelum melanjutkan menyimpan..!!");
                    Sequel.RollBack();
                }

                Sequel.AutoComitTrue();
            }
        }
    }//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
           Valid.pindah(evt,alamatpengambil,BtnCari);
        }
    }//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            tampil();
        }else{
            Valid.pindah(evt, BtnSimpan, BtnKeluar);
        }
    }//GEN-LAST:event_BtnCariKeyPressed

    private void BtnNotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnNotaActionPerformed
        if(nopenyerahan.getText().trim().equals("")){
            Valid.textKosong(nopenyerahan,"No.Penyerahan");
        }else if(keterangan.getText().trim().equals("")){
            Valid.textKosong(keterangan,"Keterangan");
        }else if(CmbAkun.getSelectedItem().toString().trim().equals("")){
            Valid.textKosong(CmbAkun,"Akun Bayar");
        }else if(kdptgcross.getText().trim().equals("")||nmptgcross.getText().trim().equals("")){
            Valid.textKosong(kdptgcross,"Petugas Cross");
        }else if(kdptgpj.getText().trim().equals("")||nmptgcross.getText().trim().equals("")){
            Valid.textKosong(kdptgpj,"Petugas P.J.");
        }else if(nmpengambil.getText().trim().equals("")){
            Valid.textKosong(nmpengambil,"Pengambil Darah");
        }else if(alamatpengambil.getText().trim().equals("")){
            Valid.textKosong(alamatpengambil,"Alamat Pengambil Darah");
        }else if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            tbDarah.requestFocus();
        }else if(ttl<=0){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan masukkan penjualan...!!!!");
            tbDarah.requestFocus();
        }else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));        
            
            Sequel.queryu("truncate table temporary");
            row=tabMode.getRowCount();
            for(i=0;i<row;i++){  
                try {
                    if(tbDarah.getValueAt(i,0).toString().equals("true")){
                           Sequel.menyimpan("temporary","'0','"+
                                   tabMode.getValueAt(i,1).toString()+"','"+
                                   tabMode.getValueAt(i,2).toString()+"','"+
                                   tabMode.getValueAt(i,3).toString()+"','"+
                                   tabMode.getValueAt(i,4).toString()+"','"+
                                   tabMode.getValueAt(i,13).toString()+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Transaksi Penjualan Darah"); 
                    }
                } catch (Exception e) {
                }                
            }
            
            Valid.panggilUrl("billing/NotaDarah.php?nopenyerahan="+nopenyerahan.getText()+"&besarppn="+besarppn+"&bayar="+Bayar.getText()+"&tanggal="+Valid.SetTgl(tanggal.getSelectedItem()+"")+"&catatan="+keterangan.getText().replaceAll(" ","_")+"&petugaspj="+nmptgpj.getText().replaceAll(" ","_")+"&pasien="+nmpengambil.getText().replaceAll(" ","_"));
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnNotaActionPerformed

    private void BtnNotaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnNotaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnNotaKeyPressed

private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
       if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            tampil();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari1.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            tbDarah.requestFocus();
        }
}//GEN-LAST:event_TCariKeyPressed

private void BtnCari1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCari1ActionPerformed
        tampil();
}//GEN-LAST:event_BtnCari1ActionPerformed

private void BtnCari1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCari1KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            tampil();
        }else{
            Valid.pindah(evt, TCari, Bayar);
        }
}//GEN-LAST:event_BtnCari1KeyPressed

private void nopenyerahanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nopenyerahanKeyPressed
        Valid.pindah(evt,TCari,keterangan);
}//GEN-LAST:event_nopenyerahanKeyPressed

private void kdptgcrossKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdptgcrossKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama from petugas where nip=?",nmptgcross,kdptgcross.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnPtgCrossActionPerformed(null);
        }else{
            Valid.pindah(evt,CmbAkun,kdptgpj);
        }
}//GEN-LAST:event_kdptgcrossKeyPressed

private void kdptgpjKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdptgpjKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama from petugas where nip=?",nmptgpj,kdptgpj.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnPtgPJActionPerformed(null);
        }else{
            Valid.pindah(evt,kdptgcross,nmpengambil);
        }
}//GEN-LAST:event_kdptgpjKeyPressed

private void btnPtgCrossActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPtgCrossActionPerformed
        pilih=1;
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setAlwaysOnTop(false);
        petugas.setVisible(true);
}//GEN-LAST:event_btnPtgCrossActionPerformed

private void btnPtgPJActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPtgPJActionPerformed
        pilih=2;
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setAlwaysOnTop(false);
        petugas.setVisible(true);
}//GEN-LAST:event_btnPtgPJActionPerformed

private void dinasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dinasKeyPressed
        Valid.pindah(evt, tanggal,CmbAkun);
}//GEN-LAST:event_dinasKeyPressed

private void keteranganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_keteranganKeyPressed
        Valid.pindah(evt, nopenyerahan,tanggal);
}//GEN-LAST:event_keteranganKeyPressed

private void tanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tanggalKeyPressed
        Valid.pindah(evt,keterangan,dinas);
}//GEN-LAST:event_tanggalKeyPressed

private void ppBersihkanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBersihkanActionPerformed
            int row2=tabMode.getRowCount();
            for(int r=0;r<row2;r++){ 
                tabMode.setValueAt("",r,0);
                tabMode.setValueAt(0,r,6);
                tabMode.setValueAt(0,r,7);
                tabMode.setValueAt(0,r,8);
                tabMode.setValueAt(0,r,9);
                tabMode.setValueAt(0,r,10);
                tabMode.setValueAt(0,r,11);
            }
}//GEN-LAST:event_ppBersihkanActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        emptTeks();
        keterangan.requestFocus();
        tampil();
        tampilMedis();
        tampilNonMedis();
    }//GEN-LAST:event_formWindowOpened

    private void BtnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        UTDStokDarah barang=new UTDStokDarah(null,false);
        barang.emptTeks();
        barang.isCek();
        barang.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        barang.setLocationRelativeTo(internalFrame1);
        barang.setAlwaysOnTop(false);
        barang.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnTambahActionPerformed

    private void ppStokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppStokActionPerformed
        for(i=0;i<tbDarah.getRowCount();i++){
            try {
                stokbarang=0;    
                psstok=koneksi.prepareStatement("select ifnull(stok,'0') from gudangbarang where kd_bangsal=? and kode_brng=?");
                try {
                    psstok.setString(2,tbDarah.getValueAt(i,1).toString());
                    rsstok=psstok.executeQuery();
                    if(rsstok.next()){
                        stokbarang=rsstok.getDouble(1);
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
                tbDarah.setValueAt(stokbarang,i,11);
            } catch (Exception e) {
                tbDarah.setValueAt(0,i,10);
            }
        }
    }//GEN-LAST:event_ppStokActionPerformed

    private void CmbAkunKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CmbAkunKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            PPN.setText(Sequel.cariIsi("select ppn from akun_bayar where nama_bayar=?",CmbAkun.getSelectedItem().toString()));
            isKembali();
            kdptgcross.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            dinas.requestFocus();
        }
    }//GEN-LAST:event_CmbAkunKeyPressed

    private void TCariMedisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariMedisKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCari2ActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            TCariNonMedis.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
           // KodePetugasUSaring.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            tbMedis.requestFocus();
        }
    }//GEN-LAST:event_TCariMedisKeyPressed

    private void BtnCari2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCari2ActionPerformed
        tampilMedis();
    }//GEN-LAST:event_BtnCari2ActionPerformed

    private void BtnCari2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCari2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnCari2KeyPressed

    private void BtnAll1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAll1ActionPerformed
        TCariMedis.setText("");
        tampilMedis();
    }//GEN-LAST:event_BtnAll1ActionPerformed

    private void BtnAll1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAll1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnAll1KeyPressed

    private void tbMedisMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbMedisMouseClicked

    }//GEN-LAST:event_tbMedisMouseClicked

    private void tbMedisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbMedisKeyPressed
        if(tbMedis.getRowCount()!=0){
            if(evt.getKeyCode()==KeyEvent.VK_ENTER){
                try {
                    i=tbMedis.getSelectedColumn();
                    if(i==1){
                        TCariMedis.setText("");
                        TCariMedis.requestFocus();
                    }
                } catch (java.lang.NullPointerException e) {
                }
            }else if(evt.getKeyCode()==KeyEvent.VK_SHIFT){
                TCariMedis.setText("");
                TCariMedis.requestFocus();
            }
        }
    }//GEN-LAST:event_tbMedisKeyPressed

    private void TCariNonMedisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariNonMedisKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCari3ActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnSimpan.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            TCariMedis.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            tbNonMedis.requestFocus();
        }
    }//GEN-LAST:event_TCariNonMedisKeyPressed

    private void BtnCari3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCari3ActionPerformed
        tampilNonMedis();
    }//GEN-LAST:event_BtnCari3ActionPerformed

    private void BtnCari3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCari3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnCari3KeyPressed

    private void BtnAll2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAll2ActionPerformed
        TCariNonMedis.setText("");
        tampilNonMedis();
    }//GEN-LAST:event_BtnAll2ActionPerformed

    private void BtnAll2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAll2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnAll2KeyPressed

    private void tbNonMedisMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbNonMedisMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbNonMedisMouseClicked

    private void tbNonMedisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbNonMedisKeyPressed
        if(tbNonMedis.getRowCount()!=0){
            if(evt.getKeyCode()==KeyEvent.VK_ENTER){
                try {
                    i=tbNonMedis.getSelectedColumn();
                    if(i==1){
                        TCariNonMedis.setText("");
                        TCariNonMedis.requestFocus();
                    }
                } catch (java.lang.NullPointerException e) {
                }
            }else if(evt.getKeyCode()==KeyEvent.VK_SHIFT){
                TCariNonMedis.setText("");
                TCariNonMedis.requestFocus();
            }
        }
    }//GEN-LAST:event_tbNonMedisKeyPressed

    private void PPNKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PPNKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            isKembali();
            Bayar.requestFocus();
        }
    }//GEN-LAST:event_PPNKeyPressed

    private void BesarPPNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BesarPPNActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BesarPPNActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        if(this.getWidth()<820){
            scrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
            scrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            panelisi3.setPreferredSize(new Dimension(800,137));
        }else{
            scrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            scrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        }
    }//GEN-LAST:event_formWindowActivated

    private void CmbCariResusItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_CmbCariResusItemStateChanged
        tampil();
    }//GEN-LAST:event_CmbCariResusItemStateChanged

    private void CmbCariResusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CmbCariResusKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_CmbCariResusKeyPressed

    private void CmbCariGdItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_CmbCariGdItemStateChanged
        tampil();
    }//GEN-LAST:event_CmbCariGdItemStateChanged

    private void CmbCariGdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CmbCariGdKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_CmbCariGdKeyPressed

    private void nmpengambilKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nmpengambilKeyPressed
        Valid.pindah(evt,kdptgpj,alamatpengambil);
    }//GEN-LAST:event_nmpengambilKeyPressed

    private void alamatpengambilKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_alamatpengambilKeyPressed
        Valid.pindah(evt,nmpengambil,BtnSimpan);
    }//GEN-LAST:event_alamatpengambilKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            UTDPenyerahanDarah dialog = new UTDPenyerahanDarah(new javax.swing.JFrame(), true);
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
    private widget.TextBox Bayar;
    private widget.TextBox BesarPPN;
    private widget.Button BtnAll1;
    private widget.Button BtnAll2;
    private widget.Button BtnCari;
    private widget.Button BtnCari1;
    private widget.Button BtnCari2;
    private widget.Button BtnCari3;
    private widget.Button BtnKeluar;
    private widget.Button BtnNota;
    private widget.Button BtnSimpan;
    private widget.Button BtnTambah;
    private widget.ComboBox CmbAkun;
    private widget.ComboBox CmbCariGd;
    private widget.ComboBox CmbCariResus;
    private widget.TextBox Kd2;
    private widget.Label LKembali;
    private widget.Label LTotal;
    private widget.TextBox PPN;
    private javax.swing.JPopupMenu Popup;
    private widget.ScrollPane Scroll1;
    private widget.ScrollPane Scroll2;
    private widget.TextBox TCari;
    private widget.TextBox TCariMedis;
    private widget.TextBox TCariNonMedis;
    private widget.Label TagihanPPn;
    private widget.TextBox alamatpengambil;
    private widget.Button btnPtgCross;
    private widget.Button btnPtgPJ;
    private widget.ComboBox dinas;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel12;
    private widget.Label jLabel13;
    private widget.Label jLabel17;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private widget.TextBox kdptgcross;
    private widget.TextBox kdptgpj;
    private widget.TextBox keterangan;
    private widget.Label label10;
    private widget.Label label11;
    private widget.Label label12;
    private widget.Label label13;
    private widget.Label label14;
    private widget.Label label15;
    private widget.Label label16;
    private widget.Label label17;
    private widget.Label label18;
    private widget.Label label19;
    private widget.Label label20;
    private widget.Label label21;
    private widget.Label label22;
    private widget.Label label23;
    private widget.Label label9;
    private widget.TextBox nmpengambil;
    private widget.TextBox nmptgcross;
    private widget.TextBox nmptgpj;
    private widget.TextBox nopenyerahan;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi3;
    private widget.panelisi panelisi4;
    private widget.panelisi panelisi5;
    private widget.panelisi panelisi6;
    private widget.panelisi panelisi7;
    private widget.panelisi panelisi8;
    private javax.swing.JMenuItem ppBersihkan;
    private javax.swing.JMenuItem ppStok;
    private widget.ScrollPane scrollPane1;
    private widget.ScrollPane scrollPane2;
    private widget.Tanggal tanggal;
    private widget.Table tbDarah;
    private widget.Table tbMedis;
    private widget.Table tbNonMedis;
    // End of variables declaration//GEN-END:variables

    private void tampil() {        
        try {
            jml=0;
            for(i=0;i<tbDarah.getRowCount();i++){
                if(tbDarah.getValueAt(i,0).toString().equals("true")){
                    jml++;
                }
            }

            pilihan=null;
            nokantung=null; 
            komponen=null;
            gd=null;
            resus=null;
            aftap=null;
            kadaluarsa=null;
            asaldarah=null;
            satatus=null;
            js=null;
            bhp=null;
            kso=null;
            menejemen=null;
            biaya=null;
            
            pilihan=new boolean[jml];
            nokantung=new String[jml];
            komponen=new String[jml];
            gd=new String[jml];
            resus=new String[jml];
            aftap=new String[jml];
            kadaluarsa=new String[jml];
            asaldarah=new String[jml];
            satatus=new String[jml];
            js=new String[jml];
            bhp=new String[jml];
            kso=new String[jml];
            menejemen=new String[jml];
            biaya=new double[jml];
            
            index=0;        
            for(i=0;i<tbDarah.getRowCount();i++){
                if(tbDarah.getValueAt(i,0).toString().equals("true")){
                    pilihan[index]=true;                            
                    nokantung[index]=tbDarah.getValueAt(i,1).toString();
                    komponen[index]=tbDarah.getValueAt(i,2).toString();
                    gd[index]=tbDarah.getValueAt(i,3).toString();
                    resus[index]=tbDarah.getValueAt(i,4).toString();
                    aftap[index]=tbDarah.getValueAt(i,5).toString();
                    kadaluarsa[index]=tbDarah.getValueAt(i,6).toString();
                    asaldarah[index]=tbDarah.getValueAt(i,7).toString();
                    satatus[index]=tbDarah.getValueAt(i,8).toString();
                    js[index]=tbDarah.getValueAt(i,9).toString();
                    bhp[index]=tbDarah.getValueAt(i,10).toString();
                    kso[index]=tbDarah.getValueAt(i,11).toString();
                    menejemen[index]=tbDarah.getValueAt(i,12).toString();
                    biaya[index]=Double.parseDouble(tbDarah.getValueAt(i,13).toString());
                    index++;
                }
            } 
            
            Valid.tabelKosong(tabMode);
            
            for(i=0;i<jml;i++){
                tabMode.addRow(new Object[] {
                    pilihan[i],nokantung[i],komponen[i],gd[i],resus[i],
                    aftap[i],kadaluarsa[i],asaldarah[i],satatus[i],js[i],
                    bhp[i],kso[i],menejemen[i],biaya[i]
                });
            }
            
            psdarah=koneksi.prepareStatement(
                    "select utd_stok_darah.no_kantong,utd_komponen_darah.nama as darah,"+
                    "utd_stok_darah.golongan_darah,utd_stok_darah.resus,"+
                    "utd_stok_darah.tanggal_aftap,utd_stok_darah.tanggal_kadaluarsa,"+
                    "utd_stok_darah.asal_darah,utd_stok_darah.status,"+
                    "utd_komponen_darah.jasa_sarana,utd_komponen_darah.paket_bhp,"+
                    "utd_komponen_darah.kso,utd_komponen_darah.manajemen,"+
                    "utd_komponen_darah.total "+
                    "from utd_komponen_darah inner join utd_stok_darah "+
                    "on utd_stok_darah.kode_komponen=utd_komponen_darah.kode where "+
                    "utd_stok_darah.status='Ada' and utd_stok_darah.golongan_darah=? and utd_stok_darah.resus=? and utd_stok_darah.no_kantong like ? or "+
                    "utd_stok_darah.status='Ada' and utd_stok_darah.golongan_darah=? and utd_stok_darah.resus=? and utd_komponen_darah.nama like ? or "+
                    "utd_stok_darah.status='Ada' and utd_stok_darah.golongan_darah=? and utd_stok_darah.resus=? and utd_stok_darah.asal_darah like ? "+
                    "order by utd_stok_darah.tanggal_kadaluarsa ");
            try {
                psdarah.setString(1,CmbCariGd.getSelectedItem().toString());
                psdarah.setString(2,CmbCariResus.getSelectedItem().toString());
                psdarah.setString(3,"%"+TCari.getText().trim()+"%");
                psdarah.setString(4,CmbCariGd.getSelectedItem().toString());
                psdarah.setString(5,CmbCariResus.getSelectedItem().toString());
                psdarah.setString(6,"%"+TCari.getText().trim()+"%");
                psdarah.setString(7,CmbCariGd.getSelectedItem().toString());
                psdarah.setString(8,CmbCariResus.getSelectedItem().toString());
                psdarah.setString(9,"%"+TCari.getText().trim()+"%");
                rsdarah=psdarah.executeQuery();
                while(rsdarah.next()){
                    tabMode.addRow(new Object[]{
                        false,rsdarah.getString(1),rsdarah.getString(2),rsdarah.getString(3),
                        rsdarah.getString(4),rsdarah.getString(5),rsdarah.getString(6),
                        rsdarah.getString(7),rsdarah.getString(8),rsdarah.getDouble(9),
                        rsdarah.getDouble(10),rsdarah.getDouble(11),rsdarah.getDouble(12),
                        rsdarah.getDouble(13)
                    });
                }
            } catch (Exception e) {
                System.out.println("Notifikasi Darah : "+e);
            } finally{
                if(rsdarah!=null){
                    rsdarah.close();
                }
                if(psdarah!=null){
                    psdarah.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi Darah : "+e);
        }
    }
    
    private void tampilMedis() {
        row=tbMedis.getRowCount();
        jml=0;
        for(i=0;i<row;i++){
            try {
                if(Double.parseDouble(tbMedis.getValueAt(i,0).toString())>0){
                    jml++;
                }
            } catch (Exception e) {
                jml=jml+0;
            } 
        }
        
        kodebarang=null;
        namabarang=null;
        satuan=null;
        hbeli=null;
        total=null;
        jumlah=null;
        stokasal=null;
        
        kodebarang=new String[jml];
        namabarang=new String[jml];
        satuan=new String[jml];
        hbeli=new String[jml];
        total=new String[jml];
        jumlah=new String[jml];
        stokasal=new String[jml];
        index=0;        
        for(i=0;i<row;i++){
            try {
                if(Double.parseDouble(tbMedis.getValueAt(i,0).toString())>0){
                    jumlah[index]=tbMedis.getValueAt(i,0).toString();
                    kodebarang[index]=tbMedis.getValueAt(i,1).toString();
                    namabarang[index]=tbMedis.getValueAt(i,2).toString();
                    hbeli[index]=tbMedis.getValueAt(i,3).toString();
                    total[index]=tbMedis.getValueAt(i,4).toString();
                    satuan[index]=tbMedis.getValueAt(i,5).toString();
                    stokasal[index]=tbMedis.getValueAt(i,6).toString();
                    index++;
                }
            } catch (Exception e) {
            }
        }
        Valid.tabelKosong(tabModeMedis);
        for(i=0;i<jml;i++){
            tabModeMedis.addRow(new Object[]{jumlah[i],kodebarang[i],namabarang[i],hbeli[i],total[i],satuan[i],stokasal[i]});
        }
        
        try{
            ps=koneksi.prepareStatement("select databarang.kode_brng, databarang.nama_brng,utd_stok_medis.hargaterakhir,databarang.kode_sat, "+
                " utd_stok_medis.stok from databarang inner join utd_stok_medis on databarang.kode_brng=utd_stok_medis.kode_brng "+
                " where databarang.status='1' and databarang.kode_brng like ? or "+
                " databarang.status='1' and databarang.nama_brng like ? order by databarang.nama_brng");
            try {
                ps.setString(1,"%"+TCariMedis.getText().trim()+"%");
                ps.setString(2,"%"+TCariMedis.getText().trim()+"%");
                rs=ps.executeQuery();
                while(rs.next()){                
                    tabModeMedis.addRow(new Object[]{"",rs.getString(1),rs.getString(2),rs.getString(3),0,rs.getString(4),rs.getString(5)});
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
    
    private void tampilNonMedis() {
        row=tbNonMedis.getRowCount();
        jml=0;
        for(i=0;i<row;i++){
            try {
                if(Double.parseDouble(tbNonMedis.getValueAt(i,0).toString())>0){
                    jml++;
                }
            } catch (Exception e) {
                jml=jml+0;
            } 
        }
        
        kodebarang=null;
        namabarang=null;
        satuan=null;
        hbeli=null;
        total=null;
        jumlah=null;
        stokasal=null;
        
        kodebarang=new String[jml];
        namabarang=new String[jml];
        satuan=new String[jml];
        hbeli=new String[jml];
        total=new String[jml];
        jumlah=new String[jml];
        stokasal=new String[jml];
        index=0;        
        for(i=0;i<row;i++){
            try {
                if(Double.parseDouble(tbNonMedis.getValueAt(i,0).toString())>0){
                    jumlah[index]=tbNonMedis.getValueAt(i,0).toString();
                    kodebarang[index]=tbNonMedis.getValueAt(i,1).toString();
                    namabarang[index]=tbNonMedis.getValueAt(i,2).toString();
                    hbeli[index]=tbNonMedis.getValueAt(i,3).toString();
                    total[index]=tbNonMedis.getValueAt(i,4).toString();
                    satuan[index]=tbNonMedis.getValueAt(i,5).toString();
                    stokasal[index]=tbNonMedis.getValueAt(i,6).toString();
                    index++;
                }
            } catch (Exception e) {
            }
        }
        Valid.tabelKosong(tabModeNonMedis);
        for(i=0;i<jml;i++){
            tabModeNonMedis.addRow(new Object[]{jumlah[i],kodebarang[i],namabarang[i],hbeli[i],total[i],satuan[i],stokasal[i]});
        }
        
        try{
            ps2=koneksi.prepareStatement("select ipsrsbarang.kode_brng, ipsrsbarang.nama_brng,utd_stok_penunjang.hargaterakhir,ipsrsbarang.kode_sat, "+
                " utd_stok_penunjang.stok from ipsrsbarang inner join utd_stok_penunjang on ipsrsbarang.kode_brng=utd_stok_penunjang.kode_brng "+
                " where ipsrsbarang.kode_brng like ? or ipsrsbarang.nama_brng like ? order by ipsrsbarang.nama_brng");
            try {
                ps2.setString(1,"%"+TCariNonMedis.getText().trim()+"%");
                ps2.setString(2,"%"+TCariNonMedis.getText().trim()+"%");
                rs2=ps2.executeQuery();
                while(rs2.next()){                
                    tabModeNonMedis.addRow(new Object[]{"",rs2.getString(1),rs2.getString(2),rs2.getString(3),0,rs2.getString(4),rs2.getString(5)});
                }   
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rs2!=null){
                    rs2.close();
                }
                if(ps2!=null){
                    ps2.close();
                }
            }                          
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }
    
    private void getData(){
        row=tbDarah.getSelectedRow();
        if(row!= -1){ 
            ttl=0;
            int row2=tbDarah.getRowCount();
            for(int r=0;r<row2;r++){ 
                if(tbDarah.getValueAt(r,0).toString().equals("true")){
                    y=0;
                    try {
                        y=Double.parseDouble(tbDarah.getValueAt(r,13).toString()); 
                    } catch (Exception e) {
                        y=0;
                    }
                    ttl=ttl+y;
                }             
            }
            LTotal.setText(Valid.SetAngka(ttl));
            isKembali();
        }
    }
    
    
    private void isKembali(){
        if(!Bayar.getText().trim().equals("")) {
            bayar=Double.parseDouble(Bayar.getText()); 
        }
        if(ttl>0) {
            total2=ttl; 
        }
        if(!PPN.getText().trim().equals("")) {
            ppn=Double.parseDouble(PPN.getText()); 
        }
        if(ppn>0){
            besarppn=(ppn/100)*total2;
            BesarPPN.setText(Valid.SetAngka(besarppn));
        }else{
            besarppn=0;
            BesarPPN.setText("0");
        }
        
        tagihanppn=besarppn+total2;
        TagihanPPn.setText(Valid.SetAngka(tagihanppn));        
        LKembali.setText(Valid.SetAngka(bayar-tagihanppn));     
    }
    
    public void isCek(){        
        TCari.requestFocus();        
        BtnSimpan.setEnabled(akses.getutd_penyerahan_darah());
        BtnTambah.setEnabled(akses.getutd_stok_darah());
        if(akses.getjml2()>=1){
            kdptgpj.setEditable(false);
            btnPtgPJ.setEnabled(false);
            kdptgpj.setText(akses.getkode());
            Sequel.cariIsi("select nama from petugas where nip=?", nmptgpj,kdptgpj.getText());
        }      
    }
    
    private void emptTeks(){
        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(no_penyerahan,5),signed)),0) from utd_penyerahan_darah where tanggal like '%"+Valid.SetTgl(tanggal.getSelectedItem()+"").substring(0,7)+"%'",dateformat.format(tanggal.getDate()).substring(0,7)+"/PD",5,nopenyerahan); 
        keterangan.setText("");
        nmpengambil.setText("");
        alamatpengambil.setText("");
        keterangan.requestFocus();
        ttl=0;
        besarppn=0;
        TagihanPPn.setText("0");
        LTotal.setText("0");
        BesarPPN.setText("0");
        LKembali.setText("0");
    }
 
}

package keuangan;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fungsi.WarnaTable2;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariPetugas;

public class KeuanganPiutangJasaPerusahaan extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement ps;
    private ResultSet rs;
    private WarnaTable2 warna=new WarnaTable2();
    private double meterai=0,ttl=0,y=0,w=0,ttldisk=0,sbttl=0,ppn=0;
    private int jml=0,i=0,row=0,index=0,pilihan=1;
    private String[] kodebarang,namabarang,satuan,satuanbeli;
    private double[] harga,jumlah,subtotal,diskon,besardiskon,jmltotal,jmlstok,isi,isibesar;
    private boolean sukses=true;    
    private File file;
    private FileWriter fileWriter;
    private ObjectMapper mapper = new ObjectMapper();
    private JsonNode root;
    private JsonNode response;
    private FileReader myObj;
    private DlgCariPetugas petugas=new DlgCariPetugas(null,false);

    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public KeuanganPiutangJasaPerusahaan(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        tabMode=new DefaultTableModel(null,new Object[]{
                "Jml","Satuan Beli","Kode Barang","Nama Barang","Satuan","Harga(Rp)","Subtotal(Rp)",
                "Disk(%)","Diskon(Rp)","Total","Jml.Stok","Isi","Isi Besar"
            }){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
               boolean a = false;
               if ((colIndex==0)||(colIndex==5)||(colIndex==7)||(colIndex==8)) {
                   a=true;
               }
               return a;
             }
              
             Class[] types = new Class[] {
                java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,
                java.lang.String.class,java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,
                java.lang.Double.class,java.lang.Double.class,java.lang.Double.class ,java.lang.Double.class ,java.lang.Double.class 
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbDokter.setModel(tabMode);

        tbDokter.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 13; i++) {
            TableColumn column = tbDokter.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(42);
            }else if(i==1){
                column.setPreferredWidth(65);
            }else if(i==2){
                column.setPreferredWidth(85);
            }else if(i==3){
                column.setPreferredWidth(190);
            }else if(i==4){
                column.setPreferredWidth(50);
            }else if(i==5){
                column.setPreferredWidth(80);
            }else if(i==6){
                column.setPreferredWidth(85);
            }else if(i==7){
                column.setPreferredWidth(50);
            }else if(i==8){
                column.setPreferredWidth(80);
            }else if(i==9){
                column.setPreferredWidth(85);
            }else{
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }
        }
        warna.kolom=0;
        tbDokter.setDefaultRenderer(Object.class,warna);

        NoPemesanan.setDocument(new batasInput((byte)20).getKata(NoPemesanan));
        kdsup.setDocument(new batasInput((byte)5).getKata(kdsup));
        kdptg.setDocument(new batasInput((byte)20).getKata(kdptg));        
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        if(koneksiDB.CARICEPAT().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampil2();
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampil2();
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampil2();
                    }
                }
            });
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
        scrollPane1 = new widget.ScrollPane();
        tbDokter = new widget.Table();
        panelisi3 = new widget.panelisi();
        label15 = new widget.Label();
        NoPemesanan = new widget.TextBox();
        label11 = new widget.Label();
        Tanggal = new widget.Tanggal();
        label13 = new widget.Label();
        kdsup = new widget.TextBox();
        label16 = new widget.Label();
        kdptg = new widget.TextBox();
        nmsup = new widget.TextBox();
        nmptg = new widget.TextBox();
        btnSuplier = new widget.Button();
        btnPetugas = new widget.Button();
        label14 = new widget.Label();
        Departemen = new widget.TextBox();
        label18 = new widget.Label();
        Tanggal1 = new widget.Tanggal();
        panelisi1 = new widget.panelisi();
        label10 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari1 = new widget.Button();
        BtnCari = new widget.Button();
        BtnKeluar = new widget.Button();
        BtnTambah = new widget.Button();
        label12 = new widget.Label();
        LSubtotal = new widget.Label();
        label17 = new widget.Label();
        tppn = new widget.TextBox();
        Meterai = new widget.TextBox();
        label24 = new widget.Label();
        BtnSimpan = new widget.Button();
        BtnPrint = new widget.Button();
        BtnAll = new widget.Button();
        label20 = new widget.Label();
        tppn1 = new widget.TextBox();
        label21 = new widget.Label();
        tppn2 = new widget.TextBox();
        tppn3 = new widget.TextBox();
        tppn4 = new widget.TextBox();
        label22 = new widget.Label();
        tppn5 = new widget.TextBox();
        tppn6 = new widget.TextBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Piutang Jasa Perusahaan ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

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
        tbDokter.setToolTipText("Masukkan jumlah geser ke kanan");
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

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(100, 103));
        panelisi3.setLayout(null);

        label15.setText("No.Piutang :");
        label15.setName("label15"); // NOI18N
        label15.setPreferredSize(new java.awt.Dimension(80, 23));
        panelisi3.add(label15);
        label15.setBounds(0, 10, 75, 23);

        NoPemesanan.setName("NoPemesanan"); // NOI18N
        NoPemesanan.setPreferredSize(new java.awt.Dimension(207, 23));
        NoPemesanan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoPemesananKeyPressed(evt);
            }
        });
        panelisi3.add(NoPemesanan);
        NoPemesanan.setBounds(79, 10, 257, 23);

        label11.setText("Tgl.Piutang :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(80, 23));
        panelisi3.add(label11);
        label11.setBounds(381, 10, 77, 23);

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
        Tanggal.setBounds(462, 10, 90, 23);

        label13.setText("Pegawai :");
        label13.setName("label13"); // NOI18N
        label13.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label13);
        label13.setBounds(381, 40, 77, 23);

        kdsup.setName("kdsup"); // NOI18N
        kdsup.setPreferredSize(new java.awt.Dimension(80, 23));
        kdsup.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdsupKeyPressed(evt);
            }
        });
        panelisi3.add(kdsup);
        kdsup.setBounds(79, 40, 70, 23);

        label16.setText("Instansi :");
        label16.setName("label16"); // NOI18N
        label16.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(label16);
        label16.setBounds(0, 40, 75, 23);

        kdptg.setName("kdptg"); // NOI18N
        kdptg.setPreferredSize(new java.awt.Dimension(80, 23));
        kdptg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdptgKeyPressed(evt);
            }
        });
        panelisi3.add(kdptg);
        kdptg.setBounds(462, 40, 100, 23);

        nmsup.setEditable(false);
        nmsup.setName("nmsup"); // NOI18N
        nmsup.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(nmsup);
        nmsup.setBounds(151, 40, 185, 23);

        nmptg.setEditable(false);
        nmptg.setName("nmptg"); // NOI18N
        nmptg.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(nmptg);
        nmptg.setBounds(564, 40, 175, 23);

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
        btnSuplier.setBounds(339, 40, 28, 23);

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
        btnPetugas.setBounds(742, 40, 28, 23);

        label14.setText("Keterangan :");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label14);
        label14.setBounds(0, 70, 75, 23);

        Departemen.setName("Departemen"); // NOI18N
        Departemen.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(Departemen);
        Departemen.setBounds(79, 70, 691, 23);

        label18.setText("Jatuh Tempo :");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(80, 23));
        panelisi3.add(label18);
        label18.setBounds(596, 10, 80, 23);

        Tanggal1.setDisplayFormat("dd-MM-yyyy");
        Tanggal1.setName("Tanggal1"); // NOI18N
        Tanggal1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                Tanggal1ItemStateChanged(evt);
            }
        });
        Tanggal1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Tanggal1KeyPressed(evt);
            }
        });
        panelisi3.add(Tanggal1);
        Tanggal1.setBounds(680, 10, 90, 23);

        internalFrame1.add(panelisi3, java.awt.BorderLayout.PAGE_START);

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 115));
        panelisi1.setLayout(null);

        label10.setText("Key Word :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(75, 23));
        panelisi1.add(label10);
        label10.setBounds(210, 75, 75, 23);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(150, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelisi1.add(TCari);
        TCari.setBounds(289, 75, 180, 23);

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
        BtnCari1.setBounds(471, 75, 28, 23);

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
        BtnCari.setBounds(575, 72, 100, 30);

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
        BtnKeluar.setBounds(680, 72, 100, 30);

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
        BtnTambah.setBounds(531, 75, 28, 23);

        label12.setText("Grand Total :");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(60, 30));
        panelisi1.add(label12);
        label12.setBounds(0, 10, 120, 23);

        LSubtotal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LSubtotal.setText("0");
        LSubtotal.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        LSubtotal.setName("LSubtotal"); // NOI18N
        LSubtotal.setPreferredSize(new java.awt.Dimension(110, 30));
        panelisi1.add(LSubtotal);
        LSubtotal.setBounds(124, 10, 157, 23);

        label17.setText("PPN (%) :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(60, 30));
        panelisi1.add(label17);
        label17.setBounds(280, 40, 85, 23);

        tppn.setText("0");
        tppn.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tppn.setName("tppn"); // NOI18N
        tppn.setPreferredSize(new java.awt.Dimension(80, 23));
        tppn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tppnKeyPressed(evt);
            }
        });
        panelisi1.add(tppn);
        tppn.setBounds(416, 40, 110, 23);

        Meterai.setText("0");
        Meterai.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Meterai.setName("Meterai"); // NOI18N
        Meterai.setPreferredSize(new java.awt.Dimension(80, 23));
        Meterai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MeteraiKeyPressed(evt);
            }
        });
        panelisi1.add(Meterai);
        Meterai.setBounds(619, 40, 157, 23);

        label24.setText("Total Tagihan :");
        label24.setName("label24"); // NOI18N
        label24.setPreferredSize(new java.awt.Dimension(60, 30));
        panelisi1.add(label24);
        label24.setBounds(525, 40, 90, 23);

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan.setMnemonic('S');
        BtnSimpan.setText("Simpan");
        BtnSimpan.setToolTipText("Alt+S");
        BtnSimpan.setName("BtnSimpan"); // NOI18N
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
        BtnSimpan.setBounds(5, 72, 100, 30);

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
        BtnPrint.setBounds(110, 72, 100, 30);

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('2');
        BtnAll.setToolTipText("2Alt+2");
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
        panelisi1.add(BtnAll);
        BtnAll.setBounds(501, 75, 28, 23);

        label20.setText("Jasa Menejemen(%) :");
        label20.setName("label20"); // NOI18N
        label20.setPreferredSize(new java.awt.Dimension(60, 30));
        panelisi1.add(label20);
        label20.setBounds(0, 40, 120, 23);

        tppn1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tppn1.setName("tppn1"); // NOI18N
        tppn1.setPreferredSize(new java.awt.Dimension(80, 23));
        tppn1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tppn1KeyPressed(evt);
            }
        });
        panelisi1.add(tppn1);
        tppn1.setBounds(171, 40, 110, 23);

        label21.setText("DPP Nilai Lain :");
        label21.setName("label21"); // NOI18N
        label21.setPreferredSize(new java.awt.Dimension(60, 30));
        panelisi1.add(label21);
        label21.setBounds(280, 10, 85, 23);

        tppn2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tppn2.setName("tppn2"); // NOI18N
        tppn2.setPreferredSize(new java.awt.Dimension(80, 23));
        tppn2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tppn2KeyPressed(evt);
            }
        });
        panelisi1.add(tppn2);
        tppn2.setBounds(369, 10, 157, 23);

        tppn3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tppn3.setName("tppn3"); // NOI18N
        tppn3.setPreferredSize(new java.awt.Dimension(80, 23));
        tppn3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tppn3KeyPressed(evt);
            }
        });
        panelisi1.add(tppn3);
        tppn3.setBounds(124, 40, 45, 23);

        tppn4.setText("12");
        tppn4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tppn4.setName("tppn4"); // NOI18N
        tppn4.setPreferredSize(new java.awt.Dimension(80, 23));
        tppn4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tppn4KeyPressed(evt);
            }
        });
        panelisi1.add(tppn4);
        tppn4.setBounds(369, 40, 45, 23);

        label22.setText("PPH 23 (%) :");
        label22.setName("label22"); // NOI18N
        label22.setPreferredSize(new java.awt.Dimension(60, 30));
        panelisi1.add(label22);
        label22.setBounds(525, 10, 90, 23);

        tppn5.setText("2");
        tppn5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tppn5.setName("tppn5"); // NOI18N
        tppn5.setPreferredSize(new java.awt.Dimension(80, 23));
        tppn5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tppn5KeyPressed(evt);
            }
        });
        panelisi1.add(tppn5);
        tppn5.setBounds(619, 10, 45, 23);

        tppn6.setText("0");
        tppn6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tppn6.setName("tppn6"); // NOI18N
        tppn6.setPreferredSize(new java.awt.Dimension(80, 23));
        tppn6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tppn6KeyPressed(evt);
            }
        });
        panelisi1.add(tppn6);
        tppn6.setBounds(666, 10, 110, 23);

        internalFrame1.add(panelisi1, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents
/*
private void KdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdKeyPressed
    Valid.pindah(evt,BtnCari,Nm);
}//GEN-LAST:event_TKdKeyPressed
*/

private void NoPemesananKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoPemesananKeyPressed
        Valid.pindah(evt, BtnSimpan, kdsup);
}//GEN-LAST:event_NoPemesananKeyPressed

private void TanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKeyPressed
        Valid.pindah(evt,NoPemesanan,kdsup);
}//GEN-LAST:event_TanggalKeyPressed

private void kdsupKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdsupKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select datasuplier.nama_suplier from datasuplier where datasuplier.kode_suplier=?", nmsup,kdsup.getText());           
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select datasuplier.nama_suplier from datasuplier where datasuplier.kode_suplier=?", nmsup,kdsup.getText());
            NoPemesanan.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select datasuplier.nama_suplier from datasuplier where datasuplier.kode_suplier=?", nmsup,kdsup.getText());
            kdptg.requestFocus(); 
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnSuplierActionPerformed(null);
        }
}//GEN-LAST:event_kdsupKeyPressed

private void kdptgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdptgKeyPressed
        
}//GEN-LAST:event_kdptgKeyPressed

private void btnSuplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuplierActionPerformed
    pilihan=1;    
}//GEN-LAST:event_btnSuplierActionPerformed

private void btnPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPetugasActionPerformed
    petugas.emptTeks();
    petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
    petugas.setLocationRelativeTo(internalFrame1);
    petugas.setAlwaysOnTop(false);
    petugas.setVisible(true);
}//GEN-LAST:event_btnPetugasActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        try {
            if(Valid.daysOld("./cache/suratpemesananobat.iyem")<8){
                tampil2();
            }else{
                tampil();
                tampil2();
            }
        } catch (Exception e) {
        }            
    }//GEN-LAST:event_formWindowOpened

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            tampil2();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari1.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            kdsup.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            tbDokter.requestFocus();
        }
    }//GEN-LAST:event_TCariKeyPressed

    private void BtnCari1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCari1ActionPerformed
        tampil2();
    }//GEN-LAST:event_BtnCari1ActionPerformed

    private void BtnCari1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCari1KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            tampil2();
        }else{
            Valid.pindah(evt, BtnSimpan, BtnKeluar);
        }
    }//GEN-LAST:event_BtnCari1KeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        /*form.emptTeks();
        form.isCek();
        form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        form.setLocationRelativeTo(internalFrame1);
        form.setAlwaysOnTop(false);
        form.setVisible(true);*/
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnSimpan,BtnKeluar);
        }
    }//GEN-LAST:event_BtnCariKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnSimpan,TCari);}
    }//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        KeuanganPiutangJasaPerusahaan aplikasi=new KeuanganPiutangJasaPerusahaan(null,false);
        aplikasi.isCek();
        aplikasi.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        aplikasi.setLocationRelativeTo(internalFrame1);
        aplikasi.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnTambahActionPerformed

    private void tppnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tppnKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            getData();
        }
    }//GEN-LAST:event_tppnKeyPressed

    private void MeteraiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MeteraiKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            getData();
        }
    }//GEN-LAST:event_MeteraiKeyPressed

    private void TanggalItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TanggalItemStateChanged
        try {
            autoNomor();
        } catch (Exception e) {
        } 
    }//GEN-LAST:event_TanggalItemStateChanged

    private void tbDokterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDokterMouseClicked
        if(tbDokter.getRowCount()!=0){
            try {
                   if((tbDokter.getSelectedColumn()==2)||(tbDokter.getSelectedColumn()==5)||(tbDokter.getSelectedColumn()==6)||(tbDokter.getSelectedColumn()==8)){                       
                        getData();  
                   }else if(tbDokter.getSelectedColumn()==7){
                       try {
                           tbDokter.setValueAt(Math.round(Double.parseDouble(tbDokter.getValueAt(tbDokter.getSelectedRow(),6).toString())*
                               (Double.parseDouble(tbDokter.getValueAt(tbDokter.getSelectedRow(),7).toString())/100)),tbDokter.getSelectedRow(),8);
                       } catch (Exception e) {
                           tbDokter.setValueAt(0,tbDokter.getSelectedRow(),8);
                       }
                       getData(); 
                   }
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbDokterMouseClicked

    private void tbDokterPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tbDokterPropertyChange
        if(this.isVisible()==true){
            getData();
        }
    }//GEN-LAST:event_tbDokterPropertyChange

    private void tbDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbDokterKeyPressed
        /*if(tbDokter.getRowCount()!=0){
            if(evt.getKeyCode()==KeyEvent.VK_ENTER){
                try {                  
                   if((tbDokter.getSelectedColumn()==2)||(tbDokter.getSelectedColumn()==3)||(tbDokter.getSelectedColumn()==6)||(tbDokter.getSelectedColumn()==9)){                       
                        getData();  
                        TCari.setText("");
                        TCari.requestFocus();                                                
                   }else if(tbDokter.getSelectedColumn()==7){
                       try {
                           tbDokter.setValueAt(Math.round(Double.parseDouble(tbDokter.getValueAt(tbDokter.getSelectedRow(),6).toString())*
                               (Double.parseDouble(tbDokter.getValueAt(tbDokter.getSelectedRow(),7).toString())/100)),tbDokter.getSelectedRow(),8);
                       } catch (Exception e) {
                           tbDokter.setValueAt(0,tbDokter.getSelectedRow(),8);
                       }
                       
                       getData();
                   }else if((tbDokter.getSelectedColumn()==9)||(tbDokter.getSelectedColumn()==10)){                       
                       TCari.setText("");
                       TCari.requestFocus();
                   }
                } catch (Exception e) {
                }
            }else if(evt.getKeyCode()==KeyEvent.VK_DELETE){
                try {
                    if(tbDokter.getSelectedColumn()==0){
                        tbDokter.setValueAt("", tbDokter.getSelectedRow(),0);
                    }else if(tbDokter.getSelectedColumn()==5){
                        tbDokter.setValueAt(0, tbDokter.getSelectedRow(),5);
                        tbDokter.setValueAt(0, tbDokter.getSelectedRow(),6);
                    }else if(tbDokter.getSelectedColumn()==7){
                        tbDokter.setValueAt(0, tbDokter.getSelectedRow(),7);
                        tbDokter.setValueAt(0, tbDokter.getSelectedRow(),8);
                    }
                } catch (Exception e) {
                }
            }else if(evt.getKeyCode()==KeyEvent.VK_SHIFT){
                TCari.setText("");
                TCari.requestFocus();
            }else if((evt.getKeyCode()==KeyEvent.VK_RIGHT)||(evt.getKeyCode()==KeyEvent.VK_LEFT)||(evt.getKeyCode()==KeyEvent.VK_DOWN)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                   if((tbDokter.getSelectedColumn()==2)||(tbDokter.getSelectedColumn()==3)||(tbDokter.getSelectedColumn()==6)||(tbDokter.getSelectedColumn()==9)){                                              
                       getData();  
                   }else if(tbDokter.getSelectedColumn()==7){
                       try {
                           tbDokter.setValueAt(Math.round(Double.parseDouble(tbDokter.getValueAt(tbDokter.getSelectedRow(),6).toString())*
                               (Double.parseDouble(tbDokter.getValueAt(tbDokter.getSelectedRow(),7).toString())/100)),tbDokter.getSelectedRow(),8);
                       } catch (Exception e) {
                           tbDokter.setValueAt(0,tbDokter.getSelectedRow(),8);
                       }                       
                       getData();
                   }else if(tbDokter.getSelectedColumn()==1){
                       getData();  
                   }
            }else if(evt.getKeyCode()==KeyEvent.VK_SPACE){
                if(tbDokter.getSelectedColumn()==1){
                    y=0;
                    try {
                        y=Double.parseDouble(tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString());                        
                    } catch (Exception e) {
                        y=0;
                    }
                    if(y>0){
                        datakonversi.setSatuanKecil(tbDokter.getValueAt(tbDokter.getSelectedRow(),4).toString());
                        datakonversi.isCek();
                        datakonversi.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight());
                        datakonversi.setLocationRelativeTo(internalFrame1);
                        datakonversi.setVisible(true);                        
                    }else{
                        JOptionPane.showMessageDialog(null,"Silahkan masukkan jumlah pemesanan terelebih dahulu..!!");
                    }
                }
            }
        }*/
    }//GEN-LAST:event_tbDokterKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(NoPemesanan.getText().trim().equals("")){
            Valid.textKosong(NoPemesanan,"No.Pemesanan");
        }else if(nmsup.getText().trim().equals("")){
            Valid.textKosong(kdsup,"Supplier");
        }else if(nmptg.getText().trim().equals("")){
            Valid.textKosong(kdptg,"Petugas");
        }else if(Meterai.getText().trim().equals("")){
            Valid.textKosong(Meterai,"Meterai");
        }else if(tbDokter.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        }else if(ttl<=0){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan masukkan pemesanan...!!!!");
            tbDokter.requestFocus();
        }else{
            int reply = JOptionPane.showConfirmDialog(rootPane,"Eeiiiiiits, udah bener belum data yang mau disimpan..??","Konfirmasi",JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
                Sequel.AutoComitFalse();
                sukses=true;
                if(Sequel.menyimpantf2("surat_pemesanan_medis","?,?,?,?,?,?,?,?,?,?,?","No.Pemesanan",11,new String[]{
                    NoPemesanan.getText(),kdsup.getText(),kdptg.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+""),
                    ""+sbttl,""+ttldisk,""+ttl,
                    ""+ppn,""+meterai,""+(ttl+ppn+meterai),"Proses Pesan"
                })==true){
                    jml=tbDokter.getRowCount();
                    for(i=0;i<jml;i++){
                        try {
                            if(Valid.SetAngka(tbDokter.getValueAt(i,0).toString())>0){
                                if(Sequel.menyimpantf2("detail_surat_pemesanan_medis","?,?,?,?,?,?,?,?,?,?","Transaksi Pemesanan",10,new String[]{
                                    NoPemesanan.getText(),
                                    tbDokter.getValueAt(i,2).toString(),
                                    tbDokter.getValueAt(i,1).toString(),
                                    tbDokter.getValueAt(i,0).toString(),
                                    tbDokter.getValueAt(i,5).toString(),
                                    tbDokter.getValueAt(i,6).toString(),
                                    tbDokter.getValueAt(i,7).toString(),
                                    tbDokter.getValueAt(i,8).toString(),
                                    tbDokter.getValueAt(i,9).toString(),
                                    tbDokter.getValueAt(i,10).toString()
                                })==false){
                                    sukses=false;
                                }
                            }
                        } catch (Exception e) {
                            sukses=false;
                            System.out.println("Notifikasi : "+e);
                        }
                    }
                }else{
                    sukses=false;
                    JOptionPane.showMessageDialog(rootPane, "Gagal Menyimpan, kemungkinan No.Pemesanan sudah ada sebelumnya...!!");
                }
                if(sukses==true){
                    Sequel.Commit();
                    jml=tbDokter.getRowCount();
                    for(i=0;i<jml;i++){
                        tbDokter.setValueAt("",i,0);
                        tbDokter.setValueAt(0,i,6);
                        tbDokter.setValueAt(0,i,7);
                        tbDokter.setValueAt(0,i,8);
                        tbDokter.setValueAt(0,i,9);
                        tbDokter.setValueAt(0,i,10);
                    }
                    Meterai.setText("0");
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

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,BtnKeluar,TCari);
        }
    }//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        
    }//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt,BtnSimpan,TCari);
        }
    }//GEN-LAST:event_BtnPrintKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        tampil();
        tampil2();
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnCari, TCari);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

    private void Tanggal1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_Tanggal1ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_Tanggal1ItemStateChanged

    private void Tanggal1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tanggal1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Tanggal1KeyPressed

    private void tppn1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tppn1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tppn1KeyPressed

    private void tppn2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tppn2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tppn2KeyPressed

    private void tppn3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tppn3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tppn3KeyPressed

    private void tppn4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tppn4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tppn4KeyPressed

    private void tppn5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tppn5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tppn5KeyPressed

    private void tppn6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tppn6KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tppn6KeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            KeuanganPiutangJasaPerusahaan dialog = new KeuanganPiutangJasaPerusahaan(new javax.swing.JFrame(), true);
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
    private widget.Button BtnCari1;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.Button BtnTambah;
    private widget.TextBox Departemen;
    private widget.Label LSubtotal;
    private widget.TextBox Meterai;
    private widget.TextBox NoPemesanan;
    private widget.TextBox TCari;
    private widget.Tanggal Tanggal;
    private widget.Tanggal Tanggal1;
    private widget.Button btnPetugas;
    private widget.Button btnSuplier;
    private widget.InternalFrame internalFrame1;
    private widget.TextBox kdptg;
    private widget.TextBox kdsup;
    private widget.Label label10;
    private widget.Label label11;
    private widget.Label label12;
    private widget.Label label13;
    private widget.Label label14;
    private widget.Label label15;
    private widget.Label label16;
    private widget.Label label17;
    private widget.Label label18;
    private widget.Label label20;
    private widget.Label label21;
    private widget.Label label22;
    private widget.Label label24;
    private widget.TextBox nmptg;
    private widget.TextBox nmsup;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi3;
    private widget.ScrollPane scrollPane1;
    private widget.Table tbDokter;
    private widget.TextBox tppn;
    private widget.TextBox tppn1;
    private widget.TextBox tppn2;
    private widget.TextBox tppn3;
    private widget.TextBox tppn4;
    private widget.TextBox tppn5;
    private widget.TextBox tppn6;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
       try{
            file=new File("./cache/suratpemesananobat.iyem");
            file.createNewFile();
            fileWriter = new FileWriter(file);
            StringBuilder iyembuilder = new StringBuilder();
            ps=koneksi.prepareStatement("select databarang.kode_brng, databarang.nama_brng,databarang.kode_satbesar, "+
                " (databarang.h_beli*databarang.isi) as harga,databarang.kode_sat,databarang.isi "+
                " from databarang inner join jenis on databarang.kdjns=jenis.kdjns "+
                " where databarang.status='1' order by databarang.nama_brng");
            try {  
                rs=ps.executeQuery();
                while(rs.next()){
                    iyembuilder.append("{\"SatuanBeli\":\"").append(rs.getString("kode_satbesar")).append("\",\"KodeBarang\":\"").append(rs.getString("kode_brng")).append("\",\"NamaBarang\":\"").append(rs.getString("nama_brng").replaceAll("\"","")).append("\",\"Satuan\":\"").append(rs.getString("kode_sat")).append("\",\"Harga\":\"").append(rs.getString("harga")).append("\",\"Isi\":\"").append(rs.getString("isi")).append("\"},");
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
            
            if (iyembuilder.length() > 0) {
                iyembuilder.setLength(iyembuilder.length() - 1);
                fileWriter.write("{\"suratpemesananobat\":["+iyembuilder+"]}");
                fileWriter.flush();
            }
            
            fileWriter.close();
            iyembuilder=null;
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        
    }
    
    private void tampil2() {
        try{
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
            satuanbeli=new String[jml];
            harga=new double[jml];
            jumlah=new double[jml];
            subtotal=new double[jml];
            diskon=new double[jml];
            besardiskon=new double[jml];
            jmltotal=new double[jml];
            jmlstok=new double[jml];
            isi=new double[jml];
            isibesar=new double[jml];
            index=0;        
            for(i=0;i<row;i++){
                try {
                    if(Double.parseDouble(tbDokter.getValueAt(i,0).toString())>0){
                        jumlah[index]=Double.parseDouble(tbDokter.getValueAt(i,0).toString());
                        satuanbeli[index]=tbDokter.getValueAt(i,1).toString();
                        kodebarang[index]=tbDokter.getValueAt(i,2).toString();
                        namabarang[index]=tbDokter.getValueAt(i,3).toString();
                        satuan[index]=tbDokter.getValueAt(i,4).toString();
                        harga[index]=Double.parseDouble(tbDokter.getValueAt(i,5).toString());
                        subtotal[index]=Double.parseDouble(tbDokter.getValueAt(i,6).toString());
                        diskon[index]=Double.parseDouble(tbDokter.getValueAt(i,7).toString());
                        besardiskon[index]=Double.parseDouble(tbDokter.getValueAt(i,8).toString());
                        jmltotal[index]=Double.parseDouble(tbDokter.getValueAt(i,9).toString());
                        jmlstok[index]=Double.parseDouble(tbDokter.getValueAt(i,10).toString());
                        isi[index]=Double.parseDouble(tbDokter.getValueAt(i,11).toString());
                        isibesar[index]=Double.parseDouble(tbDokter.getValueAt(i,12).toString());
                        index++;
                    }
                } catch (Exception e) {
                }
            }
            Valid.tabelKosong(tabMode);
            for(i=0;i<jml;i++){
                tabMode.addRow(new Object[]{jumlah[i],satuanbeli[i],kodebarang[i],namabarang[i],satuan[i],harga[i],subtotal[i],diskon[i],besardiskon[i],jmltotal[i],jmlstok[i],isi[i],isibesar[i]});
            }
            
            kodebarang=null;
            namabarang=null;
            satuan=null;
            satuanbeli=null;
            harga=null;
            jumlah=null;
            subtotal=null;
            diskon=null;
            besardiskon=null;
            jmltotal=null;
            jmlstok=null;
            isi=null;
            isibesar=null;
            
            myObj = new FileReader("./cache/suratpemesananobat.iyem");
            root = mapper.readTree(myObj);
            response = root.path("suratpemesananobat");
            if(response.isArray()){
                if(TCari.getText().trim().equals("")){
                    for(JsonNode list:response){
                        tabMode.addRow(new Object[]{
                            "",list.path("SatuanBeli").asText(),list.path("KodeBarang").asText(),list.path("NamaBarang").asText(),list.path("Satuan").asText(),list.path("Harga").asDouble(),0,0,0,0,0,list.path("Isi").asDouble(),1
                        });
                    }
                }else{
                    for(JsonNode list:response){
                        if(list.path("KodeBarang").asText().toLowerCase().contains(TCari.getText().toLowerCase())||list.path("NamaBarang").asText().toLowerCase().contains(TCari.getText().toLowerCase())){
                            tabMode.addRow(new Object[]{
                                "",list.path("SatuanBeli").asText(),list.path("KodeBarang").asText(),list.path("NamaBarang").asText(),list.path("Satuan").asText(),list.path("Harga").asDouble(),0,0,0,0,0,list.path("Isi").asDouble(),1
                            });
                        }
                    }
                }
            }
            myObj.close(); 
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        
    }
    
    private void getData(){        
        row=tbDokter.getSelectedRow();
        if(row!= -1){  
            if(!tbDokter.getValueAt(row,0).toString().equals("")){
                try {
                    if(Double.parseDouble(tbDokter.getValueAt(row,0).toString())>0){                        
                        tbDokter.setValueAt(Double.parseDouble(tbDokter.getValueAt(row,0).toString())*Double.parseDouble(tbDokter.getValueAt(row,5).toString()), row,6);                
                        tbDokter.setValueAt(Double.parseDouble(tbDokter.getValueAt(row,6).toString())-Double.parseDouble(tbDokter.getValueAt(row,8).toString()), row,9);  
                        tbDokter.setValueAt(Double.parseDouble(tbDokter.getValueAt(row,0).toString())*(Double.parseDouble(tbDokter.getValueAt(row,11).toString())/Double.parseDouble(tbDokter.getValueAt(row,12).toString())), row,10); 
                    } 
                } catch (Exception e) {
                    System.out.println("error : "+e);
                    tbDokter.setValueAt("",row,0);
                    tbDokter.setValueAt(0,row,6);
                    tbDokter.setValueAt(0,row,7);
                    tbDokter.setValueAt(0,row,8);
                    tbDokter.setValueAt(0,row,9);
                    tbDokter.setValueAt(0,row,10);          
                }
            }else{
                tbDokter.setValueAt(0,row,6);
                tbDokter.setValueAt(0,row,7);
                tbDokter.setValueAt(0,row,8);
                tbDokter.setValueAt(0,row,9);
                tbDokter.setValueAt(0,row,10);  
            }
        }
                
        ttl=0;sbttl=0;ppn=0;ttldisk=0;
        y=0;w=0;
        meterai=0;
        if(!Meterai.getText().equals("")){
            meterai=Double.parseDouble(Meterai.getText());
        }

        jml=tbDokter.getRowCount();
        for(i=0;i<jml;i++){                 
            try {
                w=Double.parseDouble(tbDokter.getValueAt(i,6).toString());
            } catch (Exception e) {
                tbDokter.setValueAt(0,tbDokter.getSelectedRow(),6);
                w=0;                
            }
            sbttl=sbttl+w;                

            try {
                y=Double.parseDouble(tbDokter.getValueAt(i,8).toString()); 
            } catch (Exception e) {
                tbDokter.setValueAt(0,tbDokter.getSelectedRow(),8);
                y=0;
            }
            ttldisk=ttldisk+y;
        }
        LSubtotal.setText(Valid.SetAngka(sbttl));
        //LPotongan.setText(Valid.SetAngka(ttldisk));
        ttl=sbttl-ttldisk;
        //LTotal2.setText(Valid.SetAngka(ttl));
        ppn=0;
        if(!tppn.getText().equals("")){
            ppn=Math.round((Double.parseDouble(tppn.getText())/100) *(ttl));
            //LPpn.setText(Valid.SetAngka(ppn));
            //LTagiha.setText(Valid.SetAngka(ttl+ppn+meterai));
        }
        
    }   
    
    public void isCek(){
        autoNomor();
        TCari.requestFocus();
        if(akses.getjml2()>=1){
            BtnTambah.setEnabled(akses.getkategori_piutang_jasa_perusahaan());
            kdptg.setEditable(false);
            btnPetugas.setEnabled(false);
            kdptg.setText(akses.getkode());
            BtnSimpan.setEnabled(akses.getpiutang_jasa_perusahaan());
            BtnTambah.setEnabled(akses.getobat());
            nmptg.setText(petugas.tampil3(kdptg.getText()));
        }        
    }
    
    private void autoNomor() {
        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(surat_pemesanan_medis.no_pemesanan,3),signed)),0) from surat_pemesanan_medis where surat_pemesanan_medis.tanggal='"+Valid.SetTgl(Tanggal.getSelectedItem()+"")+"' ",
                "SPM"+Tanggal.getSelectedItem().toString().substring(8,10)+Tanggal.getSelectedItem().toString().substring(3,5)+Tanggal.getSelectedItem().toString().substring(0,2),3,NoPemesanan); 
    }

    public DefaultTableModel tabMode(){
        return tabMode;
    }
    
    public void panggilgetData(){
        getData();
    }
    
    public void panggilgetData(String nopengajuan){
        try{
            ps=koneksi.prepareStatement(
                "select databarang.kode_brng, databarang.nama_brng,detail_pengajuan_barang_medis.kode_sat as satbesar,databarang.kode_sat,"+
                "detail_pengajuan_barang_medis.h_pengajuan,detail_pengajuan_barang_medis.jumlah,detail_pengajuan_barang_medis.total, "+
                "detail_pengajuan_barang_medis.jumlah2,databarang.isi from databarang inner join jenis inner join detail_pengajuan_barang_medis "+
                " on databarang.kdjns=jenis.kdjns and databarang.kode_brng=detail_pengajuan_barang_medis.kode_brng "+
                " where detail_pengajuan_barang_medis.no_pengajuan=?");
            try {
                ps.setString(1,nopengajuan);
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new Object[]{
                        rs.getString("jumlah"),rs.getString("satbesar"),rs.getString("kode_brng"),
                        rs.getString("nama_brng"),rs.getString("kode_sat"),rs.getDouble("h_pengajuan"),
                        rs.getDouble("total"),0,0,rs.getDouble("total"),rs.getDouble("jumlah2"),rs.getDouble("isi"),
                        (rs.getDouble("isi")/(rs.getDouble("jumlah2")/rs.getDouble("jumlah")))
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
        getData();
    }
}

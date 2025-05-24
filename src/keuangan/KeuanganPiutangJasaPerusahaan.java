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
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariPetugas;
import simrskhanza.DlgCariPerusahaan;

public class KeuanganPiutangJasaPerusahaan extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement ps;
    private ResultSet rs;
    private WarnaTable2 warna=new WarnaTable2();
    private double x=0,grandtotal=0,persenmenejemen=0,besarmenejemen=0,dppnilailain=0,persenppn=0,besarppn=0,persenpph=0,besarpph=0,totaltagihan=0;
    private int jml=0,i=0,row=0,index=0;
    private boolean sukses=true;    
    private File file;
    private FileWriter fileWriter;
    private ObjectMapper mapper = new ObjectMapper();
    private JsonNode root;
    private JsonNode response;
    private FileReader myObj;
    private DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    private Jurnal jur=new Jurnal();
    private String Piutang_Jasa_Perusahaan=Sequel.cariIsi("select set_akun2.Piutang_Jasa_Perusahaan from set_akun2"),
                   Pendapatan_Piutang_Jasa_Perusahaan=Sequel.cariIsi("select set_akun2.Pendapatan_Piutang_Jasa_Perusahaan from set_akun2");

    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public KeuanganPiutangJasaPerusahaan(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        tabMode=new DefaultTableModel(null,new Object[]{
                "Kode","Kategori","Jml","Harga(Rp)","Subtotal(Rp)","Disk(%)","Diskon(Rp)","Total(Rp)"
            }){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
               boolean a = false;
               if ((colIndex==2)||(colIndex==3)||(colIndex==5)||(colIndex==6)) {
                   a=true;
               }
               return a;
             }
              
             Class[] types = new Class[] {
                java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.Double.class,
                java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,java.lang.Double.class 
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
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
                column.setPreferredWidth(300);
            }else if(i==2){
                column.setPreferredWidth(35);
            }else if(i==3){
                column.setPreferredWidth(90);
            }else if(i==4){
                column.setPreferredWidth(100);
            }else if(i==5){
                column.setPreferredWidth(47);
            }else if(i==6){
                column.setPreferredWidth(90);
            }else if(i==7){
                column.setPreferredWidth(100);
            }
        }
        warna.kolom=2;
        tbDokter.setDefaultRenderer(Object.class,warna);

        NoPiutang.setDocument(new batasInput((byte)20).getKata(NoPiutang));
        Keterangan.setDocument(new batasInput((int)100).getKata(Keterangan));
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
        
        petugas.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(petugas.getTable().getSelectedRow()!= -1){                   
                    KdPetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                    NmPetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                }   
                KdPetugas.requestFocus();
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
        
        PersenMenejemen.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {getData();}
            @Override
            public void removeUpdate(DocumentEvent e) {getData();}
            @Override
            public void changedUpdate(DocumentEvent e) {getData();}
        });
        DPPLainLain.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {getData();}
            @Override
            public void removeUpdate(DocumentEvent e) {getData();}
            @Override
            public void changedUpdate(DocumentEvent e) {getData();}
        });
        PersenPPN.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {getData();}
            @Override
            public void removeUpdate(DocumentEvent e) {getData();}
            @Override
            public void changedUpdate(DocumentEvent e) {getData();}
        });
        PersenPPH.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {getData();}
            @Override
            public void removeUpdate(DocumentEvent e) {getData();}
            @Override
            public void changedUpdate(DocumentEvent e) {getData();}
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

        internalFrame1 = new widget.InternalFrame();
        scrollPane1 = new widget.ScrollPane();
        tbDokter = new widget.Table();
        panelisi3 = new widget.panelisi();
        label15 = new widget.Label();
        NoPiutang = new widget.TextBox();
        label11 = new widget.Label();
        TanggalPiutang = new widget.Tanggal();
        label13 = new widget.Label();
        KdPerusahaan = new widget.TextBox();
        label16 = new widget.Label();
        KdPetugas = new widget.TextBox();
        NmPerusahaan = new widget.TextBox();
        NmPetugas = new widget.TextBox();
        BtnPerusahaan = new widget.Button();
        btnPetugas = new widget.Button();
        label14 = new widget.Label();
        Keterangan = new widget.TextBox();
        label18 = new widget.Label();
        JatuhTempo = new widget.Tanggal();
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
        BesarPPN = new widget.TextBox();
        TotalTagihan = new widget.TextBox();
        label24 = new widget.Label();
        BtnSimpan = new widget.Button();
        BtnPrint = new widget.Button();
        BtnAll = new widget.Button();
        label20 = new widget.Label();
        BesarJasaMenejemen = new widget.TextBox();
        label21 = new widget.Label();
        DPPLainLain = new widget.TextBox();
        PersenMenejemen = new widget.TextBox();
        PersenPPN = new widget.TextBox();
        label22 = new widget.Label();
        PersenPPH = new widget.TextBox();
        BesarPPH = new widget.TextBox();

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

        NoPiutang.setName("NoPiutang"); // NOI18N
        NoPiutang.setPreferredSize(new java.awt.Dimension(207, 23));
        NoPiutang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoPiutangKeyPressed(evt);
            }
        });
        panelisi3.add(NoPiutang);
        NoPiutang.setBounds(79, 10, 257, 23);

        label11.setText("Tanggal Piutang :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(80, 23));
        panelisi3.add(label11);
        label11.setBounds(328, 10, 140, 23);

        TanggalPiutang.setDisplayFormat("dd-MM-yyyy");
        TanggalPiutang.setName("TanggalPiutang"); // NOI18N
        TanggalPiutang.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TanggalPiutangItemStateChanged(evt);
            }
        });
        TanggalPiutang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalPiutangKeyPressed(evt);
            }
        });
        panelisi3.add(TanggalPiutang);
        TanggalPiutang.setBounds(472, 10, 90, 23);

        label13.setText("Petugas :");
        label13.setName("label13"); // NOI18N
        label13.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label13);
        label13.setBounds(391, 40, 77, 23);

        KdPerusahaan.setEditable(false);
        KdPerusahaan.setName("KdPerusahaan"); // NOI18N
        KdPerusahaan.setPreferredSize(new java.awt.Dimension(80, 23));
        KdPerusahaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdPerusahaanKeyPressed(evt);
            }
        });
        panelisi3.add(KdPerusahaan);
        KdPerusahaan.setBounds(79, 40, 70, 23);

        label16.setText("Instansi :");
        label16.setName("label16"); // NOI18N
        label16.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(label16);
        label16.setBounds(0, 40, 75, 23);

        KdPetugas.setEditable(false);
        KdPetugas.setName("KdPetugas"); // NOI18N
        KdPetugas.setPreferredSize(new java.awt.Dimension(80, 23));
        KdPetugas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdPetugasKeyPressed(evt);
            }
        });
        panelisi3.add(KdPetugas);
        KdPetugas.setBounds(472, 40, 90, 23);

        NmPerusahaan.setEditable(false);
        NmPerusahaan.setName("NmPerusahaan"); // NOI18N
        NmPerusahaan.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(NmPerusahaan);
        NmPerusahaan.setBounds(151, 40, 185, 23);

        NmPetugas.setEditable(false);
        NmPetugas.setName("NmPetugas"); // NOI18N
        NmPetugas.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(NmPetugas);
        NmPetugas.setBounds(564, 40, 175, 23);

        BtnPerusahaan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPerusahaan.setMnemonic('1');
        BtnPerusahaan.setToolTipText("Alt+1");
        BtnPerusahaan.setName("BtnPerusahaan"); // NOI18N
        BtnPerusahaan.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnPerusahaan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPerusahaanActionPerformed(evt);
            }
        });
        panelisi3.add(BtnPerusahaan);
        BtnPerusahaan.setBounds(339, 40, 28, 23);

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

        Keterangan.setName("Keterangan"); // NOI18N
        Keterangan.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(Keterangan);
        Keterangan.setBounds(79, 70, 691, 23);

        label18.setText("Jatuh Tempo :");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(80, 23));
        panelisi3.add(label18);
        label18.setBounds(596, 10, 80, 23);

        JatuhTempo.setDisplayFormat("dd-MM-yyyy");
        JatuhTempo.setName("JatuhTempo"); // NOI18N
        JatuhTempo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                JatuhTempoItemStateChanged(evt);
            }
        });
        JatuhTempo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JatuhTempoKeyPressed(evt);
            }
        });
        panelisi3.add(JatuhTempo);
        JatuhTempo.setBounds(680, 10, 90, 23);

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

        label17.setText("PPN(%) :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(60, 30));
        panelisi1.add(label17);
        label17.setBounds(280, 40, 85, 23);

        BesarPPN.setEditable(false);
        BesarPPN.setText("0");
        BesarPPN.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        BesarPPN.setName("BesarPPN"); // NOI18N
        BesarPPN.setPreferredSize(new java.awt.Dimension(80, 23));
        BesarPPN.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BesarPPNKeyPressed(evt);
            }
        });
        panelisi1.add(BesarPPN);
        BesarPPN.setBounds(416, 40, 110, 23);

        TotalTagihan.setEditable(false);
        TotalTagihan.setText("0");
        TotalTagihan.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        TotalTagihan.setName("TotalTagihan"); // NOI18N
        TotalTagihan.setPreferredSize(new java.awt.Dimension(80, 23));
        TotalTagihan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TotalTagihanKeyPressed(evt);
            }
        });
        panelisi1.add(TotalTagihan);
        TotalTagihan.setBounds(619, 40, 157, 23);

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

        BesarJasaMenejemen.setEditable(false);
        BesarJasaMenejemen.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        BesarJasaMenejemen.setName("BesarJasaMenejemen"); // NOI18N
        BesarJasaMenejemen.setPreferredSize(new java.awt.Dimension(80, 23));
        BesarJasaMenejemen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BesarJasaMenejemenKeyPressed(evt);
            }
        });
        panelisi1.add(BesarJasaMenejemen);
        BesarJasaMenejemen.setBounds(171, 40, 110, 23);

        label21.setText("DPP Nilai Lain :");
        label21.setName("label21"); // NOI18N
        label21.setPreferredSize(new java.awt.Dimension(60, 30));
        panelisi1.add(label21);
        label21.setBounds(280, 10, 85, 23);

        DPPLainLain.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        DPPLainLain.setName("DPPLainLain"); // NOI18N
        DPPLainLain.setPreferredSize(new java.awt.Dimension(80, 23));
        DPPLainLain.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DPPLainLainKeyPressed(evt);
            }
        });
        panelisi1.add(DPPLainLain);
        DPPLainLain.setBounds(369, 10, 157, 23);

        PersenMenejemen.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        PersenMenejemen.setName("PersenMenejemen"); // NOI18N
        PersenMenejemen.setPreferredSize(new java.awt.Dimension(80, 23));
        PersenMenejemen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PersenMenejemenKeyPressed(evt);
            }
        });
        panelisi1.add(PersenMenejemen);
        PersenMenejemen.setBounds(124, 40, 45, 23);

        PersenPPN.setText("12");
        PersenPPN.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        PersenPPN.setName("PersenPPN"); // NOI18N
        PersenPPN.setPreferredSize(new java.awt.Dimension(80, 23));
        PersenPPN.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PersenPPNKeyPressed(evt);
            }
        });
        panelisi1.add(PersenPPN);
        PersenPPN.setBounds(369, 40, 45, 23);

        label22.setText("PPH 23(%) :");
        label22.setName("label22"); // NOI18N
        label22.setPreferredSize(new java.awt.Dimension(60, 30));
        panelisi1.add(label22);
        label22.setBounds(525, 10, 90, 23);

        PersenPPH.setText("2");
        PersenPPH.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        PersenPPH.setName("PersenPPH"); // NOI18N
        PersenPPH.setPreferredSize(new java.awt.Dimension(80, 23));
        PersenPPH.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PersenPPHKeyPressed(evt);
            }
        });
        panelisi1.add(PersenPPH);
        PersenPPH.setBounds(619, 10, 45, 23);

        BesarPPH.setEditable(false);
        BesarPPH.setText("0");
        BesarPPH.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        BesarPPH.setName("BesarPPH"); // NOI18N
        BesarPPH.setPreferredSize(new java.awt.Dimension(80, 23));
        BesarPPH.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BesarPPHKeyPressed(evt);
            }
        });
        panelisi1.add(BesarPPH);
        BesarPPH.setBounds(666, 10, 110, 23);

        internalFrame1.add(panelisi1, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents
/*
private void KdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdKeyPressed
    Valid.pindah(evt,BtnCari,Nm);
}//GEN-LAST:event_TKdKeyPressed
*/

private void NoPiutangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoPiutangKeyPressed
        Valid.pindah(evt, BtnSimpan, KdPerusahaan);
}//GEN-LAST:event_NoPiutangKeyPressed

private void TanggalPiutangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalPiutangKeyPressed
        Valid.pindah(evt,NoPiutang,KdPerusahaan);
}//GEN-LAST:event_TanggalPiutangKeyPressed

private void KdPerusahaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdPerusahaanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            NoPiutang.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            KdPetugas.requestFocus(); 
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnPerusahaanActionPerformed(null);
        }
}//GEN-LAST:event_KdPerusahaanKeyPressed

private void KdPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdPetugasKeyPressed
        
}//GEN-LAST:event_KdPetugasKeyPressed

private void BtnPerusahaanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPerusahaanActionPerformed
        DlgCariPerusahaan perusahaan=new DlgCariPerusahaan(null,false);
        perusahaan.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(perusahaan.getTable().getSelectedRow()!= -1){
                    KdPerusahaan.setText(perusahaan.getTable().getValueAt(perusahaan.getTable().getSelectedRow(),0).toString());
                    NmPerusahaan.setText(perusahaan.getTable().getValueAt(perusahaan.getTable().getSelectedRow(),1).toString());
                    BtnPerusahaan.requestFocus();
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
        
        perusahaan.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    perusahaan.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        perusahaan.isCek();
        perusahaan.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        perusahaan.setLocationRelativeTo(internalFrame1);
        perusahaan.setVisible(true); 
}//GEN-LAST:event_BtnPerusahaanActionPerformed

private void btnPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPetugasActionPerformed
    petugas.emptTeks();
    petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
    petugas.setLocationRelativeTo(internalFrame1);
    petugas.setAlwaysOnTop(false);
    petugas.setVisible(true);
}//GEN-LAST:event_btnPetugasActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        try {
            if(Valid.daysOld("./cache/piutangjasaperusahaan.iyem")<8){
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
            KdPerusahaan.requestFocus();
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
        KeuanganCariPiutangJasaPerusahaan form=new KeuanganCariPiutangJasaPerusahaan(null,false);
        form.isCek();
        form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        form.setLocationRelativeTo(internalFrame1);
        form.setAlwaysOnTop(false);
        form.setVisible(true);
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
        DlgKategoriPiutangJasaPerusahaan aplikasi=new DlgKategoriPiutangJasaPerusahaan(null,false);
        aplikasi.isCek();
        aplikasi.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        aplikasi.setLocationRelativeTo(internalFrame1);
        aplikasi.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnTambahActionPerformed

    private void BesarPPNKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BesarPPNKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            getData();
        }
    }//GEN-LAST:event_BesarPPNKeyPressed

    private void TotalTagihanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TotalTagihanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            getData();
        }
    }//GEN-LAST:event_TotalTagihanKeyPressed

    private void TanggalPiutangItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TanggalPiutangItemStateChanged
        try {
            autoNomor();
        } catch (Exception e) {
        } 
    }//GEN-LAST:event_TanggalPiutangItemStateChanged

    private void tbDokterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDokterMouseClicked
        if(tbDokter.getRowCount()!=0){
            try {
                   if((tbDokter.getSelectedColumn()==2)||(tbDokter.getSelectedColumn()==3)||(tbDokter.getSelectedColumn()==6)){                       
                        getData();                                                 
                   }else if(tbDokter.getSelectedColumn()==5){
                       try {
                           tbDokter.setValueAt(Math.round(Double.parseDouble(tbDokter.getValueAt(tbDokter.getSelectedRow(),4).toString())*
                               (Double.parseDouble(tbDokter.getValueAt(tbDokter.getSelectedRow(),5).toString())/100)),tbDokter.getSelectedRow(),6);
                       } catch (Exception e) {
                           tbDokter.setValueAt(0,tbDokter.getSelectedRow(),6);
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
        if(tbDokter.getRowCount()!=0){
            if(evt.getKeyCode()==KeyEvent.VK_ENTER){
                try {      
                   if((tbDokter.getSelectedColumn()==2)||(tbDokter.getSelectedColumn()==3)||(tbDokter.getSelectedColumn()==6)){                       
                        getData();  
                        TCari.setText("");
                        TCari.requestFocus();                                                
                   }else if(tbDokter.getSelectedColumn()==5){
                       try {
                           tbDokter.setValueAt(Math.round(Double.parseDouble(tbDokter.getValueAt(tbDokter.getSelectedRow(),4).toString())*
                               (Double.parseDouble(tbDokter.getValueAt(tbDokter.getSelectedRow(),5).toString())/100)),tbDokter.getSelectedRow(),6);
                       } catch (Exception e) {
                           tbDokter.setValueAt(0,tbDokter.getSelectedRow(),6);
                       }
                       
                       getData();
                   }else if((tbDokter.getSelectedColumn()==3)||(tbDokter.getSelectedColumn()==5)||(tbDokter.getSelectedColumn()==6)){                       
                       TCari.setText("");
                       TCari.requestFocus();
                   }
                } catch (Exception e) {
                }
            }else if(evt.getKeyCode()==KeyEvent.VK_DELETE){
                try {
                    if(tbDokter.getSelectedColumn()==2){
                        tbDokter.setValueAt("", tbDokter.getSelectedRow(),2);
                        tbDokter.setValueAt(0, tbDokter.getSelectedRow(),3);
                        tbDokter.setValueAt(0, tbDokter.getSelectedRow(),4);
                        tbDokter.setValueAt(0, tbDokter.getSelectedRow(),5);
                        tbDokter.setValueAt(0, tbDokter.getSelectedRow(),6);
                        tbDokter.setValueAt(0, tbDokter.getSelectedRow(),7);
                    }else if(tbDokter.getSelectedColumn()==3){
                        tbDokter.setValueAt(0, tbDokter.getSelectedRow(),3);
                        tbDokter.setValueAt(0, tbDokter.getSelectedRow(),4);
                        tbDokter.setValueAt(0, tbDokter.getSelectedRow(),5);
                        tbDokter.setValueAt(0, tbDokter.getSelectedRow(),6);
                        tbDokter.setValueAt(0, tbDokter.getSelectedRow(),7);
                    }
                } catch (Exception e) {
                }
            }else if(evt.getKeyCode()==KeyEvent.VK_SHIFT){
                TCari.setText("");
                TCari.requestFocus();
            }else if((evt.getKeyCode()==KeyEvent.VK_RIGHT)||(evt.getKeyCode()==KeyEvent.VK_LEFT)||(evt.getKeyCode()==KeyEvent.VK_DOWN)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                   if((tbDokter.getSelectedColumn()==2)||(tbDokter.getSelectedColumn()==3)||(tbDokter.getSelectedColumn()==6)){                       
                        getData();                                              
                   }else if(tbDokter.getSelectedColumn()==5){
                       try {
                           tbDokter.setValueAt(Math.round(Double.parseDouble(tbDokter.getValueAt(tbDokter.getSelectedRow(),4).toString())*
                               (Double.parseDouble(tbDokter.getValueAt(tbDokter.getSelectedRow(),5).toString())/100)),tbDokter.getSelectedRow(),6);
                       } catch (Exception e) {
                           tbDokter.setValueAt(0,tbDokter.getSelectedRow(),6);
                       }
                       
                       getData();
                   }else if(tbDokter.getSelectedColumn()==6){
                       getData();  
                   }
            }
        }
    }//GEN-LAST:event_tbDokterKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(NoPiutang.getText().trim().equals("")){
            Valid.textKosong(NoPiutang,"No.Piutang");
        }else if(NmPerusahaan.getText().trim().equals("")){
            Valid.textKosong(KdPerusahaan,"Instansi/Perusahaan");
        }else if(NmPetugas.getText().trim().equals("")){
            Valid.textKosong(KdPetugas,"Petugas");
        }else if(Keterangan.getText().trim().equals("")){
            Valid.textKosong(Keterangan,"Keterangan");
        }else if(TotalTagihan.getText().trim().equals("")){
            Valid.textKosong(TotalTagihan,"Total Tagihan");
        }else if(tbDokter.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        }else if(grandtotal<=0){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan masukkan piutang...!!!!");
            tbDokter.requestFocus();
        }else{
            int reply = JOptionPane.showConfirmDialog(rootPane,"Eeiiiiiits, udah bener belum data yang mau disimpan..??","Konfirmasi",JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
                Sequel.AutoComitFalse();
                sukses=true;
                if(Sequel.menyimpantf2("piutang_jasa_perusahaan","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,'Belum Lunas'","No.Piutang",16,new String[]{
                    NoPiutang.getText(),Valid.SetTgl(TanggalPiutang.getSelectedItem()+""),Valid.SetTgl(JatuhTempo.getSelectedItem()+""),KdPetugas.getText(),KdPerusahaan.getText(), 
                    Keterangan.getText(),Double.toString(grandtotal),Double.toString(persenmenejemen),Double.toString(besarmenejemen),Double.toString(dppnilailain),
                    Double.toString(persenppn),Double.toString(besarppn),Double.toString(persenpph),Double.toString(besarpph),Double.toString(totaltagihan),
                    Double.toString(totaltagihan)
                })==true){
                    jml=tbDokter.getRowCount();
                    for(i=0;i<jml;i++){
                        try {
                            if(Valid.SetAngka(tbDokter.getValueAt(i,2).toString())>0){
                                if(Sequel.menyimpantf2("detail_piutang_jasa_perusahaan","?,?,?,?,?,?,?,?","Transaksi Piutang",8,new String[]{
                                    NoPiutang.getText(),tbDokter.getValueAt(i,0).toString(),tbDokter.getValueAt(i,2).toString(),tbDokter.getValueAt(i,3).toString(), 
                                    tbDokter.getValueAt(i,4).toString(),tbDokter.getValueAt(i,5).toString(),tbDokter.getValueAt(i,6).toString(),tbDokter.getValueAt(i,7).toString()
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
                    JOptionPane.showMessageDialog(rootPane, "Gagal Menyimpan, kemungkinan Nomor Piutang/Tagihan sudah ada sebelumnya...!!");
                }
                if(sukses==true){
                    Sequel.queryu("delete from tampjurnal");
                    if(Sequel.menyimpantf2("tampjurnal","'"+Piutang_Jasa_Perusahaan+"','PIUTANG JASA PERUSAHAAN','"+totaltagihan+"','0'","Rekening")==false){
                        sukses=false;
                    }  
                    if(Sequel.menyimpantf2("tampjurnal","'"+Pendapatan_Piutang_Jasa_Perusahaan+"','PENDAPATAN PIUTANG JASA PERUSAHAAN','0','"+totaltagihan+"'","Rekening")==false){
                        sukses=false;
                    }
                    if(sukses==true){
                        sukses=jur.simpanJurnal(NoPiutang.getText(),"U","PIUTANG JASA PERUSAHAAN "+NmPerusahaan.getText().toUpperCase()+", OLEH "+akses.getkode()); 
                    }  
                }
                if(sukses==true){
                    Sequel.Commit();
                    jml=tbDokter.getRowCount();
                    for(i=0;i<jml;i++){
                        tbDokter.setValueAt("",i,2);
                        tbDokter.setValueAt(0,i,3);
                        tbDokter.setValueAt(0,i,4);
                        tbDokter.setValueAt(0,i,5);
                        tbDokter.setValueAt(0,i,6);
                        tbDokter.setValueAt(0,i,7);
                    }
                    grandtotal=0;persenmenejemen=0;besarmenejemen=0;dppnilailain=0;persenppn=0;besarppn=0;persenpph=0;besarpph=0;totaltagihan=0;
                    LSubtotal.setText("0");
                    PersenMenejemen.setText("");
                    BesarJasaMenejemen.setText("");
                    DPPLainLain.setText("");
                    PersenPPN.setText("12");
                    BesarPPN.setText("0");
                    PersenPPH.setText("2");
                    BesarPPH.setText("0");
                    TotalTagihan.setText("0");
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
        if(NoPiutang.getText().trim().equals("")){
            Valid.textKosong(NoPiutang,"No.Piutang");
        }else if(NmPerusahaan.getText().trim().equals("")){
            Valid.textKosong(KdPerusahaan,"Instansi/Perusahaan");
        }else if(NmPetugas.getText().trim().equals("")){
            Valid.textKosong(KdPetugas,"Petugas");
        }else if(Keterangan.getText().trim().equals("")){
            Valid.textKosong(Keterangan,"Keterangan");
        }else if(TotalTagihan.getText().trim().equals("")){
            Valid.textKosong(TotalTagihan,"Total Tagihan");
        }else if(tbDokter.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        }else if(grandtotal<=0){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan masukkan piutang...!!!!");
            tbDokter.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Sequel.queryu("delete from temporary where temp37='"+akses.getalamatip()+"'");
            row=tabMode.getRowCount();
            for(i=0;i<row;i++){  
                if(Valid.SetAngka(tbDokter.getValueAt(i,2).toString())>0){
                    Sequel.menyimpan("temporary","'"+i+"','"+
                                tabMode.getValueAt(i,1).toString()+"','"+
                                tabMode.getValueAt(i,2).toString()+"','"+
                                Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(i,3).toString()))+"','"+
                                Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(i,4).toString()))+"','"+
                                Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(i,5).toString()))+"','"+
                                Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(i,6).toString()))+"','"+
                                Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(i,7).toString()))+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','"+akses.getalamatip()+"'","Transaksi Pemesanan"); 
                }                    
            }
            
            Map<String, Object> param = new HashMap<>();    
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());  
            param.put("keterangan",Keterangan.getText());  
            param.put("perusahaan",NmPerusahaan.getText());  
            param.put("notagihan",NoPiutang.getText());  
            param.put("jatuhtempo",JatuhTempo.getSelectedItem().toString());  
            param.put("tanggalpiutang",TanggalPiutang.getSelectedItem().toString());  
            param.put("petugas",NmPetugas.getText());  
            param.put("jabatanpetugas",Sequel.cariIsi("select pegawai.jbtn from pegawai where pegawai.nik=?",KdPetugas.getText()));  
            param.put("grandtotal",Valid.SetAngka(grandtotal)); 
            param.put("besarmenejemen",Valid.SetAngka(besarmenejemen)); 
            param.put("persenmenejemen",persenmenejemen+""); 
            param.put("dpplain",Valid.SetAngka(dppnilailain));
            param.put("besarppn",Valid.SetAngka(besarppn)); 
            param.put("persenppn",persenppn+"");  
            param.put("besarpph",Valid.SetAngka(besarpph)); 
            param.put("persenpph",persenpph+"");  
            param.put("totaltagihan",Valid.SetAngka(totaltagihan)); 
            param.put("terbilang",Valid.terbilang(totaltagihan)); 
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            String finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",KdPetugas.getText());
            param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+NmPetugas.getText()+"\nID "+(finger.equals("")?KdPetugas.getText():finger)+"\n"+TanggalPiutang.getSelectedItem());
            Valid.MyReportqry("rptSuratPiutangJasaPerusahaan.jasper","report","::[ Tagihan Piutang Jasa Perusahaan ]::","select * from temporary where temporary.temp37='"+akses.getalamatip()+"' order by temporary.no",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
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

    private void JatuhTempoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_JatuhTempoItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_JatuhTempoItemStateChanged

    private void JatuhTempoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JatuhTempoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_JatuhTempoKeyPressed

    private void BesarJasaMenejemenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BesarJasaMenejemenKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BesarJasaMenejemenKeyPressed

    private void DPPLainLainKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DPPLainLainKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DPPLainLainKeyPressed

    private void PersenMenejemenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PersenMenejemenKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PersenMenejemenKeyPressed

    private void PersenPPNKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PersenPPNKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PersenPPNKeyPressed

    private void PersenPPHKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PersenPPHKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PersenPPHKeyPressed

    private void BesarPPHKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BesarPPHKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BesarPPHKeyPressed

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
    private widget.TextBox BesarJasaMenejemen;
    private widget.TextBox BesarPPH;
    private widget.TextBox BesarPPN;
    private widget.Button BtnAll;
    private widget.Button BtnCari;
    private widget.Button BtnCari1;
    private widget.Button BtnKeluar;
    private widget.Button BtnPerusahaan;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.Button BtnTambah;
    private widget.TextBox DPPLainLain;
    private widget.Tanggal JatuhTempo;
    private widget.TextBox KdPerusahaan;
    private widget.TextBox KdPetugas;
    private widget.TextBox Keterangan;
    private widget.Label LSubtotal;
    private widget.TextBox NmPerusahaan;
    private widget.TextBox NmPetugas;
    private widget.TextBox NoPiutang;
    private widget.TextBox PersenMenejemen;
    private widget.TextBox PersenPPH;
    private widget.TextBox PersenPPN;
    private widget.TextBox TCari;
    private widget.Tanggal TanggalPiutang;
    private widget.TextBox TotalTagihan;
    private widget.Button btnPetugas;
    private widget.InternalFrame internalFrame1;
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
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi3;
    private widget.ScrollPane scrollPane1;
    private widget.Table tbDokter;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
       try{
            file=new File("./cache/piutangjasaperusahaan.iyem");
            file.createNewFile();
            fileWriter = new FileWriter(file);
            StringBuilder iyembuilder = new StringBuilder();
            ps=koneksi.prepareStatement(
                "select * from kategori_piutang_jasa_perusahaan order by kategori_piutang_jasa_perusahaan.kode_kategori");
            try {  
                rs=ps.executeQuery();
                while(rs.next()){
                    iyembuilder.append("{\"Kode\":\"").append(rs.getString(1)).append("\",\"Kategori\":\"").append(rs.getString(2)).append("\"},");
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
                fileWriter.write("{\"piutangjasaperusahaan\":["+iyembuilder+"]}");
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
            String[] kode,kategori;
            double[] harga,jumlah,subtotal,diskon,besardiskon,jmltotal;
            row=tbDokter.getRowCount();
            jml=0;
            for(i=0;i<row;i++){
                try {
                    if(Double.parseDouble(tbDokter.getValueAt(i,2).toString())>0){
                        jml++;
                    }
                } catch (Exception e) {
                    jml=jml+0;
                }            
            }

            kode=new String[jml];
            kategori=new String[jml];
            harga=new double[jml];
            jumlah=new double[jml];
            subtotal=new double[jml];
            diskon=new double[jml];
            besardiskon=new double[jml];
            jmltotal=new double[jml];
            index=0;        
            for(i=0;i<row;i++){
                try {
                    if(Double.parseDouble(tbDokter.getValueAt(i,2).toString())>0){
                        kode[index]=tbDokter.getValueAt(i,0).toString();
                        kategori[index]=tbDokter.getValueAt(i,1).toString();
                        try {
                            jumlah[index]=Double.parseDouble(tbDokter.getValueAt(i,2).toString());
                        } catch (Exception e) {
                            jumlah[index]=0;
                        }
                        try {
                            harga[index]=Double.parseDouble(tbDokter.getValueAt(i,3).toString());
                        } catch (Exception e) {
                            harga[index]=0;
                        }
                        try {
                            subtotal[index]=Double.parseDouble(tbDokter.getValueAt(i,4).toString());
                        } catch (Exception e) {
                            subtotal[index]=0;
                        }
                        try {
                            diskon[index]=Double.parseDouble(tbDokter.getValueAt(i,5).toString());
                        } catch (Exception e) {
                            diskon[index]=0;
                        }
                        try {
                            besardiskon[index]=Double.parseDouble(tbDokter.getValueAt(i,6).toString());
                        } catch (Exception e) {
                            besardiskon[index]=0;
                        }
                        try {
                            jmltotal[index]=Double.parseDouble(tbDokter.getValueAt(i,7).toString());
                        } catch (Exception e) {
                            jmltotal[index]=0;
                        }
                        index++;
                    }
                } catch (Exception e) {
                }
            }
            Valid.tabelKosong(tabMode);
            for(i=0;i<jml;i++){
                tabMode.addRow(new Object[]{kode[i],kategori[i],jumlah[i],harga[i],subtotal[i],diskon[i],besardiskon[i],jmltotal[i]});
            }
            
            kode=null;
            kategori=null;
            harga=null;
            jumlah=null;
            subtotal=null;
            diskon=null;
            besardiskon=null;
            jmltotal=null;
            
            myObj = new FileReader("./cache/piutangjasaperusahaan.iyem");
            root = mapper.readTree(myObj);
            response = root.path("piutangjasaperusahaan");
            if(response.isArray()){
                if(TCari.getText().trim().equals("")){
                    for(JsonNode list:response){
                        tabMode.addRow(new Object[]{
                            list.path("Kode").asText(),list.path("Kategori").asText(),"",0,0,0,0,0
                        });
                    }
                }else{
                    for(JsonNode list:response){
                        if(list.path("Kategori").asText().toLowerCase().contains(TCari.getText().toLowerCase())||list.path("Kode").asText().toLowerCase().contains(TCari.getText().toLowerCase())){
                            tabMode.addRow(new Object[]{
                                list.path("Kode").asText(),list.path("Kategori").asText(),"",0,0,0,0,0
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
            if(!tbDokter.getValueAt(row,2).toString().equals("")){
                double jumlahitem=0,hargaitem=0,totalitem=0,diskonitem=0,totalbersih=0;
                try {
                    if(Valid.SetAngka(tbDokter.getValueAt(row,2).toString())>0){
                        try {
                            jumlahitem = Double.parseDouble(tbDokter.getValueAt(row,2).toString());
                        } catch (Exception e) {
                            jumlahitem = 0;
                        }
                        try {
                            hargaitem = Double.parseDouble(tbDokter.getValueAt(row,3).toString());
                        } catch (Exception e) {
                            hargaitem = 0;
                        }
                        try {
                            totalitem   = jumlahitem*hargaitem;
                        } catch (Exception e) {
                            totalitem = 0;
                        }
                        try {
                            diskonitem  = Double.parseDouble(tbDokter.getValueAt(row,6).toString());
                        } catch (Exception e) {
                            diskonitem = 0;
                        }
                        try {
                            totalbersih = totalitem-diskonitem;
                        } catch (Exception e) {
                            totalbersih = 0;
                        }
                        
                        tbDokter.setValueAt(totalitem, row,4);  
                        tbDokter.setValueAt(totalbersih, row,7);  
                    } 
                } catch (Exception e) {
                    tbDokter.setValueAt(jumlahitem,tbDokter.getSelectedRow(),2);
                    tbDokter.setValueAt(hargaitem,tbDokter.getSelectedRow(),3);
                    tbDokter.setValueAt(totalitem,tbDokter.getSelectedRow(),4);
                    tbDokter.setValueAt(diskonitem,tbDokter.getSelectedRow(),6);
                    tbDokter.setValueAt(totalbersih,tbDokter.getSelectedRow(),7);     
                }
            }else{
                tbDokter.setValueAt(0, tbDokter.getSelectedRow(),3);
                tbDokter.setValueAt(0, tbDokter.getSelectedRow(),4);
                tbDokter.setValueAt(0, tbDokter.getSelectedRow(),5);
                tbDokter.setValueAt(0, tbDokter.getSelectedRow(),6);
                tbDokter.setValueAt(0, tbDokter.getSelectedRow(),7);  
            }
        }
             
        grandtotal=0;persenmenejemen=0;besarmenejemen=0;dppnilailain=0;persenppn=0;besarppn=0;persenpph=0;besarpph=0;totaltagihan=0;
        jml=tbDokter.getRowCount();
        for(i=0;i<jml;i++){                 
            try {
                x=Double.parseDouble(tbDokter.getValueAt(i,7).toString());
            } catch (Exception e) {
                tbDokter.setValueAt(0,tbDokter.getSelectedRow(),7);
                x=0;                
            }
            grandtotal=grandtotal+x;   
        }
        LSubtotal.setText(Valid.SetAngka(grandtotal));
        
        if(!PersenMenejemen.getText().trim().equals("")){
            persenmenejemen=Valid.SetAngka(PersenMenejemen.getText());
        }
        besarmenejemen=Math.round((persenmenejemen/100)*grandtotal);
        BesarJasaMenejemen.setText(Valid.SetAngka(besarmenejemen));
        
        if(!DPPLainLain.getText().trim().equals("")){
            dppnilailain=Valid.SetAngka(DPPLainLain.getText());
        }
        
        if(!PersenPPN.getText().trim().equals("")){
            persenppn=Valid.SetAngka(PersenPPN.getText());
        }
        besarppn=Math.round((persenppn/100)*grandtotal);
        BesarPPN.setText(Valid.SetAngka(besarppn));
        
        if(!PersenPPH.getText().trim().equals("")){
            persenpph=Valid.SetAngka(PersenPPH.getText());
        }
        besarpph=Math.round((persenpph/100)*grandtotal);
        BesarPPH.setText(Valid.SetAngka(besarpph));
       
        totaltagihan=grandtotal+besarmenejemen+dppnilailain+besarppn+besarpph;
        TotalTagihan.setText(Valid.SetAngka(totaltagihan));
    }   
    
    public void isCek(){
        autoNomor();
        TCari.requestFocus();
        if(akses.getjml2()>=1){
            BtnTambah.setEnabled(akses.getkategori_piutang_jasa_perusahaan());
            KdPetugas.setEditable(false);
            btnPetugas.setEnabled(false);
            KdPetugas.setText(akses.getkode());
            BtnSimpan.setEnabled(akses.getpiutang_jasa_perusahaan());
            NmPetugas.setText(petugas.tampil3(KdPetugas.getText()));
        }        
    }
    
    private void autoNomor() {
        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(piutang_jasa_perusahaan.no_piutang,3),signed)),0) from piutang_jasa_perusahaan where piutang_jasa_perusahaan.tgl_piutang='"+Valid.SetTgl(TanggalPiutang.getSelectedItem()+"")+"' ",
                "PJP"+TanggalPiutang.getSelectedItem().toString().substring(6,10)+TanggalPiutang.getSelectedItem().toString().substring(3,5)+TanggalPiutang.getSelectedItem().toString().substring(0,2),3,NoPiutang); 
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
                        rs.getString("jumlah"),rs.getString("satbesar"),rs.getString(2),
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

package tranfusidarah;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariPetugas;

public class UTDPemisahanDarah extends javax.swing.JDialog {
    private final DefaultTableModel tabMode,tabModeMedis,tabModeNonMedis,tabModeKomponen;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement psdonor,pskomponen,ps,ps2,pspemisahan,pscekmedis,psceknonmedis,pscekkomponen;
    private ResultSet rs,rs2;
    private int i,index=0,row=0,jml=0;
    private Calendar cal;
    private SimpleDateFormat sdf;
    private boolean[] pilih; 
    private String[] kodebarang,namabarang,jumlah,satuan,stokasal,hbeli,total;
    private DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    private String aktifkan="",
            sqlpscekmedis="select utd_penggunaan_medis_pemisahan_komponen.kode_brng,databarang.nama_brng,utd_penggunaan_medis_pemisahan_komponen.jml,utd_penggunaan_medis_pemisahan_komponen.harga,"+
                            "utd_penggunaan_medis_pemisahan_komponen.total,databarang.kode_sat from utd_penggunaan_medis_pemisahan_komponen inner join databarang "+
                            "on utd_penggunaan_medis_pemisahan_komponen.kode_brng=databarang.kode_brng where utd_penggunaan_medis_pemisahan_komponen.no_donor=?",
            sqlpsceknonmedis="select utd_penggunaan_penunjang_pemisahan_komponen.kode_brng,ipsrsbarang.nama_brng,utd_penggunaan_penunjang_pemisahan_komponen.jml,utd_penggunaan_penunjang_pemisahan_komponen.harga,"+
                            "utd_penggunaan_penunjang_pemisahan_komponen.total,ipsrsbarang.kode_sat from utd_penggunaan_penunjang_pemisahan_komponen inner join ipsrsbarang "+
                            "on utd_penggunaan_penunjang_pemisahan_komponen.kode_brng=ipsrsbarang.kode_brng where utd_penggunaan_penunjang_pemisahan_komponen.no_donor=?";

    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public UTDPemisahanDarah(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
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
        tbMedis.setDefaultRenderer(Object.class, new WarnaTable());
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
        tbNonMedis.setDefaultRenderer(Object.class, new WarnaTable());

        Object[] row={
            "Nomor","Nama Pendonor","Tgl.Donor","Dinas","J.K.",
            "Umur","Alamat","G.D.","Resus","Tensi","No.Bag","No.Telp"
        };
        tabMode=new DefaultTableModel(null,row){
            @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}            
        };
        tbPemisahan.setModel(tabMode);

        tbPemisahan.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbPemisahan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 12; i++) {
            TableColumn column = tbPemisahan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(90);
            }else if(i==1){
                column.setPreferredWidth(150);
            }else if(i==2){
                column.setPreferredWidth(70);
            }else if(i==3){
                column.setPreferredWidth(37);
            }else if(i==4){
                column.setPreferredWidth(27);
            }else if(i==5){
                column.setPreferredWidth(35);
            }else if(i==6){
                column.setPreferredWidth(340);
            }else if(i==7){
                column.setPreferredWidth(27);
            }else if(i==8){
                column.setPreferredWidth(40);
            }else if(i==9){
                column.setPreferredWidth(42);
            }else if(i==10){
                column.setPreferredWidth(42);
            }else if(i==11){
                column.setPreferredWidth(70);
            }
        }
        tbPemisahan.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeKomponen=new DefaultTableModel(null,new String[]{"P","Kode","Nama Komponen","Lama(Hari)"}){
            @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
            }
            Class[] types = new Class[]{
                java.lang.Boolean.class,java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        tbKomponen.setModel(tabModeKomponen);

        tbKomponen.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbKomponen.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 4; i++) {
            TableColumn column = tbKomponen.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(55);
            }else if(i==2){
                column.setPreferredWidth(255);
            }else if(i==3){
                column.setPreferredWidth(65);
            }
        }
        tbKomponen.setDefaultRenderer(Object.class, new WarnaTable());
        
        petugas.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(petugas.getTable().getSelectedRow()!= -1){ 
                    KodePetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                    NamaPetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                    KodePetugas.requestFocus();                    
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
            TCariMedis.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(TCariMedis.getText().length()>2){
                        tampilMedis();
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(TCariMedis.getText().length()>2){
                        tampilMedis();
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(TCariMedis.getText().length()>2){
                        tampilMedis();
                    }
                }
            });
            TCariNonMedis.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(TCariNonMedis.getText().length()>2){
                        tampilNonMedis();
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(TCariNonMedis.getText().length()>2){
                        tampilNonMedis();
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(TCariNonMedis.getText().length()>2){
                        tampilNonMedis();
                    }
                }
            });
            Komponen.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(Komponen.getText().length()>2){
                        tampilKomponen();
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(Komponen.getText().length()>2){
                        tampilKomponen();
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(Komponen.getText().length()>2){
                        tampilKomponen();
                    }
                }
            });
            
            TCari.setDocument(new batasInput((byte)100).getKata(TCari));
            TCariMedis.setDocument(new batasInput((byte)100).getKata(TCariMedis));
            TCariNonMedis.setDocument(new batasInput((byte)100).getKata(TCariNonMedis));
            Komponen.setDocument(new batasInput((byte)100).getKata(Komponen));
            KodePetugas.setDocument(new batasInput((byte)20).getKata(KodePetugas));
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

        DlgInput = new javax.swing.JDialog();
        internalFrame2 = new widget.InternalFrame();
        panelisi5 = new widget.panelisi();
        jPanel3 = new javax.swing.JPanel();
        panelisi6 = new widget.panelisi();
        label11 = new widget.Label();
        TCariMedis = new widget.TextBox();
        BtnCari2 = new widget.Button();
        BtnAll1 = new widget.Button();
        Scroll1 = new widget.ScrollPane();
        tbMedis = new widget.Table();
        jPanel4 = new javax.swing.JPanel();
        panelisi7 = new widget.panelisi();
        label12 = new widget.Label();
        TCariNonMedis = new widget.TextBox();
        BtnCari3 = new widget.Button();
        BtnAll2 = new widget.Button();
        Scroll2 = new widget.ScrollPane();
        tbNonMedis = new widget.Table();
        panelisi4 = new widget.panelisi();
        label34 = new widget.Label();
        NomorDonor = new widget.TextBox();
        label32 = new widget.Label();
        Tanggal = new widget.Tanggal();
        jLabel8 = new widget.Label();
        Dinas = new widget.ComboBox();
        KodePetugas = new widget.TextBox();
        NamaPetugas = new widget.TextBox();
        btnPetugasAftap = new widget.Button();
        label18 = new widget.Label();
        label19 = new widget.Label();
        BtnCariKomponen = new widget.Button();
        Scroll3 = new widget.ScrollPane();
        tbKomponen = new widget.Table();
        btnTambahKomponen = new widget.Button();
        Komponen = new widget.TextBox();
        BtnAllKomponen = new widget.Button();
        panelisi3 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnTutup = new widget.Button();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        ppTampilkanBHPMedis = new javax.swing.JMenuItem();
        ppTampilkanBHPPenunjang = new javax.swing.JMenuItem();
        ppTampilkanBHPPenunjangDanMedis = new javax.swing.JMenuItem();
        ppHapusBHPMedis = new javax.swing.JMenuItem();
        ppHapusBHPNonMedis = new javax.swing.JMenuItem();
        ppHapusBHPMedisDanNonMedis = new javax.swing.JMenuItem();
        ppHapusPemisahan = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        jPanel2 = new javax.swing.JPanel();
        panelisi2 = new widget.panelisi();
        jLabel20 = new widget.Label();
        TanggalCari1 = new widget.Tanggal();
        jLabel21 = new widget.Label();
        TanggalCari2 = new widget.Tanggal();
        label9 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        panelisi1 = new widget.panelisi();
        BtnTambah = new widget.Button();
        BtnAll = new widget.Button();
        BtnPrint = new widget.Button();
        label10 = new widget.Label();
        LCount = new widget.Label();
        BtnKeluar = new widget.Button();
        scrollPane1 = new widget.ScrollPane();
        tbPemisahan = new widget.Table();

        DlgInput.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        DlgInput.setName("DlgInput"); // NOI18N
        DlgInput.setUndecorated(true);
        DlgInput.setResizable(false);
        DlgInput.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                DlgInputWindowOpened(evt);
            }
        });

        internalFrame2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(237, 242, 232)), "::[ Input Data Pemisahan Komponen Darah ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(70, 70, 70))); // NOI18N
        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi5.setBorder(null);
        panelisi5.setName("panelisi5"); // NOI18N
        panelisi5.setPreferredSize(new java.awt.Dimension(450, 77));
        panelisi5.setLayout(new java.awt.GridLayout(2, 0));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(237, 242, 232)), ".: Penggunaan BHP Medis ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(70, 70, 70))); // NOI18N
        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(300, 102));
        jPanel3.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi6.setBorder(null);
        panelisi6.setName("panelisi6"); // NOI18N
        panelisi6.setPreferredSize(new java.awt.Dimension(100, 43));
        panelisi6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        label11.setText("Key Word :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi6.add(label11);

        TCariMedis.setToolTipText("Alt+C");
        TCariMedis.setName("TCariMedis"); // NOI18N
        TCariMedis.setPreferredSize(new java.awt.Dimension(290, 23));
        TCariMedis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariMedisKeyPressed(evt);
            }
        });
        panelisi6.add(TCariMedis);

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
        panelisi6.add(BtnCari2);

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
        panelisi6.add(BtnAll1);

        jPanel3.add(panelisi6, java.awt.BorderLayout.PAGE_END);

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

        panelisi5.add(jPanel3);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(237, 242, 232)), ".: Penggunaan BHP Non Medis ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(70, 70, 70))); // NOI18N
        jPanel4.setName("jPanel4"); // NOI18N
        jPanel4.setOpaque(false);
        jPanel4.setPreferredSize(new java.awt.Dimension(300, 202));
        jPanel4.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi7.setBorder(null);
        panelisi7.setName("panelisi7"); // NOI18N
        panelisi7.setPreferredSize(new java.awt.Dimension(100, 43));
        panelisi7.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        label12.setText("Key Word :");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi7.add(label12);

        TCariNonMedis.setToolTipText("Alt+C");
        TCariNonMedis.setName("TCariNonMedis"); // NOI18N
        TCariNonMedis.setPreferredSize(new java.awt.Dimension(290, 23));
        TCariNonMedis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariNonMedisKeyPressed(evt);
            }
        });
        panelisi7.add(TCariNonMedis);

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
        panelisi7.add(BtnCari3);

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
        panelisi7.add(BtnAll2);

        jPanel4.add(panelisi7, java.awt.BorderLayout.PAGE_END);

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

        panelisi5.add(jPanel4);

        internalFrame2.add(panelisi5, java.awt.BorderLayout.EAST);

        panelisi4.setName("panelisi4"); // NOI18N
        panelisi4.setPreferredSize(new java.awt.Dimension(100, 77));
        panelisi4.setLayout(null);

        label34.setText("Nomor :");
        label34.setName("label34"); // NOI18N
        label34.setPreferredSize(new java.awt.Dimension(35, 23));
        panelisi4.add(label34);
        label34.setBounds(0, 12, 75, 23);

        NomorDonor.setEditable(false);
        NomorDonor.setHighlighter(null);
        NomorDonor.setName("NomorDonor"); // NOI18N
        NomorDonor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NomorDonorKeyPressed(evt);
            }
        });
        panelisi4.add(NomorDonor);
        NomorDonor.setBounds(78, 12, 120, 23);

        label32.setText("Tanggal :");
        label32.setName("label32"); // NOI18N
        label32.setPreferredSize(new java.awt.Dimension(35, 23));
        panelisi4.add(label32);
        label32.setBounds(205, 12, 57, 23);

        Tanggal.setEditable(false);
        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "21-05-2019" }));
        Tanggal.setDisplayFormat("dd-MM-yyyy");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeyPressed(evt);
            }
        });
        panelisi4.add(Tanggal);
        Tanggal.setBounds(265, 12, 90, 23);

        jLabel8.setText("Dinas :");
        jLabel8.setName("jLabel8"); // NOI18N
        panelisi4.add(jLabel8);
        jLabel8.setBounds(365, 12, 50, 23);

        Dinas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Pagi", "Siang", "Sore", "Malam" }));
        Dinas.setName("Dinas"); // NOI18N
        Dinas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DinasKeyPressed(evt);
            }
        });
        panelisi4.add(Dinas);
        Dinas.setBounds(418, 12, 90, 23);

        KodePetugas.setName("KodePetugas"); // NOI18N
        KodePetugas.setPreferredSize(new java.awt.Dimension(80, 23));
        KodePetugas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodePetugasKeyPressed(evt);
            }
        });
        panelisi4.add(KodePetugas);
        KodePetugas.setBounds(78, 42, 110, 23);

        NamaPetugas.setEditable(false);
        NamaPetugas.setName("NamaPetugas"); // NOI18N
        NamaPetugas.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi4.add(NamaPetugas);
        NamaPetugas.setBounds(190, 42, 287, 23);

        btnPetugasAftap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPetugasAftap.setMnemonic('1');
        btnPetugasAftap.setToolTipText("Alt+1");
        btnPetugasAftap.setName("btnPetugasAftap"); // NOI18N
        btnPetugasAftap.setPreferredSize(new java.awt.Dimension(28, 23));
        btnPetugasAftap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPetugasAftapActionPerformed(evt);
            }
        });
        panelisi4.add(btnPetugasAftap);
        btnPetugasAftap.setBounds(480, 42, 28, 23);

        label18.setText("Komponen :");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(65, 23));
        panelisi4.add(label18);
        label18.setBounds(0, 72, 75, 23);

        label19.setText("Petugas :");
        label19.setName("label19"); // NOI18N
        label19.setPreferredSize(new java.awt.Dimension(65, 23));
        panelisi4.add(label19);
        label19.setBounds(0, 42, 75, 23);

        BtnCariKomponen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCariKomponen.setMnemonic('1');
        BtnCariKomponen.setToolTipText("Alt+1");
        BtnCariKomponen.setName("BtnCariKomponen"); // NOI18N
        BtnCariKomponen.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCariKomponen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariKomponenActionPerformed(evt);
            }
        });
        panelisi4.add(BtnCariKomponen);
        BtnCariKomponen.setBounds(418, 72, 28, 23);

        Scroll3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)));
        Scroll3.setName("Scroll3"); // NOI18N
        Scroll3.setOpaque(true);

        tbKomponen.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbKomponen.setName("tbKomponen"); // NOI18N
        tbKomponen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbKomponenKeyPressed(evt);
            }
        });
        Scroll3.setViewportView(tbKomponen);

        panelisi4.add(Scroll3);
        Scroll3.setBounds(78, 98, 430, 220);

        btnTambahKomponen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/plus_16.png"))); // NOI18N
        btnTambahKomponen.setMnemonic('2');
        btnTambahKomponen.setToolTipText("Alt+2");
        btnTambahKomponen.setName("btnTambahKomponen"); // NOI18N
        btnTambahKomponen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahKomponenActionPerformed(evt);
            }
        });
        panelisi4.add(btnTambahKomponen);
        btnTambahKomponen.setBounds(480, 72, 28, 23);

        Komponen.setHighlighter(null);
        Komponen.setName("Komponen"); // NOI18N
        Komponen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KomponenKeyPressed(evt);
            }
        });
        panelisi4.add(Komponen);
        Komponen.setBounds(78, 72, 337, 23);

        BtnAllKomponen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAllKomponen.setMnemonic('2');
        BtnAllKomponen.setToolTipText("Alt+2");
        BtnAllKomponen.setName("BtnAllKomponen"); // NOI18N
        BtnAllKomponen.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnAllKomponen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAllKomponenActionPerformed(evt);
            }
        });
        panelisi4.add(BtnAllKomponen);
        BtnAllKomponen.setBounds(449, 72, 28, 23);

        internalFrame2.add(panelisi4, java.awt.BorderLayout.CENTER);

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(100, 53));
        panelisi3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16i.png"))); // NOI18N
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
        panelisi3.add(BtnSimpan);

        BtnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Cancel-2-16x16.png"))); // NOI18N
        BtnBatal.setMnemonic('B');
        BtnBatal.setText("Batal");
        BtnBatal.setToolTipText("Alt+B");
        BtnBatal.setName("BtnBatal"); // NOI18N
        BtnBatal.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBatalActionPerformed(evt);
            }
        });
        panelisi3.add(BtnBatal);

        BtnTutup.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/cross.png"))); // NOI18N
        BtnTutup.setMnemonic('T');
        BtnTutup.setText("Tutup");
        BtnTutup.setToolTipText("Alt+T");
        BtnTutup.setName("BtnTutup"); // NOI18N
        BtnTutup.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnTutup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTutupActionPerformed(evt);
            }
        });
        panelisi3.add(BtnTutup);

        internalFrame2.add(panelisi3, java.awt.BorderLayout.PAGE_END);

        DlgInput.getContentPane().add(internalFrame2, java.awt.BorderLayout.CENTER);

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        ppTampilkanBHPMedis.setBackground(new java.awt.Color(255, 255, 254));
        ppTampilkanBHPMedis.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppTampilkanBHPMedis.setForeground(new java.awt.Color(70, 70, 70));
        ppTampilkanBHPMedis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppTampilkanBHPMedis.setText("Tampilkan Penggunaan BHP Medis");
        ppTampilkanBHPMedis.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppTampilkanBHPMedis.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppTampilkanBHPMedis.setName("ppTampilkanBHPMedis"); // NOI18N
        ppTampilkanBHPMedis.setPreferredSize(new java.awt.Dimension(280, 25));
        ppTampilkanBHPMedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppTampilkanBHPMedisActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppTampilkanBHPMedis);

        ppTampilkanBHPPenunjang.setBackground(new java.awt.Color(255, 255, 254));
        ppTampilkanBHPPenunjang.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppTampilkanBHPPenunjang.setForeground(new java.awt.Color(70, 70, 70));
        ppTampilkanBHPPenunjang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppTampilkanBHPPenunjang.setText("Tampilkan Penggunaan BHP Non Medis");
        ppTampilkanBHPPenunjang.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppTampilkanBHPPenunjang.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppTampilkanBHPPenunjang.setName("ppTampilkanBHPPenunjang"); // NOI18N
        ppTampilkanBHPPenunjang.setPreferredSize(new java.awt.Dimension(280, 25));
        ppTampilkanBHPPenunjang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppTampilkanBHPPenunjangActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppTampilkanBHPPenunjang);

        ppTampilkanBHPPenunjangDanMedis.setBackground(new java.awt.Color(255, 255, 254));
        ppTampilkanBHPPenunjangDanMedis.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppTampilkanBHPPenunjangDanMedis.setForeground(new java.awt.Color(70, 70, 70));
        ppTampilkanBHPPenunjangDanMedis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppTampilkanBHPPenunjangDanMedis.setText("Tampilkan Penggunaan BHP Medis & Non Medis");
        ppTampilkanBHPPenunjangDanMedis.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppTampilkanBHPPenunjangDanMedis.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppTampilkanBHPPenunjangDanMedis.setName("ppTampilkanBHPPenunjangDanMedis"); // NOI18N
        ppTampilkanBHPPenunjangDanMedis.setPreferredSize(new java.awt.Dimension(280, 25));
        ppTampilkanBHPPenunjangDanMedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppTampilkanBHPPenunjangDanMedisActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppTampilkanBHPPenunjangDanMedis);

        ppHapusBHPMedis.setBackground(new java.awt.Color(255, 255, 254));
        ppHapusBHPMedis.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppHapusBHPMedis.setForeground(new java.awt.Color(70, 70, 70));
        ppHapusBHPMedis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppHapusBHPMedis.setText("Hapus Penggunaan BHP Medis");
        ppHapusBHPMedis.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppHapusBHPMedis.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppHapusBHPMedis.setName("ppHapusBHPMedis"); // NOI18N
        ppHapusBHPMedis.setPreferredSize(new java.awt.Dimension(280, 25));
        ppHapusBHPMedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppHapusBHPMedisActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppHapusBHPMedis);

        ppHapusBHPNonMedis.setBackground(new java.awt.Color(255, 255, 254));
        ppHapusBHPNonMedis.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppHapusBHPNonMedis.setForeground(new java.awt.Color(70, 70, 70));
        ppHapusBHPNonMedis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppHapusBHPNonMedis.setText("Hapus Penggunaan BHP Non Medis");
        ppHapusBHPNonMedis.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppHapusBHPNonMedis.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppHapusBHPNonMedis.setName("ppHapusBHPNonMedis"); // NOI18N
        ppHapusBHPNonMedis.setPreferredSize(new java.awt.Dimension(280, 25));
        ppHapusBHPNonMedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppHapusBHPNonMedisActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppHapusBHPNonMedis);

        ppHapusBHPMedisDanNonMedis.setBackground(new java.awt.Color(255, 255, 254));
        ppHapusBHPMedisDanNonMedis.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppHapusBHPMedisDanNonMedis.setForeground(new java.awt.Color(70, 70, 70));
        ppHapusBHPMedisDanNonMedis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppHapusBHPMedisDanNonMedis.setText("Hapus Penggunaan BHP Medis & Non Medis");
        ppHapusBHPMedisDanNonMedis.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppHapusBHPMedisDanNonMedis.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppHapusBHPMedisDanNonMedis.setName("ppHapusBHPMedisDanNonMedis"); // NOI18N
        ppHapusBHPMedisDanNonMedis.setPreferredSize(new java.awt.Dimension(280, 25));
        ppHapusBHPMedisDanNonMedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppHapusBHPMedisDanNonMedisActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppHapusBHPMedisDanNonMedis);

        ppHapusPemisahan.setBackground(new java.awt.Color(255, 255, 254));
        ppHapusPemisahan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppHapusPemisahan.setForeground(new java.awt.Color(70, 70, 70));
        ppHapusPemisahan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppHapusPemisahan.setText("Hapus Pemisahan Komponen");
        ppHapusPemisahan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppHapusPemisahan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppHapusPemisahan.setName("ppHapusPemisahan"); // NOI18N
        ppHapusPemisahan.setPreferredSize(new java.awt.Dimension(280, 25));
        ppHapusPemisahan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppHapusPemisahanActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppHapusPemisahan);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Pemisahan Komponen Darah ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(70, 70, 70))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        jPanel2.setName("jPanel2"); // NOI18N
        jPanel2.setOpaque(false);
        jPanel2.setPreferredSize(new java.awt.Dimension(816, 100));
        jPanel2.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi2.setBackground(new java.awt.Color(255, 150, 255));
        panelisi2.setName("panelisi2"); // NOI18N
        panelisi2.setPreferredSize(new java.awt.Dimension(100, 44));
        panelisi2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        jLabel20.setText("Tgl.Donor :");
        jLabel20.setName("jLabel20"); // NOI18N
        jLabel20.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi2.add(jLabel20);

        TanggalCari1.setForeground(new java.awt.Color(50, 70, 50));
        TanggalCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "21-05-2019" }));
        TanggalCari1.setDisplayFormat("dd-MM-yyyy");
        TanggalCari1.setName("TanggalCari1"); // NOI18N
        TanggalCari1.setOpaque(false);
        TanggalCari1.setPreferredSize(new java.awt.Dimension(95, 23));
        panelisi2.add(TanggalCari1);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("s.d");
        jLabel21.setName("jLabel21"); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(24, 23));
        panelisi2.add(jLabel21);

        TanggalCari2.setForeground(new java.awt.Color(50, 70, 50));
        TanggalCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "21-05-2019" }));
        TanggalCari2.setDisplayFormat("dd-MM-yyyy");
        TanggalCari2.setName("TanggalCari2"); // NOI18N
        TanggalCari2.setOpaque(false);
        TanggalCari2.setPreferredSize(new java.awt.Dimension(95, 23));
        panelisi2.add(TanggalCari2);

        label9.setText("Key Word :");
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi2.add(label9);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(230, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelisi2.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('1');
        BtnCari.setToolTipText("Alt+1");
        BtnCari.setName("BtnCari"); // NOI18N
        BtnCari.setPreferredSize(new java.awt.Dimension(28, 23));
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
        panelisi2.add(BtnCari);

        jPanel2.add(panelisi2, java.awt.BorderLayout.PAGE_START);

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 44));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        BtnTambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/plus_16.png"))); // NOI18N
        BtnTambah.setMnemonic('S');
        BtnTambah.setText("Pisahkan");
        BtnTambah.setToolTipText("Alt+S");
        BtnTambah.setName("BtnTambah"); // NOI18N
        BtnTambah.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTambahActionPerformed(evt);
            }
        });
        BtnTambah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnTambahKeyPressed(evt);
            }
        });
        panelisi1.add(BtnTambah);

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('M');
        BtnAll.setText("Semua");
        BtnAll.setToolTipText("Alt+M");
        BtnAll.setName("BtnAll"); // NOI18N
        BtnAll.setPreferredSize(new java.awt.Dimension(100, 30));
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

        label10.setText("Record :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(105, 23));
        panelisi1.add(label10);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(100, 23));
        panelisi1.add(LCount);

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

        jPanel2.add(panelisi1, java.awt.BorderLayout.CENTER);

        internalFrame1.add(jPanel2, java.awt.BorderLayout.PAGE_END);

        scrollPane1.setComponentPopupMenu(jPopupMenu1);
        scrollPane1.setName("scrollPane1"); // NOI18N
        scrollPane1.setOpaque(true);

        tbPemisahan.setAutoCreateRowSorter(true);
        tbPemisahan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbPemisahan.setComponentPopupMenu(jPopupMenu1);
        tbPemisahan.setName("tbPemisahan"); // NOI18N
        tbPemisahan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPemisahanMouseClicked(evt);
            }
        });
        tbPemisahan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbPemisahanKeyPressed(evt);
            }
        });
        scrollPane1.setViewportView(tbPemisahan);

        internalFrame1.add(scrollPane1, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            tbPemisahan.requestFocus();
        }
}//GEN-LAST:event_TCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        aktifkan="";
        tampil();
}//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari, BtnAll);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    private void tbPemisahanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPemisahanMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbPemisahanMouseClicked

    private void tbPemisahanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbPemisahanKeyPressed
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
}//GEN-LAST:event_tbPemisahanKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        if(tabMode.getRowCount()==0){            
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
           // BtnBatal.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Sequel.queryu("truncate table temporary");
            jml=tabMode.getRowCount();
            for(i=0;i<jml;i++){  
                Sequel.menyimpan("temporary","'0','"+
                                tabMode.getValueAt(i,0).toString()+"','"+
                                tabMode.getValueAt(i,1).toString()+"','"+
                                tabMode.getValueAt(i,2).toString()+"','"+
                                tabMode.getValueAt(i,3).toString()+"','"+
                                tabMode.getValueAt(i,4).toString()+"','"+
                                tabMode.getValueAt(i,5).toString()+"','"+
                                tabMode.getValueAt(i,6).toString()+"','"+
                                tabMode.getValueAt(i,7).toString()+"','"+
                                tabMode.getValueAt(i,8).toString()+"','"+
                                tabMode.getValueAt(i,9).toString()+"','"+
                                tabMode.getValueAt(i,10).toString()+"','"+
                                tabMode.getValueAt(i,11).toString()+"','','','','','','','','','','','','','','','','','','','','','','','','',''","Transaksi Pembelian"); 
            }           
            
            Map<String, Object> param = new HashMap<>();    
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("emailrs",akses.getemailrs());   
                param.put("logo",Sequel.cariGambar("select logo from setting")); 
            Valid.MyReport("rptUTDPemisahanKomponen.jasper","report","::[ Data Pemisahan Komponen Darah ]::",param);
            this.setCursor(Cursor.getDefaultCursor());
        }        
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            //Valid.pindah(evt,BtnEdit,BtnAll);
        }
}//GEN-LAST:event_BtnPrintKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        aktifkan="";
        tampil();
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnPrint, BtnKeluar);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
            dispose();  
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){            
            dispose();              
        }else{Valid.pindah(evt,BtnAll,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        }else if(tbPemisahan.getSelectedRow()<= -1){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan pilih data darah yang madu dipisahkan komponennya..!!");
        }else{
            if(!tbPemisahan.getValueAt(tbPemisahan.getSelectedRow(),0).toString().equals("")){
                NomorDonor.setText(tbPemisahan.getValueAt(tbPemisahan.getSelectedRow(),0).toString());
                DlgInput.setSize(internalFrame1.getWidth()-20, internalFrame1.getHeight()-20);
                DlgInput.setLocationRelativeTo(internalFrame1);
                DlgInput.setVisible(true);
                emptTeks();
            }else{
                JOptionPane.showMessageDialog(null,"Maaf,Pilih pada nomor donor...!!!!");
            } 
        }
            
}//GEN-LAST:event_BtnTambahActionPerformed

    private void BtnTambahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnTambahKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            //BtnSimpanActionPerformed(null);
        }else{
           // Valid.pindah(evt,Pembatalan,BtnBatal);
        }
}//GEN-LAST:event_BtnTambahKeyPressed
/*
private void KdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdKeyPressed
    Valid.pindah(evt,BtnCari,Nm);
}//GEN-LAST:event_TKdKeyPressed
*/

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        aktifkan="";
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void TCariMedisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariMedisKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCari2ActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            TCariNonMedis.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Komponen.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            tbMedis.requestFocus();
        }
    }//GEN-LAST:event_TCariMedisKeyPressed

    private void BtnCari2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCari2ActionPerformed
        tampilMedis();
    }//GEN-LAST:event_BtnCari2ActionPerformed

    private void BtnAll1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAll1ActionPerformed
        TCariMedis.setText("");
        tampilMedis();
    }//GEN-LAST:event_BtnAll1ActionPerformed

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

    private void BtnAll2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAll2ActionPerformed
        TCariNonMedis.setText("");
        tampilNonMedis();
    }//GEN-LAST:event_BtnAll2ActionPerformed

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

    private void NomorDonorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NomorDonorKeyPressed
        Valid.pindah(evt,TCari,Tanggal);
    }//GEN-LAST:event_NomorDonorKeyPressed

    private void TanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKeyPressed
        Valid.pindah(evt,NomorDonor,Dinas);
    }//GEN-LAST:event_TanggalKeyPressed

    private void DinasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DinasKeyPressed
        Valid.pindah(evt,Dinas,KodePetugas);
    }//GEN-LAST:event_DinasKeyPressed

    private void btnPetugasAftapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPetugasAftapActionPerformed
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setAlwaysOnTop(false);
        petugas.setVisible(true);
    }//GEN-LAST:event_btnPetugasAftapActionPerformed

    private void KodePetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodePetugasKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama from petugas where nip=?",NamaPetugas,KodePetugas.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnPetugasAftapActionPerformed(null);
        }else{
            Valid.pindah(evt,Dinas,Komponen);
        }
    }//GEN-LAST:event_KodePetugasKeyPressed

    private void BtnTutupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTutupActionPerformed
        DlgInput.dispose();
    }//GEN-LAST:event_BtnTutupActionPerformed

    private void BtnCariKomponenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariKomponenActionPerformed
        tampilKomponen();
    }//GEN-LAST:event_BtnCariKomponenActionPerformed

    private void tbKomponenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbKomponenKeyPressed
        if(tbKomponen.getRowCount()!=0){
            if(evt.getKeyCode()==KeyEvent.VK_ENTER){
                try {
                    i=tbKomponen.getSelectedColumn();
                    if(i==1){
                        if(tbKomponen.getSelectedRow()>-1){
                            tbKomponen.setValueAt(true,tbKomponen.getSelectedRow(),0);
                        }
                        Komponen.setText("");
                        Komponen.requestFocus();
                    }
                } catch (java.lang.NullPointerException e) {
                }
            }else if(evt.getKeyCode()==KeyEvent.VK_SHIFT){
                Komponen.setText("");
                Komponen.requestFocus();
            }
        }
    }//GEN-LAST:event_tbKomponenKeyPressed

    private void btnTambahKomponenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahKomponenActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        UTDKomponenDarah form=new UTDKomponenDarah(null,true);
        form.isCek();
        form.setSize(internalFrame2.getWidth(), internalFrame2.getHeight());
        form.setLocationRelativeTo(internalFrame2);
        form.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnTambahKomponenActionPerformed

    private void KomponenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KomponenKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariKomponenActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            TCariMedis.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            KodePetugas.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_DOWN){
            tbKomponen.requestFocus();
        }
    }//GEN-LAST:event_KomponenKeyPressed

    private void DlgInputWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_DlgInputWindowOpened
        tampilKomponen();
        tampilMedis();
        tampilNonMedis();
    }//GEN-LAST:event_DlgInputWindowOpened

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,TCariNonMedis,BtnBatal);
        }
    }//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        jml=0;
        for(i=0;i<tbKomponen.getRowCount();i++){
            if(tbKomponen.getValueAt(i,0).toString().equals("true")){
                jml++;
            }
        }
        if(NomorDonor.getText().trim().equals("")){
            Valid.textKosong(NomorDonor,"Nomor Donor");
        }else if(KodePetugas.getText().trim().equals("")||NamaPetugas.getText().trim().equals("")){
            Valid.textKosong(KodePetugas,"Petugas");
        }else if(jml==0){
            Valid.textKosong(Komponen,"Data Komponen Darah");
        }else{            
            
            if(Sequel.menyimpantf("utd_pemisahan_komponen","?,?,?,?","Nomor Donor",4,new String[]{
                NomorDonor.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+""),Dinas.getSelectedItem().toString(),KodePetugas.getText()
            })==true){
                row=tbKomponen.getRowCount();
                for(i=0;i<row;i++){
                    if(tbKomponen.getValueAt(i,0).toString().equals("true")){
                        cal = Calendar.getInstance();
                        cal.setTime(Tanggal.getDate());
                        cal.add( Calendar.DATE,Integer.parseInt(tbKomponen.getValueAt(i,3).toString()));
                        sdf = new SimpleDateFormat("yyyy-MM-dd");
                        if(Sequel.menyimpantf2("utd_detail_pemisahan_komponen","?,?,?,?","No.Kantong",4,new String[]{
                            NomorDonor.getText(),tbKomponen.getValueAt(i,1).toString()+NomorDonor.getText(),
                            tbKomponen.getValueAt(i,1).toString(),sdf.format(cal.getTime())
                        })==true){
                            Sequel.menyimpan2("utd_stok_darah","?,?,?,?,?,?,?,?","Kode",8,new String[]{
                                tbKomponen.getValueAt(i,1).toString()+NomorDonor.getText(),
                                tbKomponen.getValueAt(i,1).toString(),tbPemisahan.getValueAt(tbPemisahan.getSelectedRow(),7).toString(),
                                tbPemisahan.getValueAt(tbPemisahan.getSelectedRow(),8).toString(),Valid.SetTgl(Tanggal.getSelectedItem()+""),
                                sdf.format(cal.getTime()),"Produksi Sendiri","Ada"
                            });
                            tbKomponen.setValueAt(false,i,0);
                        }
                    } 
                }
                for(i=0;i<tbMedis.getRowCount();i++){  
                    try {
                        if(Valid.SetAngka(tbMedis.getValueAt(i,0).toString())>0){
                            if(Sequel.menyimpantf2("utd_penggunaan_medis_pemisahan_komponen ","?,?,?,?,?","BHP Medis",5,new String[]{
                                NomorDonor.getText(),tbMedis.getValueAt(i,1).toString(),tbMedis.getValueAt(i,0).toString(),tbMedis.getValueAt(i,3).toString(),
                                Double.toString(Double.parseDouble(tbMedis.getValueAt(i,0).toString())*Double.parseDouble(tbMedis.getValueAt(i,3).toString()))
                            })==true){
                                Sequel.menyimpan("utd_stok_medis","'"+tbMedis.getValueAt(i,1).toString()+"','-"+tbMedis.getValueAt(i,0).toString()+"','"+tbMedis.getValueAt(i,3).toString()+"'", 
                                    "stok=stok-"+tbMedis.getValueAt(i,0).toString()+"","kode_brng='"+tbMedis.getValueAt(i,1).toString()+"'");
                                tbMedis.setValueAt(null,i,0);        
                                tbMedis.setValueAt(0,i,4);
                            }   
                        }                                
                    } catch (Exception e) {
                    }                    
                }
                for(i=0;i<tbNonMedis.getRowCount();i++){  
                    try {
                        if(Valid.SetAngka(tbNonMedis.getValueAt(i,0).toString())>0){
                            if(Sequel.menyimpantf2("utd_penggunaan_penunjang_pemisahan_komponen ","?,?,?,?,?","BHP Non Medis",5,new String[]{
                                NomorDonor.getText(),tbNonMedis.getValueAt(i,1).toString(),tbNonMedis.getValueAt(i,0).toString(),tbNonMedis.getValueAt(i,3).toString(),
                                Double.toString(Double.parseDouble(tbNonMedis.getValueAt(i,0).toString())*Double.parseDouble(tbNonMedis.getValueAt(i,3).toString()))
                            })==true){
                                Sequel.menyimpan("utd_stok_penunjang","'"+tbNonMedis.getValueAt(i,1).toString()+"','-"+tbNonMedis.getValueAt(i,0).toString()+"','"+tbNonMedis.getValueAt(i,3).toString()+"'", 
                                "stok=stok-"+tbNonMedis.getValueAt(i,0).toString()+"","kode_brng='"+tbNonMedis.getValueAt(i,1).toString()+"'");
                                tbNonMedis.setValueAt(null,i,0);        
                                tbNonMedis.setValueAt(0,i,4);
                            }   
                        }                                
                    } catch (Exception e) {
                    }                    
                }
                tampil();
                JOptionPane.showMessageDialog(null,"Proses simpan selesai..!!!");
            }
                        
        }
    }//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        emptTeks();
    }//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnAllKomponenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllKomponenActionPerformed
        Komponen.setText("");
        tampilKomponen();
    }//GEN-LAST:event_BtnAllKomponenActionPerformed

    private void ppTampilkanBHPMedisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppTampilkanBHPMedisActionPerformed
        aktifkan="medis";
        tampil();
    }//GEN-LAST:event_ppTampilkanBHPMedisActionPerformed

    private void ppTampilkanBHPPenunjangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppTampilkanBHPPenunjangActionPerformed
        aktifkan="nonmedis";
        tampil();
    }//GEN-LAST:event_ppTampilkanBHPPenunjangActionPerformed

    private void ppTampilkanBHPPenunjangDanMedisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppTampilkanBHPPenunjangDanMedisActionPerformed
        aktifkan="medis&nonmedis";
        tampil();
    }//GEN-LAST:event_ppTampilkanBHPPenunjangDanMedisActionPerformed

    private void ppHapusBHPMedisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppHapusBHPMedisActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        }else if(tbPemisahan.getSelectedRow()<= -1){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan pilih data yang mau dihapus..!!");
        }else{
            if(!tbPemisahan.getValueAt(tbPemisahan.getSelectedRow(),0).toString().equals("")){
                int reply = JOptionPane.showConfirmDialog(rootPane,"Eeiiiiiits, Yakin mau dihapus..??","Konfirmasi",JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {
                    try {
                        
                        pscekmedis=koneksi.prepareStatement(sqlpscekmedis);
                        try {
                            pscekmedis.setString(1,tbPemisahan.getValueAt(tbPemisahan.getSelectedRow(),0).toString());
                            rs=pscekmedis.executeQuery();
                            while(rs.next()){
                                Sequel.menyimpan("utd_stok_medis","'"+rs.getString("kode_brng")+"','"+rs.getString("jml")+"','"+rs.getDouble("harga")+"'",
                                    "stok=stok+"+rs.getString("jml")+"","kode_brng='"+rs.getString("kode_brng")+"'");
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi : "+e);
                        } finally{
                            if(rs!=null){
                                rs.close();
                            }
                            if(pscekmedis!=null){
                                pscekmedis.close();
                            }
                        }
                        Sequel.meghapus("utd_penggunaan_medis_pemisahan_komponen","no_donor",tbPemisahan.getValueAt(tbPemisahan.getSelectedRow(),0).toString());
                        
                        tampil();
                        emptTeks();
                    } catch (Exception e) {
                        System.out.println("Notifikasi : "+e);
                    }
                }
            }else{
                JOptionPane.showMessageDialog(null,"Maaf,Pilih pada nomor donor...!!!!");
            }
        }
    }//GEN-LAST:event_ppHapusBHPMedisActionPerformed

    private void ppHapusBHPNonMedisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppHapusBHPNonMedisActionPerformed
        if(tbPemisahan.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        }else if(tbPemisahan.getSelectedRow()<= -1){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan pilih data yang mau dihapus..!!");
        }else{
            if(!tbPemisahan.getValueAt(tbPemisahan.getSelectedRow(),0).toString().equals("")){
                int reply = JOptionPane.showConfirmDialog(rootPane,"Eeiiiiiits, Yakin mau dihapus..??","Konfirmasi",JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {
                    try {
                        
                        psceknonmedis=koneksi.prepareStatement(sqlpsceknonmedis);
                        try {
                            psceknonmedis.setString(1,tbPemisahan.getValueAt(tbPemisahan.getSelectedRow(),0).toString());
                            rs2=psceknonmedis.executeQuery();
                            while(rs2.next()){
                                Sequel.menyimpan("utd_stok_penunjang","'"+rs2.getString("kode_brng")+"','"+rs2.getString("jml")+"','"+rs2.getDouble("harga")+"'",
                                    "stok=stok+"+rs2.getString("jml")+"","kode_brng='"+rs2.getString("kode_brng")+"'");
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi : "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(psceknonmedis!=null){
                                psceknonmedis.close();
                            }
                        }
                        Sequel.meghapus("utd_penggunaan_penunjang_pemisahan_komponen","no_donor",tbPemisahan.getValueAt(tbPemisahan.getSelectedRow(),0).toString());
                        
                        tampil();
                        emptTeks();
                    } catch (Exception e) {
                        System.out.println("Notifikasi : "+e);
                    }
                }
            }else{
                JOptionPane.showMessageDialog(null,"Maaf,Pilih pada nomor donor...!!!!");
            }
        }
    }//GEN-LAST:event_ppHapusBHPNonMedisActionPerformed

    private void ppHapusBHPMedisDanNonMedisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppHapusBHPMedisDanNonMedisActionPerformed
        if(tbPemisahan.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        }else if(tbPemisahan.getSelectedRow()<= -1){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan pilih data yang mau dihapus..!!");
        }else{
            if(!tbPemisahan.getValueAt(tbPemisahan.getSelectedRow(),0).toString().equals("")){
                int reply = JOptionPane.showConfirmDialog(rootPane,"Eeiiiiiits, Yakin mau dihapus..??","Konfirmasi",JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {
                    try {
                        
                        pscekmedis=koneksi.prepareStatement(sqlpscekmedis);
                        try {
                            pscekmedis.setString(1,tbPemisahan.getValueAt(tbPemisahan.getSelectedRow(),0).toString());
                            rs=pscekmedis.executeQuery();
                            while(rs.next()){
                                Sequel.menyimpan("utd_stok_medis","'"+rs.getString("kode_brng")+"','"+rs.getString("jml")+"','"+rs.getDouble("harga")+"'",
                                    "stok=stok+"+rs.getString("jml")+"","kode_brng='"+rs.getString("kode_brng")+"'");
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi : "+e);
                        } finally{
                            if(rs!=null){
                                rs.close();
                            }
                            if(pscekmedis!=null){
                                pscekmedis.close();
                            }
                        }
                        Sequel.meghapus("utd_penggunaan_medis_pemisahan_komponen","no_donor",tbPemisahan.getValueAt(tbPemisahan.getSelectedRow(),0).toString());
                        psceknonmedis=koneksi.prepareStatement(sqlpsceknonmedis);
                        try {
                            psceknonmedis.setString(1,tbPemisahan.getValueAt(tbPemisahan.getSelectedRow(),0).toString());
                            rs2=psceknonmedis.executeQuery();
                            while(rs2.next()){
                                Sequel.menyimpan("utd_stok_penunjang","'"+rs2.getString("kode_brng")+"','"+rs2.getString("jml")+"','"+rs2.getDouble("harga")+"'",
                                    "stok=stok+"+rs2.getString("jml")+"","kode_brng='"+rs2.getString("kode_brng")+"'");
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi : "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(psceknonmedis!=null){
                                psceknonmedis.close();
                            }
                        }
                        Sequel.meghapus("utd_penggunaan_penunjang_pemisahan_komponen","no_donor",tbPemisahan.getValueAt(tbPemisahan.getSelectedRow(),0).toString());
                        
                        tampil();
                        emptTeks();
                    } catch (Exception e) {
                        System.out.println("Notifikasi : "+e);
                    }
                }
            }else{
                JOptionPane.showMessageDialog(null,"Maaf,Pilih pada nomor donor...!!!!");
            }
        }
    }//GEN-LAST:event_ppHapusBHPMedisDanNonMedisActionPerformed

    private void ppHapusPemisahanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppHapusPemisahanActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        }else if(tbPemisahan.getSelectedRow()<= -1){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan pilih data yang mau dihapus..!!");
        }else{
            if(!tbPemisahan.getValueAt(tbPemisahan.getSelectedRow(),0).toString().equals("")){
                int reply = JOptionPane.showConfirmDialog(rootPane,"Eeiiiiiits, Yakin mau dihapus..??","Konfirmasi",JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {
                    Sequel.meghapus("utd_pemisahan_komponen","no_donor",tbPemisahan.getValueAt(tbPemisahan.getSelectedRow(),0).toString());
                    Sequel.queryu("delete from utd_stok_darah where no_kantong like ?","%"+tbPemisahan.getValueAt(tbPemisahan.getSelectedRow(),0).toString()+"%");
                    tampil();
                    emptTeks();
                }
            }else{
                JOptionPane.showMessageDialog(null,"Maaf,Pilih pada nomor donor...!!!!");
            }
        }
    }//GEN-LAST:event_ppHapusPemisahanActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            UTDPemisahanDarah dialog = new UTDPemisahanDarah(new javax.swing.JFrame(), true);
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
    private widget.Button BtnAll1;
    private widget.Button BtnAll2;
    private widget.Button BtnAllKomponen;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnCari2;
    private widget.Button BtnCari3;
    private widget.Button BtnCariKomponen;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.Button BtnTambah;
    private widget.Button BtnTutup;
    private widget.ComboBox Dinas;
    private javax.swing.JDialog DlgInput;
    private widget.TextBox KodePetugas;
    private widget.TextBox Komponen;
    private widget.Label LCount;
    private widget.TextBox NamaPetugas;
    private widget.TextBox NomorDonor;
    private widget.ScrollPane Scroll1;
    private widget.ScrollPane Scroll2;
    private widget.ScrollPane Scroll3;
    private widget.TextBox TCari;
    private widget.TextBox TCariMedis;
    private widget.TextBox TCariNonMedis;
    private widget.Tanggal Tanggal;
    private widget.Tanggal TanggalCari1;
    private widget.Tanggal TanggalCari2;
    private widget.Button btnPetugasAftap;
    private widget.Button btnTambahKomponen;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.Label jLabel20;
    private widget.Label jLabel21;
    private widget.Label jLabel8;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.Label label10;
    private widget.Label label11;
    private widget.Label label12;
    private widget.Label label18;
    private widget.Label label19;
    private widget.Label label32;
    private widget.Label label34;
    private widget.Label label9;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi2;
    private widget.panelisi panelisi3;
    private widget.panelisi panelisi4;
    private widget.panelisi panelisi5;
    private widget.panelisi panelisi6;
    private widget.panelisi panelisi7;
    private javax.swing.JMenuItem ppHapusBHPMedis;
    private javax.swing.JMenuItem ppHapusBHPMedisDanNonMedis;
    private javax.swing.JMenuItem ppHapusBHPNonMedis;
    private javax.swing.JMenuItem ppHapusPemisahan;
    private javax.swing.JMenuItem ppTampilkanBHPMedis;
    private javax.swing.JMenuItem ppTampilkanBHPPenunjang;
    private javax.swing.JMenuItem ppTampilkanBHPPenunjangDanMedis;
    private widget.ScrollPane scrollPane1;
    private widget.Table tbKomponen;
    private widget.Table tbMedis;
    private widget.Table tbNonMedis;
    private widget.Table tbPemisahan;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            psdonor=koneksi.prepareStatement(
                "select * from utd_donor where "+
                "tanggal between ? and ? and status='Aman' and no_donor like ? or "+
                "tanggal between ? and ? and status='Aman' and nama like ? or "+
                "tanggal between ? and ? and status='Aman' and alamat like ? or "+
                "tanggal between ? and ? and status='Aman' and jenis_donor like ? or "+
                "tanggal between ? and ? and status='Aman' and tempat_aftap like ? or "+
                "tanggal between ? and ? and status='Aman' and jenis_bag like ? or "+
                "tanggal between ? and ? and status='Aman' and dinas like ? order by tanggal,no_donor ");
            try {
                psdonor.setString(1,Valid.SetTgl(TanggalCari1.getSelectedItem()+""));
                psdonor.setString(2,Valid.SetTgl(TanggalCari2.getSelectedItem()+""));
                psdonor.setString(3,"%"+TCari.getText().trim()+"%");
                psdonor.setString(4,Valid.SetTgl(TanggalCari1.getSelectedItem()+""));
                psdonor.setString(5,Valid.SetTgl(TanggalCari2.getSelectedItem()+""));
                psdonor.setString(6,"%"+TCari.getText().trim()+"%");
                psdonor.setString(7,Valid.SetTgl(TanggalCari1.getSelectedItem()+""));
                psdonor.setString(8,Valid.SetTgl(TanggalCari2.getSelectedItem()+""));
                psdonor.setString(9,"%"+TCari.getText().trim()+"%");
                psdonor.setString(10,Valid.SetTgl(TanggalCari1.getSelectedItem()+""));
                psdonor.setString(11,Valid.SetTgl(TanggalCari2.getSelectedItem()+""));
                psdonor.setString(12,"%"+TCari.getText().trim()+"%");
                psdonor.setString(13,Valid.SetTgl(TanggalCari1.getSelectedItem()+""));
                psdonor.setString(14,Valid.SetTgl(TanggalCari2.getSelectedItem()+""));
                psdonor.setString(15,"%"+TCari.getText().trim()+"%");
                psdonor.setString(16,Valid.SetTgl(TanggalCari1.getSelectedItem()+""));
                psdonor.setString(17,Valid.SetTgl(TanggalCari2.getSelectedItem()+""));
                psdonor.setString(18,"%"+TCari.getText().trim()+"%");
                psdonor.setString(19,Valid.SetTgl(TanggalCari1.getSelectedItem()+""));
                psdonor.setString(20,Valid.SetTgl(TanggalCari2.getSelectedItem()+""));
                psdonor.setString(21,"%"+TCari.getText().trim()+"%");
                rs=psdonor.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new Object[]{
                        rs.getString("no_donor"),rs.getString("nama"),rs.getString("tanggal"), 
                        rs.getString("dinas"),rs.getString("jk"),rs.getString("umur"),
                        rs.getString("alamat"),rs.getString("golongan_darah"),rs.getString("resus"),
                        rs.getString("tensi"),rs.getString("no_bag"),rs.getString("no_telp") 
                    });
                    //pemisahan
                    pspemisahan=koneksi.prepareStatement(
                            "select utd_pemisahan_komponen.tanggal,utd_pemisahan_komponen.dinas,petugas.nama "+
                            "from utd_pemisahan_komponen inner join petugas on utd_pemisahan_komponen.nip=petugas.nip "+
                            "where utd_pemisahan_komponen.no_donor=?");
                    try {
                        pspemisahan.setString(1,rs.getString("no_donor"));
                        rs2=pspemisahan.executeQuery();
                        while(rs2.next()){
                            tabMode.addRow(new Object[]{
                               "","","","","","","Tanggal : "+rs2.getString("tanggal")+" "+rs2.getString("dinas")+", Petugas : "+rs2.getString("nama"),"","","","",""
                            });
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi Pemisahan : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(pspemisahan!=null){
                            pspemisahan.close();
                        }
                    }
                    //detail pemisahan
                    pscekkomponen=koneksi.prepareStatement(
                            "select utd_detail_pemisahan_komponen.no_kantong,utd_komponen_darah.nama,utd_detail_pemisahan_komponen.tanggal_kadaluarsa "+
                            "from utd_detail_pemisahan_komponen inner join utd_komponen_darah on utd_detail_pemisahan_komponen.kode_komponen=utd_komponen_darah.kode "+
                            "where utd_detail_pemisahan_komponen.no_donor=?");
                    try{
                        pscekkomponen.setString(1,rs.getString("no_donor"));
                        rs2=pscekkomponen.executeQuery();
                        if(rs2.next()){
                            tabMode.addRow(new Object[]{
                                    "","","","","","","Komponen Yang Dipisahkan :","","","","",""
                            });
                            i=1;
                        }
                        rs2.beforeFirst();
                        while(rs2.next()){
                            tabMode.addRow(new Object[]{
                                "","","","","","",
                                i+". "+rs2.getString("no_kantong")+" "+rs2.getString("nama")+" ("+rs2.getString("tanggal_kadaluarsa")+")","","","","",""
                            });                          
                            i++;
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi Detail Pemisahan : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(pskomponen!=null){
                            pskomponen.close();
                        }
                    }
                    if(aktifkan.equals("medis")){
                        pscekmedis=koneksi.prepareStatement(sqlpscekmedis);
                        try {
                            pscekmedis.setString(1,rs.getString("no_donor"));
                            rs2=pscekmedis.executeQuery();
                            if(rs2.next()){
                                tabMode.addRow(new Object[]{
                                        "","","","","","","Penggunaan BHP Medis :","","","","",""
                                });
                                i=1;
                            }
                            rs2.beforeFirst();
                            while(rs2.next()){
                                tabMode.addRow(new String[]{
                                    "","","","","","",i+". "+rs2.getString("nama_brng")+" ("+rs2.getString("jml")+" "+rs2.getString("kode_sat")+" X "+Valid.SetAngka(rs2.getDouble("harga"))+") = "+Valid.SetAngka(rs2.getDouble("total")),"","","","",""
                                });
                                i++;
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi : "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(pscekmedis!=null){
                                pscekmedis.close();
                            }
                        }
                    }else if(aktifkan.equals("nonmedis")){
                        psceknonmedis=koneksi.prepareStatement(sqlpsceknonmedis);
                        try {
                            psceknonmedis.setString(1,rs.getString("no_donor"));
                            rs2=psceknonmedis.executeQuery();
                            if(rs2.next()){
                                tabMode.addRow(new Object[]{
                                        "","","","","","","Penggunaan BHP Non Medis :","","","","",""
                                });
                                i=1;
                            }
                            rs2.beforeFirst();
                            while(rs2.next()){
                                tabMode.addRow(new String[]{
                                   "","","","","","",i+". "+rs2.getString("nama_brng")+" ("+rs2.getString("jml")+" "+rs2.getString("kode_sat")+" X "+Valid.SetAngka(rs2.getDouble("harga"))+") = "+Valid.SetAngka(rs2.getDouble("total")),"","","","",""
                                });                       
                                i++;
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi : "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(psceknonmedis!=null){
                                psceknonmedis.close();
                            }
                        }
                    }else if(aktifkan.equals("medis&nonmedis")){
                        pscekmedis=koneksi.prepareStatement(sqlpscekmedis);
                        try {
                            pscekmedis.setString(1,rs.getString("no_donor"));
                            rs2=pscekmedis.executeQuery();
                            if(rs2.next()){
                                tabMode.addRow(new Object[]{
                                        "","","","","","","Penggunaan BHP Medis :","","","","",""
                                });
                                i=1;
                            }
                            rs2.beforeFirst();                         
                            while(rs2.next()){
                                tabMode.addRow(new String[]{
                                    "","","","","","",i+". "+rs2.getString("nama_brng")+" ("+rs2.getString("jml")+" "+rs2.getString("kode_sat")+" X "+Valid.SetAngka(rs2.getDouble("harga"))+") = "+Valid.SetAngka(rs2.getDouble("total")),"","","","",""
                                });                       
                                i++;
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi : "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(pscekmedis!=null){
                                pscekmedis.close();
                            }
                        }
                        
                        psceknonmedis=koneksi.prepareStatement(sqlpsceknonmedis);
                        try {
                            psceknonmedis.setString(1,rs.getString("no_donor"));
                            rs2=psceknonmedis.executeQuery();
                            if(rs2.next()){
                                tabMode.addRow(new Object[]{
                                    "","","","","","","Penggunaan BHP Non Medis :","","","","",""
                                });
                                i=1;
                            }
                            rs2.beforeFirst();
                            while(rs2.next()){
                                tabMode.addRow(new String[]{
                                    "","","","","","",i+". "+rs2.getString("nama_brng")+" ("+rs2.getString("jml")+" "+rs2.getString("kode_sat")+" X "+Valid.SetAngka(rs2.getDouble("harga"))+") = "+Valid.SetAngka(rs2.getDouble("total")),"","","","",""
                                });                       
                                i++;
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi : "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(psceknonmedis!=null){
                                psceknonmedis.close();
                            }
                        }                        
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(psdonor!=null){
                    psdonor.close();
                }
            }
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        LCount.setText(""+tabMode.getRowCount());
    }

    public void emptTeks() {
        KodePetugas.setText("");
        NamaPetugas.setText("");
        Komponen.setText("");
        TCariMedis.setText("");
        TCariNonMedis.setText("");
        Tanggal.requestFocus();
        for(index=0;index<tbMedis.getRowCount();index++){   
            tbMedis.setValueAt(null,index,0);        
        }
        
        for(index=0;index<tbNonMedis.getRowCount();index++){   
            tbNonMedis.setValueAt(null,index,0);        
        }
        
        for(index=0;index<tbKomponen.getRowCount();index++){   
            tbKomponen.setValueAt(false,index,0);        
        }
    }

    private void getData() {
        if(tbPemisahan.getSelectedRow()!= -1){
            
        }
    }

    public JTable getTable(){
        return tbPemisahan;
    }
    
    public void isCek(){
        BtnTambah.setEnabled(akses.getutd_pemisahan_darah());
        BtnPrint.setEnabled(akses.getutd_pemisahan_darah());
        btnTambahKomponen.setEnabled(akses.getutd_komponen_darah());
        if(akses.getjml2()>=1){
            KodePetugas.setText(akses.getkode());
            Sequel.cariIsi("select nama from petugas where nip=?", NamaPetugas,KodePetugas.getText());
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
                    tabModeMedis.addRow(new Object[]{null,rs.getString(1),rs.getString(2),rs.getString(3),0,rs.getString(4),rs.getString(5)});
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
                    tabModeNonMedis.addRow(new Object[]{null,rs2.getString(1),rs2.getString(2),rs2.getString(3),0,rs2.getString(4),rs2.getString(5)});
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
    
    private void tampilKomponen() {
        row=tbKomponen.getRowCount();
        jml=0;
        for(i=0;i<row;i++){
            try {
                if(tbKomponen.getValueAt(i,0).toString().equals("true")){
                    jml++;
                }
            } catch (Exception e) {
                jml=jml+0;
            } 
        }
        
        pilih=null;
        pilih=new boolean[jml];
        kodebarang=null;
        kodebarang=new String[jml];
        namabarang=null;
        namabarang=new String[jml];
        jumlah=null;
        jumlah=new String[jml];
        index=0;        
        for(i=0;i<row;i++){
            try {
                if(tbKomponen.getValueAt(i,0).toString().equals("true")){
                    pilih[index]=true;
                    kodebarang[index]=tbKomponen.getValueAt(i,1).toString();
                    namabarang[index]=tbKomponen.getValueAt(i,2).toString();
                    jumlah[index]=tbKomponen.getValueAt(i,3).toString();
                    index++;
                }
            } catch (Exception e) {
            }
        }
        Valid.tabelKosong(tabModeKomponen);
        for(i=0;i<jml;i++){
            tabModeKomponen.addRow(new Object[]{pilih[i],kodebarang[i],namabarang[i],jumlah[i]});
        }
        try{            
            pskomponen=koneksi.prepareStatement(
                "select * from utd_komponen_darah where kode like ? or nama like ? order by nama");
            try {
                pskomponen.setString(1,"%"+Komponen.getText().trim()+"%");
                pskomponen.setString(2,"%"+Komponen.getText().trim()+"%");
                rs=pskomponen.executeQuery();
                while(rs.next()){
                    tabModeKomponen.addRow(new Object[]{
                        false,rs.getString(1),rs.getString(2),rs.getString(3)
                    });
                }
            } catch (Exception e) {
                System.out.println(e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(pskomponen!=null){
                    pskomponen.close();
                }
            }
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        LCount.setText(""+tabModeKomponen.getRowCount());
    }
    
}

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

package inventory;

import fungsi.WarnaTable2;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariDokter;
import widget.Button;

/**
 *
 * @author dosen
 */
public final class DlgPeresepanDokter extends javax.swing.JDialog {
    private final DefaultTableModel tabModeResep,tabModeDetailResepRacikan,tabModeResepRacikan;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement psresep,pscarikapasitas,psresepasuransi,ps2;
    private ResultSet rsobat,carikapasitas,rs2;
    private double x=0,y=0,kenaikan=0,ttl=0,ppnobat=0,jumlahracik=0,persenracik=0,kapasitasracik=0;
    private int i=0,z=0,row2=0,r=0;
    private boolean ubah=false;
    private boolean[] pilih; 
    private double[] jumlah,harga,beli,stok,kapasitas,p1,p2;
    private String[] no,kodebarang,namabarang,kodesatuan,kandungan,letakbarang,namajenis,aturan,industri;
    public DlgBarang barang=new DlgBarang(null,false);
    public DlgAturanPakai aturanpakai=new DlgAturanPakai(null,false);
    private WarnaTable2 warna=new WarnaTable2();
    private WarnaTable2 warna2=new WarnaTable2();
    private WarnaTable2 warna3=new WarnaTable2();
    private DlgMetodeRacik metoderacik=new DlgMetodeRacik(null,false);
    public DlgCariDokter dokter=new DlgCariDokter(null,false);
    private String noracik="",tampilkan_ppnobat_ralan="",status="",bangsal="",kamar="",norawatibu="",kelas,bangsaldefault=Sequel.cariIsi("select kd_bangsal from set_lokasi limit 1");
    /** Creates new form DlgPenyakit
     * @param parent
     * @param modal */
    public DlgPeresepanDokter(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(10,2);
        setSize(656,250);
        tabModeResep=new DefaultTableModel(null,new Object[]{
                "K","Jumlah","Kode Barang","Nama Barang","Satuan","Letak Barang",
                "Harga(Rp)","Jenis Obat","Aturan Pakai","I.F.","H.Beli","Stok"
            }){
            @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if ((colIndex==0)||(colIndex==1)||(colIndex==8)) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                java.lang.Object.class, java.lang.Object.class, java.lang.Double.class, java.lang.Object.class, 
                java.lang.Object.class,java.lang.Object.class,java.lang.Double.class,java.lang.Double.class
             };
             /*Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
             };*/
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbResep.setModel(tabModeResep);
        //tbPenyakit.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbPenyakit.getBackground()));
        tbResep.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbResep.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (i = 0; i < 12; i++) {
            TableColumn column = tbResep.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(45);
            }else if(i==2){
                column.setPreferredWidth(70);
            }else if(i==3){
                column.setPreferredWidth(250);
            }else if(i==4){
                column.setPreferredWidth(75);
            }else if(i==5){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==6){
                column.setPreferredWidth(85);
            }else if(i==7){
                column.setPreferredWidth(110);
            }else if(i==8){
                column.setPreferredWidth(130);
            }else if(i==9){
                column.setPreferredWidth(130);
            }else if(i==10){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==11){
                column.setPreferredWidth(50);
            }                 
        }
        warna.kolom=1;
        tbResep.setDefaultRenderer(Object.class,warna);
        
        tabModeResepRacikan=new DefaultTableModel(null,new Object[]{
                "No","Nama Racikan","Kode Racik","Metode Racik","Jml.Racik",
                "Aturan Pakai","Keterangan"
            }){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = true;
                if ((colIndex==0)||(colIndex==2)||(colIndex==3)) {
                    a=false;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };

        tbObatResepRacikan.setModel(tabModeResepRacikan);
        tbObatResepRacikan.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObatResepRacikan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);        
        
        for (i = 0; i < 7; i++) {
            TableColumn column = tbObatResepRacikan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(25);
            }else if(i==1){
                column.setPreferredWidth(250);
            }else if(i==2){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==3){
                column.setPreferredWidth(100);
            }else if(i==4){
                column.setPreferredWidth(60);
            }else if(i==5){
                column.setPreferredWidth(200);
            }else if(i==6){
                column.setPreferredWidth(250);
            }
        }

        warna2.kolom=4;
        tbObatResepRacikan.setDefaultRenderer(Object.class,warna2);
        
        tabModeDetailResepRacikan=new DefaultTableModel(null,new Object[]{
                "No","Kode Barang","Nama Barang","Satuan","Harga(Rp)","H.Beli",
                "Jenis Obat","Stok","Kps","P1","/","P2","Kandungan","Jml","I.F."
            }){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if ((colIndex==9)||(colIndex==11)||(colIndex==12)||(colIndex==13)) {
                    a=true;
                }
                return a;
             }             
             Class[] types = new Class[] {
                java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,
                java.lang.Object.class,java.lang.Double.class,java.lang.Double.class,
                java.lang.Object.class,java.lang.Double.class,java.lang.Double.class,
                java.lang.Double.class,java.lang.Object.class,java.lang.Double.class,
                java.lang.Object.class,java.lang.Double.class,java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };

        tbDetailResepObatRacikan.setModel(tabModeDetailResepRacikan);
        tbDetailResepObatRacikan.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbDetailResepObatRacikan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);        
        
        for (i = 0; i < 15; i++) {
            TableColumn column = tbDetailResepObatRacikan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(25);
            }else if(i==1){
                column.setPreferredWidth(75);
            }else if(i==2){
                column.setPreferredWidth(240);
            }else if(i==3){
                column.setPreferredWidth(45);
            }else if(i==4){
                column.setPreferredWidth(85);
            }else if(i==5){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==6){
                column.setPreferredWidth(110);
            }else if(i==7){
                column.setPreferredWidth(50);
            }else if(i==8){
                column.setPreferredWidth(40);
            }else if(i==9){
                column.setPreferredWidth(25);
            }else if(i==10){
                column.setMinWidth(11);
                column.setMaxWidth(11);
            }else if(i==11){
                column.setPreferredWidth(25);
            }else if(i==12){
                column.setPreferredWidth(70);
            }else if(i==13){
                column.setPreferredWidth(40);
            }else if(i==14){
                column.setPreferredWidth(150);
            }
        }

        warna3.kolom=9;
        tbDetailResepObatRacikan.setDefaultRenderer(Object.class,warna3);
        
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        if(koneksiDB.CARICEPAT().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampilobat();
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampilobat();
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampilobat();
                    }
                }
            });
        }
        
        aturanpakai.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(aturanpakai.getTable().getSelectedRow()!= -1){  
                    if(TabRawat.getSelectedIndex()==0){
                        tbResep.setValueAt(aturanpakai.getTable().getValueAt(aturanpakai.getTable().getSelectedRow(),0).toString(),tbResep.getSelectedRow(),8);
                    }else if(TabRawat.getSelectedIndex()==1){
                        tbObatResepRacikan.setValueAt(aturanpakai.getTable().getValueAt(aturanpakai.getTable().getSelectedRow(),0).toString(),tbObatResepRacikan.getSelectedRow(),5);
                        tbObatResepRacikan.requestFocus();
                    }   
                }   
                tbResep.requestFocus();
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
        
        dokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(dokter.getTable().getSelectedRow()!= -1){        
                     KdDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                     NmDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                }  
                KdDokter.requestFocus();
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
        
        metoderacik.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(metoderacik.getTable().getSelectedRow()!= -1){  
                    tbObatResepRacikan.setValueAt(metoderacik.getTable().getValueAt(metoderacik.getTable().getSelectedRow(),1).toString(),tbObatResepRacikan.getSelectedRow(),2);
                    tbObatResepRacikan.setValueAt(metoderacik.getTable().getValueAt(metoderacik.getTable().getSelectedRow(),2).toString(),tbObatResepRacikan.getSelectedRow(),3);
                    tbObatResepRacikan.requestFocus();
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
        
        metoderacik.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    metoderacik.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        }); 
        jam();
        
        tampilkan_ppnobat_ralan=Sequel.cariIsi("select tampilkan_ppnobat_ralan from set_nota"); 
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
        KdPj = new widget.TextBox();
        LPpn = new widget.Label();
        jLabel6 = new widget.Label();
        internalFrame1 = new widget.InternalFrame();
        panelisi3 = new widget.panelisi();
        label9 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        label12 = new widget.Label();
        Jeniskelas = new widget.ComboBox();
        BtnTambah = new widget.Button();
        BtnSeek5 = new widget.Button();
        BtnSimpan = new widget.Button();
        BtnTambah1 = new widget.Button();
        BtnHapus = new widget.Button();
        BtnKeluar = new widget.Button();
        FormInput = new widget.PanelBiasa();
        TNoRw = new widget.TextBox();
        TPasien = new widget.TextBox();
        KdDokter = new widget.TextBox();
        NmDokter = new widget.TextBox();
        jLabel3 = new widget.Label();
        jLabel13 = new widget.Label();
        btnDokter = new widget.Button();
        jLabel11 = new widget.Label();
        NoResep = new widget.TextBox();
        jLabel8 = new widget.Label();
        DTPBeri = new widget.Tanggal();
        cmbJam = new widget.ComboBox();
        cmbMnt = new widget.ComboBox();
        cmbDtk = new widget.ComboBox();
        ChkRM = new widget.CekBox();
        ChkJln = new widget.CekBox();
        jLabel5 = new widget.Label();
        LTotal = new widget.Label();
        jLabel7 = new widget.Label();
        LTotalTagihan = new widget.Label();
        TabRawat = new javax.swing.JTabbedPane();
        Scroll = new widget.ScrollPane();
        tbResep = new widget.Table();
        jPanel3 = new javax.swing.JPanel();
        Scroll1 = new widget.ScrollPane();
        tbObatResepRacikan = new widget.Table();
        Scroll2 = new widget.ScrollPane();
        tbDetailResepObatRacikan = new widget.Table();

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

        KdPj.setHighlighter(null);
        KdPj.setName("KdPj"); // NOI18N

        LPpn.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LPpn.setText("0");
        LPpn.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        LPpn.setName("LPpn"); // NOI18N
        LPpn.setPreferredSize(new java.awt.Dimension(65, 23));

        jLabel6.setText("PPN :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(35, 23));

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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Peresepan Obat Oleh Dokter ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(70, 70, 70))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(100, 43));
        panelisi3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        label9.setText("Key Word :");
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(68, 23));
        panelisi3.add(label9);

        TCari.setToolTipText("Alt+C");
        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(245, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelisi3.add(TCari);

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
        panelisi3.add(BtnCari);

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('2');
        BtnAll.setToolTipText("Alt+2");
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
        panelisi3.add(BtnAll);

        label12.setText("Tarif :");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(50, 23));
        panelisi3.add(label12);

        Jeniskelas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Rawat Jalan", "Beli Luar", "Karyawan", "Utama/BPJS", "Kelas 1", "Kelas 2", "Kelas 3", "VIP", "VVIP" }));
        Jeniskelas.setName("Jeniskelas"); // NOI18N
        Jeniskelas.setPreferredSize(new java.awt.Dimension(120, 23));
        Jeniskelas.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                JeniskelasItemStateChanged(evt);
            }
        });
        Jeniskelas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JeniskelasKeyPressed(evt);
            }
        });
        panelisi3.add(Jeniskelas);

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
        panelisi3.add(BtnTambah);

        BtnSeek5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/011.png"))); // NOI18N
        BtnSeek5.setMnemonic('4');
        BtnSeek5.setToolTipText("Alt+4");
        BtnSeek5.setName("BtnSeek5"); // NOI18N
        BtnSeek5.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSeek5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek5ActionPerformed(evt);
            }
        });
        BtnSeek5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSeek5KeyPressed(evt);
            }
        });
        panelisi3.add(BtnSeek5);

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan.setMnemonic('S');
        BtnSimpan.setToolTipText("Alt+S");
        BtnSimpan.setName("BtnSimpan"); // NOI18N
        BtnSimpan.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpanActionPerformed(evt);
            }
        });
        panelisi3.add(BtnSimpan);

        BtnTambah1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        BtnTambah1.setMnemonic('3');
        BtnTambah1.setToolTipText("Alt+3");
        BtnTambah1.setName("BtnTambah1"); // NOI18N
        BtnTambah1.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnTambah1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTambah1ActionPerformed(evt);
            }
        });
        panelisi3.add(BtnTambah1);

        BtnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); // NOI18N
        BtnHapus.setMnemonic('H');
        BtnHapus.setToolTipText("Alt+H");
        BtnHapus.setName("BtnHapus"); // NOI18N
        BtnHapus.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnHapusActionPerformed(evt);
            }
        });
        panelisi3.add(BtnHapus);

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar.setMnemonic('5');
        BtnKeluar.setToolTipText("Alt+5");
        BtnKeluar.setName("BtnKeluar"); // NOI18N
        BtnKeluar.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluarActionPerformed(evt);
            }
        });
        panelisi3.add(BtnKeluar);

        internalFrame1.add(panelisi3, java.awt.BorderLayout.PAGE_END);

        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(440, 107));
        FormInput.setLayout(null);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(75, 12, 120, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        FormInput.add(TPasien);
        TPasien.setBounds(196, 12, 487, 23);

        KdDokter.setHighlighter(null);
        KdDokter.setName("KdDokter"); // NOI18N
        KdDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdDokterKeyPressed(evt);
            }
        });
        FormInput.add(KdDokter);
        KdDokter.setBounds(75, 72, 120, 23);

        NmDokter.setEditable(false);
        NmDokter.setHighlighter(null);
        NmDokter.setName("NmDokter"); // NOI18N
        FormInput.add(NmDokter);
        NmDokter.setBounds(196, 72, 230, 23);

        jLabel3.setText("No.Rawat :");
        jLabel3.setName("jLabel3"); // NOI18N
        FormInput.add(jLabel3);
        jLabel3.setBounds(0, 12, 72, 23);

        jLabel13.setText("Peresep :");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(0, 72, 72, 23);

        btnDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnDokter.setMnemonic('3');
        btnDokter.setToolTipText("Alt+3");
        btnDokter.setName("btnDokter"); // NOI18N
        btnDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDokterActionPerformed(evt);
            }
        });
        btnDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnDokterKeyPressed(evt);
            }
        });
        FormInput.add(btnDokter);
        btnDokter.setBounds(428, 72, 28, 23);

        jLabel11.setText("No.Resep :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(455, 72, 70, 23);

        NoResep.setHighlighter(null);
        NoResep.setName("NoResep"); // NOI18N
        NoResep.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoResepKeyPressed(evt);
            }
        });
        FormInput.add(NoResep);
        NoResep.setBounds(528, 72, 130, 23);

        jLabel8.setText("Tgl.Resep :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(0, 42, 72, 23);

        DTPBeri.setForeground(new java.awt.Color(50, 70, 50));
        DTPBeri.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "18-05-2019" }));
        DTPBeri.setDisplayFormat("dd-MM-yyyy");
        DTPBeri.setName("DTPBeri"); // NOI18N
        DTPBeri.setOpaque(false);
        DTPBeri.setPreferredSize(new java.awt.Dimension(100, 23));
        DTPBeri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPBeriKeyPressed(evt);
            }
        });
        FormInput.add(DTPBeri);
        DTPBeri.setBounds(75, 42, 90, 23);

        cmbJam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        cmbJam.setName("cmbJam"); // NOI18N
        cmbJam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbJamKeyPressed(evt);
            }
        });
        FormInput.add(cmbJam);
        cmbJam.setBounds(168, 42, 62, 23);

        cmbMnt.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        cmbMnt.setName("cmbMnt"); // NOI18N
        cmbMnt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbMntKeyPressed(evt);
            }
        });
        FormInput.add(cmbMnt);
        cmbMnt.setBounds(233, 42, 62, 23);

        cmbDtk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        cmbDtk.setName("cmbDtk"); // NOI18N
        cmbDtk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbDtkKeyPressed(evt);
            }
        });
        FormInput.add(cmbDtk);
        cmbDtk.setBounds(298, 42, 62, 23);

        ChkRM.setBorder(null);
        ChkRM.setSelected(true);
        ChkRM.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkRM.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkRM.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkRM.setName("ChkRM"); // NOI18N
        ChkRM.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ChkRMItemStateChanged(evt);
            }
        });
        FormInput.add(ChkRM);
        ChkRM.setBounds(660, 72, 23, 23);

        ChkJln.setBorder(null);
        ChkJln.setSelected(true);
        ChkJln.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkJln.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkJln.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkJln.setName("ChkJln"); // NOI18N
        ChkJln.setPreferredSize(new java.awt.Dimension(22, 23));
        ChkJln.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkJlnActionPerformed(evt);
            }
        });
        FormInput.add(ChkJln);
        ChkJln.setBounds(363, 42, 23, 23);

        jLabel5.setText("Total :");
        jLabel5.setName("jLabel5"); // NOI18N
        jLabel5.setPreferredSize(new java.awt.Dimension(45, 23));
        FormInput.add(jLabel5);
        jLabel5.setBounds(385, 42, 45, 23);

        LTotal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LTotal.setText("0");
        LTotal.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        LTotal.setName("LTotal"); // NOI18N
        LTotal.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput.add(LTotal);
        LTotal.setBounds(433, 42, 85, 23);

        jLabel7.setText("Total+PPN :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(jLabel7);
        jLabel7.setBounds(520, 42, 65, 23);

        LTotalTagihan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LTotalTagihan.setText("0");
        LTotalTagihan.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        LTotalTagihan.setName("LTotalTagihan"); // NOI18N
        LTotalTagihan.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput.add(LTotalTagihan);
        LTotalTagihan.setBounds(588, 42, 95, 23);

        internalFrame1.add(FormInput, java.awt.BorderLayout.PAGE_START);

        TabRawat.setBackground(new java.awt.Color(255, 255, 253));
        TabRawat.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(241, 246, 236)));
        TabRawat.setForeground(new java.awt.Color(70, 70, 70));
        TabRawat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabRawat.setName("TabRawat"); // NOI18N
        TabRawat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatMouseClicked(evt);
            }
        });

        Scroll.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll.setComponentPopupMenu(Popup);
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbResep.setComponentPopupMenu(Popup);
        tbResep.setName("tbResep"); // NOI18N
        tbResep.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbResepMouseClicked(evt);
            }
        });
        tbResep.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tbResepPropertyChange(evt);
            }
        });
        tbResep.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbResepKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbResep);

        TabRawat.addTab("Umum", Scroll);

        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(300, 102));
        jPanel3.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll1.setName("Scroll1"); // NOI18N
        Scroll1.setOpaque(true);
        Scroll1.setPreferredSize(new java.awt.Dimension(454, 90));

        tbObatResepRacikan.setName("tbObatResepRacikan"); // NOI18N
        tbObatResepRacikan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbObatResepRacikanKeyPressed(evt);
            }
        });
        Scroll1.setViewportView(tbObatResepRacikan);

        jPanel3.add(Scroll1, java.awt.BorderLayout.PAGE_START);

        Scroll2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll2.setComponentPopupMenu(Popup);
        Scroll2.setName("Scroll2"); // NOI18N
        Scroll2.setOpaque(true);

        tbDetailResepObatRacikan.setAutoCreateRowSorter(true);
        tbDetailResepObatRacikan.setComponentPopupMenu(Popup);
        tbDetailResepObatRacikan.setName("tbDetailResepObatRacikan"); // NOI18N
        tbDetailResepObatRacikan.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tbDetailResepObatRacikanPropertyChange(evt);
            }
        });
        tbDetailResepObatRacikan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbDetailResepObatRacikanKeyPressed(evt);
            }
        });
        Scroll2.setViewportView(tbDetailResepObatRacikan);

        jPanel3.add(Scroll2, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Racikan", jPanel3);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

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
            tbResep.requestFocus();
        }
}//GEN-LAST:event_TCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        if(TabRawat.getSelectedIndex()==0){
            tampilobat();
        }else if(TabRawat.getSelectedIndex()==1){
            if(tbObatResepRacikan.getRowCount()!=0){
                if(tbObatResepRacikan.getSelectedRow()!= -1){
                    if(tbObatResepRacikan.getValueAt(tbObatResepRacikan.getSelectedRow(),0).toString().equals("")||
                            tbObatResepRacikan.getValueAt(tbObatResepRacikan.getSelectedRow(),1).toString().equals("")||
                            tbObatResepRacikan.getValueAt(tbObatResepRacikan.getSelectedRow(),2).toString().equals("")||
                            tbObatResepRacikan.getValueAt(tbObatResepRacikan.getSelectedRow(),3).toString().equals("")||
                            tbObatResepRacikan.getValueAt(tbObatResepRacikan.getSelectedRow(),4).toString().equals("")||
                            tbObatResepRacikan.getValueAt(tbObatResepRacikan.getSelectedRow(),5).toString().equals("")||
                            tbObatResepRacikan.getValueAt(tbObatResepRacikan.getSelectedRow(),6).toString().equals("")){
                        JOptionPane.showMessageDialog(null,"Silahkan lengkapi data racikan..!!");
                    }else{
                        tampildetailracikanresep();
                    }
                }else{
                    JOptionPane.showMessageDialog(null,"Silahkan pilih racikan..!!");
                }
            }else{
                JOptionPane.showMessageDialog(null,"Silahkan masukkan racikan..!!");
            }
        }  
}//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari, BtnAll);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        BtnCariActionPerformed(evt);
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnCari, TCari);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void tbResepMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbResepMouseClicked
        if(tbResep.getRowCount()!=0){
            
        }
}//GEN-LAST:event_tbResepMouseClicked

    private void tbResepKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbResepKeyPressed
        if(tbResep.getRowCount()!=0){
            if(evt.getKeyCode()==KeyEvent.VK_DELETE){
                i=tbResep.getSelectedColumn();
                if((i==1)||(i==8)){
                    if(tbResep.getSelectedRow()!= -1){
                        tbResep.setValueAt("",tbResep.getSelectedRow(),i);
                    }
                }   
            }else if(evt.getKeyCode()==KeyEvent.VK_SHIFT){
                i=tbResep.getSelectedColumn();
                if(i!=11){
                    TCari.requestFocus();
                }                
            }else if(evt.getKeyCode()==KeyEvent.VK_RIGHT){
                i=tbResep.getSelectedColumn();
                if(i==8){
                    akses.setform("DlgCariObat");
                    aturanpakai.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                    aturanpakai.setLocationRelativeTo(internalFrame1);
                    aturanpakai.setVisible(true);
                }else if(i==2){
                    hitungResep();
                }
            }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
                i=tbResep.getSelectedColumn();
                if((i==8)||(i==9)){
                    hitungResep();
                    TCari.requestFocus();
                } 
            }   
        }
}//GEN-LAST:event_tbResepKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

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

private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"pasien");
        }else if(KdDokter.getText().trim().equals("")||NmDokter.getText().trim().equals("")){
            Valid.textKosong(KdDokter,"Dokter");
        }else if(NoResep.getText().trim().equals("")){
            Valid.textKosong(NoResep,"No.Resep");
        }else{
            int reply = JOptionPane.showConfirmDialog(rootPane,"Eeiiiiiits, udah bener belum data yang mau disimpan..??","Konfirmasi",JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {                 
                ChkJln.setSelected(false);        
                if(ubah==false){
                    if(ChkRM.isSelected()==true){
                        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(no_resep,6),signed)),0) from resep_obat where tgl_perawatan like '%"+DTPBeri.getSelectedItem().toString().substring(6,10)+"%' ",DTPBeri.getSelectedItem().toString().substring(6,10),6,NoResep);           
                    }
                    if(Sequel.menyimpantf("resep_obat","?,?,?,?,?,?,?,?","Nomer Resep",8,new String[]{
                        NoResep.getText(),Valid.SetTgl(DTPBeri.getSelectedItem()+""),
                        cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),
                        TNoRw.getText(),KdDokter.getText(),Valid.SetTgl(DTPBeri.getSelectedItem()+""),
                        cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),status
                        })==true){
                            simpandata();
                    }else{
                        if(ChkRM.isSelected()==true){
                            Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(no_resep,6),signed)),0) from resep_obat where tgl_perawatan like '%"+DTPBeri.getSelectedItem().toString().substring(6,10)+"%' ",DTPBeri.getSelectedItem().toString().substring(6,10),6,NoResep);           
                        }
                        if(Sequel.menyimpantf("resep_obat","?,?,?,?,?,?,?,?","Nomer Resep",8,new String[]{
                            NoResep.getText(),Valid.SetTgl(DTPBeri.getSelectedItem()+""),
                            cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),
                            TNoRw.getText(),KdDokter.getText(),Valid.SetTgl(DTPBeri.getSelectedItem()+""),
                            cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),status
                            })==true){
                                simpandata();
                        }else{
                            if(ChkRM.isSelected()==true){
                                Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(no_resep,6),signed)),0) from resep_obat where tgl_perawatan like '%"+DTPBeri.getSelectedItem().toString().substring(6,10)+"%' ",DTPBeri.getSelectedItem().toString().substring(6,10),6,NoResep);           
                            }
                            if(Sequel.menyimpantf("resep_obat","?,?,?,?,?,?,?,?","Nomer Resep",8,new String[]{
                                NoResep.getText(),Valid.SetTgl(DTPBeri.getSelectedItem()+""),
                                cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),
                                TNoRw.getText(),KdDokter.getText(),Valid.SetTgl(DTPBeri.getSelectedItem()+""),
                                cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),status
                                })==true){
                                    simpandata();
                            }
                        }
                    }
                }else if(ubah==true){
                    Sequel.meghapus("resep_dokter","no_resep",NoResep.getText());
                    Sequel.meghapus("resep_dokter_racikan","no_resep",NoResep.getText());
                    Sequel.meghapus("resep_dokter_racikan_detail","no_resep",NoResep.getText());
                    ubah=false;
                    simpandata();
                }                                                      
                ChkJln.setSelected(true);
            }                
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

private void BtnSeek5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek5ActionPerformed
    DlgCariKonversi carikonversi=new DlgCariKonversi(null,false);
    carikonversi.setLocationRelativeTo(internalFrame1);
    carikonversi.setAlwaysOnTop(false);
    carikonversi.setVisible(true);
}//GEN-LAST:event_BtnSeek5ActionPerformed

private void BtnSeek5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeek5KeyPressed
// TODO add your handling code here:
}//GEN-LAST:event_BtnSeek5KeyPressed

private void ppBersihkanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBersihkanActionPerformed
    if(TabRawat.getSelectedIndex()==0){
        for(i=0;i<tbResep.getRowCount();i++){ 
            tbResep.setValueAt("",i,1);
            tbResep.setValueAt(0,i,10);
            tbResep.setValueAt(0,i,9);
            tbResep.setValueAt(0,i,8);
        }
    }else if(TabRawat.getSelectedIndex()==1){
        for(i=0;i<tbDetailResepObatRacikan.getRowCount();i++){ 
            tbDetailResepObatRacikan.setValueAt(1,i,9);
            tbDetailResepObatRacikan.setValueAt(1,i,11);
            tbDetailResepObatRacikan.setValueAt("",i,12);
            tbDetailResepObatRacikan.setValueAt(0,i,13);
        }
    }  
    hitungResep();
}//GEN-LAST:event_ppBersihkanActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        TabRawatMouseClicked(null);
        if(ubah==false){
            emptTeksobat();
        }
            
    }//GEN-LAST:event_formWindowActivated

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        if(ubah==false){
            tampilobat();
        }            
    }//GEN-LAST:event_formWindowOpened

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select concat(pasien.no_rkm_medis,' ',pasien.nm_pasien) from reg_periksa inner join pasien "+
                " on reg_periksa.no_rkm_medis=pasien.no_rkm_medis where no_rawat=? ",TPasien,TNoRw.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_DOWN){
            TCari.requestFocus();
        }else{
            Valid.pindah(evt,KdDokter,DTPBeri);
        }
    }//GEN-LAST:event_TNoRwKeyPressed

    private void KdDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdDokterKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",NmDokter,KdDokter.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnDokterActionPerformed(null);
        }else{
            Valid.pindah(evt,NoResep,BtnSimpan);
        }
    }//GEN-LAST:event_KdDokterKeyPressed

    private void btnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDokterActionPerformed
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.isCek();
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
    }//GEN-LAST:event_btnDokterActionPerformed

    private void btnDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnDokterKeyPressed
        Valid.pindah(evt,KdDokter,BtnSimpan);
    }//GEN-LAST:event_btnDokterKeyPressed

    private void NoResepKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoResepKeyPressed
        Valid.pindah(evt,cmbDtk,KdDokter);
    }//GEN-LAST:event_NoResepKeyPressed

    private void DTPBeriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPBeriKeyPressed
        Valid.pindah(evt,TNoRw,cmbJam);
    }//GEN-LAST:event_DTPBeriKeyPressed

    private void cmbJamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbJamKeyPressed
        Valid.pindah(evt,DTPBeri,cmbMnt);
    }//GEN-LAST:event_cmbJamKeyPressed

    private void cmbMntKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbMntKeyPressed
        Valid.pindah(evt,cmbJam,cmbDtk);
    }//GEN-LAST:event_cmbMntKeyPressed

    private void cmbDtkKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbDtkKeyPressed
        Valid.pindah(evt,cmbMnt,NoResep);
    }//GEN-LAST:event_cmbDtkKeyPressed

    private void ChkRMItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ChkRMItemStateChanged
        if(ChkRM.isSelected()==true){
            NoResep.setEditable(false);
            NoResep.setBackground(new Color(245,250,240));
            Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(no_resep,6),signed)),0) from resep_obat where tgl_perawatan like '%"+DTPBeri.getSelectedItem().toString().substring(6,10)+"%' ",DTPBeri.getSelectedItem().toString().substring(6,10),6,NoResep);
        }else if(ChkRM.isSelected()==false){
            NoResep.setEditable(true);
            NoResep.setBackground(new Color(250,255,245));
            NoResep.setText("");
        }
    }//GEN-LAST:event_ChkRMItemStateChanged

    private void ChkJlnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkJlnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ChkJlnActionPerformed

    private void JeniskelasItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_JeniskelasItemStateChanged
        tampilobat();
    }//GEN-LAST:event_JeniskelasItemStateChanged

    private void JeniskelasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JeniskelasKeyPressed
        Valid.pindah(evt, TCari,BtnKeluar);
    }//GEN-LAST:event_JeniskelasKeyPressed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if(TabRawat.getSelectedIndex()==0){
            BtnTambah1.setVisible(false);
            BtnHapus.setVisible(false);
            TCari.setPreferredSize(new Dimension(245, 23));
        }else if(TabRawat.getSelectedIndex()==1){
            BtnTambah1.setVisible(true);
            BtnHapus.setVisible(true);
            TCari.setPreferredSize(new Dimension(181, 23));
        }
    }//GEN-LAST:event_TabRawatMouseClicked

    private void tbObatResepRacikanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbObatResepRacikanKeyPressed
        if(tbObatResepRacikan.getRowCount()!=0){
            i=tbObatResepRacikan.getSelectedColumn();
            if(evt.getKeyCode()==KeyEvent.VK_RIGHT){
                if(i==5){
                    akses.setform("DlgCariObat");
                    aturanpakai.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
                    aturanpakai.setLocationRelativeTo(internalFrame1);
                    aturanpakai.setVisible(true);
                }else if(i==3){
                    if(tbObatResepRacikan.getValueAt(tbObatResepRacikan.getSelectedRow(),1).equals("")){
                        JOptionPane.showMessageDialog(null,"Silahkan masukkan nama racikan..!!");
                        tbObatResepRacikan.requestFocus();
                    }else{
                        metoderacik.isCek();
                        metoderacik.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
                        metoderacik.setLocationRelativeTo(internalFrame1);
                        metoderacik.setVisible(true);
                    }
                }
            }else if(evt.getKeyCode()==KeyEvent.VK_SHIFT){
                if(i==6){
                    TCari.requestFocus();
                }
            }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
                if(i==6){
                    tampildetailracikanresep();
                }
            }
        }
    }//GEN-LAST:event_tbObatResepRacikanKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if(tbObatResepRacikan.getValueAt(tbObatResepRacikan.getSelectedRow(),1).equals("")&&tbObatResepRacikan.getValueAt(tbObatResepRacikan.getSelectedRow(),4).equals("")&&tbObatResepRacikan.getValueAt(tbObatResepRacikan.getSelectedRow(),5).equals("")&&tbObatResepRacikan.getValueAt(tbObatResepRacikan.getSelectedRow(),6).equals("")){
            tabModeResepRacikan.removeRow(tbObatResepRacikan.getSelectedRow());
        }else{
            JOptionPane.showMessageDialog(null,"Maaf sudah terisi, gak boleh dihapus..!!");
        }

    }//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnTambah1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambah1ActionPerformed
        i=tabModeResepRacikan.getRowCount()+1;
        if(i==99){
            JOptionPane.showMessageDialog(null,"Maksimal 98 Racikan..!!");
        }else{
            tabModeResepRacikan.addRow(new Object[]{""+i,"","","","","",""});
        }
    }//GEN-LAST:event_BtnTambah1ActionPerformed

    private void tbDetailResepObatRacikanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbDetailResepObatRacikanKeyPressed
        if(tbDetailResepObatRacikan.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_RIGHT)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                i=tbDetailResepObatRacikan.getSelectedColumn();
                if((i==11)||(i==9)){
                    try {
                        if(!tbDetailResepObatRacikan.getValueAt(tbDetailResepObatRacikan.getSelectedRow(),11).toString().equals(tbDetailResepObatRacikan.getValueAt(tbDetailResepObatRacikan.getSelectedRow(),9).toString())){
                            if(Valid.SetAngka(tbDetailResepObatRacikan.getValueAt(tbDetailResepObatRacikan.getSelectedRow(),8).toString())==0){
                                JOptionPane.showMessageDialog(null,"Kapasitas obat masih kosong..!!!");
                            }else{
                                tbDetailResepObatRacikan.setValueAt(Valid.SetAngka8(Valid.SetAngka(tbDetailResepObatRacikan.getValueAt(tbDetailResepObatRacikan.getSelectedRow(),8).toString())*
                                    (Valid.SetAngka(tbDetailResepObatRacikan.getValueAt(tbDetailResepObatRacikan.getSelectedRow(),9).toString())/Valid.SetAngka(tbDetailResepObatRacikan.getValueAt(tbDetailResepObatRacikan.getSelectedRow(),11).toString())),1),
                                        tbDetailResepObatRacikan.getSelectedRow(),12);
                            }                                
                        }
                    } catch (Exception e) {
                        tbDetailResepObatRacikan.setValueAt(0,tbDetailResepObatRacikan.getSelectedRow(),12);
                    }                        
                }else if(i==12){
                    if(tbDetailResepObatRacikan.getValueAt(tbDetailResepObatRacikan.getSelectedRow(),12).toString().contains("%")){
                        getDatadetailresepracikan2();
                    }else{
                        getDatadetailresepracikan();
                    }  
                }
            }
        }
    }//GEN-LAST:event_tbDetailResepObatRacikanKeyPressed

    private void tbDetailResepObatRacikanPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tbDetailResepObatRacikanPropertyChange
        if(this.isVisible()==true){
            try {
                if(tbDetailResepObatRacikan.getSelectedRow()!= -1){
                    if(tbDetailResepObatRacikan.getValueAt(tbDetailResepObatRacikan.getSelectedRow(),12).toString().contains("%")){
                        getDatadetailresepracikan2();
                    }
                }else{
                    getDatadetailresepracikan();
                }  
                hitungResep();
            } catch (Exception e) {
            }   
        }
    }//GEN-LAST:event_tbDetailResepObatRacikanPropertyChange

    private void tbResepPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tbResepPropertyChange
        if(this.isVisible()==true){
            hitungResep();
        }
    }//GEN-LAST:event_tbResepPropertyChange

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgPeresepanDokter dialog = new DlgPeresepanDokter(new javax.swing.JFrame(), true);
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
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnSeek5;
    private widget.Button BtnSimpan;
    private widget.Button BtnTambah;
    private widget.Button BtnTambah1;
    private widget.CekBox ChkJln;
    private widget.CekBox ChkRM;
    private widget.Tanggal DTPBeri;
    private widget.PanelBiasa FormInput;
    private widget.ComboBox Jeniskelas;
    private widget.TextBox KdDokter;
    private widget.TextBox KdPj;
    private widget.Label LPpn;
    private widget.Label LTotal;
    private widget.Label LTotalTagihan;
    private widget.TextBox NmDokter;
    private widget.TextBox NoResep;
    private javax.swing.JPopupMenu Popup;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll1;
    private widget.ScrollPane Scroll2;
    private widget.TextBox TCari;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private javax.swing.JTabbedPane TabRawat;
    private widget.Button btnDokter;
    private widget.ComboBox cmbDtk;
    private widget.ComboBox cmbJam;
    private widget.ComboBox cmbMnt;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel11;
    private widget.Label jLabel13;
    private widget.Label jLabel3;
    private widget.Label jLabel5;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private javax.swing.JPanel jPanel3;
    private widget.Label label12;
    private widget.Label label9;
    private widget.panelisi panelisi3;
    private javax.swing.JMenuItem ppBersihkan;
    private widget.Table tbDetailResepObatRacikan;
    private widget.Table tbObatResepRacikan;
    private widget.Table tbResep;
    // End of variables declaration//GEN-END:variables

    public void tampilobat() {        
        z=0;
        for(i=0;i<tbResep.getRowCount();i++){
            if(!tbResep.getValueAt(i,0).toString().equals("")){
                z++;
            }
        }    
        
        pilih=null;
        pilih=new boolean[z]; 
        jumlah=null;
        jumlah=new double[z];
        harga=null;
        harga=new double[z];
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
        aturan=null;
        aturan=new String[z];           
        industri=null;
        industri=new String[z];         
        beli=null;
        beli=new double[z];
        stok=null;
        stok=new double[z]; 
        z=0;        
        for(i=0;i<tbResep.getRowCount();i++){
            if(!tbResep.getValueAt(i,1).toString().equals("")){
                pilih[z]=Boolean.parseBoolean(tbResep.getValueAt(i,0).toString());                
                try {
                    jumlah[z]=Double.parseDouble(tbResep.getValueAt(i,1).toString());
                } catch (Exception e) {
                    jumlah[z]=0;
                }  
                kodebarang[z]=tbResep.getValueAt(i,2).toString();
                namabarang[z]=tbResep.getValueAt(i,3).toString();
                kodesatuan[z]=tbResep.getValueAt(i,4).toString();
                letakbarang[z]=tbResep.getValueAt(i,5).toString();
                try {
                    harga[z]=Double.parseDouble(tbResep.getValueAt(i,6).toString());
                } catch (Exception e) {
                    harga[z]=0;
                }                  
                namajenis[z]=tbResep.getValueAt(i,7).toString();
                aturan[z]=tbResep.getValueAt(i,8).toString();
                industri[z]=tbResep.getValueAt(i,8).toString();
                try {
                    beli[z]=Double.parseDouble(tbResep.getValueAt(i,10).toString());
                } catch (Exception e) {
                    beli[z]=0;
                } 
                
                try {
                    stok[z]=Double.parseDouble(tbResep.getValueAt(i,11).toString());
                } catch (Exception e) {
                    stok[z]=0;
                } 
                z++;
            }
        }
        
        Valid.tabelKosong(tabModeResep);             
        
        for(i=0;i<z;i++){
            tabModeResep.addRow(new Object[] {
                pilih[i],jumlah[i],kodebarang[i],namabarang[i],kodesatuan[i],letakbarang[i],harga[i],namajenis[i],aturan[i],industri[i],beli[i],stok[i]
            });
        }
        
        try {
            if(kenaikan>0){
                psresepasuransi=koneksi.prepareStatement("select databarang.kode_brng, databarang.nama_brng,jenis.nama, databarang.kode_sat,(databarang.h_beli+(databarang.h_beli*?)) as harga,"+
                    " databarang.letak_barang,industrifarmasi.nama_industri,databarang.h_beli,gudangbarang.stok "+
                    " from databarang inner join jenis inner join industrifarmasi inner join gudangbarang "+
                    " on databarang.kdjns=jenis.kdjns and databarang.kode_brng=gudangbarang.kode_brng "+
                    " and industrifarmasi.kode_industri=databarang.kode_industri "+
                    " where  databarang.status='1' and gudangbarang.stok>0 and gudangbarang.kd_bangsal=? and databarang.kode_brng like ? or "+
                    "  databarang.status='1' and gudangbarang.stok>0 and gudangbarang.kd_bangsal=? and databarang.nama_brng like ? or "+
                    "  databarang.status='1' and gudangbarang.stok>0 and gudangbarang.kd_bangsal=? and jenis.nama like ? order by databarang.nama_brng");
                try{
                    psresepasuransi.setDouble(1,kenaikan);
                    psresepasuransi.setString(2,bangsal);
                    psresepasuransi.setString(3,"%"+TCari.getText().trim()+"%");
                    psresepasuransi.setString(4,bangsal);
                    psresepasuransi.setString(5,"%"+TCari.getText().trim()+"%");
                    psresepasuransi.setString(6,bangsal);
                    psresepasuransi.setString(7,"%"+TCari.getText().trim()+"%");
                    rsobat=psresepasuransi.executeQuery();
                    while(rsobat.next()){
                        tabModeResep.addRow(new Object[] {false,"",rsobat.getString("kode_brng"),rsobat.getString("nama_brng"),
                           rsobat.getString("kode_sat"),rsobat.getString("letak_barang"),Valid.roundUp(rsobat.getDouble("harga"),100),
                           rsobat.getString("nama"),"",rsobat.getString("nama_industri"),
                           rsobat.getDouble("h_beli"),rsobat.getDouble("stok")
                        });          
                    }  
                }catch(Exception e){
                    System.out.println("Notifikasi : "+e);
                }finally{
                    if(rsobat != null){
                        rsobat.close();
                    }

                    if(psresepasuransi != null){
                        psresepasuransi.close();
                    }
                }                                   
            }else{    
                psresep=koneksi.prepareStatement(
                    "select databarang.kode_brng, databarang.nama_brng,jenis.nama, databarang.kode_sat,"+
                    " databarang.karyawan,databarang.ralan,databarang.beliluar,databarang.kelas1," +
                    " databarang.kelas2,databarang.kelas3,databarang.vip,databarang.vvip,"+
                    " databarang.letak_barang,databarang.utama,industrifarmasi.nama_industri,databarang.h_beli,gudangbarang.stok "+
                    " from databarang inner join jenis inner join industrifarmasi inner join gudangbarang "+
                    " on databarang.kdjns=jenis.kdjns and databarang.kode_brng=gudangbarang.kode_brng "+
                    " and industrifarmasi.kode_industri=databarang.kode_industri "+
                    " where  databarang.status='1' and gudangbarang.stok>0 and gudangbarang.kd_bangsal=? and databarang.kode_brng like ? or "+
                    "  databarang.status='1' and gudangbarang.stok>0 and gudangbarang.kd_bangsal=? and databarang.nama_brng like ? or "+
                    "  databarang.status='1' and gudangbarang.stok>0 and gudangbarang.kd_bangsal=? and jenis.nama like ? order by databarang.nama_brng");
                try{
                    psresep.setString(1,bangsal);
                    psresep.setString(2,"%"+TCari.getText().trim()+"%");
                    psresep.setString(3,bangsal);
                    psresep.setString(4,"%"+TCari.getText().trim()+"%");
                    psresep.setString(5,bangsal);
                    psresep.setString(6,"%"+TCari.getText().trim()+"%");
                    rsobat=psresep.executeQuery();
                    while(rsobat.next()){
                        if(Jeniskelas.getSelectedItem().equals("Karyawan")){
                            tabModeResep.addRow(new Object[] {false,"",rsobat.getString("kode_brng"),rsobat.getString("nama_brng"),
                               rsobat.getString("kode_sat"),rsobat.getString("letak_barang"),Valid.roundUp(rsobat.getDouble("karyawan"),100),
                               rsobat.getString("nama"),"",rsobat.getString("nama_industri"),
                               rsobat.getDouble("h_beli"),rsobat.getDouble("stok")
                            });
                        }else if(Jeniskelas.getSelectedItem().equals("Rawat Jalan")){
                            tabModeResep.addRow(new Object[] {false,"",rsobat.getString("kode_brng"),rsobat.getString("nama_brng"),
                               rsobat.getString("kode_sat"),rsobat.getString("letak_barang"),Valid.roundUp(rsobat.getDouble("ralan"),100),
                               rsobat.getString("nama"),"",rsobat.getString("nama_industri"),
                               rsobat.getDouble("h_beli"),rsobat.getDouble("stok")
                            });
                        }else if(Jeniskelas.getSelectedItem().equals("Beli Luar")){
                            tabModeResep.addRow(new Object[] {false,"",rsobat.getString("kode_brng"),rsobat.getString("nama_brng"),
                               rsobat.getString("kode_sat"),rsobat.getString("letak_barang"),Valid.roundUp(rsobat.getDouble("beliluar"),100),
                               rsobat.getString("nama"),"",rsobat.getString("nama_industri"),
                               rsobat.getDouble("h_beli"),rsobat.getDouble("stok")
                            });
                        }else if(Jeniskelas.getSelectedItem().equals("Utama/BPJS")){
                            tabModeResep.addRow(new Object[] {false,"",rsobat.getString("kode_brng"),rsobat.getString("nama_brng"),
                               rsobat.getString("kode_sat"),rsobat.getString("letak_barang"),Valid.roundUp(rsobat.getDouble("utama"),100),
                               rsobat.getString("nama"),"",rsobat.getString("nama_industri"),
                               rsobat.getDouble("h_beli"),rsobat.getDouble("stok")
                            });
                        }else if(Jeniskelas.getSelectedItem().equals("Kelas 1")){
                            tabModeResep.addRow(new Object[] {false,"",rsobat.getString("kode_brng"),rsobat.getString("nama_brng"),
                               rsobat.getString("kode_sat"),rsobat.getString("letak_barang"),Valid.roundUp(rsobat.getDouble("kelas1"),100),
                               rsobat.getString("nama"),"",rsobat.getString("nama_industri"),
                               rsobat.getDouble("h_beli"),rsobat.getDouble("stok")
                            });
                        }else if(Jeniskelas.getSelectedItem().equals("Kelas 2")){
                            tabModeResep.addRow(new Object[] {false,"",rsobat.getString("kode_brng"),rsobat.getString("nama_brng"),
                               rsobat.getString("kode_sat"),rsobat.getString("letak_barang"),Valid.roundUp(rsobat.getDouble("kelas2"),100),
                               rsobat.getString("nama"),"",rsobat.getString("nama_industri"),
                               rsobat.getDouble("h_beli"),rsobat.getDouble("stok")
                            });
                        }else if(Jeniskelas.getSelectedItem().equals("Kelas 3")){
                            tabModeResep.addRow(new Object[] {false,"",rsobat.getString("kode_brng"),rsobat.getString("nama_brng"),
                               rsobat.getString("kode_sat"),rsobat.getString("letak_barang"),Valid.roundUp(rsobat.getDouble("kelas3"),100),
                               rsobat.getString("nama"),"",rsobat.getString("nama_industri"),
                               rsobat.getDouble("h_beli"),rsobat.getDouble("stok")
                            });
                        }else if(Jeniskelas.getSelectedItem().equals("VIP")){
                            tabModeResep.addRow(new Object[] {false,"",rsobat.getString("kode_brng"),rsobat.getString("nama_brng"),
                               rsobat.getString("kode_sat"),rsobat.getString("letak_barang"),Valid.roundUp(rsobat.getDouble("vip"),100),
                               rsobat.getString("nama"),"",rsobat.getString("nama_industri"),
                               rsobat.getDouble("h_beli"),rsobat.getDouble("stok")
                            });
                        }else if(Jeniskelas.getSelectedItem().equals("VVIP")){
                            tabModeResep.addRow(new Object[] {false,"",rsobat.getString("kode_brng"),rsobat.getString("nama_brng"),
                               rsobat.getString("kode_sat"),rsobat.getString("letak_barang"),Valid.roundUp(rsobat.getDouble("vvip"),100),
                               rsobat.getString("nama"),"",rsobat.getString("nama_industri"),
                               rsobat.getDouble("h_beli"),rsobat.getDouble("stok")
                            });
                        }                 
                    }
                }catch(Exception e){
                    System.out.println("Notifikasi : "+e);
                }finally{
                    if(rsobat != null){
                        rsobat.close();
                    }

                    if(psresep != null){
                        psresep.close();
                    }
                }
            }  
        } catch (Exception e) {
            System.out.println("Notifikasi : "+e);
        }            
    }

    public void emptTeksobat() {
        if(ChkRM.isSelected()==true){
            Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(no_resep,6),signed)),0) from resep_obat where tgl_perawatan like '%"+DTPBeri.getSelectedItem().toString().substring(6,10)+"%' ",DTPBeri.getSelectedItem().toString().substring(6,10),6,NoResep);           
        } 
    }

    public JTable getTable(){
        return tbResep;
    }
    
    public Button getButton(){
        return BtnSimpan;
    }
    
    public void isCek(){   
        BtnTambah.setEnabled(akses.getresep_dokter());
        TCari.requestFocus();
        if(status.equals("ralan")){
            bangsal=Sequel.cariIsi("select kd_bangsal from set_depo_ralan where kd_poli=?",Sequel.cariIsi("select kd_poli from reg_periksa where no_rawat=?",TNoRw.getText()));
            if(bangsal.equals("")){
                bangsal=bangsaldefault;
            }
        }else if(status.equals("ranap")){
            bangsal=akses.getkdbangsal();
        }            
    }
    
    public void setNoRm(String norwt,Date tanggal, String jam,String menit,String detik,String KodeDokter,String NamaDokter,String status) {        
        TNoRw.setText(norwt);
        Sequel.cariIsi("select concat(pasien.no_rkm_medis,' ',pasien.nm_pasien) from reg_periksa inner join pasien "+
                    " on reg_periksa.no_rkm_medis=pasien.no_rkm_medis where no_rawat=? ",TPasien,TNoRw.getText());
        
        DTPBeri.setDate(tanggal);
        cmbJam.setSelectedItem(jam);
        cmbMnt.setSelectedItem(menit);
        cmbDtk.setSelectedItem(detik); 
        KdDokter.setText(KodeDokter);
        NmDokter.setText(NamaDokter);
        KdPj.setText(Sequel.cariIsi("select kd_pj from reg_periksa where no_rawat=?",norwt));
        kenaikan=Sequel.cariIsiAngka("select (hargajual/100) from set_harga_obat_ralan where kd_pj=?",KdPj.getText());
        TCari.requestFocus();
        this.status=status;
        SetHarga();
        ubah=false;
    }
    
    public void setNoRm(String norwt,String KodeDokter,String NamaDokter,String Pasien,String kodepj,String status) {        
        TNoRw.setText(norwt);
        TPasien.setText(Pasien);
        KdDokter.setText(KodeDokter);
        NmDokter.setText(NamaDokter);
        KdPj.setText(kodepj);
        kenaikan=Sequel.cariIsiAngka("select (hargajual/100) from set_harga_obat_ralan where kd_pj=?",KdPj.getText());
        TCari.requestFocus();
        this.status=status;
        SetHarga();
        ubah=false;
    }
    
    public void setNoRm(String norwt,Date tanggal,String status) {        
        TNoRw.setText(norwt);
        Sequel.cariIsi("select concat(pasien.no_rkm_medis,' ',pasien.nm_pasien) from reg_periksa inner join pasien "+
                    " on reg_periksa.no_rkm_medis=pasien.no_rkm_medis where no_rawat=? ",TPasien,TNoRw.getText());
        
        DTPBeri.setDate(tanggal);
        KdDokter.setText(Sequel.cariIsi("select kd_dokter from dpjp_ranap where no_rawat=?",norwt));
        if(KdDokter.getText().equals("")){
            KdDokter.setText(Sequel.cariIsi("select kd_dokter from reg_periksa where no_rawat=?",norwt));
        }
        Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",NmDokter,KdDokter.getText());
        
        KdPj.setText(Sequel.cariIsi("select kd_pj from reg_periksa where no_rawat=?",norwt));
        kenaikan=Sequel.cariIsiAngka("select (hargajual/100) from set_harga_obat_ralan where kd_pj=?",KdPj.getText());
        TCari.requestFocus();
        this.status=status;
        SetHarga();
        ubah=false;
    }
    
    private void jam(){
        ActionListener taskPerformer = new ActionListener(){
            private int nilai_jam;
            private int nilai_menit;
            private int nilai_detik;
            @Override
            public void actionPerformed(ActionEvent e) {
                String nol_jam = "";
                String nol_menit = "";
                String nol_detik = "";
                // Membuat Date
                //Date dt = new Date();
                Date now = Calendar.getInstance().getTime();

                // Mengambil nilaj JAM, MENIT, dan DETIK Sekarang
                if(ChkJln.isSelected()==true){
                    nilai_jam = now.getHours();
                    nilai_menit = now.getMinutes();
                    nilai_detik = now.getSeconds();
                }else if(ChkJln.isSelected()==false){
                    nilai_jam =cmbJam.getSelectedIndex();
                    nilai_menit =cmbMnt.getSelectedIndex();
                    nilai_detik =cmbDtk.getSelectedIndex();
                }

                // Jika nilai JAM lebih kecil dari 10 (hanya 1 digit)
                if (nilai_jam <= 9) {
                    // Tambahkan "0" didepannya
                    nol_jam = "0";
                }
                // Jika nilai MENIT lebih kecil dari 10 (hanya 1 digit)
                if (nilai_menit <= 9) {
                    // Tambahkan "0" didepannya
                    nol_menit = "0";
                }
                // Jika nilai DETIK lebih kecil dari 10 (hanya 1 digit)
                if (nilai_detik <= 9) {
                    // Tambahkan "0" didepannya
                    nol_detik = "0";
                }
                // Membuat String JAM, MENIT, DETIK
                String jam = nol_jam + Integer.toString(nilai_jam);
                String menit = nol_menit + Integer.toString(nilai_menit);
                String detik = nol_detik + Integer.toString(nilai_detik);
                // Menampilkan pada Layar
                //tampil_jam.setText("  " + jam + " : " + menit + " : " + detik + "  ");
                cmbJam.setSelectedItem(jam);
                cmbMnt.setSelectedItem(menit);
                cmbDtk.setSelectedItem(detik);
            }
        };
        // Timer
        new Timer(1000, taskPerformer).start();
    }
    
    public void tampildetailracikanresep() {        
        z=0;
        for(i=0;i<tbDetailResepObatRacikan.getRowCount();i++){
            if(Valid.SetAngka(tbDetailResepObatRacikan.getValueAt(i,13).toString())>0){
                z++;
            }
        }    
        
        pilih=null;
        pilih=new boolean[z]; 
        jumlah=null;
        jumlah=new double[z];
        harga=null;
        harga=new double[z];
        stok=null;
        stok=new double[z];
        p1=null;
        p1=new double[z];
        p2=null;
        p2=new double[z];
        kodebarang=null;
        kodebarang=new String[z];
        namabarang=null;
        namabarang=new String[z];
        kodesatuan=null;
        kodesatuan=new String[z];
        letakbarang=null;
        letakbarang=new String[z];
        no=null;
        no=new String[z];
        namajenis=null;
        namajenis=new String[z];        
        industri=null;
        industri=new String[z];         
        beli=null;
        beli=new double[z];     
        kapasitas=null;
        kapasitas=new double[z];   
        kandungan=null;
        kandungan=new String[z];
        z=0;        
        for(i=0;i<tbDetailResepObatRacikan.getRowCount();i++){
            if(Valid.SetAngka(tbDetailResepObatRacikan.getValueAt(i,13).toString())>0){
                no[z]=tbDetailResepObatRacikan.getValueAt(i,0).toString();
                kodebarang[z]=tbDetailResepObatRacikan.getValueAt(i,1).toString();
                namabarang[z]=tbDetailResepObatRacikan.getValueAt(i,2).toString();
                kodesatuan[z]=tbDetailResepObatRacikan.getValueAt(i,3).toString();
                try {
                    harga[z]=Double.parseDouble(tbDetailResepObatRacikan.getValueAt(i,4).toString());
                } catch (Exception e) {
                    harga[z]=0;
                }
                try {
                    beli[z]=Double.parseDouble(tbDetailResepObatRacikan.getValueAt(i,5).toString());
                } catch (Exception e) {
                    beli[z]=0;
                }
                namajenis[z]=tbDetailResepObatRacikan.getValueAt(i,6).toString();
                try {
                    stok[z]=Double.parseDouble(tbDetailResepObatRacikan.getValueAt(i,7).toString());
                } catch (Exception e) {
                    stok[z]=0;
                }                
                try {
                    kapasitas[z]=Double.parseDouble(tbDetailResepObatRacikan.getValueAt(i,8).toString());
                } catch (Exception e) {
                    kapasitas[z]=0;
                }          
                try {
                    p1[z]=Double.parseDouble(tbDetailResepObatRacikan.getValueAt(i,9).toString());
                } catch (Exception e) {
                    p1[z]=0;
                } 
                try {
                    p2[z]=Double.parseDouble(tbDetailResepObatRacikan.getValueAt(i,11).toString());
                } catch (Exception e) {
                    p2[z]=0;
                } 
                kandungan[z]=tbDetailResepObatRacikan.getValueAt(i,12).toString();
                try {
                    jumlah[z]=Double.parseDouble(tbDetailResepObatRacikan.getValueAt(i,13).toString());
                } catch (Exception e) {
                    jumlah[z]=0;
                }                 
                industri[z]=tbDetailResepObatRacikan.getValueAt(i,14).toString();
                z++;
            }
        }
        
        Valid.tabelKosong(tabModeDetailResepRacikan);             
        
        for(i=0;i<z;i++){
            tabModeDetailResepRacikan.addRow(new Object[] {
                no[i],kodebarang[i],namabarang[i],kodesatuan[i],harga[i],beli[i],
                namajenis[i],stok[i],kapasitas[i],p1[i],"/",p2[i],kandungan[i],
                jumlah[i],industri[i]
            });
        }
        
        try {
            if(kenaikan>0){
                psresepasuransi=koneksi.prepareStatement("select databarang.kode_brng, databarang.nama_brng,jenis.nama, databarang.kode_sat,(databarang.h_beli+(databarang.h_beli*?)) as harga,"+
                    " databarang.letak_barang,industrifarmasi.nama_industri,databarang.h_beli,gudangbarang.stok,databarang.kapasitas "+
                    " from databarang inner join jenis inner join industrifarmasi inner join gudangbarang "+
                    " on databarang.kdjns=jenis.kdjns and databarang.kode_brng=gudangbarang.kode_brng "+
                    " and industrifarmasi.kode_industri=databarang.kode_industri "+
                    " where  databarang.status='1' and gudangbarang.stok>0 and gudangbarang.kd_bangsal=? and databarang.kode_brng like ? or "+
                    "  databarang.status='1' and gudangbarang.stok>0 and gudangbarang.kd_bangsal=? and databarang.nama_brng like ? or "+
                    "  databarang.status='1' and gudangbarang.stok>0 and gudangbarang.kd_bangsal=? and jenis.nama like ? order by databarang.nama_brng");
                try{ 
                    psresepasuransi.setDouble(1,kenaikan);
                    psresepasuransi.setString(2,bangsal);
                    psresepasuransi.setString(3,"%"+TCari.getText().trim()+"%");
                    psresepasuransi.setString(4,bangsal);
                    psresepasuransi.setString(5,"%"+TCari.getText().trim()+"%");
                    psresepasuransi.setString(6,bangsal);
                    psresepasuransi.setString(7,"%"+TCari.getText().trim()+"%");
                    rsobat=psresepasuransi.executeQuery();
                    while(rsobat.next()){
                        tabModeDetailResepRacikan.addRow(new Object[] {
                            tbObatResepRacikan.getValueAt(tbObatResepRacikan.getSelectedRow(),0).toString(),
                            rsobat.getString("kode_brng"),rsobat.getString("nama_brng"),
                            rsobat.getString("kode_sat"),Valid.roundUp(rsobat.getDouble("harga"),100),
                            rsobat.getDouble("h_beli"),rsobat.getString("nama"),rsobat.getDouble("stok"),
                            rsobat.getDouble("kapasitas"),1,"/",1,"",0,rsobat.getString("nama_industri")
                        });          
                    }  
                }catch(Exception e){
                    System.out.println("Notifikasi : "+e);
                }finally{
                    if(rsobat != null){
                        rsobat.close();
                    }
                    if(psresepasuransi != null){
                        psresepasuransi.close();
                    }
                }               
            }else{
                psresep=koneksi.prepareStatement(
                    "select databarang.kode_brng, databarang.nama_brng,jenis.nama, databarang.kode_sat,"+
                    " databarang.karyawan,databarang.ralan,databarang.beliluar,databarang.kelas1," +
                    " databarang.kelas2,databarang.kelas3,databarang.vip,databarang.vvip,"+
                    " databarang.letak_barang,databarang.utama,industrifarmasi.nama_industri,databarang.h_beli,gudangbarang.stok,databarang.kapasitas "+
                    " from databarang inner join jenis inner join industrifarmasi inner join gudangbarang "+
                    " on databarang.kdjns=jenis.kdjns and databarang.kode_brng=gudangbarang.kode_brng "+
                    " and industrifarmasi.kode_industri=databarang.kode_industri "+
                    " where  databarang.status='1' and gudangbarang.stok>0 and gudangbarang.kd_bangsal=? and databarang.kode_brng like ? or "+
                    "  databarang.status='1' and gudangbarang.stok>0 and gudangbarang.kd_bangsal=? and databarang.nama_brng like ? or "+
                    "  databarang.status='1' and gudangbarang.stok>0 and gudangbarang.kd_bangsal=? and jenis.nama like ? order by databarang.nama_brng");
                try{ 
                    psresep.setString(1,bangsal);
                    psresep.setString(2,"%"+TCari.getText().trim()+"%");
                    psresep.setString(3,bangsal);
                    psresep.setString(4,"%"+TCari.getText().trim()+"%");
                    psresep.setString(5,bangsal);
                    psresep.setString(6,"%"+TCari.getText().trim()+"%");
                    rsobat=psresep.executeQuery();
                    while(rsobat.next()){
                        if(Jeniskelas.getSelectedItem().equals("Karyawan")){
                            tabModeDetailResepRacikan.addRow(new Object[] {
                                tbObatResepRacikan.getValueAt(tbObatResepRacikan.getSelectedRow(),0).toString(),
                                rsobat.getString("kode_brng"),rsobat.getString("nama_brng"),
                                rsobat.getString("kode_sat"),Valid.roundUp(rsobat.getDouble("karyawan"),100),
                                rsobat.getDouble("h_beli"),rsobat.getString("nama"),rsobat.getDouble("stok"),
                                rsobat.getDouble("kapasitas"),1,"/",1,"",0,rsobat.getString("nama_industri")
                            }); 
                        }else if(Jeniskelas.getSelectedItem().equals("Rawat Jalan")){
                            tabModeDetailResepRacikan.addRow(new Object[] {
                                tbObatResepRacikan.getValueAt(tbObatResepRacikan.getSelectedRow(),0).toString(),
                                rsobat.getString("kode_brng"),rsobat.getString("nama_brng"),
                                rsobat.getString("kode_sat"),Valid.roundUp(rsobat.getDouble("ralan"),100),
                                rsobat.getDouble("h_beli"),rsobat.getString("nama"),rsobat.getDouble("stok"),
                                rsobat.getDouble("kapasitas"),1,"/",1,"",0,rsobat.getString("nama_industri")
                            }); 
                        }else if(Jeniskelas.getSelectedItem().equals("Beli Luar")){
                            tabModeDetailResepRacikan.addRow(new Object[] {
                                tbObatResepRacikan.getValueAt(tbObatResepRacikan.getSelectedRow(),0).toString(),
                                rsobat.getString("kode_brng"),rsobat.getString("nama_brng"),
                                rsobat.getString("kode_sat"),Valid.roundUp(rsobat.getDouble("beliluar"),100),
                                rsobat.getDouble("h_beli"),rsobat.getString("nama"),rsobat.getDouble("stok"),
                                rsobat.getDouble("kapasitas"),1,"/",1,"",0,rsobat.getString("nama_industri")
                            }); 
                        }else if(Jeniskelas.getSelectedItem().equals("Utama/BPJS")){
                            tabModeDetailResepRacikan.addRow(new Object[] {
                                tbObatResepRacikan.getValueAt(tbObatResepRacikan.getSelectedRow(),0).toString(),
                                rsobat.getString("kode_brng"),rsobat.getString("nama_brng"),
                                rsobat.getString("kode_sat"),Valid.roundUp(rsobat.getDouble("utama"),100),
                                rsobat.getDouble("h_beli"),rsobat.getString("nama"),rsobat.getDouble("stok"),
                                rsobat.getDouble("kapasitas"),1,"/",1,"",0,rsobat.getString("nama_industri")
                            }); 
                        }else if(Jeniskelas.getSelectedItem().equals("Kelas 1")){
                            tabModeDetailResepRacikan.addRow(new Object[] {
                                tbObatResepRacikan.getValueAt(tbObatResepRacikan.getSelectedRow(),0).toString(),
                                rsobat.getString("kode_brng"),rsobat.getString("nama_brng"),
                                rsobat.getString("kode_sat"),Valid.roundUp(rsobat.getDouble("kelas1"),100),
                                rsobat.getDouble("h_beli"),rsobat.getString("nama"),rsobat.getDouble("stok"),
                                rsobat.getDouble("kapasitas"),1,"/",1,"",0,rsobat.getString("nama_industri")
                            }); 
                        }else if(Jeniskelas.getSelectedItem().equals("Kelas 2")){
                            tabModeDetailResepRacikan.addRow(new Object[] {
                                tbObatResepRacikan.getValueAt(tbObatResepRacikan.getSelectedRow(),0).toString(),
                                rsobat.getString("kode_brng"),rsobat.getString("nama_brng"),
                                rsobat.getString("kode_sat"),Valid.roundUp(rsobat.getDouble("kelas2"),100),
                                rsobat.getDouble("h_beli"),rsobat.getString("nama"),rsobat.getDouble("stok"),
                                rsobat.getDouble("kapasitas"),1,"/",1,"",0,rsobat.getString("nama_industri")
                            }); 
                        }else if(Jeniskelas.getSelectedItem().equals("Kelas 3")){
                            tabModeDetailResepRacikan.addRow(new Object[] {
                                tbObatResepRacikan.getValueAt(tbObatResepRacikan.getSelectedRow(),0).toString(),
                                rsobat.getString("kode_brng"),rsobat.getString("nama_brng"),
                                rsobat.getString("kode_sat"),Valid.roundUp(rsobat.getDouble("kelas3"),100),
                                rsobat.getDouble("h_beli"),rsobat.getString("nama"),rsobat.getDouble("stok"),
                                rsobat.getDouble("kapasitas"),1,"/",1,"",0,rsobat.getString("nama_industri")
                            }); 
                        }else if(Jeniskelas.getSelectedItem().equals("VIP")){
                            tabModeDetailResepRacikan.addRow(new Object[] {
                                tbObatResepRacikan.getValueAt(tbObatResepRacikan.getSelectedRow(),0).toString(),
                                rsobat.getString("kode_brng"),rsobat.getString("nama_brng"),
                                rsobat.getString("kode_sat"),Valid.roundUp(rsobat.getDouble("vip"),100),
                                rsobat.getDouble("h_beli"),rsobat.getString("nama"),rsobat.getDouble("stok"),
                                rsobat.getDouble("kapasitas"),1,"/",1,"",0,rsobat.getString("nama_industri")
                            }); 
                        }else if(Jeniskelas.getSelectedItem().equals("VVIP")){
                            tabModeDetailResepRacikan.addRow(new Object[] {
                                tbObatResepRacikan.getValueAt(tbObatResepRacikan.getSelectedRow(),0).toString(),
                                rsobat.getString("kode_brng"),rsobat.getString("nama_brng"),
                                rsobat.getString("kode_sat"),Valid.roundUp(rsobat.getDouble("vvip"),100),
                                rsobat.getDouble("h_beli"),rsobat.getString("nama"),rsobat.getDouble("stok"),
                                rsobat.getDouble("kapasitas"),1,"/",1,"",0,rsobat.getString("nama_industri")
                            }); 
                        }                   
                    }
                }catch(Exception e){
                    System.out.println("Notifikasi : "+e);
                }finally{
                    if(rsobat != null){
                        rsobat.close();
                    }
                    if(psresep != null){
                        psresep.close();
                    }
                }
            }       
        } catch (Exception e) {
            System.out.println("Notifikasi : "+e);
        }            
    }

    private void getDatadetailresepracikan() {
        if(tbDetailResepObatRacikan.getSelectedRow()!= -1){
            try {
                tbDetailResepObatRacikan.setValueAt(Valid.SetAngka8((Double.parseDouble(tbObatResepRacikan.getValueAt(tbObatResepRacikan.getSelectedRow(),4).toString())
                                *Double.parseDouble(tbDetailResepObatRacikan.getValueAt(tbDetailResepObatRacikan.getSelectedRow(),12).toString()))
                                /Double.parseDouble(tbDetailResepObatRacikan.getValueAt(tbDetailResepObatRacikan.getSelectedRow(),8).toString()),1)
                                ,tbDetailResepObatRacikan.getSelectedRow(),13);
            } catch (Exception e) {
                tbDetailResepObatRacikan.setValueAt(0,tbDetailResepObatRacikan.getSelectedRow(),13);
            }
        }
    }
    
    private void getDatadetailresepracikan2() {
        if(tbDetailResepObatRacikan.getSelectedRow()!= -1){
            try {
                r=tbDetailResepObatRacikan.getSelectedRow();
                noracik=tbDetailResepObatRacikan.getValueAt(r,0).toString();
                jumlahracik=0;
                persenracik=Double.parseDouble(tbDetailResepObatRacikan.getValueAt(r,12).toString().replaceAll("%",""));
                kapasitasracik=Double.parseDouble(tbDetailResepObatRacikan.getValueAt(r,8).toString());
                for(i=0;i<tbDetailResepObatRacikan.getRowCount();i++){ 
                    if(noracik==tbDetailResepObatRacikan.getValueAt(i,0).toString()){
                        if(!tbDetailResepObatRacikan.getValueAt(i,12).toString().contains("%")){
                            jumlahracik=jumlahracik+(Double.parseDouble(tbDetailResepObatRacikan.getValueAt(i,8).toString())*
                                    Double.parseDouble(tbDetailResepObatRacikan.getValueAt(i,13).toString()));
                        }
                    }
                }
                tbDetailResepObatRacikan.setValueAt(Valid.SetAngka8((jumlahracik*(persenracik/100))/kapasitasracik,1),r,13);
            } catch (Exception e) {
                tbDetailResepObatRacikan.setValueAt(0,r,13);
            }
        }
    }
    
    public void tampilobat(String no_resep) {
        NoResep.setText(no_resep);
        ubah=true;
        try {
            Valid.tabelKosong(tabModeResep);
            Valid.tabelKosong(tabModeResepRacikan);
            Valid.tabelKosong(tabModeDetailResepRacikan);
            if(kenaikan>0){
                psresepasuransi=koneksi.prepareStatement("select databarang.kode_brng, databarang.nama_brng,jenis.nama, databarang.kode_sat,(databarang.h_beli+(databarang.h_beli*?)) as harga,"+
                    " databarang.letak_barang,industrifarmasi.nama_industri,databarang.h_beli,gudangbarang.stok,resep_dokter.jml, resep_dokter.aturan_pakai "+
                    " from databarang inner join jenis inner join industrifarmasi inner join gudangbarang inner join resep_dokter"+
                    " on databarang.kdjns=jenis.kdjns and databarang.kode_brng=gudangbarang.kode_brng "+
                    " and industrifarmasi.kode_industri=databarang.kode_industri and resep_dokter.kode_brng=databarang.kode_brng "+
                    " where databarang.status='1' and gudangbarang.kd_bangsal=? and resep_dokter.no_resep=? order by databarang.nama_brng");
                try{
                    psresepasuransi.setDouble(1,kenaikan);
                    psresepasuransi.setString(2,bangsal);
                    psresepasuransi.setString(3,no_resep);
                    rsobat=psresepasuransi.executeQuery();
                    while(rsobat.next()){
                        tabModeResep.addRow(new Object[] {
                           false,rsobat.getDouble("jml"),rsobat.getString("kode_brng"),rsobat.getString("nama_brng"),
                           rsobat.getString("kode_sat"),rsobat.getString("letak_barang"),Valid.roundUp(rsobat.getDouble("harga"),100),
                           rsobat.getString("nama"),rsobat.getString("aturan_pakai"),rsobat.getString("nama_industri"),
                           rsobat.getDouble("h_beli"),rsobat.getDouble("stok")
                        });          
                    }  
                }catch(Exception e){
                    System.out.println("Notifikasi : "+e);
                }finally{
                    if(rsobat != null){
                        rsobat.close();
                    }

                    if(psresepasuransi != null){
                        psresepasuransi.close();
                    }
                }                                   
            }else{    
                psresep=koneksi.prepareStatement("select databarang.kode_brng, databarang.nama_brng,jenis.nama, databarang.kode_sat,"+
                    " databarang.karyawan,databarang.ralan,databarang.beliluar,databarang.kelas1," +
                    " databarang.kelas2,databarang.kelas3,databarang.vip,databarang.vvip,"+
                    " databarang.letak_barang,databarang.utama,industrifarmasi.nama_industri,databarang.h_beli,gudangbarang.stok,resep_dokter.jml, resep_dokter.aturan_pakai "+
                    " from databarang inner join jenis inner join industrifarmasi inner join gudangbarang inner join resep_dokter "+
                    " on databarang.kdjns=jenis.kdjns and databarang.kode_brng=gudangbarang.kode_brng "+
                    " and industrifarmasi.kode_industri=databarang.kode_industri and resep_dokter.kode_brng=databarang.kode_brng "+
                    " where  databarang.status='1' and gudangbarang.kd_bangsal=? and resep_dokter.no_resep=? order by databarang.nama_brng");
                try{
                    psresep.setString(1,bangsal);
                    psresep.setString(2,no_resep);
                    rsobat=psresep.executeQuery();
                    while(rsobat.next()){
                        if(Jeniskelas.getSelectedItem().equals("Karyawan")){
                            tabModeResep.addRow(new Object[] {
                               false,rsobat.getDouble("jml"),rsobat.getString("kode_brng"),rsobat.getString("nama_brng"),
                               rsobat.getString("kode_sat"),rsobat.getString("letak_barang"),Valid.roundUp(rsobat.getDouble("karyawan"),100),
                               rsobat.getString("nama"),rsobat.getString("aturan_pakai"),rsobat.getString("nama_industri"),
                               rsobat.getDouble("h_beli"),rsobat.getDouble("stok")
                            });
                        }else if(Jeniskelas.getSelectedItem().equals("Rawat Jalan")){
                            tabModeResep.addRow(new Object[] {
                               false,rsobat.getDouble("jml"),rsobat.getString("kode_brng"),rsobat.getString("nama_brng"),
                               rsobat.getString("kode_sat"),rsobat.getString("letak_barang"),Valid.roundUp(rsobat.getDouble("ralan"),100),
                               rsobat.getString("nama"),rsobat.getString("aturan_pakai"),rsobat.getString("nama_industri"),
                               rsobat.getDouble("h_beli"),rsobat.getDouble("stok")
                            });
                        }else if(Jeniskelas.getSelectedItem().equals("Beli Luar")){
                            tabModeResep.addRow(new Object[] {
                               false,rsobat.getDouble("jml"),rsobat.getString("kode_brng"),rsobat.getString("nama_brng"),
                               rsobat.getString("kode_sat"),rsobat.getString("letak_barang"),Valid.roundUp(rsobat.getDouble("beliluar"),100),
                               rsobat.getString("nama"),rsobat.getString("aturan_pakai"),rsobat.getString("nama_industri"),
                               rsobat.getDouble("h_beli"),rsobat.getDouble("stok")
                            });
                        }else if(Jeniskelas.getSelectedItem().equals("Utama/BPJS")){
                            tabModeResep.addRow(new Object[] {
                               false,rsobat.getDouble("jml"),rsobat.getString("kode_brng"),rsobat.getString("nama_brng"),
                               rsobat.getString("kode_sat"),rsobat.getString("letak_barang"),Valid.roundUp(rsobat.getDouble("utama"),100),
                               rsobat.getString("nama"),rsobat.getString("aturan_pakai"),rsobat.getString("nama_industri"),
                               rsobat.getDouble("h_beli"),rsobat.getDouble("stok")
                            });
                        }else if(Jeniskelas.getSelectedItem().equals("Kelas 1")){
                            tabModeResep.addRow(new Object[] {
                               false,rsobat.getDouble("jml"),rsobat.getString("kode_brng"),rsobat.getString("nama_brng"),
                               rsobat.getString("kode_sat"),rsobat.getString("letak_barang"),Valid.roundUp(rsobat.getDouble("kelas1"),100),
                               rsobat.getString("nama"),rsobat.getString("aturan_pakai"),rsobat.getString("nama_industri"),
                               rsobat.getDouble("h_beli"),rsobat.getDouble("stok")
                            });
                        }else if(Jeniskelas.getSelectedItem().equals("Kelas 2")){
                            tabModeResep.addRow(new Object[] {
                               false,rsobat.getDouble("jml"),rsobat.getString("kode_brng"),rsobat.getString("nama_brng"),
                               rsobat.getString("kode_sat"),rsobat.getString("letak_barang"),Valid.roundUp(rsobat.getDouble("kelas2"),100),
                               rsobat.getString("nama"),rsobat.getString("aturan_pakai"),rsobat.getString("nama_industri"),
                               rsobat.getDouble("h_beli"),rsobat.getDouble("stok")
                            });
                        }else if(Jeniskelas.getSelectedItem().equals("Kelas 3")){
                            tabModeResep.addRow(new Object[] {
                               false,rsobat.getDouble("jml"),rsobat.getString("kode_brng"),rsobat.getString("nama_brng"),
                               rsobat.getString("kode_sat"),rsobat.getString("letak_barang"),Valid.roundUp(rsobat.getDouble("kelas3"),100),
                               rsobat.getString("nama"),rsobat.getString("aturan_pakai"),rsobat.getString("nama_industri"),
                               rsobat.getDouble("h_beli"),rsobat.getDouble("stok")
                            });
                        }else if(Jeniskelas.getSelectedItem().equals("VIP")){
                            tabModeResep.addRow(new Object[] {
                               false,rsobat.getDouble("jml"),rsobat.getString("kode_brng"),rsobat.getString("nama_brng"),
                               rsobat.getString("kode_sat"),rsobat.getString("letak_barang"),Valid.roundUp(rsobat.getDouble("vip"),100),
                               rsobat.getString("nama"),rsobat.getString("aturan_pakai"),rsobat.getString("nama_industri"),
                               rsobat.getDouble("h_beli"),rsobat.getDouble("stok")
                            });
                        }else if(Jeniskelas.getSelectedItem().equals("VVIP")){
                            tabModeResep.addRow(new Object[] {
                               false,rsobat.getDouble("jml"),rsobat.getString("kode_brng"),rsobat.getString("nama_brng"),
                               rsobat.getString("kode_sat"),rsobat.getString("letak_barang"),Valid.roundUp(rsobat.getDouble("vvip"),100),
                               rsobat.getString("nama"),rsobat.getString("aturan_pakai"),rsobat.getString("nama_industri"),
                               rsobat.getDouble("h_beli"),rsobat.getDouble("stok")
                            });
                        }                   
                    }
                }catch(Exception e){
                    System.out.println("Notifikasi : "+e);
                }finally{
                    if(rsobat != null){
                        rsobat.close();
                    }

                    if(psresep != null){
                        psresep.close();
                    }
                }
            } 
            psresep=koneksi.prepareStatement(
                    "select resep_dokter_racikan.no_racik,resep_dokter_racikan.nama_racik,"+
                    "resep_dokter_racikan.kd_racik,metode_racik.nm_racik as metode,"+
                    "resep_dokter_racikan.jml_dr,resep_dokter_racikan.aturan_pakai,"+
                    "resep_dokter_racikan.keterangan from resep_dokter_racikan inner join metode_racik "+
                    "on resep_dokter_racikan.kd_racik=metode_racik.kd_racik where "+
                    "resep_dokter_racikan.no_resep=? ");
            try {
                psresep.setString(1,no_resep);
                rsobat=psresep.executeQuery();
                while(rsobat.next()){
                    tabModeResepRacikan.addRow(new String[]{
                        rsobat.getString("no_racik"),rsobat.getString("nama_racik"),rsobat.getString("kd_racik"),
                        rsobat.getString("metode"),rsobat.getString("jml_dr"),rsobat.getString("aturan_pakai"),
                        rsobat.getString("keterangan")
                    });   
                    if(kenaikan>0){
                        ps2=koneksi.prepareStatement("select databarang.kode_brng, databarang.nama_brng,jenis.nama, databarang.kode_sat,(databarang.h_beli+(databarang.h_beli*?)) as harga,"+
                            " databarang.letak_barang,industrifarmasi.nama_industri,databarang.h_beli,gudangbarang.stok,databarang.kapasitas,resep_dokter_racikan_detail.p1,"+
                            " resep_dokter_racikan_detail.p2,resep_dokter_racikan_detail.kandungan,resep_dokter_racikan_detail.jml "+
                            " from databarang inner join jenis inner join industrifarmasi inner join gudangbarang inner join resep_dokter_racikan_detail "+
                            " on databarang.kdjns=jenis.kdjns and databarang.kode_brng=gudangbarang.kode_brng "+
                            " and industrifarmasi.kode_industri=databarang.kode_industri and resep_dokter_racikan_detail.kode_brng=databarang.kode_brng "+
                            " where  databarang.status='1' and gudangbarang.kd_bangsal=? and resep_dokter_racikan_detail.no_resep=? and resep_dokter_racikan_detail.no_racik=? order by databarang.nama_brng");
                        try{ 
                            ps2.setDouble(1,kenaikan);
                            ps2.setString(2,bangsal);
                            ps2.setString(3,no_resep);
                            ps2.setString(4,rsobat.getString("no_racik"));
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                tabModeDetailResepRacikan.addRow(new Object[] {
                                    rsobat.getString("no_racik"),rs2.getString("kode_brng"),rs2.getString("nama_brng"),
                                    rs2.getString("kode_sat"),Valid.roundUp(rs2.getDouble("harga"),100),
                                    rs2.getDouble("h_beli"),rs2.getString("nama"),rs2.getDouble("stok"),
                                    rs2.getDouble("kapasitas"),rs2.getDouble("p1"),"/",rs2.getDouble("p2"),
                                    rs2.getString("kandungan"),rs2.getDouble("jml"),rs2.getString("nama_industri")
                                });          
                            }  
                        }catch(Exception e){
                            System.out.println("Notifikasi : "+e);
                        }finally{
                            if(rs2 != null){
                                rs2.close();
                            }
                            if(ps2 != null){
                                ps2.close();
                            }
                        }               
                    }else{
                        ps2=koneksi.prepareStatement(
                            "select databarang.kode_brng, databarang.nama_brng,jenis.nama, databarang.kode_sat,"+
                            " databarang.karyawan,databarang.ralan,databarang.beliluar,databarang.kelas1," +
                            " databarang.kelas2,databarang.kelas3,databarang.vip,databarang.vvip,"+
                            " databarang.letak_barang,databarang.utama,industrifarmasi.nama_industri,databarang.h_beli,gudangbarang.stok,databarang.kapasitas,resep_dokter_racikan_detail.p1,"+
                            " resep_dokter_racikan_detail.p2,resep_dokter_racikan_detail.kandungan,resep_dokter_racikan_detail.jml "+
                            " from databarang inner join jenis inner join industrifarmasi inner join gudangbarang inner join resep_dokter_racikan_detail "+
                            " on databarang.kdjns=jenis.kdjns and databarang.kode_brng=gudangbarang.kode_brng "+
                            " and industrifarmasi.kode_industri=databarang.kode_industri and resep_dokter_racikan_detail.kode_brng=databarang.kode_brng "+
                            " where  databarang.status='1' and gudangbarang.kd_bangsal=? and resep_dokter_racikan_detail.no_resep=? and resep_dokter_racikan_detail.no_racik=? order by databarang.nama_brng");
                        try{ 
                            ps2.setString(1,bangsal);
                            ps2.setString(2,no_resep);
                            ps2.setString(3,rsobat.getString("no_racik"));
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                if(Jeniskelas.getSelectedItem().equals("Karyawan")){
                                    tabModeDetailResepRacikan.addRow(new Object[] {
                                        rsobat.getString("no_racik"),rs2.getString("kode_brng"),rs2.getString("nama_brng"),
                                        rs2.getString("kode_sat"),Valid.roundUp(rs2.getDouble("karyawan"),100),
                                        rs2.getDouble("h_beli"),rs2.getString("nama"),rs2.getDouble("stok"),
                                        rs2.getDouble("kapasitas"),rs2.getDouble("p1"),"/",rs2.getDouble("p2"),
                                        rs2.getString("kandungan"),rs2.getDouble("jml"),rs2.getString("nama_industri")
                                    }); 
                                }else if(Jeniskelas.getSelectedItem().equals("Rawat Jalan")){
                                    tabModeDetailResepRacikan.addRow(new Object[] {
                                        rsobat.getString("no_racik"),rs2.getString("kode_brng"),rs2.getString("nama_brng"),
                                        rs2.getString("kode_sat"),Valid.roundUp(rs2.getDouble("ralan"),100),
                                        rs2.getDouble("h_beli"),rs2.getString("nama"),rs2.getDouble("stok"),
                                        rs2.getDouble("kapasitas"),rs2.getDouble("p1"),"/",rs2.getDouble("p2"),
                                        rs2.getString("kandungan"),rs2.getDouble("jml"),rs2.getString("nama_industri")
                                    }); 
                                }else if(Jeniskelas.getSelectedItem().equals("Beli Luar")){
                                    tabModeDetailResepRacikan.addRow(new Object[] {
                                        rsobat.getString("no_racik"),rs2.getString("kode_brng"),rs2.getString("nama_brng"),
                                        rs2.getString("kode_sat"),Valid.roundUp(rs2.getDouble("beliluar"),100),
                                        rs2.getDouble("h_beli"),rs2.getString("nama"),rs2.getDouble("stok"),
                                        rs2.getDouble("kapasitas"),rs2.getDouble("p1"),"/",rs2.getDouble("p2"),
                                        rs2.getString("kandungan"),rs2.getDouble("jml"),rs2.getString("nama_industri")
                                    });  
                                }else if(Jeniskelas.getSelectedItem().equals("Utama/BPJS")){
                                    tabModeDetailResepRacikan.addRow(new Object[] {
                                        rsobat.getString("no_racik"),rs2.getString("kode_brng"),rs2.getString("nama_brng"),
                                        rs2.getString("kode_sat"),Valid.roundUp(rs2.getDouble("utama"),100),
                                        rs2.getDouble("h_beli"),rs2.getString("nama"),rs2.getDouble("stok"),
                                        rs2.getDouble("kapasitas"),rs2.getDouble("p1"),"/",rs2.getDouble("p2"),
                                        rs2.getString("kandungan"),rs2.getDouble("jml"),rs2.getString("nama_industri")
                                    }); 
                                }else if(Jeniskelas.getSelectedItem().equals("Kelas 1")){
                                    tabModeDetailResepRacikan.addRow(new Object[] {
                                        rsobat.getString("no_racik"),rs2.getString("kode_brng"),rs2.getString("nama_brng"),
                                        rs2.getString("kode_sat"),Valid.roundUp(rs2.getDouble("kelas1"),100),
                                        rs2.getDouble("h_beli"),rs2.getString("nama"),rs2.getDouble("stok"),
                                        rs2.getDouble("kapasitas"),rs2.getDouble("p1"),"/",rs2.getDouble("p2"),
                                        rs2.getString("kandungan"),rs2.getDouble("jml"),rs2.getString("nama_industri")
                                    }); 
                                }else if(Jeniskelas.getSelectedItem().equals("Kelas 2")){
                                    tabModeDetailResepRacikan.addRow(new Object[] {
                                        rsobat.getString("no_racik"),rs2.getString("kode_brng"),rs2.getString("nama_brng"),
                                        rs2.getString("kode_sat"),Valid.roundUp(rs2.getDouble("kelas2"),100),
                                        rs2.getDouble("h_beli"),rs2.getString("nama"),rs2.getDouble("stok"),
                                        rs2.getDouble("kapasitas"),rs2.getDouble("p1"),"/",rs2.getDouble("p2"),
                                        rs2.getString("kandungan"),rs2.getDouble("jml"),rs2.getString("nama_industri")
                                    }); 
                                }else if(Jeniskelas.getSelectedItem().equals("Kelas 3")){
                                    tabModeDetailResepRacikan.addRow(new Object[] {
                                        rsobat.getString("no_racik"),rs2.getString("kode_brng"),rs2.getString("nama_brng"),
                                        rs2.getString("kode_sat"),Valid.roundUp(rs2.getDouble("kelas3"),100),
                                        rs2.getDouble("h_beli"),rs2.getString("nama"),rs2.getDouble("stok"),
                                        rs2.getDouble("kapasitas"),rs2.getDouble("p1"),"/",rs2.getDouble("p2"),
                                        rs2.getString("kandungan"),rs2.getDouble("jml"),rs2.getString("nama_industri")
                                    }); 
                                }else if(Jeniskelas.getSelectedItem().equals("VIP")){
                                    tabModeDetailResepRacikan.addRow(new Object[] {
                                        rsobat.getString("no_racik"),rs2.getString("kode_brng"),rs2.getString("nama_brng"),
                                        rs2.getString("kode_sat"),Valid.roundUp(rs2.getDouble("vip"),100),
                                        rs2.getDouble("h_beli"),rs2.getString("nama"),rs2.getDouble("stok"),
                                        rs2.getDouble("kapasitas"),rs2.getDouble("p1"),"/",rs2.getDouble("p2"),
                                        rs2.getString("kandungan"),rs2.getDouble("jml"),rs2.getString("nama_industri")
                                    }); 
                                }else if(Jeniskelas.getSelectedItem().equals("VVIP")){
                                    tabModeDetailResepRacikan.addRow(new Object[] {
                                        rsobat.getString("no_racik"),rs2.getString("kode_brng"),rs2.getString("nama_brng"),
                                        rs2.getString("kode_sat"),Valid.roundUp(rs2.getDouble("vvip"),100),
                                        rs2.getDouble("h_beli"),rs2.getString("nama"),rs2.getDouble("stok"),
                                        rs2.getDouble("kapasitas"),rs2.getDouble("p1"),"/",rs2.getDouble("p2"),
                                        rs2.getString("kandungan"),rs2.getDouble("jml"),rs2.getString("nama_industri")
                                    }); 
                                }                   
                            }
                        }catch(Exception e){
                            System.out.println("Notifikasi : "+e);
                        }finally{
                            if(rs2 != null){
                                rs2.close();
                            }
                            if(ps2 != null){
                                ps2.close();
                            }
                        }
                    }  
                }
            } catch (Exception e) {
                System.out.println("Notifikasi 2 : "+e);
            } finally{
                if(rsobat!=null){
                    rsobat.close();
                }
                if(psresep!=null){
                    psresep.close();
                }
            }
            hitungResep();
        } catch (Exception e) {
            System.out.println("Notifikasi : "+e);
        } 
    }
    
    public void tampilobat2(String no_resep) {
        try {
            Valid.tabelKosong(tabModeResep);
            Valid.tabelKosong(tabModeResepRacikan);
            Valid.tabelKosong(tabModeDetailResepRacikan);
            if(kenaikan>0){
                psresepasuransi=koneksi.prepareStatement("select databarang.kode_brng, databarang.nama_brng,jenis.nama, databarang.kode_sat,(databarang.h_beli+(databarang.h_beli*?)) as harga,"+
                    " databarang.letak_barang,industrifarmasi.nama_industri,databarang.h_beli,gudangbarang.stok,resep_dokter.jml, resep_dokter.aturan_pakai "+
                    " from databarang inner join jenis inner join industrifarmasi inner join gudangbarang inner join resep_dokter"+
                    " on databarang.kdjns=jenis.kdjns and databarang.kode_brng=gudangbarang.kode_brng "+
                    " and industrifarmasi.kode_industri=databarang.kode_industri and resep_dokter.kode_brng=databarang.kode_brng "+
                    " where databarang.status='1' and gudangbarang.kd_bangsal=? and resep_dokter.no_resep=? order by databarang.nama_brng");
                try{
                    psresepasuransi.setDouble(1,kenaikan);
                    psresepasuransi.setString(2,bangsal);
                    psresepasuransi.setString(3,no_resep);
                    rsobat=psresepasuransi.executeQuery();
                    while(rsobat.next()){
                        tabModeResep.addRow(new Object[] {
                           false,rsobat.getDouble("jml"),rsobat.getString("kode_brng"),rsobat.getString("nama_brng"),
                           rsobat.getString("kode_sat"),rsobat.getString("letak_barang"),Valid.roundUp(rsobat.getDouble("harga"),100),
                           rsobat.getString("nama"),rsobat.getString("aturan_pakai"),rsobat.getString("nama_industri"),
                           rsobat.getDouble("h_beli"),rsobat.getDouble("stok")
                        });          
                    }  
                }catch(Exception e){
                    System.out.println("Notifikasi : "+e);
                }finally{
                    if(rsobat != null){
                        rsobat.close();
                    }

                    if(psresepasuransi != null){
                        psresepasuransi.close();
                    }
                }                                   
            }else{    
                psresep=koneksi.prepareStatement(
                    "select databarang.kode_brng, databarang.nama_brng,jenis.nama, databarang.kode_sat,"+
                    " databarang.karyawan,databarang.ralan,databarang.beliluar,databarang.kelas1," +
                    " databarang.kelas2,databarang.kelas3,databarang.vip,databarang.vvip,"+
                    " databarang.letak_barang,databarang.utama,industrifarmasi.nama_industri,databarang.h_beli,gudangbarang.stok,resep_dokter.jml, resep_dokter.aturan_pakai "+
                    " from databarang inner join jenis inner join industrifarmasi inner join gudangbarang inner join resep_dokter "+
                    " on databarang.kdjns=jenis.kdjns and databarang.kode_brng=gudangbarang.kode_brng "+
                    " and industrifarmasi.kode_industri=databarang.kode_industri and resep_dokter.kode_brng=databarang.kode_brng "+
                    " where  databarang.status='1' and gudangbarang.kd_bangsal=? and resep_dokter.no_resep=? order by databarang.nama_brng");
                try{
                    psresep.setString(1,bangsal);
                    psresep.setString(2,no_resep);
                    rsobat=psresep.executeQuery();
                    while(rsobat.next()){
                        if(Jeniskelas.getSelectedItem().equals("Karyawan")){
                            tabModeResep.addRow(new Object[] {
                               false,rsobat.getDouble("jml"),rsobat.getString("kode_brng"),rsobat.getString("nama_brng"),
                               rsobat.getString("kode_sat"),rsobat.getString("letak_barang"),Valid.roundUp(rsobat.getDouble("karyawan"),100),
                               rsobat.getString("nama"),rsobat.getString("aturan_pakai"),rsobat.getString("nama_industri"),
                               rsobat.getDouble("h_beli"),rsobat.getDouble("stok")
                            });
                        }else if(Jeniskelas.getSelectedItem().equals("Rawat Jalan")){
                            tabModeResep.addRow(new Object[] {
                               false,rsobat.getDouble("jml"),rsobat.getString("kode_brng"),rsobat.getString("nama_brng"),
                               rsobat.getString("kode_sat"),rsobat.getString("letak_barang"),Valid.roundUp(rsobat.getDouble("ralan"),100),
                               rsobat.getString("nama"),rsobat.getString("aturan_pakai"),rsobat.getString("nama_industri"),
                               rsobat.getDouble("h_beli"),rsobat.getDouble("stok")
                            });
                        }else if(Jeniskelas.getSelectedItem().equals("Beli Luar")){
                            tabModeResep.addRow(new Object[] {
                               false,rsobat.getDouble("jml"),rsobat.getString("kode_brng"),rsobat.getString("nama_brng"),
                               rsobat.getString("kode_sat"),rsobat.getString("letak_barang"),Valid.roundUp(rsobat.getDouble("beliluar"),100),
                               rsobat.getString("nama"),rsobat.getString("aturan_pakai"),rsobat.getString("nama_industri"),
                               rsobat.getDouble("h_beli"),rsobat.getDouble("stok")
                            });
                        }else if(Jeniskelas.getSelectedItem().equals("Utama/BPJS")){
                            tabModeResep.addRow(new Object[] {
                               false,rsobat.getDouble("jml"),rsobat.getString("kode_brng"),rsobat.getString("nama_brng"),
                               rsobat.getString("kode_sat"),rsobat.getString("letak_barang"),Valid.roundUp(rsobat.getDouble("utama"),100),
                               rsobat.getString("nama"),rsobat.getString("aturan_pakai"),rsobat.getString("nama_industri"),
                               rsobat.getDouble("h_beli"),rsobat.getDouble("stok")
                            });
                        }else if(Jeniskelas.getSelectedItem().equals("Kelas 1")){
                            tabModeResep.addRow(new Object[] {
                               false,rsobat.getDouble("jml"),rsobat.getString("kode_brng"),rsobat.getString("nama_brng"),
                               rsobat.getString("kode_sat"),rsobat.getString("letak_barang"),Valid.roundUp(rsobat.getDouble("kelas1"),100),
                               rsobat.getString("nama"),rsobat.getString("aturan_pakai"),rsobat.getString("nama_industri"),
                               rsobat.getDouble("h_beli"),rsobat.getDouble("stok")
                            });
                        }else if(Jeniskelas.getSelectedItem().equals("Kelas 2")){
                            tabModeResep.addRow(new Object[] {
                               false,rsobat.getDouble("jml"),rsobat.getString("kode_brng"),rsobat.getString("nama_brng"),
                               rsobat.getString("kode_sat"),rsobat.getString("letak_barang"),Valid.roundUp(rsobat.getDouble("kelas2"),100),
                               rsobat.getString("nama"),rsobat.getString("aturan_pakai"),rsobat.getString("nama_industri"),
                               rsobat.getDouble("h_beli"),rsobat.getDouble("stok")
                            });
                        }else if(Jeniskelas.getSelectedItem().equals("Kelas 3")){
                            tabModeResep.addRow(new Object[] {
                               false,rsobat.getDouble("jml"),rsobat.getString("kode_brng"),rsobat.getString("nama_brng"),
                               rsobat.getString("kode_sat"),rsobat.getString("letak_barang"),Valid.roundUp(rsobat.getDouble("kelas3"),100),
                               rsobat.getString("nama"),rsobat.getString("aturan_pakai"),rsobat.getString("nama_industri"),
                               rsobat.getDouble("h_beli"),rsobat.getDouble("stok")
                            });
                        }else if(Jeniskelas.getSelectedItem().equals("VIP")){
                            tabModeResep.addRow(new Object[] {
                               false,rsobat.getDouble("jml"),rsobat.getString("kode_brng"),rsobat.getString("nama_brng"),
                               rsobat.getString("kode_sat"),rsobat.getString("letak_barang"),Valid.roundUp(rsobat.getDouble("vip"),100),
                               rsobat.getString("nama"),rsobat.getString("aturan_pakai"),rsobat.getString("nama_industri"),
                               rsobat.getDouble("h_beli"),rsobat.getDouble("stok")
                            });
                        }else if(Jeniskelas.getSelectedItem().equals("VVIP")){
                            tabModeResep.addRow(new Object[] {
                               false,rsobat.getDouble("jml"),rsobat.getString("kode_brng"),rsobat.getString("nama_brng"),
                               rsobat.getString("kode_sat"),rsobat.getString("letak_barang"),Valid.roundUp(rsobat.getDouble("vvip"),100),
                               rsobat.getString("nama"),rsobat.getString("aturan_pakai"),rsobat.getString("nama_industri"),
                               rsobat.getDouble("h_beli"),rsobat.getDouble("stok")
                            });
                        }                   
                    }
                }catch(Exception e){
                    System.out.println("Notifikasi : "+e);
                }finally{
                    if(rsobat != null){
                        rsobat.close();
                    }

                    if(psresep != null){
                        psresep.close();
                    }
                }
            } 
            psresep=koneksi.prepareStatement(
                    "select resep_dokter_racikan.no_racik,resep_dokter_racikan.nama_racik,"+
                    "resep_dokter_racikan.kd_racik,metode_racik.nm_racik as metode,"+
                    "resep_dokter_racikan.jml_dr,resep_dokter_racikan.aturan_pakai,"+
                    "resep_dokter_racikan.keterangan from resep_dokter_racikan inner join metode_racik "+
                    "on resep_dokter_racikan.kd_racik=metode_racik.kd_racik where "+
                    "resep_dokter_racikan.no_resep=? ");
            try {
                psresep.setString(1,no_resep);
                rsobat=psresep.executeQuery();
                while(rsobat.next()){
                    tabModeResepRacikan.addRow(new String[]{
                        rsobat.getString("no_racik"),rsobat.getString("nama_racik"),rsobat.getString("kd_racik"),
                        rsobat.getString("metode"),rsobat.getString("jml_dr"),rsobat.getString("aturan_pakai"),
                        rsobat.getString("keterangan")
                    });   
                    if(kenaikan>0){
                        ps2=koneksi.prepareStatement("select databarang.kode_brng, databarang.nama_brng,jenis.nama, databarang.kode_sat,(databarang.h_beli+(databarang.h_beli*?)) as harga,"+
                            " databarang.letak_barang,industrifarmasi.nama_industri,databarang.h_beli,gudangbarang.stok,databarang.kapasitas,resep_dokter_racikan_detail.p1,"+
                            " resep_dokter_racikan_detail.p2,resep_dokter_racikan_detail.kandungan,resep_dokter_racikan_detail.jml "+
                            " from databarang inner join jenis inner join industrifarmasi inner join gudangbarang inner join resep_dokter_racikan_detail "+
                            " on databarang.kdjns=jenis.kdjns and databarang.kode_brng=gudangbarang.kode_brng "+
                            " and industrifarmasi.kode_industri=databarang.kode_industri and resep_dokter_racikan_detail.kode_brng=databarang.kode_brng "+
                            " where  databarang.status='1' and gudangbarang.kd_bangsal=? and resep_dokter_racikan_detail.no_resep=? and resep_dokter_racikan_detail.no_racik=? order by databarang.nama_brng");
                        try{ 
                            ps2.setDouble(1,kenaikan);
                            ps2.setString(2,bangsal);
                            ps2.setString(3,no_resep);
                            ps2.setString(4,rsobat.getString("no_racik"));
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                tabModeDetailResepRacikan.addRow(new Object[] {
                                    rsobat.getString("no_racik"),rs2.getString("kode_brng"),rs2.getString("nama_brng"),
                                    rs2.getString("kode_sat"),Valid.roundUp(rs2.getDouble("harga"),100),
                                    rs2.getDouble("h_beli"),rs2.getString("nama"),rs2.getDouble("stok"),
                                    rs2.getDouble("kapasitas"),rs2.getDouble("p1"),"/",rs2.getDouble("p2"),
                                    rs2.getString("kandungan"),rs2.getDouble("jml"),rs2.getString("nama_industri")
                                });          
                            }  
                        }catch(Exception e){
                            System.out.println("Notifikasi : "+e);
                        }finally{
                            if(rs2 != null){
                                rs2.close();
                            }
                            if(ps2 != null){
                                ps2.close();
                            }
                        }               
                    }else{
                        ps2=koneksi.prepareStatement(
                            "select databarang.kode_brng, databarang.nama_brng,jenis.nama, databarang.kode_sat,"+
                            " databarang.karyawan,databarang.ralan,databarang.beliluar,databarang.kelas1," +
                            " databarang.kelas2,databarang.kelas3,databarang.vip,databarang.vvip,"+
                            " databarang.letak_barang,databarang.utama,industrifarmasi.nama_industri,databarang.h_beli,gudangbarang.stok,databarang.kapasitas,resep_dokter_racikan_detail.p1,"+
                            " resep_dokter_racikan_detail.p2,resep_dokter_racikan_detail.kandungan,resep_dokter_racikan_detail.jml "+
                            " from databarang inner join jenis inner join industrifarmasi inner join gudangbarang inner join resep_dokter_racikan_detail "+
                            " on databarang.kdjns=jenis.kdjns and databarang.kode_brng=gudangbarang.kode_brng "+
                            " and industrifarmasi.kode_industri=databarang.kode_industri and resep_dokter_racikan_detail.kode_brng=databarang.kode_brng "+
                            " where  databarang.status='1' and gudangbarang.kd_bangsal=? and resep_dokter_racikan_detail.no_resep=? and resep_dokter_racikan_detail.no_racik=? order by databarang.nama_brng");
                        try{ 
                            ps2.setString(1,bangsal);
                            ps2.setString(2,no_resep);
                            ps2.setString(3,rsobat.getString("no_racik"));
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                if(Jeniskelas.getSelectedItem().equals("Karyawan")){
                                    tabModeDetailResepRacikan.addRow(new Object[] {
                                        rsobat.getString("no_racik"),rs2.getString("kode_brng"),rs2.getString("nama_brng"),
                                        rs2.getString("kode_sat"),Valid.roundUp(rs2.getDouble("karyawan"),100),
                                        rs2.getDouble("h_beli"),rs2.getString("nama"),rs2.getDouble("stok"),
                                        rs2.getDouble("kapasitas"),rs2.getDouble("p1"),"/",rs2.getDouble("p2"),
                                        rs2.getString("kandungan"),rs2.getDouble("jml"),rs2.getString("nama_industri")
                                    }); 
                                }else if(Jeniskelas.getSelectedItem().equals("Rawat Jalan")){
                                    tabModeDetailResepRacikan.addRow(new Object[] {
                                        rsobat.getString("no_racik"),rs2.getString("kode_brng"),rs2.getString("nama_brng"),
                                        rs2.getString("kode_sat"),Valid.roundUp(rs2.getDouble("ralan"),100),
                                        rs2.getDouble("h_beli"),rs2.getString("nama"),rs2.getDouble("stok"),
                                        rs2.getDouble("kapasitas"),rs2.getDouble("p1"),"/",rs2.getDouble("p2"),
                                        rs2.getString("kandungan"),rs2.getDouble("jml"),rs2.getString("nama_industri")
                                    }); 
                                }else if(Jeniskelas.getSelectedItem().equals("Beli Luar")){
                                    tabModeDetailResepRacikan.addRow(new Object[] {
                                        rsobat.getString("no_racik"),rs2.getString("kode_brng"),rs2.getString("nama_brng"),
                                        rs2.getString("kode_sat"),Valid.roundUp(rs2.getDouble("beliluar"),100),
                                        rs2.getDouble("h_beli"),rs2.getString("nama"),rs2.getDouble("stok"),
                                        rs2.getDouble("kapasitas"),rs2.getDouble("p1"),"/",rs2.getDouble("p2"),
                                        rs2.getString("kandungan"),rs2.getDouble("jml"),rs2.getString("nama_industri")
                                    });  
                                }else if(Jeniskelas.getSelectedItem().equals("Utama/BPJS")){
                                    tabModeDetailResepRacikan.addRow(new Object[] {
                                        rsobat.getString("no_racik"),rs2.getString("kode_brng"),rs2.getString("nama_brng"),
                                        rs2.getString("kode_sat"),Valid.roundUp(rs2.getDouble("utama"),100),
                                        rs2.getDouble("h_beli"),rs2.getString("nama"),rs2.getDouble("stok"),
                                        rs2.getDouble("kapasitas"),rs2.getDouble("p1"),"/",rs2.getDouble("p2"),
                                        rs2.getString("kandungan"),rs2.getDouble("jml"),rs2.getString("nama_industri")
                                    }); 
                                }else if(Jeniskelas.getSelectedItem().equals("Kelas 1")){
                                    tabModeDetailResepRacikan.addRow(new Object[] {
                                        rsobat.getString("no_racik"),rs2.getString("kode_brng"),rs2.getString("nama_brng"),
                                        rs2.getString("kode_sat"),Valid.roundUp(rs2.getDouble("kelas1"),100),
                                        rs2.getDouble("h_beli"),rs2.getString("nama"),rs2.getDouble("stok"),
                                        rs2.getDouble("kapasitas"),rs2.getDouble("p1"),"/",rs2.getDouble("p2"),
                                        rs2.getString("kandungan"),rs2.getDouble("jml"),rs2.getString("nama_industri")
                                    });
                                }else if(Jeniskelas.getSelectedItem().equals("Kelas 2")){
                                    tabModeDetailResepRacikan.addRow(new Object[] {
                                        rsobat.getString("no_racik"),rs2.getString("kode_brng"),rs2.getString("nama_brng"),
                                        rs2.getString("kode_sat"),Valid.roundUp(rs2.getDouble("kelas2"),100),
                                        rs2.getDouble("h_beli"),rs2.getString("nama"),rs2.getDouble("stok"),
                                        rs2.getDouble("kapasitas"),rs2.getDouble("p1"),"/",rs2.getDouble("p2"),
                                        rs2.getString("kandungan"),rs2.getDouble("jml"),rs2.getString("nama_industri")
                                    });
                                }else if(Jeniskelas.getSelectedItem().equals("Kelas 3")){
                                    tabModeDetailResepRacikan.addRow(new Object[] {
                                        rsobat.getString("no_racik"),rs2.getString("kode_brng"),rs2.getString("nama_brng"),
                                        rs2.getString("kode_sat"),Valid.roundUp(rs2.getDouble("kelas3"),100),
                                        rs2.getDouble("h_beli"),rs2.getString("nama"),rs2.getDouble("stok"),
                                        rs2.getDouble("kapasitas"),rs2.getDouble("p1"),"/",rs2.getDouble("p2"),
                                        rs2.getString("kandungan"),rs2.getDouble("jml"),rs2.getString("nama_industri")
                                    });
                                }else if(Jeniskelas.getSelectedItem().equals("VIP")){
                                    tabModeDetailResepRacikan.addRow(new Object[] {
                                        rsobat.getString("no_racik"),rs2.getString("kode_brng"),rs2.getString("nama_brng"),
                                        rs2.getString("kode_sat"),Valid.roundUp(rs2.getDouble("vip"),100),
                                        rs2.getDouble("h_beli"),rs2.getString("nama"),rs2.getDouble("stok"),
                                        rs2.getDouble("kapasitas"),rs2.getDouble("p1"),"/",rs2.getDouble("p2"),
                                        rs2.getString("kandungan"),rs2.getDouble("jml"),rs2.getString("nama_industri")
                                    });
                                }else if(Jeniskelas.getSelectedItem().equals("VVIP")){
                                    tabModeDetailResepRacikan.addRow(new Object[] {
                                        rsobat.getString("no_racik"),rs2.getString("kode_brng"),rs2.getString("nama_brng"),
                                        rs2.getString("kode_sat"),Valid.roundUp(rs2.getDouble("vvip"),100),
                                        rs2.getDouble("h_beli"),rs2.getString("nama"),rs2.getDouble("stok"),
                                        rs2.getDouble("kapasitas"),rs2.getDouble("p1"),"/",rs2.getDouble("p2"),
                                        rs2.getString("kandungan"),rs2.getDouble("jml"),rs2.getString("nama_industri")
                                    });
                                }                   
                            }
                        }catch(Exception e){
                            System.out.println("Notifikasi : "+e);
                        }finally{
                            if(rs2 != null){
                                rs2.close();
                            }
                            if(ps2 != null){
                                ps2.close();
                            }
                        }
                    }  
                }
            } catch (Exception e) {
                System.out.println("Notifikasi 2 : "+e);
            } finally{
                if(rsobat!=null){
                    rsobat.close();
                }
                if(psresep!=null){
                    psresep.close();
                }
            }
            hitungResep();
        } catch (Exception e) {
            System.out.println("Notifikasi : "+e);
        } 
    }

    private void simpandata() {
        try {
            koneksi.setAutoCommit(false);
            for(i=0;i<tbResep.getRowCount();i++){ 
                if(Valid.SetAngka(tbResep.getValueAt(i,1).toString())>0){                        
                    if(tbResep.getValueAt(i,0).toString().equals("true")){
                        pscarikapasitas= koneksi.prepareStatement("select IFNULL(kapasitas,1) from databarang where kode_brng=?");                                      
                        try {
                            pscarikapasitas.setString(1,tbResep.getValueAt(i,2).toString());
                            carikapasitas=pscarikapasitas.executeQuery();
                            if(carikapasitas.next()){ 
                                Sequel.menyimpan("resep_dokter","?,?,?,?","data",4,new String[]{
                                    NoResep.getText(),tbResep.getValueAt(i,2).toString(),
                                    ""+(Double.parseDouble(tbResep.getValueAt(i,1).toString())/carikapasitas.getDouble(1)),
                                    tbResep.getValueAt(i,8).toString()
                                });
                            }else{
                                Sequel.menyimpan("resep_dokter","?,?,?,?","data",4,new String[]{
                                    NoResep.getText(),tbResep.getValueAt(i,2).toString(),
                                    ""+(Double.parseDouble(tbResep.getValueAt(i,1).toString())),
                                    tbResep.getValueAt(i,8).toString()
                                });                                
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
                        Sequel.menyimpan("resep_dokter","?,?,?,?","data",4,new String[]{
                            NoResep.getText(),tbResep.getValueAt(i,2).toString(),
                            ""+(Double.parseDouble(tbResep.getValueAt(i,1).toString())),
                            tbResep.getValueAt(i,8).toString()
                        });                                  
                    }                      
                }
                tbResep.setValueAt("",i,1);
            } 

            for(i=0;i<tbObatResepRacikan.getRowCount();i++){ 
                if(Valid.SetAngka(tbObatResepRacikan.getValueAt(i,4).toString())>0){ 
                    Sequel.menyimpan("resep_dokter_racikan","?,?,?,?,?,?,?",7,new String[]{
                       NoResep.getText(),tbObatResepRacikan.getValueAt(i,0).toString(),tbObatResepRacikan.getValueAt(i,1).toString(),
                       tbObatResepRacikan.getValueAt(i,2).toString(),tbObatResepRacikan.getValueAt(i,4).toString(),
                       tbObatResepRacikan.getValueAt(i,5).toString(),tbObatResepRacikan.getValueAt(i,6).toString()
                    });
                }
            }

            Valid.tabelKosong(tabModeResepRacikan);

            for(i=0;i<tbDetailResepObatRacikan.getRowCount();i++){ 
                if(Valid.SetAngka(tbDetailResepObatRacikan.getValueAt(i,13).toString())>0){
                    Sequel.menyimpan("resep_dokter_racikan_detail","?,?,?,?,?,?,?",7,new String[]{
                        NoResep.getText(),tbDetailResepObatRacikan.getValueAt(i,0).toString(),tbDetailResepObatRacikan.getValueAt(i,1).toString(),
                        tbDetailResepObatRacikan.getValueAt(i,9).toString(),tbDetailResepObatRacikan.getValueAt(i,11).toString(),
                        tbDetailResepObatRacikan.getValueAt(i,12).toString(),tbDetailResepObatRacikan.getValueAt(i,13).toString()
                    });
                }
            }

            Valid.tabelKosong(tabModeDetailResepRacikan);
            koneksi.setAutoCommit(true);
            dispose();
        } catch (Exception e) {
            System.out.println("Notif : "+e);
        } 
    }
    
    public void MatikanJam(){
        ChkJln.setSelected(false);
    }

    private void SetHarga() {
        if(status.equals("ranap")){
            norawatibu=Sequel.cariIsi("select no_rawat from ranap_gabung where no_rawat2=?",TNoRw.getText());
            if(!norawatibu.equals("")){
                kamar=Sequel.cariIsi("select ifnull(kd_kamar,'') from kamar_inap where no_rawat=? order by tgl_masuk desc limit 1",norawatibu);
            }else{
                kamar=Sequel.cariIsi("select ifnull(kd_kamar,'') from kamar_inap where no_rawat=? order by tgl_masuk desc limit 1",TNoRw.getText());
            }
            if(!norawatibu.equals("")){
                kelas=Sequel.cariIsi(
                    "select kamar.kelas from kamar inner join kamar_inap "+
                    "on kamar.kd_kamar=kamar_inap.kd_kamar where no_rawat=? "+
                    "and stts_pulang='-' order by STR_TO_DATE(concat(kamar_inap.tgl_masuk,' ',jam_masuk),'%Y-%m-%d %H:%i:%s') desc limit 1",norawatibu);
            }else{
                kelas=Sequel.cariIsi(
                    "select kamar.kelas from kamar inner join kamar_inap "+
                    "on kamar.kd_kamar=kamar_inap.kd_kamar where no_rawat=? "+
                    "and stts_pulang='-' order by STR_TO_DATE(concat(kamar_inap.tgl_masuk,' ',jam_masuk),'%Y-%m-%d %H:%i:%s') desc limit 1",TNoRw.getText());
            }                
            if(kelas.equals("Kelas 1")){
                Jeniskelas.setSelectedItem("Kelas 1");
            }else if(kelas.equals("Kelas 2")){
                Jeniskelas.setSelectedItem("Kelas 2");
            }else if(kelas.equals("Kelas 3")){
                Jeniskelas.setSelectedItem("Kelas 3");
            }else if(kelas.equals("Kelas Utama")){
                Jeniskelas.setSelectedItem("Utama/BPJS");
            }else if(kelas.equals("Kelas VIP")){
                Jeniskelas.setSelectedItem("VIP");
            }else if(kelas.equals("Kelas VVIP")){
                Jeniskelas.setSelectedItem("VVIP");
            } 
        }else if(status.equals("ralan")){
            kelas="Rawat Jalan";
        }
    }
    
    private void hitungResep() {
        ttl=0;
        y=0;
        row2=tabModeResep.getRowCount();
        for(r=0;r<row2;r++){ 
            try {
                if(Double.parseDouble(tabModeResep.getValueAt(r,1).toString())>0){
                    try {                
                        y=Double.parseDouble(tabModeResep.getValueAt(r,1).toString())*
                          Double.parseDouble(tabModeResep.getValueAt(r,6).toString());                                                
                    } catch (Exception e) {
                        y=0;
                    }
                    ttl=ttl+y;
                }  
            } catch (Exception e) {
            }                           
        }
        row2=tabModeDetailResepRacikan.getRowCount();
        for(r=0;r<row2;r++){ 
            try {
                if(Valid.SetAngka(tbDetailResepObatRacikan.getValueAt(r,13).toString())>0){
                    try {
                        y=Double.parseDouble(tabModeDetailResepRacikan.getValueAt(r,13).toString())*
                          Double.parseDouble(tabModeDetailResepRacikan.getValueAt(r,4).toString());
                    } catch (Exception e) {
                        y=0;
                    }
                    ttl=ttl+y;
                }
            } catch (Exception e) {
            }    
        }
        LTotal.setText(Valid.SetAngka(ttl));
        ppnobat=0;
        if(tampilkan_ppnobat_ralan.equals("Yes")){
            ppnobat=ttl*0.1;
            ttl=ttl+ppnobat;
            LPpn.setText(Valid.SetAngka(ppnobat));
            LTotalTagihan.setText(Valid.SetAngka(ttl));
        }
    }
}

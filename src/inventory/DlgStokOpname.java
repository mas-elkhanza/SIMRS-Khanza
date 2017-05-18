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

import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.var;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author dosen
 */
public final class DlgStokOpname extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();  
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement pstampil;
    private ResultSet rstampil;
    /** Creates new form DlgPenyakit
     * @param parent
     * @param modal */
    public DlgStokOpname(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(10,2);
        setSize(628,674);

        Object[] row={"Kode Barang","Nama Barang","Satuan","Tanggal","Stok","Real",
                      "Selisih","Nominal Hilang(Rp)","Keterangan","Kode Lokasi","Nama Lokasi"};
        tabMode=new DefaultTableModel(null,row){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbKamar.setModel(tabMode);
        //tbPenyakit.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbPenyakit.getBackground()));
        tbKamar.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbKamar.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 11; i++) {
            TableColumn column = tbKamar.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(80);
            }else if(i==1){
                column.setPreferredWidth(160);
            }else if(i==2){
                column.setPreferredWidth(50);
            }else if(i==3){
                column.setPreferredWidth(80);
            }else if(i==4){
                column.setPreferredWidth(35);
            }else if(i==5){
                column.setPreferredWidth(35);
            }else if(i==6){
                column.setPreferredWidth(40);
            }else if(i==7){
                column.setPreferredWidth(100);
            }else if(i==9){
                column.setPreferredWidth(120);
            }else if(i==8){
                column.setPreferredWidth(90);
            }else if(i==10){
                column.setPreferredWidth(160);
            }
        }
        tbKamar.setDefaultRenderer(Object.class, new WarnaTable());
        
        Kdbar.setDocument(new batasInput((byte)15).getKata(Kdbar));
        Stok.setDocument(new batasInput((byte)10).getKata(Stok));
        Real.setDocument(new batasInput((byte)10).getOnlyAngka(Real));
        Keterangan.setDocument(new batasInput((byte)60).getKata(Keterangan));
        
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        if(koneksiDB.cariCepat().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {tampil();}
                @Override
                public void removeUpdate(DocumentEvent e) {tampil();}
                @Override
                public void changedUpdate(DocumentEvent e) {tampil();}
            });
        }           
                
        try {
             pstampil=koneksi.prepareStatement("select opname.kode_brng, databarang.nama_brng, databarang.kode_sat, opname.tanggal, opname.stok, "+
                  "opname.real, opname.selisih, opname.nomihilang, opname.keterangan, bangsal.kd_bangsal, bangsal.nm_bangsal "+
                  "from opname inner join databarang inner join bangsal "+
                  "on opname.kode_brng=databarang.kode_brng and opname.kd_bangsal=bangsal.kd_bangsal "+
                  "where opname.tanggal between ? and ? and opname.kode_brng like ? or "+
                  "opname.tanggal between ? and ? and databarang.nama_brng like ? or "+
                  "opname.tanggal between ? and ? and opname.kode_brng like ? or "+
                  "opname.tanggal between ? and ? and bangsal.kd_bangsal like ? or "+
                  "opname.tanggal between ? and ? and bangsal.nm_bangsal like ? or "+
                  "opname.tanggal between ? and ? and databarang.kode_sat like ? or "+ 
                  "opname.tanggal between ? and ? and opname.keterangan like ? order by opname.tanggal");
        } catch (Exception e) {
            System.out.println(e);
        }
        
    } 
    private DecimalFormat df2 = new DecimalFormat("###,###,###,###,###,###,###");
    double total=0;


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Popup = new javax.swing.JPopupMenu();
        ppHapus = new javax.swing.JMenuItem();
        ppCetak = new javax.swing.JMenuItem();
        Kd2 = new widget.TextBox();
        panelisi4 = new widget.panelisi();
        label34 = new widget.Label();
        label32 = new widget.Label();
        Stok = new widget.TextBox();
        Tanggal = new widget.Tanggal();
        label17 = new widget.Label();
        Kdbar = new widget.TextBox();
        Nmbar = new widget.TextBox();
        Harga = new widget.TextBox();
        label36 = new widget.Label();
        Real = new widget.TextBox();
        Selisih = new widget.TextBox();
        label37 = new widget.Label();
        label38 = new widget.Label();
        Nominal = new widget.TextBox();
        Keterangan = new widget.TextBox();
        label18 = new widget.Label();
        kdgudang = new widget.TextBox();
        nmgudang = new widget.TextBox();
        label39 = new widget.Label();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbKamar = new widget.Table();
        jPanel1 = new javax.swing.JPanel();
        panelisi3 = new widget.panelisi();
        label11 = new widget.Label();
        Tgl1 = new widget.Tanggal();
        label19 = new widget.Label();
        Tgl2 = new widget.Tanggal();
        label9 = new widget.Label();
        TCari = new widget.TextBox();
        BtnAll = new widget.Button();
        BtnCari = new widget.Button();
        panelisi1 = new widget.panelisi();
        BtnHapus = new widget.Button();
        BtnPrint = new widget.Button();
        label10 = new widget.Label();
        LCount = new widget.Label();
        label12 = new widget.Label();
        LTotal = new widget.Label();
        BtnKeluar = new widget.Button();

        Popup.setName("Popup"); // NOI18N

        ppHapus.setBackground(new java.awt.Color(242, 242, 242));
        ppHapus.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        ppHapus.setForeground(new java.awt.Color(102, 51, 0));
        ppHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); // NOI18N
        ppHapus.setText("Hapus");
        ppHapus.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppHapus.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppHapus.setIconTextGap(8);
        ppHapus.setName("ppHapus"); // NOI18N
        ppHapus.setPreferredSize(new java.awt.Dimension(150, 25));
        ppHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnHapusActionPerformed(evt);
            }
        });
        Popup.add(ppHapus);

        ppCetak.setBackground(new java.awt.Color(242, 242, 242));
        ppCetak.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        ppCetak.setForeground(new java.awt.Color(102, 51, 0));
        ppCetak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png"))); // NOI18N
        ppCetak.setText("Cetak");
        ppCetak.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppCetak.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppCetak.setIconTextGap(8);
        ppCetak.setName("ppCetak"); // NOI18N
        ppCetak.setPreferredSize(new java.awt.Dimension(150, 25));
        ppCetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPrintActionPerformed(evt);
            }
        });
        Popup.add(ppCetak);

        Kd2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Kd2.setHighlighter(null);
        Kd2.setName("Kd2"); // NOI18N
        Kd2.setSelectionColor(new java.awt.Color(255, 255, 255));
        Kd2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Kd2KeyPressed(evt);
            }
        });

        panelisi4.setName("panelisi4"); // NOI18N
        panelisi4.setPreferredSize(new java.awt.Dimension(100, 103));
        panelisi4.setLayout(null);

        label34.setText("Stok :");
        label34.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        label34.setName("label34"); // NOI18N
        label34.setPreferredSize(new java.awt.Dimension(35, 23));
        panelisi4.add(label34);
        label34.setBounds(0, 40, 55, 23);

        label32.setText("Tanggal :");
        label32.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        label32.setName("label32"); // NOI18N
        label32.setPreferredSize(new java.awt.Dimension(35, 23));
        panelisi4.add(label32);
        label32.setBounds(550, 10, 60, 23);

        Stok.setEditable(false);
        Stok.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Stok.setHighlighter(null);
        Stok.setName("Stok"); // NOI18N
        Stok.setSelectionColor(new java.awt.Color(255, 255, 255));
        Stok.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                StokKeyPressed(evt);
            }
        });
        panelisi4.add(Stok);
        Stok.setBounds(59, 40, 70, 23);

        Tanggal.setEditable(false);
        Tanggal.setDisplayFormat("yyyy-MM-dd");
        Tanggal.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Tanggal.setName("Tanggal"); // NOI18N
        panelisi4.add(Tanggal);
        Tanggal.setBounds(613, 10, 95, 23);

        label17.setText("Barang :");
        label17.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(65, 23));
        panelisi4.add(label17);
        label17.setBounds(0, 10, 55, 23);

        Kdbar.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Kdbar.setName("Kdbar"); // NOI18N
        Kdbar.setPreferredSize(new java.awt.Dimension(80, 23));
        panelisi4.add(Kdbar);
        Kdbar.setBounds(59, 10, 90, 23);

        Nmbar.setEditable(false);
        Nmbar.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Nmbar.setName("Nmbar"); // NOI18N
        Nmbar.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi4.add(Nmbar);
        Nmbar.setBounds(151, 10, 257, 23);

        Harga.setEditable(false);
        Harga.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Harga.setHighlighter(null);
        Harga.setName("Harga"); // NOI18N
        Harga.setSelectionColor(new java.awt.Color(255, 255, 255));
        panelisi4.add(Harga);
        Harga.setBounds(410, 10, 110, 23);

        label36.setText("Real :");
        label36.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        label36.setName("label36"); // NOI18N
        label36.setPreferredSize(new java.awt.Dimension(35, 23));
        panelisi4.add(label36);
        label36.setBounds(130, 40, 40, 23);

        Real.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Real.setHighlighter(null);
        Real.setName("Real"); // NOI18N
        Real.setSelectionColor(new java.awt.Color(255, 255, 255));
        Real.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RealKeyPressed(evt);
            }
        });
        panelisi4.add(Real);
        Real.setBounds(174, 40, 55, 23);

        Selisih.setEditable(false);
        Selisih.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Selisih.setHighlighter(null);
        Selisih.setName("Selisih"); // NOI18N
        Selisih.setSelectionColor(new java.awt.Color(255, 255, 255));
        panelisi4.add(Selisih);
        Selisih.setBounds(284, 40, 55, 23);

        label37.setText("Selisih :");
        label37.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        label37.setName("label37"); // NOI18N
        label37.setPreferredSize(new java.awt.Dimension(35, 23));
        panelisi4.add(label37);
        label37.setBounds(230, 40, 50, 23);

        label38.setText("Nominal Hilang :");
        label38.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        label38.setName("label38"); // NOI18N
        label38.setPreferredSize(new java.awt.Dimension(35, 23));
        panelisi4.add(label38);
        label38.setBounds(474, 40, 100, 23);

        Nominal.setEditable(false);
        Nominal.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Nominal.setHighlighter(null);
        Nominal.setName("Nominal"); // NOI18N
        Nominal.setSelectionColor(new java.awt.Color(255, 255, 255));
        Nominal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NominalKeyPressed(evt);
            }
        });
        panelisi4.add(Nominal);
        Nominal.setBounds(578, 40, 130, 23);

        Keterangan.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Keterangan.setHighlighter(null);
        Keterangan.setName("Keterangan"); // NOI18N
        Keterangan.setSelectionColor(new java.awt.Color(255, 255, 255));
        Keterangan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKeyPressed(evt);
            }
        });
        panelisi4.add(Keterangan);
        Keterangan.setBounds(578, 70, 130, 23);

        label18.setText("Lokasi :");
        label18.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(65, 23));
        panelisi4.add(label18);
        label18.setBounds(0, 70, 55, 23);

        kdgudang.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        kdgudang.setName("kdgudang"); // NOI18N
        kdgudang.setPreferredSize(new java.awt.Dimension(80, 23));
        panelisi4.add(kdgudang);
        kdgudang.setBounds(59, 70, 90, 23);

        nmgudang.setEditable(false);
        nmgudang.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        nmgudang.setName("nmgudang"); // NOI18N
        nmgudang.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi4.add(nmgudang);
        nmgudang.setBounds(151, 70, 257, 23);

        label39.setText("Keterangan :");
        label39.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        label39.setName("label39"); // NOI18N
        label39.setPreferredSize(new java.awt.Dimension(35, 23));
        panelisi4.add(label39);
        label39.setBounds(474, 70, 100, 23);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Stok Opname Obat, Alkes & BHP Medis ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 40))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setComponentPopupMenu(Popup);
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbKamar.setAutoCreateRowSorter(true);
        tbKamar.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbKamar.setComponentPopupMenu(Popup);
        tbKamar.setName("tbKamar"); // NOI18N
        tbKamar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbKamarMouseClicked(evt);
            }
        });
        tbKamar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbKamarKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbKamar);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(816, 100));
        jPanel1.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(100, 44));
        panelisi3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(label11);

        Tgl1.setEditable(false);
        Tgl1.setDisplayFormat("dd-MM-yyyy");
        Tgl1.setName("Tgl1"); // NOI18N
        Tgl1.setPreferredSize(new java.awt.Dimension(95, 23));
        panelisi3.add(Tgl1);

        label19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label19.setText("s.d.");
        label19.setName("label19"); // NOI18N
        label19.setPreferredSize(new java.awt.Dimension(30, 23));
        panelisi3.add(label19);

        Tgl2.setEditable(false);
        Tgl2.setDisplayFormat("dd-MM-yyyy");
        Tgl2.setName("Tgl2"); // NOI18N
        Tgl2.setPreferredSize(new java.awt.Dimension(95, 23));
        panelisi3.add(Tgl2);

        label9.setText("Key Word :");
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(90, 23));
        panelisi3.add(label9);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(250, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelisi3.add(TCari);

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('3');
        BtnAll.setToolTipText("Alt+3");
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
        panelisi3.add(BtnAll);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('2');
        BtnCari.setToolTipText("Alt+2");
        BtnCari.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
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

        jPanel1.add(panelisi3, java.awt.BorderLayout.PAGE_START);

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        BtnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); // NOI18N
        BtnHapus.setMnemonic('H');
        BtnHapus.setText("Hapus");
        BtnHapus.setToolTipText("Alt+H");
        BtnHapus.setName("BtnHapus"); // NOI18N
        BtnHapus.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnHapusActionPerformed(evt);
            }
        });
        BtnHapus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnHapusKeyPressed(evt);
            }
        });
        panelisi1.add(BtnHapus);

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
        label10.setPreferredSize(new java.awt.Dimension(90, 30));
        panelisi1.add(label10);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(60, 30));
        panelisi1.add(LCount);

        label12.setText("Total :");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(55, 30));
        panelisi1.add(label12);

        LTotal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LTotal.setText("0");
        LTotal.setName("LTotal"); // NOI18N
        LTotal.setPreferredSize(new java.awt.Dimension(175, 30));
        panelisi1.add(LTotal);

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

        jPanel1.add(panelisi1, java.awt.BorderLayout.CENTER);

        internalFrame1.add(jPanel1, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        Valid.hapusTable(tabMode,Kdbar,"opname","kd_bangsal='"+kdgudang.getText()+"' and tanggal='"+Tanggal.getSelectedItem()+"' and kode_brng");
        BtnCariActionPerformed(evt);
        emptTeks();
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari, BtnAll);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnAll,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        BtnCariActionPerformed(evt);
        if(tbKamar.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            TCari.requestFocus();
        }else if(tbKamar.getRowCount()!=0){   
            Map<String, Object> param = new HashMap<>();    
                param.put("namars",var.getnamars());
                param.put("alamatrs",var.getalamatrs());
                param.put("kotars",var.getkabupatenrs());
                param.put("propinsirs",var.getpropinsirs());
                param.put("kontakrs",var.getkontakrs());
                param.put("emailrs",var.getemailrs());   
                param.put("logo",Sequel.cariGambar("select logo from setting")); 
            Valid.MyReport("rptOpname.jrxml","report","::[ Stok Opname ]::","select opname.kode_brng, databarang.nama_brng, databarang.kode_sat, opname.tanggal, opname.stok, "+
                  "opname.real, opname.selisih, opname.nomihilang, opname.keterangan, bangsal.kd_bangsal, bangsal.nm_bangsal "+
                  "from opname inner join databarang inner join bangsal "+
                  "on opname.kode_brng=databarang.kode_brng and opname.kd_bangsal=bangsal.kd_bangsal "+
                  "where opname.tanggal between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and opname.kode_brng like '%"+TCari.getText().trim()+"%' or "+
                  "opname.tanggal between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and databarang.nama_brng like '%"+TCari.getText().trim()+"%' or "+
                  "opname.tanggal between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and opname.kode_brng like '%"+TCari.getText().trim()+"%' or "+
                  "opname.tanggal between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and bangsal.kd_bangsal like '%"+TCari.getText().trim()+"%' or "+
                  "opname.tanggal between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and bangsal.nm_bangsal like '%"+TCari.getText().trim()+"%' or "+
                  "opname.tanggal between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and databarang.kode_sat like '%"+TCari.getText().trim()+"%' or "+ 
                  "opname.tanggal between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and opname.keterangan like '%"+TCari.getText().trim()+"%' order by opname.tanggal",param);
        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnKeluar);
        }
}//GEN-LAST:event_BtnPrintKeyPressed

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            tbKamar.requestFocus();
        }
}//GEN-LAST:event_TCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        tampil();
}//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari, BtnAll);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    private void tbKamarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbKamarMouseClicked
        if(tbKamar.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbKamarMouseClicked

    private void tbKamarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbKamarKeyPressed
        if(tbKamar.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
}//GEN-LAST:event_tbKamarKeyPressed

    private void Kd2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kd2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Kd2KeyPressed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnCari, TCari);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        tampil();
    }//GEN-LAST:event_BtnAllActionPerformed

private void KeteranganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKeyPressed
        Valid.pindah(evt,Real,Tanggal);
}//GEN-LAST:event_KeteranganKeyPressed

private void NominalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NominalKeyPressed
        // TODO add your handling code here:
}//GEN-LAST:event_NominalKeyPressed

private void RealKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RealKeyPressed
        Valid.pindah(evt,Kdbar,Keterangan);
}//GEN-LAST:event_RealKeyPressed

private void StokKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_StokKeyPressed
        Valid.pindah(evt,TCari,Tanggal);
}//GEN-LAST:event_StokKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
       tampil();
    }//GEN-LAST:event_formWindowOpened

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgStokOpname dialog = new DlgStokOpname(new javax.swing.JFrame(), true);
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
    private widget.Button BtnPrint;
    private widget.TextBox Harga;
    private widget.TextBox Kd2;
    private widget.TextBox Kdbar;
    private widget.TextBox Keterangan;
    private widget.Label LCount;
    private widget.Label LTotal;
    private widget.TextBox Nmbar;
    private widget.TextBox Nominal;
    private javax.swing.JPopupMenu Popup;
    private widget.TextBox Real;
    private widget.ScrollPane Scroll;
    private widget.TextBox Selisih;
    private widget.TextBox Stok;
    private widget.TextBox TCari;
    private widget.Tanggal Tanggal;
    private widget.Tanggal Tgl1;
    private widget.Tanggal Tgl2;
    private widget.InternalFrame internalFrame1;
    private javax.swing.JPanel jPanel1;
    private widget.TextBox kdgudang;
    private widget.Label label10;
    private widget.Label label11;
    private widget.Label label12;
    private widget.Label label17;
    private widget.Label label18;
    private widget.Label label19;
    private widget.Label label32;
    private widget.Label label34;
    private widget.Label label36;
    private widget.Label label37;
    private widget.Label label38;
    private widget.Label label39;
    private widget.Label label9;
    private widget.TextBox nmgudang;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi3;
    private widget.panelisi panelisi4;
    private javax.swing.JMenuItem ppCetak;
    private javax.swing.JMenuItem ppHapus;
    private widget.Table tbKamar;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try{           
            pstampil.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
            pstampil.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
            pstampil.setString(3,"%"+TCari.getText().trim()+"%");
            pstampil.setString(4,Valid.SetTgl(Tgl1.getSelectedItem()+""));
            pstampil.setString(5,Valid.SetTgl(Tgl2.getSelectedItem()+""));
            pstampil.setString(6,"%"+TCari.getText().trim()+"%");
            pstampil.setString(7,Valid.SetTgl(Tgl1.getSelectedItem()+""));
            pstampil.setString(8,Valid.SetTgl(Tgl2.getSelectedItem()+""));
            pstampil.setString(9,"%"+TCari.getText().trim()+"%");
            pstampil.setString(10,Valid.SetTgl(Tgl1.getSelectedItem()+""));
            pstampil.setString(11,Valid.SetTgl(Tgl2.getSelectedItem()+""));
            pstampil.setString(12,"%"+TCari.getText().trim()+"%");
            pstampil.setString(13,Valid.SetTgl(Tgl1.getSelectedItem()+""));
            pstampil.setString(14,Valid.SetTgl(Tgl2.getSelectedItem()+""));
            pstampil.setString(15,"%"+TCari.getText().trim()+"%");
            pstampil.setString(16,Valid.SetTgl(Tgl1.getSelectedItem()+""));
            pstampil.setString(17,Valid.SetTgl(Tgl2.getSelectedItem()+""));
            pstampil.setString(18,"%"+TCari.getText().trim()+"%");
            pstampil.setString(19,Valid.SetTgl(Tgl1.getSelectedItem()+""));
            pstampil.setString(20,Valid.SetTgl(Tgl2.getSelectedItem()+""));
            pstampil.setString(21,"%"+TCari.getText().trim()+"%");
            rstampil=pstampil.executeQuery();
            total=0;
            while(rstampil.next()){                
                total=total+rstampil.getDouble(8);
                tabMode.addRow(new Object[]{rstampil.getString(1),
                               rstampil.getString(2),
                               rstampil.getString(3),
                               rstampil.getString(4),
                               rstampil.getString(5),
                               rstampil.getString(6),                              
                               rstampil.getString(7),
                               df2.format(rstampil.getDouble(8)),
                               rstampil.getString(9),
                               rstampil.getString(10),
                               rstampil.getString(11)});
            }
        }catch(SQLException e){
            System.out.println("Notifikasi : "+e);
        }
        LCount.setText(""+tabMode.getRowCount());
        LTotal.setText(df2.format(total));
    }

    public void emptTeks() {
        Kdbar.setText("");
        Nmbar.setText("");
        Stok.setText("0");
        Harga.setText("0");
        Real.setText("0");
        Selisih.setText("0");        
        Keterangan.setText("");
        Tanggal.setDate(new Date());
        Nominal.setText("0");
        Stok.requestFocus();
    }

    private void getData() {
        int row=tbKamar.getSelectedRow();
        if(row!= -1){
            Kdbar.setText(tbKamar.getValueAt(row,0).toString());
            Kd2.setText(tbKamar.getValueAt(row,0).toString());
            Nmbar.setText(tbKamar.getValueAt(row,1).toString());
            Stok.setText(tbKamar.getValueAt(row,4).toString());
            Real.setText(tbKamar.getValueAt(row,5).toString());            
            Selisih.setText(tbKamar.getValueAt(row,6).toString());        
            Nominal.setText(tbKamar.getValueAt(row,7).toString());      
            Keterangan.setText(tbKamar.getValueAt(row,8).toString());   
            kdgudang.setText(tbKamar.getValueAt(row,9).toString());   
            nmgudang.setText(tbKamar.getValueAt(row,10).toString());       
            Valid.SetTgl(Tanggal,tbKamar.getValueAt(row,3).toString());
        }
    }

    public JTextField getTextField(){
        return Stok;
    }

    public JButton getButton(){
        return BtnKeluar;
    }
    
        
    public void isCek(){
        BtnHapus.setEnabled(var.getstok_opname_obat());
        BtnPrint.setEnabled(var.getstok_opname_obat());    
        ppHapus.setEnabled(var.getstok_opname_obat());
        ppCetak.setEnabled(var.getstok_opname_obat());
    }
}

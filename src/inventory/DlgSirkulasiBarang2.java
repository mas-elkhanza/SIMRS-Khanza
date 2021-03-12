package inventory;
import fungsi.WarnaTable;
import fungsi.batasInput;
import grafikanalisa.grafikpembelianterbanyak;
import grafikanalisa.grafikpembeliantersedikit;
import grafikanalisa.grafikpenjualanterbanyak;
import grafikanalisa.grafikpenjualantersedikit;
import grafikanalisa.grafikpiutangterbanyak;
import grafikanalisa.grafikpiutangtersedikit;
import grafikanalisa.grafikresepterbanyak;
import grafikanalisa.grafikreseptersedikit;
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
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import simrskhanza.DlgCariBangsal;

public class DlgSirkulasiBarang2 extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private DlgCariBangsal bangsal = new DlgCariBangsal(null, false); 
    private double jumlahjual=0,jumlahbeli=0,jumlahpesan=0,jumlahpiutang=0,jumlahutd=0,jumlahkeluar=0,jumlahmutasikeluar=0,
                   jumlahmutasimasuk=0,jumlahretbeli=0,jumlahretjual=0,jumlahretpiut=0,jumlahpasin=0,jumlahrespulang=0,
                   jumlahhibah=0,stok=0,stokawal=0,stokakhir=0;
    private PreparedStatement ps,ps2;
    private ResultSet rs,rs2;
    private String lokasi="",tglopname="";
    private String qrystok="",aktifkanbatch="no",hppfarmasi="";
    private DlgCariJenis jenis = new DlgCariJenis(null, false);
    private DlgCariKategori kategori = new DlgCariKategori(null, false);
    private DlgCariGolongan golongan = new DlgCariGolongan(null, false);

    /** 
     * @param parent
     * @param modal */
    public DlgSirkulasiBarang2(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        tabMode=new DefaultTableModel(null,new Object[]{
              "Kode Barang","Nama Barang","Satuan","Tgl.Opname","Stok Awal","Pengadaan","Pemesanan","Penjualan",
              "Ke Pasien","Piutang Jual","Retur Beli","Retur Jual","Retur Piutang","Pengambilan UTD",
              "Stok Keluar Medis","Resep Pulang","Mutasi Masuk","Mutasi Keluar","Hibah","Stok Akhir"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbDokter.setModel(tabMode);

        tbDokter.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 20; i++) {
            TableColumn column = tbDokter.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(100);
            }else if(i==1){
                column.setPreferredWidth(200);
            }else if(i==3){
                column.setPreferredWidth(67);
            }else{
                column.setPreferredWidth(80);
            }
        }
        tbDokter.setDefaultRenderer(Object.class, new WarnaTable());         
        
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        if(koneksiDB.CARICEPAT().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        prosesCari();
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        prosesCari();
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        prosesCari();
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
                if (bangsal.getTable().getSelectedRow() != -1) {
                    lokasi=bangsal.getTable().getValueAt(bangsal.getTable().getSelectedRow(),1).toString();
                    prosesCari2(bangsal.getTable().getValueAt(bangsal.getTable().getSelectedRow(), 0).toString());
                }
            }

            @Override
            public void windowIconified(WindowEvent e) {}

            @Override
            public void windowDeiconified(WindowEvent e) {}

            @Override
            public void windowActivated(WindowEvent e) {
                bangsal.emptTeks();
            }

            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
        
        jenis.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (jenis.getTable().getSelectedRow() != -1) {
                    nmjns.setText(jenis.getTable().getValueAt(jenis.getTable().getSelectedRow(), 1).toString());
                }
                TCari.requestFocus();
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });
        
        golongan.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (golongan.getTable().getSelectedRow() != -1) {
                    nmgolongan.setText(golongan.getTable().getValueAt(golongan.getTable().getSelectedRow(), 1).toString());
                }
                
                TCari.requestFocus();
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
                golongan.emptTeks();
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });
        
        kategori.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (kategori.getTable().getSelectedRow() != -1) {
                    nmkategori.setText(kategori.getTable().getValueAt(kategori.getTable().getSelectedRow(), 1).toString());
                }
                TCari.requestFocus();
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
                kategori.emptTeks();
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });
        
        try {
            aktifkanbatch = koneksiDB.AKTIFKANBATCHOBAT();
        } catch (Exception e) {
            aktifkanbatch = "no";
        }
        
        try {
            hppfarmasi=koneksiDB.HPPFARMASI();
        } catch (Exception e) {
            hppfarmasi="dasar";
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
        jPopupMenu1 = new javax.swing.JPopupMenu();
        ppGrafikJualBanyak = new javax.swing.JMenuItem();
        ppGrafikJualDikit = new javax.swing.JMenuItem();
        ppGrafikbeliBanyak = new javax.swing.JMenuItem();
        ppGrafikbelidikit = new javax.swing.JMenuItem();
        ppGrafikPiutangBanyak = new javax.swing.JMenuItem();
        ppGrafikPiutangDikit = new javax.swing.JMenuItem();
        ppGrafikResepPaliingBanyak = new javax.swing.JMenuItem();
        ppGrafikResepPaliingSedikit = new javax.swing.JMenuItem();
        ppLokasi = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        scrollPane1 = new widget.ScrollPane();
        tbDokter = new widget.Table();
        panelisi1 = new widget.panelisi();
        label10 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        label9 = new widget.Label();
        BtnAll = new widget.Button();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();
        panelisi4 = new widget.panelisi();
        label11 = new widget.Label();
        Tgl1 = new widget.Tanggal();
        label18 = new widget.Label();
        Tgl2 = new widget.Tanggal();
        label20 = new widget.Label();
        nmjns = new widget.TextBox();
        BtnJenis = new widget.Button();
        label22 = new widget.Label();
        nmkategori = new widget.TextBox();
        BtnKategori = new widget.Button();
        label23 = new widget.Label();
        nmgolongan = new widget.TextBox();
        BtnGolongan = new widget.Button();

        Kd2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Kd2.setName("Kd2"); // NOI18N
        Kd2.setPreferredSize(new java.awt.Dimension(207, 23));

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        ppGrafikJualBanyak.setBackground(new java.awt.Color(255, 255, 254));
        ppGrafikJualBanyak.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppGrafikJualBanyak.setForeground(new java.awt.Color(50, 50, 50));
        ppGrafikJualBanyak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Create-Ticket24.png"))); // NOI18N
        ppGrafikJualBanyak.setText("Grafik 10 Barang Penjualan Terbanyak");
        ppGrafikJualBanyak.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikJualBanyak.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikJualBanyak.setName("ppGrafikJualBanyak"); // NOI18N
        ppGrafikJualBanyak.setPreferredSize(new java.awt.Dimension(300, 25));
        ppGrafikJualBanyak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikJualBanyakActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppGrafikJualBanyak);

        ppGrafikJualDikit.setBackground(new java.awt.Color(255, 255, 254));
        ppGrafikJualDikit.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppGrafikJualDikit.setForeground(new java.awt.Color(50, 50, 50));
        ppGrafikJualDikit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Create-Ticket24.png"))); // NOI18N
        ppGrafikJualDikit.setText("Grafik 10 Barang Penjualan Tersedikit");
        ppGrafikJualDikit.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikJualDikit.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikJualDikit.setName("ppGrafikJualDikit"); // NOI18N
        ppGrafikJualDikit.setPreferredSize(new java.awt.Dimension(300, 25));
        ppGrafikJualDikit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikJualDikitActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppGrafikJualDikit);

        ppGrafikbeliBanyak.setBackground(new java.awt.Color(255, 255, 254));
        ppGrafikbeliBanyak.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppGrafikbeliBanyak.setForeground(new java.awt.Color(50, 50, 50));
        ppGrafikbeliBanyak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Create-Ticket24.png"))); // NOI18N
        ppGrafikbeliBanyak.setText("Grafik 10 Barang Pembelian Terbanyak");
        ppGrafikbeliBanyak.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikbeliBanyak.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikbeliBanyak.setName("ppGrafikbeliBanyak"); // NOI18N
        ppGrafikbeliBanyak.setPreferredSize(new java.awt.Dimension(300, 25));
        ppGrafikbeliBanyak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikbeliBanyakActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppGrafikbeliBanyak);

        ppGrafikbelidikit.setBackground(new java.awt.Color(255, 255, 254));
        ppGrafikbelidikit.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppGrafikbelidikit.setForeground(new java.awt.Color(50, 50, 50));
        ppGrafikbelidikit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Create-Ticket24.png"))); // NOI18N
        ppGrafikbelidikit.setText("Grafik 10 Barang Pembelian Tersedikit");
        ppGrafikbelidikit.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikbelidikit.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikbelidikit.setName("ppGrafikbelidikit"); // NOI18N
        ppGrafikbelidikit.setPreferredSize(new java.awt.Dimension(300, 25));
        ppGrafikbelidikit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikbelidikitActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppGrafikbelidikit);

        ppGrafikPiutangBanyak.setBackground(new java.awt.Color(255, 255, 254));
        ppGrafikPiutangBanyak.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppGrafikPiutangBanyak.setForeground(new java.awt.Color(50, 50, 50));
        ppGrafikPiutangBanyak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Create-Ticket24.png"))); // NOI18N
        ppGrafikPiutangBanyak.setText("Grafik 10 Barang Piutang Terbanyak");
        ppGrafikPiutangBanyak.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikPiutangBanyak.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikPiutangBanyak.setName("ppGrafikPiutangBanyak"); // NOI18N
        ppGrafikPiutangBanyak.setPreferredSize(new java.awt.Dimension(300, 25));
        ppGrafikPiutangBanyak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikPiutangBanyakActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppGrafikPiutangBanyak);

        ppGrafikPiutangDikit.setBackground(new java.awt.Color(255, 255, 254));
        ppGrafikPiutangDikit.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppGrafikPiutangDikit.setForeground(new java.awt.Color(50, 50, 50));
        ppGrafikPiutangDikit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Create-Ticket24.png"))); // NOI18N
        ppGrafikPiutangDikit.setText("Grafik 10 Barang Piutang Tersedikit");
        ppGrafikPiutangDikit.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikPiutangDikit.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikPiutangDikit.setName("ppGrafikPiutangDikit"); // NOI18N
        ppGrafikPiutangDikit.setPreferredSize(new java.awt.Dimension(300, 25));
        ppGrafikPiutangDikit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikPiutangDikitActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppGrafikPiutangDikit);

        ppGrafikResepPaliingBanyak.setBackground(new java.awt.Color(255, 255, 254));
        ppGrafikResepPaliingBanyak.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppGrafikResepPaliingBanyak.setForeground(new java.awt.Color(50, 50, 50));
        ppGrafikResepPaliingBanyak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Create-Ticket24.png"))); // NOI18N
        ppGrafikResepPaliingBanyak.setText("Grafik 10 Barang Resep Ke Pasien Terbanyak");
        ppGrafikResepPaliingBanyak.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikResepPaliingBanyak.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikResepPaliingBanyak.setName("ppGrafikResepPaliingBanyak"); // NOI18N
        ppGrafikResepPaliingBanyak.setPreferredSize(new java.awt.Dimension(300, 25));
        ppGrafikResepPaliingBanyak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikResepPaliingBanyakActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppGrafikResepPaliingBanyak);

        ppGrafikResepPaliingSedikit.setBackground(new java.awt.Color(255, 255, 254));
        ppGrafikResepPaliingSedikit.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppGrafikResepPaliingSedikit.setForeground(new java.awt.Color(50, 50, 50));
        ppGrafikResepPaliingSedikit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Create-Ticket24.png"))); // NOI18N
        ppGrafikResepPaliingSedikit.setText("Grafik 10 Barang Resep Ke Pasien Tersedikit");
        ppGrafikResepPaliingSedikit.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikResepPaliingSedikit.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikResepPaliingSedikit.setName("ppGrafikResepPaliingSedikit"); // NOI18N
        ppGrafikResepPaliingSedikit.setPreferredSize(new java.awt.Dimension(300, 25));
        ppGrafikResepPaliingSedikit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikResepPaliingSedikitActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppGrafikResepPaliingSedikit);

        ppLokasi.setBackground(new java.awt.Color(255, 255, 254));
        ppLokasi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppLokasi.setForeground(new java.awt.Color(50, 50, 50));
        ppLokasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Create-Ticket24.png"))); // NOI18N
        ppLokasi.setText("Tampilkan Per Lokasi");
        ppLokasi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppLokasi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppLokasi.setName("ppLokasi"); // NOI18N
        ppLokasi.setPreferredSize(new java.awt.Dimension(180, 25));
        ppLokasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppLokasiBtnPrintActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppLokasi);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Sirkulasi Obat, Alkes & BHP Medis Keluar Masuk ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        scrollPane1.setToolTipText("<html>Stok Awal mengambil data dari Stok Opname pada tanggal terdekat pencarian di tanggal pertama. <br>\nJika Stok Opname pada tanggal tersebut tidak ditemukan maka Stok Awal dihitung dari <br>\nStok Akhir ditambah dan dikurangi dengan transaksi yang telah terjadi.</html>");
        scrollPane1.setComponentPopupMenu(jPopupMenu1);
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
        tbDokter.setToolTipText("<html>Stok Awal mengambil data dari Stok Opname pada tanggal terdekat pencarian di tanggal pertama. <br>Jika Stok Opname pada tanggal tersebut tidak ditemukan maka Stok Awal dihitung dari <br>Stok Akhir ditambah dan dikurangi dengan transaksi yang telah terjadi.</html>");
        tbDokter.setComponentPopupMenu(jPopupMenu1);
        tbDokter.setName("tbDokter"); // NOI18N
        scrollPane1.setViewportView(tbDokter);

        internalFrame1.add(scrollPane1, java.awt.BorderLayout.CENTER);

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 56));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label10.setText("Key Word :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(69, 23));
        panelisi1.add(label10);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(290, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelisi1.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('2');
        BtnCari.setToolTipText("Alt+2");
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
        panelisi1.add(BtnCari);

        label9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(39, 30));
        panelisi1.add(label9);

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('A');
        BtnAll.setText("Semua");
        BtnAll.setToolTipText("Alt+A");
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
        BtnPrint.setMnemonic('P');
        BtnPrint.setText("Cetak");
        BtnPrint.setToolTipText("Alt+P");
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

        panelisi4.setName("panelisi4"); // NOI18N
        panelisi4.setPreferredSize(new java.awt.Dimension(100, 74));
        panelisi4.setLayout(null);

        label11.setText("Tgl.Transaksi :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(80, 23));
        panelisi4.add(label11);
        label11.setBounds(0, 10, 90, 23);

        Tgl1.setDisplayFormat("dd-MM-yyyy");
        Tgl1.setName("Tgl1"); // NOI18N
        Tgl1.setPreferredSize(new java.awt.Dimension(90, 23));
        panelisi4.add(Tgl1);
        Tgl1.setBounds(95, 10, 110, 23);

        label18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label18.setText("s.d.");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(30, 23));
        panelisi4.add(label18);
        label18.setBounds(216, 10, 30, 23);

        Tgl2.setDisplayFormat("dd-MM-yyyy");
        Tgl2.setName("Tgl2"); // NOI18N
        Tgl2.setPreferredSize(new java.awt.Dimension(90, 23));
        panelisi4.add(Tgl2);
        Tgl2.setBounds(257, 10, 110, 23);

        label20.setText("Jenis :");
        label20.setName("label20"); // NOI18N
        label20.setPreferredSize(new java.awt.Dimension(40, 23));
        panelisi4.add(label20);
        label20.setBounds(423, 10, 60, 23);

        nmjns.setEditable(false);
        nmjns.setName("nmjns"); // NOI18N
        nmjns.setPreferredSize(new java.awt.Dimension(150, 23));
        panelisi4.add(nmjns);
        nmjns.setBounds(487, 10, 240, 23);

        BtnJenis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnJenis.setMnemonic('2');
        BtnJenis.setToolTipText("Alt+2");
        BtnJenis.setName("BtnJenis"); // NOI18N
        BtnJenis.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnJenis.setRequestFocusEnabled(false);
        BtnJenis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnJenisActionPerformed(evt);
            }
        });
        panelisi4.add(BtnJenis);
        BtnJenis.setBounds(730, 10, 28, 23);

        label22.setText("Kategori :");
        label22.setName("label22"); // NOI18N
        label22.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi4.add(label22);
        label22.setBounds(0, 40, 90, 23);

        nmkategori.setEditable(false);
        nmkategori.setName("nmkategori"); // NOI18N
        nmkategori.setPreferredSize(new java.awt.Dimension(150, 23));
        panelisi4.add(nmkategori);
        nmkategori.setBounds(95, 40, 240, 23);

        BtnKategori.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnKategori.setMnemonic('2');
        BtnKategori.setToolTipText("Alt+2");
        BtnKategori.setName("BtnKategori"); // NOI18N
        BtnKategori.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnKategori.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKategoriActionPerformed(evt);
            }
        });
        panelisi4.add(BtnKategori);
        BtnKategori.setBounds(338, 40, 28, 23);

        label23.setText("Golongan :");
        label23.setName("label23"); // NOI18N
        label23.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi4.add(label23);
        label23.setBounds(423, 40, 60, 23);

        nmgolongan.setEditable(false);
        nmgolongan.setName("nmgolongan"); // NOI18N
        nmgolongan.setPreferredSize(new java.awt.Dimension(150, 23));
        panelisi4.add(nmgolongan);
        nmgolongan.setBounds(487, 40, 240, 23);

        BtnGolongan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnGolongan.setMnemonic('2');
        BtnGolongan.setToolTipText("Alt+2");
        BtnGolongan.setName("BtnGolongan"); // NOI18N
        BtnGolongan.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnGolongan.setRequestFocusEnabled(false);
        BtnGolongan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnGolonganActionPerformed(evt);
            }
        });
        panelisi4.add(BtnGolongan);
        BtnGolongan.setBounds(730, 40, 28, 23);

        internalFrame1.add(panelisi4, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents
/*
private void KdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdKeyPressed
    Valid.pindah(evt,BtnCari,Nm);
}//GEN-LAST:event_TKdKeyPressed
*/

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            TCari.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            
            Sequel.queryu("truncate table temporary");
            int row=tabMode.getRowCount();
            for(int i=0;i<row;i++){  
                Sequel.menyimpan("temporary","'0',?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,'','','','','','','','','','','','','','','','',''",20,new String[]{
                    tabMode.getValueAt(i,0).toString(),tabMode.getValueAt(i,1).toString(),tabMode.getValueAt(i,2).toString(),
                    tabMode.getValueAt(i,3).toString(),tabMode.getValueAt(i,4).toString(),tabMode.getValueAt(i,5).toString(),
                    tabMode.getValueAt(i,6).toString(),tabMode.getValueAt(i,7).toString(),tabMode.getValueAt(i,8).toString(),
                    tabMode.getValueAt(i,9).toString(),tabMode.getValueAt(i,10).toString(),tabMode.getValueAt(i,11).toString(),
                    tabMode.getValueAt(i,12).toString(),tabMode.getValueAt(i,13).toString(),tabMode.getValueAt(i,14).toString(),
                    tabMode.getValueAt(i,15).toString(),tabMode.getValueAt(i,16).toString(),tabMode.getValueAt(i,17).toString(),
                    tabMode.getValueAt(i,18).toString(),tabMode.getValueAt(i,19).toString()
                }); 
            }
            
            Map<String, Object> param = new HashMap<>(); 
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("emailrs",akses.getemailrs());   
                param.put("logo",Sequel.cariGambar("select logo from setting")); 
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            if(lokasi.equals("")){
                Valid.MyReport("rptSirkulasi2.jasper","report","::[ Sirkulasi Barang ]::",param);
            }else if(!lokasi.equals("")){
                param.put("bangsal",lokasi); 
                Valid.MyReport("rptSirkulasi4.jasper","report","::[ Sirkulasi Barang ]::",param);
            }    
            this.setCursor(Cursor.getDefaultCursor());
        }
        
    }//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt,Tgl2,BtnKeluar);
        }
    }//GEN-LAST:event_BtnPrintKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnPrint,Tgl1);}
    }//GEN-LAST:event_BtnKeluarKeyPressed

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }
    }//GEN-LAST:event_TCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
        lokasi="";
        prosesCari();
        this.setCursor(Cursor.getDefaultCursor());
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
        nmgolongan.setText("");
        nmjns.setText("");
        nmkategori.setText("");
        lokasi="";
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
        prosesCari();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnPrint, BtnKeluar);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

    private void ppGrafikJualBanyakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikJualBanyakActionPerformed
        grafikpenjualanterbanyak grafik=new grafikpenjualanterbanyak("Grafik 10 Barang Penjualan Terbanyak"," penjualan.tgl_jual between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                       "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' ");
                    grafik.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
                    grafik.setLocationRelativeTo(internalFrame1);
                    grafik.setAlwaysOnTop(false);
                    grafik.setVisible(true);
    }//GEN-LAST:event_ppGrafikJualBanyakActionPerformed

    private void ppGrafikJualDikitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikJualDikitActionPerformed
        grafikpenjualantersedikit grafik=new grafikpenjualantersedikit("Grafik 10 Barang Penjualan Tersedikit"," penjualan.tgl_jual between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                       "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' ");
                    grafik.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
                    grafik.setLocationRelativeTo(internalFrame1);
                    grafik.setAlwaysOnTop(false);
                    grafik.setVisible(true);
    }//GEN-LAST:event_ppGrafikJualDikitActionPerformed

    private void ppGrafikbeliBanyakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikbeliBanyakActionPerformed
        grafikpembelianterbanyak grafik=new grafikpembelianterbanyak("Grafik 10 Barang Pembelian Terbanyak"," pembelian.tgl_beli between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                       "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' ");
                    grafik.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
                    grafik.setLocationRelativeTo(internalFrame1);
                    grafik.setAlwaysOnTop(false);
                    grafik.setVisible(true);
    }//GEN-LAST:event_ppGrafikbeliBanyakActionPerformed

    private void ppGrafikbelidikitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikbelidikitActionPerformed
        grafikpembeliantersedikit grafik=new grafikpembeliantersedikit("Grafik 10 Barang Pembelian Tersedikit"," pembelian.tgl_beli between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                       "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' ");
                    grafik.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
                    grafik.setLocationRelativeTo(internalFrame1);
                    grafik.setAlwaysOnTop(false);
                    grafik.setVisible(true);
    }//GEN-LAST:event_ppGrafikbelidikitActionPerformed

    private void ppGrafikPiutangBanyakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikPiutangBanyakActionPerformed
        grafikpiutangterbanyak grafik=new grafikpiutangterbanyak("Grafik 10 Barang Piutang Terbanyak"," piutang.tgl_piutang between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                       "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' ");
                    grafik.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
                    grafik.setLocationRelativeTo(internalFrame1);
                    grafik.setAlwaysOnTop(false);
                    grafik.setVisible(true);
    }//GEN-LAST:event_ppGrafikPiutangBanyakActionPerformed

    private void ppGrafikPiutangDikitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikPiutangDikitActionPerformed
        grafikpiutangtersedikit grafik=new grafikpiutangtersedikit("Grafik 10 Barang Piutang Tersedikit"," piutang.tgl_piutang between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                       "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' ");
                    grafik.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
                    grafik.setLocationRelativeTo(internalFrame1);
                    grafik.setAlwaysOnTop(false);
                    grafik.setVisible(true);
    }//GEN-LAST:event_ppGrafikPiutangDikitActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        
    }//GEN-LAST:event_formWindowOpened

    private void ppGrafikResepPaliingBanyakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikResepPaliingBanyakActionPerformed
        grafikresepterbanyak grafik=new grafikresepterbanyak("Grafik 10 Barang Resep Ke Pasien Terbanyak"," tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
            "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' ");
        grafik.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
        grafik.setLocationRelativeTo(internalFrame1);
        grafik.setAlwaysOnTop(false);
        grafik.setVisible(true);
    }//GEN-LAST:event_ppGrafikResepPaliingBanyakActionPerformed

    private void ppGrafikResepPaliingSedikitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikResepPaliingSedikitActionPerformed
        grafikreseptersedikit grafik=new grafikreseptersedikit("Grafik 10 Barang Resep Ke Pasien Tersedikit"," tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
            "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' ");
        grafik.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
        grafik.setLocationRelativeTo(internalFrame1);
        grafik.setAlwaysOnTop(false);
        grafik.setVisible(true);
    }//GEN-LAST:event_ppGrafikResepPaliingSedikitActionPerformed

    private void ppLokasiBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppLokasiBtnPrintActionPerformed
        bangsal.isCek();
        bangsal.setSize(internalFrame1.getWidth() - 40, internalFrame1.getHeight() - 40);
        bangsal.setLocationRelativeTo(internalFrame1);
        bangsal.setVisible(true);
    }//GEN-LAST:event_ppLokasiBtnPrintActionPerformed

    private void BtnJenisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnJenisActionPerformed
        jenis.isCek();
        jenis.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        jenis.setLocationRelativeTo(internalFrame1);
        jenis.setAlwaysOnTop(false);
        jenis.setVisible(true);
    }//GEN-LAST:event_BtnJenisActionPerformed

    private void BtnKategoriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKategoriActionPerformed
        kategori.isCek();
        kategori.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        kategori.setLocationRelativeTo(internalFrame1);
        kategori.setAlwaysOnTop(false);
        kategori.setVisible(true);
    }//GEN-LAST:event_BtnKategoriActionPerformed

    private void BtnGolonganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnGolonganActionPerformed
        golongan.isCek();
        golongan.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        golongan.setLocationRelativeTo(internalFrame1);
        golongan.setAlwaysOnTop(false);
        golongan.setVisible(true);
    }//GEN-LAST:event_BtnGolonganActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgSirkulasiBarang2 dialog = new DlgSirkulasiBarang2(new javax.swing.JFrame(), true);
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
    private widget.Button BtnGolongan;
    private widget.Button BtnJenis;
    private widget.Button BtnKategori;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.TextBox Kd2;
    private widget.TextBox TCari;
    private widget.Tanggal Tgl1;
    private widget.Tanggal Tgl2;
    private widget.InternalFrame internalFrame1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.Label label10;
    private widget.Label label11;
    private widget.Label label18;
    private widget.Label label20;
    private widget.Label label22;
    private widget.Label label23;
    private widget.Label label9;
    private widget.TextBox nmgolongan;
    private widget.TextBox nmjns;
    private widget.TextBox nmkategori;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi4;
    private javax.swing.JMenuItem ppGrafikJualBanyak;
    private javax.swing.JMenuItem ppGrafikJualDikit;
    private javax.swing.JMenuItem ppGrafikPiutangBanyak;
    private javax.swing.JMenuItem ppGrafikPiutangDikit;
    private javax.swing.JMenuItem ppGrafikResepPaliingBanyak;
    private javax.swing.JMenuItem ppGrafikResepPaliingSedikit;
    private javax.swing.JMenuItem ppGrafikbeliBanyak;
    private javax.swing.JMenuItem ppGrafikbelidikit;
    private javax.swing.JMenuItem ppLokasi;
    private widget.ScrollPane scrollPane1;
    private widget.Table tbDokter;
    // End of variables declaration//GEN-END:variables

    private void prosesCari() {
       Valid.tabelKosong(tabMode);      
       try{   
            ps=koneksi.prepareStatement(
                    "select databarang.kode_brng,databarang.nama_brng,kodesatuan.satuan "+
                    "from databarang inner join kodesatuan on databarang.kode_sat=kodesatuan.kode_sat "+
                    "inner join jenis on databarang.kdjns=jenis.kdjns "+
                    "inner join golongan_barang on databarang.kode_golongan=golongan_barang.kode "+
                    "inner join kategori_barang on databarang.kode_kategori=kategori_barang.kode where "+
                    "jenis.nama like ? and kategori_barang.nama like ? and golongan_barang.nama like ? and "+
                    "(databarang.kode_brng like ? or databarang.nama_brng like ?) order by databarang.kode_brng");
            try {
                ps.setString(1,"%"+nmjns.getText().trim()+"%");
                ps.setString(2,"%"+nmkategori.getText().trim()+"%");
                ps.setString(3,"%"+nmgolongan.getText().trim()+"%");
                ps.setString(4,"%"+TCari.getText().trim()+"%");
                ps.setString(5,"%"+TCari.getText().trim()+"%");
                rs=ps.executeQuery();    
                
                if(aktifkanbatch.equals("yes")){
                    qrystok="select sum(stok),(sum(stok)*"+hppfarmasi+") as aset "+
                            "from gudangbarang inner join databarang on gudangbarang.kode_brng=databarang.kode_brng "+
                            "where gudangbarang.kode_brng=? and gudangbarang.no_batch<>'' and gudangbarang.no_faktur<>''";
                }else{
                    qrystok="select sum(stok),(sum(stok)*"+hppfarmasi+") as aset "+
                            "from gudangbarang inner join databarang on gudangbarang.kode_brng=databarang.kode_brng "+
                            "where gudangbarang.kode_brng=? and gudangbarang.no_batch='' and gudangbarang.no_faktur=''";
                }
                while(rs.next()){
                    jumlahjual=0;jumlahbeli=0;jumlahpiutang=0;jumlahpesan=0;jumlahretbeli=0;jumlahretjual=0;
                    jumlahretpiut=0;jumlahpasin=0;stok=0;stokawal=0;jumlahutd=0;jumlahkeluar=0;jumlahrespulang=0;
                    jumlahmutasikeluar=0;jumlahmutasimasuk=0;jumlahhibah=0;stokakhir=0;tglopname="0000-00-00";
                    tglopname=Sequel.cariIsi("select tanggal from opname where kode_brng='"+rs.getString(1)+"' and tanggal between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' order by tanggal asc limit 1");

                    if(tglopname.equals("")){
                        tglopname=Valid.SetTgl(Tgl1.getSelectedItem()+"");
                    }
                    
                    ps2=koneksi.prepareStatement(qrystok);
                    try {
                        ps2.setString(1,rs.getString(1));
                        rs2=ps2.executeQuery();
                        if(rs2.next()){
                            stok=rs2.getDouble(1);
                        } 
                    } catch (Exception e) {
                        System.out.println("Note : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                                       
                    //pembelian 
                    ps2=koneksi.prepareStatement("select sum(detailbeli.jumlah2) "+
                        " from pembelian inner join detailbeli "+
                        " on pembelian.no_faktur=detailbeli.no_faktur "+
                        " where detailbeli.kode_brng=? and pembelian.tgl_beli "+
                        " between ? and ? ");
                    try {
                        ps2.setString(1,rs.getString(1));
                        ps2.setString(2,tglopname);
                        ps2.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                        rs2=ps2.executeQuery();
                        if(rs2.next()){                    
                            jumlahbeli=rs2.getDouble(1);
                        }
                    } catch (Exception e) {
                        System.out.println("Note : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                        
                    //pemesanan
                    ps2=koneksi.prepareStatement("select sum(detailpesan.jumlah2) "+
                        " from pemesanan inner join detailpesan "+
                        " on pemesanan.no_faktur=detailpesan.no_faktur "+
                        " where detailpesan.kode_brng=? and pemesanan.tgl_pesan "+
                        " between ? and ? ");            
                    try {
                        ps2.setString(1,rs.getString(1));
                        ps2.setString(2,tglopname);
                        ps2.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                        rs2=ps2.executeQuery();
                        if(rs2.next()){                    
                            jumlahpesan=rs2.getDouble(1);
                        }
                    } catch (Exception e) {
                        System.out.println("Note : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                        
                    //penjualan
                    ps2=koneksi.prepareStatement("select sum(detailjual.jumlah) "+
                        " from penjualan inner join detailjual "+
                        " on penjualan.nota_jual=detailjual.nota_jual "+
                        " where detailjual.kode_brng=? and "+
                        " penjualan.tgl_jual  between ? and ? ");
                    try {
                        ps2.setString(1,rs.getString(1));
                        ps2.setString(2,tglopname);
                        ps2.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                        rs2=ps2.executeQuery();
                        if(rs2.next()){                    
                            jumlahjual=rs2.getDouble(1);
                        }
                    } catch (Exception e) {
                        System.out.println("Note : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                        

                    //piutang 
                    ps2=koneksi.prepareStatement("select sum(detailpiutang.jumlah) "+
                        " from piutang inner join detailpiutang "+
                        " on piutang.nota_piutang=detailpiutang.nota_piutang "+
                        " where detailpiutang.kode_brng=? and "+
                        " piutang.tgl_piutang between ? and ? ");            
                    try {
                        ps2.setString(1,rs.getString(1));
                        ps2.setString(2,tglopname);
                        ps2.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                        rs2=ps2.executeQuery();
                        if(rs2.next()){                    
                            jumlahpiutang=rs2.getDouble(1);
                        }
                    } catch (Exception e) {
                        System.out.println("Note : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }   

                    //returbeli
                    ps2=koneksi.prepareStatement("select sum(detreturbeli.jml_retur2) "+
                        " from returbeli inner join detreturbeli "+
                        " on returbeli.no_retur_beli=detreturbeli.no_retur_beli "+
                        " where detreturbeli.kode_brng=? and "+
                        " returbeli.tgl_retur between ? and ? ");            
                    try {
                        ps2.setString(1,rs.getString(1));
                        ps2.setString(2,tglopname);
                        ps2.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                        rs2=ps2.executeQuery();
                        if(rs2.next()){                    
                            jumlahretbeli=rs2.getDouble(1);
                        }
                    } catch (Exception e) {
                        System.out.println("Note : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                        
                    //returjual
                    ps2=koneksi.prepareStatement("select sum(detreturjual.jml_retur) "+
                        " from returjual inner join detreturjual "+
                        " on returjual.no_retur_jual=detreturjual.no_retur_jual "+
                        " where detreturjual.kode_brng=? and "+
                        " returjual.tgl_retur between ? and ? ");            
                    try {
                        ps2.setString(1,rs.getString(1));
                        ps2.setString(2,tglopname);
                        ps2.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                        rs2=ps2.executeQuery();
                        if(rs2.next()){                    
                            jumlahretjual=rs2.getDouble(1);
                        } 
                    } catch (Exception e) {
                        System.out.println("Note : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                                       
                    ps2=koneksi.prepareStatement("select sum(detreturpiutang.jml_retur) "+
                        " from returpiutang inner join detreturpiutang "+
                        " on returpiutang.no_retur_piutang=detreturpiutang.no_retur_piutang "+
                        " where detreturpiutang.kode_brng=? and "+
                        " returpiutang.tgl_retur between ? and ?");            
                    try {
                        ps2.setString(1,rs.getString(1));
                        ps2.setString(2,tglopname);
                        ps2.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                        rs2=ps2.executeQuery();
                        if(rs2.next()){                    
                            jumlahretpiut=rs2.getDouble(1);
                        }
                    } catch (Exception e) {
                        System.out.println("Note : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }  
                    
                    ps2=koneksi.prepareStatement("select sum(detail_pemberian_obat.jml) as jumlah "+
                        " from detail_pemberian_obat where detail_pemberian_obat.kode_brng=? and "+
                        " detail_pemberian_obat.tgl_perawatan between ? and ?");            
                    try {
                        ps2.setString(1,rs.getString(1));
                        ps2.setString(2,tglopname);
                        ps2.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                        rs2=ps2.executeQuery();
                        if(rs2.next()){                    
                            jumlahpasin=rs2.getDouble(1);
                        }
                    } catch (Exception e) {
                        System.out.println("Note : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                        
                    ps2=koneksi.prepareStatement("select sum(utd_pengambilan_medis.jml) as jumlah "+
                        " from utd_pengambilan_medis where utd_pengambilan_medis.kode_brng=? and "+
                        " utd_pengambilan_medis.tanggal between ? and ?");            
                    try {
                        ps2.setString(1,rs.getString(1));
                        ps2.setString(2,tglopname);
                        ps2.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                        rs2=ps2.executeQuery();
                        if(rs2.next()){                    
                            jumlahutd=rs2.getDouble(1);
                        }
                    } catch (Exception e) {
                        System.out.println("Note : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }

                    ps2=koneksi.prepareStatement("select sum(detail_pengeluaran_obat_bhp.jumlah) "+
                        " from pengeluaran_obat_bhp inner join detail_pengeluaran_obat_bhp "+
                        " on pengeluaran_obat_bhp.no_keluar=detail_pengeluaran_obat_bhp.no_keluar "+
                        " where detail_pengeluaran_obat_bhp.kode_brng=? and pengeluaran_obat_bhp.tanggal "+
                        " between ? and ? ");
                    try {
                        ps2.setString(1,rs.getString(1));
                        ps2.setString(2,tglopname);
                        ps2.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                        rs2=ps2.executeQuery();
                        if(rs2.next()){                    
                            jumlahkeluar=rs2.getDouble(1);
                        }
                    } catch (Exception e) {
                        System.out.println("Note : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }    

                    ps2=koneksi.prepareStatement("select sum(resep_pulang.jml_barang), sum(resep_pulang.total) "+
                        " from resep_pulang where resep_pulang.kode_brng=? and "+
                        " resep_pulang.tanggal between ? and ?");
                    try {
                        ps2.setString(1,rs.getString(1));
                        ps2.setString(2,tglopname);
                        ps2.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                        rs2=ps2.executeQuery();
                        if(rs2.next()){                    
                            jumlahrespulang=rs2.getDouble(1);
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikas Resep Pulang : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                    
                    ps2=koneksi.prepareStatement("select sum(jml), sum(jml*harga) "+
                        " from mutasibarang where kode_brng=? and "+
                        " tanggal between ? and ?");
                    try {
                        ps2.setString(1,rs.getString(1));
                        ps2.setString(2,tglopname+" 00:00:00");
                        ps2.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                        rs2=ps2.executeQuery();
                        if(rs2.next()){                    
                            jumlahmutasimasuk=rs2.getDouble(1);
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikas Mutasi Masuk : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                    
                    ps2=koneksi.prepareStatement("select sum(jml), sum(jml*harga) "+
                        " from mutasibarang where kode_brng=? and "+
                        " tanggal between ? and ?");
                    try {
                        ps2.setString(1,rs.getString(1));
                        ps2.setString(2,tglopname+" 00:00:00");
                        ps2.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                        rs2=ps2.executeQuery();
                        if(rs2.next()){                    
                            jumlahmutasikeluar=rs2.getDouble(1);
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikas Mutasi Keluar : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                    
                    //hibah 
                    ps2=koneksi.prepareStatement("select sum(detailhibah_obat_bhp.jumlah2) "+
                        " from hibah_obat_bhp inner join detailhibah_obat_bhp "+
                        " on hibah_obat_bhp.no_hibah=detailhibah_obat_bhp.no_hibah "+
                        " where detailhibah_obat_bhp.kode_brng=? and hibah_obat_bhp.tgl_hibah "+
                        " between ? and ? ");
                    try {
                        ps2.setString(1,rs.getString(1));
                        ps2.setString(2,tglopname);
                        ps2.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                        rs2=ps2.executeQuery();
                        if(rs2.next()){                    
                            jumlahhibah=rs2.getDouble(1);
                        }
                    } catch (Exception e) {
                        System.out.println("Note : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }

                    ps2=koneksi.prepareStatement("select sum(opname.real) from opname where kode_brng=? and tanggal=?");
                    try {
                        ps2.setString(1,rs.getString(1));
                        ps2.setString(2,tglopname);
                        rs2=ps2.executeQuery();
                        if(rs2.next()){
                            stokawal=rs2.getDouble(1);
                        }
                    } catch (Exception e) {
                        System.out.println("Note : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }                        

                    if((jumlahbeli>0)||(jumlahpesan>0)||(jumlahjual>0)||(jumlahpasin>0)||(jumlahpiutang>0)||(jumlahhibah>0)||
                            (jumlahutd>0)||(jumlahkeluar>0)||(jumlahretbeli>0)||(jumlahretjual>0)||(jumlahretpiut>0)||(stok>0)||(jumlahrespulang>0)){
                        if(stokawal<=0){
                            stokawal=stok-jumlahbeli-jumlahpesan-jumlahmutasimasuk+jumlahmutasikeluar+jumlahjual+jumlahpasin+jumlahpiutang-jumlahhibah+jumlahretbeli-jumlahretjual-jumlahretpiut+jumlahutd+jumlahkeluar+jumlahrespulang;
                            stokakhir=stok;
                        }else{
                            stokakhir=stokawal+jumlahbeli+jumlahpesan+jumlahmutasimasuk-jumlahmutasikeluar-jumlahjual-jumlahpasin-jumlahpiutang+jumlahhibah-jumlahretbeli+jumlahretjual+jumlahretpiut-jumlahutd-jumlahkeluar-jumlahrespulang;
                        }
                        tabMode.addRow(new Object[]{
                            rs.getString(1),rs.getString(2),rs.getString(3),tglopname,Valid.SetAngka(stokawal),
                            Valid.SetAngka(jumlahbeli),Valid.SetAngka(jumlahpesan),Valid.SetAngka(jumlahjual),
                            Valid.SetAngka(jumlahpasin),Valid.SetAngka(jumlahpiutang),Valid.SetAngka(jumlahretbeli),
                            Valid.SetAngka(jumlahretjual),Valid.SetAngka(jumlahretpiut),Valid.SetAngka(jumlahutd),
                            Valid.SetAngka(jumlahkeluar),Valid.SetAngka(jumlahrespulang),Valid.SetAngka(jumlahmutasimasuk),
                            Valid.SetAngka(jumlahmutasikeluar),Valid.SetAngka(jumlahhibah),Valid.SetAngka(stokakhir)
                        }); 
                    }

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
        }catch(SQLException e){
            System.out.println("Notifikasi : "+e);
        }
        
    }
    
    private void prosesCari2(String lokasi) {
       this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
       Valid.tabelKosong(tabMode);      
       try{   
            ps=koneksi.prepareStatement(
                    "select databarang.kode_brng,databarang.nama_brng,kodesatuan.satuan "+
                    "from databarang inner join kodesatuan on databarang.kode_sat=kodesatuan.kode_sat "+
                    "inner join jenis on databarang.kdjns=jenis.kdjns "+
                    "inner join golongan_barang on databarang.kode_golongan=golongan_barang.kode "+
                    "inner join kategori_barang on databarang.kode_kategori=kategori_barang.kode where "+
                    "jenis.nama like ? and kategori_barang.nama like ? and golongan_barang.nama like ? and "+
                    "(databarang.kode_brng like ? or databarang.nama_brng like ?) order by databarang.kode_brng");
            try {
                ps.setString(1,"%"+nmjns.getText().trim()+"%");
                ps.setString(2,"%"+nmkategori.getText().trim()+"%");
                ps.setString(3,"%"+nmgolongan.getText().trim()+"%");
                ps.setString(4,"%"+TCari.getText().trim()+"%");
                ps.setString(5,"%"+TCari.getText().trim()+"%");
                rs=ps.executeQuery();   
                
                if(aktifkanbatch.equals("yes")){
                    qrystok="select sum(stok),(sum(stok)*"+hppfarmasi+") as aset "+
                            "from gudangbarang inner join databarang on gudangbarang.kode_brng=databarang.kode_brng "+
                            "where gudangbarang.kode_brng=? and gudangbarang.kd_bangsal=? and gudangbarang.no_batch<>'' and gudangbarang.no_faktur<>''";
                }else{
                    qrystok="select sum(stok),(sum(stok)*"+hppfarmasi+") as aset "+
                            "from gudangbarang inner join databarang on gudangbarang.kode_brng=databarang.kode_brng "+
                            "where gudangbarang.kode_brng=? and gudangbarang.kd_bangsal=? and gudangbarang.no_batch='' and gudangbarang.no_faktur=''";
                }
                
                while(rs.next()){
                    jumlahjual=0;jumlahbeli=0;jumlahpiutang=0;jumlahpesan=0;jumlahretbeli=0;
                    jumlahretjual=0;jumlahretpiut=0;jumlahpasin=0;stok=0;stokawal=0;jumlahutd=0;
                    jumlahkeluar=0;jumlahrespulang=0;jumlahmutasimasuk=0;jumlahmutasikeluar=0;
                    jumlahhibah=0;tglopname="0000-00-00";
                    tglopname=Sequel.cariIsi("select tanggal from opname where tanggal between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and kode_brng='"+rs.getString(1)+"' and kd_bangsal='"+lokasi+"'  order by tanggal asc limit 1");

                    if(tglopname.equals("")){
                        tglopname=Valid.SetTgl(Tgl1.getSelectedItem()+"");
                    }

                    ps2=koneksi.prepareStatement(qrystok);
                    try {
                        ps2.setString(1,rs.getString(1));
                        ps2.setString(2,lokasi);
                        rs2=ps2.executeQuery();
                        if(rs2.next()){
                            stok=rs2.getDouble(1);
                        } 
                    } catch (Exception e) {
                        System.out.println("Note : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                                       
                    //pembelian 
                    ps2=koneksi.prepareStatement("select sum(detailbeli.jumlah2) "+
                        " from pembelian inner join detailbeli "+
                        " on pembelian.no_faktur=detailbeli.no_faktur "+
                        " where detailbeli.kode_brng=? and pembelian.tgl_beli "+
                        " between ? and ? and pembelian.kd_bangsal=?");
                    try {
                        ps2.setString(1,rs.getString(1));
                        ps2.setString(2,tglopname);
                        ps2.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                        ps2.setString(4,lokasi);
                        rs2=ps2.executeQuery();
                        if(rs2.next()){                    
                            jumlahbeli=rs2.getDouble(1);
                        }
                    } catch (Exception e) {
                        System.out.println("Note : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                        
                    //pemesanan
                    ps2=koneksi.prepareStatement("select sum(detailpesan.jumlah2) "+
                        " from pemesanan inner join detailpesan "+
                        " on pemesanan.no_faktur=detailpesan.no_faktur "+
                        " where detailpesan.kode_brng=? and pemesanan.tgl_pesan "+
                        " between ? and ? and pemesanan.kd_bangsal=?");            
                    try {
                        ps2.setString(1,rs.getString(1));
                        ps2.setString(2,tglopname);
                        ps2.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                        ps2.setString(4,lokasi);
                        rs2=ps2.executeQuery();
                        if(rs2.next()){                    
                            jumlahpesan=rs2.getDouble(1);
                        }
                    } catch (Exception e) {
                        System.out.println("Note : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                        
                    //penjualan
                    ps2=koneksi.prepareStatement("select sum(detailjual.jumlah) "+
                        " from penjualan inner join detailjual "+
                        " on penjualan.nota_jual=detailjual.nota_jual "+
                        " where detailjual.kode_brng=? and "+
                        " penjualan.tgl_jual  between ? and ? and penjualan.kd_bangsal=?");
                    try {
                        ps2.setString(1,rs.getString(1));
                        ps2.setString(2,tglopname);
                        ps2.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                        ps2.setString(4,lokasi);
                        rs2=ps2.executeQuery();
                        if(rs2.next()){                    
                            jumlahjual=rs2.getDouble(1);
                        }
                    } catch (Exception e) {
                        System.out.println("Note : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }                        

                    //piutang 
                    ps2=koneksi.prepareStatement("select sum(detailpiutang.jumlah) "+
                        " from piutang inner join detailpiutang "+
                        " on piutang.nota_piutang=detailpiutang.nota_piutang "+
                        " where detailpiutang.kode_brng=? and "+
                        " piutang.tgl_piutang between ? and ? and piutang.kd_bangsal=?");            
                    try {
                        ps2.setString(1,rs.getString(1));
                        ps2.setString(2,tglopname);
                        ps2.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                        ps2.setString(4,lokasi);
                        rs2=ps2.executeQuery();
                        if(rs2.next()){                    
                            jumlahpiutang=rs2.getDouble(1);
                        }
                    } catch (Exception e) {
                        System.out.println("Note : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }                        

                    //returbeli
                    ps2=koneksi.prepareStatement("select sum(detreturbeli.jml_retur2) "+
                        " from returbeli inner join detreturbeli "+
                        " on returbeli.no_retur_beli=detreturbeli.no_retur_beli "+
                        " where detreturbeli.kode_brng=? and "+
                        " returbeli.tgl_retur between ? and ? and returbeli.kd_bangsal=?");            
                    try {
                        ps2.setString(1,rs.getString(1));
                        ps2.setString(2,tglopname);
                        ps2.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                        ps2.setString(4,lokasi);
                        rs2=ps2.executeQuery();
                        if(rs2.next()){                    
                            jumlahretbeli=rs2.getDouble(1);
                        }
                    } catch (Exception e) {
                        System.out.println("Note : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                        

                    //returjual
                    ps2=koneksi.prepareStatement("select sum(detreturjual.jml_retur) "+
                        " from returjual inner join detreturjual "+
                        " on returjual.no_retur_jual=detreturjual.no_retur_jual "+
                        " where detreturjual.kode_brng=? and "+
                        " returjual.tgl_retur between ? and ? and returjual.kd_bangsal=?");            
                    try {
                        ps2.setString(1,rs.getString(1));
                        ps2.setString(2,tglopname);
                        ps2.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                        ps2.setString(4,lokasi);
                        rs2=ps2.executeQuery();
                        if(rs2.next()){                    
                            jumlahretjual=rs2.getDouble(1);
                        } 
                    } catch (Exception e) {
                        System.out.println("Note : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                                       
                    ps2=koneksi.prepareStatement("select sum(detreturpiutang.jml_retur) "+
                        " from returpiutang inner join detreturpiutang "+
                        " on returpiutang.no_retur_piutang=detreturpiutang.no_retur_piutang "+
                        " where detreturpiutang.kode_brng=? and "+
                        " returpiutang.tgl_retur between ? and ? and returpiutang.kd_bangsal=?");            
                    try {
                        ps2.setString(1,rs.getString(1));
                        ps2.setString(2,tglopname);
                        ps2.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                        ps2.setString(4,lokasi);
                        rs2=ps2.executeQuery();
                        if(rs2.next()){                    
                            jumlahretpiut=rs2.getDouble(1);
                        }
                    } catch (Exception e) {
                        System.out.println("Note : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }  
                    
                    ps2=koneksi.prepareStatement("select sum(detail_pemberian_obat.jml) as jumlah "+
                        " from detail_pemberian_obat where detail_pemberian_obat.kode_brng=? and "+
                        " detail_pemberian_obat.tgl_perawatan between ? and ? and detail_pemberian_obat.kd_bangsal=?");            
                    try {
                        ps2.setString(1,rs.getString(1));
                        ps2.setString(2,tglopname);
                        ps2.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                        ps2.setString(4,lokasi);
                        rs2=ps2.executeQuery();
                        if(rs2.next()){                    
                            jumlahpasin=rs2.getDouble(1);
                        }
                    } catch (Exception e) {
                        System.out.println("Note : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                        
                    ps2=koneksi.prepareStatement("select sum(utd_pengambilan_medis.jml) as jumlah "+
                        " from utd_pengambilan_medis where utd_pengambilan_medis.kode_brng=? and "+
                        " utd_pengambilan_medis.tanggal between ? and ? and utd_pengambilan_medis.kd_bangsal_dr=?");            
                    try {
                        ps2.setString(1,rs.getString(1));
                        ps2.setString(2,tglopname);
                        ps2.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                        ps2.setString(4,lokasi);
                        rs2=ps2.executeQuery();
                        if(rs2.next()){                    
                            jumlahutd=rs2.getDouble(1);
                        }
                    } catch (Exception e) {
                        System.out.println("Note : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }

                    ps2=koneksi.prepareStatement("select sum(detail_pengeluaran_obat_bhp.jumlah) "+
                        " from pengeluaran_obat_bhp inner join detail_pengeluaran_obat_bhp "+
                        " on pengeluaran_obat_bhp.no_keluar=detail_pengeluaran_obat_bhp.no_keluar "+
                        " where detail_pengeluaran_obat_bhp.kode_brng=? and pengeluaran_obat_bhp.tanggal "+
                        " between ? and ? and pengeluaran_obat_bhp.kd_bangsal=?");
                    try {
                        ps2.setString(1,rs.getString(1));
                        ps2.setString(2,tglopname);
                        ps2.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                        ps2.setString(4,lokasi);
                        rs2=ps2.executeQuery();
                        if(rs2.next()){                    
                            jumlahkeluar=rs2.getDouble(1);
                        }
                    } catch (Exception e) {
                        System.out.println("Note : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }                        

                    ps2=koneksi.prepareStatement("select sum(resep_pulang.jml_barang), sum(resep_pulang.total) "+
                        " from resep_pulang where resep_pulang.kode_brng=? and "+
                        " resep_pulang.tanggal between ? and ? and resep_pulang.kd_bangsal=?");
                    try {
                        ps2.setString(1,rs.getString(1));
                        ps2.setString(2,tglopname);
                        ps2.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                        ps2.setString(4,lokasi);
                        rs2=ps2.executeQuery();
                        if(rs2.next()){                    
                            jumlahrespulang=rs2.getDouble(1);
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikas Resep Pulang : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                    
                    ps2=koneksi.prepareStatement("select sum(jml), sum(jml*harga) "+
                        " from mutasibarang where kode_brng=? and "+
                        " tanggal between ? and ? and kd_bangsalke=?");
                    try {
                        ps2.setString(1,rs.getString(1));
                        ps2.setString(2,tglopname+" 00:00:00");
                        ps2.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                        ps2.setString(4,lokasi);
                        rs2=ps2.executeQuery();
                        if(rs2.next()){                    
                            jumlahmutasimasuk=rs2.getDouble(1);
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikas Mutasi Masuk : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                    
                    ps2=koneksi.prepareStatement("select sum(jml), sum(jml*harga) "+
                        " from mutasibarang where kode_brng=? and "+
                        " tanggal between ? and ? and kd_bangsaldari=?");
                    try {
                        ps2.setString(1,rs.getString(1));
                        ps2.setString(2,tglopname+" 00:00:00");
                        ps2.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                        ps2.setString(4,lokasi);
                        rs2=ps2.executeQuery();
                        if(rs2.next()){                    
                            jumlahmutasikeluar=rs2.getDouble(1);
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikas Mutasi Keluar : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                    
                    //hibah
                    ps2=koneksi.prepareStatement("select sum(detailhibah_obat_bhp.jumlah2) "+
                        " from hibah_obat_bhp inner join detailhibah_obat_bhp "+
                        " on hibah_obat_bhp.no_hibah=detailhibah_obat_bhp.no_hibah "+
                        " where detailhibah_obat_bhp.kode_brng=? and hibah_obat_bhp.tgl_hibah "+
                        " between ? and ? and hibah_obat_bhp.kd_bangsal=?");
                    try {
                        ps2.setString(1,rs.getString(1));
                        ps2.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                        ps2.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                        ps2.setString(4,lokasi);
                        rs2=ps2.executeQuery();
                        if(rs2.next()){                    
                            jumlahhibah=rs2.getDouble(1);
                        }
                    } catch (Exception e) {
                        System.out.println("Note : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                    
                    ps2=koneksi.prepareStatement("select sum(opname.real) from opname where kode_brng=? and tanggal=? and kd_bangsal=?");
                    try {
                        ps2.setString(1,rs.getString(1));
                        ps2.setString(2,tglopname);
                        ps2.setString(3,lokasi);
                        rs2=ps2.executeQuery();
                        if(rs2.next()){
                            stokawal=rs2.getDouble(1);
                        }
                    } catch (Exception e) {
                        System.out.println("Note : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }                        

                    if((jumlahbeli>0)||(jumlahpesan>0)||(jumlahjual>0)||(jumlahpasin>0)||(jumlahpiutang>0)||(jumlahhibah>0)||(jumlahmutasimasuk>0)||(jumlahmutasikeluar>0)||
                            (jumlahutd>0)||(jumlahkeluar>0)||(jumlahretbeli>0)||(jumlahretjual>0)||(jumlahretpiut>0)||(stok>0)||(jumlahrespulang>0)){
                        if(stokawal<=0){
                            stokawal=stok-jumlahbeli-jumlahpesan-jumlahmutasimasuk+jumlahmutasikeluar+jumlahjual+jumlahpasin+jumlahpiutang-jumlahhibah+jumlahretbeli-jumlahretjual-jumlahretpiut+jumlahutd+jumlahkeluar+jumlahrespulang;
                            stokakhir=stok;
                        }else{
                            stokakhir=stokawal+jumlahbeli+jumlahpesan+jumlahmutasimasuk-jumlahmutasikeluar-jumlahjual-jumlahpasin-jumlahpiutang+jumlahhibah-jumlahretbeli+jumlahretjual+jumlahretpiut-jumlahutd-jumlahkeluar-jumlahrespulang;
                        }
                        tabMode.addRow(new Object[]{
                            rs.getString(1),rs.getString(2),rs.getString(3),tglopname,Valid.SetAngka(stokawal),
                            Valid.SetAngka(jumlahbeli),Valid.SetAngka(jumlahpesan),Valid.SetAngka(jumlahjual),
                            Valid.SetAngka(jumlahpasin),Valid.SetAngka(jumlahpiutang),Valid.SetAngka(jumlahretbeli),
                            Valid.SetAngka(jumlahretjual),Valid.SetAngka(jumlahretpiut),Valid.SetAngka(jumlahutd),
                            Valid.SetAngka(jumlahkeluar),Valid.SetAngka(jumlahrespulang),Valid.SetAngka(jumlahmutasimasuk),
                            Valid.SetAngka(jumlahmutasikeluar),Valid.SetAngka(jumlahhibah),Valid.SetAngka(stokakhir)
                        }); 
                    }

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
        }catch(SQLException e){
            System.out.println("Notifikasi : "+e);
        }
        this.setCursor(Cursor.getDefaultCursor());
    }
    
    public void isCek(){
         BtnPrint.setEnabled(akses.getsirkulasi_obat2());
    }
    
}

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
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import simrskhanza.DlgCariBangsal;

public class DlgSirkulasiBarang6 extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private double jumlahjual=0,jumlahbeli=0,jumlahpesan=0,jumlahpiutang=0,jumlahutd=0,jumlahkeluar=0,jumlahmutasikeluar=0,
                   jumlahmutasimasuk=0,jumlahretbeli=0,jumlahretjual=0,jumlahretpiut=0,jumlahpasin=0,jumlahrespulang=0,
                   jumlahhibah=0,stokawal=0,stokakhir=0,totaljual=0,totalbeli=0,totalpesan=0,totalpiutang=0,
                   totalutd=0,totalkeluar=0,totalmutasikeluar=0,totalmutasimasuk=0,totalretbeli=0,totalretjual=0,
                   totalretpiut=0,totalpasin=0,totalrespulang=0,totalhibah=0,totalstokawal=0,totalstokakhir=0,
                   ttltotaljual=0,ttltotalbeli=0,ttltotalpesan=0,ttltotalpiutang=0,ttltotalutd=0,ttltotalkeluar=0,ttltotalmutasikeluar=0,
                   ttltotalmutasimasuk=0,ttltotalretbeli=0,ttltotalretjual=0,ttltotalretpiut=0,ttltotalpasin=0,ttltotalrespulang=0,
                   ttltotalhibah=0,ttltotalstokawal=0,ttltotalstokakhir=0,harga=0;
    private PreparedStatement ps,ps2,ps3;
    private ResultSet rs,rs2,rs3;
    private String tglopname="",hppfarmasi="",pilihan="",tarifdasar="tidak",tampilmin="tidak";
    private StringBuilder htmlContent;
    private int i=0;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private volatile boolean ceksukses = false;

    /** 
     * @param parent
     * @param modal */
    public DlgSirkulasiBarang6(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        tabMode=new DefaultTableModel(null,new Object[]{
              "Kode Barang","No.Batch","No.Faktur","Nama Barang","Satuan","Tgl.Awal","Stok Awal","Stok Awal(Rp)","Pengadaan","Pengadaan(Rp)",
              "Penerimaan","Penerimaan(Rp)","Penjualan","Penjualan(Rp)","Ke Pasien","Ke Pasien(Rp)","Piutang Jual","Piutang Jual(Rp)",
              "Retur Beli","Retur Beli(Rp)","Retur Jual","Retur Jual(Rp)","Retur Piutang","Retur Piutang(Rp)",
              "Pengambilan UTD","Pengambilan UTD(Rp)","Stok Keluar Medis","Stok Keluar Medis(Rp)","Resep Pulang","Resep Pulang(Rp)",
              "Mutasi Masuk","Mutasi Masuk(Rp)","Mutasi Keluar","Mutasi Keluar(Rp)","Hibah","Hibah(Rp)","Stok Akhir","Stok Akhir(Rp)"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbDokter.setModel(tabMode);

        tbDokter.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 38; i++) {
            TableColumn column = tbDokter.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(100);
            }else if(i==1){
                column.setPreferredWidth(80);
            }else if(i==2){
                column.setPreferredWidth(110);
            }else if(i==3){
                column.setPreferredWidth(200);
            }else if(i==4){
                column.setPreferredWidth(60);
            }else if(i==5){
                column.setPreferredWidth(110);
            }else if(i==6){
                column.setPreferredWidth(70);
            }else if(i==7){
                column.setPreferredWidth(100);
            }else if(i==8){
                column.setPreferredWidth(70);
            }else if(i==9){
                column.setPreferredWidth(100);
            }else if(i==10){
                column.setPreferredWidth(70);
            }else if(i==11){
                column.setPreferredWidth(100);
            }else if(i==12){
                column.setPreferredWidth(70);
            }else if(i==13){
                column.setPreferredWidth(100);
            }else if(i==14){
                column.setPreferredWidth(70);
            }else if(i==15){
                column.setPreferredWidth(100);
            }else if(i==16){
                column.setPreferredWidth(70);
            }else if(i==17){
                column.setPreferredWidth(100);
            }else if(i==18){
                column.setPreferredWidth(70);
            }else if(i==19){
                column.setPreferredWidth(100);
            }else if(i==20){
                column.setPreferredWidth(70);
            }else if(i==21){
                column.setPreferredWidth(100);
            }else if(i==22){
                column.setPreferredWidth(70);
            }else if(i==23){
                column.setPreferredWidth(100);
            }else if(i==24){
                column.setPreferredWidth(70);
            }else if(i==25){
                column.setPreferredWidth(100);
            }else if(i==26){
                column.setPreferredWidth(70);
            }else if(i==27){
                column.setPreferredWidth(100);
            }else if(i==28){
                column.setPreferredWidth(70);
            }else if(i==29){
                column.setPreferredWidth(100);
            }else if(i==30){
                column.setPreferredWidth(70);
            }else if(i==31){
                column.setPreferredWidth(100);
            }else if(i==32){
                column.setPreferredWidth(70);
            }else if(i==33){
                column.setPreferredWidth(100);
            }else if(i==34){
                column.setPreferredWidth(70);
            }else if(i==35){
                column.setPreferredWidth(100);
            }else if(i==36){
                column.setPreferredWidth(70);
            }else if(i==37){
                column.setPreferredWidth(100);
            }
        }
        tbDokter.setDefaultRenderer(Object.class, new WarnaTable());         
        
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        if(koneksiDB.CARICEPAT().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        runBackground(() ->prosesCari());
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        runBackground(() ->prosesCari());
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        runBackground(() ->prosesCari());
                    }
                }
            });
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
        ppTarifDasar = new javax.swing.JMenuItem();
        ppTarifTransaksi = new javax.swing.JMenuItem();
        ppTampilkanMinTransaksi = new javax.swing.JMenuItem();
        ppSembunyikanMinTransaksi = new javax.swing.JMenuItem();
        KdGudang = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        scrollPane1 = new widget.ScrollPane();
        tbDokter = new widget.Table();
        panelisi1 = new widget.panelisi();
        label19 = new widget.Label();
        NmGudang = new widget.TextBox();
        btnBarang1 = new widget.Button();
        label10 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        label9 = new widget.Label();
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

        ppTarifDasar.setBackground(new java.awt.Color(255, 255, 254));
        ppTarifDasar.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppTarifDasar.setForeground(new java.awt.Color(50, 50, 50));
        ppTarifDasar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppTarifDasar.setText("Gunakan Harga Dasar");
        ppTarifDasar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppTarifDasar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppTarifDasar.setName("ppTarifDasar"); // NOI18N
        ppTarifDasar.setPreferredSize(new java.awt.Dimension(300, 25));
        ppTarifDasar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppTarifDasarActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppTarifDasar);

        ppTarifTransaksi.setBackground(new java.awt.Color(255, 255, 254));
        ppTarifTransaksi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppTarifTransaksi.setForeground(new java.awt.Color(50, 50, 50));
        ppTarifTransaksi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppTarifTransaksi.setText("Gunakan Harga Sesuai Transaksi");
        ppTarifTransaksi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppTarifTransaksi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppTarifTransaksi.setName("ppTarifTransaksi"); // NOI18N
        ppTarifTransaksi.setPreferredSize(new java.awt.Dimension(300, 25));
        ppTarifTransaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppTarifTransaksiActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppTarifTransaksi);

        ppTampilkanMinTransaksi.setBackground(new java.awt.Color(255, 255, 254));
        ppTampilkanMinTransaksi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppTampilkanMinTransaksi.setForeground(new java.awt.Color(50, 50, 50));
        ppTampilkanMinTransaksi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppTampilkanMinTransaksi.setText("Tampilkan Transaksi Yang Bernilai Min (-)");
        ppTampilkanMinTransaksi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppTampilkanMinTransaksi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppTampilkanMinTransaksi.setName("ppTampilkanMinTransaksi"); // NOI18N
        ppTampilkanMinTransaksi.setPreferredSize(new java.awt.Dimension(300, 25));
        ppTampilkanMinTransaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppTampilkanMinTransaksiActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppTampilkanMinTransaksi);

        ppSembunyikanMinTransaksi.setBackground(new java.awt.Color(255, 255, 254));
        ppSembunyikanMinTransaksi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppSembunyikanMinTransaksi.setForeground(new java.awt.Color(50, 50, 50));
        ppSembunyikanMinTransaksi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppSembunyikanMinTransaksi.setText("Sembunyikan Transaksi Yang Bernilai Min (-)");
        ppSembunyikanMinTransaksi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppSembunyikanMinTransaksi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppSembunyikanMinTransaksi.setName("ppSembunyikanMinTransaksi"); // NOI18N
        ppSembunyikanMinTransaksi.setPreferredSize(new java.awt.Dimension(300, 25));
        ppSembunyikanMinTransaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppSembunyikanMinTransaksiActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppSembunyikanMinTransaksi);

        KdGudang.setName("KdGudang"); // NOI18N
        KdGudang.setPreferredSize(new java.awt.Dimension(80, 23));
        KdGudang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdGudangKeyPressed(evt);
            }
        });

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

        scrollPane1.setToolTipText("<html>Stok Awal mengambil data dari Stok pada riwayat obat/alkes/bhp pada tanggal terdekat pencarian di tanggal pertama. \n<br>Sirkulasi 6 ini ditujukan untuk tracking seluruh data berdasarkan nomor faktur dan nomor batch.</html>");
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
        tbDokter.setToolTipText("<html>Stok Awal mengambil data dari Stok pada riwayat obat/alkes/bhp pada tanggal terdekat pencarian di tanggal pertama. \n<br>Sirkulasi 6 ini ditujukan untuk tracking seluruh data berdasarkan nomor faktur dan nomor batch.</html>");
        tbDokter.setComponentPopupMenu(jPopupMenu1);
        tbDokter.setName("tbDokter"); // NOI18N
        scrollPane1.setViewportView(tbDokter);

        internalFrame1.add(scrollPane1, java.awt.BorderLayout.CENTER);

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 56));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label19.setText("Lokasi :");
        label19.setName("label19"); // NOI18N
        label19.setPreferredSize(new java.awt.Dimension(45, 23));
        panelisi1.add(label19);

        NmGudang.setEditable(false);
        NmGudang.setName("NmGudang"); // NOI18N
        NmGudang.setPreferredSize(new java.awt.Dimension(150, 23));
        panelisi1.add(NmGudang);

        btnBarang1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnBarang1.setMnemonic('1');
        btnBarang1.setToolTipText("Alt+1");
        btnBarang1.setName("btnBarang1"); // NOI18N
        btnBarang1.setPreferredSize(new java.awt.Dimension(28, 23));
        btnBarang1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBarang1ActionPerformed(evt);
            }
        });
        panelisi1.add(btnBarang1);

        label10.setText("Key Word :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(69, 23));
        panelisi1.add(label10);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(150, 23));
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

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('A');
        BtnAll.setToolTipText("Alt+A");
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

        label9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(15, 30));
        panelisi1.add(label9);

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
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            try {            
                File g = new File("file2.css");            
                BufferedWriter bg = new BufferedWriter(new FileWriter(g));
                bg.write(
                        ".isi td{border-right: 1px solid #e2e7dd;font: 11px tahoma;height:12px;border-bottom: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                        ".isi2 td{font: 11px tahoma;height:12px;background: #ffffff;color:#323232;}"+                    
                        ".isi3 td{border-right: 1px solid #e2e7dd;font: 11px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                        ".isi4 td{font: 11px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"
                );
                bg.close();

                File f;            
                BufferedWriter bw; 

                pilihan = (String)JOptionPane.showInputDialog(null,"Silahkan pilih laporan..!","Pilihan Cetak",JOptionPane.QUESTION_MESSAGE,null,new Object[]{"Laporan 1 (HTML)","Laporan 2 (WPS)","Laporan 3 (CSV)"},"Laporan 1 (HTML)");
                switch (pilihan) {
                    case "Laporan 1 (HTML)":
                            htmlContent = new StringBuilder();
                            htmlContent.append(                             
                                "<tr class='isi'>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Kode Barang</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>No.Batch</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>No.Faktur</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Nama Barang</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Satuan</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Tgl.Awal</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Stok Awal</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Stok Awal(Rp)</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Pengadaan</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Pengadaan(Rp)</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Penerimaan</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Penerimaan(Rp)</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Penjualan</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Penjualan(Rp)</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Ke Pasien</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Ke Pasien(Rp)</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Piutang Jual</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Piutang Jual(Rp)</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Retur Beli</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Retur Beli(Rp)</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Retur Jual</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Retur Jual(Rp)</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Retur Piutang</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Retur Piutang(Rp)</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Pengambilan UTD</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Pengambilan UTD(Rp)</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Stok Keluar Medis</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Stok Keluar Medis(Rp)</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Resep Pulang</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Resep Pulang(Rp)</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Mutasi Masuk</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Mutasi Masuk(Rp)</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Mutasi Keluar</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Mutasi Keluar(Rp)</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Hibah</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Hibah(Rp)</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Stok Akhir</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Stok Akhir(Rp)</td>"+
                                "</tr>"
                            ); 
                            for(i=0;i<tabMode.getRowCount();i++){  
                                htmlContent.append(                             
                                    "<tr class='isi'>"+
                                        "<td valign='top'>"+tabMode.getValueAt(i,0)+"</td>"+
                                        "<td valign='top'>"+tabMode.getValueAt(i,1)+"</td>"+
                                        "<td valign='top'>"+tabMode.getValueAt(i,2)+"</td>"+
                                        "<td valign='top'>"+tabMode.getValueAt(i,3)+"</td>"+
                                        "<td valign='top'>"+tabMode.getValueAt(i,4)+"</td>"+
                                        "<td valign='top'>"+tabMode.getValueAt(i,5)+"</td>"+
                                        "<td valign='top' align='right'>"+tabMode.getValueAt(i,6)+"</td>"+
                                        "<td valign='top' align='right'>"+tabMode.getValueAt(i,7)+"</td>"+
                                        "<td valign='top' align='right'>"+tabMode.getValueAt(i,8)+"</td>"+
                                        "<td valign='top' align='right'>"+tabMode.getValueAt(i,9)+"</td>"+
                                        "<td valign='top' align='right'>"+tabMode.getValueAt(i,10)+"</td>"+
                                        "<td valign='top' align='right'>"+tabMode.getValueAt(i,11)+"</td>"+
                                        "<td valign='top' align='right'>"+tabMode.getValueAt(i,12)+"</td>"+
                                        "<td valign='top' align='right'>"+tabMode.getValueAt(i,13)+"</td>"+
                                        "<td valign='top' align='right'>"+tabMode.getValueAt(i,14)+"</td>"+
                                        "<td valign='top' align='right'>"+tabMode.getValueAt(i,15)+"</td>"+
                                        "<td valign='top' align='right'>"+tabMode.getValueAt(i,16)+"</td>"+
                                        "<td valign='top' align='right'>"+tabMode.getValueAt(i,17)+"</td>"+
                                        "<td valign='top' align='right'>"+tabMode.getValueAt(i,18)+"</td>"+
                                        "<td valign='top' align='right'>"+tabMode.getValueAt(i,19)+"</td>"+
                                        "<td valign='top' align='right'>"+tabMode.getValueAt(i,20)+"</td>"+
                                        "<td valign='top' align='right'>"+tabMode.getValueAt(i,21)+"</td>"+
                                        "<td valign='top' align='right'>"+tabMode.getValueAt(i,22)+"</td>"+
                                        "<td valign='top' align='right'>"+tabMode.getValueAt(i,23)+"</td>"+
                                        "<td valign='top' align='right'>"+tabMode.getValueAt(i,24)+"</td>"+
                                        "<td valign='top' align='right'>"+tabMode.getValueAt(i,25)+"</td>"+
                                        "<td valign='top' align='right'>"+tabMode.getValueAt(i,26)+"</td>"+
                                        "<td valign='top' align='right'>"+tabMode.getValueAt(i,27)+"</td>"+
                                        "<td valign='top' align='right'>"+tabMode.getValueAt(i,28)+"</td>"+
                                        "<td valign='top' align='right'>"+tabMode.getValueAt(i,29)+"</td>"+
                                        "<td valign='top' align='right'>"+tabMode.getValueAt(i,30)+"</td>"+
                                        "<td valign='top' align='right'>"+tabMode.getValueAt(i,31)+"</td>"+
                                        "<td valign='top' align='right'>"+tabMode.getValueAt(i,32)+"</td>"+
                                        "<td valign='top' align='right'>"+tabMode.getValueAt(i,33)+"</td>"+
                                        "<td valign='top' align='right'>"+tabMode.getValueAt(i,34)+"</td>"+
                                        "<td valign='top' align='right'>"+tabMode.getValueAt(i,35)+"</td>"+
                                        "<td valign='top' align='right'>"+tabMode.getValueAt(i,36)+"</td>"+
                                        "<td valign='top' align='right'>"+tabMode.getValueAt(i,37)+"</td>"+
                                    "</tr>"
                                ); 
                            }            

                            f = new File("SirkulasiObat.html");            
                            bw = new BufferedWriter(new FileWriter(f));            
                            bw.write("<html>"+
                                        "<head><link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" /></head>"+
                                        "<body>"+
                                            "<table width='3600px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                                "<tr class='isi2'>"+
                                                    "<td valign='top' align='center'>"+
                                                        "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                                        akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                                        akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                                        "<font size='2' face='Tahoma'>SIRKULASI OBAT/ALKES/BHP PERIODE "+Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem()+"<br><br></font>"+        
                                                    "</td>"+
                                               "</tr>"+
                                            "</table>"+
                                            "<table width='3600px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                                htmlContent.toString()+
                                            "</table>"+
                                        "</body>"+                   
                                     "</html>"
                            );
                            htmlContent=null;
                            bw.close();                         
                            Desktop.getDesktop().browse(f.toURI());
                        break;
                    case "Laporan 2 (WPS)":
                            htmlContent = new StringBuilder();
                            htmlContent.append(                             
                                "<tr class='isi'>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Kode Barang</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>No.Batch</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>No.Faktur</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Nama Barang</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Satuan</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Tgl.Awal</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Stok Awal</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Stok Awal(Rp)</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Pengadaan</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Pengadaan(Rp)</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Penerimaan</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Penerimaan(Rp)</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Penjualan</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Penjualan(Rp)</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Ke Pasien</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Ke Pasien(Rp)</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Piutang Jual</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Piutang Jual(Rp)</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Retur Beli</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Retur Beli(Rp)</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Retur Jual</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Retur Jual(Rp)</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Retur Piutang</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Retur Piutang(Rp)</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Pengambilan UTD</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Pengambilan UTD(Rp)</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Stok Keluar Medis</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Stok Keluar Medis(Rp)</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Resep Pulang</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Resep Pulang(Rp)</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Mutasi Masuk</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Mutasi Masuk(Rp)</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Mutasi Keluar</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Mutasi Keluar(Rp)</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Hibah</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Hibah(Rp)</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Stok Akhir</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Stok Akhir(Rp)</td>"+
                                "</tr>"
                            ); 
                            for(i=0;i<tabMode.getRowCount();i++){  
                                htmlContent.append(                             
                                    "<tr class='isi'>"+
                                        "<td valign='top'>"+tabMode.getValueAt(i,0)+"</td>"+
                                        "<td valign='top'>"+tabMode.getValueAt(i,1)+"</td>"+
                                        "<td valign='top'>"+tabMode.getValueAt(i,2)+"</td>"+
                                        "<td valign='top'>"+tabMode.getValueAt(i,3)+"</td>"+
                                        "<td valign='top'>"+tabMode.getValueAt(i,4)+"</td>"+
                                        "<td valign='top'>"+tabMode.getValueAt(i,5)+"</td>"+
                                        "<td valign='top' align='right'>"+tabMode.getValueAt(i,6)+"</td>"+
                                        "<td valign='top' align='right'>"+tabMode.getValueAt(i,7)+"</td>"+
                                        "<td valign='top' align='right'>"+tabMode.getValueAt(i,8)+"</td>"+
                                        "<td valign='top' align='right'>"+tabMode.getValueAt(i,9)+"</td>"+
                                        "<td valign='top' align='right'>"+tabMode.getValueAt(i,10)+"</td>"+
                                        "<td valign='top' align='right'>"+tabMode.getValueAt(i,11)+"</td>"+
                                        "<td valign='top' align='right'>"+tabMode.getValueAt(i,12)+"</td>"+
                                        "<td valign='top' align='right'>"+tabMode.getValueAt(i,13)+"</td>"+
                                        "<td valign='top' align='right'>"+tabMode.getValueAt(i,14)+"</td>"+
                                        "<td valign='top' align='right'>"+tabMode.getValueAt(i,15)+"</td>"+
                                        "<td valign='top' align='right'>"+tabMode.getValueAt(i,16)+"</td>"+
                                        "<td valign='top' align='right'>"+tabMode.getValueAt(i,17)+"</td>"+
                                        "<td valign='top' align='right'>"+tabMode.getValueAt(i,18)+"</td>"+
                                        "<td valign='top' align='right'>"+tabMode.getValueAt(i,19)+"</td>"+
                                        "<td valign='top' align='right'>"+tabMode.getValueAt(i,20)+"</td>"+
                                        "<td valign='top' align='right'>"+tabMode.getValueAt(i,21)+"</td>"+
                                        "<td valign='top' align='right'>"+tabMode.getValueAt(i,22)+"</td>"+
                                        "<td valign='top' align='right'>"+tabMode.getValueAt(i,23)+"</td>"+
                                        "<td valign='top' align='right'>"+tabMode.getValueAt(i,24)+"</td>"+
                                        "<td valign='top' align='right'>"+tabMode.getValueAt(i,25)+"</td>"+
                                        "<td valign='top' align='right'>"+tabMode.getValueAt(i,26)+"</td>"+
                                        "<td valign='top' align='right'>"+tabMode.getValueAt(i,27)+"</td>"+
                                        "<td valign='top' align='right'>"+tabMode.getValueAt(i,28)+"</td>"+
                                        "<td valign='top' align='right'>"+tabMode.getValueAt(i,29)+"</td>"+
                                        "<td valign='top' align='right'>"+tabMode.getValueAt(i,30)+"</td>"+
                                        "<td valign='top' align='right'>"+tabMode.getValueAt(i,31)+"</td>"+
                                        "<td valign='top' align='right'>"+tabMode.getValueAt(i,32)+"</td>"+
                                        "<td valign='top' align='right'>"+tabMode.getValueAt(i,33)+"</td>"+
                                        "<td valign='top' align='right'>"+tabMode.getValueAt(i,34)+"</td>"+
                                        "<td valign='top' align='right'>"+tabMode.getValueAt(i,35)+"</td>"+
                                        "<td valign='top' align='right'>"+tabMode.getValueAt(i,36)+"</td>"+
                                        "<td valign='top' align='right'>"+tabMode.getValueAt(i,37)+"</td>"+
                                    "</tr>"
                                ); 
                            }            

                            f = new File("SirkulasiObat.wps");            
                            bw = new BufferedWriter(new FileWriter(f));            
                            bw.write("<html>"+
                                        "<head><link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" /></head>"+
                                        "<body>"+
                                            "<table width='3600px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                                "<tr class='isi2'>"+
                                                    "<td valign='top' align='center'>"+
                                                        "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                                        akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                                        akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                                        "<font size='2' face='Tahoma'>DETAIL JM DOKTER PERIODE "+Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem()+"<br><br></font>"+        
                                                    "</td>"+
                                               "</tr>"+
                                            "</table>"+
                                            "<table width='3600px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                                htmlContent.toString()+
                                            "</table>"+
                                        "</body>"+                   
                                     "</html>"
                            );
                            htmlContent=null;
                            bw.close();                         
                            Desktop.getDesktop().browse(f.toURI());
                        break;
                    case "Laporan 3 (CSV)":
                            htmlContent = new StringBuilder();
                            htmlContent.append(                             
                                "\"Kode Barang\";\"No.Batch\";\"No.Faktur\";\"Nama Barang\";\"Satuan\";\"Tgl.Awal\";\"Stok Awal\";\"Stok Awal(Rp)\";\"Pengadaan\";\"Pengadaan(Rp)\";\"Penerimaan\";\"Penerimaan(Rp)\";\"Penjualan\";\"Penjualan(Rp)\";\"Ke Pasien\";\"Ke Pasien(Rp)\";\"Piutang Jual\";\"Piutang Jual(Rp)\";\"Retur Beli\";\"Retur Beli(Rp)\";\"Retur Jual\";\"Retur Jual(Rp)\";\"Retur Piutang\";\"Retur Piutang(Rp)\";\"Pengambilan UTD\";\"Pengambilan UTD(Rp)\";\"Stok Keluar Medis\";\"Stok Keluar Medis(Rp)\";\"Resep Pulang\";\"Resep Pulang(Rp)\";\"Mutasi Masuk\";\"Mutasi Masuk(Rp)\";\"Mutasi Keluar\";\"Mutasi Keluar(Rp)\";\"Hibah\";\"Hibah(Rp)\";\"Stok Akhir\";\"Stok Akhir(Rp)\"\n"
                            ); 
                            for(i=0;i<tabMode.getRowCount();i++){  
                                htmlContent.append(                             
                                    "\""+tabMode.getValueAt(i,0)+"\";\""+tabMode.getValueAt(i,1)+"\";\""+tabMode.getValueAt(i,2)+"\";\""+tabMode.getValueAt(i,3)+"\";\""+tabMode.getValueAt(i,4)+"\";\""+tabMode.getValueAt(i,5)+"\";\""+tabMode.getValueAt(i,6)+"\";\""+tabMode.getValueAt(i,7)+"\";\""+tabMode.getValueAt(i,8)+"\";\""+tabMode.getValueAt(i,9)+"\";\""+tabMode.getValueAt(i,10)+"\";\""+tabMode.getValueAt(i,11)+"\";\""+tabMode.getValueAt(i,12)+"\";\""+tabMode.getValueAt(i,13)+"\";\""+tabMode.getValueAt(i,14)+"\";\""+tabMode.getValueAt(i,15)+"\";\""+tabMode.getValueAt(i,16)+"\";\""+tabMode.getValueAt(i,17)+"\";\""+tabMode.getValueAt(i,18)+"\";\""+tabMode.getValueAt(i,19)+"\";\""+tabMode.getValueAt(i,20)+"\";\""+tabMode.getValueAt(i,21)+"\";\""+tabMode.getValueAt(i,22)+"\";\""+tabMode.getValueAt(i,23)+"\";\""+tabMode.getValueAt(i,24)+"\";\""+tabMode.getValueAt(i,25)+"\";\""+tabMode.getValueAt(i,26)+"\";\""+tabMode.getValueAt(i,27)+"\";\""+tabMode.getValueAt(i,28)+"\";\""+tabMode.getValueAt(i,29)+"\";\""+tabMode.getValueAt(i,30)+"\";\""+tabMode.getValueAt(i,31)+"\";\""+tabMode.getValueAt(i,32)+"\";\""+tabMode.getValueAt(i,33)+"\";\""+tabMode.getValueAt(i,34)+"\";\""+tabMode.getValueAt(i,35)+"\";\""+tabMode.getValueAt(i,36)+"\";\""+tabMode.getValueAt(i,37)+"\"\n"
                                ); 
                            }            

                            f = new File("SirkulasiObat.csv");            
                            bw = new BufferedWriter(new FileWriter(f));            
                            bw.write(htmlContent.toString());
                            htmlContent=null;
                            bw.close();                         
                            Desktop.getDesktop().browse(f.toURI());
                        break; 
                }                 
            } catch (Exception e) {
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
        runBackground(() ->prosesCari());
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
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
        runBackground(() ->prosesCari());
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

    private void BtnJenisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnJenisActionPerformed
        DlgCariJenis jenis = new DlgCariJenis(null, false);
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
        jenis.isCek();
        jenis.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        jenis.setLocationRelativeTo(internalFrame1);
        jenis.setAlwaysOnTop(false);
        jenis.setVisible(true);
    }//GEN-LAST:event_BtnJenisActionPerformed

    private void BtnKategoriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKategoriActionPerformed
        DlgCariKategori kategori = new DlgCariKategori(null, false);
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
        kategori.isCek();
        kategori.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        kategori.setLocationRelativeTo(internalFrame1);
        kategori.setAlwaysOnTop(false);
        kategori.setVisible(true);
    }//GEN-LAST:event_BtnKategoriActionPerformed

    private void BtnGolonganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnGolonganActionPerformed
        DlgCariGolongan golongan = new DlgCariGolongan(null, false);
        golongan.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}

            @Override
            public void windowClosing(WindowEvent e) {}

            @Override
            public void windowClosed(WindowEvent e) {
                if (golongan.getTable().getSelectedRow() != -1) {
                    nmgolongan.setText(golongan.getTable().getValueAt(golongan.getTable().getSelectedRow(), 1).toString());
                }
                
                TCari.requestFocus();
            }

            @Override
            public void windowIconified(WindowEvent e) {}

            @Override
            public void windowDeiconified(WindowEvent e) {}

            @Override
            public void windowActivated(WindowEvent e) {
                golongan.emptTeks();
            }

            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
        golongan.isCek();
        golongan.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        golongan.setLocationRelativeTo(internalFrame1);
        golongan.setAlwaysOnTop(false);
        golongan.setVisible(true);
    }//GEN-LAST:event_BtnGolonganActionPerformed

    private void KdGudangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdGudangKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KdGudangKeyPressed

    private void btnBarang1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBarang1ActionPerformed
        akses.setform("DlgSirkulasiBarang6");
        DlgCariBangsal bangsal = new DlgCariBangsal(null, false);
        bangsal.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}

            @Override
            public void windowClosing(WindowEvent e) {}

            @Override
            public void windowClosed(WindowEvent e) {
                if (bangsal.getTable().getSelectedRow() != -1) {
                    KdGudang.setText(bangsal.getTable().getValueAt(bangsal.getTable().getSelectedRow(),0).toString());
                    NmGudang.setText(bangsal.getTable().getValueAt(bangsal.getTable().getSelectedRow(),1).toString());
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
        bangsal.emptTeks();
        bangsal.isCek();
        bangsal.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        bangsal.setLocationRelativeTo(internalFrame1);
        bangsal.setAlwaysOnTop(false);
        bangsal.setVisible(true);
    }//GEN-LAST:event_btnBarang1ActionPerformed

    private void ppTarifDasarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppTarifDasarActionPerformed
        tarifdasar="ya";
        runBackground(() ->prosesCari());
    }//GEN-LAST:event_ppTarifDasarActionPerformed

    private void ppTarifTransaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppTarifTransaksiActionPerformed
        tarifdasar="tidak";
        runBackground(() ->prosesCari());
    }//GEN-LAST:event_ppTarifTransaksiActionPerformed

    private void ppTampilkanMinTransaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppTampilkanMinTransaksiActionPerformed
        tampilmin="ya";
        runBackground(() ->prosesCari());
    }//GEN-LAST:event_ppTampilkanMinTransaksiActionPerformed

    private void ppSembunyikanMinTransaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppSembunyikanMinTransaksiActionPerformed
        tampilmin="tidak";
        runBackground(() ->prosesCari());
    }//GEN-LAST:event_ppSembunyikanMinTransaksiActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgSirkulasiBarang6 dialog = new DlgSirkulasiBarang6(new javax.swing.JFrame(), true);
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
    private widget.TextBox KdGudang;
    private widget.TextBox NmGudang;
    private widget.TextBox TCari;
    private widget.Tanggal Tgl1;
    private widget.Tanggal Tgl2;
    private widget.Button btnBarang1;
    private widget.InternalFrame internalFrame1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.Label label10;
    private widget.Label label11;
    private widget.Label label18;
    private widget.Label label19;
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
    private javax.swing.JMenuItem ppSembunyikanMinTransaksi;
    private javax.swing.JMenuItem ppTampilkanMinTransaksi;
    private javax.swing.JMenuItem ppTarifDasar;
    private javax.swing.JMenuItem ppTarifTransaksi;
    private widget.ScrollPane scrollPane1;
    private widget.Table tbDokter;
    // End of variables declaration//GEN-END:variables

    private void prosesCari() {
        if(KdGudang.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Silahkan Pilih Lokasi...!");
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Valid.tabelKosong(tabMode);      
            try{   
                ttltotaljual=0;ttltotalbeli=0;ttltotalpesan=0;ttltotalpiutang=0;ttltotalutd=0;ttltotalkeluar=0;ttltotalmutasikeluar=0;
                ttltotalmutasimasuk=0;ttltotalretbeli=0;ttltotalretjual=0;ttltotalretpiut=0;ttltotalpasin=0;ttltotalrespulang=0;
                ttltotalhibah=0;ttltotalstokawal=0;ttltotalstokakhir=0;
                ps=koneksi.prepareStatement(
                    "select databarang.kode_brng,databarang.nama_brng,kodesatuan.satuan,databarang."+hppfarmasi+" as harga "+
                    "from databarang inner join kodesatuan on databarang.kode_sat=kodesatuan.kode_sat "+
                    "inner join jenis on databarang.kdjns=jenis.kdjns "+
                    "inner join golongan_barang on databarang.kode_golongan=golongan_barang.kode "+
                    "inner join kategori_barang on databarang.kode_kategori=kategori_barang.kode where "+
                    "jenis.nama like ? and kategori_barang.nama like ? and golongan_barang.nama like ? "+
                    (TCari.getText().trim().equals("")?"":"and (databarang.kode_brng like ? or databarang.nama_brng like ?)")+
                    "order by databarang.kode_brng");
                try {
                    ps.setString(1,"%"+nmjns.getText().trim()+"%");
                    ps.setString(2,"%"+nmkategori.getText().trim()+"%");
                    ps.setString(3,"%"+nmgolongan.getText().trim()+"%");
                    if(!TCari.getText().trim().equals("")){
                        ps.setString(4,"%"+TCari.getText().trim()+"%");
                        ps.setString(5,"%"+TCari.getText().trim()+"%");
                    }
                    rs=ps.executeQuery();
                    while(rs.next()){
                        ps2=koneksi.prepareStatement(
                            "select gudangbarang.no_batch,gudangbarang.no_faktur from gudangbarang where gudangbarang.kode_brng=? and gudangbarang.kd_bangsal=? order by gudangbarang.no_faktur,gudangbarang.no_batch");
                        try {
                            ps2.setString(1,rs.getString("kode_brng"));
                            ps2.setString(2,KdGudang.getText());
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                jumlahjual=0;jumlahbeli=0;jumlahpiutang=0;jumlahpesan=0;jumlahretbeli=0;
                                jumlahretjual=0;jumlahretpiut=0;jumlahpasin=0;stokawal=0;jumlahutd=0;stokakhir=0;
                                jumlahkeluar=0;jumlahrespulang=0;jumlahmutasimasuk=0;jumlahmutasikeluar=0;
                                jumlahhibah=0;totaljual=0;totalbeli=0;totalpesan=0;totalpiutang=0;totalutd=0;totalkeluar=0;
                                totalmutasikeluar=0;totalmutasimasuk=0;totalretbeli=0;totalretjual=0;totalretpiut=0;
                                totalpasin=0;totalrespulang=0;totalhibah=0;totalstokawal=0;totalstokakhir=0;harga=0;
                                tglopname=Valid.SetTgl(Tgl1.getSelectedItem()+"");
                                
                                ps3=koneksi.prepareStatement(
                                    "select riwayat_barang_medis.stok_akhir,riwayat_barang_medis.tanggal,riwayat_barang_medis.jam from riwayat_barang_medis where riwayat_barang_medis.tanggal < ? and "+
                                    "riwayat_barang_medis.kode_brng=? and riwayat_barang_medis.kd_bangsal=? and riwayat_barang_medis.no_batch=? and riwayat_barang_medis.no_faktur=? "+
                                    "order by concat(riwayat_barang_medis.tanggal,' ',riwayat_barang_medis.jam) desc limit 1");
                                try {
                                    ps3.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                                    ps3.setString(2,rs.getString("kode_brng"));
                                    ps3.setString(3,KdGudang.getText());
                                    ps3.setString(4,rs2.getString("no_batch"));
                                    ps3.setString(5,rs2.getString("no_faktur"));
                                    rs3=ps3.executeQuery();
                                    if(rs3.next()){
                                        tglopname=rs3.getString("tanggal")+" "+rs3.getString("jam");
                                        stokawal=rs3.getDouble("stok_akhir");
                                    }
                                } catch (Exception e) {
                                    System.out.println("Note : "+e);
                                } finally{
                                    if(rs3!=null){
                                        rs3.close();
                                    }
                                    if(ps3!=null){
                                        ps3.close();
                                    }
                                }
                                
                                
                                harga=Sequel.cariIsiAngka("select data_batch."+hppfarmasi+" from data_batch where data_batch.kode_brng='"+rs.getString("kode_brng")+"' and data_batch.no_batch='"+rs2.getString("no_batch")+"' and data_batch.no_faktur='"+rs2.getString("no_faktur")+"' ");
                                if(harga<=0){
                                    harga=rs.getDouble("harga");
                                }
                                
                                if(tampilmin.equals("ya")){
                                    totalstokawal=harga*stokawal;
                                }else{
                                    if(stokawal>0){
                                        totalstokawal=harga*stokawal;
                                    }else{
                                        totalstokawal=0;
                                    }
                                }
                                
                                //pembelian 
                                ps3=koneksi.prepareStatement(
                                    "select sum(detailbeli.jumlah2),sum(detailbeli.total) from pembelian inner join detailbeli on pembelian.no_faktur=detailbeli.no_faktur "+
                                    "where detailbeli.kode_brng=? and detailbeli.no_batch=? and detailbeli.no_faktur=? and pembelian.tgl_beli between ? and ? and pembelian.kd_bangsal=?");
                                try {
                                    ps3.setString(1,rs.getString("kode_brng"));
                                    ps3.setString(2,rs2.getString("no_batch"));
                                    ps3.setString(3,rs2.getString("no_faktur"));
                                    ps3.setString(4,tglopname);
                                    ps3.setString(5,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                                    ps3.setString(6,KdGudang.getText());
                                    rs3=ps3.executeQuery();
                                    if(rs3.next()){                    
                                        jumlahbeli=rs3.getDouble(1);
                                        if(tarifdasar.equals("ya")){
                                            totalbeli=jumlahbeli*harga;
                                        }else{
                                            totalbeli=rs3.getDouble(2);
                                        }   
                                    }
                                } catch (Exception e) {
                                    System.out.println("Note : "+e);
                                } finally{
                                    if(rs3!=null){
                                        rs3.close();
                                    }
                                    if(ps3!=null){
                                        ps3.close();
                                    }
                                }
                                
                                //pemesanan 
                                ps3=koneksi.prepareStatement(
                                    "select sum(detailpesan.jumlah2),sum(detailpesan.total) from pemesanan inner join detailpesan on pemesanan.no_faktur=detailpesan.no_faktur "+
                                    "where detailpesan.kode_brng=? and detailpesan.no_batch=? and detailpesan.no_faktur=? and pemesanan.tgl_pesan between ? and ? and pemesanan.kd_bangsal=?");
                                try {
                                    ps3.setString(1,rs.getString("kode_brng"));
                                    ps3.setString(2,rs2.getString("no_batch"));
                                    ps3.setString(3,rs2.getString("no_faktur"));
                                    ps3.setString(4,tglopname);
                                    ps3.setString(5,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                                    ps3.setString(6,KdGudang.getText());
                                    rs3=ps3.executeQuery();
                                    if(rs3.next()){                    
                                        jumlahpesan=rs3.getDouble(1);
                                        if(tarifdasar.equals("ya")){
                                            totalpesan=jumlahpesan*harga;
                                        }else{
                                            totalpesan=rs3.getDouble(2);
                                        }
                                    }
                                } catch (Exception e) {
                                    System.out.println("Note : "+e);
                                } finally{
                                    if(rs3!=null){
                                        rs3.close();
                                    }
                                    if(ps3!=null){
                                        ps3.close();
                                    }
                                }
                                
                                //penjualan
                                ps3=koneksi.prepareStatement(
                                    "select sum(detailjual.jumlah),sum(detailjual.total) from penjualan inner join detailjual on penjualan.nota_jual=detailjual.nota_jual "+
                                    "where penjualan.status='Sudah Dibayar' and detailjual.kode_brng=? and detailjual.no_batch=? and detailjual.no_faktur=? and "+
                                    "penjualan.tgl_jual between ? and ? and penjualan.kd_bangsal=?");
                                try {
                                    ps3.setString(1,rs.getString("kode_brng"));
                                    ps3.setString(2,rs2.getString("no_batch"));
                                    ps3.setString(3,rs2.getString("no_faktur"));
                                    ps3.setString(4,tglopname);
                                    ps3.setString(5,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                                    ps3.setString(6,KdGudang.getText());
                                    rs3=ps3.executeQuery();
                                    if(rs3.next()){                    
                                        jumlahjual=rs3.getDouble(1);
                                        if(tarifdasar.equals("ya")){
                                            totaljual=jumlahjual*harga;
                                        }else{
                                            totaljual=rs3.getDouble(2);
                                        }
                                    }
                                } catch (Exception e) {
                                    System.out.println("Note : "+e);
                                } finally{
                                    if(rs3!=null){
                                        rs3.close();
                                    }
                                    if(ps3!=null){
                                        ps3.close();
                                    }
                                } 
                                
                                //beri obat
                                ps3=koneksi.prepareStatement(
                                    "select sum(detail_pemberian_obat.jml) as jumlah,(sum(detail_pemberian_obat.total)-sum(detail_pemberian_obat.embalase+detail_pemberian_obat.tuslah)) as jumpas "+
                                    "from detail_pemberian_obat where detail_pemberian_obat.kode_brng=? and detail_pemberian_obat.no_batch=? and detail_pemberian_obat.no_faktur=? and "+
                                    "detail_pemberian_obat.tgl_perawatan between ? and ? and detail_pemberian_obat.kd_bangsal=?");            
                                try {
                                    ps3.setString(1,rs.getString("kode_brng"));
                                    ps3.setString(2,rs2.getString("no_batch"));
                                    ps3.setString(3,rs2.getString("no_faktur"));
                                    ps3.setString(4,tglopname);
                                    ps3.setString(5,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                                    ps3.setString(6,KdGudang.getText());
                                    rs3=ps3.executeQuery();
                                    if(rs3.next()){                    
                                        jumlahpasin=rs3.getDouble(1);
                                        if(tarifdasar.equals("ya")){
                                            totalpasin=jumlahpasin*harga;
                                        }else{
                                            totalpasin=rs3.getDouble(2);
                                        }
                                    }
                                } catch (Exception e) {
                                    System.out.println("Note : "+e);
                                } finally{
                                    if(rs3!=null){
                                        rs3.close();
                                    }
                                    if(ps3!=null){
                                        ps3.close();
                                    }
                                }
                                
                                //piutang 
                                ps3=koneksi.prepareStatement(
                                    "select sum(detailpiutang.jumlah),sum(detailpiutang.total) from piutang inner join detailpiutang on piutang.nota_piutang=detailpiutang.nota_piutang "+
                                    "where detailpiutang.kode_brng=? and detailpiutang.no_batch=? and detailpiutang.no_faktur=? and piutang.tgl_piutang between ? and ? and piutang.kd_bangsal=?");            
                                try {
                                    ps3.setString(1,rs.getString("kode_brng"));
                                    ps3.setString(2,rs2.getString("no_batch"));
                                    ps3.setString(3,rs2.getString("no_faktur"));
                                    ps3.setString(4,tglopname);
                                    ps3.setString(5,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                                    ps3.setString(6,KdGudang.getText());
                                    rs3=ps3.executeQuery();
                                    if(rs3.next()){                    
                                        jumlahpiutang=rs3.getDouble(1);
                                        if(tarifdasar.equals("ya")){
                                            totalpiutang=jumlahpiutang*harga;
                                        }else{
                                            totalpiutang=rs3.getDouble(2);
                                        }
                                    }
                                } catch (Exception e) {
                                    System.out.println("Note : "+e);
                                } finally{
                                    if(rs3!=null){
                                        rs3.close();
                                    }
                                    if(ps3!=null){
                                        ps3.close();
                                    }
                                }
                                
                                //returbeli
                                ps3=koneksi.prepareStatement(
                                    "select sum(detreturbeli.jml_retur2), sum(detreturbeli.total) from returbeli inner join detreturbeli on returbeli.no_retur_beli=detreturbeli.no_retur_beli "+
                                    "where detreturbeli.kode_brng=? and detreturbeli.no_batch=? and detreturbeli.no_faktur=? and returbeli.tgl_retur between ? and ? and returbeli.kd_bangsal=?");            
                                try {
                                    ps3.setString(1,rs.getString("kode_brng"));
                                    ps3.setString(2,rs2.getString("no_batch"));
                                    ps3.setString(3,rs2.getString("no_faktur"));
                                    ps3.setString(4,tglopname);
                                    ps3.setString(5,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                                    ps3.setString(6,KdGudang.getText());
                                    rs3=ps3.executeQuery();
                                    if(rs3.next()){                    
                                        jumlahretbeli=rs3.getDouble(1);
                                        if(tarifdasar.equals("ya")){
                                            totalretbeli=jumlahretbeli*harga;
                                        }else{
                                            totalretbeli=rs3.getDouble(2);
                                        }
                                    }
                                } catch (Exception e) {
                                    System.out.println("Note : "+e);
                                } finally{
                                    if(rs3!=null){
                                        rs3.close();
                                    }
                                    if(ps3!=null){
                                        ps3.close();
                                    }
                                }
                                
                                //returjual
                                ps3=koneksi.prepareStatement(
                                    "select sum(detreturjual.jml_retur),sum(detreturjual.subtotal) from returjual inner join detreturjual on returjual.no_retur_jual=detreturjual.no_retur_jual "+
                                    "where detreturjual.kode_brng=? and detreturjual.no_batch=? and detreturjual.no_faktur=? and returjual.tgl_retur between ? and ? and returjual.kd_bangsal=?");            
                                try {
                                    ps3.setString(1,rs.getString("kode_brng"));
                                    ps3.setString(2,rs2.getString("no_batch"));
                                    ps3.setString(3,rs2.getString("no_faktur"));
                                    ps3.setString(4,tglopname);
                                    ps3.setString(5,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                                    ps3.setString(6,KdGudang.getText());
                                    rs3=ps3.executeQuery();
                                    if(rs3.next()){                    
                                        jumlahretjual=rs3.getDouble(1);
                                        if(tarifdasar.equals("ya")){
                                            totalretjual=jumlahretjual*harga;
                                        }else{
                                            totalretjual=rs3.getDouble(2);
                                        }
                                    } 
                                } catch (Exception e) {
                                    System.out.println("Note : "+e);
                                } finally{
                                    if(rs3!=null){
                                        rs3.close();
                                    }
                                    if(ps3!=null){
                                        ps3.close();
                                    }
                                }
                                
                                //retur piutang
                                ps3=koneksi.prepareStatement(
                                    "select sum(detreturpiutang.jml_retur),sum(detreturpiutang.subtotal) from returpiutang inner join detreturpiutang on returpiutang.no_retur_piutang=detreturpiutang.no_retur_piutang "+
                                    "where detreturpiutang.kode_brng=? and detreturpiutang.no_batch=? and detreturpiutang.no_faktur=? and returpiutang.tgl_retur between ? and ? and returpiutang.kd_bangsal=?");            
                                try {
                                    ps3.setString(1,rs.getString("kode_brng"));
                                    ps3.setString(2,rs2.getString("no_batch"));
                                    ps3.setString(3,rs2.getString("no_faktur"));
                                    ps3.setString(4,tglopname);
                                    ps3.setString(5,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                                    ps3.setString(6,KdGudang.getText());
                                    rs3=ps3.executeQuery();
                                    if(rs3.next()){                    
                                        jumlahretpiut=rs3.getDouble(1);
                                        if(tarifdasar.equals("ya")){
                                            totalretpiut=jumlahretpiut*harga;
                                        }else{
                                            totalretpiut=rs3.getDouble(2);
                                        }
                                    }
                                } catch (Exception e) {
                                    System.out.println("Note : "+e);
                                } finally{
                                    if(rs3!=null){
                                        rs3.close();
                                    }
                                    if(ps3!=null){
                                        ps3.close();
                                    }
                                }
                                
                                //utd
                                ps3=koneksi.prepareStatement(
                                    "select sum(utd_pengambilan_medis.jml) as jumlah,sum(utd_pengambilan_medis.total) as jumpas from utd_pengambilan_medis "+
                                    "where utd_pengambilan_medis.kode_brng=? and utd_pengambilan_medis.no_batch=? and utd_pengambilan_medis.no_faktur=? and "+
                                    "utd_pengambilan_medis.tanggal between ? and ? and utd_pengambilan_medis.kd_bangsal_dr=?");            
                                try {
                                    ps3.setString(1,rs.getString("kode_brng"));
                                    ps3.setString(2,rs2.getString("no_batch"));
                                    ps3.setString(3,rs2.getString("no_faktur"));
                                    ps3.setString(4,tglopname+" 00:00:01");
                                    ps3.setString(5,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                                    ps3.setString(6,KdGudang.getText());
                                    rs3=ps3.executeQuery();
                                    if(rs3.next()){                    
                                        jumlahutd=rs3.getDouble(1);
                                        if(tarifdasar.equals("ya")){
                                            totalutd=jumlahutd*harga;
                                        }else{
                                            totalutd=rs3.getDouble(2);
                                        }
                                    }
                                } catch (Exception e) {
                                    System.out.println("Note : "+e);
                                } finally{
                                    if(rs3!=null){
                                        rs3.close();
                                    }
                                    if(ps3!=null){
                                        ps3.close();
                                    }
                                }
                                
                                //stok keluar
                                ps3=koneksi.prepareStatement(
                                    "select sum(detail_pengeluaran_obat_bhp.jumlah),sum(detail_pengeluaran_obat_bhp.total) from pengeluaran_obat_bhp inner join detail_pengeluaran_obat_bhp on pengeluaran_obat_bhp.no_keluar=detail_pengeluaran_obat_bhp.no_keluar "+
                                    " where detail_pengeluaran_obat_bhp.kode_brng=? and detail_pengeluaran_obat_bhp.no_batch=? and detail_pengeluaran_obat_bhp.no_faktur=? and pengeluaran_obat_bhp.tanggal between ? and ? and pengeluaran_obat_bhp.kd_bangsal=?");
                                try {
                                    ps3.setString(1,rs.getString("kode_brng"));
                                    ps3.setString(2,rs2.getString("no_batch"));
                                    ps3.setString(3,rs2.getString("no_faktur"));
                                    ps3.setString(4,tglopname);
                                    ps3.setString(5,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                                    ps3.setString(6,KdGudang.getText());
                                    rs3=ps3.executeQuery();
                                    if(rs3.next()){                    
                                        jumlahkeluar=rs3.getDouble(1);
                                        if(tarifdasar.equals("ya")){
                                            totalkeluar=jumlahkeluar*harga;
                                        }else{
                                            totalkeluar=rs3.getDouble(2);
                                        }
                                    }
                                } catch (Exception e) {
                                    System.out.println("Note : "+e);
                                } finally{
                                    if(rs3!=null){
                                        rs3.close();
                                    }
                                    if(ps3!=null){
                                        ps3.close();
                                    }
                                }  
                                
                                //resep pulang
                                ps3=koneksi.prepareStatement(
                                    "select sum(resep_pulang.jml_barang),sum(resep_pulang.total) from resep_pulang where resep_pulang.kode_brng=? and resep_pulang.no_batch=? and resep_pulang.no_faktur=? and resep_pulang.tanggal between ? and ? and resep_pulang.kd_bangsal=?");
                                try {
                                    ps3.setString(1,rs.getString("kode_brng"));
                                    ps3.setString(2,rs2.getString("no_batch"));
                                    ps3.setString(3,rs2.getString("no_faktur"));
                                    ps3.setString(4,tglopname);
                                    ps3.setString(5,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                                    ps3.setString(6,KdGudang.getText());
                                    rs3=ps3.executeQuery();
                                    if(rs3.next()){                    
                                        jumlahrespulang=rs3.getDouble(1);
                                        if(tarifdasar.equals("ya")){
                                            totalrespulang=jumlahrespulang*harga;
                                        }else{
                                            totalrespulang=rs3.getDouble(2);
                                        }
                                    }
                                } catch (Exception e) {
                                    System.out.println("Notifikas Resep Pulang : "+e);
                                } finally{
                                    if(rs3!=null){
                                        rs3.close();
                                    }
                                    if(ps3!=null){
                                        ps3.close();
                                    }
                                }
                                
                                //mutasi
                                ps3=koneksi.prepareStatement(
                                    "select sum(mutasibarang.jml),sum(mutasibarang.jml*mutasibarang.harga) from mutasibarang where mutasibarang.kode_brng=? and mutasibarang.no_batch=? and mutasibarang.no_faktur=? and mutasibarang.tanggal between ? and ? and mutasibarang.kd_bangsalke=?");
                                try {
                                    ps3.setString(1,rs.getString("kode_brng"));
                                    ps3.setString(2,rs2.getString("no_batch"));
                                    ps3.setString(3,rs2.getString("no_faktur"));
                                    ps3.setString(4,tglopname+" 00:00:01");
                                    ps3.setString(5,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                                    ps3.setString(6,KdGudang.getText());
                                    rs3=ps3.executeQuery();
                                    if(rs3.next()){                    
                                        jumlahmutasimasuk=rs3.getDouble(1);
                                        if(tarifdasar.equals("ya")){
                                            totalmutasimasuk=jumlahmutasimasuk*harga;
                                        }else{
                                            totalmutasimasuk=rs3.getDouble(2);
                                        }
                                    }
                                } catch (Exception e) {
                                    System.out.println("Notifikas Mutasi Masuk : "+e);
                                } finally{
                                    if(rs3!=null){
                                        rs3.close();
                                    }
                                    if(ps3!=null){
                                        ps3.close();
                                    }
                                }

                                ps3=koneksi.prepareStatement(
                                    "select sum(mutasibarang.jml), sum(mutasibarang.jml*mutasibarang.harga) from mutasibarang where mutasibarang.kode_brng=? and mutasibarang.no_batch=? and mutasibarang.no_faktur=? and mutasibarang.tanggal between ? and ? and mutasibarang.kd_bangsaldari=?");
                                try {
                                    ps3.setString(1,rs.getString("kode_brng"));
                                    ps3.setString(2,rs2.getString("no_batch"));
                                    ps3.setString(3,rs2.getString("no_faktur"));
                                    ps3.setString(4,tglopname+" 00:00:01");
                                    ps3.setString(5,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                                    ps3.setString(6,KdGudang.getText());
                                    rs3=ps3.executeQuery();
                                    if(rs3.next()){                    
                                        jumlahmutasikeluar=rs3.getDouble(1);
                                        if(tarifdasar.equals("ya")){
                                            totalmutasikeluar=jumlahmutasikeluar*harga;
                                        }else{
                                            totalmutasikeluar=rs3.getDouble(2);
                                        }
                                    }
                                } catch (Exception e) {
                                    System.out.println("Notifikas Mutasi Keluar : "+e);
                                } finally{
                                    if(rs3!=null){
                                        rs3.close();
                                    }
                                    if(ps3!=null){
                                        ps3.close();
                                    }
                                }
                                
                                //hibah
                                ps3=koneksi.prepareStatement(
                                    "select sum(detailhibah_obat_bhp.jumlah2),sum(detailhibah_obat_bhp.subtotaldiakui) from hibah_obat_bhp inner join detailhibah_obat_bhp on hibah_obat_bhp.no_hibah=detailhibah_obat_bhp.no_hibah "+
                                    "where detailhibah_obat_bhp.kode_brng=? and detailhibah_obat_bhp.no_batch=? and detailhibah_obat_bhp.no_hibah=? and hibah_obat_bhp.tgl_hibah between ? and ? and hibah_obat_bhp.kd_bangsal=?");
                                try {
                                    ps3.setString(1,rs.getString("kode_brng"));
                                    ps3.setString(2,rs2.getString("no_batch"));
                                    ps3.setString(3,rs2.getString("no_faktur"));
                                    ps3.setString(4,tglopname);
                                    ps3.setString(5,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                                    ps3.setString(6,KdGudang.getText());
                                    rs3=ps3.executeQuery();
                                    if(rs3.next()){                    
                                        jumlahhibah=rs3.getDouble(1);
                                        if(tarifdasar.equals("ya")){
                                            totalhibah=jumlahhibah*harga;
                                        }else{
                                            totalhibah=rs3.getDouble(2);
                                        }
                                    }
                                } catch (Exception e) {
                                    System.out.println("Note : "+e);
                                } finally{
                                    if(rs3!=null){
                                        rs3.close();
                                    }
                                    if(ps3!=null){
                                        ps3.close();
                                    }
                                }
                                
                                //stok akhir
                                ps3=koneksi.prepareStatement(
                                    "select riwayat_barang_medis.stok_akhir from riwayat_barang_medis where riwayat_barang_medis.tanggal < ? and "+
                                    "riwayat_barang_medis.kode_brng=? and riwayat_barang_medis.kd_bangsal=? and riwayat_barang_medis.no_batch=? and riwayat_barang_medis.no_faktur=? "+
                                    "order by concat(riwayat_barang_medis.tanggal,' ',riwayat_barang_medis.jam) desc limit 1");
                                try {
                                    ps3.setString(1,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                                    ps3.setString(2,rs.getString("kode_brng"));
                                    ps3.setString(3,KdGudang.getText());
                                    ps3.setString(4,rs2.getString("no_batch"));
                                    ps3.setString(5,rs2.getString("no_faktur"));
                                    rs3=ps3.executeQuery();
                                    if(rs3.next()){
                                        stokakhir=rs3.getDouble("stok_akhir");
                                    }
                                } catch (Exception e) {
                                    System.out.println("Note : "+e);
                                } finally{
                                    if(rs3!=null){
                                        rs3.close();
                                    }
                                    if(ps3!=null){
                                        ps3.close();
                                    }
                                }
                                
                                if(tampilmin.equals("ya")){
                                    totalstokakhir=harga*stokakhir;
                                }else{
                                    if(stokakhir>0){
                                        totalstokakhir=harga*stokakhir;
                                    }else{
                                        totalstokakhir=0;
                                    }
                                }
                                    
                    
                                if((jumlahbeli>0)||(jumlahpesan>0)||(jumlahjual>0)||(jumlahpasin>0)||(jumlahpiutang>0)||(jumlahhibah>0)||(jumlahmutasimasuk>0)||(jumlahmutasikeluar>0)||
                                    (jumlahutd>0)||(jumlahkeluar>0)||(jumlahretbeli>0)||(jumlahretjual>0)||(jumlahretpiut>0)||(stokawal>0)||(stokakhir>0)||(jumlahrespulang>0)){
                                    tabMode.addRow(new Object[]{
                                        rs.getString("kode_brng"),rs2.getString("no_batch"),rs2.getString("no_faktur"),rs.getString("nama_brng"),rs.getString("satuan"),
                                        tglopname,Valid.SetAngka(stokawal),Valid.SetAngka(totalstokawal),Valid.SetAngka(jumlahbeli),Valid.SetAngka(totalbeli),
                                        Valid.SetAngka(jumlahpesan),Valid.SetAngka(totalpesan),Valid.SetAngka(jumlahjual),Valid.SetAngka(totaljual),
                                        Valid.SetAngka(jumlahpasin),Valid.SetAngka(totalpasin),Valid.SetAngka(jumlahpiutang),Valid.SetAngka(totalpiutang),
                                        Valid.SetAngka(jumlahretbeli),Valid.SetAngka(totalretbeli),Valid.SetAngka(jumlahretjual),Valid.SetAngka(totalretjual),
                                        Valid.SetAngka(jumlahretpiut),Valid.SetAngka(totalretpiut),Valid.SetAngka(jumlahutd),Valid.SetAngka(totalutd),
                                        Valid.SetAngka(jumlahkeluar),Valid.SetAngka(totalkeluar),Valid.SetAngka(jumlahrespulang),Valid.SetAngka(totalrespulang),
                                        Valid.SetAngka(jumlahmutasimasuk),Valid.SetAngka(totalmutasimasuk),Valid.SetAngka(jumlahmutasikeluar),Valid.SetAngka(totalmutasikeluar),
                                        Valid.SetAngka(jumlahhibah),Valid.SetAngka(totalhibah),Valid.SetAngka(stokakhir),Valid.SetAngka(totalstokakhir)
                                    });
                                    ttltotaljual=ttltotaljual+totaljual;
                                    ttltotalbeli=ttltotalbeli+totalbeli;
                                    ttltotalpesan=ttltotalpesan+totalpesan;
                                    ttltotalpiutang=ttltotalpiutang+totalpiutang;
                                    ttltotalutd=ttltotalutd+totalutd;
                                    ttltotalkeluar=ttltotalkeluar+totalkeluar;
                                    ttltotalmutasikeluar=ttltotalmutasikeluar+totalmutasikeluar;
                                    ttltotalmutasimasuk=ttltotalmutasimasuk+totalmutasimasuk;
                                    ttltotalretbeli=ttltotalretbeli+totalretbeli;
                                    ttltotalretjual=ttltotalretjual+totalretjual;
                                    ttltotalretpiut=ttltotalretpiut+totalretpiut;
                                    ttltotalpasin=ttltotalpasin+totalpasin;
                                    ttltotalrespulang=ttltotalrespulang+totalrespulang;
                                    ttltotalhibah=ttltotalhibah+totalhibah;
                                    ttltotalstokawal=ttltotalstokawal+totalstokawal;
                                    ttltotalstokakhir=ttltotalstokakhir+totalstokakhir;
                                }
                                    
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
                tabMode.addRow(new Object[]{
                    "<>>","Total :","","","","","",Valid.SetAngka(ttltotalstokawal),"",Valid.SetAngka(ttltotalbeli),"",Valid.SetAngka(ttltotalpesan),"",Valid.SetAngka(ttltotaljual),
                    "",Valid.SetAngka(ttltotalpasin),"",Valid.SetAngka(ttltotalpiutang),"",Valid.SetAngka(ttltotalretbeli),"",Valid.SetAngka(ttltotalretjual),
                    "",Valid.SetAngka(ttltotalretpiut),"",Valid.SetAngka(ttltotalutd),"",Valid.SetAngka(ttltotalkeluar),"",Valid.SetAngka(ttltotalrespulang),
                    "",Valid.SetAngka(ttltotalmutasimasuk),"",Valid.SetAngka(ttltotalmutasikeluar),"",Valid.SetAngka(ttltotalhibah),"",Valid.SetAngka(ttltotalstokakhir)
                }); 
            }catch(Exception e){
                System.out.println("Notifikasi : "+e);
            }
            this.setCursor(Cursor.getDefaultCursor());
        }
    }
    
    public void isCek(){
         BtnPrint.setEnabled(akses.getsirkulasi_obat6());
    }
    
    private void runBackground(Runnable task) {
        if (ceksukses) return;
        ceksukses = true;

        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        executor.submit(() -> {
            try {
                task.run();
            } finally {
                ceksukses = false;
                SwingUtilities.invokeLater(() -> {
                    this.setCursor(Cursor.getDefaultCursor());
                });
            }
        });
    }
}

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

package ipsrs;
import fungsi.WarnaTable2;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
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

public class IPSRSInputStok extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement pstampil;
    private ResultSet rstampil;
    private double ttl=0,y=0,kurang=0,ttl2;
    private int jml=0,i=0,index=0;
    private String[] real,kodebarang,namabarang,kategori,satuan;
    private double[] hargabeli,stok,selisih,nomihilang,lebih,nomilebih;
    private WarnaTable2 warna=new WarnaTable2();
    private riwayatnonmedis Trackbarang=new riwayatnonmedis();
    private boolean sukses=true;    
    private String order="order by ipsrsbarang.nama_brng";

    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public IPSRSInputStok(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        tabMode=new DefaultTableModel(null,new Object[]{
                "Real","Kode Barang","Nama Barang","Kategori","Satuan","Harga","Stok","Selisih","Nominal Hilang(Rp)","Lebih","Nominal Lebih(Rp)"
            }){
            @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
            Class[] types = new Class[] {
                java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,
                java.lang.String.class,java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,
                java.lang.Double.class,java.lang.Double.class,java.lang.Double.class  
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbDokter.setModel(tabMode);

        tbDokter.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 11; i++) {
            TableColumn column = tbDokter.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(42);
            }else if(i==1){
                column.setPreferredWidth(90);
            }else if(i==2){
                column.setPreferredWidth(150);
            }else if(i==3){
                column.setPreferredWidth(100);
            }else if(i==4){
                column.setPreferredWidth(50);
            }else if(i==5){
                column.setPreferredWidth(80);
            }else if(i==6){
                column.setPreferredWidth(42);
            }else if(i==7){
                column.setPreferredWidth(42);
            }else if(i==8){
                column.setPreferredWidth(105);
            }else if(i==9){
                column.setPreferredWidth(42);
            }else if(i==10){
                column.setPreferredWidth(105);
            }
        }
        warna.kolom=0;
        tbDokter.setDefaultRenderer(Object.class,warna);

        catatan.setDocument(new batasInput((byte)60).getKata(catatan));  
        TCari.setDocument(new batasInput((byte)100).getKata(TCari)); 
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
        TCari.requestFocus();
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
        ppBelumOpname = new javax.swing.JMenuItem();
        ppSudahOpname = new javax.swing.JMenuItem();
        MnUrut = new javax.swing.JMenu();
        MnKodeBarangDesc = new javax.swing.JMenuItem();
        MnKodeBarangAsc = new javax.swing.JMenuItem();
        MnNamaBarangDesc = new javax.swing.JMenuItem();
        MnNamaBarangAsc = new javax.swing.JMenuItem();
        MnKategoriAsc = new javax.swing.JMenuItem();
        MnKategoriDesc = new javax.swing.JMenuItem();
        MnSatuanDesc = new javax.swing.JMenuItem();
        MnSatuanAsc = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        scrollPane1 = new widget.ScrollPane();
        tbDokter = new widget.Table();
        jPanel1 = new javax.swing.JPanel();
        panelisi1 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        label10 = new widget.Label();
        LTotal = new widget.Label();
        label12 = new widget.Label();
        LTotal1 = new widget.Label();
        BtnCari = new widget.Button();
        BtnKeluar = new widget.Button();
        panelisi5 = new widget.panelisi();
        label9 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari1 = new widget.Button();
        BtnTambah = new widget.Button();
        BtnPrint = new widget.Button();
        panelisi3 = new widget.panelisi();
        label18 = new widget.Label();
        catatan = new widget.TextBox();
        label11 = new widget.Label();
        Tgl = new widget.Tanggal();

        Kd2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Kd2.setName("Kd2"); // NOI18N
        Kd2.setPreferredSize(new java.awt.Dimension(207, 23));

        Popup.setName("Popup"); // NOI18N

        ppBersihkan.setBackground(new java.awt.Color(255, 255, 254));
        ppBersihkan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppBersihkan.setForeground(new java.awt.Color(50, 50, 50));
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

        ppBelumOpname.setBackground(new java.awt.Color(255, 255, 254));
        ppBelumOpname.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppBelumOpname.setForeground(new java.awt.Color(50, 50, 50));
        ppBelumOpname.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppBelumOpname.setText("Tampilkan Belum Diopname");
        ppBelumOpname.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppBelumOpname.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppBelumOpname.setName("ppBelumOpname"); // NOI18N
        ppBelumOpname.setPreferredSize(new java.awt.Dimension(200, 26));
        ppBelumOpname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppBelumOpnameActionPerformed(evt);
            }
        });
        Popup.add(ppBelumOpname);

        ppSudahOpname.setBackground(new java.awt.Color(255, 255, 254));
        ppSudahOpname.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppSudahOpname.setForeground(new java.awt.Color(50, 50, 50));
        ppSudahOpname.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppSudahOpname.setText("Tampilkan Sudah Diopname");
        ppSudahOpname.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppSudahOpname.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppSudahOpname.setName("ppSudahOpname"); // NOI18N
        ppSudahOpname.setPreferredSize(new java.awt.Dimension(200, 26));
        ppSudahOpname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppSudahOpnameActionPerformed(evt);
            }
        });
        Popup.add(ppSudahOpname);

        MnUrut.setBackground(new java.awt.Color(250, 255, 245));
        MnUrut.setForeground(new java.awt.Color(50, 50, 50));
        MnUrut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUrut.setText("Urutkan Data Berdasar");
        MnUrut.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUrut.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnUrut.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnUrut.setName("MnUrut"); // NOI18N
        MnUrut.setPreferredSize(new java.awt.Dimension(200, 26));

        MnKodeBarangDesc.setBackground(new java.awt.Color(255, 255, 254));
        MnKodeBarangDesc.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnKodeBarangDesc.setForeground(new java.awt.Color(50, 50, 50));
        MnKodeBarangDesc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnKodeBarangDesc.setText("Kode Barang Descending");
        MnKodeBarangDesc.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnKodeBarangDesc.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnKodeBarangDesc.setName("MnKodeBarangDesc"); // NOI18N
        MnKodeBarangDesc.setPreferredSize(new java.awt.Dimension(180, 26));
        MnKodeBarangDesc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnKodeBarangDescActionPerformed(evt);
            }
        });
        MnUrut.add(MnKodeBarangDesc);

        MnKodeBarangAsc.setBackground(new java.awt.Color(255, 255, 254));
        MnKodeBarangAsc.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnKodeBarangAsc.setForeground(new java.awt.Color(50, 50, 50));
        MnKodeBarangAsc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnKodeBarangAsc.setText("Kode Barang Ascending");
        MnKodeBarangAsc.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnKodeBarangAsc.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnKodeBarangAsc.setName("MnKodeBarangAsc"); // NOI18N
        MnKodeBarangAsc.setPreferredSize(new java.awt.Dimension(180, 26));
        MnKodeBarangAsc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnKodeBarangAscActionPerformed(evt);
            }
        });
        MnUrut.add(MnKodeBarangAsc);

        MnNamaBarangDesc.setBackground(new java.awt.Color(255, 255, 254));
        MnNamaBarangDesc.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnNamaBarangDesc.setForeground(new java.awt.Color(50, 50, 50));
        MnNamaBarangDesc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnNamaBarangDesc.setText("Nama Barang Descending");
        MnNamaBarangDesc.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnNamaBarangDesc.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnNamaBarangDesc.setName("MnNamaBarangDesc"); // NOI18N
        MnNamaBarangDesc.setPreferredSize(new java.awt.Dimension(180, 26));
        MnNamaBarangDesc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnNamaBarangDescActionPerformed(evt);
            }
        });
        MnUrut.add(MnNamaBarangDesc);

        MnNamaBarangAsc.setBackground(new java.awt.Color(255, 255, 254));
        MnNamaBarangAsc.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnNamaBarangAsc.setForeground(new java.awt.Color(50, 50, 50));
        MnNamaBarangAsc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnNamaBarangAsc.setText("Nama Barang Ascending");
        MnNamaBarangAsc.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnNamaBarangAsc.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnNamaBarangAsc.setName("MnNamaBarangAsc"); // NOI18N
        MnNamaBarangAsc.setPreferredSize(new java.awt.Dimension(180, 26));
        MnNamaBarangAsc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnNamaBarangAscActionPerformed(evt);
            }
        });
        MnUrut.add(MnNamaBarangAsc);

        MnKategoriAsc.setBackground(new java.awt.Color(255, 255, 254));
        MnKategoriAsc.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnKategoriAsc.setForeground(new java.awt.Color(50, 50, 50));
        MnKategoriAsc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnKategoriAsc.setText("Kategori Ascending");
        MnKategoriAsc.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnKategoriAsc.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnKategoriAsc.setName("MnKategoriAsc"); // NOI18N
        MnKategoriAsc.setPreferredSize(new java.awt.Dimension(180, 26));
        MnKategoriAsc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnKategoriAscActionPerformed(evt);
            }
        });
        MnUrut.add(MnKategoriAsc);

        MnKategoriDesc.setBackground(new java.awt.Color(255, 255, 254));
        MnKategoriDesc.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnKategoriDesc.setForeground(new java.awt.Color(50, 50, 50));
        MnKategoriDesc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnKategoriDesc.setText("Kategori Descending");
        MnKategoriDesc.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnKategoriDesc.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnKategoriDesc.setName("MnKategoriDesc"); // NOI18N
        MnKategoriDesc.setPreferredSize(new java.awt.Dimension(180, 26));
        MnKategoriDesc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnKategoriDescActionPerformed(evt);
            }
        });
        MnUrut.add(MnKategoriDesc);

        MnSatuanDesc.setBackground(new java.awt.Color(255, 255, 254));
        MnSatuanDesc.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSatuanDesc.setForeground(new java.awt.Color(50, 50, 50));
        MnSatuanDesc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnSatuanDesc.setText("Satuan Descending");
        MnSatuanDesc.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnSatuanDesc.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnSatuanDesc.setName("MnSatuanDesc"); // NOI18N
        MnSatuanDesc.setPreferredSize(new java.awt.Dimension(180, 26));
        MnSatuanDesc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSatuanDescActionPerformed(evt);
            }
        });
        MnUrut.add(MnSatuanDesc);

        MnSatuanAsc.setBackground(new java.awt.Color(255, 255, 254));
        MnSatuanAsc.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSatuanAsc.setForeground(new java.awt.Color(50, 50, 50));
        MnSatuanAsc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnSatuanAsc.setText("Satuan Ascending");
        MnSatuanAsc.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnSatuanAsc.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnSatuanAsc.setName("MnSatuanAsc"); // NOI18N
        MnSatuanAsc.setPreferredSize(new java.awt.Dimension(180, 26));
        MnSatuanAsc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSatuanAscActionPerformed(evt);
            }
        });
        MnUrut.add(MnSatuanAsc);

        Popup.add(MnUrut);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Stok Opname Barang Non Medis, Penunjang Lab & Radiologi ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        scrollPane1.setComponentPopupMenu(Popup);
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
        tbDokter.setComponentPopupMenu(Popup);
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
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbDokterKeyReleased(evt);
            }
        });
        scrollPane1.setViewportView(tbDokter);

        internalFrame1.add(scrollPane1, java.awt.BorderLayout.CENTER);

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(816, 100));
        jPanel1.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 56));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

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

        label10.setText("Hilang :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(50, 23));
        panelisi1.add(label10);

        LTotal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LTotal.setText("0");
        LTotal.setName("LTotal"); // NOI18N
        LTotal.setPreferredSize(new java.awt.Dimension(110, 23));
        panelisi1.add(LTotal);

        label12.setText("Lebih :");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(50, 23));
        panelisi1.add(label12);

        LTotal1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LTotal1.setText("0");
        LTotal1.setName("LTotal1"); // NOI18N
        LTotal1.setPreferredSize(new java.awt.Dimension(110, 23));
        panelisi1.add(LTotal1);

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
        panelisi5.setPreferredSize(new java.awt.Dimension(100, 52));
        panelisi5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        label9.setText("Key Word :");
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi5.add(label9);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(465, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelisi5.add(TCari);

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
        panelisi5.add(BtnCari1);

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
        panelisi5.add(BtnTambah);

        BtnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png"))); // NOI18N
        BtnPrint.setMnemonic('T');
        BtnPrint.setToolTipText("Alt+T");
        BtnPrint.setName("BtnPrint"); // NOI18N
        BtnPrint.setPreferredSize(new java.awt.Dimension(28, 23));
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
        panelisi5.add(BtnPrint);

        jPanel1.add(panelisi5, java.awt.BorderLayout.CENTER);

        internalFrame1.add(jPanel1, java.awt.BorderLayout.PAGE_END);

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(89, 43));
        panelisi3.setLayout(null);

        label18.setText("Keterangan :");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(label18);
        label18.setBounds(218, 10, 110, 23);

        catatan.setName("catatan"); // NOI18N
        catatan.setPreferredSize(new java.awt.Dimension(207, 23));
        catatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                catatanKeyPressed(evt);
            }
        });
        panelisi3.add(catatan);
        catatan.setBounds(332, 10, 325, 23);

        label11.setText("Tanggal Opname :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label11);
        label11.setBounds(0, 10, 110, 23);

        Tgl.setDisplayFormat("dd-MM-yyyy");
        Tgl.setName("Tgl"); // NOI18N
        Tgl.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglKeyPressed(evt);
            }
        });
        panelisi3.add(Tgl);
        Tgl.setBounds(114, 10, 95, 23);

        internalFrame1.add(panelisi3, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tbDokterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDokterMouseClicked
        if(tabMode.getRowCount()!=0){
            try {                  
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbDokterMouseClicked

    private void tbDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbDokterKeyPressed
        if(tabMode.getRowCount()!=0){
            if(evt.getKeyCode()==KeyEvent.VK_DELETE){
                try {
                    if(tbDokter.getSelectedColumn()==0){
                        tbDokter.setValueAt("",tbDokter.getSelectedRow(),0);
                        tbDokter.setValueAt(0,tbDokter.getSelectedRow(),6);
                        tbDokter.setValueAt(0,tbDokter.getSelectedRow(),7);
                        tbDokter.setValueAt(0,tbDokter.getSelectedRow(),8);
                        tbDokter.setValueAt(0,tbDokter.getSelectedRow(),9);
                        tbDokter.setValueAt(0,tbDokter.getSelectedRow(),10);
                    }
                } catch (Exception e) {
                } 
            }else if(evt.getKeyCode()==KeyEvent.VK_SHIFT){
                TCari.setText("");
                TCari.requestFocus();
            }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
                if(tbDokter.getSelectedColumn()==1){
                    TCari.setText("");
                    TCari.requestFocus();
                }
            }
        }
}//GEN-LAST:event_tbDokterKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
       this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        IPSRSStokOpname ipsrsopname=new IPSRSStokOpname(null,false);
        ipsrsopname.isCek(); 
        ipsrsopname.emptTeks();
        ipsrsopname.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        ipsrsopname.setLocationRelativeTo(internalFrame1);
        ipsrsopname.setAlwaysOnTop(false);
        ipsrsopname.setVisible(true);
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
        if(catatan.getText().trim().equals("")){
            Valid.textKosong(catatan,"Keterangan");
        }else if(tbDokter.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data kosong..!!!!");
            tbDokter.requestFocus();
        }else{
            i= JOptionPane.showConfirmDialog(rootPane,"Eeiiiiiits, udah bener belum data yang mau disimpan..??","Konfirmasi",JOptionPane.YES_NO_OPTION);
            if (i == JOptionPane.YES_OPTION) {
                Sequel.AutoComitFalse();
                sukses=true;
                for(i=0;i<tbDokter.getRowCount();i++){  
                    if(!tbDokter.getValueAt(i,0).toString().equals("")){
                        try {
                            if(Valid.SetAngka(tbDokter.getValueAt(i,0).toString())>=0){
                                if(Sequel.menyimpantf2("ipsrsopname","?,?,?,?,?,?,?,?,?,?","Stok Opname",10,new String[]{
                                        tbDokter.getValueAt(i,1).toString(),tbDokter.getValueAt(i,5).toString(),Valid.SetTgl(Tgl.getSelectedItem()+""),tbDokter.getValueAt(i,6).toString(),
                                        tbDokter.getValueAt(i,0).toString(),tbDokter.getValueAt(i,7).toString(),tbDokter.getValueAt(i,8).toString(),tbDokter.getValueAt(i,9).toString(),tbDokter.getValueAt(i,10).toString(),catatan.getText()
                                    })==true){
                                    Trackbarang.catatRiwayat(tbDokter.getValueAt(i,1).toString(),Valid.SetAngka(tbDokter.getValueAt(i,0).toString()),0,"Opname",akses.getkode(),"Simpan");
                                    Sequel.mengedit3("ipsrsbarang","kode_brng=?","stok=?",2,new String[]{
                                        tbDokter.getValueAt(i,0).toString(),tbDokter.getValueAt(i,1).toString()
                                    });
                                }else{
                                    sukses=false;
                                }
                            }
                        } catch (Exception e) {
                        }
                    }   
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
                    for(index=0;index<tbDokter.getRowCount();index++){   
                        tbDokter.setValueAt("",index,0);        
                    }
                    tampil();
                }
            }
        }
    }//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
           Valid.pindah(evt,Tgl,BtnCari);
        }
    }//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCari1ActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnSimpan,BtnKeluar);
        }
    }//GEN-LAST:event_BtnCariKeyPressed

private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
       if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            tampil();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari1.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            tbDokter.requestFocus();
        }
}//GEN-LAST:event_TCariKeyPressed

private void BtnCari1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCari1ActionPerformed
        tampil();
}//GEN-LAST:event_BtnCari1ActionPerformed

private void BtnCari1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCari1KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            tampil();
        }else{
            Valid.pindah(evt, TCari, BtnSimpan);
        }
}//GEN-LAST:event_BtnCari1KeyPressed

private void catatanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_catatanKeyPressed
        Valid.pindah(evt, Tgl,BtnSimpan);
}//GEN-LAST:event_catatanKeyPressed

private void TglKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglKeyPressed
        Valid.pindah(evt,TCari,Tgl);
}//GEN-LAST:event_TglKeyPressed

private void ppBersihkanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBersihkanActionPerformed
            int row2=tbDokter.getRowCount();
            for(int r=0;r<row2;r++){ 
                tbDokter.setValueAt("",r,0);
                tbDokter.setValueAt(0,r,6);
                tbDokter.setValueAt(0,r,7);
                tbDokter.setValueAt(0,r,8);
                tbDokter.setValueAt(0,r,9);
                tbDokter.setValueAt(0,r,10);
            }
}//GEN-LAST:event_ppBersihkanActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void tbDokterKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbDokterKeyReleased
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)||(evt.getKeyCode()==KeyEvent.VK_RIGHT)){
                try {                                     
                    getData();           
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }//GEN-LAST:event_tbDokterKeyReleased

    private void tbDokterPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tbDokterPropertyChange
        if(this.isVisible()==true){
              getData();
        }
    }//GEN-LAST:event_tbDokterPropertyChange

    private void BtnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        IPSRSBarang barang=new IPSRSBarang(null,false);
        barang.emptTeks();
        barang.isCek();
        barang.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        barang.setLocationRelativeTo(internalFrame1);
        barang.setAlwaysOnTop(false);
        barang.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnTambahActionPerformed

    private void ppBelumOpnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBelumOpnameActionPerformed
        try{
            Valid.tabelKosong(tabMode);
            pstampil=koneksi.prepareStatement("select ipsrsbarang.kode_brng, ipsrsbarang.nama_brng,ipsrsjenisbarang.nm_jenis, ipsrsbarang.kode_sat, "+
                "ipsrsbarang.harga,ipsrsbarang.stok from ipsrsbarang inner join ipsrsjenisbarang on ipsrsbarang.jenis=ipsrsjenisbarang.kd_jenis "+
                " where ipsrsbarang.kode_brng not in (select kode_brng from ipsrsopname where tanggal=?) and ipsrsbarang.status='1' and ipsrsbarang.kode_brng like ? or "+
                " ipsrsbarang.kode_brng not in (select kode_brng from ipsrsopname where tanggal=?) and ipsrsbarang.status='1' and ipsrsbarang.nama_brng like ? or "+
                " ipsrsbarang.kode_brng not in (select kode_brng from ipsrsopname where tanggal=?) and ipsrsbarang.status='1' and ipsrsbarang.kode_sat like ? or "+
                " ipsrsbarang.kode_brng not in (select kode_brng from ipsrsopname where tanggal=?) and ipsrsbarang.status='1' and ipsrsjenisbarang.nm_jenis like ? "+order);
            try {
                pstampil.setString(1,Valid.SetTgl(Tgl.getSelectedItem()+""));
                pstampil.setString(2,"%"+TCari.getText().trim()+"%");
                pstampil.setString(3,Valid.SetTgl(Tgl.getSelectedItem()+""));
                pstampil.setString(4,"%"+TCari.getText().trim()+"%");
                pstampil.setString(5,Valid.SetTgl(Tgl.getSelectedItem()+""));
                pstampil.setString(6,"%"+TCari.getText().trim()+"%");
                pstampil.setString(7,Valid.SetTgl(Tgl.getSelectedItem()+""));
                pstampil.setString(8,"%"+TCari.getText().trim()+"%");
                rstampil=pstampil.executeQuery();
                while(rstampil.next()){                            
                    tabMode.addRow(new Object[]{
                        "",rstampil.getString("kode_brng"),
                        rstampil.getString("nama_brng"),
                        rstampil.getString("nm_jenis"),
                        rstampil.getString("kode_sat"),
                        rstampil.getDouble("harga"),
                        rstampil.getDouble("stok"),0,0,0,0
                    });
                }    
            } catch (Exception e) {
                System.out.println("Ntif : "+e);
            } finally{
                if(rstampil!=null){
                    rstampil.close();
                }
                if(pstampil!=null){
                    pstampil.close();
                }
            } 
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }//GEN-LAST:event_ppBelumOpnameActionPerformed

    private void ppSudahOpnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppSudahOpnameActionPerformed
        try{
            Valid.tabelKosong(tabMode);
            pstampil=koneksi.prepareStatement("select ipsrsbarang.kode_brng, ipsrsbarang.nama_brng,ipsrsjenisbarang.nm_jenis, ipsrsbarang.kode_sat, "+
                "ipsrsbarang.harga,ipsrsbarang.stok from ipsrsbarang inner join ipsrsjenisbarang on ipsrsbarang.jenis=ipsrsjenisbarang.kd_jenis "+
                " where ipsrsbarang.kode_brng in (select kode_brng from ipsrsopname where tanggal=?) and ipsrsbarang.status='1' and ipsrsbarang.kode_brng like ? or "+
                " ipsrsbarang.kode_brng in (select kode_brng from ipsrsopname where tanggal=?) and ipsrsbarang.status='1' and ipsrsbarang.nama_brng like ? or "+
                " ipsrsbarang.kode_brng in (select kode_brng from ipsrsopname where tanggal=?) and ipsrsbarang.status='1' and ipsrsbarang.kode_sat like ? or "+
                " ipsrsbarang.kode_brng in (select kode_brng from ipsrsopname where tanggal=?) and ipsrsbarang.status='1' and ipsrsjenisbarang.nm_jenis like ? "+order);
            try {
                pstampil.setString(1,Valid.SetTgl(Tgl.getSelectedItem()+""));
                pstampil.setString(2,"%"+TCari.getText().trim()+"%");
                pstampil.setString(3,Valid.SetTgl(Tgl.getSelectedItem()+""));
                pstampil.setString(4,"%"+TCari.getText().trim()+"%");
                pstampil.setString(5,Valid.SetTgl(Tgl.getSelectedItem()+""));
                pstampil.setString(6,"%"+TCari.getText().trim()+"%");
                pstampil.setString(7,Valid.SetTgl(Tgl.getSelectedItem()+""));
                pstampil.setString(8,"%"+TCari.getText().trim()+"%");
                rstampil=pstampil.executeQuery();
                while(rstampil.next()){                            
                    tabMode.addRow(new Object[]{
                        "",rstampil.getString("kode_brng"),
                        rstampil.getString("nama_brng"),
                        rstampil.getString("nm_jenis"),
                        rstampil.getString("kode_sat"),
                        rstampil.getDouble("harga"),
                        rstampil.getDouble("stok"),0,0,0,0
                    });
                }    
            } catch (Exception e) {
                System.out.println("Ntif : "+e);
            } finally{
                if(rstampil!=null){
                    rstampil.close();
                }
                if(pstampil!=null){
                    pstampil.close();
                }
            } 
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }//GEN-LAST:event_ppSudahOpnameActionPerformed

    private void MnKodeBarangDescActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnKodeBarangDescActionPerformed
        order="order by ipsrsbarang.kode_brng desc";
        tampil();
    }//GEN-LAST:event_MnKodeBarangDescActionPerformed

    private void MnKodeBarangAscActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnKodeBarangAscActionPerformed
        order="order by ipsrsbarang.kode_brng asc";
        tampil();
    }//GEN-LAST:event_MnKodeBarangAscActionPerformed

    private void MnNamaBarangDescActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnNamaBarangDescActionPerformed
        order="order by ipsrsbarang.nama_brng desc";
        tampil();
    }//GEN-LAST:event_MnNamaBarangDescActionPerformed

    private void MnNamaBarangAscActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnNamaBarangAscActionPerformed
        order="order by ipsrsbarang.nama_brng asc";
        tampil();
    }//GEN-LAST:event_MnNamaBarangAscActionPerformed

    private void MnKategoriAscActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnKategoriAscActionPerformed
        order="order by ipsrsjenisbarang.nm_jenis desc";
        tampil();
    }//GEN-LAST:event_MnKategoriAscActionPerformed

    private void MnKategoriDescActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnKategoriDescActionPerformed
        order="order by ipsrsjenisbarang.nm_jenis asc";
        tampil();
    }//GEN-LAST:event_MnKategoriDescActionPerformed

    private void MnSatuanDescActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnSatuanDescActionPerformed
        order="order by ipsrsbarang.kode_sat desc";
        tampil();
    }//GEN-LAST:event_MnSatuanDescActionPerformed

    private void MnSatuanAscActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnSatuanAscActionPerformed
        order="order by ipsrsbarang.kode_sat asc";
        tampil();
    }//GEN-LAST:event_MnSatuanAscActionPerformed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            TCari.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            Sequel.queryu("truncate table temporary");
            int row=tabMode.getRowCount();
            for(int i=0;i<row;i++){
                Sequel.menyimpan("temporary","'0','"+
                    tabMode.getValueAt(i,1).toString()+"','"+
                    tabMode.getValueAt(i,2).toString()+"','"+
                    tabMode.getValueAt(i,3).toString()+"','"+
                    tabMode.getValueAt(i,4).toString()+"','"+
                    tabMode.getValueAt(i,5).toString()+"','"+
                    tabMode.getValueAt(i,6).toString()+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Stok Opname");
            }

            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("logo",Sequel.cariGambar("select logo from setting"));
            Valid.MyReport("rptInputStokOpnameNonMedis.jasper","report","::[ Data Persediaan Stok Barang Non Medis ]::",param);
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnCari, BtnKeluar);
        }
    }//GEN-LAST:event_BtnPrintKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            IPSRSInputStok dialog = new IPSRSInputStok(new javax.swing.JFrame(), true);
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
    private widget.Button BtnCari;
    private widget.Button BtnCari1;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.Button BtnTambah;
    private widget.TextBox Kd2;
    private widget.Label LTotal;
    private widget.Label LTotal1;
    private javax.swing.JMenuItem MnKategoriAsc;
    private javax.swing.JMenuItem MnKategoriDesc;
    private javax.swing.JMenuItem MnKodeBarangAsc;
    private javax.swing.JMenuItem MnKodeBarangDesc;
    private javax.swing.JMenuItem MnNamaBarangAsc;
    private javax.swing.JMenuItem MnNamaBarangDesc;
    private javax.swing.JMenuItem MnSatuanAsc;
    private javax.swing.JMenuItem MnSatuanDesc;
    private javax.swing.JMenu MnUrut;
    private javax.swing.JPopupMenu Popup;
    private widget.TextBox TCari;
    private widget.Tanggal Tgl;
    private widget.TextBox catatan;
    private widget.InternalFrame internalFrame1;
    private javax.swing.JPanel jPanel1;
    private widget.Label label10;
    private widget.Label label11;
    private widget.Label label12;
    private widget.Label label18;
    private widget.Label label9;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi3;
    private widget.panelisi panelisi5;
    private javax.swing.JMenuItem ppBelumOpname;
    private javax.swing.JMenuItem ppBersihkan;
    private javax.swing.JMenuItem ppSudahOpname;
    private widget.ScrollPane scrollPane1;
    private widget.Table tbDokter;
    // End of variables declaration//GEN-END:variables

    public void tampil() {        
        try{     
            jml=0;
            for(i=0;i<tbDokter.getRowCount();i++){
                if(!tbDokter.getValueAt(i,0).toString().equals("")){
                    jml++;
                }
            }
            real=new String[jml];
            kodebarang=new String[jml];
            namabarang=new String[jml];
            kategori=new String[jml];
            satuan=new String[jml];
            hargabeli=new double[jml];
            stok=new double[jml];
            selisih=new double[jml];
            nomihilang=new double[jml];
            lebih=new double[jml];
            nomilebih=new double[jml];

            index=0;        
            for(i=0;i<tbDokter.getRowCount();i++){
                if(!tbDokter.getValueAt(i,0).toString().equals("")){
                    real[index]=tbDokter.getValueAt(i,0).toString();
                    kodebarang[index]=tbDokter.getValueAt(i,1).toString();
                    namabarang[index]=tbDokter.getValueAt(i,2).toString();
                    kategori[index]=tbDokter.getValueAt(i,3).toString();
                    satuan[index]=tbDokter.getValueAt(i,4).toString();
                    hargabeli[index]=Double.parseDouble(tbDokter.getValueAt(i,5).toString());
                    stok[index]=Double.parseDouble(tbDokter.getValueAt(i,6).toString());
                    selisih[index]=Double.parseDouble(tbDokter.getValueAt(i,7).toString());
                    nomihilang[index]=Double.parseDouble(tbDokter.getValueAt(i,8).toString());
                    lebih[index]=Double.parseDouble(tbDokter.getValueAt(i,9).toString());
                    nomilebih[index]=Double.parseDouble(tbDokter.getValueAt(i,10).toString());
                    index++;
                }
            }

            Valid.tabelKosong(tabMode);
            for(i=0;i<jml;i++){
                tabMode.addRow(new Object[]{
                    real[i],kodebarang[i],namabarang[i],kategori[i],satuan[i],
                    hargabeli[i],stok[i],selisih[i],nomihilang[i],lebih[i],nomilebih[i]
                });
            }
            pstampil=koneksi.prepareStatement("select ipsrsbarang.kode_brng, ipsrsbarang.nama_brng,ipsrsjenisbarang.nm_jenis, ipsrsbarang.kode_sat, "+
                "ipsrsbarang.harga,ipsrsbarang.stok from ipsrsbarang inner join ipsrsjenisbarang on ipsrsbarang.jenis=ipsrsjenisbarang.kd_jenis "+
                " where ipsrsbarang.status='1' and ipsrsbarang.kode_brng like ? or "+
                " ipsrsbarang.status='1' and ipsrsbarang.nama_brng like ? or "+
                " ipsrsbarang.status='1' and ipsrsbarang.kode_sat like ? or "+
                " ipsrsbarang.status='1' and ipsrsjenisbarang.nm_jenis like ? "+order);
            try {
                pstampil.setString(1,"%"+TCari.getText().trim()+"%");
                pstampil.setString(2,"%"+TCari.getText().trim()+"%");
                pstampil.setString(3,"%"+TCari.getText().trim()+"%");
                pstampil.setString(4,"%"+TCari.getText().trim()+"%");
                rstampil=pstampil.executeQuery();
                while(rstampil.next()){                            
                    tabMode.addRow(new Object[]{
                        "",rstampil.getString("kode_brng"),
                        rstampil.getString("nama_brng"),
                        rstampil.getString("nm_jenis"),
                        rstampil.getString("kode_sat"),
                        rstampil.getDouble("harga"),
                        rstampil.getDouble("stok"),0,0,0,0
                    });
                }    
            } catch (Exception e) {
                System.out.println("Ntif : "+e);
            } finally{
                if(rstampil!=null){
                    rstampil.close();
                }
                if(pstampil!=null){
                    pstampil.close();
                }
            }                            
        }catch(SQLException e){
            System.out.println("Notifikasi : "+e);
        }
        
    }
    
       
    private void getData(){
        i=tbDokter.getSelectedRow();
        if(i!= -1){   
            if((tbDokter.getSelectedColumn()==1)||(tbDokter.getSelectedColumn()==0)){   
                if(!tbDokter.getValueAt(i,0).toString().equals("")){
                    try {
                        if(Double.parseDouble(tabMode.getValueAt(i,0).toString())>0){
                            try {
                                kurang=Double.parseDouble(tbDokter.getValueAt(i,6).toString())-Double.parseDouble(tbDokter.getValueAt(i,0).toString());
                            } catch (Exception e) {
                                kurang=0;
                            }

                            if(kurang>0){
                                tbDokter.setValueAt(kurang,i,7);  
                                tbDokter.setValueAt(0,i,9); 
                            }else{
                                tbDokter.setValueAt(0,i,7);  
                                tbDokter.setValueAt((kurang*-1),i,9);
                            }

                            if(kurang>0){
                                 tbDokter.setValueAt(kurang*Double.parseDouble(tbDokter.getValueAt(i,5).toString()),i,8);      
                                 tbDokter.setValueAt(0,i,10);
                            }else{                       
                                 tbDokter.setValueAt(0,i,8);   
                                 tbDokter.setValueAt(-1*kurang*Double.parseDouble(tbDokter.getValueAt(i,5).toString()),i,10); 
                            }
                        }
                    } catch (Exception e) {
                        tbDokter.setValueAt("",i,0);
                        tbDokter.setValueAt(0,i,7);
                        tbDokter.setValueAt(0,i,8); 
                        tbDokter.setValueAt(0,i,9); 
                        tbDokter.setValueAt(0,i,10); 
                    }
                }else{
                    tbDokter.setValueAt(0,i,7);
                    tbDokter.setValueAt(0,i,8); 
                    tbDokter.setValueAt(0,i,9); 
                    tbDokter.setValueAt(0,i,10);
                }   
            }              
            
            ttl=0;
            for(i=0;i<tbDokter.getRowCount();i++){ 
                y=0;
                try {
                    y=Double.parseDouble(tbDokter.getValueAt(i,8).toString());     
                } catch (Exception e) {
                    y=0;           
                }
                ttl=ttl+y;
            }
            
            ttl2=0;
            for(i=0;i<tbDokter.getRowCount();i++){ 
                y=0;
                try {
                    y=Double.parseDouble(tbDokter.getValueAt(i,10).toString());     
                } catch (Exception e) {
                    y=0;           
                }
                ttl2=ttl2+y;
            }
            LTotal.setText(Valid.SetAngka(ttl));
            LTotal1.setText(Valid.SetAngka(ttl2));
        }
    }
         
    public void isCek(){
         BtnSimpan.setEnabled(akses.getstok_opname_logistik());   
    }

}

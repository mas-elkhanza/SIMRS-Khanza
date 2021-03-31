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
import simrskhanza.DlgCariBangsal;

public class DlgInputStok extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private riwayatobat Trackobat=new riwayatobat();
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement pstampil,psstok;
    private ResultSet rstampil,rsstok;
    private DlgCariBangsal bangsal=new DlgCariBangsal(null,false);
    private double ttl=0,y=0,ttl2=0,y2=0,stokbarang=0,kurang=0,harga=0;
    private int jml=0,i=0,index=0;
    private String[] real,kodebarang,namabarang,kategori,satuan,nobatch,nofaktur;
    private double[] hargabeli,stok,selisih,lebih,nomihilang,nomilebih;
    private WarnaTable2 warna=new WarnaTable2();
    private boolean aktif=false,sukses=true;
    private String aktifkanbatch="no",hppfarmasi="",order="order by databarang.nama_brng";
    private File file;
    private FileWriter fileWriter;
    private String iyem;
    private ObjectMapper mapper = new ObjectMapper();
    private JsonNode root;
    private JsonNode response;
    private FileReader myObj;

    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public DlgInputStok(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        Object[] row={"Real","Kode Barang","Nama Barang","Kategori","Satuan","Harga","Stok","Selisih","Lebih","Nominal Hilang(Rp)","Nominal Lebih(Rp)","No.Batch","No.Faktur"};
        tabMode=new DefaultTableModel(null,row){
            @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if ((colIndex==0)||(colIndex==11)||(colIndex==12)) {
                    a=true;
                }
                return a;
             }
            Class[] types = new Class[] {
                java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,
                java.lang.String.class,java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,
                java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,java.lang.String.class,
                java.lang.String.class  
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
                column.setPreferredWidth(42);
            }else if(i==9){
                column.setPreferredWidth(105);
            }else if(i==10){
                column.setPreferredWidth(105);
            }else if(i==11){
                column.setPreferredWidth(70);
            }else if(i==12){
                column.setPreferredWidth(100);
            }
        }
        warna.kolom=0;
        tbDokter.setDefaultRenderer(Object.class,warna);

        kdgudang.setDocument(new batasInput((byte)5).getKata(kdgudang));
        catatan.setDocument(new batasInput((byte)60).getKata(catatan));  
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
        
        TCari.requestFocus();
        
        bangsal.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(bangsal.getTable().getSelectedRow()!= -1){                   
                    kdgudang.setText(bangsal.getTable().getValueAt(bangsal.getTable().getSelectedRow(),0).toString());
                    nmgudang.setText(bangsal.getTable().getValueAt(bangsal.getTable().getSelectedRow(),1).toString());
                    tampil2();
                }  
                kdgudang.requestFocus();
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
        
        try {
            aktifkanbatch = koneksiDB.AKTIFKANBATCHOBAT();
        } catch (Exception e) {
            System.out.println("E : "+e);
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
        Popup = new javax.swing.JPopupMenu();
        ppBersihkan = new javax.swing.JMenuItem();
        ppStok = new javax.swing.JMenuItem();
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
        BtnAll = new widget.Button();
        BtnTambah = new widget.Button();
        BtnPrint = new widget.Button();
        panelisi3 = new widget.panelisi();
        label18 = new widget.Label();
        catatan = new widget.TextBox();
        label11 = new widget.Label();
        Tgl = new widget.Tanggal();
        label21 = new widget.Label();
        kdgudang = new widget.TextBox();
        nmgudang = new widget.TextBox();
        BtnGudang = new widget.Button();

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
        ppBersihkan.setPreferredSize(new java.awt.Dimension(200, 26));
        ppBersihkan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppBersihkanActionPerformed(evt);
            }
        });
        Popup.add(ppBersihkan);

        ppStok.setBackground(new java.awt.Color(255, 255, 254));
        ppStok.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppStok.setForeground(new java.awt.Color(50, 50, 50));
        ppStok.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppStok.setText("Tampilkan Semua Stok");
        ppStok.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppStok.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppStok.setName("ppStok"); // NOI18N
        ppStok.setPreferredSize(new java.awt.Dimension(200, 26));
        ppStok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppStokActionPerformed(evt);
            }
        });
        Popup.add(ppStok);

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
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
            public void windowDeactivated(java.awt.event.WindowEvent evt) {
                formWindowDeactivated(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Stok Opname Obat, Alkes & BHP Medis ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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
        panelisi5.setPreferredSize(new java.awt.Dimension(100, 54));
        panelisi5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        label9.setText("Key Word :");
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi5.add(label9);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(425, 23));
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
        panelisi5.add(BtnAll);

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
        panelisi3.setPreferredSize(new java.awt.Dimension(89, 74));
        panelisi3.setLayout(null);

        label18.setText("Keterangan :");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(label18);
        label18.setBounds(0, 40, 110, 23);

        catatan.setName("catatan"); // NOI18N
        catatan.setPreferredSize(new java.awt.Dimension(207, 23));
        catatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                catatanKeyPressed(evt);
            }
        });
        panelisi3.add(catatan);
        catatan.setBounds(114, 40, 528, 23);

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

        label21.setText("Lokasi :");
        label21.setName("label21"); // NOI18N
        label21.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label21);
        label21.setBounds(236, 10, 90, 23);

        kdgudang.setName("kdgudang"); // NOI18N
        kdgudang.setPreferredSize(new java.awt.Dimension(80, 23));
        kdgudang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdgudangKeyPressed(evt);
            }
        });
        panelisi3.add(kdgudang);
        kdgudang.setBounds(329, 10, 80, 23);

        nmgudang.setEditable(false);
        nmgudang.setName("nmgudang"); // NOI18N
        nmgudang.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(nmgudang);
        nmgudang.setBounds(412, 10, 200, 23);

        BtnGudang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnGudang.setMnemonic('2');
        BtnGudang.setToolTipText("Alt+2");
        BtnGudang.setName("BtnGudang"); // NOI18N
        BtnGudang.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnGudang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnGudangActionPerformed(evt);
            }
        });
        panelisi3.add(BtnGudang);
        BtnGudang.setBounds(614, 10, 28, 23);

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
        DlgStokOpname opname=new DlgStokOpname(null,false);
        opname.isCek(); 
        opname.emptTeks();
        opname.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        opname.setLocationRelativeTo(internalFrame1);
        opname.setAlwaysOnTop(false);
        opname.setVisible(true);
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
        if(nmgudang.getText().trim().equals("")||kdgudang.getText().trim().equals("")){
            Valid.textKosong(kdgudang,"Lokasi");
        }else if(catatan.getText().trim().equals("")){
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
                            if(Double.parseDouble(tbDokter.getValueAt(i,0).toString())>=0){
                                if(Sequel.menyimpantf2("opname","?,?,?,?,?,?,?,?,?,?,?,?,?","Stok Opname",13,new String[]{
                                        tbDokter.getValueAt(i,1).toString(),tbDokter.getValueAt(i,5).toString(),Valid.SetTgl(Tgl.getSelectedItem()+""),tbDokter.getValueAt(i,6).toString(),
                                        tbDokter.getValueAt(i,0).toString(),tbDokter.getValueAt(i,7).toString(),tbDokter.getValueAt(i,9).toString(),
                                        tbDokter.getValueAt(i,8).toString(),tbDokter.getValueAt(i,10).toString(),catatan.getText(),
                                        kdgudang.getText(),tbDokter.getValueAt(i,11).toString(),tbDokter.getValueAt(i,12).toString()})==true){
                                    if(aktifkanbatch.equals("yes")){
                                        Trackobat.catatRiwayat(tbDokter.getValueAt(i,1).toString(),Valid.SetAngka(tbDokter.getValueAt(i,0).toString()),0,"Opname",akses.getkode(),kdgudang.getText(),"Simpan",tbDokter.getValueAt(i,11).toString(),tbDokter.getValueAt(i,12).toString());
                                        Sequel.menyimpan("gudangbarang","'"+tbDokter.getValueAt(i,1).toString()+"','"+kdgudang.getText()+"','"+tbDokter.getValueAt(i,0).toString()+"','"+tbDokter.getValueAt(i,11).toString()+"','"+tbDokter.getValueAt(i,12).toString()+"'", 
                                                         "stok='"+tbDokter.getValueAt(i,0).toString()+"'","kode_brng='"+tbDokter.getValueAt(i,1).toString()+"' and kd_bangsal='"+kdgudang.getText()+"' and no_batch='"+tbDokter.getValueAt(i,11).toString()+"' and no_faktur='"+tbDokter.getValueAt(i,12).toString()+"'");    
                                    }else{
                                        Trackobat.catatRiwayat(tbDokter.getValueAt(i,1).toString(),Valid.SetAngka(tbDokter.getValueAt(i,0).toString()),0,"Opname",akses.getkode(),kdgudang.getText(),"Simpan","","");
                                        Sequel.menyimpan("gudangbarang","'"+tbDokter.getValueAt(i,1).toString()+"','"+kdgudang.getText()+"','"+tbDokter.getValueAt(i,0).toString()+"','',''", 
                                                         "stok='"+tbDokter.getValueAt(i,0).toString()+"'","kode_brng='"+tbDokter.getValueAt(i,1).toString()+"' and kd_bangsal='"+kdgudang.getText()+"' and no_batch='' and no_faktur=''");    
                                    }                                                                          
                                }else{
                                    sukses=false;
                                    JOptionPane.showMessageDialog(null,tbDokter.getValueAt(i,1).toString()+" "+tbDokter.getValueAt(i,2).toString()+" gagal disimpan...!");
                                }
                            }
                        } catch (Exception e) {
                            sukses=false;
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
                        tbDokter.setValueAt(0,index,6);  
                        tbDokter.setValueAt(0,index,7);  
                        tbDokter.setValueAt(0,index,8);  
                        tbDokter.setValueAt(0,index,9);
                        tbDokter.setValueAt(0,index,10);    
                    }
                    ttl=0;
                    LTotal.setText("0");
                }
            }
            
        }
    }//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
           Valid.pindah(evt,kdgudang,BtnCari);
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
            tampil2();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari1.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
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
            Valid.pindah(evt, TCari, BtnSimpan);
        }
}//GEN-LAST:event_BtnCari1KeyPressed

private void catatanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_catatanKeyPressed
        Valid.pindah(evt, kdgudang,BtnSimpan);
}//GEN-LAST:event_catatanKeyPressed

private void TglKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglKeyPressed
        Valid.pindah(evt,TCari,kdgudang);
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

private void kdgudangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdgudangKeyPressed
    if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
        Sequel.cariIsi("select bangsal.nm_bangsal from bangsal where bangsal.kd_bangsal=?",nmgudang,kdgudang.getText());
    }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
        Sequel.cariIsi("select bangsal.nm_bangsal from bangsal where bangsal.kd_bangsal=?",nmgudang,kdgudang.getText());
        Tgl.requestFocus();
    }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
        Sequel.cariIsi("select bangsal.nm_bangsal from bangsal where bangsal.kd_bangsal=?",nmgudang,kdgudang.getText());
        BtnSimpan.requestFocus();
    }else if(evt.getKeyCode()==KeyEvent.VK_UP){
        BtnGudangActionPerformed(null);
    }
}//GEN-LAST:event_kdgudangKeyPressed

private void BtnGudangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnGudangActionPerformed
    bangsal.isCek();
    bangsal.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
    bangsal.setLocationRelativeTo(internalFrame1);
    bangsal.setAlwaysOnTop(false);
    bangsal.setVisible(true);
}//GEN-LAST:event_BtnGudangActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void ppStokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppStokActionPerformed
        if(nmgudang.getText().trim().equals("")||kdgudang.getText().trim().equals("")){
            Valid.textKosong(kdgudang,"Lokasi");
        }else{
            for(i=0;i<tbDokter.getRowCount();i++){
                try {
                    stokbarang=0;
                    if(aktifkanbatch.equals("yes")){
                        psstok=koneksi.prepareStatement("select ifnull(stok,'0') from gudangbarang where kd_bangsal=? and kode_brng=? and no_batch=? and no_faktur=?");
                        try {
                            psstok.setString(1,kdgudang.getText());
                            psstok.setString(2,tbDokter.getValueAt(i,1).toString());
                            psstok.setString(3,tbDokter.getValueAt(i,11).toString());
                            psstok.setString(4,tbDokter.getValueAt(i,12).toString());
                            rsstok=psstok.executeQuery();
                            if(rsstok.next()){
                                stokbarang=rsstok.getDouble(1);
                            }
                        } catch (Exception e) {
                            tbDokter.setValueAt(0,i,6);
                        } finally{
                            if(rsstok!=null){
                                rsstok.close();
                            }
                            if(psstok!=null){
                                psstok.close();
                            }
                        }
                    }else{
                        psstok=koneksi.prepareStatement("select ifnull(stok,'0') from gudangbarang where kd_bangsal=? and kode_brng=? and no_batch='' and no_faktur=''");
                        try {
                            psstok.setString(1,kdgudang.getText());
                            psstok.setString(2,tbDokter.getValueAt(i,1).toString());
                            rsstok=psstok.executeQuery();
                            if(rsstok.next()){
                                stokbarang=rsstok.getDouble(1);
                            }
                        } catch (Exception e) {
                            tbDokter.setValueAt(0,i,6);
                        } finally{
                            if(rsstok!=null){
                                rsstok.close();
                            }
                            if(psstok!=null){
                                psstok.close();
                            }
                        }
                    }
                        
                    tbDokter.setValueAt(stokbarang,i,6);
                } catch (Exception e) {
                    tbDokter.setValueAt(0,i,6);
                }
            }
        }   
    }//GEN-LAST:event_ppStokActionPerformed

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
        if(aktif==true){
            if(tbDokter.getSelectedRow()!= -1){
                getData();  
            }
        }
    }//GEN-LAST:event_tbDokterPropertyChange

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        aktif=true;
    }//GEN-LAST:event_formWindowActivated

    private void formWindowDeactivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowDeactivated
        aktif=false;
    }//GEN-LAST:event_formWindowDeactivated

    private void ppBelumOpnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBelumOpnameActionPerformed
        if(nmgudang.getText().trim().equals("")||kdgudang.getText().trim().equals("")){
            Valid.textKosong(kdgudang,"Lokasi");
        }else{
            try{  
                Valid.tabelKosong(tabMode);
                pstampil=koneksi.prepareStatement("select databarang.kode_brng, databarang.nama_brng,jenis.nama, databarang.kode_sat, "+
                        "databarang."+hppfarmasi+" as dasar from databarang inner join jenis on databarang.kdjns=jenis.kdjns "+
                        " where databarang.kode_brng not in (select kode_brng from opname where tanggal=? and kd_bangsal=?) and databarang.status='1' and databarang.kode_brng like ? or "+
                        " databarang.kode_brng not in (select kode_brng from opname where tanggal=? and kd_bangsal=?) and databarang.status='1' and databarang.nama_brng like ? or "+
                        " databarang.kode_brng not in (select kode_brng from opname where tanggal=? and kd_bangsal=?) and databarang.status='1' and databarang.kode_sat like ? or "+
                        " databarang.kode_brng not in (select kode_brng from opname where tanggal=? and kd_bangsal=?) and databarang.status='1' and jenis.nama like ? "+order);
                try {
                    pstampil.setString(1,Valid.SetTgl(Tgl.getSelectedItem()+""));
                    pstampil.setString(2,kdgudang.getText());
                    pstampil.setString(3,"%"+TCari.getText().trim()+"%");
                    pstampil.setString(4,Valid.SetTgl(Tgl.getSelectedItem()+""));
                    pstampil.setString(5,kdgudang.getText());
                    pstampil.setString(6,"%"+TCari.getText().trim()+"%");
                    pstampil.setString(7,Valid.SetTgl(Tgl.getSelectedItem()+""));
                    pstampil.setString(8,kdgudang.getText());
                    pstampil.setString(9,"%"+TCari.getText().trim()+"%");
                    pstampil.setString(10,Valid.SetTgl(Tgl.getSelectedItem()+""));
                    pstampil.setString(11,kdgudang.getText());
                    pstampil.setString(12,"%"+TCari.getText().trim()+"%");
                    rstampil=pstampil.executeQuery();
                    while(rstampil.next()){                            
                        tabMode.addRow(new Object[]{"",rstampil.getString("kode_brng"),
                                       rstampil.getString("nama_brng"),
                                       rstampil.getString("nama"),
                                       rstampil.getString("kode_sat"),
                                       rstampil.getDouble("dasar"),0,0,0,0,0,"",""});
                    }  
                } catch (Exception e) {
                    System.out.println("Notif : "+e);
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
        }
    }//GEN-LAST:event_ppBelumOpnameActionPerformed

    private void ppSudahOpnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppSudahOpnameActionPerformed
        try{  
            Valid.tabelKosong(tabMode);
            pstampil=koneksi.prepareStatement("select databarang.kode_brng, databarang.nama_brng,jenis.nama, databarang.kode_sat, "+
                    "databarang."+hppfarmasi+" as dasar from databarang inner join jenis on databarang.kdjns=jenis.kdjns "+
                    " where databarang.kode_brng in (select kode_brng from opname where tanggal=? and kd_bangsal=?) and databarang.status='1' and databarang.kode_brng like ? or "+
                    " databarang.kode_brng in (select kode_brng from opname where tanggal=? and kd_bangsal=?) and databarang.status='1' and databarang.nama_brng like ? or "+
                    " databarang.kode_brng in (select kode_brng from opname where tanggal=? and kd_bangsal=?) and databarang.status='1' and databarang.kode_sat like ? or "+
                    " databarang.kode_brng in (select kode_brng from opname where tanggal=? and kd_bangsal=?) and databarang.status='1' and jenis.nama like ? "+order);
            try {
                pstampil.setString(1,Valid.SetTgl(Tgl.getSelectedItem()+""));
                pstampil.setString(2,kdgudang.getText());
                pstampil.setString(3,"%"+TCari.getText().trim()+"%");
                pstampil.setString(4,Valid.SetTgl(Tgl.getSelectedItem()+""));
                pstampil.setString(5,kdgudang.getText());
                pstampil.setString(6,"%"+TCari.getText().trim()+"%");
                pstampil.setString(7,Valid.SetTgl(Tgl.getSelectedItem()+""));
                pstampil.setString(8,kdgudang.getText());
                pstampil.setString(9,"%"+TCari.getText().trim()+"%");
                pstampil.setString(10,Valid.SetTgl(Tgl.getSelectedItem()+""));
                pstampil.setString(11,kdgudang.getText());
                pstampil.setString(12,"%"+TCari.getText().trim()+"%");
                rstampil=pstampil.executeQuery();
                while(rstampil.next()){                            
                    tabMode.addRow(new Object[]{"",rstampil.getString("kode_brng"),
                                   rstampil.getString("nama_brng"),
                                   rstampil.getString("nama"),
                                   rstampil.getString("kode_sat"),
                                   rstampil.getDouble("dasar"),0,0,0,0,0,"",""});
                }  
            } catch (Exception e) {
                System.out.println("Notif : "+e);
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

    private void BtnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgBarang barang=new DlgBarang(null,false);
        barang.emptTeks();
        barang.isCek();
        barang.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        barang.setLocationRelativeTo(internalFrame1);
        barang.setAlwaysOnTop(false);
        barang.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnTambahActionPerformed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        if(nmgudang.getText().trim().equals("")||kdgudang.getText().trim().equals("")){
            Valid.textKosong(kdgudang,"Lokasi");
        }else if(tbDokter.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data kosong..!!!!");
            tbDokter.requestFocus();
        }else{
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
                                    tabMode.getValueAt(i,6).toString()+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Transaksi Penerimaan"); 
                }

                Map<String, Object> param = new HashMap<>();    
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("emailrs",akses.getemailrs());  
                param.put("bangsal",nmgudang.getText());    
                param.put("logo",Sequel.cariGambar("select logo from setting")); 
                Valid.MyReport("rptInputStokOpname.jasper","report","::[ Data Persediaan Stok Ruangan ]::",param);
            }
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnCari, BtnKeluar);
        }
    }//GEN-LAST:event_BtnPrintKeyPressed

    private void MnKodeBarangDescActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnKodeBarangDescActionPerformed
        order="order by databarang.kode_brng desc";
        tampil();
        tampil2();
    }//GEN-LAST:event_MnKodeBarangDescActionPerformed

    private void MnKodeBarangAscActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnKodeBarangAscActionPerformed
        order="order by databarang.kode_brng asc";
        tampil();
        tampil2();
    }//GEN-LAST:event_MnKodeBarangAscActionPerformed

    private void MnNamaBarangDescActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnNamaBarangDescActionPerformed
        order="order by databarang.nama_brng desc";
        tampil();
        tampil2();
    }//GEN-LAST:event_MnNamaBarangDescActionPerformed

    private void MnNamaBarangAscActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnNamaBarangAscActionPerformed
        order="order by databarang.nama_brng asc";
        tampil();
        tampil2();
    }//GEN-LAST:event_MnNamaBarangAscActionPerformed

    private void MnKategoriAscActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnKategoriAscActionPerformed
        order="order by jenis.nama desc";
        tampil();
        tampil2();
    }//GEN-LAST:event_MnKategoriAscActionPerformed

    private void MnKategoriDescActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnKategoriDescActionPerformed
        order="order by jenis.nama asc";
        tampil();
        tampil2();
    }//GEN-LAST:event_MnKategoriDescActionPerformed

    private void MnSatuanDescActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnSatuanDescActionPerformed
        order="order by databarang.kode_sat desc";
        tampil();
        tampil2();
    }//GEN-LAST:event_MnSatuanDescActionPerformed

    private void MnSatuanAscActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnSatuanAscActionPerformed
        order="order by databarang.kode_sat asc";
        tampil();
        tampil2();
    }//GEN-LAST:event_MnSatuanAscActionPerformed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        tampil();
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnCari, TCari);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgInputStok dialog = new DlgInputStok(new javax.swing.JFrame(), true);
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
    private widget.Button BtnGudang;
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
    private widget.TextBox kdgudang;
    private widget.Label label10;
    private widget.Label label11;
    private widget.Label label12;
    private widget.Label label18;
    private widget.Label label21;
    private widget.Label label9;
    private widget.TextBox nmgudang;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi3;
    private widget.panelisi panelisi5;
    private javax.swing.JMenuItem ppBelumOpname;
    private javax.swing.JMenuItem ppBersihkan;
    private javax.swing.JMenuItem ppStok;
    private javax.swing.JMenuItem ppSudahOpname;
    private widget.ScrollPane scrollPane1;
    private widget.Table tbDokter;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        try{  
            Valid.tabelKosong(tabMode);
            file=new File("./cache/stokopname.iyem");
            file.createNewFile();
            fileWriter = new FileWriter(file);
            iyem="";
            pstampil=koneksi.prepareStatement("select databarang.kode_brng, databarang.nama_brng,jenis.nama, databarang.kode_sat, "+
                "databarang."+hppfarmasi+" as dasar from databarang inner join jenis on databarang.kdjns=jenis.kdjns "+
                " where databarang.status='1' "+order);
            try {
                rstampil=pstampil.executeQuery();
                while(rstampil.next()){                            
                    tabMode.addRow(new Object[]{
                        "",rstampil.getString("kode_brng"),rstampil.getString("nama_brng"),rstampil.getString("nama"),rstampil.getString("kode_sat"),rstampil.getDouble("dasar"),0,0,0,0,0,"",""
                    });
                    iyem=iyem+"{\"KodeBarang\":\""+rstampil.getString("kode_brng")+"\",\"NamaBarang\":\""+rstampil.getString("nama_brng").replaceAll("\"","")+"\",\"Kategori\":\""+rstampil.getString("nama")+"\",\"Satuan\":\""+rstampil.getString("kode_sat")+"\",\"Harga\":\""+rstampil.getString("dasar")+"\"},";
                }  
            } catch (Exception e) {
                System.out.println("Notif : "+e);
            } finally{
                if(rstampil!=null){
                    rstampil.close();
                }
                if(pstampil!=null){
                    pstampil.close();
                }
            }      
            fileWriter.write("{\"stokopname\":["+iyem.substring(0,iyem.length()-1)+"]}");
            fileWriter.flush();
            fileWriter.close();
            iyem=null;        
        }catch(Exception e){
            if(e.toString().contains("begin")){
                System.out.println("Notifikasi : Data tidak ditemukan..!!");
            }else{
                System.out.println("Notifikasi : "+e);
            }
        }
        
    }
    
    private void tampil2() {
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
            lebih=new double[jml];
            nomihilang=new double[jml];
            nomilebih=new double[jml];
            nobatch=new String[jml];
            nofaktur=new String[jml];

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
                    lebih[index]=Double.parseDouble(tbDokter.getValueAt(i,8).toString());
                    nomihilang[index]=Double.parseDouble(tbDokter.getValueAt(i,9).toString());
                    nomilebih[index]=Double.parseDouble(tbDokter.getValueAt(i,10).toString());
                    nobatch[index]=tbDokter.getValueAt(i,11).toString();
                    nofaktur[index]=tbDokter.getValueAt(i,12).toString();
                    index++;
                }
            }

            Valid.tabelKosong(tabMode);
            for(i=0;i<jml;i++){
                tabMode.addRow(new Object[]{
                    real[i],kodebarang[i],namabarang[i],kategori[i],satuan[i],
                    hargabeli[i],stok[i],selisih[i],lebih[i],nomihilang[i],nomilebih[i],
                    nobatch[i],nofaktur[i]
                });
            }
            
            myObj = new FileReader("./cache/stokopname.iyem");
            root = mapper.readTree(myObj);
            response = root.path("stokopname");
            if(response.isArray()){
                for(JsonNode list:response){
                    if(list.path("KodeBarang").asText().toLowerCase().contains(TCari.getText().toLowerCase())||list.path("NamaBarang").asText().toLowerCase().contains(TCari.getText().toLowerCase())||list.path("Kategori").asText().toLowerCase().contains(TCari.getText().toLowerCase())){
                        tabMode.addRow(new Object[]{
                            "",list.path("KodeBarang").asText(),list.path("NamaBarang").asText(),list.path("Kategori").asText(),list.path("Satuan").asText(),list.path("Harga").asDouble(),0,0,0,0,0,"",""
                        });
                    }
                }
            }
            myObj.close();
        }catch(Exception e){
            if(e.toString().contains("begin")){
                System.out.println("Notifikasi : Data tidak ditemukan..!!");
            }else{
                System.out.println("Notifikasi : "+e);
            }
        }
        
    }
    
       
    private void getData(){
        i=tbDokter.getSelectedRow();
        if(nmgudang.getText().trim().equals("")){
            for(index=0;index<tbDokter.getRowCount();index++){   
                tbDokter.setValueAt("",index,0);        
            }
        }else if(i!= -1){   
            if(!tbDokter.getValueAt(i,0).toString().equals("")){
                try {
                    if(Double.parseDouble(tabMode.getValueAt(i,0).toString())>=0){
                        stokbarang=0;   
                        if(aktifkanbatch.equals("yes")){
                            psstok=koneksi.prepareStatement("select ifnull(stok,'0') from gudangbarang where kd_bangsal=? and kode_brng=? and no_batch=? and no_faktur=?");
                            try{
                                psstok.setString(1,kdgudang.getText());
                                psstok.setString(2,tbDokter.getValueAt(i,1).toString());
                                psstok.setString(3,tbDokter.getValueAt(i,11).toString());
                                psstok.setString(4,tbDokter.getValueAt(i,12).toString());
                                rsstok=psstok.executeQuery();
                                if(rsstok.next()){
                                     stokbarang=rsstok.getDouble(1);
                                }
                            }catch (Exception e) {
                                tbDokter.setValueAt(0,i,6);
                            } finally{
                                if(rsstok!=null){
                                    rsstok.close();
                                }
                                if(psstok!=null){
                                    psstok.close();
                                }
                            }
                        }else{
                            psstok=koneksi.prepareStatement("select ifnull(stok,'0') from gudangbarang where kd_bangsal=? and kode_brng=? and no_batch='' and no_faktur=''");
                            try{
                                psstok.setString(1,kdgudang.getText());
                                psstok.setString(2,tbDokter.getValueAt(i,1).toString());
                                rsstok=psstok.executeQuery();
                                if(rsstok.next()){
                                     stokbarang=rsstok.getDouble(1);
                                }
                            }catch (Exception e) {
                                tbDokter.setValueAt(0,i,6);
                            } finally{
                                if(rsstok!=null){
                                    rsstok.close();
                                }
                                if(psstok!=null){
                                    psstok.close();
                                }
                            }
                        }
                            
                        tbDokter.setValueAt(stokbarang,i,6);

                        harga=Sequel.cariIsiAngka("select "+hppfarmasi+" as dasar from data_batch where kode_brng='"+tbDokter.getValueAt(i,1).toString()+"' and no_batch='"+tbDokter.getValueAt(i,11).toString()+"' and no_faktur='"+tbDokter.getValueAt(i,12).toString()+"'");
                        if(harga>0){
                            tbDokter.setValueAt(harga,i,5);
                        }

                        try {
                            kurang=Double.parseDouble(tbDokter.getValueAt(i,6).toString())-Double.parseDouble(tbDokter.getValueAt(i,0).toString());
                        } catch (Exception e) {
                            kurang=0;
                        }

                        if(kurang>0){
                            tbDokter.setValueAt(kurang,i,7); 
                            tbDokter.setValueAt(0,i,8); 
                        }else{
                            tbDokter.setValueAt(0,i,7);  
                            tbDokter.setValueAt((kurang*-1),i,8); 
                        }

                        if(kurang>0){
                             tbDokter.setValueAt(kurang*Double.parseDouble(tbDokter.getValueAt(i,5).toString()),i,9);     
                             tbDokter.setValueAt(0,i,10); 
                        }else{                       
                             tbDokter.setValueAt(0,i,9);  
                             tbDokter.setValueAt(-1*kurang*Double.parseDouble(tbDokter.getValueAt(i,5).toString()),i,10);  
                        }
                    }
                } catch (Exception e) {
                    tbDokter.setValueAt(0,i,6);  
                    tbDokter.setValueAt(0,i,7);  
                    tbDokter.setValueAt(0,i,8); 
                    tbDokter.setValueAt(0,i,9); 
                    tbDokter.setValueAt(0,i,10); 
                    tbDokter.setValueAt("",i,0); 
                }
            }else{
                tbDokter.setValueAt(0,i,6);  
                tbDokter.setValueAt(0,i,7);  
                tbDokter.setValueAt(0,i,8); 
                tbDokter.setValueAt(0,i,9); 
                tbDokter.setValueAt(0,i,10); 
            }

            
            ttl=0;
            ttl2=0;
            for(i=0;i<tbDokter.getRowCount();i++){ 
                y=0;
                try {
                    y=Double.parseDouble(tbDokter.getValueAt(i,9).toString());
                } catch (Exception e) {
                    y=0;           
                }
                ttl=ttl+y;
                
                y2=0;
                try {
                    y2=Double.parseDouble(tbDokter.getValueAt(i,10).toString());
                } catch (Exception e) {
                    y2=0;           
                }
                ttl2=ttl2+y2;
            }
            LTotal.setText(Valid.SetAngka(ttl));
            LTotal1.setText(Valid.SetAngka(ttl2));
        }
    }
         
    public void isCek(){
         BtnSimpan.setEnabled(akses.getstok_opname_obat());   
    }

}

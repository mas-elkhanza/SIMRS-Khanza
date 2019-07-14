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

package setting;

import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.WarnaTable;
import inventory.DlgBarang;
import inventory.DlgCariJenis;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author perpustakaan
 */
public class DlgSetHarga extends javax.swing.JDialog {
    private final DefaultTableModel tabMode,tabModePengaturanUmum,tabModePengaturanHargaUmum,tabModePengaturanHargaPerBarang;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    public DlgCariJenis jenis = new DlgCariJenis(null, false);
    private PreparedStatement ps;
    private ResultSet rs;
    private DlgBarang barang=new DlgBarang(null,false);

    /** Creates new form DlgAdmin 
     *@param parent
     *@param modal*/
    public DlgSetHarga(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        tabMode=new DefaultTableModel(null,new Object[]{
              "Ralan(%)","Ranap K1(%)","Ranap K2(%)","Ranap K3(%)","Kelas Utama(%)",
              "Ranap VIP(%)","Ranap VVIP(%)","Beli Luar(%)","Jual Bebas(%)","Karyawan(%)",
              "Kode Jenis","Nama Jenis"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };

        tbAdmin.setModel(tabMode);
        tbAdmin.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbAdmin.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 12; i++) {
            TableColumn column = tbAdmin.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(70);
            }else{
                column.setPreferredWidth(90);
            }
        }

        tbAdmin.setDefaultRenderer(Object.class, new WarnaTable());

        tabModePengaturanUmum=new DefaultTableModel(null,new Object[]{
              "Pengaturan harga obat yang digunakan","Penggunaan harga dasar obat"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };

        tbPengaturanUmum.setModel(tabModePengaturanUmum);
        tbPengaturanUmum.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbPengaturanUmum.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 2; i++) {
            TableColumn column = tbPengaturanUmum.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(300);
            }else if(i==1){
                column.setPreferredWidth(250);
            }
        }

        tbPengaturanUmum.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModePengaturanHargaUmum=new DefaultTableModel(null,new Object[]{
              "Ralan(%)","Ranap K1(%)","Ranap K2(%)","Ranap K3(%)","Kelas Utama(%)",
              "Ranap VIP(%)","Ranap VVIP(%)","Beli Luar(%)","Jual Bebas(%)","Karyawan(%)",
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };

        tbPengaturanHargaUmum.setModel(tabModePengaturanHargaUmum);
        tbPengaturanHargaUmum.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbPengaturanHargaUmum.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 10; i++) {
            TableColumn column = tbPengaturanHargaUmum.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(70);
            }else{
                column.setPreferredWidth(90);
            }
        }

        tbPengaturanHargaUmum.setDefaultRenderer(Object.class, new WarnaTable());

        tabModePengaturanHargaPerBarang=new DefaultTableModel(null,new Object[]{
              "Ralan(%)","Ranap K1(%)","Ranap K2(%)","Ranap K3(%)","Kelas Utama(%)",
              "Ranap VIP(%)","Ranap VVIP(%)","Beli Luar(%)","Jual Bebas(%)","Karyawan(%)",
              "Kode Barang","Nama Barang"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };

        tbPengaturanHargaPerBarang.setModel(tabModePengaturanHargaPerBarang);
        tbPengaturanHargaPerBarang.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbPengaturanHargaPerBarang.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 12; i++) {
            TableColumn column = tbPengaturanHargaPerBarang.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(70);
            }else if(i==10){
                column.setPreferredWidth(90);
            }else if(i==11){
                column.setPreferredWidth(200);
            }else{
                column.setPreferredWidth(90);
            }
        }

        tbPengaturanHargaPerBarang.setDefaultRenderer(Object.class, new WarnaTable());
        ralan.setDocument(new batasInput((byte)10).getKata(ralan));
        ranapk1.setDocument(new batasInput((byte)10).getKata(ranapk1));
        ranapk2.setDocument(new batasInput((byte)10).getKata(ranapk2));
        ranapk3.setDocument(new batasInput((byte)10).getKata(ranapk3));
        ranaputama.setDocument(new batasInput((byte)10).getKata(ranaputama));
        ranapvip.setDocument(new batasInput((byte)10).getKata(ranapvip));
        ranapvvip.setDocument(new batasInput((byte)10).getKata(ranapvvip));
        beliluar.setDocument(new batasInput((byte)10).getKata(beliluar));
        jualbebas.setDocument(new batasInput((byte)10).getKata(jualbebas));
        karyawan.setDocument(new batasInput((byte)10).getKata(karyawan));
        kdjns.setDocument(new batasInput((byte)4).getKata(kdjns));
        TCari.setDocument(new batasInput((int)100).getKata(TCari));
        TCari1.setDocument(new batasInput((int)100).getKata(TCari1));
        ralanumum.setDocument(new batasInput((byte)10).getKata(ralanumum));
        ranapk1umum.setDocument(new batasInput((byte)10).getKata(ranapk1umum));
        ranapk2umum.setDocument(new batasInput((byte)10).getKata(ranapk2umum));
        ranapk3umum.setDocument(new batasInput((byte)10).getKata(ranapk3umum));
        ranaputamaumum.setDocument(new batasInput((byte)10).getKata(ranaputamaumum));
        ranapvipumum.setDocument(new batasInput((byte)10).getKata(ranapvipumum));
        ranapvvipumum.setDocument(new batasInput((byte)10).getKata(ranapvvipumum));
        beliluarumum.setDocument(new batasInput((byte)10).getKata(beliluarumum));
        jualbebasumum.setDocument(new batasInput((byte)10).getKata(jualbebasumum));
        ralanperbarang.setDocument(new batasInput((byte)10).getKata(ralanperbarang));
        ranapk1perbarang.setDocument(new batasInput((byte)10).getKata(ranapk1perbarang));
        ranapk2perbarang.setDocument(new batasInput((byte)10).getKata(ranapk2perbarang));
        ranapk3perbarang.setDocument(new batasInput((byte)10).getKata(ranapk3perbarang));
        ranaputamaperbarang.setDocument(new batasInput((byte)10).getKata(ranaputamaperbarang));
        ranapvipperbarang.setDocument(new batasInput((byte)10).getKata(ranapvipperbarang));
        ranapvvipperbarang.setDocument(new batasInput((byte)10).getKata(ranapvvipperbarang));
        beliluarperbarang.setDocument(new batasInput((byte)10).getKata(beliluarperbarang));
        jualbebasperbarang.setDocument(new batasInput((byte)10).getKata(jualbebasperbarang));        
        
        jenis.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}

            @Override
            public void windowClosed(WindowEvent e) {
                if (jenis.getTable().getSelectedRow() != -1) {
                    kdjns.setText(jenis.getTable().getValueAt(jenis.getTable().getSelectedRow(), 0).toString());
                    nmjns.setText(jenis.getTable().getValueAt(jenis.getTable().getSelectedRow(), 1).toString());
                }
                kdjns.requestFocus();
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
        
        barang.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(barang.getTable().getSelectedRow()!= -1){                   
                    kdbarang.setText(barang.getTable().getValueAt(barang.getTable().getSelectedRow(),1).toString());                    
                    nmbarang.setText(barang.getTable().getValueAt(barang.getTable().getSelectedRow(),2).toString());
                }     
                kdbarang.requestFocus();
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
        
        barang.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    barang.dispose();
                }  
            }
            @Override
            public void keyReleased(KeyEvent e) {}
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

        Popup = new javax.swing.JPopupMenu();
        ppUPdate = new javax.swing.JMenuItem();
        Popup1 = new javax.swing.JPopupMenu();
        ppUPdate1 = new javax.swing.JMenuItem();
        Popup2 = new javax.swing.JPopupMenu();
        ppUPdate2 = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        panelisi1 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        BtnKeluar = new widget.Button();
        TabSetting = new javax.swing.JTabbedPane();
        internalFrame3 = new widget.InternalFrame();
        Scroll2 = new widget.ScrollPane();
        tbPengaturanUmum = new widget.Table();
        panelGlass8 = new widget.panelisi();
        jLabel5 = new widget.Label();
        cmbPengaturan = new widget.ComboBox();
        jLabel6 = new widget.Label();
        cmbHargaDasar = new widget.ComboBox();
        internalFrame4 = new widget.InternalFrame();
        panelisi3 = new widget.panelisi();
        label44 = new widget.Label();
        ranapvipumum = new widget.TextBox();
        label45 = new widget.Label();
        ranapvvipumum = new widget.TextBox();
        label46 = new widget.Label();
        beliluarumum = new widget.TextBox();
        label47 = new widget.Label();
        jualbebasumum = new widget.TextBox();
        karyawanumum = new widget.TextBox();
        label48 = new widget.Label();
        label49 = new widget.Label();
        ralanumum = new widget.TextBox();
        ranapk1umum = new widget.TextBox();
        label50 = new widget.Label();
        label51 = new widget.Label();
        ranapk2umum = new widget.TextBox();
        ranapk3umum = new widget.TextBox();
        label52 = new widget.Label();
        label53 = new widget.Label();
        ranaputamaumum = new widget.TextBox();
        Scroll1 = new widget.ScrollPane();
        tbPengaturanHargaUmum = new widget.Table();
        internalFrame2 = new widget.InternalFrame();
        panelisi2 = new widget.panelisi();
        label35 = new widget.Label();
        ranapvip = new widget.TextBox();
        label34 = new widget.Label();
        ranapvvip = new widget.TextBox();
        label36 = new widget.Label();
        beliluar = new widget.TextBox();
        label38 = new widget.Label();
        jualbebas = new widget.TextBox();
        karyawan = new widget.TextBox();
        label39 = new widget.Label();
        label37 = new widget.Label();
        ralan = new widget.TextBox();
        ranapk1 = new widget.TextBox();
        label40 = new widget.Label();
        label41 = new widget.Label();
        ranapk2 = new widget.TextBox();
        ranapk3 = new widget.TextBox();
        label42 = new widget.Label();
        label43 = new widget.Label();
        ranaputama = new widget.TextBox();
        label20 = new widget.Label();
        kdjns = new widget.TextBox();
        nmjns = new widget.TextBox();
        BtnJenis = new widget.Button();
        Scroll = new widget.ScrollPane();
        tbAdmin = new widget.Table();
        panelisi5 = new widget.panelisi();
        label9 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        label10 = new widget.Label();
        LCount = new widget.Label();
        internalFrame5 = new widget.InternalFrame();
        panelisi4 = new widget.panelisi();
        label54 = new widget.Label();
        ranapvipperbarang = new widget.TextBox();
        label55 = new widget.Label();
        ranapvvipperbarang = new widget.TextBox();
        label56 = new widget.Label();
        beliluarperbarang = new widget.TextBox();
        label57 = new widget.Label();
        jualbebasperbarang = new widget.TextBox();
        karyawanperbarang = new widget.TextBox();
        label58 = new widget.Label();
        label59 = new widget.Label();
        ralanperbarang = new widget.TextBox();
        ranapk1perbarang = new widget.TextBox();
        label60 = new widget.Label();
        label61 = new widget.Label();
        ranapk2perbarang = new widget.TextBox();
        ranapk3perbarang = new widget.TextBox();
        label62 = new widget.Label();
        label63 = new widget.Label();
        ranaputamaperbarang = new widget.TextBox();
        label21 = new widget.Label();
        kdbarang = new widget.TextBox();
        nmbarang = new widget.TextBox();
        Btnbarang = new widget.Button();
        Scroll3 = new widget.ScrollPane();
        tbPengaturanHargaPerBarang = new widget.Table();
        panelisi6 = new widget.panelisi();
        label11 = new widget.Label();
        TCari1 = new widget.TextBox();
        BtnCari1 = new widget.Button();
        BtnAll1 = new widget.Button();
        label12 = new widget.Label();
        LCount1 = new widget.Label();

        Popup.setName("Popup"); // NOI18N

        ppUPdate.setBackground(new java.awt.Color(255, 255, 254));
        ppUPdate.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppUPdate.setForeground(new java.awt.Color(70, 70, 70));
        ppUPdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppUPdate.setText("Update Seluruh Harga Data Obat/Barang/Alkes");
        ppUPdate.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppUPdate.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppUPdate.setName("ppUPdate"); // NOI18N
        ppUPdate.setPreferredSize(new java.awt.Dimension(300, 25));
        ppUPdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppUPdateActionPerformed(evt);
            }
        });
        Popup.add(ppUPdate);

        Popup1.setName("Popup1"); // NOI18N

        ppUPdate1.setBackground(new java.awt.Color(255, 255, 254));
        ppUPdate1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppUPdate1.setForeground(new java.awt.Color(70, 70, 70));
        ppUPdate1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppUPdate1.setText("Update Seluruh Harga Data Obat/Barang/Alkes");
        ppUPdate1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppUPdate1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppUPdate1.setName("ppUPdate1"); // NOI18N
        ppUPdate1.setPreferredSize(new java.awt.Dimension(300, 25));
        ppUPdate1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppUPdate1ActionPerformed(evt);
            }
        });
        Popup1.add(ppUPdate1);

        Popup2.setName("Popup2"); // NOI18N

        ppUPdate2.setBackground(new java.awt.Color(255, 255, 254));
        ppUPdate2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppUPdate2.setForeground(new java.awt.Color(70, 70, 70));
        ppUPdate2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppUPdate2.setText("Update Seluruh Harga Data Obat/Barang/Alkes");
        ppUPdate2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppUPdate2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppUPdate2.setName("ppUPdate2"); // NOI18N
        ppUPdate2.setPreferredSize(new java.awt.Dimension(300, 25));
        ppUPdate2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppUPdate2ActionPerformed(evt);
            }
        });
        Popup2.add(ppUPdate2);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Setup Harga Obat/Barang/Alkes ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(70, 70, 70))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 54));
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

        BtnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Cancel-2-16x16.png"))); // NOI18N
        BtnBatal.setMnemonic('B');
        BtnBatal.setText("Baru");
        BtnBatal.setToolTipText("Alt+B");
        BtnBatal.setName("BtnBatal"); // NOI18N
        BtnBatal.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBatalActionPerformed(evt);
            }
        });
        BtnBatal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnBatalKeyPressed(evt);
            }
        });
        panelisi1.add(BtnBatal);

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

        BtnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/inventaris.png"))); // NOI18N
        BtnEdit.setMnemonic('G');
        BtnEdit.setText("Ganti");
        BtnEdit.setToolTipText("Alt+G");
        BtnEdit.setIconTextGap(3);
        BtnEdit.setName("BtnEdit"); // NOI18N
        BtnEdit.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEditActionPerformed(evt);
            }
        });
        BtnEdit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnEditKeyPressed(evt);
            }
        });
        panelisi1.add(BtnEdit);

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

        TabSetting.setBackground(new java.awt.Color(255, 255, 254));
        TabSetting.setForeground(new java.awt.Color(70, 70, 70));
        TabSetting.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabSetting.setName("TabSetting"); // NOI18N
        TabSetting.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabSettingMouseClicked(evt);
            }
        });

        internalFrame3.setBackground(new java.awt.Color(255, 255, 255));
        internalFrame3.setBorder(null);
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll2.setComponentPopupMenu(Popup1);
        Scroll2.setName("Scroll2"); // NOI18N
        Scroll2.setOpaque(true);

        tbPengaturanUmum.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbPengaturanUmum.setName("tbPengaturanUmum"); // NOI18N
        tbPengaturanUmum.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPengaturanUmumMouseClicked(evt);
            }
        });
        tbPengaturanUmum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbPengaturanUmumKeyPressed(evt);
            }
        });
        Scroll2.setViewportView(tbPengaturanUmum);

        internalFrame3.add(Scroll2, java.awt.BorderLayout.CENTER);

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 77));
        panelGlass8.setLayout(null);

        jLabel5.setText("Pengaturan harga obat yang digunakan :");
        jLabel5.setName("jLabel5"); // NOI18N
        panelGlass8.add(jLabel5);
        jLabel5.setBounds(0, 12, 230, 23);

        cmbPengaturan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Umum", "Per Jenis", "Per Barang" }));
        cmbPengaturan.setName("cmbPengaturan"); // NOI18N
        cmbPengaturan.setPreferredSize(new java.awt.Dimension(55, 28));
        cmbPengaturan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbPengaturanKeyPressed(evt);
            }
        });
        panelGlass8.add(cmbPengaturan);
        cmbPengaturan.setBounds(233, 12, 220, 23);

        jLabel6.setText("Penggunaan harga dasar obat :");
        jLabel6.setName("jLabel6"); // NOI18N
        panelGlass8.add(jLabel6);
        jLabel6.setBounds(0, 42, 230, 23);

        cmbHargaDasar.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Harga Beli", "Harga Diskon" }));
        cmbHargaDasar.setName("cmbHargaDasar"); // NOI18N
        cmbHargaDasar.setPreferredSize(new java.awt.Dimension(55, 28));
        cmbHargaDasar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbHargaDasarKeyPressed(evt);
            }
        });
        panelGlass8.add(cmbHargaDasar);
        cmbHargaDasar.setBounds(233, 42, 220, 23);

        internalFrame3.add(panelGlass8, java.awt.BorderLayout.PAGE_START);

        TabSetting.addTab("Pegaturan Harga", internalFrame3);

        internalFrame4.setBackground(new java.awt.Color(255, 255, 255));
        internalFrame4.setBorder(null);
        internalFrame4.setName("internalFrame4"); // NOI18N
        internalFrame4.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(100, 164));
        panelisi3.setLayout(null);

        label44.setText("Keuntungan di Rawat Inap Kelas VIP(%) :");
        label44.setName("label44"); // NOI18N
        label44.setPreferredSize(new java.awt.Dimension(82, 23));
        panelisi3.add(label44);
        label44.setBounds(320, 10, 229, 23);

        ranapvipumum.setHighlighter(null);
        ranapvipumum.setName("ranapvipumum"); // NOI18N
        ranapvipumum.setPreferredSize(new java.awt.Dimension(50, 23));
        ranapvipumum.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                ranapvipumumMouseMoved(evt);
            }
        });
        ranapvipumum.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                ranapvipumumMouseExited(evt);
            }
        });
        ranapvipumum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ranapvipumumKeyPressed(evt);
            }
        });
        panelisi3.add(ranapvipumum);
        ranapvipumum.setBounds(550, 10, 50, 23);

        label45.setText("Keuntungan di Rawat Inap Kelas VVIP(%) :");
        label45.setName("label45"); // NOI18N
        label45.setPreferredSize(new java.awt.Dimension(80, 23));
        panelisi3.add(label45);
        label45.setBounds(320, 40, 229, 23);

        ranapvvipumum.setName("ranapvvipumum"); // NOI18N
        ranapvvipumum.setPreferredSize(new java.awt.Dimension(50, 23));
        ranapvvipumum.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                ranapvvipumumMouseMoved(evt);
            }
        });
        ranapvvipumum.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                ranapvvipumumMouseExited(evt);
            }
        });
        ranapvvipumum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ranapvvipumumKeyPressed(evt);
            }
        });
        panelisi3.add(ranapvvipumum);
        ranapvvipumum.setBounds(550, 40, 50, 23);

        label46.setText("Keuntungan Jika Beli dari Apotek Lain(%) :");
        label46.setName("label46"); // NOI18N
        label46.setPreferredSize(new java.awt.Dimension(80, 23));
        panelisi3.add(label46);
        label46.setBounds(289, 70, 260, 23);

        beliluarumum.setName("beliluarumum"); // NOI18N
        beliluarumum.setPreferredSize(new java.awt.Dimension(50, 23));
        beliluarumum.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                beliluarumumMouseMoved(evt);
            }
        });
        beliluarumum.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                beliluarumumMouseExited(evt);
            }
        });
        beliluarumum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                beliluarumumKeyPressed(evt);
            }
        });
        panelisi3.add(beliluarumum);
        beliluarumum.setBounds(550, 70, 50, 23);

        label47.setText("Keuntungan di Penjualan Bebas(%) :");
        label47.setName("label47"); // NOI18N
        label47.setPreferredSize(new java.awt.Dimension(80, 23));
        panelisi3.add(label47);
        label47.setBounds(320, 100, 229, 23);

        jualbebasumum.setName("jualbebasumum"); // NOI18N
        jualbebasumum.setPreferredSize(new java.awt.Dimension(50, 23));
        jualbebasumum.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jualbebasumumMouseMoved(evt);
            }
        });
        jualbebasumum.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jualbebasumumMouseExited(evt);
            }
        });
        jualbebasumum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jualbebasumumKeyPressed(evt);
            }
        });
        panelisi3.add(jualbebasumum);
        jualbebasumum.setBounds(550, 100, 50, 23);

        karyawanumum.setName("karyawanumum"); // NOI18N
        karyawanumum.setPreferredSize(new java.awt.Dimension(50, 23));
        karyawanumum.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                karyawanumumMouseMoved(evt);
            }
        });
        karyawanumum.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                karyawanumumMouseExited(evt);
            }
        });
        karyawanumum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                karyawanumumKeyPressed(evt);
            }
        });
        panelisi3.add(karyawanumum);
        karyawanumum.setBounds(550, 130, 50, 23);

        label48.setText("Keuntungan Jika Pasien/Pembeli Karyawan(%) :");
        label48.setName("label48"); // NOI18N
        label48.setPreferredSize(new java.awt.Dimension(80, 23));
        panelisi3.add(label48);
        label48.setBounds(299, 130, 250, 23);

        label49.setText("Keuntungan di Rawat Jalan(%) :");
        label49.setName("label49"); // NOI18N
        label49.setPreferredSize(new java.awt.Dimension(82, 23));
        panelisi3.add(label49);
        label49.setBounds(0, 10, 229, 23);

        ralanumum.setHighlighter(null);
        ralanumum.setName("ralanumum"); // NOI18N
        ralanumum.setPreferredSize(new java.awt.Dimension(50, 23));
        ralanumum.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                ralanumumMouseMoved(evt);
            }
        });
        ralanumum.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                ralanumumMouseExited(evt);
            }
        });
        ralanumum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ralanumumKeyPressed(evt);
            }
        });
        panelisi3.add(ralanumum);
        ralanumum.setBounds(231, 10, 50, 23);

        ranapk1umum.setName("ranapk1umum"); // NOI18N
        ranapk1umum.setPreferredSize(new java.awt.Dimension(50, 23));
        ranapk1umum.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                ranapk1umumMouseMoved(evt);
            }
        });
        ranapk1umum.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                ranapk1umumMouseExited(evt);
            }
        });
        ranapk1umum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ranapk1umumKeyPressed(evt);
            }
        });
        panelisi3.add(ranapk1umum);
        ranapk1umum.setBounds(231, 40, 50, 23);

        label50.setText("Keuntungan di Rawat Inap Kelas 1(%) :");
        label50.setName("label50"); // NOI18N
        label50.setPreferredSize(new java.awt.Dimension(80, 23));
        panelisi3.add(label50);
        label50.setBounds(0, 40, 229, 23);

        label51.setText("Keuntungan di Rawat Inap Kelas 2(%) :");
        label51.setName("label51"); // NOI18N
        label51.setPreferredSize(new java.awt.Dimension(80, 23));
        panelisi3.add(label51);
        label51.setBounds(0, 70, 229, 23);

        ranapk2umum.setName("ranapk2umum"); // NOI18N
        ranapk2umum.setPreferredSize(new java.awt.Dimension(50, 23));
        ranapk2umum.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                ranapk2umumMouseMoved(evt);
            }
        });
        ranapk2umum.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                ranapk2umumMouseExited(evt);
            }
        });
        ranapk2umum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ranapk2umumKeyPressed(evt);
            }
        });
        panelisi3.add(ranapk2umum);
        ranapk2umum.setBounds(231, 70, 50, 23);

        ranapk3umum.setName("ranapk3umum"); // NOI18N
        ranapk3umum.setPreferredSize(new java.awt.Dimension(50, 23));
        ranapk3umum.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                ranapk3umumMouseMoved(evt);
            }
        });
        ranapk3umum.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                ranapk3umumMouseExited(evt);
            }
        });
        ranapk3umum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ranapk3umumKeyPressed(evt);
            }
        });
        panelisi3.add(ranapk3umum);
        ranapk3umum.setBounds(231, 100, 50, 23);

        label52.setText("Keuntungan di Rawat Inap Kelas 3(%) :");
        label52.setName("label52"); // NOI18N
        label52.setPreferredSize(new java.awt.Dimension(80, 23));
        panelisi3.add(label52);
        label52.setBounds(0, 100, 229, 23);

        label53.setText("Keuntungan di Rawat Inap Kelas Utama(%) :");
        label53.setName("label53"); // NOI18N
        label53.setPreferredSize(new java.awt.Dimension(80, 23));
        panelisi3.add(label53);
        label53.setBounds(0, 130, 229, 23);

        ranaputamaumum.setName("ranaputamaumum"); // NOI18N
        ranaputamaumum.setPreferredSize(new java.awt.Dimension(50, 23));
        ranaputamaumum.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                ranaputamaumumMouseMoved(evt);
            }
        });
        ranaputamaumum.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                ranaputamaumumMouseExited(evt);
            }
        });
        ranaputamaumum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ranaputamaumumKeyPressed(evt);
            }
        });
        panelisi3.add(ranaputamaumum);
        ranaputamaumum.setBounds(231, 130, 50, 23);

        internalFrame4.add(panelisi3, java.awt.BorderLayout.PAGE_START);

        Scroll1.setComponentPopupMenu(Popup1);
        Scroll1.setDoubleBuffered(true);
        Scroll1.setName("Scroll1"); // NOI18N
        Scroll1.setOpaque(true);

        tbPengaturanHargaUmum.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbPengaturanHargaUmum.setComponentPopupMenu(Popup1);
        tbPengaturanHargaUmum.setName("tbPengaturanHargaUmum"); // NOI18N
        tbPengaturanHargaUmum.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPengaturanHargaUmumMouseClicked(evt);
            }
        });
        tbPengaturanHargaUmum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbPengaturanHargaUmumKeyPressed(evt);
            }
        });
        Scroll1.setViewportView(tbPengaturanHargaUmum);

        internalFrame4.add(Scroll1, java.awt.BorderLayout.CENTER);

        TabSetting.addTab("Pegaturan Harga Umum", internalFrame4);

        internalFrame2.setBackground(new java.awt.Color(255, 255, 255));
        internalFrame2.setBorder(null);
        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi2.setName("panelisi2"); // NOI18N
        panelisi2.setPreferredSize(new java.awt.Dimension(100, 194));
        panelisi2.setLayout(null);

        label35.setText("Keuntungan di Rawat Inap Kelas VIP(%) :");
        label35.setName("label35"); // NOI18N
        label35.setPreferredSize(new java.awt.Dimension(82, 23));
        panelisi2.add(label35);
        label35.setBounds(320, 10, 229, 23);

        ranapvip.setHighlighter(null);
        ranapvip.setName("ranapvip"); // NOI18N
        ranapvip.setPreferredSize(new java.awt.Dimension(50, 23));
        ranapvip.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                ranapvipMouseMoved(evt);
            }
        });
        ranapvip.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                ranapvipMouseExited(evt);
            }
        });
        ranapvip.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ranapvipKeyPressed(evt);
            }
        });
        panelisi2.add(ranapvip);
        ranapvip.setBounds(550, 10, 50, 23);

        label34.setText("Keuntungan di Rawat Inap Kelas VVIP(%) :");
        label34.setName("label34"); // NOI18N
        label34.setPreferredSize(new java.awt.Dimension(80, 23));
        panelisi2.add(label34);
        label34.setBounds(320, 40, 229, 23);

        ranapvvip.setName("ranapvvip"); // NOI18N
        ranapvvip.setPreferredSize(new java.awt.Dimension(50, 23));
        ranapvvip.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                ranapvvipMouseMoved(evt);
            }
        });
        ranapvvip.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                ranapvvipMouseExited(evt);
            }
        });
        ranapvvip.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ranapvvipKeyPressed(evt);
            }
        });
        panelisi2.add(ranapvvip);
        ranapvvip.setBounds(550, 40, 50, 23);

        label36.setText("Keuntungan Jika Beli dari Apotek Lain(%) :");
        label36.setName("label36"); // NOI18N
        label36.setPreferredSize(new java.awt.Dimension(80, 23));
        panelisi2.add(label36);
        label36.setBounds(289, 70, 260, 23);

        beliluar.setName("beliluar"); // NOI18N
        beliluar.setPreferredSize(new java.awt.Dimension(50, 23));
        beliluar.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                beliluarMouseMoved(evt);
            }
        });
        beliluar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                beliluarMouseExited(evt);
            }
        });
        beliluar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                beliluarKeyPressed(evt);
            }
        });
        panelisi2.add(beliluar);
        beliluar.setBounds(550, 70, 50, 23);

        label38.setText("Keuntungan di Penjualan Bebas(%) :");
        label38.setName("label38"); // NOI18N
        label38.setPreferredSize(new java.awt.Dimension(80, 23));
        panelisi2.add(label38);
        label38.setBounds(320, 100, 229, 23);

        jualbebas.setName("jualbebas"); // NOI18N
        jualbebas.setPreferredSize(new java.awt.Dimension(50, 23));
        jualbebas.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jualbebasMouseMoved(evt);
            }
        });
        jualbebas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jualbebasMouseExited(evt);
            }
        });
        jualbebas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jualbebasKeyPressed(evt);
            }
        });
        panelisi2.add(jualbebas);
        jualbebas.setBounds(550, 100, 50, 23);

        karyawan.setName("karyawan"); // NOI18N
        karyawan.setPreferredSize(new java.awt.Dimension(50, 23));
        karyawan.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                karyawanMouseMoved(evt);
            }
        });
        karyawan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                karyawanMouseExited(evt);
            }
        });
        karyawan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                karyawanKeyPressed(evt);
            }
        });
        panelisi2.add(karyawan);
        karyawan.setBounds(550, 130, 50, 23);

        label39.setText("Keuntungan Jika Pasien/Pembeli Karyawan(%) :");
        label39.setName("label39"); // NOI18N
        label39.setPreferredSize(new java.awt.Dimension(80, 23));
        panelisi2.add(label39);
        label39.setBounds(299, 130, 250, 23);

        label37.setText("Keuntungan di Rawat Jalan(%) :");
        label37.setName("label37"); // NOI18N
        label37.setPreferredSize(new java.awt.Dimension(82, 23));
        panelisi2.add(label37);
        label37.setBounds(0, 10, 229, 23);

        ralan.setHighlighter(null);
        ralan.setName("ralan"); // NOI18N
        ralan.setPreferredSize(new java.awt.Dimension(50, 23));
        ralan.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                ralanMouseMoved(evt);
            }
        });
        ralan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                ralanMouseExited(evt);
            }
        });
        ralan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ralanKeyPressed(evt);
            }
        });
        panelisi2.add(ralan);
        ralan.setBounds(231, 10, 50, 23);

        ranapk1.setName("ranapk1"); // NOI18N
        ranapk1.setPreferredSize(new java.awt.Dimension(50, 23));
        ranapk1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                ranapk1MouseMoved(evt);
            }
        });
        ranapk1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                ranapk1MouseExited(evt);
            }
        });
        ranapk1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ranapk1KeyPressed(evt);
            }
        });
        panelisi2.add(ranapk1);
        ranapk1.setBounds(231, 40, 50, 23);

        label40.setText("Keuntungan di Rawat Inap Kelas 1(%) :");
        label40.setName("label40"); // NOI18N
        label40.setPreferredSize(new java.awt.Dimension(80, 23));
        panelisi2.add(label40);
        label40.setBounds(0, 40, 229, 23);

        label41.setText("Keuntungan di Rawat Inap Kelas 2(%) :");
        label41.setName("label41"); // NOI18N
        label41.setPreferredSize(new java.awt.Dimension(80, 23));
        panelisi2.add(label41);
        label41.setBounds(0, 70, 229, 23);

        ranapk2.setName("ranapk2"); // NOI18N
        ranapk2.setPreferredSize(new java.awt.Dimension(50, 23));
        ranapk2.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                ranapk2MouseMoved(evt);
            }
        });
        ranapk2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                ranapk2MouseExited(evt);
            }
        });
        ranapk2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ranapk2KeyPressed(evt);
            }
        });
        panelisi2.add(ranapk2);
        ranapk2.setBounds(231, 70, 50, 23);

        ranapk3.setName("ranapk3"); // NOI18N
        ranapk3.setPreferredSize(new java.awt.Dimension(50, 23));
        ranapk3.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                ranapk3MouseMoved(evt);
            }
        });
        ranapk3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                ranapk3MouseExited(evt);
            }
        });
        ranapk3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ranapk3KeyPressed(evt);
            }
        });
        panelisi2.add(ranapk3);
        ranapk3.setBounds(231, 100, 50, 23);

        label42.setText("Keuntungan di Rawat Inap Kelas 3(%) :");
        label42.setName("label42"); // NOI18N
        label42.setPreferredSize(new java.awt.Dimension(80, 23));
        panelisi2.add(label42);
        label42.setBounds(0, 100, 229, 23);

        label43.setText("Keuntungan di Rawat Inap Kelas Utama(%) :");
        label43.setName("label43"); // NOI18N
        label43.setPreferredSize(new java.awt.Dimension(80, 23));
        panelisi2.add(label43);
        label43.setBounds(0, 130, 229, 23);

        ranaputama.setName("ranaputama"); // NOI18N
        ranaputama.setPreferredSize(new java.awt.Dimension(50, 23));
        ranaputama.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                ranaputamaMouseMoved(evt);
            }
        });
        ranaputama.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                ranaputamaMouseExited(evt);
            }
        });
        ranaputama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ranaputamaKeyPressed(evt);
            }
        });
        panelisi2.add(ranaputama);
        ranaputama.setBounds(231, 130, 50, 23);

        label20.setText("Jenis Barang :");
        label20.setName("label20"); // NOI18N
        label20.setPreferredSize(new java.awt.Dimension(65, 23));
        panelisi2.add(label20);
        label20.setBounds(0, 162, 229, 23);

        kdjns.setName("kdjns"); // NOI18N
        kdjns.setPreferredSize(new java.awt.Dimension(207, 23));
        kdjns.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdjnsKeyPressed(evt);
            }
        });
        panelisi2.add(kdjns);
        kdjns.setBounds(231, 162, 50, 23);

        nmjns.setEditable(false);
        nmjns.setName("nmjns"); // NOI18N
        nmjns.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi2.add(nmjns);
        nmjns.setBounds(284, 162, 288, 23);

        BtnJenis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnJenis.setMnemonic('2');
        BtnJenis.setToolTipText("Alt+2");
        BtnJenis.setName("BtnJenis"); // NOI18N
        BtnJenis.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnJenis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnJenisActionPerformed(evt);
            }
        });
        panelisi2.add(BtnJenis);
        BtnJenis.setBounds(575, 162, 25, 23);

        internalFrame2.add(panelisi2, java.awt.BorderLayout.PAGE_START);

        Scroll.setComponentPopupMenu(Popup);
        Scroll.setDoubleBuffered(true);
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbAdmin.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbAdmin.setComponentPopupMenu(Popup);
        tbAdmin.setName("tbAdmin"); // NOI18N
        tbAdmin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbAdminMouseClicked(evt);
            }
        });
        tbAdmin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbAdminKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbAdmin);

        internalFrame2.add(Scroll, java.awt.BorderLayout.CENTER);

        panelisi5.setName("panelisi5"); // NOI18N
        panelisi5.setPreferredSize(new java.awt.Dimension(100, 44));
        panelisi5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        label9.setText("Key Word :");
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi5.add(label9);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(350, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelisi5.add(TCari);

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
        panelisi5.add(BtnCari);

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
        panelisi5.add(BtnAll);

        label10.setText("Record :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi5.add(label10);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi5.add(LCount);

        internalFrame2.add(panelisi5, java.awt.BorderLayout.PAGE_END);

        TabSetting.addTab("Pegaturan Harga Per Jenis Barang", internalFrame2);

        internalFrame5.setBackground(new java.awt.Color(255, 255, 255));
        internalFrame5.setBorder(null);
        internalFrame5.setName("internalFrame5"); // NOI18N
        internalFrame5.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi4.setName("panelisi4"); // NOI18N
        panelisi4.setPreferredSize(new java.awt.Dimension(100, 194));
        panelisi4.setLayout(null);

        label54.setText("Keuntungan di Rawat Inap Kelas VIP(%) :");
        label54.setName("label54"); // NOI18N
        label54.setPreferredSize(new java.awt.Dimension(82, 23));
        panelisi4.add(label54);
        label54.setBounds(320, 10, 229, 23);

        ranapvipperbarang.setHighlighter(null);
        ranapvipperbarang.setName("ranapvipperbarang"); // NOI18N
        ranapvipperbarang.setPreferredSize(new java.awt.Dimension(50, 23));
        ranapvipperbarang.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                ranapvipperbarangMouseMoved(evt);
            }
        });
        ranapvipperbarang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                ranapvipperbarangMouseExited(evt);
            }
        });
        ranapvipperbarang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ranapvipperbarangKeyPressed(evt);
            }
        });
        panelisi4.add(ranapvipperbarang);
        ranapvipperbarang.setBounds(550, 10, 50, 23);

        label55.setText("Keuntungan di Rawat Inap Kelas VVIP(%) :");
        label55.setName("label55"); // NOI18N
        label55.setPreferredSize(new java.awt.Dimension(80, 23));
        panelisi4.add(label55);
        label55.setBounds(320, 40, 229, 23);

        ranapvvipperbarang.setName("ranapvvipperbarang"); // NOI18N
        ranapvvipperbarang.setPreferredSize(new java.awt.Dimension(50, 23));
        ranapvvipperbarang.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                ranapvvipperbarangMouseMoved(evt);
            }
        });
        ranapvvipperbarang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                ranapvvipperbarangMouseExited(evt);
            }
        });
        ranapvvipperbarang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ranapvvipperbarangKeyPressed(evt);
            }
        });
        panelisi4.add(ranapvvipperbarang);
        ranapvvipperbarang.setBounds(550, 40, 50, 23);

        label56.setText("Keuntungan Jika Beli dari Apotek Lain(%) :");
        label56.setName("label56"); // NOI18N
        label56.setPreferredSize(new java.awt.Dimension(80, 23));
        panelisi4.add(label56);
        label56.setBounds(289, 70, 260, 23);

        beliluarperbarang.setName("beliluarperbarang"); // NOI18N
        beliluarperbarang.setPreferredSize(new java.awt.Dimension(50, 23));
        beliluarperbarang.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                beliluarperbarangMouseMoved(evt);
            }
        });
        beliluarperbarang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                beliluarperbarangMouseExited(evt);
            }
        });
        beliluarperbarang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                beliluarperbarangKeyPressed(evt);
            }
        });
        panelisi4.add(beliluarperbarang);
        beliluarperbarang.setBounds(550, 70, 50, 23);

        label57.setText("Keuntungan di Penjualan Bebas(%) :");
        label57.setName("label57"); // NOI18N
        label57.setPreferredSize(new java.awt.Dimension(80, 23));
        panelisi4.add(label57);
        label57.setBounds(320, 100, 229, 23);

        jualbebasperbarang.setName("jualbebasperbarang"); // NOI18N
        jualbebasperbarang.setPreferredSize(new java.awt.Dimension(50, 23));
        jualbebasperbarang.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jualbebasperbarangMouseMoved(evt);
            }
        });
        jualbebasperbarang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jualbebasperbarangMouseExited(evt);
            }
        });
        jualbebasperbarang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jualbebasperbarangKeyPressed(evt);
            }
        });
        panelisi4.add(jualbebasperbarang);
        jualbebasperbarang.setBounds(550, 100, 50, 23);

        karyawanperbarang.setName("karyawanperbarang"); // NOI18N
        karyawanperbarang.setPreferredSize(new java.awt.Dimension(50, 23));
        karyawanperbarang.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                karyawanperbarangMouseMoved(evt);
            }
        });
        karyawanperbarang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                karyawanperbarangMouseExited(evt);
            }
        });
        karyawanperbarang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                karyawanperbarangKeyPressed(evt);
            }
        });
        panelisi4.add(karyawanperbarang);
        karyawanperbarang.setBounds(550, 130, 50, 23);

        label58.setText("Keuntungan Jika Pasien/Pembeli Karyawan(%) :");
        label58.setName("label58"); // NOI18N
        label58.setPreferredSize(new java.awt.Dimension(80, 23));
        panelisi4.add(label58);
        label58.setBounds(299, 130, 250, 23);

        label59.setText("Keuntungan di Rawat Jalan(%) :");
        label59.setName("label59"); // NOI18N
        label59.setPreferredSize(new java.awt.Dimension(82, 23));
        panelisi4.add(label59);
        label59.setBounds(0, 10, 229, 23);

        ralanperbarang.setHighlighter(null);
        ralanperbarang.setName("ralanperbarang"); // NOI18N
        ralanperbarang.setPreferredSize(new java.awt.Dimension(50, 23));
        ralanperbarang.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                ralanperbarangMouseMoved(evt);
            }
        });
        ralanperbarang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                ralanperbarangMouseExited(evt);
            }
        });
        ralanperbarang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ralanperbarangKeyPressed(evt);
            }
        });
        panelisi4.add(ralanperbarang);
        ralanperbarang.setBounds(231, 10, 50, 23);

        ranapk1perbarang.setName("ranapk1perbarang"); // NOI18N
        ranapk1perbarang.setPreferredSize(new java.awt.Dimension(50, 23));
        ranapk1perbarang.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                ranapk1perbarangMouseMoved(evt);
            }
        });
        ranapk1perbarang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                ranapk1perbarangMouseExited(evt);
            }
        });
        ranapk1perbarang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ranapk1perbarangKeyPressed(evt);
            }
        });
        panelisi4.add(ranapk1perbarang);
        ranapk1perbarang.setBounds(231, 40, 50, 23);

        label60.setText("Keuntungan di Rawat Inap Kelas 1(%) :");
        label60.setName("label60"); // NOI18N
        label60.setPreferredSize(new java.awt.Dimension(80, 23));
        panelisi4.add(label60);
        label60.setBounds(0, 40, 229, 23);

        label61.setText("Keuntungan di Rawat Inap Kelas 2(%) :");
        label61.setName("label61"); // NOI18N
        label61.setPreferredSize(new java.awt.Dimension(80, 23));
        panelisi4.add(label61);
        label61.setBounds(0, 70, 229, 23);

        ranapk2perbarang.setName("ranapk2perbarang"); // NOI18N
        ranapk2perbarang.setPreferredSize(new java.awt.Dimension(50, 23));
        ranapk2perbarang.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                ranapk2perbarangMouseMoved(evt);
            }
        });
        ranapk2perbarang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                ranapk2perbarangMouseExited(evt);
            }
        });
        ranapk2perbarang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ranapk2perbarangKeyPressed(evt);
            }
        });
        panelisi4.add(ranapk2perbarang);
        ranapk2perbarang.setBounds(231, 70, 50, 23);

        ranapk3perbarang.setName("ranapk3perbarang"); // NOI18N
        ranapk3perbarang.setPreferredSize(new java.awt.Dimension(50, 23));
        ranapk3perbarang.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                ranapk3perbarangMouseMoved(evt);
            }
        });
        ranapk3perbarang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                ranapk3perbarangMouseExited(evt);
            }
        });
        ranapk3perbarang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ranapk3perbarangKeyPressed(evt);
            }
        });
        panelisi4.add(ranapk3perbarang);
        ranapk3perbarang.setBounds(231, 100, 50, 23);

        label62.setText("Keuntungan di Rawat Inap Kelas 3(%) :");
        label62.setName("label62"); // NOI18N
        label62.setPreferredSize(new java.awt.Dimension(80, 23));
        panelisi4.add(label62);
        label62.setBounds(0, 100, 229, 23);

        label63.setText("Keuntungan di Rawat Inap Kelas Utama(%) :");
        label63.setName("label63"); // NOI18N
        label63.setPreferredSize(new java.awt.Dimension(80, 23));
        panelisi4.add(label63);
        label63.setBounds(0, 130, 229, 23);

        ranaputamaperbarang.setName("ranaputamaperbarang"); // NOI18N
        ranaputamaperbarang.setPreferredSize(new java.awt.Dimension(50, 23));
        ranaputamaperbarang.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                ranaputamaperbarangMouseMoved(evt);
            }
        });
        ranaputamaperbarang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                ranaputamaperbarangMouseExited(evt);
            }
        });
        ranaputamaperbarang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ranaputamaperbarangKeyPressed(evt);
            }
        });
        panelisi4.add(ranaputamaperbarang);
        ranaputamaperbarang.setBounds(231, 130, 50, 23);

        label21.setText("Barang/BHP/Alkes :");
        label21.setName("label21"); // NOI18N
        label21.setPreferredSize(new java.awt.Dimension(65, 23));
        panelisi4.add(label21);
        label21.setBounds(0, 162, 229, 23);

        kdbarang.setName("kdbarang"); // NOI18N
        kdbarang.setPreferredSize(new java.awt.Dimension(207, 23));
        kdbarang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdbarangKeyPressed(evt);
            }
        });
        panelisi4.add(kdbarang);
        kdbarang.setBounds(231, 162, 50, 23);

        nmbarang.setEditable(false);
        nmbarang.setName("nmbarang"); // NOI18N
        nmbarang.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi4.add(nmbarang);
        nmbarang.setBounds(284, 162, 288, 23);

        Btnbarang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        Btnbarang.setMnemonic('2');
        Btnbarang.setToolTipText("Alt+2");
        Btnbarang.setName("Btnbarang"); // NOI18N
        Btnbarang.setPreferredSize(new java.awt.Dimension(28, 23));
        Btnbarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnbarangActionPerformed(evt);
            }
        });
        panelisi4.add(Btnbarang);
        Btnbarang.setBounds(575, 162, 25, 23);

        internalFrame5.add(panelisi4, java.awt.BorderLayout.PAGE_START);

        Scroll3.setComponentPopupMenu(Popup2);
        Scroll3.setDoubleBuffered(true);
        Scroll3.setName("Scroll3"); // NOI18N
        Scroll3.setOpaque(true);

        tbPengaturanHargaPerBarang.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbPengaturanHargaPerBarang.setComponentPopupMenu(Popup2);
        tbPengaturanHargaPerBarang.setName("tbPengaturanHargaPerBarang"); // NOI18N
        tbPengaturanHargaPerBarang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPengaturanHargaPerBarangMouseClicked(evt);
            }
        });
        tbPengaturanHargaPerBarang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbPengaturanHargaPerBarangKeyPressed(evt);
            }
        });
        Scroll3.setViewportView(tbPengaturanHargaPerBarang);

        internalFrame5.add(Scroll3, java.awt.BorderLayout.CENTER);

        panelisi6.setName("panelisi6"); // NOI18N
        panelisi6.setPreferredSize(new java.awt.Dimension(100, 44));
        panelisi6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        label11.setText("Key Word :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi6.add(label11);

        TCari1.setName("TCari1"); // NOI18N
        TCari1.setPreferredSize(new java.awt.Dimension(350, 23));
        TCari1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCari1KeyPressed(evt);
            }
        });
        panelisi6.add(TCari1);

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
        panelisi6.add(BtnCari1);

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
        panelisi6.add(BtnAll1);

        label12.setText("Record :");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi6.add(label12);

        LCount1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount1.setText("0");
        LCount1.setName("LCount1"); // NOI18N
        LCount1.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi6.add(LCount1);

        internalFrame5.add(panelisi6, java.awt.BorderLayout.PAGE_END);

        TabSetting.addTab("Pegaturan Harga Per Barang", internalFrame5);

        internalFrame1.add(TabSetting, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ranapvipKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ranapvipKeyPressed
        Valid.pindah(evt,ranaputama,ranapvvip);
}//GEN-LAST:event_ranapvipKeyPressed

    private void ranapvvipKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ranapvvipKeyPressed
        Valid.pindah(evt,ranapvip,beliluar);
}//GEN-LAST:event_ranapvvipKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TabSetting.getSelectedIndex()==0){
            if(tabModePengaturanUmum.getRowCount()>0){
                JOptionPane.showMessageDialog(null,"Maaf, Hanya diijinkan satu pengaturan ...!!!!");
                cmbPengaturan.requestFocus();
            }else{
                Sequel.menyimpan("set_harga_obat","?,?",2,new String[]{
                    cmbPengaturan.getSelectedItem().toString(),
                    cmbHargaDasar.getSelectedItem().toString()
                });
                tampilpengaturanumum();
                emptTeks();
            }
        }else if(TabSetting.getSelectedIndex()==1){
            if(tabModePengaturanHargaUmum.getRowCount()>0){
                JOptionPane.showMessageDialog(null,"Maaf, Hanya diijinkan satu pengaturan ...!!!!");
                ralanumum.requestFocus();
            }else{
                if(ralanumum.getText().trim().equals("")){
                    Valid.textKosong(ralanumum,"Keuntungan di Rawat Jalan");
                }else if(ranapk1umum.getText().trim().equals("")){
                    Valid.textKosong(ranapk1umum,"Keuntungan di Ranap Kelas 1");
                }else if(ranapk2umum.getText().trim().equals("")){
                    Valid.textKosong(ranapk2umum,"Keuntungan di Ranap Kelas 2");
                }else if(ranapk3umum.getText().trim().equals("")){
                    Valid.textKosong(ranapk3umum,"Keuntungan di Ranap Kelas 3");
                }else if(ranaputamaumum.getText().trim().equals("")){
                    Valid.textKosong(ranaputamaumum,"Keuntungan di Ranap Kelas Utama");
                }else if(ranapvipumum.getText().trim().equals("")){
                    Valid.textKosong(ranapvipumum,"Keuntungan di Ranap Kelas VIP");
                }else if(ranapvvipumum.getText().trim().equals("")){
                    Valid.textKosong(ranapvvipumum,"Keuntungan di Ranap Kelas VVIP");
                }else if(beliluarumum.getText().trim().equals("")){
                    Valid.textKosong(beliluarumum,"Keuntungan Jika Beli dari Apotek Lain");
                }else if(jualbebasumum.getText().trim().equals("")){
                    Valid.textKosong(jualbebasumum,"Keuntungan di Penjualan Bebas");
                }else if(karyawanumum.getText().trim().equals("")){
                    Valid.textKosong(karyawanumum,"Keuntungan Jika Pasien/Pembeli Karyawan");
                }else{  
                    Sequel.menyimpan("setpenjualanumum","'"+ralanumum.getText()+"','"+ranapk1umum.getText()+"','"+
                            ranapk2umum.getText()+"','"+ranapk3umum.getText()+"','"+ranaputamaumum.getText()+"','"+
                            ranapvipumum.getText()+"','"+ranapvvipumum.getText()+"','"+beliluarumum.getText()+"','"+
                            jualbebasumum.getText()+"','"+karyawanumum.getText()+"'","Set Harga");
                    tampilpengaturanhargaumum();
                    emptTeks();
                }                
            }
        }else if(TabSetting.getSelectedIndex()==2){
            if(ralan.getText().trim().equals("")){
                Valid.textKosong(ralan,"Keuntungan di Rawat Jalan");
            }else if(ranapk1.getText().trim().equals("")){
                Valid.textKosong(ranapk1,"Keuntungan di Ranap Kelas 1");
            }else if(ranapk2.getText().trim().equals("")){
                Valid.textKosong(ranapk2,"Keuntungan di Ranap Kelas 2");
            }else if(ranapk3.getText().trim().equals("")){
                Valid.textKosong(ranapk3,"Keuntungan di Ranap Kelas 3");
            }else if(ranaputama.getText().trim().equals("")){
                Valid.textKosong(ranaputama,"Keuntungan di Ranap Kelas Utama");
            }else if(ranapvip.getText().trim().equals("")){
                Valid.textKosong(ranapvip,"Keuntungan di Ranap Kelas VIP");
            }else if(ranapvvip.getText().trim().equals("")){
                Valid.textKosong(ranapvvip,"Keuntungan di Ranap Kelas VVIP");
            }else if(beliluar.getText().trim().equals("")){
                Valid.textKosong(beliluar,"Keuntungan Jika Beli dari Apotek Lain");
            }else if(jualbebas.getText().trim().equals("")){
                Valid.textKosong(jualbebas,"Keuntungan di Penjualan Bebas");
            }else if(karyawan.getText().trim().equals("")){
                Valid.textKosong(karyawan,"Keuntungan Jika Pasien/Pembeli Karyawan");
            }else if(kdjns.getText().trim().equals("")||nmjns.getText().trim().equals("")){
                Valid.textKosong(kdjns,"Jenis Barang");
            }else{            
                Sequel.menyimpan("setpenjualan","'"+ralan.getText()+"','"+ranapk1.getText()+"','"+
                        ranapk2.getText()+"','"+ranapk3.getText()+"','"+ranaputama.getText()+"','"+
                        ranapvip.getText()+"','"+ranapvvip.getText()+"','"+beliluar.getText()+"','"+
                        jualbebas.getText()+"','"+karyawan.getText()+"','"+kdjns.getText()+"'","Set Harga");
                tampil();
                emptTeks();
            }
        }else if(TabSetting.getSelectedIndex()==3){
            if(ralanperbarang.getText().trim().equals("")){
                Valid.textKosong(ralanperbarang,"Keuntungan di Rawat Jalan");
            }else if(ranapk1perbarang.getText().trim().equals("")){
                Valid.textKosong(ranapk1perbarang,"Keuntungan di Ranap Kelas 1");
            }else if(ranapk2perbarang.getText().trim().equals("")){
                Valid.textKosong(ranapk2perbarang,"Keuntungan di Ranap Kelas 2");
            }else if(ranapk3perbarang.getText().trim().equals("")){
                Valid.textKosong(ranapk3perbarang,"Keuntungan di Ranap Kelas 3");
            }else if(ranaputamaperbarang.getText().trim().equals("")){
                Valid.textKosong(ranaputamaperbarang,"Keuntungan di Ranap Kelas Utama");
            }else if(ranapvipperbarang.getText().trim().equals("")){
                Valid.textKosong(ranapvipperbarang,"Keuntungan di Ranap Kelas VIP");
            }else if(ranapvvipperbarang.getText().trim().equals("")){
                Valid.textKosong(ranapvvipperbarang,"Keuntungan di Ranap Kelas VVIP");
            }else if(beliluarperbarang.getText().trim().equals("")){
                Valid.textKosong(beliluarperbarang,"Keuntungan Jika Beli dari Apotek Lain");
            }else if(jualbebasperbarang.getText().trim().equals("")){
                Valid.textKosong(jualbebasperbarang,"Keuntungan di Penjualan Bebas");
            }else if(karyawanperbarang.getText().trim().equals("")){
                Valid.textKosong(karyawanperbarang,"Keuntungan Jika Pasien/Pembeli Karyawan");
            }else if(kdbarang.getText().trim().equals("")||nmbarang.getText().trim().equals("")){
                Valid.textKosong(kdbarang,"Barang/Alkes/BHP");
            }else{            
                Sequel.menyimpan("setpenjualanperbarang","'"+ralanperbarang.getText()+"','"+ranapk1perbarang.getText()+"','"+
                        ranapk2perbarang.getText()+"','"+ranapk3perbarang.getText()+"','"+ranaputamaperbarang.getText()+"','"+
                        ranapvipperbarang.getText()+"','"+ranapvvipperbarang.getText()+"','"+beliluarperbarang.getText()+"','"+
                        jualbebasperbarang.getText()+"','"+karyawanperbarang.getText()+"','"+kdbarang.getText()+"'","Set Harga");
                tampilpengaturanhargaperbarang();
                emptTeks();
            }
        }            
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            if(TabSetting.getSelectedIndex()==0){
                Valid.pindah(evt,cmbHargaDasar,BtnBatal);
            }else if(TabSetting.getSelectedIndex()==1){
                Valid.pindah(evt,karyawanumum,BtnBatal);
            }else if(TabSetting.getSelectedIndex()==2){
                Valid.pindah(evt,karyawan,BtnBatal);
            }else if(TabSetting.getSelectedIndex()==3){
                Valid.pindah(evt,ralanperbarang,BtnBatal);
            }                
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        emptTeks();
}//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            emptTeks();
        }else{Valid.pindah(evt, BtnSimpan, BtnHapus);}
}//GEN-LAST:event_BtnBatalKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if(TabSetting.getSelectedIndex()==0){
            Sequel.queryu("delete from set_harga_obat");
            tampilpengaturanumum();
            emptTeks();
        }else if(TabSetting.getSelectedIndex()==1){
            if(tabModePengaturanHargaUmum.getRowCount()==0){
                JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
                ranapvipumum.requestFocus();
            }else if(ranapvvipumum.getText().trim().equals("")){
                JOptionPane.showMessageDialog(null,"Maaf, Gagal menghapus. Pilih dulu data yang mau dihapus.\nKlik data pada table untuk memilih...!!!!");
            }else if(! ranapvvipumum.getText().trim().equals("")){
                Sequel.queryu("delete from setpenjualanumum");
                tampilpengaturanhargaumum();
                emptTeks();
            }
        }else if(TabSetting.getSelectedIndex()==2){
            if(tabMode.getRowCount()==0){
                JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
                ranapvip.requestFocus();
            }else if(ranapvvip.getText().trim().equals("")){
                JOptionPane.showMessageDialog(null,"Maaf, Gagal menghapus. Pilih dulu data yang mau dihapus.\nKlik data pada table untuk memilih...!!!!");
            }else if(! ranapvvip.getText().trim().equals("")){
                Sequel.queryu("delete from setpenjualan where kdjns='"+kdjns.getText()+"'");
                tampil();
                emptTeks();
            }
        }else if(TabSetting.getSelectedIndex()==3){
            if(tabModePengaturanHargaPerBarang.getRowCount()==0){
                JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
                ranapvipperbarang.requestFocus();
            }else if(ranapvvipperbarang.getText().trim().equals("")){
                JOptionPane.showMessageDialog(null,"Maaf, Gagal menghapus. Pilih dulu data yang mau dihapus.\nKlik data pada table untuk memilih...!!!!");
            }else if(! ranapvvipperbarang.getText().trim().equals("")){
                Sequel.queryu("delete from setpenjualanperbarang where kode_brng='"+kdbarang.getText()+"'");
                tampilpengaturanhargaperbarang();
                emptTeks();
            }
        }             
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnBatal, BtnHapus);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        if(tabModePengaturanUmum.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, pengaturan harga tidak boleh kosong ...!!!!");
            TabSetting.setSelectedIndex(0);
            cmbPengaturan.requestFocus();
        }else if(! (tabModePengaturanUmum.getRowCount()==0)) {
            dispose();
        }
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnHapus,BtnKeluar);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void tbAdminMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbAdminMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbAdminMouseClicked

    private void tbAdminKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbAdminKeyPressed
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
}//GEN-LAST:event_tbAdminKeyPressed

    private void beliluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_beliluarKeyPressed
        Valid.pindah(evt,ranapvvip,jualbebas);
    }//GEN-LAST:event_beliluarKeyPressed

    private void ranapvipMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ranapvipMouseExited
        if(ranapvip.getText().equals("")){
            ranapvip.setText("0");
        }
    }//GEN-LAST:event_ranapvipMouseExited

    private void ranapvvipMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ranapvvipMouseExited
        if(ranapvvip.getText().equals("")){
            ranapvvip.setText("0");
        }
    }//GEN-LAST:event_ranapvvipMouseExited

    private void beliluarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_beliluarMouseExited
        if(beliluar.getText().equals("")){
            beliluar.setText("0");
        }
    }//GEN-LAST:event_beliluarMouseExited

    private void ranapvipMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ranapvipMouseMoved
        if(ranapvip.getText().equals("0")||ranapvip.getText().equals("0.0")){
            ranapvip.setText("");
        }
    }//GEN-LAST:event_ranapvipMouseMoved

    private void ranapvvipMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ranapvvipMouseMoved
        if(ranapvvip.getText().equals("0")||ranapvvip.getText().equals("0.0")){
            ranapvvip.setText("");
        }
    }//GEN-LAST:event_ranapvvipMouseMoved

    private void beliluarMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_beliluarMouseMoved
        if(beliluar.getText().equals("0")||beliluar.getText().equals("0.0")){
            beliluar.setText("");
        }
    }//GEN-LAST:event_beliluarMouseMoved

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        TabSettingMouseClicked(null);
    }//GEN-LAST:event_formWindowOpened

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
        if(TabSetting.getSelectedIndex()==0){
            Sequel.queryu("delete from set_harga_obat");
            Sequel.menyimpan("set_harga_obat","?,?",2,new String[]{
                cmbPengaturan.getSelectedItem().toString(),
                cmbHargaDasar.getSelectedItem().toString()
            });
            tampilpengaturanumum();
            emptTeks();
        }else if(TabSetting.getSelectedIndex()==1){            
            if(ralanumum.getText().trim().equals("")){
                Valid.textKosong(ralanumum,"Keuntungan di Rawat Jalan");
            }else if(ranapk1umum.getText().trim().equals("")){
                Valid.textKosong(ranapk1umum,"Keuntungan di Ranap Kelas 1");
            }else if(ranapk2umum.getText().trim().equals("")){
                Valid.textKosong(ranapk2umum,"Keuntungan di Ranap Kelas 2");
            }else if(ranapk3umum.getText().trim().equals("")){
                Valid.textKosong(ranapk3umum,"Keuntungan di Ranap Kelas 3");
            }else if(ranaputamaumum.getText().trim().equals("")){
                Valid.textKosong(ranaputamaumum,"Keuntungan di Ranap Kelas Utama");
            }else if(ranapvipumum.getText().trim().equals("")){
                Valid.textKosong(ranapvipumum,"Keuntungan di Ranap Kelas VIP");
            }else if(ranapvvipumum.getText().trim().equals("")){
                Valid.textKosong(ranapvvipumum,"Keuntungan di Ranap Kelas VVIP");
            }else if(beliluarumum.getText().trim().equals("")){
                Valid.textKosong(beliluarumum,"Keuntungan Jika Beli dari Apotek Lain");
            }else if(jualbebasumum.getText().trim().equals("")){
                Valid.textKosong(jualbebasumum,"Keuntungan di Penjualan Bebas");
            }else if(karyawanumum.getText().trim().equals("")){
                Valid.textKosong(karyawanumum,"Keuntungan Jika Pasien/Pembeli Karyawan");
            }else{ 
                Sequel.queryu("delete from setpenjualanumum");
                Sequel.menyimpan("setpenjualanumum","'"+ralanumum.getText()+"','"+ranapk1umum.getText()+"','"+
                        ranapk2umum.getText()+"','"+ranapk3umum.getText()+"','"+ranaputamaumum.getText()+"','"+
                        ranapvipumum.getText()+"','"+ranapvvipumum.getText()+"','"+beliluarumum.getText()+"','"+
                        jualbebasumum.getText()+"','"+karyawanumum.getText()+"'","Set Harga");
                tampilpengaturanhargaumum();
                emptTeks();
            }   
        }else if(TabSetting.getSelectedIndex()==2){
            if(ralan.getText().trim().equals("")){
                Valid.textKosong(ralan,"Keuntungan di Rawat Jalan");
            }else if(ranapk1.getText().trim().equals("")){
                Valid.textKosong(ranapk1,"Keuntungan di Ranap Kelas 1");
            }else if(ranapk2.getText().trim().equals("")){
                Valid.textKosong(ranapk2,"Keuntungan di Ranap Kelas 2");
            }else if(ranapk3.getText().trim().equals("")){
                Valid.textKosong(ranapk3,"Keuntungan di Ranap Kelas 3");
            }else if(ranaputama.getText().trim().equals("")){
                Valid.textKosong(ranaputama,"Keuntungan di Ranap Kelas Utama");
            }else if(ranapvip.getText().trim().equals("")){
                Valid.textKosong(ranapvip,"Keuntungan di Ranap Kelas VIP");
            }else if(ranapvvip.getText().trim().equals("")){
                Valid.textKosong(ranapvvip,"Keuntungan di Ranap Kelas VVIP");
            }else if(beliluar.getText().trim().equals("")){
                Valid.textKosong(beliluar,"Keuntungan Jika Beli dari Apotek Lain");
            }else if(jualbebas.getText().trim().equals("")){
                Valid.textKosong(jualbebas,"Keuntungan di Penjualan Bebas");
            }else if(karyawan.getText().trim().equals("")){
                Valid.textKosong(karyawan,"Keuntungan Jika Pasien/Pembeli Karyawan");
            }else if(kdjns.getText().trim().equals("")||nmjns.getText().trim().equals("")){
                Valid.textKosong(kdjns,"Jenis Barang");
            }else{
                Sequel.queryu("delete from setpenjualan where kdjns='"+kdjns.getText()+"'");
                Sequel.menyimpan("setpenjualan","'"+ralan.getText()+"','"+ranapk1.getText()+"','"+
                        ranapk2.getText()+"','"+ranapk3.getText()+"','"+ranaputama.getText()+"','"+
                        ranapvip.getText()+"','"+ranapvvip.getText()+"','"+beliluar.getText()+"','"+
                        jualbebas.getText()+"','"+karyawan.getText()+"','"+kdjns.getText()+"'","Set Harga");
                tampil();
                emptTeks();
            }
        }else if(TabSetting.getSelectedIndex()==3){
            if(ralanperbarang.getText().trim().equals("")){
                Valid.textKosong(ralanperbarang,"Keuntungan di Rawat Jalan");
            }else if(ranapk1perbarang.getText().trim().equals("")){
                Valid.textKosong(ranapk1perbarang,"Keuntungan di Ranap Kelas 1");
            }else if(ranapk2perbarang.getText().trim().equals("")){
                Valid.textKosong(ranapk2perbarang,"Keuntungan di Ranap Kelas 2");
            }else if(ranapk3perbarang.getText().trim().equals("")){
                Valid.textKosong(ranapk3perbarang,"Keuntungan di Ranap Kelas 3");
            }else if(ranaputamaperbarang.getText().trim().equals("")){
                Valid.textKosong(ranaputamaperbarang,"Keuntungan di Ranap Kelas Utama");
            }else if(ranapvipperbarang.getText().trim().equals("")){
                Valid.textKosong(ranapvipperbarang,"Keuntungan di Ranap Kelas VIP");
            }else if(ranapvvipperbarang.getText().trim().equals("")){
                Valid.textKosong(ranapvvipperbarang,"Keuntungan di Ranap Kelas VVIP");
            }else if(beliluarperbarang.getText().trim().equals("")){
                Valid.textKosong(beliluarperbarang,"Keuntungan Jika Beli dari Apotek Lain");
            }else if(jualbebasperbarang.getText().trim().equals("")){
                Valid.textKosong(jualbebasperbarang,"Keuntungan di Penjualan Bebas");
            }else if(karyawanperbarang.getText().trim().equals("")){
                Valid.textKosong(karyawanperbarang,"Keuntungan Jika Pasien/Pembeli Karyawan");
            }else if(kdbarang.getText().trim().equals("")||nmbarang.getText().trim().equals("")){
                Valid.textKosong(kdbarang,"Barang/Alkes/BHP");
            }else{            
                Sequel.queryu("delete from setpenjualanperbarang where kode_brng='"+kdbarang.getText()+"'");
                Sequel.menyimpan("setpenjualanperbarang","'"+ralanperbarang.getText()+"','"+ranapk1perbarang.getText()+"','"+
                        ranapk2perbarang.getText()+"','"+ranapk3perbarang.getText()+"','"+ranaputamaperbarang.getText()+"','"+
                        ranapvipperbarang.getText()+"','"+ranapvvipperbarang.getText()+"','"+beliluarperbarang.getText()+"','"+
                        jualbebasperbarang.getText()+"','"+karyawanperbarang.getText()+"','"+kdbarang.getText()+"'","Set Harga");
                tampilpengaturanhargaperbarang();
                emptTeks();
            }
        }              
    }//GEN-LAST:event_BtnEditActionPerformed

    private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnEditActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnKeluar);
        }
    }//GEN-LAST:event_BtnEditKeyPressed

    private void jualbebasMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jualbebasMouseMoved
        if(jualbebas.getText().equals("0")||jualbebas.getText().equals("0.0")){
            jualbebas.setText("");
        }
    }//GEN-LAST:event_jualbebasMouseMoved

    private void jualbebasMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jualbebasMouseExited
        if(jualbebas.getText().equals("")){
            jualbebas.setText("0");
        }
    }//GEN-LAST:event_jualbebasMouseExited

    private void jualbebasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jualbebasKeyPressed
        Valid.pindah(evt,beliluar,karyawan);
    }//GEN-LAST:event_jualbebasKeyPressed

    private void karyawanMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_karyawanMouseMoved
        if(karyawan.getText().equals("0")||karyawan.getText().equals("0.0")){
            karyawan.setText("");
        }
    }//GEN-LAST:event_karyawanMouseMoved

    private void karyawanMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_karyawanMouseExited
        if(karyawan.getText().equals("")){
            karyawan.setText("0");
        }
    }//GEN-LAST:event_karyawanMouseExited

    private void karyawanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_karyawanKeyPressed
        Valid.pindah(evt,jualbebas,kdjns);
    }//GEN-LAST:event_karyawanKeyPressed

    private void ralanMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ralanMouseMoved
        if(ralan.getText().equals("0")||ralan.getText().equals("0.0")){
            ralan.setText("");
        }
    }//GEN-LAST:event_ralanMouseMoved

    private void ralanMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ralanMouseExited
        if(ralan.getText().equals("")){
            ralan.setText("0");
        }
    }//GEN-LAST:event_ralanMouseExited

    private void ralanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ralanKeyPressed
        Valid.pindah(evt,BtnSimpan,ranapk1);
    }//GEN-LAST:event_ralanKeyPressed

    private void ranapk1MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ranapk1MouseMoved
        if(ranapk1.getText().equals("0")||ranapk1.getText().equals("0.0")){
            ranapk1.setText("");
        }
    }//GEN-LAST:event_ranapk1MouseMoved

    private void ranapk1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ranapk1MouseExited
        if(ranapk1.getText().equals("")){
            ranapk1.setText("0");
        }
    }//GEN-LAST:event_ranapk1MouseExited

    private void ranapk1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ranapk1KeyPressed
        Valid.pindah(evt,ralan,ranapk2);
    }//GEN-LAST:event_ranapk1KeyPressed

    private void ranapk2MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ranapk2MouseMoved
        if(ranapk2.getText().equals("0")||ranapk2.getText().equals("0.0")){
            ranapk2.setText("");
        }
    }//GEN-LAST:event_ranapk2MouseMoved

    private void ranapk2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ranapk2MouseExited
        if(ranapk2.getText().equals("")){
            ranapk2.setText("0");
        }
    }//GEN-LAST:event_ranapk2MouseExited

    private void ranapk2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ranapk2KeyPressed
        Valid.pindah(evt,ranapk1,ranapk3);
    }//GEN-LAST:event_ranapk2KeyPressed

    private void ranapk3MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ranapk3MouseMoved
        if(ranapk3.getText().equals("0")||ranapk3.getText().equals("0.0")){
            ranapk3.setText("");
        }
    }//GEN-LAST:event_ranapk3MouseMoved

    private void ranapk3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ranapk3MouseExited
        if(ranapk3.getText().equals("")){
            ranapk3.setText("0");
        }
    }//GEN-LAST:event_ranapk3MouseExited

    private void ranapk3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ranapk3KeyPressed
        Valid.pindah(evt,ranapk2,ranaputama);
    }//GEN-LAST:event_ranapk3KeyPressed

    private void ranaputamaMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ranaputamaMouseMoved
        if(ranaputama.getText().equals("0")||ranaputama.getText().equals("0.0")){
            ranaputama.setText("");
        }
    }//GEN-LAST:event_ranaputamaMouseMoved

    private void ranaputamaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ranaputamaMouseExited
        if(ranaputama.getText().equals("")){
            ranaputama.setText("0");
        }
    }//GEN-LAST:event_ranaputamaMouseExited

    private void ranaputamaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ranaputamaKeyPressed
        Valid.pindah(evt,ranapk3,ranapvip);
    }//GEN-LAST:event_ranaputamaKeyPressed

    private void ppUPdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppUPdateActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        for (int i = 0; i < tbAdmin.getRowCount(); i++) {             
            Sequel.queryu2("update databarang set ralan=round(h_beli+(h_beli*("+tbAdmin.getValueAt(i,0).toString()+"/100))),"+
                "kelas1=round(h_beli+(h_beli*("+tbAdmin.getValueAt(i,1).toString()+"/100))),"+
                "kelas2=round(h_beli+(h_beli*("+tbAdmin.getValueAt(i,2).toString()+"/100))),"+
                "kelas3=round(h_beli+(h_beli*("+tbAdmin.getValueAt(i,3).toString()+"/100))),"+
                "utama=round(h_beli+(h_beli*("+tbAdmin.getValueAt(i,4).toString()+"/100))),"+
                "vip=round(h_beli+(h_beli*("+tbAdmin.getValueAt(i,5).toString()+"/100))),"+
                "vvip=round(h_beli+(h_beli*("+tbAdmin.getValueAt(i,6).toString()+"/100))),"+
                "beliluar=round(h_beli+(h_beli*("+tbAdmin.getValueAt(i,7).toString()+"/100))),"+
                "jualbebas=round(h_beli+(h_beli*("+tbAdmin.getValueAt(i,8).toString()+"/100))),"+
                "karyawan=round(h_beli+(h_beli*("+tbAdmin.getValueAt(i,9).toString()+"/100))) where "+
                "kdjns='"+tbAdmin.getValueAt(i,10).toString()+"'");                           
        }            
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_ppUPdateActionPerformed

    private void kdjnsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdjnsKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            Sequel.cariIsi("select nama from jenis where kdjns=?", nmjns, kdjns.getText());
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            Sequel.cariIsi("select nama from jenis where kdjns=?", nmjns, kdjns.getText());
            karyawan.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            Sequel.cariIsi("select nama from jenis where kdjns=?", nmjns, kdjns.getText());
            BtnSimpan.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            BtnJenisActionPerformed(null);
        }
    }//GEN-LAST:event_kdjnsKeyPressed

    private void BtnJenisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnJenisActionPerformed
        jenis.isCek();
        jenis.setSize(internalFrame1.getWidth() - 40, internalFrame1.getHeight() - 40);
        jenis.setLocationRelativeTo(internalFrame1);
        jenis.setAlwaysOnTop(false);
        jenis.setVisible(true);
    }//GEN-LAST:event_BtnJenisActionPerformed

    private void TabSettingMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabSettingMouseClicked
        if(TabSetting.getSelectedIndex()==0){
            tampilpengaturanumum();
        }else if(TabSetting.getSelectedIndex()==1){
            tampilpengaturanhargaumum();
        }else if(TabSetting.getSelectedIndex()==2){
            tampil();
        }else if(TabSetting.getSelectedIndex()==3){
            tampilpengaturanhargaperbarang();
        }
    }//GEN-LAST:event_TabSettingMouseClicked

    private void tbPengaturanUmumMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPengaturanUmumMouseClicked
        if(tabModePengaturanUmum.getRowCount()!=0){
            try {
                getDataPengaturanUmum();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbPengaturanUmumMouseClicked

    private void tbPengaturanUmumKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbPengaturanUmumKeyPressed
        if(tabModePengaturanUmum.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getDataPengaturanUmum();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }//GEN-LAST:event_tbPengaturanUmumKeyPressed

    private void cmbPengaturanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbPengaturanKeyPressed
        Valid.pindah(evt,BtnSimpan,cmbHargaDasar);
    }//GEN-LAST:event_cmbPengaturanKeyPressed

    private void ranapvipumumMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ranapvipumumMouseMoved
        if(ranapvipumum.getText().equals("0")||ranapvipumum.getText().equals("0.0")){
            ranapvipumum.setText("");
        }
    }//GEN-LAST:event_ranapvipumumMouseMoved

    private void ranapvipumumMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ranapvipumumMouseExited
        if(ranapvipumum.getText().equals("")){
            ranapvipumum.setText("0");
        }
    }//GEN-LAST:event_ranapvipumumMouseExited

    private void ranapvipumumKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ranapvipumumKeyPressed
        Valid.pindah(evt,ranaputamaumum,ranapvvipumum);
    }//GEN-LAST:event_ranapvipumumKeyPressed

    private void ranapvvipumumMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ranapvvipumumMouseMoved
        if(ranapvvipumum.getText().equals("0")||ranapvvipumum.getText().equals("0.0")){
            ranapvvipumum.setText("");
        }
    }//GEN-LAST:event_ranapvvipumumMouseMoved

    private void ranapvvipumumMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ranapvvipumumMouseExited
        if(ranapvvipumum.getText().equals("")){
            ranapvvipumum.setText("0");
        }
    }//GEN-LAST:event_ranapvvipumumMouseExited

    private void ranapvvipumumKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ranapvvipumumKeyPressed
        Valid.pindah(evt,ranapvipumum,beliluarumum);
    }//GEN-LAST:event_ranapvvipumumKeyPressed

    private void beliluarumumMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_beliluarumumMouseMoved
        if(beliluarumum.getText().equals("0")||beliluarumum.getText().equals("0.0")){
            beliluarumum.setText("");
        }
    }//GEN-LAST:event_beliluarumumMouseMoved

    private void beliluarumumMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_beliluarumumMouseExited
        if(beliluarumum.getText().equals("")){
            beliluarumum.setText("0");
        }
    }//GEN-LAST:event_beliluarumumMouseExited

    private void beliluarumumKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_beliluarumumKeyPressed
        Valid.pindah(evt,ranapvvipumum,jualbebasumum);
    }//GEN-LAST:event_beliluarumumKeyPressed

    private void jualbebasumumMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jualbebasumumMouseMoved
        if(jualbebasumum.getText().equals("0")||jualbebasumum.getText().equals("0.0")){
            jualbebasumum.setText("");
        }
    }//GEN-LAST:event_jualbebasumumMouseMoved

    private void jualbebasumumMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jualbebasumumMouseExited
        if(jualbebasumum.getText().equals("")){
            jualbebasumum.setText("0");
        }
    }//GEN-LAST:event_jualbebasumumMouseExited

    private void jualbebasumumKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jualbebasumumKeyPressed
        Valid.pindah(evt,beliluarumum,karyawanumum);
    }//GEN-LAST:event_jualbebasumumKeyPressed

    private void karyawanumumMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_karyawanumumMouseMoved
        if(karyawanumum.getText().equals("0")||karyawanumum.getText().equals("0.0")){
            karyawanumum.setText("");
        }
    }//GEN-LAST:event_karyawanumumMouseMoved

    private void karyawanumumMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_karyawanumumMouseExited
        if(karyawanumum.getText().equals("")){
            karyawanumum.setText("0");
        }
    }//GEN-LAST:event_karyawanumumMouseExited

    private void karyawanumumKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_karyawanumumKeyPressed
        Valid.pindah(evt,jualbebasumum,BtnSimpan);
    }//GEN-LAST:event_karyawanumumKeyPressed

    private void ralanumumMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ralanumumMouseMoved
        if(ralanumum.getText().equals("0")||ralanumum.getText().equals("0.0")){
            ralanumum.setText("");
        }
    }//GEN-LAST:event_ralanumumMouseMoved

    private void ralanumumMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ralanumumMouseExited
        if(ralanumum.getText().equals("")){
            ralanumum.setText("0");
        }
    }//GEN-LAST:event_ralanumumMouseExited

    private void ralanumumKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ralanumumKeyPressed
        Valid.pindah(evt,BtnSimpan,ranapk1umum);
    }//GEN-LAST:event_ralanumumKeyPressed

    private void ranapk1umumMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ranapk1umumMouseMoved
        if(ranapk1umum.getText().equals("0")||ranapk1umum.getText().equals("0.0")){
            ranapk1umum.setText("");
        }
    }//GEN-LAST:event_ranapk1umumMouseMoved

    private void ranapk1umumMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ranapk1umumMouseExited
        if(ranapk1umum.getText().equals("")){
            ranapk1umum.setText("0");
        }
    }//GEN-LAST:event_ranapk1umumMouseExited

    private void ranapk1umumKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ranapk1umumKeyPressed
        Valid.pindah(evt,ralanumum,ranapk2umum);
    }//GEN-LAST:event_ranapk1umumKeyPressed

    private void ranapk2umumMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ranapk2umumMouseMoved
        if(ranapk2umum.getText().equals("0")||ranapk2umum.getText().equals("0.0")){
            ranapk2umum.setText("");
        }
    }//GEN-LAST:event_ranapk2umumMouseMoved

    private void ranapk2umumMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ranapk2umumMouseExited
        if(ranapk2umum.getText().equals("")){
            ranapk2umum.setText("0");
        }
    }//GEN-LAST:event_ranapk2umumMouseExited

    private void ranapk2umumKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ranapk2umumKeyPressed
        Valid.pindah(evt,ranapk1umum,ranapk3umum);
    }//GEN-LAST:event_ranapk2umumKeyPressed

    private void ranapk3umumMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ranapk3umumMouseMoved
        if(ranapk3umum.getText().equals("0")||ranapk3umum.getText().equals("0.0")){
            ranapk3umum.setText("");
        }
    }//GEN-LAST:event_ranapk3umumMouseMoved

    private void ranapk3umumMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ranapk3umumMouseExited
        if(ranapk3umum.getText().equals("")){
            ranapk3umum.setText("0");
        }
    }//GEN-LAST:event_ranapk3umumMouseExited

    private void ranapk3umumKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ranapk3umumKeyPressed
        Valid.pindah(evt,ranapk2umum,ranaputamaumum);
    }//GEN-LAST:event_ranapk3umumKeyPressed

    private void ranaputamaumumMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ranaputamaumumMouseMoved
        if(ranaputamaumum.getText().equals("0")||ranaputamaumum.getText().equals("0.0")){
            ranaputamaumum.setText("");
        }
    }//GEN-LAST:event_ranaputamaumumMouseMoved

    private void ranaputamaumumMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ranaputamaumumMouseExited
        if(ranaputamaumum.getText().equals("")){
            ranaputamaumum.setText("0");
        }
    }//GEN-LAST:event_ranaputamaumumMouseExited

    private void ranaputamaumumKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ranaputamaumumKeyPressed
        Valid.pindah(evt,ranapk3umum,ranapvipumum);
    }//GEN-LAST:event_ranaputamaumumKeyPressed

    private void tbPengaturanHargaUmumMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPengaturanHargaUmumMouseClicked
        if(tabModePengaturanHargaUmum.getRowCount()!=0){
            try {
                getDataPengaturanHargaUmum();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbPengaturanHargaUmumMouseClicked

    private void tbPengaturanHargaUmumKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbPengaturanHargaUmumKeyPressed
        if(tabModePengaturanHargaUmum.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getDataPengaturanHargaUmum();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }//GEN-LAST:event_tbPengaturanHargaUmumKeyPressed

    private void ranapvipperbarangMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ranapvipperbarangMouseMoved
        if(ranapvipperbarang.getText().equals("0")||ranapvipperbarang.getText().equals("0.0")){
            ranapvipperbarang.setText("");
        }
    }//GEN-LAST:event_ranapvipperbarangMouseMoved

    private void ranapvipperbarangMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ranapvipperbarangMouseExited
        if(ranapvipperbarang.getText().equals("")){
            ranapvipperbarang.setText("0");
        }
    }//GEN-LAST:event_ranapvipperbarangMouseExited

    private void ranapvipperbarangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ranapvipperbarangKeyPressed
        Valid.pindah(evt,ranaputamaperbarang,ranapvvipperbarang);
    }//GEN-LAST:event_ranapvipperbarangKeyPressed

    private void ranapvvipperbarangMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ranapvvipperbarangMouseMoved
        if(ranapvvipperbarang.getText().equals("0")||ranapvvipperbarang.getText().equals("0.0")){
            ranapvvipperbarang.setText("");
        }
    }//GEN-LAST:event_ranapvvipperbarangMouseMoved

    private void ranapvvipperbarangMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ranapvvipperbarangMouseExited
        if(ranapvvipperbarang.getText().equals("")){
            ranapvvipperbarang.setText("0");
        }
    }//GEN-LAST:event_ranapvvipperbarangMouseExited

    private void ranapvvipperbarangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ranapvvipperbarangKeyPressed
        Valid.pindah(evt,ranapvipperbarang,beliluarperbarang);
    }//GEN-LAST:event_ranapvvipperbarangKeyPressed

    private void beliluarperbarangMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_beliluarperbarangMouseMoved
        if(beliluarperbarang.getText().equals("0")||beliluarperbarang.getText().equals("0.0")){
            beliluarperbarang.setText("");
        }
    }//GEN-LAST:event_beliluarperbarangMouseMoved

    private void beliluarperbarangMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_beliluarperbarangMouseExited
        if(beliluarperbarang.getText().equals("")){
            beliluarperbarang.setText("0");
        }
    }//GEN-LAST:event_beliluarperbarangMouseExited

    private void beliluarperbarangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_beliluarperbarangKeyPressed
        Valid.pindah(evt,ranapvvipperbarang,jualbebasperbarang);
    }//GEN-LAST:event_beliluarperbarangKeyPressed

    private void jualbebasperbarangMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jualbebasperbarangMouseMoved
        if(jualbebasperbarang.getText().equals("0")||jualbebasperbarang.getText().equals("0.0")){
            jualbebasperbarang.setText("");
        }
    }//GEN-LAST:event_jualbebasperbarangMouseMoved

    private void jualbebasperbarangMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jualbebasperbarangMouseExited
        if(jualbebasperbarang.getText().equals("")){
            jualbebasperbarang.setText("0");
        }
    }//GEN-LAST:event_jualbebasperbarangMouseExited

    private void jualbebasperbarangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jualbebasperbarangKeyPressed
        Valid.pindah(evt,beliluarperbarang,karyawanperbarang);
    }//GEN-LAST:event_jualbebasperbarangKeyPressed

    private void karyawanperbarangMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_karyawanperbarangMouseMoved
        if(karyawanperbarang.getText().equals("0")||karyawanperbarang.getText().equals("0.0")){
            karyawanperbarang.setText("");
        }
    }//GEN-LAST:event_karyawanperbarangMouseMoved

    private void karyawanperbarangMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_karyawanperbarangMouseExited
        if(karyawanperbarang.getText().equals("")){
            karyawanperbarang.setText("0");
        }
    }//GEN-LAST:event_karyawanperbarangMouseExited

    private void karyawanperbarangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_karyawanperbarangKeyPressed
        Valid.pindah(evt,jualbebasperbarang,kdbarang);
    }//GEN-LAST:event_karyawanperbarangKeyPressed

    private void ralanperbarangMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ralanperbarangMouseMoved
        if(ralanperbarang.getText().equals("0")||ralanperbarang.getText().equals("0.0")){
            ralanperbarang.setText("");
        }
    }//GEN-LAST:event_ralanperbarangMouseMoved

    private void ralanperbarangMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ralanperbarangMouseExited
        if(ralanperbarang.getText().equals("")){
            ralanperbarang.setText("0");
        }
    }//GEN-LAST:event_ralanperbarangMouseExited

    private void ralanperbarangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ralanperbarangKeyPressed
        Valid.pindah(evt,BtnSimpan,ranapk1perbarang);
    }//GEN-LAST:event_ralanperbarangKeyPressed

    private void ranapk1perbarangMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ranapk1perbarangMouseMoved
        if(ranapk1perbarang.getText().equals("0")||ranapk1perbarang.getText().equals("0.0")){
            ranapk1perbarang.setText("");
        }
    }//GEN-LAST:event_ranapk1perbarangMouseMoved

    private void ranapk1perbarangMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ranapk1perbarangMouseExited
        if(ranapk1perbarang.getText().equals("")){
            ranapk1perbarang.setText("0");
        }
    }//GEN-LAST:event_ranapk1perbarangMouseExited

    private void ranapk1perbarangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ranapk1perbarangKeyPressed
        Valid.pindah(evt,ralanperbarang,ranapk2perbarang);
    }//GEN-LAST:event_ranapk1perbarangKeyPressed

    private void ranapk2perbarangMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ranapk2perbarangMouseMoved
        if(ranapk2perbarang.getText().equals("0")||ranapk2perbarang.getText().equals("0.0")){
            ranapk2perbarang.setText("");
        }
    }//GEN-LAST:event_ranapk2perbarangMouseMoved

    private void ranapk2perbarangMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ranapk2perbarangMouseExited
        if(ranapk2perbarang.getText().equals("")){
            ranapk2perbarang.setText("0");
        }
    }//GEN-LAST:event_ranapk2perbarangMouseExited

    private void ranapk2perbarangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ranapk2perbarangKeyPressed
        Valid.pindah(evt,ranapk1perbarang,ranapk3perbarang);
    }//GEN-LAST:event_ranapk2perbarangKeyPressed

    private void ranapk3perbarangMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ranapk3perbarangMouseMoved
        if(ranapk3perbarang.getText().equals("0")||ranapk3perbarang.getText().equals("0.0")){
            ranapk3perbarang.setText("");
        }
    }//GEN-LAST:event_ranapk3perbarangMouseMoved

    private void ranapk3perbarangMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ranapk3perbarangMouseExited
        if(ranapk3perbarang.getText().equals("")){
            ranapk3perbarang.setText("0");
        }
    }//GEN-LAST:event_ranapk3perbarangMouseExited

    private void ranapk3perbarangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ranapk3perbarangKeyPressed
        Valid.pindah(evt,ranapk2perbarang,ranaputamaperbarang);
    }//GEN-LAST:event_ranapk3perbarangKeyPressed

    private void ranaputamaperbarangMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ranaputamaperbarangMouseMoved
        if(ranaputamaperbarang.getText().equals("0")||ranaputamaperbarang.getText().equals("0.0")){
            ranaputamaperbarang.setText("");
        }
    }//GEN-LAST:event_ranaputamaperbarangMouseMoved

    private void ranaputamaperbarangMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ranaputamaperbarangMouseExited
        if(ranaputamaperbarang.getText().equals("")){
            ranaputamaperbarang.setText("0");
        }
    }//GEN-LAST:event_ranaputamaperbarangMouseExited

    private void ranaputamaperbarangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ranaputamaperbarangKeyPressed
        Valid.pindah(evt,ranapk3perbarang,ranapvipperbarang);
    }//GEN-LAST:event_ranaputamaperbarangKeyPressed

    private void kdbarangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdbarangKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            Sequel.cariIsi("select nama from jenis where kdjns=?", nmbarang, kdbarang.getText());
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            Sequel.cariIsi("select nama from jenis where kdjns=?", nmbarang, kdbarang.getText());
            karyawanperbarang.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            Sequel.cariIsi("select nama from jenis where kdjns=?", nmbarang, kdbarang.getText());
            BtnSimpan.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            BtnJenisActionPerformed(null);
        }
    }//GEN-LAST:event_kdbarangKeyPressed

    private void BtnbarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnbarangActionPerformed
        barang.emptTeks();
        barang.isCek();
        barang.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        barang.setLocationRelativeTo(internalFrame1);
        barang.setAlwaysOnTop(false);
        barang.setVisible(true);
    }//GEN-LAST:event_BtnbarangActionPerformed

    private void tbPengaturanHargaPerBarangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPengaturanHargaPerBarangMouseClicked
        if(tabModePengaturanHargaPerBarang.getRowCount()!=0){
            try {
                getDataPengaturanHargaPerBarang();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbPengaturanHargaPerBarangMouseClicked

    private void tbPengaturanHargaPerBarangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbPengaturanHargaPerBarangKeyPressed
        if(tabModePengaturanHargaPerBarang.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getDataPengaturanHargaPerBarang();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }//GEN-LAST:event_tbPengaturanHargaPerBarangKeyPressed

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
        tampil();
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
        tampil();
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnCari, TCari);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

    private void TCari1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCari1KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCari1ActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari1.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }
    }//GEN-LAST:event_TCari1KeyPressed

    private void BtnCari1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCari1ActionPerformed
        tampilpengaturanhargaperbarang();
    }//GEN-LAST:event_BtnCari1ActionPerformed

    private void BtnCari1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCari1KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari, BtnAll);
        }
    }//GEN-LAST:event_BtnCari1KeyPressed

    private void BtnAll1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAll1ActionPerformed
        TCari1.setText("");
        tampilpengaturanhargaperbarang();
    }//GEN-LAST:event_BtnAll1ActionPerformed

    private void BtnAll1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAll1KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnCari, TCari);
        }
    }//GEN-LAST:event_BtnAll1KeyPressed

    private void ppUPdate1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppUPdate1ActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        for (int i = 0; i < tbPengaturanHargaUmum.getRowCount(); i++) {             
            Sequel.queryu2("update databarang set ralan=round(h_beli+(h_beli*("+tbPengaturanHargaUmum.getValueAt(i,0).toString()+"/100))),"+
                "kelas1=round(h_beli+(h_beli*("+tbPengaturanHargaUmum.getValueAt(i,1).toString()+"/100))),"+
                "kelas2=round(h_beli+(h_beli*("+tbPengaturanHargaUmum.getValueAt(i,2).toString()+"/100))),"+
                "kelas3=round(h_beli+(h_beli*("+tbPengaturanHargaUmum.getValueAt(i,3).toString()+"/100))),"+
                "utama=round(h_beli+(h_beli*("+tbPengaturanHargaUmum.getValueAt(i,4).toString()+"/100))),"+
                "vip=round(h_beli+(h_beli*("+tbPengaturanHargaUmum.getValueAt(i,5).toString()+"/100))),"+
                "vvip=round(h_beli+(h_beli*("+tbPengaturanHargaUmum.getValueAt(i,6).toString()+"/100))),"+
                "beliluar=round(h_beli+(h_beli*("+tbPengaturanHargaUmum.getValueAt(i,7).toString()+"/100))),"+
                "jualbebas=round(h_beli+(h_beli*("+tbPengaturanHargaUmum.getValueAt(i,8).toString()+"/100))),"+
                "karyawan=round(h_beli+(h_beli*("+tbPengaturanHargaUmum.getValueAt(i,9).toString()+"/100)))");                           
        }            
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_ppUPdate1ActionPerformed

    private void ppUPdate2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppUPdate2ActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        for (int i = 0; i < tbPengaturanHargaPerBarang.getRowCount(); i++) {             
            Sequel.queryu2("update databarang set ralan=round(h_beli+(h_beli*("+tbPengaturanHargaPerBarang.getValueAt(i,0).toString()+"/100))),"+
                "kelas1=round(h_beli+(h_beli*("+tbPengaturanHargaPerBarang.getValueAt(i,1).toString()+"/100))),"+
                "kelas2=round(h_beli+(h_beli*("+tbPengaturanHargaPerBarang.getValueAt(i,2).toString()+"/100))),"+
                "kelas3=round(h_beli+(h_beli*("+tbPengaturanHargaPerBarang.getValueAt(i,3).toString()+"/100))),"+
                "utama=round(h_beli+(h_beli*("+tbPengaturanHargaPerBarang.getValueAt(i,4).toString()+"/100))),"+
                "vip=round(h_beli+(h_beli*("+tbPengaturanHargaPerBarang.getValueAt(i,5).toString()+"/100))),"+
                "vvip=round(h_beli+(h_beli*("+tbPengaturanHargaPerBarang.getValueAt(i,6).toString()+"/100))),"+
                "beliluar=round(h_beli+(h_beli*("+tbPengaturanHargaPerBarang.getValueAt(i,7).toString()+"/100))),"+
                "jualbebas=round(h_beli+(h_beli*("+tbPengaturanHargaPerBarang.getValueAt(i,8).toString()+"/100))),"+
                "karyawan=round(h_beli+(h_beli*("+tbPengaturanHargaPerBarang.getValueAt(i,9).toString()+"/100))) where "+
                "kode_brng='"+tbPengaturanHargaPerBarang.getValueAt(i,10).toString()+"'");                           
        }            
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_ppUPdate2ActionPerformed

    private void cmbHargaDasarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbHargaDasarKeyPressed
        Valid.pindah(evt,cmbPengaturan,BtnSimpan);
    }//GEN-LAST:event_cmbHargaDasarKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgSetHarga dialog = new DlgSetHarga(new javax.swing.JFrame(), true);
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
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnCari1;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnJenis;
    private widget.Button BtnKeluar;
    private widget.Button BtnSimpan;
    private widget.Button Btnbarang;
    private widget.Label LCount;
    private widget.Label LCount1;
    private javax.swing.JPopupMenu Popup;
    private javax.swing.JPopupMenu Popup1;
    private javax.swing.JPopupMenu Popup2;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll1;
    private widget.ScrollPane Scroll2;
    private widget.ScrollPane Scroll3;
    private widget.TextBox TCari;
    private widget.TextBox TCari1;
    private javax.swing.JTabbedPane TabSetting;
    private widget.TextBox beliluar;
    private widget.TextBox beliluarperbarang;
    private widget.TextBox beliluarumum;
    private widget.ComboBox cmbHargaDasar;
    private widget.ComboBox cmbPengaturan;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.InternalFrame internalFrame4;
    private widget.InternalFrame internalFrame5;
    private widget.Label jLabel5;
    private widget.Label jLabel6;
    private widget.TextBox jualbebas;
    private widget.TextBox jualbebasperbarang;
    private widget.TextBox jualbebasumum;
    private widget.TextBox karyawan;
    private widget.TextBox karyawanperbarang;
    private widget.TextBox karyawanumum;
    private widget.TextBox kdbarang;
    private widget.TextBox kdjns;
    private widget.Label label10;
    private widget.Label label11;
    private widget.Label label12;
    private widget.Label label20;
    private widget.Label label21;
    private widget.Label label34;
    private widget.Label label35;
    private widget.Label label36;
    private widget.Label label37;
    private widget.Label label38;
    private widget.Label label39;
    private widget.Label label40;
    private widget.Label label41;
    private widget.Label label42;
    private widget.Label label43;
    private widget.Label label44;
    private widget.Label label45;
    private widget.Label label46;
    private widget.Label label47;
    private widget.Label label48;
    private widget.Label label49;
    private widget.Label label50;
    private widget.Label label51;
    private widget.Label label52;
    private widget.Label label53;
    private widget.Label label54;
    private widget.Label label55;
    private widget.Label label56;
    private widget.Label label57;
    private widget.Label label58;
    private widget.Label label59;
    private widget.Label label60;
    private widget.Label label61;
    private widget.Label label62;
    private widget.Label label63;
    private widget.Label label9;
    private widget.TextBox nmbarang;
    private widget.TextBox nmjns;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi2;
    private widget.panelisi panelisi3;
    private widget.panelisi panelisi4;
    private widget.panelisi panelisi5;
    private widget.panelisi panelisi6;
    private javax.swing.JMenuItem ppUPdate;
    private javax.swing.JMenuItem ppUPdate1;
    private javax.swing.JMenuItem ppUPdate2;
    private widget.TextBox ralan;
    private widget.TextBox ralanperbarang;
    private widget.TextBox ralanumum;
    private widget.TextBox ranapk1;
    private widget.TextBox ranapk1perbarang;
    private widget.TextBox ranapk1umum;
    private widget.TextBox ranapk2;
    private widget.TextBox ranapk2perbarang;
    private widget.TextBox ranapk2umum;
    private widget.TextBox ranapk3;
    private widget.TextBox ranapk3perbarang;
    private widget.TextBox ranapk3umum;
    private widget.TextBox ranaputama;
    private widget.TextBox ranaputamaperbarang;
    private widget.TextBox ranaputamaumum;
    private widget.TextBox ranapvip;
    private widget.TextBox ranapvipperbarang;
    private widget.TextBox ranapvipumum;
    private widget.TextBox ranapvvip;
    private widget.TextBox ranapvvipperbarang;
    private widget.TextBox ranapvvipumum;
    private widget.Table tbAdmin;
    private widget.Table tbPengaturanHargaPerBarang;
    private widget.Table tbPengaturanHargaUmum;
    private widget.Table tbPengaturanUmum;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            ps=koneksi.prepareStatement(
                   "select setpenjualan.ralan, setpenjualan.kelas1, setpenjualan.kelas2, "+
                   "setpenjualan.kelas3, setpenjualan.utama, setpenjualan.vip, setpenjualan.vvip, "+
                   "setpenjualan.beliluar, setpenjualan.jualbebas, setpenjualan.karyawan, "+
                   "setpenjualan.kdjns,jenis.nama from setpenjualan inner join jenis on "+
                   "setpenjualan.kdjns=jenis.kdjns where jenis.nama like ? order by jenis.nama");
            try {
                ps.setString(1,"%"+TCari.getText().trim()+"%");
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new Object[]{
                        rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),
                        rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9),rs.getString(10),
                        rs.getString(11),rs.getString(12)
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
            LCount.setText(""+tabMode.getRowCount());
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }
    
    private void tampilpengaturanumum() {
        Valid.tabelKosong(tabModePengaturanUmum);
        try{
            ps=koneksi.prepareStatement(
                   "select * from set_harga_obat");
            try {
                rs=ps.executeQuery();
                while(rs.next()){
                    tabModePengaturanUmum.addRow(new Object[]{
                        rs.getString(1),rs.getString(2)
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
    }
    
    private void tampilpengaturanhargaumum() {
        Valid.tabelKosong(tabModePengaturanHargaUmum);
        try{
            ps=koneksi.prepareStatement(
                   "select setpenjualanumum.ralan, setpenjualanumum.kelas1, setpenjualanumum.kelas2, "+
                   "setpenjualanumum.kelas3, setpenjualanumum.utama, setpenjualanumum.vip, setpenjualanumum.vvip, "+
                   "setpenjualanumum.beliluar, setpenjualanumum.jualbebas, setpenjualanumum.karyawan from setpenjualanumum");
            try {
                rs=ps.executeQuery();
                while(rs.next()){
                    tabModePengaturanHargaUmum.addRow(new Object[]{
                        rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),
                        rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9),rs.getString(10),
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
    }

    private void tampilpengaturanhargaperbarang() {
        Valid.tabelKosong(tabModePengaturanHargaPerBarang);
        try{
            ps=koneksi.prepareStatement(
                   "select setpenjualanperbarang.ralan, setpenjualanperbarang.kelas1, setpenjualanperbarang.kelas2, "+
                   "setpenjualanperbarang.kelas3, setpenjualanperbarang.utama, setpenjualanperbarang.vip, setpenjualanperbarang.vvip, "+
                   "setpenjualanperbarang.beliluar, setpenjualanperbarang.jualbebas, setpenjualanperbarang.karyawan, "+
                   "setpenjualanperbarang.kode_brng,databarang.nama_brng from setpenjualanperbarang inner join databarang on "+
                   "setpenjualanperbarang.kode_brng=databarang.kode_brng where databarang.status='1' and databarang.nama_brng like ? order by databarang.nama_brng");
            try {
                ps.setString(1,"%"+TCari1.getText().trim()+"%");
                rs=ps.executeQuery();
                while(rs.next()){
                    tabModePengaturanHargaPerBarang.addRow(new Object[]{
                        rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),
                        rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9),rs.getString(10),
                        rs.getString(11),rs.getString(12)
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
            LCount1.setText(""+tabModePengaturanHargaPerBarang.getRowCount());
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }
    
    private void getData() {
        int row=tbAdmin.getSelectedRow();
        if(row!= -1){
            ralan.setText(tabMode.getValueAt(row,0).toString());
            ranapk1.setText(tabMode.getValueAt(row,1).toString());
            ranapk2.setText(tabMode.getValueAt(row,2).toString());
            ranapk3.setText(tabMode.getValueAt(row,3).toString());
            ranaputama.setText(tabMode.getValueAt(row,4).toString());
            ranapvip.setText(tabMode.getValueAt(row,5).toString());
            ranapvvip.setText(tabMode.getValueAt(row,6).toString());
            beliluar.setText(tabMode.getValueAt(row,7).toString());
            jualbebas.setText(tabMode.getValueAt(row,8).toString());
            karyawan.setText(tabMode.getValueAt(row,9).toString());
            kdjns.setText(tabMode.getValueAt(row,10).toString());
            nmjns.setText(tabMode.getValueAt(row,11).toString());
        }
    }
    
    private void getDataPengaturanHargaUmum() {
        int row=tbPengaturanHargaUmum.getSelectedRow();
        if(row!= -1){
            ralanumum.setText(tabModePengaturanHargaUmum.getValueAt(row,0).toString());
            ranapk1umum.setText(tabModePengaturanHargaUmum.getValueAt(row,1).toString());
            ranapk2umum.setText(tabModePengaturanHargaUmum.getValueAt(row,2).toString());
            ranapk3umum.setText(tabModePengaturanHargaUmum.getValueAt(row,3).toString());
            ranaputamaumum.setText(tabModePengaturanHargaUmum.getValueAt(row,4).toString());
            ranapvipumum.setText(tabModePengaturanHargaUmum.getValueAt(row,5).toString());
            ranapvvipumum.setText(tabModePengaturanHargaUmum.getValueAt(row,6).toString());
            beliluarumum.setText(tabModePengaturanHargaUmum.getValueAt(row,7).toString());
            jualbebasumum.setText(tabModePengaturanHargaUmum.getValueAt(row,8).toString());
            karyawanumum.setText(tabModePengaturanHargaUmum.getValueAt(row,9).toString());
        }
    }
    
    private void getDataPengaturanUmum() {
        int row=tbPengaturanUmum.getSelectedRow();
        if(row!= -1){
            cmbPengaturan.setSelectedItem(tabModePengaturanUmum.getValueAt(row,0).toString());
            cmbHargaDasar.setSelectedItem(tabModePengaturanUmum.getValueAt(row,1).toString());
        }
    }

    private void getDataPengaturanHargaPerBarang() {
        int row=tbPengaturanHargaPerBarang.getSelectedRow();
        if(row!= -1){
            ralanperbarang.setText(tabModePengaturanHargaPerBarang.getValueAt(row,0).toString());
            ranapk1perbarang.setText(tabModePengaturanHargaPerBarang.getValueAt(row,1).toString());
            ranapk2perbarang.setText(tabModePengaturanHargaPerBarang.getValueAt(row,2).toString());
            ranapk3perbarang.setText(tabModePengaturanHargaPerBarang.getValueAt(row,3).toString());
            ranaputamaperbarang.setText(tabModePengaturanHargaPerBarang.getValueAt(row,4).toString());
            ranapvipperbarang.setText(tabModePengaturanHargaPerBarang.getValueAt(row,5).toString());
            ranapvvipperbarang.setText(tabModePengaturanHargaPerBarang.getValueAt(row,6).toString());
            beliluarperbarang.setText(tabModePengaturanHargaPerBarang.getValueAt(row,7).toString());
            jualbebasperbarang.setText(tabModePengaturanHargaPerBarang.getValueAt(row,8).toString());
            karyawanperbarang.setText(tabModePengaturanHargaPerBarang.getValueAt(row,9).toString());
            kdbarang.setText(tabModePengaturanHargaPerBarang.getValueAt(row,10).toString());
            nmbarang.setText(tabModePengaturanHargaPerBarang.getValueAt(row,11).toString());
        }
    }
    
    public void emptTeks() {
        if(TabSetting.getSelectedIndex()==0){
            cmbPengaturan.requestFocus();
        }else if(TabSetting.getSelectedIndex()==1){
            ralanumum.setText("0");
            ranapk1umum.setText("0");
            ranapk2umum.setText("0");
            ranapk3umum.setText("0");
            ranaputamaumum.setText("0");
            ranapvipumum.setText("0");
            ranapvvipumum.setText("0");
            beliluarumum.setText("0");
            jualbebasumum.setText("0");
            karyawanumum.setText("0");
            ralanumum.requestFocus();
        }else if(TabSetting.getSelectedIndex()==2){
            ralan.setText("0");
            ranapk1.setText("0");
            ranapk2.setText("0");
            ranapk3.setText("0");
            ranaputama.setText("0");
            ranapvip.setText("0");
            ranapvvip.setText("0");
            beliluar.setText("0");
            jualbebas.setText("0");
            karyawan.setText("0");
            kdjns.setText("");
            nmjns.setText("");
            ralan.requestFocus();
        }else if(TabSetting.getSelectedIndex()==3){
            ralanperbarang.setText("0");
            ranapk1perbarang.setText("0");
            ranapk2perbarang.setText("0");
            ranapk3perbarang.setText("0");
            ranaputamaperbarang.setText("0");
            ranapvipperbarang.setText("0");
            ranapvvipperbarang.setText("0");
            beliluarperbarang.setText("0");
            jualbebasperbarang.setText("0");
            karyawanperbarang.setText("0");
            kdbarang.setText("");
            nmbarang.setText("");
            ralanperbarang.requestFocus();
        }            
    }
}

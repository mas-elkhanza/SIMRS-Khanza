/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgJnsPerawatan.java
 *
 * Created on May 22, 2010, 11:58:21 PM
 */

package toko;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import inventory.DlgCariSatuan;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
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
import restore.DlgRestoreIPSRSBarang;

/**
 *
 * @author dosen
 */
public final class TokoBarang extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private Connection koneksi=koneksiDB.condb();
    public DlgCariSatuan satuan=new DlgCariSatuan(null,false); 
    public TokoCariJenis jenis=new TokoCariJenis(null,false);

    /** Creates new form DlgJnsPerawatan
     * @param parent
     * @param modal */
    public TokoBarang(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        this.setLocation(8,1);
        setSize(628,674);

        Object[] row={"Kode Barang","Nama Barang","Satuan","Jenis","Stok","H. Dasar","H. Beli","H. Distributor","H. Grosir","H. Retail"};
        tabMode=new DefaultTableModel(null,row){
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                boolean a = false;
                return a;
            }
            Class[] types = new Class[]{
                java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,java.lang.Double.class,
                java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,java.lang.Double.class
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        tbJnsPerawatan.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbJnsPerawatan.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbJnsPerawatan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 10; i++) {
            TableColumn column = tbJnsPerawatan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(90);
            }else if(i==1){
                column.setPreferredWidth(200);
            }else if(i==2){
                column.setPreferredWidth(90);
            }else if(i==3){
                column.setPreferredWidth(110);
            }else if(i==4){
                column.setPreferredWidth(40);
            }else if(i==5){
                column.setPreferredWidth(80);
            }else if(i==6){
                column.setPreferredWidth(80);
            }else if(i==7){
                column.setPreferredWidth(80);
            }else if(i==8){
                column.setPreferredWidth(80);
            }else if(i==9){
                column.setPreferredWidth(80);
            }
        }
        tbJnsPerawatan.setDefaultRenderer(Object.class, new WarnaTable());

        kode_brng.setDocument(new batasInput((byte)10).getKata(kode_brng));
        nama_brng.setDocument(new batasInput((byte)80).getKata(nama_brng));
        kode_sat.setDocument(new batasInput((byte)4).getKata(kode_sat));
        stok.setDocument(new batasInput((byte)10).getKata(stok));
        dasar.setDocument(new batasInput((byte)20).getKata(dasar));
        beli.setDocument(new batasInput((byte)20).getKata(beli));
        distributor.setDocument(new batasInput((byte)20).getKata(distributor));
        grosir.setDocument(new batasInput((byte)20).getKata(grosir));
        retail.setDocument(new batasInput((byte)20).getKata(retail));
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
        ChkInput.setSelected(false);
        isForm();    
        
        satuan.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("TokoBarang")){
                    if(satuan.getTable().getSelectedRow()!= -1){                   
                        kode_sat.setText(satuan.getTable().getValueAt(satuan.getTable().getSelectedRow(),0).toString());                    
                        nama_sat.setText(satuan.getTable().getValueAt(satuan.getTable().getSelectedRow(),1).toString());
                    }   
                    kode_sat.requestFocus();
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
        
        jenis.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("TokoBarang")){
                    if(jenis.getTable().getSelectedRow()!= -1){                   
                        kdjenis.setText(jenis.getTable().getValueAt(jenis.getTable().getSelectedRow(),0).toString());                    
                        nmjenis.setText(jenis.getTable().getValueAt(jenis.getTable().getSelectedRow(),1).toString());
                    }   
                    kdjenis.requestFocus();
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
        MnRestore = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbJnsPerawatan = new widget.Table();
        jPanel3 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        BtnPrint = new widget.Button();
        BtnAll = new widget.Button();
        BtnKeluar = new widget.Button();
        panelGlass9 = new widget.panelisi();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();
        PanelInput = new javax.swing.JPanel();
        FormInput = new widget.PanelBiasa();
        label1 = new widget.Label();
        kode_brng = new widget.TextBox();
        nama_brng = new widget.TextBox();
        label7 = new widget.Label();
        label10 = new widget.Label();
        label19 = new widget.Label();
        kode_sat = new widget.TextBox();
        nama_sat = new widget.TextBox();
        btnSatuan = new widget.Button();
        label20 = new widget.Label();
        stok = new widget.TextBox();
        dasar = new widget.TextBox();
        label2 = new widget.Label();
        kdjenis = new widget.TextBox();
        nmjenis = new widget.TextBox();
        btnJenis = new widget.Button();
        label3 = new widget.Label();
        beli = new widget.TextBox();
        label4 = new widget.Label();
        distributor = new widget.TextBox();
        label5 = new widget.Label();
        grosir = new widget.TextBox();
        label6 = new widget.Label();
        retail = new widget.TextBox();
        ChkInput = new widget.CekBox();

        Popup.setName("Popup"); // NOI18N

        MnRestore.setBackground(new java.awt.Color(255, 255, 254));
        MnRestore.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRestore.setForeground(new java.awt.Color(50, 50, 50));
        MnRestore.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRestore.setText("Data Sampah");
        MnRestore.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnRestore.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnRestore.setName("MnRestore"); // NOI18N
        MnRestore.setPreferredSize(new java.awt.Dimension(200, 28));
        MnRestore.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRestoreActionPerformed(evt);
            }
        });
        Popup.add(MnRestore);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Barang Toko / Minimarket / Koperasi ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setComponentPopupMenu(Popup);
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbJnsPerawatan.setAutoCreateRowSorter(true);
        tbJnsPerawatan.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbJnsPerawatan.setComponentPopupMenu(Popup);
        tbJnsPerawatan.setName("tbJnsPerawatan"); // NOI18N
        tbJnsPerawatan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbJnsPerawatanMouseClicked(evt);
            }
        });
        tbJnsPerawatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbJnsPerawatanKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbJnsPerawatan);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(44, 100));
        jPanel3.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

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
        panelGlass8.add(BtnSimpan);

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
        panelGlass8.add(BtnBatal);

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
        panelGlass8.add(BtnHapus);

        BtnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/inventaris.png"))); // NOI18N
        BtnEdit.setMnemonic('G');
        BtnEdit.setText("Ganti");
        BtnEdit.setToolTipText("Alt+G");
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
        panelGlass8.add(BtnEdit);

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
        panelGlass8.add(BtnPrint);

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
        panelGlass8.add(BtnAll);

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
        panelGlass8.add(BtnKeluar);

        jPanel3.add(panelGlass8, java.awt.BorderLayout.CENTER);

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(450, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass9.add(TCari);

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
        panelGlass9.add(BtnCari);

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(75, 23));
        panelGlass9.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass9.add(LCount);

        jPanel3.add(panelGlass9, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 185));
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(100, 97));
        FormInput.setLayout(null);

        label1.setText("Kode Barang :");
        label1.setName("label1"); // NOI18N
        FormInput.add(label1);
        label1.setBounds(0, 10, 89, 23);

        kode_brng.setName("kode_brng"); // NOI18N
        kode_brng.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kode_brngKeyPressed(evt);
            }
        });
        FormInput.add(kode_brng);
        kode_brng.setBounds(93, 10, 130, 23);

        nama_brng.setName("nama_brng"); // NOI18N
        nama_brng.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nama_brngKeyPressed(evt);
            }
        });
        FormInput.add(nama_brng);
        nama_brng.setBounds(93, 40, 480, 23);

        label7.setText("Jenis :");
        label7.setName("label7"); // NOI18N
        FormInput.add(label7);
        label7.setBounds(385, 70, 40, 23);

        label10.setText("Nama Barang :");
        label10.setName("label10"); // NOI18N
        FormInput.add(label10);
        label10.setBounds(0, 40, 89, 23);

        label19.setText("Satuan :");
        label19.setName("label19"); // NOI18N
        label19.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label19);
        label19.setBounds(0, 70, 89, 23);

        kode_sat.setEditable(false);
        kode_sat.setName("kode_sat"); // NOI18N
        kode_sat.setPreferredSize(new java.awt.Dimension(207, 23));
        kode_sat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kode_satKeyPressed(evt);
            }
        });
        FormInput.add(kode_sat);
        kode_sat.setBounds(93, 70, 60, 23);

        nama_sat.setEditable(false);
        nama_sat.setName("nama_sat"); // NOI18N
        nama_sat.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(nama_sat);
        nama_sat.setBounds(155, 70, 150, 23);

        btnSatuan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnSatuan.setMnemonic('1');
        btnSatuan.setToolTipText("Alt+1");
        btnSatuan.setName("btnSatuan"); // NOI18N
        btnSatuan.setPreferredSize(new java.awt.Dimension(28, 23));
        btnSatuan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSatuanActionPerformed(evt);
            }
        });
        FormInput.add(btnSatuan);
        btnSatuan.setBounds(307, 70, 25, 23);

        label20.setText("Stok :");
        label20.setName("label20"); // NOI18N
        label20.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label20);
        label20.setBounds(590, 40, 70, 23);

        stok.setEditable(false);
        stok.setText("0");
        stok.setName("stok"); // NOI18N
        stok.setPreferredSize(new java.awt.Dimension(207, 23));
        stok.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                stokKeyPressed(evt);
            }
        });
        FormInput.add(stok);
        stok.setBounds(663, 40, 60, 23);

        dasar.setText("0");
        dasar.setName("dasar"); // NOI18N
        dasar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                dasarMouseExited(evt);
            }
        });
        dasar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                dasarKeyPressed(evt);
            }
        });
        FormInput.add(dasar);
        dasar.setBounds(573, 10, 150, 23);

        label2.setText("Harga Dasar : Rp.");
        label2.setName("label2"); // NOI18N
        FormInput.add(label2);
        label2.setBounds(450, 10, 120, 23);

        kdjenis.setEditable(false);
        kdjenis.setName("kdjenis"); // NOI18N
        kdjenis.setPreferredSize(new java.awt.Dimension(207, 23));
        kdjenis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdjenisKeyPressed(evt);
            }
        });
        FormInput.add(kdjenis);
        kdjenis.setBounds(430, 70, 61, 23);

        nmjenis.setEditable(false);
        nmjenis.setName("nmjenis"); // NOI18N
        nmjenis.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(nmjenis);
        nmjenis.setBounds(493, 70, 203, 23);

        btnJenis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnJenis.setMnemonic('1');
        btnJenis.setToolTipText("Alt+1");
        btnJenis.setName("btnJenis"); // NOI18N
        btnJenis.setPreferredSize(new java.awt.Dimension(28, 23));
        btnJenis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnJenisActionPerformed(evt);
            }
        });
        FormInput.add(btnJenis);
        btnJenis.setBounds(698, 70, 25, 23);

        label3.setText("Harga Beli : Rp.");
        label3.setName("label3"); // NOI18N
        FormInput.add(label3);
        label3.setBounds(69, 100, 110, 23);

        beli.setText("0");
        beli.setName("beli"); // NOI18N
        beli.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                beliMouseExited(evt);
            }
        });
        beli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                beliKeyPressed(evt);
            }
        });
        FormInput.add(beli);
        beli.setBounds(182, 100, 150, 23);

        label4.setText("Harga Distributor : Rp.");
        label4.setName("label4"); // NOI18N
        FormInput.add(label4);
        label4.setBounds(430, 100, 140, 23);

        distributor.setText("0");
        distributor.setName("distributor"); // NOI18N
        distributor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                distributorMouseExited(evt);
            }
        });
        distributor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                distributorKeyPressed(evt);
            }
        });
        FormInput.add(distributor);
        distributor.setBounds(573, 100, 150, 23);

        label5.setText("Harga Grosir : Rp.");
        label5.setName("label5"); // NOI18N
        FormInput.add(label5);
        label5.setBounds(69, 130, 110, 23);

        grosir.setText("0");
        grosir.setName("grosir"); // NOI18N
        grosir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                grosirMouseExited(evt);
            }
        });
        grosir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                grosirKeyPressed(evt);
            }
        });
        FormInput.add(grosir);
        grosir.setBounds(182, 130, 150, 23);

        label6.setText("Harga Retail : Rp.");
        label6.setName("label6"); // NOI18N
        FormInput.add(label6);
        label6.setBounds(430, 130, 140, 23);

        retail.setText("0");
        retail.setName("retail"); // NOI18N
        retail.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                retailMouseExited(evt);
            }
        });
        retail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                retailKeyPressed(evt);
            }
        });
        FormInput.add(retail);
        retail.setBounds(573, 130, 150, 23);

        PanelInput.add(FormInput, java.awt.BorderLayout.CENTER);

        ChkInput.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput.setMnemonic('I');
        ChkInput.setText(".: Input Data");
        ChkInput.setToolTipText("Alt+I");
        ChkInput.setBorderPainted(true);
        ChkInput.setBorderPaintedFlat(true);
        ChkInput.setFocusable(false);
        ChkInput.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkInput.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkInput.setName("ChkInput"); // NOI18N
        ChkInput.setPreferredSize(new java.awt.Dimension(192, 20));
        ChkInput.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkInputActionPerformed(evt);
            }
        });
        PanelInput.add(ChkInput, java.awt.BorderLayout.PAGE_END);

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(kode_brng.getText().trim().equals("")){
            Valid.textKosong(kode_brng,"Kode Barang");
        }else if(nama_brng.getText().trim().equals("")){
            Valid.textKosong(nama_brng,"Nama Barang");
        }else if(dasar.getText().trim().equals("")){
            Valid.textKosong(dasar,"Harga Dasar");
        }else if(beli.getText().trim().equals("")){
            Valid.textKosong(beli,"Harga Beli");
        }else if(distributor.getText().trim().equals("")){
            Valid.textKosong(distributor,"Harga Distributor");
        }else if(grosir.getText().trim().equals("")){
            Valid.textKosong(grosir,"Harga Grosir");
        }else if(retail.getText().trim().equals("")){
            Valid.textKosong(retail,"Harga Retail");
        }else if(kode_sat.getText().trim().equals("")||nama_sat.getText().trim().equals("")){
            Valid.textKosong(kode_sat,"Satuan");
        }else if(kdjenis.getText().trim().equals("")||nmjenis.getText().trim().equals("")){
            Valid.textKosong(kdjenis,"Jenis Barang");
        }else {
            if(Sequel.menyimpantf("tokobarang","?,?,?,?,?,?,?,?,?,?,'1'","Kode Barang",10,new String[]{
                kode_brng.getText(),nama_brng.getText(), kode_sat.getText(), kdjenis.getText(),stok.getText(),dasar.getText(),beli.getText(),distributor.getText(),grosir.getText(),retail.getText()
            })==true){
                tampil();
                emptTeks();
            }   
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,retail,BtnBatal);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        ChkInput.setSelected(true);
        isForm(); 
        emptTeks();
}//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            emptTeks();
        }else{Valid.pindah(evt, BtnSimpan, BtnHapus);}
}//GEN-LAST:event_BtnBatalKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if(tbJnsPerawatan.getSelectedRow()> -1){
            Sequel.mengedit("tokobarang","kode_brng='"+kode_brng.getText()+"'","status='0'");
            BtnCariActionPerformed(evt);
            emptTeks();
        }
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnBatal, BtnEdit);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
        if(kode_brng.getText().trim().equals("")){
            Valid.textKosong(kode_brng,"Kode Barang");
        }else if(nama_brng.getText().trim().equals("")){
            Valid.textKosong(nama_brng,"Nama Barang");
        }else if(dasar.getText().trim().equals("")){
            Valid.textKosong(dasar,"Harga Dasar");
        }else if(beli.getText().trim().equals("")){
            Valid.textKosong(beli,"Harga Beli");
        }else if(distributor.getText().trim().equals("")){
            Valid.textKosong(distributor,"Harga Distributor");
        }else if(grosir.getText().trim().equals("")){
            Valid.textKosong(grosir,"Harga Grosir");
        }else if(retail.getText().trim().equals("")){
            Valid.textKosong(retail,"Harga Retail");
        }else if(kode_sat.getText().trim().equals("")||nama_sat.getText().trim().equals("")){
            Valid.textKosong(kode_sat,"Satuan");
        }else if(kdjenis.getText().trim().equals("")||nmjenis.getText().trim().equals("")){
            Valid.textKosong(kdjenis,"Jenis Barang");
        }else {
            if(tbJnsPerawatan.getSelectedRow()> -1){
                if(Sequel.mengedittf("tokobarang","kode_brng=?","kode_brng=?,nama_brng=?,kode_sat=?,jenis=?,stok=?,dasar=?,h_beli=?,distributor=?,grosir=?,retail=?",11,new String[]{
                    kode_brng.getText(),nama_brng.getText(), kode_sat.getText(), kdjenis.getText(),stok.getText(),dasar.getText(),beli.getText(),distributor.getText(),grosir.getText(),retail.getText(),tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),0).toString()
                })==true){
                    tampil();
                    emptTeks();
                }
            }
        }
}//GEN-LAST:event_BtnEditActionPerformed

    private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnEditActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnPrint);
        }
}//GEN-LAST:event_BtnEditKeyPressed

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
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        }else if(tabMode.getRowCount()!=0){
                Map<String, Object> param = new HashMap<>();                
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("emailrs",akses.getemailrs());   
                param.put("logo",Sequel.cariGambar("select logo from setting")); 
                if(TCari.getText().trim().equals("")){
                    Valid.MyReportqry("rptBarangToko.jasper","report","::[ Data Barang Toko / Minimarket / Koperasi ]::",
                       "select tokobarang.kode_brng,tokobarang.nama_brng,kodesatuan.satuan,tokojenisbarang.nm_jenis, "+
                        "tokobarang.stok,tokobarang.dasar,tokobarang.h_beli,tokobarang.distributor,tokobarang.grosir,tokobarang.retail "+
                        "from tokobarang inner join kodesatuan on tokobarang.kode_sat=kodesatuan.kode_sat "+
                        "inner join tokojenisbarang on tokobarang.jenis=tokojenisbarang.kd_jenis "+
                        "where tokobarang.status='1' order by tokobarang.kode_brng",param);
                }else{
                    Valid.MyReportqry("rptBarangToko.jasper","report","::[ Data Barang Toko / Minimarket / Koperasi ]::",
                       "select tokobarang.kode_brng,tokobarang.nama_brng,kodesatuan.satuan,tokojenisbarang.nm_jenis, "+
                        "tokobarang.stok,tokobarang.dasar,tokobarang.h_beli,tokobarang.distributor,tokobarang.grosir,tokobarang.retail "+
                        "from tokobarang inner join kodesatuan on tokobarang.kode_sat=kodesatuan.kode_sat "+
                        "inner join tokojenisbarang on tokobarang.jenis=tokojenisbarang.kd_jenis "+
                        "where tokobarang.status='1' and tokobarang.kode_brng like '%"+TCari.getText().trim()+"%' "+
                        "or tokobarang.status='1' and tokobarang.nama_brng like '%"+TCari.getText().trim()+"%' "+
                        "or tokobarang.status='1' and kodesatuan.satuan like '%"+TCari.getText().trim()+"%' "+
                        "or tokobarang.status='1' and tokojenisbarang.nm_jenis like '%"+TCari.getText().trim()+"%' order by tokobarang.kode_brng",param);
                }
                    
        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnEdit, BtnAll);
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
            tbJnsPerawatan.requestFocus();
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
            TCari.setText("");
            tampil();
        }else{
            Valid.pindah(evt, BtnPrint,BtnKeluar);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void tbJnsPerawatanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbJnsPerawatanMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }            
        }
}//GEN-LAST:event_tbJnsPerawatanMouseClicked

    private void tbJnsPerawatanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbJnsPerawatanKeyPressed
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }else if(evt.getKeyCode()==KeyEvent.VK_SHIFT){
                TCari.setText("");
                TCari.requestFocus();
            }
        }
}//GEN-LAST:event_tbJnsPerawatanKeyPressed

private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
  isForm();                
}//GEN-LAST:event_ChkInputActionPerformed

private void kode_brngKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kode_brngKeyPressed
        Valid.pindah(evt,kode_sat,dasar,TCari);
}//GEN-LAST:event_kode_brngKeyPressed

private void nama_brngKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nama_brngKeyPressed
        Valid.pindah(evt,dasar,kode_sat);
}//GEN-LAST:event_nama_brngKeyPressed

private void kode_satKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kode_satKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select satuan from kodesatuan where kode_sat=?", nama_sat,kode_sat.getText());           
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            nama_brng.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            kdjenis.requestFocus(); 
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnSatuanActionPerformed(null);
        }
}//GEN-LAST:event_kode_satKeyPressed

private void btnSatuanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSatuanActionPerformed
    akses.setform("TokoBarang");
    satuan.isCek();
    satuan.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
    satuan.setLocationRelativeTo(internalFrame1);
    satuan.setVisible(true);
}//GEN-LAST:event_btnSatuanActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void stokKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_stokKeyPressed
        Valid.pindah(evt,kdjenis,BtnSimpan);
    }//GEN-LAST:event_stokKeyPressed

    private void dasarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dasarKeyPressed
        Valid.pindah(evt,kode_brng,nama_brng);
    }//GEN-LAST:event_dasarKeyPressed

    private void kdjenisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdjenisKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nm_jenis from tokojenisbarang where kd_jenis=?", nmjenis,kdjenis.getText());           
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){            
            kode_sat.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            beli.requestFocus(); 
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnJenisActionPerformed(null);
        }
    }//GEN-LAST:event_kdjenisKeyPressed

    private void btnJenisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnJenisActionPerformed
        akses.setform("TokoBarang");
        jenis.isCek();
        jenis.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        jenis.setLocationRelativeTo(internalFrame1);
        jenis.setVisible(true);
    }//GEN-LAST:event_btnJenisActionPerformed

    private void MnRestoreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnRestoreActionPerformed
        DlgRestoreIPSRSBarang restore=new DlgRestoreIPSRSBarang(null,true);
        restore.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        restore.setLocationRelativeTo(internalFrame1);
        restore.setVisible(true);
    }//GEN-LAST:event_MnRestoreActionPerformed

    private void beliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_beliKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            isHitung();        
            distributor.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            dasar.requestFocus();
        }
    }//GEN-LAST:event_beliKeyPressed

    private void distributorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_distributorKeyPressed
        Valid.pindah(evt,beli,grosir);
    }//GEN-LAST:event_distributorKeyPressed

    private void grosirKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_grosirKeyPressed
        Valid.pindah(evt,distributor,retail);
    }//GEN-LAST:event_grosirKeyPressed

    private void retailKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_retailKeyPressed
        Valid.pindah(evt,grosir,BtnSimpan);
    }//GEN-LAST:event_retailKeyPressed

    private void dasarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dasarMouseExited
        if (dasar.getText().equals("")) {
            dasar.setText("0");
        }
    }//GEN-LAST:event_dasarMouseExited

    private void beliMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_beliMouseExited
        if (beli.getText().equals("")) {
            beli.setText("0");
        }
    }//GEN-LAST:event_beliMouseExited

    private void distributorMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_distributorMouseExited
        if (distributor.getText().equals("")) {
            distributor.setText("0");
        }
    }//GEN-LAST:event_distributorMouseExited

    private void grosirMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_grosirMouseExited
        if (grosir.getText().equals("")) {
            grosir.setText("0");
        }
    }//GEN-LAST:event_grosirMouseExited

    private void retailMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_retailMouseExited
        if (retail.getText().equals("")) {
            retail.setText("0");
        }
    }//GEN-LAST:event_retailMouseExited

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            TokoBarang dialog = new TokoBarang(new javax.swing.JFrame(), true);
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
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkInput;
    private widget.PanelBiasa FormInput;
    private widget.Label LCount;
    private javax.swing.JMenuItem MnRestore;
    private javax.swing.JPanel PanelInput;
    private javax.swing.JPopupMenu Popup;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.TextBox beli;
    private widget.Button btnJenis;
    private widget.Button btnSatuan;
    private widget.TextBox dasar;
    private widget.TextBox distributor;
    private widget.TextBox grosir;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private javax.swing.JPanel jPanel3;
    private widget.TextBox kdjenis;
    private widget.TextBox kode_brng;
    private widget.TextBox kode_sat;
    private widget.Label label1;
    private widget.Label label10;
    private widget.Label label19;
    private widget.Label label2;
    private widget.Label label20;
    private widget.Label label3;
    private widget.Label label4;
    private widget.Label label5;
    private widget.Label label6;
    private widget.Label label7;
    private widget.TextBox nama_brng;
    private widget.TextBox nama_sat;
    private widget.TextBox nmjenis;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.TextBox retail;
    private widget.TextBox stok;
    private widget.Table tbJnsPerawatan;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            if(TCari.getText().trim().equals("")){
                ps=koneksi.prepareStatement(
                        "select tokobarang.kode_brng,tokobarang.nama_brng,kodesatuan.satuan,tokojenisbarang.nm_jenis, "+
                        "tokobarang.stok,tokobarang.dasar,tokobarang.h_beli,tokobarang.distributor,tokobarang.grosir,tokobarang.retail "+
                        "from tokobarang inner join kodesatuan on tokobarang.kode_sat=kodesatuan.kode_sat "+
                        "inner join tokojenisbarang on tokobarang.jenis=tokojenisbarang.kd_jenis "+
                        "where tokobarang.status='1' order by tokobarang.kode_brng");
            }else{
                ps=koneksi.prepareStatement(
                        "select tokobarang.kode_brng,tokobarang.nama_brng,kodesatuan.satuan,tokojenisbarang.nm_jenis, "+
                        "tokobarang.stok,tokobarang.dasar,tokobarang.h_beli,tokobarang.distributor,tokobarang.grosir,tokobarang.retail "+
                        "from tokobarang inner join kodesatuan on tokobarang.kode_sat=kodesatuan.kode_sat "+
                        "inner join tokojenisbarang on tokobarang.jenis=tokojenisbarang.kd_jenis "+
                        "where tokobarang.status='1' and tokobarang.kode_brng like ? "+
                        "or tokobarang.status='1' and tokobarang.nama_brng like ? "+
                        "or tokobarang.status='1' and kodesatuan.satuan like ? "+
                        "or tokobarang.status='1' and tokojenisbarang.nm_jenis like ? order by tokobarang.kode_brng");
            } 
                
            try {
                if(TCari.getText().trim().equals("")){}else{
                    ps.setString(1,"%"+TCari.getText().trim()+"%");
                    ps.setString(2,"%"+TCari.getText().trim()+"%");
                    ps.setString(3,"%"+TCari.getText().trim()+"%");
                    ps.setString(4,"%"+TCari.getText().trim()+"%");
                }
                    
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new Object[]{
                        rs.getString("kode_brng"),rs.getString("nama_brng"),rs.getString("satuan"),rs.getString("nm_jenis"),rs.getDouble("stok"),
                        rs.getDouble("dasar"),rs.getDouble("h_beli"),rs.getDouble("distributor"),rs.getDouble("grosir"),rs.getDouble("retail")
                    });
                }
            } catch (Exception e) {
                System.out.println("Data : "+e);
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
        LCount.setText(""+tabMode.getRowCount());
    }

    public void emptTeks() {
        kode_brng.setText("");
        nama_brng.setText("");
        kode_sat.setText("");
        dasar.setText("0");
        beli.setText("0");
        distributor.setText("0");
        grosir.setText("0");
        retail.setText("0");
        nama_sat.setText("");
        stok.setText("0");
        kdjenis.setText("");
        nmjenis.setText("");
        TCari.setText("");
        kode_brng.requestFocus();
        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(kode_brng,5),signed)),0) from tokobarang  ","BT",6,kode_brng);
        kode_brng.requestFocus();
    }
    
    public void onCari(){
        TCari.requestFocus();
    }

    private void getData() {
        if(tbJnsPerawatan.getSelectedRow()!= -1){
            kode_brng.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),0).toString());
            nama_brng.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),1).toString());
            nama_sat.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),2).toString());
            nmjenis.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),3).toString());
            stok.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),4).toString());
            dasar.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),5).toString());
            beli.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),6).toString());
            distributor.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),7).toString());
            grosir.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),8).toString());
            retail.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),9).toString());
            kode_sat.setText(Sequel.cariIsi("select kode_sat from tokobarang where kode_brng=?",kode_brng.getText()));
            kdjenis.setText(Sequel.cariIsi("select jenis from tokobarang where kode_brng=?",kode_brng.getText()));
        }
    }

    public JTable getTable(){
        return tbJnsPerawatan;
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,185));
            FormInput.setVisible(true);      
            ChkInput.setVisible(true);
        }else if(ChkInput.isSelected()==false){           
            ChkInput.setVisible(false);            
            PanelInput.setPreferredSize(new Dimension(WIDTH,20));
            FormInput.setVisible(false);      
            ChkInput.setVisible(true);
        }
    }
    
    public void isCek(){
        BtnSimpan.setEnabled(akses.gettoko_barang());
        BtnHapus.setEnabled(akses.gettoko_barang());
        BtnEdit.setEnabled(akses.gettoko_barang());
        BtnPrint.setEnabled(akses.gettoko_barang());
        if(akses.getkode().equals("Admin Utama")){
            MnRestore.setEnabled(true);
        }else{
            MnRestore.setEnabled(false);
        }
        TCari.requestFocus();
    }
    
   private void isHitung() {   
        if(this.isVisible()==true){
            try {
                if (!beli.getText().equals("")) {
                    try{
                        rs=koneksi.prepareStatement("select * from tokosetharga").executeQuery();
                        if(rs.next()){
                            grosir.setText(Double.toString(Valid.roundUp(Double.parseDouble(beli.getText()) + (Double.parseDouble(beli.getText()) * (rs.getDouble("grosir") / 100)),100)));
                            distributor.setText(Double.toString(Valid.roundUp(Double.parseDouble(beli.getText()) + (Double.parseDouble(beli.getText()) * (rs.getDouble("distributor") / 100)),100)));
                            retail.setText(Double.toString(Valid.roundUp(Double.parseDouble(beli.getText()) + (Double.parseDouble(beli.getText()) * (rs.getDouble("retail") / 100)),100)));
                        }else{
                            JOptionPane.showMessageDialog(null,"Pengaturan harga masih kosong...!!");
                            TCari.requestFocus();
                        }
                    }catch(Exception e){
                        System.out.println("Notifikasi : "+e);
                    }finally{
                        if(rs!=null){
                            rs.close();
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("Notif : "+e);
            }
        }            
    }
    
}

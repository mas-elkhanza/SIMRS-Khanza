/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgJnsPerawatan.java
 *
 * Created on May 22, 2010, 11:58:21 PM
 */

package inventaris;
import fungsi.WarnaTable;
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

/**
 *
 * @author dosen
 */
public final class InventarisBarang extends javax.swing.JDialog {
    private DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();

    /** Creates new form DlgJnsPerawatan */
    public InventarisBarang(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        this.setLocation(8,1);
        setSize(628,674);

        Object[] row={"Kode Barang",
                      "Nama Koleksi Barang",
                      "Jml.Brg",
                      "Produsen",
                      "Merk",
                      "Thn.Produksi",
                      "Barcode SN",
                      "Kategori",
                      "Jenis"
        };
        tabMode=new DefaultTableModel(null,row){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbJnsPerawatan.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbJnsPerawatan.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbJnsPerawatan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 9; i++) {
            TableColumn column = tbJnsPerawatan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(100);
            }else if(i==1){
                column.setPreferredWidth(200);
            }else if(i==2){
                column.setPreferredWidth(80);
            }else if(i==3){
                column.setPreferredWidth(200);
            }else if(i==4){
                column.setPreferredWidth(200);
            }else if(i==5){
                column.setPreferredWidth(100);
            }else if(i==6){
                column.setPreferredWidth(100);
            }else if(i==7){
                column.setPreferredWidth(200);
            }else if(i==8){
                column.setPreferredWidth(200);
            }
        }
        tbJnsPerawatan.setDefaultRenderer(Object.class, new WarnaTable());

        kode_barang.setDocument(new batasInput((byte)20).getKata(kode_barang));
        nama_barang.setDocument(new batasInput((byte)60).getKata(nama_barang));
        jml_barang.setDocument(new batasInput((byte)5).getOnlyAngka(jml_barang));
        kode_produsen.setDocument(new batasInput((byte)10).getKata(kode_produsen));
        id_merk.setDocument(new batasInput((byte)7).getKata(id_merk));
        isbn.setDocument(new batasInput((byte)20).getKata(isbn));
        id_kategori.setDocument(new batasInput((byte)5).getKata(id_kategori));
        id_jenis.setDocument(new batasInput((byte)5).getKata(id_jenis));        
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        TCari.requestFocus();        
        
      
        ChkInput.setSelected(false);
        isForm(); 
        Valid.LoadTahun(thn_produksi);
        
        produsen.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(produsen.getTable().getSelectedRow()!= -1){                   
                    kode_produsen.setText(produsen.getTable().getValueAt(produsen.getTable().getSelectedRow(),0).toString());                    
                    nama_produsen.setText(produsen.getTable().getValueAt(produsen.getTable().getSelectedRow(),1).toString());
                }   
                kode_produsen.requestFocus();
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
        
        produsen.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    produsen.dispose();
                }                
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        merk.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(merk.getTable().getSelectedRow()!= -1){                   
                    id_merk.setText(merk.getTable().getValueAt(merk.getTable().getSelectedRow(),0).toString());                    
                    nm_merk.setText(merk.getTable().getValueAt(merk.getTable().getSelectedRow(),1).toString());
                }   
                id_merk.requestFocus();
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
        
        merk.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    merk.dispose();
                }                
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        kategori.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(kategori.getTable().getSelectedRow()!= -1){                   
                    id_kategori.setText(kategori.getTable().getValueAt(kategori.getTable().getSelectedRow(),0).toString());                    
                    nm_kategori.setText(kategori.getTable().getValueAt(kategori.getTable().getSelectedRow(),1).toString());
                }   
                id_kategori.requestFocus();
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
        
        kategori.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    kategori.dispose();
                }                
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        jenis.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(jenis.getTable().getSelectedRow()!= -1){                   
                    id_jenis.setText(jenis.getTable().getValueAt(jenis.getTable().getSelectedRow(),0).toString());                    
                    nm_jenis.setText(jenis.getTable().getValueAt(jenis.getTable().getSelectedRow(),1).toString());
                }   
                id_jenis.requestFocus();
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
        
        jenis.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    jenis.dispose();
                }                
            }
            @Override
            public void keyReleased(KeyEvent e) {}
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
        }
    }
    private InventarisProdusen produsen=new InventarisProdusen(null,false); 
    private InventarisMerk merk=new InventarisMerk(null,false); 
    private InventarisKategori kategori=new InventarisKategori(null,false);
    private InventarisJenis jenis=new InventarisJenis(null,false); 

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

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
        kode_barang = new widget.TextBox();
        nama_barang = new widget.TextBox();
        jml_barang = new widget.TextBox();
        label7 = new widget.Label();
        isbn = new widget.TextBox();
        label8 = new widget.Label();
        label10 = new widget.Label();
        label9 = new widget.Label();
        thn_produksi = new widget.ComboBox();
        label19 = new widget.Label();
        kode_produsen = new widget.TextBox();
        nama_produsen = new widget.TextBox();
        btnProdusen = new widget.Button();
        label20 = new widget.Label();
        id_merk = new widget.TextBox();
        nm_merk = new widget.TextBox();
        btnMerk = new widget.Button();
        label21 = new widget.Label();
        id_kategori = new widget.TextBox();
        nm_kategori = new widget.TextBox();
        btnKategori = new widget.Button();
        label22 = new widget.Label();
        id_jenis = new widget.TextBox();
        nm_jenis = new widget.TextBox();
        btnJenis = new widget.Button();
        ChkInput = new widget.CekBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 253, 247)), "::[ Data Koleksi Barang ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbJnsPerawatan.setAutoCreateRowSorter(true);
        tbJnsPerawatan.setToolTipText("Silakan klik untuk memilih data yang hendak diedit ataupun dihapus");
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
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbJnsPerawatanKeyReleased(evt);
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

        jLabel6.setText("Keyword :");
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
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 155));
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(100, 127));
        FormInput.setLayout(null);

        label1.setText("Kode Barang :");
        label1.setName("label1"); // NOI18N
        FormInput.add(label1);
        label1.setBounds(0, 10, 90, 23);

        kode_barang.setName("kode_barang"); // NOI18N
        kode_barang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kode_barangKeyPressed(evt);
            }
        });
        FormInput.add(kode_barang);
        kode_barang.setBounds(93, 10, 130, 23);

        nama_barang.setName("nama_barang"); // NOI18N
        nama_barang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nama_barangKeyPressed(evt);
            }
        });
        FormInput.add(nama_barang);
        nama_barang.setBounds(93, 40, 261, 23);

        jml_barang.setName("jml_barang"); // NOI18N
        jml_barang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jml_barangKeyPressed(evt);
            }
        });
        FormInput.add(jml_barang);
        jml_barang.setBounds(93, 70, 80, 23);

        label7.setText("Tahun Produksi :");
        label7.setName("label7"); // NOI18N
        FormInput.add(label7);
        label7.setBounds(175, 70, 90, 23);

        isbn.setName("isbn"); // NOI18N
        isbn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                isbnKeyPressed(evt);
            }
        });
        FormInput.add(isbn);
        isbn.setBounds(442, 40, 261, 23);

        label8.setText("Barcode SN :");
        label8.setName("label8"); // NOI18N
        FormInput.add(label8);
        label8.setBounds(369, 40, 70, 23);

        label10.setText("Nama Barang :");
        label10.setName("label10"); // NOI18N
        FormInput.add(label10);
        label10.setBounds(0, 40, 90, 23);

        label9.setText("Jml.Barang :");
        label9.setName("label9"); // NOI18N
        FormInput.add(label9);
        label9.setBounds(0, 70, 90, 23);

        thn_produksi.setName("thn_produksi"); // NOI18N
        thn_produksi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                thn_produksiKeyPressed(evt);
            }
        });
        FormInput.add(thn_produksi);
        thn_produksi.setBounds(269, 70, 85, 23);

        label19.setText("Produsen :");
        label19.setName("label19"); // NOI18N
        label19.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label19);
        label19.setBounds(0, 100, 90, 23);

        kode_produsen.setName("kode_produsen"); // NOI18N
        kode_produsen.setPreferredSize(new java.awt.Dimension(207, 23));
        kode_produsen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kode_produsenKeyPressed(evt);
            }
        });
        FormInput.add(kode_produsen);
        kode_produsen.setBounds(93, 100, 80, 23);

        nama_produsen.setEditable(false);
        nama_produsen.setName("nama_produsen"); // NOI18N
        nama_produsen.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(nama_produsen);
        nama_produsen.setBounds(174, 100, 180, 23);

        btnProdusen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnProdusen.setMnemonic('1');
        btnProdusen.setToolTipText("Alt+1");
        btnProdusen.setName("btnProdusen"); // NOI18N
        btnProdusen.setPreferredSize(new java.awt.Dimension(28, 23));
        btnProdusen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProdusenActionPerformed(evt);
            }
        });
        FormInput.add(btnProdusen);
        btnProdusen.setBounds(356, 100, 25, 23);

        label20.setText("Merk :");
        label20.setName("label20"); // NOI18N
        label20.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label20);
        label20.setBounds(369, 10, 70, 23);

        id_merk.setName("id_merk"); // NOI18N
        id_merk.setPreferredSize(new java.awt.Dimension(207, 23));
        id_merk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                id_merkKeyPressed(evt);
            }
        });
        FormInput.add(id_merk);
        id_merk.setBounds(442, 10, 80, 23);

        nm_merk.setEditable(false);
        nm_merk.setName("nm_merk"); // NOI18N
        nm_merk.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(nm_merk);
        nm_merk.setBounds(523, 10, 180, 23);

        btnMerk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnMerk.setMnemonic('1');
        btnMerk.setToolTipText("Alt+1");
        btnMerk.setName("btnMerk"); // NOI18N
        btnMerk.setPreferredSize(new java.awt.Dimension(28, 23));
        btnMerk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMerkActionPerformed(evt);
            }
        });
        FormInput.add(btnMerk);
        btnMerk.setBounds(705, 10, 25, 23);

        label21.setText("Kategori :");
        label21.setName("label21"); // NOI18N
        label21.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label21);
        label21.setBounds(369, 70, 70, 23);

        id_kategori.setName("id_kategori"); // NOI18N
        id_kategori.setPreferredSize(new java.awt.Dimension(207, 23));
        id_kategori.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                id_kategoriKeyPressed(evt);
            }
        });
        FormInput.add(id_kategori);
        id_kategori.setBounds(442, 70, 80, 23);

        nm_kategori.setEditable(false);
        nm_kategori.setName("nm_kategori"); // NOI18N
        nm_kategori.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(nm_kategori);
        nm_kategori.setBounds(523, 70, 180, 23);

        btnKategori.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnKategori.setMnemonic('1');
        btnKategori.setToolTipText("Alt+1");
        btnKategori.setName("btnKategori"); // NOI18N
        btnKategori.setPreferredSize(new java.awt.Dimension(28, 23));
        btnKategori.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKategoriActionPerformed(evt);
            }
        });
        FormInput.add(btnKategori);
        btnKategori.setBounds(705, 70, 25, 23);

        label22.setText("Jenis :");
        label22.setName("label22"); // NOI18N
        label22.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label22);
        label22.setBounds(369, 100, 70, 23);

        id_jenis.setName("id_jenis"); // NOI18N
        id_jenis.setPreferredSize(new java.awt.Dimension(207, 23));
        id_jenis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                id_jenisKeyPressed(evt);
            }
        });
        FormInput.add(id_jenis);
        id_jenis.setBounds(442, 100, 80, 23);

        nm_jenis.setEditable(false);
        nm_jenis.setName("nm_jenis"); // NOI18N
        nm_jenis.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(nm_jenis);
        nm_jenis.setBounds(523, 100, 180, 23);

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
        btnJenis.setBounds(705, 100, 25, 23);

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
        if(kode_barang.getText().trim().equals("")){
            Valid.textKosong(kode_barang,"Kode Barang");
        }else if(nama_barang.getText().trim().equals("")){
            Valid.textKosong(nama_barang,"Nama Barang");
        }else if(jml_barang.getText().trim().equals("")){
            Valid.textKosong(jml_barang,"Jumlah Barang");
        }else if(kode_produsen.getText().trim().equals("")||nama_produsen.getText().trim().equals("")){
            Valid.textKosong(kode_produsen,"Produsen");
        }else if(id_merk.getText().trim().equals("")||nm_merk.getText().trim().equals("")){
            Valid.textKosong(id_merk,"Merk");
        }else if(id_jenis.getText().trim().equals("")||nm_jenis.getText().trim().equals("")){
            Valid.textKosong(id_jenis,"Jenis");
        }else if(id_kategori.getText().trim().equals("")||nm_kategori.getText().trim().equals("")){
            Valid.textKosong(id_kategori,"Kategori");
        }else {
                //menyimpan-------------------------------------------------
                Sequel.menyimpan("inventaris_barang","'"+kode_barang.getText()+"','"+nama_barang.getText()+"','"+jml_barang.getText()+"','"+
                        kode_produsen.getText()+"','"+id_merk.getText()+"','"+thn_produksi.getSelectedItem()+"','"+isbn.getText()+"','"+
                        id_kategori.getText()+"','"+id_jenis.getText()+"'","Kode Barang");
                //----------------------------------------------------------
                kode_barang.requestFocus();
            tampil();
            emptTeks();
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,id_jenis,BtnBatal);
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
        Valid.hapusTable(tabMode,kode_barang,"inventaris_barang","kode_barang");
        BtnCariActionPerformed(evt);
        emptTeks();
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnBatal, BtnEdit);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
        if(kode_barang.getText().trim().equals("")){
            Valid.textKosong(kode_barang,"Kode Barang");
        }else if(nama_barang.getText().trim().equals("")){
            Valid.textKosong(nama_barang,"Nama Barang");
        }else if(jml_barang.getText().trim().equals("")){
            Valid.textKosong(jml_barang,"Jumlah Barang");
        }else if(kode_produsen.getText().trim().equals("")||nama_produsen.getText().trim().equals("")){
            Valid.textKosong(kode_produsen,"Produsen");
        }else if(id_merk.getText().trim().equals("")||nm_merk.getText().trim().equals("")){
            Valid.textKosong(id_merk,"Merk");
        }else if(id_jenis.getText().trim().equals("")||nm_jenis.getText().trim().equals("")){
            Valid.textKosong(id_jenis,"Jenis");
        }else if(id_kategori.getText().trim().equals("")||nm_kategori.getText().trim().equals("")){
            Valid.textKosong(id_kategori,"Kategori");
        }else {
                //menyimpan-------------------------------------------------
                Sequel.mengedit("inventaris_barang","kode_barang='"+tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),0) +"'",
                        "kode_barang='"+kode_barang.getText()+"',nama_barang='"+nama_barang.getText()+"',jml_barang='"+jml_barang.getText()+"',kode_produsen='"+
                        kode_produsen.getText()+"',id_merk='"+id_merk.getText()+"',thn_produksi='"+thn_produksi.getSelectedItem()+"',isbn='"+isbn.getText()+"',id_kategori='"+
                        id_kategori.getText()+"',id_jenis='"+id_jenis.getText()+"'");
                //----------------------------------------------------------
                kode_barang.requestFocus();
            tampil();
            emptTeks();
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
        if(! TCari.getText().trim().equals("")){
            BtnCariActionPerformed(evt);
        }
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
                param.put("parameter","%"+TCari.getText().trim()+"%");  
                Valid.MyReport("rptBarangInventaris.jasper","report","::[ Data Koleksi Barang ]::",param);
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
            BtnAllActionPerformed(null);
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
            if(evt.getKeyCode()==KeyEvent.VK_SHIFT){
                TCari.setText("");
                TCari.requestFocus();
            }
        }
}//GEN-LAST:event_tbJnsPerawatanKeyPressed

private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
  isForm();                
}//GEN-LAST:event_ChkInputActionPerformed

private void kode_barangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kode_barangKeyPressed
        Valid.pindah(evt,id_jenis,nama_barang,TCari);
}//GEN-LAST:event_kode_barangKeyPressed

private void nama_barangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nama_barangKeyPressed
        Valid.pindah(evt,kode_barang,jml_barang);
}//GEN-LAST:event_nama_barangKeyPressed

private void jml_barangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jml_barangKeyPressed
        Valid.pindah(evt,nama_barang,thn_produksi);
}//GEN-LAST:event_jml_barangKeyPressed

private void isbnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_isbnKeyPressed
        Valid.pindah(evt,id_merk,id_kategori);
}//GEN-LAST:event_isbnKeyPressed

private void thn_produksiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_thn_produksiKeyPressed
    Valid.pindah(evt,jml_barang,kode_produsen);
}//GEN-LAST:event_thn_produksiKeyPressed

private void kode_produsenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kode_produsenKeyPressed
    if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
        Sequel.cariIsi("select inventaris_produsen.nama_produsen from inventaris_produsen where inventaris_produsen.kode_produsen=?",nama_produsen,kode_produsen.getText());        
    }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
        Sequel.cariIsi("select inventaris_produsen.nama_produsen from inventaris_produsen where inventaris_produsen.kode_produsen=?",nama_produsen,kode_produsen.getText()); 
        thn_produksi.requestFocus();
    }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
        Sequel.cariIsi("select inventaris_produsen.nama_produsen from inventaris_produsen where inventaris_produsen.kode_produsen=?",nama_produsen,kode_produsen.getText()); 
        id_merk.requestFocus();
    }else if(evt.getKeyCode()==KeyEvent.VK_UP){
        btnProdusenActionPerformed(null);
    }
}//GEN-LAST:event_kode_produsenKeyPressed

private void btnProdusenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProdusenActionPerformed
    produsen.isCek();
    produsen.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
    produsen.setLocationRelativeTo(internalFrame1);
    produsen.setVisible(true);
}//GEN-LAST:event_btnProdusenActionPerformed

private void id_merkKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_id_merkKeyPressed
    if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
        Sequel.cariIsi("select inventaris_merk.nama_merk from inventaris_merk where inventaris_merk.id_merk=?",nm_merk,id_merk.getText());        
    }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
        Sequel.cariIsi("select inventaris_merk.nama_merk from inventaris_merk where inventaris_merk.id_merk=?",nm_merk,id_merk.getText());   
        kode_produsen.requestFocus();
    }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
        Sequel.cariIsi("select inventaris_merk.nama_merk from inventaris_merk where inventaris_merk.id_merk=?",nm_merk,id_merk.getText());   
        isbn.requestFocus();
    }else if(evt.getKeyCode()==KeyEvent.VK_UP){
        btnMerkActionPerformed(null);
    }
}//GEN-LAST:event_id_merkKeyPressed

private void btnMerkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMerkActionPerformed
    merk.isCek();
    merk.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
    merk.setLocationRelativeTo(internalFrame1);
    merk.setVisible(true);
}//GEN-LAST:event_btnMerkActionPerformed

private void id_kategoriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_id_kategoriKeyPressed
    if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
        Sequel.cariIsi("select inventaris_kategori.nama_kategori from inventaris_kategori where inventaris_kategori.id_kategori='"+id_kategori.getText()+"'",nm_kategori);       
    }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
        Sequel.cariIsi("select inventaris_kategori.nama_kategori from inventaris_kategori where inventaris_kategori.id_kategori='"+id_kategori.getText()+"'",nm_kategori);
        isbn.requestFocus();
    }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
        Sequel.cariIsi("select inventaris_kategori.nama_kategori from inventaris_kategori where inventaris_kategori.id_kategori='"+id_kategori.getText()+"'",nm_kategori);
        id_jenis.requestFocus();
    }else if(evt.getKeyCode()==KeyEvent.VK_UP){
        btnKategoriActionPerformed(null);
    }
}//GEN-LAST:event_id_kategoriKeyPressed

private void btnKategoriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKategoriActionPerformed
    kategori.isCek();
    kategori.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
    kategori.setLocationRelativeTo(internalFrame1);
    kategori.setAlwaysOnTop(false);
    kategori.setVisible(true);
}//GEN-LAST:event_btnKategoriActionPerformed

private void id_jenisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_id_jenisKeyPressed
    if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
        Sequel.cariIsi("select inventaris_jenis.nama_jenis from inventaris_jenis where inventaris_jenis.id_jenis=?",nm_jenis,id_jenis.getText());       
    }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
        Sequel.cariIsi("select inventaris_jenis.nama_jenis from inventaris_jenis where inventaris_jenis.id_jenis=?",nm_jenis,id_jenis.getText());  
        id_kategori.requestFocus();
    }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
        Sequel.cariIsi("select inventaris_jenis.nama_jenis from inventaris_jenis where inventaris_jenis.id_jenis=?",nm_jenis,id_jenis.getText());  
        BtnSimpan.requestFocus();
    }else if(evt.getKeyCode()==KeyEvent.VK_UP){
        btnJenisActionPerformed(null);
    }
}//GEN-LAST:event_id_jenisKeyPressed

private void btnJenisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnJenisActionPerformed
    jenis.isCek();
    jenis.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
    jenis.setLocationRelativeTo(internalFrame1);
    jenis.setAlwaysOnTop(false);
    jenis.setVisible(true);
}//GEN-LAST:event_btnJenisActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void tbJnsPerawatanKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbJnsPerawatanKeyReleased
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }//GEN-LAST:event_tbJnsPerawatanKeyReleased

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            InventarisBarang dialog = new InventarisBarang(new javax.swing.JFrame(), true);
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
    private javax.swing.JPanel PanelInput;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.Button btnJenis;
    private widget.Button btnKategori;
    private widget.Button btnMerk;
    private widget.Button btnProdusen;
    private widget.TextBox id_jenis;
    private widget.TextBox id_kategori;
    private widget.TextBox id_merk;
    private widget.InternalFrame internalFrame1;
    private widget.TextBox isbn;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private javax.swing.JPanel jPanel3;
    private widget.TextBox jml_barang;
    private widget.TextBox kode_barang;
    private widget.TextBox kode_produsen;
    private widget.Label label1;
    private widget.Label label10;
    private widget.Label label19;
    private widget.Label label20;
    private widget.Label label21;
    private widget.Label label22;
    private widget.Label label7;
    private widget.Label label8;
    private widget.Label label9;
    private widget.TextBox nama_barang;
    private widget.TextBox nama_produsen;
    private widget.TextBox nm_jenis;
    private widget.TextBox nm_kategori;
    private widget.TextBox nm_merk;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.Table tbJnsPerawatan;
    private widget.ComboBox thn_produksi;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        String sql="select inventaris_barang.kode_barang, inventaris_barang.nama_barang, inventaris_barang.jml_barang, "+
                   "inventaris_produsen.nama_produsen, inventaris_merk.nama_merk, inventaris_barang.thn_produksi, inventaris_barang.isbn,"+
                   "inventaris_kategori.nama_kategori, inventaris_jenis.nama_jenis from inventaris_barang inner join inventaris_produsen "+
                   "inner join inventaris_jenis inner join inventaris_kategori inner join inventaris_merk "+
                   "on inventaris_barang.kode_produsen=inventaris_produsen.kode_produsen and inventaris_barang.id_merk=inventaris_merk.id_merk "+
                   "and inventaris_barang.id_kategori=inventaris_kategori.id_kategori and inventaris_barang.id_jenis=inventaris_jenis.id_jenis "+
                   "where inventaris_barang.kode_barang like '%"+TCari.getText().trim()+"%' "+
                    "or inventaris_barang.nama_barang like '%"+TCari.getText().trim()+"%' "+
                    "or inventaris_barang.jml_barang like '%"+TCari.getText().trim()+"%' "+
                    "or inventaris_produsen.nama_produsen like '%"+TCari.getText().trim()+"%' "+
                    "or inventaris_merk.nama_merk like '%"+TCari.getText().trim()+"%' "+
                    "or inventaris_barang.thn_produksi like '%"+TCari.getText().trim()+"%' "+
                    "or inventaris_barang.isbn like '%"+TCari.getText().trim()+"%' "+
                    "or inventaris_kategori.nama_kategori like '%"+TCari.getText().trim()+"%' "+
                    "or inventaris_jenis.nama_jenis like '%"+TCari.getText().trim()+"%' order by inventaris_barang.kode_barang";
        prosesCari(sql);
    }

    private void prosesCari(String sql) {
        Valid.tabelKosong(tabMode);
        try{
            ResultSet rs=koneksiDB.condb().prepareStatement(sql).executeQuery();
            while(rs.next()){
                tabMode.addRow(new Object[]{rs.getString("kode_barang"),
                               rs.getString("nama_barang"),
                               rs.getString("jml_barang"),
                               rs.getString("nama_produsen"),
                               rs.getString("nama_merk"),
                               rs.getString("thn_produksi").substring(0,4),
                               rs.getString("isbn"),
                               rs.getString("nama_kategori"),
                               rs.getString("nama_jenis")});
            }
        }catch(SQLException e){
            System.out.println("Notifikasi : "+e);
        }
        LCount.setText(""+tabMode.getRowCount());
    }

    public void emptTeks() {
        kode_barang.setText("");
        nama_barang.setText("");
        jml_barang.setText("0");
        kode_produsen.setText("");
        nama_produsen.setText("");
        id_merk.setText("");
        nm_merk.setText("");
        thn_produksi.setSelectedIndex(0);
        isbn.setText("");
        id_kategori.setText("");
        nm_kategori.setText("");
        id_jenis.setText("");
        nm_jenis.setText("");
        TCari.setText("");
        kode_barang.requestFocus();
        //Valid.autoNomer(" jns_perawatan ","JP",6,TKd);
        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(kode_barang,4),signed)),0) from inventaris_barang  ","BI",8,kode_barang);
        kode_barang.requestFocus();
    }

    private void getData() {
        if(tbJnsPerawatan.getSelectedRow()!= -1){
            try {
                PreparedStatement ps=koneksiDB.condb().prepareStatement(
                       "select inventaris_barang.kode_barang, inventaris_barang.nama_barang, inventaris_barang.jml_barang, "+
                       "inventaris_barang.kode_produsen, inventaris_barang.id_merk, inventaris_barang.thn_produksi, inventaris_barang.isbn,"+
                       "inventaris_barang.id_kategori, inventaris_barang.id_jenis from inventaris_barang where inventaris_barang.kode_barang=? ");
                ps.setString(1,tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),0).toString());
                ResultSet rs=ps.executeQuery();
                if(rs.next()){
                    kode_barang.setText(rs.getString("kode_barang"));
                    nama_barang.setText(rs.getString("nama_barang"));
                    jml_barang.setText(rs.getString("jml_barang"));
                    kode_produsen.setText(rs.getString("kode_produsen"));
                    id_merk.setText(rs.getString("id_merk"));
                    thn_produksi.setSelectedItem(rs.getString("thn_produksi"));
                    isbn.setText(rs.getString("isbn"));
                    id_kategori.setText(rs.getString("id_kategori"));
                    id_jenis.setText(rs.getString("id_jenis"));
                }
                nama_produsen.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),3).toString());
                nm_merk.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),4).toString());
                nama_produsen.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),3).toString());
                nm_kategori.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),7).toString());
                nm_jenis.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),8).toString());
            } catch (SQLException ex) {
                System.out.println("Notifikasi : "+ex);
            }
        }
    }

    

    public JTable getTable(){
        return tbJnsPerawatan;
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,155));
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
        BtnSimpan.setEnabled(akses.getinventaris_koleksi());
        BtnHapus.setEnabled(akses.getinventaris_koleksi());
        BtnEdit.setEnabled(akses.getinventaris_koleksi());
        BtnPrint.setEnabled(akses.getinventaris_koleksi());
        TCari.requestFocus();
    }
    
   

    
}

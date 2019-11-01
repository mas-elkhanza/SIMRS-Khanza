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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariPegawai;

/**
 *
 * @author dosen
 */
public final class InventarisPermintaanPerbaikan extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement ps;
    private ResultSet rs;
    private DlgCariPegawai petugas=new DlgCariPegawai(null,false);

    /** Creates new form DlgJnsPerawatan
     * @param parent
     * @param modal */
    public InventarisPermintaanPerbaikan(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        this.setLocation(8,1);
        setSize(628,674);

        Object[] row={"No.Inventaris","Kode Barang","Nama Barang","Produsen",
                      "Merk","Thn.Produksi","Barcode SN","Kategori","Jenis",
                      "Asal Barang","Tgl.Pengadaan","Harga","Stts.Barang","Ruang",
                      "No.Rak","No.Box"
        };
        tabMode=new DefaultTableModel(null,row){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbJnsPerawatan.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbJnsPerawatan.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbJnsPerawatan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 16; i++) {
            TableColumn column = tbJnsPerawatan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(100);
            }else if(i==1){
                column.setPreferredWidth(100);
            }else if(i==2){
                column.setPreferredWidth(200);
            }else if(i==3){
                column.setPreferredWidth(150);
            }else if(i==4){
                column.setPreferredWidth(150);
            }else if(i==5){
                column.setPreferredWidth(70);
            }else if(i==6){
                column.setPreferredWidth(100);
            }else if(i==7){
                column.setPreferredWidth(160);
            }else if(i==8){
                column.setPreferredWidth(100);
            }else if(i==9){
                column.setPreferredWidth(150);
            }else if(i==10){
                column.setPreferredWidth(80);
            }else if(i==11){
                column.setPreferredWidth(90);
            }else if(i==12){
                column.setPreferredWidth(100);
            }else if(i==13){
                column.setPreferredWidth(120);
            }else if(i==14){
                column.setPreferredWidth(60);
            }else if(i==15){
                column.setPreferredWidth(60);
            }
        }
        tbJnsPerawatan.setDefaultRenderer(Object.class, new WarnaTable());

        nopermintaan.setDocument(new batasInput((byte)30).getKata(nopermintaan));
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        TCari.requestFocus();
        
        ChkInput.setSelected(false);
        isForm(); 
        
        inventaris.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(inventaris.getTable().getSelectedRow()!= -1){     
                       no_inventaris.setText(inventaris.getTable().getValueAt(inventaris.getTable().getSelectedRow(),0).toString());
                       nama_barang.setText(inventaris.getTable().getValueAt(inventaris.getTable().getSelectedRow(),1).toString()+", "+inventaris.getTable().getValueAt(inventaris.getTable().getSelectedRow(),2).toString());
                       merk.setText(inventaris.getTable().getValueAt(inventaris.getTable().getSelectedRow(),4).toString());
                       jenis.setText(inventaris.getTable().getValueAt(inventaris.getTable().getSelectedRow(),8).toString());
                       kategori.setText(inventaris.getTable().getValueAt(inventaris.getTable().getSelectedRow(),7).toString());                        
                       nopermintaan.requestFocus();
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
        
        inventaris.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    inventaris.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        ruang.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(ruang.getTable().getSelectedRow()!= -1){ 
                        nm_ruangcari.setText(ruang.getTable().getValueAt(ruang.getTable().getSelectedRow(),1).toString());
                        TCari.requestFocus();
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
        
        ruang.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    ruang.dispose();
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
        
         petugas.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(petugas.getTable().getSelectedRow()!= -1){                   
                    nip.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                    nama_petugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                    departemen.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),5).toString());
                }   
                nip.requestFocus();
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
    private InventarisKoleksi inventaris=new InventarisKoleksi(null,false);
    private InventarisRuang ruang=new InventarisRuang(null,false); 

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Popup = new javax.swing.JPopupMenu();
        ppBarcode = new javax.swing.JMenuItem();
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
        label23 = new widget.Label();
        nm_ruangcari = new widget.TextBox();
        btnRuang1 = new widget.Button();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();
        PanelInput = new javax.swing.JPanel();
        FormInput = new widget.PanelBiasa();
        label1 = new widget.Label();
        nopermintaan = new widget.TextBox();
        label8 = new widget.Label();
        tgl_permintaan = new widget.Tanggal();
        btnInv = new widget.Button();
        nama_barang = new widget.TextBox();
        no_inventaris = new widget.TextBox();
        label2 = new widget.Label();
        label11 = new widget.Label();
        jenis = new widget.TextBox();
        label12 = new widget.Label();
        merk = new widget.TextBox();
        label9 = new widget.Label();
        nip = new widget.TextBox();
        nama_petugas = new widget.TextBox();
        btnPtg = new widget.Button();
        label7 = new widget.Label();
        deskripsi = new widget.TextBox();
        kategori = new widget.TextBox();
        label13 = new widget.Label();
        departemen = new widget.TextBox();
        ChkInput = new widget.CekBox();

        Popup.setName("Popup"); // NOI18N

        ppBarcode.setBackground(new java.awt.Color(255, 255, 254));
        ppBarcode.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppBarcode.setForeground(new java.awt.Color(50, 50, 50));
        ppBarcode.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png"))); // NOI18N
        ppBarcode.setText("Barcode");
        ppBarcode.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppBarcode.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppBarcode.setName("ppBarcode"); // NOI18N
        ppBarcode.setPreferredSize(new java.awt.Dimension(150, 25));
        ppBarcode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppBarcodeBtnPrintActionPerformed(evt);
            }
        });
        Popup.add(ppBarcode);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Permintaan Perbaikan Aset/Inventaris ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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

        label23.setText("Ruang :");
        label23.setName("label23"); // NOI18N
        label23.setPreferredSize(new java.awt.Dimension(50, 23));
        panelGlass9.add(label23);

        nm_ruangcari.setEditable(false);
        nm_ruangcari.setName("nm_ruangcari"); // NOI18N
        nm_ruangcari.setPreferredSize(new java.awt.Dimension(190, 23));
        panelGlass9.add(nm_ruangcari);

        btnRuang1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnRuang1.setMnemonic('1');
        btnRuang1.setToolTipText("Alt+1");
        btnRuang1.setName("btnRuang1"); // NOI18N
        btnRuang1.setPreferredSize(new java.awt.Dimension(28, 23));
        btnRuang1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRuang1ActionPerformed(evt);
            }
        });
        panelGlass9.add(btnRuang1);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(jLabel6);

        TCari.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(230, 23));
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

        jLabel7.setText("Record  :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(65, 23));
        panelGlass9.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(60, 23));
        panelGlass9.add(LCount);

        jPanel3.add(panelGlass9, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 185));
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(100, 127));
        FormInput.setLayout(null);

        label1.setText("Nomor Permintaan :");
        label1.setName("label1"); // NOI18N
        FormInput.add(label1);
        label1.setBounds(0, 10, 120, 23);

        nopermintaan.setName("nopermintaan"); // NOI18N
        nopermintaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nopermintaanKeyPressed(evt);
            }
        });
        FormInput.add(nopermintaan);
        nopermintaan.setBounds(123, 10, 180, 23);

        label8.setText("Tgl.Permintaan Perbaikan :");
        label8.setName("label8"); // NOI18N
        FormInput.add(label8);
        label8.setBounds(337, 10, 200, 23);

        tgl_permintaan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "01-11-2019 15:52:07" }));
        tgl_permintaan.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        tgl_permintaan.setName("tgl_permintaan"); // NOI18N
        tgl_permintaan.setOpaque(false);
        tgl_permintaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tgl_permintaanKeyPressed(evt);
            }
        });
        FormInput.add(tgl_permintaan);
        tgl_permintaan.setBounds(546, 10, 140, 23);

        btnInv.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnInv.setMnemonic('1');
        btnInv.setToolTipText("Alt+1");
        btnInv.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnInv.setName("btnInv"); // NOI18N
        btnInv.setPreferredSize(new java.awt.Dimension(100, 30));
        btnInv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInvActionPerformed(evt);
            }
        });
        FormInput.add(btnInv);
        btnInv.setBounds(690, 40, 25, 23);

        nama_barang.setEditable(false);
        nama_barang.setName("nama_barang"); // NOI18N
        FormInput.add(nama_barang);
        nama_barang.setBounds(256, 40, 430, 23);

        no_inventaris.setName("no_inventaris"); // NOI18N
        no_inventaris.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                no_inventarisKeyPressed(evt);
            }
        });
        FormInput.add(no_inventaris);
        no_inventaris.setBounds(123, 40, 130, 23);

        label2.setText("Nomor Inventaris :");
        label2.setName("label2"); // NOI18N
        FormInput.add(label2);
        label2.setBounds(0, 40, 120, 23);

        label11.setText("Jenis :");
        label11.setName("label11"); // NOI18N
        FormInput.add(label11);
        label11.setBounds(0, 70, 120, 23);

        jenis.setEditable(false);
        jenis.setName("jenis"); // NOI18N
        FormInput.add(jenis);
        jenis.setBounds(123, 70, 160, 23);

        label12.setText("Merk :");
        label12.setName("label12"); // NOI18N
        FormInput.add(label12);
        label12.setBounds(512, 70, 40, 23);

        merk.setEditable(false);
        merk.setName("merk"); // NOI18N
        FormInput.add(merk);
        merk.setBounds(555, 70, 160, 23);

        label9.setText("Yang Meminta :");
        label9.setName("label9"); // NOI18N
        FormInput.add(label9);
        label9.setBounds(0, 130, 120, 23);

        nip.setEditable(false);
        nip.setName("nip"); // NOI18N
        nip.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nipKeyPressed(evt);
            }
        });
        FormInput.add(nip);
        nip.setBounds(123, 130, 130, 23);

        nama_petugas.setEditable(false);
        nama_petugas.setName("nama_petugas"); // NOI18N
        FormInput.add(nama_petugas);
        nama_petugas.setBounds(256, 130, 307, 23);

        btnPtg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPtg.setMnemonic('3');
        btnPtg.setToolTipText("Alt+3");
        btnPtg.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnPtg.setName("btnPtg"); // NOI18N
        btnPtg.setPreferredSize(new java.awt.Dimension(100, 30));
        btnPtg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPtgActionPerformed(evt);
            }
        });
        FormInput.add(btnPtg);
        btnPtg.setBounds(690, 130, 25, 23);

        label7.setText("Deskripsi Kerusakan :");
        label7.setName("label7"); // NOI18N
        FormInput.add(label7);
        label7.setBounds(0, 100, 120, 23);

        deskripsi.setName("deskripsi"); // NOI18N
        deskripsi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                deskripsiKeyPressed(evt);
            }
        });
        FormInput.add(deskripsi);
        deskripsi.setBounds(123, 100, 592, 23);

        kategori.setEditable(false);
        kategori.setName("kategori"); // NOI18N
        FormInput.add(kategori);
        kategori.setBounds(348, 70, 160, 23);

        label13.setText("Kategori :");
        label13.setName("label13"); // NOI18N
        FormInput.add(label13);
        label13.setBounds(275, 70, 70, 23);

        departemen.setEditable(false);
        departemen.setName("departemen"); // NOI18N
        FormInput.add(departemen);
        departemen.setBounds(566, 130, 120, 23);

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
        if(nopermintaan.getText().trim().equals("")){
            Valid.textKosong(nopermintaan,"No.Inventaris");
        }else {
               
            tampil();
            emptTeks();
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,deskripsi,BtnBatal);
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
        Valid.hapusTable(tabMode,nopermintaan,"inventaris","no_inventaris");
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
        }else{Valid.pindah(evt,BtnEdit,TCari);}
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
                param.put("ruang","%"+nm_ruangcari.getText().trim()+"%"); 
                param.put("parameter","%"+TCari.getText().trim()+"%"); 
                Valid.MyReport("rptInv.jasper","report","::[ Data Inv Barang ]::",param);
        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnEdit, BtnKeluar);
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
        nm_ruangcari.setText("");
        tampil();
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            tampil();
            TCari.setText("");
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

private void nopermintaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nopermintaanKeyPressed
    Valid.pindah(evt,TCari, tgl_permintaan);
}//GEN-LAST:event_nopermintaanKeyPressed

private void tgl_permintaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tgl_permintaanKeyPressed
    Valid.pindah(evt,nopermintaan,no_inventaris);
}//GEN-LAST:event_tgl_permintaanKeyPressed

private void ppBarcodeBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBarcodeBtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        BtnCariActionPerformed(evt);
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
            param.put("ruang","%"+nm_ruangcari.getText().trim()+"%"); 
            param.put("parameter","%"+TCari.getText().trim()+"%"); 
            Valid.MyReport("rptBarcodeInventaris.jasper","report","::[ Data Barang ]::",param);            
        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_ppBarcodeBtnPrintActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void btnRuang1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRuang1ActionPerformed
        ruang.isCek();
        ruang.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        ruang.setLocationRelativeTo(internalFrame1);
        ruang.setAlwaysOnTop(false);
        ruang.setVisible(true);
    }//GEN-LAST:event_btnRuang1ActionPerformed

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

    private void btnInvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInvActionPerformed
        inventaris.isCek();
        inventaris.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        inventaris.setLocationRelativeTo(internalFrame1);
        inventaris.setAlwaysOnTop(false);
        inventaris.setVisible(true);
    }//GEN-LAST:event_btnInvActionPerformed

    private void no_inventarisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_no_inventarisKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            isInventaris();
            tgl_permintaan.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            isInventaris();
            deskripsi.requestFocus();
        } else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnInvActionPerformed(null);
        }
    }//GEN-LAST:event_no_inventarisKeyPressed

    private void nipKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nipKeyPressed
        Valid.pindah(evt,deskripsi,BtnSimpan);
    }//GEN-LAST:event_nipKeyPressed

    private void btnPtgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPtgActionPerformed
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setAlwaysOnTop(false);
        petugas.setVisible(true);
    }//GEN-LAST:event_btnPtgActionPerformed

    private void deskripsiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_deskripsiKeyPressed
        Valid.pindah(evt,no_inventaris,nip);
    }//GEN-LAST:event_deskripsiKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            InventarisPermintaanPerbaikan dialog = new InventarisPermintaanPerbaikan(new javax.swing.JFrame(), true);
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
    private javax.swing.JPopupMenu Popup;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.Button btnInv;
    private widget.Button btnPtg;
    private widget.Button btnRuang1;
    private widget.TextBox departemen;
    private widget.TextBox deskripsi;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private javax.swing.JPanel jPanel3;
    private widget.TextBox jenis;
    private widget.TextBox kategori;
    private widget.Label label1;
    private widget.Label label11;
    private widget.Label label12;
    private widget.Label label13;
    private widget.Label label2;
    private widget.Label label23;
    private widget.Label label7;
    private widget.Label label8;
    private widget.Label label9;
    private widget.TextBox merk;
    private widget.TextBox nama_barang;
    private widget.TextBox nama_petugas;
    private widget.TextBox nip;
    private widget.TextBox nm_ruangcari;
    private widget.TextBox no_inventaris;
    private widget.TextBox nopermintaan;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private javax.swing.JMenuItem ppBarcode;
    private widget.Table tbJnsPerawatan;
    private widget.Tanggal tgl_permintaan;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        try {
            
            LCount.setText(tabMode.getRowCount()+"");
        } catch (Exception e) {
            System.out.println("Notif : "+e);
        }        
    }

    public void emptTeks() {
        nopermintaan.setText("");
        no_inventaris.setText("");
        nama_barang.setText("");
        jenis.setText("");
        kategori.setText("");
        merk.setText("");
        deskripsi.setText("");
        tgl_permintaan.setDate(new Date());
        TCari.setText("");
        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(no_permintaan,3),signed)),0) from permintaan_perbaikan_inventaris where tanggal='"+Valid.SetTgl(tgl_permintaan.getSelectedItem()+"")+"' ",
                "PPI"+tgl_permintaan.getSelectedItem().toString().substring(6,10)+tgl_permintaan.getSelectedItem().toString().substring(3,5)+tgl_permintaan.getSelectedItem().toString().substring(0,2),3,nopermintaan); 
        nopermintaan.requestFocus();
    }

    private void getData() {
        if(tbJnsPerawatan.getSelectedRow()!= -1){
                nopermintaan.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),0).toString());   
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
        if(akses.getjml2()>=1){
            nip.setEditable(false);
            btnPtg.setEnabled(false);
            BtnSimpan.setEnabled(akses.getpermintaan_perbaikan_inventaris());
            BtnHapus.setEnabled(akses.getpermintaan_perbaikan_inventaris());
            BtnEdit.setEnabled(akses.getpermintaan_perbaikan_inventaris());
            BtnPrint.setEnabled(akses.getpermintaan_perbaikan_inventaris());
            nip.setText(akses.getkode());
            Sequel.cariIsi("select nama from pegawai where nik=?", nama_petugas,nip.getText());
        } 
        TCari.requestFocus();
    }
    
    public void isInventaris(){
        try {
            ps=koneksi.prepareStatement(
               "select inventaris.no_inventaris,inventaris_barang.kode_barang, inventaris_barang.nama_barang, "+
               "inventaris_merk.nama_merk,inventaris_jenis.nama_jenis,inventaris_kategori.nama_kategori "+
               "from inventaris inner join inventaris_barang on inventaris_barang.kode_barang=inventaris.kode_barang "+
               "inner join inventaris_jenis on inventaris_barang.id_jenis=inventaris_jenis.id_jenis "+
               "inner join inventaris_merk on inventaris_barang.id_merk=inventaris_merk.id_merk "+
               "inner join inventaris_kategori on inventaris_barang.id_kategori=inventaris_kategori.id_kategori "+
               "where inventaris.no_inventaris=?");
            try{
                ps.setString(1,no_inventaris.getText());
                rs=ps.executeQuery();
                if(rs.next()){
                    nama_barang.setText(rs.getString("kode_barang")+", "+rs.getString("nama_barang"));
                    merk.setText(rs.getString("nama_merk"));
                    jenis.setText(rs.getString("nama_jenis"));
                    kategori.setText(rs.getString("nama_kategori"));
                }
            } catch (Exception e) {
                System.out.println("Notif : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }
        } catch (Exception ex) {
            System.out.println("Notifikasi : "+ex);
        }
    }

    
}

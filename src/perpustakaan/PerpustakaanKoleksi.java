/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgJnsPerawatan.java
 *
 * Created on May 22, 2010, 11:58:21 PM
 */

package perpustakaan;
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
public final class PerpustakaanKoleksi extends javax.swing.JDialog {
    private DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private Connection koneksi=koneksiDB.condb();

    /** Creates new form DlgJnsPerawatan */
    public PerpustakaanKoleksi(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        this.setLocation(8,1);
        setSize(628,674);

        tabMode=new DefaultTableModel(null,new Object[]{
                "Kode Koleksi","Nama/Judul","Halaman","Penerbit","Pengarang","Terbit","ISBN","Kategori","Jenis"
            }){
            @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbJnsPerawatan.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbJnsPerawatan.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbJnsPerawatan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 9; i++) {
            TableColumn column = tbJnsPerawatan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(90);
            }else if(i==1){
                column.setPreferredWidth(200);
            }else if(i==2){
                column.setPreferredWidth(60);
            }else if(i==3){
                column.setPreferredWidth(150);
            }else if(i==4){
                column.setPreferredWidth(150);
            }else if(i==5){
                column.setPreferredWidth(50);
            }else if(i==6){
                column.setPreferredWidth(100);
            }else if(i==7){
                column.setPreferredWidth(150);
            }else if(i==8){
                column.setPreferredWidth(150);
            }
        }
        tbJnsPerawatan.setDefaultRenderer(Object.class, new WarnaTable());

        KodeBuku.setDocument(new batasInput((byte)10).getKata(KodeBuku));
        Judul.setDocument(new batasInput((int)200).getKata(Judul));
        Halaman.setDocument(new batasInput((byte)5).getOnlyAngka(Halaman));
        KodePenerbit.setDocument(new batasInput((byte)10).getKata(KodePenerbit));
        KodePengarang.setDocument(new batasInput((byte)7).getKata(KodePengarang));
        ISBN.setDocument(new batasInput((byte)20).getKata(ISBN));
        KodeKategori.setDocument(new batasInput((byte)5).getKata(KodeKategori));
        IdJenis.setDocument(new batasInput((byte)5).getKata(IdJenis));        
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        TCari.requestFocus();        
        
      
        ChkInput.setSelected(false);
        isForm(); 
        Valid.LoadTahun(ThnTerbit);
        
        penerbit.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(penerbit.getTable().getSelectedRow()!= -1){                   
                    KodePenerbit.setText(penerbit.getTable().getValueAt(penerbit.getTable().getSelectedRow(),0).toString());                    
                    NamaPenerbit.setText(penerbit.getTable().getValueAt(penerbit.getTable().getSelectedRow(),1).toString());
                }   
                KodePenerbit.requestFocus();
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
        
        penerbit.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    penerbit.dispose();
                }                
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        pengarang.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(pengarang.getTable().getSelectedRow()!= -1){                   
                    KodePengarang.setText(pengarang.getTable().getValueAt(pengarang.getTable().getSelectedRow(),0).toString());                    
                    NamaPengarang.setText(pengarang.getTable().getValueAt(pengarang.getTable().getSelectedRow(),1).toString());
                }   
                KodePengarang.requestFocus();
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
        
        pengarang.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    pengarang.dispose();
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
                    KodeKategori.setText(kategori.getTable().getValueAt(kategori.getTable().getSelectedRow(),0).toString());                    
                    NamaKategori.setText(kategori.getTable().getValueAt(kategori.getTable().getSelectedRow(),1).toString());
                }   
                KodeKategori.requestFocus();
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
                    IdJenis.setText(jenis.getTable().getValueAt(jenis.getTable().getSelectedRow(),0).toString());                    
                    nm_jenis.setText(jenis.getTable().getValueAt(jenis.getTable().getSelectedRow(),1).toString());
                }   
                IdJenis.requestFocus();
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
    private PerpustakaanPenerbit penerbit=new PerpustakaanPenerbit(null,false); 
    private PerpustakaanPengarang pengarang=new PerpustakaanPengarang(null,false); 
    private PerpustakaanKategori kategori=new PerpustakaanKategori(null,false);
    private PerpustakaanJenis jenis=new PerpustakaanJenis(null,false); 

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
        KodeBuku = new widget.TextBox();
        Judul = new widget.TextBox();
        Halaman = new widget.TextBox();
        label7 = new widget.Label();
        ISBN = new widget.TextBox();
        label8 = new widget.Label();
        label10 = new widget.Label();
        label9 = new widget.Label();
        ThnTerbit = new widget.ComboBox();
        label19 = new widget.Label();
        KodePenerbit = new widget.TextBox();
        NamaPenerbit = new widget.TextBox();
        btnPenerbit = new widget.Button();
        label20 = new widget.Label();
        KodePengarang = new widget.TextBox();
        NamaPengarang = new widget.TextBox();
        btnPengarang = new widget.Button();
        label21 = new widget.Label();
        KodeKategori = new widget.TextBox();
        NamaKategori = new widget.TextBox();
        btnKategori = new widget.Button();
        label22 = new widget.Label();
        IdJenis = new widget.TextBox();
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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 253, 247)), "::[ Data Koleksi Perpustakaan ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 3, 9));

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

        label1.setText("Kode Koleksi :");
        label1.setName("label1"); // NOI18N
        FormInput.add(label1);
        label1.setBounds(0, 10, 85, 23);

        KodeBuku.setName("KodeBuku"); // NOI18N
        KodeBuku.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodeBukuKeyPressed(evt);
            }
        });
        FormInput.add(KodeBuku);
        KodeBuku.setBounds(88, 10, 130, 23);

        Judul.setName("Judul"); // NOI18N
        Judul.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JudulKeyPressed(evt);
            }
        });
        FormInput.add(Judul);
        Judul.setBounds(88, 40, 288, 23);

        Halaman.setName("Halaman"); // NOI18N
        Halaman.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HalamanKeyPressed(evt);
            }
        });
        FormInput.add(Halaman);
        Halaman.setBounds(88, 70, 80, 23);

        label7.setText("Tahun Terbit :");
        label7.setName("label7"); // NOI18N
        FormInput.add(label7);
        label7.setBounds(170, 70, 90, 23);

        ISBN.setName("ISBN"); // NOI18N
        ISBN.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ISBNKeyPressed(evt);
            }
        });
        FormInput.add(ISBN);
        ISBN.setBounds(442, 40, 261, 23);

        label8.setText("ISBN :");
        label8.setName("label8"); // NOI18N
        FormInput.add(label8);
        label8.setBounds(369, 40, 70, 23);

        label10.setText("Nama/Judul :");
        label10.setName("label10"); // NOI18N
        FormInput.add(label10);
        label10.setBounds(0, 40, 85, 23);

        label9.setText("Jml.Halaman :");
        label9.setName("label9"); // NOI18N
        FormInput.add(label9);
        label9.setBounds(0, 70, 85, 23);

        ThnTerbit.setName("ThnTerbit"); // NOI18N
        ThnTerbit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ThnTerbitKeyPressed(evt);
            }
        });
        FormInput.add(ThnTerbit);
        ThnTerbit.setBounds(264, 70, 85, 23);

        label19.setText("Penerbit :");
        label19.setName("label19"); // NOI18N
        label19.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label19);
        label19.setBounds(0, 100, 85, 23);

        KodePenerbit.setEditable(false);
        KodePenerbit.setName("KodePenerbit"); // NOI18N
        KodePenerbit.setPreferredSize(new java.awt.Dimension(207, 23));
        KodePenerbit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodePenerbitKeyPressed(evt);
            }
        });
        FormInput.add(KodePenerbit);
        KodePenerbit.setBounds(88, 100, 80, 23);

        NamaPenerbit.setEditable(false);
        NamaPenerbit.setName("NamaPenerbit"); // NOI18N
        NamaPenerbit.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NamaPenerbit);
        NamaPenerbit.setBounds(169, 100, 180, 23);

        btnPenerbit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPenerbit.setMnemonic('1');
        btnPenerbit.setToolTipText("Alt+1");
        btnPenerbit.setName("btnPenerbit"); // NOI18N
        btnPenerbit.setPreferredSize(new java.awt.Dimension(28, 23));
        btnPenerbit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPenerbitActionPerformed(evt);
            }
        });
        FormInput.add(btnPenerbit);
        btnPenerbit.setBounds(351, 100, 25, 23);

        label20.setText("Pengarang :");
        label20.setName("label20"); // NOI18N
        label20.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label20);
        label20.setBounds(349, 10, 90, 23);

        KodePengarang.setEditable(false);
        KodePengarang.setName("KodePengarang"); // NOI18N
        KodePengarang.setPreferredSize(new java.awt.Dimension(207, 23));
        KodePengarang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodePengarangKeyPressed(evt);
            }
        });
        FormInput.add(KodePengarang);
        KodePengarang.setBounds(442, 10, 80, 23);

        NamaPengarang.setEditable(false);
        NamaPengarang.setName("NamaPengarang"); // NOI18N
        NamaPengarang.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NamaPengarang);
        NamaPengarang.setBounds(523, 10, 180, 23);

        btnPengarang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPengarang.setMnemonic('1');
        btnPengarang.setToolTipText("Alt+1");
        btnPengarang.setName("btnPengarang"); // NOI18N
        btnPengarang.setPreferredSize(new java.awt.Dimension(28, 23));
        btnPengarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPengarangActionPerformed(evt);
            }
        });
        FormInput.add(btnPengarang);
        btnPengarang.setBounds(705, 10, 25, 23);

        label21.setText("Kategori :");
        label21.setName("label21"); // NOI18N
        label21.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label21);
        label21.setBounds(369, 70, 70, 23);

        KodeKategori.setEditable(false);
        KodeKategori.setName("KodeKategori"); // NOI18N
        KodeKategori.setPreferredSize(new java.awt.Dimension(207, 23));
        KodeKategori.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodeKategoriKeyPressed(evt);
            }
        });
        FormInput.add(KodeKategori);
        KodeKategori.setBounds(442, 70, 80, 23);

        NamaKategori.setEditable(false);
        NamaKategori.setName("NamaKategori"); // NOI18N
        NamaKategori.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NamaKategori);
        NamaKategori.setBounds(523, 70, 180, 23);

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

        IdJenis.setEditable(false);
        IdJenis.setName("IdJenis"); // NOI18N
        IdJenis.setPreferredSize(new java.awt.Dimension(207, 23));
        IdJenis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                IdJenisKeyPressed(evt);
            }
        });
        FormInput.add(IdJenis);
        IdJenis.setBounds(442, 100, 80, 23);

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
        if(KodeBuku.getText().trim().equals("")){
            Valid.textKosong(KodeBuku,"Kode Koleksi");
        }else if(Judul.getText().trim().equals("")){
            Valid.textKosong(Judul,"Nama Koleksi");
        }else if(Halaman.getText().trim().equals("")){
            Valid.textKosong(Halaman,"Jumlah Halaman");
        }else if(KodePenerbit.getText().trim().equals("")||NamaPenerbit.getText().trim().equals("")){
            Valid.textKosong(KodePenerbit,"Penerbit");
        }else if(KodePengarang.getText().trim().equals("")||NamaPengarang.getText().trim().equals("")){
            Valid.textKosong(KodePengarang,"Pengarang");
        }else if(IdJenis.getText().trim().equals("")||nm_jenis.getText().trim().equals("")){
            Valid.textKosong(IdJenis,"Jenis");
        }else if(KodeKategori.getText().trim().equals("")||NamaKategori.getText().trim().equals("")){
            Valid.textKosong(KodeKategori,"Kategori");
        }else {
                //menyimpan-------------------------------------------------
                Sequel.menyimpan("perpustakaan_buku","'"+KodeBuku.getText()+"','"+Judul.getText()+"','"+Halaman.getText()+"','"+
                        KodePenerbit.getText()+"','"+KodePengarang.getText()+"','"+ThnTerbit.getSelectedItem()+"','"+ISBN.getText()+"','"+
                        KodeKategori.getText()+"','"+IdJenis.getText()+"'","Kode Koleksi");
                //----------------------------------------------------------
                KodeBuku.requestFocus();
            tampil();
            emptTeks();
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,IdJenis,BtnBatal);
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
        Valid.hapusTable(tabMode,KodeBuku,"perpustakaan_buku","kode_buku");
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
        if(KodeBuku.getText().trim().equals("")){
            Valid.textKosong(KodeBuku,"Kode Koleksi");
        }else if(Judul.getText().trim().equals("")){
            Valid.textKosong(Judul,"Nama Koleksi");
        }else if(Halaman.getText().trim().equals("")){
            Valid.textKosong(Halaman,"Jumlah Halaman");
        }else if(KodePenerbit.getText().trim().equals("")||NamaPenerbit.getText().trim().equals("")){
            Valid.textKosong(KodePenerbit,"Penerbit");
        }else if(KodePengarang.getText().trim().equals("")||NamaPengarang.getText().trim().equals("")){
            Valid.textKosong(KodePengarang,"Pengarang");
        }else if(IdJenis.getText().trim().equals("")||nm_jenis.getText().trim().equals("")){
            Valid.textKosong(IdJenis,"Jenis");
        }else if(KodeKategori.getText().trim().equals("")||NamaKategori.getText().trim().equals("")){
            Valid.textKosong(KodeKategori,"Kategori");
        }else {
            if(tbJnsPerawatan.getSelectedRow()> -1){
                //menyimpan-------------------------------------------------
                Sequel.mengedit("perpustakaan_buku","kode_buku='"+tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),0) +"'",
                        "kode_buku='"+KodeBuku.getText()+"',judul_buku='"+Judul.getText()+"',jml_halaman='"+Halaman.getText()+"',kode_penerbit='"+
                        KodePenerbit.getText()+"',kode_pengarang='"+KodePengarang.getText()+"',thn_terbit='"+ThnTerbit.getSelectedItem()+"',isbn='"+ISBN.getText()+"',id_kategori='"+
                        KodeKategori.getText()+"',id_jenis='"+IdJenis.getText()+"'");
                //----------------------------------------------------------
                KodeBuku.requestFocus();
                tampil();
                emptTeks();
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
                Valid.MyReportqry("rptKoleksiPerpustakaan.jasper","report","::[ Data Koleksi Perpustakaan ]::","select perpustakaan_buku.kode_buku, perpustakaan_buku.judul_buku, perpustakaan_buku.jml_halaman, "+
                   "perpustakaan_penerbit.nama_penerbit, perpustakaan_pengarang.nama_pengarang, perpustakaan_buku.thn_terbit, perpustakaan_buku.isbn,"+
                   "perpustakaan_kategori.nama_kategori, perpustakaan_jenis_buku.nama_jenis from perpustakaan_buku inner join perpustakaan_penerbit "+
                   "inner join perpustakaan_jenis_buku inner join perpustakaan_kategori inner join perpustakaan_pengarang "+
                   "on perpustakaan_buku.kode_penerbit=perpustakaan_penerbit.kode_penerbit and perpustakaan_buku.kode_pengarang=perpustakaan_pengarang.kode_pengarang "+
                   "and perpustakaan_buku.id_kategori=perpustakaan_kategori.id_kategori and perpustakaan_buku.id_jenis=perpustakaan_jenis_buku.id_jenis "+
                   "where perpustakaan_buku.kode_buku like '%"+TCari.getText().trim()+"%' "+
                    "or perpustakaan_buku.judul_buku like '%"+TCari.getText().trim()+"%' "+
                    "or perpustakaan_buku.jml_halaman like '%"+TCari.getText().trim()+"%' "+
                    "or perpustakaan_penerbit.nama_penerbit like '%"+TCari.getText().trim()+"%' "+
                    "or perpustakaan_pengarang.nama_pengarang like '%"+TCari.getText().trim()+"%' "+
                    "or perpustakaan_buku.thn_terbit like '%"+TCari.getText().trim()+"%' "+
                    "or perpustakaan_buku.isbn like '%"+TCari.getText().trim()+"%' "+
                    "or perpustakaan_kategori.nama_kategori like '%"+TCari.getText().trim()+"%' "+
                    "or perpustakaan_jenis_buku.nama_jenis like '%"+TCari.getText().trim()+"%' order by perpustakaan_buku.kode_buku",param);
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

private void KodeBukuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodeBukuKeyPressed
        Valid.pindah(evt,IdJenis,Judul,TCari);
}//GEN-LAST:event_KodeBukuKeyPressed

private void JudulKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JudulKeyPressed
        Valid.pindah(evt,KodeBuku,Halaman);
}//GEN-LAST:event_JudulKeyPressed

private void HalamanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HalamanKeyPressed
        Valid.pindah(evt,Judul,ThnTerbit);
}//GEN-LAST:event_HalamanKeyPressed

private void ISBNKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ISBNKeyPressed
        Valid.pindah(evt,KodePengarang,KodeKategori);
}//GEN-LAST:event_ISBNKeyPressed

private void ThnTerbitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ThnTerbitKeyPressed
    Valid.pindah(evt,Halaman,KodePenerbit);
}//GEN-LAST:event_ThnTerbitKeyPressed

private void KodePenerbitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodePenerbitKeyPressed
    if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
        Sequel.cariIsi("select perpustakaan_penerbit.nama_penerbit from perpustakaan_penerbit where perpustakaan_penerbit.kode_penerbit=?",NamaPenerbit,KodePenerbit.getText());        
    }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
        Sequel.cariIsi("select perpustakaan_penerbit.nama_penerbit from perpustakaan_penerbit where perpustakaan_penerbit.kode_penerbit=?",NamaPenerbit,KodePenerbit.getText()); 
        ThnTerbit.requestFocus();
    }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
        Sequel.cariIsi("select perpustakaan_penerbit.nama_penerbit from perpustakaan_penerbit where perpustakaan_penerbit.kode_penerbit=?",NamaPenerbit,KodePenerbit.getText()); 
        KodePengarang.requestFocus();
    }else if(evt.getKeyCode()==KeyEvent.VK_UP){
        btnPenerbitActionPerformed(null);
    }
}//GEN-LAST:event_KodePenerbitKeyPressed

private void btnPenerbitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPenerbitActionPerformed
    penerbit.isCek();
    penerbit.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
    penerbit.setLocationRelativeTo(internalFrame1);
    penerbit.setVisible(true);
}//GEN-LAST:event_btnPenerbitActionPerformed

private void KodePengarangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodePengarangKeyPressed
    if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
        Sequel.cariIsi("select perpustakaan_pengarang.nama_pengarang from perpustakaan_pengarang where perpustakaan_pengarang.kode_pengarang=?",NamaPengarang,KodePengarang.getText());        
    }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
        Sequel.cariIsi("select perpustakaan_pengarang.nama_pengarang from perpustakaan_pengarang where perpustakaan_pengarang.kode_pengarang=?",NamaPengarang,KodePengarang.getText());   
        KodePenerbit.requestFocus();
    }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
        Sequel.cariIsi("select perpustakaan_pengarang.nama_pengarang from perpustakaan_pengarang where perpustakaan_pengarang.kode_pengarang=?",NamaPengarang,KodePengarang.getText());   
        ISBN.requestFocus();
    }else if(evt.getKeyCode()==KeyEvent.VK_UP){
        btnPengarangActionPerformed(null);
    }
}//GEN-LAST:event_KodePengarangKeyPressed

private void btnPengarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPengarangActionPerformed
    pengarang.isCek();
    pengarang.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
    pengarang.setLocationRelativeTo(internalFrame1);
    pengarang.setVisible(true);
}//GEN-LAST:event_btnPengarangActionPerformed

private void KodeKategoriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodeKategoriKeyPressed
    if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
        Sequel.cariIsi("select perpustakaan_kategori.nama_kategori from perpustakaan_kategori where perpustakaan_kategori.id_kategori='"+KodeKategori.getText()+"'",NamaKategori);       
    }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
        Sequel.cariIsi("select perpustakaan_kategori.nama_kategori from perpustakaan_kategori where perpustakaan_kategori.id_kategori='"+KodeKategori.getText()+"'",NamaKategori);
        ISBN.requestFocus();
    }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
        Sequel.cariIsi("select perpustakaan_kategori.nama_kategori from perpustakaan_kategori where perpustakaan_kategori.id_kategori='"+KodeKategori.getText()+"'",NamaKategori);
        IdJenis.requestFocus();
    }else if(evt.getKeyCode()==KeyEvent.VK_UP){
        btnKategoriActionPerformed(null);
    }
}//GEN-LAST:event_KodeKategoriKeyPressed

private void btnKategoriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKategoriActionPerformed
    kategori.isCek();
    kategori.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
    kategori.setLocationRelativeTo(internalFrame1);
    kategori.setAlwaysOnTop(false);
    kategori.setVisible(true);
}//GEN-LAST:event_btnKategoriActionPerformed

private void IdJenisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_IdJenisKeyPressed
    if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
        Sequel.cariIsi("select perpustakaan_jenis_buku.nama_jenis from perpustakaan_jenis_buku where perpustakaan_jenis_buku.id_jenis=?",nm_jenis,IdJenis.getText());       
    }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
        Sequel.cariIsi("select perpustakaan_jenis_buku.nama_jenis from perpustakaan_jenis_buku where perpustakaan_jenis_buku.id_jenis=?",nm_jenis,IdJenis.getText());  
        KodeKategori.requestFocus();
    }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
        Sequel.cariIsi("select perpustakaan_jenis_buku.nama_jenis from perpustakaan_jenis_buku where perpustakaan_jenis_buku.id_jenis=?",nm_jenis,IdJenis.getText());  
        BtnSimpan.requestFocus();
    }else if(evt.getKeyCode()==KeyEvent.VK_UP){
        btnJenisActionPerformed(null);
    }
}//GEN-LAST:event_IdJenisKeyPressed

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
            PerpustakaanKoleksi dialog = new PerpustakaanKoleksi(new javax.swing.JFrame(), true);
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
    private widget.TextBox Halaman;
    private widget.TextBox ISBN;
    private widget.TextBox IdJenis;
    private widget.TextBox Judul;
    private widget.TextBox KodeBuku;
    private widget.TextBox KodeKategori;
    private widget.TextBox KodePenerbit;
    private widget.TextBox KodePengarang;
    private widget.Label LCount;
    private widget.TextBox NamaKategori;
    private widget.TextBox NamaPenerbit;
    private widget.TextBox NamaPengarang;
    private javax.swing.JPanel PanelInput;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.ComboBox ThnTerbit;
    private widget.Button btnJenis;
    private widget.Button btnKategori;
    private widget.Button btnPenerbit;
    private widget.Button btnPengarang;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private javax.swing.JPanel jPanel3;
    private widget.Label label1;
    private widget.Label label10;
    private widget.Label label19;
    private widget.Label label20;
    private widget.Label label21;
    private widget.Label label22;
    private widget.Label label7;
    private widget.Label label8;
    private widget.Label label9;
    private widget.TextBox nm_jenis;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.Table tbJnsPerawatan;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            ps=koneksi.prepareStatement("select perpustakaan_buku.kode_buku, perpustakaan_buku.judul_buku, perpustakaan_buku.jml_halaman, "+
                   "perpustakaan_penerbit.nama_penerbit, perpustakaan_pengarang.nama_pengarang, perpustakaan_buku.thn_terbit, perpustakaan_buku.isbn,"+
                   "perpustakaan_kategori.nama_kategori, perpustakaan_jenis_buku.nama_jenis from perpustakaan_buku inner join perpustakaan_penerbit "+
                   "inner join perpustakaan_jenis_buku inner join perpustakaan_kategori inner join perpustakaan_pengarang "+
                   "on perpustakaan_buku.kode_penerbit=perpustakaan_penerbit.kode_penerbit and perpustakaan_buku.kode_pengarang=perpustakaan_pengarang.kode_pengarang "+
                   "and perpustakaan_buku.id_kategori=perpustakaan_kategori.id_kategori and perpustakaan_buku.id_jenis=perpustakaan_jenis_buku.id_jenis "+
                   "where perpustakaan_buku.kode_buku like ? "+
                    "or perpustakaan_buku.judul_buku like ? "+
                    "or perpustakaan_buku.jml_halaman like ? "+
                    "or perpustakaan_penerbit.nama_penerbit like ? "+
                    "or perpustakaan_pengarang.nama_pengarang like ? "+
                    "or perpustakaan_buku.thn_terbit like ? "+
                    "or perpustakaan_buku.isbn like ? "+
                    "or perpustakaan_kategori.nama_kategori like ? "+
                    "or perpustakaan_jenis_buku.nama_jenis like ? order by perpustakaan_buku.kode_buku");
            try {
                ps.setString(1,"%"+TCari.getText().trim()+"%");
                ps.setString(2,"%"+TCari.getText().trim()+"%");
                ps.setString(3,"%"+TCari.getText().trim()+"%");
                ps.setString(4,"%"+TCari.getText().trim()+"%");
                ps.setString(5,"%"+TCari.getText().trim()+"%");
                ps.setString(6,"%"+TCari.getText().trim()+"%");
                ps.setString(7,"%"+TCari.getText().trim()+"%");
                ps.setString(8,"%"+TCari.getText().trim()+"%");
                ps.setString(9,"%"+TCari.getText().trim()+"%");
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new Object[]{
                        rs.getString("kode_buku"),rs.getString("judul_buku"),rs.getString("jml_halaman"),rs.getString("nama_penerbit"),
                        rs.getString("nama_pengarang"),rs.getString("thn_terbit").substring(0,4),rs.getString("isbn"),
                        rs.getString("nama_kategori"),rs.getString("nama_jenis")
                    });
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
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        LCount.setText(""+tabMode.getRowCount());
    }

    public void emptTeks() {
        KodeBuku.setText("");
        Judul.setText("");
        Halaman.setText("0");
        KodePenerbit.setText("");
        NamaPenerbit.setText("");
        KodePengarang.setText("");
        NamaPengarang.setText("");
        ThnTerbit.setSelectedIndex(0);
        ISBN.setText("");
        KodeKategori.setText("");
        NamaKategori.setText("");
        IdJenis.setText("");
        nm_jenis.setText("");
        TCari.setText("");
        KodeBuku.requestFocus();
        //Valid.autoNomer(" jns_perawatan ","JP",6,TKd);
        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(kode_buku,8),signed)),0) from perpustakaan_buku  ","KP",8,KodeBuku);
        KodeBuku.requestFocus();
    }

    private void getData() {
        if(tbJnsPerawatan.getSelectedRow()!= -1){
            try {
                NamaPenerbit.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),3).toString());
                NamaPengarang.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),4).toString());
                NamaPenerbit.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),3).toString());
                NamaKategori.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),7).toString());
                nm_jenis.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),8).toString());
                ThnTerbit.setSelectedItem(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),5).toString());
                
                ps=koneksi.prepareStatement(
                       "select perpustakaan_buku.kode_buku, perpustakaan_buku.judul_buku, perpustakaan_buku.jml_halaman, "+
                       "perpustakaan_buku.kode_penerbit, perpustakaan_buku.kode_pengarang, perpustakaan_buku.thn_terbit, perpustakaan_buku.isbn,"+
                       "perpustakaan_buku.id_kategori, perpustakaan_buku.id_jenis from perpustakaan_buku where perpustakaan_buku.kode_buku=? ");
                try {
                    ps.setString(1,tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),0).toString());
                    rs=ps.executeQuery();
                    if(rs.next()){
                        KodeBuku.setText(rs.getString("kode_buku"));
                        Judul.setText(rs.getString("judul_buku"));
                        Halaman.setText(rs.getString("jml_halaman"));
                        KodePenerbit.setText(rs.getString("kode_penerbit"));
                        KodePengarang.setText(rs.getString("kode_pengarang"));
                        ISBN.setText(rs.getString("isbn"));
                        KodeKategori.setText(rs.getString("id_kategori"));
                        IdJenis.setText(rs.getString("id_jenis"));
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
        BtnSimpan.setEnabled(akses.getkoleksi_perpustakaan());
        BtnHapus.setEnabled(akses.getkoleksi_perpustakaan());
        BtnEdit.setEnabled(akses.getkoleksi_perpustakaan());
        TCari.requestFocus();
    }
    
   

    
}

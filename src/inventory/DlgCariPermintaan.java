package inventory;
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
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import simrskhanza.DlgCariBangsal;
import kepegawaian.DlgCariPegawai;

public class DlgCariPermintaan extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    public  DlgCariBangsal suplier=new DlgCariBangsal(null,false);
    public  DlgCariPegawai pegawai=new DlgCariPegawai(null,false);
    public  DlgBarang barang=new DlgBarang(null,false);
    private PreparedStatement ps,ps2;
    private ResultSet rs,rs2;
    private DlgMutasiBarang aplikasi=new DlgMutasiBarang(null,false);
    private DlgPengeluaranApotek aplikasi2=new DlgPengeluaranApotek(null,false);

    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public DlgCariPermintaan(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        Object[] row={"Tanggal","No.Permintaan","Asal Permintaan","Pegawai","Ditujukan Ke"};
        tabMode=new DefaultTableModel(null,row){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbDokter.setModel(tabMode);

        tbDokter.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 5; i++) {
            TableColumn column = tbDokter.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(70);
            }else if(i==1){
                column.setPreferredWidth(400);
            }else if(i==2){
                column.setPreferredWidth(100);
            }else if(i==3){
                column.setPreferredWidth(200);
            }else if(i==4){
                column.setPreferredWidth(150);
            }
        }
        tbDokter.setDefaultRenderer(Object.class, new WarnaTable());

        NoPermintaan.setDocument(new batasInput((byte)20).getKata(NoPermintaan));
        KdBangsal.setDocument(new batasInput((byte)5).getKata(KdBangsal));
        KdPeg.setDocument(new batasInput((byte)20).getKata(KdPeg));
        kdbar.setDocument(new batasInput((byte)15).getKata(kdbar));
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
        suplier.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(suplier.getTable().getSelectedRow()!= -1){                   
                    KdBangsal.setText(suplier.getTable().getValueAt(suplier.getTable().getSelectedRow(),0).toString());                    
                    NmBangsal.setText(suplier.getTable().getValueAt(suplier.getTable().getSelectedRow(),1).toString());
                }  
                KdBangsal.requestFocus();
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
        
        suplier.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    suplier.dispose();
                }                
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });         
        
        pegawai.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(pegawai.getTable().getSelectedRow()!= -1){                   
                    KdPeg.setText(pegawai.getTable().getValueAt(pegawai.getTable().getSelectedRow(),0).toString());
                    NmPeg.setText(pegawai.getTable().getValueAt(pegawai.getTable().getSelectedRow(),1).toString());
                }            
                KdPeg.requestFocus();
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
                    kdbar.setText(barang.getTable().getValueAt(barang.getTable().getSelectedRow(),1).toString());                    
                    nmbar.setText(barang.getTable().getValueAt(barang.getTable().getSelectedRow(),2).toString());
                }   
                kdbar.requestFocus();
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
        
        barang.jenis.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                    if(barang.jenis.getTable().getSelectedRow()!= -1){                   
                        kdjenis.setText(barang.jenis.getTable().getValueAt(barang.jenis.getTable().getSelectedRow(),0).toString());                    
                        nmjenis.setText(barang.jenis.getTable().getValueAt(barang.jenis.getTable().getSelectedRow(),1).toString());
                    }   
                    kdjenis.requestFocus();
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
        
        barang.golongan.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if (barang.golongan.getTable().getSelectedRow() != -1) {
                    kdgolongan.setText(barang.golongan.getTable().getValueAt(barang.golongan.getTable().getSelectedRow(), 0).toString());
                    nmgolongan.setText(barang.golongan.getTable().getValueAt(barang.golongan.getTable().getSelectedRow(), 1).toString());
                }
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {
                barang.golongan.emptTeks();
            }
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
        
        barang.kategori.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if (barang.kategori.getTable().getSelectedRow() != -1) {
                    kdkategori.setText(barang.kategori.getTable().getValueAt(barang.kategori.getTable().getSelectedRow(), 0).toString());
                    nmkategori.setText(barang.kategori.getTable().getValueAt(barang.kategori.getTable().getSelectedRow(), 1).toString());
                }
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {
                barang.kategori.emptTeks();
            }
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
        
        ChkInput.setSelected(false);
        isForm();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        ppHapus = new javax.swing.JMenuItem();
        ppDisetujui = new javax.swing.JMenuItem();
        ppDisetujui1 = new javax.swing.JMenuItem();
        ppTidakDisetujui = new javax.swing.JMenuItem();
        kdjenis = new widget.TextBox();
        kdgolongan = new widget.TextBox();
        kdkategori = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        scrollPane1 = new widget.ScrollPane();
        tbDokter = new widget.Table();
        jPanel1 = new javax.swing.JPanel();
        panelisi1 = new widget.panelisi();
        label10 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        label9 = new widget.Label();
        LTotal = new widget.Label();
        BtnAll = new widget.Button();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();
        panelisi4 = new widget.panelisi();
        label7 = new widget.Label();
        nmjenis = new widget.TextBox();
        btnJenis = new widget.Button();
        label22 = new widget.Label();
        nmkategori = new widget.TextBox();
        BtnKategori = new widget.Button();
        label23 = new widget.Label();
        nmgolongan = new widget.TextBox();
        BtnGolongan = new widget.Button();
        PanelInput = new javax.swing.JPanel();
        ChkInput = new widget.CekBox();
        FormInput = new widget.panelisi();
        label15 = new widget.Label();
        NoPermintaan = new widget.TextBox();
        label11 = new widget.Label();
        Tanggal1 = new widget.Tanggal();
        label16 = new widget.Label();
        label13 = new widget.Label();
        KdBangsal = new widget.TextBox();
        KdPeg = new widget.TextBox();
        NmBangsal = new widget.TextBox();
        NmPeg = new widget.TextBox();
        btnSuplier = new widget.Button();
        btnPetugas = new widget.Button();
        label12 = new widget.Label();
        Tanggal2 = new widget.Tanggal();
        btnBarang = new widget.Button();
        nmbar = new widget.TextBox();
        kdbar = new widget.TextBox();
        label17 = new widget.Label();
        label14 = new widget.Label();
        Status = new widget.ComboBox();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        ppHapus.setBackground(new java.awt.Color(255, 255, 254));
        ppHapus.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppHapus.setForeground(new java.awt.Color(50, 50, 50));
        ppHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppHapus.setText("Hapus Permintaan Barang");
        ppHapus.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppHapus.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppHapus.setName("ppHapus"); // NOI18N
        ppHapus.setPreferredSize(new java.awt.Dimension(200, 25));
        ppHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppHapusActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppHapus);

        ppDisetujui.setBackground(new java.awt.Color(255, 255, 254));
        ppDisetujui.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppDisetujui.setForeground(new java.awt.Color(50, 50, 50));
        ppDisetujui.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppDisetujui.setText("Disetujui ( Mutasi )");
        ppDisetujui.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppDisetujui.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppDisetujui.setName("ppDisetujui"); // NOI18N
        ppDisetujui.setPreferredSize(new java.awt.Dimension(200, 25));
        ppDisetujui.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppDisetujuiActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppDisetujui);

        ppDisetujui1.setBackground(new java.awt.Color(255, 255, 254));
        ppDisetujui1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppDisetujui1.setForeground(new java.awt.Color(50, 50, 50));
        ppDisetujui1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppDisetujui1.setText("Disetujui ( Stok Keluar )");
        ppDisetujui1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppDisetujui1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppDisetujui1.setName("ppDisetujui1"); // NOI18N
        ppDisetujui1.setPreferredSize(new java.awt.Dimension(200, 25));
        ppDisetujui1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppDisetujui1ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppDisetujui1);

        ppTidakDisetujui.setBackground(new java.awt.Color(255, 255, 254));
        ppTidakDisetujui.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppTidakDisetujui.setForeground(new java.awt.Color(50, 50, 50));
        ppTidakDisetujui.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppTidakDisetujui.setText("Tidak Disetujui");
        ppTidakDisetujui.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppTidakDisetujui.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppTidakDisetujui.setName("ppTidakDisetujui"); // NOI18N
        ppTidakDisetujui.setPreferredSize(new java.awt.Dimension(200, 25));
        ppTidakDisetujui.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppTidakDisetujuiActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppTidakDisetujui);

        kdjenis.setEditable(false);
        kdjenis.setName("kdjenis"); // NOI18N
        kdjenis.setPreferredSize(new java.awt.Dimension(207, 23));
        kdjenis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdjenisKeyPressed(evt);
            }
        });

        kdgolongan.setEditable(false);
        kdgolongan.setName("kdgolongan"); // NOI18N
        kdgolongan.setPreferredSize(new java.awt.Dimension(207, 23));
        kdgolongan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdgolonganKeyPressed(evt);
            }
        });

        kdkategori.setEditable(false);
        kdkategori.setName("kdkategori"); // NOI18N
        kdkategori.setPreferredSize(new java.awt.Dimension(207, 23));
        kdkategori.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdkategoriKeyPressed(evt);
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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 253, 247)), "::[ Cari Permintaan Obat/Alkes/BHP Medis ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

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
        tbDokter.setToolTipText("Silakan klik pada nomor permintaan untuk verifikasi pilihan");
        tbDokter.setComponentPopupMenu(jPopupMenu1);
        tbDokter.setName("tbDokter"); // NOI18N
        scrollPane1.setViewportView(tbDokter);

        internalFrame1.add(scrollPane1, java.awt.BorderLayout.CENTER);

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(816, 100));
        jPanel1.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 56));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        label10.setText("Keyword :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi1.add(label10);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(235, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelisi1.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('5');
        BtnCari.setToolTipText("Alt+5");
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

        label9.setText("Record :");
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(55, 23));
        panelisi1.add(label9);

        LTotal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LTotal.setText("0");
        LTotal.setName("LTotal"); // NOI18N
        LTotal.setPreferredSize(new java.awt.Dimension(50, 23));
        panelisi1.add(LTotal);

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
        panelisi1.add(BtnAll);

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

        panelisi4.setName("panelisi4"); // NOI18N
        panelisi4.setPreferredSize(new java.awt.Dimension(100, 44));
        panelisi4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        label7.setText("Jenis :");
        label7.setName("label7"); // NOI18N
        label7.setPreferredSize(new java.awt.Dimension(40, 23));
        panelisi4.add(label7);

        nmjenis.setEditable(false);
        nmjenis.setName("nmjenis"); // NOI18N
        nmjenis.setPreferredSize(new java.awt.Dimension(155, 23));
        panelisi4.add(nmjenis);

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
        panelisi4.add(btnJenis);

        label22.setText("Kategori :");
        label22.setName("label22"); // NOI18N
        label22.setPreferredSize(new java.awt.Dimension(65, 23));
        panelisi4.add(label22);

        nmkategori.setEditable(false);
        nmkategori.setName("nmkategori"); // NOI18N
        nmkategori.setPreferredSize(new java.awt.Dimension(155, 23));
        panelisi4.add(nmkategori);

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

        label23.setText("Golongan :");
        label23.setName("label23"); // NOI18N
        label23.setPreferredSize(new java.awt.Dimension(65, 23));
        panelisi4.add(label23);

        nmgolongan.setEditable(false);
        nmgolongan.setName("nmgolongan"); // NOI18N
        nmgolongan.setPreferredSize(new java.awt.Dimension(155, 23));
        panelisi4.add(nmgolongan);

        BtnGolongan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnGolongan.setMnemonic('2');
        BtnGolongan.setToolTipText("Alt+2");
        BtnGolongan.setName("BtnGolongan"); // NOI18N
        BtnGolongan.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnGolongan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnGolonganActionPerformed(evt);
            }
        });
        panelisi4.add(BtnGolongan);

        jPanel1.add(panelisi4, java.awt.BorderLayout.CENTER);

        internalFrame1.add(jPanel1, java.awt.BorderLayout.PAGE_END);

        PanelInput.setBackground(new java.awt.Color(255, 255, 255));
        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 125));
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        ChkInput.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput.setMnemonic('M');
        ChkInput.setText(".: Filter Data");
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

        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(100, 74));
        FormInput.setLayout(null);

        label15.setText("No.Permintaan :");
        label15.setName("label15"); // NOI18N
        label15.setPreferredSize(new java.awt.Dimension(60, 23));
        FormInput.add(label15);
        label15.setBounds(0, 10, 92, 23);

        NoPermintaan.setName("NoPermintaan"); // NOI18N
        NoPermintaan.setPreferredSize(new java.awt.Dimension(207, 23));
        NoPermintaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoPermintaanKeyPressed(evt);
            }
        });
        FormInput.add(NoPermintaan);
        NoPermintaan.setBounds(95, 10, 170, 23);

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label11);
        label11.setBounds(0, 40, 92, 23);

        Tanggal1.setDisplayFormat("dd-MM-yyyy");
        Tanggal1.setName("Tanggal1"); // NOI18N
        Tanggal1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Tanggal1KeyPressed(evt);
            }
        });
        FormInput.add(Tanggal1);
        Tanggal1.setBounds(95, 40, 90, 23);

        label16.setText("Asal Permintaan :");
        label16.setName("label16"); // NOI18N
        label16.setPreferredSize(new java.awt.Dimension(60, 23));
        FormInput.add(label16);
        label16.setBounds(326, 10, 110, 23);

        label13.setText("Pegawai :");
        label13.setName("label13"); // NOI18N
        label13.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label13);
        label13.setBounds(326, 40, 110, 23);

        KdBangsal.setEditable(false);
        KdBangsal.setName("KdBangsal"); // NOI18N
        KdBangsal.setPreferredSize(new java.awt.Dimension(80, 23));
        KdBangsal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdBangsalKeyPressed(evt);
            }
        });
        FormInput.add(KdBangsal);
        KdBangsal.setBounds(439, 10, 90, 23);

        KdPeg.setEditable(false);
        KdPeg.setName("KdPeg"); // NOI18N
        KdPeg.setPreferredSize(new java.awt.Dimension(80, 23));
        KdPeg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdPegKeyPressed(evt);
            }
        });
        FormInput.add(KdPeg);
        KdPeg.setBounds(439, 40, 90, 23);

        NmBangsal.setEditable(false);
        NmBangsal.setName("NmBangsal"); // NOI18N
        NmBangsal.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmBangsal);
        NmBangsal.setBounds(531, 10, 200, 23);

        NmPeg.setEditable(false);
        NmPeg.setName("NmPeg"); // NOI18N
        NmPeg.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmPeg);
        NmPeg.setBounds(531, 40, 200, 23);

        btnSuplier.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnSuplier.setMnemonic('1');
        btnSuplier.setToolTipText("Alt+1");
        btnSuplier.setName("btnSuplier"); // NOI18N
        btnSuplier.setPreferredSize(new java.awt.Dimension(28, 23));
        btnSuplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuplierActionPerformed(evt);
            }
        });
        FormInput.add(btnSuplier);
        btnSuplier.setBounds(734, 10, 28, 23);

        btnPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPetugas.setMnemonic('2');
        btnPetugas.setToolTipText("Alt+2");
        btnPetugas.setName("btnPetugas"); // NOI18N
        btnPetugas.setPreferredSize(new java.awt.Dimension(28, 23));
        btnPetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPetugasActionPerformed(evt);
            }
        });
        FormInput.add(btnPetugas);
        btnPetugas.setBounds(734, 40, 28, 23);

        label12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label12.setText("s.d.");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label12);
        label12.setBounds(189, 40, 27, 23);

        Tanggal2.setDisplayFormat("dd-MM-yyyy");
        Tanggal2.setName("Tanggal2"); // NOI18N
        Tanggal2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Tanggal2KeyPressed(evt);
            }
        });
        FormInput.add(Tanggal2);
        Tanggal2.setBounds(220, 40, 90, 23);

        btnBarang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnBarang.setMnemonic('4');
        btnBarang.setToolTipText("Alt+4");
        btnBarang.setName("btnBarang"); // NOI18N
        btnBarang.setPreferredSize(new java.awt.Dimension(28, 23));
        btnBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBarangActionPerformed(evt);
            }
        });
        FormInput.add(btnBarang);
        btnBarang.setBounds(734, 70, 28, 23);

        nmbar.setEditable(false);
        nmbar.setName("nmbar"); // NOI18N
        nmbar.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(nmbar);
        nmbar.setBounds(531, 70, 200, 23);

        kdbar.setEditable(false);
        kdbar.setName("kdbar"); // NOI18N
        kdbar.setPreferredSize(new java.awt.Dimension(80, 23));
        kdbar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdbarKeyPressed(evt);
            }
        });
        FormInput.add(kdbar);
        kdbar.setBounds(439, 70, 90, 23);

        label17.setText("Barang :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label17);
        label17.setBounds(326, 70, 110, 23);

        label14.setText("Status :");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label14);
        label14.setBounds(0, 70, 92, 23);

        Status.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Semua", "Baru", "Disetujui", "Tidak Disetujui" }));
        Status.setName("Status"); // NOI18N
        Status.setPreferredSize(new java.awt.Dimension(40, 23));
        FormInput.add(Status);
        Status.setBounds(95, 70, 170, 23);

        PanelInput.add(FormInput, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        aplikasi.dispose();
        suplier.dispose();
        pegawai.dispose();
        barang.dispose();
        barang.jenis.dispose();
        dispose();  
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){            
            dispose();              
        }else{Valid.pindah(evt,BtnPrint,kdbar);}
}//GEN-LAST:event_BtnKeluarKeyPressed
/*
private void KdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdKeyPressed
    Valid.pindah(evt,BtnCari,Nm);
}//GEN-LAST:event_TKdKeyPressed
*/

    private void btnSuplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuplierActionPerformed
        suplier.emptTeks();
        suplier.isCek();
        suplier.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        suplier.setLocationRelativeTo(internalFrame1);
        suplier.setAlwaysOnTop(false);
        suplier.setVisible(true);
    }//GEN-LAST:event_btnSuplierActionPerformed

    private void btnPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPetugasActionPerformed
        pegawai.emptTeks();
        pegawai.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        pegawai.setLocationRelativeTo(internalFrame1);
        pegawai.setAlwaysOnTop(false);
        pegawai.setVisible(true);
    }//GEN-LAST:event_btnPetugasActionPerformed

    private void Tanggal1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tanggal1KeyPressed
        Valid.pindah(evt,NoPermintaan,KdBangsal);
    }//GEN-LAST:event_Tanggal1KeyPressed

    private void btnBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBarangActionPerformed
        barang.emptTeks();
        barang.isCek();
        barang.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        barang.setLocationRelativeTo(internalFrame1);
        barang.setAlwaysOnTop(false);
        barang.setVisible(true);
    }//GEN-LAST:event_btnBarangActionPerformed

    private void KdBangsalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdBangsalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nm_bangsal from bangsal where kd_bangsal=?", NmBangsal,KdBangsal.getText());            
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select nm_bangsal from bangsal where kd_bangsal=?", NmBangsal,KdBangsal.getText());
            NoPermintaan.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select nm_bangsal from bangsal where kd_bangsal=?", NmBangsal,KdBangsal.getText());
            KdPeg.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnSuplierActionPerformed(null);
        }
    }//GEN-LAST:event_KdBangsalKeyPressed

    private void NoPermintaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoPermintaanKeyPressed
        Valid.pindah(evt, BtnKeluar, KdBangsal);
    }//GEN-LAST:event_NoPermintaanKeyPressed

    private void KdPegKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdPegKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama from pegawai where nik=?",NmPeg,KdPeg.getText());     
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            KdBangsal.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            kdbar.requestFocus();       
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnPetugasActionPerformed(null);
        }
    }//GEN-LAST:event_KdPegKeyPressed

    private void kdbarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdbarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama_brng from databarang where kode_brng=?", nmbar,kdbar.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){  
            kdjenis.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){    
            TCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnBarangActionPerformed(null);
        }
    }//GEN-LAST:event_kdbarKeyPressed

    private void Tanggal2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tanggal2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Tanggal2KeyPressed

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            tbDokter.requestFocus();
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
        NoPermintaan.setText("");
        kdbar.setText("");
        kdjenis.setText("");
        nmjenis.setText("");
        nmbar.setText("");
        KdBangsal.setText("");
        NmBangsal.setText("");
        KdPeg.setText("");
        NmPeg.setText("");
        kdkategori.setText("");
        nmkategori.setText("");
        kdgolongan.setText("");
        nmgolongan.setText("");
        tampil();
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnPrint, BtnKeluar);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        BtnCariActionPerformed(evt);
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            TCari.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            
            Sequel.queryu("truncate table temporary");
            int row=tabMode.getRowCount();
            for(int i=0;i<row;i++){  
                Sequel.menyimpan("temporary","'0','"+
                                tabMode.getValueAt(i,0).toString()+"','"+
                                tabMode.getValueAt(i,1).toString()+"','"+
                                tabMode.getValueAt(i,2).toString()+"','"+
                                tabMode.getValueAt(i,3).toString()+"','"+
                                tabMode.getValueAt(i,4).toString()+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Transaksi Pembelian"); 
            }
            
            
            Map<String, Object> param = new HashMap<>();    
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("emailrs",akses.getemailrs());   
                param.put("logo",Sequel.cariGambar("select logo from setting")); 
            Valid.MyReport("rptPermintaan.jasper","report","::[ Data Permintaan Obat/Alkes/BHP Medis ]::",param);
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt,BtnAll,BtnAll);
        }
    }//GEN-LAST:event_BtnPrintKeyPressed

private void ppHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppHapusActionPerformed
    if(tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString().trim().equals("")){
        Valid.textKosong(TCari,"pilihan data");
    }else{
        Sequel.queryu("delete from permintaan_medis where no_permintaan=?",tbDokter.getValueAt(tbDokter.getSelectedRow(),1).toString().trim());
        tampil();
    }    
}//GEN-LAST:event_ppHapusActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void kdjenisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdjenisKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nm_jenis from ipsrsjenisbarang where kd_jenis=?", nmjenis,kdjenis.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            kdbar.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            TCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnJenisActionPerformed(null);
        }
    }//GEN-LAST:event_kdjenisKeyPressed

    private void btnJenisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnJenisActionPerformed
        barang.jenis.isCek();
        barang.jenis.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        barang.jenis.setLocationRelativeTo(internalFrame1);
        barang.jenis.setVisible(true);
    }//GEN-LAST:event_btnJenisActionPerformed

    private void ppDisetujuiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppDisetujuiActionPerformed
        if(tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString().trim().equals("")){
            Valid.textKosong(TCari,"pilihan data");
        }else{
            Sequel.queryu("update permintaan_medis set status='Disetujui' where no_permintaan=?",tbDokter.getValueAt(tbDokter.getSelectedRow(),1).toString().trim());
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            aplikasi.tampilkanpermintaan=false;
            aplikasi.tampil(tbDokter.getValueAt(tbDokter.getSelectedRow(),1).toString().trim());
            aplikasi.isCek();
            aplikasi.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            aplikasi.setLocationRelativeTo(internalFrame1);
            aplikasi.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
            tampil();
        }
    }//GEN-LAST:event_ppDisetujuiActionPerformed

    private void ppTidakDisetujuiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppTidakDisetujuiActionPerformed
        if(tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString().trim().equals("")){
            Valid.textKosong(TCari,"pilihan data");
        }else{
            Sequel.queryu("update permintaan_medis set status='Tidak Disetujui' where no_permintaan=?",tbDokter.getValueAt(tbDokter.getSelectedRow(),1).toString().trim());
            tampil();
        } 
    }//GEN-LAST:event_ppTidakDisetujuiActionPerformed

    private void ppDisetujui1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppDisetujui1ActionPerformed
        if(tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString().trim().equals("")){
            Valid.textKosong(TCari,"pilihan data");
        }else{
            Sequel.queryu("update permintaan_medis set status='Disetujui' where no_permintaan=?",tbDokter.getValueAt(tbDokter.getSelectedRow(),1).toString().trim());
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            aplikasi2.tampilkanpermintaan=false;
            aplikasi2.tampil(tbDokter.getValueAt(tbDokter.getSelectedRow(),1).toString().trim());
            aplikasi2.isCek();
            aplikasi2.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            aplikasi2.setLocationRelativeTo(internalFrame1);
            aplikasi2.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
            tampil();
        }
    }//GEN-LAST:event_ppDisetujui1ActionPerformed

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void BtnKategoriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKategoriActionPerformed
        barang.kategori.isCek();
        barang.kategori.setSize(internalFrame1.getWidth() -20, internalFrame1.getHeight() -20);
        barang.kategori.setLocationRelativeTo(internalFrame1);
        barang.kategori.setAlwaysOnTop(false);
        barang.kategori.setVisible(true);
    }//GEN-LAST:event_BtnKategoriActionPerformed

    private void BtnGolonganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnGolonganActionPerformed
        barang.golongan.isCek();
        barang.golongan.setSize(internalFrame1.getWidth() -20, internalFrame1.getHeight() -20);
        barang.golongan.setLocationRelativeTo(internalFrame1);
        barang.golongan.setAlwaysOnTop(false);
        barang.golongan.setVisible(true);
    }//GEN-LAST:event_BtnGolonganActionPerformed

    private void kdgolonganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdgolonganKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_kdgolonganKeyPressed

    private void kdkategoriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdkategoriKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_kdkategoriKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgCariPermintaan dialog = new DlgCariPermintaan(new javax.swing.JFrame(), true);
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
    private widget.Button BtnKategori;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.CekBox ChkInput;
    private widget.panelisi FormInput;
    private widget.TextBox KdBangsal;
    private widget.TextBox KdPeg;
    private widget.Label LTotal;
    private widget.TextBox NmBangsal;
    private widget.TextBox NmPeg;
    private widget.TextBox NoPermintaan;
    private javax.swing.JPanel PanelInput;
    private widget.ComboBox Status;
    private widget.TextBox TCari;
    private widget.Tanggal Tanggal1;
    private widget.Tanggal Tanggal2;
    private widget.Button btnBarang;
    private widget.Button btnJenis;
    private widget.Button btnPetugas;
    private widget.Button btnSuplier;
    private widget.InternalFrame internalFrame1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.TextBox kdbar;
    private widget.TextBox kdgolongan;
    private widget.TextBox kdjenis;
    private widget.TextBox kdkategori;
    private widget.Label label10;
    private widget.Label label11;
    private widget.Label label12;
    private widget.Label label13;
    private widget.Label label14;
    private widget.Label label15;
    private widget.Label label16;
    private widget.Label label17;
    private widget.Label label22;
    private widget.Label label23;
    private widget.Label label7;
    private widget.Label label9;
    private widget.TextBox nmbar;
    private widget.TextBox nmgolongan;
    private widget.TextBox nmjenis;
    private widget.TextBox nmkategori;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi4;
    private javax.swing.JMenuItem ppDisetujui;
    private javax.swing.JMenuItem ppDisetujui1;
    private javax.swing.JMenuItem ppHapus;
    private javax.swing.JMenuItem ppTidakDisetujui;
    private widget.ScrollPane scrollPane1;
    private widget.Table tbDokter;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
       Valid.tabelKosong(tabMode);
        try{  
            if(NoPermintaan.getText().equals("")&&NmBangsal.getText().equals("")&&NmPeg.getText().equals("")&&nmjenis.getText().equals("")&&
                 nmkategori.getText().equals("")&&nmgolongan.getText().equals("")&&nmbar.getText().equals("")&&TCari.getText().equals("")&&
                    Status.getSelectedItem().toString().equals("Semua")){
                ps=koneksi.prepareStatement(
                    "select permintaan_medis.tanggal,permintaan_medis.no_permintaan, "+
                    "permintaan_medis.kd_bangsal,bangsal.nm_bangsal as asal, "+
                    "permintaan_medis.nip,pegawai.nama,permintaan_medis.status, "+
                    "permintaan_medis.kd_bangsaltujuan,tujuan.nm_bangsal as tujuan "+
                    "from permintaan_medis inner join bangsal on permintaan_medis.kd_bangsal=bangsal.kd_bangsal "+
                    "inner join detail_permintaan_medis on permintaan_medis.no_permintaan=detail_permintaan_medis.no_permintaan "+
                    "inner join databarang on detail_permintaan_medis.kode_brng=databarang.kode_brng "+
                    "inner join pegawai on permintaan_medis.nip=pegawai.nik "+
                    "inner join kodesatuan on databarang.kode_sat=kodesatuan.kode_sat "+
                    "inner join jenis on databarang.kdjns=jenis.kdjns "+
                    "inner join kategori_barang on kategori_barang.kode=databarang.kode_kategori "+
                    "inner join golongan_barang on golongan_barang.kode=databarang.kode_golongan "+
                    "inner join bangsal as tujuan on permintaan_medis.kd_bangsaltujuan=tujuan.kd_bangsal "+
                    " where permintaan_medis.tanggal between ? and ? group by permintaan_medis.no_permintaan order by permintaan_medis.tanggal,permintaan_medis.no_permintaan ");
            }else{
                ps=koneksi.prepareStatement(
                    "select permintaan_medis.tanggal,permintaan_medis.no_permintaan, "+
                    "permintaan_medis.kd_bangsal,bangsal.nm_bangsal as asal, "+
                    "permintaan_medis.nip,pegawai.nama,permintaan_medis.status, "+
                    "permintaan_medis.kd_bangsaltujuan,tujuan.nm_bangsal as tujuan "+
                    "from permintaan_medis inner join bangsal on permintaan_medis.kd_bangsal=bangsal.kd_bangsal "+
                    "inner join detail_permintaan_medis on permintaan_medis.no_permintaan=detail_permintaan_medis.no_permintaan "+
                    "inner join databarang on detail_permintaan_medis.kode_brng=databarang.kode_brng "+
                    "inner join pegawai on permintaan_medis.nip=pegawai.nik "+
                    "inner join kodesatuan on databarang.kode_sat=kodesatuan.kode_sat "+
                    "inner join jenis on databarang.kdjns=jenis.kdjns "+
                    "inner join kategori_barang on kategori_barang.kode=databarang.kode_kategori "+
                    "inner join golongan_barang on golongan_barang.kode=databarang.kode_golongan "+
                    "inner join bangsal as tujuan on permintaan_medis.kd_bangsaltujuan=tujuan.kd_bangsal "+
                    " where permintaan_medis.tanggal between ? and ? and permintaan_medis.status like ? and permintaan_medis.no_permintaan like ? and bangsal.nm_bangsal like ? and pegawai.nama like ?  and concat(databarang.kdjns,jenis.nama) like ? and concat(databarang.kode_kategori,kategori_barang.nama) like ? and concat(databarang.kode_golongan,golongan_barang.nama) like ? and databarang.nama_brng like ? and permintaan_medis.no_permintaan like ? or "+
                    " permintaan_medis.tanggal between ? and ? and permintaan_medis.status like ? and permintaan_medis.no_permintaan like ? and bangsal.nm_bangsal like ? and pegawai.nama like ?  and concat(databarang.kdjns,jenis.nama) like ? and concat(databarang.kode_kategori,kategori_barang.nama) like ? and concat(databarang.kode_golongan,golongan_barang.nama) like ? and databarang.nama_brng like ? and permintaan_medis.kd_bangsal like ? or "+
                    " permintaan_medis.tanggal between ? and ? and permintaan_medis.status like ? and permintaan_medis.no_permintaan like ? and bangsal.nm_bangsal like ? and pegawai.nama like ?  and concat(databarang.kdjns,jenis.nama) like ? and concat(databarang.kode_kategori,kategori_barang.nama) like ? and concat(databarang.kode_golongan,golongan_barang.nama) like ? and databarang.nama_brng like ? and bangsal.nm_bangsal like ? or "+
                    " permintaan_medis.tanggal between ? and ? and permintaan_medis.status like ? and permintaan_medis.no_permintaan like ? and bangsal.nm_bangsal like ? and pegawai.nama like ?  and concat(databarang.kdjns,jenis.nama) like ? and concat(databarang.kode_kategori,kategori_barang.nama) like ? and concat(databarang.kode_golongan,golongan_barang.nama) like ? and databarang.nama_brng like ? and permintaan_medis.nip like ? or "+
                    " permintaan_medis.tanggal between ? and ? and permintaan_medis.status like ? and permintaan_medis.no_permintaan like ? and bangsal.nm_bangsal like ? and pegawai.nama like ?  and concat(databarang.kdjns,jenis.nama) like ? and concat(databarang.kode_kategori,kategori_barang.nama) like ? and concat(databarang.kode_golongan,golongan_barang.nama) like ? and databarang.nama_brng like ? and pegawai.nama like ? or "+
                    " permintaan_medis.tanggal between ? and ? and permintaan_medis.status like ? and permintaan_medis.no_permintaan like ? and bangsal.nm_bangsal like ? and pegawai.nama like ?  and concat(databarang.kdjns,jenis.nama) like ? and concat(databarang.kode_kategori,kategori_barang.nama) like ? and concat(databarang.kode_golongan,golongan_barang.nama) like ? and databarang.nama_brng like ? and jenis.nama like ? or "+
                    " permintaan_medis.tanggal between ? and ? and permintaan_medis.status like ? and permintaan_medis.no_permintaan like ? and bangsal.nm_bangsal like ? and pegawai.nama like ?  and concat(databarang.kdjns,jenis.nama) like ? and concat(databarang.kode_kategori,kategori_barang.nama) like ? and concat(databarang.kode_golongan,golongan_barang.nama) like ? and databarang.nama_brng like ? and detail_permintaan_medis.kode_brng like ? or "+
                    " permintaan_medis.tanggal between ? and ? and permintaan_medis.status like ? and permintaan_medis.no_permintaan like ? and bangsal.nm_bangsal like ? and pegawai.nama like ?  and concat(databarang.kdjns,jenis.nama) like ? and concat(databarang.kode_kategori,kategori_barang.nama) like ? and concat(databarang.kode_golongan,golongan_barang.nama) like ? and databarang.nama_brng like ? and databarang.nama_brng like ? or "+
                    " permintaan_medis.tanggal between ? and ? and permintaan_medis.status like ? and permintaan_medis.no_permintaan like ? and bangsal.nm_bangsal like ? and pegawai.nama like ?  and concat(databarang.kdjns,jenis.nama) like ? and concat(databarang.kode_kategori,kategori_barang.nama) like ? and concat(databarang.kode_golongan,golongan_barang.nama) like ? and databarang.nama_brng like ? and detail_permintaan_medis.kode_sat like ? or "+
                    " permintaan_medis.tanggal between ? and ? and permintaan_medis.status like ? and permintaan_medis.no_permintaan like ? and bangsal.nm_bangsal like ? and pegawai.nama like ?  and concat(databarang.kdjns,jenis.nama) like ? and concat(databarang.kode_kategori,kategori_barang.nama) like ? and concat(databarang.kode_golongan,golongan_barang.nama) like ? and databarang.nama_brng like ? and tujuan.nm_bangsal like ? or "+
                    " permintaan_medis.tanggal between ? and ? and permintaan_medis.status like ? and permintaan_medis.no_permintaan like ? and bangsal.nm_bangsal like ? and pegawai.nama like ?  and concat(databarang.kdjns,jenis.nama) like ? and concat(databarang.kode_kategori,kategori_barang.nama) like ? and concat(databarang.kode_golongan,golongan_barang.nama) like ? and databarang.nama_brng like ? and kodesatuan.satuan like ? "+
                    " group by permintaan_medis.no_permintaan order by permintaan_medis.tanggal,permintaan_medis.no_permintaan ");
            }
                
            try {
                if(NoPermintaan.getText().equals("")&&NmBangsal.getText().equals("")&&NmPeg.getText().equals("")&&nmjenis.getText().equals("")&&
                    nmkategori.getText().equals("")&&nmgolongan.getText().equals("")&&nmbar.getText().equals("")&&TCari.getText().equals("")&&
                        Status.getSelectedItem().toString().equals("Semua")){
                    ps.setString(1,Valid.SetTgl(Tanggal1.getSelectedItem()+""));
                    ps.setString(2,Valid.SetTgl(Tanggal2.getSelectedItem()+""));
                }else{
                    ps.setString(1,Valid.SetTgl(Tanggal1.getSelectedItem()+""));
                    ps.setString(2,Valid.SetTgl(Tanggal2.getSelectedItem()+""));
                    ps.setString(3,"%"+Status.getSelectedItem().toString().replaceAll("Semua","")+"%");
                    ps.setString(4,"%"+NoPermintaan.getText()+"%");
                    ps.setString(5,"%"+NmBangsal.getText()+"%");
                    ps.setString(6,"%"+NmPeg.getText()+"%");
                    ps.setString(7,"%"+kdjenis.getText()+nmjenis.getText()+"%");
                    ps.setString(8,"%"+kdkategori.getText()+nmkategori.getText()+"%");
                    ps.setString(9,"%"+kdgolongan.getText()+nmgolongan.getText()+"%");
                    ps.setString(10,"%"+nmbar.getText()+"%");
                    ps.setString(11,"%"+TCari.getText()+"%");
                    ps.setString(12,Valid.SetTgl(Tanggal1.getSelectedItem()+""));
                    ps.setString(13,Valid.SetTgl(Tanggal2.getSelectedItem()+""));
                    ps.setString(14,"%"+Status.getSelectedItem().toString().replaceAll("Semua","")+"%");
                    ps.setString(15,"%"+NoPermintaan.getText()+"%");
                    ps.setString(16,"%"+NmBangsal.getText()+"%");
                    ps.setString(17,"%"+NmPeg.getText()+"%");
                    ps.setString(18,"%"+kdjenis.getText()+nmjenis.getText()+"%");
                    ps.setString(19,"%"+kdkategori.getText()+nmkategori.getText()+"%");
                    ps.setString(20,"%"+kdgolongan.getText()+nmgolongan.getText()+"%");
                    ps.setString(21,"%"+nmbar.getText()+"%");
                    ps.setString(22,"%"+TCari.getText()+"%");
                    ps.setString(23,Valid.SetTgl(Tanggal1.getSelectedItem()+""));
                    ps.setString(24,Valid.SetTgl(Tanggal2.getSelectedItem()+""));
                    ps.setString(25,"%"+Status.getSelectedItem().toString().replaceAll("Semua","")+"%");
                    ps.setString(26,"%"+NoPermintaan.getText()+"%");
                    ps.setString(27,"%"+NmBangsal.getText()+"%");
                    ps.setString(28,"%"+NmPeg.getText()+"%");
                    ps.setString(29,"%"+kdjenis.getText()+nmjenis.getText()+"%");
                    ps.setString(30,"%"+kdkategori.getText()+nmkategori.getText()+"%");
                    ps.setString(31,"%"+kdgolongan.getText()+nmgolongan.getText()+"%");
                    ps.setString(32,"%"+nmbar.getText()+"%");
                    ps.setString(33,"%"+TCari.getText()+"%");
                    ps.setString(34,Valid.SetTgl(Tanggal1.getSelectedItem()+""));
                    ps.setString(35,Valid.SetTgl(Tanggal2.getSelectedItem()+""));
                    ps.setString(36,"%"+Status.getSelectedItem().toString().replaceAll("Semua","")+"%");
                    ps.setString(37,"%"+NoPermintaan.getText()+"%");
                    ps.setString(38,"%"+NmBangsal.getText()+"%");
                    ps.setString(39,"%"+NmPeg.getText()+"%");
                    ps.setString(40,"%"+kdjenis.getText()+nmjenis.getText()+"%");
                    ps.setString(41,"%"+kdkategori.getText()+nmkategori.getText()+"%");
                    ps.setString(42,"%"+kdgolongan.getText()+nmgolongan.getText()+"%");
                    ps.setString(43,"%"+nmbar.getText()+"%");
                    ps.setString(44,"%"+TCari.getText()+"%");
                    ps.setString(45,Valid.SetTgl(Tanggal1.getSelectedItem()+""));
                    ps.setString(46,Valid.SetTgl(Tanggal2.getSelectedItem()+""));
                    ps.setString(47,"%"+Status.getSelectedItem().toString().replaceAll("Semua","")+"%");
                    ps.setString(48,"%"+NoPermintaan.getText()+"%");
                    ps.setString(49,"%"+NmBangsal.getText()+"%");
                    ps.setString(50,"%"+NmPeg.getText()+"%");
                    ps.setString(51,"%"+kdjenis.getText()+nmjenis.getText()+"%");
                    ps.setString(52,"%"+kdkategori.getText()+nmkategori.getText()+"%");
                    ps.setString(53,"%"+kdgolongan.getText()+nmgolongan.getText()+"%");
                    ps.setString(54,"%"+nmbar.getText()+"%");
                    ps.setString(55,"%"+TCari.getText()+"%");
                    ps.setString(56,Valid.SetTgl(Tanggal1.getSelectedItem()+""));
                    ps.setString(57,Valid.SetTgl(Tanggal2.getSelectedItem()+""));
                    ps.setString(58,"%"+Status.getSelectedItem().toString().replaceAll("Semua","")+"%");
                    ps.setString(59,"%"+NoPermintaan.getText()+"%");
                    ps.setString(60,"%"+NmBangsal.getText()+"%");
                    ps.setString(61,"%"+NmPeg.getText()+"%");
                    ps.setString(62,"%"+kdjenis.getText()+nmjenis.getText()+"%");
                    ps.setString(63,"%"+kdkategori.getText()+nmkategori.getText()+"%");
                    ps.setString(64,"%"+kdgolongan.getText()+nmgolongan.getText()+"%");
                    ps.setString(65,"%"+nmbar.getText()+"%");
                    ps.setString(66,"%"+TCari.getText()+"%");
                    ps.setString(67,Valid.SetTgl(Tanggal1.getSelectedItem()+""));
                    ps.setString(68,Valid.SetTgl(Tanggal2.getSelectedItem()+""));
                    ps.setString(69,"%"+Status.getSelectedItem().toString().replaceAll("Semua","")+"%");
                    ps.setString(70,"%"+NoPermintaan.getText()+"%");
                    ps.setString(71,"%"+NmBangsal.getText()+"%");
                    ps.setString(72,"%"+NmPeg.getText()+"%");
                    ps.setString(73,"%"+kdjenis.getText()+nmjenis.getText()+"%");
                    ps.setString(74,"%"+kdkategori.getText()+nmkategori.getText()+"%");
                    ps.setString(75,"%"+kdgolongan.getText()+nmgolongan.getText()+"%");
                    ps.setString(76,"%"+nmbar.getText()+"%");
                    ps.setString(77,"%"+TCari.getText()+"%");
                    ps.setString(78,Valid.SetTgl(Tanggal1.getSelectedItem()+""));
                    ps.setString(79,Valid.SetTgl(Tanggal2.getSelectedItem()+""));
                    ps.setString(80,"%"+Status.getSelectedItem().toString().replaceAll("Semua","")+"%");
                    ps.setString(81,"%"+NoPermintaan.getText()+"%");
                    ps.setString(82,"%"+NmBangsal.getText()+"%");
                    ps.setString(83,"%"+NmPeg.getText()+"%");
                    ps.setString(84,"%"+kdjenis.getText()+nmjenis.getText()+"%");
                    ps.setString(85,"%"+kdkategori.getText()+nmkategori.getText()+"%");
                    ps.setString(86,"%"+kdgolongan.getText()+nmgolongan.getText()+"%");
                    ps.setString(87,"%"+nmbar.getText()+"%");
                    ps.setString(88,"%"+TCari.getText()+"%");
                    ps.setString(89,Valid.SetTgl(Tanggal1.getSelectedItem()+""));
                    ps.setString(90,Valid.SetTgl(Tanggal2.getSelectedItem()+""));
                    ps.setString(91,"%"+Status.getSelectedItem().toString().replaceAll("Semua","")+"%");
                    ps.setString(92,"%"+NoPermintaan.getText()+"%");
                    ps.setString(93,"%"+NmBangsal.getText()+"%");
                    ps.setString(94,"%"+NmPeg.getText()+"%");
                    ps.setString(95,"%"+kdjenis.getText()+nmjenis.getText()+"%");
                    ps.setString(96,"%"+kdkategori.getText()+nmkategori.getText()+"%");
                    ps.setString(97,"%"+kdgolongan.getText()+nmgolongan.getText()+"%");
                    ps.setString(98,"%"+nmbar.getText()+"%");
                    ps.setString(99,"%"+TCari.getText()+"%");
                    ps.setString(100,Valid.SetTgl(Tanggal1.getSelectedItem()+""));
                    ps.setString(101,Valid.SetTgl(Tanggal2.getSelectedItem()+""));
                    ps.setString(102,"%"+Status.getSelectedItem().toString().replaceAll("Semua","")+"%");
                    ps.setString(103,"%"+NoPermintaan.getText()+"%");
                    ps.setString(104,"%"+NmBangsal.getText()+"%");
                    ps.setString(105,"%"+NmPeg.getText()+"%");
                    ps.setString(106,"%"+kdjenis.getText()+nmjenis.getText()+"%");
                    ps.setString(107,"%"+kdkategori.getText()+nmkategori.getText()+"%");
                    ps.setString(108,"%"+kdgolongan.getText()+nmgolongan.getText()+"%");
                    ps.setString(109,"%"+nmbar.getText()+"%");
                    ps.setString(110,"%"+TCari.getText()+"%");
                    ps.setString(111,Valid.SetTgl(Tanggal1.getSelectedItem()+""));
                    ps.setString(112,Valid.SetTgl(Tanggal2.getSelectedItem()+""));
                    ps.setString(113,"%"+Status.getSelectedItem().toString().replaceAll("Semua","")+"%");
                    ps.setString(114,"%"+NoPermintaan.getText()+"%");
                    ps.setString(115,"%"+NmBangsal.getText()+"%");
                    ps.setString(116,"%"+NmPeg.getText()+"%");
                    ps.setString(117,"%"+kdjenis.getText()+nmjenis.getText()+"%");
                    ps.setString(118,"%"+kdkategori.getText()+nmkategori.getText()+"%");
                    ps.setString(119,"%"+kdgolongan.getText()+nmgolongan.getText()+"%");
                    ps.setString(120,"%"+nmbar.getText()+"%");
                    ps.setString(121,"%"+TCari.getText()+"%");
                }
                    
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new Object[]{
                        rs.getString("tanggal"),rs.getString("no_permintaan"),rs.getString("asal"),
                        rs.getString("nip")+" "+rs.getString("nama"),rs.getString("tujuan")+" ( "+rs.getString("status")+" )"
                    });  
                    if(nmjenis.getText().equals("")&&nmkategori.getText().equals("")&&nmgolongan.getText().equals("")&&nmbar.getText().equals("")&&TCari.getText().equals("")){
                        ps2=koneksi.prepareStatement(
                                "select detail_permintaan_medis.kode_brng,databarang.nama_brng, "+
                                "detail_permintaan_medis.kode_sat,kodesatuan.satuan,"+
                                "detail_permintaan_medis.jumlah,detail_permintaan_medis.keterangan "+
                                "from detail_permintaan_medis inner join databarang on detail_permintaan_medis.kode_brng=databarang.kode_brng "+
                                "inner join kodesatuan on detail_permintaan_medis.kode_sat=kodesatuan.kode_sat "+
                                "inner join kategori_barang on kategori_barang.kode=databarang.kode_kategori "+
                                "inner join golongan_barang on golongan_barang.kode=databarang.kode_golongan "+
                                "inner join jenis on databarang.kdjns=jenis.kdjns where "+
                                " detail_permintaan_medis.no_permintaan=? order by detail_permintaan_medis.kode_brng  ");
                    }else{
                        ps2=koneksi.prepareStatement(
                                "select detail_permintaan_medis.kode_brng,databarang.nama_brng, "+
                                "detail_permintaan_medis.kode_sat,kodesatuan.satuan,"+
                                "detail_permintaan_medis.jumlah,detail_permintaan_medis.keterangan "+
                                "from detail_permintaan_medis inner join databarang on detail_permintaan_medis.kode_brng=databarang.kode_brng "+
                                "inner join kodesatuan on detail_permintaan_medis.kode_sat=kodesatuan.kode_sat "+
                                "inner join kategori_barang on kategori_barang.kode=databarang.kode_kategori "+
                                "inner join golongan_barang on golongan_barang.kode=databarang.kode_golongan "+
                                "inner join jenis on databarang.kdjns=jenis.kdjns where "+
                                " detail_permintaan_medis.no_permintaan=? and databarang.nama_brng like ? and concat(databarang.kdjns,jenis.nama) like ? and concat(databarang.kode_kategori,kategori_barang.nama) like ? and concat(databarang.kode_golongan,golongan_barang.nama) like ? and detail_permintaan_medis.kode_brng like ? or "+
                                " detail_permintaan_medis.no_permintaan=? and databarang.nama_brng like ? and concat(databarang.kdjns,jenis.nama) like ? and concat(databarang.kode_kategori,kategori_barang.nama) like ? and concat(databarang.kode_golongan,golongan_barang.nama) like ? and databarang.nama_brng like ? or "+
                                " detail_permintaan_medis.no_permintaan=? and databarang.nama_brng like ? and concat(databarang.kdjns,jenis.nama) like ? and concat(databarang.kode_kategori,kategori_barang.nama) like ? and concat(databarang.kode_golongan,golongan_barang.nama) like ? and detail_permintaan_medis.kode_sat like ? or "+
                                " detail_permintaan_medis.no_permintaan=? and databarang.nama_brng like ? and concat(databarang.kdjns,jenis.nama) like ? and concat(databarang.kode_kategori,kategori_barang.nama) like ? and concat(databarang.kode_golongan,golongan_barang.nama) like ? and jenis.nama like ? order by detail_permintaan_medis.kode_brng  ");
                    }
                        
                    try {
                        if(nmjenis.getText().equals("")&&nmkategori.getText().equals("")&&nmgolongan.getText().equals("")&&nmbar.getText().equals("")&&TCari.getText().equals("")){
                            ps2.setString(1,rs.getString(2));
                        }else{
                            ps2.setString(1,rs.getString(2));
                            ps2.setString(2,"%"+nmbar.getText()+"%");
                            ps2.setString(3,"%"+kdjenis.getText()+nmjenis.getText()+"%");
                            ps2.setString(4,"%"+kdkategori.getText()+nmkategori.getText()+"%");
                            ps2.setString(5,"%"+kdgolongan.getText()+nmgolongan.getText()+"%");
                            ps2.setString(6,"%"+TCari.getText()+"%");
                            ps2.setString(7,rs.getString(2));
                            ps2.setString(8,"%"+nmbar.getText()+"%");
                            ps2.setString(9,"%"+kdjenis.getText()+nmjenis.getText()+"%");
                            ps2.setString(10,"%"+kdkategori.getText()+nmkategori.getText()+"%");
                            ps2.setString(11,"%"+kdgolongan.getText()+nmgolongan.getText()+"%");
                            ps2.setString(12,"%"+TCari.getText()+"%");
                            ps2.setString(13,rs.getString(2));
                            ps2.setString(14,"%"+nmbar.getText()+"%");
                            ps2.setString(15,"%"+kdjenis.getText()+nmjenis.getText()+"%");
                            ps2.setString(16,"%"+kdkategori.getText()+nmkategori.getText()+"%");
                            ps2.setString(17,"%"+kdgolongan.getText()+nmgolongan.getText()+"%");
                            ps2.setString(18,"%"+TCari.getText()+"%");
                            ps2.setString(19,rs.getString(2));
                            ps2.setString(20,"%"+nmbar.getText()+"%");
                            ps2.setString(21,"%"+kdjenis.getText()+nmjenis.getText()+"%");
                            ps2.setString(22,"%"+kdkategori.getText()+nmkategori.getText()+"%");
                            ps2.setString(23,"%"+kdgolongan.getText()+nmgolongan.getText()+"%");
                            ps2.setString(24,"%"+TCari.getText()+"%");
                        }
                        rs2=ps2.executeQuery();
                        int no=1;
                        while(rs2.next()){
                            tabMode.addRow(new Object[]{
                                "",no+". "+rs2.getString("kode_brng")+" "+rs2.getString("nama_brng"),
                                rs2.getString("jumlah"),rs2.getString("satuan"),rs2.getString("keterangan")
                            });
                            no++;
                        } 
                    } catch (Exception e) {
                        System.out.println(e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    } 
                    tabMode.addRow(new Object[]{"","","","",""});
                }         
                rs.last();
                LTotal.setText(""+rs.getRow());
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
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }        
    }

    public void emptTeks() {
        kdbar.setText("");
        nmbar.setText("");
        kdbar.requestFocus();        
    }
    
    public void isCek(){
        TCari.requestFocus();
        if(akses.getkode().equals("Admin Utama")){
            ppHapus.setEnabled(true);
        }else{
            ppHapus.setEnabled(false);
        }    
        ppDisetujui.setEnabled(akses.getmutasi_barang());
        ppDisetujui1.setEnabled(akses.getpengeluaran_stok_apotek());
        if((akses.getpengeluaran_stok_apotek()==false)&&(akses.getmutasi_barang()==false)){
            ppTidakDisetujui.setEnabled(false);
        }else{
            ppTidakDisetujui.setEnabled(true);
        }
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,126));
            FormInput.setVisible(true);      
            ChkInput.setVisible(true);
        }else if(ChkInput.isSelected()==false){           
            ChkInput.setVisible(false);            
            PanelInput.setPreferredSize(new Dimension(WIDTH,20));
            FormInput.setVisible(false);      
            ChkInput.setVisible(true);
        }
    }
}

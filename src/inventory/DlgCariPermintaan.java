package inventory;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Toolkit;
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
import keuangan.Jurnal;
import simrskhanza.DlgCariBangsal;
import kepegawaian.DlgCariPegawai;

public class DlgCariPermintaan extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private Dimension screen=Toolkit.getDefaultToolkit().getScreenSize();
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

        Object[] row={"Tanggal","No.Permintaan","Ruangan","Pegawai","Status"};
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
                column.setPreferredWidth(100);
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
        label17 = new widget.Label();
        kdbar = new widget.TextBox();
        nmbar = new widget.TextBox();
        btnBarang = new widget.Button();
        label7 = new widget.Label();
        kdjenis = new widget.TextBox();
        nmjenis = new widget.TextBox();
        btnJenis = new widget.Button();
        panelisi3 = new widget.panelisi();
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

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        ppHapus.setBackground(new java.awt.Color(255, 255, 254));
        ppHapus.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppHapus.setForeground(new java.awt.Color(70, 70, 70));
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
        ppDisetujui.setForeground(new java.awt.Color(70, 70, 70));
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
        ppDisetujui1.setForeground(new java.awt.Color(70, 70, 70));
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
        ppTidakDisetujui.setForeground(new java.awt.Color(70, 70, 70));
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

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Cari Permintaan Obat/Alkes/BHP Medis ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(70, 70, 70))); // NOI18N
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
        tbDokter.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
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
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label10.setText("Key Word :");
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
        panelisi4.setLayout(null);

        label17.setText("Barang :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(65, 23));
        panelisi4.add(label17);
        label17.setBounds(325, 10, 60, 23);

        kdbar.setName("kdbar"); // NOI18N
        kdbar.setPreferredSize(new java.awt.Dimension(80, 23));
        kdbar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdbarKeyPressed(evt);
            }
        });
        panelisi4.add(kdbar);
        kdbar.setBounds(389, 10, 110, 23);

        nmbar.setEditable(false);
        nmbar.setName("nmbar"); // NOI18N
        nmbar.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi4.add(nmbar);
        nmbar.setBounds(501, 10, 230, 23);

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
        panelisi4.add(btnBarang);
        btnBarang.setBounds(734, 10, 28, 23);

        label7.setText("Jenis :");
        label7.setName("label7"); // NOI18N
        panelisi4.add(label7);
        label7.setBounds(0, 10, 42, 23);

        kdjenis.setName("kdjenis"); // NOI18N
        kdjenis.setPreferredSize(new java.awt.Dimension(207, 23));
        kdjenis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdjenisKeyPressed(evt);
            }
        });
        panelisi4.add(kdjenis);
        kdjenis.setBounds(45, 10, 61, 23);

        nmjenis.setEditable(false);
        nmjenis.setName("nmjenis"); // NOI18N
        nmjenis.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi4.add(nmjenis);
        nmjenis.setBounds(108, 10, 180, 23);

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
        btnJenis.setBounds(290, 10, 25, 23);

        jPanel1.add(panelisi4, java.awt.BorderLayout.CENTER);

        internalFrame1.add(jPanel1, java.awt.BorderLayout.PAGE_END);

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(100, 73));
        panelisi3.setLayout(null);

        label15.setText("No.Permintaan :");
        label15.setName("label15"); // NOI18N
        label15.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(label15);
        label15.setBounds(0, 10, 92, 23);

        NoPermintaan.setName("NoPermintaan"); // NOI18N
        NoPermintaan.setPreferredSize(new java.awt.Dimension(207, 23));
        NoPermintaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoPermintaanKeyPressed(evt);
            }
        });
        panelisi3.add(NoPermintaan);
        NoPermintaan.setBounds(95, 10, 207, 23);

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label11);
        label11.setBounds(0, 40, 92, 23);

        Tanggal1.setDisplayFormat("dd-MM-yyyy");
        Tanggal1.setName("Tanggal1"); // NOI18N
        Tanggal1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Tanggal1KeyPressed(evt);
            }
        });
        panelisi3.add(Tanggal1);
        Tanggal1.setBounds(95, 40, 90, 23);

        label16.setText("Ruangan/Depo :");
        label16.setName("label16"); // NOI18N
        label16.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(label16);
        label16.setBounds(305, 10, 110, 23);

        label13.setText("Pegawai :");
        label13.setName("label13"); // NOI18N
        label13.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label13);
        label13.setBounds(305, 40, 110, 23);

        KdBangsal.setName("KdBangsal"); // NOI18N
        KdBangsal.setPreferredSize(new java.awt.Dimension(80, 23));
        KdBangsal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdBangsalKeyPressed(evt);
            }
        });
        panelisi3.add(KdBangsal);
        KdBangsal.setBounds(419, 10, 80, 23);

        KdPeg.setName("KdPeg"); // NOI18N
        KdPeg.setPreferredSize(new java.awt.Dimension(80, 23));
        KdPeg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdPegKeyPressed(evt);
            }
        });
        panelisi3.add(KdPeg);
        KdPeg.setBounds(419, 40, 80, 23);

        NmBangsal.setEditable(false);
        NmBangsal.setName("NmBangsal"); // NOI18N
        NmBangsal.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(NmBangsal);
        NmBangsal.setBounds(501, 10, 230, 23);

        NmPeg.setEditable(false);
        NmPeg.setName("NmPeg"); // NOI18N
        NmPeg.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(NmPeg);
        NmPeg.setBounds(501, 40, 230, 23);

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
        panelisi3.add(btnSuplier);
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
        panelisi3.add(btnPetugas);
        btnPetugas.setBounds(734, 40, 28, 23);

        label12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label12.setText("s.d.");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label12);
        label12.setBounds(187, 40, 27, 23);

        Tanggal2.setDisplayFormat("dd-MM-yyyy");
        Tanggal2.setName("Tanggal2"); // NOI18N
        Tanggal2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Tanggal2KeyPressed(evt);
            }
        });
        panelisi3.add(Tanggal2);
        Tanggal2.setBounds(213, 40, 90, 23);

        internalFrame1.add(panelisi3, java.awt.BorderLayout.PAGE_START);

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
            
            Sequel.queryu("delete from temporary");
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
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.TextBox KdBangsal;
    private widget.TextBox KdPeg;
    private widget.Label LTotal;
    private widget.TextBox NmBangsal;
    private widget.TextBox NmPeg;
    private widget.TextBox NoPermintaan;
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
    private widget.TextBox kdjenis;
    private widget.Label label10;
    private widget.Label label11;
    private widget.Label label12;
    private widget.Label label13;
    private widget.Label label15;
    private widget.Label label16;
    private widget.Label label17;
    private widget.Label label7;
    private widget.Label label9;
    private widget.TextBox nmbar;
    private widget.TextBox nmjenis;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi3;
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
            ps=koneksi.prepareStatement(
                    "select permintaan_medis.tanggal,permintaan_medis.no_permintaan, "+
                    "permintaan_medis.kd_bangsal,bangsal.nm_bangsal, "+
                    "permintaan_medis.nip,pegawai.nama,permintaan_medis.status "+
                    "from permintaan_medis inner join bangsal inner join pegawai "+
                    "inner join kodesatuan inner join detail_permintaan_medis inner join jenis "+
                    "inner join databarang on detail_permintaan_medis.kode_brng=databarang.kode_brng "+
                    " and databarang.kode_sat=kodesatuan.kode_sat "+
                    " and permintaan_medis.no_permintaan=detail_permintaan_medis.no_permintaan "+
                    " and permintaan_medis.kd_bangsal=bangsal.kd_bangsal "+
                    " and permintaan_medis.nip=pegawai.nik "+
                    " and databarang.kdjns=jenis.kdjns "+
                    " where permintaan_medis.tanggal between ? and ? and permintaan_medis.no_permintaan like ? and bangsal.nm_bangsal like ? and pegawai.nama like ?  and jenis.nama like ? and databarang.nama_brng like ? and permintaan_medis.no_permintaan like ? or "+
                    " permintaan_medis.tanggal between ? and ? and permintaan_medis.no_permintaan like ? and bangsal.nm_bangsal like ? and pegawai.nama like ?  and jenis.nama like ? and databarang.nama_brng like ? and permintaan_medis.kd_bangsal like ? or "+
                    " permintaan_medis.tanggal between ? and ? and permintaan_medis.no_permintaan like ? and bangsal.nm_bangsal like ? and pegawai.nama like ?  and jenis.nama like ? and databarang.nama_brng like ? and bangsal.nm_bangsal like ? or "+
                    " permintaan_medis.tanggal between ? and ? and permintaan_medis.no_permintaan like ? and bangsal.nm_bangsal like ? and pegawai.nama like ?  and jenis.nama like ? and databarang.nama_brng like ? and permintaan_medis.nip like ? or "+
                    " permintaan_medis.tanggal between ? and ? and permintaan_medis.no_permintaan like ? and bangsal.nm_bangsal like ? and pegawai.nama like ?  and jenis.nama like ? and databarang.nama_brng like ? and pegawai.nama like ? or "+
                    " permintaan_medis.tanggal between ? and ? and permintaan_medis.no_permintaan like ? and bangsal.nm_bangsal like ? and pegawai.nama like ?  and jenis.nama like ? and databarang.nama_brng like ? and jenis.nama like ? or "+
                    " permintaan_medis.tanggal between ? and ? and permintaan_medis.no_permintaan like ? and bangsal.nm_bangsal like ? and pegawai.nama like ?  and jenis.nama like ? and databarang.nama_brng like ? and detail_permintaan_medis.kode_brng like ? or "+
                    " permintaan_medis.tanggal between ? and ? and permintaan_medis.no_permintaan like ? and bangsal.nm_bangsal like ? and pegawai.nama like ?  and jenis.nama like ? and databarang.nama_brng like ? and databarang.nama_brng like ? or "+
                    " permintaan_medis.tanggal between ? and ? and permintaan_medis.no_permintaan like ? and bangsal.nm_bangsal like ? and pegawai.nama like ?  and jenis.nama like ? and databarang.nama_brng like ? and detail_permintaan_medis.kode_sat like ? or "+
                    " permintaan_medis.tanggal between ? and ? and permintaan_medis.no_permintaan like ? and bangsal.nm_bangsal like ? and pegawai.nama like ?  and jenis.nama like ? and databarang.nama_brng like ? and kodesatuan.satuan like ? "+
                    " group by permintaan_medis.no_permintaan order by permintaan_medis.tanggal,permintaan_medis.no_permintaan ");
            try {
                ps.setString(1,Valid.SetTgl(Tanggal1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(Tanggal2.getSelectedItem()+""));
                ps.setString(3,"%"+NoPermintaan.getText()+"%");
                ps.setString(4,"%"+NmBangsal.getText()+"%");
                ps.setString(5,"%"+NmPeg.getText()+"%");
                ps.setString(6,"%"+kdjenis.getText()+"%");
                ps.setString(7,"%"+nmbar.getText()+"%");
                ps.setString(8,"%"+TCari.getText()+"%");
                ps.setString(9,Valid.SetTgl(Tanggal1.getSelectedItem()+""));
                ps.setString(10,Valid.SetTgl(Tanggal2.getSelectedItem()+""));
                ps.setString(11,"%"+NoPermintaan.getText()+"%");
                ps.setString(12,"%"+NmBangsal.getText()+"%");
                ps.setString(13,"%"+NmPeg.getText()+"%");
                ps.setString(14,"%"+kdjenis.getText()+"%");
                ps.setString(15,"%"+nmbar.getText()+"%");
                ps.setString(16,"%"+TCari.getText()+"%");
                ps.setString(17,Valid.SetTgl(Tanggal1.getSelectedItem()+""));
                ps.setString(18,Valid.SetTgl(Tanggal2.getSelectedItem()+""));
                ps.setString(19,"%"+NoPermintaan.getText()+"%");
                ps.setString(20,"%"+NmBangsal.getText()+"%");
                ps.setString(21,"%"+NmPeg.getText()+"%");
                ps.setString(22,"%"+kdjenis.getText()+"%");
                ps.setString(23,"%"+nmbar.getText()+"%");
                ps.setString(24,"%"+TCari.getText()+"%");
                ps.setString(25,Valid.SetTgl(Tanggal1.getSelectedItem()+""));
                ps.setString(26,Valid.SetTgl(Tanggal2.getSelectedItem()+""));
                ps.setString(27,"%"+NoPermintaan.getText()+"%");
                ps.setString(28,"%"+NmBangsal.getText()+"%");
                ps.setString(29,"%"+NmPeg.getText()+"%");
                ps.setString(30,"%"+kdjenis.getText()+"%");
                ps.setString(31,"%"+nmbar.getText()+"%");
                ps.setString(32,"%"+TCari.getText()+"%");
                ps.setString(33,Valid.SetTgl(Tanggal1.getSelectedItem()+""));
                ps.setString(34,Valid.SetTgl(Tanggal2.getSelectedItem()+""));
                ps.setString(35,"%"+NoPermintaan.getText()+"%");
                ps.setString(36,"%"+NmBangsal.getText()+"%");
                ps.setString(37,"%"+NmPeg.getText()+"%");
                ps.setString(38,"%"+kdjenis.getText()+"%");
                ps.setString(39,"%"+nmbar.getText()+"%");
                ps.setString(40,"%"+TCari.getText()+"%");
                ps.setString(41,Valid.SetTgl(Tanggal1.getSelectedItem()+""));
                ps.setString(42,Valid.SetTgl(Tanggal2.getSelectedItem()+""));
                ps.setString(43,"%"+NoPermintaan.getText()+"%");
                ps.setString(44,"%"+NmBangsal.getText()+"%");
                ps.setString(45,"%"+NmPeg.getText()+"%");
                ps.setString(46,"%"+kdjenis.getText()+"%");
                ps.setString(47,"%"+nmbar.getText()+"%");
                ps.setString(48,"%"+TCari.getText()+"%");
                ps.setString(49,Valid.SetTgl(Tanggal1.getSelectedItem()+""));
                ps.setString(50,Valid.SetTgl(Tanggal2.getSelectedItem()+""));
                ps.setString(51,"%"+NoPermintaan.getText()+"%");
                ps.setString(52,"%"+NmBangsal.getText()+"%");
                ps.setString(53,"%"+NmPeg.getText()+"%");
                ps.setString(54,"%"+kdjenis.getText()+"%");
                ps.setString(55,"%"+nmbar.getText()+"%");
                ps.setString(56,"%"+TCari.getText()+"%");
                ps.setString(57,Valid.SetTgl(Tanggal1.getSelectedItem()+""));
                ps.setString(58,Valid.SetTgl(Tanggal2.getSelectedItem()+""));
                ps.setString(59,"%"+NoPermintaan.getText()+"%");
                ps.setString(60,"%"+NmBangsal.getText()+"%");
                ps.setString(61,"%"+NmPeg.getText()+"%");
                ps.setString(62,"%"+kdjenis.getText()+"%");
                ps.setString(63,"%"+nmbar.getText()+"%");
                ps.setString(64,"%"+TCari.getText()+"%");
                ps.setString(65,Valid.SetTgl(Tanggal1.getSelectedItem()+""));
                ps.setString(66,Valid.SetTgl(Tanggal2.getSelectedItem()+""));
                ps.setString(67,"%"+NoPermintaan.getText()+"%");
                ps.setString(68,"%"+NmBangsal.getText()+"%");
                ps.setString(69,"%"+NmPeg.getText()+"%");
                ps.setString(70,"%"+kdjenis.getText()+"%");
                ps.setString(71,"%"+nmbar.getText()+"%");
                ps.setString(72,"%"+TCari.getText()+"%");
                ps.setString(73,Valid.SetTgl(Tanggal1.getSelectedItem()+""));
                ps.setString(74,Valid.SetTgl(Tanggal2.getSelectedItem()+""));
                ps.setString(75,"%"+NoPermintaan.getText()+"%");
                ps.setString(76,"%"+NmBangsal.getText()+"%");
                ps.setString(77,"%"+NmPeg.getText()+"%");
                ps.setString(78,"%"+kdjenis.getText()+"%");
                ps.setString(79,"%"+nmbar.getText()+"%");
                ps.setString(80,"%"+TCari.getText()+"%");
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new Object[]{
                        rs.getString("tanggal"),rs.getString("no_permintaan"),rs.getString("nm_bangsal"),
                        rs.getString("nip")+" "+rs.getString("nama"),rs.getString("status")
                    });   
                    ps2=koneksi.prepareStatement(
                                "select detail_permintaan_medis.kode_brng,databarang.nama_brng, "+
                                "detail_permintaan_medis.kode_sat,kodesatuan.satuan,"+
                                "detail_permintaan_medis.jumlah,detail_permintaan_medis.keterangan "+
                                "from detail_permintaan_medis inner join databarang inner join kodesatuan inner join jenis "+
                                " on detail_permintaan_medis.kode_brng=databarang.kode_brng "+
                                " and detail_permintaan_medis.kode_sat=kodesatuan.kode_sat "+
                                " and databarang.kdjns=jenis.kdjns where "+
                                " detail_permintaan_medis.no_permintaan=? and databarang.nama_brng like ? and jenis.nama like ? and detail_permintaan_medis.kode_brng like ? or "+
                                " detail_permintaan_medis.no_permintaan=? and databarang.nama_brng like ? and jenis.nama like ? and databarang.nama_brng like ? or "+
                                " detail_permintaan_medis.no_permintaan=? and databarang.nama_brng like ? and jenis.nama like ? and detail_permintaan_medis.kode_sat like ? or "+
                                " detail_permintaan_medis.no_permintaan=? and databarang.nama_brng like ? and jenis.nama like ? and jenis.nama like ? order by detail_permintaan_medis.kode_brng  ");
                    try {
                        ps2.setString(1,rs.getString(2));
                        ps2.setString(2,"%"+nmbar.getText()+"%");
                        ps2.setString(3,"%"+kdjenis.getText()+"%");
                        ps2.setString(4,"%"+TCari.getText()+"%");
                        ps2.setString(5,rs.getString(2));
                        ps2.setString(6,"%"+nmbar.getText()+"%");
                        ps2.setString(7,"%"+kdjenis.getText()+"%");
                        ps2.setString(8,"%"+TCari.getText()+"%");
                        ps2.setString(9,rs.getString(2));
                        ps2.setString(10,"%"+nmbar.getText()+"%");
                        ps2.setString(11,"%"+kdjenis.getText()+"%");
                        ps2.setString(12,"%"+TCari.getText()+"%");
                        ps2.setString(13,rs.getString(2));
                        ps2.setString(14,"%"+nmbar.getText()+"%");
                        ps2.setString(15,"%"+kdjenis.getText()+"%");
                        ps2.setString(16,"%"+TCari.getText()+"%");
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
}

package inventory;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import inventaris.InventarisAsalHibah;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import keuangan.Jurnal;
import kepegawaian.DlgCariPetugas;

public class InventoryCariHibahObatBHP extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    public  InventarisAsalHibah suplier=new InventarisAsalHibah(null,false);
    public  DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    public  DlgBarang barang=new DlgBarang(null,false);
    private PreparedStatement ps,ps2,pscaribeli,psdetailhibah_obat_bhp;
    private ResultSet rs,rs2;
    private double tagihan=0;
    private Jurnal jur=new Jurnal();
    private riwayatobat Trackobat=new riwayatobat();
    private String aktifkanbatch="no";
    private boolean sukses=true;
    private int i=0;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private volatile boolean ceksukses = false;
   
    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public InventoryCariHibahObatBHP(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        try {
            aktifkanbatch = koneksiDB.AKTIFKANBATCHOBAT();
        } catch (Exception e) {
            System.out.println("E : "+e);
            aktifkanbatch = "no";
        }

        Object[] row={"Tgl.Hibah","No.Hibah","Asal Hibah","Petugas","Barang",
                    "Satuan","Jml","Nilai Hibah(Rp)","Subttl Hibah(Rp)",
                    "Nilai Diakui(Rp)","Subttl Diakui(Rp)"};
        tabMode=new DefaultTableModel(null,row){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbDokter.setModel(tabMode);

        tbDokter.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 11; i++) {
            TableColumn column = tbDokter.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(70);
            }else if(i==1){
                column.setPreferredWidth(100);
            }else if(i==2){
                column.setPreferredWidth(150);
            }else if(i==3){
                column.setPreferredWidth(180);
            }else if(i==4){
                column.setPreferredWidth(230);
            }else if(i==5){
                column.setPreferredWidth(70);
            }else if(i==6){
                column.setPreferredWidth(27);
            }else if(i==7){
                column.setPreferredWidth(85);
            }else if(i==8){
                column.setPreferredWidth(100);
            }else if(i==9){
                column.setPreferredWidth(85);
            }else if(i==10){
                column.setPreferredWidth(100);
            }
        }
        tbDokter.setDefaultRenderer(Object.class, new WarnaTable());

        NoFaktur.setDocument(new batasInput((byte)25).getKata(NoFaktur));
        kdsup.setDocument(new batasInput((byte)5).getKata(kdsup));
        kdptg.setDocument(new batasInput((byte)25).getKata(kdptg));
        kdbar.setDocument(new batasInput((byte)15).getKata(kdbar));
        kdjenis.setDocument(new batasInput((byte)3).getKata(kdjenis));
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));    
        
        suplier.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("CariHibahObatBHP")){
                    if(suplier.getTable().getSelectedRow()!= -1){                   
                        kdsup.setText(suplier.getTable().getValueAt(suplier.getTable().getSelectedRow(),0).toString());                    
                        nmsup.setText(suplier.getTable().getValueAt(suplier.getTable().getSelectedRow(),1).toString());
                    }  
                    kdsup.requestFocus();
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
        
        suplier.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(akses.getform().equals("CariHibahObatBHP")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        suplier.dispose();
                    }     
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });    
        
        petugas.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("CariHibahObatBHP")){
                    if(petugas.getTable().getSelectedRow()!= -1){                   
                        kdptg.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                        nmptg.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                    }            
                    kdptg.requestFocus();
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
        
        barang.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("CariHibahObatBHP")){
                    if(barang.getTable().getSelectedRow()!= -1){                   
                        kdbar.setText(barang.getTable().getValueAt(barang.getTable().getSelectedRow(),1).toString());                    
                        nmbar.setText(barang.getTable().getValueAt(barang.getTable().getSelectedRow(),2).toString());                                        
                    }   
                    kdbar.requestFocus();
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
        
        barang.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(akses.getform().equals("CariHibahObatBHP")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        barang.dispose();
                    }      
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
                        runBackground(() ->tampil());
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        runBackground(() ->tampil());
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        runBackground(() ->tampil());
                    }
                }
            });
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

        jPopupMenu1 = new javax.swing.JPopupMenu();
        ppHapus = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        scrollPane1 = new widget.ScrollPane();
        tbDokter = new widget.Table();
        jPanel1 = new javax.swing.JPanel();
        panelisi1 = new widget.panelisi();
        label10 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        label9 = new widget.Label();
        LTotal = new widget.Label();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();
        panelisi4 = new widget.panelisi();
        label17 = new widget.Label();
        kdbar = new widget.TextBox();
        nmbar = new widget.TextBox();
        btnBarang = new widget.Button();
        label24 = new widget.Label();
        kdjenis = new widget.TextBox();
        btnSatuan = new widget.Button();
        nmjenis = new widget.TextBox();
        panelisi3 = new widget.panelisi();
        label15 = new widget.Label();
        NoFaktur = new widget.TextBox();
        label11 = new widget.Label();
        TglBeli1 = new widget.Tanggal();
        label16 = new widget.Label();
        label13 = new widget.Label();
        kdsup = new widget.TextBox();
        kdptg = new widget.TextBox();
        nmsup = new widget.TextBox();
        nmptg = new widget.TextBox();
        btnSuplier = new widget.Button();
        btnPetugas = new widget.Button();
        label12 = new widget.Label();
        TglBeli2 = new widget.Tanggal();
        label14 = new widget.Label();
        KdIF = new widget.TextBox();
        NmIF = new widget.TextBox();
        btnIF = new widget.Button();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        ppHapus.setBackground(new java.awt.Color(255, 255, 254));
        ppHapus.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppHapus.setForeground(new java.awt.Color(50, 50, 50));
        ppHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppHapus.setText("Hapus Hibah");
        ppHapus.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppHapus.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppHapus.setName("ppHapus"); // NOI18N
        ppHapus.setPreferredSize(new java.awt.Dimension(150, 25));
        ppHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppHapusActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppHapus);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Cari Hibah Obat, Alkes & BHP Medis ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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
        label10.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi1.add(label10);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(170, 23));
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

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('M');
        BtnAll.setToolTipText("Alt+M");
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

        label9.setText("Total Nilai Diakui :");
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(105, 30));
        panelisi1.add(label9);

        LTotal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LTotal.setText("0");
        LTotal.setName("LTotal"); // NOI18N
        LTotal.setPreferredSize(new java.awt.Dimension(125, 30));
        panelisi1.add(LTotal);

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
        label17.setBounds(295, 10, 90, 23);

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

        label24.setText("Jenis :");
        label24.setName("label24"); // NOI18N
        label24.setPreferredSize(new java.awt.Dimension(68, 23));
        panelisi4.add(label24);
        label24.setBounds(0, 10, 75, 23);

        kdjenis.setName("kdjenis"); // NOI18N
        kdjenis.setPreferredSize(new java.awt.Dimension(80, 23));
        kdjenis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdjenisKeyPressed(evt);
            }
        });
        panelisi4.add(kdjenis);
        kdjenis.setBounds(80, 10, 53, 23);

        btnSatuan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnSatuan.setMnemonic('3');
        btnSatuan.setToolTipText("Alt+3");
        btnSatuan.setName("btnSatuan"); // NOI18N
        btnSatuan.setPreferredSize(new java.awt.Dimension(28, 23));
        btnSatuan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSatuanActionPerformed(evt);
            }
        });
        panelisi4.add(btnSatuan);
        btnSatuan.setBounds(253, 10, 28, 23);

        nmjenis.setName("nmjenis"); // NOI18N
        nmjenis.setPreferredSize(new java.awt.Dimension(80, 23));
        nmjenis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nmjenisKeyPressed(evt);
            }
        });
        panelisi4.add(nmjenis);
        nmjenis.setBounds(135, 10, 115, 23);

        jPanel1.add(panelisi4, java.awt.BorderLayout.CENTER);

        internalFrame1.add(jPanel1, java.awt.BorderLayout.PAGE_END);

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(100, 103));
        panelisi3.setLayout(null);

        label15.setText("No.Hibah :");
        label15.setName("label15"); // NOI18N
        label15.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(label15);
        label15.setBounds(0, 10, 70, 23);

        NoFaktur.setName("NoFaktur"); // NOI18N
        NoFaktur.setPreferredSize(new java.awt.Dimension(207, 23));
        NoFaktur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoFakturKeyPressed(evt);
            }
        });
        panelisi3.add(NoFaktur);
        NoFaktur.setBounds(74, 10, 207, 23);

        label11.setText("Tgl.Hibah :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label11);
        label11.setBounds(0, 40, 70, 23);

        TglBeli1.setDisplayFormat("dd-MM-yyyy");
        TglBeli1.setName("TglBeli1"); // NOI18N
        TglBeli1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglBeli1KeyPressed(evt);
            }
        });
        panelisi3.add(TglBeli1);
        TglBeli1.setBounds(74, 40, 90, 23);

        label16.setText("Asal Hibah :");
        label16.setName("label16"); // NOI18N
        label16.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(label16);
        label16.setBounds(305, 10, 80, 23);

        label13.setText("Petugas :");
        label13.setName("label13"); // NOI18N
        label13.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label13);
        label13.setBounds(305, 40, 80, 23);

        kdsup.setName("kdsup"); // NOI18N
        kdsup.setPreferredSize(new java.awt.Dimension(80, 23));
        kdsup.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdsupKeyPressed(evt);
            }
        });
        panelisi3.add(kdsup);
        kdsup.setBounds(389, 10, 80, 23);

        kdptg.setName("kdptg"); // NOI18N
        kdptg.setPreferredSize(new java.awt.Dimension(80, 23));
        kdptg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdptgKeyPressed(evt);
            }
        });
        panelisi3.add(kdptg);
        kdptg.setBounds(389, 40, 80, 23);

        nmsup.setEditable(false);
        nmsup.setName("nmsup"); // NOI18N
        nmsup.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(nmsup);
        nmsup.setBounds(471, 10, 260, 23);

        nmptg.setEditable(false);
        nmptg.setName("nmptg"); // NOI18N
        nmptg.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(nmptg);
        nmptg.setBounds(471, 40, 260, 23);

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
        label12.setBounds(164, 40, 27, 23);

        TglBeli2.setDisplayFormat("dd-MM-yyyy");
        TglBeli2.setName("TglBeli2"); // NOI18N
        TglBeli2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglBeli2KeyPressed(evt);
            }
        });
        panelisi3.add(TglBeli2);
        TglBeli2.setBounds(191, 40, 90, 23);

        label14.setText("Ind. Farmasi :");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label14);
        label14.setBounds(265, 70, 120, 23);

        KdIF.setName("KdIF"); // NOI18N
        KdIF.setPreferredSize(new java.awt.Dimension(80, 23));
        KdIF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdIFKeyPressed(evt);
            }
        });
        panelisi3.add(KdIF);
        KdIF.setBounds(389, 70, 80, 23);

        NmIF.setEditable(false);
        NmIF.setName("NmIF"); // NOI18N
        NmIF.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(NmIF);
        NmIF.setBounds(471, 70, 260, 23);

        btnIF.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnIF.setMnemonic('2');
        btnIF.setToolTipText("Alt+2");
        btnIF.setName("btnIF"); // NOI18N
        btnIF.setPreferredSize(new java.awt.Dimension(28, 23));
        btnIF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIFActionPerformed(evt);
            }
        });
        panelisi3.add(btnIF);
        btnIF.setBounds(734, 70, 28, 23);

        internalFrame1.add(panelisi3, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
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
        akses.setform("CariHibahObatBHP");
        suplier.emptTeks();
        suplier.isCek();
        suplier.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        suplier.setLocationRelativeTo(internalFrame1);
        suplier.setAlwaysOnTop(false);
        suplier.setVisible(true);
    }//GEN-LAST:event_btnSuplierActionPerformed

    private void btnPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPetugasActionPerformed
        akses.setform("CariHibahObatBHP");
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setAlwaysOnTop(false);
        petugas.setVisible(true);
    }//GEN-LAST:event_btnPetugasActionPerformed

    private void TglBeli1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglBeli1KeyPressed
        Valid.pindah(evt,NoFaktur,kdsup);
    }//GEN-LAST:event_TglBeli1KeyPressed

    private void btnBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBarangActionPerformed
        akses.setform("CariHibahObatBHP");
        barang.isCek();
        barang.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        barang.setLocationRelativeTo(internalFrame1);
        barang.setAlwaysOnTop(false);
        barang.setVisible(true);
    }//GEN-LAST:event_btnBarangActionPerformed

    private void btnSatuanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSatuanActionPerformed
        DlgCariJenis jenis = new DlgCariJenis(null, false);
        jenis.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}

            @Override
            public void windowClosed(WindowEvent e) {
                if (jenis.getTable().getSelectedRow() != -1) {
                    kdjenis.setText(jenis.getTable().getValueAt(jenis.getTable().getSelectedRow(), 0).toString());
                    nmjenis.setText(jenis.getTable().getValueAt(jenis.getTable().getSelectedRow(), 1).toString());
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
        jenis.isCek();
        jenis.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        jenis.setLocationRelativeTo(internalFrame1);
        jenis.setAlwaysOnTop(false);
        jenis.setVisible(true);
    }//GEN-LAST:event_btnSatuanActionPerformed

    private void kdsupKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdsupKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama_pemberi from pemberihibah where kode_pemberi=?", nmsup,kdsup.getText());            
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select nama_pemberi from pemberihibah where kode_pemberi=?", nmsup,kdsup.getText());
            NoFaktur.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select nama_pemberi from pemberihibah where kode_pemberi=?", nmsup,kdsup.getText());
            kdptg.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnSuplierActionPerformed(null);
        }
    }//GEN-LAST:event_kdsupKeyPressed

    private void NoFakturKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoFakturKeyPressed
        Valid.pindah(evt, BtnKeluar, kdsup);
    }//GEN-LAST:event_NoFakturKeyPressed

    private void kdptgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdptgKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            nmptg.setText(petugas.tampil3(kdptg.getText()));     
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            nmptg.setText(petugas.tampil3(kdptg.getText()));
            kdsup.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            nmptg.setText(petugas.tampil3(kdptg.getText()));
            KdIF.requestFocus();       
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnPetugasActionPerformed(null);
        }
    }//GEN-LAST:event_kdptgKeyPressed

    private void kdbarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdbarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama_brng from databarang where kode_brng=?", nmbar,kdbar.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){            
            Sequel.cariIsi("select nama_brng from databarang where kode_brng=?", nmbar,kdbar.getText());
            kdjenis.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){            
            Sequel.cariIsi("select nama_brng from databarang where kode_brng=?", nmbar,kdbar.getText());
            TCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnBarangActionPerformed(null);
        }
    }//GEN-LAST:event_kdbarKeyPressed

    private void kdjenisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdjenisKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select kodesatuan.satuan from kodesatuan where kodesatuan.kode_sat=?", nmjenis,kdjenis.getText());         
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select kodesatuan.satuan from kodesatuan where kodesatuan.kode_sat=?", nmjenis,kdjenis.getText());
            kdptg.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select kodesatuan.satuan from kodesatuan where kodesatuan.kode_sat=?", nmjenis,kdjenis.getText());
            kdbar.requestFocus();   
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnSatuanActionPerformed(null);
        }
    }//GEN-LAST:event_kdjenisKeyPressed

    private void TglBeli2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglBeli2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglBeli2KeyPressed

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
        runBackground(() ->tampil());
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
        NoFaktur.setText("");
        kdbar.setText("");
        nmbar.setText("");
        kdjenis.setText("");
        nmjenis.setText("");
        kdsup.setText("");
        nmsup.setText("");
        kdptg.setText("");
        nmptg.setText("");
        KdIF.setText("");
        NmIF.setText("");
        runBackground(() ->tampil());
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
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            TCari.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            Sequel.queryu("delete from temporary where temp37='"+akses.getalamatip()+"'");
            int row=tabMode.getRowCount();
            for(i=0;i<row;i++){  
                Sequel.menyimpan("temporary","'"+i+"','"+
                                tabMode.getValueAt(i,0).toString()+"','"+
                                tabMode.getValueAt(i,1).toString()+"','"+
                                tabMode.getValueAt(i,2).toString()+"','"+
                                tabMode.getValueAt(i,3).toString()+"','"+
                                tabMode.getValueAt(i,4).toString()+"','"+
                                tabMode.getValueAt(i,5).toString()+"','"+
                                tabMode.getValueAt(i,6).toString()+"','"+
                                tabMode.getValueAt(i,7).toString()+"','"+
                                tabMode.getValueAt(i,8).toString()+"','"+
                                tabMode.getValueAt(i,9).toString()+"','"+
                                tabMode.getValueAt(i,10).toString()+"','','','','','','','','','','','','','','','','','','','','','','','','','','"+akses.getalamatip()+"'","Transaksi Hibah Obat & BHP"); 
            }
            i++;
            Sequel.menyimpan("temporary","'"+i+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','"+akses.getalamatip()+"'","Transaksi Hibah Obat & BHP"); 
            i++;
            Sequel.menyimpan("temporary","'"+i+"','','Jumlah Total',':','','','','',' ','','','"+LTotal.getText()+"','','','','','','','','','','','','','','','','','','','','','','','','','','"+akses.getalamatip()+"'","Transaksi Pembelian"); 
            
            Map<String, Object> param = new HashMap<>();    
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("emailrs",akses.getemailrs());   
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptHibahObatBHP.jasper","report","::[ Transaksi Hibah Obat, Alkes & BHP Medis ]::","select * from temporary where temporary.temp37='"+akses.getalamatip()+"' order by temporary.no",param);
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

    private void nmjenisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nmjenisKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_nmjenisKeyPressed

private void ppHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppHapusActionPerformed
    if(tabMode.getRowCount()==0){
        JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
        TCari.requestFocus();
    }else if(tbDokter.getSelectedRow()<= -1){
        JOptionPane.showMessageDialog(null,"Maaf, Silahkan pilih data..!!");
    }else{
        if(tbDokter.getValueAt(tbDokter.getSelectedRow(),1).toString().trim().equals("")){
            Valid.textKosong(TCari,"pilihan data");
        }else{
           try {
               pscaribeli=koneksi.prepareStatement("select no_hibah, totalnilai, kd_bangsal,tgl_hibah from hibah_obat_bhp where no_hibah=?");
               try {
                  pscaribeli.setString(1,tbDokter.getValueAt(tbDokter.getSelectedRow(),1).toString());
                  rs=pscaribeli.executeQuery();
                  if(rs.next()){
                      Sequel.AutoComitFalse();
                      sukses=true;
                      psdetailhibah_obat_bhp=koneksi.prepareStatement("select kode_brng,jumlah2,no_batch,no_hibah from detailhibah_obat_bhp where no_hibah=? ");
                      try {            
                          psdetailhibah_obat_bhp.setString(1,rs.getString(1));
                          rs2=psdetailhibah_obat_bhp.executeQuery();
                          while(rs2.next()){
                              if(aktifkanbatch.equals("yes")){
                                  Trackobat.catatRiwayat(rs2.getString("kode_brng"),0,rs2.getDouble("jumlah2"),"Hibah",akses.getkode(),rs.getString("kd_bangsal"),"Hapus",rs2.getString("no_batch"),rs2.getString("no_hibah"),rs.getString("no_hibah"));
                                  Sequel.menyimpan("gudangbarang","'"+rs2.getString("kode_brng") +"','"+rs.getString("kd_bangsal") +"','-"+rs2.getString("jumlah2")+"','"+rs2.getString("no_batch")+"','"+rs2.getString("no_hibah")+"'", 
                                                         "stok=stok-'"+rs2.getString("jumlah2") +"'","kode_brng='"+rs2.getString("kode_brng")+"' and kd_bangsal='"+rs.getString("kd_bangsal") +"' and no_batch='"+rs2.getString("no_batch")+"' and no_faktur='"+rs2.getString("no_hibah")+"'");
                                  Sequel.queryu3("delete from data_batch where kode_brng=? and no_batch=? and no_faktur=?",3,new String[]{
                                      rs2.getString("kode_brng"),rs2.getString("no_batch"),rs2.getString("no_hibah")
                                  });
                              }else{
                                  Trackobat.catatRiwayat(rs2.getString("kode_brng"),0,rs2.getDouble("jumlah2"),"Hibah",akses.getkode(),rs.getString("kd_bangsal"),"Hapus","","",rs.getString("no_hibah"));
                                  Sequel.menyimpan("gudangbarang","'"+rs2.getString("kode_brng") +"','"+rs.getString("kd_bangsal") +"','-"+rs2.getString("jumlah2")+"','',''", 
                                                         "stok=stok-'"+rs2.getString("jumlah2") +"'","kode_brng='"+rs2.getString("kode_brng")+"' and kd_bangsal='"+rs.getString("kd_bangsal") +"' and no_batch='' and no_faktur=''");
                                  Sequel.queryu3("delete from data_batch where kode_brng=? and no_batch=? and no_faktur=?",3,new String[]{
                                      rs2.getString("kode_brng"),"",""
                                  });
                              } 
                          }
                          sukses=true;
                      } catch (Exception e) {
                          sukses=false;
                          System.out.println(e);
                      } finally{
                          if(rs2!=null){
                              rs2.close();
                          }
                          if(psdetailhibah_obat_bhp!=null){
                              psdetailhibah_obat_bhp.close();
                          }                    
                      }     

                      if(sukses==true){
                          Sequel.queryu("delete from tampjurnal");
                          if(Sequel.menyimpantf2("tampjurnal","?,?,?,?","Rekening",4,new String[]{
                              Sequel.cariIsi("select Hibah_Obat from set_akun"),"PERSEDIAAN HIBAH OBAT & BHP","0",rs.getString("totalnilai")
                          })==false){
                              sukses=false;
                          }    
                          if(Sequel.menyimpantf2("tampjurnal","?,?,?,?","Rekening",4,new String[]{
                              Sequel.cariIsi("select Kontra_Hibah_Obat from set_akun"),"PENDAPATAN HIBAH",rs.getString("totalnilai"),"0"
                          })==false){
                              sukses=false;
                          } 
                          if(sukses==true){
                              sukses=jur.simpanJurnal(rs.getString("no_hibah"),"U","BATAL HIBAH OBAT & BHP DI "+Sequel.cariIsi("select bangsal.nm_bangsal from bangsal where bangsal.kd_bangsal=?",rs.getString("kd_bangsal")).toUpperCase()+", OLEH "+akses.getkode());   
                          }
                      }

                      if(sukses==true){
                          Sequel.queryu2("delete from hibah_obat_bhp where no_hibah=?",1,new String[]{tbDokter.getValueAt(tbDokter.getSelectedRow(),1).toString()});
                          Sequel.Commit();
                      }else{
                          sukses=false;
                          JOptionPane.showMessageDialog(null,"Terjadi kesalahan saat pemrosesan data, transaksi dibatalkan.\nPeriksa kembali data sebelum melanjutkan menyimpan..!!");
                          Sequel.RollBack();
                      }
                      Sequel.AutoComitTrue();
                  }   

                  if(sukses==true){
                      runBackground(() ->tampil());
                  }

               } catch (Exception e) {
                   System.out.println("Notifikasi : "+e);
               } finally{
                   if(rs!=null){
                       rs.close();
                   }
                   if(pscaribeli!=null){
                       pscaribeli.close();
                   }
               }            
           } catch (SQLException ex) {
               System.out.println(ex);
           }
        }   
    }    
}//GEN-LAST:event_ppHapusActionPerformed

    private void KdIFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdIFKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama_industri from industrifarmasi where nip=?",NmIF,KdIF.getText());     
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select nama_industri from industrifarmasi where nip=?",NmIF,KdIF.getText());
            kdptg.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select nama_industri from industrifarmasi where nip=?",NmIF,KdIF.getText());
            kdbar.requestFocus();       
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnIFActionPerformed(null);
        }
    }//GEN-LAST:event_KdIFKeyPressed

    private void btnIFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIFActionPerformed
        DlgCariIndustriFarmasi industri=new DlgCariIndustriFarmasi(null,false);
        industri.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(industri.getTable().getSelectedRow()!= -1){                   
                    KdIF.setText(industri.getTable().getValueAt(industri.getTable().getSelectedRow(),0).toString());                    
                    NmIF.setText(industri.getTable().getValueAt(industri.getTable().getSelectedRow(),1).toString());
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
        
        industri.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    industri.dispose();
                } 
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        }); 
        industri.isCek();
        industri.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        industri.setLocationRelativeTo(internalFrame1);
        industri.setAlwaysOnTop(false);
        industri.setVisible(true);
    }//GEN-LAST:event_btnIFActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            InventoryCariHibahObatBHP dialog = new InventoryCariHibahObatBHP(new javax.swing.JFrame(), true);
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
    private widget.TextBox KdIF;
    private widget.Label LTotal;
    private widget.TextBox NmIF;
    private widget.TextBox NoFaktur;
    private widget.TextBox TCari;
    private widget.Tanggal TglBeli1;
    private widget.Tanggal TglBeli2;
    private widget.Button btnBarang;
    private widget.Button btnIF;
    private widget.Button btnPetugas;
    private widget.Button btnSatuan;
    private widget.Button btnSuplier;
    private widget.InternalFrame internalFrame1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.TextBox kdbar;
    private widget.TextBox kdjenis;
    private widget.TextBox kdptg;
    private widget.TextBox kdsup;
    private widget.Label label10;
    private widget.Label label11;
    private widget.Label label12;
    private widget.Label label13;
    private widget.Label label14;
    private widget.Label label15;
    private widget.Label label16;
    private widget.Label label17;
    private widget.Label label24;
    private widget.Label label9;
    private widget.TextBox nmbar;
    private widget.TextBox nmjenis;
    private widget.TextBox nmptg;
    private widget.TextBox nmsup;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi3;
    private widget.panelisi panelisi4;
    private javax.swing.JMenuItem ppHapus;
    private widget.ScrollPane scrollPane1;
    private widget.Table tbDokter;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
       Valid.tabelKosong(tabMode);
        try{     
            ps=koneksi.prepareStatement(
                "select hibah_obat_bhp.tgl_hibah,hibah_obat_bhp.no_hibah,hibah_obat_bhp.kode_pemberi,pemberihibah.nama_pemberi, "+
                "hibah_obat_bhp.nip,petugas.nama,bangsal.nm_bangsal,hibah_obat_bhp.totalhibah,hibah_obat_bhp.totalnilai "+
                "from hibah_obat_bhp inner join pemberihibah on hibah_obat_bhp.kode_pemberi=pemberihibah.kode_pemberi "+
                "inner join petugas on hibah_obat_bhp.nip=petugas.nip inner join bangsal on hibah_obat_bhp.kd_bangsal=bangsal.kd_bangsal "+
                "inner join detailhibah_obat_bhp on hibah_obat_bhp.no_hibah=detailhibah_obat_bhp.no_hibah "+
                "inner join databarang on detailhibah_obat_bhp.kode_brng=databarang.kode_brng inner join kodesatuan on detailhibah_obat_bhp.kode_sat=kodesatuan.kode_sat "+
                "inner join jenis on databarang.kdjns=jenis.kdjns inner join industrifarmasi on databarang.kode_industri=industrifarmasi.kode_industri "+
                "where hibah_obat_bhp.tgl_hibah between ? and ? and hibah_obat_bhp.no_hibah like ? and pemberihibah.nama_pemberi like ? and petugas.nama like ?  and jenis.nama like ? and databarang.nama_brng like ? and industrifarmasi.nama_industri like ? "+
                (TCari.getText().trim().equals("")?"":"and (hibah_obat_bhp.no_hibah like ? or hibah_obat_bhp.kode_pemberi like ? or pemberihibah.nama_pemberi like ? or hibah_obat_bhp.nip like ? or petugas.nama like ? or bangsal.nm_bangsal like ? or "+
                "detailhibah_obat_bhp.kode_brng like ? or databarang.nama_brng like ? or detailhibah_obat_bhp.kode_sat like ? or detailhibah_obat_bhp.no_batch like ? or industrifarmasi.nama_industri like ? or jenis.nama like ?) ")+
                "group by hibah_obat_bhp.no_hibah order by hibah_obat_bhp.tgl_hibah,hibah_obat_bhp.no_hibah "
            );
            try {
                ps.setString(1,Valid.SetTgl(TglBeli1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(TglBeli2.getSelectedItem()+""));
                ps.setString(3,"%"+NoFaktur.getText()+"%");
                ps.setString(4,"%"+nmsup.getText()+"%");
                ps.setString(5,"%"+nmptg.getText()+"%");
                ps.setString(6,"%"+nmjenis.getText()+"%");
                ps.setString(7,"%"+nmbar.getText()+"%");
                ps.setString(8,"%"+NmIF.getText()+"%");
                if(!TCari.getText().trim().equals("")){
                    ps.setString(9,"%"+TCari.getText()+"%");
                    ps.setString(10,"%"+TCari.getText()+"%");
                    ps.setString(11,"%"+TCari.getText()+"%");
                    ps.setString(12,"%"+TCari.getText()+"%");
                    ps.setString(13,"%"+TCari.getText()+"%");
                    ps.setString(14,"%"+TCari.getText()+"%");
                    ps.setString(15,"%"+TCari.getText()+"%");
                    ps.setString(16,"%"+TCari.getText()+"%");
                    ps.setString(17,"%"+TCari.getText()+"%");
                    ps.setString(18,"%"+TCari.getText()+"%");
                    ps.setString(19,"%"+TCari.getText()+"%");
                    ps.setString(20,"%"+TCari.getText()+"%");
                }
                    
                rs=ps.executeQuery();
                tagihan=0;
                while(rs.next()){
                    tabMode.addRow(new Object[]{
                        rs.getString("tgl_hibah"),rs.getString("no_hibah"),rs.getString("kode_pemberi")+", "+rs.getString("nama_pemberi"),
                        rs.getString("nip")+", "+rs.getString("nama"),"Pembelian di "+rs.getString("nm_bangsal") +" :","","","","","",""
                    });    
                    ps2=koneksi.prepareStatement(
                        "select detailhibah_obat_bhp.kode_brng,databarang.nama_brng, "+
                        "detailhibah_obat_bhp.kode_sat,kodesatuan.satuan,detailhibah_obat_bhp.jumlah,detailhibah_obat_bhp.h_hibah, "+
                        "detailhibah_obat_bhp.subtotalhibah,detailhibah_obat_bhp.h_diakui,detailhibah_obat_bhp.subtotaldiakui,"+
                        "detailhibah_obat_bhp.no_batch,industrifarmasi.nama_industri,detailhibah_obat_bhp.kadaluarsa "+
                        "from detailhibah_obat_bhp inner join databarang on detailhibah_obat_bhp.kode_brng=databarang.kode_brng "+
                        "inner join kodesatuan on detailhibah_obat_bhp.kode_sat=kodesatuan.kode_sat "+
                        "inner join jenis on databarang.kdjns=jenis.kdjns "+
                        "inner join industrifarmasi on databarang.kode_industri=industrifarmasi.kode_industri "+
                        "where detailhibah_obat_bhp.no_hibah=? and jenis.nama like ? and databarang.nama_brng like ? and industrifarmasi.nama_industri like ? "+
                        (TCari.getText().trim().equals("")?"":"and (detailhibah_obat_bhp.kode_brng like ? or databarang.nama_brng like ? or "+
                        "detailhibah_obat_bhp.kode_sat like ? or detailhibah_obat_bhp.no_batch like ? or industrifarmasi.nama_industri like ? or "+
                        "jenis.nama like ?) ")+"order by detailhibah_obat_bhp.kode_brng"
                    );
                    try {
                        ps2.setString(1,rs.getString(2));
                        ps2.setString(2,"%"+nmjenis.getText()+"%");
                        ps2.setString(3,"%"+nmbar.getText()+"%");
                        ps2.setString(4,"%"+NmIF.getText()+"%");
                        if(!TCari.getText().trim().equals("")){
                            ps2.setString(5,"%"+TCari.getText()+"%");
                            ps2.setString(6,"%"+TCari.getText()+"%");
                            ps2.setString(7,"%"+TCari.getText()+"%");
                            ps2.setString(8,"%"+TCari.getText()+"%");
                            ps2.setString(9,"%"+TCari.getText()+"%");
                            ps2.setString(10,"%"+TCari.getText()+"%");
                        }   
                        rs2=ps2.executeQuery();
                        int no=1;
                        while(rs2.next()){
                            tabMode.addRow(new Object[]{"",no+". Batch : "+rs2.getString("no_batch"),"Exp : "+rs2.getString("kadaluarsa"),"I.F. : "+rs2.getString("nama_industri"),
                                rs2.getString("kode_brng")+", "+rs2.getString("nama_brng"),rs2.getString("satuan"),rs2.getString("jumlah"),Valid.SetAngka(rs2.getDouble("h_hibah")),
                                Valid.SetAngka(rs2.getDouble("subtotalhibah")),Valid.SetAngka(rs2.getDouble("h_diakui")),Valid.SetAngka(rs2.getDouble("subtotaldiakui"))});
                            no++;
                        }
                        tabMode.addRow(new Object[]{"","Total",":","","","","","",Valid.SetAngka(rs.getDouble("totalhibah")),"",Valid.SetAngka(rs.getDouble("totalnilai"))});
                        tagihan=tagihan+rs.getDouble("totalnilai");
                    } catch (Exception e) {
                        System.out.println("Notifikasi : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }                        
                }                
                LTotal.setText(Valid.SetAngka(tagihan));
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
        }catch(SQLException e){
            System.out.println("Notifikasi : "+e);
        }        
    }

    public void emptTeks() {
        TCari.requestFocus();        
    }
    
    public void isCek(){
        BtnPrint.setEnabled(akses.gethibah_obat_bhp());
        if(akses.getkode().equals("Admin Utama")){
            ppHapus.setEnabled(true);
        }else{
            ppHapus.setEnabled(false);
        }  
    }
    
    private void runBackground(Runnable task) {
        if (ceksukses) return;
        if (executor.isShutdown() || executor.isTerminated()) return;
        if (!isDisplayable()) return;

        ceksukses = true;
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        try {
            executor.submit(() -> {
                try {
                    task.run();
                } finally {
                    ceksukses = false;
                    SwingUtilities.invokeLater(() -> {
                        if (isDisplayable()) {
                            setCursor(Cursor.getDefaultCursor());
                        }
                    });
                }
            });
        } catch (RejectedExecutionException ex) {
            ceksukses = false;
        }
    }
    
    @Override
    public void dispose() {
        executor.shutdownNow();
        super.dispose();
    }
}

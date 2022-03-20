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
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import keuangan.Jurnal;
import simrskhanza.DlgCariBangsal;
import kepegawaian.DlgCariPetugas;

public class DlgCariPengeluaranApotek extends javax.swing.JDialog {
    private final DefaultTableModel tabMode,tabMode2;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps,ps2;
    private ResultSet rs,rs2;
    private Jurnal jur=new Jurnal();
    private Connection koneksi=koneksiDB.condb();
    private riwayatobat Trackobat=new riwayatobat();
    private int i=0,no=1;
    private Dimension screen=Toolkit.getDefaultToolkit().getScreenSize();
    public DlgCariBangsal bangsal=new DlgCariBangsal(null,false);
    public DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    public DlgBarang barang=new DlgBarang(null,false);
    private DecimalFormat df2 = new DecimalFormat("###,###,###,###,###,###,###");    
    private double ttl=0,subttl=0;
    private String nokeluar="",bang="",ptg="",jen="",bar="",tanggal="";
    private String aktifkanbatch="no",DEPOAKTIFOBAT="";
    private boolean sukses=true;
    
    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public DlgCariPengeluaranApotek(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        tabMode=new DefaultTableModel(null,new Object[]{
                "No.Keluar","Tanggal","Keterangan","Lokasi","Petugas","","",""
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbDokter.setModel(tabMode);

        tbDokter.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 8; i++) {
            TableColumn column = tbDokter.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(100);
            }else if(i==1){
                column.setPreferredWidth(200);
            }else if(i==2){
                column.setPreferredWidth(350);
            }else if(i==3){
                column.setPreferredWidth(140);
            }else if(i==4){
                column.setPreferredWidth(150);
            }else if(i==5){
                column.setPreferredWidth(80);
            }else if(i==6){
                column.setPreferredWidth(30);
            }else if(i==7){
                column.setPreferredWidth(90);
            }
        }
        tbDokter.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabMode2=new DefaultTableModel(null,new Object[]{
                "No.Keluar","Tanggal","Keterangan","Asal Stok","Petugas","Kode Barang","Nama Barang","Jenis","Satuan","No.Batch","No.Faktur","Harga","Jml","Total"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbDokter2.setModel(tabMode2);

        tbDokter2.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbDokter2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 14; i++) {
            TableColumn column = tbDokter2.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(100);
            }else if(i==1){
                column.setPreferredWidth(65);
            }else if(i==2){
                column.setPreferredWidth(130);
            }else if(i==3){
                column.setPreferredWidth(130);
            }else if(i==4){
                column.setPreferredWidth(160);
            }else if(i==5){
                column.setPreferredWidth(80);
            }else if(i==6){
                column.setPreferredWidth(150);
            }else if(i==7){
                column.setPreferredWidth(100);
            }else if(i==8){
                column.setPreferredWidth(50);
            }else if(i==9){
                column.setPreferredWidth(70);
            }else if(i==10){
                column.setPreferredWidth(80);
            }else if(i==11){
                column.setPreferredWidth(80);
            }else if(i==12){
                column.setPreferredWidth(30);
            }else if(i==13){
                column.setPreferredWidth(90);
            }
        }
        tbDokter2.setDefaultRenderer(Object.class, new WarnaTable());

        NoNota.setDocument(new batasInput((byte)25).getKata(NoNota));
        kdgudang.setDocument(new batasInput((byte)15).getKata(kdgudang));
        nmgudang.setDocument(new batasInput((byte)70).getKata(nmgudang));
        kdptg.setDocument(new batasInput((byte)25).getKata(kdptg));
        kdbar.setDocument(new batasInput((byte)15).getKata(kdbar));
        kdjenis.setDocument(new batasInput((byte)3).getKata(kdjenis));
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
        
        bangsal.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgCariPengeluaranApotek")){
                    if(bangsal.getTable().getSelectedRow()!= -1){                   
                        kdgudang.setText(bangsal.getTable().getValueAt(bangsal.getTable().getSelectedRow(),0).toString());
                        nmgudang.setText(bangsal.getTable().getValueAt(bangsal.getTable().getSelectedRow(),1).toString());
                    } 
                    kdgudang.requestFocus();
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
        
        petugas.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgCariPengeluaranApotek")){
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
                if(akses.getform().equals("DlgCariPengeluaranApotek")){
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
                if(akses.getform().equals("DlgCariPengeluaranApotek")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        barang.dispose();
                    }                
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
                if(akses.getform().equals("DlgCariPengeluaranApotek")){
                    if(barang.jenis.getTable().getSelectedRow()!= -1){
                        kdjenis.setText(barang.jenis.getTable().getValueAt(barang.jenis.getTable().getSelectedRow(),0).toString());
                        nmjenis.setText(barang.jenis.getTable().getValueAt(barang.jenis.getTable().getSelectedRow(),1).toString());
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
        
        try {
            aktifkanbatch = koneksiDB.AKTIFKANBATCHOBAT();
            DEPOAKTIFOBAT = koneksiDB.DEPOAKTIFOBAT();
        } catch (Exception e) {
            System.out.println("E : "+e);
            aktifkanbatch = "no";
            DEPOAKTIFOBAT = "";
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
        jPanel1 = new javax.swing.JPanel();
        panelisi4 = new widget.panelisi();
        label17 = new widget.Label();
        kdbar = new widget.TextBox();
        nmbar = new widget.TextBox();
        btnBarang = new widget.Button();
        label24 = new widget.Label();
        kdjenis = new widget.TextBox();
        btnSatuan = new widget.Button();
        nmjenis = new widget.TextBox();
        panelisi1 = new widget.panelisi();
        label10 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        label9 = new widget.Label();
        LTotal = new widget.Label();
        BtnAll = new widget.Button();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();
        panelisi3 = new widget.panelisi();
        label15 = new widget.Label();
        NoNota = new widget.TextBox();
        label11 = new widget.Label();
        Tgl1 = new widget.Tanggal();
        label16 = new widget.Label();
        label13 = new widget.Label();
        kdgudang = new widget.TextBox();
        kdptg = new widget.TextBox();
        nmgudang = new widget.TextBox();
        nmptg = new widget.TextBox();
        btnPasien = new widget.Button();
        btnPetugas = new widget.Button();
        label18 = new widget.Label();
        Tgl2 = new widget.Tanggal();
        TabRawat = new javax.swing.JTabbedPane();
        scrollPane1 = new widget.ScrollPane();
        tbDokter = new widget.Table();
        scrollPane2 = new widget.ScrollPane();
        tbDokter2 = new widget.Table();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        ppHapus.setBackground(new java.awt.Color(255, 255, 254));
        ppHapus.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppHapus.setForeground(new java.awt.Color(50, 50, 50));
        ppHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppHapus.setText("Hapus Stok Keluar");
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
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Cari Stok Keluar Obat, Alkes & BHP Medis ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(816, 100));
        jPanel1.setLayout(new java.awt.BorderLayout(1, 1));

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
        nmbar.setBounds(501, 10, 270, 23);

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
        btnBarang.setBounds(774, 10, 28, 23);

        label24.setText("Jenis :");
        label24.setName("label24"); // NOI18N
        label24.setPreferredSize(new java.awt.Dimension(68, 23));
        panelisi4.add(label24);
        label24.setBounds(0, 10, 74, 23);

        kdjenis.setName("kdjenis"); // NOI18N
        kdjenis.setPreferredSize(new java.awt.Dimension(80, 23));
        kdjenis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdjenisKeyPressed(evt);
            }
        });
        panelisi4.add(kdjenis);
        kdjenis.setBounds(79, 10, 53, 23);

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
        btnSatuan.setBounds(255, 10, 28, 23);

        nmjenis.setName("nmjenis"); // NOI18N
        nmjenis.setPreferredSize(new java.awt.Dimension(80, 23));
        nmjenis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nmjenisKeyPressed(evt);
            }
        });
        panelisi4.add(nmjenis);
        nmjenis.setBounds(134, 10, 116, 23);

        jPanel1.add(panelisi4, java.awt.BorderLayout.CENTER);

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

        label9.setText("Total :");
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(55, 30));
        panelisi1.add(label9);

        LTotal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LTotal.setText("0");
        LTotal.setName("LTotal"); // NOI18N
        LTotal.setPreferredSize(new java.awt.Dimension(155, 30));
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

        internalFrame1.add(jPanel1, java.awt.BorderLayout.PAGE_END);

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(100, 73));
        panelisi3.setLayout(null);

        label15.setText("No.Keluar :");
        label15.setName("label15"); // NOI18N
        label15.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(label15);
        label15.setBounds(0, 10, 70, 23);

        NoNota.setName("NoNota"); // NOI18N
        NoNota.setPreferredSize(new java.awt.Dimension(207, 23));
        NoNota.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoNotaKeyPressed(evt);
            }
        });
        panelisi3.add(NoNota);
        NoNota.setBounds(74, 10, 226, 23);

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label11);
        label11.setBounds(0, 40, 70, 23);

        Tgl1.setDisplayFormat("dd-MM-yyyy");
        Tgl1.setName("Tgl1"); // NOI18N
        Tgl1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Tgl1KeyPressed(evt);
            }
        });
        panelisi3.add(Tgl1);
        Tgl1.setBounds(74, 40, 100, 23);

        label16.setText("Bangsal :");
        label16.setName("label16"); // NOI18N
        label16.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(label16);
        label16.setBounds(385, 10, 60, 23);

        label13.setText("Petugas :");
        label13.setName("label13"); // NOI18N
        label13.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label13);
        label13.setBounds(385, 40, 60, 23);

        kdgudang.setName("kdgudang"); // NOI18N
        kdgudang.setPreferredSize(new java.awt.Dimension(80, 23));
        kdgudang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdgudangKeyPressed(evt);
            }
        });
        panelisi3.add(kdgudang);
        kdgudang.setBounds(449, 10, 80, 23);

        kdptg.setName("kdptg"); // NOI18N
        kdptg.setPreferredSize(new java.awt.Dimension(80, 23));
        kdptg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdptgKeyPressed(evt);
            }
        });
        panelisi3.add(kdptg);
        kdptg.setBounds(449, 40, 80, 23);

        nmgudang.setName("nmgudang"); // NOI18N
        nmgudang.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(nmgudang);
        nmgudang.setBounds(531, 10, 240, 23);

        nmptg.setEditable(false);
        nmptg.setName("nmptg"); // NOI18N
        nmptg.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(nmptg);
        nmptg.setBounds(531, 40, 240, 23);

        btnPasien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPasien.setMnemonic('1');
        btnPasien.setToolTipText("Alt+1");
        btnPasien.setName("btnPasien"); // NOI18N
        btnPasien.setPreferredSize(new java.awt.Dimension(28, 23));
        btnPasien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPasienActionPerformed(evt);
            }
        });
        panelisi3.add(btnPasien);
        btnPasien.setBounds(774, 10, 28, 23);

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
        btnPetugas.setBounds(774, 40, 28, 23);

        label18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label18.setText("s.d.");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label18);
        label18.setBounds(173, 40, 30, 23);

        Tgl2.setDisplayFormat("dd-MM-yyyy");
        Tgl2.setName("Tgl2"); // NOI18N
        Tgl2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Tgl2KeyPressed(evt);
            }
        });
        panelisi3.add(Tgl2);
        Tgl2.setBounds(200, 40, 100, 23);

        internalFrame1.add(panelisi3, java.awt.BorderLayout.PAGE_START);

        TabRawat.setBackground(new java.awt.Color(255, 255, 253));
        TabRawat.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(241, 246, 236)));
        TabRawat.setForeground(new java.awt.Color(50, 50, 50));
        TabRawat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabRawat.setName("TabRawat"); // NOI18N
        TabRawat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatMouseClicked(evt);
            }
        });

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

        TabRawat.addTab("Laporan 1", scrollPane1);

        scrollPane2.setComponentPopupMenu(jPopupMenu1);
        scrollPane2.setName("scrollPane2"); // NOI18N
        scrollPane2.setOpaque(true);

        tbDokter2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbDokter2.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbDokter2.setComponentPopupMenu(jPopupMenu1);
        tbDokter2.setName("tbDokter2"); // NOI18N
        scrollPane2.setViewportView(tbDokter2);

        TabRawat.addTab("Laporan 2", scrollPane2);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents
/*
private void KdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdKeyPressed
    Valid.pindah(evt,BtnCari,Nm);
}//GEN-LAST:event_TKdKeyPressed
*/

    private void btnPasienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPasienActionPerformed
        akses.setform("DlgCariPengeluaranApotek");
        bangsal.emptTeks();
        bangsal.isCek();
        bangsal.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        bangsal.setLocationRelativeTo(internalFrame1);
        bangsal.setAlwaysOnTop(false);
        bangsal.setVisible(true);
    }//GEN-LAST:event_btnPasienActionPerformed

    private void btnPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPetugasActionPerformed
        akses.setform("DlgCariPengeluaranApotek");
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setAlwaysOnTop(false);
        petugas.setVisible(true);
    }//GEN-LAST:event_btnPetugasActionPerformed

    private void Tgl1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tgl1KeyPressed
        Valid.pindah(evt,kdgudang,Tgl2);
    }//GEN-LAST:event_Tgl1KeyPressed

    private void kdgudangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdgudangKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nm_bangsal from pasien where kd_bangsal=?", nmgudang,kdgudang.getText());      
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select nm_bangsal from pasien where kd_bangsal=?", nmgudang,kdgudang.getText());   
            NoNota.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select nm_bangsal from pasien where kd_bangsal=?", nmgudang,kdgudang.getText());   
            Tgl1.requestFocus();      
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnPasienActionPerformed(null);
        }
    }//GEN-LAST:event_kdgudangKeyPressed

    private void NoNotaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoNotaKeyPressed
        Valid.pindah(evt, BtnKeluar, kdptg);
    }//GEN-LAST:event_NoNotaKeyPressed

    private void kdptgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdptgKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama from petugas where nip=?", nmptg,kdptg.getText());           
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select nama from petugas where nip=?", nmptg,kdptg.getText()); 
            Tgl2.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select nama from petugas where nip=?", nmptg,kdptg.getText()); 
            kdbar.requestFocus(); 
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnPetugasActionPerformed(null);
        }
    }//GEN-LAST:event_kdptgKeyPressed

    private void Tgl2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tgl2KeyPressed
        Valid.pindah(evt, Tgl1,kdptg);
    }//GEN-LAST:event_Tgl2KeyPressed

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

    private void btnBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBarangActionPerformed
        akses.setform("DlgCariPengeluaranApotek");
        barang.emptTeks();
        barang.isCek();
        barang.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        barang.setLocationRelativeTo(internalFrame1);
        barang.setAlwaysOnTop(false);
        barang.setVisible(true);
    }//GEN-LAST:event_btnBarangActionPerformed

    private void kdjenisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdjenisKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama from jenis where kode_jen=?", nmjenis,kdjenis.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select nama from jenis where kode_jen=?", nmjenis,kdjenis.getText());
            kdptg.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select nama from jenis where kode_jen=?", nmjenis,kdjenis.getText());
            kdbar.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnSatuanActionPerformed(null);
        }
    }//GEN-LAST:event_kdjenisKeyPressed

    private void btnSatuanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSatuanActionPerformed
        akses.setform("DlgCariPengeluaranApotek");
        barang.jenis.emptTeks();
        barang.jenis.isCek();
        barang.jenis.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        barang.jenis.setLocationRelativeTo(internalFrame1);
        barang.jenis.setAlwaysOnTop(false);
        barang.jenis.setVisible(true);
    }//GEN-LAST:event_btnSatuanActionPerformed

    private void nmjenisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nmjenisKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_nmjenisKeyPressed

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
        if(TabRawat.getSelectedIndex()==0){
            tampil();
        }else if(TabRawat.getSelectedIndex()==1){
            tampil2();
        }
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
        NoNota.setText("");
        kdbar.setText("");
        nmbar.setText("");
        kdjenis.setText("");
        nmjenis.setText("");
        kdgudang.setText("");
        nmgudang.setText("");
        kdptg.setText("");
        nmptg.setText("");
        if(TabRawat.getSelectedIndex()==0){
            tampil();
        }else if(TabRawat.getSelectedIndex()==1){
            tampil2();
        }
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnPrint, BtnKeluar);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        if(TabRawat.getSelectedIndex()==0){
            if(tabMode.getRowCount()==0){
                JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                TCari.requestFocus();
            }else if(tabMode.getRowCount()!=0){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                Sequel.queryu("truncate table temporary");
                int row=tabMode.getRowCount();
                for(int i=0;i<row;i++){  
                    Sequel.menyimpan("temporary","'0','"+
                                    tabMode.getValueAt(i,0).toString()+"','"+
                                    tabMode.getValueAt(i,1).toString()+"','"+
                                    tabMode.getValueAt(i,2).toString()+"','"+
                                    tabMode.getValueAt(i,3).toString()+"','"+
                                    tabMode.getValueAt(i,4).toString()+"','"+
                                    tabMode.getValueAt(i,5).toString()+"','"+
                                    tabMode.getValueAt(i,6).toString()+"','"+
                                    tabMode.getValueAt(i,7).toString()+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Transaksi Stok Keluar"); 
                }
                Sequel.menyimpan("temporary","'0','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Transaksi Stok Keluar"); 
                Sequel.menyimpan("temporary","'0','Total','','','','','','','"+LTotal.getText()+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Transaksi Stok Keluar"); 

                Map<String, Object> param = new HashMap<>();    
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("emailrs",akses.getemailrs());   
                param.put("logo",Sequel.cariGambar("select logo from setting")); 
                Valid.MyReport("rptStokKeluarMedis.jasper","report","::[ Data Stok Keluar Obat, Alkes & BHP ]::",param);
                this.setCursor(Cursor.getDefaultCursor());
            }
                
        }else if(TabRawat.getSelectedIndex()==1){
            if(tabMode2.getRowCount()==0){
                JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                TCari.requestFocus();
            }else if(tabMode2.getRowCount()!=0){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                tanggal=" pengeluaran_obat_bhp.tanggal between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' ";
                nokeluar="";bang="";ptg="";jen="";bar="";
                if(!NoNota.getText().equals("")){
                    nokeluar=" and pengeluaran_obat_bhp.no_keluar='"+NoNota.getText()+"' ";
                }        
                if(!nmgudang.getText().equals("")){
                    bang=" and bangsal.nm_bangsal='"+nmgudang.getText()+"' ";
                }
                if(!nmptg.getText().equals("")){
                    ptg=" and petugas.nama='"+nmptg.getText()+"' ";
                }
                if(!nmjenis.getText().equals("")){
                    jen=" and jenis.nama='"+nmjenis.getText()+"' ";
                }
                if(!nmbar.getText().equals("")){
                    bar=" and databarang.nama_brng='"+nmbar.getText()+"' ";
                }

                Map<String, Object> param = new HashMap<>();    
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("emailrs",akses.getemailrs());   
                param.put("logo",Sequel.cariGambar("select logo from setting")); 
                Valid.MyReportqry("rptStokKeluarMedis2.jasper","report","::[ Data Stok Keluar Obat, Alkes & BHP ]::",
                    "select pengeluaran_obat_bhp.no_keluar, pengeluaran_obat_bhp.tanggal,pengeluaran_obat_bhp.nip,petugas.nama, "+
                    "pengeluaran_obat_bhp.kd_bangsal,bangsal.nm_bangsal,pengeluaran_obat_bhp.keterangan,detail_pengeluaran_obat_bhp.kode_brng,"+
                    "databarang.nama_brng,detail_pengeluaran_obat_bhp.no_faktur,jenis.nama as namajenis,detail_pengeluaran_obat_bhp.harga_beli,"+
                    "detail_pengeluaran_obat_bhp.jumlah, detail_pengeluaran_obat_bhp.kode_sat,detail_pengeluaran_obat_bhp.no_batch,"+
                    " detail_pengeluaran_obat_bhp.total "+
                    " from pengeluaran_obat_bhp inner join petugas inner join bangsal inner join jenis  "+
                    " inner join detail_pengeluaran_obat_bhp inner join databarang "+
                    " on detail_pengeluaran_obat_bhp.kode_brng=databarang.kode_brng "+
                    " and pengeluaran_obat_bhp.kd_bangsal=bangsal.kd_bangsal "+
                    " and pengeluaran_obat_bhp.no_keluar=detail_pengeluaran_obat_bhp.no_keluar "+
                    " and pengeluaran_obat_bhp.nip=petugas.nip and databarang.kdjns=jenis.kdjns "+
                    " where "+tanggal+nokeluar+bang+ptg+jen+bar+" and pengeluaran_obat_bhp.no_keluar like '%"+TCari.getText()+"%' or "+
                    tanggal+nokeluar+bang+ptg+jen+bar+" and pengeluaran_obat_bhp.kd_bangsal like '%"+TCari.getText()+"%' or "+
                    tanggal+nokeluar+bang+ptg+jen+bar+" and pengeluaran_obat_bhp.nip like '%"+TCari.getText()+"%' or "+
                    tanggal+nokeluar+bang+ptg+jen+bar+" and petugas.nama like '%"+TCari.getText()+"%' or "+
                    tanggal+nokeluar+bang+ptg+jen+bar+" and pengeluaran_obat_bhp.keterangan like '%"+TCari.getText()+"%' or "+
                    tanggal+nokeluar+bang+ptg+jen+bar+" and detail_pengeluaran_obat_bhp.no_batch like '%"+TCari.getText()+"%' or "+
                    tanggal+nokeluar+bang+ptg+jen+bar+" and detail_pengeluaran_obat_bhp.kode_brng like '%"+TCari.getText()+"%' or "+
                    tanggal+nokeluar+bang+ptg+jen+bar+" and bangsal.nm_bangsal like '%"+TCari.getText()+"%' or "+
                    tanggal+nokeluar+bang+ptg+jen+bar+" and databarang.nama_brng like '%"+TCari.getText()+"%' or "+
                    tanggal+nokeluar+bang+ptg+jen+bar+" and jenis.nama like '%"+TCari.getText()+"%' "+
                    " order by pengeluaran_obat_bhp.tanggal,pengeluaran_obat_bhp.no_keluar ",param);
                this.setCursor(Cursor.getDefaultCursor());
            }
        } 
    }//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt,BtnAll,BtnAll);
        }
    }//GEN-LAST:event_BtnPrintKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnPrint,kdbar);}
    }//GEN-LAST:event_BtnKeluarKeyPressed

private void ppHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppHapusActionPerformed
    if(tabMode.getRowCount()==0){
        JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
        TCari.requestFocus();
    }else if(tbDokter.getSelectedRow()<= -1){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan pilih data..!!");
    }else{
        if(tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString().trim().equals("")){
            Valid.textKosong(TCari,"No.Keluar");
        }else{
            if(!akses.getkode().equals("Admin Utama")){
                if(!DEPOAKTIFOBAT.equals("")){
                    if(!DEPOAKTIFOBAT.equals(Sequel.cariIsi("select kd_bangsal from pengeluaran_obat_bhp where no_keluar=?",tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString().trim()))){
                        JOptionPane.showMessageDialog(null,"Anda tidak punya hak akses untuk menghapus stok keluar dari depo ini...!");
                    }else{
                        hapus();
                    }
                }else{
                    hapus();
                }
            }else{
                hapus();
            }
        }
    }           
}//GEN-LAST:event_ppHapusActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        if(TabRawat.getSelectedIndex()==0){
            tampil();
        }else if(TabRawat.getSelectedIndex()==1){
            tampil2();
        }
    }//GEN-LAST:event_formWindowOpened

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if(TabRawat.getSelectedIndex()==0){
            tampil();
        }else if(TabRawat.getSelectedIndex()==1){
            tampil2();
        }
    }//GEN-LAST:event_TabRawatMouseClicked

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgCariPengeluaranApotek dialog = new DlgCariPengeluaranApotek(new javax.swing.JFrame(), true);
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
    private widget.Label LTotal;
    private widget.TextBox NoNota;
    private widget.TextBox TCari;
    private javax.swing.JTabbedPane TabRawat;
    private widget.Tanggal Tgl1;
    private widget.Tanggal Tgl2;
    private widget.Button btnBarang;
    private widget.Button btnPasien;
    private widget.Button btnPetugas;
    private widget.Button btnSatuan;
    private widget.InternalFrame internalFrame1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.TextBox kdbar;
    private widget.TextBox kdgudang;
    private widget.TextBox kdjenis;
    private widget.TextBox kdptg;
    private widget.Label label10;
    private widget.Label label11;
    private widget.Label label13;
    private widget.Label label15;
    private widget.Label label16;
    private widget.Label label17;
    private widget.Label label18;
    private widget.Label label24;
    private widget.Label label9;
    private widget.TextBox nmbar;
    private widget.TextBox nmgudang;
    private widget.TextBox nmjenis;
    private widget.TextBox nmptg;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi3;
    private widget.panelisi panelisi4;
    private javax.swing.JMenuItem ppHapus;
    private widget.ScrollPane scrollPane1;
    private widget.ScrollPane scrollPane2;
    private widget.Table tbDokter;
    private widget.Table tbDokter2;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        tanggal="  pengeluaran_obat_bhp.tanggal between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' ";
        nokeluar="";bang="";ptg="";jen="";bar="";
        if(!NoNota.getText().equals("")){
            nokeluar=" and pengeluaran_obat_bhp.no_keluar='"+NoNota.getText()+"' ";
        }        
        if(!nmgudang.getText().equals("")){
            bang=" and bangsal.nm_bangsal='"+nmgudang.getText()+"' ";
        }
        if(!nmptg.getText().equals("")){
            ptg=" and petugas.nama='"+nmptg.getText()+"' ";
        }
        if(!nmjenis.getText().equals("")){
            jen=" and jenis.nama='"+nmjenis.getText()+"' ";
        }
        if(!nmbar.getText().equals("")){
            bar=" and databarang.nama_brng='"+nmbar.getText()+"' ";
        }

        Valid.tabelKosong(tabMode);
        try{
            ps=koneksi.prepareStatement(
                    "select pengeluaran_obat_bhp.no_keluar, pengeluaran_obat_bhp.tanggal, "+
                    "pengeluaran_obat_bhp.nip,petugas.nama, "+
                    "pengeluaran_obat_bhp.kd_bangsal,bangsal.nm_bangsal, "+
                    "pengeluaran_obat_bhp.keterangan "+
                    " from pengeluaran_obat_bhp inner join petugas inner join bangsal inner join jenis  "+
                    " inner join detail_pengeluaran_obat_bhp inner join databarang "+
                    " on detail_pengeluaran_obat_bhp.kode_brng=databarang.kode_brng "+
                    " and pengeluaran_obat_bhp.kd_bangsal=bangsal.kd_bangsal "+
                    " and pengeluaran_obat_bhp.no_keluar=detail_pengeluaran_obat_bhp.no_keluar "+
                    " and pengeluaran_obat_bhp.nip=petugas.nip and databarang.kdjns=jenis.kdjns "+
                    " where "+tanggal+nokeluar+bang+ptg+jen+bar+" and pengeluaran_obat_bhp.no_keluar like '%"+TCari.getText()+"%' or "+
                    tanggal+nokeluar+bang+ptg+jen+bar+" and pengeluaran_obat_bhp.kd_bangsal like '%"+TCari.getText()+"%' or "+
                    tanggal+nokeluar+bang+ptg+jen+bar+" and pengeluaran_obat_bhp.nip like '%"+TCari.getText()+"%' or "+
                    tanggal+nokeluar+bang+ptg+jen+bar+" and petugas.nama like '%"+TCari.getText()+"%' or "+
                    tanggal+nokeluar+bang+ptg+jen+bar+" and pengeluaran_obat_bhp.keterangan like '%"+TCari.getText()+"%' or "+
                    tanggal+nokeluar+bang+ptg+jen+bar+" and detail_pengeluaran_obat_bhp.no_batch like '%"+TCari.getText()+"%' or "+
                    tanggal+nokeluar+bang+ptg+jen+bar+" and detail_pengeluaran_obat_bhp.kode_brng like '%"+TCari.getText()+"%' or "+
                    tanggal+nokeluar+bang+ptg+jen+bar+" and bangsal.nm_bangsal like '%"+TCari.getText()+"%' or "+
                    tanggal+nokeluar+bang+ptg+jen+bar+" and databarang.nama_brng like '%"+TCari.getText()+"%' or "+
                    tanggal+nokeluar+bang+ptg+jen+bar+" and jenis.nama like '%"+TCari.getText()+"%' "+
                    " group by pengeluaran_obat_bhp.no_keluar order by pengeluaran_obat_bhp.tanggal,pengeluaran_obat_bhp.no_keluar ");
            try {
                rs=ps.executeQuery();
                ttl=0;
                while(rs.next()){     
                    tabMode.addRow(new String[]{
                        rs.getString("no_keluar"),rs.getString("tanggal"),
                        rs.getString("keterangan"),
                        rs.getString("kd_bangsal")+", "+rs.getString("nm_bangsal"),
                        rs.getString("nip")+", "+rs.getString("nama"),"","",""
                    });
                    tabMode.addRow(new String[]{
                        "","No.Batch & Faktur","Barang","Jenis","Satuan","Harga","Jml","Total"
                    });
                    ps2=koneksi.prepareStatement(
                            " select detail_pengeluaran_obat_bhp.kode_brng,databarang.nama_brng,detail_pengeluaran_obat_bhp.no_faktur,"+
                            " jenis.nama,detail_pengeluaran_obat_bhp.harga_beli,detail_pengeluaran_obat_bhp.jumlah, "+
                            " detail_pengeluaran_obat_bhp.kode_sat,detail_pengeluaran_obat_bhp.no_batch,detail_pengeluaran_obat_bhp.total from "+
                            " detail_pengeluaran_obat_bhp inner join databarang inner join jenis "+
                            " on detail_pengeluaran_obat_bhp.kode_brng=databarang.kode_brng "+
                            " and databarang.kdjns=jenis.kdjns where "+
                            " detail_pengeluaran_obat_bhp.no_keluar='"+rs.getString("no_keluar")+"' "+jen+bar+" and detail_pengeluaran_obat_bhp.kode_brng like '%"+TCari.getText()+"%' or "+
                            " detail_pengeluaran_obat_bhp.no_keluar='"+rs.getString("no_keluar")+"' "+jen+bar+" and databarang.nama_brng like '%"+TCari.getText()+"%' or "+
                            " detail_pengeluaran_obat_bhp.no_keluar='"+rs.getString("no_keluar")+"' "+jen+bar+" and detail_pengeluaran_obat_bhp.no_batch like '%"+TCari.getText()+"%' or "+
                            " detail_pengeluaran_obat_bhp.no_keluar='"+rs.getString("no_keluar")+"' "+jen+bar+" and jenis.nama like '%"+TCari.getText()+"%' order by detail_pengeluaran_obat_bhp.kode_brng");
                    try {
                        rs2=ps2.executeQuery();
                        no=1;
                        subttl=0;
                        while(rs2.next()){
                            subttl=subttl+rs2.getDouble("total");
                            ttl=ttl+rs2.getDouble("total");
                            tabMode.addRow(new String[]{
                                "",no+". B "+rs2.getString("no_batch")+", F "+rs2.getString("no_faktur"),rs2.getString("kode_brng")+", "+rs2.getString("nama_brng"),
                                rs2.getString("nama"),rs2.getString("kode_sat"),df2.format(rs2.getDouble("harga_beli")),
                                df2.format(rs2.getDouble("jumlah")),df2.format(rs2.getDouble("total"))
                            });
                            no++;
                        }   
                        tabMode.addRow(new String[]{
                            "","Sub Total :","","","","","",df2.format(subttl)
                        }); 
                        tabMode.addRow(new String[]{
                            "","","","","","","",""
                        }); 
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
            LTotal.setText(df2.format(ttl));
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }
    
    private void tampil2() {
        tanggal=" pengeluaran_obat_bhp.tanggal between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' ";
        nokeluar="";bang="";ptg="";jen="";bar="";
        if(!NoNota.getText().equals("")){
            nokeluar=" and pengeluaran_obat_bhp.no_keluar='"+NoNota.getText()+"' ";
        }        
        if(!nmgudang.getText().equals("")){
            bang=" and bangsal.nm_bangsal='"+nmgudang.getText()+"' ";
        }
        if(!nmptg.getText().equals("")){
            ptg=" and petugas.nama='"+nmptg.getText()+"' ";
        }
        if(!nmjenis.getText().equals("")){
            jen=" and jenis.nama='"+nmjenis.getText()+"' ";
        }
        if(!nmbar.getText().equals("")){
            bar=" and databarang.nama_brng='"+nmbar.getText()+"' ";
        }

        Valid.tabelKosong(tabMode2);
        try{
            ps=koneksi.prepareStatement(
                    "select pengeluaran_obat_bhp.no_keluar, pengeluaran_obat_bhp.tanggal,pengeluaran_obat_bhp.nip,petugas.nama, "+
                    "pengeluaran_obat_bhp.kd_bangsal,bangsal.nm_bangsal,pengeluaran_obat_bhp.keterangan,detail_pengeluaran_obat_bhp.kode_brng,"+
                    "databarang.nama_brng,detail_pengeluaran_obat_bhp.no_faktur,jenis.nama as namajenis,detail_pengeluaran_obat_bhp.harga_beli,"+
                    "detail_pengeluaran_obat_bhp.jumlah, detail_pengeluaran_obat_bhp.kode_sat,detail_pengeluaran_obat_bhp.no_batch,"+
                    " detail_pengeluaran_obat_bhp.total "+
                    " from pengeluaran_obat_bhp inner join petugas inner join bangsal inner join jenis  "+
                    " inner join detail_pengeluaran_obat_bhp inner join databarang "+
                    " on detail_pengeluaran_obat_bhp.kode_brng=databarang.kode_brng "+
                    " and pengeluaran_obat_bhp.kd_bangsal=bangsal.kd_bangsal "+
                    " and pengeluaran_obat_bhp.no_keluar=detail_pengeluaran_obat_bhp.no_keluar "+
                    " and pengeluaran_obat_bhp.nip=petugas.nip and databarang.kdjns=jenis.kdjns "+
                    " where "+tanggal+nokeluar+bang+ptg+jen+bar+" and pengeluaran_obat_bhp.no_keluar like '%"+TCari.getText()+"%' or "+
                    tanggal+nokeluar+bang+ptg+jen+bar+" and pengeluaran_obat_bhp.kd_bangsal like '%"+TCari.getText()+"%' or "+
                    tanggal+nokeluar+bang+ptg+jen+bar+" and pengeluaran_obat_bhp.nip like '%"+TCari.getText()+"%' or "+
                    tanggal+nokeluar+bang+ptg+jen+bar+" and petugas.nama like '%"+TCari.getText()+"%' or "+
                    tanggal+nokeluar+bang+ptg+jen+bar+" and pengeluaran_obat_bhp.keterangan like '%"+TCari.getText()+"%' or "+
                    tanggal+nokeluar+bang+ptg+jen+bar+" and detail_pengeluaran_obat_bhp.no_batch like '%"+TCari.getText()+"%' or "+
                    tanggal+nokeluar+bang+ptg+jen+bar+" and detail_pengeluaran_obat_bhp.kode_brng like '%"+TCari.getText()+"%' or "+
                    tanggal+nokeluar+bang+ptg+jen+bar+" and bangsal.nm_bangsal like '%"+TCari.getText()+"%' or "+
                    tanggal+nokeluar+bang+ptg+jen+bar+" and databarang.nama_brng like '%"+TCari.getText()+"%' or "+
                    tanggal+nokeluar+bang+ptg+jen+bar+" and jenis.nama like '%"+TCari.getText()+"%' "+
                    " order by pengeluaran_obat_bhp.tanggal,pengeluaran_obat_bhp.no_keluar ");
            try {
                rs=ps.executeQuery();
                ttl=0;
                while(rs.next()){     
                    tabMode2.addRow(new String[]{
                        rs.getString("no_keluar"),rs.getString("tanggal"),rs.getString("keterangan"),
                        rs.getString("kd_bangsal")+", "+rs.getString("nm_bangsal"),rs.getString("nip")+", "+rs.getString("nama"),
                        rs.getString("kode_brng"),rs.getString("nama_brng"),rs.getString("namajenis"),rs.getString("kode_sat"),
                        rs.getString("no_batch"),rs.getString("no_faktur"),df2.format(rs.getDouble("harga_beli")),
                        df2.format(rs.getDouble("jumlah")),df2.format(rs.getDouble("total"))
                    });
                    ttl=ttl+rs.getDouble("total");
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
            LTotal.setText(df2.format(ttl));
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        
    }

    public void emptTeks() {
        kdbar.setText("");
        nmbar.setText("");
        kdjenis.setText("");
        kdbar.requestFocus();        
    }   
     
    public void isCek(){
        BtnPrint.setEnabled(akses.getpengeluaran_stok_apotek());
        if(akses.getkode().equals("Admin Utama")){
            ppHapus.setEnabled(true);
        }else{
            ppHapus.setEnabled(false);
        }  
    }

    private void hapus() {
       Sequel.AutoComitFalse();
       sukses=true;
       try {
           ps=koneksi.prepareStatement(
                   "select no_keluar, kd_bangsal from pengeluaran_obat_bhp where no_keluar=?");
           try {
              ps.setString(1,tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString().trim());
              rs=ps.executeQuery();
              if(rs.next()){
                  ps2=koneksi.prepareStatement(
                       "select kode_brng,jumlah,no_batch,no_faktur from detail_pengeluaran_obat_bhp where no_keluar=? ");
                  try {
                      ps2.setString(1,rs.getString(1));                
                      rs2=ps2.executeQuery();
                      while(rs2.next()){
                          if(aktifkanbatch.equals("yes")){
                             Sequel.mengedit3("data_batch","no_batch=? and kode_brng=? and no_faktur=?","sisa=sisa+?",4,new String[]{
                                ""+rs2.getDouble("jumlah"),rs2.getString("no_batch"),rs2.getString("kode_brng"),rs2.getString("no_faktur")
                             });
                             Trackobat.catatRiwayat(rs2.getString("kode_brng"),rs2.getDouble("jumlah"),0,"Stok Keluar",akses.getkode(),rs.getString("kd_bangsal"),"Hapus",rs2.getString("no_batch"),rs2.getString("no_faktur"),rs.getString("no_keluar"));
                             Sequel.menyimpan("gudangbarang","'"+rs2.getString("kode_brng") +"','"+rs.getString("kd_bangsal")+"','"+rs2.getString("jumlah") +"','"+rs2.getString("no_batch")+"','"+rs2.getString("no_faktur")+"'", 
                                                   "stok=stok+'"+rs2.getString("jumlah") +"'","kode_brng='"+rs2.getString("kode_brng")+"' and kd_bangsal='"+rs.getString("kd_bangsal")+"' and no_batch='"+rs2.getString("no_batch")+"' and no_faktur='"+rs2.getString("no_faktur")+"'");
                          }else{
                             Trackobat.catatRiwayat(rs2.getString("kode_brng"),rs2.getDouble("jumlah"),0,"Stok Keluar",akses.getkode(),rs.getString("kd_bangsal"),"Hapus","","",rs.getString("no_keluar"));
                             Sequel.menyimpan("gudangbarang","'"+rs2.getString("kode_brng") +"','"+rs.getString("kd_bangsal")+"','"+rs2.getString("jumlah") +"','',''", 
                                                   "stok=stok+'"+rs2.getString("jumlah") +"'","kode_brng='"+rs2.getString("kode_brng")+"' and kd_bangsal='"+rs.getString("kd_bangsal")+"' and no_batch='' and no_faktur=''");
                          } 
                      }
                  } catch (Exception e) {
                      sukses=false;
                      System.out.println("Notifikasi : "+e);
                  } finally{
                      if(rs2!=null){
                          rs2.close();
                      }
                      if(ps2!=null){
                          ps2.close();
                      }
                  }
                  if(sukses==true){
                     Sequel.queryu("delete from tampjurnal");
                     Sequel.menyimpan("tampjurnal","?,?,?,?",4,new String[]{Sequel.cariIsi("select Stok_Keluar_Medis from set_akun"),"PERSEDIAAN BARANG",Sequel.cariIsi("select sum(total) from detail_pengeluaran_obat_bhp where no_keluar='"+rs.getString("no_keluar")+"'"),"0"});
                     Sequel.menyimpan("tampjurnal","?,?,?,?",4,new String[]{Sequel.cariIsi("select Kontra_Stok_Keluar_Medis from set_akun"),"KONTRA PERSEDIAAN BARANG","0",Sequel.cariIsi("select sum(total) from detail_pengeluaran_obat_bhp where no_keluar='"+rs.getString("no_keluar")+"'")}); 
                     sukses=jur.simpanJurnal(rs.getString("no_keluar"),"U","PEMBATALAN STOK KELUAR BARANG MEDIS/OBAT/ALKES/BHP"+", OLEH "+akses.getkode());  
                  } 
              }    
              if(sukses==true){
                  Sequel.queryu("delete from pengeluaran_obat_bhp where no_keluar='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString().trim()+"'");   
                  Sequel.Commit();
              }else{
                  sukses=false;
                  JOptionPane.showMessageDialog(null,"Terjadi kesalahan saat pemrosesan data, transaksi dibatalkan.\nPeriksa kembali data sebelum melanjutkan menyimpan..!!");
                  Sequel.RollBack();
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
       } catch (Exception ex) {
           System.out.println(ex);
       } 
       if(sukses==true){
           tampil();
       }
       Sequel.AutoComitTrue();
    }
}

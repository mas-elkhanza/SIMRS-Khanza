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
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import keuangan.DlgBayarPemesanan;
import keuangan.Jurnal;
import kepegawaian.DlgCariPetugas;

public class DlgRekapPenerimaan extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private riwayatobat Trackobat=new riwayatobat();
    private Connection koneksi=koneksiDB.condb();
    private Dimension screen=Toolkit.getDefaultToolkit().getScreenSize();
    public  DlgSuplier suplier=new DlgSuplier(null,false);
    public  DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    public  DlgBarang barang=new DlgBarang(null,false);
    private PreparedStatement ps;
    private ResultSet rs;
    private double tagihan=0,subtotal=0,diskon;

    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public DlgRekapPenerimaan(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        tabMode=new DefaultTableModel(null,new Object[]{
                "No.Faktur","No.Order","Suplier","Petugas","Tgl.Pesan","Tgl.Faktur",
                "Jatuh Tempo","Kadaluarsa","No.Batch","Barang","Satuan","Jml.Beli",
                "Harga Beli(Rp)","SubTotal(Rp)","Disk(%)","Bsr.Disk(Rp)","Total(Rp)",
                "Status Bayar"
            }){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
             Class[] types = new Class[] {
                java.lang.String.class,java.lang.String.class,java.lang.String.class,
                java.lang.String.class,java.lang.String.class,java.lang.String.class,
                java.lang.String.class,java.lang.String.class,java.lang.String.class,
                java.lang.String.class,java.lang.String.class,java.lang.Double.class,
                java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,
                java.lang.Double.class,java.lang.Double.class,java.lang.String.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbDokter.setModel(tabMode);

        tbDokter.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 18; i++) {
            TableColumn column = tbDokter.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(90);
            }else if(i==1){
                column.setPreferredWidth(90);
            }else if(i==2){
                column.setPreferredWidth(170);
            }else if(i==3){
                column.setPreferredWidth(170);
            }else if(i==4){
                column.setPreferredWidth(70);
            }else if(i==5){
                column.setPreferredWidth(70);
            }else if(i==6){
                column.setPreferredWidth(70);
            }else if(i==7){
                column.setPreferredWidth(70);
            }else if(i==8){
                column.setPreferredWidth(70);
            }else if(i==9){
                column.setPreferredWidth(200);
            }else if(i==10){
                column.setPreferredWidth(50);
            }else if(i==11){
                column.setPreferredWidth(50);
            }else if(i==12){
                column.setPreferredWidth(85);
            }else if(i==13){
                column.setPreferredWidth(90);
            }else if(i==14){
                column.setPreferredWidth(45);
            }else if(i==15){
                column.setPreferredWidth(85);
            }else if(i==16){
                column.setPreferredWidth(90);
            }else if(i==17){
                column.setPreferredWidth(85);
            }
        }
        tbDokter.setDefaultRenderer(Object.class, new WarnaTable());

        NoFaktur.setDocument(new batasInput((byte)25).getKata(NoFaktur));
        kdsup.setDocument(new batasInput((byte)5).getKata(kdsup));
        kdptg.setDocument(new batasInput((byte)25).getKata(kdptg));
        kdbar.setDocument(new batasInput((byte)15).getKata(kdbar));
        KdIF.setDocument(new batasInput((byte)15).getKata(KdIF));
        kdsat.setDocument(new batasInput((byte)4).getKata(kdsat));
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
                if(akses.getform().equals("DlgRekapPenerimaan")){
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
                if(akses.getform().equals("DlgRekapPenerimaan")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        suplier.dispose();
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
                if(akses.getform().equals("DlgRekapPenerimaan")){
                    if(barang.jenis.getTable().getSelectedRow()!= -1){                          
                        kdsat.setText(barang.jenis.getTable().getValueAt(barang.jenis.getTable().getSelectedRow(),0).toString());                    
                        nmsat.setText(barang.jenis.getTable().getValueAt(barang.jenis.getTable().getSelectedRow(),1).toString());                        
                    }                    
                    kdsat.requestFocus();
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
                if(akses.getform().equals("DlgRekapPenerimaan")){
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
                if(akses.getform().equals("DlgRekapPenerimaan")){
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
                if(akses.getform().equals("DlgRekapPenerimaan")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        barang.dispose();
                    }
                }                                
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        }); 
        
        barang.industri.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgRekapPenerimaan")){
                    if(barang.industri.getTable().getSelectedRow()!= -1){                   
                        KdIF.setText(barang.industri.getTable().getValueAt(barang.industri.getTable().getSelectedRow(),0).toString());                    
                        NmIF.setText(barang.industri.getTable().getValueAt(barang.industri.getTable().getSelectedRow(),1).toString());                                        
                    }   
                    KdIF.requestFocus();
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
        
        barang.industri.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(akses.getform().equals("DlgRekapPenerimaan")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        barang.industri.dispose();
                    }      
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
        label24 = new widget.Label();
        kdsat = new widget.TextBox();
        btnSatuan = new widget.Button();
        nmsat = new widget.TextBox();
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

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Rekap Penerimaan Obat, Alkes & BHP Medis ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(70, 70, 70))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

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

        label9.setText("Total :");
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(45, 30));
        panelisi1.add(label9);

        LTotal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LTotal.setText("0");
        LTotal.setName("LTotal"); // NOI18N
        LTotal.setPreferredSize(new java.awt.Dimension(120, 30));
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

        kdsat.setName("kdsat"); // NOI18N
        kdsat.setPreferredSize(new java.awt.Dimension(80, 23));
        kdsat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdsatKeyPressed(evt);
            }
        });
        panelisi4.add(kdsat);
        kdsat.setBounds(80, 10, 53, 23);

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

        nmsat.setName("nmsat"); // NOI18N
        nmsat.setPreferredSize(new java.awt.Dimension(80, 23));
        nmsat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nmsatKeyPressed(evt);
            }
        });
        panelisi4.add(nmsat);
        nmsat.setBounds(135, 10, 115, 23);

        jPanel1.add(panelisi4, java.awt.BorderLayout.CENTER);

        internalFrame1.add(jPanel1, java.awt.BorderLayout.PAGE_END);

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(100, 103));
        panelisi3.setLayout(null);

        label15.setText("No.Faktur :");
        label15.setName("label15"); // NOI18N
        label15.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(label15);
        label15.setBounds(0, 10, 80, 23);

        NoFaktur.setName("NoFaktur"); // NOI18N
        NoFaktur.setPreferredSize(new java.awt.Dimension(207, 23));
        NoFaktur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoFakturKeyPressed(evt);
            }
        });
        panelisi3.add(NoFaktur);
        NoFaktur.setBounds(84, 10, 219, 23);

        label11.setText("Tgl.Datang :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label11);
        label11.setBounds(0, 40, 80, 23);

        TglBeli1.setDisplayFormat("dd-MM-yyyy");
        TglBeli1.setName("TglBeli1"); // NOI18N
        TglBeli1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglBeli1KeyPressed(evt);
            }
        });
        panelisi3.add(TglBeli1);
        TglBeli1.setBounds(84, 40, 95, 23);

        label16.setText("Supplier :");
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
        label12.setBounds(179, 40, 27, 23);

        TglBeli2.setDisplayFormat("dd-MM-yyyy");
        TglBeli2.setName("TglBeli2"); // NOI18N
        TglBeli2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglBeli2KeyPressed(evt);
            }
        });
        panelisi3.add(TglBeli2);
        TglBeli2.setBounds(208, 40, 95, 23);

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
        suplier.dispose();
        petugas.dispose();
        barang.industri.dispose();
        barang.jenis.dispose();
        barang.dispose();
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
        akses.setform("DlgRekapPenerimaan");
        suplier.emptTeks();
        suplier.isCek();
        suplier.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        suplier.setLocationRelativeTo(internalFrame1);
        suplier.setAlwaysOnTop(false);
        suplier.setVisible(true);
    }//GEN-LAST:event_btnSuplierActionPerformed

    private void btnPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPetugasActionPerformed
        akses.setform("DlgRekapPenerimaan");
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
        akses.setform("DlgRekapPenerimaan");
        barang.emptTeks();
        barang.isCek();
        barang.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        barang.setLocationRelativeTo(internalFrame1);
        barang.setAlwaysOnTop(false);
        barang.setVisible(true);
    }//GEN-LAST:event_btnBarangActionPerformed

    private void btnSatuanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSatuanActionPerformed
        akses.setform("DlgRekapPenerimaan");
        barang.jenis.emptTeks();
        barang.jenis.isCek();
        barang.jenis.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        barang.jenis.setLocationRelativeTo(internalFrame1);
        barang.jenis.setAlwaysOnTop(false);
        barang.jenis.setVisible(true);
    }//GEN-LAST:event_btnSatuanActionPerformed

    private void kdsupKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdsupKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama_suplier from datasuplier where kode_suplier=?", nmsup,kdsup.getText());            
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select nama_suplier from datasuplier where kode_suplier=?", nmsup,kdsup.getText());
            NoFaktur.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select nama_suplier from datasuplier where kode_suplier=?", nmsup,kdsup.getText());
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
            Sequel.cariIsi("select nama from petugas where nip=?",nmptg,kdptg.getText());     
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select nama from petugas where nip=?",nmptg,kdptg.getText());
            kdsup.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select nama from petugas where nip=?",nmptg,kdptg.getText());
            kdbar.requestFocus();       
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnPetugasActionPerformed(null);
        }
    }//GEN-LAST:event_kdptgKeyPressed

    private void kdbarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdbarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama_brng from databarang where kode_brng=?", nmbar,kdbar.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){            
            Sequel.cariIsi("select nama_brng from databarang where kode_brng=?", nmbar,kdbar.getText());
            kdsat.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){            
            Sequel.cariIsi("select nama_brng from databarang where kode_brng=?", nmbar,kdbar.getText());
            TCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnBarangActionPerformed(null);
        }
    }//GEN-LAST:event_kdbarKeyPressed

    private void kdsatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdsatKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select satuan from kodesatuan where kode_sat=?", nmsat,kdsat.getText());         
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select satuan from kodesatuan where kode_sat=?", nmsat,kdsat.getText());
            kdptg.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select satuan from kodesatuan where kode_sat=?", nmsat,kdsat.getText());
            kdbar.requestFocus();   
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnSatuanActionPerformed(null);
        }
    }//GEN-LAST:event_kdsatKeyPressed

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
        NoFaktur.setText("");
        kdbar.setText("");
        nmbar.setText("");
        kdsat.setText("");
        nmsat.setText("");
        kdsup.setText("");
        nmsup.setText("");
        kdptg.setText("");
        nmptg.setText("");
        KdIF.setText("");
        NmIF.setText("");
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
            Map<String, Object> param = new HashMap<>();    
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("emailrs",akses.getemailrs());   
                param.put("logo",Sequel.cariGambar("select logo from setting")); 
            Valid.MyReportqry("rptRekapPenerimaan.jasper","report","::[ Rekap Penerimaan Barang ]::",
                "select pemesanan.tgl_pesan,pemesanan.no_faktur, "+
                    "pemesanan.kode_suplier,datasuplier.nama_suplier, "+
                    "pemesanan.nip,petugas.nama,bangsal.nm_bangsal,pemesanan.tgl_faktur, "+
                    "pemesanan.tgl_tempo,pemesanan.status,pemesanan.total2,pemesanan.ppn,"+
                    "pemesanan.meterai,pemesanan.tagihan,pemesanan.no_order,detailpesan.kode_brng,databarang.nama_brng, "+
                    "detailpesan.kode_sat,kodesatuan.satuan,detailpesan.jumlah,detailpesan.h_pesan, "+
                    "detailpesan.subtotal,detailpesan.dis,detailpesan.besardis,detailpesan.total,"+
                    "detailpesan.no_batch,industrifarmasi.nama_industri,detailpesan.kadaluarsa "+
                    " from pemesanan inner join datasuplier inner join petugas inner join bangsal  "+
                    " inner join detailpesan inner join databarang inner join kodesatuan "+
                    " inner join jenis inner join industrifarmasi "+
                    " on detailpesan.kode_brng=databarang.kode_brng "+
                    " and detailpesan.kode_sat=kodesatuan.kode_sat "+
                    " and pemesanan.kd_bangsal=bangsal.kd_bangsal "+
                    " and pemesanan.no_faktur=detailpesan.no_faktur "+
                    " and pemesanan.kode_suplier=datasuplier.kode_suplier "+
                    " and databarang.kode_industri=industrifarmasi.kode_industri "+
                    " and pemesanan.nip=petugas.nip and databarang.kdjns=jenis.kdjns"+
                    " where pemesanan.tgl_pesan between '"+Valid.SetTgl(TglBeli1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(TglBeli2.getSelectedItem()+"")+"' and pemesanan.no_faktur like '%"+NoFaktur.getText()+"%' and datasuplier.nama_suplier like '%"+nmsup.getText()+"%' and petugas.nama like '%"+nmptg.getText()+"%'  and jenis.nama like '%"+nmsat.getText()+"%' and databarang.nama_brng like '%"+nmbar.getText()+"%' and industrifarmasi.nama_industri like '%"+NmIF.getText()+"%' and pemesanan.no_faktur like '%"+TCari.getText().trim()+"%' or "+
                    " pemesanan.tgl_pesan between '"+Valid.SetTgl(TglBeli1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(TglBeli2.getSelectedItem()+"")+"' and pemesanan.no_faktur like '%"+NoFaktur.getText()+"%' and datasuplier.nama_suplier like '%"+nmsup.getText()+"%' and petugas.nama like '%"+nmptg.getText()+"%'  and jenis.nama like '%"+nmsat.getText()+"%' and databarang.nama_brng like '%"+nmbar.getText()+"%' and industrifarmasi.nama_industri like '%"+NmIF.getText()+"%' and pemesanan.kode_suplier like '%"+TCari.getText().trim()+"%' or "+
                    " pemesanan.tgl_pesan between '"+Valid.SetTgl(TglBeli1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(TglBeli2.getSelectedItem()+"")+"' and pemesanan.no_faktur like '%"+NoFaktur.getText()+"%' and datasuplier.nama_suplier like '%"+nmsup.getText()+"%' and petugas.nama like '%"+nmptg.getText()+"%'  and jenis.nama like '%"+nmsat.getText()+"%' and databarang.nama_brng like '%"+nmbar.getText()+"%' and industrifarmasi.nama_industri like '%"+NmIF.getText()+"%' and datasuplier.nama_suplier like '%"+TCari.getText().trim()+"%' or "+
                    " pemesanan.tgl_pesan between '"+Valid.SetTgl(TglBeli1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(TglBeli2.getSelectedItem()+"")+"' and pemesanan.no_faktur like '%"+NoFaktur.getText()+"%' and datasuplier.nama_suplier like '%"+nmsup.getText()+"%' and petugas.nama like '%"+nmptg.getText()+"%'  and jenis.nama like '%"+nmsat.getText()+"%' and databarang.nama_brng like '%"+nmbar.getText()+"%' and industrifarmasi.nama_industri like '%"+NmIF.getText()+"%' and pemesanan.nip like '%"+TCari.getText().trim()+"%' or "+
                    " pemesanan.tgl_pesan between '"+Valid.SetTgl(TglBeli1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(TglBeli2.getSelectedItem()+"")+"' and pemesanan.no_faktur like '%"+NoFaktur.getText()+"%' and datasuplier.nama_suplier like '%"+nmsup.getText()+"%' and petugas.nama like '%"+nmptg.getText()+"%'  and jenis.nama like '%"+nmsat.getText()+"%' and databarang.nama_brng like '%"+nmbar.getText()+"%' and industrifarmasi.nama_industri like '%"+NmIF.getText()+"%' and petugas.nama like '%"+TCari.getText().trim()+"%' or "+
                    " pemesanan.tgl_pesan between '"+Valid.SetTgl(TglBeli1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(TglBeli2.getSelectedItem()+"")+"' and pemesanan.no_faktur like '%"+NoFaktur.getText()+"%' and datasuplier.nama_suplier like '%"+nmsup.getText()+"%' and petugas.nama like '%"+nmptg.getText()+"%'  and jenis.nama like '%"+nmsat.getText()+"%' and databarang.nama_brng like '%"+nmbar.getText()+"%' and industrifarmasi.nama_industri like '%"+NmIF.getText()+"%' and bangsal.nm_bangsal like '%"+TCari.getText().trim()+"%' or "+
                    " pemesanan.tgl_pesan between '"+Valid.SetTgl(TglBeli1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(TglBeli2.getSelectedItem()+"")+"' and pemesanan.no_faktur like '%"+NoFaktur.getText()+"%' and datasuplier.nama_suplier like '%"+nmsup.getText()+"%' and petugas.nama like '%"+nmptg.getText()+"%'  and jenis.nama like '%"+nmsat.getText()+"%' and databarang.nama_brng like '%"+nmbar.getText()+"%' and industrifarmasi.nama_industri like '%"+NmIF.getText()+"%' and detailpesan.kode_brng like '%"+TCari.getText().trim()+"%' or "+
                    " pemesanan.tgl_pesan between '"+Valid.SetTgl(TglBeli1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(TglBeli2.getSelectedItem()+"")+"' and pemesanan.no_faktur like '%"+NoFaktur.getText()+"%' and datasuplier.nama_suplier like '%"+nmsup.getText()+"%' and petugas.nama like '%"+nmptg.getText()+"%'  and jenis.nama like '%"+nmsat.getText()+"%' and databarang.nama_brng like '%"+nmbar.getText()+"%' and industrifarmasi.nama_industri like '%"+NmIF.getText()+"%' and databarang.nama_brng like '%"+TCari.getText().trim()+"%' or "+
                    " pemesanan.tgl_pesan between '"+Valid.SetTgl(TglBeli1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(TglBeli2.getSelectedItem()+"")+"' and pemesanan.no_faktur like '%"+NoFaktur.getText()+"%' and datasuplier.nama_suplier like '%"+nmsup.getText()+"%' and petugas.nama like '%"+nmptg.getText()+"%'  and jenis.nama like '%"+nmsat.getText()+"%' and databarang.nama_brng like '%"+nmbar.getText()+"%' and industrifarmasi.nama_industri like '%"+NmIF.getText()+"%' and detailpesan.kode_sat like '%"+TCari.getText().trim()+"%' or "+
                    " pemesanan.tgl_pesan between '"+Valid.SetTgl(TglBeli1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(TglBeli2.getSelectedItem()+"")+"' and pemesanan.no_faktur like '%"+NoFaktur.getText()+"%' and datasuplier.nama_suplier like '%"+nmsup.getText()+"%' and petugas.nama like '%"+nmptg.getText()+"%'  and jenis.nama like '%"+nmsat.getText()+"%' and databarang.nama_brng like '%"+nmbar.getText()+"%' and industrifarmasi.nama_industri like '%"+NmIF.getText()+"%' and detailpesan.no_batch like '%"+TCari.getText().trim()+"%' or "+
                    " pemesanan.tgl_pesan between '"+Valid.SetTgl(TglBeli1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(TglBeli2.getSelectedItem()+"")+"' and pemesanan.no_faktur like '%"+NoFaktur.getText()+"%' and datasuplier.nama_suplier like '%"+nmsup.getText()+"%' and petugas.nama like '%"+nmptg.getText()+"%'  and jenis.nama like '%"+nmsat.getText()+"%' and databarang.nama_brng like '%"+nmbar.getText()+"%' and industrifarmasi.nama_industri like '%"+NmIF.getText()+"%' and industrifarmasi.nama_industri like '%"+TCari.getText().trim()+"%' or "+
                    " pemesanan.tgl_pesan between '"+Valid.SetTgl(TglBeli1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(TglBeli2.getSelectedItem()+"")+"' and pemesanan.no_faktur like '%"+NoFaktur.getText()+"%' and datasuplier.nama_suplier like '%"+nmsup.getText()+"%' and petugas.nama like '%"+nmptg.getText()+"%'  and jenis.nama like '%"+nmsat.getText()+"%' and databarang.nama_brng like '%"+nmbar.getText()+"%' and industrifarmasi.nama_industri like '%"+NmIF.getText()+"%' and pemesanan.no_order like '%"+TCari.getText().trim()+"%' or "+
                    " pemesanan.tgl_pesan between '"+Valid.SetTgl(TglBeli1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(TglBeli2.getSelectedItem()+"")+"' and pemesanan.no_faktur like '%"+NoFaktur.getText()+"%' and datasuplier.nama_suplier like '%"+nmsup.getText()+"%' and petugas.nama like '%"+nmptg.getText()+"%'  and jenis.nama like '%"+nmsat.getText()+"%' and databarang.nama_brng like '%"+nmbar.getText()+"%' and industrifarmasi.nama_industri like '%"+NmIF.getText()+"%' and jenis.nama like '%"+TCari.getText().trim()+"%' "+
                    " order by pemesanan.tgl_pesan,pemesanan.no_faktur ",param);
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

    private void nmsatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nmsatKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_nmsatKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

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
        akses.setform("DlgRekapPenerimaan");
        barang.industri.emptTeks();
        barang.industri.isCek();
        barang.industri.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        barang.industri.setLocationRelativeTo(internalFrame1);
        barang.industri.setAlwaysOnTop(false);
        barang.industri.setVisible(true);
    }//GEN-LAST:event_btnIFActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgRekapPenerimaan dialog = new DlgRekapPenerimaan(new javax.swing.JFrame(), true);
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
    private widget.TextBox kdbar;
    private widget.TextBox kdptg;
    private widget.TextBox kdsat;
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
    private widget.TextBox nmptg;
    private widget.TextBox nmsat;
    private widget.TextBox nmsup;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi3;
    private widget.panelisi panelisi4;
    private widget.ScrollPane scrollPane1;
    private widget.Table tbDokter;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
       Valid.tabelKosong(tabMode);
        try{   
            ps=koneksi.prepareStatement(
                    "select pemesanan.tgl_pesan,pemesanan.no_faktur, "+
                    "pemesanan.kode_suplier,datasuplier.nama_suplier, "+
                    "pemesanan.nip,petugas.nama,bangsal.nm_bangsal,pemesanan.tgl_faktur, "+
                    "pemesanan.tgl_tempo,pemesanan.status,pemesanan.total2,pemesanan.ppn,"+
                    "pemesanan.meterai,pemesanan.tagihan,pemesanan.no_order,detailpesan.kode_brng,databarang.nama_brng, "+
                    "detailpesan.kode_sat,kodesatuan.satuan,detailpesan.jumlah,detailpesan.h_pesan, "+
                    "detailpesan.subtotal,detailpesan.dis,detailpesan.besardis,detailpesan.total,"+
                    "detailpesan.no_batch,industrifarmasi.nama_industri,detailpesan.kadaluarsa "+
                    " from pemesanan inner join datasuplier inner join petugas inner join bangsal  "+
                    " inner join detailpesan inner join databarang inner join kodesatuan "+
                    " inner join jenis inner join industrifarmasi "+
                    " on detailpesan.kode_brng=databarang.kode_brng "+
                    " and detailpesan.kode_sat=kodesatuan.kode_sat "+
                    " and pemesanan.kd_bangsal=bangsal.kd_bangsal "+
                    " and pemesanan.no_faktur=detailpesan.no_faktur "+
                    " and pemesanan.kode_suplier=datasuplier.kode_suplier "+
                    " and databarang.kode_industri=industrifarmasi.kode_industri "+
                    " and pemesanan.nip=petugas.nip and databarang.kdjns=jenis.kdjns"+
                    " where pemesanan.tgl_pesan between ? and ? and pemesanan.no_faktur like ? and datasuplier.nama_suplier like ? and petugas.nama like ?  and jenis.nama like ? and databarang.nama_brng like ? and industrifarmasi.nama_industri like ? and pemesanan.no_faktur like ? or "+
                    " pemesanan.tgl_pesan between ? and ? and pemesanan.no_faktur like ? and datasuplier.nama_suplier like ? and petugas.nama like ?  and jenis.nama like ? and databarang.nama_brng like ? and industrifarmasi.nama_industri like ? and pemesanan.kode_suplier like ? or "+
                    " pemesanan.tgl_pesan between ? and ? and pemesanan.no_faktur like ? and datasuplier.nama_suplier like ? and petugas.nama like ?  and jenis.nama like ? and databarang.nama_brng like ? and industrifarmasi.nama_industri like ? and datasuplier.nama_suplier like ? or "+
                    " pemesanan.tgl_pesan between ? and ? and pemesanan.no_faktur like ? and datasuplier.nama_suplier like ? and petugas.nama like ?  and jenis.nama like ? and databarang.nama_brng like ? and industrifarmasi.nama_industri like ? and pemesanan.nip like ? or "+
                    " pemesanan.tgl_pesan between ? and ? and pemesanan.no_faktur like ? and datasuplier.nama_suplier like ? and petugas.nama like ?  and jenis.nama like ? and databarang.nama_brng like ? and industrifarmasi.nama_industri like ? and petugas.nama like ? or "+
                    " pemesanan.tgl_pesan between ? and ? and pemesanan.no_faktur like ? and datasuplier.nama_suplier like ? and petugas.nama like ?  and jenis.nama like ? and databarang.nama_brng like ? and industrifarmasi.nama_industri like ? and bangsal.nm_bangsal like ? or "+
                    " pemesanan.tgl_pesan between ? and ? and pemesanan.no_faktur like ? and datasuplier.nama_suplier like ? and petugas.nama like ?  and jenis.nama like ? and databarang.nama_brng like ? and industrifarmasi.nama_industri like ? and detailpesan.kode_brng like ? or "+
                    " pemesanan.tgl_pesan between ? and ? and pemesanan.no_faktur like ? and datasuplier.nama_suplier like ? and petugas.nama like ?  and jenis.nama like ? and databarang.nama_brng like ? and industrifarmasi.nama_industri like ? and databarang.nama_brng like ? or "+
                    " pemesanan.tgl_pesan between ? and ? and pemesanan.no_faktur like ? and datasuplier.nama_suplier like ? and petugas.nama like ?  and jenis.nama like ? and databarang.nama_brng like ? and industrifarmasi.nama_industri like ? and detailpesan.kode_sat like ? or "+
                    " pemesanan.tgl_pesan between ? and ? and pemesanan.no_faktur like ? and datasuplier.nama_suplier like ? and petugas.nama like ?  and jenis.nama like ? and databarang.nama_brng like ? and industrifarmasi.nama_industri like ? and detailpesan.no_batch like ? or "+
                    " pemesanan.tgl_pesan between ? and ? and pemesanan.no_faktur like ? and datasuplier.nama_suplier like ? and petugas.nama like ?  and jenis.nama like ? and databarang.nama_brng like ? and industrifarmasi.nama_industri like ? and industrifarmasi.nama_industri like ? or "+
                    " pemesanan.tgl_pesan between ? and ? and pemesanan.no_faktur like ? and datasuplier.nama_suplier like ? and petugas.nama like ?  and jenis.nama like ? and databarang.nama_brng like ? and industrifarmasi.nama_industri like ? and pemesanan.no_order like ? or "+
                    " pemesanan.tgl_pesan between ? and ? and pemesanan.no_faktur like ? and datasuplier.nama_suplier like ? and petugas.nama like ?  and jenis.nama like ? and databarang.nama_brng like ? and industrifarmasi.nama_industri like ? and jenis.nama like ? "+
                    " order by pemesanan.tgl_pesan,pemesanan.no_faktur ");
            try {
                ps.setString(1,Valid.SetTgl(TglBeli1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(TglBeli2.getSelectedItem()+""));
                ps.setString(3,"%"+NoFaktur.getText()+"%");
                ps.setString(4,"%"+nmsup.getText()+"%");
                ps.setString(5,"%"+nmptg.getText()+"%");
                ps.setString(6,"%"+nmsat.getText()+"%");
                ps.setString(7,"%"+nmbar.getText()+"%");
                ps.setString(8,"%"+NmIF.getText()+"%");
                ps.setString(9,"%"+TCari.getText()+"%");
                ps.setString(10,Valid.SetTgl(TglBeli1.getSelectedItem()+""));
                ps.setString(11,Valid.SetTgl(TglBeli2.getSelectedItem()+""));
                ps.setString(12,"%"+NoFaktur.getText()+"%");
                ps.setString(13,"%"+nmsup.getText()+"%");
                ps.setString(14,"%"+nmptg.getText()+"%");
                ps.setString(15,"%"+nmsat.getText()+"%");
                ps.setString(16,"%"+nmbar.getText()+"%");
                ps.setString(17,"%"+NmIF.getText()+"%");
                ps.setString(18,"%"+TCari.getText()+"%");
                ps.setString(19,Valid.SetTgl(TglBeli1.getSelectedItem()+""));
                ps.setString(20,Valid.SetTgl(TglBeli2.getSelectedItem()+""));
                ps.setString(21,"%"+NoFaktur.getText()+"%");
                ps.setString(22,"%"+nmsup.getText()+"%");
                ps.setString(23,"%"+nmptg.getText()+"%");
                ps.setString(24,"%"+nmsat.getText()+"%");
                ps.setString(25,"%"+nmbar.getText()+"%");
                ps.setString(26,"%"+NmIF.getText()+"%");
                ps.setString(27,"%"+TCari.getText()+"%");
                ps.setString(28,Valid.SetTgl(TglBeli1.getSelectedItem()+""));
                ps.setString(29,Valid.SetTgl(TglBeli2.getSelectedItem()+""));
                ps.setString(30,"%"+NoFaktur.getText()+"%");
                ps.setString(31,"%"+nmsup.getText()+"%");
                ps.setString(32,"%"+nmptg.getText()+"%");
                ps.setString(33,"%"+nmsat.getText()+"%");
                ps.setString(34,"%"+nmbar.getText()+"%");
                ps.setString(35,"%"+NmIF.getText()+"%");
                ps.setString(36,"%"+TCari.getText()+"%");
                ps.setString(37,Valid.SetTgl(TglBeli1.getSelectedItem()+""));
                ps.setString(38,Valid.SetTgl(TglBeli2.getSelectedItem()+""));
                ps.setString(39,"%"+NoFaktur.getText()+"%");
                ps.setString(40,"%"+nmsup.getText()+"%");
                ps.setString(41,"%"+nmptg.getText()+"%");
                ps.setString(42,"%"+nmsat.getText()+"%");
                ps.setString(43,"%"+nmbar.getText()+"%");
                ps.setString(44,"%"+NmIF.getText()+"%");
                ps.setString(45,"%"+TCari.getText()+"%");
                ps.setString(46,Valid.SetTgl(TglBeli1.getSelectedItem()+""));
                ps.setString(47,Valid.SetTgl(TglBeli2.getSelectedItem()+""));
                ps.setString(48,"%"+NoFaktur.getText()+"%");
                ps.setString(49,"%"+nmsup.getText()+"%");
                ps.setString(50,"%"+nmptg.getText()+"%");
                ps.setString(51,"%"+nmsat.getText()+"%");
                ps.setString(52,"%"+nmbar.getText()+"%");
                ps.setString(53,"%"+NmIF.getText()+"%");
                ps.setString(54,"%"+TCari.getText()+"%");
                ps.setString(55,Valid.SetTgl(TglBeli1.getSelectedItem()+""));
                ps.setString(56,Valid.SetTgl(TglBeli2.getSelectedItem()+""));
                ps.setString(57,"%"+NoFaktur.getText()+"%");
                ps.setString(58,"%"+nmsup.getText()+"%");
                ps.setString(59,"%"+nmptg.getText()+"%");
                ps.setString(60,"%"+nmsat.getText()+"%");
                ps.setString(61,"%"+nmbar.getText()+"%");
                ps.setString(62,"%"+NmIF.getText()+"%");
                ps.setString(63,"%"+TCari.getText()+"%");
                ps.setString(64,Valid.SetTgl(TglBeli1.getSelectedItem()+""));
                ps.setString(65,Valid.SetTgl(TglBeli2.getSelectedItem()+""));
                ps.setString(66,"%"+NoFaktur.getText()+"%");
                ps.setString(67,"%"+nmsup.getText()+"%");
                ps.setString(68,"%"+nmptg.getText()+"%");
                ps.setString(69,"%"+nmsat.getText()+"%");
                ps.setString(70,"%"+nmbar.getText()+"%");
                ps.setString(71,"%"+NmIF.getText()+"%");
                ps.setString(72,"%"+TCari.getText()+"%");
                ps.setString(73,Valid.SetTgl(TglBeli1.getSelectedItem()+""));
                ps.setString(74,Valid.SetTgl(TglBeli2.getSelectedItem()+""));
                ps.setString(75,"%"+NoFaktur.getText()+"%");
                ps.setString(76,"%"+nmsup.getText()+"%");
                ps.setString(77,"%"+nmptg.getText()+"%");
                ps.setString(78,"%"+nmsat.getText()+"%");
                ps.setString(79,"%"+nmbar.getText()+"%");
                ps.setString(80,"%"+NmIF.getText()+"%");
                ps.setString(81,"%"+TCari.getText()+"%");
                ps.setString(82,Valid.SetTgl(TglBeli1.getSelectedItem()+""));
                ps.setString(83,Valid.SetTgl(TglBeli2.getSelectedItem()+""));
                ps.setString(84,"%"+NoFaktur.getText()+"%");
                ps.setString(85,"%"+nmsup.getText()+"%");
                ps.setString(86,"%"+nmptg.getText()+"%");
                ps.setString(87,"%"+nmsat.getText()+"%");
                ps.setString(88,"%"+nmbar.getText()+"%");
                ps.setString(89,"%"+NmIF.getText()+"%");
                ps.setString(90,"%"+TCari.getText()+"%");
                ps.setString(91,Valid.SetTgl(TglBeli1.getSelectedItem()+""));
                ps.setString(92,Valid.SetTgl(TglBeli2.getSelectedItem()+""));
                ps.setString(93,"%"+NoFaktur.getText()+"%");
                ps.setString(94,"%"+nmsup.getText()+"%");
                ps.setString(95,"%"+nmptg.getText()+"%");
                ps.setString(96,"%"+nmsat.getText()+"%");
                ps.setString(97,"%"+nmbar.getText()+"%");
                ps.setString(98,"%"+NmIF.getText()+"%");
                ps.setString(99,"%"+TCari.getText()+"%");
                ps.setString(100,Valid.SetTgl(TglBeli1.getSelectedItem()+""));
                ps.setString(101,Valid.SetTgl(TglBeli2.getSelectedItem()+""));
                ps.setString(102,"%"+NoFaktur.getText()+"%");
                ps.setString(103,"%"+nmsup.getText()+"%");
                ps.setString(104,"%"+nmptg.getText()+"%");
                ps.setString(105,"%"+nmsat.getText()+"%");
                ps.setString(106,"%"+nmbar.getText()+"%");
                ps.setString(107,"%"+NmIF.getText()+"%");
                ps.setString(108,"%"+TCari.getText()+"%");
                ps.setString(109,Valid.SetTgl(TglBeli1.getSelectedItem()+""));
                ps.setString(110,Valid.SetTgl(TglBeli2.getSelectedItem()+""));
                ps.setString(111,"%"+NoFaktur.getText()+"%");
                ps.setString(112,"%"+nmsup.getText()+"%");
                ps.setString(113,"%"+nmptg.getText()+"%");
                ps.setString(114,"%"+nmsat.getText()+"%");
                ps.setString(115,"%"+nmbar.getText()+"%");
                ps.setString(116,"%"+NmIF.getText()+"%");
                ps.setString(117,"%"+TCari.getText()+"%");
                rs=ps.executeQuery();
                tagihan=0;
                subtotal=0;
                diskon=0;
                while(rs.next()){
                    tabMode.addRow(new Object[]{
                        rs.getString("no_faktur"),rs.getString("no_order"),rs.getString("kode_suplier")+" "+rs.getString("nama_suplier"),
                        rs.getString("nip")+" "+rs.getString("nama"),rs.getString("tgl_pesan"),rs.getString("tgl_faktur"),
                        rs.getString("tgl_tempo"),rs.getString("kadaluarsa"),rs.getString("no_batch"),rs.getString("kode_brng")+" "+rs.getString("nama_brng"),
                        rs.getString("satuan"),rs.getDouble("jumlah"),rs.getDouble("h_pesan"),rs.getDouble("subtotal"),rs.getDouble("dis"),
                        rs.getDouble("besardis"),rs.getDouble("total"),rs.getString("status")
                    });  
                    tagihan=tagihan+rs.getDouble("total");
                    subtotal=subtotal+rs.getDouble("subtotal");
                    diskon=diskon+rs.getDouble("besardis");
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
            if(tagihan>0){
                tabMode.addRow(new Object[]{
                    "","Total :","","","","","","","","","",null,null,
                    subtotal,null,diskon,tagihan,null
                }); 
            }
                 
            LTotal.setText(Valid.SetAngka(tagihan));
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }        
    }

    public void emptTeks() {
        kdbar.setText("");
        nmbar.setText("");
        kdsat.setText("");
        kdbar.requestFocus();        
    }
    
    public void isCek(){
        BtnPrint.setEnabled(akses.getrekap_pemesanan());
    }
    
}

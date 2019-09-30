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
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import keuangan.Jurnal;
import kepegawaian.DlgCariPetugas;

public class DlgCariReturPiutang extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Jurnal jur=new Jurnal();
    private riwayatobat Trackobat=new riwayatobat();
    private Dimension screen=Toolkit.getDefaultToolkit().getScreenSize();
    public DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    public DlgBarang barang=new DlgBarang(null,false);
    private double ttlretur=0,subtotal=0;
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement ps,ps2;
    private ResultSet rs,rs2;
    private String tanggal="",noret="",ptg="",sat="",bar="",nonot="";  private String aktifkanbatch="no";
    private final Properties prop = new Properties();

    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public DlgCariReturPiutang(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        try {
            prop.loadFromXML(new FileInputStream("setting/database.xml"));   
            aktifkanbatch = prop.getProperty("AKTIFKANBATCHOBAT");
        } catch (Exception e) {
            System.out.println("E : "+e);
            aktifkanbatch = "no";
        }
        
        Object[] row={"No.Retur",
                    "Tgl.Retur",
                    "Petugas",
                    "Pasien",
                    "No.Nota",
                    "Barang",
                    "Satuan",
                    "Harga Retur(Rp)",
                    "Jml",
                    "SubTotal(Rp)"};
        tabMode=new DefaultTableModel(null,row){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbRetur.setModel(tabMode);

        tbRetur.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbRetur.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 10; i++) {
            TableColumn column = tbRetur.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(90);
            }else if(i==1){
                column.setPreferredWidth(80);
            }else if(i==2){
                column.setPreferredWidth(140);
            }else if(i==3){
                column.setPreferredWidth(140);
            }else if(i==4){
                column.setPreferredWidth(90);
            }else if(i==5){
                column.setPreferredWidth(220);
            }else if(i==6){
                column.setPreferredWidth(60);
            }else if(i==7){
                column.setPreferredWidth(100);
            }else if(i==8){
                column.setPreferredWidth(35);
            }else if(i==9){
                column.setPreferredWidth(100);
            }
        }
        tbRetur.setDefaultRenderer(Object.class, new WarnaTable());

        NoRetur.setDocument(new batasInput((byte)25).getKata(NoRetur));
        NoNota.setDocument(new batasInput((byte)25).getKata(NoNota));
        Kdptg.setDocument(new batasInput((byte)25).getKata(Kdptg));    
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
        petugas.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgCariReturPiutang")){
                    if(petugas.getTable().getSelectedRow()!= -1){                   
                        Kdptg.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                        Nmptg.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                    }  
                    Kdptg.requestFocus();
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
                if(akses.getform().equals("DlgCariReturPiutang")){
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
                if(akses.getform().equals("DlgCariReturPiutang")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        barang.dispose();
                    }                
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        barang.satuan.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgCariReturPiutang")){
                    if(barang.satuan.getTable().getSelectedRow()!= -1){
                        kdsat.setText(barang.satuan.getTable().getValueAt(barang.satuan.getTable().getSelectedRow(),0).toString());
                        nmsat.setText(barang.satuan.getTable().getValueAt(barang.satuan.getTable().getSelectedRow(),1).toString());
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
        tbRetur = new widget.Table();
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
        label24 = new widget.Label();
        kdsat = new widget.TextBox();
        nmsat = new widget.TextBox();
        btnSatuan = new widget.Button();
        label17 = new widget.Label();
        kdbar = new widget.TextBox();
        nmbar = new widget.TextBox();
        btnBarang = new widget.Button();
        panelisipiutang = new widget.panelisi();
        label15 = new widget.Label();
        NoRetur = new widget.TextBox();
        label18 = new widget.Label();
        NoNota = new widget.TextBox();
        label13 = new widget.Label();
        Kdptg = new widget.TextBox();
        Nmptg = new widget.TextBox();
        btnPetugas = new widget.Button();
        label11 = new widget.Label();
        TglRetur1 = new widget.Tanggal();
        label12 = new widget.Label();
        TglRetur2 = new widget.Tanggal();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        ppHapus.setBackground(new java.awt.Color(255, 255, 254));
        ppHapus.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppHapus.setForeground(new java.awt.Color(50,50,50));
        ppHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppHapus.setText("Hapus Piutang");
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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Cari Retur Piutang Obat, Alkes & BHP Medis ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50,50,50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        scrollPane1.setComponentPopupMenu(jPopupMenu1);
        scrollPane1.setName("scrollPane1"); // NOI18N
        scrollPane1.setOpaque(true);

        tbRetur.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbRetur.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbRetur.setComponentPopupMenu(jPopupMenu1);
        tbRetur.setName("tbRetur"); // NOI18N
        scrollPane1.setViewportView(tbRetur);

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
        BtnCari.setMnemonic('4');
        BtnCari.setToolTipText("Alt+4");
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

        label24.setText("Satuan :");
        label24.setName("label24"); // NOI18N
        label24.setPreferredSize(new java.awt.Dimension(68, 23));
        panelisi4.add(label24);
        label24.setBounds(0, 10, 70, 23);

        kdsat.setName("kdsat"); // NOI18N
        kdsat.setPreferredSize(new java.awt.Dimension(80, 23));
        kdsat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdsatKeyPressed(evt);
            }
        });
        panelisi4.add(kdsat);
        kdsat.setBounds(75, 10, 53, 23);

        nmsat.setName("nmsat"); // NOI18N
        nmsat.setPreferredSize(new java.awt.Dimension(80, 23));
        nmsat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nmsatKeyPressed(evt);
            }
        });
        panelisi4.add(nmsat);
        nmsat.setBounds(130, 10, 115, 23);

        btnSatuan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnSatuan.setMnemonic('2');
        btnSatuan.setToolTipText("Alt+2");
        btnSatuan.setName("btnSatuan"); // NOI18N
        btnSatuan.setPreferredSize(new java.awt.Dimension(28, 23));
        btnSatuan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSatuanActionPerformed(evt);
            }
        });
        panelisi4.add(btnSatuan);
        btnSatuan.setBounds(248, 10, 28, 23);

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
        btnBarang.setMnemonic('3');
        btnBarang.setToolTipText("Alt+3");
        btnBarang.setName("btnBarang"); // NOI18N
        btnBarang.setPreferredSize(new java.awt.Dimension(28, 23));
        btnBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBarangActionPerformed(evt);
            }
        });
        panelisi4.add(btnBarang);
        btnBarang.setBounds(734, 10, 28, 23);

        jPanel1.add(panelisi4, java.awt.BorderLayout.CENTER);

        internalFrame1.add(jPanel1, java.awt.BorderLayout.PAGE_END);

        panelisipiutang.setName("panelisipiutang"); // NOI18N
        panelisipiutang.setPreferredSize(new java.awt.Dimension(100, 73));
        panelisipiutang.setLayout(null);

        label15.setText("No.Retur :");
        label15.setName("label15"); // NOI18N
        label15.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisipiutang.add(label15);
        label15.setBounds(0, 10, 70, 23);

        NoRetur.setName("NoRetur"); // NOI18N
        NoRetur.setPreferredSize(new java.awt.Dimension(207, 23));
        NoRetur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoReturKeyPressed(evt);
            }
        });
        panelisipiutang.add(NoRetur);
        NoRetur.setBounds(75, 10, 160, 23);

        label18.setText("No.Nota :");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisipiutang.add(label18);
        label18.setBounds(0, 40, 70, 23);

        NoNota.setName("NoNota"); // NOI18N
        NoNota.setPreferredSize(new java.awt.Dimension(207, 23));
        NoNota.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoNotaKeyPressed(evt);
            }
        });
        panelisipiutang.add(NoNota);
        NoNota.setBounds(75, 40, 160, 23);

        label13.setText("Petugas :");
        label13.setName("label13"); // NOI18N
        label13.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisipiutang.add(label13);
        label13.setBounds(320, 10, 55, 23);

        Kdptg.setName("Kdptg"); // NOI18N
        Kdptg.setPreferredSize(new java.awt.Dimension(80, 23));
        Kdptg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdptgKeyPressed(evt);
            }
        });
        panelisipiutang.add(Kdptg);
        Kdptg.setBounds(379, 10, 100, 23);

        Nmptg.setEditable(false);
        Nmptg.setName("Nmptg"); // NOI18N
        Nmptg.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisipiutang.add(Nmptg);
        Nmptg.setBounds(481, 10, 250, 23);

        btnPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPetugas.setMnemonic('1');
        btnPetugas.setToolTipText("Alt+1");
        btnPetugas.setName("btnPetugas"); // NOI18N
        btnPetugas.setPreferredSize(new java.awt.Dimension(28, 23));
        btnPetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPetugasActionPerformed(evt);
            }
        });
        panelisipiutang.add(btnPetugas);
        btnPetugas.setBounds(734, 10, 28, 23);

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisipiutang.add(label11);
        label11.setBounds(320, 40, 55, 23);

        TglRetur1.setDisplayFormat("dd-MM-yyyy");
        TglRetur1.setName("TglRetur1"); // NOI18N
        TglRetur1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglRetur1KeyPressed(evt);
            }
        });
        panelisipiutang.add(TglRetur1);
        TglRetur1.setBounds(379, 40, 120, 23);

        label12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label12.setText("s.d.");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisipiutang.add(label12);
        label12.setBounds(500, 40, 27, 23);

        TglRetur2.setDisplayFormat("dd-MM-yyyy");
        TglRetur2.setName("TglRetur2"); // NOI18N
        TglRetur2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglRetur2KeyPressed(evt);
            }
        });
        panelisipiutang.add(TglRetur2);
        TglRetur2.setBounds(526, 40, 120, 23);

        internalFrame1.add(panelisipiutang, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tbDokterKeyPressed(java.awt.event.KeyEvent evt) {                                    
        
}

    private void btnPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPetugasActionPerformed
        akses.setform("DlgCariReturPiutang");
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setAlwaysOnTop(false);
        petugas.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnPetugasActionPerformed

    private void NoReturKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoReturKeyPressed
        Valid.pindah(evt, TCari, TglRetur1);
    }//GEN-LAST:event_NoReturKeyPressed

    private void KdptgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdptgKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama from petugas where nip=?",Nmptg,Kdptg.getText());           
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select nama from petugas where nip=?",Nmptg,Kdptg.getText());      
            TglRetur1.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select nama from petugas where nip=?",Nmptg,Kdptg.getText());      
            NoNota.requestFocus(); 
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnPetugasActionPerformed(null);
        }
    }//GEN-LAST:event_KdptgKeyPressed

    private void NoNotaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoNotaKeyPressed
            Valid.pindah(evt, NoRetur, Kdptg);
    }//GEN-LAST:event_NoNotaKeyPressed

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
        NoNota.setText("");
        kdbar.setText("");
        nmbar.setText("");
        kdsat.setText("");
        nmsat.setText("");
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
                                tabMode.getValueAt(i,4).toString()+"','"+
                                tabMode.getValueAt(i,5).toString()+"','"+
                                tabMode.getValueAt(i,6).toString()+"','"+
                                tabMode.getValueAt(i,7).toString()+"','"+
                                tabMode.getValueAt(i,8).toString()+"','"+
                                tabMode.getValueAt(i,9).toString()+"','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Transaksi Retur Piutang"); 
            }
            Sequel.menyimpan("temporary","'0','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Transaksi Retur Piutang"); 
            Sequel.menyimpan("temporary","'0','Jml.Total :','','','','','','','',' ','"+LTotal.getText()+"','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Transaksi Retur Piutang"); 
            
            Map<String, Object> param = new HashMap<>();  
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("emailrs",akses.getemailrs());   
                param.put("logo",Sequel.cariGambar("select logo from setting")); 
            Valid.MyReport("rptReturPiutang.jasper","report","::[ Transaksi Retur Piutang ]::",param);
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

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnPrint,kdbar);}
    }//GEN-LAST:event_BtnKeluarKeyPressed

    private void kdsatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdsatKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select satuan from kodesatuan where kode_sat=?",nmsat,kdsat.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select satuan from kodesatuan where kode_sat=?",nmsat,kdsat.getText());
            Kdptg.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select satuan from kodesatuan where kode_sat=?",nmsat,kdsat.getText());
            kdbar.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnSatuanActionPerformed(null);
        }
    }//GEN-LAST:event_kdsatKeyPressed

    private void nmsatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nmsatKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_nmsatKeyPressed

    private void btnSatuanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSatuanActionPerformed
        akses.setform("DlgCariReturPiutang");
        barang.satuan.emptTeks();
        barang.satuan.isCek();
        barang.satuan.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        barang.satuan.setLocationRelativeTo(internalFrame1);
        barang.satuan.setAlwaysOnTop(false);
        barang.satuan.setVisible(true);
    }//GEN-LAST:event_btnSatuanActionPerformed

    private void kdbarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdbarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama_brng from databarang where kode_brng=?", nmbar);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select nama_brng from databarang where kode_brng=?", nmbar);
            kdsat.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select nama_brng from databarang where kode_brng=?", nmbar);
            TCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnPetugasActionPerformed(null);
        }
    }//GEN-LAST:event_kdbarKeyPressed

    private void btnBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBarangActionPerformed
        akses.setform("DlgCariReturPiutang");
        barang.emptTeks();
        barang.isCek();
        barang.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        barang.setLocationRelativeTo(internalFrame1);
        barang.setAlwaysOnTop(false);
        barang.setVisible(true);
    }//GEN-LAST:event_btnBarangActionPerformed

    private void TglRetur1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglRetur1KeyPressed
        Valid.pindah(evt,NoNota,Kdptg);
    }//GEN-LAST:event_TglRetur1KeyPressed

    private void TglRetur2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglRetur2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglRetur2KeyPressed

private void ppHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppHapusActionPerformed
  if(tbRetur.getValueAt(tbRetur.getSelectedRow(),0).toString().trim().equals("")){
      Valid.textKosong(TCari,"No.Faktur");
  }else{
     try {
         ps=koneksi.prepareStatement("select no_retur_piutang, kd_bangsal from returpiutang where no_retur_piutang=?");
         try {
            ps.setString(1,tbRetur.getValueAt(tbRetur.getSelectedRow(),0).toString());
            rs=ps.executeQuery();
            if(rs.next()){
                ps2=koneksi.prepareStatement("select kode_brng,jml_retur,no_batch from detreturpiutang where no_retur_piutang=? ");
                try {
                    ps2.setString(1,rs.getString(1));
                    rs2=ps2.executeQuery();
                    while(rs2.next()){
                        Trackobat.catatRiwayat(rs2.getString("kode_brng"),0,rs2.getDouble("jml_retur"),"Retur Piutang",akses.getkode(),rs.getString("kd_bangsal"),"Hapus");
                        Sequel.menyimpan("gudangbarang","'"+rs2.getString("kode_brng") +"','"+rs.getString("kd_bangsal") +"','-"+rs2.getString("jml_retur") +"'", 
                                               "stok=stok-'"+rs2.getString("jml_retur") +"'","kode_brng='"+rs2.getString("kode_brng")+"' and kd_bangsal='"+rs.getString("kd_bangsal") +"'");
                        if(aktifkanbatch.equals("yes")){
                            Sequel.mengedit("data_batch","no_batch=? and kode_brng=?","sisa=sisa-?",3,new String[]{
                                rs2.getString("jml_retur"),rs2.getString("no_batch"),rs2.getString("kode_brng")
                            });
                        } 
                    }
                } catch (Exception e) {
                    System.out.println("Notif rs2 : "+e);
                } finally{
                    if(rs2!=null){
                        rs2.close();
                    }
                    if(ps2!=null){
                        ps2.close();
                    }
                }
                Sequel.menyimpan("tampjurnal","'"+Sequel.cariIsi("select Retur_Piutang_Obat from set_akun")+"','RETUR PIUTANG','0','"+Sequel.cariIsi("select sum(subtotal) from detreturpiutang where no_retur_piutang='"+rs.getString("no_retur_piutang")+"'")+"'","Rekening");    
                Sequel.menyimpan("tampjurnal","'"+Sequel.cariIsi("select Kontra_Retur_Piutang_Obat from set_akun")+"','KAS DI TANGAN','"+Sequel.cariIsi("select sum(subtotal) from detreturpiutang where no_retur_piutang='"+rs.getString("no_retur_piutang")+"'")+"','0'","Rekening"); 
                jur.simpanJurnal(rs.getString(1),Sequel.cariIsi("select current_date()"),"U","BATAL RETUR PENJUALAN DI "+Sequel.cariIsi("select nm_bangsal from bangsal where kd_bangsal='"+rs.getString("kd_bangsal")+"'").toUpperCase()+", OLEH "+akses.getkode());
            }          
            Sequel.queryu("delete from returpiutang where no_retur_piutang='"+tbRetur.getValueAt(tbRetur.getSelectedRow(),0).toString()+"'");
            tampil();
         } catch (Exception e) {
             System.out.println("Notif Retur Piutang : "+e);
         } finally{
             if(rs!=null){
                 rs.close();
             }
             if(ps!=null){
                 ps.close();
             }
         }
     } catch (SQLException ex) {
         System.out.println(ex);
     }      
  }       
    
}//GEN-LAST:event_ppHapusActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgCariReturPiutang dialog = new DlgCariReturPiutang(new javax.swing.JFrame(), true);
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
    private widget.TextBox Kdptg;
    private widget.Label LTotal;
    private widget.TextBox Nmptg;
    private widget.TextBox NoNota;
    private widget.TextBox NoRetur;
    private widget.TextBox TCari;
    private widget.Tanggal TglRetur1;
    private widget.Tanggal TglRetur2;
    private widget.Button btnBarang;
    private widget.Button btnPetugas;
    private widget.Button btnSatuan;
    private widget.InternalFrame internalFrame1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.TextBox kdbar;
    private widget.TextBox kdsat;
    private widget.Label label10;
    private widget.Label label11;
    private widget.Label label12;
    private widget.Label label13;
    private widget.Label label15;
    private widget.Label label17;
    private widget.Label label18;
    private widget.Label label24;
    private widget.Label label9;
    private widget.TextBox nmbar;
    private widget.TextBox nmsat;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi4;
    private widget.panelisi panelisipiutang;
    private javax.swing.JMenuItem ppHapus;
    private widget.ScrollPane scrollPane1;
    private widget.Table tbRetur;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        tanggal=" returpiutang.tgl_retur between '"+Valid.SetTgl(TglRetur1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(TglRetur2.getSelectedItem()+"")+"' ";
        noret="";ptg="";sat="";bar="";     
        if(!NoRetur.getText().equals("")){
            noret=" and returpiutang.no_retur_piutang='"+NoNota.getText()+"' ";
        } 
        if(!Nmptg.getText().equals("")){
            ptg=" and petugas.nama='"+Nmptg.getText()+"' ";
        }
        if(!nmsat.getText().equals("")){
            sat=" and kodesatuan.satuan='"+nmsat.getText()+"' ";
        }
        if(!nmbar.getText().equals("")){
            bar=" and databarang.nama_brng='"+nmbar.getText()+"' ";
        }
        Valid.tabelKosong(tabMode);
        try{
            ps=koneksi.prepareStatement("select returpiutang.no_retur_piutang,returpiutang.tgl_retur, "+
                    "returpiutang.nip,petugas.nama,pasien.no_rkm_medis,pasien.nm_pasien,bangsal.nm_bangsal "+
                    " from returpiutang inner join petugas inner join pasien inner join bangsal "+
                    " inner join detreturpiutang inner join databarang inner join kodesatuan "+
                    " on detreturpiutang.kode_brng=databarang.kode_brng "+
                    " and returpiutang.kd_bangsal=bangsal.kd_bangsal "+
                    " and returpiutang.no_rkm_medis=pasien.no_rkm_medis "+
                    " and detreturpiutang.kode_sat=kodesatuan.kode_sat "+
                    " and returpiutang.no_retur_piutang=detreturpiutang.no_retur_piutang "+
                    " and returpiutang.nip=petugas.nip "+
                    " where "+tanggal+noret+ptg+sat+bar+" and returpiutang.no_retur_piutang like '%"+TCari.getText()+"%' or "+
                    tanggal+noret+ptg+sat+bar+" and returpiutang.nip like '%"+TCari.getText()+"%' or "+
                    tanggal+noret+ptg+sat+bar+" and petugas.nama like '%"+TCari.getText()+"%' or "+
                    tanggal+noret+ptg+sat+bar+" and detreturpiutang.kode_brng like '%"+TCari.getText()+"%' or "+
                    tanggal+noret+ptg+sat+bar+" and databarang.nama_brng like '%"+TCari.getText()+"%' or "+
                    tanggal+noret+ptg+sat+bar+" and bangsal.nm_bangsal like '%"+TCari.getText()+"%' or "+
                    tanggal+noret+ptg+sat+bar+" and pasien.no_rkm_medis like '%"+TCari.getText()+"%' or "+
                    tanggal+noret+ptg+sat+bar+" and pasien.nm_pasien like '%"+TCari.getText()+"%' or "+
                    tanggal+noret+ptg+sat+bar+" and detreturpiutang.nota_piutang like '%"+TCari.getText()+"%' or "+
                    tanggal+noret+ptg+sat+bar+" and kodesatuan.satuan like '%"+TCari.getText()+"%' or "+
                    tanggal+noret+ptg+sat+bar+" and detreturpiutang.kode_sat like '%"+TCari.getText()+"%' "+
                    " group by returpiutang.no_retur_piutang order by returpiutang.tgl_retur,returpiutang.no_retur_piutang ");
            try {
                ttlretur=0;
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new Object[]{
                        rs.getString(1),rs.getString(2),rs.getString(3)+", "+rs.getString(4),
                        rs.getString(5)+", "+rs.getString(6),"Retur Piutang :","di "+rs.getString(7),"","","",""
                    });
                    sat="";bar="";nonot="";
                    if(!nmsat.getText().equals("")){
                        sat=" and kodesatuan.satuan='"+nmsat.getText()+"' ";
                    }    
                    if(!nmbar.getText().equals("")){
                        bar=" and databarang.nama_brng='"+nmbar.getText()+"' ";
                    }
                    if(!NoNota.getText().equals("")){
                        nonot=" and detreturpiutang.nota_piutang='"+NoNota.getText()+"' ";
                    }
                    ps2=koneksi.prepareStatement("select detreturpiutang.nota_piutang,detreturpiutang.kode_brng,databarang.nama_brng, "+
                            "detreturpiutang.kode_sat,kodesatuan.satuan,detreturpiutang.h_retur,detreturpiutang.jml_retur, "+
                            "detreturpiutang.subtotal,detreturpiutang.no_batch from detreturpiutang inner join databarang inner join kodesatuan "+
                            " on detreturpiutang.kode_brng=databarang.kode_brng "+
                            " and detreturpiutang.kode_sat=kodesatuan.kode_sat where "+
                            " detreturpiutang.no_retur_piutang='"+rs.getString(1)+"' "+sat+bar+nonot+" and detreturpiutang.kode_brng like '%"+TCari.getText()+"%' or "+
                            " detreturpiutang.no_retur_piutang='"+rs.getString(1)+"' "+sat+bar+nonot+" and databarang.nama_brng like '%"+TCari.getText()+"%' or "+
                            " detreturpiutang.no_retur_piutang='"+rs.getString(1)+"' "+sat+bar+nonot+" and detreturpiutang.nota_piutang like '%"+TCari.getText()+"%' or "+
                            " detreturpiutang.no_retur_piutang='"+rs.getString(1)+"' "+sat+bar+nonot+" and detreturpiutang.kode_sat like '%"+TCari.getText()+"%' or "+
                            " detreturpiutang.no_retur_piutang='"+rs.getString(1)+"' "+sat+bar+nonot+" and detreturpiutang.nota_piutang like '%"+TCari.getText()+"%' or "+
                            " detreturpiutang.no_retur_piutang='"+rs.getString(1)+"' "+sat+bar+nonot+" and kodesatuan.satuan like '%"+TCari.getText()+"%' order by detreturpiutang.kode_brng  ");
                    try {
                        rs2=ps2.executeQuery();
                        subtotal=0;
                        int no=1;
                        while(rs2.next()){
                            ttlretur=ttlretur+rs2.getDouble(8);
                            subtotal=subtotal+rs2.getDouble(8);
                            tabMode.addRow(new Object[]{
                                "","","",no+". No.Batch "+rs2.getString("no_batch"),rs2.getString(1),rs2.getString(2)+", "+rs2.getString(3),
                                rs2.getString(4)+", "+rs2.getString(5),
                                Valid.SetAngka(rs2.getDouble(6)),rs2.getString(7),Valid.SetAngka(rs2.getDouble(8))
                            });
                            no++;
                        }
                        tabMode.addRow(new Object[]{"Total Retur :","","","","","","","","",Valid.SetAngka(subtotal)});                
                    } catch (Exception e) {
                        System.out.println("Notif Detail Retur "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                }                 
                LTotal.setText(Valid.SetAngka(ttlretur));
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

    public void isCek(){
        BtnPrint.setEnabled(akses.getretur_piutang_pasien());
        if(akses.getkode().equals("Admin Utama")){
            ppHapus.setEnabled(true);
        }else{
            ppHapus.setEnabled(false);
        }  
    }
}

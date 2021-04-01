package inventory;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
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
import kepegawaian.DlgCariPetugas;

public class DlgRekapPenerimaan extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    public  InventoryCariSuplier suplier=new InventoryCariSuplier(null,false);
    public  DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    public  DlgBarang barang=new DlgBarang(null,false);
    private PreparedStatement ps;
    private ResultSet rs;
    private double tagihan=0,subtotal=0,diskon;
    private String pilihan="";
    private StringBuilder htmlContent;
    private int i;

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

        for (i = 0; i < 18; i++) {
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

        kdsup.setDocument(new batasInput((byte)5).getKata(kdsup));
        kdptg.setDocument(new batasInput((byte)25).getKata(kdptg));
        kdbar.setDocument(new batasInput((byte)15).getKata(kdbar));
        KdIF.setDocument(new batasInput((byte)15).getKata(KdIF));
        kdJenis.setDocument(new batasInput((byte)4).getKata(kdJenis));
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
                        kdJenis.setText(barang.jenis.getTable().getValueAt(barang.jenis.getTable().getSelectedRow(),0).toString());                    
                        nmjenis.setText(barang.jenis.getTable().getValueAt(barang.jenis.getTable().getSelectedRow(),1).toString());                        
                    }                    
                    kdJenis.requestFocus();
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
        
        barang.kategori.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgRekapPenerimaan")){
                    if(barang.kategori.getTable().getSelectedRow()!= -1){                          
                        kdkategori.setText(barang.kategori.getTable().getValueAt(barang.kategori.getTable().getSelectedRow(),0).toString());                    
                        nmkategori.setText(barang.kategori.getTable().getValueAt(barang.kategori.getTable().getSelectedRow(),1).toString());                        
                    }                    
                    kdkategori.requestFocus();
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
        
        barang.golongan.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgRekapPenerimaan")){
                    if(barang.golongan.getTable().getSelectedRow()!= -1){                          
                        kdgolongan.setText(barang.golongan.getTable().getValueAt(barang.golongan.getTable().getSelectedRow(),0).toString());                    
                        nmgolongan.setText(barang.golongan.getTable().getValueAt(barang.golongan.getTable().getSelectedRow(),1).toString());                        
                    }                    
                    kdgolongan.requestFocus();
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
        label11 = new widget.Label();
        TglBeli1 = new widget.Tanggal();
        TglBeli2 = new widget.Tanggal();
        label12 = new widget.Label();
        PanelInput = new javax.swing.JPanel();
        FormInput = new widget.PanelBiasa();
        label16 = new widget.Label();
        label13 = new widget.Label();
        kdsup = new widget.TextBox();
        kdptg = new widget.TextBox();
        nmsup = new widget.TextBox();
        nmptg = new widget.TextBox();
        btnSuplier = new widget.Button();
        btnPetugas = new widget.Button();
        label14 = new widget.Label();
        KdIF = new widget.TextBox();
        NmIF = new widget.TextBox();
        btnIF = new widget.Button();
        label24 = new widget.Label();
        kdJenis = new widget.TextBox();
        nmjenis = new widget.TextBox();
        btnJenis = new widget.Button();
        btnKategori = new widget.Button();
        nmkategori = new widget.TextBox();
        kdkategori = new widget.TextBox();
        label25 = new widget.Label();
        label26 = new widget.Label();
        kdgolongan = new widget.TextBox();
        nmgolongan = new widget.TextBox();
        BtnGolongan = new widget.Button();
        ChkInput = new widget.CekBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Rekap Penerimaan Obat, Alkes & BHP Medis ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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

        kdbar.setEditable(false);
        kdbar.setName("kdbar"); // NOI18N
        kdbar.setPreferredSize(new java.awt.Dimension(80, 23));
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

        label11.setText("Tgl.Datang :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi4.add(label11);
        label11.setBounds(0, 10, 80, 23);

        TglBeli1.setDisplayFormat("dd-MM-yyyy");
        TglBeli1.setName("TglBeli1"); // NOI18N
        TglBeli1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglBeli1KeyPressed(evt);
            }
        });
        panelisi4.add(TglBeli1);
        TglBeli1.setBounds(84, 10, 95, 23);

        TglBeli2.setDisplayFormat("dd-MM-yyyy");
        TglBeli2.setName("TglBeli2"); // NOI18N
        TglBeli2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglBeli2KeyPressed(evt);
            }
        });
        panelisi4.add(TglBeli2);
        TglBeli2.setBounds(208, 10, 95, 23);

        label12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label12.setText("s.d.");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi4.add(label12);
        label12.setBounds(179, 10, 27, 23);

        jPanel1.add(panelisi4, java.awt.BorderLayout.CENTER);

        internalFrame1.add(jPanel1, java.awt.BorderLayout.PAGE_END);

        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setPreferredSize(new java.awt.Dimension(660, 125));
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(660, 107));
        FormInput.setLayout(null);

        label16.setText("Supplier :");
        label16.setName("label16"); // NOI18N
        label16.setPreferredSize(new java.awt.Dimension(60, 23));
        FormInput.add(label16);
        label16.setBounds(325, 10, 80, 23);

        label13.setText("Petugas :");
        label13.setName("label13"); // NOI18N
        label13.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label13);
        label13.setBounds(325, 40, 80, 23);

        kdsup.setEditable(false);
        kdsup.setName("kdsup"); // NOI18N
        kdsup.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput.add(kdsup);
        kdsup.setBounds(409, 10, 80, 23);

        kdptg.setEditable(false);
        kdptg.setName("kdptg"); // NOI18N
        kdptg.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput.add(kdptg);
        kdptg.setBounds(409, 40, 80, 23);

        nmsup.setEditable(false);
        nmsup.setName("nmsup"); // NOI18N
        nmsup.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(nmsup);
        nmsup.setBounds(491, 10, 240, 23);

        nmptg.setEditable(false);
        nmptg.setName("nmptg"); // NOI18N
        nmptg.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(nmptg);
        nmptg.setBounds(491, 40, 240, 23);

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

        label14.setText("Ind. Farmasi :");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label14);
        label14.setBounds(325, 70, 80, 23);

        KdIF.setEditable(false);
        KdIF.setName("KdIF"); // NOI18N
        KdIF.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput.add(KdIF);
        KdIF.setBounds(409, 70, 80, 23);

        NmIF.setEditable(false);
        NmIF.setName("NmIF"); // NOI18N
        NmIF.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmIF);
        NmIF.setBounds(491, 70, 240, 23);

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
        FormInput.add(btnIF);
        btnIF.setBounds(734, 70, 28, 23);

        label24.setText("Jenis :");
        label24.setName("label24"); // NOI18N
        label24.setPreferredSize(new java.awt.Dimension(68, 23));
        FormInput.add(label24);
        label24.setBounds(0, 10, 70, 23);

        kdJenis.setEditable(false);
        kdJenis.setName("kdJenis"); // NOI18N
        kdJenis.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput.add(kdJenis);
        kdJenis.setBounds(74, 10, 53, 23);

        nmjenis.setEditable(false);
        nmjenis.setName("nmjenis"); // NOI18N
        nmjenis.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput.add(nmjenis);
        nmjenis.setBounds(129, 10, 134, 23);

        btnJenis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnJenis.setMnemonic('3');
        btnJenis.setToolTipText("Alt+3");
        btnJenis.setName("btnJenis"); // NOI18N
        btnJenis.setPreferredSize(new java.awt.Dimension(28, 23));
        btnJenis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnJenisActionPerformed(evt);
            }
        });
        FormInput.add(btnJenis);
        btnJenis.setBounds(265, 10, 28, 23);

        btnKategori.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnKategori.setMnemonic('3');
        btnKategori.setToolTipText("Alt+3");
        btnKategori.setName("btnKategori"); // NOI18N
        btnKategori.setPreferredSize(new java.awt.Dimension(28, 23));
        btnKategori.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKategoriActionPerformed(evt);
            }
        });
        FormInput.add(btnKategori);
        btnKategori.setBounds(265, 40, 28, 23);

        nmkategori.setEditable(false);
        nmkategori.setName("nmkategori"); // NOI18N
        nmkategori.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput.add(nmkategori);
        nmkategori.setBounds(129, 40, 134, 23);

        kdkategori.setEditable(false);
        kdkategori.setName("kdkategori"); // NOI18N
        kdkategori.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput.add(kdkategori);
        kdkategori.setBounds(74, 40, 53, 23);

        label25.setText("Kategori :");
        label25.setName("label25"); // NOI18N
        label25.setPreferredSize(new java.awt.Dimension(68, 23));
        FormInput.add(label25);
        label25.setBounds(0, 40, 70, 23);

        label26.setText("Golongan :");
        label26.setName("label26"); // NOI18N
        label26.setPreferredSize(new java.awt.Dimension(68, 23));
        FormInput.add(label26);
        label26.setBounds(0, 70, 70, 23);

        kdgolongan.setEditable(false);
        kdgolongan.setName("kdgolongan"); // NOI18N
        kdgolongan.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput.add(kdgolongan);
        kdgolongan.setBounds(74, 70, 53, 23);

        nmgolongan.setEditable(false);
        nmgolongan.setName("nmgolongan"); // NOI18N
        nmgolongan.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput.add(nmgolongan);
        nmgolongan.setBounds(129, 70, 134, 23);

        BtnGolongan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnGolongan.setMnemonic('3');
        BtnGolongan.setToolTipText("Alt+3");
        BtnGolongan.setName("BtnGolongan"); // NOI18N
        BtnGolongan.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnGolongan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnGolonganActionPerformed(evt);
            }
        });
        FormInput.add(BtnGolongan);
        BtnGolongan.setBounds(265, 70, 28, 23);

        PanelInput.add(FormInput, java.awt.BorderLayout.CENTER);

        ChkInput.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput.setMnemonic('I');
        ChkInput.setText(".: Filter Data");
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
        Valid.pindah(evt,KdIF,kdsup);
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

    private void btnJenisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnJenisActionPerformed
        akses.setform("DlgRekapPenerimaan");
        barang.jenis.emptTeks();
        barang.jenis.isCek();
        barang.jenis.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        barang.jenis.setLocationRelativeTo(internalFrame1);
        barang.jenis.setAlwaysOnTop(false);
        barang.jenis.setVisible(true);
    }//GEN-LAST:event_btnJenisActionPerformed

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
        kdbar.setText("");
        nmbar.setText("");
        kdJenis.setText("");
        nmjenis.setText("");
        kdkategori.setText("");
        nmkategori.setText("");
        kdgolongan.setText("");
        nmgolongan.setText("");
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
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            TCari.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            try {            
                File g = new File("file2.css");            
                BufferedWriter bg = new BufferedWriter(new FileWriter(g));
                bg.write(
                        ".isi td{border-right: 1px solid #e2e7dd;font: 11px tahoma;height:12px;border-bottom: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                        ".isi2 td{font: 11px tahoma;height:12px;background: #ffffff;color:#323232;}"+                    
                        ".isi3 td{border-right: 1px solid #e2e7dd;font: 11px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                        ".isi4 td{font: 11px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"
                );
                bg.close();

                File f;            
                BufferedWriter bw; 

                pilihan = (String)JOptionPane.showInputDialog(null,"Silahkan pilih laporan..!","Pilihan Cetak",JOptionPane.QUESTION_MESSAGE,null,new Object[]{"Laporan 1 (HTML)","Laporan 2 (WPS)","Laporan 3 (CSV)","Laporan 4 (Jasper)"},"Laporan 1 (HTML)");
                switch (pilihan) {
                    case "Laporan 1 (HTML)":
                            htmlContent = new StringBuilder();
                            htmlContent.append(                             
                                "<tr class='isi'>"+
                                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='5%'>No.Faktur</td>"+
                                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='5%'>No.Order</td>"+
                                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='9%'>Suplier</td>"+
                                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='9%'>Petugas</td>"+
                                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='4%'>Tgl.Pesan</td>"+
                                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='4%'>Tgl.Faktur</td>"+
                                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='4%'>Jatuh Tempo</td>"+
                                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='4%'>Kadaluarsa</td>"+
                                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='4%'>No.Batch</td>"+
                                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='12%'>Barang</td>"+
                                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='3%'>Satuan</td>"+
                                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='2%'>Jml.Beli</td>"+
                                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='7%'>Harga Beli(Rp)</td>"+
                                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='7%'>SubTotal(Rp)</td>"+
                                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='3%'>Disk(%)</td>"+
                                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='5%'>Bsr.Disk(Rp)</td>"+
                                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='7%'>Total(Rp)</td>"+
                                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='6%'>Status Bayar</td>"+
                                "</tr>"
                            ); 
                            for(i=0;i<tabMode.getRowCount();i++){  
                                htmlContent.append(                             
                                    "<tr class='isi'>"+
                                        "<td valign='top'>"+tabMode.getValueAt(i,0)+"</td>"+
                                        "<td valign='top'>"+tabMode.getValueAt(i,1)+"</td>"+
                                        "<td valign='top'>"+tabMode.getValueAt(i,2)+"</td>"+
                                        "<td valign='top'>"+tabMode.getValueAt(i,3)+"</td>"+
                                        "<td valign='top' align='center'>"+tabMode.getValueAt(i,4)+"</td>"+
                                        "<td valign='top' align='center'>"+tabMode.getValueAt(i,5)+"</td>"+
                                        "<td valign='top' align='center'>"+tabMode.getValueAt(i,6)+"</td>"+
                                        "<td valign='top' align='center'>"+tabMode.getValueAt(i,7)+"</td>"+
                                        "<td valign='top'>"+tabMode.getValueAt(i,8)+"</td>"+
                                        "<td valign='top'>"+tabMode.getValueAt(i,9)+"</td>"+
                                        "<td valign='top' align='center'>"+tabMode.getValueAt(i,10)+"</td>"+
                                        "<td valign='top' align='center'>"+tabMode.getValueAt(i,11)+"</td>"+
                                        "<td valign='top' align='right'>"+tabMode.getValueAt(i,12)+"</td>"+
                                        "<td valign='top' align='right'>"+tabMode.getValueAt(i,13)+"</td>"+
                                        "<td valign='top' align='right'>"+tabMode.getValueAt(i,14)+"</td>"+
                                        "<td valign='top' align='right'>"+tabMode.getValueAt(i,15)+"</td>"+
                                        "<td valign='top' align='right'>"+tabMode.getValueAt(i,16)+"</td>"+
                                        "<td valign='top' align='center'>"+tabMode.getValueAt(i,17)+"</td>"+
                                    "</tr>"
                                ); 
                            }            

                            f = new File("RekapPenerimaanObatBHP.html");            
                            bw = new BufferedWriter(new FileWriter(f));            
                            bw.write("<html>"+
                                        "<head><link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" /></head>"+
                                        "<body>"+
                                            "<table width='2000px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                                "<tr class='isi2'>"+
                                                    "<td valign='top' align='center'>"+
                                                        "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                                        akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                                        akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                                        "<font size='2' face='Tahoma'>REKAP PENERIMAAN OBAT, ALKES & BHP PERIODE "+TglBeli1.getSelectedItem()+" s.d. "+TglBeli2.getSelectedItem()+"<br><br></font>"+        
                                                    "</td>"+
                                               "</tr>"+
                                            "</table>"+
                                            "<table width='2000px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                                htmlContent.toString()+
                                            "</table>"+
                                        "</body>"+                   
                                     "</html>"
                            );

                            bw.close();                         
                            Desktop.getDesktop().browse(f.toURI());
                        break;
                    case "Laporan 2 (WPS)":
                            htmlContent = new StringBuilder();
                            htmlContent.append(                             
                                "<tr class='isi'>"+
                                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='5%'>No.Faktur</td>"+
                                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='5%'>No.Order</td>"+
                                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='9%'>Suplier</td>"+
                                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='9%'>Petugas</td>"+
                                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='4%'>Tgl.Pesan</td>"+
                                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='4%'>Tgl.Faktur</td>"+
                                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='4%'>Jatuh Tempo</td>"+
                                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='4%'>Kadaluarsa</td>"+
                                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='4%'>No.Batch</td>"+
                                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='12%'>Barang</td>"+
                                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='3%'>Satuan</td>"+
                                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='2%'>Jml.Beli</td>"+
                                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='7%'>Harga Beli(Rp)</td>"+
                                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='7%'>SubTotal(Rp)</td>"+
                                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='3%'>Disk(%)</td>"+
                                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='5%'>Bsr.Disk(Rp)</td>"+
                                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='7%'>Total(Rp)</td>"+
                                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='6%'>Status Bayar</td>"+
                                "</tr>"
                            ); 
                            for(i=0;i<tabMode.getRowCount();i++){  
                                htmlContent.append(                             
                                    "<tr class='isi'>"+
                                        "<td valign='top'>"+tabMode.getValueAt(i,0)+"</td>"+
                                        "<td valign='top'>"+tabMode.getValueAt(i,1)+"</td>"+
                                        "<td valign='top'>"+tabMode.getValueAt(i,2)+"</td>"+
                                        "<td valign='top'>"+tabMode.getValueAt(i,3)+"</td>"+
                                        "<td valign='top' align='center'>"+tabMode.getValueAt(i,4)+"</td>"+
                                        "<td valign='top' align='center'>"+tabMode.getValueAt(i,5)+"</td>"+
                                        "<td valign='top' align='center'>"+tabMode.getValueAt(i,6)+"</td>"+
                                        "<td valign='top' align='center'>"+tabMode.getValueAt(i,7)+"</td>"+
                                        "<td valign='top'>"+tabMode.getValueAt(i,8)+"</td>"+
                                        "<td valign='top'>"+tabMode.getValueAt(i,9)+"</td>"+
                                        "<td valign='top' align='center'>"+tabMode.getValueAt(i,10)+"</td>"+
                                        "<td valign='top' align='center'>"+tabMode.getValueAt(i,11)+"</td>"+
                                        "<td valign='top' align='right'>"+tabMode.getValueAt(i,12)+"</td>"+
                                        "<td valign='top' align='right'>"+tabMode.getValueAt(i,13)+"</td>"+
                                        "<td valign='top' align='right'>"+tabMode.getValueAt(i,14)+"</td>"+
                                        "<td valign='top' align='right'>"+tabMode.getValueAt(i,15)+"</td>"+
                                        "<td valign='top' align='right'>"+tabMode.getValueAt(i,16)+"</td>"+
                                        "<td valign='top' align='center'>"+tabMode.getValueAt(i,17)+"</td>"+
                                    "</tr>"
                                ); 
                            }          

                            f = new File("RekapPenerimaanObatBHP.wps");            
                            bw = new BufferedWriter(new FileWriter(f));            
                            bw.write("<html>"+
                                        "<head><link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" /></head>"+
                                        "<body>"+
                                            "<table width='2000px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                                "<tr class='isi2'>"+
                                                    "<td valign='top' align='center'>"+
                                                        "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                                        akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                                        akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                                        "<font size='2' face='Tahoma'>DETAIL JM DOKTER PERIODE "+TglBeli1.getSelectedItem()+" s.d. "+TglBeli2.getSelectedItem()+"<br><br></font>"+        
                                                    "</td>"+
                                               "</tr>"+
                                            "</table>"+
                                            "<table width='2000px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                                htmlContent.toString()+
                                            "</table>"+
                                        "</body>"+                   
                                     "</html>"
                            );

                            bw.close();                         
                            Desktop.getDesktop().browse(f.toURI());
                        break;
                    case "Laporan 3 (CSV)":
                            htmlContent = new StringBuilder();
                            htmlContent.append(                             
                                "\"No.Faktur\";\"No.Order\";\"Suplier\";\"Petugas\";\"Tgl.Pesan\";\"Tgl.Faktur\";\"Jatuh Tempo\";\"Kadaluarsa\";\"No.Batch\";\"Barang\";\"Satuan\";\"Jml.Beli\";\"Harga Beli(Rp)\";\"SubTotal(Rp)\";\"Disk(%)\";\"Bsr.Disk(Rp)\";\"Total(Rp)\";\"Status Bayar\"\n"
                            ); 
                            for(i=0;i<tabMode.getRowCount();i++){  
                                htmlContent.append(                             
                                    "\""+tabMode.getValueAt(i,0)+"\";\""+tabMode.getValueAt(i,1)+"\";\""+tabMode.getValueAt(i,2)+"\";\""+tabMode.getValueAt(i,3)+"\";\""+tabMode.getValueAt(i,4)+"\";\""+tabMode.getValueAt(i,5)+"\";\""+tabMode.getValueAt(i,6)+"\";\""+tabMode.getValueAt(i,7)+"\";\""+tabMode.getValueAt(i,8)+"\";\""+tabMode.getValueAt(i,9)+"\";\""+tabMode.getValueAt(i,10)+"\";\""+tabMode.getValueAt(i,11)+"\";\""+tabMode.getValueAt(i,12)+"\";\""+tabMode.getValueAt(i,13)+"\";\""+tabMode.getValueAt(i,14)+"\";\""+tabMode.getValueAt(i,15)+"\";\""+tabMode.getValueAt(i,16)+"\";\""+tabMode.getValueAt(i,17)+"\"\n"
                                ); 
                            }            

                            f = new File("RekapPenerimaanObatBHP.csv");            
                            bw = new BufferedWriter(new FileWriter(f));            
                            bw.write(htmlContent.toString());

                            bw.close();                         
                            Desktop.getDesktop().browse(f.toURI());
                        break; 
                    case "Laporan 4 (Jasper)":
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
                                "if(detailpesan.no_batch='','-',detailpesan.no_batch) as no_batch,industrifarmasi.nama_industri,detailpesan.kadaluarsa "+
                                " from pemesanan inner join datasuplier on pemesanan.kode_suplier=datasuplier.kode_suplier "+
                                " inner join petugas on pemesanan.nip=petugas.nip "+
                                " inner join bangsal on pemesanan.kd_bangsal=bangsal.kd_bangsal "+
                                " inner join detailpesan on pemesanan.no_faktur=detailpesan.no_faktur "+
                                " inner join databarang on detailpesan.kode_brng=databarang.kode_brng "+
                                " inner join kodesatuan on detailpesan.kode_sat=kodesatuan.kode_sat "+
                                " inner join jenis on databarang.kdjns=jenis.kdjns "+
                                " inner join industrifarmasi on databarang.kode_industri=industrifarmasi.kode_industri "+
                                " inner join golongan_barang on databarang.kode_golongan=golongan_barang.kode "+
                                " inner join kategori_barang on databarang.kode_kategori=kategori_barang.kode "+
                                " where pemesanan.tgl_pesan between '"+Valid.SetTgl(TglBeli1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(TglBeli2.getSelectedItem()+"")+"' and datasuplier.nama_suplier like '%"+nmsup.getText()+"%' and petugas.nama like '%"+nmptg.getText()+"%'  and jenis.nama like '%"+nmjenis.getText()+"%' and "+
                                " databarang.nama_brng like '%"+nmbar.getText()+"%' and industrifarmasi.nama_industri like '%"+NmIF.getText()+"%' and kategori_barang.nama like '%"+nmkategori.getText()+"%' and golongan_barang.nama like '%"+nmgolongan.getText()+"%' and "+
                                " (pemesanan.no_faktur like '%"+TCari.getText().trim()+"%' or pemesanan.kode_suplier like '%"+TCari.getText().trim()+"%' or datasuplier.nama_suplier like '%"+TCari.getText().trim()+"%' or pemesanan.nip like '%"+TCari.getText().trim()+"%' or petugas.nama like '%"+TCari.getText().trim()+"%' or bangsal.nm_bangsal like '%"+TCari.getText().trim()+"%' or detailpesan.kode_brng like '%"+TCari.getText().trim()+"%' or "+
                                " databarang.nama_brng like '%"+TCari.getText().trim()+"%' or detailpesan.kode_sat like '%"+TCari.getText().trim()+"%' or detailpesan.no_batch like '%"+TCari.getText().trim()+"%' or industrifarmasi.nama_industri like '%"+TCari.getText().trim()+"%' or pemesanan.no_order like '%"+TCari.getText().trim()+"%') "+
                                " order by pemesanan.tgl_pesan,pemesanan.no_faktur ",param);
                        break; 
                }                 
            } catch (Exception e) {
            }     
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt,BtnAll,BtnAll);
        }
    }//GEN-LAST:event_BtnPrintKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void btnIFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIFActionPerformed
        akses.setform("DlgRekapPenerimaan");
        barang.industri.emptTeks();
        barang.industri.isCek();
        barang.industri.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        barang.industri.setLocationRelativeTo(internalFrame1);
        barang.industri.setAlwaysOnTop(false);
        barang.industri.setVisible(true);
    }//GEN-LAST:event_btnIFActionPerformed

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void btnKategoriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKategoriActionPerformed
        akses.setform("DlgRekapPenerimaan");
        barang.kategori.emptTeks();
        barang.kategori.isCek();
        barang.kategori.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        barang.kategori.setLocationRelativeTo(internalFrame1);
        barang.kategori.setAlwaysOnTop(false);
        barang.kategori.setVisible(true);
    }//GEN-LAST:event_btnKategoriActionPerformed

    private void BtnGolonganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnGolonganActionPerformed
        akses.setform("DlgRekapPenerimaan");
        barang.golongan.emptTeks();
        barang.golongan.isCek();
        barang.golongan.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        barang.golongan.setLocationRelativeTo(internalFrame1);
        barang.golongan.setAlwaysOnTop(false);
        barang.golongan.setVisible(true);
    }//GEN-LAST:event_BtnGolonganActionPerformed

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
    private widget.Button BtnGolongan;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.CekBox ChkInput;
    private widget.PanelBiasa FormInput;
    private widget.TextBox KdIF;
    private widget.Label LTotal;
    private widget.TextBox NmIF;
    private javax.swing.JPanel PanelInput;
    private widget.TextBox TCari;
    private widget.Tanggal TglBeli1;
    private widget.Tanggal TglBeli2;
    private widget.Button btnBarang;
    private widget.Button btnIF;
    private widget.Button btnJenis;
    private widget.Button btnKategori;
    private widget.Button btnPetugas;
    private widget.Button btnSuplier;
    private widget.InternalFrame internalFrame1;
    private javax.swing.JPanel jPanel1;
    private widget.TextBox kdJenis;
    private widget.TextBox kdbar;
    private widget.TextBox kdgolongan;
    private widget.TextBox kdkategori;
    private widget.TextBox kdptg;
    private widget.TextBox kdsup;
    private widget.Label label10;
    private widget.Label label11;
    private widget.Label label12;
    private widget.Label label13;
    private widget.Label label14;
    private widget.Label label16;
    private widget.Label label17;
    private widget.Label label24;
    private widget.Label label25;
    private widget.Label label26;
    private widget.Label label9;
    private widget.TextBox nmbar;
    private widget.TextBox nmgolongan;
    private widget.TextBox nmjenis;
    private widget.TextBox nmkategori;
    private widget.TextBox nmptg;
    private widget.TextBox nmsup;
    private widget.panelisi panelisi1;
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
                    "detailpesan.no_batch,industrifarmasi.nama_industri,if(detailpesan.kadaluarsa='0000-00-00','-',detailpesan.kadaluarsa) as kadaluarsa  "+
                    " from pemesanan inner join datasuplier on pemesanan.kode_suplier=datasuplier.kode_suplier "+
                    " inner join petugas on pemesanan.nip=petugas.nip "+
                    " inner join bangsal on pemesanan.kd_bangsal=bangsal.kd_bangsal "+
                    " inner join detailpesan on pemesanan.no_faktur=detailpesan.no_faktur "+
                    " inner join databarang on detailpesan.kode_brng=databarang.kode_brng "+
                    " inner join kodesatuan on detailpesan.kode_sat=kodesatuan.kode_sat "+
                    " inner join jenis on databarang.kdjns=jenis.kdjns "+
                    " inner join industrifarmasi on databarang.kode_industri=industrifarmasi.kode_industri "+
                    " inner join golongan_barang on databarang.kode_golongan=golongan_barang.kode "+
                    " inner join kategori_barang on databarang.kode_kategori=kategori_barang.kode "+
                    " where pemesanan.tgl_pesan between ? and ? and datasuplier.nama_suplier like ? and petugas.nama like ?  and jenis.nama like ? and "+
                    " databarang.nama_brng like ? and industrifarmasi.nama_industri like ? and kategori_barang.nama like ? and golongan_barang.nama like ? and "+
                    " (pemesanan.no_faktur like ? or pemesanan.kode_suplier like ? or datasuplier.nama_suplier like ? or pemesanan.nip like ? or petugas.nama like ? or bangsal.nm_bangsal like ? or detailpesan.kode_brng like ? or "+
                    " databarang.nama_brng like ? or detailpesan.kode_sat like ? or detailpesan.no_batch like ? or industrifarmasi.nama_industri like ? or pemesanan.no_order like ?) "+
                    " order by pemesanan.tgl_pesan,pemesanan.no_faktur ");
            try {
                ps.setString(1,Valid.SetTgl(TglBeli1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(TglBeli2.getSelectedItem()+""));
                ps.setString(3,"%"+nmsup.getText()+"%");
                ps.setString(4,"%"+nmptg.getText()+"%");
                ps.setString(5,"%"+nmjenis.getText()+"%");
                ps.setString(6,"%"+nmbar.getText()+"%");
                ps.setString(7,"%"+NmIF.getText()+"%");
                ps.setString(8,"%"+nmkategori.getText().trim()+"%");
                ps.setString(9,"%"+nmgolongan.getText().trim()+"%");
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
                ps.setString(21,"%"+TCari.getText()+"%");
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
        kdJenis.setText("");
        kdbar.requestFocus();        
    }
    
    public void isCek(){
        BtnPrint.setEnabled(akses.getrekap_pemesanan());
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,125));
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

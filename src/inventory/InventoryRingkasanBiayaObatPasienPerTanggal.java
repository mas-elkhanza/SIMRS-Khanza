package inventory;
import fungsi.WarnaTable;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import fungsi.batasInput;
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
import simrskhanza.DlgCariBangsal;
import simrskhanza.DlgCariCaraBayar;

public class InventoryRingkasanBiayaObatPasienPerTanggal extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement ps;
    private ResultSet rs; 
    private DlgCariCaraBayar penjab=new DlgCariCaraBayar(null,false);
    private DlgCariJenis jenis = new DlgCariJenis(null, false);
    private DlgCariKategori kategori = new DlgCariKategori(null, false);
    private DlgCariGolongan golongan = new DlgCariGolongan(null, false);
    private DlgCariBangsal asalstok=new DlgCariBangsal(null,false);
    private int i=0;
    private double total=0;
    private String statusobat="",carabayar="",bangsal="",jenisbarang="",kategoribarang="",golonganbarang="",unit="",pilihan="";
    private StringBuilder htmlContent;
    
    /** 
     * @param parent
     * @param modal */
    public InventoryRingkasanBiayaObatPasienPerTanggal(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        tabMode=new DefaultTableModel(null,new Object[]{
                "No.","Tanggal","No.Rawat","No.RM","Nama Pasien","Alamat","Cara Bayar","Unit/Layanan","Total"
            }){
            @Override 
            public boolean isCellEditable(int rowIndex, int colIndex){return false;}
            Class[] types = new Class[]{
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Double.class
            };
            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        tbDokter.setModel(tabMode);

        tbDokter.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0;i < 9; i++) {
            TableColumn column = tbDokter.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(35);
            }else if(i==1){
                column.setPreferredWidth(65);
            }else if(i==2){
                column.setPreferredWidth(105);
            }else if(i==3){
                column.setPreferredWidth(70);
            }else if(i==4){
                column.setPreferredWidth(170);
            }else if(i==5){
                column.setPreferredWidth(160);
            }else if(i==6){
                column.setPreferredWidth(100);
            }else if(i==7){
                column.setPreferredWidth(140);
            }else if(i==8){
                column.setPreferredWidth(90);
            }
        }
        tbDokter.setDefaultRenderer(Object.class, new WarnaTable());  
        
        penjab.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(penjab.getTable().getSelectedRow()!= -1){
                    kdpenjab.setText(penjab.getTable().getValueAt(penjab.getTable().getSelectedRow(),1).toString());
                    nmpenjab.setText(penjab.getTable().getValueAt(penjab.getTable().getSelectedRow(),2).toString());
                }      
                kdpenjab.requestFocus();
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {penjab.emptTeks();}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });   
        
        penjab.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    penjab.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        asalstok.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(asalstok.getTable().getSelectedRow()!= -1){                   
                    kdasal.setText(asalstok.getTable().getValueAt(asalstok.getTable().getSelectedRow(),0).toString());                    
                    nmasal.setText(asalstok.getTable().getValueAt(asalstok.getTable().getSelectedRow(),1).toString());
                }  
                kdasal.requestFocus();
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
        
        asalstok.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    asalstok.dispose();
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
                if (jenis.getTable().getSelectedRow() != -1) {
                    kdjenis.setText(jenis.getTable().getValueAt(jenis.getTable().getSelectedRow(), 0).toString());
                    nmjns.setText(jenis.getTable().getValueAt(jenis.getTable().getSelectedRow(), 1).toString());
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
        
        golongan.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if (golongan.getTable().getSelectedRow() != -1) {
                    kdgolongan.setText(golongan.getTable().getValueAt(golongan.getTable().getSelectedRow(), 0).toString());
                    nmgolongan.setText(golongan.getTable().getValueAt(golongan.getTable().getSelectedRow(), 1).toString());
                }
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {
                golongan.emptTeks();
            }
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
        
        kategori.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if (kategori.getTable().getSelectedRow() != -1) {
                    kdkategori.setText(kategori.getTable().getValueAt(kategori.getTable().getSelectedRow(), 0).toString());
                    nmkategori.setText(kategori.getTable().getValueAt(kategori.getTable().getSelectedRow(), 1).toString());
                }
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {
                kategori.emptTeks();
            }
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
        
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        if(koneksiDB.CARICEPAT().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        prosesCari();
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        prosesCari();
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        prosesCari();
                    }
                }
            });
        } 
        
        ChkInput.setSelected(false);
        isForm();
     
    }
    

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Kd2 = new widget.TextBox();
        kdasal = new widget.TextBox();
        kdpenjab = new widget.TextBox();
        kdjenis = new widget.TextBox();
        kdkategori = new widget.TextBox();
        kdgolongan = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        panelisi1 = new widget.panelisi();
        label11 = new widget.Label();
        Tgl1 = new widget.Tanggal();
        label18 = new widget.Label();
        Tgl2 = new widget.Tanggal();
        label9 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        jLabel7 = new widget.Label();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();
        PanelInput = new javax.swing.JPanel();
        ChkInput = new widget.CekBox();
        FormInput = new widget.panelisi();
        label17 = new widget.Label();
        label19 = new widget.Label();
        nmpenjab = new widget.TextBox();
        BtnSeek3 = new widget.Button();
        label20 = new widget.Label();
        nmasal = new widget.TextBox();
        BtnSeek4 = new widget.Button();
        label21 = new widget.Label();
        nmjns = new widget.TextBox();
        BtnJenis = new widget.Button();
        label22 = new widget.Label();
        nmkategori = new widget.TextBox();
        BtnKategori = new widget.Button();
        label23 = new widget.Label();
        nmgolongan = new widget.TextBox();
        BtnGolongan = new widget.Button();
        status = new widget.ComboBox();
        scrollPane4 = new widget.ScrollPane();
        tbDokter = new widget.Table();

        Kd2.setName("Kd2"); // NOI18N
        Kd2.setPreferredSize(new java.awt.Dimension(207, 23));

        kdasal.setEditable(false);
        kdasal.setName("kdasal"); // NOI18N
        kdasal.setPreferredSize(new java.awt.Dimension(75, 23));

        kdpenjab.setName("kdpenjab"); // NOI18N
        kdpenjab.setPreferredSize(new java.awt.Dimension(60, 23));

        kdjenis.setEditable(false);
        kdjenis.setName("kdjenis"); // NOI18N
        kdjenis.setPreferredSize(new java.awt.Dimension(75, 23));

        kdkategori.setEditable(false);
        kdkategori.setName("kdkategori"); // NOI18N
        kdkategori.setPreferredSize(new java.awt.Dimension(75, 23));

        kdgolongan.setEditable(false);
        kdgolongan.setName("kdgolongan"); // NOI18N
        kdgolongan.setPreferredSize(new java.awt.Dimension(75, 23));

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Ringkasan Biaya Obat Pasien Per Tanggal ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 56));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(55, 23));
        panelisi1.add(label11);

        Tgl1.setDisplayFormat("dd-MM-yyyy");
        Tgl1.setName("Tgl1"); // NOI18N
        Tgl1.setPreferredSize(new java.awt.Dimension(90, 23));
        Tgl1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Tgl1KeyPressed(evt);
            }
        });
        panelisi1.add(Tgl1);

        label18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label18.setText("s.d.");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(30, 23));
        panelisi1.add(label18);

        Tgl2.setDisplayFormat("dd-MM-yyyy");
        Tgl2.setName("Tgl2"); // NOI18N
        Tgl2.setPreferredSize(new java.awt.Dimension(90, 23));
        Tgl2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Tgl2KeyPressed(evt);
            }
        });
        panelisi1.add(Tgl2);

        label9.setText("Key Word :");
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(65, 23));
        panelisi1.add(label9);

        TCari.setToolTipText("Alt+C");
        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(135, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelisi1.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('2');
        BtnCari.setToolTipText("Alt+2");
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

        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(15, 23));
        panelisi1.add(jLabel7);

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

        internalFrame1.add(panelisi1, java.awt.BorderLayout.PAGE_END);

        PanelInput.setBackground(new java.awt.Color(255, 255, 255));
        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
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

        label17.setText("Status :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(37, 23));
        FormInput.add(label17);
        label17.setBounds(10, 10, 43, 23);

        label19.setText("Cara Bayar :");
        label19.setName("label19"); // NOI18N
        label19.setPreferredSize(new java.awt.Dimension(75, 23));
        FormInput.add(label19);
        label19.setBounds(265, 10, 65, 23);

        nmpenjab.setEditable(false);
        nmpenjab.setName("nmpenjab"); // NOI18N
        nmpenjab.setPreferredSize(new java.awt.Dimension(168, 23));
        FormInput.add(nmpenjab);
        nmpenjab.setBounds(333, 10, 150, 23);

        BtnSeek3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek3.setMnemonic('3');
        BtnSeek3.setToolTipText("Alt+3");
        BtnSeek3.setName("BtnSeek3"); // NOI18N
        BtnSeek3.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSeek3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek3ActionPerformed(evt);
            }
        });
        BtnSeek3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSeek3KeyPressed(evt);
            }
        });
        FormInput.add(BtnSeek3);
        BtnSeek3.setBounds(486, 10, 28, 23);

        label20.setText("Asal Stok :");
        label20.setName("label20"); // NOI18N
        label20.setPreferredSize(new java.awt.Dimension(35, 23));
        FormInput.add(label20);
        label20.setBounds(538, 10, 60, 23);

        nmasal.setEditable(false);
        nmasal.setName("nmasal"); // NOI18N
        nmasal.setPreferredSize(new java.awt.Dimension(215, 23));
        FormInput.add(nmasal);
        nmasal.setBounds(601, 10, 150, 23);

        BtnSeek4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek4.setMnemonic('3');
        BtnSeek4.setToolTipText("Alt+3");
        BtnSeek4.setName("BtnSeek4"); // NOI18N
        BtnSeek4.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSeek4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek4ActionPerformed(evt);
            }
        });
        BtnSeek4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSeek4KeyPressed(evt);
            }
        });
        FormInput.add(BtnSeek4);
        BtnSeek4.setBounds(754, 10, 28, 23);

        label21.setText("Jenis :");
        label21.setName("label21"); // NOI18N
        label21.setPreferredSize(new java.awt.Dimension(40, 23));
        FormInput.add(label21);
        label21.setBounds(10, 40, 43, 23);

        nmjns.setEditable(false);
        nmjns.setName("nmjns"); // NOI18N
        nmjns.setPreferredSize(new java.awt.Dimension(205, 23));
        FormInput.add(nmjns);
        nmjns.setBounds(56, 40, 150, 23);

        BtnJenis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnJenis.setMnemonic('2');
        BtnJenis.setToolTipText("Alt+2");
        BtnJenis.setName("BtnJenis"); // NOI18N
        BtnJenis.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnJenis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnJenisActionPerformed(evt);
            }
        });
        FormInput.add(BtnJenis);
        BtnJenis.setBounds(209, 40, 28, 23);

        label22.setText("Kategori :");
        label22.setName("label22"); // NOI18N
        label22.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label22);
        label22.setBounds(265, 40, 65, 23);

        nmkategori.setEditable(false);
        nmkategori.setName("nmkategori"); // NOI18N
        nmkategori.setPreferredSize(new java.awt.Dimension(205, 23));
        FormInput.add(nmkategori);
        nmkategori.setBounds(333, 40, 150, 23);

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
        FormInput.add(BtnKategori);
        BtnKategori.setBounds(486, 40, 28, 23);

        label23.setText("Golongan :");
        label23.setName("label23"); // NOI18N
        label23.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label23);
        label23.setBounds(538, 40, 60, 23);

        nmgolongan.setEditable(false);
        nmgolongan.setName("nmgolongan"); // NOI18N
        nmgolongan.setPreferredSize(new java.awt.Dimension(205, 23));
        FormInput.add(nmgolongan);
        nmgolongan.setBounds(601, 40, 150, 23);

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
        FormInput.add(BtnGolongan);
        BtnGolongan.setBounds(754, 40, 28, 23);

        status.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Semua Status", "Obat Rawat Jalan", "Obat Rawat Inap" }));
        status.setName("status"); // NOI18N
        FormInput.add(status);
        status.setBounds(56, 10, 181, 23);

        PanelInput.add(FormInput, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        scrollPane4.setName("scrollPane4"); // NOI18N
        scrollPane4.setOpaque(true);

        tbDokter.setAutoCreateRowSorter(true);
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
        scrollPane4.setViewportView(tbDokter);

        internalFrame1.add(scrollPane4, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents
/*
private void KdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdKeyPressed
    Valid.pindah(evt,BtnCari,Nm);
}//GEN-LAST:event_TKdKeyPressed
*/

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnPrint,Tgl1);}
    }//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        kdpenjab.setText("");
        nmpenjab.setText("");
        kdasal.setText("");
        nmasal.setText("");
        kdjenis.setText("");
        nmjns.setText("");
        kdkategori.setText("");
        nmkategori.setText("");
        kdgolongan.setText("");
        nmgolongan.setText("");
        TCari.setText("");
        status.setSelectedIndex(0);
        prosesCari();
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, status, BtnPrint);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        prosesCari();
}//GEN-LAST:event_BtnCariActionPerformed

private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, status, BtnAll);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    private void Tgl1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tgl1KeyPressed
        Valid.pindah(evt, BtnKeluar, Tgl2);
    }//GEN-LAST:event_Tgl1KeyPressed

    private void Tgl2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tgl2KeyPressed
        Valid.pindah(evt, Tgl1,status);
    }//GEN-LAST:event_Tgl2KeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        Tgl1.requestFocus();
    }//GEN-LAST:event_formWindowOpened

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void BtnSeek3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek3ActionPerformed
        penjab.isCek();
        penjab.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        penjab.setLocationRelativeTo(internalFrame1);
        penjab.setAlwaysOnTop(false);
        penjab.setVisible(true);
    }//GEN-LAST:event_BtnSeek3ActionPerformed

    private void BtnSeek3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeek3KeyPressed
        //Valid.pindah(evt,DTPCari2,TCari);
    }//GEN-LAST:event_BtnSeek3KeyPressed

    private void BtnSeek4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek4ActionPerformed
        asalstok.isCek();
        asalstok.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        asalstok.setLocationRelativeTo(internalFrame1);
        asalstok.setAlwaysOnTop(false);
        asalstok.setVisible(true);
    }//GEN-LAST:event_BtnSeek4ActionPerformed

    private void BtnSeek4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeek4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnSeek4KeyPressed

    private void BtnJenisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnJenisActionPerformed
        jenis.isCek();
        jenis.setSize(internalFrame1.getWidth() -20, internalFrame1.getHeight() -20);
        jenis.setLocationRelativeTo(internalFrame1);
        jenis.setAlwaysOnTop(false);
        jenis.setVisible(true);
    }//GEN-LAST:event_BtnJenisActionPerformed

    private void BtnKategoriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKategoriActionPerformed
        kategori.isCek();
        kategori.setSize(internalFrame1.getWidth() -20, internalFrame1.getHeight() -20);
        kategori.setLocationRelativeTo(internalFrame1);
        kategori.setAlwaysOnTop(false);
        kategori.setVisible(true);
    }//GEN-LAST:event_BtnKategoriActionPerformed

    private void BtnGolonganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnGolonganActionPerformed
        golongan.isCek();
        golongan.setSize(internalFrame1.getWidth() -20, internalFrame1.getHeight() -20);
        golongan.setLocationRelativeTo(internalFrame1);
        golongan.setAlwaysOnTop(false);
        golongan.setVisible(true);
    }//GEN-LAST:event_BtnGolonganActionPerformed

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

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnPrint.requestFocus();
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
                                "<td valign='middle' bgcolor='#FFFAFA' align='center' width='3%'>No.</td>"+
                                "<td valign='middle' bgcolor='#FFFAFA' align='center' width='8%'>Tanggal</td>"+
                                "<td valign='middle' bgcolor='#FFFAFA' align='center' width='10%'>No.Rawat</td>"+
                                "<td valign='middle' bgcolor='#FFFAFA' align='center' width='8%'>No.RM</td>"+
                                "<td valign='middle' bgcolor='#FFFAFA' align='center' width='17%'>Nama Pasien</td>"+
                                "<td valign='middle' bgcolor='#FFFAFA' align='center' width='18%'>Alamat</td>"+
                                "<td valign='middle' bgcolor='#FFFAFA' align='center' width='12%'>Cara Bayar</td>"+
                                "<td valign='middle' bgcolor='#FFFAFA' align='center' width='12%'>Unit/Layanan</td>"+
                                "<td valign='middle' bgcolor='#FFFAFA' align='center' width='10%'>Total</td>"+
                            "</tr>"
                        );
                        for(i=0;i<tabMode.getRowCount();i++){
                            htmlContent.append(
                                "<tr class='isi'>"+
                                    "<td valign='top'>"+tabMode.getValueAt(i,0)+"</td>"+
                                    "<td valign='top' align='center'>"+tabMode.getValueAt(i,1)+"</td>"+
                                    "<td valign='top' align='center'>"+tabMode.getValueAt(i,2)+"</td>"+
                                    "<td valign='top' align='center'>"+tabMode.getValueAt(i,3)+"</td>"+
                                    "<td valign='top'>"+tabMode.getValueAt(i,4)+"</td>"+
                                    "<td valign='top'>"+tabMode.getValueAt(i,5)+"</td>"+
                                    "<td valign='top' align='center'>"+tabMode.getValueAt(i,6)+"</td>"+
                                    "<td valign='top' align='center'>"+tabMode.getValueAt(i,7)+"</td>"+
                                    "<td valign='top' align='right'>"+Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(i,8).toString()))+"</td>"+
                                "</tr>"
                            );
                        }

                        f = new File("RingkasanBiayaObat.html");
                        bw = new BufferedWriter(new FileWriter(f));
                        bw.write("<html>"+
                            "<head><link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" /></head>"+
                                "<body>"+
                                    "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                        "<tr class='isi2'>"+
                                        "<td valign='top' align='center'>"+
                                        "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                        akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                        akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                        "<font size='2' face='Tahoma'>RINGKASAN BIAYA OBAT PASIEN PERIODE "+Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem()+"<br><br></font>"+
                                        "</td>"+
                                        "</tr>"+
                                    "</table>"+
                                    "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
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
                                "<td valign='middle' bgcolor='#FFFAFA' align='center' width='3%'>No.</td>"+
                                "<td valign='middle' bgcolor='#FFFAFA' align='center' width='8%'>Tanggal</td>"+
                                "<td valign='middle' bgcolor='#FFFAFA' align='center' width='10%'>No.Rawat</td>"+
                                "<td valign='middle' bgcolor='#FFFAFA' align='center' width='7%'>No.RM</td>"+
                                "<td valign='middle' bgcolor='#FFFAFA' align='center' width='18%'>Nama Pasien</td>"+
                                "<td valign='middle' bgcolor='#FFFAFA' align='center' width='18%'>Alamat</td>"+
                                "<td valign='middle' bgcolor='#FFFAFA' align='center' width='12%'>Cara Bayar</td>"+
                                "<td valign='middle' bgcolor='#FFFAFA' align='center' width='12%'>Unit/Layanan</td>"+
                                "<td valign='middle' bgcolor='#FFFAFA' align='center' width='10%'>Total</td>"+
                            "</tr>"
                        );
                        for(i=0;i<tabMode.getRowCount();i++){
                            htmlContent.append(
                                "<tr class='isi'>"+
                                    "<td valign='top'>"+tabMode.getValueAt(i,0)+"</td>"+
                                    "<td valign='top' align='center'>"+tabMode.getValueAt(i,1)+"</td>"+
                                    "<td valign='top' align='center'>"+tabMode.getValueAt(i,2)+"</td>"+
                                    "<td valign='top' align='center'>"+tabMode.getValueAt(i,3)+"</td>"+
                                    "<td valign='top'>"+tabMode.getValueAt(i,4)+"</td>"+
                                    "<td valign='top'>"+tabMode.getValueAt(i,5)+"</td>"+
                                    "<td valign='top' align='center'>"+tabMode.getValueAt(i,6)+"</td>"+
                                    "<td valign='top' align='center'>"+tabMode.getValueAt(i,7)+"</td>"+
                                    "<td valign='top' align='right'>"+Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(i,8).toString()))+"</td>"+
                                "</tr>"
                            );
                        }

                        f = new File("RingkasanBiayaObat.wps");
                        bw = new BufferedWriter(new FileWriter(f));
                        bw.write("<html>"+
                            "<head><link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" /></head>"+
                                "<body>"+
                                    "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                        "<tr class='isi2'>"+
                                        "<td valign='top' align='center'>"+
                                        "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                        akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                        akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                        "<font size='2' face='Tahoma'>RINGKASAN BIAYA OBAT PASIEN PERIODE "+Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem()+"<br><br></font>"+
                                        "</td>"+
                                        "</tr>"+
                                    "</table>"+
                                    "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
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
                        //"No.","Tanggal","No.Rawat","No.RM","Nama Pasien","Alamat","Cara Bayar","Unit/Layanan","Total"
                        htmlContent.append(
                            "\"No.\";\"Tanggal\";\"No.Rawat\";\"No.RM\";\"Nama Pasien\";\"Alamat\";\"Cara Bayar\";\"Unit/Layanan\";\"Total\"\n"
                        );
                        for(i=0;i<tabMode.getRowCount();i++){
                            htmlContent.append(
                                "\""+tabMode.getValueAt(i,0)+"\";\""+tabMode.getValueAt(i,1)+"\";\""+tabMode.getValueAt(i,2)+"\";\""+tabMode.getValueAt(i,3)+"\";\""+tabMode.getValueAt(i,4)+"\";"+
                                "\""+tabMode.getValueAt(i,5)+"\";\""+tabMode.getValueAt(i,6)+"\";\""+tabMode.getValueAt(i,7)+"\";\""+tabMode.getValueAt(i,8)+"\"\n"
                            );
                        }

                        f = new File("RingkasanBiayaObat.csv");
                        bw = new BufferedWriter(new FileWriter(f));
                        bw.write(htmlContent.toString());

                        bw.close();
                        Desktop.getDesktop().browse(f.toURI());
                        break;
                    case "Laporan 4 (Jasper)":
                        System.out.println("tesss");
                        Sequel.queryu("delete from temporary where temp37='"+akses.getalamatip()+"'");
                        int row=tabMode.getRowCount();
                        for(i=0;i<row;i++){  
                            Sequel.menyimpan("temporary","'"+i+"','"+
                                            tabMode.getValueAt(i,0).toString().replaceAll("'","`")+"','"+
                                            tabMode.getValueAt(i,1).toString().replaceAll("'","`")+"','"+
                                            tabMode.getValueAt(i,2).toString().replaceAll("'","`")+"','"+
                                            tabMode.getValueAt(i,3).toString().replaceAll("'","`")+"','"+
                                            tabMode.getValueAt(i,4).toString().replaceAll("'","`")+"','"+
                                            tabMode.getValueAt(i,5).toString().replaceAll("'","`")+"','"+
                                            tabMode.getValueAt(i,6).toString().replaceAll("'","`")+"','"+
                                            tabMode.getValueAt(i,7).toString().replaceAll("'","`")+"','"+
                                            Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(i,8).toString()))+
                                    "','','','','','','','','','','','','','','','','','','','','','','','','','','','','"+akses.getalamatip()+"'","Data"); 
                        }

                        Map<String, Object> param = new HashMap<>();
                        param.put("namars",akses.getnamars());
                        param.put("alamatrs",akses.getalamatrs());
                        param.put("kotars",akses.getkabupatenrs());
                        param.put("propinsirs",akses.getpropinsirs());
                        param.put("kontakrs",akses.getkontakrs());
                        param.put("emailrs",akses.getemailrs());   
                        param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                        Valid.MyReportqry("rptRingkasanBiayaObatPasienPertanggal.jasper","report","[ Ringkasan Biaya Obat Pasien Per Tanggal ]","select * from temporary where temporary.temp37='"+akses.getalamatip()+"' order by temporary.no",param);
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
            //Valid.pindah(evt, BtnHapus, BtnAll);
        }
    }//GEN-LAST:event_BtnPrintKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            InventoryRingkasanBiayaObatPasienPerTanggal dialog = new InventoryRingkasanBiayaObatPasienPerTanggal(new javax.swing.JFrame(), true);
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
    private widget.Button BtnJenis;
    private widget.Button BtnKategori;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSeek3;
    private widget.Button BtnSeek4;
    private widget.CekBox ChkInput;
    private widget.panelisi FormInput;
    private widget.TextBox Kd2;
    private javax.swing.JPanel PanelInput;
    private widget.TextBox TCari;
    private widget.Tanggal Tgl1;
    private widget.Tanggal Tgl2;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel7;
    private widget.TextBox kdasal;
    private widget.TextBox kdgolongan;
    private widget.TextBox kdjenis;
    private widget.TextBox kdkategori;
    private widget.TextBox kdpenjab;
    private widget.Label label11;
    private widget.Label label17;
    private widget.Label label18;
    private widget.Label label19;
    private widget.Label label20;
    private widget.Label label21;
    private widget.Label label22;
    private widget.Label label23;
    private widget.Label label9;
    private widget.TextBox nmasal;
    private widget.TextBox nmgolongan;
    private widget.TextBox nmjns;
    private widget.TextBox nmkategori;
    private widget.TextBox nmpenjab;
    private widget.panelisi panelisi1;
    private widget.ScrollPane scrollPane4;
    private widget.ComboBox status;
    private widget.Table tbDokter;
    // End of variables declaration//GEN-END:variables

    private void prosesCari() {             
        try{   
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
            Valid.tabelKosong(tabMode);
            statusobat="";
            if(status.getSelectedIndex()==1){
                statusobat=" and detail_pemberian_obat.status='Ralan' ";
            }else if(status.getSelectedIndex()==2){
                statusobat=" and detail_pemberian_obat.status='Ranap' ";
            }
            carabayar="";
            if(!kdpenjab.getText().trim().equals("")){
                carabayar=" and reg_periksa.kd_pj='"+kdpenjab.getText()+"' ";
            }
            bangsal="";
            if(!kdasal.getText().trim().equals("")){
                bangsal=" and detail_pemberian_obat.kd_bangsal='"+kdasal.getText()+"' ";
            }
            jenisbarang="";
            if(!kdjenis.getText().trim().equals("")){
                jenisbarang=" and databarang.kdjns='"+kdjenis.getText()+"' ";
            }
            kategoribarang="";
            if(!kdkategori.getText().trim().equals("")){
                kategoribarang=" and databarang.kode_kategori='"+kdkategori.getText()+"' ";
            }
            golonganbarang="";
            if(!kdgolongan.getText().trim().equals("")){
                golonganbarang=" and databarang.kode_golongan='"+kdgolongan.getText()+"' ";
            }
            ps=koneksi.prepareStatement(
                "select detail_pemberian_obat.tgl_perawatan,detail_pemberian_obat.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab,', ',propinsi.nm_prop) as alamat,"+
                "penjab.png_jawab,sum(detail_pemberian_obat.total) as total,detail_pemberian_obat.status,poliklinik.nm_poli "+
                "from detail_pemberian_obat inner join reg_periksa on detail_pemberian_obat.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join databarang on detail_pemberian_obat.kode_brng=databarang.kode_brng "+
                "inner join kelurahan on pasien.kd_kel=kelurahan.kd_kel inner join kecamatan on pasien.kd_kec=kecamatan.kd_kec "+
                "inner join kabupaten on pasien.kd_kab=kabupaten.kd_kab inner join propinsi on pasien.kd_prop=propinsi.kd_prop "+
                "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj inner join poliklinik on poliklinik.kd_poli=reg_periksa.kd_poli "+
                "where detail_pemberian_obat.tgl_perawatan between ? and ? "+
                statusobat+carabayar+bangsal+jenisbarang+kategoribarang+golonganbarang+(TCari.getText().trim().equals("")?"":
                "and (detail_pemberian_obat.no_rawat like ? or reg_periksa.no_rkm_medis like ? or pasien.nm_pasien like ? or "+
                "concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab,', ',propinsi.nm_prop) like ?) ")+
                "group by detail_pemberian_obat.tgl_perawatan,detail_pemberian_obat.no_rawat order by detail_pemberian_obat.tgl_perawatan");
            try {
                ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                if(!TCari.getText().trim().equals("")){
                    ps.setString(3,"%"+TCari.getText().trim()+"%");
                    ps.setString(4,"%"+TCari.getText().trim()+"%");
                    ps.setString(5,"%"+TCari.getText().trim()+"%");
                    ps.setString(6,"%"+TCari.getText().trim()+"%");
                }
                rs=ps.executeQuery();
                i=1;
                total=0;
                while(rs.next()){
                    total=total+rs.getDouble("total");
                    unit=rs.getString("nm_poli");
                    if(rs.getString("status").equals("Ranap")){
                        unit=Sequel.cariIsi("select concat(kamar_inap.kd_kamar,' ',bangsal.nm_bangsal) from kamar_inap inner join kamar on kamar_inap.kd_kamar=kamar.kd_kamar inner join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal where kamar_inap.no_rawat=? order by kamar_inap.tgl_keluar desc limit 1  ",rs.getString("no_rawat"));
                    }
                    tabMode.addRow(new Object[]{
                        i+".",rs.getString("tgl_perawatan"),rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),
                        rs.getString("alamat"),rs.getString("png_jawab"),unit,rs.getDouble("total")
                    });
                    i++;
                }
                if(total>0){
                    tabMode.addRow(new Object[]{
                        "","Total",":","","","","","",total
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
                
            this.setCursor(Cursor.getDefaultCursor());             
        }catch(Exception e){
            System.out.println("Catatan  "+e);
        }        
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,96));
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

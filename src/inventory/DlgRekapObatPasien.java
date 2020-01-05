package inventory;
import fungsi.WarnaTable;
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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import simrskhanza.DlgCariBangsal;
import simrskhanza.DlgPenanggungJawab;

public class DlgRekapObatPasien extends javax.swing.JDialog {
    private final DefaultTableModel tabMode,tabMode2;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement psobat,psreg,pskamar;
    private ResultSet rsobat,rsreg,rskamar; 
    private DlgPenanggungJawab penjab=new DlgPenanggungJawab(null,false);
    private DlgCariJenis jenis = new DlgCariJenis(null, false);
    private DlgCariKategori kategori = new DlgCariKategori(null, false);
    private DlgCariGolongan golongan = new DlgCariGolongan(null, false);
    private DlgCariBangsal asalstok=new DlgCariBangsal(null,false);
    private int i=0,a=0;
    private double jmlbiaya=0,ttlbiaya=0,jmlembalase=0,ttlembalase=0,jmltuslah=0,ttltuslah=0,jmltotal=0,ttltotal=0;

    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public DlgRekapObatPasien(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        tabMode=new DefaultTableModel(null,new Object[]{"No.","Tanggal","No.RM","Nama Pasien","Jml","Nama Obat","Biaya Obat","Embalase","Tuslah","Total"}){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbDokter.setModel(tabMode);

        tbDokter.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0;i < 10; i++) {
            TableColumn column = tbDokter.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(35);
            }else if(i==1){
                column.setPreferredWidth(70);
            }else if(i==2){
                column.setPreferredWidth(60);
            }else if(i==3){
                column.setPreferredWidth(170);
            }else if(i==4){
                column.setPreferredWidth(40);
            }else if(i==5){
                column.setPreferredWidth(230);
            }else{
                column.setPreferredWidth(80);
            }
        }
        tbDokter.setDefaultRenderer(Object.class, new WarnaTable());  
        
        tabMode2=new DefaultTableModel(null,new Object[]{"No.","Tanggal","No.RM","Nama Pasien","Jml","Nama Obat","Biaya Obat","Embalase","Tuslah","Total"}){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbDokter2.setModel(tabMode2);

        tbDokter2.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbDokter2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0;i < 10; i++) {
            TableColumn column = tbDokter2.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(35);
            }else if(i==1){
                column.setPreferredWidth(70);
            }else if(i==2){
                column.setPreferredWidth(60);
            }else if(i==3){
                column.setPreferredWidth(170);
            }else if(i==4){
                column.setPreferredWidth(40);
            }else if(i==5){
                column.setPreferredWidth(230);
            }else{
                column.setPreferredWidth(80);
            }
        }
        tbDokter2.setDefaultRenderer(Object.class, new WarnaTable());  
        
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
        BtnCari = new widget.Button();
        jLabel7 = new widget.Label();
        BtnAll = new widget.Button();
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
        TabRawat1 = new javax.swing.JTabbedPane();
        scrollPane4 = new widget.ScrollPane();
        tbDokter = new widget.Table();
        scrollPane5 = new widget.ScrollPane();
        tbDokter2 = new widget.Table();

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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 253, 247)), "::[ Rekap Penggunaan Obat Per Pasien ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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

        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(105, 23));
        panelisi1.add(jLabel7);

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
        label19.setBounds(245, 10, 65, 23);

        nmpenjab.setEditable(false);
        nmpenjab.setName("nmpenjab"); // NOI18N
        nmpenjab.setPreferredSize(new java.awt.Dimension(168, 23));
        FormInput.add(nmpenjab);
        nmpenjab.setBounds(313, 10, 150, 23);

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
        BtnSeek3.setBounds(466, 10, 28, 23);

        label20.setText("Asal Stok :");
        label20.setName("label20"); // NOI18N
        label20.setPreferredSize(new java.awt.Dimension(35, 23));
        FormInput.add(label20);
        label20.setBounds(498, 10, 60, 23);

        nmasal.setEditable(false);
        nmasal.setName("nmasal"); // NOI18N
        nmasal.setPreferredSize(new java.awt.Dimension(215, 23));
        FormInput.add(nmasal);
        nmasal.setBounds(561, 10, 150, 23);

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
        BtnSeek4.setBounds(714, 10, 28, 23);

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
        label22.setBounds(245, 40, 65, 23);

        nmkategori.setEditable(false);
        nmkategori.setName("nmkategori"); // NOI18N
        nmkategori.setPreferredSize(new java.awt.Dimension(205, 23));
        FormInput.add(nmkategori);
        nmkategori.setBounds(313, 40, 150, 23);

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
        BtnKategori.setBounds(466, 40, 28, 23);

        label23.setText("Golongan :");
        label23.setName("label23"); // NOI18N
        label23.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label23);
        label23.setBounds(498, 40, 60, 23);

        nmgolongan.setEditable(false);
        nmgolongan.setName("nmgolongan"); // NOI18N
        nmgolongan.setPreferredSize(new java.awt.Dimension(205, 23));
        FormInput.add(nmgolongan);
        nmgolongan.setBounds(561, 40, 150, 23);

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
        BtnGolongan.setBounds(714, 40, 28, 23);

        status.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Semua Status", "Obat Rawat Jalan", "Obat Rawat Inap" }));
        status.setName("status"); // NOI18N
        FormInput.add(status);
        status.setBounds(56, 10, 181, 23);

        PanelInput.add(FormInput, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        TabRawat1.setBackground(new java.awt.Color(255, 255, 253));
        TabRawat1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(241, 246, 236)));
        TabRawat1.setForeground(new java.awt.Color(50, 50, 50));
        TabRawat1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabRawat1.setName("TabRawat1"); // NOI18N
        TabRawat1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawat1MouseClicked(evt);
            }
        });

        scrollPane4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
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

        TabRawat1.addTab("Berdasar Tanggal Masuk", scrollPane4);

        scrollPane5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        scrollPane5.setName("scrollPane5"); // NOI18N
        scrollPane5.setOpaque(true);

        tbDokter2.setAutoCreateRowSorter(true);
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
        tbDokter2.setName("tbDokter2"); // NOI18N
        scrollPane5.setViewportView(tbDokter2);

        TabRawat1.addTab("Berdasar Tanggal Pulang", scrollPane5);

        internalFrame1.add(TabRawat1, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents
/*
private void KdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdKeyPressed
    Valid.pindah(evt,BtnCari,Nm);
}//GEN-LAST:event_TKdKeyPressed
*/

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        if(TabRawat1.getSelectedIndex()==0){
           this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            if(tabMode.getRowCount()==0){
                JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                //TCari.requestFocus();
            }else if(tabMode.getRowCount()!=0){

                Sequel.queryu("truncate table temporary");
                int row=tabMode.getRowCount();
                for(int r=0;r<row;r++){  
                    Sequel.menyimpan("temporary","'0','"+
                                    tabMode.getValueAt(r,0).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,1).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,2).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,3).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,4).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,5).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,6).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,7).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,8).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,9).toString().replaceAll("'","`")+"','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Rekap Obat Perdokter Poli"); 
                }

                Map<String, Object> param = new HashMap<>();
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());   
                    param.put("logo",Sequel.cariGambar("select logo from setting")); 
                Valid.MyReport("rptRekapObatPasien.jasper","report","[ Rekap Penggunaan Obat Per Pasien ]",param);
            }
            this.setCursor(Cursor.getDefaultCursor());
        }else if(TabRawat1.getSelectedIndex()==1){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            if(tabMode2.getRowCount()==0){
                JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                //TCari.requestFocus();
            }else if(tabMode2.getRowCount()!=0){

                Sequel.queryu("truncate table temporary");
                int row=tabMode2.getRowCount();
                for(int r=0;r<row;r++){  
                    Sequel.menyimpan("temporary","'0','"+
                                    tabMode2.getValueAt(r,0).toString().replaceAll("'","`")+"','"+
                                    tabMode2.getValueAt(r,1).toString().replaceAll("'","`")+"','"+
                                    tabMode2.getValueAt(r,2).toString().replaceAll("'","`")+"','"+
                                    tabMode2.getValueAt(r,3).toString().replaceAll("'","`")+"','"+
                                    tabMode2.getValueAt(r,4).toString().replaceAll("'","`")+"','"+
                                    tabMode2.getValueAt(r,5).toString().replaceAll("'","`")+"','"+
                                    tabMode2.getValueAt(r,6).toString().replaceAll("'","`")+"','"+
                                    tabMode2.getValueAt(r,7).toString().replaceAll("'","`")+"','"+
                                    tabMode2.getValueAt(r,8).toString().replaceAll("'","`")+"','"+
                                    tabMode2.getValueAt(r,9).toString().replaceAll("'","`")+"','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Rekap Obat Perdokter Poli"); 
                }

                Map<String, Object> param = new HashMap<>();
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());   
                    param.put("logo",Sequel.cariGambar("select logo from setting")); 
                Valid.MyReport("rptRekapObatPasien.jasper","report","[ Rekap Penggunaan Obat Per Pasien ]",param);
            }
            this.setCursor(Cursor.getDefaultCursor());
        }
            
    }//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt,BtnAll,BtnKeluar);
        }
    }//GEN-LAST:event_BtnPrintKeyPressed

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
        status.setSelectedIndex(0);
        if(TabRawat1.getSelectedIndex()==0){
           prosesCari();
        }else if(TabRawat1.getSelectedIndex()==1){
           prosesCari2();
        }
            
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, status, BtnPrint);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        if(TabRawat1.getSelectedIndex()==0){
           prosesCari();
        }else if(TabRawat1.getSelectedIndex()==1){
           prosesCari2();
        }
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

    private void TabRawat1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawat1MouseClicked
        if(TabRawat1.getSelectedIndex()==0){
           prosesCari();
        }else if(TabRawat1.getSelectedIndex()==1){
           prosesCari2();
        }
    }//GEN-LAST:event_TabRawat1MouseClicked

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgRekapObatPasien dialog = new DlgRekapObatPasien(new javax.swing.JFrame(), true);
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
    private javax.swing.JTabbedPane TabRawat1;
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
    private widget.TextBox nmasal;
    private widget.TextBox nmgolongan;
    private widget.TextBox nmjns;
    private widget.TextBox nmkategori;
    private widget.TextBox nmpenjab;
    private widget.panelisi panelisi1;
    private widget.ScrollPane scrollPane4;
    private widget.ScrollPane scrollPane5;
    private widget.ComboBox status;
    private widget.Table tbDokter;
    private widget.Table tbDokter2;
    // End of variables declaration//GEN-END:variables

    private void prosesCari() {             
        try{   
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
            Valid.tabelKosong(tabMode);
            if((status.getSelectedIndex()==0)&&nmpenjab.getText().equals("")){
                psreg=koneksi.prepareStatement(
                   "select reg_periksa.tgl_registrasi,reg_periksa.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien "+
                   "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                   "where reg_periksa.stts<>'Batal' and reg_periksa.tgl_registrasi between ? and ? order by reg_periksa.tgl_registrasi");
            }else{
                psreg=koneksi.prepareStatement(
                   "select reg_periksa.tgl_registrasi,reg_periksa.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien "+
                   "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                   "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj where reg_periksa.stts<>'Batal' and reg_periksa.tgl_registrasi between ? and ? "+
                   "and reg_periksa.status_lanjut like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? order by reg_periksa.tgl_registrasi");
            }
           
            try {
                if((status.getSelectedIndex()==0)&&nmpenjab.getText().equals("")){
                    psreg.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    psreg.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                }else{
                    psreg.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    psreg.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    psreg.setString(3,"%"+status.getSelectedItem().toString().replaceAll("Obat Rawat Jalan","Ralan").replaceAll("Obat Rawat Inap","Ranap").replaceAll("Semua Status","")+"%");
                    psreg.setString(4,"%"+kdpenjab.getText()+nmpenjab.getText()+"%");
                }
                rsreg=psreg.executeQuery();
                i=1;
                ttlbiaya=0;ttlembalase=0;ttltuslah=0;ttltotal=0;
                while(rsreg.next()){
                    if((status.getSelectedIndex()==0)&&nmjns.getText().equals("")&&nmkategori.getText().equals("")&&nmgolongan.getText().equals("")&&nmasal.getText().equals("")){
                        psobat=koneksi.prepareStatement(
                            "select detail_pemberian_obat.kode_brng,databarang.nama_brng,sum(detail_pemberian_obat.jml) as jml,"+
                            "(sum(detail_pemberian_obat.total)-sum(detail_pemberian_obat.embalase+detail_pemberian_obat.tuslah)) as biaya,"+
                            "sum(detail_pemberian_obat.embalase) as embalase, sum(detail_pemberian_obat.tuslah) as tuslah,"+
                            "sum(detail_pemberian_obat.total) as total "+
                            "from detail_pemberian_obat inner join reg_periksa on detail_pemberian_obat.no_rawat=reg_periksa.no_rawat "+
                            "inner join databarang on detail_pemberian_obat.kode_brng=databarang.kode_brng "+
                            "where detail_pemberian_obat.no_rawat=?  "+
                            "group by detail_pemberian_obat.kode_brng order by databarang.nama_brng");  
                    }else{
                        psobat=koneksi.prepareStatement(
                            "select detail_pemberian_obat.kode_brng,databarang.nama_brng,sum(detail_pemberian_obat.jml) as jml,"+
                            "(sum(detail_pemberian_obat.total)-sum(detail_pemberian_obat.embalase+detail_pemberian_obat.tuslah)) as biaya,"+
                            "sum(detail_pemberian_obat.embalase) as embalase, sum(detail_pemberian_obat.tuslah) as tuslah,"+
                            "sum(detail_pemberian_obat.total) as total "+
                            "from detail_pemberian_obat inner join reg_periksa on detail_pemberian_obat.no_rawat=reg_periksa.no_rawat "+
                            "inner join databarang on detail_pemberian_obat.kode_brng=databarang.kode_brng "+
                            "inner join jenis on databarang.kdjns=jenis.kdjns "+
                            "inner join kategori_barang on kategori_barang.kode=databarang.kode_kategori "+
                            "inner join golongan_barang on golongan_barang.kode=databarang.kode_golongan "+
                            "inner join bangsal on detail_pemberian_obat.kd_bangsal=bangsal.kd_bangsal "+
                            "where detail_pemberian_obat.no_rawat=? and detail_pemberian_obat.status like ? "+
                            "and concat(databarang.kdjns,jenis.nama) like ? and concat(databarang.kode_kategori,kategori_barang.nama) like ? "+
                            "and concat(databarang.kode_golongan,golongan_barang.nama) like ? and concat(detail_pemberian_obat.kd_bangsal,bangsal.nm_bangsal) like ? "+
                            "group by detail_pemberian_obat.kode_brng order by databarang.nama_brng");   
                    }
                    try {
                        if((status.getSelectedIndex()==0)&&nmjns.getText().equals("")&&nmkategori.getText().equals("")&&nmgolongan.getText().equals("")&&nmasal.getText().equals("")){
                            psobat.setString(1,rsreg.getString("no_rawat"));
                        }else{
                            psobat.setString(1,rsreg.getString("no_rawat"));
                            psobat.setString(2,"%"+status.getSelectedItem().toString().replaceAll("Obat Rawat Jalan","Ralan").replaceAll("Obat Rawat Inap","Ranap").replaceAll("Semua Status","")+"%");
                            psobat.setString(3,"%"+kdjenis.getText()+nmjns.getText()+"%");
                            psobat.setString(4,"%"+kdkategori.getText()+nmkategori.getText()+"%");
                            psobat.setString(5,"%"+kdgolongan.getText()+nmgolongan.getText()+"%");
                            psobat.setString(6,"%"+kdasal.getText()+nmasal.getText()+"%");
                        }
                        rsobat=psobat.executeQuery();
                        if(rsobat.next()){
                             rsobat.beforeFirst();
                             a=1;
                             jmlbiaya=0;jmlembalase=0;jmltotal=0;jmltuslah=0;
                             while(rsobat.next()){
                                 if(a==1){
                                    tabMode.addRow(new String[]{
                                        i+"",rsreg.getString("tgl_registrasi"),rsreg.getString("no_rkm_medis"),rsreg.getString("nm_pasien"),
                                        rsobat.getString(3),rsobat.getString(1)+" "+rsobat.getString(2),Valid.SetAngka(rsobat.getDouble(4)),
                                        Valid.SetAngka(rsobat.getDouble(5)),Valid.SetAngka(rsobat.getDouble(6)),Valid.SetAngka(rsobat.getDouble(7))
                                    });
                                    i++; 
                                 }else{
                                    tabMode.addRow(new String[]{
                                        "","","","",rsobat.getString(3),rsobat.getString(1)+" "+rsobat.getString(2),Valid.SetAngka(rsobat.getDouble(4)),
                                        Valid.SetAngka(rsobat.getDouble(5)),Valid.SetAngka(rsobat.getDouble(6)),Valid.SetAngka(rsobat.getDouble(7))
                                    }); 
                                 }   
                                 jmlbiaya=jmlbiaya+rsobat.getDouble(4);
                                 ttlbiaya=ttlbiaya+rsobat.getDouble(4);
                                 jmlembalase=jmlembalase+rsobat.getDouble(5);
                                 ttlembalase=ttlembalase+rsobat.getDouble(5);
                                 jmltuslah=jmltuslah+rsobat.getDouble(6);
                                 ttltuslah=ttltuslah+rsobat.getDouble(6);
                                 jmltotal=jmltotal+rsobat.getDouble(7);
                                 ttltotal=ttltotal+rsobat.getDouble(7);
                                 a++;
                             }
                             if(jmltotal>0){
                                 tabMode.addRow(new String[]{
                                    "","","","","","Subtotal :",Valid.SetAngka(jmlbiaya),Valid.SetAngka(jmlembalase),Valid.SetAngka(jmltuslah),Valid.SetAngka(jmltotal)
                                 });
                             }
                        }
                    } catch (Exception e) {
                        System.out.println("Notif : "+e);
                    } finally{
                        if(rsobat!=null){
                            rsobat.close();
                        }
                        if(psobat!=null){
                            psobat.close();
                        }
                    }
                }
                if(ttltotal>0){
                    tabMode.addRow(new Object[]{">>","Total ",":","","","",Valid.SetAngka(ttlbiaya),Valid.SetAngka(ttlembalase),Valid.SetAngka(ttltuslah),Valid.SetAngka(ttltotal)});
                }
            } catch (Exception e) {
                System.out.println("Notif : "+e);
            } finally{
                if(rsreg!=null){
                    rsreg.close();
                }
                if(psreg!=null){
                    psreg.close();
                }
            }
                
           this.setCursor(Cursor.getDefaultCursor());             
        }catch(Exception e){
            System.out.println("Catatan  "+e);
        }        
    }
    
    private void prosesCari2() {
        try{   
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
            Valid.tabelKosong(tabMode2);
            if(nmpenjab.getText().equals("")){
                if((status.getSelectedIndex()==0)||(status.getSelectedIndex()==1)){
                    psreg=koneksi.prepareStatement(
                       "select reg_periksa.tgl_registrasi,reg_periksa.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien "+
                       "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                       "where reg_periksa.stts<>'Batal' and reg_periksa.status_lanjut='Ralan' and "+
                       "reg_periksa.tgl_registrasi between ? and ? order by reg_periksa.tgl_registrasi");
                }
                
                if((status.getSelectedIndex()==0)||(status.getSelectedIndex()==2)){
                    pskamar=koneksi.prepareStatement(
                       "select kamar_inap.tgl_keluar,reg_periksa.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien "+
                       "from kamar_inap inner join reg_periksa on kamar_inap.no_rawat=reg_periksa.no_rawat "+
                       "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                       "where kamar_inap.stts_pulang<>'Pindah Kamar' and kamar_inap.tgl_keluar between ? and ? "+
                       "group by reg_periksa.no_rawat order by kamar_inap.tgl_keluar");
                }
            }else{
                if((status.getSelectedIndex()==0)||(status.getSelectedIndex()==1)){
                    psreg=koneksi.prepareStatement(
                        "select reg_periksa.tgl_registrasi,reg_periksa.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj where reg_periksa.stts<>'Batal' and reg_periksa.status_lanjut='Ralan' "+
                        "and reg_periksa.tgl_registrasi between ? and ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? order by reg_periksa.tgl_registrasi");
                }
                if((status.getSelectedIndex()==0)||(status.getSelectedIndex()==2)){
                    pskamar=koneksi.prepareStatement(
                       "select kamar_inap.tgl_keluar,reg_periksa.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien "+
                       "from kamar_inap inner join reg_periksa on kamar_inap.no_rawat=reg_periksa.no_rawat "+
                       "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                       "where kamar_inap.stts_pulang<>'Pindah Kamar' and kamar_inap.tgl_keluar between ? and ? "+
                       "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? group by reg_periksa.no_rawat order by kamar_inap.tgl_keluar");
                }
            }
            try {
                if(nmpenjab.getText().equals("")){
                    if((status.getSelectedIndex()==0)||(status.getSelectedIndex()==1)){
                        psreg.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                        psreg.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    }
                    if((status.getSelectedIndex()==0)||(status.getSelectedIndex()==2)){
                        pskamar.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                        pskamar.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    }
                }else{
                    if((status.getSelectedIndex()==0)||(status.getSelectedIndex()==1)){
                        psreg.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                        psreg.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                        psreg.setString(3,"%"+kdpenjab.getText()+nmpenjab.getText()+"%");
                    }
                    if((status.getSelectedIndex()==0)||(status.getSelectedIndex()==2)){
                        pskamar.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                        pskamar.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                        pskamar.setString(3,"%"+kdpenjab.getText()+nmpenjab.getText()+"%");
                    }
                }
                
                i=1;
                ttlbiaya=0;ttlembalase=0;ttltuslah=0;ttltotal=0;
                if((status.getSelectedIndex()==0)||(status.getSelectedIndex()==1)){
                    rsreg=psreg.executeQuery();
                    while(rsreg.next()){
                        if((status.getSelectedIndex()==0)&&nmjns.getText().equals("")&&nmkategori.getText().equals("")&&nmgolongan.getText().equals("")&&nmasal.getText().equals("")){
                            psobat=koneksi.prepareStatement(
                                "select detail_pemberian_obat.kode_brng,databarang.nama_brng,sum(detail_pemberian_obat.jml) as jml,"+
                                "(sum(detail_pemberian_obat.total)-sum(detail_pemberian_obat.embalase+detail_pemberian_obat.tuslah)) as biaya,"+
                                "sum(detail_pemberian_obat.embalase) as embalase, sum(detail_pemberian_obat.tuslah) as tuslah,"+
                                "sum(detail_pemberian_obat.total) as total "+
                                "from detail_pemberian_obat inner join reg_periksa on detail_pemberian_obat.no_rawat=reg_periksa.no_rawat "+
                                "inner join databarang on detail_pemberian_obat.kode_brng=databarang.kode_brng "+
                                "where detail_pemberian_obat.no_rawat=?  "+
                                "group by detail_pemberian_obat.kode_brng order by databarang.nama_brng");  
                        }else{
                            psobat=koneksi.prepareStatement(
                                "select detail_pemberian_obat.kode_brng,databarang.nama_brng,sum(detail_pemberian_obat.jml) as jml,"+
                                "(sum(detail_pemberian_obat.total)-sum(detail_pemberian_obat.embalase+detail_pemberian_obat.tuslah)) as biaya,"+
                                "sum(detail_pemberian_obat.embalase) as embalase, sum(detail_pemberian_obat.tuslah) as tuslah,"+
                                "sum(detail_pemberian_obat.total) as total "+
                                "from detail_pemberian_obat inner join reg_periksa on detail_pemberian_obat.no_rawat=reg_periksa.no_rawat "+
                                "inner join databarang on detail_pemberian_obat.kode_brng=databarang.kode_brng "+
                                "inner join jenis on databarang.kdjns=jenis.kdjns "+
                                "inner join kategori_barang on kategori_barang.kode=databarang.kode_kategori "+
                                "inner join golongan_barang on golongan_barang.kode=databarang.kode_golongan "+
                                "inner join bangsal on detail_pemberian_obat.kd_bangsal=bangsal.kd_bangsal "+
                                "where detail_pemberian_obat.no_rawat=? and detail_pemberian_obat.status like ? "+
                                "and concat(databarang.kdjns,jenis.nama) like ? and concat(databarang.kode_kategori,kategori_barang.nama) like ? "+
                                "and concat(databarang.kode_golongan,golongan_barang.nama) like ? and concat(detail_pemberian_obat.kd_bangsal,bangsal.nm_bangsal) like ? "+
                                "group by detail_pemberian_obat.kode_brng order by databarang.nama_brng");   
                        }
                        try {
                            if((status.getSelectedIndex()==0)&&nmjns.getText().equals("")&&nmkategori.getText().equals("")&&nmgolongan.getText().equals("")&&nmasal.getText().equals("")){
                                psobat.setString(1,rsreg.getString("no_rawat"));
                            }else{
                                psobat.setString(1,rsreg.getString("no_rawat"));
                                psobat.setString(2,"%"+status.getSelectedItem().toString().replaceAll("Obat Rawat Jalan","Ralan").replaceAll("Obat Rawat Inap","Ranap").replaceAll("Semua Status","")+"%");
                                psobat.setString(3,"%"+kdjenis.getText()+nmjns.getText()+"%");
                                psobat.setString(4,"%"+kdkategori.getText()+nmkategori.getText()+"%");
                                psobat.setString(5,"%"+kdgolongan.getText()+nmgolongan.getText()+"%");
                                psobat.setString(6,"%"+kdasal.getText()+nmasal.getText()+"%");
                            }
                            rsobat=psobat.executeQuery();
                            if(rsobat.next()){
                                 rsobat.beforeFirst();
                                 a=1;
                                 jmlbiaya=0;jmlembalase=0;jmltotal=0;jmltuslah=0;
                                 while(rsobat.next()){
                                     if(a==1){
                                        tabMode2.addRow(new String[]{
                                            i+"",rsreg.getString("tgl_registrasi"),rsreg.getString("no_rkm_medis"),rsreg.getString("nm_pasien"),
                                            rsobat.getString(3),rsobat.getString(1)+" "+rsobat.getString(2),Valid.SetAngka(rsobat.getDouble(4)),
                                            Valid.SetAngka(rsobat.getDouble(5)),Valid.SetAngka(rsobat.getDouble(6)),Valid.SetAngka(rsobat.getDouble(7))
                                        });
                                        i++; 
                                     }else{
                                        tabMode2.addRow(new String[]{
                                            "","","","",rsobat.getString(3),rsobat.getString(1)+" "+rsobat.getString(2),Valid.SetAngka(rsobat.getDouble(4)),
                                            Valid.SetAngka(rsobat.getDouble(5)),Valid.SetAngka(rsobat.getDouble(6)),Valid.SetAngka(rsobat.getDouble(7))
                                        }); 
                                     }   
                                     jmlbiaya=jmlbiaya+rsobat.getDouble(4);
                                     ttlbiaya=ttlbiaya+rsobat.getDouble(4);
                                     jmlembalase=jmlembalase+rsobat.getDouble(5);
                                     ttlembalase=ttlembalase+rsobat.getDouble(5);
                                     jmltuslah=jmltuslah+rsobat.getDouble(6);
                                     ttltuslah=ttltuslah+rsobat.getDouble(6);
                                     jmltotal=jmltotal+rsobat.getDouble(7);
                                     ttltotal=ttltotal+rsobat.getDouble(7);
                                     a++;
                                 }
                                 if(jmltotal>0){
                                     tabMode2.addRow(new String[]{
                                        "","","","","","Subtotal :",Valid.SetAngka(jmlbiaya),Valid.SetAngka(jmlembalase),Valid.SetAngka(jmltuslah),Valid.SetAngka(jmltotal)
                                     });
                                 }
                            }
                        } catch (Exception e) {
                            System.out.println("Notif : "+e);
                        } finally{
                            if(rsobat!=null){
                                rsobat.close();
                            }
                            if(psobat!=null){
                                psobat.close();
                            }
                        }
                    }
                }
                if((status.getSelectedIndex()==0)||(status.getSelectedIndex()==2)){
                    rskamar=pskamar.executeQuery();
                    while(rskamar.next()){
                        if((status.getSelectedIndex()==0)&&nmjns.getText().equals("")&&nmkategori.getText().equals("")&&nmgolongan.getText().equals("")&&nmasal.getText().equals("")){
                            psobat=koneksi.prepareStatement(
                                "select detail_pemberian_obat.kode_brng,databarang.nama_brng,sum(detail_pemberian_obat.jml) as jml,"+
                                "(sum(detail_pemberian_obat.total)-sum(detail_pemberian_obat.embalase+detail_pemberian_obat.tuslah)) as biaya,"+
                                "sum(detail_pemberian_obat.embalase) as embalase, sum(detail_pemberian_obat.tuslah) as tuslah,"+
                                "sum(detail_pemberian_obat.total) as total "+
                                "from detail_pemberian_obat inner join reg_periksa on detail_pemberian_obat.no_rawat=reg_periksa.no_rawat "+
                                "inner join databarang on detail_pemberian_obat.kode_brng=databarang.kode_brng "+
                                "where detail_pemberian_obat.no_rawat=?  "+
                                "group by detail_pemberian_obat.kode_brng order by databarang.nama_brng");  
                        }else{
                            psobat=koneksi.prepareStatement(
                                "select detail_pemberian_obat.kode_brng,databarang.nama_brng,sum(detail_pemberian_obat.jml) as jml,"+
                                "(sum(detail_pemberian_obat.total)-sum(detail_pemberian_obat.embalase+detail_pemberian_obat.tuslah)) as biaya,"+
                                "sum(detail_pemberian_obat.embalase) as embalase, sum(detail_pemberian_obat.tuslah) as tuslah,"+
                                "sum(detail_pemberian_obat.total) as total "+
                                "from detail_pemberian_obat inner join reg_periksa on detail_pemberian_obat.no_rawat=reg_periksa.no_rawat "+
                                "inner join databarang on detail_pemberian_obat.kode_brng=databarang.kode_brng "+
                                "inner join jenis on databarang.kdjns=jenis.kdjns "+
                                "inner join kategori_barang on kategori_barang.kode=databarang.kode_kategori "+
                                "inner join golongan_barang on golongan_barang.kode=databarang.kode_golongan "+
                                "inner join bangsal on detail_pemberian_obat.kd_bangsal=bangsal.kd_bangsal "+
                                "where detail_pemberian_obat.no_rawat=? and detail_pemberian_obat.status like ? "+
                                "and concat(databarang.kdjns,jenis.nama) like ? and concat(databarang.kode_kategori,kategori_barang.nama) like ? "+
                                "and concat(databarang.kode_golongan,golongan_barang.nama) like ? and concat(detail_pemberian_obat.kd_bangsal,bangsal.nm_bangsal) like ? "+
                                "group by detail_pemberian_obat.kode_brng order by databarang.nama_brng");   
                        }
                        try {
                            if((status.getSelectedIndex()==0)&&nmjns.getText().equals("")&&nmkategori.getText().equals("")&&nmgolongan.getText().equals("")&&nmasal.getText().equals("")){
                                psobat.setString(1,rskamar.getString("no_rawat"));
                            }else{
                                psobat.setString(1,rskamar.getString("no_rawat"));
                                psobat.setString(2,"%"+status.getSelectedItem().toString().replaceAll("Obat Rawat Jalan","Ralan").replaceAll("Obat Rawat Inap","Ranap").replaceAll("Semua Status","")+"%");
                                psobat.setString(3,"%"+kdjenis.getText()+nmjns.getText()+"%");
                                psobat.setString(4,"%"+kdkategori.getText()+nmkategori.getText()+"%");
                                psobat.setString(5,"%"+kdgolongan.getText()+nmgolongan.getText()+"%");
                                psobat.setString(6,"%"+kdasal.getText()+nmasal.getText()+"%");
                            }
                            rsobat=psobat.executeQuery();
                            if(rsobat.next()){
                                 rsobat.beforeFirst();
                                 a=1;
                                 jmlbiaya=0;jmlembalase=0;jmltotal=0;jmltuslah=0;
                                 while(rsobat.next()){
                                     if(a==1){
                                        tabMode2.addRow(new String[]{
                                            i+"",rskamar.getString("tgl_keluar"),rskamar.getString("no_rkm_medis"),rskamar.getString("nm_pasien"),
                                            rsobat.getString(3),rsobat.getString(1)+" "+rsobat.getString(2),Valid.SetAngka(rsobat.getDouble(4)),
                                            Valid.SetAngka(rsobat.getDouble(5)),Valid.SetAngka(rsobat.getDouble(6)),Valid.SetAngka(rsobat.getDouble(7))
                                        });
                                        i++; 
                                     }else{
                                        tabMode2.addRow(new String[]{
                                            "","","","",rsobat.getString(3),rsobat.getString(1)+" "+rsobat.getString(2),Valid.SetAngka(rsobat.getDouble(4)),
                                            Valid.SetAngka(rsobat.getDouble(5)),Valid.SetAngka(rsobat.getDouble(6)),Valid.SetAngka(rsobat.getDouble(7))
                                        }); 
                                     }   
                                     jmlbiaya=jmlbiaya+rsobat.getDouble(4);
                                     ttlbiaya=ttlbiaya+rsobat.getDouble(4);
                                     jmlembalase=jmlembalase+rsobat.getDouble(5);
                                     ttlembalase=ttlembalase+rsobat.getDouble(5);
                                     jmltuslah=jmltuslah+rsobat.getDouble(6);
                                     ttltuslah=ttltuslah+rsobat.getDouble(6);
                                     jmltotal=jmltotal+rsobat.getDouble(7);
                                     ttltotal=ttltotal+rsobat.getDouble(7);
                                     a++;
                                 }
                                 if(jmltotal>0){
                                     tabMode2.addRow(new String[]{
                                        "","","","","","Subtotal :",Valid.SetAngka(jmlbiaya),Valid.SetAngka(jmlembalase),Valid.SetAngka(jmltuslah),Valid.SetAngka(jmltotal)
                                     });
                                 }
                            }
                        } catch (Exception e) {
                            System.out.println("Notif : "+e);
                        } finally{
                            if(rsobat!=null){
                                rsobat.close();
                            }
                            if(psobat!=null){
                                psobat.close();
                            }
                        }
                    }
                }
                if(ttltotal>0){
                    tabMode2.addRow(new Object[]{">>","Total ",":","","","",Valid.SetAngka(ttlbiaya),Valid.SetAngka(ttlembalase),Valid.SetAngka(ttltuslah),Valid.SetAngka(ttltotal)});
                }
            } catch (Exception e) {
                System.out.println("Notif : "+e);
            } finally{
                if(rskamar!=null){
                    rskamar.close();
                }
                if(pskamar!=null){
                    pskamar.close();
                }
                if(rsreg!=null){
                    rsreg.close();
                }
                if(psreg!=null){
                    psreg.close();
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

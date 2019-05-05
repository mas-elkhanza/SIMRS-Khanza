/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgPenyakit.java
 *
 * Created on May 23, 2010, 12:57:16 AM
 */

package laporan;

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
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author dosen
 */
public final class DlgPenyakit extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private DlgKtgPenyakit ktg=new DlgKtgPenyakit(null,false);
    private String[] hlm;
    private String awal="0";
    private PreparedStatement ps,ps2;
    private ResultSet rs,rs2;
    private double jumlah=0,x=0,i=0;
    private int z=0,j=0,mulai=0;
    /** Creates new form DlgPenyakit
     * @param parent
     * @param modal */
    public DlgPenyakit(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(10,2);
        setSize(628,674);

        Object[] row={"P","Kode","Nama Penyakit","Ciri-ciri Penyakit","Keterangan","Kategori Penyakit","Ciri-ciri Umum","Status"};
        tabMode=new DefaultTableModel(null,row){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbPenyakit.setModel(tabMode);
        //tbPenyakit.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbPenyakit.getBackground()));
        tbPenyakit.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbPenyakit.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (z = 0; z < 8; z++) {
            TableColumn column = tbPenyakit.getColumnModel().getColumn(z);
            if(z==0){
                column.setPreferredWidth(20);
            }else if(z==1){
                column.setPreferredWidth(70);
            }else if(z==2){
                column.setPreferredWidth(200);
            }else if(z==3){
                column.setPreferredWidth(200);
            }else if(z==4){
                column.setPreferredWidth(150);
            }else if(z==5){
                column.setPreferredWidth(150);
            }else if(z==6){
                column.setPreferredWidth(150);
            }else if(z==7){
                column.setPreferredWidth(100);
            }
        }
        tbPenyakit.setDefaultRenderer(Object.class, new WarnaTable());

        
        TKd.setDocument(new batasInput((byte)10).getKata(TKd));
        TNm.setDocument(new batasInput((int)100).getKata(TNm));
        TCiri.setDocument(new batasInput((int)1500).getKata(TCiri));
        TKeterangan.setDocument(new batasInput((byte)60).getKata(TKeterangan));
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        if(koneksiDB.cariCepat().equals("aktif")){
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
        
        ChkInput.setSelected(false);
        isForm(); 
        try{
            ps=koneksi.prepareStatement("select penyakit.kd_penyakit,penyakit.nm_penyakit,penyakit.ciri_ciri,penyakit.keterangan, "+
                "kategori_penyakit.nm_kategori,kategori_penyakit.ciri_umum,status "+
                "from kategori_penyakit inner join penyakit "+
                "on penyakit.kd_ktg=kategori_penyakit.kd_ktg where  "+
                " penyakit.kd_penyakit like ? or "+
                " penyakit.nm_penyakit like ? or "+
                " penyakit.ciri_ciri like ? or "+
                " penyakit.keterangan like ? or "+
                " kategori_penyakit.nm_kategori like ? or "+
                " penyakit.status like ? or "+
                " kategori_penyakit.ciri_umum like ? "+
                "order by penyakit.kd_penyakit  LIMIT ?,500");
            ps2=koneksi.prepareStatement("select count(penyakit.kd_penyakit) as jumlah from kategori_penyakit inner join penyakit "+
                " on penyakit.kd_ktg=kategori_penyakit.kd_ktg where  "+
                " penyakit.kd_penyakit like ? or "+
                " penyakit.nm_penyakit like ? or "+
                " penyakit.ciri_ciri like ? or "+
                " penyakit.keterangan like ? or "+
                " kategori_penyakit.nm_kategori like ? or "+
                " penyakit.status like ? or "+
                " kategori_penyakit.ciri_umum like ? "+
                " order by penyakit.kd_penyakit");
        }catch(Exception ex){
            System.out.println(ex);
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

        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbPenyakit = new widget.Table();
        jPanel3 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();
        panelGlass9 = new widget.panelisi();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();
        jLabel11 = new widget.Label();
        cmbHlm = new widget.ComboBox();
        PanelInput = new javax.swing.JPanel();
        FormInput = new widget.PanelBiasa();
        jLabel3 = new widget.Label();
        jLabel4 = new widget.Label();
        TKd = new widget.TextBox();
        TKtg = new widget.TextBox();
        jLabel8 = new widget.Label();
        TNm = new widget.TextBox();
        jLabel9 = new widget.Label();
        ScrollCiri = new widget.ScrollPane();
        TCiri = new widget.TextArea();
        TKeterangan = new widget.TextBox();
        jLabel10 = new widget.Label();
        btnKtg = new widget.Button();
        kd_ktg = new widget.TextBox();
        cmbStatus = new widget.ComboBox();
        jLabel12 = new widget.Label();
        ChkInput = new widget.CekBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowDeactivated(java.awt.event.WindowEvent evt) {
                formWindowDeactivated(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data ICD 10 Penyakit ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(70, 70, 70))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbPenyakit.setAutoCreateRowSorter(true);
        tbPenyakit.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbPenyakit.setName("tbPenyakit"); // NOI18N
        tbPenyakit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPenyakitMouseClicked(evt);
            }
        });
        tbPenyakit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbPenyakitKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbPenyakit);

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
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(70, 23));
        jLabel6.setRequestFocusEnabled(false);
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(202, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass9.add(TCari);

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
        panelGlass9.add(BtnCari);

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('3');
        BtnAll.setToolTipText("Alt+3");
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
        panelGlass9.add(BtnAll);

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(65, 23));
        panelGlass9.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(50, 23));
        panelGlass9.add(LCount);

        jLabel11.setText("Halaman :");
        jLabel11.setName("jLabel11"); // NOI18N
        jLabel11.setPreferredSize(new java.awt.Dimension(55, 23));
        panelGlass9.add(jLabel11);

        cmbHlm.setName("cmbHlm"); // NOI18N
        cmbHlm.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass9.add(cmbHlm);

        jPanel3.add(panelGlass9, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(611, 155));
        FormInput.setLayout(null);

        jLabel3.setText("Kode Penyakit :");
        jLabel3.setName("jLabel3"); // NOI18N
        FormInput.add(jLabel3);
        jLabel3.setBounds(0, 12, 100, 23);

        jLabel4.setText("Kategori :");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(0, 119, 100, 23);

        TKd.setHighlighter(null);
        TKd.setName("TKd"); // NOI18N
        TKd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TKdKeyPressed(evt);
            }
        });
        FormInput.add(TKd);
        TKd.setBounds(103, 12, 110, 23);

        TKtg.setEditable(false);
        TKtg.setName("TKtg"); // NOI18N
        TKtg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TKtgKeyPressed(evt);
            }
        });
        FormInput.add(TKtg);
        TKtg.setBounds(215, 119, 374, 23);

        jLabel8.setText("Nama Penyakit :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(222, 12, 110, 23);

        TNm.setHighlighter(null);
        TNm.setName("TNm"); // NOI18N
        TNm.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNmKeyPressed(evt);
            }
        });
        FormInput.add(TNm);
        TNm.setBounds(335, 12, 284, 23);

        jLabel9.setText("Ciri-ciri :");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(0, 42, 100, 23);

        ScrollCiri.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        ScrollCiri.setForeground(new java.awt.Color(70, 70, 70));
        ScrollCiri.setName("ScrollCiri"); // NOI18N

        TCiri.setBorder(null);
        TCiri.setColumns(20);
        TCiri.setRows(5);
        TCiri.setName("TCiri"); // NOI18N
        TCiri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCiriKeyPressed(evt);
            }
        });
        ScrollCiri.setViewportView(TCiri);

        FormInput.add(ScrollCiri);
        ScrollCiri.setBounds(103, 42, 516, 40);

        TKeterangan.setHighlighter(null);
        TKeterangan.setName("TKeterangan"); // NOI18N
        TKeterangan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TKeteranganKeyPressed(evt);
            }
        });
        FormInput.add(TKeterangan);
        TKeterangan.setBounds(103, 90, 330, 23);

        jLabel10.setText("Keterangan :");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(0, 89, 100, 23);

        btnKtg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnKtg.setMnemonic('1');
        btnKtg.setToolTipText("Alt+1");
        btnKtg.setName("btnKtg"); // NOI18N
        btnKtg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKtgActionPerformed(evt);
            }
        });
        btnKtg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnKtgKeyPressed(evt);
            }
        });
        FormInput.add(btnKtg);
        btnKtg.setBounds(591, 120, 28, 23);

        kd_ktg.setHighlighter(null);
        kd_ktg.setName("kd_ktg"); // NOI18N
        kd_ktg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kd_ktgKeyPressed(evt);
            }
        });
        FormInput.add(kd_ktg);
        kd_ktg.setBounds(103, 119, 110, 23);

        cmbStatus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Menular", "Tidak Menular" }));
        cmbStatus.setLightWeightPopupEnabled(false);
        cmbStatus.setName("cmbStatus"); // NOI18N
        cmbStatus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbStatusKeyPressed(evt);
            }
        });
        FormInput.add(cmbStatus);
        cmbStatus.setBounds(489, 89, 130, 23);

        jLabel12.setText("Status :");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(436, 89, 50, 23);

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

    private void TKdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdKeyPressed
        Valid.pindah(evt,TCari,TNm);
}//GEN-LAST:event_TKdKeyPressed

    private void TKtgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKtgKeyPressed
        Valid.pindah(evt,TKd,BtnSimpan);
}//GEN-LAST:event_TKtgKeyPressed

    private void TNmKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNmKeyPressed
        Valid.pindah(evt,TKd,TCiri);
}//GEN-LAST:event_TNmKeyPressed

    private void TCiriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCiriKeyPressed
        Valid.pindah(evt,TNm,TKeterangan);
}//GEN-LAST:event_TCiriKeyPressed

    private void TKeteranganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKeteranganKeyPressed
        Valid.pindah(evt,TCiri,cmbStatus);
}//GEN-LAST:event_TKeteranganKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TKd.getText().trim().equals("")){
            Valid.textKosong(TKd,"Kode Penyakit");
        }else if(TNm.getText().trim().equals("")){
            Valid.textKosong(TNm,"Nama Penyakit");
        }else if(TCiri.getText().trim().equals("")){
            Valid.textKosong(TCiri,"Ciri-ciri Penyakit");
        }else if(TKeterangan.getText().trim().equals("")){
            Valid.textKosong(TKeterangan,"Keterangan");
        }else if(TKtg.getText().trim().equals("")){
            Valid.textKosong(kd_ktg,"Kategori Penyakit");
        }else{            
            Sequel.menyimpan("penyakit","?,?,?,?,?,?","Kode Penyakit",6,new String[]{
                TKd.getText(),TNm.getText(),TCiri.getText(),TKeterangan.getText(),kd_ktg.getText(),cmbStatus.getSelectedItem().toString()
            });
            tampil();
            emptTeks();
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,kd_ktg,BtnBatal);
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
        for(z=0;z<tbPenyakit.getRowCount();z++){ 
            if(tbPenyakit.getValueAt(z,0).toString().equals("true")){
                Sequel.meghapus("penyakit","kd_penyakit",tbPenyakit.getValueAt(z,1).toString());
            }
        } 
        tampil();
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
        if(TKd.getText().trim().equals("")){
            Valid.textKosong(TKd,"Kode Penyakit");
        }else if(TNm.getText().trim().equals("")){
            Valid.textKosong(TNm,"Nama Penyakit");
        }else if(TCiri.getText().trim().equals("")){
            Valid.textKosong(TCiri,"Ciri-ciri Penyakit");
        }else if(TKeterangan.getText().trim().equals("")){
            Valid.textKosong(TKeterangan,"Keterangan");
        }else if(TKtg.getText().trim().equals("")){
            Valid.textKosong(kd_ktg,"Kategori Penyakit");
        }else{
            Valid.editTable(tabMode,"penyakit","kd_penyakit","?","nm_penyakit=?,ciri_ciri=?,keterangan=?,kd_ktg=?,kd_penyakit=?,status=?",7,new String[]{
                TNm.getText(),TCiri.getText(),TKeterangan.getText(),kd_ktg.getText(),TKd.getText(),cmbStatus.getSelectedItem().toString(),tbPenyakit.getValueAt(tbPenyakit.getSelectedRow(),1).toString()
            });
            if(tabMode.getRowCount()!=0){tampil();}
            emptTeks();
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
        }else{Valid.pindah(evt,BtnEdit,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
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
            Valid.MyReportqry("rptPenyakit.jasper","report","::[ Data Penyakit ]::","select penyakit.kd_penyakit,penyakit.nm_penyakit,penyakit.ciri_ciri,penyakit.keterangan, "+
                "kategori_penyakit.nm_kategori,kategori_penyakit.ciri_umum "+
                "from kategori_penyakit inner join penyakit "+
                "on penyakit.kd_ktg=kategori_penyakit.kd_ktg where  "+
                " penyakit.kd_penyakit like '%"+TCari.getText().trim()+"%' or "+
                " penyakit.nm_penyakit like '%"+TCari.getText().trim()+"%' or "+
                " penyakit.ciri_ciri like '%"+TCari.getText().trim()+"%' or "+
                " penyakit.keterangan like '%"+TCari.getText().trim()+"%' or "+
                " kategori_penyakit.nm_kategori like '%"+TCari.getText().trim()+"%' or "+
                " penyakit.status like '%"+TCari.getText().trim()+"%' or "+
                " kategori_penyakit.ciri_umum like '%"+TCari.getText().trim()+"%' "+
                "order by penyakit.kd_penyakit LIMIT "+awal+",500",param);
        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnEdit, BtnKeluar);
        }
}//GEN-LAST:event_BtnPrintKeyPressed

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
            tampil();
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
            TCari.setText("");
            tampil();
        }else{
            Valid.pindah(evt, BtnCari, kd_ktg);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void btnKtgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKtgActionPerformed
        ktg.emptTeks();
        ktg.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        ktg.setLocationRelativeTo(internalFrame1);
        ktg.setVisible(true);
}//GEN-LAST:event_btnKtgActionPerformed

    private void btnKtgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnKtgKeyPressed
        Valid.pindah(evt,kd_ktg,BtnSimpan);
}//GEN-LAST:event_btnKtgKeyPressed

    private void tbPenyakitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPenyakitMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbPenyakitMouseClicked

    private void tbPenyakitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbPenyakitKeyPressed
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }else if(evt.getKeyCode()==KeyEvent.VK_A){                
                for(z=0;z<tbPenyakit.getRowCount();z++){ 
                    tbPenyakit.setValueAt(true,z,0);
                }
            }
            
        }
}//GEN-LAST:event_tbPenyakitKeyPressed

private void kd_ktgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kd_ktgKeyPressed
       if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select kategori_penyakit.nm_kategori from kategori_penyakit where kategori_penyakit.kd_ktg=?", TKtg,kd_ktg.getText());// TODO add your handling code here:
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnKtgActionPerformed(null);
        }else{
            Valid.pindah(evt, kd_ktg,BtnSimpan);
        }
}//GEN-LAST:event_kd_ktgKeyPressed

private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
  isForm();                
}//GEN-LAST:event_ChkInputActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        ktg.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(ktg.getTable().getSelectedRow()!= -1){                   
                    kd_ktg.setText(ktg.getTable().getValueAt(ktg.getTable().getSelectedRow(),1).toString());                    
                    TKtg.setText(ktg.getTable().getValueAt(ktg.getTable().getSelectedRow(),2).toString());
                }   
                kd_ktg.requestFocus();
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
        
        ktg.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    ktg.dispose();
                }                
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        }); 
        
       
    }//GEN-LAST:event_formWindowActivated

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        ktg.removeWindowListener(null);
        ktg.getTable().removeKeyListener(null);
    }//GEN-LAST:event_formWindowClosed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void cmbStatusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbStatusKeyPressed
        Valid.pindah(evt,TKeterangan,kd_ktg);
    }//GEN-LAST:event_cmbStatusKeyPressed

    private void formWindowDeactivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowDeactivated
        ktg.removeWindowListener(null);
        ktg.getTable().removeKeyListener(null);
    }//GEN-LAST:event_formWindowDeactivated

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgPenyakit dialog = new DlgPenyakit(new javax.swing.JFrame(), true);
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
    private widget.Label LCount;
    private javax.swing.JPanel PanelInput;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane ScrollCiri;
    private widget.TextBox TCari;
    private widget.TextArea TCiri;
    private widget.TextBox TKd;
    private widget.TextBox TKeterangan;
    private widget.TextBox TKtg;
    private widget.TextBox TNm;
    private widget.Button btnKtg;
    private widget.ComboBox cmbHlm;
    private widget.ComboBox cmbStatus;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel12;
    private widget.Label jLabel3;
    private widget.Label jLabel4;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private javax.swing.JPanel jPanel3;
    private widget.TextBox kd_ktg;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.Table tbPenyakit;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            awal="0";
            if(cmbHlm.getItemCount()>0){
                awal=hlm[Integer.parseInt(cmbHlm.getSelectedItem().toString())];
            }
            ps.setString(1,"%"+TCari.getText().trim()+"%");
            ps.setString(2,"%"+TCari.getText().trim()+"%");
            ps.setString(3,"%"+TCari.getText().trim()+"%");
            ps.setString(4,"%"+TCari.getText().trim()+"%");
            ps.setString(5,"%"+TCari.getText().trim()+"%");
            ps.setString(6,"%"+TCari.getText().trim()+"%");
            ps.setString(7,"%"+TCari.getText().trim()+"%");
            ps.setInt(8,Integer.parseInt(awal));
            rs=ps.executeQuery();
            while(rs.next()){
                tabMode.addRow(new Object[]{false,rs.getString(1),
                               rs.getString(2),
                               rs.getString(3),
                               rs.getString(4),
                               rs.getString(5),
                               rs.getString(6),
                               rs.getString(7)});
            }
            
            cmbHlm.removeAllItems();
            ps2.setString(1,"%"+TCari.getText().trim()+"%");
            ps2.setString(2,"%"+TCari.getText().trim()+"%");
            ps2.setString(3,"%"+TCari.getText().trim()+"%");
            ps2.setString(4,"%"+TCari.getText().trim()+"%");
            ps2.setString(5,"%"+TCari.getText().trim()+"%");
            ps2.setString(6,"%"+TCari.getText().trim()+"%");
            ps2.setString(7,"%"+TCari.getText().trim()+"%");
            rs2=ps2.executeQuery();
            jumlah=0;
            if(rs2.next()){
               jumlah=rs2.getDouble("jumlah");   
            }
            x=jumlah/499;
            i=Math.ceil(x);
            z=(int) i;
            
            hlm=new String[z+1];
            for(j=1;j<=i;j++){
                 mulai=((j-1)*499+j)-1;
                 hlm[j]=Integer.toString(mulai);
                 cmbHlm.addItem(j);
            }
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        LCount.setText(""+tabMode.getRowCount());
    }

    public void emptTeks() {
        TKd.setText("");
        TNm.setText("");
        TCiri.setText("");
        TKeterangan.setText("");
        kd_ktg.setText("");
        TKtg.setText("");
        TKd.requestFocus();
    }

    private void getData() {
        int row=tbPenyakit.getSelectedRow();
        if(row!= -1){
            TKd.setText(tbPenyakit.getValueAt(row,1).toString());
            TNm.setText(tbPenyakit.getValueAt(row,2).toString());
            TCiri.setText(tbPenyakit.getValueAt(row,3).toString());
            TKeterangan.setText(tbPenyakit.getValueAt(row,4).toString());
            TKtg.setText(tbPenyakit.getValueAt(row,5).toString());
            Sequel.cariIsi("select kd_ktg from penyakit where kd_penyakit=?", kd_ktg,tbPenyakit.getValueAt(row,1).toString());
            cmbStatus.setSelectedItem(tbPenyakit.getValueAt(row,6).toString());
        }
    }

    public JTextField getTextField(){
        return TKd;
    }

    public JTable getTabel(){
        return tbPenyakit;
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,176));
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
        BtnSimpan.setEnabled(akses.getpenyakit());
        BtnHapus.setEnabled(akses.getpenyakit());
        BtnEdit.setEnabled(akses.getpenyakit());
        BtnPrint.setEnabled(akses.getpenyakit());
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgPenyakit.java
 *
 * Created on May 23, 2010, 12:57:16 AM
 */

package tranfusidarah;

import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import simrskhanza.DlgCariPetugas;

/**
 *
 * @author dosen
 */
public final class UTDDonor extends javax.swing.JDialog {
    private final DefaultTableModel tabModeMedis,tabModeNonMedis,tabModeTranfusi;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps,ps2,pstranfusi;
    private ResultSet rs,rs2,rstranfusi;
    private Connection koneksi=koneksiDB.condb();
    private DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    private int jml=0,i=0,row=0,index=0,pilih=0;
    private String[] kodebarang,namabarang,jumlah,satuan,stokasal,hbeli,total;
    private SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd");
    
    /** Creates new form DlgPenyakit
     * @param parent
     * @param modal */
    public UTDDonor(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(10,2);
        setSize(628,674);
        
        tabModeMedis=new DefaultTableModel(null,new Object[]{"Jml","Kode Barang","Nama Barang","Harga","Subtotal","Satuan","Stok"}){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
        };
        tbMedis.setModel(tabModeMedis);
        tbMedis.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbMedis.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 7; i++) {
            TableColumn column = tbMedis.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(35);
            }else if(i==1){
                column.setPreferredWidth(80);
            }else if(i==2){
                column.setPreferredWidth(200);
            }else if(i==3){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==4){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==5){
                column.setPreferredWidth(50);
            }else if(i==6){
                column.setPreferredWidth(40);
            }
        }
        tbMedis.setDefaultRenderer(Object.class, new WarnaTable());
        //non medis
        tabModeNonMedis=new DefaultTableModel(null,new Object[]{"Jml","Kode Barang","Nama Barang","Harga","Subtotal","Satuan","Stok"}){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
        };
        tbNonMedis.setModel(tabModeNonMedis);
        tbNonMedis.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbNonMedis.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 7; i++) {
            TableColumn column = tbNonMedis.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(35);
            }else if(i==1){
                column.setPreferredWidth(80);
            }else if(i==2){
                column.setPreferredWidth(200);
            }else if(i==3){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==4){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==5){
                column.setPreferredWidth(50);
            }else if(i==6){
                column.setPreferredWidth(40);
            }
        }
        tbNonMedis.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeTranfusi=new DefaultTableModel(null,new Object[]{
                "Nomor Donor","Nama Pendonor","Tanggal","Dinas","J.K.","Umur","Alamat","G.D.","Resus","Tensi",
                "No.Bag","No.Selang","J.B.","J.D.","Tempat Aftap","Petugas Aftap","HBSAg","HCV","HIV",
                "Spilis","Petugas U.Saring","Hasil Cross","Petugas Crossmatch"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbTranfusiDarah.setModel(tabModeTranfusi);
        //tbPenyakit.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbPenyakit.getBackground()));
        tbTranfusiDarah.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbTranfusiDarah.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 23; i++) {
            TableColumn column = tbTranfusiDarah.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(95);
            }else if(i==1){
                column.setPreferredWidth(160);
            }else if(i==2){
                column.setPreferredWidth(70);
            }else if(i==3){
                column.setPreferredWidth(40);
            }else if(i==4){
                column.setPreferredWidth(30);
            }else if(i==5){
                column.setPreferredWidth(40);
            }else if(i==6){
                column.setPreferredWidth(160);
            }else if(i==7){
                column.setPreferredWidth(30);
            }else if(i==8){
                column.setPreferredWidth(40);
            }else if(i==9){
                column.setPreferredWidth(50);
            }else if(i==10){
                column.setPreferredWidth(50);
            }else if(i==11){
                column.setPreferredWidth(70);
            }else if(i==12){
                column.setPreferredWidth(30);
            }else if(i==13){
                column.setPreferredWidth(30);
            }else if(i==14){
                column.setPreferredWidth(90);
            }else if(i==15){
                column.setPreferredWidth(150);
            }else if(i==16){
                column.setPreferredWidth(45);
            }else if(i==17){
                column.setPreferredWidth(45);
            }else if(i==18){
                column.setPreferredWidth(45);
            }else if(i==19){
                column.setPreferredWidth(45);
            }else if(i==20){
                column.setPreferredWidth(150);
            }else if(i==21){
                column.setPreferredWidth(67);
            }else if(i==22){
                column.setPreferredWidth(150);
            }
        }
        tbTranfusiDarah.setDefaultRenderer(Object.class, new WarnaTable());
        
        petugas.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(petugas.getTable().getSelectedRow()!= -1){ 
                    if(pilih==1){
                        KodePetugasAftap.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                        NamaPetugasAftap.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                        KodePetugasAftap.requestFocus();
                    }else if(pilih==2){
                        KodePetugasUSaring.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                        NamaPetugasUSaring.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                        KodePetugasUSaring.requestFocus();
                    }else if(pilih==3){
                        KodePetugasCross.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                        NamaPetugasCross.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                        KodePetugasCross.requestFocus();
                    }                        
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
        
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        TCariMedis.setDocument(new batasInput((byte)100).getKata(TCariMedis));
        TCariNonMedis.setDocument(new batasInput((byte)100).getKata(TCariNonMedis));
        NomorDonor.setDocument(new batasInput((byte)15).getKata(NomorDonor));
        NamaPendonor.setDocument(new batasInput((byte)60).getKata(NamaPendonor));
        Umur.setDocument(new batasInput((byte)4).getOnlyAngka(Umur));
        Alamat.setDocument(new batasInput((byte)100).getKata(Alamat));
        Tensi.setDocument(new batasInput((byte)7).getKata(Tensi));
        NomorBag.setDocument(new batasInput((byte)3).getOnlyAngka(NomorBag));
        NomorSelang.setDocument(new batasInput((byte)10).getKata(NomorSelang));
        KodePetugasAftap.setDocument(new batasInput((byte)20).getKata(KodePetugasAftap));
        KodePetugasUSaring.setDocument(new batasInput((byte)20).getKata(KodePetugasUSaring));
        KodePetugasCross.setDocument(new batasInput((byte)20).getKata(KodePetugasCross));
        if(koneksiDB.cariCepat().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {tampil();}
                @Override
                public void removeUpdate(DocumentEvent e) {tampil();}
                @Override
                public void changedUpdate(DocumentEvent e) {tampil();}
            });
            TCariMedis.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {tampilMedis();}
                @Override
                public void removeUpdate(DocumentEvent e) {tampilMedis();}
                @Override
                public void changedUpdate(DocumentEvent e) {tampilMedis();}
            });
            TCariNonMedis.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {tampilNonMedis();}
                @Override
                public void removeUpdate(DocumentEvent e) {tampilNonMedis();}
                @Override
                public void changedUpdate(DocumentEvent e) {tampilNonMedis();}
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

        internalFrame1 = new widget.InternalFrame();
        TabRawat = new javax.swing.JTabbedPane();
        internalFrame2 = new widget.InternalFrame();
        panelisi5 = new widget.panelisi();
        jPanel3 = new javax.swing.JPanel();
        panelisi6 = new widget.panelisi();
        label10 = new widget.Label();
        TCariMedis = new widget.TextBox();
        BtnCari2 = new widget.Button();
        BtnAll1 = new widget.Button();
        Scroll1 = new widget.ScrollPane();
        tbMedis = new widget.Table();
        jPanel4 = new javax.swing.JPanel();
        panelisi7 = new widget.panelisi();
        label11 = new widget.Label();
        TCariNonMedis = new widget.TextBox();
        BtnCari3 = new widget.Button();
        BtnAll2 = new widget.Button();
        Scroll2 = new widget.ScrollPane();
        tbNonMedis = new widget.Table();
        scrollPane1 = new widget.ScrollPane();
        panelisi4 = new widget.panelisi();
        label34 = new widget.Label();
        NomorDonor = new widget.TextBox();
        label36 = new widget.Label();
        NamaPendonor = new widget.TextBox();
        label32 = new widget.Label();
        Tanggal = new widget.Tanggal();
        jLabel8 = new widget.Label();
        Dinas = new widget.ComboBox();
        label35 = new widget.Label();
        Umur = new widget.TextBox();
        label37 = new widget.Label();
        label38 = new widget.Label();
        Alamat = new widget.TextBox();
        jLabel9 = new widget.Label();
        JK = new widget.ComboBox();
        jLabel10 = new widget.Label();
        GolonganDarah = new widget.ComboBox();
        jLabel11 = new widget.Label();
        Resus = new widget.ComboBox();
        label39 = new widget.Label();
        Tensi = new widget.TextBox();
        label40 = new widget.Label();
        NomorBag = new widget.TextBox();
        label41 = new widget.Label();
        NomorSelang = new widget.TextBox();
        jLabel12 = new widget.Label();
        JenisBag = new widget.ComboBox();
        jLabel13 = new widget.Label();
        JenisDonor = new widget.ComboBox();
        jLabel14 = new widget.Label();
        TempatAftap = new widget.ComboBox();
        label17 = new widget.Label();
        KodePetugasAftap = new widget.TextBox();
        NamaPetugasAftap = new widget.TextBox();
        btnPetugasAftap = new widget.Button();
        label18 = new widget.Label();
        jLabel15 = new widget.Label();
        HBSAg = new widget.ComboBox();
        HCV = new widget.ComboBox();
        jLabel16 = new widget.Label();
        jLabel17 = new widget.Label();
        HIV = new widget.ComboBox();
        jLabel18 = new widget.Label();
        Spilis = new widget.ComboBox();
        label19 = new widget.Label();
        KodePetugasUSaring = new widget.TextBox();
        NamaPetugasUSaring = new widget.TextBox();
        btnPetugasUSaring = new widget.Button();
        jLabel19 = new widget.Label();
        HasilCrossmatch = new widget.ComboBox();
        label20 = new widget.Label();
        KodePetugasCross = new widget.TextBox();
        NamaPetugasCross = new widget.TextBox();
        btnPetugasCross = new widget.Button();
        internalFrame3 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbTranfusiDarah = new widget.Table();
        jPanel1 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        BtnPrint = new widget.Button();
        BtnAll = new widget.Button();
        BtnKeluar = new widget.Button();
        panelGlass9 = new widget.panelisi();
        jLabel20 = new widget.Label();
        TanggalCari1 = new widget.Tanggal();
        jLabel21 = new widget.Label();
        TanggalCari2 = new widget.Tanggal();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Donor Darah ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 40))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        TabRawat.setBackground(new java.awt.Color(250, 255, 245));
        TabRawat.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(230, 235, 225)));
        TabRawat.setForeground(new java.awt.Color(50, 70, 40));
        TabRawat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabRawat.setName("TabRawat"); // NOI18N
        TabRawat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatMouseClicked(evt);
            }
        });

        internalFrame2.setBorder(null);
        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi5.setBorder(null);
        panelisi5.setName("panelisi5"); // NOI18N
        panelisi5.setPreferredSize(new java.awt.Dimension(450, 77));
        panelisi5.setLayout(new java.awt.GridLayout(2, 0));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(237, 242, 232)), ".: Penggunaan BHP Medis ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 40))); // NOI18N
        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(300, 102));
        jPanel3.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi6.setBorder(null);
        panelisi6.setName("panelisi6"); // NOI18N
        panelisi6.setPreferredSize(new java.awt.Dimension(100, 43));
        panelisi6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        label10.setText("Key Word :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi6.add(label10);

        TCariMedis.setToolTipText("Alt+C");
        TCariMedis.setName("TCariMedis"); // NOI18N
        TCariMedis.setPreferredSize(new java.awt.Dimension(290, 23));
        TCariMedis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariMedisKeyPressed(evt);
            }
        });
        panelisi6.add(TCariMedis);

        BtnCari2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari2.setMnemonic('1');
        BtnCari2.setToolTipText("Alt+1");
        BtnCari2.setName("BtnCari2"); // NOI18N
        BtnCari2.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCari2ActionPerformed(evt);
            }
        });
        BtnCari2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCari2KeyPressed(evt);
            }
        });
        panelisi6.add(BtnCari2);

        BtnAll1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll1.setMnemonic('2');
        BtnAll1.setToolTipText("Alt+2");
        BtnAll1.setName("BtnAll1"); // NOI18N
        BtnAll1.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnAll1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAll1ActionPerformed(evt);
            }
        });
        BtnAll1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnAll1KeyPressed(evt);
            }
        });
        panelisi6.add(BtnAll1);

        jPanel3.add(panelisi6, java.awt.BorderLayout.PAGE_END);

        Scroll1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(245, 255, 235)));
        Scroll1.setName("Scroll1"); // NOI18N
        Scroll1.setOpaque(true);

        tbMedis.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbMedis.setName("tbMedis"); // NOI18N
        tbMedis.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbMedisMouseClicked(evt);
            }
        });
        tbMedis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbMedisKeyPressed(evt);
            }
        });
        Scroll1.setViewportView(tbMedis);

        jPanel3.add(Scroll1, java.awt.BorderLayout.CENTER);

        panelisi5.add(jPanel3);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(237, 242, 232)), ".: Penggunaan BHP Non Medis ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 40))); // NOI18N
        jPanel4.setName("jPanel4"); // NOI18N
        jPanel4.setOpaque(false);
        jPanel4.setPreferredSize(new java.awt.Dimension(300, 202));
        jPanel4.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi7.setBorder(null);
        panelisi7.setName("panelisi7"); // NOI18N
        panelisi7.setPreferredSize(new java.awt.Dimension(100, 43));
        panelisi7.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        label11.setText("Key Word :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi7.add(label11);

        TCariNonMedis.setToolTipText("Alt+C");
        TCariNonMedis.setName("TCariNonMedis"); // NOI18N
        TCariNonMedis.setPreferredSize(new java.awt.Dimension(290, 23));
        TCariNonMedis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariNonMedisKeyPressed(evt);
            }
        });
        panelisi7.add(TCariNonMedis);

        BtnCari3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari3.setMnemonic('1');
        BtnCari3.setToolTipText("Alt+1");
        BtnCari3.setName("BtnCari3"); // NOI18N
        BtnCari3.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCari3ActionPerformed(evt);
            }
        });
        BtnCari3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCari3KeyPressed(evt);
            }
        });
        panelisi7.add(BtnCari3);

        BtnAll2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll2.setMnemonic('2');
        BtnAll2.setToolTipText("Alt+2");
        BtnAll2.setName("BtnAll2"); // NOI18N
        BtnAll2.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnAll2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAll2ActionPerformed(evt);
            }
        });
        BtnAll2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnAll2KeyPressed(evt);
            }
        });
        panelisi7.add(BtnAll2);

        jPanel4.add(panelisi7, java.awt.BorderLayout.PAGE_END);

        Scroll2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(245, 255, 235)));
        Scroll2.setName("Scroll2"); // NOI18N
        Scroll2.setOpaque(true);

        tbNonMedis.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbNonMedis.setName("tbNonMedis"); // NOI18N
        tbNonMedis.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbNonMedisMouseClicked(evt);
            }
        });
        tbNonMedis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbNonMedisKeyPressed(evt);
            }
        });
        Scroll2.setViewportView(tbNonMedis);

        jPanel4.add(Scroll2, java.awt.BorderLayout.CENTER);

        panelisi5.add(jPanel4);

        internalFrame2.add(panelisi5, java.awt.BorderLayout.EAST);

        scrollPane1.setName("scrollPane1"); // NOI18N

        panelisi4.setName("panelisi4"); // NOI18N
        panelisi4.setPreferredSize(new java.awt.Dimension(100, 77));
        panelisi4.setLayout(null);

        label34.setText("Nomor Donor :");
        label34.setName("label34"); // NOI18N
        label34.setPreferredSize(new java.awt.Dimension(35, 23));
        panelisi4.add(label34);
        label34.setBounds(0, 12, 102, 23);

        NomorDonor.setHighlighter(null);
        NomorDonor.setName("NomorDonor"); // NOI18N
        NomorDonor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NomorDonorKeyPressed(evt);
            }
        });
        panelisi4.add(NomorDonor);
        NomorDonor.setBounds(105, 12, 120, 23);

        label36.setText("Nama Pendonor :");
        label36.setName("label36"); // NOI18N
        label36.setPreferredSize(new java.awt.Dimension(35, 23));
        panelisi4.add(label36);
        label36.setBounds(0, 42, 102, 23);

        NamaPendonor.setHighlighter(null);
        NamaPendonor.setName("NamaPendonor"); // NOI18N
        NamaPendonor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NamaPendonorKeyPressed(evt);
            }
        });
        panelisi4.add(NamaPendonor);
        NamaPendonor.setBounds(105, 42, 410, 23);

        label32.setText("Tanggal :");
        label32.setName("label32"); // NOI18N
        label32.setPreferredSize(new java.awt.Dimension(35, 23));
        panelisi4.add(label32);
        label32.setBounds(230, 12, 57, 23);

        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "28-12-2016" }));
        Tanggal.setDisplayFormat("dd-MM-yyyy");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeyPressed(evt);
            }
        });
        panelisi4.add(Tanggal);
        Tanggal.setBounds(290, 12, 95, 23);

        jLabel8.setText("Dinas :");
        jLabel8.setName("jLabel8"); // NOI18N
        panelisi4.add(jLabel8);
        jLabel8.setBounds(387, 12, 50, 23);

        Dinas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Pagi", "Siang", "Sore", "Malam" }));
        Dinas.setName("Dinas"); // NOI18N
        Dinas.setOpaque(false);
        Dinas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DinasKeyPressed(evt);
            }
        });
        panelisi4.add(Dinas);
        Dinas.setBounds(440, 12, 70, 23);

        label35.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label35.setText("Tahun");
        label35.setName("label35"); // NOI18N
        label35.setPreferredSize(new java.awt.Dimension(35, 23));
        panelisi4.add(label35);
        label35.setBounds(377, 72, 80, 23);

        Umur.setHighlighter(null);
        Umur.setName("Umur"); // NOI18N
        Umur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                UmurKeyPressed(evt);
            }
        });
        panelisi4.add(Umur);
        Umur.setBounds(313, 72, 60, 23);

        label37.setText("Umur :");
        label37.setName("label37"); // NOI18N
        label37.setPreferredSize(new java.awt.Dimension(35, 23));
        panelisi4.add(label37);
        label37.setBounds(230, 72, 80, 23);

        label38.setText("Alamat :");
        label38.setName("label38"); // NOI18N
        label38.setPreferredSize(new java.awt.Dimension(35, 23));
        panelisi4.add(label38);
        label38.setBounds(0, 102, 102, 23);

        Alamat.setHighlighter(null);
        Alamat.setName("Alamat"); // NOI18N
        Alamat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlamatKeyPressed(evt);
            }
        });
        panelisi4.add(Alamat);
        Alamat.setBounds(105, 102, 410, 23);

        jLabel9.setText("J.K. :");
        jLabel9.setName("jLabel9"); // NOI18N
        panelisi4.add(jLabel9);
        jLabel9.setBounds(0, 72, 102, 23);

        JK.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "LAKI-LAKI", "PEREMPUAN" }));
        JK.setName("JK"); // NOI18N
        JK.setOpaque(false);
        JK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JKKeyPressed(evt);
            }
        });
        panelisi4.add(JK);
        JK.setBounds(105, 72, 110, 23);

        jLabel10.setText("Golongan Darah :");
        jLabel10.setName("jLabel10"); // NOI18N
        panelisi4.add(jLabel10);
        jLabel10.setBounds(0, 132, 102, 23);

        GolonganDarah.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "A", "AB", "B", "O" }));
        GolonganDarah.setName("GolonganDarah"); // NOI18N
        GolonganDarah.setOpaque(false);
        GolonganDarah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GolonganDarahKeyPressed(evt);
            }
        });
        panelisi4.add(GolonganDarah);
        GolonganDarah.setBounds(105, 132, 60, 23);

        jLabel11.setText("Resus :");
        jLabel11.setName("jLabel11"); // NOI18N
        panelisi4.add(jLabel11);
        jLabel11.setBounds(190, 132, 70, 23);

        Resus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "(-)", "(+)" }));
        Resus.setName("Resus"); // NOI18N
        Resus.setOpaque(false);
        Resus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ResusKeyPressed(evt);
            }
        });
        panelisi4.add(Resus);
        Resus.setBounds(263, 132, 60, 23);

        label39.setText("Tensi :");
        label39.setName("label39"); // NOI18N
        label39.setPreferredSize(new java.awt.Dimension(35, 23));
        panelisi4.add(label39);
        label39.setBounds(352, 132, 80, 23);

        Tensi.setHighlighter(null);
        Tensi.setName("Tensi"); // NOI18N
        Tensi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TensiKeyPressed(evt);
            }
        });
        panelisi4.add(Tensi);
        Tensi.setBounds(435, 132, 80, 23);

        label40.setText("Nomor Bag :");
        label40.setName("label40"); // NOI18N
        label40.setPreferredSize(new java.awt.Dimension(35, 23));
        panelisi4.add(label40);
        label40.setBounds(0, 162, 102, 23);

        NomorBag.setHighlighter(null);
        NomorBag.setName("NomorBag"); // NOI18N
        NomorBag.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NomorBagKeyPressed(evt);
            }
        });
        panelisi4.add(NomorBag);
        NomorBag.setBounds(105, 162, 60, 23);

        label41.setText("Nomor Selang :");
        label41.setName("label41"); // NOI18N
        label41.setPreferredSize(new java.awt.Dimension(35, 23));
        panelisi4.add(label41);
        label41.setBounds(352, 162, 80, 23);

        NomorSelang.setHighlighter(null);
        NomorSelang.setName("NomorSelang"); // NOI18N
        NomorSelang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NomorSelangKeyPressed(evt);
            }
        });
        panelisi4.add(NomorSelang);
        NomorSelang.setBounds(435, 162, 80, 23);

        jLabel12.setText("Jenis Bag :");
        jLabel12.setName("jLabel12"); // NOI18N
        panelisi4.add(jLabel12);
        jLabel12.setBounds(190, 162, 70, 23);

        JenisBag.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "SB", "DB", "TB", "QB" }));
        JenisBag.setName("JenisBag"); // NOI18N
        JenisBag.setOpaque(false);
        JenisBag.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JenisBagKeyPressed(evt);
            }
        });
        panelisi4.add(JenisBag);
        JenisBag.setBounds(263, 162, 60, 23);

        jLabel13.setText("Jenis Donor :");
        jLabel13.setName("jLabel13"); // NOI18N
        panelisi4.add(jLabel13);
        jLabel13.setBounds(0, 192, 102, 23);

        JenisDonor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "DB", "DP", "DS" }));
        JenisDonor.setName("JenisDonor"); // NOI18N
        JenisDonor.setOpaque(false);
        JenisDonor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JenisDonorKeyPressed(evt);
            }
        });
        panelisi4.add(JenisDonor);
        JenisDonor.setBounds(105, 192, 60, 23);

        jLabel14.setText("Tempat Aftap :");
        jLabel14.setName("jLabel14"); // NOI18N
        panelisi4.add(jLabel14);
        jLabel14.setBounds(230, 192, 80, 23);

        TempatAftap.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Dalam Gedung", "Luar Gedung" }));
        TempatAftap.setName("TempatAftap"); // NOI18N
        TempatAftap.setOpaque(false);
        TempatAftap.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TempatAftapKeyPressed(evt);
            }
        });
        panelisi4.add(TempatAftap);
        TempatAftap.setBounds(313, 192, 202, 23);

        label17.setText("Hasil Uji Saring :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(65, 23));
        panelisi4.add(label17);
        label17.setBounds(0, 250, 102, 23);

        KodePetugasAftap.setName("KodePetugasAftap"); // NOI18N
        KodePetugasAftap.setPreferredSize(new java.awt.Dimension(80, 23));
        KodePetugasAftap.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodePetugasAftapKeyPressed(evt);
            }
        });
        panelisi4.add(KodePetugasAftap);
        KodePetugasAftap.setBounds(105, 222, 90, 23);

        NamaPetugasAftap.setEditable(false);
        NamaPetugasAftap.setName("NamaPetugasAftap"); // NOI18N
        NamaPetugasAftap.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi4.add(NamaPetugasAftap);
        NamaPetugasAftap.setBounds(198, 222, 286, 23);

        btnPetugasAftap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPetugasAftap.setMnemonic('1');
        btnPetugasAftap.setToolTipText("Alt+1");
        btnPetugasAftap.setName("btnPetugasAftap"); // NOI18N
        btnPetugasAftap.setPreferredSize(new java.awt.Dimension(28, 23));
        btnPetugasAftap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPetugasAftapActionPerformed(evt);
            }
        });
        panelisi4.add(btnPetugasAftap);
        btnPetugasAftap.setBounds(487, 222, 28, 23);

        label18.setText("Petugas Aftap :");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(65, 23));
        panelisi4.add(label18);
        label18.setBounds(0, 222, 102, 23);

        jLabel15.setText("HBSAg :");
        jLabel15.setName("jLabel15"); // NOI18N
        panelisi4.add(jLabel15);
        jLabel15.setBounds(40, 272, 97, 23);

        HBSAg.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Negatif", "Positif" }));
        HBSAg.setName("HBSAg"); // NOI18N
        HBSAg.setOpaque(false);
        HBSAg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HBSAgActionPerformed(evt);
            }
        });
        HBSAg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HBSAgKeyPressed(evt);
            }
        });
        panelisi4.add(HBSAg);
        HBSAg.setBounds(140, 272, 110, 23);

        HCV.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Negatif", "Positif" }));
        HCV.setName("HCV"); // NOI18N
        HCV.setOpaque(false);
        HCV.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HCVKeyPressed(evt);
            }
        });
        panelisi4.add(HCV);
        HCV.setBounds(341, 272, 110, 23);

        jLabel16.setText("HCV :");
        jLabel16.setName("jLabel16"); // NOI18N
        panelisi4.add(jLabel16);
        jLabel16.setBounds(268, 272, 70, 23);

        jLabel17.setText("HIV :");
        jLabel17.setName("jLabel17"); // NOI18N
        panelisi4.add(jLabel17);
        jLabel17.setBounds(40, 302, 97, 23);

        HIV.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Negatif", "Positif" }));
        HIV.setName("HIV"); // NOI18N
        HIV.setOpaque(false);
        HIV.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HIVKeyPressed(evt);
            }
        });
        panelisi4.add(HIV);
        HIV.setBounds(140, 302, 110, 23);

        jLabel18.setText("Spilis :");
        jLabel18.setName("jLabel18"); // NOI18N
        panelisi4.add(jLabel18);
        jLabel18.setBounds(268, 302, 70, 23);

        Spilis.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Negatif", "Positif" }));
        Spilis.setName("Spilis"); // NOI18N
        Spilis.setOpaque(false);
        Spilis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SpilisKeyPressed(evt);
            }
        });
        panelisi4.add(Spilis);
        Spilis.setBounds(341, 302, 110, 23);

        label19.setText("Petugas U.Saring :");
        label19.setName("label19"); // NOI18N
        label19.setPreferredSize(new java.awt.Dimension(65, 23));
        panelisi4.add(label19);
        label19.setBounds(0, 332, 102, 23);

        KodePetugasUSaring.setName("KodePetugasUSaring"); // NOI18N
        KodePetugasUSaring.setPreferredSize(new java.awt.Dimension(80, 23));
        KodePetugasUSaring.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodePetugasUSaringKeyPressed(evt);
            }
        });
        panelisi4.add(KodePetugasUSaring);
        KodePetugasUSaring.setBounds(105, 332, 90, 23);

        NamaPetugasUSaring.setEditable(false);
        NamaPetugasUSaring.setName("NamaPetugasUSaring"); // NOI18N
        NamaPetugasUSaring.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi4.add(NamaPetugasUSaring);
        NamaPetugasUSaring.setBounds(198, 332, 286, 23);

        btnPetugasUSaring.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPetugasUSaring.setMnemonic('1');
        btnPetugasUSaring.setToolTipText("Alt+1");
        btnPetugasUSaring.setName("btnPetugasUSaring"); // NOI18N
        btnPetugasUSaring.setPreferredSize(new java.awt.Dimension(28, 23));
        btnPetugasUSaring.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPetugasUSaringActionPerformed(evt);
            }
        });
        panelisi4.add(btnPetugasUSaring);
        btnPetugasUSaring.setBounds(487, 332, 28, 23);

        jLabel19.setText("Hasil Crossmatch :");
        jLabel19.setName("jLabel19"); // NOI18N
        panelisi4.add(jLabel19);
        jLabel19.setBounds(0, 362, 102, 23);

        HasilCrossmatch.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Negatif", "Positif" }));
        HasilCrossmatch.setName("HasilCrossmatch"); // NOI18N
        HasilCrossmatch.setOpaque(false);
        HasilCrossmatch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HasilCrossmatchKeyPressed(evt);
            }
        });
        panelisi4.add(HasilCrossmatch);
        HasilCrossmatch.setBounds(105, 362, 110, 23);

        label20.setText("Petugas Cross :");
        label20.setName("label20"); // NOI18N
        label20.setPreferredSize(new java.awt.Dimension(65, 23));
        panelisi4.add(label20);
        label20.setBounds(0, 392, 102, 23);

        KodePetugasCross.setName("KodePetugasCross"); // NOI18N
        KodePetugasCross.setPreferredSize(new java.awt.Dimension(80, 23));
        KodePetugasCross.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodePetugasCrossKeyPressed(evt);
            }
        });
        panelisi4.add(KodePetugasCross);
        KodePetugasCross.setBounds(105, 392, 90, 23);

        NamaPetugasCross.setEditable(false);
        NamaPetugasCross.setName("NamaPetugasCross"); // NOI18N
        NamaPetugasCross.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi4.add(NamaPetugasCross);
        NamaPetugasCross.setBounds(198, 392, 286, 23);

        btnPetugasCross.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPetugasCross.setMnemonic('1');
        btnPetugasCross.setToolTipText("Alt+1");
        btnPetugasCross.setName("btnPetugasCross"); // NOI18N
        btnPetugasCross.setPreferredSize(new java.awt.Dimension(28, 23));
        btnPetugasCross.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPetugasCrossActionPerformed(evt);
            }
        });
        panelisi4.add(btnPetugasCross);
        btnPetugasCross.setBounds(487, 392, 28, 23);

        scrollPane1.setViewportView(panelisi4);

        internalFrame2.add(scrollPane1, java.awt.BorderLayout.CENTER);

        TabRawat.addTab(".: Input Pendonor Darah  ", internalFrame2);

        internalFrame3.setBorder(null);
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbTranfusiDarah.setAutoCreateRowSorter(true);
        tbTranfusiDarah.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbTranfusiDarah.setName("tbTranfusiDarah"); // NOI18N
        tbTranfusiDarah.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbTranfusiDarahMouseClicked(evt);
            }
        });
        tbTranfusiDarah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbTranfusiDarahKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbTranfusiDarah);

        internalFrame3.add(Scroll, java.awt.BorderLayout.CENTER);

        TabRawat.addTab(".: Daftar Pendonor Darah  ", internalFrame3);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(816, 100));
        jPanel1.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(55, 55));
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan.setMnemonic('S');
        BtnSimpan.setText("Simpan");
        BtnSimpan.setToolTipText("Alt+S");
        BtnSimpan.setName("BtnSimpan"); // NOI18N
        BtnSimpan.setPreferredSize(new java.awt.Dimension(100, 30));
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
        panelGlass8.add(BtnAll);

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

        jPanel1.add(panelGlass8, java.awt.BorderLayout.CENTER);

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel20.setText("Tanggal :");
        jLabel20.setName("jLabel20"); // NOI18N
        jLabel20.setPreferredSize(new java.awt.Dimension(50, 23));
        panelGlass9.add(jLabel20);

        TanggalCari1.setEditable(false);
        TanggalCari1.setForeground(new java.awt.Color(50, 70, 50));
        TanggalCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "28-12-2016" }));
        TanggalCari1.setDisplayFormat("dd-MM-yyyy");
        TanggalCari1.setName("TanggalCari1"); // NOI18N
        TanggalCari1.setOpaque(false);
        TanggalCari1.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass9.add(TanggalCari1);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("s.d");
        jLabel21.setName("jLabel21"); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(24, 23));
        panelGlass9.add(jLabel21);

        TanggalCari2.setEditable(false);
        TanggalCari2.setForeground(new java.awt.Color(50, 70, 50));
        TanggalCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "28-12-2016" }));
        TanggalCari2.setDisplayFormat("dd-MM-yyyy");
        TanggalCari2.setName("TanggalCari2"); // NOI18N
        TanggalCari2.setOpaque(false);
        TanggalCari2.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass9.add(TanggalCari2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(200, 23));
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

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(75, 23));
        panelGlass9.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass9.add(LCount);

        jPanel1.add(panelGlass9, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel1, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void NomorDonorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NomorDonorKeyPressed
      Valid.pindah(evt,TCari,Tanggal);
}//GEN-LAST:event_NomorDonorKeyPressed

private void NamaPendonorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NamaPendonorKeyPressed
    Valid.pindah(evt,Dinas,JK);
}//GEN-LAST:event_NamaPendonorKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        emptTeks();
        tampilMedis();
        tampilNonMedis();
    }//GEN-LAST:event_formWindowOpened

    private void TCariMedisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariMedisKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCari2ActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari2.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }
    }//GEN-LAST:event_TCariMedisKeyPressed

    private void BtnCari2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCari2ActionPerformed
        tampilMedis();
    }//GEN-LAST:event_BtnCari2ActionPerformed

    private void BtnCari2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCari2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnCari2KeyPressed

    private void BtnAll1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAll1ActionPerformed
        TCariMedis.setText("");
        tampilMedis();
    }//GEN-LAST:event_BtnAll1ActionPerformed

    private void BtnAll1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAll1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnAll1KeyPressed

    private void tbMedisMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbMedisMouseClicked
       
    }//GEN-LAST:event_tbMedisMouseClicked

    private void tbMedisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbMedisKeyPressed
        
    }//GEN-LAST:event_tbMedisKeyPressed

    private void TCariNonMedisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariNonMedisKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCari3ActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari3.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }
    }//GEN-LAST:event_TCariNonMedisKeyPressed

    private void BtnCari3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCari3ActionPerformed
        tampilNonMedis();
    }//GEN-LAST:event_BtnCari3ActionPerformed

    private void BtnCari3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCari3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnCari3KeyPressed

    private void BtnAll2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAll2ActionPerformed
        TCariNonMedis.setText("");
        tampilNonMedis();
    }//GEN-LAST:event_BtnAll2ActionPerformed

    private void BtnAll2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAll2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnAll2KeyPressed

    private void tbNonMedisMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbNonMedisMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbNonMedisMouseClicked

    private void tbNonMedisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbNonMedisKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbNonMedisKeyPressed

    private void tbTranfusiDarahMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbTranfusiDarahMouseClicked
        /*if(tabMode.getRowCount()!=0){
            try {
                getData();
                if(evt.getClickCount()==2){
                    TabRawat.setSelectedIndex(0);
                }
            } catch (java.lang.NullPointerException e) {
            }
        }*/
    }//GEN-LAST:event_tbTranfusiDarahMouseClicked

    private void tbTranfusiDarahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbTranfusiDarahKeyPressed
        /*if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }else if(evt.getKeyCode()==KeyEvent.VK_SPACE){
                try {
                    getData();
                    TabRawat.setSelectedIndex(0);
                } catch (java.lang.NullPointerException e) {
                }
            }
        }*/
    }//GEN-LAST:event_tbTranfusiDarahKeyPressed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if(TabRawat.getSelectedIndex()==1){
            tampil();
        }
    }//GEN-LAST:event_TabRawatMouseClicked

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(NomorDonor.getText().trim().equals("")){
            Valid.textKosong(NomorDonor,"Nomor Donor");
        }else if(NamaPendonor.getText().trim().equals("")){
            Valid.textKosong(NamaPendonor,"Nama Pendonor");
        }else if(Umur.getText().trim().equals("")){
            Valid.textKosong(Umur,"Umur");
        }else if(Tensi.getText().trim().equals("")){
            Valid.textKosong(Tensi,"Tensi");
        }else if(NomorBag.getText().trim().equals("")){
            Valid.textKosong(NomorBag,"Nomor Bag");
        }else if(NomorSelang.getText().trim().equals("")){
            Valid.textKosong(NomorSelang,"Nomor Selang");
        }else if(KodePetugasAftap.getText().trim().equals("")||NamaPetugasAftap.getText().trim().equals("")){
            Valid.textKosong(KodePetugasAftap,"Petugas Aftap");
        }else if(KodePetugasUSaring.getText().trim().equals("")||NamaPetugasUSaring.getText().trim().equals("")){
            Valid.textKosong(KodePetugasUSaring,"Petugas Uji Saring");
        }else if(KodePetugasCross.getText().trim().equals("")||NamaPetugasCross.getText().trim().equals("")){
            Valid.textKosong(KodePetugasCross,"Petugas Cross");
        }else{
            Sequel.AutoComitFalse();
            if(Sequel.menyimpantf("utd_donor","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","Nomor Donor",23,new String[]{
                    NomorDonor.getText(),NamaPendonor.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+""), 
                    Dinas.getSelectedItem().toString(),JK.getSelectedItem().toString().substring(0,1),Umur.getText(), 
                    Alamat.getText(),GolonganDarah.getSelectedItem().toString(),Resus.getSelectedItem().toString(), 
                    Tensi.getText(),NomorBag.getText(),NomorSelang.getText(),JenisBag.getSelectedItem().toString(), 
                    JenisDonor.getSelectedItem().toString(),TempatAftap.getSelectedItem().toString(), 
                    KodePetugasAftap.getText(),HBSAg.getSelectedItem().toString(),HCV.getSelectedItem().toString(), 
                    HIV.getSelectedItem().toString(),Spilis.getSelectedItem().toString(),KodePetugasUSaring.getText(), 
                    HasilCrossmatch.getSelectedItem().toString(),KodePetugasCross.getText()
                })==true){                    
                    for(i=0;i<tbMedis.getRowCount();i++){  
                        try {
                            if(Valid.SetAngka(tbMedis.getValueAt(i,0).toString())>0){
                                if(Sequel.menyimpantf2("utd_penggunaan_medis_donor","?,?,?,?,?","BHP Medis",5,new String[]{
                                    NomorDonor.getText(),tbMedis.getValueAt(i,1).toString(),tbMedis.getValueAt(i,0).toString(),tbMedis.getValueAt(i,3).toString(),
                                    Double.toString(Double.parseDouble(tbMedis.getValueAt(i,0).toString())*Double.parseDouble(tbMedis.getValueAt(i,3).toString()))
                                })==true){
                                    Sequel.menyimpan("utd_stok_medis","'"+tbMedis.getValueAt(i,1).toString()+"','-"+tbMedis.getValueAt(i,0).toString()+"','"+tbMedis.getValueAt(i,3).toString()+"'", 
                                        "stok=stok-"+tbMedis.getValueAt(i,0).toString()+"","kode_brng='"+tbMedis.getValueAt(i,1).toString()+"'");
                                    tbMedis.setValueAt(null,i,0);        
                                    tbMedis.setValueAt(0,i,4);
                                }   
                            }                                
                        } catch (Exception e) {
                        }                    
                    }
                    for(i=0;i<tbNonMedis.getRowCount();i++){  
                        try {
                            if(Valid.SetAngka(tbNonMedis.getValueAt(i,0).toString())>0){
                                if(Sequel.menyimpantf2("utd_penggunaan_penunjang_donor","?,?,?,?,?","BHP Non Medis",5,new String[]{
                                    NomorDonor.getText(),tbNonMedis.getValueAt(i,1).toString(),tbNonMedis.getValueAt(i,0).toString(),tbNonMedis.getValueAt(i,3).toString(),
                                    Double.toString(Double.parseDouble(tbMedis.getValueAt(i,0).toString())*Double.parseDouble(tbMedis.getValueAt(i,3).toString()))
                                })==true){
                                    Sequel.menyimpan("utd_stok_penunjang","'"+tbNonMedis.getValueAt(i,1).toString()+"','-"+tbNonMedis.getValueAt(i,0).toString()+"','"+tbNonMedis.getValueAt(i,3).toString()+"'", 
                                        "stok=stok-"+tbNonMedis.getValueAt(i,0).toString()+"","kode_brng='"+tbNonMedis.getValueAt(i,1).toString()+"'");
                                    tbNonMedis.setValueAt(null,i,0);        
                                    tbNonMedis.setValueAt(0,i,4);
                                }   
                            }                                
                        } catch (Exception e) {
                        }                    
                    }
                    tampil();
                    emptTeks();
            }
            Sequel.AutoComitTrue();            
        }
    }//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,KodePetugasCross,BtnBatal);
        }
    }//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed

        emptTeks();
    }//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            emptTeks();
        }else{Valid.pindah(evt, BtnSimpan, BtnHapus);}
    }//GEN-LAST:event_BtnBatalKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        for(i=0;i<tbTranfusiDarah.getRowCount();i++){
            if(tbTranfusiDarah.getValueAt(i,0).toString().equals("true")){
                Sequel.mengedit("paket_operasi","kode_paket='"+tbTranfusiDarah.getValueAt(i,1).toString()+"'","status='0'");
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

    }//GEN-LAST:event_BtnEditActionPerformed

    private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnEditActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnPrint);
        }
    }//GEN-LAST:event_BtnEditKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(! TCari.getText().trim().equals("")){
            BtnCariActionPerformed(evt);
        }
        /*if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            Map<String, Object> param = new HashMap<>();
            param.put("namars",var.getnamars());
            param.put("alamatrs",var.getalamatrs());
            param.put("kotars",var.getkabupatenrs());
            param.put("propinsirs",var.getpropinsirs());
            param.put("kontakrs",var.getkontakrs());
            param.put("emailrs",var.getemailrs());
            param.put("logo",Sequel.cariGambar("select logo from setting"));
            Valid.MyReport("rptPaketOperasi.jrxml","report","::[ Data Paket Operasi ]::",
                "select paket_operasi.kode_paket, paket_operasi.nm_perawatan,(paket_operasi.operator1+paket_operasi.operator2+paket_operasi.operator3+"+
                "paket_operasi.asisten_operator1+paket_operasi.asisten_operator2+paket_operasi.instrumen+"+
                "paket_operasi.dokter_anak+paket_operasi.perawaat_resusitas+"+
                "paket_operasi.alat+paket_operasi.dokter_anestesi+paket_operasi.asisten_anestesi+"+
                "paket_operasi.bidan+paket_operasi.bidan2+paket_operasi.bidan3+paket_operasi.perawat_luar+"+
                "paket_operasi.sewa_ok+paket_operasi.akomodasi+paket_operasi.bagian_rs+"+
                "paket_operasi.omloop+paket_operasi.omloop2+paket_operasi.omloop3+"+
                "paket_operasi.sarpras+paket_operasi.dokter_pjanak+paket_operasi.dokter_umum) as jumlah "+
                "from paket_operasi inner join penjab on penjab.kd_pj=paket_operasi.kd_pj "+
                "where paket_operasi.status='1' and paket_operasi.kode_paket like '%"+TCari.getText()+"%' or "+
                "paket_operasi.status='1' and paket_operasi.nm_perawatan like '%"+TCari.getText()+"%' or "+
                "paket_operasi.status='1' and penjab.png_jawab like '%"+TCari.getText()+"%' order by paket_operasi.kode_paket ",param);
        }*/
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnEdit, BtnAll);
        }
    }//GEN-LAST:event_BtnPrintKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        tampil();
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            tampil();
            TCari.setText("");
        }else{
            Valid.pindah(evt, BtnPrint, BtnKeluar);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnAll,TCari);}
    }//GEN-LAST:event_BtnKeluarKeyPressed

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

    private void TanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKeyPressed
        Valid.pindah(evt,NomorDonor,Dinas);
    }//GEN-LAST:event_TanggalKeyPressed

    private void UmurKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_UmurKeyPressed
        Valid.pindah(evt,JK,Alamat);
    }//GEN-LAST:event_UmurKeyPressed

    private void AlamatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlamatKeyPressed
        Valid.pindah(evt,Umur,GolonganDarah);
    }//GEN-LAST:event_AlamatKeyPressed

    private void TensiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TensiKeyPressed
        Valid.pindah(evt,Resus,NomorBag);
    }//GEN-LAST:event_TensiKeyPressed

    private void NomorBagKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NomorBagKeyPressed
        Valid.pindah(evt,Tensi,JenisBag);
    }//GEN-LAST:event_NomorBagKeyPressed

    private void NomorSelangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NomorSelangKeyPressed
        Valid.pindah(evt,JenisBag,JenisDonor);
    }//GEN-LAST:event_NomorSelangKeyPressed

    private void KodePetugasAftapKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodePetugasAftapKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama from petugas where nip=?",NamaPetugasAftap,KodePetugasAftap.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnPetugasAftapActionPerformed(null);
        }else{
            Valid.pindah(evt,TempatAftap,HBSAg);
        }
    }//GEN-LAST:event_KodePetugasAftapKeyPressed

    private void btnPetugasAftapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPetugasAftapActionPerformed
        pilih=1;
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setAlwaysOnTop(false);
        petugas.setVisible(true);
    }//GEN-LAST:event_btnPetugasAftapActionPerformed

    private void HBSAgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HBSAgActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_HBSAgActionPerformed

    private void KodePetugasUSaringKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodePetugasUSaringKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama from petugas where nip=?",NamaPetugasUSaring,KodePetugasUSaring.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnPetugasUSaringActionPerformed(null);
        }else{
            Valid.pindah(evt,Spilis,HasilCrossmatch);
        }
    }//GEN-LAST:event_KodePetugasUSaringKeyPressed

    private void btnPetugasUSaringActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPetugasUSaringActionPerformed
        pilih=2;
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setAlwaysOnTop(false);
        petugas.setVisible(true);
    }//GEN-LAST:event_btnPetugasUSaringActionPerformed

    private void KodePetugasCrossKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodePetugasCrossKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama from petugas where nip=?",NamaPetugasCross,KodePetugasCross.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnPetugasCrossActionPerformed(null);
        }else{
            Valid.pindah(evt,HasilCrossmatch,BtnSimpan);
        }
    }//GEN-LAST:event_KodePetugasCrossKeyPressed

    private void btnPetugasCrossActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPetugasCrossActionPerformed
        pilih=3;
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setAlwaysOnTop(false);
        petugas.setVisible(true);
    }//GEN-LAST:event_btnPetugasCrossActionPerformed

    private void DinasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DinasKeyPressed
        Valid.pindah(evt,Tanggal,NamaPendonor);
    }//GEN-LAST:event_DinasKeyPressed

    private void JKKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JKKeyPressed
        Valid.pindah(evt,NamaPendonor,Umur);
    }//GEN-LAST:event_JKKeyPressed

    private void GolonganDarahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GolonganDarahKeyPressed
        Valid.pindah(evt,Alamat,Resus);
    }//GEN-LAST:event_GolonganDarahKeyPressed

    private void ResusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ResusKeyPressed
        Valid.pindah(evt,GolonganDarah,Tensi);
    }//GEN-LAST:event_ResusKeyPressed

    private void JenisBagKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JenisBagKeyPressed
        Valid.pindah(evt,NomorBag,NomorSelang);
    }//GEN-LAST:event_JenisBagKeyPressed

    private void JenisDonorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JenisDonorKeyPressed
        Valid.pindah(evt,NomorSelang,TempatAftap);
    }//GEN-LAST:event_JenisDonorKeyPressed

    private void TempatAftapKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TempatAftapKeyPressed
        Valid.pindah(evt,JenisDonor,KodePetugasAftap);
    }//GEN-LAST:event_TempatAftapKeyPressed

    private void HBSAgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HBSAgKeyPressed
        Valid.pindah(evt,KodePetugasAftap,HCV);
    }//GEN-LAST:event_HBSAgKeyPressed

    private void HCVKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HCVKeyPressed
        Valid.pindah(evt,HBSAg,HIV);
    }//GEN-LAST:event_HCVKeyPressed

    private void HIVKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HIVKeyPressed
        Valid.pindah(evt,HCV,Spilis);
    }//GEN-LAST:event_HIVKeyPressed

    private void SpilisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SpilisKeyPressed
        Valid.pindah(evt,HIV,KodePetugasUSaring);
    }//GEN-LAST:event_SpilisKeyPressed

    private void HasilCrossmatchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HasilCrossmatchKeyPressed
        Valid.pindah(evt,KodePetugasUSaring,KodePetugasCross);
    }//GEN-LAST:event_HasilCrossmatchKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            UTDDonor dialog = new UTDDonor(new javax.swing.JFrame(), true);
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
    private widget.TextBox Alamat;
    private widget.Button BtnAll;
    private widget.Button BtnAll1;
    private widget.Button BtnAll2;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnCari2;
    private widget.Button BtnCari3;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.ComboBox Dinas;
    private widget.ComboBox GolonganDarah;
    private widget.ComboBox HBSAg;
    private widget.ComboBox HCV;
    private widget.ComboBox HIV;
    private widget.ComboBox HasilCrossmatch;
    private widget.ComboBox JK;
    private widget.ComboBox JenisBag;
    private widget.ComboBox JenisDonor;
    private widget.TextBox KodePetugasAftap;
    private widget.TextBox KodePetugasCross;
    private widget.TextBox KodePetugasUSaring;
    private widget.Label LCount;
    private widget.TextBox NamaPendonor;
    private widget.TextBox NamaPetugasAftap;
    private widget.TextBox NamaPetugasCross;
    private widget.TextBox NamaPetugasUSaring;
    private widget.TextBox NomorBag;
    private widget.TextBox NomorDonor;
    private widget.TextBox NomorSelang;
    private widget.ComboBox Resus;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll1;
    private widget.ScrollPane Scroll2;
    private widget.ComboBox Spilis;
    private widget.TextBox TCari;
    private widget.TextBox TCariMedis;
    private widget.TextBox TCariNonMedis;
    private javax.swing.JTabbedPane TabRawat;
    private widget.Tanggal Tanggal;
    private widget.Tanggal TanggalCari1;
    private widget.Tanggal TanggalCari2;
    private widget.ComboBox TempatAftap;
    private widget.TextBox Tensi;
    private widget.TextBox Umur;
    private widget.Button btnPetugasAftap;
    private widget.Button btnPetugasCross;
    private widget.Button btnPetugasUSaring;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel12;
    private widget.Label jLabel13;
    private widget.Label jLabel14;
    private widget.Label jLabel15;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
    private widget.Label jLabel18;
    private widget.Label jLabel19;
    private widget.Label jLabel20;
    private widget.Label jLabel21;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private widget.Label label10;
    private widget.Label label11;
    private widget.Label label17;
    private widget.Label label18;
    private widget.Label label19;
    private widget.Label label20;
    private widget.Label label32;
    private widget.Label label34;
    private widget.Label label35;
    private widget.Label label36;
    private widget.Label label37;
    private widget.Label label38;
    private widget.Label label39;
    private widget.Label label40;
    private widget.Label label41;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.panelisi panelisi4;
    private widget.panelisi panelisi5;
    private widget.panelisi panelisi6;
    private widget.panelisi panelisi7;
    private widget.ScrollPane scrollPane1;
    private widget.Table tbMedis;
    private widget.Table tbNonMedis;
    private widget.Table tbTranfusiDarah;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabModeTranfusi);
        try{    
            pstranfusi=koneksi.prepareStatement(
                    "select * from utd_donor where tanggal between ? and ? and no_donor like ? or "+
                    "tanggal between ? and ? and nama like ? or "+
                    "tanggal between ? and ? and alamat like ? or "+
                    "tanggal between ? and ? and jenis_donor like ? or "+
                    "tanggal between ? and ? and tempat_aftap like ? or "+
                    "tanggal between ? and ? and jenis_bag like ? or "+
                    "tanggal between ? and ? and dinas like ? order by tanggal,no_donor "
            );
            try {
                pstranfusi.setString(1,Valid.SetTgl(TanggalCari1.getSelectedItem()+""));
                pstranfusi.setString(2,Valid.SetTgl(TanggalCari2.getSelectedItem()+""));
                pstranfusi.setString(3,"%"+TCari.getText().trim()+"%");
                pstranfusi.setString(4,Valid.SetTgl(TanggalCari1.getSelectedItem()+""));
                pstranfusi.setString(5,Valid.SetTgl(TanggalCari2.getSelectedItem()+""));
                pstranfusi.setString(6,"%"+TCari.getText().trim()+"%");
                pstranfusi.setString(7,Valid.SetTgl(TanggalCari1.getSelectedItem()+""));
                pstranfusi.setString(8,Valid.SetTgl(TanggalCari2.getSelectedItem()+""));
                pstranfusi.setString(9,"%"+TCari.getText().trim()+"%");
                pstranfusi.setString(10,Valid.SetTgl(TanggalCari1.getSelectedItem()+""));
                pstranfusi.setString(11,Valid.SetTgl(TanggalCari2.getSelectedItem()+""));
                pstranfusi.setString(12,"%"+TCari.getText().trim()+"%");
                pstranfusi.setString(13,Valid.SetTgl(TanggalCari1.getSelectedItem()+""));
                pstranfusi.setString(14,Valid.SetTgl(TanggalCari2.getSelectedItem()+""));
                pstranfusi.setString(15,"%"+TCari.getText().trim()+"%");
                pstranfusi.setString(16,Valid.SetTgl(TanggalCari1.getSelectedItem()+""));
                pstranfusi.setString(17,Valid.SetTgl(TanggalCari2.getSelectedItem()+""));
                pstranfusi.setString(18,"%"+TCari.getText().trim()+"%");
                pstranfusi.setString(19,Valid.SetTgl(TanggalCari1.getSelectedItem()+""));
                pstranfusi.setString(20,Valid.SetTgl(TanggalCari2.getSelectedItem()+""));
                pstranfusi.setString(21,"%"+TCari.getText().trim()+"%");
                rstranfusi=pstranfusi.executeQuery();
                while(rstranfusi.next()){
                    tabModeTranfusi.addRow(new Object[]{
                        rstranfusi.getString("no_donor"),rstranfusi.getString("nama"),rstranfusi.getString("tanggal"), 
                        rstranfusi.getString("dinas"),rstranfusi.getString("jk"),rstranfusi.getString("umur"),
                        rstranfusi.getString("alamat"),rstranfusi.getString("golongan_darah"),rstranfusi.getString("resus"),
                        rstranfusi.getString("tensi"),rstranfusi.getString("no_bag"),rstranfusi.getString("no_selang"),
                        rstranfusi.getString("jenis_bag"),rstranfusi.getString("jenis_donor"),rstranfusi.getString("tempat_aftap"),
                        Sequel.cariIsi("select nama from petugas where nip=?",rstranfusi.getString("petugas_aftap")),rstranfusi.getString("hbsag"),rstranfusi.getString("hcv"),
                        rstranfusi.getString("hiv"),rstranfusi.getString("spilis"),Sequel.cariIsi("select nama from petugas where nip=?",rstranfusi.getString("petugas_u_saring")),
                        rstranfusi.getString("hasil_cross_match"),Sequel.cariIsi("select nama from petugas where nip=?",rstranfusi.getString("petugas_cross"))
                    });
                }                
            } catch (Exception e) {
                System.out.println(e);
            } finally{
                if(rstranfusi!=null){
                    rstranfusi.close();
                }
                if(pstranfusi!=null){
                    pstranfusi.close();
                }
            }            
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }

    private void tampilMedis() {
        row=tbMedis.getRowCount();
        jml=0;
        for(i=0;i<row;i++){
            try {
                if(Double.parseDouble(tbMedis.getValueAt(i,0).toString())>0){
                    jml++;
                }
            } catch (Exception e) {
                jml=jml+0;
            } 
        }
        
        kodebarang=new String[jml];
        namabarang=new String[jml];
        satuan=new String[jml];
        hbeli=new String[jml];
        total=new String[jml];
        jumlah=new String[jml];
        stokasal=new String[jml];
        index=0;        
        for(i=0;i<row;i++){
            try {
                if(Double.parseDouble(tbMedis.getValueAt(i,0).toString())>0){
                    jumlah[index]=tbMedis.getValueAt(i,0).toString();
                    kodebarang[index]=tbMedis.getValueAt(i,1).toString();
                    namabarang[index]=tbMedis.getValueAt(i,2).toString();
                    hbeli[index]=tbMedis.getValueAt(i,3).toString();
                    total[index]=tbMedis.getValueAt(i,4).toString();
                    satuan[index]=tbMedis.getValueAt(i,5).toString();
                    stokasal[index]=tbMedis.getValueAt(i,6).toString();
                    index++;
                }
            } catch (Exception e) {
            }
        }
        Valid.tabelKosong(tabModeMedis);
        for(i=0;i<jml;i++){
            tabModeMedis.addRow(new Object[]{jumlah[i],kodebarang[i],namabarang[i],hbeli[i],total[i],satuan[i],stokasal[i]});
        }
        
        try{
            ps=koneksi.prepareStatement("select databarang.kode_brng, databarang.nama_brng,utd_stok_medis.hargaterakhir,databarang.kode_sat, "+
                " utd_stok_medis.stok from databarang inner join utd_stok_medis on databarang.kode_brng=utd_stok_medis.kode_brng "+
                " where databarang.status='1' and databarang.kode_brng like ? or "+
                " databarang.status='1' and databarang.nama_brng like ? order by databarang.nama_brng");
            try {
                ps.setString(1,"%"+TCariMedis.getText().trim()+"%");
                ps.setString(2,"%"+TCariMedis.getText().trim()+"%");
                rs=ps.executeQuery();
                while(rs.next()){                
                    tabModeMedis.addRow(new Object[]{null,rs.getString(1),rs.getString(2),rs.getString(3),0,rs.getString(4),rs.getString(5)});
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
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }
    
    private void tampilNonMedis() {
        row=tbNonMedis.getRowCount();
        jml=0;
        for(i=0;i<row;i++){
            try {
                if(Double.parseDouble(tbNonMedis.getValueAt(i,0).toString())>0){
                    jml++;
                }
            } catch (Exception e) {
                jml=jml+0;
            } 
        }
        
        kodebarang=new String[jml];
        namabarang=new String[jml];
        satuan=new String[jml];
        hbeli=new String[jml];
        total=new String[jml];
        jumlah=new String[jml];
        stokasal=new String[jml];
        index=0;        
        for(i=0;i<row;i++){
            try {
                if(Double.parseDouble(tbNonMedis.getValueAt(i,0).toString())>0){
                    jumlah[index]=tbNonMedis.getValueAt(i,0).toString();
                    kodebarang[index]=tbNonMedis.getValueAt(i,1).toString();
                    namabarang[index]=tbNonMedis.getValueAt(i,2).toString();
                    hbeli[index]=tbNonMedis.getValueAt(i,3).toString();
                    total[index]=tbNonMedis.getValueAt(i,4).toString();
                    satuan[index]=tbNonMedis.getValueAt(i,5).toString();
                    stokasal[index]=tbNonMedis.getValueAt(i,6).toString();
                    index++;
                }
            } catch (Exception e) {
            }
        }
        Valid.tabelKosong(tabModeNonMedis);
        for(i=0;i<jml;i++){
            tabModeNonMedis.addRow(new Object[]{jumlah[i],kodebarang[i],namabarang[i],hbeli[i],total[i],satuan[i],stokasal[i]});
        }
        
        try{
            ps2=koneksi.prepareStatement("select ipsrsbarang.kode_brng, ipsrsbarang.nama_brng,utd_stok_penunjang.hargaterakhir,ipsrsbarang.kode_sat, "+
                " utd_stok_penunjang.stok from ipsrsbarang inner join utd_stok_penunjang on ipsrsbarang.kode_brng=utd_stok_penunjang.kode_brng "+
                " where ipsrsbarang.kode_brng like ? or ipsrsbarang.nama_brng like ? order by ipsrsbarang.nama_brng");
            try {
                ps2.setString(1,"%"+TCariNonMedis.getText().trim()+"%");
                ps2.setString(2,"%"+TCariNonMedis.getText().trim()+"%");
                rs2=ps2.executeQuery();
                while(rs2.next()){                
                    tabModeNonMedis.addRow(new Object[]{null,rs2.getString(1),rs2.getString(2),rs2.getString(3),0,rs2.getString(4),rs2.getString(5)});
                }   
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
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }
    
    public void emptTeks() {
        NomorDonor.setText("");
        NamaPendonor.setText("");
        Umur.setText("");
        Alamat.setText("");
        Tensi.setText("");
        NomorBag.setText("");
        NomorSelang.setText("");
        KodePetugasAftap.setText("");
        NamaPetugasAftap.setText("");
        KodePetugasUSaring.setText("");
        NamaPetugasUSaring.setText("");
        KodePetugasCross.setText("");
        NamaPetugasCross.setText("");
        NomorDonor.requestFocus();        
        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(no_donor,4),signed)),0) from utd_donor where tanggal like '%"+Valid.SetTgl(Tanggal.getSelectedItem()+"").substring(0,7)+"%'",dateformat.format(Tanggal.getDate()).substring(0,7)+"/UTD",4,NomorDonor); 
    }

    private void getData() {
        
    }

    public JTextField getTextField(){
        return NomorDonor;
    }

    public JButton getButton(){
        return BtnKeluar;
    }
    
    public void isCek(){
        
    }
}

package permintaan;
import fungsi.BackgroundMusic;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariDokter;
import simrskhanza.DlgCariBangsal;
import simrskhanza.DlgCariPoli;
import simrskhanza.DlgPeriksaRadiologi;

public class DlgCariPermintaanRadiologi extends javax.swing.JDialog {
    private final DefaultTableModel tabMode,tabMode2,tabMode3,tabMode4;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private DlgCariDokter dokter=new DlgCariDokter(null,false);
    private DlgCariPoli poli=new DlgCariPoli(null,false);
    private DlgCariBangsal ruang=new DlgCariBangsal(null,false);
    private int i,nilai_detik,permintaanbaru=0;
    private PreparedStatement ps,ps2;
    private final Properties prop = new Properties();
    private BackgroundMusic music;
    private ResultSet rs,rs2;
    private Date now;
    private boolean aktif=false;
    private String alarm="",formalarm="",nol_detik,detik,tglsampel="",tglhasil="",norm="",kamar="",namakamar="",diagnosa="";
    
    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public DlgCariPermintaanRadiologi(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        WindowAmbilSampel.setSize(530,80);
        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Permintaan","No.Rawat","Pasien","Permintaan","Jam",
            "Sampel","Jam","Hasil","Jam","Kode Dokter","Dokter Perujuk","Poli Registrasi"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbRadiologiRalan.setModel(tabMode);

        tbRadiologiRalan.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbRadiologiRalan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 12; i++) {
            TableColumn column = tbRadiologiRalan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(90);
            }else if(i==1){
                column.setPreferredWidth(105);
            }else if(i==2){
                column.setPreferredWidth(400);
            }else if(i==3){
                column.setPreferredWidth(65);
            }else if(i==4){
                column.setPreferredWidth(50);
            }else if(i==5){
                column.setPreferredWidth(65);
            }else if(i==6){
                column.setPreferredWidth(50);
            }else if(i==7){
                column.setPreferredWidth(65);
            }else if(i==8){
                column.setPreferredWidth(50);
            }else if(i==9){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==10){
                column.setPreferredWidth(150);
            }else if(i==11){
                column.setPreferredWidth(150);
            }
        }
        tbRadiologiRalan.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabMode2=new DefaultTableModel(null,new Object[]{
                "No.Permintaan","No.Rawat","Pasien","Pemeriksaan","Permintaan",
                "Jam","Sampel","Jam","Hasil","Jam","Kode Dokter","Dokter Perujuk","Poli Registrasi"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbRadiologiRalan2.setModel(tabMode2);

        tbRadiologiRalan2.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbRadiologiRalan2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 13; i++) {
            TableColumn column = tbRadiologiRalan2.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(90);
            }else if(i==1){
                column.setPreferredWidth(105);
            }else if(i==2){
                column.setPreferredWidth(250);
            }else if(i==3){
                column.setPreferredWidth(150);
            }else if(i==4){
                column.setPreferredWidth(65);
            }else if(i==5){
                column.setPreferredWidth(50);
            }else if(i==6){
                column.setPreferredWidth(65);
            }else if(i==7){
                column.setPreferredWidth(50);
            }else if(i==8){
                column.setPreferredWidth(65);
            }else if(i==9){
                column.setPreferredWidth(50);
            }else if(i==10){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==11){
                column.setPreferredWidth(150);
            }else if(i==12){
                column.setPreferredWidth(150);
            }
        }
        tbRadiologiRalan2.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabMode3=new DefaultTableModel(null,new Object[]{
            "No.Permintaan","No.Rawat","Pasien","Permintaan","Jam",
            "Sampel","Jam","Hasil","Jam","Kode Dokter","Dokter Perujuk","Kamar Terakhir"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbRadiologiRanap.setModel(tabMode3);

        tbRadiologiRanap.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbRadiologiRanap.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 12; i++) {
            TableColumn column = tbRadiologiRanap.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(90);
            }else if(i==1){
                column.setPreferredWidth(105);
            }else if(i==2){
                column.setPreferredWidth(400);
            }else if(i==3){
                column.setPreferredWidth(65);
            }else if(i==4){
                column.setPreferredWidth(50);
            }else if(i==5){
                column.setPreferredWidth(65);
            }else if(i==6){
                column.setPreferredWidth(50);
            }else if(i==7){
                column.setPreferredWidth(65);
            }else if(i==8){
                column.setPreferredWidth(50);
            }else if(i==9){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==10){
                column.setPreferredWidth(150);
            }else if(i==11){
                column.setPreferredWidth(150);
            }
        }
        tbRadiologiRanap.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabMode4=new DefaultTableModel(null,new Object[]{
                "No.Permintaan","No.Rawat","Pasien","Pemeriksaan","Permintaan",
                "Jam","Sampel","Jam","Hasil","Jam","Kode Dokter","Dokter Perujuk","Hasil Akhir"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbRadiologiRanap2.setModel(tabMode4);

        tbRadiologiRanap2.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbRadiologiRanap2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 13; i++) {
            TableColumn column = tbRadiologiRanap2.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(90);
            }else if(i==1){
                column.setPreferredWidth(105);
            }else if(i==2){
                column.setPreferredWidth(250);
            }else if(i==3){
                column.setPreferredWidth(150);
            }else if(i==4){
                column.setPreferredWidth(65);
            }else if(i==5){
                column.setPreferredWidth(50);
            }else if(i==6){
                column.setPreferredWidth(65);
            }else if(i==7){
                column.setPreferredWidth(50);
            }else if(i==8){
                column.setPreferredWidth(65);
            }else if(i==9){
                column.setPreferredWidth(50);
            }else if(i==10){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==11){
                column.setPreferredWidth(150);
            }else if(i==12){
                column.setPreferredWidth(150);
            }
        }
        tbRadiologiRanap2.setDefaultRenderer(Object.class, new WarnaTable());
        
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
        
        dokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {;}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(dokter.getTable().getSelectedRow()!= -1){ 
                    if(TabPilihRawat.getSelectedIndex()==0){
                        CrDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                        CrDokter.requestFocus();
                    }else if(TabPilihRawat.getSelectedIndex()==1){
                        CrDokter2.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                        CrDokter2.requestFocus();
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
        
        poli.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(poli.getTable().getSelectedRow()!= -1){   
                    CrPoli.setText(poli.getTable().getValueAt(poli.getTable().getSelectedRow(),1).toString());
                    CrPoli.requestFocus();
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
        
        ruang.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(ruang.getTable().getSelectedRow()!= -1){   
                    Kamar.setText(ruang.getTable().getValueAt(ruang.getTable().getSelectedRow(),1).toString());  
                    Kamar.requestFocus();
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
            prop.loadFromXML(new FileInputStream("setting/database.xml"));
            alarm=prop.getProperty("ALARMRADIOLOGI");
            formalarm=prop.getProperty("FORMALARMRADIOLOGI");
        } catch (Exception ex) {
            alarm="no";
            formalarm="ralan + ranap";
        }
        
        if(alarm.equals("yes")){
            jam();
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

        Kd2 = new widget.TextBox();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnCetakHasilRadiologi = new javax.swing.JMenuItem();
        MnBarcodePermintaan = new javax.swing.JMenuItem();
        MnBarcodePermintaan1 = new javax.swing.JMenuItem();
        WindowAmbilSampel = new javax.swing.JDialog();
        internalFrame5 = new widget.InternalFrame();
        BtnCloseIn4 = new widget.Button();
        BtnSimpan4 = new widget.Button();
        jLabel26 = new widget.Label();
        TanggalPulang = new widget.Tanggal();
        internalFrame1 = new widget.InternalFrame();
        jPanel2 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        label11 = new widget.Label();
        Tgl1 = new widget.Tanggal();
        label18 = new widget.Label();
        Tgl2 = new widget.Tanggal();
        label10 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        panelisi1 = new widget.panelisi();
        BtnHapus = new widget.Button();
        BtnHasil = new widget.Button();
        BtnSampel = new widget.Button();
        BtnAll = new widget.Button();
        BtnPrint = new widget.Button();
        jLabel10 = new widget.Label();
        LCount = new widget.Label();
        BtnKeluar = new widget.Button();
        TabPilihRawat = new javax.swing.JTabbedPane();
        internalFrame2 = new widget.InternalFrame();
        panelGlass9 = new widget.panelisi();
        jLabel14 = new widget.Label();
        CrDokter = new widget.TextBox();
        BtnSeek3 = new widget.Button();
        jLabel16 = new widget.Label();
        CrPoli = new widget.TextBox();
        BtnSeek4 = new widget.Button();
        TabRawatJalan = new javax.swing.JTabbedPane();
        scrollPane1 = new widget.ScrollPane();
        tbRadiologiRalan = new widget.Table();
        scrollPane2 = new widget.ScrollPane();
        tbRadiologiRalan2 = new widget.Table();
        internalFrame3 = new widget.InternalFrame();
        panelGlass10 = new widget.panelisi();
        jLabel15 = new widget.Label();
        CrDokter2 = new widget.TextBox();
        BtnSeek5 = new widget.Button();
        jLabel17 = new widget.Label();
        Kamar = new widget.TextBox();
        BtnSeek6 = new widget.Button();
        TabRawatInap = new javax.swing.JTabbedPane();
        scrollPane3 = new widget.ScrollPane();
        tbRadiologiRanap = new widget.Table();
        scrollPane4 = new widget.ScrollPane();
        tbRadiologiRanap2 = new widget.Table();

        Kd2.setName("Kd2"); // NOI18N
        Kd2.setPreferredSize(new java.awt.Dimension(207, 23));

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnCetakHasilRadiologi.setBackground(new java.awt.Color(255, 255, 254));
        MnCetakHasilRadiologi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakHasilRadiologi.setForeground(new java.awt.Color(70, 70, 70));
        MnCetakHasilRadiologi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakHasilRadiologi.setText("Cetak Permintaan Radiologi");
        MnCetakHasilRadiologi.setName("MnCetakHasilRadiologi"); // NOI18N
        MnCetakHasilRadiologi.setPreferredSize(new java.awt.Dimension(200, 28));
        MnCetakHasilRadiologi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakHasilRadiologiActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnCetakHasilRadiologi);

        MnBarcodePermintaan.setBackground(new java.awt.Color(255, 255, 254));
        MnBarcodePermintaan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnBarcodePermintaan.setForeground(new java.awt.Color(70, 70, 70));
        MnBarcodePermintaan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnBarcodePermintaan.setText("Barcode No.Permintaan");
        MnBarcodePermintaan.setName("MnBarcodePermintaan"); // NOI18N
        MnBarcodePermintaan.setPreferredSize(new java.awt.Dimension(200, 28));
        MnBarcodePermintaan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnBarcodePermintaanActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnBarcodePermintaan);

        MnBarcodePermintaan1.setBackground(new java.awt.Color(255, 255, 254));
        MnBarcodePermintaan1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnBarcodePermintaan1.setForeground(new java.awt.Color(70, 70, 70));
        MnBarcodePermintaan1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnBarcodePermintaan1.setText("Barcode No.Permintaan 2");
        MnBarcodePermintaan1.setName("MnBarcodePermintaan1"); // NOI18N
        MnBarcodePermintaan1.setPreferredSize(new java.awt.Dimension(200, 28));
        MnBarcodePermintaan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnBarcodePermintaan1ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnBarcodePermintaan1);

        WindowAmbilSampel.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowAmbilSampel.setName("WindowAmbilSampel"); // NOI18N
        WindowAmbilSampel.setUndecorated(true);
        WindowAmbilSampel.setResizable(false);

        internalFrame5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Update Waktu Pengambilan Sampel ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(70, 70, 70))); // NOI18N
        internalFrame5.setName("internalFrame5"); // NOI18N
        internalFrame5.setLayout(null);

        BtnCloseIn4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/cross.png"))); // NOI18N
        BtnCloseIn4.setMnemonic('U');
        BtnCloseIn4.setText("Tutup");
        BtnCloseIn4.setToolTipText("Alt+U");
        BtnCloseIn4.setName("BtnCloseIn4"); // NOI18N
        BtnCloseIn4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCloseIn4ActionPerformed(evt);
            }
        });
        internalFrame5.add(BtnCloseIn4);
        BtnCloseIn4.setBounds(410, 30, 100, 30);

        BtnSimpan4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan4.setMnemonic('S');
        BtnSimpan4.setText("Simpan");
        BtnSimpan4.setToolTipText("Alt+S");
        BtnSimpan4.setName("BtnSimpan4"); // NOI18N
        BtnSimpan4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpan4ActionPerformed(evt);
            }
        });
        internalFrame5.add(BtnSimpan4);
        BtnSimpan4.setBounds(305, 30, 100, 30);

        jLabel26.setText("Tanggal & Jam :");
        jLabel26.setName("jLabel26"); // NOI18N
        internalFrame5.add(jLabel26);
        jLabel26.setBounds(6, 32, 100, 23);

        TanggalPulang.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "27-02-2019 21:45:50" }));
        TanggalPulang.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        TanggalPulang.setName("TanggalPulang"); // NOI18N
        TanggalPulang.setOpaque(false);
        TanggalPulang.setPreferredSize(new java.awt.Dimension(95, 23));
        internalFrame5.add(TanggalPulang);
        TanggalPulang.setBounds(110, 32, 150, 23);

        WindowAmbilSampel.getContentPane().add(internalFrame5, java.awt.BorderLayout.CENTER);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
            public void windowDeactivated(java.awt.event.WindowEvent evt) {
                formWindowDeactivated(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Permintaan Radiologi ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(70, 70, 70))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        jPanel2.setName("jPanel2"); // NOI18N
        jPanel2.setOpaque(false);
        jPanel2.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 3, 9));

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(60, 23));
        panelGlass8.add(label11);

        Tgl1.setDisplayFormat("dd-MM-yyyy");
        Tgl1.setName("Tgl1"); // NOI18N
        Tgl1.setPreferredSize(new java.awt.Dimension(90, 23));
        Tgl1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Tgl1KeyPressed(evt);
            }
        });
        panelGlass8.add(Tgl1);

        label18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label18.setText("s.d.");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(25, 23));
        panelGlass8.add(label18);

        Tgl2.setDisplayFormat("dd-MM-yyyy");
        Tgl2.setName("Tgl2"); // NOI18N
        Tgl2.setPreferredSize(new java.awt.Dimension(90, 23));
        Tgl2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Tgl2KeyPressed(evt);
            }
        });
        panelGlass8.add(Tgl2);

        label10.setText("Key Word :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(112, 23));
        panelGlass8.add(label10);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(318, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass8.add(TCari);

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
        panelGlass8.add(BtnCari);

        jPanel2.add(panelGlass8, java.awt.BorderLayout.CENTER);

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 56));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

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
        panelisi1.add(BtnHapus);

        BtnHasil.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/file-edit-16x16.png"))); // NOI18N
        BtnHasil.setMnemonic('P');
        BtnHasil.setText("Sampel");
        BtnHasil.setToolTipText("Alt+P");
        BtnHasil.setName("BtnHasil"); // NOI18N
        BtnHasil.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnHasil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnHasilActionPerformed(evt);
            }
        });
        BtnHasil.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnHasilKeyPressed(evt);
            }
        });
        panelisi1.add(BtnHasil);

        BtnSampel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Agenda-1-16x16.png"))); // NOI18N
        BtnSampel.setMnemonic('I');
        BtnSampel.setText("Hasil");
        BtnSampel.setToolTipText("Alt+I");
        BtnSampel.setName("BtnSampel"); // NOI18N
        BtnSampel.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnSampel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSampelActionPerformed(evt);
            }
        });
        BtnSampel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSampelKeyPressed(evt);
            }
        });
        panelisi1.add(BtnSampel);

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

        jLabel10.setText("Record :");
        jLabel10.setName("jLabel10"); // NOI18N
        jLabel10.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi1.add(jLabel10);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(53, 23));
        panelisi1.add(LCount);

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

        jPanel2.add(panelisi1, java.awt.BorderLayout.PAGE_END);

        internalFrame1.add(jPanel2, java.awt.BorderLayout.PAGE_END);

        TabPilihRawat.setBackground(new java.awt.Color(255, 255, 254));
        TabPilihRawat.setForeground(new java.awt.Color(70, 70, 70));
        TabPilihRawat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabPilihRawat.setName("TabPilihRawat"); // NOI18N
        TabPilihRawat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabPilihRawatMouseClicked(evt);
            }
        });

        internalFrame2.setBorder(null);
        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setLayout(new java.awt.BorderLayout());

        panelGlass9.setBorder(null);
        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 41));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel14.setText("Dokter :");
        jLabel14.setName("jLabel14"); // NOI18N
        jLabel14.setPreferredSize(new java.awt.Dimension(55, 23));
        panelGlass9.add(jLabel14);

        CrDokter.setEditable(false);
        CrDokter.setName("CrDokter"); // NOI18N
        CrDokter.setPreferredSize(new java.awt.Dimension(257, 23));
        panelGlass9.add(CrDokter);

        BtnSeek3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek3.setMnemonic('6');
        BtnSeek3.setToolTipText("ALt+6");
        BtnSeek3.setName("BtnSeek3"); // NOI18N
        BtnSeek3.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSeek3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek3ActionPerformed(evt);
            }
        });
        panelGlass9.add(BtnSeek3);

        jLabel16.setText("Unit :");
        jLabel16.setName("jLabel16"); // NOI18N
        jLabel16.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(jLabel16);

        CrPoli.setEditable(false);
        CrPoli.setName("CrPoli"); // NOI18N
        CrPoli.setPreferredSize(new java.awt.Dimension(257, 23));
        panelGlass9.add(CrPoli);

        BtnSeek4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek4.setMnemonic('5');
        BtnSeek4.setToolTipText("ALt+5");
        BtnSeek4.setName("BtnSeek4"); // NOI18N
        BtnSeek4.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSeek4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek4ActionPerformed(evt);
            }
        });
        panelGlass9.add(BtnSeek4);

        internalFrame2.add(panelGlass9, java.awt.BorderLayout.PAGE_END);

        TabRawatJalan.setBackground(new java.awt.Color(255, 255, 254));
        TabRawatJalan.setForeground(new java.awt.Color(70, 70, 70));
        TabRawatJalan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabRawatJalan.setName("TabRawatJalan"); // NOI18N
        TabRawatJalan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatJalanMouseClicked(evt);
            }
        });

        scrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        scrollPane1.setName("scrollPane1"); // NOI18N
        scrollPane1.setOpaque(true);

        tbRadiologiRalan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbRadiologiRalan.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbRadiologiRalan.setComponentPopupMenu(jPopupMenu1);
        tbRadiologiRalan.setName("tbRadiologiRalan"); // NOI18N
        tbRadiologiRalan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbRadiologiRalanMouseClicked(evt);
            }
        });
        tbRadiologiRalan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbRadiologiRalanKeyPressed(evt);
            }
        });
        scrollPane1.setViewportView(tbRadiologiRalan);

        TabRawatJalan.addTab("Data Permintaan", scrollPane1);

        scrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        scrollPane2.setName("scrollPane2"); // NOI18N
        scrollPane2.setOpaque(true);

        tbRadiologiRalan2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbRadiologiRalan2.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbRadiologiRalan2.setName("tbRadiologiRalan2"); // NOI18N
        tbRadiologiRalan2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbRadiologiRalan2MouseClicked(evt);
            }
        });
        tbRadiologiRalan2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbRadiologiRalan2KeyPressed(evt);
            }
        });
        scrollPane2.setViewportView(tbRadiologiRalan2);

        TabRawatJalan.addTab("Item Permintaan", scrollPane2);

        internalFrame2.add(TabRawatJalan, java.awt.BorderLayout.CENTER);

        TabPilihRawat.addTab("Rawat Jalan", internalFrame2);

        internalFrame3.setBorder(null);
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setLayout(new java.awt.BorderLayout());

        panelGlass10.setBorder(null);
        panelGlass10.setName("panelGlass10"); // NOI18N
        panelGlass10.setPreferredSize(new java.awt.Dimension(44, 41));
        panelGlass10.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel15.setText("Dokter :");
        jLabel15.setName("jLabel15"); // NOI18N
        jLabel15.setPreferredSize(new java.awt.Dimension(55, 23));
        panelGlass10.add(jLabel15);

        CrDokter2.setEditable(false);
        CrDokter2.setName("CrDokter2"); // NOI18N
        CrDokter2.setPreferredSize(new java.awt.Dimension(250, 23));
        panelGlass10.add(CrDokter2);

        BtnSeek5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek5.setMnemonic('6');
        BtnSeek5.setToolTipText("ALt+6");
        BtnSeek5.setName("BtnSeek5"); // NOI18N
        BtnSeek5.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSeek5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek5ActionPerformed(evt);
            }
        });
        panelGlass10.add(BtnSeek5);

        jLabel17.setText("Ruang/Kamar :");
        jLabel17.setName("jLabel17"); // NOI18N
        jLabel17.setPreferredSize(new java.awt.Dimension(104, 23));
        panelGlass10.add(jLabel17);

        Kamar.setEditable(false);
        Kamar.setName("Kamar"); // NOI18N
        Kamar.setPreferredSize(new java.awt.Dimension(250, 23));
        panelGlass10.add(Kamar);

        BtnSeek6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek6.setMnemonic('5');
        BtnSeek6.setToolTipText("ALt+5");
        BtnSeek6.setName("BtnSeek6"); // NOI18N
        BtnSeek6.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSeek6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek6ActionPerformed(evt);
            }
        });
        panelGlass10.add(BtnSeek6);

        internalFrame3.add(panelGlass10, java.awt.BorderLayout.PAGE_END);

        TabRawatInap.setBackground(new java.awt.Color(255, 255, 254));
        TabRawatInap.setForeground(new java.awt.Color(70, 70, 70));
        TabRawatInap.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabRawatInap.setName("TabRawatInap"); // NOI18N
        TabRawatInap.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatInapMouseClicked(evt);
            }
        });

        scrollPane3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        scrollPane3.setName("scrollPane3"); // NOI18N
        scrollPane3.setOpaque(true);

        tbRadiologiRanap.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbRadiologiRanap.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbRadiologiRanap.setComponentPopupMenu(jPopupMenu1);
        tbRadiologiRanap.setName("tbRadiologiRanap"); // NOI18N
        tbRadiologiRanap.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbRadiologiRanapMouseClicked(evt);
            }
        });
        tbRadiologiRanap.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbRadiologiRanapKeyPressed(evt);
            }
        });
        scrollPane3.setViewportView(tbRadiologiRanap);

        TabRawatInap.addTab("Data Permintaan", scrollPane3);

        scrollPane4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        scrollPane4.setName("scrollPane4"); // NOI18N
        scrollPane4.setOpaque(true);

        tbRadiologiRanap2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbRadiologiRanap2.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbRadiologiRanap2.setName("tbRadiologiRanap2"); // NOI18N
        tbRadiologiRanap2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbRadiologiRanap2MouseClicked(evt);
            }
        });
        tbRadiologiRanap2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbRadiologiRanap2KeyPressed(evt);
            }
        });
        scrollPane4.setViewportView(tbRadiologiRanap2);

        TabRawatInap.addTab("Item Permintaan", scrollPane4);

        internalFrame3.add(TabRawatInap, java.awt.BorderLayout.CENTER);

        TabPilihRawat.addTab("Rawat Inap", internalFrame3);

        internalFrame1.add(TabPilihRawat, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents
/*
private void KdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdKeyPressed
    Valid.pindah(evt,BtnCari,Nm);
}//GEN-LAST:event_TKdKeyPressed
*/

    private void Tgl1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tgl1KeyPressed
        Valid.pindah(evt,BtnKeluar,Tgl2);
    }//GEN-LAST:event_Tgl1KeyPressed

    private void Tgl2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tgl2KeyPressed
        Valid.pindah(evt, Tgl1,TCari);
    }//GEN-LAST:event_Tgl2KeyPressed

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
        pilihTab();
    }//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari, BtnAll);
        }
    }//GEN-LAST:event_BtnCariKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        if(TabPilihRawat.getSelectedIndex()==0){
            CrDokter.setText("");
            CrPoli.setText("");
            pilihRalan();
        }else if(TabPilihRawat.getSelectedIndex()==1){
            CrDokter2.setText("");
            Kamar.setText("");
            pilihRanap();
        }
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnKeluar);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));        
        if(TabPilihRawat.getSelectedIndex()==0){
            if(TabRawatJalan.getSelectedIndex()==0){
                if(tabMode.getRowCount()==0){
                    JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                    TCari.requestFocus();
                }else if(tabMode.getRowCount()!=0){
                    
                    Sequel.queryu("delete from temporary_permintaan_radiologi");
                    int row=tabMode.getRowCount();
                    for(i=0;i<row;i++){  
                        tglsampel="";
                        try {
                            tglsampel=tabMode.getValueAt(i,5).toString();
                        } catch (Exception e) {
                            tglsampel="";
                        }
                        tglhasil="";
                        try {
                            tglhasil=tabMode.getValueAt(i,7).toString();
                        } catch (Exception e) {
                            tglhasil="";
                        }
                        Sequel.menyimpan("temporary_permintaan_radiologi","'0','"+
                            tabMode.getValueAt(i,0).toString()+"','"+
                            tabMode.getValueAt(i,1).toString()+"','"+
                            tabMode.getValueAt(i,2).toString()+"','"+
                            tabMode.getValueAt(i,3).toString()+"','"+
                            tabMode.getValueAt(i,4).toString()+"','"+
                            tglsampel+"','"+
                            tabMode.getValueAt(i,6).toString()+"','"+
                            tglhasil+"','"+
                            tabMode.getValueAt(i,8).toString()+"','"+
                            tabMode.getValueAt(i,9).toString()+"','"+
                            tabMode.getValueAt(i,10).toString()+"','"+
                            tabMode.getValueAt(i,11).toString()+"','','','','','','','','','','','','','','','','','','','','','','','','',''","Periksa Lab"); 
                    }
                    
                    Map<String, Object> param = new HashMap<>();
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());
                    param.put("logo",Sequel.cariGambar("select logo from setting")); 
                    Valid.MyReport("rptLapPermintaanRadiologi.jasper","report","::[ Data Permintaan Radiologi ]::",param);
                }
            }else if(TabRawatJalan.getSelectedIndex()==1){
                if(tabMode2.getRowCount()==0){
                    JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                    TCari.requestFocus();
                }else if(tabMode2.getRowCount()!=0){
                    
                    Sequel.queryu("delete from temporary_permintaan_radiologi");
                    int row=tabMode2.getRowCount();
                    for(i=0;i<row;i++){  
                        tglsampel="";
                        try {
                            tglsampel=tabMode2.getValueAt(i,6).toString();
                        } catch (Exception e) {
                            tglsampel="";
                        }
                        tglhasil="";
                        try {
                            tglhasil=tabMode2.getValueAt(i,8).toString();
                        } catch (Exception e) {
                            tglhasil="";
                        }
                        Sequel.menyimpan("temporary_permintaan_radiologi","'0','"+
                                        tabMode2.getValueAt(i,0).toString()+"','"+
                                        tabMode2.getValueAt(i,1).toString()+"','"+
                                        tabMode2.getValueAt(i,2).toString()+"','"+
                                        tabMode2.getValueAt(i,3).toString()+"','"+
                                        tabMode2.getValueAt(i,4).toString()+"','"+
                                        tabMode2.getValueAt(i,5).toString()+"','"+
                                        tglsampel+"','"+
                                        tabMode2.getValueAt(i,7).toString()+"','"+
                                        tglhasil+"','"+
                                        tabMode2.getValueAt(i,9).toString()+"','"+
                                        tabMode2.getValueAt(i,10).toString()+"','"+
                                        tabMode2.getValueAt(i,11).toString()+"','"+
                                        tabMode2.getValueAt(i,12).toString()+"','','','','','','','','','','','','','','','','','','','','','','','',''","Periksa Lab"); 
                    }
                    
                    Map<String, Object> param = new HashMap<>();
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());
                    param.put("logo",Sequel.cariGambar("select logo from setting")); 
                    Valid.MyReport("rptLapPermintaanRadiologi2.jasper","report","::[ Data Detail Permintaan Radiologi ]::",param);
                }
            }            
        }else if(TabPilihRawat.getSelectedIndex()==1){
            if(TabRawatInap.getSelectedIndex()==0){
                if(tabMode3.getRowCount()==0){
                    JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                    TCari.requestFocus();
                }else if(tabMode3.getRowCount()!=0){
                    
                    Sequel.queryu("delete from temporary_permintaan_radiologi");
                    int row=tabMode3.getRowCount();
                    for(i=0;i<row;i++){  
                        tglsampel="";
                        try {
                            tglsampel=tabMode.getValueAt(i,5).toString();
                        } catch (Exception e) {
                            tglsampel="";
                        }
                        tglhasil="";
                        try {
                            tglhasil=tabMode.getValueAt(i,7).toString();
                        } catch (Exception e) {
                            tglhasil="";
                        }
                        Sequel.menyimpan("temporary_permintaan_radiologi","'0','"+
                            tabMode3.getValueAt(i,0).toString()+"','"+
                            tabMode3.getValueAt(i,1).toString()+"','"+
                            tabMode3.getValueAt(i,2).toString()+"','"+
                            tabMode3.getValueAt(i,3).toString()+"','"+
                            tabMode3.getValueAt(i,4).toString()+"','"+
                            tglsampel+"','"+
                            tabMode3.getValueAt(i,6).toString()+"','"+
                            tglhasil+"','"+
                            tabMode3.getValueAt(i,8).toString()+"','"+
                            tabMode3.getValueAt(i,9).toString()+"','"+
                            tabMode3.getValueAt(i,10).toString()+"','"+
                            tabMode3.getValueAt(i,11).toString()+"','','','','','','','','','','','','','','','','','','','','','','','','',''","Periksa Lab"); 
                    }
                    
                    Map<String, Object> param = new HashMap<>();
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());
                    param.put("logo",Sequel.cariGambar("select logo from setting")); 
                    Valid.MyReport("rptLapPermintaanRadiologi3.jasper","report","::[ Data Permintaan Radiologi ]::",param);
                }
            }else if(TabRawatInap.getSelectedIndex()==1){
                if(tabMode4.getRowCount()==0){
                    JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                    TCari.requestFocus();
                }else if(tabMode4.getRowCount()!=0){
                    
                    Sequel.queryu("delete from temporary_permintaan_radiologi");
                    int row=tabMode4.getRowCount();
                    for(i=0;i<row;i++){  
                        tglsampel="";
                        try {
                            tglsampel=tabMode4.getValueAt(i,6).toString();
                        } catch (Exception e) {
                            tglsampel="";
                        }
                        tglhasil="";
                        try {
                            tglhasil=tabMode4.getValueAt(i,8).toString();
                        } catch (Exception e) {
                            tglhasil="";
                        }
                        Sequel.menyimpan("temporary_permintaan_radiologi","'0','"+
                                        tabMode4.getValueAt(i,0).toString()+"','"+
                                        tabMode4.getValueAt(i,1).toString()+"','"+
                                        tabMode4.getValueAt(i,2).toString()+"','"+
                                        tabMode4.getValueAt(i,3).toString()+"','"+
                                        tabMode4.getValueAt(i,4).toString()+"','"+
                                        tabMode4.getValueAt(i,5).toString()+"','"+
                                        tglsampel+"','"+
                                        tabMode4.getValueAt(i,7).toString()+"','"+
                                        tglhasil+"','"+
                                        tabMode4.getValueAt(i,9).toString()+"','"+
                                        tabMode4.getValueAt(i,10).toString()+"','"+
                                        tabMode4.getValueAt(i,11).toString()+"','"+
                                        tabMode4.getValueAt(i,12).toString()+"','','','','','','','','','','','','','','','','','','','','','','','',''","Periksa Lab"); 
                    }
                    
                    Map<String, Object> param = new HashMap<>();
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());
                    param.put("logo",Sequel.cariGambar("select logo from setting")); 
                    Valid.MyReport("rptLapPermintaanRadiologi4.jasper","report","::[ Data Detail Permintaan Radiologi ]::",param);
                }
            }            
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
        WindowAmbilSampel.dispose();
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            WindowAmbilSampel.dispose();
            dispose();
        }else{Valid.pindah(evt,BtnPrint,BtnHapus);}
    }//GEN-LAST:event_BtnKeluarKeyPressed

private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
    if(TabPilihRawat.getSelectedIndex()==0){
        if(TabRawatJalan.getSelectedIndex()==0){
            if(tbRadiologiRalan.getSelectedRow()!= -1){
                if(tbRadiologiRalan.getValueAt(tbRadiologiRalan.getSelectedRow(),0).toString().trim().equals("")){
                    Valid.textKosong(TCari,"No.Permintaan");
                }else{
                    if(Sequel.cariInteger("select count(noorder) from permintaan_pemeriksaan_radiologi where stts_bayar='Sudah' and noorder=?",tbRadiologiRalan.getValueAt(tbRadiologiRalan.getSelectedRow(),0).toString())>0){
                        JOptionPane.showMessageDialog(null,"Maaf, Tidak boleh dihapus karena sudah ada tindakan yang sudah dibayar.\nSilahkan hubungi kasir...!!!!");
                    }else{
                        Sequel.meghapus("permintaan_radiologi","noorder",tbRadiologiRalan.getValueAt(tbRadiologiRalan.getSelectedRow(),0).toString());
                        tampil();
                    }                    
                }
            }else{            
                JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data permintaan...!!!!");
                TCari.requestFocus();
            }
        }else if(TabRawatJalan.getSelectedIndex()==1){
            JOptionPane.showMessageDialog(null,"Hanya bisa dilakukan hapus di Data Permintaan..!!!");
            TabRawatJalan.setSelectedIndex(0);
            TCari.requestFocus();
        } 
    }else if(TabPilihRawat.getSelectedIndex()==1){
        if(TabRawatInap.getSelectedIndex()==0){
            if(tbRadiologiRanap.getSelectedRow()!= -1){
                if(tbRadiologiRanap.getValueAt(tbRadiologiRanap.getSelectedRow(),0).toString().trim().equals("")){
                    Valid.textKosong(TCari,"No.Permintaan");
                }else{
                    if(Sequel.cariInteger("select count(noorder) from permintaan_pemeriksaan_radiologi where stts_bayar='Sudah' and noorder=?",tbRadiologiRanap.getValueAt(tbRadiologiRanap.getSelectedRow(),0).toString())>0){
                        JOptionPane.showMessageDialog(null,"Maaf, Tidak boleh dihapus karena sudah ada tindakan yang sudah dibayar.\nSilahkan hubungi kasir...!!!!");
                    }else{
                        Sequel.meghapus("permintaan_radiologi","noorder",tbRadiologiRanap.getValueAt(tbRadiologiRanap.getSelectedRow(),0).toString());
                        tampil3();
                    }                    
                }
            }else{            
                JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data permintaan...!!!!");
                TCari.requestFocus();
            }
        }else if(TabRawatInap.getSelectedIndex()==1){
            JOptionPane.showMessageDialog(null,"Hanya bisa dilakukan hapus di Data Permintaan..!!!");
            TabRawatInap.setSelectedIndex(0);
            TCari.requestFocus();
        } 
    }                
}//GEN-LAST:event_BtnHapusActionPerformed

private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari,BtnAll);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

private void tbRadiologiRalanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbRadiologiRalanMouseClicked
    if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbRadiologiRalanMouseClicked

private void tbRadiologiRalanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbRadiologiRalanKeyPressed
   if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
}//GEN-LAST:event_tbRadiologiRalanKeyPressed

    private void MnCetakHasilRadiologiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCetakHasilRadiologiActionPerformed
        if(TabPilihRawat.getSelectedIndex()==0){
            if(tbRadiologiRalan.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                if(tbRadiologiRalan.getValueAt(tbRadiologiRalan.getSelectedRow(),0).toString().trim().equals("")){
                    Valid.textKosong(TCari,"No.Permintaan");
                }else{   
                    
                    Sequel.queryu("delete from temporary_permintaan_radiologi");
                    try {
                        ps2=koneksi.prepareStatement(
                                "select permintaan_pemeriksaan_radiologi.kd_jenis_prw,jns_perawatan_radiologi.nm_perawatan "+
                                "from permintaan_pemeriksaan_radiologi inner join jns_perawatan_radiologi on "+
                                "permintaan_pemeriksaan_radiologi.kd_jenis_prw=jns_perawatan_radiologi.kd_jenis_prw "+
                                "where permintaan_pemeriksaan_radiologi.noorder=?");
                        try {
                            ps2.setString(1,tbRadiologiRalan.getValueAt(tbRadiologiRalan.getSelectedRow(),0).toString());
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                Sequel.menyimpan("temporary_permintaan_radiologi","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?",38,new String[]{
                                    "0",rs2.getString("kd_jenis_prw"),rs2.getString("nm_perawatan"),"","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","",""
                                });
                            }
                        } catch (Exception e) {
                            System.out.println("Notif 2 : "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(ps2!=null){
                                ps2.close();
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Notif : "+e);
                    }            
                    
                    Map<String, Object> param = new HashMap<>();
                    param.put("noperiksa",tbRadiologiRalan.getValueAt(tbRadiologiRalan.getSelectedRow(),0).toString());
                    norm=Sequel.cariIsi("select no_rkm_medis from reg_periksa where no_rawat=? ",tbRadiologiRalan.getValueAt(tbRadiologiRalan.getSelectedRow(),1).toString());
                    param.put("norm",norm);
                    param.put("pekerjaan",Sequel.cariIsi("select pekerjaan from pasien where no_rkm_medis=?",norm));
                    param.put("noktp",Sequel.cariIsi("select no_ktp from pasien where no_rkm_medis=?",norm));
                    param.put("namapasien",Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=? ",norm));
                    param.put("jkel",Sequel.cariIsi("select jk from pasien where no_rkm_medis=? ",norm));
                    param.put("umur",Sequel.cariIsi("select umur from pasien where no_rkm_medis=?",norm));
                    param.put("lahir",Sequel.cariIsi("select DATE_FORMAT(tgl_lahir,'%d-%m-%Y') from pasien where no_rkm_medis=? ",norm));
                    param.put("pengirim",tbRadiologiRalan.getValueAt(tbRadiologiRalan.getSelectedRow(),10).toString());
                    param.put("tanggal",Valid.SetTgl3(tbRadiologiRalan.getValueAt(tbRadiologiRalan.getSelectedRow(),3).toString()));
                    param.put("alamat",Sequel.cariIsi("select concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat from pasien inner join kelurahan inner join kecamatan inner join kabupaten on pasien.kd_kel=kelurahan.kd_kel and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab where no_rkm_medis=? ",norm));
                    
                    kamar="Poli";
                    namakamar=Sequel.cariIsi("select nm_poli from poliklinik inner join reg_periksa on poliklinik.kd_poli=reg_periksa.kd_poli "+
                            "where reg_periksa.no_rawat=?",tbRadiologiRalan.getValueAt(tbRadiologiRalan.getSelectedRow(),1).toString());
                    
                    diagnosa=Sequel.cariIsi("select diagnosa_awal from kamar_inap where no_rawat=?",tbRadiologiRalan.getValueAt(tbRadiologiRalan.getSelectedRow(),1).toString());
                    param.put("kamar",kamar);
                    param.put("namakamar",namakamar);
                    param.put("diagnosa",diagnosa);
                    param.put("jam",tbRadiologiRalan.getValueAt(tbRadiologiRalan.getSelectedRow(),4).toString());
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());   
                    param.put("logo",Sequel.cariGambar("select logo from setting")); 

                    Valid.MyReport("rptPermintaanRadiologi.jasper","report","::[ Permintaan Radiologi ]::",param);            
                }
                this.setCursor(Cursor.getDefaultCursor());
            }else{            
                JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data permintaan...!!!!");
                TCari.requestFocus();
            } 
        }else if(TabPilihRawat.getSelectedIndex()==1){
            if(tbRadiologiRanap.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                if(tbRadiologiRanap.getValueAt(tbRadiologiRanap.getSelectedRow(),0).toString().trim().equals("")){
                    Valid.textKosong(TCari,"No.Permintaan");
                }else{   
                    
                    Sequel.queryu("delete from temporary_permintaan_radiologi");
                    try {
                        ps2=koneksi.prepareStatement(
                                "select permintaan_pemeriksaan_radiologi.kd_jenis_prw,jns_perawatan_radiologi.nm_perawatan "+
                                "from permintaan_pemeriksaan_radiologi inner join jns_perawatan_radiologi on "+
                                "permintaan_pemeriksaan_radiologi.kd_jenis_prw=jns_perawatan_radiologi.kd_jenis_prw "+
                                "where permintaan_pemeriksaan_radiologi.noorder=?");
                        try {
                            ps2.setString(1,tbRadiologiRanap.getValueAt(tbRadiologiRanap.getSelectedRow(),0).toString());
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                Sequel.menyimpan("temporary_permintaan_radiologi","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?",38,new String[]{
                                    "0",rs2.getString("kd_jenis_prw"),rs2.getString("nm_perawatan"),"","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","",""
                                });
                            }
                        } catch (Exception e) {
                            System.out.println("Notif 2 : "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(ps2!=null){
                                ps2.close();
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Notif : "+e);
                    }            
                    
                    Map<String, Object> param = new HashMap<>();
                    param.put("noperiksa",tbRadiologiRanap.getValueAt(tbRadiologiRanap.getSelectedRow(),0).toString());
                    norm=Sequel.cariIsi("select no_rkm_medis from reg_periksa where no_rawat=? ",tbRadiologiRanap.getValueAt(tbRadiologiRanap.getSelectedRow(),1).toString());
                    param.put("norm",norm);
                    param.put("pekerjaan",Sequel.cariIsi("select pekerjaan from pasien where no_rkm_medis=?",norm));
                    param.put("noktp",Sequel.cariIsi("select no_ktp from pasien where no_rkm_medis=?",norm));
                    param.put("namapasien",Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=? ",norm));
                    param.put("jkel",Sequel.cariIsi("select jk from pasien where no_rkm_medis=? ",norm));
                    param.put("umur",Sequel.cariIsi("select umur from pasien where no_rkm_medis=?",norm));
                    param.put("lahir",Sequel.cariIsi("select DATE_FORMAT(tgl_lahir,'%d-%m-%Y') from pasien where no_rkm_medis=? ",norm));
                    param.put("pengirim",tbRadiologiRanap.getValueAt(tbRadiologiRanap.getSelectedRow(),10).toString());
                    param.put("tanggal",Valid.SetTgl3(tbRadiologiRanap.getValueAt(tbRadiologiRanap.getSelectedRow(),3).toString()));
                    param.put("alamat",Sequel.cariIsi("select concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat from pasien inner join kelurahan inner join kecamatan inner join kabupaten on pasien.kd_kel=kelurahan.kd_kel and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab where no_rkm_medis=? ",norm));
                    kamar=Sequel.cariIsi("select ifnull(kd_kamar,'') from kamar_inap where no_rawat=? order by tgl_masuk desc limit 1",tbRadiologiRanap.getValueAt(tbRadiologiRanap.getSelectedRow(),1).toString());
                    namakamar=kamar+", "+Sequel.cariIsi("select nm_bangsal from bangsal inner join kamar on bangsal.kd_bangsal=kamar.kd_bangsal "+
                                " where kamar.kd_kamar=? ",kamar);            
                    kamar="Kamar";
                    diagnosa=Sequel.cariIsi("select diagnosa_awal from kamar_inap where no_rawat=?",tbRadiologiRanap.getValueAt(tbRadiologiRanap.getSelectedRow(),1).toString());
                    param.put("kamar",kamar);
                    param.put("namakamar",namakamar);
                    param.put("diagnosa",diagnosa);
                    param.put("jam",tbRadiologiRanap.getValueAt(tbRadiologiRanap.getSelectedRow(),4).toString());
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());   
                    param.put("logo",Sequel.cariGambar("select logo from setting")); 

                    Valid.MyReport("rptPermintaanRadiologi.jasper","report","::[ Permintaan Radiologi ]::",param);            
                }
                this.setCursor(Cursor.getDefaultCursor());
            }else{            
                JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data permintaan...!!!!");
                TCari.requestFocus();
            } 
        }
            
    }//GEN-LAST:event_MnCetakHasilRadiologiActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        pilihTab();
    }//GEN-LAST:event_formWindowOpened

    private void TabRawatJalanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatJalanMouseClicked
        pilihRalan();
    }//GEN-LAST:event_TabRawatJalanMouseClicked

    private void tbRadiologiRalan2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbRadiologiRalan2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbRadiologiRalan2MouseClicked

    private void tbRadiologiRalan2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbRadiologiRalan2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbRadiologiRalan2KeyPressed

    private void BtnSampelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSampelActionPerformed
        if(TabPilihRawat.getSelectedIndex()==0){
            if(TabRawatJalan.getSelectedIndex()==0){
                if(tbRadiologiRalan.getSelectedRow()!= -1){
                    if(tbRadiologiRalan.getValueAt(tbRadiologiRalan.getSelectedRow(),0).toString().trim().equals("")){
                        Valid.textKosong(TCari,"No.Permintaan");
                    }else{                     
                        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                        DlgPeriksaRadiologi dlgro=new DlgPeriksaRadiologi(null,false);
                        dlgro.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                        dlgro.setLocationRelativeTo(internalFrame1);
                        dlgro.emptTeks();
                        dlgro.isCek();
                        dlgro.setOrder(tbRadiologiRalan.getValueAt(tbRadiologiRalan.getSelectedRow(),0).toString(),tbRadiologiRalan.getValueAt(tbRadiologiRalan.getSelectedRow(),1).toString(),"Ralan");
                        dlgro.setDokterPerujuk(tbRadiologiRalan.getValueAt(tbRadiologiRalan.getSelectedRow(),9).toString(),tbRadiologiRalan.getValueAt(tbRadiologiRalan.getSelectedRow(),10).toString());
                        dlgro.setVisible(true);
                        this.setCursor(Cursor.getDefaultCursor());
                    }
                }else{            
                    JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data permintaan...!!!!");
                    TCari.requestFocus();
                } 
            }else if(TabRawatJalan.getSelectedIndex()==1){
                JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih Data Permintaan...!!!!");
                TabRawatJalan.setSelectedIndex(0);
                TCari.requestFocus();
            }
        }else if(TabPilihRawat.getSelectedIndex()==1){
            if(TabRawatInap.getSelectedIndex()==0){
                if(tbRadiologiRanap.getSelectedRow()!= -1){
                    if(tbRadiologiRanap.getValueAt(tbRadiologiRanap.getSelectedRow(),0).toString().trim().equals("")){
                        Valid.textKosong(TCari,"No.Permintaan");
                    }else{                     
                        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                        DlgPeriksaRadiologi dlgro=new DlgPeriksaRadiologi(null,false);
                        dlgro.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                        dlgro.setLocationRelativeTo(internalFrame1);
                        dlgro.emptTeks();
                        dlgro.isCek();
                        dlgro.setOrder(tbRadiologiRanap.getValueAt(tbRadiologiRanap.getSelectedRow(),0).toString(),tbRadiologiRanap.getValueAt(tbRadiologiRanap.getSelectedRow(),1).toString(),"Ranap");
                        dlgro.setDokterPerujuk(tbRadiologiRanap.getValueAt(tbRadiologiRanap.getSelectedRow(),9).toString(),tbRadiologiRanap.getValueAt(tbRadiologiRanap.getSelectedRow(),10).toString());
                        dlgro.setVisible(true);
                        this.setCursor(Cursor.getDefaultCursor());
                    }
                }else{            
                    JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data permintaan...!!!!");
                    TCari.requestFocus();
                } 
            }else if(TabRawatInap.getSelectedIndex()==1){
                JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih Data Permintaan...!!!!");
                TabRawatInap.setSelectedIndex(0);
                TCari.requestFocus();
            }
        }            
    }//GEN-LAST:event_BtnSampelActionPerformed

    private void BtnSampelKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSampelKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnSampelKeyPressed

    private void BtnHasilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHasilActionPerformed
        if(TabPilihRawat.getSelectedIndex()==0){
            if(TabRawatJalan.getSelectedIndex()==0){
                if(tbRadiologiRalan.getSelectedRow()!= -1){
                    if(tbRadiologiRalan.getValueAt(tbRadiologiRalan.getSelectedRow(),0).toString().trim().equals("")){
                        Valid.textKosong(TCari,"No.Permintaan");
                    }else{ 
                        TanggalPulang.setDate(new Date());
                        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));        
                        WindowAmbilSampel.setLocationRelativeTo(internalFrame1);
                        WindowAmbilSampel.setVisible(true);
                        this.setCursor(Cursor.getDefaultCursor());
                    }
                }else{            
                    JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data permintaan...!!!!");
                    TCari.requestFocus();
                }   
            }else if(TabRawatJalan.getSelectedIndex()==1){
                JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih Data Permintaan...!!!!");
                TabRawatJalan.setSelectedIndex(0);
                TCari.requestFocus();
            } 
        }else if(TabPilihRawat.getSelectedIndex()==1){
            if(TabRawatInap.getSelectedIndex()==0){
                if(tbRadiologiRanap.getSelectedRow()!= -1){
                    if(tbRadiologiRanap.getValueAt(tbRadiologiRanap.getSelectedRow(),0).toString().trim().equals("")){
                        Valid.textKosong(TCari,"No.Permintaan");
                    }else{ 
                        TanggalPulang.setDate(new Date());
                        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));        
                        WindowAmbilSampel.setLocationRelativeTo(internalFrame1);
                        WindowAmbilSampel.setVisible(true);
                        this.setCursor(Cursor.getDefaultCursor());
                    }
                }else{            
                    JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data permintaan...!!!!");
                    TCari.requestFocus();
                }   
            }else if(TabRawatInap.getSelectedIndex()==1){
                JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih Data Permintaan...!!!!");
                TabRawatInap.setSelectedIndex(0);
                TCari.requestFocus();
            } 
        }                       
    }//GEN-LAST:event_BtnHasilActionPerformed

    private void BtnHasilKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHasilKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnHasilKeyPressed

    private void MnBarcodePermintaanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnBarcodePermintaanActionPerformed
        if(TabPilihRawat.getSelectedIndex()==0){
            if(tbRadiologiRalan.getSelectedRow()!= -1){
                if(tbRadiologiRalan.getValueAt(tbRadiologiRalan.getSelectedRow(),0).toString().trim().equals("")){
                    Valid.textKosong(TCari,"No.Permintaan");
                }else{ 
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    Map<String, Object> param = new HashMap<>();
                    norm=Sequel.cariIsi("select no_rkm_medis from reg_periksa where no_rawat=? ",tbRadiologiRalan.getValueAt(tbRadiologiRalan.getSelectedRow(),1).toString());
                    param.put("nama",Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=? ",norm));
                    param.put("alamat",Sequel.cariIsi("select date_format(tgl_lahir,'%d/%m/%Y') from pasien where no_rkm_medis=?",norm));
                    param.put("norm",norm);
                    param.put("parameter","%"+TCari.getText().trim()+"%");     
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());   
                    Valid.MyReportqry("rptBarcodePermintaanRadiologi.jasper","report","::[ Barcode No.Permintaan Radiologi ]::",
                            "select noorder from permintaan_radiologi where no_rawat='"+tbRadiologiRalan.getValueAt(tbRadiologiRalan.getSelectedRow(),1).toString()+"'",param); 
                    this.setCursor(Cursor.getDefaultCursor());
                } 
            }else{            
                JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data permintaan...!!!!");
                TCari.requestFocus();
            } 
        }else if(TabPilihRawat.getSelectedIndex()==1){
            if(tbRadiologiRanap.getSelectedRow()!= -1){
                if(tbRadiologiRanap.getValueAt(tbRadiologiRanap.getSelectedRow(),0).toString().trim().equals("")){
                    Valid.textKosong(TCari,"No.Permintaan");
                }else{ 
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    Map<String, Object> param = new HashMap<>();
                    norm=Sequel.cariIsi("select no_rkm_medis from reg_periksa where no_rawat=? ",tbRadiologiRanap.getValueAt(tbRadiologiRanap.getSelectedRow(),1).toString());
                    param.put("nama",Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=? ",norm));
                    param.put("alamat",Sequel.cariIsi("select date_format(tgl_lahir,'%d/%m/%Y') from pasien where no_rkm_medis=?",norm));
                    param.put("norm",norm);
                    param.put("parameter","%"+TCari.getText().trim()+"%");     
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());   
                    Valid.MyReportqry("rptBarcodePermintaanRadiologi.jasper","report","::[ Barcode No.Permintaan Radiologi ]::",
                            "select noorder from permintaan_radiologi where no_rawat='"+tbRadiologiRanap.getValueAt(tbRadiologiRanap.getSelectedRow(),1).toString()+"'",param); 
                    this.setCursor(Cursor.getDefaultCursor());
                } 
            }else{            
                JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data permintaan...!!!!");
                TCari.requestFocus();
            } 
        }
            
    }//GEN-LAST:event_MnBarcodePermintaanActionPerformed

    private void MnBarcodePermintaan1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnBarcodePermintaan1ActionPerformed
        if(TabPilihRawat.getSelectedIndex()==0){
            if(tbRadiologiRalan.getSelectedRow()!= -1){
                if(tbRadiologiRalan.getValueAt(tbRadiologiRalan.getSelectedRow(),0).toString().trim().equals("")){
                    Valid.textKosong(TCari,"No.Permintaan");
                }else{ 
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    Map<String, Object> param = new HashMap<>();
                    norm=Sequel.cariIsi("select no_rkm_medis from reg_periksa where no_rawat=? ",tbRadiologiRalan.getValueAt(tbRadiologiRalan.getSelectedRow(),1).toString());
                    param.put("nama",Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=? ",norm));
                    param.put("alamat",Sequel.cariIsi("select date_format(tgl_lahir,'%d/%m/%Y') from pasien where no_rkm_medis=?",norm));
                    param.put("norm",norm);
                    param.put("parameter","%"+TCari.getText().trim()+"%");     
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());   
                    Valid.MyReportqry("rptBarcodePermintaanRadiologi2.jasper","report","::[ Barcode No.Permintaan Radiologi ]::",
                            "select noorder from permintaan_radiologi where no_rawat='"+tbRadiologiRalan.getValueAt(tbRadiologiRalan.getSelectedRow(),1).toString()+"'",param); 
                    this.setCursor(Cursor.getDefaultCursor());
                } 
            }else{            
                JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data permintaan...!!!!");
                TCari.requestFocus();
            } 
        }else if(TabPilihRawat.getSelectedIndex()==1){
            if(tbRadiologiRanap.getSelectedRow()!= -1){
                if(tbRadiologiRanap.getValueAt(tbRadiologiRanap.getSelectedRow(),0).toString().trim().equals("")){
                    Valid.textKosong(TCari,"No.Permintaan");
                }else{ 
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    Map<String, Object> param = new HashMap<>();
                    norm=Sequel.cariIsi("select no_rkm_medis from reg_periksa where no_rawat=? ",tbRadiologiRanap.getValueAt(tbRadiologiRanap.getSelectedRow(),1).toString());
                    param.put("nama",Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=? ",norm));
                    param.put("alamat",Sequel.cariIsi("select date_format(tgl_lahir,'%d/%m/%Y') from pasien where no_rkm_medis=?",norm));
                    param.put("norm",norm);
                    param.put("parameter","%"+TCari.getText().trim()+"%");     
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());   
                    Valid.MyReportqry("rptBarcodePermintaanRadiologi2.jasper","report","::[ Barcode No.Permintaan Radiologi ]::",
                            "select noorder from permintaan_radiologi where no_rawat='"+tbRadiologiRanap.getValueAt(tbRadiologiRanap.getSelectedRow(),1).toString()+"'",param); 
                    this.setCursor(Cursor.getDefaultCursor());
                } 
            }else{            
                JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data permintaan...!!!!");
                TCari.requestFocus();
            } 
        }   
    }//GEN-LAST:event_MnBarcodePermintaan1ActionPerformed

    private void BtnCloseIn4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCloseIn4ActionPerformed
        WindowAmbilSampel.dispose();
    }//GEN-LAST:event_BtnCloseIn4ActionPerformed

    private void BtnSimpan4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpan4ActionPerformed
        if(TabPilihRawat.getSelectedIndex()==0){
            if(tbRadiologiRalan.getSelectedRow()!= -1){
                if(tbRadiologiRalan.getValueAt(tbRadiologiRalan.getSelectedRow(),0).toString().trim().equals("")){
                    Valid.textKosong(TanggalPulang,"No.Permintaan");
                }else{
                    Sequel.mengedit("permintaan_radiologi","noorder=?","tgl_sampel=?,jam_sampel=?",3,new String[]{
                        Valid.SetTgl(TanggalPulang.getSelectedItem()+""),TanggalPulang.getSelectedItem().toString().substring(11,19),tbRadiologiRalan.getValueAt(tbRadiologiRalan.getSelectedRow(),0).toString()
                    });
                    tampil();
                    WindowAmbilSampel.dispose();
                }
            }else{            
                JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data permintaan...!!!!");
                TCari.requestFocus();
            } 
        }else if(TabPilihRawat.getSelectedIndex()==1){
            if(tbRadiologiRanap.getSelectedRow()!= -1){
                if(tbRadiologiRanap.getValueAt(tbRadiologiRanap.getSelectedRow(),0).toString().trim().equals("")){
                    Valid.textKosong(TanggalPulang,"No.Permintaan");
                }else{
                    Sequel.mengedit("permintaan_radiologi","noorder=?","tgl_sampel=?,jam_sampel=?",3,new String[]{
                        Valid.SetTgl(TanggalPulang.getSelectedItem()+""),TanggalPulang.getSelectedItem().toString().substring(11,19),tbRadiologiRanap.getValueAt(tbRadiologiRanap.getSelectedRow(),0).toString()
                    });
                    tampil3();
                    WindowAmbilSampel.dispose();
                }
            }else{            
                JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data permintaan...!!!!");
                TCari.requestFocus();
            } 
        }             
    }//GEN-LAST:event_BtnSimpan4ActionPerformed

    private void BtnSeek3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek3ActionPerformed
        dokter.isCek();
        dokter.TCari.requestFocus();
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
    }//GEN-LAST:event_BtnSeek3ActionPerformed

    private void BtnSeek4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek4ActionPerformed
        poli.isCek();
        poli.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        poli.setLocationRelativeTo(internalFrame1);
        poli.setVisible(true);
    }//GEN-LAST:event_BtnSeek4ActionPerformed

    private void BtnSeek5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek5ActionPerformed
        dokter.isCek();
        dokter.TCari.requestFocus();
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
    }//GEN-LAST:event_BtnSeek5ActionPerformed

    private void BtnSeek6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek6ActionPerformed
        ruang.isCek();
        ruang.emptTeks();
        ruang.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        ruang.setLocationRelativeTo(internalFrame1);
        ruang.setVisible(true);
    }//GEN-LAST:event_BtnSeek6ActionPerformed

    private void TabPilihRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabPilihRawatMouseClicked
        pilihTab();
    }//GEN-LAST:event_TabPilihRawatMouseClicked

    private void tbRadiologiRanapMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbRadiologiRanapMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbRadiologiRanapMouseClicked

    private void tbRadiologiRanapKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbRadiologiRanapKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbRadiologiRanapKeyPressed

    private void tbRadiologiRanap2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbRadiologiRanap2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbRadiologiRanap2MouseClicked

    private void tbRadiologiRanap2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbRadiologiRanap2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbRadiologiRanap2KeyPressed

    private void TabRawatInapMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatInapMouseClicked
        pilihRanap();
    }//GEN-LAST:event_TabRawatInapMouseClicked

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        aktif=true;
    }//GEN-LAST:event_formWindowActivated

    private void formWindowDeactivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowDeactivated
        aktif=false;
    }//GEN-LAST:event_formWindowDeactivated

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgCariPermintaanRadiologi dialog = new DlgCariPermintaanRadiologi(new javax.swing.JFrame(), true);
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
    private widget.Button BtnCloseIn4;
    private widget.Button BtnHapus;
    private widget.Button BtnHasil;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSampel;
    private widget.Button BtnSeek3;
    private widget.Button BtnSeek4;
    private widget.Button BtnSeek5;
    private widget.Button BtnSeek6;
    private widget.Button BtnSimpan4;
    private widget.TextBox CrDokter;
    private widget.TextBox CrDokter2;
    private widget.TextBox CrPoli;
    private widget.TextBox Kamar;
    private widget.TextBox Kd2;
    private widget.Label LCount;
    private javax.swing.JMenuItem MnBarcodePermintaan;
    private javax.swing.JMenuItem MnBarcodePermintaan1;
    private javax.swing.JMenuItem MnCetakHasilRadiologi;
    private widget.TextBox TCari;
    private javax.swing.JTabbedPane TabPilihRawat;
    private javax.swing.JTabbedPane TabRawatInap;
    private javax.swing.JTabbedPane TabRawatJalan;
    private widget.Tanggal TanggalPulang;
    private widget.Tanggal Tgl1;
    private widget.Tanggal Tgl2;
    private javax.swing.JDialog WindowAmbilSampel;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.InternalFrame internalFrame5;
    private widget.Label jLabel10;
    private widget.Label jLabel14;
    private widget.Label jLabel15;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
    private widget.Label jLabel26;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.Label label10;
    private widget.Label label11;
    private widget.Label label18;
    private widget.panelisi panelGlass10;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.panelisi panelisi1;
    private widget.ScrollPane scrollPane1;
    private widget.ScrollPane scrollPane2;
    private widget.ScrollPane scrollPane3;
    private widget.ScrollPane scrollPane4;
    private widget.Table tbRadiologiRalan;
    private widget.Table tbRadiologiRalan2;
    private widget.Table tbRadiologiRanap;
    private widget.Table tbRadiologiRanap2;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try {
            ps=koneksi.prepareStatement(
                    "select permintaan_radiologi.noorder,permintaan_radiologi.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,permintaan_radiologi.tgl_permintaan,"+
                    "if(permintaan_radiologi.jam_permintaan='00:00:00','',permintaan_radiologi.jam_permintaan) as jam_permintaan,"+
                    "if(permintaan_radiologi.tgl_sampel='0000-00-00','',permintaan_radiologi.tgl_sampel) as tgl_sampel,if(permintaan_radiologi.jam_sampel='00:00:00','',permintaan_radiologi.jam_sampel) as jam_sampel,"+
                    "permintaan_radiologi.tgl_hasil,if(permintaan_radiologi.jam_hasil='00:00:00','',permintaan_radiologi.jam_hasil) as jam_hasil,"+
                    "permintaan_radiologi.dokter_perujuk,dokter.nm_dokter,poliklinik.nm_poli from permintaan_radiologi inner join reg_periksa "+
                    "inner join pasien inner join dokter inner join poliklinik on permintaan_radiologi.no_rawat=reg_periksa.no_rawat and "+
                    "reg_periksa.no_rkm_medis=pasien.no_rkm_medis and permintaan_radiologi.dokter_perujuk=dokter.kd_dokter and reg_periksa.kd_poli=poliklinik.kd_poli where "+
                    "permintaan_radiologi.status='ralan' and permintaan_radiologi.tgl_permintaan between ? and ? and dokter.nm_dokter like ? and poliklinik.nm_poli like ? and permintaan_radiologi.noorder like ? or "+
                    "permintaan_radiologi.status='ralan' and permintaan_radiologi.tgl_permintaan between ? and ? and dokter.nm_dokter like ? and poliklinik.nm_poli like ? and permintaan_radiologi.no_rawat like ? or "+
                    "permintaan_radiologi.status='ralan' and permintaan_radiologi.tgl_permintaan between ? and ? and dokter.nm_dokter like ? and poliklinik.nm_poli like ? and reg_periksa.no_rkm_medis like ? or "+
                    "permintaan_radiologi.status='ralan' and permintaan_radiologi.tgl_permintaan between ? and ? and dokter.nm_dokter like ? and poliklinik.nm_poli like ? and pasien.nm_pasien like ? or "+
                    "permintaan_radiologi.status='ralan' and permintaan_radiologi.tgl_permintaan between ? and ? and dokter.nm_dokter like ? and poliklinik.nm_poli like ? and dokter.nm_dokter like ? order by "+
                    "permintaan_radiologi.tgl_permintaan,permintaan_radiologi.jam_permintaan desc");
            try {
                ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(3,"%"+CrDokter.getText().trim()+"%");
                ps.setString(4,"%"+CrPoli.getText().trim()+"%");
                ps.setString(5,"%"+TCari.getText()+"%");
                ps.setString(6,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(7,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(8,"%"+CrDokter.getText().trim()+"%");
                ps.setString(9,"%"+CrPoli.getText().trim()+"%");
                ps.setString(10,"%"+TCari.getText()+"%");
                ps.setString(11,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(12,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(13,"%"+CrDokter.getText().trim()+"%");
                ps.setString(14,"%"+CrPoli.getText().trim()+"%");
                ps.setString(15,"%"+TCari.getText()+"%");
                ps.setString(16,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(17,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(18,"%"+CrDokter.getText().trim()+"%");
                ps.setString(19,"%"+CrPoli.getText().trim()+"%");
                ps.setString(20,"%"+TCari.getText()+"%");
                ps.setString(21,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(22,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(23,"%"+CrDokter.getText().trim()+"%");
                ps.setString(24,"%"+CrPoli.getText().trim()+"%");
                ps.setString(25,"%"+TCari.getText()+"%");
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new String[]{
                        rs.getString("noorder"),rs.getString("no_rawat"),rs.getString("no_rkm_medis")+" "+
                        rs.getString("nm_pasien"),rs.getString("tgl_permintaan"),rs.getString("jam_permintaan"),
                        rs.getString("tgl_sampel"),rs.getString("jam_sampel"),rs.getString("tgl_hasil"),
                        rs.getString("jam_hasil"),rs.getString("dokter_perujuk"),rs.getString("nm_dokter"),rs.getString("nm_poli")
                    });
                    ps2=koneksi.prepareStatement(
                            "select permintaan_pemeriksaan_radiologi.kd_jenis_prw,jns_perawatan_radiologi.nm_perawatan "+
                            "from permintaan_pemeriksaan_radiologi inner join jns_perawatan_radiologi on "+
                            "permintaan_pemeriksaan_radiologi.kd_jenis_prw=jns_perawatan_radiologi.kd_jenis_prw "+
                            "where permintaan_pemeriksaan_radiologi.noorder=?");
                    try {
                        ps2.setString(1,rs.getString("noorder"));
                        rs2=ps2.executeQuery();
                        while(rs2.next()){
                            tabMode.addRow(new Object[]{
                                "","",rs2.getString("nm_perawatan"),"","","","","","","","",""
                            });
                        }
                    } catch (Exception e) {
                        System.out.println("Notif 2 : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                }
                rs.last();
                LCount.setText(""+rs.getRow());
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
        } catch (Exception e) {
            System.out.println("Notif : "+e);
        }        
    }
    
    private void tampil2() {
        Valid.tabelKosong(tabMode2);  
        try {
            ps=koneksi.prepareStatement(
                    "select permintaan_radiologi.noorder,permintaan_radiologi.no_rawat,reg_periksa.no_rkm_medis,"+
                    "pasien.nm_pasien,jns_perawatan_radiologi.nm_perawatan,permintaan_radiologi.tgl_permintaan,"+
                    "if(permintaan_radiologi.jam_permintaan='00:00:00','',permintaan_radiologi.jam_permintaan) as jam_permintaan,permintaan_radiologi.tgl_sampel,"+
                    "if(permintaan_radiologi.jam_sampel='00:00:00','',permintaan_radiologi.jam_sampel) as jam_sampel, permintaan_radiologi.tgl_hasil,"+
                    "if(permintaan_radiologi.jam_hasil='00:00:00','',permintaan_radiologi.jam_hasil) as jam_hasil,"+
                    "permintaan_radiologi.dokter_perujuk,dokter.nm_dokter,poliklinik.nm_poli from permintaan_radiologi "+
                    "inner join reg_periksa inner join pasien inner join permintaan_pemeriksaan_radiologi "+
                    "inner join jns_perawatan_radiologi inner join dokter inner join poliklinik on "+
                    "permintaan_radiologi.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and "+
                    "permintaan_radiologi.noorder=permintaan_pemeriksaan_radiologi.noorder and jns_perawatan_radiologi.kd_jenis_prw=permintaan_pemeriksaan_radiologi.kd_jenis_prw "+
                    "and permintaan_radiologi.dokter_perujuk=dokter.kd_dokter and reg_periksa.kd_poli=poliklinik.kd_poli where "+
                    "permintaan_radiologi.status='ralan' and permintaan_radiologi.tgl_permintaan between ? and ? and dokter.nm_dokter like ? and poliklinik.nm_poli like ? and permintaan_radiologi.noorder like ? or "+
                    "permintaan_radiologi.status='ralan' and permintaan_radiologi.tgl_permintaan between ? and ? and dokter.nm_dokter like ? and poliklinik.nm_poli like ? and permintaan_radiologi.no_rawat like ? or "+
                    "permintaan_radiologi.status='ralan' and permintaan_radiologi.tgl_permintaan between ? and ? and dokter.nm_dokter like ? and poliklinik.nm_poli like ? and reg_periksa.no_rkm_medis like ? or "+
                    "permintaan_radiologi.status='ralan' and permintaan_radiologi.tgl_permintaan between ? and ? and dokter.nm_dokter like ? and poliklinik.nm_poli like ? and pasien.nm_pasien like ? or "+
                    "permintaan_radiologi.status='ralan' and permintaan_radiologi.tgl_permintaan between ? and ? and dokter.nm_dokter like ? and poliklinik.nm_poli like ? and jns_perawatan_radiologi.nm_perawatan like ? or "+
                    "permintaan_radiologi.status='ralan' and permintaan_radiologi.tgl_permintaan between ? and ? and dokter.nm_dokter like ? and poliklinik.nm_poli like ? and dokter.nm_dokter like ? order by "+
                    "permintaan_radiologi.tgl_permintaan,permintaan_radiologi.jam_permintaan desc");
            try {
                ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(3,"%"+CrDokter.getText().trim()+"%");
                ps.setString(4,"%"+CrPoli.getText().trim()+"%");
                ps.setString(5,"%"+TCari.getText()+"%");
                ps.setString(6,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(7,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(8,"%"+CrDokter.getText().trim()+"%");
                ps.setString(9,"%"+CrPoli.getText().trim()+"%");
                ps.setString(10,"%"+TCari.getText()+"%");
                ps.setString(11,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(12,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(13,"%"+CrDokter.getText().trim()+"%");
                ps.setString(14,"%"+CrPoli.getText().trim()+"%");
                ps.setString(15,"%"+TCari.getText()+"%");
                ps.setString(16,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(17,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(18,"%"+CrDokter.getText().trim()+"%");
                ps.setString(19,"%"+CrPoli.getText().trim()+"%");
                ps.setString(20,"%"+TCari.getText()+"%");
                ps.setString(21,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(22,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(23,"%"+CrDokter.getText().trim()+"%");
                ps.setString(24,"%"+CrPoli.getText().trim()+"%");
                ps.setString(25,"%"+TCari.getText()+"%");
                ps.setString(26,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(27,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(28,"%"+CrDokter.getText().trim()+"%");
                ps.setString(29,"%"+CrPoli.getText().trim()+"%");
                ps.setString(30,"%"+TCari.getText()+"%");
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode2.addRow(new String[]{
                        rs.getString("noorder"),rs.getString("no_rawat"),rs.getString("no_rkm_medis")+" "+rs.getString("nm_pasien"),
                        rs.getString("nm_perawatan"),rs.getString("tgl_permintaan"),rs.getString("jam_permintaan"),
                        rs.getString("tgl_sampel"),rs.getString("jam_sampel"),rs.getString("tgl_hasil"),
                        rs.getString("jam_hasil"),rs.getString("dokter_perujuk"),rs.getString("nm_dokter"),rs.getString("nm_poli")
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
            LCount.setText(""+tabMode2.getRowCount());
        } catch (Exception e) {
            System.out.println("Notif : "+e);
        }            
    }
    
    
    private void getData() {
        Kd2.setText("");
        if(tbRadiologiRalan.getSelectedRow()!= -1){
            Kd2.setText(tbRadiologiRalan.getValueAt(tbRadiologiRalan.getSelectedRow(),0).toString());
        }
    }
    
    public void isCek(){
        MnCetakHasilRadiologi.setEnabled(akses.getpermintaan_radiologi());
        BtnSampel.setEnabled(akses.getpermintaan_radiologi());
        BtnHasil.setEnabled(akses.getperiksa_radiologi());
        BtnHapus.setEnabled(akses.getpermintaan_radiologi());
        BtnPrint.setEnabled(akses.getpermintaan_radiologi());
    }
    
    public void setPasien(String pasien){
        TCari.setText(pasien);
    }

    public void pilihTab(){
        if(TabPilihRawat.getSelectedIndex()==0){
            pilihRalan();
        }else if(TabPilihRawat.getSelectedIndex()==1){
            pilihRanap();
        }
    }
    
    public void pilihRalan(){
        if(TabRawatJalan.getSelectedIndex()==0){
            tampil();
        }else if(TabRawatJalan.getSelectedIndex()==1){
            tampil2();
        }
    }
    
    public void pilihRanap(){
        if(TabRawatInap.getSelectedIndex()==0){
            tampil3();
        }else if(TabRawatInap.getSelectedIndex()==1){
            tampil4();
        }
    }
    
    private void tampil3() {
        Valid.tabelKosong(tabMode3);
        try {
            ps=koneksi.prepareStatement(
                    "select permintaan_radiologi.noorder,permintaan_radiologi.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,permintaan_radiologi.tgl_permintaan,"+
                    "if(permintaan_radiologi.jam_permintaan='00:00:00','',permintaan_radiologi.jam_permintaan) as jam_permintaan,"+
                    "if(permintaan_radiologi.tgl_sampel='0000-00-00','',permintaan_radiologi.tgl_sampel) as tgl_sampel,if(permintaan_radiologi.jam_sampel='00:00:00','',permintaan_radiologi.jam_sampel) as jam_sampel,"+
                    "permintaan_radiologi.tgl_hasil,if(permintaan_radiologi.jam_hasil='00:00:00','',permintaan_radiologi.jam_hasil) as jam_hasil,"+
                    "permintaan_radiologi.dokter_perujuk,dokter.nm_dokter,bangsal.nm_bangsal from permintaan_radiologi inner join reg_periksa "+
                    "inner join pasien inner join dokter inner join bangsal inner join kamar inner join kamar_inap on permintaan_radiologi.no_rawat=reg_periksa.no_rawat and "+
                    "reg_periksa.no_rkm_medis=pasien.no_rkm_medis and permintaan_radiologi.dokter_perujuk=dokter.kd_dokter and kamar.kd_bangsal=bangsal.kd_bangsal "+
                    "and reg_periksa.no_rawat=kamar_inap.no_rawat and kamar_inap.kd_kamar=kamar.kd_kamar where "+
                    "permintaan_radiologi.status='ranap' and permintaan_radiologi.tgl_permintaan between ? and ? and dokter.nm_dokter like ? and bangsal.nm_bangsal like ? and permintaan_radiologi.noorder like ? or "+
                    "permintaan_radiologi.status='ranap' and permintaan_radiologi.tgl_permintaan between ? and ? and dokter.nm_dokter like ? and bangsal.nm_bangsal like ? and permintaan_radiologi.no_rawat like ? or "+
                    "permintaan_radiologi.status='ranap' and permintaan_radiologi.tgl_permintaan between ? and ? and dokter.nm_dokter like ? and bangsal.nm_bangsal like ? and reg_periksa.no_rkm_medis like ? or "+
                    "permintaan_radiologi.status='ranap' and permintaan_radiologi.tgl_permintaan between ? and ? and dokter.nm_dokter like ? and bangsal.nm_bangsal like ? and pasien.nm_pasien like ? or "+
                    "permintaan_radiologi.status='ranap' and permintaan_radiologi.tgl_permintaan between ? and ? and dokter.nm_dokter like ? and bangsal.nm_bangsal like ? and dokter.nm_dokter like ? "+
                    "group by permintaan_radiologi.noorder order by permintaan_radiologi.tgl_permintaan,permintaan_radiologi.jam_permintaan desc");
            try {
                ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(3,"%"+CrDokter2.getText().trim()+"%");
                ps.setString(4,"%"+Kamar.getText().trim()+"%");
                ps.setString(5,"%"+TCari.getText()+"%");
                ps.setString(6,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(7,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(8,"%"+CrDokter2.getText().trim()+"%");
                ps.setString(9,"%"+Kamar.getText().trim()+"%");
                ps.setString(10,"%"+TCari.getText()+"%");
                ps.setString(11,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(12,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(13,"%"+CrDokter2.getText().trim()+"%");
                ps.setString(14,"%"+Kamar.getText().trim()+"%");
                ps.setString(15,"%"+TCari.getText()+"%");
                ps.setString(16,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(17,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(18,"%"+CrDokter2.getText().trim()+"%");
                ps.setString(19,"%"+Kamar.getText().trim()+"%");
                ps.setString(20,"%"+TCari.getText()+"%");
                ps.setString(21,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(22,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(23,"%"+CrDokter2.getText().trim()+"%");
                ps.setString(24,"%"+Kamar.getText().trim()+"%");
                ps.setString(25,"%"+TCari.getText()+"%");
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode3.addRow(new String[]{
                        rs.getString("noorder"),rs.getString("no_rawat"),rs.getString("no_rkm_medis")+" "+
                        rs.getString("nm_pasien"),rs.getString("tgl_permintaan"),rs.getString("jam_permintaan"),
                        rs.getString("tgl_sampel"),rs.getString("jam_sampel"),rs.getString("tgl_hasil"),
                        rs.getString("jam_hasil"),rs.getString("dokter_perujuk"),rs.getString("nm_dokter"),rs.getString("nm_bangsal")
                    });
                    ps2=koneksi.prepareStatement(
                            "select permintaan_pemeriksaan_radiologi.kd_jenis_prw,jns_perawatan_radiologi.nm_perawatan "+
                            "from permintaan_pemeriksaan_radiologi inner join jns_perawatan_radiologi on "+
                            "permintaan_pemeriksaan_radiologi.kd_jenis_prw=jns_perawatan_radiologi.kd_jenis_prw "+
                            "where permintaan_pemeriksaan_radiologi.noorder=?");
                    try {
                        ps2.setString(1,rs.getString("noorder"));
                        rs2=ps2.executeQuery();
                        while(rs2.next()){
                            tabMode3.addRow(new Object[]{
                                "","",rs2.getString("nm_perawatan"),"","","","","","","","",""
                            });
                        }
                    } catch (Exception e) {
                        System.out.println("Notif 2 : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                }
                rs.last();
                LCount.setText(""+rs.getRow());
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
        } catch (Exception e) {
            System.out.println("Notif : "+e);
        }        
    }
    
    private void tampil4() {
        Valid.tabelKosong(tabMode4);  
        try {
            ps=koneksi.prepareStatement(
                    "select permintaan_radiologi.noorder,permintaan_radiologi.no_rawat,reg_periksa.no_rkm_medis,"+
                    "pasien.nm_pasien,jns_perawatan_radiologi.nm_perawatan,permintaan_radiologi.tgl_permintaan,"+
                    "if(permintaan_radiologi.jam_permintaan='00:00:00','',permintaan_radiologi.jam_permintaan) as jam_permintaan,permintaan_radiologi.tgl_sampel,"+
                    "if(permintaan_radiologi.jam_sampel='00:00:00','',permintaan_radiologi.jam_sampel) as jam_sampel, permintaan_radiologi.tgl_hasil,"+
                    "if(permintaan_radiologi.jam_hasil='00:00:00','',permintaan_radiologi.jam_hasil) as jam_hasil,"+
                    "permintaan_radiologi.dokter_perujuk,dokter.nm_dokter,bangsal.nm_bangsal from permintaan_radiologi "+
                    "inner join reg_periksa inner join pasien inner join permintaan_pemeriksaan_radiologi "+
                    "inner join jns_perawatan_radiologi inner join dokter inner join bangsal inner join kamar inner join kamar_inap on "+
                    "permintaan_radiologi.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and "+
                    "permintaan_radiologi.noorder=permintaan_pemeriksaan_radiologi.noorder and jns_perawatan_radiologi.kd_jenis_prw=permintaan_pemeriksaan_radiologi.kd_jenis_prw "+
                    "and permintaan_radiologi.dokter_perujuk=dokter.kd_dokter and kamar.kd_bangsal=bangsal.kd_bangsal "+
                    " and reg_periksa.no_rawat=kamar_inap.no_rawat and kamar_inap.kd_kamar=kamar.kd_kamar where "+
                    "permintaan_radiologi.status='ranap' and permintaan_radiologi.tgl_permintaan between ? and ? and dokter.nm_dokter like ? and bangsal.nm_bangsal like ? and permintaan_radiologi.noorder like ? or "+
                    "permintaan_radiologi.status='ranap' and permintaan_radiologi.tgl_permintaan between ? and ? and dokter.nm_dokter like ? and bangsal.nm_bangsal like ? and permintaan_radiologi.no_rawat like ? or "+
                    "permintaan_radiologi.status='ranap' and permintaan_radiologi.tgl_permintaan between ? and ? and dokter.nm_dokter like ? and bangsal.nm_bangsal like ? and reg_periksa.no_rkm_medis like ? or "+
                    "permintaan_radiologi.status='ranap' and permintaan_radiologi.tgl_permintaan between ? and ? and dokter.nm_dokter like ? and bangsal.nm_bangsal like ? and pasien.nm_pasien like ? or "+
                    "permintaan_radiologi.status='ranap' and permintaan_radiologi.tgl_permintaan between ? and ? and dokter.nm_dokter like ? and bangsal.nm_bangsal like ? and jns_perawatan_radiologi.nm_perawatan like ? or "+
                    "permintaan_radiologi.status='ranap' and permintaan_radiologi.tgl_permintaan between ? and ? and dokter.nm_dokter like ? and bangsal.nm_bangsal like ? and dokter.nm_dokter like ? "+
                    "group by permintaan_radiologi.noorder order by permintaan_radiologi.tgl_permintaan,permintaan_radiologi.jam_permintaan desc");
            try {
                ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(3,"%"+CrDokter2.getText().trim()+"%");
                ps.setString(4,"%"+Kamar.getText().trim()+"%");
                ps.setString(5,"%"+TCari.getText()+"%");
                ps.setString(6,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(7,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(8,"%"+CrDokter2.getText().trim()+"%");
                ps.setString(9,"%"+Kamar.getText().trim()+"%");
                ps.setString(10,"%"+TCari.getText()+"%");
                ps.setString(11,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(12,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(13,"%"+CrDokter2.getText().trim()+"%");
                ps.setString(14,"%"+Kamar.getText().trim()+"%");
                ps.setString(15,"%"+TCari.getText()+"%");
                ps.setString(16,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(17,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(18,"%"+CrDokter2.getText().trim()+"%");
                ps.setString(19,"%"+Kamar.getText().trim()+"%");
                ps.setString(20,"%"+TCari.getText()+"%");
                ps.setString(21,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(22,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(23,"%"+CrDokter2.getText().trim()+"%");
                ps.setString(24,"%"+Kamar.getText().trim()+"%");
                ps.setString(25,"%"+TCari.getText()+"%");
                ps.setString(26,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(27,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(28,"%"+CrDokter2.getText().trim()+"%");
                ps.setString(29,"%"+Kamar.getText().trim()+"%");
                ps.setString(30,"%"+TCari.getText()+"%");
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode4.addRow(new String[]{
                        rs.getString("noorder"),rs.getString("no_rawat"),rs.getString("no_rkm_medis")+" "+rs.getString("nm_pasien"),
                        rs.getString("nm_perawatan"),rs.getString("tgl_permintaan"),rs.getString("jam_permintaan"),
                        rs.getString("tgl_sampel"),rs.getString("jam_sampel"),rs.getString("tgl_hasil"),
                        rs.getString("jam_hasil"),rs.getString("dokter_perujuk"),rs.getString("nm_dokter"),rs.getString("nm_bangsal")
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
            LCount.setText(""+tabMode4.getRowCount());
        } catch (Exception e) {
            System.out.println("Notif : "+e);
        }            
    }
    
    private void jam(){
        ActionListener taskPerformer = (ActionEvent e) -> {
            if(aktif==true){
                nol_detik = "";
                now = Calendar.getInstance().getTime();
                nilai_detik = now.getSeconds();
                if (nilai_detik <= 9) {
                    nol_detik = "0";
                }

                detik = nol_detik + Integer.toString(nilai_detik);
                if(detik.equals("05")){
                    permintaanbaru=0;
                    if(formalarm.contains("ralan")){
                        tampil();
                        for(i=0;i<tbRadiologiRalan.getRowCount();i++){
                            if((!tbRadiologiRalan.getValueAt(i,0).toString().equals(""))&&tbRadiologiRalan.getValueAt(i,5).toString().equals("")){
                                permintaanbaru++;
                            }
                        }
                    }

                    if(formalarm.contains("ranap")){
                        tampil3();
                        for(i=0;i<tbRadiologiRanap.getRowCount();i++){
                            if((!tbRadiologiRanap.getValueAt(i,0).toString().equals(""))&&tbRadiologiRanap.getValueAt(i,5).toString().equals("")){
                                permintaanbaru++;
                            }
                        }
                    }

                    if(permintaanbaru>0){
                        try {
                            music = new BackgroundMusic("./suara/alarm.mp3");
                            music.start();
                        } catch (Exception ex) {
                            System.out.println(ex);
                        }
                    }
                }
            }
        };
        // Timer
        new Timer(1000, taskPerformer).start();
    }
 
}

package tranfusidarah;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariPetugas;

public class UTDStokDarah extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement ps;
    private ResultSet rs;
    private UTDKomponenDarah komponen=new UTDKomponenDarah(null,true);
    private int i;
    private Calendar cal;
    private SimpleDateFormat sdf;

    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public UTDStokDarah(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        Object[] row={
            "No.Kantung","Komponen","G.D.","Rhesus","Aftap","Kadaluarsa",
            "Asal Darah","Status","Jasa Sarana","Paket BHP",
            "KSO","Manajemen","Biaya","Pembatalan","Kode Komponen"
        };
        tabMode=new DefaultTableModel(null,row){
            @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}            
        };
        tbDokter.setModel(tabMode);

        tbDokter.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 15; i++) {
            TableColumn column = tbDokter.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(80);
            }else if(i==1){
                column.setPreferredWidth(230);
            }else if(i==2){
                column.setPreferredWidth(35);
            }else if(i==3){
                column.setPreferredWidth(50);
            }else if(i==4){
                column.setPreferredWidth(75);
            }else if(i==5){
                column.setPreferredWidth(75);
            }else if(i==6){
                column.setPreferredWidth(100);
            }else if(i==7){
                column.setPreferredWidth(80);
            }else{
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }
        }
        tbDokter.setDefaultRenderer(Object.class, new WarnaTable());

        NoKantong.setDocument(new batasInput((byte)20).getKata(NoKantong));
        KodeKomponen.setDocument(new batasInput((byte)5).getKata(KodeKomponen));
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
        
        komponen.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(komponen.getTable().getSelectedRow()!= -1){ 
                    KodeKomponen.setText(komponen.getTable().getValueAt(komponen.getTable().getSelectedRow(),0).toString());
                    NoKantong.setText(komponen.getTable().getValueAt(komponen.getTable().getSelectedRow(),0).toString()+NoKantong.getText());
                    NamaKomponen.setText(komponen.getTable().getValueAt(komponen.getTable().getSelectedRow(),1).toString());
                    KodeKomponen.requestFocus();
                    cal = Calendar.getInstance();
                    cal.setTime(Aftap.getDate());
                    cal.add( Calendar.DATE,Integer.parseInt(komponen.getTable().getValueAt(komponen.getTable().getSelectedRow(),2).toString()));
                    sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Valid.SetTgl(Kadaluarsa,sdf.format(cal.getTime()));
                    NoKantong.requestFocus();
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
        
        komponen.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        komponen.dispose();
                    }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });  
        ChkInput.setSelected(false);
        panelCari.setVisible(false);
        posisi(); 
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

        Popup = new javax.swing.JPopupMenu();
        ppCetak = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        jPanel2 = new javax.swing.JPanel();
        scrollPane1 = new widget.ScrollPane();
        tbDokter = new widget.Table();
        PanelInput = new javax.swing.JPanel();
        FormInput = new widget.PanelBiasa();
        label12 = new widget.Label();
        NoKantong = new widget.TextBox();
        label18 = new widget.Label();
        KodeKomponen = new widget.TextBox();
        NamaKomponen = new widget.TextBox();
        btnKomponen = new widget.Button();
        jLabel10 = new widget.Label();
        GolonganDarah = new widget.ComboBox();
        jLabel11 = new widget.Label();
        Resus = new widget.ComboBox();
        Aftap = new widget.Tanggal();
        label32 = new widget.Label();
        Kadaluarsa = new widget.Tanggal();
        label33 = new widget.Label();
        jLabel12 = new widget.Label();
        Asal = new widget.ComboBox();
        ChkInput = new widget.CekBox();
        PanelCariUtama = new javax.swing.JPanel();
        panelCariKategori = new javax.swing.JPanel();
        panelCari = new widget.panelisi();
        jLabel14 = new widget.Label();
        CmbCrStatus = new widget.ComboBox();
        jLabel13 = new widget.Label();
        CmbCariResus = new widget.ComboBox();
        jLabel17 = new widget.Label();
        CmbCariGd = new widget.ComboBox();
        jLabel18 = new widget.Label();
        CmbCariAsal = new widget.ComboBox();
        ChkCari = new widget.CekBox();
        panelisi2 = new widget.panelisi();
        label9 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        label10 = new widget.Label();
        LCount = new widget.Label();
        panelisi1 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();

        Popup.setName("Popup"); // NOI18N

        ppCetak.setBackground(new java.awt.Color(255, 255, 254));
        ppCetak.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        ppCetak.setForeground(new java.awt.Color(70, 70, 70));
        ppCetak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png"))); // NOI18N
        ppCetak.setText("Cetak");
        ppCetak.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppCetak.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppCetak.setName("ppCetak"); // NOI18N
        ppCetak.setPreferredSize(new java.awt.Dimension(150, 25));
        ppCetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPrintActionPerformed(evt);
            }
        });
        Popup.add(ppCetak);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Stok Darah ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(70, 70, 70))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        jPanel2.setName("jPanel2"); // NOI18N
        jPanel2.setOpaque(false);
        jPanel2.setPreferredSize(new java.awt.Dimension(816, 100));
        jPanel2.setLayout(new java.awt.BorderLayout(1, 1));
        internalFrame1.add(jPanel2, java.awt.BorderLayout.PAGE_END);

        scrollPane1.setComponentPopupMenu(Popup);
        scrollPane1.setName("scrollPane1"); // NOI18N
        scrollPane1.setOpaque(true);

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
        tbDokter.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbDokter.setComponentPopupMenu(Popup);
        tbDokter.setName("tbDokter"); // NOI18N
        tbDokter.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDokterMouseClicked(evt);
            }
        });
        tbDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbDokterKeyPressed(evt);
            }
        });
        scrollPane1.setViewportView(tbDokter);

        internalFrame1.add(scrollPane1, java.awt.BorderLayout.CENTER);

        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(660, 138));
        FormInput.setLayout(null);

        label12.setText("No.Kantong :");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(75, 23));
        FormInput.add(label12);
        label12.setBounds(0, 12, 85, 23);

        NoKantong.setName("NoKantong"); // NOI18N
        NoKantong.setPreferredSize(new java.awt.Dimension(207, 23));
        NoKantong.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoKantongKeyPressed(evt);
            }
        });
        FormInput.add(NoKantong);
        NoKantong.setBounds(88, 12, 100, 23);

        label18.setText("Komponen :");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label18);
        label18.setBounds(0, 42, 85, 23);

        KodeKomponen.setName("KodeKomponen"); // NOI18N
        KodeKomponen.setPreferredSize(new java.awt.Dimension(80, 23));
        KodeKomponen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodeKomponenKeyPressed(evt);
            }
        });
        FormInput.add(KodeKomponen);
        KodeKomponen.setBounds(88, 42, 100, 23);

        NamaKomponen.setEditable(false);
        NamaKomponen.setName("NamaKomponen"); // NOI18N
        NamaKomponen.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NamaKomponen);
        NamaKomponen.setBounds(190, 42, 403, 23);

        btnKomponen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnKomponen.setMnemonic('1');
        btnKomponen.setToolTipText("Alt+1");
        btnKomponen.setName("btnKomponen"); // NOI18N
        btnKomponen.setPreferredSize(new java.awt.Dimension(28, 23));
        btnKomponen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKomponenActionPerformed(evt);
            }
        });
        FormInput.add(btnKomponen);
        btnKomponen.setBounds(595, 42, 28, 23);

        jLabel10.setText("G.D.:");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(355, 72, 40, 23);

        GolonganDarah.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "A", "AB", "B", "O" }));
        GolonganDarah.setName("GolonganDarah"); // NOI18N
        GolonganDarah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GolonganDarahKeyPressed(evt);
            }
        });
        FormInput.add(GolonganDarah);
        GolonganDarah.setBounds(398, 72, 65, 23);

        jLabel11.setText("Resus :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(221, 72, 70, 23);

        Resus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "(-)", "(+)" }));
        Resus.setName("Resus"); // NOI18N
        Resus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ResusKeyPressed(evt);
            }
        });
        FormInput.add(Resus);
        Resus.setBounds(294, 72, 65, 23);

        Aftap.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "01-05-2019" }));
        Aftap.setDisplayFormat("dd-MM-yyyy");
        Aftap.setName("Aftap"); // NOI18N
        Aftap.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AftapKeyPressed(evt);
            }
        });
        FormInput.add(Aftap);
        Aftap.setBounds(88, 72, 100, 23);

        label32.setText("Tanggal Aftap :");
        label32.setName("label32"); // NOI18N
        label32.setPreferredSize(new java.awt.Dimension(35, 23));
        FormInput.add(label32);
        label32.setBounds(0, 72, 85, 23);

        Kadaluarsa.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "01-05-2019" }));
        Kadaluarsa.setDisplayFormat("dd-MM-yyyy");
        Kadaluarsa.setName("Kadaluarsa"); // NOI18N
        Kadaluarsa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KadaluarsaKeyPressed(evt);
            }
        });
        FormInput.add(Kadaluarsa);
        Kadaluarsa.setBounds(88, 102, 100, 23);

        label33.setText("Kadaluarsa :");
        label33.setName("label33"); // NOI18N
        label33.setPreferredSize(new java.awt.Dimension(35, 23));
        FormInput.add(label33);
        label33.setBounds(0, 102, 85, 23);

        jLabel12.setText("Asal Darah :");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(221, 102, 70, 23);

        Asal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Produksi Sendiri", "Hibah", "Beli" }));
        Asal.setName("Asal"); // NOI18N
        Asal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AsalKeyPressed(evt);
            }
        });
        FormInput.add(Asal);
        Asal.setBounds(294, 102, 169, 23);

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

        PanelCariUtama.setName("PanelCariUtama"); // NOI18N
        PanelCariUtama.setOpaque(false);
        PanelCariUtama.setPreferredSize(new java.awt.Dimension(100, 162));
        PanelCariUtama.setLayout(new java.awt.BorderLayout(1, 1));

        panelCariKategori.setName("panelCariKategori"); // NOI18N
        panelCariKategori.setOpaque(false);
        panelCariKategori.setPreferredSize(new java.awt.Dimension(100, 62));
        panelCariKategori.setLayout(new java.awt.BorderLayout(1, 1));

        panelCari.setName("panelCari"); // NOI18N
        panelCari.setPreferredSize(new java.awt.Dimension(44, 44));
        panelCari.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 3, 7));

        jLabel14.setText("Status :");
        jLabel14.setName("jLabel14"); // NOI18N
        jLabel14.setPreferredSize(new java.awt.Dimension(45, 23));
        panelCari.add(jLabel14);

        CmbCrStatus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ada", "Diambil", "Dimusnahkan" }));
        CmbCrStatus.setName("CmbCrStatus"); // NOI18N
        CmbCrStatus.setPreferredSize(new java.awt.Dimension(125, 23));
        CmbCrStatus.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                CmbCrStatusItemStateChanged(evt);
            }
        });
        panelCari.add(CmbCrStatus);

        jLabel13.setText("Resus :");
        jLabel13.setName("jLabel13"); // NOI18N
        jLabel13.setPreferredSize(new java.awt.Dimension(43, 23));
        panelCari.add(jLabel13);

        CmbCariResus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "(-)", "(+)" }));
        CmbCariResus.setName("CmbCariResus"); // NOI18N
        CmbCariResus.setPreferredSize(new java.awt.Dimension(65, 23));
        CmbCariResus.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                CmbCariResusItemStateChanged(evt);
            }
        });
        CmbCariResus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CmbCariResusKeyPressed(evt);
            }
        });
        panelCari.add(CmbCariResus);

        jLabel17.setText("G.D.:");
        jLabel17.setName("jLabel17"); // NOI18N
        jLabel17.setPreferredSize(new java.awt.Dimension(37, 23));
        panelCari.add(jLabel17);

        CmbCariGd.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "A", "AB", "B", "O" }));
        CmbCariGd.setName("CmbCariGd"); // NOI18N
        CmbCariGd.setPreferredSize(new java.awt.Dimension(65, 23));
        CmbCariGd.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                CmbCariGdItemStateChanged(evt);
            }
        });
        CmbCariGd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CmbCariGdKeyPressed(evt);
            }
        });
        panelCari.add(CmbCariGd);

        jLabel18.setText("Asal Darah :");
        jLabel18.setName("jLabel18"); // NOI18N
        jLabel18.setPreferredSize(new java.awt.Dimension(75, 23));
        panelCari.add(jLabel18);

        CmbCariAsal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Produksi Sendiri", "Hibah", "Beli" }));
        CmbCariAsal.setName("CmbCariAsal"); // NOI18N
        CmbCariAsal.setPreferredSize(new java.awt.Dimension(140, 23));
        CmbCariAsal.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                CmbCariAsalItemStateChanged(evt);
            }
        });
        CmbCariAsal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CmbCariAsalKeyPressed(evt);
            }
        });
        panelCari.add(CmbCariAsal);

        panelCariKategori.add(panelCari, java.awt.BorderLayout.CENTER);

        ChkCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkCari.setMnemonic('I');
        ChkCari.setText("  .: Pencarian Data");
        ChkCari.setToolTipText("Alt+I");
        ChkCari.setBorderPainted(true);
        ChkCari.setBorderPaintedFlat(true);
        ChkCari.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkCari.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkCari.setIconTextGap(2);
        ChkCari.setName("ChkCari"); // NOI18N
        ChkCari.setPreferredSize(new java.awt.Dimension(632, 22));
        ChkCari.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkCari.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkCari.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkCariActionPerformed(evt);
            }
        });
        panelCariKategori.add(ChkCari, java.awt.BorderLayout.PAGE_START);

        PanelCariUtama.add(panelCariKategori, java.awt.BorderLayout.PAGE_START);

        panelisi2.setBackground(new java.awt.Color(255, 150, 255));
        panelisi2.setName("panelisi2"); // NOI18N
        panelisi2.setPreferredSize(new java.awt.Dimension(100, 44));
        panelisi2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        label9.setText("Key Word :");
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi2.add(label9);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(350, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelisi2.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('1');
        BtnCari.setToolTipText("Alt+1");
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
        panelisi2.add(BtnCari);

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
        panelisi2.add(BtnAll);

        label10.setText("Record :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi2.add(label10);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi2.add(LCount);

        PanelCariUtama.add(panelisi2, java.awt.BorderLayout.CENTER);

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 55));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16i.png"))); // NOI18N
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
        panelisi1.add(BtnSimpan);

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
        panelisi1.add(BtnBatal);

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
        panelisi1.add(BtnEdit);

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

        PanelCariUtama.add(panelisi1, java.awt.BorderLayout.PAGE_END);

        internalFrame1.add(PanelCariUtama, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

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

    private void tbDokterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDokterMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbDokterMouseClicked

    private void tbDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbDokterKeyPressed
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
}//GEN-LAST:event_tbDokterKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if(KodeKomponen.getText().trim().equals("")||NamaKomponen.getText().trim().equals("")||NoKantong.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Pilih dulu data yang akan Anda hapus dengan menklik data pada tabel...!!!");
            tbDokter.requestFocus();
        }else{
            Valid.hapusTable(tabMode,NoKantong,"utd_stok_darah","no_kantong");
            tampil();
            emptTeks();
        }
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnBatal, BtnEdit);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
        if(NoKantong.getText().trim().equals("")){
            Valid.textKosong(NoKantong,"Nomor Kantong");
        }else if(KodeKomponen.getText().trim().equals("")||NamaKomponen.getText().trim().equals("")){
            Valid.textKosong(KodeKomponen,"Komponen");
        }else{
            if(Sequel.mengedittf("utd_stok_darah","no_kantong=?",
                "no_kantong=?,kode_komponen=?,golongan_darah=?,resus=?,"+
                "tanggal_aftap=?,tanggal_kadaluarsa=?,asal_darah=?",8,new String[]{     
                    NoKantong.getText(),KodeKomponen.getText(),GolonganDarah.getSelectedItem().toString(),
                    Resus.getSelectedItem().toString(),Valid.SetTgl(Aftap.getSelectedItem()+""),
                    Valid.SetTgl(Kadaluarsa.getSelectedItem()+""),Asal.getSelectedItem().toString(),
                    tabMode.getValueAt(tbDokter.getSelectedRow(),0).toString()
              })==true){
                emptTeks();
                tampil();
            }
        }
}//GEN-LAST:event_BtnEditActionPerformed

    private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnEditActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnPrint);
        }
}//GEN-LAST:event_BtnEditKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        if(tabMode.getRowCount()==0){            
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>(); 
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("emailrs",akses.getemailrs());   
                param.put("logo",Sequel.cariGambar("select logo from setting"));                 
            if(panelCari.isVisible()==false){
                Valid.MyReportqry("rptUTDStokDarah.jasper","report","::[ Data Stok Darah ]::",
                    "select utd_stok_darah.no_kantong,utd_komponen_darah.nama as darah,"+
                    "utd_stok_darah.golongan_darah,utd_stok_darah.resus,"+
                    "utd_stok_darah.tanggal_aftap,utd_stok_darah.tanggal_kadaluarsa,"+
                    "utd_stok_darah.asal_darah,utd_stok_darah.status,"+
                    "utd_komponen_darah.jasa_sarana,utd_komponen_darah.paket_bhp,"+
                    "utd_komponen_darah.kso,utd_komponen_darah.manajemen,"+
                    "utd_komponen_darah.total,utd_komponen_darah.pembatalan,utd_stok_darah.kode_komponen "+
                    "from utd_komponen_darah inner join utd_stok_darah "+
                    "on utd_stok_darah.kode_komponen=utd_komponen_darah.kode "+
                    "where utd_stok_darah.status='Ada' and utd_stok_darah.no_kantong like '%"+TCari.getText().trim()+"%' or "+
                    "utd_stok_darah.status='Ada' and utd_komponen_darah.nama like '%"+TCari.getText().trim()+"%' or "+
                    "utd_stok_darah.status='Ada' and utd_stok_darah.resus like '%"+TCari.getText().trim()+"%' or "+
                    "utd_stok_darah.status='Ada' and utd_stok_darah.asal_darah like '%"+TCari.getText().trim()+"%' or "+
                    "utd_stok_darah.status='Ada' and utd_stok_darah.status like '%"+TCari.getText().trim()+"%' "+
                    "order by utd_stok_darah.tanggal_kadaluarsa",param);            
            }else{
                Valid.MyReportqry("rptUTDStokDarah.jasper","report","::[ Data Stok Darah ]::",
                    "select utd_stok_darah.no_kantong,utd_komponen_darah.nama as darah,"+
                    "utd_stok_darah.golongan_darah,utd_stok_darah.resus,"+
                    "utd_stok_darah.tanggal_aftap,utd_stok_darah.tanggal_kadaluarsa,"+
                    "utd_stok_darah.asal_darah,utd_stok_darah.status,"+
                    "utd_komponen_darah.jasa_sarana,utd_komponen_darah.paket_bhp,"+
                    "utd_komponen_darah.kso,utd_komponen_darah.manajemen,"+
                    "utd_komponen_darah.total,utd_komponen_darah.pembatalan,utd_stok_darah.kode_komponen "+
                    "from utd_komponen_darah inner join utd_stok_darah "+
                    "on utd_stok_darah.kode_komponen=utd_komponen_darah.kode "+
                    "where utd_stok_darah.status='"+CmbCrStatus.getSelectedItem().toString()+"' and utd_stok_darah.resus='"+CmbCariResus.getSelectedItem().toString()+"' and "+
                    "utd_stok_darah.golongan_darah='"+CmbCariGd.getSelectedItem().toString()+"' and utd_stok_darah.asal_darah='"+CmbCariAsal.getSelectedItem().toString()+"' and "+
                    "utd_stok_darah.no_kantong like '%"+TCari.getText().trim()+"%' or "+
                    "utd_stok_darah.status='"+CmbCrStatus.getSelectedItem().toString()+"' and utd_stok_darah.resus='"+CmbCariResus.getSelectedItem().toString()+"' and "+
                    "utd_stok_darah.golongan_darah='"+CmbCariGd.getSelectedItem().toString()+"' and utd_stok_darah.asal_darah='"+CmbCariAsal.getSelectedItem().toString()+"' and "+
                    "utd_komponen_darah.nama like '%"+TCari.getText().trim()+"%' "+
                    " order by utd_stok_darah.tanggal_kadaluarsa",param);            
            }
            this.setCursor(Cursor.getDefaultCursor());
        }        
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt,BtnEdit,BtnAll);
        }
}//GEN-LAST:event_BtnPrintKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        tampil();
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
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

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(NoKantong.getText().trim().equals("")){
            Valid.textKosong(NoKantong,"Nomor Kantong");
        }else if(KodeKomponen.getText().trim().equals("")||NamaKomponen.getText().trim().equals("")){
            Valid.textKosong(KodeKomponen,"Komponen");
        }else{
            if(Sequel.menyimpantf("utd_stok_darah","?,?,?,?,?,?,?,?","Kode",8,new String[]{
                NoKantong.getText(),KodeKomponen.getText(),GolonganDarah.getSelectedItem().toString(),
                Resus.getSelectedItem().toString(),Valid.SetTgl(Aftap.getSelectedItem()+""),
                Valid.SetTgl(Kadaluarsa.getSelectedItem()+""),Asal.getSelectedItem().toString(),
                "Ada"
              })==true){
                emptTeks();
                tampil();
            }
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,Asal,BtnBatal);
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
/*
private void KdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdKeyPressed
    Valid.pindah(evt,BtnCari,Nm);
}//GEN-LAST:event_TKdKeyPressed
*/

    private void NoKantongKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoKantongKeyPressed
        Valid.pindah(evt,KodeKomponen,TCari);
    }//GEN-LAST:event_NoKantongKeyPressed

private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
  isForm();                
}//GEN-LAST:event_ChkInputActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void KodeKomponenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodeKomponenKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama from utd_komponen_darah where kode=?",NamaKomponen,KodeKomponen.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnKomponenActionPerformed(null);
        }else{
            Valid.pindah(evt,NoKantong,Aftap);
        }
    }//GEN-LAST:event_KodeKomponenKeyPressed

    private void btnKomponenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKomponenActionPerformed
        komponen.emptTeks();
        komponen.isCek();
        komponen.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        komponen.setLocationRelativeTo(internalFrame1);        
        komponen.setVisible(true);
    }//GEN-LAST:event_btnKomponenActionPerformed

    private void GolonganDarahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GolonganDarahKeyPressed
        Valid.pindah(evt,Resus,Asal);
    }//GEN-LAST:event_GolonganDarahKeyPressed

    private void ResusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ResusKeyPressed
        Valid.pindah(evt,Kadaluarsa,GolonganDarah);
    }//GEN-LAST:event_ResusKeyPressed

    private void AftapKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AftapKeyPressed
        Valid.pindah(evt,KodeKomponen,Kadaluarsa);
    }//GEN-LAST:event_AftapKeyPressed

    private void KadaluarsaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KadaluarsaKeyPressed
        Valid.pindah(evt,Aftap,Resus);
    }//GEN-LAST:event_KadaluarsaKeyPressed

    private void AsalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AsalKeyPressed
        Valid.pindah(evt,GolonganDarah,BtnSimpan);
    }//GEN-LAST:event_AsalKeyPressed

    private void CmbCrStatusItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_CmbCrStatusItemStateChanged
        tampil();
    }//GEN-LAST:event_CmbCrStatusItemStateChanged

    private void ChkCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkCariActionPerformed
        if(ChkCari.isSelected()==true){
            panelCari.setVisible(true);
            TCari.setText("");
            tampil();
            posisi();
        }else if(ChkCari.isSelected()==false){
            panelCari.setVisible(false);
            BtnCariActionPerformed(null);
            posisi();
        }
    }//GEN-LAST:event_ChkCariActionPerformed

    private void CmbCariResusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CmbCariResusKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_CmbCariResusKeyPressed

    private void CmbCariGdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CmbCariGdKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_CmbCariGdKeyPressed

    private void CmbCariAsalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CmbCariAsalKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_CmbCariAsalKeyPressed

    private void CmbCariResusItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_CmbCariResusItemStateChanged
        tampil();
    }//GEN-LAST:event_CmbCariResusItemStateChanged

    private void CmbCariGdItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_CmbCariGdItemStateChanged
        tampil();
    }//GEN-LAST:event_CmbCariGdItemStateChanged

    private void CmbCariAsalItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_CmbCariAsalItemStateChanged
        tampil();
    }//GEN-LAST:event_CmbCariAsalItemStateChanged

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            UTDStokDarah dialog = new UTDStokDarah(new javax.swing.JFrame(), true);
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
    private widget.Tanggal Aftap;
    private widget.ComboBox Asal;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkCari;
    private widget.CekBox ChkInput;
    private widget.ComboBox CmbCariAsal;
    private widget.ComboBox CmbCariGd;
    private widget.ComboBox CmbCariResus;
    private widget.ComboBox CmbCrStatus;
    private widget.PanelBiasa FormInput;
    private widget.ComboBox GolonganDarah;
    private widget.Tanggal Kadaluarsa;
    private widget.TextBox KodeKomponen;
    private widget.Label LCount;
    private widget.TextBox NamaKomponen;
    private widget.TextBox NoKantong;
    private javax.swing.JPanel PanelCariUtama;
    private javax.swing.JPanel PanelInput;
    private javax.swing.JPopupMenu Popup;
    private widget.ComboBox Resus;
    private widget.TextBox TCari;
    private widget.Button btnKomponen;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel12;
    private widget.Label jLabel13;
    private widget.Label jLabel14;
    private widget.Label jLabel17;
    private widget.Label jLabel18;
    private javax.swing.JPanel jPanel2;
    private widget.Label label10;
    private widget.Label label12;
    private widget.Label label18;
    private widget.Label label32;
    private widget.Label label33;
    private widget.Label label9;
    private widget.panelisi panelCari;
    private javax.swing.JPanel panelCariKategori;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi2;
    private javax.swing.JMenuItem ppCetak;
    private widget.ScrollPane scrollPane1;
    private widget.Table tbDokter;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            if(panelCari.isVisible()==false){
                ps=koneksi.prepareStatement(
                    "select utd_stok_darah.no_kantong,utd_komponen_darah.nama as darah,"+
                    "utd_stok_darah.golongan_darah,utd_stok_darah.resus,"+
                    "utd_stok_darah.tanggal_aftap,utd_stok_darah.tanggal_kadaluarsa,"+
                    "utd_stok_darah.asal_darah,utd_stok_darah.status,"+
                    "utd_komponen_darah.jasa_sarana,utd_komponen_darah.paket_bhp,"+
                    "utd_komponen_darah.kso,utd_komponen_darah.manajemen,"+
                    "utd_komponen_darah.total,utd_komponen_darah.pembatalan,utd_stok_darah.kode_komponen "+
                    "from utd_komponen_darah inner join utd_stok_darah "+
                    "on utd_stok_darah.kode_komponen=utd_komponen_darah.kode "+
                    "where utd_stok_darah.status='Ada' and utd_stok_darah.no_kantong like ? or "+
                    "utd_stok_darah.status='Ada' and utd_komponen_darah.nama like ? or "+
                    "utd_stok_darah.status='Ada' and utd_stok_darah.resus like ? or "+
                    "utd_stok_darah.status='Ada' and utd_stok_darah.asal_darah like ? or "+
                    "utd_stok_darah.status='Ada' and utd_stok_darah.status like ? "+
                    "order by utd_stok_darah.tanggal_kadaluarsa");
            }else{
                ps=koneksi.prepareStatement(
                    "select utd_stok_darah.no_kantong,utd_komponen_darah.nama as darah,"+
                    "utd_stok_darah.golongan_darah,utd_stok_darah.resus,"+
                    "utd_stok_darah.tanggal_aftap,utd_stok_darah.tanggal_kadaluarsa,"+
                    "utd_stok_darah.asal_darah,utd_stok_darah.status,"+
                    "utd_komponen_darah.jasa_sarana,utd_komponen_darah.paket_bhp,"+
                    "utd_komponen_darah.kso,utd_komponen_darah.manajemen,"+
                    "utd_komponen_darah.total,utd_komponen_darah.pembatalan,utd_stok_darah.kode_komponen "+
                    "from utd_komponen_darah inner join utd_stok_darah "+
                    "on utd_stok_darah.kode_komponen=utd_komponen_darah.kode "+
                    "where utd_stok_darah.status=? and utd_stok_darah.resus=? and "+
                    "utd_stok_darah.golongan_darah=? and utd_stok_darah.asal_darah=? and "+
                    "utd_stok_darah.no_kantong like ? or "+
                    "utd_stok_darah.status=? and utd_stok_darah.resus=? and "+
                    "utd_stok_darah.golongan_darah=? and utd_stok_darah.asal_darah=? and "+
                    "utd_komponen_darah.nama like ? "+
                    " order by utd_stok_darah.tanggal_kadaluarsa");
            }
            
            try {
                if(panelCari.isVisible()==false){
                    ps.setString(1,"%"+TCari.getText().trim()+"%");
                    ps.setString(2,"%"+TCari.getText().trim()+"%");
                    ps.setString(3,"%"+TCari.getText().trim()+"%");
                    ps.setString(4,"%"+TCari.getText().trim()+"%");
                    ps.setString(5,"%"+TCari.getText().trim()+"%");
                }else{
                    ps.setString(1,CmbCrStatus.getSelectedItem().toString());
                    ps.setString(2,CmbCariResus.getSelectedItem().toString());
                    ps.setString(3,CmbCariGd.getSelectedItem().toString());
                    ps.setString(4,CmbCariAsal.getSelectedItem().toString());
                    ps.setString(5,"%"+TCari.getText().trim()+"%");
                    ps.setString(6,CmbCrStatus.getSelectedItem().toString());
                    ps.setString(7,CmbCariResus.getSelectedItem().toString());
                    ps.setString(8,CmbCariGd.getSelectedItem().toString());
                    ps.setString(9,CmbCariAsal.getSelectedItem().toString());
                    ps.setString(10,"%"+TCari.getText().trim()+"%");
                }
                    
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new Object[]{
                        rs.getString(1),rs.getString(2),rs.getString(3),
                        rs.getString(4),rs.getString(5),rs.getString(6),
                        rs.getString(7),rs.getString(8),rs.getDouble(9),
                        rs.getDouble(10),rs.getDouble(11),rs.getDouble(12),
                        rs.getDouble(13),rs.getDouble(14),rs.getString(15)
                    });
                }
            } catch (Exception e) {
                System.out.println(e);
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
        LCount.setText(""+tabMode.getRowCount());
    }

    public void emptTeks() {
        KodeKomponen.setText("");
        NamaKomponen.setText("");
        NoKantong.setText("");
        NoKantong.requestFocus();
    }

    private void getData() {
        if(tbDokter.getSelectedRow()!= -1){
            NoKantong.setText(tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString());
            KodeKomponen.setText(tbDokter.getValueAt(tbDokter.getSelectedRow(),14).toString());
            NamaKomponen.setText(tbDokter.getValueAt(tbDokter.getSelectedRow(),1).toString());
            GolonganDarah.setSelectedItem(tbDokter.getValueAt(tbDokter.getSelectedRow(),2).toString());
            Resus.setSelectedItem(tbDokter.getValueAt(tbDokter.getSelectedRow(),3).toString());
            Valid.SetTgl(Aftap,tbDokter.getValueAt(tbDokter.getSelectedRow(),4).toString());
            Valid.SetTgl(Kadaluarsa,tbDokter.getValueAt(tbDokter.getSelectedRow(),5).toString());
            Asal.setSelectedItem(tbDokter.getValueAt(tbDokter.getSelectedRow(),6).toString());
        }
    }

    public JTable getTable(){
        return tbDokter;
    }
    
    public void isCek(){
        BtnSimpan.setEnabled(akses.getutd_stok_darah());
        BtnHapus.setEnabled(akses.getutd_stok_darah());
        BtnEdit.setEnabled(akses.getutd_stok_darah());
        BtnPrint.setEnabled(akses.getutd_stok_darah());
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,158));
            FormInput.setVisible(true);      
            ChkInput.setVisible(true);
        }else if(ChkInput.isSelected()==false){           
            ChkInput.setVisible(false);            
            PanelInput.setPreferredSize(new Dimension(WIDTH,20));
            FormInput.setVisible(false);      
            ChkInput.setVisible(true);
        }
    }    

    private void posisi() {
        if(panelCari.isVisible()==false){
            panelCariKategori.setPreferredSize(new Dimension(WIDTH,22));
            PanelCariUtama.setPreferredSize(new Dimension(WIDTH,122));
        }else if(panelCari.isVisible()==true){
            panelCariKategori.setPreferredSize(new Dimension(WIDTH,61));
            PanelCariUtama.setPreferredSize(new Dimension(WIDTH,161));
        }
    }
 
}

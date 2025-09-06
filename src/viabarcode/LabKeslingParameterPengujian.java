package viabarcode;
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
import restore.DlgRestoreTarifUTD;

/**
 *
 * @author dosen
 */
public final class LabKeslingParameterPengujian extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement ps;
    private ResultSet rs;    
    private int i=0;

    /** Creates new form DlgJnsPerawatanRalan
     * @param parent
     * @param modal */
    public LabKeslingParameterPengujian(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        this.setLocation(8,1);
        setSize(628,674);

        Object[] row={"Kode","Nama Parameter","Metode Pengujian","Satuan","Jasa Sarana","Paket BHP","Jasa P.J. Lab","Jasa Petugas","K.S.O.","Manajemen","Total Tarif"};
        tabMode=new DefaultTableModel(null,row){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
             Class[] types = new Class[] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, 
                java.lang.Double.class, java.lang.Double.class, java.lang.Double.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbJnsPerawatan.setModel(tabMode);

        //tbJnsPerawatan.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbJnsPerawatan.getBackground()));
        tbJnsPerawatan.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbJnsPerawatan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 11; i++) {
            TableColumn column = tbJnsPerawatan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(50);
            }else if(i==1){
                column.setPreferredWidth(200);
            }else if(i==2){
                column.setPreferredWidth(100);
            }else if(i==3){
                column.setPreferredWidth(70);
            }else{
                column.setPreferredWidth(90);
            }
        }
        tbJnsPerawatan.setDefaultRenderer(Object.class, new WarnaTable());

        Kode.setDocument(new batasInput((byte)5).getKata(Kode));
        Parameter.setDocument(new batasInput((byte)70).getKata(Parameter));
        Satuan.setDocument(new batasInput((byte)20).getKata(Satuan));
        JasaSarana.setDocument(new batasInput((int)15).getOnlyAngka(JasaSarana));
        PaketBHP.setDocument(new batasInput((int)15).getOnlyAngka(PaketBHP));
        JasaPJLab.setDocument(new batasInput((int)15).getOnlyAngka(JasaPJLab));
        JasaPetugas.setDocument(new batasInput((int)15).getOnlyAngka(JasaPetugas));
        KSO.setDocument(new batasInput((int)15).getOnlyAngka(KSO));
        Manajemen.setDocument(new batasInput((int)15).getOnlyAngka(Manajemen));
        TotalTarif.setDocument(new batasInput((int)20).getOnlyAngka(TotalTarif));

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
        Scroll = new widget.ScrollPane();
        tbJnsPerawatan = new widget.Table();
        jPanel3 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        BtnPrint = new widget.Button();
        BtnAll = new widget.Button();
        BtnKeluar = new widget.Button();
        panelGlass9 = new widget.panelisi();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();
        PanelInput = new javax.swing.JPanel();
        FormInput = new widget.PanelBiasa();
        jLabel3 = new widget.Label();
        Kode = new widget.TextBox();
        jLabel8 = new widget.Label();
        Parameter = new widget.TextBox();
        JasaSarana = new widget.TextBox();
        jLabel9 = new widget.Label();
        jLabel11 = new widget.Label();
        TotalTarif = new widget.TextBox();
        jLabel13 = new widget.Label();
        JasaPetugas = new widget.TextBox();
        jLabel10 = new widget.Label();
        JasaPJLab = new widget.TextBox();
        jLabel14 = new widget.Label();
        PaketBHP = new widget.TextBox();
        KSO = new widget.TextBox();
        Manajemen = new widget.TextBox();
        jLabel15 = new widget.Label();
        jLabel16 = new widget.Label();
        jLabel12 = new widget.Label();
        MetodePengujian = new widget.ComboBox();
        jLabel4 = new widget.Label();
        Satuan = new widget.TextBox();
        jLabel5 = new widget.Label();
        jLabel17 = new widget.Label();
        ChkInput = new widget.CekBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Tarif & Parameter Pengujian Laboratorium Kesehatan Lingkungan ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbJnsPerawatan.setAutoCreateRowSorter(true);
        tbJnsPerawatan.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbJnsPerawatan.setName("tbJnsPerawatan"); // NOI18N
        tbJnsPerawatan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbJnsPerawatanMouseClicked(evt);
            }
        });
        tbJnsPerawatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbJnsPerawatanKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbJnsPerawatanKeyReleased(evt);
            }
        });
        Scroll.setViewportView(tbJnsPerawatan);

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

        jPanel3.add(panelGlass8, java.awt.BorderLayout.CENTER);

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(450, 23));
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

        jPanel3.add(panelGlass9, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 155));
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(100, 197));
        FormInput.setLayout(null);

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel3.setText("Kode");
        jLabel3.setName("jLabel3"); // NOI18N
        FormInput.add(jLabel3);
        jLabel3.setBounds(15, 10, 45, 23);

        Kode.setHighlighter(null);
        Kode.setName("Kode"); // NOI18N
        Kode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodeKeyPressed(evt);
            }
        });
        FormInput.add(Kode);
        Kode.setBounds(50, 10, 70, 23);

        jLabel8.setText("Parameter :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(123, 10, 80, 23);

        Parameter.setHighlighter(null);
        Parameter.setName("Parameter"); // NOI18N
        Parameter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ParameterKeyPressed(evt);
            }
        });
        FormInput.add(Parameter);
        Parameter.setBounds(207, 10, 220, 23);

        JasaSarana.setText("0");
        JasaSarana.setHighlighter(null);
        JasaSarana.setName("JasaSarana"); // NOI18N
        JasaSarana.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JasaSaranaKeyPressed(evt);
            }
        });
        FormInput.add(JasaSarana);
        JasaSarana.setBounds(102, 70, 130, 23);

        jLabel9.setText("Jasa Sarana : Rp.");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(0, 70, 100, 23);

        jLabel11.setText("Total Tarif : Rp.");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(472, 70, 100, 23);

        TotalTarif.setText("0");
        TotalTarif.setHighlighter(null);
        TotalTarif.setName("TotalTarif"); // NOI18N
        TotalTarif.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TotalTarifKeyPressed(evt);
            }
        });
        FormInput.add(TotalTarif);
        TotalTarif.setBounds(574, 70, 130, 23);

        jLabel13.setText("Jasa Petugas : Rp.");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(241, 70, 100, 23);

        JasaPetugas.setText("0");
        JasaPetugas.setHighlighter(null);
        JasaPetugas.setName("JasaPetugas"); // NOI18N
        JasaPetugas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JasaPetugasKeyPressed(evt);
            }
        });
        FormInput.add(JasaPetugas);
        JasaPetugas.setBounds(342, 70, 130, 23);

        jLabel10.setText("Jasa P.J. Lab : Rp.");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(241, 40, 100, 23);

        JasaPJLab.setText("0");
        JasaPJLab.setHighlighter(null);
        JasaPJLab.setName("JasaPJLab"); // NOI18N
        JasaPJLab.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JasaPJLabKeyPressed(evt);
            }
        });
        FormInput.add(JasaPJLab);
        JasaPJLab.setBounds(342, 40, 130, 23);

        jLabel14.setText("Paket BHP : Rp.");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(0, 100, 100, 23);

        PaketBHP.setText("0");
        PaketBHP.setHighlighter(null);
        PaketBHP.setName("PaketBHP"); // NOI18N
        PaketBHP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PaketBHPKeyPressed(evt);
            }
        });
        FormInput.add(PaketBHP);
        PaketBHP.setBounds(102, 100, 130, 23);

        KSO.setText("0");
        KSO.setHighlighter(null);
        KSO.setName("KSO"); // NOI18N
        KSO.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KSOKeyPressed(evt);
            }
        });
        FormInput.add(KSO);
        KSO.setBounds(342, 100, 130, 23);

        Manajemen.setText("0");
        Manajemen.setHighlighter(null);
        Manajemen.setName("Manajemen"); // NOI18N
        Manajemen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ManajemenKeyPressed(evt);
            }
        });
        FormInput.add(Manajemen);
        Manajemen.setBounds(574, 40, 130, 23);

        jLabel15.setText("K.S.O : Rp.");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(241, 100, 100, 23);

        jLabel16.setText("Manajemen : Rp.");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput.add(jLabel16);
        jLabel16.setBounds(472, 40, 100, 23);

        jLabel12.setText("Metode Pengujian :");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(433, 10, 110, 23);

        MetodePengujian.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Spektrofotometri", "Elektrometri", "Gravimetri", "AAS", "Titrimetri", "Angka Lempeng Total", "Membran Filter", "Tabung Fermentasi", "Biakan & Identifikasi", "Singlepath" }));
        MetodePengujian.setName("MetodePengujian"); // NOI18N
        MetodePengujian.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MetodePengujianKeyPressed(evt);
            }
        });
        FormInput.add(MetodePengujian);
        MetodePengujian.setBounds(547, 10, 157, 23);

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel4.setText("Satuan");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(15, 40, 54, 23);

        Satuan.setHighlighter(null);
        Satuan.setName("Satuan"); // NOI18N
        Satuan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SatuanKeyPressed(evt);
            }
        });
        FormInput.add(Satuan);
        Satuan.setBounds(60, 40, 130, 23);

        jLabel5.setText(":");
        jLabel5.setName("jLabel5"); // NOI18N
        FormInput.add(jLabel5);
        jLabel5.setBounds(0, 10, 46, 23);

        jLabel17.setText(":");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(0, 40, 56, 23);

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

    private void KodeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodeKeyPressed
        Valid.pindah(evt,TCari,Parameter);
}//GEN-LAST:event_KodeKeyPressed

    private void ParameterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ParameterKeyPressed
        Valid.pindah(evt,Kode,MetodePengujian);
}//GEN-LAST:event_ParameterKeyPressed

    private void JasaSaranaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JasaSaranaKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isjml();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            isjml();
            PaketBHP.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){          
            isjml();
            Satuan.requestFocus();
        }
}//GEN-LAST:event_JasaSaranaKeyPressed

    private void TotalTarifKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TotalTarifKeyPressed
        Valid.pindah(evt,Manajemen,BtnSimpan);
}//GEN-LAST:event_TotalTarifKeyPressed

    private void JasaPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JasaPetugasKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isjml();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            isjml();
            KSO.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){          
            isjml();
            JasaPJLab.requestFocus();
        }
}//GEN-LAST:event_JasaPetugasKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(Kode.getText().trim().equals("")){
            Valid.textKosong(Kode,"Kode");
        }else if(Parameter.getText().trim().equals("")){
            Valid.textKosong(Parameter,"Parameter");
        }else if(Satuan.getText().trim().equals("")){
            Valid.textKosong(Satuan,"Satuan");
        }else if(JasaSarana.getText().trim().equals("")){
            Valid.textKosong(JasaSarana,"Jasa Sarana");
        }else if(PaketBHP.getText().trim().equals("")){
            Valid.textKosong(PaketBHP,"Paket BHP");
        }else if(JasaPJLab.getText().trim().equals("")){
            Valid.textKosong(JasaPJLab,"Jasa P.J. Lab");
        }else if(JasaPetugas.getText().trim().equals("")){
            Valid.textKosong(JasaPetugas,"Jasa Petugas");
        }else if(KSO.getText().trim().equals("")){
            Valid.textKosong(KSO,"K.S.O");
        }else if(Manajemen.getText().trim().equals("")){
            Valid.textKosong(Manajemen,"Manajemen");
        }else if(TotalTarif.getText().trim().equals("")){
            Valid.textKosong(TotalTarif,"Total Tarif");
        }else{
            if(Sequel.menyimpantf("laborat_kesling_parameter_pengujian","?,?,?,?,?,?,?,?,?,?,?","Kode Periksa",11,new String[]{
                Kode.getText(),Parameter.getText(),MetodePengujian.getSelectedItem().toString(),Satuan.getText(),JasaSarana.getText(),
                PaketBHP.getText(),JasaPJLab.getText(),JasaPetugas.getText(),KSO.getText(),Manajemen.getText(),TotalTarif.getText()
            })==true){
                tabMode.addRow(new Object[]{
                    Kode.getText(),Parameter.getText(),MetodePengujian.getSelectedItem().toString(),Satuan.getText(),JasaSarana.getText(),
                    PaketBHP.getText(),JasaPJLab.getText(),JasaPetugas.getText(),KSO.getText(),Manajemen.getText(),TotalTarif.getText()
                });
                LCount.setText(""+tabMode.getRowCount());
                emptTeks();
            }                
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,TotalTarif,BtnBatal);
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
        if(Valid.hapusTabletf(tabMode,Kode,"laborat_kesling_parameter_pengujian","kode_parameter")==true){
            if(tbJnsPerawatan.getSelectedRow()!= -1){
                tabMode.removeRow(tbJnsPerawatan.getSelectedRow());
                LCount.setText(""+tabMode.getRowCount());
                emptTeks();
            }
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
        if(Kode.getText().trim().equals("")){
            Valid.textKosong(Kode,"Kode");
        }else if(Parameter.getText().trim().equals("")){
            Valid.textKosong(Parameter,"Parameter");
        }else if(Satuan.getText().trim().equals("")){
            Valid.textKosong(Satuan,"Satuan");
        }else if(JasaSarana.getText().trim().equals("")){
            Valid.textKosong(JasaSarana,"Jasa Sarana");
        }else if(PaketBHP.getText().trim().equals("")){
            Valid.textKosong(PaketBHP,"Paket BHP");
        }else if(JasaPJLab.getText().trim().equals("")){
            Valid.textKosong(JasaPJLab,"Jasa P.J. Lab");
        }else if(JasaPetugas.getText().trim().equals("")){
            Valid.textKosong(JasaPetugas,"Jasa Petugas");
        }else if(KSO.getText().trim().equals("")){
            Valid.textKosong(KSO,"K.S.O");
        }else if(Manajemen.getText().trim().equals("")){
            Valid.textKosong(Manajemen,"Manajemen");
        }else if(TotalTarif.getText().trim().equals("")){
            Valid.textKosong(TotalTarif,"Total Tarif");
        }else{
            if(Sequel.mengedittf("laborat_kesling_parameter_pengujian","kode_parameter=?","kode_parameter=?,nama_parameter=?,metode_pengujian=?,satuan=?,"+
                "jasa_sarana=?,paket_bhp=?,jasa_pj_lab=?,jasa_petugas=?,kso=?,jasa_menejemen=?,total=?",12,new String[]{
                Kode.getText(),Parameter.getText(),MetodePengujian.getSelectedItem().toString(),Satuan.getText(),JasaSarana.getText(),
                PaketBHP.getText(),JasaPJLab.getText(),JasaPetugas.getText(),KSO.getText(),Manajemen.getText(),TotalTarif.getText(),
                tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),1).toString()
            })==true){
                tbJnsPerawatan.setValueAt(Kode.getText(),tbJnsPerawatan.getSelectedRow(),0);
                tbJnsPerawatan.setValueAt(Parameter.getText(),tbJnsPerawatan.getSelectedRow(),1);
                tbJnsPerawatan.setValueAt(MetodePengujian.getSelectedItem().toString(),tbJnsPerawatan.getSelectedRow(),2);
                tbJnsPerawatan.setValueAt(Satuan.getText(),tbJnsPerawatan.getSelectedRow(),3);
                tbJnsPerawatan.setValueAt(JasaSarana.getText(),tbJnsPerawatan.getSelectedRow(),4);
                tbJnsPerawatan.setValueAt(PaketBHP.getText(),tbJnsPerawatan.getSelectedRow(),5);
                tbJnsPerawatan.setValueAt(JasaPJLab.getText(),tbJnsPerawatan.getSelectedRow(),6);
                tbJnsPerawatan.setValueAt(JasaPetugas.getText(),tbJnsPerawatan.getSelectedRow(),7);
                tbJnsPerawatan.setValueAt(KSO.getText(),tbJnsPerawatan.getSelectedRow(),8);
                tbJnsPerawatan.setValueAt(Manajemen.getText(),tbJnsPerawatan.getSelectedRow(),9);
                tbJnsPerawatan.setValueAt(TotalTarif.getText(),tbJnsPerawatan.getSelectedRow(),10);
                emptTeks();
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

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnAll,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(! TCari.getText().trim().equals("")){
            BtnCariActionPerformed(evt);
        }
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
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptTarifUtd.jasper","report","::[ Data Tarif Radiologi ]::","select laborat_kesling_parameter_pengujian.kd_jenis_prw,laborat_kesling_parameter_pengujian.nm_perawatan,laborat_kesling_parameter_pengujian.bagian_rs,"+
                    "laborat_kesling_parameter_pengujian.bhp,laborat_kesling_parameter_pengujian.tarif_perujuk,laborat_kesling_parameter_pengujian.tarif_tindakan_dokter,laborat_kesling_parameter_pengujian.tarif_tindakan_petugas,laborat_kesling_parameter_pengujian.kso,"+
                    "laborat_kesling_parameter_pengujian.manajemen,laborat_kesling_parameter_pengujian.total_byr,penjab.png_jawab "+
                    "from laborat_kesling_parameter_pengujian inner join penjab on penjab.kd_pj=laborat_kesling_parameter_pengujian.kd_pj where "+
                    " laborat_kesling_parameter_pengujian.status='1' and laborat_kesling_parameter_pengujian.kd_jenis_prw like '%"+TCari.getText().trim()+"%' or "+
                    " laborat_kesling_parameter_pengujian.status='1' and laborat_kesling_parameter_pengujian.nm_perawatan like '%"+TCari.getText().trim()+"%' or "+
                    " laborat_kesling_parameter_pengujian.status='1' and penjab.png_jawab like '%"+TCari.getText().trim()+"%' "+
                    "order by laborat_kesling_parameter_pengujian.kd_jenis_prw",param);   
        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnEdit, BtnAll);
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
            BtnCariActionPerformed(null);
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
            tampil();
            TCari.setText("");
        }else{
            Valid.pindah(evt, BtnPrint,BtnKeluar);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void tbJnsPerawatanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbJnsPerawatanMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbJnsPerawatanMouseClicked

    private void tbJnsPerawatanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbJnsPerawatanKeyPressed
        if(tabMode.getRowCount()!=0){
            if(evt.getKeyCode()==KeyEvent.VK_A){                
                for(i=0;i<tbJnsPerawatan.getRowCount();i++){ 
                    tbJnsPerawatan.setValueAt(true,i,0);
                }
            }             
        }
}//GEN-LAST:event_tbJnsPerawatanKeyPressed

private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
  isForm();                
}//GEN-LAST:event_ChkInputActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void JasaPJLabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JasaPJLabKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isjml();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            isjml();
            JasaPetugas.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){          
            isjml();
            PaketBHP.requestFocus();
        }
    }//GEN-LAST:event_JasaPJLabKeyPressed

    private void PaketBHPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PaketBHPKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isjml();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            isjml();
            JasaPJLab.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){          
            isjml();
            JasaSarana.requestFocus();
        }
    }//GEN-LAST:event_PaketBHPKeyPressed

    private void KSOKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KSOKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isjml();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            isjml();
            Manajemen.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){          
            isjml();
            JasaPetugas.requestFocus();
        }
    }//GEN-LAST:event_KSOKeyPressed

    private void ManajemenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ManajemenKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isjml();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            isjml();
            TotalTarif.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){          
            isjml();
            KSO.requestFocus();
        }
    }//GEN-LAST:event_ManajemenKeyPressed

    private void tbJnsPerawatanKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbJnsPerawatanKeyReleased
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }           
        }
    }//GEN-LAST:event_tbJnsPerawatanKeyReleased

    private void MetodePengujianKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MetodePengujianKeyPressed
        Valid.pindah(evt,Parameter,Satuan);
    }//GEN-LAST:event_MetodePengujianKeyPressed

    private void SatuanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SatuanKeyPressed
        Valid.pindah(evt,MetodePengujian,JasaSarana);
    }//GEN-LAST:event_SatuanKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            LabKeslingParameterPengujian dialog = new LabKeslingParameterPengujian(new javax.swing.JFrame(), true);
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
    private widget.TextBox JasaPJLab;
    private widget.TextBox JasaPetugas;
    private widget.TextBox JasaSarana;
    private widget.TextBox KSO;
    private widget.TextBox Kode;
    private widget.Label LCount;
    private widget.TextBox Manajemen;
    private widget.ComboBox MetodePengujian;
    private widget.TextBox PaketBHP;
    private javax.swing.JPanel PanelInput;
    private widget.TextBox Parameter;
    private widget.TextBox Satuan;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.TextBox TotalTarif;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel12;
    private widget.Label jLabel13;
    private widget.Label jLabel14;
    private widget.Label jLabel15;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
    private widget.Label jLabel3;
    private widget.Label jLabel4;
    private widget.Label jLabel5;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private javax.swing.JPanel jPanel3;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.Table tbJnsPerawatan;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            ps=koneksi.prepareStatement(
                        "select laborat_kesling_parameter_pengujian.kode_parameter,laborat_kesling_parameter_pengujian.nama_parameter,laborat_kesling_parameter_pengujian.metode_pengujian,laborat_kesling_parameter_pengujian.satuan,"+
                        "laborat_kesling_parameter_pengujian.jasa_sarana,laborat_kesling_parameter_pengujian.paket_bhp,laborat_kesling_parameter_pengujian.jasa_pj_lab,laborat_kesling_parameter_pengujian.jasa_petugas,"+
                        "laborat_kesling_parameter_pengujian.kso,laborat_kesling_parameter_pengujian.jasa_menejemen,laborat_kesling_parameter_pengujian.total from laborat_kesling_parameter_pengujian "+
                        (TCari.getText().trim().equals("")?"":"where laborat_kesling_parameter_pengujian.kode_parameter like ? or laborat_kesling_parameter_pengujian.nama_parameter like ? "+
                        "or laborat_kesling_parameter_pengujian.metode_pengujian like ? ")+"order by laborat_kesling_parameter_pengujian.kode_parameter");
            try {    
                if(!TCari.getText().trim().equals("")){
                    ps.setString(1,"%"+TCari.getText().trim()+"%");
                    ps.setString(2,"%"+TCari.getText().trim()+"%");
                    ps.setString(3,"%"+TCari.getText().trim()+"%");
                }
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new Object[]{
                        rs.getString("kode_parameter"),rs.getString("nama_parameter"),rs.getString("metode_pengujian"),rs.getString("satuan"),rs.getString("jasa_sarana"),
                        rs.getString("paket_bhp"),rs.getString("jasa_pj_lab"),rs.getString("jasa_petugas"),rs.getString("kso"),rs.getString("jasa_menejemen"),rs.getString("total")
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
        Kode.setText("");
        Parameter.setText("");
        Satuan.setText("");
        MetodePengujian.setSelectedIndex(0);
        JasaSarana.setText("0");
        PaketBHP.setText("0");
        JasaPJLab.setText("0");
        JasaPetugas.setText("0");
        KSO.setText("0");        
        Manajemen.setText("0");
        TotalTarif.setText("0");
        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(laborat_kesling_parameter_pengujian.kode_parameter,3),signed)),0) from laborat_kesling_parameter_pengujian","",3,Kode);
        Kode.requestFocus();
    }

    private void getData() {
        if(tbJnsPerawatan.getSelectedRow()!= -1){
            Kode.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),0).toString());
            Parameter.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),1).toString());
            MetodePengujian.setSelectedItem(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),2).toString());
            Satuan.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),3).toString());
            JasaSarana.setText(Valid.SetAngka2(Double.parseDouble(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),4).toString())));
            PaketBHP.setText(Valid.SetAngka2(Double.parseDouble(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),5).toString())));
            JasaPJLab.setText(Valid.SetAngka2(Double.parseDouble(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),6).toString())));
            JasaPetugas.setText(Valid.SetAngka2(Double.parseDouble(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),7).toString())));
            KSO.setText(Valid.SetAngka2(Double.parseDouble(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),8).toString())));
            Manajemen.setText(Valid.SetAngka2(Double.parseDouble(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),9).toString())));
            TotalTarif.setText(Valid.SetAngka2(Double.parseDouble(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),10).toString())));
        }
    }

    private void isjml(){
        if((! JasaSarana.getText().equals(""))&&(! PaketBHP.getText().equals(""))&&(! JasaPJLab.getText().equals(""))&&(! JasaPetugas.getText().equals(""))&&(! KSO.getText().equals(""))&&(! Manajemen.getText().equals(""))){
            TotalTarif.setText(
                Valid.SetAngka2(
                    Double.parseDouble(JasaSarana.getText().trim())+Double.parseDouble(PaketBHP.getText().trim())+Double.parseDouble(JasaPJLab.getText().trim())+Double.parseDouble(JasaPetugas.getText().trim())+Double.parseDouble(KSO.getText().trim())+Double.parseDouble(Manajemen.getText().trim())
                )
            );
        }
    }
    
    public JTextField getTextField(){
        return Kode;
    }
   
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,155));
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
        BtnSimpan.setEnabled(akses.getparameter_pengujian_lab_kesehatan_lingkungan());
        BtnHapus.setEnabled(akses.getparameter_pengujian_lab_kesehatan_lingkungan());
        BtnEdit.setEnabled(akses.getparameter_pengujian_lab_kesehatan_lingkungan());
        BtnPrint.setEnabled(akses.getparameter_pengujian_lab_kesehatan_lingkungan());
    }
    
    public JTable getTable(){
        return tbJnsPerawatan;
    }
}

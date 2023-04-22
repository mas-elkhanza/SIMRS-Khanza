package rekammedis;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class MasterTemplatePemeriksaanDokter extends javax.swing.JDialog {
    private final DefaultTableModel tabMode,tabModeDiagnosa,tabModeProsedur;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement ps,psprosedur,pspenyakit;
    private ResultSet rs;
    private int i,index=0,jml=0;
    private String[] kode,nama,ciripny,keterangan,kategori,cirium,kode2,panjang,pendek;
    private boolean[] pilih;

    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public MasterTemplatePemeriksaanDokter(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        Object[] row={"No.Template","Nama Pemeriksaan","Template Hasil Radiologi"};
        tabMode=new DefaultTableModel(null,row){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbDokter.setModel(tabMode);

        tbDokter.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 3; i++) {
            TableColumn column = tbDokter.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(100);
            }else if(i==1){
                column.setPreferredWidth(200);
            }else if(i==2){
                column.setPreferredWidth(500);
            }
        }
        tbDokter.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeDiagnosa=new DefaultTableModel(null,new Object[]{
            "P","Kode","Nama Penyakit","Ciri-ciri Penyakit","Keterangan","Ktg.Penyakit","Ciri-ciri Umum"}){
            @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbDiagnosa.setModel(tabModeDiagnosa);
        //tbPenyakit.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbPenyakit.getBackground()));
        tbDiagnosa.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbDiagnosa.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (i= 0; i < 7; i++) {
            TableColumn column = tbDiagnosa.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(40);
            }else if(i==2){
                column.setPreferredWidth(280);
            }else if(i==3){
                column.setPreferredWidth(285);
            }else if(i==4){
                column.setPreferredWidth(75);
            }else if(i==5){
                column.setPreferredWidth(75);
            }else if(i==6){
                column.setPreferredWidth(75);
            }
        }
        tbDiagnosa.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeProsedur=new DefaultTableModel(null,new Object[]{
            "P","Kode","Deskripsi Panjang","Deskripsi Pendek"}){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbProsedur.setModel(tabModeProsedur);
        //tbPenyakit.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbPenyakit.getBackground()));
        tbProsedur.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbProsedur.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 4; i++) {
            TableColumn column = tbProsedur.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(50);
            }else if(i==2){
                column.setPreferredWidth(350);
            }else if(i==3){
                column.setPreferredWidth(350);
            }
        }
        tbProsedur.setDefaultRenderer(Object.class, new WarnaTable());

        Kd.setDocument(new batasInput((byte)5).getKata(Kd));
        //Nm.setDocument(new batasInput((byte)50).getKata(Nm));      
        Subjek.setDocument(new batasInput((int)5000).getKata(Subjek));  
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
        scrollInput = new widget.ScrollPane();
        FormInput = new widget.PanelBiasa();
        label12 = new widget.Label();
        Kd = new widget.TextBox();
        jLabel40 = new widget.Label();
        scrollPane2 = new widget.ScrollPane();
        Subjek = new widget.TextArea();
        label14 = new widget.Label();
        KdDokter = new widget.TextBox();
        NmDokter = new widget.TextBox();
        BtnDokter = new widget.Button();
        jLabel41 = new widget.Label();
        scrollPane3 = new widget.ScrollPane();
        Objek = new widget.TextArea();
        jLabel42 = new widget.Label();
        scrollPane4 = new widget.ScrollPane();
        Template2 = new widget.TextArea();
        jLabel43 = new widget.Label();
        scrollPane5 = new widget.ScrollPane();
        Template3 = new widget.TextArea();
        jLabel44 = new widget.Label();
        scrollPane6 = new widget.ScrollPane();
        Template4 = new widget.TextArea();
        jLabel45 = new widget.Label();
        scrollPane7 = new widget.ScrollPane();
        Template5 = new widget.TextArea();
        Scroll1 = new widget.ScrollPane();
        tbDiagnosa = new widget.Table();
        BtnCariPenyakit = new widget.Button();
        Diagnosa = new widget.TextBox();
        jLabel13 = new widget.Label();
        jLabel14 = new widget.Label();
        Prosedur = new widget.TextBox();
        BtnCariPenyakit1 = new widget.Button();
        Scroll2 = new widget.ScrollPane();
        tbProsedur = new widget.Table();
        BtnCariPenyakit2 = new widget.Button();
        Scroll3 = new widget.ScrollPane();
        tbDiagnosa2 = new widget.Table();
        Diagnosa2 = new widget.TextBox();
        jLabel15 = new widget.Label();
        jLabel16 = new widget.Label();
        Diagnosa3 = new widget.TextBox();
        BtnCariPenyakit3 = new widget.Button();
        Scroll4 = new widget.ScrollPane();
        tbDiagnosa3 = new widget.Table();
        Scroll5 = new widget.ScrollPane();
        tbDiagnosa4 = new widget.Table();
        Diagnosa4 = new widget.TextBox();
        BtnCariPenyakit4 = new widget.Button();
        jLabel17 = new widget.Label();
        Diagnosa5 = new widget.TextBox();
        BtnCariPenyakit5 = new widget.Button();
        Scroll6 = new widget.ScrollPane();
        tbDiagnosa5 = new widget.Table();
        jLabel18 = new widget.Label();
        Diagnosa6 = new widget.TextBox();
        BtnCariPenyakit6 = new widget.Button();
        Scroll7 = new widget.ScrollPane();
        tbDiagnosa6 = new widget.Table();
        Diagnosa7 = new widget.TextBox();
        BtnCariPenyakit7 = new widget.Button();
        Scroll8 = new widget.ScrollPane();
        tbDiagnosa7 = new widget.Table();
        jLabel19 = new widget.Label();
        BtnCariPenyakit8 = new widget.Button();
        Diagnosa8 = new widget.TextBox();
        Scroll9 = new widget.ScrollPane();
        tbDiagnosa8 = new widget.Table();
        jLabel20 = new widget.Label();
        Scroll10 = new widget.ScrollPane();
        tbDiagnosa9 = new widget.Table();
        Diagnosa10 = new widget.TextBox();
        BtnCariPenyakit10 = new widget.Button();
        Scroll11 = new widget.ScrollPane();
        tbDiagnosa10 = new widget.Table();
        internalFrame3 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbDokter = new widget.Table();
        panelGlass9 = new widget.panelisi();
        label9 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        label10 = new widget.Label();
        LCount = new widget.Label();
        BtnKeluar = new widget.Button();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Master Template Pemeriksaan Dokter ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        TabRawat.setBackground(new java.awt.Color(254, 255, 254));
        TabRawat.setForeground(new java.awt.Color(50, 50, 50));
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

        scrollInput.setName("scrollInput"); // NOI18N
        scrollInput.setPreferredSize(new java.awt.Dimension(102, 557));

        FormInput.setBackground(new java.awt.Color(255, 255, 255));
        FormInput.setBorder(null);
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(700, 2483));
        FormInput.setLayout(null);

        label12.setText("No.Template :");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(75, 23));
        FormInput.add(label12);
        label12.setBounds(0, 10, 85, 23);

        Kd.setName("Kd"); // NOI18N
        Kd.setPreferredSize(new java.awt.Dimension(207, 23));
        Kd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdKeyPressed(evt);
            }
        });
        FormInput.add(Kd);
        Kd.setBounds(89, 10, 120, 23);

        jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel40.setText("Subjek :");
        jLabel40.setName("jLabel40"); // NOI18N
        FormInput.add(jLabel40);
        jLabel40.setBounds(16, 40, 410, 20);

        scrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane2.setName("scrollPane2"); // NOI18N

        Subjek.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Subjek.setColumns(20);
        Subjek.setRows(7);
        Subjek.setName("Subjek"); // NOI18N
        Subjek.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SubjekKeyPressed(evt);
            }
        });
        scrollPane2.setViewportView(Subjek);

        FormInput.add(scrollPane2);
        scrollPane2.setBounds(16, 60, 670, 73);

        label14.setText("Dokter :");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label14);
        label14.setBounds(230, 10, 70, 23);

        KdDokter.setEditable(false);
        KdDokter.setName("KdDokter"); // NOI18N
        KdDokter.setPreferredSize(new java.awt.Dimension(80, 23));
        KdDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdDokterKeyPressed(evt);
            }
        });
        FormInput.add(KdDokter);
        KdDokter.setBounds(304, 10, 110, 23);

        NmDokter.setEditable(false);
        NmDokter.setName("NmDokter"); // NOI18N
        NmDokter.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmDokter);
        NmDokter.setBounds(416, 10, 240, 23);

        BtnDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter.setMnemonic('2');
        BtnDokter.setToolTipText("Alt+2");
        BtnDokter.setName("BtnDokter"); // NOI18N
        BtnDokter.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokterActionPerformed(evt);
            }
        });
        BtnDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnDokterKeyPressed(evt);
            }
        });
        FormInput.add(BtnDokter);
        BtnDokter.setBounds(658, 10, 28, 23);

        jLabel41.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel41.setText("Objek :");
        jLabel41.setName("jLabel41"); // NOI18N
        FormInput.add(jLabel41);
        jLabel41.setBounds(16, 140, 410, 20);

        scrollPane3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane3.setName("scrollPane3"); // NOI18N

        Objek.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Objek.setColumns(20);
        Objek.setRows(7);
        Objek.setName("Objek"); // NOI18N
        Objek.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ObjekKeyPressed(evt);
            }
        });
        scrollPane3.setViewportView(Objek);

        FormInput.add(scrollPane3);
        scrollPane3.setBounds(16, 160, 670, 73);

        jLabel42.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel42.setText("Asesmen :");
        jLabel42.setName("jLabel42"); // NOI18N
        FormInput.add(jLabel42);
        jLabel42.setBounds(16, 240, 410, 20);

        scrollPane4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane4.setName("scrollPane4"); // NOI18N

        Template2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Template2.setColumns(20);
        Template2.setRows(7);
        Template2.setName("Template2"); // NOI18N
        Template2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Template2KeyPressed(evt);
            }
        });
        scrollPane4.setViewportView(Template2);

        FormInput.add(scrollPane4);
        scrollPane4.setBounds(16, 260, 670, 73);

        jLabel43.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel43.setText("Plan :");
        jLabel43.setName("jLabel43"); // NOI18N
        FormInput.add(jLabel43);
        jLabel43.setBounds(16, 340, 410, 20);

        scrollPane5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane5.setName("scrollPane5"); // NOI18N

        Template3.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Template3.setColumns(20);
        Template3.setRows(7);
        Template3.setName("Template3"); // NOI18N
        Template3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Template3KeyPressed(evt);
            }
        });
        scrollPane5.setViewportView(Template3);

        FormInput.add(scrollPane5);
        scrollPane5.setBounds(16, 360, 670, 73);

        jLabel44.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel44.setText("Instruksi :");
        jLabel44.setName("jLabel44"); // NOI18N
        FormInput.add(jLabel44);
        jLabel44.setBounds(16, 440, 410, 20);

        scrollPane6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane6.setName("scrollPane6"); // NOI18N

        Template4.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Template4.setColumns(20);
        Template4.setRows(7);
        Template4.setName("Template4"); // NOI18N
        Template4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Template4KeyPressed(evt);
            }
        });
        scrollPane6.setViewportView(Template4);

        FormInput.add(scrollPane6);
        scrollPane6.setBounds(16, 460, 670, 73);

        jLabel45.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel45.setText("Evaluasi :");
        jLabel45.setName("jLabel45"); // NOI18N
        FormInput.add(jLabel45);
        jLabel45.setBounds(16, 540, 410, 20);

        scrollPane7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane7.setName("scrollPane7"); // NOI18N

        Template5.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Template5.setColumns(20);
        Template5.setRows(7);
        Template5.setName("Template5"); // NOI18N
        Template5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Template5KeyPressed(evt);
            }
        });
        scrollPane7.setViewportView(Template5);

        FormInput.add(scrollPane7);
        scrollPane7.setBounds(16, 560, 670, 73);

        Scroll1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)));
        Scroll1.setName("Scroll1"); // NOI18N
        Scroll1.setOpaque(true);

        tbDiagnosa.setName("tbDiagnosa"); // NOI18N
        Scroll1.setViewportView(tbDiagnosa);

        FormInput.add(Scroll1);
        Scroll1.setBounds(16, 687, 670, 116);

        BtnCariPenyakit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCariPenyakit.setMnemonic('1');
        BtnCariPenyakit.setToolTipText("Alt+1");
        BtnCariPenyakit.setName("BtnCariPenyakit"); // NOI18N
        BtnCariPenyakit.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCariPenyakit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariPenyakitActionPerformed(evt);
            }
        });
        FormInput.add(BtnCariPenyakit);
        BtnCariPenyakit.setBounds(658, 660, 28, 23);

        Diagnosa.setHighlighter(null);
        Diagnosa.setName("Diagnosa"); // NOI18N
        Diagnosa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosaKeyPressed(evt);
            }
        });
        FormInput.add(Diagnosa);
        Diagnosa.setBounds(16, 660, 640, 23);

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel13.setText("Diagnosa :");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(16, 640, 68, 23);

        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel14.setText("Prosedur :");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(16, 810, 68, 23);

        Prosedur.setHighlighter(null);
        Prosedur.setName("Prosedur"); // NOI18N
        Prosedur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ProsedurKeyPressed(evt);
            }
        });
        FormInput.add(Prosedur);
        Prosedur.setBounds(16, 830, 640, 23);

        BtnCariPenyakit1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCariPenyakit1.setMnemonic('1');
        BtnCariPenyakit1.setToolTipText("Alt+1");
        BtnCariPenyakit1.setName("BtnCariPenyakit1"); // NOI18N
        BtnCariPenyakit1.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCariPenyakit1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariPenyakit1ActionPerformed(evt);
            }
        });
        FormInput.add(BtnCariPenyakit1);
        BtnCariPenyakit1.setBounds(658, 830, 28, 23);

        Scroll2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)));
        Scroll2.setName("Scroll2"); // NOI18N
        Scroll2.setOpaque(true);

        tbProsedur.setName("tbProsedur"); // NOI18N
        Scroll2.setViewportView(tbProsedur);

        FormInput.add(Scroll2);
        Scroll2.setBounds(16, 857, 670, 116);

        BtnCariPenyakit2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCariPenyakit2.setMnemonic('1');
        BtnCariPenyakit2.setToolTipText("Alt+1");
        BtnCariPenyakit2.setName("BtnCariPenyakit2"); // NOI18N
        BtnCariPenyakit2.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCariPenyakit2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariPenyakit2ActionPerformed(evt);
            }
        });
        FormInput.add(BtnCariPenyakit2);
        BtnCariPenyakit2.setBounds(658, 1000, 28, 23);

        Scroll3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)));
        Scroll3.setName("Scroll3"); // NOI18N
        Scroll3.setOpaque(true);

        tbDiagnosa2.setName("tbDiagnosa2"); // NOI18N
        Scroll3.setViewportView(tbDiagnosa2);

        FormInput.add(Scroll3);
        Scroll3.setBounds(16, 1027, 670, 116);

        Diagnosa2.setHighlighter(null);
        Diagnosa2.setName("Diagnosa2"); // NOI18N
        Diagnosa2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Diagnosa2KeyPressed(evt);
            }
        });
        FormInput.add(Diagnosa2);
        Diagnosa2.setBounds(16, 1000, 640, 23);

        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel15.setText("Permintaan Radiologi :");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(16, 980, 120, 23);

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel16.setText("Permintaan Laborat Patologi Klinis :");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput.add(jLabel16);
        jLabel16.setBounds(20, 1160, 190, 23);

        Diagnosa3.setHighlighter(null);
        Diagnosa3.setName("Diagnosa3"); // NOI18N
        Diagnosa3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Diagnosa3KeyPressed(evt);
            }
        });
        FormInput.add(Diagnosa3);
        Diagnosa3.setBounds(20, 1180, 640, 23);

        BtnCariPenyakit3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCariPenyakit3.setMnemonic('1');
        BtnCariPenyakit3.setToolTipText("Alt+1");
        BtnCariPenyakit3.setName("BtnCariPenyakit3"); // NOI18N
        BtnCariPenyakit3.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCariPenyakit3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariPenyakit3ActionPerformed(evt);
            }
        });
        FormInput.add(BtnCariPenyakit3);
        BtnCariPenyakit3.setBounds(660, 1180, 28, 23);

        Scroll4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)));
        Scroll4.setName("Scroll4"); // NOI18N
        Scroll4.setOpaque(true);

        tbDiagnosa3.setName("tbDiagnosa3"); // NOI18N
        Scroll4.setViewportView(tbDiagnosa3);

        FormInput.add(Scroll4);
        Scroll4.setBounds(20, 1210, 670, 76);

        Scroll5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)));
        Scroll5.setName("Scroll5"); // NOI18N
        Scroll5.setOpaque(true);

        tbDiagnosa4.setName("tbDiagnosa4"); // NOI18N
        Scroll5.setViewportView(tbDiagnosa4);

        FormInput.add(Scroll5);
        Scroll5.setBounds(20, 1320, 670, 186);

        Diagnosa4.setHighlighter(null);
        Diagnosa4.setName("Diagnosa4"); // NOI18N
        Diagnosa4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Diagnosa4KeyPressed(evt);
            }
        });
        FormInput.add(Diagnosa4);
        Diagnosa4.setBounds(20, 1290, 640, 23);

        BtnCariPenyakit4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCariPenyakit4.setMnemonic('1');
        BtnCariPenyakit4.setToolTipText("Alt+1");
        BtnCariPenyakit4.setName("BtnCariPenyakit4"); // NOI18N
        BtnCariPenyakit4.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCariPenyakit4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariPenyakit4ActionPerformed(evt);
            }
        });
        FormInput.add(BtnCariPenyakit4);
        BtnCariPenyakit4.setBounds(660, 1290, 28, 23);

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel17.setText("Permintaan Laborat Patologi Anatomi :");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(20, 1510, 250, 23);

        Diagnosa5.setHighlighter(null);
        Diagnosa5.setName("Diagnosa5"); // NOI18N
        Diagnosa5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Diagnosa5KeyPressed(evt);
            }
        });
        FormInput.add(Diagnosa5);
        Diagnosa5.setBounds(20, 1530, 640, 23);

        BtnCariPenyakit5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCariPenyakit5.setMnemonic('1');
        BtnCariPenyakit5.setToolTipText("Alt+1");
        BtnCariPenyakit5.setName("BtnCariPenyakit5"); // NOI18N
        BtnCariPenyakit5.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCariPenyakit5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariPenyakit5ActionPerformed(evt);
            }
        });
        FormInput.add(BtnCariPenyakit5);
        BtnCariPenyakit5.setBounds(660, 1530, 28, 23);

        Scroll6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)));
        Scroll6.setName("Scroll6"); // NOI18N
        Scroll6.setOpaque(true);

        tbDiagnosa5.setName("tbDiagnosa5"); // NOI18N
        Scroll6.setViewportView(tbDiagnosa5);

        FormInput.add(Scroll6);
        Scroll6.setBounds(20, 1560, 670, 76);

        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel18.setText("Permintaan Laborat Mikrobiologi & Bio Molekuler :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(20, 1650, 270, 23);

        Diagnosa6.setHighlighter(null);
        Diagnosa6.setName("Diagnosa6"); // NOI18N
        Diagnosa6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Diagnosa6KeyPressed(evt);
            }
        });
        FormInput.add(Diagnosa6);
        Diagnosa6.setBounds(20, 1670, 640, 23);

        BtnCariPenyakit6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCariPenyakit6.setMnemonic('1');
        BtnCariPenyakit6.setToolTipText("Alt+1");
        BtnCariPenyakit6.setName("BtnCariPenyakit6"); // NOI18N
        BtnCariPenyakit6.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCariPenyakit6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariPenyakit6ActionPerformed(evt);
            }
        });
        FormInput.add(BtnCariPenyakit6);
        BtnCariPenyakit6.setBounds(660, 1670, 28, 23);

        Scroll7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)));
        Scroll7.setName("Scroll7"); // NOI18N
        Scroll7.setOpaque(true);

        tbDiagnosa6.setName("tbDiagnosa6"); // NOI18N
        Scroll7.setViewportView(tbDiagnosa6);

        FormInput.add(Scroll7);
        Scroll7.setBounds(20, 1700, 670, 76);

        Diagnosa7.setHighlighter(null);
        Diagnosa7.setName("Diagnosa7"); // NOI18N
        Diagnosa7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Diagnosa7KeyPressed(evt);
            }
        });
        FormInput.add(Diagnosa7);
        Diagnosa7.setBounds(20, 1780, 640, 23);

        BtnCariPenyakit7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCariPenyakit7.setMnemonic('1');
        BtnCariPenyakit7.setToolTipText("Alt+1");
        BtnCariPenyakit7.setName("BtnCariPenyakit7"); // NOI18N
        BtnCariPenyakit7.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCariPenyakit7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariPenyakit7ActionPerformed(evt);
            }
        });
        FormInput.add(BtnCariPenyakit7);
        BtnCariPenyakit7.setBounds(660, 1780, 28, 23);

        Scroll8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)));
        Scroll8.setName("Scroll8"); // NOI18N
        Scroll8.setOpaque(true);

        tbDiagnosa7.setName("tbDiagnosa7"); // NOI18N
        Scroll8.setViewportView(tbDiagnosa7);

        FormInput.add(Scroll8);
        Scroll8.setBounds(20, 1810, 670, 106);

        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel19.setText("Obat Umum/Non Racikan :");
        jLabel19.setName("jLabel19"); // NOI18N
        FormInput.add(jLabel19);
        jLabel19.setBounds(20, 1930, 270, 23);

        BtnCariPenyakit8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCariPenyakit8.setMnemonic('1');
        BtnCariPenyakit8.setToolTipText("Alt+1");
        BtnCariPenyakit8.setName("BtnCariPenyakit8"); // NOI18N
        BtnCariPenyakit8.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCariPenyakit8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariPenyakit8ActionPerformed(evt);
            }
        });
        FormInput.add(BtnCariPenyakit8);
        BtnCariPenyakit8.setBounds(660, 1950, 28, 23);

        Diagnosa8.setHighlighter(null);
        Diagnosa8.setName("Diagnosa8"); // NOI18N
        Diagnosa8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Diagnosa8KeyPressed(evt);
            }
        });
        FormInput.add(Diagnosa8);
        Diagnosa8.setBounds(20, 1950, 640, 23);

        Scroll9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)));
        Scroll9.setName("Scroll9"); // NOI18N
        Scroll9.setOpaque(true);

        tbDiagnosa8.setName("tbDiagnosa8"); // NOI18N
        Scroll9.setViewportView(tbDiagnosa8);

        FormInput.add(Scroll9);
        Scroll9.setBounds(20, 1980, 670, 76);

        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel20.setText("Obat Racikan :");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(20, 2070, 270, 23);

        Scroll10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)));
        Scroll10.setName("Scroll10"); // NOI18N
        Scroll10.setOpaque(true);

        tbDiagnosa9.setName("tbDiagnosa9"); // NOI18N
        Scroll10.setViewportView(tbDiagnosa9);

        FormInput.add(Scroll10);
        Scroll10.setBounds(20, 2090, 670, 50);

        Diagnosa10.setHighlighter(null);
        Diagnosa10.setName("Diagnosa10"); // NOI18N
        Diagnosa10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Diagnosa10KeyPressed(evt);
            }
        });
        FormInput.add(Diagnosa10);
        Diagnosa10.setBounds(20, 2150, 640, 23);

        BtnCariPenyakit10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCariPenyakit10.setMnemonic('1');
        BtnCariPenyakit10.setToolTipText("Alt+1");
        BtnCariPenyakit10.setName("BtnCariPenyakit10"); // NOI18N
        BtnCariPenyakit10.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCariPenyakit10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariPenyakit10ActionPerformed(evt);
            }
        });
        FormInput.add(BtnCariPenyakit10);
        BtnCariPenyakit10.setBounds(660, 2150, 28, 23);

        Scroll11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)));
        Scroll11.setName("Scroll11"); // NOI18N
        Scroll11.setOpaque(true);

        tbDiagnosa10.setName("tbDiagnosa10"); // NOI18N
        Scroll11.setViewportView(tbDiagnosa10);

        FormInput.add(Scroll11);
        Scroll11.setBounds(20, 2180, 670, 106);

        scrollInput.setViewportView(FormInput);

        internalFrame2.add(scrollInput, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Input Template", internalFrame2);

        internalFrame3.setBorder(null);
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);
        Scroll.setPreferredSize(new java.awt.Dimension(452, 200));

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
        Scroll.setViewportView(tbDokter);

        internalFrame3.add(Scroll, java.awt.BorderLayout.CENTER);

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label9.setText("Key Word :");
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(label9);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(530, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass9.add(TCari);

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
        panelGlass9.add(BtnCari);

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
        panelGlass9.add(BtnAll);

        internalFrame3.add(panelGlass9, java.awt.BorderLayout.PAGE_END);

        TabRawat.addTab("Data Template", internalFrame3);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 54));
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

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

        label10.setText("Record :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass8.add(label10);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass8.add(LCount);

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

        internalFrame1.add(panelGlass8, java.awt.BorderLayout.PAGE_END);

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
            if((evt.getClickCount()==2)&&(tbDokter.getSelectedColumn()==0)){
                TabRawat.setSelectedIndex(0);
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
            }else if(evt.getKeyCode()==KeyEvent.VK_SPACE){
                try {
                    getData();
                    TabRawat.setSelectedIndex(0);
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
}//GEN-LAST:event_tbDokterKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        /*if(Nm.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Pilih dulu data yang akan Anda hapus dengan menklik data pada tabel...!!!");
            tbDokter.requestFocus();
        }else{
            Valid.hapusTable(tabMode,Kd,"template_hasil_radiologi","no_template");
            tampil();
            emptTeks();
        }*/
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnBatal, BtnEdit);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
        /*if(Kd.getText().trim().equals("")){
            Valid.textKosong(Kd,"No.Template");
        }else if(Nm.getText().trim().equals("")){
            Valid.textKosong(Nm,"Nama Pemeriksaan");
        }else if(Template.getText().trim().equals("")){
            Valid.textKosong(Template,"Template Hasil Radiologi");
        }else{
            Valid.editTable(tabMode,"template_hasil_radiologi","no_template","?","no_template=?,nama_pemeriksaan=?,template_hasil_radiologi=?",4,new String[]{
                Kd.getText(),Nm.getText(),Template.getText(),tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString()
            });
            if(tabMode.getRowCount()!=0){tampil();emptTeks();TabRawat.setSelectedIndex(1);}
            
        }*/
}//GEN-LAST:event_BtnEditActionPerformed

    private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnEditActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnKeluar);
        }
}//GEN-LAST:event_BtnEditKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        tampil();
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnCari, BtnKeluar);
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
        /*if(Kd.getText().trim().equals("")){
            Valid.textKosong(Kd,"No.Template");
        }else if(Nm.getText().trim().equals("")){
            Valid.textKosong(Nm,"Nama Pemeriksaan");
        }else if(Template.getText().trim().equals("")){
            Valid.textKosong(Template,"Template Hasil Radiologi");
        }else{
            if(Sequel.menyimpantf("template_hasil_radiologi","?,?,?","No.Template",3,new String[]{
                Kd.getText(),Nm.getText(),Template.getText() 
            })==true){
                tampil();
                emptTeks();
            }                
        }*/
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,Subjek,BtnBatal);
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
/*
private void KdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdKeyPressed
    Valid.pindah(evt,BtnCari,Nm);
}//GEN-LAST:event_TKdKeyPressed
*/

    private void KdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdKeyPressed
        //Valid.pindah(evt,TCari,Nm,TCari);
    }//GEN-LAST:event_KdKeyPressed

    private void SubjekKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SubjekKeyPressed
        //Valid.pindah2(evt,Nm,BtnSimpan);
    }//GEN-LAST:event_SubjekKeyPressed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if(TabRawat.getSelectedIndex()==1){
            tampil();
        }
    }//GEN-LAST:event_TabRawatMouseClicked

    private void KdDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdDokterKeyPressed

    }//GEN-LAST:event_KdDokterKeyPressed

    private void BtnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokterActionPerformed
        /*dokter.isCek();
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setAlwaysOnTop(false);
        dokter.setVisible(true);*/
    }//GEN-LAST:event_BtnDokterActionPerformed

    private void BtnDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokterKeyPressed
        //Valid.pindah(evt,Monitoring,BtnSimpan);
    }//GEN-LAST:event_BtnDokterKeyPressed

    private void ObjekKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ObjekKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_ObjekKeyPressed

    private void Template2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Template2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Template2KeyPressed

    private void Template3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Template3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Template3KeyPressed

    private void Template4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Template4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Template4KeyPressed

    private void Template5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Template5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Template5KeyPressed

    private void BtnCariPenyakitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariPenyakitActionPerformed
        tampildiagnosa();
    }//GEN-LAST:event_BtnCariPenyakitActionPerformed

    private void DiagnosaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosaKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            tampildiagnosa();
        }
    }//GEN-LAST:event_DiagnosaKeyPressed

    private void ProsedurKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ProsedurKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            tampilprosedure();
        }
    }//GEN-LAST:event_ProsedurKeyPressed

    private void BtnCariPenyakit1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariPenyakit1ActionPerformed
        tampilprosedure();
    }//GEN-LAST:event_BtnCariPenyakit1ActionPerformed

    private void BtnCariPenyakit2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariPenyakit2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnCariPenyakit2ActionPerformed

    private void Diagnosa2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Diagnosa2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Diagnosa2KeyPressed

    private void Diagnosa3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Diagnosa3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Diagnosa3KeyPressed

    private void BtnCariPenyakit3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariPenyakit3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnCariPenyakit3ActionPerformed

    private void Diagnosa4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Diagnosa4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Diagnosa4KeyPressed

    private void BtnCariPenyakit4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariPenyakit4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnCariPenyakit4ActionPerformed

    private void Diagnosa5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Diagnosa5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Diagnosa5KeyPressed

    private void BtnCariPenyakit5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariPenyakit5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnCariPenyakit5ActionPerformed

    private void Diagnosa6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Diagnosa6KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Diagnosa6KeyPressed

    private void BtnCariPenyakit6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariPenyakit6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnCariPenyakit6ActionPerformed

    private void Diagnosa7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Diagnosa7KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Diagnosa7KeyPressed

    private void BtnCariPenyakit7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariPenyakit7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnCariPenyakit7ActionPerformed

    private void BtnCariPenyakit8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariPenyakit8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnCariPenyakit8ActionPerformed

    private void Diagnosa8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Diagnosa8KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Diagnosa8KeyPressed

    private void Diagnosa10KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Diagnosa10KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Diagnosa10KeyPressed

    private void BtnCariPenyakit10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariPenyakit10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnCariPenyakit10ActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            MasterTemplatePemeriksaanDokter dialog = new MasterTemplatePemeriksaanDokter(new javax.swing.JFrame(), true);
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
    private widget.Button BtnCariPenyakit;
    private widget.Button BtnCariPenyakit1;
    private widget.Button BtnCariPenyakit10;
    private widget.Button BtnCariPenyakit2;
    private widget.Button BtnCariPenyakit3;
    private widget.Button BtnCariPenyakit4;
    private widget.Button BtnCariPenyakit5;
    private widget.Button BtnCariPenyakit6;
    private widget.Button BtnCariPenyakit7;
    private widget.Button BtnCariPenyakit8;
    private widget.Button BtnDokter;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnSimpan;
    public widget.TextBox Diagnosa;
    public widget.TextBox Diagnosa10;
    public widget.TextBox Diagnosa2;
    public widget.TextBox Diagnosa3;
    public widget.TextBox Diagnosa4;
    public widget.TextBox Diagnosa5;
    public widget.TextBox Diagnosa6;
    public widget.TextBox Diagnosa7;
    public widget.TextBox Diagnosa8;
    private widget.PanelBiasa FormInput;
    private widget.TextBox Kd;
    private widget.TextBox KdDokter;
    private widget.Label LCount;
    private widget.TextBox NmDokter;
    private widget.TextArea Objek;
    public widget.TextBox Prosedur;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll1;
    private widget.ScrollPane Scroll10;
    private widget.ScrollPane Scroll11;
    private widget.ScrollPane Scroll2;
    private widget.ScrollPane Scroll3;
    private widget.ScrollPane Scroll4;
    private widget.ScrollPane Scroll5;
    private widget.ScrollPane Scroll6;
    private widget.ScrollPane Scroll7;
    private widget.ScrollPane Scroll8;
    private widget.ScrollPane Scroll9;
    private widget.TextArea Subjek;
    private widget.TextBox TCari;
    private javax.swing.JTabbedPane TabRawat;
    private widget.TextArea Template2;
    private widget.TextArea Template3;
    private widget.TextArea Template4;
    private widget.TextArea Template5;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.Label jLabel13;
    private widget.Label jLabel14;
    private widget.Label jLabel15;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
    private widget.Label jLabel18;
    private widget.Label jLabel19;
    private widget.Label jLabel20;
    private widget.Label jLabel40;
    private widget.Label jLabel41;
    private widget.Label jLabel42;
    private widget.Label jLabel43;
    private widget.Label jLabel44;
    private widget.Label jLabel45;
    private widget.Label label10;
    private widget.Label label12;
    private widget.Label label14;
    private widget.Label label9;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollInput;
    private widget.ScrollPane scrollPane2;
    private widget.ScrollPane scrollPane3;
    private widget.ScrollPane scrollPane4;
    private widget.ScrollPane scrollPane5;
    private widget.ScrollPane scrollPane6;
    private widget.ScrollPane scrollPane7;
    public widget.Table tbDiagnosa;
    public widget.Table tbDiagnosa10;
    public widget.Table tbDiagnosa2;
    public widget.Table tbDiagnosa3;
    public widget.Table tbDiagnosa4;
    public widget.Table tbDiagnosa5;
    public widget.Table tbDiagnosa6;
    public widget.Table tbDiagnosa7;
    public widget.Table tbDiagnosa8;
    public widget.Table tbDiagnosa9;
    private widget.Table tbDokter;
    public widget.Table tbProsedur;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            ps=koneksi.prepareStatement(
                    "select template_hasil_radiologi.no_template,template_hasil_radiologi.nama_pemeriksaan, "+
                    "template_hasil_radiologi.template_hasil_radiologi from template_hasil_radiologi "+
                    "where template_hasil_radiologi.no_template like ? or template_hasil_radiologi.nama_pemeriksaan like ? or "+
                    "template_hasil_radiologi.template_hasil_radiologi like ? order by template_hasil_radiologi.no_template");
            try {
                ps.setString(1,"%"+TCari.getText().trim()+"%");
                ps.setString(2,"%"+TCari.getText().trim()+"%");
                ps.setString(3,"%"+TCari.getText().trim()+"%");
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new Object[]{
                        rs.getString(1),rs.getString(2),rs.getString(3)
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
        /*Kd.setText("");
        Nm.setText("");
        Template.setText("");
        Valid.autoNomer("template_hasil_radiologi","R",4,Kd);
        TabRawat.setSelectedIndex(0);
        Kd.requestFocus();*/
    }

    private void getData() {
        if(tbDokter.getSelectedRow()!= -1){
            Kd.setText(tabMode.getValueAt(tbDokter.getSelectedRow(),0).toString());
            //Nm.setText(tabMode.getValueAt(tbDokter.getSelectedRow(),1).toString());
            Subjek.setText(tabMode.getValueAt(tbDokter.getSelectedRow(),2).toString());
        }
    }

    public JTable getTable(){
        return tbDokter;
    }
    
    public void isCek(){
        BtnSimpan.setEnabled(akses.gettemplate_pemeriksaan());
        BtnHapus.setEnabled(akses.gettemplate_pemeriksaan());
        BtnEdit.setEnabled(akses.gettemplate_pemeriksaan());
    }
    
    public void setTampil(){
       TabRawat.setSelectedIndex(1);
    }
    
    private void tampildiagnosa() {
        try{
            jml=0;
            for(i=0;i<tbDiagnosa.getRowCount();i++){
                if(tbDiagnosa.getValueAt(i,0).toString().equals("true")){
                    jml++;
                }
            }

            pilih=null;
            pilih=new boolean[jml];
            kode=null;
            kode=new String[jml];
            nama=null;
            nama=new String[jml];
            ciripny=null;
            ciripny=new String[jml];
            keterangan=null;
            keterangan=new String[jml];
            kategori=null;
            kategori=new String[jml];
            cirium=null;
            cirium=new String[jml];

            index=0; 
            for(i=0;i<tbDiagnosa.getRowCount();i++){
                if(tbDiagnosa.getValueAt(i,0).toString().equals("true")){
                    pilih[index]=true;
                    kode[index]=tbDiagnosa.getValueAt(i,1).toString();
                    nama[index]=tbDiagnosa.getValueAt(i,2).toString();
                    ciripny[index]=tbDiagnosa.getValueAt(i,3).toString();
                    keterangan[index]=tbDiagnosa.getValueAt(i,4).toString();
                    kategori[index]=tbDiagnosa.getValueAt(i,5).toString();
                    cirium[index]=tbDiagnosa.getValueAt(i,6).toString();
                    index++;
                }
            }

            Valid.tabelKosong(tabModeDiagnosa);
            for(i=0;i<jml;i++){
                tabModeDiagnosa.addRow(new Object[] {pilih[i],kode[i],nama[i],ciripny[i],keterangan[i],kategori[i],cirium[i]});
            }       

            pspenyakit=koneksi.prepareStatement("select penyakit.kd_penyakit,penyakit.nm_penyakit,penyakit.ciri_ciri,penyakit.keterangan, "+
                    "kategori_penyakit.nm_kategori,kategori_penyakit.ciri_umum "+
                    "from kategori_penyakit inner join penyakit "+
                    "on penyakit.kd_ktg=kategori_penyakit.kd_ktg where  "+
                    " penyakit.kd_penyakit like ? or "+
                    " penyakit.nm_penyakit like ? or "+
                    " penyakit.ciri_ciri like ? or "+
                    " penyakit.keterangan like ? or "+
                    " kategori_penyakit.nm_kategori like ? or "+
                    " kategori_penyakit.ciri_umum like ? "+
                    "order by penyakit.kd_penyakit  LIMIT 1000");
            try {
                pspenyakit.setString(1,"%"+Diagnosa.getText().trim()+"%");
                pspenyakit.setString(2,"%"+Diagnosa.getText().trim()+"%");
                pspenyakit.setString(3,"%"+Diagnosa.getText().trim()+"%");
                pspenyakit.setString(4,"%"+Diagnosa.getText().trim()+"%");
                pspenyakit.setString(5,"%"+Diagnosa.getText().trim()+"%");
                pspenyakit.setString(6,"%"+Diagnosa.getText().trim()+"%");  
                rs=pspenyakit.executeQuery();
                while(rs.next()){
                    tabModeDiagnosa.addRow(new Object[]{false,rs.getString(1),
                                   rs.getString(2),
                                   rs.getString(3),
                                   rs.getString(4),
                                   rs.getString(5),
                                   rs.getString(6)});
                } 
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(pspenyakit!=null){
                    pspenyakit.close();
                }
            }           
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }
    
    private void tampilprosedure() {
        try{
            jml=0;
            for(i=0;i<tbProsedur.getRowCount();i++){
                if(tbProsedur.getValueAt(i,0).toString().equals("true")){
                    jml++;
                }
            }

            pilih=null;
            pilih=new boolean[jml];
            kode2=null;
            kode2=new String[jml];
            panjang=null;
            panjang=new String[jml];
            pendek=null;
            pendek=new String[jml];

            index=0; 
            for(i=0;i<tbProsedur.getRowCount();i++){
                if(tbProsedur.getValueAt(i,0).toString().equals("true")){
                    pilih[index]=true;
                    kode2[index]=tbProsedur.getValueAt(i,1).toString();
                    panjang[index]=tbProsedur.getValueAt(i,2).toString();
                    pendek[index]=tbProsedur.getValueAt(i,3).toString();
                    index++;
                }
            }

            Valid.tabelKosong(tabModeProsedur);
            for(i=0;i<jml;i++){
                tabModeProsedur.addRow(new Object[] {pilih[i],kode2[i],panjang[i],pendek[i]});
            }
            
            psprosedur=koneksi.prepareStatement("select * from icd9 where kode like ? or "+
                    " deskripsi_panjang like ? or  deskripsi_pendek like ? order by kode");
            try{
                psprosedur.setString(1,"%"+Prosedur.getText().trim()+"%");
                psprosedur.setString(2,"%"+Prosedur.getText().trim()+"%");
                psprosedur.setString(3,"%"+Prosedur.getText().trim()+"%");
                rs=psprosedur.executeQuery();
                while(rs.next()){
                    tabModeProsedur.addRow(new Object[]{
                        false,rs.getString(1),rs.getString(2),rs.getString(3)});
                }
            }catch(Exception ex){
                System.out.println(ex);
            }finally{
                if(rs != null){
                    rs.close();
                }
                if(psprosedur != null){
                    psprosedur.close();
                }
            }
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }
}

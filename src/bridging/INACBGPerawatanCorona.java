package bridging;

import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
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

/**
 *
 * @author dosen
 */
public class INACBGPerawatanCorona extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;
    

    /** Creates new form DlgPemberianInfus
     * @param parent
     * @param modal */
    public INACBGPerawatanCorona(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        tabMode=new DefaultTableModel(null,new Object[]{
                "No.Rawat","No.RM","Nama Pasien","Tgl.Masuk","Pemulasaran Jenazah","Kantong Jenazah","Peti Jenazah","Plastik Erat",
                "Desinfektan Jenazah","Mobil Jenazah","Desinfektan Mobil Jenazah","Status Covid","No.Jaminan/KTP/JKN","Episode 1",
                "Episode 2","Episode 3","Episode 4","Episode 5","Episode 6","Comorbid"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 20; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(105);
            }else if(i==1){
                column.setPreferredWidth(60);
            }else if(i==2){
                column.setPreferredWidth(150);
            }else if(i==3){
                column.setPreferredWidth(65);
            }else if(i==4){
                column.setPreferredWidth(114);
            }else if(i==5){
                column.setPreferredWidth(91);
            }else if(i==6){
                column.setPreferredWidth(70);
            }else if(i==7){
                column.setPreferredWidth(63);
            }else if(i==8){
                column.setPreferredWidth(109);
            }else if(i==9){
                column.setPreferredWidth(77);
            }else if(i==10){
                column.setPreferredWidth(138);
            }else if(i==11){
                column.setPreferredWidth(69);
            }else if(i==12){
                column.setPreferredWidth(125);
            }else if(i==13){
                column.setPreferredWidth(55);
            }else if(i==14){
                column.setPreferredWidth(55);
            }else if(i==15){
                column.setPreferredWidth(55);
            }else if(i==16){
                column.setPreferredWidth(55);
            }else if(i==17){
                column.setPreferredWidth(55);
            }else if(i==18){
                column.setPreferredWidth(55);
            }else if(i==19){
                column.setPreferredWidth(55);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());

        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        NoJaminan.setDocument(new batasInput((byte)30).getKata(NoJaminan));
        Episodes1.setDocument(new batasInput((byte)3).getOnlyAngka(Episodes1));
        Episodes2.setDocument(new batasInput((byte)3).getOnlyAngka(Episodes2));
        Episodes3.setDocument(new batasInput((byte)3).getOnlyAngka(Episodes3));
        Episodes4.setDocument(new batasInput((byte)3).getOnlyAngka(Episodes4));
        Episodes5.setDocument(new batasInput((byte)3).getOnlyAngka(Episodes5));
        Episodes6.setDocument(new batasInput((byte)3).getOnlyAngka(Episodes6));

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

        jPopupMenu2 = new javax.swing.JPopupMenu();
        MnNIK = new javax.swing.JMenuItem();
        MnKartu = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbObat = new widget.Table();
        jPanel3 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        BtnPrint = new widget.Button();
        jLabel10 = new widget.Label();
        LCount = new widget.Label();
        BtnKeluar = new widget.Button();
        panelGlass7 = new widget.panelisi();
        jLabel15 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel17 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        PanelInput = new javax.swing.JPanel();
        ChkInput = new widget.CekBox();
        FormInput = new widget.PanelBiasa();
        NoRM = new widget.TextBox();
        NamaPasien = new widget.TextBox();
        jLabel7 = new widget.Label();
        NoRawat = new widget.TextBox();
        PemulasaranJenazah = new widget.ComboBox();
        jLabel12 = new widget.Label();
        KantongJenazah = new widget.ComboBox();
        jLabel13 = new widget.Label();
        jLabel14 = new widget.Label();
        PetiJenazah = new widget.ComboBox();
        jLabel16 = new widget.Label();
        PlastikErat = new widget.ComboBox();
        jLabel18 = new widget.Label();
        DesinfektanJenazah = new widget.ComboBox();
        jLabel19 = new widget.Label();
        MobilJenazah = new widget.ComboBox();
        DesinFektanMobilJenazah = new widget.ComboBox();
        jLabel20 = new widget.Label();
        jLabel21 = new widget.Label();
        StatusCovid = new widget.ComboBox();
        jLabel8 = new widget.Label();
        NoJaminan = new widget.TextBox();
        jLabel9 = new widget.Label();
        Episodes1 = new widget.TextBox();
        Episodes2 = new widget.TextBox();
        jLabel11 = new widget.Label();
        Episodes3 = new widget.TextBox();
        jLabel22 = new widget.Label();
        Episodes4 = new widget.TextBox();
        jLabel23 = new widget.Label();
        Episodes5 = new widget.TextBox();
        jLabel24 = new widget.Label();
        jLabel25 = new widget.Label();
        Episodes6 = new widget.TextBox();
        jLabel26 = new widget.Label();
        Comorbid = new widget.ComboBox();

        jPopupMenu2.setName("jPopupMenu2"); // NOI18N

        MnNIK.setBackground(new java.awt.Color(255, 255, 254));
        MnNIK.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnNIK.setForeground(new java.awt.Color(50, 50, 50));
        MnNIK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnNIK.setText("Tampilkan NIK/No.KTP/No.Paspor");
        MnNIK.setName("MnNIK"); // NOI18N
        MnNIK.setPreferredSize(new java.awt.Dimension(270, 26));
        MnNIK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnNIKActionPerformed(evt);
            }
        });
        jPopupMenu2.add(MnNIK);

        MnKartu.setBackground(new java.awt.Color(255, 255, 254));
        MnKartu.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnKartu.setForeground(new java.awt.Color(50, 50, 50));
        MnKartu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnKartu.setText("Tampilkan No.Penjamin/Asuransi/JKN");
        MnKartu.setName("MnKartu"); // NOI18N
        MnKartu.setPreferredSize(new java.awt.Dimension(270, 26));
        MnKartu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnKartuActionPerformed(evt);
            }
        });
        jPopupMenu2.add(MnKartu);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Kebutuhan Perawatan Pasien Corona Untuk Penagihan INACBG ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbObat.setAutoCreateRowSorter(true);
        tbObat.setName("tbObat"); // NOI18N
        tbObat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbObatMouseClicked(evt);
            }
        });
        tbObat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbObatKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbObat);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(44, 100));
        jPanel3.setLayout(new java.awt.BorderLayout(1, 1));

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

        jLabel10.setText("Record :");
        jLabel10.setName("jLabel10"); // NOI18N
        jLabel10.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass8.add(jLabel10);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(95, 23));
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

        jPanel3.add(panelGlass8, java.awt.BorderLayout.PAGE_END);

        panelGlass7.setName("panelGlass7"); // NOI18N
        panelGlass7.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass7.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel15.setText("Tgl.Masuk :");
        jLabel15.setName("jLabel15"); // NOI18N
        jLabel15.setPreferredSize(new java.awt.Dimension(63, 23));
        panelGlass7.add(jLabel15);

        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "23-04-2020" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass7.add(DTPCari1);

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("s.d");
        jLabel17.setName("jLabel17"); // NOI18N
        jLabel17.setPreferredSize(new java.awt.Dimension(24, 23));
        panelGlass7.add(jLabel17);

        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "23-04-2020" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass7.add(DTPCari2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass7.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(360, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass7.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('7');
        BtnCari.setToolTipText("Alt+7");
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
        panelGlass7.add(BtnCari);

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
        panelGlass7.add(BtnAll);

        jPanel3.add(panelGlass7, java.awt.BorderLayout.CENTER);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 306));
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        ChkInput.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput.setMnemonic('M');
        ChkInput.setText(".: Input Data");
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
        FormInput.setPreferredSize(new java.awt.Dimension(190, 107));
        FormInput.setLayout(null);

        NoRM.setEditable(false);
        NoRM.setHighlighter(null);
        NoRM.setName("NoRM"); // NOI18N
        FormInput.add(NoRM);
        NoRM.setBounds(219, 10, 100, 23);

        NamaPasien.setEditable(false);
        NamaPasien.setHighlighter(null);
        NamaPasien.setName("NamaPasien"); // NOI18N
        FormInput.add(NamaPasien);
        NamaPasien.setBounds(321, 10, 497, 23);

        jLabel7.setText("No.Rawat :");
        jLabel7.setName("jLabel7"); // NOI18N
        FormInput.add(jLabel7);
        jLabel7.setBounds(0, 10, 72, 23);

        NoRawat.setEditable(false);
        NoRawat.setHighlighter(null);
        NoRawat.setName("NoRawat"); // NOI18N
        FormInput.add(NoRawat);
        NoRawat.setBounds(76, 10, 141, 23);

        PemulasaranJenazah.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        PemulasaranJenazah.setName("PemulasaranJenazah"); // NOI18N
        PemulasaranJenazah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PemulasaranJenazahKeyPressed(evt);
            }
        });
        FormInput.add(PemulasaranJenazah);
        PemulasaranJenazah.setBounds(229, 40, 85, 23);

        jLabel12.setText("Dilakukan Pemulasaran Jenazah ?");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(0, 40, 225, 23);

        KantongJenazah.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        KantongJenazah.setName("KantongJenazah"); // NOI18N
        KantongJenazah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KantongJenazahKeyPressed(evt);
            }
        });
        FormInput.add(KantongJenazah);
        KantongJenazah.setBounds(229, 70, 85, 23);

        jLabel13.setText("Menggunakan Kantong Jenazah ?");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(0, 70, 225, 23);

        jLabel14.setText("Menggunakan Peti Jenazah ?");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(0, 100, 225, 23);

        PetiJenazah.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        PetiJenazah.setName("PetiJenazah"); // NOI18N
        PetiJenazah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PetiJenazahKeyPressed(evt);
            }
        });
        FormInput.add(PetiJenazah);
        PetiJenazah.setBounds(229, 100, 85, 23);

        jLabel16.setText("Menggunakan Plastik Erat ?");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput.add(jLabel16);
        jLabel16.setBounds(0, 130, 225, 23);

        PlastikErat.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        PlastikErat.setName("PlastikErat"); // NOI18N
        PlastikErat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PlastikEratKeyPressed(evt);
            }
        });
        FormInput.add(PlastikErat);
        PlastikErat.setBounds(229, 130, 85, 23);

        jLabel18.setText("Menggunakan Desinfektan Jenazah ?");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(0, 160, 225, 23);

        DesinfektanJenazah.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        DesinfektanJenazah.setName("DesinfektanJenazah"); // NOI18N
        DesinfektanJenazah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DesinfektanJenazahKeyPressed(evt);
            }
        });
        FormInput.add(DesinfektanJenazah);
        DesinfektanJenazah.setBounds(229, 160, 85, 23);

        jLabel19.setText("Menggunakan Mobil Jenazah ?");
        jLabel19.setName("jLabel19"); // NOI18N
        FormInput.add(jLabel19);
        jLabel19.setBounds(0, 190, 225, 23);

        MobilJenazah.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        MobilJenazah.setName("MobilJenazah"); // NOI18N
        MobilJenazah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MobilJenazahKeyPressed(evt);
            }
        });
        FormInput.add(MobilJenazah);
        MobilJenazah.setBounds(229, 190, 85, 23);

        DesinFektanMobilJenazah.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        DesinFektanMobilJenazah.setName("DesinFektanMobilJenazah"); // NOI18N
        DesinFektanMobilJenazah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DesinFektanMobilJenazahKeyPressed(evt);
            }
        });
        FormInput.add(DesinFektanMobilJenazah);
        DesinFektanMobilJenazah.setBounds(229, 220, 85, 23);

        jLabel20.setText("Menggunakan Desinfektan Mobil Jenazah ?");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(0, 220, 225, 23);

        jLabel21.setText("Status Covid/Corona :");
        jLabel21.setName("jLabel21"); // NOI18N
        FormInput.add(jLabel21);
        jLabel21.setBounds(0, 250, 225, 23);

        StatusCovid.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ODP", "PDP", "Positif" }));
        StatusCovid.setName("StatusCovid"); // NOI18N
        StatusCovid.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                StatusCovidKeyPressed(evt);
            }
        });
        FormInput.add(StatusCovid);
        StatusCovid.setBounds(229, 250, 85, 23);

        jLabel8.setText("No.Jaminan/NIK/KITAS/KITAP/PASPOR/JKN :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(320, 40, 309, 23);

        NoJaminan.setComponentPopupMenu(jPopupMenu2);
        NoJaminan.setHighlighter(null);
        NoJaminan.setName("NoJaminan"); // NOI18N
        NoJaminan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoJaminanKeyPressed(evt);
            }
        });
        FormInput.add(NoJaminan);
        NoJaminan.setBounds(633, 40, 185, 23);

        jLabel9.setText("Jumlah Hari Penggunaan Ruang ICU Dengan Ventilator :");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(320, 70, 409, 23);

        Episodes1.setHighlighter(null);
        Episodes1.setName("Episodes1"); // NOI18N
        Episodes1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Episodes1KeyPressed(evt);
            }
        });
        FormInput.add(Episodes1);
        Episodes1.setBounds(733, 70, 85, 23);

        Episodes2.setHighlighter(null);
        Episodes2.setName("Episodes2"); // NOI18N
        Episodes2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Episodes2KeyPressed(evt);
            }
        });
        FormInput.add(Episodes2);
        Episodes2.setBounds(733, 100, 85, 23);

        jLabel11.setText("Jumlah Hari Penggunaan Ruang ICU Tanpa Ventilator :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(320, 100, 409, 23);

        Episodes3.setHighlighter(null);
        Episodes3.setName("Episodes3"); // NOI18N
        Episodes3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Episodes3KeyPressed(evt);
            }
        });
        FormInput.add(Episodes3);
        Episodes3.setBounds(733, 130, 85, 23);

        jLabel22.setText("Jumlah Hari Penggunaan Ruang Isolasi Tekanan Negatif Dengan Ventilator :");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(320, 130, 409, 23);

        Episodes4.setHighlighter(null);
        Episodes4.setName("Episodes4"); // NOI18N
        Episodes4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Episodes4KeyPressed(evt);
            }
        });
        FormInput.add(Episodes4);
        Episodes4.setBounds(733, 160, 85, 23);

        jLabel23.setText("Jumlah Hari Penggunaan Ruang Isolasi Tekanan Negatif Tanpa Ventilator :");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(320, 160, 409, 23);

        Episodes5.setHighlighter(null);
        Episodes5.setName("Episodes5"); // NOI18N
        Episodes5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Episodes5KeyPressed(evt);
            }
        });
        FormInput.add(Episodes5);
        Episodes5.setBounds(733, 190, 85, 23);

        jLabel24.setText("Jumlah Hari Penggunaan Ruang Isolasi Non Tekanan Negatif Dengan Ventilator :");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(320, 190, 409, 23);

        jLabel25.setText("Jumlah Hari Penggunaan Ruang Isolasi Non Tekanan Negatif Tanpa Ventilator :");
        jLabel25.setName("jLabel25"); // NOI18N
        FormInput.add(jLabel25);
        jLabel25.setBounds(320, 220, 409, 23);

        Episodes6.setHighlighter(null);
        Episodes6.setName("Episodes6"); // NOI18N
        Episodes6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Episodes6KeyPressed(evt);
            }
        });
        FormInput.add(Episodes6);
        Episodes6.setBounds(733, 220, 85, 23);

        jLabel26.setText("Ada Comorbid/Complexity/Penyerta ?");
        jLabel26.setName("jLabel26"); // NOI18N
        FormInput.add(jLabel26);
        jLabel26.setBounds(320, 250, 309, 23);

        Comorbid.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Comorbid.setName("Comorbid"); // NOI18N
        Comorbid.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ComorbidKeyPressed(evt);
            }
        });
        FormInput.add(Comorbid);
        Comorbid.setBounds(633, 250, 85, 23);

        PanelInput.add(FormInput, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(NoRM.getText().trim().equals("")||NamaPasien.getText().trim().equals("")){
            Valid.textKosong(NoRM,"Pasien");
        }else if(NoJaminan.getText().trim().equals("")){
            Valid.textKosong(NoJaminan,"No.Jaminan");
        }else if(Episodes1.getText().trim().equals("")){
            Valid.textKosong(Episodes1,"Jumlah Hari");
        }else if(Episodes2.getText().trim().equals("")){
            Valid.textKosong(Episodes2,"Jumlah Hari");
        }else if(Episodes3.getText().trim().equals("")){
            Valid.textKosong(Episodes3,"Jumlah Hari");
        }else if(Episodes4.getText().trim().equals("")){
            Valid.textKosong(Episodes4,"Jumlah Hari");
        }else if(Episodes5.getText().trim().equals("")){
            Valid.textKosong(Episodes5,"Jumlah Hari");
        }else if(Episodes6.getText().trim().equals("")){
            Valid.textKosong(Episodes6,"Jumlah Hari");
        }else{
            if(Sequel.menyimpantf("perawatan_corona","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat",17,new String[]{
                NoRawat.getText(),PemulasaranJenazah.getSelectedItem().toString(),KantongJenazah.getSelectedItem().toString(),PetiJenazah.getSelectedItem().toString(), 
                PlastikErat.getSelectedItem().toString(),DesinfektanJenazah.getSelectedItem().toString(),MobilJenazah.getSelectedItem().toString(),
                DesinFektanMobilJenazah.getSelectedItem().toString(),StatusCovid.getSelectedItem().toString(),NoJaminan.getText(),Episodes1.getText(),
                Episodes2.getText(),Episodes3.getText(),Episodes4.getText(),Episodes5.getText(),Episodes6.getText(),Comorbid.getSelectedItem().toString()
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
           Valid.pindah(evt,Comorbid,BtnBatal);
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
        if(tbObat.getSelectedRow()> -1){ 
            Sequel.meghapus("perawatan_corona","no_rawat",tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
            tampil();
        }else{
            JOptionPane.showMessageDialog(null,"Maaf silahkan pilih data terlebih dahulu..!!");
        }
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnBatal, BtnPrint);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnPrint,TCari);}
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
            Valid.MyReportqry("rptPerawatanCorona.jasper","report","::[ Kebutuhan Perawatan Pasien Corona Untuk Penagihan INACBG ]::",
                    "select reg_periksa.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,reg_periksa.tgl_registrasi,perawatan_corona.pemulasaraan_jenazah,"+
                    "perawatan_corona.kantong_jenazah,perawatan_corona.peti_jenazah,perawatan_corona.plastik_erat,perawatan_corona.desinfektan_jenazah,"+
                    "perawatan_corona.mobil_jenazah,perawatan_corona.desinfektan_mobil_jenazah,perawatan_corona.covid19_status_cd,perawatan_corona.nomor_kartu_t,"+
                    "perawatan_corona.episodes1,perawatan_corona.episodes2,perawatan_corona.episodes3,perawatan_corona.episodes4,perawatan_corona.episodes5,"+
                    "perawatan_corona.episodes6,perawatan_corona.covid19_cc_ind from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join perawatan_corona on perawatan_corona.no_rawat=reg_periksa.no_rawat where "+
                    "reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' "+
                    (TCari.getText().trim().equals("")?"":"and (reg_periksa.no_rawat like '%"+TCari.getText().trim()+"%' or reg_periksa.no_rkm_medis like '%"+TCari.getText().trim()+"%' "+
                    "or pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or perawatan_corona.covid19_status_cd like '%"+TCari.getText().trim()+"%' or "+
                    "perawatan_corona.nomor_kartu_t like '%"+TCari.getText().trim()+"%') ")+"order by reg_periksa.tgl_registrasi",param);
        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnKeluar);
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
            Valid.pindah(evt, BtnCari, NamaPasien);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void tbObatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbObatMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbObatMouseClicked

    private void tbObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbObatKeyPressed
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
}//GEN-LAST:event_tbObatKeyPressed

private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
  isForm();                
}//GEN-LAST:event_ChkInputActionPerformed

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

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
        if(NoRM.getText().trim().equals("")||NamaPasien.getText().trim().equals("")){
            Valid.textKosong(NoRM,"Pasien");
        }else if(NoJaminan.getText().trim().equals("")){
            Valid.textKosong(NoJaminan,"No.Jaminan");
        }else if(Episodes1.getText().trim().equals("")){
            Valid.textKosong(Episodes1,"Jumlah Hari");
        }else if(Episodes2.getText().trim().equals("")){
            Valid.textKosong(Episodes2,"Jumlah Hari");
        }else if(Episodes3.getText().trim().equals("")){
            Valid.textKosong(Episodes3,"Jumlah Hari");
        }else if(Episodes4.getText().trim().equals("")){
            Valid.textKosong(Episodes4,"Jumlah Hari");
        }else if(Episodes5.getText().trim().equals("")){
            Valid.textKosong(Episodes5,"Jumlah Hari");
        }else if(Episodes6.getText().trim().equals("")){
            Valid.textKosong(Episodes6,"Jumlah Hari");
        }else{
            if(tbObat.getSelectedRow()> -1){ 
                if(Sequel.mengedittf("perawatan_corona","no_rawat=?","no_rawat=?,pemulasaraan_jenazah=?,kantong_jenazah=?,peti_jenazah=?,plastik_erat=?,desinfektan_jenazah=?,mobil_jenazah=?,desinfektan_mobil_jenazah=?,covid19_status_cd=?,nomor_kartu_t=?,episodes1=?,episodes2=?,episodes3=?,episodes4=?,episodes5=?,episodes6=?,covid19_cc_ind=?",18,new String[]{
                    NoRawat.getText(),PemulasaranJenazah.getSelectedItem().toString(),KantongJenazah.getSelectedItem().toString(),PetiJenazah.getSelectedItem().toString(), 
                    PlastikErat.getSelectedItem().toString(),DesinfektanJenazah.getSelectedItem().toString(),MobilJenazah.getSelectedItem().toString(),
                    DesinFektanMobilJenazah.getSelectedItem().toString(),StatusCovid.getSelectedItem().toString(),NoJaminan.getText(),Episodes1.getText(),
                    Episodes2.getText(),Episodes3.getText(),Episodes4.getText(),Episodes5.getText(),Episodes6.getText(),Comorbid.getSelectedItem().toString(),
                    tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
                })==true){
                    tampil();
                    emptTeks();
                }
            }
        }
    }//GEN-LAST:event_BtnEditActionPerformed

    private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnEditActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnKeluar);
        }
    }//GEN-LAST:event_BtnEditKeyPressed

    private void MnNIKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnNIKActionPerformed
        if(NoRM.getText().trim().equals("")||NamaPasien.getText().trim().equals("")){
            Valid.textKosong(NoRM,"Pasien");
        }else{
            Sequel.cariIsi("select no_ktp from pasien where no_rkm_medis=?",NoJaminan,NoRM.getText());
        }
            
    }//GEN-LAST:event_MnNIKActionPerformed

    private void MnKartuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnKartuActionPerformed
        if(NoRM.getText().trim().equals("")||NamaPasien.getText().trim().equals("")){
            Valid.textKosong(NoRM,"Pasien");
        }else{
            Sequel.cariIsi("select no_peserta from pasien where no_rkm_medis=?",NoJaminan,NoRM.getText());
        }
    }//GEN-LAST:event_MnKartuActionPerformed

    private void PemulasaranJenazahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PemulasaranJenazahKeyPressed
        Valid.pindah(evt,TCari,KantongJenazah);
    }//GEN-LAST:event_PemulasaranJenazahKeyPressed

    private void KantongJenazahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KantongJenazahKeyPressed
        Valid.pindah(evt,PemulasaranJenazah,PetiJenazah);
    }//GEN-LAST:event_KantongJenazahKeyPressed

    private void PetiJenazahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PetiJenazahKeyPressed
        Valid.pindah(evt,KantongJenazah,PlastikErat);
    }//GEN-LAST:event_PetiJenazahKeyPressed

    private void PlastikEratKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PlastikEratKeyPressed
        Valid.pindah(evt,PetiJenazah,DesinfektanJenazah);
    }//GEN-LAST:event_PlastikEratKeyPressed

    private void DesinfektanJenazahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DesinfektanJenazahKeyPressed
        Valid.pindah(evt,PlastikErat,MobilJenazah);
    }//GEN-LAST:event_DesinfektanJenazahKeyPressed

    private void MobilJenazahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MobilJenazahKeyPressed
        Valid.pindah(evt,DesinfektanJenazah,DesinFektanMobilJenazah);
    }//GEN-LAST:event_MobilJenazahKeyPressed

    private void DesinFektanMobilJenazahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DesinFektanMobilJenazahKeyPressed
        Valid.pindah(evt,MobilJenazah,StatusCovid);
    }//GEN-LAST:event_DesinFektanMobilJenazahKeyPressed

    private void StatusCovidKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_StatusCovidKeyPressed
        Valid.pindah(evt,DesinFektanMobilJenazah,NoJaminan);
    }//GEN-LAST:event_StatusCovidKeyPressed

    private void NoJaminanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoJaminanKeyPressed
        Valid.pindah(evt,StatusCovid,Episodes1);
    }//GEN-LAST:event_NoJaminanKeyPressed

    private void Episodes1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Episodes1KeyPressed
        Valid.pindah(evt,NoJaminan,Episodes2);
    }//GEN-LAST:event_Episodes1KeyPressed

    private void Episodes2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Episodes2KeyPressed
        Valid.pindah(evt,Episodes1,Episodes3);
    }//GEN-LAST:event_Episodes2KeyPressed

    private void Episodes3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Episodes3KeyPressed
        Valid.pindah(evt,Episodes2,Episodes4);
    }//GEN-LAST:event_Episodes3KeyPressed

    private void Episodes4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Episodes4KeyPressed
        Valid.pindah(evt,Episodes3,Episodes5);
    }//GEN-LAST:event_Episodes4KeyPressed

    private void Episodes5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Episodes5KeyPressed
        Valid.pindah(evt,Episodes4,Episodes6);
    }//GEN-LAST:event_Episodes5KeyPressed

    private void Episodes6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Episodes6KeyPressed
        Valid.pindah(evt,Episodes5,Comorbid);
    }//GEN-LAST:event_Episodes6KeyPressed

    private void ComorbidKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ComorbidKeyPressed
        Valid.pindah(evt,Episodes6,BtnSimpan);
    }//GEN-LAST:event_ComorbidKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            INACBGPerawatanCorona dialog = new INACBGPerawatanCorona(new javax.swing.JFrame(), true);
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
    private widget.ComboBox Comorbid;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.ComboBox DesinFektanMobilJenazah;
    private widget.ComboBox DesinfektanJenazah;
    private widget.TextBox Episodes1;
    private widget.TextBox Episodes2;
    private widget.TextBox Episodes3;
    private widget.TextBox Episodes4;
    private widget.TextBox Episodes5;
    private widget.TextBox Episodes6;
    private widget.PanelBiasa FormInput;
    private widget.ComboBox KantongJenazah;
    private widget.Label LCount;
    private javax.swing.JMenuItem MnKartu;
    private javax.swing.JMenuItem MnNIK;
    private widget.ComboBox MobilJenazah;
    private widget.TextBox NamaPasien;
    private widget.TextBox NoJaminan;
    private widget.TextBox NoRM;
    private widget.TextBox NoRawat;
    private javax.swing.JPanel PanelInput;
    private widget.ComboBox PemulasaranJenazah;
    private widget.ComboBox PetiJenazah;
    private widget.ComboBox PlastikErat;
    private widget.ScrollPane Scroll;
    private widget.ComboBox StatusCovid;
    private widget.TextBox TCari;
    private widget.InternalFrame internalFrame1;
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
    private widget.Label jLabel22;
    private widget.Label jLabel23;
    private widget.Label jLabel24;
    private widget.Label jLabel25;
    private widget.Label jLabel26;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu2;
    private widget.panelisi panelGlass7;
    private widget.panelisi panelGlass8;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    public void tampil() {     
        Valid.tabelKosong(tabMode);
        try{    
            ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,reg_periksa.tgl_registrasi,perawatan_corona.pemulasaraan_jenazah,"+
                    "perawatan_corona.kantong_jenazah,perawatan_corona.peti_jenazah,perawatan_corona.plastik_erat,perawatan_corona.desinfektan_jenazah,"+
                    "perawatan_corona.mobil_jenazah,perawatan_corona.desinfektan_mobil_jenazah,perawatan_corona.covid19_status_cd,perawatan_corona.nomor_kartu_t,"+
                    "perawatan_corona.episodes1,perawatan_corona.episodes2,perawatan_corona.episodes3,perawatan_corona.episodes4,perawatan_corona.episodes5,"+
                    "perawatan_corona.episodes6,perawatan_corona.covid19_cc_ind from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join perawatan_corona on perawatan_corona.no_rawat=reg_periksa.no_rawat where reg_periksa.tgl_registrasi between ? and ? "+
                    (TCari.getText().trim().equals("")?"":"and (reg_periksa.no_rawat like ? or reg_periksa.no_rkm_medis like ? or pasien.nm_pasien like ? or "+
                    "perawatan_corona.covid19_status_cd like ? or perawatan_corona.nomor_kartu_t like ?) ")+
                    "order by reg_periksa.tgl_registrasi");
            try {
                ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                if(!TCari.getText().trim().equals("")){
                    ps.setString(3,"%"+TCari.getText().trim()+"%");
                    ps.setString(4,"%"+TCari.getText().trim()+"%");
                    ps.setString(5,"%"+TCari.getText().trim()+"%");
                    ps.setString(6,"%"+TCari.getText().trim()+"%");
                    ps.setString(7,"%"+TCari.getText().trim()+"%");
                }
                rs=ps.executeQuery();
                while(rs.next()){    
                    tabMode.addRow(new String[]{
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("tgl_registrasi"),rs.getString("pemulasaraan_jenazah"),
                        rs.getString("kantong_jenazah"),rs.getString("peti_jenazah"),rs.getString("plastik_erat"),rs.getString("desinfektan_jenazah"),
                        rs.getString("mobil_jenazah"),rs.getString("desinfektan_mobil_jenazah"),rs.getString("covid19_status_cd"),rs.getString("nomor_kartu_t"),
                        rs.getString("episodes1"),rs.getString("episodes2"),rs.getString("episodes3"),rs.getString("episodes4"),rs.getString("episodes5"),
                        rs.getString("episodes6"),rs.getString("covid19_cc_ind")
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
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        LCount.setText(""+tabMode.getRowCount());
    }

    public void emptTeks() {
        NoRM.setText("");
        NamaPasien.setText("");
        NoRawat.setText("");
        PemulasaranJenazah.setSelectedIndex(0);
        KantongJenazah.setSelectedIndex(0);
        PetiJenazah.setSelectedIndex(0);
        PlastikErat.setSelectedIndex(0);
        DesinfektanJenazah.setSelectedIndex(0);
        MobilJenazah.setSelectedIndex(0);
        DesinFektanMobilJenazah.setSelectedIndex(0);
        StatusCovid.setSelectedIndex(0);
        NoJaminan.setText("");
        Episodes1.setText("0");
        Episodes2.setText("0");
        Episodes3.setText("0");
        Episodes4.setText("0");
        Episodes5.setText("0");
        Episodes6.setText("0");
        Comorbid.setSelectedIndex(0);
        PemulasaranJenazah.requestFocus();
    }

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){      
            NoRawat.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
            NoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            NamaPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            PemulasaranJenazah.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString());
            KantongJenazah.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
            PetiJenazah.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString());
            PlastikErat.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());
            DesinfektanJenazah.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            MobilJenazah.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            DesinFektanMobilJenazah.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            StatusCovid.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            NoJaminan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            Episodes1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            Episodes2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            Episodes3.setText(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
            Episodes4.setText(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            Episodes5.setText(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());
            Episodes6.setText(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
            Comorbid.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
        }
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,306));
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
        BtnSimpan.setEnabled(akses.getperawatan_pasien_corona());
        BtnHapus.setEnabled(akses.getperawatan_pasien_corona());
        BtnEdit.setEnabled(akses.getperawatan_pasien_corona());
    }
    
    public void setPasien(String norawat,String norm,String namapasien){
        NoRawat.setText(norawat);
        NoRM.setText(norm);
        NamaPasien.setText(namapasien);
        Sequel.cariIsi("select no_ktp from pasien where no_rkm_medis=?",NoJaminan,norm);
        Sequel.cariIsi("select tgl_registrasi from reg_periksa where no_rawat='"+norawat+"'", DTPCari1);
        TCari.setText(norawat);
        ChkInput.setSelected(true);
        isForm(); 
    }
    
    public JTable getTable(){
        return tbObat;
    }

}

/*
 * by MAs ElKhanza
 */


package rekammedis;

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
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import kepegawaian.DlgCariPetugas;


/**
 *
 * @author perpustakaan
 */
public final class RMPerencanaanPemulangan extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;
    private DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    private StringBuilder htmlContent;
    private String finger="",pilihan="";
    
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public RMPerencanaanPemulangan(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Pernyataan","No.Rawat","No.RM","Nama Pasien","Tgl.Lahir","J.K.","Tanggal","Diagnosa","Ya/Tidak","Tindakan Kedokteran","Ya/Tidak",
            "Indikasi Tindakan","Ya/Tidak","Tata Cara","Ya/Tidak","Tujuan","Ya/Tidak","Risiko","Ya/Tidak","Komplikasi","Ya/Tidak","Prognosis","Ya/Tidak",
            "Alternatif & Resikonya","Ya/Tidak","Lain-lain","Ya/Tidak","Biaya","Ya/Tidak","Kode Dokter","Nama Dokter","Nip","Saksi II Perawat",
            "Penerima Informasi","Alasan Jika Diwakilkan","J.K. P.I","Tgl.Lahir P.I.","Umur P.I.","Alamat Penerima Informasi", "No.H.P. P.I",
            "Hubungan Dengan Pasien","Pernyataan","Saksi I Keluarga"
        }){
          @Override 
              public boolean isCellEditable(int rowIndex, int colIndex){
                  return false;
              }              
              Class[] types = new Class[] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Boolean.class, java.lang.Object.class, 
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Boolean.class, java.lang.Object.class, 
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Boolean.class, java.lang.Object.class, 
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Boolean.class, java.lang.Object.class, 
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Boolean.class, java.lang.Object.class, 
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        
        tbObat.setModel(tabMode);
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 43; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(95);
            }else if(i==1){
                column.setPreferredWidth(105);
            }else if(i==2){
                column.setPreferredWidth(70);
            }else if(i==3){
                column.setPreferredWidth(150);
            }else if(i==4){
                column.setPreferredWidth(65);
            }else if(i==5){
                column.setPreferredWidth(25);
            }else if(i==6){
                column.setPreferredWidth(65);
            }else if(i==7){
                column.setPreferredWidth(200);
            }else if(i==8){
                column.setPreferredWidth(50);
            }else if(i==9){
                column.setPreferredWidth(200);
            }else if(i==10){
                column.setPreferredWidth(50);
            }else if(i==11){
                column.setPreferredWidth(200);
            }else if(i==12){
                column.setPreferredWidth(50);
            }else if(i==13){
                column.setPreferredWidth(200);
            }else if(i==14){
                column.setPreferredWidth(50);
            }else if(i==15){
                column.setPreferredWidth(200);
            }else if(i==16){
                column.setPreferredWidth(50);
            }else if(i==17){
                column.setPreferredWidth(200);
            }else if(i==18){
                column.setPreferredWidth(50);
            }else if(i==19){
                column.setPreferredWidth(200);
            }else if(i==20){
                column.setPreferredWidth(50);
            }else if(i==21){
                column.setPreferredWidth(200);
            }else if(i==22){
                column.setPreferredWidth(50);
            }else if(i==23){
                column.setPreferredWidth(200);
            }else if(i==24){
                column.setPreferredWidth(50);
            }else if(i==25){
                column.setPreferredWidth(200);
            }else if(i==26){
                column.setPreferredWidth(50);
            }else if(i==27){
                column.setPreferredWidth(90);
            }else if(i==28){
                column.setPreferredWidth(50);
            }else if(i==29){
                column.setPreferredWidth(90);
            }else if(i==30){
                column.setPreferredWidth(150);
            }else if(i==31){
                column.setPreferredWidth(90);
            }else if(i==32){
                column.setPreferredWidth(150);
            }else if(i==33){
                column.setPreferredWidth(150);
            }else if(i==34){
                column.setPreferredWidth(150);
            }else if(i==35){
                column.setPreferredWidth(45);
            }else if(i==36){
                column.setPreferredWidth(70);
            }else if(i==37){
                column.setPreferredWidth(55);
            }else if(i==38){
                column.setPreferredWidth(150);
            }else if(i==39){
                column.setPreferredWidth(100);
            }else if(i==40){
                column.setPreferredWidth(130);
            }else if(i==41){
                column.setPreferredWidth(100);
            }else if(i==42){
                column.setPreferredWidth(150);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());
        
        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        TCari.setDocument(new batasInput((int)100).getKata(TCari));
        
        ChkAccor.setSelected(false);
        isPhoto();
        
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
        
        petugas.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(petugas.getTable().getSelectedRow()!= -1){
                    KdPetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                    NmPetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                    KdPetugas.requestFocus();
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
        
        HTMLEditorKit kit = new HTMLEditorKit();
        LoadHTML2.setEditable(true);
        LoadHTML2.setEditorKit(kit);
        LoadHTML3.setEditable(true);
        LoadHTML3.setEditorKit(kit);
        StyleSheet styleSheet = kit.getStyleSheet();
        styleSheet.addRule(
                ".isi td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-bottom: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                ".isi2 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#323232;}"+
                ".isi3 td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                ".isi4 td{font: 11px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                ".isi5 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#AA0000;}"+
                ".isi6 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#FF0000;}"+
                ".isi7 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#C8C800;}"+
                ".isi8 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#00AA00;}"+
                ".isi9 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#969696;}"
        );
        Document doc = kit.createDefaultDocument();
        LoadHTML2.setDocument(doc);
        LoadHTML3.setDocument(doc);
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
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        BtnPrint = new widget.Button();
        BtnAll = new widget.Button();
        BtnKeluar = new widget.Button();
        TabRawat = new javax.swing.JTabbedPane();
        internalFrame2 = new widget.InternalFrame();
        scrollInput = new widget.ScrollPane();
        FormInput = new widget.PanelBiasa();
        jSeparator14 = new javax.swing.JSeparator();
        TNoRw = new widget.TextBox();
        TPasien = new widget.TextBox();
        TNoRM = new widget.TextBox();
        jLabel8 = new widget.Label();
        TglLahir = new widget.TextBox();
        Jk = new widget.TextBox();
        jLabel10 = new widget.Label();
        label11 = new widget.Label();
        jLabel11 = new widget.Label();
        HubunganDenganPasien = new widget.ComboBox();
        TglPernyataan = new widget.Tanggal();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel13 = new widget.Label();
        AlamatPenerima = new widget.TextBox();
        jLabel39 = new widget.Label();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel101 = new widget.Label();
        label15 = new widget.Label();
        KdPetugas = new widget.TextBox();
        NmPetugas = new widget.TextBox();
        BtnDokter = new widget.Button();
        jLabel42 = new widget.Label();
        SaksiKeluarga = new widget.TextBox();
        TNoRw1 = new widget.TextBox();
        label12 = new widget.Label();
        jLabel40 = new widget.Label();
        AlamatPenerima1 = new widget.TextBox();
        jLabel41 = new widget.Label();
        AlamatPenerima2 = new widget.TextBox();
        internalFrame3 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbObat = new widget.Table();
        panelGlass9 = new widget.panelisi();
        jLabel19 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel21 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();
        PanelAccor = new widget.PanelBiasa();
        ChkAccor = new widget.CekBox();
        FormPhoto = new widget.PanelBiasa();
        FormPass3 = new widget.PanelBiasa();
        btnAmbil = new widget.Button();
        BtnRefreshPhoto1 = new widget.Button();
        TabData = new javax.swing.JTabbedPane();
        Scroll5 = new widget.ScrollPane();
        LoadHTML2 = new widget.editorpane();
        Scroll6 = new widget.ScrollPane();
        LoadHTML3 = new widget.editorpane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Perencanaan Pemulangan Pasien ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 54));
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

        internalFrame1.add(panelGlass8, java.awt.BorderLayout.PAGE_END);

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
        FormInput.setPreferredSize(new java.awt.Dimension(870, 633));
        FormInput.setLayout(null);

        jSeparator14.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator14.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator14.setName("jSeparator14"); // NOI18N
        FormInput.add(jSeparator14);
        jSeparator14.setBounds(0, 861, 880, 0);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(84, 10, 131, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        FormInput.add(TPasien);
        TPasien.setBounds(319, 10, 250, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        FormInput.add(TNoRM);
        TNoRM.setBounds(217, 10, 100, 23);

        jLabel8.setText("Tgl.Lahir :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(580, 10, 60, 23);

        TglLahir.setEditable(false);
        TglLahir.setHighlighter(null);
        TglLahir.setName("TglLahir"); // NOI18N
        FormInput.add(TglLahir);
        TglLahir.setBounds(644, 10, 80, 23);

        Jk.setEditable(false);
        Jk.setHighlighter(null);
        Jk.setName("Jk"); // NOI18N
        FormInput.add(Jk);
        Jk.setBounds(774, 10, 80, 23);

        jLabel10.setText("No.Rawat :");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(0, 10, 80, 23);

        label11.setText("Masuk Dirawat :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label11);
        label11.setBounds(0, 40, 104, 23);

        jLabel11.setText("J.K. :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(740, 10, 30, 23);

        HubunganDenganPasien.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Diri Sendiri", "Orang Tua", "Anak", "Saudara Kandung", "Teman", "Lain-lain" }));
        HubunganDenganPasien.setName("HubunganDenganPasien"); // NOI18N
        HubunganDenganPasien.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                HubunganDenganPasienItemStateChanged(evt);
            }
        });
        HubunganDenganPasien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HubunganDenganPasienKeyPressed(evt);
            }
        });
        FormInput.add(HubunganDenganPasien);
        HubunganDenganPasien.setBounds(179, 490, 140, 23);

        TglPernyataan.setForeground(new java.awt.Color(50, 70, 50));
        TglPernyataan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "27-10-2022" }));
        TglPernyataan.setDisplayFormat("dd-MM-yyyy");
        TglPernyataan.setName("TglPernyataan"); // NOI18N
        TglPernyataan.setOpaque(false);
        TglPernyataan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglPernyataanKeyPressed(evt);
            }
        });
        FormInput.add(TglPernyataan);
        TglPernyataan.setBounds(374, 40, 90, 23);

        jSeparator1.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator1.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator1.setName("jSeparator1"); // NOI18N
        FormInput.add(jSeparator1);
        jSeparator1.setBounds(0, 100, 880, 1);

        jLabel13.setText("Hubungan Dengan Pasien :");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(0, 490, 175, 23);

        AlamatPenerima.setHighlighter(null);
        AlamatPenerima.setName("AlamatPenerima"); // NOI18N
        AlamatPenerima.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlamatPenerimaKeyPressed(evt);
            }
        });
        FormInput.add(AlamatPenerima);
        AlamatPenerima.setBounds(554, 490, 300, 23);

        jLabel39.setText("Alamat :");
        jLabel39.setName("jLabel39"); // NOI18N
        FormInput.add(jLabel39);
        jLabel39.setBounds(460, 490, 90, 23);

        jSeparator3.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator3.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator3.setName("jSeparator3"); // NOI18N
        FormInput.add(jSeparator3);
        jSeparator3.setBounds(0, 580, 880, 1);

        jLabel101.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel101.setText("C. SAKSI-SAKSI");
        jLabel101.setName("jLabel101"); // NOI18N
        FormInput.add(jLabel101);
        jLabel101.setBounds(10, 580, 180, 23);

        label15.setText("Perawat/Petugas :");
        label15.setName("label15"); // NOI18N
        label15.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label15);
        label15.setBounds(0, 600, 130, 23);

        KdPetugas.setEditable(false);
        KdPetugas.setName("KdPetugas"); // NOI18N
        KdPetugas.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput.add(KdPetugas);
        KdPetugas.setBounds(134, 600, 100, 23);

        NmPetugas.setEditable(false);
        NmPetugas.setName("NmPetugas"); // NOI18N
        NmPetugas.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmPetugas);
        NmPetugas.setBounds(236, 600, 193, 23);

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
        BtnDokter.setBounds(431, 600, 28, 23);

        jLabel42.setText("Pasien/Keluarga :");
        jLabel42.setName("jLabel42"); // NOI18N
        FormInput.add(jLabel42);
        jLabel42.setBounds(460, 600, 110, 23);

        SaksiKeluarga.setHighlighter(null);
        SaksiKeluarga.setName("SaksiKeluarga"); // NOI18N
        SaksiKeluarga.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SaksiKeluargaKeyPressed(evt);
            }
        });
        FormInput.add(SaksiKeluarga);
        SaksiKeluarga.setBounds(574, 600, 280, 23);

        TNoRw1.setHighlighter(null);
        TNoRw1.setName("TNoRw1"); // NOI18N
        TNoRw1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRw1KeyPressed(evt);
            }
        });
        FormInput.add(TNoRw1);
        TNoRw1.setBounds(108, 40, 131, 23);

        label12.setText("Rencana Pemulangan :");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label12);
        label12.setBounds(250, 40, 120, 23);

        jLabel40.setText("Diagnosa Medis :");
        jLabel40.setName("jLabel40"); // NOI18N
        FormInput.add(jLabel40);
        jLabel40.setBounds(463, 40, 100, 23);

        AlamatPenerima1.setHighlighter(null);
        AlamatPenerima1.setName("AlamatPenerima1"); // NOI18N
        AlamatPenerima1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlamatPenerima1KeyPressed(evt);
            }
        });
        FormInput.add(AlamatPenerima1);
        AlamatPenerima1.setBounds(567, 40, 287, 23);

        jLabel41.setText("Alasan Masuk Rumah Sakit :");
        jLabel41.setName("jLabel41"); // NOI18N
        FormInput.add(jLabel41);
        jLabel41.setBounds(0, 70, 163, 23);

        AlamatPenerima2.setHighlighter(null);
        AlamatPenerima2.setName("AlamatPenerima2"); // NOI18N
        AlamatPenerima2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlamatPenerima2KeyPressed(evt);
            }
        });
        FormInput.add(AlamatPenerima2);
        AlamatPenerima2.setBounds(167, 70, 687, 23);

        scrollInput.setViewportView(FormInput);

        internalFrame2.add(scrollInput, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Input Perencanaan Pemulangan", internalFrame2);

        internalFrame3.setBorder(null);
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);
        Scroll.setPreferredSize(new java.awt.Dimension(452, 200));

        tbObat.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
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

        internalFrame3.add(Scroll, java.awt.BorderLayout.CENTER);

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel19.setText("Tgl.Asuhan :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "27-10-2022" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(DTPCari1);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("s.d.");
        jLabel21.setName("jLabel21"); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(23, 23));
        panelGlass9.add(jLabel21);

        DTPCari2.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "27-10-2022" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(DTPCari2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(195, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass9.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('3');
        BtnCari.setToolTipText("Alt+3");
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
        jLabel7.setPreferredSize(new java.awt.Dimension(60, 23));
        panelGlass9.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(LCount);

        internalFrame3.add(panelGlass9, java.awt.BorderLayout.PAGE_END);

        PanelAccor.setBackground(new java.awt.Color(255, 255, 255));
        PanelAccor.setName("PanelAccor"); // NOI18N
        PanelAccor.setPreferredSize(new java.awt.Dimension(430, 43));
        PanelAccor.setLayout(new java.awt.BorderLayout(1, 1));

        ChkAccor.setBackground(new java.awt.Color(255, 250, 248));
        ChkAccor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kiri.png"))); // NOI18N
        ChkAccor.setSelected(true);
        ChkAccor.setFocusable(false);
        ChkAccor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkAccor.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkAccor.setName("ChkAccor"); // NOI18N
        ChkAccor.setPreferredSize(new java.awt.Dimension(15, 20));
        ChkAccor.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kiri.png"))); // NOI18N
        ChkAccor.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kanan.png"))); // NOI18N
        ChkAccor.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kanan.png"))); // NOI18N
        ChkAccor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkAccorActionPerformed(evt);
            }
        });
        PanelAccor.add(ChkAccor, java.awt.BorderLayout.WEST);

        FormPhoto.setBackground(new java.awt.Color(255, 255, 255));
        FormPhoto.setBorder(null);
        FormPhoto.setName("FormPhoto"); // NOI18N
        FormPhoto.setPreferredSize(new java.awt.Dimension(115, 73));
        FormPhoto.setLayout(new java.awt.BorderLayout());

        FormPass3.setBackground(new java.awt.Color(255, 255, 255));
        FormPass3.setBorder(null);
        FormPass3.setName("FormPass3"); // NOI18N
        FormPass3.setPreferredSize(new java.awt.Dimension(115, 40));

        btnAmbil.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/plus_16.png"))); // NOI18N
        btnAmbil.setMnemonic('U');
        btnAmbil.setText("Ambil");
        btnAmbil.setToolTipText("Alt+U");
        btnAmbil.setName("btnAmbil"); // NOI18N
        btnAmbil.setPreferredSize(new java.awt.Dimension(100, 30));
        btnAmbil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAmbilActionPerformed(evt);
            }
        });
        FormPass3.add(btnAmbil);

        BtnRefreshPhoto1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/refresh.png"))); // NOI18N
        BtnRefreshPhoto1.setMnemonic('U');
        BtnRefreshPhoto1.setText("Refresh");
        BtnRefreshPhoto1.setToolTipText("Alt+U");
        BtnRefreshPhoto1.setName("BtnRefreshPhoto1"); // NOI18N
        BtnRefreshPhoto1.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnRefreshPhoto1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRefreshPhoto1ActionPerformed(evt);
            }
        });
        FormPass3.add(BtnRefreshPhoto1);

        FormPhoto.add(FormPass3, java.awt.BorderLayout.PAGE_END);

        TabData.setBackground(new java.awt.Color(254, 255, 254));
        TabData.setForeground(new java.awt.Color(50, 50, 50));
        TabData.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabData.setName("TabData"); // NOI18N
        TabData.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabDataMouseClicked(evt);
            }
        });

        Scroll5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll5.setName("Scroll5"); // NOI18N
        Scroll5.setOpaque(true);
        Scroll5.setPreferredSize(new java.awt.Dimension(200, 200));

        LoadHTML2.setBorder(null);
        LoadHTML2.setName("LoadHTML2"); // NOI18N
        Scroll5.setViewportView(LoadHTML2);

        TabData.addTab("Tanda Tangan Pembuat Pernyataan", Scroll5);

        Scroll6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll6.setName("Scroll6"); // NOI18N
        Scroll6.setOpaque(true);
        Scroll6.setPreferredSize(new java.awt.Dimension(200, 200));

        LoadHTML3.setBorder(null);
        LoadHTML3.setName("LoadHTML3"); // NOI18N
        Scroll6.setViewportView(LoadHTML3);

        TabData.addTab("Tanda Tangan Saksi I Keluarga", Scroll6);

        FormPhoto.add(TabData, java.awt.BorderLayout.CENTER);

        PanelAccor.add(FormPhoto, java.awt.BorderLayout.CENTER);

        internalFrame3.add(PanelAccor, java.awt.BorderLayout.EAST);

        TabRawat.addTab("Data Perencanaan Pemulangan", internalFrame3);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        /*if(TNoRM.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"Nama Pasien");
        }else if(NmDokter.getText().trim().equals("")){
            Valid.textKosong(BtnDokter,"Dokter");
        }else if(Diagnosa.getText().trim().equals("")){
            Valid.textKosong(Diagnosa,"Diagnosa");
        }else if(TindakanKedokteran.getText().trim().equals("")){
            Valid.textKosong(TindakanKedokteran,"Tindakan");
        }else if(PenerimaInformasi.getText().trim().equals("")){
            Valid.textKosong(PenerimaInformasi,"Penerima Informasi");
        }else if(NmPerawat.getText().trim().equals("")){
            Valid.textKosong(NmPerawat,"Saksi II Perawat");
        }else if(SaksiKeluarga.getText().trim().equals("")){
            Valid.textKosong(SaksiKeluarga,"Saksi I Keluarga");
        }else if(Biaya.getText().trim().equals("")){
            Valid.textKosong(Biaya,"Biaya");
        }else{
            if(Sequel.menyimpantf("persetujuan_penolakan_tindakan","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Pernyataan",37,new String[]{
                    NoPenyataan.getText(),TNoRw.getText(),Valid.SetTgl(TglPernyataan.getSelectedItem()+""),Diagnosa.getText(),"false",TindakanKedokteran.getText(),"false",
                    IndikasiTindakan.getText(),"false",TataCara.getText(),"false",Tujuan.getText(),"false",Risiko.getText(),"false",Komplikasi.getText(),"false",Prognosis.getText(), 
                    "false",AlternatifResiko.getText(),"false",Biaya.getText(),"false",LainLain.getText(),"false",KdDokter.getText(),KdPerawat.getText(),PenerimaInformasi.getText(),
                    AlasanDiwakilkan.getText(),JKPenerima.getSelectedItem().toString().substring(0,1),Valid.SetTgl(TglLahirPenerima.getSelectedItem()+""),UmurPenerima.getText(),
                    AlamatPenerima.getText(),NoHPPenerima.getText(),HubunganDenganPasien.getSelectedItem().toString(),"Belum Dikonfirmasi",SaksiKeluarga.getText()
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
            Valid.pindah(evt,SaksiKeluarga,BtnBatal);
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
        if(tbObat.getSelectedRow()>-1){
            if(akses.getkode().equals("Admin Utama")){
                hapus();
            }else{
                if(KdPetugas.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),29).toString())){
                    hapus();
                }else{
                    JOptionPane.showMessageDialog(null,"Hanya bisa dihapus oleh petugas yang bersangkutan..!!");
                }
            }
        }else{
            JOptionPane.showMessageDialog(rootPane,"Silahkan anda pilih data terlebih dahulu..!!");
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
        /*if(TNoRM.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"Nama Pasien");
        }else if(NmDokter.getText().trim().equals("")){
            Valid.textKosong(BtnDokter,"Dokter");
        }else if(Diagnosa.getText().trim().equals("")){
            Valid.textKosong(Diagnosa,"Diagnosa");
        }else if(TindakanKedokteran.getText().trim().equals("")){
            Valid.textKosong(TindakanKedokteran,"Tindakan");
        }else if(PenerimaInformasi.getText().trim().equals("")){
            Valid.textKosong(PenerimaInformasi,"Penerima Informasi");
        }else if(NmPerawat.getText().trim().equals("")){
            Valid.textKosong(NmPerawat,"Saksi II Perawat");
        }else if(SaksiKeluarga.getText().trim().equals("")){
            Valid.textKosong(SaksiKeluarga,"Saksi I Keluarga");
        }else if(Biaya.getText().trim().equals("")){
            Valid.textKosong(Biaya,"Biaya");
        }else{
            if(tbObat.getSelectedRow()>-1){
                if(akses.getkode().equals("Admin Utama")){
                    ganti();
                }else{
                    if(KdDokter.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),29).toString())){
                        ganti();
                    }else{
                        JOptionPane.showMessageDialog(null,"Hanya bisa diganti oleh dokter yang bersangkutan..!!");
                    }
                }
            }else{
                JOptionPane.showMessageDialog(rootPane,"Silahkan anda pilih data terlebih dahulu..!!");
            }
        }*/
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
            BtnKeluarActionPerformed(null);
        }else{Valid.pindah(evt,BtnEdit,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            try{
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
                
                if(TCari.getText().trim().equals("")){
                    ps=koneksi.prepareStatement(
                            "select persetujuan_penolakan_tindakan.no_pernyataan,reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,"+
                            "persetujuan_penolakan_tindakan.tanggal,persetujuan_penolakan_tindakan.diagnosa,persetujuan_penolakan_tindakan.diagnosa_konfirmasi,persetujuan_penolakan_tindakan.tindakan,"+
                            "persetujuan_penolakan_tindakan.tindakan_konfirmasi,persetujuan_penolakan_tindakan.indikasi_tindakan,persetujuan_penolakan_tindakan.indikasi_tindakan_konfirmasi,"+
                            "persetujuan_penolakan_tindakan.tata_cara,persetujuan_penolakan_tindakan.tata_cara_konfirmasi,persetujuan_penolakan_tindakan.tujuan,persetujuan_penolakan_tindakan.tujuan_konfirmasi,"+
                            "persetujuan_penolakan_tindakan.risiko,persetujuan_penolakan_tindakan.risiko_konfirmasi,persetujuan_penolakan_tindakan.komplikasi,persetujuan_penolakan_tindakan.komplikasi_konfirmasi,"+
                            "persetujuan_penolakan_tindakan.prognosis,persetujuan_penolakan_tindakan.prognosis_konfirmasi,persetujuan_penolakan_tindakan.alternatif_dan_risikonya,"+
                            "persetujuan_penolakan_tindakan.alternatif_konfirmasi,persetujuan_penolakan_tindakan.biaya,persetujuan_penolakan_tindakan.biaya_konfirmasi,persetujuan_penolakan_tindakan.lain_lain,"+
                            "persetujuan_penolakan_tindakan.lain_lain_konfirmasi,persetujuan_penolakan_tindakan.kd_dokter,dokter.nm_dokter,persetujuan_penolakan_tindakan.nip,petugas.nama,"+
                            "persetujuan_penolakan_tindakan.penerima_informasi,persetujuan_penolakan_tindakan.alasan_diwakilkan_penerima_informasi,persetujuan_penolakan_tindakan.jk_penerima_informasi,"+
                            "persetujuan_penolakan_tindakan.tanggal_lahir_penerima_informasi,persetujuan_penolakan_tindakan.umur_penerima_informasi,persetujuan_penolakan_tindakan.alamat_penerima_informasi,"+
                            "persetujuan_penolakan_tindakan.no_hp,persetujuan_penolakan_tindakan.hubungan_penerima_informasi,persetujuan_penolakan_tindakan.pernyataan,persetujuan_penolakan_tindakan.saksi_keluarga "+
                            "from persetujuan_penolakan_tindakan inner join reg_periksa on persetujuan_penolakan_tindakan.no_rawat=reg_periksa.no_rawat "+
                            "inner join pasien on pasien.no_rkm_medis=reg_periksa.no_rkm_medis "+
                            "inner join dokter on dokter.kd_dokter=persetujuan_penolakan_tindakan.kd_dokter "+
                            "inner join petugas on petugas.nip=persetujuan_penolakan_tindakan.nip where "+
                            "persetujuan_penolakan_tindakan.tanggal between ? and ? order by persetujuan_penolakan_tindakan.tanggal");
                }else{
                    ps=koneksi.prepareStatement(
                            "select persetujuan_penolakan_tindakan.no_pernyataan,reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,"+
                            "persetujuan_penolakan_tindakan.tanggal,persetujuan_penolakan_tindakan.diagnosa,persetujuan_penolakan_tindakan.diagnosa_konfirmasi,persetujuan_penolakan_tindakan.tindakan,"+
                            "persetujuan_penolakan_tindakan.tindakan_konfirmasi,persetujuan_penolakan_tindakan.indikasi_tindakan,persetujuan_penolakan_tindakan.indikasi_tindakan_konfirmasi,"+
                            "persetujuan_penolakan_tindakan.tata_cara,persetujuan_penolakan_tindakan.tata_cara_konfirmasi,persetujuan_penolakan_tindakan.tujuan,persetujuan_penolakan_tindakan.tujuan_konfirmasi,"+
                            "persetujuan_penolakan_tindakan.risiko,persetujuan_penolakan_tindakan.risiko_konfirmasi,persetujuan_penolakan_tindakan.komplikasi,persetujuan_penolakan_tindakan.komplikasi_konfirmasi,"+
                            "persetujuan_penolakan_tindakan.prognosis,persetujuan_penolakan_tindakan.prognosis_konfirmasi,persetujuan_penolakan_tindakan.alternatif_dan_risikonya,"+
                            "persetujuan_penolakan_tindakan.alternatif_konfirmasi,persetujuan_penolakan_tindakan.biaya,persetujuan_penolakan_tindakan.biaya_konfirmasi,persetujuan_penolakan_tindakan.lain_lain,"+
                            "persetujuan_penolakan_tindakan.lain_lain_konfirmasi,persetujuan_penolakan_tindakan.kd_dokter,dokter.nm_dokter,persetujuan_penolakan_tindakan.nip,petugas.nama,"+
                            "persetujuan_penolakan_tindakan.penerima_informasi,persetujuan_penolakan_tindakan.alasan_diwakilkan_penerima_informasi,persetujuan_penolakan_tindakan.jk_penerima_informasi,"+
                            "persetujuan_penolakan_tindakan.tanggal_lahir_penerima_informasi,persetujuan_penolakan_tindakan.umur_penerima_informasi,persetujuan_penolakan_tindakan.alamat_penerima_informasi,"+
                            "persetujuan_penolakan_tindakan.no_hp,persetujuan_penolakan_tindakan.hubungan_penerima_informasi,persetujuan_penolakan_tindakan.pernyataan,persetujuan_penolakan_tindakan.saksi_keluarga "+
                            "from persetujuan_penolakan_tindakan inner join reg_periksa on persetujuan_penolakan_tindakan.no_rawat=reg_periksa.no_rawat "+
                            "inner join pasien on pasien.no_rkm_medis=reg_periksa.no_rkm_medis "+
                            "inner join dokter on dokter.kd_dokter=persetujuan_penolakan_tindakan.kd_dokter "+
                            "inner join petugas on petugas.nip=persetujuan_penolakan_tindakan.nip where "+
                            "persetujuan_penolakan_tindakan.tanggal between ? and ? and (persetujuan_penolakan_tindakan.no_pernyataan like ? or reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or "+
                            "persetujuan_penolakan_tindakan.kd_dokter like ? or dokter.nm_dokter like ?) order by persetujuan_penolakan_tindakan.tanggal");
                }

                try {
                    if(TCari.getText().trim().equals("")){
                        ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                        ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                    }else{
                        ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                        ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                        ps.setString(3,"%"+TCari.getText()+"%");
                        ps.setString(4,"%"+TCari.getText()+"%");
                        ps.setString(5,"%"+TCari.getText()+"%");
                        ps.setString(6,"%"+TCari.getText()+"%");
                        ps.setString(7,"%"+TCari.getText()+"%");
                        ps.setString(8,"%"+TCari.getText()+"%");
                    } 
                    rs=ps.executeQuery();
                    pilihan = (String)JOptionPane.showInputDialog(null,"Silahkan pilih laporan..!","Pilihan Cetak",JOptionPane.QUESTION_MESSAGE,null,new Object[]{"Laporan 1 (HTML)","Laporan 2 (WPS)","Laporan 3 (CSV)"},"Laporan 1 (HTML)");
                    switch (pilihan) {
                        case "Laporan 1 (HTML)":
                                htmlContent = new StringBuilder();
                                htmlContent.append(                             
                                    "<tr class='isi'>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>No.Pernyataan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>No.Rawat</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>No.RM</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Nama Pasien</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Tgl.Lahir</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>J.K.</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Tanggal</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Diagnosa</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Ya/Tidak</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Tindakan Kedokteran</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Ya/Tidak</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Indikasi Tindakan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Ya/Tidak</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Tata Cara</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Ya/Tidak</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Tujuan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Ya/Tidak</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Risiko</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Ya/Tidak</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Komplikasi</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Ya/Tidak</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Prognosis</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Ya/Tidak</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Alternatif & Resikonya</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Ya/Tidak</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Lain-lain</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Ya/Tidak</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Biaya</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Ya/Tidak</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Kode Dokter</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Nama Dokter</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Nip</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Saksi II Perawat</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Penerima Informasi</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Alasan Jika Diwakilkan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>J.K. P.I</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Tgl.Lahir P.I.</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Umur P.I.</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Alamat Penerima Informasi</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>No.H.P. P.I</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Hubungan Dengan Pasien</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Pernyataan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Saksi I Keluarga</td>"+
                                    "</tr>"
                                );
                                while(rs.next()){
                                    htmlContent.append(
                                        "<tr class='isi'>"+
                                            "<td valign='top'>"+rs.getString("no_pernyataan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("no_rawat")+"</td>"+
                                            "<td valign='top'>"+rs.getString("no_rkm_medis")+"</td>"+
                                            "<td valign='top'>"+rs.getString("nm_pasien")+"</td>"+
                                            "<td valign='top'>"+rs.getString("tgl_lahir")+"</td>"+
                                            "<td valign='top'>"+rs.getString("jk")+"</td>"+
                                            "<td valign='top'>"+rs.getString("tanggal")+"</td>"+
                                            "<td valign='top'>"+rs.getString("diagnosa")+"</td>"+
                                            "<td valign='top' align='center'><input type=checkbox "+rs.getString("diagnosa_konfirmasi").replaceAll("true","checked").replaceAll("false","")+"></td>"+
                                            "<td valign='top'>"+rs.getString("tindakan")+"</td>"+
                                            "<td valign='top' align='center'><input type=checkbox "+rs.getString("tindakan_konfirmasi").replaceAll("true","checked").replaceAll("false","")+"></td>"+
                                            "<td valign='top'>"+rs.getString("indikasi_tindakan")+"</td>"+
                                            "<td valign='top' align='center'><input type=checkbox "+rs.getString("indikasi_tindakan_konfirmasi").replaceAll("true","checked").replaceAll("false","")+"></td>"+
                                            "<td valign='top'>"+rs.getString("tata_cara")+"</td>"+
                                            "<td valign='top' align='center'><input type=checkbox "+rs.getString("tata_cara_konfirmasi").replaceAll("true","checked").replaceAll("false","")+"></td>"+
                                            "<td valign='top'>"+rs.getString("tujuan")+"</td>"+
                                            "<td valign='top' align='center'><input type=checkbox "+rs.getString("tujuan_konfirmasi").replaceAll("true","checked").replaceAll("false","")+"></td>"+
                                            "<td valign='top'>"+rs.getString("risiko")+"</td>"+
                                            "<td valign='top' align='center'><input type=checkbox "+rs.getString("risiko_konfirmasi").replaceAll("true","checked").replaceAll("false","")+"></td>"+
                                            "<td valign='top'>"+rs.getString("komplikasi")+"</td>"+
                                            "<td valign='top' align='center'><input type=checkbox "+rs.getString("komplikasi_konfirmasi").replaceAll("true","checked").replaceAll("false","")+"></td>"+
                                            "<td valign='top'>"+rs.getString("prognosis")+"</td>"+
                                            "<td valign='top' align='center'><input type=checkbox "+rs.getString("prognosis_konfirmasi").replaceAll("true","checked").replaceAll("false","")+"></td>"+
                                            "<td valign='top'>"+rs.getString("alternatif_dan_risikonya")+"</td>"+
                                            "<td valign='top' align='center'><input type=checkbox "+rs.getString("alternatif_konfirmasi").replaceAll("true","checked").replaceAll("false","")+"></td>"+
                                            "<td valign='top'>"+rs.getString("lain_lain")+"</td>"+
                                            "<td valign='top' align='center'><input type=checkbox "+rs.getString("lain_lain_konfirmasi").replaceAll("true","checked").replaceAll("false","")+"></td>"+
                                            "<td valign='top'>"+rs.getString("biaya")+"</td>"+
                                            "<td valign='top' align='center'><input type=checkbox "+rs.getString("biaya_konfirmasi").replaceAll("true","checked").replaceAll("false","")+"></td>"+
                                            "<td valign='top'>"+rs.getString("kd_dokter")+"</td>"+
                                            "<td valign='top'>"+rs.getString("nm_dokter")+"</td>"+
                                            "<td valign='top'>"+rs.getString("nip")+"</td>"+
                                            "<td valign='top'>"+rs.getString("nama")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penerima_informasi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("alasan_diwakilkan_penerima_informasi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("jk_penerima_informasi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("tanggal_lahir_penerima_informasi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("umur_penerima_informasi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("alamat_penerima_informasi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("no_hp")+"</td>"+
                                            "<td valign='top'>"+rs.getString("hubungan_penerima_informasi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pernyataan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("saksi_keluarga")+"</td>"+ 
                                        "</tr>"
                                    );
                                }
                                f = new File("PersetujuanPenolakanTindakan.html");            
                                bw = new BufferedWriter(new FileWriter(f));            
                                bw.write("<html>"+
                                            "<head><link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" /></head>"+
                                            "<body>"+
                                                "<table width='3600px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
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
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>No.Pernyataan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>No.Rawat</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>No.RM</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Nama Pasien</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Tgl.Lahir</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>J.K.</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Tanggal</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Diagnosa</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Ya/Tidak</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Tindakan Kedokteran</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Ya/Tidak</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Indikasi Tindakan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Ya/Tidak</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Tata Cara</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Ya/Tidak</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Tujuan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Ya/Tidak</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Risiko</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Ya/Tidak</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Komplikasi</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Ya/Tidak</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Prognosis</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Ya/Tidak</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Alternatif & Resikonya</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Ya/Tidak</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Lain-lain</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Ya/Tidak</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Biaya</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Ya/Tidak</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Kode Dokter</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Nama Dokter</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Nip</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Saksi II Perawat</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Penerima Informasi</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Alasan Jika Diwakilkan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>J.K. P.I</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Tgl.Lahir P.I.</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Umur P.I.</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Alamat Penerima Informasi</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>No.H.P. P.I</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Hubungan Dengan Pasien</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Pernyataan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Saksi I Keluarga</td>"+
                                    "</tr>"
                                );
                                while(rs.next()){
                                    htmlContent.append(
                                        "<tr class='isi'>"+
                                            "<td valign='top'>"+rs.getString("no_pernyataan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("no_rawat")+"</td>"+
                                            "<td valign='top'>"+rs.getString("no_rkm_medis")+"</td>"+
                                            "<td valign='top'>"+rs.getString("nm_pasien")+"</td>"+
                                            "<td valign='top'>"+rs.getString("tgl_lahir")+"</td>"+
                                            "<td valign='top'>"+rs.getString("jk")+"</td>"+
                                            "<td valign='top'>"+rs.getString("tanggal")+"</td>"+
                                            "<td valign='top'>"+rs.getString("diagnosa")+"</td>"+
                                            "<td valign='top' align='center'><input type=checkbox "+rs.getString("diagnosa_konfirmasi").replaceAll("true","checked").replaceAll("false","")+"></td>"+
                                            "<td valign='top'>"+rs.getString("tindakan")+"</td>"+
                                            "<td valign='top' align='center'><input type=checkbox "+rs.getString("tindakan_konfirmasi").replaceAll("true","checked").replaceAll("false","")+"></td>"+
                                            "<td valign='top'>"+rs.getString("indikasi_tindakan")+"</td>"+
                                            "<td valign='top' align='center'><input type=checkbox "+rs.getString("indikasi_tindakan_konfirmasi").replaceAll("true","checked").replaceAll("false","")+"></td>"+
                                            "<td valign='top'>"+rs.getString("tata_cara")+"</td>"+
                                            "<td valign='top' align='center'><input type=checkbox "+rs.getString("tata_cara_konfirmasi").replaceAll("true","checked").replaceAll("false","")+"></td>"+
                                            "<td valign='top'>"+rs.getString("tujuan")+"</td>"+
                                            "<td valign='top' align='center'><input type=checkbox "+rs.getString("tujuan_konfirmasi").replaceAll("true","checked").replaceAll("false","")+"></td>"+
                                            "<td valign='top'>"+rs.getString("risiko")+"</td>"+
                                            "<td valign='top' align='center'><input type=checkbox "+rs.getString("risiko_konfirmasi").replaceAll("true","checked").replaceAll("false","")+"></td>"+
                                            "<td valign='top'>"+rs.getString("komplikasi")+"</td>"+
                                            "<td valign='top' align='center'><input type=checkbox "+rs.getString("komplikasi_konfirmasi").replaceAll("true","checked").replaceAll("false","")+"></td>"+
                                            "<td valign='top'>"+rs.getString("prognosis")+"</td>"+
                                            "<td valign='top' align='center'><input type=checkbox "+rs.getString("prognosis_konfirmasi").replaceAll("true","checked").replaceAll("false","")+"></td>"+
                                            "<td valign='top'>"+rs.getString("alternatif_dan_risikonya")+"</td>"+
                                            "<td valign='top' align='center'><input type=checkbox "+rs.getString("alternatif_konfirmasi").replaceAll("true","checked").replaceAll("false","")+"></td>"+
                                            "<td valign='top'>"+rs.getString("lain_lain")+"</td>"+
                                            "<td valign='top' align='center'><input type=checkbox "+rs.getString("lain_lain_konfirmasi").replaceAll("true","checked").replaceAll("false","")+"></td>"+
                                            "<td valign='top'>"+rs.getString("biaya")+"</td>"+
                                            "<td valign='top' align='center'><input type=checkbox "+rs.getString("biaya_konfirmasi").replaceAll("true","checked").replaceAll("false","")+"></td>"+
                                            "<td valign='top'>"+rs.getString("kd_dokter")+"</td>"+
                                            "<td valign='top'>"+rs.getString("nm_dokter")+"</td>"+
                                            "<td valign='top'>"+rs.getString("nip")+"</td>"+
                                            "<td valign='top'>"+rs.getString("nama")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penerima_informasi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("alasan_diwakilkan_penerima_informasi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("jk_penerima_informasi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("tanggal_lahir_penerima_informasi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("umur_penerima_informasi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("alamat_penerima_informasi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("no_hp")+"</td>"+
                                            "<td valign='top'>"+rs.getString("hubungan_penerima_informasi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pernyataan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("saksi_keluarga")+"</td>"+ 
                                        "</tr>"
                                    );
                                }
                                f = new File("PersetujuanPenolakanTindakan.wps");            
                                bw = new BufferedWriter(new FileWriter(f));            
                                bw.write("<html>"+
                                            "<head><link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" /></head>"+
                                            "<body>"+
                                                "<table width='3600px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
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
                                    "\"No.Pernyataan\";\"No.Rawat\";\"No.RM\";\"Nama Pasien\";\"Tgl.Lahir\";\"J.K.\";\"Tanggal\";\"Diagnosa\";\"Ya/Tidak\";\"Tindakan Kedokteran\";\"Ya/Tidak\";\"Indikasi Tindakan\";\"Ya/Tidak\";\"Tata Cara\";\"Ya/Tidak\";\"Tujuan\";\"Ya/Tidak\";\"Risiko\";\"Ya/Tidak\";\"Komplikasi\";\"Ya/Tidak\";\"Prognosis\";\"Ya/Tidak\";\"Alternatif & Resikonya\";\"Ya/Tidak\";\"Lain-lain\";\"Ya/Tidak\";\"Biaya\";\"Ya/Tidak\";\"Kode Dokter\";\"Nama Dokter\";\"Nip\";\"Saksi II Perawat\";\"Penerima Informasi\";\"Alasan Jika Diwakilkan\";\"J.K. P.I\";\"Tgl.Lahir P.I.\";\"Umur P.I.\";\"Alamat Penerima Informasi\";\"No.H.P. P.I\";\"Hubungan Dengan Pasien\";\"Pernyataan\";\"Saksi I Keluarga\"\n"
                                ); 
                                while(rs.next()){
                                    htmlContent.append(
                                        "\""+rs.getString("no_pernyataan")+"\";\""+rs.getString("no_rawat")+"\";\" "+rs.getString("no_rkm_medis")+"\";\""+rs.getString("nm_pasien")+"\";\""+rs.getString("tgl_lahir")+"\";\""+rs.getString("jk")+"\";\""+rs.getString("tanggal")+"\";\""+rs.getString("diagnosa")+"\";\""+rs.getString("diagnosa_konfirmasi").replaceAll("true","Ya").replaceAll("false","Tidak")+"\";\""+rs.getString("tindakan")+"\";\""+rs.getString("tindakan_konfirmasi").replaceAll("true","Ya").replaceAll("false","Tidak")+"\";\""+rs.getString("indikasi_tindakan")+"\";\""+rs.getString("indikasi_tindakan_konfirmasi").replaceAll("true","Ya").replaceAll("false","Tidak")+"\";\""+rs.getString("tata_cara")+"\";\""+rs.getString("tata_cara_konfirmasi").replaceAll("true","Ya").replaceAll("false","Tidak")+"\";\""+rs.getString("tujuan")+"\";\""+rs.getString("tujuan_konfirmasi").replaceAll("true","Ya").replaceAll("false","Tidak")+"\";\""+rs.getString("risiko")+"\";\""+rs.getString("risiko_konfirmasi").replaceAll("true","Ya").replaceAll("false","Tidak")+"\";\""+rs.getString("komplikasi")+"\";\""+rs.getString("komplikasi_konfirmasi").replaceAll("true","Ya").replaceAll("false","Tidak")+"\";\""+rs.getString("prognosis")+"\";\""+rs.getString("prognosis_konfirmasi").replaceAll("true","Ya").replaceAll("false","Tidak")+"\";\""+rs.getString("alternatif_dan_risikonya")+"\";\""+rs.getString("alternatif_konfirmasi").replaceAll("true","Ya").replaceAll("false","Tidak")+"\";\""+rs.getString("lain_lain")+"\";\""+rs.getString("lain_lain_konfirmasi").replaceAll("true","Ya").replaceAll("false","Tidak")+"\";\""+rs.getString("biaya")+"\";\""+rs.getString("biaya_konfirmasi").replaceAll("true","Ya").replaceAll("false","Tidak")+"\";\""+rs.getString("kd_dokter")+"\";\""+rs.getString("nm_dokter")+"\";\""+rs.getString("nip")+"\";\""+rs.getString("nama")+"\";\""+rs.getString("penerima_informasi")+"\";\""+rs.getString("alasan_diwakilkan_penerima_informasi")+"\";\""+rs.getString("jk_penerima_informasi")+"\";\""+rs.getString("tanggal_lahir_penerima_informasi")+"\";\""+rs.getString("umur_penerima_informasi")+"\";\""+rs.getString("alamat_penerima_informasi")+"\";\""+rs.getString("no_hp")+"\";\""+rs.getString("hubungan_penerima_informasi")+"\";\""+rs.getString("pernyataan")+"\";\""+rs.getString("saksi_keluarga")+"\"\n"
                                    );
                                }
                                f = new File("PersetujuanPenolakanTindakan.csv");            
                                bw = new BufferedWriter(new FileWriter(f));            
                                bw.write(htmlContent.toString());

                                bw.close();                         
                                Desktop.getDesktop().browse(f.toURI());
                            break; 
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
            TCari.setText("");
            tampil();
        }else{
            Valid.pindah(evt, BtnCari, TPasien);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void tbObatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbObatMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                isPhoto();
                panggilPhoto();
            } catch (java.lang.NullPointerException e) {
            }
            if((evt.getClickCount()==2)&&(tbObat.getSelectedColumn()==0)){
                TabRawat.setSelectedIndex(0);
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
            }else if(evt.getKeyCode()==KeyEvent.VK_SPACE){
                try {
                    getData();
                    TabRawat.setSelectedIndex(0);
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
}//GEN-LAST:event_tbObatKeyPressed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if(TabRawat.getSelectedIndex()==1){
            tampil();
        }
    }//GEN-LAST:event_TabRawatMouseClicked

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isRawat();
        }else{
            //Valid.pindah(evt,TCari,BtnDokter);
        }
    }//GEN-LAST:event_TNoRwKeyPressed

    private void TglPernyataanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglPernyataanKeyPressed
        //Valid.pindah(evt,BtnDokter,HubunganDenganPasien);
    }//GEN-LAST:event_TglPernyataanKeyPressed

    private void BtnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokterActionPerformed
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setAlwaysOnTop(false);
        petugas.setVisible(true);
    }//GEN-LAST:event_BtnDokterActionPerformed

    private void BtnDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokterKeyPressed
        //Valid.pindah(evt,UmurPenerima,SaksiKeluarga);
    }//GEN-LAST:event_BtnDokterKeyPressed

    private void SaksiKeluargaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SaksiKeluargaKeyPressed
        Valid.pindah(evt,BtnDokter,BtnSimpan);
    }//GEN-LAST:event_SaksiKeluargaKeyPressed

    private void ChkAccorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkAccorActionPerformed
        if(tbObat.getSelectedRow()!= -1){
            isPhoto();
            TabDataMouseClicked(null);
        }else{
            ChkAccor.setSelected(false);
            JOptionPane.showMessageDialog(null,"Silahkan pilih No.Pernyataan..!!!");
        }
    }//GEN-LAST:event_ChkAccorActionPerformed

    private void btnAmbilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAmbilActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        }else{
            if(tbObat.getSelectedRow()>-1){
                if(tbObat.getValueAt(tbObat.getSelectedRow(),41).toString().equals("Belum Dikonfirmasi")){
                    Sequel.queryu("delete from antripersetujuan");
                    Sequel.queryu("insert into antripersetujuan values('"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"','"+tbObat.getValueAt(tbObat.getSelectedRow(),1).toString()+"')");
                    Sequel.queryu("delete from bukti_persetujuan_penolakan_tindakan_penerimainformasi where no_pernyataan='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'");
                    Sequel.queryu("delete from bukti_persetujuan_penolakan_tindakan_saksikeluarga where no_pernyataan='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'");
                }else{
                    JOptionPane.showMessageDialog(rootPane,"Sudah terkonfirmasi oleh pasien/keluarga/pendamping..!!");
                }
            }else{
                JOptionPane.showMessageDialog(rootPane,"Silahkan anda pilih No.Pernyataan terlebih dahulu..!!");
            }   
        }
    }//GEN-LAST:event_btnAmbilActionPerformed

    private void BtnRefreshPhoto1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRefreshPhoto1ActionPerformed
        if(tbObat.getSelectedRow()>-1){
            panggilPhoto();
        }else{
            JOptionPane.showMessageDialog(rootPane,"Silahkan anda pilih No.Pernyataan terlebih dahulu..!!");
        }
    }//GEN-LAST:event_BtnRefreshPhoto1ActionPerformed

    private void TabDataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabDataMouseClicked
        if(tbObat.getSelectedRow()>-1){
            panggilPhoto();
        }else{
            JOptionPane.showMessageDialog(rootPane,"Silahkan anda pilih No.Pernyataan terlebih dahulu..!!");
        }
    }//GEN-LAST:event_TabDataMouseClicked

    private void AlamatPenerimaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlamatPenerimaKeyPressed
        //Valid.pindah(evt,PenerimaInformasi,NoHPPenerima);
    }//GEN-LAST:event_AlamatPenerimaKeyPressed

    private void HubunganDenganPasienKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HubunganDenganPasienKeyPressed
        //Valid.pindah(evt,Biaya,AlasanDiwakilkan);
    }//GEN-LAST:event_HubunganDenganPasienKeyPressed

    private void HubunganDenganPasienItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_HubunganDenganPasienItemStateChanged
        /*if(HubunganDenganPasien.getSelectedIndex()==0){
            try {
                ps=koneksi.prepareStatement("select concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) asal,"+
                    "TIMESTAMPDIFF(YEAR, pasien.tgl_lahir, CURDATE()) as tahun,pasien.tgl_lahir,pasien.no_tlp,pasien.umur "+
                    "from pasien inner join kelurahan on pasien.kd_kel=kelurahan.kd_kel "+
                    "inner join kecamatan on pasien.kd_kec=kecamatan.kd_kec "+
                    "inner join kabupaten on pasien.kd_kab=kabupaten.kd_kab "+
                    "inner join penjab on pasien.kd_pj=penjab.kd_pj "+
                    "where pasien.no_rkm_medis=?");
                try {
                    ps.setString(1,TNoRM.getText());
                    rs=ps.executeQuery();
                    while(rs.next()){
                        PenerimaInformasi.setText(TPasien.getText());
                        AlamatPenerima.setText(rs.getString("asal"));
                        TglLahirPenerima.setDate(rs.getDate("tgl_lahir"));
                        NoHPPenerima.setText(rs.getString("no_tlp"));
                        JKPenerima.setSelectedItem(Jk.getText());
                        UmurPenerima.setText(rs.getString("umur"));
                    }
                } catch (Exception ex) {
                    System.out.println(ex);
                }finally{
                    if(rs != null ){
                        rs.close();
                    }

                    if(ps != null ){
                        ps.close();
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }else{
            PenerimaInformasi.setText("");
            AlamatPenerima.setText("");
            TglLahirPenerima.setDate(new Date());
            NoHPPenerima.setText("");
            JKPenerima.setSelectedIndex(0);
            UmurPenerima.setText("");
        }*/
    }//GEN-LAST:event_HubunganDenganPasienItemStateChanged

    private void TNoRw1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRw1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TNoRw1KeyPressed

    private void AlamatPenerima1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlamatPenerima1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_AlamatPenerima1KeyPressed

    private void AlamatPenerima2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlamatPenerima2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_AlamatPenerima2KeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMPerencanaanPemulangan dialog = new RMPerencanaanPemulangan(new javax.swing.JFrame(), true);
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
    private widget.TextBox AlamatPenerima;
    private widget.TextBox AlamatPenerima1;
    private widget.TextBox AlamatPenerima2;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnDokter;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnRefreshPhoto1;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkAccor;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.PanelBiasa FormInput;
    private widget.PanelBiasa FormPass3;
    private widget.PanelBiasa FormPhoto;
    private widget.ComboBox HubunganDenganPasien;
    private widget.TextBox Jk;
    private widget.TextBox KdPetugas;
    private widget.Label LCount;
    private widget.editorpane LoadHTML2;
    private widget.editorpane LoadHTML3;
    private widget.TextBox NmPetugas;
    private widget.PanelBiasa PanelAccor;
    private widget.TextBox SaksiKeluarga;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll5;
    private widget.ScrollPane Scroll6;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TNoRw1;
    private widget.TextBox TPasien;
    private javax.swing.JTabbedPane TabData;
    private javax.swing.JTabbedPane TabRawat;
    private widget.TextBox TglLahir;
    private widget.Tanggal TglPernyataan;
    private widget.Button btnAmbil;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.Label jLabel10;
    private widget.Label jLabel101;
    private widget.Label jLabel11;
    private widget.Label jLabel13;
    private widget.Label jLabel19;
    private widget.Label jLabel21;
    private widget.Label jLabel39;
    private widget.Label jLabel40;
    private widget.Label jLabel41;
    private widget.Label jLabel42;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator3;
    private widget.Label label11;
    private widget.Label label12;
    private widget.Label label15;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollInput;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            if(TCari.getText().trim().equals("")){
                ps=koneksi.prepareStatement(
                        "select persetujuan_penolakan_tindakan.no_pernyataan,reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,"+
                        "persetujuan_penolakan_tindakan.tanggal,persetujuan_penolakan_tindakan.diagnosa,persetujuan_penolakan_tindakan.diagnosa_konfirmasi,persetujuan_penolakan_tindakan.tindakan,"+
                        "persetujuan_penolakan_tindakan.tindakan_konfirmasi,persetujuan_penolakan_tindakan.indikasi_tindakan,persetujuan_penolakan_tindakan.indikasi_tindakan_konfirmasi,"+
                        "persetujuan_penolakan_tindakan.tata_cara,persetujuan_penolakan_tindakan.tata_cara_konfirmasi,persetujuan_penolakan_tindakan.tujuan,persetujuan_penolakan_tindakan.tujuan_konfirmasi,"+
                        "persetujuan_penolakan_tindakan.risiko,persetujuan_penolakan_tindakan.risiko_konfirmasi,persetujuan_penolakan_tindakan.komplikasi,persetujuan_penolakan_tindakan.komplikasi_konfirmasi,"+
                        "persetujuan_penolakan_tindakan.prognosis,persetujuan_penolakan_tindakan.prognosis_konfirmasi,persetujuan_penolakan_tindakan.alternatif_dan_risikonya,"+
                        "persetujuan_penolakan_tindakan.alternatif_konfirmasi,persetujuan_penolakan_tindakan.biaya,persetujuan_penolakan_tindakan.biaya_konfirmasi,persetujuan_penolakan_tindakan.lain_lain,"+
                        "persetujuan_penolakan_tindakan.lain_lain_konfirmasi,persetujuan_penolakan_tindakan.kd_dokter,dokter.nm_dokter,persetujuan_penolakan_tindakan.nip,petugas.nama,"+
                        "persetujuan_penolakan_tindakan.penerima_informasi,persetujuan_penolakan_tindakan.alasan_diwakilkan_penerima_informasi,persetujuan_penolakan_tindakan.jk_penerima_informasi,"+
                        "persetujuan_penolakan_tindakan.tanggal_lahir_penerima_informasi,persetujuan_penolakan_tindakan.umur_penerima_informasi,persetujuan_penolakan_tindakan.alamat_penerima_informasi,"+
                        "persetujuan_penolakan_tindakan.no_hp,persetujuan_penolakan_tindakan.hubungan_penerima_informasi,persetujuan_penolakan_tindakan.pernyataan,persetujuan_penolakan_tindakan.saksi_keluarga "+
                        "from persetujuan_penolakan_tindakan inner join reg_periksa on persetujuan_penolakan_tindakan.no_rawat=reg_periksa.no_rawat "+
                        "inner join pasien on pasien.no_rkm_medis=reg_periksa.no_rkm_medis "+
                        "inner join dokter on dokter.kd_dokter=persetujuan_penolakan_tindakan.kd_dokter "+
                        "inner join petugas on petugas.nip=persetujuan_penolakan_tindakan.nip where "+
                        "persetujuan_penolakan_tindakan.tanggal between ? and ? order by persetujuan_penolakan_tindakan.tanggal");
            }else{
                ps=koneksi.prepareStatement(
                        "select persetujuan_penolakan_tindakan.no_pernyataan,reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,"+
                        "persetujuan_penolakan_tindakan.tanggal,persetujuan_penolakan_tindakan.diagnosa,persetujuan_penolakan_tindakan.diagnosa_konfirmasi,persetujuan_penolakan_tindakan.tindakan,"+
                        "persetujuan_penolakan_tindakan.tindakan_konfirmasi,persetujuan_penolakan_tindakan.indikasi_tindakan,persetujuan_penolakan_tindakan.indikasi_tindakan_konfirmasi,"+
                        "persetujuan_penolakan_tindakan.tata_cara,persetujuan_penolakan_tindakan.tata_cara_konfirmasi,persetujuan_penolakan_tindakan.tujuan,persetujuan_penolakan_tindakan.tujuan_konfirmasi,"+
                        "persetujuan_penolakan_tindakan.risiko,persetujuan_penolakan_tindakan.risiko_konfirmasi,persetujuan_penolakan_tindakan.komplikasi,persetujuan_penolakan_tindakan.komplikasi_konfirmasi,"+
                        "persetujuan_penolakan_tindakan.prognosis,persetujuan_penolakan_tindakan.prognosis_konfirmasi,persetujuan_penolakan_tindakan.alternatif_dan_risikonya,"+
                        "persetujuan_penolakan_tindakan.alternatif_konfirmasi,persetujuan_penolakan_tindakan.biaya,persetujuan_penolakan_tindakan.biaya_konfirmasi,persetujuan_penolakan_tindakan.lain_lain,"+
                        "persetujuan_penolakan_tindakan.lain_lain_konfirmasi,persetujuan_penolakan_tindakan.kd_dokter,dokter.nm_dokter,persetujuan_penolakan_tindakan.nip,petugas.nama,"+
                        "persetujuan_penolakan_tindakan.penerima_informasi,persetujuan_penolakan_tindakan.alasan_diwakilkan_penerima_informasi,persetujuan_penolakan_tindakan.jk_penerima_informasi,"+
                        "persetujuan_penolakan_tindakan.tanggal_lahir_penerima_informasi,persetujuan_penolakan_tindakan.umur_penerima_informasi,persetujuan_penolakan_tindakan.alamat_penerima_informasi,"+
                        "persetujuan_penolakan_tindakan.no_hp,persetujuan_penolakan_tindakan.hubungan_penerima_informasi,persetujuan_penolakan_tindakan.pernyataan,persetujuan_penolakan_tindakan.saksi_keluarga "+
                        "from persetujuan_penolakan_tindakan inner join reg_periksa on persetujuan_penolakan_tindakan.no_rawat=reg_periksa.no_rawat "+
                        "inner join pasien on pasien.no_rkm_medis=reg_periksa.no_rkm_medis "+
                        "inner join dokter on dokter.kd_dokter=persetujuan_penolakan_tindakan.kd_dokter "+
                        "inner join petugas on petugas.nip=persetujuan_penolakan_tindakan.nip where "+
                        "persetujuan_penolakan_tindakan.tanggal between ? and ? and (persetujuan_penolakan_tindakan.no_pernyataan like ? or reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or "+
                        "persetujuan_penolakan_tindakan.kd_dokter like ? or dokter.nm_dokter like ?) order by persetujuan_penolakan_tindakan.tanggal");
            }
                
            try {
                if(TCari.getText().trim().equals("")){
                    ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                    ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                }else{
                    ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                    ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                    ps.setString(3,"%"+TCari.getText()+"%");
                    ps.setString(4,"%"+TCari.getText()+"%");
                    ps.setString(5,"%"+TCari.getText()+"%");
                    ps.setString(6,"%"+TCari.getText()+"%");
                    ps.setString(7,"%"+TCari.getText()+"%");
                    ps.setString(8,"%"+TCari.getText()+"%");
                }   
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new Object[]{
                        rs.getString("no_pernyataan"),rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("tgl_lahir"),rs.getString("jk"),rs.getString("tanggal"),
                        rs.getString("diagnosa"),rs.getBoolean("diagnosa_konfirmasi"),rs.getString("tindakan"),rs.getBoolean("tindakan_konfirmasi"),rs.getString("indikasi_tindakan"),
                        rs.getBoolean("indikasi_tindakan_konfirmasi"),rs.getString("tata_cara"),rs.getBoolean("tata_cara_konfirmasi"),rs.getString("tujuan"),rs.getBoolean("tujuan_konfirmasi"),
                        rs.getString("risiko"),rs.getBoolean("risiko_konfirmasi"),rs.getString("komplikasi"),rs.getBoolean("komplikasi_konfirmasi"),rs.getString("prognosis"),rs.getBoolean("prognosis_konfirmasi"),
                        rs.getString("alternatif_dan_risikonya"),rs.getBoolean("alternatif_konfirmasi"),rs.getString("lain_lain"),rs.getBoolean("lain_lain_konfirmasi"),
                        rs.getString("biaya"),rs.getBoolean("biaya_konfirmasi"),rs.getString("kd_dokter"),rs.getString("nm_dokter"),rs.getString("nip"),rs.getString("nama"),
                        rs.getString("penerima_informasi"),rs.getString("alasan_diwakilkan_penerima_informasi"),rs.getString("jk_penerima_informasi"),rs.getString("tanggal_lahir_penerima_informasi"),
                        rs.getString("umur_penerima_informasi"),rs.getString("alamat_penerima_informasi"),rs.getString("no_hp"),rs.getString("hubungan_penerima_informasi"),rs.getString("pernyataan"),
                        rs.getString("saksi_keluarga")                  
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
        HubunganDenganPasien.setSelectedIndex(0);
        TglPernyataan.setDate(new Date());
        TabRawat.setSelectedIndex(0);
        KdPetugas.setText("");
        NmPetugas.setText("");
        SaksiKeluarga.setText("");
        TabRawat.setSelectedIndex(0);
    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString()); 
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString());
            Jk.setText(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString()); 
        }
    }

    private void isRawat() {
        try {
            ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rkm_medis,pasien.nm_pasien, if(pasien.jk='L','LAKI-LAKI','PEREMPUAN') as jk,pasien.tgl_lahir,reg_periksa.tgl_registrasi, "+
                    "concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) asal,TIMESTAMPDIFF(YEAR, pasien.tgl_lahir, CURDATE()) as tahun,"+
                    "pasien.no_tlp,pasien.umur from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join kelurahan on pasien.kd_kel=kelurahan.kd_kel "+
                    "inner join kecamatan on pasien.kd_kec=kecamatan.kd_kec "+
                    "inner join kabupaten on pasien.kd_kab=kabupaten.kd_kab "+
                    "where reg_periksa.no_rawat=?");
            try {
                ps.setString(1,TNoRw.getText());
                rs=ps.executeQuery();
                if(rs.next()){
                    TNoRM.setText(rs.getString("no_rkm_medis"));
                    DTPCari1.setDate(rs.getDate("tgl_registrasi"));
                    TPasien.setText(rs.getString("nm_pasien"));
                    Jk.setText(rs.getString("jk"));
                    TglLahir.setText(rs.getString("tgl_lahir"));
                    AlamatPenerima.setText(rs.getString("asal"));
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
        } catch (Exception e) {
            System.out.println("Notif : "+e);
        }
    }
 
    public void setNoRm(String norwt,Date tgl2) {
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        DTPCari2.setDate(tgl2);    
        isRawat(); 
    }
    
    public void isCek(){
        BtnSimpan.setEnabled(akses.getperencanaan_pemulangan());
        BtnHapus.setEnabled(akses.getperencanaan_pemulangan());
        BtnEdit.setEnabled(akses.getperencanaan_pemulangan());
        BtnEdit.setEnabled(akses.getperencanaan_pemulangan());
        if(akses.getjml2()>=1){
            KdPetugas.setEditable(false);
            BtnDokter.setEnabled(false);
            KdPetugas.setText(akses.getkode());
            Sequel.cariIsi("select petugas.nama from petugas where petugas.nip=?", NmPetugas,KdPetugas.getText());
            if(NmPetugas.getText().equals("")){
                KdPetugas.setText("");
                JOptionPane.showMessageDialog(null,"User login bukan petugas...!!");
            }
        }            
    }
    
    public void setTampil(){
       TabRawat.setSelectedIndex(1);
       tampil();
    }

    private void hapus() {
        if(Sequel.queryu2tf("delete from persetujuan_penolakan_tindakan where no_pernyataan=?",1,new String[]{
            tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
        })==true){
            tampil();
            TabRawat.setSelectedIndex(1);
        }else{
            JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
        }
    }

    private void ganti() {
        /*if(Sequel.mengedittf("persetujuan_penolakan_tindakan","no_pernyataan=?","no_pernyataan=?,no_rawat=?,tanggal=?,diagnosa=?,diagnosa_konfirmasi=?,tindakan=?,tindakan_konfirmasi=?,indikasi_tindakan=?,indikasi_tindakan_konfirmasi=?,tata_cara=?,tata_cara_konfirmasi=?,tujuan=?,tujuan_konfirmasi=?,risiko=?,risiko_konfirmasi=?,komplikasi=?,komplikasi_konfirmasi=?,prognosis=?,prognosis_konfirmasi=?,alternatif_dan_risikonya=?,alternatif_konfirmasi=?,biaya=?,biaya_konfirmasi=?,lain_lain=?,lain_lain_konfirmasi=?,kd_dokter=?,nip=?,penerima_informasi=?,alasan_diwakilkan_penerima_informasi=?,jk_penerima_informasi=?,tanggal_lahir_penerima_informasi=?,umur_penerima_informasi=?,alamat_penerima_informasi=?,no_hp=?,hubungan_penerima_informasi=?,pernyataan=?,saksi_keluarga=?",38,new String[]{
                NoPenyataan.getText(),TNoRw.getText(),Valid.SetTgl(TglPernyataan.getSelectedItem()+""),Diagnosa.getText(),"false",TindakanKedokteran.getText(),"false",
                IndikasiTindakan.getText(),"false",TataCara.getText(),"false",Tujuan.getText(),"false",Risiko.getText(),"false",Komplikasi.getText(),"false",Prognosis.getText(), 
                "false",AlternatifResiko.getText(),"false",Biaya.getText(),"false",LainLain.getText(),"false",KdDokter.getText(),KdPerawat.getText(),PenerimaInformasi.getText(),
                AlasanDiwakilkan.getText(),JKPenerima.getSelectedItem().toString().substring(0,1),Valid.SetTgl(TglLahirPenerima.getSelectedItem()+""),UmurPenerima.getText(),
                AlamatPenerima.getText(),NoHPPenerima.getText(),HubunganDenganPasien.getSelectedItem().toString(),"Belum Dikonfirmasi",SaksiKeluarga.getText(),
                tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
            })==true){
               tampil();
               emptTeks();
               TabRawat.setSelectedIndex(1);
        }*/
    }
    
    private void isPhoto(){
        if(ChkAccor.isSelected()==true){
            ChkAccor.setVisible(false);
            PanelAccor.setPreferredSize(new Dimension(430,HEIGHT));
            TabData.setVisible(true);  
            ChkAccor.setVisible(true);
        }else if(ChkAccor.isSelected()==false){    
            ChkAccor.setVisible(false);
            PanelAccor.setPreferredSize(new Dimension(15,HEIGHT));
            TabData.setVisible(false);  
            ChkAccor.setVisible(true);
        }
    }

    private void panggilPhoto() {
        if(FormPhoto.isVisible()==true){
            if(TabData.getSelectedIndex()==0){
                try {
                    ps=koneksi.prepareStatement("select bukti_persetujuan_penolakan_tindakan_penerimainformasi.photo from bukti_persetujuan_penolakan_tindakan_penerimainformasi where bukti_persetujuan_penolakan_tindakan_penerimainformasi.no_pernyataan=?");
                    try {
                        ps.setString(1,tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
                        rs=ps.executeQuery();
                        if(rs.next()){
                            if(rs.getString("photo").equals("")||rs.getString("photo").equals("-")){
                                LoadHTML2.setText("<html><body><center><br><br><font face='tahoma' size='2' color='#434343'>Kosong</font></center></body></html>");
                            }else{
                                LoadHTML2.setText("<html><body><center><img src='http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/persetujuantindakan/"+rs.getString("photo")+"' alt='photo' width='450' height='550'/></center></body></html>");
                            }  
                        }else{
                            LoadHTML2.setText("<html><body><center><br><br><font face='tahoma' size='2' color='#434343'>Kosong</font></center></body></html>");
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
                } catch (Exception e) {
                    System.out.println("Notif : "+e);
                }
            }else{
                try {
                    ps=koneksi.prepareStatement("select bukti_persetujuan_penolakan_tindakan_saksikeluarga.photo from bukti_persetujuan_penolakan_tindakan_saksikeluarga where bukti_persetujuan_penolakan_tindakan_saksikeluarga.no_pernyataan=?");
                    try {
                        ps.setString(1,tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
                        rs=ps.executeQuery();
                        if(rs.next()){
                            if(rs.getString("photo").equals("")||rs.getString("photo").equals("-")){
                                LoadHTML3.setText("<html><body><center><br><br><font face='tahoma' size='2' color='#434343'>Kosong</font></center></body></html>");
                            }else{
                                LoadHTML3.setText("<html><body><center><img src='http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/persetujuantindakan/"+rs.getString("photo")+"' alt='photo' width='450' height='550'/></center></body></html>");
                            }  
                        }else{
                            LoadHTML3.setText("<html><body><center><br><br><font face='tahoma' size='2' color='#434343'>Kosong</font></center></body></html>");
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
                } catch (Exception e) {
                    System.out.println("Notif : "+e);
                }
            } 
        }
    }
}

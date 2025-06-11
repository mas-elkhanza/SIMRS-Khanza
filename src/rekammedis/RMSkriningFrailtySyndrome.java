/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


package rekammedis;

import fungsi.WarnaTable;
import fungsi.akses;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.Timer;
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
public final class RMSkriningFrailtySyndrome extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;    
    private DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    private String finger="";
    private String TANGGALMUNDUR="yes";
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public RMSkriningFrailtySyndrome(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(628,674);

        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.RM","Nama Pasien","Tgl.Lahir","Umur","Kode Petugas","Nama Petugas","Tanggal",
            "Resistensi","N.R.","Aktivitas","N.A.","Tidak Pernah","Kanker","Gagal Jantung","Ginjal","Nyeri Dada",
            "Serangan Jantung","Stroke","Asma","Nyeri Sendi","Paru Kronis","Hipertensi","Diabetes","N.P.",
            "Usaha Berjalan","N.U.","Berat Badan","N.B.","N.Total","Hasil Skrining","Keterangan"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 32; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(105);
            }else if(i==1){
                column.setPreferredWidth(70);
            }else if(i==2){
                column.setPreferredWidth(150);
            }else if(i==3){
                column.setPreferredWidth(65);
            }else if(i==4){
                column.setPreferredWidth(40);
            }else if(i==5){
                column.setPreferredWidth(90);
            }else if(i==6){
                column.setPreferredWidth(150);
            }else if(i==7){
                column.setPreferredWidth(115);
            }else if(i==8){
                column.setPreferredWidth(60);
            }else if(i==9){
                column.setPreferredWidth(30);
            }else if(i==10){
                column.setPreferredWidth(115);
            }else if(i==11){
                column.setPreferredWidth(30);
            }else if(i==12){
                column.setPreferredWidth(72);
            }else if(i==13){
                column.setPreferredWidth(45);
            }else if(i==14){
                column.setPreferredWidth(80);
            }else if(i==15){
                column.setPreferredWidth(40);
            }else if(i==16){
                column.setPreferredWidth(65);
            }else if(i==17){
                column.setPreferredWidth(96);
            }else if(i==18){
                column.setPreferredWidth(40);
            }else if(i==19){
                column.setPreferredWidth(40);
            }else if(i==20){
                column.setPreferredWidth(65);
            }else if(i==21){
                column.setPreferredWidth(65);
            }else if(i==22){
                column.setPreferredWidth(62);
            }else if(i==23){
                column.setPreferredWidth(57);
            }else if(i==24){
                column.setPreferredWidth(30);
            }else if(i==25){
                column.setPreferredWidth(85);
            }else if(i==26){
                column.setPreferredWidth(30);
            }else if(i==27){
                column.setPreferredWidth(67);
            }else if(i==28){
                column.setPreferredWidth(30);
            }else if(i==29){
                column.setPreferredWidth(45);
            }else if(i==30){
                column.setPreferredWidth(150);
            }else if(i==31){
                column.setPreferredWidth(150);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());

        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        Keterangan.setDocument(new batasInput((byte)40).getKata(Keterangan));
        TCari.setDocument(new batasInput((int)100).getKata(TCari));
        
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
                }  
                KdPetugas.requestFocus();
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
        
        ChkInput.setSelected(false);
        isForm();
        
        HTMLEditorKit kit = new HTMLEditorKit();
        LoadHTML.setEditable(true);
        LoadHTML.setEditorKit(kit);
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
        LoadHTML.setDocument(doc);
        
        try {
            TANGGALMUNDUR=koneksiDB.TANGGALMUNDUR();
        } catch (Exception e) {
            TANGGALMUNDUR="yes";
        }
        
        jam();
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnSkriningFrailtySyndrome = new javax.swing.JMenuItem();
        buttonGroup1 = new javax.swing.ButtonGroup();
        LoadHTML = new widget.editorpane();
        Umur = new widget.TextBox();
        TanggalRegistrasi = new widget.TextBox();
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
        jLabel7 = new widget.Label();
        LCount = new widget.Label();
        BtnKeluar = new widget.Button();
        panelGlass9 = new widget.panelisi();
        jLabel19 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel21 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        PanelInput = new javax.swing.JPanel();
        ChkInput = new widget.CekBox();
        scrollInput = new widget.ScrollPane();
        FormInput = new widget.PanelBiasa();
        jLabel4 = new widget.Label();
        TNoRw = new widget.TextBox();
        TPasien = new widget.TextBox();
        Tanggal = new widget.Tanggal();
        TNoRM = new widget.TextBox();
        jLabel16 = new widget.Label();
        Jam = new widget.ComboBox();
        Menit = new widget.ComboBox();
        Detik = new widget.ComboBox();
        ChkKejadian = new widget.CekBox();
        jLabel18 = new widget.Label();
        KdPetugas = new widget.TextBox();
        NmPetugas = new widget.TextBox();
        btnPetugas = new widget.Button();
        jLabel8 = new widget.Label();
        TglLahir = new widget.TextBox();
        Resistensi = new widget.ComboBox();
        jLabel92 = new widget.Label();
        jLabel73 = new widget.Label();
        TotalHasil = new widget.TextBox();
        NilaiResistensi = new widget.TextBox();
        jLabel148 = new widget.Label();
        HasilSkrining = new widget.TextBox();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel99 = new widget.Label();
        jLabel75 = new widget.Label();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel101 = new widget.Label();
        jLabel149 = new widget.Label();
        jLabel150 = new widget.Label();
        Keterangan = new widget.TextBox();
        jLabel77 = new widget.Label();
        jLabel78 = new widget.Label();
        jLabel76 = new widget.Label();
        jLabel79 = new widget.Label();
        Aktivitas = new widget.ComboBox();
        jLabel93 = new widget.Label();
        NilaiAktivitas = new widget.TextBox();
        jLabel80 = new widget.Label();
        jLabel81 = new widget.Label();
        jLabel94 = new widget.Label();
        NilaiPenyakit = new widget.TextBox();
        ChkTidakPernah = new widget.CekBox();
        ChkDiabetes = new widget.CekBox();
        ChkKanker = new widget.CekBox();
        ChkHipertensi = new widget.CekBox();
        ChkPenyakitParuKronis = new widget.CekBox();
        ChkSeranganJantung = new widget.CekBox();
        ChkGagalJantung = new widget.CekBox();
        ChkNyeriDada = new widget.CekBox();
        ChkAsma = new widget.CekBox();
        ChkNyeriSendi = new widget.CekBox();
        ChkPenyakitGinjal = new widget.CekBox();
        ChkStroke = new widget.CekBox();
        jLabel82 = new widget.Label();
        UsahaBerjalan = new widget.ComboBox();
        NilaiUsahaBerjalan = new widget.TextBox();
        jLabel83 = new widget.Label();
        jLabel84 = new widget.Label();
        jLabel86 = new widget.Label();
        jLabel87 = new widget.Label();
        jLabel88 = new widget.Label();
        jSeparator35 = new javax.swing.JSeparator();
        jLabel89 = new widget.Label();
        jSeparator36 = new javax.swing.JSeparator();
        jSeparator37 = new javax.swing.JSeparator();
        jLabel95 = new widget.Label();
        jSeparator38 = new javax.swing.JSeparator();
        jLabel85 = new widget.Label();
        jLabel90 = new widget.Label();
        jLabel91 = new widget.Label();
        jLabel96 = new widget.Label();
        jLabel97 = new widget.Label();
        jLabel98 = new widget.Label();
        BeratBadan = new widget.ComboBox();
        jLabel100 = new widget.Label();
        NilaiBeratBadan = new widget.TextBox();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnSkriningFrailtySyndrome.setBackground(new java.awt.Color(255, 255, 254));
        MnSkriningFrailtySyndrome.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSkriningFrailtySyndrome.setForeground(new java.awt.Color(50, 50, 50));
        MnSkriningFrailtySyndrome.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnSkriningFrailtySyndrome.setText("Formulir Skrining Frailty Syndrome");
        MnSkriningFrailtySyndrome.setName("MnSkriningFrailtySyndrome"); // NOI18N
        MnSkriningFrailtySyndrome.setPreferredSize(new java.awt.Dimension(260, 26));
        MnSkriningFrailtySyndrome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSkriningFrailtySyndromeActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnSkriningFrailtySyndrome);

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N

        Umur.setEditable(false);
        Umur.setFocusTraversalPolicyProvider(true);
        Umur.setName("Umur"); // NOI18N

        TanggalRegistrasi.setHighlighter(null);
        TanggalRegistrasi.setName("TanggalRegistrasi"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Skrining Frailty Syndrome ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);
        Scroll.setPreferredSize(new java.awt.Dimension(452, 200));

        tbObat.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbObat.setComponentPopupMenu(jPopupMenu1);
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
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 44));
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

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass8.add(jLabel7);

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

        jPanel3.add(panelGlass8, java.awt.BorderLayout.CENTER);

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel19.setText("Tanggal :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(60, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "01-05-2025" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass9.add(DTPCari1);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("s.d.");
        jLabel21.setName("jLabel21"); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(23, 23));
        panelGlass9.add(jLabel21);

        DTPCari2.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "01-05-2025" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass9.add(DTPCari2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(310, 23));
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

        jPanel3.add(panelGlass9, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 485));
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

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

        scrollInput.setName("scrollInput"); // NOI18N
        scrollInput.setPreferredSize(new java.awt.Dimension(102, 557));

        FormInput.setBackground(new java.awt.Color(250, 255, 245));
        FormInput.setBorder(null);
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(100, 413));
        FormInput.setLayout(null);

        jLabel4.setText("No.Rawat :");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(0, 10, 75, 23);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(79, 10, 141, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        TPasien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPasienKeyPressed(evt);
            }
        });
        FormInput.add(TPasien);
        TPasien.setBounds(336, 10, 285, 23);

        Tanggal.setForeground(new java.awt.Color(50, 70, 50));
        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "01-05-2025" }));
        Tanggal.setDisplayFormat("dd-MM-yyyy");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.setOpaque(false);
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeyPressed(evt);
            }
        });
        FormInput.add(Tanggal);
        Tanggal.setBounds(79, 40, 90, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        FormInput.add(TNoRM);
        TNoRM.setBounds(222, 10, 112, 23);

        jLabel16.setText("Tanggal :");
        jLabel16.setName("jLabel16"); // NOI18N
        jLabel16.setVerifyInputWhenFocusTarget(false);
        FormInput.add(jLabel16);
        jLabel16.setBounds(0, 40, 75, 23);

        Jam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        Jam.setName("Jam"); // NOI18N
        Jam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JamKeyPressed(evt);
            }
        });
        FormInput.add(Jam);
        Jam.setBounds(173, 40, 62, 23);

        Menit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        Menit.setName("Menit"); // NOI18N
        Menit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MenitKeyPressed(evt);
            }
        });
        FormInput.add(Menit);
        Menit.setBounds(238, 40, 62, 23);

        Detik.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        Detik.setName("Detik"); // NOI18N
        Detik.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DetikKeyPressed(evt);
            }
        });
        FormInput.add(Detik);
        Detik.setBounds(303, 40, 62, 23);

        ChkKejadian.setBorder(null);
        ChkKejadian.setSelected(true);
        ChkKejadian.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkKejadian.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkKejadian.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkKejadian.setName("ChkKejadian"); // NOI18N
        FormInput.add(ChkKejadian);
        ChkKejadian.setBounds(368, 40, 23, 23);

        jLabel18.setText("Petugas :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(400, 40, 70, 23);

        KdPetugas.setEditable(false);
        KdPetugas.setHighlighter(null);
        KdPetugas.setName("KdPetugas"); // NOI18N
        FormInput.add(KdPetugas);
        KdPetugas.setBounds(474, 40, 94, 23);

        NmPetugas.setEditable(false);
        NmPetugas.setName("NmPetugas"); // NOI18N
        FormInput.add(NmPetugas);
        NmPetugas.setBounds(570, 40, 187, 23);

        btnPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPetugas.setMnemonic('2');
        btnPetugas.setToolTipText("ALt+2");
        btnPetugas.setName("btnPetugas"); // NOI18N
        btnPetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPetugasActionPerformed(evt);
            }
        });
        btnPetugas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnPetugasKeyPressed(evt);
            }
        });
        FormInput.add(btnPetugas);
        btnPetugas.setBounds(761, 40, 28, 23);

        jLabel8.setText("Tgl.Lahir :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(625, 10, 60, 23);

        TglLahir.setHighlighter(null);
        TglLahir.setName("TglLahir"); // NOI18N
        FormInput.add(TglLahir);
        TglLahir.setBounds(689, 10, 100, 23);

        Resistensi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Resistensi.setName("Resistensi"); // NOI18N
        Resistensi.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ResistensiItemStateChanged(evt);
            }
        });
        Resistensi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ResistensiKeyPressed(evt);
            }
        });
        FormInput.add(Resistensi);
        Resistensi.setBounds(615, 105, 80, 23);

        jLabel92.setText("Nilai :");
        jLabel92.setName("jLabel92"); // NOI18N
        FormInput.add(jLabel92);
        jLabel92.setBounds(690, 105, 50, 23);

        jLabel73.setText("Total Skor :");
        jLabel73.setName("jLabel73"); // NOI18N
        FormInput.add(jLabel73);
        jLabel73.setBounds(670, 430, 70, 23);

        TotalHasil.setEditable(false);
        TotalHasil.setText("0");
        TotalHasil.setFocusTraversalPolicyProvider(true);
        TotalHasil.setName("TotalHasil"); // NOI18N
        FormInput.add(TotalHasil);
        TotalHasil.setBounds(744, 430, 45, 23);

        NilaiResistensi.setEditable(false);
        NilaiResistensi.setText("0");
        NilaiResistensi.setFocusTraversalPolicyProvider(true);
        NilaiResistensi.setName("NilaiResistensi"); // NOI18N
        FormInput.add(NilaiResistensi);
        NilaiResistensi.setBounds(744, 105, 45, 23);

        jLabel148.setText("Hasil Skrining :");
        jLabel148.setName("jLabel148"); // NOI18N
        FormInput.add(jLabel148);
        jLabel148.setBounds(336, 430, 90, 23);

        HasilSkrining.setEditable(false);
        HasilSkrining.setFocusTraversalPolicyProvider(true);
        HasilSkrining.setName("HasilSkrining"); // NOI18N
        HasilSkrining.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HasilSkriningKeyPressed(evt);
            }
        });
        FormInput.add(HasilSkrining);
        HasilSkrining.setBounds(430, 430, 220, 23);

        jSeparator1.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator1.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator1.setName("jSeparator1"); // NOI18N
        FormInput.add(jSeparator1);
        jSeparator1.setBounds(0, 70, 807, 1);

        jLabel99.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel99.setText("I. ANAMNESIS");
        jLabel99.setName("jLabel99"); // NOI18N
        FormInput.add(jLabel99);
        jLabel99.setBounds(10, 70, 200, 23);

        jLabel75.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel75.setText("1.");
        jLabel75.setName("jLabel75"); // NOI18N
        FormInput.add(jLabel75);
        jLabel75.setBounds(44, 90, 20, 23);

        jSeparator3.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator3.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator3.setName("jSeparator3"); // NOI18N
        FormInput.add(jSeparator3);
        jSeparator3.setBounds(0, 410, 807, 1);

        jLabel101.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel101.setText("II. INTERPRETASI");
        jLabel101.setName("jLabel101"); // NOI18N
        FormInput.add(jLabel101);
        jLabel101.setBounds(10, 410, 200, 23);

        jLabel149.setText(":");
        jLabel149.setName("jLabel149"); // NOI18N
        FormInput.add(jLabel149);
        jLabel149.setBounds(0, 430, 107, 23);

        jLabel150.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel150.setText("Keterangan");
        jLabel150.setName("jLabel150"); // NOI18N
        FormInput.add(jLabel150);
        jLabel150.setBounds(44, 430, 90, 23);

        Keterangan.setFocusTraversalPolicyProvider(true);
        Keterangan.setName("Keterangan"); // NOI18N
        Keterangan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKeyPressed(evt);
            }
        });
        FormInput.add(Keterangan);
        Keterangan.setBounds(111, 430, 205, 23);

        jLabel77.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel77.setText("R = Resistensi");
        jLabel77.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel77.setName("jLabel77"); // NOI18N
        FormInput.add(jLabel77);
        jLabel77.setBounds(57, 90, 100, 23);

        jLabel78.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel78.setText("Dengan usaha sendiri tanpa bantuan alat berjalan, apakah Anda mengalami kesulitan untuk ");
        jLabel78.setName("jLabel78"); // NOI18N
        FormInput.add(jLabel78);
        jLabel78.setBounds(57, 105, 530, 23);

        jLabel76.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel76.setText("2.");
        jLabel76.setName("jLabel76"); // NOI18N
        FormInput.add(jLabel76);
        jLabel76.setBounds(44, 145, 20, 23);

        jLabel79.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel79.setText("A = Aktivitas 1");
        jLabel79.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel79.setName("jLabel79"); // NOI18N
        FormInput.add(jLabel79);
        jLabel79.setBounds(57, 145, 140, 23);

        Aktivitas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Jarang", "Kadang-kadang", "Sebagian Besar Waktu", "Sepanjang Waktu" }));
        Aktivitas.setName("Aktivitas"); // NOI18N
        Aktivitas.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                AktivitasItemStateChanged(evt);
            }
        });
        Aktivitas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AktivitasKeyPressed(evt);
            }
        });
        FormInput.add(Aktivitas);
        Aktivitas.setBounds(535, 160, 160, 23);

        jLabel93.setText("Nilai :");
        jLabel93.setName("jLabel93"); // NOI18N
        FormInput.add(jLabel93);
        jLabel93.setBounds(690, 160, 50, 23);

        NilaiAktivitas.setEditable(false);
        NilaiAktivitas.setText("0");
        NilaiAktivitas.setFocusTraversalPolicyProvider(true);
        NilaiAktivitas.setName("NilaiAktivitas"); // NOI18N
        FormInput.add(NilaiAktivitas);
        NilaiAktivitas.setBounds(744, 160, 45, 23);

        jLabel80.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel80.setText("3.");
        jLabel80.setName("jLabel80"); // NOI18N
        FormInput.add(jLabel80);
        jLabel80.setBounds(44, 190, 20, 23);

        jLabel81.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel81.setText("P = Penyakit Lebih Dari 5");
        jLabel81.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel81.setName("jLabel81"); // NOI18N
        FormInput.add(jLabel81);
        jLabel81.setBounds(57, 190, 160, 23);

        jLabel94.setText("Nilai :");
        jLabel94.setName("jLabel94"); // NOI18N
        FormInput.add(jLabel94);
        jLabel94.setBounds(690, 210, 50, 23);

        NilaiPenyakit.setEditable(false);
        NilaiPenyakit.setText("0");
        NilaiPenyakit.setFocusTraversalPolicyProvider(true);
        NilaiPenyakit.setName("NilaiPenyakit"); // NOI18N
        FormInput.add(NilaiPenyakit);
        NilaiPenyakit.setBounds(744, 210, 50, 23);

        ChkTidakPernah.setText("Tidak Pernah");
        ChkTidakPernah.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkTidakPernah.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkTidakPernah.setName("ChkTidakPernah"); // NOI18N
        ChkTidakPernah.setPreferredSize(new java.awt.Dimension(100, 23));
        ChkTidakPernah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkTidakPernahActionPerformed(evt);
            }
        });
        FormInput.add(ChkTidakPernah);
        ChkTidakPernah.setBounds(57, 223, 95, 23);

        ChkDiabetes.setText("Diabetes");
        ChkDiabetes.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkDiabetes.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkDiabetes.setName("ChkDiabetes"); // NOI18N
        ChkDiabetes.setPreferredSize(new java.awt.Dimension(100, 23));
        ChkDiabetes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkDiabetesActionPerformed(evt);
            }
        });
        FormInput.add(ChkDiabetes);
        ChkDiabetes.setBounds(612, 243, 73, 23);

        ChkKanker.setText("Kanker (Selain Kanker Kulit Kecil)");
        ChkKanker.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkKanker.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkKanker.setName("ChkKanker"); // NOI18N
        ChkKanker.setPreferredSize(new java.awt.Dimension(100, 23));
        ChkKanker.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkKankerActionPerformed(evt);
            }
        });
        FormInput.add(ChkKanker);
        ChkKanker.setBounds(155, 223, 190, 23);

        ChkHipertensi.setText("Hipertensi");
        ChkHipertensi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkHipertensi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkHipertensi.setName("ChkHipertensi"); // NOI18N
        ChkHipertensi.setPreferredSize(new java.awt.Dimension(100, 23));
        ChkHipertensi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkHipertensiActionPerformed(evt);
            }
        });
        FormInput.add(ChkHipertensi);
        ChkHipertensi.setBounds(529, 243, 80, 23);

        ChkPenyakitParuKronis.setText("Penyakit Paru Kronis");
        ChkPenyakitParuKronis.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkPenyakitParuKronis.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkPenyakitParuKronis.setName("ChkPenyakitParuKronis"); // NOI18N
        ChkPenyakitParuKronis.setPreferredSize(new java.awt.Dimension(100, 23));
        ChkPenyakitParuKronis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkPenyakitParuKronisActionPerformed(evt);
            }
        });
        FormInput.add(ChkPenyakitParuKronis);
        ChkPenyakitParuKronis.setBounds(396, 243, 130, 23);

        ChkSeranganJantung.setText("Serangan Jantung");
        ChkSeranganJantung.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkSeranganJantung.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkSeranganJantung.setName("ChkSeranganJantung"); // NOI18N
        ChkSeranganJantung.setPreferredSize(new java.awt.Dimension(100, 23));
        ChkSeranganJantung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkSeranganJantungActionPerformed(evt);
            }
        });
        FormInput.add(ChkSeranganJantung);
        ChkSeranganJantung.setBounds(57, 243, 120, 23);

        ChkGagalJantung.setText("Gagal Jantung Kongestif");
        ChkGagalJantung.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkGagalJantung.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkGagalJantung.setName("ChkGagalJantung"); // NOI18N
        ChkGagalJantung.setPreferredSize(new java.awt.Dimension(100, 23));
        ChkGagalJantung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkGagalJantungActionPerformed(evt);
            }
        });
        FormInput.add(ChkGagalJantung);
        ChkGagalJantung.setBounds(348, 223, 150, 23);

        ChkNyeriDada.setText("Nyeri Dada");
        ChkNyeriDada.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkNyeriDada.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkNyeriDada.setName("ChkNyeriDada"); // NOI18N
        ChkNyeriDada.setPreferredSize(new java.awt.Dimension(100, 23));
        ChkNyeriDada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkNyeriDadaActionPerformed(evt);
            }
        });
        FormInput.add(ChkNyeriDada);
        ChkNyeriDada.setBounds(604, 223, 84, 23);

        ChkAsma.setText("Asma");
        ChkAsma.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkAsma.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkAsma.setName("ChkAsma"); // NOI18N
        ChkAsma.setPreferredSize(new java.awt.Dimension(100, 23));
        ChkAsma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkAsmaActionPerformed(evt);
            }
        });
        FormInput.add(ChkAsma);
        ChkAsma.setBounds(245, 243, 59, 23);

        ChkNyeriSendi.setText("Nyeri Sendi");
        ChkNyeriSendi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkNyeriSendi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkNyeriSendi.setName("ChkNyeriSendi"); // NOI18N
        ChkNyeriSendi.setPreferredSize(new java.awt.Dimension(100, 23));
        ChkNyeriSendi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkNyeriSendiActionPerformed(evt);
            }
        });
        FormInput.add(ChkNyeriSendi);
        ChkNyeriSendi.setBounds(307, 243, 86, 23);

        ChkPenyakitGinjal.setText("Penyakit Ginjal");
        ChkPenyakitGinjal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkPenyakitGinjal.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkPenyakitGinjal.setName("ChkPenyakitGinjal"); // NOI18N
        ChkPenyakitGinjal.setPreferredSize(new java.awt.Dimension(100, 23));
        ChkPenyakitGinjal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkPenyakitGinjalActionPerformed(evt);
            }
        });
        FormInput.add(ChkPenyakitGinjal);
        ChkPenyakitGinjal.setBounds(501, 223, 100, 23);

        ChkStroke.setText("Stroke");
        ChkStroke.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkStroke.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkStroke.setName("ChkStroke"); // NOI18N
        ChkStroke.setPreferredSize(new java.awt.Dimension(100, 23));
        ChkStroke.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkStrokeActionPerformed(evt);
            }
        });
        FormInput.add(ChkStroke);
        ChkStroke.setBounds(180, 243, 62, 23);

        jLabel82.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel82.setText("4.");
        jLabel82.setName("jLabel82"); // NOI18N
        FormInput.add(jLabel82);
        jLabel82.setBounds(44, 270, 20, 23);

        UsahaBerjalan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        UsahaBerjalan.setName("UsahaBerjalan"); // NOI18N
        UsahaBerjalan.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                UsahaBerjalanItemStateChanged(evt);
            }
        });
        UsahaBerjalan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                UsahaBerjalanKeyPressed(evt);
            }
        });
        FormInput.add(UsahaBerjalan);
        UsahaBerjalan.setBounds(615, 285, 80, 23);

        NilaiUsahaBerjalan.setEditable(false);
        NilaiUsahaBerjalan.setText("0");
        NilaiUsahaBerjalan.setFocusTraversalPolicyProvider(true);
        NilaiUsahaBerjalan.setName("NilaiUsahaBerjalan"); // NOI18N
        FormInput.add(NilaiUsahaBerjalan);
        NilaiUsahaBerjalan.setBounds(744, 285, 45, 23);

        jLabel83.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel83.setText("U = Usaha Berjalan");
        jLabel83.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel83.setName("jLabel83"); // NOI18N
        FormInput.add(jLabel83);
        jLabel83.setBounds(57, 270, 130, 23);

        jLabel84.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel84.setText("kira-kira sejauh 100 sampai 200 meter ?");
        jLabel84.setName("jLabel84"); // NOI18N
        FormInput.add(jLabel84);
        jLabel84.setBounds(57, 300, 450, 23);

        jLabel86.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel86.setText("Seberapa sering dalam 4 minggu Anda merasa kelelahan ?");
        jLabel86.setName("jLabel86"); // NOI18N
        FormInput.add(jLabel86);
        jLabel86.setBounds(57, 160, 360, 23);

        jLabel87.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel87.setText("Apakah dokter pernah mengatakan kepada Anda tentang penyakit Anda (11 penyakit utama) ?");
        jLabel87.setName("jLabel87"); // NOI18N
        FormInput.add(jLabel87);
        jLabel87.setBounds(57, 205, 490, 23);

        jLabel88.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel88.setText("Dengan usaha sendiri tanpa bantuan alat berjalan, apakah Anda mengalami kesulitan berjalan");
        jLabel88.setName("jLabel88"); // NOI18N
        FormInput.add(jLabel88);
        jLabel88.setBounds(57, 285, 540, 23);

        jSeparator35.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator35.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator35.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator35.setName("jSeparator35"); // NOI18N
        FormInput.add(jSeparator35);
        jSeparator35.setBounds(44, 145, 745, 1);

        jLabel89.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel89.setText("naik 10 anak tangga dan tanpa istirahat diantaranya ? ");
        jLabel89.setName("jLabel89"); // NOI18N
        FormInput.add(jLabel89);
        jLabel89.setBounds(57, 120, 530, 23);

        jSeparator36.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator36.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator36.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator36.setName("jSeparator36"); // NOI18N
        FormInput.add(jSeparator36);
        jSeparator36.setBounds(44, 190, 745, 1);

        jSeparator37.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator37.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator37.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator37.setName("jSeparator37"); // NOI18N
        FormInput.add(jSeparator37);
        jSeparator37.setBounds(44, 270, 745, 1);

        jLabel95.setText("Nilai :");
        jLabel95.setName("jLabel95"); // NOI18N
        FormInput.add(jLabel95);
        jLabel95.setBounds(690, 285, 50, 23);

        jSeparator38.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator38.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator38.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator38.setName("jSeparator38"); // NOI18N
        FormInput.add(jSeparator38);
        jSeparator38.setBounds(44, 325, 745, 1);

        jLabel85.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel85.setText("5.");
        jLabel85.setName("jLabel85"); // NOI18N
        FormInput.add(jLabel85);
        jLabel85.setBounds(44, 325, 20, 23);

        jLabel90.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel90.setText("H = Hilangnya Berat Badan");
        jLabel90.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel90.setName("jLabel90"); // NOI18N
        FormInput.add(jLabel90);
        jLabel90.setBounds(57, 325, 240, 23);

        jLabel91.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel91.setText("- Berapa berat badan Anda dengan mengenakan baju tanpa alas kaki saat ini ?");
        jLabel91.setName("jLabel91"); // NOI18N
        FormInput.add(jLabel91);
        jLabel91.setBounds(57, 340, 540, 23);

        jLabel96.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel96.setText("- Satu tahun yang lalu, berapa berat badan Anda dengan mengenakan baju tanpa alas kaki ?");
        jLabel96.setName("jLabel96"); // NOI18N
        FormInput.add(jLabel96);
        jLabel96.setBounds(57, 355, 540, 23);

        jLabel97.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel97.setText("Keterangan perhitungan berat badan dalam persen : [(berat badan 1 tahun yang lalu - berat badan sekarang)/ ");
        jLabel97.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        jLabel97.setName("jLabel97"); // NOI18N
        FormInput.add(jLabel97);
        jLabel97.setBounds(57, 370, 670, 23);

        jLabel98.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel98.setText("berat badan satu tahun lalu)] x 100 %");
        jLabel98.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        jLabel98.setName("jLabel98"); // NOI18N
        FormInput.add(jLabel98);
        jLabel98.setBounds(57, 385, 670, 23);

        BeratBadan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "< 5%", ">= 5%" }));
        BeratBadan.setName("BeratBadan"); // NOI18N
        BeratBadan.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                BeratBadanItemStateChanged(evt);
            }
        });
        BeratBadan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BeratBadanKeyPressed(evt);
            }
        });
        FormInput.add(BeratBadan);
        BeratBadan.setBounds(605, 340, 90, 23);

        jLabel100.setText("Nilai :");
        jLabel100.setName("jLabel100"); // NOI18N
        FormInput.add(jLabel100);
        jLabel100.setBounds(690, 340, 50, 23);

        NilaiBeratBadan.setEditable(false);
        NilaiBeratBadan.setText("0");
        NilaiBeratBadan.setFocusTraversalPolicyProvider(true);
        NilaiBeratBadan.setName("NilaiBeratBadan"); // NOI18N
        FormInput.add(NilaiBeratBadan);
        NilaiBeratBadan.setBounds(744, 340, 45, 23);

        scrollInput.setViewportView(FormInput);

        PanelInput.add(scrollInput, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isRawat();
        }else{            
            Valid.pindah(evt,TCari,Tanggal);
        }
}//GEN-LAST:event_TNoRwKeyPressed

    private void TPasienKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TPasienKeyPressed
        Valid.pindah(evt,TCari,BtnSimpan);
}//GEN-LAST:event_TPasienKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"pasien");
        }else if(KdPetugas.getText().trim().equals("")||NmPetugas.getText().trim().equals("")){
            Valid.textKosong(KdPetugas,"Petugas");
        }else{
            if(akses.getkode().equals("Admin Utama")){
                simpan();
            }else{
                if(TanggalRegistrasi.getText().equals("")){
                    TanggalRegistrasi.setText(Sequel.cariIsi("select concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) from reg_periksa where reg_periksa.no_rawat=?",TNoRw.getText()));
                }
                if(Sequel.cekTanggalRegistrasi(TanggalRegistrasi.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem())==true){
                    simpan();
                }
            }
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,Keterangan,BtnBatal);
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
        if(tbObat.getSelectedRow()>-1){
            if(akses.getkode().equals("Admin Utama")){
                hapus();
            }else{
                if(KdPetugas.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString())){
                    if(Sequel.cekTanggal48jam(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString(),Sequel.ambiltanggalsekarang())==true){
                        hapus();
                    }
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
        if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"pasien");
        }else if(KdPetugas.getText().trim().equals("")||NmPetugas.getText().trim().equals("")){
            Valid.textKosong(KdPetugas,"Petugas");
        }else{
            if(tbObat.getSelectedRow()>-1){
                if(akses.getkode().equals("Admin Utama")){
                    ganti();
                }else{
                    if(KdPetugas.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString())){
                        if(Sequel.cekTanggal48jam(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString(),Sequel.ambiltanggalsekarang())==true){
                            if(TanggalRegistrasi.getText().equals("")){
                                TanggalRegistrasi.setText(Sequel.cariIsi("select concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) from reg_periksa where reg_periksa.no_rawat=?",TNoRw.getText()));
                            }
                            if(Sequel.cekTanggalRegistrasi(TanggalRegistrasi.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem())==true){
                                ganti();
                            }
                        }
                    }else{
                        JOptionPane.showMessageDialog(null,"Hanya bisa diganti oleh petugas yang bersangkutan..!!");
                    }
                }
            }else{
                JOptionPane.showMessageDialog(rootPane,"Silahkan anda pilih data terlebih dahulu..!!");
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
        petugas.dispose();
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
                bg.close();

                File f;            
                BufferedWriter bw;
                StringBuilder htmlContent;
                
                String pilihan =(String) JOptionPane.showInputDialog(null,"Silahkan pilih laporan..!","Pilihan Cetak",JOptionPane.QUESTION_MESSAGE,null,new Object[]{"Laporan 1 (HTML)","Laporan 2 (WPS)","Laporan 3 (CSV)"},"Laporan 1 (HTML)");
                switch (pilihan) {
                    case "Laporan 1 (HTML)":
                            htmlContent = new StringBuilder();
                            htmlContent.append("<tr class='isi'>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Rawat</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.RM</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Pasien</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tgl.Lahir</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Umur</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kode Petugas</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Petugas</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tanggal</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Resistensi</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.R.</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Aktivitas</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.A.</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tidak Pernah</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kanker</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Gagal Jantung</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Ginjal</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nyeri Dada</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Serangan Jantung</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Stroke</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Asma</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nyeri Sendi</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Paru Kronis</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Hipertensi</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Diabetes</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.P.</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Usaha Berjalan</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.U.</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Berat Badan</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.B.</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.Total</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Hasil Skrining</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan</b></td>").
                                        append("</tr>");
                            for (i = 0; i < tabMode.getRowCount(); i++) {
                                htmlContent.append("<tr class='isi'>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,0).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,1).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,2).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,3).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,4).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,5).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,6).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,7).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,8).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,9).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,10).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,11).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,12).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,13).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,14).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,15).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,16).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,17).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,18).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,19).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,20).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,21).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,22).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,23).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,24).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,25).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,26).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,27).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,28).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,29).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,30).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,31).toString()).append("</td>").
                                            append("</tr>");
                            }
                            LoadHTML.setText(
                                "<html>"+
                                  "<table width='2000px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
                                   htmlContent.toString()+
                                  "</table>"+
                                "</html>"
                            );
                            
                            f = new File("DataSkriningFrailtySyndrome.html");            
                            bw = new BufferedWriter(new FileWriter(f));            
                            bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                                        "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                                        "<table width='2000px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                            "<tr class='isi2'>"+
                                                "<td valign='top' align='center'>"+
                                                    "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                                    akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                                    akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                                    "<font size='2' face='Tahoma'>DATA SEKRINING FRAILTY SYNDROME<br><br></font>"+        
                                                "</td>"+
                                           "</tr>"+
                                        "</table>")
                            );
                            bw.close();                         
                            Desktop.getDesktop().browse(f.toURI());
                        break;
                    case "Laporan 2 (WPS)":
                            htmlContent = new StringBuilder();
                            htmlContent.append("<tr class='isi'>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Rawat</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.RM</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Pasien</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tgl.Lahir</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Umur</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kode Petugas</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Petugas</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tanggal</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Resistensi</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.R.</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Aktivitas</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.A.</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tidak Pernah</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kanker</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Gagal Jantung</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Ginjal</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nyeri Dada</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Serangan Jantung</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Stroke</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Asma</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nyeri Sendi</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Paru Kronis</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Hipertensi</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Diabetes</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.P.</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Usaha Berjalan</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.U.</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Berat Badan</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.B.</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.Total</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Hasil Skrining</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan</b></td>").
                                        append("</tr>");
                            for (i = 0; i < tabMode.getRowCount(); i++) {
                                htmlContent.append("<tr class='isi'>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,0).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,1).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,2).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,3).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,4).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,5).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,6).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,7).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,8).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,9).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,10).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,11).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,12).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,13).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,14).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,15).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,16).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,17).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,18).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,19).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,20).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,21).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,22).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,23).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,24).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,25).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,26).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,27).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,28).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,29).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,30).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,31).toString()).append("</td>").
                                            append("</tr>");
                            }
                            LoadHTML.setText(
                                "<html>"+
                                  "<table width='2000px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
                                   htmlContent.toString()+
                                  "</table>"+
                                "</html>"
                            );
                            
                            f = new File("DataSkriningFrailtySyndrome.wps");            
                            bw = new BufferedWriter(new FileWriter(f));            
                            bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                                        "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                                        "<table width='2000px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                            "<tr class='isi2'>"+
                                                "<td valign='top' align='center'>"+
                                                    "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                                    akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                                    akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                                    "<font size='2' face='Tahoma'>DATA SEKRINING FRAILTY SYNDROME<br><br></font>"+        
                                                "</td>"+
                                           "</tr>"+
                                        "</table>")
                            );
                            bw.close();                         
                            Desktop.getDesktop().browse(f.toURI());
                        break;
                    case "Laporan 3 (CSV)":
                            htmlContent = new StringBuilder();
                            htmlContent.append(                             
                                "\"No.Rawat\";\"No.RM\";\"Nama Pasien\";\"Tgl.Lahir\";\"Umur\";\"Kode Petugas\";\"Nama Petugas\";\"Tanggal\";\"Resistensi\";\"N.R.\";\"Aktivitas\";\"N.A.\";\"Tidak Pernah\";\"Kanker\";\"Gagal Jantung\";\"Ginjal\";\"Nyeri Dada\";\"Serangan Jantung\";\"Stroke\";\"Asma\";\"Nyeri Sendi\";\"Paru Kronis\";\"Hipertensi\";\"Diabetes\";\"N.P.\";\"Usaha Berjalan\";\"N.U.\";\"Berat Badan\";\"N.B.\";\"N.Total\";\"Hasil Skrining\";\"Keterangan\"\n"
                            ); 
                            for (i = 0; i < tabMode.getRowCount(); i++) {
                                htmlContent.append("\"").append(tbObat.getValueAt(i,0).toString()).append("\";\"").append(tbObat.getValueAt(i,1).toString()).append("\";\"").append(tbObat.getValueAt(i,2).toString()).append("\";\"").append(tbObat.getValueAt(i,3).toString()).append("\";\"").append(tbObat.getValueAt(i,4).toString()).append("\";\"").append(tbObat.getValueAt(i,5).toString()).append("\";\"").append(tbObat.getValueAt(i,6).toString()).append("\";\"").append(tbObat.getValueAt(i,7).toString()).append("\";\"").append(tbObat.getValueAt(i,8).toString()).append("\";\"").append(tbObat.getValueAt(i,9).toString()).append("\";\"").
                                                         append(tbObat.getValueAt(i,10).toString()).append("\";\"").append(tbObat.getValueAt(i,11).toString()).append("\";\"").append(tbObat.getValueAt(i,12).toString()).append("\";\"").append(tbObat.getValueAt(i,13).toString()).append("\";\"").append(tbObat.getValueAt(i,14).toString()).append("\";\"").append(tbObat.getValueAt(i,15).toString()).append("\";\"").append(tbObat.getValueAt(i,16).toString()).append("\";\"").append(tbObat.getValueAt(i,17).toString()).append("\";\"").append(tbObat.getValueAt(i,18).toString()).append("\";\"").append(tbObat.getValueAt(i,19).toString()).append("\";\"").
                                                         append(tbObat.getValueAt(i,20).toString()).append("\";\"").append(tbObat.getValueAt(i,21).toString()).append("\";\"").append(tbObat.getValueAt(i,22).toString()).append("\";\"").append(tbObat.getValueAt(i,23).toString()).append("\";\"").append(tbObat.getValueAt(i,24).toString()).append("\";\"").append(tbObat.getValueAt(i,25).toString()).append("\";\"").append(tbObat.getValueAt(i,26).toString()).append("\";\"").append(tbObat.getValueAt(i,27).toString()).append("\";\"").append(tbObat.getValueAt(i,28).toString()).append("\";\"").append(tbObat.getValueAt(i,29).toString()).append("\";\"").
                                                         append(tbObat.getValueAt(i,30).toString()).append("\";\"").append(tbObat.getValueAt(i,31).toString()).append("\"\n");
                            }
                            f = new File("DataSkriningFrailtySyndrome.csv");            
                            bw = new BufferedWriter(new FileWriter(f));            
                            bw.write(htmlContent.toString());
                            bw.close();                         
                            Desktop.getDesktop().browse(f.toURI());
                        break; 
                }   
                htmlContent=null;
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
            tampil();
            TCari.setText("");
        }else{
            Valid.pindah(evt, BtnCari, TPasien);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void TanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKeyPressed
        Valid.pindah(evt,TCari,Jam);
}//GEN-LAST:event_TanggalKeyPressed

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

    private void JamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JamKeyPressed
        Valid.pindah(evt,Tanggal,Menit);
    }//GEN-LAST:event_JamKeyPressed

    private void MenitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MenitKeyPressed
        Valid.pindah(evt,Jam,Detik);
    }//GEN-LAST:event_MenitKeyPressed

    private void DetikKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DetikKeyPressed
        Valid.pindah(evt,Menit,btnPetugas);
    }//GEN-LAST:event_DetikKeyPressed

    private void btnPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPetugasActionPerformed
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setVisible(true);
    }//GEN-LAST:event_btnPetugasActionPerformed

    private void btnPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPetugasKeyPressed
        Valid.pindah(evt,TCari,Resistensi);
    }//GEN-LAST:event_btnPetugasKeyPressed

    private void MnSkriningFrailtySyndromeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnSkriningFrailtySyndromeActionPerformed
        if(tbObat.getSelectedRow()>-1){
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());   
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
            param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbObat.getValueAt(tbObat.getSelectedRow(),6).toString()+"\nID "+(finger.equals("")?tbObat.getValueAt(tbObat.getSelectedRow(),5).toString():finger)+"\n"+Tanggal.getSelectedItem()); 
            Valid.MyReportqry("rptFormulirSkriningFrailtySyndrome.jasper","report","::[ Formulir Skrining Frailty Syndrome ]::",
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,reg_periksa.umurdaftar,reg_periksa.sttsumur,skrining_frailty_syndrome.nip,"+
                    "petugas.nama,skrining_frailty_syndrome.tanggal,skrining_frailty_syndrome.resistensi,skrining_frailty_syndrome.nilai_resistensi,skrining_frailty_syndrome.aktivitas,"+
                    "skrining_frailty_syndrome.nilai_aktivitas,skrining_frailty_syndrome.penyakit_tidak_pernah,skrining_frailty_syndrome.penyakit_kanker,skrining_frailty_syndrome.penyakit_gagal_jantung,"+
                    "skrining_frailty_syndrome.penyakit_ginjal,skrining_frailty_syndrome.penyakit_nyeri_dada,skrining_frailty_syndrome.penyakit_serangan_jantung,skrining_frailty_syndrome.penyakit_stroke,"+
                    "skrining_frailty_syndrome.penyakit_asma,skrining_frailty_syndrome.penyakit_nyeri_sendi,skrining_frailty_syndrome.penyakit_paru_kronis,skrining_frailty_syndrome.penyakit_hipertensi,"+
                    "skrining_frailty_syndrome.penyakit_diabetes,skrining_frailty_syndrome.nilai_penyakit,skrining_frailty_syndrome.usaha_berjalan,skrining_frailty_syndrome.nilai_usaha_berjalan,"+
                    "skrining_frailty_syndrome.berat_badan,skrining_frailty_syndrome.nilai_berat_badan,skrining_frailty_syndrome.nilai_total,skrining_frailty_syndrome.hasil_skrining,"+
                    "skrining_frailty_syndrome.keterangan from skrining_frailty_syndrome inner join reg_periksa on skrining_frailty_syndrome.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join petugas on skrining_frailty_syndrome.nip=petugas.nip "+
                    "where reg_periksa.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'",param);
        }
    }//GEN-LAST:event_MnSkriningFrailtySyndromeActionPerformed

    private void ResistensiItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ResistensiItemStateChanged
        if(Resistensi.getSelectedIndex()==0){
            NilaiResistensi.setText("0");
        }else{
            NilaiResistensi.setText("1");
        }
        isTotal();
    }//GEN-LAST:event_ResistensiItemStateChanged

    private void ResistensiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ResistensiKeyPressed
        Valid.pindah(evt,TCari,Aktivitas);
    }//GEN-LAST:event_ResistensiKeyPressed

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void HasilSkriningKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HasilSkriningKeyPressed
        //Valid.pindah(evt,Lapor,SG1);
    }//GEN-LAST:event_HasilSkriningKeyPressed

    private void KeteranganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKeyPressed
        Valid.pindah(evt,BeratBadan,BtnSimpan);
    }//GEN-LAST:event_KeteranganKeyPressed

    private void AktivitasItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_AktivitasItemStateChanged
        if((Aktivitas.getSelectedIndex()==0)||(Aktivitas.getSelectedIndex()==1)){
            NilaiAktivitas.setText("0");
        }else{
            NilaiAktivitas.setText("1");
        }
        isTotal();
    }//GEN-LAST:event_AktivitasItemStateChanged

    private void AktivitasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AktivitasKeyPressed
        Valid.pindah(evt,Resistensi,UsahaBerjalan);
    }//GEN-LAST:event_AktivitasKeyPressed

    private void UsahaBerjalanItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_UsahaBerjalanItemStateChanged
        if(UsahaBerjalan.getSelectedIndex()==0){
            NilaiUsahaBerjalan.setText("0");
        }else{
            NilaiUsahaBerjalan.setText("1");
        }
        isTotal();
    }//GEN-LAST:event_UsahaBerjalanItemStateChanged

    private void UsahaBerjalanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_UsahaBerjalanKeyPressed
        Valid.pindah(evt,Aktivitas,BeratBadan);
    }//GEN-LAST:event_UsahaBerjalanKeyPressed

    private void BeratBadanItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_BeratBadanItemStateChanged
        if(BeratBadan.getSelectedIndex()==0){
            NilaiBeratBadan.setText("0");
        }else{
            NilaiBeratBadan.setText("1");
        }
        isTotal();
    }//GEN-LAST:event_BeratBadanItemStateChanged

    private void BeratBadanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BeratBadanKeyPressed
        Valid.pindah(evt,UsahaBerjalan,Keterangan);
    }//GEN-LAST:event_BeratBadanKeyPressed

    private void ChkTidakPernahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkTidakPernahActionPerformed
        if(ChkTidakPernah.isSelected()==true){
            NilaiPenyakit.setText("0");
            isTotal();
            ChkKanker.setSelected(false);
            ChkGagalJantung.setSelected(false);
            ChkPenyakitGinjal.setSelected(false);
            ChkNyeriDada.setSelected(false);
            ChkSeranganJantung.setSelected(false);
            ChkStroke.setSelected(false);
            ChkAsma.setSelected(false);
            ChkNyeriSendi.setSelected(false);
            ChkPenyakitParuKronis.setSelected(false);
            ChkHipertensi.setSelected(false);
            ChkDiabetes.setSelected(false);
        }
    }//GEN-LAST:event_ChkTidakPernahActionPerformed

    private void ChkKankerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkKankerActionPerformed
        isPenyakit();
    }//GEN-LAST:event_ChkKankerActionPerformed

    private void ChkGagalJantungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkGagalJantungActionPerformed
        isPenyakit();
    }//GEN-LAST:event_ChkGagalJantungActionPerformed

    private void ChkPenyakitGinjalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkPenyakitGinjalActionPerformed
        isPenyakit();
    }//GEN-LAST:event_ChkPenyakitGinjalActionPerformed

    private void ChkNyeriDadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkNyeriDadaActionPerformed
        isPenyakit();
    }//GEN-LAST:event_ChkNyeriDadaActionPerformed

    private void ChkSeranganJantungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkSeranganJantungActionPerformed
        isPenyakit();
    }//GEN-LAST:event_ChkSeranganJantungActionPerformed

    private void ChkStrokeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkStrokeActionPerformed
        isPenyakit();
    }//GEN-LAST:event_ChkStrokeActionPerformed

    private void ChkAsmaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkAsmaActionPerformed
        isPenyakit();
    }//GEN-LAST:event_ChkAsmaActionPerformed

    private void ChkNyeriSendiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkNyeriSendiActionPerformed
        isPenyakit();
    }//GEN-LAST:event_ChkNyeriSendiActionPerformed

    private void ChkPenyakitParuKronisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkPenyakitParuKronisActionPerformed
        isPenyakit();
    }//GEN-LAST:event_ChkPenyakitParuKronisActionPerformed

    private void ChkHipertensiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkHipertensiActionPerformed
        isPenyakit();
    }//GEN-LAST:event_ChkHipertensiActionPerformed

    private void ChkDiabetesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkDiabetesActionPerformed
        isPenyakit();
    }//GEN-LAST:event_ChkDiabetesActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMSkriningFrailtySyndrome dialog = new RMSkriningFrailtySyndrome(new javax.swing.JFrame(), true);
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
    private widget.ComboBox Aktivitas;
    private widget.ComboBox BeratBadan;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkAsma;
    private widget.CekBox ChkDiabetes;
    private widget.CekBox ChkGagalJantung;
    private widget.CekBox ChkHipertensi;
    private widget.CekBox ChkInput;
    private widget.CekBox ChkKanker;
    private widget.CekBox ChkKejadian;
    private widget.CekBox ChkNyeriDada;
    private widget.CekBox ChkNyeriSendi;
    private widget.CekBox ChkPenyakitGinjal;
    private widget.CekBox ChkPenyakitParuKronis;
    private widget.CekBox ChkSeranganJantung;
    private widget.CekBox ChkStroke;
    private widget.CekBox ChkTidakPernah;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.ComboBox Detik;
    private widget.PanelBiasa FormInput;
    private widget.TextBox HasilSkrining;
    private widget.ComboBox Jam;
    private widget.TextBox KdPetugas;
    private widget.TextBox Keterangan;
    private widget.Label LCount;
    private widget.editorpane LoadHTML;
    private widget.ComboBox Menit;
    private javax.swing.JMenuItem MnSkriningFrailtySyndrome;
    private widget.TextBox NilaiAktivitas;
    private widget.TextBox NilaiBeratBadan;
    private widget.TextBox NilaiPenyakit;
    private widget.TextBox NilaiResistensi;
    private widget.TextBox NilaiUsahaBerjalan;
    private widget.TextBox NmPetugas;
    private javax.swing.JPanel PanelInput;
    private widget.ComboBox Resistensi;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.Tanggal Tanggal;
    private widget.TextBox TanggalRegistrasi;
    private widget.TextBox TglLahir;
    private widget.TextBox TotalHasil;
    private widget.TextBox Umur;
    private widget.ComboBox UsahaBerjalan;
    private widget.Button btnPetugas;
    private javax.swing.ButtonGroup buttonGroup1;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel100;
    private widget.Label jLabel101;
    private widget.Label jLabel148;
    private widget.Label jLabel149;
    private widget.Label jLabel150;
    private widget.Label jLabel16;
    private widget.Label jLabel18;
    private widget.Label jLabel19;
    private widget.Label jLabel21;
    private widget.Label jLabel4;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel73;
    private widget.Label jLabel75;
    private widget.Label jLabel76;
    private widget.Label jLabel77;
    private widget.Label jLabel78;
    private widget.Label jLabel79;
    private widget.Label jLabel8;
    private widget.Label jLabel80;
    private widget.Label jLabel81;
    private widget.Label jLabel82;
    private widget.Label jLabel83;
    private widget.Label jLabel84;
    private widget.Label jLabel85;
    private widget.Label jLabel86;
    private widget.Label jLabel87;
    private widget.Label jLabel88;
    private widget.Label jLabel89;
    private widget.Label jLabel90;
    private widget.Label jLabel91;
    private widget.Label jLabel92;
    private widget.Label jLabel93;
    private widget.Label jLabel94;
    private widget.Label jLabel95;
    private widget.Label jLabel96;
    private widget.Label jLabel97;
    private widget.Label jLabel98;
    private widget.Label jLabel99;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator35;
    private javax.swing.JSeparator jSeparator36;
    private javax.swing.JSeparator jSeparator37;
    private javax.swing.JSeparator jSeparator38;
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
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,reg_periksa.umurdaftar,reg_periksa.sttsumur,skrining_frailty_syndrome.nip,"+
                    "petugas.nama,skrining_frailty_syndrome.tanggal,skrining_frailty_syndrome.resistensi,skrining_frailty_syndrome.nilai_resistensi,skrining_frailty_syndrome.aktivitas,"+
                    "skrining_frailty_syndrome.nilai_aktivitas,skrining_frailty_syndrome.penyakit_tidak_pernah,skrining_frailty_syndrome.penyakit_kanker,skrining_frailty_syndrome.penyakit_gagal_jantung,"+
                    "skrining_frailty_syndrome.penyakit_ginjal,skrining_frailty_syndrome.penyakit_nyeri_dada,skrining_frailty_syndrome.penyakit_serangan_jantung,skrining_frailty_syndrome.penyakit_stroke,"+
                    "skrining_frailty_syndrome.penyakit_asma,skrining_frailty_syndrome.penyakit_nyeri_sendi,skrining_frailty_syndrome.penyakit_paru_kronis,skrining_frailty_syndrome.penyakit_hipertensi,"+
                    "skrining_frailty_syndrome.penyakit_diabetes,skrining_frailty_syndrome.nilai_penyakit,skrining_frailty_syndrome.usaha_berjalan,skrining_frailty_syndrome.nilai_usaha_berjalan,"+
                    "skrining_frailty_syndrome.berat_badan,skrining_frailty_syndrome.nilai_berat_badan,skrining_frailty_syndrome.nilai_total,skrining_frailty_syndrome.hasil_skrining,"+
                    "skrining_frailty_syndrome.keterangan from skrining_frailty_syndrome inner join reg_periksa on skrining_frailty_syndrome.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join petugas on skrining_frailty_syndrome.nip=petugas.nip "+
                    "where skrining_frailty_syndrome.tanggal between ? and ? order by skrining_frailty_syndrome.tanggal ");
            }else{
                ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,reg_periksa.umurdaftar,reg_periksa.sttsumur,skrining_frailty_syndrome.nip,"+
                    "petugas.nama,skrining_frailty_syndrome.tanggal,skrining_frailty_syndrome.resistensi,skrining_frailty_syndrome.nilai_resistensi,skrining_frailty_syndrome.aktivitas,"+
                    "skrining_frailty_syndrome.nilai_aktivitas,skrining_frailty_syndrome.penyakit_tidak_pernah,skrining_frailty_syndrome.penyakit_kanker,skrining_frailty_syndrome.penyakit_gagal_jantung,"+
                    "skrining_frailty_syndrome.penyakit_ginjal,skrining_frailty_syndrome.penyakit_nyeri_dada,skrining_frailty_syndrome.penyakit_serangan_jantung,skrining_frailty_syndrome.penyakit_stroke,"+
                    "skrining_frailty_syndrome.penyakit_asma,skrining_frailty_syndrome.penyakit_nyeri_sendi,skrining_frailty_syndrome.penyakit_paru_kronis,skrining_frailty_syndrome.penyakit_hipertensi,"+
                    "skrining_frailty_syndrome.penyakit_diabetes,skrining_frailty_syndrome.nilai_penyakit,skrining_frailty_syndrome.usaha_berjalan,skrining_frailty_syndrome.nilai_usaha_berjalan,"+
                    "skrining_frailty_syndrome.berat_badan,skrining_frailty_syndrome.nilai_berat_badan,skrining_frailty_syndrome.nilai_total,skrining_frailty_syndrome.hasil_skrining,"+
                    "skrining_frailty_syndrome.keterangan from skrining_frailty_syndrome inner join reg_periksa on skrining_frailty_syndrome.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join petugas on skrining_frailty_syndrome.nip=petugas.nip "+
                    "where skrining_frailty_syndrome.tanggal between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or "+
                    "pasien.nm_pasien like ? or skrining_frailty_syndrome.nip like ? or petugas.nama like ?) "+
                    "order by skrining_frailty_syndrome.tanggal ");
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
                }
                    
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new Object[]{
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getDate("tgl_lahir"),rs.getString("umurdaftar")+" "+rs.getString("sttsumur"),
                        rs.getString("nip"),rs.getString("nama"),rs.getString("tanggal"),rs.getString("resistensi"),rs.getString("nilai_resistensi"),rs.getString("aktivitas"),rs.getString("nilai_aktivitas"),
                        rs.getString("penyakit_tidak_pernah"),rs.getString("penyakit_kanker"),rs.getString("penyakit_gagal_jantung"),rs.getString("penyakit_ginjal"),rs.getString("penyakit_nyeri_dada"),
                        rs.getString("penyakit_serangan_jantung"),rs.getString("penyakit_stroke"),rs.getString("penyakit_asma"),rs.getString("penyakit_nyeri_sendi"),rs.getString("penyakit_paru_kronis"),
                        rs.getString("penyakit_hipertensi"),rs.getString("penyakit_diabetes"),rs.getString("nilai_penyakit"),rs.getString("usaha_berjalan"),rs.getString("nilai_usaha_berjalan"),
                        rs.getString("berat_badan"),rs.getString("nilai_berat_badan"),rs.getString("nilai_total"),rs.getString("hasil_skrining"),rs.getString("keterangan")
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
        Tanggal.setDate(new Date());
        Resistensi.setSelectedIndex(0);
        Aktivitas.setSelectedIndex(0);
        UsahaBerjalan.setSelectedIndex(0);
        BeratBadan.setSelectedIndex(0);
        NilaiResistensi.setText("0");
        NilaiAktivitas.setText("0");
        NilaiPenyakit.setText("0");
        NilaiUsahaBerjalan.setText("0");
        NilaiBeratBadan.setText("0");
        ChkTidakPernah.setSelected(false);
        ChkKanker.setSelected(false);
        ChkGagalJantung.setSelected(false);
        ChkPenyakitGinjal.setSelected(false);
        ChkNyeriDada.setSelected(false);
        ChkSeranganJantung.setSelected(false);
        ChkStroke.setSelected(false);
        ChkAsma.setSelected(false);
        ChkNyeriSendi.setSelected(false);
        ChkPenyakitParuKronis.setSelected(false);
        ChkHipertensi.setSelected(false);
        ChkDiabetes.setSelected(false);
        TotalHasil.setText("0");
        HasilSkrining.setText("Segar & Tidak Rapuh");
        Keterangan.setText("");
        Resistensi.requestFocus();
    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            Umur.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString());
            Jam.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString().substring(11,13));
            Menit.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString().substring(14,15));
            Detik.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString().substring(17,19));
            Resistensi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            NilaiResistensi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            Aktivitas.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            NilaiAktivitas.setText(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            if(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString().equals("Ya")){
                ChkTidakPernah.setSelected(true);
            }else{
                ChkTidakPernah.setSelected(false); 
            }
            if(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString().equals("Ya")){
                ChkKanker.setSelected(true);
            }else{
                ChkKanker.setSelected(false); 
            }
            if(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString().equals("Ya")){
                ChkGagalJantung.setSelected(true);
            }else{
                ChkGagalJantung.setSelected(false); 
            }
            if(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString().equals("Ya")){
                ChkPenyakitGinjal.setSelected(true);
            }else{
                ChkPenyakitGinjal.setSelected(false); 
            }
            if(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString().equals("Ya")){
                ChkNyeriDada.setSelected(true);
            }else{
                ChkNyeriDada.setSelected(false); 
            }
            if(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString().equals("Ya")){
                ChkSeranganJantung.setSelected(true);
            }else{
                ChkSeranganJantung.setSelected(false); 
            }
            if(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString().equals("Ya")){
                ChkStroke.setSelected(true);
            }else{
                ChkStroke.setSelected(false); 
            }
            if(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString().equals("Ya")){
                ChkAsma.setSelected(true);
            }else{
                ChkAsma.setSelected(false); 
            }
            if(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString().equals("Ya")){
                ChkNyeriSendi.setSelected(true);
            }else{
                ChkNyeriSendi.setSelected(false); 
            }
            if(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString().equals("Ya")){
                ChkPenyakitParuKronis.setSelected(true);
            }else{
                ChkPenyakitParuKronis.setSelected(false); 
            }
            if(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString().equals("Ya")){
                ChkHipertensi.setSelected(true);
            }else{
                ChkHipertensi.setSelected(false); 
            }
            if(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString().equals("Ya")){
                ChkDiabetes.setSelected(true);
            }else{
                ChkDiabetes.setSelected(false); 
            }
            NilaiPenyakit.setText(tbObat.getValueAt(tbObat.getSelectedRow(),24).toString());
            UsahaBerjalan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),25).toString());
            NilaiUsahaBerjalan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),26).toString());
            BeratBadan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),27).toString());
            NilaiBeratBadan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),28).toString());
            TotalHasil.setText(tbObat.getValueAt(tbObat.getSelectedRow(),29).toString());
            HasilSkrining.setText(tbObat.getValueAt(tbObat.getSelectedRow(),30).toString());
            Keterangan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),31).toString());
            Valid.SetTgl(Tanggal,tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());  
        }
    }
    
    private void isRawat() {
        try {
            ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,pasien.jk,"+
                    "reg_periksa.tgl_registrasi,reg_periksa.jam_reg,reg_periksa.umurdaftar,reg_periksa.sttsumur "+
                    "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "where reg_periksa.no_rawat=?");
            try {
                ps.setString(1,TNoRw.getText());
                rs=ps.executeQuery();
                if(rs.next()){
                    TNoRM.setText(rs.getString("no_rkm_medis"));
                    DTPCari1.setDate(rs.getDate("tgl_registrasi"));
                    TPasien.setText(rs.getString("nm_pasien"));
                    TglLahir.setText(rs.getString("tgl_lahir"));
                    TanggalRegistrasi.setText(rs.getString("tgl_registrasi")+" "+rs.getString("jam_reg"));
                    Umur.setText(rs.getString("umurdaftar")+" "+rs.getString("sttsumur"));
                    if(rs.getString("jk").equals("L")){
                        Resistensi.setSelectedIndex(0);
                    }else{
                        Resistensi.setSelectedIndex(1);
                    }
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
        ChkInput.setSelected(true);
        isForm();
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            if(internalFrame1.getHeight()>657){
                ChkInput.setVisible(false);
                PanelInput.setPreferredSize(new Dimension(WIDTH,485));
                FormInput.setVisible(true);      
                ChkInput.setVisible(true);
            }else{
                ChkInput.setVisible(false);
                PanelInput.setPreferredSize(new Dimension(WIDTH,internalFrame1.getHeight()-175));
                FormInput.setVisible(true);      
                ChkInput.setVisible(true);
            }
        }else if(ChkInput.isSelected()==false){           
            ChkInput.setVisible(false);            
            PanelInput.setPreferredSize(new Dimension(WIDTH,20));
            FormInput.setVisible(false);      
            ChkInput.setVisible(true);
        }
    }
    
    public void isCek(){
        BtnSimpan.setEnabled(akses.getskrining_frailty_syndrome());
        BtnHapus.setEnabled(akses.getskrining_frailty_syndrome());
        BtnEdit.setEnabled(akses.getskrining_frailty_syndrome());
        BtnPrint.setEnabled(akses.getskrining_frailty_syndrome()); 
        if(akses.getjml2()>=1){
            KdPetugas.setEditable(false);
            btnPetugas.setEnabled(false);
            KdPetugas.setText(akses.getkode());
            NmPetugas.setText(petugas.tampil3(KdPetugas.getText()));
            if(NmPetugas.getText().equals("")){
                KdPetugas.setText("");
                JOptionPane.showMessageDialog(null,"User login bukan petugas...!!");
            }
        }  
        
        if(TANGGALMUNDUR.equals("no")){
            if(!akses.getkode().equals("Admin Utama")){
                Tanggal.setEditable(false);
                Tanggal.setEnabled(false);
                ChkKejadian.setEnabled(false);
                Jam.setEnabled(false);
                Menit.setEnabled(false);
                Detik.setEnabled(false);
            }
        }
    }

    private void jam(){
        ActionListener taskPerformer = new ActionListener(){
            private int nilai_jam;
            private int nilai_menit;
            private int nilai_detik;
            public void actionPerformed(ActionEvent e) {
                String nol_jam = "";
                String nol_menit = "";
                String nol_detik = "";
                
                Date now = Calendar.getInstance().getTime();

                // Mengambil nilaj JAM, MENIT, dan DETIK Sekarang
                if(ChkKejadian.isSelected()==true){
                    nilai_jam = now.getHours();
                    nilai_menit = now.getMinutes();
                    nilai_detik = now.getSeconds();
                }else if(ChkKejadian.isSelected()==false){
                    nilai_jam =Jam.getSelectedIndex();
                    nilai_menit =Menit.getSelectedIndex();
                    nilai_detik =Detik.getSelectedIndex();
                }

                // Jika nilai JAM lebih kecil dari 10 (hanya 1 digit)
                if (nilai_jam <= 9) {
                    // Tambahkan "0" didepannya
                    nol_jam = "0";
                }
                // Jika nilai MENIT lebih kecil dari 10 (hanya 1 digit)
                if (nilai_menit <= 9) {
                    // Tambahkan "0" didepannya
                    nol_menit = "0";
                }
                // Jika nilai DETIK lebih kecil dari 10 (hanya 1 digit)
                if (nilai_detik <= 9) {
                    // Tambahkan "0" didepannya
                    nol_detik = "0";
                }
                // Membuat String JAM, MENIT, DETIK
                String jam = nol_jam + Integer.toString(nilai_jam);
                String menit = nol_menit + Integer.toString(nilai_menit);
                String detik = nol_detik + Integer.toString(nilai_detik);
                // Menampilkan pada Layar
                //tampil_jam.setText("  " + jam + " : " + menit + " : " + detik + "  ");
                Jam.setSelectedItem(jam);
                Menit.setSelectedItem(menit);
                Detik.setSelectedItem(detik);
            }
        };
        // Timer
        new Timer(1000, taskPerformer).start();
    }

    private void ganti() {
        if(Sequel.mengedittf("skrining_frailty_syndrome","no_rawat=?","no_rawat=?,tanggal=?,resistensi=?,nilai_resistensi=?,aktivitas=?,nilai_aktivitas=?,penyakit_tidak_pernah=?,penyakit_kanker=?,"+
                "penyakit_gagal_jantung=?,penyakit_ginjal=?,penyakit_nyeri_dada=?,penyakit_serangan_jantung=?,penyakit_stroke=?,penyakit_asma=?,penyakit_nyeri_sendi=?,penyakit_paru_kronis=?,penyakit_hipertensi=?,"+
                "penyakit_diabetes=?,nilai_penyakit=?,usaha_berjalan=?,nilai_usaha_berjalan=?,berat_badan=?,nilai_berat_badan=?,nilai_total=?,hasil_skrining=?,keterangan=?,nip=?",28,new String[]{
                TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),Resistensi.getSelectedItem().toString(),NilaiResistensi.getText(),Aktivitas.getSelectedItem().toString(),NilaiAktivitas.getText(),
                (ChkTidakPernah.isSelected()==true?"Ya":"Tidak"),(ChkKanker.isSelected()==true?"Ya":"Tidak"),(ChkGagalJantung.isSelected()==true?"Ya":"Tidak"),(ChkPenyakitGinjal.isSelected()==true?"Ya":"Tidak"),(ChkNyeriDada.isSelected()==true?"Ya":"Tidak"),(ChkSeranganJantung.isSelected()==true?"Ya":"Tidak"), 
                (ChkStroke.isSelected()==true?"Ya":"Tidak"),(ChkAsma.isSelected()==true?"Ya":"Tidak"),(ChkNyeriSendi.isSelected()==true?"Ya":"Tidak"),(ChkPenyakitParuKronis.isSelected()==true?"Ya":"Tidak"),(ChkHipertensi.isSelected()==true?"Ya":"Tidak"),(ChkDiabetes.isSelected()==true?"Ya":"Tidak"),
                NilaiPenyakit.getText(),UsahaBerjalan.getSelectedItem().toString(),NilaiUsahaBerjalan.getText(),BeratBadan.getSelectedItem().toString(),NilaiBeratBadan.getText(),TotalHasil.getText(),HasilSkrining.getText(),Keterangan.getText(),KdPetugas.getText(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
            })==true){
               tbObat.setValueAt(TNoRw.getText(),tbObat.getSelectedRow(),0);
               tbObat.setValueAt(TNoRM.getText(),tbObat.getSelectedRow(),1);
               tbObat.setValueAt(TPasien.getText(),tbObat.getSelectedRow(),2);
               tbObat.setValueAt(TglLahir.getText(),tbObat.getSelectedRow(),3);
               tbObat.setValueAt(Umur.getText(),tbObat.getSelectedRow(),4);
               tbObat.setValueAt(KdPetugas.getText(),tbObat.getSelectedRow(),5);
               tbObat.setValueAt(NmPetugas.getText(),tbObat.getSelectedRow(),6);
               tbObat.setValueAt(Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),tbObat.getSelectedRow(),7);
               tbObat.setValueAt(Resistensi.getSelectedItem().toString(),tbObat.getSelectedRow(),8);
               tbObat.setValueAt(NilaiResistensi.getText(),tbObat.getSelectedRow(),9);
               tbObat.setValueAt(Aktivitas.getSelectedItem().toString(),tbObat.getSelectedRow(),10);
               tbObat.setValueAt(NilaiAktivitas.getText(),tbObat.getSelectedRow(),11);
               tbObat.setValueAt((ChkTidakPernah.isSelected()==true?"Ya":"Tidak"),tbObat.getSelectedRow(),12);
               tbObat.setValueAt((ChkKanker.isSelected()==true?"Ya":"Tidak"),tbObat.getSelectedRow(),13);
               tbObat.setValueAt((ChkGagalJantung.isSelected()==true?"Ya":"Tidak"),tbObat.getSelectedRow(),14);
               tbObat.setValueAt((ChkPenyakitGinjal.isSelected()==true?"Ya":"Tidak"),tbObat.getSelectedRow(),15);
               tbObat.setValueAt((ChkNyeriDada.isSelected()==true?"Ya":"Tidak"),tbObat.getSelectedRow(),16);
               tbObat.setValueAt((ChkSeranganJantung.isSelected()==true?"Ya":"Tidak"),tbObat.getSelectedRow(),17);
               tbObat.setValueAt((ChkStroke.isSelected()==true?"Ya":"Tidak"),tbObat.getSelectedRow(),18);
               tbObat.setValueAt((ChkAsma.isSelected()==true?"Ya":"Tidak"),tbObat.getSelectedRow(),19);
               tbObat.setValueAt((ChkNyeriSendi.isSelected()==true?"Ya":"Tidak"),tbObat.getSelectedRow(),20);
               tbObat.setValueAt((ChkPenyakitParuKronis.isSelected()==true?"Ya":"Tidak"),tbObat.getSelectedRow(),21);
               tbObat.setValueAt((ChkHipertensi.isSelected()==true?"Ya":"Tidak"),tbObat.getSelectedRow(),22);
               tbObat.setValueAt((ChkDiabetes.isSelected()==true?"Ya":"Tidak"),tbObat.getSelectedRow(),23);
               tbObat.setValueAt(NilaiPenyakit.getText(),tbObat.getSelectedRow(),24);
               tbObat.setValueAt(UsahaBerjalan.getSelectedItem().toString(),tbObat.getSelectedRow(),25);
               tbObat.setValueAt(NilaiUsahaBerjalan.getText(),tbObat.getSelectedRow(),26);
               tbObat.setValueAt(BeratBadan.getSelectedItem().toString(),tbObat.getSelectedRow(),27);
               tbObat.setValueAt(NilaiBeratBadan.getText(),tbObat.getSelectedRow(),28);
               tbObat.setValueAt(TotalHasil.getText(),tbObat.getSelectedRow(),29);
               tbObat.setValueAt(HasilSkrining.getText(),tbObat.getSelectedRow(),30);
               tbObat.setValueAt(Keterangan.getText(),tbObat.getSelectedRow(),31);
               emptTeks();
        }
    }

    private void hapus() {
        if(Sequel.queryu2tf("delete from skrining_frailty_syndrome where no_rawat=?",1,new String[]{
            tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
        })==true){
            tabMode.removeRow(tbObat.getSelectedRow());
            LCount.setText(""+tabMode.getRowCount());
            emptTeks();
        }else{
            JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
        }
    }

    private void isTotal() {
        try {
            TotalHasil.setText(""+(Integer.parseInt(NilaiResistensi.getText())+Integer.parseInt(NilaiAktivitas.getText())+Integer.parseInt(NilaiPenyakit.getText())+Integer.parseInt(NilaiUsahaBerjalan.getText())+Integer.parseInt(NilaiBeratBadan.getText())));
            if(Integer.parseInt(TotalHasil.getText())>2){
                HasilSkrining.setText("Rapuh/Renta");
            }else if(Integer.parseInt(TotalHasil.getText())>0){
                HasilSkrining.setText("Pra-Kerapuhan");
            }else if(Integer.parseInt(TotalHasil.getText())==0){
                HasilSkrining.setText("Segar & Tidak Rapuh");
            }
        } catch (Exception e) {
            HasilSkrining.setText("Segar & Tidak Rapuh");
        }
    }
    
    private void isPenyakit() {
        ChkTidakPernah.setSelected(false);
        i=0;
        if(ChkKanker.isSelected()==true){
            i++;
        }
        if(ChkGagalJantung.isSelected()==true){
            i++;
        }
        if(ChkPenyakitGinjal.isSelected()==true){
            i++;
        }
        if(ChkNyeriDada.isSelected()==true){
            i++;
        }
        if(ChkSeranganJantung.isSelected()==true){
            i++;
        }
        if(ChkStroke.isSelected()==true){
            i++;
        }
        if(ChkAsma.isSelected()==true){
            i++;
        }
        if(ChkNyeriSendi.isSelected()==true){
            i++;
        }
        if(ChkPenyakitParuKronis.isSelected()==true){
            i++;
        }
        if(ChkHipertensi.isSelected()==true){
            i++;
        }
        if(ChkDiabetes.isSelected()==true){
            i++;
        }
        if(i>4){
            NilaiPenyakit.setText("1");
        }else{
            NilaiPenyakit.setText("0");
        }
        isTotal();
    }

    private void simpan() {
        if(Sequel.menyimpantf("skrining_frailty_syndrome","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","Data",27,new String[]{
            TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),Resistensi.getSelectedItem().toString(),NilaiResistensi.getText(),Aktivitas.getSelectedItem().toString(),NilaiAktivitas.getText(),
            (ChkTidakPernah.isSelected()==true?"Ya":"Tidak"),(ChkKanker.isSelected()==true?"Ya":"Tidak"),(ChkGagalJantung.isSelected()==true?"Ya":"Tidak"),(ChkPenyakitGinjal.isSelected()==true?"Ya":"Tidak"),(ChkNyeriDada.isSelected()==true?"Ya":"Tidak"),(ChkSeranganJantung.isSelected()==true?"Ya":"Tidak"), 
            (ChkStroke.isSelected()==true?"Ya":"Tidak"),(ChkAsma.isSelected()==true?"Ya":"Tidak"),(ChkNyeriSendi.isSelected()==true?"Ya":"Tidak"),(ChkPenyakitParuKronis.isSelected()==true?"Ya":"Tidak"),(ChkHipertensi.isSelected()==true?"Ya":"Tidak"),(ChkDiabetes.isSelected()==true?"Ya":"Tidak"),
            NilaiPenyakit.getText(),UsahaBerjalan.getSelectedItem().toString(),NilaiUsahaBerjalan.getText(),BeratBadan.getSelectedItem().toString(),NilaiBeratBadan.getText(),TotalHasil.getText(),HasilSkrining.getText(),Keterangan.getText(),KdPetugas.getText()
        })==true){
            tabMode.addRow(new Object[]{
                TNoRw.getText(),TNoRM.getText(),TPasien.getText(),TglLahir.getText(),Umur.getText(),KdPetugas.getText(),NmPetugas.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),
                Resistensi.getSelectedItem().toString(),NilaiResistensi.getText(),Aktivitas.getSelectedItem().toString(),NilaiAktivitas.getText(),
                (ChkTidakPernah.isSelected()==true?"Ya":"Tidak"),(ChkKanker.isSelected()==true?"Ya":"Tidak"),(ChkGagalJantung.isSelected()==true?"Ya":"Tidak"),(ChkPenyakitGinjal.isSelected()==true?"Ya":"Tidak"),(ChkNyeriDada.isSelected()==true?"Ya":"Tidak"),(ChkSeranganJantung.isSelected()==true?"Ya":"Tidak"), 
                (ChkStroke.isSelected()==true?"Ya":"Tidak"),(ChkAsma.isSelected()==true?"Ya":"Tidak"),(ChkNyeriSendi.isSelected()==true?"Ya":"Tidak"),(ChkPenyakitParuKronis.isSelected()==true?"Ya":"Tidak"),(ChkHipertensi.isSelected()==true?"Ya":"Tidak"),(ChkDiabetes.isSelected()==true?"Ya":"Tidak"),
                NilaiPenyakit.getText(),UsahaBerjalan.getSelectedItem().toString(),NilaiUsahaBerjalan.getText(),BeratBadan.getSelectedItem().toString(),NilaiBeratBadan.getText(),TotalHasil.getText(),HasilSkrining.getText(),Keterangan.getText()
            });
            LCount.setText(""+tabMode.getRowCount());
            emptTeks();
        } 
    }
    
}

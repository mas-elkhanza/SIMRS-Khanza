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
public final class RMSkriningTBC extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;    
    private DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    private String finger="";
    private StringBuilder htmlContent;
    private String TANGGALMUNDUR="yes";
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public RMSkriningTBC(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(628,674);

        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.RM","Nama Pasien","Tgl.Lahir","J.K.","Kode Petugas","Nama Petugas","Tanggal",
            "BB(Kg)","TB(Cm)","IMT","Kasifikasi IMT","LP(Cm)","Risiko L.P.","Riwayat Kontak TBC","Jenis Kontak TBC",
            "Pernah Terdiagnosa TBC","Jika Ya Kapan ?","Pernah Berobat TBC","Malnutrisi","Merokok","Riwayat DM","ODHIV",
            "Lansia > 65 Tahun","Ibu Hamil","Warga Binaan Permasyarakatan (WBP)","Tinggal Di Wilayah Padat Kumuh Miskin",
            "Abnormalitas TBC","Gejala Batuk","Gejala BB Turun","Gejala Demam","Gejala Berkeringat Malam Hari",
            "Gejala Penyakit Lain","Kesimpulan Skrining","Keterangan"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 35; i++) {
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
                column.setPreferredWidth(35);
            }else if(i==5){
                column.setPreferredWidth(80);
            }else if(i==6){
                column.setPreferredWidth(150);
            }else if(i==7){
                column.setPreferredWidth(120);
            }else if(i==8){
                column.setPreferredWidth(48);
            }else if(i==9){
                column.setPreferredWidth(48);
            }else if(i==10){
                column.setPreferredWidth(50);
            }else if(i==11){
                column.setPreferredWidth(120);
            }else if(i==12){
                column.setPreferredWidth(48);
            }else if(i==13){
                column.setPreferredWidth(120);
            }else if(i==14){
                column.setPreferredWidth(105);
            }else if(i==15){
                column.setPreferredWidth(118);
            }else if(i==16){
                column.setPreferredWidth(130);
            }else if(i==17){
                column.setPreferredWidth(150);
            }else if(i==18){
                column.setPreferredWidth(105);
            }else if(i==19){
                column.setPreferredWidth(56);
            }else if(i==20){
                column.setPreferredWidth(48);
            }else if(i==21){
                column.setPreferredWidth(66);
            }else if(i==22){
                column.setPreferredWidth(43);
            }else if(i==23){
                column.setPreferredWidth(99);
            }else if(i==24){
                column.setPreferredWidth(58);
            }else if(i==25){
                column.setPreferredWidth(198);
            }else if(i==26){
                column.setPreferredWidth(200);
            }else if(i==27){
                column.setPreferredWidth(97);
            }else if(i==28){
                column.setPreferredWidth(75);
            }else if(i==29){
                column.setPreferredWidth(85);
            }else if(i==30){
                column.setPreferredWidth(81);
            }else if(i==31){
                column.setPreferredWidth(156);
            }else if(i==32){
                column.setPreferredWidth(200);
            }else if(i==33){
                column.setPreferredWidth(108);
            }else if(i==34){
                column.setPreferredWidth(200);
            }
            
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());

        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        KdPetugas.setDocument(new batasInput((byte)20).getKata(KdPetugas));
        TB.setDocument(new batasInput((byte)8).getOnlyAngka(TB));
        BB.setDocument(new batasInput((byte)6).getOnlyAngka(BB));
        LP.setDocument(new batasInput((byte)6).getOnlyAngka(LP));
        KetGejalaPenyakit.setDocument(new batasInput((byte)40).getKata(KetGejalaPenyakit));
        KetHasilSkrining.setDocument(new batasInput((byte)40).getKata(KetHasilSkrining));
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
        
        BB.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {
                isBMI();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                isBMI();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                isBMI();
            }
        });
        
        TB.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {
                isBMI();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                isBMI();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                isBMI();
            }
        });
        
        IMT.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {
                isLP();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                isLP();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                isLP();
            }
        });
        
        LP.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {
                isLP();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                isLP();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                isLP();
            }
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
        MnSkriningTBC = new javax.swing.JMenuItem();
        buttonGroup1 = new javax.swing.ButtonGroup();
        LoadHTML = new widget.editorpane();
        Jk = new widget.TextBox();
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
        jLabel12 = new widget.Label();
        jLabel13 = new widget.Label();
        TB = new widget.TextBox();
        jLabel15 = new widget.Label();
        jLabel25 = new widget.Label();
        PernahTerdiagnosaTBC = new widget.ComboBox();
        RisikoLP = new widget.TextBox();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel99 = new widget.Label();
        jLabel77 = new widget.Label();
        jLabel78 = new widget.Label();
        jLabel92 = new widget.Label();
        jLabel79 = new widget.Label();
        jLabel80 = new widget.Label();
        PernahberobatTBC = new widget.ComboBox();
        Malnutrisi = new widget.ComboBox();
        jLabel81 = new widget.Label();
        jLabel82 = new widget.Label();
        jLabel83 = new widget.Label();
        jLabel84 = new widget.Label();
        jLabel85 = new widget.Label();
        Merokok = new widget.ComboBox();
        jLabel86 = new widget.Label();
        jLabel87 = new widget.Label();
        jLabel88 = new widget.Label();
        Odhiv = new widget.ComboBox();
        RiwayatDM = new widget.ComboBox();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel100 = new widget.Label();
        jLabel17 = new widget.Label();
        IMT = new widget.TextBox();
        KlasifikasiIMT = new widget.TextBox();
        jLabel20 = new widget.Label();
        LP = new widget.TextBox();
        jLabel26 = new widget.Label();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel101 = new widget.Label();
        KesimpulanHasilSkrining = new widget.TextBox();
        jLabel22 = new widget.Label();
        jLabel93 = new widget.Label();
        KetHasilSkrining = new widget.TextBox();
        jLabel23 = new widget.Label();
        jLabel5 = new widget.Label();
        jLabel9 = new widget.Label();
        jLabel24 = new widget.Label();
        jLabel94 = new widget.Label();
        jLabel27 = new widget.Label();
        jLabel102 = new widget.Label();
        jLabel90 = new widget.Label();
        RiwayatKontakTBC = new widget.ComboBox();
        jLabel95 = new widget.Label();
        JenisKontakTBC = new widget.ComboBox();
        jSeparator4 = new javax.swing.JSeparator();
        BB = new widget.TextBox();
        jLabel96 = new widget.Label();
        jLabel97 = new widget.Label();
        jLabel98 = new widget.Label();
        Lansia = new widget.ComboBox();
        jLabel103 = new widget.Label();
        jLabel104 = new widget.Label();
        WargaBinaan = new widget.ComboBox();
        jLabel105 = new widget.Label();
        jLabel106 = new widget.Label();
        Tingaldiwilayahkumuh = new widget.ComboBox();
        jLabel107 = new widget.Label();
        jLabel108 = new widget.Label();
        Ibuhamil = new widget.ComboBox();
        AbnormalitasTBC = new widget.ComboBox();
        jLabel109 = new widget.Label();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel110 = new widget.Label();
        jLabel111 = new widget.Label();
        jLabel112 = new widget.Label();
        Batuk = new widget.ComboBox();
        jLabel113 = new widget.Label();
        jLabel114 = new widget.Label();
        BBTurun = new widget.ComboBox();
        jLabel115 = new widget.Label();
        jLabel116 = new widget.Label();
        Demam = new widget.ComboBox();
        jLabel117 = new widget.Label();
        jLabel118 = new widget.Label();
        BerkeringatMalam = new widget.ComboBox();
        jSeparator6 = new javax.swing.JSeparator();
        jLabel119 = new widget.Label();
        KetGejalaPenyakit = new widget.TextBox();
        jLabel120 = new widget.Label();
        KetPernahTerdiagnosa = new widget.TextBox();
        jLabel8 = new widget.Label();
        TglLahir = new widget.TextBox();
        jLabel10 = new widget.Label();
        jLabel89 = new widget.Label();
        jLabel11 = new widget.Label();
        jLabel14 = new widget.Label();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnSkriningTBC.setBackground(new java.awt.Color(255, 255, 254));
        MnSkriningTBC.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSkriningTBC.setForeground(new java.awt.Color(50, 50, 50));
        MnSkriningTBC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnSkriningTBC.setText("Formulir Skrining TBC");
        MnSkriningTBC.setName("MnSkriningTBC"); // NOI18N
        MnSkriningTBC.setPreferredSize(new java.awt.Dimension(200, 26));
        MnSkriningTBC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSkriningTBCActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnSkriningTBC);

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N

        Jk.setEditable(false);
        Jk.setFocusTraversalPolicyProvider(true);
        Jk.setName("Jk"); // NOI18N

        TanggalRegistrasi.setHighlighter(null);
        TanggalRegistrasi.setName("TanggalRegistrasi"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(462, 769));
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Skrining TBC ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setPreferredSize(new java.awt.Dimension(462, 1300));
        internalFrame1.setRequestFocusEnabled(false);
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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "26-02-2025" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "26-02-2025" }));
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
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 446));
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
        FormInput.setPreferredSize(new java.awt.Dimension(100, 613));
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
        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "26-02-2025" }));
        Tanggal.setDisplayFormat("dd-MM-yyyy");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.setOpaque(false);
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeyPressed(evt);
            }
        });
        FormInput.add(Tanggal);
        Tanggal.setBounds(80, 40, 90, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        TNoRM.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRMKeyPressed(evt);
            }
        });
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

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel12.setText("BB ");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(44, 90, 40, 23);

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel13.setText("Kg");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(120, 90, 25, 23);

        TB.setFocusTraversalPolicyProvider(true);
        TB.setName("TB"); // NOI18N
        TB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TBKeyPressed(evt);
            }
        });
        FormInput.add(TB);
        TB.setBounds(204, 90, 50, 23);

        jLabel15.setText(":");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(150, 90, 50, 23);

        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel25.setText("Cm");
        jLabel25.setName("jLabel25"); // NOI18N
        FormInput.add(jLabel25);
        jLabel25.setBounds(256, 90, 20, 23);

        PernahTerdiagnosaTBC.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        PernahTerdiagnosaTBC.setName("PernahTerdiagnosaTBC"); // NOI18N
        PernahTerdiagnosaTBC.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PernahTerdiagnosaTBCKeyPressed(evt);
            }
        });
        FormInput.add(PernahTerdiagnosaTBC);
        PernahTerdiagnosaTBC.setBounds(320, 220, 80, 23);

        RisikoLP.setEditable(false);
        RisikoLP.setFocusTraversalPolicyProvider(true);
        RisikoLP.setName("RisikoLP"); // NOI18N
        FormInput.add(RisikoLP);
        RisikoLP.setBounds(370, 120, 170, 23);

        jSeparator1.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator1.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator1.setName("jSeparator1"); // NOI18N
        FormInput.add(jSeparator1);
        jSeparator1.setBounds(0, 70, 807, 1);

        jLabel99.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel99.setText("II. PEMERIKSAAN RIWAYAT KONTAK TBC");
        jLabel99.setName("jLabel99"); // NOI18N
        FormInput.add(jLabel99);
        jLabel99.setBounds(10, 150, 200, 23);

        jLabel77.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel77.setText("1.");
        jLabel77.setName("jLabel77"); // NOI18N
        FormInput.add(jLabel77);
        jLabel77.setBounds(44, 220, 20, 23);

        jLabel78.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel78.setText("Pernah Terdiagnosa / Berobat TBC ?");
        jLabel78.setName("jLabel78"); // NOI18N
        FormInput.add(jLabel78);
        jLabel78.setBounds(60, 220, 200, 23);

        jLabel92.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel92.setText("Risiko Berdasarkan Lingkar Pinggang");
        jLabel92.setName("jLabel92"); // NOI18N
        FormInput.add(jLabel92);
        jLabel92.setBounds(180, 120, 200, 23);

        jLabel79.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel79.setText("2.");
        jLabel79.setName("jLabel79"); // NOI18N
        FormInput.add(jLabel79);
        jLabel79.setBounds(44, 250, 20, 23);

        jLabel80.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel80.setText("Pernah Berobat TBC Tapi Pernah Tidak Tuntas");
        jLabel80.setName("jLabel80"); // NOI18N
        FormInput.add(jLabel80);
        jLabel80.setBounds(60, 280, 240, 23);

        PernahberobatTBC.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        PernahberobatTBC.setName("PernahberobatTBC"); // NOI18N
        PernahberobatTBC.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PernahberobatTBCKeyPressed(evt);
            }
        });
        FormInput.add(PernahberobatTBC);
        PernahberobatTBC.setBounds(320, 280, 80, 23);

        Malnutrisi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Malnutrisi.setName("Malnutrisi"); // NOI18N
        Malnutrisi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MalnutrisiKeyPressed(evt);
            }
        });
        FormInput.add(Malnutrisi);
        Malnutrisi.setBounds(320, 310, 80, 23);

        jLabel81.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel81.setText("Malnutrisi");
        jLabel81.setName("jLabel81"); // NOI18N
        FormInput.add(jLabel81);
        jLabel81.setBounds(60, 310, 200, 23);

        jLabel82.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel82.setText("4.");
        jLabel82.setName("jLabel82"); // NOI18N
        FormInput.add(jLabel82);
        jLabel82.setBounds(44, 310, 20, 23);

        jLabel83.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel83.setText("Merokok / Perokok Pasif ?");
        jLabel83.setName("jLabel83"); // NOI18N
        FormInput.add(jLabel83);
        jLabel83.setBounds(60, 340, 200, 23);

        jLabel84.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel84.setText("5.");
        jLabel84.setName("jLabel84"); // NOI18N
        FormInput.add(jLabel84);
        jLabel84.setBounds(44, 340, 9, 23);

        jLabel85.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel85.setText("Riwayat DM / Kencing Manis ?");
        jLabel85.setName("jLabel85"); // NOI18N
        FormInput.add(jLabel85);
        jLabel85.setBounds(60, 370, 210, 23);

        Merokok.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Merokok.setName("Merokok"); // NOI18N
        Merokok.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MerokokKeyPressed(evt);
            }
        });
        FormInput.add(Merokok);
        Merokok.setBounds(320, 340, 80, 23);

        jLabel86.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel86.setText("6.");
        jLabel86.setName("jLabel86"); // NOI18N
        FormInput.add(jLabel86);
        jLabel86.setBounds(44, 370, 20, 23);

        jLabel87.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel87.setText("7.");
        jLabel87.setName("jLabel87"); // NOI18N
        FormInput.add(jLabel87);
        jLabel87.setBounds(440, 220, 20, 23);

        jLabel88.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel88.setText("ODHIV");
        jLabel88.setName("jLabel88"); // NOI18N
        FormInput.add(jLabel88);
        jLabel88.setBounds(456, 220, 240, 23);

        Odhiv.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Odhiv.setName("Odhiv"); // NOI18N
        Odhiv.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                OdhivKeyPressed(evt);
            }
        });
        FormInput.add(Odhiv);
        Odhiv.setBounds(709, 220, 80, 23);

        RiwayatDM.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        RiwayatDM.setName("RiwayatDM"); // NOI18N
        RiwayatDM.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RiwayatDMKeyPressed(evt);
            }
        });
        FormInput.add(RiwayatDM);
        RiwayatDM.setBounds(320, 370, 80, 23);

        jSeparator2.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator2.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator2.setName("jSeparator2"); // NOI18N
        FormInput.add(jSeparator2);
        jSeparator2.setBounds(0, 150, 807, 1);

        jLabel100.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel100.setText("I. PEMERIKSAAN FISIK (KLASIFIKASI WHO ASIA PASIFIK, 2020)");
        jLabel100.setName("jLabel100"); // NOI18N
        FormInput.add(jLabel100);
        jLabel100.setBounds(10, 70, 490, 23);

        jLabel17.setText("IMT(BB/TB²) :");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(310, 90, 80, 23);

        IMT.setEditable(false);
        IMT.setFocusTraversalPolicyProvider(true);
        IMT.setName("IMT"); // NOI18N
        FormInput.add(IMT);
        IMT.setBounds(394, 90, 50, 23);

        KlasifikasiIMT.setEditable(false);
        KlasifikasiIMT.setFocusTraversalPolicyProvider(true);
        KlasifikasiIMT.setName("KlasifikasiIMT"); // NOI18N
        FormInput.add(KlasifikasiIMT);
        KlasifikasiIMT.setBounds(574, 90, 215, 23);

        jLabel20.setText("Klasifikasi IMT :");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(470, 90, 100, 23);

        LP.setFocusTraversalPolicyProvider(true);
        LP.setName("LP"); // NOI18N
        LP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LPKeyPressed(evt);
            }
        });
        FormInput.add(LP);
        LP.setBounds(68, 120, 50, 23);

        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel26.setText("Cm");
        jLabel26.setName("jLabel26"); // NOI18N
        FormInput.add(jLabel26);
        jLabel26.setBounds(120, 120, 25, 23);

        jSeparator3.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator3.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator3.setName("jSeparator3"); // NOI18N
        FormInput.add(jSeparator3);
        jSeparator3.setBounds(0, 400, 807, 1);

        jLabel101.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel101.setText("VI. HASIL SKRINING GEJALA TBC DAN PENYAKIT PERNAFASAN LAINNYA");
        jLabel101.setName("jLabel101"); // NOI18N
        FormInput.add(jLabel101);
        jLabel101.setBounds(10, 560, 380, 23);

        KesimpulanHasilSkrining.setFocusTraversalPolicyProvider(true);
        KesimpulanHasilSkrining.setName("KesimpulanHasilSkrining"); // NOI18N
        FormInput.add(KesimpulanHasilSkrining);
        KesimpulanHasilSkrining.setBounds(180, 580, 220, 23);

        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel22.setText("Kesimpulan Hasil Skrining");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(44, 580, 150, 23);

        jLabel93.setText("Keterangan :");
        jLabel93.setName("jLabel93"); // NOI18N
        FormInput.add(jLabel93);
        jLabel93.setBounds(415, 580, 70, 23);

        KetHasilSkrining.setFocusTraversalPolicyProvider(true);
        KetHasilSkrining.setName("KetHasilSkrining"); // NOI18N
        KetHasilSkrining.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetHasilSkriningKeyPressed(evt);
            }
        });
        FormInput.add(KetHasilSkrining);
        KetHasilSkrining.setBounds(489, 580, 300, 23);

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel23.setText("LP");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(44, 120, 30, 23);

        jLabel5.setText(":");
        jLabel5.setName("jLabel5"); // NOI18N
        FormInput.add(jLabel5);
        jLabel5.setBounds(0, 90, 64, 23);

        jLabel9.setText(":");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(0, 120, 64, 23);

        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel24.setText("TB");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(180, 90, 50, 23);

        jLabel94.setText(":");
        jLabel94.setName("jLabel94"); // NOI18N
        FormInput.add(jLabel94);
        jLabel94.setBounds(180, 120, 186, 23);

        jLabel27.setText(":");
        jLabel27.setName("jLabel27"); // NOI18N
        FormInput.add(jLabel27);
        jLabel27.setBounds(-2, 580, 178, 23);

        jLabel102.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel102.setText("III. FAKTOR RESIKO");
        jLabel102.setName("jLabel102"); // NOI18N
        FormInput.add(jLabel102);
        jLabel102.setBounds(10, 200, 200, 23);

        jLabel90.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel90.setText("Riwayat Kontak TBC");
        jLabel90.setName("jLabel90"); // NOI18N
        FormInput.add(jLabel90);
        jLabel90.setBounds(44, 170, 120, 23);

        RiwayatKontakTBC.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        RiwayatKontakTBC.setName("RiwayatKontakTBC"); // NOI18N
        RiwayatKontakTBC.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                RiwayatKontakTBCItemStateChanged(evt);
            }
        });
        RiwayatKontakTBC.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RiwayatKontakTBCKeyPressed(evt);
            }
        });
        FormInput.add(RiwayatKontakTBC);
        RiwayatKontakTBC.setBounds(153, 170, 80, 23);

        jLabel95.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel95.setText("Jika Pertanyaan 1 Ya, Kapan ?");
        jLabel95.setName("jLabel95"); // NOI18N
        FormInput.add(jLabel95);
        jLabel95.setBounds(60, 250, 180, 23);

        JenisKontakTBC.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "TBC Paru Bakteriologis", "TBC Paru Klinis", "TBC Paru Ekstraparu" }));
        JenisKontakTBC.setName("JenisKontakTBC"); // NOI18N
        JenisKontakTBC.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JenisKontakTBCKeyPressed(evt);
            }
        });
        FormInput.add(JenisKontakTBC);
        JenisKontakTBC.setBounds(424, 170, 160, 23);

        jSeparator4.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator4.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator4.setName("jSeparator4"); // NOI18N
        FormInput.add(jSeparator4);
        jSeparator4.setBounds(0, 200, 807, 1);

        BB.setFocusTraversalPolicyProvider(true);
        BB.setName("BB"); // NOI18N
        BB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BBKeyPressed(evt);
            }
        });
        FormInput.add(BB);
        BB.setBounds(68, 90, 50, 23);

        jLabel96.setText("Jenis Kontak TBC :");
        jLabel96.setName("jLabel96"); // NOI18N
        FormInput.add(jLabel96);
        jLabel96.setBounds(290, 170, 130, 23);

        jLabel97.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel97.setText("8.");
        jLabel97.setName("jLabel97"); // NOI18N
        FormInput.add(jLabel97);
        jLabel97.setBounds(440, 250, 20, 23);

        jLabel98.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel98.setText("Lansia > 65 Tahun");
        jLabel98.setName("jLabel98"); // NOI18N
        FormInput.add(jLabel98);
        jLabel98.setBounds(456, 250, 240, 23);

        Lansia.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Lansia.setName("Lansia"); // NOI18N
        Lansia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LansiaKeyPressed(evt);
            }
        });
        FormInput.add(Lansia);
        Lansia.setBounds(709, 250, 80, 23);

        jLabel103.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel103.setText("10.");
        jLabel103.setName("jLabel103"); // NOI18N
        FormInput.add(jLabel103);
        jLabel103.setBounds(440, 310, 20, 23);

        jLabel104.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel104.setText("Warga Binaan Permasyarakatan (WBP) ?");
        jLabel104.setName("jLabel104"); // NOI18N
        FormInput.add(jLabel104);
        jLabel104.setBounds(456, 310, 240, 23);

        WargaBinaan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        WargaBinaan.setName("WargaBinaan"); // NOI18N
        WargaBinaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                WargaBinaanKeyPressed(evt);
            }
        });
        FormInput.add(WargaBinaan);
        WargaBinaan.setBounds(709, 310, 80, 23);

        jLabel105.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel105.setText("11.");
        jLabel105.setName("jLabel105"); // NOI18N
        FormInput.add(jLabel105);
        jLabel105.setBounds(440, 340, 20, 23);

        jLabel106.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel106.setText("Tinggal diwilayah padat kumuh miskin ?");
        jLabel106.setName("jLabel106"); // NOI18N
        FormInput.add(jLabel106);
        jLabel106.setBounds(456, 340, 240, 23);

        Tingaldiwilayahkumuh.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Tingaldiwilayahkumuh.setName("Tingaldiwilayahkumuh"); // NOI18N
        Tingaldiwilayahkumuh.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TingaldiwilayahkumuhKeyPressed(evt);
            }
        });
        FormInput.add(Tingaldiwilayahkumuh);
        Tingaldiwilayahkumuh.setBounds(709, 340, 80, 23);

        jLabel107.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel107.setText("9.");
        jLabel107.setName("jLabel107"); // NOI18N
        FormInput.add(jLabel107);
        jLabel107.setBounds(440, 280, 20, 23);

        jLabel108.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel108.setText("Ibu Hamil ?");
        jLabel108.setName("jLabel108"); // NOI18N
        FormInput.add(jLabel108);
        jLabel108.setBounds(456, 280, 240, 23);

        Ibuhamil.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Ibuhamil.setName("Ibuhamil"); // NOI18N
        Ibuhamil.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                IbuhamilKeyPressed(evt);
            }
        });
        FormInput.add(Ibuhamil);
        Ibuhamil.setBounds(709, 280, 80, 23);

        AbnormalitasTBC.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormalitas TBC", "Abnormalitas Bukan TBC" }));
        AbnormalitasTBC.setName("AbnormalitasTBC"); // NOI18N
        AbnormalitasTBC.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AbnormalitasTBCKeyPressed(evt);
            }
        });
        FormInput.add(AbnormalitasTBC);
        AbnormalitasTBC.setBounds(142, 420, 190, 23);

        jLabel109.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel109.setText("Abnormalitas TBC");
        jLabel109.setName("jLabel109"); // NOI18N
        FormInput.add(jLabel109);
        jLabel109.setBounds(44, 420, 130, 23);

        jSeparator5.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator5.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator5.setName("jSeparator5"); // NOI18N
        FormInput.add(jSeparator5);
        jSeparator5.setBounds(0, 450, 807, 1);

        jLabel110.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel110.setText("IV. ABNOMARLITAS TBC");
        jLabel110.setName("jLabel110"); // NOI18N
        FormInput.add(jLabel110);
        jLabel110.setBounds(10, 400, 200, 23);

        jLabel111.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel111.setText("1.");
        jLabel111.setName("jLabel111"); // NOI18N
        FormInput.add(jLabel111);
        jLabel111.setBounds(44, 470, 30, 23);

        jLabel112.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel112.setText("Batuk ( Semua bentuk batuk tanpa melihat durasi )");
        jLabel112.setName("jLabel112"); // NOI18N
        FormInput.add(jLabel112);
        jLabel112.setBounds(60, 470, 260, 23);

        Batuk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Batuk.setName("Batuk"); // NOI18N
        Batuk.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                BatukItemStateChanged(evt);
            }
        });
        Batuk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BatukKeyPressed(evt);
            }
        });
        FormInput.add(Batuk);
        Batuk.setBounds(388, 470, 80, 23);

        jLabel113.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel113.setText("2.");
        jLabel113.setName("jLabel113"); // NOI18N
        FormInput.add(jLabel113);
        jLabel113.setBounds(44, 500, 30, 23);

        jLabel114.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel114.setText("BB turun tanpa penyebab jelas / BB tidak naik / Nafsu makan turun");
        jLabel114.setName("jLabel114"); // NOI18N
        FormInput.add(jLabel114);
        jLabel114.setBounds(60, 500, 340, 23);

        BBTurun.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        BBTurun.setName("BBTurun"); // NOI18N
        BBTurun.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                BBTurunItemStateChanged(evt);
            }
        });
        BBTurun.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BBTurunKeyPressed(evt);
            }
        });
        FormInput.add(BBTurun);
        BBTurun.setBounds(388, 500, 80, 23);

        jLabel115.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel115.setText("3.");
        jLabel115.setName("jLabel115"); // NOI18N
        FormInput.add(jLabel115);
        jLabel115.setBounds(481, 470, 20, 23);

        jLabel116.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel116.setText("Demam yang tidak diketahui penyebabnya");
        jLabel116.setName("jLabel116"); // NOI18N
        FormInput.add(jLabel116);
        jLabel116.setBounds(497, 470, 210, 23);

        Demam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Demam.setName("Demam"); // NOI18N
        Demam.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                DemamItemStateChanged(evt);
            }
        });
        Demam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DemamKeyPressed(evt);
            }
        });
        FormInput.add(Demam);
        Demam.setBounds(709, 470, 80, 23);

        jLabel117.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel117.setText("4.");
        jLabel117.setName("jLabel117"); // NOI18N
        FormInput.add(jLabel117);
        jLabel117.setBounds(481, 500, 20, 23);

        jLabel118.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel118.setText("Berkeringat malam hari tanpa kegiatan");
        jLabel118.setName("jLabel118"); // NOI18N
        FormInput.add(jLabel118);
        jLabel118.setBounds(497, 500, 210, 23);

        BerkeringatMalam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        BerkeringatMalam.setName("BerkeringatMalam"); // NOI18N
        BerkeringatMalam.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                BerkeringatMalamItemStateChanged(evt);
            }
        });
        BerkeringatMalam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BerkeringatMalamKeyPressed(evt);
            }
        });
        FormInput.add(BerkeringatMalam);
        BerkeringatMalam.setBounds(709, 500, 80, 23);

        jSeparator6.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator6.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator6.setName("jSeparator6"); // NOI18N
        FormInput.add(jSeparator6);
        jSeparator6.setBounds(0, 560, 807, 1);

        jLabel119.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel119.setText("Gejala Penyakit Pernafasan Lainnya, Sebutkan ( Seperti pilek/flu, sakit tengorokan dll )");
        jLabel119.setName("jLabel119"); // NOI18N
        FormInput.add(jLabel119);
        jLabel119.setBounds(44, 530, 430, 23);

        KetGejalaPenyakit.setFocusTraversalPolicyProvider(true);
        KetGejalaPenyakit.setName("KetGejalaPenyakit"); // NOI18N
        KetGejalaPenyakit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetGejalaPenyakitKeyPressed(evt);
            }
        });
        FormInput.add(KetGejalaPenyakit);
        KetGejalaPenyakit.setBounds(476, 530, 313, 23);

        jLabel120.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel120.setText("V. GEJALA TBC");
        jLabel120.setName("jLabel120"); // NOI18N
        FormInput.add(jLabel120);
        jLabel120.setBounds(10, 450, 200, 23);

        KetPernahTerdiagnosa.setFocusTraversalPolicyProvider(true);
        KetPernahTerdiagnosa.setName("KetPernahTerdiagnosa"); // NOI18N
        KetPernahTerdiagnosa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetPernahTerdiagnosaKeyPressed(evt);
            }
        });
        FormInput.add(KetPernahTerdiagnosa);
        KetPernahTerdiagnosa.setBounds(210, 250, 190, 23);

        jLabel8.setText("Tgl.Lahir :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(625, 10, 60, 23);

        TglLahir.setHighlighter(null);
        TglLahir.setName("TglLahir"); // NOI18N
        FormInput.add(TglLahir);
        TglLahir.setBounds(689, 10, 100, 23);

        jLabel10.setText(":");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(0, 170, 149, 23);

        jLabel89.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel89.setText("3.");
        jLabel89.setName("jLabel89"); // NOI18N
        FormInput.add(jLabel89);
        jLabel89.setBounds(44, 280, 20, 23);

        jLabel11.setText(":");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(0, 420, 138, 23);

        jLabel14.setText(":");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(0, 530, 472, 23);

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
        }else if(BB.getText().trim().equals("")){
            Valid.textKosong(BB,"Berat Badan");
        }else if(TB.getText().trim().equals("")){
            Valid.textKosong(TB,"Tinggi Badan");
        }else if(IMT.getText().trim().equals("")){
            Valid.textKosong(IMT,"IMT");
        }else if(KlasifikasiIMT.getText().trim().equals("")){
            Valid.textKosong(IMT,"Klasifikasi IMT");
        }else if(LP.getText().trim().equals("")){
            Valid.textKosong(LP,"Lingkar Pinggang");
        }else if(RisikoLP.getText().trim().equals("")){
            Valid.textKosong(LP,"Gejala Penyakit Lain");
        }else if(KesimpulanHasilSkrining.getText().trim().equals("")){
            Valid.textKosong(LP,"Kesimpulan Skrining");
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
            Valid.pindah(evt,KetHasilSkrining,BtnBatal);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        emptTeks();
        ChkInput.setSelected(true);
        isForm(); 
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
        }else if(BB.getText().trim().equals("")){
            Valid.textKosong(BB,"Berat Badan");
        }else if(TB.getText().trim().equals("")){
            Valid.textKosong(TB,"Tinggi Badan");
        }else if(IMT.getText().trim().equals("")){
            Valid.textKosong(IMT,"IMT");
        }else if(KlasifikasiIMT.getText().trim().equals("")){
            Valid.textKosong(IMT,"Klasifikasi IMT");
        }else if(LP.getText().trim().equals("")){
            Valid.textKosong(LP,"Lingkar Pinggang");
        }else if(RisikoLP.getText().trim().equals("")){
            Valid.textKosong(LP,"Gejala Penyakit Lain");
        }else if(KesimpulanHasilSkrining.getText().trim().equals("")){
            Valid.textKosong(LP,"Kesimpulan Skrining");
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
                htmlContent = new StringBuilder();
                htmlContent.append(                             
                    "<tr class='isi'>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Rawat</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.RM</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Pasien</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tgl.Lahir</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>J.K.</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kode Petugas</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Petugas</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tanggal</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>BB(Kg)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>TB(Cm)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>IMT</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kasifikasi IMT</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>LP(Cm)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Risiko L.P.</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Riwayat Kontak TBC</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Jenis Kontak TBC</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Pernah Terdiagnosa TBC</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Jika Ya Kapan ?</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Pernah Berobat TBC</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Malnutrisi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Merokok</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Riwayat DM</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>ODHIV</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Lansia > 65 Tahun</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Ibu Hamil</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>WBP (Warga Binaan Permasyarakatan )</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tinggal di wilayah padat kumuh miskin</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Abnormalitas TBC</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Gejala Batuk</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Gejala BB Turun</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Gejala Demam</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Gejala Berkeringat Malam Hari</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Gejala Penyakit Lain</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kesimpulan Skrining</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan</b></td>"+
                    "</tr>"
                );
                for (i = 0; i < tabMode.getRowCount(); i++) {
                    htmlContent.append(
                        "<tr class='isi'>"+
                           "<td valign='top'>"+tbObat.getValueAt(i,0).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,1).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,2).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,3).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,4).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,5).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,6).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,7).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,8).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,9).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,10).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,11).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,12).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,13).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,14).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,15).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,16).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,17).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,18).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,19).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,20).toString()+"</td>"+ 
                            "<td valign='top'>"+tbObat.getValueAt(i,21).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,22).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,23).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,24).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,25).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,26).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,27).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,28).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,29).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,30).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,31).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,32).toString()+"</td>"+ 
                            "<td valign='top'>"+tbObat.getValueAt(i,33).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,34).toString()+"</td>"+
                        "</tr>");
                }
                LoadHTML.setText(
                    "<html>"+
                      "<table width='2100px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
                       htmlContent.toString()+
                      "</table>"+
                    "</html>"
                );

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

                File f = new File("DataSkriningTBC.html");            
                BufferedWriter bw = new BufferedWriter(new FileWriter(f));            
                bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                            "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                            "<table width='2100px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                "<tr class='isi2'>"+
                                    "<td valign='top' align='center'>"+
                                        "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                        akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                        akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                        "<font size='2' face='Tahoma'>DATA SEKRINING TBC<br><br></font>"+        
                                    "</td>"+
                               "</tr>"+
                            "</table>")
                );
                bw.close();                         
                Desktop.getDesktop().browse(f.toURI());

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

    private void TNoRMKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRMKeyPressed
        // Valid.pindah(evt, TNm, BtnSimpan);
}//GEN-LAST:event_TNoRMKeyPressed

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
        Valid.pindah(evt,Detik,BB);
    }//GEN-LAST:event_btnPetugasKeyPressed

    private void MnSkriningTBCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnSkriningTBCActionPerformed
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
            Valid.MyReportqry("rptFormulirSkriningTBC.jasper","report","::[ Formulir Skrining TBC ]::",
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,pasien.jk,skrining_tbc.nip,petugas.nama,skrining_tbc.tanggal,"+
                    "skrining_tbc.berat_badan,skrining_tbc.tinggi_badan,skrining_tbc.imt,skrining_tbc.kasifikasi_imt,skrining_tbc.lingkar_pinggang,skrining_tbc.risiko_lingkar_pinggang,"+
                    "skrining_tbc.riwayat_kontak_tbc,skrining_tbc.jenis_kontak_tbc,"+
                    "skrining_tbc.faktor_resiko_pernah_terdiagnosa_tbc,skrining_tbc.keterangan_pernah_terdiagnosa,"+
                    "skrining_tbc.faktor_resiko_pernah_berobat_tbc,skrining_tbc.faktor_resiko_malnutrisi,skrining_tbc.faktor_resiko_merokok,skrining_tbc.faktor_resiko_riwayat_dm,"+
                    "skrining_tbc.faktor_resiko_odhiv,skrining_tbc.faktor_resiko_lansia,skrining_tbc.faktor_resiko_ibu_hamil,skrining_tbc.faktor_resiko_wbp,skrining_tbc.faktor_resiko_tinggal_diwilayah_padat_kumuh,"+
                    "skrining_tbc.abnormalitas_tbc,skrining_tbc.gejala_tbc_batuk,skrining_tbc.gejala_tbc_bb_turun,skrining_tbc.gejala_tbc_demam,skrining_tbc.gejala_tbc_berkeringat_malam_hari,skrining_tbc.keterangan_gejala_penyakit_lain,"+
                    "skrining_tbc.kesimpulan_skrining,skrining_tbc.keterangan_hasil_skrining from skrining_tbc inner join reg_periksa on skrining_tbc.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join petugas on skrining_tbc.nip=petugas.nip "+
                    "where reg_periksa.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'",param);
        }
    }//GEN-LAST:event_MnSkriningTBCActionPerformed

    private void TBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TBKeyPressed
        Valid.pindah(evt,BB,LP);
    }//GEN-LAST:event_TBKeyPressed

    private void PernahTerdiagnosaTBCKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PernahTerdiagnosaTBCKeyPressed
        Valid.pindah(evt,JenisKontakTBC,KetPernahTerdiagnosa);
    }//GEN-LAST:event_PernahTerdiagnosaTBCKeyPressed

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void PernahberobatTBCKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PernahberobatTBCKeyPressed
        Valid.pindah(evt,KetPernahTerdiagnosa,Malnutrisi);
    }//GEN-LAST:event_PernahberobatTBCKeyPressed

    private void MalnutrisiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MalnutrisiKeyPressed
        Valid.pindah(evt,PernahberobatTBC,Merokok);
    }//GEN-LAST:event_MalnutrisiKeyPressed

    private void MerokokKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MerokokKeyPressed
        Valid.pindah(evt,Malnutrisi,RiwayatDM);
    }//GEN-LAST:event_MerokokKeyPressed

    private void OdhivKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_OdhivKeyPressed
        Valid.pindah(evt,RiwayatDM,Lansia);
    }//GEN-LAST:event_OdhivKeyPressed

    private void RiwayatDMKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RiwayatDMKeyPressed
        Valid.pindah(evt,Merokok,Odhiv);
    }//GEN-LAST:event_RiwayatDMKeyPressed

    private void LPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LPKeyPressed
        Valid.pindah(evt,TB,RiwayatKontakTBC);
    }//GEN-LAST:event_LPKeyPressed

    private void KetHasilSkriningKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetHasilSkriningKeyPressed
        Valid.pindah(evt,KetGejalaPenyakit,BtnSimpan);
    }//GEN-LAST:event_KetHasilSkriningKeyPressed

    private void RiwayatKontakTBCKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RiwayatKontakTBCKeyPressed
        Valid.pindah(evt,LP,JenisKontakTBC);
    }//GEN-LAST:event_RiwayatKontakTBCKeyPressed

    private void JenisKontakTBCKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JenisKontakTBCKeyPressed
        Valid.pindah(evt,RiwayatKontakTBC,PernahTerdiagnosaTBC);
    }//GEN-LAST:event_JenisKontakTBCKeyPressed

    private void BBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BBKeyPressed
        Valid.pindah(evt,NmPetugas,TB);
    }//GEN-LAST:event_BBKeyPressed

    private void LansiaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LansiaKeyPressed
        Valid.pindah(evt,Odhiv,Ibuhamil);
    }//GEN-LAST:event_LansiaKeyPressed

    private void WargaBinaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_WargaBinaanKeyPressed
        Valid.pindah(evt,Ibuhamil,Tingaldiwilayahkumuh);
    }//GEN-LAST:event_WargaBinaanKeyPressed

    private void TingaldiwilayahkumuhKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TingaldiwilayahkumuhKeyPressed
        Valid.pindah(evt,WargaBinaan,AbnormalitasTBC);
    }//GEN-LAST:event_TingaldiwilayahkumuhKeyPressed

    private void IbuhamilKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_IbuhamilKeyPressed
        Valid.pindah(evt,Lansia,WargaBinaan);
    }//GEN-LAST:event_IbuhamilKeyPressed

    private void AbnormalitasTBCKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AbnormalitasTBCKeyPressed
        Valid.pindah(evt,Tingaldiwilayahkumuh,Batuk);
    }//GEN-LAST:event_AbnormalitasTBCKeyPressed

    private void BatukKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BatukKeyPressed
        Valid.pindah(evt,AbnormalitasTBC,BBTurun);
    }//GEN-LAST:event_BatukKeyPressed

    private void BBTurunKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BBTurunKeyPressed
        Valid.pindah(evt,Batuk,Demam);
    }//GEN-LAST:event_BBTurunKeyPressed

    private void DemamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DemamKeyPressed
        Valid.pindah(evt,BBTurun,BerkeringatMalam);
    }//GEN-LAST:event_DemamKeyPressed

    private void BerkeringatMalamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BerkeringatMalamKeyPressed
        Valid.pindah(evt,Demam,KetGejalaPenyakit);
    }//GEN-LAST:event_BerkeringatMalamKeyPressed

    private void KetGejalaPenyakitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetGejalaPenyakitKeyPressed
        Valid.pindah(evt,BerkeringatMalam,KetHasilSkrining);
    }//GEN-LAST:event_KetGejalaPenyakitKeyPressed

    private void RiwayatKontakTBCItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_RiwayatKontakTBCItemStateChanged
        isTbc();
    }//GEN-LAST:event_RiwayatKontakTBCItemStateChanged

    private void BatukItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_BatukItemStateChanged
        isTbc();
    }//GEN-LAST:event_BatukItemStateChanged

    private void BerkeringatMalamItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_BerkeringatMalamItemStateChanged
        isTbc();
    }//GEN-LAST:event_BerkeringatMalamItemStateChanged

    private void BBTurunItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_BBTurunItemStateChanged
        isTbc();
    }//GEN-LAST:event_BBTurunItemStateChanged

    private void DemamItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_DemamItemStateChanged
        isTbc();
    }//GEN-LAST:event_DemamItemStateChanged

    private void KetPernahTerdiagnosaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetPernahTerdiagnosaKeyPressed
        Valid.pindah(evt,PernahTerdiagnosaTBC,PernahberobatTBC);
    }//GEN-LAST:event_KetPernahTerdiagnosaKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMSkriningTBC dialog = new RMSkriningTBC(new javax.swing.JFrame(), true);
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
    private widget.ComboBox AbnormalitasTBC;
    private widget.TextBox BB;
    private widget.ComboBox BBTurun;
    private widget.ComboBox Batuk;
    private widget.ComboBox BerkeringatMalam;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkInput;
    private widget.CekBox ChkKejadian;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.ComboBox Demam;
    private widget.ComboBox Detik;
    private widget.PanelBiasa FormInput;
    private widget.TextBox IMT;
    private widget.ComboBox Ibuhamil;
    private widget.ComboBox Jam;
    private widget.ComboBox JenisKontakTBC;
    private widget.TextBox Jk;
    private widget.TextBox KdPetugas;
    private widget.TextBox KesimpulanHasilSkrining;
    private widget.TextBox KetGejalaPenyakit;
    private widget.TextBox KetHasilSkrining;
    private widget.TextBox KetPernahTerdiagnosa;
    private widget.TextBox KlasifikasiIMT;
    private widget.Label LCount;
    private widget.TextBox LP;
    private widget.ComboBox Lansia;
    private widget.editorpane LoadHTML;
    private widget.ComboBox Malnutrisi;
    private widget.ComboBox Menit;
    private widget.ComboBox Merokok;
    private javax.swing.JMenuItem MnSkriningTBC;
    private widget.TextBox NmPetugas;
    private widget.ComboBox Odhiv;
    private javax.swing.JPanel PanelInput;
    private widget.ComboBox PernahTerdiagnosaTBC;
    private widget.ComboBox PernahberobatTBC;
    private widget.TextBox RisikoLP;
    private widget.ComboBox RiwayatDM;
    private widget.ComboBox RiwayatKontakTBC;
    private widget.ScrollPane Scroll;
    private widget.TextBox TB;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.Tanggal Tanggal;
    private widget.TextBox TanggalRegistrasi;
    private widget.TextBox TglLahir;
    private widget.ComboBox Tingaldiwilayahkumuh;
    private widget.ComboBox WargaBinaan;
    private widget.Button btnPetugas;
    private javax.swing.ButtonGroup buttonGroup1;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel10;
    private widget.Label jLabel100;
    private widget.Label jLabel101;
    private widget.Label jLabel102;
    private widget.Label jLabel103;
    private widget.Label jLabel104;
    private widget.Label jLabel105;
    private widget.Label jLabel106;
    private widget.Label jLabel107;
    private widget.Label jLabel108;
    private widget.Label jLabel109;
    private widget.Label jLabel11;
    private widget.Label jLabel110;
    private widget.Label jLabel111;
    private widget.Label jLabel112;
    private widget.Label jLabel113;
    private widget.Label jLabel114;
    private widget.Label jLabel115;
    private widget.Label jLabel116;
    private widget.Label jLabel117;
    private widget.Label jLabel118;
    private widget.Label jLabel119;
    private widget.Label jLabel12;
    private widget.Label jLabel120;
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
    private widget.Label jLabel27;
    private widget.Label jLabel4;
    private widget.Label jLabel5;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
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
    private widget.Label jLabel9;
    private widget.Label jLabel90;
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
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
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
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,pasien.jk,skrining_tbc.nip,petugas.nama,skrining_tbc.tanggal,"+
                    "skrining_tbc.berat_badan,skrining_tbc.tinggi_badan,skrining_tbc.imt,skrining_tbc.kasifikasi_imt,skrining_tbc.lingkar_pinggang,skrining_tbc.risiko_lingkar_pinggang,"+
                    "skrining_tbc.riwayat_kontak_tbc,skrining_tbc.jenis_kontak_tbc,"+
                    "skrining_tbc.faktor_resiko_pernah_terdiagnosa_tbc,skrining_tbc.keterangan_pernah_terdiagnosa,"+
                    "skrining_tbc.faktor_resiko_pernah_berobat_tbc,skrining_tbc.faktor_resiko_malnutrisi,skrining_tbc.faktor_resiko_merokok,skrining_tbc.faktor_resiko_riwayat_dm,"+
                    "skrining_tbc.faktor_resiko_odhiv,skrining_tbc.faktor_resiko_lansia,skrining_tbc.faktor_resiko_ibu_hamil,skrining_tbc.faktor_resiko_wbp,skrining_tbc.faktor_resiko_tinggal_diwilayah_padat_kumuh,"+
                    "skrining_tbc.abnormalitas_tbc,skrining_tbc.gejala_tbc_batuk,skrining_tbc.gejala_tbc_bb_turun,skrining_tbc.gejala_tbc_demam,skrining_tbc.gejala_tbc_berkeringat_malam_hari,skrining_tbc.keterangan_gejala_penyakit_lain,"+
                    "skrining_tbc.kesimpulan_skrining,skrining_tbc.keterangan_hasil_skrining from skrining_tbc inner join reg_periksa on skrining_tbc.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join petugas on skrining_tbc.nip=petugas.nip "+
                    "where skrining_tbc.tanggal between ? and ? order by skrining_tbc.tanggal ");
            }else{
                ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,pasien.jk,skrining_tbc.nip,petugas.nama,skrining_tbc.tanggal,"+
                    "skrining_tbc.berat_badan,skrining_tbc.tinggi_badan,skrining_tbc.imt,skrining_tbc.kasifikasi_imt,skrining_tbc.lingkar_pinggang,skrining_tbc.risiko_lingkar_pinggang,"+
                    "skrining_tbc.riwayat_kontak_tbc,skrining_tbc.jenis_kontak_tbc,"+
                    "skrining_tbc.faktor_resiko_pernah_terdiagnosa_tbc,skrining_tbc.keterangan_pernah_terdiagnosa,"+
                    "skrining_tbc.faktor_resiko_pernah_berobat_tbc,skrining_tbc.faktor_resiko_malnutrisi,skrining_tbc.faktor_resiko_merokok,skrining_tbc.faktor_resiko_riwayat_dm,"+
                    "skrining_tbc.faktor_resiko_odhiv,skrining_tbc.faktor_resiko_lansia,skrining_tbc.faktor_resiko_ibu_hamil,skrining_tbc.faktor_resiko_wbp,skrining_tbc.faktor_resiko_tinggal_diwilayah_padat_kumuh,"+
                    "skrining_tbc.abnormalitas_tbc,skrining_tbc.gejala_tbc_batuk,skrining_tbc.gejala_tbc_bb_turun,skrining_tbc.gejala_tbc_demam,skrining_tbc.gejala_tbc_berkeringat_malam_hari,skrining_tbc.keterangan_gejala_penyakit_lain,"+
                    "skrining_tbc.kesimpulan_skrining,skrining_tbc.keterangan_hasil_skrining from skrining_tbc inner join reg_periksa on skrining_tbc.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join petugas on skrining_tbc.nip=petugas.nip "+
                    "where skrining_tbc.tanggal between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or "+
                    "pasien.nm_pasien like ? or skrining_tbc.nip like ? or petugas.nama like ? or skrining_tbc.kasifikasi_imt like ? or skrining_tbc.kesimpulan_skrining like ?) "+
                    "order by skrining_tbc.tanggal ");
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
                    ps.setString(9,"%"+TCari.getText()+"%");

                }
                    
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new String[]{
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("tgl_lahir"),rs.getString("jk"),rs.getString("nip"),rs.getString("nama"),rs.getString("tanggal"),
                        rs.getString("berat_badan"),rs.getString("tinggi_badan"),rs.getString("imt"),rs.getString("kasifikasi_imt"),rs.getString("lingkar_pinggang"),rs.getString("risiko_lingkar_pinggang"),
                        rs.getString("riwayat_kontak_tbc"),rs.getString("jenis_kontak_tbc"),rs.getString("faktor_resiko_pernah_terdiagnosa_tbc"),rs.getString("keterangan_pernah_terdiagnosa"),rs.getString("faktor_resiko_pernah_berobat_tbc"),
                        rs.getString("faktor_resiko_malnutrisi"),rs.getString("faktor_resiko_merokok"),rs.getString("faktor_resiko_riwayat_dm"),rs.getString("faktor_resiko_odhiv"),rs.getString("faktor_resiko_lansia"),
                        rs.getString("faktor_resiko_ibu_hamil"),rs.getString("faktor_resiko_wbp"),rs.getString("faktor_resiko_tinggal_diwilayah_padat_kumuh"),rs.getString("abnormalitas_tbc"),rs.getString("gejala_tbc_batuk"),
                        rs.getString("gejala_tbc_bb_turun"),rs.getString("gejala_tbc_demam"),rs.getString("gejala_tbc_berkeringat_malam_hari"),rs.getString("keterangan_gejala_penyakit_lain"),rs.getString("kesimpulan_skrining"),
                        rs.getString("keterangan_hasil_skrining"),
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
        BB.setText("");
        TB.setText("");
        IMT.setText("");
        KlasifikasiIMT.setText("");
        LP.setText("");
        RisikoLP.setText("");
        RiwayatKontakTBC.setSelectedIndex(0);
        JenisKontakTBC.setSelectedIndex(0);
        PernahTerdiagnosaTBC.setSelectedIndex(0);
        KetPernahTerdiagnosa.setText("");
        PernahberobatTBC.setSelectedIndex(0);
        Malnutrisi.setSelectedIndex(0);
        Merokok.setSelectedIndex(0);
        RiwayatDM.setSelectedIndex(0);
        Odhiv.setSelectedIndex(0);
        Lansia.setSelectedIndex(0);
        Ibuhamil.setSelectedIndex(0);
        WargaBinaan.setSelectedIndex(0);
        Tingaldiwilayahkumuh.setSelectedIndex(0);
        AbnormalitasTBC.setSelectedIndex(0);
        Batuk.setSelectedIndex(0);
        BBTurun.setSelectedIndex(0);
        Demam.setSelectedIndex(0);
        BerkeringatMalam.setSelectedIndex(0);
        KetGejalaPenyakit.setText("");
        KesimpulanHasilSkrining.setText("Bukan Terduga TBC");
        KetHasilSkrining.setText("");
        Tanggal.setDate(new Date());
        BB.requestFocus();
    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            Jk.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString());
            Jam.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString().substring(11,13));
            Menit.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString().substring(14,15));
            Detik.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString().substring(17,19));
            BB.setText(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            TB.setText(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            IMT.setText(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            KlasifikasiIMT.setText(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            LP.setText(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            RisikoLP.setText(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            RiwayatKontakTBC.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            JenisKontakTBC.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
            PernahTerdiagnosaTBC.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            KetPernahTerdiagnosa.setText(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());
            PernahberobatTBC.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
            Malnutrisi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
            Merokok.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString());
            RiwayatDM.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString());
            Odhiv.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString());
            Lansia.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString());
            Ibuhamil.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),24).toString());
            WargaBinaan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),25).toString());
            Tingaldiwilayahkumuh.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),26).toString());
            AbnormalitasTBC.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),27).toString());
            Batuk.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),28).toString());
            BBTurun.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),29).toString());
            Demam.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),30).toString());
            BerkeringatMalam.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),31).toString());
            KetGejalaPenyakit.setText(tbObat.getValueAt(tbObat.getSelectedRow(),32).toString());
            KesimpulanHasilSkrining.setText(tbObat.getValueAt(tbObat.getSelectedRow(),33).toString());
            KetHasilSkrining.setText(tbObat.getValueAt(tbObat.getSelectedRow(),34).toString());
            Valid.SetTgl(Tanggal,tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());  
        }
    }
    
    private void isRawat() {
        try {
            ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,"+
                    "reg_periksa.tgl_registrasi,reg_periksa.jam_reg "+
                    "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
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
                    TanggalRegistrasi.setText(rs.getString("tgl_registrasi")+" "+rs.getString("jam_reg"));
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
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,internalFrame1.getHeight()-172));
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
        //BtnSimpan.setEnabled(akses.getskrining_tbc());
        //BtnHapus.setEnabled(akses.getskrining_tbc());
        //BtnEdit.setEnabled(akses.getskrining_tbc());
        //BtnPrint.setEnabled(akses.getskrining_tbc()); 
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
        if(Sequel.mengedittf("skrining_tbc","no_rawat=?","no_rawat=?,tanggal=?,berat_badan=?,tinggi_badan=?,imt=?,kasifikasi_imt=?,lingkar_pinggang=?,risiko_lingkar_pinggang=?,"+
                "riwayat_kontak_tbc=?,jenis_kontak_tbc=?,faktor_resiko_pernah_terdiagnosa_tbc=?,keterangan_pernah_terdiagnosa=?,faktor_resiko_pernah_berobat_tbc=?,"+
                "faktor_resiko_malnutrisi=?,faktor_resiko_merokok=?,faktor_resiko_riwayat_dm=?,faktor_resiko_odhiv=?,faktor_resiko_lansia=?,faktor_resiko_ibu_hamil=?,"+
                "faktor_resiko_wbp=?,faktor_resiko_tinggal_diwilayah_padat_kumuh=?,abnormalitas_tbc=?,gejala_tbc_batuk=?,gejala_tbc_bb_turun=?,gejala_tbc_demam=?,"+
                "gejala_tbc_berkeringat_malam_hari=?,keterangan_gejala_penyakit_lain=?,kesimpulan_skrining=?,keterangan_hasil_skrining=?,nip=?",31,new String[]{
                TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),
                BB.getText(),TB.getText(),IMT.getText(),KlasifikasiIMT.getText(),LP.getText(),RisikoLP.getText(),RiwayatKontakTBC.getSelectedItem().toString(),
                JenisKontakTBC.getSelectedItem().toString(),PernahTerdiagnosaTBC.getSelectedItem().toString(),KetPernahTerdiagnosa.getText(),PernahberobatTBC.getSelectedItem().toString(),
                Malnutrisi.getSelectedItem().toString(),Merokok.getSelectedItem().toString(),RiwayatDM.getSelectedItem().toString(),Odhiv.getSelectedItem().toString(),
                Lansia.getSelectedItem().toString(),Ibuhamil.getSelectedItem().toString(),WargaBinaan.getSelectedItem().toString(),Tingaldiwilayahkumuh.getSelectedItem().toString(),
                AbnormalitasTBC.getSelectedItem().toString(),Batuk.getSelectedItem().toString(),BBTurun.getSelectedItem().toString(),Demam.getSelectedItem().toString(),
                BerkeringatMalam.getSelectedItem().toString(),KetGejalaPenyakit.getText(),KesimpulanHasilSkrining.getText(),KetHasilSkrining.getText(),KdPetugas.getText(),
                tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
            })==true){
               tbObat.setValueAt(TNoRw.getText(),tbObat.getSelectedRow(),0);
               tbObat.setValueAt(TNoRM.getText(),tbObat.getSelectedRow(),1);
               tbObat.setValueAt(TPasien.getText(),tbObat.getSelectedRow(),2);
               tbObat.setValueAt(TglLahir.getText(),tbObat.getSelectedRow(),3);
               tbObat.setValueAt(Jk.getText(),tbObat.getSelectedRow(),4);
               tbObat.setValueAt(KdPetugas.getText(),tbObat.getSelectedRow(),5);
               tbObat.setValueAt(NmPetugas.getText(),tbObat.getSelectedRow(),6);
               tbObat.setValueAt(Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),tbObat.getSelectedRow(),7);
               tbObat.setValueAt(BB.getText(),tbObat.getSelectedRow(),8);
               tbObat.setValueAt(TB.getText(),tbObat.getSelectedRow(),9);
               tbObat.setValueAt(IMT.getText(),tbObat.getSelectedRow(),10);
               tbObat.setValueAt(KlasifikasiIMT.getText(),tbObat.getSelectedRow(),11);
               tbObat.setValueAt(LP.getText(),tbObat.getSelectedRow(),12);
               tbObat.setValueAt(RisikoLP.getText(),tbObat.getSelectedRow(),13);
               tbObat.setValueAt(RiwayatKontakTBC.getSelectedItem().toString(),tbObat.getSelectedRow(),14);
               tbObat.setValueAt(JenisKontakTBC.getSelectedItem().toString(),tbObat.getSelectedRow(),15);
               tbObat.setValueAt(PernahTerdiagnosaTBC.getSelectedItem().toString(),tbObat.getSelectedRow(),16);
               tbObat.setValueAt(KetPernahTerdiagnosa.getText(),tbObat.getSelectedRow(),17);
               tbObat.setValueAt(PernahberobatTBC.getSelectedItem().toString(),tbObat.getSelectedRow(),18);
               tbObat.setValueAt(Malnutrisi.getSelectedItem().toString(),tbObat.getSelectedRow(),19);
               tbObat.setValueAt(Merokok.getSelectedItem().toString(),tbObat.getSelectedRow(),20);
               tbObat.setValueAt(RiwayatDM.getSelectedItem().toString(),tbObat.getSelectedRow(),21);
               tbObat.setValueAt(Odhiv.getSelectedItem().toString(),tbObat.getSelectedRow(),22);
               tbObat.setValueAt(Lansia.getSelectedItem().toString(),tbObat.getSelectedRow(),23);
               tbObat.setValueAt(Ibuhamil.getSelectedItem().toString(),tbObat.getSelectedRow(),24);
               tbObat.setValueAt(WargaBinaan.getSelectedItem().toString(),tbObat.getSelectedRow(),25);
               tbObat.setValueAt(Tingaldiwilayahkumuh.getSelectedItem().toString(),tbObat.getSelectedRow(),26);
               tbObat.setValueAt(AbnormalitasTBC.getSelectedItem().toString(),tbObat.getSelectedRow(),27);
               tbObat.setValueAt(Batuk.getSelectedItem().toString(),tbObat.getSelectedRow(),28);
               tbObat.setValueAt(BBTurun.getSelectedItem().toString(),tbObat.getSelectedRow(),29);
               tbObat.setValueAt(Demam.getSelectedItem().toString(),tbObat.getSelectedRow(),30);
               tbObat.setValueAt(BerkeringatMalam.getSelectedItem().toString(),tbObat.getSelectedRow(),31);
               tbObat.setValueAt(KetGejalaPenyakit.getText(),tbObat.getSelectedRow(),32);
               tbObat.setValueAt(KesimpulanHasilSkrining.getText(),tbObat.getSelectedRow(),33);
               tbObat.setValueAt(KetHasilSkrining.getText(),tbObat.getSelectedRow(),34);
               emptTeks();
        }
    }

    private void hapus() {
        if(Sequel.queryu2tf("delete from skrining_tbc where no_rawat=?",1,new String[]{
            tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
        })==true){
            tabMode.removeRow(tbObat.getSelectedRow());
            LCount.setText(""+tabMode.getRowCount());
            emptTeks();
        }else{
            JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
        }
    }
    
    private void isBMI(){
        try {
            if((!TB.getText().equals(""))&&(!BB.getText().equals(""))){
                try {
                    IMT.setText(Valid.SetAngka8(Valid.SetAngka(BB.getText())/((Valid.SetAngka(TB.getText())/100)*(Valid.SetAngka(TB.getText())/100)),1)+"");
                } catch (Exception e) {
                    IMT.setText("");
                }
                if(Valid.SetAngka(IMT.getText())<18.5){
                    KlasifikasiIMT.setText("Berat Badan Kurang");
                }else if((Valid.SetAngka(IMT.getText())>=18.5)&&(Valid.SetAngka(IMT.getText())<=22.9)){
                    KlasifikasiIMT.setText("Berat Badan Normal");
                }else if((Valid.SetAngka(IMT.getText())>=23)&&(Valid.SetAngka(IMT.getText())<=24.9)){
                    KlasifikasiIMT.setText("Kelebihan Berat Badan");
                }else if((Valid.SetAngka(IMT.getText())>=25)&&(Valid.SetAngka(IMT.getText())<=29.9)){
                    KlasifikasiIMT.setText("Obesitas I");
                }else if(Valid.SetAngka(IMT.getText())>=30){
                    KlasifikasiIMT.setText("Obesitas II");
                }else{
                    KlasifikasiIMT.setText("");
                }
            }else{
                IMT.setText("");
                KlasifikasiIMT.setText("");
            }
        } catch (Exception e) {
            IMT.setText("");
            KlasifikasiIMT.setText("");
        }
    }
    
    private void isLP(){
        try {
            if((!LP.getText().equals(""))&&(!Jk.getText().equals(""))&&(!KlasifikasiIMT.getText().equals(""))){
                if(Jk.getText().equals("L")){
                    if(KlasifikasiIMT.getText().equals("Berat Badan Kurang")){
                        if(Valid.SetAngka(LP.getText())<90){
                            RisikoLP.setText("Rendah");
                        }else if(Valid.SetAngka(LP.getText())>=90){
                            RisikoLP.setText("Cukup");
                        }
                    }else if(KlasifikasiIMT.getText().equals("Berat Badan Normal")){
                        if(Valid.SetAngka(LP.getText())<90){
                            RisikoLP.setText("Cukup");
                        }else if(Valid.SetAngka(LP.getText())>=90){
                            RisikoLP.setText("Meningkat");
                        }
                    }else if(KlasifikasiIMT.getText().equals("Kelebihan Berat Badan")){
                        if(Valid.SetAngka(LP.getText())<90){
                            RisikoLP.setText("Meningkat");
                        }else if(Valid.SetAngka(LP.getText())>=90){
                            RisikoLP.setText("Moderat");
                        }
                    }else if(KlasifikasiIMT.getText().equals("Obesitas I")){
                        if(Valid.SetAngka(LP.getText())<90){
                            RisikoLP.setText("Moderat");
                        }else if(Valid.SetAngka(LP.getText())>=90){
                            RisikoLP.setText("Berat");
                        }
                    }else if(KlasifikasiIMT.getText().equals("Obesitas II")){
                        if(Valid.SetAngka(LP.getText())<90){
                            RisikoLP.setText("Berat");
                        }else if(Valid.SetAngka(LP.getText())>=90){
                            RisikoLP.setText("Sangat");
                        }
                    }else{
                        RisikoLP.setText("");
                    }
                }else if(Jk.getText().equals("P")){
                    if(KlasifikasiIMT.getText().equals("Berat Badan Kurang")){
                        if(Valid.SetAngka(LP.getText())<80){
                            RisikoLP.setText("Rendah");
                        }else if(Valid.SetAngka(LP.getText())>=80){
                            RisikoLP.setText("Cukup");
                        }
                    }else if(KlasifikasiIMT.getText().equals("Berat Badan Normal")){
                        if(Valid.SetAngka(LP.getText())<80){
                            RisikoLP.setText("Cukup");
                        }else if(Valid.SetAngka(LP.getText())>=80){
                            RisikoLP.setText("Meningkat");
                        }
                    }else if(KlasifikasiIMT.getText().equals("Kelebihan Berat Badan")){
                        if(Valid.SetAngka(LP.getText())<80){
                            RisikoLP.setText("Meningkat");
                        }else if(Valid.SetAngka(LP.getText())>=80){
                            RisikoLP.setText("Moderat");
                        }
                    }else if(KlasifikasiIMT.getText().equals("Obesitas I")){
                        if(Valid.SetAngka(LP.getText())<80){
                            RisikoLP.setText("Moderat");
                        }else if(Valid.SetAngka(LP.getText())>=80){
                            RisikoLP.setText("Berat");
                        }
                    }else if(KlasifikasiIMT.getText().equals("Obesitas II")){
                        if(Valid.SetAngka(LP.getText())<80){
                            RisikoLP.setText("Berat");
                        }else if(Valid.SetAngka(LP.getText())>=80){
                            RisikoLP.setText("Sangat");
                        }
                    }else{
                        RisikoLP.setText("");
                    }
                }else{
                    RisikoLP.setText("");
                }
            }else{
                RisikoLP.setText("");
            }
        } catch (Exception e) {
            RisikoLP.setText("");
        }
    }
    
    private void isTbc(){
        try {
            if((!RiwayatKontakTBC.getSelectedItem().toString().equals(""))&&(!Batuk.getSelectedItem().toString().equals(""))&&(!BBTurun.getSelectedItem().toString().equals(""))&&(!Demam.getSelectedItem().toString().equals(""))&&(!BerkeringatMalam.getSelectedItem().toString().equals(""))){
                if(RiwayatKontakTBC.getSelectedItem().toString().equals("Tidak")){
                    if((Batuk.getSelectedItem().toString().equals("Ya"))){
                        KesimpulanHasilSkrining.setText("Terduga TBC");
                    }else if((BBTurun.getSelectedItem().toString().equals("Ya"))){
                        KesimpulanHasilSkrining.setText("Terduga TBC");
                    }else if((BBTurun.getSelectedItem().toString().equals("Ya"))){
                        KesimpulanHasilSkrining.setText("Terduga TBC");
                    }else if((Demam.getSelectedItem().toString().equals("Ya"))){
                        KesimpulanHasilSkrining.setText("Terduga TBC");
                    }else if((BerkeringatMalam.getSelectedItem().toString().equals("Ya"))){
                        KesimpulanHasilSkrining.setText("Terduga TBC");
                    }else {
                        KesimpulanHasilSkrining.setText("Bukan Terduga TBC");
                    }
                }else if(RiwayatKontakTBC.getSelectedItem().toString().equals("Ya")){
                    if((Batuk.getSelectedItem().toString().equals("Tidak"))){
                        KesimpulanHasilSkrining.setText("Kontak Erat");
                    }else if((BBTurun.getSelectedItem().toString().equals("Tidak"))){
                        KesimpulanHasilSkrining.setText("Kontak Erat");
                    }else if((BBTurun.getSelectedItem().toString().equals("Tidak"))){
                        KesimpulanHasilSkrining.setText("Kontak Erat");
                    }else if((Demam.getSelectedItem().toString().equals("Tidak"))){
                        KesimpulanHasilSkrining.setText("Kontak Erat");
                    }else if((BerkeringatMalam.getSelectedItem().toString().equals("Tidak"))){
                        KesimpulanHasilSkrining.setText("Kontak Erat");
                    }else {
                        KesimpulanHasilSkrining.setText("Terduga TBC");
                    }
                }else{
                    KesimpulanHasilSkrining.setText("Bukan Terduga TBC");
                }
            }else{
                KesimpulanHasilSkrining.setText("Bukan Terduga TBC");
            }
        } catch (Exception e) {
            KesimpulanHasilSkrining.setText("Bukan Terduga TBC");
        }
    }

    private void simpan() {
        if(Sequel.menyimpantf("skrining_tbc","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","Data",30,new String[]{
            TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),
            BB.getText(),TB.getText(),IMT.getText(),KlasifikasiIMT.getText(),LP.getText(),RisikoLP.getText(),RiwayatKontakTBC.getSelectedItem().toString(),
            JenisKontakTBC.getSelectedItem().toString(),PernahTerdiagnosaTBC.getSelectedItem().toString(),KetPernahTerdiagnosa.getText(),PernahberobatTBC.getSelectedItem().toString(),
            Malnutrisi.getSelectedItem().toString(),Merokok.getSelectedItem().toString(),RiwayatDM.getSelectedItem().toString(),Odhiv.getSelectedItem().toString(),
            Lansia.getSelectedItem().toString(),Ibuhamil.getSelectedItem().toString(),WargaBinaan.getSelectedItem().toString(),Tingaldiwilayahkumuh.getSelectedItem().toString(),
            AbnormalitasTBC.getSelectedItem().toString(),Batuk.getSelectedItem().toString(),BBTurun.getSelectedItem().toString(),Demam.getSelectedItem().toString(),
            BerkeringatMalam.getSelectedItem().toString(),KetGejalaPenyakit.getText(),KesimpulanHasilSkrining.getText(),KetHasilSkrining.getText(),KdPetugas.getText()
        })==true){
            tabMode.addRow(new String[]{
                TNoRw.getText(),TNoRM.getText(),TPasien.getText(),TglLahir.getText(),Jk.getText(),KdPetugas.getText(),NmPetugas.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),
                BB.getText(),TB.getText(),IMT.getText(),KlasifikasiIMT.getText(),LP.getText(),RisikoLP.getText(),RiwayatKontakTBC.getSelectedItem().toString(),JenisKontakTBC.getSelectedItem().toString(),PernahTerdiagnosaTBC.getSelectedItem().toString(),KetPernahTerdiagnosa.getText(),
                PernahberobatTBC.getSelectedItem().toString(),Malnutrisi.getSelectedItem().toString(),Merokok.getSelectedItem().toString(),RiwayatDM.getSelectedItem().toString(),Odhiv.getSelectedItem().toString(),Lansia.getSelectedItem().toString(),Ibuhamil.getSelectedItem().toString(),
                WargaBinaan.getSelectedItem().toString(),Tingaldiwilayahkumuh.getSelectedItem().toString(),AbnormalitasTBC.getSelectedItem().toString(),Batuk.getSelectedItem().toString(),BBTurun.getSelectedItem().toString(),Demam.getSelectedItem().toString(),BerkeringatMalam.getSelectedItem().toString(),
                KetGejalaPenyakit.getText(),KesimpulanHasilSkrining.getText(),KetHasilSkrining.getText()
            });
            LCount.setText(""+tabMode.getRowCount());
            emptTeks();
        } 
    }
}

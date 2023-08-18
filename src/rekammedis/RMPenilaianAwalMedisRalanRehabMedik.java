/*
 * Kontribusi dari Abdul Wahid, RSUD Cipayung Jakarta Timur
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
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import kepegawaian.DlgCariDokter;


/**
 *
 * @author perpustakaan
 */
public final class RMPenilaianAwalMedisRalanRehabMedik extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;
    private DlgCariDokter dokter=new DlgCariDokter(null,false);
    private StringBuilder htmlContent;
    private String finger="";
    
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public RMPenilaianAwalMedisRalanRehabMedik(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.RM","Nama Pasien","Tgl.Lahir","J.K.","NIP","Nama Dokter","Tanggal","Anamnesis","Hubungan","Keluhan Utama","Riwayat Penyakit Sekarang","Riwayat Penyakit Dahulu",
            "Riwayat Alergi","Kesadaran","Nyeri","Skala Nyeri","TD(mmHg)","Nadi(x/menit)","Suhu","RR(x/menit)","BB(Kg)","Kepala",
            "Thoraks","Abdomen","Ekstremitas","Columna Vertebralis","Muskuloskeletal","Lainnya","Resiko Jatuh","Resiko Nutrisional","Kebutuhan Fungsional","Diagnosa Medis","Diagnosa Fungsi","Pemeriksaan Penunjang",
            "Fisioterapi","Okupasi","Wicara","Akupuntur","Tatalaksana Lainnya","Frekuensi Terapi","Terapi Fisio","Terapi Okupasi","Terapi Wicara","Terapi Akupuntur","Terapi Lainnya","Edukasi"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        
        tbObat.setModel(tabMode);
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 47; i++) {
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
                column.setPreferredWidth(65);
            }else if(i==5){
                column.setPreferredWidth(80);
            }else if(i==6){
                column.setPreferredWidth(150);
            }else if(i==7){
                column.setPreferredWidth(115);
            }else if(i==8){
                column.setPreferredWidth(80);
            }else if(i==9){
                column.setPreferredWidth(100);
            }else if(i==10){
                column.setPreferredWidth(300);
            }else if(i==11){
                column.setPreferredWidth(150);
            }else if(i==12){
                column.setPreferredWidth(150);
            }else if(i==13){
                column.setPreferredWidth(150);
            }else if(i==14){
                column.setPreferredWidth(150);
            }else if(i==15){
                column.setPreferredWidth(84);
            }else if(i==16){
                column.setPreferredWidth(76);
            }else if(i==17){
                column.setPreferredWidth(60);
            }else if(i==18){
                column.setPreferredWidth(76);
            }else if(i==19){
                column.setPreferredWidth(45);
            }else if(i==20){
                column.setPreferredWidth(69);
            }else if(i==21){
                column.setPreferredWidth(40);
            }else if(i==22){
                column.setPreferredWidth(150);
            }else if(i==23){
                column.setPreferredWidth(150);
            }else if(i==24){
                column.setPreferredWidth(150);
            }else if(i==25){
                column.setPreferredWidth(150);
            }else if(i==26){
                column.setPreferredWidth(150);
            }else if(i==27){
                column.setPreferredWidth(150);
            }else if(i==28){
                column.setPreferredWidth(150);
            }else if(i==29){
                column.setPreferredWidth(150);
            }else if(i==30){
                column.setPreferredWidth(200);
            }else if(i==31){
                column.setPreferredWidth(200);
            }else if(i==32){
                column.setPreferredWidth(200);
            }else if(i==33){
                column.setPreferredWidth(200);
            }else if(i==34){
                column.setPreferredWidth(150);
            }else if(i==35){
                column.setPreferredWidth(150);
            }else if(i==36){
                column.setPreferredWidth(200);
            }else if(i==37){
                column.setPreferredWidth(200);
            }else if(i==38){
                column.setPreferredWidth(200);
            }else if(i==39){
                column.setPreferredWidth(200);
            }else if(i==40){
                column.setPreferredWidth(150);
            }else if(i==41){
                column.setPreferredWidth(150);
            }else if(i==42){
                column.setPreferredWidth(200);
            }else if(i==43){
                column.setPreferredWidth(200);
            }else if(i==44){
                column.setPreferredWidth(200);
            }else if(i==45){
                column.setPreferredWidth(200);
            }else if(i==46){
                column.setPreferredWidth(200);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());
        
        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        Hubungan.setDocument(new batasInput((int)30).getKata(Hubungan));
        KeluhanUtama.setDocument(new batasInput((int)2000).getKata(KeluhanUtama));
        RPS.setDocument(new batasInput((int)2000).getKata(RPS));
        RPD.setDocument(new batasInput((int)1000).getKata(RPD));
        Alergi.setDocument(new batasInput((int)50).getKata(Alergi));
        TD.setDocument(new batasInput((byte)8).getKata(TD));
        Nadi.setDocument(new batasInput((byte)5).getKata(Nadi));
        Suhu.setDocument(new batasInput((byte)5).getKata(Suhu));
        RR.setDocument(new batasInput((byte)5).getKata(RR));
        BB.setDocument(new batasInput((byte)5).getKata(BB));
        KeteranganKepala.setDocument(new batasInput((byte)30).getKata(KeteranganKepala));
        KeteranganThoraks.setDocument(new batasInput((byte)30).getKata(KeteranganThoraks));
        KeteranganAbdomen.setDocument(new batasInput((byte)30).getKata(KeteranganAbdomen));
        KeteranganMusku.setDocument(new batasInput((byte)30).getKata(KeteranganMusku));
        KeteranganEkstremitas.setDocument(new batasInput((byte)30).getKata(KeteranganEkstremitas));
        KeteranganColumna.setDocument(new batasInput((byte)30).getKata(KeteranganColumna));
        Lainnya.setDocument(new batasInput((int)1000).getKata(Lainnya));
        PenunjangLain.setDocument(new batasInput((int)500).getKata(PenunjangLain));
        DiagnosaMedis.setDocument(new batasInput((int)500).getKata(DiagnosaMedis));
        DiagnosaFungsi.setDocument(new batasInput((int)500).getKata(DiagnosaFungsi));
        PenunjangLain.setDocument(new batasInput((int)500).getKata(PenunjangLain));
        Fisio.setDocument(new batasInput((int)500).getKata(Fisio));
        Okupasi.setDocument(new batasInput((int)500).getKata(Okupasi));
        Wicara.setDocument(new batasInput((int)500).getKata(Wicara));
        Tatalainnya.setDocument(new batasInput((int)500).getKata(Tatalainnya));
        Frekuensiterapi.setDocument(new batasInput((int)500).getKata(Frekuensiterapi));
        //Fisioterapi.setDocument(new batasInput((int)500).getKata(Fisioterapi));
        //Terapiokupasi.setDocument(new batasInput((int)500).getKata(Terapiokupasi));
        //Terapiwicara.setDocument(new batasInput((int)500).getKata(Terapiwicara));
        //Terapiakupuntur.setDocument(new batasInput((int)500).getKata(Terapiakupuntur));
        //Terapilainnya.setDocument(new batasInput((int)500).getKata(Terapilainnya));
        Edukasi.setDocument(new batasInput((int)500).getKata(Edukasi));
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
        
        dokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(dokter.getTable().getSelectedRow()!= -1){
                    KdDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                    NmDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                    KdDokter.requestFocus();
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
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        LoadHTML = new widget.editorpane();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnPenilaianMedis = new javax.swing.JMenuItem();
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
        TNoRw = new widget.TextBox();
        TPasien = new widget.TextBox();
        TNoRM = new widget.TextBox();
        label14 = new widget.Label();
        KdDokter = new widget.TextBox();
        NmDokter = new widget.TextBox();
        BtnDokter = new widget.Button();
        jLabel8 = new widget.Label();
        TglLahir = new widget.TextBox();
        Jk = new widget.TextBox();
        jLabel10 = new widget.Label();
        jLabel11 = new widget.Label();
        jLabel12 = new widget.Label();
        BB = new widget.TextBox();
        jLabel13 = new widget.Label();
        jLabel16 = new widget.Label();
        Nadi = new widget.TextBox();
        jLabel17 = new widget.Label();
        jLabel18 = new widget.Label();
        Suhu = new widget.TextBox();
        jLabel22 = new widget.Label();
        TD = new widget.TextBox();
        jLabel20 = new widget.Label();
        jLabel23 = new widget.Label();
        jLabel25 = new widget.Label();
        RR = new widget.TextBox();
        jLabel26 = new widget.Label();
        Anamnesis = new widget.ComboBox();
        jLabel94 = new widget.Label();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel38 = new widget.Label();
        Hubungan = new widget.TextBox();
        jSeparator12 = new javax.swing.JSeparator();
        jLabel39 = new widget.Label();
        Kesadaran = new widget.ComboBox();
        jLabel29 = new widget.Label();
        jLabel44 = new widget.Label();
        Muskulos = new widget.ComboBox();
        jLabel50 = new widget.Label();
        Columna = new widget.ComboBox();
        jLabel99 = new widget.Label();
        jLabel101 = new widget.Label();
        label11 = new widget.Label();
        TglAsuhan = new widget.Tanggal();
        Nyeri = new widget.ComboBox();
        jLabel95 = new widget.Label();
        scrollPane1 = new widget.ScrollPane();
        KeluhanUtama = new widget.TextArea();
        jLabel30 = new widget.Label();
        scrollPane2 = new widget.ScrollPane();
        RPD = new widget.TextArea();
        jLabel31 = new widget.Label();
        jLabel33 = new widget.Label();
        scrollPane7 = new widget.ScrollPane();
        RPS = new widget.TextArea();
        Alergi = new widget.TextBox();
        jLabel37 = new widget.Label();
        jSeparator14 = new javax.swing.JSeparator();
        jLabel40 = new widget.Label();
        Kepala = new widget.ComboBox();
        jLabel47 = new widget.Label();
        jLabel46 = new widget.Label();
        Thoraks = new widget.ComboBox();
        jLabel49 = new widget.Label();
        Abdomen = new widget.ComboBox();
        jLabel51 = new widget.Label();
        Ekstremitas = new widget.ComboBox();
        KeteranganKepala = new widget.TextBox();
        KeteranganThoraks = new widget.TextBox();
        KeteranganMusku = new widget.TextBox();
        KeteranganAbdomen = new widget.TextBox();
        scrollPane5 = new widget.ScrollPane();
        Lainnya = new widget.TextArea();
        KeteranganEkstremitas = new widget.TextBox();
        KeteranganColumna = new widget.TextBox();
        scrollPane12 = new widget.ScrollPane();
        PenunjangLain = new widget.TextArea();
        jLabel105 = new widget.Label();
        scrollPane9 = new widget.ScrollPane();
        DiagnosaMedis = new widget.TextArea();
        jLabel80 = new widget.Label();
        jLabel81 = new widget.Label();
        scrollPane10 = new widget.ScrollPane();
        DiagnosaFungsi = new widget.TextArea();
        jSeparator17 = new javax.swing.JSeparator();
        jLabel106 = new widget.Label();
        jSeparator19 = new javax.swing.JSeparator();
        jLabel109 = new widget.Label();
        scrollPane14 = new widget.ScrollPane();
        Edukasi = new widget.TextArea();
        Fisio = new widget.TextBox();
        Okupasi = new widget.TextBox();
        Wicara = new widget.TextBox();
        Akupuntur = new widget.TextBox();
        PanelWall1 = new usu.widget.glass.PanelGlass();
        SkalaNyeri = new widget.ComboBox();
        Tatalainnya = new widget.TextBox();
        jSeparator20 = new javax.swing.JSeparator();
        Frekuensiterapi = new widget.TextBox();
        jLabel82 = new widget.Label();
        jLabel83 = new widget.Label();
        jLabel84 = new widget.Label();
        jLabel85 = new widget.Label();
        jLabel86 = new widget.Label();
        jLabel32 = new widget.Label();
        Resikojatuh = new widget.ComboBox();
        Resikonutrisional = new widget.ComboBox();
        jLabel34 = new widget.Label();
        jLabel35 = new widget.Label();
        Kebutuhanfungsional = new widget.ComboBox();
        jLabel107 = new widget.Label();
        jLabel108 = new widget.Label();
        jLabel110 = new widget.Label();
        jLabel111 = new widget.Label();
        jLabel112 = new widget.Label();
        jLabel113 = new widget.Label();
        Fisioterapi = new widget.Tanggal();
        Terapiokupasi = new widget.Tanggal();
        Terapiwicara = new widget.Tanggal();
        Terapiakupuntur = new widget.Tanggal();
        Terapilainnya = new widget.Tanggal();
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

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnPenilaianMedis.setBackground(new java.awt.Color(255, 255, 254));
        MnPenilaianMedis.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPenilaianMedis.setForeground(new java.awt.Color(50, 50, 50));
        MnPenilaianMedis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPenilaianMedis.setText("Laporan Penilaian Medis");
        MnPenilaianMedis.setName("MnPenilaianMedis"); // NOI18N
        MnPenilaianMedis.setPreferredSize(new java.awt.Dimension(220, 26));
        MnPenilaianMedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPenilaianMedisActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnPenilaianMedis);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Penilaian Awal Medis Kedokteran Fisik dan Rehabilitasi ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setPreferredSize(new java.awt.Dimension(467, 500));
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
        TabRawat.setPreferredSize(new java.awt.Dimension(457, 480));
        TabRawat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatMouseClicked(evt);
            }
        });

        internalFrame2.setBorder(null);
        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setPreferredSize(new java.awt.Dimension(102, 480));
        internalFrame2.setLayout(new java.awt.BorderLayout(1, 1));

        scrollInput.setName("scrollInput"); // NOI18N
        scrollInput.setPreferredSize(new java.awt.Dimension(102, 557));

        FormInput.setBackground(new java.awt.Color(255, 255, 255));
        FormInput.setBorder(null);
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(870, 1260));
        FormInput.setLayout(null);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(74, 10, 131, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        FormInput.add(TPasien);
        TPasien.setBounds(309, 10, 260, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        FormInput.add(TNoRM);
        TNoRM.setBounds(207, 10, 100, 23);

        label14.setText("Dokter :");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label14);
        label14.setBounds(0, 40, 70, 23);

        KdDokter.setEditable(false);
        KdDokter.setName("KdDokter"); // NOI18N
        KdDokter.setPreferredSize(new java.awt.Dimension(80, 23));
        KdDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdDokterKeyPressed(evt);
            }
        });
        FormInput.add(KdDokter);
        KdDokter.setBounds(74, 40, 90, 23);

        NmDokter.setEditable(false);
        NmDokter.setName("NmDokter"); // NOI18N
        NmDokter.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmDokter);
        NmDokter.setBounds(166, 40, 180, 23);

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
        BtnDokter.setBounds(348, 40, 28, 23);

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
        jLabel10.setBounds(0, 10, 70, 23);

        jLabel11.setText("J.K. :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(740, 10, 30, 23);

        jLabel12.setText("BB :");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(620, 280, 30, 23);

        BB.setFocusTraversalPolicyProvider(true);
        BB.setName("BB"); // NOI18N
        BB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BBKeyPressed(evt);
            }
        });
        FormInput.add(BB);
        BB.setBounds(660, 280, 50, 23);

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel13.setText("Kg");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(720, 280, 30, 23);

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel16.setText("x/menit");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput.add(jLabel16);
        jLabel16.setBounds(540, 240, 50, 23);

        Nadi.setFocusTraversalPolicyProvider(true);
        Nadi.setName("Nadi"); // NOI18N
        Nadi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NadiKeyPressed(evt);
            }
        });
        FormInput.add(Nadi);
        Nadi.setBounds(492, 240, 45, 23);

        jLabel17.setText("Nadi :");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(448, 240, 40, 23);

        jLabel18.setText("Suhu :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(608, 240, 40, 23);

        Suhu.setFocusTraversalPolicyProvider(true);
        Suhu.setName("Suhu"); // NOI18N
        Suhu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SuhuKeyPressed(evt);
            }
        });
        FormInput.add(Suhu);
        Suhu.setBounds(660, 240, 50, 23);

        jLabel22.setText("TD :");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(276, 240, 30, 23);

        TD.setFocusTraversalPolicyProvider(true);
        TD.setName("TD"); // NOI18N
        TD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TDKeyPressed(evt);
            }
        });
        FormInput.add(TD);
        TD.setBounds(310, 240, 76, 23);

        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel20.setText("°C");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(720, 240, 30, 23);

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel23.setText("mmHg");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(389, 240, 50, 23);

        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel25.setText("x/menit");
        jLabel25.setName("jLabel25"); // NOI18N
        FormInput.add(jLabel25);
        jLabel25.setBounds(840, 240, 50, 23);

        RR.setFocusTraversalPolicyProvider(true);
        RR.setName("RR"); // NOI18N
        RR.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RRKeyPressed(evt);
            }
        });
        FormInput.add(RR);
        RR.setBounds(790, 240, 45, 23);

        jLabel26.setText("RR :");
        jLabel26.setName("jLabel26"); // NOI18N
        FormInput.add(jLabel26);
        jLabel26.setBounds(740, 240, 40, 23);

        Anamnesis.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Autoanamnesis", "Alloanamnesis" }));
        Anamnesis.setName("Anamnesis"); // NOI18N
        Anamnesis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AnamnesisKeyPressed(evt);
            }
        });
        FormInput.add(Anamnesis);
        Anamnesis.setBounds(644, 40, 128, 23);

        jLabel94.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel94.setText("III. STATUS KELAINAN");
        jLabel94.setName("jLabel94"); // NOI18N
        FormInput.add(jLabel94);
        jLabel94.setBounds(10, 380, 180, 23);

        jSeparator1.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator1.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator1.setName("jSeparator1"); // NOI18N
        FormInput.add(jSeparator1);
        jSeparator1.setBounds(0, 70, 880, 1);

        jLabel38.setText("Anamnesis :");
        jLabel38.setName("jLabel38"); // NOI18N
        FormInput.add(jLabel38);
        jLabel38.setBounds(570, 40, 70, 23);

        Hubungan.setName("Hubungan"); // NOI18N
        Hubungan.setPreferredSize(new java.awt.Dimension(207, 23));
        Hubungan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HubunganKeyPressed(evt);
            }
        });
        FormInput.add(Hubungan);
        Hubungan.setBounds(774, 40, 80, 23);

        jSeparator12.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator12.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator12.setName("jSeparator12"); // NOI18N
        FormInput.add(jSeparator12);
        jSeparator12.setBounds(0, 220, 880, 1);

        jLabel39.setText("Kesadaran :");
        jLabel39.setName("jLabel39"); // NOI18N
        FormInput.add(jLabel39);
        jLabel39.setBounds(0, 240, 117, 23);

        Kesadaran.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Compos Mentis", "Apatis", "Somnolen", "Sopor", "Koma" }));
        Kesadaran.setName("Kesadaran"); // NOI18N
        Kesadaran.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KesadaranKeyPressed(evt);
            }
        });
        FormInput.add(Kesadaran);
        Kesadaran.setBounds(121, 240, 130, 23);

        jLabel29.setText("Skala Nyeri :");
        jLabel29.setName("jLabel29"); // NOI18N
        FormInput.add(jLabel29);
        jLabel29.setBounds(440, 310, 70, 23);

        jLabel44.setText("Muskuloskeletal :");
        jLabel44.setName("jLabel44"); // NOI18N
        FormInput.add(jLabel44);
        jLabel44.setBounds(0, 560, 151, 23);

        Muskulos.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Muskulos.setName("Muskulos"); // NOI18N
        Muskulos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MuskulosKeyPressed(evt);
            }
        });
        FormInput.add(Muskulos);
        Muskulos.setBounds(150, 560, 128, 23);

        jLabel50.setText("Columna Vertebralis :");
        jLabel50.setName("jLabel50"); // NOI18N
        FormInput.add(jLabel50);
        jLabel50.setBounds(0, 530, 151, 23);

        Columna.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Columna.setName("Columna"); // NOI18N
        Columna.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ColumnaKeyPressed(evt);
            }
        });
        FormInput.add(Columna);
        Columna.setBounds(150, 530, 128, 23);

        jLabel99.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel99.setText("I. RIWAYAT KESEHATAN");
        jLabel99.setName("jLabel99"); // NOI18N
        FormInput.add(jLabel99);
        jLabel99.setBounds(10, 70, 180, 23);

        jLabel101.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel101.setText("IV. PEMERIKSAAN FISIK DAN UJI FUNGSI");
        jLabel101.setName("jLabel101"); // NOI18N
        FormInput.add(jLabel101);
        jLabel101.setBounds(10, 690, 240, 23);

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label11);
        label11.setBounds(380, 40, 52, 23);

        TglAsuhan.setForeground(new java.awt.Color(50, 70, 50));
        TglAsuhan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "18-08-2023 22:47:12" }));
        TglAsuhan.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        TglAsuhan.setName("TglAsuhan"); // NOI18N
        TglAsuhan.setOpaque(false);
        TglAsuhan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglAsuhanKeyPressed(evt);
            }
        });
        FormInput.add(TglAsuhan);
        TglAsuhan.setBounds(440, 40, 130, 23);

        Nyeri.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Nyeri", "Nyeri Sedang", "Nyeri Sangat Hebat" }));
        Nyeri.setName("Nyeri"); // NOI18N
        Nyeri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NyeriKeyPressed(evt);
            }
        });
        FormInput.add(Nyeri);
        Nyeri.setBounds(450, 280, 130, 23);

        jLabel95.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel95.setText("II. PEMERIKSAAN FISIK");
        jLabel95.setName("jLabel95"); // NOI18N
        FormInput.add(jLabel95);
        jLabel95.setBounds(10, 220, 180, 23);

        scrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane1.setName("scrollPane1"); // NOI18N

        KeluhanUtama.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        KeluhanUtama.setColumns(20);
        KeluhanUtama.setRows(5);
        KeluhanUtama.setName("KeluhanUtama"); // NOI18N
        KeluhanUtama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeluhanUtamaKeyPressed(evt);
            }
        });
        scrollPane1.setViewportView(KeluhanUtama);

        FormInput.add(scrollPane1);
        scrollPane1.setBounds(129, 90, 310, 73);

        jLabel30.setText("Riwayat Penyakit Sekarang :");
        jLabel30.setName("jLabel30"); // NOI18N
        FormInput.add(jLabel30);
        jLabel30.setBounds(440, 90, 150, 23);

        scrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane2.setName("scrollPane2"); // NOI18N

        RPD.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        RPD.setColumns(20);
        RPD.setRows(5);
        RPD.setName("RPD"); // NOI18N
        RPD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RPDKeyPressed(evt);
            }
        });
        scrollPane2.setViewportView(RPD);

        FormInput.add(scrollPane2);
        scrollPane2.setBounds(594, 140, 260, 43);

        jLabel31.setText("Riwayat Penyakit Dahulu :");
        jLabel31.setName("jLabel31"); // NOI18N
        FormInput.add(jLabel31);
        jLabel31.setBounds(440, 140, 150, 23);

        jLabel33.setText("Keluhan Utama :");
        jLabel33.setName("jLabel33"); // NOI18N
        FormInput.add(jLabel33);
        jLabel33.setBounds(0, 90, 125, 23);

        scrollPane7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane7.setName("scrollPane7"); // NOI18N

        RPS.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        RPS.setColumns(20);
        RPS.setRows(5);
        RPS.setName("RPS"); // NOI18N
        RPS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RPSKeyPressed(evt);
            }
        });
        scrollPane7.setViewportView(RPS);

        FormInput.add(scrollPane7);
        scrollPane7.setBounds(594, 90, 260, 43);

        Alergi.setFocusTraversalPolicyProvider(true);
        Alergi.setName("Alergi"); // NOI18N
        Alergi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlergiKeyPressed(evt);
            }
        });
        FormInput.add(Alergi);
        Alergi.setBounds(140, 180, 300, 23);

        jLabel37.setText("Riwayat Alergi :");
        jLabel37.setName("jLabel37"); // NOI18N
        FormInput.add(jLabel37);
        jLabel37.setBounds(20, 180, 110, 23);

        jSeparator14.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator14.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator14.setName("jSeparator14"); // NOI18N
        FormInput.add(jSeparator14);
        jSeparator14.setBounds(0, 370, 880, 3);

        jLabel40.setText("Kepala :");
        jLabel40.setName("jLabel40"); // NOI18N
        FormInput.add(jLabel40);
        jLabel40.setBounds(0, 410, 151, 23);

        Kepala.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Kepala.setName("Kepala"); // NOI18N
        Kepala.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KepalaKeyPressed(evt);
            }
        });
        FormInput.add(Kepala);
        Kepala.setBounds(150, 410, 128, 23);

        jLabel47.setText("Lainnya :");
        jLabel47.setName("jLabel47"); // NOI18N
        FormInput.add(jLabel47);
        jLabel47.setBounds(530, 390, 60, 23);

        jLabel46.setText("Thoraks :");
        jLabel46.setName("jLabel46"); // NOI18N
        FormInput.add(jLabel46);
        jLabel46.setBounds(0, 440, 151, 23);

        Thoraks.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Thoraks.setName("Thoraks"); // NOI18N
        Thoraks.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ThoraksKeyPressed(evt);
            }
        });
        FormInput.add(Thoraks);
        Thoraks.setBounds(150, 440, 128, 23);

        jLabel49.setText("Abdomen :");
        jLabel49.setName("jLabel49"); // NOI18N
        FormInput.add(jLabel49);
        jLabel49.setBounds(0, 470, 151, 23);

        Abdomen.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Abdomen.setName("Abdomen"); // NOI18N
        Abdomen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AbdomenKeyPressed(evt);
            }
        });
        FormInput.add(Abdomen);
        Abdomen.setBounds(150, 470, 128, 23);

        jLabel51.setText("Ekstremitas :");
        jLabel51.setName("jLabel51"); // NOI18N
        FormInput.add(jLabel51);
        jLabel51.setBounds(0, 500, 151, 23);

        Ekstremitas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Ekstremitas.setName("Ekstremitas"); // NOI18N
        Ekstremitas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EkstremitasKeyPressed(evt);
            }
        });
        FormInput.add(Ekstremitas);
        Ekstremitas.setBounds(150, 500, 128, 23);

        KeteranganKepala.setFocusTraversalPolicyProvider(true);
        KeteranganKepala.setName("KeteranganKepala"); // NOI18N
        KeteranganKepala.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKepalaKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganKepala);
        KeteranganKepala.setBounds(280, 410, 190, 23);

        KeteranganThoraks.setFocusTraversalPolicyProvider(true);
        KeteranganThoraks.setName("KeteranganThoraks"); // NOI18N
        KeteranganThoraks.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganThoraksKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganThoraks);
        KeteranganThoraks.setBounds(280, 440, 190, 23);

        KeteranganMusku.setFocusTraversalPolicyProvider(true);
        KeteranganMusku.setName("KeteranganMusku"); // NOI18N
        KeteranganMusku.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganMuskuKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganMusku);
        KeteranganMusku.setBounds(280, 560, 190, 23);

        KeteranganAbdomen.setFocusTraversalPolicyProvider(true);
        KeteranganAbdomen.setName("KeteranganAbdomen"); // NOI18N
        KeteranganAbdomen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganAbdomenKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganAbdomen);
        KeteranganAbdomen.setBounds(280, 470, 190, 23);

        scrollPane5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane5.setName("scrollPane5"); // NOI18N

        Lainnya.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Lainnya.setColumns(20);
        Lainnya.setRows(13);
        Lainnya.setName("Lainnya"); // NOI18N
        Lainnya.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LainnyaKeyPressed(evt);
            }
        });
        scrollPane5.setViewportView(Lainnya);

        FormInput.add(scrollPane5);
        scrollPane5.setBounds(540, 410, 310, 173);

        KeteranganEkstremitas.setFocusTraversalPolicyProvider(true);
        KeteranganEkstremitas.setName("KeteranganEkstremitas"); // NOI18N
        KeteranganEkstremitas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganEkstremitasKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganEkstremitas);
        KeteranganEkstremitas.setBounds(280, 500, 190, 23);

        KeteranganColumna.setFocusTraversalPolicyProvider(true);
        KeteranganColumna.setName("KeteranganColumna"); // NOI18N
        KeteranganColumna.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganColumnaKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganColumna);
        KeteranganColumna.setBounds(280, 530, 190, 23);

        scrollPane12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane12.setName("scrollPane12"); // NOI18N

        PenunjangLain.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        PenunjangLain.setColumns(20);
        PenunjangLain.setRows(5);
        PenunjangLain.setName("PenunjangLain"); // NOI18N
        PenunjangLain.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenunjangLainKeyPressed(evt);
            }
        });
        scrollPane12.setViewportView(PenunjangLain);

        FormInput.add(scrollPane12);
        scrollPane12.setBounds(590, 730, 260, 63);

        jLabel105.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel105.setText("Pemeriksaan Penunjang :");
        jLabel105.setName("jLabel105"); // NOI18N
        FormInput.add(jLabel105);
        jLabel105.setBounds(590, 710, 190, 23);

        scrollPane9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane9.setName("scrollPane9"); // NOI18N

        DiagnosaMedis.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        DiagnosaMedis.setColumns(20);
        DiagnosaMedis.setRows(5);
        DiagnosaMedis.setName("DiagnosaMedis"); // NOI18N
        DiagnosaMedis.setPreferredSize(new java.awt.Dimension(102, 52));
        DiagnosaMedis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosaMedisKeyPressed(evt);
            }
        });
        scrollPane9.setViewportView(DiagnosaMedis);

        FormInput.add(scrollPane9);
        scrollPane9.setBounds(40, 730, 260, 63);

        jLabel80.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel80.setText("Diagnosa Medis :");
        jLabel80.setName("jLabel80"); // NOI18N
        FormInput.add(jLabel80);
        jLabel80.setBounds(40, 710, 150, 23);

        jLabel81.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel81.setText("Diagnosa Fungsi :");
        jLabel81.setName("jLabel81"); // NOI18N
        FormInput.add(jLabel81);
        jLabel81.setBounds(320, 710, 150, 23);

        scrollPane10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane10.setName("scrollPane10"); // NOI18N

        DiagnosaFungsi.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        DiagnosaFungsi.setColumns(20);
        DiagnosaFungsi.setRows(5);
        DiagnosaFungsi.setName("DiagnosaFungsi"); // NOI18N
        DiagnosaFungsi.setPreferredSize(new java.awt.Dimension(102, 52));
        DiagnosaFungsi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosaFungsiKeyPressed(evt);
            }
        });
        scrollPane10.setViewportView(DiagnosaFungsi);

        FormInput.add(scrollPane10);
        scrollPane10.setBounds(320, 730, 260, 63);

        jSeparator17.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator17.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator17.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator17.setName("jSeparator17"); // NOI18N
        FormInput.add(jSeparator17);
        jSeparator17.setBounds(0, 679, 880, 3);

        jLabel106.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel106.setText("Frekuensi terapi :");
        jLabel106.setName("jLabel106"); // NOI18N
        FormInput.add(jLabel106);
        jLabel106.setBounds(30, 1010, 160, 23);

        jSeparator19.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator19.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator19.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator19.setName("jSeparator19"); // NOI18N
        FormInput.add(jSeparator19);
        jSeparator19.setBounds(0, 990, 880, 3);

        jLabel109.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel109.setText("VII. EDUKASI");
        jLabel109.setName("jLabel109"); // NOI18N
        FormInput.add(jLabel109);
        jLabel109.setBounds(10, 1150, 190, 23);

        scrollPane14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane14.setName("scrollPane14"); // NOI18N

        Edukasi.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Edukasi.setColumns(20);
        Edukasi.setRows(5);
        Edukasi.setName("Edukasi"); // NOI18N
        Edukasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EdukasiKeyPressed(evt);
            }
        });
        scrollPane14.setViewportView(Edukasi);

        FormInput.add(scrollPane14);
        scrollPane14.setBounds(40, 1180, 810, 63);

        Fisio.setFocusTraversalPolicyProvider(true);
        Fisio.setName("Fisio"); // NOI18N
        Fisio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FisioKeyPressed(evt);
            }
        });
        FormInput.add(Fisio);
        Fisio.setBounds(200, 830, 250, 23);

        Okupasi.setFocusTraversalPolicyProvider(true);
        Okupasi.setName("Okupasi"); // NOI18N
        Okupasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                OkupasiKeyPressed(evt);
            }
        });
        FormInput.add(Okupasi);
        Okupasi.setBounds(200, 860, 250, 23);

        Wicara.setFocusTraversalPolicyProvider(true);
        Wicara.setName("Wicara"); // NOI18N
        Wicara.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                WicaraKeyPressed(evt);
            }
        });
        FormInput.add(Wicara);
        Wicara.setBounds(200, 890, 250, 23);

        Akupuntur.setFocusTraversalPolicyProvider(true);
        Akupuntur.setName("Akupuntur"); // NOI18N
        Akupuntur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AkupunturKeyPressed(evt);
            }
        });
        FormInput.add(Akupuntur);
        Akupuntur.setBounds(200, 920, 250, 23);

        PanelWall1.setBackground(new java.awt.Color(255, 255, 255));
        PanelWall1.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/picture/nyeri.png"))); // NOI18N
        PanelWall1.setBackgroundImageType(usu.widget.constan.BackgroundConstan.BACKGROUND_IMAGE_STRECT);
        PanelWall1.setOpaque(true);
        PanelWall1.setPreferredSize(new java.awt.Dimension(200, 200));
        PanelWall1.setRound(false);
        PanelWall1.setWarna(new java.awt.Color(110, 110, 110));
        PanelWall1.setLayout(null);
        FormInput.add(PanelWall1);
        PanelWall1.setBounds(60, 270, 330, 80);

        SkalaNyeri.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));
        SkalaNyeri.setName("SkalaNyeri"); // NOI18N
        SkalaNyeri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkalaNyeriKeyPressed(evt);
            }
        });
        FormInput.add(SkalaNyeri);
        SkalaNyeri.setBounds(520, 310, 60, 23);

        Tatalainnya.setFocusTraversalPolicyProvider(true);
        Tatalainnya.setName("Tatalainnya"); // NOI18N
        Tatalainnya.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TatalainnyaKeyPressed(evt);
            }
        });
        FormInput.add(Tatalainnya);
        Tatalainnya.setBounds(200, 950, 250, 23);

        jSeparator20.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator20.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator20.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator20.setName("jSeparator20"); // NOI18N
        FormInput.add(jSeparator20);
        jSeparator20.setBounds(0, 1142, 880, 3);

        Frekuensiterapi.setFocusTraversalPolicyProvider(true);
        Frekuensiterapi.setName("Frekuensiterapi"); // NOI18N
        Frekuensiterapi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FrekuensiterapiKeyPressed(evt);
            }
        });
        FormInput.add(Frekuensiterapi);
        Frekuensiterapi.setBounds(200, 1010, 250, 23);

        jLabel82.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel82.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel82.setText("FISIOTERAPI");
        jLabel82.setName("jLabel82"); // NOI18N
        FormInput.add(jLabel82);
        jLabel82.setBounds(30, 1060, 130, 23);

        jLabel83.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel83.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel83.setText("TERAPI OKUPASI");
        jLabel83.setName("jLabel83"); // NOI18N
        FormInput.add(jLabel83);
        jLabel83.setBounds(180, 1060, 130, 23);

        jLabel84.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel84.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel84.setText("TERAPI WICARA");
        jLabel84.setName("jLabel84"); // NOI18N
        FormInput.add(jLabel84);
        jLabel84.setBounds(330, 1060, 130, 23);

        jLabel85.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel85.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel85.setText("TERAPI AKUPUNTUR");
        jLabel85.setName("jLabel85"); // NOI18N
        FormInput.add(jLabel85);
        jLabel85.setBounds(480, 1060, 130, 23);

        jLabel86.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel86.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel86.setText("TERAPI LAINNYA");
        jLabel86.setName("jLabel86"); // NOI18N
        FormInput.add(jLabel86);
        jLabel86.setBounds(640, 1060, 130, 23);

        jLabel32.setText("Resiko Jatuh :");
        jLabel32.setName("jLabel32"); // NOI18N
        FormInput.add(jLabel32);
        jLabel32.setBounds(30, 630, 80, 23);

        Resikojatuh.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak beresiko", "Beresiko sedang", "Beresiko tinggi" }));
        Resikojatuh.setName("Resikojatuh"); // NOI18N
        Resikojatuh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ResikojatuhActionPerformed(evt);
            }
        });
        Resikojatuh.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ResikojatuhKeyPressed(evt);
            }
        });
        FormInput.add(Resikojatuh);
        Resikojatuh.setBounds(120, 630, 130, 23);

        Resikonutrisional.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak beresiko malnutrisi", "Beresiko malnutrisi", "Malnutrisi" }));
        Resikonutrisional.setName("Resikonutrisional"); // NOI18N
        Resikonutrisional.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ResikonutrisionalKeyPressed(evt);
            }
        });
        FormInput.add(Resikonutrisional);
        Resikonutrisional.setBounds(390, 630, 160, 23);

        jLabel34.setText("Resiko Nutrisional :");
        jLabel34.setName("jLabel34"); // NOI18N
        FormInput.add(jLabel34);
        jLabel34.setBounds(280, 630, 100, 23);

        jLabel35.setText("Kebutuhan Fungsional :");
        jLabel35.setName("jLabel35"); // NOI18N
        FormInput.add(jLabel35);
        jLabel35.setBounds(570, 630, 130, 23);

        Kebutuhanfungsional.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak perlu bantuan", "Perlu bantuan", "Perlu bantuan total" }));
        Kebutuhanfungsional.setName("Kebutuhanfungsional"); // NOI18N
        Kebutuhanfungsional.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KebutuhanfungsionalKeyPressed(evt);
            }
        });
        FormInput.add(Kebutuhanfungsional);
        Kebutuhanfungsional.setBounds(710, 630, 130, 23);

        jLabel107.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel107.setText("V. TATALAKSANA KFR");
        jLabel107.setName("jLabel107"); // NOI18N
        FormInput.add(jLabel107);
        jLabel107.setBounds(10, 800, 190, 23);

        jLabel108.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel108.setText("Fisioterapi :");
        jLabel108.setName("jLabel108"); // NOI18N
        FormInput.add(jLabel108);
        jLabel108.setBounds(30, 830, 160, 23);

        jLabel110.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel110.setText("Terapi Okupasi :");
        jLabel110.setName("jLabel110"); // NOI18N
        FormInput.add(jLabel110);
        jLabel110.setBounds(30, 860, 150, 23);

        jLabel111.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel111.setText("Terapi Wicara :");
        jLabel111.setName("jLabel111"); // NOI18N
        FormInput.add(jLabel111);
        jLabel111.setBounds(30, 890, 150, 23);

        jLabel112.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel112.setText("Terapi Akupuntur :");
        jLabel112.setName("jLabel112"); // NOI18N
        FormInput.add(jLabel112);
        jLabel112.setBounds(30, 920, 160, 23);

        jLabel113.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel113.setText("Terapi lainnya :");
        jLabel113.setName("jLabel113"); // NOI18N
        FormInput.add(jLabel113);
        jLabel113.setBounds(30, 950, 160, 23);

        Fisioterapi.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        Fisioterapi.setForeground(new java.awt.Color(50, 70, 50));
        Fisioterapi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "18-08-2023" }));
        Fisioterapi.setDisplayFormat("dd-MM-yyyy");
        Fisioterapi.setName("Fisioterapi"); // NOI18N
        Fisioterapi.setOpaque(false);
        Fisioterapi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FisioterapiKeyPressed(evt);
            }
        });
        FormInput.add(Fisioterapi);
        Fisioterapi.setBounds(30, 1090, 130, 23);

        Terapiokupasi.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        Terapiokupasi.setForeground(new java.awt.Color(50, 70, 50));
        Terapiokupasi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "18-08-2023" }));
        Terapiokupasi.setDisplayFormat("dd-MM-yyyy");
        Terapiokupasi.setName("Terapiokupasi"); // NOI18N
        Terapiokupasi.setOpaque(false);
        Terapiokupasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TerapiokupasiKeyPressed(evt);
            }
        });
        FormInput.add(Terapiokupasi);
        Terapiokupasi.setBounds(180, 1090, 130, 23);

        Terapiwicara.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        Terapiwicara.setForeground(new java.awt.Color(50, 70, 50));
        Terapiwicara.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "18-08-2023" }));
        Terapiwicara.setDisplayFormat("dd-MM-yyyy");
        Terapiwicara.setName("Terapiwicara"); // NOI18N
        Terapiwicara.setOpaque(false);
        Terapiwicara.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TerapiwicaraKeyPressed(evt);
            }
        });
        FormInput.add(Terapiwicara);
        Terapiwicara.setBounds(330, 1090, 130, 23);

        Terapiakupuntur.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        Terapiakupuntur.setForeground(new java.awt.Color(50, 70, 50));
        Terapiakupuntur.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "18-08-2023" }));
        Terapiakupuntur.setDisplayFormat("dd-MM-yyyy");
        Terapiakupuntur.setName("Terapiakupuntur"); // NOI18N
        Terapiakupuntur.setOpaque(false);
        Terapiakupuntur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TerapiakupunturKeyPressed(evt);
            }
        });
        FormInput.add(Terapiakupuntur);
        Terapiakupuntur.setBounds(480, 1090, 130, 23);

        Terapilainnya.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        Terapilainnya.setForeground(new java.awt.Color(50, 70, 50));
        Terapilainnya.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "18-08-2023" }));
        Terapilainnya.setDisplayFormat("dd-MM-yyyy");
        Terapilainnya.setName("Terapilainnya"); // NOI18N
        Terapilainnya.setOpaque(false);
        Terapilainnya.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TerapilainnyaKeyPressed(evt);
            }
        });
        FormInput.add(Terapilainnya);
        Terapilainnya.setBounds(640, 1090, 130, 23);

        scrollInput.setViewportView(FormInput);

        internalFrame2.add(scrollInput, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Input Penilaian", internalFrame2);

        internalFrame3.setBorder(null);
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setLayout(new java.awt.BorderLayout(1, 1));

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

        internalFrame3.add(Scroll, java.awt.BorderLayout.CENTER);

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel19.setText("Tgl.Asuhan :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "18-08-2023" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "18-08-2023" }));
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

        TabRawat.addTab("Data Penilaian", internalFrame3);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isRawat();
        }else{            
            Valid.pindah(evt,TCari,BtnDokter);
        }
}//GEN-LAST:event_TNoRwKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TNoRM.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"Nama Pasien");
        }else if(NmDokter.getText().trim().equals("")){
            Valid.textKosong(BtnDokter,"Dokter");
        }else if(KeluhanUtama.getText().trim().equals("")){
            Valid.textKosong(KeluhanUtama,"Keluhan Utama");
        }else if(RPS.getText().trim().equals("")){
            Valid.textKosong(RPS,"Riwayat Penyakit Sekarang");
        }else if(RPD.getText().trim().equals("")){
            Valid.textKosong(RPD,"Riwayat Penyakit Dahulu");
        }else{
            String dls = Fisioterapi.getSelectedItem().toString();
            String dls2 = Terapiokupasi.getSelectedItem().toString();
            String dls3 = Terapiwicara.getSelectedItem().toString();
            String dls4 = Terapiakupuntur.getSelectedItem().toString();
            String dls5 = Terapilainnya.getSelectedItem().toString();
            if(Sequel.menyimpantf("penilaian_medis_ralan_rehab_medik","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat",48,new String[]{
                    TNoRw.getText(),Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),KdDokter.getText(),Anamnesis.getSelectedItem().toString(),Hubungan.getText(),
                    KeluhanUtama.getText(),RPS.getText(),RPD.getText(),Alergi.getText(),Kesadaran.getSelectedItem().toString(),Nyeri.getSelectedItem().toString(),SkalaNyeri.getSelectedItem().toString(),TD.getText(),Nadi.getText(),Suhu.getText(),RR.getText(),BB.getText(),
                    Kepala.getSelectedItem().toString(),KeteranganKepala.getText(),Thoraks.getSelectedItem().toString(),KeteranganThoraks.getText(),Abdomen.getSelectedItem().toString(),KeteranganAbdomen.getText(),Ekstremitas.getSelectedItem().toString(),KeteranganEkstremitas.getText(),
                    Columna.getSelectedItem().toString(),KeteranganColumna.getText(),Muskulos.getSelectedItem().toString(),KeteranganMusku.getText(),Lainnya.getText(),Resikojatuh.getSelectedItem().toString(),Resikonutrisional.getSelectedItem().toString(),Kebutuhanfungsional.getSelectedItem().toString(),
                    DiagnosaMedis.getText(),DiagnosaFungsi.getText(),PenunjangLain.getText(),Fisio.getText(),Okupasi.getText(),Wicara.getText(),Akupuntur.getText(),Tatalainnya.getText(),Frekuensiterapi.getText(),Valid.SetTgl(Fisioterapi.getSelectedItem()+""),Valid.SetTgl(Terapiokupasi.getSelectedItem()+""),Valid.SetTgl(Terapiwicara.getSelectedItem()+""),Valid.SetTgl(Terapiakupuntur.getSelectedItem()+""),Valid.SetTgl(Terapilainnya.getSelectedItem()+""),Edukasi.getText()
                })==true){
                    emptTeks();
            }
        }
    
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,Edukasi,BtnBatal);
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
                if(KdDokter.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString())){
                    hapus();
                }else{
                    JOptionPane.showMessageDialog(null,"Hanya bisa dihapus oleh dokter yang bersangkutan..!!");
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
        if(TNoRM.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"Nama Pasien");
        }else if(NmDokter.getText().trim().equals("")){
            Valid.textKosong(BtnDokter,"Dokter");
        }else if(KeluhanUtama.getText().trim().equals("")){
            Valid.textKosong(KeluhanUtama,"Keluhan Utama");
        }else if(RPS.getText().trim().equals("")){
            Valid.textKosong(RPS,"Riwayat Penyakit Sekarang");
        }else if(RPD.getText().trim().equals("")){
            Valid.textKosong(RPD,"Riwayat Penyakit Dahulu");
        }else{
            if(tbObat.getSelectedRow()>-1){
                if(akses.getkode().equals("Admin Utama")){
                    ganti();
                }else{
                    if(KdDokter.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString())){
                        ganti();
                    }else{
                        JOptionPane.showMessageDialog(null,"Hanya bisa diganti oleh dokter yang bersangkutan..!!");
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
                if(TCari.getText().trim().equals("")){
                    ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penilaian_medis_ralan_rehab_medik.tanggal,"+
                        "penilaian_medis_ralan_rehab_medik.kd_dokter,penilaian_medis_ralan_rehab_medik.anamnesis,penilaian_medis_ralan_rehab_medik.hubungan,penilaian_medis_ralan_rehab_medik.keluhan_utama,"+
                        "penilaian_medis_ralan_rehab_medik.rps,penilaian_medis_ralan_rehab_medik.rpd,penilaian_medis_ralan_rehab_medik.alergi,penilaian_medis_ralan_rehab_medik.kesadaran,penilaian_medis_ralan_rehab_medik.nyeri,penilaian_medis_ralan_rehab_medik.skala_nyeri,"+
                        "penilaian_medis_ralan_rehab_medik.td,penilaian_medis_ralan_rehab_medik.nadi,penilaian_medis_ralan_rehab_medik.suhu,penilaian_medis_ralan_rehab_medik.rr,penilaian_medis_ralan_rehab_medik.bb,"+
                        "penilaian_medis_ralan_rehab_medik.kepala,penilaian_medis_ralan_rehab_medik.keterangan_kepala,penilaian_medis_ralan_rehab_medik.thoraks,penilaian_medis_ralan_rehab_medik.keterangan_thoraks,"+
                        "penilaian_medis_ralan_rehab_medik.abdomen,penilaian_medis_ralan_rehab_medik.keterangan_abdomen,penilaian_medis_ralan_rehab_medik.ekstremitas,penilaian_medis_ralan_rehab_medik.keterangan_ekstremitas,"+
                        "penilaian_medis_ralan_rehab_medik.columna,penilaian_medis_ralan_rehab_medik.keterangan_columna,penilaian_medis_ralan_rehab_medik.muskulos,penilaian_medis_ralan_rehab_medik.keterangan_muskulos,"+
                        "penilaian_medis_ralan_rehab_medik.lainnya,penilaian_medis_ralan_rehab_medik.resiko_jatuh,penilaian_medis_ralan_rehab_medik.resiko_nutrisional,penilaian_medis_ralan_rehab_medik.kebutuhan_fungsional,"+
                        "penilaian_medis_ralan_rehab_medik.diagnosa_medis,penilaian_medis_ralan_rehab_medik.diagnosa_fungsi,penilaian_medis_ralan_rehab_medik.penunjang_lain,penilaian_medis_ralan_rehab_medik.fisio,penilaian_medis_ralan_rehab_medik.okupasi,"+
                        "penilaian_medis_ralan_rehab_medik.wicara,penilaian_medis_ralan_rehab_medik.akupuntur,penilaian_medis_ralan_rehab_medik.tatalain,penilaian_medis_ralan_rehab_medik.frekuensi_terapi,penilaian_medis_ralan_rehab_medik.fisioterapi,"+
                        "penilaian_medis_ralan_rehab_medik.terapi_okupasi,penilaian_medis_ralan_rehab_medik.terapi_wicara,penilaian_medis_ralan_rehab_medik.terapi_akupuntur,penilaian_medis_ralan_rehab_medik.terapi_lainnya,penilaian_medis_ralan_rehab_medik.edukasi,dokter.nm_dokter "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join penilaian_medis_ralan_rehab_medik on reg_periksa.no_rawat=penilaian_medis_ralan_rehab_medik.no_rawat "+
                        "inner join dokter on penilaian_medis_ralan_rehab_medik.kd_dokter=dokter.kd_dokter where "+
                        "penilaian_medis_ralan_rehab_medik.tanggal between ? and ? order by penilaian_medis_ralan_rehab_medik.tanggal");
                }else{
                    ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penilaian_medis_ralan_rehab_medik.tanggal,"+
                        "penilaian_medis_ralan_rehab_medik.kd_dokter,penilaian_medis_ralan_rehab_medik.anamnesis,penilaian_medis_ralan_rehab_medik.hubungan,penilaian_medis_ralan_rehab_medik.keluhan_utama,"+
                        "penilaian_medis_ralan_rehab_medik.rps,penilaian_medis_ralan_rehab_medik.rpd,penilaian_medis_ralan_rehab_medik.alergi,penilaian_medis_ralan_rehab_medik.kesadaran,penilaian_medis_ralan_rehab_medik.nyeri,penilaian_medis_ralan_rehab_medik.skala_nyeri,"+
                        "penilaian_medis_ralan_rehab_medik.td,penilaian_medis_ralan_rehab_medik.nadi,penilaian_medis_ralan_rehab_medik.suhu,penilaian_medis_ralan_rehab_medik.rr,penilaian_medis_ralan_rehab_medik.bb,"+
                        "penilaian_medis_ralan_rehab_medik.kepala,penilaian_medis_ralan_rehab_medik.keterangan_kepala,penilaian_medis_ralan_rehab_medik.thoraks,penilaian_medis_ralan_rehab_medik.keterangan_thoraks,"+
                        "penilaian_medis_ralan_rehab_medik.abdomen,penilaian_medis_ralan_rehab_medik.keterangan_abdomen,penilaian_medis_ralan_rehab_medik.ekstremitas,penilaian_medis_ralan_rehab_medik.keterangan_ekstremitas,"+
                        "penilaian_medis_ralan_rehab_medik.columna,penilaian_medis_ralan_rehab_medik.keterangan_columna,penilaian_medis_ralan_rehab_medik.muskulos,penilaian_medis_ralan_rehab_medik.keterangan_muskulos,"+
                        "penilaian_medis_ralan_rehab_medik.lainnya,penilaian_medis_ralan_rehab_medik.resiko_jatuh,penilaian_medis_ralan_rehab_medik.resiko_nutrisional,penilaian_medis_ralan_rehab_medik.kebutuhan_fungsional,"+
                        "penilaian_medis_ralan_rehab_medik.diagnosa_medis,penilaian_medis_ralan_rehab_medik.diagnosa_fungsi,penilaian_medis_ralan_rehab_medik.penunjang_lain,penilaian_medis_ralan_rehab_medik.fisio,penilaian_medis_ralan_rehab_medik.okupasi,"+
                        "penilaian_medis_ralan_rehab_medik.wicara,penilaian_medis_ralan_rehab_medik.akupuntur,penilaian_medis_ralan_rehab_medik.tatalain,penilaian_medis_ralan_rehab_medik.frekuensi_terapi,penilaian_medis_ralan_rehab_medik.fisioterapi,"+
                        "penilaian_medis_ralan_rehab_medik.terapi_okupasi,penilaian_medis_ralan_rehab_medik.terapi_wicara,penilaian_medis_ralan_rehab_medik.terapi_akupuntur,penilaian_medis_ralan_rehab_medik.terapi_lainnya,penilaian_medis_ralan_rehab_medik.edukasi,dokter.nm_dokter "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join penilaian_medis_ralan_rehab_medik on reg_periksa.no_rawat=penilaian_medis_ralan_rehab_medik.no_rawat "+
                        "inner join dokter on penilaian_medis_ralan_rehab_medik.kd_dokter=dokter.kd_dokter where "+
                        "penilaian_medis_ralan_rehab_medik.tanggal between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or "+
                        "penilaian_medis_ralan_rehab_medik.kd_dokter like ? or dokter.nm_dokter like ?) order by penilaian_medis_ralan_rehab_medik.tanggal");
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
                    htmlContent = new StringBuilder();
                    htmlContent.append(                             
                        "<tr class='isi'>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>No.Rawat</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>No.RM</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Nama Pasien</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Tgl.Lahir</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>J.K.</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>NIP</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Nama Dokter</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Tanggal</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Anamnesis</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Hubungan</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Keluhan Utama</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Riwayat Penyakit Sekarang</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Riwayat Penyakit Dahulu</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Riwayat Alergi</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Kesadaran</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Nyeri</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Skala Nyeri</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>TD(mmHg)</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Nadi(x/menit)</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Suhu</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>RR(x/menit)</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>BB(Kg)</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Kepala</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Thoraks</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Abdomen</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Ekstremitas</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Columna</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Muskulos</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Lainnya</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Resiko Jatuh</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Resiko Nutrisional</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Kebutuhan Fungsional</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Diagnosa Medis</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Diagnosa Fungsi</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Pemeriksaan Penunjang</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Fisio</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Okupasi</b></td>"+
			    "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Wicara</b></td>"+
			    "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Akupuntur</b></td>"+
			    "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Tatalaksana Lainnya</b></td>"+
			    "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Frekuensi Terapi</b></td>"+
			    "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Fisioterapi</b></td>"+
			    "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Terapi Okupasi</b></td>"+
			    "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Terapi Wicara</b></td>"+
			    "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Terapi Akupuntur</b></td>"+
			    "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Terapi Lainnya</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Edukasi</b></td>"+
                        "</tr>"
                    );
                    while(rs.next()){
                        htmlContent.append(
                            "<tr class='isi'>"+
                               "<td valign='top'>"+rs.getString("no_rawat")+"</td>"+
                               "<td valign='top'>"+rs.getString("no_rkm_medis")+"</td>"+
                               "<td valign='top'>"+rs.getString("nm_pasien")+"</td>"+
                               "<td valign='top'>"+rs.getString("tgl_lahir")+"</td>"+
                               "<td valign='top'>"+rs.getString("jk")+"</td>"+
                               "<td valign='top'>"+rs.getString("kd_dokter")+"</td>"+
                               "<td valign='top'>"+rs.getString("nm_dokter")+"</td>"+
                               "<td valign='top'>"+rs.getString("tanggal")+"</td>"+
                               "<td valign='top'>"+rs.getString("anamnesis")+"</td>"+
                               "<td valign='top'>"+rs.getString("hubungan")+"</td>"+
                               "<td valign='top'>"+rs.getString("keluhan_utama")+"</td>"+
                               "<td valign='top'>"+rs.getString("rps")+"</td>"+
                               "<td valign='top'>"+rs.getString("rpd")+"</td>"+
                               "<td valign='top'>"+rs.getString("alergi")+"</td>"+
                               "<td valign='top'>"+rs.getString("kesadaran")+"</td>"+
                               "<td valign='top'>"+rs.getString("nyeri")+"</td>"+
                               "<td valign='top'>"+rs.getString("skala_nyeri")+"</td>"+
                               "<td valign='top'>"+rs.getString("td")+"</td>"+
                               "<td valign='top'>"+rs.getString("nadi")+"</td>"+
                               "<td valign='top'>"+rs.getString("suhu")+"</td>"+
                               "<td valign='top'>"+rs.getString("rr")+"</td>"+
                               "<td valign='top'>"+rs.getString("bb")+"</td>"+
                               "<td valign='top'>"+rs.getString("kepala")+", "+rs.getString("keterangan_kepala")+"</td>"+
                               "<td valign='top'>"+rs.getString("thoraks")+", "+rs.getString("keterangan_thoraks")+"</td>"+
                               "<td valign='top'>"+rs.getString("abdomen")+", "+rs.getString("keterangan_abdomen")+"</td>"+
                               "<td valign='top'>"+rs.getString("ekstremitas")+", "+rs.getString("keterangan_ekstremitas")+"</td>"+
                               "<td valign='top'>"+rs.getString("columna")+", "+rs.getString("keterangan_columna")+"</td>"+
                               "<td valign='top'>"+rs.getString("muskulos")+", "+rs.getString("keterangan_muskulos")+"</td>"+
                               "<td valign='top'>"+rs.getString("lainnya")+"</td>"+
                               "<td valign='top'>"+rs.getString("resiko_jatuh")+"</td>"+
                               "<td valign='top'>"+rs.getString("resiko_nutrisional")+"</td>"+
                               "<td valign='top'>"+rs.getString("kebutuhan_fungsional")+"</td>"+
                               "<td valign='top'>"+rs.getString("diagnosa_medis")+"</td>"+
                               "<td valign='top'>"+rs.getString("diagnosa_fungsi")+"</td>"+
                               "<td valign='top'>"+rs.getString("penunjang_lain")+"</td>"+
                               "<td valign='top'>"+rs.getString("fisio")+"</td>"+
                               "<td valign='top'>"+rs.getString("okupasi")+"</td>"+
                               "<td valign='top'>"+rs.getString("wicara")+"</td>"+
                               "<td valign='top'>"+rs.getString("akupuntur")+"</td>"+
                               "<td valign='top'>"+rs.getString("tatalain")+"</td>"+
                               "<td valign='top'>"+rs.getString("frekuensi_terapi")+"</td>"+
                               "<td valign='top'>"+rs.getString("fisioterapi")+"</td>"+
                               "<td valign='top'>"+rs.getString("terapi_okupasi")+"</td>"+
                               "<td valign='top'>"+rs.getString("terapi_wicara")+"</td>"+
                               "<td valign='top'>"+rs.getString("terapi_akupuntur")+"</td>"+
                               "<td valign='top'>"+rs.getString("terapi_lainnya")+"</td>"+
                               "<td valign='top'>"+rs.getString("edukasi")+"</td>"+
                            "</tr>");
                    }
                    LoadHTML.setText(
                        "<html>"+
                          "<table width='4400px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
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

                    File f = new File("DataPenilaianAwalMedisRalan.html");            
                    BufferedWriter bw = new BufferedWriter(new FileWriter(f));            
                    bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                                "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                                "<table width='4400px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                    "<tr class='isi2'>"+
                                        "<td valign='top' align='center'>"+
                                            "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                            akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                            akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                            "<font size='2' face='Tahoma'>DATA PENILAIAN AWAL MEDIS RAWAT JALAN<br><br></font>"+        
                                        "</td>"+
                                   "</tr>"+
                                "</table>")
                    );
                    bw.close();                         
                    Desktop.getDesktop().browse(f.toURI());
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
                getData();
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

    private void KdDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdDokterKeyPressed
        
    }//GEN-LAST:event_KdDokterKeyPressed

    private void BtnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokterActionPerformed
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setAlwaysOnTop(false);
        dokter.setVisible(true);
    }//GEN-LAST:event_BtnDokterActionPerformed

    private void BtnDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokterKeyPressed
        Valid.pindah(evt,Edukasi,BtnSimpan);
    }//GEN-LAST:event_BtnDokterKeyPressed

    private void BBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BBKeyPressed
        Valid.pindah(evt,RR,Kepala);
    }//GEN-LAST:event_BBKeyPressed

    private void NadiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NadiKeyPressed
        Valid.pindah(evt,TD,Suhu);
    }//GEN-LAST:event_NadiKeyPressed

    private void SuhuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SuhuKeyPressed
        Valid.pindah(evt,Nadi,RR);
    }//GEN-LAST:event_SuhuKeyPressed

    private void TDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TDKeyPressed
        Valid.pindah(evt,Kesadaran,Nadi);
    }//GEN-LAST:event_TDKeyPressed

    private void RRKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RRKeyPressed
        Valid.pindah(evt,Suhu,Nyeri);
    }//GEN-LAST:event_RRKeyPressed

    private void AnamnesisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AnamnesisKeyPressed
        Valid.pindah(evt,TglAsuhan,Hubungan);
    }//GEN-LAST:event_AnamnesisKeyPressed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if(TabRawat.getSelectedIndex()==1){
            tampil();
        }
    }//GEN-LAST:event_TabRawatMouseClicked

    private void KesadaranKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KesadaranKeyPressed
        Valid.pindah(evt,Alergi,TD);
    }//GEN-LAST:event_KesadaranKeyPressed

    private void MuskulosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MuskulosKeyPressed
        Valid.pindah(evt,KeteranganColumna,KeteranganMusku);
    }//GEN-LAST:event_MuskulosKeyPressed

    private void ColumnaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ColumnaKeyPressed
        Valid.pindah(evt,KeteranganEkstremitas,KeteranganColumna);
    }//GEN-LAST:event_ColumnaKeyPressed

    private void TglAsuhanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglAsuhanKeyPressed
        Valid.pindah(evt,Edukasi,Anamnesis);
    }//GEN-LAST:event_TglAsuhanKeyPressed

    private void HubunganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HubunganKeyPressed
        Valid.pindah(evt,Anamnesis,KeluhanUtama);
    }//GEN-LAST:event_HubunganKeyPressed

    private void MnPenilaianMedisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPenilaianMedisActionPerformed
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
            param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbObat.getValueAt(tbObat.getSelectedRow(),6).toString()+"\nID "+(finger.equals("")?tbObat.getValueAt(tbObat.getSelectedRow(),5).toString():finger)+"\n"+Valid.SetTgl3(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString())); 
            
            Valid.MyReportqry("rptCetakPenilaianAwalMedisRalanRehabMedik.jasper","report","::[ Laporan Penilaian Awal Medis Rawat Jalan Kedokteran Fisik dan Rehabilitasi ]::",
                "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penilaian_medis_ralan_rehab_medik.tanggal,"+
                        "penilaian_medis_ralan_rehab_medik.kd_dokter,penilaian_medis_ralan_rehab_medik.anamnesis,penilaian_medis_ralan_rehab_medik.hubungan,penilaian_medis_ralan_rehab_medik.keluhan_utama,"+
                        "penilaian_medis_ralan_rehab_medik.rps,penilaian_medis_ralan_rehab_medik.rpd,penilaian_medis_ralan_rehab_medik.alergi,penilaian_medis_ralan_rehab_medik.kesadaran,penilaian_medis_ralan_rehab_medik.nyeri,penilaian_medis_ralan_rehab_medik.skala_nyeri,"+
                        "penilaian_medis_ralan_rehab_medik.td,penilaian_medis_ralan_rehab_medik.nadi,penilaian_medis_ralan_rehab_medik.suhu,penilaian_medis_ralan_rehab_medik.rr,penilaian_medis_ralan_rehab_medik.bb,"+
                        "penilaian_medis_ralan_rehab_medik.kepala,penilaian_medis_ralan_rehab_medik.keterangan_kepala,penilaian_medis_ralan_rehab_medik.thoraks,penilaian_medis_ralan_rehab_medik.keterangan_thoraks,"+
                        "penilaian_medis_ralan_rehab_medik.abdomen,penilaian_medis_ralan_rehab_medik.keterangan_abdomen,penilaian_medis_ralan_rehab_medik.ekstremitas,penilaian_medis_ralan_rehab_medik.keterangan_ekstremitas,"+
                        "penilaian_medis_ralan_rehab_medik.columna,penilaian_medis_ralan_rehab_medik.keterangan_columna,penilaian_medis_ralan_rehab_medik.muskulos,penilaian_medis_ralan_rehab_medik.keterangan_muskulos,"+
                        "penilaian_medis_ralan_rehab_medik.lainnya,penilaian_medis_ralan_rehab_medik.resiko_jatuh,penilaian_medis_ralan_rehab_medik.resiko_nutrisional,penilaian_medis_ralan_rehab_medik.kebutuhan_fungsional,"+
                        "penilaian_medis_ralan_rehab_medik.diagnosa_medis,penilaian_medis_ralan_rehab_medik.diagnosa_fungsi,penilaian_medis_ralan_rehab_medik.penunjang_lain,penilaian_medis_ralan_rehab_medik.fisio,penilaian_medis_ralan_rehab_medik.okupasi,"+
                        "penilaian_medis_ralan_rehab_medik.wicara,penilaian_medis_ralan_rehab_medik.akupuntur,penilaian_medis_ralan_rehab_medik.tatalain,penilaian_medis_ralan_rehab_medik.frekuensi_terapi,penilaian_medis_ralan_rehab_medik.fisioterapi,"+
                        "penilaian_medis_ralan_rehab_medik.terapi_okupasi,penilaian_medis_ralan_rehab_medik.terapi_wicara,penilaian_medis_ralan_rehab_medik.terapi_akupuntur,penilaian_medis_ralan_rehab_medik.terapi_lainnya,penilaian_medis_ralan_rehab_medik.edukasi,dokter.nm_dokter "+
                "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join penilaian_medis_ralan_rehab_medik on reg_periksa.no_rawat=penilaian_medis_ralan_rehab_medik.no_rawat "+
                "inner join dokter on penilaian_medis_ralan_rehab_medik.kd_dokter=dokter.kd_dokter where penilaian_medis_ralan_rehab_medik.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'",param);
        }
    }//GEN-LAST:event_MnPenilaianMedisActionPerformed

    private void KeluhanUtamaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeluhanUtamaKeyPressed
        Valid.pindah2(evt,Hubungan,RPS);
    }//GEN-LAST:event_KeluhanUtamaKeyPressed

    private void RPDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RPDKeyPressed
        Valid.pindah2(evt,RPS,Alergi);
    }//GEN-LAST:event_RPDKeyPressed

    private void RPSKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RPSKeyPressed
        Valid.pindah2(evt,KeluhanUtama,RPD);
    }//GEN-LAST:event_RPSKeyPressed

    private void AlergiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlergiKeyPressed
        Valid.pindah(evt,RPD,Kesadaran);
    }//GEN-LAST:event_AlergiKeyPressed

    private void KepalaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KepalaKeyPressed
        Valid.pindah(evt,BB,KeteranganKepala);
    }//GEN-LAST:event_KepalaKeyPressed

    private void ThoraksKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ThoraksKeyPressed
        Valid.pindah(evt,KeteranganKepala,KeteranganThoraks);
    }//GEN-LAST:event_ThoraksKeyPressed

    private void AbdomenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AbdomenKeyPressed
        Valid.pindah(evt,KeteranganThoraks,KeteranganAbdomen);
    }//GEN-LAST:event_AbdomenKeyPressed

    private void EkstremitasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EkstremitasKeyPressed
        Valid.pindah(evt,KeteranganAbdomen,KeteranganEkstremitas);
    }//GEN-LAST:event_EkstremitasKeyPressed

    private void KeteranganKepalaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKepalaKeyPressed
        Valid.pindah(evt,Kepala,Thoraks);
    }//GEN-LAST:event_KeteranganKepalaKeyPressed

    private void KeteranganThoraksKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganThoraksKeyPressed
        Valid.pindah(evt,Thoraks,Abdomen);
    }//GEN-LAST:event_KeteranganThoraksKeyPressed

    private void KeteranganMuskuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganMuskuKeyPressed
        Valid.pindah(evt,Muskulos,Lainnya);
    }//GEN-LAST:event_KeteranganMuskuKeyPressed

    private void KeteranganAbdomenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganAbdomenKeyPressed
        Valid.pindah(evt,Abdomen,Ekstremitas);
    }//GEN-LAST:event_KeteranganAbdomenKeyPressed

    private void LainnyaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LainnyaKeyPressed
        Valid.pindah2(evt,KeteranganMusku,Resikojatuh);
    }//GEN-LAST:event_LainnyaKeyPressed

    private void KeteranganEkstremitasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganEkstremitasKeyPressed
        Valid.pindah(evt,Ekstremitas,Columna);
    }//GEN-LAST:event_KeteranganEkstremitasKeyPressed

    private void KeteranganColumnaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganColumnaKeyPressed
        Valid.pindah(evt,Columna,Muskulos);
    }//GEN-LAST:event_KeteranganColumnaKeyPressed

    private void PenunjangLainKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenunjangLainKeyPressed
        Valid.pindah2(evt,DiagnosaFungsi,Fisio);
    }//GEN-LAST:event_PenunjangLainKeyPressed

    private void DiagnosaMedisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosaMedisKeyPressed
        Valid.pindah2(evt,Kebutuhanfungsional,DiagnosaFungsi);
    }//GEN-LAST:event_DiagnosaMedisKeyPressed

    private void DiagnosaFungsiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosaFungsiKeyPressed
        Valid.pindah2(evt,DiagnosaMedis,PenunjangLain);
    }//GEN-LAST:event_DiagnosaFungsiKeyPressed

    private void EdukasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EdukasiKeyPressed
        Valid.pindah2(evt,Terapilainnya,BtnSimpan);
    }//GEN-LAST:event_EdukasiKeyPressed

    private void NyeriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NyeriKeyPressed
        Valid.pindah(evt,Kesadaran,SkalaNyeri);
    }//GEN-LAST:event_NyeriKeyPressed

    private void FisioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FisioKeyPressed
        Valid.pindah(evt,PenunjangLain,Okupasi);
    }//GEN-LAST:event_FisioKeyPressed

    private void OkupasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_OkupasiKeyPressed
        Valid.pindah(evt,Fisio,Wicara);
    }//GEN-LAST:event_OkupasiKeyPressed

    private void WicaraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_WicaraKeyPressed
        Valid.pindah(evt,Okupasi,Akupuntur);
    }//GEN-LAST:event_WicaraKeyPressed

    private void AkupunturKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AkupunturKeyPressed
        Valid.pindah(evt,Wicara,Tatalainnya);
    }//GEN-LAST:event_AkupunturKeyPressed

    private void SkalaNyeriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkalaNyeriKeyPressed
        Valid.pindah(evt,Nyeri,Kepala);
    }//GEN-LAST:event_SkalaNyeriKeyPressed

    private void TatalainnyaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TatalainnyaKeyPressed
        Valid.pindah(evt,Akupuntur,Frekuensiterapi);
    }//GEN-LAST:event_TatalainnyaKeyPressed

    private void FrekuensiterapiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FrekuensiterapiKeyPressed
        Valid.pindah(evt,Tatalainnya,Fisioterapi);
    }//GEN-LAST:event_FrekuensiterapiKeyPressed

    private void ResikojatuhKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ResikojatuhKeyPressed
        Valid.pindah(evt,Lainnya,Resikonutrisional);
    }//GEN-LAST:event_ResikojatuhKeyPressed

    private void ResikonutrisionalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ResikonutrisionalKeyPressed
        Valid.pindah(evt,Resikojatuh,Kebutuhanfungsional);
    }//GEN-LAST:event_ResikonutrisionalKeyPressed

    private void ResikojatuhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ResikojatuhActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ResikojatuhActionPerformed

    private void KebutuhanfungsionalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KebutuhanfungsionalKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KebutuhanfungsionalKeyPressed

    private void FisioterapiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FisioterapiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_FisioterapiKeyPressed

    private void TerapiokupasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TerapiokupasiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TerapiokupasiKeyPressed

    private void TerapiwicaraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TerapiwicaraKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TerapiwicaraKeyPressed

    private void TerapiakupunturKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TerapiakupunturKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TerapiakupunturKeyPressed

    private void TerapilainnyaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TerapilainnyaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TerapilainnyaKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMPenilaianAwalMedisRalanRehabMedik dialog = new RMPenilaianAwalMedisRalanRehabMedik(new javax.swing.JFrame(), true);
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
    private widget.ComboBox Abdomen;
    private widget.TextBox Akupuntur;
    private widget.TextBox Alergi;
    private widget.ComboBox Anamnesis;
    private widget.TextBox BB;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnDokter;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.ComboBox Columna;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.TextArea DiagnosaFungsi;
    private widget.TextArea DiagnosaMedis;
    private widget.TextArea Edukasi;
    private widget.ComboBox Ekstremitas;
    private widget.TextBox Fisio;
    private widget.Tanggal Fisioterapi;
    private widget.PanelBiasa FormInput;
    private widget.TextBox Frekuensiterapi;
    private widget.TextBox Hubungan;
    private widget.TextBox Jk;
    private widget.TextBox KdDokter;
    private widget.ComboBox Kebutuhanfungsional;
    private widget.TextArea KeluhanUtama;
    private widget.ComboBox Kepala;
    private widget.ComboBox Kesadaran;
    private widget.TextBox KeteranganAbdomen;
    private widget.TextBox KeteranganColumna;
    private widget.TextBox KeteranganEkstremitas;
    private widget.TextBox KeteranganKepala;
    private widget.TextBox KeteranganMusku;
    private widget.TextBox KeteranganThoraks;
    private widget.Label LCount;
    private widget.TextArea Lainnya;
    private widget.editorpane LoadHTML;
    private javax.swing.JMenuItem MnPenilaianMedis;
    private widget.ComboBox Muskulos;
    private widget.TextBox Nadi;
    private widget.TextBox NmDokter;
    private widget.ComboBox Nyeri;
    private widget.TextBox Okupasi;
    private usu.widget.glass.PanelGlass PanelWall1;
    private widget.TextArea PenunjangLain;
    private widget.TextArea RPD;
    private widget.TextArea RPS;
    private widget.TextBox RR;
    private widget.ComboBox Resikojatuh;
    private widget.ComboBox Resikonutrisional;
    private widget.ScrollPane Scroll;
    private widget.ComboBox SkalaNyeri;
    private widget.TextBox Suhu;
    private widget.TextBox TCari;
    private widget.TextBox TD;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private javax.swing.JTabbedPane TabRawat;
    private widget.TextBox Tatalainnya;
    private widget.Tanggal Terapiakupuntur;
    private widget.Tanggal Terapilainnya;
    private widget.Tanggal Terapiokupasi;
    private widget.Tanggal Terapiwicara;
    private widget.Tanggal TglAsuhan;
    private widget.TextBox TglLahir;
    private widget.ComboBox Thoraks;
    private widget.TextBox Wicara;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.Label jLabel10;
    private widget.Label jLabel101;
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
    private widget.Label jLabel12;
    private widget.Label jLabel13;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
    private widget.Label jLabel18;
    private widget.Label jLabel19;
    private widget.Label jLabel20;
    private widget.Label jLabel21;
    private widget.Label jLabel22;
    private widget.Label jLabel23;
    private widget.Label jLabel25;
    private widget.Label jLabel26;
    private widget.Label jLabel29;
    private widget.Label jLabel30;
    private widget.Label jLabel31;
    private widget.Label jLabel32;
    private widget.Label jLabel33;
    private widget.Label jLabel34;
    private widget.Label jLabel35;
    private widget.Label jLabel37;
    private widget.Label jLabel38;
    private widget.Label jLabel39;
    private widget.Label jLabel40;
    private widget.Label jLabel44;
    private widget.Label jLabel46;
    private widget.Label jLabel47;
    private widget.Label jLabel49;
    private widget.Label jLabel50;
    private widget.Label jLabel51;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel80;
    private widget.Label jLabel81;
    private widget.Label jLabel82;
    private widget.Label jLabel83;
    private widget.Label jLabel84;
    private widget.Label jLabel85;
    private widget.Label jLabel86;
    private widget.Label jLabel94;
    private widget.Label jLabel95;
    private widget.Label jLabel99;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator17;
    private javax.swing.JSeparator jSeparator19;
    private javax.swing.JSeparator jSeparator20;
    private widget.Label label11;
    private widget.Label label14;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollInput;
    private widget.ScrollPane scrollPane1;
    private widget.ScrollPane scrollPane10;
    private widget.ScrollPane scrollPane12;
    private widget.ScrollPane scrollPane14;
    private widget.ScrollPane scrollPane2;
    private widget.ScrollPane scrollPane5;
    private widget.ScrollPane scrollPane7;
    private widget.ScrollPane scrollPane9;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            if(TCari.getText().trim().equals("")){
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penilaian_medis_ralan_rehab_medik.tanggal,"+
                        "penilaian_medis_ralan_rehab_medik.kd_dokter,penilaian_medis_ralan_rehab_medik.anamnesis,penilaian_medis_ralan_rehab_medik.hubungan,penilaian_medis_ralan_rehab_medik.keluhan_utama,"+
                        "penilaian_medis_ralan_rehab_medik.rps,penilaian_medis_ralan_rehab_medik.rpd,penilaian_medis_ralan_rehab_medik.alergi,penilaian_medis_ralan_rehab_medik.kesadaran,penilaian_medis_ralan_rehab_medik.nyeri,penilaian_medis_ralan_rehab_medik.skala_nyeri,"+
                        "penilaian_medis_ralan_rehab_medik.td,penilaian_medis_ralan_rehab_medik.nadi,penilaian_medis_ralan_rehab_medik.suhu,penilaian_medis_ralan_rehab_medik.rr,penilaian_medis_ralan_rehab_medik.bb,"+
                        "penilaian_medis_ralan_rehab_medik.kepala,penilaian_medis_ralan_rehab_medik.keterangan_kepala,penilaian_medis_ralan_rehab_medik.thoraks,penilaian_medis_ralan_rehab_medik.keterangan_thoraks,"+
                        "penilaian_medis_ralan_rehab_medik.abdomen,penilaian_medis_ralan_rehab_medik.keterangan_abdomen,penilaian_medis_ralan_rehab_medik.ekstremitas,penilaian_medis_ralan_rehab_medik.keterangan_ekstremitas,"+
                        "penilaian_medis_ralan_rehab_medik.columna,penilaian_medis_ralan_rehab_medik.keterangan_columna,penilaian_medis_ralan_rehab_medik.muskulos,penilaian_medis_ralan_rehab_medik.keterangan_muskulos,"+
                        "penilaian_medis_ralan_rehab_medik.lainnya,penilaian_medis_ralan_rehab_medik.resiko_jatuh,penilaian_medis_ralan_rehab_medik.resiko_nutrisional,penilaian_medis_ralan_rehab_medik.kebutuhan_fungsional,"+
                        "penilaian_medis_ralan_rehab_medik.diagnosa_medis,penilaian_medis_ralan_rehab_medik.diagnosa_fungsi,penilaian_medis_ralan_rehab_medik.penunjang_lain,penilaian_medis_ralan_rehab_medik.fisio,penilaian_medis_ralan_rehab_medik.okupasi,"+
                        "penilaian_medis_ralan_rehab_medik.wicara,penilaian_medis_ralan_rehab_medik.akupuntur,penilaian_medis_ralan_rehab_medik.tatalain,penilaian_medis_ralan_rehab_medik.frekuensi_terapi,penilaian_medis_ralan_rehab_medik.fisioterapi,"+
                        "penilaian_medis_ralan_rehab_medik.terapi_okupasi,penilaian_medis_ralan_rehab_medik.terapi_wicara,penilaian_medis_ralan_rehab_medik.terapi_akupuntur,penilaian_medis_ralan_rehab_medik.terapi_lainnya,penilaian_medis_ralan_rehab_medik.edukasi,dokter.nm_dokter "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join penilaian_medis_ralan_rehab_medik on reg_periksa.no_rawat=penilaian_medis_ralan_rehab_medik.no_rawat "+
                        "inner join dokter on penilaian_medis_ralan_rehab_medik.kd_dokter=dokter.kd_dokter where "+
                        "penilaian_medis_ralan_rehab_medik.tanggal between ? and ? order by penilaian_medis_ralan_rehab_medik.tanggal");
            }else{
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penilaian_medis_ralan_rehab_medik.tanggal,"+
                        "penilaian_medis_ralan_rehab_medik.kd_dokter,penilaian_medis_ralan_rehab_medik.anamnesis,penilaian_medis_ralan_rehab_medik.hubungan,penilaian_medis_ralan_rehab_medik.keluhan_utama,"+
                        "penilaian_medis_ralan_rehab_medik.rps,penilaian_medis_ralan_rehab_medik.rpd,penilaian_medis_ralan_rehab_medik.alergi,penilaian_medis_ralan_rehab_medik.kesadaran,penilaian_medis_ralan_rehab_medik.nyeri,penilaian_medis_ralan_rehab_medik.skala_nyeri,"+
                        "penilaian_medis_ralan_rehab_medik.td,penilaian_medis_ralan_rehab_medik.nadi,penilaian_medis_ralan_rehab_medik.suhu,penilaian_medis_ralan_rehab_medik.rr,penilaian_medis_ralan_rehab_medik.bb,"+
                        "penilaian_medis_ralan_rehab_medik.kepala,penilaian_medis_ralan_rehab_medik.keterangan_kepala,penilaian_medis_ralan_rehab_medik.thoraks,penilaian_medis_ralan_rehab_medik.keterangan_thoraks,"+
                        "penilaian_medis_ralan_rehab_medik.abdomen,penilaian_medis_ralan_rehab_medik.keterangan_abdomen,penilaian_medis_ralan_rehab_medik.ekstremitas,penilaian_medis_ralan_rehab_medik.keterangan_ekstremitas,"+
                        "penilaian_medis_ralan_rehab_medik.columna,penilaian_medis_ralan_rehab_medik.keterangan_columna,penilaian_medis_ralan_rehab_medik.muskulos,penilaian_medis_ralan_rehab_medik.keterangan_muskulos,"+
                        "penilaian_medis_ralan_rehab_medik.lainnya,penilaian_medis_ralan_rehab_medik.resiko_jatuh,penilaian_medis_ralan_rehab_medik.resiko_nutrisional,penilaian_medis_ralan_rehab_medik.kebutuhan_fungsional,"+
                        "penilaian_medis_ralan_rehab_medik.diagnosa_medis,penilaian_medis_ralan_rehab_medik.diagnosa_fungsi,penilaian_medis_ralan_rehab_medik.penunjang_lain,penilaian_medis_ralan_rehab_medik.fisio,penilaian_medis_ralan_rehab_medik.okupasi,"+
                        "penilaian_medis_ralan_rehab_medik.wicara,penilaian_medis_ralan_rehab_medik.akupuntur,penilaian_medis_ralan_rehab_medik.tatalain,penilaian_medis_ralan_rehab_medik.frekuensi_terapi,penilaian_medis_ralan_rehab_medik.fisioterapi,"+
                        "penilaian_medis_ralan_rehab_medik.terapi_okupasi,penilaian_medis_ralan_rehab_medik.terapi_wicara,penilaian_medis_ralan_rehab_medik.terapi_akupuntur,penilaian_medis_ralan_rehab_medik.terapi_lainnya,penilaian_medis_ralan_rehab_medik.edukasi,dokter.nm_dokter "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join penilaian_medis_ralan_rehab_medik on reg_periksa.no_rawat=penilaian_medis_ralan_rehab_medik.no_rawat "+
                        "inner join dokter on penilaian_medis_ralan_rehab_medik.kd_dokter=dokter.kd_dokter where "+
                        "penilaian_medis_ralan_rehab_medik.tanggal between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or "+
                        "penilaian_medis_ralan_rehab_medik.kd_dokter like ? or dokter.nm_dokter like ?) order by penilaian_medis_ralan_rehab_medik.tanggal");
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
                    tabMode.addRow(new String[]{
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("tgl_lahir"),rs.getString("jk"),rs.getString("kd_dokter"),rs.getString("nm_dokter"),rs.getString("tanggal"),
                        rs.getString("anamnesis"),rs.getString("hubungan"),rs.getString("keluhan_utama"),rs.getString("rps"),rs.getString("rpd"),rs.getString("alergi"),rs.getString("kesadaran"),rs.getString("nyeri"),
                        rs.getString("skala_nyeri"),rs.getString("td"),rs.getString("nadi"),rs.getString("suhu"),rs.getString("rr"),rs.getString("bb"),rs.getString("kepala")+", "+rs.getString("keterangan_kepala"),
                        rs.getString("thoraks")+", "+rs.getString("keterangan_thoraks"),rs.getString("abdomen")+", "+rs.getString("keterangan_abdomen"),rs.getString("ekstremitas")+", "+rs.getString("keterangan_ekstremitas"),
                        rs.getString("columna")+", "+rs.getString("keterangan_columna"),rs.getString("muskulos")+", "+rs.getString("keterangan_muskulos"),rs.getString("lainnya"),rs.getString("resiko_jatuh"),rs.getString("resiko_nutrisional"),
                        rs.getString("kebutuhan_fungsional"),rs.getString("diagnosa_medis"),rs.getString("diagnosa_fungsi"),rs.getString("penunjang_lain"),rs.getString("fisio"),rs.getString("okupasi"),rs.getString("wicara"),rs.getString("akupuntur"),
                        rs.getString("tatalain"),rs.getString("frekuensi_terapi"),rs.getString("fisioterapi"),rs.getString("terapi_okupasi"),rs.getString("terapi_wicara"),rs.getString("terapi_akupuntur"),rs.getString("terapi_lainnya"),rs.getString("edukasi")
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
        Anamnesis.setSelectedIndex(0);
        Hubungan.setText("");
        KeluhanUtama.setText("");
        RPS.setText("");
        RPD.setText("");
        Alergi.setText("");
        Kesadaran.setSelectedIndex(0);
        TD.setText("");
        Nadi.setText("");
        Suhu.setText("");
        RR.setText("");
        BB.setText("");
        Nyeri.setSelectedIndex(0);
        SkalaNyeri.setSelectedIndex(0);
        Kepala.setSelectedIndex(0);
        Thoraks.setSelectedIndex(0);
        Abdomen.setSelectedIndex(0);
        Ekstremitas.setSelectedIndex(0);
        Columna.setSelectedIndex(0);
        Muskulos.setSelectedIndex(0);
        KeteranganKepala.setText("");
        KeteranganThoraks.setText("");
        KeteranganAbdomen.setText("");
        KeteranganEkstremitas.setText("");
        KeteranganColumna.setText("");
        KeteranganMusku.setText("");
        Lainnya.setText("");
        Resikojatuh.setSelectedIndex(0);
        Resikonutrisional.setSelectedIndex(0);
        Kebutuhanfungsional.setSelectedIndex(0);
        DiagnosaMedis.setText("");
        DiagnosaFungsi.setText("");
        PenunjangLain.setText("");
        Fisio.setText("");
        Okupasi.setText("");
        Wicara.setText("");
        Akupuntur.setText("");
        Tatalainnya.setText("");
        Fisioterapi.setDate(new Date());
        Terapiokupasi.setDate(new Date());
        Terapiwicara.setDate(new Date());
        Terapiakupuntur.setDate(new Date());
        Terapilainnya.setDate(new Date());
        Edukasi.setText("");
        TglAsuhan.setDate(new Date());
        TabRawat.setSelectedIndex(0);
        Anamnesis.requestFocus();
    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()); 
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            Jk.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString()); 
            KdDokter.setText(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
            NmDokter.setText(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString());
            Anamnesis.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            Hubungan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            KeluhanUtama.setText(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            RPS.setText(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            RPD.setText(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            Alergi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            Kesadaran.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            Nyeri.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
            SkalaNyeri.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            TD.setText(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());
            Nadi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
            Suhu.setText(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
            RR.setText(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString());
            BB.setText(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString());
            if(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString().contains("Normal")){
                Kepala.setSelectedItem("Normal");
            }else if(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString().contains("Abnormal")){
                Kepala.setSelectedItem("Abnormal");
            }else{
                Kepala.setSelectedItem("Tidak Diperiksa");
            }
            KeteranganKepala.setText(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString().replaceAll(Kepala.getSelectedItem().toString()+", ",""));
            if(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString().contains("Normal")){
                Thoraks.setSelectedItem("Normal");
            }else if(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString().contains("Abnormal")){
                Thoraks.setSelectedItem("Abnormal");
            }else{
                Thoraks.setSelectedItem("Tidak Diperiksa");
            }
            KeteranganThoraks.setText(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString().replaceAll(Kepala.getSelectedItem().toString()+", ",""));
            if(tbObat.getValueAt(tbObat.getSelectedRow(),24).toString().contains("Normal")){
                Abdomen.setSelectedItem("Normal");
            }else if(tbObat.getValueAt(tbObat.getSelectedRow(),24).toString().contains("Abnormal")){
                Abdomen.setSelectedItem("Abnormal");
            }else{
                Abdomen.setSelectedItem("Tidak Diperiksa");
            }
            KeteranganAbdomen.setText(tbObat.getValueAt(tbObat.getSelectedRow(),24).toString().replaceAll(Kepala.getSelectedItem().toString()+", ",""));
            if(tbObat.getValueAt(tbObat.getSelectedRow(),25).toString().contains("Normal")){
                Ekstremitas.setSelectedItem("Normal");
            }else if(tbObat.getValueAt(tbObat.getSelectedRow(),25).toString().contains("Abnormal")){
                Ekstremitas.setSelectedItem("Abnormal");
            }else{
                Ekstremitas.setSelectedItem("Tidak Diperiksa");
            }
            KeteranganEkstremitas.setText(tbObat.getValueAt(tbObat.getSelectedRow(),25).toString().replaceAll(Kepala.getSelectedItem().toString()+", ",""));
            if(tbObat.getValueAt(tbObat.getSelectedRow(),26).toString().contains("Normal")){
                Columna.setSelectedItem("Normal");
            }else if(tbObat.getValueAt(tbObat.getSelectedRow(),26).toString().contains("Abnormal")){
                Columna.setSelectedItem("Abnormal");
            }else{
                Columna.setSelectedItem("Tidak Diperiksa");
            }
            KeteranganColumna.setText(tbObat.getValueAt(tbObat.getSelectedRow(),26).toString().replaceAll(Kepala.getSelectedItem().toString()+", ",""));
            if(tbObat.getValueAt(tbObat.getSelectedRow(),27).toString().contains("Normal")){
                Muskulos.setSelectedItem("Normal");
            }else if(tbObat.getValueAt(tbObat.getSelectedRow(),27).toString().contains("Abnormal")){
                Muskulos.setSelectedItem("Abnormal");
            }else{
                Muskulos.setSelectedItem("Tidak Diperiksa");
            }
            KeteranganMusku.setText(tbObat.getValueAt(tbObat.getSelectedRow(),27).toString().replaceAll(Kepala.getSelectedItem().toString()+", ",""));
            Lainnya.setText(tbObat.getValueAt(tbObat.getSelectedRow(),28).toString());
            Resikojatuh.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),29).toString());
            Resikonutrisional.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),30).toString());
            Kebutuhanfungsional.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),31).toString());
            DiagnosaMedis.setText(tbObat.getValueAt(tbObat.getSelectedRow(),32).toString());
            DiagnosaFungsi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),33).toString());
            PenunjangLain.setText(tbObat.getValueAt(tbObat.getSelectedRow(),34).toString());
            Fisio.setText(tbObat.getValueAt(tbObat.getSelectedRow(),35).toString());
            Okupasi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),36).toString());
            Wicara.setText(tbObat.getValueAt(tbObat.getSelectedRow(),37).toString());
            Akupuntur.setText(tbObat.getValueAt(tbObat.getSelectedRow(),38).toString());
            Tatalainnya.setText(tbObat.getValueAt(tbObat.getSelectedRow(),39).toString());
            Frekuensiterapi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),40).toString());
            Valid.SetTgl(Fisioterapi,tbObat.getValueAt(tbObat.getSelectedRow(),41).toString());
            Valid.SetTgl(Terapiokupasi,tbObat.getValueAt(tbObat.getSelectedRow(),42).toString());
            Valid.SetTgl(Terapiwicara,tbObat.getValueAt(tbObat.getSelectedRow(),43).toString());
            Valid.SetTgl(Terapiakupuntur,tbObat.getValueAt(tbObat.getSelectedRow(),44).toString());
            Valid.SetTgl(Terapilainnya,tbObat.getValueAt(tbObat.getSelectedRow(),45).toString());
            Edukasi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),46).toString());
            Valid.SetTgl2(TglAsuhan,tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());
        }
    }

    private void isRawat() {
        try {
            ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rkm_medis,pasien.nm_pasien, if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,reg_periksa.tgl_registrasi "+
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
        BtnSimpan.setEnabled(akses.getpenilaian_awal_medis_ralan());
        BtnHapus.setEnabled(akses.getpenilaian_awal_medis_ralan());
        BtnEdit.setEnabled(akses.getpenilaian_awal_medis_ralan());
        BtnEdit.setEnabled(akses.getpenilaian_awal_medis_ralan());
        if(akses.getjml2()>=1){
            KdDokter.setEditable(false);
            BtnDokter.setEnabled(false);
            KdDokter.setText(akses.getkode());
            Sequel.cariIsi("select dokter.nm_dokter from dokter where dokter.kd_dokter=?", NmDokter,KdDokter.getText());
            if(NmDokter.getText().equals("")){
                KdDokter.setText("");
                JOptionPane.showMessageDialog(null,"User login bukan Dokter...!!");
            }
        }            
    }
    
    public void setTampil(){
       TabRawat.setSelectedIndex(1);
       tampil();
    }

    private void hapus() {
        if(Sequel.queryu2tf("delete from penilaian_medis_ralan_rehab_medik where no_rawat=?",1,new String[]{
            tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
        })==true){
            tampil();
            TabRawat.setSelectedIndex(1);
        }else{
            JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
        }
    }

    private void ganti() {
        String dls = Fisioterapi.getSelectedItem().toString();
        if(Sequel.mengedittf("penilaian_medis_ralan_rehab_medik","no_rawat=?","no_rawat=?,tanggal=?,kd_dokter=?,anamnesis=?,hubungan=?,keluhan_utama=?,rps=?,rpd=?,alergi=?,kesadaran=?,nyeri=?,skala_nyeri=?,td=?,nadi=?,suhu=?,rr=?,bb=?,"+
                "kepala=?,keterangan_kepala=?,thoraks=?,keterangan_thoraks=?,abdomen=?,keterangan_abdomen=?,ekstremitas=?,keterangan_ekstremitas=?,columna=?,keterangan_columna=?,muskulos=?,keterangan_muskulos=?,lainnya=?,resiko_jatuh=?,resiko_nutrisional=?,kebutuhan_fungsional=?,diagnosa_medis=?,diagnosa_fungsi=?,penunjang_lain=?,"+
                "fisio=?,okupasi=?,wicara=?,akupuntur=?,tatalain=?,frekuensi_terapi=?,fisioterapi=?,terapi_okupasi=?,terapi_wicara=?,terapi_akupuntur=?,terapi_lainnya=?,edukasi=?",49,new String[]{
                TNoRw.getText(),Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),KdDokter.getText(),Anamnesis.getSelectedItem().toString(),Hubungan.getText(),
                KeluhanUtama.getText(),RPS.getText(),RPD.getText(),Alergi.getText(),Kesadaran.getSelectedItem().toString(),Nyeri.getSelectedItem().toString(),SkalaNyeri.getSelectedItem().toString(),TD.getText(),Nadi.getText(),Suhu.getText(),RR.getText(),BB.getText(),
                Kepala.getSelectedItem().toString(),KeteranganKepala.getText(),Thoraks.getSelectedItem().toString(),KeteranganThoraks.getText(),Abdomen.getSelectedItem().toString(),KeteranganAbdomen.getText(),Ekstremitas.getSelectedItem().toString(),KeteranganEkstremitas.getText(),
                Columna.getSelectedItem().toString(),KeteranganColumna.getText(),Muskulos.getSelectedItem().toString(),KeteranganMusku.getText(),Lainnya.getText(),Resikojatuh.getSelectedItem().toString(),Resikonutrisional.getSelectedItem().toString(),Kebutuhanfungsional.getSelectedItem().toString(),DiagnosaMedis.getText(),DiagnosaFungsi.getText(),PenunjangLain.getText(),Fisio.getText(),Okupasi.getText(),Wicara.getText(),
                Akupuntur.getText(),Tatalainnya.getText(),Frekuensiterapi.getText(),Valid.SetTgl(Fisioterapi.getSelectedItem()+""),Valid.SetTgl(Terapiokupasi.getSelectedItem()+""),Valid.SetTgl(Terapiwicara.getSelectedItem()+""),Valid.SetTgl(Terapiakupuntur.getSelectedItem()+""),Valid.SetTgl(Terapilainnya.getSelectedItem()+""),Edukasi.getText(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
            })==true){
               tampil();
               emptTeks();
               TabRawat.setSelectedIndex(1);
        }
    }
}

/*
 * Kontribusi dari RSUD Prembun
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
            "Riwayat Alergi","Kesadaran","Nyeri","Skala Nyeri","TD(mmHg)","Nadi(x/menit)","Suhu(°C)","RR(x/menit)","BB(Kg)","Kepala","Keterangan Kepala","Thoraks","Keterangan Thoraks",
            "Abdomen","Keterangan Abdomen","Ekstremitas","Keterangan Ekstremitas","Columna Vertebralis","Keterangan Columna","Muskuloskeletal","Keterangan Muskuloskeletal","Lainnya",
            "Resiko Jatuh","Resiko Nutrisional","Kebutuhan Fungsional","Diagnosa Medis","Diagnosa Fungsi","Pemeriksaan Penunjang","Fisioterapi","Okupasi","Wicara","Akupuntur",
            "Tatalaksana Lainnya","Frekuensi Terapi","Terapi Fisio","Terapi Okupasi","Terapi Wicara","Terapi Akupuntur","Terapi Lainnya","Edukasi"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        
        tbObat.setModel(tabMode);
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 53; i++) {
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
                column.setPreferredWidth(250);
            }else if(i==11){
                column.setPreferredWidth(170);
            }else if(i==12){
                column.setPreferredWidth(170);
            }else if(i==13){
                column.setPreferredWidth(120);
            }else if(i==14){
                column.setPreferredWidth(80);
            }else if(i==15){
                column.setPreferredWidth(100);
            }else if(i==16){
                column.setPreferredWidth(62);
            }else if(i==17){
                column.setPreferredWidth(61);
            }else if(i==18){
                column.setPreferredWidth(75);
            }else if(i==19){
                column.setPreferredWidth(53);
            }else if(i==20){
                column.setPreferredWidth(68);
            }else if(i==21){
                column.setPreferredWidth(43);
            }else if(i==22){
                column.setPreferredWidth(80);
            }else if(i==23){
                column.setPreferredWidth(150);
            }else if(i==24){
                column.setPreferredWidth(80);
            }else if(i==25){
                column.setPreferredWidth(150);
            }else if(i==26){
                column.setPreferredWidth(80);
            }else if(i==27){
                column.setPreferredWidth(150);
            }else if(i==28){
                column.setPreferredWidth(80);
            }else if(i==29){
                column.setPreferredWidth(150);
            }else if(i==30){
                column.setPreferredWidth(110);
            }else if(i==31){
                column.setPreferredWidth(150);
            }else if(i==32){
                column.setPreferredWidth(87);
            }else if(i==33){
                column.setPreferredWidth(150);
            }else if(i==34){
                column.setPreferredWidth(250);
            }else if(i==35){
                column.setPreferredWidth(81);
            }else if(i==36){
                column.setPreferredWidth(126);
            }else if(i==37){
                column.setPreferredWidth(117);
            }else if(i==38){
                column.setPreferredWidth(200);
            }else if(i==39){
                column.setPreferredWidth(200);
            }else if(i==40){
                column.setPreferredWidth(200);
            }else if(i==41){
                column.setPreferredWidth(150);
            }else if(i==42){
                column.setPreferredWidth(150);
            }else if(i==43){
                column.setPreferredWidth(150);
            }else if(i==44){
                column.setPreferredWidth(150);
            }else if(i==45){
                column.setPreferredWidth(150);
            }else if(i==46){
                column.setPreferredWidth(90);
            }else if(i==47){
                column.setPreferredWidth(67);
            }else if(i==48){
                column.setPreferredWidth(81);
            }else if(i==49){
                column.setPreferredWidth(77);
            }else if(i==50){
                column.setPreferredWidth(93);
            }else if(i==51){
                column.setPreferredWidth(80);
            }else if(i==52){
                column.setPreferredWidth(250);
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
        DiagnosaMedis.setDocument(new batasInput((int)500).getKata(DiagnosaMedis));
        DiagnosaFungsi.setDocument(new batasInput((int)500).getKata(DiagnosaFungsi));
        PenunjangLain.setDocument(new batasInput((int)500).getKata(PenunjangLain));
        Fisio.setDocument(new batasInput((int)100).getKata(Fisio));
        Okupasi.setDocument(new batasInput((int)100).getKata(Okupasi));
        Wicara.setDocument(new batasInput((int)100).getKata(Wicara));
        Akupuntur.setDocument(new batasInput((int)100).getKata(Akupuntur));
        Tatalainnya.setDocument(new batasInput((int)100).getKata(Tatalainnya));
        Frekuensiterapi.setDocument(new batasInput((int)40).getKata(Frekuensiterapi));
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
        scrollPane7 = new widget.ScrollPane();
        RPS = new widget.TextArea();
        Alergi = new widget.TextBox();
        jLabel37 = new widget.Label();
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
        jLabel106 = new widget.Label();
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
        Frekuensiterapi = new widget.TextBox();
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
        TglFisioterapi = new widget.Tanggal();
        TglOkupasi = new widget.Tanggal();
        TglWicara = new widget.Tanggal();
        TglAkupuntur = new widget.Tanggal();
        TglTerapilainnya = new widget.Tanggal();
        jLabel33 = new widget.Label();
        jLabel36 = new widget.Label();
        jLabel41 = new widget.Label();
        jSeparator9 = new javax.swing.JSeparator();
        jSeparator13 = new javax.swing.JSeparator();
        jLabel42 = new widget.Label();
        jSeparator14 = new javax.swing.JSeparator();
        jSeparator15 = new javax.swing.JSeparator();
        ChkTerapiLainnya = new widget.CekBox();
        ChkFisioterapi = new widget.CekBox();
        ChkOkupasi = new widget.CekBox();
        ChkWicara = new widget.CekBox();
        ChkAkupungtur = new widget.CekBox();
        jSeparator16 = new javax.swing.JSeparator();
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
        MnPenilaianMedis.setText("Laporan Pengkajian Medis");
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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Pengkajian Awal Medis Ralan Kedokteran Fisik & Rehabilitasi ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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
        FormInput.setPreferredSize(new java.awt.Dimension(870, 913));
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
        jLabel12.setBounds(524, 240, 50, 23);

        BB.setFocusTraversalPolicyProvider(true);
        BB.setName("BB"); // NOI18N
        BB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BBKeyPressed(evt);
            }
        });
        FormInput.add(BB);
        BB.setBounds(578, 240, 50, 23);

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel13.setText("Kg");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(631, 240, 50, 23);

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel16.setText("x/menit");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput.add(jLabel16);
        jLabel16.setBounds(467, 240, 50, 23);

        Nadi.setFocusTraversalPolicyProvider(true);
        Nadi.setName("Nadi"); // NOI18N
        Nadi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NadiKeyPressed(evt);
            }
        });
        FormInput.add(Nadi);
        Nadi.setBounds(414, 240, 50, 23);

        jLabel17.setText("Nadi :");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(370, 240, 40, 23);

        jLabel18.setText("Suhu :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(370, 270, 40, 23);

        Suhu.setFocusTraversalPolicyProvider(true);
        Suhu.setName("Suhu"); // NOI18N
        Suhu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SuhuKeyPressed(evt);
            }
        });
        FormInput.add(Suhu);
        Suhu.setBounds(414, 270, 50, 23);

        jLabel22.setText("TD :");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(690, 240, 30, 23);

        TD.setFocusTraversalPolicyProvider(true);
        TD.setName("TD"); // NOI18N
        TD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TDKeyPressed(evt);
            }
        });
        FormInput.add(TD);
        TD.setBounds(724, 240, 80, 23);

        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel20.setText("°C");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(467, 270, 50, 23);

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel23.setText("mmHg");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(807, 240, 50, 23);

        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel25.setText("x/menit");
        jLabel25.setName("jLabel25"); // NOI18N
        FormInput.add(jLabel25);
        jLabel25.setBounds(631, 270, 50, 23);

        RR.setFocusTraversalPolicyProvider(true);
        RR.setName("RR"); // NOI18N
        RR.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RRKeyPressed(evt);
            }
        });
        FormInput.add(RR);
        RR.setBounds(578, 270, 50, 23);

        jLabel26.setText("RR :");
        jLabel26.setName("jLabel26"); // NOI18N
        FormInput.add(jLabel26);
        jLabel26.setBounds(524, 270, 50, 23);

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
        jLabel94.setBounds(10, 300, 180, 23);

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
        jSeparator12.setBounds(0, 190, 880, 1);

        jLabel39.setText("Kesadaran :");
        jLabel39.setName("jLabel39"); // NOI18N
        FormInput.add(jLabel39);
        jLabel39.setBounds(650, 210, 70, 23);

        Kesadaran.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Compos Mentis", "Apatis", "Somnolen", "Sopor", "Koma" }));
        Kesadaran.setName("Kesadaran"); // NOI18N
        Kesadaran.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KesadaranKeyPressed(evt);
            }
        });
        FormInput.add(Kesadaran);
        Kesadaran.setBounds(724, 210, 130, 23);

        jLabel44.setText("Muskuloskeletal :");
        jLabel44.setName("jLabel44"); // NOI18N
        FormInput.add(jLabel44);
        jLabel44.setBounds(417, 380, 127, 23);

        Muskulos.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Muskulos.setSelectedIndex(2);
        Muskulos.setName("Muskulos"); // NOI18N
        Muskulos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MuskulosKeyPressed(evt);
            }
        });
        FormInput.add(Muskulos);
        Muskulos.setBounds(548, 380, 128, 23);

        jLabel50.setText("Columna Vertebralis :");
        jLabel50.setName("jLabel50"); // NOI18N
        FormInput.add(jLabel50);
        jLabel50.setBounds(417, 350, 127, 23);

        Columna.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Columna.setSelectedIndex(2);
        Columna.setName("Columna"); // NOI18N
        Columna.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ColumnaKeyPressed(evt);
            }
        });
        FormInput.add(Columna);
        Columna.setBounds(548, 350, 128, 23);

        jLabel99.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel99.setText("I. RIWAYAT KESEHATAN");
        jLabel99.setName("jLabel99"); // NOI18N
        FormInput.add(jLabel99);
        jLabel99.setBounds(10, 70, 180, 23);

        jLabel101.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel101.setText("IV. PEMERIKSAAN FISIK DAN UJI FUNGSI");
        jLabel101.setName("jLabel101"); // NOI18N
        FormInput.add(jLabel101);
        jLabel101.setBounds(10, 510, 240, 23);

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label11);
        label11.setBounds(380, 40, 52, 23);

        TglAsuhan.setForeground(new java.awt.Color(50, 70, 50));
        TglAsuhan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "30-09-2025 15:11:40" }));
        TglAsuhan.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        TglAsuhan.setName("TglAsuhan"); // NOI18N
        TglAsuhan.setOpaque(false);
        TglAsuhan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglAsuhanKeyPressed(evt);
            }
        });
        FormInput.add(TglAsuhan);
        TglAsuhan.setBounds(436, 40, 130, 23);

        Nyeri.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Nyeri", "Nyeri Sedang", "Nyeri Sangat Hebat" }));
        Nyeri.setName("Nyeri"); // NOI18N
        Nyeri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NyeriKeyPressed(evt);
            }
        });
        FormInput.add(Nyeri);
        Nyeri.setBounds(375, 210, 145, 23);

        jLabel95.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel95.setText("II. PEMERIKSAAN FISIK");
        jLabel95.setName("jLabel95"); // NOI18N
        FormInput.add(jLabel95);
        jLabel95.setBounds(10, 190, 180, 23);

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
        scrollPane1.setBounds(129, 90, 310, 63);

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
        Alergi.setBounds(129, 160, 310, 23);

        jLabel37.setText("Riwayat Alergi :");
        jLabel37.setName("jLabel37"); // NOI18N
        FormInput.add(jLabel37);
        jLabel37.setBounds(0, 160, 125, 23);

        jLabel40.setText("Kepala :");
        jLabel40.setName("jLabel40"); // NOI18N
        FormInput.add(jLabel40);
        jLabel40.setBounds(0, 320, 98, 23);

        Kepala.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Kepala.setSelectedIndex(2);
        Kepala.setName("Kepala"); // NOI18N
        Kepala.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KepalaKeyPressed(evt);
            }
        });
        FormInput.add(Kepala);
        Kepala.setBounds(102, 320, 128, 23);

        jLabel47.setText("Lainnya :");
        jLabel47.setName("jLabel47"); // NOI18N
        FormInput.add(jLabel47);
        jLabel47.setBounds(0, 410, 98, 23);

        jLabel46.setText("Thoraks :");
        jLabel46.setName("jLabel46"); // NOI18N
        FormInput.add(jLabel46);
        jLabel46.setBounds(0, 350, 98, 23);

        Thoraks.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Thoraks.setSelectedIndex(2);
        Thoraks.setName("Thoraks"); // NOI18N
        Thoraks.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ThoraksKeyPressed(evt);
            }
        });
        FormInput.add(Thoraks);
        Thoraks.setBounds(102, 350, 128, 23);

        jLabel49.setText("Abdomen :");
        jLabel49.setName("jLabel49"); // NOI18N
        FormInput.add(jLabel49);
        jLabel49.setBounds(0, 380, 98, 23);

        Abdomen.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Abdomen.setSelectedIndex(2);
        Abdomen.setName("Abdomen"); // NOI18N
        Abdomen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AbdomenKeyPressed(evt);
            }
        });
        FormInput.add(Abdomen);
        Abdomen.setBounds(102, 380, 128, 23);

        jLabel51.setText("Ekstremitas :");
        jLabel51.setName("jLabel51"); // NOI18N
        FormInput.add(jLabel51);
        jLabel51.setBounds(417, 320, 127, 23);

        Ekstremitas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Ekstremitas.setSelectedIndex(2);
        Ekstremitas.setName("Ekstremitas"); // NOI18N
        Ekstremitas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EkstremitasKeyPressed(evt);
            }
        });
        FormInput.add(Ekstremitas);
        Ekstremitas.setBounds(548, 320, 128, 23);

        KeteranganKepala.setFocusTraversalPolicyProvider(true);
        KeteranganKepala.setName("KeteranganKepala"); // NOI18N
        KeteranganKepala.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKepalaKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganKepala);
        KeteranganKepala.setBounds(233, 320, 175, 23);

        KeteranganThoraks.setFocusTraversalPolicyProvider(true);
        KeteranganThoraks.setName("KeteranganThoraks"); // NOI18N
        KeteranganThoraks.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganThoraksKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganThoraks);
        KeteranganThoraks.setBounds(233, 350, 175, 23);

        KeteranganMusku.setFocusTraversalPolicyProvider(true);
        KeteranganMusku.setName("KeteranganMusku"); // NOI18N
        KeteranganMusku.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganMuskuKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganMusku);
        KeteranganMusku.setBounds(679, 380, 175, 23);

        KeteranganAbdomen.setFocusTraversalPolicyProvider(true);
        KeteranganAbdomen.setName("KeteranganAbdomen"); // NOI18N
        KeteranganAbdomen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganAbdomenKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganAbdomen);
        KeteranganAbdomen.setBounds(233, 380, 175, 23);

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
        scrollPane5.setBounds(102, 410, 752, 63);

        KeteranganEkstremitas.setFocusTraversalPolicyProvider(true);
        KeteranganEkstremitas.setName("KeteranganEkstremitas"); // NOI18N
        KeteranganEkstremitas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganEkstremitasKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganEkstremitas);
        KeteranganEkstremitas.setBounds(679, 320, 175, 23);

        KeteranganColumna.setFocusTraversalPolicyProvider(true);
        KeteranganColumna.setName("KeteranganColumna"); // NOI18N
        KeteranganColumna.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganColumnaKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganColumna);
        KeteranganColumna.setBounds(679, 350, 175, 23);

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
        scrollPane12.setBounds(594, 550, 260, 63);

        jLabel105.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel105.setText("Pemeriksaan Penunjang :");
        jLabel105.setName("jLabel105"); // NOI18N
        FormInput.add(jLabel105);
        jLabel105.setBounds(594, 530, 190, 23);

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
        scrollPane9.setBounds(44, 550, 260, 63);

        jLabel80.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel80.setText("Diagnosa Medis :");
        jLabel80.setName("jLabel80"); // NOI18N
        FormInput.add(jLabel80);
        jLabel80.setBounds(44, 530, 150, 23);

        jLabel81.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel81.setText("Diagnosa Fungsi :");
        jLabel81.setName("jLabel81"); // NOI18N
        FormInput.add(jLabel81);
        jLabel81.setBounds(319, 530, 150, 23);

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
        scrollPane10.setBounds(319, 550, 260, 63);

        jLabel106.setText("Frekuensi Terapi :");
        jLabel106.setName("jLabel106"); // NOI18N
        FormInput.add(jLabel106);
        jLabel106.setBounds(0, 790, 135, 23);

        jLabel109.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel109.setText("VI. EDUKASI");
        jLabel109.setName("jLabel109"); // NOI18N
        FormInput.add(jLabel109);
        jLabel109.setBounds(10, 820, 190, 23);

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
        scrollPane14.setBounds(44, 840, 810, 63);

        Fisio.setFocusTraversalPolicyProvider(true);
        Fisio.setName("Fisio"); // NOI18N
        Fisio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FisioKeyPressed(evt);
            }
        });
        FormInput.add(Fisio);
        Fisio.setBounds(139, 640, 597, 23);

        Okupasi.setFocusTraversalPolicyProvider(true);
        Okupasi.setName("Okupasi"); // NOI18N
        Okupasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                OkupasiKeyPressed(evt);
            }
        });
        FormInput.add(Okupasi);
        Okupasi.setBounds(139, 670, 597, 23);

        Wicara.setFocusTraversalPolicyProvider(true);
        Wicara.setName("Wicara"); // NOI18N
        Wicara.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                WicaraKeyPressed(evt);
            }
        });
        FormInput.add(Wicara);
        Wicara.setBounds(139, 700, 597, 23);

        Akupuntur.setFocusTraversalPolicyProvider(true);
        Akupuntur.setName("Akupuntur"); // NOI18N
        Akupuntur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AkupunturKeyPressed(evt);
            }
        });
        FormInput.add(Akupuntur);
        Akupuntur.setBounds(139, 730, 597, 23);

        PanelWall1.setBackground(new java.awt.Color(255, 255, 255));
        PanelWall1.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/picture/nyeri.png"))); // NOI18N
        PanelWall1.setBackgroundImageType(usu.widget.constan.BackgroundConstan.BACKGROUND_IMAGE_STRECT);
        PanelWall1.setOpaque(true);
        PanelWall1.setPreferredSize(new java.awt.Dimension(200, 200));
        PanelWall1.setRound(false);
        PanelWall1.setWarna(new java.awt.Color(110, 110, 110));
        PanelWall1.setLayout(null);
        FormInput.add(PanelWall1);
        PanelWall1.setBounds(44, 210, 320, 83);

        SkalaNyeri.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));
        SkalaNyeri.setName("SkalaNyeri"); // NOI18N
        SkalaNyeri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkalaNyeriKeyPressed(evt);
            }
        });
        FormInput.add(SkalaNyeri);
        SkalaNyeri.setBounds(578, 210, 65, 23);

        Tatalainnya.setFocusTraversalPolicyProvider(true);
        Tatalainnya.setName("Tatalainnya"); // NOI18N
        Tatalainnya.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TatalainnyaKeyPressed(evt);
            }
        });
        FormInput.add(Tatalainnya);
        Tatalainnya.setBounds(139, 760, 597, 23);

        Frekuensiterapi.setFocusTraversalPolicyProvider(true);
        Frekuensiterapi.setName("Frekuensiterapi"); // NOI18N
        Frekuensiterapi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FrekuensiterapiKeyPressed(evt);
            }
        });
        FormInput.add(Frekuensiterapi);
        Frekuensiterapi.setBounds(139, 790, 340, 23);

        Resikojatuh.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Berisiko", "Berisiko Sedang", "Berisiko Tinggi" }));
        Resikojatuh.setName("Resikojatuh"); // NOI18N
        Resikojatuh.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ResikojatuhKeyPressed(evt);
            }
        });
        FormInput.add(Resikojatuh);
        Resikojatuh.setBounds(117, 480, 130, 23);

        Resikonutrisional.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Berisiko Malnutrisi", "Berisiko Malnutrisi", "Malnutrisi" }));
        Resikonutrisional.setName("Resikonutrisional"); // NOI18N
        Resikonutrisional.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ResikonutrisionalKeyPressed(evt);
            }
        });
        FormInput.add(Resikonutrisional);
        Resikonutrisional.setBounds(384, 480, 170, 23);

        jLabel34.setText("Resiko Nutrisional :");
        jLabel34.setName("jLabel34"); // NOI18N
        FormInput.add(jLabel34);
        jLabel34.setBounds(280, 480, 100, 23);

        jLabel35.setText("Kebutuhan Fungsional :");
        jLabel35.setName("jLabel35"); // NOI18N
        FormInput.add(jLabel35);
        jLabel35.setBounds(570, 480, 130, 23);

        Kebutuhanfungsional.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Perlu Bantuan", "Perlu Bantuan", "Perlu Bantuan Total" }));
        Kebutuhanfungsional.setName("Kebutuhanfungsional"); // NOI18N
        Kebutuhanfungsional.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KebutuhanfungsionalKeyPressed(evt);
            }
        });
        FormInput.add(Kebutuhanfungsional);
        Kebutuhanfungsional.setBounds(704, 480, 150, 23);

        jLabel107.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel107.setText("V. TATALAKSANA KFR");
        jLabel107.setName("jLabel107"); // NOI18N
        FormInput.add(jLabel107);
        jLabel107.setBounds(10, 620, 180, 23);

        jLabel108.setText("Fisioterapi :");
        jLabel108.setName("jLabel108"); // NOI18N
        FormInput.add(jLabel108);
        jLabel108.setBounds(0, 640, 135, 23);

        jLabel110.setText("Terapi Okupasi :");
        jLabel110.setName("jLabel110"); // NOI18N
        FormInput.add(jLabel110);
        jLabel110.setBounds(0, 670, 135, 23);

        jLabel111.setText("Terapi Wicara :");
        jLabel111.setName("jLabel111"); // NOI18N
        FormInput.add(jLabel111);
        jLabel111.setBounds(0, 700, 135, 23);

        jLabel112.setText("Terapi Akupuntur :");
        jLabel112.setName("jLabel112"); // NOI18N
        FormInput.add(jLabel112);
        jLabel112.setBounds(0, 730, 135, 23);

        jLabel113.setText("Terapi lainnya :");
        jLabel113.setName("jLabel113"); // NOI18N
        FormInput.add(jLabel113);
        jLabel113.setBounds(0, 760, 135, 23);

        TglFisioterapi.setForeground(new java.awt.Color(50, 70, 50));
        TglFisioterapi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "30-09-2025" }));
        TglFisioterapi.setDisplayFormat("dd-MM-yyyy");
        TglFisioterapi.setEnabled(false);
        TglFisioterapi.setName("TglFisioterapi"); // NOI18N
        TglFisioterapi.setOpaque(false);
        FormInput.add(TglFisioterapi);
        TglFisioterapi.setBounds(764, 640, 90, 23);

        TglOkupasi.setForeground(new java.awt.Color(50, 70, 50));
        TglOkupasi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "30-09-2025" }));
        TglOkupasi.setDisplayFormat("dd-MM-yyyy");
        TglOkupasi.setEnabled(false);
        TglOkupasi.setName("TglOkupasi"); // NOI18N
        TglOkupasi.setOpaque(false);
        FormInput.add(TglOkupasi);
        TglOkupasi.setBounds(764, 670, 90, 23);

        TglWicara.setForeground(new java.awt.Color(50, 70, 50));
        TglWicara.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "30-09-2025" }));
        TglWicara.setDisplayFormat("dd-MM-yyyy");
        TglWicara.setEnabled(false);
        TglWicara.setName("TglWicara"); // NOI18N
        TglWicara.setOpaque(false);
        FormInput.add(TglWicara);
        TglWicara.setBounds(764, 700, 90, 23);

        TglAkupuntur.setForeground(new java.awt.Color(50, 70, 50));
        TglAkupuntur.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "30-09-2025" }));
        TglAkupuntur.setDisplayFormat("dd-MM-yyyy");
        TglAkupuntur.setEnabled(false);
        TglAkupuntur.setName("TglAkupuntur"); // NOI18N
        TglAkupuntur.setOpaque(false);
        FormInput.add(TglAkupuntur);
        TglAkupuntur.setBounds(764, 730, 90, 23);

        TglTerapilainnya.setForeground(new java.awt.Color(50, 70, 50));
        TglTerapilainnya.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "30-09-2025" }));
        TglTerapilainnya.setDisplayFormat("dd-MM-yyyy");
        TglTerapilainnya.setEnabled(false);
        TglTerapilainnya.setName("TglTerapilainnya"); // NOI18N
        TglTerapilainnya.setOpaque(false);
        FormInput.add(TglTerapilainnya);
        TglTerapilainnya.setBounds(764, 760, 90, 23);

        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel33.setText("Keluhan Utama");
        jLabel33.setName("jLabel33"); // NOI18N
        FormInput.add(jLabel33);
        jLabel33.setBounds(44, 90, 90, 23);

        jLabel36.setText(":");
        jLabel36.setName("jLabel36"); // NOI18N
        FormInput.add(jLabel36);
        jLabel36.setBounds(0, 90, 125, 23);

        jLabel41.setText("Skala :");
        jLabel41.setName("jLabel41"); // NOI18N
        FormInput.add(jLabel41);
        jLabel41.setBounds(524, 210, 50, 23);

        jSeparator9.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator9.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator9.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator9.setName("jSeparator9"); // NOI18N
        FormInput.add(jSeparator9);
        jSeparator9.setBounds(365, 210, 1, 90);

        jSeparator13.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator13.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator13.setName("jSeparator13"); // NOI18N
        FormInput.add(jSeparator13);
        jSeparator13.setBounds(0, 300, 880, 1);

        jLabel42.setText("Risiko Jatuh :");
        jLabel42.setName("jLabel42"); // NOI18N
        FormInput.add(jLabel42);
        jLabel42.setBounds(0, 480, 113, 23);

        jSeparator14.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator14.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator14.setName("jSeparator14"); // NOI18N
        FormInput.add(jSeparator14);
        jSeparator14.setBounds(0, 510, 880, 1);

        jSeparator15.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator15.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator15.setName("jSeparator15"); // NOI18N
        FormInput.add(jSeparator15);
        jSeparator15.setBounds(0, 620, 880, 1);

        ChkTerapiLainnya.setBorder(null);
        ChkTerapiLainnya.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkTerapiLainnya.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkTerapiLainnya.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkTerapiLainnya.setName("ChkTerapiLainnya"); // NOI18N
        ChkTerapiLainnya.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ChkTerapiLainnyaItemStateChanged(evt);
            }
        });
        FormInput.add(ChkTerapiLainnya);
        ChkTerapiLainnya.setBounds(738, 760, 23, 23);

        ChkFisioterapi.setBorder(null);
        ChkFisioterapi.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkFisioterapi.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkFisioterapi.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkFisioterapi.setName("ChkFisioterapi"); // NOI18N
        ChkFisioterapi.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ChkFisioterapiItemStateChanged(evt);
            }
        });
        FormInput.add(ChkFisioterapi);
        ChkFisioterapi.setBounds(738, 640, 23, 23);

        ChkOkupasi.setBorder(null);
        ChkOkupasi.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkOkupasi.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkOkupasi.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkOkupasi.setName("ChkOkupasi"); // NOI18N
        ChkOkupasi.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ChkOkupasiItemStateChanged(evt);
            }
        });
        FormInput.add(ChkOkupasi);
        ChkOkupasi.setBounds(738, 670, 23, 23);

        ChkWicara.setBorder(null);
        ChkWicara.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkWicara.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkWicara.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkWicara.setName("ChkWicara"); // NOI18N
        ChkWicara.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ChkWicaraItemStateChanged(evt);
            }
        });
        FormInput.add(ChkWicara);
        ChkWicara.setBounds(738, 700, 23, 23);

        ChkAkupungtur.setBorder(null);
        ChkAkupungtur.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkAkupungtur.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkAkupungtur.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkAkupungtur.setName("ChkAkupungtur"); // NOI18N
        ChkAkupungtur.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ChkAkupungturItemStateChanged(evt);
            }
        });
        FormInput.add(ChkAkupungtur);
        ChkAkupungtur.setBounds(738, 730, 23, 23);

        jSeparator16.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator16.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator16.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator16.setName("jSeparator16"); // NOI18N
        FormInput.add(jSeparator16);
        jSeparator16.setBounds(0, 820, 880, 1);

        scrollInput.setViewportView(FormInput);

        internalFrame2.add(scrollInput, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Input Pengkajian", internalFrame2);

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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "30-09-2025" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "30-09-2025" }));
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

        TabRawat.addTab("Data Pengkajian", internalFrame3);

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
            if(Sequel.menyimpantf("penilaian_medis_ralan_rehab_medik","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat",48,new String[]{
                    TNoRw.getText(),Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),KdDokter.getText(),Anamnesis.getSelectedItem().toString(),Hubungan.getText(),
                    KeluhanUtama.getText(),RPS.getText(),RPD.getText(),Alergi.getText(),Kesadaran.getSelectedItem().toString(),Nyeri.getSelectedItem().toString(),SkalaNyeri.getSelectedItem().toString(),TD.getText(),
                    Nadi.getText(),Suhu.getText(),RR.getText(),BB.getText(),Kepala.getSelectedItem().toString(),KeteranganKepala.getText(),Thoraks.getSelectedItem().toString(),KeteranganThoraks.getText(),
                    Abdomen.getSelectedItem().toString(),KeteranganAbdomen.getText(),Ekstremitas.getSelectedItem().toString(),KeteranganEkstremitas.getText(),Columna.getSelectedItem().toString(),KeteranganColumna.getText(),
                    Muskulos.getSelectedItem().toString(),KeteranganMusku.getText(),Lainnya.getText(),Resikojatuh.getSelectedItem().toString(),Resikonutrisional.getSelectedItem().toString(),Kebutuhanfungsional.getSelectedItem().toString(),
                    DiagnosaMedis.getText(),DiagnosaFungsi.getText(),PenunjangLain.getText(),Fisio.getText(),Okupasi.getText(),Wicara.getText(),Akupuntur.getText(),Tatalainnya.getText(),Frekuensiterapi.getText(),
                    (ChkFisioterapi.isSelected()==true?Valid.SetTgl(TglFisioterapi.getSelectedItem()+""):"0000-00-00"),(ChkOkupasi.isSelected()==true?Valid.SetTgl(TglOkupasi.getSelectedItem()+""):"0000-00-00"),
                    (ChkWicara.isSelected()==true?Valid.SetTgl(TglWicara.getSelectedItem()+""):"0000-00-00"),(ChkAkupungtur.isSelected()==true?Valid.SetTgl(TglAkupuntur.getSelectedItem()+""):"0000-00-00"),
                    (ChkTerapiLainnya.isSelected()==true?Valid.SetTgl(TglTerapilainnya.getSelectedItem()+""):"0000-00-00"),Edukasi.getText()
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
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Suhu(°C)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>RR(x/menit)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>BB(Kg)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Kepala</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Keterangan Kepala</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Thoraks</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Keterangan Thoraks</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Abdomen</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Keterangan Abdomen</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Ekstremitas</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Keterangan Ekstremitas</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Columna Vertebralis</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Keterangan Columna</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Muskuloskeletal</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Keterangan Muskuloskeletal</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Lainnya</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Resiko Jatuh</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Resiko Nutrisional</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Kebutuhan Fungsional</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Diagnosa Medis</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Diagnosa Fungsi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Pemeriksaan Penunjang</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Fisioterapi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Okupasi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Wicara</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Akupuntur</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Tatalaksana Lainnya</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Frekuensi Terapi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Terapi Fisio</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Terapi Okupasi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Terapi Wicara</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Terapi Akupuntur</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Terapi Lainnya</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Edukasi</b></td>"+
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
                            "<td valign='top'>"+tbObat.getValueAt(i,35).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,36).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,37).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,38).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,39).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,40).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,41).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,42).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,43).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,44).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,45).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,46).toString()+"</td>"+
                            "<td valign='top'>"+(tbObat.getValueAt(i,47)==null?"":tbObat.getValueAt(i,47).toString())+"</td>"+
                            "<td valign='top'>"+(tbObat.getValueAt(i,48)==null?"":tbObat.getValueAt(i,48).toString())+"</td>"+
                            "<td valign='top'>"+(tbObat.getValueAt(i,49)==null?"":tbObat.getValueAt(i,49).toString())+"</td>"+
                            "<td valign='top'>"+(tbObat.getValueAt(i,50)==null?"":tbObat.getValueAt(i,50).toString())+"</td>"+
                            "<td valign='top'>"+(tbObat.getValueAt(i,51)==null?"":tbObat.getValueAt(i,51).toString())+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,52).toString()+"</td>"+
                        "</tr>");
                }
                LoadHTML.setText(
                    "<html>"+
                      "<table width='4500px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
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

                File f = new File("PenilaianAwalMedisKedokteranFisik.html");            
                BufferedWriter bw = new BufferedWriter(new FileWriter(f));            
                bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                            "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                            "<table width='4500px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                "<tr class='isi2'>"+
                                    "<td valign='top' align='center'>"+
                                        "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                        akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                        akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                        "<font size='2' face='Tahoma'>DATA PENGKAJIAN AWAL MEDIS KEDOKTERAN FISIK<br><br></font>"+        
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
        Valid.pindah(evt,Nadi,TD);
    }//GEN-LAST:event_BBKeyPressed

    private void NadiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NadiKeyPressed
        Valid.pindah(evt,Kesadaran,BB);
    }//GEN-LAST:event_NadiKeyPressed

    private void SuhuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SuhuKeyPressed
        Valid.pindah(evt,TD,RR);
    }//GEN-LAST:event_SuhuKeyPressed

    private void TDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TDKeyPressed
        Valid.pindah(evt,BB,Suhu);
    }//GEN-LAST:event_TDKeyPressed

    private void RRKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RRKeyPressed
        Valid.pindah(evt,Suhu,Kepala);
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
        Valid.pindah(evt,SkalaNyeri,Nadi);
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
            Valid.MyReportqry("rptCetakPenilaianAwalMedisRalanRehabMedik.jasper","report","::[ Laporan Pengkajian Awal Medis Rawat Jalan Kedokteran Fisik dan Rehabilitasi ]::",
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
        Valid.pindah2(evt,Alergi,Nyeri);
    }//GEN-LAST:event_RPDKeyPressed

    private void RPSKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RPSKeyPressed
        Valid.pindah2(evt,KeluhanUtama,Alergi);
    }//GEN-LAST:event_RPSKeyPressed

    private void AlergiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlergiKeyPressed
        Valid.pindah(evt,RPS,RPD);
    }//GEN-LAST:event_AlergiKeyPressed

    private void KepalaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KepalaKeyPressed
        Valid.pindah(evt,RR,KeteranganKepala);
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
        Valid.pindah2(evt,Frekuensiterapi,BtnSimpan);
    }//GEN-LAST:event_EdukasiKeyPressed

    private void NyeriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NyeriKeyPressed
        Valid.pindah(evt,RPD,SkalaNyeri);
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
        Valid.pindah(evt,Nyeri,Kesadaran);
    }//GEN-LAST:event_SkalaNyeriKeyPressed

    private void TatalainnyaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TatalainnyaKeyPressed
        Valid.pindah(evt,Akupuntur,Frekuensiterapi);
    }//GEN-LAST:event_TatalainnyaKeyPressed

    private void FrekuensiterapiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FrekuensiterapiKeyPressed
        Valid.pindah(evt,Tatalainnya,Edukasi);
    }//GEN-LAST:event_FrekuensiterapiKeyPressed

    private void ResikojatuhKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ResikojatuhKeyPressed
        Valid.pindah(evt,Lainnya,Resikonutrisional);
    }//GEN-LAST:event_ResikojatuhKeyPressed

    private void ResikonutrisionalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ResikonutrisionalKeyPressed
        Valid.pindah(evt,Resikojatuh,Kebutuhanfungsional);
    }//GEN-LAST:event_ResikonutrisionalKeyPressed

    private void KebutuhanfungsionalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KebutuhanfungsionalKeyPressed
        Valid.pindah(evt,Resikonutrisional,DiagnosaMedis);
    }//GEN-LAST:event_KebutuhanfungsionalKeyPressed

    private void ChkTerapiLainnyaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ChkTerapiLainnyaItemStateChanged
        if(ChkTerapiLainnya.isSelected()==true){
            TglTerapilainnya.setEnabled(true);
        }else{
            TglTerapilainnya.setEnabled(false);
        }
    }//GEN-LAST:event_ChkTerapiLainnyaItemStateChanged

    private void ChkFisioterapiItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ChkFisioterapiItemStateChanged
        if(ChkFisioterapi.isSelected()==true){
            TglFisioterapi.setEnabled(true);
        }else{
            TglFisioterapi.setEnabled(false);
        }
    }//GEN-LAST:event_ChkFisioterapiItemStateChanged

    private void ChkOkupasiItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ChkOkupasiItemStateChanged
        if(ChkOkupasi.isSelected()==true){
            TglOkupasi.setEnabled(true);
        }else{
            TglOkupasi.setEnabled(false);
        }
    }//GEN-LAST:event_ChkOkupasiItemStateChanged

    private void ChkWicaraItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ChkWicaraItemStateChanged
        if(ChkWicara.isSelected()==true){
            TglWicara.setEnabled(true);
        }else{
            TglWicara.setEnabled(false);
        }
    }//GEN-LAST:event_ChkWicaraItemStateChanged

    private void ChkAkupungturItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ChkAkupungturItemStateChanged
        if(ChkAkupungtur.isSelected()==true){
            TglAkupuntur.setEnabled(true);
        }else{
            TglAkupuntur.setEnabled(false);
        }
    }//GEN-LAST:event_ChkAkupungturItemStateChanged

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
    private widget.CekBox ChkAkupungtur;
    private widget.CekBox ChkFisioterapi;
    private widget.CekBox ChkOkupasi;
    private widget.CekBox ChkTerapiLainnya;
    private widget.CekBox ChkWicara;
    private widget.ComboBox Columna;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.TextArea DiagnosaFungsi;
    private widget.TextArea DiagnosaMedis;
    private widget.TextArea Edukasi;
    private widget.ComboBox Ekstremitas;
    private widget.TextBox Fisio;
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
    private widget.Tanggal TglAkupuntur;
    private widget.Tanggal TglAsuhan;
    private widget.Tanggal TglFisioterapi;
    private widget.TextBox TglLahir;
    private widget.Tanggal TglOkupasi;
    private widget.Tanggal TglTerapilainnya;
    private widget.Tanggal TglWicara;
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
    private widget.Label jLabel30;
    private widget.Label jLabel31;
    private widget.Label jLabel33;
    private widget.Label jLabel34;
    private widget.Label jLabel35;
    private widget.Label jLabel36;
    private widget.Label jLabel37;
    private widget.Label jLabel38;
    private widget.Label jLabel39;
    private widget.Label jLabel40;
    private widget.Label jLabel41;
    private widget.Label jLabel42;
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
    private widget.Label jLabel94;
    private widget.Label jLabel95;
    private widget.Label jLabel99;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator15;
    private javax.swing.JSeparator jSeparator16;
    private javax.swing.JSeparator jSeparator9;
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
                    tabMode.addRow(new Object[]{
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getDate("tgl_lahir"),rs.getString("jk"),rs.getString("kd_dokter"),rs.getString("nm_dokter"),rs.getString("tanggal"),
                        rs.getString("anamnesis"),rs.getString("hubungan"),rs.getString("keluhan_utama"),rs.getString("rps"),rs.getString("rpd"),rs.getString("alergi"),rs.getString("kesadaran"),rs.getString("nyeri"),
                        rs.getString("skala_nyeri"),rs.getString("td"),rs.getString("nadi"),rs.getString("suhu"),rs.getString("rr"),rs.getString("bb"),rs.getString("kepala"),rs.getString("keterangan_kepala"),
                        rs.getString("thoraks"),rs.getString("keterangan_thoraks"),rs.getString("abdomen"),rs.getString("keterangan_abdomen"),rs.getString("ekstremitas"),rs.getString("keterangan_ekstremitas"),
                        rs.getString("columna"),rs.getString("keterangan_columna"),rs.getString("muskulos"),rs.getString("keterangan_muskulos"),rs.getString("lainnya"),rs.getString("resiko_jatuh"),rs.getString("resiko_nutrisional"),
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
        Kepala.setSelectedIndex(2);
        Thoraks.setSelectedIndex(2);
        Abdomen.setSelectedIndex(2);
        Ekstremitas.setSelectedIndex(2);
        Columna.setSelectedIndex(2);
        Muskulos.setSelectedIndex(2);
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
        ChkFisioterapi.setSelected(false);
        ChkOkupasi.setSelected(false);
        ChkWicara.setSelected(false);
        ChkAkupungtur.setSelected(false);
        ChkTerapiLainnya.setSelected(false);
        TglFisioterapi.setDate(new Date());
        TglOkupasi.setDate(new Date());
        TglWicara.setDate(new Date());
        TglAkupuntur.setDate(new Date());
        TglTerapilainnya.setDate(new Date());
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
            Kepala.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString());
            KeteranganKepala.setText(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString());
            Thoraks.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),24).toString());
            KeteranganThoraks.setText(tbObat.getValueAt(tbObat.getSelectedRow(),25).toString());
            Abdomen.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),26).toString());
            KeteranganAbdomen.setText(tbObat.getValueAt(tbObat.getSelectedRow(),27).toString());
            Ekstremitas.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),28).toString());
            KeteranganEkstremitas.setText(tbObat.getValueAt(tbObat.getSelectedRow(),29).toString());
            Columna.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),30).toString());
            KeteranganColumna.setText(tbObat.getValueAt(tbObat.getSelectedRow(),31).toString());
            Muskulos.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),32).toString().contains("Normal"));
            KeteranganMusku.setText(tbObat.getValueAt(tbObat.getSelectedRow(),33).toString());
            Lainnya.setText(tbObat.getValueAt(tbObat.getSelectedRow(),34).toString());
            Resikojatuh.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),35).toString());
            Resikonutrisional.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),36).toString());
            Kebutuhanfungsional.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),37).toString());
            DiagnosaMedis.setText(tbObat.getValueAt(tbObat.getSelectedRow(),38).toString());
            DiagnosaFungsi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),39).toString());
            PenunjangLain.setText(tbObat.getValueAt(tbObat.getSelectedRow(),40).toString());
            Fisio.setText(tbObat.getValueAt(tbObat.getSelectedRow(),41).toString());
            Okupasi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),42).toString());
            Wicara.setText(tbObat.getValueAt(tbObat.getSelectedRow(),43).toString());
            Akupuntur.setText(tbObat.getValueAt(tbObat.getSelectedRow(),44).toString());
            Tatalainnya.setText(tbObat.getValueAt(tbObat.getSelectedRow(),45).toString());
            Frekuensiterapi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),46).toString());
            try {
                ChkFisioterapi.setSelected(true);
                Valid.SetTgl(TglFisioterapi,tbObat.getValueAt(tbObat.getSelectedRow(),47).toString());
            } catch (Exception e) {
                ChkFisioterapi.setSelected(false);
            }
            try {
                ChkOkupasi.setSelected(true);
                Valid.SetTgl(TglOkupasi,tbObat.getValueAt(tbObat.getSelectedRow(),48).toString());
            } catch (Exception e) {
                ChkOkupasi.setSelected(false);
            }
            try {
                ChkWicara.setSelected(true);
                Valid.SetTgl(TglWicara,tbObat.getValueAt(tbObat.getSelectedRow(),49).toString());
            } catch (Exception e) {
                ChkWicara.setSelected(false);
            }
            try {
                ChkAkupungtur.setSelected(true);
                Valid.SetTgl(TglAkupuntur,tbObat.getValueAt(tbObat.getSelectedRow(),50).toString());
            } catch (Exception e) {
                ChkAkupungtur.setSelected(false);
            }
            try {
                ChkTerapiLainnya.setSelected(true);
                Valid.SetTgl(TglTerapilainnya,tbObat.getValueAt(tbObat.getSelectedRow(),51).toString());
            } catch (Exception e) {
                ChkTerapiLainnya.setSelected(false);
            }
            Edukasi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),52).toString());
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
        BtnSimpan.setEnabled(akses.getpenilaian_medis_ralan_rehab_medik());
        BtnHapus.setEnabled(akses.getpenilaian_medis_ralan_rehab_medik());
        BtnEdit.setEnabled(akses.getpenilaian_medis_ralan_rehab_medik());
        BtnEdit.setEnabled(akses.getpenilaian_medis_ralan_rehab_medik());
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
            tabMode.removeRow(tbObat.getSelectedRow());
            LCount.setText(""+tabMode.getRowCount());
            TabRawat.setSelectedIndex(1);
        }else{
            JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
        }
    }

    private void ganti() {
        if(Sequel.mengedittf("penilaian_medis_ralan_rehab_medik","no_rawat=?","no_rawat=?,tanggal=?,kd_dokter=?,anamnesis=?,hubungan=?,keluhan_utama=?,rps=?,rpd=?,alergi=?,kesadaran=?,nyeri=?,skala_nyeri=?,td=?,nadi=?,suhu=?,rr=?,bb=?,"+
                "kepala=?,keterangan_kepala=?,thoraks=?,keterangan_thoraks=?,abdomen=?,keterangan_abdomen=?,ekstremitas=?,keterangan_ekstremitas=?,columna=?,keterangan_columna=?,muskulos=?,keterangan_muskulos=?,lainnya=?,resiko_jatuh=?,resiko_nutrisional=?,kebutuhan_fungsional=?,diagnosa_medis=?,diagnosa_fungsi=?,penunjang_lain=?,"+
                "fisio=?,okupasi=?,wicara=?,akupuntur=?,tatalain=?,frekuensi_terapi=?,fisioterapi=?,terapi_okupasi=?,terapi_wicara=?,terapi_akupuntur=?,terapi_lainnya=?,edukasi=?",49,new String[]{
                TNoRw.getText(),Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),KdDokter.getText(),Anamnesis.getSelectedItem().toString(),Hubungan.getText(),
                KeluhanUtama.getText(),RPS.getText(),RPD.getText(),Alergi.getText(),Kesadaran.getSelectedItem().toString(),Nyeri.getSelectedItem().toString(),SkalaNyeri.getSelectedItem().toString(),TD.getText(),
                Nadi.getText(),Suhu.getText(),RR.getText(),BB.getText(),Kepala.getSelectedItem().toString(),KeteranganKepala.getText(),Thoraks.getSelectedItem().toString(),KeteranganThoraks.getText(),
                Abdomen.getSelectedItem().toString(),KeteranganAbdomen.getText(),Ekstremitas.getSelectedItem().toString(),KeteranganEkstremitas.getText(),Columna.getSelectedItem().toString(),KeteranganColumna.getText(),
                Muskulos.getSelectedItem().toString(),KeteranganMusku.getText(),Lainnya.getText(),Resikojatuh.getSelectedItem().toString(),Resikonutrisional.getSelectedItem().toString(),Kebutuhanfungsional.getSelectedItem().toString(),
                DiagnosaMedis.getText(),DiagnosaFungsi.getText(),PenunjangLain.getText(),Fisio.getText(),Okupasi.getText(),Wicara.getText(),Akupuntur.getText(),Tatalainnya.getText(),Frekuensiterapi.getText(),
                (ChkFisioterapi.isSelected()==true?Valid.SetTgl(TglFisioterapi.getSelectedItem()+""):"0000-00-00"),(ChkOkupasi.isSelected()==true?Valid.SetTgl(TglOkupasi.getSelectedItem()+""):"0000-00-00"),
                (ChkWicara.isSelected()==true?Valid.SetTgl(TglWicara.getSelectedItem()+""):"0000-00-00"),(ChkAkupungtur.isSelected()==true?Valid.SetTgl(TglAkupuntur.getSelectedItem()+""):"0000-00-00"),
                (ChkTerapiLainnya.isSelected()==true?Valid.SetTgl(TglTerapilainnya.getSelectedItem()+""):"0000-00-00"),Edukasi.getText(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
            })==true){
               tampil();
               emptTeks();
               TabRawat.setSelectedIndex(1);
        }
    }
}

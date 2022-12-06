/*
 * Kontribusi dari tim IT RSUD Prembun
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
public final class RMPenilaianAwalMedisRalanGeriatri extends javax.swing.JDialog {
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
    public RMPenilaianAwalMedisRalanGeriatri(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.RM","Nama Pasien","Tgl.Lahir","J.K.","Kode Dokter","Nama Dokter","Tanggal","Anamnesis","Hubungan","Keluhan Utama","Riwayat Penyakit Sekarang","Riwayat Penyakit Dahulu",
            "Riwayat Penggunakan Obat","Riwayat Alergi","Kondisi Umum","TD(mmHg)","Nadi(x/menit)","Suhu","RR(x/menit)","Tulang Belakang","Kepala","Keterangan Kepala","Thoraks","Keterangan Thoraks",
            "Abdomen","Keterangan Abdomen","Ekstremitas","Keterangan Ekstremitas","In.Kebersihan","In.Warna","In.Kelembaban","In.Gangguan","Lainnya","Kondisi Sosial","Status Psikologis (GDS)",
            "Status Kognitif (MMSE)","Status Nutrisi (MNA)","Skrinning Risiko Jatuh (OMS)","Status Fungsional (ADL: BARTHEL INDEX)","Laboratorium","Radiologi","Pemeriksaan","Diagnosis",
            "Diagnosis Banding","Permasalahan","Terapi","Tindakan","Edukasi"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        
        tbObat.setModel(tabMode);
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 49; i++) {
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
                column.setPreferredWidth(100);
            }else if(i==15){
                column.setPreferredWidth(170);
            }else if(i==16){
                column.setPreferredWidth(60);
            }else if(i==17){
                column.setPreferredWidth(76);
            }else if(i==18){
                column.setPreferredWidth(34);
            }else if(i==19){
                column.setPreferredWidth(68);
            }else if(i==20){
                column.setPreferredWidth(88);
            }else if(i==21){
                column.setPreferredWidth(83);
            }else if(i==22){
                column.setPreferredWidth(150);
            }else if(i==23){
                column.setPreferredWidth(83);
            }else if(i==24){
                column.setPreferredWidth(150);
            }else if(i==25){
                column.setPreferredWidth(83);
            }else if(i==26){
                column.setPreferredWidth(150);
            }else if(i==27){
                column.setPreferredWidth(83);
            }else if(i==28){
                column.setPreferredWidth(150);
            }else if(i==29){
                column.setPreferredWidth(75);
            }else if(i==30){
                column.setPreferredWidth(53);
            }else if(i==31){
                column.setPreferredWidth(80);
            }else if(i==32){
                column.setPreferredWidth(71);
            }else if(i==33){
                column.setPreferredWidth(150);
            }else if(i==34){
                column.setPreferredWidth(150);
            }else if(i==35){
                column.setPreferredWidth(150);
            }else if(i==36){
                column.setPreferredWidth(150);
            }else if(i==37){
                column.setPreferredWidth(150);
            }else if(i==38){
                column.setPreferredWidth(150);
            }else if(i==39){
                column.setPreferredWidth(150);
            }else if(i==40){
                column.setPreferredWidth(200);
            }else if(i==41){
                column.setPreferredWidth(200);
            }else if(i==42){
                column.setPreferredWidth(200);
            }else if(i==43){
                column.setPreferredWidth(150);
            }else if(i==44){
                column.setPreferredWidth(150);
            }else if(i==45){
                column.setPreferredWidth(200);
            }else if(i==46){
                column.setPreferredWidth(200);
            }else if(i==47){
                column.setPreferredWidth(200);
            }else if(i==48){
                column.setPreferredWidth(200);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());
        
        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        Hubungan.setDocument(new batasInput((int)30).getKata(Hubungan));
        KeluhanUtama.setDocument(new batasInput((int)2000).getKata(KeluhanUtama));
        RPS.setDocument(new batasInput((int)2000).getKata(RPS));
        RPD.setDocument(new batasInput((int)1000).getKata(RPD));
        RPO.setDocument(new batasInput((int)1000).getKata(RPO));
        Alergi.setDocument(new batasInput((int)50).getKata(Alergi));
        KondisiUmum.setDocument(new batasInput((int)1000).getKata(KondisiUmum));
        KondisiSosial.setDocument(new batasInput((int)500).getKata(KondisiSosial));
        TD.setDocument(new batasInput((byte)8).getKata(TD));
        Nadi.setDocument(new batasInput((byte)5).getKata(Nadi));
        Suhu.setDocument(new batasInput((byte)5).getKata(Suhu));
        RR.setDocument(new batasInput((byte)5).getKata(RR));
        KeteranganKepala.setDocument(new batasInput((byte)100).getKata(KeteranganKepala));
        KeteranganThoraks.setDocument(new batasInput((byte)100).getKata(KeteranganThoraks));
        KeteranganAbdomen.setDocument(new batasInput((byte)100).getKata(KeteranganAbdomen));
        KeteranganEkstremitas.setDocument(new batasInput((byte)100).getKata(KeteranganEkstremitas));
        Lainnya.setDocument(new batasInput((int)1000).getKata(Lainnya));
        Lab.setDocument(new batasInput((int)1000).getKata(Lab));
        Rad.setDocument(new batasInput((int)1000).getKata(Rad));
        PenunjangLain.setDocument(new batasInput((int)1000).getKata(PenunjangLain));
        Diagnosis.setDocument(new batasInput((int)500).getKata(Diagnosis));
        Diagnosis2.setDocument(new batasInput((int)500).getKata(Diagnosis2));
        Permasalahan.setDocument(new batasInput((int)500).getKata(Permasalahan));
        Tindakan.setDocument(new batasInput((int)500).getKata(Tindakan));
        Terapi.setDocument(new batasInput((int)500).getKata(Terapi));
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
        jLabel9 = new widget.Label();
        Jk = new widget.TextBox();
        jLabel10 = new widget.Label();
        jLabel11 = new widget.Label();
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
        jLabel37 = new widget.Label();
        Alergi = new widget.TextBox();
        Anamnesis = new widget.ComboBox();
        scrollPane1 = new widget.ScrollPane();
        KeluhanUtama = new widget.TextArea();
        jLabel30 = new widget.Label();
        scrollPane2 = new widget.ScrollPane();
        RPD = new widget.TextArea();
        jLabel31 = new widget.Label();
        scrollPane4 = new widget.ScrollPane();
        RPO = new widget.TextArea();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel38 = new widget.Label();
        Hubungan = new widget.TextBox();
        jLabel33 = new widget.Label();
        scrollPane7 = new widget.ScrollPane();
        RPS = new widget.TextArea();
        jSeparator12 = new javax.swing.JSeparator();
        jLabel39 = new widget.Label();
        jLabel99 = new widget.Label();
        label11 = new widget.Label();
        TglAsuhan = new widget.Tanggal();
        jLabel41 = new widget.Label();
        jLabel95 = new widget.Label();
        scrollPane3 = new widget.ScrollPane();
        KondisiUmum = new widget.TextArea();
        PosturTulang = new widget.ComboBox();
        jSeparator13 = new javax.swing.JSeparator();
        jLabel94 = new widget.Label();
        scrollPane5 = new widget.ScrollPane();
        Lainnya = new widget.TextArea();
        jLabel45 = new widget.Label();
        KeteranganKepala = new widget.TextBox();
        Kepala = new widget.ComboBox();
        jLabel40 = new widget.Label();
        jLabel46 = new widget.Label();
        Thoraks = new widget.ComboBox();
        KeteranganThoraks = new widget.TextBox();
        jLabel49 = new widget.Label();
        Abdomen = new widget.ComboBox();
        KeteranganAbdomen = new widget.TextBox();
        jLabel51 = new widget.Label();
        Ekstremitas = new widget.ComboBox();
        KeteranganEkstremitas = new widget.TextBox();
        jLabel52 = new widget.Label();
        Psikologis = new widget.ComboBox();
        jLabel53 = new widget.Label();
        Kognitif = new widget.ComboBox();
        jLabel54 = new widget.Label();
        jLabel55 = new widget.Label();
        IntegumentKebersihan = new widget.ComboBox();
        jLabel56 = new widget.Label();
        IntegumentWarna = new widget.ComboBox();
        IntegumentGangguan = new widget.ComboBox();
        IntegumentKelembaban = new widget.ComboBox();
        jLabel57 = new widget.Label();
        jLabel58 = new widget.Label();
        jLabel47 = new widget.Label();
        scrollPane6 = new widget.ScrollPane();
        KondisiSosial = new widget.TextArea();
        jLabel59 = new widget.Label();
        Fungsional = new widget.ComboBox();
        jLabel60 = new widget.Label();
        RisikoJatuh = new widget.ComboBox();
        jLabel61 = new widget.Label();
        Nutrisi = new widget.ComboBox();
        jSeparator14 = new javax.swing.JSeparator();
        jLabel101 = new widget.Label();
        jLabel80 = new widget.Label();
        jLabel81 = new widget.Label();
        jLabel105 = new widget.Label();
        scrollPane12 = new widget.ScrollPane();
        PenunjangLain = new widget.TextArea();
        scrollPane10 = new widget.ScrollPane();
        Rad = new widget.TextArea();
        scrollPane9 = new widget.ScrollPane();
        Lab = new widget.TextArea();
        jSeparator16 = new javax.swing.JSeparator();
        jLabel106 = new widget.Label();
        jLabel82 = new widget.Label();
        jLabel85 = new widget.Label();
        scrollPane16 = new widget.ScrollPane();
        Diagnosis2 = new widget.TextArea();
        scrollPane15 = new widget.ScrollPane();
        Diagnosis = new widget.TextArea();
        jSeparator17 = new javax.swing.JSeparator();
        jLabel107 = new widget.Label();
        jLabel108 = new widget.Label();
        jLabel109 = new widget.Label();
        scrollPane20 = new widget.ScrollPane();
        Permasalahan = new widget.TextArea();
        scrollPane17 = new widget.ScrollPane();
        Terapi = new widget.TextArea();
        jLabel112 = new widget.Label();
        scrollPane19 = new widget.ScrollPane();
        Tindakan = new widget.TextArea();
        jSeparator18 = new javax.swing.JSeparator();
        jLabel110 = new widget.Label();
        scrollPane14 = new widget.ScrollPane();
        Edukasi = new widget.TextArea();
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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Penilaian Awal Medis Rawat Jalan Geriatri ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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
        FormInput.setPreferredSize(new java.awt.Dimension(870, 1063));
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

        jLabel9.setText("Riwayat Penggunaan Obat :");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(0, 170, 180, 23);

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

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel16.setText("x/menit");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput.add(jLabel16);
        jLabel16.setBounds(282, 240, 50, 23);

        Nadi.setFocusTraversalPolicyProvider(true);
        Nadi.setName("Nadi"); // NOI18N
        Nadi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NadiKeyPressed(evt);
            }
        });
        FormInput.add(Nadi);
        Nadi.setBounds(234, 240, 45, 23);

        jLabel17.setText("Nadi :");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(190, 240, 40, 23);

        jLabel18.setText("Suhu :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(323, 240, 40, 23);

        Suhu.setFocusTraversalPolicyProvider(true);
        Suhu.setName("Suhu"); // NOI18N
        Suhu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SuhuKeyPressed(evt);
            }
        });
        FormInput.add(Suhu);
        Suhu.setBounds(367, 240, 45, 23);

        jLabel22.setText("TD :");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(0, 240, 80, 23);

        TD.setFocusTraversalPolicyProvider(true);
        TD.setName("TD"); // NOI18N
        TD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TDKeyPressed(evt);
            }
        });
        FormInput.add(TD);
        TD.setBounds(84, 240, 71, 23);

        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel20.setText("°C");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(415, 240, 30, 23);

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel23.setText("mmHg");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(158, 240, 50, 23);

        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel25.setText("x/menit");
        jLabel25.setName("jLabel25"); // NOI18N
        FormInput.add(jLabel25);
        jLabel25.setBounds(132, 270, 50, 23);

        RR.setFocusTraversalPolicyProvider(true);
        RR.setName("RR"); // NOI18N
        RR.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RRKeyPressed(evt);
            }
        });
        FormInput.add(RR);
        RR.setBounds(84, 270, 45, 23);

        jLabel26.setText("RR :");
        jLabel26.setName("jLabel26"); // NOI18N
        FormInput.add(jLabel26);
        jLabel26.setBounds(0, 270, 80, 23);

        jLabel37.setText("Riwayat Alergi :");
        jLabel37.setName("jLabel37"); // NOI18N
        FormInput.add(jLabel37);
        jLabel37.setBounds(440, 190, 150, 23);

        Alergi.setFocusTraversalPolicyProvider(true);
        Alergi.setName("Alergi"); // NOI18N
        Alergi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlergiKeyPressed(evt);
            }
        });
        FormInput.add(Alergi);
        Alergi.setBounds(594, 190, 260, 23);

        Anamnesis.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Autoanamnesis", "Alloanamnesis" }));
        Anamnesis.setName("Anamnesis"); // NOI18N
        Anamnesis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AnamnesisKeyPressed(evt);
            }
        });
        FormInput.add(Anamnesis);
        Anamnesis.setBounds(644, 40, 128, 23);

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

        scrollPane4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane4.setName("scrollPane4"); // NOI18N

        RPO.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        RPO.setColumns(20);
        RPO.setRows(5);
        RPO.setName("RPO"); // NOI18N
        RPO.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RPOKeyPressed(evt);
            }
        });
        scrollPane4.setViewportView(RPO);

        FormInput.add(scrollPane4);
        scrollPane4.setBounds(184, 170, 255, 43);

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

        jSeparator12.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator12.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator12.setName("jSeparator12"); // NOI18N
        FormInput.add(jSeparator12);
        jSeparator12.setBounds(0, 220, 880, 1);

        jLabel39.setText("Kondisi Umum :");
        jLabel39.setName("jLabel39"); // NOI18N
        FormInput.add(jLabel39);
        jLabel39.setBounds(440, 240, 90, 23);

        jLabel99.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel99.setText("I. RIWAYAT KESEHATAN");
        jLabel99.setName("jLabel99"); // NOI18N
        FormInput.add(jLabel99);
        jLabel99.setBounds(10, 70, 180, 23);

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label11);
        label11.setBounds(380, 40, 52, 23);

        TglAsuhan.setForeground(new java.awt.Color(50, 70, 50));
        TglAsuhan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "04-11-2022 11:25:51" }));
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

        jLabel41.setText("Postur Tulang Belakang :");
        jLabel41.setName("jLabel41"); // NOI18N
        FormInput.add(jLabel41);
        jLabel41.setBounds(177, 270, 130, 23);

        jLabel95.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel95.setText("II. PEMERIKSAAN FISIK");
        jLabel95.setName("jLabel95"); // NOI18N
        FormInput.add(jLabel95);
        jLabel95.setBounds(10, 220, 180, 23);

        scrollPane3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane3.setName("scrollPane3"); // NOI18N

        KondisiUmum.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        KondisiUmum.setColumns(20);
        KondisiUmum.setRows(5);
        KondisiUmum.setName("KondisiUmum"); // NOI18N
        KondisiUmum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KondisiUmumKeyPressed(evt);
            }
        });
        scrollPane3.setViewportView(KondisiUmum);

        FormInput.add(scrollPane3);
        scrollPane3.setBounds(534, 240, 320, 53);

        PosturTulang.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tegap", "Membungkuk", "Kifosis", "Skoliosis", "Lordosis" }));
        PosturTulang.setName("PosturTulang"); // NOI18N
        PosturTulang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PosturTulangKeyPressed(evt);
            }
        });
        FormInput.add(PosturTulang);
        PosturTulang.setBounds(310, 270, 115, 23);

        jSeparator13.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator13.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator13.setName("jSeparator13"); // NOI18N
        FormInput.add(jSeparator13);
        jSeparator13.setBounds(0, 300, 880, 1);

        jLabel94.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel94.setText("III. STATUS KELAINAN");
        jLabel94.setName("jLabel94"); // NOI18N
        FormInput.add(jLabel94);
        jLabel94.setBounds(10, 300, 180, 23);

        scrollPane5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane5.setName("scrollPane5"); // NOI18N

        Lainnya.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Lainnya.setColumns(20);
        Lainnya.setRows(14);
        Lainnya.setName("Lainnya"); // NOI18N
        Lainnya.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LainnyaKeyPressed(evt);
            }
        });
        scrollPane5.setViewportView(Lainnya);

        FormInput.add(scrollPane5);
        scrollPane5.setBounds(132, 440, 370, 73);

        jLabel45.setText("Lainnya :");
        jLabel45.setName("jLabel45"); // NOI18N
        FormInput.add(jLabel45);
        jLabel45.setBounds(0, 440, 128, 23);

        KeteranganKepala.setFocusTraversalPolicyProvider(true);
        KeteranganKepala.setName("KeteranganKepala"); // NOI18N
        KeteranganKepala.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKepalaKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganKepala);
        KeteranganKepala.setBounds(273, 320, 310, 23);

        Kepala.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Kepala.setName("Kepala"); // NOI18N
        Kepala.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KepalaKeyPressed(evt);
            }
        });
        FormInput.add(Kepala);
        Kepala.setBounds(132, 320, 138, 23);

        jLabel40.setText("Kepala :");
        jLabel40.setName("jLabel40"); // NOI18N
        FormInput.add(jLabel40);
        jLabel40.setBounds(0, 320, 128, 23);

        jLabel46.setText("Thoraks :");
        jLabel46.setName("jLabel46"); // NOI18N
        FormInput.add(jLabel46);
        jLabel46.setBounds(0, 350, 128, 23);

        Thoraks.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Thoraks.setName("Thoraks"); // NOI18N
        Thoraks.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ThoraksKeyPressed(evt);
            }
        });
        FormInput.add(Thoraks);
        Thoraks.setBounds(132, 350, 138, 23);

        KeteranganThoraks.setFocusTraversalPolicyProvider(true);
        KeteranganThoraks.setName("KeteranganThoraks"); // NOI18N
        KeteranganThoraks.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganThoraksKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganThoraks);
        KeteranganThoraks.setBounds(273, 350, 310, 23);

        jLabel49.setText("Abdomen :");
        jLabel49.setName("jLabel49"); // NOI18N
        FormInput.add(jLabel49);
        jLabel49.setBounds(0, 380, 128, 23);

        Abdomen.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Abdomen.setName("Abdomen"); // NOI18N
        Abdomen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AbdomenKeyPressed(evt);
            }
        });
        FormInput.add(Abdomen);
        Abdomen.setBounds(132, 380, 138, 23);

        KeteranganAbdomen.setFocusTraversalPolicyProvider(true);
        KeteranganAbdomen.setName("KeteranganAbdomen"); // NOI18N
        KeteranganAbdomen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganAbdomenKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganAbdomen);
        KeteranganAbdomen.setBounds(273, 380, 310, 23);

        jLabel51.setText("Ekstremitas :");
        jLabel51.setName("jLabel51"); // NOI18N
        FormInput.add(jLabel51);
        jLabel51.setBounds(0, 410, 128, 23);

        Ekstremitas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Ekstremitas.setName("Ekstremitas"); // NOI18N
        Ekstremitas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EkstremitasKeyPressed(evt);
            }
        });
        FormInput.add(Ekstremitas);
        Ekstremitas.setBounds(132, 410, 138, 23);

        KeteranganEkstremitas.setFocusTraversalPolicyProvider(true);
        KeteranganEkstremitas.setName("KeteranganEkstremitas"); // NOI18N
        KeteranganEkstremitas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganEkstremitasKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganEkstremitas);
        KeteranganEkstremitas.setBounds(273, 410, 310, 23);

        jLabel52.setText("Psikologis (GDS) :");
        jLabel52.setName("jLabel52"); // NOI18N
        FormInput.add(jLabel52);
        jLabel52.setBounds(0, 520, 128, 23);

        Psikologis.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Skor 1-4 Tidak Ada Depresi", "Skor Antara 5-9 Menunjukkan Kemungkinan Besar Depresi", "Skor 10 Atau Lebih Menunjukkan Depresi" }));
        Psikologis.setName("Psikologis"); // NOI18N
        Psikologis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PsikologisKeyPressed(evt);
            }
        });
        FormInput.add(Psikologis);
        Psikologis.setBounds(132, 520, 341, 23);

        jLabel53.setText("Kognitif (MMSE) :");
        jLabel53.setName("jLabel53"); // NOI18N
        FormInput.add(jLabel53);
        jLabel53.setBounds(0, 550, 128, 23);

        Kognitif.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "24-30 : Tidak Ada Gangguan Kognitif", "18-23 : Gangguan Kognitif Sedang", "0-17 : Gangguan Kognitif Berat" }));
        Kognitif.setName("Kognitif"); // NOI18N
        Kognitif.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KognitifKeyPressed(evt);
            }
        });
        FormInput.add(Kognitif);
        Kognitif.setBounds(132, 550, 270, 23);

        jLabel54.setText("Integument :");
        jLabel54.setName("jLabel54"); // NOI18N
        FormInput.add(jLabel54);
        jLabel54.setBounds(590, 320, 90, 23);

        jLabel55.setText("Kebersihan :");
        jLabel55.setName("jLabel55"); // NOI18N
        FormInput.add(jLabel55);
        jLabel55.setBounds(622, 340, 90, 23);

        IntegumentKebersihan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal" }));
        IntegumentKebersihan.setName("IntegumentKebersihan"); // NOI18N
        IntegumentKebersihan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                IntegumentKebersihanKeyPressed(evt);
            }
        });
        FormInput.add(IntegumentKebersihan);
        IntegumentKebersihan.setBounds(716, 340, 138, 23);

        jLabel56.setText("Warna :");
        jLabel56.setName("jLabel56"); // NOI18N
        FormInput.add(jLabel56);
        jLabel56.setBounds(622, 370, 90, 23);

        IntegumentWarna.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Pucat", "Sianosis", "Lain-lain" }));
        IntegumentWarna.setName("IntegumentWarna"); // NOI18N
        IntegumentWarna.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                IntegumentWarnaKeyPressed(evt);
            }
        });
        FormInput.add(IntegumentWarna);
        IntegumentWarna.setBounds(716, 370, 138, 23);

        IntegumentGangguan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Rash", "Luka", "Memar", "Ptekie", "Bula" }));
        IntegumentGangguan.setName("IntegumentGangguan"); // NOI18N
        IntegumentGangguan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                IntegumentGangguanKeyPressed(evt);
            }
        });
        FormInput.add(IntegumentGangguan);
        IntegumentGangguan.setBounds(716, 430, 138, 23);

        IntegumentKelembaban.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Kering", "Lembab" }));
        IntegumentKelembaban.setName("IntegumentKelembaban"); // NOI18N
        IntegumentKelembaban.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                IntegumentKelembabanKeyPressed(evt);
            }
        });
        FormInput.add(IntegumentKelembaban);
        IntegumentKelembaban.setBounds(716, 400, 138, 23);

        jLabel57.setText("Kelembaban :");
        jLabel57.setName("jLabel57"); // NOI18N
        FormInput.add(jLabel57);
        jLabel57.setBounds(622, 400, 90, 23);

        jLabel58.setText("Gangguan Kulit :");
        jLabel58.setName("jLabel58"); // NOI18N
        FormInput.add(jLabel58);
        jLabel58.setBounds(622, 430, 90, 23);

        jLabel47.setText("Kondisi Sosial :");
        jLabel47.setName("jLabel47"); // NOI18N
        FormInput.add(jLabel47);
        jLabel47.setBounds(510, 460, 90, 23);

        scrollPane6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane6.setName("scrollPane6"); // NOI18N

        KondisiSosial.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        KondisiSosial.setColumns(20);
        KondisiSosial.setRows(14);
        KondisiSosial.setName("KondisiSosial"); // NOI18N
        KondisiSosial.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KondisiSosialKeyPressed(evt);
            }
        });
        scrollPane6.setViewportView(KondisiSosial);

        FormInput.add(scrollPane6);
        scrollPane6.setBounds(604, 460, 250, 83);

        jLabel59.setText("Status Fungsional (ADL: BARTHEL INDEX) :");
        jLabel59.setName("jLabel59"); // NOI18N
        FormInput.add(jLabel59);
        jLabel59.setBounds(370, 580, 250, 23);

        Fungsional.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "20 : Mandiri (A)", "12-19 : Ketergantungan Ringan (B)", "9-11 : Ketergantungan Sedang (B)", "5-8 : Ketergantungan Berat (C)", "0-4 : Ketergantungan Total (C)" }));
        Fungsional.setName("Fungsional"); // NOI18N
        Fungsional.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FungsionalKeyPressed(evt);
            }
        });
        FormInput.add(Fungsional);
        Fungsional.setBounds(624, 580, 230, 23);

        jLabel60.setText("Skrinning Risiko Jatuh (OMS) :");
        jLabel60.setName("jLabel60"); // NOI18N
        FormInput.add(jLabel60);
        jLabel60.setBounds(470, 550, 200, 23);

        RisikoJatuh.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Risiko Rendah Skor 0-5", "Risiko Sedang Skor 6-16", "Risiko Tinggi Skor 17-30" }));
        RisikoJatuh.setName("RisikoJatuh"); // NOI18N
        RisikoJatuh.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RisikoJatuhKeyPressed(evt);
            }
        });
        FormInput.add(RisikoJatuh);
        RisikoJatuh.setBounds(674, 550, 180, 23);

        jLabel61.setText("Nutrisi (MNA) :");
        jLabel61.setName("jLabel61"); // NOI18N
        FormInput.add(jLabel61);
        jLabel61.setBounds(0, 580, 128, 23);

        Nutrisi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Skor 12-14 : Status Gizi Normal", "Skor 8-11 : Berisiko Malnutrisi", "Skor 0-7 : Malnutrisi" }));
        Nutrisi.setName("Nutrisi"); // NOI18N
        Nutrisi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NutrisiKeyPressed(evt);
            }
        });
        FormInput.add(Nutrisi);
        Nutrisi.setBounds(132, 580, 220, 23);

        jSeparator14.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator14.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator14.setName("jSeparator14"); // NOI18N
        FormInput.add(jSeparator14);
        jSeparator14.setBounds(0, 610, 880, 1);

        jLabel101.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel101.setText("IV. PEMERIKSAAN PENUNJANG");
        jLabel101.setName("jLabel101"); // NOI18N
        FormInput.add(jLabel101);
        jLabel101.setBounds(10, 610, 190, 23);

        jLabel80.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel80.setText("Laboratorium :");
        jLabel80.setName("jLabel80"); // NOI18N
        FormInput.add(jLabel80);
        jLabel80.setBounds(44, 630, 150, 23);

        jLabel81.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel81.setText("Radiologi :");
        jLabel81.setName("jLabel81"); // NOI18N
        FormInput.add(jLabel81);
        jLabel81.setBounds(319, 630, 150, 23);

        jLabel105.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel105.setText("Penunjang Lainnya :");
        jLabel105.setName("jLabel105"); // NOI18N
        FormInput.add(jLabel105);
        jLabel105.setBounds(594, 630, 190, 23);

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
        scrollPane12.setBounds(594, 650, 260, 63);

        scrollPane10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane10.setName("scrollPane10"); // NOI18N

        Rad.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Rad.setColumns(20);
        Rad.setRows(5);
        Rad.setName("Rad"); // NOI18N
        Rad.setPreferredSize(new java.awt.Dimension(102, 52));
        Rad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RadKeyPressed(evt);
            }
        });
        scrollPane10.setViewportView(Rad);

        FormInput.add(scrollPane10);
        scrollPane10.setBounds(319, 650, 260, 63);

        scrollPane9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane9.setName("scrollPane9"); // NOI18N

        Lab.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Lab.setColumns(20);
        Lab.setRows(5);
        Lab.setName("Lab"); // NOI18N
        Lab.setPreferredSize(new java.awt.Dimension(102, 52));
        Lab.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LabKeyPressed(evt);
            }
        });
        scrollPane9.setViewportView(Lab);

        FormInput.add(scrollPane9);
        scrollPane9.setBounds(44, 650, 260, 63);

        jSeparator16.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator16.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator16.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator16.setName("jSeparator16"); // NOI18N
        FormInput.add(jSeparator16);
        jSeparator16.setBounds(0, 720, 880, 1);

        jLabel106.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel106.setText("V. DIAGNOSIS/ASESMEN");
        jLabel106.setName("jLabel106"); // NOI18N
        FormInput.add(jLabel106);
        jLabel106.setBounds(10, 720, 190, 23);

        jLabel82.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel82.setText("Asesmen Kerja :");
        jLabel82.setName("jLabel82"); // NOI18N
        FormInput.add(jLabel82);
        jLabel82.setBounds(44, 740, 150, 23);

        jLabel85.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel85.setText("Asesmen Banding :");
        jLabel85.setName("jLabel85"); // NOI18N
        FormInput.add(jLabel85);
        jLabel85.setBounds(454, 740, 150, 23);

        scrollPane16.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane16.setName("scrollPane16"); // NOI18N

        Diagnosis2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Diagnosis2.setColumns(20);
        Diagnosis2.setRows(3);
        Diagnosis2.setName("Diagnosis2"); // NOI18N
        Diagnosis2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Diagnosis2KeyPressed(evt);
            }
        });
        scrollPane16.setViewportView(Diagnosis2);

        FormInput.add(scrollPane16);
        scrollPane16.setBounds(454, 760, 400, 43);

        scrollPane15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane15.setName("scrollPane15"); // NOI18N

        Diagnosis.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Diagnosis.setColumns(20);
        Diagnosis.setRows(3);
        Diagnosis.setName("Diagnosis"); // NOI18N
        Diagnosis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosisKeyPressed(evt);
            }
        });
        scrollPane15.setViewportView(Diagnosis);

        FormInput.add(scrollPane15);
        scrollPane15.setBounds(44, 760, 400, 43);

        jSeparator17.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator17.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator17.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator17.setName("jSeparator17"); // NOI18N
        FormInput.add(jSeparator17);
        jSeparator17.setBounds(0, 810, 880, 1);

        jLabel107.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel107.setText("VI. PERMASALAHAN & TATALAKSANA");
        jLabel107.setName("jLabel107"); // NOI18N
        FormInput.add(jLabel107);
        jLabel107.setBounds(10, 810, 230, 23);

        jLabel108.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel108.setText("Permasalahan :");
        jLabel108.setName("jLabel108"); // NOI18N
        FormInput.add(jLabel108);
        jLabel108.setBounds(44, 830, 190, 20);

        jLabel109.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel109.setText("Terapi/Pengobatan :");
        jLabel109.setName("jLabel109"); // NOI18N
        FormInput.add(jLabel109);
        jLabel109.setBounds(454, 830, 190, 20);

        scrollPane20.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane20.setName("scrollPane20"); // NOI18N

        Permasalahan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Permasalahan.setColumns(20);
        Permasalahan.setRows(3);
        Permasalahan.setName("Permasalahan"); // NOI18N
        Permasalahan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PermasalahanKeyPressed(evt);
            }
        });
        scrollPane20.setViewportView(Permasalahan);

        FormInput.add(scrollPane20);
        scrollPane20.setBounds(44, 850, 400, 43);

        scrollPane17.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane17.setName("scrollPane17"); // NOI18N

        Terapi.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Terapi.setColumns(20);
        Terapi.setRows(3);
        Terapi.setName("Terapi"); // NOI18N
        Terapi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TerapiKeyPressed(evt);
            }
        });
        scrollPane17.setViewportView(Terapi);

        FormInput.add(scrollPane17);
        scrollPane17.setBounds(454, 850, 400, 43);

        jLabel112.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel112.setText("Tindakan/Rencana Tindakan :");
        jLabel112.setName("jLabel112"); // NOI18N
        FormInput.add(jLabel112);
        jLabel112.setBounds(44, 900, 320, 20);

        scrollPane19.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane19.setName("scrollPane19"); // NOI18N

        Tindakan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Tindakan.setColumns(20);
        Tindakan.setRows(3);
        Tindakan.setName("Tindakan"); // NOI18N
        Tindakan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TindakanKeyPressed(evt);
            }
        });
        scrollPane19.setViewportView(Tindakan);

        FormInput.add(scrollPane19);
        scrollPane19.setBounds(44, 920, 810, 43);

        jSeparator18.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator18.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator18.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator18.setName("jSeparator18"); // NOI18N
        FormInput.add(jSeparator18);
        jSeparator18.setBounds(0, 970, 880, 1);

        jLabel110.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel110.setText("VII. EDUKASI");
        jLabel110.setName("jLabel110"); // NOI18N
        FormInput.add(jLabel110);
        jLabel110.setBounds(10, 970, 190, 23);

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
        scrollPane14.setBounds(44, 990, 810, 63);

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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "04-11-2022" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "04-11-2022" }));
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
        }else if(RPO.getText().trim().equals("")){
            Valid.textKosong(RPO,"Riwayat Pengunaan obat");
        }else{
            if(Sequel.menyimpantf("penilaian_medis_ralan_geriatri","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat",44,new String[]{
                    TNoRw.getText(),Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),KdDokter.getText(),Anamnesis.getSelectedItem().toString(),Hubungan.getText(),
                    KeluhanUtama.getText(),RPS.getText(),RPD.getText(),RPO.getText(),Alergi.getText(),PosturTulang.getSelectedItem().toString(),TD.getText(),Nadi.getText(),Suhu.getText(),RR.getText(),KondisiUmum.getText(),
                    Psikologis.getSelectedItem().toString(),KondisiSosial.getText(),Kognitif.getSelectedItem().toString(),Kepala.getSelectedItem().toString(),KeteranganKepala.getText(),Thoraks.getSelectedItem().toString(),
                    KeteranganThoraks.getText(),Abdomen.getSelectedItem().toString(),KeteranganAbdomen.getText(),Ekstremitas.getSelectedItem().toString(),KeteranganEkstremitas.getText(),IntegumentKebersihan.getSelectedItem().toString(),
                    IntegumentWarna.getSelectedItem().toString(),IntegumentKelembaban.getSelectedItem().toString(),IntegumentGangguan.getSelectedItem().toString(),Fungsional.getSelectedItem().toString(),
                    RisikoJatuh.getSelectedItem().toString(),Nutrisi.getSelectedItem().toString(),Lainnya.getText(),Lab.getText(),Rad.getText(),PenunjangLain.getText(),Diagnosis.getText(),Diagnosis2.getText(),
                    Permasalahan.getText(),Terapi.getText(),Tindakan.getText(),Edukasi.getText()
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
        }else if(RPO.getText().trim().equals("")){
            Valid.textKosong(RPO,"Riwayat Pengunaan obat");
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
                            "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penilaian_medis_ralan_geriatri.tanggal,"+
                            "penilaian_medis_ralan_geriatri.kd_dokter,dokter.nm_dokter,penilaian_medis_ralan_geriatri.anamnesis,penilaian_medis_ralan_geriatri.hubungan,penilaian_medis_ralan_geriatri.keluhan_utama,"+
                            "penilaian_medis_ralan_geriatri.rps,penilaian_medis_ralan_geriatri.rpd,penilaian_medis_ralan_geriatri.rpo,penilaian_medis_ralan_geriatri.alergi,penilaian_medis_ralan_geriatri.tulang_belakang,"+
                            "penilaian_medis_ralan_geriatri.td,penilaian_medis_ralan_geriatri.nadi,penilaian_medis_ralan_geriatri.suhu,penilaian_medis_ralan_geriatri.rr,penilaian_medis_ralan_geriatri.kondisi_umum,"+
                            "penilaian_medis_ralan_geriatri.status_psikologis_gds,penilaian_medis_ralan_geriatri.kondisi_sosial,penilaian_medis_ralan_geriatri.status_kognitif_mmse,penilaian_medis_ralan_geriatri.kepala,"+
                            "penilaian_medis_ralan_geriatri.keterangan_kepala,penilaian_medis_ralan_geriatri.thoraks,penilaian_medis_ralan_geriatri.keterangan_thoraks,penilaian_medis_ralan_geriatri.abdomen,"+
                            "penilaian_medis_ralan_geriatri.keterangan_abdomen,penilaian_medis_ralan_geriatri.ekstremitas,penilaian_medis_ralan_geriatri.keterangan_ekstremitas,penilaian_medis_ralan_geriatri.Integument_kebersihan,"+
                            "penilaian_medis_ralan_geriatri.Integument_warna,penilaian_medis_ralan_geriatri.Integument_kelembaban,penilaian_medis_ralan_geriatri.Integument_gangguan_kulit,penilaian_medis_ralan_geriatri.status_fungsional,"+
                            "penilaian_medis_ralan_geriatri.skrining_jatuh,penilaian_medis_ralan_geriatri.status_nutrisi,penilaian_medis_ralan_geriatri.lainnya,penilaian_medis_ralan_geriatri.lab,penilaian_medis_ralan_geriatri.rad,"+
                            "penilaian_medis_ralan_geriatri.pemeriksaan,penilaian_medis_ralan_geriatri.diagnosis,penilaian_medis_ralan_geriatri.diagnosis2,penilaian_medis_ralan_geriatri.permasalahan,penilaian_medis_ralan_geriatri.terapi,"+
                            "penilaian_medis_ralan_geriatri.tindakan,penilaian_medis_ralan_geriatri.edukasi "+
                            "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                            "inner join penilaian_medis_ralan_geriatri on reg_periksa.no_rawat=penilaian_medis_ralan_geriatri.no_rawat "+
                            "inner join dokter on penilaian_medis_ralan_geriatri.kd_dokter=dokter.kd_dokter where "+
                            "penilaian_medis_ralan_geriatri.tanggal between ? and ? order by penilaian_medis_ralan_geriatri.tanggal");
                }else{
                    ps=koneksi.prepareStatement(
                            "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penilaian_medis_ralan_geriatri.tanggal,"+
                            "penilaian_medis_ralan_geriatri.kd_dokter,dokter.nm_dokter,penilaian_medis_ralan_geriatri.anamnesis,penilaian_medis_ralan_geriatri.hubungan,penilaian_medis_ralan_geriatri.keluhan_utama,"+
                            "penilaian_medis_ralan_geriatri.rps,penilaian_medis_ralan_geriatri.rpd,penilaian_medis_ralan_geriatri.rpo,penilaian_medis_ralan_geriatri.alergi,penilaian_medis_ralan_geriatri.tulang_belakang,"+
                            "penilaian_medis_ralan_geriatri.td,penilaian_medis_ralan_geriatri.nadi,penilaian_medis_ralan_geriatri.suhu,penilaian_medis_ralan_geriatri.rr,penilaian_medis_ralan_geriatri.kondisi_umum,"+
                            "penilaian_medis_ralan_geriatri.status_psikologis_gds,penilaian_medis_ralan_geriatri.kondisi_sosial,penilaian_medis_ralan_geriatri.status_kognitif_mmse,penilaian_medis_ralan_geriatri.kepala,"+
                            "penilaian_medis_ralan_geriatri.keterangan_kepala,penilaian_medis_ralan_geriatri.thoraks,penilaian_medis_ralan_geriatri.keterangan_thoraks,penilaian_medis_ralan_geriatri.abdomen,"+
                            "penilaian_medis_ralan_geriatri.keterangan_abdomen,penilaian_medis_ralan_geriatri.ekstremitas,penilaian_medis_ralan_geriatri.keterangan_ekstremitas,penilaian_medis_ralan_geriatri.Integument_kebersihan,"+
                            "penilaian_medis_ralan_geriatri.Integument_warna,penilaian_medis_ralan_geriatri.Integument_kelembaban,penilaian_medis_ralan_geriatri.Integument_gangguan_kulit,penilaian_medis_ralan_geriatri.status_fungsional,"+
                            "penilaian_medis_ralan_geriatri.skrining_jatuh,penilaian_medis_ralan_geriatri.status_nutrisi,penilaian_medis_ralan_geriatri.lainnya,penilaian_medis_ralan_geriatri.lab,penilaian_medis_ralan_geriatri.rad,"+
                            "penilaian_medis_ralan_geriatri.pemeriksaan,penilaian_medis_ralan_geriatri.diagnosis,penilaian_medis_ralan_geriatri.diagnosis2,penilaian_medis_ralan_geriatri.permasalahan,penilaian_medis_ralan_geriatri.terapi,"+
                            "penilaian_medis_ralan_geriatri.tindakan,penilaian_medis_ralan_geriatri.edukasi "+
                            "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                            "inner join penilaian_medis_ralan_geriatri on reg_periksa.no_rawat=penilaian_medis_ralan_geriatri.no_rawat "+
                            "inner join dokter on penilaian_medis_ralan_geriatri.kd_dokter=dokter.kd_dokter where "+
                            "penilaian_medis_ralan_geriatri.tanggal between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or "+
                            "penilaian_medis_ralan_geriatri.kd_dokter like ? or dokter.nm_dokter like ?) order by penilaian_medis_ralan_geriatri.tanggal");
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
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Kode Dokter</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Nama Dokter</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Tanggal</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Anamnesis</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Hubungan</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Keluhan Utama</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Riwayat Penyakit Sekarang</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Riwayat Penyakit Dahulu</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Riwayat Penggunakan Obat</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Riwayat Alergi</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Kondisi Umum</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>TD(mmHg)</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Nadi(x/menit)</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Suhu</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>RR(x/menit)</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Tulang Belakang</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Kepala</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Keterangan Kepala</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Thoraks</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Keterangan Thoraks</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Abdomen</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Keterangan Abdomen</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Ekstremitas</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Keterangan Ekstremitas</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>In.Kebersihan</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>In.Warna</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>In.Kelembaban</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>In.Gangguan</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Lainnya</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Kondisi Sosial</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Status Psikologis (GDS)</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Status Kognitif (MMSE)</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Status Nutrisi (MNA)</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Skrinning Risiko Jatuh (OMS)</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Status Fungsional (ADL: BARTHEL INDEX)</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Laboratorium</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Radiologi</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Pemeriksaan</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Diagnosis</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Diagnosis Banding</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Permasalahan</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Terapi</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Tindakan</b></td>"+
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
                               "<td valign='top'>"+rs.getString("rpo")+"</td>"+
                               "<td valign='top'>"+rs.getString("alergi")+"</td>"+
                               "<td valign='top'>"+rs.getString("kondisi_umum")+"</td>"+
                               "<td valign='top'>"+rs.getString("td")+"</td>"+
                               "<td valign='top'>"+rs.getString("nadi")+"</td>"+
                               "<td valign='top'>"+rs.getString("suhu")+"</td>"+
                               "<td valign='top'>"+rs.getString("rr")+"</td>"+
                               "<td valign='top'>"+rs.getString("tulang_belakang")+"</td>"+
                               "<td valign='top'>"+rs.getString("kepala")+"</td>"+
                               "<td valign='top'>"+rs.getString("keterangan_kepala")+"</td>"+
                               "<td valign='top'>"+rs.getString("thoraks")+"</td>"+
                               "<td valign='top'>"+rs.getString("keterangan_thoraks")+"</td>"+
                               "<td valign='top'>"+rs.getString("abdomen")+"</td>"+
                               "<td valign='top'>"+rs.getString("keterangan_abdomen")+"</td>"+
                               "<td valign='top'>"+rs.getString("ekstremitas")+"</td>"+
                               "<td valign='top'>"+rs.getString("keterangan_ekstremitas")+"</td>"+
                               "<td valign='top'>"+rs.getString("Integument_kebersihan")+"</td>"+
                               "<td valign='top'>"+rs.getString("Integument_warna")+"</td>"+
                               "<td valign='top'>"+rs.getString("Integument_kelembaban")+"</td>"+
                               "<td valign='top'>"+rs.getString("Integument_gangguan_kulit")+"</td>"+
                               "<td valign='top'>"+rs.getString("lainnya")+"</td>"+
                               "<td valign='top'>"+rs.getString("kondisi_sosial")+"</td>"+
                               "<td valign='top'>"+rs.getString("status_psikologis_gds")+"</td>"+
                               "<td valign='top'>"+rs.getString("status_kognitif_mmse")+"</td>"+
                               "<td valign='top'>"+rs.getString("status_nutrisi")+"</td>"+
                               "<td valign='top'>"+rs.getString("skrining_jatuh")+"</td>"+
                               "<td valign='top'>"+rs.getString("status_fungsional")+"</td>"+
                               "<td valign='top'>"+rs.getString("lab")+"</td>"+
                               "<td valign='top'>"+rs.getString("rad")+"</td>"+
                               "<td valign='top'>"+rs.getString("pemeriksaan")+"</td>"+
                               "<td valign='top'>"+rs.getString("diagnosis")+"</td>"+
                               "<td valign='top'>"+rs.getString("diagnosis2")+"</td>"+
                               "<td valign='top'>"+rs.getString("permasalahan")+"</td>"+
                               "<td valign='top'>"+rs.getString("terapi")+"</td>"+
                               "<td valign='top'>"+rs.getString("tindakan")+"</td>"+
                               "<td valign='top'>"+rs.getString("edukasi")+"</td>"+
                            "</tr>");
                    }
                    LoadHTML.setText(
                        "<html>"+
                          "<table width='5400px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
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

                    File f = new File("DataPenilaianAwalMedisRalanGeriatri.html");            
                    BufferedWriter bw = new BufferedWriter(new FileWriter(f));            
                    bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                                "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                                "<table width='5400px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                    "<tr class='isi2'>"+
                                        "<td valign='top' align='center'>"+
                                            "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                            akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                            akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                            "<font size='2' face='Tahoma'>DATA PENILAIAN AWAL MEDIS RAWAT JALAN GERIATRI<br><br></font>"+        
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

    private void BtnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokterActionPerformed
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setAlwaysOnTop(false);
        dokter.setVisible(true);
    }//GEN-LAST:event_BtnDokterActionPerformed

    private void BtnDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokterKeyPressed
        //Valid.pindah(evt,Edukasi,Hubungan);
    }//GEN-LAST:event_BtnDokterKeyPressed

    private void AlergiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlergiKeyPressed
        Valid.pindah(evt,RPO,TD);
    }//GEN-LAST:event_AlergiKeyPressed

    private void AnamnesisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AnamnesisKeyPressed
        Valid.pindah(evt,TglAsuhan,Hubungan);
    }//GEN-LAST:event_AnamnesisKeyPressed

    private void KeluhanUtamaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeluhanUtamaKeyPressed
        Valid.pindah2(evt,Hubungan,RPS);
    }//GEN-LAST:event_KeluhanUtamaKeyPressed

    private void RPDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RPDKeyPressed
        Valid.pindah2(evt,RPS,RPO);
    }//GEN-LAST:event_RPDKeyPressed

    private void RPOKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RPOKeyPressed
        Valid.pindah2(evt,RPD,Alergi);
    }//GEN-LAST:event_RPOKeyPressed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if(TabRawat.getSelectedIndex()==1){
            tampil();
        }
    }//GEN-LAST:event_TabRawatMouseClicked

    private void RPSKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RPSKeyPressed
        Valid.pindah2(evt,KeluhanUtama,RPD);
    }//GEN-LAST:event_RPSKeyPressed

    private void TglAsuhanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglAsuhanKeyPressed
        //Valid.pindah(evt,Edukasi,Anamnesis);
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
            
            Valid.MyReportqry("rptCetakPenilaianAwalMedisRalanGeriatri.jasper","report","::[ Laporan Penilaian Awal Medis Rawat Jalan Geriatri ]::",
                "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penilaian_medis_ralan_geriatri.tanggal,"+
                "penilaian_medis_ralan_geriatri.kd_dokter,dokter.nm_dokter,penilaian_medis_ralan_geriatri.anamnesis,penilaian_medis_ralan_geriatri.hubungan,penilaian_medis_ralan_geriatri.keluhan_utama,"+
                "penilaian_medis_ralan_geriatri.rps,penilaian_medis_ralan_geriatri.rpd,penilaian_medis_ralan_geriatri.rpo,penilaian_medis_ralan_geriatri.alergi,penilaian_medis_ralan_geriatri.tulang_belakang,"+
                "penilaian_medis_ralan_geriatri.td,penilaian_medis_ralan_geriatri.nadi,penilaian_medis_ralan_geriatri.suhu,penilaian_medis_ralan_geriatri.rr,penilaian_medis_ralan_geriatri.kondisi_umum,"+
                "penilaian_medis_ralan_geriatri.status_psikologis_gds,penilaian_medis_ralan_geriatri.kondisi_sosial,penilaian_medis_ralan_geriatri.status_kognitif_mmse,penilaian_medis_ralan_geriatri.kepala,"+
                "penilaian_medis_ralan_geriatri.keterangan_kepala,penilaian_medis_ralan_geriatri.thoraks,penilaian_medis_ralan_geriatri.keterangan_thoraks,penilaian_medis_ralan_geriatri.abdomen,"+
                "penilaian_medis_ralan_geriatri.keterangan_abdomen,penilaian_medis_ralan_geriatri.ekstremitas,penilaian_medis_ralan_geriatri.keterangan_ekstremitas,penilaian_medis_ralan_geriatri.Integument_kebersihan,"+
                "penilaian_medis_ralan_geriatri.Integument_warna,penilaian_medis_ralan_geriatri.Integument_kelembaban,penilaian_medis_ralan_geriatri.Integument_gangguan_kulit,penilaian_medis_ralan_geriatri.status_fungsional,"+
                "penilaian_medis_ralan_geriatri.skrining_jatuh,penilaian_medis_ralan_geriatri.status_nutrisi,penilaian_medis_ralan_geriatri.lainnya,penilaian_medis_ralan_geriatri.lab,penilaian_medis_ralan_geriatri.rad,"+
                "penilaian_medis_ralan_geriatri.pemeriksaan,penilaian_medis_ralan_geriatri.diagnosis,penilaian_medis_ralan_geriatri.diagnosis2,penilaian_medis_ralan_geriatri.permasalahan,penilaian_medis_ralan_geriatri.terapi,"+
                "penilaian_medis_ralan_geriatri.tindakan,penilaian_medis_ralan_geriatri.edukasi "+
                "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join penilaian_medis_ralan_geriatri on reg_periksa.no_rawat=penilaian_medis_ralan_geriatri.no_rawat "+
                "inner join dokter on penilaian_medis_ralan_geriatri.kd_dokter=dokter.kd_dokter where penilaian_medis_ralan_geriatri.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'",param);
        }
    }//GEN-LAST:event_MnPenilaianMedisActionPerformed

    private void KondisiUmumKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KondisiUmumKeyPressed
        Valid.pindah2(evt,PosturTulang,Kepala);
    }//GEN-LAST:event_KondisiUmumKeyPressed

    private void RRKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RRKeyPressed
        Valid.pindah(evt,Suhu,PosturTulang);
    }//GEN-LAST:event_RRKeyPressed

    private void TDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TDKeyPressed
       Valid.pindah(evt,Alergi,Nadi);
    }//GEN-LAST:event_TDKeyPressed

    private void SuhuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SuhuKeyPressed
        Valid.pindah(evt,Nadi,RR);
    }//GEN-LAST:event_SuhuKeyPressed

    private void NadiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NadiKeyPressed
        Valid.pindah(evt,TD,Suhu);
    }//GEN-LAST:event_NadiKeyPressed

    private void PosturTulangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PosturTulangKeyPressed
        Valid.pindah(evt,RR,KondisiUmum);
    }//GEN-LAST:event_PosturTulangKeyPressed

    private void LainnyaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LainnyaKeyPressed
        Valid.pindah2(evt,IntegumentGangguan,KondisiSosial);
    }//GEN-LAST:event_LainnyaKeyPressed

    private void KeteranganKepalaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKepalaKeyPressed
        Valid.pindah(evt,Kepala,Thoraks);
    }//GEN-LAST:event_KeteranganKepalaKeyPressed

    private void KepalaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KepalaKeyPressed
        Valid.pindah(evt,KondisiUmum,KeteranganKepala);
    }//GEN-LAST:event_KepalaKeyPressed

    private void ThoraksKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ThoraksKeyPressed
        Valid.pindah(evt,KeteranganKepala,KeteranganThoraks);
    }//GEN-LAST:event_ThoraksKeyPressed

    private void KeteranganThoraksKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganThoraksKeyPressed
        Valid.pindah(evt,Thoraks,Abdomen);
    }//GEN-LAST:event_KeteranganThoraksKeyPressed

    private void AbdomenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AbdomenKeyPressed
        Valid.pindah(evt,KeteranganThoraks,KeteranganAbdomen);
    }//GEN-LAST:event_AbdomenKeyPressed

    private void KeteranganAbdomenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganAbdomenKeyPressed
        Valid.pindah(evt,Abdomen,Ekstremitas);
    }//GEN-LAST:event_KeteranganAbdomenKeyPressed

    private void EkstremitasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EkstremitasKeyPressed
        Valid.pindah(evt,KeteranganAbdomen,KeteranganEkstremitas);
    }//GEN-LAST:event_EkstremitasKeyPressed

    private void KeteranganEkstremitasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganEkstremitasKeyPressed
        Valid.pindah(evt,Ekstremitas,IntegumentKebersihan);
    }//GEN-LAST:event_KeteranganEkstremitasKeyPressed

    private void PsikologisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PsikologisKeyPressed
        Valid.pindah(evt,KondisiSosial,Kognitif);
    }//GEN-LAST:event_PsikologisKeyPressed

    private void KognitifKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KognitifKeyPressed
        Valid.pindah(evt,Psikologis,Nutrisi);
    }//GEN-LAST:event_KognitifKeyPressed

    private void IntegumentKebersihanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_IntegumentKebersihanKeyPressed
        Valid.pindah(evt,KeteranganEkstremitas,IntegumentWarna);
    }//GEN-LAST:event_IntegumentKebersihanKeyPressed

    private void IntegumentWarnaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_IntegumentWarnaKeyPressed
        Valid.pindah(evt,IntegumentKebersihan,IntegumentKelembaban);
    }//GEN-LAST:event_IntegumentWarnaKeyPressed

    private void IntegumentGangguanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_IntegumentGangguanKeyPressed
        Valid.pindah(evt,IntegumentKelembaban,Lainnya);
    }//GEN-LAST:event_IntegumentGangguanKeyPressed

    private void IntegumentKelembabanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_IntegumentKelembabanKeyPressed
        Valid.pindah(evt,IntegumentWarna,IntegumentGangguan);
    }//GEN-LAST:event_IntegumentKelembabanKeyPressed

    private void KondisiSosialKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KondisiSosialKeyPressed
        Valid.pindah2(evt,Lainnya,Psikologis);
    }//GEN-LAST:event_KondisiSosialKeyPressed

    private void FungsionalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FungsionalKeyPressed
        Valid.pindah(evt,RisikoJatuh,Lab);
    }//GEN-LAST:event_FungsionalKeyPressed

    private void RisikoJatuhKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RisikoJatuhKeyPressed
        Valid.pindah(evt,Nutrisi,Fungsional);
    }//GEN-LAST:event_RisikoJatuhKeyPressed

    private void NutrisiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NutrisiKeyPressed
        Valid.pindah(evt,Kognitif,RisikoJatuh);
    }//GEN-LAST:event_NutrisiKeyPressed

    private void PenunjangLainKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenunjangLainKeyPressed
        Valid.pindah2(evt,Edukasi,Diagnosis);
    }//GEN-LAST:event_PenunjangLainKeyPressed

    private void RadKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RadKeyPressed
        Valid.pindah2(evt,Lab,PenunjangLain);
    }//GEN-LAST:event_RadKeyPressed

    private void LabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LabKeyPressed
        Valid.pindah2(evt,Fungsional,Rad);
    }//GEN-LAST:event_LabKeyPressed

    private void Diagnosis2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Diagnosis2KeyPressed
        Valid.pindah2(evt,Diagnosis,Permasalahan);
    }//GEN-LAST:event_Diagnosis2KeyPressed

    private void DiagnosisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosisKeyPressed
        Valid.pindah2(evt,PenunjangLain,Diagnosis2);
    }//GEN-LAST:event_DiagnosisKeyPressed

    private void PermasalahanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PermasalahanKeyPressed
        Valid.pindah2(evt,Diagnosis2,Terapi);
    }//GEN-LAST:event_PermasalahanKeyPressed

    private void TerapiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TerapiKeyPressed
        Valid.pindah2(evt,Permasalahan,Tindakan);
    }//GEN-LAST:event_TerapiKeyPressed

    private void TindakanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TindakanKeyPressed
        Valid.pindah2(evt,Terapi,Edukasi);
    }//GEN-LAST:event_TindakanKeyPressed

    private void EdukasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EdukasiKeyPressed
        Valid.pindah2(evt,Tindakan,BtnSimpan);
    }//GEN-LAST:event_EdukasiKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMPenilaianAwalMedisRalanGeriatri dialog = new RMPenilaianAwalMedisRalanGeriatri(new javax.swing.JFrame(), true);
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
    private widget.TextBox Alergi;
    private widget.ComboBox Anamnesis;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnDokter;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.TextArea Diagnosis;
    private widget.TextArea Diagnosis2;
    private widget.TextArea Edukasi;
    private widget.ComboBox Ekstremitas;
    private widget.PanelBiasa FormInput;
    private widget.ComboBox Fungsional;
    private widget.TextBox Hubungan;
    private widget.ComboBox IntegumentGangguan;
    private widget.ComboBox IntegumentKebersihan;
    private widget.ComboBox IntegumentKelembaban;
    private widget.ComboBox IntegumentWarna;
    private widget.TextBox Jk;
    private widget.TextBox KdDokter;
    private widget.TextArea KeluhanUtama;
    private widget.ComboBox Kepala;
    private widget.TextBox KeteranganAbdomen;
    private widget.TextBox KeteranganEkstremitas;
    private widget.TextBox KeteranganKepala;
    private widget.TextBox KeteranganThoraks;
    private widget.ComboBox Kognitif;
    private widget.TextArea KondisiSosial;
    private widget.TextArea KondisiUmum;
    private widget.Label LCount;
    private widget.TextArea Lab;
    private widget.TextArea Lainnya;
    private widget.editorpane LoadHTML;
    private javax.swing.JMenuItem MnPenilaianMedis;
    private widget.TextBox Nadi;
    private widget.TextBox NmDokter;
    private widget.ComboBox Nutrisi;
    private widget.TextArea PenunjangLain;
    private widget.TextArea Permasalahan;
    private widget.ComboBox PosturTulang;
    private widget.ComboBox Psikologis;
    private widget.TextArea RPD;
    private widget.TextArea RPO;
    private widget.TextArea RPS;
    private widget.TextBox RR;
    private widget.TextArea Rad;
    private widget.ComboBox RisikoJatuh;
    private widget.ScrollPane Scroll;
    private widget.TextBox Suhu;
    private widget.TextBox TCari;
    private widget.TextBox TD;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private javax.swing.JTabbedPane TabRawat;
    private widget.TextArea Terapi;
    private widget.Tanggal TglAsuhan;
    private widget.TextBox TglLahir;
    private widget.ComboBox Thoraks;
    private widget.TextArea Tindakan;
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
    private widget.Label jLabel112;
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
    private widget.Label jLabel37;
    private widget.Label jLabel38;
    private widget.Label jLabel39;
    private widget.Label jLabel40;
    private widget.Label jLabel41;
    private widget.Label jLabel45;
    private widget.Label jLabel46;
    private widget.Label jLabel47;
    private widget.Label jLabel49;
    private widget.Label jLabel51;
    private widget.Label jLabel52;
    private widget.Label jLabel53;
    private widget.Label jLabel54;
    private widget.Label jLabel55;
    private widget.Label jLabel56;
    private widget.Label jLabel57;
    private widget.Label jLabel58;
    private widget.Label jLabel59;
    private widget.Label jLabel6;
    private widget.Label jLabel60;
    private widget.Label jLabel61;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel80;
    private widget.Label jLabel81;
    private widget.Label jLabel82;
    private widget.Label jLabel85;
    private widget.Label jLabel9;
    private widget.Label jLabel94;
    private widget.Label jLabel95;
    private widget.Label jLabel99;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator16;
    private javax.swing.JSeparator jSeparator17;
    private javax.swing.JSeparator jSeparator18;
    private widget.Label label11;
    private widget.Label label14;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollInput;
    private widget.ScrollPane scrollPane1;
    private widget.ScrollPane scrollPane10;
    private widget.ScrollPane scrollPane12;
    private widget.ScrollPane scrollPane14;
    private widget.ScrollPane scrollPane15;
    private widget.ScrollPane scrollPane16;
    private widget.ScrollPane scrollPane17;
    private widget.ScrollPane scrollPane19;
    private widget.ScrollPane scrollPane2;
    private widget.ScrollPane scrollPane20;
    private widget.ScrollPane scrollPane3;
    private widget.ScrollPane scrollPane4;
    private widget.ScrollPane scrollPane5;
    private widget.ScrollPane scrollPane6;
    private widget.ScrollPane scrollPane7;
    private widget.ScrollPane scrollPane9;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            if(TCari.getText().trim().equals("")){
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penilaian_medis_ralan_geriatri.tanggal,"+
                        "penilaian_medis_ralan_geriatri.kd_dokter,dokter.nm_dokter,penilaian_medis_ralan_geriatri.anamnesis,penilaian_medis_ralan_geriatri.hubungan,penilaian_medis_ralan_geriatri.keluhan_utama,"+
                        "penilaian_medis_ralan_geriatri.rps,penilaian_medis_ralan_geriatri.rpd,penilaian_medis_ralan_geriatri.rpo,penilaian_medis_ralan_geriatri.alergi,penilaian_medis_ralan_geriatri.tulang_belakang,"+
                        "penilaian_medis_ralan_geriatri.td,penilaian_medis_ralan_geriatri.nadi,penilaian_medis_ralan_geriatri.suhu,penilaian_medis_ralan_geriatri.rr,penilaian_medis_ralan_geriatri.kondisi_umum,"+
                        "penilaian_medis_ralan_geriatri.status_psikologis_gds,penilaian_medis_ralan_geriatri.kondisi_sosial,penilaian_medis_ralan_geriatri.status_kognitif_mmse,penilaian_medis_ralan_geriatri.kepala,"+
                        "penilaian_medis_ralan_geriatri.keterangan_kepala,penilaian_medis_ralan_geriatri.thoraks,penilaian_medis_ralan_geriatri.keterangan_thoraks,penilaian_medis_ralan_geriatri.abdomen,"+
                        "penilaian_medis_ralan_geriatri.keterangan_abdomen,penilaian_medis_ralan_geriatri.ekstremitas,penilaian_medis_ralan_geriatri.keterangan_ekstremitas,penilaian_medis_ralan_geriatri.Integument_kebersihan,"+
                        "penilaian_medis_ralan_geriatri.Integument_warna,penilaian_medis_ralan_geriatri.Integument_kelembaban,penilaian_medis_ralan_geriatri.Integument_gangguan_kulit,penilaian_medis_ralan_geriatri.status_fungsional,"+
                        "penilaian_medis_ralan_geriatri.skrining_jatuh,penilaian_medis_ralan_geriatri.status_nutrisi,penilaian_medis_ralan_geriatri.lainnya,penilaian_medis_ralan_geriatri.lab,penilaian_medis_ralan_geriatri.rad,"+
                        "penilaian_medis_ralan_geriatri.pemeriksaan,penilaian_medis_ralan_geriatri.diagnosis,penilaian_medis_ralan_geriatri.diagnosis2,penilaian_medis_ralan_geriatri.permasalahan,penilaian_medis_ralan_geriatri.terapi,"+
                        "penilaian_medis_ralan_geriatri.tindakan,penilaian_medis_ralan_geriatri.edukasi "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join penilaian_medis_ralan_geriatri on reg_periksa.no_rawat=penilaian_medis_ralan_geriatri.no_rawat "+
                        "inner join dokter on penilaian_medis_ralan_geriatri.kd_dokter=dokter.kd_dokter where "+
                        "penilaian_medis_ralan_geriatri.tanggal between ? and ? order by penilaian_medis_ralan_geriatri.tanggal");
            }else{
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penilaian_medis_ralan_geriatri.tanggal,"+
                        "penilaian_medis_ralan_geriatri.kd_dokter,dokter.nm_dokter,penilaian_medis_ralan_geriatri.anamnesis,penilaian_medis_ralan_geriatri.hubungan,penilaian_medis_ralan_geriatri.keluhan_utama,"+
                        "penilaian_medis_ralan_geriatri.rps,penilaian_medis_ralan_geriatri.rpd,penilaian_medis_ralan_geriatri.rpo,penilaian_medis_ralan_geriatri.alergi,penilaian_medis_ralan_geriatri.tulang_belakang,"+
                        "penilaian_medis_ralan_geriatri.td,penilaian_medis_ralan_geriatri.nadi,penilaian_medis_ralan_geriatri.suhu,penilaian_medis_ralan_geriatri.rr,penilaian_medis_ralan_geriatri.kondisi_umum,"+
                        "penilaian_medis_ralan_geriatri.status_psikologis_gds,penilaian_medis_ralan_geriatri.kondisi_sosial,penilaian_medis_ralan_geriatri.status_kognitif_mmse,penilaian_medis_ralan_geriatri.kepala,"+
                        "penilaian_medis_ralan_geriatri.keterangan_kepala,penilaian_medis_ralan_geriatri.thoraks,penilaian_medis_ralan_geriatri.keterangan_thoraks,penilaian_medis_ralan_geriatri.abdomen,"+
                        "penilaian_medis_ralan_geriatri.keterangan_abdomen,penilaian_medis_ralan_geriatri.ekstremitas,penilaian_medis_ralan_geriatri.keterangan_ekstremitas,penilaian_medis_ralan_geriatri.Integument_kebersihan,"+
                        "penilaian_medis_ralan_geriatri.Integument_warna,penilaian_medis_ralan_geriatri.Integument_kelembaban,penilaian_medis_ralan_geriatri.Integument_gangguan_kulit,penilaian_medis_ralan_geriatri.status_fungsional,"+
                        "penilaian_medis_ralan_geriatri.skrining_jatuh,penilaian_medis_ralan_geriatri.status_nutrisi,penilaian_medis_ralan_geriatri.lainnya,penilaian_medis_ralan_geriatri.lab,penilaian_medis_ralan_geriatri.rad,"+
                        "penilaian_medis_ralan_geriatri.pemeriksaan,penilaian_medis_ralan_geriatri.diagnosis,penilaian_medis_ralan_geriatri.diagnosis2,penilaian_medis_ralan_geriatri.permasalahan,penilaian_medis_ralan_geriatri.terapi,"+
                        "penilaian_medis_ralan_geriatri.tindakan,penilaian_medis_ralan_geriatri.edukasi "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join penilaian_medis_ralan_geriatri on reg_periksa.no_rawat=penilaian_medis_ralan_geriatri.no_rawat "+
                        "inner join dokter on penilaian_medis_ralan_geriatri.kd_dokter=dokter.kd_dokter where "+
                        "penilaian_medis_ralan_geriatri.tanggal between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or "+
                        "penilaian_medis_ralan_geriatri.kd_dokter like ? or dokter.nm_dokter like ?) order by penilaian_medis_ralan_geriatri.tanggal");
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
                        rs.getString("anamnesis"),rs.getString("hubungan"),rs.getString("keluhan_utama"),rs.getString("rps"),rs.getString("rpd"),rs.getString("rpo"),rs.getString("alergi"),rs.getString("kondisi_umum"),
                        rs.getString("td"),rs.getString("nadi"),rs.getString("suhu"),rs.getString("rr"),rs.getString("tulang_belakang"),rs.getString("kepala"),rs.getString("keterangan_kepala"),rs.getString("thoraks"),
                        rs.getString("keterangan_thoraks"),rs.getString("abdomen"),rs.getString("keterangan_abdomen"),rs.getString("ekstremitas"),rs.getString("keterangan_ekstremitas"),rs.getString("Integument_kebersihan"),
                        rs.getString("Integument_warna"),rs.getString("Integument_kelembaban"),rs.getString("Integument_gangguan_kulit"),rs.getString("lainnya"),rs.getString("kondisi_sosial"),rs.getString("status_psikologis_gds"),
                        rs.getString("status_kognitif_mmse"),rs.getString("status_nutrisi"),rs.getString("skrining_jatuh"),rs.getString("status_fungsional"),rs.getString("lab"),rs.getString("rad"),rs.getString("pemeriksaan"),
                        rs.getString("diagnosis"),rs.getString("diagnosis2"),rs.getString("permasalahan"),rs.getString("terapi"),rs.getString("tindakan"),rs.getString("edukasi")
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
        RPO.setText("");
        Alergi.setText("");
        KondisiUmum.setText("");
        TD.setText("");
        Nadi.setText("");
        Suhu.setText("");
        RR.setText("");
        PosturTulang.setSelectedIndex(0);
        Kepala.setSelectedIndex(0);
        Thoraks.setSelectedIndex(0);
        Abdomen.setSelectedIndex(0);
        Ekstremitas.setSelectedIndex(0);
        KeteranganKepala.setText("");
        KeteranganThoraks.setText("");
        KeteranganAbdomen.setText("");
        KeteranganEkstremitas.setText("");
        KondisiSosial.setText("");
        Psikologis.setSelectedIndex(0);
        Kognitif.setSelectedIndex(0);
        Nutrisi.setSelectedIndex(0);
        RisikoJatuh.setSelectedIndex(0);
        Fungsional.setSelectedIndex(0);
        IntegumentKebersihan.setSelectedIndex(0);
        IntegumentWarna.setSelectedIndex(0);
        IntegumentKelembaban.setSelectedIndex(0);
        IntegumentGangguan.setSelectedIndex(0);
        Lainnya.setText("");
        Lab.setText("");
        Rad.setText("");
        PenunjangLain.setText("");
        Diagnosis.setText("");
        Diagnosis2.setText("");
        Permasalahan.setText("");
        Terapi.setText("");
        Tindakan.setText("");
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
            Anamnesis.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            Hubungan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            KeluhanUtama.setText(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            RPS.setText(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            RPD.setText(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            RPO.setText(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            Alergi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            KondisiUmum.setText(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
            TD.setText(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            Nadi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());
            Suhu.setText(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
            RR.setText(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
            PosturTulang.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString());
            Kepala.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString());
            KeteranganKepala.setText(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString());
            Thoraks.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString());
            KeteranganThoraks.setText(tbObat.getValueAt(tbObat.getSelectedRow(),24).toString());
            Abdomen.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),25).toString());
            KeteranganAbdomen.setText(tbObat.getValueAt(tbObat.getSelectedRow(),26).toString());
            Ekstremitas.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),27).toString());
            KeteranganEkstremitas.setText(tbObat.getValueAt(tbObat.getSelectedRow(),28).toString());
            IntegumentKebersihan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),29).toString());
            IntegumentWarna.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),30).toString());
            IntegumentKelembaban.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),31).toString());
            IntegumentGangguan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),32).toString());
            Lainnya.setText(tbObat.getValueAt(tbObat.getSelectedRow(),33).toString());
            KondisiSosial.setText(tbObat.getValueAt(tbObat.getSelectedRow(),34).toString());
            Psikologis.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),35).toString());
            Kognitif.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),36).toString());
            Nutrisi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),37).toString());
            RisikoJatuh.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),38).toString());
            Fungsional.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),39).toString());
            Lab.setText(tbObat.getValueAt(tbObat.getSelectedRow(),40).toString());
            Rad.setText(tbObat.getValueAt(tbObat.getSelectedRow(),41).toString());
            PenunjangLain.setText(tbObat.getValueAt(tbObat.getSelectedRow(),42).toString());
            Diagnosis.setText(tbObat.getValueAt(tbObat.getSelectedRow(),43).toString());
            Diagnosis2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),44).toString());
            Permasalahan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),45).toString());
            Terapi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),46).toString());
            Tindakan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),47).toString());
            Edukasi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),48).toString());
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
        BtnSimpan.setEnabled(akses.getpenilaian_awal_medis_ralan_penyakit_dalam());
        BtnHapus.setEnabled(akses.getpenilaian_awal_medis_ralan_penyakit_dalam());
        BtnEdit.setEnabled(akses.getpenilaian_awal_medis_ralan_penyakit_dalam());
        BtnEdit.setEnabled(akses.getpenilaian_awal_medis_ralan_penyakit_dalam());
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
        if(Sequel.queryu2tf("delete from penilaian_medis_ralan_geriatri where no_rawat=?",1,new String[]{
            tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
        })==true){
            tampil();
            TabRawat.setSelectedIndex(1);
        }else{
            JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
        }
    }

    private void ganti() {
        if(Sequel.mengedittf("penilaian_medis_ralan_geriatri","no_rawat=?","no_rawat=?,tanggal=?,kd_dokter=?,anamnesis=?,hubungan=?,keluhan_utama=?,rps=?,rpd=?,rpo=?,alergi=?,tulang_belakang=?,td=?,nadi=?,suhu=?,rr=?,kondisi_umum=?,"+
                "status_psikologis_gds=?,kondisi_sosial=?,status_kognitif_mmse=?,kepala=?,keterangan_kepala=?,thoraks=?,keterangan_thoraks=?,abdomen=?,keterangan_abdomen=?,ekstremitas=?,keterangan_ekstremitas=?,Integument_kebersihan=?,"+
                "Integument_warna=?,Integument_kelembaban=?,Integument_gangguan_kulit=?,status_fungsional=?,skrining_jatuh=?,status_nutrisi=?,lainnya=?,lab=?,rad=?,pemeriksaan=?,diagnosis=?,diagnosis2=?,permasalahan=?,terapi=?,"+
                "tindakan=?,edukasi=?",45,new String[]{
                TNoRw.getText(),Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),KdDokter.getText(),Anamnesis.getSelectedItem().toString(),Hubungan.getText(),
                KeluhanUtama.getText(),RPS.getText(),RPD.getText(),RPO.getText(),Alergi.getText(),PosturTulang.getSelectedItem().toString(),TD.getText(),Nadi.getText(),Suhu.getText(),RR.getText(),KondisiUmum.getText(),
                Psikologis.getSelectedItem().toString(),KondisiSosial.getText(),Kognitif.getSelectedItem().toString(),Kepala.getSelectedItem().toString(),KeteranganKepala.getText(),Thoraks.getSelectedItem().toString(),
                KeteranganThoraks.getText(),Abdomen.getSelectedItem().toString(),KeteranganAbdomen.getText(),Ekstremitas.getSelectedItem().toString(),KeteranganEkstremitas.getText(),IntegumentKebersihan.getSelectedItem().toString(),
                IntegumentWarna.getSelectedItem().toString(),IntegumentKelembaban.getSelectedItem().toString(),IntegumentGangguan.getSelectedItem().toString(),Fungsional.getSelectedItem().toString(),
                RisikoJatuh.getSelectedItem().toString(),Nutrisi.getSelectedItem().toString(),Lainnya.getText(),Lab.getText(),Rad.getText(),PenunjangLain.getText(),Diagnosis.getText(),Diagnosis2.getText(),
                Permasalahan.getText(),Terapi.getText(),Tindakan.getText(),Edukasi.getText(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
            })==true){
               tampil();
               emptTeks();
               TabRawat.setSelectedIndex(1);
        }
    }
}

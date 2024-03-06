/*
 * Kontribusi RSUD Prembun
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
public final class RMPenilaianAwalMedisRalanBedahMulut extends javax.swing.JDialog {
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
    public RMPenilaianAwalMedisRalanBedahMulut(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        tabMode=new DefaultTableModel(null,new Object[]{
                "No.Rawat","No.RM","Nama Pasien","Tgl.Lahir","J.K.","Kode Dokter","Nama Dokter","Tanggal","Anamnesis","Hubungan","Keluhan Utama", 
                "Riwayat Penyakit Sekarang","Riwayat Penyakit Keluarga","Alergi","Keadaan Umum","Kesadaran","Skala Nyeri","Tensi(mmHg)","Nadi(x/menit)",
                "Suhu(°C)","RR(x/menit)","BB(Kg)","TB(Cm)","Status Nutrisi","Kulit","Keterangan Kulit","Kepala","Keterangan Kepala","Mata","Keterangan Mata", 
                "Leher","Keterangan Leher","Kelenjar","Keterangan Kelenjar","Dada","Keterangan Dada","Perut","Keterangan Perut","Ekstremitas",
                "Keterangan Ekstremitas","Wajah","Intra","Gigi Geligi","Laborat","Radiologi","Penunjang","Asesmen Kerja","Asesmen Banding", 
                "Permasalahan","Terapi","Tindakan","Edukasi"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        
        tbObat.setModel(tabMode);
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 52; i++) {
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
                column.setPreferredWidth(55);
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
                column.setPreferredWidth(200);
            }else if(i==11){
                column.setPreferredWidth(200);
            }else if(i==12){
                column.setPreferredWidth(150);
            }else if(i==13){
                column.setPreferredWidth(100);
            }else if(i==14){
                column.setPreferredWidth(85);
            }else if(i==15){
                column.setPreferredWidth(80);
            }else if(i==16){
                column.setPreferredWidth(115);
            }else if(i==17){
                column.setPreferredWidth(73);
            }else if(i==18){
                column.setPreferredWidth(75);
            }else if(i==19){
                column.setPreferredWidth(52);
            }else if(i==20){
                column.setPreferredWidth(70);
            }else if(i==21){
                column.setPreferredWidth(45);
            }else if(i==22){
                column.setPreferredWidth(47);
            }else if(i==23){
                column.setPreferredWidth(120);
            }else if(i==24){
                column.setPreferredWidth(40);
            }else if(i==25){
                column.setPreferredWidth(130);
            }else if(i==26){
                column.setPreferredWidth(40);
            }else if(i==27){
                column.setPreferredWidth(130);
            }else if(i==28){
                column.setPreferredWidth(40);
            }else if(i==29){
                column.setPreferredWidth(130);
            }else if(i==30){
                column.setPreferredWidth(40);
            }else if(i==31){
                column.setPreferredWidth(130);
            }else if(i==32){
                column.setPreferredWidth(50);
            }else if(i==33){
                column.setPreferredWidth(130);
            }else if(i==34){
                column.setPreferredWidth(40);
            }else if(i==35){
                column.setPreferredWidth(130);
            }else if(i==36){
                column.setPreferredWidth(40);
            }else if(i==37){
                column.setPreferredWidth(130);
            }else if(i==38){
                column.setPreferredWidth(65);
            }else if(i==39){
                column.setPreferredWidth(130);
            }else if(i==40){
                column.setPreferredWidth(300);
            }else if(i==41){
                column.setPreferredWidth(300);
            }else if(i==42){
                column.setPreferredWidth(300);
            }else if(i==43){
                column.setPreferredWidth(200);
            }else if(i==44){
                column.setPreferredWidth(200);
            }else if(i==45){
                column.setPreferredWidth(200);
            }else if(i==46){
                column.setPreferredWidth(150);
            }else if(i==47){
                column.setPreferredWidth(150);
            }else if(i==48){
                column.setPreferredWidth(200);
            }else if(i==49){
                column.setPreferredWidth(200);
            }else if(i==50){
                column.setPreferredWidth(200);
            }else if(i==51){
                column.setPreferredWidth(200);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());
        
        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        Hubungan.setDocument(new batasInput((int)30).getKata(Hubungan));
        KeluhanUtama.setDocument(new batasInput((int)2000).getKata(KeluhanUtama));
        RPS.setDocument(new batasInput((int)2000).getKata(RPS));
        RPK.setDocument(new batasInput((int)1000).getKata(RPK));
        Alergi.setDocument(new batasInput((int)50).getKata(Alergi));
        TD.setDocument(new batasInput((int)8).getKata(TD));
        Nadi.setDocument(new batasInput((int)5).getKata(Nadi));
        Suhu.setDocument(new batasInput((int)5).getKata(Suhu));
        RR.setDocument(new batasInput((int)5).getKata(RR));
        BB.setDocument(new batasInput((int)5).getKata(BB));
        TB.setDocument(new batasInput((int)5).getKata(TB));
        StatusNutrisi.setDocument(new batasInput((int)50).getKata(StatusNutrisi));
        KeteranganKulit.setDocument(new batasInput((int)30).getKata(KeteranganKulit));
        KeteranganKepala.setDocument(new batasInput((int)30).getKata(KeteranganKepala));
        KeteranganMata.setDocument(new batasInput((int)30).getKata(KeteranganMata));
        KeteranganLeher.setDocument(new batasInput((int)30).getKata(KeteranganLeher));
        KeteranganKelenjar.setDocument(new batasInput((int)30).getKata(KeteranganKelenjar));
        KeteranganDada.setDocument(new batasInput((int)30).getKata(KeteranganDada));
        KeteranganPerut.setDocument(new batasInput((int)30).getKata(KeteranganPerut));
        KeteranganEkstrimitas.setDocument(new batasInput((int)30).getKata(KeteranganEkstrimitas));
        Wajah.setDocument(new batasInput((int)1000).getKata(Wajah));
        IntraOral.setDocument(new batasInput((int)1000).getKata(IntraOral));
        GigiGeligi.setDocument(new batasInput((int)1000).getKata(GigiGeligi));
        Laborat.setDocument(new batasInput((int)300).getKata(Laborat));
        Radiologi.setDocument(new batasInput((int)300).getKata(Radiologi));
        Penunjang.setDocument(new batasInput((int)300).getKata(Penunjang));
        AsesmenKerja.setDocument(new batasInput((int)500).getKata(AsesmenKerja));
        AsesmenBanding.setDocument(new batasInput((int)500).getKata(AsesmenBanding));
        Permasalahan.setDocument(new batasInput((int)1000).getKata(Permasalahan));
        Terapi.setDocument(new batasInput((int)1000).getKata(Terapi));
        Tindakan.setDocument(new batasInput((int)1000).getKata(Tindakan));
        Edukasi.setDocument(new batasInput((int)1000).getKata(Edukasi));
        
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
        jLabel37 = new widget.Label();
        Alergi = new widget.TextBox();
        Anamnesis = new widget.ComboBox();
        scrollPane1 = new widget.ScrollPane();
        KeluhanUtama = new widget.TextArea();
        jLabel30 = new widget.Label();
        scrollPane4 = new widget.ScrollPane();
        RPK = new widget.TextArea();
        jLabel28 = new widget.Label();
        TB = new widget.TextBox();
        jLabel94 = new widget.Label();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel38 = new widget.Label();
        Hubungan = new widget.TextBox();
        scrollPane7 = new widget.ScrollPane();
        RPS = new widget.TextArea();
        jSeparator12 = new javax.swing.JSeparator();
        jLabel40 = new widget.Label();
        Kesadaran = new widget.ComboBox();
        jLabel29 = new widget.Label();
        StatusNutrisi = new widget.TextBox();
        Kepala = new widget.ComboBox();
        jLabel44 = new widget.Label();
        Kelenjar = new widget.ComboBox();
        jLabel45 = new widget.Label();
        Dada = new widget.ComboBox();
        jLabel46 = new widget.Label();
        Kulit = new widget.ComboBox();
        jLabel49 = new widget.Label();
        Mata = new widget.ComboBox();
        jLabel50 = new widget.Label();
        Leher = new widget.ComboBox();
        jLabel51 = new widget.Label();
        Ekstremitas = new widget.ComboBox();
        jLabel99 = new widget.Label();
        jLabel101 = new widget.Label();
        label11 = new widget.Label();
        TglAsuhan = new widget.Tanggal();
        jLabel41 = new widget.Label();
        Nyeri = new widget.ComboBox();
        jLabel95 = new widget.Label();
        jLabel47 = new widget.Label();
        Perut = new widget.ComboBox();
        jLabel14 = new widget.Label();
        jLabel42 = new widget.Label();
        Keadaan = new widget.ComboBox();
        jLabel113 = new widget.Label();
        jLabel115 = new widget.Label();
        jLabel33 = new widget.Label();
        jLabel34 = new widget.Label();
        jLabel32 = new widget.Label();
        jLabel36 = new widget.Label();
        jLabel43 = new widget.Label();
        jLabel48 = new widget.Label();
        jSeparator17 = new javax.swing.JSeparator();
        jLabel24 = new widget.Label();
        KeteranganEkstrimitas = new widget.TextBox();
        KeteranganKulit = new widget.TextBox();
        KeteranganKepala = new widget.TextBox();
        KeteranganMata = new widget.TextBox();
        KeteranganLeher = new widget.TextBox();
        KeteranganKelenjar = new widget.TextBox();
        KeteranganDada = new widget.TextBox();
        KeteranganPerut = new widget.TextBox();
        jLabel52 = new widget.Label();
        jSeparator18 = new javax.swing.JSeparator();
        scrollPane5 = new widget.ScrollPane();
        Wajah = new widget.TextArea();
        PanelWall1 = new usu.widget.glass.PanelGlass();
        PanelWall2 = new usu.widget.glass.PanelGlass();
        jSeparator35 = new javax.swing.JSeparator();
        jLabel116 = new widget.Label();
        scrollPane6 = new widget.ScrollPane();
        IntraOral = new widget.TextArea();
        jSeparator36 = new javax.swing.JSeparator();
        PanelWall3 = new usu.widget.glass.PanelGlass();
        jLabel117 = new widget.Label();
        scrollPane8 = new widget.ScrollPane();
        GigiGeligi = new widget.TextArea();
        PanelWall4 = new usu.widget.glass.PanelGlass();
        jSeparator19 = new javax.swing.JSeparator();
        jLabel112 = new widget.Label();
        jLabel80 = new widget.Label();
        jLabel81 = new widget.Label();
        scrollPane15 = new widget.ScrollPane();
        Penunjang = new widget.TextArea();
        scrollPane9 = new widget.ScrollPane();
        Laborat = new widget.TextArea();
        scrollPane10 = new widget.ScrollPane();
        Radiologi = new widget.TextArea();
        jSeparator20 = new javax.swing.JSeparator();
        jLabel106 = new widget.Label();
        jLabel85 = new widget.Label();
        jLabel82 = new widget.Label();
        scrollPane16 = new widget.ScrollPane();
        AsesmenBanding = new widget.TextArea();
        scrollPane19 = new widget.ScrollPane();
        AsesmenKerja = new widget.TextArea();
        jSeparator21 = new javax.swing.JSeparator();
        jLabel107 = new widget.Label();
        jLabel114 = new widget.Label();
        jLabel108 = new widget.Label();
        scrollPane20 = new widget.ScrollPane();
        Permasalahan = new widget.TextArea();
        scrollPane17 = new widget.ScrollPane();
        Terapi = new widget.TextArea();
        jLabel118 = new widget.Label();
        scrollPane21 = new widget.ScrollPane();
        Tindakan = new widget.TextArea();
        jSeparator22 = new javax.swing.JSeparator();
        jLabel109 = new widget.Label();
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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Penilaian Awal Medis Rawat Jalan Bedah Mulut ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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
        internalFrame2.setPreferredSize(new java.awt.Dimension(102, 650));
        internalFrame2.setLayout(new java.awt.BorderLayout(1, 1));

        scrollInput.setName("scrollInput"); // NOI18N
        scrollInput.setPreferredSize(new java.awt.Dimension(102, 557));

        FormInput.setBackground(new java.awt.Color(255, 255, 255));
        FormInput.setBorder(null);
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(870, 1623));
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
        jLabel12.setBounds(417, 240, 30, 23);

        BB.setFocusTraversalPolicyProvider(true);
        BB.setName("BB"); // NOI18N
        BB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BBKeyPressed(evt);
            }
        });
        FormInput.add(BB);
        BB.setBounds(451, 240, 45, 23);

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel13.setText("Cm");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(600, 240, 30, 23);

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel16.setText("x/menit");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput.add(jLabel16);
        jLabel16.setBounds(124, 240, 50, 23);

        Nadi.setFocusTraversalPolicyProvider(true);
        Nadi.setName("Nadi"); // NOI18N
        Nadi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NadiKeyPressed(evt);
            }
        });
        FormInput.add(Nadi);
        Nadi.setBounds(76, 240, 45, 23);

        jLabel17.setText(":");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(0, 240, 72, 23);

        jLabel18.setText("Suhu :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(165, 240, 50, 23);

        Suhu.setFocusTraversalPolicyProvider(true);
        Suhu.setName("Suhu"); // NOI18N
        Suhu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SuhuKeyPressed(evt);
            }
        });
        FormInput.add(Suhu);
        Suhu.setBounds(219, 240, 45, 23);

        jLabel22.setText("TD :");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(697, 210, 40, 23);

        TD.setFocusTraversalPolicyProvider(true);
        TD.setName("TD"); // NOI18N
        TD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TDKeyPressed(evt);
            }
        });
        FormInput.add(TD);
        TD.setBounds(741, 210, 76, 23);

        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel20.setText("°C");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(267, 240, 30, 23);

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel23.setText("mmHg");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(820, 210, 50, 23);

        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel25.setText("x/menit");
        jLabel25.setName("jLabel25"); // NOI18N
        FormInput.add(jLabel25);
        jLabel25.setBounds(372, 240, 50, 23);

        RR.setFocusTraversalPolicyProvider(true);
        RR.setName("RR"); // NOI18N
        RR.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RRKeyPressed(evt);
            }
        });
        FormInput.add(RR);
        RR.setBounds(324, 240, 45, 23);

        jLabel26.setText("RR :");
        jLabel26.setName("jLabel26"); // NOI18N
        FormInput.add(jLabel26);
        jLabel26.setBounds(280, 240, 40, 23);

        jLabel37.setText("Riwayat Alergi :");
        jLabel37.setName("jLabel37"); // NOI18N
        FormInput.add(jLabel37);
        jLabel37.setBounds(440, 160, 150, 23);

        Alergi.setFocusTraversalPolicyProvider(true);
        Alergi.setName("Alergi"); // NOI18N
        Alergi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlergiKeyPressed(evt);
            }
        });
        FormInput.add(Alergi);
        Alergi.setBounds(594, 160, 260, 23);

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
        scrollPane1.setBounds(129, 90, 310, 43);

        jLabel30.setText("Riwayat Penyakit Sekarang :");
        jLabel30.setName("jLabel30"); // NOI18N
        FormInput.add(jLabel30);
        jLabel30.setBounds(440, 90, 150, 23);

        scrollPane4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane4.setName("scrollPane4"); // NOI18N

        RPK.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        RPK.setColumns(20);
        RPK.setRows(5);
        RPK.setName("RPK"); // NOI18N
        RPK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RPKKeyPressed(evt);
            }
        });
        scrollPane4.setViewportView(RPK);

        FormInput.add(scrollPane4);
        scrollPane4.setBounds(184, 140, 255, 43);

        jLabel28.setText("TB :");
        jLabel28.setName("jLabel28"); // NOI18N
        FormInput.add(jLabel28);
        jLabel28.setBounds(518, 240, 30, 23);

        TB.setFocusTraversalPolicyProvider(true);
        TB.setName("TB"); // NOI18N
        TB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TBKeyPressed(evt);
            }
        });
        FormInput.add(TB);
        TB.setBounds(552, 240, 45, 23);

        jLabel94.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel94.setText("III. STATUS KELAINAN");
        jLabel94.setName("jLabel94"); // NOI18N
        FormInput.add(jLabel94);
        jLabel94.setBounds(10, 270, 180, 23);

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
        scrollPane7.setBounds(594, 90, 260, 63);

        jSeparator12.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator12.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator12.setName("jSeparator12"); // NOI18N
        FormInput.add(jSeparator12);
        jSeparator12.setBounds(0, 190, 880, 1);

        jLabel40.setText(":");
        jLabel40.setName("jLabel40"); // NOI18N
        FormInput.add(jLabel40);
        jLabel40.setBounds(0, 320, 84, 23);

        Kesadaran.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Compos Mentis", "Apatis", "Somnolen", "Sopor", "Koma" }));
        Kesadaran.setName("Kesadaran"); // NOI18N
        Kesadaran.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KesadaranKeyPressed(evt);
            }
        });
        FormInput.add(Kesadaran);
        Kesadaran.setBounds(312, 210, 125, 23);

        jLabel29.setText("Status Nutrisi :");
        jLabel29.setName("jLabel29"); // NOI18N
        FormInput.add(jLabel29);
        jLabel29.setBounds(620, 240, 90, 23);

        StatusNutrisi.setFocusTraversalPolicyProvider(true);
        StatusNutrisi.setName("StatusNutrisi"); // NOI18N
        StatusNutrisi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                StatusNutrisiKeyPressed(evt);
            }
        });
        FormInput.add(StatusNutrisi);
        StatusNutrisi.setBounds(714, 240, 140, 23);

        Kepala.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Kepala.setName("Kepala"); // NOI18N
        Kepala.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KepalaKeyPressed(evt);
            }
        });
        FormInput.add(Kepala);
        Kepala.setBounds(88, 320, 80, 23);

        jLabel44.setText("Kelenjar Limfe :");
        jLabel44.setName("jLabel44"); // NOI18N
        FormInput.add(jLabel44);
        jLabel44.setBounds(442, 290, 90, 23);

        Kelenjar.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Kelenjar.setName("Kelenjar"); // NOI18N
        Kelenjar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KelenjarKeyPressed(evt);
            }
        });
        FormInput.add(Kelenjar);
        Kelenjar.setBounds(536, 290, 80, 23);

        jLabel45.setText("Dada :");
        jLabel45.setName("jLabel45"); // NOI18N
        FormInput.add(jLabel45);
        jLabel45.setBounds(442, 320, 90, 23);

        Dada.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Dada.setName("Dada"); // NOI18N
        Dada.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DadaKeyPressed(evt);
            }
        });
        FormInput.add(Dada);
        Dada.setBounds(536, 320, 80, 23);

        jLabel46.setText("Kulit :");
        jLabel46.setName("jLabel46"); // NOI18N
        FormInput.add(jLabel46);
        jLabel46.setBounds(0, 290, 84, 23);

        Kulit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Kulit.setName("Kulit"); // NOI18N
        Kulit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KulitKeyPressed(evt);
            }
        });
        FormInput.add(Kulit);
        Kulit.setBounds(88, 290, 80, 23);

        jLabel49.setText("Mata :");
        jLabel49.setName("jLabel49"); // NOI18N
        FormInput.add(jLabel49);
        jLabel49.setBounds(0, 350, 84, 23);

        Mata.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Mata.setName("Mata"); // NOI18N
        Mata.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MataKeyPressed(evt);
            }
        });
        FormInput.add(Mata);
        Mata.setBounds(88, 350, 80, 23);

        jLabel50.setText("Leher :");
        jLabel50.setName("jLabel50"); // NOI18N
        FormInput.add(jLabel50);
        jLabel50.setBounds(0, 380, 84, 23);

        Leher.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Leher.setName("Leher"); // NOI18N
        Leher.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LeherKeyPressed(evt);
            }
        });
        FormInput.add(Leher);
        Leher.setBounds(88, 380, 80, 23);

        jLabel51.setText("Ekstremitas :");
        jLabel51.setName("jLabel51"); // NOI18N
        FormInput.add(jLabel51);
        jLabel51.setBounds(442, 380, 90, 23);

        Ekstremitas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Ekstremitas.setName("Ekstremitas"); // NOI18N
        Ekstremitas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EkstremitasKeyPressed(evt);
            }
        });
        FormInput.add(Ekstremitas);
        Ekstremitas.setBounds(536, 380, 80, 23);

        jLabel99.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel99.setText("I. RIWAYAT KESEHATAN");
        jLabel99.setName("jLabel99"); // NOI18N
        FormInput.add(jLabel99);
        jLabel99.setBounds(10, 70, 180, 23);

        jLabel101.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel101.setText("V. PEMERIKSAAN PENUNJANG");
        jLabel101.setName("jLabel101"); // NOI18N
        FormInput.add(jLabel101);
        jLabel101.setBounds(10, 1170, 190, 23);

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label11);
        label11.setBounds(380, 40, 52, 23);

        TglAsuhan.setForeground(new java.awt.Color(50, 70, 50));
        TglAsuhan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "28-05-2023 00:52:46" }));
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

        jLabel41.setText("Skala Nyeri :");
        jLabel41.setName("jLabel41"); // NOI18N
        FormInput.add(jLabel41);
        jLabel41.setBounds(446, 210, 80, 23);

        Nyeri.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Nyeri", "Nyeri Ringan", "Nyeri Sedang", "Nyeri Berat", "Nyeri Sangat Berat", "Nyeri Tak Tertahankan" }));
        Nyeri.setName("Nyeri"); // NOI18N
        Nyeri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NyeriKeyPressed(evt);
            }
        });
        FormInput.add(Nyeri);
        Nyeri.setBounds(530, 210, 160, 23);

        jLabel95.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel95.setText("II. PEMERIKSAAN FISIK");
        jLabel95.setName("jLabel95"); // NOI18N
        FormInput.add(jLabel95);
        jLabel95.setBounds(10, 190, 180, 23);

        jLabel47.setText("Perut :");
        jLabel47.setName("jLabel47"); // NOI18N
        FormInput.add(jLabel47);
        jLabel47.setBounds(442, 350, 90, 23);

        Perut.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Perut.setName("Perut"); // NOI18N
        Perut.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PerutKeyPressed(evt);
            }
        });
        FormInput.add(Perut);
        Perut.setBounds(536, 350, 80, 23);

        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel14.setText("Kg");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(499, 240, 30, 23);

        jLabel42.setText("Kesadaran :");
        jLabel42.setName("jLabel42"); // NOI18N
        FormInput.add(jLabel42);
        jLabel42.setBounds(238, 210, 70, 23);

        Keadaan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Baik", "Sedang", "Lemah", "Buruk" }));
        Keadaan.setName("Keadaan"); // NOI18N
        Keadaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeadaanKeyPressed(evt);
            }
        });
        FormInput.add(Keadaan);
        Keadaan.setBounds(131, 210, 90, 23);

        jLabel113.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel113.setText("IV. STATUS LOKALISATA");
        jLabel113.setName("jLabel113"); // NOI18N
        FormInput.add(jLabel113);
        jLabel113.setBounds(10, 410, 190, 23);

        jLabel115.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel115.setText("Wajah :");
        jLabel115.setName("jLabel115"); // NOI18N
        FormInput.add(jLabel115);
        jLabel115.setBounds(44, 430, 190, 23);

        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel33.setText("Keluhan Utama");
        jLabel33.setName("jLabel33"); // NOI18N
        FormInput.add(jLabel33);
        jLabel33.setBounds(44, 90, 90, 23);

        jLabel34.setText(":");
        jLabel34.setName("jLabel34"); // NOI18N
        FormInput.add(jLabel34);
        jLabel34.setBounds(0, 90, 125, 23);

        jLabel32.setText(":");
        jLabel32.setName("jLabel32"); // NOI18N
        FormInput.add(jLabel32);
        jLabel32.setBounds(0, 140, 180, 23);

        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel36.setText("Riwayat Penyakit Keluarga");
        jLabel36.setName("jLabel36"); // NOI18N
        FormInput.add(jLabel36);
        jLabel36.setBounds(44, 140, 150, 23);

        jLabel43.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel43.setText("Keadaan Umum");
        jLabel43.setName("jLabel43"); // NOI18N
        FormInput.add(jLabel43);
        jLabel43.setBounds(44, 210, 90, 23);

        jLabel48.setText(":");
        jLabel48.setName("jLabel48"); // NOI18N
        FormInput.add(jLabel48);
        jLabel48.setBounds(0, 210, 127, 23);

        jSeparator17.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator17.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator17.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator17.setName("jSeparator17"); // NOI18N
        FormInput.add(jSeparator17);
        jSeparator17.setBounds(0, 270, 880, 1);

        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel24.setText("Nadi");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(44, 240, 40, 23);

        KeteranganEkstrimitas.setFocusTraversalPolicyProvider(true);
        KeteranganEkstrimitas.setName("KeteranganEkstrimitas"); // NOI18N
        KeteranganEkstrimitas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganEkstrimitasKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganEkstrimitas);
        KeteranganEkstrimitas.setBounds(619, 380, 235, 23);

        KeteranganKulit.setFocusTraversalPolicyProvider(true);
        KeteranganKulit.setName("KeteranganKulit"); // NOI18N
        KeteranganKulit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKulitKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganKulit);
        KeteranganKulit.setBounds(171, 290, 235, 23);

        KeteranganKepala.setFocusTraversalPolicyProvider(true);
        KeteranganKepala.setName("KeteranganKepala"); // NOI18N
        KeteranganKepala.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKepalaKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganKepala);
        KeteranganKepala.setBounds(171, 320, 235, 23);

        KeteranganMata.setFocusTraversalPolicyProvider(true);
        KeteranganMata.setName("KeteranganMata"); // NOI18N
        KeteranganMata.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganMataKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganMata);
        KeteranganMata.setBounds(171, 350, 235, 23);

        KeteranganLeher.setFocusTraversalPolicyProvider(true);
        KeteranganLeher.setName("KeteranganLeher"); // NOI18N
        KeteranganLeher.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganLeherKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganLeher);
        KeteranganLeher.setBounds(171, 380, 235, 23);

        KeteranganKelenjar.setFocusTraversalPolicyProvider(true);
        KeteranganKelenjar.setName("KeteranganKelenjar"); // NOI18N
        KeteranganKelenjar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKelenjarKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganKelenjar);
        KeteranganKelenjar.setBounds(619, 290, 235, 23);

        KeteranganDada.setFocusTraversalPolicyProvider(true);
        KeteranganDada.setName("KeteranganDada"); // NOI18N
        KeteranganDada.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganDadaKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganDada);
        KeteranganDada.setBounds(619, 320, 235, 23);

        KeteranganPerut.setFocusTraversalPolicyProvider(true);
        KeteranganPerut.setName("KeteranganPerut"); // NOI18N
        KeteranganPerut.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganPerutKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganPerut);
        KeteranganPerut.setBounds(619, 350, 235, 23);

        jLabel52.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel52.setText("Kepala");
        jLabel52.setName("jLabel52"); // NOI18N
        FormInput.add(jLabel52);
        jLabel52.setBounds(44, 320, 50, 23);

        jSeparator18.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator18.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator18.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator18.setName("jSeparator18"); // NOI18N
        FormInput.add(jSeparator18);
        jSeparator18.setBounds(0, 410, 880, 1);

        scrollPane5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane5.setName("scrollPane5"); // NOI18N

        Wajah.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Wajah.setColumns(20);
        Wajah.setRows(15);
        Wajah.setName("Wajah"); // NOI18N
        Wajah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                WajahKeyPressed(evt);
            }
        });
        scrollPane5.setViewportView(Wajah);

        FormInput.add(scrollPane5);
        scrollPane5.setBounds(44, 450, 210, 173);

        PanelWall1.setBackground(new java.awt.Color(255, 255, 255));
        PanelWall1.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/picture/wajah2.png"))); // NOI18N
        PanelWall1.setBackgroundImageType(usu.widget.constan.BackgroundConstan.BACKGROUND_IMAGE_STRECT);
        PanelWall1.setOpaque(true);
        PanelWall1.setPreferredSize(new java.awt.Dimension(200, 200));
        PanelWall1.setRound(false);
        PanelWall1.setWarna(new java.awt.Color(110, 110, 110));
        PanelWall1.setLayout(null);
        FormInput.add(PanelWall1);
        PanelWall1.setBounds(694, 450, 130, 173);

        PanelWall2.setBackground(new java.awt.Color(255, 255, 255));
        PanelWall2.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/picture/wajah1.png"))); // NOI18N
        PanelWall2.setBackgroundImageType(usu.widget.constan.BackgroundConstan.BACKGROUND_IMAGE_STRECT);
        PanelWall2.setOpaque(true);
        PanelWall2.setPreferredSize(new java.awt.Dimension(200, 200));
        PanelWall2.setRound(false);
        PanelWall2.setWarna(new java.awt.Color(110, 110, 110));
        PanelWall2.setLayout(null);
        FormInput.add(PanelWall2);
        PanelWall2.setBounds(270, 450, 400, 173);

        jSeparator35.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator35.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator35.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator35.setName("jSeparator35"); // NOI18N
        FormInput.add(jSeparator35);
        jSeparator35.setBounds(44, 630, 780, 1);

        jLabel116.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel116.setText("Intra Oral :");
        jLabel116.setName("jLabel116"); // NOI18N
        FormInput.add(jLabel116);
        jLabel116.setBounds(44, 640, 190, 23);

        scrollPane6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane6.setName("scrollPane6"); // NOI18N

        IntraOral.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        IntraOral.setColumns(20);
        IntraOral.setRows(15);
        IntraOral.setName("IntraOral"); // NOI18N
        IntraOral.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                IntraOralKeyPressed(evt);
            }
        });
        scrollPane6.setViewportView(IntraOral);

        FormInput.add(scrollPane6);
        scrollPane6.setBounds(44, 660, 210, 173);

        jSeparator36.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator36.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator36.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator36.setName("jSeparator36"); // NOI18N
        FormInput.add(jSeparator36);
        jSeparator36.setBounds(44, 840, 780, 1);

        PanelWall3.setBackground(new java.awt.Color(255, 255, 255));
        PanelWall3.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/picture/intraoral.png"))); // NOI18N
        PanelWall3.setBackgroundImageType(usu.widget.constan.BackgroundConstan.BACKGROUND_IMAGE_STRECT);
        PanelWall3.setOpaque(true);
        PanelWall3.setPreferredSize(new java.awt.Dimension(200, 200));
        PanelWall3.setRound(false);
        PanelWall3.setWarna(new java.awt.Color(110, 110, 110));
        PanelWall3.setLayout(null);
        FormInput.add(PanelWall3);
        PanelWall3.setBounds(270, 660, 554, 173);

        jLabel117.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel117.setText("Gigi Geligi :");
        jLabel117.setName("jLabel117"); // NOI18N
        FormInput.add(jLabel117);
        jLabel117.setBounds(44, 850, 190, 23);

        scrollPane8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane8.setName("scrollPane8"); // NOI18N

        GigiGeligi.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        GigiGeligi.setColumns(20);
        GigiGeligi.setRows(25);
        GigiGeligi.setName("GigiGeligi"); // NOI18N
        GigiGeligi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GigiGeligiKeyPressed(evt);
            }
        });
        scrollPane8.setViewportView(GigiGeligi);

        FormInput.add(scrollPane8);
        scrollPane8.setBounds(44, 870, 210, 293);

        PanelWall4.setBackground(new java.awt.Color(255, 255, 255));
        PanelWall4.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/picture/gigigeligi.png"))); // NOI18N
        PanelWall4.setBackgroundImageType(usu.widget.constan.BackgroundConstan.BACKGROUND_IMAGE_STRECT);
        PanelWall4.setOpaque(true);
        PanelWall4.setPreferredSize(new java.awt.Dimension(200, 200));
        PanelWall4.setRound(false);
        PanelWall4.setWarna(new java.awt.Color(110, 110, 110));
        PanelWall4.setLayout(null);
        FormInput.add(PanelWall4);
        PanelWall4.setBounds(270, 870, 554, 293);

        jSeparator19.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator19.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator19.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator19.setName("jSeparator19"); // NOI18N
        FormInput.add(jSeparator19);
        jSeparator19.setBounds(0, 1170, 880, 1);

        jLabel112.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel112.setText("Penunjang Lainnya :");
        jLabel112.setName("jLabel112"); // NOI18N
        FormInput.add(jLabel112);
        jLabel112.setBounds(594, 1190, 190, 23);

        jLabel80.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel80.setText("Laboratorium :");
        jLabel80.setName("jLabel80"); // NOI18N
        FormInput.add(jLabel80);
        jLabel80.setBounds(44, 1190, 150, 23);

        jLabel81.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel81.setText("Radiologi :");
        jLabel81.setName("jLabel81"); // NOI18N
        FormInput.add(jLabel81);
        jLabel81.setBounds(319, 1190, 150, 23);

        scrollPane15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane15.setName("scrollPane15"); // NOI18N

        Penunjang.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Penunjang.setColumns(20);
        Penunjang.setRows(5);
        Penunjang.setName("Penunjang"); // NOI18N
        Penunjang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenunjangKeyPressed(evt);
            }
        });
        scrollPane15.setViewportView(Penunjang);

        FormInput.add(scrollPane15);
        scrollPane15.setBounds(594, 1210, 260, 63);

        scrollPane9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane9.setName("scrollPane9"); // NOI18N

        Laborat.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Laborat.setColumns(20);
        Laborat.setRows(5);
        Laborat.setName("Laborat"); // NOI18N
        Laborat.setPreferredSize(new java.awt.Dimension(102, 52));
        Laborat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LaboratKeyPressed(evt);
            }
        });
        scrollPane9.setViewportView(Laborat);

        FormInput.add(scrollPane9);
        scrollPane9.setBounds(44, 1210, 260, 63);

        scrollPane10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane10.setName("scrollPane10"); // NOI18N

        Radiologi.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Radiologi.setColumns(20);
        Radiologi.setRows(5);
        Radiologi.setName("Radiologi"); // NOI18N
        Radiologi.setPreferredSize(new java.awt.Dimension(102, 52));
        Radiologi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RadiologiKeyPressed(evt);
            }
        });
        scrollPane10.setViewportView(Radiologi);

        FormInput.add(scrollPane10);
        scrollPane10.setBounds(319, 1210, 260, 63);

        jSeparator20.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator20.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator20.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator20.setName("jSeparator20"); // NOI18N
        FormInput.add(jSeparator20);
        jSeparator20.setBounds(0, 1280, 880, 1);

        jLabel106.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel106.setText("VI. DIAGNOSIS/ASESMEN");
        jLabel106.setName("jLabel106"); // NOI18N
        FormInput.add(jLabel106);
        jLabel106.setBounds(10, 1280, 190, 23);

        jLabel85.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel85.setText("Asesmen Banding :");
        jLabel85.setName("jLabel85"); // NOI18N
        FormInput.add(jLabel85);
        jLabel85.setBounds(454, 1300, 150, 23);

        jLabel82.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel82.setText("Asesmen Kerja :");
        jLabel82.setName("jLabel82"); // NOI18N
        FormInput.add(jLabel82);
        jLabel82.setBounds(44, 1300, 150, 23);

        scrollPane16.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane16.setName("scrollPane16"); // NOI18N

        AsesmenBanding.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        AsesmenBanding.setColumns(20);
        AsesmenBanding.setRows(3);
        AsesmenBanding.setName("AsesmenBanding"); // NOI18N
        AsesmenBanding.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AsesmenBandingKeyPressed(evt);
            }
        });
        scrollPane16.setViewportView(AsesmenBanding);

        FormInput.add(scrollPane16);
        scrollPane16.setBounds(454, 1320, 400, 43);

        scrollPane19.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane19.setName("scrollPane19"); // NOI18N

        AsesmenKerja.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        AsesmenKerja.setColumns(20);
        AsesmenKerja.setRows(3);
        AsesmenKerja.setName("AsesmenKerja"); // NOI18N
        AsesmenKerja.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AsesmenKerjaKeyPressed(evt);
            }
        });
        scrollPane19.setViewportView(AsesmenKerja);

        FormInput.add(scrollPane19);
        scrollPane19.setBounds(44, 1320, 400, 43);

        jSeparator21.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator21.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator21.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator21.setName("jSeparator21"); // NOI18N
        FormInput.add(jSeparator21);
        jSeparator21.setBounds(0, 1370, 880, 1);

        jLabel107.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel107.setText("VII. PERMASALAHAN & TATALAKSANA");
        jLabel107.setName("jLabel107"); // NOI18N
        FormInput.add(jLabel107);
        jLabel107.setBounds(10, 1370, 190, 23);

        jLabel114.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel114.setText("Permasalahan :");
        jLabel114.setName("jLabel114"); // NOI18N
        FormInput.add(jLabel114);
        jLabel114.setBounds(44, 1390, 190, 20);

        jLabel108.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel108.setText("Terapi/Pengobatan :");
        jLabel108.setName("jLabel108"); // NOI18N
        FormInput.add(jLabel108);
        jLabel108.setBounds(454, 1390, 190, 20);

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
        scrollPane20.setBounds(44, 1410, 400, 43);

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
        scrollPane17.setBounds(454, 1410, 400, 43);

        jLabel118.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel118.setText("Tindakan/Rencana Tindakan :");
        jLabel118.setName("jLabel118"); // NOI18N
        FormInput.add(jLabel118);
        jLabel118.setBounds(44, 1460, 320, 20);

        scrollPane21.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane21.setName("scrollPane21"); // NOI18N

        Tindakan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Tindakan.setColumns(20);
        Tindakan.setRows(3);
        Tindakan.setName("Tindakan"); // NOI18N
        Tindakan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TindakanKeyPressed(evt);
            }
        });
        scrollPane21.setViewportView(Tindakan);

        FormInput.add(scrollPane21);
        scrollPane21.setBounds(44, 1480, 810, 43);

        jSeparator22.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator22.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator22.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator22.setName("jSeparator22"); // NOI18N
        FormInput.add(jSeparator22);
        jSeparator22.setBounds(0, 1530, 880, 1);

        jLabel109.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel109.setText("VIII. EDUKASI");
        jLabel109.setName("jLabel109"); // NOI18N
        FormInput.add(jLabel109);
        jLabel109.setBounds(10, 1530, 190, 23);

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
        scrollPane14.setBounds(44, 1550, 810, 63);

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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "28-05-2023" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "28-05-2023" }));
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
        }else if(RPK.getText().trim().equals("")){
            Valid.textKosong(RPK,"Riwayat Penyakit Keluarga");
        }else{
            if(Sequel.menyimpantf("penilaian_medis_ralan_bedah_mulut","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat",47,new String[]{
                    TNoRw.getText(),Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),KdDokter.getText(),Anamnesis.getSelectedItem().toString(),Hubungan.getText(),
                    KeluhanUtama.getText(),RPS.getText(),RPK.getText(),Alergi.getText(),Keadaan.getSelectedItem().toString(),Kesadaran.getSelectedItem().toString(),Nyeri.getSelectedItem().toString(),TD.getText(),Nadi.getText(), 
                    Suhu.getText(),RR.getText(),BB.getText(),TB.getText(),StatusNutrisi.getText(),Kulit.getSelectedItem().toString(),KeteranganKulit.getText(),Kepala.getSelectedItem().toString(),KeteranganKepala.getText(), 
                    Mata.getSelectedItem().toString(),KeteranganMata.getText(),Leher.getSelectedItem().toString(),KeteranganLeher.getText(),Kelenjar.getSelectedItem().toString(),KeteranganKelenjar.getText(),Dada.getSelectedItem().toString(), 
                    KeteranganDada.getText(),Perut.getSelectedItem().toString(),KeteranganPerut.getText(),Ekstremitas.getSelectedItem().toString(),KeteranganEkstrimitas.getText(),Wajah.getText(),IntraOral.getText(),GigiGeligi.getText(),
                    Laborat.getText(),Radiologi.getText(),Penunjang.getText(),AsesmenKerja.getText(),AsesmenBanding.getText(),Permasalahan.getText(),Terapi.getText(),Tindakan.getText(),Edukasi.getText()
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
        }else if(RPK.getText().trim().equals("")){
            Valid.textKosong(RPK,"Riwayat Penyakit Keluarga");
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
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Kode Dokter</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Nama Dokter</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Tanggal</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Anamnesis</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Hubungan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Keluhan Utama</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Riwayat Penyakit Sekarang</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Riwayat Penyakit Keluarga</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Alergi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Keadaan Umum</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Kesadaran</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Skala Nyeri</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Tensi(mmHg)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Nadi(x/menit)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Suhu(°C)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>RR(x/menit)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>BB(Kg)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>TB(Cm)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Status Nutrisi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Kulit</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Keterangan Kulit</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Kepala</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Keterangan Kepala</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Mata</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Keterangan Mata</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Leher</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Keterangan Leher</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Kelenjar</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Keterangan Kelenjar</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Dada</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Keterangan Dada</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Perut</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Keterangan Perut</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Ekstremitas</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Keterangan Ekstremitas</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Wajah</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Intra</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Gigi Geligi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Laborat</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Radiologi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Penunjang</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Asesmen Kerja</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Asesmen Banding</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Permasalahan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Terapi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Tindakan</b></td>"+
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
                            "<td valign='top'>"+tbObat.getValueAt(i,47).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,48).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,49).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,50).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,51).toString()+"</td>"+
                        "</tr>");
                }
                LoadHTML.setText(
                    "<html>"+
                      "<table width='4200px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
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

                File f = new File("DataPenilaianAwalMedisRalanBedahMulut.html");            
                BufferedWriter bw = new BufferedWriter(new FileWriter(f));            
                bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                            "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                            "<table width='4200px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                "<tr class='isi2'>"+
                                    "<td valign='top' align='center'>"+
                                        "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                        akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                        akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                        "<font size='2' face='Tahoma'>DATA PENILAIAN AWAL MEDIS RAWAT JALAN BEDAH MULUT<br><br></font>"+        
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
        //Valid.pindah(evt,Monitoring,BtnSimpan);
    }//GEN-LAST:event_BtnDokterKeyPressed

    private void BBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BBKeyPressed
        Valid.pindah(evt,RR,TB);
    }//GEN-LAST:event_BBKeyPressed

    private void NadiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NadiKeyPressed
        Valid.pindah(evt,TD,Suhu);
    }//GEN-LAST:event_NadiKeyPressed

    private void SuhuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SuhuKeyPressed
        Valid.pindah(evt,Nadi,RR);
    }//GEN-LAST:event_SuhuKeyPressed

    private void TDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TDKeyPressed
        Valid.pindah(evt,Nyeri,Nadi);
    }//GEN-LAST:event_TDKeyPressed

    private void RRKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RRKeyPressed
        Valid.pindah(evt,Suhu,BB);
    }//GEN-LAST:event_RRKeyPressed

    private void AlergiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlergiKeyPressed
        Valid.pindah(evt,RPK,Keadaan);
    }//GEN-LAST:event_AlergiKeyPressed

    private void AnamnesisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AnamnesisKeyPressed
        Valid.pindah(evt,TglAsuhan,Hubungan);
    }//GEN-LAST:event_AnamnesisKeyPressed

    private void KeluhanUtamaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeluhanUtamaKeyPressed
        Valid.pindah2(evt,Hubungan,RPS);
    }//GEN-LAST:event_KeluhanUtamaKeyPressed

    private void RPKKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RPKKeyPressed
        Valid.pindah2(evt,RPS,Alergi);
    }//GEN-LAST:event_RPKKeyPressed

    private void TBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TBKeyPressed
        Valid.pindah(evt,BB,StatusNutrisi);
    }//GEN-LAST:event_TBKeyPressed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if(TabRawat.getSelectedIndex()==1){
            tampil();
        }
    }//GEN-LAST:event_TabRawatMouseClicked

    private void RPSKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RPSKeyPressed
        Valid.pindah2(evt,KeluhanUtama,RPK);
    }//GEN-LAST:event_RPSKeyPressed

    private void KesadaranKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KesadaranKeyPressed
        Valid.pindah(evt,Keadaan,Nyeri);
    }//GEN-LAST:event_KesadaranKeyPressed

    private void StatusNutrisiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_StatusNutrisiKeyPressed
        Valid.pindah(evt,TB,Kulit);
    }//GEN-LAST:event_StatusNutrisiKeyPressed

    private void KepalaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KepalaKeyPressed
        Valid.pindah(evt,KeteranganKulit,KeteranganKepala);
    }//GEN-LAST:event_KepalaKeyPressed

    private void KelenjarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KelenjarKeyPressed
        Valid.pindah(evt,KeteranganLeher,KeteranganKelenjar);
    }//GEN-LAST:event_KelenjarKeyPressed

    private void DadaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DadaKeyPressed
        Valid.pindah(evt,KeteranganKelenjar,KeteranganDada);
    }//GEN-LAST:event_DadaKeyPressed

    private void KulitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KulitKeyPressed
       Valid.pindah(evt,StatusNutrisi,KeteranganKulit);
    }//GEN-LAST:event_KulitKeyPressed

    private void MataKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MataKeyPressed
        Valid.pindah(evt,KeteranganKepala,KeteranganMata);
    }//GEN-LAST:event_MataKeyPressed

    private void LeherKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LeherKeyPressed
        Valid.pindah(evt,KeteranganMata,KeteranganLeher);
    }//GEN-LAST:event_LeherKeyPressed

    private void EkstremitasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EkstremitasKeyPressed
        Valid.pindah(evt,KeteranganPerut,KeteranganEkstrimitas);
    }//GEN-LAST:event_EkstremitasKeyPressed

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
            try {
                param.put("wajah1",getClass().getResource("/picture/wajah1.png").openStream()); 
            } catch (Exception e) {
            }   
            try {
                param.put("wajah2",getClass().getResource("/picture/wajah2.png").openStream()); 
            } catch (Exception e) {
            } 
            try {
                param.put("intraoral",getClass().getResource("/picture/intraoral.png").openStream()); ; 
            } catch (Exception e) {
            } 
            try {
                param.put("gigigeligi",getClass().getResource("/picture/gigigeligi.png").openStream()); 
            } catch (Exception e) {
            } 
            finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
            param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbObat.getValueAt(tbObat.getSelectedRow(),6).toString()+"\nID "+(finger.equals("")?tbObat.getValueAt(tbObat.getSelectedRow(),5).toString():finger)+"\n"+Valid.SetTgl3(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString())); 
            
            Valid.MyReportqry("rptCetakPenilaianAwalMedisRalanBedahMulut.jasper","report","::[ Laporan Penilaian Awal Medis Rawat Jalan Bedah Mulut ]::",
                "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penilaian_medis_ralan_bedah_mulut.tanggal,"+
                "penilaian_medis_ralan_bedah_mulut.kd_dokter,penilaian_medis_ralan_bedah_mulut.anamnesis,penilaian_medis_ralan_bedah_mulut.hubungan,penilaian_medis_ralan_bedah_mulut.keluhan_utama,"+
                "penilaian_medis_ralan_bedah_mulut.rps,penilaian_medis_ralan_bedah_mulut.rpk,penilaian_medis_ralan_bedah_mulut.alergi,penilaian_medis_ralan_bedah_mulut.keadaan,"+
                "penilaian_medis_ralan_bedah_mulut.kesadaran,penilaian_medis_ralan_bedah_mulut.nyeri,penilaian_medis_ralan_bedah_mulut.td,penilaian_medis_ralan_bedah_mulut.nadi,"+
                "penilaian_medis_ralan_bedah_mulut.suhu,penilaian_medis_ralan_bedah_mulut.rr,penilaian_medis_ralan_bedah_mulut.bb,penilaian_medis_ralan_bedah_mulut.tb,"+
                "penilaian_medis_ralan_bedah_mulut.status_nutrisi,penilaian_medis_ralan_bedah_mulut.kulit,penilaian_medis_ralan_bedah_mulut.keterangan_kulit,penilaian_medis_ralan_bedah_mulut.kepala,"+
                "penilaian_medis_ralan_bedah_mulut.keterangan_kepala,penilaian_medis_ralan_bedah_mulut.mata,penilaian_medis_ralan_bedah_mulut.keterangan_mata,penilaian_medis_ralan_bedah_mulut.leher,"+
                "penilaian_medis_ralan_bedah_mulut.keterangan_leher,penilaian_medis_ralan_bedah_mulut.kelenjar,penilaian_medis_ralan_bedah_mulut.keterangan_kelenjar,penilaian_medis_ralan_bedah_mulut.dada,"+
                "penilaian_medis_ralan_bedah_mulut.keterangan_dada,penilaian_medis_ralan_bedah_mulut.perut,penilaian_medis_ralan_bedah_mulut.keterangan_perut,penilaian_medis_ralan_bedah_mulut.ekstremitas,"+
                "penilaian_medis_ralan_bedah_mulut.keterangan_ekstremitas,penilaian_medis_ralan_bedah_mulut.wajah,penilaian_medis_ralan_bedah_mulut.intra,penilaian_medis_ralan_bedah_mulut.gigigeligi,"+
                "penilaian_medis_ralan_bedah_mulut.lab,penilaian_medis_ralan_bedah_mulut.rad,penilaian_medis_ralan_bedah_mulut.penunjang,penilaian_medis_ralan_bedah_mulut.diagnosis,"+
                "penilaian_medis_ralan_bedah_mulut.diagnosis2,penilaian_medis_ralan_bedah_mulut.permasalahan,penilaian_medis_ralan_bedah_mulut.terapi,penilaian_medis_ralan_bedah_mulut.tindakan,"+
                "penilaian_medis_ralan_bedah_mulut.edukasi,dokter.nm_dokter "+
                "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join penilaian_medis_ralan_bedah_mulut on reg_periksa.no_rawat=penilaian_medis_ralan_bedah_mulut.no_rawat "+
                "inner join dokter on penilaian_medis_ralan_bedah_mulut.kd_dokter=dokter.kd_dokter where penilaian_medis_ralan_bedah_mulut.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'",param);
        }
    }//GEN-LAST:event_MnPenilaianMedisActionPerformed

    private void NyeriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NyeriKeyPressed
         Valid.pindah(evt,Kesadaran,TD);
    }//GEN-LAST:event_NyeriKeyPressed

    private void PerutKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PerutKeyPressed
        Valid.pindah(evt,KeteranganDada,KeteranganPerut);
    }//GEN-LAST:event_PerutKeyPressed

    private void KeadaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeadaanKeyPressed
        Valid.pindah(evt,RPS,Kesadaran);
    }//GEN-LAST:event_KeadaanKeyPressed

    private void KeteranganEkstrimitasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganEkstrimitasKeyPressed
        Valid.pindah(evt,Ekstremitas,Wajah);
    }//GEN-LAST:event_KeteranganEkstrimitasKeyPressed

    private void KeteranganKulitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKulitKeyPressed
        Valid.pindah(evt,Kulit,Kepala);
    }//GEN-LAST:event_KeteranganKulitKeyPressed

    private void KeteranganKepalaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKepalaKeyPressed
        Valid.pindah(evt,Kepala,Mata);
    }//GEN-LAST:event_KeteranganKepalaKeyPressed

    private void KeteranganMataKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganMataKeyPressed
        Valid.pindah(evt,Mata,Leher);
    }//GEN-LAST:event_KeteranganMataKeyPressed

    private void KeteranganLeherKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganLeherKeyPressed
        Valid.pindah(evt,Leher,Kelenjar);
    }//GEN-LAST:event_KeteranganLeherKeyPressed

    private void KeteranganKelenjarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKelenjarKeyPressed
        Valid.pindah(evt,Kelenjar,Dada);
    }//GEN-LAST:event_KeteranganKelenjarKeyPressed

    private void KeteranganDadaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganDadaKeyPressed
        Valid.pindah(evt,Dada,Perut);
    }//GEN-LAST:event_KeteranganDadaKeyPressed

    private void KeteranganPerutKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganPerutKeyPressed
        Valid.pindah(evt,Perut,Ekstremitas);
    }//GEN-LAST:event_KeteranganPerutKeyPressed

    private void WajahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_WajahKeyPressed
        Valid.pindah2(evt,KeteranganEkstrimitas,IntraOral);
    }//GEN-LAST:event_WajahKeyPressed

    private void IntraOralKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_IntraOralKeyPressed
        Valid.pindah2(evt,Wajah,GigiGeligi);
    }//GEN-LAST:event_IntraOralKeyPressed

    private void GigiGeligiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GigiGeligiKeyPressed
        Valid.pindah2(evt,IntraOral,Laborat);
    }//GEN-LAST:event_GigiGeligiKeyPressed

    private void PenunjangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenunjangKeyPressed
        Valid.pindah2(evt,Radiologi,AsesmenKerja);
    }//GEN-LAST:event_PenunjangKeyPressed

    private void LaboratKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LaboratKeyPressed
        Valid.pindah2(evt,GigiGeligi,Radiologi);
    }//GEN-LAST:event_LaboratKeyPressed

    private void RadiologiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RadiologiKeyPressed
        Valid.pindah2(evt,Laborat,Penunjang);
    }//GEN-LAST:event_RadiologiKeyPressed

    private void AsesmenBandingKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AsesmenBandingKeyPressed
        Valid.pindah2(evt,AsesmenKerja,Permasalahan);
    }//GEN-LAST:event_AsesmenBandingKeyPressed

    private void AsesmenKerjaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AsesmenKerjaKeyPressed
        Valid.pindah2(evt,Penunjang,AsesmenBanding);
    }//GEN-LAST:event_AsesmenKerjaKeyPressed

    private void PermasalahanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PermasalahanKeyPressed
        Valid.pindah2(evt,AsesmenBanding,Terapi);
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
            RMPenilaianAwalMedisRalanBedahMulut dialog = new RMPenilaianAwalMedisRalanBedahMulut(new javax.swing.JFrame(), true);
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
    private widget.TextBox Alergi;
    private widget.ComboBox Anamnesis;
    private widget.TextArea AsesmenBanding;
    private widget.TextArea AsesmenKerja;
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
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.ComboBox Dada;
    private widget.TextArea Edukasi;
    private widget.ComboBox Ekstremitas;
    private widget.PanelBiasa FormInput;
    private widget.TextArea GigiGeligi;
    private widget.TextBox Hubungan;
    private widget.TextArea IntraOral;
    private widget.TextBox Jk;
    private widget.TextBox KdDokter;
    private widget.ComboBox Keadaan;
    private widget.ComboBox Kelenjar;
    private widget.TextArea KeluhanUtama;
    private widget.ComboBox Kepala;
    private widget.ComboBox Kesadaran;
    private widget.TextBox KeteranganDada;
    private widget.TextBox KeteranganEkstrimitas;
    private widget.TextBox KeteranganKelenjar;
    private widget.TextBox KeteranganKepala;
    private widget.TextBox KeteranganKulit;
    private widget.TextBox KeteranganLeher;
    private widget.TextBox KeteranganMata;
    private widget.TextBox KeteranganPerut;
    private widget.ComboBox Kulit;
    private widget.Label LCount;
    private widget.TextArea Laborat;
    private widget.ComboBox Leher;
    private widget.editorpane LoadHTML;
    private widget.ComboBox Mata;
    private javax.swing.JMenuItem MnPenilaianMedis;
    private widget.TextBox Nadi;
    private widget.TextBox NmDokter;
    private widget.ComboBox Nyeri;
    private usu.widget.glass.PanelGlass PanelWall1;
    private usu.widget.glass.PanelGlass PanelWall2;
    private usu.widget.glass.PanelGlass PanelWall3;
    private usu.widget.glass.PanelGlass PanelWall4;
    private widget.TextArea Penunjang;
    private widget.TextArea Permasalahan;
    private widget.ComboBox Perut;
    private widget.TextArea RPK;
    private widget.TextArea RPS;
    private widget.TextBox RR;
    private widget.TextArea Radiologi;
    private widget.ScrollPane Scroll;
    private widget.TextBox StatusNutrisi;
    private widget.TextBox Suhu;
    private widget.TextBox TB;
    private widget.TextBox TCari;
    private widget.TextBox TD;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private javax.swing.JTabbedPane TabRawat;
    private widget.TextArea Terapi;
    private widget.Tanggal TglAsuhan;
    private widget.TextBox TglLahir;
    private widget.TextArea Tindakan;
    private widget.TextArea Wajah;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.Label jLabel10;
    private widget.Label jLabel101;
    private widget.Label jLabel106;
    private widget.Label jLabel107;
    private widget.Label jLabel108;
    private widget.Label jLabel109;
    private widget.Label jLabel11;
    private widget.Label jLabel112;
    private widget.Label jLabel113;
    private widget.Label jLabel114;
    private widget.Label jLabel115;
    private widget.Label jLabel116;
    private widget.Label jLabel117;
    private widget.Label jLabel118;
    private widget.Label jLabel12;
    private widget.Label jLabel13;
    private widget.Label jLabel14;
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
    private widget.Label jLabel28;
    private widget.Label jLabel29;
    private widget.Label jLabel30;
    private widget.Label jLabel32;
    private widget.Label jLabel33;
    private widget.Label jLabel34;
    private widget.Label jLabel36;
    private widget.Label jLabel37;
    private widget.Label jLabel38;
    private widget.Label jLabel40;
    private widget.Label jLabel41;
    private widget.Label jLabel42;
    private widget.Label jLabel43;
    private widget.Label jLabel44;
    private widget.Label jLabel45;
    private widget.Label jLabel46;
    private widget.Label jLabel47;
    private widget.Label jLabel48;
    private widget.Label jLabel49;
    private widget.Label jLabel50;
    private widget.Label jLabel51;
    private widget.Label jLabel52;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel80;
    private widget.Label jLabel81;
    private widget.Label jLabel82;
    private widget.Label jLabel85;
    private widget.Label jLabel94;
    private widget.Label jLabel95;
    private widget.Label jLabel99;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator17;
    private javax.swing.JSeparator jSeparator18;
    private javax.swing.JSeparator jSeparator19;
    private javax.swing.JSeparator jSeparator20;
    private javax.swing.JSeparator jSeparator21;
    private javax.swing.JSeparator jSeparator22;
    private javax.swing.JSeparator jSeparator35;
    private javax.swing.JSeparator jSeparator36;
    private widget.Label label11;
    private widget.Label label14;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollInput;
    private widget.ScrollPane scrollPane1;
    private widget.ScrollPane scrollPane10;
    private widget.ScrollPane scrollPane14;
    private widget.ScrollPane scrollPane15;
    private widget.ScrollPane scrollPane16;
    private widget.ScrollPane scrollPane17;
    private widget.ScrollPane scrollPane19;
    private widget.ScrollPane scrollPane20;
    private widget.ScrollPane scrollPane21;
    private widget.ScrollPane scrollPane4;
    private widget.ScrollPane scrollPane5;
    private widget.ScrollPane scrollPane6;
    private widget.ScrollPane scrollPane7;
    private widget.ScrollPane scrollPane8;
    private widget.ScrollPane scrollPane9;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            if(TCari.getText().trim().equals("")){
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penilaian_medis_ralan_bedah_mulut.tanggal,"+
                        "penilaian_medis_ralan_bedah_mulut.kd_dokter,penilaian_medis_ralan_bedah_mulut.anamnesis,penilaian_medis_ralan_bedah_mulut.hubungan,penilaian_medis_ralan_bedah_mulut.keluhan_utama,"+
                        "penilaian_medis_ralan_bedah_mulut.rps,penilaian_medis_ralan_bedah_mulut.rpk,penilaian_medis_ralan_bedah_mulut.alergi,penilaian_medis_ralan_bedah_mulut.keadaan,"+
                        "penilaian_medis_ralan_bedah_mulut.kesadaran,penilaian_medis_ralan_bedah_mulut.nyeri,penilaian_medis_ralan_bedah_mulut.td,penilaian_medis_ralan_bedah_mulut.nadi,"+
                        "penilaian_medis_ralan_bedah_mulut.suhu,penilaian_medis_ralan_bedah_mulut.rr,penilaian_medis_ralan_bedah_mulut.bb,penilaian_medis_ralan_bedah_mulut.tb,"+
                        "penilaian_medis_ralan_bedah_mulut.status_nutrisi,penilaian_medis_ralan_bedah_mulut.kulit,penilaian_medis_ralan_bedah_mulut.keterangan_kulit,penilaian_medis_ralan_bedah_mulut.kepala,"+
                        "penilaian_medis_ralan_bedah_mulut.keterangan_kepala,penilaian_medis_ralan_bedah_mulut.mata,penilaian_medis_ralan_bedah_mulut.keterangan_mata,penilaian_medis_ralan_bedah_mulut.leher,"+
                        "penilaian_medis_ralan_bedah_mulut.keterangan_leher,penilaian_medis_ralan_bedah_mulut.kelenjar,penilaian_medis_ralan_bedah_mulut.keterangan_kelenjar,penilaian_medis_ralan_bedah_mulut.dada,"+
                        "penilaian_medis_ralan_bedah_mulut.keterangan_dada,penilaian_medis_ralan_bedah_mulut.perut,penilaian_medis_ralan_bedah_mulut.keterangan_perut,penilaian_medis_ralan_bedah_mulut.ekstremitas,"+
                        "penilaian_medis_ralan_bedah_mulut.keterangan_ekstremitas,penilaian_medis_ralan_bedah_mulut.wajah,penilaian_medis_ralan_bedah_mulut.intra,penilaian_medis_ralan_bedah_mulut.gigigeligi,"+
                        "penilaian_medis_ralan_bedah_mulut.lab,penilaian_medis_ralan_bedah_mulut.rad,penilaian_medis_ralan_bedah_mulut.penunjang,penilaian_medis_ralan_bedah_mulut.diagnosis,"+
                        "penilaian_medis_ralan_bedah_mulut.diagnosis2,penilaian_medis_ralan_bedah_mulut.permasalahan,penilaian_medis_ralan_bedah_mulut.terapi,penilaian_medis_ralan_bedah_mulut.tindakan,"+
                        "penilaian_medis_ralan_bedah_mulut.edukasi,dokter.nm_dokter "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join penilaian_medis_ralan_bedah_mulut on reg_periksa.no_rawat=penilaian_medis_ralan_bedah_mulut.no_rawat "+
                        "inner join dokter on penilaian_medis_ralan_bedah_mulut.kd_dokter=dokter.kd_dokter where "+
                        "penilaian_medis_ralan_bedah_mulut.tanggal between ? and ? order by penilaian_medis_ralan_bedah_mulut.tanggal");
            }else{
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penilaian_medis_ralan_bedah_mulut.tanggal,"+
                        "penilaian_medis_ralan_bedah_mulut.kd_dokter,penilaian_medis_ralan_bedah_mulut.anamnesis,penilaian_medis_ralan_bedah_mulut.hubungan,penilaian_medis_ralan_bedah_mulut.keluhan_utama,"+
                        "penilaian_medis_ralan_bedah_mulut.rps,penilaian_medis_ralan_bedah_mulut.rpk,penilaian_medis_ralan_bedah_mulut.alergi,penilaian_medis_ralan_bedah_mulut.keadaan,"+
                        "penilaian_medis_ralan_bedah_mulut.kesadaran,penilaian_medis_ralan_bedah_mulut.nyeri,penilaian_medis_ralan_bedah_mulut.td,penilaian_medis_ralan_bedah_mulut.nadi,"+
                        "penilaian_medis_ralan_bedah_mulut.suhu,penilaian_medis_ralan_bedah_mulut.rr,penilaian_medis_ralan_bedah_mulut.bb,penilaian_medis_ralan_bedah_mulut.tb,"+
                        "penilaian_medis_ralan_bedah_mulut.status_nutrisi,penilaian_medis_ralan_bedah_mulut.kulit,penilaian_medis_ralan_bedah_mulut.keterangan_kulit,penilaian_medis_ralan_bedah_mulut.kepala,"+
                        "penilaian_medis_ralan_bedah_mulut.keterangan_kepala,penilaian_medis_ralan_bedah_mulut.mata,penilaian_medis_ralan_bedah_mulut.keterangan_mata,penilaian_medis_ralan_bedah_mulut.leher,"+
                        "penilaian_medis_ralan_bedah_mulut.keterangan_leher,penilaian_medis_ralan_bedah_mulut.kelenjar,penilaian_medis_ralan_bedah_mulut.keterangan_kelenjar,penilaian_medis_ralan_bedah_mulut.dada,"+
                        "penilaian_medis_ralan_bedah_mulut.keterangan_dada,penilaian_medis_ralan_bedah_mulut.perut,penilaian_medis_ralan_bedah_mulut.keterangan_perut,penilaian_medis_ralan_bedah_mulut.ekstremitas,"+
                        "penilaian_medis_ralan_bedah_mulut.keterangan_ekstremitas,penilaian_medis_ralan_bedah_mulut.wajah,penilaian_medis_ralan_bedah_mulut.intra,penilaian_medis_ralan_bedah_mulut.gigigeligi,"+
                        "penilaian_medis_ralan_bedah_mulut.lab,penilaian_medis_ralan_bedah_mulut.rad,penilaian_medis_ralan_bedah_mulut.penunjang,penilaian_medis_ralan_bedah_mulut.diagnosis,"+
                        "penilaian_medis_ralan_bedah_mulut.diagnosis2,penilaian_medis_ralan_bedah_mulut.permasalahan,penilaian_medis_ralan_bedah_mulut.terapi,penilaian_medis_ralan_bedah_mulut.tindakan,"+
                        "penilaian_medis_ralan_bedah_mulut.edukasi,dokter.nm_dokter "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join penilaian_medis_ralan_bedah_mulut on reg_periksa.no_rawat=penilaian_medis_ralan_bedah_mulut.no_rawat "+
                        "inner join dokter on penilaian_medis_ralan_bedah_mulut.kd_dokter=dokter.kd_dokter where "+
                        "penilaian_medis_ralan_bedah_mulut.tanggal between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or "+
                        "penilaian_medis_ralan_bedah_mulut.kd_dokter like ? or dokter.nm_dokter like ?) order by penilaian_medis_ralan_bedah_mulut.tanggal");
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
                        rs.getString("anamnesis"),rs.getString("hubungan"),rs.getString("keluhan_utama"),rs.getString("rps"),rs.getString("rpk"),rs.getString("alergi"),rs.getString("keadaan"),rs.getString("kesadaran"),
                        rs.getString("nyeri"),rs.getString("td"),rs.getString("nadi"),rs.getString("suhu"),rs.getString("rr"),rs.getString("bb"),rs.getString("tb"),rs.getString("status_nutrisi"),rs.getString("kulit"),
                        rs.getString("keterangan_kulit"),rs.getString("kepala"),rs.getString("keterangan_kepala"),rs.getString("mata"),rs.getString("keterangan_mata"),rs.getString("leher"),rs.getString("keterangan_leher"),
                        rs.getString("kelenjar"),rs.getString("keterangan_kelenjar"),rs.getString("dada"),rs.getString("keterangan_dada"),rs.getString("perut"),rs.getString("keterangan_perut"),rs.getString("ekstremitas"),
                        rs.getString("keterangan_ekstremitas"),rs.getString("wajah"),rs.getString("intra"),rs.getString("gigigeligi"),rs.getString("lab"),rs.getString("rad"),rs.getString("penunjang"),rs.getString("diagnosis"),
                        rs.getString("diagnosis2"),rs.getString("permasalahan"),rs.getString("terapi"),rs.getString("tindakan"),rs.getString("edukasi")
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
        RPK.setText("");
        Alergi.setText("");
        Keadaan.setSelectedIndex(0);
        Kesadaran.setSelectedIndex(0);
        Nyeri.setSelectedIndex(0);
        TD.setText("");
        Nadi.setText("");
        Suhu.setText("");
        RR.setText("");
        BB.setText("");
        StatusNutrisi.setText("");
        TB.setText("");
        Kulit.setSelectedIndex(0);
        Kepala.setSelectedIndex(0);
        Mata.setSelectedIndex(0);
        Leher.setSelectedIndex(0);
        Kelenjar.setSelectedIndex(0);
        Dada.setSelectedIndex(0);
        Perut.setSelectedIndex(0);
        Ekstremitas.setSelectedIndex(0);
        Wajah.setText("");
        IntraOral.setText("");
        GigiGeligi.setText("");
        Laborat.setText("");
        Radiologi.setText("");
        Penunjang.setText("");
        AsesmenKerja.setText("");
        AsesmenBanding.setText("");
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
            RPK.setText(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            Alergi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            Keadaan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            Kesadaran.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
            Nyeri.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            TD.setText(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());
            Nadi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
            Suhu.setText(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
            RR.setText(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString());
            BB.setText(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString());
            TB.setText(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString());
            StatusNutrisi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString());
            Kulit.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),24).toString());
            KeteranganKulit.setText(tbObat.getValueAt(tbObat.getSelectedRow(),25).toString());
            Kepala.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),26).toString());
            KeteranganKepala.setText(tbObat.getValueAt(tbObat.getSelectedRow(),27).toString());
            Mata.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),28).toString());
            KeteranganMata.setText(tbObat.getValueAt(tbObat.getSelectedRow(),29).toString());
            Leher.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),30).toString());
            KeteranganLeher.setText(tbObat.getValueAt(tbObat.getSelectedRow(),31).toString());
            Kelenjar.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),32).toString());
            KeteranganKelenjar.setText(tbObat.getValueAt(tbObat.getSelectedRow(),33).toString());
            Dada.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),34).toString());
            KeteranganDada.setText(tbObat.getValueAt(tbObat.getSelectedRow(),35).toString());
            Perut.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),36).toString());
            KeteranganPerut.setText(tbObat.getValueAt(tbObat.getSelectedRow(),37).toString());
            Ekstremitas.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),38).toString());
            KeteranganEkstrimitas.setText(tbObat.getValueAt(tbObat.getSelectedRow(),39).toString());
            Wajah.setText(tbObat.getValueAt(tbObat.getSelectedRow(),40).toString());
            IntraOral.setText(tbObat.getValueAt(tbObat.getSelectedRow(),41).toString());
            GigiGeligi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),42).toString());
            Laborat.setText(tbObat.getValueAt(tbObat.getSelectedRow(),43).toString());
            Radiologi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),44).toString());
            Penunjang.setText(tbObat.getValueAt(tbObat.getSelectedRow(),45).toString());
            AsesmenKerja.setText(tbObat.getValueAt(tbObat.getSelectedRow(),46).toString());
            AsesmenBanding.setText(tbObat.getValueAt(tbObat.getSelectedRow(),47).toString());
            Permasalahan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),48).toString());
            Terapi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),49).toString());
            Tindakan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),50).toString());
            Edukasi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),51).toString());
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
        BtnSimpan.setEnabled(akses.getpenilaian_awal_medis_ralan_bedah_mulut());
        BtnHapus.setEnabled(akses.getpenilaian_awal_medis_ralan_bedah_mulut());
        BtnEdit.setEnabled(akses.getpenilaian_awal_medis_ralan_bedah_mulut());
        if(akses.getjml2()>=1){
            KdDokter.setEditable(false);
            BtnDokter.setEnabled(false);
            KdDokter.setText(akses.getkode());
            NmDokter.setText(dokter.tampil3(KdDokter.getText()));
            if(NmDokter.getText().equals("")){
                KdDokter.setText("");
                JOptionPane.showMessageDialog(null,"User login bukan Dokter...!!");
            }
        }            
    }
    
    public void setTampil(){
       TabRawat.setSelectedIndex(1);
    }

    private void hapus() {
        if(Sequel.queryu2tf("delete from penilaian_medis_ralan_bedah_mulut where no_rawat=?",1,new String[]{
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
        if(Sequel.mengedittf("penilaian_medis_ralan_bedah_mulut","no_rawat=?","no_rawat=?,tanggal=?,kd_dokter=?,anamnesis=?,hubungan=?,keluhan_utama=?,rps=?,rpk=?,alergi=?,keadaan=?,kesadaran=?,nyeri=?,td=?,nadi=?,suhu=?,rr=?,bb=?,"+
                "tb=?,status_nutrisi=?,kulit=?,keterangan_kulit=?,kepala=?,keterangan_kepala=?,mata=?,keterangan_mata=?,leher=?,keterangan_leher=?,kelenjar=?,keterangan_kelenjar=?,dada=?,keterangan_dada=?,perut=?,keterangan_perut=?,"+
                "ekstremitas=?,keterangan_ekstremitas=?,wajah=?,intra=?,gigigeligi=?,lab=?,rad=?,penunjang=?,diagnosis=?,diagnosis2=?,permasalahan=?,terapi=?,tindakan=?,edukasi=?",48,new String[]{
                TNoRw.getText(),Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),KdDokter.getText(),Anamnesis.getSelectedItem().toString(),Hubungan.getText(),
                KeluhanUtama.getText(),RPS.getText(),RPK.getText(),Alergi.getText(),Keadaan.getSelectedItem().toString(),Kesadaran.getSelectedItem().toString(),Nyeri.getSelectedItem().toString(),TD.getText(),Nadi.getText(), 
                Suhu.getText(),RR.getText(),BB.getText(),TB.getText(),StatusNutrisi.getText(),Kulit.getSelectedItem().toString(),KeteranganKulit.getText(),Kepala.getSelectedItem().toString(),KeteranganKepala.getText(), 
                Mata.getSelectedItem().toString(),KeteranganMata.getText(),Leher.getSelectedItem().toString(),KeteranganLeher.getText(),Kelenjar.getSelectedItem().toString(),KeteranganKelenjar.getText(),Dada.getSelectedItem().toString(), 
                KeteranganDada.getText(),Perut.getSelectedItem().toString(),KeteranganPerut.getText(),Ekstremitas.getSelectedItem().toString(),KeteranganEkstrimitas.getText(),Wajah.getText(),IntraOral.getText(),GigiGeligi.getText(),
                Laborat.getText(),Radiologi.getText(),Penunjang.getText(),AsesmenKerja.getText(),AsesmenBanding.getText(),Permasalahan.getText(),Terapi.getText(),Tindakan.getText(),Edukasi.getText(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
            })==true){
               tbObat.setValueAt(TNoRw.getText(),tbObat.getSelectedRow(),0);
               tbObat.setValueAt(TNoRM.getText(),tbObat.getSelectedRow(),1);
               tbObat.setValueAt(TPasien.getText(),tbObat.getSelectedRow(),2);
               tbObat.setValueAt(TglLahir.getText(),tbObat.getSelectedRow(),3);
               tbObat.setValueAt(Jk.getText().substring(0,1),tbObat.getSelectedRow(),4);
               tbObat.setValueAt(KdDokter.getText(),tbObat.getSelectedRow(),5);
               tbObat.setValueAt(NmDokter.getText(),tbObat.getSelectedRow(),6);
               tbObat.setValueAt(Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),tbObat.getSelectedRow(),7);
               tbObat.setValueAt(Anamnesis.getSelectedItem().toString(),tbObat.getSelectedRow(),8);
               tbObat.setValueAt(Hubungan.getText(),tbObat.getSelectedRow(),9);
               tbObat.setValueAt(KeluhanUtama.getText(),tbObat.getSelectedRow(),10);
               tbObat.setValueAt(RPS.getText(),tbObat.getSelectedRow(),11);
               tbObat.setValueAt(RPK.getText(),tbObat.getSelectedRow(),12);
               tbObat.setValueAt(Alergi.getText(),tbObat.getSelectedRow(),13);
               tbObat.setValueAt(Keadaan.getSelectedItem().toString(),tbObat.getSelectedRow(),14);
               tbObat.setValueAt(Kesadaran.getSelectedItem().toString(),tbObat.getSelectedRow(),15);
               tbObat.setValueAt(Nyeri.getSelectedItem().toString(),tbObat.getSelectedRow(),16);
               tbObat.setValueAt(TD.getText(),tbObat.getSelectedRow(),17);
               tbObat.setValueAt(Nadi.getText(),tbObat.getSelectedRow(),18);
               tbObat.setValueAt(Suhu.getText(),tbObat.getSelectedRow(),19);
               tbObat.setValueAt(RR.getText(),tbObat.getSelectedRow(),20);
               tbObat.setValueAt(BB.getText(),tbObat.getSelectedRow(),21);
               tbObat.setValueAt(TB.getText(),tbObat.getSelectedRow(),22);
               tbObat.setValueAt(StatusNutrisi.getText(),tbObat.getSelectedRow(),23);
               tbObat.setValueAt(Kulit.getSelectedItem().toString(),tbObat.getSelectedRow(),24);
               tbObat.setValueAt(KeteranganKulit.getText(),tbObat.getSelectedRow(),25);
               tbObat.setValueAt(Kepala.getSelectedItem().toString(),tbObat.getSelectedRow(),26);
               tbObat.setValueAt(KeteranganKepala.getText(),tbObat.getSelectedRow(),27);
               tbObat.setValueAt(Mata.getSelectedItem().toString(),tbObat.getSelectedRow(),28);
               tbObat.setValueAt(KeteranganMata.getText(),tbObat.getSelectedRow(),29);
               tbObat.setValueAt(Leher.getSelectedItem().toString(),tbObat.getSelectedRow(),30);
               tbObat.setValueAt(KeteranganLeher.getText(),tbObat.getSelectedRow(),31);
               tbObat.setValueAt(Kelenjar.getSelectedItem().toString(),tbObat.getSelectedRow(),32);
               tbObat.setValueAt(KeteranganKelenjar.getText(),tbObat.getSelectedRow(),33);
               tbObat.setValueAt(Dada.getSelectedItem().toString(),tbObat.getSelectedRow(),34);
               tbObat.setValueAt( KeteranganDada.getText(),tbObat.getSelectedRow(),35);
               tbObat.setValueAt(Perut.getSelectedItem().toString(),tbObat.getSelectedRow(),36);
               tbObat.setValueAt(KeteranganPerut.getText(),tbObat.getSelectedRow(),37);
               tbObat.setValueAt(Ekstremitas.getSelectedItem().toString(),tbObat.getSelectedRow(),38);
               tbObat.setValueAt(KeteranganEkstrimitas.getText(),tbObat.getSelectedRow(),39);
               tbObat.setValueAt(Wajah.getText(),tbObat.getSelectedRow(),40);
               tbObat.setValueAt(IntraOral.getText(),tbObat.getSelectedRow(),41);
               tbObat.setValueAt(GigiGeligi.getText(),tbObat.getSelectedRow(),42);
               tbObat.setValueAt(Laborat.getText(),tbObat.getSelectedRow(),43);
               tbObat.setValueAt(Radiologi.getText(),tbObat.getSelectedRow(),44);
               tbObat.setValueAt(Penunjang.getText(),tbObat.getSelectedRow(),45);
               tbObat.setValueAt(AsesmenKerja.getText(),tbObat.getSelectedRow(),46);
               tbObat.setValueAt(AsesmenBanding.getText(),tbObat.getSelectedRow(),47);
               tbObat.setValueAt(Permasalahan.getText(),tbObat.getSelectedRow(),48);
               tbObat.setValueAt(Terapi.getText(),tbObat.getSelectedRow(),49);
               tbObat.setValueAt(Tindakan.getText(),tbObat.getSelectedRow(),50);
               tbObat.setValueAt(Edukasi.getText(),tbObat.getSelectedRow(),51);
               emptTeks();
               TabRawat.setSelectedIndex(1);
        }
    }
}

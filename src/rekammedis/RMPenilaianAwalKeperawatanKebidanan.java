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
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
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
public final class RMPenilaianAwalKeperawatanKebidanan extends javax.swing.JDialog {
    private final DefaultTableModel tabMode,tabModeRiwayatKehamilan;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;
    private DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    private StringBuilder htmlContent;
    
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public RMPenilaianAwalKeperawatanKebidanan(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.RM","Nama Pasien","J.K.","Agama","Bahasa","Cacat Fisik","Tgl.Lahir","Tgl.Asuhan","Informasi","TD","Nadi","RR","Suhu",
            "GCS","BB","TB","BMI","Keluhan Utama","Riwayat Penyakit Dahulu","Riwayat Penyakit Keluarga","Riwayat Pengobatan",
            "Alergi","Alat Bantu","Ket. Alat Bantu","Prothesa","Ket. Prothesa","ADL","Stts Psikologi","Ket. Psikologi","Hubungan Keluarga","Tinggal Dengan",
            "Ket. Tinggal","Ekonomi","Budaya","Ket. Budaya","Edukasi","Ket. Edukasi","Cara Berjalan A","Cara Berjalan B","Cara Berjalan C",
            "Hasil Penilaian Resiko Jatuh","Lapor Dokter","Ket. Lapor","Skrining Gizi 1","Nilai 1","Skrining Gizi 2","Nilai 2","Total Skor","Tingkat Nyeri","Provokes",
            "Ket. Provokes","Kualitas","Ket. Kualitas","Lokasi","Menyebar","Skala Nyeri","Durasi","Nyeri Hilang","Ket. Hilang Nyeri","Lapor Ke Dokter",
            "Jam Lapor","Rencana","NIP","Nama Petugas"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 65; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(105);
            }else if(i==1){
                column.setPreferredWidth(65);
            }else if(i==2){
                column.setPreferredWidth(160);
            }else if(i==3){
                column.setPreferredWidth(50);
            }else if(i==4){
                column.setPreferredWidth(60);
            }else if(i==5){
                column.setPreferredWidth(90);
            }else if(i==6){
                column.setPreferredWidth(90);
            }else if(i==7){
                column.setPreferredWidth(65);
            }else if(i==8){
                column.setPreferredWidth(120);
            }else if(i==9){
                column.setPreferredWidth(90);
            }else if(i==10){
                column.setPreferredWidth(35);
            }else if(i==11){
                column.setPreferredWidth(40);
            }else if(i==12){
                column.setPreferredWidth(35);
            }else if(i==13){
                column.setPreferredWidth(40);
            }else if(i==14){
                column.setPreferredWidth(35);
            }else if(i==15){
                column.setPreferredWidth(35);
            }else if(i==16){
                column.setPreferredWidth(35);
            }else if(i==17){
                column.setPreferredWidth(35);
            }else if(i==18){
                column.setPreferredWidth(180);
            }else if(i==19){
                column.setPreferredWidth(150);
            }else if(i==20){
                column.setPreferredWidth(150);
            }else if(i==21){
                column.setPreferredWidth(150);
            }else if(i==22){
                column.setPreferredWidth(100);
            }else if(i==23){
                column.setPreferredWidth(60);
            }else if(i==24){
                column.setPreferredWidth(90);
            }else if(i==25){
                column.setPreferredWidth(60);
            }else if(i==26){
                column.setPreferredWidth(90);
            }else if(i==27){
                column.setPreferredWidth(60);
            }else if(i==28){
                column.setPreferredWidth(80);
            }else if(i==29){
                column.setPreferredWidth(100);
            }else if(i==30){
                column.setPreferredWidth(103);
            }else if(i==31){
                column.setPreferredWidth(87);
            }else if(i==32){
                column.setPreferredWidth(90);
            }else if(i==33){
                column.setPreferredWidth(50);
            }else if(i==34){
                column.setPreferredWidth(58);
            }else if(i==35){
                column.setPreferredWidth(90);
            }else if(i==36){
                column.setPreferredWidth(60);
            }else if(i==37){
                column.setPreferredWidth(90);
            }else if(i==38){
                column.setPreferredWidth(87);
            }else if(i==39){
                column.setPreferredWidth(87);
            }else if(i==40){
                column.setPreferredWidth(87);
            }else if(i==41){
                column.setPreferredWidth(206);
            }else if(i==42){
                column.setPreferredWidth(75);
            }else if(i==43){
                column.setPreferredWidth(90);
            }else if(i==44){
                column.setPreferredWidth(80);
            }else if(i==45){
                column.setPreferredWidth(40);
            }else if(i==46){
                column.setPreferredWidth(80);
            }else if(i==47){
                column.setPreferredWidth(40);
            }else if(i==48){
                column.setPreferredWidth(60);
            }else if(i==49){
                column.setPreferredWidth(87);
            }else if(i==50){
                column.setPreferredWidth(87);
            }else if(i==51){
                column.setPreferredWidth(87);
            }else if(i==52){
                column.setPreferredWidth(90);
            }else if(i==53){
                column.setPreferredWidth(90);
            }else if(i==54){
                column.setPreferredWidth(110);
            }else if(i==55){
                column.setPreferredWidth(56);
            }else if(i==56){
                column.setPreferredWidth(60);
            }else if(i==57){
                column.setPreferredWidth(50);
            }else if(i==58){
                column.setPreferredWidth(90);
            }else if(i==59){
                column.setPreferredWidth(90);
            }else if(i==60){
                column.setPreferredWidth(90);
            }else if(i==61){
                column.setPreferredWidth(70);
            }else if(i==62){
                column.setPreferredWidth(200);
            }else if(i==63){
                column.setPreferredWidth(80);
            }else if(i==64){
                column.setPreferredWidth(150);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeRiwayatKehamilan=new DefaultTableModel(null,new Object[]{
            "No","Tgl/Thn","Tempat Persalinan","Usia Hamil","Jenis Persalinan","Penolong","Penyulit","J.K.","BB/PB","Keadaan"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbRiwayatKehamilan.setModel(tabModeRiwayatKehamilan);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbRiwayatKehamilan.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbRiwayatKehamilan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 10; i++) {
            TableColumn column = tbRiwayatKehamilan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(30);
            }else if(i==1){
                column.setPreferredWidth(65);
            }else if(i==2){
                column.setPreferredWidth(170);
            }else if(i==3){
                column.setPreferredWidth(65);
            }else if(i==4){
                column.setPreferredWidth(100);
            }else if(i==5){
                column.setPreferredWidth(150);
            }else if(i==6){
                column.setPreferredWidth(150);
            }else if(i==7){
                column.setPreferredWidth(30);
            }else if(i==8){
                column.setPreferredWidth(60);
            }else if(i==9){
                column.setPreferredWidth(150);
            }
        }
        tbRiwayatKehamilan.setDefaultRenderer(Object.class, new WarnaTable());
        
        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        TD.setDocument(new batasInput((byte)5).getKata(TD));
        Nadi.setDocument(new batasInput((byte)5).getKata(Nadi));
        RR.setDocument(new batasInput((byte)5).getKata(RR));
        Suhu.setDocument(new batasInput((byte)5).getKata(Suhu));
        GCS.setDocument(new batasInput((byte)5).getKata(GCS));
        BB.setDocument(new batasInput((byte)5).getKata(BB));
        TB.setDocument(new batasInput((byte)5).getKata(TB));
        BMI.setDocument(new batasInput((byte)5).getKata(BMI));
        
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
        
        
        ChkAccor.setSelected(false);
        isMenu();
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
        KdPetugas = new widget.TextBox();
        NmPetugas = new widget.TextBox();
        BtnDokter = new widget.Button();
        jLabel8 = new widget.Label();
        TglLahir = new widget.TextBox();
        Jk = new widget.TextBox();
        jLabel10 = new widget.Label();
        label11 = new widget.Label();
        jLabel11 = new widget.Label();
        jLabel12 = new widget.Label();
        BB = new widget.TextBox();
        jLabel13 = new widget.Label();
        TB = new widget.TextBox();
        jLabel15 = new widget.Label();
        jLabel16 = new widget.Label();
        Nadi = new widget.TextBox();
        jLabel17 = new widget.Label();
        jLabel18 = new widget.Label();
        Suhu = new widget.TextBox();
        jLabel22 = new widget.Label();
        TD = new widget.TextBox();
        jLabel20 = new widget.Label();
        jLabel23 = new widget.Label();
        jLabel24 = new widget.Label();
        jLabel25 = new widget.Label();
        RR = new widget.TextBox();
        jLabel26 = new widget.Label();
        jLabel14 = new widget.Label();
        BMI = new widget.TextBox();
        jLabel27 = new widget.Label();
        jLabel36 = new widget.Label();
        Informasi = new widget.ComboBox();
        jLabel53 = new widget.Label();
        TglAsuhan = new widget.Tanggal();
        jLabel28 = new widget.Label();
        GCS = new widget.TextBox();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel29 = new widget.Label();
        LILA = new widget.TextBox();
        jLabel30 = new widget.Label();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel54 = new widget.Label();
        jLabel31 = new widget.Label();
        TFU = new widget.TextBox();
        jLabel32 = new widget.Label();
        jLabel33 = new widget.Label();
        TBJ = new widget.TextBox();
        jLabel37 = new widget.Label();
        Letak = new widget.TextBox();
        Presentasi = new widget.TextBox();
        jLabel38 = new widget.Label();
        jLabel39 = new widget.Label();
        Penurunan = new widget.TextBox();
        jLabel40 = new widget.Label();
        Kontraksi = new widget.TextBox();
        jLabel41 = new widget.Label();
        Kekuatan = new widget.TextBox();
        jLabel42 = new widget.Label();
        jLabel43 = new widget.Label();
        Lamanya = new widget.TextBox();
        jLabel44 = new widget.Label();
        jLabel45 = new widget.Label();
        BJJ = new widget.TextBox();
        jLabel35 = new widget.Label();
        KeteranganBJJ = new widget.ComboBox();
        jLabel46 = new widget.Label();
        Portio = new widget.TextBox();
        LabelServiks = new widget.Label();
        PembukaanServiks = new widget.TextBox();
        jLabel48 = new widget.Label();
        jLabel49 = new widget.Label();
        Ketuban = new widget.TextBox();
        jLabel50 = new widget.Label();
        jLabel51 = new widget.Label();
        Hodge = new widget.TextBox();
        jLabel52 = new widget.Label();
        jLabel55 = new widget.Label();
        KeteranganInspekulo = new widget.TextBox();
        Inspekulo = new widget.ComboBox();
        jLabel56 = new widget.Label();
        jLabel57 = new widget.Label();
        CTG = new widget.ComboBox();
        jLabel58 = new widget.Label();
        KeteranganCTG = new widget.TextBox();
        jLabel59 = new widget.Label();
        Laboratorium = new widget.ComboBox();
        jLabel60 = new widget.Label();
        KeteranganLaboratorium = new widget.TextBox();
        jLabel61 = new widget.Label();
        StatusKawin1 = new widget.ComboBox();
        jLabel62 = new widget.Label();
        KeteranganUSG = new widget.TextBox();
        jLabel63 = new widget.Label();
        Lakmus = new widget.ComboBox();
        jLabel64 = new widget.Label();
        KeteranganLakmus = new widget.TextBox();
        jLabel65 = new widget.Label();
        PemeriksaanPanggul = new widget.ComboBox();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel94 = new widget.Label();
        jLabel47 = new widget.Label();
        scrollPane1 = new widget.ScrollPane();
        KeluhanUtama = new widget.TextArea();
        jLabel66 = new widget.Label();
        jLabel67 = new widget.Label();
        Umur = new widget.TextBox();
        jLabel68 = new widget.Label();
        jLabel69 = new widget.Label();
        Lama = new widget.TextBox();
        jLabel70 = new widget.Label();
        jLabel71 = new widget.Label();
        Banyaknya = new widget.TextBox();
        jLabel72 = new widget.Label();
        HaidTerakhir = new widget.TextBox();
        jLabel73 = new widget.Label();
        jLabel74 = new widget.Label();
        Siklus = new widget.TextBox();
        jLabel75 = new widget.Label();
        USG = new widget.ComboBox();
        KetSiklus1 = new widget.ComboBox();
        jLabel76 = new widget.Label();
        jLabel78 = new widget.Label();
        jLabel79 = new widget.Label();
        jLabel80 = new widget.Label();
        StatusMenikah = new widget.ComboBox();
        jLabel77 = new widget.Label();
        jLabel81 = new widget.Label();
        UsiaKawin1 = new widget.TextBox();
        jLabel82 = new widget.Label();
        KetSiklus2 = new widget.ComboBox();
        jLabel83 = new widget.Label();
        jLabel84 = new widget.Label();
        UsiaKawin3 = new widget.TextBox();
        jLabel85 = new widget.Label();
        jLabel86 = new widget.Label();
        StatusKawin3 = new widget.ComboBox();
        jLabel87 = new widget.Label();
        UsiaKawin2 = new widget.TextBox();
        StatusKawin2 = new widget.ComboBox();
        jLabel88 = new widget.Label();
        jLabel89 = new widget.Label();
        KaliMenikah = new widget.TextBox();
        jLabel90 = new widget.Label();
        jLabel91 = new widget.Label();
        jLabel92 = new widget.Label();
        HPHT = new widget.Tanggal();
        jLabel93 = new widget.Label();
        UsiaKehamilan = new widget.TextBox();
        jLabel95 = new widget.Label();
        jLabel96 = new widget.Label();
        TP = new widget.Tanggal();
        jLabel97 = new widget.Label();
        RiwayatImunisasi = new widget.ComboBox();
        jLabel98 = new widget.Label();
        JumlahImunisasi = new widget.TextBox();
        jLabel101 = new widget.Label();
        G = new widget.TextBox();
        jLabel100 = new widget.Label();
        jLabel102 = new widget.Label();
        P = new widget.TextBox();
        jLabel103 = new widget.Label();
        A = new widget.TextBox();
        jLabel104 = new widget.Label();
        Hidup = new widget.TextBox();
        Scroll6 = new widget.ScrollPane();
        tbRiwayatKehamilan = new widget.Table();
        BtnTambahMasalah = new widget.Button();
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
        FormMenu = new widget.PanelBiasa();
        jLabel34 = new widget.Label();
        TNoRM1 = new widget.TextBox();
        TPasien1 = new widget.TextBox();
        BtnPrint1 = new widget.Button();
        FormMasalahRencana = new widget.PanelBiasa();
        scrollPane7 = new widget.ScrollPane();
        MasalahKebidanan = new widget.TextArea();
        scrollPane6 = new widget.ScrollPane();
        TindakanKebidanan = new widget.TextArea();

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Penilaian Awal Pasien Rawat Jalan Kebidanan & Kandungan ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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
        FormInput.setPreferredSize(new java.awt.Dimension(100, 1183));
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

        label14.setText("Petugas :");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label14);
        label14.setBounds(0, 40, 70, 23);

        KdPetugas.setEditable(false);
        KdPetugas.setName("KdPetugas"); // NOI18N
        KdPetugas.setPreferredSize(new java.awt.Dimension(80, 23));
        KdPetugas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdPetugasKeyPressed(evt);
            }
        });
        FormInput.add(KdPetugas);
        KdPetugas.setBounds(74, 40, 100, 23);

        NmPetugas.setEditable(false);
        NmPetugas.setName("NmPetugas"); // NOI18N
        NmPetugas.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmPetugas);
        NmPetugas.setBounds(176, 40, 180, 23);

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
        BtnDokter.setBounds(358, 40, 28, 23);

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

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label11);
        label11.setBounds(395, 40, 57, 23);

        jLabel11.setText("J.K. :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(740, 10, 30, 23);

        jLabel12.setText("BB :");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(0, 120, 70, 23);

        BB.setFocusTraversalPolicyProvider(true);
        BB.setName("BB"); // NOI18N
        BB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BBKeyPressed(evt);
            }
        });
        FormInput.add(BB);
        BB.setBounds(74, 120, 60, 23);

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel13.setText("Kg");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(137, 120, 30, 23);

        TB.setFocusTraversalPolicyProvider(true);
        TB.setName("TB"); // NOI18N
        TB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TBKeyPressed(evt);
            }
        });
        FormInput.add(TB);
        TB.setBounds(250, 120, 60, 23);

        jLabel15.setText("TB :");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(206, 120, 40, 23);

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel16.setText("x/menit");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput.add(jLabel16);
        jLabel16.setBounds(313, 90, 50, 23);

        Nadi.setFocusTraversalPolicyProvider(true);
        Nadi.setName("Nadi"); // NOI18N
        Nadi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NadiKeyPressed(evt);
            }
        });
        FormInput.add(Nadi);
        Nadi.setBounds(250, 90, 60, 23);

        jLabel17.setText("Nadi :");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(206, 90, 40, 23);

        jLabel18.setText("Suhu :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(566, 90, 40, 23);

        Suhu.setFocusTraversalPolicyProvider(true);
        Suhu.setName("Suhu"); // NOI18N
        Suhu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SuhuKeyPressed(evt);
            }
        });
        FormInput.add(Suhu);
        Suhu.setBounds(610, 90, 60, 23);

        jLabel22.setText("TD :");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(0, 90, 70, 23);

        TD.setFocusTraversalPolicyProvider(true);
        TD.setName("TD"); // NOI18N
        TD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TDKeyPressed(evt);
            }
        });
        FormInput.add(TD);
        TD.setBounds(74, 90, 60, 23);

        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel20.setText("°C");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(673, 90, 30, 23);

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel23.setText("mmHg");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(137, 90, 50, 23);

        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel24.setText("m");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(313, 120, 30, 23);

        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel25.setText("x/menit");
        jLabel25.setName("jLabel25"); // NOI18N
        FormInput.add(jLabel25);
        jLabel25.setBounds(485, 90, 50, 23);

        RR.setFocusTraversalPolicyProvider(true);
        RR.setName("RR"); // NOI18N
        RR.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RRKeyPressed(evt);
            }
        });
        FormInput.add(RR);
        RR.setBounds(422, 90, 60, 23);

        jLabel26.setText("RR :");
        jLabel26.setName("jLabel26"); // NOI18N
        FormInput.add(jLabel26);
        jLabel26.setBounds(378, 90, 40, 23);

        jLabel14.setText("BMI :");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(566, 120, 40, 23);

        BMI.setFocusTraversalPolicyProvider(true);
        BMI.setName("BMI"); // NOI18N
        BMI.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BMIKeyPressed(evt);
            }
        });
        FormInput.add(BMI);
        BMI.setBounds(610, 120, 60, 23);

        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel27.setText("tahun,");
        jLabel27.setName("jLabel27"); // NOI18N
        FormInput.add(jLabel27);
        jLabel27.setBounds(237, 450, 40, 23);

        jLabel36.setText("Informasi didapat dari :");
        jLabel36.setName("jLabel36"); // NOI18N
        FormInput.add(jLabel36);
        jLabel36.setBounds(592, 40, 130, 23);

        Informasi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Autoanamnesis", "Alloanamnesis" }));
        Informasi.setName("Informasi"); // NOI18N
        Informasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                InformasiKeyPressed(evt);
            }
        });
        FormInput.add(Informasi);
        Informasi.setBounds(726, 40, 128, 23);

        jLabel53.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel53.setText("I. KEADAAN UMUM");
        jLabel53.setName("jLabel53"); // NOI18N
        FormInput.add(jLabel53);
        jLabel53.setBounds(10, 70, 180, 23);

        TglAsuhan.setForeground(new java.awt.Color(50, 70, 50));
        TglAsuhan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "11-05-2020 10:38:42" }));
        TglAsuhan.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        TglAsuhan.setName("TglAsuhan"); // NOI18N
        TglAsuhan.setOpaque(false);
        TglAsuhan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglAsuhanKeyPressed(evt);
            }
        });
        FormInput.add(TglAsuhan);
        TglAsuhan.setBounds(456, 40, 130, 23);

        jLabel28.setText("GCS(E,V,M) :");
        jLabel28.setName("jLabel28"); // NOI18N
        FormInput.add(jLabel28);
        jLabel28.setBounds(700, 90, 90, 23);

        GCS.setFocusTraversalPolicyProvider(true);
        GCS.setName("GCS"); // NOI18N
        GCS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GCSKeyPressed(evt);
            }
        });
        FormInput.add(GCS);
        GCS.setBounds(794, 90, 60, 23);

        jSeparator1.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator1.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator1.setName("jSeparator1"); // NOI18N
        FormInput.add(jSeparator1);
        jSeparator1.setBounds(0, 70, 880, 1);

        jLabel29.setText("LILA :");
        jLabel29.setName("jLabel29"); // NOI18N
        FormInput.add(jLabel29);
        jLabel29.setBounds(378, 120, 40, 23);

        LILA.setFocusTraversalPolicyProvider(true);
        LILA.setName("LILA"); // NOI18N
        LILA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LILAKeyPressed(evt);
            }
        });
        FormInput.add(LILA);
        LILA.setBounds(422, 120, 60, 23);

        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel30.setText("cm");
        jLabel30.setName("jLabel30"); // NOI18N
        FormInput.add(jLabel30);
        jLabel30.setBounds(485, 120, 50, 23);

        jSeparator2.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator2.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator2.setName("jSeparator2"); // NOI18N
        FormInput.add(jSeparator2);
        jSeparator2.setBounds(0, 150, 880, 1);

        jLabel54.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel54.setText("II. PEMERIKSAAN KEBIDANAN");
        jLabel54.setName("jLabel54"); // NOI18N
        FormInput.add(jLabel54);
        jLabel54.setBounds(10, 150, 180, 23);

        jLabel31.setText("TFU :");
        jLabel31.setName("jLabel31"); // NOI18N
        FormInput.add(jLabel31);
        jLabel31.setBounds(0, 170, 70, 23);

        TFU.setFocusTraversalPolicyProvider(true);
        TFU.setName("TFU"); // NOI18N
        TFU.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TFUKeyPressed(evt);
            }
        });
        FormInput.add(TFU);
        TFU.setBounds(74, 170, 60, 23);

        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel32.setText("detik");
        jLabel32.setName("jLabel32"); // NOI18N
        FormInput.add(jLabel32);
        jLabel32.setBounds(467, 200, 30, 23);

        jLabel33.setText("TBJ :");
        jLabel33.setName("jLabel33"); // NOI18N
        FormInput.add(jLabel33);
        jLabel33.setBounds(206, 170, 40, 23);

        TBJ.setFocusTraversalPolicyProvider(true);
        TBJ.setName("TBJ"); // NOI18N
        TBJ.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TBJKeyPressed(evt);
            }
        });
        FormInput.add(TBJ);
        TBJ.setBounds(250, 170, 60, 23);

        jLabel37.setText("Letak :");
        jLabel37.setName("jLabel37"); // NOI18N
        FormInput.add(jLabel37);
        jLabel37.setBounds(378, 170, 40, 23);

        Letak.setFocusTraversalPolicyProvider(true);
        Letak.setName("Letak"); // NOI18N
        Letak.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LetakKeyPressed(evt);
            }
        });
        FormInput.add(Letak);
        Letak.setBounds(422, 170, 60, 23);

        Presentasi.setFocusTraversalPolicyProvider(true);
        Presentasi.setName("Presentasi"); // NOI18N
        Presentasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PresentasiKeyPressed(evt);
            }
        });
        FormInput.add(Presentasi);
        Presentasi.setBounds(610, 170, 60, 23);

        jLabel38.setText("Presentasi :");
        jLabel38.setName("jLabel38"); // NOI18N
        FormInput.add(jLabel38);
        jLabel38.setBounds(526, 170, 80, 23);

        jLabel39.setText("Penurunan :");
        jLabel39.setName("jLabel39"); // NOI18N
        FormInput.add(jLabel39);
        jLabel39.setBounds(700, 170, 90, 23);

        Penurunan.setFocusTraversalPolicyProvider(true);
        Penurunan.setName("Penurunan"); // NOI18N
        Penurunan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenurunanKeyPressed(evt);
            }
        });
        FormInput.add(Penurunan);
        Penurunan.setBounds(794, 170, 60, 23);

        jLabel40.setText("Kontraksi/HIS :");
        jLabel40.setName("jLabel40"); // NOI18N
        FormInput.add(jLabel40);
        jLabel40.setBounds(0, 200, 117, 23);

        Kontraksi.setFocusTraversalPolicyProvider(true);
        Kontraksi.setName("Kontraksi"); // NOI18N
        Kontraksi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KontraksiKeyPressed(evt);
            }
        });
        FormInput.add(Kontraksi);
        Kontraksi.setBounds(121, 200, 60, 23);

        jLabel41.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel41.setText("x/10’,");
        jLabel41.setName("jLabel41"); // NOI18N
        FormInput.add(jLabel41);
        jLabel41.setBounds(184, 200, 40, 23);

        Kekuatan.setFocusTraversalPolicyProvider(true);
        Kekuatan.setName("Kekuatan"); // NOI18N
        Kekuatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KekuatanKeyPressed(evt);
            }
        });
        FormInput.add(Kekuatan);
        Kekuatan.setBounds(278, 200, 60, 23);

        jLabel42.setText("Kekuatan :");
        jLabel42.setName("jLabel42"); // NOI18N
        FormInput.add(jLabel42);
        jLabel42.setBounds(206, 200, 68, 23);

        jLabel43.setText("Lamanya :");
        jLabel43.setName("jLabel43"); // NOI18N
        FormInput.add(jLabel43);
        jLabel43.setBounds(340, 200, 60, 23);

        Lamanya.setFocusTraversalPolicyProvider(true);
        Lamanya.setName("Lamanya"); // NOI18N
        Lamanya.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LamanyaKeyPressed(evt);
            }
        });
        FormInput.add(Lamanya);
        Lamanya.setBounds(404, 200, 60, 23);

        jLabel44.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel44.setText("cm");
        jLabel44.setName("jLabel44"); // NOI18N
        FormInput.add(jLabel44);
        jLabel44.setBounds(137, 170, 25, 23);

        jLabel45.setText("Gerak janin x/30 menit, BJJ :");
        jLabel45.setName("jLabel45"); // NOI18N
        FormInput.add(jLabel45);
        jLabel45.setBounds(493, 200, 150, 23);

        BJJ.setFocusTraversalPolicyProvider(true);
        BJJ.setName("BJJ"); // NOI18N
        BJJ.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BJJKeyPressed(evt);
            }
        });
        FormInput.add(BJJ);
        BJJ.setBounds(647, 200, 60, 23);

        jLabel35.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel35.setText("/mnt");
        jLabel35.setName("jLabel35"); // NOI18N
        FormInput.add(jLabel35);
        jLabel35.setBounds(710, 200, 30, 23);

        KeteranganBJJ.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Teratur", "Tidak Teratur" }));
        KeteranganBJJ.setName("KeteranganBJJ"); // NOI18N
        KeteranganBJJ.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganBJJKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganBJJ);
        KeteranganBJJ.setBounds(739, 200, 115, 23);

        jLabel46.setText("Portio :");
        jLabel46.setName("jLabel46"); // NOI18N
        FormInput.add(jLabel46);
        jLabel46.setBounds(0, 230, 80, 23);

        Portio.setFocusTraversalPolicyProvider(true);
        Portio.setName("Portio"); // NOI18N
        Portio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PortioKeyPressed(evt);
            }
        });
        FormInput.add(Portio);
        Portio.setBounds(84, 230, 60, 23);

        LabelServiks.setText("Pembukaan Serviks :");
        LabelServiks.setName("LabelServiks"); // NOI18N
        FormInput.add(LabelServiks);
        LabelServiks.setBounds(169, 230, 110, 23);

        PembukaanServiks.setFocusTraversalPolicyProvider(true);
        PembukaanServiks.setName("PembukaanServiks"); // NOI18N
        PembukaanServiks.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PembukaanServiksKeyPressed(evt);
            }
        });
        FormInput.add(PembukaanServiks);
        PembukaanServiks.setBounds(283, 230, 60, 23);

        jLabel48.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel48.setText("cm ");
        jLabel48.setName("jLabel48"); // NOI18N
        FormInput.add(jLabel48);
        jLabel48.setBounds(346, 230, 25, 23);

        jLabel49.setText("Ketuban :");
        jLabel49.setName("jLabel49"); // NOI18N
        FormInput.add(jLabel49);
        jLabel49.setBounds(390, 230, 60, 23);

        Ketuban.setFocusTraversalPolicyProvider(true);
        Ketuban.setName("Ketuban"); // NOI18N
        Ketuban.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetubanKeyPressed(evt);
            }
        });
        FormInput.add(Ketuban);
        Ketuban.setBounds(454, 230, 60, 23);

        jLabel50.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel50.setText("kep / bok");
        jLabel50.setName("jLabel50"); // NOI18N
        FormInput.add(jLabel50);
        jLabel50.setBounds(517, 230, 60, 23);

        jLabel51.setText("Hodge :");
        jLabel51.setName("jLabel51"); // NOI18N
        FormInput.add(jLabel51);
        jLabel51.setBounds(563, 230, 80, 23);

        Hodge.setFocusTraversalPolicyProvider(true);
        Hodge.setName("Hodge"); // NOI18N
        Hodge.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HodgeKeyPressed(evt);
            }
        });
        FormInput.add(Hodge);
        Hodge.setBounds(647, 230, 60, 23);

        jLabel52.setText("Pemeriksaan penunjang :");
        jLabel52.setName("jLabel52"); // NOI18N
        FormInput.add(jLabel52);
        jLabel52.setBounds(0, 260, 167, 23);

        jLabel55.setText("Hasil :");
        jLabel55.setName("jLabel55"); // NOI18N
        FormInput.add(jLabel55);
        jLabel55.setBounds(255, 280, 38, 23);

        KeteranganInspekulo.setFocusTraversalPolicyProvider(true);
        KeteranganInspekulo.setName("KeteranganInspekulo"); // NOI18N
        KeteranganInspekulo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganInspekuloKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganInspekulo);
        KeteranganInspekulo.setBounds(297, 280, 175, 23);

        Inspekulo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Dilakukan", "Tidak" }));
        Inspekulo.setName("Inspekulo"); // NOI18N
        Inspekulo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                InspekuloKeyPressed(evt);
            }
        });
        FormInput.add(Inspekulo);
        Inspekulo.setBounds(154, 280, 100, 23);

        jLabel56.setText("Inspekulo :");
        jLabel56.setName("jLabel56"); // NOI18N
        FormInput.add(jLabel56);
        jLabel56.setBounds(70, 280, 80, 23);

        jLabel57.setText("CTG :");
        jLabel57.setName("jLabel57"); // NOI18N
        FormInput.add(jLabel57);
        jLabel57.setBounds(477, 280, 55, 23);

        CTG.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Dilakukan", "Tidak" }));
        CTG.setName("CTG"); // NOI18N
        CTG.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CTGKeyPressed(evt);
            }
        });
        FormInput.add(CTG);
        CTG.setBounds(536, 280, 100, 23);

        jLabel58.setText("Hasil :");
        jLabel58.setName("jLabel58"); // NOI18N
        FormInput.add(jLabel58);
        jLabel58.setBounds(637, 280, 38, 23);

        KeteranganCTG.setFocusTraversalPolicyProvider(true);
        KeteranganCTG.setName("KeteranganCTG"); // NOI18N
        KeteranganCTG.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganCTGKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganCTG);
        KeteranganCTG.setBounds(679, 280, 175, 23);

        jLabel59.setText("Laboratorium :");
        jLabel59.setName("jLabel59"); // NOI18N
        FormInput.add(jLabel59);
        jLabel59.setBounds(60, 310, 90, 23);

        Laboratorium.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Dilakukan", "Tidak" }));
        Laboratorium.setName("Laboratorium"); // NOI18N
        Laboratorium.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LaboratoriumKeyPressed(evt);
            }
        });
        FormInput.add(Laboratorium);
        Laboratorium.setBounds(154, 310, 100, 23);

        jLabel60.setText("Hasil :");
        jLabel60.setName("jLabel60"); // NOI18N
        FormInput.add(jLabel60);
        jLabel60.setBounds(255, 310, 38, 23);

        KeteranganLaboratorium.setFocusTraversalPolicyProvider(true);
        KeteranganLaboratorium.setName("KeteranganLaboratorium"); // NOI18N
        KeteranganLaboratorium.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganLaboratoriumKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganLaboratorium);
        KeteranganLaboratorium.setBounds(297, 310, 175, 23);

        jLabel61.setText("USG :");
        jLabel61.setName("jLabel61"); // NOI18N
        FormInput.add(jLabel61);
        jLabel61.setBounds(477, 310, 55, 23);

        StatusKawin1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Masih Menikah", "Cerai", "Meninggal" }));
        StatusKawin1.setName("StatusKawin1"); // NOI18N
        StatusKawin1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                StatusKawin1KeyPressed(evt);
            }
        });
        FormInput.add(StatusKawin1);
        StatusKawin1.setBounds(729, 530, 125, 23);

        jLabel62.setText("Hasil :");
        jLabel62.setName("jLabel62"); // NOI18N
        FormInput.add(jLabel62);
        jLabel62.setBounds(637, 310, 38, 23);

        KeteranganUSG.setFocusTraversalPolicyProvider(true);
        KeteranganUSG.setName("KeteranganUSG"); // NOI18N
        KeteranganUSG.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganUSGKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganUSG);
        KeteranganUSG.setBounds(679, 310, 175, 23);

        jLabel63.setText("Lakmus :");
        jLabel63.setName("jLabel63"); // NOI18N
        FormInput.add(jLabel63);
        jLabel63.setBounds(60, 340, 90, 23);

        Lakmus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Dilakukan", "Tidak" }));
        Lakmus.setName("Lakmus"); // NOI18N
        Lakmus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LakmusKeyPressed(evt);
            }
        });
        FormInput.add(Lakmus);
        Lakmus.setBounds(154, 340, 100, 23);

        jLabel64.setText("Hasil :");
        jLabel64.setName("jLabel64"); // NOI18N
        FormInput.add(jLabel64);
        jLabel64.setBounds(255, 340, 38, 23);

        KeteranganLakmus.setFocusTraversalPolicyProvider(true);
        KeteranganLakmus.setName("KeteranganLakmus"); // NOI18N
        KeteranganLakmus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganLakmusKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganLakmus);
        KeteranganLakmus.setBounds(297, 340, 175, 23);

        jLabel65.setText("Pemeriksaan Panggul :");
        jLabel65.setName("jLabel65"); // NOI18N
        FormInput.add(jLabel65);
        jLabel65.setBounds(485, 340, 170, 23);

        PemeriksaanPanggul.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Luas", "Sedang", "Sempit", "Tidak Dilakukan Pemeriksaan" }));
        PemeriksaanPanggul.setName("PemeriksaanPanggul"); // NOI18N
        PemeriksaanPanggul.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PemeriksaanPanggulKeyPressed(evt);
            }
        });
        FormInput.add(PemeriksaanPanggul);
        PemeriksaanPanggul.setBounds(659, 340, 195, 23);

        jSeparator3.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator3.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator3.setName("jSeparator3"); // NOI18N
        FormInput.add(jSeparator3);
        jSeparator3.setBounds(0, 370, 880, 1);

        jLabel94.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel94.setText("III. RIWAYAT KESEHATAN");
        jLabel94.setName("jLabel94"); // NOI18N
        FormInput.add(jLabel94);
        jLabel94.setBounds(10, 370, 180, 23);

        jLabel47.setText("Keluhan Utama :");
        jLabel47.setName("jLabel47"); // NOI18N
        FormInput.add(jLabel47);
        jLabel47.setBounds(0, 390, 125, 20);

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
        scrollPane1.setBounds(129, 390, 725, 35);

        jLabel66.setText("Riwayat Perkawinan :");
        jLabel66.setName("jLabel66"); // NOI18N
        FormInput.add(jLabel66);
        jLabel66.setBounds(0, 510, 149, 23);

        jLabel67.setText("Umur Menarche :");
        jLabel67.setName("jLabel67"); // NOI18N
        FormInput.add(jLabel67);
        jLabel67.setBounds(0, 450, 170, 23);

        Umur.setFocusTraversalPolicyProvider(true);
        Umur.setName("Umur"); // NOI18N
        Umur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                UmurKeyPressed(evt);
            }
        });
        FormInput.add(Umur);
        Umur.setBounds(174, 450, 60, 23);

        jLabel68.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel68.setText("Kg/m²");
        jLabel68.setName("jLabel68"); // NOI18N
        FormInput.add(jLabel68);
        jLabel68.setBounds(673, 120, 50, 23);

        jLabel69.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel69.setText(")");
        jLabel69.setName("jLabel69"); // NOI18N
        FormInput.add(jLabel69);
        jLabel69.setBounds(393, 480, 28, 23);

        Lama.setFocusTraversalPolicyProvider(true);
        Lama.setName("Lama"); // NOI18N
        Lama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LamaKeyPressed(evt);
            }
        });
        FormInput.add(Lama);
        Lama.setBounds(324, 450, 60, 23);

        jLabel70.setText("(");
        jLabel70.setName("jLabel70"); // NOI18N
        FormInput.add(jLabel70);
        jLabel70.setBounds(247, 480, 20, 23);

        jLabel71.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel71.setText("tahun,");
        jLabel71.setName("jLabel71"); // NOI18N
        FormInput.add(jLabel71);
        jLabel71.setBounds(652, 530, 45, 23);

        Banyaknya.setFocusTraversalPolicyProvider(true);
        Banyaknya.setName("Banyaknya"); // NOI18N
        Banyaknya.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BanyaknyaKeyPressed(evt);
            }
        });
        FormInput.add(Banyaknya);
        Banyaknya.setBounds(475, 450, 60, 23);

        jLabel72.setText("Masalah yang dirasakan saat menstruasi :");
        jLabel72.setName("jLabel72"); // NOI18N
        FormInput.add(jLabel72);
        jLabel72.setBounds(415, 480, 260, 23);

        HaidTerakhir.setFocusTraversalPolicyProvider(true);
        HaidTerakhir.setName("HaidTerakhir"); // NOI18N
        HaidTerakhir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HaidTerakhirKeyPressed(evt);
            }
        });
        FormInput.add(HaidTerakhir);
        HaidTerakhir.setBounds(679, 450, 175, 23);

        jLabel73.setText("Haid Terakhir :");
        jLabel73.setName("jLabel73"); // NOI18N
        FormInput.add(jLabel73);
        jLabel73.setBounds(585, 450, 90, 23);

        jLabel74.setText("Siklus :");
        jLabel74.setName("jLabel74"); // NOI18N
        FormInput.add(jLabel74);
        jLabel74.setBounds(0, 480, 170, 23);

        Siklus.setFocusTraversalPolicyProvider(true);
        Siklus.setName("Siklus"); // NOI18N
        Siklus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SiklusKeyPressed(evt);
            }
        });
        FormInput.add(Siklus);
        Siklus.setBounds(174, 480, 60, 23);

        jLabel75.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel75.setText("hari,");
        jLabel75.setName("jLabel75"); // NOI18N
        FormInput.add(jLabel75);
        jLabel75.setBounds(237, 480, 30, 23);

        USG.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Dilakukan", "Tidak" }));
        USG.setName("USG"); // NOI18N
        USG.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                USGKeyPressed(evt);
            }
        });
        FormInput.add(USG);
        USG.setBounds(536, 310, 100, 23);

        KetSiklus1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Teratur", "Tidak Teratur" }));
        KetSiklus1.setName("KetSiklus1"); // NOI18N
        KetSiklus1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetSiklus1KeyPressed(evt);
            }
        });
        FormInput.add(KetSiklus1);
        KetSiklus1.setBounds(270, 480, 120, 23);

        jLabel76.setText("Riwayat Menstruasi :");
        jLabel76.setName("jLabel76"); // NOI18N
        FormInput.add(jLabel76);
        jLabel76.setBounds(0, 430, 146, 23);

        jLabel78.setText("banyaknya :");
        jLabel78.setName("jLabel78"); // NOI18N
        FormInput.add(jLabel78);
        jLabel78.setBounds(405, 450, 66, 23);

        jLabel79.setText("lamanya :");
        jLabel79.setName("jLabel79"); // NOI18N
        FormInput.add(jLabel79);
        jLabel79.setBounds(265, 450, 55, 23);

        jLabel80.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel80.setText("hari,");
        jLabel80.setName("jLabel80"); // NOI18N
        FormInput.add(jLabel80);
        jLabel80.setBounds(387, 450, 30, 23);

        StatusMenikah.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Menikah", "Tidak / Belum Menikah" }));
        StatusMenikah.setName("StatusMenikah"); // NOI18N
        StatusMenikah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                StatusMenikahKeyPressed(evt);
            }
        });
        FormInput.add(StatusMenikah);
        StatusMenikah.setBounds(174, 530, 175, 23);

        jLabel77.setText("Status Menikah :");
        jLabel77.setName("jLabel77"); // NOI18N
        FormInput.add(jLabel77);
        jLabel77.setBounds(0, 530, 170, 23);

        jLabel81.setText("Status :");
        jLabel81.setName("jLabel81"); // NOI18N
        FormInput.add(jLabel81);
        jLabel81.setBounds(675, 530, 50, 23);

        UsiaKawin1.setFocusTraversalPolicyProvider(true);
        UsiaKawin1.setName("UsiaKawin1"); // NOI18N
        UsiaKawin1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                UsiaKawin1KeyPressed(evt);
            }
        });
        FormInput.add(UsiaKawin1);
        UsiaKawin1.setBounds(589, 530, 60, 23);

        jLabel82.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel82.setText("pembalut");
        jLabel82.setName("jLabel82"); // NOI18N
        FormInput.add(jLabel82);
        jLabel82.setBounds(538, 450, 50, 23);

        KetSiklus2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada Masalah", "Dismenorhea", "Spotting", "Menorhagia", "PMS" }));
        KetSiklus2.setName("KetSiklus2"); // NOI18N
        KetSiklus2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetSiklus2KeyPressed(evt);
            }
        });
        FormInput.add(KetSiklus2);
        KetSiklus2.setBounds(679, 480, 175, 23);

        jLabel83.setText("Usia Perkawinan Ke 1 :");
        jLabel83.setName("jLabel83"); // NOI18N
        FormInput.add(jLabel83);
        jLabel83.setBounds(455, 530, 130, 23);

        jLabel84.setText("Usia Perkawinan Ke 3 :");
        jLabel84.setName("jLabel84"); // NOI18N
        FormInput.add(jLabel84);
        jLabel84.setBounds(455, 560, 130, 23);

        UsiaKawin3.setFocusTraversalPolicyProvider(true);
        UsiaKawin3.setName("UsiaKawin3"); // NOI18N
        UsiaKawin3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                UsiaKawin3KeyPressed(evt);
            }
        });
        FormInput.add(UsiaKawin3);
        UsiaKawin3.setBounds(589, 560, 60, 23);

        jLabel85.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel85.setText("tahun,");
        jLabel85.setName("jLabel85"); // NOI18N
        FormInput.add(jLabel85);
        jLabel85.setBounds(652, 560, 45, 23);

        jLabel86.setText("Status :");
        jLabel86.setName("jLabel86"); // NOI18N
        FormInput.add(jLabel86);
        jLabel86.setBounds(675, 560, 50, 23);

        StatusKawin3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Masih Menikah", "Cerai", "Meninggal" }));
        StatusKawin3.setName("StatusKawin3"); // NOI18N
        StatusKawin3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                StatusKawin3KeyPressed(evt);
            }
        });
        FormInput.add(StatusKawin3);
        StatusKawin3.setBounds(729, 560, 125, 23);

        jLabel87.setText("Usia Perkawinan 2 :");
        jLabel87.setName("jLabel87"); // NOI18N
        FormInput.add(jLabel87);
        jLabel87.setBounds(0, 560, 170, 23);

        UsiaKawin2.setFocusTraversalPolicyProvider(true);
        UsiaKawin2.setName("UsiaKawin2"); // NOI18N
        UsiaKawin2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                UsiaKawin2KeyPressed(evt);
            }
        });
        FormInput.add(UsiaKawin2);
        UsiaKawin2.setBounds(174, 560, 60, 23);

        StatusKawin2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Masih Menikah", "Cerai", "Meninggal" }));
        StatusKawin2.setName("StatusKawin2"); // NOI18N
        StatusKawin2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                StatusKawin2KeyPressed(evt);
            }
        });
        FormInput.add(StatusKawin2);
        StatusKawin2.setBounds(314, 560, 125, 23);

        jLabel88.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel88.setText("tahun,");
        jLabel88.setName("jLabel88"); // NOI18N
        FormInput.add(jLabel88);
        jLabel88.setBounds(237, 560, 50, 23);

        jLabel89.setText("Status :");
        jLabel89.setName("jLabel89"); // NOI18N
        FormInput.add(jLabel89);
        jLabel89.setBounds(260, 560, 50, 23);

        KaliMenikah.setFocusTraversalPolicyProvider(true);
        KaliMenikah.setName("KaliMenikah"); // NOI18N
        KaliMenikah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KaliMenikahKeyPressed(evt);
            }
        });
        FormInput.add(KaliMenikah);
        KaliMenikah.setBounds(353, 530, 60, 23);

        jLabel90.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel90.setText("kali");
        jLabel90.setName("jLabel90"); // NOI18N
        FormInput.add(jLabel90);
        jLabel90.setBounds(416, 530, 30, 23);

        jLabel91.setText("Riwayat Kehamilan Tetap :");
        jLabel91.setName("jLabel91"); // NOI18N
        FormInput.add(jLabel91);
        jLabel91.setBounds(0, 590, 175, 23);

        jLabel92.setText("HPHT :");
        jLabel92.setName("jLabel92"); // NOI18N
        FormInput.add(jLabel92);
        jLabel92.setBounds(0, 610, 170, 23);

        HPHT.setForeground(new java.awt.Color(50, 70, 50));
        HPHT.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "11-05-2020" }));
        HPHT.setDisplayFormat("dd-MM-yyyy");
        HPHT.setName("HPHT"); // NOI18N
        HPHT.setOpaque(false);
        HPHT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HPHTKeyPressed(evt);
            }
        });
        FormInput.add(HPHT);
        HPHT.setBounds(174, 610, 90, 23);

        jLabel93.setText("Usia Hamil :");
        jLabel93.setName("jLabel93"); // NOI18N
        FormInput.add(jLabel93);
        jLabel93.setBounds(265, 610, 105, 23);

        UsiaKehamilan.setFocusTraversalPolicyProvider(true);
        UsiaKehamilan.setName("UsiaKehamilan"); // NOI18N
        UsiaKehamilan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                UsiaKehamilanKeyPressed(evt);
            }
        });
        FormInput.add(UsiaKehamilan);
        UsiaKehamilan.setBounds(374, 610, 60, 23);

        jLabel95.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel95.setText("bln/mgg");
        jLabel95.setName("jLabel95"); // NOI18N
        FormInput.add(jLabel95);
        jLabel95.setBounds(437, 610, 60, 23);

        jLabel96.setText("TP :");
        jLabel96.setName("jLabel96"); // NOI18N
        FormInput.add(jLabel96);
        jLabel96.setBounds(505, 610, 40, 23);

        TP.setForeground(new java.awt.Color(50, 70, 50));
        TP.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "11-05-2020" }));
        TP.setDisplayFormat("dd-MM-yyyy");
        TP.setName("TP"); // NOI18N
        TP.setOpaque(false);
        TP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPKeyPressed(evt);
            }
        });
        FormInput.add(TP);
        TP.setBounds(549, 610, 90, 23);

        jLabel97.setText("Riwayat Imunisasi :");
        jLabel97.setName("jLabel97"); // NOI18N
        FormInput.add(jLabel97);
        jLabel97.setBounds(660, 610, 110, 23);

        RiwayatImunisasi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        RiwayatImunisasi.setName("RiwayatImunisasi"); // NOI18N
        RiwayatImunisasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RiwayatImunisasiKeyPressed(evt);
            }
        });
        FormInput.add(RiwayatImunisasi);
        RiwayatImunisasi.setBounds(774, 610, 80, 23);

        jLabel98.setText("Imunisasi :");
        jLabel98.setName("jLabel98"); // NOI18N
        FormInput.add(jLabel98);
        jLabel98.setBounds(0, 640, 170, 23);

        JumlahImunisasi.setFocusTraversalPolicyProvider(true);
        JumlahImunisasi.setName("JumlahImunisasi"); // NOI18N
        JumlahImunisasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JumlahImunisasiKeyPressed(evt);
            }
        });
        FormInput.add(JumlahImunisasi);
        JumlahImunisasi.setBounds(174, 640, 60, 23);

        jLabel101.setText("G :");
        jLabel101.setName("jLabel101"); // NOI18N
        FormInput.add(jLabel101);
        jLabel101.setBounds(330, 640, 40, 23);

        G.setFocusTraversalPolicyProvider(true);
        G.setName("G"); // NOI18N
        G.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GKeyPressed(evt);
            }
        });
        FormInput.add(G);
        G.setBounds(374, 640, 60, 23);

        jLabel100.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel100.setText("kali");
        jLabel100.setName("jLabel100"); // NOI18N
        FormInput.add(jLabel100);
        jLabel100.setBounds(237, 640, 50, 23);

        jLabel102.setText("P :");
        jLabel102.setName("jLabel102"); // NOI18N
        FormInput.add(jLabel102);
        jLabel102.setBounds(443, 640, 30, 23);

        P.setFocusTraversalPolicyProvider(true);
        P.setName("P"); // NOI18N
        P.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PKeyPressed(evt);
            }
        });
        FormInput.add(P);
        P.setBounds(477, 640, 60, 23);

        jLabel103.setText("A :");
        jLabel103.setName("jLabel103"); // NOI18N
        FormInput.add(jLabel103);
        jLabel103.setBounds(545, 640, 30, 23);

        A.setFocusTraversalPolicyProvider(true);
        A.setName("A"); // NOI18N
        A.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AKeyPressed(evt);
            }
        });
        FormInput.add(A);
        A.setBounds(579, 640, 60, 23);

        jLabel104.setText("Hidup :");
        jLabel104.setName("jLabel104"); // NOI18N
        FormInput.add(jLabel104);
        jLabel104.setBounds(660, 640, 110, 23);

        Hidup.setFocusTraversalPolicyProvider(true);
        Hidup.setName("Hidup"); // NOI18N
        Hidup.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HidupKeyPressed(evt);
            }
        });
        FormInput.add(Hidup);
        Hidup.setBounds(774, 640, 80, 23);

        Scroll6.setName("Scroll6"); // NOI18N
        Scroll6.setOpaque(true);

        tbRiwayatKehamilan.setName("tbRiwayatKehamilan"); // NOI18N
        Scroll6.setViewportView(tbRiwayatKehamilan);

        FormInput.add(Scroll6);
        Scroll6.setBounds(174, 670, 680, 90);

        BtnTambahMasalah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/plus_16.png"))); // NOI18N
        BtnTambahMasalah.setMnemonic('3');
        BtnTambahMasalah.setToolTipText("Alt+3");
        BtnTambahMasalah.setName("BtnTambahMasalah"); // NOI18N
        BtnTambahMasalah.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnTambahMasalah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTambahMasalahActionPerformed(evt);
            }
        });
        FormInput.add(BtnTambahMasalah);
        BtnTambahMasalah.setBounds(142, 670, 28, 23);

        scrollInput.setViewportView(FormInput);

        internalFrame2.add(scrollInput, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Input Penilaian", internalFrame2);

        internalFrame3.setBorder(null);
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);
        Scroll.setPreferredSize(new java.awt.Dimension(452, 200));

        tbObat.setAutoCreateRowSorter(true);
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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "11-05-2020" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "11-05-2020" }));
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
        PanelAccor.setPreferredSize(new java.awt.Dimension(470, 43));
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

        FormMenu.setBackground(new java.awt.Color(255, 255, 255));
        FormMenu.setBorder(null);
        FormMenu.setName("FormMenu"); // NOI18N
        FormMenu.setPreferredSize(new java.awt.Dimension(115, 43));
        FormMenu.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        jLabel34.setText("Pasien :");
        jLabel34.setName("jLabel34"); // NOI18N
        jLabel34.setPreferredSize(new java.awt.Dimension(55, 23));
        FormMenu.add(jLabel34);

        TNoRM1.setEditable(false);
        TNoRM1.setHighlighter(null);
        TNoRM1.setName("TNoRM1"); // NOI18N
        TNoRM1.setPreferredSize(new java.awt.Dimension(100, 23));
        FormMenu.add(TNoRM1);

        TPasien1.setEditable(false);
        TPasien1.setBackground(new java.awt.Color(245, 250, 240));
        TPasien1.setHighlighter(null);
        TPasien1.setName("TPasien1"); // NOI18N
        TPasien1.setPreferredSize(new java.awt.Dimension(250, 23));
        FormMenu.add(TPasien1);

        BtnPrint1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item (copy).png"))); // NOI18N
        BtnPrint1.setMnemonic('T');
        BtnPrint1.setToolTipText("Alt+T");
        BtnPrint1.setName("BtnPrint1"); // NOI18N
        BtnPrint1.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnPrint1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPrint1ActionPerformed(evt);
            }
        });
        FormMenu.add(BtnPrint1);

        PanelAccor.add(FormMenu, java.awt.BorderLayout.NORTH);

        FormMasalahRencana.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 254)));
        FormMasalahRencana.setName("FormMasalahRencana"); // NOI18N
        FormMasalahRencana.setLayout(new java.awt.GridLayout(2, 0, 1, 1));

        scrollPane7.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 254)), "Masalah Kebidanan :", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        scrollPane7.setName("scrollPane7"); // NOI18N

        MasalahKebidanan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 5, 1, 1));
        MasalahKebidanan.setColumns(20);
        MasalahKebidanan.setRows(5);
        MasalahKebidanan.setName("MasalahKebidanan"); // NOI18N
        MasalahKebidanan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MasalahKebidananKeyPressed(evt);
            }
        });
        scrollPane7.setViewportView(MasalahKebidanan);

        FormMasalahRencana.add(scrollPane7);

        scrollPane6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 254)), "Tindakan :", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        scrollPane6.setName("scrollPane6"); // NOI18N

        TindakanKebidanan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 5, 1, 1));
        TindakanKebidanan.setColumns(20);
        TindakanKebidanan.setRows(5);
        TindakanKebidanan.setName("TindakanKebidanan"); // NOI18N
        TindakanKebidanan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TindakanKebidananKeyPressed(evt);
            }
        });
        scrollPane6.setViewportView(TindakanKebidanan);

        FormMasalahRencana.add(scrollPane6);

        PanelAccor.add(FormMasalahRencana, java.awt.BorderLayout.CENTER);

        internalFrame3.add(PanelAccor, java.awt.BorderLayout.EAST);

        TabRawat.addTab("Data Penilaian", internalFrame3);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TNoRM.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"Nama Pasien");
        }else if(TD.getText().trim().equals("")){
            Valid.textKosong(TD,"TD(mmHg)");
        }else if(Nadi.getText().trim().equals("")){
            Valid.textKosong(Nadi,"Nadi(x/menit)");
        }else if(RR.getText().trim().equals("")){
            Valid.textKosong(RR,"RR(x/menit)");
        }else if(Suhu.getText().trim().equals("")){
            Valid.textKosong(Suhu,"Suhu(C)");
        }else if(GCS.getText().trim().equals("")){
            Valid.textKosong(GCS,"GCS");
        }else if(BB.getText().trim().equals("")){
            Valid.textKosong(BB,"BB(Kg)");
        }else if(TB.getText().trim().equals("")){
            Valid.textKosong(TB,"TB(Cm)");
        }else if(BMI.getText().trim().equals("")){
            Valid.textKosong(BMI,"BMI(Kg/m2)");
        }
    
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            //Valid.pindah(evt,Rencana,BtnBatal);
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
            if(Sequel.queryu2tf("delete from penilaian_awal_keperawatan_ralan where no_rawat=?",1,new String[]{
                tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
            })==true){
                TNoRM1.setText("");
                TPasien1.setText("");
                Sequel.meghapus("penilaian_awal_keperawatan_ralan_masalah","no_rawat",tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
                ChkAccor.setSelected(false);
                isMenu();
                tampil();
                emptTeks();
            }else{
                JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
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
        }else if(TD.getText().trim().equals("")){
            Valid.textKosong(TD,"TD(mmHg)");
        }else if(Nadi.getText().trim().equals("")){
            Valid.textKosong(Nadi,"Nadi(x/menit)");
        }else if(RR.getText().trim().equals("")){
            Valid.textKosong(RR,"RR(x/menit)");
        }else if(Suhu.getText().trim().equals("")){
            Valid.textKosong(Suhu,"Suhu(C)");
        }else if(GCS.getText().trim().equals("")){
            Valid.textKosong(GCS,"GCS");
        }else if(BB.getText().trim().equals("")){
            Valid.textKosong(BB,"BB(Kg)");
        }else if(TB.getText().trim().equals("")){
            Valid.textKosong(TB,"TB(Cm)");
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
                ChkAccor.setSelected(true);
                isMenu();
                getMasalah();
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbObatMouseClicked

    private void tbObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbObatKeyPressed
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    ChkAccor.setSelected(true);
                    isMenu();
                    getMasalah();
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

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        //tampilMasalah();
    }//GEN-LAST:event_formWindowOpened

    private void ChkAccorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkAccorActionPerformed
        if(tbObat.getSelectedRow()!= -1){
            isMenu();
        }else{
            JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data yang mau ditampilkan triasenya...!!!!");
        }
    }//GEN-LAST:event_ChkAccorActionPerformed

    private void BtnPrint1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrint1ActionPerformed
       
    }//GEN-LAST:event_BtnPrint1ActionPerformed

    private void TindakanKebidananKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TindakanKebidananKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TindakanKebidananKeyPressed

    private void MasalahKebidananKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MasalahKebidananKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_MasalahKebidananKeyPressed

    private void TBJKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TBJKeyPressed
        Valid.pindah(evt,TFU,Letak);
    }//GEN-LAST:event_TBJKeyPressed

    private void TFUKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TFUKeyPressed
        Valid.pindah(evt,BMI,TBJ);
    }//GEN-LAST:event_TFUKeyPressed

    private void LILAKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LILAKeyPressed
        Valid.pindah(evt,TB,BMI);
    }//GEN-LAST:event_LILAKeyPressed

    private void GCSKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GCSKeyPressed
        Valid.pindah(evt,Suhu,BB);
    }//GEN-LAST:event_GCSKeyPressed

    private void TglAsuhanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglAsuhanKeyPressed
        //Valid.pindah(evt,Rencana,Informasi);
    }//GEN-LAST:event_TglAsuhanKeyPressed

    private void InformasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_InformasiKeyPressed
        Valid.pindah(evt,TglAsuhan,TD);
    }//GEN-LAST:event_InformasiKeyPressed

    private void BMIKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BMIKeyPressed
        Valid.pindah(evt,LILA,TFU);
    }//GEN-LAST:event_BMIKeyPressed

    private void RRKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RRKeyPressed
        Valid.pindah(evt,Nadi,Suhu);
    }//GEN-LAST:event_RRKeyPressed

    private void TDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TDKeyPressed
        Valid.pindah(evt,Informasi,Nadi);
    }//GEN-LAST:event_TDKeyPressed

    private void SuhuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SuhuKeyPressed
        Valid.pindah(evt,RR,GCS);
    }//GEN-LAST:event_SuhuKeyPressed

    private void NadiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NadiKeyPressed
        Valid.pindah(evt,TD,RR);
    }//GEN-LAST:event_NadiKeyPressed

    private void TBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TBKeyPressed
        Valid.pindah(evt,BB,LILA);
    }//GEN-LAST:event_TBKeyPressed

    private void BBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BBKeyPressed
        Valid.pindah(evt,GCS,TB);
    }//GEN-LAST:event_BBKeyPressed

    private void BtnDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokterKeyPressed
        //Valid.pindah(evt,Monitoring,BtnSimpan);
    }//GEN-LAST:event_BtnDokterKeyPressed

    private void BtnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokterActionPerformed
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setAlwaysOnTop(false);
        petugas.setVisible(true);
    }//GEN-LAST:event_BtnDokterActionPerformed

    private void KdPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdPetugasKeyPressed

    }//GEN-LAST:event_KdPetugasKeyPressed

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isRawat();
        }else{
            Valid.pindah(evt,TCari,BtnDokter);
        }
    }//GEN-LAST:event_TNoRwKeyPressed

    private void LetakKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LetakKeyPressed
        Valid.pindah(evt,TBJ,Presentasi);
    }//GEN-LAST:event_LetakKeyPressed

    private void PresentasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PresentasiKeyPressed
        Valid.pindah(evt,Letak,Penurunan);
    }//GEN-LAST:event_PresentasiKeyPressed

    private void PenurunanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenurunanKeyPressed
        Valid.pindah(evt,Presentasi,Kontraksi);
    }//GEN-LAST:event_PenurunanKeyPressed

    private void KontraksiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KontraksiKeyPressed
        Valid.pindah(evt,Penurunan,Kekuatan);
    }//GEN-LAST:event_KontraksiKeyPressed

    private void KekuatanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KekuatanKeyPressed
        Valid.pindah(evt,Kontraksi,Lamanya);
    }//GEN-LAST:event_KekuatanKeyPressed

    private void LamanyaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LamanyaKeyPressed
        Valid.pindah(evt,Kekuatan,BJJ);
    }//GEN-LAST:event_LamanyaKeyPressed

    private void BJJKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BJJKeyPressed
        Valid.pindah(evt,Lamanya,KeteranganBJJ);
    }//GEN-LAST:event_BJJKeyPressed

    private void KeteranganBJJKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganBJJKeyPressed
        Valid.pindah(evt,BJJ,Portio);
    }//GEN-LAST:event_KeteranganBJJKeyPressed

    private void PortioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PortioKeyPressed
        Valid.pindah(evt,KeteranganBJJ,PembukaanServiks);
    }//GEN-LAST:event_PortioKeyPressed

    private void PembukaanServiksKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PembukaanServiksKeyPressed
        Valid.pindah(evt,Portio,Ketuban);
    }//GEN-LAST:event_PembukaanServiksKeyPressed

    private void KetubanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetubanKeyPressed
        Valid.pindah(evt,PembukaanServiks,Hodge);
    }//GEN-LAST:event_KetubanKeyPressed

    private void HodgeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HodgeKeyPressed
        Valid.pindah(evt,Ketuban,Inspekulo);
    }//GEN-LAST:event_HodgeKeyPressed

    private void KeteranganInspekuloKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganInspekuloKeyPressed
        Valid.pindah(evt,Inspekulo,CTG);
    }//GEN-LAST:event_KeteranganInspekuloKeyPressed

    private void InspekuloKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_InspekuloKeyPressed
        Valid.pindah(evt,Hodge,KeteranganInspekulo);
    }//GEN-LAST:event_InspekuloKeyPressed

    private void CTGKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CTGKeyPressed
        Valid.pindah(evt,KeteranganInspekulo,KeteranganCTG);
    }//GEN-LAST:event_CTGKeyPressed

    private void KeteranganCTGKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganCTGKeyPressed
        Valid.pindah(evt,CTG,Laboratorium);
    }//GEN-LAST:event_KeteranganCTGKeyPressed

    private void LaboratoriumKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LaboratoriumKeyPressed
        Valid.pindah(evt,KeteranganCTG,KeteranganLaboratorium);
    }//GEN-LAST:event_LaboratoriumKeyPressed

    private void KeteranganLaboratoriumKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganLaboratoriumKeyPressed
        Valid.pindah(evt,Laboratorium,USG);
    }//GEN-LAST:event_KeteranganLaboratoriumKeyPressed

    private void StatusKawin1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_StatusKawin1KeyPressed
        Valid.pindah(evt,UsiaKawin1,UsiaKawin2);
    }//GEN-LAST:event_StatusKawin1KeyPressed

    private void KeteranganUSGKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganUSGKeyPressed
        Valid.pindah(evt,USG,Lakmus);
    }//GEN-LAST:event_KeteranganUSGKeyPressed

    private void LakmusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LakmusKeyPressed
        Valid.pindah(evt,KeteranganUSG,KeteranganLakmus);
    }//GEN-LAST:event_LakmusKeyPressed

    private void KeteranganLakmusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganLakmusKeyPressed
        Valid.pindah(evt,Lakmus,PemeriksaanPanggul);
    }//GEN-LAST:event_KeteranganLakmusKeyPressed

    private void PemeriksaanPanggulKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PemeriksaanPanggulKeyPressed
        Valid.pindah(evt,KeteranganLakmus,KeluhanUtama);
    }//GEN-LAST:event_PemeriksaanPanggulKeyPressed

    private void KeluhanUtamaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeluhanUtamaKeyPressed
        Valid.pindah(evt,PemeriksaanPanggul,Umur);
    }//GEN-LAST:event_KeluhanUtamaKeyPressed

    private void UmurKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_UmurKeyPressed
        Valid.pindah(evt,KeluhanUtama,Lama);
    }//GEN-LAST:event_UmurKeyPressed

    private void LamaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LamaKeyPressed
        Valid.pindah(evt,Umur,Banyaknya);
    }//GEN-LAST:event_LamaKeyPressed

    private void BanyaknyaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BanyaknyaKeyPressed
        Valid.pindah(evt,Lama,HaidTerakhir);
    }//GEN-LAST:event_BanyaknyaKeyPressed

    private void HaidTerakhirKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HaidTerakhirKeyPressed
       Valid.pindah(evt,Banyaknya,Siklus);
    }//GEN-LAST:event_HaidTerakhirKeyPressed

    private void SiklusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SiklusKeyPressed
        Valid.pindah(evt,HaidTerakhir,KetSiklus1);
    }//GEN-LAST:event_SiklusKeyPressed

    private void USGKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_USGKeyPressed
        Valid.pindah(evt,KeteranganLaboratorium,KeteranganUSG);
    }//GEN-LAST:event_USGKeyPressed

    private void KetSiklus1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetSiklus1KeyPressed
        Valid.pindah(evt,Siklus,KetSiklus2);
    }//GEN-LAST:event_KetSiklus1KeyPressed

    private void StatusMenikahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_StatusMenikahKeyPressed
        Valid.pindah(evt,KetSiklus2,KaliMenikah);
    }//GEN-LAST:event_StatusMenikahKeyPressed

    private void UsiaKawin1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_UsiaKawin1KeyPressed
        Valid.pindah(evt,KaliMenikah,StatusKawin1);
    }//GEN-LAST:event_UsiaKawin1KeyPressed

    private void KetSiklus2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetSiklus2KeyPressed
       Valid.pindah(evt,KetSiklus1,StatusMenikah);
    }//GEN-LAST:event_KetSiklus2KeyPressed

    private void UsiaKawin3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_UsiaKawin3KeyPressed
        Valid.pindah(evt,StatusKawin2,StatusKawin3);
    }//GEN-LAST:event_UsiaKawin3KeyPressed

    private void StatusKawin3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_StatusKawin3KeyPressed
        Valid.pindah(evt,UsiaKawin3,HPHT);
    }//GEN-LAST:event_StatusKawin3KeyPressed

    private void UsiaKawin2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_UsiaKawin2KeyPressed
        Valid.pindah(evt,StatusKawin1,StatusKawin2);
    }//GEN-LAST:event_UsiaKawin2KeyPressed

    private void StatusKawin2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_StatusKawin2KeyPressed
        Valid.pindah(evt,UsiaKawin2,UsiaKawin3);
    }//GEN-LAST:event_StatusKawin2KeyPressed

    private void KaliMenikahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KaliMenikahKeyPressed
        Valid.pindah(evt,StatusMenikah,UsiaKawin1);
    }//GEN-LAST:event_KaliMenikahKeyPressed

    private void HPHTKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HPHTKeyPressed
        Valid.pindah(evt,UsiaKawin3,UsiaKehamilan);
    }//GEN-LAST:event_HPHTKeyPressed

    private void UsiaKehamilanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_UsiaKehamilanKeyPressed
        Valid.pindah(evt,HPHT,TP);
    }//GEN-LAST:event_UsiaKehamilanKeyPressed

    private void TPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TPKeyPressed
        Valid.pindah(evt,UsiaKehamilan,RiwayatImunisasi);
    }//GEN-LAST:event_TPKeyPressed

    private void RiwayatImunisasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RiwayatImunisasiKeyPressed
        Valid.pindah(evt,UsiaKehamilan,JumlahImunisasi);
    }//GEN-LAST:event_RiwayatImunisasiKeyPressed

    private void JumlahImunisasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JumlahImunisasiKeyPressed
        Valid.pindah(evt,RiwayatImunisasi,G);
    }//GEN-LAST:event_JumlahImunisasiKeyPressed

    private void GKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GKeyPressed
        Valid.pindah(evt,JumlahImunisasi,P);
    }//GEN-LAST:event_GKeyPressed

    private void PKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PKeyPressed
        Valid.pindah(evt,G,A);
    }//GEN-LAST:event_PKeyPressed

    private void AKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AKeyPressed
        Valid.pindah(evt,P,Hidup);
    }//GEN-LAST:event_AKeyPressed

    private void HidupKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HidupKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_HidupKeyPressed

    private void BtnTambahMasalahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahMasalahActionPerformed
        
    }//GEN-LAST:event_BtnTambahMasalahActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMPenilaianAwalKeperawatanKebidanan dialog = new RMPenilaianAwalKeperawatanKebidanan(new javax.swing.JFrame(), true);
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
    private widget.TextBox A;
    private widget.TextBox BB;
    private widget.TextBox BJJ;
    private widget.TextBox BMI;
    private widget.TextBox Banyaknya;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnDokter;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnPrint1;
    private widget.Button BtnSimpan;
    private widget.Button BtnTambahMasalah;
    private widget.ComboBox CTG;
    private widget.CekBox ChkAccor;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.PanelBiasa FormInput;
    private widget.PanelBiasa FormMasalahRencana;
    private widget.PanelBiasa FormMenu;
    private widget.TextBox G;
    private widget.TextBox GCS;
    private widget.Tanggal HPHT;
    private widget.TextBox HaidTerakhir;
    private widget.TextBox Hidup;
    private widget.TextBox Hodge;
    private widget.ComboBox Informasi;
    private widget.ComboBox Inspekulo;
    private widget.TextBox Jk;
    private widget.TextBox JumlahImunisasi;
    private widget.TextBox KaliMenikah;
    private widget.TextBox KdPetugas;
    private widget.TextBox Kekuatan;
    private widget.TextArea KeluhanUtama;
    private widget.ComboBox KetSiklus1;
    private widget.ComboBox KetSiklus2;
    private widget.ComboBox KeteranganBJJ;
    private widget.TextBox KeteranganCTG;
    private widget.TextBox KeteranganInspekulo;
    private widget.TextBox KeteranganLaboratorium;
    private widget.TextBox KeteranganLakmus;
    private widget.TextBox KeteranganUSG;
    private widget.TextBox Ketuban;
    private widget.TextBox Kontraksi;
    private widget.Label LCount;
    private widget.TextBox LILA;
    private widget.Label LabelServiks;
    private widget.ComboBox Laboratorium;
    private widget.ComboBox Lakmus;
    private widget.TextBox Lama;
    private widget.TextBox Lamanya;
    private widget.TextBox Letak;
    private widget.editorpane LoadHTML;
    private widget.TextArea MasalahKebidanan;
    private widget.TextBox Nadi;
    private widget.TextBox NmPetugas;
    private widget.TextBox P;
    private widget.PanelBiasa PanelAccor;
    private widget.TextBox PembukaanServiks;
    private widget.ComboBox PemeriksaanPanggul;
    private widget.TextBox Penurunan;
    private widget.TextBox Portio;
    private widget.TextBox Presentasi;
    private widget.TextBox RR;
    private widget.ComboBox RiwayatImunisasi;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll6;
    private widget.TextBox Siklus;
    private widget.ComboBox StatusKawin1;
    private widget.ComboBox StatusKawin2;
    private widget.ComboBox StatusKawin3;
    private widget.ComboBox StatusMenikah;
    private widget.TextBox Suhu;
    private widget.TextBox TB;
    private widget.TextBox TBJ;
    private widget.TextBox TCari;
    private widget.TextBox TD;
    private widget.TextBox TFU;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRM1;
    private widget.TextBox TNoRw;
    private widget.Tanggal TP;
    private widget.TextBox TPasien;
    private widget.TextBox TPasien1;
    private javax.swing.JTabbedPane TabRawat;
    private widget.Tanggal TglAsuhan;
    private widget.TextBox TglLahir;
    private widget.TextArea TindakanKebidanan;
    private widget.ComboBox USG;
    private widget.TextBox Umur;
    private widget.TextBox UsiaKawin1;
    private widget.TextBox UsiaKawin2;
    private widget.TextBox UsiaKawin3;
    private widget.TextBox UsiaKehamilan;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.Label jLabel10;
    private widget.Label jLabel100;
    private widget.Label jLabel101;
    private widget.Label jLabel102;
    private widget.Label jLabel103;
    private widget.Label jLabel104;
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
    private widget.Label jLabel27;
    private widget.Label jLabel28;
    private widget.Label jLabel29;
    private widget.Label jLabel30;
    private widget.Label jLabel31;
    private widget.Label jLabel32;
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
    private widget.Label jLabel62;
    private widget.Label jLabel63;
    private widget.Label jLabel64;
    private widget.Label jLabel65;
    private widget.Label jLabel66;
    private widget.Label jLabel67;
    private widget.Label jLabel68;
    private widget.Label jLabel69;
    private widget.Label jLabel7;
    private widget.Label jLabel70;
    private widget.Label jLabel71;
    private widget.Label jLabel72;
    private widget.Label jLabel73;
    private widget.Label jLabel74;
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
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private widget.Label label11;
    private widget.Label label14;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollInput;
    private widget.ScrollPane scrollPane1;
    private widget.ScrollPane scrollPane6;
    private widget.ScrollPane scrollPane7;
    private widget.Table tbObat;
    private widget.Table tbRiwayatKehamilan;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            if(TCari.getText().equals("")){
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,pasien.agama,bahasa_pasien.nama_bahasa,cacat_fisik.nama_cacat,penilaian_awal_keperawatan_ralan.tanggal,"+
                        "penilaian_awal_keperawatan_ralan.informasi,penilaian_awal_keperawatan_ralan.td,penilaian_awal_keperawatan_ralan.nadi,penilaian_awal_keperawatan_ralan.rr,penilaian_awal_keperawatan_ralan.suhu,penilaian_awal_keperawatan_ralan.bb,penilaian_awal_keperawatan_ralan.tb,"+
                        "penilaian_awal_keperawatan_ralan.nadi,penilaian_awal_keperawatan_ralan.rr,penilaian_awal_keperawatan_ralan.suhu,penilaian_awal_keperawatan_ralan.gcs,penilaian_awal_keperawatan_ralan.bb,penilaian_awal_keperawatan_ralan.tb,penilaian_awal_keperawatan_ralan.bmi,penilaian_awal_keperawatan_ralan.keluhan_utama,"+
                        "penilaian_awal_keperawatan_ralan.rpd,penilaian_awal_keperawatan_ralan.rpk,penilaian_awal_keperawatan_ralan.rpo,penilaian_awal_keperawatan_ralan.alergi,penilaian_awal_keperawatan_ralan.alat_bantu,penilaian_awal_keperawatan_ralan.ket_bantu,penilaian_awal_keperawatan_ralan.prothesa,"+
                        "penilaian_awal_keperawatan_ralan.ket_pro,penilaian_awal_keperawatan_ralan.adl,penilaian_awal_keperawatan_ralan.status_psiko,penilaian_awal_keperawatan_ralan.ket_psiko,penilaian_awal_keperawatan_ralan.hub_keluarga,penilaian_awal_keperawatan_ralan.tinggal_dengan,"+
                        "penilaian_awal_keperawatan_ralan.ket_tinggal,penilaian_awal_keperawatan_ralan.ekonomi,penilaian_awal_keperawatan_ralan.edukasi,penilaian_awal_keperawatan_ralan.ket_edukasi,penilaian_awal_keperawatan_ralan.berjalan_a,penilaian_awal_keperawatan_ralan.berjalan_b,"+
                        "penilaian_awal_keperawatan_ralan.berjalan_c,penilaian_awal_keperawatan_ralan.hasil,penilaian_awal_keperawatan_ralan.lapor,penilaian_awal_keperawatan_ralan.ket_lapor,penilaian_awal_keperawatan_ralan.sg1,penilaian_awal_keperawatan_ralan.nilai1,penilaian_awal_keperawatan_ralan.sg2,penilaian_awal_keperawatan_ralan.nilai2,"+
                        "penilaian_awal_keperawatan_ralan.total_hasil,penilaian_awal_keperawatan_ralan.nyeri,penilaian_awal_keperawatan_ralan.provokes,penilaian_awal_keperawatan_ralan.ket_provokes,penilaian_awal_keperawatan_ralan.quality,penilaian_awal_keperawatan_ralan.ket_quality,penilaian_awal_keperawatan_ralan.lokasi,penilaian_awal_keperawatan_ralan.menyebar,"+
                        "penilaian_awal_keperawatan_ralan.skala_nyeri,penilaian_awal_keperawatan_ralan.durasi,penilaian_awal_keperawatan_ralan.nyeri_hilang,penilaian_awal_keperawatan_ralan.ket_nyeri,penilaian_awal_keperawatan_ralan.pada_dokter,penilaian_awal_keperawatan_ralan.ket_dokter,penilaian_awal_keperawatan_ralan.rencana,"+
                        "penilaian_awal_keperawatan_ralan.nip,petugas.nama,penilaian_awal_keperawatan_ralan.budaya,penilaian_awal_keperawatan_ralan.ket_budaya "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join penilaian_awal_keperawatan_ralan on reg_periksa.no_rawat=penilaian_awal_keperawatan_ralan.no_rawat "+
                        "inner join petugas on penilaian_awal_keperawatan_ralan.nip=petugas.nip "+
                        "inner join bahasa_pasien on bahasa_pasien.id=pasien.bahasa_pasien "+
                        "inner join cacat_fisik on cacat_fisik.id=pasien.cacat_fisik where "+
                        "penilaian_awal_keperawatan_ralan.tanggal between ? and ? order by penilaian_awal_keperawatan_ralan.tanggal");
            }else{
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,pasien.agama,bahasa_pasien.nama_bahasa,cacat_fisik.nama_cacat,penilaian_awal_keperawatan_ralan.tanggal,"+
                        "penilaian_awal_keperawatan_ralan.informasi,penilaian_awal_keperawatan_ralan.td,penilaian_awal_keperawatan_ralan.nadi,penilaian_awal_keperawatan_ralan.rr,penilaian_awal_keperawatan_ralan.suhu,penilaian_awal_keperawatan_ralan.bb,penilaian_awal_keperawatan_ralan.tb,"+
                        "penilaian_awal_keperawatan_ralan.nadi,penilaian_awal_keperawatan_ralan.rr,penilaian_awal_keperawatan_ralan.suhu,penilaian_awal_keperawatan_ralan.gcs,penilaian_awal_keperawatan_ralan.bb,penilaian_awal_keperawatan_ralan.tb,penilaian_awal_keperawatan_ralan.bmi,penilaian_awal_keperawatan_ralan.keluhan_utama,"+
                        "penilaian_awal_keperawatan_ralan.rpd,penilaian_awal_keperawatan_ralan.rpk,penilaian_awal_keperawatan_ralan.rpo,penilaian_awal_keperawatan_ralan.alergi,penilaian_awal_keperawatan_ralan.alat_bantu,penilaian_awal_keperawatan_ralan.ket_bantu,penilaian_awal_keperawatan_ralan.prothesa,"+
                        "penilaian_awal_keperawatan_ralan.ket_pro,penilaian_awal_keperawatan_ralan.adl,penilaian_awal_keperawatan_ralan.status_psiko,penilaian_awal_keperawatan_ralan.ket_psiko,penilaian_awal_keperawatan_ralan.hub_keluarga,penilaian_awal_keperawatan_ralan.tinggal_dengan,"+
                        "penilaian_awal_keperawatan_ralan.ket_tinggal,penilaian_awal_keperawatan_ralan.ekonomi,penilaian_awal_keperawatan_ralan.edukasi,penilaian_awal_keperawatan_ralan.ket_edukasi,penilaian_awal_keperawatan_ralan.berjalan_a,penilaian_awal_keperawatan_ralan.berjalan_b,"+
                        "penilaian_awal_keperawatan_ralan.berjalan_c,penilaian_awal_keperawatan_ralan.hasil,penilaian_awal_keperawatan_ralan.lapor,penilaian_awal_keperawatan_ralan.ket_lapor,penilaian_awal_keperawatan_ralan.sg1,penilaian_awal_keperawatan_ralan.nilai1,penilaian_awal_keperawatan_ralan.sg2,penilaian_awal_keperawatan_ralan.nilai2,"+
                        "penilaian_awal_keperawatan_ralan.total_hasil,penilaian_awal_keperawatan_ralan.nyeri,penilaian_awal_keperawatan_ralan.provokes,penilaian_awal_keperawatan_ralan.ket_provokes,penilaian_awal_keperawatan_ralan.quality,penilaian_awal_keperawatan_ralan.ket_quality,penilaian_awal_keperawatan_ralan.lokasi,penilaian_awal_keperawatan_ralan.menyebar,"+
                        "penilaian_awal_keperawatan_ralan.skala_nyeri,penilaian_awal_keperawatan_ralan.durasi,penilaian_awal_keperawatan_ralan.nyeri_hilang,penilaian_awal_keperawatan_ralan.ket_nyeri,penilaian_awal_keperawatan_ralan.pada_dokter,penilaian_awal_keperawatan_ralan.ket_dokter,penilaian_awal_keperawatan_ralan.rencana,"+
                        "penilaian_awal_keperawatan_ralan.nip,petugas.nama,penilaian_awal_keperawatan_ralan.budaya,penilaian_awal_keperawatan_ralan.ket_budaya "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join penilaian_awal_keperawatan_ralan on reg_periksa.no_rawat=penilaian_awal_keperawatan_ralan.no_rawat "+
                        "inner join petugas on penilaian_awal_keperawatan_ralan.nip=petugas.nip "+
                        "inner join bahasa_pasien on bahasa_pasien.id=pasien.bahasa_pasien "+
                        "inner join cacat_fisik on cacat_fisik.id=pasien.cacat_fisik where "+
                        "penilaian_awal_keperawatan_ralan.tanggal between ? and ? and reg_periksa.no_rawat like ? or "+
                        "penilaian_awal_keperawatan_ralan.tanggal between ? and ? and pasien.no_rkm_medis like ? or "+
                        "penilaian_awal_keperawatan_ralan.tanggal between ? and ? and pasien.nm_pasien like ? or "+
                        "penilaian_awal_keperawatan_ralan.tanggal between ? and ? and penilaian_awal_keperawatan_ralan.nip like ? or "+
                        "penilaian_awal_keperawatan_ralan.tanggal between ? and ? and petugas.nama like ? order by penilaian_awal_keperawatan_ralan.tanggal");
            }
                
            try {
                if(TCari.getText().equals("")){
                    ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                    ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                }else{
                    ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                    ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                    ps.setString(3,"%"+TCari.getText()+"%");
                    ps.setString(4,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                    ps.setString(5,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                    ps.setString(6,"%"+TCari.getText()+"%");
                    ps.setString(7,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                    ps.setString(8,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                    ps.setString(9,"%"+TCari.getText()+"%");
                    ps.setString(10,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                    ps.setString(11,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                    ps.setString(12,"%"+TCari.getText()+"%");
                    ps.setString(13,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                    ps.setString(14,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                    ps.setString(15,"%"+TCari.getText()+"%");
                }   
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new String[]{
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("jk"),rs.getString("agama"),rs.getString("nama_bahasa"),rs.getString("nama_cacat"),
                        rs.getString("tgl_lahir"),rs.getString("tanggal"),rs.getString("informasi"),rs.getString("td"),rs.getString("nadi"),rs.getString("rr"),rs.getString("suhu"),
                        rs.getString("gcs"),rs.getString("bb"),rs.getString("tb"),rs.getString("bmi"),rs.getString("keluhan_utama"),rs.getString("rpd"),rs.getString("rpk"),rs.getString("rpo"),
                        rs.getString("alergi"),rs.getString("alat_bantu"),rs.getString("ket_bantu"),rs.getString("prothesa"),rs.getString("ket_pro"),rs.getString("adl"),rs.getString("status_psiko"),
                        rs.getString("ket_psiko"),rs.getString("hub_keluarga"),rs.getString("tinggal_dengan"),rs.getString("ket_tinggal"),rs.getString("ekonomi"),rs.getString("budaya"),
                        rs.getString("ket_budaya"),rs.getString("edukasi"),rs.getString("ket_edukasi"),rs.getString("berjalan_a"),rs.getString("berjalan_b"),rs.getString("berjalan_c"),
                        rs.getString("hasil"),rs.getString("lapor"),rs.getString("ket_lapor"),rs.getString("sg1"),rs.getString("nilai1"),rs.getString("sg2"),rs.getString("nilai2"),
                        rs.getString("total_hasil"),rs.getString("nyeri"),rs.getString("provokes"),rs.getString("ket_provokes"),rs.getString("quality"),rs.getString("ket_quality"),
                        rs.getString("lokasi"),rs.getString("menyebar"),rs.getString("skala_nyeri"),rs.getString("durasi"),rs.getString("nyeri_hilang"),rs.getString("ket_nyeri"),
                        rs.getString("pada_dokter"),rs.getString("ket_dokter"),rs.getString("rencana"),rs.getString("nip"),rs.getString("nama")
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
        TglAsuhan.setDate(new Date());
        Informasi.setSelectedIndex(0);
        TD.setText("");
        Nadi.setText("");
        RR.setText("");
        Suhu.setText("");
        GCS.setText("");
        BB.setText("");
        TB.setText("");
        BMI.setText("");
        TabRawat.setSelectedIndex(0);
        Informasi.requestFocus();
    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()); 
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString()); 
            Jk.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString()); 
        }
    }

    private void isRawat() {
        Sequel.cariIsi("select no_rkm_medis from reg_periksa where no_rawat=? ",TNoRM,TNoRw.getText());
        try {
            ps=koneksi.prepareStatement(
                    "select nm_pasien, if(jk='L','Laki-Laki','Perempuan') as jk,tgl_lahir,agama,bahasa_pasien.nama_bahasa,cacat_fisik.nama_cacat "+
                    "from pasien inner join bahasa_pasien on bahasa_pasien.id=pasien.bahasa_pasien "+
                    "inner join cacat_fisik on cacat_fisik.id=pasien.cacat_fisik "+
                    "where no_rkm_medis=?");
            try {
                ps.setString(1,TNoRM.getText());
                rs=ps.executeQuery();
                if(rs.next()){
                    TPasien.setText(rs.getString("nm_pasien"));
                    Jk.setText(rs.getString("jk"));
                    TglLahir.setText(rs.getString("tgl_lahir"));
                    //Agama.setText(rs.getString("agama"));
                    //Bahasa.setText(rs.getString("nama_bahasa"));
                    //CacatFisik.setText(rs.getString("nama_cacat"));
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
    
    public void setNoRm(String norwt, Date tgl2) {
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        Sequel.cariIsi("select tgl_registrasi from reg_periksa where no_rawat='"+norwt+"'", DTPCari1);
        DTPCari2.setDate(tgl2);    
        isRawat(); 
    }
    
    
    public void isCek(){
        BtnSimpan.setEnabled(akses.getpenilaian_awal_keperawatan_kebidanan());
        BtnHapus.setEnabled(akses.getpenilaian_awal_keperawatan_kebidanan());
        BtnEdit.setEnabled(akses.getpenilaian_awal_keperawatan_kebidanan());
        BtnEdit.setEnabled(akses.getpenilaian_awal_keperawatan_kebidanan()); 
        if(akses.getjml2()>=1){
            KdPetugas.setEditable(false);
            BtnDokter.setEnabled(false);
            KdPetugas.setText(akses.getkode());
            Sequel.cariIsi("select nama from petugas where nip=?", NmPetugas,KdPetugas.getText());
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
    
    
    
    private void isMenu(){
        if(ChkAccor.isSelected()==true){
            ChkAccor.setVisible(false);
            PanelAccor.setPreferredSize(new Dimension(470,HEIGHT));
            FormMenu.setVisible(true);  
            FormMasalahRencana.setVisible(true);  
            ChkAccor.setVisible(true);
        }else if(ChkAccor.isSelected()==false){           
            ChkAccor.setVisible(false);            
            PanelAccor.setPreferredSize(new Dimension(15,HEIGHT));
            FormMenu.setVisible(false);  
            FormMasalahRencana.setVisible(false);      
            ChkAccor.setVisible(true);
        }
    }

    private void getMasalah() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRM1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TPasien1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString()); 
            TindakanKebidanan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),62).toString());
        }
    }
    
    private void isBMI(){
        if((!TB.getText().equals(""))&&(!BB.getText().equals(""))){
            BMI.setText(Valid.SetAngka7(Valid.SetAngka(BB.getText())/((Valid.SetAngka(TB.getText())/100)*(Valid.SetAngka(TB.getText())/100)))+"");
        }
    }
}

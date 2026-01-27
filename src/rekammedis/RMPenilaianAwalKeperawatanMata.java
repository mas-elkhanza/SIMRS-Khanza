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
import java.awt.event.WindowAdapter;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
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
public final class RMPenilaianAwalKeperawatanMata extends javax.swing.JDialog {
    private final DefaultTableModel tabMode,tabModeMasalah,tabModeDetailMasalah;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps,ps2;
    private ResultSet rs,rs2;
    private int i=0,jml=0,index=0;
    private DlgCariPetugas petugas;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private volatile boolean ceksukses = false;
    private boolean[] pilih; 
    private String[] kode,masalah;
    private String masalahkeperawatan=""; 
    private StringBuilder htmlContent;
    
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public RMPenilaianAwalKeperawatanMata(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.RM","Nama Pasien","J.K.","Agama","Bahasa","Cacat Fisik","Tgl.Lahir","Tgl.Asuhan","Informasi","TD","Nadi","RR","Suhu",
            "GCS","BB","TB","BMI","Keluhan Utama","Riwayat Penyakit Dahulu","Riwayat Penyakit Sekarang","Riwayat Penyakit Keluarga","Riwayat Pengobatan",
            "Alergi","Alat Bantu","Ket. Alat Bantu","Prothesa","Ket. Prothesa","ADL","Stts Psikologi","Ket. Psikologi","Hubungan Keluarga","Tinggal Dengan",
            "Ket. Tinggal","Ekonomi","Budaya","Ket. Budaya","Edukasi","Ket. Edukasi","Cara Berjalan A","Cara Berjalan B","Cara Berjalan C",
            "Hasil Pengkajian Resiko Jatuh","Lapor Dokter","Ket. Lapor","Skrining Gizi 1","Nilai 1","Skrining Gizi 2","Nilai 2","Skrining Gizi 3","Nilai 3","Skrining Gizi 4","Nilai 4","Total Skor","Tingkat Nyeri","Provokes",
            "Ket. Provokes","Kualitas","Ket. Kualitas","Lokasi","Menyebar","Skala Nyeri","Durasi","Nyeri Hilang","Ket. Hilang Nyeri","Lapor Ke Dokter",
            "Jam Lapor","Visus Kanan","Visus Kiri","Refraksi Kanan","Refraksi Kiri","Tio Kanan","Tio Kiri","Palbera Kanan","Palbera Kiri","Konjungtiva Kanan","Konjungtiva Kiri","Sklera Kanan","Sklera Kiri",
            "Kornea Kanan","Kornea Kiri","BMD Kanan","BMD Kiri","Iris Kanan","Iris Kiri","Pupil Kanan","Pupil Kiri","Lensa Kanan","Lensa Kiri","Oftalmoskopi Kanan","Oftalmoskopi Kiri","Rencana","NIP","Nama Petugas"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 94; i++) {
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
                column.setPreferredWidth(150);
            }else if(i==23){
                column.setPreferredWidth(100);
            }else if(i==24){
                column.setPreferredWidth(60);
            }else if(i==20){
                column.setPreferredWidth(90);
            }else if(i==26){
                column.setPreferredWidth(60);
            }else if(i==27){
                column.setPreferredWidth(90);
            }else if(i==28){
                column.setPreferredWidth(60);
            }else if(i==29){
                column.setPreferredWidth(80);
            }else if(i==30){
                column.setPreferredWidth(100);
            }else if(i==31){
                column.setPreferredWidth(103);
            }else if(i==32){
                column.setPreferredWidth(87);
            }else if(i==33){
                column.setPreferredWidth(90);
            }else if(i==34){
                column.setPreferredWidth(50);
            }else if(i==35){
                column.setPreferredWidth(58);
            }else if(i==36){
                column.setPreferredWidth(90);
            }else if(i==37){
                column.setPreferredWidth(60);
            }else if(i==38){
                column.setPreferredWidth(90);
            }else if(i==39){
                column.setPreferredWidth(87);
            }else if(i==40){
                column.setPreferredWidth(87);
            }else if(i==41){
                column.setPreferredWidth(87);
            }else if(i==42){
                column.setPreferredWidth(206);
            }else if(i==43){
                column.setPreferredWidth(75);
            }else if(i==44){
                column.setPreferredWidth(90);
            }else if(i==45){
                column.setPreferredWidth(80);
            }else if(i==46){
                column.setPreferredWidth(40);
            }else if(i==47){
                column.setPreferredWidth(80);
            }else if(i==48){
                column.setPreferredWidth(40);
            }else if(i==49){
                column.setPreferredWidth(40);
            }else if(i==50){
                column.setPreferredWidth(40);
            }else if(i==51){
                column.setPreferredWidth(40);
            }else if(i==52){
                column.setPreferredWidth(40);
            }else if(i==53){
                column.setPreferredWidth(60);
            }else if(i==54){
                column.setPreferredWidth(87);
            }else if(i==55){
                column.setPreferredWidth(87);
            }else if(i==56){
                column.setPreferredWidth(87);
            }else if(i==57){
                column.setPreferredWidth(90);
            }else if(i==58){
                column.setPreferredWidth(90);
            }else if(i==59){
                column.setPreferredWidth(110);
            }else if(i==60){
                column.setPreferredWidth(56);
            }else if(i==61){
                column.setPreferredWidth(60);
            }else if(i==62){
                column.setPreferredWidth(50);
            }else if(i==63){
                column.setPreferredWidth(90);
            }else if(i==58){
                column.setPreferredWidth(90);
            }else if(i==65){
                column.setPreferredWidth(90);
            }else if(i==66){
                column.setPreferredWidth(70);
            }else if(i==91){
                column.setPreferredWidth(200);
            }else if(i==92){
                column.setPreferredWidth(80);
            }else if(i==93){
                column.setPreferredWidth(150);
            }else{
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeMasalah=new DefaultTableModel(null,new Object[]{
                "P","KODE","MASALAH KEPERAWATAN"
            }){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Double.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbMasalahKeperawatan.setModel(tabModeMasalah);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbMasalahKeperawatan.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbMasalahKeperawatan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        
        for (i = 0; i < 3; i++) {
            TableColumn column = tbMasalahKeperawatan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==2){
                column.setPreferredWidth(350);
            }
        }
        tbMasalahKeperawatan.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeDetailMasalah=new DefaultTableModel(null,new Object[]{
                "Kode","Masalah Keperawatan"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbMasalahDetailMasalah.setModel(tabModeDetailMasalah);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbMasalahDetailMasalah.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbMasalahDetailMasalah.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 2; i++) {
            TableColumn column = tbMasalahDetailMasalah.getColumnModel().getColumn(i);
            if(i==0){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==1){
                column.setPreferredWidth(420);
            }
        }
        tbMasalahDetailMasalah.setDefaultRenderer(Object.class, new WarnaTable());

        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        TD.setDocument(new batasInput((byte)8).getKata(TD));
        Nadi.setDocument(new batasInput((byte)5).getKata(Nadi));
        RR.setDocument(new batasInput((byte)5).getKata(RR));
        Suhu.setDocument(new batasInput((byte)5).getKata(Suhu));
        GCS.setDocument(new batasInput((byte)5).getKata(GCS));
        BB.setDocument(new batasInput((byte)5).getKata(BB));
        TB.setDocument(new batasInput((byte)5).getKata(TB));
        BMI.setDocument(new batasInput((byte)5).getKata(BMI));
        KeluhanUtama.setDocument(new batasInput((int)150).getKata(KeluhanUtama));
        RPD.setDocument(new batasInput((int)100).getKata(RPD));
        RPS.setDocument(new batasInput((int)100).getKata(RPS));
        RPK.setDocument(new batasInput((int)100).getKata(RPK));
        RPO.setDocument(new batasInput((int)100).getKata(RPO));
        Alergi.setDocument(new batasInput((int)20).getKata(Alergi));
        KetBantu.setDocument(new batasInput((int)50).getKata(KetBantu));
        KetProthesa.setDocument(new batasInput((int)50).getKata(KetProthesa));
        KetBudaya.setDocument(new batasInput((int)50).getKata(KetBudaya));
        KetPsiko.setDocument(new batasInput((int)70).getKata(KetPsiko));
        KetTinggal.setDocument(new batasInput((int)40).getKata(KetTinggal));
        KetEdukasi.setDocument(new batasInput((int)50).getKata(KetEdukasi));
        KetLapor.setDocument(new batasInput((int)15).getKata(KetLapor));
        KetProvokes.setDocument(new batasInput((int)40).getKata(KetProvokes));
        KetQuality.setDocument(new batasInput((int)50).getKata(KetQuality));
        Lokasi.setDocument(new batasInput((int)50).getKata(Lokasi));
        Durasi.setDocument(new batasInput((int)20).getKata(Durasi));
        KetNyeri.setDocument(new batasInput((int)40).getKata(KetNyeri));
        KetDokter.setDocument(new batasInput((int)15).getKata(KetDokter));
        Rencana.setDocument(new batasInput((int)200).getKata(Rencana));
        
        TCari.setDocument(new batasInput((int)100).getKata(TCari));
        
        if(koneksiDB.CARICEPAT().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        runBackground(() ->tampil());
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        runBackground(() ->tampil());
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        runBackground(() ->tampil());
                    }
                }
            });
        }
        
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
        BtnPetugas = new widget.Button();
        jLabel8 = new widget.Label();
        TglLahir = new widget.TextBox();
        jLabel9 = new widget.Label();
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
        jLabel37 = new widget.Label();
        Alergi = new widget.TextBox();
        jLabel43 = new widget.Label();
        jLabel50 = new widget.Label();
        jLabel52 = new widget.Label();
        Informasi = new widget.ComboBox();
        jLabel53 = new widget.Label();
        scrollPane1 = new widget.ScrollPane();
        KeluhanUtama = new widget.TextArea();
        jLabel30 = new widget.Label();
        scrollPane2 = new widget.ScrollPane();
        RPD = new widget.TextArea();
        jLabel31 = new widget.Label();
        scrollPane3 = new widget.ScrollPane();
        RPK = new widget.TextArea();
        jLabel32 = new widget.Label();
        scrollPane4 = new widget.ScrollPane();
        RPO = new widget.TextArea();
        AlatBantu = new widget.ComboBox();
        KetBantu = new widget.TextBox();
        jLabel54 = new widget.Label();
        Prothesa = new widget.ComboBox();
        KetProthesa = new widget.TextBox();
        jLabel55 = new widget.Label();
        ADL = new widget.ComboBox();
        jLabel57 = new widget.Label();
        StatusPsiko = new widget.ComboBox();
        KetPsiko = new widget.TextBox();
        jLabel58 = new widget.Label();
        HubunganKeluarga = new widget.ComboBox();
        jLabel59 = new widget.Label();
        TinggalDengan = new widget.ComboBox();
        KetTinggal = new widget.TextBox();
        jLabel60 = new widget.Label();
        Ekonomi = new widget.ComboBox();
        jLabel61 = new widget.Label();
        Edukasi = new widget.ComboBox();
        KetEdukasi = new widget.TextBox();
        jLabel64 = new widget.Label();
        jLabel65 = new widget.Label();
        jLabel66 = new widget.Label();
        Lapor = new widget.ComboBox();
        ATS = new widget.ComboBox();
        BJM = new widget.ComboBox();
        jLabel67 = new widget.Label();
        Hasil = new widget.ComboBox();
        jLabel68 = new widget.Label();
        SG2 = new widget.ComboBox();
        KetLapor = new widget.TextBox();
        jLabel69 = new widget.Label();
        jLabel70 = new widget.Label();
        SG1 = new widget.ComboBox();
        jLabel72 = new widget.Label();
        Nilai1 = new widget.ComboBox();
        MSA = new widget.ComboBox();
        jLabel73 = new widget.Label();
        Nilai2 = new widget.ComboBox();
        jLabel75 = new widget.Label();
        Nyeri = new widget.ComboBox();
        jLabel79 = new widget.Label();
        Provokes = new widget.ComboBox();
        KetProvokes = new widget.TextBox();
        jLabel80 = new widget.Label();
        Quality = new widget.ComboBox();
        KetQuality = new widget.TextBox();
        jLabel81 = new widget.Label();
        jLabel82 = new widget.Label();
        Lokasi = new widget.TextBox();
        jLabel83 = new widget.Label();
        Menyebar = new widget.ComboBox();
        jLabel84 = new widget.Label();
        jLabel85 = new widget.Label();
        SkalaNyeri = new widget.ComboBox();
        jLabel86 = new widget.Label();
        Durasi = new widget.TextBox();
        jLabel87 = new widget.Label();
        jLabel88 = new widget.Label();
        NyeriHilang = new widget.ComboBox();
        KetNyeri = new widget.TextBox();
        jLabel89 = new widget.Label();
        PadaDokter = new widget.ComboBox();
        KetDokter = new widget.TextBox();
        scrollPane5 = new widget.ScrollPane();
        Rencana = new widget.TextArea();
        jLabel92 = new widget.Label();
        TotalHasil = new widget.TextBox();
        TglAsuhan = new widget.Tanggal();
        jLabel28 = new widget.Label();
        GCS = new widget.TextBox();
        jLabel93 = new widget.Label();
        jLabel94 = new widget.Label();
        jLabel51 = new widget.Label();
        CacatFisik = new widget.TextBox();
        jLabel56 = new widget.Label();
        jLabel62 = new widget.Label();
        jLabel95 = new widget.Label();
        StatusBudaya = new widget.ComboBox();
        KetBudaya = new widget.TextBox();
        jLabel96 = new widget.Label();
        jLabel97 = new widget.Label();
        jLabel63 = new widget.Label();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        jSeparator6 = new javax.swing.JSeparator();
        jSeparator7 = new javax.swing.JSeparator();
        jSeparator8 = new javax.swing.JSeparator();
        jLabel74 = new widget.Label();
        PanelWall = new usu.widget.glass.PanelGlass();
        jSeparator9 = new javax.swing.JSeparator();
        jLabel71 = new widget.Label();
        jSeparator10 = new javax.swing.JSeparator();
        Scroll6 = new widget.ScrollPane();
        tbMasalahKeperawatan = new widget.Table();
        label12 = new widget.Label();
        TCariMasalah = new widget.TextBox();
        BtnCariPemeriksaan1 = new widget.Button();
        BtnTambahMasalah = new widget.Button();
        Bahasa = new widget.TextBox();
        jLabel76 = new widget.Label();
        jLabel77 = new widget.Label();
        Agama = new widget.TextBox();
        jLabel78 = new widget.Label();
        PanelWall1 = new usu.widget.glass.PanelGlass();
        label15 = new widget.Label();
        label16 = new widget.Label();
        VisusKanan = new widget.TextBox();
        VisusKiri = new widget.TextBox();
        label17 = new widget.Label();
        PanelWall2 = new usu.widget.glass.PanelGlass();
        label18 = new widget.Label();
        RefraksiKanan = new widget.TextBox();
        RefraksiKiri = new widget.TextBox();
        TioKanan = new widget.TextBox();
        label19 = new widget.Label();
        TioKiri = new widget.TextBox();
        PalberaKanan = new widget.TextBox();
        label20 = new widget.Label();
        PalberaKiri = new widget.TextBox();
        KonjungtivaKanan = new widget.TextBox();
        label21 = new widget.Label();
        KonjungtivaKiri = new widget.TextBox();
        SkleraKanan = new widget.TextBox();
        label22 = new widget.Label();
        SkleraKiri = new widget.TextBox();
        KorneaKanan = new widget.TextBox();
        label23 = new widget.Label();
        KorneaKiri = new widget.TextBox();
        BMDKanan = new widget.TextBox();
        label24 = new widget.Label();
        BMDKiri = new widget.TextBox();
        IrisKanan = new widget.TextBox();
        label25 = new widget.Label();
        IrisKiri = new widget.TextBox();
        PupilKanan = new widget.TextBox();
        label26 = new widget.Label();
        PupilKiri = new widget.TextBox();
        LensaKanan = new widget.TextBox();
        LensaKiri = new widget.TextBox();
        label27 = new widget.Label();
        jLabel90 = new widget.Label();
        ScorllPane5 = new widget.ScrollPane();
        OftalmoskopiKanan = new widget.TextArea();
        jLabel91 = new widget.Label();
        ScrollPane6 = new widget.ScrollPane();
        OftalmoskopiKiri = new widget.TextArea();
        jLabel33 = new widget.Label();
        scrollPane9 = new widget.ScrollPane();
        RPS = new widget.TextArea();
        jLabel98 = new widget.Label();
        jLabel99 = new widget.Label();
        SG3 = new widget.ComboBox();
        jLabel100 = new widget.Label();
        Nilai3 = new widget.ComboBox();
        SG4 = new widget.ComboBox();
        jLabel101 = new widget.Label();
        Nilai4 = new widget.ComboBox();
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
        Scroll7 = new widget.ScrollPane();
        tbMasalahDetailMasalah = new widget.Table();
        scrollPane6 = new widget.ScrollPane();
        DetailRencana = new widget.TextArea();

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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Pengkajian Awal Keperawatan Mata ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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
        FormInput.setPreferredSize(new java.awt.Dimension(870, 1610));
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

        BtnPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPetugas.setMnemonic('2');
        BtnPetugas.setToolTipText("Alt+2");
        BtnPetugas.setName("BtnPetugas"); // NOI18N
        BtnPetugas.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnPetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPetugasActionPerformed(evt);
            }
        });
        BtnPetugas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPetugasKeyPressed(evt);
            }
        });
        FormInput.add(BtnPetugas);
        BtnPetugas.setBounds(358, 40, 28, 23);

        jLabel8.setText("Tgl.Lahir :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(580, 10, 60, 23);

        TglLahir.setEditable(false);
        TglLahir.setHighlighter(null);
        TglLahir.setName("TglLahir"); // NOI18N
        FormInput.add(TglLahir);
        TglLahir.setBounds(644, 10, 80, 23);

        jLabel9.setText("Riwayat Pengobatan :");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(440, 230, 150, 23);

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
        jLabel12.setBounds(0, 140, 70, 23);

        BB.setFocusTraversalPolicyProvider(true);
        BB.setName("BB"); // NOI18N
        BB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BBKeyPressed(evt);
            }
        });
        FormInput.add(BB);
        BB.setBounds(74, 140, 60, 23);

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel13.setText("Kg");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(137, 140, 30, 23);

        TB.setFocusTraversalPolicyProvider(true);
        TB.setName("TB"); // NOI18N
        TB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TBKeyPressed(evt);
            }
        });
        FormInput.add(TB);
        TB.setBounds(250, 140, 60, 23);

        jLabel15.setText("TB :");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(206, 140, 40, 23);

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
        jLabel24.setText(" cm");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(313, 140, 30, 23);

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
        jLabel14.setBounds(378, 140, 40, 23);

        BMI.setFocusTraversalPolicyProvider(true);
        BMI.setName("BMI"); // NOI18N
        BMI.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BMIKeyPressed(evt);
            }
        });
        FormInput.add(BMI);
        BMI.setBounds(422, 140, 60, 23);

        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel27.setText("Kg/m²");
        jLabel27.setName("jLabel27"); // NOI18N
        FormInput.add(jLabel27);
        jLabel27.setBounds(485, 140, 50, 23);

        jLabel36.setText("Informasi didapat dari :");
        jLabel36.setName("jLabel36"); // NOI18N
        FormInput.add(jLabel36);
        jLabel36.setBounds(592, 40, 130, 23);

        jLabel37.setText("Riwayat Alergi :");
        jLabel37.setName("jLabel37"); // NOI18N
        FormInput.add(jLabel37);
        jLabel37.setBounds(440, 270, 150, 23);

        Alergi.setFocusTraversalPolicyProvider(true);
        Alergi.setName("Alergi"); // NOI18N
        Alergi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlergiKeyPressed(evt);
            }
        });
        FormInput.add(Alergi);
        Alergi.setBounds(594, 270, 260, 23);

        jLabel43.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel43.setText("2. Apakah ada penurunan BB selama satu bulan terakhir ? ");
        jLabel43.setName("jLabel43"); // NOI18N
        FormInput.add(jLabel43);
        jLabel43.setBounds(40, 740, 460, 23);
        jLabel43.getAccessibleContext().setAccessibleName("2. Apakah ada penurunan BB selama satu bulan terakhir ?\n( berdasarkan penilaian objektif data BB bila ada/penilaian subjektif \ndari orang tua pasien atau untuk bayi < 1 th : BB naik selama 3 bulan terakhir )");

        jLabel50.setText("Prothesa :");
        jLabel50.setName("jLabel50"); // NOI18N
        FormInput.add(jLabel50);
        jLabel50.setBounds(476, 340, 60, 23);

        jLabel52.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel52.setText("VIII. PENGKAJIAN TINGKAT NYERI");
        jLabel52.setName("jLabel52"); // NOI18N
        FormInput.add(jLabel52);
        jLabel52.setBounds(10, 860, 380, 23);

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
        scrollPane1.setBounds(179, 190, 260, 32);

        jLabel30.setText("Keluhan Utama :");
        jLabel30.setName("jLabel30"); // NOI18N
        FormInput.add(jLabel30);
        jLabel30.setBounds(0, 190, 175, 20);

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
        scrollPane2.setBounds(179, 230, 260, 32);

        jLabel31.setText("Riwayat Penyakit Dahulu :");
        jLabel31.setName("jLabel31"); // NOI18N
        FormInput.add(jLabel31);
        jLabel31.setBounds(0, 230, 175, 23);

        scrollPane3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane3.setName("scrollPane3"); // NOI18N

        RPK.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        RPK.setColumns(20);
        RPK.setRows(5);
        RPK.setName("RPK"); // NOI18N
        RPK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RPKKeyPressed(evt);
            }
        });
        scrollPane3.setViewportView(RPK);

        FormInput.add(scrollPane3);
        scrollPane3.setBounds(594, 190, 260, 32);

        jLabel32.setText("Riwayat Penyakit Keluarga :");
        jLabel32.setName("jLabel32"); // NOI18N
        FormInput.add(jLabel32);
        jLabel32.setBounds(440, 190, 150, 23);

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
        scrollPane4.setBounds(594, 230, 260, 32);

        AlatBantu.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        AlatBantu.setName("AlatBantu"); // NOI18N
        AlatBantu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlatBantuKeyPressed(evt);
            }
        });
        FormInput.add(AlatBantu);
        AlatBantu.setBounds(124, 340, 90, 23);

        KetBantu.setFocusTraversalPolicyProvider(true);
        KetBantu.setName("KetBantu"); // NOI18N
        KetBantu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetBantuKeyPressed(evt);
            }
        });
        FormInput.add(KetBantu);
        KetBantu.setBounds(218, 340, 220, 23);

        jLabel54.setText("a. Hubungan pasien dengan anggota keluarga :");
        jLabel54.setName("jLabel54"); // NOI18N
        FormInput.add(jLabel54);
        jLabel54.setBounds(35, 470, 250, 23);

        Prothesa.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Prothesa.setName("Prothesa"); // NOI18N
        Prothesa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ProthesaKeyPressed(evt);
            }
        });
        FormInput.add(Prothesa);
        Prothesa.setBounds(540, 340, 90, 23);

        KetProthesa.setFocusTraversalPolicyProvider(true);
        KetProthesa.setName("KetProthesa"); // NOI18N
        KetProthesa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetProthesaKeyPressed(evt);
            }
        });
        FormInput.add(KetProthesa);
        KetProthesa.setBounds(634, 340, 220, 23);

        jLabel55.setText("Alat Bantu :");
        jLabel55.setName("jLabel55"); // NOI18N
        FormInput.add(jLabel55);
        jLabel55.setBounds(0, 340, 120, 23);

        ADL.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Mandiri", "Dibantu" }));
        ADL.setName("ADL"); // NOI18N
        ADL.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ADLKeyPressed(evt);
            }
        });
        FormInput.add(ADL);
        ADL.setBounds(724, 370, 130, 23);

        jLabel57.setText("Aktivitas Kehidupan Sehari-hari ( ADL ) :");
        jLabel57.setName("jLabel57"); // NOI18N
        FormInput.add(jLabel57);
        jLabel57.setBounds(440, 370, 280, 23);

        StatusPsiko.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tenang", "Takut", "Cemas", "Depresi", "Lain-lain" }));
        StatusPsiko.setName("StatusPsiko"); // NOI18N
        StatusPsiko.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                StatusPsikoKeyPressed(evt);
            }
        });
        FormInput.add(StatusPsiko);
        StatusPsiko.setBounds(134, 420, 110, 23);

        KetPsiko.setFocusTraversalPolicyProvider(true);
        KetPsiko.setName("KetPsiko"); // NOI18N
        KetPsiko.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetPsikoKeyPressed(evt);
            }
        });
        FormInput.add(KetPsiko);
        KetPsiko.setBounds(248, 420, 230, 23);

        jLabel58.setText("Edukasi diberikan kepada :");
        jLabel58.setName("jLabel58"); // NOI18N
        FormInput.add(jLabel58);
        jLabel58.setBounds(226, 530, 140, 23);

        HubunganKeluarga.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Baik", "Tidak Baik" }));
        HubunganKeluarga.setName("HubunganKeluarga"); // NOI18N
        HubunganKeluarga.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HubunganKeluargaKeyPressed(evt);
            }
        });
        FormInput.add(HubunganKeluarga);
        HubunganKeluarga.setBounds(289, 470, 100, 23);

        jLabel59.setText("Status Sosial dan ekonomi :");
        jLabel59.setName("jLabel59"); // NOI18N
        FormInput.add(jLabel59);
        jLabel59.setBounds(0, 450, 176, 23);

        TinggalDengan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Sendiri", "Orang Tua", "Suami / Istri", "Lainnya" }));
        TinggalDengan.setName("TinggalDengan"); // NOI18N
        TinggalDengan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TinggalDenganKeyPressed(evt);
            }
        });
        FormInput.add(TinggalDengan);
        TinggalDengan.setBounds(497, 470, 110, 23);

        KetTinggal.setFocusTraversalPolicyProvider(true);
        KetTinggal.setName("KetTinggal"); // NOI18N
        KetTinggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetTinggalKeyPressed(evt);
            }
        });
        FormInput.add(KetTinggal);
        KetTinggal.setBounds(610, 470, 85, 23);

        jLabel60.setText("b. Tinggal dengan :");
        jLabel60.setName("jLabel60"); // NOI18N
        FormInput.add(jLabel60);
        jLabel60.setBounds(393, 470, 100, 23);

        Ekonomi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Baik", "Cukup", "Kurang" }));
        Ekonomi.setName("Ekonomi"); // NOI18N
        Ekonomi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EkonomiKeyPressed(evt);
            }
        });
        FormInput.add(Ekonomi);
        Ekonomi.setBounds(770, 470, 84, 23);

        jLabel61.setText("c. Ekonomi :");
        jLabel61.setName("jLabel61"); // NOI18N
        FormInput.add(jLabel61);
        jLabel61.setBounds(695, 470, 71, 23);

        Edukasi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Pasien", "Keluarga" }));
        Edukasi.setName("Edukasi"); // NOI18N
        Edukasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EdukasiKeyPressed(evt);
            }
        });
        FormInput.add(Edukasi);
        Edukasi.setBounds(370, 530, 110, 23);

        KetEdukasi.setFocusTraversalPolicyProvider(true);
        KetEdukasi.setName("KetEdukasi"); // NOI18N
        KetEdukasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetEdukasiKeyPressed(evt);
            }
        });
        FormInput.add(KetEdukasi);
        KetEdukasi.setBounds(484, 530, 370, 23);

        jLabel64.setText("Jam  :");
        jLabel64.setName("jLabel64"); // NOI18N
        FormInput.add(jLabel64);
        jLabel64.setBounds(720, 1040, 50, 23);

        jLabel65.setText("1. Tidak seimbang / sempoyongan / limbung :");
        jLabel65.setName("jLabel65"); // NOI18N
        FormInput.add(jLabel65);
        jLabel65.setBounds(35, 600, 250, 23);

        jLabel66.setText("2. Jalan dengan menggunakan alat bantu (kruk, tripot, kursi roda, orang lain) :");
        jLabel66.setName("jLabel66"); // NOI18N
        FormInput.add(jLabel66);
        jLabel66.setBounds(370, 600, 400, 23);

        Lapor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Lapor.setName("Lapor"); // NOI18N
        Lapor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LaporKeyPressed(evt);
            }
        });
        FormInput.add(Lapor);
        Lapor.setBounds(575, 660, 80, 23);

        ATS.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        ATS.setName("ATS"); // NOI18N
        ATS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ATSKeyPressed(evt);
            }
        });
        FormInput.add(ATS);
        ATS.setBounds(289, 600, 80, 23);

        BJM.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        BJM.setName("BJM"); // NOI18N
        BJM.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BJMKeyPressed(evt);
            }
        });
        FormInput.add(BJM);
        BJM.setBounds(774, 600, 80, 23);

        jLabel67.setText("Menyebar :");
        jLabel67.setName("jLabel67"); // NOI18N
        FormInput.add(jLabel67);
        jLabel67.setBounds(690, 970, 79, 23);

        Hasil.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak beresiko (tidak ditemukan a dan b)", "Resiko rendah (ditemukan a/b)", "Resiko tinggi (ditemukan a dan b)" }));
        Hasil.setName("Hasil"); // NOI18N
        Hasil.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HasilKeyPressed(evt);
            }
        });
        FormInput.add(Hasil);
        Hasil.setBounds(76, 660, 293, 23);

        jLabel68.setText("Hasil :");
        jLabel68.setName("jLabel68"); // NOI18N
        FormInput.add(jLabel68);
        jLabel68.setBounds(0, 660, 72, 23);

        SG2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        SG2.setName("SG2"); // NOI18N
        SG2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SG2ItemStateChanged(evt);
            }
        });
        SG2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SG2KeyPressed(evt);
            }
        });
        FormInput.add(SG2);
        SG2.setBounds(540, 740, 160, 23);

        KetLapor.setFocusTraversalPolicyProvider(true);
        KetLapor.setName("KetLapor"); // NOI18N
        KetLapor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetLaporKeyPressed(evt);
            }
        });
        FormInput.add(KetLapor);
        KetLapor.setBounds(774, 660, 80, 23);

        jLabel69.setText("Nilai :");
        jLabel69.setName("jLabel69"); // NOI18N
        FormInput.add(jLabel69);
        jLabel69.setBounds(695, 740, 75, 23);

        jLabel70.setText("b. Menopang saat akan duduk, tampak memegang pinggiran kursi atau meja / benda lain sebagai penopang :");
        jLabel70.setName("jLabel70"); // NOI18N
        FormInput.add(jLabel70);
        jLabel70.setBounds(0, 630, 571, 23);

        SG1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Tidak Yakin", "Ya, 1-5 Kg", "Ya, 6-10 Kg", "Ya, 11-15 Kg", "Ya, >15 Kg" }));
        SG1.setName("SG1"); // NOI18N
        SG1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SG1ItemStateChanged(evt);
            }
        });
        SG1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SG1KeyPressed(evt);
            }
        });
        FormInput.add(SG1);
        SG1.setBounds(540, 710, 160, 23);

        jLabel72.setText("a. Cara Berjalan :");
        jLabel72.setName("jLabel72"); // NOI18N
        FormInput.add(jLabel72);
        jLabel72.setBounds(0, 580, 126, 23);

        Nilai1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "2", "1", "2", "3", "4" }));
        Nilai1.setName("Nilai1"); // NOI18N
        Nilai1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                Nilai1ItemStateChanged(evt);
            }
        });
        Nilai1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Nilai1KeyPressed(evt);
            }
        });
        FormInput.add(Nilai1);
        Nilai1.setBounds(774, 710, 80, 23);

        MSA.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        MSA.setName("MSA"); // NOI18N
        MSA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MSAKeyPressed(evt);
            }
        });
        FormInput.add(MSA);
        MSA.setBounds(575, 630, 80, 23);

        jLabel73.setText("Total Skor :");
        jLabel73.setName("jLabel73"); // NOI18N
        FormInput.add(jLabel73);
        jLabel73.setBounds(695, 830, 75, 23);

        Nilai2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1" }));
        Nilai2.setName("Nilai2"); // NOI18N
        Nilai2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                Nilai2ItemStateChanged(evt);
            }
        });
        Nilai2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Nilai2KeyPressed(evt);
            }
        });
        FormInput.add(Nilai2);
        Nilai2.setBounds(774, 740, 80, 23);

        jLabel75.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel75.setText("1. Apakah Pasien tampak Kurus ?");
        jLabel75.setName("jLabel75"); // NOI18N
        FormInput.add(jLabel75);
        jLabel75.setBounds(40, 710, 460, 23);

        Nyeri.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada Nyeri", "Nyeri Akut", "Nyeri Kronis" }));
        Nyeri.setName("Nyeri"); // NOI18N
        Nyeri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NyeriKeyPressed(evt);
            }
        });
        FormInput.add(Nyeri);
        Nyeri.setBounds(375, 890, 130, 23);

        jLabel79.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel79.setText("Rencana Keperawatan/Terapi/Tindakan :");
        jLabel79.setName("jLabel79"); // NOI18N
        FormInput.add(jLabel79);
        jLabel79.setBounds(450, 1500, 260, 23);

        Provokes.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Proses Penyakit", "Benturan", "Lain-lain" }));
        Provokes.setName("Provokes"); // NOI18N
        Provokes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ProvokesKeyPressed(evt);
            }
        });
        FormInput.add(Provokes);
        Provokes.setBounds(574, 890, 130, 23);

        KetProvokes.setFocusTraversalPolicyProvider(true);
        KetProvokes.setName("KetProvokes"); // NOI18N
        KetProvokes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetProvokesKeyPressed(evt);
            }
        });
        FormInput.add(KetProvokes);
        KetProvokes.setBounds(708, 890, 146, 23);

        jLabel80.setText("Penyebab :");
        jLabel80.setName("jLabel80"); // NOI18N
        FormInput.add(jLabel80);
        jLabel80.setBounds(510, 890, 60, 23);

        Quality.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seperti Tertusuk", "Berdenyut", "Teriris", "Tertindih", "Tertiban", "Lain-lain" }));
        Quality.setName("Quality"); // NOI18N
        Quality.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                QualityKeyPressed(evt);
            }
        });
        FormInput.add(Quality);
        Quality.setBounds(429, 920, 140, 23);

        KetQuality.setFocusTraversalPolicyProvider(true);
        KetQuality.setName("KetQuality"); // NOI18N
        KetQuality.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetQualityKeyPressed(evt);
            }
        });
        FormInput.add(KetQuality);
        KetQuality.setBounds(573, 920, 280, 23);

        jLabel81.setText("Kualitas :");
        jLabel81.setName("jLabel81"); // NOI18N
        FormInput.add(jLabel81);
        jLabel81.setBounds(370, 920, 55, 23);

        jLabel82.setText("Wilayah :");
        jLabel82.setName("jLabel82"); // NOI18N
        FormInput.add(jLabel82);
        jLabel82.setBounds(370, 950, 55, 23);

        Lokasi.setFocusTraversalPolicyProvider(true);
        Lokasi.setName("Lokasi"); // NOI18N
        Lokasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LokasiKeyPressed(evt);
            }
        });
        FormInput.add(Lokasi);
        Lokasi.setBounds(458, 970, 220, 23);

        jLabel83.setText("Lokasi :");
        jLabel83.setName("jLabel83"); // NOI18N
        FormInput.add(jLabel83);
        jLabel83.setBounds(394, 970, 60, 23);

        Menyebar.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Menyebar.setName("Menyebar"); // NOI18N
        Menyebar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MenyebarKeyPressed(evt);
            }
        });
        FormInput.add(Menyebar);
        Menyebar.setBounds(773, 970, 80, 23);

        jLabel84.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel84.setText("Menit");
        jLabel84.setName("jLabel84"); // NOI18N
        FormInput.add(jLabel84);
        jLabel84.setBounds(815, 1000, 35, 23);

        jLabel85.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel85.setText("Skala Nyeri");
        jLabel85.setName("jLabel85"); // NOI18N
        FormInput.add(jLabel85);
        jLabel85.setBounds(428, 1000, 60, 23);

        SkalaNyeri.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));
        SkalaNyeri.setName("SkalaNyeri"); // NOI18N
        SkalaNyeri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkalaNyeriKeyPressed(evt);
            }
        });
        FormInput.add(SkalaNyeri);
        SkalaNyeri.setBounds(491, 1000, 70, 23);

        jLabel86.setText("Diberitahukan pada dokter ?");
        jLabel86.setName("jLabel86"); // NOI18N
        FormInput.add(jLabel86);
        jLabel86.setBounds(480, 1040, 150, 23);

        Durasi.setFocusTraversalPolicyProvider(true);
        Durasi.setName("Durasi"); // NOI18N
        Durasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DurasiKeyPressed(evt);
            }
        });
        FormInput.add(Durasi);
        Durasi.setBounds(721, 1000, 90, 23);

        jLabel87.setText("Waktu / Durasi :");
        jLabel87.setName("jLabel87"); // NOI18N
        FormInput.add(jLabel87);
        jLabel87.setBounds(627, 1000, 90, 23);

        jLabel88.setText("Severity :");
        jLabel88.setName("jLabel88"); // NOI18N
        FormInput.add(jLabel88);
        jLabel88.setBounds(370, 1000, 55, 23);

        NyeriHilang.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Istirahat", "Medengar Musik", "Minum Obat" }));
        NyeriHilang.setName("NyeriHilang"); // NOI18N
        NyeriHilang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NyeriHilangKeyPressed(evt);
            }
        });
        FormInput.add(NyeriHilang);
        NyeriHilang.setBounds(134, 1040, 130, 23);

        KetNyeri.setFocusTraversalPolicyProvider(true);
        KetNyeri.setName("KetNyeri"); // NOI18N
        KetNyeri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetNyeriKeyPressed(evt);
            }
        });
        FormInput.add(KetNyeri);
        KetNyeri.setBounds(268, 1040, 150, 23);

        jLabel89.setText("Nyeri hilang bila :");
        jLabel89.setName("jLabel89"); // NOI18N
        FormInput.add(jLabel89);
        jLabel89.setBounds(0, 1040, 130, 23);

        PadaDokter.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        PadaDokter.setName("PadaDokter"); // NOI18N
        PadaDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PadaDokterKeyPressed(evt);
            }
        });
        FormInput.add(PadaDokter);
        PadaDokter.setBounds(634, 1040, 80, 23);

        KetDokter.setFocusTraversalPolicyProvider(true);
        KetDokter.setName("KetDokter"); // NOI18N
        KetDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetDokterKeyPressed(evt);
            }
        });
        FormInput.add(KetDokter);
        KetDokter.setBounds(774, 1040, 80, 23);

        scrollPane5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane5.setName("scrollPane5"); // NOI18N

        Rencana.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Rencana.setColumns(20);
        Rencana.setRows(5);
        Rencana.setName("Rencana"); // NOI18N
        Rencana.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RencanaKeyPressed(evt);
            }
        });
        scrollPane5.setViewportView(Rencana);

        FormInput.add(scrollPane5);
        scrollPane5.setBounds(450, 1520, 400, 75);

        jLabel92.setText("Nilai :");
        jLabel92.setName("jLabel92"); // NOI18N
        FormInput.add(jLabel92);
        jLabel92.setBounds(695, 710, 75, 23);

        TotalHasil.setEditable(false);
        TotalHasil.setText("0");
        TotalHasil.setFocusTraversalPolicyProvider(true);
        TotalHasil.setName("TotalHasil"); // NOI18N
        TotalHasil.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TotalHasilKeyPressed(evt);
            }
        });
        FormInput.add(TotalHasil);
        TotalHasil.setBounds(774, 830, 80, 23);

        TglAsuhan.setForeground(new java.awt.Color(50, 70, 50));
        TglAsuhan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "27-01-2026 19:01:17" }));
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

        jLabel93.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel93.setText("II. STATUS NUTRISI");
        jLabel93.setName("jLabel93"); // NOI18N
        FormInput.add(jLabel93);
        jLabel93.setBounds(10, 120, 180, 23);

        jLabel94.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel94.setText("III. RIWAYAT KESEHATAN");
        jLabel94.setName("jLabel94"); // NOI18N
        FormInput.add(jLabel94);
        jLabel94.setBounds(10, 170, 180, 23);

        jLabel51.setText("Cacat Fisik :");
        jLabel51.setName("jLabel51"); // NOI18N
        FormInput.add(jLabel51);
        jLabel51.setBounds(0, 370, 120, 23);

        CacatFisik.setEditable(false);
        CacatFisik.setFocusTraversalPolicyProvider(true);
        CacatFisik.setName("CacatFisik"); // NOI18N
        CacatFisik.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CacatFisikKeyPressed(evt);
            }
        });
        FormInput.add(CacatFisik);
        CacatFisik.setBounds(124, 370, 314, 23);

        jLabel56.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel56.setText("IV. FUNGSIONAL");
        jLabel56.setName("jLabel56"); // NOI18N
        FormInput.add(jLabel56);
        jLabel56.setBounds(10, 320, 230, 23);

        jLabel62.setText("Status Psikologis :");
        jLabel62.setName("jLabel62"); // NOI18N
        FormInput.add(jLabel62);
        jLabel62.setBounds(0, 420, 130, 23);

        jLabel95.setText("Kepercayaan / Budaya / Nilai-nilai khusus yang perlu diperhatikan :");
        jLabel95.setName("jLabel95"); // NOI18N
        FormInput.add(jLabel95);
        jLabel95.setBounds(0, 500, 366, 23);

        StatusBudaya.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada", "Ada" }));
        StatusBudaya.setName("StatusBudaya"); // NOI18N
        StatusBudaya.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                StatusBudayaKeyPressed(evt);
            }
        });
        FormInput.add(StatusBudaya);
        StatusBudaya.setBounds(370, 500, 110, 23);

        KetBudaya.setFocusTraversalPolicyProvider(true);
        KetBudaya.setName("KetBudaya"); // NOI18N
        KetBudaya.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetBudayaKeyPressed(evt);
            }
        });
        FormInput.add(KetBudaya);
        KetBudaya.setBounds(484, 500, 370, 23);

        jLabel96.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel96.setText("V. RIWAYAT PSIKO-SOSIAL, SPIRITUAL DAN BUDAYA");
        jLabel96.setName("jLabel96"); // NOI18N
        FormInput.add(jLabel96);
        jLabel96.setBounds(10, 400, 380, 23);

        jLabel97.setText("Dilaporkan kepada dokter ?");
        jLabel97.setName("jLabel97"); // NOI18N
        FormInput.add(jLabel97);
        jLabel97.setBounds(381, 660, 190, 23);

        jLabel63.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel63.setText("VI. PENGKAJIAN RESIKO JATUH");
        jLabel63.setName("jLabel63"); // NOI18N
        FormInput.add(jLabel63);
        jLabel63.setBounds(10, 560, 380, 23);

        jSeparator1.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator1.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator1.setName("jSeparator1"); // NOI18N
        FormInput.add(jSeparator1);
        jSeparator1.setBounds(0, 70, 880, 1);

        jSeparator2.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator2.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator2.setName("jSeparator2"); // NOI18N
        FormInput.add(jSeparator2);
        jSeparator2.setBounds(0, 120, 880, 1);

        jSeparator3.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator3.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator3.setName("jSeparator3"); // NOI18N
        FormInput.add(jSeparator3);
        jSeparator3.setBounds(0, 170, 880, 1);

        jSeparator4.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator4.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator4.setName("jSeparator4"); // NOI18N
        FormInput.add(jSeparator4);
        jSeparator4.setBounds(0, 320, 880, 1);

        jSeparator5.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator5.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator5.setName("jSeparator5"); // NOI18N
        FormInput.add(jSeparator5);
        jSeparator5.setBounds(0, 400, 880, 1);

        jSeparator6.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator6.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator6.setName("jSeparator6"); // NOI18N
        FormInput.add(jSeparator6);
        jSeparator6.setBounds(0, 560, 880, 1);

        jSeparator7.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator7.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator7.setName("jSeparator7"); // NOI18N
        FormInput.add(jSeparator7);
        jSeparator7.setBounds(0, 690, 880, 1);

        jSeparator8.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator8.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator8.setName("jSeparator8"); // NOI18N
        FormInput.add(jSeparator8);
        jSeparator8.setBounds(0, 860, 880, 1);

        jLabel74.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel74.setText("VII. SKRINING GIZI");
        jLabel74.setName("jLabel74"); // NOI18N
        FormInput.add(jLabel74);
        jLabel74.setBounds(10, 690, 380, 23);

        PanelWall.setBackground(new java.awt.Color(29, 29, 29));
        PanelWall.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/picture/nyeri.png"))); // NOI18N
        PanelWall.setBackgroundImageType(usu.widget.constan.BackgroundConstan.BACKGROUND_IMAGE_STRECT);
        PanelWall.setPreferredSize(new java.awt.Dimension(200, 200));
        PanelWall.setRound(false);
        PanelWall.setWarna(new java.awt.Color(110, 110, 110));
        PanelWall.setLayout(null);
        FormInput.add(PanelWall);
        PanelWall.setBounds(40, 890, 320, 130);

        jSeparator9.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator9.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator9.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator9.setName("jSeparator9"); // NOI18N
        FormInput.add(jSeparator9);
        jSeparator9.setBounds(365, 885, 1, 140);

        jLabel71.setText("Jam dilaporkan :");
        jLabel71.setName("jLabel71"); // NOI18N
        FormInput.add(jLabel71);
        jLabel71.setBounds(680, 660, 90, 23);

        jSeparator10.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator10.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator10.setName("jSeparator10"); // NOI18N
        FormInput.add(jSeparator10);
        jSeparator10.setBounds(0, 1070, 880, 1);

        Scroll6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 253)));
        Scroll6.setName("Scroll6"); // NOI18N
        Scroll6.setOpaque(true);

        tbMasalahKeperawatan.setName("tbMasalahKeperawatan"); // NOI18N
        Scroll6.setViewportView(tbMasalahKeperawatan);

        FormInput.add(Scroll6);
        Scroll6.setBounds(10, 1420, 400, 133);

        label12.setText("Key Word :");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(60, 23));
        FormInput.add(label12);
        label12.setBounds(20, 1570, 60, 23);

        TCariMasalah.setToolTipText("Alt+C");
        TCariMasalah.setName("TCariMasalah"); // NOI18N
        TCariMasalah.setPreferredSize(new java.awt.Dimension(140, 23));
        TCariMasalah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariMasalahKeyPressed(evt);
            }
        });
        FormInput.add(TCariMasalah);
        TCariMasalah.setBounds(80, 1570, 245, 23);

        BtnCariPemeriksaan1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCariPemeriksaan1.setMnemonic('1');
        BtnCariPemeriksaan1.setToolTipText("Alt+1");
        BtnCariPemeriksaan1.setName("BtnCariPemeriksaan1"); // NOI18N
        BtnCariPemeriksaan1.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCariPemeriksaan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariPemeriksaan1ActionPerformed(evt);
            }
        });
        BtnCariPemeriksaan1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCariPemeriksaan1KeyPressed(evt);
            }
        });
        FormInput.add(BtnCariPemeriksaan1);
        BtnCariPemeriksaan1.setBounds(330, 1570, 28, 23);

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
        BtnTambahMasalah.setBounds(370, 1570, 28, 23);

        Bahasa.setEditable(false);
        Bahasa.setFocusTraversalPolicyProvider(true);
        Bahasa.setName("Bahasa"); // NOI18N
        Bahasa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BahasaKeyPressed(evt);
            }
        });
        FormInput.add(Bahasa);
        Bahasa.setBounds(684, 420, 170, 23);

        jLabel76.setText("Bahasa yang digunakan sehari-hari :");
        jLabel76.setName("jLabel76"); // NOI18N
        FormInput.add(jLabel76);
        jLabel76.setBounds(450, 420, 230, 23);

        jLabel77.setText("Agama :");
        jLabel77.setName("jLabel77"); // NOI18N
        FormInput.add(jLabel77);
        jLabel77.setBounds(0, 530, 82, 23);

        Agama.setEditable(false);
        Agama.setFocusTraversalPolicyProvider(true);
        Agama.setName("Agama"); // NOI18N
        Agama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AgamaKeyPressed(evt);
            }
        });
        FormInput.add(Agama);
        Agama.setBounds(86, 530, 110, 23);

        jLabel78.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel78.setText("IX. PENGKAJIAN DOKTER");
        jLabel78.setName("jLabel78"); // NOI18N
        FormInput.add(jLabel78);
        jLabel78.setBounds(10, 1070, 380, 23);

        PanelWall1.setBackground(new java.awt.Color(29, 29, 29));
        PanelWall1.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/picture/newmata.png"))); // NOI18N
        PanelWall1.setBackgroundImageType(usu.widget.constan.BackgroundConstan.BACKGROUND_IMAGE_STRECT);
        PanelWall1.setPreferredSize(new java.awt.Dimension(200, 200));
        PanelWall1.setRound(false);
        PanelWall1.setWarna(new java.awt.Color(110, 110, 110));
        PanelWall1.setLayout(null);
        FormInput.add(PanelWall1);
        PanelWall1.setBounds(30, 1100, 130, 80);

        label15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label15.setText("Mata Kanan (OD)");
        label15.setName("label15"); // NOI18N
        label15.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label15);
        label15.setBounds(190, 1090, 170, 23);

        label16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label16.setText("VISUS");
        label16.setName("label16"); // NOI18N
        label16.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label16);
        label16.setBounds(360, 1120, 100, 23);

        VisusKanan.setFocusTraversalPolicyProvider(true);
        VisusKanan.setName("VisusKanan"); // NOI18N
        VisusKanan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                VisusKananKeyPressed(evt);
            }
        });
        FormInput.add(VisusKanan);
        VisusKanan.setBounds(190, 1120, 170, 23);

        VisusKiri.setFocusTraversalPolicyProvider(true);
        VisusKiri.setName("VisusKiri"); // NOI18N
        VisusKiri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                VisusKiriKeyPressed(evt);
            }
        });
        FormInput.add(VisusKiri);
        VisusKiri.setBounds(460, 1120, 170, 23);

        label17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label17.setText("Mata Kiri (OS) ");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label17);
        label17.setBounds(460, 1090, 170, 23);

        PanelWall2.setBackground(new java.awt.Color(29, 29, 29));
        PanelWall2.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/picture/newmata.png"))); // NOI18N
        PanelWall2.setBackgroundImageType(usu.widget.constan.BackgroundConstan.BACKGROUND_IMAGE_STRECT);
        PanelWall2.setPreferredSize(new java.awt.Dimension(200, 200));
        PanelWall2.setRound(false);
        PanelWall2.setWarna(new java.awt.Color(110, 110, 110));
        PanelWall2.setLayout(null);
        FormInput.add(PanelWall2);
        PanelWall2.setBounds(660, 1100, 130, 80);

        label18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label18.setText("REFRAKSI");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label18);
        label18.setBounds(360, 1145, 100, 23);

        RefraksiKanan.setFocusTraversalPolicyProvider(true);
        RefraksiKanan.setName("RefraksiKanan"); // NOI18N
        RefraksiKanan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RefraksiKananKeyPressed(evt);
            }
        });
        FormInput.add(RefraksiKanan);
        RefraksiKanan.setBounds(190, 1145, 170, 23);

        RefraksiKiri.setFocusTraversalPolicyProvider(true);
        RefraksiKiri.setName("RefraksiKiri"); // NOI18N
        RefraksiKiri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RefraksiKiriKeyPressed(evt);
            }
        });
        FormInput.add(RefraksiKiri);
        RefraksiKiri.setBounds(460, 1145, 170, 23);

        TioKanan.setFocusTraversalPolicyProvider(true);
        TioKanan.setName("TioKanan"); // NOI18N
        TioKanan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TioKananKeyPressed(evt);
            }
        });
        FormInput.add(TioKanan);
        TioKanan.setBounds(190, 1170, 170, 23);

        label19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label19.setText("TIO");
        label19.setName("label19"); // NOI18N
        label19.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label19);
        label19.setBounds(360, 1170, 100, 23);

        TioKiri.setFocusTraversalPolicyProvider(true);
        TioKiri.setName("TioKiri"); // NOI18N
        TioKiri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TioKiriKeyPressed(evt);
            }
        });
        FormInput.add(TioKiri);
        TioKiri.setBounds(460, 1170, 170, 23);

        PalberaKanan.setFocusTraversalPolicyProvider(true);
        PalberaKanan.setName("PalberaKanan"); // NOI18N
        PalberaKanan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PalberaKananKeyPressed(evt);
            }
        });
        FormInput.add(PalberaKanan);
        PalberaKanan.setBounds(190, 1195, 170, 23);

        label20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label20.setText("PALBERA");
        label20.setName("label20"); // NOI18N
        label20.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label20);
        label20.setBounds(360, 1195, 100, 23);

        PalberaKiri.setFocusTraversalPolicyProvider(true);
        PalberaKiri.setName("PalberaKiri"); // NOI18N
        PalberaKiri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PalberaKiriKeyPressed(evt);
            }
        });
        FormInput.add(PalberaKiri);
        PalberaKiri.setBounds(460, 1195, 170, 23);

        KonjungtivaKanan.setFocusTraversalPolicyProvider(true);
        KonjungtivaKanan.setName("KonjungtivaKanan"); // NOI18N
        KonjungtivaKanan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KonjungtivaKananKeyPressed(evt);
            }
        });
        FormInput.add(KonjungtivaKanan);
        KonjungtivaKanan.setBounds(190, 1220, 170, 23);

        label21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label21.setText("KONJUNGTIVA");
        label21.setName("label21"); // NOI18N
        label21.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label21);
        label21.setBounds(360, 1220, 100, 23);

        KonjungtivaKiri.setFocusTraversalPolicyProvider(true);
        KonjungtivaKiri.setName("KonjungtivaKiri"); // NOI18N
        KonjungtivaKiri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KonjungtivaKiriKeyPressed(evt);
            }
        });
        FormInput.add(KonjungtivaKiri);
        KonjungtivaKiri.setBounds(460, 1220, 170, 23);

        SkleraKanan.setFocusTraversalPolicyProvider(true);
        SkleraKanan.setName("SkleraKanan"); // NOI18N
        SkleraKanan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkleraKananKeyPressed(evt);
            }
        });
        FormInput.add(SkleraKanan);
        SkleraKanan.setBounds(190, 1245, 170, 23);

        label22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label22.setText("SKLERA");
        label22.setName("label22"); // NOI18N
        label22.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label22);
        label22.setBounds(360, 1245, 100, 23);

        SkleraKiri.setFocusTraversalPolicyProvider(true);
        SkleraKiri.setName("SkleraKiri"); // NOI18N
        SkleraKiri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkleraKiriKeyPressed(evt);
            }
        });
        FormInput.add(SkleraKiri);
        SkleraKiri.setBounds(460, 1245, 170, 23);

        KorneaKanan.setFocusTraversalPolicyProvider(true);
        KorneaKanan.setName("KorneaKanan"); // NOI18N
        KorneaKanan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KorneaKananKeyPressed(evt);
            }
        });
        FormInput.add(KorneaKanan);
        KorneaKanan.setBounds(190, 1270, 170, 23);

        label23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label23.setText("KORNEA");
        label23.setName("label23"); // NOI18N
        label23.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label23);
        label23.setBounds(360, 1270, 100, 23);

        KorneaKiri.setFocusTraversalPolicyProvider(true);
        KorneaKiri.setName("KorneaKiri"); // NOI18N
        KorneaKiri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KorneaKiriKeyPressed(evt);
            }
        });
        FormInput.add(KorneaKiri);
        KorneaKiri.setBounds(460, 1270, 170, 23);

        BMDKanan.setFocusTraversalPolicyProvider(true);
        BMDKanan.setName("BMDKanan"); // NOI18N
        BMDKanan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BMDKananKeyPressed(evt);
            }
        });
        FormInput.add(BMDKanan);
        BMDKanan.setBounds(190, 1295, 170, 23);

        label24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label24.setText("BMD");
        label24.setName("label24"); // NOI18N
        label24.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label24);
        label24.setBounds(360, 1295, 100, 23);

        BMDKiri.setFocusTraversalPolicyProvider(true);
        BMDKiri.setName("BMDKiri"); // NOI18N
        BMDKiri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BMDKiriKeyPressed(evt);
            }
        });
        FormInput.add(BMDKiri);
        BMDKiri.setBounds(460, 1295, 170, 23);

        IrisKanan.setFocusTraversalPolicyProvider(true);
        IrisKanan.setName("IrisKanan"); // NOI18N
        IrisKanan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                IrisKananKeyPressed(evt);
            }
        });
        FormInput.add(IrisKanan);
        IrisKanan.setBounds(190, 1320, 170, 23);

        label25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label25.setText("IRIS");
        label25.setName("label25"); // NOI18N
        label25.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label25);
        label25.setBounds(360, 1320, 100, 23);

        IrisKiri.setFocusTraversalPolicyProvider(true);
        IrisKiri.setName("IrisKiri"); // NOI18N
        IrisKiri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                IrisKiriKeyPressed(evt);
            }
        });
        FormInput.add(IrisKiri);
        IrisKiri.setBounds(460, 1320, 170, 23);

        PupilKanan.setFocusTraversalPolicyProvider(true);
        PupilKanan.setName("PupilKanan"); // NOI18N
        PupilKanan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PupilKananKeyPressed(evt);
            }
        });
        FormInput.add(PupilKanan);
        PupilKanan.setBounds(190, 1345, 170, 23);

        label26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label26.setText("PUPIL");
        label26.setName("label26"); // NOI18N
        label26.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label26);
        label26.setBounds(360, 1345, 100, 23);

        PupilKiri.setFocusTraversalPolicyProvider(true);
        PupilKiri.setName("PupilKiri"); // NOI18N
        PupilKiri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PupilKiriKeyPressed(evt);
            }
        });
        FormInput.add(PupilKiri);
        PupilKiri.setBounds(460, 1345, 170, 23);

        LensaKanan.setFocusTraversalPolicyProvider(true);
        LensaKanan.setName("LensaKanan"); // NOI18N
        LensaKanan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LensaKananKeyPressed(evt);
            }
        });
        FormInput.add(LensaKanan);
        LensaKanan.setBounds(190, 1370, 170, 23);

        LensaKiri.setFocusTraversalPolicyProvider(true);
        LensaKiri.setName("LensaKiri"); // NOI18N
        LensaKiri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LensaKiriKeyPressed(evt);
            }
        });
        FormInput.add(LensaKiri);
        LensaKiri.setBounds(460, 1370, 170, 23);

        label27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label27.setText("LENSA");
        label27.setName("label27"); // NOI18N
        label27.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label27);
        label27.setBounds(360, 1370, 100, 23);

        jLabel90.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel90.setText("Oftalmoskopi Kanan (OD) :");
        jLabel90.setName("jLabel90"); // NOI18N
        FormInput.add(jLabel90);
        jLabel90.setBounds(450, 1400, 170, 23);

        ScorllPane5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        ScorllPane5.setName("ScorllPane5"); // NOI18N

        OftalmoskopiKanan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        OftalmoskopiKanan.setColumns(20);
        OftalmoskopiKanan.setRows(5);
        OftalmoskopiKanan.setName("OftalmoskopiKanan"); // NOI18N
        OftalmoskopiKanan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                OftalmoskopiKananKeyPressed(evt);
            }
        });
        ScorllPane5.setViewportView(OftalmoskopiKanan);

        FormInput.add(ScorllPane5);
        ScorllPane5.setBounds(450, 1420, 190, 70);

        jLabel91.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel91.setText("Oftalmoskopi Kiri (OS) :");
        jLabel91.setName("jLabel91"); // NOI18N
        FormInput.add(jLabel91);
        jLabel91.setBounds(660, 1400, 170, 23);

        ScrollPane6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        ScrollPane6.setName("ScrollPane6"); // NOI18N

        OftalmoskopiKiri.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        OftalmoskopiKiri.setColumns(20);
        OftalmoskopiKiri.setRows(5);
        OftalmoskopiKiri.setName("OftalmoskopiKiri"); // NOI18N
        OftalmoskopiKiri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                OftalmoskopiKiriKeyPressed(evt);
            }
        });
        ScrollPane6.setViewportView(OftalmoskopiKiri);

        FormInput.add(ScrollPane6);
        ScrollPane6.setBounds(660, 1420, 190, 70);

        jLabel33.setText("Riwayat Penyakit Sekarang :");
        jLabel33.setName("jLabel33"); // NOI18N
        FormInput.add(jLabel33);
        jLabel33.setBounds(0, 270, 175, 23);

        scrollPane9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane9.setName("scrollPane9"); // NOI18N

        RPS.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        RPS.setColumns(20);
        RPS.setRows(5);
        RPS.setName("RPS"); // NOI18N
        RPS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RPSKeyPressed(evt);
            }
        });
        scrollPane9.setViewportView(RPS);

        FormInput.add(scrollPane9);
        scrollPane9.setBounds(179, 270, 260, 32);

        jLabel98.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel98.setText("3. Apakah terdapat salah satu dari kondisi berikut ?");
        jLabel98.setName("jLabel98"); // NOI18N
        FormInput.add(jLabel98);
        jLabel98.setBounds(40, 770, 460, 23);

        jLabel99.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel99.setText("4. Apakah terdapat penyakit / keadaan yang mengakibatkan pasien berisiko mengalami malnutrisi ?");
        jLabel99.setName("jLabel99"); // NOI18N
        FormInput.add(jLabel99);
        jLabel99.setBounds(40, 800, 550, 23);

        SG3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        SG3.setName("SG3"); // NOI18N
        SG3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SG3ItemStateChanged(evt);
            }
        });
        SG3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SG3KeyPressed(evt);
            }
        });
        FormInput.add(SG3);
        SG3.setBounds(540, 770, 160, 23);

        jLabel100.setText("Nilai :");
        jLabel100.setName("jLabel100"); // NOI18N
        FormInput.add(jLabel100);
        jLabel100.setBounds(695, 770, 75, 23);

        Nilai3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1" }));
        Nilai3.setName("Nilai3"); // NOI18N
        Nilai3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                Nilai3ItemStateChanged(evt);
            }
        });
        Nilai3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Nilai3KeyPressed(evt);
            }
        });
        FormInput.add(Nilai3);
        Nilai3.setBounds(774, 770, 80, 23);

        SG4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        SG4.setName("SG4"); // NOI18N
        SG4.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SG4ItemStateChanged(evt);
            }
        });
        SG4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SG4KeyPressed(evt);
            }
        });
        FormInput.add(SG4);
        SG4.setBounds(540, 800, 160, 23);

        jLabel101.setText("Nilai :");
        jLabel101.setName("jLabel101"); // NOI18N
        FormInput.add(jLabel101);
        jLabel101.setBounds(695, 800, 75, 23);

        Nilai4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1" }));
        Nilai4.setName("Nilai4"); // NOI18N
        Nilai4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Nilai4KeyPressed(evt);
            }
        });
        FormInput.add(Nilai4);
        Nilai4.setBounds(774, 800, 80, 23);

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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "27-01-2026" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "27-01-2026" }));
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

        ChkAccor.setBackground(new java.awt.Color(255, 250, 250));
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

        Scroll7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 254)));
        Scroll7.setName("Scroll7"); // NOI18N
        Scroll7.setOpaque(true);

        tbMasalahDetailMasalah.setName("tbMasalahDetailMasalah"); // NOI18N
        Scroll7.setViewportView(tbMasalahDetailMasalah);

        FormMasalahRencana.add(Scroll7);

        scrollPane6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 254)), "Rencana Keperawatan :", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        scrollPane6.setName("scrollPane6"); // NOI18N

        DetailRencana.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 5, 1, 1));
        DetailRencana.setColumns(20);
        DetailRencana.setRows(5);
        DetailRencana.setName("DetailRencana"); // NOI18N
        DetailRencana.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DetailRencanaKeyPressed(evt);
            }
        });
        scrollPane6.setViewportView(DetailRencana);

        FormMasalahRencana.add(scrollPane6);

        PanelAccor.add(FormMasalahRencana, java.awt.BorderLayout.CENTER);

        internalFrame3.add(PanelAccor, java.awt.BorderLayout.EAST);

        TabRawat.addTab("Data Pengkajian", internalFrame3);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isRawat();
        }else{            
            Valid.pindah(evt,TCari,BtnPetugas);
        }
}//GEN-LAST:event_TNoRwKeyPressed

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
        }else if(KeluhanUtama.getText().trim().equals("")){
            Valid.textKosong(KeluhanUtama,"Keluhan Utama");
        }else if(RPD.getText().trim().equals("")){
            Valid.textKosong(RPD,"Riwayat Penyakit Dahulu");
        }else if(RPS.getText().trim().equals("")){
            Valid.textKosong(RPS,"Riwayat Penyakit Sekarang");
        }else if(RPK.getText().trim().equals("")){
            Valid.textKosong(RPK,"Riwayat Penyakit Keluarga");
        }else if(RPO.getText().trim().equals("")){
            Valid.textKosong(RPO,"Riwayat Pengobatan");
        }else if(Alergi.getText().trim().equals("")){
            Valid.textKosong(Alergi,"Alergi");
        }else if(TotalHasil.getText().trim().equals("")){
            Valid.textKosong(TotalHasil,"Total Hasil");
        }else if(Lokasi.getText().trim().equals("")){
            Valid.textKosong(Lokasi,"Lokasi");
        }else if(Rencana.getText().trim().equals("")){
            Valid.textKosong(Rencana,"Rencana");
        }else if(NmPetugas.getText().trim().equals("")){
            Valid.textKosong(BtnPetugas,"Petugas");
        }else{
            if(Sequel.menyimpantf("penilaian_awal_keperawatan_mata","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat",86,new String[]{
                    TNoRw.getText(),Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),Informasi.getSelectedItem().toString(),TD.getText(),Nadi.getText(),RR.getText(),
                    Suhu.getText(),GCS.getText(),BB.getText(),TB.getText(),BMI.getText(),KeluhanUtama.getText(),RPD.getText(),RPS.getText(),RPK.getText(),RPO.getText(),Alergi.getText(),AlatBantu.getSelectedItem().toString(),KetBantu.getText(), 
                    Prothesa.getSelectedItem().toString(),KetProthesa.getText(),ADL.getSelectedItem().toString(),StatusPsiko.getSelectedItem().toString(),KetPsiko.getText(),HubunganKeluarga.getSelectedItem().toString(), 
                    TinggalDengan.getSelectedItem().toString(),KetTinggal.getText(),Ekonomi.getSelectedItem().toString(),StatusBudaya.getSelectedItem().toString(),KetBudaya.getText(),Edukasi.getSelectedItem().toString(), 
                    KetEdukasi.getText(),ATS.getSelectedItem().toString(),BJM.getSelectedItem().toString(),MSA.getSelectedItem().toString(),Hasil.getSelectedItem().toString(),Lapor.getSelectedItem().toString(),KetLapor.getText(), 
                    SG1.getSelectedItem().toString(),Nilai1.getSelectedItem().toString(),SG2.getSelectedItem().toString(),Nilai2.getSelectedItem().toString(),SG3.getSelectedItem().toString(),Nilai3.getSelectedItem().toString(),
                    SG4.getSelectedItem().toString(),Nilai4.getSelectedItem().toString(),TotalHasil.getText(),Nyeri.getSelectedItem().toString(),
                    Provokes.getSelectedItem().toString(),KetProvokes.getText(),Quality.getSelectedItem().toString(),KetQuality.getText(),Lokasi.getText(),Menyebar.getSelectedItem().toString(),SkalaNyeri.getSelectedItem().toString(),
                    Durasi.getText(),NyeriHilang.getSelectedItem().toString(),KetNyeri.getText(),PadaDokter.getSelectedItem().toString(),KetDokter.getText(),VisusKanan.getText(),VisusKiri.getText(),RefraksiKanan.getText(),RefraksiKiri.getText(),
                    TioKanan.getText(),TioKiri.getText(),PalberaKanan.getText(),PalberaKiri.getText(),KonjungtivaKanan.getText(),KonjungtivaKiri.getText(),SkleraKanan.getText(),SkleraKiri.getText(),KorneaKanan.getText(),KorneaKiri.getText(),
                    BMDKanan.getText(),BMDKiri.getText(),IrisKanan.getText(),IrisKiri.getText(),PupilKanan.getText(),PupilKiri.getText(),LensaKanan.getText(),LensaKiri.getText(),OftalmoskopiKanan.getText(),OftalmoskopiKiri.getText(),
                    Rencana.getText(),KdPetugas.getText()
                })==true){
                    for (i = 0; i < tbMasalahKeperawatan.getRowCount(); i++) {
                        if(tbMasalahKeperawatan.getValueAt(i,0).toString().equals("true")){
                            Sequel.menyimpan2("penilaian_awal_keperawatan_mata_masalah","?,?",2,new String[]{TNoRw.getText(),tbMasalahKeperawatan.getValueAt(i,1).toString()});
                        }
                    }
                    emptTeks();
            }
        }
    
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,Rencana,BtnBatal);
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
            if(Sequel.queryu2tf("delete from penilaian_awal_keperawatan_mata where no_rawat=?",1,new String[]{
                tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
            })==true){
                TNoRM1.setText("");
                TPasien1.setText("");
                Sequel.meghapus("penilaian_awal_keperawatan_mata_masalah","no_rawat",tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
                Valid.tabelKosong(tabModeDetailMasalah);
                ChkAccor.setSelected(false);
                isMenu();
                tabMode.removeRow(tbObat.getSelectedRow());
                LCount.setText(""+tabMode.getRowCount());
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
        }else if(BMI.getText().trim().equals("")){
            Valid.textKosong(BMI,"BMI(Kg/m2)");
        }else if(KeluhanUtama.getText().trim().equals("")){
            Valid.textKosong(KeluhanUtama,"Keluhan Utama");
        }else if(RPD.getText().trim().equals("")){
            Valid.textKosong(RPD,"Riwayat Penyakit Dahulu");
        }else if(RPS.getText().trim().equals("")){
            Valid.textKosong(RPS,"Riwayat Penyakit Sekarang");
        }else if(RPK.getText().trim().equals("")){
            Valid.textKosong(RPK,"Riwayat Penyakit Keluarga");
        }else if(RPO.getText().trim().equals("")){
            Valid.textKosong(RPO,"Riwayat Pengobatan");
        }else if(Alergi.getText().trim().equals("")){
            Valid.textKosong(Alergi,"Alergi");
        }else if(TotalHasil.getText().trim().equals("")){
            Valid.textKosong(TotalHasil,"Total Hasil");
        }else if(Lokasi.getText().trim().equals("")){
            Valid.textKosong(Lokasi,"Lokasi");
        }else if(Rencana.getText().trim().equals("")){
            Valid.textKosong(Rencana,"Rencana");
        }else if(NmPetugas.getText().trim().equals("")){
            Valid.textKosong(BtnPetugas,"Petugas");
        }else{
            if(tbObat.getSelectedRow()>-1){
                if(Sequel.mengedittf("penilaian_awal_keperawatan_mata","no_rawat=?","no_rawat=?,tanggal=?,informasi=?,td=?,nadi=?,rr=?,suhu=?,gcs=?,bb=?,tb=?,bmi=?,keluhan_utama=?,rpd=?,rps=?,rpk=?,rpo=?,alergi=?,alat_bantu=?,ket_bantu=?,prothesa=?,ket_pro=?,adl=?,status_psiko=?,ket_psiko=?,hub_keluarga=?,tinggal_dengan=?,ket_tinggal=?,ekonomi=?,budaya=?,"+
                        "ket_budaya=?,edukasi=?,ket_edukasi=?,berjalan_a=?,berjalan_b=?,berjalan_c=?,hasil=?,lapor=?,ket_lapor=?,sg1=?,nilai1=?,sg2=?,nilai2=?,sg3=?,nilai3=?,sg4=?,nilai4=?,total_hasil=?,nyeri=?,provokes=?,ket_provokes=?,quality=?,ket_quality=?,lokasi=?,menyebar=?,skala_nyeri=?,durasi=?,nyeri_hilang=?,ket_nyeri=?,pada_dokter=?,ket_dokter=?,"+
                        "visuskanan=?,visuskiri=?,refraksikanan=?,refraksikiri=?,tiokanan=?,tiokiri=?,palberakanan=?,palberakiri=?,konjungtivakanan=?,konjungtivakiri=?,sklerakanan=?,sklerakiri=?,korneakanan=?,korneakiri=?,bmdkanan=?,bmdkiri=?,iriskanan=?,iriskiri=?,pupilkanan=?,pupilkiri=?,lensakanan=?,lensakiri=?,oftalmoskopikanan=?,oftalmoskopikiri=?,rencana=?,nip=?",87,new String[]{
                        TNoRw.getText(),Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),Informasi.getSelectedItem().toString(),TD.getText(),Nadi.getText(),RR.getText(),
                        Suhu.getText(),GCS.getText(),BB.getText(),TB.getText(),BMI.getText(),KeluhanUtama.getText(),RPD.getText(),RPS.getText(),RPK.getText(),RPO.getText(),Alergi.getText(),AlatBantu.getSelectedItem().toString(),KetBantu.getText(), 
                        Prothesa.getSelectedItem().toString(),KetProthesa.getText(),ADL.getSelectedItem().toString(),StatusPsiko.getSelectedItem().toString(),KetPsiko.getText(),HubunganKeluarga.getSelectedItem().toString(), 
                        TinggalDengan.getSelectedItem().toString(),KetTinggal.getText(),Ekonomi.getSelectedItem().toString(),StatusBudaya.getSelectedItem().toString(),KetBudaya.getText(),Edukasi.getSelectedItem().toString(), 
                        KetEdukasi.getText(),ATS.getSelectedItem().toString(),BJM.getSelectedItem().toString(),MSA.getSelectedItem().toString(),Hasil.getSelectedItem().toString(),Lapor.getSelectedItem().toString(),KetLapor.getText(), 
                        SG1.getSelectedItem().toString(),Nilai1.getSelectedItem().toString(),SG2.getSelectedItem().toString(),Nilai2.getSelectedItem().toString(),SG3.getSelectedItem().toString(),Nilai3.getSelectedItem().toString(),SG4.getSelectedItem().toString(),Nilai4.getSelectedItem().toString(),TotalHasil.getText(),Nyeri.getSelectedItem().toString(),
                        Provokes.getSelectedItem().toString(),KetProvokes.getText(),Quality.getSelectedItem().toString(),KetQuality.getText(),Lokasi.getText(),Menyebar.getSelectedItem().toString(),SkalaNyeri.getSelectedItem().toString(),
                        Durasi.getText(),NyeriHilang.getSelectedItem().toString(),KetNyeri.getText(),PadaDokter.getSelectedItem().toString(),KetDokter.getText(),VisusKanan.getText(),VisusKiri.getText(),RefraksiKanan.getText(),RefraksiKiri.getText(),
                        TioKanan.getText(),TioKiri.getText(),PalberaKanan.getText(),PalberaKiri.getText(),KonjungtivaKanan.getText(),KonjungtivaKiri.getText(),SkleraKanan.getText(),SkleraKiri.getText(),KorneaKanan.getText(),KorneaKiri.getText(),
                        BMDKanan.getText(),BMDKiri.getText(),IrisKanan.getText(),IrisKiri.getText(),PupilKanan.getText(),PupilKiri.getText(),LensaKanan.getText(),LensaKiri.getText(),OftalmoskopiKanan.getText(),OftalmoskopiKiri.getText(),Rencana.getText(),KdPetugas.getText(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
                     })==true){
                        Sequel.meghapus("penilaian_awal_keperawatan_mata_masalah","no_rawat",tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
                        for (i = 0; i < tbMasalahKeperawatan.getRowCount(); i++) {
                            if(tbMasalahKeperawatan.getValueAt(i,0).toString().equals("true")){
                                Sequel.menyimpan2("penilaian_awal_keperawatan_mata_masalah","?,?",2,new String[]{TNoRw.getText(),tbMasalahKeperawatan.getValueAt(i,1).toString()});
                            }
                        }
                        getMasalah();
                        runBackground(() ->tampil());
                        emptTeks();
                        TabRawat.setSelectedIndex(1);
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
                if(TCari.getText().equals("")){
                    ps=koneksi.prepareStatement(
                            "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,pasien.agama,bahasa_pasien.nama_bahasa,cacat_fisik.nama_cacat,penilaian_awal_keperawatan_mata.tanggal,"+
                            "penilaian_awal_keperawatan_mata.informasi,penilaian_awal_keperawatan_mata.td,penilaian_awal_keperawatan_mata.nadi,penilaian_awal_keperawatan_mata.rr,penilaian_awal_keperawatan_mata.suhu,penilaian_awal_keperawatan_mata.bb,penilaian_awal_keperawatan_mata.tb,"+
                            "penilaian_awal_keperawatan_mata.nadi,penilaian_awal_keperawatan_mata.rr,penilaian_awal_keperawatan_mata.suhu,penilaian_awal_keperawatan_mata.gcs,penilaian_awal_keperawatan_mata.bb,penilaian_awal_keperawatan_mata.tb,penilaian_awal_keperawatan_mata.bmi,penilaian_awal_keperawatan_mata.keluhan_utama,"+
                            "penilaian_awal_keperawatan_mata.rpd,penilaian_awal_keperawatan_mata.rps,penilaian_awal_keperawatan_mata.rpk,penilaian_awal_keperawatan_mata.rpo,penilaian_awal_keperawatan_mata.alergi,penilaian_awal_keperawatan_mata.alat_bantu,penilaian_awal_keperawatan_mata.ket_bantu,penilaian_awal_keperawatan_mata.prothesa,"+
                            "penilaian_awal_keperawatan_mata.ket_pro,penilaian_awal_keperawatan_mata.adl,penilaian_awal_keperawatan_mata.status_psiko,penilaian_awal_keperawatan_mata.ket_psiko,penilaian_awal_keperawatan_mata.hub_keluarga,penilaian_awal_keperawatan_mata.tinggal_dengan,"+
                            "penilaian_awal_keperawatan_mata.ket_tinggal,penilaian_awal_keperawatan_mata.ekonomi,penilaian_awal_keperawatan_mata.edukasi,penilaian_awal_keperawatan_mata.ket_edukasi,penilaian_awal_keperawatan_mata.berjalan_a,penilaian_awal_keperawatan_mata.berjalan_b,"+
                            "penilaian_awal_keperawatan_mata.berjalan_c,penilaian_awal_keperawatan_mata.hasil,penilaian_awal_keperawatan_mata.lapor,penilaian_awal_keperawatan_mata.ket_lapor,penilaian_awal_keperawatan_mata.sg1,penilaian_awal_keperawatan_mata.nilai1,penilaian_awal_keperawatan_mata.sg2,penilaian_awal_keperawatan_mata.nilai2,penilaian_awal_keperawatan_mata.sg3,penilaian_awal_keperawatan_mata.nilai3,penilaian_awal_keperawatan_mata.sg4,penilaian_awal_keperawatan_mata.nilai4,"+
                            "penilaian_awal_keperawatan_mata.total_hasil,penilaian_awal_keperawatan_mata.nyeri,penilaian_awal_keperawatan_mata.provokes,penilaian_awal_keperawatan_mata.ket_provokes,penilaian_awal_keperawatan_mata.quality,penilaian_awal_keperawatan_mata.ket_quality,penilaian_awal_keperawatan_mata.lokasi,penilaian_awal_keperawatan_mata.menyebar,"+
                            "penilaian_awal_keperawatan_mata.skala_nyeri,penilaian_awal_keperawatan_mata.durasi,penilaian_awal_keperawatan_mata.nyeri_hilang,penilaian_awal_keperawatan_mata.ket_nyeri,penilaian_awal_keperawatan_mata.pada_dokter,penilaian_awal_keperawatan_mata.ket_dokter,penilaian_awal_keperawatan_mata.rencana,penilaian_awal_keperawatan_mata.visuskanan,penilaian_awal_keperawatan_mata.visuskiri,penilaian_awal_keperawatan_mata.refraksikanan,penilaian_awal_keperawatan_mata.refraksikiri,"+
                            "penilaian_awal_keperawatan_mata.tiokanan,penilaian_awal_keperawatan_mata.tiokiri,penilaian_awal_keperawatan_mata.palberakanan,penilaian_awal_keperawatan_mata.palberakiri,penilaian_awal_keperawatan_mata.konjungtivakanan,penilaian_awal_keperawatan_mata.konjungtivakiri,penilaian_awal_keperawatan_mata.sklerakanan,penilaian_awal_keperawatan_mata.sklerakiri,penilaian_awal_keperawatan_mata.korneakanan,penilaian_awal_keperawatan_mata.korneakiri,penilaian_awal_keperawatan_mata.bmdkanan,penilaian_awal_keperawatan_mata.bmdkiri,"+
                            "penilaian_awal_keperawatan_mata.iriskanan,penilaian_awal_keperawatan_mata.iriskiri,penilaian_awal_keperawatan_mata.pupilkanan,penilaian_awal_keperawatan_mata.pupilkiri,penilaian_awal_keperawatan_mata.lensakanan,penilaian_awal_keperawatan_mata.lensakiri,penilaian_awal_keperawatan_mata.oftalmoskopikanan,penilaian_awal_keperawatan_mata.oftalmoskopikiri, "+
                            "penilaian_awal_keperawatan_mata.nip,petugas.nama,penilaian_awal_keperawatan_mata.budaya,penilaian_awal_keperawatan_mata.ket_budaya "+
                            "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                            "inner join penilaian_awal_keperawatan_mata on reg_periksa.no_rawat=penilaian_awal_keperawatan_mata.no_rawat "+
                            "inner join petugas on penilaian_awal_keperawatan_mata.nip=petugas.nip "+
                            "inner join bahasa_pasien on bahasa_pasien.id=pasien.bahasa_pasien "+
                            "inner join cacat_fisik on cacat_fisik.id=pasien.cacat_fisik where "+
                            "penilaian_awal_keperawatan_mata.tanggal between ? and ? order by penilaian_awal_keperawatan_mata.tanggal");
                }else{
                    ps=koneksi.prepareStatement(
                            "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,pasien.agama,bahasa_pasien.nama_bahasa,cacat_fisik.nama_cacat,penilaian_awal_keperawatan_mata.tanggal,"+
                            "penilaian_awal_keperawatan_mata.informasi,penilaian_awal_keperawatan_mata.td,penilaian_awal_keperawatan_mata.nadi,penilaian_awal_keperawatan_mata.rr,penilaian_awal_keperawatan_mata.suhu,penilaian_awal_keperawatan_mata.bb,penilaian_awal_keperawatan_mata.tb,"+
                            "penilaian_awal_keperawatan_mata.nadi,penilaian_awal_keperawatan_mata.rr,penilaian_awal_keperawatan_mata.suhu,penilaian_awal_keperawatan_mata.gcs,penilaian_awal_keperawatan_mata.bb,penilaian_awal_keperawatan_mata.tb,penilaian_awal_keperawatan_mata.bmi,penilaian_awal_keperawatan_mata.keluhan_utama,"+
                            "penilaian_awal_keperawatan_mata.rpd,penilaian_awal_keperawatan_mata.rps,penilaian_awal_keperawatan_mata.rpk,penilaian_awal_keperawatan_mata.rpo,penilaian_awal_keperawatan_mata.alergi,penilaian_awal_keperawatan_mata.alat_bantu,penilaian_awal_keperawatan_mata.ket_bantu,penilaian_awal_keperawatan_mata.prothesa,"+
                            "penilaian_awal_keperawatan_mata.ket_pro,penilaian_awal_keperawatan_mata.adl,penilaian_awal_keperawatan_mata.status_psiko,penilaian_awal_keperawatan_mata.ket_psiko,penilaian_awal_keperawatan_mata.hub_keluarga,penilaian_awal_keperawatan_mata.tinggal_dengan,"+
                            "penilaian_awal_keperawatan_mata.ket_tinggal,penilaian_awal_keperawatan_mata.ekonomi,penilaian_awal_keperawatan_mata.edukasi,penilaian_awal_keperawatan_mata.ket_edukasi,penilaian_awal_keperawatan_mata.berjalan_a,penilaian_awal_keperawatan_mata.berjalan_b,"+
                            "penilaian_awal_keperawatan_mata.berjalan_c,penilaian_awal_keperawatan_mata.hasil,penilaian_awal_keperawatan_mata.lapor,penilaian_awal_keperawatan_mata.ket_lapor,penilaian_awal_keperawatan_mata.sg1,penilaian_awal_keperawatan_mata.nilai1,penilaian_awal_keperawatan_mata.sg2,penilaian_awal_keperawatan_mata.nilai2,penilaian_awal_keperawatan_mata.sg3,penilaian_awal_keperawatan_mata.nilai3,penilaian_awal_keperawatan_mata.sg4,penilaian_awal_keperawatan_mata.nilai4,"+
                            "penilaian_awal_keperawatan_mata.total_hasil,penilaian_awal_keperawatan_mata.nyeri,penilaian_awal_keperawatan_mata.provokes,penilaian_awal_keperawatan_mata.ket_provokes,penilaian_awal_keperawatan_mata.quality,penilaian_awal_keperawatan_mata.ket_quality,penilaian_awal_keperawatan_mata.lokasi,penilaian_awal_keperawatan_mata.menyebar,"+
                            "penilaian_awal_keperawatan_mata.skala_nyeri,penilaian_awal_keperawatan_mata.durasi,penilaian_awal_keperawatan_mata.nyeri_hilang,penilaian_awal_keperawatan_mata.ket_nyeri,penilaian_awal_keperawatan_mata.pada_dokter,penilaian_awal_keperawatan_mata.ket_dokter,penilaian_awal_keperawatan_mata.rencana,penilaian_awal_keperawatan_mata.visuskanan,penilaian_awal_keperawatan_mata.visuskiri,penilaian_awal_keperawatan_mata.refraksikanan,penilaian_awal_keperawatan_mata.refraksikiri,"+
                            "penilaian_awal_keperawatan_mata.tiokanan,penilaian_awal_keperawatan_mata.tiokiri,penilaian_awal_keperawatan_mata.palberakanan,penilaian_awal_keperawatan_mata.palberakiri,penilaian_awal_keperawatan_mata.konjungtivakanan,penilaian_awal_keperawatan_mata.konjungtivakiri,penilaian_awal_keperawatan_mata.sklerakanan,penilaian_awal_keperawatan_mata.sklerakiri,penilaian_awal_keperawatan_mata.korneakanan,penilaian_awal_keperawatan_mata.korneakiri,penilaian_awal_keperawatan_mata.bmdkanan,penilaian_awal_keperawatan_mata.bmdkiri,"+
                            "penilaian_awal_keperawatan_mata.iriskanan,penilaian_awal_keperawatan_mata.iriskiri,penilaian_awal_keperawatan_mata.pupilkanan,penilaian_awal_keperawatan_mata.pupilkiri,penilaian_awal_keperawatan_mata.lensakanan,penilaian_awal_keperawatan_mata.lensakiri,penilaian_awal_keperawatan_mata.oftalmoskopikanan,penilaian_awal_keperawatan_mata.oftalmoskopikiri, "+
                            "penilaian_awal_keperawatan_mata.nip,petugas.nama,penilaian_awal_keperawatan_mata.budaya,penilaian_awal_keperawatan_mata.ket_budaya "+
                            "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                            "inner join penilaian_awal_keperawatan_mata on reg_periksa.no_rawat=penilaian_awal_keperawatan_mata.no_rawat "+
                            "inner join petugas on penilaian_awal_keperawatan_mata.nip=petugas.nip "+
                            "inner join bahasa_pasien on bahasa_pasien.id=pasien.bahasa_pasien "+
                            "inner join cacat_fisik on cacat_fisik.id=pasien.cacat_fisik where "+
                            "penilaian_awal_keperawatan_mata.tanggal between ? and ? and reg_periksa.no_rawat like ? or "+
                            "penilaian_awal_keperawatan_mata.tanggal between ? and ? and pasien.no_rkm_medis like ? or "+
                            "penilaian_awal_keperawatan_mata.tanggal between ? and ? and pasien.nm_pasien like ? or "+
                            "penilaian_awal_keperawatan_mata.tanggal between ? and ? and penilaian_awal_keperawatan_mata.nip like ? or "+
                            "penilaian_awal_keperawatan_mata.tanggal between ? and ? and petugas.nama like ? order by penilaian_awal_keperawatan_mata.tanggal");
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
                    htmlContent = new StringBuilder();
                    htmlContent.append(                             
                        "<tr class='isi'>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center' width='9%'><b>PASIEN & PETUGAS</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center' width='5%'><b>I. KEADAAN UMUM</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center' width='5%'><b>II. STATUS NUTRISI</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center' width='13%'><b>III. RIWAYAT KESEHATAN</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center' width='8%'><b>IV. FUNGSIONAL</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center' width='16%'><b>V. RIWAYAT PSIKO-SOSIAL SPIRITUAL DAN BUDAYA</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center' width='16%'><b>VI. PENGKAJIAN RESIKO JATUH</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center' width='7%'><b>VII. SKRINING GIZI</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center' width='7%'><b>VIII. PENGKAJIAN TINGKAT NYERI</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center' width='8%'><b>IX. PENGKAJIAN DOKTER</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center' width='6%'><b>MASALAH & RENCANA KEPERAWATAN</b></td>"+
                        "</tr>"
                    );
                    while(rs.next()){
                        masalahkeperawatan="";
                        ps2=koneksi.prepareStatement(
                            "select master_masalah_keperawatan_mata.kode_masalah,master_masalah_keperawatan_mata.nama_masalah from master_masalah_keperawatan_mata "+
                            "inner join penilaian_awal_keperawatan_mata_masalah on penilaian_awal_keperawatan_mata_masalah.kode_masalah=master_masalah_keperawatan_mata.kode_masalah "+
                            "where penilaian_awal_keperawatan_mata_masalah.no_rawat=? order by kode_masalah");
                        try {
                            ps2.setString(1,rs.getString("no_rawat"));
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                masalahkeperawatan=rs2.getString("nama_masalah")+", "+masalahkeperawatan;
                            }
                        } catch (Exception e) {
                            System.out.println("Notif : "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(ps2!=null){
                                ps2.close();
                            }
                        }
                        htmlContent.append(
                            "<tr class='isi'>"+
                                "<td valign='top' cellpadding='0' cellspacing='0'>"+
                                    "<table width='100%' border='0' cellpadding='0' cellspacing='0'align='center'>"+
                                        "<tr class='isi2'>"+
                                            "<td width='32%' valign='top'>No.Rawat</td><td valign='top'>:&nbsp;</td><td width='67%' valign='top'>"+rs.getString("no_rawat")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='32%' valign='top'>No.R.M.</td><td valign='top'>:&nbsp;</td><td width='67%' valign='top'>"+rs.getString("no_rkm_medis")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='32%' valign='top'>Nama Pasien</td><td valign='top'>:&nbsp;</td><td width='67%' valign='top'>"+rs.getString("nm_pasien")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='32%' valign='top'>J.K.</td><td valign='top'>:&nbsp;</td><td width='67%' valign='top'>"+rs.getString("jk")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='32%' valign='top'>Agama</td><td valign='top'>:&nbsp;</td><td width='67%' valign='top'>"+rs.getString("agama")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='32%' valign='top'>Bahasa</td><td valign='top'>:&nbsp;</td><td width='67%' valign='top'>"+rs.getString("nama_bahasa")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='32%' valign='top'>Tgl.Lahir</td><td valign='top'>:&nbsp;</td><td width='67%' valign='top'>"+rs.getString("nama_cacat")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='32%' valign='top'>Cacat Fisik</td><td valign='top'>:&nbsp;</td><td width='67%' valign='top'>"+rs.getString("tgl_lahir")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='32%' valign='top'>Petugas</td><td valign='top'>:&nbsp;</td><td width='67%' valign='top'>"+rs.getString("nip")+" "+rs.getString("nama")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='32%' valign='top'>Tgl.Asuhan</td><td valign='top'>:&nbsp;</td><td width='67%' valign='top'>"+rs.getString("tanggal")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='32%' valign='top'>Informasi</td><td valign='top'>:&nbsp;</td><td width='67%' valign='top'>"+rs.getString("informasi")+"</td>"+
                                        "</tr>"+
                                    "</table>"+
                                "</td>"+
                                "<td valign='top' cellpadding='0' cellspacing='0'>"+
                                    "<table width='100%' border='0' cellpadding='0' cellspacing='0'align='center'>"+
                                        "<tr class='isi2'>"+
                                            "<td width='34%' valign='top'>TD</td><td valign='top'>:&nbsp;</td><td width='65%' valign='top'>"+rs.getString("td")+"mmHg</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='34%' valign='top'>Nadi</td><td valign='top'>:&nbsp;</td><td width='65%' valign='top'>"+rs.getString("nadi")+"x/menit</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='34%' valign='top'>RR</td><td valign='top'>:&nbsp;</td><td width='65%' valign='top'>"+rs.getString("rr")+"x/menit</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='34%' valign='top'>Suhu</td><td valign='top'>:&nbsp;</td><td width='65%' valign='top'>"+rs.getString("suhu")+"°C</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='34%' valign='top'>GCS</td><td valign='top'>:&nbsp;</td><td width='65%' valign='top'>"+rs.getString("gcs")+"</td>"+
                                        "</tr>"+
                                    "</table>"+
                                "</td>"+
                                "<td valign='top' cellpadding='0' cellspacing='0'>"+
                                    "<table width='100%' border='0' cellpadding='0' cellspacing='0'align='center'>"+
                                        "<tr class='isi2'>"+
                                            "<td width='34%' valign='top'>BB</td><td valign='top'>:&nbsp;</td><td width='65%' valign='top'>"+rs.getString("bb")+"Kg</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='34%' valign='top'>TB</td><td valign='top'>:&nbsp;</td><td width='65%' valign='top'>"+rs.getString("tb")+"cm</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='34%' valign='top'>BMI</td><td valign='top'>:&nbsp;</td><td width='65%' valign='top'>"+rs.getString("bmi")+"Kg/m²</td>"+
                                        "</tr>"+
                                    "</table>"+
                                "</td>"+
                                "<td valign='top' cellpadding='0' cellspacing='0'>"+
                                    "<table width='100%' border='0' cellpadding='0' cellspacing='0'align='center'>"+
                                        "<tr class='isi2'>"+
                                            "<td width='32%' valign='top'>Keluhan Utama</td><td valign='top'>:&nbsp;</td><td width='67%' valign='top'>"+rs.getString("keluhan_utama")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='32%' valign='top'>RPD</td><td valign='top'>:&nbsp;</td><td width='67%' valign='top'>"+rs.getString("rpd")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='32%' valign='top'>RPS</td><td valign='top'>:&nbsp;</td><td width='67%' valign='top'>"+rs.getString("rps")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='32%' valign='top'>RPK</td><td valign='top'>:&nbsp;</td><td width='67%' valign='top'>"+rs.getString("rpk")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='32%' valign='top'>RPO</td><td valign='top'>:&nbsp;</td><td width='67%' valign='top'>"+rs.getString("rpo")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='32%' valign='top'>Alergi</td><td valign='top'>:&nbsp;</td><td width='67%' valign='top'>"+rs.getString("alergi")+"</td>"+
                                        "</tr>"+
                                    "</table>"+
                                "</td>"+
                                "<td valign='top' cellpadding='0' cellspacing='0'>"+
                                    "<table width='100%' border='0' cellpadding='0' cellspacing='0'align='center'>"+
                                        "<tr class='isi2'>"+
                                            "<td width='44%' valign='top'>Alat Bantu</td><td valign='top'>:&nbsp;</td><td width='55%' valign='top'>"+rs.getString("alat_bantu")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='44%' valign='top'>Ket. Alat Bantu</td><td valign='top'>:&nbsp;</td><td width='55%' valign='top'>"+rs.getString("ket_bantu")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='44%' valign='top'>Prothesa</td><td valign='top'>:&nbsp;</td><td width='55%' valign='top'>"+rs.getString("prothesa")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='44%' valign='top'>Ket. Prothesa</td><td valign='top'>:&nbsp;</td><td width='55%' valign='top'>"+rs.getString("ket_pro")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='44%' valign='top'>ADL</td><td valign='top'>:&nbsp;</td><td width='55%' valign='top'>"+rs.getString("adl")+"</td>"+
                                        "</tr>"+
                                    "</table>"+
                                "</td>"+
                                "<td valign='top' cellpadding='0' cellspacing='0'>"+
                                    "<table width='100%' border='0' cellpadding='0' cellspacing='0'align='center'>"+
                                        "<tr class='isi2'>"+
                                            "<td width='58%' valign='top'>Status Psikologis</td><td valign='top'>:&nbsp;</td><td width='35%' valign='top'>"+rs.getString("status_psiko")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='58%' valign='top'>Ket. Psikologi</td><td valign='top'>:&nbsp;</td><td width='35%' valign='top'>"+rs.getString("ket_psiko")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='58%' valign='top'>Hubungan pasien dengan anggota keluarga</td><td valign='top'>:&nbsp;</td><td width='35%' valign='top'>"+rs.getString("hub_keluarga")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='58%' valign='top'>Tinggal dengan</td><td valign='top'>:&nbsp;</td><td width='35%' valign='top'>"+rs.getString("tinggal_dengan")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='58%' valign='top'>Ket. Tinggal</td><td valign='top'>:&nbsp;</td><td width='35%' valign='top'>"+rs.getString("ket_tinggal")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='58%' valign='top'>Ekonomi</td><td valign='top'>:&nbsp;</td><td width='35%' valign='top'>"+rs.getString("ekonomi")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='58%' valign='top'>Kepercayaan / Budaya / Nilai-nilai khusus yang perlu diperhatikan</td><td valign='top'>:&nbsp;</td><td width='35%' valign='top'>"+rs.getString("budaya")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='58%' valign='top'>Ket. Budaya</td><td valign='top'>:&nbsp;</td><td width='35%' valign='top'>"+rs.getString("ket_budaya")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='58%' valign='top'>Edukasi diberikan kepada </td><td valign='top'>:&nbsp;</td><td width='35%' valign='top'>"+rs.getString("edukasi")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='58%' valign='top'>Ket. Edukasi</td><td valign='top'>:&nbsp;</td><td width='35%' valign='top'>"+rs.getString("ket_edukasi")+"</td>"+
                                        "</tr>"+
                                    "</table>"+
                                "</td>"+
                                "<td valign='top' cellpadding='0' cellspacing='0'>"+
                                    "<table width='100%' border='0' cellpadding='0' cellspacing='0'align='center'>"+
                                        "<tr class='isi2'>"+
                                            "<td width='58%' valign='top'>Tidak seimbang/sempoyongan/limbung</td><td valign='top'>:&nbsp;</td><td width='35%' valign='top'>"+rs.getString("berjalan_a")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='58%' valign='top'>Jalan dengan menggunakan alat bantu (kruk, tripot, kursi roda, orang lain)</td><td valign='top'>:&nbsp;</td><td width='35%' valign='top'>"+rs.getString("berjalan_b")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='58%' valign='top'>Menopang saat akan duduk, tampak memegang pinggiran kursi atau meja/benda lain sebagai penopang</td><td valign='top'>:&nbsp;</td><td width='35%' valign='top'>"+rs.getString("berjalan_c")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='58%' valign='top'>Hasil</td><td valign='top'>:&nbsp;</td><td width='35%' valign='top'>"+rs.getString("hasil")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='58%' valign='top'>Dilaporan ke dokter?</td><td valign='top'>:&nbsp;</td><td width='35%' valign='top'>"+rs.getString("lapor")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='58%' valign='top'>Jam Lapor</td><td valign='top'>:&nbsp;</td><td width='35%' valign='top'>"+rs.getString("ket_lapor")+"</td>"+
                                        "</tr>"+
                                    "</table>"+
                                "</td>"+
                                "<td valign='top' cellpadding='0' cellspacing='0'>"+
                                    "<table width='100%' border='0' cellpadding='0' cellspacing='0'align='center'>"+
                                        "<tr class='isi2'>"+
                                            "<td width='58%' valign='top'>Apakah Pasien tampak Kurus?</td><td valign='top'>:&nbsp;</td><td width='35%' valign='top'>"+rs.getString("sg1")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='58%' valign='top'>Apakah ada penurunan BB selama satu bulan terakhir?</td><td valign='top'>:&nbsp;</td><td width='35%' valign='top'>"+rs.getString("sg2")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='58%' valign='top'>Apakah terdapat salah satu dari kondisi berikut?</td><td valign='top'>:&nbsp;</td><td width='35%' valign='top'>"+rs.getString("sg3")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='58%' valign='top'>Apakah terdapat penyakit / keadaan yang mengakibatkan pasien berisiko mengalami malnutrisi?</td><td valign='top'>:&nbsp;</td><td width='35%' valign='top'>"+rs.getString("sg4")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='58%' valign='top'>Nilai 1</td><td valign='top'>:&nbsp;</td><td width='35%' valign='top'>"+rs.getString("nilai1")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='58%' valign='top'>Nilai 2</td><td valign='top'>:&nbsp;</td><td width='35%' valign='top'>"+rs.getString("nilai2")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='58%' valign='top'>Nilai 3</td><td valign='top'>:&nbsp;</td><td width='35%' valign='top'>"+rs.getString("nilai3")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='58%' valign='top'>Nilai 4</td><td valign='top'>:&nbsp;</td><td width='35%' valign='top'>"+rs.getString("nilai4")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='58%' valign='top'>Total Skor</td><td valign='top'>:&nbsp;</td><td width='35%' valign='top'>"+rs.getString("total_hasil")+"</td>"+
                                        "</tr>"+
                                    "</table>"+
                                "</td>"+
                                "<td valign='top' cellpadding='0' cellspacing='0'>"+
                                    "<table width='100%' border='0' cellpadding='0' cellspacing='0'align='center'>"+
                                        "<tr class='isi2'>"+
                                            "<td width='44%' valign='top'>Tingkat Nyeri</td><td valign='top'>:&nbsp;</td><td width='55%' valign='top'>"+rs.getString("nyeri")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='44%' valign='top'>Provokes</td><td valign='top'>:&nbsp;</td><td width='55%' valign='top'>"+rs.getString("provokes")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='44%' valign='top'>Ket. Provokes</td><td valign='top'>:&nbsp;</td><td width='55%' valign='top'>"+rs.getString("ket_provokes")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='44%' valign='top'>Kualitas</td><td valign='top'>:&nbsp;</td><td width='55%' valign='top'>"+rs.getString("quality")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='44%' valign='top'>Ket. Kualitas</td><td valign='top'>:&nbsp;</td><td width='55%' valign='top'>"+rs.getString("ket_quality")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='44%' valign='top'>Lokasi</td><td valign='top'>:&nbsp;</td><td width='55%' valign='top'>"+rs.getString("lokasi")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='44%' valign='top'>Menyebar</td><td valign='top'>:&nbsp;</td><td width='55%' valign='top'>"+rs.getString("menyebar")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='44%' valign='top'>Skala Nyeri</td><td valign='top'>:&nbsp;</td><td width='55%' valign='top'>"+rs.getString("skala_nyeri")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='44%' valign='top'>Durasi</td><td valign='top'>:&nbsp;</td><td width='55%' valign='top'>"+rs.getString("durasi")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='44%' valign='top'>Nyeri Hilang</td><td valign='top'>:&nbsp;</td><td width='55%' valign='top'>"+rs.getString("nyeri_hilang")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='44%' valign='top'>Ket. Hilang Nyeri</td><td valign='top'>:&nbsp;</td><td width='55%' valign='top'>"+rs.getString("ket_nyeri")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='44%' valign='top'>Lapor Ke Dokter</td><td valign='top'>:&nbsp;</td><td width='55%' valign='top'>"+rs.getString("pada_dokter")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='44%' valign='top'>Jam Lapor</td><td valign='top'>:&nbsp;</td><td width='55%' valign='top'>"+rs.getString("ket_dokter")+"</td>"+
                                        "</tr>"+
                                    "</table>"+
                                "</td>"+
                                "<td valign='top' cellpadding='0' cellspacing='0'>"+
                                    "<table width='100%' border='0' cellpadding='0' cellspacing='0'align='center'>"+
                                        "<tr class='isi2'>"+
                                            "<td width='58%' valign='top'>Visus Kanan</td><td valign='top'>:&nbsp;</td><td width='20%' valign='top'>"+rs.getString("visuskanan")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='58%' valign='top'>Visus Kiri</td><td valign='top'>:&nbsp;</td><td width='20%' valign='top'>"+rs.getString("visuskiri")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='58%' valign='top'>Refraksi Kanan</td><td valign='top'>:&nbsp;</td><td width='20%' valign='top'>"+rs.getString("refraksikanan")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='58%' valign='top'>Refraksi Kiri</td><td valign='top'>:&nbsp;</td><td width='20%' valign='top'>"+rs.getString("refraksikiri")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='58%' valign='top'>Tio Kanan</td><td valign='top'>:&nbsp;</td><td width='20%' valign='top'>"+rs.getString("tiokanan")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='58%' valign='top'>Tio Kiri</td><td valign='top'>:&nbsp;</td><td width='20%' valign='top'>"+rs.getString("tiokiri")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='58%' valign='top'>Palbera Kanan</td><td valign='top'>:&nbsp;</td><td width='20%' valign='top'>"+rs.getString("palberakanan")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='58%' valign='top'>Palbera Kiri</td><td valign='top'>:&nbsp;</td><td width='20%' valign='top'>"+rs.getString("palberakiri")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='58%' valign='top'>Konjungtiva Kanan</td><td valign='top'>:&nbsp;</td><td width='20%' valign='top'>"+rs.getString("konjungtivakanan")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='58%' valign='top'>Konjungtiva Kiri</td><td valign='top'>:&nbsp;</td><td width='20%' valign='top'>"+rs.getString("konjungtivakiri")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='58%' valign='top'>Sklera Kanan</td><td valign='top'>:&nbsp;</td><td width='20%' valign='top'>"+rs.getString("sklerakanan")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='58%' valign='top'>Sklera Kiri</td><td valign='top'>:&nbsp;</td><td width='20%' valign='top'>"+rs.getString("sklerakiri")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='58%' valign='top'>Kornea Kanan</td><td valign='top'>:&nbsp;</td><td width='20%' valign='top'>"+rs.getString("korneakanan")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='58%' valign='top'>Kornea Kiri</td><td valign='top'>:&nbsp;</td><td width='20%' valign='top'>"+rs.getString("korneakiri")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='58%' valign='top'>BMD Kanan</td><td valign='top'>:&nbsp;</td><td width='20%' valign='top'>"+rs.getString("bmdkanan")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='58%' valign='top'>BMD Kiri</td><td valign='top'>:&nbsp;</td><td width='20%' valign='top'>"+rs.getString("bmdkiri")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='58%' valign='top'>Iris Kanan</td><td valign='top'>:&nbsp;</td><td width='20%' valign='top'>"+rs.getString("iriskanan")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='58%' valign='top'>Iris Kiri</td><td valign='top'>:&nbsp;</td><td width='20%' valign='top'>"+rs.getString("iriskiri")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='58%' valign='top'>Pupil Kanan</td><td valign='top'>:&nbsp;</td><td width='20%' valign='top'>"+rs.getString("pupilkanan")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='58%' valign='top'>Pupil Kiri</td><td valign='top'>:&nbsp;</td><td width='20%' valign='top'>"+rs.getString("pupilkiri")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='58%' valign='top'>Lensa Kanan</td><td valign='top'>:&nbsp;</td><td width='20%' valign='top'>"+rs.getString("lensakanan")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='58%' valign='top'>Lensa Kiri</td><td valign='top'>:&nbsp;</td><td width='20%' valign='top'>"+rs.getString("lensakiri")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='58%' valign='top'>Oftalmoskopi Kanan</td><td valign='top'>:&nbsp;</td><td width='35%' valign='top'>"+rs.getString("oftalmoskopikanan")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='58%' valign='top'>Oftalmoskopi Kiri</td><td valign='top'>:&nbsp;</td><td width='35%' valign='top'>"+rs.getString("oftalmoskopikiri")+"</td>"+
                                        "</tr>"+
                                    "</table>"+
                                "</td>"+
                                "<td valign='top' cellpadding='0' cellspacing='0'>"+
                                    "Masalah Keperawatan : "+masalahkeperawatan+"<br><br>"+
                                    "Rencana Keperawatan : "+rs.getString("rencana")+
                                "</td>"+
                            "</tr>"
                        );
                    }
                    LoadHTML.setText(
                        "<html>"+
                          "<table width='1800px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
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

                    File f = new File("DataPenilaianAwalKeperawatanRalan.html");            
                    BufferedWriter bw = new BufferedWriter(new FileWriter(f));            
                    bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                                "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                                "<table width='1800px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                    "<tr class='isi2'>"+
                                        "<td valign='top' align='center'>"+
                                            "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                            akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                            akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                            "<font size='2' face='Tahoma'>DATA PENGKAJIAN AWAL KEPERAWATAN RAWAT JALAN<br><br></font>"+        
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
        runBackground(() ->tampil());
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
        runBackground(() ->tampil());
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            TCari.setText("");
            runBackground(() ->tampil());
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
            if((evt.getClickCount()==2)&&(tbObat.getSelectedColumn()==0)){
                TabRawat.setSelectedIndex(0);
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

    private void KdPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdPetugasKeyPressed
        
    }//GEN-LAST:event_KdPetugasKeyPressed

    private void BtnPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPetugasActionPerformed
        if (petugas == null || !petugas.isDisplayable()) {
            petugas=new DlgCariPetugas(null,false);
            petugas.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            petugas.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    if(petugas.getTable().getSelectedRow()!= -1){                   
                        KdPetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                        NmPetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                    }  
                    BtnPetugas.requestFocus();
                    petugas=null;
                }
            });

            petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            petugas.setLocationRelativeTo(internalFrame1);
        }
        if (petugas == null) return;
        if (!petugas.isVisible()) {
            petugas.isCek();    
            petugas.emptTeks();
        }
        
        if (petugas.isVisible()) {
            petugas.toFront();
            return;
        }
        petugas.setVisible(true); 
    }//GEN-LAST:event_BtnPetugasActionPerformed

    private void BtnPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPetugasKeyPressed
        //Valid.pindah(evt,Monitoring,BtnSimpan);
    }//GEN-LAST:event_BtnPetugasKeyPressed

    private void BBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BBKeyPressed
        Valid.pindah(evt,GCS,TB);
    }//GEN-LAST:event_BBKeyPressed

    private void TBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TBKeyPressed
        Valid.pindah(evt,BB,BMI);
    }//GEN-LAST:event_TBKeyPressed

    private void NadiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NadiKeyPressed
        Valid.pindah(evt,TD,RR);
    }//GEN-LAST:event_NadiKeyPressed

    private void SuhuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SuhuKeyPressed
        Valid.pindah(evt,RR,GCS);
    }//GEN-LAST:event_SuhuKeyPressed

    private void TDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TDKeyPressed
        Valid.pindah(evt,Informasi,Nadi);
    }//GEN-LAST:event_TDKeyPressed

    private void RRKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RRKeyPressed
        Valid.pindah(evt,Nadi,Suhu);
    }//GEN-LAST:event_RRKeyPressed

    private void BMIKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BMIKeyPressed
        Valid.pindah(evt,TB,KeluhanUtama);
    }//GEN-LAST:event_BMIKeyPressed

    private void AlergiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlergiKeyPressed
        Valid.pindah(evt,RPS,AlatBantu);
    }//GEN-LAST:event_AlergiKeyPressed

    private void InformasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_InformasiKeyPressed
        Valid.pindah(evt,TglAsuhan,TD);
    }//GEN-LAST:event_InformasiKeyPressed

    private void KeluhanUtamaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeluhanUtamaKeyPressed
        Valid.pindah2(evt,BMI,RPK);
    }//GEN-LAST:event_KeluhanUtamaKeyPressed

    private void RPDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RPDKeyPressed
        Valid.pindah2(evt,RPK,RPO);
    }//GEN-LAST:event_RPDKeyPressed

    private void RPKKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RPKKeyPressed
        Valid.pindah2(evt,KeluhanUtama,RPD);
    }//GEN-LAST:event_RPKKeyPressed

    private void RPOKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RPOKeyPressed
        Valid.pindah2(evt,RPD,RPS);
    }//GEN-LAST:event_RPOKeyPressed

    private void AlatBantuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlatBantuKeyPressed
        Valid.pindah(evt,Alergi,KetBantu);
    }//GEN-LAST:event_AlatBantuKeyPressed

    private void KetBantuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetBantuKeyPressed
        Valid.pindah(evt,AlatBantu,Prothesa);
    }//GEN-LAST:event_KetBantuKeyPressed

    private void ProthesaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ProthesaKeyPressed
        Valid.pindah(evt,KetBantu,KetProthesa);
    }//GEN-LAST:event_ProthesaKeyPressed

    private void KetProthesaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetProthesaKeyPressed
        Valid.pindah(evt,Prothesa,ADL);
    }//GEN-LAST:event_KetProthesaKeyPressed

    private void ADLKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ADLKeyPressed
        Valid.pindah(evt,KetProthesa,StatusPsiko);
    }//GEN-LAST:event_ADLKeyPressed

    private void StatusPsikoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_StatusPsikoKeyPressed
        Valid.pindah(evt,ADL,KetPsiko);
    }//GEN-LAST:event_StatusPsikoKeyPressed

    private void KetPsikoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetPsikoKeyPressed
        Valid.pindah(evt,StatusPsiko,HubunganKeluarga);
    }//GEN-LAST:event_KetPsikoKeyPressed

    private void HubunganKeluargaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HubunganKeluargaKeyPressed
        Valid.pindah(evt,KetPsiko,TinggalDengan);
    }//GEN-LAST:event_HubunganKeluargaKeyPressed

    private void TinggalDenganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TinggalDenganKeyPressed
        Valid.pindah(evt,HubunganKeluarga,KetTinggal);
    }//GEN-LAST:event_TinggalDenganKeyPressed

    private void KetTinggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetTinggalKeyPressed
        Valid.pindah(evt,TinggalDengan,Ekonomi);
    }//GEN-LAST:event_KetTinggalKeyPressed

    private void EkonomiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EkonomiKeyPressed
        Valid.pindah(evt,KetTinggal,StatusBudaya);
    }//GEN-LAST:event_EkonomiKeyPressed

    private void EdukasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EdukasiKeyPressed
        Valid.pindah(evt,KetBudaya,KetEdukasi);
    }//GEN-LAST:event_EdukasiKeyPressed

    private void KetEdukasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetEdukasiKeyPressed
        Valid.pindah(evt,Edukasi,ATS);
    }//GEN-LAST:event_KetEdukasiKeyPressed

    private void LaporKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LaporKeyPressed
        Valid.pindah(evt,Hasil,KetLapor);
    }//GEN-LAST:event_LaporKeyPressed

    private void ATSKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ATSKeyPressed
        Valid.pindah(evt,KetEdukasi,BJM);
    }//GEN-LAST:event_ATSKeyPressed

    private void BJMKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BJMKeyPressed
        Valid.pindah(evt,ATS,MSA);
    }//GEN-LAST:event_BJMKeyPressed

    private void HasilKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HasilKeyPressed
        Valid.pindah(evt,MSA,Lapor);
    }//GEN-LAST:event_HasilKeyPressed

    private void SG2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SG2KeyPressed
        Valid.pindah(evt,Nilai1,Nilai2);
    }//GEN-LAST:event_SG2KeyPressed

    private void KetLaporKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetLaporKeyPressed
        Valid.pindah(evt,Lapor,SG1);
    }//GEN-LAST:event_KetLaporKeyPressed

    private void SG1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SG1KeyPressed
        Valid.pindah(evt,KetLapor,Nilai1);
    }//GEN-LAST:event_SG1KeyPressed

    private void Nilai1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Nilai1KeyPressed
        Valid.pindah(evt,SG1,SG2);
    }//GEN-LAST:event_Nilai1KeyPressed

    private void MSAKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MSAKeyPressed
        Valid.pindah(evt,BJM,Hasil);
    }//GEN-LAST:event_MSAKeyPressed

    private void Nilai2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Nilai2KeyPressed
        Valid.pindah(evt,SG2,SG3);
    }//GEN-LAST:event_Nilai2KeyPressed

    private void NyeriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NyeriKeyPressed
        Valid.pindah(evt,Nilai2,Provokes);
    }//GEN-LAST:event_NyeriKeyPressed

    private void ProvokesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ProvokesKeyPressed
        Valid.pindah(evt,Nyeri,KetProvokes);
    }//GEN-LAST:event_ProvokesKeyPressed

    private void KetProvokesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetProvokesKeyPressed
        Valid.pindah(evt,Provokes,Quality);
    }//GEN-LAST:event_KetProvokesKeyPressed

    private void QualityKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_QualityKeyPressed
        Valid.pindah(evt,KetProvokes,KetQuality);
    }//GEN-LAST:event_QualityKeyPressed

    private void KetQualityKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetQualityKeyPressed
        Valid.pindah(evt,Quality,Lokasi);
    }//GEN-LAST:event_KetQualityKeyPressed

    private void LokasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LokasiKeyPressed
        Valid.pindah(evt,KetQuality,Menyebar);
    }//GEN-LAST:event_LokasiKeyPressed

    private void MenyebarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MenyebarKeyPressed
        Valid.pindah(evt,Lokasi,SkalaNyeri);
    }//GEN-LAST:event_MenyebarKeyPressed

    private void SkalaNyeriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkalaNyeriKeyPressed
        Valid.pindah(evt,Menyebar,Durasi);
    }//GEN-LAST:event_SkalaNyeriKeyPressed

    private void DurasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DurasiKeyPressed
        Valid.pindah(evt,SkalaNyeri,NyeriHilang);
    }//GEN-LAST:event_DurasiKeyPressed

    private void NyeriHilangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NyeriHilangKeyPressed
        Valid.pindah(evt,Durasi,KetNyeri);
    }//GEN-LAST:event_NyeriHilangKeyPressed

    private void KetNyeriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetNyeriKeyPressed
        Valid.pindah(evt,NyeriHilang,PadaDokter);
    }//GEN-LAST:event_KetNyeriKeyPressed

    private void PadaDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PadaDokterKeyPressed
        Valid.pindah(evt,KetNyeri,KetDokter);
    }//GEN-LAST:event_PadaDokterKeyPressed

    private void KetDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetDokterKeyPressed
        Valid.pindah(evt,PadaDokter,VisusKanan);
    }//GEN-LAST:event_KetDokterKeyPressed

    private void RencanaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RencanaKeyPressed
        Valid.pindah2(evt,TCariMasalah,BtnSimpan);
    }//GEN-LAST:event_RencanaKeyPressed

    private void TotalHasilKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TotalHasilKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TotalHasilKeyPressed

    private void TglAsuhanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglAsuhanKeyPressed
        Valid.pindah(evt,Rencana,Informasi);
    }//GEN-LAST:event_TglAsuhanKeyPressed

    private void GCSKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GCSKeyPressed
        Valid.pindah(evt,Suhu,BB);
    }//GEN-LAST:event_GCSKeyPressed

    private void CacatFisikKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CacatFisikKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_CacatFisikKeyPressed

    private void StatusBudayaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_StatusBudayaKeyPressed
        Valid.pindah(evt,Ekonomi,KetBudaya);
    }//GEN-LAST:event_StatusBudayaKeyPressed

    private void KetBudayaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetBudayaKeyPressed
        Valid.pindah(evt,StatusBudaya,Edukasi);
    }//GEN-LAST:event_KetBudayaKeyPressed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if(TabRawat.getSelectedIndex()==1){
            runBackground(() ->tampil());
        }
    }//GEN-LAST:event_TabRawatMouseClicked

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        runBackground(() ->tampilMasalah());
    }//GEN-LAST:event_formWindowOpened

    private void BahasaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BahasaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BahasaKeyPressed

    private void AgamaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AgamaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_AgamaKeyPressed

    private void SG1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SG1ItemStateChanged
        Nilai1.setSelectedIndex(SG1.getSelectedIndex());
        TotalHasil.setText(""+(Integer.parseInt(Nilai1.getSelectedItem().toString())+Integer.parseInt(Nilai2.getSelectedItem().toString())+Integer.parseInt(Nilai3.getSelectedItem().toString())+Integer.parseInt(Nilai4.getSelectedItem().toString())));
    }//GEN-LAST:event_SG1ItemStateChanged

    private void Nilai1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_Nilai1ItemStateChanged
        TotalHasil.setText(""+(Integer.parseInt(Nilai1.getSelectedItem().toString())+Integer.parseInt(Nilai2.getSelectedItem().toString())+Integer.parseInt(Nilai3.getSelectedItem().toString())+Integer.parseInt(Nilai4.getSelectedItem().toString())));
    }//GEN-LAST:event_Nilai1ItemStateChanged

    private void SG2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SG2ItemStateChanged
        Nilai2.setSelectedIndex(SG2.getSelectedIndex());
        TotalHasil.setText(""+(Integer.parseInt(Nilai1.getSelectedItem().toString())+Integer.parseInt(Nilai2.getSelectedItem().toString())+Integer.parseInt(Nilai3.getSelectedItem().toString())+Integer.parseInt(Nilai4.getSelectedItem().toString())));
    }//GEN-LAST:event_SG2ItemStateChanged

    private void ChkAccorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkAccorActionPerformed
        if(tbObat.getSelectedRow()!= -1){
            isMenu();
        }else{
            ChkAccor.setSelected(false);
            JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data yang mau ditampilkan...!!!!");
        }
    }//GEN-LAST:event_ChkAccorActionPerformed

    private void BtnPrint1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrint1ActionPerformed
       if(tbObat.getSelectedRow()>-1){
            Map<String, Object> param = new HashMap<>();    
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());          
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            param.put("nyeri",Sequel.cariGambar("select gambar.nyeri from gambar"));
            param.put("mata",Sequel.cariGambar("select mata from gambar"));  
            param.put("finger",Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",tbObat.getValueAt(tbObat.getSelectedRow(),63).toString()));
            try {
                masalahkeperawatan="";
                ps2=koneksi.prepareStatement(
                    "select master_masalah_keperawatan_mata.kode_masalah,master_masalah_keperawatan_mata.nama_masalah from master_masalah_keperawatan_mata "+
                    "inner join penilaian_awal_keperawatan_mata_masalah on penilaian_awal_keperawatan_mata_masalah.kode_masalah=master_masalah_keperawatan_mata.kode_masalah "+
                    "where penilaian_awal_keperawatan_mata_masalah.no_rawat=? order by kode_masalah");
                try {
                    ps2.setString(1,tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
                    rs2=ps2.executeQuery();
                    while(rs2.next()){
                        masalahkeperawatan=rs2.getString("nama_masalah")+", "+masalahkeperawatan;
                    }
                } catch (Exception e) {
                    System.out.println("Notif : "+e);
                } finally{
                    if(rs2!=null){
                        rs2.close();
                    }
                    if(ps2!=null){
                        ps2.close();
                    }
                }
            } catch (Exception e) {
                System.out.println("Notif : "+e);
            }
            param.put("masalah",masalahkeperawatan);  
            Valid.MyReportqry("rptCetakPenilaianAwalKeperawatanMata.jasper","report","::[ Laporan Pengkajian Awal Keperawatan Mata ]::",
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,pasien.agama,bahasa_pasien.nama_bahasa,cacat_fisik.nama_cacat,penilaian_awal_keperawatan_mata.tanggal,"+
                        "penilaian_awal_keperawatan_mata.informasi,penilaian_awal_keperawatan_mata.td,penilaian_awal_keperawatan_mata.nadi,penilaian_awal_keperawatan_mata.rr,penilaian_awal_keperawatan_mata.suhu,penilaian_awal_keperawatan_mata.bb,penilaian_awal_keperawatan_mata.tb,"+
                        "penilaian_awal_keperawatan_mata.nadi,penilaian_awal_keperawatan_mata.rr,penilaian_awal_keperawatan_mata.suhu,penilaian_awal_keperawatan_mata.gcs,penilaian_awal_keperawatan_mata.bb,penilaian_awal_keperawatan_mata.tb,penilaian_awal_keperawatan_mata.bmi,penilaian_awal_keperawatan_mata.keluhan_utama,"+
                        "penilaian_awal_keperawatan_mata.rpd,penilaian_awal_keperawatan_mata.rps,penilaian_awal_keperawatan_mata.rpk,penilaian_awal_keperawatan_mata.rpo,penilaian_awal_keperawatan_mata.alergi,penilaian_awal_keperawatan_mata.alat_bantu,penilaian_awal_keperawatan_mata.ket_bantu,penilaian_awal_keperawatan_mata.prothesa,"+
                        "penilaian_awal_keperawatan_mata.ket_pro,penilaian_awal_keperawatan_mata.adl,penilaian_awal_keperawatan_mata.status_psiko,penilaian_awal_keperawatan_mata.ket_psiko,penilaian_awal_keperawatan_mata.hub_keluarga,penilaian_awal_keperawatan_mata.tinggal_dengan,"+
                        "penilaian_awal_keperawatan_mata.ket_tinggal,penilaian_awal_keperawatan_mata.ekonomi,penilaian_awal_keperawatan_mata.edukasi,penilaian_awal_keperawatan_mata.ket_edukasi,penilaian_awal_keperawatan_mata.berjalan_a,penilaian_awal_keperawatan_mata.berjalan_b,"+
                        "penilaian_awal_keperawatan_mata.berjalan_c,penilaian_awal_keperawatan_mata.hasil,penilaian_awal_keperawatan_mata.lapor,penilaian_awal_keperawatan_mata.ket_lapor,penilaian_awal_keperawatan_mata.sg1,penilaian_awal_keperawatan_mata.nilai1,penilaian_awal_keperawatan_mata.sg2,penilaian_awal_keperawatan_mata.nilai2,penilaian_awal_keperawatan_mata.sg3,penilaian_awal_keperawatan_mata.nilai3,penilaian_awal_keperawatan_mata.sg4,penilaian_awal_keperawatan_mata.nilai4,"+
                        "penilaian_awal_keperawatan_mata.total_hasil,penilaian_awal_keperawatan_mata.nyeri,penilaian_awal_keperawatan_mata.provokes,penilaian_awal_keperawatan_mata.ket_provokes,penilaian_awal_keperawatan_mata.quality,penilaian_awal_keperawatan_mata.ket_quality,penilaian_awal_keperawatan_mata.lokasi,penilaian_awal_keperawatan_mata.menyebar,"+
                        "penilaian_awal_keperawatan_mata.skala_nyeri,penilaian_awal_keperawatan_mata.durasi,penilaian_awal_keperawatan_mata.nyeri_hilang,penilaian_awal_keperawatan_mata.ket_nyeri,penilaian_awal_keperawatan_mata.pada_dokter,penilaian_awal_keperawatan_mata.ket_dokter,penilaian_awal_keperawatan_mata.rencana,penilaian_awal_keperawatan_mata.visuskanan,penilaian_awal_keperawatan_mata.visuskiri,penilaian_awal_keperawatan_mata.refraksikanan,penilaian_awal_keperawatan_mata.refraksikiri,"+
                        "penilaian_awal_keperawatan_mata.tiokanan,penilaian_awal_keperawatan_mata.tiokiri,penilaian_awal_keperawatan_mata.palberakanan,penilaian_awal_keperawatan_mata.palberakiri,penilaian_awal_keperawatan_mata.konjungtivakanan,penilaian_awal_keperawatan_mata.konjungtivakiri,penilaian_awal_keperawatan_mata.sklerakanan,penilaian_awal_keperawatan_mata.sklerakiri,penilaian_awal_keperawatan_mata.korneakanan,penilaian_awal_keperawatan_mata.korneakiri,penilaian_awal_keperawatan_mata.bmdkanan,penilaian_awal_keperawatan_mata.bmdkiri,"+
                        "penilaian_awal_keperawatan_mata.iriskanan,penilaian_awal_keperawatan_mata.iriskiri,penilaian_awal_keperawatan_mata.pupilkanan,penilaian_awal_keperawatan_mata.pupilkiri,penilaian_awal_keperawatan_mata.lensakanan,penilaian_awal_keperawatan_mata.lensakiri,penilaian_awal_keperawatan_mata.oftalmoskopikanan,penilaian_awal_keperawatan_mata.oftalmoskopikiri, "+
                        "penilaian_awal_keperawatan_mata.nip,petugas.nama,penilaian_awal_keperawatan_mata.budaya,penilaian_awal_keperawatan_mata.ket_budaya "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join penilaian_awal_keperawatan_mata on reg_periksa.no_rawat=penilaian_awal_keperawatan_mata.no_rawat "+
                        "inner join petugas on penilaian_awal_keperawatan_mata.nip=petugas.nip "+
                        "inner join bahasa_pasien on bahasa_pasien.id=pasien.bahasa_pasien "+
                        "inner join cacat_fisik on cacat_fisik.id=pasien.cacat_fisik where reg_periksa.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'",param);
        }else{
            JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data terlebih dahulu..!!!!");
        }  
    }//GEN-LAST:event_BtnPrint1ActionPerformed

    private void DetailRencanaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DetailRencanaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DetailRencanaKeyPressed

    private void VisusKananKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_VisusKananKeyPressed
        Valid.pindah(evt, KetDokter, VisusKiri);
    }//GEN-LAST:event_VisusKananKeyPressed

    private void VisusKiriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_VisusKiriKeyPressed
        Valid.pindah(evt, VisusKanan, RefraksiKanan);
    }//GEN-LAST:event_VisusKiriKeyPressed

    private void RefraksiKananKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RefraksiKananKeyPressed
        Valid.pindah(evt, VisusKiri, RefraksiKiri);
    }//GEN-LAST:event_RefraksiKananKeyPressed

    private void RefraksiKiriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RefraksiKiriKeyPressed
        Valid.pindah(evt, RefraksiKanan, TioKanan);
    }//GEN-LAST:event_RefraksiKiriKeyPressed

    private void TioKananKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TioKananKeyPressed
        Valid.pindah(evt, RefraksiKiri, TioKiri);
    }//GEN-LAST:event_TioKananKeyPressed

    private void TioKiriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TioKiriKeyPressed
        Valid.pindah(evt, TioKanan, PalberaKanan);
    }//GEN-LAST:event_TioKiriKeyPressed

    private void PalberaKananKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PalberaKananKeyPressed
        Valid.pindah(evt, TioKiri, PalberaKiri);
    }//GEN-LAST:event_PalberaKananKeyPressed

    private void PalberaKiriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PalberaKiriKeyPressed
        Valid.pindah(evt, PalberaKanan, KonjungtivaKanan);
    }//GEN-LAST:event_PalberaKiriKeyPressed

    private void KonjungtivaKananKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KonjungtivaKananKeyPressed
        Valid.pindah(evt, PalberaKiri, KonjungtivaKiri);
    }//GEN-LAST:event_KonjungtivaKananKeyPressed

    private void KonjungtivaKiriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KonjungtivaKiriKeyPressed
        Valid.pindah(evt, KonjungtivaKanan, SkleraKanan);
    }//GEN-LAST:event_KonjungtivaKiriKeyPressed

    private void SkleraKananKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkleraKananKeyPressed
        Valid.pindah(evt, KonjungtivaKiri, SkleraKiri);
    }//GEN-LAST:event_SkleraKananKeyPressed

    private void SkleraKiriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkleraKiriKeyPressed
        Valid.pindah(evt, SkleraKanan, KorneaKanan);
    }//GEN-LAST:event_SkleraKiriKeyPressed

    private void KorneaKananKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KorneaKananKeyPressed
        Valid.pindah(evt, SkleraKiri, KorneaKiri);
    }//GEN-LAST:event_KorneaKananKeyPressed

    private void KorneaKiriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KorneaKiriKeyPressed
        Valid.pindah(evt, KorneaKanan, BMDKanan);
    }//GEN-LAST:event_KorneaKiriKeyPressed

    private void BMDKananKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BMDKananKeyPressed
        Valid.pindah(evt, KorneaKiri, BMDKiri);
    }//GEN-LAST:event_BMDKananKeyPressed

    private void BMDKiriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BMDKiriKeyPressed
        Valid.pindah(evt, BMDKanan, IrisKanan);
    }//GEN-LAST:event_BMDKiriKeyPressed

    private void IrisKananKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_IrisKananKeyPressed
        Valid.pindah(evt, BMDKiri, IrisKiri);
    }//GEN-LAST:event_IrisKananKeyPressed

    private void IrisKiriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_IrisKiriKeyPressed
        Valid.pindah(evt, IrisKanan, PupilKanan);
    }//GEN-LAST:event_IrisKiriKeyPressed

    private void PupilKananKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PupilKananKeyPressed
        Valid.pindah(evt, IrisKiri, PupilKiri);
    }//GEN-LAST:event_PupilKananKeyPressed

    private void PupilKiriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PupilKiriKeyPressed
        Valid.pindah(evt, PupilKanan, LensaKanan);
    }//GEN-LAST:event_PupilKiriKeyPressed

    private void LensaKananKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LensaKananKeyPressed
        Valid.pindah(evt, PupilKiri, LensaKiri);
    }//GEN-LAST:event_LensaKananKeyPressed

    private void LensaKiriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LensaKiriKeyPressed
        Valid.pindah(evt, LensaKanan, OftalmoskopiKanan);
    }//GEN-LAST:event_LensaKiriKeyPressed

    private void OftalmoskopiKananKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_OftalmoskopiKananKeyPressed
        Valid.pindah(evt, LensaKiri, OftalmoskopiKiri);
    }//GEN-LAST:event_OftalmoskopiKananKeyPressed

    private void OftalmoskopiKiriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_OftalmoskopiKiriKeyPressed
        Valid.pindah(evt, OftalmoskopiKanan, Rencana);
    }//GEN-LAST:event_OftalmoskopiKiriKeyPressed

    private void BtnTambahMasalahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahMasalahActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        MasterMasalahKeperawatanMata form=new MasterMasalahKeperawatanMata(null,false);
        form.isCek();
        form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        form.setLocationRelativeTo(internalFrame1);
        form.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnTambahMasalahActionPerformed

    private void BtnCariPemeriksaan1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariPemeriksaan1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnCariPemeriksaan1KeyPressed

    private void BtnCariPemeriksaan1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariPemeriksaan1ActionPerformed
        runBackground(() ->tampilMasalah());
    }//GEN-LAST:event_BtnCariPemeriksaan1ActionPerformed

    private void TCariMasalahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariMasalahKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            runBackground(() ->tampilMasalah());
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Rencana.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            KetDokter.requestFocus();
        }
    }//GEN-LAST:event_TCariMasalahKeyPressed

    private void RPSKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RPSKeyPressed
        Valid.pindah(evt, RPO, Alergi);
    }//GEN-LAST:event_RPSKeyPressed

    private void SG3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SG3ItemStateChanged
        Nilai3.setSelectedIndex(SG3.getSelectedIndex());
        TotalHasil.setText(""+(Integer.parseInt(Nilai1.getSelectedItem().toString())+Integer.parseInt(Nilai2.getSelectedItem().toString())+Integer.parseInt(Nilai3.getSelectedItem().toString())+Integer.parseInt(Nilai4.getSelectedItem().toString())));
    }//GEN-LAST:event_SG3ItemStateChanged

    private void SG3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SG3KeyPressed
        Valid.pindah(evt, Nilai2,Nilai3);
    }//GEN-LAST:event_SG3KeyPressed

    private void Nilai3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Nilai3KeyPressed
        Valid.pindah(evt, SG3,SG4 );
    }//GEN-LAST:event_Nilai3KeyPressed

    private void SG4ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SG4ItemStateChanged
        Nilai4.setSelectedIndex(SG4.getSelectedIndex());
        TotalHasil.setText(""+(Integer.parseInt(Nilai1.getSelectedItem().toString())+Integer.parseInt(Nilai2.getSelectedItem().toString())+Integer.parseInt(Nilai3.getSelectedItem().toString())+Integer.parseInt(Nilai4.getSelectedItem().toString())));
    }//GEN-LAST:event_SG4ItemStateChanged

    private void SG4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SG4KeyPressed
        Valid.pindah(evt, Nilai3, Nilai4);
    }//GEN-LAST:event_SG4KeyPressed

    private void Nilai4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Nilai4KeyPressed
        Valid.pindah(evt, SG4, Nyeri);
    }//GEN-LAST:event_Nilai4KeyPressed

    private void Nilai2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_Nilai2ItemStateChanged
        TotalHasil.setText(""+(Integer.parseInt(Nilai1.getSelectedItem().toString())+Integer.parseInt(Nilai2.getSelectedItem().toString())+Integer.parseInt(Nilai3.getSelectedItem().toString())+Integer.parseInt(Nilai4.getSelectedItem().toString())));
    }//GEN-LAST:event_Nilai2ItemStateChanged

    private void Nilai3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_Nilai3ItemStateChanged
        TotalHasil.setText(""+(Integer.parseInt(Nilai1.getSelectedItem().toString())+Integer.parseInt(Nilai2.getSelectedItem().toString())+Integer.parseInt(Nilai3.getSelectedItem().toString())+Integer.parseInt(Nilai4.getSelectedItem().toString())));
    }//GEN-LAST:event_Nilai3ItemStateChanged

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMPenilaianAwalKeperawatanMata dialog = new RMPenilaianAwalKeperawatanMata(new javax.swing.JFrame(), true);
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
    private widget.ComboBox ADL;
    private widget.ComboBox ATS;
    private widget.TextBox Agama;
    private widget.ComboBox AlatBantu;
    private widget.TextBox Alergi;
    private widget.TextBox BB;
    private widget.ComboBox BJM;
    private widget.TextBox BMDKanan;
    private widget.TextBox BMDKiri;
    private widget.TextBox BMI;
    private widget.TextBox Bahasa;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnCariPemeriksaan1;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPetugas;
    private widget.Button BtnPrint;
    private widget.Button BtnPrint1;
    private widget.Button BtnSimpan;
    private widget.Button BtnTambahMasalah;
    private widget.TextBox CacatFisik;
    private widget.CekBox ChkAccor;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.TextArea DetailRencana;
    private widget.TextBox Durasi;
    private widget.ComboBox Edukasi;
    private widget.ComboBox Ekonomi;
    private widget.PanelBiasa FormInput;
    private widget.PanelBiasa FormMasalahRencana;
    private widget.PanelBiasa FormMenu;
    private widget.TextBox GCS;
    private widget.ComboBox Hasil;
    private widget.ComboBox HubunganKeluarga;
    private widget.ComboBox Informasi;
    private widget.TextBox IrisKanan;
    private widget.TextBox IrisKiri;
    private widget.TextBox Jk;
    private widget.TextBox KdPetugas;
    private widget.TextArea KeluhanUtama;
    private widget.TextBox KetBantu;
    private widget.TextBox KetBudaya;
    private widget.TextBox KetDokter;
    private widget.TextBox KetEdukasi;
    private widget.TextBox KetLapor;
    private widget.TextBox KetNyeri;
    private widget.TextBox KetProthesa;
    private widget.TextBox KetProvokes;
    private widget.TextBox KetPsiko;
    private widget.TextBox KetQuality;
    private widget.TextBox KetTinggal;
    private widget.TextBox KonjungtivaKanan;
    private widget.TextBox KonjungtivaKiri;
    private widget.TextBox KorneaKanan;
    private widget.TextBox KorneaKiri;
    private widget.Label LCount;
    private widget.ComboBox Lapor;
    private widget.TextBox LensaKanan;
    private widget.TextBox LensaKiri;
    private widget.editorpane LoadHTML;
    private widget.TextBox Lokasi;
    private widget.ComboBox MSA;
    private widget.ComboBox Menyebar;
    private widget.TextBox Nadi;
    private widget.ComboBox Nilai1;
    private widget.ComboBox Nilai2;
    private widget.ComboBox Nilai3;
    private widget.ComboBox Nilai4;
    private widget.TextBox NmPetugas;
    private widget.ComboBox Nyeri;
    private widget.ComboBox NyeriHilang;
    private widget.TextArea OftalmoskopiKanan;
    private widget.TextArea OftalmoskopiKiri;
    private widget.ComboBox PadaDokter;
    private widget.TextBox PalberaKanan;
    private widget.TextBox PalberaKiri;
    private widget.PanelBiasa PanelAccor;
    private usu.widget.glass.PanelGlass PanelWall;
    private usu.widget.glass.PanelGlass PanelWall1;
    private usu.widget.glass.PanelGlass PanelWall2;
    private widget.ComboBox Prothesa;
    private widget.ComboBox Provokes;
    private widget.TextBox PupilKanan;
    private widget.TextBox PupilKiri;
    private widget.ComboBox Quality;
    private widget.TextArea RPD;
    private widget.TextArea RPK;
    private widget.TextArea RPO;
    private widget.TextArea RPS;
    private widget.TextBox RR;
    private widget.TextBox RefraksiKanan;
    private widget.TextBox RefraksiKiri;
    private widget.TextArea Rencana;
    private widget.ComboBox SG1;
    private widget.ComboBox SG2;
    private widget.ComboBox SG3;
    private widget.ComboBox SG4;
    private widget.ScrollPane ScorllPane5;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll6;
    private widget.ScrollPane Scroll7;
    private widget.ScrollPane ScrollPane6;
    private widget.ComboBox SkalaNyeri;
    private widget.TextBox SkleraKanan;
    private widget.TextBox SkleraKiri;
    private widget.ComboBox StatusBudaya;
    private widget.ComboBox StatusPsiko;
    private widget.TextBox Suhu;
    private widget.TextBox TB;
    private widget.TextBox TCari;
    private widget.TextBox TCariMasalah;
    private widget.TextBox TD;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRM1;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.TextBox TPasien1;
    private javax.swing.JTabbedPane TabRawat;
    private widget.Tanggal TglAsuhan;
    private widget.TextBox TglLahir;
    private widget.ComboBox TinggalDengan;
    private widget.TextBox TioKanan;
    private widget.TextBox TioKiri;
    private widget.TextBox TotalHasil;
    private widget.TextBox VisusKanan;
    private widget.TextBox VisusKiri;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.Label jLabel10;
    private widget.Label jLabel100;
    private widget.Label jLabel101;
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
    private widget.Label jLabel30;
    private widget.Label jLabel31;
    private widget.Label jLabel32;
    private widget.Label jLabel33;
    private widget.Label jLabel34;
    private widget.Label jLabel36;
    private widget.Label jLabel37;
    private widget.Label jLabel43;
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
    private widget.Label jLabel9;
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
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private widget.Label label11;
    private widget.Label label12;
    private widget.Label label14;
    private widget.Label label15;
    private widget.Label label16;
    private widget.Label label17;
    private widget.Label label18;
    private widget.Label label19;
    private widget.Label label20;
    private widget.Label label21;
    private widget.Label label22;
    private widget.Label label23;
    private widget.Label label24;
    private widget.Label label25;
    private widget.Label label26;
    private widget.Label label27;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollInput;
    private widget.ScrollPane scrollPane1;
    private widget.ScrollPane scrollPane2;
    private widget.ScrollPane scrollPane3;
    private widget.ScrollPane scrollPane4;
    private widget.ScrollPane scrollPane5;
    private widget.ScrollPane scrollPane6;
    private widget.ScrollPane scrollPane9;
    private widget.Table tbMasalahDetailMasalah;
    private widget.Table tbMasalahKeperawatan;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            if(TCari.getText().equals("")){
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,pasien.agama,bahasa_pasien.nama_bahasa,cacat_fisik.nama_cacat,penilaian_awal_keperawatan_mata.tanggal,"+
                        "penilaian_awal_keperawatan_mata.informasi,penilaian_awal_keperawatan_mata.td,penilaian_awal_keperawatan_mata.nadi,penilaian_awal_keperawatan_mata.rr,penilaian_awal_keperawatan_mata.suhu,penilaian_awal_keperawatan_mata.bb,penilaian_awal_keperawatan_mata.tb,"+
                        "penilaian_awal_keperawatan_mata.nadi,penilaian_awal_keperawatan_mata.rr,penilaian_awal_keperawatan_mata.suhu,penilaian_awal_keperawatan_mata.gcs,penilaian_awal_keperawatan_mata.bb,penilaian_awal_keperawatan_mata.tb,penilaian_awal_keperawatan_mata.bmi,penilaian_awal_keperawatan_mata.keluhan_utama,"+
                        "penilaian_awal_keperawatan_mata.rpd,penilaian_awal_keperawatan_mata.rps,penilaian_awal_keperawatan_mata.rpk,penilaian_awal_keperawatan_mata.rpo,penilaian_awal_keperawatan_mata.alergi,penilaian_awal_keperawatan_mata.alat_bantu,penilaian_awal_keperawatan_mata.ket_bantu,penilaian_awal_keperawatan_mata.prothesa,"+
                        "penilaian_awal_keperawatan_mata.ket_pro,penilaian_awal_keperawatan_mata.adl,penilaian_awal_keperawatan_mata.status_psiko,penilaian_awal_keperawatan_mata.ket_psiko,penilaian_awal_keperawatan_mata.hub_keluarga,penilaian_awal_keperawatan_mata.tinggal_dengan,"+
                        "penilaian_awal_keperawatan_mata.ket_tinggal,penilaian_awal_keperawatan_mata.ekonomi,penilaian_awal_keperawatan_mata.edukasi,penilaian_awal_keperawatan_mata.ket_edukasi,penilaian_awal_keperawatan_mata.berjalan_a,penilaian_awal_keperawatan_mata.berjalan_b,"+
                        "penilaian_awal_keperawatan_mata.berjalan_c,penilaian_awal_keperawatan_mata.hasil,penilaian_awal_keperawatan_mata.lapor,penilaian_awal_keperawatan_mata.ket_lapor,penilaian_awal_keperawatan_mata.sg1,penilaian_awal_keperawatan_mata.nilai1,penilaian_awal_keperawatan_mata.sg2,penilaian_awal_keperawatan_mata.nilai2,penilaian_awal_keperawatan_mata.sg3,penilaian_awal_keperawatan_mata.nilai3,penilaian_awal_keperawatan_mata.sg4,penilaian_awal_keperawatan_mata.nilai4,"+
                        "penilaian_awal_keperawatan_mata.total_hasil,penilaian_awal_keperawatan_mata.nyeri,penilaian_awal_keperawatan_mata.provokes,penilaian_awal_keperawatan_mata.ket_provokes,penilaian_awal_keperawatan_mata.quality,penilaian_awal_keperawatan_mata.ket_quality,penilaian_awal_keperawatan_mata.lokasi,penilaian_awal_keperawatan_mata.menyebar,"+
                        "penilaian_awal_keperawatan_mata.skala_nyeri,penilaian_awal_keperawatan_mata.durasi,penilaian_awal_keperawatan_mata.nyeri_hilang,penilaian_awal_keperawatan_mata.ket_nyeri,penilaian_awal_keperawatan_mata.pada_dokter,penilaian_awal_keperawatan_mata.ket_dokter,penilaian_awal_keperawatan_mata.rencana,penilaian_awal_keperawatan_mata.visuskanan,penilaian_awal_keperawatan_mata.visuskiri,penilaian_awal_keperawatan_mata.refraksikanan,penilaian_awal_keperawatan_mata.refraksikiri,"+
                        "penilaian_awal_keperawatan_mata.tiokanan,penilaian_awal_keperawatan_mata.tiokiri,penilaian_awal_keperawatan_mata.palberakanan,penilaian_awal_keperawatan_mata.palberakiri,penilaian_awal_keperawatan_mata.konjungtivakanan,penilaian_awal_keperawatan_mata.konjungtivakiri,penilaian_awal_keperawatan_mata.sklerakanan,penilaian_awal_keperawatan_mata.sklerakiri,penilaian_awal_keperawatan_mata.korneakanan,penilaian_awal_keperawatan_mata.korneakiri,penilaian_awal_keperawatan_mata.bmdkanan,penilaian_awal_keperawatan_mata.bmdkiri,"+
                        "penilaian_awal_keperawatan_mata.iriskanan,penilaian_awal_keperawatan_mata.iriskiri,penilaian_awal_keperawatan_mata.pupilkanan,penilaian_awal_keperawatan_mata.pupilkiri,penilaian_awal_keperawatan_mata.lensakanan,penilaian_awal_keperawatan_mata.lensakiri,penilaian_awal_keperawatan_mata.oftalmoskopikanan,penilaian_awal_keperawatan_mata.oftalmoskopikiri, "+
                        "penilaian_awal_keperawatan_mata.nip,petugas.nama,penilaian_awal_keperawatan_mata.budaya,penilaian_awal_keperawatan_mata.ket_budaya "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join penilaian_awal_keperawatan_mata on reg_periksa.no_rawat=penilaian_awal_keperawatan_mata.no_rawat "+
                        "inner join petugas on penilaian_awal_keperawatan_mata.nip=petugas.nip "+
                        "inner join bahasa_pasien on bahasa_pasien.id=pasien.bahasa_pasien "+
                        "inner join cacat_fisik on cacat_fisik.id=pasien.cacat_fisik where "+
                        "penilaian_awal_keperawatan_mata.tanggal between ? and ? order by penilaian_awal_keperawatan_mata.tanggal");
            }else{
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,pasien.agama,bahasa_pasien.nama_bahasa,cacat_fisik.nama_cacat,penilaian_awal_keperawatan_mata.tanggal,"+
                        "penilaian_awal_keperawatan_mata.informasi,penilaian_awal_keperawatan_mata.td,penilaian_awal_keperawatan_mata.nadi,penilaian_awal_keperawatan_mata.rr,penilaian_awal_keperawatan_mata.suhu,penilaian_awal_keperawatan_mata.bb,penilaian_awal_keperawatan_mata.tb,"+
                        "penilaian_awal_keperawatan_mata.nadi,penilaian_awal_keperawatan_mata.rr,penilaian_awal_keperawatan_mata.suhu,penilaian_awal_keperawatan_mata.gcs,penilaian_awal_keperawatan_mata.bb,penilaian_awal_keperawatan_mata.tb,penilaian_awal_keperawatan_mata.bmi,penilaian_awal_keperawatan_mata.keluhan_utama,"+
                        "penilaian_awal_keperawatan_mata.rpd,penilaian_awal_keperawatan_mata.rps,penilaian_awal_keperawatan_mata.rpk,penilaian_awal_keperawatan_mata.rpo,penilaian_awal_keperawatan_mata.alergi,penilaian_awal_keperawatan_mata.alat_bantu,penilaian_awal_keperawatan_mata.ket_bantu,penilaian_awal_keperawatan_mata.prothesa,"+
                        "penilaian_awal_keperawatan_mata.ket_pro,penilaian_awal_keperawatan_mata.adl,penilaian_awal_keperawatan_mata.status_psiko,penilaian_awal_keperawatan_mata.ket_psiko,penilaian_awal_keperawatan_mata.hub_keluarga,penilaian_awal_keperawatan_mata.tinggal_dengan,"+
                        "penilaian_awal_keperawatan_mata.ket_tinggal,penilaian_awal_keperawatan_mata.ekonomi,penilaian_awal_keperawatan_mata.edukasi,penilaian_awal_keperawatan_mata.ket_edukasi,penilaian_awal_keperawatan_mata.berjalan_a,penilaian_awal_keperawatan_mata.berjalan_b,"+
                        "penilaian_awal_keperawatan_mata.berjalan_c,penilaian_awal_keperawatan_mata.hasil,penilaian_awal_keperawatan_mata.lapor,penilaian_awal_keperawatan_mata.ket_lapor,penilaian_awal_keperawatan_mata.sg1,penilaian_awal_keperawatan_mata.nilai1,penilaian_awal_keperawatan_mata.sg2,penilaian_awal_keperawatan_mata.nilai2,penilaian_awal_keperawatan_mata.sg3,penilaian_awal_keperawatan_mata.nilai3,penilaian_awal_keperawatan_mata.sg4,penilaian_awal_keperawatan_mata.nilai4,"+
                        "penilaian_awal_keperawatan_mata.total_hasil,penilaian_awal_keperawatan_mata.nyeri,penilaian_awal_keperawatan_mata.provokes,penilaian_awal_keperawatan_mata.ket_provokes,penilaian_awal_keperawatan_mata.quality,penilaian_awal_keperawatan_mata.ket_quality,penilaian_awal_keperawatan_mata.lokasi,penilaian_awal_keperawatan_mata.menyebar,"+
                        "penilaian_awal_keperawatan_mata.skala_nyeri,penilaian_awal_keperawatan_mata.durasi,penilaian_awal_keperawatan_mata.nyeri_hilang,penilaian_awal_keperawatan_mata.ket_nyeri,penilaian_awal_keperawatan_mata.pada_dokter,penilaian_awal_keperawatan_mata.ket_dokter,penilaian_awal_keperawatan_mata.rencana,penilaian_awal_keperawatan_mata.visuskanan,penilaian_awal_keperawatan_mata.visuskiri,penilaian_awal_keperawatan_mata.refraksikanan,penilaian_awal_keperawatan_mata.refraksikiri,"+
                        "penilaian_awal_keperawatan_mata.tiokanan,penilaian_awal_keperawatan_mata.tiokiri,penilaian_awal_keperawatan_mata.palberakanan,penilaian_awal_keperawatan_mata.palberakiri,penilaian_awal_keperawatan_mata.konjungtivakanan,penilaian_awal_keperawatan_mata.konjungtivakiri,penilaian_awal_keperawatan_mata.sklerakanan,penilaian_awal_keperawatan_mata.sklerakiri,penilaian_awal_keperawatan_mata.korneakanan,penilaian_awal_keperawatan_mata.korneakiri,penilaian_awal_keperawatan_mata.bmdkanan,penilaian_awal_keperawatan_mata.bmdkiri,"+
                        "penilaian_awal_keperawatan_mata.iriskanan,penilaian_awal_keperawatan_mata.iriskiri,penilaian_awal_keperawatan_mata.pupilkanan,penilaian_awal_keperawatan_mata.pupilkiri,penilaian_awal_keperawatan_mata.lensakanan,penilaian_awal_keperawatan_mata.lensakiri,penilaian_awal_keperawatan_mata.oftalmoskopikanan,penilaian_awal_keperawatan_mata.oftalmoskopikiri, "+
                        "penilaian_awal_keperawatan_mata.nip,petugas.nama,penilaian_awal_keperawatan_mata.budaya,penilaian_awal_keperawatan_mata.ket_budaya "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join penilaian_awal_keperawatan_mata on reg_periksa.no_rawat=penilaian_awal_keperawatan_mata.no_rawat "+
                        "inner join petugas on penilaian_awal_keperawatan_mata.nip=petugas.nip "+
                        "inner join bahasa_pasien on bahasa_pasien.id=pasien.bahasa_pasien "+
                        "inner join cacat_fisik on cacat_fisik.id=pasien.cacat_fisik where "+
                        "penilaian_awal_keperawatan_mata.tanggal between ? and ? and "+
                        "(reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or "+
                        "penilaian_awal_keperawatan_mata.nip like ? or petugas.nama like ?) "+
                        "order by penilaian_awal_keperawatan_mata.tanggal");
            }
                
            try {
                if(TCari.getText().equals("")){
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
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("jk"),rs.getString("agama"),rs.getString("nama_bahasa"),rs.getString("nama_cacat"),
                        rs.getString("tgl_lahir"),rs.getString("tanggal"),rs.getString("informasi"),rs.getString("td"),rs.getString("nadi"),rs.getString("rr"),rs.getString("suhu"),
                        rs.getString("gcs"),rs.getString("bb"),rs.getString("tb"),rs.getString("bmi"),rs.getString("keluhan_utama"),rs.getString("rpd"),rs.getString("rps"),rs.getString("rpk"),rs.getString("rpo"),
                        rs.getString("alergi"),rs.getString("alat_bantu"),rs.getString("ket_bantu"),rs.getString("prothesa"),rs.getString("ket_pro"),rs.getString("adl"),rs.getString("status_psiko"),
                        rs.getString("ket_psiko"),rs.getString("hub_keluarga"),rs.getString("tinggal_dengan"),rs.getString("ket_tinggal"),rs.getString("ekonomi"),rs.getString("budaya"),
                        rs.getString("ket_budaya"),rs.getString("edukasi"),rs.getString("ket_edukasi"),rs.getString("berjalan_a"),rs.getString("berjalan_b"),rs.getString("berjalan_c"),
                        rs.getString("hasil"),rs.getString("lapor"),rs.getString("ket_lapor"),rs.getString("sg1"),rs.getString("nilai1"),rs.getString("sg2"),rs.getString("nilai2"),rs.getString("sg3"),rs.getString("nilai3"),rs.getString("sg4"),rs.getString("nilai4"),
                        rs.getString("total_hasil"),rs.getString("nyeri"),rs.getString("provokes"),rs.getString("ket_provokes"),rs.getString("quality"),rs.getString("ket_quality"),
                        rs.getString("lokasi"),rs.getString("menyebar"),rs.getString("skala_nyeri"),rs.getString("durasi"),rs.getString("nyeri_hilang"),rs.getString("ket_nyeri"),
                        rs.getString("pada_dokter"),rs.getString("ket_dokter"),rs.getString("visuskanan"),rs.getString("visuskiri"),rs.getString("refraksikanan"),rs.getString("refraksikiri"),rs.getString("tiokanan"),rs.getString("tiokiri"),
                        rs.getString("palberakanan"),rs.getString("palberakiri"),rs.getString("konjungtivakanan"),rs.getString("konjungtivakiri"),rs.getString("sklerakanan"),rs.getString("sklerakiri"),
                        rs.getString("korneakanan"),rs.getString("korneakiri"),rs.getString("bmdkanan"),rs.getString("bmdkiri"),rs.getString("iriskanan"),rs.getString("iriskiri"),rs.getString("pupilkanan"),rs.getString("pupilkiri"),rs.getString("lensakanan"),rs.getString("lensakiri"),
                        rs.getString("oftalmoskopikanan"),rs.getString("oftalmoskopikiri"),rs.getString("rencana"),rs.getString("nip"),rs.getString("nama")
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
        KeluhanUtama.setText("");
        RPK.setText("");
        RPD.setText("");
        RPS.setText("");
        RPO.setText("");
        Alergi.setText("");
        AlatBantu.setSelectedIndex(0);
        KetBantu.setText("");
        Prothesa.setSelectedIndex(0);
        KetProthesa.setText("");
        ADL.setSelectedIndex(0);
        StatusPsiko.setSelectedIndex(0);
        KetPsiko.setText("");
        HubunganKeluarga.setSelectedIndex(0);
        TinggalDengan.setSelectedIndex(0);
        KetTinggal.setText("");
        Ekonomi.setSelectedIndex(0);
        StatusBudaya.setSelectedIndex(0);
        KetBudaya.setText("");
        Edukasi.setSelectedIndex(0);
        KetEdukasi.setText("");
        ATS.setSelectedIndex(0);
        BJM.setSelectedIndex(0);
        MSA.setSelectedIndex(0);
        Hasil.setSelectedIndex(0);
        Lapor.setSelectedIndex(0);
        KetLapor.setText("");
        SG1.setSelectedIndex(0);
        Nilai1.setSelectedIndex(0);
        SG2.setSelectedIndex(0);
        Nilai2.setSelectedIndex(0);
        TotalHasil.setText("0");
        Nyeri.setSelectedIndex(0);
        Provokes.setSelectedIndex(0);
        KetProvokes.setText("");
        Quality.setSelectedIndex(0);
        KetQuality.setText("");
        Lokasi.setText("");
        Menyebar.setSelectedIndex(0);
        SkalaNyeri.setSelectedIndex(0);
        Durasi.setText("");
        NyeriHilang.setSelectedIndex(0);
        KetNyeri.setText("");
        PadaDokter.setSelectedIndex(0);
        KetDokter.setText("");
        VisusKanan.setText("");
        VisusKiri.setText("");
        RefraksiKanan.setText("");
        RefraksiKiri.setText("");
        TioKanan.setText("");
        TioKiri.setText("");
        PalberaKanan.setText("");
        PalberaKiri.setText("");
        KonjungtivaKanan.setText("");
        KonjungtivaKiri.setText("");
        SkleraKanan.setText("");
        SkleraKiri.setText("");
        KorneaKanan.setText("");
        KorneaKiri.setText("");
        BMDKanan.setText("");
        BMDKiri.setText("");
        IrisKanan.setText("");
        IrisKiri.setText("");
        PupilKanan.setText("");
        PupilKiri.setText("");
        LensaKanan.setText("");
        LensaKiri.setText("");
        OftalmoskopiKanan.setText("");
        OftalmoskopiKiri.setText("");
        Rencana.setText("");
        for (i = 0; i < tabModeMasalah.getRowCount(); i++) {
            tabModeMasalah.setValueAt(false,i,0);
        }
        TabRawat.setSelectedIndex(0);
        Informasi.requestFocus();
    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()); 
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString()); 
            Jk.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString()); 
            Agama.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString());
            Bahasa.setText(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
            CacatFisik.setText(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString());
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString()); 
            Informasi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            TD.setText(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            Nadi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            RR.setText(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            Suhu.setText(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            GCS.setText(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            BB.setText(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
            TB.setText(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            BMI.setText(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());
            KeluhanUtama.setText(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
            RPD.setText(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
            RPS.setText(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString());
            RPK.setText(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString());
            RPO.setText(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString());
            Alergi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString());
            AlatBantu.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),24).toString());
            KetBantu.setText(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString());
            Prothesa.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),26).toString());
            KetProthesa.setText(tbObat.getValueAt(tbObat.getSelectedRow(),27).toString());
            ADL.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),28).toString());
            StatusPsiko.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),29).toString());
            KetPsiko.setText(tbObat.getValueAt(tbObat.getSelectedRow(),30).toString());
            HubunganKeluarga.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),31).toString());
            TinggalDengan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),32).toString());
            KetTinggal.setText(tbObat.getValueAt(tbObat.getSelectedRow(),33).toString());
            Ekonomi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),34).toString());
            StatusBudaya.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),35).toString());
            KetBudaya.setText(tbObat.getValueAt(tbObat.getSelectedRow(),36).toString());
            Edukasi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),37).toString());
            KetEdukasi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),38).toString());
            ATS.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),39).toString());
            BJM.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),40).toString());
            MSA.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),41).toString());
            Hasil.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),42).toString());
            Lapor.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),43).toString());
            KetLapor.setText(tbObat.getValueAt(tbObat.getSelectedRow(),44).toString());
            SG1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),45).toString());
            Nilai1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),46).toString());
            SG2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),47).toString());
            Nilai2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),48).toString());
            TotalHasil.setText(tbObat.getValueAt(tbObat.getSelectedRow(),49).toString());
            Nyeri.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),50).toString());
            Provokes.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),51).toString());
            KetProvokes.setText(tbObat.getValueAt(tbObat.getSelectedRow(),52).toString());
            Quality.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),53).toString());
            KetQuality.setText(tbObat.getValueAt(tbObat.getSelectedRow(),54).toString());
            Lokasi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),55).toString());
            Menyebar.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),56).toString());
            SkalaNyeri.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),57).toString());
            Durasi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),58).toString());
            NyeriHilang.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),59).toString());
            KetNyeri.setText(tbObat.getValueAt(tbObat.getSelectedRow(),60).toString());
            PadaDokter.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),61).toString());
            KetLapor.setText(tbObat.getValueAt(tbObat.getSelectedRow(),62).toString());
            VisusKanan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),63).toString());
            VisusKiri.setText(tbObat.getValueAt(tbObat.getSelectedRow(),58).toString());
            RefraksiKanan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),65).toString());
            RefraksiKiri.setText(tbObat.getValueAt(tbObat.getSelectedRow(),66).toString());
            TioKanan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),67).toString());
            TioKiri.setText(tbObat.getValueAt(tbObat.getSelectedRow(),68).toString());
            PalberaKanan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),69).toString());
            PalberaKiri.setText(tbObat.getValueAt(tbObat.getSelectedRow(),70).toString());
            KonjungtivaKanan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),71).toString());
            KonjungtivaKiri.setText(tbObat.getValueAt(tbObat.getSelectedRow(),72).toString());
            SkleraKanan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),73).toString());
            SkleraKiri.setText(tbObat.getValueAt(tbObat.getSelectedRow(),74).toString());
            KorneaKanan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),75).toString());
            KorneaKiri.setText(tbObat.getValueAt(tbObat.getSelectedRow(),76).toString());
            BMDKanan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),77).toString());
            BMDKiri.setText(tbObat.getValueAt(tbObat.getSelectedRow(),78).toString());
            IrisKanan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),79).toString());
            IrisKiri.setText(tbObat.getValueAt(tbObat.getSelectedRow(),80).toString());
            PupilKanan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),81).toString());
            PupilKiri.setText(tbObat.getValueAt(tbObat.getSelectedRow(),82).toString());
            LensaKanan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),83).toString());
            LensaKiri.setText(tbObat.getValueAt(tbObat.getSelectedRow(),84).toString());
            OftalmoskopiKanan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),85).toString());
            OftalmoskopiKiri.setText(tbObat.getValueAt(tbObat.getSelectedRow(),86).toString());
            Valid.SetTgl2(TglAsuhan,tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            
            try {
                Valid.tabelKosong(tabModeMasalah);
                
                ps=koneksi.prepareStatement(
                        "select master_masalah_keperawatan_mata.kode_masalah,master_masalah_keperawatan_mata.nama_masalah from master_masalah_keperawatan_mata "+
                        "inner join penilaian_awal_keperawatan_mata_masalah on penilaian_awal_keperawatan_mata_masalah.kode_masalah=master_masalah_keperawatan_mata.kode_masalah "+
                        "where penilaian_awal_keperawatan_mata_masalah.no_rawat=? order by kode_masalah");
                try {
                    ps.setString(1,tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
                    rs=ps.executeQuery();
                    while(rs.next()){
                        tabModeMasalah.addRow(new Object[]{true,rs.getString(1),rs.getString(2)});
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

    private void isRawat() {
        try {
            ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rkm_medis,pasien.nm_pasien, if(pasien.jk='L','Laki-Laki','Perempuan') as jk,"+
                    "pasien.tgl_lahir,pasien.agama,bahasa_pasien.nama_bahasa,cacat_fisik.nama_cacat,reg_periksa.tgl_registrasi "+
                    "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join bahasa_pasien on bahasa_pasien.id=pasien.bahasa_pasien "+
                    "inner join cacat_fisik on cacat_fisik.id=pasien.cacat_fisik "+
                    "where reg_periksa.no_rawat=?");
            try {
                ps.setString(1,TNoRw.getText());
                rs=ps.executeQuery();
                if(rs.next()){
                    TNoRM.setText(rs.getString("no_rkm_medis"));
                    TPasien.setText(rs.getString("nm_pasien"));
                    DTPCari1.setDate(rs.getDate("tgl_registrasi"));
                    Jk.setText(rs.getString("jk"));
                    TglLahir.setText(rs.getString("tgl_lahir"));
                    Agama.setText(rs.getString("agama"));
                    Bahasa.setText(rs.getString("nama_bahasa"));
                    CacatFisik.setText(rs.getString("nama_cacat"));
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
        DTPCari2.setDate(tgl2);    
        isRawat(); 
    }
    
    
    public void isCek(){
        BtnSimpan.setEnabled(akses.getpenilaian_awal_keperawatan_mata());
        BtnHapus.setEnabled(akses.getpenilaian_awal_keperawatan_mata());
        BtnEdit.setEnabled(akses.getpenilaian_awal_keperawatan_mata());
        BtnEdit.setEnabled(akses.getpenilaian_awal_keperawatan_mata());
        BtnTambahMasalah.setEnabled(akses.getmaster_masalah_keperawatan_mata());  
        if(akses.getjml2()>=1){
            KdPetugas.setEditable(false);
            BtnPetugas.setEnabled(false);
            KdPetugas.setText(akses.getkode());
            NmPetugas.setText(Sequel.CariPetugas(KdPetugas.getText()));
            if(NmPetugas.getText().equals("")){
                KdPetugas.setText("");
                JOptionPane.showMessageDialog(null,"User login bukan petugas...!!");
            }
        }            
    }

    public void setTampil(){
       TabRawat.setSelectedIndex(1);
    }
    
    private void tampilMasalah() {
        try{
            jml=0;
            for(i=0;i<tbMasalahKeperawatan.getRowCount();i++){
                if(tbMasalahKeperawatan.getValueAt(i,0).toString().equals("true")){
                    jml++;
                }
            }

            pilih=null;
            pilih=new boolean[jml]; 
            kode=null;
            kode=new String[jml];
            masalah=null;
            masalah=new String[jml];

            index=0;        
            for(i=0;i<tbMasalahKeperawatan.getRowCount();i++){
                if(tbMasalahKeperawatan.getValueAt(i,0).toString().equals("true")){
                    pilih[index]=true;
                    kode[index]=tbMasalahKeperawatan.getValueAt(i,1).toString();
                    masalah[index]=tbMasalahKeperawatan.getValueAt(i,2).toString();
                    index++;
                }
            } 

            Valid.tabelKosong(tabModeMasalah);

            for(i=0;i<jml;i++){
                tabModeMasalah.addRow(new Object[] {
                    pilih[i],kode[i],masalah[i]
                });
            }
            ps=koneksi.prepareStatement("select * from master_masalah_keperawatan_mata where kode_masalah like ? or nama_masalah like ? order by kode_masalah");
            try {
                ps.setString(1,"%"+TCariMasalah.getText().trim()+"%");
                ps.setString(2,"%"+TCariMasalah.getText().trim()+"%");
                rs=ps.executeQuery();
                while(rs.next()){
                    tabModeMasalah.addRow(new Object[]{false,rs.getString(1),rs.getString(2)});
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
            DetailRencana.setText(tbObat.getValueAt(tbObat.getSelectedRow(),62).toString());
            try {
                Valid.tabelKosong(tabModeDetailMasalah);
                ps=koneksi.prepareStatement(
                        "select master_masalah_keperawatan_mata.kode_masalah,master_masalah_keperawatan_mata.nama_masalah from master_masalah_keperawatan_mata "+
                        "inner join penilaian_awal_keperawatan_mata_masalah on penilaian_awal_keperawatan_mata_masalah.kode_masalah=master_masalah_keperawatan_mata.kode_masalah "+
                        "where penilaian_awal_keperawatan_mata_masalah.no_rawat=? order by kode_masalah");
                try {
                    ps.setString(1,tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
                    rs=ps.executeQuery();
                    while(rs.next()){
                        tabModeDetailMasalah.addRow(new Object[]{rs.getString(1),rs.getString(2)});
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
    
    private void isBMI(){
        if((!TB.getText().equals(""))&&(!BB.getText().equals(""))){
            BMI.setText(Valid.SetAngka8(Valid.SetAngka(BB.getText())/((Valid.SetAngka(TB.getText())/100)*(Valid.SetAngka(TB.getText())/100)),1)+"");
        }
    }
    
    private void runBackground(Runnable task) {
        if (ceksukses) return;
        if (executor.isShutdown() || executor.isTerminated()) return;
        if (!isDisplayable()) return;

        ceksukses = true;
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        try {
            executor.submit(() -> {
                try {
                    task.run();
                } finally {
                    ceksukses = false;
                    SwingUtilities.invokeLater(() -> {
                        if (isDisplayable()) {
                            setCursor(Cursor.getDefaultCursor());
                        }
                    });
                }
            });
        } catch (RejectedExecutionException ex) {
            ceksukses = false;
        }
    }
    
    @Override
    public void dispose() {
        executor.shutdownNow();
        super.dispose();
    }
}

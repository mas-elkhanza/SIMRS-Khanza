package rekammedis;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.io.FileReader;
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
import kepegawaian.DlgCariDokter;
import kepegawaian.DlgCariPetugas;


/**
 *
 * @author perpustakaan
 */
public final class RMPenilaianAwalKeperawatanRanapNeonatus extends javax.swing.JDialog {
    private final DefaultTableModel tabMode,tabModeMasalah,tabModeDetailMasalah,tabModeRencana,tabModeDetailRencana;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0,jml=0,index=0;
    private DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    private DlgCariDokter dokter=new DlgCariDokter(null,false);
    private StringBuilder htmlContent;
    private String pilihan="";
    private boolean[] pilih; 
    private String[] kode,masalah;
    private String masalahkeperawatan="",finger=""; 
    private File file;
    private FileWriter fileWriter;
    private String iyem;
    private ObjectMapper mapper = new ObjectMapper();
    private JsonNode root;
    private JsonNode response;
    private FileReader myObj;
    
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public RMPenilaianAwalKeperawatanRanapNeonatus(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.RM","Nama Pasien","Tgl.Lahir","J.K.","NIP Pengkaji 1","Nama Pengkaji 1","NIP Pengkaji 2","Nama Pengkaji 2","Kode DPJP","Nama DPJP",
            "Tgl.Asuhan","Asal Pasien","Cara Masuk","Diperoleh Dari","Hubungan Dengan Pasien","Keluhan Utama","Prenatal G","Prenatal P","Prenatal A","Prenatal UK",
            "Riwayat Penyakit Ibu","Keterangan Riwayat Penyakit Ibu","Riwayat Pengobatan Ibu Selama Hamil","Pernah Dirawat","Keterangan Pernah Dirawat","Status Gizi Ibu",
            "Intranatal G","Intranatal P","Intranatal A","Kondisi Lahir","Cara Persalinan","Keterangan Cara Persalinan","APGAR Score","Intranatal Letak","Tali Pusat",
            "Ketuban","BB(gr)","PB(cm)","LK(cm)","LD(cm)","LP(cm)","Risiko Infeksi Mayor","Keterangan Risiko Infeksi Mayor","Risiko Infeksi Minor","Keterangan Risiko Infeksi Minor",
            "Nutrisi","Keterangan Nutrisi","Frekuensi(cc)","Frekuenasi(x)","Eliminasi BAK","Keterangan Eliminasi BAK","Eliminasi BAB","Keterangan Eliminasi BAB","Alergi Obat",
            "Keterangan Alergi Obat","Reaksi Alergi Obat","Alergi Makanan","Keterangan Alergi Makanan","Reaksi Alergi Makanan","Alergi Lainnya","Keterangan Alergi Lainnya",
            "Reaksi Alergi Lainnya","Riwayat Penyakit Keluarga","Keterangan Riwayat Penyakit Keluarga","Riwayat Imunisasi","Keterangan Riwayat Imunisasi","Riwayat Tranfusi Darah",
            "Keterangan Riwayat Tranfusi Darah","Reaksi Tranfusi Darah","Keterangan Reaksi Tranfusi Darah","Kebiasan Minum Obat","Keterangan Kebiasan Minum Obat",
            "Kebiasan Obat Tidur/Narkoba","Keterangan Obat Tidur/Narkoba","Kebiasan Merokok","Keterangan Kebiasan Merokok","Kebiasan Alkohol","Keterangan Kebiasan Alkohol",
            "Kesadaran","Keadaan Umum","GCS","TD(mmHg)","Suhu(°C)","HR(x/menit)","RR(x/menit)","SPO2(%)","Down Score","BB(Kg)","TB(cm)","LK(cm)","LD(cm)","LP(cm)","GD Bayi",
            "GD Ibu","GD Ayah","Gerak Bayi","Kepala","Keterangan Kepala","Ubun-ubun","Keterangan Ubun-ubun","Wajah","Keterangan Wajah","Kejang","Keterangan Kejang",
            "Refleks","Keterangan Refleks","Tangis Bayi","Keterangan Tangis Bayi","Denyut Nadi","Sirkulasi","Keterangan Sirkulasi","Pulsasi","Keterangan Pulsasi","Pola Napas",
            "Jenis Pernapasan","Keterangan Jenis Pernapasan","Retraksi","Air Entry","Merintih","Suara Napas","Mulut","Keterangan Mulut","Lidah","Keterangan Lidah","Tenggorakan",
            "Keterangan Tenggorokan","Abdomen","Keterangan Abdomen","BAB","Keterangan BAB","Warna BAB","Keterangan Warna BAB","BAK","Keterangan BAK","Warna BAK","Keterangan Warna BAK",
            "Posisi Mata","Kelopak Mata","Keterangan Kelopak Mata","Besar Pupil","Konjugtiva","Keterangan Konjugtiva","Sklera","Keterangan Sklera","Pendengaran","Keterangan Pendengaran",
            "Penciuman","Keterangan Penciuman","Warna Kulit","Keterangan Warna Kulit","Vernic Kaseosa","Keterangan Vernic Kaseosa","Turgor","Lanugo","Kulit","Kriteria Risiko Dekubitas",
            "Reproduksi","Keterangan Reproduksi","Rekoil Telinga","Keterangan Rekoil Telinga","Lengan","Keterangan Lengan","Tungkai","Keterangan Tungkai","Telapak Kaki",
            "Kondisi Psikologis","Gangguan Jiwa Di Masa Lalu","Menerima Kondisi Bayi","Status Menikah","Masalah Pernikahan","Keterangan Masalah Pernikahan","Pekerjaan",
            "Agama","Nilai Kepercayaan","Keterangan Nilai Kepercayaan","Suku","Pendidikan","Pembayaran","Tinggal Bersama","Keterangan Tinggal Bersama","Hubungan Keluarga",
            "Respon Emosi","Bahasa Sehari-hari","Baca & Tulis","Butuh Penerjemah","Keterangan Butuh Penerjemah","Terdapat Hambatan Belajar","Hambatan Belajar","Keterangan Hambatan Belajar",
            "Hambatan Cara Bicara","Hambatan Bahasa Isyarat","Cara Belajar Disukai","Kesediaan Menerima Informasi","Keterangan Kesediaan Menerima Informasi","Pemahaman Nutrisi",
            "Pemahaman Penyakit","Pemahaman Pengobatan","Pemahaman Perawatan","Masalah Gizi 1","N.Gizi 1","Masalah Gizi 2","N.Gizi 2","Masalah Gizi 3","N.Gizi 3","Total N.Gizi",
            "Keterangan Skrining Gizi","Humpty Dumpty Skala 1","Nilai H.D.1","Humpty Dumpty Skala 2","Nilai H.D.2","Humpty Dumpty Skala 3","Nilai H.D.3","Humpty Dumpty Skala 4",
            "Nilai H.D.4","Humpty Dumpty Skala 5","Nilai H.D.5","Humpty Dumpty Skala 6","Nilai H.D.6","Humpty Dumpty Skala 7","Nilai H.D.7","Total Nilai H.D.",
            "Keterangan Hasil Penilaian H.D.","Skala NIPS 1","Nilai NIPS 1","Skala NIPS 2","Nilai NIPS 2","Skala NIPS 3","Nilai NIPS 3","Skala NIPS 4","Nilai NIPS 4",
            "Skala NIPS 5","Nilai NIPS 5","Total NIPS","Keterangan Penilaian NIPS","Informasi Perencanaan Pulang","Rawat Rata-rata","Perencanaan Pulang","Kondisi Klinis Pulang",
            "Perawatan Lanjutan Dirumah","Cara Transportasi Pulang","Transportasi Digunakan","Rencana Keperawatan Lainnya"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 175; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(105);
            }else if(i==1){
                column.setPreferredWidth(65);
            }else if(i==2){
                column.setPreferredWidth(160);
            }else if(i==3){
                column.setPreferredWidth(65);
            }else if(i==4){
                column.setPreferredWidth(25);
            }else if(i==5){
                column.setPreferredWidth(85);
            }else if(i==6){
                column.setPreferredWidth(150);
            }else if(i==7){
                column.setPreferredWidth(85);
            }else if(i==8){
                column.setPreferredWidth(150);
            }else if(i==9){
                column.setPreferredWidth(90);
            }else if(i==10){
                column.setPreferredWidth(150);
            }else if(i==11){
                column.setPreferredWidth(117);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());
        
        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        DiperolehDari.setDocument(new batasInput((byte)30).getKata(DiperolehDari));
        Rencana.setDocument(new batasInput((int)200).getKata(Rencana));
        
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
            
            TCariMasalah.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(TCariMasalah.getText().length()>2){
                        tampilMasalah2();
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(TCariMasalah.getText().length()>2){
                        tampilMasalah2();
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(TCariMasalah.getText().length()>2){
                        tampilMasalah2();
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
                    if(i==1){
                        KdPetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                        NmPetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());  
                    }else{
                        KdPetugas2.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                        NmPetugas2.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());  
                    }
                         
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
        
        dokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(dokter.getTable().getSelectedRow()!= -1){ 
                    KdDPJP.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                    NmDPJP.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());  
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
        
        tabModeRencana=new DefaultTableModel(null,new Object[]{
                "P","KODE","RENCANA KEPERAWATAN"
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
        tbRencanaKeperawatan.setModel(tabModeRencana);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbRencanaKeperawatan.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbRencanaKeperawatan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        
        for (i = 0; i < 3; i++) {
            TableColumn column = tbRencanaKeperawatan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==2){
                column.setPreferredWidth(350);
            }
        }
        tbRencanaKeperawatan.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeDetailMasalah=new DefaultTableModel(null,new Object[]{
                "Kode","Masalah Keperawatan"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbMasalahDetail.setModel(tabModeDetailMasalah);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbMasalahDetail.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbMasalahDetail.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 2; i++) {
            TableColumn column = tbMasalahDetail.getColumnModel().getColumn(i);
            if(i==0){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==1){
                column.setPreferredWidth(420);
            }
        }
        tbMasalahDetail.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeDetailRencana=new DefaultTableModel(null,new Object[]{
                "Kode","Rencana Keperawatan"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbRencanaDetail.setModel(tabModeDetailRencana);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbRencanaDetail.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbRencanaDetail.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 2; i++) {
            TableColumn column = tbRencanaDetail.getColumnModel().getColumn(i);
            if(i==0){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==1){
                column.setPreferredWidth(420);
            }
        }
        tbRencanaDetail.setDefaultRenderer(Object.class, new WarnaTable());
        
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
        TglLahir = new widget.TextBox();
        Jk = new widget.TextBox();
        jLabel10 = new widget.Label();
        label11 = new widget.Label();
        jLabel11 = new widget.Label();
        jLabel36 = new widget.Label();
        CaraMasuk = new widget.ComboBox();
        TglAsuhan = new widget.Tanggal();
        NmPetugas2 = new widget.TextBox();
        BtnPetugas2 = new widget.Button();
        KdPetugas2 = new widget.TextBox();
        label15 = new widget.Label();
        label16 = new widget.Label();
        KdDPJP = new widget.TextBox();
        NmDPJP = new widget.TextBox();
        BtnDPJP = new widget.Button();
        jSeparator2 = new javax.swing.JSeparator();
        AsalPasien = new widget.ComboBox();
        jLabel41 = new widget.Label();
        DiperolehDari = new widget.TextBox();
        jSeparator11 = new javax.swing.JSeparator();
        jLabel271 = new widget.Label();
        jSeparator12 = new javax.swing.JSeparator();
        Scroll6 = new widget.ScrollPane();
        tbMasalahKeperawatan = new widget.Table();
        TabRencanaKeperawatan = new javax.swing.JTabbedPane();
        panelBiasa1 = new widget.PanelBiasa();
        Scroll8 = new widget.ScrollPane();
        tbRencanaKeperawatan = new widget.Table();
        scrollPane5 = new widget.ScrollPane();
        Rencana = new widget.TextArea();
        BtnTambahMasalah = new widget.Button();
        BtnAllMasalah = new widget.Button();
        BtnCariMasalah = new widget.Button();
        TCariMasalah = new widget.TextBox();
        BtnTambahRencana = new widget.Button();
        BtnAllRencana = new widget.Button();
        BtnCariRencana = new widget.Button();
        label13 = new widget.Label();
        TCariRencana = new widget.TextBox();
        label12 = new widget.Label();
        label17 = new widget.Label();
        HubunganDenganPasien = new widget.TextBox();
        label18 = new widget.Label();
        jLabel94 = new widget.Label();
        jLabel30 = new widget.Label();
        jLabel200 = new widget.Label();
        scrollPane1 = new widget.ScrollPane();
        KeluhanUtama = new widget.TextArea();
        jLabel31 = new widget.Label();
        jLabel32 = new widget.Label();
        jLabel101 = new widget.Label();
        PrenatalG = new widget.TextBox();
        jLabel102 = new widget.Label();
        PrenatalP = new widget.TextBox();
        jLabel103 = new widget.Label();
        PrenatalA = new widget.TextBox();
        jLabel104 = new widget.Label();
        PrenatalUK = new widget.TextBox();
        jLabel42 = new widget.Label();
        RiwayatPenyakitIbu = new widget.ComboBox();
        KeteranganRiwayatPenyakitIbu = new widget.TextBox();
        jLabel35 = new widget.Label();
        jLabel37 = new widget.Label();
        KetAnamnesis3 = new widget.TextBox();
        jLabel38 = new widget.Label();
        RiwayatPengobatanIbu = new widget.TextBox();
        PernahDirawat = new widget.ComboBox();
        jLabel43 = new widget.Label();
        KeteranganPernahDirawat = new widget.TextBox();
        jLabel39 = new widget.Label();
        jLabel44 = new widget.Label();
        StatusGiziIbu = new widget.ComboBox();
        jLabel33 = new widget.Label();
        jLabel40 = new widget.Label();
        jLabel105 = new widget.Label();
        IntranatalG = new widget.TextBox();
        jLabel106 = new widget.Label();
        IntranatalP = new widget.TextBox();
        jLabel107 = new widget.Label();
        IntranatalA = new widget.TextBox();
        jLabel45 = new widget.Label();
        label19 = new widget.Label();
        label20 = new widget.Label();
        KondisiSaatLahir = new widget.TextBox();
        jLabel46 = new widget.Label();
        jLabel47 = new widget.Label();
        KeteranganCaraPersalinan = new widget.TextBox();
        CaraPersalinan = new widget.ComboBox();
        jLabel108 = new widget.Label();
        ApgarScore = new widget.TextBox();
        label21 = new widget.Label();
        IntranatalLetak = new widget.TextBox();
        Ketuban = new widget.ComboBox();
        jLabel48 = new widget.Label();
        jLabel49 = new widget.Label();
        jLabel50 = new widget.Label();
        TaliPusat = new widget.ComboBox();
        jLabel51 = new widget.Label();
        jLabel109 = new widget.Label();
        AntoBB = new widget.TextBox();
        jLabel110 = new widget.Label();
        jLabel111 = new widget.Label();
        AntoPB = new widget.TextBox();
        jLabel112 = new widget.Label();
        jLabel113 = new widget.Label();
        jLabel114 = new widget.Label();
        AntoLK = new widget.TextBox();
        jLabel115 = new widget.Label();
        AntoLD = new widget.TextBox();
        jLabel116 = new widget.Label();
        jLabel117 = new widget.Label();
        AntoLP = new widget.TextBox();
        jLabel118 = new widget.Label();
        jLabel52 = new widget.Label();
        jLabel54 = new widget.Label();
        jLabel53 = new widget.Label();
        RisikoInfeksiMayor = new widget.ComboBox();
        KeteranganRisikoInfeksiMayor = new widget.TextBox();
        RisikoInfeksiMinor = new widget.ComboBox();
        KeteranganRisikoInfeksiMinor = new widget.TextBox();
        label22 = new widget.Label();
        jLabel55 = new widget.Label();
        jLabel57 = new widget.Label();
        Nutrisi = new widget.ComboBox();
        jLabel56 = new widget.Label();
        KeteranganNutrisi = new widget.TextBox();
        NutrisiFrekuensi = new widget.TextBox();
        NutrisiKali = new widget.TextBox();
        jLabel120 = new widget.Label();
        jLabel119 = new widget.Label();
        jLabel122 = new widget.Label();
        label23 = new widget.Label();
        EliminasiBAK = new widget.ComboBox();
        KeteranganEliminasiBAK = new widget.TextBox();
        jLabel58 = new widget.Label();
        label24 = new widget.Label();
        EliminasiBAB = new widget.ComboBox();
        KeteranganEliminasiBAB = new widget.TextBox();
        jLabel121 = new widget.Label();
        jLabel123 = new widget.Label();
        jLabel59 = new widget.Label();
        jLabel60 = new widget.Label();
        jLabel61 = new widget.Label();
        AlergiObat = new widget.ComboBox();
        KeteranganAlergiObat = new widget.TextBox();
        label25 = new widget.Label();
        ReaksiAlergiObat = new widget.TextBox();
        jLabel62 = new widget.Label();
        jLabel63 = new widget.Label();
        AlergiMakanan = new widget.ComboBox();
        KeteranganAlergiMakanan = new widget.TextBox();
        label26 = new widget.Label();
        ReaksiAlergiMakanan = new widget.TextBox();
        jLabel64 = new widget.Label();
        jLabel65 = new widget.Label();
        AlergiLainnya = new widget.ComboBox();
        KeteranganAlergiLainnya = new widget.TextBox();
        label27 = new widget.Label();
        ReaksiAlergiLainnya = new widget.TextBox();
        jLabel66 = new widget.Label();
        jLabel67 = new widget.Label();
        RiwayatPenyakitKeluarga = new widget.ComboBox();
        KeteranganRiwayatPenyakitKeluarga = new widget.TextBox();
        jLabel68 = new widget.Label();
        jLabel69 = new widget.Label();
        RiwayatImunisasi = new widget.ComboBox();
        KeteranganRiwayatImunisasi = new widget.TextBox();
        TranfusiDarah = new widget.ComboBox();
        jLabel70 = new widget.Label();
        jLabel71 = new widget.Label();
        KeteranganTranfusiDarah = new widget.TextBox();
        label28 = new widget.Label();
        ReaksiTranfusiDarah = new widget.ComboBox();
        KeteranganReaksiTranfusiDarah = new widget.TextBox();
        jLabel72 = new widget.Label();
        jLabel73 = new widget.Label();
        jLabel74 = new widget.Label();
        ObatobatanDiminum = new widget.ComboBox();
        KeteranganObatobatanDiminum = new widget.TextBox();
        jLabel75 = new widget.Label();
        Merokok = new widget.ComboBox();
        jLabel124 = new widget.Label();
        JumlahMerokok = new widget.TextBox();
        jLabel125 = new widget.Label();
        jLabel126 = new widget.Label();
        jLabel76 = new widget.Label();
        jLabel77 = new widget.Label();
        ObatTidurNarkoba = new widget.ComboBox();
        KeteranganObatTidurNarkoba = new widget.TextBox();
        jLabel78 = new widget.Label();
        Alkohol = new widget.ComboBox();
        jLabel127 = new widget.Label();
        JumlahAlkohol = new widget.TextBox();
        jLabel128 = new widget.Label();
        jLabel129 = new widget.Label();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel95 = new widget.Label();
        jLabel130 = new widget.Label();
        KeadaanUmum = new widget.ComboBox();
        jLabel28 = new widget.Label();
        FisikGCS = new widget.TextBox();
        jLabel22 = new widget.Label();
        FisikTD = new widget.TextBox();
        jLabel23 = new widget.Label();
        jLabel201 = new widget.Label();
        ComposMentis = new widget.ComboBox();
        jLabel80 = new widget.Label();
        jLabel18 = new widget.Label();
        FisikSuhu = new widget.TextBox();
        jLabel20 = new widget.Label();
        jLabel17 = new widget.Label();
        FisikHR = new widget.TextBox();
        jLabel16 = new widget.Label();
        jLabel26 = new widget.Label();
        FisikRR = new widget.TextBox();
        jLabel25 = new widget.Label();
        jLabel29 = new widget.Label();
        FisikSPO = new widget.TextBox();
        jLabel79 = new widget.Label();
        jLabel12 = new widget.Label();
        FisikBB = new widget.TextBox();
        jLabel13 = new widget.Label();
        jLabel15 = new widget.Label();
        FisikTB = new widget.TextBox();
        jLabel81 = new widget.Label();
        jLabel82 = new widget.Label();
        FisikDownScore = new widget.TextBox();
        jLabel83 = new widget.Label();
        jLabel131 = new widget.Label();
        FisikLK = new widget.TextBox();
        jLabel132 = new widget.Label();
        FisikLD = new widget.TextBox();
        jLabel134 = new widget.Label();
        jLabel135 = new widget.Label();
        FisikLP = new widget.TextBox();
        jLabel136 = new widget.Label();
        jLabel84 = new widget.Label();
        jLabel24 = new widget.Label();
        jLabel133 = new widget.Label();
        GDBayi = new widget.ComboBox();
        jLabel137 = new widget.Label();
        GDIbu = new widget.ComboBox();
        jLabel138 = new widget.Label();
        GDAyah = new widget.ComboBox();
        jLabel85 = new widget.Label();
        jLabel86 = new widget.Label();
        jLabel87 = new widget.Label();
        GerakBayi = new widget.ComboBox();
        jLabel139 = new widget.Label();
        KepalaBayi = new widget.ComboBox();
        KeteranganKepalaBayi = new widget.TextBox();
        Ubunubun = new widget.ComboBox();
        jLabel140 = new widget.Label();
        KeteranganUbunubun = new widget.TextBox();
        jLabel88 = new widget.Label();
        jLabel89 = new widget.Label();
        Wajah = new widget.ComboBox();
        KeteranganWajah = new widget.TextBox();
        jLabel141 = new widget.Label();
        Kejang = new widget.ComboBox();
        KeteranganKejang = new widget.TextBox();
        jLabel90 = new widget.Label();
        jLabel91 = new widget.Label();
        Refleks = new widget.ComboBox();
        KeteranganRefleks = new widget.TextBox();
        jLabel142 = new widget.Label();
        TangisBayi = new widget.ComboBox();
        KeteranganTangisBayi = new widget.TextBox();
        jLabel92 = new widget.Label();
        jLabel93 = new widget.Label();
        jLabel96 = new widget.Label();
        DenyutNadi = new widget.ComboBox();
        jLabel143 = new widget.Label();
        Sirkulasi = new widget.ComboBox();
        KeteranganSirkulasi = new widget.TextBox();
        Pulsasi = new widget.ComboBox();
        jLabel144 = new widget.Label();
        KeteranganPulsasi = new widget.TextBox();
        jLabel97 = new widget.Label();
        jLabel98 = new widget.Label();
        jLabel99 = new widget.Label();
        PolaNapas = new widget.ComboBox();
        jLabel145 = new widget.Label();
        JenisPernapasan = new widget.ComboBox();
        KeteranganJenisPernapasan = new widget.TextBox();
        Retraksi = new widget.ComboBox();
        jLabel146 = new widget.Label();
        jLabel100 = new widget.Label();
        jLabel147 = new widget.Label();
        AirEntry = new widget.ComboBox();
        jLabel148 = new widget.Label();
        Merintih = new widget.ComboBox();
        SuaraNapas = new widget.ComboBox();
        jLabel149 = new widget.Label();
        jLabel150 = new widget.Label();
        Mulut = new widget.ComboBox();
        jLabel151 = new widget.Label();
        jLabel152 = new widget.Label();
        KeteranganMulut = new widget.TextBox();
        jLabel153 = new widget.Label();
        Lidah = new widget.ComboBox();
        KeteranganLidah = new widget.TextBox();
        Tenggorokan = new widget.ComboBox();
        jLabel154 = new widget.Label();
        jLabel155 = new widget.Label();
        KeteranganTenggorokan = new widget.TextBox();
        jLabel156 = new widget.Label();
        Abdomen = new widget.ComboBox();
        KeteranganAbdomen = new widget.TextBox();
        jLabel157 = new widget.Label();
        GastroBAB = new widget.ComboBox();
        jLabel158 = new widget.Label();
        KeteranganGastroBAB = new widget.TextBox();
        jLabel159 = new widget.Label();
        GastroWarnaBAB = new widget.ComboBox();
        KeteranganGastroWarnaBAB = new widget.TextBox();
        jLabel160 = new widget.Label();
        GastroBAK = new widget.ComboBox();
        jLabel161 = new widget.Label();
        KeteranganGastroBAK = new widget.TextBox();
        jLabel162 = new widget.Label();
        GastroWarnaBAK = new widget.ComboBox();
        KeteranganGastroWarnaBAK = new widget.TextBox();
        jLabel163 = new widget.Label();
        jLabel164 = new widget.Label();
        jLabel165 = new widget.Label();
        PosisiMata = new widget.ComboBox();
        jLabel166 = new widget.Label();
        KelopakMata = new widget.ComboBox();
        KeteranganKelopakMata = new widget.TextBox();
        BesarPupil = new widget.ComboBox();
        jLabel167 = new widget.Label();
        Konjungtiva = new widget.ComboBox();
        jLabel168 = new widget.Label();
        jLabel169 = new widget.Label();
        KeteranganKonjungtiva = new widget.TextBox();
        jLabel170 = new widget.Label();
        Sklera = new widget.ComboBox();
        KeteranganSklera = new widget.TextBox();
        Pendengaran = new widget.ComboBox();
        jLabel171 = new widget.Label();
        jLabel172 = new widget.Label();
        KeteranganPendengaran = new widget.TextBox();
        jLabel173 = new widget.Label();
        Penciuman = new widget.ComboBox();
        KeteranganPenciuman = new widget.TextBox();
        jLabel174 = new widget.Label();
        jLabel176 = new widget.Label();
        WarnaKulit = new widget.ComboBox();
        jLabel175 = new widget.Label();
        jLabel177 = new widget.Label();
        VernicKaseosa = new widget.ComboBox();
        Lanugo = new widget.ComboBox();
        jLabel178 = new widget.Label();
        KeteranganVernicKaseosa = new widget.TextBox();
        KeteranganWarnaKulit = new widget.TextBox();
        Turgor = new widget.ComboBox();
        jLabel180 = new widget.Label();
        jLabel179 = new widget.Label();
        jLabel181 = new widget.Label();
        Kulit = new widget.ComboBox();
        RisikoDekubitas = new widget.ComboBox();
        jLabel182 = new widget.Label();
        jLabel183 = new widget.Label();
        jLabel202 = new widget.Label();
        Reproduksi = new widget.ComboBox();
        KeteranganReproduksi = new widget.TextBox();
        jLabel184 = new widget.Label();
        RekoilTelinga = new widget.ComboBox();
        jLabel185 = new widget.Label();
        jLabel186 = new widget.Label();
        jLabel187 = new widget.Label();
        Lengan = new widget.ComboBox();
        KeteranganLengan = new widget.TextBox();
        KeteranganRekoilTelinga = new widget.TextBox();
        Tungkai = new widget.ComboBox();
        jLabel188 = new widget.Label();
        jLabel189 = new widget.Label();
        jLabel190 = new widget.Label();
        GarisTelapakKaki = new widget.ComboBox();
        KeteranganTungkai = new widget.TextBox();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel191 = new widget.Label();
        jLabel203 = new widget.Label();
        KondisiPsikologis = new widget.ComboBox();
        jLabel192 = new widget.Label();
        jLabel193 = new widget.Label();
        MenerimaKondisiBayi = new widget.ComboBox();
        jLabel194 = new widget.Label();
        GangguanJiwa = new widget.ComboBox();
        jLabel204 = new widget.Label();
        StatusMenikah = new widget.ComboBox();
        jLabel195 = new widget.Label();
        jLabel196 = new widget.Label();
        MasalahPernikahan = new widget.ComboBox();
        KeteranganMasalahPernikahan = new widget.TextBox();
        KeteranganTinggalBersama = new widget.TextBox();
        jLabel197 = new widget.Label();
        Agama = new widget.TextBox();
        jLabel198 = new widget.Label();
        jLabel205 = new widget.Label();
        jLabel199 = new widget.Label();
        NilaiKepercayaan = new widget.ComboBox();
        KeteranganNilaiKepercayaan = new widget.TextBox();
        jLabel207 = new widget.Label();
        Suku = new widget.TextBox();
        jLabel206 = new widget.Label();
        jLabel208 = new widget.Label();
        Pendidikan = new widget.TextBox();
        jLabel209 = new widget.Label();
        TinggalBersama = new widget.ComboBox();
        ResponEmosi = new widget.ComboBox();
        jLabel210 = new widget.Label();
        HubunganAnggotaKeluarga = new widget.ComboBox();
        jLabel211 = new widget.Label();
        Pekerjaan = new widget.TextBox();
        Pembayaran = new widget.TextBox();
        jLabel212 = new widget.Label();
        jLabel213 = new widget.Label();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel214 = new widget.Label();
        jLabel215 = new widget.Label();
        jLabel216 = new widget.Label();
        BahasaSehari = new widget.TextBox();
        jLabel217 = new widget.Label();
        KemampuanBacaTulis = new widget.ComboBox();
        jLabel218 = new widget.Label();
        ButuhPenerjemah = new widget.ComboBox();
        jLabel219 = new widget.Label();
        KeteranganButuhPenerjemah = new widget.TextBox();
        jLabel220 = new widget.Label();
        jLabel221 = new widget.Label();
        TerdapatHambatanBelajar = new widget.ComboBox();
        jLabel222 = new widget.Label();
        HambatanBelajar = new widget.ComboBox();
        KeteranganHambatanBelajar = new widget.TextBox();
        jLabel224 = new widget.Label();
        jLabel225 = new widget.Label();
        jLabel223 = new widget.Label();
        HambatanCaraBicara = new widget.ComboBox();
        HambatanBahasaIsyarat = new widget.ComboBox();
        jLabel226 = new widget.Label();
        jLabel227 = new widget.Label();
        CaraBelajarDisukai = new widget.ComboBox();
        jLabel228 = new widget.Label();
        jLabel229 = new widget.Label();
        KesediaanMenerimaInformasi = new widget.ComboBox();
        KeteranganKesediaanMenerimaInformasi = new widget.TextBox();
        jLabel230 = new widget.Label();
        jLabel231 = new widget.Label();
        PemahamanNutrisi = new widget.ComboBox();
        jLabel232 = new widget.Label();
        PemahamanPenyakit = new widget.ComboBox();
        jLabel233 = new widget.Label();
        jLabel234 = new widget.Label();
        PemahamanPerawatan = new widget.ComboBox();
        PemahamanPengobatan = new widget.ComboBox();
        jLabel235 = new widget.Label();
        jSeparator7 = new javax.swing.JSeparator();
        jLabel236 = new widget.Label();
        SG1 = new widget.ComboBox();
        jLabel237 = new widget.Label();
        NilaiGizi1 = new widget.TextBox();
        jLabel238 = new widget.Label();
        SG2 = new widget.ComboBox();
        NilaiGizi2 = new widget.TextBox();
        jLabel239 = new widget.Label();
        SG3 = new widget.ComboBox();
        NilaiGizi3 = new widget.TextBox();
        jLabel240 = new widget.Label();
        TotalNilaiGizi = new widget.TextBox();
        KeteranganSkriningGizi = new widget.TextBox();
        jLabel241 = new widget.Label();
        jSeparator8 = new javax.swing.JSeparator();
        jLabel242 = new widget.Label();
        jLabel243 = new widget.Label();
        SkalaResiko1 = new widget.ComboBox();
        NilaiResiko1 = new widget.TextBox();
        jLabel244 = new widget.Label();
        SkalaResiko2 = new widget.ComboBox();
        NilaiResiko2 = new widget.TextBox();
        jLabel245 = new widget.Label();
        SkalaResiko3 = new widget.ComboBox();
        NilaiResiko3 = new widget.TextBox();
        jLabel246 = new widget.Label();
        SkalaResiko4 = new widget.ComboBox();
        NilaiResiko4 = new widget.TextBox();
        jLabel247 = new widget.Label();
        SkalaResiko5 = new widget.ComboBox();
        NilaiResiko5 = new widget.TextBox();
        jLabel248 = new widget.Label();
        SkalaResiko6 = new widget.ComboBox();
        NilaiResiko6 = new widget.TextBox();
        jLabel249 = new widget.Label();
        SkalaResiko7 = new widget.ComboBox();
        NilaiResiko7 = new widget.TextBox();
        jLabel251 = new widget.Label();
        SkalaNIPS1 = new widget.ComboBox();
        NilaiNIPS1 = new widget.TextBox();
        NilaiNIPS2 = new widget.TextBox();
        SkalaNIPS2 = new widget.ComboBox();
        jLabel252 = new widget.Label();
        NilaiNIPS3 = new widget.TextBox();
        SkalaNIPS3 = new widget.ComboBox();
        jLabel253 = new widget.Label();
        NilaiNIPS4 = new widget.TextBox();
        SkalaNIPS4 = new widget.ComboBox();
        jLabel254 = new widget.Label();
        NilaiNIPS5 = new widget.TextBox();
        SkalaNIPS5 = new widget.ComboBox();
        jLabel255 = new widget.Label();
        TingkatResiko1 = new widget.Label();
        jLabel256 = new widget.Label();
        NilaiResikoTotal = new widget.TextBox();
        TingkatResiko2 = new widget.Label();
        jLabel258 = new widget.Label();
        TotalNIPS = new widget.TextBox();
        jSeparator13 = new javax.swing.JSeparator();
        jLabel272 = new widget.Label();
        InformasiPerencanaanPulang = new widget.ComboBox();
        jLabel250 = new widget.Label();
        jLabel257 = new widget.Label();
        jLabel259 = new widget.Label();
        jLabel260 = new widget.Label();
        KondisiPulang = new widget.TextBox();
        label29 = new widget.Label();
        TanggalPulang = new widget.Tanggal();
        jLabel261 = new widget.Label();
        LamaRatarata = new widget.TextBox();
        jLabel262 = new widget.Label();
        scrollPane2 = new widget.ScrollPane();
        PerawatanLanjutan = new widget.TextArea();
        jLabel263 = new widget.Label();
        CaraTransportasiPulang = new widget.ComboBox();
        jLabel264 = new widget.Label();
        jLabel265 = new widget.Label();
        jLabel266 = new widget.Label();
        TransportasiYangDigunakan = new widget.ComboBox();
        KeteranganTingkatRisiko = new widget.TextBox();
        jLabel267 = new widget.Label();
        jLabel268 = new widget.Label();
        KeteranganPenilaianNyeri = new widget.TextBox();
        jLabel269 = new widget.Label();
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
        tbMasalahDetail = new widget.Table();
        Scroll9 = new widget.ScrollPane();
        tbRencanaDetail = new widget.Table();
        scrollPane6 = new widget.ScrollPane();
        DetailRencana = new widget.TextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Penilaian Awal Keperawatan Rawat Inap Neonatus ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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
        FormInput.setMaximumSize(new java.awt.Dimension(3767, 3767));
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(870, 2933));
        FormInput.setLayout(null);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(79, 10, 131, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        FormInput.add(TPasien);
        TPasien.setBounds(314, 10, 410, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        FormInput.add(TNoRM);
        TNoRM.setBounds(212, 10, 100, 23);

        label14.setText("Pengkaji 1 :");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label14);
        label14.setBounds(0, 40, 75, 23);

        KdPetugas.setEditable(false);
        KdPetugas.setName("KdPetugas"); // NOI18N
        KdPetugas.setPreferredSize(new java.awt.Dimension(80, 23));
        KdPetugas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdPetugasKeyPressed(evt);
            }
        });
        FormInput.add(KdPetugas);
        KdPetugas.setBounds(79, 40, 100, 23);

        NmPetugas.setEditable(false);
        NmPetugas.setName("NmPetugas"); // NOI18N
        NmPetugas.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmPetugas);
        NmPetugas.setBounds(181, 40, 210, 23);

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
        BtnPetugas.setBounds(393, 40, 28, 23);

        TglLahir.setEditable(false);
        TglLahir.setHighlighter(null);
        TglLahir.setName("TglLahir"); // NOI18N
        FormInput.add(TglLahir);
        TglLahir.setBounds(429, 330, 90, 23);

        Jk.setEditable(false);
        Jk.setHighlighter(null);
        Jk.setName("Jk"); // NOI18N
        FormInput.add(Jk);
        Jk.setBounds(774, 10, 80, 23);

        jLabel10.setText("No.Rawat :");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(0, 10, 75, 23);

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label11);
        label11.setBounds(448, 70, 60, 23);

        jLabel11.setText("J.K. :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(740, 10, 30, 23);

        jLabel36.setText("Cara Masuk :");
        jLabel36.setName("jLabel36"); // NOI18N
        FormInput.add(jLabel36);
        jLabel36.setBounds(0, 100, 75, 23);

        CaraMasuk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Baby Box", "Inkubator", "Kursi Roda" }));
        CaraMasuk.setName("CaraMasuk"); // NOI18N
        CaraMasuk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CaraMasukKeyPressed(evt);
            }
        });
        FormInput.add(CaraMasuk);
        CaraMasuk.setBounds(79, 100, 105, 23);

        TglAsuhan.setForeground(new java.awt.Color(50, 70, 50));
        TglAsuhan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "08-03-2024 20:11:06" }));
        TglAsuhan.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        TglAsuhan.setName("TglAsuhan"); // NOI18N
        TglAsuhan.setOpaque(false);
        TglAsuhan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglAsuhanKeyPressed(evt);
            }
        });
        FormInput.add(TglAsuhan);
        TglAsuhan.setBounds(512, 70, 135, 23);

        NmPetugas2.setEditable(false);
        NmPetugas2.setName("NmPetugas2"); // NOI18N
        NmPetugas2.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmPetugas2);
        NmPetugas2.setBounds(614, 40, 210, 23);

        BtnPetugas2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPetugas2.setMnemonic('2');
        BtnPetugas2.setToolTipText("Alt+2");
        BtnPetugas2.setName("BtnPetugas2"); // NOI18N
        BtnPetugas2.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnPetugas2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPetugas2ActionPerformed(evt);
            }
        });
        BtnPetugas2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPetugas2KeyPressed(evt);
            }
        });
        FormInput.add(BtnPetugas2);
        BtnPetugas2.setBounds(826, 40, 28, 23);

        KdPetugas2.setEditable(false);
        KdPetugas2.setName("KdPetugas2"); // NOI18N
        KdPetugas2.setPreferredSize(new java.awt.Dimension(80, 23));
        KdPetugas2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdPetugas2KeyPressed(evt);
            }
        });
        FormInput.add(KdPetugas2);
        KdPetugas2.setBounds(512, 40, 100, 23);

        label15.setText("Pengkaji 2 :");
        label15.setName("label15"); // NOI18N
        label15.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label15);
        label15.setBounds(438, 40, 70, 23);

        label16.setText("DPJP :");
        label16.setName("label16"); // NOI18N
        label16.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label16);
        label16.setBounds(0, 70, 75, 23);

        KdDPJP.setEditable(false);
        KdDPJP.setName("KdDPJP"); // NOI18N
        KdDPJP.setPreferredSize(new java.awt.Dimension(80, 23));
        KdDPJP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdDPJPKeyPressed(evt);
            }
        });
        FormInput.add(KdDPJP);
        KdDPJP.setBounds(79, 70, 110, 23);

        NmDPJP.setEditable(false);
        NmDPJP.setName("NmDPJP"); // NOI18N
        NmDPJP.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmDPJP);
        NmDPJP.setBounds(191, 70, 226, 23);

        BtnDPJP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDPJP.setMnemonic('2');
        BtnDPJP.setToolTipText("Alt+2");
        BtnDPJP.setName("BtnDPJP"); // NOI18N
        BtnDPJP.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDPJP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDPJPActionPerformed(evt);
            }
        });
        BtnDPJP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnDPJPKeyPressed(evt);
            }
        });
        FormInput.add(BtnDPJP);
        BtnDPJP.setBounds(419, 70, 28, 23);

        jSeparator2.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator2.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator2.setName("jSeparator2"); // NOI18N
        FormInput.add(jSeparator2);
        jSeparator2.setBounds(0, 130, 880, 1);

        AsalPasien.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "IGD", "Poliklinik", "Kamar Bersalin", "Kamar Operasi" }));
        AsalPasien.setName("AsalPasien"); // NOI18N
        AsalPasien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AsalPasienKeyPressed(evt);
            }
        });
        FormInput.add(AsalPasien);
        AsalPasien.setBounds(727, 70, 127, 23);

        jLabel41.setText("Asal Pasien :");
        jLabel41.setName("jLabel41"); // NOI18N
        FormInput.add(jLabel41);
        jLabel41.setBounds(643, 70, 80, 23);

        DiperolehDari.setFocusTraversalPolicyProvider(true);
        DiperolehDari.setName("DiperolehDari"); // NOI18N
        DiperolehDari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiperolehDariKeyPressed(evt);
            }
        });
        FormInput.add(DiperolehDari);
        DiperolehDari.setBounds(287, 100, 205, 23);

        jSeparator11.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator11.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator11.setName("jSeparator11"); // NOI18N
        FormInput.add(jSeparator11);
        jSeparator11.setBounds(0, 2380, 880, 1);

        jLabel271.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel271.setText("VII. PENILAIAN TINGKAT NYERI (SKALA NIPS)");
        jLabel271.setName("jLabel271"); // NOI18N
        FormInput.add(jLabel271);
        jLabel271.setBounds(10, 2380, 380, 23);

        jSeparator12.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator12.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator12.setName("jSeparator12"); // NOI18N
        FormInput.add(jSeparator12);
        jSeparator12.setBounds(0, 2740, 880, 1);

        Scroll6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 253)));
        Scroll6.setName("Scroll6"); // NOI18N
        Scroll6.setOpaque(true);

        tbMasalahKeperawatan.setName("tbMasalahKeperawatan"); // NOI18N
        tbMasalahKeperawatan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbMasalahKeperawatanMouseClicked(evt);
            }
        });
        tbMasalahKeperawatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbMasalahKeperawatanKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbMasalahKeperawatanKeyReleased(evt);
            }
        });
        Scroll6.setViewportView(tbMasalahKeperawatan);

        FormInput.add(Scroll6);
        Scroll6.setBounds(10, 2750, 400, 143);

        TabRencanaKeperawatan.setBackground(new java.awt.Color(255, 255, 254));
        TabRencanaKeperawatan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        TabRencanaKeperawatan.setForeground(new java.awt.Color(50, 50, 50));
        TabRencanaKeperawatan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabRencanaKeperawatan.setName("TabRencanaKeperawatan"); // NOI18N

        panelBiasa1.setName("panelBiasa1"); // NOI18N
        panelBiasa1.setLayout(new java.awt.BorderLayout());

        Scroll8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 253)));
        Scroll8.setName("Scroll8"); // NOI18N
        Scroll8.setOpaque(true);

        tbRencanaKeperawatan.setName("tbRencanaKeperawatan"); // NOI18N
        Scroll8.setViewportView(tbRencanaKeperawatan);

        panelBiasa1.add(Scroll8, java.awt.BorderLayout.CENTER);

        TabRencanaKeperawatan.addTab("Rencana Keperawatan", panelBiasa1);

        scrollPane5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane5.setName("scrollPane5"); // NOI18N

        Rencana.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Rencana.setColumns(20);
        Rencana.setRows(5);
        Rencana.setName("Rencana"); // NOI18N
        Rencana.setOpaque(true);
        Rencana.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RencanaKeyPressed(evt);
            }
        });
        scrollPane5.setViewportView(Rencana);

        TabRencanaKeperawatan.addTab("Rencana Keperawatan Lainnya", scrollPane5);

        FormInput.add(TabRencanaKeperawatan);
        TabRencanaKeperawatan.setBounds(433, 2750, 420, 143);

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
        BtnTambahMasalah.setBounds(363, 2900, 28, 23);

        BtnAllMasalah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAllMasalah.setMnemonic('2');
        BtnAllMasalah.setToolTipText("2Alt+2");
        BtnAllMasalah.setName("BtnAllMasalah"); // NOI18N
        BtnAllMasalah.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnAllMasalah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAllMasalahActionPerformed(evt);
            }
        });
        BtnAllMasalah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnAllMasalahKeyPressed(evt);
            }
        });
        FormInput.add(BtnAllMasalah);
        BtnAllMasalah.setBounds(331, 2900, 28, 23);

        BtnCariMasalah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCariMasalah.setMnemonic('1');
        BtnCariMasalah.setToolTipText("Alt+1");
        BtnCariMasalah.setName("BtnCariMasalah"); // NOI18N
        BtnCariMasalah.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCariMasalah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariMasalahActionPerformed(evt);
            }
        });
        BtnCariMasalah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCariMasalahKeyPressed(evt);
            }
        });
        FormInput.add(BtnCariMasalah);
        BtnCariMasalah.setBounds(299, 2900, 28, 23);

        TCariMasalah.setToolTipText("Alt+C");
        TCariMasalah.setName("TCariMasalah"); // NOI18N
        TCariMasalah.setPreferredSize(new java.awt.Dimension(140, 23));
        TCariMasalah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariMasalahKeyPressed(evt);
            }
        });
        FormInput.add(TCariMasalah);
        TCariMasalah.setBounds(80, 2900, 215, 23);

        BtnTambahRencana.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/plus_16.png"))); // NOI18N
        BtnTambahRencana.setMnemonic('3');
        BtnTambahRencana.setToolTipText("Alt+3");
        BtnTambahRencana.setName("BtnTambahRencana"); // NOI18N
        BtnTambahRencana.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnTambahRencana.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTambahRencanaActionPerformed(evt);
            }
        });
        FormInput.add(BtnTambahRencana);
        BtnTambahRencana.setBounds(806, 2900, 28, 23);

        BtnAllRencana.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAllRencana.setMnemonic('2');
        BtnAllRencana.setToolTipText("2Alt+2");
        BtnAllRencana.setName("BtnAllRencana"); // NOI18N
        BtnAllRencana.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnAllRencana.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAllRencanaActionPerformed(evt);
            }
        });
        BtnAllRencana.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnAllRencanaKeyPressed(evt);
            }
        });
        FormInput.add(BtnAllRencana);
        BtnAllRencana.setBounds(774, 2900, 28, 23);

        BtnCariRencana.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCariRencana.setMnemonic('1');
        BtnCariRencana.setToolTipText("Alt+1");
        BtnCariRencana.setName("BtnCariRencana"); // NOI18N
        BtnCariRencana.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCariRencana.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariRencanaActionPerformed(evt);
            }
        });
        BtnCariRencana.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCariRencanaKeyPressed(evt);
            }
        });
        FormInput.add(BtnCariRencana);
        BtnCariRencana.setBounds(742, 2900, 28, 23);

        label13.setText("Key Word :");
        label13.setName("label13"); // NOI18N
        label13.setPreferredSize(new java.awt.Dimension(60, 23));
        FormInput.add(label13);
        label13.setBounds(439, 2900, 60, 23);

        TCariRencana.setToolTipText("Alt+C");
        TCariRencana.setName("TCariRencana"); // NOI18N
        TCariRencana.setPreferredSize(new java.awt.Dimension(215, 23));
        TCariRencana.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariRencanaKeyPressed(evt);
            }
        });
        FormInput.add(TCariRencana);
        TCariRencana.setBounds(503, 2900, 235, 23);

        label12.setText("Key Word :");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(60, 23));
        FormInput.add(label12);
        label12.setBounds(16, 2900, 60, 23);

        label17.setText("Deperoleh Dari :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label17);
        label17.setBounds(193, 100, 90, 23);

        HubunganDenganPasien.setFocusTraversalPolicyProvider(true);
        HubunganDenganPasien.setName("HubunganDenganPasien"); // NOI18N
        HubunganDenganPasien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HubunganDenganPasienKeyPressed(evt);
            }
        });
        FormInput.add(HubunganDenganPasien);
        HubunganDenganPasien.setBounds(644, 100, 210, 23);

        label18.setText("Hubungan Dengan Pasien :");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label18);
        label18.setBounds(500, 100, 140, 23);

        jLabel94.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel94.setText("I. RIWAYAT KESEHATAN");
        jLabel94.setName("jLabel94"); // NOI18N
        FormInput.add(jLabel94);
        jLabel94.setBounds(10, 130, 180, 23);

        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel30.setText("Keluhan Utama");
        jLabel30.setName("jLabel30"); // NOI18N
        FormInput.add(jLabel30);
        jLabel30.setBounds(44, 150, 90, 23);

        jLabel200.setText(":");
        jLabel200.setName("jLabel200"); // NOI18N
        FormInput.add(jLabel200);
        jLabel200.setBounds(0, 150, 125, 23);

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
        scrollPane1.setBounds(129, 150, 725, 43);

        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel31.setText("Riwayat Prenatal :");
        jLabel31.setName("jLabel31"); // NOI18N
        FormInput.add(jLabel31);
        jLabel31.setBounds(44, 200, 110, 23);

        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel32.setText("Riwayat Obstetri");
        jLabel32.setName("jLabel32"); // NOI18N
        FormInput.add(jLabel32);
        jLabel32.setBounds(74, 220, 90, 23);

        jLabel101.setText("G");
        jLabel101.setName("jLabel101"); // NOI18N
        FormInput.add(jLabel101);
        jLabel101.setBounds(154, 220, 20, 23);

        PrenatalG.setFocusTraversalPolicyProvider(true);
        PrenatalG.setName("PrenatalG"); // NOI18N
        PrenatalG.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PrenatalGKeyPressed(evt);
            }
        });
        FormInput.add(PrenatalG);
        PrenatalG.setBounds(178, 220, 50, 23);

        jLabel102.setText("P");
        jLabel102.setName("jLabel102"); // NOI18N
        FormInput.add(jLabel102);
        jLabel102.setBounds(210, 220, 30, 23);

        PrenatalP.setFocusTraversalPolicyProvider(true);
        PrenatalP.setName("PrenatalP"); // NOI18N
        PrenatalP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PrenatalPKeyPressed(evt);
            }
        });
        FormInput.add(PrenatalP);
        PrenatalP.setBounds(244, 220, 50, 23);

        jLabel103.setText("A");
        jLabel103.setName("jLabel103"); // NOI18N
        FormInput.add(jLabel103);
        jLabel103.setBounds(277, 220, 30, 23);

        PrenatalA.setFocusTraversalPolicyProvider(true);
        PrenatalA.setName("PrenatalA"); // NOI18N
        PrenatalA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PrenatalAKeyPressed(evt);
            }
        });
        FormInput.add(PrenatalA);
        PrenatalA.setBounds(311, 220, 50, 23);

        jLabel104.setText("UK");
        jLabel104.setName("jLabel104"); // NOI18N
        FormInput.add(jLabel104);
        jLabel104.setBounds(355, 220, 25, 23);

        PrenatalUK.setFocusTraversalPolicyProvider(true);
        PrenatalUK.setName("PrenatalUK"); // NOI18N
        PrenatalUK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PrenatalUKKeyPressed(evt);
            }
        });
        FormInput.add(PrenatalUK);
        PrenatalUK.setBounds(384, 220, 80, 23);

        jLabel42.setText("Riwayat Penyakit Ibu :");
        jLabel42.setName("jLabel42"); // NOI18N
        FormInput.add(jLabel42);
        jLabel42.setBounds(463, 220, 130, 23);

        RiwayatPenyakitIbu.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada", "DM", "Hipertensi", "Jantung", "TBC", "Hep B", "Asma", "PMS", "Lainnya" }));
        RiwayatPenyakitIbu.setName("RiwayatPenyakitIbu"); // NOI18N
        RiwayatPenyakitIbu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RiwayatPenyakitIbuKeyPressed(evt);
            }
        });
        FormInput.add(RiwayatPenyakitIbu);
        RiwayatPenyakitIbu.setBounds(597, 220, 100, 23);

        KeteranganRiwayatPenyakitIbu.setFocusTraversalPolicyProvider(true);
        KeteranganRiwayatPenyakitIbu.setName("KeteranganRiwayatPenyakitIbu"); // NOI18N
        KeteranganRiwayatPenyakitIbu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganRiwayatPenyakitIbuKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganRiwayatPenyakitIbu);
        KeteranganRiwayatPenyakitIbu.setBounds(700, 220, 154, 23);

        jLabel35.setText(":");
        jLabel35.setName("jLabel35"); // NOI18N
        FormInput.add(jLabel35);
        jLabel35.setBounds(0, 220, 163, 23);

        jLabel37.setText(":");
        jLabel37.setName("jLabel37"); // NOI18N
        FormInput.add(jLabel37);
        jLabel37.setBounds(0, 250, 270, 23);

        KetAnamnesis3.setFocusTraversalPolicyProvider(true);
        KetAnamnesis3.setName("KetAnamnesis3"); // NOI18N
        KetAnamnesis3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetAnamnesis3KeyPressed(evt);
            }
        });
        FormInput.add(KetAnamnesis3);
        KetAnamnesis3.setBounds(700, 220, 154, 23);

        jLabel38.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel38.setText("Riwayat Pengobatan Ibu Selama Hamil");
        jLabel38.setName("jLabel38"); // NOI18N
        FormInput.add(jLabel38);
        jLabel38.setBounds(74, 250, 210, 23);

        RiwayatPengobatanIbu.setFocusTraversalPolicyProvider(true);
        RiwayatPengobatanIbu.setName("RiwayatPengobatanIbu"); // NOI18N
        RiwayatPengobatanIbu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RiwayatPengobatanIbuKeyPressed(evt);
            }
        });
        FormInput.add(RiwayatPengobatanIbu);
        RiwayatPengobatanIbu.setBounds(274, 250, 580, 23);

        PernahDirawat.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        PernahDirawat.setName("PernahDirawat"); // NOI18N
        PernahDirawat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PernahDirawatKeyPressed(evt);
            }
        });
        FormInput.add(PernahDirawat);
        PernahDirawat.setBounds(159, 280, 80, 23);

        jLabel43.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel43.setText("Pernah Dirawat");
        jLabel43.setName("jLabel43"); // NOI18N
        FormInput.add(jLabel43);
        jLabel43.setBounds(74, 280, 110, 23);

        KeteranganPernahDirawat.setFocusTraversalPolicyProvider(true);
        KeteranganPernahDirawat.setName("KeteranganPernahDirawat"); // NOI18N
        KeteranganPernahDirawat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganPernahDirawatKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganPernahDirawat);
        KeteranganPernahDirawat.setBounds(242, 280, 430, 23);

        jLabel39.setText(":");
        jLabel39.setName("jLabel39"); // NOI18N
        FormInput.add(jLabel39);
        jLabel39.setBounds(0, 280, 155, 23);

        jLabel44.setText("Status Gizi Ibu :");
        jLabel44.setName("jLabel44"); // NOI18N
        FormInput.add(jLabel44);
        jLabel44.setBounds(670, 280, 100, 23);

        StatusGiziIbu.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Baik", "Buruk" }));
        StatusGiziIbu.setName("StatusGiziIbu"); // NOI18N
        StatusGiziIbu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                StatusGiziIbuKeyPressed(evt);
            }
        });
        FormInput.add(StatusGiziIbu);
        StatusGiziIbu.setBounds(774, 280, 80, 23);

        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel33.setText("Riwayat Intranatal :");
        jLabel33.setName("jLabel33"); // NOI18N
        FormInput.add(jLabel33);
        jLabel33.setBounds(44, 310, 110, 23);

        jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel40.setText("Riwayat Obstetri");
        jLabel40.setName("jLabel40"); // NOI18N
        FormInput.add(jLabel40);
        jLabel40.setBounds(74, 330, 90, 23);

        jLabel105.setText("G");
        jLabel105.setName("jLabel105"); // NOI18N
        FormInput.add(jLabel105);
        jLabel105.setBounds(154, 330, 20, 23);

        IntranatalG.setFocusTraversalPolicyProvider(true);
        IntranatalG.setName("IntranatalG"); // NOI18N
        IntranatalG.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                IntranatalGKeyPressed(evt);
            }
        });
        FormInput.add(IntranatalG);
        IntranatalG.setBounds(178, 330, 50, 23);

        jLabel106.setText("P");
        jLabel106.setName("jLabel106"); // NOI18N
        FormInput.add(jLabel106);
        jLabel106.setBounds(210, 330, 30, 23);

        IntranatalP.setFocusTraversalPolicyProvider(true);
        IntranatalP.setName("IntranatalP"); // NOI18N
        IntranatalP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                IntranatalPKeyPressed(evt);
            }
        });
        FormInput.add(IntranatalP);
        IntranatalP.setBounds(244, 330, 50, 23);

        jLabel107.setText("A");
        jLabel107.setName("jLabel107"); // NOI18N
        FormInput.add(jLabel107);
        jLabel107.setBounds(277, 330, 30, 23);

        IntranatalA.setFocusTraversalPolicyProvider(true);
        IntranatalA.setName("IntranatalA"); // NOI18N
        IntranatalA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                IntranatalAKeyPressed(evt);
            }
        });
        FormInput.add(IntranatalA);
        IntranatalA.setBounds(311, 330, 50, 23);

        jLabel45.setText(":");
        jLabel45.setName("jLabel45"); // NOI18N
        FormInput.add(jLabel45);
        jLabel45.setBounds(0, 330, 163, 23);

        label19.setText("Tgl.Lahir :");
        label19.setName("label19"); // NOI18N
        label19.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label19);
        label19.setBounds(365, 330, 60, 23);

        label20.setText("Kondisi Saat Lahir :");
        label20.setName("label20"); // NOI18N
        label20.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label20);
        label20.setBounds(518, 330, 110, 23);

        KondisiSaatLahir.setFocusTraversalPolicyProvider(true);
        KondisiSaatLahir.setName("KondisiSaatLahir"); // NOI18N
        KondisiSaatLahir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KondisiSaatLahirKeyPressed(evt);
            }
        });
        FormInput.add(KondisiSaatLahir);
        KondisiSaatLahir.setBounds(632, 330, 222, 23);

        jLabel46.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel46.setText("Cara Persalinan");
        jLabel46.setName("jLabel46"); // NOI18N
        FormInput.add(jLabel46);
        jLabel46.setBounds(74, 360, 90, 23);

        jLabel47.setText(":");
        jLabel47.setName("jLabel47"); // NOI18N
        FormInput.add(jLabel47);
        jLabel47.setBounds(0, 360, 158, 23);

        KeteranganCaraPersalinan.setFocusTraversalPolicyProvider(true);
        KeteranganCaraPersalinan.setName("KeteranganCaraPersalinan"); // NOI18N
        KeteranganCaraPersalinan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganCaraPersalinanKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganCaraPersalinan);
        KeteranganCaraPersalinan.setBounds(295, 360, 120, 23);

        CaraPersalinan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Spontan", "Vacum Ekstraksi", "Forcep Ekstraksi", "Sectio Caesarea", "Lainnya" }));
        CaraPersalinan.setName("CaraPersalinan"); // NOI18N
        CaraPersalinan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CaraPersalinanKeyPressed(evt);
            }
        });
        FormInput.add(CaraPersalinan);
        CaraPersalinan.setBounds(162, 360, 130, 23);

        jLabel108.setText("APGAR :");
        jLabel108.setName("jLabel108"); // NOI18N
        FormInput.add(jLabel108);
        jLabel108.setBounds(417, 360, 50, 23);

        ApgarScore.setFocusTraversalPolicyProvider(true);
        ApgarScore.setName("ApgarScore"); // NOI18N
        ApgarScore.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ApgarScoreKeyPressed(evt);
            }
        });
        FormInput.add(ApgarScore);
        ApgarScore.setBounds(471, 360, 60, 23);

        label21.setText("Minor :");
        label21.setName("label21"); // NOI18N
        label21.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label21);
        label21.setBounds(452, 440, 50, 23);

        IntranatalLetak.setFocusTraversalPolicyProvider(true);
        IntranatalLetak.setName("IntranatalLetak"); // NOI18N
        IntranatalLetak.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                IntranatalLetakKeyPressed(evt);
            }
        });
        FormInput.add(IntranatalLetak);
        IntranatalLetak.setBounds(579, 360, 123, 23);

        Ketuban.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Jernih", "Darah", "Putih Keruh", "Hijau", "Meconium" }));
        Ketuban.setName("Ketuban"); // NOI18N
        Ketuban.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetubanKeyPressed(evt);
            }
        });
        FormInput.add(Ketuban);
        Ketuban.setBounds(125, 390, 106, 23);

        jLabel48.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel48.setText("Ketuban");
        jLabel48.setName("jLabel48"); // NOI18N
        FormInput.add(jLabel48);
        jLabel48.setBounds(74, 390, 90, 23);

        jLabel49.setText(":");
        jLabel49.setName("jLabel49"); // NOI18N
        FormInput.add(jLabel49);
        jLabel49.setBounds(0, 390, 121, 23);

        jLabel50.setText("Tali Pusat :");
        jLabel50.setName("jLabel50"); // NOI18N
        FormInput.add(jLabel50);
        jLabel50.setBounds(702, 360, 65, 23);

        TaliPusat.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Segar", "Layu", "Simpul" }));
        TaliPusat.setName("TaliPusat"); // NOI18N
        TaliPusat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TaliPusatKeyPressed(evt);
            }
        });
        FormInput.add(TaliPusat);
        TaliPusat.setBounds(771, 360, 83, 23);

        jLabel51.setText("Antopometri BBL :");
        jLabel51.setName("jLabel51"); // NOI18N
        FormInput.add(jLabel51);
        jLabel51.setBounds(228, 390, 110, 23);

        jLabel109.setText("BB");
        jLabel109.setName("jLabel109"); // NOI18N
        FormInput.add(jLabel109);
        jLabel109.setBounds(323, 390, 30, 23);

        AntoBB.setFocusTraversalPolicyProvider(true);
        AntoBB.setName("AntoBB"); // NOI18N
        AntoBB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AntoBBKeyPressed(evt);
            }
        });
        FormInput.add(AntoBB);
        AntoBB.setBounds(356, 390, 50, 23);

        jLabel110.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel110.setText("gr,");
        jLabel110.setName("jLabel110"); // NOI18N
        FormInput.add(jLabel110);
        jLabel110.setBounds(408, 390, 20, 23);

        jLabel111.setText("PB");
        jLabel111.setName("jLabel111"); // NOI18N
        FormInput.add(jLabel111);
        jLabel111.setBounds(418, 390, 20, 23);

        AntoPB.setFocusTraversalPolicyProvider(true);
        AntoPB.setName("AntoPB"); // NOI18N
        AntoPB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AntoPBKeyPressed(evt);
            }
        });
        FormInput.add(AntoPB);
        AntoPB.setBounds(441, 390, 50, 23);

        jLabel112.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel112.setText("cm,");
        jLabel112.setName("jLabel112"); // NOI18N
        FormInput.add(jLabel112);
        jLabel112.setBounds(493, 390, 22, 23);

        jLabel113.setText("LK");
        jLabel113.setName("jLabel113"); // NOI18N
        FormInput.add(jLabel113);
        jLabel113.setBounds(506, 390, 20, 23);

        jLabel114.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel114.setText("cm,");
        jLabel114.setName("jLabel114"); // NOI18N
        FormInput.add(jLabel114);
        jLabel114.setBounds(581, 390, 22, 23);

        AntoLK.setFocusTraversalPolicyProvider(true);
        AntoLK.setName("AntoLK"); // NOI18N
        AntoLK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AntoLKKeyPressed(evt);
            }
        });
        FormInput.add(AntoLK);
        AntoLK.setBounds(529, 390, 50, 23);

        jLabel115.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel115.setText("cm,");
        jLabel115.setName("jLabel115"); // NOI18N
        FormInput.add(jLabel115);
        jLabel115.setBounds(670, 390, 22, 23);

        AntoLD.setFocusTraversalPolicyProvider(true);
        AntoLD.setName("AntoLD"); // NOI18N
        AntoLD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AntoLDKeyPressed(evt);
            }
        });
        FormInput.add(AntoLD);
        AntoLD.setBounds(618, 390, 50, 23);

        jLabel116.setText("LD");
        jLabel116.setName("jLabel116"); // NOI18N
        FormInput.add(jLabel116);
        jLabel116.setBounds(595, 390, 20, 23);

        jLabel117.setText("LP");
        jLabel117.setName("jLabel117"); // NOI18N
        FormInput.add(jLabel117);
        jLabel117.setBounds(683, 390, 20, 23);

        AntoLP.setFocusTraversalPolicyProvider(true);
        AntoLP.setName("AntoLP"); // NOI18N
        AntoLP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AntoLPKeyPressed(evt);
            }
        });
        FormInput.add(AntoLP);
        AntoLP.setBounds(706, 390, 50, 23);

        jLabel118.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel118.setText("cm");
        jLabel118.setName("jLabel118"); // NOI18N
        FormInput.add(jLabel118);
        jLabel118.setBounds(758, 390, 22, 23);

        jLabel52.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel52.setText("Faktor Risiko Infeksi :");
        jLabel52.setName("jLabel52"); // NOI18N
        FormInput.add(jLabel52);
        jLabel52.setBounds(44, 420, 160, 23);

        jLabel54.setText(":");
        jLabel54.setName("jLabel54"); // NOI18N
        FormInput.add(jLabel54);
        jLabel54.setBounds(0, 440, 111, 23);

        jLabel53.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel53.setText("Mayor");
        jLabel53.setName("jLabel53"); // NOI18N
        FormInput.add(jLabel53);
        jLabel53.setBounds(74, 440, 60, 23);

        RisikoInfeksiMayor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Ibu Demam >= 38° C", "KPD > 24 Jam", "Ketuban Hijau", "Korioamniotis", "Fetal Distress" }));
        RisikoInfeksiMayor.setName("RisikoInfeksiMayor"); // NOI18N
        RisikoInfeksiMayor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RisikoInfeksiMayorKeyPressed(evt);
            }
        });
        FormInput.add(RisikoInfeksiMayor);
        RisikoInfeksiMayor.setBounds(115, 440, 157, 23);

        KeteranganRisikoInfeksiMayor.setFocusTraversalPolicyProvider(true);
        KeteranganRisikoInfeksiMayor.setName("KeteranganRisikoInfeksiMayor"); // NOI18N
        KeteranganRisikoInfeksiMayor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganRisikoInfeksiMayorKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganRisikoInfeksiMayor);
        KeteranganRisikoInfeksiMayor.setBounds(275, 440, 175, 23);

        RisikoInfeksiMinor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "KPD > 12 Jam", "Asfiksia", "BBLR", "ISK", "UK < 37 Minggu", "Gemeli", "Keputihan", "Ibu Temperatur > 37° C" }));
        RisikoInfeksiMinor.setName("RisikoInfeksiMinor"); // NOI18N
        RisikoInfeksiMinor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RisikoInfeksiMinorKeyPressed(evt);
            }
        });
        FormInput.add(RisikoInfeksiMinor);
        RisikoInfeksiMinor.setBounds(506, 440, 170, 23);

        KeteranganRisikoInfeksiMinor.setFocusTraversalPolicyProvider(true);
        KeteranganRisikoInfeksiMinor.setName("KeteranganRisikoInfeksiMinor"); // NOI18N
        KeteranganRisikoInfeksiMinor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganRisikoInfeksiMinorKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganRisikoInfeksiMinor);
        KeteranganRisikoInfeksiMinor.setBounds(679, 440, 175, 23);

        label22.setText("Letak :");
        label22.setName("label22"); // NOI18N
        label22.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label22);
        label22.setBounds(525, 360, 50, 23);

        jLabel55.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel55.setText("Kebutuhan Biologis :");
        jLabel55.setName("jLabel55"); // NOI18N
        FormInput.add(jLabel55);
        jLabel55.setBounds(44, 470, 160, 23);

        jLabel57.setText(":");
        jLabel57.setName("jLabel57"); // NOI18N
        FormInput.add(jLabel57);
        jLabel57.setBounds(0, 490, 113, 23);

        Nutrisi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Air Susu Ibu", "Lainnya" }));
        Nutrisi.setName("Nutrisi"); // NOI18N
        Nutrisi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NutrisiKeyPressed(evt);
            }
        });
        FormInput.add(Nutrisi);
        Nutrisi.setBounds(117, 490, 110, 23);

        jLabel56.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel56.setText("Nutrisi");
        jLabel56.setName("jLabel56"); // NOI18N
        FormInput.add(jLabel56);
        jLabel56.setBounds(74, 490, 60, 23);

        KeteranganNutrisi.setFocusTraversalPolicyProvider(true);
        KeteranganNutrisi.setName("KeteranganNutrisi"); // NOI18N
        KeteranganNutrisi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganNutrisiKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganNutrisi);
        KeteranganNutrisi.setBounds(230, 490, 426, 23);

        NutrisiFrekuensi.setFocusTraversalPolicyProvider(true);
        NutrisiFrekuensi.setName("NutrisiFrekuensi"); // NOI18N
        NutrisiFrekuensi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NutrisiFrekuensiKeyPressed(evt);
            }
        });
        FormInput.add(NutrisiFrekuensi);
        NutrisiFrekuensi.setBounds(723, 490, 55, 23);

        NutrisiKali.setFocusTraversalPolicyProvider(true);
        NutrisiKali.setName("NutrisiKali"); // NOI18N
        NutrisiKali.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NutrisiKaliKeyPressed(evt);
            }
        });
        FormInput.add(NutrisiKali);
        NutrisiKali.setBounds(800, 490, 45, 23);

        jLabel120.setText("/");
        jLabel120.setName("jLabel120"); // NOI18N
        FormInput.add(jLabel120);
        jLabel120.setBounds(777, 490, 20, 23);

        jLabel119.setText("Frekuensi :");
        jLabel119.setName("jLabel119"); // NOI18N
        FormInput.add(jLabel119);
        jLabel119.setBounds(640, 490, 80, 23);

        jLabel122.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel122.setText("x");
        jLabel122.setName("jLabel122"); // NOI18N
        FormInput.add(jLabel122);
        jLabel122.setBounds(847, 490, 22, 23);

        label23.setText("BAK :");
        label23.setName("label23"); // NOI18N
        label23.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label23);
        label23.setBounds(88, 540, 50, 23);

        EliminasiBAK.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ada Keluhan", "Tidak Ada Keluhan" }));
        EliminasiBAK.setName("EliminasiBAK"); // NOI18N
        EliminasiBAK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EliminasiBAKKeyPressed(evt);
            }
        });
        FormInput.add(EliminasiBAK);
        EliminasiBAK.setBounds(142, 540, 140, 23);

        KeteranganEliminasiBAK.setFocusTraversalPolicyProvider(true);
        KeteranganEliminasiBAK.setName("KeteranganEliminasiBAK"); // NOI18N
        KeteranganEliminasiBAK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganEliminasiBAKKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganEliminasiBAK);
        KeteranganEliminasiBAK.setBounds(285, 540, 190, 23);

        jLabel58.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel58.setText("Eliminasi :");
        jLabel58.setName("jLabel58"); // NOI18N
        FormInput.add(jLabel58);
        jLabel58.setBounds(74, 520, 60, 23);

        label24.setText("BAB :");
        label24.setName("label24"); // NOI18N
        label24.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label24);
        label24.setBounds(472, 540, 45, 23);

        EliminasiBAB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ada Keluhan", "Tidak Ada Keluhan" }));
        EliminasiBAB.setName("EliminasiBAB"); // NOI18N
        EliminasiBAB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EliminasiBABKeyPressed(evt);
            }
        });
        FormInput.add(EliminasiBAB);
        EliminasiBAB.setBounds(521, 540, 140, 23);

        KeteranganEliminasiBAB.setFocusTraversalPolicyProvider(true);
        KeteranganEliminasiBAB.setName("KeteranganEliminasiBAB"); // NOI18N
        KeteranganEliminasiBAB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganEliminasiBABKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganEliminasiBAB);
        KeteranganEliminasiBAB.setBounds(664, 540, 190, 23);

        jLabel121.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel121.setText(",");
        jLabel121.setName("jLabel121"); // NOI18N
        FormInput.add(jLabel121);
        jLabel121.setBounds(658, 490, 20, 23);

        jLabel123.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel123.setText("cc");
        jLabel123.setName("jLabel123"); // NOI18N
        FormInput.add(jLabel123);
        jLabel123.setBounds(780, 490, 22, 23);

        jLabel59.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel59.setText("Alergi/Reaksi (Pada Orang Tua) :");
        jLabel59.setName("jLabel59"); // NOI18N
        FormInput.add(jLabel59);
        jLabel59.setBounds(44, 570, 240, 23);

        jLabel60.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel60.setText(" Alergi Obat");
        jLabel60.setName("jLabel60"); // NOI18N
        FormInput.add(jLabel60);
        jLabel60.setBounds(74, 590, 60, 23);

        jLabel61.setText(":");
        jLabel61.setName("jLabel61"); // NOI18N
        FormInput.add(jLabel61);
        jLabel61.setBounds(0, 590, 140, 23);

        AlergiObat.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada", "Tidak Diketahui", "Ada" }));
        AlergiObat.setName("AlergiObat"); // NOI18N
        AlergiObat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlergiObatKeyPressed(evt);
            }
        });
        FormInput.add(AlergiObat);
        AlergiObat.setBounds(144, 590, 130, 23);

        KeteranganAlergiObat.setFocusTraversalPolicyProvider(true);
        KeteranganAlergiObat.setName("KeteranganAlergiObat"); // NOI18N
        KeteranganAlergiObat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganAlergiObatKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganAlergiObat);
        KeteranganAlergiObat.setBounds(277, 590, 240, 23);

        label25.setText("Jika Ada, Reaksi :");
        label25.setName("label25"); // NOI18N
        label25.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label25);
        label25.setBounds(510, 590, 100, 23);

        ReaksiAlergiObat.setFocusTraversalPolicyProvider(true);
        ReaksiAlergiObat.setName("ReaksiAlergiObat"); // NOI18N
        ReaksiAlergiObat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ReaksiAlergiObatKeyPressed(evt);
            }
        });
        FormInput.add(ReaksiAlergiObat);
        ReaksiAlergiObat.setBounds(614, 590, 240, 23);

        jLabel62.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel62.setText("Alergi Makanan");
        jLabel62.setName("jLabel62"); // NOI18N
        FormInput.add(jLabel62);
        jLabel62.setBounds(74, 620, 90, 23);

        jLabel63.setText(":");
        jLabel63.setName("jLabel63"); // NOI18N
        FormInput.add(jLabel63);
        jLabel63.setBounds(0, 620, 156, 23);

        AlergiMakanan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada", "Tidak Diketahui", "Ada" }));
        AlergiMakanan.setName("AlergiMakanan"); // NOI18N
        AlergiMakanan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlergiMakananKeyPressed(evt);
            }
        });
        FormInput.add(AlergiMakanan);
        AlergiMakanan.setBounds(160, 620, 130, 23);

        KeteranganAlergiMakanan.setFocusTraversalPolicyProvider(true);
        KeteranganAlergiMakanan.setName("KeteranganAlergiMakanan"); // NOI18N
        KeteranganAlergiMakanan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganAlergiMakananKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganAlergiMakanan);
        KeteranganAlergiMakanan.setBounds(293, 620, 232, 23);

        label26.setText("Jika Ada, Reaksi :");
        label26.setName("label26"); // NOI18N
        label26.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label26);
        label26.setBounds(518, 620, 100, 23);

        ReaksiAlergiMakanan.setFocusTraversalPolicyProvider(true);
        ReaksiAlergiMakanan.setName("ReaksiAlergiMakanan"); // NOI18N
        ReaksiAlergiMakanan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ReaksiAlergiMakananKeyPressed(evt);
            }
        });
        FormInput.add(ReaksiAlergiMakanan);
        ReaksiAlergiMakanan.setBounds(622, 620, 232, 23);

        jLabel64.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel64.setText("Alergi Lainnya");
        jLabel64.setName("jLabel64"); // NOI18N
        FormInput.add(jLabel64);
        jLabel64.setBounds(74, 650, 90, 23);

        jLabel65.setText(":");
        jLabel65.setName("jLabel65"); // NOI18N
        FormInput.add(jLabel65);
        jLabel65.setBounds(0, 650, 150, 23);

        AlergiLainnya.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada", "Tidak Diketahui", "Ada" }));
        AlergiLainnya.setName("AlergiLainnya"); // NOI18N
        AlergiLainnya.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlergiLainnyaKeyPressed(evt);
            }
        });
        FormInput.add(AlergiLainnya);
        AlergiLainnya.setBounds(154, 650, 130, 23);

        KeteranganAlergiLainnya.setFocusTraversalPolicyProvider(true);
        KeteranganAlergiLainnya.setName("KeteranganAlergiLainnya"); // NOI18N
        KeteranganAlergiLainnya.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganAlergiLainnyaKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganAlergiLainnya);
        KeteranganAlergiLainnya.setBounds(287, 650, 235, 23);

        label27.setText("Jika Ada, Reaksi :");
        label27.setName("label27"); // NOI18N
        label27.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label27);
        label27.setBounds(515, 650, 100, 23);

        ReaksiAlergiLainnya.setFocusTraversalPolicyProvider(true);
        ReaksiAlergiLainnya.setName("ReaksiAlergiLainnya"); // NOI18N
        ReaksiAlergiLainnya.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ReaksiAlergiLainnyaKeyPressed(evt);
            }
        });
        FormInput.add(ReaksiAlergiLainnya);
        ReaksiAlergiLainnya.setBounds(619, 650, 235, 23);

        jLabel66.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel66.setText("Riwayat Penyakit Keluarga");
        jLabel66.setName("jLabel66"); // NOI18N
        FormInput.add(jLabel66);
        jLabel66.setBounds(44, 680, 140, 23);

        jLabel67.setText(":");
        jLabel67.setName("jLabel67"); // NOI18N
        FormInput.add(jLabel67);
        jLabel67.setBounds(0, 680, 180, 23);

        RiwayatPenyakitKeluarga.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada", "Diabetes", "Kanker", "Asma", "Hipertensi", "Jantung", "Lainnya" }));
        RiwayatPenyakitKeluarga.setName("RiwayatPenyakitKeluarga"); // NOI18N
        RiwayatPenyakitKeluarga.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RiwayatPenyakitKeluargaKeyPressed(evt);
            }
        });
        FormInput.add(RiwayatPenyakitKeluarga);
        RiwayatPenyakitKeluarga.setBounds(184, 680, 100, 23);

        KeteranganRiwayatPenyakitKeluarga.setFocusTraversalPolicyProvider(true);
        KeteranganRiwayatPenyakitKeluarga.setName("KeteranganRiwayatPenyakitKeluarga"); // NOI18N
        KeteranganRiwayatPenyakitKeluarga.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganRiwayatPenyakitKeluargaKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganRiwayatPenyakitKeluarga);
        KeteranganRiwayatPenyakitKeluarga.setBounds(287, 680, 567, 23);

        jLabel68.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel68.setText("Riwayat Imunisasi");
        jLabel68.setName("jLabel68"); // NOI18N
        FormInput.add(jLabel68);
        jLabel68.setBounds(44, 710, 110, 23);

        jLabel69.setText(":");
        jLabel69.setName("jLabel69"); // NOI18N
        FormInput.add(jLabel69);
        jLabel69.setBounds(0, 710, 140, 23);

        RiwayatImunisasi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada", "Ada" }));
        RiwayatImunisasi.setName("RiwayatImunisasi"); // NOI18N
        RiwayatImunisasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RiwayatImunisasiKeyPressed(evt);
            }
        });
        FormInput.add(RiwayatImunisasi);
        RiwayatImunisasi.setBounds(144, 710, 100, 23);

        KeteranganRiwayatImunisasi.setFocusTraversalPolicyProvider(true);
        KeteranganRiwayatImunisasi.setName("KeteranganRiwayatImunisasi"); // NOI18N
        KeteranganRiwayatImunisasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganRiwayatImunisasiKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganRiwayatImunisasi);
        KeteranganRiwayatImunisasi.setBounds(247, 710, 607, 23);

        TranfusiDarah.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada", "Ada" }));
        TranfusiDarah.setName("TranfusiDarah"); // NOI18N
        TranfusiDarah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TranfusiDarahKeyPressed(evt);
            }
        });
        FormInput.add(TranfusiDarah);
        TranfusiDarah.setBounds(169, 740, 100, 23);

        jLabel70.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel70.setText("Riwayat Tranfusi Darah");
        jLabel70.setName("jLabel70"); // NOI18N
        FormInput.add(jLabel70);
        jLabel70.setBounds(44, 740, 140, 23);

        jLabel71.setText(":");
        jLabel71.setName("jLabel71"); // NOI18N
        FormInput.add(jLabel71);
        jLabel71.setBounds(0, 740, 165, 23);

        KeteranganTranfusiDarah.setFocusTraversalPolicyProvider(true);
        KeteranganTranfusiDarah.setName("KeteranganTranfusiDarah"); // NOI18N
        KeteranganTranfusiDarah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganTranfusiDarahKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganTranfusiDarah);
        KeteranganTranfusiDarah.setBounds(272, 740, 150, 23);

        label28.setText("Jika Ada, Reaksi Tranfusi :");
        label28.setName("label28"); // NOI18N
        label28.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label28);
        label28.setBounds(417, 740, 140, 23);

        ReaksiTranfusiDarah.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada", "Ada" }));
        ReaksiTranfusiDarah.setName("ReaksiTranfusiDarah"); // NOI18N
        ReaksiTranfusiDarah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ReaksiTranfusiDarahKeyPressed(evt);
            }
        });
        FormInput.add(ReaksiTranfusiDarah);
        ReaksiTranfusiDarah.setBounds(561, 740, 100, 23);

        KeteranganReaksiTranfusiDarah.setFocusTraversalPolicyProvider(true);
        KeteranganReaksiTranfusiDarah.setName("KeteranganReaksiTranfusiDarah"); // NOI18N
        KeteranganReaksiTranfusiDarah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganReaksiTranfusiDarahKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganReaksiTranfusiDarah);
        KeteranganReaksiTranfusiDarah.setBounds(664, 740, 190, 23);

        jLabel72.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel72.setText("Kebiasaan Ibu :");
        jLabel72.setName("jLabel72"); // NOI18N
        FormInput.add(jLabel72);
        jLabel72.setBounds(44, 770, 240, 23);

        jLabel73.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel73.setText("Obat-obatan Diminum");
        jLabel73.setName("jLabel73"); // NOI18N
        FormInput.add(jLabel73);
        jLabel73.setBounds(74, 790, 120, 23);

        jLabel74.setText(":");
        jLabel74.setName("jLabel74"); // NOI18N
        FormInput.add(jLabel74);
        jLabel74.setBounds(0, 790, 189, 23);

        ObatobatanDiminum.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada", "Vitamin", "Jamu-jamuan", "Lainnya" }));
        ObatobatanDiminum.setName("ObatobatanDiminum"); // NOI18N
        ObatobatanDiminum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ObatobatanDiminumKeyPressed(evt);
            }
        });
        FormInput.add(ObatobatanDiminum);
        ObatobatanDiminum.setBounds(193, 790, 120, 23);

        KeteranganObatobatanDiminum.setFocusTraversalPolicyProvider(true);
        KeteranganObatobatanDiminum.setName("KeteranganObatobatanDiminum"); // NOI18N
        KeteranganObatobatanDiminum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganObatobatanDiminumKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganObatobatanDiminum);
        KeteranganObatobatanDiminum.setBounds(316, 790, 228, 23);

        jLabel75.setText("Merokok :");
        jLabel75.setName("jLabel75"); // NOI18N
        FormInput.add(jLabel75);
        jLabel75.setBounds(551, 790, 60, 23);

        Merokok.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Merokok.setName("Merokok"); // NOI18N
        Merokok.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MerokokKeyPressed(evt);
            }
        });
        FormInput.add(Merokok);
        Merokok.setBounds(615, 790, 80, 23);

        jLabel124.setText("Jika Ya :");
        jLabel124.setName("jLabel124"); // NOI18N
        FormInput.add(jLabel124);
        jLabel124.setBounds(696, 790, 50, 23);

        JumlahMerokok.setFocusTraversalPolicyProvider(true);
        JumlahMerokok.setName("JumlahMerokok"); // NOI18N
        JumlahMerokok.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JumlahMerokokKeyPressed(evt);
            }
        });
        FormInput.add(JumlahMerokok);
        JumlahMerokok.setBounds(749, 790, 45, 23);

        jLabel125.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel125.setText("batang/hari");
        jLabel125.setName("jLabel125"); // NOI18N
        FormInput.add(jLabel125);
        jLabel125.setBounds(796, 790, 70, 23);

        jLabel126.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel126.setText(",");
        jLabel126.setName("jLabel126"); // NOI18N
        FormInput.add(jLabel126);
        jLabel126.setBounds(697, 790, 20, 23);

        jLabel76.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel76.setText("Obat Tidur/Narkoba");
        jLabel76.setName("jLabel76"); // NOI18N
        FormInput.add(jLabel76);
        jLabel76.setBounds(74, 820, 120, 23);

        jLabel77.setText(":");
        jLabel77.setName("jLabel77"); // NOI18N
        FormInput.add(jLabel77);
        jLabel77.setBounds(0, 820, 177, 23);

        ObatTidurNarkoba.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada", "Ada" }));
        ObatTidurNarkoba.setName("ObatTidurNarkoba"); // NOI18N
        ObatTidurNarkoba.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ObatTidurNarkobaKeyPressed(evt);
            }
        });
        FormInput.add(ObatTidurNarkoba);
        ObatTidurNarkoba.setBounds(181, 820, 100, 23);

        KeteranganObatTidurNarkoba.setFocusTraversalPolicyProvider(true);
        KeteranganObatTidurNarkoba.setName("KeteranganObatTidurNarkoba"); // NOI18N
        KeteranganObatTidurNarkoba.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganObatTidurNarkobaKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganObatTidurNarkoba);
        KeteranganObatTidurNarkoba.setBounds(284, 820, 272, 23);

        jLabel78.setText("Alkohol :");
        jLabel78.setName("jLabel78"); // NOI18N
        FormInput.add(jLabel78);
        jLabel78.setBounds(559, 820, 60, 23);

        Alkohol.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Alkohol.setName("Alkohol"); // NOI18N
        Alkohol.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlkoholKeyPressed(evt);
            }
        });
        FormInput.add(Alkohol);
        Alkohol.setBounds(623, 820, 80, 23);

        jLabel127.setText("Jika Ya :");
        jLabel127.setName("jLabel127"); // NOI18N
        FormInput.add(jLabel127);
        jLabel127.setBounds(704, 820, 50, 23);

        JumlahAlkohol.setFocusTraversalPolicyProvider(true);
        JumlahAlkohol.setName("JumlahAlkohol"); // NOI18N
        JumlahAlkohol.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JumlahAlkoholKeyPressed(evt);
            }
        });
        FormInput.add(JumlahAlkohol);
        JumlahAlkohol.setBounds(757, 820, 45, 23);

        jLabel128.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel128.setText("gelas/hari");
        jLabel128.setName("jLabel128"); // NOI18N
        FormInput.add(jLabel128);
        jLabel128.setBounds(804, 820, 70, 23);

        jLabel129.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel129.setText(",");
        jLabel129.setName("jLabel129"); // NOI18N
        FormInput.add(jLabel129);
        jLabel129.setBounds(705, 820, 20, 23);

        jSeparator3.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator3.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator3.setName("jSeparator3"); // NOI18N
        FormInput.add(jSeparator3);
        jSeparator3.setBounds(0, 850, 880, 1);

        jLabel95.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel95.setText("II. PEMERIKSAAN FISIK");
        jLabel95.setName("jLabel95"); // NOI18N
        FormInput.add(jLabel95);
        jLabel95.setBounds(10, 850, 180, 23);

        jLabel130.setText("Keadaan Umum :");
        jLabel130.setName("jLabel130"); // NOI18N
        FormInput.add(jLabel130);
        jLabel130.setBounds(230, 870, 97, 23);

        KeadaanUmum.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tampak Tidak Sakit", "Sakit Ringan", "Sakit Sedang", "Sakit Berat" }));
        KeadaanUmum.setName("KeadaanUmum"); // NOI18N
        KeadaanUmum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeadaanUmumKeyPressed(evt);
            }
        });
        FormInput.add(KeadaanUmum);
        KeadaanUmum.setBounds(331, 870, 145, 23);

        jLabel28.setText("GCS(E,V,M) :");
        jLabel28.setName("jLabel28"); // NOI18N
        FormInput.add(jLabel28);
        jLabel28.setBounds(481, 870, 70, 23);

        FisikGCS.setFocusTraversalPolicyProvider(true);
        FisikGCS.setName("FisikGCS"); // NOI18N
        FisikGCS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FisikGCSKeyPressed(evt);
            }
        });
        FormInput.add(FisikGCS);
        FisikGCS.setBounds(555, 870, 55, 23);

        jLabel22.setText("TD :");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(611, 870, 30, 23);

        FisikTD.setFocusTraversalPolicyProvider(true);
        FisikTD.setName("FisikTD"); // NOI18N
        FisikTD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FisikTDKeyPressed(evt);
            }
        });
        FormInput.add(FisikTD);
        FisikTD.setBounds(645, 870, 60, 23);

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel23.setText("mmHg");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(707, 870, 40, 23);

        jLabel201.setText(":");
        jLabel201.setName("jLabel201"); // NOI18N
        FormInput.add(jLabel201);
        jLabel201.setBounds(0, 870, 102, 23);

        ComposMentis.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Compos Mentis", "Apatis", "Somnolen", "Sopor", "Soporcoma", "Coma" }));
        ComposMentis.setName("ComposMentis"); // NOI18N
        ComposMentis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ComposMentisKeyPressed(evt);
            }
        });
        FormInput.add(ComposMentis);
        ComposMentis.setBounds(106, 870, 125, 23);

        jLabel80.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel80.setText("Kesadaran");
        jLabel80.setName("jLabel80"); // NOI18N
        FormInput.add(jLabel80);
        jLabel80.setBounds(44, 870, 70, 23);

        jLabel18.setText("Suhu :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(744, 870, 40, 23);

        FisikSuhu.setFocusTraversalPolicyProvider(true);
        FisikSuhu.setName("FisikSuhu"); // NOI18N
        FisikSuhu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FisikSuhuKeyPressed(evt);
            }
        });
        FormInput.add(FisikSuhu);
        FisikSuhu.setBounds(788, 870, 50, 23);

        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel20.setText("°C");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(840, 870, 30, 23);

        jLabel17.setText(":");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(0, 900, 65, 23);

        FisikHR.setFocusTraversalPolicyProvider(true);
        FisikHR.setName("FisikHR"); // NOI18N
        FisikHR.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FisikHRKeyPressed(evt);
            }
        });
        FormInput.add(FisikHR);
        FisikHR.setBounds(69, 900, 50, 23);

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel16.setText("x/menit");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput.add(jLabel16);
        jLabel16.setBounds(121, 900, 50, 23);

        jLabel26.setText("RR :");
        jLabel26.setName("jLabel26"); // NOI18N
        FormInput.add(jLabel26);
        jLabel26.setBounds(157, 900, 40, 23);

        FisikRR.setFocusTraversalPolicyProvider(true);
        FisikRR.setName("FisikRR"); // NOI18N
        FisikRR.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FisikRRKeyPressed(evt);
            }
        });
        FormInput.add(FisikRR);
        FisikRR.setBounds(201, 900, 50, 23);

        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel25.setText("x/menit");
        jLabel25.setName("jLabel25"); // NOI18N
        FormInput.add(jLabel25);
        jLabel25.setBounds(253, 900, 50, 23);

        jLabel29.setText("SpO2 :");
        jLabel29.setName("jLabel29"); // NOI18N
        FormInput.add(jLabel29);
        jLabel29.setBounds(300, 900, 40, 23);

        FisikSPO.setFocusTraversalPolicyProvider(true);
        FisikSPO.setName("FisikSPO"); // NOI18N
        FisikSPO.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FisikSPOKeyPressed(evt);
            }
        });
        FormInput.add(FisikSPO);
        FisikSPO.setBounds(344, 900, 50, 23);

        jLabel79.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel79.setText("%");
        jLabel79.setName("jLabel79"); // NOI18N
        FormInput.add(jLabel79);
        jLabel79.setBounds(396, 900, 30, 23);

        jLabel12.setText("BB :");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(543, 900, 30, 23);

        FisikBB.setFocusTraversalPolicyProvider(true);
        FisikBB.setName("FisikBB"); // NOI18N
        FisikBB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FisikBBKeyPressed(evt);
            }
        });
        FormInput.add(FisikBB);
        FisikBB.setBounds(577, 900, 50, 23);

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel13.setText("Kg");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(629, 900, 30, 23);

        jLabel15.setText("TB :");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(647, 900, 30, 23);

        FisikTB.setFocusTraversalPolicyProvider(true);
        FisikTB.setName("FisikTB"); // NOI18N
        FisikTB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FisikTBKeyPressed(evt);
            }
        });
        FormInput.add(FisikTB);
        FisikTB.setBounds(681, 900, 50, 23);

        jLabel81.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel81.setText("cm");
        jLabel81.setName("jLabel81"); // NOI18N
        FormInput.add(jLabel81);
        jLabel81.setBounds(733, 900, 30, 23);

        jLabel82.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel82.setText("HR");
        jLabel82.setName("jLabel82"); // NOI18N
        FormInput.add(jLabel82);
        jLabel82.setBounds(44, 900, 30, 23);

        FisikDownScore.setFocusTraversalPolicyProvider(true);
        FisikDownScore.setName("FisikDownScore"); // NOI18N
        FisikDownScore.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FisikDownScoreKeyPressed(evt);
            }
        });
        FormInput.add(FisikDownScore);
        FisikDownScore.setBounds(490, 900, 50, 23);

        jLabel83.setText("Down Score :");
        jLabel83.setName("jLabel83"); // NOI18N
        FormInput.add(jLabel83);
        jLabel83.setBounds(406, 900, 80, 23);

        jLabel131.setText("LK :");
        jLabel131.setName("jLabel131"); // NOI18N
        FormInput.add(jLabel131);
        jLabel131.setBounds(752, 900, 30, 23);

        FisikLK.setFocusTraversalPolicyProvider(true);
        FisikLK.setName("FisikLK"); // NOI18N
        FisikLK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FisikLKKeyPressed(evt);
            }
        });
        FormInput.add(FisikLK);
        FisikLK.setBounds(786, 900, 50, 23);

        jLabel132.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel132.setText("cm");
        jLabel132.setName("jLabel132"); // NOI18N
        FormInput.add(jLabel132);
        jLabel132.setBounds(838, 900, 22, 23);

        FisikLD.setFocusTraversalPolicyProvider(true);
        FisikLD.setName("FisikLD"); // NOI18N
        FisikLD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FisikLDKeyPressed(evt);
            }
        });
        FormInput.add(FisikLD);
        FisikLD.setBounds(69, 930, 50, 23);

        jLabel134.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel134.setText("cm");
        jLabel134.setName("jLabel134"); // NOI18N
        FormInput.add(jLabel134);
        jLabel134.setBounds(121, 930, 22, 23);

        jLabel135.setText("LP :");
        jLabel135.setName("jLabel135"); // NOI18N
        FormInput.add(jLabel135);
        jLabel135.setBounds(137, 930, 36, 23);

        FisikLP.setFocusTraversalPolicyProvider(true);
        FisikLP.setName("FisikLP"); // NOI18N
        FisikLP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FisikLPKeyPressed(evt);
            }
        });
        FormInput.add(FisikLP);
        FisikLP.setBounds(177, 930, 50, 23);

        jLabel136.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel136.setText("cm");
        jLabel136.setName("jLabel136"); // NOI18N
        FormInput.add(jLabel136);
        jLabel136.setBounds(229, 930, 22, 23);

        jLabel84.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel84.setText("LD");
        jLabel84.setName("jLabel84"); // NOI18N
        FormInput.add(jLabel84);
        jLabel84.setBounds(44, 930, 30, 23);

        jLabel24.setText(":");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(0, 930, 65, 23);

        jLabel133.setText("Golongan Darah Bayi :");
        jLabel133.setName("jLabel133"); // NOI18N
        FormInput.add(jLabel133);
        jLabel133.setBounds(259, 930, 110, 23);

        GDBayi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "A +", "A -", "B +", "B -", "AB +", "AB -", "O +", "O -" }));
        GDBayi.setName("GDBayi"); // NOI18N
        GDBayi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GDBayiKeyPressed(evt);
            }
        });
        FormInput.add(GDBayi);
        GDBayi.setBounds(373, 930, 75, 23);

        jLabel137.setText("Golongan Darah Ibu :");
        jLabel137.setName("jLabel137"); // NOI18N
        FormInput.add(jLabel137);
        jLabel137.setBounds(458, 930, 110, 23);

        GDIbu.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "A +", "A -", "B +", "B -", "AB +", "AB -", "O +", "O -" }));
        GDIbu.setName("GDIbu"); // NOI18N
        GDIbu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GDIbuKeyPressed(evt);
            }
        });
        FormInput.add(GDIbu);
        GDIbu.setBounds(572, 930, 75, 23);

        jLabel138.setText("Golongan Darah Ayah :");
        jLabel138.setName("jLabel138"); // NOI18N
        FormInput.add(jLabel138);
        jLabel138.setBounds(645, 930, 130, 23);

        GDAyah.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "A +", "A -", "B +", "B -", "AB +", "AB -", "O +", "O -" }));
        GDAyah.setName("GDAyah"); // NOI18N
        GDAyah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GDAyahKeyPressed(evt);
            }
        });
        FormInput.add(GDAyah);
        GDAyah.setBounds(779, 930, 75, 23);

        jLabel85.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel85.setText("Sistem Susunan Saraf Pusat :");
        jLabel85.setName("jLabel85"); // NOI18N
        FormInput.add(jLabel85);
        jLabel85.setBounds(44, 960, 240, 23);

        jLabel86.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel86.setText("Gerak Bayi");
        jLabel86.setName("jLabel86"); // NOI18N
        FormInput.add(jLabel86);
        jLabel86.setBounds(74, 980, 60, 23);

        jLabel87.setText(":");
        jLabel87.setName("jLabel87"); // NOI18N
        FormInput.add(jLabel87);
        jLabel87.setBounds(0, 980, 132, 23);

        GerakBayi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Aktif", "Tidak Aktif" }));
        GerakBayi.setName("GerakBayi"); // NOI18N
        GerakBayi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GerakBayiKeyPressed(evt);
            }
        });
        FormInput.add(GerakBayi);
        GerakBayi.setBounds(136, 980, 105, 23);

        jLabel139.setText("Kepala :");
        jLabel139.setName("jLabel139"); // NOI18N
        FormInput.add(jLabel139);
        jLabel139.setBounds(244, 980, 50, 23);

        KepalaBayi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "TAK", "Hydrocephalus", "Hematoma", "Lainnya" }));
        KepalaBayi.setName("KepalaBayi"); // NOI18N
        KepalaBayi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KepalaBayiKeyPressed(evt);
            }
        });
        FormInput.add(KepalaBayi);
        KepalaBayi.setBounds(298, 980, 125, 23);

        KeteranganKepalaBayi.setFocusTraversalPolicyProvider(true);
        KeteranganKepalaBayi.setName("KeteranganKepalaBayi"); // NOI18N
        KeteranganKepalaBayi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKepalaBayiKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganKepalaBayi);
        KeteranganKepalaBayi.setBounds(426, 980, 125, 23);

        Ubunubun.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Datar", "Cekung", "Menonjol", "Lainnya" }));
        Ubunubun.setName("Ubunubun"); // NOI18N
        Ubunubun.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                UbunubunKeyPressed(evt);
            }
        });
        FormInput.add(Ubunubun);
        Ubunubun.setBounds(628, 980, 98, 23);

        jLabel140.setText("Ubun-ubun :");
        jLabel140.setName("jLabel140"); // NOI18N
        FormInput.add(jLabel140);
        jLabel140.setBounds(554, 980, 70, 23);

        KeteranganUbunubun.setFocusTraversalPolicyProvider(true);
        KeteranganUbunubun.setName("KeteranganUbunubun"); // NOI18N
        KeteranganUbunubun.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganUbunubunKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganUbunubun);
        KeteranganUbunubun.setBounds(729, 980, 125, 23);

        jLabel88.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel88.setText("Wajah");
        jLabel88.setName("jLabel88"); // NOI18N
        FormInput.add(jLabel88);
        jLabel88.setBounds(74, 1010, 60, 23);

        jLabel89.setText(":");
        jLabel89.setName("jLabel89"); // NOI18N
        FormInput.add(jLabel89);
        jLabel89.setBounds(0, 1010, 112, 23);

        Wajah.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "TAK", "Asimetris", "Kelainan Kongenital", "Lainnya" }));
        Wajah.setName("Wajah"); // NOI18N
        Wajah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                WajahKeyPressed(evt);
            }
        });
        FormInput.add(Wajah);
        Wajah.setBounds(120, 1010, 150, 23);

        KeteranganWajah.setFocusTraversalPolicyProvider(true);
        KeteranganWajah.setName("KeteranganWajah"); // NOI18N
        KeteranganWajah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganWajahKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganWajah);
        KeteranganWajah.setBounds(273, 1010, 180, 23);

        jLabel141.setText("Kejang :");
        jLabel141.setName("jLabel141"); // NOI18N
        FormInput.add(jLabel141);
        jLabel141.setBounds(435, 1010, 70, 23);

        Kejang.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada", "Ada" }));
        Kejang.setName("Kejang"); // NOI18N
        Kejang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KejangKeyPressed(evt);
            }
        });
        FormInput.add(Kejang);
        Kejang.setBounds(509, 1010, 98, 23);

        KeteranganKejang.setFocusTraversalPolicyProvider(true);
        KeteranganKejang.setName("KeteranganKejang"); // NOI18N
        KeteranganKejang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKejangKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganKejang);
        KeteranganKejang.setBounds(610, 1010, 244, 23);

        jLabel90.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel90.setText("Refleks");
        jLabel90.setName("jLabel90"); // NOI18N
        FormInput.add(jLabel90);
        jLabel90.setBounds(74, 1040, 60, 23);

        jLabel91.setText(":");
        jLabel91.setName("jLabel91"); // NOI18N
        FormInput.add(jLabel91);
        jLabel91.setBounds(0, 1040, 117, 23);

        Refleks.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Moro", "Menelan", "Hisap", "Babinski", "Rooting", "Lainnya" }));
        Refleks.setName("Refleks"); // NOI18N
        Refleks.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RefleksKeyPressed(evt);
            }
        });
        FormInput.add(Refleks);
        Refleks.setBounds(121, 1040, 95, 23);

        KeteranganRefleks.setFocusTraversalPolicyProvider(true);
        KeteranganRefleks.setName("KeteranganRefleks"); // NOI18N
        KeteranganRefleks.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganRefleksKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganRefleks);
        KeteranganRefleks.setBounds(219, 1040, 212, 23);

        jLabel142.setText("Tangis Bayi :");
        jLabel142.setName("jLabel142"); // NOI18N
        FormInput.add(jLabel142);
        jLabel142.setBounds(425, 1040, 80, 23);

        TangisBayi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Kuat", "Melengking", "Lainnya" }));
        TangisBayi.setName("TangisBayi"); // NOI18N
        TangisBayi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TangisBayiKeyPressed(evt);
            }
        });
        FormInput.add(TangisBayi);
        TangisBayi.setBounds(509, 1040, 107, 23);

        KeteranganTangisBayi.setFocusTraversalPolicyProvider(true);
        KeteranganTangisBayi.setName("KeteranganTangisBayi"); // NOI18N
        KeteranganTangisBayi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganTangisBayiKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganTangisBayi);
        KeteranganTangisBayi.setBounds(619, 1040, 235, 23);

        jLabel92.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel92.setText("Kardiovaskuler :");
        jLabel92.setName("jLabel92"); // NOI18N
        FormInput.add(jLabel92);
        jLabel92.setBounds(44, 1070, 170, 23);

        jLabel93.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel93.setText("Denyut Nadi");
        jLabel93.setName("jLabel93"); // NOI18N
        FormInput.add(jLabel93);
        jLabel93.setBounds(74, 1090, 60, 23);

        jLabel96.setText(":");
        jLabel96.setName("jLabel96"); // NOI18N
        FormInput.add(jLabel96);
        jLabel96.setBounds(0, 1090, 139, 23);

        DenyutNadi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Teratur", "Tidak Teratur" }));
        DenyutNadi.setName("DenyutNadi"); // NOI18N
        DenyutNadi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DenyutNadiKeyPressed(evt);
            }
        });
        FormInput.add(DenyutNadi);
        DenyutNadi.setBounds(143, 1090, 116, 23);

        jLabel143.setText("Sirkulasi :");
        jLabel143.setName("jLabel143"); // NOI18N
        FormInput.add(jLabel143);
        jLabel143.setBounds(260, 1090, 60, 23);

        Sirkulasi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Akral Hangat", "Akral Dingin", "CRT", "Edema" }));
        Sirkulasi.setName("Sirkulasi"); // NOI18N
        Sirkulasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SirkulasiKeyPressed(evt);
            }
        });
        FormInput.add(Sirkulasi);
        Sirkulasi.setBounds(324, 1090, 117, 23);

        KeteranganSirkulasi.setFocusTraversalPolicyProvider(true);
        KeteranganSirkulasi.setName("KeteranganSirkulasi"); // NOI18N
        KeteranganSirkulasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganSirkulasiKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganSirkulasi);
        KeteranganSirkulasi.setBounds(444, 1090, 132, 23);

        Pulsasi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Kuat", "Lemah", "Lainnya" }));
        Pulsasi.setName("Pulsasi"); // NOI18N
        Pulsasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PulsasiKeyPressed(evt);
            }
        });
        FormInput.add(Pulsasi);
        Pulsasi.setBounds(635, 1090, 91, 23);

        jLabel144.setText("Pulsasi :");
        jLabel144.setName("jLabel144"); // NOI18N
        FormInput.add(jLabel144);
        jLabel144.setBounds(571, 1090, 60, 23);

        KeteranganPulsasi.setFocusTraversalPolicyProvider(true);
        KeteranganPulsasi.setName("KeteranganPulsasi"); // NOI18N
        KeteranganPulsasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganPulsasiKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganPulsasi);
        KeteranganPulsasi.setBounds(729, 1090, 125, 23);

        jLabel97.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel97.setText("Respirasi :");
        jLabel97.setName("jLabel97"); // NOI18N
        FormInput.add(jLabel97);
        jLabel97.setBounds(44, 1120, 170, 23);

        jLabel98.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel98.setText("Pola Napas");
        jLabel98.setName("jLabel98"); // NOI18N
        FormInput.add(jLabel98);
        jLabel98.setBounds(74, 1140, 60, 23);

        jLabel99.setText(":");
        jLabel99.setName("jLabel99"); // NOI18N
        FormInput.add(jLabel99);
        jLabel99.setBounds(0, 1140, 135, 23);

        PolaNapas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Bradipnea", "Tachipnea", "Hiperventilasi", "Othopneu", "Kusmaul", "Apneu" }));
        PolaNapas.setName("PolaNapas"); // NOI18N
        PolaNapas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PolaNapasKeyPressed(evt);
            }
        });
        FormInput.add(PolaNapas);
        PolaNapas.setBounds(139, 1140, 120, 23);

        jLabel145.setText("Jenis Pernapasan :");
        jLabel145.setName("jLabel145"); // NOI18N
        FormInput.add(jLabel145);
        jLabel145.setBounds(260, 1140, 104, 23);

        JenisPernapasan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Pernapasan Dada", "Pernapasan Perut", "Bahu Diangkat", "Cuping Hidung", "Alat Bantu Napas" }));
        JenisPernapasan.setName("JenisPernapasan"); // NOI18N
        JenisPernapasan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JenisPernapasanKeyPressed(evt);
            }
        });
        FormInput.add(JenisPernapasan);
        JenisPernapasan.setBounds(368, 1140, 140, 23);

        KeteranganJenisPernapasan.setFocusTraversalPolicyProvider(true);
        KeteranganJenisPernapasan.setName("KeteranganJenisPernapasan"); // NOI18N
        KeteranganJenisPernapasan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganJenisPernapasanKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganJenisPernapasan);
        KeteranganJenisPernapasan.setBounds(511, 1140, 179, 23);

        Retraksi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada", "Ringan", "Berat" }));
        Retraksi.setName("Retraksi"); // NOI18N
        Retraksi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RetraksiKeyPressed(evt);
            }
        });
        FormInput.add(Retraksi);
        Retraksi.setBounds(754, 1140, 100, 23);

        jLabel146.setText("Retraksi :");
        jLabel146.setName("jLabel146"); // NOI18N
        FormInput.add(jLabel146);
        jLabel146.setBounds(680, 1140, 70, 23);

        jLabel100.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel100.setText("Air Entry");
        jLabel100.setName("jLabel100"); // NOI18N
        FormInput.add(jLabel100);
        jLabel100.setBounds(74, 1170, 60, 23);

        jLabel147.setText(":");
        jLabel147.setName("jLabel147"); // NOI18N
        FormInput.add(jLabel147);
        jLabel147.setBounds(0, 1170, 123, 23);

        AirEntry.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Udara Masuk", "Penurunan Udara Masuk", "Tidak Ada Udara Masuk" }));
        AirEntry.setName("AirEntry"); // NOI18N
        AirEntry.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AirEntryKeyPressed(evt);
            }
        });
        FormInput.add(AirEntry);
        AirEntry.setBounds(127, 1170, 173, 23);

        jLabel148.setText("Merintih :");
        jLabel148.setName("jLabel148"); // NOI18N
        FormInput.add(jLabel148);
        jLabel148.setBounds(300, 1170, 60, 23);

        Merintih.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada", "Terdengar Dengan Stetoskop", "Terdengar Tanpa Stetoskop" }));
        Merintih.setName("Merintih"); // NOI18N
        Merintih.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MerintihKeyPressed(evt);
            }
        });
        FormInput.add(Merintih);
        Merintih.setBounds(364, 1170, 200, 23);

        SuaraNapas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Vesikuler", "Wheezing", "Ronkhi", "Stridor" }));
        SuaraNapas.setName("SuaraNapas"); // NOI18N
        SuaraNapas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SuaraNapasKeyPressed(evt);
            }
        });
        FormInput.add(SuaraNapas);
        SuaraNapas.setBounds(650, 1170, 105, 23);

        jLabel149.setText("Suara Napas :");
        jLabel149.setName("jLabel149"); // NOI18N
        FormInput.add(jLabel149);
        jLabel149.setBounds(567, 1170, 79, 23);

        jLabel150.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel150.setText("Gastrointestinal :");
        jLabel150.setName("jLabel150"); // NOI18N
        FormInput.add(jLabel150);
        jLabel150.setBounds(44, 1200, 170, 23);

        Mulut.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "TAK", "Simetris", "Asimetri", "Bibir Pucat", "Lainnya" }));
        Mulut.setName("Mulut"); // NOI18N
        Mulut.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MulutKeyPressed(evt);
            }
        });
        FormInput.add(Mulut);
        Mulut.setBounds(112, 1220, 107, 23);

        jLabel151.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel151.setText("Mulut");
        jLabel151.setName("jLabel151"); // NOI18N
        FormInput.add(jLabel151);
        jLabel151.setBounds(74, 1220, 60, 23);

        jLabel152.setText(":");
        jLabel152.setName("jLabel152"); // NOI18N
        FormInput.add(jLabel152);
        jLabel152.setBounds(0, 1220, 108, 23);

        KeteranganMulut.setFocusTraversalPolicyProvider(true);
        KeteranganMulut.setName("KeteranganMulut"); // NOI18N
        KeteranganMulut.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganMulutKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganMulut);
        KeteranganMulut.setBounds(222, 1220, 219, 23);

        jLabel153.setText("Lidah :");
        jLabel153.setName("jLabel153"); // NOI18N
        FormInput.add(jLabel153);
        jLabel153.setBounds(417, 1220, 70, 23);

        Lidah.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "TAK", "Kotor", "Gerak Asimetris", "Lainnya" }));
        Lidah.setName("Lidah"); // NOI18N
        Lidah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LidahKeyPressed(evt);
            }
        });
        FormInput.add(Lidah);
        Lidah.setBounds(491, 1220, 130, 23);

        KeteranganLidah.setFocusTraversalPolicyProvider(true);
        KeteranganLidah.setName("KeteranganLidah"); // NOI18N
        KeteranganLidah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganLidahKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganLidah);
        KeteranganLidah.setBounds(624, 1220, 230, 23);

        Tenggorokan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "TAK", "Ada Kelainan" }));
        Tenggorokan.setName("Tenggorokan"); // NOI18N
        Tenggorokan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TenggorokanKeyPressed(evt);
            }
        });
        FormInput.add(Tenggorokan);
        Tenggorokan.setBounds(148, 1250, 115, 23);

        jLabel154.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel154.setText("Tenggorokan");
        jLabel154.setName("jLabel154"); // NOI18N
        FormInput.add(jLabel154);
        jLabel154.setBounds(74, 1250, 90, 23);

        jLabel155.setText(":");
        jLabel155.setName("jLabel155"); // NOI18N
        FormInput.add(jLabel155);
        jLabel155.setBounds(0, 1250, 144, 23);

        KeteranganTenggorokan.setFocusTraversalPolicyProvider(true);
        KeteranganTenggorokan.setName("KeteranganTenggorokan"); // NOI18N
        KeteranganTenggorokan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganTenggorokanKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganTenggorokan);
        KeteranganTenggorokan.setBounds(266, 1250, 212, 23);

        jLabel156.setText("Abdomen :");
        jLabel156.setName("jLabel156"); // NOI18N
        FormInput.add(jLabel156);
        jLabel156.setBounds(475, 1250, 70, 23);

        Abdomen.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Supel", "Asites", "Tegang", "BU", "Lainnya" }));
        Abdomen.setName("Abdomen"); // NOI18N
        Abdomen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AbdomenKeyPressed(evt);
            }
        });
        FormInput.add(Abdomen);
        Abdomen.setBounds(549, 1250, 90, 23);

        KeteranganAbdomen.setFocusTraversalPolicyProvider(true);
        KeteranganAbdomen.setName("KeteranganAbdomen"); // NOI18N
        KeteranganAbdomen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganAbdomenKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganAbdomen);
        KeteranganAbdomen.setBounds(642, 1250, 212, 23);

        jLabel157.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel157.setText("BAB");
        jLabel157.setName("jLabel157"); // NOI18N
        FormInput.add(jLabel157);
        jLabel157.setBounds(74, 1280, 40, 23);

        GastroBAB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Konstipasi", "Melena", "Colostomy", "Diare", "Meco Pertama" }));
        GastroBAB.setName("GastroBAB"); // NOI18N
        GastroBAB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GastroBABKeyPressed(evt);
            }
        });
        FormInput.add(GastroBAB);
        GastroBAB.setBounds(105, 1280, 125, 23);

        jLabel158.setText(":");
        jLabel158.setName("jLabel158"); // NOI18N
        FormInput.add(jLabel158);
        jLabel158.setBounds(0, 1280, 101, 23);

        KeteranganGastroBAB.setFocusTraversalPolicyProvider(true);
        KeteranganGastroBAB.setName("KeteranganGastroBAB"); // NOI18N
        KeteranganGastroBAB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganGastroBABKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganGastroBAB);
        KeteranganGastroBAB.setBounds(233, 1280, 237, 23);

        jLabel159.setText("Warna BAB :");
        jLabel159.setName("jLabel159"); // NOI18N
        FormInput.add(jLabel159);
        jLabel159.setBounds(475, 1280, 70, 23);

        GastroWarnaBAB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Kuning", "Dempul", "Coklat", "Hijau", "Lainnya" }));
        GastroWarnaBAB.setName("GastroWarnaBAB"); // NOI18N
        GastroWarnaBAB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GastroWarnaBABKeyPressed(evt);
            }
        });
        FormInput.add(GastroWarnaBAB);
        GastroWarnaBAB.setBounds(549, 1280, 90, 23);

        KeteranganGastroWarnaBAB.setFocusTraversalPolicyProvider(true);
        KeteranganGastroWarnaBAB.setName("KeteranganGastroWarnaBAB"); // NOI18N
        KeteranganGastroWarnaBAB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganGastroWarnaBABKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganGastroWarnaBAB);
        KeteranganGastroWarnaBAB.setBounds(642, 1280, 212, 23);

        jLabel160.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel160.setText("BAK");
        jLabel160.setName("jLabel160"); // NOI18N
        FormInput.add(jLabel160);
        jLabel160.setBounds(74, 1310, 40, 23);

        GastroBAK.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Hematuri", "Urin Menetes", "Oliguri", "BAK Pertama" }));
        GastroBAK.setName("GastroBAK"); // NOI18N
        GastroBAK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GastroBAKKeyPressed(evt);
            }
        });
        FormInput.add(GastroBAK);
        GastroBAK.setBounds(105, 1310, 120, 23);

        jLabel161.setText(":");
        jLabel161.setName("jLabel161"); // NOI18N
        FormInput.add(jLabel161);
        jLabel161.setBounds(0, 1310, 101, 23);

        KeteranganGastroBAK.setFocusTraversalPolicyProvider(true);
        KeteranganGastroBAK.setName("KeteranganGastroBAK"); // NOI18N
        KeteranganGastroBAK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganGastroBAKKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganGastroBAK);
        KeteranganGastroBAK.setBounds(228, 1310, 220, 23);

        jLabel162.setText("Warna BAK :");
        jLabel162.setName("jLabel162"); // NOI18N
        FormInput.add(jLabel162);
        jLabel162.setBounds(442, 1310, 80, 23);

        GastroWarnaBAK.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Jernih", "Kuning", "Kuning Pekat", "Lainnya" }));
        GastroWarnaBAK.setName("GastroWarnaBAK"); // NOI18N
        GastroWarnaBAK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GastroWarnaBAKKeyPressed(evt);
            }
        });
        FormInput.add(GastroWarnaBAK);
        GastroWarnaBAK.setBounds(526, 1310, 115, 23);

        KeteranganGastroWarnaBAK.setFocusTraversalPolicyProvider(true);
        KeteranganGastroWarnaBAK.setName("KeteranganGastroWarnaBAK"); // NOI18N
        KeteranganGastroWarnaBAK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganGastroWarnaBAKKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganGastroWarnaBAK);
        KeteranganGastroWarnaBAK.setBounds(644, 1310, 210, 23);

        jLabel163.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel163.setText("Neurologi :");
        jLabel163.setName("jLabel163"); // NOI18N
        FormInput.add(jLabel163);
        jLabel163.setBounds(44, 1340, 170, 23);

        jLabel164.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel164.setText("Posisi Mata ");
        jLabel164.setName("jLabel164"); // NOI18N
        FormInput.add(jLabel164);
        jLabel164.setBounds(74, 1360, 60, 23);

        jLabel165.setText(":");
        jLabel165.setName("jLabel165"); // NOI18N
        FormInput.add(jLabel165);
        jLabel165.setBounds(0, 1360, 136, 23);

        PosisiMata.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Simetris", "Asimetris" }));
        PosisiMata.setName("PosisiMata"); // NOI18N
        PosisiMata.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PosisiMataKeyPressed(evt);
            }
        });
        FormInput.add(PosisiMata);
        PosisiMata.setBounds(140, 1360, 100, 23);

        jLabel166.setText("Kelopak Mata :");
        jLabel166.setName("jLabel166"); // NOI18N
        FormInput.add(jLabel166);
        jLabel166.setBounds(240, 1360, 86, 23);

        KelopakMata.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "TAK", "Edema", "Cekung", "Lainnya" }));
        KelopakMata.setName("KelopakMata"); // NOI18N
        KelopakMata.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KelopakMataKeyPressed(evt);
            }
        });
        FormInput.add(KelopakMata);
        KelopakMata.setBounds(330, 1360, 90, 23);

        KeteranganKelopakMata.setFocusTraversalPolicyProvider(true);
        KeteranganKelopakMata.setName("KeteranganKelopakMata"); // NOI18N
        KeteranganKelopakMata.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKelopakMataKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganKelopakMata);
        KeteranganKelopakMata.setBounds(423, 1360, 254, 23);

        BesarPupil.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Isokor", "Anisokor" }));
        BesarPupil.setName("BesarPupil"); // NOI18N
        BesarPupil.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BesarPupilKeyPressed(evt);
            }
        });
        FormInput.add(BesarPupil);
        BesarPupil.setBounds(757, 1360, 97, 23);

        jLabel167.setText("Besar Pupil :");
        jLabel167.setName("jLabel167"); // NOI18N
        FormInput.add(jLabel167);
        jLabel167.setBounds(683, 1360, 70, 23);

        Konjungtiva.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "TAK", "Anemis", "Konjungtivitis", "Lainnya" }));
        Konjungtiva.setName("Konjungtiva"); // NOI18N
        Konjungtiva.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KonjungtivaKeyPressed(evt);
            }
        });
        FormInput.add(Konjungtiva);
        Konjungtiva.setBounds(142, 1390, 120, 23);

        jLabel168.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel168.setText("Konjungtiva");
        jLabel168.setName("jLabel168"); // NOI18N
        FormInput.add(jLabel168);
        jLabel168.setBounds(74, 1390, 90, 23);

        jLabel169.setText(":");
        jLabel169.setName("jLabel169"); // NOI18N
        FormInput.add(jLabel169);
        jLabel169.setBounds(0, 1390, 138, 23);

        KeteranganKonjungtiva.setFocusTraversalPolicyProvider(true);
        KeteranganKonjungtiva.setName("KeteranganKonjungtiva"); // NOI18N
        KeteranganKonjungtiva.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKonjungtivaKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganKonjungtiva);
        KeteranganKonjungtiva.setBounds(265, 1390, 209, 23);

        jLabel170.setText("Sklera :");
        jLabel170.setName("jLabel170"); // NOI18N
        FormInput.add(jLabel170);
        jLabel170.setBounds(475, 1390, 50, 23);

        Sklera.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "TAK", "Ikterik", "Perdarahan", "Lainnya" }));
        Sklera.setName("Sklera"); // NOI18N
        Sklera.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkleraKeyPressed(evt);
            }
        });
        FormInput.add(Sklera);
        Sklera.setBounds(529, 1390, 113, 23);

        KeteranganSklera.setFocusTraversalPolicyProvider(true);
        KeteranganSklera.setName("KeteranganSklera"); // NOI18N
        KeteranganSklera.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganSkleraKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganSklera);
        KeteranganSklera.setBounds(645, 1390, 209, 23);

        Pendengaran.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "TAK", "Asimetris", "Keluar Cairan", "Lainnya" }));
        Pendengaran.setName("Pendengaran"); // NOI18N
        Pendengaran.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PendengaranKeyPressed(evt);
            }
        });
        FormInput.add(Pendengaran);
        Pendengaran.setBounds(149, 1420, 120, 23);

        jLabel171.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel171.setText("Pendengaran");
        jLabel171.setName("jLabel171"); // NOI18N
        FormInput.add(jLabel171);
        jLabel171.setBounds(74, 1420, 90, 23);

        jLabel172.setText(":");
        jLabel172.setName("jLabel172"); // NOI18N
        FormInput.add(jLabel172);
        jLabel172.setBounds(0, 1420, 145, 23);

        KeteranganPendengaran.setFocusTraversalPolicyProvider(true);
        KeteranganPendengaran.setName("KeteranganPendengaran"); // NOI18N
        KeteranganPendengaran.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganPendengaranKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganPendengaran);
        KeteranganPendengaran.setBounds(272, 1420, 190, 23);

        jLabel173.setText("Penciuman :");
        jLabel173.setName("jLabel173"); // NOI18N
        FormInput.add(jLabel173);
        jLabel173.setBounds(457, 1420, 80, 23);

        Penciuman.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "TAK", "Asimetris", "Keluar Cairan", "Lainnya" }));
        Penciuman.setName("Penciuman"); // NOI18N
        Penciuman.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenciumanKeyPressed(evt);
            }
        });
        FormInput.add(Penciuman);
        Penciuman.setBounds(541, 1420, 120, 23);

        KeteranganPenciuman.setFocusTraversalPolicyProvider(true);
        KeteranganPenciuman.setName("KeteranganPenciuman"); // NOI18N
        KeteranganPenciuman.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganPenciumanKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganPenciuman);
        KeteranganPenciuman.setBounds(664, 1420, 190, 23);

        jLabel174.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel174.setText("Integument :");
        jLabel174.setName("jLabel174"); // NOI18N
        FormInput.add(jLabel174);
        jLabel174.setBounds(44, 1450, 170, 23);

        jLabel176.setText(":");
        jLabel176.setName("jLabel176"); // NOI18N
        FormInput.add(jLabel176);
        jLabel176.setBounds(0, 1470, 138, 23);

        WarnaKulit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Pucat", "Sianosis", "Normal", "Lainnya" }));
        WarnaKulit.setName("WarnaKulit"); // NOI18N
        WarnaKulit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                WarnaKulitKeyPressed(evt);
            }
        });
        FormInput.add(WarnaKulit);
        WarnaKulit.setBounds(142, 1470, 90, 23);

        jLabel175.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel175.setText("Warna Kulit");
        jLabel175.setName("jLabel175"); // NOI18N
        FormInput.add(jLabel175);
        jLabel175.setBounds(74, 1470, 80, 23);

        jLabel177.setText("Vernic Kaseosa :");
        jLabel177.setName("jLabel177"); // NOI18N
        FormInput.add(jLabel177);
        jLabel177.setBounds(366, 1470, 100, 23);

        VernicKaseosa.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ada", "Tidak Ada", "Lainnya" }));
        VernicKaseosa.setName("VernicKaseosa"); // NOI18N
        VernicKaseosa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                VernicKaseosaKeyPressed(evt);
            }
        });
        FormInput.add(VernicKaseosa);
        VernicKaseosa.setBounds(470, 1470, 100, 23);

        Lanugo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada", "Banyak", "Tipis", "Bercak-bercak Tanpa Lanugo", "Sebagian Besar Tanpa Lanugo" }));
        Lanugo.setName("Lanugo"); // NOI18N
        Lanugo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LanugoKeyPressed(evt);
            }
        });
        FormInput.add(Lanugo);
        Lanugo.setBounds(121, 1500, 205, 23);

        jLabel178.setText("Turgor :");
        jLabel178.setName("jLabel178"); // NOI18N
        FormInput.add(jLabel178);
        jLabel178.setBounds(700, 1470, 60, 23);

        KeteranganVernicKaseosa.setFocusTraversalPolicyProvider(true);
        KeteranganVernicKaseosa.setName("KeteranganVernicKaseosa"); // NOI18N
        KeteranganVernicKaseosa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganVernicKaseosaKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganVernicKaseosa);
        KeteranganVernicKaseosa.setBounds(573, 1470, 135, 23);

        KeteranganWarnaKulit.setFocusTraversalPolicyProvider(true);
        KeteranganWarnaKulit.setName("KeteranganWarnaKulit"); // NOI18N
        KeteranganWarnaKulit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganWarnaKulitKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganWarnaKulit);
        KeteranganWarnaKulit.setBounds(235, 1470, 135, 23);

        Turgor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Baik", "Sedang", "Buruk" }));
        Turgor.setName("Turgor"); // NOI18N
        Turgor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TurgorKeyPressed(evt);
            }
        });
        FormInput.add(Turgor);
        Turgor.setBounds(764, 1470, 90, 23);

        jLabel180.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel180.setText("Lanugo");
        jLabel180.setName("jLabel180"); // NOI18N
        FormInput.add(jLabel180);
        jLabel180.setBounds(74, 1500, 80, 23);

        jLabel179.setText(":");
        jLabel179.setName("jLabel179"); // NOI18N
        FormInput.add(jLabel179);
        jLabel179.setBounds(0, 1500, 117, 23);

        jLabel181.setText("Kulit :");
        jLabel181.setName("jLabel181"); // NOI18N
        FormInput.add(jLabel181);
        jLabel181.setBounds(322, 1500, 50, 23);

        Kulit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Rash/Kemerahan", "Luka", "Memar", "Ptekie", "Bula" }));
        Kulit.setName("Kulit"); // NOI18N
        Kulit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KulitKeyPressed(evt);
            }
        });
        FormInput.add(Kulit);
        Kulit.setBounds(376, 1500, 140, 23);

        RisikoDekubitas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Jaringan/Elastisitas Kurang", "Imobilisasi" }));
        RisikoDekubitas.setName("RisikoDekubitas"); // NOI18N
        RisikoDekubitas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RisikoDekubitasKeyPressed(evt);
            }
        });
        FormInput.add(RisikoDekubitas);
        RisikoDekubitas.setBounds(662, 1500, 192, 23);

        jLabel182.setText("Kriteria Risiko Dekubitas :");
        jLabel182.setName("jLabel182"); // NOI18N
        FormInput.add(jLabel182);
        jLabel182.setBounds(518, 1500, 140, 23);

        jLabel183.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel183.setText("Reproduksi");
        jLabel183.setName("jLabel183"); // NOI18N
        FormInput.add(jLabel183);
        jLabel183.setBounds(44, 1530, 70, 23);

        jLabel202.setText(":");
        jLabel202.setName("jLabel202"); // NOI18N
        FormInput.add(jLabel202);
        jLabel202.setBounds(0, 1530, 105, 23);

        Reproduksi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Hipospadia", "Epispadia", "Fimosis", "Hidrokel", "Keputihan", "Vagina Skintag", "Lainnya" }));
        Reproduksi.setName("Reproduksi"); // NOI18N
        Reproduksi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ReproduksiKeyPressed(evt);
            }
        });
        FormInput.add(Reproduksi);
        Reproduksi.setBounds(109, 1530, 125, 23);

        KeteranganReproduksi.setFocusTraversalPolicyProvider(true);
        KeteranganReproduksi.setName("KeteranganReproduksi"); // NOI18N
        KeteranganReproduksi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganReproduksiKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganReproduksi);
        KeteranganReproduksi.setBounds(237, 1530, 617, 23);

        jLabel184.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel184.setText("Sistem Muskuloskeletal :");
        jLabel184.setName("jLabel184"); // NOI18N
        FormInput.add(jLabel184);
        jLabel184.setBounds(44, 1560, 170, 23);

        RekoilTelinga.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Rekoil Lambat", "Rekoil Cepat", "Rekoil Segera", "Lainnya" }));
        RekoilTelinga.setName("RekoilTelinga"); // NOI18N
        RekoilTelinga.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RekoilTelingaKeyPressed(evt);
            }
        });
        FormInput.add(RekoilTelinga);
        RekoilTelinga.setBounds(154, 1580, 120, 23);

        jLabel185.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel185.setText("Rekoil Telinga");
        jLabel185.setName("jLabel185"); // NOI18N
        FormInput.add(jLabel185);
        jLabel185.setBounds(74, 1580, 90, 23);

        jLabel186.setText(":");
        jLabel186.setName("jLabel186"); // NOI18N
        FormInput.add(jLabel186);
        jLabel186.setBounds(0, 1580, 150, 23);

        jLabel187.setText("Lengan :");
        jLabel187.setName("jLabel187"); // NOI18N
        FormInput.add(jLabel187);
        jLabel187.setBounds(432, 1580, 60, 23);

        Lengan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Fleksi", "Ekstensi", "Pergerakan Aktif", "Pergerakan Tidak Aktif", "Lainnya" }));
        Lengan.setName("Lengan"); // NOI18N
        Lengan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LenganKeyPressed(evt);
            }
        });
        FormInput.add(Lengan);
        Lengan.setBounds(496, 1580, 165, 23);

        KeteranganLengan.setFocusTraversalPolicyProvider(true);
        KeteranganLengan.setName("KeteranganLengan"); // NOI18N
        KeteranganLengan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganLenganKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganLengan);
        KeteranganLengan.setBounds(664, 1580, 190, 23);

        KeteranganRekoilTelinga.setFocusTraversalPolicyProvider(true);
        KeteranganRekoilTelinga.setName("KeteranganRekoilTelinga"); // NOI18N
        KeteranganRekoilTelinga.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganRekoilTelingaKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganRekoilTelinga);
        KeteranganRekoilTelinga.setBounds(277, 1580, 157, 23);

        Tungkai.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Fleksi", "Ekstensi", "Pergerakan Aktif", "Pergerakan Tidak Aktif", "Lainnya" }));
        Tungkai.setName("Tungkai"); // NOI18N
        Tungkai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TungkaiKeyPressed(evt);
            }
        });
        FormInput.add(Tungkai);
        Tungkai.setBounds(122, 1610, 165, 23);

        jLabel188.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel188.setText("Tungkai");
        jLabel188.setName("jLabel188"); // NOI18N
        FormInput.add(jLabel188);
        jLabel188.setBounds(74, 1610, 90, 23);

        jLabel189.setText(":");
        jLabel189.setName("jLabel189"); // NOI18N
        FormInput.add(jLabel189);
        jLabel189.setBounds(0, 1610, 118, 23);

        jLabel190.setText("Garis Telapak Kaki :");
        jLabel190.setName("jLabel190"); // NOI18N
        FormInput.add(jLabel190);
        jLabel190.setBounds(540, 1610, 120, 23);

        GarisTelapakKaki.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tipis", "Garis Transversal Anterior", "Garis 2/3 Anterior", "Seluruh Telapak Kaki" }));
        GarisTelapakKaki.setName("GarisTelapakKaki"); // NOI18N
        GarisTelapakKaki.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GarisTelapakKakiKeyPressed(evt);
            }
        });
        FormInput.add(GarisTelapakKaki);
        GarisTelapakKaki.setBounds(664, 1610, 190, 23);

        KeteranganTungkai.setFocusTraversalPolicyProvider(true);
        KeteranganTungkai.setName("KeteranganTungkai"); // NOI18N
        KeteranganTungkai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganTungkaiKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganTungkai);
        KeteranganTungkai.setBounds(290, 1610, 260, 23);

        jSeparator4.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator4.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator4.setName("jSeparator4"); // NOI18N
        FormInput.add(jSeparator4);
        jSeparator4.setBounds(0, 1640, 880, 1);

        jLabel191.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel191.setText("III. RIWAYAT PSIKOLOGIS – SOSIAL – EKONOMI – BUDAYA – SPIRITUAL (ORANGTUA)");
        jLabel191.setName("jLabel191"); // NOI18N
        FormInput.add(jLabel191);
        jLabel191.setBounds(10, 1640, 470, 23);

        jLabel203.setText(":");
        jLabel203.setName("jLabel203"); // NOI18N
        FormInput.add(jLabel203);
        jLabel203.setBounds(0, 1660, 137, 23);

        KondisiPsikologis.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada Masalah", "Marah", "Takut", "Depresi", "Cepat Lelah", "Cemas", "Gelisah", "Sulit Tidur", "Lainnya" }));
        KondisiPsikologis.setName("KondisiPsikologis"); // NOI18N
        KondisiPsikologis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KondisiPsikologisKeyPressed(evt);
            }
        });
        FormInput.add(KondisiPsikologis);
        KondisiPsikologis.setBounds(141, 1660, 150, 23);

        jLabel192.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel192.setText("Kondisi Psikologis ");
        jLabel192.setName("jLabel192"); // NOI18N
        FormInput.add(jLabel192);
        jLabel192.setBounds(44, 1660, 110, 23);

        jLabel193.setText("Menerima Kondisi Bayi Saat Ini :");
        jLabel193.setName("jLabel193"); // NOI18N
        FormInput.add(jLabel193);
        jLabel193.setBounds(545, 1660, 170, 23);

        MenerimaKondisiBayi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Menerima", "Tidak Menerima" }));
        MenerimaKondisiBayi.setName("MenerimaKondisiBayi"); // NOI18N
        MenerimaKondisiBayi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MenerimaKondisiBayiKeyPressed(evt);
            }
        });
        FormInput.add(MenerimaKondisiBayi);
        MenerimaKondisiBayi.setBounds(719, 1660, 135, 23);

        jLabel194.setText("Gangguan Jiwa Di Masa Lalu :");
        jLabel194.setName("jLabel194"); // NOI18N
        FormInput.add(jLabel194);
        jLabel194.setBounds(292, 1660, 160, 23);

        GangguanJiwa.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        GangguanJiwa.setName("GangguanJiwa"); // NOI18N
        GangguanJiwa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GangguanJiwaKeyPressed(evt);
            }
        });
        FormInput.add(GangguanJiwa);
        GangguanJiwa.setBounds(456, 1660, 87, 23);

        jLabel204.setText(":");
        jLabel204.setName("jLabel204"); // NOI18N
        FormInput.add(jLabel204);
        jLabel204.setBounds(0, 1690, 125, 23);

        StatusMenikah.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Menikah", "Belum Menikah", "Janda", "Duda" }));
        StatusMenikah.setName("StatusMenikah"); // NOI18N
        StatusMenikah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                StatusMenikahKeyPressed(evt);
            }
        });
        FormInput.add(StatusMenikah);
        StatusMenikah.setBounds(129, 1690, 127, 23);

        jLabel195.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel195.setText("Status Menikah ");
        jLabel195.setName("jLabel195"); // NOI18N
        FormInput.add(jLabel195);
        jLabel195.setBounds(44, 1690, 110, 23);

        jLabel196.setText("Masalah Pernikahan :");
        jLabel196.setName("jLabel196"); // NOI18N
        FormInput.add(jLabel196);
        jLabel196.setBounds(255, 1690, 120, 23);

        MasalahPernikahan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada", "Cerai/Istri Baru", "Simpanan", "Lainnya" }));
        MasalahPernikahan.setName("MasalahPernikahan"); // NOI18N
        MasalahPernikahan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MasalahPernikahanKeyPressed(evt);
            }
        });
        FormInput.add(MasalahPernikahan);
        MasalahPernikahan.setBounds(379, 1690, 130, 23);

        KeteranganMasalahPernikahan.setFocusTraversalPolicyProvider(true);
        KeteranganMasalahPernikahan.setName("KeteranganMasalahPernikahan"); // NOI18N
        KeteranganMasalahPernikahan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganMasalahPernikahanKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganMasalahPernikahan);
        KeteranganMasalahPernikahan.setBounds(512, 1690, 150, 23);

        KeteranganTinggalBersama.setFocusTraversalPolicyProvider(true);
        KeteranganTinggalBersama.setName("KeteranganTinggalBersama"); // NOI18N
        KeteranganTinggalBersama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganTinggalBersamaKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganTinggalBersama);
        KeteranganTinggalBersama.setBounds(764, 1750, 90, 23);

        jLabel197.setText("Pekerjaan :");
        jLabel197.setName("jLabel197"); // NOI18N
        FormInput.add(jLabel197);
        jLabel197.setBounds(650, 1690, 80, 23);

        Agama.setFocusTraversalPolicyProvider(true);
        Agama.setName("Agama"); // NOI18N
        Agama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AgamaKeyPressed(evt);
            }
        });
        FormInput.add(Agama);
        Agama.setBounds(89, 1720, 120, 23);

        jLabel198.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel198.setText("Agama");
        jLabel198.setName("jLabel198"); // NOI18N
        FormInput.add(jLabel198);
        jLabel198.setBounds(44, 1720, 70, 23);

        jLabel205.setText(":");
        jLabel205.setName("jLabel205"); // NOI18N
        FormInput.add(jLabel205);
        jLabel205.setBounds(0, 1720, 85, 23);

        jLabel199.setText("Nilai-nilai Kepercayaan/Budaya Yang Perlu Diperhatikan :");
        jLabel199.setName("jLabel199"); // NOI18N
        FormInput.add(jLabel199);
        jLabel199.setBounds(205, 1720, 295, 23);

        NilaiKepercayaan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada", "Ada" }));
        NilaiKepercayaan.setName("NilaiKepercayaan"); // NOI18N
        NilaiKepercayaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NilaiKepercayaanKeyPressed(evt);
            }
        });
        FormInput.add(NilaiKepercayaan);
        NilaiKepercayaan.setBounds(504, 1720, 100, 23);

        KeteranganNilaiKepercayaan.setFocusTraversalPolicyProvider(true);
        KeteranganNilaiKepercayaan.setName("KeteranganNilaiKepercayaan"); // NOI18N
        KeteranganNilaiKepercayaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganNilaiKepercayaanKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganNilaiKepercayaan);
        KeteranganNilaiKepercayaan.setBounds(607, 1720, 247, 23);

        jLabel207.setText(":");
        jLabel207.setName("jLabel207"); // NOI18N
        FormInput.add(jLabel207);
        jLabel207.setBounds(0, 1750, 74, 23);

        Suku.setFocusTraversalPolicyProvider(true);
        Suku.setName("Suku"); // NOI18N
        Suku.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SukuKeyPressed(evt);
            }
        });
        FormInput.add(Suku);
        Suku.setBounds(78, 1750, 111, 23);

        jLabel206.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel206.setText("Suku");
        jLabel206.setName("jLabel206"); // NOI18N
        FormInput.add(jLabel206);
        jLabel206.setBounds(44, 1750, 70, 23);

        jLabel208.setText("Pendidikan :");
        jLabel208.setName("jLabel208"); // NOI18N
        FormInput.add(jLabel208);
        jLabel208.setBounds(187, 1750, 75, 23);

        Pendidikan.setFocusTraversalPolicyProvider(true);
        Pendidikan.setName("Pendidikan"); // NOI18N
        Pendidikan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PendidikanKeyPressed(evt);
            }
        });
        FormInput.add(Pendidikan);
        Pendidikan.setBounds(266, 1750, 91, 23);

        jLabel209.setText("Tinggal Bersama :");
        jLabel209.setName("jLabel209"); // NOI18N
        FormInput.add(jLabel209);
        jLabel209.setBounds(542, 1750, 110, 23);

        TinggalBersama.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Suami", "Orang Tua", "Teman", "Anak", "Sendiri", "Lainnya" }));
        TinggalBersama.setName("TinggalBersama"); // NOI18N
        TinggalBersama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TinggalBersamaKeyPressed(evt);
            }
        });
        FormInput.add(TinggalBersama);
        TinggalBersama.setBounds(656, 1750, 105, 23);

        ResponEmosi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Takut Terhadap Terapi / Pembedahan / Lingkungan RS", "Marah / Tegang", "Sedih", "Menangis", "Senang", "Takut", "Mampu Menahan Diri", "Cemas", "Rendah Diri", "Gelisah", "Tenang", "Mudah Tersinggung" }));
        ResponEmosi.setSelectedIndex(10);
        ResponEmosi.setName("ResponEmosi"); // NOI18N
        ResponEmosi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ResponEmosiKeyPressed(evt);
            }
        });
        FormInput.add(ResponEmosi);
        ResponEmosi.setBounds(502, 1780, 320, 23);

        jLabel210.setText("Respon Emosi :");
        jLabel210.setName("jLabel210"); // NOI18N
        FormInput.add(jLabel210);
        jLabel210.setBounds(410, 1780, 88, 23);

        HubunganAnggotaKeluarga.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Harmonis", "Kurang Harmonis", "Tidak Harmonis", "Konflik Besar" }));
        HubunganAnggotaKeluarga.setName("HubunganAnggotaKeluarga"); // NOI18N
        HubunganAnggotaKeluarga.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HubunganAnggotaKeluargaKeyPressed(evt);
            }
        });
        FormInput.add(HubunganAnggotaKeluarga);
        HubunganAnggotaKeluarga.setBounds(268, 1780, 140, 23);

        jLabel211.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel211.setText("Hubungan Pasien dengan Anggota Keluarga");
        jLabel211.setName("jLabel211"); // NOI18N
        FormInput.add(jLabel211);
        jLabel211.setBounds(44, 1780, 240, 23);

        Pekerjaan.setFocusTraversalPolicyProvider(true);
        Pekerjaan.setName("Pekerjaan"); // NOI18N
        Pekerjaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PekerjaanKeyPressed(evt);
            }
        });
        FormInput.add(Pekerjaan);
        Pekerjaan.setBounds(734, 1690, 120, 23);

        Pembayaran.setFocusTraversalPolicyProvider(true);
        Pembayaran.setName("Pembayaran"); // NOI18N
        Pembayaran.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PembayaranKeyPressed(evt);
            }
        });
        FormInput.add(Pembayaran);
        Pembayaran.setBounds(442, 1750, 110, 23);

        jLabel212.setText("Pembayaran :");
        jLabel212.setName("jLabel212"); // NOI18N
        FormInput.add(jLabel212);
        jLabel212.setBounds(358, 1750, 80, 23);

        jLabel213.setText(":");
        jLabel213.setName("jLabel213"); // NOI18N
        FormInput.add(jLabel213);
        jLabel213.setBounds(0, 1780, 264, 23);

        jSeparator5.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator5.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator5.setName("jSeparator5"); // NOI18N
        FormInput.add(jSeparator5);
        jSeparator5.setBounds(0, 1810, 880, 1);

        jLabel214.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel214.setText("IV. KEBUTUHAN KOMUNIKASI DAN BELAJAR/EDUKASI (ORANGTUA)");
        jLabel214.setName("jLabel214"); // NOI18N
        FormInput.add(jLabel214);
        jLabel214.setBounds(10, 1810, 400, 23);

        jLabel215.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel215.setText("Bahasa Sehari-hari ");
        jLabel215.setName("jLabel215"); // NOI18N
        FormInput.add(jLabel215);
        jLabel215.setBounds(44, 1830, 120, 23);

        jLabel216.setText(":");
        jLabel216.setName("jLabel216"); // NOI18N
        FormInput.add(jLabel216);
        jLabel216.setBounds(0, 1830, 143, 23);

        BahasaSehari.setFocusTraversalPolicyProvider(true);
        BahasaSehari.setName("BahasaSehari"); // NOI18N
        BahasaSehari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BahasaSehariKeyPressed(evt);
            }
        });
        FormInput.add(BahasaSehari);
        BahasaSehari.setBounds(147, 1830, 110, 23);

        jLabel217.setText("Kemampuan Baca & Tulis :");
        jLabel217.setName("jLabel217"); // NOI18N
        FormInput.add(jLabel217);
        jLabel217.setBounds(259, 1830, 140, 23);

        KemampuanBacaTulis.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Baik", "Kurang", "Tidak Bisa" }));
        KemampuanBacaTulis.setName("KemampuanBacaTulis"); // NOI18N
        KemampuanBacaTulis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KemampuanBacaTulisKeyPressed(evt);
            }
        });
        FormInput.add(KemampuanBacaTulis);
        KemampuanBacaTulis.setBounds(403, 1830, 100, 23);

        jLabel218.setText("Butuh Penerjamah :");
        jLabel218.setName("jLabel218"); // NOI18N
        FormInput.add(jLabel218);
        jLabel218.setBounds(502, 1830, 110, 23);

        ButuhPenerjemah.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        ButuhPenerjemah.setName("ButuhPenerjemah"); // NOI18N
        ButuhPenerjemah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ButuhPenerjemahKeyPressed(evt);
            }
        });
        FormInput.add(ButuhPenerjemah);
        ButuhPenerjemah.setBounds(616, 1830, 80, 23);

        jLabel219.setText("Jika Ya :");
        jLabel219.setName("jLabel219"); // NOI18N
        FormInput.add(jLabel219);
        jLabel219.setBounds(696, 1830, 50, 23);

        KeteranganButuhPenerjemah.setFocusTraversalPolicyProvider(true);
        KeteranganButuhPenerjemah.setName("KeteranganButuhPenerjemah"); // NOI18N
        KeteranganButuhPenerjemah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganButuhPenerjemahKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganButuhPenerjemah);
        KeteranganButuhPenerjemah.setBounds(750, 1830, 104, 23);

        jLabel220.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel220.setText(",");
        jLabel220.setName("jLabel220"); // NOI18N
        FormInput.add(jLabel220);
        jLabel220.setBounds(348, 1860, 20, 23);

        jLabel221.setText(":");
        jLabel221.setName("jLabel221"); // NOI18N
        FormInput.add(jLabel221);
        jLabel221.setBounds(0, 1860, 252, 23);

        TerdapatHambatanBelajar.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        TerdapatHambatanBelajar.setName("TerdapatHambatanBelajar"); // NOI18N
        TerdapatHambatanBelajar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TerdapatHambatanBelajarKeyPressed(evt);
            }
        });
        FormInput.add(TerdapatHambatanBelajar);
        TerdapatHambatanBelajar.setBounds(256, 1860, 90, 23);

        jLabel222.setText("Jika Ya :");
        jLabel222.setName("jLabel222"); // NOI18N
        FormInput.add(jLabel222);
        jLabel222.setBounds(336, 1860, 60, 23);

        HambatanBelajar.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Gangguan Pendengaran", "Gangguan Penglihatan", "Gangguan Kognitif", "Gangguan Fisik", "Gangguan Emosi", "Keterbatasan Bahasa", "Keterbatasan Budaya", "Keterbatasan Spiritual", "Agama", "Lainnya" }));
        HambatanBelajar.setName("HambatanBelajar"); // NOI18N
        HambatanBelajar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HambatanBelajarKeyPressed(evt);
            }
        });
        FormInput.add(HambatanBelajar);
        HambatanBelajar.setBounds(400, 1860, 177, 23);

        KeteranganHambatanBelajar.setFocusTraversalPolicyProvider(true);
        KeteranganHambatanBelajar.setName("KeteranganHambatanBelajar"); // NOI18N
        KeteranganHambatanBelajar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganHambatanBelajarKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganHambatanBelajar);
        KeteranganHambatanBelajar.setBounds(580, 1860, 274, 23);

        jLabel224.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel224.setText("Terdapat Hambatan Dalam Pembelajaran");
        jLabel224.setName("jLabel224"); // NOI18N
        FormInput.add(jLabel224);
        jLabel224.setBounds(44, 1860, 240, 23);

        jLabel225.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel225.setText(",");
        jLabel225.setName("jLabel225"); // NOI18N
        FormInput.add(jLabel225);
        jLabel225.setBounds(698, 1830, 30, 23);

        jLabel223.setText(":");
        jLabel223.setName("jLabel223"); // NOI18N
        FormInput.add(jLabel223);
        jLabel223.setBounds(0, 1890, 160, 23);

        HambatanCaraBicara.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Gangguan Bicara" }));
        HambatanCaraBicara.setName("HambatanCaraBicara"); // NOI18N
        HambatanCaraBicara.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HambatanCaraBicaraKeyPressed(evt);
            }
        });
        FormInput.add(HambatanCaraBicara);
        HambatanCaraBicara.setBounds(164, 1890, 150, 23);

        HambatanBahasaIsyarat.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        HambatanBahasaIsyarat.setName("HambatanBahasaIsyarat"); // NOI18N
        HambatanBahasaIsyarat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HambatanBahasaIsyaratKeyPressed(evt);
            }
        });
        FormInput.add(HambatanBahasaIsyarat);
        HambatanBahasaIsyarat.setBounds(474, 1890, 90, 23);

        jLabel226.setText("Hambatan Bahasa Isyarat :");
        jLabel226.setName("jLabel226"); // NOI18N
        FormInput.add(jLabel226);
        jLabel226.setBounds(320, 1890, 150, 23);

        jLabel227.setText("Cara Belajar Yang Disukai :");
        jLabel227.setName("jLabel227"); // NOI18N
        FormInput.add(jLabel227);
        jLabel227.setBounds(570, 1890, 150, 23);

        CaraBelajarDisukai.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Audio", "Lisan", "Visual", "Demonstrasi", "Tulisan" }));
        CaraBelajarDisukai.setName("CaraBelajarDisukai"); // NOI18N
        CaraBelajarDisukai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CaraBelajarDisukaiKeyPressed(evt);
            }
        });
        FormInput.add(CaraBelajarDisukai);
        CaraBelajarDisukai.setBounds(724, 1890, 130, 23);

        jLabel228.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel228.setText("Hambatan Cara Bicara");
        jLabel228.setName("jLabel228"); // NOI18N
        FormInput.add(jLabel228);
        jLabel228.setBounds(44, 1890, 140, 23);

        jLabel229.setText(":");
        jLabel229.setName("jLabel229"); // NOI18N
        FormInput.add(jLabel229);
        jLabel229.setBounds(0, 1920, 202, 23);

        KesediaanMenerimaInformasi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        KesediaanMenerimaInformasi.setName("KesediaanMenerimaInformasi"); // NOI18N
        KesediaanMenerimaInformasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KesediaanMenerimaInformasiKeyPressed(evt);
            }
        });
        FormInput.add(KesediaanMenerimaInformasi);
        KesediaanMenerimaInformasi.setBounds(206, 1920, 90, 23);

        KeteranganKesediaanMenerimaInformasi.setFocusTraversalPolicyProvider(true);
        KeteranganKesediaanMenerimaInformasi.setName("KeteranganKesediaanMenerimaInformasi"); // NOI18N
        KeteranganKesediaanMenerimaInformasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKesediaanMenerimaInformasiKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganKesediaanMenerimaInformasi);
        KeteranganKesediaanMenerimaInformasi.setBounds(299, 1920, 273, 23);

        jLabel230.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel230.setText("Kesediaan Menerima Informasi");
        jLabel230.setName("jLabel230"); // NOI18N
        FormInput.add(jLabel230);
        jLabel230.setBounds(44, 1920, 170, 23);

        jLabel231.setText("Pemahaman Tentang Nutrisi/Diet :");
        jLabel231.setName("jLabel231"); // NOI18N
        FormInput.add(jLabel231);
        jLabel231.setBounds(580, 1920, 180, 23);

        PemahamanNutrisi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        PemahamanNutrisi.setName("PemahamanNutrisi"); // NOI18N
        PemahamanNutrisi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PemahamanNutrisiKeyPressed(evt);
            }
        });
        FormInput.add(PemahamanNutrisi);
        PemahamanNutrisi.setBounds(764, 1920, 90, 23);

        jLabel232.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel232.setText("Pemahaman Tentang Penyakit");
        jLabel232.setName("jLabel232"); // NOI18N
        FormInput.add(jLabel232);
        jLabel232.setBounds(44, 1950, 180, 23);

        PemahamanPenyakit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        PemahamanPenyakit.setName("PemahamanPenyakit"); // NOI18N
        PemahamanPenyakit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PemahamanPenyakitKeyPressed(evt);
            }
        });
        FormInput.add(PemahamanPenyakit);
        PemahamanPenyakit.setBounds(202, 1950, 90, 23);

        jLabel233.setText(":");
        jLabel233.setName("jLabel233"); // NOI18N
        FormInput.add(jLabel233);
        jLabel233.setBounds(0, 1950, 198, 23);

        jLabel234.setText("Pemahaman Tentang Perawatan :");
        jLabel234.setName("jLabel234"); // NOI18N
        FormInput.add(jLabel234);
        jLabel234.setBounds(580, 1950, 180, 23);

        PemahamanPerawatan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        PemahamanPerawatan.setName("PemahamanPerawatan"); // NOI18N
        PemahamanPerawatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PemahamanPerawatanKeyPressed(evt);
            }
        });
        FormInput.add(PemahamanPerawatan);
        PemahamanPerawatan.setBounds(764, 1950, 90, 23);

        PemahamanPengobatan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        PemahamanPengobatan.setName("PemahamanPengobatan"); // NOI18N
        PemahamanPengobatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PemahamanPengobatanKeyPressed(evt);
            }
        });
        FormInput.add(PemahamanPengobatan);
        PemahamanPengobatan.setBounds(486, 1950, 90, 23);

        jLabel235.setText("Pemahaman Tentang Pengobatan :");
        jLabel235.setName("jLabel235"); // NOI18N
        FormInput.add(jLabel235);
        jLabel235.setBounds(302, 1950, 180, 23);

        jSeparator7.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator7.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator7.setName("jSeparator7"); // NOI18N
        FormInput.add(jSeparator7);
        jSeparator7.setBounds(0, 1980, 880, 1);

        jLabel236.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel236.setText("V. SKRINING GIZI");
        jLabel236.setName("jLabel236"); // NOI18N
        FormInput.add(jLabel236);
        jLabel236.setBounds(10, 1980, 380, 23);

        SG1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
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
        SG1.setBounds(700, 2000, 80, 23);

        jLabel237.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel237.setText("1. Masalah Minum (ASI/PASI)");
        jLabel237.setName("jLabel237"); // NOI18N
        FormInput.add(jLabel237);
        jLabel237.setBounds(44, 2000, 610, 23);

        NilaiGizi1.setEditable(false);
        NilaiGizi1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        NilaiGizi1.setText("0");
        NilaiGizi1.setFocusTraversalPolicyProvider(true);
        NilaiGizi1.setName("NilaiGizi1"); // NOI18N
        NilaiGizi1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NilaiGizi1KeyPressed(evt);
            }
        });
        FormInput.add(NilaiGizi1);
        NilaiGizi1.setBounds(794, 2000, 60, 23);

        jLabel238.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel238.setText("2. Penurunan Berat Badan > 10% Dari BBL (Berat Badan Lahir)");
        jLabel238.setName("jLabel238"); // NOI18N
        FormInput.add(jLabel238);
        jLabel238.setBounds(44, 2030, 610, 23);

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
        SG2.setBounds(700, 2030, 80, 23);

        NilaiGizi2.setEditable(false);
        NilaiGizi2.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        NilaiGizi2.setText("0");
        NilaiGizi2.setFocusTraversalPolicyProvider(true);
        NilaiGizi2.setName("NilaiGizi2"); // NOI18N
        NilaiGizi2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NilaiGizi2KeyPressed(evt);
            }
        });
        FormInput.add(NilaiGizi2);
        NilaiGizi2.setBounds(794, 2030, 60, 23);

        jLabel239.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel239.setText("3. Penyakit / Kelainan Yang Menyertai (Sepsis, Jantung, BBLR, Hipoglikemi, Diare, Lain–lain)");
        jLabel239.setName("jLabel239"); // NOI18N
        FormInput.add(jLabel239);
        jLabel239.setBounds(44, 2060, 610, 23);

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
        SG3.setBounds(700, 2060, 80, 23);

        NilaiGizi3.setEditable(false);
        NilaiGizi3.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        NilaiGizi3.setText("0");
        NilaiGizi3.setFocusTraversalPolicyProvider(true);
        NilaiGizi3.setName("NilaiGizi3"); // NOI18N
        NilaiGizi3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NilaiGizi3KeyPressed(evt);
            }
        });
        FormInput.add(NilaiGizi3);
        NilaiGizi3.setBounds(794, 2060, 60, 23);

        jLabel240.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel240.setText("Total Skor");
        jLabel240.setName("jLabel240"); // NOI18N
        FormInput.add(jLabel240);
        jLabel240.setBounds(44, 2090, 100, 23);

        TotalNilaiGizi.setEditable(false);
        TotalNilaiGizi.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        TotalNilaiGizi.setText("0");
        TotalNilaiGizi.setFocusTraversalPolicyProvider(true);
        TotalNilaiGizi.setName("TotalNilaiGizi"); // NOI18N
        TotalNilaiGizi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TotalNilaiGiziKeyPressed(evt);
            }
        });
        FormInput.add(TotalNilaiGizi);
        TotalNilaiGizi.setBounds(794, 2090, 60, 23);

        KeteranganSkriningGizi.setFocusTraversalPolicyProvider(true);
        KeteranganSkriningGizi.setName("KeteranganSkriningGizi"); // NOI18N
        KeteranganSkriningGizi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganSkriningGiziKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganSkriningGizi);
        KeteranganSkriningGizi.setBounds(104, 2090, 390, 23);

        jLabel241.setText(":");
        jLabel241.setName("jLabel241"); // NOI18N
        FormInput.add(jLabel241);
        jLabel241.setBounds(0, 2090, 100, 23);

        jSeparator8.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator8.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator8.setName("jSeparator8"); // NOI18N
        FormInput.add(jSeparator8);
        jSeparator8.setBounds(0, 2120, 880, 1);

        jLabel242.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel242.setText("VI. PENILAIAN RISIKO JATUH (SKALA HUMPTY DUMPTY)");
        jLabel242.setName("jLabel242"); // NOI18N
        FormInput.add(jLabel242);
        jLabel242.setBounds(10, 2120, 380, 23);

        jLabel243.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel243.setText("1. Umur");
        jLabel243.setName("jLabel243"); // NOI18N
        FormInput.add(jLabel243);
        jLabel243.setBounds(44, 2140, 200, 23);

        SkalaResiko1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0 - 3 Tahun", "3 - 7 Tahun", "7 - 13 Tahun", "> 13 Tahun" }));
        SkalaResiko1.setName("SkalaResiko1"); // NOI18N
        SkalaResiko1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SkalaResiko1ItemStateChanged(evt);
            }
        });
        SkalaResiko1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkalaResiko1KeyPressed(evt);
            }
        });
        FormInput.add(SkalaResiko1);
        SkalaResiko1.setBounds(254, 2140, 550, 23);

        NilaiResiko1.setEditable(false);
        NilaiResiko1.setFocusTraversalPolicyProvider(true);
        NilaiResiko1.setName("NilaiResiko1"); // NOI18N
        FormInput.add(NilaiResiko1);
        NilaiResiko1.setBounds(814, 2140, 40, 23);

        jLabel244.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel244.setText("2. Jenis Kelamin");
        jLabel244.setName("jLabel244"); // NOI18N
        FormInput.add(jLabel244);
        jLabel244.setBounds(44, 2170, 200, 23);

        SkalaResiko2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Laki-laki", "Perempuan" }));
        SkalaResiko2.setName("SkalaResiko2"); // NOI18N
        SkalaResiko2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SkalaResiko2ItemStateChanged(evt);
            }
        });
        SkalaResiko2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkalaResiko2KeyPressed(evt);
            }
        });
        FormInput.add(SkalaResiko2);
        SkalaResiko2.setBounds(254, 2170, 550, 23);

        NilaiResiko2.setEditable(false);
        NilaiResiko2.setFocusTraversalPolicyProvider(true);
        NilaiResiko2.setName("NilaiResiko2"); // NOI18N
        FormInput.add(NilaiResiko2);
        NilaiResiko2.setBounds(814, 2170, 40, 23);

        jLabel245.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel245.setText("3. Diagnosa");
        jLabel245.setName("jLabel245"); // NOI18N
        FormInput.add(jLabel245);
        jLabel245.setBounds(44, 2200, 200, 23);

        SkalaResiko3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Kelainan Neurologi", "Perubahan Dalam Oksigen(Masalah Saluran Nafas, Dehidrasi, Anemia, Anoreksia / Sakit Kepala, Dll)", "Kelainan Psikis / Perilaku", "Diagnosa Lain" }));
        SkalaResiko3.setName("SkalaResiko3"); // NOI18N
        SkalaResiko3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SkalaResiko3ItemStateChanged(evt);
            }
        });
        SkalaResiko3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkalaResiko3KeyPressed(evt);
            }
        });
        FormInput.add(SkalaResiko3);
        SkalaResiko3.setBounds(254, 2200, 550, 23);

        NilaiResiko3.setEditable(false);
        NilaiResiko3.setFocusTraversalPolicyProvider(true);
        NilaiResiko3.setName("NilaiResiko3"); // NOI18N
        FormInput.add(NilaiResiko3);
        NilaiResiko3.setBounds(814, 2200, 40, 23);

        jLabel246.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel246.setText("4. Gangguan Kognitif");
        jLabel246.setName("jLabel246"); // NOI18N
        FormInput.add(jLabel246);
        jLabel246.setBounds(44, 2230, 200, 23);

        SkalaResiko4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Sadar Terhadap Keterbatasan", "Lupa Keterbatasan", "Mengetahui Kemampuan Diri" }));
        SkalaResiko4.setName("SkalaResiko4"); // NOI18N
        SkalaResiko4.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SkalaResiko4ItemStateChanged(evt);
            }
        });
        SkalaResiko4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkalaResiko4KeyPressed(evt);
            }
        });
        FormInput.add(SkalaResiko4);
        SkalaResiko4.setBounds(254, 2230, 550, 23);

        NilaiResiko4.setEditable(false);
        NilaiResiko4.setFocusTraversalPolicyProvider(true);
        NilaiResiko4.setName("NilaiResiko4"); // NOI18N
        FormInput.add(NilaiResiko4);
        NilaiResiko4.setBounds(814, 2230, 40, 23);

        jLabel247.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel247.setText("5. Faktor Lingkungan");
        jLabel247.setName("jLabel247"); // NOI18N
        FormInput.add(jLabel247);
        jLabel247.setBounds(44, 2260, 200, 23);

        SkalaResiko5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Riwayat Jatuh Dari Tempat Tidur Saat Bayi/Anak", "Pasien Menggunakan Alat Bantu/Box/Mebel", "Pasien Berada Di Tempat Tidur", "Di Luar Ruang Rawat" }));
        SkalaResiko5.setName("SkalaResiko5"); // NOI18N
        SkalaResiko5.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SkalaResiko5ItemStateChanged(evt);
            }
        });
        SkalaResiko5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkalaResiko5KeyPressed(evt);
            }
        });
        FormInput.add(SkalaResiko5);
        SkalaResiko5.setBounds(254, 2260, 550, 23);

        NilaiResiko5.setEditable(false);
        NilaiResiko5.setFocusTraversalPolicyProvider(true);
        NilaiResiko5.setName("NilaiResiko5"); // NOI18N
        FormInput.add(NilaiResiko5);
        NilaiResiko5.setBounds(814, 2260, 40, 23);

        jLabel248.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel248.setText("6. Efek Obat Penenang/Operasi/Anastesi");
        jLabel248.setName("jLabel248"); // NOI18N
        FormInput.add(jLabel248);
        jLabel248.setBounds(44, 2290, 210, 23);

        SkalaResiko6.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Dalam 24 Jam", "Dalam 48 Jam", "> 48 Jam" }));
        SkalaResiko6.setName("SkalaResiko6"); // NOI18N
        SkalaResiko6.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SkalaResiko6ItemStateChanged(evt);
            }
        });
        SkalaResiko6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkalaResiko6KeyPressed(evt);
            }
        });
        FormInput.add(SkalaResiko6);
        SkalaResiko6.setBounds(254, 2290, 550, 23);

        NilaiResiko6.setEditable(false);
        NilaiResiko6.setFocusTraversalPolicyProvider(true);
        NilaiResiko6.setName("NilaiResiko6"); // NOI18N
        FormInput.add(NilaiResiko6);
        NilaiResiko6.setBounds(814, 2290, 40, 23);

        jLabel249.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel249.setText("7. Penggunaan Obat");
        jLabel249.setName("jLabel249"); // NOI18N
        FormInput.add(jLabel249);
        jLabel249.setBounds(44, 2320, 210, 23);

        SkalaResiko7.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Bermacam-macam Obat Yang Digunakan : Obat Sedative (Kecuali Pasien ICU Yang Menggunakan sedasi dan paralisis), Hipnotik, Barbiturat, Fenoti-Azin, Antidepresan, Laksans/Diuretika,Narkotik", "Salah Satu Dari Pengobatan Di Atas", "Pengobatan Lain" }));
        SkalaResiko7.setName("SkalaResiko7"); // NOI18N
        SkalaResiko7.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SkalaResiko7ItemStateChanged(evt);
            }
        });
        SkalaResiko7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkalaResiko7KeyPressed(evt);
            }
        });
        FormInput.add(SkalaResiko7);
        SkalaResiko7.setBounds(254, 2320, 550, 23);

        NilaiResiko7.setEditable(false);
        NilaiResiko7.setFocusTraversalPolicyProvider(true);
        NilaiResiko7.setName("NilaiResiko7"); // NOI18N
        FormInput.add(NilaiResiko7);
        NilaiResiko7.setBounds(814, 2320, 40, 23);

        jLabel251.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel251.setText("1. Ekspresi Wajah");
        jLabel251.setName("jLabel251"); // NOI18N
        FormInput.add(jLabel251);
        jLabel251.setBounds(44, 2400, 200, 23);

        SkalaNIPS1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Otot Relaks / Wajah Tenang / Ekspresi Netral", "Meringis / Otot Wajah Tegang / Alis Berkerut (Ekspresi Wajah Negatif)" }));
        SkalaNIPS1.setName("SkalaNIPS1"); // NOI18N
        SkalaNIPS1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SkalaNIPS1ItemStateChanged(evt);
            }
        });
        SkalaNIPS1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkalaNIPS1KeyPressed(evt);
            }
        });
        FormInput.add(SkalaNIPS1);
        SkalaNIPS1.setBounds(254, 2400, 550, 23);

        NilaiNIPS1.setEditable(false);
        NilaiNIPS1.setFocusTraversalPolicyProvider(true);
        NilaiNIPS1.setName("NilaiNIPS1"); // NOI18N
        FormInput.add(NilaiNIPS1);
        NilaiNIPS1.setBounds(814, 2400, 40, 23);

        NilaiNIPS2.setEditable(false);
        NilaiNIPS2.setFocusTraversalPolicyProvider(true);
        NilaiNIPS2.setName("NilaiNIPS2"); // NOI18N
        FormInput.add(NilaiNIPS2);
        NilaiNIPS2.setBounds(814, 2430, 40, 23);

        SkalaNIPS2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tenang / Tidak Menangis", "Merengek / Mengerang Lemah Intermiten", "Menangis Keras / Melengking Terus Menerus / Menangis Tanpa Suara Bila Bayi Intubasi" }));
        SkalaNIPS2.setName("SkalaNIPS2"); // NOI18N
        SkalaNIPS2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SkalaNIPS2ItemStateChanged(evt);
            }
        });
        SkalaNIPS2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkalaNIPS2KeyPressed(evt);
            }
        });
        FormInput.add(SkalaNIPS2);
        SkalaNIPS2.setBounds(254, 2430, 550, 23);

        jLabel252.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel252.setText("2. Tangisan");
        jLabel252.setName("jLabel252"); // NOI18N
        FormInput.add(jLabel252);
        jLabel252.setBounds(44, 2430, 200, 23);

        NilaiNIPS3.setEditable(false);
        NilaiNIPS3.setFocusTraversalPolicyProvider(true);
        NilaiNIPS3.setName("NilaiNIPS3"); // NOI18N
        FormInput.add(NilaiNIPS3);
        NilaiNIPS3.setBounds(814, 2460, 40, 23);

        SkalaNIPS3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Relaks / Bernapas Biasa", "Perubahan Napas / Tarikan Irreguler / Lebih Cepat Dibandingkan Biasa / Menahan Napas / Tersedak" }));
        SkalaNIPS3.setName("SkalaNIPS3"); // NOI18N
        SkalaNIPS3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SkalaNIPS3ItemStateChanged(evt);
            }
        });
        SkalaNIPS3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkalaNIPS3KeyPressed(evt);
            }
        });
        FormInput.add(SkalaNIPS3);
        SkalaNIPS3.setBounds(254, 2460, 550, 23);

        jLabel253.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel253.setText("3. Pola Nafas");
        jLabel253.setName("jLabel253"); // NOI18N
        FormInput.add(jLabel253);
        jLabel253.setBounds(44, 2460, 200, 23);

        NilaiNIPS4.setEditable(false);
        NilaiNIPS4.setFocusTraversalPolicyProvider(true);
        NilaiNIPS4.setName("NilaiNIPS4"); // NOI18N
        FormInput.add(NilaiNIPS4);
        NilaiNIPS4.setBounds(814, 2490, 40, 23);

        SkalaNIPS4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Relaks / Tidak Ada Kekakuan Otot / Gerakan Tungkai Biasa", "Fleksi / Ekstensi Tegang Kaku" }));
        SkalaNIPS4.setName("SkalaNIPS4"); // NOI18N
        SkalaNIPS4.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SkalaNIPS4ItemStateChanged(evt);
            }
        });
        SkalaNIPS4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkalaNIPS4KeyPressed(evt);
            }
        });
        FormInput.add(SkalaNIPS4);
        SkalaNIPS4.setBounds(254, 2490, 550, 23);

        jLabel254.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel254.setText("4. Tungkai");
        jLabel254.setName("jLabel254"); // NOI18N
        FormInput.add(jLabel254);
        jLabel254.setBounds(44, 2490, 200, 23);

        NilaiNIPS5.setEditable(false);
        NilaiNIPS5.setFocusTraversalPolicyProvider(true);
        NilaiNIPS5.setName("NilaiNIPS5"); // NOI18N
        FormInput.add(NilaiNIPS5);
        NilaiNIPS5.setBounds(814, 2520, 40, 23);

        SkalaNIPS5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tenang / Tidur Lelap / Bangun", "Gelisah" }));
        SkalaNIPS5.setName("SkalaNIPS5"); // NOI18N
        SkalaNIPS5.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SkalaNIPS5ItemStateChanged(evt);
            }
        });
        SkalaNIPS5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkalaNIPS5KeyPressed(evt);
            }
        });
        FormInput.add(SkalaNIPS5);
        SkalaNIPS5.setBounds(254, 2520, 550, 23);

        jLabel255.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel255.setText("5. Tingkat kesadaran");
        jLabel255.setName("jLabel255"); // NOI18N
        FormInput.add(jLabel255);
        jLabel255.setBounds(44, 2520, 200, 23);

        TingkatResiko1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        TingkatResiko1.setText("Tingkat Resiko");
        TingkatResiko1.setToolTipText("");
        TingkatResiko1.setName("TingkatResiko1"); // NOI18N
        FormInput.add(TingkatResiko1);
        TingkatResiko1.setBounds(44, 2350, 90, 23);

        jLabel256.setText("Total :");
        jLabel256.setName("jLabel256"); // NOI18N
        FormInput.add(jLabel256);
        jLabel256.setBounds(734, 2350, 70, 23);

        NilaiResikoTotal.setEditable(false);
        NilaiResikoTotal.setFocusTraversalPolicyProvider(true);
        NilaiResikoTotal.setName("NilaiResikoTotal"); // NOI18N
        FormInput.add(NilaiResikoTotal);
        NilaiResikoTotal.setBounds(814, 2350, 40, 23);

        TingkatResiko2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        TingkatResiko2.setText("Keterangan");
        TingkatResiko2.setToolTipText("");
        TingkatResiko2.setName("TingkatResiko2"); // NOI18N
        FormInput.add(TingkatResiko2);
        TingkatResiko2.setBounds(44, 2550, 90, 23);

        jLabel258.setText("Total :");
        jLabel258.setName("jLabel258"); // NOI18N
        FormInput.add(jLabel258);
        jLabel258.setBounds(734, 2550, 70, 23);

        TotalNIPS.setEditable(false);
        TotalNIPS.setFocusTraversalPolicyProvider(true);
        TotalNIPS.setName("TotalNIPS"); // NOI18N
        FormInput.add(TotalNIPS);
        TotalNIPS.setBounds(814, 2550, 40, 23);

        jSeparator13.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator13.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator13.setName("jSeparator13"); // NOI18N
        FormInput.add(jSeparator13);
        jSeparator13.setBounds(0, 2580, 880, 1);

        jLabel272.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel272.setText("VIII. PERENCANAAN PULANG (DISCHARGE PLANNING)");
        jLabel272.setName("jLabel272"); // NOI18N
        FormInput.add(jLabel272);
        jLabel272.setBounds(10, 2580, 380, 23);

        InformasiPerencanaanPulang.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        InformasiPerencanaanPulang.setName("InformasiPerencanaanPulang"); // NOI18N
        InformasiPerencanaanPulang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                InformasiPerencanaanPulangKeyPressed(evt);
            }
        });
        FormInput.add(InformasiPerencanaanPulang);
        InformasiPerencanaanPulang.setBounds(351, 2600, 80, 23);

        jLabel250.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel250.setText("Ibu Bayi & Keluarga Diberikan Informasi Perencanaan Pulang");
        jLabel250.setName("jLabel250"); // NOI18N
        FormInput.add(jLabel250);
        jLabel250.setBounds(44, 2600, 330, 23);

        jLabel257.setText("?");
        jLabel257.setName("jLabel257"); // NOI18N
        FormInput.add(jLabel257);
        jLabel257.setBounds(0, 2600, 347, 23);

        jLabel259.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel259.setText("Kondisi Klinis Saat Pulang");
        jLabel259.setName("jLabel259"); // NOI18N
        FormInput.add(jLabel259);
        jLabel259.setBounds(44, 2630, 180, 23);

        jLabel260.setText(":");
        jLabel260.setName("jLabel260"); // NOI18N
        FormInput.add(jLabel260);
        jLabel260.setBounds(0, 2630, 176, 23);

        KondisiPulang.setFocusTraversalPolicyProvider(true);
        KondisiPulang.setName("KondisiPulang"); // NOI18N
        KondisiPulang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KondisiPulangKeyPressed(evt);
            }
        });
        FormInput.add(KondisiPulang);
        KondisiPulang.setBounds(180, 2630, 674, 23);

        label29.setText("Perencanaan Pulang :");
        label29.setName("label29"); // NOI18N
        label29.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label29);
        label29.setBounds(630, 2600, 130, 23);

        TanggalPulang.setForeground(new java.awt.Color(50, 70, 50));
        TanggalPulang.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "08-03-2024" }));
        TanggalPulang.setDisplayFormat("dd-MM-yyyy");
        TanggalPulang.setName("TanggalPulang"); // NOI18N
        TanggalPulang.setOpaque(false);
        TanggalPulang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalPulangKeyPressed(evt);
            }
        });
        FormInput.add(TanggalPulang);
        TanggalPulang.setBounds(764, 2600, 90, 23);

        jLabel261.setText("Lama Rawat Rata-rata :");
        jLabel261.setName("jLabel261"); // NOI18N
        FormInput.add(jLabel261);
        jLabel261.setBounds(442, 2600, 130, 23);

        LamaRatarata.setFocusTraversalPolicyProvider(true);
        LamaRatarata.setName("LamaRatarata"); // NOI18N
        LamaRatarata.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LamaRatarataKeyPressed(evt);
            }
        });
        FormInput.add(LamaRatarata);
        LamaRatarata.setBounds(576, 2600, 55, 23);

        jLabel262.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel262.setText("Perawatan Lanjutan Yang Diberikan Di Rumah");
        jLabel262.setName("jLabel262"); // NOI18N
        FormInput.add(jLabel262);
        jLabel262.setBounds(44, 2660, 240, 23);

        scrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane2.setName("scrollPane2"); // NOI18N

        PerawatanLanjutan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        PerawatanLanjutan.setColumns(20);
        PerawatanLanjutan.setRows(5);
        PerawatanLanjutan.setName("PerawatanLanjutan"); // NOI18N
        PerawatanLanjutan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PerawatanLanjutanKeyPressed(evt);
            }
        });
        scrollPane2.setViewportView(PerawatanLanjutan);

        FormInput.add(scrollPane2);
        scrollPane2.setBounds(278, 2660, 576, 43);

        jLabel263.setText(":");
        jLabel263.setName("jLabel263"); // NOI18N
        FormInput.add(jLabel263);
        jLabel263.setBounds(0, 2660, 274, 23);

        CaraTransportasiPulang.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Mandiri", "Dibantu Sebagian", "Dibantu Keseluruhan", "Menggunakan Rostul", "Menggunakan Brankar", "Berjalan" }));
        CaraTransportasiPulang.setName("CaraTransportasiPulang"); // NOI18N
        CaraTransportasiPulang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CaraTransportasiPulangKeyPressed(evt);
            }
        });
        FormInput.add(CaraTransportasiPulang);
        CaraTransportasiPulang.setBounds(178, 2710, 160, 23);

        jLabel264.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel264.setText("Cara Transportasi Pulang");
        jLabel264.setName("jLabel264"); // NOI18N
        FormInput.add(jLabel264);
        jLabel264.setBounds(44, 2710, 140, 23);

        jLabel265.setText(":");
        jLabel265.setName("jLabel265"); // NOI18N
        FormInput.add(jLabel265);
        jLabel265.setBounds(0, 2710, 174, 23);

        jLabel266.setText("Transportasi Yang Digunakan :");
        jLabel266.setName("jLabel266"); // NOI18N
        FormInput.add(jLabel266);
        jLabel266.setBounds(340, 2710, 167, 23);

        TransportasiYangDigunakan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Kendaraan Pribadi", "Mobil Ambulance", "Kendaraan Umum" }));
        TransportasiYangDigunakan.setName("TransportasiYangDigunakan"); // NOI18N
        TransportasiYangDigunakan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TransportasiYangDigunakanKeyPressed(evt);
            }
        });
        FormInput.add(TransportasiYangDigunakan);
        TransportasiYangDigunakan.setBounds(511, 2710, 140, 23);

        KeteranganTingkatRisiko.setFocusTraversalPolicyProvider(true);
        KeteranganTingkatRisiko.setName("KeteranganTingkatRisiko"); // NOI18N
        KeteranganTingkatRisiko.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganTingkatRisikoKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganTingkatRisiko);
        KeteranganTingkatRisiko.setBounds(126, 2350, 368, 23);

        jLabel267.setText(":");
        jLabel267.setName("jLabel267"); // NOI18N
        FormInput.add(jLabel267);
        jLabel267.setBounds(0, 2350, 122, 23);

        jLabel268.setText(":");
        jLabel268.setName("jLabel268"); // NOI18N
        FormInput.add(jLabel268);
        jLabel268.setBounds(0, 2090, 100, 23);

        KeteranganPenilaianNyeri.setFocusTraversalPolicyProvider(true);
        KeteranganPenilaianNyeri.setName("KeteranganPenilaianNyeri"); // NOI18N
        KeteranganPenilaianNyeri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganPenilaianNyeriKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganPenilaianNyeri);
        KeteranganPenilaianNyeri.setBounds(111, 2550, 383, 23);

        jLabel269.setText(":");
        jLabel269.setName("jLabel269"); // NOI18N
        FormInput.add(jLabel269);
        jLabel269.setBounds(0, 2550, 107, 23);

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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "08-03-2024" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "08-03-2024" }));
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
        FormMasalahRencana.setLayout(new java.awt.GridLayout(3, 0, 1, 1));

        Scroll7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 254)));
        Scroll7.setName("Scroll7"); // NOI18N
        Scroll7.setOpaque(true);

        tbMasalahDetail.setName("tbMasalahDetail"); // NOI18N
        Scroll7.setViewportView(tbMasalahDetail);

        FormMasalahRencana.add(Scroll7);

        Scroll9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 254)));
        Scroll9.setName("Scroll9"); // NOI18N
        Scroll9.setOpaque(true);

        tbRencanaDetail.setName("tbRencanaDetail"); // NOI18N
        Scroll9.setViewportView(tbRencanaDetail);

        FormMasalahRencana.add(Scroll9);

        scrollPane6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 254)), "Rencana Keperawatan Lainnya :", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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

        TabRawat.addTab("Data Penilaian", internalFrame3);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        /*if(TNoRM.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"Nama Pasien");
        }else if(KdPetugas.getText().trim().equals("")||NmPetugas.getText().trim().equals("")){
            Valid.textKosong(BtnPetugas,"Pengkaji 1");
        }else if(KdPetugas2.getText().trim().equals("")||NmPetugas2.getText().trim().equals("")){
            Valid.textKosong(BtnPetugas2,"Pegkaji 2");
        }else if(KdDPJP.getText().trim().equals("")||NmDPJP.getText().trim().equals("")){
            Valid.textKosong(BtnDPJP,"DPJP");
        }else if(RPS.getText().trim().equals("")){
            Valid.textKosong(RPS,"Riwayat Penyakit Sekarang");
        }else{
            if(Sequel.menyimpantf("penilaian_awal_keperawatan_ranap","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat",193,new String[]{
                    TNoRw.getText(),Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),Anamnesis.getSelectedItem().toString(),KetAnamnesis.getText(),TibadiRuang.getSelectedItem().toString(),MacamKasus.getSelectedItem().toString(), 
                    CaraMasuk.getSelectedItem().toString(),RPS.getText(),RPD.getText(),RPK.getText(),RPO.getText(),RPembedahan.getText(),RDirawatRS.getText(),AlatBantuDipakai.getSelectedItem().toString(),SedangMenyusui.getSelectedItem().toString(),KetSedangMenyusui.getText(),RTranfusi.getText(), 
                    Alergi.getText(),KebiasaanMerokok.getSelectedItem().toString(),KebiasaanJumlahRokok.getText(),KebiasaanAlkohol.getSelectedItem().toString(),KebiasaanJumlahAlkohol.getText(),KebiasaanNarkoba.getSelectedItem().toString(),OlahRaga.getSelectedItem().toString(),KesadaranMental.getText(), 
                    KeadaanMentalUmum.getSelectedItem().toString(),GCS.getText(),TD.getText(),Nadi.getText(),RR.getText(),Suhu.getText(),SpO2.getText(),BB.getText(),TB.getText(),SistemSarafKepala.getSelectedItem().toString(),KetSistemSarafKepala.getText(),SistemSarafWajah.getSelectedItem().toString(), 
                    KetSistemSarafWajah.getText(),SistemSarafLeher.getSelectedItem().toString(),SistemSarafKejang.getSelectedItem().toString(),KetSistemSarafKejang.getText(),SistemSarafSensorik.getSelectedItem().toString(),KardiovaskularDenyutNadi.getSelectedItem().toString(),KardiovaskularSirkulasi.getSelectedItem().toString(), 
                    KetKardiovaskularSirkulasi.getText(),KardiovaskularPulsasi.getSelectedItem().toString(),RespirasiPolaNafas.getSelectedItem().toString(),RespirasiRetraksi.getSelectedItem().toString(),RespirasiSuaraNafas.getSelectedItem().toString(),RespirasiVolume.getSelectedItem().toString(),
                    RespirasiJenisPernafasan.getSelectedItem().toString(),KetRespirasiJenisPernafasan.getText(),RespirasiIrama.getSelectedItem().toString(),RespirasiBatuk.getSelectedItem().toString(),GastrointestinalMulut.getSelectedItem().toString(),KetGastrointestinalMulut.getText(),
                    GastrointestinalGigi.getSelectedItem().toString(),KetGastrointestinalGigi.getText(),GastrointestinalLidah.getSelectedItem().toString(),KetGastrointestinalLidah.getText(),GastrointestinalTenggorakan.getSelectedItem().toString(),KetGastrointestinalTenggorakan.getText(), 
                    GastrointestinalAbdomen.getSelectedItem().toString(),KetGastrointestinalAbdomen.getText(),GastrointestinalUsus.getSelectedItem().toString(),GastrointestinalAnus.getSelectedItem().toString(),NeurologiPenglihatan.getSelectedItem().toString(),KetNeurologiPenglihatan.getText(), 
                    NeurologiAlatBantuPenglihatan.getSelectedItem().toString(),NeurologiPendengaran.getSelectedItem().toString(),NeurologiBicara.getSelectedItem().toString(),KetNeurologiBicara.getText(),NeurologiSensorik.getSelectedItem().toString(),NeurologiMotorik.getSelectedItem().toString(), 
                    NeurologiOtot.getSelectedItem().toString(),IntegumentWarnaKulit.getSelectedItem().toString(),IntegumentTurgor.getSelectedItem().toString(),IntegumentKulit.getSelectedItem().toString(),IntegumentDecubitus.getSelectedItem().toString(),MuskuloskletalPegerakanSendi.getSelectedItem().toString(), 
                    MuskuloskletalKekuatanOtot.getSelectedItem().toString(),MuskuloskletalNyeriSendi.getSelectedItem().toString(),KetMuskuloskletalNyeriSendi.getText(),MuskuloskletalOedema.getSelectedItem().toString(),KetMuskuloskletalOedema.getText(),MuskuloskletalFraktur.getSelectedItem().toString(), 
                    KetMuskuloskletalFraktur.getText(),BAB.getText(),XBAB.getText(),KBAB.getText(),WBAB.getText(),BAK.getText(),XBAK.getText(),WBAK.getText(),LBAK.getText(),PolaAktifitasMakan.getSelectedItem().toString(),PolaAktifitasMandi.getSelectedItem().toString(),PolaAktifitasEliminasi.getSelectedItem().toString(), 
                    PolaAktifitasBerpakaian.getSelectedItem().toString(),PolaAktifitasBerpindah.getSelectedItem().toString(),PolaNutrisiFrekuensi.getText(),PolaNutrisiJenis.getText(),PolaNutrisiPorsi.getText(),PolaTidurLama.getText(),PolaTidurGangguan.getSelectedItem().toString(),AktifitasSehari2.getSelectedItem().toString(), 
                    Aktifitas.getSelectedItem().toString(),Berjalan.getSelectedItem().toString(),KeteranganBerjalan.getText(),AlatAmbulasi.getSelectedItem().toString(),EkstrimitasAtas.getSelectedItem().toString(),KeteranganEkstrimitasAtas.getText(),EkstrimitasBawah.getSelectedItem().toString(),
                    KeteranganEkstrimitasBawah.getText(),KemampuanMenggenggam.getSelectedItem().toString(),KeteranganKemampuanMenggenggam.getText(),KemampuanKoordinasi.getSelectedItem().toString(),KeteranganKemampuanKoordinasi.getText(),KesimpulanGangguanFungsi.getSelectedItem().toString(),
                    KondisiPsikologis.getSelectedItem().toString(),GangguanJiwa.getSelectedItem().toString(),AdakahPerilaku.getSelectedItem().toString(),KeteranganAdakahPerilaku.getText(),HubunganAnggotaKeluarga.getSelectedItem().toString(),TinggalDengan.getSelectedItem().toString(),KeteranganTinggalDengan.getText(),
                    NilaiKepercayaan.getSelectedItem().toString(),KeteranganNilaiKepercayaan.getText(),PendidikanPJ.getSelectedItem().toString(),EdukasiPsikolgis.getSelectedItem().toString(),KeteranganEdukasiPsikologis.getText(),Nyeri.getSelectedItem().toString(),Provokes.getSelectedItem().toString(),KetProvokes.getText(), 
                    Quality.getSelectedItem().toString(),KetQuality.getText(),Lokasi.getText(),Menyebar.getSelectedItem().toString(),SkalaNyeri.getSelectedItem().toString(),Durasi.getText(),NyeriHilang.getSelectedItem().toString(),KetNyeri.getText(),PadaDokter.getSelectedItem().toString(), 
                    KetPadaDokter.getText(),SkalaResiko1.getSelectedItem().toString(),NilaiResiko1.getText(),SkalaResiko2.getSelectedItem().toString(),NilaiResiko2.getText(),SkalaResiko3.getSelectedItem().toString(),NilaiResiko3.getText(),SkalaResiko4.getSelectedItem().toString(),NilaiResiko4.getText(), 
                    SkalaResiko5.getSelectedItem().toString(),NilaiResiko5.getText(),SkalaResiko6.getSelectedItem().toString(),NilaiResiko6.getText(),NilaiResikoTotal.getText(),SkalaSydney1.getSelectedItem().toString(),NilaiSydney1.getText(),SkalaSydney2.getSelectedItem().toString(),NilaiSydney2.getText(),
                    SkalaSydney3.getSelectedItem().toString(),NilaiSydney3.getText(),SkalaSydney4.getSelectedItem().toString(),NilaiSydney4.getText(),SkalaSydney5.getSelectedItem().toString(),NilaiSydney5.getText(),SkalaSydney6.getSelectedItem().toString(),NilaiSydney6.getText(), 
                    SkalaSydney7.getSelectedItem().toString(),NilaiSydney7.getText(),SkalaSydney8.getSelectedItem().toString(),NilaiSydney8.getText(),SkalaSydney9.getSelectedItem().toString(),NilaiSydney9.getText(),SkalaSydney10.getSelectedItem().toString(),NilaiSydney10.getText(), 
                    SkalaSydney11.getSelectedItem().toString(),NilaiSydney11.getText(),NilaiSydneyTotal.getText(),SkalaGizi1.getSelectedItem().toString(),NilaiGizi1.getText(),SkalaGizi2.getSelectedItem().toString(),NilaiGizi2.getText(),NilaiGiziTotal.getText(),DiagnosaKhususGizi.getSelectedItem().toString(),
                    KeteranganDiagnosaKhususGizi.getText(),DiketahuiDietisen.getSelectedItem().toString(),KeteranganDiketahuiDietisen.getText(),Rencana.getText(),KdPetugas.getText(),KdPetugas2.getText(),KdDPJP.getText()
                })==true){
                    for (i = 0; i < tbMasalahKeperawatan.getRowCount(); i++) {
                        if(tbMasalahKeperawatan.getValueAt(i,0).toString().equals("true")){
                            Sequel.menyimpan2("penilaian_awal_keperawatan_ranap_masalah","?,?",2,new String[]{TNoRw.getText(),tbMasalahKeperawatan.getValueAt(i,1).toString()});
                        }
                    }
                    for (i = 0; i < tbRencanaKeperawatan.getRowCount(); i++) {
                        if(tbRencanaKeperawatan.getValueAt(i,0).toString().equals("true")){
                            Sequel.menyimpan2("penilaian_awal_keperawatan_ranap_rencana","?,?",2,new String[]{TNoRw.getText(),tbRencanaKeperawatan.getValueAt(i,1).toString()});
                        }
                    }
                    emptTeks();
            }
        }*/
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
        }else{
            Valid.pindah(evt, BtnSimpan, BtnHapus);
        }
}//GEN-LAST:event_BtnBatalKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if(tbObat.getSelectedRow()>-1){
            if(akses.getkode().equals("Admin Utama")){
                hapus();
            }else{
                if(KdPetugas.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString())){
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
        }else if(KdPetugas.getText().trim().equals("")||NmPetugas.getText().trim().equals("")){
            Valid.textKosong(BtnPetugas,"Pengkaji 1");
        }else if(KdPetugas2.getText().trim().equals("")||NmPetugas2.getText().trim().equals("")){
            Valid.textKosong(BtnPetugas2,"Pegkaji 2");
        }else if(KdDPJP.getText().trim().equals("")||NmDPJP.getText().trim().equals("")){
            Valid.textKosong(BtnDPJP,"DPJP");
        }else if(RPS.getText().trim().equals("")){
            Valid.textKosong(RPS,"Riwayat Penyakit Sekarang");
        }else{
            if(tbObat.getSelectedRow()>-1){
                if(akses.getkode().equals("Admin Utama")){
                    ganti();
                }else{
                    if(KdPetugas.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString())){
                        ganti();
                    }else{
                        JOptionPane.showMessageDialog(null,"Hanya bisa diganti oleh petugas yang bersangkutan..!!");
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
                
                ps=koneksi.prepareStatement(
                    "select penilaian_awal_keperawatan_ranap.no_rawat,penilaian_awal_keperawatan_ranap.tanggal,penilaian_awal_keperawatan_ranap.informasi,penilaian_awal_keperawatan_ranap.ket_informasi,penilaian_awal_keperawatan_ranap.tiba_diruang_rawat,"+
                    "penilaian_awal_keperawatan_ranap.kasus_trauma,penilaian_awal_keperawatan_ranap.cara_masuk,penilaian_awal_keperawatan_ranap.rps,penilaian_awal_keperawatan_ranap.rpd,penilaian_awal_keperawatan_ranap.rpk,penilaian_awal_keperawatan_ranap.rpo,"+
                    "penilaian_awal_keperawatan_ranap.riwayat_pembedahan,penilaian_awal_keperawatan_ranap.riwayat_dirawat_dirs,penilaian_awal_keperawatan_ranap.alat_bantu_dipakai,penilaian_awal_keperawatan_ranap.riwayat_kehamilan,"+
                    "penilaian_awal_keperawatan_ranap.riwayat_kehamilan_perkiraan,penilaian_awal_keperawatan_ranap.riwayat_tranfusi,penilaian_awal_keperawatan_ranap.riwayat_alergi,penilaian_awal_keperawatan_ranap.riwayat_merokok,"+
                    "penilaian_awal_keperawatan_ranap.riwayat_merokok_jumlah,penilaian_awal_keperawatan_ranap.riwayat_alkohol,penilaian_awal_keperawatan_ranap.riwayat_alkohol_jumlah,penilaian_awal_keperawatan_ranap.riwayat_narkoba,"+
                    "penilaian_awal_keperawatan_ranap.riwayat_olahraga,penilaian_awal_keperawatan_ranap.pemeriksaan_mental,penilaian_awal_keperawatan_ranap.pemeriksaan_keadaan_umum,penilaian_awal_keperawatan_ranap.pemeriksaan_gcs,"+
                    "penilaian_awal_keperawatan_ranap.pemeriksaan_td,penilaian_awal_keperawatan_ranap.pemeriksaan_nadi,penilaian_awal_keperawatan_ranap.pemeriksaan_rr,penilaian_awal_keperawatan_ranap.pemeriksaan_suhu,"+
                    "penilaian_awal_keperawatan_ranap.pemeriksaan_spo2,penilaian_awal_keperawatan_ranap.pemeriksaan_bb,penilaian_awal_keperawatan_ranap.pemeriksaan_tb,penilaian_awal_keperawatan_ranap.pemeriksaan_susunan_kepala,"+
                    "penilaian_awal_keperawatan_ranap.pemeriksaan_susunan_kepala_keterangan,penilaian_awal_keperawatan_ranap.pemeriksaan_susunan_wajah,penilaian_awal_keperawatan_ranap.pemeriksaan_susunan_wajah_keterangan,"+
                    "penilaian_awal_keperawatan_ranap.pemeriksaan_susunan_leher,penilaian_awal_keperawatan_ranap.pemeriksaan_susunan_kejang,penilaian_awal_keperawatan_ranap.pemeriksaan_susunan_kejang_keterangan,"+
                    "penilaian_awal_keperawatan_ranap.pemeriksaan_susunan_sensorik,penilaian_awal_keperawatan_ranap.pemeriksaan_kardiovaskuler_denyut_nadi,penilaian_awal_keperawatan_ranap.pemeriksaan_kardiovaskuler_sirkulasi,"+
                    "penilaian_awal_keperawatan_ranap.pemeriksaan_kardiovaskuler_sirkulasi_keterangan,penilaian_awal_keperawatan_ranap.pemeriksaan_kardiovaskuler_pulsasi,penilaian_awal_keperawatan_ranap.pemeriksaan_respirasi_pola_nafas,"+
                    "penilaian_awal_keperawatan_ranap.pemeriksaan_respirasi_retraksi,penilaian_awal_keperawatan_ranap.pemeriksaan_respirasi_suara_nafas,penilaian_awal_keperawatan_ranap.pemeriksaan_respirasi_volume_pernafasan,"+
                    "penilaian_awal_keperawatan_ranap.pemeriksaan_respirasi_jenis_pernafasan,penilaian_awal_keperawatan_ranap.pemeriksaan_respirasi_jenis_pernafasan_keterangan,penilaian_awal_keperawatan_ranap.pemeriksaan_respirasi_irama_nafas,"+
                    "penilaian_awal_keperawatan_ranap.pemeriksaan_respirasi_batuk,penilaian_awal_keperawatan_ranap.pemeriksaan_gastrointestinal_mulut,penilaian_awal_keperawatan_ranap.pemeriksaan_gastrointestinal_mulut_keterangan,"+
                    "penilaian_awal_keperawatan_ranap.pemeriksaan_gastrointestinal_gigi,penilaian_awal_keperawatan_ranap.pemeriksaan_gastrointestinal_gigi_keterangan,penilaian_awal_keperawatan_ranap.pemeriksaan_gastrointestinal_lidah,"+
                    "penilaian_awal_keperawatan_ranap.pemeriksaan_gastrointestinal_lidah_keterangan,penilaian_awal_keperawatan_ranap.pemeriksaan_gastrointestinal_tenggorokan,penilaian_awal_keperawatan_ranap.pemeriksaan_gastrointestinal_tenggorokan_keterangan,"+
                    "penilaian_awal_keperawatan_ranap.pemeriksaan_gastrointestinal_abdomen,penilaian_awal_keperawatan_ranap.pemeriksaan_gastrointestinal_abdomen_keterangan,penilaian_awal_keperawatan_ranap.pemeriksaan_gastrointestinal_peistatik_usus,"+
                    "penilaian_awal_keperawatan_ranap.pemeriksaan_gastrointestinal_anus,penilaian_awal_keperawatan_ranap.pemeriksaan_neurologi_pengelihatan,penilaian_awal_keperawatan_ranap.pemeriksaan_neurologi_pengelihatan_keterangan,"+
                    "penilaian_awal_keperawatan_ranap.pemeriksaan_neurologi_alat_bantu_penglihatan,penilaian_awal_keperawatan_ranap.pemeriksaan_neurologi_pendengaran,penilaian_awal_keperawatan_ranap.pemeriksaan_neurologi_bicara,"+
                    "penilaian_awal_keperawatan_ranap.pemeriksaan_neurologi_bicara_keterangan,penilaian_awal_keperawatan_ranap.pemeriksaan_neurologi_sensorik,penilaian_awal_keperawatan_ranap.pemeriksaan_neurologi_motorik,"+
                    "penilaian_awal_keperawatan_ranap.pemeriksaan_neurologi_kekuatan_otot,penilaian_awal_keperawatan_ranap.pemeriksaan_integument_warnakulit,penilaian_awal_keperawatan_ranap.pemeriksaan_integument_turgor,"+
                    "penilaian_awal_keperawatan_ranap.pemeriksaan_integument_kulit,penilaian_awal_keperawatan_ranap.pemeriksaan_integument_dekubitas,penilaian_awal_keperawatan_ranap.pemeriksaan_muskuloskletal_pergerakan_sendi,"+
                    "penilaian_awal_keperawatan_ranap.pemeriksaan_muskuloskletal_kekauatan_otot,penilaian_awal_keperawatan_ranap.pemeriksaan_muskuloskletal_nyeri_sendi,penilaian_awal_keperawatan_ranap.pemeriksaan_muskuloskletal_nyeri_sendi_keterangan,"+
                    "penilaian_awal_keperawatan_ranap.pemeriksaan_muskuloskletal_oedema,penilaian_awal_keperawatan_ranap.pemeriksaan_muskuloskletal_oedema_keterangan,penilaian_awal_keperawatan_ranap.pemeriksaan_muskuloskletal_fraktur,"+
                    "penilaian_awal_keperawatan_ranap.pemeriksaan_muskuloskletal_fraktur_keterangan,penilaian_awal_keperawatan_ranap.pemeriksaan_eliminasi_bab_frekuensi_jumlah,penilaian_awal_keperawatan_ranap.pemeriksaan_eliminasi_bab_frekuensi_durasi,"+
                    "penilaian_awal_keperawatan_ranap.pemeriksaan_eliminasi_bab_konsistensi,penilaian_awal_keperawatan_ranap.pemeriksaan_eliminasi_bab_warna,penilaian_awal_keperawatan_ranap.pemeriksaan_eliminasi_bak_frekuensi_jumlah,"+
                    "penilaian_awal_keperawatan_ranap.pemeriksaan_eliminasi_bak_frekuensi_durasi,penilaian_awal_keperawatan_ranap.pemeriksaan_eliminasi_bak_warna,penilaian_awal_keperawatan_ranap.pemeriksaan_eliminasi_bak_lainlain,"+
                    "penilaian_awal_keperawatan_ranap.pola_aktifitas_makanminum,penilaian_awal_keperawatan_ranap.pola_aktifitas_mandi,penilaian_awal_keperawatan_ranap.pola_aktifitas_eliminasi,penilaian_awal_keperawatan_ranap.pola_aktifitas_berpakaian,"+
                    "penilaian_awal_keperawatan_ranap.pola_aktifitas_berpindah,penilaian_awal_keperawatan_ranap.pola_nutrisi_frekuesi_makan,penilaian_awal_keperawatan_ranap.pola_nutrisi_jenis_makanan,penilaian_awal_keperawatan_ranap.pola_nutrisi_porsi_makan,"+
                    "penilaian_awal_keperawatan_ranap.pola_tidur_lama_tidur,penilaian_awal_keperawatan_ranap.pola_tidur_gangguan,penilaian_awal_keperawatan_ranap.pengkajian_fungsi_kemampuan_sehari,penilaian_awal_keperawatan_ranap.pengkajian_fungsi_aktifitas,"+
                    "penilaian_awal_keperawatan_ranap.pengkajian_fungsi_berjalan,penilaian_awal_keperawatan_ranap.pengkajian_fungsi_berjalan_keterangan,penilaian_awal_keperawatan_ranap.pengkajian_fungsi_ambulasi,"+
                    "penilaian_awal_keperawatan_ranap.pengkajian_fungsi_ekstrimitas_atas,penilaian_awal_keperawatan_ranap.pengkajian_fungsi_ekstrimitas_atas_keterangan,penilaian_awal_keperawatan_ranap.pengkajian_fungsi_ekstrimitas_bawah,"+
                    "penilaian_awal_keperawatan_ranap.pengkajian_fungsi_ekstrimitas_bawah_keterangan,penilaian_awal_keperawatan_ranap.pengkajian_fungsi_menggenggam,penilaian_awal_keperawatan_ranap.pengkajian_fungsi_menggenggam_keterangan,"+
                    "penilaian_awal_keperawatan_ranap.pengkajian_fungsi_koordinasi,penilaian_awal_keperawatan_ranap.pengkajian_fungsi_koordinasi_keterangan,penilaian_awal_keperawatan_ranap.pengkajian_fungsi_kesimpulan,"+
                    "penilaian_awal_keperawatan_ranap.riwayat_psiko_kondisi_psiko,penilaian_awal_keperawatan_ranap.riwayat_psiko_gangguan_jiwa,penilaian_awal_keperawatan_ranap.riwayat_psiko_perilaku,"+
                    "penilaian_awal_keperawatan_ranap.riwayat_psiko_perilaku_keterangan,penilaian_awal_keperawatan_ranap.riwayat_psiko_hubungan_keluarga,penilaian_awal_keperawatan_ranap.riwayat_psiko_tinggal,"+
                    "penilaian_awal_keperawatan_ranap.riwayat_psiko_tinggal_keterangan,penilaian_awal_keperawatan_ranap.riwayat_psiko_nilai_kepercayaan,penilaian_awal_keperawatan_ranap.riwayat_psiko_nilai_kepercayaan_keterangan,"+
                    "penilaian_awal_keperawatan_ranap.riwayat_psiko_pendidikan_pj,penilaian_awal_keperawatan_ranap.riwayat_psiko_edukasi_diberikan,penilaian_awal_keperawatan_ranap.riwayat_psiko_edukasi_diberikan_keterangan,"+
                    "penilaian_awal_keperawatan_ranap.penilaian_nyeri,penilaian_awal_keperawatan_ranap.penilaian_nyeri_penyebab,penilaian_awal_keperawatan_ranap.penilaian_nyeri_ket_penyebab,penilaian_awal_keperawatan_ranap.penilaian_nyeri_kualitas,"+
                    "penilaian_awal_keperawatan_ranap.penilaian_nyeri_ket_kualitas,penilaian_awal_keperawatan_ranap.penilaian_nyeri_lokasi,penilaian_awal_keperawatan_ranap.penilaian_nyeri_menyebar,penilaian_awal_keperawatan_ranap.penilaian_nyeri_skala,"+
                    "penilaian_awal_keperawatan_ranap.penilaian_nyeri_waktu,penilaian_awal_keperawatan_ranap.penilaian_nyeri_hilang,penilaian_awal_keperawatan_ranap.penilaian_nyeri_ket_hilang,penilaian_awal_keperawatan_ranap.penilaian_nyeri_diberitahukan_dokter,"+
                    "penilaian_awal_keperawatan_ranap.penilaian_nyeri_jam_diberitahukan_dokter,penilaian_awal_keperawatan_ranap.penilaian_jatuhmorse_skala1,penilaian_awal_keperawatan_ranap.penilaian_jatuhmorse_nilai1,"+
                    "penilaian_awal_keperawatan_ranap.penilaian_jatuhmorse_skala2,penilaian_awal_keperawatan_ranap.penilaian_jatuhmorse_nilai2,penilaian_awal_keperawatan_ranap.penilaian_jatuhmorse_skala3,"+
                    "penilaian_awal_keperawatan_ranap.penilaian_jatuhmorse_nilai3,penilaian_awal_keperawatan_ranap.penilaian_jatuhmorse_skala4,penilaian_awal_keperawatan_ranap.penilaian_jatuhmorse_nilai4,"+
                    "penilaian_awal_keperawatan_ranap.penilaian_jatuhmorse_skala5,penilaian_awal_keperawatan_ranap.penilaian_jatuhmorse_nilai5,penilaian_awal_keperawatan_ranap.penilaian_jatuhmorse_skala6,"+
                    "penilaian_awal_keperawatan_ranap.penilaian_jatuhmorse_nilai6,penilaian_awal_keperawatan_ranap.penilaian_jatuhmorse_totalnilai,penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_skala1,"+
                    "penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_nilai1,penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_skala2,penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_nilai2,"+
                    "penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_skala3,penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_nilai3,penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_skala4,"+
                    "penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_nilai4,penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_skala5,penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_nilai5,"+
                    "penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_skala6,penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_nilai6,penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_skala7,"+
                    "penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_nilai7,penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_skala8,penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_nilai8,"+
                    "penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_skala9,penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_nilai9,penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_skala10,"+
                    "penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_nilai10,penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_skala11,penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_nilai11,"+
                    "penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_totalnilai,penilaian_awal_keperawatan_ranap.skrining_gizi1,penilaian_awal_keperawatan_ranap.nilai_gizi1,penilaian_awal_keperawatan_ranap.skrining_gizi2,"+
                    "penilaian_awal_keperawatan_ranap.nilai_gizi2,penilaian_awal_keperawatan_ranap.nilai_total_gizi,penilaian_awal_keperawatan_ranap.skrining_gizi_diagnosa_khusus,penilaian_awal_keperawatan_ranap.skrining_gizi_ket_diagnosa_khusus,"+
                    "penilaian_awal_keperawatan_ranap.skrining_gizi_diketahui_dietisen,penilaian_awal_keperawatan_ranap.skrining_gizi_jam_diketahui_dietisen,penilaian_awal_keperawatan_ranap.rencana,penilaian_awal_keperawatan_ranap.nip1,"+
                    "penilaian_awal_keperawatan_ranap.nip2,penilaian_awal_keperawatan_ranap.kd_dokter,pasien.tgl_lahir,pasien.jk,pengkaji1.nama as pengkaji1,pengkaji2.nama as pengkaji2,dokter.nm_dokter,"+
                    "reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.agama,pasien.pekerjaan,pasien.pnd,penjab.png_jawab,bahasa_pasien.nama_bahasa "+
                    "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join penilaian_awal_keperawatan_ranap on reg_periksa.no_rawat=penilaian_awal_keperawatan_ranap.no_rawat "+
                    "inner join petugas as pengkaji1 on penilaian_awal_keperawatan_ranap.nip1=pengkaji1.nip "+
                    "inner join petugas as pengkaji2 on penilaian_awal_keperawatan_ranap.nip2=pengkaji2.nip "+
                    "inner join dokter on penilaian_awal_keperawatan_ranap.kd_dokter=dokter.kd_dokter "+
                    "inner join bahasa_pasien on bahasa_pasien.id=pasien.bahasa_pasien "+
                    "inner join penjab on penjab.kd_pj=reg_periksa.kd_pj where "+
                    "penilaian_awal_keperawatan_ranap.tanggal between ? and ? "+
                    (TCari.getText().trim().equals("")?"":"and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or penilaian_awal_keperawatan_ranap.nip1 like ? or "+
                    "pengkaji1.nama like ? or penilaian_awal_keperawatan_ranap.kd_dokter like ? or dokter.nm_dokter like ?)")+
                    " order by penilaian_awal_keperawatan_ranap.tanggal");

                try {
                    ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                    ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                    if(!TCari.getText().equals("")){
                        ps.setString(3,"%"+TCari.getText()+"%");
                        ps.setString(4,"%"+TCari.getText()+"%");
                        ps.setString(5,"%"+TCari.getText()+"%");
                        ps.setString(6,"%"+TCari.getText()+"%");
                        ps.setString(7,"%"+TCari.getText()+"%");
                        ps.setString(8,"%"+TCari.getText()+"%");
                        ps.setString(9,"%"+TCari.getText()+"%");
                    }   
                    rs=ps.executeQuery();
                    pilihan = (String)JOptionPane.showInputDialog(null,"Silahkan pilih laporan..!","Pilihan Cetak",JOptionPane.QUESTION_MESSAGE,null,new Object[]{"Laporan 1 (HTML)","Laporan 2 (WPS)","Laporan 3 (CSV)"},"Laporan 1 (HTML)");
                    switch (pilihan) {
                        case "Laporan 1 (HTML)":
                                htmlContent = new StringBuilder();
                                htmlContent.append(                             
                                    "<tr class='isi'>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='105px'>No.Rawat</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>No.RM</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Nama Pasien</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Tgl.Lahir</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='25px'>J.K.</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>NIP Pengkaji 1</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Nama Pengkaji 1</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>NIP Pengkaji 2</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Nama Pengkaji 2</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Kode DPJP</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Nama DPJP</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='117px'>Tgl.Asuhan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='78px'>Macam Kasus</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Anamnesis</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='110px'>Tiba Di Ruang Rawat</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Cara Masuk</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='220px'>Riwayat Penyakit Saat Ini</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Riwayat Penyakit Dahulu</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Riwayat Penyakit Keluarga</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Riwayat Penggunaan Obat</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Riwayat Pembedahan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Riwayat Dirawat Di RS</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Alat Bantu Yang Dipakai</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='210px'>Dalam Keadaan Hamil/Sedang Menyusui</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Riwayat Transfusi Darah</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Riwayat Alergi</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='48px'>Merokok</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Batang/Hari</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='44px'>Alkohol</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='59px'>Gelas/Hari</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='61px'>Obat Tidur</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='59px'>Olah Raga</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Kesadaran Mental</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Keadaan Umum</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='64px'>GCS(E,V,M)</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='60px'>TD(mmHg)</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='74px'>Nadi(x/menit)</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='67px'>RR(x/menit)</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='52px'>Suhu(°C)</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='52px'>SpO2(%)</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='44px'>BB(Kg)</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='44px'>TB(cm)</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Kepala</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Wajah</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='106px'>Leher</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Kejang</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Sensorik</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='50px'>Pulsasi</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Sirkulasi</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='72px'>Denyut Nadi</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='54px'>Retraksi</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='63px'>Pola Nafas</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='69px'>Suara Nafas</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='97px'>Batuk & Sekresi</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='75px'>Volume</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Jenis Pernafasaan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Irama</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Mulut</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Lidah</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Gigi</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Tenggorokan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Abdomen</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Peistatik Usus</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Anus</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Sensorik</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Penglihatan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Alat Bantu Penglihatan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Motorik</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Pendengaran</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Bicara</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Otot</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Kulit</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Warna Kulit</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Turgor</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Resiko Decubitas</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Oedema</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Pergerakan Sendi</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Kekuatan Otot</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Fraktur</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Nyeri Sendi</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Frekuensi BAB</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>x/</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Konsistensi BAB</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Warna BAB</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Frekuensi BAK</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>x/</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Warna BAK</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Lain-lain BAK</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Mandi</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Makan/Minum</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Berpakaian</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Eliminasi</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Berpindah</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Porsi Makan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Frekuensi Makan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Jenis Makanan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Lama Tidur</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Gangguan Tidur</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>a. Aktifitas Sehari-hari</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>b. Berjalan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>c. Aktifitas</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>d. Alat Ambulasi</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>e. Ekstremitas Atas</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>f. Ekstremitas Bawah</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>g. Kemampuan Menggenggam</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>h. Kemampuan Koordinasi</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>i. Kesimpulan Gangguan Fungsi</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>a. Kondisi Psikologis</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>b. Adakah Perilaku</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>c. Gangguan Jiwa di Masa Lalu</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>d. Hubungan Pasien</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>e. Agama</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>f. Tinggal Dengan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>g. Pekerjaan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>h. Pembayaran</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>i. Nilai-nilai Kepercayaan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>j. Bahasa Sehari-hari</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>k. Pendidikan Pasien</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>l. Pendidikan P.J.</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>m. Edukasi Diberikan Kepada</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Nyeri</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Penyebab Nyeri</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Kualitas Nyeri</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Lokasi Nyeri</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Nyeri Menyebar</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Skala Nyeri</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Waktu / Durasi</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Nyeri Hilang Bila</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Diberitahukan Pada Dokter</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Skala Morse 1</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>N.M. 1</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Skala Morse 2</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>N.M. 2</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Skala Morse 3</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>N.M. 3</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Skala Morse 4</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>N.M. 4</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Skala Morse 5</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>N.M. 5</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Skala Morse 6</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>N.M. 6</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>T.M.</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Skala Sydney 1</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>N.S. 1</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Skala Sydney 2</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>N.S. 2</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Skala Sydney 3</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>N.S. 3</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Skala Sydney 4</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>N.S. 4</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Skala Sydney 5</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>N.S. 5</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Skala Sydney 6</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>N.S. 6</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Skala Sydney 7</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>N.S. 7</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Skala Sydney 8</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>N.S. 8</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Skala Sydney 9</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>N.S. 9</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Skala Sydney 10</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>N.S. 10</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Skala Sydney 11</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>N.S. 11</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>T.S.</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>1. Apakah ada penurunan BB yang tidak diinginkan selama 6 bulan terakhir ?</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Skor 1</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>2. Apakah asupan makan berkurang karena tidak nafsu makan ?</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Skor 2</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Total Skor</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Pasien dengan diagnosis khusus</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Keterangan Diagnosa Khusus</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Sudah dibaca dan diketahui oleh Dietisen</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Jam Dibaca Dietisen</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Rencana Keperawatan Lainnya</td>"+
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
                                            "<td valign='top'>"+rs.getString("nip1")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pengkaji1")+"</td>"+
                                            "<td valign='top'>"+rs.getString("nip2")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pengkaji2")+"</td>"+
                                            "<td valign='top'>"+rs.getString("kd_dokter")+"</td>"+
                                            "<td valign='top'>"+rs.getString("nm_dokter")+"</td>"+
                                            "<td valign='top'>"+rs.getString("tanggal")+"</td>"+
                                            "<td valign='top'>"+rs.getString("kasus_trauma")+"</td>"+
                                            "<td valign='top'>"+rs.getString("informasi")+", "+rs.getString("ket_informasi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("tiba_diruang_rawat")+"</td>"+
                                            "<td valign='top'>"+rs.getString("cara_masuk")+"</td>"+
                                            "<td valign='top'>"+rs.getString("rps")+"</td>"+
                                            "<td valign='top'>"+rs.getString("rpd")+"</td>"+
                                            "<td valign='top'>"+rs.getString("rpk")+"</td>"+
                                            "<td valign='top'>"+rs.getString("rpo")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_pembedahan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_dirawat_dirs")+"</td>"+
                                            "<td valign='top'>"+rs.getString("alat_bantu_dipakai")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_kehamilan")+", "+rs.getString("riwayat_kehamilan_perkiraan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_tranfusi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_alergi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_merokok")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_merokok_jumlah")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_alkohol")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_alkohol_jumlah")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_narkoba")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_olahraga")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_mental")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_keadaan_umum")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_gcs")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_td")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_nadi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_rr")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_suhu")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_spo2")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_bb")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_tb")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_susunan_kepala")+", "+rs.getString("pemeriksaan_susunan_kepala_keterangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_susunan_wajah")+", "+rs.getString("pemeriksaan_susunan_wajah_keterangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_susunan_leher")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_susunan_kejang")+", "+rs.getString("pemeriksaan_susunan_kejang_keterangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_susunan_sensorik")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_kardiovaskuler_pulsasi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_kardiovaskuler_sirkulasi")+", "+rs.getString("pemeriksaan_kardiovaskuler_sirkulasi_keterangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_kardiovaskuler_denyut_nadi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_respirasi_retraksi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_respirasi_pola_nafas")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_respirasi_suara_nafas")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_respirasi_batuk")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_respirasi_volume_pernafasan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_respirasi_jenis_pernafasan")+", "+rs.getString("pemeriksaan_respirasi_jenis_pernafasan_keterangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_respirasi_irama_nafas")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_gastrointestinal_mulut")+", "+rs.getString("pemeriksaan_gastrointestinal_mulut_keterangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_gastrointestinal_lidah")+", "+rs.getString("pemeriksaan_gastrointestinal_lidah_keterangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_gastrointestinal_gigi")+", "+rs.getString("pemeriksaan_gastrointestinal_gigi_keterangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_gastrointestinal_tenggorokan")+", "+rs.getString("pemeriksaan_gastrointestinal_tenggorokan_keterangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_gastrointestinal_abdomen")+", "+rs.getString("pemeriksaan_gastrointestinal_abdomen_keterangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_gastrointestinal_peistatik_usus")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_gastrointestinal_anus")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_neurologi_sensorik")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_neurologi_pengelihatan")+", "+rs.getString("pemeriksaan_neurologi_pengelihatan_keterangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_neurologi_alat_bantu_penglihatan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_neurologi_motorik")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_neurologi_pendengaran")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_neurologi_bicara")+", "+rs.getString("pemeriksaan_neurologi_bicara_keterangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_neurologi_kekuatan_otot")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_integument_kulit")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_integument_warnakulit")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_integument_turgor")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_integument_dekubitas")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_muskuloskletal_oedema")+", "+rs.getString("pemeriksaan_muskuloskletal_oedema_keterangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_muskuloskletal_pergerakan_sendi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_muskuloskletal_kekauatan_otot")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_muskuloskletal_fraktur")+", "+rs.getString("pemeriksaan_muskuloskletal_fraktur_keterangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_muskuloskletal_nyeri_sendi")+", "+rs.getString("pemeriksaan_muskuloskletal_nyeri_sendi_keterangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_eliminasi_bab_frekuensi_jumlah")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_eliminasi_bab_frekuensi_durasi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_eliminasi_bab_konsistensi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_eliminasi_bab_warna")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_eliminasi_bak_frekuensi_jumlah")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_eliminasi_bak_frekuensi_durasi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_eliminasi_bak_warna")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_eliminasi_bak_lainlain")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pola_aktifitas_mandi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pola_aktifitas_makanminum")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pola_aktifitas_berpakaian")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pola_aktifitas_eliminasi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pola_aktifitas_berpindah")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pola_nutrisi_porsi_makan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pola_nutrisi_frekuesi_makan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pola_nutrisi_jenis_makanan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pola_tidur_lama_tidur")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pola_tidur_gangguan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pengkajian_fungsi_kemampuan_sehari")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pengkajian_fungsi_berjalan")+", "+rs.getString("pengkajian_fungsi_berjalan_keterangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pengkajian_fungsi_aktifitas")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pengkajian_fungsi_ambulasi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pengkajian_fungsi_ekstrimitas_atas")+", "+rs.getString("pengkajian_fungsi_ekstrimitas_atas_keterangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pengkajian_fungsi_ekstrimitas_bawah")+", "+rs.getString("pengkajian_fungsi_ekstrimitas_bawah_keterangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pengkajian_fungsi_menggenggam")+", "+rs.getString("pengkajian_fungsi_menggenggam_keterangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pengkajian_fungsi_koordinasi")+", "+rs.getString("pengkajian_fungsi_koordinasi_keterangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pengkajian_fungsi_kesimpulan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_psiko_kondisi_psiko")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_psiko_perilaku")+", "+rs.getString("riwayat_psiko_perilaku_keterangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_psiko_gangguan_jiwa")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_psiko_hubungan_keluarga")+"</td>"+
                                            "<td valign='top'>"+rs.getString("agama")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_psiko_tinggal")+", "+rs.getString("riwayat_psiko_tinggal_keterangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pekerjaan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("png_jawab")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_psiko_nilai_kepercayaan")+", "+rs.getString("riwayat_psiko_nilai_kepercayaan_keterangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("nama_bahasa")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pnd")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_psiko_pendidikan_pj")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_psiko_edukasi_diberikan")+", "+rs.getString("riwayat_psiko_edukasi_diberikan_keterangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_nyeri")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_nyeri_penyebab")+", "+rs.getString("penilaian_nyeri_ket_penyebab")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_nyeri_kualitas")+", "+rs.getString("penilaian_nyeri_ket_kualitas")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_nyeri_lokasi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_nyeri_menyebar")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_nyeri_skala")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_nyeri_waktu")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_nyeri_hilang")+", "+rs.getString("penilaian_nyeri_ket_hilang")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_nyeri_diberitahukan_dokter")+", "+rs.getString("penilaian_nyeri_jam_diberitahukan_dokter")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhmorse_skala1")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhmorse_nilai1")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhmorse_skala2")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhmorse_nilai2")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhmorse_skala3")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhmorse_nilai3")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhmorse_skala4")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhmorse_nilai4")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhmorse_skala5")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhmorse_nilai5")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhmorse_skala6")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhmorse_nilai6")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhmorse_totalnilai")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhsydney_skala1")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhsydney_nilai1")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhsydney_skala2")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhsydney_nilai2")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhsydney_skala3")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhsydney_nilai3")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhsydney_skala4")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhsydney_nilai4")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhsydney_skala5")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhsydney_nilai5")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhsydney_skala6")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhsydney_nilai6")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhsydney_skala7")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhsydney_nilai7")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhsydney_skala8")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhsydney_nilai8")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhsydney_skala9")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhsydney_nilai9")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhsydney_skala10")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhsydney_nilai10")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhsydney_skala11")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhsydney_nilai11")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhsydney_totalnilai")+"</td>"+
                                            "<td valign='top'>"+rs.getString("skrining_gizi1")+"</td>"+
                                            "<td valign='top'>"+rs.getString("nilai_gizi1")+"</td>"+
                                            "<td valign='top'>"+rs.getString("skrining_gizi2")+"</td>"+
                                            "<td valign='top'>"+rs.getString("nilai_gizi2")+"</td>"+
                                            "<td valign='top'>"+rs.getString("nilai_total_gizi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("skrining_gizi_diagnosa_khusus")+"</td>"+
                                            "<td valign='top'>"+rs.getString("skrining_gizi_ket_diagnosa_khusus")+"</td>"+
                                            "<td valign='top'>"+rs.getString("skrining_gizi_diketahui_dietisen")+"</td>"+
                                            "<td valign='top'>"+rs.getString("skrining_gizi_jam_diketahui_dietisen")+"</td>"+
                                            "<td valign='top'>"+rs.getString("rencana")+"</td>"+
                                        "</tr>"
                                    );
                                }
                                f = new File("RMPenilaianAwalKeperawatanRanap.html");            
                                bw = new BufferedWriter(new FileWriter(f));            
                                bw.write("<html>"+
                                            "<head><link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" /></head>"+
                                            "<body>"+
                                                "<table width='18500px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
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
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='105px'>No.Rawat</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>No.RM</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Nama Pasien</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Tgl.Lahir</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='25px'>J.K.</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>NIP Pengkaji 1</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Nama Pengkaji 1</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>NIP Pengkaji 2</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Nama Pengkaji 2</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Kode DPJP</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Nama DPJP</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='117px'>Tgl.Asuhan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='78px'>Macam Kasus</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Anamnesis</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='110px'>Tiba Di Ruang Rawat</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Cara Masuk</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='220px'>Riwayat Penyakit Saat Ini</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Riwayat Penyakit Dahulu</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Riwayat Penyakit Keluarga</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Riwayat Penggunaan Obat</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Riwayat Pembedahan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Riwayat Dirawat Di RS</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Alat Bantu Yang Dipakai</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='210px'>Dalam Keadaan Hamil/Sedang Menyusui</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Riwayat Transfusi Darah</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Riwayat Alergi</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='48px'>Merokok</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Batang/Hari</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='44px'>Alkohol</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='59px'>Gelas/Hari</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='61px'>Obat Tidur</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='59px'>Olah Raga</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Kesadaran Mental</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Keadaan Umum</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='64px'>GCS(E,V,M)</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='60px'>TD(mmHg)</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='74px'>Nadi(x/menit)</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='67px'>RR(x/menit)</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='52px'>Suhu(°C)</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='52px'>SpO2(%)</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='44px'>BB(Kg)</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='44px'>TB(cm)</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Kepala</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Wajah</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='106px'>Leher</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Kejang</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Sensorik</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='50px'>Pulsasi</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Sirkulasi</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='72px'>Denyut Nadi</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='54px'>Retraksi</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='63px'>Pola Nafas</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='69px'>Suara Nafas</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='97px'>Batuk & Sekresi</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='75px'>Volume</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Jenis Pernafasaan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Irama</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Mulut</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Lidah</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Gigi</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Tenggorokan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Abdomen</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Peistatik Usus</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Anus</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Sensorik</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Penglihatan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Alat Bantu Penglihatan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Motorik</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Pendengaran</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Bicara</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Otot</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Kulit</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Warna Kulit</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Turgor</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Resiko Decubitas</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Oedema</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Pergerakan Sendi</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Kekuatan Otot</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Fraktur</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Nyeri Sendi</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Frekuensi BAB</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>x/</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Konsistensi BAB</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Warna BAB</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Frekuensi BAK</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>x/</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Warna BAK</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Lain-lain BAK</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Mandi</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Makan/Minum</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Berpakaian</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Eliminasi</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Berpindah</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Porsi Makan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Frekuensi Makan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Jenis Makanan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Lama Tidur</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Gangguan Tidur</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>a. Aktifitas Sehari-hari</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>b. Berjalan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>c. Aktifitas</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>d. Alat Ambulasi</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>e. Ekstremitas Atas</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>f. Ekstremitas Bawah</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>g. Kemampuan Menggenggam</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>h. Kemampuan Koordinasi</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>i. Kesimpulan Gangguan Fungsi</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>a. Kondisi Psikologis</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>b. Adakah Perilaku</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>c. Gangguan Jiwa di Masa Lalu</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>d. Hubungan Pasien</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>e. Agama</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>f. Tinggal Dengan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>g. Pekerjaan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>h. Pembayaran</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>i. Nilai-nilai Kepercayaan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>j. Bahasa Sehari-hari</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>k. Pendidikan Pasien</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>l. Pendidikan P.J.</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>m. Edukasi Diberikan Kepada</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Nyeri</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Penyebab Nyeri</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Kualitas Nyeri</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Lokasi Nyeri</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Nyeri Menyebar</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Skala Nyeri</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Waktu / Durasi</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Nyeri Hilang Bila</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Diberitahukan Pada Dokter</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Skala Morse 1</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>N.M. 1</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Skala Morse 2</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>N.M. 2</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Skala Morse 3</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>N.M. 3</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Skala Morse 4</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>N.M. 4</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Skala Morse 5</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>N.M. 5</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Skala Morse 6</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>N.M. 6</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>T.M.</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Skala Sydney 1</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>N.S. 1</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Skala Sydney 2</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>N.S. 2</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Skala Sydney 3</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>N.S. 3</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Skala Sydney 4</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>N.S. 4</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Skala Sydney 5</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>N.S. 5</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Skala Sydney 6</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>N.S. 6</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Skala Sydney 7</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>N.S. 7</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Skala Sydney 8</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>N.S. 8</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Skala Sydney 9</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>N.S. 9</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Skala Sydney 10</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>N.S. 10</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Skala Sydney 11</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>N.S. 11</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>T.S.</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>1. Apakah ada penurunan BB yang tidak diinginkan selama 6 bulan terakhir ?</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Skor 1</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>2. Apakah asupan makan berkurang karena tidak nafsu makan ?</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Skor 2</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Total Skor</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Pasien dengan diagnosis khusus</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Keterangan Diagnosa Khusus</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Sudah dibaca dan diketahui oleh Dietisen</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Jam Dibaca Dietisen</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center'>Rencana Keperawatan Lainnya</td>"+
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
                                            "<td valign='top'>"+rs.getString("nip1")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pengkaji1")+"</td>"+
                                            "<td valign='top'>"+rs.getString("nip2")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pengkaji2")+"</td>"+
                                            "<td valign='top'>"+rs.getString("kd_dokter")+"</td>"+
                                            "<td valign='top'>"+rs.getString("nm_dokter")+"</td>"+
                                            "<td valign='top'>"+rs.getString("tanggal")+"</td>"+
                                            "<td valign='top'>"+rs.getString("kasus_trauma")+"</td>"+
                                            "<td valign='top'>"+rs.getString("informasi")+", "+rs.getString("ket_informasi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("tiba_diruang_rawat")+"</td>"+
                                            "<td valign='top'>"+rs.getString("cara_masuk")+"</td>"+
                                            "<td valign='top'>"+rs.getString("rps")+"</td>"+
                                            "<td valign='top'>"+rs.getString("rpd")+"</td>"+
                                            "<td valign='top'>"+rs.getString("rpk")+"</td>"+
                                            "<td valign='top'>"+rs.getString("rpo")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_pembedahan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_dirawat_dirs")+"</td>"+
                                            "<td valign='top'>"+rs.getString("alat_bantu_dipakai")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_kehamilan")+", "+rs.getString("riwayat_kehamilan_perkiraan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_tranfusi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_alergi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_merokok")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_merokok_jumlah")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_alkohol")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_alkohol_jumlah")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_narkoba")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_olahraga")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_mental")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_keadaan_umum")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_gcs")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_td")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_nadi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_rr")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_suhu")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_spo2")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_bb")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_tb")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_susunan_kepala")+", "+rs.getString("pemeriksaan_susunan_kepala_keterangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_susunan_wajah")+", "+rs.getString("pemeriksaan_susunan_wajah_keterangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_susunan_leher")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_susunan_kejang")+", "+rs.getString("pemeriksaan_susunan_kejang_keterangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_susunan_sensorik")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_kardiovaskuler_pulsasi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_kardiovaskuler_sirkulasi")+", "+rs.getString("pemeriksaan_kardiovaskuler_sirkulasi_keterangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_kardiovaskuler_denyut_nadi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_respirasi_retraksi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_respirasi_pola_nafas")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_respirasi_suara_nafas")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_respirasi_batuk")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_respirasi_volume_pernafasan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_respirasi_jenis_pernafasan")+", "+rs.getString("pemeriksaan_respirasi_jenis_pernafasan_keterangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_respirasi_irama_nafas")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_gastrointestinal_mulut")+", "+rs.getString("pemeriksaan_gastrointestinal_mulut_keterangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_gastrointestinal_lidah")+", "+rs.getString("pemeriksaan_gastrointestinal_lidah_keterangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_gastrointestinal_gigi")+", "+rs.getString("pemeriksaan_gastrointestinal_gigi_keterangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_gastrointestinal_tenggorokan")+", "+rs.getString("pemeriksaan_gastrointestinal_tenggorokan_keterangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_gastrointestinal_abdomen")+", "+rs.getString("pemeriksaan_gastrointestinal_abdomen_keterangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_gastrointestinal_peistatik_usus")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_gastrointestinal_anus")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_neurologi_sensorik")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_neurologi_pengelihatan")+", "+rs.getString("pemeriksaan_neurologi_pengelihatan_keterangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_neurologi_alat_bantu_penglihatan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_neurologi_motorik")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_neurologi_pendengaran")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_neurologi_bicara")+", "+rs.getString("pemeriksaan_neurologi_bicara_keterangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_neurologi_kekuatan_otot")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_integument_kulit")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_integument_warnakulit")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_integument_turgor")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_integument_dekubitas")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_muskuloskletal_oedema")+", "+rs.getString("pemeriksaan_muskuloskletal_oedema_keterangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_muskuloskletal_pergerakan_sendi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_muskuloskletal_kekauatan_otot")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_muskuloskletal_fraktur")+", "+rs.getString("pemeriksaan_muskuloskletal_fraktur_keterangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_muskuloskletal_nyeri_sendi")+", "+rs.getString("pemeriksaan_muskuloskletal_nyeri_sendi_keterangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_eliminasi_bab_frekuensi_jumlah")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_eliminasi_bab_frekuensi_durasi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_eliminasi_bab_konsistensi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_eliminasi_bab_warna")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_eliminasi_bak_frekuensi_jumlah")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_eliminasi_bak_frekuensi_durasi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_eliminasi_bak_warna")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_eliminasi_bak_lainlain")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pola_aktifitas_mandi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pola_aktifitas_makanminum")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pola_aktifitas_berpakaian")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pola_aktifitas_eliminasi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pola_aktifitas_berpindah")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pola_nutrisi_porsi_makan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pola_nutrisi_frekuesi_makan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pola_nutrisi_jenis_makanan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pola_tidur_lama_tidur")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pola_tidur_gangguan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pengkajian_fungsi_kemampuan_sehari")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pengkajian_fungsi_berjalan")+", "+rs.getString("pengkajian_fungsi_berjalan_keterangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pengkajian_fungsi_aktifitas")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pengkajian_fungsi_ambulasi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pengkajian_fungsi_ekstrimitas_atas")+", "+rs.getString("pengkajian_fungsi_ekstrimitas_atas_keterangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pengkajian_fungsi_ekstrimitas_bawah")+", "+rs.getString("pengkajian_fungsi_ekstrimitas_bawah_keterangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pengkajian_fungsi_menggenggam")+", "+rs.getString("pengkajian_fungsi_menggenggam_keterangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pengkajian_fungsi_koordinasi")+", "+rs.getString("pengkajian_fungsi_koordinasi_keterangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pengkajian_fungsi_kesimpulan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_psiko_kondisi_psiko")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_psiko_perilaku")+", "+rs.getString("riwayat_psiko_perilaku_keterangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_psiko_gangguan_jiwa")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_psiko_hubungan_keluarga")+"</td>"+
                                            "<td valign='top'>"+rs.getString("agama")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_psiko_tinggal")+", "+rs.getString("riwayat_psiko_tinggal_keterangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pekerjaan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("png_jawab")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_psiko_nilai_kepercayaan")+", "+rs.getString("riwayat_psiko_nilai_kepercayaan_keterangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("nama_bahasa")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pnd")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_psiko_pendidikan_pj")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_psiko_edukasi_diberikan")+", "+rs.getString("riwayat_psiko_edukasi_diberikan_keterangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_nyeri")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_nyeri_penyebab")+", "+rs.getString("penilaian_nyeri_ket_penyebab")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_nyeri_kualitas")+", "+rs.getString("penilaian_nyeri_ket_kualitas")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_nyeri_lokasi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_nyeri_menyebar")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_nyeri_skala")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_nyeri_waktu")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_nyeri_hilang")+", "+rs.getString("penilaian_nyeri_ket_hilang")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_nyeri_diberitahukan_dokter")+", "+rs.getString("penilaian_nyeri_jam_diberitahukan_dokter")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhmorse_skala1")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhmorse_nilai1")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhmorse_skala2")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhmorse_nilai2")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhmorse_skala3")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhmorse_nilai3")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhmorse_skala4")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhmorse_nilai4")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhmorse_skala5")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhmorse_nilai5")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhmorse_skala6")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhmorse_nilai6")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhmorse_totalnilai")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhsydney_skala1")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhsydney_nilai1")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhsydney_skala2")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhsydney_nilai2")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhsydney_skala3")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhsydney_nilai3")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhsydney_skala4")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhsydney_nilai4")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhsydney_skala5")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhsydney_nilai5")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhsydney_skala6")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhsydney_nilai6")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhsydney_skala7")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhsydney_nilai7")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhsydney_skala8")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhsydney_nilai8")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhsydney_skala9")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhsydney_nilai9")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhsydney_skala10")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhsydney_nilai10")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhsydney_skala11")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhsydney_nilai11")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuhsydney_totalnilai")+"</td>"+
                                            "<td valign='top'>"+rs.getString("skrining_gizi1")+"</td>"+
                                            "<td valign='top'>"+rs.getString("nilai_gizi1")+"</td>"+
                                            "<td valign='top'>"+rs.getString("skrining_gizi2")+"</td>"+
                                            "<td valign='top'>"+rs.getString("nilai_gizi2")+"</td>"+
                                            "<td valign='top'>"+rs.getString("nilai_total_gizi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("skrining_gizi_diagnosa_khusus")+"</td>"+
                                            "<td valign='top'>"+rs.getString("skrining_gizi_ket_diagnosa_khusus")+"</td>"+
                                            "<td valign='top'>"+rs.getString("skrining_gizi_diketahui_dietisen")+"</td>"+
                                            "<td valign='top'>"+rs.getString("skrining_gizi_jam_diketahui_dietisen")+"</td>"+
                                            "<td valign='top'>"+rs.getString("rencana")+"</td>"+
                                        "</tr>"
                                    );
                                }
                                f = new File("RMPenilaianAwalKeperawatanRanap.wps");            
                                bw = new BufferedWriter(new FileWriter(f));            
                                bw.write("<html>"+
                                            "<head><link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" /></head>"+
                                            "<body>"+
                                                "<table width='18500px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
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
                                    "\"No.Rawat\";\"No.RM\";\"Nama Pasien\";\"Tgl.Lahir\";\"J.K.\";\"NIP Pengkaji 1\";\"Nama Pengkaji 1\";\"NIP Pengkaji 2\";\"Nama Pengkaji 2\";\"Kode DPJP\";\"Nama DPJP\";\"Tgl.Asuhan\";\"Macam Kasus\";\"Anamnesis\";\"Tiba Di Ruang Rawat\";\"Cara Masuk\";\"Riwayat Penyakit Saat Ini\";\"Riwayat Penyakit Dahulu\";\"Riwayat Penyakit Keluarga\";\"Riwayat Penggunaan Obat\";\"Riwayat Pembedahan\";\"Riwayat Dirawat Di RS\";\"Alat Bantu Yang Dipakai\";\"Dalam Keadaan Hamil/Sedang Menyusui\";\"Riwayat Transfusi Darah\";\"Riwayat Alergi\";\"Merokok\";\"Batang/Hari\";\"Alkohol\";\"Gelas/Hari\";\"Obat Tidur\";\"Olah Raga\";\"Kesadaran Mental\";\"Keadaan Umum\";\"GCS(E,V,M)\";\"TD(mmHg)\";\"Nadi(x/menit)\";\"RR(x/menit)\";\"Suhu(°C)\";\"SpO2(%)\";\"BB(Kg)\";\"TB(cm)\";\"Kepala\";\"Wajah\";\"Leher\";\"Kejang\";\"Sensorik\";\"Pulsasi\";\"Sirkulasi\";\"Denyut Nadi\";\"Retraksi\";\"Pola Nafas\";\"Suara Nafas\";\"Batuk & Sekresi\";\"Volume\";\"Jenis Pernafasaan\";\"Irama\";\"Mulut\";\"Lidah\";\"Gigi\";\"Tenggorokan\";\"Abdomen\";\"Peistatik Usus\";\"Anus\";\"Sensorik\";\"Penglihatan\";\"Alat Bantu Penglihatan\";\"Motorik\";\"Pendengaran\";\"Bicara\";\"Otot\";\"Kulit\";\"Warna Kulit\";\"Turgor\";\"Resiko Decubitas\";\"Oedema\";\"Pergerakan Sendi\";\"Kekuatan Otot\";\"Fraktur\";\"Nyeri Sendi\";\"Frekuensi BAB\";\"x/\";\"Konsistensi BAB\";\"Warna BAB\";\"Frekuensi BAK\";\"x/\";\"Warna BAK\";\"Lain-lain BAK\";\"Mandi\";\"Makan/Minum\";\"Berpakaian\";\"Eliminasi\";\"Berpindah\";\"Porsi Makan\";\"Frekuensi Makan\";\"Jenis Makanan\";\"Lama Tidur\";\"Gangguan Tidur\";\"a. Aktifitas Sehari-hari\";\"b. Berjalan\";\"c. Aktifitas\";\"d. Alat Ambulasi\";\"e. Ekstremitas Atas\";\"f. Ekstremitas Bawah\";\"g. Kemampuan Menggenggam\";\"h. Kemampuan Koordinasi\";\"i. Kesimpulan Gangguan Fungsi\";\"a. Kondisi Psikologis\";\"b. Adakah Perilaku\";\"c. Gangguan Jiwa di Masa Lalu\";\"d. Hubungan Pasien\";\"e. Agama\";\"f. Tinggal Dengan\";\"g. Pekerjaan\";\"h. Pembayaran\";\"i. Nilai-nilai Kepercayaan\";\"j. Bahasa Sehari-hari\";\"k. Pendidikan Pasien\";\"l. Pendidikan P.J.\";\"m. Edukasi Diberikan Kepada\";\"Nyeri\";\"Penyebab Nyeri\";\"Kualitas Nyeri\";\"Lokasi Nyeri\";\"Nyeri Menyebar\";\"Skala Nyeri\";\"Waktu / Durasi\";\"Nyeri Hilang Bila\";\"Diberitahukan Pada Dokter\";\"Skala Morse 1\";\"N.M. 1\";\"Skala Morse 2\";\"N.M. 2\";\"Skala Morse 3\";\"N.M. 3\";\"Skala Morse 4\";\"N.M. 4\";\"Skala Morse 5\";\"N.M. 5\";\"Skala Morse 6\";\"N.M. 6\";\"T.M.\";\"Skala Sydney 1\";\"N.S. 1\";\"Skala Sydney 2\";\"N.S. 2\";\"Skala Sydney 3\";\"N.S. 3\";\"Skala Sydney 4\";\"N.S. 4\";\"Skala Sydney 5\";\"N.S. 5\";\"Skala Sydney 6\";\"N.S. 6\";\"Skala Sydney 7\";\"N.S. 7\";\"Skala Sydney 8\";\"N.S. 8\";\"Skala Sydney 9\";\"N.S. 9\";\"Skala Sydney 10\";\"N.S. 10\";\"Skala Sydney 11\";\"N.S. 11\";\"T.S.\";\"1. Apakah ada penurunan BB yang tidak diinginkan selama 6 bulan terakhir ?\";\"Skor 1\";\"2. Apakah asupan makan berkurang karena tidak nafsu makan ?\";\"Skor 2\";\"Total Skor\";\"Pasien dengan diagnosis khusus\";\"Keterangan Diagnosa Khusus\";\"Sudah dibaca dan diketahui oleh Dietisen\";\"Jam Dibaca Dietisen\";\"Rencana Keperawatan Lainnya\"\n"
                                ); 
                                while(rs.next()){
                                    htmlContent.append(
                                        "\""+rs.getString("no_rawat")+"\";\""+rs.getString("no_rkm_medis")+"\";\""+rs.getString("nm_pasien")+"\";\""+rs.getString("tgl_lahir")+"\";\""+rs.getString("jk")+"\";\""+rs.getString("nip1")+"\";\""+rs.getString("pengkaji1")+"\";\""+rs.getString("nip2")+"\";\""+rs.getString("pengkaji2")+"\";\""+rs.getString("kd_dokter")+"\";\""+rs.getString("nm_dokter")+"\";\""+rs.getString("tanggal")+"\";\""+rs.getString("kasus_trauma")+"\";\""+rs.getString("informasi")+", "+rs.getString("ket_informasi")+"\";\""+rs.getString("tiba_diruang_rawat")+"\";\""+rs.getString("cara_masuk")+"\";\""+rs.getString("rps")+"\";\""+rs.getString("rpd")+"\";\""+rs.getString("rpk")+"\";\""+rs.getString("rpo")+"\";\""+rs.getString("riwayat_pembedahan")+"\";\""+rs.getString("riwayat_dirawat_dirs")+"\";\""+rs.getString("alat_bantu_dipakai")+"\";\""+rs.getString("riwayat_kehamilan")+", "+rs.getString("riwayat_kehamilan_perkiraan")+"\";\""+rs.getString("riwayat_tranfusi")+"\";\""+rs.getString("riwayat_alergi")+"\";\""+rs.getString("riwayat_merokok")+"\";\""+rs.getString("riwayat_merokok_jumlah")+"\";\""+rs.getString("riwayat_alkohol")+"\";\""+rs.getString("riwayat_alkohol_jumlah")+"\";\""+rs.getString("riwayat_narkoba")+"\";\""+rs.getString("riwayat_olahraga")+"\";\""+rs.getString("pemeriksaan_mental")+"\";\""+rs.getString("pemeriksaan_keadaan_umum")+"\";\""+rs.getString("pemeriksaan_gcs")+"\";\""+rs.getString("pemeriksaan_td")+"\";\""+rs.getString("pemeriksaan_nadi")+"\";\""+rs.getString("pemeriksaan_rr")+"\";\""+rs.getString("pemeriksaan_suhu")+"\";\""+rs.getString("pemeriksaan_spo2")+"\";\""+rs.getString("pemeriksaan_bb")+"\";\""+rs.getString("pemeriksaan_tb")+"\";\""+rs.getString("pemeriksaan_susunan_kepala")+", "+rs.getString("pemeriksaan_susunan_kepala_keterangan")+"\";\""+rs.getString("pemeriksaan_susunan_wajah")+", "+rs.getString("pemeriksaan_susunan_wajah_keterangan")+"\";\""+rs.getString("pemeriksaan_susunan_leher")+"\";\""+rs.getString("pemeriksaan_susunan_kejang")+", "+rs.getString("pemeriksaan_susunan_kejang_keterangan")+"\";\""+rs.getString("pemeriksaan_susunan_sensorik")+"\";\""+rs.getString("pemeriksaan_kardiovaskuler_pulsasi")+"\";\""+rs.getString("pemeriksaan_kardiovaskuler_sirkulasi")+", "+rs.getString("pemeriksaan_kardiovaskuler_sirkulasi_keterangan")+"\";\""+rs.getString("pemeriksaan_kardiovaskuler_denyut_nadi")+"\";\""+rs.getString("pemeriksaan_respirasi_retraksi")+"\";\""+rs.getString("pemeriksaan_respirasi_pola_nafas")+"\";\""+rs.getString("pemeriksaan_respirasi_suara_nafas")+"\";\""+rs.getString("pemeriksaan_respirasi_batuk")+"\";\""+rs.getString("pemeriksaan_respirasi_volume_pernafasan")+"\";\""+rs.getString("pemeriksaan_respirasi_jenis_pernafasan")+", "+rs.getString("pemeriksaan_respirasi_jenis_pernafasan_keterangan")+"\";\""+rs.getString("pemeriksaan_respirasi_irama_nafas")+"\";\""+rs.getString("pemeriksaan_gastrointestinal_mulut")+", "+rs.getString("pemeriksaan_gastrointestinal_mulut_keterangan")+"\";\""+rs.getString("pemeriksaan_gastrointestinal_lidah")+", "+rs.getString("pemeriksaan_gastrointestinal_lidah_keterangan")+"\";\""+rs.getString("pemeriksaan_gastrointestinal_gigi")+", "+rs.getString("pemeriksaan_gastrointestinal_gigi_keterangan")+"\";\""+rs.getString("pemeriksaan_gastrointestinal_tenggorokan")+", "+rs.getString("pemeriksaan_gastrointestinal_tenggorokan_keterangan")+"\";\""+rs.getString("pemeriksaan_gastrointestinal_abdomen")+", "+rs.getString("pemeriksaan_gastrointestinal_abdomen_keterangan")+"\";\""+rs.getString("pemeriksaan_gastrointestinal_peistatik_usus")+"\";\""+rs.getString("pemeriksaan_gastrointestinal_anus")+"\";\""+rs.getString("pemeriksaan_neurologi_sensorik")+"\";\""+rs.getString("pemeriksaan_neurologi_pengelihatan")+", "+rs.getString("pemeriksaan_neurologi_pengelihatan_keterangan")+"\";\""+rs.getString("pemeriksaan_neurologi_alat_bantu_penglihatan")+"\";\""+rs.getString("pemeriksaan_neurologi_motorik")+"\";\""+rs.getString("pemeriksaan_neurologi_pendengaran")+"\";\""+rs.getString("pemeriksaan_neurologi_bicara")+", "+rs.getString("pemeriksaan_neurologi_bicara_keterangan")+"\";\""+rs.getString("pemeriksaan_neurologi_kekuatan_otot")+"\";\""+rs.getString("pemeriksaan_integument_kulit")+"\";\""+rs.getString("pemeriksaan_integument_warnakulit")+"\";\""+rs.getString("pemeriksaan_integument_turgor")+"\";\""+rs.getString("pemeriksaan_integument_dekubitas")+"\";\""+rs.getString("pemeriksaan_muskuloskletal_oedema")+", "+rs.getString("pemeriksaan_muskuloskletal_oedema_keterangan")+"\";\""+rs.getString("pemeriksaan_muskuloskletal_pergerakan_sendi")+"\";\""+rs.getString("pemeriksaan_muskuloskletal_kekauatan_otot")+"\";\""+rs.getString("pemeriksaan_muskuloskletal_fraktur")+", "+rs.getString("pemeriksaan_muskuloskletal_fraktur_keterangan")+"\";\""+rs.getString("pemeriksaan_muskuloskletal_nyeri_sendi")+", "+rs.getString("pemeriksaan_muskuloskletal_nyeri_sendi_keterangan")+"\";\""+rs.getString("pemeriksaan_eliminasi_bab_frekuensi_jumlah")+"\";\""+rs.getString("pemeriksaan_eliminasi_bab_frekuensi_durasi")+"\";\""+rs.getString("pemeriksaan_eliminasi_bab_konsistensi")+"\";\""+rs.getString("pemeriksaan_eliminasi_bab_warna")+"\";\""+rs.getString("pemeriksaan_eliminasi_bak_frekuensi_jumlah")+"\";\""+rs.getString("pemeriksaan_eliminasi_bak_frekuensi_durasi")+"\";\""+rs.getString("pemeriksaan_eliminasi_bak_warna")+"\";\""+rs.getString("pemeriksaan_eliminasi_bak_lainlain")+"\";\""+rs.getString("pola_aktifitas_mandi")+"\";\""+rs.getString("pola_aktifitas_makanminum")+"\";\""+rs.getString("pola_aktifitas_berpakaian")+"\";\""+rs.getString("pola_aktifitas_eliminasi")+"\";\""+rs.getString("pola_aktifitas_berpindah")+"\";\""+rs.getString("pola_nutrisi_porsi_makan")+"\";\""+rs.getString("pola_nutrisi_frekuesi_makan")+"\";\""+rs.getString("pola_nutrisi_jenis_makanan")+"\";\""+rs.getString("pola_tidur_lama_tidur")+"\";\""+rs.getString("pola_tidur_gangguan")+"\";\""+rs.getString("pengkajian_fungsi_kemampuan_sehari")+"\";\""+rs.getString("pengkajian_fungsi_berjalan")+", "+rs.getString("pengkajian_fungsi_berjalan_keterangan")+"\";\""+rs.getString("pengkajian_fungsi_aktifitas")+"\";\""+rs.getString("pengkajian_fungsi_ambulasi")+"\";\""+rs.getString("pengkajian_fungsi_ekstrimitas_atas")+", "+rs.getString("pengkajian_fungsi_ekstrimitas_atas_keterangan")+"\";\""+rs.getString("pengkajian_fungsi_ekstrimitas_bawah")+", "+rs.getString("pengkajian_fungsi_ekstrimitas_bawah_keterangan")+"\";\""+rs.getString("pengkajian_fungsi_menggenggam")+", "+rs.getString("pengkajian_fungsi_menggenggam_keterangan")+"\";\""+rs.getString("pengkajian_fungsi_koordinasi")+", "+rs.getString("pengkajian_fungsi_koordinasi_keterangan")+"\";\""+rs.getString("pengkajian_fungsi_kesimpulan")+"\";\""+rs.getString("riwayat_psiko_kondisi_psiko")+"\";\""+rs.getString("riwayat_psiko_perilaku")+", "+rs.getString("riwayat_psiko_perilaku_keterangan")+"\";\""+rs.getString("riwayat_psiko_gangguan_jiwa")+"\";\""+rs.getString("riwayat_psiko_hubungan_keluarga")+"\";\""+rs.getString("agama")+"\";\""+rs.getString("riwayat_psiko_tinggal")+", "+rs.getString("riwayat_psiko_tinggal_keterangan")+"\";\""+rs.getString("pekerjaan")+"\";\""+rs.getString("png_jawab")+"\";\""+rs.getString("riwayat_psiko_nilai_kepercayaan")+", "+rs.getString("riwayat_psiko_nilai_kepercayaan_keterangan")+"\";\""+rs.getString("nama_bahasa")+"\";\""+rs.getString("pnd")+"\";\""+rs.getString("riwayat_psiko_pendidikan_pj")+"\";\""+rs.getString("riwayat_psiko_edukasi_diberikan")+", "+rs.getString("riwayat_psiko_edukasi_diberikan_keterangan")+"\";\""+rs.getString("penilaian_nyeri")+"\";\""+rs.getString("penilaian_nyeri_penyebab")+", "+rs.getString("penilaian_nyeri_ket_penyebab")+"\";\""+rs.getString("penilaian_nyeri_kualitas")+", "+rs.getString("penilaian_nyeri_ket_kualitas")+"\";\""+rs.getString("penilaian_nyeri_lokasi")+"\";\""+rs.getString("penilaian_nyeri_menyebar")+"\";\""+rs.getString("penilaian_nyeri_skala")+"\";\""+rs.getString("penilaian_nyeri_waktu")+"\";\""+rs.getString("penilaian_nyeri_hilang")+", "+rs.getString("penilaian_nyeri_ket_hilang")+"\";\""+rs.getString("penilaian_nyeri_diberitahukan_dokter")+", "+rs.getString("penilaian_nyeri_jam_diberitahukan_dokter")+"\";\""+rs.getString("penilaian_jatuhmorse_skala1")+"\";\""+rs.getString("penilaian_jatuhmorse_nilai1")+"\";\""+rs.getString("penilaian_jatuhmorse_skala2")+"\";\""+rs.getString("penilaian_jatuhmorse_nilai2")+"\";\""+rs.getString("penilaian_jatuhmorse_skala3")+"\";\""+rs.getString("penilaian_jatuhmorse_nilai3")+"\";\""+rs.getString("penilaian_jatuhmorse_skala4")+"\";\""+rs.getString("penilaian_jatuhmorse_nilai4")+"\";\""+rs.getString("penilaian_jatuhmorse_skala5")+"\";\""+rs.getString("penilaian_jatuhmorse_nilai5")+"\";\""+rs.getString("penilaian_jatuhmorse_skala6")+"\";\""+rs.getString("penilaian_jatuhmorse_nilai6")+"\";\""+rs.getString("penilaian_jatuhmorse_totalnilai")+"\";\""+rs.getString("penilaian_jatuhsydney_skala1")+"\";\""+rs.getString("penilaian_jatuhsydney_nilai1")+"\";\""+rs.getString("penilaian_jatuhsydney_skala2")+"\";\""+rs.getString("penilaian_jatuhsydney_nilai2")+"\";\""+rs.getString("penilaian_jatuhsydney_skala3")+"\";\""+rs.getString("penilaian_jatuhsydney_nilai3")+"\";\""+rs.getString("penilaian_jatuhsydney_skala4")+"\";\""+rs.getString("penilaian_jatuhsydney_nilai4")+"\";\""+rs.getString("penilaian_jatuhsydney_skala5")+"\";\""+rs.getString("penilaian_jatuhsydney_nilai5")+"\";\""+rs.getString("penilaian_jatuhsydney_skala6")+"\";\""+rs.getString("penilaian_jatuhsydney_nilai6")+"\";\""+rs.getString("penilaian_jatuhsydney_skala7")+"\";\""+rs.getString("penilaian_jatuhsydney_nilai7")+"\";\""+rs.getString("penilaian_jatuhsydney_skala8")+"\";\""+rs.getString("penilaian_jatuhsydney_nilai8")+"\";\""+rs.getString("penilaian_jatuhsydney_skala9")+"\";\""+rs.getString("penilaian_jatuhsydney_nilai9")+"\";\""+rs.getString("penilaian_jatuhsydney_skala10")+"\";\""+rs.getString("penilaian_jatuhsydney_nilai10")+"\";\""+rs.getString("penilaian_jatuhsydney_skala11")+"\";\""+rs.getString("penilaian_jatuhsydney_nilai11")+"\";\""+rs.getString("penilaian_jatuhsydney_totalnilai")+"\";\""+rs.getString("skrining_gizi1")+"\";\""+rs.getString("nilai_gizi1")+"\";\""+rs.getString("skrining_gizi2")+"\";\""+rs.getString("nilai_gizi2")+"\";\""+rs.getString("nilai_total_gizi")+"\";\""+rs.getString("skrining_gizi_diagnosa_khusus")+"\";\""+rs.getString("skrining_gizi_ket_diagnosa_khusus")+"\";\""+rs.getString("skrining_gizi_diketahui_dietisen")+"\";\""+rs.getString("skrining_gizi_jam_diketahui_dietisen")+"\";\""+rs.getString("rencana")+"\"\n"
                                    );
                                }
                                f = new File("RMPenilaianAwalKeperawatanRanap.csv");            
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

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if(TabRawat.getSelectedIndex()==1){
            tampil();
        }
    }//GEN-LAST:event_TabRawatMouseClicked

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        try {
            if(Valid.daysOld("./cache/masalahkeperawatanneonatus.iyem")<30){
                tampilMasalah2();
            }else{
                tampilMasalah();
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_formWindowOpened

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
            finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
            param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbObat.getValueAt(tbObat.getSelectedRow(),6).toString()+"\nID "+(finger.equals("")?tbObat.getValueAt(tbObat.getSelectedRow(),5).toString():finger)+"\n"+Valid.SetTgl3(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString())); 
            
            try {
                masalahkeperawatan="";
                ps=koneksi.prepareStatement(
                    "select master_masalah_keperawatan_neonatus.kode_masalah,master_masalah_keperawatan_neonatus.nama_masalah from master_masalah_keperawatan_neonatus "+
                    "inner join penilaian_awal_keperawatan_ranap_masalah on penilaian_awal_keperawatan_ranap_masalah.kode_masalah=master_masalah_keperawatan_neonatus.kode_masalah "+
                    "where penilaian_awal_keperawatan_ranap_masalah.no_rawat=? order by penilaian_awal_keperawatan_ranap_masalah.kode_masalah");
                try {
                    ps.setString(1,tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
                    rs=ps.executeQuery();
                    while(rs.next()){
                        masalahkeperawatan=rs.getString("nama_masalah")+", "+masalahkeperawatan;
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
            param.put("masalah",masalahkeperawatan);  
            try {
                masalahkeperawatan="";
                ps=koneksi.prepareStatement(
                    "select master_rencana_keperawatan_neonatus.kode_rencana,master_rencana_keperawatan_neonatus.rencana_keperawatan from master_rencana_keperawatan_neonatus "+
                    "inner join penilaian_awal_keperawatan_ranap_rencana on penilaian_awal_keperawatan_ranap_rencana.kode_rencana=master_rencana_keperawatan_neonatus.kode_rencana "+
                    "where penilaian_awal_keperawatan_ranap_rencana.no_rawat=? order by penilaian_awal_keperawatan_ranap_rencana.kode_rencana");
                try {
                    ps.setString(1,tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
                    rs=ps.executeQuery();
                    while(rs.next()){
                        masalahkeperawatan=rs.getString("rencana_keperawatan")+", "+masalahkeperawatan;
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
            param.put("rencana",masalahkeperawatan); 
            Valid.MyReportqry("rptCetakPenilaianAwalKeperawatanRanap.jasper","report","::[ Laporan Penilaian Awal Keperawatan Rawat Inap ]::",
                "select penilaian_awal_keperawatan_ranap.no_rawat,penilaian_awal_keperawatan_ranap.tanggal,penilaian_awal_keperawatan_ranap.informasi,penilaian_awal_keperawatan_ranap.ket_informasi,penilaian_awal_keperawatan_ranap.tiba_diruang_rawat,"+
                "penilaian_awal_keperawatan_ranap.kasus_trauma,penilaian_awal_keperawatan_ranap.cara_masuk,penilaian_awal_keperawatan_ranap.rps,penilaian_awal_keperawatan_ranap.rpd,penilaian_awal_keperawatan_ranap.rpk,penilaian_awal_keperawatan_ranap.rpo,"+
                "penilaian_awal_keperawatan_ranap.riwayat_pembedahan,penilaian_awal_keperawatan_ranap.riwayat_dirawat_dirs,penilaian_awal_keperawatan_ranap.alat_bantu_dipakai,penilaian_awal_keperawatan_ranap.riwayat_kehamilan,"+
                "penilaian_awal_keperawatan_ranap.riwayat_kehamilan_perkiraan,penilaian_awal_keperawatan_ranap.riwayat_tranfusi,penilaian_awal_keperawatan_ranap.riwayat_alergi,penilaian_awal_keperawatan_ranap.riwayat_merokok,"+
                "penilaian_awal_keperawatan_ranap.riwayat_merokok_jumlah,penilaian_awal_keperawatan_ranap.riwayat_alkohol,penilaian_awal_keperawatan_ranap.riwayat_alkohol_jumlah,penilaian_awal_keperawatan_ranap.riwayat_narkoba,"+
                "penilaian_awal_keperawatan_ranap.riwayat_olahraga,penilaian_awal_keperawatan_ranap.pemeriksaan_mental,penilaian_awal_keperawatan_ranap.pemeriksaan_keadaan_umum,penilaian_awal_keperawatan_ranap.pemeriksaan_gcs,"+
                "penilaian_awal_keperawatan_ranap.pemeriksaan_td,penilaian_awal_keperawatan_ranap.pemeriksaan_nadi,penilaian_awal_keperawatan_ranap.pemeriksaan_rr,penilaian_awal_keperawatan_ranap.pemeriksaan_suhu,"+
                "penilaian_awal_keperawatan_ranap.pemeriksaan_spo2,penilaian_awal_keperawatan_ranap.pemeriksaan_bb,penilaian_awal_keperawatan_ranap.pemeriksaan_tb,penilaian_awal_keperawatan_ranap.pemeriksaan_susunan_kepala,"+
                "penilaian_awal_keperawatan_ranap.pemeriksaan_susunan_kepala_keterangan,penilaian_awal_keperawatan_ranap.pemeriksaan_susunan_wajah,penilaian_awal_keperawatan_ranap.pemeriksaan_susunan_wajah_keterangan,"+
                "penilaian_awal_keperawatan_ranap.pemeriksaan_susunan_leher,penilaian_awal_keperawatan_ranap.pemeriksaan_susunan_kejang,penilaian_awal_keperawatan_ranap.pemeriksaan_susunan_kejang_keterangan,"+
                "penilaian_awal_keperawatan_ranap.pemeriksaan_susunan_sensorik,penilaian_awal_keperawatan_ranap.pemeriksaan_kardiovaskuler_denyut_nadi,penilaian_awal_keperawatan_ranap.pemeriksaan_kardiovaskuler_sirkulasi,"+
                "penilaian_awal_keperawatan_ranap.pemeriksaan_kardiovaskuler_sirkulasi_keterangan,penilaian_awal_keperawatan_ranap.pemeriksaan_kardiovaskuler_pulsasi,penilaian_awal_keperawatan_ranap.pemeriksaan_respirasi_pola_nafas,"+
                "penilaian_awal_keperawatan_ranap.pemeriksaan_respirasi_retraksi,penilaian_awal_keperawatan_ranap.pemeriksaan_respirasi_suara_nafas,penilaian_awal_keperawatan_ranap.pemeriksaan_respirasi_volume_pernafasan,"+
                "penilaian_awal_keperawatan_ranap.pemeriksaan_respirasi_jenis_pernafasan,penilaian_awal_keperawatan_ranap.pemeriksaan_respirasi_jenis_pernafasan_keterangan,penilaian_awal_keperawatan_ranap.pemeriksaan_respirasi_irama_nafas,"+
                "penilaian_awal_keperawatan_ranap.pemeriksaan_respirasi_batuk,penilaian_awal_keperawatan_ranap.pemeriksaan_gastrointestinal_mulut,penilaian_awal_keperawatan_ranap.pemeriksaan_gastrointestinal_mulut_keterangan,"+
                "penilaian_awal_keperawatan_ranap.pemeriksaan_gastrointestinal_gigi,penilaian_awal_keperawatan_ranap.pemeriksaan_gastrointestinal_gigi_keterangan,penilaian_awal_keperawatan_ranap.pemeriksaan_gastrointestinal_lidah,"+
                "penilaian_awal_keperawatan_ranap.pemeriksaan_gastrointestinal_lidah_keterangan,penilaian_awal_keperawatan_ranap.pemeriksaan_gastrointestinal_tenggorokan,penilaian_awal_keperawatan_ranap.pemeriksaan_gastrointestinal_tenggorokan_keterangan,"+
                "penilaian_awal_keperawatan_ranap.pemeriksaan_gastrointestinal_abdomen,penilaian_awal_keperawatan_ranap.pemeriksaan_gastrointestinal_abdomen_keterangan,penilaian_awal_keperawatan_ranap.pemeriksaan_gastrointestinal_peistatik_usus,"+
                "penilaian_awal_keperawatan_ranap.pemeriksaan_gastrointestinal_anus,penilaian_awal_keperawatan_ranap.pemeriksaan_neurologi_pengelihatan,penilaian_awal_keperawatan_ranap.pemeriksaan_neurologi_pengelihatan_keterangan,"+
                "penilaian_awal_keperawatan_ranap.pemeriksaan_neurologi_alat_bantu_penglihatan,penilaian_awal_keperawatan_ranap.pemeriksaan_neurologi_pendengaran,penilaian_awal_keperawatan_ranap.pemeriksaan_neurologi_bicara,"+
                "penilaian_awal_keperawatan_ranap.pemeriksaan_neurologi_bicara_keterangan,penilaian_awal_keperawatan_ranap.pemeriksaan_neurologi_sensorik,penilaian_awal_keperawatan_ranap.pemeriksaan_neurologi_motorik,"+
                "penilaian_awal_keperawatan_ranap.pemeriksaan_neurologi_kekuatan_otot,penilaian_awal_keperawatan_ranap.pemeriksaan_integument_warnakulit,penilaian_awal_keperawatan_ranap.pemeriksaan_integument_turgor,"+
                "penilaian_awal_keperawatan_ranap.pemeriksaan_integument_kulit,penilaian_awal_keperawatan_ranap.pemeriksaan_integument_dekubitas,penilaian_awal_keperawatan_ranap.pemeriksaan_muskuloskletal_pergerakan_sendi,"+
                "penilaian_awal_keperawatan_ranap.pemeriksaan_muskuloskletal_kekauatan_otot,penilaian_awal_keperawatan_ranap.pemeriksaan_muskuloskletal_nyeri_sendi,penilaian_awal_keperawatan_ranap.pemeriksaan_muskuloskletal_nyeri_sendi_keterangan,"+
                "penilaian_awal_keperawatan_ranap.pemeriksaan_muskuloskletal_oedema,penilaian_awal_keperawatan_ranap.pemeriksaan_muskuloskletal_oedema_keterangan,penilaian_awal_keperawatan_ranap.pemeriksaan_muskuloskletal_fraktur,"+
                "penilaian_awal_keperawatan_ranap.pemeriksaan_muskuloskletal_fraktur_keterangan,penilaian_awal_keperawatan_ranap.pemeriksaan_eliminasi_bab_frekuensi_jumlah,penilaian_awal_keperawatan_ranap.pemeriksaan_eliminasi_bab_frekuensi_durasi,"+
                "penilaian_awal_keperawatan_ranap.pemeriksaan_eliminasi_bab_konsistensi,penilaian_awal_keperawatan_ranap.pemeriksaan_eliminasi_bab_warna,penilaian_awal_keperawatan_ranap.pemeriksaan_eliminasi_bak_frekuensi_jumlah,"+
                "penilaian_awal_keperawatan_ranap.pemeriksaan_eliminasi_bak_frekuensi_durasi,penilaian_awal_keperawatan_ranap.pemeriksaan_eliminasi_bak_warna,penilaian_awal_keperawatan_ranap.pemeriksaan_eliminasi_bak_lainlain,"+
                "penilaian_awal_keperawatan_ranap.pola_aktifitas_makanminum,penilaian_awal_keperawatan_ranap.pola_aktifitas_mandi,penilaian_awal_keperawatan_ranap.pola_aktifitas_eliminasi,penilaian_awal_keperawatan_ranap.pola_aktifitas_berpakaian,"+
                "penilaian_awal_keperawatan_ranap.pola_aktifitas_berpindah,penilaian_awal_keperawatan_ranap.pola_nutrisi_frekuesi_makan,penilaian_awal_keperawatan_ranap.pola_nutrisi_jenis_makanan,penilaian_awal_keperawatan_ranap.pola_nutrisi_porsi_makan,"+
                "penilaian_awal_keperawatan_ranap.pola_tidur_lama_tidur,penilaian_awal_keperawatan_ranap.pola_tidur_gangguan,penilaian_awal_keperawatan_ranap.pengkajian_fungsi_kemampuan_sehari,penilaian_awal_keperawatan_ranap.pengkajian_fungsi_aktifitas,"+
                "penilaian_awal_keperawatan_ranap.pengkajian_fungsi_berjalan,penilaian_awal_keperawatan_ranap.pengkajian_fungsi_berjalan_keterangan,penilaian_awal_keperawatan_ranap.pengkajian_fungsi_ambulasi,"+
                "penilaian_awal_keperawatan_ranap.pengkajian_fungsi_ekstrimitas_atas,penilaian_awal_keperawatan_ranap.pengkajian_fungsi_ekstrimitas_atas_keterangan,penilaian_awal_keperawatan_ranap.pengkajian_fungsi_ekstrimitas_bawah,"+
                "penilaian_awal_keperawatan_ranap.pengkajian_fungsi_ekstrimitas_bawah_keterangan,penilaian_awal_keperawatan_ranap.pengkajian_fungsi_menggenggam,penilaian_awal_keperawatan_ranap.pengkajian_fungsi_menggenggam_keterangan,"+
                "penilaian_awal_keperawatan_ranap.pengkajian_fungsi_koordinasi,penilaian_awal_keperawatan_ranap.pengkajian_fungsi_koordinasi_keterangan,penilaian_awal_keperawatan_ranap.pengkajian_fungsi_kesimpulan,"+
                "penilaian_awal_keperawatan_ranap.riwayat_psiko_kondisi_psiko,penilaian_awal_keperawatan_ranap.riwayat_psiko_gangguan_jiwa,penilaian_awal_keperawatan_ranap.riwayat_psiko_perilaku,"+
                "penilaian_awal_keperawatan_ranap.riwayat_psiko_perilaku_keterangan,penilaian_awal_keperawatan_ranap.riwayat_psiko_hubungan_keluarga,penilaian_awal_keperawatan_ranap.riwayat_psiko_tinggal,"+
                "penilaian_awal_keperawatan_ranap.riwayat_psiko_tinggal_keterangan,penilaian_awal_keperawatan_ranap.riwayat_psiko_nilai_kepercayaan,penilaian_awal_keperawatan_ranap.riwayat_psiko_nilai_kepercayaan_keterangan,"+
                "penilaian_awal_keperawatan_ranap.riwayat_psiko_pendidikan_pj,penilaian_awal_keperawatan_ranap.riwayat_psiko_edukasi_diberikan,penilaian_awal_keperawatan_ranap.riwayat_psiko_edukasi_diberikan_keterangan,"+
                "penilaian_awal_keperawatan_ranap.penilaian_nyeri,penilaian_awal_keperawatan_ranap.penilaian_nyeri_penyebab,penilaian_awal_keperawatan_ranap.penilaian_nyeri_ket_penyebab,penilaian_awal_keperawatan_ranap.penilaian_nyeri_kualitas,"+
                "penilaian_awal_keperawatan_ranap.penilaian_nyeri_ket_kualitas,penilaian_awal_keperawatan_ranap.penilaian_nyeri_lokasi,penilaian_awal_keperawatan_ranap.penilaian_nyeri_menyebar,penilaian_awal_keperawatan_ranap.penilaian_nyeri_skala,"+
                "penilaian_awal_keperawatan_ranap.penilaian_nyeri_waktu,penilaian_awal_keperawatan_ranap.penilaian_nyeri_hilang,penilaian_awal_keperawatan_ranap.penilaian_nyeri_ket_hilang,penilaian_awal_keperawatan_ranap.penilaian_nyeri_diberitahukan_dokter,"+
                "penilaian_awal_keperawatan_ranap.penilaian_nyeri_jam_diberitahukan_dokter,penilaian_awal_keperawatan_ranap.penilaian_jatuhmorse_skala1,penilaian_awal_keperawatan_ranap.penilaian_jatuhmorse_nilai1,"+
                "penilaian_awal_keperawatan_ranap.penilaian_jatuhmorse_skala2,penilaian_awal_keperawatan_ranap.penilaian_jatuhmorse_nilai2,penilaian_awal_keperawatan_ranap.penilaian_jatuhmorse_skala3,"+
                "penilaian_awal_keperawatan_ranap.penilaian_jatuhmorse_nilai3,penilaian_awal_keperawatan_ranap.penilaian_jatuhmorse_skala4,penilaian_awal_keperawatan_ranap.penilaian_jatuhmorse_nilai4,"+
                "penilaian_awal_keperawatan_ranap.penilaian_jatuhmorse_skala5,penilaian_awal_keperawatan_ranap.penilaian_jatuhmorse_nilai5,penilaian_awal_keperawatan_ranap.penilaian_jatuhmorse_skala6,"+
                "penilaian_awal_keperawatan_ranap.penilaian_jatuhmorse_nilai6,penilaian_awal_keperawatan_ranap.penilaian_jatuhmorse_totalnilai,penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_skala1,"+
                "penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_nilai1,penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_skala2,penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_nilai2,"+
                "penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_skala3,penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_nilai3,penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_skala4,"+
                "penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_nilai4,penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_skala5,penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_nilai5,"+
                "penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_skala6,penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_nilai6,penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_skala7,"+
                "penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_nilai7,penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_skala8,penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_nilai8,"+
                "penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_skala9,penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_nilai9,penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_skala10,"+
                "penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_nilai10,penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_skala11,penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_nilai11,"+
                "penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_totalnilai,penilaian_awal_keperawatan_ranap.skrining_gizi1,penilaian_awal_keperawatan_ranap.nilai_gizi1,penilaian_awal_keperawatan_ranap.skrining_gizi2,"+
                "penilaian_awal_keperawatan_ranap.nilai_gizi2,penilaian_awal_keperawatan_ranap.nilai_total_gizi,penilaian_awal_keperawatan_ranap.skrining_gizi_diagnosa_khusus,penilaian_awal_keperawatan_ranap.skrining_gizi_ket_diagnosa_khusus,"+
                "penilaian_awal_keperawatan_ranap.skrining_gizi_diketahui_dietisen,penilaian_awal_keperawatan_ranap.skrining_gizi_jam_diketahui_dietisen,penilaian_awal_keperawatan_ranap.rencana,penilaian_awal_keperawatan_ranap.nip1,"+
                "penilaian_awal_keperawatan_ranap.nip2,penilaian_awal_keperawatan_ranap.kd_dokter,pasien.tgl_lahir,pasien.jk,pengkaji1.nama as pengkaji1,pengkaji2.nama as pengkaji2,dokter.nm_dokter,"+
                "reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.agama,pasien.pekerjaan,pasien.pnd,penjab.png_jawab,bahasa_pasien.nama_bahasa "+
                "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join penilaian_awal_keperawatan_ranap on reg_periksa.no_rawat=penilaian_awal_keperawatan_ranap.no_rawat "+
                "inner join petugas as pengkaji1 on penilaian_awal_keperawatan_ranap.nip1=pengkaji1.nip "+
                "inner join petugas as pengkaji2 on penilaian_awal_keperawatan_ranap.nip2=pengkaji2.nip "+
                "inner join dokter on penilaian_awal_keperawatan_ranap.kd_dokter=dokter.kd_dokter "+
                "inner join bahasa_pasien on bahasa_pasien.id=pasien.bahasa_pasien "+
                "inner join penjab on penjab.kd_pj=reg_periksa.kd_pj where reg_periksa.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'",param);
            
            Valid.MyReportqry("rptCetakPenilaianAwalKeperawatanRanap2.jasper","report","::[ Laporan Penilaian Awal Keperawatan Rawat Inap ]::",
                "select penilaian_awal_keperawatan_ranap.no_rawat,penilaian_awal_keperawatan_ranap.tanggal,penilaian_awal_keperawatan_ranap.informasi,penilaian_awal_keperawatan_ranap.ket_informasi,penilaian_awal_keperawatan_ranap.tiba_diruang_rawat,"+
                "penilaian_awal_keperawatan_ranap.kasus_trauma,penilaian_awal_keperawatan_ranap.cara_masuk,penilaian_awal_keperawatan_ranap.rps,penilaian_awal_keperawatan_ranap.rpd,penilaian_awal_keperawatan_ranap.rpk,penilaian_awal_keperawatan_ranap.rpo,"+
                "penilaian_awal_keperawatan_ranap.riwayat_pembedahan,penilaian_awal_keperawatan_ranap.riwayat_dirawat_dirs,penilaian_awal_keperawatan_ranap.alat_bantu_dipakai,penilaian_awal_keperawatan_ranap.riwayat_kehamilan,"+
                "penilaian_awal_keperawatan_ranap.riwayat_kehamilan_perkiraan,penilaian_awal_keperawatan_ranap.riwayat_tranfusi,penilaian_awal_keperawatan_ranap.riwayat_alergi,penilaian_awal_keperawatan_ranap.riwayat_merokok,"+
                "penilaian_awal_keperawatan_ranap.riwayat_merokok_jumlah,penilaian_awal_keperawatan_ranap.riwayat_alkohol,penilaian_awal_keperawatan_ranap.riwayat_alkohol_jumlah,penilaian_awal_keperawatan_ranap.riwayat_narkoba,"+
                "penilaian_awal_keperawatan_ranap.riwayat_olahraga,penilaian_awal_keperawatan_ranap.pemeriksaan_mental,penilaian_awal_keperawatan_ranap.pemeriksaan_keadaan_umum,penilaian_awal_keperawatan_ranap.pemeriksaan_gcs,"+
                "penilaian_awal_keperawatan_ranap.pemeriksaan_td,penilaian_awal_keperawatan_ranap.pemeriksaan_nadi,penilaian_awal_keperawatan_ranap.pemeriksaan_rr,penilaian_awal_keperawatan_ranap.pemeriksaan_suhu,"+
                "penilaian_awal_keperawatan_ranap.pemeriksaan_spo2,penilaian_awal_keperawatan_ranap.pemeriksaan_bb,penilaian_awal_keperawatan_ranap.pemeriksaan_tb,penilaian_awal_keperawatan_ranap.pemeriksaan_susunan_kepala,"+
                "penilaian_awal_keperawatan_ranap.pemeriksaan_susunan_kepala_keterangan,penilaian_awal_keperawatan_ranap.pemeriksaan_susunan_wajah,penilaian_awal_keperawatan_ranap.pemeriksaan_susunan_wajah_keterangan,"+
                "penilaian_awal_keperawatan_ranap.pemeriksaan_susunan_leher,penilaian_awal_keperawatan_ranap.pemeriksaan_susunan_kejang,penilaian_awal_keperawatan_ranap.pemeriksaan_susunan_kejang_keterangan,"+
                "penilaian_awal_keperawatan_ranap.pemeriksaan_susunan_sensorik,penilaian_awal_keperawatan_ranap.pemeriksaan_kardiovaskuler_denyut_nadi,penilaian_awal_keperawatan_ranap.pemeriksaan_kardiovaskuler_sirkulasi,"+
                "penilaian_awal_keperawatan_ranap.pemeriksaan_kardiovaskuler_sirkulasi_keterangan,penilaian_awal_keperawatan_ranap.pemeriksaan_kardiovaskuler_pulsasi,penilaian_awal_keperawatan_ranap.pemeriksaan_respirasi_pola_nafas,"+
                "penilaian_awal_keperawatan_ranap.pemeriksaan_respirasi_retraksi,penilaian_awal_keperawatan_ranap.pemeriksaan_respirasi_suara_nafas,penilaian_awal_keperawatan_ranap.pemeriksaan_respirasi_volume_pernafasan,"+
                "penilaian_awal_keperawatan_ranap.pemeriksaan_respirasi_jenis_pernafasan,penilaian_awal_keperawatan_ranap.pemeriksaan_respirasi_jenis_pernafasan_keterangan,penilaian_awal_keperawatan_ranap.pemeriksaan_respirasi_irama_nafas,"+
                "penilaian_awal_keperawatan_ranap.pemeriksaan_respirasi_batuk,penilaian_awal_keperawatan_ranap.pemeriksaan_gastrointestinal_mulut,penilaian_awal_keperawatan_ranap.pemeriksaan_gastrointestinal_mulut_keterangan,"+
                "penilaian_awal_keperawatan_ranap.pemeriksaan_gastrointestinal_gigi,penilaian_awal_keperawatan_ranap.pemeriksaan_gastrointestinal_gigi_keterangan,penilaian_awal_keperawatan_ranap.pemeriksaan_gastrointestinal_lidah,"+
                "penilaian_awal_keperawatan_ranap.pemeriksaan_gastrointestinal_lidah_keterangan,penilaian_awal_keperawatan_ranap.pemeriksaan_gastrointestinal_tenggorokan,penilaian_awal_keperawatan_ranap.pemeriksaan_gastrointestinal_tenggorokan_keterangan,"+
                "penilaian_awal_keperawatan_ranap.pemeriksaan_gastrointestinal_abdomen,penilaian_awal_keperawatan_ranap.pemeriksaan_gastrointestinal_abdomen_keterangan,penilaian_awal_keperawatan_ranap.pemeriksaan_gastrointestinal_peistatik_usus,"+
                "penilaian_awal_keperawatan_ranap.pemeriksaan_gastrointestinal_anus,penilaian_awal_keperawatan_ranap.pemeriksaan_neurologi_pengelihatan,penilaian_awal_keperawatan_ranap.pemeriksaan_neurologi_pengelihatan_keterangan,"+
                "penilaian_awal_keperawatan_ranap.pemeriksaan_neurologi_alat_bantu_penglihatan,penilaian_awal_keperawatan_ranap.pemeriksaan_neurologi_pendengaran,penilaian_awal_keperawatan_ranap.pemeriksaan_neurologi_bicara,"+
                "penilaian_awal_keperawatan_ranap.pemeriksaan_neurologi_bicara_keterangan,penilaian_awal_keperawatan_ranap.pemeriksaan_neurologi_sensorik,penilaian_awal_keperawatan_ranap.pemeriksaan_neurologi_motorik,"+
                "penilaian_awal_keperawatan_ranap.pemeriksaan_neurologi_kekuatan_otot,penilaian_awal_keperawatan_ranap.pemeriksaan_integument_warnakulit,penilaian_awal_keperawatan_ranap.pemeriksaan_integument_turgor,"+
                "penilaian_awal_keperawatan_ranap.pemeriksaan_integument_kulit,penilaian_awal_keperawatan_ranap.pemeriksaan_integument_dekubitas,penilaian_awal_keperawatan_ranap.pemeriksaan_muskuloskletal_pergerakan_sendi,"+
                "penilaian_awal_keperawatan_ranap.pemeriksaan_muskuloskletal_kekauatan_otot,penilaian_awal_keperawatan_ranap.pemeriksaan_muskuloskletal_nyeri_sendi,penilaian_awal_keperawatan_ranap.pemeriksaan_muskuloskletal_nyeri_sendi_keterangan,"+
                "penilaian_awal_keperawatan_ranap.pemeriksaan_muskuloskletal_oedema,penilaian_awal_keperawatan_ranap.pemeriksaan_muskuloskletal_oedema_keterangan,penilaian_awal_keperawatan_ranap.pemeriksaan_muskuloskletal_fraktur,"+
                "penilaian_awal_keperawatan_ranap.pemeriksaan_muskuloskletal_fraktur_keterangan,penilaian_awal_keperawatan_ranap.pemeriksaan_eliminasi_bab_frekuensi_jumlah,penilaian_awal_keperawatan_ranap.pemeriksaan_eliminasi_bab_frekuensi_durasi,"+
                "penilaian_awal_keperawatan_ranap.pemeriksaan_eliminasi_bab_konsistensi,penilaian_awal_keperawatan_ranap.pemeriksaan_eliminasi_bab_warna,penilaian_awal_keperawatan_ranap.pemeriksaan_eliminasi_bak_frekuensi_jumlah,"+
                "penilaian_awal_keperawatan_ranap.pemeriksaan_eliminasi_bak_frekuensi_durasi,penilaian_awal_keperawatan_ranap.pemeriksaan_eliminasi_bak_warna,penilaian_awal_keperawatan_ranap.pemeriksaan_eliminasi_bak_lainlain,"+
                "penilaian_awal_keperawatan_ranap.pola_aktifitas_makanminum,penilaian_awal_keperawatan_ranap.pola_aktifitas_mandi,penilaian_awal_keperawatan_ranap.pola_aktifitas_eliminasi,penilaian_awal_keperawatan_ranap.pola_aktifitas_berpakaian,"+
                "penilaian_awal_keperawatan_ranap.pola_aktifitas_berpindah,penilaian_awal_keperawatan_ranap.pola_nutrisi_frekuesi_makan,penilaian_awal_keperawatan_ranap.pola_nutrisi_jenis_makanan,penilaian_awal_keperawatan_ranap.pola_nutrisi_porsi_makan,"+
                "penilaian_awal_keperawatan_ranap.pola_tidur_lama_tidur,penilaian_awal_keperawatan_ranap.pola_tidur_gangguan,penilaian_awal_keperawatan_ranap.pengkajian_fungsi_kemampuan_sehari,penilaian_awal_keperawatan_ranap.pengkajian_fungsi_aktifitas,"+
                "penilaian_awal_keperawatan_ranap.pengkajian_fungsi_berjalan,penilaian_awal_keperawatan_ranap.pengkajian_fungsi_berjalan_keterangan,penilaian_awal_keperawatan_ranap.pengkajian_fungsi_ambulasi,"+
                "penilaian_awal_keperawatan_ranap.pengkajian_fungsi_ekstrimitas_atas,penilaian_awal_keperawatan_ranap.pengkajian_fungsi_ekstrimitas_atas_keterangan,penilaian_awal_keperawatan_ranap.pengkajian_fungsi_ekstrimitas_bawah,"+
                "penilaian_awal_keperawatan_ranap.pengkajian_fungsi_ekstrimitas_bawah_keterangan,penilaian_awal_keperawatan_ranap.pengkajian_fungsi_menggenggam,penilaian_awal_keperawatan_ranap.pengkajian_fungsi_menggenggam_keterangan,"+
                "penilaian_awal_keperawatan_ranap.pengkajian_fungsi_koordinasi,penilaian_awal_keperawatan_ranap.pengkajian_fungsi_koordinasi_keterangan,penilaian_awal_keperawatan_ranap.pengkajian_fungsi_kesimpulan,"+
                "penilaian_awal_keperawatan_ranap.riwayat_psiko_kondisi_psiko,penilaian_awal_keperawatan_ranap.riwayat_psiko_gangguan_jiwa,penilaian_awal_keperawatan_ranap.riwayat_psiko_perilaku,"+
                "penilaian_awal_keperawatan_ranap.riwayat_psiko_perilaku_keterangan,penilaian_awal_keperawatan_ranap.riwayat_psiko_hubungan_keluarga,penilaian_awal_keperawatan_ranap.riwayat_psiko_tinggal,"+
                "penilaian_awal_keperawatan_ranap.riwayat_psiko_tinggal_keterangan,penilaian_awal_keperawatan_ranap.riwayat_psiko_nilai_kepercayaan,penilaian_awal_keperawatan_ranap.riwayat_psiko_nilai_kepercayaan_keterangan,"+
                "penilaian_awal_keperawatan_ranap.riwayat_psiko_pendidikan_pj,penilaian_awal_keperawatan_ranap.riwayat_psiko_edukasi_diberikan,penilaian_awal_keperawatan_ranap.riwayat_psiko_edukasi_diberikan_keterangan,"+
                "penilaian_awal_keperawatan_ranap.penilaian_nyeri,penilaian_awal_keperawatan_ranap.penilaian_nyeri_penyebab,penilaian_awal_keperawatan_ranap.penilaian_nyeri_ket_penyebab,penilaian_awal_keperawatan_ranap.penilaian_nyeri_kualitas,"+
                "penilaian_awal_keperawatan_ranap.penilaian_nyeri_ket_kualitas,penilaian_awal_keperawatan_ranap.penilaian_nyeri_lokasi,penilaian_awal_keperawatan_ranap.penilaian_nyeri_menyebar,penilaian_awal_keperawatan_ranap.penilaian_nyeri_skala,"+
                "penilaian_awal_keperawatan_ranap.penilaian_nyeri_waktu,penilaian_awal_keperawatan_ranap.penilaian_nyeri_hilang,penilaian_awal_keperawatan_ranap.penilaian_nyeri_ket_hilang,penilaian_awal_keperawatan_ranap.penilaian_nyeri_diberitahukan_dokter,"+
                "penilaian_awal_keperawatan_ranap.penilaian_nyeri_jam_diberitahukan_dokter,penilaian_awal_keperawatan_ranap.penilaian_jatuhmorse_skala1,penilaian_awal_keperawatan_ranap.penilaian_jatuhmorse_nilai1,"+
                "penilaian_awal_keperawatan_ranap.penilaian_jatuhmorse_skala2,penilaian_awal_keperawatan_ranap.penilaian_jatuhmorse_nilai2,penilaian_awal_keperawatan_ranap.penilaian_jatuhmorse_skala3,"+
                "penilaian_awal_keperawatan_ranap.penilaian_jatuhmorse_nilai3,penilaian_awal_keperawatan_ranap.penilaian_jatuhmorse_skala4,penilaian_awal_keperawatan_ranap.penilaian_jatuhmorse_nilai4,"+
                "penilaian_awal_keperawatan_ranap.penilaian_jatuhmorse_skala5,penilaian_awal_keperawatan_ranap.penilaian_jatuhmorse_nilai5,penilaian_awal_keperawatan_ranap.penilaian_jatuhmorse_skala6,"+
                "penilaian_awal_keperawatan_ranap.penilaian_jatuhmorse_nilai6,penilaian_awal_keperawatan_ranap.penilaian_jatuhmorse_totalnilai,penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_skala1,"+
                "penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_nilai1,penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_skala2,penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_nilai2,"+
                "penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_skala3,penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_nilai3,penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_skala4,"+
                "penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_nilai4,penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_skala5,penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_nilai5,"+
                "penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_skala6,penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_nilai6,penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_skala7,"+
                "penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_nilai7,penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_skala8,penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_nilai8,"+
                "penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_skala9,penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_nilai9,penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_skala10,"+
                "penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_nilai10,penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_skala11,penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_nilai11,"+
                "penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_totalnilai,penilaian_awal_keperawatan_ranap.skrining_gizi1,penilaian_awal_keperawatan_ranap.nilai_gizi1,penilaian_awal_keperawatan_ranap.skrining_gizi2,"+
                "penilaian_awal_keperawatan_ranap.nilai_gizi2,penilaian_awal_keperawatan_ranap.nilai_total_gizi,penilaian_awal_keperawatan_ranap.skrining_gizi_diagnosa_khusus,penilaian_awal_keperawatan_ranap.skrining_gizi_ket_diagnosa_khusus,"+
                "penilaian_awal_keperawatan_ranap.skrining_gizi_diketahui_dietisen,penilaian_awal_keperawatan_ranap.skrining_gizi_jam_diketahui_dietisen,penilaian_awal_keperawatan_ranap.rencana,penilaian_awal_keperawatan_ranap.nip1,"+
                "penilaian_awal_keperawatan_ranap.nip2,penilaian_awal_keperawatan_ranap.kd_dokter,pasien.tgl_lahir,pasien.jk,pengkaji1.nama as pengkaji1,pengkaji2.nama as pengkaji2,dokter.nm_dokter,"+
                "reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.agama,pasien.pekerjaan,pasien.pnd,penjab.png_jawab,bahasa_pasien.nama_bahasa "+
                "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join penilaian_awal_keperawatan_ranap on reg_periksa.no_rawat=penilaian_awal_keperawatan_ranap.no_rawat "+
                "inner join petugas as pengkaji1 on penilaian_awal_keperawatan_ranap.nip1=pengkaji1.nip "+
                "inner join petugas as pengkaji2 on penilaian_awal_keperawatan_ranap.nip2=pengkaji2.nip "+
                "inner join dokter on penilaian_awal_keperawatan_ranap.kd_dokter=dokter.kd_dokter "+
                "inner join bahasa_pasien on bahasa_pasien.id=pasien.bahasa_pasien "+
                "inner join penjab on penjab.kd_pj=reg_periksa.kd_pj where reg_periksa.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'",param);
        }else{
            JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data terlebih dahulu..!!!!");
        }  
    }//GEN-LAST:event_BtnPrint1ActionPerformed

    private void TglAsuhanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglAsuhanKeyPressed
        Valid.pindah(evt,BtnDPJP,AsalPasien);
    }//GEN-LAST:event_TglAsuhanKeyPressed

    private void CaraMasukKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CaraMasukKeyPressed
        Valid.pindah(evt,AsalPasien,DiperolehDari);
    }//GEN-LAST:event_CaraMasukKeyPressed

    private void BtnPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPetugasKeyPressed
        Valid.pindah(evt,BtnSimpan,BtnPetugas2);
    }//GEN-LAST:event_BtnPetugasKeyPressed

    private void BtnPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPetugasActionPerformed
        i=1;
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setAlwaysOnTop(false);
        petugas.setVisible(true);
    }//GEN-LAST:event_BtnPetugasActionPerformed

    private void KdPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdPetugasKeyPressed

    }//GEN-LAST:event_KdPetugasKeyPressed

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isRawat();
        }else{
            Valid.pindah(evt,TCari,BtnPetugas);
        }
    }//GEN-LAST:event_TNoRwKeyPressed

    private void BtnPetugas2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPetugas2ActionPerformed
        i=2;
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setAlwaysOnTop(false);
        petugas.setVisible(true);
    }//GEN-LAST:event_BtnPetugas2ActionPerformed

    private void BtnPetugas2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPetugas2KeyPressed
        Valid.pindah(evt,BtnPetugas,BtnDPJP);
    }//GEN-LAST:event_BtnPetugas2KeyPressed

    private void KdPetugas2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdPetugas2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KdPetugas2KeyPressed

    private void KdDPJPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdDPJPKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KdDPJPKeyPressed

    private void BtnDPJPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDPJPActionPerformed
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setAlwaysOnTop(false);
        dokter.setVisible(true);
    }//GEN-LAST:event_BtnDPJPActionPerformed

    private void BtnDPJPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDPJPKeyPressed
        Valid.pindah(evt,BtnPetugas2,AsalPasien);
    }//GEN-LAST:event_BtnDPJPKeyPressed

    private void DetailRencanaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DetailRencanaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DetailRencanaKeyPressed

    private void AsalPasienKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AsalPasienKeyPressed
        Valid.pindah(evt,BtnDPJP,CaraMasuk);
    }//GEN-LAST:event_AsalPasienKeyPressed

    private void DiperolehDariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiperolehDariKeyPressed
        //Valid.pindah(evt,Anamnesis,TibadiRuang);
    }//GEN-LAST:event_DiperolehDariKeyPressed

    private void tbMasalahKeperawatanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbMasalahKeperawatanMouseClicked
        if(tabModeMasalah.getRowCount()!=0){
            try {
                tampilRencana2();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbMasalahKeperawatanMouseClicked

    private void tbMasalahKeperawatanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbMasalahKeperawatanKeyPressed
        if(tabModeMasalah.getRowCount()!=0){
            if(evt.getKeyCode()==KeyEvent.VK_SHIFT){
                TCariMasalah.setText("");
                TCariMasalah.requestFocus();
            }
        }
    }//GEN-LAST:event_tbMasalahKeperawatanKeyPressed

    private void tbMasalahKeperawatanKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbMasalahKeperawatanKeyReleased
        if(tabModeMasalah.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    tampilRencana2();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }//GEN-LAST:event_tbMasalahKeperawatanKeyReleased

    private void RencanaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RencanaKeyPressed
        Valid.pindah2(evt,TCariMasalah,BtnSimpan);
    }//GEN-LAST:event_RencanaKeyPressed

    private void BtnTambahMasalahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahMasalahActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        MasterMasalahKeperawatanNeonatus form=new MasterMasalahKeperawatanNeonatus(null,false);
        form.isCek();
        form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        form.setLocationRelativeTo(internalFrame1);
        form.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnTambahMasalahActionPerformed

    private void BtnAllMasalahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllMasalahActionPerformed
        TCari.setText("");
        tampilMasalah();
    }//GEN-LAST:event_BtnAllMasalahActionPerformed

    private void BtnAllMasalahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllMasalahKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllMasalahActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnCariMasalah, TCariMasalah);
        }
    }//GEN-LAST:event_BtnAllMasalahKeyPressed

    private void BtnCariMasalahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariMasalahActionPerformed
        tampilMasalah2();
    }//GEN-LAST:event_BtnCariMasalahActionPerformed

    private void BtnCariMasalahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariMasalahKeyPressed
        /*if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            tampilMasalah2();
        }else if((evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN)||(evt.getKeyCode()==KeyEvent.VK_TAB)){
            Rencana.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            KeteranganDiketahuiDietisen.requestFocus();
        }*/
    }//GEN-LAST:event_BtnCariMasalahKeyPressed

    private void TCariMasalahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariMasalahKeyPressed
        /*if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            tampilMasalah2();
        }else if((evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN)||(evt.getKeyCode()==KeyEvent.VK_TAB)){
            Rencana.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            KeteranganDiketahuiDietisen.requestFocus();
        }*/
    }//GEN-LAST:event_TCariMasalahKeyPressed

    private void BtnTambahRencanaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahRencanaActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        MasterRencanaKeperawatanNeonatus form=new MasterRencanaKeperawatanNeonatus(null,false);
        form.isCek();
        form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        form.setLocationRelativeTo(internalFrame1);
        form.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnTambahRencanaActionPerformed

    private void BtnAllRencanaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllRencanaActionPerformed
        TCariRencana.setText("");
        tampilRencana();
        tampilRencana2();
    }//GEN-LAST:event_BtnAllRencanaActionPerformed

    private void BtnAllRencanaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllRencanaKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllRencanaActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnCariRencana, TCariRencana);
        }
    }//GEN-LAST:event_BtnAllRencanaKeyPressed

    private void BtnCariRencanaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariRencanaActionPerformed
        tampilRencana2();
    }//GEN-LAST:event_BtnCariRencanaActionPerformed

    private void BtnCariRencanaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariRencanaKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            tampilRencana2();
        }else if((evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN)||(evt.getKeyCode()==KeyEvent.VK_TAB)){
            BtnSimpan.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            TCariRencana.requestFocus();
        }
    }//GEN-LAST:event_BtnCariRencanaKeyPressed

    private void TCariRencanaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariRencanaKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            tampilRencana2();
        }else if((evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN)||(evt.getKeyCode()==KeyEvent.VK_TAB)){
            BtnCariRencana.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            TCariMasalah.requestFocus();
        }
    }//GEN-LAST:event_TCariRencanaKeyPressed

    private void HubunganDenganPasienKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HubunganDenganPasienKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_HubunganDenganPasienKeyPressed

    private void KeluhanUtamaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeluhanUtamaKeyPressed
        //Valid.pindah2(evt,CaraMasuk,RPK);
    }//GEN-LAST:event_KeluhanUtamaKeyPressed

    private void PrenatalGKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PrenatalGKeyPressed
        //Valid.pindah(evt,StatusKawin3,P);
    }//GEN-LAST:event_PrenatalGKeyPressed

    private void PrenatalPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PrenatalPKeyPressed
        //Valid.pindah(evt,G,A);
    }//GEN-LAST:event_PrenatalPKeyPressed

    private void PrenatalAKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PrenatalAKeyPressed
        //Valid.pindah(evt,P,Hidup);
    }//GEN-LAST:event_PrenatalAKeyPressed

    private void PrenatalUKKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PrenatalUKKeyPressed
        //Valid.pindah(evt,A,HPHT);
    }//GEN-LAST:event_PrenatalUKKeyPressed

    private void RiwayatPenyakitIbuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RiwayatPenyakitIbuKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_RiwayatPenyakitIbuKeyPressed

    private void KeteranganRiwayatPenyakitIbuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganRiwayatPenyakitIbuKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganRiwayatPenyakitIbuKeyPressed

    private void KetAnamnesis3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetAnamnesis3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KetAnamnesis3KeyPressed

    private void RiwayatPengobatanIbuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RiwayatPengobatanIbuKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_RiwayatPengobatanIbuKeyPressed

    private void PernahDirawatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PernahDirawatKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PernahDirawatKeyPressed

    private void KeteranganPernahDirawatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganPernahDirawatKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganPernahDirawatKeyPressed

    private void StatusGiziIbuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_StatusGiziIbuKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_StatusGiziIbuKeyPressed

    private void IntranatalGKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_IntranatalGKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_IntranatalGKeyPressed

    private void IntranatalPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_IntranatalPKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_IntranatalPKeyPressed

    private void IntranatalAKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_IntranatalAKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_IntranatalAKeyPressed

    private void KondisiSaatLahirKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KondisiSaatLahirKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KondisiSaatLahirKeyPressed

    private void KeteranganCaraPersalinanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganCaraPersalinanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganCaraPersalinanKeyPressed

    private void CaraPersalinanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CaraPersalinanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_CaraPersalinanKeyPressed

    private void ApgarScoreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ApgarScoreKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_ApgarScoreKeyPressed

    private void IntranatalLetakKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_IntranatalLetakKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_IntranatalLetakKeyPressed

    private void KetubanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetubanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KetubanKeyPressed

    private void TaliPusatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TaliPusatKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TaliPusatKeyPressed

    private void AntoBBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AntoBBKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_AntoBBKeyPressed

    private void AntoPBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AntoPBKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_AntoPBKeyPressed

    private void AntoLKKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AntoLKKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_AntoLKKeyPressed

    private void AntoLDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AntoLDKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_AntoLDKeyPressed

    private void AntoLPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AntoLPKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_AntoLPKeyPressed

    private void RisikoInfeksiMayorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RisikoInfeksiMayorKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_RisikoInfeksiMayorKeyPressed

    private void KeteranganRisikoInfeksiMayorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganRisikoInfeksiMayorKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganRisikoInfeksiMayorKeyPressed

    private void RisikoInfeksiMinorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RisikoInfeksiMinorKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_RisikoInfeksiMinorKeyPressed

    private void KeteranganRisikoInfeksiMinorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganRisikoInfeksiMinorKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganRisikoInfeksiMinorKeyPressed

    private void NutrisiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NutrisiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NutrisiKeyPressed

    private void KeteranganNutrisiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganNutrisiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganNutrisiKeyPressed

    private void NutrisiFrekuensiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NutrisiFrekuensiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NutrisiFrekuensiKeyPressed

    private void NutrisiKaliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NutrisiKaliKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NutrisiKaliKeyPressed

    private void EliminasiBAKKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EliminasiBAKKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_EliminasiBAKKeyPressed

    private void KeteranganEliminasiBAKKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganEliminasiBAKKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganEliminasiBAKKeyPressed

    private void EliminasiBABKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EliminasiBABKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_EliminasiBABKeyPressed

    private void KeteranganEliminasiBABKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganEliminasiBABKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganEliminasiBABKeyPressed

    private void AlergiObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlergiObatKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_AlergiObatKeyPressed

    private void KeteranganAlergiObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganAlergiObatKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganAlergiObatKeyPressed

    private void ReaksiAlergiObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ReaksiAlergiObatKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_ReaksiAlergiObatKeyPressed

    private void AlergiMakananKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlergiMakananKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_AlergiMakananKeyPressed

    private void KeteranganAlergiMakananKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganAlergiMakananKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganAlergiMakananKeyPressed

    private void ReaksiAlergiMakananKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ReaksiAlergiMakananKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_ReaksiAlergiMakananKeyPressed

    private void AlergiLainnyaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlergiLainnyaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_AlergiLainnyaKeyPressed

    private void KeteranganAlergiLainnyaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganAlergiLainnyaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganAlergiLainnyaKeyPressed

    private void ReaksiAlergiLainnyaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ReaksiAlergiLainnyaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_ReaksiAlergiLainnyaKeyPressed

    private void RiwayatPenyakitKeluargaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RiwayatPenyakitKeluargaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_RiwayatPenyakitKeluargaKeyPressed

    private void KeteranganRiwayatPenyakitKeluargaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganRiwayatPenyakitKeluargaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganRiwayatPenyakitKeluargaKeyPressed

    private void RiwayatImunisasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RiwayatImunisasiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_RiwayatImunisasiKeyPressed

    private void KeteranganRiwayatImunisasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganRiwayatImunisasiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganRiwayatImunisasiKeyPressed

    private void TranfusiDarahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TranfusiDarahKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TranfusiDarahKeyPressed

    private void KeteranganTranfusiDarahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganTranfusiDarahKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganTranfusiDarahKeyPressed

    private void ReaksiTranfusiDarahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ReaksiTranfusiDarahKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_ReaksiTranfusiDarahKeyPressed

    private void KeteranganReaksiTranfusiDarahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganReaksiTranfusiDarahKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganReaksiTranfusiDarahKeyPressed

    private void ObatobatanDiminumKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ObatobatanDiminumKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_ObatobatanDiminumKeyPressed

    private void KeteranganObatobatanDiminumKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganObatobatanDiminumKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganObatobatanDiminumKeyPressed

    private void MerokokKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MerokokKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_MerokokKeyPressed

    private void JumlahMerokokKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JumlahMerokokKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_JumlahMerokokKeyPressed

    private void ObatTidurNarkobaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ObatTidurNarkobaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_ObatTidurNarkobaKeyPressed

    private void KeteranganObatTidurNarkobaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganObatTidurNarkobaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganObatTidurNarkobaKeyPressed

    private void AlkoholKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlkoholKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_AlkoholKeyPressed

    private void JumlahAlkoholKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JumlahAlkoholKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_JumlahAlkoholKeyPressed

    private void KeadaanUmumKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeadaanUmumKeyPressed
        //Valid.pindah(evt,KesadaranMental,GCS);
    }//GEN-LAST:event_KeadaanUmumKeyPressed

    private void FisikGCSKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FisikGCSKeyPressed
        //Valid.pindah(evt,KeadaanMentalUmum,TD);
    }//GEN-LAST:event_FisikGCSKeyPressed

    private void FisikTDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FisikTDKeyPressed
        //Valid.pindah(evt,GCS,Nadi);
    }//GEN-LAST:event_FisikTDKeyPressed

    private void ComposMentisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ComposMentisKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_ComposMentisKeyPressed

    private void FisikSuhuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FisikSuhuKeyPressed
        //Valid.pindah(evt,RR,SpO2);
    }//GEN-LAST:event_FisikSuhuKeyPressed

    private void FisikHRKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FisikHRKeyPressed
        //Valid.pindah(evt,TD,RR);
    }//GEN-LAST:event_FisikHRKeyPressed

    private void FisikRRKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FisikRRKeyPressed
        //Valid.pindah(evt,Nadi,Suhu);
    }//GEN-LAST:event_FisikRRKeyPressed

    private void FisikSPOKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FisikSPOKeyPressed
        //Valid.pindah(evt,Suhu,BB);
    }//GEN-LAST:event_FisikSPOKeyPressed

    private void FisikBBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FisikBBKeyPressed
        //Valid.pindah(evt,SpO2,TB);
    }//GEN-LAST:event_FisikBBKeyPressed

    private void FisikTBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FisikTBKeyPressed
        //Valid.pindah(evt,BB,SistemSarafKepala);
    }//GEN-LAST:event_FisikTBKeyPressed

    private void FisikDownScoreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FisikDownScoreKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_FisikDownScoreKeyPressed

    private void FisikLKKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FisikLKKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_FisikLKKeyPressed

    private void FisikLDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FisikLDKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_FisikLDKeyPressed

    private void FisikLPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FisikLPKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_FisikLPKeyPressed

    private void GDBayiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GDBayiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_GDBayiKeyPressed

    private void GDIbuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GDIbuKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_GDIbuKeyPressed

    private void GDAyahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GDAyahKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_GDAyahKeyPressed

    private void GerakBayiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GerakBayiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_GerakBayiKeyPressed

    private void KepalaBayiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KepalaBayiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KepalaBayiKeyPressed

    private void KeteranganKepalaBayiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKepalaBayiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganKepalaBayiKeyPressed

    private void UbunubunKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_UbunubunKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_UbunubunKeyPressed

    private void KeteranganUbunubunKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganUbunubunKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganUbunubunKeyPressed

    private void WajahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_WajahKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_WajahKeyPressed

    private void KeteranganWajahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganWajahKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganWajahKeyPressed

    private void KejangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KejangKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KejangKeyPressed

    private void KeteranganKejangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKejangKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganKejangKeyPressed

    private void RefleksKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RefleksKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_RefleksKeyPressed

    private void KeteranganRefleksKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganRefleksKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganRefleksKeyPressed

    private void TangisBayiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TangisBayiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TangisBayiKeyPressed

    private void KeteranganTangisBayiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganTangisBayiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganTangisBayiKeyPressed

    private void DenyutNadiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DenyutNadiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DenyutNadiKeyPressed

    private void SirkulasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SirkulasiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SirkulasiKeyPressed

    private void KeteranganSirkulasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganSirkulasiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganSirkulasiKeyPressed

    private void PulsasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PulsasiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PulsasiKeyPressed

    private void KeteranganPulsasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganPulsasiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganPulsasiKeyPressed

    private void PolaNapasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PolaNapasKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PolaNapasKeyPressed

    private void JenisPernapasanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JenisPernapasanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_JenisPernapasanKeyPressed

    private void KeteranganJenisPernapasanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganJenisPernapasanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganJenisPernapasanKeyPressed

    private void RetraksiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RetraksiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_RetraksiKeyPressed

    private void AirEntryKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AirEntryKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_AirEntryKeyPressed

    private void MerintihKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MerintihKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_MerintihKeyPressed

    private void SuaraNapasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SuaraNapasKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SuaraNapasKeyPressed

    private void MulutKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MulutKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_MulutKeyPressed

    private void KeteranganMulutKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganMulutKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganMulutKeyPressed

    private void LidahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LidahKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_LidahKeyPressed

    private void KeteranganLidahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganLidahKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganLidahKeyPressed

    private void TenggorokanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TenggorokanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TenggorokanKeyPressed

    private void KeteranganTenggorokanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganTenggorokanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganTenggorokanKeyPressed

    private void AbdomenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AbdomenKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_AbdomenKeyPressed

    private void KeteranganAbdomenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganAbdomenKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganAbdomenKeyPressed

    private void GastroBABKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GastroBABKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_GastroBABKeyPressed

    private void KeteranganGastroBABKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganGastroBABKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganGastroBABKeyPressed

    private void GastroWarnaBABKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GastroWarnaBABKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_GastroWarnaBABKeyPressed

    private void KeteranganGastroWarnaBABKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganGastroWarnaBABKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganGastroWarnaBABKeyPressed

    private void GastroBAKKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GastroBAKKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_GastroBAKKeyPressed

    private void KeteranganGastroBAKKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganGastroBAKKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganGastroBAKKeyPressed

    private void GastroWarnaBAKKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GastroWarnaBAKKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_GastroWarnaBAKKeyPressed

    private void KeteranganGastroWarnaBAKKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganGastroWarnaBAKKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganGastroWarnaBAKKeyPressed

    private void PosisiMataKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PosisiMataKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PosisiMataKeyPressed

    private void KelopakMataKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KelopakMataKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KelopakMataKeyPressed

    private void KeteranganKelopakMataKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKelopakMataKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganKelopakMataKeyPressed

    private void BesarPupilKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BesarPupilKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BesarPupilKeyPressed

    private void KonjungtivaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KonjungtivaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KonjungtivaKeyPressed

    private void KeteranganKonjungtivaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKonjungtivaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganKonjungtivaKeyPressed

    private void SkleraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkleraKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SkleraKeyPressed

    private void KeteranganSkleraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganSkleraKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganSkleraKeyPressed

    private void PendengaranKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PendengaranKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PendengaranKeyPressed

    private void KeteranganPendengaranKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganPendengaranKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganPendengaranKeyPressed

    private void PenciumanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenciumanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PenciumanKeyPressed

    private void KeteranganPenciumanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganPenciumanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganPenciumanKeyPressed

    private void WarnaKulitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_WarnaKulitKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_WarnaKulitKeyPressed

    private void VernicKaseosaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_VernicKaseosaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_VernicKaseosaKeyPressed

    private void LanugoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LanugoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_LanugoKeyPressed

    private void KeteranganVernicKaseosaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganVernicKaseosaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganVernicKaseosaKeyPressed

    private void KeteranganWarnaKulitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganWarnaKulitKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganWarnaKulitKeyPressed

    private void TurgorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TurgorKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TurgorKeyPressed

    private void KulitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KulitKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KulitKeyPressed

    private void RisikoDekubitasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RisikoDekubitasKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_RisikoDekubitasKeyPressed

    private void ReproduksiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ReproduksiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_ReproduksiKeyPressed

    private void KeteranganReproduksiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganReproduksiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganReproduksiKeyPressed

    private void RekoilTelingaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RekoilTelingaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_RekoilTelingaKeyPressed

    private void LenganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LenganKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_LenganKeyPressed

    private void KeteranganLenganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganLenganKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganLenganKeyPressed

    private void KeteranganRekoilTelingaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganRekoilTelingaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganRekoilTelingaKeyPressed

    private void TungkaiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TungkaiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TungkaiKeyPressed

    private void GarisTelapakKakiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GarisTelapakKakiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_GarisTelapakKakiKeyPressed

    private void KeteranganTungkaiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganTungkaiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganTungkaiKeyPressed

    private void KondisiPsikologisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KondisiPsikologisKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KondisiPsikologisKeyPressed

    private void MenerimaKondisiBayiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MenerimaKondisiBayiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_MenerimaKondisiBayiKeyPressed

    private void GangguanJiwaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GangguanJiwaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_GangguanJiwaKeyPressed

    private void MasalahPernikahanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MasalahPernikahanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_MasalahPernikahanKeyPressed

    private void KeteranganMasalahPernikahanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganMasalahPernikahanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganMasalahPernikahanKeyPressed

    private void KeteranganTinggalBersamaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganTinggalBersamaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganTinggalBersamaKeyPressed

    private void AgamaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AgamaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_AgamaKeyPressed

    private void NilaiKepercayaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NilaiKepercayaanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NilaiKepercayaanKeyPressed

    private void KeteranganNilaiKepercayaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganNilaiKepercayaanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganNilaiKepercayaanKeyPressed

    private void SukuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SukuKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SukuKeyPressed

    private void PendidikanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PendidikanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PendidikanKeyPressed

    private void TinggalBersamaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TinggalBersamaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TinggalBersamaKeyPressed

    private void ResponEmosiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ResponEmosiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_ResponEmosiKeyPressed

    private void HubunganAnggotaKeluargaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HubunganAnggotaKeluargaKeyPressed
        //Valid.pindah(evt,GangguanJiwa,TinggalDengan);
    }//GEN-LAST:event_HubunganAnggotaKeluargaKeyPressed

    private void PekerjaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PekerjaanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PekerjaanKeyPressed

    private void PembayaranKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PembayaranKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PembayaranKeyPressed

    private void BahasaSehariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BahasaSehariKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BahasaSehariKeyPressed

    private void KemampuanBacaTulisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KemampuanBacaTulisKeyPressed
        //Valid.pindah(evt,KetDokter,DibutuhkanPenerjemah);
    }//GEN-LAST:event_KemampuanBacaTulisKeyPressed

    private void ButuhPenerjemahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ButuhPenerjemahKeyPressed
        //Valid.pindah(evt,KemampuanBacaTulis,KeteranganPenerjamahLainnya);
    }//GEN-LAST:event_ButuhPenerjemahKeyPressed

    private void KeteranganButuhPenerjemahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganButuhPenerjemahKeyPressed
        //Valid.pindah(evt,DibutuhkanPenerjemah,HambatanPembelajaran);
    }//GEN-LAST:event_KeteranganButuhPenerjemahKeyPressed

    private void TerdapatHambatanBelajarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TerdapatHambatanBelajarKeyPressed
        //Valid.pindah(evt,KeteranganPenerjamahLainnya,KategoriHambatanPembelajaran);
    }//GEN-LAST:event_TerdapatHambatanBelajarKeyPressed

    private void HambatanBelajarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HambatanBelajarKeyPressed
        //Valid.pindah(evt,HambatanPembelajaran,KeteranganHambatanPembelajaran);
    }//GEN-LAST:event_HambatanBelajarKeyPressed

    private void KeteranganHambatanBelajarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganHambatanBelajarKeyPressed
        //Valid.pindah(evt,KategoriHambatanPembelajaran,HambatanCaraBicara);
    }//GEN-LAST:event_KeteranganHambatanBelajarKeyPressed

    private void HambatanCaraBicaraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HambatanCaraBicaraKeyPressed
        //Valid.pindah(evt,KeteranganHambatanPembelajaran,HambatanBahasaIsyarat);
    }//GEN-LAST:event_HambatanCaraBicaraKeyPressed

    private void HambatanBahasaIsyaratKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HambatanBahasaIsyaratKeyPressed
        //Valid.pindah(evt,HambatanCaraBicara,CaraBelajarDisukai);
    }//GEN-LAST:event_HambatanBahasaIsyaratKeyPressed

    private void CaraBelajarDisukaiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CaraBelajarDisukaiKeyPressed
        //Valid.pindah(evt,HambatanBahasaIsyarat,KesediaanMenerimaInformasi);
    }//GEN-LAST:event_CaraBelajarDisukaiKeyPressed

    private void KesediaanMenerimaInformasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KesediaanMenerimaInformasiKeyPressed
        //Valid.pindah(evt,CaraBelajarDisukai,KeteranganKesediaanMenerimaInformasi);
    }//GEN-LAST:event_KesediaanMenerimaInformasiKeyPressed

    private void KeteranganKesediaanMenerimaInformasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKesediaanMenerimaInformasiKeyPressed
        //Valid.pindah(evt,KesediaanMenerimaInformasi,KehilanganBeratBadan);
    }//GEN-LAST:event_KeteranganKesediaanMenerimaInformasiKeyPressed

    private void PemahamanNutrisiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PemahamanNutrisiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PemahamanNutrisiKeyPressed

    private void PemahamanPenyakitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PemahamanPenyakitKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PemahamanPenyakitKeyPressed

    private void PemahamanPerawatanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PemahamanPerawatanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PemahamanPerawatanKeyPressed

    private void PemahamanPengobatanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PemahamanPengobatanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PemahamanPengobatanKeyPressed

    private void SG1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SG1ItemStateChanged
        //NilaiGizi1.setText(SG1.getSelectedIndex()+"");
        //TotalNilaiGizi.setText(""+(Integer.parseInt(NilaiGizi1.getText())+Integer.parseInt(NilaiGizi2.getText())+Integer.parseInt(NilaiGizi3.getText())+Integer.parseInt(NilaiGizi4.getText())));
    }//GEN-LAST:event_SG1ItemStateChanged

    private void SG1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SG1KeyPressed
        //Valid.pindah(evt,KetLapor,SG2);
    }//GEN-LAST:event_SG1KeyPressed

    private void NilaiGizi1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NilaiGizi1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NilaiGizi1KeyPressed

    private void SG2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SG2ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_SG2ItemStateChanged

    private void SG2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SG2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SG2KeyPressed

    private void NilaiGizi2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NilaiGizi2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NilaiGizi2KeyPressed

    private void SG3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SG3ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_SG3ItemStateChanged

    private void SG3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SG3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SG3KeyPressed

    private void NilaiGizi3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NilaiGizi3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NilaiGizi3KeyPressed

    private void TotalNilaiGiziKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TotalNilaiGiziKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TotalNilaiGiziKeyPressed

    private void KeteranganSkriningGiziKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganSkriningGiziKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganSkriningGiziKeyPressed

    private void SkalaResiko1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SkalaResiko1ItemStateChanged
        /*if(SkalaResiko1.getSelectedIndex()==0){
            NilaiResiko1.setText("4");
        }else if(SkalaResiko1.getSelectedIndex()==1){
            NilaiResiko1.setText("3");
        }else if(SkalaResiko1.getSelectedIndex()==2){
            NilaiResiko1.setText("2");
        }else{
            NilaiResiko1.setText("1");
        }
        isTotalResikoJatuh();*/
    }//GEN-LAST:event_SkalaResiko1ItemStateChanged

    private void SkalaResiko1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkalaResiko1KeyPressed
        //Valid.pindah(evt,btnPetugas,SkalaResiko2);
    }//GEN-LAST:event_SkalaResiko1KeyPressed

    private void SkalaResiko2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SkalaResiko2ItemStateChanged
        /*if(SkalaResiko2.getSelectedIndex()==0){
            NilaiResiko2.setText("2");
        }else{
            NilaiResiko2.setText("1");
        }
        isTotalResikoJatuh();*/
    }//GEN-LAST:event_SkalaResiko2ItemStateChanged

    private void SkalaResiko2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkalaResiko2KeyPressed
        //Valid.pindah(evt,SkalaResiko1,SkalaResiko3);
    }//GEN-LAST:event_SkalaResiko2KeyPressed

    private void SkalaResiko3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SkalaResiko3ItemStateChanged
        /*if(SkalaResiko3.getSelectedIndex()==0){
            NilaiResiko3.setText("4");
        }else if(SkalaResiko3.getSelectedIndex()==1){
            NilaiResiko3.setText("3");
        }else if(SkalaResiko3.getSelectedIndex()==2){
            NilaiResiko3.setText("2");
        }else{
            NilaiResiko3.setText("1");
        }
        isTotalResikoJatuh();*/
    }//GEN-LAST:event_SkalaResiko3ItemStateChanged

    private void SkalaResiko3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkalaResiko3KeyPressed
        //Valid.pindah(evt,SkalaResiko2,SkalaResiko4);
    }//GEN-LAST:event_SkalaResiko3KeyPressed

    private void SkalaResiko4ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SkalaResiko4ItemStateChanged
        /*if(SkalaResiko4.getSelectedIndex()==0){
            NilaiResiko4.setText("3");
        }else if(SkalaResiko4.getSelectedIndex()==1){
            NilaiResiko4.setText("2");
        }else{
            NilaiResiko4.setText("1");
        }
        isTotalResikoJatuh();*/
    }//GEN-LAST:event_SkalaResiko4ItemStateChanged

    private void SkalaResiko4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkalaResiko4KeyPressed
        //Valid.pindah(evt,SkalaResiko3,SkalaResiko5);
    }//GEN-LAST:event_SkalaResiko4KeyPressed

    private void SkalaResiko5ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SkalaResiko5ItemStateChanged
        /*if(SkalaResiko5.getSelectedIndex()==0){
            NilaiResiko5.setText("4");
        }else if(SkalaResiko5.getSelectedIndex()==1){
            NilaiResiko5.setText("3");
        }else if(SkalaResiko5.getSelectedIndex()==2){
            NilaiResiko5.setText("2");
        }else{
            NilaiResiko5.setText("1");
        }
        isTotalResikoJatuh();*/
    }//GEN-LAST:event_SkalaResiko5ItemStateChanged

    private void SkalaResiko5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkalaResiko5KeyPressed
        //Valid.pindah(evt,SkalaResiko4,SkalaResiko6);
    }//GEN-LAST:event_SkalaResiko5KeyPressed

    private void SkalaResiko6ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SkalaResiko6ItemStateChanged
        /*if(SkalaResiko6.getSelectedIndex()==0){
            NilaiResiko6.setText("3");
        }else if(SkalaResiko6.getSelectedIndex()==1){
            NilaiResiko6.setText("2");
        }else{
            NilaiResiko6.setText("1");
        }
        isTotalResikoJatuh();*/
    }//GEN-LAST:event_SkalaResiko6ItemStateChanged

    private void SkalaResiko6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkalaResiko6KeyPressed
        Valid.pindah(evt,SkalaResiko5,SkalaResiko7);
    }//GEN-LAST:event_SkalaResiko6KeyPressed

    private void SkalaResiko7ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SkalaResiko7ItemStateChanged
        /*if(SkalaResiko7.getSelectedIndex()==0){
            NilaiResiko7.setText("3");
        }else if(SkalaResiko7.getSelectedIndex()==1){
            NilaiResiko7.setText("2");
        }else{
            NilaiResiko7.setText("1");
        }
        isTotalResikoJatuh();*/
    }//GEN-LAST:event_SkalaResiko7ItemStateChanged

    private void SkalaResiko7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkalaResiko7KeyPressed
        //Valid.pindah(evt,SkalaResiko6,HasilSkrining);
    }//GEN-LAST:event_SkalaResiko7KeyPressed

    private void SkalaNIPS1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SkalaNIPS1ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_SkalaNIPS1ItemStateChanged

    private void SkalaNIPS1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkalaNIPS1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SkalaNIPS1KeyPressed

    private void SkalaNIPS2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SkalaNIPS2ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_SkalaNIPS2ItemStateChanged

    private void SkalaNIPS2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkalaNIPS2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SkalaNIPS2KeyPressed

    private void SkalaNIPS3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SkalaNIPS3ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_SkalaNIPS3ItemStateChanged

    private void SkalaNIPS3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkalaNIPS3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SkalaNIPS3KeyPressed

    private void SkalaNIPS4ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SkalaNIPS4ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_SkalaNIPS4ItemStateChanged

    private void SkalaNIPS4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkalaNIPS4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SkalaNIPS4KeyPressed

    private void SkalaNIPS5ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SkalaNIPS5ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_SkalaNIPS5ItemStateChanged

    private void SkalaNIPS5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkalaNIPS5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SkalaNIPS5KeyPressed

    private void InformasiPerencanaanPulangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_InformasiPerencanaanPulangKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_InformasiPerencanaanPulangKeyPressed

    private void KondisiPulangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KondisiPulangKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KondisiPulangKeyPressed

    private void TanggalPulangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalPulangKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TanggalPulangKeyPressed

    private void LamaRatarataKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LamaRatarataKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_LamaRatarataKeyPressed

    private void PerawatanLanjutanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PerawatanLanjutanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PerawatanLanjutanKeyPressed

    private void CaraTransportasiPulangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CaraTransportasiPulangKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_CaraTransportasiPulangKeyPressed

    private void TransportasiYangDigunakanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TransportasiYangDigunakanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TransportasiYangDigunakanKeyPressed

    private void StatusMenikahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_StatusMenikahKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_StatusMenikahKeyPressed

    private void KeteranganTingkatRisikoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganTingkatRisikoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganTingkatRisikoKeyPressed

    private void KeteranganPenilaianNyeriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganPenilaianNyeriKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganPenilaianNyeriKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMPenilaianAwalKeperawatanRanapNeonatus dialog = new RMPenilaianAwalKeperawatanRanapNeonatus(new javax.swing.JFrame(), true);
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
    private widget.TextBox Agama;
    private widget.ComboBox AirEntry;
    private widget.ComboBox AlergiLainnya;
    private widget.ComboBox AlergiMakanan;
    private widget.ComboBox AlergiObat;
    private widget.ComboBox Alkohol;
    private widget.TextBox AntoBB;
    private widget.TextBox AntoLD;
    private widget.TextBox AntoLK;
    private widget.TextBox AntoLP;
    private widget.TextBox AntoPB;
    private widget.TextBox ApgarScore;
    private widget.ComboBox AsalPasien;
    private widget.TextBox BahasaSehari;
    private widget.ComboBox BesarPupil;
    private widget.Button BtnAll;
    private widget.Button BtnAllMasalah;
    private widget.Button BtnAllRencana;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnCariMasalah;
    private widget.Button BtnCariRencana;
    private widget.Button BtnDPJP;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPetugas;
    private widget.Button BtnPetugas2;
    private widget.Button BtnPrint;
    private widget.Button BtnPrint1;
    private widget.Button BtnSimpan;
    private widget.Button BtnTambahMasalah;
    private widget.Button BtnTambahRencana;
    private widget.ComboBox ButuhPenerjemah;
    private widget.ComboBox CaraBelajarDisukai;
    private widget.ComboBox CaraMasuk;
    private widget.ComboBox CaraPersalinan;
    private widget.ComboBox CaraTransportasiPulang;
    private widget.CekBox ChkAccor;
    private widget.ComboBox ComposMentis;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.ComboBox DenyutNadi;
    private widget.TextArea DetailRencana;
    private widget.TextBox DiperolehDari;
    private widget.ComboBox EliminasiBAB;
    private widget.ComboBox EliminasiBAK;
    private widget.TextBox FisikBB;
    private widget.TextBox FisikDownScore;
    private widget.TextBox FisikGCS;
    private widget.TextBox FisikHR;
    private widget.TextBox FisikLD;
    private widget.TextBox FisikLK;
    private widget.TextBox FisikLP;
    private widget.TextBox FisikRR;
    private widget.TextBox FisikSPO;
    private widget.TextBox FisikSuhu;
    private widget.TextBox FisikTB;
    private widget.TextBox FisikTD;
    private widget.PanelBiasa FormInput;
    private widget.PanelBiasa FormMasalahRencana;
    private widget.PanelBiasa FormMenu;
    private widget.ComboBox GDAyah;
    private widget.ComboBox GDBayi;
    private widget.ComboBox GDIbu;
    private widget.ComboBox GangguanJiwa;
    private widget.ComboBox GarisTelapakKaki;
    private widget.ComboBox GastroBAB;
    private widget.ComboBox GastroBAK;
    private widget.ComboBox GastroWarnaBAB;
    private widget.ComboBox GastroWarnaBAK;
    private widget.ComboBox GerakBayi;
    private widget.ComboBox HambatanBahasaIsyarat;
    private widget.ComboBox HambatanBelajar;
    private widget.ComboBox HambatanCaraBicara;
    private widget.ComboBox HubunganAnggotaKeluarga;
    private widget.TextBox HubunganDenganPasien;
    private widget.ComboBox InformasiPerencanaanPulang;
    private widget.TextBox IntranatalA;
    private widget.TextBox IntranatalG;
    private widget.TextBox IntranatalLetak;
    private widget.TextBox IntranatalP;
    private widget.ComboBox JenisPernapasan;
    private widget.TextBox Jk;
    private widget.TextBox JumlahAlkohol;
    private widget.TextBox JumlahMerokok;
    private widget.TextBox KdDPJP;
    private widget.TextBox KdPetugas;
    private widget.TextBox KdPetugas2;
    private widget.ComboBox KeadaanUmum;
    private widget.ComboBox Kejang;
    private widget.ComboBox KelopakMata;
    private widget.TextArea KeluhanUtama;
    private widget.ComboBox KemampuanBacaTulis;
    private widget.ComboBox KepalaBayi;
    private widget.ComboBox KesediaanMenerimaInformasi;
    private widget.TextBox KetAnamnesis3;
    private widget.TextBox KeteranganAbdomen;
    private widget.TextBox KeteranganAlergiLainnya;
    private widget.TextBox KeteranganAlergiMakanan;
    private widget.TextBox KeteranganAlergiObat;
    private widget.TextBox KeteranganButuhPenerjemah;
    private widget.TextBox KeteranganCaraPersalinan;
    private widget.TextBox KeteranganEliminasiBAB;
    private widget.TextBox KeteranganEliminasiBAK;
    private widget.TextBox KeteranganGastroBAB;
    private widget.TextBox KeteranganGastroBAK;
    private widget.TextBox KeteranganGastroWarnaBAB;
    private widget.TextBox KeteranganGastroWarnaBAK;
    private widget.TextBox KeteranganHambatanBelajar;
    private widget.TextBox KeteranganJenisPernapasan;
    private widget.TextBox KeteranganKejang;
    private widget.TextBox KeteranganKelopakMata;
    private widget.TextBox KeteranganKepalaBayi;
    private widget.TextBox KeteranganKesediaanMenerimaInformasi;
    private widget.TextBox KeteranganKonjungtiva;
    private widget.TextBox KeteranganLengan;
    private widget.TextBox KeteranganLidah;
    private widget.TextBox KeteranganMasalahPernikahan;
    private widget.TextBox KeteranganMulut;
    private widget.TextBox KeteranganNilaiKepercayaan;
    private widget.TextBox KeteranganNutrisi;
    private widget.TextBox KeteranganObatTidurNarkoba;
    private widget.TextBox KeteranganObatobatanDiminum;
    private widget.TextBox KeteranganPenciuman;
    private widget.TextBox KeteranganPendengaran;
    private widget.TextBox KeteranganPenilaianNyeri;
    private widget.TextBox KeteranganPernahDirawat;
    private widget.TextBox KeteranganPulsasi;
    private widget.TextBox KeteranganReaksiTranfusiDarah;
    private widget.TextBox KeteranganRefleks;
    private widget.TextBox KeteranganRekoilTelinga;
    private widget.TextBox KeteranganReproduksi;
    private widget.TextBox KeteranganRisikoInfeksiMayor;
    private widget.TextBox KeteranganRisikoInfeksiMinor;
    private widget.TextBox KeteranganRiwayatImunisasi;
    private widget.TextBox KeteranganRiwayatPenyakitIbu;
    private widget.TextBox KeteranganRiwayatPenyakitKeluarga;
    private widget.TextBox KeteranganSirkulasi;
    private widget.TextBox KeteranganSklera;
    private widget.TextBox KeteranganSkriningGizi;
    private widget.TextBox KeteranganTangisBayi;
    private widget.TextBox KeteranganTenggorokan;
    private widget.TextBox KeteranganTinggalBersama;
    private widget.TextBox KeteranganTingkatRisiko;
    private widget.TextBox KeteranganTranfusiDarah;
    private widget.TextBox KeteranganTungkai;
    private widget.TextBox KeteranganUbunubun;
    private widget.TextBox KeteranganVernicKaseosa;
    private widget.TextBox KeteranganWajah;
    private widget.TextBox KeteranganWarnaKulit;
    private widget.ComboBox Ketuban;
    private widget.ComboBox KondisiPsikologis;
    private widget.TextBox KondisiPulang;
    private widget.TextBox KondisiSaatLahir;
    private widget.ComboBox Konjungtiva;
    private widget.ComboBox Kulit;
    private widget.Label LCount;
    private widget.TextBox LamaRatarata;
    private widget.ComboBox Lanugo;
    private widget.ComboBox Lengan;
    private widget.ComboBox Lidah;
    private widget.ComboBox MasalahPernikahan;
    private widget.ComboBox MenerimaKondisiBayi;
    private widget.ComboBox Merintih;
    private widget.ComboBox Merokok;
    private widget.ComboBox Mulut;
    private widget.TextBox NilaiGizi1;
    private widget.TextBox NilaiGizi2;
    private widget.TextBox NilaiGizi3;
    private widget.ComboBox NilaiKepercayaan;
    private widget.TextBox NilaiNIPS1;
    private widget.TextBox NilaiNIPS2;
    private widget.TextBox NilaiNIPS3;
    private widget.TextBox NilaiNIPS4;
    private widget.TextBox NilaiNIPS5;
    private widget.TextBox NilaiResiko1;
    private widget.TextBox NilaiResiko2;
    private widget.TextBox NilaiResiko3;
    private widget.TextBox NilaiResiko4;
    private widget.TextBox NilaiResiko5;
    private widget.TextBox NilaiResiko6;
    private widget.TextBox NilaiResiko7;
    private widget.TextBox NilaiResikoTotal;
    private widget.TextBox NmDPJP;
    private widget.TextBox NmPetugas;
    private widget.TextBox NmPetugas2;
    private widget.ComboBox Nutrisi;
    private widget.TextBox NutrisiFrekuensi;
    private widget.TextBox NutrisiKali;
    private widget.ComboBox ObatTidurNarkoba;
    private widget.ComboBox ObatobatanDiminum;
    private widget.PanelBiasa PanelAccor;
    private widget.TextBox Pekerjaan;
    private widget.ComboBox PemahamanNutrisi;
    private widget.ComboBox PemahamanPengobatan;
    private widget.ComboBox PemahamanPenyakit;
    private widget.ComboBox PemahamanPerawatan;
    private widget.TextBox Pembayaran;
    private widget.ComboBox Penciuman;
    private widget.ComboBox Pendengaran;
    private widget.TextBox Pendidikan;
    private widget.TextArea PerawatanLanjutan;
    private widget.ComboBox PernahDirawat;
    private widget.ComboBox PolaNapas;
    private widget.ComboBox PosisiMata;
    private widget.TextBox PrenatalA;
    private widget.TextBox PrenatalG;
    private widget.TextBox PrenatalP;
    private widget.TextBox PrenatalUK;
    private widget.ComboBox Pulsasi;
    private widget.TextBox ReaksiAlergiLainnya;
    private widget.TextBox ReaksiAlergiMakanan;
    private widget.TextBox ReaksiAlergiObat;
    private widget.ComboBox ReaksiTranfusiDarah;
    private widget.ComboBox Refleks;
    private widget.ComboBox RekoilTelinga;
    private widget.TextArea Rencana;
    private widget.ComboBox Reproduksi;
    private widget.ComboBox ResponEmosi;
    private widget.ComboBox Retraksi;
    private widget.ComboBox RisikoDekubitas;
    private widget.ComboBox RisikoInfeksiMayor;
    private widget.ComboBox RisikoInfeksiMinor;
    private widget.ComboBox RiwayatImunisasi;
    private widget.TextBox RiwayatPengobatanIbu;
    private widget.ComboBox RiwayatPenyakitIbu;
    private widget.ComboBox RiwayatPenyakitKeluarga;
    private widget.ComboBox SG1;
    private widget.ComboBox SG2;
    private widget.ComboBox SG3;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll6;
    private widget.ScrollPane Scroll7;
    private widget.ScrollPane Scroll8;
    private widget.ScrollPane Scroll9;
    private widget.ComboBox Sirkulasi;
    private widget.ComboBox SkalaNIPS1;
    private widget.ComboBox SkalaNIPS2;
    private widget.ComboBox SkalaNIPS3;
    private widget.ComboBox SkalaNIPS4;
    private widget.ComboBox SkalaNIPS5;
    private widget.ComboBox SkalaResiko1;
    private widget.ComboBox SkalaResiko2;
    private widget.ComboBox SkalaResiko3;
    private widget.ComboBox SkalaResiko4;
    private widget.ComboBox SkalaResiko5;
    private widget.ComboBox SkalaResiko6;
    private widget.ComboBox SkalaResiko7;
    private widget.ComboBox Sklera;
    private widget.ComboBox StatusGiziIbu;
    private widget.ComboBox StatusMenikah;
    private widget.ComboBox SuaraNapas;
    private widget.TextBox Suku;
    private widget.TextBox TCari;
    private widget.TextBox TCariMasalah;
    private widget.TextBox TCariRencana;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRM1;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.TextBox TPasien1;
    private javax.swing.JTabbedPane TabRawat;
    private javax.swing.JTabbedPane TabRencanaKeperawatan;
    private widget.ComboBox TaliPusat;
    private widget.Tanggal TanggalPulang;
    private widget.ComboBox TangisBayi;
    private widget.ComboBox Tenggorokan;
    private widget.ComboBox TerdapatHambatanBelajar;
    private widget.Tanggal TglAsuhan;
    private widget.TextBox TglLahir;
    private widget.ComboBox TinggalBersama;
    private widget.Label TingkatResiko1;
    private widget.Label TingkatResiko2;
    private widget.TextBox TotalNIPS;
    private widget.TextBox TotalNilaiGizi;
    private widget.ComboBox TranfusiDarah;
    private widget.ComboBox TransportasiYangDigunakan;
    private widget.ComboBox Tungkai;
    private widget.ComboBox Turgor;
    private widget.ComboBox Ubunubun;
    private widget.ComboBox VernicKaseosa;
    private widget.ComboBox Wajah;
    private widget.ComboBox WarnaKulit;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
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
    private widget.Label jLabel121;
    private widget.Label jLabel122;
    private widget.Label jLabel123;
    private widget.Label jLabel124;
    private widget.Label jLabel125;
    private widget.Label jLabel126;
    private widget.Label jLabel127;
    private widget.Label jLabel128;
    private widget.Label jLabel129;
    private widget.Label jLabel13;
    private widget.Label jLabel130;
    private widget.Label jLabel131;
    private widget.Label jLabel132;
    private widget.Label jLabel133;
    private widget.Label jLabel134;
    private widget.Label jLabel135;
    private widget.Label jLabel136;
    private widget.Label jLabel137;
    private widget.Label jLabel138;
    private widget.Label jLabel139;
    private widget.Label jLabel140;
    private widget.Label jLabel141;
    private widget.Label jLabel142;
    private widget.Label jLabel143;
    private widget.Label jLabel144;
    private widget.Label jLabel145;
    private widget.Label jLabel146;
    private widget.Label jLabel147;
    private widget.Label jLabel148;
    private widget.Label jLabel149;
    private widget.Label jLabel15;
    private widget.Label jLabel150;
    private widget.Label jLabel151;
    private widget.Label jLabel152;
    private widget.Label jLabel153;
    private widget.Label jLabel154;
    private widget.Label jLabel155;
    private widget.Label jLabel156;
    private widget.Label jLabel157;
    private widget.Label jLabel158;
    private widget.Label jLabel159;
    private widget.Label jLabel16;
    private widget.Label jLabel160;
    private widget.Label jLabel161;
    private widget.Label jLabel162;
    private widget.Label jLabel163;
    private widget.Label jLabel164;
    private widget.Label jLabel165;
    private widget.Label jLabel166;
    private widget.Label jLabel167;
    private widget.Label jLabel168;
    private widget.Label jLabel169;
    private widget.Label jLabel17;
    private widget.Label jLabel170;
    private widget.Label jLabel171;
    private widget.Label jLabel172;
    private widget.Label jLabel173;
    private widget.Label jLabel174;
    private widget.Label jLabel175;
    private widget.Label jLabel176;
    private widget.Label jLabel177;
    private widget.Label jLabel178;
    private widget.Label jLabel179;
    private widget.Label jLabel18;
    private widget.Label jLabel180;
    private widget.Label jLabel181;
    private widget.Label jLabel182;
    private widget.Label jLabel183;
    private widget.Label jLabel184;
    private widget.Label jLabel185;
    private widget.Label jLabel186;
    private widget.Label jLabel187;
    private widget.Label jLabel188;
    private widget.Label jLabel189;
    private widget.Label jLabel19;
    private widget.Label jLabel190;
    private widget.Label jLabel191;
    private widget.Label jLabel192;
    private widget.Label jLabel193;
    private widget.Label jLabel194;
    private widget.Label jLabel195;
    private widget.Label jLabel196;
    private widget.Label jLabel197;
    private widget.Label jLabel198;
    private widget.Label jLabel199;
    private widget.Label jLabel20;
    private widget.Label jLabel200;
    private widget.Label jLabel201;
    private widget.Label jLabel202;
    private widget.Label jLabel203;
    private widget.Label jLabel204;
    private widget.Label jLabel205;
    private widget.Label jLabel206;
    private widget.Label jLabel207;
    private widget.Label jLabel208;
    private widget.Label jLabel209;
    private widget.Label jLabel21;
    private widget.Label jLabel210;
    private widget.Label jLabel211;
    private widget.Label jLabel212;
    private widget.Label jLabel213;
    private widget.Label jLabel214;
    private widget.Label jLabel215;
    private widget.Label jLabel216;
    private widget.Label jLabel217;
    private widget.Label jLabel218;
    private widget.Label jLabel219;
    private widget.Label jLabel22;
    private widget.Label jLabel220;
    private widget.Label jLabel221;
    private widget.Label jLabel222;
    private widget.Label jLabel223;
    private widget.Label jLabel224;
    private widget.Label jLabel225;
    private widget.Label jLabel226;
    private widget.Label jLabel227;
    private widget.Label jLabel228;
    private widget.Label jLabel229;
    private widget.Label jLabel23;
    private widget.Label jLabel230;
    private widget.Label jLabel231;
    private widget.Label jLabel232;
    private widget.Label jLabel233;
    private widget.Label jLabel234;
    private widget.Label jLabel235;
    private widget.Label jLabel236;
    private widget.Label jLabel237;
    private widget.Label jLabel238;
    private widget.Label jLabel239;
    private widget.Label jLabel24;
    private widget.Label jLabel240;
    private widget.Label jLabel241;
    private widget.Label jLabel242;
    private widget.Label jLabel243;
    private widget.Label jLabel244;
    private widget.Label jLabel245;
    private widget.Label jLabel246;
    private widget.Label jLabel247;
    private widget.Label jLabel248;
    private widget.Label jLabel249;
    private widget.Label jLabel25;
    private widget.Label jLabel250;
    private widget.Label jLabel251;
    private widget.Label jLabel252;
    private widget.Label jLabel253;
    private widget.Label jLabel254;
    private widget.Label jLabel255;
    private widget.Label jLabel256;
    private widget.Label jLabel257;
    private widget.Label jLabel258;
    private widget.Label jLabel259;
    private widget.Label jLabel26;
    private widget.Label jLabel260;
    private widget.Label jLabel261;
    private widget.Label jLabel262;
    private widget.Label jLabel263;
    private widget.Label jLabel264;
    private widget.Label jLabel265;
    private widget.Label jLabel266;
    private widget.Label jLabel267;
    private widget.Label jLabel268;
    private widget.Label jLabel269;
    private widget.Label jLabel271;
    private widget.Label jLabel272;
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
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private widget.Label label11;
    private widget.Label label12;
    private widget.Label label13;
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
    private widget.Label label28;
    private widget.Label label29;
    private widget.PanelBiasa panelBiasa1;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollInput;
    private widget.ScrollPane scrollPane1;
    private widget.ScrollPane scrollPane2;
    private widget.ScrollPane scrollPane5;
    private widget.ScrollPane scrollPane6;
    private widget.Table tbMasalahDetail;
    private widget.Table tbMasalahKeperawatan;
    private widget.Table tbObat;
    private widget.Table tbRencanaDetail;
    private widget.Table tbRencanaKeperawatan;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            ps=koneksi.prepareStatement(
                "select penilaian_awal_keperawatan_ranap.no_rawat,penilaian_awal_keperawatan_ranap.tanggal,penilaian_awal_keperawatan_ranap.informasi,penilaian_awal_keperawatan_ranap.ket_informasi,penilaian_awal_keperawatan_ranap.tiba_diruang_rawat,"+
                "penilaian_awal_keperawatan_ranap.kasus_trauma,penilaian_awal_keperawatan_ranap.cara_masuk,penilaian_awal_keperawatan_ranap.rps,penilaian_awal_keperawatan_ranap.rpd,penilaian_awal_keperawatan_ranap.rpk,penilaian_awal_keperawatan_ranap.rpo,"+
                "penilaian_awal_keperawatan_ranap.riwayat_pembedahan,penilaian_awal_keperawatan_ranap.riwayat_dirawat_dirs,penilaian_awal_keperawatan_ranap.alat_bantu_dipakai,penilaian_awal_keperawatan_ranap.riwayat_kehamilan,"+
                "penilaian_awal_keperawatan_ranap.riwayat_kehamilan_perkiraan,penilaian_awal_keperawatan_ranap.riwayat_tranfusi,penilaian_awal_keperawatan_ranap.riwayat_alergi,penilaian_awal_keperawatan_ranap.riwayat_merokok,"+
                "penilaian_awal_keperawatan_ranap.riwayat_merokok_jumlah,penilaian_awal_keperawatan_ranap.riwayat_alkohol,penilaian_awal_keperawatan_ranap.riwayat_alkohol_jumlah,penilaian_awal_keperawatan_ranap.riwayat_narkoba,"+
                "penilaian_awal_keperawatan_ranap.riwayat_olahraga,penilaian_awal_keperawatan_ranap.pemeriksaan_mental,penilaian_awal_keperawatan_ranap.pemeriksaan_keadaan_umum,penilaian_awal_keperawatan_ranap.pemeriksaan_gcs,"+
                "penilaian_awal_keperawatan_ranap.pemeriksaan_td,penilaian_awal_keperawatan_ranap.pemeriksaan_nadi,penilaian_awal_keperawatan_ranap.pemeriksaan_rr,penilaian_awal_keperawatan_ranap.pemeriksaan_suhu,"+
                "penilaian_awal_keperawatan_ranap.pemeriksaan_spo2,penilaian_awal_keperawatan_ranap.pemeriksaan_bb,penilaian_awal_keperawatan_ranap.pemeriksaan_tb,penilaian_awal_keperawatan_ranap.pemeriksaan_susunan_kepala,"+
                "penilaian_awal_keperawatan_ranap.pemeriksaan_susunan_kepala_keterangan,penilaian_awal_keperawatan_ranap.pemeriksaan_susunan_wajah,penilaian_awal_keperawatan_ranap.pemeriksaan_susunan_wajah_keterangan,"+
                "penilaian_awal_keperawatan_ranap.pemeriksaan_susunan_leher,penilaian_awal_keperawatan_ranap.pemeriksaan_susunan_kejang,penilaian_awal_keperawatan_ranap.pemeriksaan_susunan_kejang_keterangan,"+
                "penilaian_awal_keperawatan_ranap.pemeriksaan_susunan_sensorik,penilaian_awal_keperawatan_ranap.pemeriksaan_kardiovaskuler_denyut_nadi,penilaian_awal_keperawatan_ranap.pemeriksaan_kardiovaskuler_sirkulasi,"+
                "penilaian_awal_keperawatan_ranap.pemeriksaan_kardiovaskuler_sirkulasi_keterangan,penilaian_awal_keperawatan_ranap.pemeriksaan_kardiovaskuler_pulsasi,penilaian_awal_keperawatan_ranap.pemeriksaan_respirasi_pola_nafas,"+
                "penilaian_awal_keperawatan_ranap.pemeriksaan_respirasi_retraksi,penilaian_awal_keperawatan_ranap.pemeriksaan_respirasi_suara_nafas,penilaian_awal_keperawatan_ranap.pemeriksaan_respirasi_volume_pernafasan,"+
                "penilaian_awal_keperawatan_ranap.pemeriksaan_respirasi_jenis_pernafasan,penilaian_awal_keperawatan_ranap.pemeriksaan_respirasi_jenis_pernafasan_keterangan,penilaian_awal_keperawatan_ranap.pemeriksaan_respirasi_irama_nafas,"+
                "penilaian_awal_keperawatan_ranap.pemeriksaan_respirasi_batuk,penilaian_awal_keperawatan_ranap.pemeriksaan_gastrointestinal_mulut,penilaian_awal_keperawatan_ranap.pemeriksaan_gastrointestinal_mulut_keterangan,"+
                "penilaian_awal_keperawatan_ranap.pemeriksaan_gastrointestinal_gigi,penilaian_awal_keperawatan_ranap.pemeriksaan_gastrointestinal_gigi_keterangan,penilaian_awal_keperawatan_ranap.pemeriksaan_gastrointestinal_lidah,"+
                "penilaian_awal_keperawatan_ranap.pemeriksaan_gastrointestinal_lidah_keterangan,penilaian_awal_keperawatan_ranap.pemeriksaan_gastrointestinal_tenggorokan,penilaian_awal_keperawatan_ranap.pemeriksaan_gastrointestinal_tenggorokan_keterangan,"+
                "penilaian_awal_keperawatan_ranap.pemeriksaan_gastrointestinal_abdomen,penilaian_awal_keperawatan_ranap.pemeriksaan_gastrointestinal_abdomen_keterangan,penilaian_awal_keperawatan_ranap.pemeriksaan_gastrointestinal_peistatik_usus,"+
                "penilaian_awal_keperawatan_ranap.pemeriksaan_gastrointestinal_anus,penilaian_awal_keperawatan_ranap.pemeriksaan_neurologi_pengelihatan,penilaian_awal_keperawatan_ranap.pemeriksaan_neurologi_pengelihatan_keterangan,"+
                "penilaian_awal_keperawatan_ranap.pemeriksaan_neurologi_alat_bantu_penglihatan,penilaian_awal_keperawatan_ranap.pemeriksaan_neurologi_pendengaran,penilaian_awal_keperawatan_ranap.pemeriksaan_neurologi_bicara,"+
                "penilaian_awal_keperawatan_ranap.pemeriksaan_neurologi_bicara_keterangan,penilaian_awal_keperawatan_ranap.pemeriksaan_neurologi_sensorik,penilaian_awal_keperawatan_ranap.pemeriksaan_neurologi_motorik,"+
                "penilaian_awal_keperawatan_ranap.pemeriksaan_neurologi_kekuatan_otot,penilaian_awal_keperawatan_ranap.pemeriksaan_integument_warnakulit,penilaian_awal_keperawatan_ranap.pemeriksaan_integument_turgor,"+
                "penilaian_awal_keperawatan_ranap.pemeriksaan_integument_kulit,penilaian_awal_keperawatan_ranap.pemeriksaan_integument_dekubitas,penilaian_awal_keperawatan_ranap.pemeriksaan_muskuloskletal_pergerakan_sendi,"+
                "penilaian_awal_keperawatan_ranap.pemeriksaan_muskuloskletal_kekauatan_otot,penilaian_awal_keperawatan_ranap.pemeriksaan_muskuloskletal_nyeri_sendi,penilaian_awal_keperawatan_ranap.pemeriksaan_muskuloskletal_nyeri_sendi_keterangan,"+
                "penilaian_awal_keperawatan_ranap.pemeriksaan_muskuloskletal_oedema,penilaian_awal_keperawatan_ranap.pemeriksaan_muskuloskletal_oedema_keterangan,penilaian_awal_keperawatan_ranap.pemeriksaan_muskuloskletal_fraktur,"+
                "penilaian_awal_keperawatan_ranap.pemeriksaan_muskuloskletal_fraktur_keterangan,penilaian_awal_keperawatan_ranap.pemeriksaan_eliminasi_bab_frekuensi_jumlah,penilaian_awal_keperawatan_ranap.pemeriksaan_eliminasi_bab_frekuensi_durasi,"+
                "penilaian_awal_keperawatan_ranap.pemeriksaan_eliminasi_bab_konsistensi,penilaian_awal_keperawatan_ranap.pemeriksaan_eliminasi_bab_warna,penilaian_awal_keperawatan_ranap.pemeriksaan_eliminasi_bak_frekuensi_jumlah,"+
                "penilaian_awal_keperawatan_ranap.pemeriksaan_eliminasi_bak_frekuensi_durasi,penilaian_awal_keperawatan_ranap.pemeriksaan_eliminasi_bak_warna,penilaian_awal_keperawatan_ranap.pemeriksaan_eliminasi_bak_lainlain,"+
                "penilaian_awal_keperawatan_ranap.pola_aktifitas_makanminum,penilaian_awal_keperawatan_ranap.pola_aktifitas_mandi,penilaian_awal_keperawatan_ranap.pola_aktifitas_eliminasi,penilaian_awal_keperawatan_ranap.pola_aktifitas_berpakaian,"+
                "penilaian_awal_keperawatan_ranap.pola_aktifitas_berpindah,penilaian_awal_keperawatan_ranap.pola_nutrisi_frekuesi_makan,penilaian_awal_keperawatan_ranap.pola_nutrisi_jenis_makanan,penilaian_awal_keperawatan_ranap.pola_nutrisi_porsi_makan,"+
                "penilaian_awal_keperawatan_ranap.pola_tidur_lama_tidur,penilaian_awal_keperawatan_ranap.pola_tidur_gangguan,penilaian_awal_keperawatan_ranap.pengkajian_fungsi_kemampuan_sehari,penilaian_awal_keperawatan_ranap.pengkajian_fungsi_aktifitas,"+
                "penilaian_awal_keperawatan_ranap.pengkajian_fungsi_berjalan,penilaian_awal_keperawatan_ranap.pengkajian_fungsi_berjalan_keterangan,penilaian_awal_keperawatan_ranap.pengkajian_fungsi_ambulasi,"+
                "penilaian_awal_keperawatan_ranap.pengkajian_fungsi_ekstrimitas_atas,penilaian_awal_keperawatan_ranap.pengkajian_fungsi_ekstrimitas_atas_keterangan,penilaian_awal_keperawatan_ranap.pengkajian_fungsi_ekstrimitas_bawah,"+
                "penilaian_awal_keperawatan_ranap.pengkajian_fungsi_ekstrimitas_bawah_keterangan,penilaian_awal_keperawatan_ranap.pengkajian_fungsi_menggenggam,penilaian_awal_keperawatan_ranap.pengkajian_fungsi_menggenggam_keterangan,"+
                "penilaian_awal_keperawatan_ranap.pengkajian_fungsi_koordinasi,penilaian_awal_keperawatan_ranap.pengkajian_fungsi_koordinasi_keterangan,penilaian_awal_keperawatan_ranap.pengkajian_fungsi_kesimpulan,"+
                "penilaian_awal_keperawatan_ranap.riwayat_psiko_kondisi_psiko,penilaian_awal_keperawatan_ranap.riwayat_psiko_gangguan_jiwa,penilaian_awal_keperawatan_ranap.riwayat_psiko_perilaku,"+
                "penilaian_awal_keperawatan_ranap.riwayat_psiko_perilaku_keterangan,penilaian_awal_keperawatan_ranap.riwayat_psiko_hubungan_keluarga,penilaian_awal_keperawatan_ranap.riwayat_psiko_tinggal,"+
                "penilaian_awal_keperawatan_ranap.riwayat_psiko_tinggal_keterangan,penilaian_awal_keperawatan_ranap.riwayat_psiko_nilai_kepercayaan,penilaian_awal_keperawatan_ranap.riwayat_psiko_nilai_kepercayaan_keterangan,"+
                "penilaian_awal_keperawatan_ranap.riwayat_psiko_pendidikan_pj,penilaian_awal_keperawatan_ranap.riwayat_psiko_edukasi_diberikan,penilaian_awal_keperawatan_ranap.riwayat_psiko_edukasi_diberikan_keterangan,"+
                "penilaian_awal_keperawatan_ranap.penilaian_nyeri,penilaian_awal_keperawatan_ranap.penilaian_nyeri_penyebab,penilaian_awal_keperawatan_ranap.penilaian_nyeri_ket_penyebab,penilaian_awal_keperawatan_ranap.penilaian_nyeri_kualitas,"+
                "penilaian_awal_keperawatan_ranap.penilaian_nyeri_ket_kualitas,penilaian_awal_keperawatan_ranap.penilaian_nyeri_lokasi,penilaian_awal_keperawatan_ranap.penilaian_nyeri_menyebar,penilaian_awal_keperawatan_ranap.penilaian_nyeri_skala,"+
                "penilaian_awal_keperawatan_ranap.penilaian_nyeri_waktu,penilaian_awal_keperawatan_ranap.penilaian_nyeri_hilang,penilaian_awal_keperawatan_ranap.penilaian_nyeri_ket_hilang,penilaian_awal_keperawatan_ranap.penilaian_nyeri_diberitahukan_dokter,"+
                "penilaian_awal_keperawatan_ranap.penilaian_nyeri_jam_diberitahukan_dokter,penilaian_awal_keperawatan_ranap.penilaian_jatuhmorse_skala1,penilaian_awal_keperawatan_ranap.penilaian_jatuhmorse_nilai1,"+
                "penilaian_awal_keperawatan_ranap.penilaian_jatuhmorse_skala2,penilaian_awal_keperawatan_ranap.penilaian_jatuhmorse_nilai2,penilaian_awal_keperawatan_ranap.penilaian_jatuhmorse_skala3,"+
                "penilaian_awal_keperawatan_ranap.penilaian_jatuhmorse_nilai3,penilaian_awal_keperawatan_ranap.penilaian_jatuhmorse_skala4,penilaian_awal_keperawatan_ranap.penilaian_jatuhmorse_nilai4,"+
                "penilaian_awal_keperawatan_ranap.penilaian_jatuhmorse_skala5,penilaian_awal_keperawatan_ranap.penilaian_jatuhmorse_nilai5,penilaian_awal_keperawatan_ranap.penilaian_jatuhmorse_skala6,"+
                "penilaian_awal_keperawatan_ranap.penilaian_jatuhmorse_nilai6,penilaian_awal_keperawatan_ranap.penilaian_jatuhmorse_totalnilai,penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_skala1,"+
                "penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_nilai1,penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_skala2,penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_nilai2,"+
                "penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_skala3,penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_nilai3,penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_skala4,"+
                "penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_nilai4,penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_skala5,penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_nilai5,"+
                "penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_skala6,penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_nilai6,penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_skala7,"+
                "penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_nilai7,penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_skala8,penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_nilai8,"+
                "penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_skala9,penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_nilai9,penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_skala10,"+
                "penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_nilai10,penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_skala11,penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_nilai11,"+
                "penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_totalnilai,penilaian_awal_keperawatan_ranap.skrining_gizi1,penilaian_awal_keperawatan_ranap.nilai_gizi1,penilaian_awal_keperawatan_ranap.skrining_gizi2,"+
                "penilaian_awal_keperawatan_ranap.nilai_gizi2,penilaian_awal_keperawatan_ranap.nilai_total_gizi,penilaian_awal_keperawatan_ranap.skrining_gizi_diagnosa_khusus,penilaian_awal_keperawatan_ranap.skrining_gizi_ket_diagnosa_khusus,"+
                "penilaian_awal_keperawatan_ranap.skrining_gizi_diketahui_dietisen,penilaian_awal_keperawatan_ranap.skrining_gizi_jam_diketahui_dietisen,penilaian_awal_keperawatan_ranap.rencana,penilaian_awal_keperawatan_ranap.nip1,"+
                "penilaian_awal_keperawatan_ranap.nip2,penilaian_awal_keperawatan_ranap.kd_dokter,pasien.tgl_lahir,pasien.jk,pengkaji1.nama as pengkaji1,pengkaji2.nama as pengkaji2,dokter.nm_dokter,"+
                "reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.agama,pasien.pekerjaan,pasien.pnd,penjab.png_jawab,bahasa_pasien.nama_bahasa "+
                "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join penilaian_awal_keperawatan_ranap on reg_periksa.no_rawat=penilaian_awal_keperawatan_ranap.no_rawat "+
                "inner join petugas as pengkaji1 on penilaian_awal_keperawatan_ranap.nip1=pengkaji1.nip "+
                "inner join petugas as pengkaji2 on penilaian_awal_keperawatan_ranap.nip2=pengkaji2.nip "+
                "inner join dokter on penilaian_awal_keperawatan_ranap.kd_dokter=dokter.kd_dokter "+
                "inner join bahasa_pasien on bahasa_pasien.id=pasien.bahasa_pasien "+
                "inner join penjab on penjab.kd_pj=reg_periksa.kd_pj where "+
                "penilaian_awal_keperawatan_ranap.tanggal between ? and ? "+
                (TCari.getText().trim().equals("")?"":"and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or penilaian_awal_keperawatan_ranap.nip1 like ? or "+
                "pengkaji1.nama like ? or penilaian_awal_keperawatan_ranap.kd_dokter like ? or dokter.nm_dokter like ?)")+
                " order by penilaian_awal_keperawatan_ranap.tanggal");
            
            try {
                ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                if(!TCari.getText().equals("")){
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
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("tgl_lahir"),rs.getString("jk"),rs.getString("nip1"),rs.getString("pengkaji1"),rs.getString("nip2"),rs.getString("pengkaji2"),
                        rs.getString("kd_dokter"),rs.getString("nm_dokter"),rs.getString("tanggal"),rs.getString("kasus_trauma"),rs.getString("informasi")+", "+rs.getString("ket_informasi"),rs.getString("tiba_diruang_rawat"),rs.getString("cara_masuk"),
                        rs.getString("rps"),rs.getString("rpd"),rs.getString("rpk"),rs.getString("rpo"),rs.getString("riwayat_pembedahan"),rs.getString("riwayat_dirawat_dirs"),rs.getString("alat_bantu_dipakai"),
                        rs.getString("riwayat_kehamilan")+", "+rs.getString("riwayat_kehamilan_perkiraan"),rs.getString("riwayat_tranfusi"),rs.getString("riwayat_alergi"),rs.getString("riwayat_merokok"),rs.getString("riwayat_merokok_jumlah"),
                        rs.getString("riwayat_alkohol"),rs.getString("riwayat_alkohol_jumlah"),rs.getString("riwayat_narkoba"),rs.getString("riwayat_olahraga"),rs.getString("pemeriksaan_mental"),rs.getString("pemeriksaan_keadaan_umum"),
                        rs.getString("pemeriksaan_gcs"),rs.getString("pemeriksaan_td"),rs.getString("pemeriksaan_nadi"),rs.getString("pemeriksaan_rr"),rs.getString("pemeriksaan_suhu"),rs.getString("pemeriksaan_spo2"),rs.getString("pemeriksaan_bb"),
                        rs.getString("pemeriksaan_tb"),rs.getString("pemeriksaan_susunan_kepala")+", "+rs.getString("pemeriksaan_susunan_kepala_keterangan"),rs.getString("pemeriksaan_susunan_wajah")+", "+rs.getString("pemeriksaan_susunan_wajah_keterangan"),
                        rs.getString("pemeriksaan_susunan_leher"),rs.getString("pemeriksaan_susunan_kejang")+", "+rs.getString("pemeriksaan_susunan_kejang_keterangan"),rs.getString("pemeriksaan_susunan_sensorik"),rs.getString("pemeriksaan_kardiovaskuler_pulsasi"),
                        rs.getString("pemeriksaan_kardiovaskuler_sirkulasi")+", "+rs.getString("pemeriksaan_kardiovaskuler_sirkulasi_keterangan"),rs.getString("pemeriksaan_kardiovaskuler_denyut_nadi"),rs.getString("pemeriksaan_respirasi_retraksi"),
                        rs.getString("pemeriksaan_respirasi_pola_nafas"),rs.getString("pemeriksaan_respirasi_suara_nafas"),rs.getString("pemeriksaan_respirasi_batuk"),rs.getString("pemeriksaan_respirasi_volume_pernafasan"),
                        rs.getString("pemeriksaan_respirasi_jenis_pernafasan")+", "+rs.getString("pemeriksaan_respirasi_jenis_pernafasan_keterangan"),rs.getString("pemeriksaan_respirasi_irama_nafas"),
                        rs.getString("pemeriksaan_gastrointestinal_mulut")+", "+rs.getString("pemeriksaan_gastrointestinal_mulut_keterangan"),rs.getString("pemeriksaan_gastrointestinal_lidah")+", "+rs.getString("pemeriksaan_gastrointestinal_lidah_keterangan"),
                        rs.getString("pemeriksaan_gastrointestinal_gigi")+", "+rs.getString("pemeriksaan_gastrointestinal_gigi_keterangan"),rs.getString("pemeriksaan_gastrointestinal_tenggorokan")+", "+rs.getString("pemeriksaan_gastrointestinal_tenggorokan_keterangan"),
                        rs.getString("pemeriksaan_gastrointestinal_abdomen")+", "+rs.getString("pemeriksaan_gastrointestinal_abdomen_keterangan"),rs.getString("pemeriksaan_gastrointestinal_peistatik_usus"),rs.getString("pemeriksaan_gastrointestinal_anus"),
                        rs.getString("pemeriksaan_neurologi_sensorik"),rs.getString("pemeriksaan_neurologi_pengelihatan")+", "+rs.getString("pemeriksaan_neurologi_pengelihatan_keterangan"),rs.getString("pemeriksaan_neurologi_alat_bantu_penglihatan"),
                        rs.getString("pemeriksaan_neurologi_motorik"),rs.getString("pemeriksaan_neurologi_pendengaran"),rs.getString("pemeriksaan_neurologi_bicara")+", "+rs.getString("pemeriksaan_neurologi_bicara_keterangan"),
                        rs.getString("pemeriksaan_neurologi_kekuatan_otot"),rs.getString("pemeriksaan_integument_kulit"),rs.getString("pemeriksaan_integument_warnakulit"),rs.getString("pemeriksaan_integument_turgor"),
                        rs.getString("pemeriksaan_integument_dekubitas"),rs.getString("pemeriksaan_muskuloskletal_oedema")+", "+rs.getString("pemeriksaan_muskuloskletal_oedema_keterangan"),rs.getString("pemeriksaan_muskuloskletal_pergerakan_sendi"),
                        rs.getString("pemeriksaan_muskuloskletal_kekauatan_otot"),rs.getString("pemeriksaan_muskuloskletal_fraktur")+", "+rs.getString("pemeriksaan_muskuloskletal_fraktur_keterangan"),
                        rs.getString("pemeriksaan_muskuloskletal_nyeri_sendi")+", "+rs.getString("pemeriksaan_muskuloskletal_nyeri_sendi_keterangan"),rs.getString("pemeriksaan_eliminasi_bab_frekuensi_jumlah"),
                        rs.getString("pemeriksaan_eliminasi_bab_frekuensi_durasi"),rs.getString("pemeriksaan_eliminasi_bab_konsistensi"),rs.getString("pemeriksaan_eliminasi_bab_warna"),rs.getString("pemeriksaan_eliminasi_bak_frekuensi_jumlah"),
                        rs.getString("pemeriksaan_eliminasi_bak_frekuensi_durasi"),rs.getString("pemeriksaan_eliminasi_bak_warna"),rs.getString("pemeriksaan_eliminasi_bak_lainlain"),rs.getString("pola_aktifitas_mandi"),
                        rs.getString("pola_aktifitas_makanminum"),rs.getString("pola_aktifitas_berpakaian"),rs.getString("pola_aktifitas_eliminasi"),rs.getString("pola_aktifitas_berpindah"),rs.getString("pola_nutrisi_porsi_makan"),
                        rs.getString("pola_nutrisi_frekuesi_makan"),rs.getString("pola_nutrisi_jenis_makanan"),rs.getString("pola_tidur_lama_tidur"),rs.getString("pola_tidur_gangguan"),rs.getString("pengkajian_fungsi_kemampuan_sehari"),
                        rs.getString("pengkajian_fungsi_berjalan")+", "+rs.getString("pengkajian_fungsi_berjalan_keterangan"),rs.getString("pengkajian_fungsi_aktifitas"),rs.getString("pengkajian_fungsi_ambulasi"),
                        rs.getString("pengkajian_fungsi_ekstrimitas_atas")+", "+rs.getString("pengkajian_fungsi_ekstrimitas_atas_keterangan"),rs.getString("pengkajian_fungsi_ekstrimitas_bawah")+", "+rs.getString("pengkajian_fungsi_ekstrimitas_bawah_keterangan"),
                        rs.getString("pengkajian_fungsi_menggenggam")+", "+rs.getString("pengkajian_fungsi_menggenggam_keterangan"),rs.getString("pengkajian_fungsi_koordinasi")+", "+rs.getString("pengkajian_fungsi_koordinasi_keterangan"),
                        rs.getString("pengkajian_fungsi_kesimpulan"),rs.getString("riwayat_psiko_kondisi_psiko"),rs.getString("riwayat_psiko_perilaku")+", "+rs.getString("riwayat_psiko_perilaku_keterangan"),rs.getString("riwayat_psiko_gangguan_jiwa"),
                        rs.getString("riwayat_psiko_hubungan_keluarga"),rs.getString("agama"),rs.getString("riwayat_psiko_tinggal")+", "+rs.getString("riwayat_psiko_tinggal_keterangan"),rs.getString("pekerjaan"),rs.getString("png_jawab"),
                        rs.getString("riwayat_psiko_nilai_kepercayaan")+", "+rs.getString("riwayat_psiko_nilai_kepercayaan_keterangan"),rs.getString("nama_bahasa"),rs.getString("pnd"),rs.getString("riwayat_psiko_pendidikan_pj"),
                        rs.getString("riwayat_psiko_edukasi_diberikan")+", "+rs.getString("riwayat_psiko_edukasi_diberikan_keterangan"),rs.getString("penilaian_nyeri"),rs.getString("penilaian_nyeri_penyebab")+", "+rs.getString("penilaian_nyeri_ket_penyebab"),
                        rs.getString("penilaian_nyeri_kualitas")+", "+rs.getString("penilaian_nyeri_ket_kualitas"),rs.getString("penilaian_nyeri_lokasi"),rs.getString("penilaian_nyeri_menyebar"),rs.getString("penilaian_nyeri_skala"),
                        rs.getString("penilaian_nyeri_waktu"),rs.getString("penilaian_nyeri_hilang")+", "+rs.getString("penilaian_nyeri_ket_hilang"),rs.getString("penilaian_nyeri_diberitahukan_dokter")+", "+rs.getString("penilaian_nyeri_jam_diberitahukan_dokter"),
                        rs.getString("penilaian_jatuhmorse_skala1"),rs.getString("penilaian_jatuhmorse_nilai1"),rs.getString("penilaian_jatuhmorse_skala2"),rs.getString("penilaian_jatuhmorse_nilai2"),
                        rs.getString("penilaian_jatuhmorse_skala3"),rs.getString("penilaian_jatuhmorse_nilai3"),rs.getString("penilaian_jatuhmorse_skala4"),rs.getString("penilaian_jatuhmorse_nilai4"),
                        rs.getString("penilaian_jatuhmorse_skala5"),rs.getString("penilaian_jatuhmorse_nilai5"),rs.getString("penilaian_jatuhmorse_skala6"),rs.getString("penilaian_jatuhmorse_nilai6"),
                        rs.getString("penilaian_jatuhmorse_totalnilai"),rs.getString("penilaian_jatuhsydney_skala1"),rs.getString("penilaian_jatuhsydney_nilai1"),rs.getString("penilaian_jatuhsydney_skala2"),
                        rs.getString("penilaian_jatuhsydney_nilai2"),rs.getString("penilaian_jatuhsydney_skala3"),rs.getString("penilaian_jatuhsydney_nilai3"),rs.getString("penilaian_jatuhsydney_skala4"),rs.getString("penilaian_jatuhsydney_nilai4"),
                        rs.getString("penilaian_jatuhsydney_skala5"),rs.getString("penilaian_jatuhsydney_nilai5"),rs.getString("penilaian_jatuhsydney_skala6"),rs.getString("penilaian_jatuhsydney_nilai6"),rs.getString("penilaian_jatuhsydney_skala7"),
                        rs.getString("penilaian_jatuhsydney_nilai7"),rs.getString("penilaian_jatuhsydney_skala8"),rs.getString("penilaian_jatuhsydney_nilai8"),rs.getString("penilaian_jatuhsydney_skala9"),rs.getString("penilaian_jatuhsydney_nilai9"),
                        rs.getString("penilaian_jatuhsydney_skala10"),rs.getString("penilaian_jatuhsydney_nilai10"),rs.getString("penilaian_jatuhsydney_skala11"),rs.getString("penilaian_jatuhsydney_nilai11"),
                        rs.getString("penilaian_jatuhsydney_totalnilai"),rs.getString("skrining_gizi1"),rs.getString("nilai_gizi1"),rs.getString("skrining_gizi2"),rs.getString("nilai_gizi2"),rs.getString("nilai_total_gizi"),
                        rs.getString("skrining_gizi_diagnosa_khusus"),rs.getString("skrining_gizi_ket_diagnosa_khusus"),rs.getString("skrining_gizi_diketahui_dietisen"),rs.getString("skrining_gizi_jam_diketahui_dietisen"),
                        rs.getString("rencana")
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
        AsalPasien.setSelectedIndex(0);
        CaraMasuk.setSelectedIndex(0);
        DiperolehDari.setText("");
        HubunganDenganPasien.setText("");
        KeluhanUtama.setText("");
        PrenatalG.setText("");
        PrenatalP.setText("");
        PrenatalA.setText("");
        PrenatalUK.setText("");
        RiwayatPenyakitIbu.setSelectedIndex(0);
        KeteranganRiwayatPenyakitIbu.setText("");
        RiwayatPengobatanIbu.setText("");
        PernahDirawat.setSelectedIndex(0);
        KeteranganPernahDirawat.setText("");
        StatusGiziIbu.setSelectedIndex(0);
        IntranatalG.setText("");
        IntranatalP.setText("");
        IntranatalA.setText("");
        KondisiSaatLahir.setText("");
        CaraPersalinan.setSelectedIndex(0);
        KeteranganCaraPersalinan.setText("");
        ApgarScore.setText("");
        IntranatalLetak.setText("");
        TaliPusat.setSelectedIndex(0);
        Ketuban.setSelectedIndex(0);
        AntoBB.setText("");
        AntoPB.setText("");
        AntoLK.setText("");
        AntoLD.setText("");
        AntoLP.setText("");
        RisikoInfeksiMayor.setSelectedIndex(0);
        KeteranganRisikoInfeksiMayor.setText("");
        RisikoInfeksiMinor.setSelectedIndex(0);
        KeteranganRisikoInfeksiMinor.setText("");
        Nutrisi.setSelectedIndex(0);
        KeteranganNutrisi.setText("");
        NutrisiFrekuensi.setText("");
        NutrisiKali.setText("");
        EliminasiBAK.setSelectedIndex(0);
        KeteranganEliminasiBAK.setText("");
        EliminasiBAB.setSelectedIndex(0);
        KeteranganEliminasiBAB.setText("");
        AlergiObat.setSelectedIndex(0);
        KeteranganAlergiObat.setText("");
        ReaksiAlergiObat.setText("");
        AlergiMakanan.setSelectedIndex(0);
        KeteranganAlergiMakanan.setText("");
        ReaksiAlergiMakanan.setText("");
        AlergiLainnya.setSelectedIndex(0);
        KeteranganAlergiLainnya.setText("");
        ReaksiAlergiLainnya.setText("");
        RiwayatPenyakitKeluarga.setSelectedIndex(0);
        KeteranganRiwayatPenyakitKeluarga.setText("");
        RiwayatImunisasi.setSelectedIndex(0);
        KeteranganRiwayatImunisasi.setText("");
        TranfusiDarah.setSelectedIndex(0);
        KeteranganTranfusiDarah.setText("");
        ReaksiTranfusiDarah.setSelectedIndex(0);
        KeteranganReaksiTranfusiDarah.setText("");
        ObatobatanDiminum.setSelectedIndex(0);
        KeteranganObatobatanDiminum.setText("");
        Merokok.setSelectedIndex(0);
        JumlahMerokok.setText("");
        ObatTidurNarkoba.setSelectedIndex(0);
        KeteranganObatTidurNarkoba.setText("");
        Alkohol.setSelectedIndex(0);
        JumlahAlkohol.setText("");
        ComposMentis.setSelectedIndex(0);
        KeadaanUmum.setSelectedIndex(0);
        FisikGCS.setText("");
        FisikTD.setText("");
        FisikSuhu.setText("");
        FisikHR.setText("");
        FisikRR.setText("");
        FisikSPO.setText("");
        FisikDownScore.setText("");
        FisikBB.setText("");
        FisikTB.setText("");
        FisikLK.setText("");
        FisikLD.setText("");
        FisikLP.setText("");
        GDBayi.setSelectedIndex(0);
        GDIbu.setSelectedIndex(0);
        GDAyah.setSelectedIndex(0);
        GerakBayi.setSelectedIndex(0);
        KepalaBayi.setSelectedIndex(0);
        KeteranganKepalaBayi.setText("");
        Ubunubun.setSelectedIndex(0);
        KeteranganUbunubun.setText("");
        Wajah.setSelectedIndex(0);
        KeteranganWajah.setText("");
        Kejang.setSelectedIndex(0);
        KeteranganKejang.setText("");
        Refleks.setSelectedIndex(0);
        KeteranganRefleks.setText("");
        TangisBayi.setSelectedIndex(0);
        KeteranganTangisBayi.setText("");
        
        Rencana.setText("");
        for (i = 0; i < tabModeMasalah.getRowCount(); i++) {
            tabModeMasalah.setValueAt(false,i,0);
        }
        Valid.tabelKosong(tabModeRencana);
        TabRawat.setSelectedIndex(0);
        AsalPasien.requestFocus();
    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()); 
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString()); 
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString()); 
            Jk.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString()); 
            KdPetugas2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString()); 
            NmPetugas2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString()); 
            KdDPJP.setText(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString()); 
            NmDPJP.setText(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString()); 
            AsalPasien.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString()); 
            if(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString().contains("Autoanamnesis")){
                CaraMasuk.setSelectedItem("Autoanamnesis");
            }else{
                CaraMasuk.setSelectedItem("Alloanamnesis");
            }
            DiperolehDari.setText(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString().replaceAll(CaraMasuk.getSelectedItem().toString()+", ",""));
            //isTotalResikoSydney();
            
            Rencana.setText(tbObat.getValueAt(tbObat.getSelectedRow(),174).toString());
            
            try {
                Valid.tabelKosong(tabModeMasalah);
                
                ps=koneksi.prepareStatement(
                        "select master_masalah_keperawatan_neonatus.kode_masalah,master_masalah_keperawatan_neonatus.nama_masalah from master_masalah_keperawatan_neonatus "+
                        "inner join penilaian_awal_keperawatan_ranap_masalah on penilaian_awal_keperawatan_ranap_masalah.kode_masalah=master_masalah_keperawatan_neonatus.kode_masalah "+
                        "where penilaian_awal_keperawatan_ranap_masalah.no_rawat=? order by penilaian_awal_keperawatan_ranap_masalah.kode_masalah");
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
            
            try {
                Valid.tabelKosong(tabModeRencana);
                
                ps=koneksi.prepareStatement(
                        "select master_rencana_keperawatan_neonatus.kode_rencana,master_rencana_keperawatan_neonatus.rencana_keperawatan from master_rencana_keperawatan_neonatus "+
                        "inner join penilaian_awal_keperawatan_ranap_rencana on penilaian_awal_keperawatan_ranap_rencana.kode_rencana=master_rencana_keperawatan_neonatus.kode_rencana "+
                        "where penilaian_awal_keperawatan_ranap_rencana.no_rawat=? order by penilaian_awal_keperawatan_ranap_rencana.kode_rencana");
                try {
                    ps.setString(1,tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
                    rs=ps.executeQuery();
                    while(rs.next()){
                        tabModeRencana.addRow(new Object[]{true,rs.getString(1),rs.getString(2)});
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
            
            Valid.SetTgl2(TglAsuhan,tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
        }
    }

    private void isRawat() {
        try {
            ps=koneksi.prepareStatement(
                    "select pasien.nm_pasien, if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,pasien.agama,"+
                    "bahasa_pasien.nama_bahasa,pasien.pnd,pasien.pekerjaan "+
                    "from pasien inner join bahasa_pasien on bahasa_pasien.id=pasien.bahasa_pasien "+
                    "where pasien.no_rkm_medis=?");
            try {
                ps.setString(1,TNoRM.getText());
                rs=ps.executeQuery();
                if(rs.next()){
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
    
    public void setNoRm(String norwt, Date tgl2,String carabayar,String norm) {
        TNoRw.setText(norwt);
        TNoRM.setText(norm);
        TCari.setText(norwt);
        Sequel.cariIsi("select reg_periksa.tgl_registrasi from reg_periksa where reg_periksa.no_rawat='"+norwt+"'", DTPCari1);
        //CaraBayar.setText(carabayar);
        DTPCari2.setDate(tgl2);    
        isRawat(); 
    }
    
    
    public void isCek(){
        BtnSimpan.setEnabled(akses.getpenilaian_awal_keperawatan_ranap_neonatus());
        BtnHapus.setEnabled(akses.getpenilaian_awal_keperawatan_ranap_neonatus());
        BtnEdit.setEnabled(akses.getpenilaian_awal_keperawatan_ranap_neonatus());
        BtnEdit.setEnabled(akses.getpenilaian_awal_keperawatan_ranap_neonatus()); 
        BtnTambahMasalah.setEnabled(akses.getmaster_masalah_keperawatan_neonatus()); 
        BtnTambahRencana.setEnabled(akses.getmaster_rencana_keperawatan_neonatus()); 
        if(akses.getjml2()>=1){
            KdPetugas.setEditable(false);
            BtnPetugas.setEnabled(false);
            KdPetugas.setText(akses.getkode());
            NmPetugas.setText(petugas.tampil3(KdPetugas.getText()));
            if(NmPetugas.getText().equals("")){
                KdPetugas.setText("");
                JOptionPane.showMessageDialog(null,"User login bukan petugas...!!");
            }
        }            
    }

    public void setTampil(){
       TabRawat.setSelectedIndex(1);
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
            DetailRencana.setText(tbObat.getValueAt(tbObat.getSelectedRow(),174).toString());
            
            try {
                Valid.tabelKosong(tabModeDetailMasalah);
                ps=koneksi.prepareStatement(
                        "select master_masalah_keperawatan_neonatus.kode_masalah,master_masalah_keperawatan_neonatus.nama_masalah from master_masalah_keperawatan_neonatus "+
                        "inner join penilaian_awal_keperawatan_ranap_masalah on penilaian_awal_keperawatan_ranap_masalah.kode_masalah=master_masalah_keperawatan_neonatus.kode_masalah "+
                        "where penilaian_awal_keperawatan_ranap_masalah.no_rawat=? order by penilaian_awal_keperawatan_ranap_masalah.kode_masalah");
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
            
            try {
                Valid.tabelKosong(tabModeDetailRencana);
                ps=koneksi.prepareStatement(
                        "select master_rencana_keperawatan_neonatus.kode_rencana,master_rencana_keperawatan_neonatus.rencana_keperawatan from master_rencana_keperawatan_neonatus "+
                        "inner join penilaian_awal_keperawatan_ranap_rencana on penilaian_awal_keperawatan_ranap_rencana.kode_rencana=master_rencana_keperawatan_neonatus.kode_rencana "+
                        "where penilaian_awal_keperawatan_ranap_rencana.no_rawat=? order by penilaian_awal_keperawatan_ranap_rencana.kode_rencana");
                try {
                    ps.setString(1,tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
                    rs=ps.executeQuery();
                    while(rs.next()){
                        tabModeDetailRencana.addRow(new Object[]{rs.getString(1),rs.getString(2)});
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
   
    

    private void hapus() {
        if(Sequel.queryu2tf("delete from penilaian_awal_keperawatan_ranap where no_rawat=?",1,new String[]{
            tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
        })==true){
            TNoRM1.setText("");
            TPasien1.setText("");
            ChkAccor.setSelected(false);
            isMenu();
            tabMode.removeRow(tbObat.getSelectedRow());
            LCount.setText(""+tabMode.getRowCount());
        }else{
            JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
        }
    }

    private void ganti() {
        /*if(Sequel.mengedittf("penilaian_awal_keperawatan_ranap","no_rawat=?","no_rawat=?,tanggal=?,informasi=?,ket_informasi=?,tiba_diruang_rawat=?,kasus_trauma=?,cara_masuk=?,rps=?,rpd=?,rpk=?,rpo=?,riwayat_pembedahan=?,riwayat_dirawat_dirs=?,alat_bantu_dipakai=?,riwayat_kehamilan=?,riwayat_kehamilan_perkiraan=?,riwayat_tranfusi=?,riwayat_alergi=?,riwayat_merokok=?,riwayat_merokok_jumlah=?,riwayat_alkohol=?,riwayat_alkohol_jumlah=?,riwayat_narkoba=?,riwayat_olahraga=?,pemeriksaan_mental=?,pemeriksaan_keadaan_umum=?,pemeriksaan_gcs=?,pemeriksaan_td=?,pemeriksaan_nadi=?,pemeriksaan_rr=?,pemeriksaan_suhu=?,pemeriksaan_spo2=?,pemeriksaan_bb=?,pemeriksaan_tb=?,pemeriksaan_susunan_kepala=?,pemeriksaan_susunan_kepala_keterangan=?,pemeriksaan_susunan_wajah=?,pemeriksaan_susunan_wajah_keterangan=?,pemeriksaan_susunan_leher=?,pemeriksaan_susunan_kejang=?,pemeriksaan_susunan_kejang_keterangan=?,pemeriksaan_susunan_sensorik=?,pemeriksaan_kardiovaskuler_denyut_nadi=?,pemeriksaan_kardiovaskuler_sirkulasi=?,pemeriksaan_kardiovaskuler_sirkulasi_keterangan=?,pemeriksaan_kardiovaskuler_pulsasi=?,pemeriksaan_respirasi_pola_nafas=?,pemeriksaan_respirasi_retraksi=?,pemeriksaan_respirasi_suara_nafas=?,pemeriksaan_respirasi_volume_pernafasan=?,pemeriksaan_respirasi_jenis_pernafasan=?,pemeriksaan_respirasi_jenis_pernafasan_keterangan=?,pemeriksaan_respirasi_irama_nafas=?,pemeriksaan_respirasi_batuk=?,pemeriksaan_gastrointestinal_mulut=?,pemeriksaan_gastrointestinal_mulut_keterangan=?,pemeriksaan_gastrointestinal_gigi=?,pemeriksaan_gastrointestinal_gigi_keterangan=?,pemeriksaan_gastrointestinal_lidah=?,pemeriksaan_gastrointestinal_lidah_keterangan=?,pemeriksaan_gastrointestinal_tenggorokan=?,pemeriksaan_gastrointestinal_tenggorokan_keterangan=?,pemeriksaan_gastrointestinal_abdomen=?,pemeriksaan_gastrointestinal_abdomen_keterangan=?,pemeriksaan_gastrointestinal_peistatik_usus=?,pemeriksaan_gastrointestinal_anus=?,pemeriksaan_neurologi_pengelihatan=?,pemeriksaan_neurologi_pengelihatan_keterangan=?,pemeriksaan_neurologi_alat_bantu_penglihatan=?,pemeriksaan_neurologi_pendengaran=?,pemeriksaan_neurologi_bicara=?,pemeriksaan_neurologi_bicara_keterangan=?,pemeriksaan_neurologi_sensorik=?,pemeriksaan_neurologi_motorik=?,pemeriksaan_neurologi_kekuatan_otot=?,pemeriksaan_integument_warnakulit=?,pemeriksaan_integument_turgor=?,pemeriksaan_integument_kulit=?,pemeriksaan_integument_dekubitas=?,pemeriksaan_muskuloskletal_pergerakan_sendi=?,pemeriksaan_muskuloskletal_kekauatan_otot=?,pemeriksaan_muskuloskletal_nyeri_sendi=?,pemeriksaan_muskuloskletal_nyeri_sendi_keterangan=?,pemeriksaan_muskuloskletal_oedema=?,pemeriksaan_muskuloskletal_oedema_keterangan=?,pemeriksaan_muskuloskletal_fraktur=?,pemeriksaan_muskuloskletal_fraktur_keterangan=?,pemeriksaan_eliminasi_bab_frekuensi_jumlah=?,pemeriksaan_eliminasi_bab_frekuensi_durasi=?,pemeriksaan_eliminasi_bab_konsistensi=?,pemeriksaan_eliminasi_bab_warna=?,pemeriksaan_eliminasi_bak_frekuensi_jumlah=?,pemeriksaan_eliminasi_bak_frekuensi_durasi=?,pemeriksaan_eliminasi_bak_warna=?,pemeriksaan_eliminasi_bak_lainlain=?,pola_aktifitas_makanminum=?,pola_aktifitas_mandi=?,pola_aktifitas_eliminasi=?,pola_aktifitas_berpakaian=?,pola_aktifitas_berpindah=?,pola_nutrisi_frekuesi_makan=?,pola_nutrisi_jenis_makanan=?,pola_nutrisi_porsi_makan=?,pola_tidur_lama_tidur=?,pola_tidur_gangguan=?,pengkajian_fungsi_kemampuan_sehari=?,pengkajian_fungsi_aktifitas=?,pengkajian_fungsi_berjalan=?,pengkajian_fungsi_berjalan_keterangan=?,pengkajian_fungsi_ambulasi=?,pengkajian_fungsi_ekstrimitas_atas=?,pengkajian_fungsi_ekstrimitas_atas_keterangan=?,pengkajian_fungsi_ekstrimitas_bawah=?,pengkajian_fungsi_ekstrimitas_bawah_keterangan=?,pengkajian_fungsi_menggenggam=?,pengkajian_fungsi_menggenggam_keterangan=?,pengkajian_fungsi_koordinasi=?,pengkajian_fungsi_koordinasi_keterangan=?,pengkajian_fungsi_kesimpulan=?,riwayat_psiko_kondisi_psiko=?,riwayat_psiko_gangguan_jiwa=?,riwayat_psiko_perilaku=?,riwayat_psiko_perilaku_keterangan=?,riwayat_psiko_hubungan_keluarga=?,riwayat_psiko_tinggal=?,riwayat_psiko_tinggal_keterangan=?,riwayat_psiko_nilai_kepercayaan=?,riwayat_psiko_nilai_kepercayaan_keterangan=?,riwayat_psiko_pendidikan_pj=?,riwayat_psiko_edukasi_diberikan=?,riwayat_psiko_edukasi_diberikan_keterangan=?,penilaian_nyeri=?,penilaian_nyeri_penyebab=?,penilaian_nyeri_ket_penyebab=?,penilaian_nyeri_kualitas=?,penilaian_nyeri_ket_kualitas=?,penilaian_nyeri_lokasi=?,penilaian_nyeri_menyebar=?,penilaian_nyeri_skala=?,penilaian_nyeri_waktu=?,penilaian_nyeri_hilang=?,penilaian_nyeri_ket_hilang=?,penilaian_nyeri_diberitahukan_dokter=?,penilaian_nyeri_jam_diberitahukan_dokter=?,penilaian_jatuhmorse_skala1=?,penilaian_jatuhmorse_nilai1=?,penilaian_jatuhmorse_skala2=?,penilaian_jatuhmorse_nilai2=?,penilaian_jatuhmorse_skala3=?,penilaian_jatuhmorse_nilai3=?,penilaian_jatuhmorse_skala4=?,penilaian_jatuhmorse_nilai4=?,penilaian_jatuhmorse_skala5=?,penilaian_jatuhmorse_nilai5=?,penilaian_jatuhmorse_skala6=?,penilaian_jatuhmorse_nilai6=?,penilaian_jatuhmorse_totalnilai=?,penilaian_jatuhsydney_skala1=?,penilaian_jatuhsydney_nilai1=?,penilaian_jatuhsydney_skala2=?,penilaian_jatuhsydney_nilai2=?,penilaian_jatuhsydney_skala3=?,penilaian_jatuhsydney_nilai3=?,penilaian_jatuhsydney_skala4=?,penilaian_jatuhsydney_nilai4=?,penilaian_jatuhsydney_skala5=?,penilaian_jatuhsydney_nilai5=?,penilaian_jatuhsydney_skala6=?,penilaian_jatuhsydney_nilai6=?,penilaian_jatuhsydney_skala7=?,penilaian_jatuhsydney_nilai7=?,penilaian_jatuhsydney_skala8=?,penilaian_jatuhsydney_nilai8=?,penilaian_jatuhsydney_skala9=?,penilaian_jatuhsydney_nilai9=?,penilaian_jatuhsydney_skala10=?,penilaian_jatuhsydney_nilai10=?,penilaian_jatuhsydney_skala11=?,penilaian_jatuhsydney_nilai11=?,penilaian_jatuhsydney_totalnilai=?,skrining_gizi1=?,nilai_gizi1=?,skrining_gizi2=?,nilai_gizi2=?,nilai_total_gizi=?,skrining_gizi_diagnosa_khusus=?,skrining_gizi_ket_diagnosa_khusus=?,skrining_gizi_diketahui_dietisen=?,skrining_gizi_jam_diketahui_dietisen=?,rencana=?,nip1=?,nip2=?,kd_dokter=?",194,new String[]{
                TNoRw.getText(),Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),Anamnesis.getSelectedItem().toString(),KetAnamnesis.getText(),TibadiRuang.getSelectedItem().toString(),MacamKasus.getSelectedItem().toString(), 
                    CaraMasuk.getSelectedItem().toString(),RPS.getText(),RPD.getText(),RPK.getText(),RPO.getText(),RPembedahan.getText(),RDirawatRS.getText(),AlatBantuDipakai.getSelectedItem().toString(),SedangMenyusui.getSelectedItem().toString(),KetSedangMenyusui.getText(),RTranfusi.getText(), 
                    Alergi.getText(),KebiasaanMerokok.getSelectedItem().toString(),KebiasaanJumlahRokok.getText(),KebiasaanAlkohol.getSelectedItem().toString(),KebiasaanJumlahAlkohol.getText(),KebiasaanNarkoba.getSelectedItem().toString(),OlahRaga.getSelectedItem().toString(),KesadaranMental.getText(), 
                    KeadaanMentalUmum.getSelectedItem().toString(),GCS.getText(),TD.getText(),Nadi.getText(),RR.getText(),Suhu.getText(),SpO2.getText(),BB.getText(),TB.getText(),SistemSarafKepala.getSelectedItem().toString(),KetSistemSarafKepala.getText(),SistemSarafWajah.getSelectedItem().toString(), 
                    KetSistemSarafWajah.getText(),SistemSarafLeher.getSelectedItem().toString(),SistemSarafKejang.getSelectedItem().toString(),KetSistemSarafKejang.getText(),SistemSarafSensorik.getSelectedItem().toString(),KardiovaskularDenyutNadi.getSelectedItem().toString(),KardiovaskularSirkulasi.getSelectedItem().toString(), 
                    KetKardiovaskularSirkulasi.getText(),KardiovaskularPulsasi.getSelectedItem().toString(),RespirasiPolaNafas.getSelectedItem().toString(),RespirasiRetraksi.getSelectedItem().toString(),RespirasiSuaraNafas.getSelectedItem().toString(),RespirasiVolume.getSelectedItem().toString(),
                    RespirasiJenisPernafasan.getSelectedItem().toString(),KetRespirasiJenisPernafasan.getText(),RespirasiIrama.getSelectedItem().toString(),RespirasiBatuk.getSelectedItem().toString(),GastrointestinalMulut.getSelectedItem().toString(),KetGastrointestinalMulut.getText(),
                    GastrointestinalGigi.getSelectedItem().toString(),KetGastrointestinalGigi.getText(),GastrointestinalLidah.getSelectedItem().toString(),KetGastrointestinalLidah.getText(),GastrointestinalTenggorakan.getSelectedItem().toString(),KetGastrointestinalTenggorakan.getText(), 
                    GastrointestinalAbdomen.getSelectedItem().toString(),KetGastrointestinalAbdomen.getText(),GastrointestinalUsus.getSelectedItem().toString(),GastrointestinalAnus.getSelectedItem().toString(),NeurologiPenglihatan.getSelectedItem().toString(),KetNeurologiPenglihatan.getText(), 
                    NeurologiAlatBantuPenglihatan.getSelectedItem().toString(),NeurologiPendengaran.getSelectedItem().toString(),NeurologiBicara.getSelectedItem().toString(),KetNeurologiBicara.getText(),NeurologiSensorik.getSelectedItem().toString(),NeurologiMotorik.getSelectedItem().toString(), 
                    NeurologiOtot.getSelectedItem().toString(),IntegumentWarnaKulit.getSelectedItem().toString(),IntegumentTurgor.getSelectedItem().toString(),IntegumentKulit.getSelectedItem().toString(),IntegumentDecubitus.getSelectedItem().toString(),MuskuloskletalPegerakanSendi.getSelectedItem().toString(), 
                    MuskuloskletalKekuatanOtot.getSelectedItem().toString(),MuskuloskletalNyeriSendi.getSelectedItem().toString(),KetMuskuloskletalNyeriSendi.getText(),MuskuloskletalOedema.getSelectedItem().toString(),KetMuskuloskletalOedema.getText(),MuskuloskletalFraktur.getSelectedItem().toString(), 
                    KetMuskuloskletalFraktur.getText(),BAB.getText(),XBAB.getText(),KBAB.getText(),WBAB.getText(),BAK.getText(),XBAK.getText(),WBAK.getText(),LBAK.getText(),PolaAktifitasMakan.getSelectedItem().toString(),PolaAktifitasMandi.getSelectedItem().toString(),PolaAktifitasEliminasi.getSelectedItem().toString(), 
                    PolaAktifitasBerpakaian.getSelectedItem().toString(),PolaAktifitasBerpindah.getSelectedItem().toString(),PolaNutrisiFrekuensi.getText(),PolaNutrisiJenis.getText(),PolaNutrisiPorsi.getText(),PolaTidurLama.getText(),PolaTidurGangguan.getSelectedItem().toString(),AktifitasSehari2.getSelectedItem().toString(), 
                    Aktifitas.getSelectedItem().toString(),Berjalan.getSelectedItem().toString(),KeteranganBerjalan.getText(),AlatAmbulasi.getSelectedItem().toString(),EkstrimitasAtas.getSelectedItem().toString(),KeteranganEkstrimitasAtas.getText(),EkstrimitasBawah.getSelectedItem().toString(),
                    KeteranganEkstrimitasBawah.getText(),KemampuanMenggenggam.getSelectedItem().toString(),KeteranganKemampuanMenggenggam.getText(),KemampuanKoordinasi.getSelectedItem().toString(),KeteranganKemampuanKoordinasi.getText(),KesimpulanGangguanFungsi.getSelectedItem().toString(),
                    KondisiPsikologis.getSelectedItem().toString(),GangguanJiwa.getSelectedItem().toString(),AdakahPerilaku.getSelectedItem().toString(),KeteranganAdakahPerilaku.getText(),HubunganAnggotaKeluarga.getSelectedItem().toString(),TinggalDengan.getSelectedItem().toString(),KeteranganTinggalDengan.getText(),
                    NilaiKepercayaan.getSelectedItem().toString(),KeteranganNilaiKepercayaan.getText(),PendidikanPJ.getSelectedItem().toString(),EdukasiPsikolgis.getSelectedItem().toString(),KeteranganEdukasiPsikologis.getText(),Nyeri.getSelectedItem().toString(),Provokes.getSelectedItem().toString(),KetProvokes.getText(), 
                    Quality.getSelectedItem().toString(),KetQuality.getText(),Lokasi.getText(),Menyebar.getSelectedItem().toString(),SkalaNyeri.getSelectedItem().toString(),Durasi.getText(),NyeriHilang.getSelectedItem().toString(),KetNyeri.getText(),PadaDokter.getSelectedItem().toString(), 
                    KetPadaDokter.getText(),SkalaResiko1.getSelectedItem().toString(),NilaiResiko1.getText(),SkalaResiko2.getSelectedItem().toString(),NilaiResiko2.getText(),SkalaResiko3.getSelectedItem().toString(),NilaiResiko3.getText(),SkalaResiko4.getSelectedItem().toString(),NilaiResiko4.getText(), 
                    SkalaResiko5.getSelectedItem().toString(),NilaiResiko5.getText(),SkalaResiko6.getSelectedItem().toString(),NilaiResiko6.getText(),NilaiResikoTotal.getText(),SkalaSydney1.getSelectedItem().toString(),NilaiSydney1.getText(),SkalaSydney2.getSelectedItem().toString(),NilaiSydney2.getText(),
                    SkalaSydney3.getSelectedItem().toString(),NilaiSydney3.getText(),SkalaSydney4.getSelectedItem().toString(),NilaiSydney4.getText(),SkalaSydney5.getSelectedItem().toString(),NilaiSydney5.getText(),SkalaSydney6.getSelectedItem().toString(),NilaiSydney6.getText(), 
                    SkalaSydney7.getSelectedItem().toString(),NilaiSydney7.getText(),SkalaSydney8.getSelectedItem().toString(),NilaiSydney8.getText(),SkalaSydney9.getSelectedItem().toString(),NilaiSydney9.getText(),SkalaSydney10.getSelectedItem().toString(),NilaiSydney10.getText(), 
                    SkalaSydney11.getSelectedItem().toString(),NilaiSydney11.getText(),NilaiSydneyTotal.getText(),SkalaGizi1.getSelectedItem().toString(),NilaiGizi1.getText(),SkalaGizi2.getSelectedItem().toString(),NilaiGizi2.getText(),NilaiGiziTotal.getText(),DiagnosaKhususGizi.getSelectedItem().toString(),
                    KeteranganDiagnosaKhususGizi.getText(),DiketahuiDietisen.getSelectedItem().toString(),KeteranganDiketahuiDietisen.getText(),Rencana.getText(),KdPetugas.getText(),KdPetugas2.getText(),KdDPJP.getText(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
             })==true){
                Sequel.meghapus("penilaian_awal_keperawatan_ranap_masalah","no_rawat",tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
                for (i = 0; i < tbMasalahKeperawatan.getRowCount(); i++) {
                    if(tbMasalahKeperawatan.getValueAt(i,0).toString().equals("true")){
                        Sequel.menyimpan2("penilaian_awal_keperawatan_ranap_masalah","?,?",2,new String[]{TNoRw.getText(),tbMasalahKeperawatan.getValueAt(i,1).toString()});
                    }
                }
                Sequel.meghapus("penilaian_awal_keperawatan_ranap_rencana","no_rawat",tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
                for (i = 0; i < tbRencanaKeperawatan.getRowCount(); i++) {
                    if(tbRencanaKeperawatan.getValueAt(i,0).toString().equals("true")){
                        Sequel.menyimpan2("penilaian_awal_keperawatan_ranap_rencana","?,?",2,new String[]{TNoRw.getText(),tbRencanaKeperawatan.getValueAt(i,1).toString()});
                    }
                }
                getMasalah();
                tampil();
                DetailRencana.setText(Rencana.getText());
                emptTeks();
                TabRawat.setSelectedIndex(1);
        }*/
    }
    
    private void tampilMasalah() {
        try{
            Valid.tabelKosong(tabModeMasalah);
            file=new File("./cache/masalahkeperawatanneonatus.iyem");
            file.createNewFile();
            fileWriter = new FileWriter(file);
            iyem="";
            ps=koneksi.prepareStatement("select * from master_masalah_keperawatan_neonatus order by master_masalah_keperawatan_neonatus.kode_masalah");
            try {
                rs=ps.executeQuery();
                while(rs.next()){
                    tabModeMasalah.addRow(new Object[]{false,rs.getString(1),rs.getString(2)});
                    iyem=iyem+"{\"KodeMasalah\":\""+rs.getString(1)+"\",\"NamaMasalah\":\""+rs.getString(2)+"\"},";
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
            fileWriter.write("{\"masalahkeperawatan\":["+iyem.substring(0,iyem.length()-1)+"]}");
            fileWriter.flush();
            fileWriter.close();
            iyem=null;
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }
    
    private void tampilMasalah2() {
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
            
            myObj = new FileReader("./cache/masalahkeperawatanneonatus.iyem");
            root = mapper.readTree(myObj);
            response = root.path("masalahkeperawatan");
            if(response.isArray()){
                for(JsonNode list:response){
                    if(list.path("KodeMasalah").asText().toLowerCase().contains(TCariMasalah.getText().toLowerCase())||list.path("NamaMasalah").asText().toLowerCase().contains(TCariMasalah.getText().toLowerCase())){
                        tabModeMasalah.addRow(new Object[]{
                            false,list.path("KodeMasalah").asText(),list.path("NamaMasalah").asText()
                        });                    
                    }
                }
            }
            myObj.close();
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }
    
    private void tampilRencana() {
        try{
            file=new File("./cache/rencanakeperawatanneonatus.iyem");
            file.createNewFile();
            fileWriter = new FileWriter(file);
            iyem="";
            ps=koneksi.prepareStatement("select * from master_rencana_keperawatan_neonatus order by master_rencana_keperawatan_neonatus.kode_rencana");
            try {
                rs=ps.executeQuery();
                while(rs.next()){
                    iyem=iyem+"{\"KodeMasalah\":\""+rs.getString(1)+"\",\"KodeRencana\":\""+rs.getString(2)+"\",\"NamaRencana\":\""+rs.getString(3)+"\"},";
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
            fileWriter.write("{\"rencanakeperawatan\":["+iyem.substring(0,iyem.length()-1)+"]}");
            fileWriter.flush();
            fileWriter.close();
            iyem=null;
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }
    
    private void tampilRencana2() {
        try{
            jml=0;
            for(i=0;i<tbRencanaKeperawatan.getRowCount();i++){
                if(tbRencanaKeperawatan.getValueAt(i,0).toString().equals("true")){
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
            for(i=0;i<tbRencanaKeperawatan.getRowCount();i++){
                if(tbRencanaKeperawatan.getValueAt(i,0).toString().equals("true")){
                    pilih[index]=true;
                    kode[index]=tbRencanaKeperawatan.getValueAt(i,1).toString();
                    masalah[index]=tbRencanaKeperawatan.getValueAt(i,2).toString();
                    index++;
                }
            } 

            Valid.tabelKosong(tabModeRencana);

            for(i=0;i<jml;i++){
                tabModeRencana.addRow(new Object[] {
                    pilih[i],kode[i],masalah[i]
                });
            }

            myObj = new FileReader("./cache/rencanakeperawatanneonatus.iyem");
            root = mapper.readTree(myObj);
            response = root.path("rencanakeperawatan");
            if(response.isArray()){
                for(i=0;i<tbMasalahKeperawatan.getRowCount();i++){
                    if(tbMasalahKeperawatan.getValueAt(i,0).toString().equals("true")){
                        for(JsonNode list:response){
                            if(list.path("KodeMasalah").asText().toLowerCase().equals(tbMasalahKeperawatan.getValueAt(i,1).toString())&&
                                    list.path("NamaRencana").asText().toLowerCase().contains(TCariRencana.getText().toLowerCase())){
                                tabModeRencana.addRow(new Object[]{
                                    false,list.path("KodeRencana").asText(),list.path("NamaRencana").asText()
                                });                    
                            }
                        }
                    }
                }
            }
            myObj.close();
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }
}

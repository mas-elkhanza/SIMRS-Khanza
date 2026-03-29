/*
 * Kontribusi dari Abdul Wahid, RSUD Cipayung Jakarta Timur
 */


package pcraicra;

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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
import kepegawaian.DlgCariDokter;


/**
 *
 * @author perpustakaan
 */
public final class PCRAICRAPengkajianRisikoPraKonstruksi extends javax.swing.JDialog {
    private final DefaultTableModel tabMode,tabModeKelompokRisikoArea,tabModeIdentifikasiRisikoKebakaran,tabModeIdentifikasiRisikoInfeksi,
            tabModeIdentifikasiRisikoKeselamatan,tabModeIdentifikasiRisikoUtilitas,tabModeTindakanPengendalian,tabModePersyaratanDipenuhi;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0,jml=0,index=0;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private volatile boolean ceksukses = false;
    private StringBuilder htmlContent;
    private String finger="";
    private String TANGGALMUNDUR="yes";
    private File file;
    private FileWriter fileWriter;
    private ObjectMapper mapper = new ObjectMapper();
    private JsonNode root;
    private JsonNode response;
    private FileReader myObj;
    private boolean[] pilih; 
    private String[] kode,nama;
    private PCRAICRALokasiKelompokRisikoArea kelompokrisikoarea;
    private PCRAICRAIdentifikasiRisikoKebakaran identifikasirisikokebakaran;
    private PCRAICRAIdentifikasiRisikoInfeksi identifikasirisikoinfeksi;
    private PCRAICRAIdentifikasiRisikoKeselamatan identifikasirisikokeselamatan;
    private PCRAICRAIdentifikasiRisikoUtilitas identifikasirisikoutilitas;
    private PCRAICRATindakanPengendalian tindakanpengendalian;
    private PCRAICRAPersyaratanHarusDipenuhi persyaratandipenuhi;
    
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public PCRAICRAPengkajianRisikoPraKonstruksi(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.RM","Nama Pasien","Tgl.Lahir","J.K.","Kode Dokter","Nama Dokter","Tanggal","Anamnesis","Hubungan","Keluhan Utama","Riwayat Penyakit Sekarang","Riwayat Penyakit Dahulu",
            "Riwayat Penyakit Keluarga","Riwayat Penggunakan Obat","Riwayat Alergi","Keadaan Umum","GCS","Kesadaran","TD(mmHg)","Nadi(x/menit)","RR(x/menit)","Suhu","SpO2","BB(Kg)","TB(cm)","Kepala",
            "Gigi & Mulut","THT","Thoraks","Abdomen","Genital & Anus","Ekstremitas","Kulit","Ket.Pemeriksaan Fisik","Ket.Status Lokalis","Pemeriksaan Penunjang","Diagnosis/Asesmen",
            "Tatalaksana","Konsul/Rujuk"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        
        tbObat.setModel(tabMode);
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 40; i++) {
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
                column.setPreferredWidth(120);
            }else if(i==16){
                column.setPreferredWidth(90);
            }else if(i==17){
                column.setPreferredWidth(50);
            }else if(i==18){
                column.setPreferredWidth(80);
            }else if(i==19){
                column.setPreferredWidth(60);
            }else if(i==20){
                column.setPreferredWidth(75);
            }else if(i==21){
                column.setPreferredWidth(67);
            }else if(i==22){
                column.setPreferredWidth(40);
            }else if(i==23){
                column.setPreferredWidth(40);
            }else if(i==24){
                column.setPreferredWidth(40);
            }else if(i==25){
                column.setPreferredWidth(40);
            }else if(i==26){
                column.setPreferredWidth(80);
            }else if(i==27){
                column.setPreferredWidth(80);
            }else if(i==28){
                column.setPreferredWidth(80);
            }else if(i==29){
                column.setPreferredWidth(80);
            }else if(i==30){
                column.setPreferredWidth(80);
            }else if(i==31){
                column.setPreferredWidth(80);
            }else if(i==32){
                column.setPreferredWidth(80);
            }else if(i==33){
                column.setPreferredWidth(80);
            }else if(i==34){
                column.setPreferredWidth(300);
            }else if(i==35){
                column.setPreferredWidth(200);
            }else if(i==36){
                column.setPreferredWidth(170);
            }else if(i==37){
                column.setPreferredWidth(150);
            }else if(i==38){
                column.setPreferredWidth(300);
            }else if(i==39){
                column.setPreferredWidth(150);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeKelompokRisikoArea=new DefaultTableModel(null,new Object[]{"P","Kode Area","Nama Lokasi & Kelompok Risiko Area"}){
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

        tbKelompokRisikoArea.setModel(tabModeKelompokRisikoArea);
        tbKelompokRisikoArea.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbKelompokRisikoArea.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 3; i++) {
            TableColumn column = tbKelompokRisikoArea.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==2){
                column.setPreferredWidth(350);
            }
        }
        tbKelompokRisikoArea.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeIdentifikasiRisikoKebakaran=new DefaultTableModel(null,new Object[]{"P","Kode","Risiko Kebakaran"}){
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

        tbIdentifikasiRisikoKebakaran.setModel(tabModeIdentifikasiRisikoKebakaran);
        tbIdentifikasiRisikoKebakaran.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbIdentifikasiRisikoKebakaran.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 3; i++) {
            TableColumn column = tbIdentifikasiRisikoKebakaran.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==2){
                column.setPreferredWidth(350);
            }
        }
        tbIdentifikasiRisikoKebakaran.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeIdentifikasiRisikoInfeksi=new DefaultTableModel(null,new Object[]{"P","Kode","Risiko Infeksi"}){
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

        tbIdentifikasiRisikoInfeksi.setModel(tabModeIdentifikasiRisikoInfeksi);
        tbIdentifikasiRisikoInfeksi.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbIdentifikasiRisikoInfeksi.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 3; i++) {
            TableColumn column = tbIdentifikasiRisikoInfeksi.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==2){
                column.setPreferredWidth(350);
            }
        }
        tbIdentifikasiRisikoInfeksi.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeIdentifikasiRisikoKeselamatan=new DefaultTableModel(null,new Object[]{"P","Kode","Risiko Keselamatan"}){
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

        tbIdentifikasiRisikoKeselamatan.setModel(tabModeIdentifikasiRisikoKeselamatan);
        tbIdentifikasiRisikoKeselamatan.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbIdentifikasiRisikoKeselamatan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 3; i++) {
            TableColumn column = tbIdentifikasiRisikoKeselamatan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==2){
                column.setPreferredWidth(350);
            }
        }
        tbIdentifikasiRisikoKeselamatan.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeIdentifikasiRisikoUtilitas=new DefaultTableModel(null,new Object[]{"P","Kode","Risiko Utilitas"}){
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

        tbIdentifikasiRisikoUtilitas.setModel(tabModeIdentifikasiRisikoUtilitas);
        tbIdentifikasiRisikoUtilitas.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbIdentifikasiRisikoUtilitas.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 3; i++) {
            TableColumn column = tbIdentifikasiRisikoUtilitas.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==2){
                column.setPreferredWidth(350);
            }
        }
        tbIdentifikasiRisikoUtilitas.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeTindakanPengendalian=new DefaultTableModel(null,new Object[]{"P","Kode","Tindakan Pengendalian"}){
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

        tbTindakanPengendalian.setModel(tabModeTindakanPengendalian);
        tbTindakanPengendalian.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbTindakanPengendalian.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 3; i++) {
            TableColumn column = tbTindakanPengendalian.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==2){
                column.setPreferredWidth(350);
            }
        }
        tbTindakanPengendalian.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModePersyaratanDipenuhi=new DefaultTableModel(null,new Object[]{"P","Kode","Persyaratan Yang Harus Dipenuhi"}){
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

        tbPersyaratanDipenuhi.setModel(tabModePersyaratanDipenuhi);
        tbPersyaratanDipenuhi.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbPersyaratanDipenuhi.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 3; i++) {
            TableColumn column = tbPersyaratanDipenuhi.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==2){
                column.setPreferredWidth(750);
            }
        }
        tbPersyaratanDipenuhi.setDefaultRenderer(Object.class, new WarnaTable());
        
        NoProyek.setDocument(new batasInput((byte)17).getKata(NoProyek));
        Hubungan.setDocument(new batasInput((int)30).getKata(Hubungan));
        KeluhanUtama.setDocument(new batasInput((int)2000).getKata(KeluhanUtama));
        RPS.setDocument(new batasInput((int)2000).getKata(RPS));
        TCari.setDocument(new batasInput((int)100).getKata(TCari));
        TCariKelompokRisikoArea.setDocument(new batasInput((int)100).getKata(TCariKelompokRisikoArea));
        TCariRisikoKebakaran.setDocument(new batasInput((int)100).getKata(TCariRisikoKebakaran));
        TCariRisikoInfeksi.setDocument(new batasInput((int)100).getKata(TCariRisikoInfeksi));
        TCariRisikoKeselamatan.setDocument(new batasInput((int)100).getKata(TCariRisikoKeselamatan));
        TCariRisikoUtilitas.setDocument(new batasInput((int)100).getKata(TCariRisikoUtilitas));
        TCariTindakanPengendalian.setDocument(new batasInput((int)100).getKata(TCariTindakanPengendalian));
        TCariPersyaratanDipenuhi.setDocument(new batasInput((int)100).getKata(TCariPersyaratanDipenuhi));
        
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
        TanggalRegistrasi = new widget.TextBox();
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
        NoProyek = new widget.TextBox();
        NamaProyek = new widget.TextBox();
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
        Anamnesis = new widget.ComboBox();
        scrollPane1 = new widget.ScrollPane();
        KeluhanUtama = new widget.TextArea();
        jLabel30 = new widget.Label();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel38 = new widget.Label();
        Hubungan = new widget.TextBox();
        scrollPane7 = new widget.ScrollPane();
        RPS = new widget.TextArea();
        label11 = new widget.Label();
        TglAsuhan = new widget.Tanggal();
        jLabel34 = new widget.Label();
        jLabel12 = new widget.Label();
        jLabel13 = new widget.Label();
        LokasiProyek = new widget.TextBox();
        label12 = new widget.Label();
        TglAsuhan1 = new widget.Tanggal();
        jLabel14 = new widget.Label();
        jLabel36 = new widget.Label();
        jLabel37 = new widget.Label();
        jLabel39 = new widget.Label();
        jLabel40 = new widget.Label();
        BtnDokter1 = new widget.Button();
        NmDokter1 = new widget.TextBox();
        KdDokter1 = new widget.TextBox();
        label15 = new widget.Label();
        jLabel41 = new widget.Label();
        label16 = new widget.Label();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel42 = new widget.Label();
        jLabel43 = new widget.Label();
        Scroll6 = new widget.ScrollPane();
        tbKelompokRisikoArea = new widget.Table();
        label13 = new widget.Label();
        TCariKelompokRisikoArea = new widget.TextBox();
        BtnCariKelompokRisiko = new widget.Button();
        BtnAllKelomokRisiko = new widget.Button();
        BtnTambahMasalah = new widget.Button();
        label17 = new widget.Label();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel44 = new widget.Label();
        Scroll7 = new widget.ScrollPane();
        tbIdentifikasiRisikoInfeksi = new widget.Table();
        label18 = new widget.Label();
        TCariRisikoInfeksi = new widget.TextBox();
        BtnCariRisikoInfeksi = new widget.Button();
        BtnAllRisikoInfeksi = new widget.Button();
        BtnTambahRisikoInfeksi = new widget.Button();
        jLabel45 = new widget.Label();
        Scroll8 = new widget.ScrollPane();
        tbIdentifikasiRisikoKebakaran = new widget.Table();
        label19 = new widget.Label();
        TCariRisikoKebakaran = new widget.TextBox();
        BtnCariRisikoKebakarab = new widget.Button();
        BtnAllRisikoKebakaran = new widget.Button();
        BtnTambahRisikoKebakaran = new widget.Button();
        jLabel46 = new widget.Label();
        Scroll9 = new widget.ScrollPane();
        tbIdentifikasiRisikoKeselamatan = new widget.Table();
        BtnTambahRisikoKeselamatan = new widget.Button();
        BtnAllRisikoKeselamatan = new widget.Button();
        BtnCariRisikoKeselamatan = new widget.Button();
        TCariRisikoKeselamatan = new widget.TextBox();
        label20 = new widget.Label();
        jLabel47 = new widget.Label();
        Scroll10 = new widget.ScrollPane();
        tbIdentifikasiRisikoUtilitas = new widget.Table();
        BtnTambahRisikoUtilitas = new widget.Button();
        BtnAllRisikoUtilitas = new widget.Button();
        BtnCariRisikoUtilitas = new widget.Button();
        TCariRisikoUtilitas = new widget.TextBox();
        label21 = new widget.Label();
        label22 = new widget.Label();
        jSeparator4 = new javax.swing.JSeparator();
        KdDokter2 = new widget.TextBox();
        NmDokter2 = new widget.TextBox();
        BtnDokter2 = new widget.Button();
        jLabel48 = new widget.Label();
        jLabel49 = new widget.Label();
        label23 = new widget.Label();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel50 = new widget.Label();
        Scroll11 = new widget.ScrollPane();
        tbTindakanPengendalian = new widget.Table();
        label24 = new widget.Label();
        TCariTindakanPengendalian = new widget.TextBox();
        BtnCariTindakanPengendalian = new widget.Button();
        BtnAllTindakanPengendalian = new widget.Button();
        BtnTambahTindakanPengendalian = new widget.Button();
        jLabel51 = new widget.Label();
        scrollPane2 = new widget.ScrollPane();
        KeluhanUtama1 = new widget.TextArea();
        jLabel52 = new widget.Label();
        scrollPane3 = new widget.ScrollPane();
        KeluhanUtama2 = new widget.TextArea();
        jLabel31 = new widget.Label();
        scrollPane8 = new widget.ScrollPane();
        RPS1 = new widget.TextArea();
        label25 = new widget.Label();
        jSeparator6 = new javax.swing.JSeparator();
        jLabel53 = new widget.Label();
        Scroll12 = new widget.ScrollPane();
        tbPersyaratanDipenuhi = new widget.Table();
        BtnTambahPersyaratanDipenuhi = new widget.Button();
        BtnAllPersyaratanDipenuhi = new widget.Button();
        BtnCariPersyaratanDipenuhi = new widget.Button();
        TCariPersyaratanDipenuhi = new widget.TextBox();
        label26 = new widget.Label();
        label27 = new widget.Label();
        jSeparator7 = new javax.swing.JSeparator();
        scrollPane9 = new widget.ScrollPane();
        RPS2 = new widget.TextArea();
        label28 = new widget.Label();
        jSeparator8 = new javax.swing.JSeparator();
        label29 = new widget.Label();
        KdDokter3 = new widget.TextBox();
        NmDokter3 = new widget.TextBox();
        BtnDokter3 = new widget.Button();
        label30 = new widget.Label();
        NmDokter4 = new widget.TextBox();
        BtnDokter4 = new widget.Button();
        Hubungan1 = new widget.TextBox();
        BtnDokter5 = new widget.Button();
        NmDokter5 = new widget.TextBox();
        label31 = new widget.Label();
        TglAsuhan2 = new widget.Tanggal();
        label32 = new widget.Label();
        jLabel15 = new widget.Label();
        Jk1 = new widget.TextBox();
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

        TanggalRegistrasi.setHighlighter(null);
        TanggalRegistrasi.setName("TanggalRegistrasi"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Pengkajian Risiko Pra Konstruksi/PCRA ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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

        internalFrame2.setBorder(null);
        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setLayout(new java.awt.BorderLayout(1, 1));

        scrollInput.setName("scrollInput"); // NOI18N
        scrollInput.setPreferredSize(new java.awt.Dimension(102, 557));

        FormInput.setBackground(new java.awt.Color(255, 255, 255));
        FormInput.setBorder(null);
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(870, 1533));
        FormInput.setLayout(null);

        NoProyek.setHighlighter(null);
        NoProyek.setName("NoProyek"); // NOI18N
        NoProyek.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoProyekKeyPressed(evt);
            }
        });
        FormInput.add(NoProyek);
        NoProyek.setBounds(74, 10, 131, 23);

        NamaProyek.setHighlighter(null);
        NamaProyek.setName("NamaProyek"); // NOI18N
        FormInput.add(NamaProyek);
        NamaProyek.setBounds(404, 10, 450, 23);

        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        FormInput.add(TNoRM);
        TNoRM.setBounds(280, 70, 574, 23);

        label14.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label14.setText("I. JENIS AKTIVITAS PROYEK");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label14);
        label14.setBounds(15, 130, 310, 23);

        KdDokter.setEditable(false);
        KdDokter.setName("KdDokter"); // NOI18N
        KdDokter.setPreferredSize(new java.awt.Dimension(80, 23));
        KdDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdDokterKeyPressed(evt);
            }
        });
        FormInput.add(KdDokter);
        KdDokter.setBounds(414, 150, 78, 23);

        NmDokter.setEditable(false);
        NmDokter.setName("NmDokter"); // NOI18N
        NmDokter.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmDokter);
        NmDokter.setBounds(494, 150, 330, 23);

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
        BtnDokter.setBounds(826, 150, 28, 23);

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel8.setText("Siapa yang bertanggung jawab");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(15, 100, 160, 23);

        TglLahir.setHighlighter(null);
        TglLahir.setName("TglLahir"); // NOI18N
        FormInput.add(TglLahir);
        TglLahir.setBounds(177, 100, 245, 23);

        Jk.setHighlighter(null);
        Jk.setName("Jk"); // NOI18N
        FormInput.add(Jk);
        Jk.setBounds(609, 100, 245, 23);

        jLabel10.setText("Proyek apa yang akan dikerjakan ?");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(210, 10, 190, 23);

        jLabel11.setText("Siapa pelaksana/kontraktornya ?");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(425, 100, 180, 23);

        Anamnesis.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Anamnesis.setName("Anamnesis"); // NOI18N
        Anamnesis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AnamnesisKeyPressed(evt);
            }
        });
        FormInput.add(Anamnesis);
        Anamnesis.setBounds(764, 830, 90, 23);

        scrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane1.setName("scrollPane1"); // NOI18N

        KeluhanUtama.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        KeluhanUtama.setColumns(20);
        KeluhanUtama.setRows(10);
        KeluhanUtama.setName("KeluhanUtama"); // NOI18N
        KeluhanUtama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeluhanUtamaKeyPressed(evt);
            }
        });
        scrollPane1.setViewportView(KeluhanUtama);

        FormInput.add(scrollPane1);
        scrollPane1.setBounds(40, 220, 390, 143);

        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel30.setText("Adakah penyebab risiko lainnya yang mungkin terjadi ?");
        jLabel30.setName("jLabel30"); // NOI18N
        FormInput.add(jLabel30);
        jLabel30.setBounds(40, 730, 310, 23);

        jSeparator1.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator1.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator1.setName("jSeparator1"); // NOI18N
        FormInput.add(jSeparator1);
        jSeparator1.setBounds(0, 130, 880, 1);

        jLabel38.setText("Apakah dibutuhkan ICRA ?");
        jLabel38.setName("jLabel38"); // NOI18N
        FormInput.add(jLabel38);
        jLabel38.setBounds(600, 830, 160, 23);

        Hubungan.setEditable(false);
        Hubungan.setName("Hubungan"); // NOI18N
        Hubungan.setPreferredSize(new java.awt.Dimension(207, 23));
        Hubungan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HubunganKeyPressed(evt);
            }
        });
        FormInput.add(Hubungan);
        Hubungan.setBounds(522, 1440, 105, 23);

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
        scrollPane7.setBounds(40, 750, 814, 53);

        label11.setText("Kapan dimulai ?");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label11);
        label11.setBounds(468, 40, 90, 23);

        TglAsuhan.setForeground(new java.awt.Color(50, 70, 50));
        TglAsuhan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "26-03-2026" }));
        TglAsuhan.setDisplayFormat("dd-MM-yyyy");
        TglAsuhan.setName("TglAsuhan"); // NOI18N
        TglAsuhan.setOpaque(false);
        TglAsuhan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglAsuhanKeyPressed(evt);
            }
        });
        FormInput.add(TglAsuhan);
        TglAsuhan.setBounds(562, 40, 90, 23);

        jLabel34.setText(":");
        jLabel34.setName("jLabel34"); // NOI18N
        FormInput.add(jLabel34);
        jLabel34.setBounds(0, 10, 70, 23);

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel12.setText("Dimana lokasinya");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(15, 40, 110, 23);

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel13.setText("No.Proyek");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(15, 10, 70, 23);

        LokasiProyek.setHighlighter(null);
        LokasiProyek.setName("LokasiProyek"); // NOI18N
        LokasiProyek.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LokasiProyekKeyPressed(evt);
            }
        });
        FormInput.add(LokasiProyek);
        LokasiProyek.setBounds(110, 40, 353, 23);

        label12.setText("Perkiraan selesai ?");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label12);
        label12.setBounds(650, 40, 110, 23);

        TglAsuhan1.setForeground(new java.awt.Color(50, 70, 50));
        TglAsuhan1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "26-03-2026" }));
        TglAsuhan1.setDisplayFormat("dd-MM-yyyy");
        TglAsuhan1.setName("TglAsuhan1"); // NOI18N
        TglAsuhan1.setOpaque(false);
        TglAsuhan1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglAsuhan1KeyPressed(evt);
            }
        });
        FormInput.add(TglAsuhan1);
        TglAsuhan1.setBounds(764, 40, 90, 23);

        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel14.setText("Deskripsi pekerjaan yang akan dilakukan seperti apa");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(15, 70, 270, 23);

        jLabel36.setText("?");
        jLabel36.setName("jLabel36"); // NOI18N
        FormInput.add(jLabel36);
        jLabel36.setBounds(0, 40, 106, 23);

        jLabel37.setText("?");
        jLabel37.setName("jLabel37"); // NOI18N
        FormInput.add(jLabel37);
        jLabel37.setBounds(0, 70, 276, 23);

        jLabel39.setText("?");
        jLabel39.setName("jLabel39"); // NOI18N
        FormInput.add(jLabel39);
        jLabel39.setBounds(0, 100, 173, 23);

        jLabel40.setText("!");
        jLabel40.setName("jLabel40"); // NOI18N
        FormInput.add(jLabel40);
        jLabel40.setBounds(0, 150, 410, 23);

        BtnDokter1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter1.setMnemonic('2');
        BtnDokter1.setToolTipText("Alt+2");
        BtnDokter1.setName("BtnDokter1"); // NOI18N
        BtnDokter1.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDokter1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokter1ActionPerformed(evt);
            }
        });
        BtnDokter1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnDokter1KeyPressed(evt);
            }
        });
        FormInput.add(BtnDokter1);
        BtnDokter1.setBounds(418, 1440, 28, 23);

        NmDokter1.setEditable(false);
        NmDokter1.setName("NmDokter1"); // NOI18N
        NmDokter1.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmDokter1);
        NmDokter1.setBounds(221, 1440, 195, 23);

        KdDokter1.setEditable(false);
        KdDokter1.setName("KdDokter1"); // NOI18N
        KdDokter1.setPreferredSize(new java.awt.Dimension(80, 23));
        KdDokter1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdDokter1KeyPressed(evt);
            }
        });
        FormInput.add(KdDokter1);
        KdDokter1.setBounds(114, 1440, 105, 23);

        label15.setText("Tim K3 :");
        label15.setName("label15"); // NOI18N
        label15.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label15);
        label15.setBounds(0, 1440, 110, 23);

        jLabel41.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel41.setText("Seperti apa aktivitas proyek yang akan dilakukan ? silahkan pilih salah satu");
        jLabel41.setName("jLabel41"); // NOI18N
        FormInput.add(jLabel41);
        jLabel41.setBounds(40, 150, 450, 23);

        label16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label16.setText("II. DESKRIPSI LOKASI PROYEK & KELOMPOK RISIKO AREA");
        label16.setName("label16"); // NOI18N
        label16.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label16);
        label16.setBounds(15, 180, 310, 23);

        jSeparator2.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator2.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator2.setName("jSeparator2"); // NOI18N
        FormInput.add(jSeparator2);
        jSeparator2.setBounds(0, 180, 880, 1);

        jLabel42.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel42.setText("Di mana lokasi proyek ini? Jelaskan area ini dan sekitarnya :");
        jLabel42.setName("jLabel42"); // NOI18N
        FormInput.add(jLabel42);
        jLabel42.setBounds(40, 200, 450, 23);

        jLabel43.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel43.setText("Kelompok area mana saja yang terdampak ?");
        jLabel43.setName("jLabel43"); // NOI18N
        FormInput.add(jLabel43);
        jLabel43.setBounds(464, 200, 390, 23);

        Scroll6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 253)));
        Scroll6.setName("Scroll6"); // NOI18N
        Scroll6.setOpaque(true);

        tbKelompokRisikoArea.setName("tbKelompokRisikoArea"); // NOI18N
        Scroll6.setViewportView(tbKelompokRisikoArea);

        FormInput.add(Scroll6);
        Scroll6.setBounds(464, 220, 390, 113);

        label13.setText("Key Word :");
        label13.setName("label13"); // NOI18N
        label13.setPreferredSize(new java.awt.Dimension(60, 23));
        FormInput.add(label13);
        label13.setBounds(472, 340, 60, 23);

        TCariKelompokRisikoArea.setToolTipText("Alt+C");
        TCariKelompokRisikoArea.setName("TCariKelompokRisikoArea"); // NOI18N
        TCariKelompokRisikoArea.setPreferredSize(new java.awt.Dimension(140, 23));
        TCariKelompokRisikoArea.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKelompokRisikoAreaKeyPressed(evt);
            }
        });
        FormInput.add(TCariKelompokRisikoArea);
        TCariKelompokRisikoArea.setBounds(536, 340, 215, 23);

        BtnCariKelompokRisiko.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCariKelompokRisiko.setMnemonic('1');
        BtnCariKelompokRisiko.setToolTipText("Alt+1");
        BtnCariKelompokRisiko.setName("BtnCariKelompokRisiko"); // NOI18N
        BtnCariKelompokRisiko.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCariKelompokRisiko.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariKelompokRisikoActionPerformed(evt);
            }
        });
        BtnCariKelompokRisiko.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCariKelompokRisikoKeyPressed(evt);
            }
        });
        FormInput.add(BtnCariKelompokRisiko);
        BtnCariKelompokRisiko.setBounds(755, 340, 28, 23);

        BtnAllKelomokRisiko.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAllKelomokRisiko.setMnemonic('2');
        BtnAllKelomokRisiko.setToolTipText("2Alt+2");
        BtnAllKelomokRisiko.setName("BtnAllKelomokRisiko"); // NOI18N
        BtnAllKelomokRisiko.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnAllKelomokRisiko.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAllKelomokRisikoActionPerformed(evt);
            }
        });
        BtnAllKelomokRisiko.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnAllKelomokRisikoKeyPressed(evt);
            }
        });
        FormInput.add(BtnAllKelomokRisiko);
        BtnAllKelomokRisiko.setBounds(787, 340, 28, 23);

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
        BtnTambahMasalah.setBounds(819, 340, 28, 23);

        label17.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label17.setText("III. IDENTIFIKASI RISIKO");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label17);
        label17.setBounds(15, 370, 310, 23);

        jSeparator3.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator3.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator3.setName("jSeparator3"); // NOI18N
        FormInput.add(jSeparator3);
        jSeparator3.setBounds(0, 370, 880, 1);

        jLabel44.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel44.setText("Apa saja penyebab risiko infeksi yang mungkin terjadi ?");
        jLabel44.setName("jLabel44"); // NOI18N
        FormInput.add(jLabel44);
        jLabel44.setBounds(464, 390, 390, 23);

        Scroll7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 253)));
        Scroll7.setName("Scroll7"); // NOI18N
        Scroll7.setOpaque(true);

        tbIdentifikasiRisikoInfeksi.setName("tbIdentifikasiRisikoInfeksi"); // NOI18N
        Scroll7.setViewportView(tbIdentifikasiRisikoInfeksi);

        FormInput.add(Scroll7);
        Scroll7.setBounds(464, 410, 390, 113);

        label18.setText("Key Word :");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(60, 23));
        FormInput.add(label18);
        label18.setBounds(472, 530, 60, 23);

        TCariRisikoInfeksi.setToolTipText("Alt+C");
        TCariRisikoInfeksi.setName("TCariRisikoInfeksi"); // NOI18N
        TCariRisikoInfeksi.setPreferredSize(new java.awt.Dimension(140, 23));
        TCariRisikoInfeksi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariRisikoInfeksiKeyPressed(evt);
            }
        });
        FormInput.add(TCariRisikoInfeksi);
        TCariRisikoInfeksi.setBounds(536, 530, 215, 23);

        BtnCariRisikoInfeksi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCariRisikoInfeksi.setMnemonic('1');
        BtnCariRisikoInfeksi.setToolTipText("Alt+1");
        BtnCariRisikoInfeksi.setName("BtnCariRisikoInfeksi"); // NOI18N
        BtnCariRisikoInfeksi.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCariRisikoInfeksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariRisikoInfeksiActionPerformed(evt);
            }
        });
        BtnCariRisikoInfeksi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCariRisikoInfeksiKeyPressed(evt);
            }
        });
        FormInput.add(BtnCariRisikoInfeksi);
        BtnCariRisikoInfeksi.setBounds(755, 530, 28, 23);

        BtnAllRisikoInfeksi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAllRisikoInfeksi.setMnemonic('2');
        BtnAllRisikoInfeksi.setToolTipText("2Alt+2");
        BtnAllRisikoInfeksi.setName("BtnAllRisikoInfeksi"); // NOI18N
        BtnAllRisikoInfeksi.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnAllRisikoInfeksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAllRisikoInfeksiActionPerformed(evt);
            }
        });
        BtnAllRisikoInfeksi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnAllRisikoInfeksiKeyPressed(evt);
            }
        });
        FormInput.add(BtnAllRisikoInfeksi);
        BtnAllRisikoInfeksi.setBounds(787, 530, 28, 23);

        BtnTambahRisikoInfeksi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/plus_16.png"))); // NOI18N
        BtnTambahRisikoInfeksi.setMnemonic('3');
        BtnTambahRisikoInfeksi.setToolTipText("Alt+3");
        BtnTambahRisikoInfeksi.setName("BtnTambahRisikoInfeksi"); // NOI18N
        BtnTambahRisikoInfeksi.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnTambahRisikoInfeksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTambahRisikoInfeksiActionPerformed(evt);
            }
        });
        FormInput.add(BtnTambahRisikoInfeksi);
        BtnTambahRisikoInfeksi.setBounds(819, 530, 28, 23);

        jLabel45.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel45.setText("Apa saja penyebab risiko kebakaran yang mungkin terjadi ?");
        jLabel45.setName("jLabel45"); // NOI18N
        FormInput.add(jLabel45);
        jLabel45.setBounds(40, 390, 390, 23);

        Scroll8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 253)));
        Scroll8.setName("Scroll8"); // NOI18N
        Scroll8.setOpaque(true);

        tbIdentifikasiRisikoKebakaran.setName("tbIdentifikasiRisikoKebakaran"); // NOI18N
        Scroll8.setViewportView(tbIdentifikasiRisikoKebakaran);

        FormInput.add(Scroll8);
        Scroll8.setBounds(40, 410, 390, 113);

        label19.setText("Key Word :");
        label19.setName("label19"); // NOI18N
        label19.setPreferredSize(new java.awt.Dimension(60, 23));
        FormInput.add(label19);
        label19.setBounds(48, 530, 60, 23);

        TCariRisikoKebakaran.setToolTipText("Alt+C");
        TCariRisikoKebakaran.setName("TCariRisikoKebakaran"); // NOI18N
        TCariRisikoKebakaran.setPreferredSize(new java.awt.Dimension(140, 23));
        TCariRisikoKebakaran.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariRisikoKebakaranKeyPressed(evt);
            }
        });
        FormInput.add(TCariRisikoKebakaran);
        TCariRisikoKebakaran.setBounds(112, 530, 215, 23);

        BtnCariRisikoKebakarab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCariRisikoKebakarab.setMnemonic('1');
        BtnCariRisikoKebakarab.setToolTipText("Alt+1");
        BtnCariRisikoKebakarab.setName("BtnCariRisikoKebakarab"); // NOI18N
        BtnCariRisikoKebakarab.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCariRisikoKebakarab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariRisikoKebakarabActionPerformed(evt);
            }
        });
        BtnCariRisikoKebakarab.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCariRisikoKebakarabKeyPressed(evt);
            }
        });
        FormInput.add(BtnCariRisikoKebakarab);
        BtnCariRisikoKebakarab.setBounds(331, 530, 28, 23);

        BtnAllRisikoKebakaran.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAllRisikoKebakaran.setMnemonic('2');
        BtnAllRisikoKebakaran.setToolTipText("2Alt+2");
        BtnAllRisikoKebakaran.setName("BtnAllRisikoKebakaran"); // NOI18N
        BtnAllRisikoKebakaran.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnAllRisikoKebakaran.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAllRisikoKebakaranActionPerformed(evt);
            }
        });
        BtnAllRisikoKebakaran.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnAllRisikoKebakaranKeyPressed(evt);
            }
        });
        FormInput.add(BtnAllRisikoKebakaran);
        BtnAllRisikoKebakaran.setBounds(363, 530, 28, 23);

        BtnTambahRisikoKebakaran.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/plus_16.png"))); // NOI18N
        BtnTambahRisikoKebakaran.setMnemonic('3');
        BtnTambahRisikoKebakaran.setToolTipText("Alt+3");
        BtnTambahRisikoKebakaran.setName("BtnTambahRisikoKebakaran"); // NOI18N
        BtnTambahRisikoKebakaran.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnTambahRisikoKebakaran.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTambahRisikoKebakaranActionPerformed(evt);
            }
        });
        FormInput.add(BtnTambahRisikoKebakaran);
        BtnTambahRisikoKebakaran.setBounds(395, 530, 28, 23);

        jLabel46.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel46.setText("Apa saja penyebab risiko keselamatan yang mungkin terjadi ?");
        jLabel46.setName("jLabel46"); // NOI18N
        FormInput.add(jLabel46);
        jLabel46.setBounds(40, 560, 390, 23);

        Scroll9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 253)));
        Scroll9.setName("Scroll9"); // NOI18N
        Scroll9.setOpaque(true);

        tbIdentifikasiRisikoKeselamatan.setName("tbIdentifikasiRisikoKeselamatan"); // NOI18N
        Scroll9.setViewportView(tbIdentifikasiRisikoKeselamatan);

        FormInput.add(Scroll9);
        Scroll9.setBounds(40, 580, 390, 113);

        BtnTambahRisikoKeselamatan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/plus_16.png"))); // NOI18N
        BtnTambahRisikoKeselamatan.setMnemonic('3');
        BtnTambahRisikoKeselamatan.setToolTipText("Alt+3");
        BtnTambahRisikoKeselamatan.setName("BtnTambahRisikoKeselamatan"); // NOI18N
        BtnTambahRisikoKeselamatan.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnTambahRisikoKeselamatan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTambahRisikoKeselamatanActionPerformed(evt);
            }
        });
        FormInput.add(BtnTambahRisikoKeselamatan);
        BtnTambahRisikoKeselamatan.setBounds(395, 700, 28, 23);

        BtnAllRisikoKeselamatan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAllRisikoKeselamatan.setMnemonic('2');
        BtnAllRisikoKeselamatan.setToolTipText("2Alt+2");
        BtnAllRisikoKeselamatan.setName("BtnAllRisikoKeselamatan"); // NOI18N
        BtnAllRisikoKeselamatan.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnAllRisikoKeselamatan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAllRisikoKeselamatanActionPerformed(evt);
            }
        });
        BtnAllRisikoKeselamatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnAllRisikoKeselamatanKeyPressed(evt);
            }
        });
        FormInput.add(BtnAllRisikoKeselamatan);
        BtnAllRisikoKeselamatan.setBounds(363, 700, 28, 23);

        BtnCariRisikoKeselamatan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCariRisikoKeselamatan.setMnemonic('1');
        BtnCariRisikoKeselamatan.setToolTipText("Alt+1");
        BtnCariRisikoKeselamatan.setName("BtnCariRisikoKeselamatan"); // NOI18N
        BtnCariRisikoKeselamatan.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCariRisikoKeselamatan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariRisikoKeselamatanActionPerformed(evt);
            }
        });
        BtnCariRisikoKeselamatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCariRisikoKeselamatanKeyPressed(evt);
            }
        });
        FormInput.add(BtnCariRisikoKeselamatan);
        BtnCariRisikoKeselamatan.setBounds(331, 700, 28, 23);

        TCariRisikoKeselamatan.setToolTipText("Alt+C");
        TCariRisikoKeselamatan.setName("TCariRisikoKeselamatan"); // NOI18N
        TCariRisikoKeselamatan.setPreferredSize(new java.awt.Dimension(140, 23));
        TCariRisikoKeselamatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariRisikoKeselamatanKeyPressed(evt);
            }
        });
        FormInput.add(TCariRisikoKeselamatan);
        TCariRisikoKeselamatan.setBounds(112, 700, 215, 23);

        label20.setText("Key Word :");
        label20.setName("label20"); // NOI18N
        label20.setPreferredSize(new java.awt.Dimension(60, 23));
        FormInput.add(label20);
        label20.setBounds(48, 700, 60, 23);

        jLabel47.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel47.setText("Apa saja penyebab risiko utilitas yang mungkin terjadi ?");
        jLabel47.setName("jLabel47"); // NOI18N
        FormInput.add(jLabel47);
        jLabel47.setBounds(464, 560, 390, 23);

        Scroll10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 253)));
        Scroll10.setName("Scroll10"); // NOI18N
        Scroll10.setOpaque(true);

        tbIdentifikasiRisikoUtilitas.setName("tbIdentifikasiRisikoUtilitas"); // NOI18N
        Scroll10.setViewportView(tbIdentifikasiRisikoUtilitas);

        FormInput.add(Scroll10);
        Scroll10.setBounds(464, 580, 390, 113);

        BtnTambahRisikoUtilitas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/plus_16.png"))); // NOI18N
        BtnTambahRisikoUtilitas.setMnemonic('3');
        BtnTambahRisikoUtilitas.setToolTipText("Alt+3");
        BtnTambahRisikoUtilitas.setName("BtnTambahRisikoUtilitas"); // NOI18N
        BtnTambahRisikoUtilitas.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnTambahRisikoUtilitas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTambahRisikoUtilitasActionPerformed(evt);
            }
        });
        FormInput.add(BtnTambahRisikoUtilitas);
        BtnTambahRisikoUtilitas.setBounds(819, 700, 28, 23);

        BtnAllRisikoUtilitas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAllRisikoUtilitas.setMnemonic('2');
        BtnAllRisikoUtilitas.setToolTipText("2Alt+2");
        BtnAllRisikoUtilitas.setName("BtnAllRisikoUtilitas"); // NOI18N
        BtnAllRisikoUtilitas.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnAllRisikoUtilitas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAllRisikoUtilitasActionPerformed(evt);
            }
        });
        BtnAllRisikoUtilitas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnAllRisikoUtilitasKeyPressed(evt);
            }
        });
        FormInput.add(BtnAllRisikoUtilitas);
        BtnAllRisikoUtilitas.setBounds(787, 700, 28, 23);

        BtnCariRisikoUtilitas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCariRisikoUtilitas.setMnemonic('1');
        BtnCariRisikoUtilitas.setToolTipText("Alt+1");
        BtnCariRisikoUtilitas.setName("BtnCariRisikoUtilitas"); // NOI18N
        BtnCariRisikoUtilitas.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCariRisikoUtilitas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariRisikoUtilitasActionPerformed(evt);
            }
        });
        BtnCariRisikoUtilitas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCariRisikoUtilitasKeyPressed(evt);
            }
        });
        FormInput.add(BtnCariRisikoUtilitas);
        BtnCariRisikoUtilitas.setBounds(755, 700, 28, 23);

        TCariRisikoUtilitas.setToolTipText("Alt+C");
        TCariRisikoUtilitas.setName("TCariRisikoUtilitas"); // NOI18N
        TCariRisikoUtilitas.setPreferredSize(new java.awt.Dimension(140, 23));
        TCariRisikoUtilitas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariRisikoUtilitasKeyPressed(evt);
            }
        });
        FormInput.add(TCariRisikoUtilitas);
        TCariRisikoUtilitas.setBounds(536, 700, 215, 23);

        label21.setText("Key Word :");
        label21.setName("label21"); // NOI18N
        label21.setPreferredSize(new java.awt.Dimension(60, 23));
        FormInput.add(label21);
        label21.setBounds(472, 700, 60, 23);

        label22.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label22.setText("IV. KELAS RISIKO / KELAS PENCEGAHAN");
        label22.setName("label22"); // NOI18N
        label22.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label22);
        label22.setBounds(15, 810, 310, 23);

        jSeparator4.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator4.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator4.setName("jSeparator4"); // NOI18N
        FormInput.add(jSeparator4);
        jSeparator4.setBounds(0, 810, 880, 1);

        KdDokter2.setEditable(false);
        KdDokter2.setName("KdDokter2"); // NOI18N
        KdDokter2.setPreferredSize(new java.awt.Dimension(80, 23));
        KdDokter2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdDokter2KeyPressed(evt);
            }
        });
        FormInput.add(KdDokter2);
        KdDokter2.setBounds(310, 830, 58, 23);

        NmDokter2.setEditable(false);
        NmDokter2.setName("NmDokter2"); // NOI18N
        NmDokter2.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmDokter2);
        NmDokter2.setBounds(370, 830, 200, 23);

        BtnDokter2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter2.setMnemonic('2');
        BtnDokter2.setToolTipText("Alt+2");
        BtnDokter2.setName("BtnDokter2"); // NOI18N
        BtnDokter2.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDokter2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokter2ActionPerformed(evt);
            }
        });
        BtnDokter2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnDokter2KeyPressed(evt);
            }
        });
        FormInput.add(BtnDokter2);
        BtnDokter2.setBounds(570, 830, 28, 23);

        jLabel48.setText("?");
        jLabel48.setName("jLabel48"); // NOI18N
        FormInput.add(jLabel48);
        jLabel48.setBounds(0, 830, 306, 23);

        jLabel49.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel49.setText("Proyek yang akan dilakukan masuk ke kelas risiko apa");
        jLabel49.setName("jLabel49"); // NOI18N
        FormInput.add(jLabel49);
        jLabel49.setBounds(40, 830, 270, 23);

        label23.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label23.setText("V. TINDAKAN PENGENDALIAN & REKOMENDASI");
        label23.setName("label23"); // NOI18N
        label23.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label23);
        label23.setBounds(15, 860, 310, 23);

        jSeparator5.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator5.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator5.setName("jSeparator5"); // NOI18N
        FormInput.add(jSeparator5);
        jSeparator5.setBounds(0, 860, 880, 1);

        jLabel50.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel50.setText("Tindakan pengendalian apa saja yang bisa dilakukan ?");
        jLabel50.setName("jLabel50"); // NOI18N
        FormInput.add(jLabel50);
        jLabel50.setBounds(40, 880, 390, 23);

        Scroll11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 253)));
        Scroll11.setName("Scroll11"); // NOI18N
        Scroll11.setOpaque(true);

        tbTindakanPengendalian.setName("tbTindakanPengendalian"); // NOI18N
        Scroll11.setViewportView(tbTindakanPengendalian);

        FormInput.add(Scroll11);
        Scroll11.setBounds(40, 900, 390, 113);

        label24.setText("Key Word :");
        label24.setName("label24"); // NOI18N
        label24.setPreferredSize(new java.awt.Dimension(60, 23));
        FormInput.add(label24);
        label24.setBounds(48, 1020, 60, 23);

        TCariTindakanPengendalian.setToolTipText("Alt+C");
        TCariTindakanPengendalian.setName("TCariTindakanPengendalian"); // NOI18N
        TCariTindakanPengendalian.setPreferredSize(new java.awt.Dimension(140, 23));
        TCariTindakanPengendalian.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariTindakanPengendalianKeyPressed(evt);
            }
        });
        FormInput.add(TCariTindakanPengendalian);
        TCariTindakanPengendalian.setBounds(112, 1020, 215, 23);

        BtnCariTindakanPengendalian.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCariTindakanPengendalian.setMnemonic('1');
        BtnCariTindakanPengendalian.setToolTipText("Alt+1");
        BtnCariTindakanPengendalian.setName("BtnCariTindakanPengendalian"); // NOI18N
        BtnCariTindakanPengendalian.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCariTindakanPengendalian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariTindakanPengendalianActionPerformed(evt);
            }
        });
        BtnCariTindakanPengendalian.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCariTindakanPengendalianKeyPressed(evt);
            }
        });
        FormInput.add(BtnCariTindakanPengendalian);
        BtnCariTindakanPengendalian.setBounds(331, 1020, 28, 23);

        BtnAllTindakanPengendalian.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAllTindakanPengendalian.setMnemonic('2');
        BtnAllTindakanPengendalian.setToolTipText("2Alt+2");
        BtnAllTindakanPengendalian.setName("BtnAllTindakanPengendalian"); // NOI18N
        BtnAllTindakanPengendalian.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnAllTindakanPengendalian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAllTindakanPengendalianActionPerformed(evt);
            }
        });
        BtnAllTindakanPengendalian.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnAllTindakanPengendalianKeyPressed(evt);
            }
        });
        FormInput.add(BtnAllTindakanPengendalian);
        BtnAllTindakanPengendalian.setBounds(363, 1020, 28, 23);

        BtnTambahTindakanPengendalian.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/plus_16.png"))); // NOI18N
        BtnTambahTindakanPengendalian.setMnemonic('3');
        BtnTambahTindakanPengendalian.setToolTipText("Alt+3");
        BtnTambahTindakanPengendalian.setName("BtnTambahTindakanPengendalian"); // NOI18N
        BtnTambahTindakanPengendalian.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnTambahTindakanPengendalian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTambahTindakanPengendalianActionPerformed(evt);
            }
        });
        FormInput.add(BtnTambahTindakanPengendalian);
        BtnTambahTindakanPengendalian.setBounds(395, 1020, 28, 23);

        jLabel51.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel51.setText("Apa saja rekomendasi setelah pengerjaan proyek?");
        jLabel51.setName("jLabel51"); // NOI18N
        FormInput.add(jLabel51);
        jLabel51.setBounds(464, 965, 370, 23);

        scrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane2.setName("scrollPane2"); // NOI18N

        KeluhanUtama1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        KeluhanUtama1.setColumns(20);
        KeluhanUtama1.setRows(10);
        KeluhanUtama1.setName("KeluhanUtama1"); // NOI18N
        KeluhanUtama1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeluhanUtama1KeyPressed(evt);
            }
        });
        scrollPane2.setViewportView(KeluhanUtama1);

        FormInput.add(scrollPane2);
        scrollPane2.setBounds(464, 985, 390, 58);

        jLabel52.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel52.setText("Apa saja rekomendasi selama pengerjaan proyek?");
        jLabel52.setName("jLabel52"); // NOI18N
        FormInput.add(jLabel52);
        jLabel52.setBounds(464, 880, 370, 23);

        scrollPane3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane3.setName("scrollPane3"); // NOI18N

        KeluhanUtama2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        KeluhanUtama2.setColumns(20);
        KeluhanUtama2.setRows(10);
        KeluhanUtama2.setName("KeluhanUtama2"); // NOI18N
        KeluhanUtama2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeluhanUtama2KeyPressed(evt);
            }
        });
        scrollPane3.setViewportView(KeluhanUtama2);

        FormInput.add(scrollPane3);
        scrollPane3.setBounds(464, 900, 390, 58);

        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel31.setText("Apakah ada hal-hal yang perlu dimonitor secara khusus ?");
        jLabel31.setName("jLabel31"); // NOI18N
        FormInput.add(jLabel31);
        jLabel31.setBounds(40, 1050, 310, 23);

        scrollPane8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane8.setName("scrollPane8"); // NOI18N

        RPS1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        RPS1.setColumns(20);
        RPS1.setRows(5);
        RPS1.setName("RPS1"); // NOI18N
        RPS1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RPS1KeyPressed(evt);
            }
        });
        scrollPane8.setViewportView(RPS1);

        FormInput.add(scrollPane8);
        scrollPane8.setBounds(40, 1070, 814, 53);

        label25.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label25.setText("VI. PERSYARATAN YANG HARUS DIPENUHI SEBELUM PEKERJAAN DIMULAI");
        label25.setName("label25"); // NOI18N
        label25.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label25);
        label25.setBounds(15, 1130, 460, 23);

        jSeparator6.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator6.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator6.setName("jSeparator6"); // NOI18N
        FormInput.add(jSeparator6);
        jSeparator6.setBounds(0, 1130, 880, 1);

        jLabel53.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel53.setText("Persyaratan apa saja yang harus dipenuhi sebelum pekerjaan dapat dimulai ?");
        jLabel53.setName("jLabel53"); // NOI18N
        FormInput.add(jLabel53);
        jLabel53.setBounds(40, 1150, 390, 23);

        Scroll12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 253)));
        Scroll12.setName("Scroll12"); // NOI18N
        Scroll12.setOpaque(true);

        tbPersyaratanDipenuhi.setName("tbPersyaratanDipenuhi"); // NOI18N
        Scroll12.setViewportView(tbPersyaratanDipenuhi);

        FormInput.add(Scroll12);
        Scroll12.setBounds(40, 1170, 814, 133);

        BtnTambahPersyaratanDipenuhi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/plus_16.png"))); // NOI18N
        BtnTambahPersyaratanDipenuhi.setMnemonic('3');
        BtnTambahPersyaratanDipenuhi.setToolTipText("Alt+3");
        BtnTambahPersyaratanDipenuhi.setName("BtnTambahPersyaratanDipenuhi"); // NOI18N
        BtnTambahPersyaratanDipenuhi.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnTambahPersyaratanDipenuhi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTambahPersyaratanDipenuhiActionPerformed(evt);
            }
        });
        FormInput.add(BtnTambahPersyaratanDipenuhi);
        BtnTambahPersyaratanDipenuhi.setBounds(819, 1310, 28, 23);

        BtnAllPersyaratanDipenuhi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAllPersyaratanDipenuhi.setMnemonic('2');
        BtnAllPersyaratanDipenuhi.setToolTipText("2Alt+2");
        BtnAllPersyaratanDipenuhi.setName("BtnAllPersyaratanDipenuhi"); // NOI18N
        BtnAllPersyaratanDipenuhi.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnAllPersyaratanDipenuhi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAllPersyaratanDipenuhiActionPerformed(evt);
            }
        });
        BtnAllPersyaratanDipenuhi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnAllPersyaratanDipenuhiKeyPressed(evt);
            }
        });
        FormInput.add(BtnAllPersyaratanDipenuhi);
        BtnAllPersyaratanDipenuhi.setBounds(787, 1310, 28, 23);

        BtnCariPersyaratanDipenuhi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCariPersyaratanDipenuhi.setMnemonic('1');
        BtnCariPersyaratanDipenuhi.setToolTipText("Alt+1");
        BtnCariPersyaratanDipenuhi.setName("BtnCariPersyaratanDipenuhi"); // NOI18N
        BtnCariPersyaratanDipenuhi.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCariPersyaratanDipenuhi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariPersyaratanDipenuhiActionPerformed(evt);
            }
        });
        BtnCariPersyaratanDipenuhi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCariPersyaratanDipenuhiKeyPressed(evt);
            }
        });
        FormInput.add(BtnCariPersyaratanDipenuhi);
        BtnCariPersyaratanDipenuhi.setBounds(755, 1310, 28, 23);

        TCariPersyaratanDipenuhi.setToolTipText("Alt+C");
        TCariPersyaratanDipenuhi.setName("TCariPersyaratanDipenuhi"); // NOI18N
        TCariPersyaratanDipenuhi.setPreferredSize(new java.awt.Dimension(140, 23));
        TCariPersyaratanDipenuhi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariPersyaratanDipenuhiKeyPressed(evt);
            }
        });
        FormInput.add(TCariPersyaratanDipenuhi);
        TCariPersyaratanDipenuhi.setBounds(112, 1310, 639, 23);

        label26.setText("Key Word :");
        label26.setName("label26"); // NOI18N
        label26.setPreferredSize(new java.awt.Dimension(60, 23));
        FormInput.add(label26);
        label26.setBounds(48, 1310, 60, 23);

        label27.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label27.setText("VII. CATATAN TIM PPI / K3 / LAINNYA");
        label27.setName("label27"); // NOI18N
        label27.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label27);
        label27.setBounds(15, 1340, 460, 23);

        jSeparator7.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator7.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator7.setName("jSeparator7"); // NOI18N
        FormInput.add(jSeparator7);
        jSeparator7.setBounds(0, 1340, 880, 1);

        scrollPane9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane9.setName("scrollPane9"); // NOI18N

        RPS2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        RPS2.setColumns(20);
        RPS2.setRows(5);
        RPS2.setName("RPS2"); // NOI18N
        RPS2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RPS2KeyPressed(evt);
            }
        });
        scrollPane9.setViewportView(RPS2);

        FormInput.add(scrollPane9);
        scrollPane9.setBounds(40, 1360, 814, 53);

        label28.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label28.setText("VIII. PERSETUJUAN & PENGESAHAN");
        label28.setName("label28"); // NOI18N
        label28.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label28);
        label28.setBounds(15, 1420, 460, 23);

        jSeparator8.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator8.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator8.setName("jSeparator8"); // NOI18N
        FormInput.add(jSeparator8);
        jSeparator8.setBounds(0, 1420, 880, 1);

        label29.setText("P.J. Proyek :");
        label29.setName("label29"); // NOI18N
        label29.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label29);
        label29.setBounds(0, 1470, 110, 23);

        KdDokter3.setEditable(false);
        KdDokter3.setName("KdDokter3"); // NOI18N
        KdDokter3.setPreferredSize(new java.awt.Dimension(80, 23));
        KdDokter3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdDokter3KeyPressed(evt);
            }
        });
        FormInput.add(KdDokter3);
        KdDokter3.setBounds(114, 1470, 105, 23);

        NmDokter3.setEditable(false);
        NmDokter3.setName("NmDokter3"); // NOI18N
        NmDokter3.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmDokter3);
        NmDokter3.setBounds(221, 1470, 195, 23);

        BtnDokter3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter3.setMnemonic('2');
        BtnDokter3.setToolTipText("Alt+2");
        BtnDokter3.setName("BtnDokter3"); // NOI18N
        BtnDokter3.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDokter3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokter3ActionPerformed(evt);
            }
        });
        BtnDokter3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnDokter3KeyPressed(evt);
            }
        });
        FormInput.add(BtnDokter3);
        BtnDokter3.setBounds(418, 1470, 28, 23);

        label30.setText("Manajer :");
        label30.setName("label30"); // NOI18N
        label30.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label30);
        label30.setBounds(448, 1440, 70, 23);

        NmDokter4.setEditable(false);
        NmDokter4.setName("NmDokter4"); // NOI18N
        NmDokter4.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmDokter4);
        NmDokter4.setBounds(629, 1440, 195, 23);

        BtnDokter4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter4.setMnemonic('2');
        BtnDokter4.setToolTipText("Alt+2");
        BtnDokter4.setName("BtnDokter4"); // NOI18N
        BtnDokter4.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDokter4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokter4ActionPerformed(evt);
            }
        });
        BtnDokter4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnDokter4KeyPressed(evt);
            }
        });
        FormInput.add(BtnDokter4);
        BtnDokter4.setBounds(826, 1440, 28, 23);

        Hubungan1.setEditable(false);
        Hubungan1.setName("Hubungan1"); // NOI18N
        Hubungan1.setPreferredSize(new java.awt.Dimension(207, 23));
        Hubungan1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Hubungan1KeyPressed(evt);
            }
        });
        FormInput.add(Hubungan1);
        Hubungan1.setBounds(522, 1470, 105, 23);

        BtnDokter5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter5.setMnemonic('2');
        BtnDokter5.setToolTipText("Alt+2");
        BtnDokter5.setName("BtnDokter5"); // NOI18N
        BtnDokter5.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDokter5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokter5ActionPerformed(evt);
            }
        });
        BtnDokter5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnDokter5KeyPressed(evt);
            }
        });
        FormInput.add(BtnDokter5);
        BtnDokter5.setBounds(826, 1470, 28, 23);

        NmDokter5.setEditable(false);
        NmDokter5.setName("NmDokter5"); // NOI18N
        NmDokter5.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmDokter5);
        NmDokter5.setBounds(629, 1470, 195, 23);

        label31.setText("Direktur :");
        label31.setName("label31"); // NOI18N
        label31.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label31);
        label31.setBounds(448, 1470, 70, 23);

        TglAsuhan2.setForeground(new java.awt.Color(50, 70, 50));
        TglAsuhan2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "27-03-2026 08:40:07" }));
        TglAsuhan2.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        TglAsuhan2.setName("TglAsuhan2"); // NOI18N
        TglAsuhan2.setOpaque(false);
        TglAsuhan2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglAsuhan2KeyPressed(evt);
            }
        });
        FormInput.add(TglAsuhan2);
        TglAsuhan2.setBounds(114, 1500, 130, 23);

        label32.setText("Tanggal :");
        label32.setName("label32"); // NOI18N
        label32.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label32);
        label32.setBounds(0, 1500, 110, 23);

        jLabel15.setText("Nomor Pengkajian Risiko Pra Konstruksi / PCRA / Persetujuan / Pengesahan :");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(250, 1500, 400, 23);

        Jk1.setHighlighter(null);
        Jk1.setName("Jk1"); // NOI18N
        FormInput.add(Jk1);
        Jk1.setBounds(654, 1500, 170, 23);

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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "26-03-2026" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "26-03-2026" }));
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

    private void NoProyekKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoProyekKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isRawat();
        }else{            
            Valid.pindah(evt,TCari,BtnDokter);
        }
}//GEN-LAST:event_NoProyekKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        
    
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            //Valid.pindah(evt,KetFisik,BtnBatal);
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
                    
            
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnBatal, BtnEdit);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
        
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
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='105px'><b>No.Rawat</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='70px'><b>No.RM</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='150px'><b>Nama Pasien</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='65px'><b>Tgl.Lahir</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='55px'><b>J.K.</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Kode Dokter</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='150px'><b>Nama Dokter</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='115px'><b>Tanggal</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Anamnesis</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='100px'><b>Hubungan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='300px'><b>Keluhan Utama</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='150px'><b>Riwayat Penyakit Sekarang</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='150px'><b>Riwayat Penyakit Dahulu</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='150px'><b>Riwayat Penyakit Keluarga</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='150px'><b>Riwayat Penggunakan Obat</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='120px'><b>Riwayat Alergi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='90px'><b>Keadaan Umum</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='50px'><b>GCS</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Kesadaran</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='60px'><b>TD(mmHg)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='75px'><b>Nadi(x/menit)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='67px'><b>RR(x/menit)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='40px'><b>Suhu</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='40px'><b>SpO2</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='40px'><b>BB(Kg)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='40px'><b>TB(cm)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Kepala</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Gigi & Mulut</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>THT</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Thoraks</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Abdomen</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Genital & Anus</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Ekstremitas</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Kulit</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='300px'><b>Ket.Pemeriksaan Fisik</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='200px'><b>Ket.Status Lokalis</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='170px'><b>Pemeriksaa Penunjang</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='150px'><b>Diagnosis/Asesmen</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='300px'><b>Tatalaksana</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='150px'><b>Konsul/Rujuk</b></td>"+
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
                                        "<font size='2' face='Tahoma'>DATA PENGKAJIAN AWAL MEDIS RAWAT JALAN<br><br></font>"+        
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
            Valid.pindah(evt, BtnCari, NamaProyek);
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
        
    }//GEN-LAST:event_BtnDokterActionPerformed

    private void BtnDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokterKeyPressed
        //Valid.pindah(evt,Monitoring,BtnSimpan);
    }//GEN-LAST:event_BtnDokterKeyPressed

    private void AnamnesisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AnamnesisKeyPressed
        Valid.pindah(evt,TglAsuhan,Hubungan);
    }//GEN-LAST:event_AnamnesisKeyPressed

    private void KeluhanUtamaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeluhanUtamaKeyPressed
        //Valid.pindah2(evt,Hubungan,RPS);
    }//GEN-LAST:event_KeluhanUtamaKeyPressed

    private void RPSKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RPSKeyPressed
        //Valid.pindah2(evt,KeluhanUtama,RPK);
    }//GEN-LAST:event_RPSKeyPressed

    private void TglAsuhanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglAsuhanKeyPressed
        //Valid.pindah(evt,Konsul,Anamnesis);
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
                param.put("lokalis",getClass().getResource("/picture/semua.png").openStream());
            } catch (Exception e) {
            } 
            finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
            param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbObat.getValueAt(tbObat.getSelectedRow(),6).toString()+"\nID "+(finger.equals("")?tbObat.getValueAt(tbObat.getSelectedRow(),5).toString():finger)+"\n"+Valid.SetTgl3(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString())); 
            
            Valid.MyReportqry("rptCetakPenilaianAwalMedisRalan.jasper","report","::[ Laporan Pengkajian Awal Medis Rawat Jalan ]::",
                "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penilaian_medis_ralan.tanggal,"+
                "penilaian_medis_ralan.kd_dokter,penilaian_medis_ralan.anamnesis,penilaian_medis_ralan.hubungan,penilaian_medis_ralan.keluhan_utama,penilaian_medis_ralan.rps,penilaian_medis_ralan.rpk,penilaian_medis_ralan.rpd,penilaian_medis_ralan.rpo,penilaian_medis_ralan.alergi,"+
                "penilaian_medis_ralan.keadaan,penilaian_medis_ralan.gcs,penilaian_medis_ralan.kesadaran,penilaian_medis_ralan.td,penilaian_medis_ralan.nadi,penilaian_medis_ralan.rr,penilaian_medis_ralan.suhu,penilaian_medis_ralan.spo,penilaian_medis_ralan.bb,penilaian_medis_ralan.tb,"+
                "penilaian_medis_ralan.kepala,penilaian_medis_ralan.gigi,penilaian_medis_ralan.tht,penilaian_medis_ralan.thoraks,penilaian_medis_ralan.abdomen,penilaian_medis_ralan.ekstremitas,penilaian_medis_ralan.genital,penilaian_medis_ralan.kulit,"+
                "penilaian_medis_ralan.ket_fisik,penilaian_medis_ralan.ket_lokalis,penilaian_medis_ralan.penunjang,penilaian_medis_ralan.diagnosis,penilaian_medis_ralan.tata,penilaian_medis_ralan.konsulrujuk,dokter.nm_dokter "+
                "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join penilaian_medis_ralan on reg_periksa.no_rawat=penilaian_medis_ralan.no_rawat "+
                "inner join dokter on penilaian_medis_ralan.kd_dokter=dokter.kd_dokter where penilaian_medis_ralan.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'",param);
        }
    }//GEN-LAST:event_MnPenilaianMedisActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        if(Valid.daysOld("./cache/pcrakelompokrisikoarea.iyem")<30){
            tampilKelompokRisiko2();
        }else{
            tampilKelompokRisiko();
        }
        
        if(Valid.daysOld("./cache/pcraidentifikasirisikokebakaran.iyem")<30){
            tampilIdentifikasiRisikoKebakaran2();
        }else{
            tampilIdentifikasiRisikoKebakaran();
        }
        
        if(Valid.daysOld("./cache/pcraidentifikasirisikoinfeksi.iyem")<30){
            tampilIdentifikasiRisikoInfeksi2();
        }else{
            tampilIdentifikasiRisikoInfeksi();
        }
        
        if(Valid.daysOld("./cache/pcraidentifikasirisikokeselamatan.iyem")<30){
            tampilIdentifikasiRisikoKeselamatan2();
        }else{
            tampilIdentifikasiRisikoKeselamatan();
        }
        
        if(Valid.daysOld("./cache/pcraidentifikasirisikoutilitas.iyem")<30){
            tampilIdentifikasiRisikoUtilitas2();
        }else{
            tampilIdentifikasiRisikoUtilitas();
        }
        
        if(Valid.daysOld("./cache/pcratindakanpengendalian.iyem")<30){
            tampilTindakanPengendalian2();
        }else{
            tampilTindakanPengendalian();
        }
        
        if(Valid.daysOld("./cache/pcrapersyaratandipenuhi.iyem")<30){
            tampilPersyaratanDipenuhi2();
        }else{
            tampilPersyaratanDipenuhi();
        }
        
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
            
            TCariKelompokRisikoArea.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(TCariKelompokRisikoArea.getText().length()>2){
                        runBackground(() ->tampilKelompokRisiko2());
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(TCariKelompokRisikoArea.getText().length()>2){
                        runBackground(() ->tampilKelompokRisiko2());
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(TCariKelompokRisikoArea.getText().length()>2){
                        runBackground(() ->tampilKelompokRisiko2());
                    }
                }
            });
            
            TCariRisikoKebakaran.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(TCariRisikoKebakaran.getText().length()>2){
                        runBackground(() ->tampilIdentifikasiRisikoKebakaran2());
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(TCariRisikoKebakaran.getText().length()>2){
                        runBackground(() ->tampilIdentifikasiRisikoKebakaran2());
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(TCariRisikoKebakaran.getText().length()>2){
                        runBackground(() ->tampilIdentifikasiRisikoKebakaran2());
                    }
                }
            });
            
            TCariRisikoInfeksi.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(TCariRisikoInfeksi.getText().length()>2){
                        runBackground(() ->tampilIdentifikasiRisikoInfeksi2());
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(TCariRisikoInfeksi.getText().length()>2){
                        runBackground(() ->tampilIdentifikasiRisikoInfeksi2());
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(TCariRisikoInfeksi.getText().length()>2){
                        runBackground(() ->tampilIdentifikasiRisikoInfeksi2());
                    }
                }
            });
            
            TCariRisikoKeselamatan.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(TCariRisikoKeselamatan.getText().length()>2){
                        runBackground(() ->tampilIdentifikasiRisikoKeselamatan2());
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(TCariRisikoKeselamatan.getText().length()>2){
                        runBackground(() ->tampilIdentifikasiRisikoKeselamatan2());
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(TCariRisikoKeselamatan.getText().length()>2){
                        runBackground(() ->tampilIdentifikasiRisikoKeselamatan2());
                    }
                }
            });
            
            TCariRisikoUtilitas.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(TCariRisikoUtilitas.getText().length()>2){
                        runBackground(() ->tampilIdentifikasiRisikoUtilitas2());
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(TCariRisikoUtilitas.getText().length()>2){
                        runBackground(() ->tampilIdentifikasiRisikoUtilitas2());
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(TCariRisikoUtilitas.getText().length()>2){
                        runBackground(() ->tampilIdentifikasiRisikoUtilitas2());
                    }
                }
            });
            
            TCariTindakanPengendalian.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(TCariTindakanPengendalian.getText().length()>2){
                        runBackground(() ->tampilTindakanPengendalian2());
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(TCariTindakanPengendalian.getText().length()>2){
                        runBackground(() ->tampilTindakanPengendalian2());
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(TCariTindakanPengendalian.getText().length()>2){
                        runBackground(() ->tampilTindakanPengendalian2());
                    }
                }
            });
            
            TCariPersyaratanDipenuhi.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(TCariPersyaratanDipenuhi.getText().length()>2){
                        runBackground(() ->tampilPersyaratanDipenuhi2());
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(TCariPersyaratanDipenuhi.getText().length()>2){
                        runBackground(() ->tampilPersyaratanDipenuhi2());
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(TCariPersyaratanDipenuhi.getText().length()>2){
                        runBackground(() ->tampilPersyaratanDipenuhi2());
                    }
                }
            });
        }
    }//GEN-LAST:event_formWindowOpened

    private void LokasiProyekKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LokasiProyekKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_LokasiProyekKeyPressed

    private void TglAsuhan1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglAsuhan1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglAsuhan1KeyPressed

    private void BtnDokter1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokter1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnDokter1ActionPerformed

    private void BtnDokter1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokter1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnDokter1KeyPressed

    private void KdDokter1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdDokter1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KdDokter1KeyPressed

    private void TCariKelompokRisikoAreaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKelompokRisikoAreaKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            runBackground(() ->tampilKelompokRisiko2());
        }else if((evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN)||(evt.getKeyCode()==KeyEvent.VK_TAB)){
            //Rencana.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            //KetDokter.requestFocus();
        }
    }//GEN-LAST:event_TCariKelompokRisikoAreaKeyPressed

    private void BtnCariKelompokRisikoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariKelompokRisikoActionPerformed
        runBackground(() ->tampilKelompokRisiko2());
    }//GEN-LAST:event_BtnCariKelompokRisikoActionPerformed

    private void BtnCariKelompokRisikoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKelompokRisikoKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            runBackground(() ->tampilKelompokRisiko2());
        }else if((evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN)||(evt.getKeyCode()==KeyEvent.VK_TAB)){
            //Rencana.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            //KetDokter.requestFocus();
        }
    }//GEN-LAST:event_BtnCariKelompokRisikoKeyPressed

    private void BtnAllKelomokRisikoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllKelomokRisikoActionPerformed
        TCariKelompokRisikoArea.setText("");
        runBackground(() ->tampilKelompokRisiko());
    }//GEN-LAST:event_BtnAllKelomokRisikoActionPerformed

    private void BtnAllKelomokRisikoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKelomokRisikoKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllKelomokRisikoActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnCariKelompokRisiko, TCariKelompokRisikoArea);
        }
    }//GEN-LAST:event_BtnAllKelomokRisikoKeyPressed

    private void BtnTambahMasalahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahMasalahActionPerformed
        if (kelompokrisikoarea == null || !kelompokrisikoarea.isDisplayable()) {
            kelompokrisikoarea=new PCRAICRALokasiKelompokRisikoArea(null,false);
            kelompokrisikoarea.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            kelompokrisikoarea.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    kelompokrisikoarea=null;
                }
            });

            kelompokrisikoarea.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            kelompokrisikoarea.setLocationRelativeTo(internalFrame1);
        }
        if (kelompokrisikoarea == null) return;
        if (kelompokrisikoarea.isVisible()) {
            kelompokrisikoarea.toFront();
            return;
        }
        kelompokrisikoarea.setVisible(true); 
    }//GEN-LAST:event_BtnTambahMasalahActionPerformed

    private void TCariRisikoInfeksiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariRisikoInfeksiKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            runBackground(() ->tampilIdentifikasiRisikoInfeksi2());
        }else if((evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN)||(evt.getKeyCode()==KeyEvent.VK_TAB)){
            //Rencana.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            //KetDokter.requestFocus();
        }
    }//GEN-LAST:event_TCariRisikoInfeksiKeyPressed

    private void BtnCariRisikoInfeksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariRisikoInfeksiActionPerformed
        runBackground(() ->tampilIdentifikasiRisikoInfeksi2());
    }//GEN-LAST:event_BtnCariRisikoInfeksiActionPerformed

    private void BtnCariRisikoInfeksiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariRisikoInfeksiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnCariRisikoInfeksiKeyPressed

    private void BtnAllRisikoInfeksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllRisikoInfeksiActionPerformed
        TCariRisikoInfeksi.setText("");
        runBackground(() ->tampilIdentifikasiRisikoInfeksi());
    }//GEN-LAST:event_BtnAllRisikoInfeksiActionPerformed

    private void BtnAllRisikoInfeksiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllRisikoInfeksiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnAllRisikoInfeksiKeyPressed

    private void BtnTambahRisikoInfeksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahRisikoInfeksiActionPerformed
        if (identifikasirisikoinfeksi == null || !identifikasirisikoinfeksi.isDisplayable()) {
            identifikasirisikoinfeksi=new PCRAICRAIdentifikasiRisikoInfeksi(null,false);
            identifikasirisikoinfeksi.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            identifikasirisikoinfeksi.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    identifikasirisikoinfeksi=null;
                }
            });

            identifikasirisikoinfeksi.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            identifikasirisikoinfeksi.setLocationRelativeTo(internalFrame1);
        }
        if (identifikasirisikoinfeksi == null) return;
        if (identifikasirisikoinfeksi.isVisible()) {
            identifikasirisikoinfeksi.toFront();
            return;
        }
        identifikasirisikoinfeksi.setVisible(true); 
    }//GEN-LAST:event_BtnTambahRisikoInfeksiActionPerformed

    private void TCariRisikoKebakaranKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariRisikoKebakaranKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            runBackground(() ->tampilIdentifikasiRisikoKebakaran2());
        }else if((evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN)||(evt.getKeyCode()==KeyEvent.VK_TAB)){
            //Rencana.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            //KetDokter.requestFocus();
        }
    }//GEN-LAST:event_TCariRisikoKebakaranKeyPressed

    private void BtnCariRisikoKebakarabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariRisikoKebakarabActionPerformed
        runBackground(() ->tampilIdentifikasiRisikoKebakaran2());
    }//GEN-LAST:event_BtnCariRisikoKebakarabActionPerformed

    private void BtnCariRisikoKebakarabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariRisikoKebakarabKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnCariRisikoKebakarabKeyPressed

    private void BtnAllRisikoKebakaranActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllRisikoKebakaranActionPerformed
        TCariRisikoKebakaran.setText("");
        runBackground(() ->tampilIdentifikasiRisikoKebakaran());
    }//GEN-LAST:event_BtnAllRisikoKebakaranActionPerformed

    private void BtnAllRisikoKebakaranKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllRisikoKebakaranKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnAllRisikoKebakaranKeyPressed

    private void BtnTambahRisikoKebakaranActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahRisikoKebakaranActionPerformed
        if (identifikasirisikokebakaran == null || !identifikasirisikokebakaran.isDisplayable()) {
            identifikasirisikokebakaran=new PCRAICRAIdentifikasiRisikoKebakaran(null,false);
            identifikasirisikokebakaran.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            identifikasirisikokebakaran.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    identifikasirisikokebakaran=null;
                }
            });

            identifikasirisikokebakaran.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            identifikasirisikokebakaran.setLocationRelativeTo(internalFrame1);
        }
        if (identifikasirisikokebakaran == null) return;
        if (identifikasirisikokebakaran.isVisible()) {
            identifikasirisikokebakaran.toFront();
            return;
        }
        identifikasirisikokebakaran.setVisible(true); 
    }//GEN-LAST:event_BtnTambahRisikoKebakaranActionPerformed

    private void BtnTambahRisikoKeselamatanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahRisikoKeselamatanActionPerformed
        if (identifikasirisikokeselamatan == null || !identifikasirisikokeselamatan.isDisplayable()) {
            identifikasirisikokeselamatan=new PCRAICRAIdentifikasiRisikoKeselamatan(null,false);
            identifikasirisikokeselamatan.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            identifikasirisikokeselamatan.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    identifikasirisikokeselamatan=null;
                }
            });

            identifikasirisikokeselamatan.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            identifikasirisikokeselamatan.setLocationRelativeTo(internalFrame1);
        }
        if (identifikasirisikokeselamatan == null) return;
        if (identifikasirisikokeselamatan.isVisible()) {
            identifikasirisikokeselamatan.toFront();
            return;
        }
        identifikasirisikokeselamatan.setVisible(true); 
    }//GEN-LAST:event_BtnTambahRisikoKeselamatanActionPerformed

    private void BtnAllRisikoKeselamatanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllRisikoKeselamatanActionPerformed
        TCariRisikoKeselamatan.setText("");
        runBackground(() ->tampilIdentifikasiRisikoKeselamatan());
    }//GEN-LAST:event_BtnAllRisikoKeselamatanActionPerformed

    private void BtnAllRisikoKeselamatanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllRisikoKeselamatanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnAllRisikoKeselamatanKeyPressed

    private void BtnCariRisikoKeselamatanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariRisikoKeselamatanActionPerformed
        runBackground(() ->tampilIdentifikasiRisikoKeselamatan2());
    }//GEN-LAST:event_BtnCariRisikoKeselamatanActionPerformed

    private void BtnCariRisikoKeselamatanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariRisikoKeselamatanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnCariRisikoKeselamatanKeyPressed

    private void TCariRisikoKeselamatanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariRisikoKeselamatanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            runBackground(() ->tampilIdentifikasiRisikoKeselamatan2());
        }else if((evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN)||(evt.getKeyCode()==KeyEvent.VK_TAB)){
            //Rencana.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            //KetDokter.requestFocus();
        }
    }//GEN-LAST:event_TCariRisikoKeselamatanKeyPressed

    private void BtnTambahRisikoUtilitasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahRisikoUtilitasActionPerformed
        if (identifikasirisikoutilitas == null || !identifikasirisikoutilitas.isDisplayable()) {
            identifikasirisikoutilitas=new PCRAICRAIdentifikasiRisikoUtilitas(null,false);
            identifikasirisikoutilitas.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            identifikasirisikoutilitas.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    identifikasirisikoutilitas=null;
                }
            });

            identifikasirisikoutilitas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            identifikasirisikoutilitas.setLocationRelativeTo(internalFrame1);
        }
        if (identifikasirisikoutilitas == null) return;
        if (identifikasirisikoutilitas.isVisible()) {
            identifikasirisikoutilitas.toFront();
            return;
        }
        identifikasirisikoutilitas.setVisible(true); 
    }//GEN-LAST:event_BtnTambahRisikoUtilitasActionPerformed

    private void BtnAllRisikoUtilitasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllRisikoUtilitasActionPerformed
        TCariRisikoUtilitas.setText("");
        runBackground(() ->tampilIdentifikasiRisikoUtilitas());
    }//GEN-LAST:event_BtnAllRisikoUtilitasActionPerformed

    private void BtnAllRisikoUtilitasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllRisikoUtilitasKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnAllRisikoUtilitasKeyPressed

    private void BtnCariRisikoUtilitasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariRisikoUtilitasActionPerformed
        runBackground(() ->tampilIdentifikasiRisikoUtilitas2());
    }//GEN-LAST:event_BtnCariRisikoUtilitasActionPerformed

    private void BtnCariRisikoUtilitasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariRisikoUtilitasKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnCariRisikoUtilitasKeyPressed

    private void TCariRisikoUtilitasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariRisikoUtilitasKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            runBackground(() ->tampilIdentifikasiRisikoUtilitas2());
        }else if((evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN)||(evt.getKeyCode()==KeyEvent.VK_TAB)){
            //Rencana.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            //KetDokter.requestFocus();
        }
    }//GEN-LAST:event_TCariRisikoUtilitasKeyPressed

    private void KdDokter2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdDokter2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KdDokter2KeyPressed

    private void BtnDokter2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokter2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnDokter2ActionPerformed

    private void BtnDokter2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokter2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnDokter2KeyPressed

    private void TCariTindakanPengendalianKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariTindakanPengendalianKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            runBackground(() ->tampilTindakanPengendalian2());
        }else if((evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN)||(evt.getKeyCode()==KeyEvent.VK_TAB)){
            //Rencana.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            //KetDokter.requestFocus();
        }
    }//GEN-LAST:event_TCariTindakanPengendalianKeyPressed

    private void BtnCariTindakanPengendalianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariTindakanPengendalianActionPerformed
        runBackground(() ->tampilTindakanPengendalian2());
    }//GEN-LAST:event_BtnCariTindakanPengendalianActionPerformed

    private void BtnCariTindakanPengendalianKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariTindakanPengendalianKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnCariTindakanPengendalianKeyPressed

    private void BtnAllTindakanPengendalianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllTindakanPengendalianActionPerformed
        TCariTindakanPengendalian.setText("");
        runBackground(() ->tampilTindakanPengendalian());
    }//GEN-LAST:event_BtnAllTindakanPengendalianActionPerformed

    private void BtnAllTindakanPengendalianKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllTindakanPengendalianKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnAllTindakanPengendalianKeyPressed

    private void BtnTambahTindakanPengendalianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahTindakanPengendalianActionPerformed
        if (tindakanpengendalian == null || !tindakanpengendalian.isDisplayable()) {
            tindakanpengendalian=new PCRAICRATindakanPengendalian(null,false);
            tindakanpengendalian.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            tindakanpengendalian.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    tindakanpengendalian=null;
                }
            });

            tindakanpengendalian.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            tindakanpengendalian.setLocationRelativeTo(internalFrame1);
        }
        if (tindakanpengendalian == null) return;
        if (tindakanpengendalian.isVisible()) {
            tindakanpengendalian.toFront();
            return;
        }
        tindakanpengendalian.setVisible(true); 
    }//GEN-LAST:event_BtnTambahTindakanPengendalianActionPerformed

    private void KeluhanUtama1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeluhanUtama1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeluhanUtama1KeyPressed

    private void KeluhanUtama2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeluhanUtama2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeluhanUtama2KeyPressed

    private void RPS1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RPS1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_RPS1KeyPressed

    private void BtnTambahPersyaratanDipenuhiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahPersyaratanDipenuhiActionPerformed
        if (persyaratandipenuhi == null || !persyaratandipenuhi.isDisplayable()) {
            persyaratandipenuhi=new PCRAICRAPersyaratanHarusDipenuhi(null,false);
            persyaratandipenuhi.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            persyaratandipenuhi.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    persyaratandipenuhi=null;
                }
            });

            persyaratandipenuhi.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            persyaratandipenuhi.setLocationRelativeTo(internalFrame1);
        }
        if (persyaratandipenuhi == null) return;
        if (persyaratandipenuhi.isVisible()) {
            persyaratandipenuhi.toFront();
            return;
        }
        persyaratandipenuhi.setVisible(true); 
    }//GEN-LAST:event_BtnTambahPersyaratanDipenuhiActionPerformed

    private void BtnAllPersyaratanDipenuhiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllPersyaratanDipenuhiActionPerformed
        TCariPersyaratanDipenuhi.setText("");
        runBackground(() ->tampilPersyaratanDipenuhi());
    }//GEN-LAST:event_BtnAllPersyaratanDipenuhiActionPerformed

    private void BtnAllPersyaratanDipenuhiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllPersyaratanDipenuhiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnAllPersyaratanDipenuhiKeyPressed

    private void BtnCariPersyaratanDipenuhiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariPersyaratanDipenuhiActionPerformed
        runBackground(() ->tampilPersyaratanDipenuhi2());
    }//GEN-LAST:event_BtnCariPersyaratanDipenuhiActionPerformed

    private void BtnCariPersyaratanDipenuhiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariPersyaratanDipenuhiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnCariPersyaratanDipenuhiKeyPressed

    private void TCariPersyaratanDipenuhiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariPersyaratanDipenuhiKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            runBackground(() ->tampilPersyaratanDipenuhi2());
        }else if((evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN)||(evt.getKeyCode()==KeyEvent.VK_TAB)){
            //Rencana.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            //KetDokter.requestFocus();
        }
    }//GEN-LAST:event_TCariPersyaratanDipenuhiKeyPressed

    private void RPS2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RPS2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_RPS2KeyPressed

    private void KdDokter3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdDokter3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KdDokter3KeyPressed

    private void BtnDokter3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokter3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnDokter3ActionPerformed

    private void BtnDokter3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokter3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnDokter3KeyPressed

    private void BtnDokter4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokter4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnDokter4ActionPerformed

    private void BtnDokter4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokter4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnDokter4KeyPressed

    private void Hubungan1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Hubungan1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Hubungan1KeyPressed

    private void BtnDokter5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokter5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnDokter5ActionPerformed

    private void BtnDokter5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokter5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnDokter5KeyPressed

    private void TglAsuhan2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglAsuhan2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglAsuhan2KeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            PCRAICRAPengkajianRisikoPraKonstruksi dialog = new PCRAICRAPengkajianRisikoPraKonstruksi(new javax.swing.JFrame(), true);
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
    private widget.ComboBox Anamnesis;
    private widget.Button BtnAll;
    private widget.Button BtnAllKelomokRisiko;
    private widget.Button BtnAllPersyaratanDipenuhi;
    private widget.Button BtnAllRisikoInfeksi;
    private widget.Button BtnAllRisikoKebakaran;
    private widget.Button BtnAllRisikoKeselamatan;
    private widget.Button BtnAllRisikoUtilitas;
    private widget.Button BtnAllTindakanPengendalian;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnCariKelompokRisiko;
    private widget.Button BtnCariPersyaratanDipenuhi;
    private widget.Button BtnCariRisikoInfeksi;
    private widget.Button BtnCariRisikoKebakarab;
    private widget.Button BtnCariRisikoKeselamatan;
    private widget.Button BtnCariRisikoUtilitas;
    private widget.Button BtnCariTindakanPengendalian;
    private widget.Button BtnDokter;
    private widget.Button BtnDokter1;
    private widget.Button BtnDokter2;
    private widget.Button BtnDokter3;
    private widget.Button BtnDokter4;
    private widget.Button BtnDokter5;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.Button BtnTambahMasalah;
    private widget.Button BtnTambahPersyaratanDipenuhi;
    private widget.Button BtnTambahRisikoInfeksi;
    private widget.Button BtnTambahRisikoKebakaran;
    private widget.Button BtnTambahRisikoKeselamatan;
    private widget.Button BtnTambahRisikoUtilitas;
    private widget.Button BtnTambahTindakanPengendalian;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.PanelBiasa FormInput;
    private widget.TextBox Hubungan;
    private widget.TextBox Hubungan1;
    private widget.TextBox Jk;
    private widget.TextBox Jk1;
    private widget.TextBox KdDokter;
    private widget.TextBox KdDokter1;
    private widget.TextBox KdDokter2;
    private widget.TextBox KdDokter3;
    private widget.TextArea KeluhanUtama;
    private widget.TextArea KeluhanUtama1;
    private widget.TextArea KeluhanUtama2;
    private widget.Label LCount;
    private widget.editorpane LoadHTML;
    private widget.TextBox LokasiProyek;
    private javax.swing.JMenuItem MnPenilaianMedis;
    private widget.TextBox NamaProyek;
    private widget.TextBox NmDokter;
    private widget.TextBox NmDokter1;
    private widget.TextBox NmDokter2;
    private widget.TextBox NmDokter3;
    private widget.TextBox NmDokter4;
    private widget.TextBox NmDokter5;
    private widget.TextBox NoProyek;
    private widget.TextArea RPS;
    private widget.TextArea RPS1;
    private widget.TextArea RPS2;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll10;
    private widget.ScrollPane Scroll11;
    private widget.ScrollPane Scroll12;
    private widget.ScrollPane Scroll6;
    private widget.ScrollPane Scroll7;
    private widget.ScrollPane Scroll8;
    private widget.ScrollPane Scroll9;
    private widget.TextBox TCari;
    private widget.TextBox TCariKelompokRisikoArea;
    private widget.TextBox TCariPersyaratanDipenuhi;
    private widget.TextBox TCariRisikoInfeksi;
    private widget.TextBox TCariRisikoKebakaran;
    private widget.TextBox TCariRisikoKeselamatan;
    private widget.TextBox TCariRisikoUtilitas;
    private widget.TextBox TCariTindakanPengendalian;
    private widget.TextBox TNoRM;
    private javax.swing.JTabbedPane TabRawat;
    private widget.TextBox TanggalRegistrasi;
    private widget.Tanggal TglAsuhan;
    private widget.Tanggal TglAsuhan1;
    private widget.Tanggal TglAsuhan2;
    private widget.TextBox TglLahir;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel12;
    private widget.Label jLabel13;
    private widget.Label jLabel14;
    private widget.Label jLabel15;
    private widget.Label jLabel19;
    private widget.Label jLabel21;
    private widget.Label jLabel30;
    private widget.Label jLabel31;
    private widget.Label jLabel34;
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
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
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
    private widget.Label label30;
    private widget.Label label31;
    private widget.Label label32;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollInput;
    private widget.ScrollPane scrollPane1;
    private widget.ScrollPane scrollPane2;
    private widget.ScrollPane scrollPane3;
    private widget.ScrollPane scrollPane7;
    private widget.ScrollPane scrollPane8;
    private widget.ScrollPane scrollPane9;
    private widget.Table tbIdentifikasiRisikoInfeksi;
    private widget.Table tbIdentifikasiRisikoKebakaran;
    private widget.Table tbIdentifikasiRisikoKeselamatan;
    private widget.Table tbIdentifikasiRisikoUtilitas;
    private widget.Table tbKelompokRisikoArea;
    private widget.Table tbObat;
    private widget.Table tbPersyaratanDipenuhi;
    private widget.Table tbTindakanPengendalian;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            if(TCari.getText().trim().equals("")){
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penilaian_medis_ralan.tanggal,"+
                        "penilaian_medis_ralan.kd_dokter,penilaian_medis_ralan.anamnesis,penilaian_medis_ralan.hubungan,penilaian_medis_ralan.keluhan_utama,penilaian_medis_ralan.rps,penilaian_medis_ralan.rpk,penilaian_medis_ralan.rpd,penilaian_medis_ralan.rpo,penilaian_medis_ralan.alergi,"+
                        "penilaian_medis_ralan.keadaan,penilaian_medis_ralan.gcs,penilaian_medis_ralan.kesadaran,penilaian_medis_ralan.td,penilaian_medis_ralan.nadi,penilaian_medis_ralan.rr,penilaian_medis_ralan.suhu,penilaian_medis_ralan.spo,penilaian_medis_ralan.bb,penilaian_medis_ralan.tb,"+
                        "penilaian_medis_ralan.kepala,penilaian_medis_ralan.gigi,penilaian_medis_ralan.tht,penilaian_medis_ralan.thoraks,penilaian_medis_ralan.abdomen,penilaian_medis_ralan.ekstremitas,penilaian_medis_ralan.genital,penilaian_medis_ralan.kulit,"+
                        "penilaian_medis_ralan.ket_fisik,penilaian_medis_ralan.ket_lokalis,penilaian_medis_ralan.penunjang,penilaian_medis_ralan.diagnosis,penilaian_medis_ralan.tata,penilaian_medis_ralan.konsulrujuk,dokter.nm_dokter "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join penilaian_medis_ralan on reg_periksa.no_rawat=penilaian_medis_ralan.no_rawat "+
                        "inner join dokter on penilaian_medis_ralan.kd_dokter=dokter.kd_dokter where "+
                        "penilaian_medis_ralan.tanggal between ? and ? order by penilaian_medis_ralan.tanggal");
            }else{
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penilaian_medis_ralan.tanggal,"+
                        "penilaian_medis_ralan.kd_dokter,penilaian_medis_ralan.anamnesis,penilaian_medis_ralan.hubungan,penilaian_medis_ralan.keluhan_utama,penilaian_medis_ralan.rps,penilaian_medis_ralan.rpk,penilaian_medis_ralan.rpd,penilaian_medis_ralan.rpo,penilaian_medis_ralan.alergi,"+
                        "penilaian_medis_ralan.keadaan,penilaian_medis_ralan.gcs,penilaian_medis_ralan.kesadaran,penilaian_medis_ralan.td,penilaian_medis_ralan.nadi,penilaian_medis_ralan.rr,penilaian_medis_ralan.suhu,penilaian_medis_ralan.spo,penilaian_medis_ralan.bb,penilaian_medis_ralan.tb,"+
                        "penilaian_medis_ralan.kepala,penilaian_medis_ralan.gigi,penilaian_medis_ralan.tht,penilaian_medis_ralan.thoraks,penilaian_medis_ralan.abdomen,penilaian_medis_ralan.ekstremitas,penilaian_medis_ralan.genital,penilaian_medis_ralan.kulit,"+
                        "penilaian_medis_ralan.ket_fisik,penilaian_medis_ralan.ket_lokalis,penilaian_medis_ralan.penunjang,penilaian_medis_ralan.diagnosis,penilaian_medis_ralan.tata,penilaian_medis_ralan.konsulrujuk,dokter.nm_dokter "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join penilaian_medis_ralan on reg_periksa.no_rawat=penilaian_medis_ralan.no_rawat "+
                        "inner join dokter on penilaian_medis_ralan.kd_dokter=dokter.kd_dokter where "+
                        "penilaian_medis_ralan.tanggal between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or "+
                        "penilaian_medis_ralan.kd_dokter like ? or dokter.nm_dokter like ?) order by penilaian_medis_ralan.tanggal");
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
                        rs.getString("anamnesis"),rs.getString("hubungan"),rs.getString("keluhan_utama"),rs.getString("rps"),rs.getString("rpd"),rs.getString("rpk"),rs.getString("rpo"),rs.getString("alergi"),
                        rs.getString("keadaan"),rs.getString("gcs"),rs.getString("kesadaran"),rs.getString("td"),rs.getString("nadi"),rs.getString("rr"),rs.getString("suhu"),rs.getString("spo"),rs.getString("bb"),
                        rs.getString("tb"),rs.getString("kepala"),rs.getString("gigi"),rs.getString("tht"),rs.getString("thoraks"),rs.getString("abdomen"),rs.getString("genital"),rs.getString("ekstremitas"),
                        rs.getString("kulit"),rs.getString("ket_fisik"),rs.getString("ket_lokalis"),rs.getString("penunjang"),rs.getString("diagnosis"),rs.getString("tata"),rs.getString("konsulrujuk")
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
        TglAsuhan.setDate(new Date());
        TabRawat.setSelectedIndex(0);
        Anamnesis.requestFocus();
    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            NoProyek.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()); 
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            NamaProyek.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            Jk.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString()); 
            Anamnesis.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            Hubungan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            KeluhanUtama.setText(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            RPS.setText(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            Valid.SetTgl2(TglAsuhan,tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());
        }
    }

    private void isRawat() {
        try {
            ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rkm_medis,pasien.nm_pasien, if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,"+
                    "reg_periksa.tgl_registrasi,reg_periksa.jam_reg "+
                    "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "where reg_periksa.no_rawat=?");
            try {
                ps.setString(1,NoProyek.getText());
                rs=ps.executeQuery();
                if(rs.next()){
                    TNoRM.setText(rs.getString("no_rkm_medis"));
                    DTPCari1.setDate(rs.getDate("tgl_registrasi"));
                    NamaProyek.setText(rs.getString("nm_pasien"));
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
        NoProyek.setText(norwt);
        TCari.setText(norwt);
        DTPCari2.setDate(tgl2);    
        isRawat(); 
    }
    
    public void isCek(){
        //BtnSimpan.setEnabled(akses.getpenilaian_awal_medis_ralan());
        //BtnHapus.setEnabled(akses.getpenilaian_awal_medis_ralan());
        //BtnEdit.setEnabled(akses.getpenilaian_awal_medis_ralan());
        if(akses.getjml2()>=1){
            KdDokter.setEditable(false);
            BtnDokter.setEnabled(false);
            KdDokter.setText(akses.getkode());
            NmDokter.setText(Sequel.CariDokter(KdDokter.getText()));
            if(NmDokter.getText().equals("")){
                KdDokter.setText("");
                JOptionPane.showMessageDialog(null,"User login bukan Dokter...!!");
            }
        }  
        
        if(TANGGALMUNDUR.equals("no")){
            if(!akses.getkode().equals("Admin Utama")){
                TglAsuhan.setEditable(false);
                TglAsuhan.setEnabled(false);
            }
        }
    }
    
    public void setTampil(){
       TabRawat.setSelectedIndex(1);
    }

    private void tampilKelompokRisiko() {
        try{
            Valid.tabelKosong(tabModeKelompokRisikoArea);
            file=new File("./cache/pcrakelompokrisikoarea.iyem");
            file.createNewFile();
            fileWriter = new FileWriter(file);
            StringBuilder iyembuilder = new StringBuilder();
            ps=koneksi.prepareStatement("select pcra_icra_lokasi_kelompok_risiko_area.kode_area,pcra_icra_lokasi_kelompok_risiko_area.nama_area from pcra_icra_lokasi_kelompok_risiko_area");
            try {
                rs=ps.executeQuery();
                while(rs.next()){
                    tabModeKelompokRisikoArea.addRow(new Object[]{false,rs.getString(1),rs.getString(2)});
                    iyembuilder.append("{\"Kode\":\"").append(rs.getString(1)).append("\",\"Nama\":\"").append(rs.getString(2)).append("\"},");
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
            
            if (iyembuilder.length() > 0) {
                iyembuilder.setLength(iyembuilder.length() - 1);
                fileWriter.write("{\"pcrakelompokrisikoarea\":["+iyembuilder+"]}");
                fileWriter.flush();
            }
            
            fileWriter.close();
            iyembuilder=null;
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }
    
    private void tampilKelompokRisiko2() {
        try{
            jml=0;
            for(i=0;i<tbKelompokRisikoArea.getRowCount();i++){
                if(tbKelompokRisikoArea.getValueAt(i,0).toString().equals("true")){
                    jml++;
                }
            }

            pilih=new boolean[jml]; 
            kode=new String[jml];
            nama=new String[jml];

            index=0;        
            for(i=0;i<tbKelompokRisikoArea.getRowCount();i++){
                if(tbKelompokRisikoArea.getValueAt(i,0).toString().equals("true")){
                    pilih[index]=true;
                    kode[index]=tbKelompokRisikoArea.getValueAt(i,1).toString();
                    nama[index]=tbKelompokRisikoArea.getValueAt(i,2).toString();
                    index++;
                }
            } 

            Valid.tabelKosong(tabModeKelompokRisikoArea);

            for(i=0;i<jml;i++){
                tabModeKelompokRisikoArea.addRow(new Object[] {
                    pilih[i],kode[i],nama[i]
                });
            }
            
            pilih=null;
            kode=null;
            nama=null;
            
            myObj = new FileReader("./cache/pcrakelompokrisikoarea.iyem");
            root = mapper.readTree(myObj);
            response = root.path("pcrakelompokrisikoarea");
            if(response.isArray()){
                for(JsonNode list:response){
                    if(list.path("Kode").asText().toLowerCase().contains(TCariKelompokRisikoArea.getText().toLowerCase())||list.path("Nama").asText().toLowerCase().contains(TCariKelompokRisikoArea.getText().toLowerCase())){
                        tabModeKelompokRisikoArea.addRow(new Object[]{
                            false,list.path("Kode").asText(),list.path("Nama").asText()
                        });                    
                    }
                }
            }
            myObj.close();
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }
    
    private void tampilIdentifikasiRisikoKebakaran() {
        try{
            Valid.tabelKosong(tabModeIdentifikasiRisikoKebakaran);
            file=new File("./cache/pcraidentifikasirisikokebakaran.iyem");
            file.createNewFile();
            fileWriter = new FileWriter(file);
            StringBuilder iyembuilder = new StringBuilder();
            ps=koneksi.prepareStatement("select pcra_icra_identifkasi_risiko_kebakaran.kode_risiko,pcra_icra_identifkasi_risiko_kebakaran.nama_risiko from pcra_icra_identifkasi_risiko_kebakaran");
            try {
                rs=ps.executeQuery();
                while(rs.next()){
                    tabModeIdentifikasiRisikoKebakaran.addRow(new Object[]{false,rs.getString(1),rs.getString(2)});
                    iyembuilder.append("{\"Kode\":\"").append(rs.getString(1)).append("\",\"Nama\":\"").append(rs.getString(2)).append("\"},");
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
            
            if (iyembuilder.length() > 0) {
                iyembuilder.setLength(iyembuilder.length() - 1);
                fileWriter.write("{\"pcraidentifikasirisikokebakaran\":["+iyembuilder+"]}");
                fileWriter.flush();
            }
            
            fileWriter.close();
            iyembuilder=null;
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }
    
    private void tampilIdentifikasiRisikoKebakaran2() {
        try{
            jml=0;
            for(i=0;i<tbIdentifikasiRisikoKebakaran.getRowCount();i++){
                if(tbIdentifikasiRisikoKebakaran.getValueAt(i,0).toString().equals("true")){
                    jml++;
                }
            }

            pilih=new boolean[jml]; 
            kode=new String[jml];
            nama=new String[jml];

            index=0;        
            for(i=0;i<tbIdentifikasiRisikoKebakaran.getRowCount();i++){
                if(tbIdentifikasiRisikoKebakaran.getValueAt(i,0).toString().equals("true")){
                    pilih[index]=true;
                    kode[index]=tbIdentifikasiRisikoKebakaran.getValueAt(i,1).toString();
                    nama[index]=tbIdentifikasiRisikoKebakaran.getValueAt(i,2).toString();
                    index++;
                }
            } 

            Valid.tabelKosong(tabModeIdentifikasiRisikoKebakaran);

            for(i=0;i<jml;i++){
                tabModeIdentifikasiRisikoKebakaran.addRow(new Object[] {
                    pilih[i],kode[i],nama[i]
                });
            }
            
            pilih=null;
            kode=null;
            nama=null;
            
            myObj = new FileReader("./cache/pcraidentifikasirisikokebakaran.iyem");
            root = mapper.readTree(myObj);
            response = root.path("pcraidentifikasirisikokebakaran");
            if(response.isArray()){
                for(JsonNode list:response){
                    if(list.path("Kode").asText().toLowerCase().contains(TCariRisikoKebakaran.getText().toLowerCase())||list.path("Nama").asText().toLowerCase().contains(TCariRisikoKebakaran.getText().toLowerCase())){
                        tabModeIdentifikasiRisikoKebakaran.addRow(new Object[]{
                            false,list.path("Kode").asText(),list.path("Nama").asText()
                        });                    
                    }
                }
            }
            myObj.close();
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }
    
    private void tampilIdentifikasiRisikoInfeksi() {
        try{
            Valid.tabelKosong(tabModeIdentifikasiRisikoInfeksi);
            file=new File("./cache/pcraidentifikasirisikoinfeksi.iyem");
            file.createNewFile();
            fileWriter = new FileWriter(file);
            StringBuilder iyembuilder = new StringBuilder();
            ps=koneksi.prepareStatement("select pcra_icra_identifkasi_risiko_infeksi.kode_risiko,pcra_icra_identifkasi_risiko_infeksi.nama_risiko from pcra_icra_identifkasi_risiko_infeksi");
            try {
                rs=ps.executeQuery();
                while(rs.next()){
                    tabModeIdentifikasiRisikoInfeksi.addRow(new Object[]{false,rs.getString(1),rs.getString(2)});
                    iyembuilder.append("{\"Kode\":\"").append(rs.getString(1)).append("\",\"Nama\":\"").append(rs.getString(2)).append("\"},");
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
            
            if (iyembuilder.length() > 0) {
                iyembuilder.setLength(iyembuilder.length() - 1);
                fileWriter.write("{\"pcraidentifikasirisikoinfeksi\":["+iyembuilder+"]}");
                fileWriter.flush();
            }
            
            fileWriter.close();
            iyembuilder=null;
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }
    
    private void tampilIdentifikasiRisikoInfeksi2() {
        try{
            jml=0;
            for(i=0;i<tbIdentifikasiRisikoInfeksi.getRowCount();i++){
                if(tbIdentifikasiRisikoInfeksi.getValueAt(i,0).toString().equals("true")){
                    jml++;
                }
            }

            pilih=new boolean[jml]; 
            kode=new String[jml];
            nama=new String[jml];

            index=0;        
            for(i=0;i<tbIdentifikasiRisikoInfeksi.getRowCount();i++){
                if(tbIdentifikasiRisikoInfeksi.getValueAt(i,0).toString().equals("true")){
                    pilih[index]=true;
                    kode[index]=tbIdentifikasiRisikoInfeksi.getValueAt(i,1).toString();
                    nama[index]=tbIdentifikasiRisikoInfeksi.getValueAt(i,2).toString();
                    index++;
                }
            } 

            Valid.tabelKosong(tabModeIdentifikasiRisikoInfeksi);

            for(i=0;i<jml;i++){
                tabModeIdentifikasiRisikoInfeksi.addRow(new Object[] {
                    pilih[i],kode[i],nama[i]
                });
            }
            
            pilih=null;
            kode=null;
            nama=null;
            
            myObj = new FileReader("./cache/pcraidentifikasirisikoinfeksi.iyem");
            root = mapper.readTree(myObj);
            response = root.path("pcraidentifikasirisikoinfeksi");
            if(response.isArray()){
                for(JsonNode list:response){
                    if(list.path("Kode").asText().toLowerCase().contains(TCariRisikoInfeksi.getText().toLowerCase())||list.path("Nama").asText().toLowerCase().contains(TCariRisikoInfeksi.getText().toLowerCase())){
                        tabModeIdentifikasiRisikoInfeksi.addRow(new Object[]{
                            false,list.path("Kode").asText(),list.path("Nama").asText()
                        });                    
                    }
                }
            }
            myObj.close();
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }
    
    private void tampilIdentifikasiRisikoKeselamatan() {
        try{
            Valid.tabelKosong(tabModeIdentifikasiRisikoKeselamatan);
            file=new File("./cache/pcraidentifikasirisikokeselamatan.iyem");
            file.createNewFile();
            fileWriter = new FileWriter(file);
            StringBuilder iyembuilder = new StringBuilder();
            ps=koneksi.prepareStatement("select pcra_icra_identifkasi_risiko_keselamatan.kode_risiko,pcra_icra_identifkasi_risiko_keselamatan.nama_risiko from pcra_icra_identifkasi_risiko_keselamatan");
            try {
                rs=ps.executeQuery();
                while(rs.next()){
                    tabModeIdentifikasiRisikoKeselamatan.addRow(new Object[]{false,rs.getString(1),rs.getString(2)});
                    iyembuilder.append("{\"Kode\":\"").append(rs.getString(1)).append("\",\"Nama\":\"").append(rs.getString(2)).append("\"},");
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
            
            if (iyembuilder.length() > 0) {
                iyembuilder.setLength(iyembuilder.length() - 1);
                fileWriter.write("{\"pcraidentifikasirisikokeselamatan\":["+iyembuilder+"]}");
                fileWriter.flush();
            }
            
            fileWriter.close();
            iyembuilder=null;
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }
    
    private void tampilIdentifikasiRisikoKeselamatan2() {
        try{
            jml=0;
            for(i=0;i<tbIdentifikasiRisikoKeselamatan.getRowCount();i++){
                if(tbIdentifikasiRisikoKeselamatan.getValueAt(i,0).toString().equals("true")){
                    jml++;
                }
            }

            pilih=new boolean[jml]; 
            kode=new String[jml];
            nama=new String[jml];

            index=0;        
            for(i=0;i<tbIdentifikasiRisikoKeselamatan.getRowCount();i++){
                if(tbIdentifikasiRisikoKeselamatan.getValueAt(i,0).toString().equals("true")){
                    pilih[index]=true;
                    kode[index]=tbIdentifikasiRisikoKeselamatan.getValueAt(i,1).toString();
                    nama[index]=tbIdentifikasiRisikoKeselamatan.getValueAt(i,2).toString();
                    index++;
                }
            } 

            Valid.tabelKosong(tabModeIdentifikasiRisikoKeselamatan);

            for(i=0;i<jml;i++){
                tabModeIdentifikasiRisikoKeselamatan.addRow(new Object[] {
                    pilih[i],kode[i],nama[i]
                });
            }
            
            pilih=null;
            kode=null;
            nama=null;
            
            myObj = new FileReader("./cache/pcraidentifikasirisikokeselamatan.iyem");
            root = mapper.readTree(myObj);
            response = root.path("pcraidentifikasirisikokeselamatan");
            if(response.isArray()){
                for(JsonNode list:response){
                    if(list.path("Kode").asText().toLowerCase().contains(TCariRisikoKeselamatan.getText().toLowerCase())||list.path("Nama").asText().toLowerCase().contains(TCariRisikoKeselamatan.getText().toLowerCase())){
                        tabModeIdentifikasiRisikoKeselamatan.addRow(new Object[]{
                            false,list.path("Kode").asText(),list.path("Nama").asText()
                        });                    
                    }
                }
            }
            myObj.close();
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }
    
    private void tampilIdentifikasiRisikoUtilitas() {
        try{
            Valid.tabelKosong(tabModeIdentifikasiRisikoUtilitas);
            file=new File("./cache/pcraidentifikasirisikoutilitas.iyem");
            file.createNewFile();
            fileWriter = new FileWriter(file);
            StringBuilder iyembuilder = new StringBuilder();
            ps=koneksi.prepareStatement("select pcra_icra_identifkasi_risiko_utilitas.kode_risiko,pcra_icra_identifkasi_risiko_utilitas.nama_risiko from pcra_icra_identifkasi_risiko_utilitas");
            try {
                rs=ps.executeQuery();
                while(rs.next()){
                    tabModeIdentifikasiRisikoUtilitas.addRow(new Object[]{false,rs.getString(1),rs.getString(2)});
                    iyembuilder.append("{\"Kode\":\"").append(rs.getString(1)).append("\",\"Nama\":\"").append(rs.getString(2)).append("\"},");
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
            
            if (iyembuilder.length() > 0) {
                iyembuilder.setLength(iyembuilder.length() - 1);
                fileWriter.write("{\"pcraidentifikasirisikoutilitas\":["+iyembuilder+"]}");
                fileWriter.flush();
            }
            
            fileWriter.close();
            iyembuilder=null;
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }
    
    private void tampilIdentifikasiRisikoUtilitas2() {
        try{
            jml=0;
            for(i=0;i<tbIdentifikasiRisikoUtilitas.getRowCount();i++){
                if(tbIdentifikasiRisikoUtilitas.getValueAt(i,0).toString().equals("true")){
                    jml++;
                }
            }

            pilih=new boolean[jml]; 
            kode=new String[jml];
            nama=new String[jml];

            index=0;        
            for(i=0;i<tbIdentifikasiRisikoUtilitas.getRowCount();i++){
                if(tbIdentifikasiRisikoUtilitas.getValueAt(i,0).toString().equals("true")){
                    pilih[index]=true;
                    kode[index]=tbIdentifikasiRisikoUtilitas.getValueAt(i,1).toString();
                    nama[index]=tbIdentifikasiRisikoUtilitas.getValueAt(i,2).toString();
                    index++;
                }
            } 

            Valid.tabelKosong(tabModeIdentifikasiRisikoUtilitas);

            for(i=0;i<jml;i++){
                tabModeIdentifikasiRisikoUtilitas.addRow(new Object[] {
                    pilih[i],kode[i],nama[i]
                });
            }
            
            pilih=null;
            kode=null;
            nama=null;
            
            myObj = new FileReader("./cache/pcraidentifikasirisikoutilitas.iyem");
            root = mapper.readTree(myObj);
            response = root.path("pcraidentifikasirisikoutilitas");
            if(response.isArray()){
                for(JsonNode list:response){
                    if(list.path("Kode").asText().toLowerCase().contains(TCariRisikoUtilitas.getText().toLowerCase())||list.path("Nama").asText().toLowerCase().contains(TCariRisikoUtilitas.getText().toLowerCase())){
                        tabModeIdentifikasiRisikoUtilitas.addRow(new Object[]{
                            false,list.path("Kode").asText(),list.path("Nama").asText()
                        });                    
                    }
                }
            }
            myObj.close();
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }
    
    private void tampilTindakanPengendalian() {
        try{
            Valid.tabelKosong(tabModeTindakanPengendalian);
            file=new File("./cache/pcratindakanpengendalian.iyem");
            file.createNewFile();
            fileWriter = new FileWriter(file);
            StringBuilder iyembuilder = new StringBuilder();
            ps=koneksi.prepareStatement("select pcra_icra_tindakan_pengendalian.kode_pengendalian,pcra_icra_tindakan_pengendalian.nama_pengendalian from pcra_icra_tindakan_pengendalian");
            try {
                rs=ps.executeQuery();
                while(rs.next()){
                    tabModeTindakanPengendalian.addRow(new Object[]{false,rs.getString(1),rs.getString(2)});
                    iyembuilder.append("{\"Kode\":\"").append(rs.getString(1)).append("\",\"Nama\":\"").append(rs.getString(2)).append("\"},");
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
            
            if (iyembuilder.length() > 0) {
                iyembuilder.setLength(iyembuilder.length() - 1);
                fileWriter.write("{\"pcratindakanpengendalian\":["+iyembuilder+"]}");
                fileWriter.flush();
            }
            
            fileWriter.close();
            iyembuilder=null;
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }
    
    private void tampilTindakanPengendalian2() {
        try{
            jml=0;
            for(i=0;i<tbTindakanPengendalian.getRowCount();i++){
                if(tbTindakanPengendalian.getValueAt(i,0).toString().equals("true")){
                    jml++;
                }
            }

            pilih=new boolean[jml]; 
            kode=new String[jml];
            nama=new String[jml];

            index=0;        
            for(i=0;i<tbTindakanPengendalian.getRowCount();i++){
                if(tbTindakanPengendalian.getValueAt(i,0).toString().equals("true")){
                    pilih[index]=true;
                    kode[index]=tbTindakanPengendalian.getValueAt(i,1).toString();
                    nama[index]=tbTindakanPengendalian.getValueAt(i,2).toString();
                    index++;
                }
            } 

            Valid.tabelKosong(tabModeTindakanPengendalian);

            for(i=0;i<jml;i++){
                tabModeTindakanPengendalian.addRow(new Object[] {
                    pilih[i],kode[i],nama[i]
                });
            }
            
            pilih=null;
            kode=null;
            nama=null;
            
            myObj = new FileReader("./cache/pcratindakanpengendalian.iyem");
            root = mapper.readTree(myObj);
            response = root.path("pcratindakanpengendalian");
            if(response.isArray()){
                for(JsonNode list:response){
                    if(list.path("Kode").asText().toLowerCase().contains(TCariTindakanPengendalian.getText().toLowerCase())||list.path("Nama").asText().toLowerCase().contains(TCariTindakanPengendalian.getText().toLowerCase())){
                        tabModeTindakanPengendalian.addRow(new Object[]{
                            false,list.path("Kode").asText(),list.path("Nama").asText()
                        });                    
                    }
                }
            }
            myObj.close();
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }
    
    private void tampilPersyaratanDipenuhi() {
        try{
            Valid.tabelKosong(tabModePersyaratanDipenuhi);
            file=new File("./cache/pcrapersyaratandipenuhi.iyem");
            file.createNewFile();
            fileWriter = new FileWriter(file);
            StringBuilder iyembuilder = new StringBuilder();
            ps=koneksi.prepareStatement("select pcra_icra_persyaratan_harus_dipenuhi.kode_persyaratan,pcra_icra_persyaratan_harus_dipenuhi.nama_persyaratan from pcra_icra_persyaratan_harus_dipenuhi");
            try {
                rs=ps.executeQuery();
                while(rs.next()){
                    tabModePersyaratanDipenuhi.addRow(new Object[]{false,rs.getString(1),rs.getString(2)});
                    iyembuilder.append("{\"Kode\":\"").append(rs.getString(1)).append("\",\"Nama\":\"").append(rs.getString(2)).append("\"},");
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
            
            if (iyembuilder.length() > 0) {
                iyembuilder.setLength(iyembuilder.length() - 1);
                fileWriter.write("{\"pcrapersyaratandipenuhi\":["+iyembuilder+"]}");
                fileWriter.flush();
            }
            
            fileWriter.close();
            iyembuilder=null;
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }
    
    private void tampilPersyaratanDipenuhi2() {
        try{
            jml=0;
            for(i=0;i<tbPersyaratanDipenuhi.getRowCount();i++){
                if(tbPersyaratanDipenuhi.getValueAt(i,0).toString().equals("true")){
                    jml++;
                }
            }

            pilih=new boolean[jml]; 
            kode=new String[jml];
            nama=new String[jml];

            index=0;        
            for(i=0;i<tbPersyaratanDipenuhi.getRowCount();i++){
                if(tbPersyaratanDipenuhi.getValueAt(i,0).toString().equals("true")){
                    pilih[index]=true;
                    kode[index]=tbPersyaratanDipenuhi.getValueAt(i,1).toString();
                    nama[index]=tbPersyaratanDipenuhi.getValueAt(i,2).toString();
                    index++;
                }
            } 

            Valid.tabelKosong(tabModePersyaratanDipenuhi);

            for(i=0;i<jml;i++){
                tabModePersyaratanDipenuhi.addRow(new Object[] {
                    pilih[i],kode[i],nama[i]
                });
            }
            
            pilih=null;
            kode=null;
            nama=null;
            
            myObj = new FileReader("./cache/pcrapersyaratandipenuhi.iyem");
            root = mapper.readTree(myObj);
            response = root.path("pcrapersyaratandipenuhi");
            if(response.isArray()){
                for(JsonNode list:response){
                    if(list.path("Kode").asText().toLowerCase().contains(TCariPersyaratanDipenuhi.getText().toLowerCase())||list.path("Nama").asText().toLowerCase().contains(TCariPersyaratanDipenuhi.getText().toLowerCase())){
                        tabModePersyaratanDipenuhi.addRow(new Object[]{
                            false,list.path("Kode").asText(),list.path("Nama").asText()
                        });                    
                    }
                }
            }
            myObj.close();
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
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

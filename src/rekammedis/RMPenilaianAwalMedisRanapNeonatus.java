/*
 * Kontribusi dari windiarto
 */


package rekammedis;

import fungsi.WarnaTable;
import fungsi.WarnaTable5;
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
public final class RMPenilaianAwalMedisRanapNeonatus extends javax.swing.JDialog {
    private final DefaultTableModel tabMode,tabModeRiwayatKehamilan,tabModeAPGAR;
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
    public RMPenilaianAwalMedisRanapNeonatus(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.RM","Nama Pasien","Tgl.Lahir","J.K.","Kode Dokter","Nama Dokter","Tanggal","Anamnesis","Hubungan","Keluhan Utama","Riwayat Penyakit Sekarang","Riwayat Penyakit Dahulu",
            "Riwayat Penyakit Keluarga","Riwayat Penggunakan Obat","Riwayat Alergi","Keadaan Umum","GCS","Kesadaran","TD(mmHg)","Nadi(x/menit)","RR(x/menit)","Suhu","SpO2","BB(Kg)","TB(cm)","Kepala",
            "Mata","Gigi & Mulut","THT","Thoraks","Jantung","Paru","Abdomen","Genital & Anus","Ekstremitas","Kulit","Ket.Pemeriksaan Fisik","Ket.Status Lokalis","Laboratorium","Radiologi",
            "Penunjang Lainnya","Diagnosis/Asesmen","Tatalaksana","Edukasi"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        
        tbObat.setModel(tabMode);
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 45; i++) {
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
                column.setPreferredWidth(80);
            }else if(i==35){
                column.setPreferredWidth(80);
            }else if(i==36){
                column.setPreferredWidth(80);
            }else if(i==37){
                column.setPreferredWidth(300);
            }else if(i==38){
                column.setPreferredWidth(200);
            }else if(i==39){
                column.setPreferredWidth(170);
            }else if(i==40){
                column.setPreferredWidth(170);
            }else if(i==41){
                column.setPreferredWidth(170);
            }else if(i==42){
                column.setPreferredWidth(150);
            }else if(i==43){
                column.setPreferredWidth(300);
            }else if(i==44){
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
        
        tabModeAPGAR=new DefaultTableModel(null,new Object[]{
                "Tanda","0","1","2","N 1'","N 5'","N 10'"
            }){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                    boolean a = false;
                    if ((colIndex==4)||(colIndex==5)||(colIndex==6)) {
                        a=true;
                    }
                    return a;
             }
             Class[] types = new Class[] {
                java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,
                java.lang.String.class,java.lang.String.class,java.lang.String.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }  
        };
        tbAPGAR.setModel(tabModeAPGAR);
        tbAPGAR.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        
        for (int i = 0; i < 7; i++) {
            TableColumn column = tbAPGAR.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(146);
            }else if(i==1){
                column.setPreferredWidth(146);
            }else if(i==2){
                column.setPreferredWidth(250);
            }else if(i==3){
                column.setPreferredWidth(145);
            }else if(i==4){
                column.setPreferredWidth(28);
            }else if(i==5){
                column.setPreferredWidth(28);
            }else if(i==6){
                column.setPreferredWidth(33);              
            }
        }
        
        tbAPGAR.setRowHeight(22);
        tbAPGAR.setDefaultRenderer(Object.class, new WarnaTable5());
        tabModeAPGAR.addRow(new Object[]{"Frekuensi Jantung","Tidak Ada","< 100","> 100","","",""});
        tabModeAPGAR.addRow(new Object[]{"Usaha Nafas","Tidak Ada","Lambat Tak Teratur","Menangis Kuat","","",""});
        tabModeAPGAR.addRow(new Object[]{"Tanus Otot","Lumpuh","Ext. Fleksi Sedikit","Gerakan Aktif","","",""});
        tabModeAPGAR.addRow(new Object[]{"Refleks","Tidak Ada Respon","Pergerakan Sedikit","Menangis","","",""});
        tabModeAPGAR.addRow(new Object[]{"Warna","Biru Pucat","Tubuh Kemerahan, Tangan & Kaki Biru","Kemerahan","","",""});
        
        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        Hubungan.setDocument(new batasInput((int)30).getKata(Hubungan));
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
        DlgRiwayatPersalinan = new javax.swing.JDialog();
        internalFrame4 = new widget.InternalFrame();
        panelBiasa2 = new widget.PanelBiasa();
        TanggalPersalinan = new widget.Tanggal();
        jLabel100 = new widget.Label();
        BtnKeluarKehamilan = new widget.Button();
        jLabel107 = new widget.Label();
        UsiaHamil = new widget.TextBox();
        BtnSimpanRiwayatKehamilan = new widget.Button();
        jLabel108 = new widget.Label();
        TempatPersalinan = new widget.TextBox();
        jLabel109 = new widget.Label();
        JenisPersalinan = new widget.TextBox();
        jLabel110 = new widget.Label();
        JK = new widget.ComboBox();
        jLabel111 = new widget.Label();
        Penolong = new widget.TextBox();
        jLabel112 = new widget.Label();
        BBPB = new widget.TextBox();
        jLabel113 = new widget.Label();
        Penyulit = new widget.TextBox();
        jLabel114 = new widget.Label();
        Keadaan = new widget.TextBox();
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
        Anamnesis = new widget.ComboBox();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel38 = new widget.Label();
        Hubungan = new widget.TextBox();
        jLabel99 = new widget.Label();
        label11 = new widget.Label();
        TglAsuhan = new widget.Tanggal();
        jLabel12 = new widget.Label();
        TNoRw1 = new widget.TextBox();
        jLabel13 = new widget.Label();
        TNoRw2 = new widget.TextBox();
        jLabel68 = new widget.Label();
        jLabel101 = new widget.Label();
        G = new widget.TextBox();
        jLabel102 = new widget.Label();
        P = new widget.TextBox();
        jLabel103 = new widget.Label();
        A = new widget.TextBox();
        jLabel104 = new widget.Label();
        Hidup = new widget.TextBox();
        jLabel105 = new widget.Label();
        Hidup1 = new widget.TextBox();
        jLabel106 = new widget.Label();
        Scroll6 = new widget.ScrollPane();
        tbRiwayatKehamilan = new widget.Table();
        BtnTambahMasalah = new widget.Button();
        BtnHapusRiwayatPersalinan = new widget.Button();
        jLabel69 = new widget.Label();
        Anamnesis1 = new widget.ComboBox();
        jLabel39 = new widget.Label();
        Anamnesis2 = new widget.ComboBox();
        jLabel40 = new widget.Label();
        jLabel41 = new widget.Label();
        Anamnesis3 = new widget.ComboBox();
        jLabel70 = new widget.Label();
        jLabel42 = new widget.Label();
        Anamnesis4 = new widget.ComboBox();
        jLabel71 = new widget.Label();
        Hubungan1 = new widget.TextBox();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel115 = new widget.Label();
        label12 = new widget.Label();
        TglAsuhan1 = new widget.Tanggal();
        jLabel43 = new widget.Label();
        Hidup2 = new widget.TextBox();
        jLabel116 = new widget.Label();
        Anamnesis5 = new widget.ComboBox();
        jLabel72 = new widget.Label();
        jLabel45 = new widget.Label();
        Hubungan2 = new widget.TextBox();
        jLabel117 = new widget.Label();
        jLabel73 = new widget.Label();
        Anamnesis6 = new widget.ComboBox();
        jLabel44 = new widget.Label();
        jLabel46 = new widget.Label();
        Anamnesis7 = new widget.ComboBox();
        jLabel47 = new widget.Label();
        Anamnesis8 = new widget.ComboBox();
        jLabel118 = new widget.Label();
        jLabel48 = new widget.Label();
        jLabel49 = new widget.Label();
        jLabel50 = new widget.Label();
        Anamnesis9 = new widget.ComboBox();
        jLabel74 = new widget.Label();
        Scroll2 = new widget.ScrollPane();
        tbAPGAR = new widget.Table();
        label71 = new widget.Label();
        N1 = new widget.TextBox2();
        N5 = new widget.TextBox2();
        N10 = new widget.TextBox2();
        jLabel76 = new widget.Label();
        jLabel51 = new widget.Label();
        Anamnesis10 = new widget.ComboBox();
        jLabel52 = new widget.Label();
        Anamnesis11 = new widget.ComboBox();
        jLabel53 = new widget.Label();
        Anamnesis12 = new widget.ComboBox();
        Anamnesis13 = new widget.ComboBox();
        jLabel54 = new widget.Label();
        jLabel55 = new widget.Label();
        Anamnesis14 = new widget.ComboBox();
        A1 = new widget.TextBox();
        A2 = new widget.TextBox();
        A3 = new widget.TextBox();
        A4 = new widget.TextBox();
        A5 = new widget.TextBox();
        A6 = new widget.TextBox();
        jLabel56 = new widget.Label();
        jLabel57 = new widget.Label();
        Anamnesis15 = new widget.ComboBox();
        jLabel77 = new widget.Label();
        jLabel119 = new widget.Label();
        jLabel120 = new widget.Label();
        G1 = new widget.TextBox();
        jLabel121 = new widget.Label();
        jLabel122 = new widget.Label();
        G2 = new widget.TextBox();
        jLabel123 = new widget.Label();
        jLabel124 = new widget.Label();
        G3 = new widget.TextBox();
        jLabel125 = new widget.Label();
        jLabel126 = new widget.Label();
        G4 = new widget.TextBox();
        jLabel127 = new widget.Label();
        jLabel58 = new widget.Label();
        jLabel78 = new widget.Label();
        jLabel128 = new widget.Label();
        G5 = new widget.TextBox();
        jLabel129 = new widget.Label();
        jLabel130 = new widget.Label();
        G6 = new widget.TextBox();
        jLabel131 = new widget.Label();
        jLabel132 = new widget.Label();
        G7 = new widget.TextBox();
        jLabel133 = new widget.Label();
        jLabel134 = new widget.Label();
        G8 = new widget.TextBox();
        jLabel135 = new widget.Label();
        jLabel136 = new widget.Label();
        jLabel79 = new widget.Label();
        Kepala = new widget.ComboBox();
        jLabel59 = new widget.Label();
        KeteranganKepala = new widget.TextBox();
        jLabel60 = new widget.Label();
        Kepala1 = new widget.ComboBox();
        KeteranganKepala1 = new widget.TextBox();
        jLabel61 = new widget.Label();
        Kepala2 = new widget.ComboBox();
        KeteranganKepala2 = new widget.TextBox();
        jLabel62 = new widget.Label();
        Kepala3 = new widget.ComboBox();
        KeteranganKepala3 = new widget.TextBox();
        jLabel63 = new widget.Label();
        Kepala4 = new widget.ComboBox();
        KeteranganKepala4 = new widget.TextBox();
        jLabel64 = new widget.Label();
        Kepala5 = new widget.ComboBox();
        KeteranganKepala5 = new widget.TextBox();
        jLabel65 = new widget.Label();
        Kepala6 = new widget.ComboBox();
        KeteranganKepala6 = new widget.TextBox();
        jLabel66 = new widget.Label();
        KeteranganKepala7 = new widget.TextBox();
        Kepala7 = new widget.ComboBox();
        jLabel67 = new widget.Label();
        Kepala8 = new widget.ComboBox();
        KeteranganKepala8 = new widget.TextBox();
        Kepala9 = new widget.ComboBox();
        jLabel75 = new widget.Label();
        KeteranganKepala9 = new widget.TextBox();
        jLabel80 = new widget.Label();
        Kepala10 = new widget.ComboBox();
        KeteranganKepala10 = new widget.TextBox();
        jLabel81 = new widget.Label();
        Kepala11 = new widget.ComboBox();
        KeteranganKepala11 = new widget.TextBox();
        jLabel82 = new widget.Label();
        Kepala12 = new widget.ComboBox();
        KeteranganKepala12 = new widget.TextBox();
        jLabel83 = new widget.Label();
        Kepala13 = new widget.ComboBox();
        KeteranganKepala13 = new widget.TextBox();
        jLabel84 = new widget.Label();
        Kepala14 = new widget.ComboBox();
        KeteranganKepala14 = new widget.TextBox();
        jLabel85 = new widget.Label();
        Kepala15 = new widget.ComboBox();
        KeteranganKepala15 = new widget.TextBox();
        jLabel86 = new widget.Label();
        KeteranganKepala16 = new widget.TextBox();
        Kepala16 = new widget.ComboBox();
        jLabel137 = new widget.Label();
        jLabel87 = new widget.Label();
        KeteranganKepala17 = new widget.TextBox();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel138 = new widget.Label();
        scrollPane12 = new widget.ScrollPane();
        PemeriksaanRegional = new widget.TextArea();
        jSeparator14 = new javax.swing.JSeparator();
        jLabel139 = new widget.Label();
        jLabel88 = new widget.Label();
        jLabel89 = new widget.Label();
        jLabel90 = new widget.Label();
        scrollPane9 = new widget.ScrollPane();
        Laborat = new widget.TextArea();
        scrollPane10 = new widget.ScrollPane();
        Radiologi = new widget.TextArea();
        scrollPane11 = new widget.ScrollPane();
        Penunjang = new widget.TextArea();
        jSeparator15 = new javax.swing.JSeparator();
        jLabel140 = new widget.Label();
        scrollPane13 = new widget.ScrollPane();
        Diagnosis = new widget.TextArea();
        jSeparator16 = new javax.swing.JSeparator();
        jLabel141 = new widget.Label();
        scrollPane14 = new widget.ScrollPane();
        Tatalaksana = new widget.TextArea();
        jSeparator17 = new javax.swing.JSeparator();
        jLabel142 = new widget.Label();
        scrollPane15 = new widget.ScrollPane();
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

        DlgRiwayatPersalinan.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        DlgRiwayatPersalinan.setName("DlgRiwayatPersalinan"); // NOI18N
        DlgRiwayatPersalinan.setUndecorated(true);
        DlgRiwayatPersalinan.setResizable(false);

        internalFrame4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(230, 235, 225)), "::[ Riwayat Persalinan Pasien ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 50))); // NOI18N
        internalFrame4.setName("internalFrame4"); // NOI18N
        internalFrame4.setLayout(new java.awt.BorderLayout(1, 1));

        panelBiasa2.setName("panelBiasa2"); // NOI18N
        panelBiasa2.setLayout(null);

        TanggalPersalinan.setForeground(new java.awt.Color(50, 70, 50));
        TanggalPersalinan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "02-01-2025" }));
        TanggalPersalinan.setDisplayFormat("dd-MM-yyyy");
        TanggalPersalinan.setName("TanggalPersalinan"); // NOI18N
        TanggalPersalinan.setOpaque(false);
        panelBiasa2.add(TanggalPersalinan);
        TanggalPersalinan.setBounds(530, 10, 95, 23);

        jLabel100.setText("Tempat Persalinan :");
        jLabel100.setName("jLabel100"); // NOI18N
        panelBiasa2.add(jLabel100);
        jLabel100.setBounds(0, 10, 110, 23);

        BtnKeluarKehamilan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/cross.png"))); // NOI18N
        BtnKeluarKehamilan.setMnemonic('U');
        BtnKeluarKehamilan.setText("Tutup");
        BtnKeluarKehamilan.setToolTipText("Alt+U");
        BtnKeluarKehamilan.setName("BtnKeluarKehamilan"); // NOI18N
        BtnKeluarKehamilan.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluarKehamilan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluarKehamilanActionPerformed(evt);
            }
        });
        panelBiasa2.add(BtnKeluarKehamilan);
        BtnKeluarKehamilan.setBounds(530, 130, 100, 30);

        jLabel107.setText("Usia Hamil :");
        jLabel107.setName("jLabel107"); // NOI18N
        panelBiasa2.add(jLabel107);
        jLabel107.setBounds(430, 40, 96, 23);

        UsiaHamil.setHighlighter(null);
        UsiaHamil.setName("UsiaHamil"); // NOI18N
        UsiaHamil.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                UsiaHamilKeyPressed(evt);
            }
        });
        panelBiasa2.add(UsiaHamil);
        UsiaHamil.setBounds(530, 40, 95, 23);

        BtnSimpanRiwayatKehamilan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpanRiwayatKehamilan.setMnemonic('S');
        BtnSimpanRiwayatKehamilan.setText("Simpan");
        BtnSimpanRiwayatKehamilan.setToolTipText("Alt+S");
        BtnSimpanRiwayatKehamilan.setName("BtnSimpanRiwayatKehamilan"); // NOI18N
        BtnSimpanRiwayatKehamilan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpanRiwayatKehamilanActionPerformed(evt);
            }
        });
        panelBiasa2.add(BtnSimpanRiwayatKehamilan);
        BtnSimpanRiwayatKehamilan.setBounds(420, 130, 100, 30);

        jLabel108.setText("Tanggal/Tahun :");
        jLabel108.setName("jLabel108"); // NOI18N
        panelBiasa2.add(jLabel108);
        jLabel108.setBounds(430, 10, 96, 23);

        TempatPersalinan.setHighlighter(null);
        TempatPersalinan.setName("TempatPersalinan"); // NOI18N
        TempatPersalinan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TempatPersalinanKeyPressed(evt);
            }
        });
        panelBiasa2.add(TempatPersalinan);
        TempatPersalinan.setBounds(114, 10, 290, 23);

        jLabel109.setText("Jenis Persalinan :");
        jLabel109.setName("jLabel109"); // NOI18N
        panelBiasa2.add(jLabel109);
        jLabel109.setBounds(0, 40, 110, 23);

        JenisPersalinan.setHighlighter(null);
        JenisPersalinan.setName("JenisPersalinan"); // NOI18N
        JenisPersalinan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JenisPersalinanKeyPressed(evt);
            }
        });
        panelBiasa2.add(JenisPersalinan);
        JenisPersalinan.setBounds(114, 40, 290, 23);

        jLabel110.setText("Jenis Kelamin :");
        jLabel110.setName("jLabel110"); // NOI18N
        panelBiasa2.add(jLabel110);
        jLabel110.setBounds(430, 70, 96, 23);

        JK.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Laki-Laki", "Perempuan", "-" }));
        JK.setName("JK"); // NOI18N
        JK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JKKeyPressed(evt);
            }
        });
        panelBiasa2.add(JK);
        JK.setBounds(530, 70, 95, 23);

        jLabel111.setText("Penolong :");
        jLabel111.setName("jLabel111"); // NOI18N
        panelBiasa2.add(jLabel111);
        jLabel111.setBounds(0, 70, 110, 23);

        Penolong.setHighlighter(null);
        Penolong.setName("Penolong"); // NOI18N
        Penolong.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenolongKeyPressed(evt);
            }
        });
        panelBiasa2.add(Penolong);
        Penolong.setBounds(114, 70, 290, 23);

        jLabel112.setText("BB/PB :");
        jLabel112.setName("jLabel112"); // NOI18N
        panelBiasa2.add(jLabel112);
        jLabel112.setBounds(430, 100, 96, 23);

        BBPB.setHighlighter(null);
        BBPB.setName("BBPB"); // NOI18N
        BBPB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BBPBKeyPressed(evt);
            }
        });
        panelBiasa2.add(BBPB);
        BBPB.setBounds(530, 100, 95, 23);

        jLabel113.setText("Penyulit :");
        jLabel113.setName("jLabel113"); // NOI18N
        panelBiasa2.add(jLabel113);
        jLabel113.setBounds(0, 100, 110, 23);

        Penyulit.setHighlighter(null);
        Penyulit.setName("Penyulit"); // NOI18N
        Penyulit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenyulitKeyPressed(evt);
            }
        });
        panelBiasa2.add(Penyulit);
        Penyulit.setBounds(114, 100, 290, 23);

        jLabel114.setText("Keadaan :");
        jLabel114.setName("jLabel114"); // NOI18N
        panelBiasa2.add(jLabel114);
        jLabel114.setBounds(0, 130, 110, 23);

        Keadaan.setHighlighter(null);
        Keadaan.setName("Keadaan"); // NOI18N
        Keadaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeadaanKeyPressed(evt);
            }
        });
        panelBiasa2.add(Keadaan);
        Keadaan.setBounds(114, 130, 290, 23);

        internalFrame4.add(panelBiasa2, java.awt.BorderLayout.CENTER);

        DlgRiwayatPersalinan.getContentPane().add(internalFrame4, java.awt.BorderLayout.CENTER);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Penilaian Awal Medis Rawat Inap Neonatus ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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
        FormInput.setPreferredSize(new java.awt.Dimension(870, 1683));
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
        label14.setBounds(280, 40, 70, 23);

        KdDokter.setEditable(false);
        KdDokter.setName("KdDokter"); // NOI18N
        KdDokter.setPreferredSize(new java.awt.Dimension(80, 23));
        KdDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdDokterKeyPressed(evt);
            }
        });
        FormInput.add(KdDokter);
        KdDokter.setBounds(354, 40, 90, 23);

        NmDokter.setEditable(false);
        NmDokter.setName("NmDokter"); // NOI18N
        NmDokter.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmDokter);
        NmDokter.setBounds(446, 40, 180, 23);

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
        BtnDokter.setBounds(628, 40, 28, 23);

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

        Anamnesis.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Negatif (-)", "Positif (+)", "Tidak Ada Keterangan" }));
        Anamnesis.setName("Anamnesis"); // NOI18N
        Anamnesis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AnamnesisKeyPressed(evt);
            }
        });
        FormInput.add(Anamnesis);
        Anamnesis.setBounds(119, 260, 160, 23);

        jSeparator1.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator1.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator1.setName("jSeparator1"); // NOI18N
        FormInput.add(jSeparator1);
        jSeparator1.setBounds(0, 70, 880, 1);

        jLabel38.setText(":");
        jLabel38.setName("jLabel38"); // NOI18N
        FormInput.add(jLabel38);
        jLabel38.setBounds(0, 260, 115, 23);

        Hubungan.setName("Hubungan"); // NOI18N
        Hubungan.setPreferredSize(new java.awt.Dimension(207, 23));
        Hubungan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HubunganKeyPressed(evt);
            }
        });
        FormInput.add(Hubungan);
        Hubungan.setBounds(344, 290, 510, 23);

        jLabel99.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel99.setText("I. ANAMNESIS");
        jLabel99.setName("jLabel99"); // NOI18N
        FormInput.add(jLabel99);
        jLabel99.setBounds(10, 70, 180, 23);

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label11);
        label11.setBounds(668, 40, 52, 23);

        TglAsuhan.setForeground(new java.awt.Color(50, 70, 50));
        TglAsuhan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "02-01-2025 06:02:54" }));
        TglAsuhan.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        TglAsuhan.setName("TglAsuhan"); // NOI18N
        TglAsuhan.setOpaque(false);
        TglAsuhan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglAsuhanKeyPressed(evt);
            }
        });
        FormInput.add(TglAsuhan);
        TglAsuhan.setBounds(724, 40, 130, 23);

        jLabel12.setText("Umur :");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(0, 40, 70, 23);

        TNoRw1.setHighlighter(null);
        TNoRw1.setName("TNoRw1"); // NOI18N
        TNoRw1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRw1KeyPressed(evt);
            }
        });
        FormInput.add(TNoRw1);
        TNoRw1.setBounds(74, 40, 50, 23);

        jLabel13.setText("NIK :");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(120, 40, 45, 23);

        TNoRw2.setHighlighter(null);
        TNoRw2.setName("TNoRw2"); // NOI18N
        TNoRw2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRw2KeyPressed(evt);
            }
        });
        FormInput.add(TNoRw2);
        TNoRw2.setBounds(169, 40, 125, 23);

        jLabel68.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel68.setText("Riwayat Persalinan & Nifas Ibu :");
        jLabel68.setName("jLabel68"); // NOI18N
        FormInput.add(jLabel68);
        jLabel68.setBounds(44, 90, 182, 23);

        jLabel101.setText(":");
        jLabel101.setName("jLabel101"); // NOI18N
        FormInput.add(jLabel101);
        jLabel101.setBounds(0, 110, 91, 23);

        G.setFocusTraversalPolicyProvider(true);
        G.setName("G"); // NOI18N
        G.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GKeyPressed(evt);
            }
        });
        FormInput.add(G);
        G.setBounds(95, 110, 50, 23);

        jLabel102.setText("P :");
        jLabel102.setName("jLabel102"); // NOI18N
        FormInput.add(jLabel102);
        jLabel102.setBounds(146, 110, 30, 23);

        P.setFocusTraversalPolicyProvider(true);
        P.setName("P"); // NOI18N
        P.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PKeyPressed(evt);
            }
        });
        FormInput.add(P);
        P.setBounds(180, 110, 50, 23);

        jLabel103.setText("A :");
        jLabel103.setName("jLabel103"); // NOI18N
        FormInput.add(jLabel103);
        jLabel103.setBounds(231, 110, 30, 23);

        A.setFocusTraversalPolicyProvider(true);
        A.setName("A"); // NOI18N
        A.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AKeyPressed(evt);
            }
        });
        FormInput.add(A);
        A.setBounds(265, 110, 50, 23);

        jLabel104.setText("Anak Yang Hidup :");
        jLabel104.setName("jLabel104"); // NOI18N
        FormInput.add(jLabel104);
        jLabel104.setBounds(330, 110, 110, 23);

        Hidup.setFocusTraversalPolicyProvider(true);
        Hidup.setName("Hidup"); // NOI18N
        Hidup.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HidupKeyPressed(evt);
            }
        });
        FormInput.add(Hidup);
        Hidup.setBounds(444, 110, 60, 23);

        jLabel105.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel105.setText("minggu");
        jLabel105.setName("jLabel105"); // NOI18N
        FormInput.add(jLabel105);
        jLabel105.setBounds(687, 110, 110, 23);

        Hidup1.setFocusTraversalPolicyProvider(true);
        Hidup1.setName("Hidup1"); // NOI18N
        Hidup1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Hidup1KeyPressed(evt);
            }
        });
        FormInput.add(Hidup1);
        Hidup1.setBounds(624, 110, 60, 23);

        jLabel106.setText("Usia Kehamilan :");
        jLabel106.setName("jLabel106"); // NOI18N
        FormInput.add(jLabel106);
        jLabel106.setBounds(520, 110, 100, 23);

        Scroll6.setName("Scroll6"); // NOI18N
        Scroll6.setOpaque(true);

        tbRiwayatKehamilan.setName("tbRiwayatKehamilan"); // NOI18N
        Scroll6.setViewportView(tbRiwayatKehamilan);

        FormInput.add(Scroll6);
        Scroll6.setBounds(110, 140, 744, 93);

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
        BtnTambahMasalah.setBounds(77, 140, 28, 23);

        BtnHapusRiwayatPersalinan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); // NOI18N
        BtnHapusRiwayatPersalinan.setMnemonic('3');
        BtnHapusRiwayatPersalinan.setToolTipText("Alt+3");
        BtnHapusRiwayatPersalinan.setName("BtnHapusRiwayatPersalinan"); // NOI18N
        BtnHapusRiwayatPersalinan.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnHapusRiwayatPersalinan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnHapusRiwayatPersalinanActionPerformed(evt);
            }
        });
        FormInput.add(BtnHapusRiwayatPersalinan);
        BtnHapusRiwayatPersalinan.setBounds(77, 170, 28, 23);

        jLabel69.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel69.setText("Skrining Ibu :");
        jLabel69.setName("jLabel69"); // NOI18N
        FormInput.add(jLabel69);
        jLabel69.setBounds(44, 240, 182, 23);

        Anamnesis1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Negatif (-)", "Positif (+)", "Tidak Ada Keterangan" }));
        Anamnesis1.setName("Anamnesis1"); // NOI18N
        Anamnesis1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Anamnesis1KeyPressed(evt);
            }
        });
        FormInput.add(Anamnesis1);
        Anamnesis1.setBounds(412, 260, 160, 23);

        jLabel39.setText("HIV/AIDS :");
        jLabel39.setName("jLabel39"); // NOI18N
        FormInput.add(jLabel39);
        jLabel39.setBounds(338, 260, 70, 23);

        Anamnesis2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Negatif (-)", "Positif (+)", "Tidak Ada Keterangan" }));
        Anamnesis2.setName("Anamnesis2"); // NOI18N
        Anamnesis2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Anamnesis2KeyPressed(evt);
            }
        });
        FormInput.add(Anamnesis2);
        Anamnesis2.setBounds(694, 260, 160, 23);

        jLabel40.setText("Syphilis :");
        jLabel40.setName("jLabel40"); // NOI18N
        FormInput.add(jLabel40);
        jLabel40.setBounds(620, 260, 70, 23);

        jLabel41.setText(":");
        jLabel41.setName("jLabel41"); // NOI18N
        FormInput.add(jLabel41);
        jLabel41.setBounds(0, 290, 152, 23);

        Anamnesis3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada", "Demam Pada Ibu > 38°C", "Ketuban Pecah Dini", "Ada Ketuban Berbau/Keruh", "Nyeri Berkemih/ISK", "Ibu DM", "Ibu Hipertensi", "Ibu Perdarahan", "Ibu Eklamsia/Pre Eklamsia", "Lainnya" }));
        Anamnesis3.setName("Anamnesis3"); // NOI18N
        Anamnesis3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Anamnesis3KeyPressed(evt);
            }
        });
        FormInput.add(Anamnesis3);
        Anamnesis3.setBounds(156, 290, 185, 23);

        jLabel70.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel70.setText("Riwayat Obstetri Ibu");
        jLabel70.setName("jLabel70"); // NOI18N
        FormInput.add(jLabel70);
        jLabel70.setBounds(44, 290, 182, 23);

        jLabel42.setText(":");
        jLabel42.setName("jLabel42"); // NOI18N
        FormInput.add(jLabel42);
        jLabel42.setBounds(0, 320, 160, 23);

        Anamnesis4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada", "Kelahiran Preterm", "Kelahiran Post Date", "DJJ Abnormal", "Lainnya" }));
        Anamnesis4.setName("Anamnesis4"); // NOI18N
        Anamnesis4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Anamnesis4KeyPressed(evt);
            }
        });
        FormInput.add(Anamnesis4);
        Anamnesis4.setBounds(164, 320, 150, 23);

        jLabel71.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel71.setText("Faktor Risiko Neonatal");
        jLabel71.setName("jLabel71"); // NOI18N
        FormInput.add(jLabel71);
        jLabel71.setBounds(44, 320, 182, 23);

        Hubungan1.setName("Hubungan1"); // NOI18N
        Hubungan1.setPreferredSize(new java.awt.Dimension(207, 23));
        Hubungan1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Hubungan1KeyPressed(evt);
            }
        });
        FormInput.add(Hubungan1);
        Hubungan1.setBounds(317, 320, 537, 23);

        jSeparator2.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator2.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator2.setName("jSeparator2"); // NOI18N
        FormInput.add(jSeparator2);
        jSeparator2.setBounds(0, 360, 880, 1);

        jLabel115.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel115.setText("II. PEMERIKSAAN FISIK");
        jLabel115.setName("jLabel115"); // NOI18N
        FormInput.add(jLabel115);
        jLabel115.setBounds(10, 360, 180, 23);

        label12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label12.setText("Tanggal & Jam Persalinan");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label12);
        label12.setBounds(44, 380, 150, 23);

        TglAsuhan1.setForeground(new java.awt.Color(50, 70, 50));
        TglAsuhan1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "03-01-2025 18:42:01" }));
        TglAsuhan1.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        TglAsuhan1.setName("TglAsuhan1"); // NOI18N
        TglAsuhan1.setOpaque(false);
        TglAsuhan1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglAsuhan1KeyPressed(evt);
            }
        });
        FormInput.add(TglAsuhan1);
        TglAsuhan1.setBounds(181, 380, 130, 23);

        jLabel43.setText(":");
        jLabel43.setName("jLabel43"); // NOI18N
        FormInput.add(jLabel43);
        jLabel43.setBounds(0, 380, 177, 23);

        Hidup2.setFocusTraversalPolicyProvider(true);
        Hidup2.setName("Hidup2"); // NOI18N
        Hidup2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Hidup2KeyPressed(evt);
            }
        });
        FormInput.add(Hidup2);
        Hidup2.setBounds(416, 380, 205, 23);

        jLabel116.setText("Bersalin Di :");
        jLabel116.setName("jLabel116"); // NOI18N
        FormInput.add(jLabel116);
        jLabel116.setBounds(332, 380, 80, 23);

        Anamnesis5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Spontan/Normal", "Induksi", "Sectio Caesaria", "Vacum Ekstraksi" }));
        Anamnesis5.setName("Anamnesis5"); // NOI18N
        Anamnesis5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Anamnesis5KeyPressed(evt);
            }
        });
        FormInput.add(Anamnesis5);
        Anamnesis5.setBounds(134, 410, 133, 23);

        jLabel72.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel72.setText("Jenis Persalinan");
        jLabel72.setName("jLabel72"); // NOI18N
        FormInput.add(jLabel72);
        jLabel72.setBounds(44, 410, 130, 23);

        jLabel45.setText(":");
        jLabel45.setName("jLabel45"); // NOI18N
        FormInput.add(jLabel45);
        jLabel45.setBounds(0, 410, 130, 23);

        Hubungan2.setName("Hubungan2"); // NOI18N
        Hubungan2.setPreferredSize(new java.awt.Dimension(207, 23));
        Hubungan2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Hubungan2KeyPressed(evt);
            }
        });
        FormInput.add(Hubungan2);
        Hubungan2.setBounds(416, 410, 438, 23);

        jLabel117.setText("Indikasi/Keterangan :");
        jLabel117.setName("jLabel117"); // NOI18N
        FormInput.add(jLabel117);
        jLabel117.setBounds(292, 410, 120, 23);

        jLabel73.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel73.setText("Penilaian Awal Lahir :");
        jLabel73.setName("jLabel73"); // NOI18N
        FormInput.add(jLabel73);
        jLabel73.setBounds(44, 440, 182, 23);

        Anamnesis6.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Anamnesis6.setName("Anamnesis6"); // NOI18N
        Anamnesis6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Anamnesis6KeyPressed(evt);
            }
        });
        FormInput.add(Anamnesis6);
        Anamnesis6.setBounds(119, 460, 80, 23);

        jLabel44.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel44.setText("Aterm");
        jLabel44.setName("jLabel44"); // NOI18N
        FormInput.add(jLabel44);
        jLabel44.setBounds(77, 460, 70, 23);

        jLabel46.setText("Bernafas/Menangis ?");
        jLabel46.setName("jLabel46"); // NOI18N
        FormInput.add(jLabel46);
        jLabel46.setBounds(216, 460, 120, 23);

        Anamnesis7.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Anamnesis7.setName("Anamnesis7"); // NOI18N
        Anamnesis7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Anamnesis7KeyPressed(evt);
            }
        });
        FormInput.add(Anamnesis7);
        Anamnesis7.setBounds(340, 460, 80, 23);

        jLabel47.setText("Cairan Amnion Jernih ?");
        jLabel47.setName("jLabel47"); // NOI18N
        FormInput.add(jLabel47);
        jLabel47.setBounds(640, 460, 130, 23);

        Anamnesis8.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Anamnesis8.setName("Anamnesis8"); // NOI18N
        Anamnesis8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Anamnesis8KeyPressed(evt);
            }
        });
        FormInput.add(Anamnesis8);
        Anamnesis8.setBounds(774, 460, 80, 23);

        jLabel118.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel118.setText("G");
        jLabel118.setName("jLabel118"); // NOI18N
        FormInput.add(jLabel118);
        jLabel118.setBounds(77, 110, 91, 23);

        jLabel48.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel48.setText("HbsAg");
        jLabel48.setName("jLabel48"); // NOI18N
        FormInput.add(jLabel48);
        jLabel48.setBounds(77, 260, 115, 23);

        jLabel49.setText("?");
        jLabel49.setName("jLabel49"); // NOI18N
        FormInput.add(jLabel49);
        jLabel49.setBounds(0, 460, 115, 23);

        jLabel50.setText("Tonus Otot Baik ?");
        jLabel50.setName("jLabel50"); // NOI18N
        FormInput.add(jLabel50);
        jLabel50.setBounds(420, 460, 120, 23);

        Anamnesis9.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Anamnesis9.setName("Anamnesis9"); // NOI18N
        Anamnesis9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Anamnesis9KeyPressed(evt);
            }
        });
        FormInput.add(Anamnesis9);
        Anamnesis9.setBounds(544, 460, 80, 23);

        jLabel74.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel74.setText("APGAR Score :");
        jLabel74.setName("jLabel74"); // NOI18N
        FormInput.add(jLabel74);
        jLabel74.setBounds(44, 490, 182, 23);

        Scroll2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(150, 150, 150)));
        Scroll2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        Scroll2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        Scroll2.setName("Scroll2"); // NOI18N

        tbAPGAR.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tbAPGAR.setAutoscrolls(false);
        tbAPGAR.setGridColor(new java.awt.Color(150, 150, 150));
        tbAPGAR.setName("tbAPGAR"); // NOI18N
        tbAPGAR.setRowHeight(150);
        tbAPGAR.getTableHeader().setResizingAllowed(false);
        tbAPGAR.getTableHeader().setReorderingAllowed(false);
        tbAPGAR.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tbAPGARPropertyChange(evt);
            }
        });
        Scroll2.setViewportView(tbAPGAR);

        FormInput.add(Scroll2);
        Scroll2.setBounds(77, 510, 777, 127);

        label71.setText("Jumlah Nilai :");
        label71.setName("label71"); // NOI18N
        label71.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label71);
        label71.setBounds(660, 636, 100, 27);

        N1.setEditable(false);
        N1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(150, 150, 150)));
        N1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        N1.setName("N1"); // NOI18N
        FormInput.add(N1);
        N1.setBounds(764, 636, 29, 27);

        N5.setEditable(false);
        N5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(150, 150, 150)));
        N5.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        N5.setName("N5"); // NOI18N
        FormInput.add(N5);
        N5.setBounds(792, 636, 29, 27);

        N10.setEditable(false);
        N10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(150, 150, 150)));
        N10.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        N10.setName("N10"); // NOI18N
        FormInput.add(N10);
        N10.setBounds(820, 636, 34, 27);

        jLabel76.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel76.setText("Down Score :");
        jLabel76.setName("jLabel76"); // NOI18N
        FormInput.add(jLabel76);
        jLabel76.setBounds(44, 660, 182, 23);

        jLabel51.setText(":");
        jLabel51.setName("jLabel51"); // NOI18N
        FormInput.add(jLabel51);
        jLabel51.setBounds(57, 680, 107, 23);

        Anamnesis10.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "< 60", "60 - 80", "> 80" }));
        Anamnesis10.setName("Anamnesis10"); // NOI18N
        Anamnesis10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Anamnesis10KeyPressed(evt);
            }
        });
        FormInput.add(Anamnesis10);
        Anamnesis10.setBounds(168, 680, 180, 23);

        jLabel52.setText("Retraksi :");
        jLabel52.setName("jLabel52"); // NOI18N
        FormInput.add(jLabel52);
        jLabel52.setBounds(57, 710, 107, 23);

        Anamnesis11.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada", "Retraksi Ringan", "Retraksi Berat" }));
        Anamnesis11.setName("Anamnesis11"); // NOI18N
        Anamnesis11.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Anamnesis11KeyPressed(evt);
            }
        });
        FormInput.add(Anamnesis11);
        Anamnesis11.setBounds(168, 710, 180, 23);

        jLabel53.setText("Sianosis :");
        jLabel53.setName("jLabel53"); // NOI18N
        FormInput.add(jLabel53);
        jLabel53.setBounds(57, 740, 107, 23);

        Anamnesis12.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada", "Hilang Dengan O2", "Tidak Hilang Dengan O2" }));
        Anamnesis12.setName("Anamnesis12"); // NOI18N
        Anamnesis12.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Anamnesis12KeyPressed(evt);
            }
        });
        FormInput.add(Anamnesis12);
        Anamnesis12.setBounds(168, 740, 180, 23);

        Anamnesis13.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Baik", "Penurunan Ringan Udara Masuk", "Tidak Ada Udara Masuk" }));
        Anamnesis13.setName("Anamnesis13"); // NOI18N
        Anamnesis13.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Anamnesis13KeyPressed(evt);
            }
        });
        FormInput.add(Anamnesis13);
        Anamnesis13.setBounds(576, 680, 230, 23);

        jLabel54.setText("Jalan Masuk Udara :");
        jLabel54.setName("jLabel54"); // NOI18N
        FormInput.add(jLabel54);
        jLabel54.setBounds(462, 680, 110, 23);

        jLabel55.setText("Grunting :");
        jLabel55.setName("jLabel55"); // NOI18N
        FormInput.add(jLabel55);
        jLabel55.setBounds(462, 710, 110, 23);

        Anamnesis14.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada", "Dapat Didengar Dengan Stetoskop", "Dapat Didengar Tanpa Stetoskop" }));
        Anamnesis14.setName("Anamnesis14"); // NOI18N
        Anamnesis14.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Anamnesis14KeyPressed(evt);
            }
        });
        FormInput.add(Anamnesis14);
        Anamnesis14.setBounds(576, 710, 230, 23);

        A1.setFocusTraversalPolicyProvider(true);
        A1.setName("A1"); // NOI18N
        A1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                A1KeyPressed(evt);
            }
        });
        FormInput.add(A1);
        A1.setBounds(351, 740, 45, 23);

        A2.setFocusTraversalPolicyProvider(true);
        A2.setName("A2"); // NOI18N
        A2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                A2KeyPressed(evt);
            }
        });
        FormInput.add(A2);
        A2.setBounds(351, 680, 45, 23);

        A3.setFocusTraversalPolicyProvider(true);
        A3.setName("A3"); // NOI18N
        A3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                A3KeyPressed(evt);
            }
        });
        FormInput.add(A3);
        A3.setBounds(351, 710, 45, 23);

        A4.setFocusTraversalPolicyProvider(true);
        A4.setName("A4"); // NOI18N
        A4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                A4KeyPressed(evt);
            }
        });
        FormInput.add(A4);
        A4.setBounds(809, 680, 45, 23);

        A5.setFocusTraversalPolicyProvider(true);
        A5.setName("A5"); // NOI18N
        A5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                A5KeyPressed(evt);
            }
        });
        FormInput.add(A5);
        A5.setBounds(809, 740, 45, 23);

        A6.setFocusTraversalPolicyProvider(true);
        A6.setName("A6"); // NOI18N
        A6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                A6KeyPressed(evt);
            }
        });
        FormInput.add(A6);
        A6.setBounds(809, 710, 45, 23);

        jLabel56.setText("Total Nilai :");
        jLabel56.setName("jLabel56"); // NOI18N
        FormInput.add(jLabel56);
        jLabel56.setBounds(545, 740, 260, 23);

        jLabel57.setText("Inisiasi Menyusui Dini :");
        jLabel57.setName("jLabel57"); // NOI18N
        FormInput.add(jLabel57);
        jLabel57.setBounds(640, 380, 130, 23);

        Anamnesis15.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Anamnesis15.setName("Anamnesis15"); // NOI18N
        Anamnesis15.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Anamnesis15KeyPressed(evt);
            }
        });
        FormInput.add(Anamnesis15);
        Anamnesis15.setBounds(774, 380, 80, 23);

        jLabel77.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel77.setText("Tanda-tanda Vital :");
        jLabel77.setName("jLabel77"); // NOI18N
        FormInput.add(jLabel77);
        jLabel77.setBounds(44, 770, 182, 23);

        jLabel119.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel119.setText("x/menit");
        jLabel119.setName("jLabel119"); // NOI18N
        FormInput.add(jLabel119);
        jLabel119.setBounds(172, 790, 40, 23);

        jLabel120.setText(":");
        jLabel120.setName("jLabel120"); // NOI18N
        FormInput.add(jLabel120);
        jLabel120.setBounds(0, 790, 105, 23);

        G1.setFocusTraversalPolicyProvider(true);
        G1.setName("G1"); // NOI18N
        G1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                G1KeyPressed(evt);
            }
        });
        FormInput.add(G1);
        G1.setBounds(109, 790, 60, 23);

        jLabel121.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel121.setText("Nadi");
        jLabel121.setName("jLabel121"); // NOI18N
        FormInput.add(jLabel121);
        jLabel121.setBounds(77, 790, 40, 23);

        jLabel122.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel122.setText("x/menit");
        jLabel122.setName("jLabel122"); // NOI18N
        FormInput.add(jLabel122);
        jLabel122.setBounds(390, 790, 40, 23);

        G2.setFocusTraversalPolicyProvider(true);
        G2.setName("G2"); // NOI18N
        G2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                G2KeyPressed(evt);
            }
        });
        FormInput.add(G2);
        G2.setBounds(327, 790, 60, 23);

        jLabel123.setText("RR :");
        jLabel123.setName("jLabel123"); // NOI18N
        FormInput.add(jLabel123);
        jLabel123.setBounds(273, 790, 50, 23);

        jLabel124.setText("Suhu :");
        jLabel124.setName("jLabel124"); // NOI18N
        FormInput.add(jLabel124);
        jLabel124.setBounds(499, 790, 50, 23);

        G3.setFocusTraversalPolicyProvider(true);
        G3.setName("G3"); // NOI18N
        G3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                G3KeyPressed(evt);
            }
        });
        FormInput.add(G3);
        G3.setBounds(553, 790, 60, 23);

        jLabel125.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel125.setText("°C");
        jLabel125.setName("jLabel125"); // NOI18N
        FormInput.add(jLabel125);
        jLabel125.setBounds(616, 790, 40, 23);

        jLabel126.setText("Saturasi O2 :");
        jLabel126.setName("jLabel126"); // NOI18N
        FormInput.add(jLabel126);
        jLabel126.setBounds(665, 790, 110, 23);

        G4.setFocusTraversalPolicyProvider(true);
        G4.setName("G4"); // NOI18N
        G4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                G4KeyPressed(evt);
            }
        });
        FormInput.add(G4);
        G4.setBounds(779, 790, 60, 23);

        jLabel127.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel127.setText("%");
        jLabel127.setName("jLabel127"); // NOI18N
        FormInput.add(jLabel127);
        jLabel127.setBounds(842, 790, 40, 23);

        jLabel58.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel58.setText("Frekuensi Napas");
        jLabel58.setName("jLabel58"); // NOI18N
        FormInput.add(jLabel58);
        jLabel58.setBounds(77, 680, 107, 23);

        jLabel78.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel78.setText("Antropometri :");
        jLabel78.setName("jLabel78"); // NOI18N
        FormInput.add(jLabel78);
        jLabel78.setBounds(44, 820, 182, 23);

        jLabel128.setText(":");
        jLabel128.setName("jLabel128"); // NOI18N
        FormInput.add(jLabel128);
        jLabel128.setBounds(0, 840, 143, 23);

        G5.setFocusTraversalPolicyProvider(true);
        G5.setName("G5"); // NOI18N
        G5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                G5KeyPressed(evt);
            }
        });
        FormInput.add(G5);
        G5.setBounds(147, 840, 60, 23);

        jLabel129.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel129.setText("gram");
        jLabel129.setName("jLabel129"); // NOI18N
        FormInput.add(jLabel129);
        jLabel129.setBounds(210, 840, 40, 23);

        jLabel130.setText("Panjang Badan :");
        jLabel130.setName("jLabel130"); // NOI18N
        FormInput.add(jLabel130);
        jLabel130.setBounds(268, 840, 100, 23);

        G6.setFocusTraversalPolicyProvider(true);
        G6.setName("G6"); // NOI18N
        G6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                G6KeyPressed(evt);
            }
        });
        FormInput.add(G6);
        G6.setBounds(372, 840, 60, 23);

        jLabel131.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel131.setText("cm");
        jLabel131.setName("jLabel131"); // NOI18N
        FormInput.add(jLabel131);
        jLabel131.setBounds(435, 840, 40, 23);

        jLabel132.setText("Lingkar Kepala :");
        jLabel132.setName("jLabel132"); // NOI18N
        FormInput.add(jLabel132);
        jLabel132.setBounds(484, 840, 90, 23);

        G7.setFocusTraversalPolicyProvider(true);
        G7.setName("G7"); // NOI18N
        G7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                G7KeyPressed(evt);
            }
        });
        FormInput.add(G7);
        G7.setBounds(578, 840, 60, 23);

        jLabel133.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel133.setText("cm");
        jLabel133.setName("jLabel133"); // NOI18N
        FormInput.add(jLabel133);
        jLabel133.setBounds(641, 840, 40, 23);

        jLabel134.setText("Lingkar Dada :");
        jLabel134.setName("jLabel134"); // NOI18N
        FormInput.add(jLabel134);
        jLabel134.setBounds(662, 840, 110, 23);

        G8.setFocusTraversalPolicyProvider(true);
        G8.setName("G8"); // NOI18N
        G8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                G8KeyPressed(evt);
            }
        });
        FormInput.add(G8);
        G8.setBounds(776, 840, 60, 23);

        jLabel135.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel135.setText("cm");
        jLabel135.setName("jLabel135"); // NOI18N
        FormInput.add(jLabel135);
        jLabel135.setBounds(839, 840, 40, 23);

        jLabel136.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel136.setText("Berat Badan");
        jLabel136.setName("jLabel136"); // NOI18N
        FormInput.add(jLabel136);
        jLabel136.setBounds(77, 840, 60, 23);

        jLabel79.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel79.setText("Status Kelainan :");
        jLabel79.setName("jLabel79"); // NOI18N
        FormInput.add(jLabel79);
        jLabel79.setBounds(44, 870, 182, 23);

        Kepala.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Kepala.setName("Kepala"); // NOI18N
        Kepala.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KepalaKeyPressed(evt);
            }
        });
        FormInput.add(Kepala);
        Kepala.setBounds(157, 890, 129, 23);

        jLabel59.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel59.setText("Kondisi Umum");
        jLabel59.setName("jLabel59"); // NOI18N
        FormInput.add(jLabel59);
        jLabel59.setBounds(77, 890, 128, 23);

        KeteranganKepala.setFocusTraversalPolicyProvider(true);
        KeteranganKepala.setName("KeteranganKepala"); // NOI18N
        KeteranganKepala.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKepalaKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganKepala);
        KeteranganKepala.setBounds(289, 890, 160, 23);

        jLabel60.setText("Kulit :");
        jLabel60.setName("jLabel60"); // NOI18N
        FormInput.add(jLabel60);
        jLabel60.setBounds(0, 920, 153, 23);

        Kepala1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Kepala1.setName("Kepala1"); // NOI18N
        Kepala1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Kepala1KeyPressed(evt);
            }
        });
        FormInput.add(Kepala1);
        Kepala1.setBounds(157, 920, 129, 23);

        KeteranganKepala1.setFocusTraversalPolicyProvider(true);
        KeteranganKepala1.setName("KeteranganKepala1"); // NOI18N
        KeteranganKepala1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKepala1KeyPressed(evt);
            }
        });
        FormInput.add(KeteranganKepala1);
        KeteranganKepala1.setBounds(289, 920, 160, 23);

        jLabel61.setText("Kepala :");
        jLabel61.setName("jLabel61"); // NOI18N
        FormInput.add(jLabel61);
        jLabel61.setBounds(0, 950, 153, 23);

        Kepala2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Kepala2.setName("Kepala2"); // NOI18N
        Kepala2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Kepala2KeyPressed(evt);
            }
        });
        FormInput.add(Kepala2);
        Kepala2.setBounds(157, 950, 129, 23);

        KeteranganKepala2.setFocusTraversalPolicyProvider(true);
        KeteranganKepala2.setName("KeteranganKepala2"); // NOI18N
        KeteranganKepala2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKepala2KeyPressed(evt);
            }
        });
        FormInput.add(KeteranganKepala2);
        KeteranganKepala2.setBounds(289, 950, 160, 23);

        jLabel62.setText("Mata :");
        jLabel62.setName("jLabel62"); // NOI18N
        FormInput.add(jLabel62);
        jLabel62.setBounds(0, 980, 153, 23);

        Kepala3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Kepala3.setName("Kepala3"); // NOI18N
        Kepala3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Kepala3KeyPressed(evt);
            }
        });
        FormInput.add(Kepala3);
        Kepala3.setBounds(157, 980, 129, 23);

        KeteranganKepala3.setFocusTraversalPolicyProvider(true);
        KeteranganKepala3.setName("KeteranganKepala3"); // NOI18N
        KeteranganKepala3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKepala3KeyPressed(evt);
            }
        });
        FormInput.add(KeteranganKepala3);
        KeteranganKepala3.setBounds(289, 980, 160, 23);

        jLabel63.setText("Telinga :");
        jLabel63.setName("jLabel63"); // NOI18N
        FormInput.add(jLabel63);
        jLabel63.setBounds(0, 1010, 153, 23);

        Kepala4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Kepala4.setName("Kepala4"); // NOI18N
        Kepala4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Kepala4KeyPressed(evt);
            }
        });
        FormInput.add(Kepala4);
        Kepala4.setBounds(157, 1010, 129, 23);

        KeteranganKepala4.setFocusTraversalPolicyProvider(true);
        KeteranganKepala4.setName("KeteranganKepala4"); // NOI18N
        KeteranganKepala4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKepala4KeyPressed(evt);
            }
        });
        FormInput.add(KeteranganKepala4);
        KeteranganKepala4.setBounds(289, 1010, 160, 23);

        jLabel64.setText("Hidung :");
        jLabel64.setName("jLabel64"); // NOI18N
        FormInput.add(jLabel64);
        jLabel64.setBounds(0, 1040, 153, 23);

        Kepala5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Kepala5.setName("Kepala5"); // NOI18N
        Kepala5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Kepala5KeyPressed(evt);
            }
        });
        FormInput.add(Kepala5);
        Kepala5.setBounds(157, 1040, 129, 23);

        KeteranganKepala5.setFocusTraversalPolicyProvider(true);
        KeteranganKepala5.setName("KeteranganKepala5"); // NOI18N
        KeteranganKepala5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKepala5KeyPressed(evt);
            }
        });
        FormInput.add(KeteranganKepala5);
        KeteranganKepala5.setBounds(289, 1040, 160, 23);

        jLabel65.setText("Mulut :");
        jLabel65.setName("jLabel65"); // NOI18N
        FormInput.add(jLabel65);
        jLabel65.setBounds(0, 1070, 153, 23);

        Kepala6.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Kepala6.setName("Kepala6"); // NOI18N
        Kepala6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Kepala6KeyPressed(evt);
            }
        });
        FormInput.add(Kepala6);
        Kepala6.setBounds(157, 1070, 129, 23);

        KeteranganKepala6.setFocusTraversalPolicyProvider(true);
        KeteranganKepala6.setName("KeteranganKepala6"); // NOI18N
        KeteranganKepala6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKepala6KeyPressed(evt);
            }
        });
        FormInput.add(KeteranganKepala6);
        KeteranganKepala6.setBounds(289, 1070, 160, 23);

        jLabel66.setText("Tenggorokan :");
        jLabel66.setName("jLabel66"); // NOI18N
        FormInput.add(jLabel66);
        jLabel66.setBounds(0, 1100, 153, 23);

        KeteranganKepala7.setFocusTraversalPolicyProvider(true);
        KeteranganKepala7.setName("KeteranganKepala7"); // NOI18N
        KeteranganKepala7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKepala7KeyPressed(evt);
            }
        });
        FormInput.add(KeteranganKepala7);
        KeteranganKepala7.setBounds(289, 1100, 160, 23);

        Kepala7.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Kepala7.setName("Kepala7"); // NOI18N
        Kepala7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Kepala7KeyPressed(evt);
            }
        });
        FormInput.add(Kepala7);
        Kepala7.setBounds(157, 1100, 129, 23);

        jLabel67.setText("Leher :");
        jLabel67.setName("jLabel67"); // NOI18N
        FormInput.add(jLabel67);
        jLabel67.setBounds(0, 1130, 153, 23);

        Kepala8.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Kepala8.setName("Kepala8"); // NOI18N
        Kepala8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Kepala8KeyPressed(evt);
            }
        });
        FormInput.add(Kepala8);
        Kepala8.setBounds(157, 1130, 129, 23);

        KeteranganKepala8.setFocusTraversalPolicyProvider(true);
        KeteranganKepala8.setName("KeteranganKepala8"); // NOI18N
        KeteranganKepala8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKepala8KeyPressed(evt);
            }
        });
        FormInput.add(KeteranganKepala8);
        KeteranganKepala8.setBounds(289, 1130, 160, 23);

        Kepala9.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Kepala9.setName("Kepala9"); // NOI18N
        Kepala9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Kepala9KeyPressed(evt);
            }
        });
        FormInput.add(Kepala9);
        Kepala9.setBounds(562, 890, 129, 23);

        jLabel75.setText("Thorax :");
        jLabel75.setName("jLabel75"); // NOI18N
        FormInput.add(jLabel75);
        jLabel75.setBounds(458, 890, 100, 23);

        KeteranganKepala9.setFocusTraversalPolicyProvider(true);
        KeteranganKepala9.setName("KeteranganKepala9"); // NOI18N
        KeteranganKepala9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKepala9KeyPressed(evt);
            }
        });
        FormInput.add(KeteranganKepala9);
        KeteranganKepala9.setBounds(694, 890, 160, 23);

        jLabel80.setText("Abdomen :");
        jLabel80.setName("jLabel80"); // NOI18N
        FormInput.add(jLabel80);
        jLabel80.setBounds(458, 920, 100, 23);

        Kepala10.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Kepala10.setName("Kepala10"); // NOI18N
        Kepala10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Kepala10KeyPressed(evt);
            }
        });
        FormInput.add(Kepala10);
        Kepala10.setBounds(562, 920, 129, 23);

        KeteranganKepala10.setFocusTraversalPolicyProvider(true);
        KeteranganKepala10.setName("KeteranganKepala10"); // NOI18N
        KeteranganKepala10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKepala10KeyPressed(evt);
            }
        });
        FormInput.add(KeteranganKepala10);
        KeteranganKepala10.setBounds(694, 920, 160, 23);

        jLabel81.setText("Genitalia :");
        jLabel81.setName("jLabel81"); // NOI18N
        FormInput.add(jLabel81);
        jLabel81.setBounds(458, 950, 100, 23);

        Kepala11.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Kepala11.setName("Kepala11"); // NOI18N
        Kepala11.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Kepala11KeyPressed(evt);
            }
        });
        FormInput.add(Kepala11);
        Kepala11.setBounds(562, 950, 129, 23);

        KeteranganKepala11.setFocusTraversalPolicyProvider(true);
        KeteranganKepala11.setName("KeteranganKepala11"); // NOI18N
        KeteranganKepala11.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKepala11KeyPressed(evt);
            }
        });
        FormInput.add(KeteranganKepala11);
        KeteranganKepala11.setBounds(694, 950, 160, 23);

        jLabel82.setText("Anus :");
        jLabel82.setName("jLabel82"); // NOI18N
        FormInput.add(jLabel82);
        jLabel82.setBounds(458, 980, 100, 23);

        Kepala12.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Kepala12.setName("Kepala12"); // NOI18N
        Kepala12.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Kepala12KeyPressed(evt);
            }
        });
        FormInput.add(Kepala12);
        Kepala12.setBounds(562, 980, 129, 23);

        KeteranganKepala12.setFocusTraversalPolicyProvider(true);
        KeteranganKepala12.setName("KeteranganKepala12"); // NOI18N
        KeteranganKepala12.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKepala12KeyPressed(evt);
            }
        });
        FormInput.add(KeteranganKepala12);
        KeteranganKepala12.setBounds(694, 980, 160, 23);

        jLabel83.setText("Muskuloskeletal :");
        jLabel83.setName("jLabel83"); // NOI18N
        FormInput.add(jLabel83);
        jLabel83.setBounds(458, 1010, 100, 23);

        Kepala13.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Kepala13.setName("Kepala13"); // NOI18N
        Kepala13.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Kepala13KeyPressed(evt);
            }
        });
        FormInput.add(Kepala13);
        Kepala13.setBounds(562, 1010, 129, 23);

        KeteranganKepala13.setFocusTraversalPolicyProvider(true);
        KeteranganKepala13.setName("KeteranganKepala13"); // NOI18N
        KeteranganKepala13.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKepala13KeyPressed(evt);
            }
        });
        FormInput.add(KeteranganKepala13);
        KeteranganKepala13.setBounds(694, 1010, 160, 23);

        jLabel84.setText("Ekstrimitas :");
        jLabel84.setName("jLabel84"); // NOI18N
        FormInput.add(jLabel84);
        jLabel84.setBounds(458, 1040, 100, 23);

        Kepala14.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Kepala14.setName("Kepala14"); // NOI18N
        Kepala14.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Kepala14KeyPressed(evt);
            }
        });
        FormInput.add(Kepala14);
        Kepala14.setBounds(562, 1040, 129, 23);

        KeteranganKepala14.setFocusTraversalPolicyProvider(true);
        KeteranganKepala14.setName("KeteranganKepala14"); // NOI18N
        KeteranganKepala14.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKepala14KeyPressed(evt);
            }
        });
        FormInput.add(KeteranganKepala14);
        KeteranganKepala14.setBounds(694, 1040, 160, 23);

        jLabel85.setText("Kelainan :");
        jLabel85.setName("jLabel85"); // NOI18N
        FormInput.add(jLabel85);
        jLabel85.setBounds(458, 1070, 100, 23);

        Kepala15.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Kepala15.setName("Kepala15"); // NOI18N
        Kepala15.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Kepala15KeyPressed(evt);
            }
        });
        FormInput.add(Kepala15);
        Kepala15.setBounds(562, 1070, 129, 23);

        KeteranganKepala15.setFocusTraversalPolicyProvider(true);
        KeteranganKepala15.setName("KeteranganKepala15"); // NOI18N
        KeteranganKepala15.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKepala15KeyPressed(evt);
            }
        });
        FormInput.add(KeteranganKepala15);
        KeteranganKepala15.setBounds(694, 1070, 160, 23);

        jLabel86.setText("Refleks Primitif :");
        jLabel86.setName("jLabel86"); // NOI18N
        FormInput.add(jLabel86);
        jLabel86.setBounds(458, 1100, 100, 23);

        KeteranganKepala16.setFocusTraversalPolicyProvider(true);
        KeteranganKepala16.setName("KeteranganKepala16"); // NOI18N
        KeteranganKepala16.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKepala16KeyPressed(evt);
            }
        });
        FormInput.add(KeteranganKepala16);
        KeteranganKepala16.setBounds(694, 1100, 160, 23);

        Kepala16.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Kepala16.setName("Kepala16"); // NOI18N
        Kepala16.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Kepala16KeyPressed(evt);
            }
        });
        FormInput.add(Kepala16);
        Kepala16.setBounds(562, 1100, 129, 23);

        jLabel137.setText(":");
        jLabel137.setName("jLabel137"); // NOI18N
        FormInput.add(jLabel137);
        jLabel137.setBounds(0, 890, 153, 23);

        jLabel87.setText("Lainnya :");
        jLabel87.setName("jLabel87"); // NOI18N
        FormInput.add(jLabel87);
        jLabel87.setBounds(458, 1130, 100, 23);

        KeteranganKepala17.setFocusTraversalPolicyProvider(true);
        KeteranganKepala17.setName("KeteranganKepala17"); // NOI18N
        KeteranganKepala17.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKepala17KeyPressed(evt);
            }
        });
        FormInput.add(KeteranganKepala17);
        KeteranganKepala17.setBounds(562, 1130, 292, 23);

        jSeparator3.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator3.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator3.setName("jSeparator3"); // NOI18N
        FormInput.add(jSeparator3);
        jSeparator3.setBounds(0, 1160, 880, 1);

        jLabel138.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel138.setText("III. PEMERIKSAAN REGIONAL/KHUSUS/TAMBAHAN");
        jLabel138.setName("jLabel138"); // NOI18N
        FormInput.add(jLabel138);
        jLabel138.setBounds(10, 1160, 340, 23);

        scrollPane12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane12.setName("scrollPane12"); // NOI18N

        PemeriksaanRegional.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        PemeriksaanRegional.setColumns(20);
        PemeriksaanRegional.setRows(3);
        PemeriksaanRegional.setName("PemeriksaanRegional"); // NOI18N
        PemeriksaanRegional.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PemeriksaanRegionalKeyPressed(evt);
            }
        });
        scrollPane12.setViewportView(PemeriksaanRegional);

        FormInput.add(scrollPane12);
        scrollPane12.setBounds(44, 1180, 810, 43);

        jSeparator14.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator14.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator14.setName("jSeparator14"); // NOI18N
        FormInput.add(jSeparator14);
        jSeparator14.setBounds(0, 1230, 880, 1);

        jLabel139.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel139.setText("IV. PEMERIKSAAN PENUNJANG");
        jLabel139.setName("jLabel139"); // NOI18N
        FormInput.add(jLabel139);
        jLabel139.setBounds(10, 1230, 190, 23);

        jLabel88.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel88.setText("Laboratorium :");
        jLabel88.setName("jLabel88"); // NOI18N
        FormInput.add(jLabel88);
        jLabel88.setBounds(44, 1250, 150, 23);

        jLabel89.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel89.setText("Radiologi :");
        jLabel89.setName("jLabel89"); // NOI18N
        FormInput.add(jLabel89);
        jLabel89.setBounds(319, 1250, 150, 23);

        jLabel90.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel90.setText("Penunjang Lainnya :");
        jLabel90.setName("jLabel90"); // NOI18N
        FormInput.add(jLabel90);
        jLabel90.setBounds(594, 1250, 150, 23);

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
        scrollPane9.setBounds(44, 1270, 260, 63);

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
        scrollPane10.setBounds(319, 1270, 260, 63);

        scrollPane11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane11.setName("scrollPane11"); // NOI18N

        Penunjang.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Penunjang.setColumns(20);
        Penunjang.setRows(5);
        Penunjang.setName("Penunjang"); // NOI18N
        Penunjang.setPreferredSize(new java.awt.Dimension(102, 52));
        Penunjang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenunjangKeyPressed(evt);
            }
        });
        scrollPane11.setViewportView(Penunjang);

        FormInput.add(scrollPane11);
        scrollPane11.setBounds(594, 1270, 260, 63);

        jSeparator15.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator15.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator15.setName("jSeparator15"); // NOI18N
        FormInput.add(jSeparator15);
        jSeparator15.setBounds(0, 1340, 880, 1);

        jLabel140.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel140.setText("V. DIAGNOSIS/ASESMEN");
        jLabel140.setName("jLabel140"); // NOI18N
        FormInput.add(jLabel140);
        jLabel140.setBounds(10, 1340, 190, 23);

        scrollPane13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane13.setName("scrollPane13"); // NOI18N

        Diagnosis.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Diagnosis.setColumns(20);
        Diagnosis.setRows(3);
        Diagnosis.setName("Diagnosis"); // NOI18N
        Diagnosis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosisKeyPressed(evt);
            }
        });
        scrollPane13.setViewportView(Diagnosis);

        FormInput.add(scrollPane13);
        scrollPane13.setBounds(44, 1360, 810, 43);

        jSeparator16.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator16.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator16.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator16.setName("jSeparator16"); // NOI18N
        FormInput.add(jSeparator16);
        jSeparator16.setBounds(0, 1410, 880, 1);

        jLabel141.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel141.setText("VI. TATALAKSANA");
        jLabel141.setName("jLabel141"); // NOI18N
        FormInput.add(jLabel141);
        jLabel141.setBounds(10, 1410, 190, 23);

        scrollPane14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane14.setName("scrollPane14"); // NOI18N

        Tatalaksana.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Tatalaksana.setColumns(30);
        Tatalaksana.setRows(20);
        Tatalaksana.setName("Tatalaksana"); // NOI18N
        Tatalaksana.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TatalaksanaKeyPressed(evt);
            }
        });
        scrollPane14.setViewportView(Tatalaksana);

        FormInput.add(scrollPane14);
        scrollPane14.setBounds(44, 1430, 810, 153);

        jSeparator17.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator17.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator17.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator17.setName("jSeparator17"); // NOI18N
        FormInput.add(jSeparator17);
        jSeparator17.setBounds(0, 1590, 880, 1);

        jLabel142.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel142.setText("VII. EDUKASI");
        jLabel142.setName("jLabel142"); // NOI18N
        FormInput.add(jLabel142);
        jLabel142.setBounds(10, 1590, 190, 23);

        scrollPane15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane15.setName("scrollPane15"); // NOI18N

        Edukasi.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Edukasi.setColumns(20);
        Edukasi.setRows(5);
        Edukasi.setName("Edukasi"); // NOI18N
        Edukasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EdukasiKeyPressed(evt);
            }
        });
        scrollPane15.setViewportView(Edukasi);

        FormInput.add(scrollPane15);
        scrollPane15.setBounds(44, 1610, 810, 63);

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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "02-01-2025" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "02-01-2025" }));
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
        /*if(TNoRM.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"Nama Pasien");
        }else if(NmDokter.getText().trim().equals("")){
            Valid.textKosong(BtnDokter,"Dokter");
        }else if(KeluhanUtama.getText().trim().equals("")){
            Valid.textKosong(KeluhanUtama,"Keluhan Utama");
        }else if(RPS.getText().trim().equals("")){
            Valid.textKosong(RPS,"Riwayat Penyakit Sekarang");
        }else if(RPK.getText().trim().equals("")){
            Valid.textKosong(RPK,"Riwayat Penyakit Keluarga");
        }else if(RPD.getText().trim().equals("")){
            Valid.textKosong(RPD,"Riwayat Penyakit Dahulu");
        }else if(RPO.getText().trim().equals("")){
            Valid.textKosong(RPO,"Riwayat Pengunaan obat");
        }else{
            if(Sequel.menyimpantf("penilaian_medis_ranap","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat",40,new String[]{
                    TNoRw.getText(),Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),KdDokter.getText(),Anamnesis.getSelectedItem().toString(),Hubungan.getText(),
                    KeluhanUtama.getText(),RPS.getText(),RPD.getText(),RPK.getText(),RPO.getText(),Alergi.getText(),Keadaan.getSelectedItem().toString(),GCS.getText(),Kesadaran.getSelectedItem().toString(),TD.getText(),
                    Nadi.getText(),RR.getText(),Suhu.getText(),SPO.getText(),BB.getText(),TB.getText(),Kepala.getSelectedItem().toString(),Mata.getSelectedItem().toString(),Gigi.getSelectedItem().toString(),THT.getSelectedItem().toString(),
                    Thoraks.getSelectedItem().toString(),Jantung.getSelectedItem().toString(),Paru.getSelectedItem().toString(),Abdomen.getSelectedItem().toString(),Genital.getSelectedItem().toString(),Ekstremitas.getSelectedItem().toString(),
                    Kulit.getSelectedItem().toString(),KetFisik.getText(),KetLokalis.getText(),Laborat.getText(),Radiologi.getText(),Penunjang.getText(),Diagnosis.getText(),Tatalaksana.getText(),Edukasi.getText()
                })==true){
                    emptTeks();
            }
        }*/
    
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
        /*if(TNoRM.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"Nama Pasien");
        }else if(NmDokter.getText().trim().equals("")){
            Valid.textKosong(BtnDokter,"Dokter");
        }else if(KeluhanUtama.getText().trim().equals("")){
            Valid.textKosong(KeluhanUtama,"Keluhan Utama");
        }else if(RPS.getText().trim().equals("")){
            Valid.textKosong(RPS,"Riwayat Penyakit Sekarang");
        }else if(RPK.getText().trim().equals("")){
            Valid.textKosong(RPK,"Riwayat Penyakit Keluarga");
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
                if(TCari.getText().trim().equals("")){
                    ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penilaian_medis_ranap.tanggal,"+
                        "penilaian_medis_ranap.kd_dokter,penilaian_medis_ranap.anamnesis,penilaian_medis_ranap.hubungan,penilaian_medis_ranap.keluhan_utama,penilaian_medis_ranap.rps,penilaian_medis_ranap.rpk,penilaian_medis_ranap.rpd,penilaian_medis_ranap.rpo,penilaian_medis_ranap.alergi,"+
                        "penilaian_medis_ranap.keadaan,penilaian_medis_ranap.gcs,penilaian_medis_ranap.kesadaran,penilaian_medis_ranap.td,penilaian_medis_ranap.nadi,penilaian_medis_ranap.rr,penilaian_medis_ranap.suhu,penilaian_medis_ranap.spo,penilaian_medis_ranap.bb,penilaian_medis_ranap.tb,"+
                        "penilaian_medis_ranap.kepala,penilaian_medis_ranap.mata,penilaian_medis_ranap.gigi,penilaian_medis_ranap.tht,penilaian_medis_ranap.thoraks,penilaian_medis_ranap.jantung,penilaian_medis_ranap.paru,penilaian_medis_ranap.abdomen,penilaian_medis_ranap.ekstremitas,"+
                        "penilaian_medis_ranap.genital,penilaian_medis_ranap.kulit,penilaian_medis_ranap.ket_fisik,penilaian_medis_ranap.ket_lokalis,penilaian_medis_ranap.lab,penilaian_medis_ranap.rad,penilaian_medis_ranap.penunjang,penilaian_medis_ranap.diagnosis,penilaian_medis_ranap.tata,"+
                        "penilaian_medis_ranap.edukasi,dokter.nm_dokter "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join penilaian_medis_ranap on reg_periksa.no_rawat=penilaian_medis_ranap.no_rawat "+
                        "inner join dokter on penilaian_medis_ranap.kd_dokter=dokter.kd_dokter where "+
                        "penilaian_medis_ranap.tanggal between ? and ? order by penilaian_medis_ranap.tanggal");
                }else{
                    ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penilaian_medis_ranap.tanggal,"+
                        "penilaian_medis_ranap.kd_dokter,penilaian_medis_ranap.anamnesis,penilaian_medis_ranap.hubungan,penilaian_medis_ranap.keluhan_utama,penilaian_medis_ranap.rps,penilaian_medis_ranap.rpk,penilaian_medis_ranap.rpd,penilaian_medis_ranap.rpo,penilaian_medis_ranap.alergi,"+
                        "penilaian_medis_ranap.keadaan,penilaian_medis_ranap.gcs,penilaian_medis_ranap.kesadaran,penilaian_medis_ranap.td,penilaian_medis_ranap.nadi,penilaian_medis_ranap.rr,penilaian_medis_ranap.suhu,penilaian_medis_ranap.spo,penilaian_medis_ranap.bb,penilaian_medis_ranap.tb,"+
                        "penilaian_medis_ranap.kepala,penilaian_medis_ranap.mata,penilaian_medis_ranap.gigi,penilaian_medis_ranap.tht,penilaian_medis_ranap.thoraks,penilaian_medis_ranap.jantung,penilaian_medis_ranap.paru,penilaian_medis_ranap.abdomen,penilaian_medis_ranap.ekstremitas,"+
                        "penilaian_medis_ranap.genital,penilaian_medis_ranap.kulit,penilaian_medis_ranap.ket_fisik,penilaian_medis_ranap.ket_lokalis,penilaian_medis_ranap.lab,penilaian_medis_ranap.rad,penilaian_medis_ranap.penunjang,penilaian_medis_ranap.diagnosis,penilaian_medis_ranap.tata,"+
                        "penilaian_medis_ranap.edukasi,dokter.nm_dokter "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join penilaian_medis_ranap on reg_periksa.no_rawat=penilaian_medis_ranap.no_rawat "+
                        "inner join dokter on penilaian_medis_ranap.kd_dokter=dokter.kd_dokter where "+
                        "penilaian_medis_ranap.tanggal between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or "+
                        "penilaian_medis_ranap.kd_dokter like ? or dokter.nm_dokter like ?) order by penilaian_medis_ranap.tanggal");
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
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='105px'><b>No.Rawat</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='70px'><b>No.RM</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='150px'><b>Nama Pasien</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='65px'><b>Tgl.Lahir</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='55px'><b>J.K.</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='80px'><b>Kode Dokter</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='150px'><b>Nama Dokter</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='115px'><b>Tanggal</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='80px'><b>Anamnesis</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='100px'><b>Hubungan</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='300px'><b>Keluhan Utama</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='150px'><b>Riwayat Penyakit Sekarang</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='150px'><b>Riwayat Penyakit Dahulu</b></td>"+
			    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='150px'><b>Riwayat Penyakit Keluarga</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='150px'><b>Riwayat Penggunakan Obat</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='120px'><b>Riwayat Alergi</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='90px'><b>Keadaan Umum</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='50px'><b>GCS</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='80px'><b>Kesadaran</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='60px'><b>TD(mmHg)</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='75px'><b>Nadi(x/menit)</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='67px'><b>RR(x/menit)</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='40px'><b>Suhu</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='40px'><b>SpO2</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='40px'><b>BB(Kg)</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='40px'><b>TB(cm)</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='80px'><b>Kepala</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='80px'><b>Mata</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='80px'><b>Gigi & Mulut</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='80px'><b>THT</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='80px'><b>Thoraks</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='80px'><b>Jantung</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='80px'><b>Paru</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='80px'><b>Abdomen</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='80px'><b>Genital & Anus</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='80px'><b>Ekstremitas</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='80px'><b>Kulit</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='300px'><b>Ket.Pemeriksaan Fisik</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='200px'><b>Ket.Status Lokalis</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='170px'><b>Laboratorium</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='170px'><b>Radiologi</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='170px'><b>Penunjang Lainnya</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='150px'><b>Diagnosis/Asesmen</b></td>"+
			    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='300px'><b>Tatalaksana</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='150px'><b>Edukasi</b></td>"+
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
                               "<td valign='top'>"+rs.getString("rpk")+"</td>"+
                               "<td valign='top'>"+rs.getString("rpo")+"</td>"+
                               "<td valign='top'>"+rs.getString("alergi")+"</td>"+
                               "<td valign='top'>"+rs.getString("keadaan")+"</td>"+
                               "<td valign='top'>"+rs.getString("gcs")+"</td>"+
                               "<td valign='top'>"+rs.getString("kesadaran")+"</td>"+
                               "<td valign='top'>"+rs.getString("td")+"</td>"+
                               "<td valign='top'>"+rs.getString("nadi")+"</td>"+
                               "<td valign='top'>"+rs.getString("rr")+"</td>"+
                               "<td valign='top'>"+rs.getString("suhu")+"</td>"+
                               "<td valign='top'>"+rs.getString("spo")+"</td>"+
                               "<td valign='top'>"+rs.getString("bb")+"</td>"+
                               "<td valign='top'>"+rs.getString("tb")+"</td>"+
                               "<td valign='top'>"+rs.getString("kepala")+"</td>"+
                               "<td valign='top'>"+rs.getString("mata")+"</td>"+
                               "<td valign='top'>"+rs.getString("gigi")+"</td>"+
                               "<td valign='top'>"+rs.getString("tht")+"</td>"+
                               "<td valign='top'>"+rs.getString("thoraks")+"</td>"+
                               "<td valign='top'>"+rs.getString("jantung")+"</td>"+
                               "<td valign='top'>"+rs.getString("paru")+"</td>"+
                               "<td valign='top'>"+rs.getString("abdomen")+"</td>"+
                               "<td valign='top'>"+rs.getString("genital")+"</td>"+
                               "<td valign='top'>"+rs.getString("ekstremitas")+"</td>"+
                               "<td valign='top'>"+rs.getString("kulit")+"</td>"+
                               "<td valign='top'>"+rs.getString("ket_fisik")+"</td>"+
                               "<td valign='top'>"+rs.getString("ket_lokalis")+"</td>"+
                               "<td valign='top'>"+rs.getString("lab")+"</td>"+
                               "<td valign='top'>"+rs.getString("rad")+"</td>"+
                               "<td valign='top'>"+rs.getString("penunjang")+"</td>"+
                               "<td valign='top'>"+rs.getString("diagnosis")+"</td>"+
                               "<td valign='top'>"+rs.getString("tata")+"</td>"+
                               "<td valign='top'>"+rs.getString("edukasi")+"</td>"+
                            "</tr>");
                    }
                    LoadHTML.setText(
                        "<html>"+
                          "<table width='4600px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
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

                    File f = new File("DataPenilaianAwalMedisRanap.html");            
                    BufferedWriter bw = new BufferedWriter(new FileWriter(f));            
                    bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                                "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                                "<table width='4600px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                    "<tr class='isi2'>"+
                                        "<td valign='top' align='center'>"+
                                            "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                            akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                            akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                            "<font size='2' face='Tahoma'>DATA PENILAIAN AWAL MEDIS RAWAT INAP<br><br></font>"+        
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
        //Valid.pindah(evt,Monitoring,BtnSimpan);
    }//GEN-LAST:event_BtnDokterKeyPressed

    private void AnamnesisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AnamnesisKeyPressed
        Valid.pindah(evt,TglAsuhan,Hubungan);
    }//GEN-LAST:event_AnamnesisKeyPressed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if(TabRawat.getSelectedIndex()==1){
            tampil();
        }
    }//GEN-LAST:event_TabRawatMouseClicked

    private void TglAsuhanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglAsuhanKeyPressed
        //Valid.pindah(evt,Edukasi,Anamnesis);
    }//GEN-LAST:event_TglAsuhanKeyPressed

    private void HubunganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HubunganKeyPressed
        //Valid.pindah(evt,Anamnesis,KeluhanUtama);
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
            
            Valid.MyReportqry("rptCetakPenilaianAwalMedisRanap.jasper","report","::[ Laporan Penilaian Awal Medis Rawat Inap ]::",
                "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penilaian_medis_ranap.tanggal,"+
                "penilaian_medis_ranap.kd_dokter,penilaian_medis_ranap.anamnesis,penilaian_medis_ranap.hubungan,penilaian_medis_ranap.keluhan_utama,penilaian_medis_ranap.rps,penilaian_medis_ranap.rpk,penilaian_medis_ranap.rpd,penilaian_medis_ranap.rpo,penilaian_medis_ranap.alergi,"+
                "penilaian_medis_ranap.keadaan,penilaian_medis_ranap.gcs,penilaian_medis_ranap.kesadaran,penilaian_medis_ranap.td,penilaian_medis_ranap.nadi,penilaian_medis_ranap.rr,penilaian_medis_ranap.suhu,penilaian_medis_ranap.spo,penilaian_medis_ranap.bb,penilaian_medis_ranap.tb,"+
                "penilaian_medis_ranap.kepala,penilaian_medis_ranap.mata,penilaian_medis_ranap.gigi,penilaian_medis_ranap.tht,penilaian_medis_ranap.thoraks,penilaian_medis_ranap.jantung,penilaian_medis_ranap.paru,penilaian_medis_ranap.abdomen,penilaian_medis_ranap.ekstremitas,"+
                "penilaian_medis_ranap.genital,penilaian_medis_ranap.kulit,penilaian_medis_ranap.ket_fisik,penilaian_medis_ranap.ket_lokalis,penilaian_medis_ranap.lab,penilaian_medis_ranap.rad,penilaian_medis_ranap.penunjang,penilaian_medis_ranap.diagnosis,penilaian_medis_ranap.tata,"+
                "penilaian_medis_ranap.edukasi,dokter.nm_dokter "+
                "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join penilaian_medis_ranap on reg_periksa.no_rawat=penilaian_medis_ranap.no_rawat "+
                "inner join dokter on penilaian_medis_ranap.kd_dokter=dokter.kd_dokter where penilaian_medis_ranap.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'",param);
        }
    }//GEN-LAST:event_MnPenilaianMedisActionPerformed

    private void TNoRw1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRw1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TNoRw1KeyPressed

    private void TNoRw2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRw2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TNoRw2KeyPressed

    private void GKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GKeyPressed
        //Valid.pindah(evt,StatusKawin3,P);
    }//GEN-LAST:event_GKeyPressed

    private void PKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PKeyPressed
        Valid.pindah(evt,G,A);
    }//GEN-LAST:event_PKeyPressed

    private void AKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AKeyPressed
        Valid.pindah(evt,P,Hidup);
    }//GEN-LAST:event_AKeyPressed

    private void HidupKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HidupKeyPressed
        //Valid.pindah(evt,A,HPHT);
    }//GEN-LAST:event_HidupKeyPressed

    private void Hidup1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Hidup1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Hidup1KeyPressed

    private void BtnTambahMasalahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahMasalahActionPerformed
        if(TNoRM.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Pilih terlebih dahulu pasien yang mau dimasukkan data kelarihannya...");
            Anamnesis.requestFocus();
        }else{
            //emptTeksPersalinan();
            DlgRiwayatPersalinan.setLocationRelativeTo(internalFrame1);
            DlgRiwayatPersalinan.setVisible(true);
        }
    }//GEN-LAST:event_BtnTambahMasalahActionPerformed

    private void BtnHapusRiwayatPersalinanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusRiwayatPersalinanActionPerformed
        if(tbRiwayatKehamilan.getSelectedRow()>-1){
            Sequel.meghapus("riwayat_persalinan_pasien","no_rkm_medis","tgl_thn",TNoRM.getText(),tbRiwayatKehamilan.getValueAt(tbRiwayatKehamilan.getSelectedRow(),1).toString());
            //tampilPersalinan();
        }else{
            JOptionPane.showMessageDialog(rootPane,"Silahkan anda pilih data terlebih dahulu..!!");
        }
    }//GEN-LAST:event_BtnHapusRiwayatPersalinanActionPerformed

    private void BtnKeluarKehamilanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarKehamilanActionPerformed
        DlgRiwayatPersalinan.dispose();
    }//GEN-LAST:event_BtnKeluarKehamilanActionPerformed

    private void UsiaHamilKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_UsiaHamilKeyPressed
        Valid.pindah(evt,Keadaan,JK);
    }//GEN-LAST:event_UsiaHamilKeyPressed

    private void BtnSimpanRiwayatKehamilanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanRiwayatKehamilanActionPerformed
        if(TempatPersalinan.getText().trim().equals("")){
            Valid.textKosong(TempatPersalinan,"Tempat Persalinan");
        }else if(JenisPersalinan.getText().trim().equals("")){
            Valid.textKosong(JenisPersalinan,"Jenis Persalinan");
        }else if(Penolong.getText().trim().equals("")){
            Valid.textKosong(Penolong,"Penolong Persalinan");
        }else if(Penyulit.getText().trim().equals("")){
            Valid.textKosong(Penyulit,"Penyulit Persalinan");
        }else if(Keadaan.getText().trim().equals("")){
            Valid.textKosong(Keadaan,"Keadaan Persalinan");
        }else if(UsiaHamil.getText().trim().equals("")){
            Valid.textKosong(UsiaHamil,"Usia Hamil");
        }else if(BBPB.getText().trim().equals("")){
            Valid.textKosong(BBPB,"BB/PB");
        }else{
            if(Sequel.menyimpantf("riwayat_persalinan_pasien","?,?,?,?,?,?,?,?,?,?","Riwayat Persalinan",10,new String[]{
                TNoRM.getText(),Valid.SetTgl(TanggalPersalinan.getSelectedItem()+""),TempatPersalinan.getText(),UsiaHamil.getText(),JenisPersalinan.getText(),Penolong.getText(),Penyulit.getText(),JK.getSelectedItem().toString().substring(0,1),BBPB.getText(),Keadaan.getText()
            })==true){
                //emptTeksPersalinan();
                //tampilPersalinan();
            }
        }
    }//GEN-LAST:event_BtnSimpanRiwayatKehamilanActionPerformed

    private void TempatPersalinanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TempatPersalinanKeyPressed
        Valid.pindah(evt,BtnKeluarKehamilan,JenisPersalinan);
    }//GEN-LAST:event_TempatPersalinanKeyPressed

    private void JenisPersalinanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JenisPersalinanKeyPressed
        Valid.pindah(evt,TempatPersalinan,Penolong);
    }//GEN-LAST:event_JenisPersalinanKeyPressed

    private void JKKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JKKeyPressed
        Valid.pindah(evt,UsiaHamil,BBPB);
    }//GEN-LAST:event_JKKeyPressed

    private void PenolongKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenolongKeyPressed
        Valid.pindah(evt,JenisPersalinan,Penyulit);
    }//GEN-LAST:event_PenolongKeyPressed

    private void BBPBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BBPBKeyPressed
        Valid.pindah(evt,JK,BtnSimpanRiwayatKehamilan);
    }//GEN-LAST:event_BBPBKeyPressed

    private void PenyulitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenyulitKeyPressed
        Valid.pindah(evt,Penolong,Keadaan);
    }//GEN-LAST:event_PenyulitKeyPressed

    private void KeadaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeadaanKeyPressed
        Valid.pindah(evt,Penyulit,UsiaHamil);
    }//GEN-LAST:event_KeadaanKeyPressed

    private void Anamnesis1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Anamnesis1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Anamnesis1KeyPressed

    private void Anamnesis2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Anamnesis2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Anamnesis2KeyPressed

    private void Anamnesis3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Anamnesis3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Anamnesis3KeyPressed

    private void Anamnesis4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Anamnesis4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Anamnesis4KeyPressed

    private void Hubungan1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Hubungan1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Hubungan1KeyPressed

    private void TglAsuhan1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglAsuhan1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglAsuhan1KeyPressed

    private void Hidup2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Hidup2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Hidup2KeyPressed

    private void Anamnesis5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Anamnesis5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Anamnesis5KeyPressed

    private void Hubungan2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Hubungan2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Hubungan2KeyPressed

    private void Anamnesis6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Anamnesis6KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Anamnesis6KeyPressed

    private void Anamnesis7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Anamnesis7KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Anamnesis7KeyPressed

    private void Anamnesis8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Anamnesis8KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Anamnesis8KeyPressed

    private void Anamnesis9KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Anamnesis9KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Anamnesis9KeyPressed

    private void tbAPGARPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tbAPGARPropertyChange
        if(this.isVisible()==true){
            getDataApgar();
        }
    }//GEN-LAST:event_tbAPGARPropertyChange

    private void Anamnesis10KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Anamnesis10KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Anamnesis10KeyPressed

    private void Anamnesis11KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Anamnesis11KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Anamnesis11KeyPressed

    private void Anamnesis12KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Anamnesis12KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Anamnesis12KeyPressed

    private void Anamnesis13KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Anamnesis13KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Anamnesis13KeyPressed

    private void Anamnesis14KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Anamnesis14KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Anamnesis14KeyPressed

    private void A1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_A1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_A1KeyPressed

    private void A2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_A2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_A2KeyPressed

    private void A3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_A3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_A3KeyPressed

    private void A4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_A4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_A4KeyPressed

    private void A5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_A5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_A5KeyPressed

    private void A6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_A6KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_A6KeyPressed

    private void Anamnesis15KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Anamnesis15KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Anamnesis15KeyPressed

    private void G1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_G1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_G1KeyPressed

    private void G2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_G2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_G2KeyPressed

    private void G3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_G3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_G3KeyPressed

    private void G4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_G4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_G4KeyPressed

    private void G5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_G5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_G5KeyPressed

    private void G6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_G6KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_G6KeyPressed

    private void G7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_G7KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_G7KeyPressed

    private void G8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_G8KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_G8KeyPressed

    private void KepalaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KepalaKeyPressed
        //Valid.pindah(evt,KondisiUmum,KeteranganKepala);
    }//GEN-LAST:event_KepalaKeyPressed

    private void KeteranganKepalaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKepalaKeyPressed
        //Valid.pindah(evt,Kepala,Thoraks);
    }//GEN-LAST:event_KeteranganKepalaKeyPressed

    private void Kepala1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kepala1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Kepala1KeyPressed

    private void KeteranganKepala1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKepala1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganKepala1KeyPressed

    private void Kepala2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kepala2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Kepala2KeyPressed

    private void KeteranganKepala2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKepala2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganKepala2KeyPressed

    private void Kepala3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kepala3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Kepala3KeyPressed

    private void KeteranganKepala3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKepala3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganKepala3KeyPressed

    private void Kepala4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kepala4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Kepala4KeyPressed

    private void KeteranganKepala4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKepala4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganKepala4KeyPressed

    private void Kepala5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kepala5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Kepala5KeyPressed

    private void KeteranganKepala5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKepala5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganKepala5KeyPressed

    private void Kepala6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kepala6KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Kepala6KeyPressed

    private void KeteranganKepala6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKepala6KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganKepala6KeyPressed

    private void KeteranganKepala7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKepala7KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganKepala7KeyPressed

    private void Kepala7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kepala7KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Kepala7KeyPressed

    private void Kepala8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kepala8KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Kepala8KeyPressed

    private void KeteranganKepala8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKepala8KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganKepala8KeyPressed

    private void Kepala9KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kepala9KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Kepala9KeyPressed

    private void KeteranganKepala9KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKepala9KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganKepala9KeyPressed

    private void Kepala10KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kepala10KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Kepala10KeyPressed

    private void KeteranganKepala10KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKepala10KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganKepala10KeyPressed

    private void Kepala11KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kepala11KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Kepala11KeyPressed

    private void KeteranganKepala11KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKepala11KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganKepala11KeyPressed

    private void Kepala12KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kepala12KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Kepala12KeyPressed

    private void KeteranganKepala12KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKepala12KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganKepala12KeyPressed

    private void Kepala13KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kepala13KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Kepala13KeyPressed

    private void KeteranganKepala13KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKepala13KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganKepala13KeyPressed

    private void Kepala14KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kepala14KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Kepala14KeyPressed

    private void KeteranganKepala14KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKepala14KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganKepala14KeyPressed

    private void Kepala15KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kepala15KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Kepala15KeyPressed

    private void KeteranganKepala15KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKepala15KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganKepala15KeyPressed

    private void KeteranganKepala16KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKepala16KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganKepala16KeyPressed

    private void Kepala16KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kepala16KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Kepala16KeyPressed

    private void KeteranganKepala17KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKepala17KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganKepala17KeyPressed

    private void PemeriksaanRegionalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PemeriksaanRegionalKeyPressed
        //Valid.pindah2(evt,Penunjang,Tatalaksana);
    }//GEN-LAST:event_PemeriksaanRegionalKeyPressed

    private void LaboratKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LaboratKeyPressed
        //Valid.pindah2(evt,KetLokalis,Radiologi);
    }//GEN-LAST:event_LaboratKeyPressed

    private void RadiologiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RadiologiKeyPressed
        //Valid.pindah2(evt,Laborat,Penunjang);
    }//GEN-LAST:event_RadiologiKeyPressed

    private void PenunjangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenunjangKeyPressed
        //Valid.pindah2(evt,Radiologi,Diagnosis);
    }//GEN-LAST:event_PenunjangKeyPressed

    private void DiagnosisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosisKeyPressed
        //Valid.pindah2(evt,Penunjang,Tatalaksana);
    }//GEN-LAST:event_DiagnosisKeyPressed

    private void TatalaksanaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TatalaksanaKeyPressed
        //Valid.pindah2(evt,Diagnosis,Edukasi);
    }//GEN-LAST:event_TatalaksanaKeyPressed

    private void EdukasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EdukasiKeyPressed
        Valid.pindah2(evt,Tatalaksana,BtnSimpan);
    }//GEN-LAST:event_EdukasiKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMPenilaianAwalMedisRanapNeonatus dialog = new RMPenilaianAwalMedisRanapNeonatus(new javax.swing.JFrame(), true);
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
    private widget.TextBox A1;
    private widget.TextBox A2;
    private widget.TextBox A3;
    private widget.TextBox A4;
    private widget.TextBox A5;
    private widget.TextBox A6;
    private widget.ComboBox Anamnesis;
    private widget.ComboBox Anamnesis1;
    private widget.ComboBox Anamnesis10;
    private widget.ComboBox Anamnesis11;
    private widget.ComboBox Anamnesis12;
    private widget.ComboBox Anamnesis13;
    private widget.ComboBox Anamnesis14;
    private widget.ComboBox Anamnesis15;
    private widget.ComboBox Anamnesis2;
    private widget.ComboBox Anamnesis3;
    private widget.ComboBox Anamnesis4;
    private widget.ComboBox Anamnesis5;
    private widget.ComboBox Anamnesis6;
    private widget.ComboBox Anamnesis7;
    private widget.ComboBox Anamnesis8;
    private widget.ComboBox Anamnesis9;
    private widget.TextBox BBPB;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnDokter;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnHapusRiwayatPersalinan;
    private widget.Button BtnKeluar;
    private widget.Button BtnKeluarKehamilan;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.Button BtnSimpanRiwayatKehamilan;
    private widget.Button BtnTambahMasalah;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.TextArea Diagnosis;
    private javax.swing.JDialog DlgRiwayatPersalinan;
    private widget.TextArea Edukasi;
    private widget.PanelBiasa FormInput;
    private widget.TextBox G;
    private widget.TextBox G1;
    private widget.TextBox G2;
    private widget.TextBox G3;
    private widget.TextBox G4;
    private widget.TextBox G5;
    private widget.TextBox G6;
    private widget.TextBox G7;
    private widget.TextBox G8;
    private widget.TextBox Hidup;
    private widget.TextBox Hidup1;
    private widget.TextBox Hidup2;
    private widget.TextBox Hubungan;
    private widget.TextBox Hubungan1;
    private widget.TextBox Hubungan2;
    private widget.ComboBox JK;
    private widget.TextBox JenisPersalinan;
    private widget.TextBox Jk;
    private widget.TextBox KdDokter;
    private widget.TextBox Keadaan;
    private widget.ComboBox Kepala;
    private widget.ComboBox Kepala1;
    private widget.ComboBox Kepala10;
    private widget.ComboBox Kepala11;
    private widget.ComboBox Kepala12;
    private widget.ComboBox Kepala13;
    private widget.ComboBox Kepala14;
    private widget.ComboBox Kepala15;
    private widget.ComboBox Kepala16;
    private widget.ComboBox Kepala2;
    private widget.ComboBox Kepala3;
    private widget.ComboBox Kepala4;
    private widget.ComboBox Kepala5;
    private widget.ComboBox Kepala6;
    private widget.ComboBox Kepala7;
    private widget.ComboBox Kepala8;
    private widget.ComboBox Kepala9;
    private widget.TextBox KeteranganKepala;
    private widget.TextBox KeteranganKepala1;
    private widget.TextBox KeteranganKepala10;
    private widget.TextBox KeteranganKepala11;
    private widget.TextBox KeteranganKepala12;
    private widget.TextBox KeteranganKepala13;
    private widget.TextBox KeteranganKepala14;
    private widget.TextBox KeteranganKepala15;
    private widget.TextBox KeteranganKepala16;
    private widget.TextBox KeteranganKepala17;
    private widget.TextBox KeteranganKepala2;
    private widget.TextBox KeteranganKepala3;
    private widget.TextBox KeteranganKepala4;
    private widget.TextBox KeteranganKepala5;
    private widget.TextBox KeteranganKepala6;
    private widget.TextBox KeteranganKepala7;
    private widget.TextBox KeteranganKepala8;
    private widget.TextBox KeteranganKepala9;
    private widget.Label LCount;
    private widget.TextArea Laborat;
    private widget.editorpane LoadHTML;
    private javax.swing.JMenuItem MnPenilaianMedis;
    private widget.TextBox2 N1;
    private widget.TextBox2 N10;
    private widget.TextBox2 N5;
    private widget.TextBox NmDokter;
    private widget.TextBox P;
    private widget.TextArea PemeriksaanRegional;
    private widget.TextBox Penolong;
    private widget.TextArea Penunjang;
    private widget.TextBox Penyulit;
    private widget.TextArea Radiologi;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll2;
    private widget.ScrollPane Scroll6;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TNoRw1;
    private widget.TextBox TNoRw2;
    private widget.TextBox TPasien;
    private javax.swing.JTabbedPane TabRawat;
    private widget.Tanggal TanggalPersalinan;
    private widget.TextArea Tatalaksana;
    private widget.TextBox TempatPersalinan;
    private widget.Tanggal TglAsuhan;
    private widget.Tanggal TglAsuhan1;
    private widget.TextBox TglLahir;
    private widget.TextBox UsiaHamil;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.InternalFrame internalFrame4;
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
    private widget.Label jLabel19;
    private widget.Label jLabel21;
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
    private widget.Label jLabel99;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator15;
    private javax.swing.JSeparator jSeparator16;
    private javax.swing.JSeparator jSeparator17;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private widget.Label label11;
    private widget.Label label12;
    private widget.Label label14;
    private widget.Label label71;
    private widget.PanelBiasa panelBiasa2;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollInput;
    private widget.ScrollPane scrollPane10;
    private widget.ScrollPane scrollPane11;
    private widget.ScrollPane scrollPane12;
    private widget.ScrollPane scrollPane13;
    private widget.ScrollPane scrollPane14;
    private widget.ScrollPane scrollPane15;
    private widget.ScrollPane scrollPane9;
    private widget.Table tbAPGAR;
    private widget.Table tbObat;
    private widget.Table tbRiwayatKehamilan;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            if(TCari.getText().trim().equals("")){
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penilaian_medis_ranap.tanggal,"+
                        "penilaian_medis_ranap.kd_dokter,penilaian_medis_ranap.anamnesis,penilaian_medis_ranap.hubungan,penilaian_medis_ranap.keluhan_utama,penilaian_medis_ranap.rps,penilaian_medis_ranap.rpk,penilaian_medis_ranap.rpd,penilaian_medis_ranap.rpo,penilaian_medis_ranap.alergi,"+
                        "penilaian_medis_ranap.keadaan,penilaian_medis_ranap.gcs,penilaian_medis_ranap.kesadaran,penilaian_medis_ranap.td,penilaian_medis_ranap.nadi,penilaian_medis_ranap.rr,penilaian_medis_ranap.suhu,penilaian_medis_ranap.spo,penilaian_medis_ranap.bb,penilaian_medis_ranap.tb,"+
                        "penilaian_medis_ranap.kepala,penilaian_medis_ranap.mata,penilaian_medis_ranap.gigi,penilaian_medis_ranap.tht,penilaian_medis_ranap.thoraks,penilaian_medis_ranap.jantung,penilaian_medis_ranap.paru,penilaian_medis_ranap.abdomen,penilaian_medis_ranap.ekstremitas,"+
                        "penilaian_medis_ranap.genital,penilaian_medis_ranap.kulit,penilaian_medis_ranap.ket_fisik,penilaian_medis_ranap.ket_lokalis,penilaian_medis_ranap.lab,penilaian_medis_ranap.rad,penilaian_medis_ranap.penunjang,penilaian_medis_ranap.diagnosis,penilaian_medis_ranap.tata,"+
                        "penilaian_medis_ranap.edukasi,dokter.nm_dokter "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join penilaian_medis_ranap on reg_periksa.no_rawat=penilaian_medis_ranap.no_rawat "+
                        "inner join dokter on penilaian_medis_ranap.kd_dokter=dokter.kd_dokter where "+
                        "penilaian_medis_ranap.tanggal between ? and ? order by penilaian_medis_ranap.tanggal");
            }else{
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penilaian_medis_ranap.tanggal,"+
                        "penilaian_medis_ranap.kd_dokter,penilaian_medis_ranap.anamnesis,penilaian_medis_ranap.hubungan,penilaian_medis_ranap.keluhan_utama,penilaian_medis_ranap.rps,penilaian_medis_ranap.rpk,penilaian_medis_ranap.rpd,penilaian_medis_ranap.rpo,penilaian_medis_ranap.alergi,"+
                        "penilaian_medis_ranap.keadaan,penilaian_medis_ranap.gcs,penilaian_medis_ranap.kesadaran,penilaian_medis_ranap.td,penilaian_medis_ranap.nadi,penilaian_medis_ranap.rr,penilaian_medis_ranap.suhu,penilaian_medis_ranap.spo,penilaian_medis_ranap.bb,penilaian_medis_ranap.tb,"+
                        "penilaian_medis_ranap.kepala,penilaian_medis_ranap.mata,penilaian_medis_ranap.gigi,penilaian_medis_ranap.tht,penilaian_medis_ranap.thoraks,penilaian_medis_ranap.jantung,penilaian_medis_ranap.paru,penilaian_medis_ranap.abdomen,penilaian_medis_ranap.ekstremitas,"+
                        "penilaian_medis_ranap.genital,penilaian_medis_ranap.kulit,penilaian_medis_ranap.ket_fisik,penilaian_medis_ranap.ket_lokalis,penilaian_medis_ranap.lab,penilaian_medis_ranap.rad,penilaian_medis_ranap.penunjang,penilaian_medis_ranap.diagnosis,penilaian_medis_ranap.tata,"+
                        "penilaian_medis_ranap.edukasi,dokter.nm_dokter "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join penilaian_medis_ranap on reg_periksa.no_rawat=penilaian_medis_ranap.no_rawat "+
                        "inner join dokter on penilaian_medis_ranap.kd_dokter=dokter.kd_dokter where "+
                        "penilaian_medis_ranap.tanggal between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or "+
                        "penilaian_medis_ranap.kd_dokter like ? or dokter.nm_dokter like ?) order by penilaian_medis_ranap.tanggal");
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
                        rs.getString("anamnesis"),rs.getString("hubungan"),rs.getString("keluhan_utama"),rs.getString("rps"),rs.getString("rpd"),rs.getString("rpk"),rs.getString("rpo"),rs.getString("alergi"),
                        rs.getString("keadaan"),rs.getString("gcs"),rs.getString("kesadaran"),rs.getString("td"),rs.getString("nadi"),rs.getString("rr"),rs.getString("suhu"),rs.getString("spo"),rs.getString("bb"),
                        rs.getString("tb"),rs.getString("kepala"),rs.getString("mata"),rs.getString("gigi"),rs.getString("tht"),rs.getString("thoraks"),rs.getString("jantung"),rs.getString("paru"),rs.getString("abdomen"),
                        rs.getString("genital"),rs.getString("ekstremitas"),rs.getString("kulit"),rs.getString("ket_fisik"),rs.getString("ket_lokalis"),rs.getString("lab"),rs.getString("rad"),rs.getString("penunjang"),
                        rs.getString("diagnosis"),rs.getString("tata"),rs.getString("edukasi")
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
        BtnSimpan.setEnabled(akses.getpenilaian_awal_medis_ranap());
        BtnHapus.setEnabled(akses.getpenilaian_awal_medis_ranap());
        BtnEdit.setEnabled(akses.getpenilaian_awal_medis_ranap());
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
        if(Sequel.queryu2tf("delete from penilaian_medis_ranap where no_rawat=?",1,new String[]{
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
        /*if(Sequel.mengedittf("penilaian_medis_ranap","no_rawat=?","no_rawat=?,tanggal=?,kd_dokter=?,anamnesis=?,hubungan=?,keluhan_utama=?,rps=?,rpk=?,rpd=?,rpo=?,alergi=?,keadaan=?,gcs=?,kesadaran=?,td=?,nadi=?,rr=?,suhu=?,"+
                "spo=?,bb=?,tb=?,kepala=?,mata=?,gigi=?,tht=?,thoraks=?,jantung=?,paru=?,abdomen=?,genital=?,ekstremitas=?,kulit=?,ket_fisik=?,ket_lokalis=?,lab=?,rad=?,penunjang=?,diagnosis=?,tata=?,edukasi=?",41,new String[]{
                TNoRw.getText(),Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),KdDokter.getText(),Anamnesis.getSelectedItem().toString(),Hubungan.getText(),
                    KeluhanUtama.getText(),RPS.getText(),RPD.getText(),RPK.getText(),RPO.getText(),Alergi.getText(),Keadaan.getSelectedItem().toString(),GCS.getText(),Kesadaran.getSelectedItem().toString(),TD.getText(),
                    Nadi.getText(),RR.getText(),Suhu.getText(),SPO.getText(),BB.getText(),TB.getText(),Kepala.getSelectedItem().toString(),Mata.getSelectedItem().toString(),Gigi.getSelectedItem().toString(),THT.getSelectedItem().toString(),
                    Thoraks.getSelectedItem().toString(),Jantung.getSelectedItem().toString(),Paru.getSelectedItem().toString(),Abdomen.getSelectedItem().toString(),Genital.getSelectedItem().toString(),Ekstremitas.getSelectedItem().toString(),
                    Kulit.getSelectedItem().toString(),KetFisik.getText(),KetLokalis.getText(),Laborat.getText(),Radiologi.getText(),Penunjang.getText(),Diagnosis.getText(),Tatalaksana.getText(),Edukasi.getText(),
                    tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
            })==true){
               tampil();
               emptTeks();
               TabRawat.setSelectedIndex(1);
        }*/
    }
    
    private void getDataApgar() {
        try {
            if(tbAPGAR.getValueAt(0,4).toString().equals("")||tbAPGAR.getValueAt(1,4).toString().equals("")||tbAPGAR.getValueAt(2,4).toString().equals("")||
                tbAPGAR.getValueAt(3,4).toString().equals("")||tbAPGAR.getValueAt(4,4).toString().equals("")){
                N1.setText("");
            }else{
                N1.setText((Valid.SetInteger(tbAPGAR.getValueAt(0,4).toString())+Valid.SetInteger(tbAPGAR.getValueAt(1,4).toString())+Valid.SetInteger(tbAPGAR.getValueAt(2,4).toString())+Valid.SetInteger(tbAPGAR.getValueAt(3,4).toString())+Valid.SetInteger(tbAPGAR.getValueAt(4,4).toString()))+"");
            }
        } catch (Exception e) {
            N1.setText("");
        }
        
        try {
            if(tbAPGAR.getValueAt(0,5).toString().equals("")||tbAPGAR.getValueAt(1,5).toString().equals("")||tbAPGAR.getValueAt(2,5).toString().equals("")||
                tbAPGAR.getValueAt(3,5).toString().equals("")||tbAPGAR.getValueAt(4,5).toString().equals("")){
                N5.setText("");
            }else{
                N5.setText((Valid.SetInteger(tbAPGAR.getValueAt(0,5).toString())+Valid.SetInteger(tbAPGAR.getValueAt(1,5).toString())+Valid.SetInteger(tbAPGAR.getValueAt(2,5).toString())+Valid.SetInteger(tbAPGAR.getValueAt(3,5).toString())+Valid.SetInteger(tbAPGAR.getValueAt(4,5).toString()))+"");
            }
        } catch (Exception e) {
            N5.setText("");
        }
        
        try {
            if(tbAPGAR.getValueAt(0,6).toString().equals("")||tbAPGAR.getValueAt(1,6).toString().equals("")||tbAPGAR.getValueAt(2,6).toString().equals("")||
                tbAPGAR.getValueAt(3,6).toString().equals("")||tbAPGAR.getValueAt(4,6).toString().equals("")){
                N10.setText("");
            }else{
                N10.setText((Valid.SetInteger(tbAPGAR.getValueAt(0,6).toString())+Valid.SetInteger(tbAPGAR.getValueAt(1,6).toString())+Valid.SetInteger(tbAPGAR.getValueAt(2,6).toString())+Valid.SetInteger(tbAPGAR.getValueAt(3,6).toString())+Valid.SetInteger(tbAPGAR.getValueAt(4,6).toString()))+"");
            }
        } catch (Exception e) {
            N10.setText("");
        }
    }
}

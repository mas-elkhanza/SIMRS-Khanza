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
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import kepegawaian.DlgCariDokter;
import kepegawaian.DlgCariPetugas;


/**
 *
 * @author perpustakaan
 */
public final class AsesmenAwalMedisRanapNeonatus extends javax.swing.JDialog {
    private final DefaultTableModel tabMode,tabModeMasalah,tabModeDetailMasalah;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps,ps2;
    private ResultSet rs,rs2;
    private int i=0,jml=0,index=0;
    private DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    private DlgCariDokter dokter=new DlgCariDokter(null,false);
    private boolean[] pilih; 
    private String[] kode,masalah;
    private String masalahkeperawatan=""; 
    private StringBuilder htmlContent;
    private SimpleDateFormat tanggalNow = new SimpleDateFormat("yyyy-MM-dd");
//    private SimpleDateFormat tanggalNow = new SimpleDateFormat("dd-MM-yyyy");
    private SimpleDateFormat jamNow = new SimpleDateFormat("HH:mm:ss");
    
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public AsesmenAwalMedisRanapNeonatus(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.RM","Nama Pasien","Tanggal Lahir","J.K.","NIP","Nama Dokter","Tanggal","Jam","Anamnesis","Hubungan","Keluhan Utama","Riwayat Penyakit Sekarang","Riwayat Penyakit Dahulu","Riwayat Penyakit Keluarga","Riwayat Penggunakan Obat","Riwayat Alergi","Kondisi Saat Lahir",
            "Keterangan Kondisi","Warna Kulit","Denyut Jantung","Reflek Gerak","Tonus Otot","Pernafasan","Skor Warna Kulit","Skor Denyut Jantung","Skor Reflek Gerak","Skor Tonus Otot","Skor Pernafasan","Total Pemeriksaan Fisik",
            "HR(x/m)","RR(x/menit)","Suhu","SpO2","BBL(gr)","PB(cm)","LK(cm)","LD(cm)","LP(cm)","Kepala","Mata","Gigi&Mulut","THT","Thoraks","Jantung","Paru","Abdomen","Punggung","Genital&Anus","Ekstremitas","Kulit","Ket.Pemeriksaan Fisik",
            "Frekuensi Nafas","Retraksi","Sianosis","Air Entry","Merintih","Skor Frekuensi Nafas","Skor Retraksi","Skor Sianosis","Skor Air Entry","Skor Merintih","Total Downes",
            "Maturitas Kulit","Maturitas Lanugo","Maturitas Permukaan Plantar Kaki","Maturitas Payudara","Maturitas Mata/Daun Telinga","Maturitas Kelamin Laki-laki","Maturitas Kelamin Perempuan",
            "Skor Maturitas Kulit","Skor Maturitas Lanugo","Skor Maturitas Permukaan Plantar Kaki","Skor Maturitas Payudara","Skor Maturitas Mata/Daun Telinga","Skor Maturitas Kelamin Laki-laki","Skor Maturitas Kelamin Perempuan","Total Maturitas Fisik",
            "Skor Sikap","Skor Jendela Pergelangan Tangan","Skor Rikoil Lengan","Skor Sudut Popliteal","Skor Tanda Scraf","Skor Tumit Ke Telinga","Total Ballarad","Sumbu X Lubechenco","Sumbu Y Lubechenco",
            "Hasil Laboratorium","Hasil Radiologi","Hasil Penunjang Lainnya","Diagnosis/Asesmen","Tatalaksana","Edukasi"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 93; i++) {
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
                column.setPreferredWidth(75);
            }else if(i==44){
                column.setPreferredWidth(75);
            }else if(i==45){
                column.setPreferredWidth(65);
            }else if(i==46){
                column.setPreferredWidth(160);
            }else if(i==47){
                column.setPreferredWidth(50);
            }else if(i==48){
                column.setPreferredWidth(60);
            }else if(i==49){
                column.setPreferredWidth(90);
            }else if(i==50){
                column.setPreferredWidth(90);
            }else if(i==51){
                column.setPreferredWidth(65);
            }else if(i==52){
                column.setPreferredWidth(120);
            }else if(i==53){
                column.setPreferredWidth(90);
            }else if(i==54){
                column.setPreferredWidth(35);
            }else if(i==55){
                column.setPreferredWidth(40);
            }else if(i==56){
                column.setPreferredWidth(35);
            }else if(i==57){
                column.setPreferredWidth(40);
            }else if(i==58){
                column.setPreferredWidth(35);
            }else if(i==59){
                column.setPreferredWidth(35);
            }else if(i==60){
                column.setPreferredWidth(35);
            }else if(i==61){
                column.setPreferredWidth(35);
            }else if(i==62){
                column.setPreferredWidth(180);
            }else if(i==63){
                column.setPreferredWidth(150);
            }else if(i==64){
                column.setPreferredWidth(150);
            }else if(i==65){
                column.setPreferredWidth(150);
            }else if(i==66){
                column.setPreferredWidth(100);
            }else if(i==67){
                column.setPreferredWidth(60);
            }else if(i==68){
                column.setPreferredWidth(90);
            }else if(i==69){
                column.setPreferredWidth(60);
            }else if(i==70){
                column.setPreferredWidth(90);
            }else if(i==71){
                column.setPreferredWidth(60);
            }else if(i==72){
                column.setPreferredWidth(80);
            }else if(i==73){
                column.setPreferredWidth(100);
            }else if(i==74){
                column.setPreferredWidth(103);
            }else if(i==75){
                column.setPreferredWidth(87);
            }else if(i==76){
                column.setPreferredWidth(90);
            }else if(i==77){
                column.setPreferredWidth(50);
            }else if(i==78){
                column.setPreferredWidth(58);
            }else if(i==79){
                column.setPreferredWidth(90);
            }else if(i==80){
                column.setPreferredWidth(60);
            }else if(i==81){
                column.setPreferredWidth(90);
            }else if(i==82){
                column.setPreferredWidth(87);
            }else if(i==83){
                column.setPreferredWidth(87);
            }else if(i==84){
                column.setPreferredWidth(87);
            }else if(i==85){
                column.setPreferredWidth(206);
            }else if(i==86){
                column.setPreferredWidth(75);
            }else if(i==87){
                column.setPreferredWidth(75);
            }else if(i==88){
                column.setPreferredWidth(75);
            }else if(i==89){
                column.setPreferredWidth(75);
            }else if(i==90){
                column.setPreferredWidth(75);
            }else if(i==91){
                column.setPreferredWidth(75);
            }else if(i==92){
                column.setPreferredWidth(75);
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
//        tbMasalahKeperawatan.setModel(tabModeMasalah);
//
//        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
//        tbMasalahKeperawatan.setPreferredScrollableViewportSize(new Dimension(500,500));
//        tbMasalahKeperawatan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
//        
//        for (i = 0; i < 3; i++) {
//            TableColumn column = tbMasalahKeperawatan.getColumnModel().getColumn(i);
//            if(i==0){
//                column.setPreferredWidth(20);
//            }else if(i==1){
//                column.setMinWidth(0);
//                column.setMaxWidth(0);
//            }else if(i==2){
//                column.setPreferredWidth(350);
//            }
//        }
//        tbMasalahKeperawatan.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeDetailMasalah=new DefaultTableModel(null,new Object[]{
                "Kode","Masalah Keperawatan"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
//        tbMasalahDetailMasalah.setModel(tabModeDetailMasalah);
//
//        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
//        tbMasalahDetailMasalah.setPreferredScrollableViewportSize(new Dimension(500,500));
//        tbMasalahDetailMasalah.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
//
//        for (i = 0; i < 2; i++) {
//            TableColumn column = tbMasalahDetailMasalah.getColumnModel().getColumn(i);
//            if(i==0){
//                column.setMinWidth(0);
//                column.setMaxWidth(0);
//            }else if(i==1){
//                column.setPreferredWidth(420);
//            }
//        }
//        tbMasalahDetailMasalah.setDefaultRenderer(Object.class, new WarnaTable());

        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        Hubungan.setDocument(new batasInput((int)100).getKata(Hubungan));
        KeluhanUtama.setDocument(new batasInput((int)2000).getKata(KeluhanUtama));
        RPS.setDocument(new batasInput((int)2000).getKata(RPS));
        RPK.setDocument(new batasInput((int)2000).getKata(RPK));
        RPD.setDocument(new batasInput((int)1000).getKata(RPD));
        RPO.setDocument(new batasInput((int)1000).getKata(RPO));
        Alergi.setDocument(new batasInput((int)100).getKata(Alergi));
        KetKondisi.setDocument(new batasInput((byte)10).getKata(KetKondisi));
        HR.setDocument(new batasInput((byte)8).getKata(HR));
        RR.setDocument(new batasInput((byte)5).getKata(RR));
        Suhu.setDocument(new batasInput((byte)5).getKata(Suhu));
        SPO.setDocument(new batasInput((byte)5).getKata(SPO));
        BBL.setDocument(new batasInput((byte)5).getKata(BBL));
        PB.setDocument(new batasInput((byte)5).getKata(PB));
        LK.setDocument(new batasInput((byte)5).getKata(LK));
        LD.setDocument(new batasInput((byte)5).getKata(LD));
        LP.setDocument(new batasInput((byte)5).getKata(LP));
        Diagnosis.setDocument(new batasInput((int)500).getKata(Diagnosis));
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
        
        dokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(dokter.getTable().getSelectedRow()!= -1){
                    KdPetugas.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                    NmPetugas.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                    KdPetugas.requestFocus();
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
        
//        BB.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
//            @Override
//            public void insertUpdate(DocumentEvent e) {
//                isBMI();
//            }
//            @Override
//            public void removeUpdate(DocumentEvent e) {
//                isBMI();
//            }
//            @Override
//            public void changedUpdate(DocumentEvent e) {
//                isBMI();
//            }
//        });
//        
//        TB.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
//            @Override
//            public void insertUpdate(DocumentEvent e) {
//                isBMI();
//            }
//            @Override
//            public void removeUpdate(DocumentEvent e) {
//                isBMI();
//            }
//            @Override
//            public void changedUpdate(DocumentEvent e) {
//                isBMI();
//            }
//        });
        
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
        
        
//        ChkAccor.setSelected(false);
//        isMenu();
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
        jLabel9 = new widget.Label();
        Jk = new widget.TextBox();
        jLabel10 = new widget.Label();
        jLabel11 = new widget.Label();
        jLabel12 = new widget.Label();
        BBL = new widget.TextBox();
        PB = new widget.TextBox();
        jLabel15 = new widget.Label();
        jLabel18 = new widget.Label();
        Suhu = new widget.TextBox();
        jLabel22 = new widget.Label();
        HR = new widget.TextBox();
        jLabel20 = new widget.Label();
        jLabel23 = new widget.Label();
        jLabel24 = new widget.Label();
        jLabel25 = new widget.Label();
        RR = new widget.TextBox();
        jLabel26 = new widget.Label();
        jLabel36 = new widget.Label();
        jLabel37 = new widget.Label();
        Alergi = new widget.TextBox();
        Anamnesis = new widget.ComboBox();
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
        scrollPane5 = new widget.ScrollPane();
        KetFisik = new widget.TextArea();
        KetKondisi = new widget.TextBox();
        jLabel94 = new widget.Label();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel38 = new widget.Label();
        Hubungan = new widget.TextBox();
        jSeparator11 = new javax.swing.JSeparator();
        jLabel33 = new widget.Label();
        scrollPane7 = new widget.ScrollPane();
        RPS = new widget.TextArea();
        jSeparator12 = new javax.swing.JSeparator();
        Kondisi = new widget.ComboBox();
        jLabel40 = new widget.Label();
        jLabel41 = new widget.Label();
        jLabel29 = new widget.Label();
        SPO = new widget.TextBox();
        jLabel35 = new widget.Label();
        jLabel42 = new widget.Label();
        Kepala = new widget.ComboBox();
        jLabel43 = new widget.Label();
        Mata = new widget.ComboBox();
        jLabel44 = new widget.Label();
        Gigi = new widget.ComboBox();
        jLabel45 = new widget.Label();
        THT = new widget.ComboBox();
        jLabel46 = new widget.Label();
        Thoraks = new widget.ComboBox();
        jLabel47 = new widget.Label();
        Jantung = new widget.ComboBox();
        jLabel48 = new widget.Label();
        Paru = new widget.ComboBox();
        jLabel49 = new widget.Label();
        Abdomen = new widget.ComboBox();
        jLabel50 = new widget.Label();
        Genital = new widget.ComboBox();
        jLabel51 = new widget.Label();
        Ekstremitas = new widget.ComboBox();
        jLabel52 = new widget.Label();
        Kulit = new widget.ComboBox();
        jLabel99 = new widget.Label();
        jSeparator14 = new javax.swing.JSeparator();
        scrollPane9 = new widget.ScrollPane();
        Laboratorium = new widget.TextArea();
        jLabel80 = new widget.Label();
        scrollPane10 = new widget.ScrollPane();
        Radiologi = new widget.TextArea();
        jLabel81 = new widget.Label();
        scrollPane11 = new widget.ScrollPane();
        Penunjang = new widget.TextArea();
        jLabel82 = new widget.Label();
        jSeparator15 = new javax.swing.JSeparator();
        jLabel101 = new widget.Label();
        scrollPane12 = new widget.ScrollPane();
        Diagnosis = new widget.TextArea();
        jSeparator16 = new javax.swing.JSeparator();
        jLabel102 = new widget.Label();
        scrollPane13 = new widget.ScrollPane();
        Tata = new widget.TextArea();
        jLabel103 = new widget.Label();
        scrollPane14 = new widget.ScrollPane();
        Edukasi = new widget.TextArea();
        jLabel83 = new widget.Label();
        PanelWall1 = new usu.widget.glass.PanelGlass();
        jLabel54 = new widget.Label();
        jLabel61 = new widget.Label();
        jLabel68 = new widget.Label();
        jLabel74 = new widget.Label();
        jLabel78 = new widget.Label();
        jLabel84 = new widget.Label();
        Warna = new widget.ComboBox();
        Denyut = new widget.ComboBox();
        Reflek = new widget.ComboBox();
        Tonus = new widget.ComboBox();
        Pernafasan = new widget.ComboBox();
        jLabel85 = new widget.Label();
        jLabel86 = new widget.Label();
        TotalFisik = new widget.TextBox();
        jLabel53 = new widget.Label();
        jLabel27 = new widget.Label();
        jLabel16 = new widget.Label();
        LK = new widget.TextBox();
        jLabel28 = new widget.Label();
        jLabel17 = new widget.Label();
        LD = new widget.TextBox();
        jLabel34 = new widget.Label();
        jLabel39 = new widget.Label();
        LP = new widget.TextBox();
        jLabel55 = new widget.Label();
        jSeparator17 = new javax.swing.JSeparator();
        jLabel95 = new widget.Label();
        jLabel62 = new widget.Label();
        jLabel69 = new widget.Label();
        jLabel75 = new widget.Label();
        jLabel87 = new widget.Label();
        jLabel88 = new widget.Label();
        Frekuensi = new widget.ComboBox();
        Retraksi = new widget.ComboBox();
        Sianosis = new widget.ComboBox();
        Air = new widget.ComboBox();
        Merintih = new widget.ComboBox();
        jLabel89 = new widget.Label();
        jLabel90 = new widget.Label();
        TotalDownes = new widget.TextBox();
        SFrekuensi = new widget.TextBox();
        SRetraksi = new widget.TextBox();
        SSianosis = new widget.TextBox();
        SAir = new widget.TextBox();
        SMerintih = new widget.TextBox();
        jLabel91 = new widget.Label();
        jLabel92 = new widget.Label();
        jLabel96 = new widget.Label();
        jSeparator18 = new javax.swing.JSeparator();
        jLabel63 = new widget.Label();
        jLabel70 = new widget.Label();
        jLabel76 = new widget.Label();
        jLabel93 = new widget.Label();
        MKulit = new widget.ComboBox();
        Lanugo = new widget.ComboBox();
        Permukaan = new widget.ComboBox();
        Payudara = new widget.ComboBox();
        MMata = new widget.ComboBox();
        jLabel98 = new widget.Label();
        jLabel104 = new widget.Label();
        TotalMasturitas = new widget.TextBox();
        jLabel105 = new widget.Label();
        jLabel107 = new widget.Label();
        jLabel106 = new widget.Label();
        Laki = new widget.ComboBox();
        jLabel108 = new widget.Label();
        Perempuan = new widget.ComboBox();
        PanelWall2 = new usu.widget.glass.PanelGlass();
        jLabel117 = new widget.Label();
        TotalBallarad = new widget.TextBox();
        Sikap = new widget.ComboBox();
        jLabel109 = new widget.Label();
        jLabel111 = new widget.Label();
        Jendela = new widget.ComboBox();
        jLabel112 = new widget.Label();
        Rikoil = new widget.ComboBox();
        jLabel113 = new widget.Label();
        Sudut = new widget.ComboBox();
        jLabel110 = new widget.Label();
        Tanda = new widget.ComboBox();
        Tumit = new widget.ComboBox();
        jLabel115 = new widget.Label();
        PanelWall3 = new usu.widget.glass.PanelGlass();
        jLabel116 = new widget.Label();
        jLabel118 = new widget.Label();
        jLabel119 = new widget.Label();
        jLabel120 = new widget.Label();
        jLabel121 = new widget.Label();
        jLabel122 = new widget.Label();
        jLabel123 = new widget.Label();
        jLabel124 = new widget.Label();
        jLabel125 = new widget.Label();
        jLabel126 = new widget.Label();
        SumbuX = new widget.ComboBox();
        jLabel127 = new widget.Label();
        jLabel128 = new widget.Label();
        SumbuY = new widget.ComboBox();
        SWarna = new widget.ComboBox();
        SPernafasan = new widget.ComboBox();
        SDenyut = new widget.ComboBox();
        SReflek = new widget.ComboBox();
        STonus = new widget.ComboBox();
        SPerempuan = new widget.ComboBox();
        SKulit = new widget.ComboBox();
        SLanugo = new widget.ComboBox();
        SPermukaan = new widget.ComboBox();
        SPayudara = new widget.ComboBox();
        SMata = new widget.ComboBox();
        SLaki = new widget.ComboBox();
        jLabel56 = new widget.Label();
        Punggung = new widget.ComboBox();
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

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Asesmen Awal Medis Rawat Inap Neonatus ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 10), new java.awt.Color(50, 50, 50))); // NOI18N
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
        FormInput.setPreferredSize(new java.awt.Dimension(870, 3550));
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
        TPasien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TPasienActionPerformed(evt);
            }
        });
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

        KdPetugas.setEditable(false);
        KdPetugas.setName("KdPetugas"); // NOI18N
        KdPetugas.setPreferredSize(new java.awt.Dimension(80, 23));
        KdPetugas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdPetugasKeyPressed(evt);
            }
        });
        FormInput.add(KdPetugas);
        KdPetugas.setBounds(74, 40, 140, 23);

        NmPetugas.setEditable(false);
        NmPetugas.setName("NmPetugas"); // NOI18N
        NmPetugas.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmPetugas);
        NmPetugas.setBounds(215, 40, 220, 23);

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
        BtnDokter.setBounds(430, 40, 28, 23);

        jLabel8.setText("Tgl.Lahir :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(580, 10, 60, 23);

        TglLahir.setEditable(false);
        TglLahir.setHighlighter(null);
        TglLahir.setName("TglLahir"); // NOI18N
        FormInput.add(TglLahir);
        TglLahir.setBounds(644, 10, 80, 23);

        jLabel9.setText("Riwayat Pengunaan obat :");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(20, 300, 150, 23);

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

        jLabel12.setText("BBL :");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(110, 650, 30, 23);

        BBL.setFocusTraversalPolicyProvider(true);
        BBL.setName("BBL"); // NOI18N
        BBL.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BBLKeyPressed(evt);
            }
        });
        FormInput.add(BBL);
        BBL.setBounds(140, 650, 60, 23);

        PB.setFocusTraversalPolicyProvider(true);
        PB.setName("PB"); // NOI18N
        PB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PBKeyPressed(evt);
            }
        });
        FormInput.add(PB);
        PB.setBounds(260, 650, 60, 23);

        jLabel15.setText("PB :");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(220, 650, 40, 23);

        jLabel18.setText("Suhu :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(370, 620, 40, 23);

        Suhu.setFocusTraversalPolicyProvider(true);
        Suhu.setName("Suhu"); // NOI18N
        Suhu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SuhuKeyPressed(evt);
            }
        });
        FormInput.add(Suhu);
        Suhu.setBounds(410, 620, 60, 23);

        jLabel22.setText("HR :");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(110, 620, 30, 23);

        HR.setFocusTraversalPolicyProvider(true);
        HR.setName("HR"); // NOI18N
        HR.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HRKeyPressed(evt);
            }
        });
        FormInput.add(HR);
        HR.setBounds(140, 620, 60, 23);

        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel20.setText("°C");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(470, 620, 30, 23);

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel23.setText("x/m");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(200, 620, 50, 23);

        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel24.setText("gr");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(200, 650, 30, 23);

        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel25.setText("x/menit");
        jLabel25.setName("jLabel25"); // NOI18N
        FormInput.add(jLabel25);
        jLabel25.setBounds(330, 620, 50, 23);

        RR.setFocusTraversalPolicyProvider(true);
        RR.setName("RR"); // NOI18N
        RR.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RRKeyPressed(evt);
            }
        });
        FormInput.add(RR);
        RR.setBounds(260, 620, 60, 23);

        jLabel26.setText("RR :");
        jLabel26.setName("jLabel26"); // NOI18N
        FormInput.add(jLabel26);
        jLabel26.setBounds(220, 620, 40, 23);

        jLabel36.setText("Hubungan :");
        jLabel36.setName("jLabel36"); // NOI18N
        FormInput.add(jLabel36);
        jLabel36.setBounds(220, 80, 60, 23);

        jLabel37.setText("Riwayat Alergi :");
        jLabel37.setName("jLabel37"); // NOI18N
        FormInput.add(jLabel37);
        jLabel37.setBounds(440, 300, 170, 23);

        Alergi.setFocusTraversalPolicyProvider(true);
        Alergi.setName("Alergi"); // NOI18N
        Alergi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlergiKeyPressed(evt);
            }
        });
        FormInput.add(Alergi);
        Alergi.setBounds(620, 300, 260, 23);

        Anamnesis.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Autoanamnesis", "Alloanamnesis" }));
        Anamnesis.setName("Anamnesis"); // NOI18N
        Anamnesis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AnamnesisKeyPressed(evt);
            }
        });
        FormInput.add(Anamnesis);
        Anamnesis.setBounds(80, 80, 128, 23);

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
        scrollPane1.setBounds(180, 140, 260, 100);

        jLabel30.setText("Riwayat Penyakit Sekarang :");
        jLabel30.setName("jLabel30"); // NOI18N
        FormInput.add(jLabel30);
        jLabel30.setBounds(440, 140, 170, 20);

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
        scrollPane2.setBounds(620, 250, 260, 43);

        jLabel31.setText("Riwayat Penyakit Dahulu :");
        jLabel31.setName("jLabel31"); // NOI18N
        FormInput.add(jLabel31);
        jLabel31.setBounds(460, 250, 150, 23);

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
        scrollPane3.setBounds(180, 250, 260, 42);

        jLabel32.setText("Riwayat Penyakit Keluarga :");
        jLabel32.setName("jLabel32"); // NOI18N
        FormInput.add(jLabel32);
        jLabel32.setBounds(20, 250, 150, 23);

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
        scrollPane4.setBounds(180, 300, 260, 42);

        scrollPane5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane5.setName("scrollPane5"); // NOI18N

        KetFisik.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        KetFisik.setColumns(20);
        KetFisik.setRows(5);
        KetFisik.setName("KetFisik"); // NOI18N
        KetFisik.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetFisikKeyPressed(evt);
            }
        });
        scrollPane5.setViewportView(KetFisik);

        FormInput.add(scrollPane5);
        scrollPane5.setBounds(250, 690, 400, 350);

        KetKondisi.setFocusTraversalPolicyProvider(true);
        KetKondisi.setName("KetKondisi"); // NOI18N
        KetKondisi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetKondisiKeyPressed(evt);
            }
        });
        FormInput.add(KetKondisi);
        KetKondisi.setBounds(390, 380, 100, 23);

        jLabel94.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel94.setText("III. PENILAIAN SKOR DOWNES");
        jLabel94.setName("jLabel94"); // NOI18N
        FormInput.add(jLabel94);
        jLabel94.setBounds(30, 1060, 180, 23);

        jSeparator1.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator1.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator1.setName("jSeparator1"); // NOI18N
        FormInput.add(jSeparator1);
        jSeparator1.setBounds(0, 70, 880, 1);

        jLabel38.setText("Anamnesis :");
        jLabel38.setName("jLabel38"); // NOI18N
        FormInput.add(jLabel38);
        jLabel38.setBounds(0, 80, 70, 23);

        Hubungan.setName("Hubungan"); // NOI18N
        Hubungan.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(Hubungan);
        Hubungan.setBounds(290, 80, 310, 23);

        jSeparator11.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator11.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator11.setName("jSeparator11"); // NOI18N
        FormInput.add(jSeparator11);
        jSeparator11.setBounds(0, 110, 880, 1);

        jLabel33.setText("Keluhan Utama :");
        jLabel33.setName("jLabel33"); // NOI18N
        FormInput.add(jLabel33);
        jLabel33.setBounds(0, 140, 175, 20);

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
        scrollPane7.setBounds(620, 140, 260, 100);

        jSeparator12.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator12.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator12.setName("jSeparator12"); // NOI18N
        FormInput.add(jSeparator12);
        jSeparator12.setBounds(0, 350, 880, 1);

        Kondisi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Segera Menangis", "Tidak Segera Menangis", "APGAR" }));
        Kondisi.setName("Kondisi"); // NOI18N
        Kondisi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KondisiKeyPressed(evt);
            }
        });
        FormInput.add(Kondisi);
        Kondisi.setBounds(200, 380, 180, 23);

        jLabel40.setText("Kepala :");
        jLabel40.setName("jLabel40"); // NOI18N
        FormInput.add(jLabel40);
        jLabel40.setBounds(0, 690, 100, 23);

        jLabel41.setText("Kondisi Saat Lahir :");
        jLabel41.setName("jLabel41"); // NOI18N
        FormInput.add(jLabel41);
        jLabel41.setBounds(100, 380, 100, 23);

        jLabel29.setText("SpO2 :");
        jLabel29.setName("jLabel29"); // NOI18N
        FormInput.add(jLabel29);
        jLabel29.setBounds(490, 620, 40, 23);

        SPO.setFocusTraversalPolicyProvider(true);
        SPO.setName("SPO"); // NOI18N
        SPO.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SPOKeyPressed(evt);
            }
        });
        FormInput.add(SPO);
        SPO.setBounds(530, 620, 60, 23);

        jLabel35.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel35.setText("%");
        jLabel35.setName("jLabel35"); // NOI18N
        FormInput.add(jLabel35);
        jLabel35.setBounds(590, 620, 30, 23);

        jLabel42.setText("Antropometri :");
        jLabel42.setName("jLabel42"); // NOI18N
        FormInput.add(jLabel42);
        jLabel42.setBounds(0, 650, 100, 23);

        Kepala.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Kepala.setName("Kepala"); // NOI18N
        Kepala.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KepalaKeyPressed(evt);
            }
        });
        FormInput.add(Kepala);
        Kepala.setBounds(110, 690, 128, 23);

        jLabel43.setText("Mata :");
        jLabel43.setName("jLabel43"); // NOI18N
        FormInput.add(jLabel43);
        jLabel43.setBounds(0, 720, 100, 23);

        Mata.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Mata.setName("Mata"); // NOI18N
        Mata.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MataKeyPressed(evt);
            }
        });
        FormInput.add(Mata);
        Mata.setBounds(110, 720, 128, 23);

        jLabel44.setText("Gigi & Mulut :");
        jLabel44.setName("jLabel44"); // NOI18N
        FormInput.add(jLabel44);
        jLabel44.setBounds(0, 750, 100, 23);

        Gigi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Gigi.setName("Gigi"); // NOI18N
        Gigi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GigiKeyPressed(evt);
            }
        });
        FormInput.add(Gigi);
        Gigi.setBounds(110, 750, 128, 23);

        jLabel45.setText("THT :");
        jLabel45.setName("jLabel45"); // NOI18N
        FormInput.add(jLabel45);
        jLabel45.setBounds(0, 780, 100, 23);

        THT.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        THT.setName("THT"); // NOI18N
        THT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                THTKeyPressed(evt);
            }
        });
        FormInput.add(THT);
        THT.setBounds(110, 780, 128, 23);

        jLabel46.setText("Thoraks :");
        jLabel46.setName("jLabel46"); // NOI18N
        FormInput.add(jLabel46);
        jLabel46.setBounds(0, 810, 100, 23);

        Thoraks.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Thoraks.setName("Thoraks"); // NOI18N
        Thoraks.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ThoraksKeyPressed(evt);
            }
        });
        FormInput.add(Thoraks);
        Thoraks.setBounds(110, 810, 128, 23);

        jLabel47.setText("Jantung :");
        jLabel47.setName("jLabel47"); // NOI18N
        FormInput.add(jLabel47);
        jLabel47.setBounds(0, 840, 100, 23);

        Jantung.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Jantung.setName("Jantung"); // NOI18N
        Jantung.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JantungKeyPressed(evt);
            }
        });
        FormInput.add(Jantung);
        Jantung.setBounds(110, 840, 128, 23);

        jLabel48.setText("Paru :");
        jLabel48.setName("jLabel48"); // NOI18N
        FormInput.add(jLabel48);
        jLabel48.setBounds(0, 870, 100, 23);

        Paru.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Paru.setName("Paru"); // NOI18N
        Paru.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ParuKeyPressed(evt);
            }
        });
        FormInput.add(Paru);
        Paru.setBounds(110, 870, 128, 23);

        jLabel49.setText("Abdomen :");
        jLabel49.setName("jLabel49"); // NOI18N
        FormInput.add(jLabel49);
        jLabel49.setBounds(0, 900, 100, 23);

        Abdomen.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Abdomen.setName("Abdomen"); // NOI18N
        Abdomen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AbdomenKeyPressed(evt);
            }
        });
        FormInput.add(Abdomen);
        Abdomen.setBounds(110, 900, 128, 23);

        jLabel50.setText("Genital & Anus :");
        jLabel50.setName("jLabel50"); // NOI18N
        FormInput.add(jLabel50);
        jLabel50.setBounds(0, 960, 100, 23);

        Genital.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Genital.setName("Genital"); // NOI18N
        Genital.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GenitalKeyPressed(evt);
            }
        });
        FormInput.add(Genital);
        Genital.setBounds(110, 960, 128, 23);

        jLabel51.setText("Ekstremitas :");
        jLabel51.setName("jLabel51"); // NOI18N
        FormInput.add(jLabel51);
        jLabel51.setBounds(0, 990, 100, 23);

        Ekstremitas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Ekstremitas.setName("Ekstremitas"); // NOI18N
        Ekstremitas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EkstremitasKeyPressed(evt);
            }
        });
        FormInput.add(Ekstremitas);
        Ekstremitas.setBounds(110, 990, 128, 23);

        jLabel52.setText("Kulit :");
        jLabel52.setName("jLabel52"); // NOI18N
        FormInput.add(jLabel52);
        jLabel52.setBounds(0, 1020, 100, 23);

        Kulit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Kulit.setName("Kulit"); // NOI18N
        Kulit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KulitKeyPressed(evt);
            }
        });
        FormInput.add(Kulit);
        Kulit.setBounds(110, 1020, 128, 23);

        jLabel99.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel99.setText("I. RIWAYAT KESEHATAN");
        jLabel99.setName("jLabel99"); // NOI18N
        FormInput.add(jLabel99);
        jLabel99.setBounds(30, 110, 180, 23);

        jSeparator14.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator14.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator14.setName("jSeparator14"); // NOI18N
        FormInput.add(jSeparator14);
        jSeparator14.setBounds(0, 3080, 880, 1);

        scrollPane9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane9.setName("scrollPane9"); // NOI18N

        Laboratorium.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Laboratorium.setColumns(20);
        Laboratorium.setRows(5);
        Laboratorium.setName("Laboratorium"); // NOI18N
        Laboratorium.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LaboratoriumKeyPressed(evt);
            }
        });
        scrollPane9.setViewportView(Laboratorium);

        FormInput.add(scrollPane9);
        scrollPane9.setBounds(30, 3130, 250, 100);

        jLabel80.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel80.setText("Laboratorium :");
        jLabel80.setName("jLabel80"); // NOI18N
        FormInput.add(jLabel80);
        jLabel80.setBounds(30, 3110, 120, 23);

        scrollPane10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane10.setName("scrollPane10"); // NOI18N

        Radiologi.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Radiologi.setColumns(20);
        Radiologi.setRows(5);
        Radiologi.setName("Radiologi"); // NOI18N
        Radiologi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RadiologiKeyPressed(evt);
            }
        });
        scrollPane10.setViewportView(Radiologi);

        FormInput.add(scrollPane10);
        scrollPane10.setBounds(300, 3130, 250, 100);

        jLabel81.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel81.setText("Radiologi :");
        jLabel81.setName("jLabel81"); // NOI18N
        FormInput.add(jLabel81);
        jLabel81.setBounds(300, 3110, 120, 23);

        scrollPane11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane11.setName("scrollPane11"); // NOI18N

        Penunjang.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Penunjang.setColumns(20);
        Penunjang.setRows(5);
        Penunjang.setName("Penunjang"); // NOI18N
        Penunjang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenunjangKeyPressed(evt);
            }
        });
        scrollPane11.setViewportView(Penunjang);

        FormInput.add(scrollPane11);
        scrollPane11.setBounds(570, 3130, 250, 100);

        jLabel82.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel82.setText("Penunjang Lainnya :");
        jLabel82.setName("jLabel82"); // NOI18N
        FormInput.add(jLabel82);
        jLabel82.setBounds(570, 3110, 120, 23);

        jSeparator15.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator15.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator15.setName("jSeparator15"); // NOI18N
        FormInput.add(jSeparator15);
        jSeparator15.setBounds(0, 3250, 880, 1);

        jLabel101.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel101.setText("V. HASIL PEMERIKSAAN PENUNJANG");
        jLabel101.setName("jLabel101"); // NOI18N
        FormInput.add(jLabel101);
        jLabel101.setBounds(30, 3090, 190, 23);

        scrollPane12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane12.setName("scrollPane12"); // NOI18N

        Diagnosis.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Diagnosis.setColumns(20);
        Diagnosis.setRows(5);
        Diagnosis.setName("Diagnosis"); // NOI18N
        Diagnosis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosisKeyPressed(evt);
            }
        });
        scrollPane12.setViewportView(Diagnosis);

        FormInput.add(scrollPane12);
        scrollPane12.setBounds(30, 3290, 260, 43);

        jSeparator16.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator16.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator16.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator16.setName("jSeparator16"); // NOI18N
        FormInput.add(jSeparator16);
        jSeparator16.setBounds(0, 3350, 880, 1);

        jLabel102.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel102.setText("VI. DIAGNOSIS / ASESMEN");
        jLabel102.setName("jLabel102"); // NOI18N
        FormInput.add(jLabel102);
        jLabel102.setBounds(30, 3260, 190, 23);

        scrollPane13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane13.setName("scrollPane13"); // NOI18N

        Tata.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Tata.setColumns(20);
        Tata.setRows(5);
        Tata.setName("Tata"); // NOI18N
        Tata.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TataKeyPressed(evt);
            }
        });
        scrollPane13.setViewportView(Tata);

        FormInput.add(scrollPane13);
        scrollPane13.setBounds(40, 3390, 360, 143);

        jLabel103.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel103.setText("VII. TATALAKSANA");
        jLabel103.setName("jLabel103"); // NOI18N
        FormInput.add(jLabel103);
        jLabel103.setBounds(30, 3360, 190, 23);

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
        scrollPane14.setBounds(440, 3390, 370, 143);

        jLabel83.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel83.setText("Edukasi :");
        jLabel83.setName("jLabel83"); // NOI18N
        FormInput.add(jLabel83);
        jLabel83.setBounds(440, 3370, 120, 23);

        PanelWall1.setBackground(new java.awt.Color(29, 29, 29));
        PanelWall1.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/picture/nilai_maturitas.png"))); // NOI18N
        PanelWall1.setBackgroundImageType(usu.widget.constan.BackgroundConstan.BACKGROUND_IMAGE_STRECT);
        PanelWall1.setPreferredSize(new java.awt.Dimension(200, 200));
        PanelWall1.setRound(false);
        PanelWall1.setWarna(new java.awt.Color(110, 110, 110));
        PanelWall1.setLayout(null);
        FormInput.add(PanelWall1);
        PanelWall1.setBounds(20, 2010, 240, 340);

        jLabel54.setText("Keadaan Umum :");
        jLabel54.setName("jLabel54"); // NOI18N
        FormInput.add(jLabel54);
        jLabel54.setBounds(0, 380, 100, 23);

        jLabel61.setText("Denyut Jantung :");
        jLabel61.setName("jLabel61"); // NOI18N
        FormInput.add(jLabel61);
        jLabel61.setBounds(0, 460, 100, 23);

        jLabel68.setText("Reflek Gerak :");
        jLabel68.setName("jLabel68"); // NOI18N
        FormInput.add(jLabel68);
        jLabel68.setBounds(0, 490, 100, 23);

        jLabel74.setText("Tonus Otot :");
        jLabel74.setName("jLabel74"); // NOI18N
        FormInput.add(jLabel74);
        jLabel74.setBounds(0, 520, 100, 23);

        jLabel78.setText("Total");
        jLabel78.setName("jLabel78"); // NOI18N
        FormInput.add(jLabel78);
        jLabel78.setBounds(460, 580, 50, 23);

        jLabel84.setText("Pernafasan :");
        jLabel84.setName("jLabel84"); // NOI18N
        FormInput.add(jLabel84);
        jLabel84.setBounds(0, 550, 100, 23);

        Warna.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Warna Kulit pink pada tubuh dan ekstrimitas", "Warna Kulit biru pada ekstrimitas warna kulit pink pada tubuh", "Warna kulit seluruh tubuh dan ekstrimitas biru" }));
        Warna.setName("Warna"); // NOI18N
        Warna.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                WarnaItemStateChanged(evt);
            }
        });
        Warna.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                WarnaKeyPressed(evt);
            }
        });
        FormInput.add(Warna);
        Warna.setBounds(110, 430, 400, 23);

        Denyut.setModel(new javax.swing.DefaultComboBoxModel(new String[] { ">100 kali/menit", "<100 kali/menit", "Tidak ada denyut nadi" }));
        Denyut.setName("Denyut"); // NOI18N
        Denyut.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                DenyutItemStateChanged(evt);
            }
        });
        Denyut.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DenyutKeyPressed(evt);
            }
        });
        FormInput.add(Denyut);
        Denyut.setBounds(110, 460, 400, 23);

        Reflek.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Bayi menangis batuk atau bersin", "Meringis atau menangis lemah saat distimulasi", "Tidak ada respon terhadap stimulasi" }));
        Reflek.setName("Reflek"); // NOI18N
        Reflek.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ReflekItemStateChanged(evt);
            }
        });
        Reflek.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ReflekKeyPressed(evt);
            }
        });
        FormInput.add(Reflek);
        Reflek.setBounds(110, 490, 400, 23);

        Tonus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Bergerak aktif", "Sedikit gerakan", "Lemah atau tidak ada gerakan" }));
        Tonus.setName("Tonus"); // NOI18N
        Tonus.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TonusItemStateChanged(evt);
            }
        });
        Tonus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TonusKeyPressed(evt);
            }
        });
        FormInput.add(Tonus);
        Tonus.setBounds(110, 520, 400, 23);

        Pernafasan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Pernafasan baik dan teratur menangis kuat", "Pernafasan lemah tidak teratur", "Tidak ada nafas" }));
        Pernafasan.setName("Pernafasan"); // NOI18N
        Pernafasan.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                PernafasanItemStateChanged(evt);
            }
        });
        Pernafasan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PernafasanKeyPressed(evt);
            }
        });
        FormInput.add(Pernafasan);
        Pernafasan.setBounds(110, 550, 400, 23);

        jLabel85.setText("Warna Kulit :");
        jLabel85.setName("jLabel85"); // NOI18N
        FormInput.add(jLabel85);
        jLabel85.setBounds(0, 430, 100, 23);

        jLabel86.setText("Skor");
        jLabel86.setName("jLabel86"); // NOI18N
        FormInput.add(jLabel86);
        jLabel86.setBounds(510, 400, 50, 23);

        TotalFisik.setEditable(false);
        TotalFisik.setText("10");
        TotalFisik.setFocusTraversalPolicyProvider(true);
        TotalFisik.setName("TotalFisik"); // NOI18N
        TotalFisik.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TotalFisikKeyPressed(evt);
            }
        });
        FormInput.add(TotalFisik);
        TotalFisik.setBounds(520, 580, 60, 23);

        jLabel53.setText("Tanda Vital :");
        jLabel53.setName("jLabel53"); // NOI18N
        FormInput.add(jLabel53);
        jLabel53.setBounds(0, 620, 100, 23);

        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel27.setText(" cm");
        jLabel27.setName("jLabel27"); // NOI18N
        FormInput.add(jLabel27);
        jLabel27.setBounds(320, 650, 30, 23);

        jLabel16.setText("LK :");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput.add(jLabel16);
        jLabel16.setBounds(340, 650, 40, 23);

        LK.setFocusTraversalPolicyProvider(true);
        LK.setName("LK"); // NOI18N
        LK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LKKeyPressed(evt);
            }
        });
        FormInput.add(LK);
        LK.setBounds(380, 650, 60, 23);

        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel28.setText(" cm");
        jLabel28.setName("jLabel28"); // NOI18N
        FormInput.add(jLabel28);
        jLabel28.setBounds(440, 650, 30, 23);

        jLabel17.setText("LD :");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(460, 650, 40, 23);

        LD.setFocusTraversalPolicyProvider(true);
        LD.setName("LD"); // NOI18N
        LD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LDKeyPressed(evt);
            }
        });
        FormInput.add(LD);
        LD.setBounds(500, 650, 60, 23);

        jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel34.setText(" cm");
        jLabel34.setName("jLabel34"); // NOI18N
        FormInput.add(jLabel34);
        jLabel34.setBounds(560, 650, 30, 23);

        jLabel39.setText("LP :");
        jLabel39.setName("jLabel39"); // NOI18N
        FormInput.add(jLabel39);
        jLabel39.setBounds(580, 650, 40, 23);

        LP.setFocusTraversalPolicyProvider(true);
        LP.setName("LP"); // NOI18N
        LP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LPKeyPressed(evt);
            }
        });
        FormInput.add(LP);
        LP.setBounds(620, 650, 60, 23);

        jLabel55.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel55.setText(" cm");
        jLabel55.setName("jLabel55"); // NOI18N
        FormInput.add(jLabel55);
        jLabel55.setBounds(680, 650, 30, 23);

        jSeparator17.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator17.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator17.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator17.setName("jSeparator17"); // NOI18N
        FormInput.add(jSeparator17);
        jSeparator17.setBounds(0, 1050, 880, 1);

        jLabel95.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel95.setText("II. PEMERIKSAAN FISIK");
        jLabel95.setName("jLabel95"); // NOI18N
        FormInput.add(jLabel95);
        jLabel95.setBounds(30, 350, 180, 23);

        jLabel62.setText("Retraksi :");
        jLabel62.setName("jLabel62"); // NOI18N
        FormInput.add(jLabel62);
        jLabel62.setBounds(0, 1150, 100, 23);

        jLabel69.setText("Sianosis :");
        jLabel69.setName("jLabel69"); // NOI18N
        FormInput.add(jLabel69);
        jLabel69.setBounds(0, 1180, 100, 23);

        jLabel75.setText("Air Entry :");
        jLabel75.setName("jLabel75"); // NOI18N
        FormInput.add(jLabel75);
        jLabel75.setBounds(0, 1210, 100, 23);

        jLabel87.setText("Total");
        jLabel87.setName("jLabel87"); // NOI18N
        FormInput.add(jLabel87);
        jLabel87.setBounds(460, 1270, 50, 23);

        jLabel88.setText("Skor <4 = Tidak ada gawat nafas, Skor 4-7= Gawat nafas, Skor >7= Ancaman gagal nafas");
        jLabel88.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel88.setName("jLabel88"); // NOI18N
        FormInput.add(jLabel88);
        jLabel88.setBounds(100, 1300, 500, 23);

        Frekuensi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "<60 x/menit", "60-80 menit", ">80 x/menit" }));
        Frekuensi.setName("Frekuensi"); // NOI18N
        Frekuensi.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                FrekuensiItemStateChanged(evt);
            }
        });
        Frekuensi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FrekuensiKeyPressed(evt);
            }
        });
        FormInput.add(Frekuensi);
        Frekuensi.setBounds(110, 1120, 400, 23);

        Retraksi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada", "Retraksi ringan", "Retraksi berat" }));
        Retraksi.setName("Retraksi"); // NOI18N
        Retraksi.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                RetraksiItemStateChanged(evt);
            }
        });
        Retraksi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RetraksiKeyPressed(evt);
            }
        });
        FormInput.add(Retraksi);
        Retraksi.setBounds(110, 1150, 400, 23);

        Sianosis.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak sianosis", "Sianosis hilang dengan o2", "Sianosis menetap walaupun diberi o2" }));
        Sianosis.setName("Sianosis"); // NOI18N
        Sianosis.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SianosisItemStateChanged(evt);
            }
        });
        Sianosis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SianosisKeyPressed(evt);
            }
        });
        FormInput.add(Sianosis);
        Sianosis.setBounds(110, 1180, 400, 23);

        Air.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Udara masuk", "Penurunan ringan udara masuk", "Tidak ada udara masuk" }));
        Air.setName("Air"); // NOI18N
        Air.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                AirItemStateChanged(evt);
            }
        });
        Air.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AirKeyPressed(evt);
            }
        });
        FormInput.add(Air);
        Air.setBounds(110, 1210, 400, 23);

        Merintih.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Dapat di dengar dengan stetoskop", "Dapat di dengar tanpa alat bantu" }));
        Merintih.setName("Merintih"); // NOI18N
        Merintih.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                MerintihItemStateChanged(evt);
            }
        });
        Merintih.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MerintihKeyPressed(evt);
            }
        });
        FormInput.add(Merintih);
        Merintih.setBounds(110, 1240, 400, 23);

        jLabel89.setText("Frekuensi Nafas :");
        jLabel89.setName("jLabel89"); // NOI18N
        FormInput.add(jLabel89);
        jLabel89.setBounds(0, 1120, 100, 23);

        jLabel90.setText("Skor");
        jLabel90.setName("jLabel90"); // NOI18N
        FormInput.add(jLabel90);
        jLabel90.setBounds(510, 1090, 50, 23);

        TotalDownes.setEditable(false);
        TotalDownes.setText("0");
        TotalDownes.setFocusTraversalPolicyProvider(true);
        TotalDownes.setName("TotalDownes"); // NOI18N
        TotalDownes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TotalDownesKeyPressed(evt);
            }
        });
        FormInput.add(TotalDownes);
        TotalDownes.setBounds(520, 1270, 60, 23);

        SFrekuensi.setEditable(false);
        SFrekuensi.setText("0");
        SFrekuensi.setFocusTraversalPolicyProvider(true);
        SFrekuensi.setName("SFrekuensi"); // NOI18N
        SFrekuensi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SFrekuensiKeyPressed(evt);
            }
        });
        FormInput.add(SFrekuensi);
        SFrekuensi.setBounds(520, 1120, 60, 23);

        SRetraksi.setEditable(false);
        SRetraksi.setText("0");
        SRetraksi.setFocusTraversalPolicyProvider(true);
        SRetraksi.setName("SRetraksi"); // NOI18N
        SRetraksi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SRetraksiKeyPressed(evt);
            }
        });
        FormInput.add(SRetraksi);
        SRetraksi.setBounds(520, 1150, 60, 23);

        SSianosis.setEditable(false);
        SSianosis.setText("0");
        SSianosis.setFocusTraversalPolicyProvider(true);
        SSianosis.setName("SSianosis"); // NOI18N
        SSianosis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SSianosisKeyPressed(evt);
            }
        });
        FormInput.add(SSianosis);
        SSianosis.setBounds(520, 1180, 60, 23);

        SAir.setEditable(false);
        SAir.setText("0");
        SAir.setFocusTraversalPolicyProvider(true);
        SAir.setName("SAir"); // NOI18N
        SAir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SAirActionPerformed(evt);
            }
        });
        SAir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SAirKeyPressed(evt);
            }
        });
        FormInput.add(SAir);
        SAir.setBounds(520, 1210, 60, 23);

        SMerintih.setEditable(false);
        SMerintih.setText("0");
        SMerintih.setFocusTraversalPolicyProvider(true);
        SMerintih.setName("SMerintih"); // NOI18N
        SMerintih.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SMerintihKeyPressed(evt);
            }
        });
        FormInput.add(SMerintih);
        SMerintih.setBounds(520, 1240, 60, 23);

        jLabel91.setText("Merintih :");
        jLabel91.setName("jLabel91"); // NOI18N
        FormInput.add(jLabel91);
        jLabel91.setBounds(0, 1240, 100, 23);

        jLabel92.setText("NB :");
        jLabel92.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel92.setName("jLabel92"); // NOI18N
        FormInput.add(jLabel92);
        jLabel92.setBounds(0, 1300, 100, 23);

        jLabel96.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel96.setText("IV. PENILAIAN USIA GESTASI");
        jLabel96.setName("jLabel96"); // NOI18N
        FormInput.add(jLabel96);
        jLabel96.setBounds(20, 1340, 180, 23);

        jSeparator18.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator18.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator18.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator18.setName("jSeparator18"); // NOI18N
        FormInput.add(jSeparator18);
        jSeparator18.setBounds(0, 1330, 880, 1);

        jLabel63.setText("Lanugo :");
        jLabel63.setName("jLabel63"); // NOI18N
        FormInput.add(jLabel63);
        jLabel63.setBounds(0, 1430, 150, 23);

        jLabel70.setText("Permukaan Plataran Kaki :");
        jLabel70.setName("jLabel70"); // NOI18N
        FormInput.add(jLabel70);
        jLabel70.setBounds(0, 1460, 150, 23);

        jLabel76.setText("Payudara :");
        jLabel76.setName("jLabel76"); // NOI18N
        FormInput.add(jLabel76);
        jLabel76.setBounds(0, 1490, 150, 23);

        jLabel93.setText("Total");
        jLabel93.setName("jLabel93"); // NOI18N
        FormInput.add(jLabel93);
        jLabel93.setBounds(510, 1610, 50, 23);

        MKulit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Lengket rapuh transparan", "Merah seperti agar gelatin transparan", "Merah muda halus vena vena tampak", "Permukaan mengelupas dengan atau tanpa ruam vena jarang", "Daerah pucat dan pecah pecah vena panjang", "Seperti kertas kulit pecah pecah dalam tidak ada vena", "Pecah pecah kasar keriput" }));
        MKulit.setName("MKulit"); // NOI18N
        MKulit.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                MKulitItemStateChanged(evt);
            }
        });
        MKulit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MKulitKeyPressed(evt);
            }
        });
        FormInput.add(MKulit);
        MKulit.setBounds(160, 1400, 400, 23);

        Lanugo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak ada", "Jarang", "Banyak sekali", "Menipis", "Menghilang", "Umumnya tidak ada" }));
        Lanugo.setName("Lanugo"); // NOI18N
        Lanugo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                LanugoItemStateChanged(evt);
            }
        });
        Lanugo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LanugoKeyPressed(evt);
            }
        });
        FormInput.add(Lanugo);
        Lanugo.setBounds(160, 1430, 400, 23);

        Permukaan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tumit ibu jari kaki 40-50mm:-1 <40mm:-2", ">50mm tidak ada garis", "Garis garis merah tipis", "Lipatan melintang hanya pada bagian anterior", "Lipatan pada 2/3 anterior", "Garis garis pada seluruh telapak kaki" }));
        Permukaan.setName("Permukaan"); // NOI18N
        Permukaan.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                PermukaanItemStateChanged(evt);
            }
        });
        Permukaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PermukaanKeyPressed(evt);
            }
        });
        FormInput.add(Permukaan);
        Permukaan.setBounds(160, 1460, 400, 23);

        Payudara.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak tampak", "Hamper tidak tampak", "Areola datar tidak ada benjolan", "Areola berbintil benjolan 1-2mm", "Areola timbul benjolan 3-4mm", "Areola penuh berjalan 5-10mm" }));
        Payudara.setName("Payudara"); // NOI18N
        Payudara.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                PayudaraItemStateChanged(evt);
            }
        });
        Payudara.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PayudaraKeyPressed(evt);
            }
        });
        FormInput.add(Payudara);
        Payudara.setBounds(160, 1490, 400, 23);

        MMata.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Kelopak mata menyatu Longgar:-1 Ketat:-2", "Kelopak terbuka pinna datar tetap terlipat", "Pinna sedikit melengkung lunak recoil lambat", "Pinna memutar penuh lunak tetapi sudah recoil", "Pinna keras dan berbentuk recoil segera", "Kartilago tebal telinga kaku" }));
        MMata.setName("MMata"); // NOI18N
        MMata.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                MMataItemStateChanged(evt);
            }
        });
        MMata.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MMataKeyPressed(evt);
            }
        });
        FormInput.add(MMata);
        MMata.setBounds(160, 1520, 400, 23);

        jLabel98.setText("MASTURITAS FISIK");
        jLabel98.setName("jLabel98"); // NOI18N
        FormInput.add(jLabel98);
        jLabel98.setBounds(0, 1370, 130, 23);

        jLabel104.setText("Skor");
        jLabel104.setName("jLabel104"); // NOI18N
        FormInput.add(jLabel104);
        jLabel104.setBounds(560, 1370, 50, 23);

        TotalMasturitas.setEditable(false);
        TotalMasturitas.setText("-7");
        TotalMasturitas.setFocusTraversalPolicyProvider(true);
        TotalMasturitas.setName("TotalMasturitas"); // NOI18N
        TotalMasturitas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TotalMasturitasKeyPressed(evt);
            }
        });
        FormInput.add(TotalMasturitas);
        TotalMasturitas.setBounds(570, 1610, 60, 23);

        jLabel105.setText("Mata / Daun Telinga :");
        jLabel105.setName("jLabel105"); // NOI18N
        FormInput.add(jLabel105);
        jLabel105.setBounds(0, 1520, 150, 23);

        jLabel107.setText("Kulit :");
        jLabel107.setName("jLabel107"); // NOI18N
        FormInput.add(jLabel107);
        jLabel107.setBounds(0, 1400, 150, 23);

        jLabel106.setText("Kelamin Laki-laki :");
        jLabel106.setName("jLabel106"); // NOI18N
        FormInput.add(jLabel106);
        jLabel106.setBounds(0, 1550, 150, 23);

        Laki.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Skrotum datar halus", "Skrotum kosong rugae samar", "Testis pada kanal bagian atas rugae jarang", "Testis menuju ke bawah rugae sedikit", "Testis di skrotum rugae jelas", "Testis pendulous rugae dalam" }));
        Laki.setName("Laki"); // NOI18N
        Laki.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                LakiItemStateChanged(evt);
            }
        });
        Laki.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LakiKeyPressed(evt);
            }
        });
        FormInput.add(Laki);
        Laki.setBounds(160, 1550, 400, 23);

        jLabel108.setText("Sikap :");
        jLabel108.setName("jLabel108"); // NOI18N
        FormInput.add(jLabel108);
        jLabel108.setBounds(510, 1730, 150, 23);

        Perempuan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Klitoris menonjol labia datar", "Klitoris menonjol labia minora kecil", "Klitoris menonjol labia minora membesar", "Labia mayora dan minora sama sama menonjol", "Labia mayora besar labia minora kecil", "Labia mayora menutupi klitoris dan labia minora" }));
        Perempuan.setName("Perempuan"); // NOI18N
        Perempuan.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                PerempuanItemStateChanged(evt);
            }
        });
        Perempuan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PerempuanKeyPressed(evt);
            }
        });
        FormInput.add(Perempuan);
        Perempuan.setBounds(160, 1580, 400, 23);

        PanelWall2.setBackground(new java.awt.Color(29, 29, 29));
        PanelWall2.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/picture/lubechenco.png"))); // NOI18N
        PanelWall2.setBackgroundImageType(usu.widget.constan.BackgroundConstan.BACKGROUND_IMAGE_STRECT);
        PanelWall2.setPreferredSize(new java.awt.Dimension(200, 200));
        PanelWall2.setRound(false);
        PanelWall2.setWarna(new java.awt.Color(110, 110, 110));
        PanelWall2.setLayout(null);

        jLabel117.setText("Y (BBL)");
        jLabel117.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel117.setName("jLabel117"); // NOI18N
        PanelWall2.add(jLabel117);
        jLabel117.setBounds(40, 10, 50, 23);

        FormInput.add(PanelWall2);
        PanelWall2.setBounds(20, 2380, 630, 530);

        TotalBallarad.setEditable(false);
        TotalBallarad.setText("-4");
        TotalBallarad.setFocusTraversalPolicyProvider(true);
        TotalBallarad.setName("TotalBallarad"); // NOI18N
        TotalBallarad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TotalBallaradKeyPressed(evt);
            }
        });
        FormInput.add(TotalBallarad);
        TotalBallarad.setBounds(680, 2000, 60, 23);

        Sikap.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4" }));
        Sikap.setName("Sikap"); // NOI18N
        Sikap.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SikapItemStateChanged(evt);
            }
        });
        Sikap.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SikapKeyPressed(evt);
            }
        });
        FormInput.add(Sikap);
        Sikap.setBounds(680, 1730, 50, 23);

        jLabel109.setText("Kelamin Perempuan :");
        jLabel109.setName("jLabel109"); // NOI18N
        FormInput.add(jLabel109);
        jLabel109.setBounds(0, 1580, 150, 23);

        jLabel111.setText("Jendela Pergelangan Tangan :");
        jLabel111.setName("jLabel111"); // NOI18N
        FormInput.add(jLabel111);
        jLabel111.setBounds(510, 1780, 150, 23);

        Jendela.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-1", "0", "1", "2", "3", "4" }));
        Jendela.setName("Jendela"); // NOI18N
        Jendela.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                JendelaItemStateChanged(evt);
            }
        });
        Jendela.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JendelaKeyPressed(evt);
            }
        });
        FormInput.add(Jendela);
        Jendela.setBounds(680, 1780, 50, 23);

        jLabel112.setText("Rikoil Lengan :");
        jLabel112.setName("jLabel112"); // NOI18N
        FormInput.add(jLabel112);
        jLabel112.setBounds(510, 1820, 150, 23);

        Rikoil.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4" }));
        Rikoil.setName("Rikoil"); // NOI18N
        Rikoil.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                RikoilItemStateChanged(evt);
            }
        });
        Rikoil.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RikoilKeyPressed(evt);
            }
        });
        FormInput.add(Rikoil);
        Rikoil.setBounds(680, 1820, 50, 23);

        jLabel113.setText("Sudut Popliteal :");
        jLabel113.setName("jLabel113"); // NOI18N
        FormInput.add(jLabel113);
        jLabel113.setBounds(510, 1870, 150, 23);

        Sudut.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-1", "0", "1", "2", "3", "4", "5" }));
        Sudut.setName("Sudut"); // NOI18N
        Sudut.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SudutItemStateChanged(evt);
            }
        });
        Sudut.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SudutKeyPressed(evt);
            }
        });
        FormInput.add(Sudut);
        Sudut.setBounds(680, 1870, 50, 23);

        jLabel110.setText("Tanda \"Scarf\" :");
        jLabel110.setName("jLabel110"); // NOI18N
        FormInput.add(jLabel110);
        jLabel110.setBounds(510, 1920, 150, 23);

        Tanda.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-1", "0", "1", "2", "3", "4" }));
        Tanda.setName("Tanda"); // NOI18N
        Tanda.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TandaItemStateChanged(evt);
            }
        });
        Tanda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TandaKeyPressed(evt);
            }
        });
        FormInput.add(Tanda);
        Tanda.setBounds(680, 1920, 50, 23);

        Tumit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-1", "0", "1", "2", "3", "4" }));
        Tumit.setName("Tumit"); // NOI18N
        Tumit.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TumitItemStateChanged(evt);
            }
        });
        Tumit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TumitKeyPressed(evt);
            }
        });
        FormInput.add(Tumit);
        Tumit.setBounds(680, 1960, 50, 23);

        jLabel115.setText("Tumit Ke Telinga :");
        jLabel115.setName("jLabel115"); // NOI18N
        FormInput.add(jLabel115);
        jLabel115.setBounds(510, 1960, 150, 23);

        PanelWall3.setBackground(new java.awt.Color(29, 29, 29));
        PanelWall3.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/picture/ballarad1.png"))); // NOI18N
        PanelWall3.setBackgroundImageType(usu.widget.constan.BackgroundConstan.BACKGROUND_IMAGE_STRECT);
        PanelWall3.setPreferredSize(new java.awt.Dimension(200, 200));
        PanelWall3.setRound(false);
        PanelWall3.setWarna(new java.awt.Color(110, 110, 110));
        PanelWall3.setLayout(null);
        FormInput.add(PanelWall3);
        PanelWall3.setBounds(20, 1660, 490, 340);

        jLabel116.setText("Total :");
        jLabel116.setName("jLabel116"); // NOI18N
        FormInput.add(jLabel116);
        jLabel116.setBounds(510, 2000, 150, 23);

        jLabel118.setText("KMK = Kecil untuk masa kehamilan");
        jLabel118.setName("jLabel118"); // NOI18N
        FormInput.add(jLabel118);
        jLabel118.setBounds(57, 3050, 190, 23);

        jLabel119.setText("KB = Kurang bulan");
        jLabel119.setName("jLabel119"); // NOI18N
        FormInput.add(jLabel119);
        jLabel119.setBounds(70, 2950, 100, 23);

        jLabel120.setText("CB = Cukup bulan");
        jLabel120.setName("jLabel120"); // NOI18N
        FormInput.add(jLabel120);
        jLabel120.setBounds(65, 2970, 100, 23);

        jLabel121.setText("LB = Lebih bulan");
        jLabel121.setName("jLabel121"); // NOI18N
        FormInput.add(jLabel121);
        jLabel121.setBounds(60, 2990, 100, 23);

        jLabel122.setText("BMK = Besar untuk masa kehamilan");
        jLabel122.setName("jLabel122"); // NOI18N
        FormInput.add(jLabel122);
        jLabel122.setBounds(62, 3010, 190, 23);

        jLabel123.setText("SMK = Sesuai untuk masa kehamilan");
        jLabel123.setName("jLabel123"); // NOI18N
        FormInput.add(jLabel123);
        jLabel123.setBounds(67, 3030, 190, 23);

        jLabel124.setText("Input Klasifikasi");
        jLabel124.setName("jLabel124"); // NOI18N
        FormInput.add(jLabel124);
        jLabel124.setBounds(660, 2520, 90, 20);

        jLabel125.setText("X (Usia Kehamilan) ");
        jLabel125.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel125.setName("jLabel125"); // NOI18N
        FormInput.add(jLabel125);
        jLabel125.setBounds(640, 2860, 120, 23);

        jLabel126.setText("Penilaian klasifikasi maturitas bayi baru lahir menurut Lubechenco");
        jLabel126.setName("jLabel126"); // NOI18N
        FormInput.add(jLabel126);
        jLabel126.setBounds(60, 2930, 330, 23);

        SumbuX.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43" }));
        SumbuX.setName("SumbuX"); // NOI18N
        SumbuX.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SumbuXKeyPressed(evt);
            }
        });
        FormInput.add(SumbuX);
        SumbuX.setBounds(780, 2560, 50, 23);

        jLabel127.setText("X  (Usia Kehamilan):");
        jLabel127.setName("jLabel127"); // NOI18N
        FormInput.add(jLabel127);
        jLabel127.setBounds(660, 2560, 110, 20);

        jLabel128.setText("Y (BBL) :");
        jLabel128.setName("jLabel128"); // NOI18N
        FormInput.add(jLabel128);
        jLabel128.setBounds(710, 2600, 60, 20);

        SumbuY.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "400", "600", "800", "1000", "1200", "1400", "1600", "1800", "2000", "2200", "2400", "2600", "2800", "3000", "3200", "3400", "3600", "3800", "4000", "4200" }));
        SumbuY.setName("SumbuY"); // NOI18N
        SumbuY.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SumbuYKeyPressed(evt);
            }
        });
        FormInput.add(SumbuY);
        SumbuY.setBounds(780, 2600, 80, 23);

        SWarna.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "2", "1", "0" }));
        SWarna.setName("SWarna"); // NOI18N
        SWarna.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SWarnaItemStateChanged(evt);
            }
        });
        SWarna.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SWarnaKeyPressed(evt);
            }
        });
        FormInput.add(SWarna);
        SWarna.setBounds(520, 430, 50, 23);

        SPernafasan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "2", "1", "0" }));
        SPernafasan.setName("SPernafasan"); // NOI18N
        SPernafasan.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SPernafasanItemStateChanged(evt);
            }
        });
        SPernafasan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SPernafasanKeyPressed(evt);
            }
        });
        FormInput.add(SPernafasan);
        SPernafasan.setBounds(520, 550, 50, 23);

        SDenyut.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "2", "1", "0" }));
        SDenyut.setName("SDenyut"); // NOI18N
        SDenyut.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SDenyutItemStateChanged(evt);
            }
        });
        SDenyut.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SDenyutKeyPressed(evt);
            }
        });
        FormInput.add(SDenyut);
        SDenyut.setBounds(520, 460, 50, 23);

        SReflek.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "2", "1", "0" }));
        SReflek.setName("SReflek"); // NOI18N
        SReflek.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SReflekItemStateChanged(evt);
            }
        });
        SReflek.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SReflekKeyPressed(evt);
            }
        });
        FormInput.add(SReflek);
        SReflek.setBounds(520, 490, 50, 23);

        STonus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "2", "1", "0" }));
        STonus.setName("STonus"); // NOI18N
        STonus.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                STonusItemStateChanged(evt);
            }
        });
        STonus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                STonusKeyPressed(evt);
            }
        });
        FormInput.add(STonus);
        STonus.setBounds(520, 520, 50, 23);

        SPerempuan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-1", "0", "1", "2", "3", "4" }));
        SPerempuan.setName("SPerempuan"); // NOI18N
        SPerempuan.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SPerempuanItemStateChanged(evt);
            }
        });
        SPerempuan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SPerempuanKeyPressed(evt);
            }
        });
        FormInput.add(SPerempuan);
        SPerempuan.setBounds(570, 1580, 50, 23);

        SKulit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-1", "0", "1", "2", "3", "4", "5" }));
        SKulit.setName("SKulit"); // NOI18N
        SKulit.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SKulitItemStateChanged(evt);
            }
        });
        SKulit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SKulitKeyPressed(evt);
            }
        });
        FormInput.add(SKulit);
        SKulit.setBounds(570, 1400, 50, 23);

        SLanugo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-1", "0", "1", "2", "3", "4" }));
        SLanugo.setName("SLanugo"); // NOI18N
        SLanugo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SLanugoItemStateChanged(evt);
            }
        });
        SLanugo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SLanugoKeyPressed(evt);
            }
        });
        FormInput.add(SLanugo);
        SLanugo.setBounds(570, 1430, 50, 23);

        SPermukaan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-1", "0", "1", "2", "3", "4" }));
        SPermukaan.setName("SPermukaan"); // NOI18N
        SPermukaan.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SPermukaanItemStateChanged(evt);
            }
        });
        SPermukaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SPermukaanKeyPressed(evt);
            }
        });
        FormInput.add(SPermukaan);
        SPermukaan.setBounds(570, 1460, 50, 23);

        SPayudara.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-1", "0", "1", "2", "3", "4" }));
        SPayudara.setName("SPayudara"); // NOI18N
        SPayudara.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SPayudaraItemStateChanged(evt);
            }
        });
        SPayudara.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SPayudaraKeyPressed(evt);
            }
        });
        FormInput.add(SPayudara);
        SPayudara.setBounds(570, 1490, 50, 23);

        SMata.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-1", "0", "1", "2", "3", "4" }));
        SMata.setName("SMata"); // NOI18N
        SMata.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SMataItemStateChanged(evt);
            }
        });
        SMata.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SMataKeyPressed(evt);
            }
        });
        FormInput.add(SMata);
        SMata.setBounds(570, 1520, 50, 23);

        SLaki.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-1", "0", "1", "2", "3", "4" }));
        SLaki.setName("SLaki"); // NOI18N
        SLaki.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SLakiItemStateChanged(evt);
            }
        });
        SLaki.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SLakiKeyPressed(evt);
            }
        });
        FormInput.add(SLaki);
        SLaki.setBounds(570, 1550, 50, 23);

        jLabel56.setText("Punggung :");
        jLabel56.setName("jLabel56"); // NOI18N
        FormInput.add(jLabel56);
        jLabel56.setBounds(0, 930, 100, 23);

        Punggung.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Punggung.setName("Punggung"); // NOI18N
        Punggung.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PunggungKeyPressed(evt);
            }
        });
        FormInput.add(Punggung);
        Punggung.setBounds(110, 930, 128, 23);

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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "24-06-2021" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "24-06-2021" }));
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
        }else if(NmPetugas.getText().trim().equals("")){
            Valid.textKosong(BtnDokter,"Dokter");
        }else if(Hubungan.getText().trim().equals("")){
            Valid.textKosong(Hubungan,"Hubungan");
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
        }else if(Alergi.getText().trim().equals("")){
            Valid.textKosong(Alergi,"Alergi");
        }else if(KetKondisi.getText().trim().equals("")){
            Valid.textKosong(KetKondisi,"GCS");
        }else if(HR.getText().trim().equals("")){
            Valid.textKosong(HR,"HR(x/m)");
        }else if(RR.getText().trim().equals("")){
            Valid.textKosong(RR,"RR(x/menit)");
        }else if(Suhu.getText().trim().equals("")){
            Valid.textKosong(Suhu,"Suhu(C)");
        }else if(SPO.getText().trim().equals("")){
            Valid.textKosong(SPO,"SpO2");
        }else if(BBL.getText().trim().equals("")){
            Valid.textKosong(BBL,"BBL(Kg)");
        }else if(PB.getText().trim().equals("")){
            Valid.textKosong(PB,"TB(Cm)");
        }else if(LK.getText().trim().equals("")){
            Valid.textKosong(LK,"LK(Cm)");
        }else if(LD.getText().trim().equals("")){
            Valid.textKosong(LD,"LD(Cm)");
        }else if(LP.getText().trim().equals("")){
            Valid.textKosong(LP,"LP(Cm)");
        }else if(KetFisik.getText().trim().equals("")){
            Valid.textKosong(KetFisik,"Keterangan Pemeriksaan Fisik");
        }else if(Laboratorium.getText().trim().equals("")){
            Valid.textKosong(Laboratorium,"Hasil Pemeriksaan Laboratorium");
        }else if(Radiologi.getText().trim().equals("")){
            Valid.textKosong(Radiologi,"Hasil Pemeriksaan Radiologi");
        }else if(Penunjang.getText().trim().equals("")){
            Valid.textKosong(Penunjang,"Hasil Pemeriksaan Penunjang Lainnya");
        }else if(Diagnosis.getText().trim().equals("")){
            Valid.textKosong(Diagnosis,"Diagnosis/Asesmen");
        }else if(Tata.getText().trim().equals("")){
            Valid.textKosong(Tata,"Tatalaksana");
        }else if(Edukasi.getText().trim().equals("")){
            Valid.textKosong(Edukasi,"Edukasi");
        }else{
            if(Sequel.menyimpantf("asesmen_medis_ranap_neonatus","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat",88,new String[]{
                    TNoRw.getText(),tanggalNow.format(new Date()),jamNow.format(new Date()),KdPetugas.getText(),Anamnesis.getSelectedItem().toString(),Hubungan.getText(),
                    KeluhanUtama.getText(),RPS.getText(),RPD.getText(),RPK.getText(),RPO.getText(),Alergi.getText(),Kondisi.getSelectedItem().toString(),KetKondisi.getText(),Warna.getSelectedItem().toString(),Denyut.getSelectedItem().toString(),Reflek.getSelectedItem().toString(),Tonus.getSelectedItem().toString(),Pernafasan.getSelectedItem().toString(),
                    SWarna.getSelectedItem().toString(),SDenyut.getSelectedItem().toString(),SReflek.getSelectedItem().toString(),STonus.getSelectedItem().toString(),SPernafasan.getSelectedItem().toString(),TotalFisik.getText(),HR.getText(),RR.getText(),Suhu.getText(),SPO.getText(),BBL.getText(),PB.getText(),LK.getText(),LD.getText(),LP.getText(),
                    Kepala.getSelectedItem().toString(),Mata.getSelectedItem().toString(),Gigi.getSelectedItem().toString(),THT.getSelectedItem().toString(),Thoraks.getSelectedItem().toString(),Jantung.getSelectedItem().toString(),
                    Paru.getSelectedItem().toString(),Abdomen.getSelectedItem().toString(),Punggung.getSelectedItem().toString(),Genital.getSelectedItem().toString(),Ekstremitas.getSelectedItem().toString(),Kulit.getSelectedItem().toString(),KetFisik.getText(),Frekuensi.getSelectedItem().toString(),Retraksi.getSelectedItem().toString(),Sianosis.getSelectedItem().toString(),
                    Air.getSelectedItem().toString(),Merintih.getSelectedItem().toString(),SFrekuensi.getText(),SRetraksi.getText(),SSianosis.getText(),SAir.getText(),SMerintih.getText(),TotalDownes.getText(),MKulit.getSelectedItem().toString(),Lanugo.getSelectedItem().toString(),Permukaan.getSelectedItem().toString(),Payudara.getSelectedItem().toString(),
                    MMata.getSelectedItem().toString(),Laki.getSelectedItem().toString(),Perempuan.getSelectedItem().toString(),SKulit.getSelectedItem().toString(),SLanugo.getSelectedItem().toString(),SPermukaan.getSelectedItem().toString(),SPayudara.getSelectedItem().toString(),SMata.getSelectedItem().toString(),SLaki.getSelectedItem().toString(),SPerempuan.getSelectedItem().toString(),TotalMasturitas.getText(),Sikap.getSelectedItem().toString(),Jendela.getSelectedItem().toString(),
                    Rikoil.getSelectedItem().toString(),Sudut.getSelectedItem().toString(),Tanda.getSelectedItem().toString(),Tumit.getSelectedItem().toString(),TotalBallarad.getText(),SumbuX.getSelectedItem().toString(),SumbuY.getSelectedItem().toString(),
                    Laboratorium.getText(),Radiologi.getText(),Penunjang.getText(),Diagnosis.getText(),Tata.getText(),Edukasi.getText()
                })==true){
                    emptTeks();
            }
        }
    
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,KetFisik,BtnBatal);
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
            if(Sequel.queryu2tf("delete from asesmen_medis_ranap_neonatus where no_rawat=?",1,new String[]{
                tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
            })==true){
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
        }else if(NmPetugas.getText().trim().equals("")){
            Valid.textKosong(BtnDokter,"Dokter");
        }else if(Hubungan.getText().trim().equals("")){
            Valid.textKosong(Hubungan,"Hubungan");
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
        }else if(Alergi.getText().trim().equals("")){
            Valid.textKosong(Alergi,"Alergi");
        }else if(KetKondisi.getText().trim().equals("")){
            Valid.textKosong(KetKondisi,"GCS");
        }else if(HR.getText().trim().equals("")){
            Valid.textKosong(HR,"HR(x/m)");
        }else if(RR.getText().trim().equals("")){
            Valid.textKosong(RR,"RR(x/menit)");
        }else if(Suhu.getText().trim().equals("")){
            Valid.textKosong(Suhu,"Suhu(C)");
        }else if(SPO.getText().trim().equals("")){
            Valid.textKosong(SPO,"SpO2");
        }else if(BBL.getText().trim().equals("")){
            Valid.textKosong(BBL,"BBL(Kg)");
        }else if(PB.getText().trim().equals("")){
            Valid.textKosong(PB,"TB(Cm)");
        }else if(LK.getText().trim().equals("")){
            Valid.textKosong(LK,"LK(Cm)");
        }else if(LD.getText().trim().equals("")){
            Valid.textKosong(LD,"LD(Cm)");
        }else if(LP.getText().trim().equals("")){
            Valid.textKosong(LP,"LP(Cm)");
        }else if(KetFisik.getText().trim().equals("")){
            Valid.textKosong(KetFisik,"Keterangan Pemeriksaan Fisik");
        }else if(Laboratorium.getText().trim().equals("")){
            Valid.textKosong(Laboratorium,"Hasil Pemeriksaan Laboratorium");
        }else if(Radiologi.getText().trim().equals("")){
            Valid.textKosong(Radiologi,"Hasil Pemeriksaan Radiologi");
        }else if(Penunjang.getText().trim().equals("")){
            Valid.textKosong(Penunjang,"Hasil Pemeriksaan Penunjang Lainnya");
        }else if(Diagnosis.getText().trim().equals("")){
            Valid.textKosong(Diagnosis,"Diagnosis/Asesmen");
        }else if(Tata.getText().trim().equals("")){
            Valid.textKosong(Tata,"Tatalaksana");
        }else if(Edukasi.getText().trim().equals("")){
            Valid.textKosong(Edukasi,"Edukasi");
        }else{
            if(tbObat.getSelectedRow()>-1){
                if(Sequel.mengedittf("asesmen_medis_ranap_neonatus","no_rawat=?","no_rawat=?,tanggal=?,jam=?,kd_dokter=?,anamnesis=?,hubungan=?,keluhan_utama=?,rps=?,rpk=?,rpd=?,rpo=?,alergi=?,kondisi=?,ket_kondisi=?,warna=?,denyut=?,reflek=?,tonus=?,pernafasan=?,s_warna=?,s_denyut=?,s_reflek=?,s_tonus=?,s_pernafasan=?,total_fisik=?,hr=?,rr=?,suhu=?,spo=?,bbl=?,pb=?,lk=?,ld=?,lp=?,kepala=?,mata=?,gigi=?,tht=?,thoraks=?,jantung=?,paru=?,abdomen=?,punggung=?,genital=?,ekstremitas=?,kulit=?,ket_fisik=?,frekuensi=?,retraksi=?,sianosis=?,air=?,merintih=?,s_frekuensi=?,s_retraksi=?,s_sianosis=?,s_air=?,s_merintih=?,total_downes=?,m_kulit=?,lanugo=?,permukaan=?,payudara=?,m_mata=?,laki=?,perempuan=?,s_kulit=?,s_lanugo=?,s_permukaan=?,s_payudara=?,s_mata=?,s_laki=?,s_perempuan=?,total_masturitas=?,s_sikap=?,s_jendela=?,s_rikoil=?,s_sudut=?,s_tanda=?,s_tumit=?,total_ballarad=?,sumbu_x=?,sumbu_y=?,lab=?,rad=?,penunjang=?,diagnosis=?,tata=?,edukasi=?",89,new String[]{
                    TNoRw.getText(),tanggalNow.format(new Date()),jamNow.format(new Date()),KdPetugas.getText(),Anamnesis.getSelectedItem().toString(),Hubungan.getText(),
                    KeluhanUtama.getText(),RPS.getText(),RPD.getText(),RPK.getText(),RPO.getText(),Alergi.getText(),Kondisi.getSelectedItem().toString(),KetKondisi.getText(),Warna.getSelectedItem().toString(),Denyut.getSelectedItem().toString(),Reflek.getSelectedItem().toString(),Tonus.getSelectedItem().toString(),Pernafasan.getSelectedItem().toString(),
                    SWarna.getSelectedItem().toString(),SDenyut.getSelectedItem().toString(),SReflek.getSelectedItem().toString(),STonus.getSelectedItem().toString(),SPernafasan.getSelectedItem().toString(),TotalFisik.getText(),HR.getText(),RR.getText(),Suhu.getText(),SPO.getText(),BBL.getText(),PB.getText(),LK.getText(),LD.getText(),LP.getText(),
                    Kepala.getSelectedItem().toString(),Mata.getSelectedItem().toString(),Gigi.getSelectedItem().toString(),THT.getSelectedItem().toString(),Thoraks.getSelectedItem().toString(),Jantung.getSelectedItem().toString(),
                    Paru.getSelectedItem().toString(),Abdomen.getSelectedItem().toString(),Punggung.getSelectedItem().toString(),Genital.getSelectedItem().toString(),Ekstremitas.getSelectedItem().toString(),Kulit.getSelectedItem().toString(),KetFisik.getText(),Frekuensi.getSelectedItem().toString(),Retraksi.getSelectedItem().toString(),Sianosis.getSelectedItem().toString(),
                    Air.getSelectedItem().toString(),Merintih.getSelectedItem().toString(),SFrekuensi.getText(),SRetraksi.getText(),SSianosis.getText(),SAir.getText(),SMerintih.getText(),TotalDownes.getText(),MKulit.getSelectedItem().toString(),Lanugo.getSelectedItem().toString(),Permukaan.getSelectedItem().toString(),Payudara.getSelectedItem().toString(),
                    MMata.getSelectedItem().toString(),Laki.getSelectedItem().toString(),Perempuan.getSelectedItem().toString(),SKulit.getSelectedItem().toString(),SLanugo.getSelectedItem().toString(),SPermukaan.getSelectedItem().toString(),SPayudara.getSelectedItem().toString(),SMata.getSelectedItem().toString(),SLaki.getSelectedItem().toString(),SPerempuan.getSelectedItem().toString(),TotalMasturitas.getText(),Sikap.getSelectedItem().toString(),Jendela.getSelectedItem().toString(),
                    Rikoil.getSelectedItem().toString(),Sudut.getSelectedItem().toString(),Tanda.getSelectedItem().toString(),Tumit.getSelectedItem().toString(),TotalBallarad.getText(),SumbuX.getSelectedItem().toString(),SumbuY.getSelectedItem().toString(),
                    Laboratorium.getText(),Radiologi.getText(),Penunjang.getText(),Diagnosis.getText(),Tata.getText(),Edukasi.getText(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
                     })==true){
                       tampil();
                       emptTeks();
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
                    htmlContent = new StringBuilder();
                    htmlContent.append(                             
                        "<tr class='isi'>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center' width='9%'><b>PASIEN & PETUGAS</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center' width='5%'><b>I. KEADAAN UMUM</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center' width='5%'><b>II. STATUS NUTRISI</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center' width='13%'><b>III. RIWAYAT KESEHATAN</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center' width='8%'><b>IV. FUNGSIONAL</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center' width='16%'><b>V. RIWAYAT PSIKO-SOSIAL SPIRITUAL DAN BUDAYA</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center' width='16%'><b>VI. PENILAIAN RESIKO JATUH</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center' width='11%'><b>VII. SKRINING GIZI</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center' width='11%'><b>VIII. PENILAIAN TINGKAT NYERI</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center' width='6%'><b>MASALAH & RENCANA KEPERAWATAN</b></td>"+
                        "</tr>"
                    );
                    while(rs.next()){
                        masalahkeperawatan="";
                        ps2=koneksi.prepareStatement(
                            "select master_masalah_keperawatan.kode_masalah,master_masalah_keperawatan.nama_masalah from master_masalah_keperawatan "+
                            "inner join penilaian_awal_keperawatan_ralan_masalah on penilaian_awal_keperawatan_ralan_masalah.kode_masalah=master_masalah_keperawatan.kode_masalah "+
                            "where penilaian_awal_keperawatan_ralan_masalah.no_rawat=? order by kode_masalah");
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
                                            "<td width='64%' valign='top'>Status Psikologis</td><td valign='top'>:&nbsp;</td><td width='35%' valign='top'>"+rs.getString("status_psiko")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='64%' valign='top'>Ket. Psikologi</td><td valign='top'>:&nbsp;</td><td width='35%' valign='top'>"+rs.getString("ket_psiko")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='64%' valign='top'>Hubungan pasien dengan anggota keluarga</td><td valign='top'>:&nbsp;</td><td width='35%' valign='top'>"+rs.getString("hub_keluarga")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='64%' valign='top'>Tinggal dengan</td><td valign='top'>:&nbsp;</td><td width='35%' valign='top'>"+rs.getString("tinggal_dengan")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='64%' valign='top'>Ket. Tinggal</td><td valign='top'>:&nbsp;</td><td width='35%' valign='top'>"+rs.getString("ket_tinggal")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='64%' valign='top'>Ekonomi</td><td valign='top'>:&nbsp;</td><td width='35%' valign='top'>"+rs.getString("ekonomi")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='64%' valign='top'>Kepercayaan / Budaya / Nilai-nilai khusus yang perlu diperhatikan</td><td valign='top'>:&nbsp;</td><td width='35%' valign='top'>"+rs.getString("budaya")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='64%' valign='top'>Ket. Budaya</td><td valign='top'>:&nbsp;</td><td width='35%' valign='top'>"+rs.getString("ket_budaya")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='64%' valign='top'>Edukasi diberikan kepada </td><td valign='top'>:&nbsp;</td><td width='35%' valign='top'>"+rs.getString("edukasi")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='64%' valign='top'>Ket. Edukasi</td><td valign='top'>:&nbsp;</td><td width='35%' valign='top'>"+rs.getString("ket_edukasi")+"</td>"+
                                        "</tr>"+
                                    "</table>"+
                                "</td>"+
                                "<td valign='top' cellpadding='0' cellspacing='0'>"+
                                    "<table width='100%' border='0' cellpadding='0' cellspacing='0'align='center'>"+
                                        "<tr class='isi2'>"+
                                            "<td width='64%' valign='top'>Tidak seimbang/sempoyongan/limbung</td><td valign='top'>:&nbsp;</td><td width='35%' valign='top'>"+rs.getString("berjalan_a")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='64%' valign='top'>Jalan dengan menggunakan alat bantu (kruk, tripot, kursi roda, orang lain)</td><td valign='top'>:&nbsp;</td><td width='35%' valign='top'>"+rs.getString("berjalan_b")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='64%' valign='top'>Menopang saat akan duduk, tampak memegang pinggiran kursi atau meja/benda lain sebagai penopang</td><td valign='top'>:&nbsp;</td><td width='35%' valign='top'>"+rs.getString("berjalan_c")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='64%' valign='top'>Hasil</td><td valign='top'>:&nbsp;</td><td width='35%' valign='top'>"+rs.getString("hasil")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='64%' valign='top'>Dilaporan ke dokter?</td><td valign='top'>:&nbsp;</td><td width='35%' valign='top'>"+rs.getString("lapor")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='64%' valign='top'>Jam Lapor</td><td valign='top'>:&nbsp;</td><td width='35%' valign='top'>"+rs.getString("ket_lapor")+"</td>"+
                                        "</tr>"+
                                    "</table>"+
                                "</td>"+
                                "<td valign='top' cellpadding='0' cellspacing='0'>"+
                                    "<table width='100%' border='0' cellpadding='0' cellspacing='0'align='center'>"+
                                        "<tr class='isi2'>"+
                                            "<td width='64%' valign='top'>Apakah ada penurunan berat badanyang tidak diinginkan selama enam bulan terakhir?</td><td valign='top'>:&nbsp;</td><td width='35%' valign='top'>"+rs.getString("sg1")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='64%' valign='top'>Apakah nafsu makan berkurang karena tidak nafsu makan?</td><td valign='top'>:&nbsp;</td><td width='35%' valign='top'>"+rs.getString("sg2")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='64%' valign='top'>Nilai 1</td><td valign='top'>:&nbsp;</td><td width='35%' valign='top'>"+rs.getString("nilai1")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='64%' valign='top'>Nilai 2</td><td valign='top'>:&nbsp;</td><td width='35%' valign='top'>"+rs.getString("nilai2")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='64%' valign='top'>Total Skor</td><td valign='top'>:&nbsp;</td><td width='35%' valign='top'>"+rs.getString("total_hasil")+"</td>"+
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
                                            "<td width='44%' valign='top'>Lokas</td><td valign='top'>:&nbsp;</td><td width='55%' valign='top'>"+rs.getString("lokasi")+"</td>"+
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
                                            "<font size='2' face='Tahoma'>DATA PENILAIAN AWAL KEPERAWATAN RAWAT JALAN<br><br></font>"+        
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
//                ChkAccor.setSelected(true);
//                isMenu();
//                getMasalah();
                getData();
//                TabRawat.setSelectedIndex(0);
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbObatMouseClicked

    private void tbObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbObatKeyPressed
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
//                    ChkAccor.setSelected(true);
//                    isMenu();
//                    getMasalah();
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

    private void KdPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdPetugasKeyPressed
        
    }//GEN-LAST:event_KdPetugasKeyPressed

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

    private void BBLKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BBLKeyPressed
        Valid.pindah(evt,KetKondisi,PB);
    }//GEN-LAST:event_BBLKeyPressed

    private void PBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PBKeyPressed
        Valid.pindah(evt,BBL,KetKondisi);
    }//GEN-LAST:event_PBKeyPressed

    private void SuhuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SuhuKeyPressed
        Valid.pindah(evt,RR,KetKondisi);
    }//GEN-LAST:event_SuhuKeyPressed

    private void HRKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HRKeyPressed
        Valid.pindah(evt,Anamnesis,KetKondisi);
    }//GEN-LAST:event_HRKeyPressed

    private void RRKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RRKeyPressed
        Valid.pindah(evt,KetKondisi,Suhu);
    }//GEN-LAST:event_RRKeyPressed

    private void AlergiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlergiKeyPressed
        Valid.pindah(evt,RPO,Suhu);
    }//GEN-LAST:event_AlergiKeyPressed

    private void AnamnesisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AnamnesisKeyPressed
        Valid.pindah(evt,Suhu,HR);
    }//GEN-LAST:event_AnamnesisKeyPressed

    private void KeluhanUtamaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeluhanUtamaKeyPressed
        Valid.pindah(evt,Suhu,RPK);
    }//GEN-LAST:event_KeluhanUtamaKeyPressed

    private void RPDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RPDKeyPressed
        Valid.pindah(evt,RPK,RPO);
    }//GEN-LAST:event_RPDKeyPressed

    private void RPKKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RPKKeyPressed
        Valid.pindah(evt,KeluhanUtama,RPD);
    }//GEN-LAST:event_RPKKeyPressed

    private void RPOKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RPOKeyPressed
        Valid.pindah(evt,RPD,Alergi);
    }//GEN-LAST:event_RPOKeyPressed

    private void KetFisikKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetFisikKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KetFisikKeyPressed

    private void KetKondisiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetKondisiKeyPressed
        Valid.pindah(evt,Suhu,BBL);
    }//GEN-LAST:event_KetKondisiKeyPressed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if(TabRawat.getSelectedIndex()==1){
            tampil();
        }
    }//GEN-LAST:event_TabRawatMouseClicked

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
//        tampilMasalah();
    }//GEN-LAST:event_formWindowOpened

    private void TPasienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TPasienActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TPasienActionPerformed

    private void RPSKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RPSKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_RPSKeyPressed

    private void KondisiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KondisiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KondisiKeyPressed

    private void SPOKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SPOKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SPOKeyPressed

    private void KepalaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KepalaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KepalaKeyPressed

    private void MataKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MataKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_MataKeyPressed

    private void GigiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GigiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_GigiKeyPressed

    private void THTKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_THTKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_THTKeyPressed

    private void ThoraksKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ThoraksKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_ThoraksKeyPressed

    private void JantungKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JantungKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_JantungKeyPressed

    private void ParuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ParuKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_ParuKeyPressed

    private void AbdomenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AbdomenKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_AbdomenKeyPressed

    private void GenitalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GenitalKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_GenitalKeyPressed

    private void EkstremitasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EkstremitasKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_EkstremitasKeyPressed

    private void KulitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KulitKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KulitKeyPressed

    private void LaboratoriumKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LaboratoriumKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_LaboratoriumKeyPressed

    private void RadiologiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RadiologiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_RadiologiKeyPressed

    private void PenunjangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenunjangKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PenunjangKeyPressed

    private void DiagnosisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosisKeyPressed
        Valid.pindah(evt,Suhu,RPK);
    }//GEN-LAST:event_DiagnosisKeyPressed

    private void TataKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TataKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TataKeyPressed

    private void EdukasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EdukasiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_EdukasiKeyPressed

    private void ReflekKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ReflekKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_ReflekKeyPressed

    private void TonusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TonusKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TonusKeyPressed

    private void PernafasanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PernafasanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PernafasanKeyPressed

    private void DenyutKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DenyutKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DenyutKeyPressed

    private void WarnaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_WarnaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_WarnaKeyPressed

    private void TotalFisikKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TotalFisikKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TotalFisikKeyPressed

    private void LKKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LKKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_LKKeyPressed

    private void LDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LDKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_LDKeyPressed

    private void LPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LPKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_LPKeyPressed

    private void FrekuensiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FrekuensiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_FrekuensiKeyPressed

    private void RetraksiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RetraksiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_RetraksiKeyPressed

    private void SianosisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SianosisKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SianosisKeyPressed

    private void AirKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AirKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_AirKeyPressed

    private void MerintihKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MerintihKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_MerintihKeyPressed

    private void TotalDownesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TotalDownesKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TotalDownesKeyPressed

    private void SFrekuensiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SFrekuensiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SFrekuensiKeyPressed

    private void SRetraksiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SRetraksiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SRetraksiKeyPressed

    private void SSianosisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SSianosisKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SSianosisKeyPressed

    private void SAirKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SAirKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SAirKeyPressed

    private void SMerintihKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SMerintihKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SMerintihKeyPressed

    private void MKulitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MKulitKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_MKulitKeyPressed

    private void LanugoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LanugoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_LanugoKeyPressed

    private void PermukaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PermukaanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PermukaanKeyPressed

    private void PayudaraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PayudaraKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PayudaraKeyPressed

    private void MMataKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MMataKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_MMataKeyPressed

    private void TotalMasturitasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TotalMasturitasKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TotalMasturitasKeyPressed

    private void LakiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LakiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_LakiKeyPressed

    private void PerempuanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PerempuanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PerempuanKeyPressed

    private void TotalBallaradKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TotalBallaradKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TotalBallaradKeyPressed

    private void SikapKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SikapKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SikapKeyPressed

    private void JendelaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JendelaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_JendelaKeyPressed

    private void RikoilKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RikoilKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_RikoilKeyPressed

    private void SudutKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SudutKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SudutKeyPressed

    private void TandaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TandaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TandaKeyPressed

    private void TumitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TumitKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TumitKeyPressed

    private void SumbuXKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SumbuXKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SumbuXKeyPressed

    private void SumbuYKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SumbuYKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SumbuYKeyPressed

    private void WarnaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_WarnaItemStateChanged
        SWarna.setSelectedIndex(Warna.getSelectedIndex());
        TotalFisik.setText(""+(Integer.parseInt(SWarna.getSelectedItem().toString())+Integer.parseInt(SDenyut.getSelectedItem().toString())+Integer.parseInt(SReflek.getSelectedItem().toString())+Integer.parseInt(STonus.getSelectedItem().toString())+Integer.parseInt(SPernafasan.getSelectedItem().toString())));
    }//GEN-LAST:event_WarnaItemStateChanged

    private void DenyutItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_DenyutItemStateChanged
        SDenyut.setSelectedIndex(Denyut.getSelectedIndex());
        TotalFisik.setText(""+(Integer.parseInt(SWarna.getSelectedItem().toString())+Integer.parseInt(SDenyut.getSelectedItem().toString())+Integer.parseInt(SReflek.getSelectedItem().toString())+Integer.parseInt(STonus.getSelectedItem().toString())+Integer.parseInt(SPernafasan.getSelectedItem().toString())));
    }//GEN-LAST:event_DenyutItemStateChanged

    private void ReflekItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ReflekItemStateChanged
        SReflek.setSelectedIndex(Reflek.getSelectedIndex());
        TotalFisik.setText(""+(Integer.parseInt(SWarna.getSelectedItem().toString())+Integer.parseInt(SDenyut.getSelectedItem().toString())+Integer.parseInt(SReflek.getSelectedItem().toString())+Integer.parseInt(STonus.getSelectedItem().toString())+Integer.parseInt(SPernafasan.getSelectedItem().toString())));
    }//GEN-LAST:event_ReflekItemStateChanged

    private void TonusItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TonusItemStateChanged
        STonus.setSelectedIndex(Tonus.getSelectedIndex());
        TotalFisik.setText(""+(Integer.parseInt(SWarna.getSelectedItem().toString())+Integer.parseInt(SDenyut.getSelectedItem().toString())+Integer.parseInt(SReflek.getSelectedItem().toString())+Integer.parseInt(STonus.getSelectedItem().toString())+Integer.parseInt(SPernafasan.getSelectedItem().toString())));
    }//GEN-LAST:event_TonusItemStateChanged

    private void PernafasanItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_PernafasanItemStateChanged
        SPernafasan.setSelectedIndex(Pernafasan.getSelectedIndex());
        TotalFisik.setText(""+(Integer.parseInt(SWarna.getSelectedItem().toString())+Integer.parseInt(SDenyut.getSelectedItem().toString())+Integer.parseInt(SReflek.getSelectedItem().toString())+Integer.parseInt(STonus.getSelectedItem().toString())+Integer.parseInt(SPernafasan.getSelectedItem().toString())));
    }//GEN-LAST:event_PernafasanItemStateChanged

    private void SAirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SAirActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SAirActionPerformed

    private void FrekuensiItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_FrekuensiItemStateChanged
        SFrekuensi.setText(Frekuensi.getSelectedIndex()+"");
        TotalDownes.setText(""+(Integer.parseInt(SFrekuensi.getText())+Integer.parseInt(SRetraksi.getText())+Integer.parseInt(SSianosis.getText())+Integer.parseInt(SAir.getText())+Integer.parseInt(SMerintih.getText())));
    }//GEN-LAST:event_FrekuensiItemStateChanged

    private void RetraksiItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_RetraksiItemStateChanged
        SRetraksi.setText(Retraksi.getSelectedIndex()+"");
        TotalDownes.setText(""+(Integer.parseInt(SFrekuensi.getText())+Integer.parseInt(SRetraksi.getText())+Integer.parseInt(SSianosis.getText())+Integer.parseInt(SAir.getText())+Integer.parseInt(SMerintih.getText())));
    }//GEN-LAST:event_RetraksiItemStateChanged

    private void SianosisItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SianosisItemStateChanged
        SSianosis.setText(Sianosis.getSelectedIndex()+"");
        TotalDownes.setText(""+(Integer.parseInt(SFrekuensi.getText())+Integer.parseInt(SRetraksi.getText())+Integer.parseInt(SSianosis.getText())+Integer.parseInt(SAir.getText())+Integer.parseInt(SMerintih.getText())));
    }//GEN-LAST:event_SianosisItemStateChanged

    private void AirItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_AirItemStateChanged
        SAir.setText(Air.getSelectedIndex()+"");
        TotalDownes.setText(""+(Integer.parseInt(SFrekuensi.getText())+Integer.parseInt(SRetraksi.getText())+Integer.parseInt(SSianosis.getText())+Integer.parseInt(SAir.getText())+Integer.parseInt(SMerintih.getText())));
    }//GEN-LAST:event_AirItemStateChanged

    private void MerintihItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_MerintihItemStateChanged
        SMerintih.setText(Merintih.getSelectedIndex()+"");
        TotalDownes.setText(""+(Integer.parseInt(SFrekuensi.getText())+Integer.parseInt(SRetraksi.getText())+Integer.parseInt(SSianosis.getText())+Integer.parseInt(SAir.getText())+Integer.parseInt(SMerintih.getText())));
    }//GEN-LAST:event_MerintihItemStateChanged

    private void SWarnaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SWarnaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SWarnaKeyPressed

    private void SWarnaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SWarnaItemStateChanged
        TotalFisik.setText(""+(Integer.parseInt(SWarna.getSelectedItem().toString())+Integer.parseInt(SDenyut.getSelectedItem().toString())+Integer.parseInt(SReflek.getSelectedItem().toString())+Integer.parseInt(STonus.getSelectedItem().toString())+Integer.parseInt(SPernafasan.getSelectedItem().toString())));
    }//GEN-LAST:event_SWarnaItemStateChanged

    private void SPernafasanItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SPernafasanItemStateChanged
        TotalFisik.setText(""+(Integer.parseInt(SWarna.getSelectedItem().toString())+Integer.parseInt(SDenyut.getSelectedItem().toString())+Integer.parseInt(SReflek.getSelectedItem().toString())+Integer.parseInt(STonus.getSelectedItem().toString())+Integer.parseInt(SPernafasan.getSelectedItem().toString())));
    }//GEN-LAST:event_SPernafasanItemStateChanged

    private void SPernafasanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SPernafasanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SPernafasanKeyPressed

    private void SDenyutItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SDenyutItemStateChanged
        TotalFisik.setText(""+(Integer.parseInt(SWarna.getSelectedItem().toString())+Integer.parseInt(SDenyut.getSelectedItem().toString())+Integer.parseInt(SReflek.getSelectedItem().toString())+Integer.parseInt(STonus.getSelectedItem().toString())+Integer.parseInt(SPernafasan.getSelectedItem().toString())));
    }//GEN-LAST:event_SDenyutItemStateChanged

    private void SDenyutKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SDenyutKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SDenyutKeyPressed

    private void SReflekItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SReflekItemStateChanged
        TotalFisik.setText(""+(Integer.parseInt(SWarna.getSelectedItem().toString())+Integer.parseInt(SDenyut.getSelectedItem().toString())+Integer.parseInt(SReflek.getSelectedItem().toString())+Integer.parseInt(STonus.getSelectedItem().toString())+Integer.parseInt(SPernafasan.getSelectedItem().toString())));
    }//GEN-LAST:event_SReflekItemStateChanged

    private void SReflekKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SReflekKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SReflekKeyPressed

    private void STonusItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_STonusItemStateChanged
        TotalFisik.setText(""+(Integer.parseInt(SWarna.getSelectedItem().toString())+Integer.parseInt(SDenyut.getSelectedItem().toString())+Integer.parseInt(SReflek.getSelectedItem().toString())+Integer.parseInt(STonus.getSelectedItem().toString())+Integer.parseInt(SPernafasan.getSelectedItem().toString())));
    }//GEN-LAST:event_STonusItemStateChanged

    private void STonusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_STonusKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_STonusKeyPressed

    private void SPerempuanItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SPerempuanItemStateChanged
        TotalMasturitas.setText(""+(Integer.parseInt(SKulit.getSelectedItem().toString())+Integer.parseInt(SLanugo.getSelectedItem().toString())+Integer.parseInt(SPermukaan.getSelectedItem().toString())+Integer.parseInt(SPayudara.getSelectedItem().toString())+Integer.parseInt(SMata.getSelectedItem().toString())+Integer.parseInt(SLaki.getSelectedItem().toString())+Integer.parseInt(SPerempuan.getSelectedItem().toString())));
    }//GEN-LAST:event_SPerempuanItemStateChanged

    private void SPerempuanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SPerempuanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SPerempuanKeyPressed

    private void SKulitItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SKulitItemStateChanged
        TotalMasturitas.setText(""+(Integer.parseInt(SKulit.getSelectedItem().toString())+Integer.parseInt(SLanugo.getSelectedItem().toString())+Integer.parseInt(SPermukaan.getSelectedItem().toString())+Integer.parseInt(SPayudara.getSelectedItem().toString())+Integer.parseInt(SMata.getSelectedItem().toString())+Integer.parseInt(SLaki.getSelectedItem().toString())+Integer.parseInt(SPerempuan.getSelectedItem().toString())));
    }//GEN-LAST:event_SKulitItemStateChanged

    private void SKulitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SKulitKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SKulitKeyPressed

    private void SLanugoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SLanugoItemStateChanged
        TotalMasturitas.setText(""+(Integer.parseInt(SKulit.getSelectedItem().toString())+Integer.parseInt(SLanugo.getSelectedItem().toString())+Integer.parseInt(SPermukaan.getSelectedItem().toString())+Integer.parseInt(SPayudara.getSelectedItem().toString())+Integer.parseInt(SMata.getSelectedItem().toString())+Integer.parseInt(SLaki.getSelectedItem().toString())+Integer.parseInt(SPerempuan.getSelectedItem().toString())));
    }//GEN-LAST:event_SLanugoItemStateChanged

    private void SLanugoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SLanugoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SLanugoKeyPressed

    private void SPermukaanItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SPermukaanItemStateChanged
        TotalMasturitas.setText(""+(Integer.parseInt(SKulit.getSelectedItem().toString())+Integer.parseInt(SLanugo.getSelectedItem().toString())+Integer.parseInt(SPermukaan.getSelectedItem().toString())+Integer.parseInt(SPayudara.getSelectedItem().toString())+Integer.parseInt(SMata.getSelectedItem().toString())+Integer.parseInt(SLaki.getSelectedItem().toString())+Integer.parseInt(SPerempuan.getSelectedItem().toString())));
    }//GEN-LAST:event_SPermukaanItemStateChanged

    private void SPermukaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SPermukaanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SPermukaanKeyPressed

    private void SPayudaraItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SPayudaraItemStateChanged
        TotalMasturitas.setText(""+(Integer.parseInt(SKulit.getSelectedItem().toString())+Integer.parseInt(SLanugo.getSelectedItem().toString())+Integer.parseInt(SPermukaan.getSelectedItem().toString())+Integer.parseInt(SPayudara.getSelectedItem().toString())+Integer.parseInt(SMata.getSelectedItem().toString())+Integer.parseInt(SLaki.getSelectedItem().toString())+Integer.parseInt(SPerempuan.getSelectedItem().toString())));
    }//GEN-LAST:event_SPayudaraItemStateChanged

    private void SPayudaraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SPayudaraKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SPayudaraKeyPressed

    private void SMataItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SMataItemStateChanged
        TotalMasturitas.setText(""+(Integer.parseInt(SKulit.getSelectedItem().toString())+Integer.parseInt(SLanugo.getSelectedItem().toString())+Integer.parseInt(SPermukaan.getSelectedItem().toString())+Integer.parseInt(SPayudara.getSelectedItem().toString())+Integer.parseInt(SMata.getSelectedItem().toString())+Integer.parseInt(SLaki.getSelectedItem().toString())+Integer.parseInt(SPerempuan.getSelectedItem().toString())));
    }//GEN-LAST:event_SMataItemStateChanged

    private void SMataKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SMataKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SMataKeyPressed

    private void SLakiItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SLakiItemStateChanged
        TotalMasturitas.setText(""+(Integer.parseInt(SKulit.getSelectedItem().toString())+Integer.parseInt(SLanugo.getSelectedItem().toString())+Integer.parseInt(SPermukaan.getSelectedItem().toString())+Integer.parseInt(SPayudara.getSelectedItem().toString())+Integer.parseInt(SMata.getSelectedItem().toString())+Integer.parseInt(SLaki.getSelectedItem().toString())+Integer.parseInt(SPerempuan.getSelectedItem().toString())));
    }//GEN-LAST:event_SLakiItemStateChanged

    private void SLakiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SLakiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SLakiKeyPressed

    private void MKulitItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_MKulitItemStateChanged
        SKulit.setSelectedIndex(MKulit.getSelectedIndex());
        TotalMasturitas.setText(""+(Integer.parseInt(SKulit.getSelectedItem().toString())+Integer.parseInt(SLanugo.getSelectedItem().toString())+Integer.parseInt(SPermukaan.getSelectedItem().toString())+Integer.parseInt(SPayudara.getSelectedItem().toString())+Integer.parseInt(SMata.getSelectedItem().toString())+Integer.parseInt(SLaki.getSelectedItem().toString())+Integer.parseInt(SPerempuan.getSelectedItem().toString())));
    }//GEN-LAST:event_MKulitItemStateChanged

    private void LanugoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_LanugoItemStateChanged
        SLanugo.setSelectedIndex(Lanugo.getSelectedIndex());
        TotalMasturitas.setText(""+(Integer.parseInt(SKulit.getSelectedItem().toString())+Integer.parseInt(SLanugo.getSelectedItem().toString())+Integer.parseInt(SPermukaan.getSelectedItem().toString())+Integer.parseInt(SPayudara.getSelectedItem().toString())+Integer.parseInt(SMata.getSelectedItem().toString())+Integer.parseInt(SLaki.getSelectedItem().toString())+Integer.parseInt(SPerempuan.getSelectedItem().toString())));
    }//GEN-LAST:event_LanugoItemStateChanged

    private void PermukaanItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_PermukaanItemStateChanged
        SPermukaan.setSelectedIndex(Permukaan.getSelectedIndex());
        TotalMasturitas.setText(""+(Integer.parseInt(SKulit.getSelectedItem().toString())+Integer.parseInt(SLanugo.getSelectedItem().toString())+Integer.parseInt(SPermukaan.getSelectedItem().toString())+Integer.parseInt(SPayudara.getSelectedItem().toString())+Integer.parseInt(SMata.getSelectedItem().toString())+Integer.parseInt(SLaki.getSelectedItem().toString())+Integer.parseInt(SPerempuan.getSelectedItem().toString())));
    }//GEN-LAST:event_PermukaanItemStateChanged

    private void PayudaraItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_PayudaraItemStateChanged
        SPayudara.setSelectedIndex(Payudara.getSelectedIndex());
        TotalMasturitas.setText(""+(Integer.parseInt(SKulit.getSelectedItem().toString())+Integer.parseInt(SLanugo.getSelectedItem().toString())+Integer.parseInt(SPermukaan.getSelectedItem().toString())+Integer.parseInt(SPayudara.getSelectedItem().toString())+Integer.parseInt(SMata.getSelectedItem().toString())+Integer.parseInt(SLaki.getSelectedItem().toString())+Integer.parseInt(SPerempuan.getSelectedItem().toString())));
    }//GEN-LAST:event_PayudaraItemStateChanged

    private void MMataItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_MMataItemStateChanged
        SMata.setSelectedIndex(MMata.getSelectedIndex());
        TotalMasturitas.setText(""+(Integer.parseInt(SKulit.getSelectedItem().toString())+Integer.parseInt(SLanugo.getSelectedItem().toString())+Integer.parseInt(SPermukaan.getSelectedItem().toString())+Integer.parseInt(SPayudara.getSelectedItem().toString())+Integer.parseInt(SMata.getSelectedItem().toString())+Integer.parseInt(SLaki.getSelectedItem().toString())+Integer.parseInt(SPerempuan.getSelectedItem().toString())));
    }//GEN-LAST:event_MMataItemStateChanged

    private void LakiItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_LakiItemStateChanged
        SLaki.setSelectedIndex(Laki.getSelectedIndex());
        TotalMasturitas.setText(""+(Integer.parseInt(SKulit.getSelectedItem().toString())+Integer.parseInt(SLanugo.getSelectedItem().toString())+Integer.parseInt(SPermukaan.getSelectedItem().toString())+Integer.parseInt(SPayudara.getSelectedItem().toString())+Integer.parseInt(SMata.getSelectedItem().toString())+Integer.parseInt(SLaki.getSelectedItem().toString())+Integer.parseInt(SPerempuan.getSelectedItem().toString())));
    }//GEN-LAST:event_LakiItemStateChanged

    private void PerempuanItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_PerempuanItemStateChanged
        SPerempuan.setSelectedIndex(Perempuan.getSelectedIndex());
        TotalMasturitas.setText(""+(Integer.parseInt(SKulit.getSelectedItem().toString())+Integer.parseInt(SLanugo.getSelectedItem().toString())+Integer.parseInt(SPermukaan.getSelectedItem().toString())+Integer.parseInt(SPayudara.getSelectedItem().toString())+Integer.parseInt(SMata.getSelectedItem().toString())+Integer.parseInt(SLaki.getSelectedItem().toString())+Integer.parseInt(SPerempuan.getSelectedItem().toString())));
    }//GEN-LAST:event_PerempuanItemStateChanged

    private void SikapItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SikapItemStateChanged
        TotalBallarad.setText(""+(Integer.parseInt(Sikap.getSelectedItem().toString())+Integer.parseInt(Jendela.getSelectedItem().toString())+Integer.parseInt(Rikoil.getSelectedItem().toString())+Integer.parseInt(Sudut.getSelectedItem().toString())+Integer.parseInt(Tanda.getSelectedItem().toString())+Integer.parseInt(Tumit.getSelectedItem().toString())));
    }//GEN-LAST:event_SikapItemStateChanged

    private void JendelaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_JendelaItemStateChanged
        TotalBallarad.setText(""+(Integer.parseInt(Sikap.getSelectedItem().toString())+Integer.parseInt(Jendela.getSelectedItem().toString())+Integer.parseInt(Rikoil.getSelectedItem().toString())+Integer.parseInt(Sudut.getSelectedItem().toString())+Integer.parseInt(Tanda.getSelectedItem().toString())+Integer.parseInt(Tumit.getSelectedItem().toString())));
    }//GEN-LAST:event_JendelaItemStateChanged

    private void RikoilItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_RikoilItemStateChanged
        TotalBallarad.setText(""+(Integer.parseInt(Sikap.getSelectedItem().toString())+Integer.parseInt(Jendela.getSelectedItem().toString())+Integer.parseInt(Rikoil.getSelectedItem().toString())+Integer.parseInt(Sudut.getSelectedItem().toString())+Integer.parseInt(Tanda.getSelectedItem().toString())+Integer.parseInt(Tumit.getSelectedItem().toString())));
    }//GEN-LAST:event_RikoilItemStateChanged

    private void SudutItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SudutItemStateChanged
        TotalBallarad.setText(""+(Integer.parseInt(Sikap.getSelectedItem().toString())+Integer.parseInt(Jendela.getSelectedItem().toString())+Integer.parseInt(Rikoil.getSelectedItem().toString())+Integer.parseInt(Sudut.getSelectedItem().toString())+Integer.parseInt(Tanda.getSelectedItem().toString())+Integer.parseInt(Tumit.getSelectedItem().toString())));
    }//GEN-LAST:event_SudutItemStateChanged

    private void TandaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TandaItemStateChanged
        TotalBallarad.setText(""+(Integer.parseInt(Sikap.getSelectedItem().toString())+Integer.parseInt(Jendela.getSelectedItem().toString())+Integer.parseInt(Rikoil.getSelectedItem().toString())+Integer.parseInt(Sudut.getSelectedItem().toString())+Integer.parseInt(Tanda.getSelectedItem().toString())+Integer.parseInt(Tumit.getSelectedItem().toString())));
    }//GEN-LAST:event_TandaItemStateChanged

    private void TumitItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TumitItemStateChanged
        TotalBallarad.setText(""+(Integer.parseInt(Sikap.getSelectedItem().toString())+Integer.parseInt(Jendela.getSelectedItem().toString())+Integer.parseInt(Rikoil.getSelectedItem().toString())+Integer.parseInt(Sudut.getSelectedItem().toString())+Integer.parseInt(Tanda.getSelectedItem().toString())+Integer.parseInt(Tumit.getSelectedItem().toString())));
    }//GEN-LAST:event_TumitItemStateChanged

    private void PunggungKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PunggungKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PunggungKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            AsesmenAwalMedisRanapNeonatus dialog = new AsesmenAwalMedisRanapNeonatus(new javax.swing.JFrame(), true);
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
    private widget.ComboBox Air;
    private widget.TextBox Alergi;
    private widget.ComboBox Anamnesis;
    private widget.TextBox BBL;
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
    private widget.ComboBox Denyut;
    private widget.TextArea Diagnosis;
    private widget.TextArea Edukasi;
    private widget.ComboBox Ekstremitas;
    private widget.PanelBiasa FormInput;
    private widget.ComboBox Frekuensi;
    private widget.ComboBox Genital;
    private widget.ComboBox Gigi;
    private widget.TextBox HR;
    private widget.TextBox Hubungan;
    private widget.ComboBox Jantung;
    private widget.ComboBox Jendela;
    private widget.TextBox Jk;
    private widget.TextBox KdPetugas;
    private widget.TextArea KeluhanUtama;
    private widget.ComboBox Kepala;
    private widget.TextArea KetFisik;
    private widget.TextBox KetKondisi;
    private widget.ComboBox Kondisi;
    private widget.ComboBox Kulit;
    private widget.Label LCount;
    private widget.TextBox LD;
    private widget.TextBox LK;
    private widget.TextBox LP;
    private widget.TextArea Laboratorium;
    private widget.ComboBox Laki;
    private widget.ComboBox Lanugo;
    private widget.editorpane LoadHTML;
    private widget.ComboBox MKulit;
    private widget.ComboBox MMata;
    private widget.ComboBox Mata;
    private widget.ComboBox Merintih;
    private widget.TextBox NmPetugas;
    private widget.TextBox PB;
    private usu.widget.glass.PanelGlass PanelWall1;
    private usu.widget.glass.PanelGlass PanelWall2;
    private usu.widget.glass.PanelGlass PanelWall3;
    private widget.ComboBox Paru;
    private widget.ComboBox Payudara;
    private widget.TextArea Penunjang;
    private widget.ComboBox Perempuan;
    private widget.ComboBox Permukaan;
    private widget.ComboBox Pernafasan;
    private widget.ComboBox Punggung;
    private widget.TextArea RPD;
    private widget.TextArea RPK;
    private widget.TextArea RPO;
    private widget.TextArea RPS;
    private widget.TextBox RR;
    private widget.TextArea Radiologi;
    private widget.ComboBox Reflek;
    private widget.ComboBox Retraksi;
    private widget.ComboBox Rikoil;
    private widget.TextBox SAir;
    private widget.ComboBox SDenyut;
    private widget.TextBox SFrekuensi;
    private widget.ComboBox SKulit;
    private widget.ComboBox SLaki;
    private widget.ComboBox SLanugo;
    private widget.ComboBox SMata;
    private widget.TextBox SMerintih;
    private widget.TextBox SPO;
    private widget.ComboBox SPayudara;
    private widget.ComboBox SPerempuan;
    private widget.ComboBox SPermukaan;
    private widget.ComboBox SPernafasan;
    private widget.ComboBox SReflek;
    private widget.TextBox SRetraksi;
    private widget.TextBox SSianosis;
    private widget.ComboBox STonus;
    private widget.ComboBox SWarna;
    private widget.ScrollPane Scroll;
    private widget.ComboBox Sianosis;
    private widget.ComboBox Sikap;
    private widget.ComboBox Sudut;
    private widget.TextBox Suhu;
    private widget.ComboBox SumbuX;
    private widget.ComboBox SumbuY;
    private widget.TextBox TCari;
    private widget.ComboBox THT;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private javax.swing.JTabbedPane TabRawat;
    private widget.ComboBox Tanda;
    private widget.TextArea Tata;
    private widget.TextBox TglLahir;
    private widget.ComboBox Thoraks;
    private widget.ComboBox Tonus;
    private widget.TextBox TotalBallarad;
    private widget.TextBox TotalDownes;
    private widget.TextBox TotalFisik;
    private widget.TextBox TotalMasturitas;
    private widget.ComboBox Tumit;
    private widget.ComboBox Warna;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.Label jLabel10;
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
    private widget.Label jLabel6;
    private widget.Label jLabel61;
    private widget.Label jLabel62;
    private widget.Label jLabel63;
    private widget.Label jLabel68;
    private widget.Label jLabel69;
    private widget.Label jLabel7;
    private widget.Label jLabel70;
    private widget.Label jLabel74;
    private widget.Label jLabel75;
    private widget.Label jLabel76;
    private widget.Label jLabel78;
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
    private widget.Label jLabel98;
    private widget.Label jLabel99;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator15;
    private javax.swing.JSeparator jSeparator16;
    private javax.swing.JSeparator jSeparator17;
    private javax.swing.JSeparator jSeparator18;
    private widget.Label label14;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollInput;
    private widget.ScrollPane scrollPane1;
    private widget.ScrollPane scrollPane10;
    private widget.ScrollPane scrollPane11;
    private widget.ScrollPane scrollPane12;
    private widget.ScrollPane scrollPane13;
    private widget.ScrollPane scrollPane14;
    private widget.ScrollPane scrollPane2;
    private widget.ScrollPane scrollPane3;
    private widget.ScrollPane scrollPane4;
    private widget.ScrollPane scrollPane5;
    private widget.ScrollPane scrollPane7;
    private widget.ScrollPane scrollPane9;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            if(TCari.getText().equals("")){
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,asesmen_medis_ranap_neonatus.tanggal,"+
                        "asesmen_medis_ranap_neonatus.jam,asesmen_medis_ranap_neonatus.kd_dokter,asesmen_medis_ranap_neonatus.anamnesis,asesmen_medis_ranap_neonatus.hubungan,asesmen_medis_ranap_neonatus.keluhan_utama,asesmen_medis_ranap_neonatus.rps,asesmen_medis_ranap_neonatus.rpk,asesmen_medis_ranap_neonatus.rpd,asesmen_medis_ranap_neonatus.rpo,asesmen_medis_ranap_neonatus.alergi,"+
                        "asesmen_medis_ranap_neonatus.kondisi,asesmen_medis_ranap_neonatus.ket_kondisi,asesmen_medis_ranap_neonatus.warna,asesmen_medis_ranap_neonatus.denyut,asesmen_medis_ranap_neonatus.reflek,asesmen_medis_ranap_neonatus.tonus,asesmen_medis_ranap_neonatus.pernafasan,asesmen_medis_ranap_neonatus.s_warna,asesmen_medis_ranap_neonatus.s_denyut,asesmen_medis_ranap_neonatus.s_reflek,asesmen_medis_ranap_neonatus.s_tonus,asesmen_medis_ranap_neonatus.s_pernafasan,asesmen_medis_ranap_neonatus.total_fisik,asesmen_medis_ranap_neonatus.hr,asesmen_medis_ranap_neonatus.rr,asesmen_medis_ranap_neonatus.suhu,asesmen_medis_ranap_neonatus.spo,"+
                        "asesmen_medis_ranap_neonatus.bbl,asesmen_medis_ranap_neonatus.pb,asesmen_medis_ranap_neonatus.lk,asesmen_medis_ranap_neonatus.ld,asesmen_medis_ranap_neonatus.lp,asesmen_medis_ranap_neonatus.kepala,asesmen_medis_ranap_neonatus.mata,asesmen_medis_ranap_neonatus.gigi,asesmen_medis_ranap_neonatus.tht,asesmen_medis_ranap_neonatus.thoraks,"+
                        "asesmen_medis_ranap_neonatus.jantung,asesmen_medis_ranap_neonatus.paru,asesmen_medis_ranap_neonatus.abdomen,asesmen_medis_ranap_neonatus.punggung,asesmen_medis_ranap_neonatus.ekstremitas,asesmen_medis_ranap_neonatus.genital,asesmen_medis_ranap_neonatus.kulit,"+
                        "asesmen_medis_ranap_neonatus.ket_fisik,asesmen_medis_ranap_neonatus.frekuensi,asesmen_medis_ranap_neonatus.retraksi,asesmen_medis_ranap_neonatus.sianosis,asesmen_medis_ranap_neonatus.air,asesmen_medis_ranap_neonatus.merintih,asesmen_medis_ranap_neonatus.s_frekuensi,asesmen_medis_ranap_neonatus.s_retraksi,asesmen_medis_ranap_neonatus.s_sianosis,asesmen_medis_ranap_neonatus.s_air,asesmen_medis_ranap_neonatus.s_merintih,asesmen_medis_ranap_neonatus.total_downes,"+
                        "asesmen_medis_ranap_neonatus.m_kulit,asesmen_medis_ranap_neonatus.lanugo,asesmen_medis_ranap_neonatus.permukaan,asesmen_medis_ranap_neonatus.payudara,asesmen_medis_ranap_neonatus.m_mata,asesmen_medis_ranap_neonatus.laki,asesmen_medis_ranap_neonatus.perempuan,asesmen_medis_ranap_neonatus.s_kulit,asesmen_medis_ranap_neonatus.s_lanugo,asesmen_medis_ranap_neonatus.s_permukaan,asesmen_medis_ranap_neonatus.s_payudara,asesmen_medis_ranap_neonatus.s_mata,asesmen_medis_ranap_neonatus.s_laki,asesmen_medis_ranap_neonatus.s_perempuan,asesmen_medis_ranap_neonatus.total_masturitas,"+
                        "asesmen_medis_ranap_neonatus.s_sikap,asesmen_medis_ranap_neonatus.s_jendela,asesmen_medis_ranap_neonatus.s_rikoil,asesmen_medis_ranap_neonatus.s_sudut,asesmen_medis_ranap_neonatus.s_tanda,asesmen_medis_ranap_neonatus.s_tumit,asesmen_medis_ranap_neonatus.total_ballarad,asesmen_medis_ranap_neonatus.sumbu_x,asesmen_medis_ranap_neonatus.sumbu_y,asesmen_medis_ranap_neonatus.lab,asesmen_medis_ranap_neonatus.rad,asesmen_medis_ranap_neonatus.penunjang,asesmen_medis_ranap_neonatus.diagnosis,"+
                        "asesmen_medis_ranap_neonatus.tata,asesmen_medis_ranap_neonatus.edukasi,dokter.nm_dokter "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join asesmen_medis_ranap_neonatus on reg_periksa.no_rawat=asesmen_medis_ranap_neonatus.no_rawat "+
                        "inner join dokter on asesmen_medis_ranap_neonatus.kd_dokter=dokter.kd_dokter "+
                        "inner join bahasa_pasien on bahasa_pasien.id=pasien.bahasa_pasien "+
                        "inner join cacat_fisik on cacat_fisik.id=pasien.cacat_fisik where "+
                        "asesmen_medis_ranap_neonatus.tanggal between ? and ? order by asesmen_medis_ranap_neonatus.tanggal");
            }else{
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,asesmen_medis_ranap_neonatus.tanggal,"+
                        "asesmen_medis_ranap_neonatus.jam,asesmen_medis_ranap_neonatus.kd_dokter,asesmen_medis_ranap_neonatus.anamnesis,asesmen_medis_ranap_neonatus.hubungan,asesmen_medis_ranap_neonatus.keluhan_utama,asesmen_medis_ranap_neonatus.rps,asesmen_medis_ranap_neonatus.rpk,asesmen_medis_ranap_neonatus.rpd,asesmen_medis_ranap_neonatus.rpo,asesmen_medis_ranap_neonatus.alergi,"+
                        "asesmen_medis_ranap_neonatus.kondisi,asesmen_medis_ranap_neonatus.ket_kondisi,asesmen_medis_ranap_neonatus.warna,asesmen_medis_ranap_neonatus.denyut,asesmen_medis_ranap_neonatus.reflek,asesmen_medis_ranap_neonatus.tonus,asesmen_medis_ranap_neonatus.pernafasan,asesmen_medis_ranap_neonatus.s_warna,asesmen_medis_ranap_neonatus.s_denyut,asesmen_medis_ranap_neonatus.s_reflek,asesmen_medis_ranap_neonatus.s_tonus,asesmen_medis_ranap_neonatus.s_pernafasan,asesmen_medis_ranap_neonatus.total_fisik,asesmen_medis_ranap_neonatus.hr,asesmen_medis_ranap_neonatus.rr,asesmen_medis_ranap_neonatus.suhu,asesmen_medis_ranap_neonatus.spo,"+
                        "asesmen_medis_ranap_neonatus.bbl,asesmen_medis_ranap_neonatus.pb,asesmen_medis_ranap_neonatus.lk,asesmen_medis_ranap_neonatus.ld,asesmen_medis_ranap_neonatus.lp,asesmen_medis_ranap_neonatus.kepala,asesmen_medis_ranap_neonatus.mata,asesmen_medis_ranap_neonatus.gigi,asesmen_medis_ranap_neonatus.tht,asesmen_medis_ranap_neonatus.thoraks,"+
                        "asesmen_medis_ranap_neonatus.jantung,asesmen_medis_ranap_neonatus.paru,asesmen_medis_ranap_neonatus.abdomen,asesmen_medis_ranap_neonatus.punggung,asesmen_medis_ranap_neonatus.ekstremitas,asesmen_medis_ranap_neonatus.genital,asesmen_medis_ranap_neonatus.kulit,"+
                        "asesmen_medis_ranap_neonatus.ket_fisik,asesmen_medis_ranap_neonatus.frekuensi,asesmen_medis_ranap_neonatus.retraksi,asesmen_medis_ranap_neonatus.sianosis,asesmen_medis_ranap_neonatus.air,asesmen_medis_ranap_neonatus.merintih,asesmen_medis_ranap_neonatus.s_frekuensi,asesmen_medis_ranap_neonatus.s_retraksi,asesmen_medis_ranap_neonatus.s_sianosis,asesmen_medis_ranap_neonatus.s_air,asesmen_medis_ranap_neonatus.s_merintih,asesmen_medis_ranap_neonatus.total_downes,"+
                        "asesmen_medis_ranap_neonatus.m_kulit,asesmen_medis_ranap_neonatus.lanugo,asesmen_medis_ranap_neonatus.permukaan,asesmen_medis_ranap_neonatus.payudara,asesmen_medis_ranap_neonatus.m_mata,asesmen_medis_ranap_neonatus.laki,asesmen_medis_ranap_neonatus.perempuan,asesmen_medis_ranap_neonatus.s_kulit,asesmen_medis_ranap_neonatus.s_lanugo,asesmen_medis_ranap_neonatus.s_permukaan,asesmen_medis_ranap_neonatus.s_payudara,asesmen_medis_ranap_neonatus.s_mata,asesmen_medis_ranap_neonatus.s_laki,asesmen_medis_ranap_neonatus.s_perempuan,asesmen_medis_ranap_neonatus.total_masturitas,"+
                        "asesmen_medis_ranap_neonatus.s_sikap,asesmen_medis_ranap_neonatus.s_jendela,asesmen_medis_ranap_neonatus.s_rikoil,asesmen_medis_ranap_neonatus.s_sudut,asesmen_medis_ranap_neonatus.s_tanda,asesmen_medis_ranap_neonatus.s_tumit,asesmen_medis_ranap_neonatus.total_ballarad,asesmen_medis_ranap_neonatus.sumbu_x,asesmen_medis_ranap_neonatus.sumbu_y,asesmen_medis_ranap_neonatus.lab,asesmen_medis_ranap_neonatus.rad,asesmen_medis_ranap_neonatus.penunjang,asesmen_medis_ranap_neonatus.diagnosis,"+
                        "asesmen_medis_ranap_neonatus.tata,asesmen_medis_ranap_neonatus.edukasi,dokter.nm_dokter "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join asesmen_medis_ranap_neonatus on reg_periksa.no_rawat=asesmen_medis_ranap_neonatus.no_rawat "+
                        "inner join dokter on asesmen_medis_ranap_neonatus.kd_dokter=dokter.kd_dokter "+
                        "inner join bahasa_pasien on bahasa_pasien.id=pasien.bahasa_pasien "+
                        "inner join cacat_fisik on cacat_fisik.id=pasien.cacat_fisik where "+
                        "asesmen_medis_ranap_neonatus.tanggal between ? and ? and reg_periksa.no_rawat like ? or "+
                        "asesmen_medis_ranap_neonatus.tanggal between ? and ? and pasien.no_rkm_medis like ? or "+
                        "asesmen_medis_ranap_neonatus.tanggal between ? and ? and pasien.nm_pasien like ? or "+
                        "asesmen_medis_ranap_neonatus.tanggal between ? and ? and asesmen_medis_ranap_neonatus.kd_dokter like ? or "+
                        "asesmen_medis_ranap_neonatus.tanggal between ? and ? and dokter.nm_dokter like ? order by asesmen_medis_ranap_neonatus.tanggal");
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
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("tgl_lahir"),rs.getString("jk"),rs.getString("kd_dokter"),rs.getString("nm_dokter"),rs.getString("tanggal"),rs.getString("jam"),
                        rs.getString("anamnesis"),rs.getString("hubungan"),rs.getString("keluhan_utama"),rs.getString("rps"),rs.getString("rpd"),rs.getString("rpk"),rs.getString("rpo"),rs.getString("alergi"),
                        rs.getString("kondisi"),rs.getString("ket_kondisi"),rs.getString("warna"),rs.getString("denyut"),rs.getString("reflek"),rs.getString("tonus"),rs.getString("pernafasan"),
                        rs.getString("s_warna"),rs.getString("s_denyut"),rs.getString("s_reflek"),rs.getString("s_tonus"),rs.getString("s_pernafasan"),rs.getString("total_fisik"),
                        rs.getString("hr"),rs.getString("rr"),rs.getString("suhu"),rs.getString("spo"),rs.getString("bbl"),rs.getString("pb"),rs.getString("lk"),rs.getString("ld"),rs.getString("lp"),
                        rs.getString("kepala"),rs.getString("mata"),rs.getString("gigi"),rs.getString("tht"),rs.getString("thoraks"),rs.getString("jantung"),rs.getString("paru"),rs.getString("abdomen"),rs.getString("punggung"),rs.getString("genital"),rs.getString("ekstremitas"),rs.getString("kulit"),rs.getString("ket_fisik"),
                        rs.getString("frekuensi"),rs.getString("retraksi"),rs.getString("sianosis"),rs.getString("air"),rs.getString("merintih"),rs.getString("s_frekuensi"),rs.getString("s_retraksi"),rs.getString("s_sianosis"),rs.getString("s_air"),rs.getString("s_merintih"),rs.getString("total_downes"),
                        rs.getString("m_kulit"),rs.getString("lanugo"),rs.getString("permukaan"),rs.getString("payudara"),rs.getString("m_mata"),rs.getString("laki"),rs.getString("perempuan"),
                        rs.getString("s_kulit"),rs.getString("s_lanugo"),rs.getString("s_permukaan"),rs.getString("s_payudara"),rs.getString("s_mata"),rs.getString("s_laki"),rs.getString("s_perempuan"),rs.getString("total_masturitas"),
                        rs.getString("s_sikap"),rs.getString("s_jendela"),rs.getString("s_rikoil"),rs.getString("s_sudut"),rs.getString("s_tanda"),rs.getString("s_tumit"),rs.getString("total_ballarad"),rs.getString("sumbu_x"),rs.getString("sumbu_y"),
                        rs.getString("lab"),rs.getString("rad"),rs.getString("penunjang"),rs.getString("diagnosis"),rs.getString("tata"),rs.getString("edukasi")
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
        RPD.setText("");
        RPO.setText("");
        Alergi.setText("");
        Kondisi.setSelectedIndex(0);
        KetKondisi.setText("");
        Warna.setSelectedIndex(0);
        Denyut.setSelectedIndex(0);
        Reflek.setSelectedIndex(0);
        Tonus.setSelectedIndex(0);
        Pernafasan.setSelectedIndex(0);
        SWarna.setSelectedIndex(0);
        SDenyut.setSelectedIndex(0);
        SReflek.setSelectedIndex(0);
        STonus.setSelectedIndex(0);
        SPernafasan.setSelectedIndex(0);
        TotalFisik.setText("10");
        HR.setText("");
        RR.setText("");
        Suhu.setText("");
        SPO.setText("");
        BBL.setText("");
        PB.setText("");
        LK.setText("");
        LD.setText("");
        LP.setText("");
        Kepala.setSelectedIndex(0);
        Mata.setSelectedIndex(0);
        Gigi.setSelectedIndex(0);
        THT.setSelectedIndex(0);
        Thoraks.setSelectedIndex(0);
        Jantung.setSelectedIndex(0);
        Paru.setSelectedIndex(0);
        Abdomen.setSelectedIndex(0);
        Punggung.setSelectedIndex(0);
        Genital.setSelectedIndex(0);
        Ekstremitas.setSelectedIndex(0);
        Kulit.setSelectedIndex(0);
        KetFisik.setText("");
        Frekuensi.setSelectedIndex(0);
        Retraksi.setSelectedIndex(0);
        Sianosis.setSelectedIndex(0);
        Air.setSelectedIndex(0);
        Merintih.setSelectedIndex(0);
        SFrekuensi.setText("0");
        SRetraksi.setText("0");
        SSianosis.setText("0");
        SAir.setText("0");
        SMerintih.setText("0");
        TotalDownes.setText("0");
        MKulit.setSelectedIndex(0);
        Lanugo.setSelectedIndex(0);
        Permukaan.setSelectedIndex(0);
        Payudara.setSelectedIndex(0);
        MMata.setSelectedIndex(0);
        Laki.setSelectedIndex(0);
        Perempuan.setSelectedIndex(0);
        SKulit.setSelectedIndex(0);
        SLanugo.setSelectedIndex(0);;
        SPermukaan.setSelectedIndex(0);
        SPayudara.setSelectedIndex(0);
        SMata.setSelectedIndex(0);
        SLaki.setSelectedIndex(0);
        SPerempuan.setSelectedIndex(0);
        TotalMasturitas.setText("-7");
        Sikap.setSelectedIndex(0);
        Jendela.setSelectedIndex(0);
        Rikoil.setSelectedIndex(0);
        Sudut.setSelectedIndex(0);
        Tanda.setSelectedIndex(0);
        Tumit.setSelectedIndex(0);
        TotalBallarad.setText("-4");
        SumbuX.setSelectedIndex(0);
        SumbuY.setSelectedIndex(0);
        Laboratorium.setText("");
        Radiologi.setText("");
        Penunjang.setText("");
        Diagnosis.setText("");
        Tata.setText("");
        Edukasi.setText("");
        TNoRw.requestFocus();
    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()); 
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            Jk.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString()); 
            KdPetugas.setText(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
            NmPetugas.setText(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString());
            Anamnesis.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            Hubungan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            KeluhanUtama.setText(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            RPS.setText(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            RPD.setText(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            RPK.setText(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            RPO.setText(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
            Alergi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            Kondisi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());
            KetKondisi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
            Warna.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
            Denyut.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString());
            Reflek.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString());
            Tonus.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString());
            Pernafasan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString());
            SWarna.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),24).toString());
            SDenyut.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),25).toString());
            SReflek.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),26).toString());
            STonus.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),27).toString());
            SPernafasan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),28).toString());
            TotalFisik.setText(tbObat.getValueAt(tbObat.getSelectedRow(),29).toString());
            HR.setText(tbObat.getValueAt(tbObat.getSelectedRow(),30).toString());
            RR.setText(tbObat.getValueAt(tbObat.getSelectedRow(),31).toString());
            Suhu.setText(tbObat.getValueAt(tbObat.getSelectedRow(),32).toString());
            SPO.setText(tbObat.getValueAt(tbObat.getSelectedRow(),33).toString());
            BBL.setText(tbObat.getValueAt(tbObat.getSelectedRow(),34).toString());
            PB.setText(tbObat.getValueAt(tbObat.getSelectedRow(),35).toString());
            LK.setText(tbObat.getValueAt(tbObat.getSelectedRow(),36).toString());
            LD.setText(tbObat.getValueAt(tbObat.getSelectedRow(),37).toString());
            LP.setText(tbObat.getValueAt(tbObat.getSelectedRow(),38).toString());
            Kepala.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),39).toString());
            Mata.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),40).toString());
            Gigi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),41).toString());
            THT.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),42).toString());
            Thoraks.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),43).toString());
            Jantung.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),44).toString());
            Paru.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),45).toString());
            Abdomen.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),46).toString());
            Punggung.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),47).toString());
            Genital.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),48).toString());
            Ekstremitas.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),49).toString());
            Kulit.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),50).toString());
            KetFisik.setText(tbObat.getValueAt(tbObat.getSelectedRow(),51).toString());
            Frekuensi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),52).toString());
            Retraksi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),53).toString());
            Sianosis.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),54).toString());
            Air.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),55).toString());
            Merintih.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),56).toString());
            SFrekuensi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),57).toString());
            SRetraksi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),58).toString());
            SSianosis.setText(tbObat.getValueAt(tbObat.getSelectedRow(),59).toString());
            SAir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),60).toString());
            SMerintih.setText(tbObat.getValueAt(tbObat.getSelectedRow(),61).toString());
            TotalDownes.setText(tbObat.getValueAt(tbObat.getSelectedRow(),62).toString());
            MKulit.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),63).toString());
            Lanugo.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),64).toString());
            Permukaan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),65).toString());
            Payudara.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),66).toString());
            MMata.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),67).toString());
            Laki.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),68).toString());
            Perempuan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),69).toString());
            SKulit.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),70).toString());
            SLanugo.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),71).toString());
            SPermukaan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),72).toString());
            SPayudara.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),73).toString());
            SMata.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),74).toString());
            SLaki.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),75).toString());
            SPerempuan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),76).toString());
            TotalMasturitas.setText(tbObat.getValueAt(tbObat.getSelectedRow(),77).toString());
            Sikap.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),78).toString());
            Jendela.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),79).toString());
            Rikoil.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),80).toString());
            Sudut.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),81).toString());
            Tanda.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),82).toString());
            Tumit.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),83).toString());
            TotalBallarad.setText(tbObat.getValueAt(tbObat.getSelectedRow(),84).toString());
            SumbuX.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),85).toString());
            SumbuY.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),86).toString());
            Laboratorium.setText(tbObat.getValueAt(tbObat.getSelectedRow(),87).toString());
            Radiologi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),88).toString());
            Penunjang.setText(tbObat.getValueAt(tbObat.getSelectedRow(),89).toString());
            Diagnosis.setText(tbObat.getValueAt(tbObat.getSelectedRow(),90).toString());
            Tata.setText(tbObat.getValueAt(tbObat.getSelectedRow(),91).toString());
            Edukasi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),92).toString());
            
//            try {
//                Valid.tabelKosong(tabModeMasalah);
//                
//                ps=koneksi.prepareStatement(
//                        "select master_masalah_keperawatan.kode_masalah,master_masalah_keperawatan.nama_masalah from master_masalah_keperawatan "+
//                        "inner join penilaian_awal_keperawatan_ralan_masalah on penilaian_awal_keperawatan_ralan_masalah.kode_masalah=master_masalah_keperawatan.kode_masalah "+
//                        "where penilaian_awal_keperawatan_ralan_masalah.no_rawat=? order by kode_masalah");
//                try {
//                    ps.setString(1,tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
//                    rs=ps.executeQuery();
//                    while(rs.next()){
//                        tabModeMasalah.addRow(new Object[]{true,rs.getString(1),rs.getString(2)});
//                    }
//                } catch (Exception e) {
//                    System.out.println("Notif : "+e);
//                } finally{
//                    if(rs!=null){
//                        rs.close();
//                    }
//                    if(ps!=null){
//                        ps.close();
//                    }
//                }
//            } catch (Exception e) {
//                System.out.println("Notif : "+e);
//            }
        }
    }

    private void isRawat() {
        Sequel.cariIsi("select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat=? ",TNoRM,TNoRw.getText());
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
    
    private void isPsien() {
        Sequel.cariIsi("select pasien.nm_pasien from pasien where pasien.no_rkm_medis=? ",TPasien,TNoRM.getText());
        Sequel.cariIsi("select if(jk='L','Laki-Laki','Perempuan') from pasien where no_rkm_medis=? ",Jk,TNoRM.getText());
        Sequel.cariIsi("select tgl_lahir from pasien where no_rkm_medis=? ",TglLahir,TNoRM.getText());
    }
    
    public void setNoRm(String norwt, Date tgl2) {
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        Sequel.cariIsi("select reg_periksa.tgl_registrasi from reg_periksa where reg_periksa.no_rawat='"+norwt+"'", DTPCari1);
        DTPCari2.setDate(tgl2);    
        isRawat(); 
        isPsien();
    }
    
    
    public void isCek(){
        BtnSimpan.setEnabled(akses.getdata_resume_pasien());
        BtnHapus.setEnabled(akses.getdata_resume_pasien());
        BtnEdit.setEnabled(akses.getdata_resume_pasien());
        BtnEdit.setEnabled(akses.getdata_resume_pasien());
//        BtnTambahMasalah.setEnabled(akses.getmaster_masalah_keperawatan());  
        if(akses.getjml2()>=1){
            KdPetugas.setEditable(false);
            BtnDokter.setEnabled(false);
            KdPetugas.setText(akses.getkode());
            NmPetugas.setText(dokter.tampil3(KdPetugas.getText()));
            if(NmPetugas.getText().equals("")){
                KdPetugas.setText("");
                JOptionPane.showMessageDialog(null,"User login bukan Dokter...!!");
            }
        }            
    }

    public void setTampil(){
       TabRawat.setSelectedIndex(1);
    }
    
//    private void tampilMasalah() {
//        try{
//            jml=0;
//            for(i=0;i<tbMasalahKeperawatan.getRowCount();i++){
//                if(tbMasalahKeperawatan.getValueAt(i,0).toString().equals("true")){
//                    jml++;
//                }
//            }
//
//            pilih=null;
//            pilih=new boolean[jml]; 
//            kode=null;
//            kode=new String[jml];
//            masalah=null;
//            masalah=new String[jml];
//
//            index=0;        
//            for(i=0;i<tbMasalahKeperawatan.getRowCount();i++){
//                if(tbMasalahKeperawatan.getValueAt(i,0).toString().equals("true")){
//                    pilih[index]=true;
//                    kode[index]=tbMasalahKeperawatan.getValueAt(i,1).toString();
//                    masalah[index]=tbMasalahKeperawatan.getValueAt(i,2).toString();
//                    index++;
//                }
//            } 
//
//            Valid.tabelKosong(tabModeMasalah);
//
//            for(i=0;i<jml;i++){
//                tabModeMasalah.addRow(new Object[] {
//                    pilih[i],kode[i],masalah[i]
//                });
//            }
//            ps=koneksi.prepareStatement("select * from master_masalah_keperawatan where kode_masalah like ? or nama_masalah like ? order by kode_masalah");
//            try {
//                ps.setString(1,"%"+TCariMasalah.getText().trim()+"%");
//                ps.setString(2,"%"+TCariMasalah.getText().trim()+"%");
//                rs=ps.executeQuery();
//                while(rs.next()){
//                    tabModeMasalah.addRow(new Object[]{false,rs.getString(1),rs.getString(2)});
//                }
//            } catch (Exception e) {
//                System.out.println("Notif : "+e);
//            } finally{
//                if(rs!=null){
//                    rs.close();
//                }
//                if(ps!=null){
//                    ps.close();
//                }
//            }
//        }catch(Exception e){
//            System.out.println("Notifikasi : "+e);
//        }
//    }
    
//    private void isMenu(){
//        if(ChkAccor.isSelected()==true){
//            ChkAccor.setVisible(false);
//            PanelAccor.setPreferredSize(new Dimension(470,HEIGHT));
//            FormMenu.setVisible(true);  
//            FormMasalahRencana.setVisible(true);  
//            ChkAccor.setVisible(true);
//        }else if(ChkAccor.isSelected()==false){   
//            ChkAccor.setVisible(false);
//            PanelAccor.setPreferredSize(new Dimension(15,HEIGHT));
//            FormMenu.setVisible(false);  
//            FormMasalahRencana.setVisible(false);   
//            ChkAccor.setVisible(true);
//        }
//    }

//    private void getMasalah() {
//        if(tbObat.getSelectedRow()!= -1){
//            TNoRM1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
//            TPasien1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString()); 
//            DetailRencana.setText(tbObat.getValueAt(tbObat.getSelectedRow(),62).toString());
//            try {
//                Valid.tabelKosong(tabModeDetailMasalah);
//                ps=koneksi.prepareStatement(
//                        "select asesmen_medis_ranap_neonatus.tata from asesmen_medis_ranap_neonatus "+
//                        "where asesmen_medis_ranap_neonatus.no_rawat=? ");
//                try {
//                    ps.setString(1,tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
//                    rs=ps.executeQuery();
//                    while(rs.next()){
//                        tabModeDetailMasalah.addRow(new Object[]{rs.getString(1),rs.getString(2)});
//                    }
//                } catch (Exception e) {
//                    System.out.println("Notif : "+e);
//                } finally{
//                    if(rs!=null){
//                        rs.close();
//                    }
//                    if(ps!=null){
//                        ps.close();
//                    }
//                }
//            } catch (Exception e) {
//                System.out.println("Notif : "+e);
//            }
//        }
//    }
    
//    private void isBMI(){
//        if((!TB.getText().equals(""))&&(!BB.getText().equals(""))){
//            BMI.setText(Valid.SetAngka7(Valid.SetAngka(BB.getText())/((Valid.SetAngka(TB.getText())/100)*(Valid.SetAngka(TB.getText())/100)))+"");
//        }
//    }
}

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
import kepegawaian.DlgCariPetugas;


/**
 *
 * @author perpustakaan
 */
public final class RMPenilaianAwalKeperawatanKebidananRanap extends javax.swing.JDialog {
    private final DefaultTableModel tabMode,tabModeRiwayatKehamilan,tabModeRiwayatKehamilan2;
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
    public RMPenilaianAwalKeperawatanKebidananRanap(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        DlgRiwayatPersalinan.setSize(650,192);
        
        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.RM","Nama Pasien","J.K.","Agama","Bahasa","Cacat Fisik","Tgl.Lahir","Tgl.Asuhan","Informasi","TD","Nadi","RR","Suhu",
            "GCS","BB","TB","LILA","BMI","TFU","TBJ","Letak","Presentasi","Penurunan","Kontraksi/HIS","Kekuatan","Lamanya","BJJ","Keterangan BJJ",
            "Portio","Pembukaan Serviks","Ketuban","Hodge","Inspekulo","Hasil Inspekulo","CTG","Hasil CTG","USG","Hasil USG","Laboratorium",
            "Hasil Laboratorium","Lakmus","Hasil Lakmus","Pemeriksaan Panggul","Keluhan Utama","Menarche","Lamanya","Banyaknya","Haid Terakhir",
            "Siklus","Ket.Siklus","Masalah Menstruasi","Stts.Menikah","Kali","Usia Kw 1","Stts.Kawin 1","Usia Kw 2","Stts.Kawin 2","Usia Kw 3",
            "Stts.Kawin 3","HPHT","Usia Hamil","TP","Imunisasi","Jml.Imun","G","P","A","Hidup","Riwayat Ginekologi","Obat/Vitamin","Obat/Vitamin Diminum",
            "Merokok","Jml.Rokok","Alkohol","Jml.Alkohol","Obat Tidur","Riwayat KB","Lama KB","Komplikasi KB","Ket.Komplikasi KB","Berhenti KB",
            "Alasan Berhenti KB","Alat Bantu","Ket.Alat Bantu","Prothesa","Keterangan Prothesa","ADL","Stts.Psikologi","Ket.Stts Psikologi",
            "Hubungan Keluarga","Tinggal Dengan","Keterangan Tinggal","Ekonomi","Budaya","Keterangan Budaya","Edukasi","Keterangan Edukasi",
            "R.J.a.1","R.J.a.2","R.J.b","Hasil Penilaian Resiko Jatuh","Dilaporkan","Jam Lapor","Skrining Gizi 1","N1","Skrining Gizi 2","N2",
            "Total Skor","Kondisi Nyeri","Penyebab Nyeri","Ket.Peny Nyeri","Kualitas","Ket.Kualitas","Lokasi","Menyebar","Skala","Durasi",
            "Nyeri Hilang","Ket.Nyeri Hilang","Diberitahukan","Jam Diberitahukan","Masalah Kebidanan","Tindakan","NIP","Nama Petugas/Bidan"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 126; i++) {
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
                column.setPreferredWidth(40);
            }else if(i==19){
                column.setPreferredWidth(45);
            }else if(i==20){
                column.setPreferredWidth(45);
            }else if(i==21){
                column.setPreferredWidth(45);
            }else if(i==22){
                column.setPreferredWidth(59);
            }else if(i==23){
                column.setPreferredWidth(60);
            }else if(i==24){
                column.setPreferredWidth(74);
            }else if(i==25){
                column.setPreferredWidth(53);
            }else if(i==26){
                column.setPreferredWidth(51);
            }else if(i==27){
                column.setPreferredWidth(35);
            }else if(i==28){
                column.setPreferredWidth(83);
            }else if(i==29){
                column.setPreferredWidth(70);
            }else if(i==30){
                column.setPreferredWidth(101);
            }else if(i==31){
                column.setPreferredWidth(55);
            }else if(i==32){
                column.setPreferredWidth(45);
            }else if(i==33){
                column.setPreferredWidth(55);
            }else if(i==34){
                column.setPreferredWidth(100);
            }else if(i==35){
                column.setPreferredWidth(55);
            }else if(i==36){
                column.setPreferredWidth(100);
            }else if(i==37){
                column.setPreferredWidth(55);
            }else if(i==38){
                column.setPreferredWidth(100);
            }else if(i==39){
                column.setPreferredWidth(73);
            }else if(i==40){
                column.setPreferredWidth(100);
            }else if(i==41){
                column.setPreferredWidth(55);
            }else if(i==42){
                column.setPreferredWidth(80);
            }else if(i==43){
                column.setPreferredWidth(130);
            }else if(i==44){
                column.setPreferredWidth(200);
            }else if(i==45){
                column.setPreferredWidth(54);
            }else if(i==46){
                column.setPreferredWidth(50);
            }else if(i==47){
                column.setPreferredWidth(58);
            }else if(i==48){
                column.setPreferredWidth(73);
            }else if(i==49){
                column.setPreferredWidth(40);
            }else if(i==50){
                column.setPreferredWidth(70);
            }else if(i==51){
                column.setPreferredWidth(105);
            }else if(i==52){
                column.setPreferredWidth(70);
            }else if(i==53){
                column.setPreferredWidth(35);
            }else if(i==54){
                column.setPreferredWidth(54);
            }else if(i==55){
                column.setPreferredWidth(76);
            }else if(i==56){
                column.setPreferredWidth(54);
            }else if(i==57){
                column.setPreferredWidth(76);
            }else if(i==58){
                column.setPreferredWidth(54);
            }else if(i==59){
                column.setPreferredWidth(76);
            }else if(i==60){
                column.setPreferredWidth(65);
            }else if(i==61){
                column.setPreferredWidth(59);
            }else if(i==62){
                column.setPreferredWidth(65);
            }else if(i==63){
                column.setPreferredWidth(54);
            }else if(i==64){
                column.setPreferredWidth(52);
            }else if(i==65){
                column.setPreferredWidth(35);
            }else if(i==66){
                column.setPreferredWidth(35);
            }else if(i==67){
                column.setPreferredWidth(35);
            }else if(i==68){
                column.setPreferredWidth(36);
            }else if(i==69){
                column.setPreferredWidth(100);
            }else if(i==70){
                column.setPreferredWidth(73);
            }else if(i==71){
                column.setPreferredWidth(120);
            }else if(i==72){
                column.setPreferredWidth(48);
            }else if(i==73){
                column.setPreferredWidth(57);
            }else if(i==74){
                column.setPreferredWidth(43);
            }else if(i==75){
                column.setPreferredWidth(63);
            }else if(i==76){
                column.setPreferredWidth(60);
            }else if(i==77){
                column.setPreferredWidth(71);
            }else if(i==78){
                column.setPreferredWidth(49);
            }else if(i==79){
                column.setPreferredWidth(75);
            }else if(i==80){
                column.setPreferredWidth(94);
            }else if(i==81){
                column.setPreferredWidth(64);
            }else if(i==82){
                column.setPreferredWidth(100);
            }else if(i==83){
                column.setPreferredWidth(58);
            }else if(i==84){
                column.setPreferredWidth(100);
            }else if(i==85){
                column.setPreferredWidth(50);
            }else if(i==86){
                column.setPreferredWidth(109);
            }else if(i==87){
                column.setPreferredWidth(45);
            }else if(i==88){
                column.setPreferredWidth(72);
            }else if(i==89){
                column.setPreferredWidth(91);
            }else if(i==90){
                column.setPreferredWidth(102);
            }else if(i==91){
                column.setPreferredWidth(83);
            }else if(i==92){
                column.setPreferredWidth(102);
            }else if(i==93){
                column.setPreferredWidth(48);
            }else if(i==94){
                column.setPreferredWidth(54);
            }else if(i==95){
                column.setPreferredWidth(101);
            }else if(i==96){
                column.setPreferredWidth(48);
            }else if(i==97){
                column.setPreferredWidth(103);
            }else if(i==98){
                column.setPreferredWidth(40);
            }else if(i==99){
                column.setPreferredWidth(40);
            }else if(i==100){
                column.setPreferredWidth(40);
            }else if(i==101){
                column.setPreferredWidth(210);
            }else if(i==102){
                column.setPreferredWidth(60);
            }else if(i==103){
                column.setPreferredWidth(58);
            }else if(i==104){
                column.setPreferredWidth(76);
            }else if(i==105){
                column.setPreferredWidth(24);
            }else if(i==106){
                column.setPreferredWidth(76);
            }else if(i==107){
                column.setPreferredWidth(24);
            }else if(i==108){
                column.setPreferredWidth(56);
            }else if(i==109){
                column.setPreferredWidth(80);
            }else if(i==110){
                column.setPreferredWidth(83);
            }else if(i==111){
                column.setPreferredWidth(77);
            }else if(i==112){
                column.setPreferredWidth(85);
            }else if(i==113){
                column.setPreferredWidth(90);
            }else if(i==114){
                column.setPreferredWidth(90);
            }else if(i==115){
                column.setPreferredWidth(55);
            }else if(i==116){
                column.setPreferredWidth(33);
            }else if(i==117){
                column.setPreferredWidth(38);
            }else if(i==118){
                column.setPreferredWidth(66);
            }else if(i==119){
                column.setPreferredWidth(90);
            }else if(i==120){
                column.setPreferredWidth(75);
            }else if(i==121){
                column.setPreferredWidth(98);
            }else if(i==122){
                column.setPreferredWidth(150);
            }else if(i==123){
                column.setPreferredWidth(150);
            }else if(i==124){
                column.setPreferredWidth(90);
            }else if(i==125){
                column.setPreferredWidth(130);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeRiwayatKehamilan=new DefaultTableModel(null,new Object[]{
                "No","Tgl/Thn","Tempat Persalinan","Usia Hamil","Jenis Persalinan","Penolong","Penyulit","J.K.","BB/PB","Keadaan"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };

        
        tabModeRiwayatKehamilan2=new DefaultTableModel(null,new Object[]{
                "No","Tgl/Thn","Tempat Persalinan","Usia Hamil","Jenis Persalinan","Penolong","Penyulit","J.K.","BB/PB","Keadaan"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbRiwayatKehamilan1.setModel(tabModeRiwayatKehamilan2);

        tbRiwayatKehamilan1.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbRiwayatKehamilan1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 10; i++) {
            TableColumn column = tbRiwayatKehamilan1.getColumnModel().getColumn(i);
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
        tbRiwayatKehamilan1.setDefaultRenderer(Object.class, new WarnaTable());
        
        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        TempatPersalinan.setDocument(new batasInput((byte)30).getKata(TempatPersalinan));
        UsiaHamil.setDocument(new batasInput((byte)20).getKata(UsiaHamil));
        JenisPersalinan.setDocument(new batasInput((byte)20).getKata(JenisPersalinan));
        Penolong.setDocument(new batasInput((byte)30).getKata(Penolong));
        Penyulit.setDocument(new batasInput((byte)40).getKata(Penyulit));
        BBPB.setDocument(new batasInput((byte)10).getKata(BBPB));
        Keadaan.setDocument(new batasInput((byte)40).getKata(Keadaan));
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
        
        HTMLEditorKit kit = new HTMLEditorKit();
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
        LoadHTML.setEditable(true);
        
        
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
        DlgRiwayatPersalinan = new javax.swing.JDialog();
        internalFrame4 = new widget.InternalFrame();
        panelBiasa2 = new widget.PanelBiasa();
        TanggalPersalinan = new widget.Tanggal();
        jLabel99 = new widget.Label();
        BtnKeluarKehamilan = new widget.Button();
        jLabel106 = new widget.Label();
        UsiaHamil = new widget.TextBox();
        BtnSimpanRiwayatKehamilan = new widget.Button();
        jLabel107 = new widget.Label();
        TempatPersalinan = new widget.TextBox();
        jLabel105 = new widget.Label();
        JenisPersalinan = new widget.TextBox();
        jLabel108 = new widget.Label();
        JK = new widget.ComboBox();
        jLabel109 = new widget.Label();
        Penolong = new widget.TextBox();
        jLabel110 = new widget.Label();
        BBPB = new widget.TextBox();
        jLabel111 = new widget.Label();
        Penyulit = new widget.TextBox();
        jLabel112 = new widget.Label();
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
        KdPetugas = new widget.TextBox();
        NmPetugas = new widget.TextBox();
        BtnDokter = new widget.Button();
        jLabel8 = new widget.Label();
        TglLahir = new widget.TextBox();
        Jk = new widget.TextBox();
        jLabel10 = new widget.Label();
        label11 = new widget.Label();
        jLabel11 = new widget.Label();
        jLabel36 = new widget.Label();
        Informasi = new widget.ComboBox();
        TglAsuhan = new widget.Tanggal();
        jSeparator1 = new javax.swing.JSeparator();
        NmPetugas1 = new widget.TextBox();
        BtnDokter1 = new widget.Button();
        KdPetugas1 = new widget.TextBox();
        label15 = new widget.Label();
        label16 = new widget.Label();
        KdPetugas2 = new widget.TextBox();
        NmPetugas2 = new widget.TextBox();
        BtnDokter2 = new widget.Button();
        Informasi1 = new widget.ComboBox();
        jLabel37 = new widget.Label();
        Informasi2 = new widget.ComboBox();
        jLabel38 = new widget.Label();
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
        scrollPane9 = new widget.ScrollPane();
        tbRiwayatKehamilan1 = new widget.Table();
        scrollPane7 = new widget.ScrollPane();
        MasalahKebidanan = new widget.TextArea();
        scrollPane6 = new widget.ScrollPane();
        TindakanKebidanan = new widget.TextArea();

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N

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
        TanggalPersalinan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "10-03-2021" }));
        TanggalPersalinan.setDisplayFormat("dd-MM-yyyy");
        TanggalPersalinan.setName("TanggalPersalinan"); // NOI18N
        TanggalPersalinan.setOpaque(false);
        panelBiasa2.add(TanggalPersalinan);
        TanggalPersalinan.setBounds(530, 10, 95, 23);

        jLabel99.setText("Tempat Persalinan :");
        jLabel99.setName("jLabel99"); // NOI18N
        panelBiasa2.add(jLabel99);
        jLabel99.setBounds(0, 10, 110, 23);

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

        jLabel106.setText("Usia Hamil :");
        jLabel106.setName("jLabel106"); // NOI18N
        panelBiasa2.add(jLabel106);
        jLabel106.setBounds(430, 40, 96, 23);

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

        jLabel107.setText("Tanggal/Tahun :");
        jLabel107.setName("jLabel107"); // NOI18N
        panelBiasa2.add(jLabel107);
        jLabel107.setBounds(430, 10, 96, 23);

        TempatPersalinan.setHighlighter(null);
        TempatPersalinan.setName("TempatPersalinan"); // NOI18N
        TempatPersalinan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TempatPersalinanKeyPressed(evt);
            }
        });
        panelBiasa2.add(TempatPersalinan);
        TempatPersalinan.setBounds(114, 10, 290, 23);

        jLabel105.setText("Jenis Persalinan :");
        jLabel105.setName("jLabel105"); // NOI18N
        panelBiasa2.add(jLabel105);
        jLabel105.setBounds(0, 40, 110, 23);

        JenisPersalinan.setHighlighter(null);
        JenisPersalinan.setName("JenisPersalinan"); // NOI18N
        JenisPersalinan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JenisPersalinanKeyPressed(evt);
            }
        });
        panelBiasa2.add(JenisPersalinan);
        JenisPersalinan.setBounds(114, 40, 290, 23);

        jLabel108.setText("Jenis Kelamin :");
        jLabel108.setName("jLabel108"); // NOI18N
        panelBiasa2.add(jLabel108);
        jLabel108.setBounds(430, 70, 96, 23);

        JK.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Laki-Laki", "Perempuan" }));
        JK.setName("JK"); // NOI18N
        JK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JKKeyPressed(evt);
            }
        });
        panelBiasa2.add(JK);
        JK.setBounds(530, 70, 95, 23);

        jLabel109.setText("Penolong :");
        jLabel109.setName("jLabel109"); // NOI18N
        panelBiasa2.add(jLabel109);
        jLabel109.setBounds(0, 70, 110, 23);

        Penolong.setHighlighter(null);
        Penolong.setName("Penolong"); // NOI18N
        Penolong.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenolongKeyPressed(evt);
            }
        });
        panelBiasa2.add(Penolong);
        Penolong.setBounds(114, 70, 290, 23);

        jLabel110.setText("BB/PB :");
        jLabel110.setName("jLabel110"); // NOI18N
        panelBiasa2.add(jLabel110);
        jLabel110.setBounds(430, 100, 96, 23);

        BBPB.setHighlighter(null);
        BBPB.setName("BBPB"); // NOI18N
        BBPB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BBPBKeyPressed(evt);
            }
        });
        panelBiasa2.add(BBPB);
        BBPB.setBounds(530, 100, 95, 23);

        jLabel111.setText("Penyulit :");
        jLabel111.setName("jLabel111"); // NOI18N
        panelBiasa2.add(jLabel111);
        jLabel111.setBounds(0, 100, 110, 23);

        Penyulit.setHighlighter(null);
        Penyulit.setName("Penyulit"); // NOI18N
        Penyulit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenyulitKeyPressed(evt);
            }
        });
        panelBiasa2.add(Penyulit);
        Penyulit.setBounds(114, 100, 290, 23);

        jLabel112.setText("Keadaan :");
        jLabel112.setName("jLabel112"); // NOI18N
        panelBiasa2.add(jLabel112);
        jLabel112.setBounds(0, 130, 110, 23);

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
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Penilaian Awal Pasien Rawat Inap Kebidanan & Kandungan ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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
        FormInput.setPreferredSize(new java.awt.Dimension(870, 1763));
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

        label14.setText("Pengkaji 1 :");
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
        NmPetugas.setBounds(176, 40, 210, 23);

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
        BtnDokter.setBounds(388, 40, 28, 23);

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
        label11.setBounds(640, 70, 70, 23);

        jLabel11.setText("J.K. :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(740, 10, 30, 23);

        jLabel36.setText("Anamnesis :");
        jLabel36.setName("jLabel36"); // NOI18N
        FormInput.add(jLabel36);
        jLabel36.setBounds(0, 100, 70, 23);

        Informasi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Autoanamnesis", "Alloanamnesis" }));
        Informasi.setName("Informasi"); // NOI18N
        Informasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                InformasiKeyPressed(evt);
            }
        });
        FormInput.add(Informasi);
        Informasi.setBounds(74, 100, 150, 23);

        TglAsuhan.setForeground(new java.awt.Color(50, 70, 50));
        TglAsuhan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "10-03-2021 16:03:55" }));
        TglAsuhan.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        TglAsuhan.setName("TglAsuhan"); // NOI18N
        TglAsuhan.setOpaque(false);
        TglAsuhan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglAsuhanKeyPressed(evt);
            }
        });
        FormInput.add(TglAsuhan);
        TglAsuhan.setBounds(714, 70, 140, 23);

        jSeparator1.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator1.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator1.setName("jSeparator1"); // NOI18N
        FormInput.add(jSeparator1);
        jSeparator1.setBounds(0, 130, 880, 1);

        NmPetugas1.setEditable(false);
        NmPetugas1.setName("NmPetugas1"); // NOI18N
        NmPetugas1.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmPetugas1);
        NmPetugas1.setBounds(614, 40, 210, 23);

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
        BtnDokter1.setBounds(826, 40, 28, 23);

        KdPetugas1.setEditable(false);
        KdPetugas1.setName("KdPetugas1"); // NOI18N
        KdPetugas1.setPreferredSize(new java.awt.Dimension(80, 23));
        KdPetugas1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdPetugas1KeyPressed(evt);
            }
        });
        FormInput.add(KdPetugas1);
        KdPetugas1.setBounds(512, 40, 100, 23);

        label15.setText("Pengkaji 2 :");
        label15.setName("label15"); // NOI18N
        label15.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label15);
        label15.setBounds(438, 40, 70, 23);

        label16.setText("DPJP :");
        label16.setName("label16"); // NOI18N
        label16.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label16);
        label16.setBounds(0, 70, 70, 23);

        KdPetugas2.setEditable(false);
        KdPetugas2.setName("KdPetugas2"); // NOI18N
        KdPetugas2.setPreferredSize(new java.awt.Dimension(80, 23));
        KdPetugas2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdPetugas2KeyPressed(evt);
            }
        });
        FormInput.add(KdPetugas2);
        KdPetugas2.setBounds(74, 70, 130, 23);

        NmPetugas2.setEditable(false);
        NmPetugas2.setName("NmPetugas2"); // NOI18N
        NmPetugas2.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmPetugas2);
        NmPetugas2.setBounds(206, 70, 360, 23);

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
        BtnDokter2.setBounds(568, 70, 28, 23);

        Informasi1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Jalan Tanpa Bantuan", "Kursi Roda", "Brankar" }));
        Informasi1.setName("Informasi1"); // NOI18N
        Informasi1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Informasi1KeyPressed(evt);
            }
        });
        FormInput.add(Informasi1);
        Informasi1.setBounds(370, 100, 220, 23);

        jLabel37.setText("Tiba di Ruang Rawat :");
        jLabel37.setName("jLabel37"); // NOI18N
        FormInput.add(jLabel37);
        jLabel37.setBounds(220, 100, 130, 23);

        Informasi2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Poli", "IGD", "VK", "OK", "Lain-lain" }));
        Informasi2.setName("Informasi2"); // NOI18N
        Informasi2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Informasi2KeyPressed(evt);
            }
        });
        FormInput.add(Informasi2);
        Informasi2.setBounds(743, 100, 110, 23);

        jLabel38.setText("Cara Masuk :");
        jLabel38.setName("jLabel38"); // NOI18N
        FormInput.add(jLabel38);
        jLabel38.setBounds(650, 100, 90, 23);

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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "10-03-2021" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "10-03-2021" }));
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
        FormMasalahRencana.setLayout(new java.awt.GridLayout(3, 0, 1, 1));

        scrollPane9.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 254)), "Riwayat Persalinan :", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        scrollPane9.setName("scrollPane9"); // NOI18N

        tbRiwayatKehamilan1.setName("tbRiwayatKehamilan1"); // NOI18N
        scrollPane9.setViewportView(tbRiwayatKehamilan1);

        FormMasalahRencana.add(scrollPane9);

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
        
    
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        
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
            if(Sequel.queryu2tf("delete from penilaian_awal_keperawatan_kebidanan where no_rawat=?",1,new String[]{
                tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
            })==true){
                TNoRM1.setText("");
                TPasien1.setText("");
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
                ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,pasien.agama,bahasa_pasien.nama_bahasa,cacat_fisik.nama_cacat,penilaian_awal_keperawatan_kebidanan.tanggal,"+
                    "penilaian_awal_keperawatan_kebidanan.informasi,penilaian_awal_keperawatan_kebidanan.td,penilaian_awal_keperawatan_kebidanan.nadi,penilaian_awal_keperawatan_kebidanan.rr,penilaian_awal_keperawatan_kebidanan.suhu,penilaian_awal_keperawatan_kebidanan.bb,"+
                    "penilaian_awal_keperawatan_kebidanan.tb,penilaian_awal_keperawatan_kebidanan.nadi,penilaian_awal_keperawatan_kebidanan.rr,penilaian_awal_keperawatan_kebidanan.suhu,penilaian_awal_keperawatan_kebidanan.gcs,penilaian_awal_keperawatan_kebidanan.bb,"+
                    "penilaian_awal_keperawatan_kebidanan.tb,penilaian_awal_keperawatan_kebidanan.bmi,penilaian_awal_keperawatan_kebidanan.lila,penilaian_awal_keperawatan_kebidanan.tfu,penilaian_awal_keperawatan_kebidanan.tbj,penilaian_awal_keperawatan_kebidanan.letak,"+
                    "penilaian_awal_keperawatan_kebidanan.presentasi,penilaian_awal_keperawatan_kebidanan.penurunan,penilaian_awal_keperawatan_kebidanan.his,penilaian_awal_keperawatan_kebidanan.kekuatan,penilaian_awal_keperawatan_kebidanan.lamanya,penilaian_awal_keperawatan_kebidanan.bjj,"+
                    "penilaian_awal_keperawatan_kebidanan.ket_bjj,penilaian_awal_keperawatan_kebidanan.portio,penilaian_awal_keperawatan_kebidanan.serviks,penilaian_awal_keperawatan_kebidanan.ketuban,penilaian_awal_keperawatan_kebidanan.hodge,penilaian_awal_keperawatan_kebidanan.inspekulo,"+
                    "penilaian_awal_keperawatan_kebidanan.ket_inspekulo,penilaian_awal_keperawatan_kebidanan.ctg,penilaian_awal_keperawatan_kebidanan.ket_ctg,penilaian_awal_keperawatan_kebidanan.usg,penilaian_awal_keperawatan_kebidanan.ket_usg,penilaian_awal_keperawatan_kebidanan.lab,"+
                    "penilaian_awal_keperawatan_kebidanan.ket_lab,penilaian_awal_keperawatan_kebidanan.lakmus,penilaian_awal_keperawatan_kebidanan.ket_lakmus,penilaian_awal_keperawatan_kebidanan.panggul,penilaian_awal_keperawatan_kebidanan.keluhan_utama,penilaian_awal_keperawatan_kebidanan.umur,"+
                    "penilaian_awal_keperawatan_kebidanan.lama,penilaian_awal_keperawatan_kebidanan.banyaknya,penilaian_awal_keperawatan_kebidanan.haid,penilaian_awal_keperawatan_kebidanan.siklus,penilaian_awal_keperawatan_kebidanan.ket_siklus,penilaian_awal_keperawatan_kebidanan.ket_siklus1,"+
                    "penilaian_awal_keperawatan_kebidanan.status,penilaian_awal_keperawatan_kebidanan.kali,penilaian_awal_keperawatan_kebidanan.usia1,penilaian_awal_keperawatan_kebidanan.ket1,penilaian_awal_keperawatan_kebidanan.usia2,penilaian_awal_keperawatan_kebidanan.ket2,"+
                    "penilaian_awal_keperawatan_kebidanan.usia3,penilaian_awal_keperawatan_kebidanan.ket3,penilaian_awal_keperawatan_kebidanan.hpht,penilaian_awal_keperawatan_kebidanan.usia_kehamilan,penilaian_awal_keperawatan_kebidanan.tp,penilaian_awal_keperawatan_kebidanan.imunisasi,"+
                    "penilaian_awal_keperawatan_kebidanan.ket_imunisasi,penilaian_awal_keperawatan_kebidanan.g,penilaian_awal_keperawatan_kebidanan.p,penilaian_awal_keperawatan_kebidanan.a,penilaian_awal_keperawatan_kebidanan.hidup,penilaian_awal_keperawatan_kebidanan.ginekologi,"+
                    "penilaian_awal_keperawatan_kebidanan.kebiasaan,penilaian_awal_keperawatan_kebidanan.ket_kebiasaan,penilaian_awal_keperawatan_kebidanan.kebiasaan1,penilaian_awal_keperawatan_kebidanan.ket_kebiasaan1,penilaian_awal_keperawatan_kebidanan.kebiasaan2,"+
                    "penilaian_awal_keperawatan_kebidanan.ket_kebiasaan2,penilaian_awal_keperawatan_kebidanan.kebiasaan3,penilaian_awal_keperawatan_kebidanan.kb,penilaian_awal_keperawatan_kebidanan.ket_kb,penilaian_awal_keperawatan_kebidanan.komplikasi,"+
                    "penilaian_awal_keperawatan_kebidanan.ket_komplikasi,penilaian_awal_keperawatan_kebidanan.berhenti,penilaian_awal_keperawatan_kebidanan.alasan,penilaian_awal_keperawatan_kebidanan.alat_bantu,penilaian_awal_keperawatan_kebidanan.ket_bantu,"+
                    "penilaian_awal_keperawatan_kebidanan.prothesa,penilaian_awal_keperawatan_kebidanan.ket_pro,penilaian_awal_keperawatan_kebidanan.adl,penilaian_awal_keperawatan_kebidanan.status_psiko,penilaian_awal_keperawatan_kebidanan.ket_psiko,"+
                    "penilaian_awal_keperawatan_kebidanan.hub_keluarga,penilaian_awal_keperawatan_kebidanan.tinggal_dengan,penilaian_awal_keperawatan_kebidanan.ket_tinggal,penilaian_awal_keperawatan_kebidanan.ekonomi,penilaian_awal_keperawatan_kebidanan.budaya,"+
                    "penilaian_awal_keperawatan_kebidanan.ket_budaya,penilaian_awal_keperawatan_kebidanan.edukasi,penilaian_awal_keperawatan_kebidanan.ket_edukasi,penilaian_awal_keperawatan_kebidanan.berjalan_a,penilaian_awal_keperawatan_kebidanan.berjalan_b,"+
                    "penilaian_awal_keperawatan_kebidanan.berjalan_c,penilaian_awal_keperawatan_kebidanan.hasil,penilaian_awal_keperawatan_kebidanan.lapor,penilaian_awal_keperawatan_kebidanan.ket_lapor,penilaian_awal_keperawatan_kebidanan.sg1,"+
                    "penilaian_awal_keperawatan_kebidanan.nilai1,penilaian_awal_keperawatan_kebidanan.sg2,penilaian_awal_keperawatan_kebidanan.nilai2,penilaian_awal_keperawatan_kebidanan.total_hasil,penilaian_awal_keperawatan_kebidanan.nyeri,"+
                    "penilaian_awal_keperawatan_kebidanan.provokes,penilaian_awal_keperawatan_kebidanan.ket_provokes,penilaian_awal_keperawatan_kebidanan.quality,penilaian_awal_keperawatan_kebidanan.ket_quality,penilaian_awal_keperawatan_kebidanan.lokasi,"+
                    "penilaian_awal_keperawatan_kebidanan.menyebar,penilaian_awal_keperawatan_kebidanan.skala_nyeri,penilaian_awal_keperawatan_kebidanan.durasi,penilaian_awal_keperawatan_kebidanan.nyeri_hilang,penilaian_awal_keperawatan_kebidanan.ket_nyeri,"+
                    "penilaian_awal_keperawatan_kebidanan.pada_dokter,penilaian_awal_keperawatan_kebidanan.ket_dokter,penilaian_awal_keperawatan_kebidanan.masalah,penilaian_awal_keperawatan_kebidanan.tindakan,penilaian_awal_keperawatan_kebidanan.nip,petugas.nama "+
                    "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join penilaian_awal_keperawatan_kebidanan on reg_periksa.no_rawat=penilaian_awal_keperawatan_kebidanan.no_rawat "+
                    "inner join petugas on penilaian_awal_keperawatan_kebidanan.nip=petugas.nip "+
                    "inner join bahasa_pasien on bahasa_pasien.id=pasien.bahasa_pasien "+
                    "inner join cacat_fisik on cacat_fisik.id=pasien.cacat_fisik where "+
                    "penilaian_awal_keperawatan_kebidanan.tanggal between ? and ? "+
                     (TCari.getText().trim().equals("")?"":"and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or penilaian_awal_keperawatan_kebidanan.nip like ? or  petugas.nama like ?)")+
                     " order by penilaian_awal_keperawatan_kebidanan.tanggal");
            
                try {
                    ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                    ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                    if(!TCari.getText().equals("")){
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
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='10%'><b>PASIEN & PETUGAS</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='14%'><b>II. PEMERIKSAAN KEBIDANAN</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='17%'><b>III. RIWAYAT KESEHATAN</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='22%'><b>IV. FUNGSIONAL</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='19%'><b>VI. PENILAIAN RESIKO JATUH</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='18%'><b>VIII. PENILAIAN TINGKAT NYERI</b></td>"+
                        "</tr>"
                    );
                    while(rs.next()){
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
                                        "<tr class='isi2'>"+
                                            "<td valign='top' align='center' colspan='3'><b>I. KEADAAN UMUM</b></td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='34%' valign='top'>TD</td><td valign='top'>:&nbsp;</td><td width='65%' valign='top'>"+rs.getString("td")+" mmHg</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='34%' valign='top'>Nadi</td><td valign='top'>:&nbsp;</td><td width='65%' valign='top'>"+rs.getString("nadi")+" x/menit</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='34%' valign='top'>RR</td><td valign='top'>:&nbsp;</td><td width='65%' valign='top'>"+rs.getString("rr")+" x/menit</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='34%' valign='top'>Suhu</td><td valign='top'>:&nbsp;</td><td width='65%' valign='top'>"+rs.getString("suhu")+" °C</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='34%' valign='top'>GCS</td><td valign='top'>:&nbsp;</td><td width='65%' valign='top'>"+rs.getString("gcs")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='34%' valign='top'>BB</td><td valign='top'>:&nbsp;</td><td width='65%' valign='top'>"+rs.getString("bb")+" Kg</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='34%' valign='top'>TB</td><td valign='top'>:&nbsp;</td><td width='65%' valign='top'>"+rs.getString("tb")+" cm</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='34%' valign='top'>LILA</td><td valign='top'>:&nbsp;</td><td width='65%' valign='top'>"+rs.getString("lila")+" cm</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='34%' valign='top'>BMI</td><td valign='top'>:&nbsp;</td><td width='65%' valign='top'>"+rs.getString("bmi")+" Kg/m²</td>"+
                                        "</tr>"+
                                    "</table>"+
                                "</td>"+
                                "<td valign='top' cellpadding='0' cellspacing='0'>"+
                                    "<table width='100%' border='0' cellpadding='0' cellspacing='0'align='center'>"+
                                        "<tr class='isi2'>"+
                                            "<td width='44%' valign='top'>TFU</td><td valign='top'>:&nbsp;</td><td width='55%' valign='top'>"+rs.getString("tfu")+" cm</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='44%' valign='top'>TBJ</td><td valign='top'>:&nbsp;</td><td width='55%' valign='top'>"+rs.getString("tbj")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='44%' valign='top'>Letak</td><td valign='top'>:&nbsp;</td><td width='55%' valign='top'>"+rs.getString("letak")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='44%' valign='top'>Presentasi</td><td valign='top'>:&nbsp;</td><td width='55%' valign='top'>"+rs.getString("presentasi")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='44%' valign='top'>Penurunan</td><td valign='top'>:&nbsp;</td><td width='55%' valign='top'>"+rs.getString("penurunan")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='44%' valign='top'>Kontraksi/HIS</td><td valign='top'>:&nbsp;</td><td width='55%' valign='top'>"+rs.getString("his")+" x/10’</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='44%' valign='top'>Kekuatan</td><td valign='top'>:&nbsp;</td><td width='55%' valign='top'>"+rs.getString("kekuatan")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='44%' valign='top'>Lamanya</td><td valign='top'>:&nbsp;</td><td width='55%' valign='top'>"+rs.getString("lamanya")+" detik</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='44%' valign='top'>Gerak janin, BJJ </td><td valign='top'>:&nbsp;</td><td width='55%' valign='top'>"+rs.getString("bjj")+" /mnt</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='44%' valign='top'>Status</td><td valign='top'>:&nbsp;</td><td width='55%' valign='top'>"+rs.getString("ket_bjj")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='44%' valign='top'>Portio</td><td valign='top'>:&nbsp;</td><td width='55%' valign='top'>"+rs.getString("portio")+" detik</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='44%' valign='top'>Pembukaan Serviks</td><td valign='top'>:&nbsp;</td><td width='55%' valign='top'>"+rs.getString("serviks")+" cm </td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='44%' valign='top'>Ketuban</td><td valign='top'>:&nbsp;</td><td width='55%' valign='top'>"+rs.getString("ketuban")+" kep/bok</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='44%' valign='top'>Hodge</td><td valign='top'>:&nbsp;</td><td width='55%' valign='top'>"+rs.getString("hodge")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='44%' valign='top'>Inspekulo</td><td valign='top'>:&nbsp;</td><td width='55%' valign='top'>"+rs.getString("inspekulo")+", Hasil "+rs.getString("ket_inspekulo")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='44%' valign='top'>CTG</td><td valign='top'>:&nbsp;</td><td width='55%' valign='top'>"+rs.getString("ctg")+", Hasil "+rs.getString("ket_ctg")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='44%' valign='top'>USG</td><td valign='top'>:&nbsp;</td><td width='55%' valign='top'>"+rs.getString("usg")+", Hasil "+rs.getString("ket_usg")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='44%' valign='top'>Laborat</td><td valign='top'>:&nbsp;</td><td width='55%' valign='top'>"+rs.getString("lab")+", Hasil "+rs.getString("ket_lab")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='44%' valign='top'>Lakmus</td><td valign='top'>:&nbsp;</td><td width='55%' valign='top'>"+rs.getString("lakmus")+", Hasil "+rs.getString("ket_lakmus")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='44%' valign='top'>Pemeriksaan Panggul</td><td valign='top'>:&nbsp;</td><td width='55%' valign='top'>"+rs.getString("panggul")+"</td>"+
                                        "</tr>"+
                                    "</table>"+
                                "</td>"+
                                "<td valign='top' cellpadding='0' cellspacing='0'>"+
                                    "<table width='100%' border='0' cellpadding='0' cellspacing='0'align='center'>"+
                                        "<tr class='isi2'>"+
                                            "<td width='34%' valign='top'>Keluhan Utama</td><td valign='top'>:&nbsp;</td><td width='65%' valign='top'>"+rs.getString("keluhan_utama")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='34%' valign='top'>Umur Menarche</td><td valign='top'>:&nbsp;</td><td width='65%' valign='top'>"+rs.getString("umur")+" tahun, lamanya "+rs.getString("lama")+" hari, banyaknya "+rs.getString("banyaknya")+" pembalut </td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='34%' valign='top'>Haid Terakhir</td><td valign='top'>:&nbsp;</td><td width='65%' valign='top'>"+rs.getString("haid")+", Siklus "+rs.getString("siklus")+" hari, ("+rs.getString("ket_siklus")+")</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='34%' valign='top'>Masalah Menstruasi</td><td valign='top'>:&nbsp;</td><td width='65%' valign='top'>"+rs.getString("ket_siklus1")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='34%' valign='top'>Status Menikah</td><td valign='top'>:&nbsp;</td><td width='65%' valign='top'>"+rs.getString("status")+", "+rs.getString("kali")+" kali</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='34%' valign='top'>Usia Perkawinan 1</td><td valign='top'>:&nbsp;</td><td width='65%' valign='top'>"+rs.getString("usia1")+" tahun, "+rs.getString("ket1")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='34%' valign='top'>Usia Perkawinan 2</td><td valign='top'>:&nbsp;</td><td width='65%' valign='top'>"+rs.getString("usia2")+" tahun, "+rs.getString("ket2")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='34%' valign='top'>Usia Perkawinan 3</td><td valign='top'>:&nbsp;</td><td width='65%' valign='top'>"+rs.getString("usia3")+" tahun, "+rs.getString("ket3")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='34%' valign='top'>HPHT</td><td valign='top'>:&nbsp;</td><td width='65%' valign='top'>"+rs.getString("hpht")+", Usia Hamil "+rs.getString("usia_kehamilan")+" bln/mgg</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='34%' valign='top'>TP</td><td valign='top'>:&nbsp;</td><td width='65%' valign='top'>"+rs.getString("tp")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='34%' valign='top'>Riwayat Imunisasi</td><td valign='top'>:&nbsp;</td><td width='65%' valign='top'>"+rs.getString("imunisasi")+", "+rs.getString("ket_imunisasi")+" kali</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='34%' valign='top'>G,P,A, Hidup</td><td valign='top'>:&nbsp;</td><td width='65%' valign='top'>"+rs.getString("g")+", "+rs.getString("p")+", "+rs.getString("a")+", "+rs.getString("hidup")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='34%' valign='top'>Riwayat KB</td><td valign='top'>:&nbsp;</td><td width='65%' valign='top'>"+rs.getString("kb")+", lamanya "+rs.getString("ket_kb")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='34%' valign='top'>Komplikasi KB</td><td valign='top'>:&nbsp;</td><td width='65%' valign='top'>"+rs.getString("komplikasi")+", "+rs.getString("ket_komplikasi")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='34%' valign='top'>Kapan Berhenti KB</td><td valign='top'>:&nbsp;</td><td width='65%' valign='top'>"+rs.getString("berhenti")+", Alasan : "+rs.getString("alasan")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='34%' valign='top'>Riwayat Ginekologi</td><td valign='top'>:&nbsp;</td><td width='65%' valign='top'>"+rs.getString("ginekologi")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='34%' valign='top'>Obat/Vitamin</td><td valign='top'>:&nbsp;</td><td width='65%' valign='top'>"+rs.getString("kebiasaan")+(rs.getString("ket_kebiasaan").equals("")?"":", "+rs.getString("ket_kebiasaan"))+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='34%' valign='top'>Merokok</td><td valign='top'>:&nbsp;</td><td width='65%' valign='top'>"+rs.getString("kebiasaan1")+(rs.getString("ket_kebiasaan1").equals("")?"":", "+rs.getString("ket_kebiasaan1"))+" batang/hari</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='34%' valign='top'>Alkohol</td><td valign='top'>:&nbsp;</td><td width='65%' valign='top'>"+rs.getString("kebiasaan2")+(rs.getString("ket_kebiasaan2").equals("")?"":", "+rs.getString("ket_kebiasaan2"))+" gelas/hari</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='34%' valign='top'>Obat Tidur/Narkoba</td><td valign='top'>:&nbsp;</td><td width='65%' valign='top'>"+rs.getString("kebiasaan3")+"</td>"+
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
                                        "<tr class='isi2'>"+
                                            "<td valign='top' align='center' colspan='3'><b>V. RIWAYAT PSIKO-SOSIAL SPIRITUAL DAN BUDAYA</b></td>"+
                                        "</tr>"+            
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
                                        "<tr class='isi2'>"+
                                            "<td valign='top' align='center' colspan='3'><b>VII. SKRINING GIZI</b></td>"+
                                        "</tr>"+ 
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
                                        "<tr class='isi2'>"+
                                            "<td valign='top' align='center' colspan='3'><b>MASALAH & TINDAKAN KEBIDANAN</b></td>"+
                                        "</tr>"+ 
                                        "<tr class='isi2'>"+
                                            "<td valign='top' colspan='3'>"+
                                                "Masalah Kebidanan : "+rs.getString("masalah")+"<br><br>"+
                                                "Tindakan Kebidanan : "+rs.getString("tindakan")+
                                            "</td>"+
                                        "</tr>"+ 
                                    "</table>"+
                                "</td>"+
                            "</tr>"
                        );
                        //  `masalah`, `tindakan`, `nip`
                    }
                    
                    LoadHTML.setText(
                        "<html>"+
                          "<table width='1900px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
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

                    File f = new File("DataPenilaianAwalKeperawatanKebidanan.html");            
                    BufferedWriter bw = new BufferedWriter(new FileWriter(f));            
                    bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                                "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                                "<table width='1900px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                    "<tr class='isi2'>"+
                                        "<td valign='top' align='center'>"+
                                            "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                            akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                            akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                            "<font size='2' face='Tahoma'>DATA PENILAIAN AWAL RAWAT JALAN KEBIDANAN & KANDUNGAN<br><br></font>"+        
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
            param.put("logo",Sequel.cariGambar("select logo from setting")); 
            param.put("nyeri",Sequel.cariGambar("select nyeri from gambar")); 
            param.put("finger",Sequel.cariIsi("select sha1(sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",tbObat.getValueAt(tbObat.getSelectedRow(),63).toString()));
            try {
                ps=koneksi.prepareStatement("select * from riwayat_persalinan_pasien where no_rkm_medis=? order by tgl_thn");
                try {
                    ps.setString(1,tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
                    rs=ps.executeQuery();
                    i=1;
                    while(rs.next()){
                        param.put("no"+i,i+"");  
                        param.put("tgl"+i,rs.getString("tgl_thn"));
                        param.put("tempatpersalinan"+i,rs.getString("tempat_persalinan"));
                        param.put("usiahamil"+i,rs.getString("usia_hamil"));
                        param.put("jenispersalinan"+i,rs.getString("jenis_persalinan"));
                        param.put("penolong"+i,rs.getString("penolong"));
                        param.put("penyulit"+i,rs.getString("penyulit"));
                        param.put("jk"+i,rs.getString("jk"));
                        param.put("bbpb"+i,rs.getString("bbpb"));
                        param.put("keadaan"+i,rs.getString("keadaan"));
                        i++;
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
            
            Valid.MyReportqry("rptCetakPenilaianAwalKebidananRalan.jasper","report","::[ Laporan Penilaian Awal Ralan Kebidanan & Kandungan ]::",
                "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,pasien.agama,bahasa_pasien.nama_bahasa,cacat_fisik.nama_cacat,penilaian_awal_keperawatan_kebidanan.tanggal,"+
                "penilaian_awal_keperawatan_kebidanan.informasi,penilaian_awal_keperawatan_kebidanan.td,penilaian_awal_keperawatan_kebidanan.nadi,penilaian_awal_keperawatan_kebidanan.rr,penilaian_awal_keperawatan_kebidanan.suhu,penilaian_awal_keperawatan_kebidanan.bb,"+
                "penilaian_awal_keperawatan_kebidanan.tb,penilaian_awal_keperawatan_kebidanan.nadi,penilaian_awal_keperawatan_kebidanan.rr,penilaian_awal_keperawatan_kebidanan.suhu,penilaian_awal_keperawatan_kebidanan.gcs,penilaian_awal_keperawatan_kebidanan.bb,"+
                "penilaian_awal_keperawatan_kebidanan.tb,penilaian_awal_keperawatan_kebidanan.bmi,penilaian_awal_keperawatan_kebidanan.lila,penilaian_awal_keperawatan_kebidanan.tfu,penilaian_awal_keperawatan_kebidanan.tbj,penilaian_awal_keperawatan_kebidanan.letak,"+
                "penilaian_awal_keperawatan_kebidanan.presentasi,penilaian_awal_keperawatan_kebidanan.penurunan,penilaian_awal_keperawatan_kebidanan.his,penilaian_awal_keperawatan_kebidanan.kekuatan,penilaian_awal_keperawatan_kebidanan.lamanya,penilaian_awal_keperawatan_kebidanan.bjj,"+
                "penilaian_awal_keperawatan_kebidanan.ket_bjj,penilaian_awal_keperawatan_kebidanan.portio,penilaian_awal_keperawatan_kebidanan.serviks,penilaian_awal_keperawatan_kebidanan.ketuban,penilaian_awal_keperawatan_kebidanan.hodge,penilaian_awal_keperawatan_kebidanan.inspekulo,"+
                "penilaian_awal_keperawatan_kebidanan.ket_inspekulo,penilaian_awal_keperawatan_kebidanan.ctg,penilaian_awal_keperawatan_kebidanan.ket_ctg,penilaian_awal_keperawatan_kebidanan.usg,penilaian_awal_keperawatan_kebidanan.ket_usg,penilaian_awal_keperawatan_kebidanan.lab,"+
                "penilaian_awal_keperawatan_kebidanan.ket_lab,penilaian_awal_keperawatan_kebidanan.lakmus,penilaian_awal_keperawatan_kebidanan.ket_lakmus,penilaian_awal_keperawatan_kebidanan.panggul,penilaian_awal_keperawatan_kebidanan.keluhan_utama,penilaian_awal_keperawatan_kebidanan.umur,"+
                "penilaian_awal_keperawatan_kebidanan.lama,penilaian_awal_keperawatan_kebidanan.banyaknya,penilaian_awal_keperawatan_kebidanan.haid,penilaian_awal_keperawatan_kebidanan.siklus,penilaian_awal_keperawatan_kebidanan.ket_siklus,penilaian_awal_keperawatan_kebidanan.ket_siklus1,"+
                "penilaian_awal_keperawatan_kebidanan.status,penilaian_awal_keperawatan_kebidanan.kali,penilaian_awal_keperawatan_kebidanan.usia1,penilaian_awal_keperawatan_kebidanan.ket1,penilaian_awal_keperawatan_kebidanan.usia2,penilaian_awal_keperawatan_kebidanan.ket2,"+
                "penilaian_awal_keperawatan_kebidanan.usia3,penilaian_awal_keperawatan_kebidanan.ket3,penilaian_awal_keperawatan_kebidanan.hpht,penilaian_awal_keperawatan_kebidanan.usia_kehamilan,penilaian_awal_keperawatan_kebidanan.tp,penilaian_awal_keperawatan_kebidanan.imunisasi,"+
                "penilaian_awal_keperawatan_kebidanan.ket_imunisasi,penilaian_awal_keperawatan_kebidanan.g,penilaian_awal_keperawatan_kebidanan.p,penilaian_awal_keperawatan_kebidanan.a,penilaian_awal_keperawatan_kebidanan.hidup,penilaian_awal_keperawatan_kebidanan.ginekologi,"+
                "penilaian_awal_keperawatan_kebidanan.kebiasaan,penilaian_awal_keperawatan_kebidanan.ket_kebiasaan,penilaian_awal_keperawatan_kebidanan.kebiasaan1,penilaian_awal_keperawatan_kebidanan.ket_kebiasaan1,penilaian_awal_keperawatan_kebidanan.kebiasaan2,"+
                "penilaian_awal_keperawatan_kebidanan.ket_kebiasaan2,penilaian_awal_keperawatan_kebidanan.kebiasaan3,penilaian_awal_keperawatan_kebidanan.kb,penilaian_awal_keperawatan_kebidanan.ket_kb,penilaian_awal_keperawatan_kebidanan.komplikasi,"+
                "penilaian_awal_keperawatan_kebidanan.ket_komplikasi,penilaian_awal_keperawatan_kebidanan.berhenti,penilaian_awal_keperawatan_kebidanan.alasan,penilaian_awal_keperawatan_kebidanan.alat_bantu,penilaian_awal_keperawatan_kebidanan.ket_bantu,"+
                "penilaian_awal_keperawatan_kebidanan.prothesa,penilaian_awal_keperawatan_kebidanan.ket_pro,penilaian_awal_keperawatan_kebidanan.adl,penilaian_awal_keperawatan_kebidanan.status_psiko,penilaian_awal_keperawatan_kebidanan.ket_psiko,"+
                "penilaian_awal_keperawatan_kebidanan.hub_keluarga,penilaian_awal_keperawatan_kebidanan.tinggal_dengan,penilaian_awal_keperawatan_kebidanan.ket_tinggal,penilaian_awal_keperawatan_kebidanan.ekonomi,penilaian_awal_keperawatan_kebidanan.budaya,"+
                "penilaian_awal_keperawatan_kebidanan.ket_budaya,penilaian_awal_keperawatan_kebidanan.edukasi,penilaian_awal_keperawatan_kebidanan.ket_edukasi,penilaian_awal_keperawatan_kebidanan.berjalan_a,penilaian_awal_keperawatan_kebidanan.berjalan_b,"+
                "penilaian_awal_keperawatan_kebidanan.berjalan_c,penilaian_awal_keperawatan_kebidanan.hasil,penilaian_awal_keperawatan_kebidanan.lapor,penilaian_awal_keperawatan_kebidanan.ket_lapor,penilaian_awal_keperawatan_kebidanan.sg1,"+
                "penilaian_awal_keperawatan_kebidanan.nilai1,penilaian_awal_keperawatan_kebidanan.sg2,penilaian_awal_keperawatan_kebidanan.nilai2,penilaian_awal_keperawatan_kebidanan.total_hasil,penilaian_awal_keperawatan_kebidanan.nyeri,"+
                "penilaian_awal_keperawatan_kebidanan.provokes,penilaian_awal_keperawatan_kebidanan.ket_provokes,penilaian_awal_keperawatan_kebidanan.quality,penilaian_awal_keperawatan_kebidanan.ket_quality,penilaian_awal_keperawatan_kebidanan.lokasi,"+
                "penilaian_awal_keperawatan_kebidanan.menyebar,penilaian_awal_keperawatan_kebidanan.skala_nyeri,penilaian_awal_keperawatan_kebidanan.durasi,penilaian_awal_keperawatan_kebidanan.nyeri_hilang,penilaian_awal_keperawatan_kebidanan.ket_nyeri,"+
                "penilaian_awal_keperawatan_kebidanan.pada_dokter,penilaian_awal_keperawatan_kebidanan.ket_dokter,penilaian_awal_keperawatan_kebidanan.masalah,penilaian_awal_keperawatan_kebidanan.tindakan,penilaian_awal_keperawatan_kebidanan.nip,petugas.nama "+
                "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join penilaian_awal_keperawatan_kebidanan on reg_periksa.no_rawat=penilaian_awal_keperawatan_kebidanan.no_rawat "+
                "inner join petugas on penilaian_awal_keperawatan_kebidanan.nip=petugas.nip "+
                "inner join bahasa_pasien on bahasa_pasien.id=pasien.bahasa_pasien "+
                "inner join cacat_fisik on cacat_fisik.id=pasien.cacat_fisik where reg_periksa.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'",param);
            
            Valid.MyReportqry("rptCetakPenilaianAwalKebidananRalan2.jasper","report","::[ Laporan Penilaian Awal Ralan Kebidanan & Kandungan ]::",
                "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,pasien.agama,bahasa_pasien.nama_bahasa,cacat_fisik.nama_cacat,penilaian_awal_keperawatan_kebidanan.tanggal,"+
                "penilaian_awal_keperawatan_kebidanan.informasi,penilaian_awal_keperawatan_kebidanan.td,penilaian_awal_keperawatan_kebidanan.nadi,penilaian_awal_keperawatan_kebidanan.rr,penilaian_awal_keperawatan_kebidanan.suhu,penilaian_awal_keperawatan_kebidanan.bb,"+
                "penilaian_awal_keperawatan_kebidanan.tb,penilaian_awal_keperawatan_kebidanan.nadi,penilaian_awal_keperawatan_kebidanan.rr,penilaian_awal_keperawatan_kebidanan.suhu,penilaian_awal_keperawatan_kebidanan.gcs,penilaian_awal_keperawatan_kebidanan.bb,"+
                "penilaian_awal_keperawatan_kebidanan.tb,penilaian_awal_keperawatan_kebidanan.bmi,penilaian_awal_keperawatan_kebidanan.lila,penilaian_awal_keperawatan_kebidanan.tfu,penilaian_awal_keperawatan_kebidanan.tbj,penilaian_awal_keperawatan_kebidanan.letak,"+
                "penilaian_awal_keperawatan_kebidanan.presentasi,penilaian_awal_keperawatan_kebidanan.penurunan,penilaian_awal_keperawatan_kebidanan.his,penilaian_awal_keperawatan_kebidanan.kekuatan,penilaian_awal_keperawatan_kebidanan.lamanya,penilaian_awal_keperawatan_kebidanan.bjj,"+
                "penilaian_awal_keperawatan_kebidanan.ket_bjj,penilaian_awal_keperawatan_kebidanan.portio,penilaian_awal_keperawatan_kebidanan.serviks,penilaian_awal_keperawatan_kebidanan.ketuban,penilaian_awal_keperawatan_kebidanan.hodge,penilaian_awal_keperawatan_kebidanan.inspekulo,"+
                "penilaian_awal_keperawatan_kebidanan.ket_inspekulo,penilaian_awal_keperawatan_kebidanan.ctg,penilaian_awal_keperawatan_kebidanan.ket_ctg,penilaian_awal_keperawatan_kebidanan.usg,penilaian_awal_keperawatan_kebidanan.ket_usg,penilaian_awal_keperawatan_kebidanan.lab,"+
                "penilaian_awal_keperawatan_kebidanan.ket_lab,penilaian_awal_keperawatan_kebidanan.lakmus,penilaian_awal_keperawatan_kebidanan.ket_lakmus,penilaian_awal_keperawatan_kebidanan.panggul,penilaian_awal_keperawatan_kebidanan.keluhan_utama,penilaian_awal_keperawatan_kebidanan.umur,"+
                "penilaian_awal_keperawatan_kebidanan.lama,penilaian_awal_keperawatan_kebidanan.banyaknya,penilaian_awal_keperawatan_kebidanan.haid,penilaian_awal_keperawatan_kebidanan.siklus,penilaian_awal_keperawatan_kebidanan.ket_siklus,penilaian_awal_keperawatan_kebidanan.ket_siklus1,"+
                "penilaian_awal_keperawatan_kebidanan.status,penilaian_awal_keperawatan_kebidanan.kali,penilaian_awal_keperawatan_kebidanan.usia1,penilaian_awal_keperawatan_kebidanan.ket1,penilaian_awal_keperawatan_kebidanan.usia2,penilaian_awal_keperawatan_kebidanan.ket2,"+
                "penilaian_awal_keperawatan_kebidanan.usia3,penilaian_awal_keperawatan_kebidanan.ket3,penilaian_awal_keperawatan_kebidanan.hpht,penilaian_awal_keperawatan_kebidanan.usia_kehamilan,penilaian_awal_keperawatan_kebidanan.tp,penilaian_awal_keperawatan_kebidanan.imunisasi,"+
                "penilaian_awal_keperawatan_kebidanan.ket_imunisasi,penilaian_awal_keperawatan_kebidanan.g,penilaian_awal_keperawatan_kebidanan.p,penilaian_awal_keperawatan_kebidanan.a,penilaian_awal_keperawatan_kebidanan.hidup,penilaian_awal_keperawatan_kebidanan.ginekologi,"+
                "penilaian_awal_keperawatan_kebidanan.kebiasaan,penilaian_awal_keperawatan_kebidanan.ket_kebiasaan,penilaian_awal_keperawatan_kebidanan.kebiasaan1,penilaian_awal_keperawatan_kebidanan.ket_kebiasaan1,penilaian_awal_keperawatan_kebidanan.kebiasaan2,"+
                "penilaian_awal_keperawatan_kebidanan.ket_kebiasaan2,penilaian_awal_keperawatan_kebidanan.kebiasaan3,penilaian_awal_keperawatan_kebidanan.kb,penilaian_awal_keperawatan_kebidanan.ket_kb,penilaian_awal_keperawatan_kebidanan.komplikasi,"+
                "penilaian_awal_keperawatan_kebidanan.ket_komplikasi,penilaian_awal_keperawatan_kebidanan.berhenti,penilaian_awal_keperawatan_kebidanan.alasan,penilaian_awal_keperawatan_kebidanan.alat_bantu,penilaian_awal_keperawatan_kebidanan.ket_bantu,"+
                "penilaian_awal_keperawatan_kebidanan.prothesa,penilaian_awal_keperawatan_kebidanan.ket_pro,penilaian_awal_keperawatan_kebidanan.adl,penilaian_awal_keperawatan_kebidanan.status_psiko,penilaian_awal_keperawatan_kebidanan.ket_psiko,"+
                "penilaian_awal_keperawatan_kebidanan.hub_keluarga,penilaian_awal_keperawatan_kebidanan.tinggal_dengan,penilaian_awal_keperawatan_kebidanan.ket_tinggal,penilaian_awal_keperawatan_kebidanan.ekonomi,penilaian_awal_keperawatan_kebidanan.budaya,"+
                "penilaian_awal_keperawatan_kebidanan.ket_budaya,penilaian_awal_keperawatan_kebidanan.edukasi,penilaian_awal_keperawatan_kebidanan.ket_edukasi,penilaian_awal_keperawatan_kebidanan.berjalan_a,penilaian_awal_keperawatan_kebidanan.berjalan_b,"+
                "penilaian_awal_keperawatan_kebidanan.berjalan_c,penilaian_awal_keperawatan_kebidanan.hasil,penilaian_awal_keperawatan_kebidanan.lapor,penilaian_awal_keperawatan_kebidanan.ket_lapor,penilaian_awal_keperawatan_kebidanan.sg1,"+
                "penilaian_awal_keperawatan_kebidanan.nilai1,penilaian_awal_keperawatan_kebidanan.sg2,penilaian_awal_keperawatan_kebidanan.nilai2,penilaian_awal_keperawatan_kebidanan.total_hasil,penilaian_awal_keperawatan_kebidanan.nyeri,"+
                "penilaian_awal_keperawatan_kebidanan.provokes,penilaian_awal_keperawatan_kebidanan.ket_provokes,penilaian_awal_keperawatan_kebidanan.quality,penilaian_awal_keperawatan_kebidanan.ket_quality,penilaian_awal_keperawatan_kebidanan.lokasi,"+
                "penilaian_awal_keperawatan_kebidanan.menyebar,penilaian_awal_keperawatan_kebidanan.skala_nyeri,penilaian_awal_keperawatan_kebidanan.durasi,penilaian_awal_keperawatan_kebidanan.nyeri_hilang,penilaian_awal_keperawatan_kebidanan.ket_nyeri,"+
                "penilaian_awal_keperawatan_kebidanan.pada_dokter,penilaian_awal_keperawatan_kebidanan.ket_dokter,penilaian_awal_keperawatan_kebidanan.masalah,penilaian_awal_keperawatan_kebidanan.tindakan,penilaian_awal_keperawatan_kebidanan.nip,petugas.nama "+
                "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join penilaian_awal_keperawatan_kebidanan on reg_periksa.no_rawat=penilaian_awal_keperawatan_kebidanan.no_rawat "+
                "inner join petugas on penilaian_awal_keperawatan_kebidanan.nip=petugas.nip "+
                "inner join bahasa_pasien on bahasa_pasien.id=pasien.bahasa_pasien "+
                "inner join cacat_fisik on cacat_fisik.id=pasien.cacat_fisik where reg_periksa.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'",param);
        }else{
            JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data terlebih dahulu..!!!!");
        }  
    }//GEN-LAST:event_BtnPrint1ActionPerformed

    private void TindakanKebidananKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TindakanKebidananKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TindakanKebidananKeyPressed

    private void MasalahKebidananKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MasalahKebidananKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_MasalahKebidananKeyPressed

    private void TglAsuhanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglAsuhanKeyPressed
        //Valid.pindah(evt,Rencana,Informasi);
    }//GEN-LAST:event_TglAsuhanKeyPressed

    private void InformasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_InformasiKeyPressed
        
    }//GEN-LAST:event_InformasiKeyPressed

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

    private void BtnKeluarKehamilanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarKehamilanActionPerformed
        DlgRiwayatPersalinan.dispose();
    }//GEN-LAST:event_BtnKeluarKehamilanActionPerformed

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
                emptTeksPersalinan();
                tampilPersalinan();
            }
        }
    }//GEN-LAST:event_BtnSimpanRiwayatKehamilanActionPerformed

    private void JKKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JKKeyPressed
        Valid.pindah(evt,UsiaHamil,BBPB);
    }//GEN-LAST:event_JKKeyPressed

    private void TempatPersalinanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TempatPersalinanKeyPressed
        Valid.pindah(evt,BtnKeluarKehamilan,JenisPersalinan);
    }//GEN-LAST:event_TempatPersalinanKeyPressed

    private void JenisPersalinanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JenisPersalinanKeyPressed
        Valid.pindah(evt,TempatPersalinan,Penolong);
    }//GEN-LAST:event_JenisPersalinanKeyPressed

    private void PenolongKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenolongKeyPressed
        Valid.pindah(evt,JenisPersalinan,Penyulit);
    }//GEN-LAST:event_PenolongKeyPressed

    private void PenyulitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenyulitKeyPressed
        Valid.pindah(evt,Penolong,Keadaan);
    }//GEN-LAST:event_PenyulitKeyPressed

    private void KeadaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeadaanKeyPressed
        Valid.pindah(evt,Penyulit,UsiaHamil);
    }//GEN-LAST:event_KeadaanKeyPressed

    private void UsiaHamilKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_UsiaHamilKeyPressed
        Valid.pindah(evt,Keadaan,JK);
    }//GEN-LAST:event_UsiaHamilKeyPressed

    private void BBPBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BBPBKeyPressed
        Valid.pindah(evt,JK,BtnSimpanRiwayatKehamilan);
    }//GEN-LAST:event_BBPBKeyPressed

    private void BtnDokter1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokter1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnDokter1ActionPerformed

    private void BtnDokter1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokter1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnDokter1KeyPressed

    private void KdPetugas1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdPetugas1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KdPetugas1KeyPressed

    private void KdPetugas2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdPetugas2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KdPetugas2KeyPressed

    private void BtnDokter2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokter2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnDokter2ActionPerformed

    private void BtnDokter2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokter2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnDokter2KeyPressed

    private void Informasi1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Informasi1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Informasi1KeyPressed

    private void Informasi2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Informasi2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Informasi2KeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMPenilaianAwalKeperawatanKebidananRanap dialog = new RMPenilaianAwalKeperawatanKebidananRanap(new javax.swing.JFrame(), true);
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
    private widget.TextBox BBPB;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnDokter;
    private widget.Button BtnDokter1;
    private widget.Button BtnDokter2;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnKeluarKehamilan;
    private widget.Button BtnPrint;
    private widget.Button BtnPrint1;
    private widget.Button BtnSimpan;
    private widget.Button BtnSimpanRiwayatKehamilan;
    private widget.CekBox ChkAccor;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private javax.swing.JDialog DlgRiwayatPersalinan;
    private widget.PanelBiasa FormInput;
    private widget.PanelBiasa FormMasalahRencana;
    private widget.PanelBiasa FormMenu;
    private widget.ComboBox Informasi;
    private widget.ComboBox Informasi1;
    private widget.ComboBox Informasi2;
    private widget.ComboBox JK;
    private widget.TextBox JenisPersalinan;
    private widget.TextBox Jk;
    private widget.TextBox KdPetugas;
    private widget.TextBox KdPetugas1;
    private widget.TextBox KdPetugas2;
    private widget.TextBox Keadaan;
    private widget.Label LCount;
    private widget.editorpane LoadHTML;
    private widget.TextArea MasalahKebidanan;
    private widget.TextBox NmPetugas;
    private widget.TextBox NmPetugas1;
    private widget.TextBox NmPetugas2;
    private widget.PanelBiasa PanelAccor;
    private widget.TextBox Penolong;
    private widget.TextBox Penyulit;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRM1;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.TextBox TPasien1;
    private javax.swing.JTabbedPane TabRawat;
    private widget.Tanggal TanggalPersalinan;
    private widget.TextBox TempatPersalinan;
    private widget.Tanggal TglAsuhan;
    private widget.TextBox TglLahir;
    private widget.TextArea TindakanKebidanan;
    private widget.TextBox UsiaHamil;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.InternalFrame internalFrame4;
    private widget.Label jLabel10;
    private widget.Label jLabel105;
    private widget.Label jLabel106;
    private widget.Label jLabel107;
    private widget.Label jLabel108;
    private widget.Label jLabel109;
    private widget.Label jLabel11;
    private widget.Label jLabel110;
    private widget.Label jLabel111;
    private widget.Label jLabel112;
    private widget.Label jLabel19;
    private widget.Label jLabel21;
    private widget.Label jLabel34;
    private widget.Label jLabel36;
    private widget.Label jLabel37;
    private widget.Label jLabel38;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel99;
    private javax.swing.JSeparator jSeparator1;
    private widget.Label label11;
    private widget.Label label14;
    private widget.Label label15;
    private widget.Label label16;
    private widget.PanelBiasa panelBiasa2;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollInput;
    private widget.ScrollPane scrollPane6;
    private widget.ScrollPane scrollPane7;
    private widget.ScrollPane scrollPane9;
    private widget.Table tbObat;
    private widget.Table tbRiwayatKehamilan1;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            ps=koneksi.prepareStatement(
                "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,pasien.agama,bahasa_pasien.nama_bahasa,cacat_fisik.nama_cacat,penilaian_awal_keperawatan_kebidanan.tanggal,"+
                "penilaian_awal_keperawatan_kebidanan.informasi,penilaian_awal_keperawatan_kebidanan.td,penilaian_awal_keperawatan_kebidanan.nadi,penilaian_awal_keperawatan_kebidanan.rr,penilaian_awal_keperawatan_kebidanan.suhu,penilaian_awal_keperawatan_kebidanan.bb,"+
                "penilaian_awal_keperawatan_kebidanan.tb,penilaian_awal_keperawatan_kebidanan.nadi,penilaian_awal_keperawatan_kebidanan.rr,penilaian_awal_keperawatan_kebidanan.suhu,penilaian_awal_keperawatan_kebidanan.gcs,penilaian_awal_keperawatan_kebidanan.bb,"+
                "penilaian_awal_keperawatan_kebidanan.tb,penilaian_awal_keperawatan_kebidanan.bmi,penilaian_awal_keperawatan_kebidanan.lila,penilaian_awal_keperawatan_kebidanan.tfu,penilaian_awal_keperawatan_kebidanan.tbj,penilaian_awal_keperawatan_kebidanan.letak,"+
                "penilaian_awal_keperawatan_kebidanan.presentasi,penilaian_awal_keperawatan_kebidanan.penurunan,penilaian_awal_keperawatan_kebidanan.his,penilaian_awal_keperawatan_kebidanan.kekuatan,penilaian_awal_keperawatan_kebidanan.lamanya,penilaian_awal_keperawatan_kebidanan.bjj,"+
                "penilaian_awal_keperawatan_kebidanan.ket_bjj,penilaian_awal_keperawatan_kebidanan.portio,penilaian_awal_keperawatan_kebidanan.serviks,penilaian_awal_keperawatan_kebidanan.ketuban,penilaian_awal_keperawatan_kebidanan.hodge,penilaian_awal_keperawatan_kebidanan.inspekulo,"+
                "penilaian_awal_keperawatan_kebidanan.ket_inspekulo,penilaian_awal_keperawatan_kebidanan.ctg,penilaian_awal_keperawatan_kebidanan.ket_ctg,penilaian_awal_keperawatan_kebidanan.usg,penilaian_awal_keperawatan_kebidanan.ket_usg,penilaian_awal_keperawatan_kebidanan.lab,"+
                "penilaian_awal_keperawatan_kebidanan.ket_lab,penilaian_awal_keperawatan_kebidanan.lakmus,penilaian_awal_keperawatan_kebidanan.ket_lakmus,penilaian_awal_keperawatan_kebidanan.panggul,penilaian_awal_keperawatan_kebidanan.keluhan_utama,penilaian_awal_keperawatan_kebidanan.umur,"+
                "penilaian_awal_keperawatan_kebidanan.lama,penilaian_awal_keperawatan_kebidanan.banyaknya,penilaian_awal_keperawatan_kebidanan.haid,penilaian_awal_keperawatan_kebidanan.siklus,penilaian_awal_keperawatan_kebidanan.ket_siklus,penilaian_awal_keperawatan_kebidanan.ket_siklus1,"+
                "penilaian_awal_keperawatan_kebidanan.status,penilaian_awal_keperawatan_kebidanan.kali,penilaian_awal_keperawatan_kebidanan.usia1,penilaian_awal_keperawatan_kebidanan.ket1,penilaian_awal_keperawatan_kebidanan.usia2,penilaian_awal_keperawatan_kebidanan.ket2,"+
                "penilaian_awal_keperawatan_kebidanan.usia3,penilaian_awal_keperawatan_kebidanan.ket3,penilaian_awal_keperawatan_kebidanan.hpht,penilaian_awal_keperawatan_kebidanan.usia_kehamilan,penilaian_awal_keperawatan_kebidanan.tp,penilaian_awal_keperawatan_kebidanan.imunisasi,"+
                "penilaian_awal_keperawatan_kebidanan.ket_imunisasi,penilaian_awal_keperawatan_kebidanan.g,penilaian_awal_keperawatan_kebidanan.p,penilaian_awal_keperawatan_kebidanan.a,penilaian_awal_keperawatan_kebidanan.hidup,penilaian_awal_keperawatan_kebidanan.ginekologi,"+
                "penilaian_awal_keperawatan_kebidanan.kebiasaan,penilaian_awal_keperawatan_kebidanan.ket_kebiasaan,penilaian_awal_keperawatan_kebidanan.kebiasaan1,penilaian_awal_keperawatan_kebidanan.ket_kebiasaan1,penilaian_awal_keperawatan_kebidanan.kebiasaan2,"+
                "penilaian_awal_keperawatan_kebidanan.ket_kebiasaan2,penilaian_awal_keperawatan_kebidanan.kebiasaan3,penilaian_awal_keperawatan_kebidanan.kb,penilaian_awal_keperawatan_kebidanan.ket_kb,penilaian_awal_keperawatan_kebidanan.komplikasi,"+
                "penilaian_awal_keperawatan_kebidanan.ket_komplikasi,penilaian_awal_keperawatan_kebidanan.berhenti,penilaian_awal_keperawatan_kebidanan.alasan,penilaian_awal_keperawatan_kebidanan.alat_bantu,penilaian_awal_keperawatan_kebidanan.ket_bantu,"+
                "penilaian_awal_keperawatan_kebidanan.prothesa,penilaian_awal_keperawatan_kebidanan.ket_pro,penilaian_awal_keperawatan_kebidanan.adl,penilaian_awal_keperawatan_kebidanan.status_psiko,penilaian_awal_keperawatan_kebidanan.ket_psiko,"+
                "penilaian_awal_keperawatan_kebidanan.hub_keluarga,penilaian_awal_keperawatan_kebidanan.tinggal_dengan,penilaian_awal_keperawatan_kebidanan.ket_tinggal,penilaian_awal_keperawatan_kebidanan.ekonomi,penilaian_awal_keperawatan_kebidanan.budaya,"+
                "penilaian_awal_keperawatan_kebidanan.ket_budaya,penilaian_awal_keperawatan_kebidanan.edukasi,penilaian_awal_keperawatan_kebidanan.ket_edukasi,penilaian_awal_keperawatan_kebidanan.berjalan_a,penilaian_awal_keperawatan_kebidanan.berjalan_b,"+
                "penilaian_awal_keperawatan_kebidanan.berjalan_c,penilaian_awal_keperawatan_kebidanan.hasil,penilaian_awal_keperawatan_kebidanan.lapor,penilaian_awal_keperawatan_kebidanan.ket_lapor,penilaian_awal_keperawatan_kebidanan.sg1,"+
                "penilaian_awal_keperawatan_kebidanan.nilai1,penilaian_awal_keperawatan_kebidanan.sg2,penilaian_awal_keperawatan_kebidanan.nilai2,penilaian_awal_keperawatan_kebidanan.total_hasil,penilaian_awal_keperawatan_kebidanan.nyeri,"+
                "penilaian_awal_keperawatan_kebidanan.provokes,penilaian_awal_keperawatan_kebidanan.ket_provokes,penilaian_awal_keperawatan_kebidanan.quality,penilaian_awal_keperawatan_kebidanan.ket_quality,penilaian_awal_keperawatan_kebidanan.lokasi,"+
                "penilaian_awal_keperawatan_kebidanan.menyebar,penilaian_awal_keperawatan_kebidanan.skala_nyeri,penilaian_awal_keperawatan_kebidanan.durasi,penilaian_awal_keperawatan_kebidanan.nyeri_hilang,penilaian_awal_keperawatan_kebidanan.ket_nyeri,"+
                "penilaian_awal_keperawatan_kebidanan.pada_dokter,penilaian_awal_keperawatan_kebidanan.ket_dokter,penilaian_awal_keperawatan_kebidanan.masalah,penilaian_awal_keperawatan_kebidanan.tindakan,penilaian_awal_keperawatan_kebidanan.nip,petugas.nama "+
                "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join penilaian_awal_keperawatan_kebidanan on reg_periksa.no_rawat=penilaian_awal_keperawatan_kebidanan.no_rawat "+
                "inner join petugas on penilaian_awal_keperawatan_kebidanan.nip=petugas.nip "+
                "inner join bahasa_pasien on bahasa_pasien.id=pasien.bahasa_pasien "+
                "inner join cacat_fisik on cacat_fisik.id=pasien.cacat_fisik where "+
                "penilaian_awal_keperawatan_kebidanan.tanggal between ? and ? "+
                 (TCari.getText().trim().equals("")?"":"and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or penilaian_awal_keperawatan_kebidanan.nip like ? or  petugas.nama like ?)")+
                 " order by penilaian_awal_keperawatan_kebidanan.tanggal");
            
            try {
                ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                if(!TCari.getText().equals("")){
                    ps.setString(3,"%"+TCari.getText()+"%");
                    ps.setString(4,"%"+TCari.getText()+"%");
                    ps.setString(5,"%"+TCari.getText()+"%");
                    ps.setString(6,"%"+TCari.getText()+"%");
                    ps.setString(7,"%"+TCari.getText()+"%");
                }   
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new String[]{
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("jk"),rs.getString("agama"),rs.getString("nama_bahasa"),rs.getString("nama_cacat"),
                        rs.getString("tgl_lahir"),rs.getString("tanggal"),rs.getString("informasi"),rs.getString("td"),rs.getString("nadi"),rs.getString("rr"),rs.getString("suhu"),
                        rs.getString("gcs"),rs.getString("bb"),rs.getString("tb"),rs.getString("lila"),rs.getString("bmi"),rs.getString("tfu"),rs.getString("tbj"),rs.getString("letak"),
                        rs.getString("presentasi"),rs.getString("penurunan"),rs.getString("his"),rs.getString("kekuatan"),rs.getString("lamanya"),rs.getString("bjj"),rs.getString("ket_bjj"),
                        rs.getString("portio"),rs.getString("serviks"),rs.getString("ketuban"),rs.getString("hodge"),rs.getString("inspekulo"),rs.getString("ket_inspekulo"),rs.getString("ctg"),
                        rs.getString("ket_ctg"),rs.getString("usg"),rs.getString("ket_usg"),rs.getString("lab"),rs.getString("ket_lab"),rs.getString("lakmus"),rs.getString("ket_lakmus"),
                        rs.getString("panggul"),rs.getString("keluhan_utama"),rs.getString("umur"),rs.getString("lama"),rs.getString("banyaknya"),rs.getString("haid"),rs.getString("siklus"),
                        rs.getString("ket_siklus"),rs.getString("ket_siklus1"),rs.getString("status"),rs.getString("kali"),rs.getString("usia1"),rs.getString("ket1"),rs.getString("usia2"),
                        rs.getString("ket2"),rs.getString("usia3"),rs.getString("ket3"),rs.getString("hpht"),rs.getString("usia_kehamilan"),rs.getString("tp"),rs.getString("imunisasi"),
                        rs.getString("ket_imunisasi"),rs.getString("g"),rs.getString("P"),rs.getString("a"),rs.getString("hidup"),rs.getString("ginekologi"),rs.getString("kebiasaan"),
                        rs.getString("ket_kebiasaan"),rs.getString("kebiasaan1"),rs.getString("ket_kebiasaan1"),rs.getString("kebiasaan2"),rs.getString("ket_kebiasaan2"),rs.getString("kebiasaan3"),
                        rs.getString("kb"),rs.getString("ket_kb"),rs.getString("komplikasi"),rs.getString("ket_komplikasi"),rs.getString("berhenti"),rs.getString("alasan"),rs.getString("alat_bantu"),
                        rs.getString("ket_bantu"),rs.getString("prothesa"),rs.getString("ket_pro"),rs.getString("adl"),rs.getString("status_psiko"),rs.getString("ket_psiko"),rs.getString("hub_keluarga"),
                        rs.getString("tinggal_dengan"),rs.getString("ket_tinggal"),rs.getString("ekonomi"),rs.getString("budaya"),rs.getString("ket_budaya"),rs.getString("edukasi"),
                        rs.getString("ket_edukasi"),rs.getString("berjalan_a"),rs.getString("berjalan_b"),rs.getString("berjalan_c"),rs.getString("hasil"),rs.getString("lapor"),rs.getString("ket_lapor"),
                        rs.getString("sg1"),rs.getString("nilai1"),rs.getString("sg2"),rs.getString("nilai2"),rs.getString("total_hasil"),rs.getString("nyeri"),rs.getString("provokes"),
                        rs.getString("ket_provokes"),rs.getString("quality"),rs.getString("ket_quality"),rs.getString("lokasi"),rs.getString("menyebar"),rs.getString("skala_nyeri"),
                        rs.getString("durasi"),rs.getString("nyeri_hilang"),rs.getString("ket_nyeri"),rs.getString("pada_dokter"),rs.getString("ket_dokter"),rs.getString("masalah"),
                        rs.getString("tindakan"),rs.getString("nip"),rs.getString("nama")
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
        tampilPersalinan();
    }
    
    
    public void isCek(){
        BtnSimpan.setEnabled(akses.getpenilaian_awal_keperawatan_ranapkebidanan());
        BtnHapus.setEnabled(akses.getpenilaian_awal_keperawatan_ranapkebidanan());
        BtnEdit.setEnabled(akses.getpenilaian_awal_keperawatan_ranapkebidanan());
        BtnEdit.setEnabled(akses.getpenilaian_awal_keperawatan_ranapkebidanan()); 
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
            MasalahKebidanan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),122).toString());
            TindakanKebidanan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),123).toString());
            Valid.tabelKosong(tabModeRiwayatKehamilan2);
            try {
                ps=koneksi.prepareStatement("select * from riwayat_persalinan_pasien where no_rkm_medis=? order by tgl_thn");
                try {
                    ps.setString(1,TNoRM1.getText());
                    rs=ps.executeQuery();
                    i=1;
                    while(rs.next()){
                        tabModeRiwayatKehamilan2.addRow(new String[]{
                            i+"",rs.getString("tgl_thn"),rs.getString("tempat_persalinan"),rs.getString("usia_hamil"),rs.getString("jenis_persalinan"),
                            rs.getString("penolong"),rs.getString("penyulit"),rs.getString("jk"),rs.getString("bbpb"),rs.getString("keadaan")
                        });
                        i++;
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
        
    }

    private void emptTeksPersalinan() {
        TempatPersalinan.setText("");
        JenisPersalinan.setText("");
        Penolong.setText("");
        Penyulit.setText("");
        Keadaan.setText("");
        UsiaHamil.setText("");
        BBPB.setText("");
        TanggalPersalinan.setDate(new Date());
        JK.setSelectedIndex(0);
        TempatPersalinan.requestFocus();
    }

    private void tampilPersalinan() {
        Valid.tabelKosong(tabModeRiwayatKehamilan);
        try {
            ps=koneksi.prepareStatement("select * from riwayat_persalinan_pasien where no_rkm_medis=? order by tgl_thn");
            try {
                ps.setString(1,TNoRM.getText());
                rs=ps.executeQuery();
                i=1;
                while(rs.next()){
                    tabModeRiwayatKehamilan.addRow(new String[]{
                        i+"",rs.getString("tgl_thn"),rs.getString("tempat_persalinan"),rs.getString("usia_hamil"),rs.getString("jenis_persalinan"),
                        rs.getString("penolong"),rs.getString("penyulit"),rs.getString("jk"),rs.getString("bbpb"),rs.getString("keadaan")
                    });
                    i++;
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

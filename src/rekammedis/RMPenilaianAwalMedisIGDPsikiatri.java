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
public final class RMPenilaianAwalMedisIGDPsikiatri extends javax.swing.JDialog {
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
    public RMPenilaianAwalMedisIGDPsikiatri(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        tabMode=new DefaultTableModel(null,new Object[]{
                "No.Rawat","No.RM","Nama Pasien","Tgl.Lahir","J.K.","Kode Dokter","Nama Dokter","Tanggal","Anamnesis","Hubungan","Keluhan Utama",
                "Gejala Keluhan","Pencetus Keluhan","Riwayat Penyakit Sekarang","Keterangan Riwayat Penyakit Sekarang","Riwayat Kehamilan","Sosial","Ket Sosial","Pekerjaan","Ket Pekerjaan","Riwayat pemberian Obat",
                "Faktor Premorbid","Keturunan","Ket Keturunan","Faktor Organik","Ket Organik","Alergi","Kesadaran","Skala Nyeri","Tensi(mmHg)","Nadi(x/menit)",
                "Suhu(°C)","RR(x/menit)","BB(Kg)","TB(Cm)","GCS","Status Nutrisi","Kepala","Keterangan Kepala",
                "Leher","Keterangan Leher","Dada","Keterangan Dada","Perut","Keterangan Perut","Anggota Gerak","Keterangan Gerak",
                "Anggota Badan","Kesan Umum","Sikap","Kesadaran 1","Orientasi","Daya Ingat","Persepsi","Pikiran","Insight","Laborat","Radiologi","Penunjang","Permasalahan","Rencana","Instruksi","Di pulangkan","Keterangan dipulangkan","Keterangan Ruang","Keterangan Indikasi", 
                "Keterangan di rujuk","Keterangan alasan dirujuk","Pulang paksa","Ket Pulang paksa","Meninggal","Ket meninggal","Kesadaran2","TD 1","Nadi 1","RR 1","Suhu 1","GCS 1","Edukasi"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        
        tbObat.setModel(tabMode);
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 79; i++) {
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
                column.setPreferredWidth(200);
            }else if(i==13){
                column.setPreferredWidth(200);
            }else if(i==14){
                column.setPreferredWidth(200);
            }else if(i==15){
                column.setPreferredWidth(200);
            }else if(i==16){
                column.setPreferredWidth(200);
            }else if(i==17){
                column.setPreferredWidth(200);
            }else if(i==18){
                column.setPreferredWidth(200);
            }else if(i==19){
                column.setPreferredWidth(200);
            }else if(i==20){
                column.setPreferredWidth(200);
            }else if(i==21){
                column.setPreferredWidth(200);
            }else if(i==22){
                column.setPreferredWidth(200);
            }else if(i==23){
                column.setPreferredWidth(200);
            }else if(i==24){
                column.setPreferredWidth(200);
            }else if(i==25){
                column.setPreferredWidth(200);
            }else if(i==26){
                column.setPreferredWidth(150);
            }else if(i==27){
                column.setPreferredWidth(100);
            }else if(i==28){
                column.setPreferredWidth(85);
            }else if(i==29){
                column.setPreferredWidth(80);
            }else if(i==30){
                column.setPreferredWidth(115);
            }else if(i==31){
                column.setPreferredWidth(73);
            }else if(i==32){
                column.setPreferredWidth(75);
            }else if(i==33){
                column.setPreferredWidth(52);
            }else if(i==34){
                column.setPreferredWidth(70);
            }else if(i==35){
                column.setPreferredWidth(70);
            }else if(i==36){
                column.setPreferredWidth(70);
            }else if(i==37){
                column.setPreferredWidth(70);
            }else if(i==38){
                column.setPreferredWidth(70);
            }else if(i==39){
                column.setPreferredWidth(70);
            }else if(i==40){
                column.setPreferredWidth(70);
            }else if(i==41){
                column.setPreferredWidth(70);
            }else if(i==42){
                column.setPreferredWidth(70);
            }else if(i==43){
                column.setPreferredWidth(70);
            }else if(i==44){
                column.setPreferredWidth(70);
            }else if(i==45){
                column.setPreferredWidth(70);
            }else if(i==46){
                column.setPreferredWidth(70);
            }else if(i==47){
                column.setPreferredWidth(70);
            }else if(i==48){
                column.setPreferredWidth(70);
            }else if(i==49){
                column.setPreferredWidth(70);
            }else if(i==50){
                column.setPreferredWidth(70);
            }else if(i==51){
                column.setPreferredWidth(70);
            }else if(i==52){
                column.setPreferredWidth(70);
            }else if(i==53){
                column.setPreferredWidth(70);
            }else if(i==54){
                column.setPreferredWidth(70);
            }else if(i==55){
                column.setPreferredWidth(70);
            }else if(i==56){
                column.setPreferredWidth(45);
            }else if(i==57){
                column.setPreferredWidth(47);
            }else if(i==58){
                column.setPreferredWidth(120);
            }else if(i==59){
                column.setPreferredWidth(40);
            }else if(i==60){
                column.setPreferredWidth(130);
            }else if(i==61){
                column.setPreferredWidth(40);
            }else if(i==62){
                column.setPreferredWidth(130);
            }else if(i==63){
                column.setPreferredWidth(40);
            }else if(i==64){
                column.setPreferredWidth(130);
            }else if(i==65){
                column.setPreferredWidth(50);
            }else if(i==66){
                column.setPreferredWidth(130);
            }else if(i==67){
                column.setPreferredWidth(65);
            }else if(i==68){
                column.setPreferredWidth(130);
            }else if(i==69){
                column.setPreferredWidth(300);
            }else if(i==70){
                column.setPreferredWidth(200);
            }else if(i==71){
                column.setPreferredWidth(200);
            }else if(i==72){
                column.setPreferredWidth(200);
            }else if(i==73){
                column.setPreferredWidth(150);
            }else if(i==74){
                column.setPreferredWidth(150);
            }else if(i==75){
                column.setPreferredWidth(200);
            }else if(i==76){
                column.setPreferredWidth(200);
            }else if(i==77){
                column.setPreferredWidth(200);
            }else if(i==78){
                column.setPreferredWidth(200);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());
        
        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        Hubungan.setDocument(new batasInput((int)30).getKata(Hubungan));
        KeluhanUtama.setDocument(new batasInput((int)2000).getKata(KeluhanUtama));
        KetRPS.setDocument(new batasInput((int)2000).getKata(KetRPS));
        Alergi.setDocument(new batasInput((int)50).getKata(Alergi));
        TD.setDocument(new batasInput((int)8).getKata(TD));
        Nadi.setDocument(new batasInput((int)5).getKata(Nadi));
        Suhu.setDocument(new batasInput((int)5).getKata(Suhu));
        RR.setDocument(new batasInput((int)5).getKata(RR));
        BB.setDocument(new batasInput((int)5).getKata(BB));
        TB.setDocument(new batasInput((int)5).getKata(TB));
        StatusNutrisi.setDocument(new batasInput((int)50).getKata(StatusNutrisi));
        KeteranganKepala.setDocument(new batasInput((int)30).getKata(KeteranganKepala));
        KeteranganLeher.setDocument(new batasInput((int)30).getKata(KeteranganLeher));
        KeteranganGerak.setDocument(new batasInput((int)30).getKata(KeteranganGerak));
        KeteranganDada.setDocument(new batasInput((int)30).getKata(KeteranganDada));
        KeteranganPerut.setDocument(new batasInput((int)30).getKata(KeteranganPerut));
        KeteranganBadan.setDocument(new batasInput((int)1000).getKata(KeteranganBadan));
        Laborat.setDocument(new batasInput((int)300).getKata(Laborat));
        Radiologi.setDocument(new batasInput((int)300).getKata(Radiologi));
        Penunjang.setDocument(new batasInput((int)300).getKata(Penunjang));
        Permasalahan.setDocument(new batasInput((int)1000).getKata(Permasalahan));
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
        scrollPane4 = new widget.ScrollPane();
        RPO = new widget.TextArea();
        jLabel28 = new widget.Label();
        TB = new widget.TextBox();
        jLabel30 = new widget.Label();
        jLabel94 = new widget.Label();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel38 = new widget.Label();
        Hubungan = new widget.TextBox();
        scrollPane7 = new widget.ScrollPane();
        KetRPS = new widget.TextArea();
        Kesadaran = new widget.ComboBox();
        jLabel29 = new widget.Label();
        StatusNutrisi = new widget.TextBox();
        Kepala = new widget.ComboBox();
        jLabel44 = new widget.Label();
        Gerak = new widget.ComboBox();
        Dada = new widget.ComboBox();
        jLabel50 = new widget.Label();
        Leher = new widget.ComboBox();
        jLabel99 = new widget.Label();
        label11 = new widget.Label();
        TglAsuhan = new widget.Tanggal();
        jLabel41 = new widget.Label();
        Nyeri = new widget.ComboBox();
        jLabel95 = new widget.Label();
        jLabel47 = new widget.Label();
        Perut = new widget.ComboBox();
        jLabel14 = new widget.Label();
        jLabel42 = new widget.Label();
        jLabel115 = new widget.Label();
        jLabel33 = new widget.Label();
        jLabel36 = new widget.Label();
        KeteranganKepala = new widget.TextBox();
        KeteranganLeher = new widget.TextBox();
        KeteranganGerak = new widget.TextBox();
        KesanUmum = new widget.TextBox();
        KeteranganPerut = new widget.TextBox();
        scrollPane5 = new widget.ScrollPane();
        KeteranganBadan = new widget.TextArea();
        PanelWall2 = new usu.widget.glass.PanelGlass();
        jLabel112 = new widget.Label();
        jLabel80 = new widget.Label();
        jLabel81 = new widget.Label();
        scrollPane15 = new widget.ScrollPane();
        Penunjang = new widget.TextArea();
        scrollPane9 = new widget.ScrollPane();
        Laborat = new widget.TextArea();
        scrollPane10 = new widget.ScrollPane();
        Radiologi = new widget.TextArea();
        jLabel118 = new widget.Label();
        scrollPane14 = new widget.ScrollPane();
        Edukasi = new widget.TextArea();
        jLabel52 = new widget.Label();
        jLabel53 = new widget.Label();
        scrollPane6 = new widget.ScrollPane();
        Instruksi = new widget.TextArea();
        jLabel83 = new widget.Label();
        scrollPane8 = new widget.ScrollPane();
        Permasalahan = new widget.TextArea();
        jLabel84 = new widget.Label();
        jLabel51 = new widget.Label();
        Dipulangkan = new widget.ComboBox();
        Ketruang = new widget.TextBox();
        jLabel54 = new widget.Label();
        KetPulangpaksa = new widget.TextBox();
        jLabel55 = new widget.Label();
        Ketindikasi = new widget.TextBox();
        jLabel56 = new widget.Label();
        Alasandirujuk = new widget.ComboBox();
        jLabel57 = new widget.Label();
        Ketdirujuk = new widget.TextBox();
        jLabel58 = new widget.Label();
        Meninggal = new widget.ComboBox();
        Ketdipulangkan = new widget.TextBox();
        jLabel59 = new widget.Label();
        Pulangpaksa = new widget.ComboBox();
        jLabel60 = new widget.Label();
        Ketmeninggal = new widget.TextBox();
        jLabel45 = new widget.Label();
        Kesadaran2 = new widget.ComboBox();
        jLabel27 = new widget.Label();
        TD1 = new widget.TextBox();
        jLabel31 = new widget.Label();
        jLabel35 = new widget.Label();
        Nadi1 = new widget.TextBox();
        jLabel39 = new widget.Label();
        jLabel40 = new widget.Label();
        RR1 = new widget.TextBox();
        jLabel46 = new widget.Label();
        jLabel49 = new widget.Label();
        Suhu1 = new widget.TextBox();
        jLabel61 = new widget.Label();
        jLabel63 = new widget.Label();
        jLabel64 = new widget.Label();
        GCS1 = new widget.TextBox();
        jLabel65 = new widget.Label();
        jLabel66 = new widget.Label();
        GCS = new widget.TextBox();
        scrollPane2 = new widget.ScrollPane();
        PencetusKeluhan = new widget.TextArea();
        jLabel48 = new widget.Label();
        scrollPane3 = new widget.ScrollPane();
        GejalaKeluhan = new widget.TextArea();
        jLabel34 = new widget.Label();
        RPS = new widget.ComboBox();
        scrollPane11 = new widget.ScrollPane();
        RiwayatKehamilan = new widget.TextArea();
        jLabel68 = new widget.Label();
        jLabel69 = new widget.Label();
        Sosial = new widget.ComboBox();
        KetSosial = new widget.TextBox();
        jLabel70 = new widget.Label();
        jLabel71 = new widget.Label();
        Pekerjaan = new widget.ComboBox();
        KetPekerjaan = new widget.TextBox();
        jLabel72 = new widget.Label();
        Keturunan = new widget.ComboBox();
        FaktorPremorbid = new widget.TextBox();
        KetKeturunan = new widget.TextBox();
        jLabel73 = new widget.Label();
        jLabel74 = new widget.Label();
        FaktorOrganik = new widget.ComboBox();
        KetOrganik = new widget.TextBox();
        jLabel24 = new widget.Label();
        jLabel114 = new widget.Label();
        KeteranganDada = new widget.TextBox();
        jLabel116 = new widget.Label();
        jLabel117 = new widget.Label();
        Sikap = new widget.TextBox();
        jLabel119 = new widget.Label();
        Kesadaran1 = new widget.TextBox();
        jLabel120 = new widget.Label();
        Orientasi = new widget.TextBox();
        jLabel121 = new widget.Label();
        DayaIngat = new widget.TextBox();
        jLabel122 = new widget.Label();
        Persepsi = new widget.TextBox();
        jLabel123 = new widget.Label();
        Pikiran = new widget.TextBox();
        jLabel124 = new widget.Label();
        Insight = new widget.TextBox();
        scrollPane12 = new widget.ScrollPane();
        Rencana = new widget.TextArea();
        jLabel130 = new widget.Label();
        jLabel131 = new widget.Label();
        jLabel62 = new widget.Label();
        jLabel43 = new widget.Label();
        jLabel75 = new widget.Label();
        jLabel32 = new widget.Label();
        jLabel76 = new widget.Label();
        jLabel77 = new widget.Label();
        jLabel78 = new widget.Label();
        jLabel79 = new widget.Label();
        jLabel82 = new widget.Label();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        jSeparator6 = new javax.swing.JSeparator();
        jLabel101 = new widget.Label();
        jSeparator7 = new javax.swing.JSeparator();
        jLabel102 = new widget.Label();
        scrollPane13 = new widget.ScrollPane();
        Diagnosis = new widget.TextArea();
        jSeparator8 = new javax.swing.JSeparator();
        jLabel113 = new widget.Label();
        jSeparator9 = new javax.swing.JSeparator();
        jLabel132 = new widget.Label();
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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Penilaian Awal Medis IGD Psikiatri ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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
        FormInput.setPreferredSize(new java.awt.Dimension(870, 1920));
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
        jLabel12.setBounds(210, 450, 30, 23);

        BB.setFocusTraversalPolicyProvider(true);
        BB.setName("BB"); // NOI18N
        BB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BBKeyPressed(evt);
            }
        });
        FormInput.add(BB);
        BB.setBounds(244, 450, 45, 23);

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel13.setText("Cm");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(398, 450, 30, 23);

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel16.setText("x/menit");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput.add(jLabel16);
        jLabel16.setBounds(156, 450, 50, 23);

        Nadi.setFocusTraversalPolicyProvider(true);
        Nadi.setName("Nadi"); // NOI18N
        Nadi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NadiKeyPressed(evt);
            }
        });
        FormInput.add(Nadi);
        Nadi.setBounds(108, 450, 45, 23);

        jLabel17.setText(" Nadi :");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(0, 450, 104, 23);

        jLabel18.setText("Suhu :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(533, 420, 40, 23);

        Suhu.setFocusTraversalPolicyProvider(true);
        Suhu.setName("Suhu"); // NOI18N
        Suhu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SuhuKeyPressed(evt);
            }
        });
        FormInput.add(Suhu);
        Suhu.setBounds(577, 420, 45, 23);

        TD.setFocusTraversalPolicyProvider(true);
        TD.setName("TD"); // NOI18N
        TD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TDKeyPressed(evt);
            }
        });
        FormInput.add(TD);
        TD.setBounds(278, 420, 76, 23);

        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel20.setText("°C");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(625, 420, 30, 23);

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel23.setText("mmHg");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(357, 420, 50, 23);

        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel25.setText("x/menit");
        jLabel25.setName("jLabel25"); // NOI18N
        FormInput.add(jLabel25);
        jLabel25.setBounds(482, 420, 50, 23);

        RR.setFocusTraversalPolicyProvider(true);
        RR.setName("RR"); // NOI18N
        RR.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RRKeyPressed(evt);
            }
        });
        FormInput.add(RR);
        RR.setBounds(434, 420, 45, 23);

        jLabel26.setText("RR :");
        jLabel26.setName("jLabel26"); // NOI18N
        FormInput.add(jLabel26);
        jLabel26.setBounds(390, 420, 40, 23);

        jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel37.setText("Riwayat Alergi");
        jLabel37.setName("jLabel37"); // NOI18N
        FormInput.add(jLabel37);
        jLabel37.setBounds(520, 360, 90, 23);

        Alergi.setFocusTraversalPolicyProvider(true);
        Alergi.setName("Alergi"); // NOI18N
        Alergi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlergiKeyPressed(evt);
            }
        });
        FormInput.add(Alergi);
        Alergi.setBounds(602, 360, 252, 23);

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
        scrollPane1.setBounds(129, 90, 368, 53);

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
        scrollPane4.setBounds(235, 350, 262, 43);

        jLabel28.setText("TB :");
        jLabel28.setName("jLabel28"); // NOI18N
        FormInput.add(jLabel28);
        jLabel28.setBounds(316, 450, 30, 23);

        TB.setFocusTraversalPolicyProvider(true);
        TB.setName("TB"); // NOI18N
        TB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TBKeyPressed(evt);
            }
        });
        FormInput.add(TB);
        TB.setBounds(350, 450, 45, 23);

        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel30.setText("Riwayat Penyakit Dahulu");
        jLabel30.setName("jLabel30"); // NOI18N
        FormInput.add(jLabel30);
        jLabel30.setBounds(520, 90, 140, 23);

        jLabel94.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel94.setText("III. STATUS KELAINAN");
        jLabel94.setName("jLabel94"); // NOI18N
        FormInput.add(jLabel94);
        jLabel94.setBounds(10, 480, 180, 23);

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

        KetRPS.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        KetRPS.setColumns(20);
        KetRPS.setRows(5);
        KetRPS.setName("KetRPS"); // NOI18N
        KetRPS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetRPSKeyPressed(evt);
            }
        });
        scrollPane7.setViewportView(KetRPS);

        FormInput.add(scrollPane7);
        scrollPane7.setBounds(520, 120, 334, 63);

        Kesadaran.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Compos Mentis", "Apatis", "Somnolen", "Sopor", "Koma" }));
        Kesadaran.setName("Kesadaran"); // NOI18N
        Kesadaran.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KesadaranKeyPressed(evt);
            }
        });
        FormInput.add(Kesadaran);
        Kesadaran.setBounds(108, 420, 130, 23);

        jLabel29.setText("Status Nutrisi :");
        jLabel29.setName("jLabel29"); // NOI18N
        FormInput.add(jLabel29);
        jLabel29.setBounds(421, 450, 90, 23);

        StatusNutrisi.setFocusTraversalPolicyProvider(true);
        StatusNutrisi.setName("StatusNutrisi"); // NOI18N
        StatusNutrisi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                StatusNutrisiKeyPressed(evt);
            }
        });
        FormInput.add(StatusNutrisi);
        StatusNutrisi.setBounds(515, 450, 195, 23);

        Kepala.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Kepala.setName("Kepala"); // NOI18N
        Kepala.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KepalaKeyPressed(evt);
            }
        });
        FormInput.add(Kepala);
        Kepala.setBounds(90, 500, 128, 23);

        jLabel44.setText("Anggota Gerak :");
        jLabel44.setName("jLabel44"); // NOI18N
        FormInput.add(jLabel44);
        jLabel44.setBounds(430, 530, 90, 23);

        Gerak.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Gerak.setName("Gerak"); // NOI18N
        Gerak.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GerakKeyPressed(evt);
            }
        });
        FormInput.add(Gerak);
        Gerak.setBounds(524, 530, 128, 23);

        Dada.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Dada.setName("Dada"); // NOI18N
        Dada.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DadaKeyPressed(evt);
            }
        });
        FormInput.add(Dada);
        Dada.setBounds(90, 560, 128, 23);

        jLabel50.setText("Dada :");
        jLabel50.setName("jLabel50"); // NOI18N
        FormInput.add(jLabel50);
        jLabel50.setBounds(0, 560, 86, 23);

        Leher.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Leher.setName("Leher"); // NOI18N
        Leher.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LeherKeyPressed(evt);
            }
        });
        FormInput.add(Leher);
        Leher.setBounds(90, 530, 128, 23);

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
        TglAsuhan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "28-08-2023 09:07:17" }));
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

        jLabel41.setText("Nyeri :");
        jLabel41.setName("jLabel41"); // NOI18N
        FormInput.add(jLabel41);
        jLabel41.setBounds(630, 420, 60, 23);

        Nyeri.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Nyeri", "Nyeri Ringan", "Nyeri Sedang", "Nyeri Berat", "Nyeri Sangat Berat", "Nyeri Tak Tertahankan" }));
        Nyeri.setName("Nyeri"); // NOI18N
        Nyeri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NyeriKeyPressed(evt);
            }
        });
        FormInput.add(Nyeri);
        Nyeri.setBounds(694, 420, 160, 23);

        jLabel95.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel95.setText("II. PEMERIKSAAN FISIK");
        jLabel95.setName("jLabel95"); // NOI18N
        FormInput.add(jLabel95);
        jLabel95.setBounds(10, 400, 180, 23);

        jLabel47.setText("Perut :");
        jLabel47.setName("jLabel47"); // NOI18N
        FormInput.add(jLabel47);
        jLabel47.setBounds(430, 500, 90, 23);

        Perut.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Perut.setName("Perut"); // NOI18N
        Perut.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PerutKeyPressed(evt);
            }
        });
        FormInput.add(Perut);
        Perut.setBounds(524, 500, 128, 23);

        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel14.setText("Kg");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(292, 450, 30, 23);

        jLabel42.setText("Kesadaran :");
        jLabel42.setName("jLabel42"); // NOI18N
        FormInput.add(jLabel42);
        jLabel42.setBounds(0, 420, 104, 23);

        jLabel115.setText("Kesan Umum :");
        jLabel115.setName("jLabel115"); // NOI18N
        FormInput.add(jLabel115);
        jLabel115.setBounds(0, 930, 130, 23);

        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel33.setText("Peristiwa/Faktor Pencetus Terkait");
        jLabel33.setName("jLabel33"); // NOI18N
        FormInput.add(jLabel33);
        jLabel33.setBounds(44, 210, 180, 23);

        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel36.setText("Riwayat Obat Yang Diminum Saat Ini");
        jLabel36.setName("jLabel36"); // NOI18N
        FormInput.add(jLabel36);
        jLabel36.setBounds(44, 350, 200, 23);

        KeteranganKepala.setFocusTraversalPolicyProvider(true);
        KeteranganKepala.setName("KeteranganKepala"); // NOI18N
        KeteranganKepala.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKepalaKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganKepala);
        KeteranganKepala.setBounds(220, 500, 200, 23);

        KeteranganLeher.setFocusTraversalPolicyProvider(true);
        KeteranganLeher.setName("KeteranganLeher"); // NOI18N
        KeteranganLeher.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganLeherKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganLeher);
        KeteranganLeher.setBounds(220, 530, 200, 23);

        KeteranganGerak.setFocusTraversalPolicyProvider(true);
        KeteranganGerak.setName("KeteranganGerak"); // NOI18N
        KeteranganGerak.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganGerakKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganGerak);
        KeteranganGerak.setBounds(654, 530, 200, 23);

        KesanUmum.setFocusTraversalPolicyProvider(true);
        KesanUmum.setName("KesanUmum"); // NOI18N
        KesanUmum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KesanUmumKeyPressed(evt);
            }
        });
        FormInput.add(KesanUmum);
        KesanUmum.setBounds(134, 930, 310, 23);

        KeteranganPerut.setFocusTraversalPolicyProvider(true);
        KeteranganPerut.setName("KeteranganPerut"); // NOI18N
        KeteranganPerut.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganPerutKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganPerut);
        KeteranganPerut.setBounds(654, 500, 200, 23);

        scrollPane5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane5.setName("scrollPane5"); // NOI18N

        KeteranganBadan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        KeteranganBadan.setColumns(20);
        KeteranganBadan.setRows(20);
        KeteranganBadan.setName("KeteranganBadan"); // NOI18N
        KeteranganBadan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganBadanKeyPressed(evt);
            }
        });
        scrollPane5.setViewportView(KeteranganBadan);

        FormInput.add(scrollPane5);
        scrollPane5.setBounds(614, 630, 240, 273);

        PanelWall2.setBackground(new java.awt.Color(255, 255, 255));
        PanelWall2.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/picture/LokalisOrtho.png"))); // NOI18N
        PanelWall2.setBackgroundImageType(usu.widget.constan.BackgroundConstan.BACKGROUND_IMAGE_STRECT);
        PanelWall2.setOpaque(true);
        PanelWall2.setPreferredSize(new java.awt.Dimension(200, 200));
        PanelWall2.setRound(false);
        PanelWall2.setWarna(new java.awt.Color(110, 110, 110));
        PanelWall2.setLayout(null);
        FormInput.add(PanelWall2);
        PanelWall2.setBounds(44, 610, 560, 293);

        jLabel112.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel112.setText("EKG :");
        jLabel112.setName("jLabel112"); // NOI18N
        FormInput.add(jLabel112);
        jLabel112.setBounds(594, 1070, 190, 23);

        jLabel80.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel80.setText("Instruksi Medis & Obat-obatan (Ditulis Dengan Jelas & Rinci) :");
        jLabel80.setName("jLabel80"); // NOI18N
        FormInput.add(jLabel80);
        jLabel80.setBounds(454, 1250, 340, 23);

        jLabel81.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel81.setText("Radiologi :");
        jLabel81.setName("jLabel81"); // NOI18N
        FormInput.add(jLabel81);
        jLabel81.setBounds(319, 1070, 150, 23);

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
        scrollPane15.setBounds(594, 1090, 260, 63);

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
        scrollPane9.setBounds(44, 1090, 260, 63);

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
        scrollPane10.setBounds(319, 1090, 260, 63);

        jLabel118.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel118.setText("EDUKASI :");
        jLabel118.setName("jLabel118"); // NOI18N
        FormInput.add(jLabel118);
        jLabel118.setBounds(10, 1810, 70, 20);

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
        scrollPane14.setBounds(90, 1810, 720, 70);

        jLabel52.setText("Kepala :");
        jLabel52.setName("jLabel52"); // NOI18N
        FormInput.add(jLabel52);
        jLabel52.setBounds(0, 500, 86, 23);

        jLabel53.setText("Leher :");
        jLabel53.setName("jLabel53"); // NOI18N
        FormInput.add(jLabel53);
        jLabel53.setBounds(0, 530, 86, 23);

        scrollPane6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane6.setName("scrollPane6"); // NOI18N

        Instruksi.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Instruksi.setColumns(20);
        Instruksi.setRows(15);
        Instruksi.setName("Instruksi"); // NOI18N
        Instruksi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                InstruksiKeyPressed(evt);
            }
        });
        scrollPane6.setViewportView(Instruksi);

        FormInput.add(scrollPane6);
        scrollPane6.setBounds(454, 1270, 400, 43);

        jLabel83.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel83.setText("Laboratorium :");
        jLabel83.setName("jLabel83"); // NOI18N
        FormInput.add(jLabel83);
        jLabel83.setBounds(44, 1070, 150, 23);

        scrollPane8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane8.setName("scrollPane8"); // NOI18N

        Permasalahan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Permasalahan.setColumns(20);
        Permasalahan.setRows(15);
        Permasalahan.setName("Permasalahan"); // NOI18N
        Permasalahan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PermasalahanKeyPressed(evt);
            }
        });
        scrollPane8.setViewportView(Permasalahan);

        FormInput.add(scrollPane8);
        scrollPane8.setBounds(44, 1270, 400, 43);

        jLabel84.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel84.setText("Rencana Penatalaksanaan & Target Terukur :");
        jLabel84.setName("jLabel84"); // NOI18N
        FormInput.add(jLabel84);
        jLabel84.setBounds(44, 1320, 260, 23);

        jLabel51.setText("Dirawat di Ruang :");
        jLabel51.setName("jLabel51"); // NOI18N
        FormInput.add(jLabel51);
        jLabel51.setBounds(50, 1480, 100, 23);

        Dipulangkan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Perlu Kontrol", "Kontrol/Berobat Jalan", "Rawat Inap" }));
        Dipulangkan.setName("Dipulangkan"); // NOI18N
        Dipulangkan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DipulangkanKeyPressed(evt);
            }
        });
        FormInput.add(Dipulangkan);
        Dipulangkan.setBounds(160, 1450, 140, 23);

        Ketruang.setFocusTraversalPolicyProvider(true);
        Ketruang.setName("Ketruang"); // NOI18N
        Ketruang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetruangKeyPressed(evt);
            }
        });
        FormInput.add(Ketruang);
        Ketruang.setBounds(160, 1480, 235, 23);

        jLabel54.setText("Dipulangkan :");
        jLabel54.setName("jLabel54"); // NOI18N
        FormInput.add(jLabel54);
        jLabel54.setBounds(60, 1450, 90, 23);

        KetPulangpaksa.setFocusTraversalPolicyProvider(true);
        KetPulangpaksa.setName("KetPulangpaksa"); // NOI18N
        KetPulangpaksa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetPulangpaksaKeyPressed(evt);
            }
        });
        FormInput.add(KetPulangpaksa);
        KetPulangpaksa.setBounds(320, 1600, 235, 23);

        jLabel55.setText("Indikasi Rawat Inap :");
        jLabel55.setName("jLabel55"); // NOI18N
        FormInput.add(jLabel55);
        jLabel55.setBounds(40, 1510, 110, 23);

        Ketindikasi.setFocusTraversalPolicyProvider(true);
        Ketindikasi.setName("Ketindikasi"); // NOI18N
        Ketindikasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetindikasiKeyPressed(evt);
            }
        });
        FormInput.add(Ketindikasi);
        Ketindikasi.setBounds(160, 1510, 320, 23);

        jLabel56.setText("Alasan dikirim/dirujuk :");
        jLabel56.setName("jLabel56"); // NOI18N
        FormInput.add(jLabel56);
        jLabel56.setBounds(20, 1570, 130, 23);

        Alasandirujuk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Tempat Penuh", "Perlu fasilitas lebih", "Permintaan pasien/keluarga" }));
        Alasandirujuk.setName("Alasandirujuk"); // NOI18N
        Alasandirujuk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlasandirujukKeyPressed(evt);
            }
        });
        FormInput.add(Alasandirujuk);
        Alasandirujuk.setBounds(160, 1570, 170, 23);

        jLabel57.setText("Dikirim/dirujuk ke :");
        jLabel57.setName("jLabel57"); // NOI18N
        FormInput.add(jLabel57);
        jLabel57.setBounds(40, 1540, 110, 23);

        Ketdirujuk.setFocusTraversalPolicyProvider(true);
        Ketdirujuk.setName("Ketdirujuk"); // NOI18N
        Ketdirujuk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetdirujukKeyPressed(evt);
            }
        });
        FormInput.add(Ketdirujuk);
        Ketdirujuk.setBounds(160, 1540, 320, 23);

        jLabel58.setText("Meninggal di IGD :");
        jLabel58.setName("jLabel58"); // NOI18N
        FormInput.add(jLabel58);
        jLabel58.setBounds(20, 1630, 130, 23);

        Meninggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "<=2 jam", "> 2 jam" }));
        Meninggal.setName("Meninggal"); // NOI18N
        Meninggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MeninggalKeyPressed(evt);
            }
        });
        FormInput.add(Meninggal);
        Meninggal.setBounds(160, 1630, 80, 23);

        Ketdipulangkan.setFocusTraversalPolicyProvider(true);
        Ketdipulangkan.setName("Ketdipulangkan"); // NOI18N
        Ketdipulangkan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetdipulangkanKeyPressed(evt);
            }
        });
        FormInput.add(Ketdipulangkan);
        Ketdipulangkan.setBounds(310, 1450, 235, 23);

        jLabel59.setText("Pulang paksa, Alasan :");
        jLabel59.setName("jLabel59"); // NOI18N
        FormInput.add(jLabel59);
        jLabel59.setBounds(20, 1600, 130, 23);

        Pulangpaksa.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Masalah biaya", "Kondisi pasien", "Masalah lokasi rumah", "Lain-lain" }));
        Pulangpaksa.setName("Pulangpaksa"); // NOI18N
        Pulangpaksa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PulangpaksaKeyPressed(evt);
            }
        });
        FormInput.add(Pulangpaksa);
        Pulangpaksa.setBounds(160, 1600, 150, 23);

        jLabel60.setText("Penyebab kematian :");
        jLabel60.setName("jLabel60"); // NOI18N
        FormInput.add(jLabel60);
        jLabel60.setBounds(40, 1660, 110, 23);

        Ketmeninggal.setFocusTraversalPolicyProvider(true);
        Ketmeninggal.setName("Ketmeninggal"); // NOI18N
        Ketmeninggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetmeninggalKeyPressed(evt);
            }
        });
        FormInput.add(Ketmeninggal);
        Ketmeninggal.setBounds(160, 1660, 320, 23);

        jLabel45.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel45.setText("Kesadaran :");
        jLabel45.setName("jLabel45"); // NOI18N
        FormInput.add(jLabel45);
        jLabel45.setBounds(50, 1700, 90, 23);

        Kesadaran2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "CM", "Apatis", "Delirium", "Sopor" }));
        Kesadaran2.setName("Kesadaran2"); // NOI18N
        Kesadaran2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Kesadaran2KeyPressed(evt);
            }
        });
        FormInput.add(Kesadaran2);
        Kesadaran2.setBounds(140, 1700, 90, 23);

        jLabel27.setText("TD :");
        jLabel27.setName("jLabel27"); // NOI18N
        FormInput.add(jLabel27);
        jLabel27.setBounds(320, 1700, 40, 23);

        TD1.setFocusTraversalPolicyProvider(true);
        TD1.setName("TD1"); // NOI18N
        TD1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TD1KeyPressed(evt);
            }
        });
        FormInput.add(TD1);
        TD1.setBounds(370, 1700, 70, 23);

        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel31.setText("mmHg");
        jLabel31.setName("jLabel31"); // NOI18N
        FormInput.add(jLabel31);
        jLabel31.setBounds(450, 1700, 50, 23);

        jLabel35.setText("Tanda Vital :");
        jLabel35.setName("jLabel35"); // NOI18N
        FormInput.add(jLabel35);
        jLabel35.setBounds(240, 1700, 72, 23);

        Nadi1.setFocusTraversalPolicyProvider(true);
        Nadi1.setName("Nadi1"); // NOI18N
        Nadi1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Nadi1KeyPressed(evt);
            }
        });
        FormInput.add(Nadi1);
        Nadi1.setBounds(370, 1730, 70, 23);

        jLabel39.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel39.setText("x/menit");
        jLabel39.setName("jLabel39"); // NOI18N
        FormInput.add(jLabel39);
        jLabel39.setBounds(450, 1730, 50, 23);

        jLabel40.setText("RR :");
        jLabel40.setName("jLabel40"); // NOI18N
        FormInput.add(jLabel40);
        jLabel40.setBounds(320, 1760, 40, 23);

        RR1.setFocusTraversalPolicyProvider(true);
        RR1.setName("RR1"); // NOI18N
        RR1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RR1KeyPressed(evt);
            }
        });
        FormInput.add(RR1);
        RR1.setBounds(370, 1760, 70, 23);

        jLabel46.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel46.setText("x/menit");
        jLabel46.setName("jLabel46"); // NOI18N
        FormInput.add(jLabel46);
        jLabel46.setBounds(450, 1760, 50, 23);

        jLabel49.setText("Suhu :");
        jLabel49.setName("jLabel49"); // NOI18N
        FormInput.add(jLabel49);
        jLabel49.setBounds(540, 1700, 50, 23);

        Suhu1.setFocusTraversalPolicyProvider(true);
        Suhu1.setName("Suhu1"); // NOI18N
        Suhu1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Suhu1KeyPressed(evt);
            }
        });
        FormInput.add(Suhu1);
        Suhu1.setBounds(600, 1700, 70, 23);

        jLabel61.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel61.setText("E, M, V");
        jLabel61.setName("jLabel61"); // NOI18N
        FormInput.add(jLabel61);
        jLabel61.setBounds(680, 1730, 50, 23);

        jLabel63.setText("Nadi :");
        jLabel63.setName("jLabel63"); // NOI18N
        FormInput.add(jLabel63);
        jLabel63.setBounds(290, 1730, 70, 23);

        jLabel64.setText("GCS :");
        jLabel64.setName("jLabel64"); // NOI18N
        FormInput.add(jLabel64);
        jLabel64.setBounds(540, 1730, 50, 23);

        GCS1.setFocusTraversalPolicyProvider(true);
        GCS1.setName("GCS1"); // NOI18N
        GCS1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GCS1KeyPressed(evt);
            }
        });
        FormInput.add(GCS1);
        GCS1.setBounds(600, 1730, 70, 23);

        jLabel65.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel65.setText("°C");
        jLabel65.setName("jLabel65"); // NOI18N
        FormInput.add(jLabel65);
        jLabel65.setBounds(680, 1700, 30, 23);

        jLabel66.setText("GCS(E,V,M) :");
        jLabel66.setName("jLabel66"); // NOI18N
        FormInput.add(jLabel66);
        jLabel66.setBounds(720, 450, 70, 23);

        GCS.setFocusTraversalPolicyProvider(true);
        GCS.setName("GCS"); // NOI18N
        GCS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GCSKeyPressed(evt);
            }
        });
        FormInput.add(GCS);
        GCS.setBounds(794, 450, 60, 23);

        scrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane2.setName("scrollPane2"); // NOI18N

        PencetusKeluhan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        PencetusKeluhan.setColumns(20);
        PencetusKeluhan.setRows(5);
        PencetusKeluhan.setName("PencetusKeluhan"); // NOI18N
        PencetusKeluhan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PencetusKeluhanKeyPressed(evt);
            }
        });
        scrollPane2.setViewportView(PencetusKeluhan);

        FormInput.add(scrollPane2);
        scrollPane2.setBounds(219, 210, 278, 53);

        jLabel48.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel48.setText("Keluhan Utama");
        jLabel48.setName("jLabel48"); // NOI18N
        FormInput.add(jLabel48);
        jLabel48.setBounds(44, 90, 90, 23);

        scrollPane3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane3.setName("scrollPane3"); // NOI18N

        GejalaKeluhan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        GejalaKeluhan.setColumns(20);
        GejalaKeluhan.setRows(5);
        GejalaKeluhan.setName("GejalaKeluhan"); // NOI18N
        GejalaKeluhan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GejalaKeluhanKeyPressed(evt);
            }
        });
        scrollPane3.setViewportView(GejalaKeluhan);

        FormInput.add(scrollPane3);
        scrollPane3.setBounds(187, 150, 310, 53);

        jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel34.setText("Gejala Lain Yang Menyertai");
        jLabel34.setName("jLabel34"); // NOI18N
        FormInput.add(jLabel34);
        jLabel34.setBounds(44, 150, 150, 23);

        RPS.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada", "Ada" }));
        RPS.setName("RPS"); // NOI18N
        RPS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RPSKeyPressed(evt);
            }
        });
        FormInput.add(RPS);
        RPS.setBounds(651, 90, 100, 23);

        scrollPane11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane11.setName("scrollPane11"); // NOI18N

        RiwayatKehamilan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        RiwayatKehamilan.setColumns(20);
        RiwayatKehamilan.setRows(5);
        RiwayatKehamilan.setName("RiwayatKehamilan"); // NOI18N
        RiwayatKehamilan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RiwayatKehamilanKeyPressed(evt);
            }
        });
        scrollPane11.setViewportView(RiwayatKehamilan);

        FormInput.add(scrollPane11);
        scrollPane11.setBounds(520, 210, 334, 53);

        jLabel68.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel68.setText("Riwayat Masa Kehamilan, Persalinan & Perkembangan Anak :");
        jLabel68.setName("jLabel68"); // NOI18N
        FormInput.add(jLabel68);
        jLabel68.setBounds(520, 190, 330, 23);

        jLabel69.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel69.setText("Riwayat Sosial & Riwayat Pekerjaan :");
        jLabel69.setName("jLabel69"); // NOI18N
        FormInput.add(jLabel69);
        jLabel69.setBounds(44, 270, 200, 23);

        Sosial.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Bergaul", "Tidak Bergaul", "Lain-lain" }));
        Sosial.setName("Sosial"); // NOI18N
        Sosial.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SosialKeyPressed(evt);
            }
        });
        FormInput.add(Sosial);
        Sosial.setBounds(134, 290, 120, 23);

        KetSosial.setFocusTraversalPolicyProvider(true);
        KetSosial.setName("KetSosial"); // NOI18N
        KetSosial.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetSosialKeyPressed(evt);
            }
        });
        FormInput.add(KetSosial);
        KetSosial.setBounds(256, 290, 241, 23);

        jLabel70.setText("Sosial :");
        jLabel70.setName("jLabel70"); // NOI18N
        FormInput.add(jLabel70);
        jLabel70.setBounds(0, 290, 130, 23);

        jLabel71.setText("Pekerjaan :");
        jLabel71.setName("jLabel71"); // NOI18N
        FormInput.add(jLabel71);
        jLabel71.setBounds(0, 320, 130, 23);

        Pekerjaan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Bekerja", "Tidak Bekerja", "Ganti-gantian Pekerjaan" }));
        Pekerjaan.setName("Pekerjaan"); // NOI18N
        Pekerjaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PekerjaanKeyPressed(evt);
            }
        });
        FormInput.add(Pekerjaan);
        Pekerjaan.setBounds(134, 320, 170, 23);

        KetPekerjaan.setFocusTraversalPolicyProvider(true);
        KetPekerjaan.setName("KetPekerjaan"); // NOI18N
        KetPekerjaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetPekerjaanKeyPressed(evt);
            }
        });
        FormInput.add(KetPekerjaan);
        KetPekerjaan.setBounds(306, 320, 191, 23);

        jLabel72.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel72.setText("Faktor Keturunan");
        jLabel72.setName("jLabel72"); // NOI18N
        FormInput.add(jLabel72);
        jLabel72.setBounds(520, 300, 100, 23);

        Keturunan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada", "Ada" }));
        Keturunan.setName("Keturunan"); // NOI18N
        Keturunan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeturunanKeyPressed(evt);
            }
        });
        FormInput.add(Keturunan);
        Keturunan.setBounds(615, 300, 100, 23);

        FaktorPremorbid.setFocusTraversalPolicyProvider(true);
        FaktorPremorbid.setName("FaktorPremorbid"); // NOI18N
        FaktorPremorbid.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FaktorPremorbidKeyPressed(evt);
            }
        });
        FormInput.add(FaktorPremorbid);
        FaktorPremorbid.setBounds(676, 270, 178, 23);

        KetKeturunan.setFocusTraversalPolicyProvider(true);
        KetKeturunan.setName("KetKeturunan"); // NOI18N
        KetKeturunan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetKeturunanKeyPressed(evt);
            }
        });
        FormInput.add(KetKeturunan);
        KetKeturunan.setBounds(717, 300, 137, 23);

        jLabel73.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel73.setText("Faktor Kepribadian Premorbid");
        jLabel73.setName("jLabel73"); // NOI18N
        FormInput.add(jLabel73);
        jLabel73.setBounds(520, 270, 160, 23);

        jLabel74.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel74.setText("Faktor Organik");
        jLabel74.setName("jLabel74"); // NOI18N
        FormInput.add(jLabel74);
        jLabel74.setBounds(520, 330, 90, 23);

        FaktorOrganik.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada", "Ada" }));
        FaktorOrganik.setName("FaktorOrganik"); // NOI18N
        FaktorOrganik.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FaktorOrganikKeyPressed(evt);
            }
        });
        FormInput.add(FaktorOrganik);
        FaktorOrganik.setBounds(605, 330, 100, 23);

        KetOrganik.setFocusTraversalPolicyProvider(true);
        KetOrganik.setName("KetOrganik"); // NOI18N
        KetOrganik.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetOrganikKeyPressed(evt);
            }
        });
        FormInput.add(KetOrganik);
        KetOrganik.setBounds(707, 330, 147, 23);

        jLabel24.setText("TD :");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(244, 420, 30, 23);

        jLabel114.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel114.setText("IV. STATUS LOKALISATA");
        jLabel114.setName("jLabel114"); // NOI18N
        FormInput.add(jLabel114);
        jLabel114.setBounds(10, 590, 190, 23);

        KeteranganDada.setFocusTraversalPolicyProvider(true);
        KeteranganDada.setName("KeteranganDada"); // NOI18N
        KeteranganDada.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganDadaKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganDada);
        KeteranganDada.setBounds(220, 560, 200, 23);

        jLabel116.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel116.setText("Keterangan :");
        jLabel116.setName("jLabel116"); // NOI18N
        FormInput.add(jLabel116);
        jLabel116.setBounds(614, 610, 210, 23);

        jLabel117.setText("Sikap & Perilaku :");
        jLabel117.setName("jLabel117"); // NOI18N
        FormInput.add(jLabel117);
        jLabel117.setBounds(0, 960, 130, 23);

        Sikap.setFocusTraversalPolicyProvider(true);
        Sikap.setName("Sikap"); // NOI18N
        Sikap.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SikapKeyPressed(evt);
            }
        });
        FormInput.add(Sikap);
        Sikap.setBounds(134, 960, 310, 23);

        jLabel119.setText("Kesadaran :");
        jLabel119.setName("jLabel119"); // NOI18N
        FormInput.add(jLabel119);
        jLabel119.setBounds(0, 990, 130, 23);

        Kesadaran1.setFocusTraversalPolicyProvider(true);
        Kesadaran1.setName("Kesadaran1"); // NOI18N
        Kesadaran1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Kesadaran1KeyPressed(evt);
            }
        });
        FormInput.add(Kesadaran1);
        Kesadaran1.setBounds(134, 990, 310, 23);

        jLabel120.setText("Orientasi :");
        jLabel120.setName("jLabel120"); // NOI18N
        FormInput.add(jLabel120);
        jLabel120.setBounds(0, 1020, 130, 23);

        Orientasi.setFocusTraversalPolicyProvider(true);
        Orientasi.setName("Orientasi"); // NOI18N
        Orientasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                OrientasiKeyPressed(evt);
            }
        });
        FormInput.add(Orientasi);
        Orientasi.setBounds(134, 1020, 310, 23);

        jLabel121.setText("Daya Ingat :");
        jLabel121.setName("jLabel121"); // NOI18N
        FormInput.add(jLabel121);
        jLabel121.setBounds(450, 930, 90, 23);

        DayaIngat.setFocusTraversalPolicyProvider(true);
        DayaIngat.setName("DayaIngat"); // NOI18N
        DayaIngat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DayaIngatKeyPressed(evt);
            }
        });
        FormInput.add(DayaIngat);
        DayaIngat.setBounds(544, 930, 310, 23);

        jLabel122.setText("Persepsi :");
        jLabel122.setName("jLabel122"); // NOI18N
        FormInput.add(jLabel122);
        jLabel122.setBounds(450, 960, 90, 23);

        Persepsi.setFocusTraversalPolicyProvider(true);
        Persepsi.setName("Persepsi"); // NOI18N
        Persepsi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PersepsiKeyPressed(evt);
            }
        });
        FormInput.add(Persepsi);
        Persepsi.setBounds(544, 960, 310, 23);

        jLabel123.setText("Pikiran :");
        jLabel123.setName("jLabel123"); // NOI18N
        FormInput.add(jLabel123);
        jLabel123.setBounds(450, 990, 90, 23);

        Pikiran.setFocusTraversalPolicyProvider(true);
        Pikiran.setName("Pikiran"); // NOI18N
        Pikiran.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PikiranKeyPressed(evt);
            }
        });
        FormInput.add(Pikiran);
        Pikiran.setBounds(544, 990, 310, 23);

        jLabel124.setText("Insight :");
        jLabel124.setName("jLabel124"); // NOI18N
        FormInput.add(jLabel124);
        jLabel124.setBounds(450, 1020, 90, 23);

        Insight.setFocusTraversalPolicyProvider(true);
        Insight.setName("Insight"); // NOI18N
        Insight.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                InsightKeyPressed(evt);
            }
        });
        FormInput.add(Insight);
        Insight.setBounds(544, 1020, 310, 23);

        scrollPane12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane12.setName("scrollPane12"); // NOI18N

        Rencana.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Rencana.setColumns(20);
        Rencana.setRows(15);
        Rencana.setName("Rencana"); // NOI18N
        Rencana.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RencanaKeyPressed(evt);
            }
        });
        scrollPane12.setViewportView(Rencana);

        FormInput.add(scrollPane12);
        scrollPane12.setBounds(44, 1340, 810, 43);

        jLabel130.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel130.setText("V. STATUS PSIKIATRIK");
        jLabel130.setName("jLabel130"); // NOI18N
        FormInput.add(jLabel130);
        jLabel130.setBounds(10, 910, 190, 23);

        jLabel131.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel131.setText("VIII. PERMASALAHAN & TATALAKSANA");
        jLabel131.setName("jLabel131"); // NOI18N
        FormInput.add(jLabel131);
        jLabel131.setBounds(10, 1230, 240, 23);

        jLabel62.setText(":");
        jLabel62.setName("jLabel62"); // NOI18N
        FormInput.add(jLabel62);
        jLabel62.setBounds(0, 90, 125, 23);

        jLabel43.setText(":");
        jLabel43.setName("jLabel43"); // NOI18N
        FormInput.add(jLabel43);
        jLabel43.setBounds(0, 150, 183, 23);

        jLabel75.setText(":");
        jLabel75.setName("jLabel75"); // NOI18N
        FormInput.add(jLabel75);
        jLabel75.setBounds(0, 210, 215, 23);

        jLabel32.setText(":");
        jLabel32.setName("jLabel32"); // NOI18N
        FormInput.add(jLabel32);
        jLabel32.setBounds(520, 90, 127, 23);

        jLabel76.setText(":");
        jLabel76.setName("jLabel76"); // NOI18N
        FormInput.add(jLabel76);
        jLabel76.setBounds(0, 350, 231, 23);

        jLabel77.setText(":");
        jLabel77.setName("jLabel77"); // NOI18N
        FormInput.add(jLabel77);
        jLabel77.setBounds(510, 270, 162, 23);

        jLabel78.setText(":");
        jLabel78.setName("jLabel78"); // NOI18N
        FormInput.add(jLabel78);
        jLabel78.setBounds(510, 300, 101, 23);

        jLabel79.setText(":");
        jLabel79.setName("jLabel79"); // NOI18N
        FormInput.add(jLabel79);
        jLabel79.setBounds(510, 330, 91, 23);

        jLabel82.setText(":");
        jLabel82.setName("jLabel82"); // NOI18N
        FormInput.add(jLabel82);
        jLabel82.setBounds(510, 360, 88, 23);

        jSeparator2.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator2.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator2.setName("jSeparator2"); // NOI18N
        FormInput.add(jSeparator2);
        jSeparator2.setBounds(0, 400, 880, 1);

        jSeparator3.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator3.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator3.setName("jSeparator3"); // NOI18N
        FormInput.add(jSeparator3);
        jSeparator3.setBounds(0, 480, 880, 1);

        jSeparator4.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator4.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator4.setName("jSeparator4"); // NOI18N
        FormInput.add(jSeparator4);
        jSeparator4.setBounds(0, 590, 880, 1);

        jSeparator5.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator5.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator5.setName("jSeparator5"); // NOI18N
        FormInput.add(jSeparator5);
        jSeparator5.setBounds(0, 910, 880, 1);

        jSeparator6.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator6.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator6.setName("jSeparator6"); // NOI18N
        FormInput.add(jSeparator6);
        jSeparator6.setBounds(0, 1050, 880, 1);

        jLabel101.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel101.setText("VI. PEMERIKSAAN PENUNJANG");
        jLabel101.setName("jLabel101"); // NOI18N
        FormInput.add(jLabel101);
        jLabel101.setBounds(10, 1050, 190, 23);

        jSeparator7.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator7.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator7.setName("jSeparator7"); // NOI18N
        FormInput.add(jSeparator7);
        jSeparator7.setBounds(0, 1160, 880, 1);

        jLabel102.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel102.setText("VII. DIAGNOSIS/ASESMEN");
        jLabel102.setName("jLabel102"); // NOI18N
        FormInput.add(jLabel102);
        jLabel102.setBounds(10, 1160, 190, 23);

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
        scrollPane13.setBounds(44, 1180, 810, 43);

        jSeparator8.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator8.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator8.setName("jSeparator8"); // NOI18N
        FormInput.add(jSeparator8);
        jSeparator8.setBounds(0, 1230, 880, 1);

        jLabel113.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel113.setText("Permasalahan :");
        jLabel113.setName("jLabel113"); // NOI18N
        FormInput.add(jLabel113);
        jLabel113.setBounds(44, 1250, 190, 20);

        jSeparator9.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator9.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator9.setName("jSeparator9"); // NOI18N
        FormInput.add(jSeparator9);
        jSeparator9.setBounds(0, 1390, 880, 1);

        jLabel132.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel132.setText("IX. STATUS PULANG/RUJUK");
        jLabel132.setName("jLabel132"); // NOI18N
        FormInput.add(jLabel132);
        jLabel132.setBounds(10, 1390, 240, 23);

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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "28-08-2023" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "28-08-2023" }));
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
        }else if(KetRPS.getText().trim().equals("")){
            Valid.textKosong(RPS,"Riwayat Penyakit Dahulu");
        }else{
            if(Sequel.menyimpantf("penilaian_medis_ralan_gawat_darurat_psikiatri","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat",74,new String[]{
                    TNoRw.getText(),Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),KdDokter.getText(),Anamnesis.getSelectedItem().toString(),Hubungan.getText(),
                    KeluhanUtama.getText(),GejalaKeluhan.getText(),PencetusKeluhan.getText(),RPS.getSelectedItem().toString(),KetRPS.getText(),RiwayatKehamilan.getText(),Sosial.getSelectedItem().toString(),KetSosial.getText(),Pekerjaan.getSelectedItem().toString(),KetPekerjaan.getText(),
                    RPO.getText(),FaktorPremorbid.getText(),Keturunan.getSelectedItem().toString(),KetKeturunan.getText(),FaktorOrganik.getSelectedItem().toString(),KetOrganik.getText(),Alergi.getText(),Kesadaran.getSelectedItem().toString(),Nyeri.getSelectedItem().toString(),TD.getText(),Nadi.getText(), 
                    Suhu.getText(),RR.getText(),BB.getText(),TB.getText(),GCS.getText(),StatusNutrisi.getText(),Kepala.getSelectedItem().toString(),KeteranganKepala.getText(),Leher.getSelectedItem().toString(),KeteranganLeher.getText(),Gerak.getSelectedItem().toString(),KeteranganGerak.getText(),
                    Dada.getSelectedItem().toString(),KeteranganDada.getText(),Perut.getSelectedItem().toString(),KeteranganPerut.getText(),KeteranganBadan.getText(),KesanUmum.getText(),Sikap.getText(),Kesadaran1.getText(),Orientasi.getText(),DayaIngat.getText(),Persepsi.getText(),Pikiran.getText(),Insight.getText(),
                    Laborat.getText(),Radiologi.getText(),Penunjang.getText(),Permasalahan.getText(),Rencana.getText(),Instruksi.getText(),Dipulangkan.getSelectedItem().toString(),Ketdipulangkan.getText(),Ketruang.getText(),Ketindikasi.getText(),Ketdirujuk.getText(),Alasandirujuk.getSelectedItem().toString(),
                    Pulangpaksa.getSelectedItem().toString(),KetPulangpaksa.getText(),Meninggal.getSelectedItem().toString(),Ketmeninggal.getText(),Kesadaran2.getSelectedItem().toString(),TD1.getText(),Nadi1.getText(),RR1.getText(),Suhu1.getText(),GCS1.getText(),Edukasi.getText()
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
        }else if(KetRPS.getText().trim().equals("")){
            Valid.textKosong(KetRPS,"Riwayat Penyakit Dahulu");
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
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Gejala Keluhan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Pencetus Keluhan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Riwayat Penyakit Dahulu</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Keterangan Riwayat Penyakit Dahulu</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Riwayat Kehamilan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Sosial</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Keterangan Sosial</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Pekerjaan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Keterangan Pekerjaan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Riwayat Pemberian Obat</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Faktor Premorbid</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Keturunan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Keterangan Keturunan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Faktor Organik</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Organik</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Alergi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Kesadaran</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Skala Nyeri</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Tensi(mmHg)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Nadi(x/menit)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Suhu(°C)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>RR(x/menit)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>BB(Kg)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>TB(Cm)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Status Nutrisi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Kepala</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Keterangan Kepala</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Leher</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Keterangan Leher</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Gerak</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Keterangan Gerak</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Dada</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Keterangan Dada</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Perut</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Keterangan Perut</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>KeteranganBadan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Kesan Umum</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Sikap</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Kesadaran 1</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Orientasi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Daya Ingat</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Persepsi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Pikiran</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Insight</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Laborat</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Radiologi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Penunjang</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Permasalahan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Rencana</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Instruksi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Dipulangkan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Keterangan Dipulangkan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Keterangan Ruang</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Keterangan Indikasi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Keterangan Dirujuk</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Alasan Dirujuk</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Pulang Paksa</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Keterangan Pulang Paksa</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Meninggal</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Keterangan Meninggal</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Kesadaran 2</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>TD 1</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Nadi 1</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>RR 1</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Suhu 1</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>GCS 1</b></td>"+
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
                            "<td valign='top'>"+tbObat.getValueAt(i,52).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,53).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,54).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,55).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,56).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,57).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,58).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,59).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,60).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,61).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,62).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,63).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,64).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,65).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,66).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,67).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,68).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,69).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,70).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,71).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,72).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,73).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,74).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,75).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,76).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,77).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,78).toString()+"</td>"+
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

                File f = new File("DataPenilaianAwalMedisRalanGawatDaruratPsikiatri.html");            
                BufferedWriter bw = new BufferedWriter(new FileWriter(f));            
                bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                            "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                            "<table width='4200px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                "<tr class='isi2'>"+
                                    "<td valign='top' align='center'>"+
                                        "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                        akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                        akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                        "<font size='2' face='Tahoma'>DATA PENILAIAN AWAL MEDIS RAWAT JALAN GAWAT DARURAT PSIKIATRI<br><br></font>"+        
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
        Valid.pindah(evt,RPS,KetRPS);
    }//GEN-LAST:event_AlergiKeyPressed

    private void AnamnesisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AnamnesisKeyPressed
        Valid.pindah(evt,TglAsuhan,Hubungan);
    }//GEN-LAST:event_AnamnesisKeyPressed

    private void KeluhanUtamaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeluhanUtamaKeyPressed
        Valid.pindah2(evt,Hubungan,RPS);
    }//GEN-LAST:event_KeluhanUtamaKeyPressed

    private void RPOKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RPOKeyPressed
        Valid.pindah2(evt,RPS,Alergi);
    }//GEN-LAST:event_RPOKeyPressed

    private void TBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TBKeyPressed
        Valid.pindah(evt,BB,StatusNutrisi);
    }//GEN-LAST:event_TBKeyPressed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if(TabRawat.getSelectedIndex()==1){
            tampil();
        }
    }//GEN-LAST:event_TabRawatMouseClicked

    private void KetRPSKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetRPSKeyPressed
        Valid.pindah2(evt,KeluhanUtama,RPS);
    }//GEN-LAST:event_KetRPSKeyPressed

    private void KesadaranKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KesadaranKeyPressed
        Valid.pindah(evt,KetRPS,Nyeri);
    }//GEN-LAST:event_KesadaranKeyPressed

    private void StatusNutrisiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_StatusNutrisiKeyPressed
        Valid.pindah(evt,TB,Perut);
    }//GEN-LAST:event_StatusNutrisiKeyPressed

    private void KepalaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KepalaKeyPressed
        Valid.pindah(evt,KeteranganDada,KeteranganKepala);
    }//GEN-LAST:event_KepalaKeyPressed

    private void GerakKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GerakKeyPressed
        Valid.pindah(evt,KeteranganLeher,KeteranganGerak);
    }//GEN-LAST:event_GerakKeyPressed

    private void DadaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DadaKeyPressed
        Valid.pindah(evt,KeteranganGerak,KeteranganDada);
    }//GEN-LAST:event_DadaKeyPressed

    private void LeherKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LeherKeyPressed
        Valid.pindah(evt,KeteranganPerut,KeteranganLeher);
    }//GEN-LAST:event_LeherKeyPressed

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
            param.put("wajah1",Sequel.cariGambar("select gambar.wajah1 from gambar")); 
            param.put("wajah2",Sequel.cariGambar("select gambar.wajah2 from gambar")); 
            finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
            param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbObat.getValueAt(tbObat.getSelectedRow(),6).toString()+"\nID "+(finger.equals("")?tbObat.getValueAt(tbObat.getSelectedRow(),5).toString():finger)+"\n"+Valid.SetTgl3(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString())); 
            
            Valid.MyReportqry("rptCetakPenilaianAwalMedisRalanGawatDaruratPsikiatri.jasper","report","::[ Laporan Penilaian Awal Medis Rawat Jalan Bedah Mulut ]::",
                "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penilaian_medis_ralan_gawat_darurat_psikiatri.tanggal,"+
                "penilaian_medis_ralan_gawat_darurat_psikiatri.kd_dokter,penilaian_medis_ralan_gawat_darurat_psikiatri.anamnesis,penilaian_medis_ralan_gawat_darurat_psikiatri.hubungan,penilaian_medis_ralan_gawat_darurat_psikiatri.keluhan_utama,"+
                "penilaian_medis_ralan_gawat_darurat_psikiatri.gejala_keluhan,penilaian_medis_ralan_gawat_darurat_psikiatri.pencetus_keluhan,penilaian_medis_ralan_gawat_darurat_psikiatri.rps,penilaian_medis_ralan_gawat_darurat_psikiatri.keterangan_rps,"+
                "penilaian_medis_ralan_gawat_darurat_psikiatri.riwayat_kehamilan,penilaian_medis_ralan_gawat_darurat_psikiatri.sosial,penilaian_medis_ralan_gawat_darurat_psikiatri.ket_sosial,penilaian_medis_ralan_gawat_darurat_psikiatri.pekerjaan,penilaian_medis_ralan_gawat_darurat_psikiatri.ket_pekerjaan,"+
                "penilaian_medis_ralan_gawat_darurat_psikiatri.rpo,penilaian_medis_ralan_gawat_darurat_psikiatri.faktor_premorbid,penilaian_medis_ralan_gawat_darurat_psikiatri.keturunan,penilaian_medis_ralan_gawat_darurat_psikiatri.ket_keturunan,penilaian_medis_ralan_gawat_darurat_psikiatri.faktor_organik,"+
                "penilaian_medis_ralan_gawat_darurat_psikiatri.ket_organik,penilaian_medis_ralan_gawat_darurat_psikiatri.alergi,penilaian_medis_ralan_gawat_darurat_psikiatri.kesadaran,penilaian_medis_ralan_gawat_darurat_psikiatri.nyeri,penilaian_medis_ralan_gawat_darurat_psikiatri.td,"+
                "penilaian_medis_ralan_gawat_darurat_psikiatri.nadi,penilaian_medis_ralan_gawat_darurat_psikiatri.suhu,penilaian_medis_ralan_gawat_darurat_psikiatri.rr,penilaian_medis_ralan_gawat_darurat_psikiatri.bb,penilaian_medis_ralan_gawat_darurat_psikiatri.tb,penilaian_medis_ralan_gawat_darurat_psikiatri.gcs,"+
                "penilaian_medis_ralan_gawat_darurat_psikiatri.status_nutrisi,penilaian_medis_ralan_gawat_darurat_psikiatri.kepala,penilaian_medis_ralan_gawat_darurat_psikiatri.keterangan_kepala,penilaian_medis_ralan_gawat_darurat_psikiatri.leher,penilaian_medis_ralan_gawat_darurat_psikiatri.keterangan_leher,"+
                "penilaian_medis_ralan_gawat_darurat_psikiatri.anggota_gerak,penilaian_medis_ralan_gawat_darurat_psikiatri.keterangan_gerak,penilaian_medis_ralan_gawat_darurat_psikiatri.dada,penilaian_medis_ralan_gawat_darurat_psikiatri.keterangan_dada,"+
                "penilaian_medis_ralan_gawat_darurat_psikiatri.perut,penilaian_medis_ralan_gawat_darurat_psikiatri.keterangan_perut,penilaian_medis_ralan_gawat_darurat_psikiatri.keterangan_badan,penilaian_medis_ralan_gawat_darurat_psikiatri.kesan_utama,penilaian_medis_ralan_gawat_darurat_psikiatri.sikap,"+
                "penilaian_medis_ralan_gawat_darurat_psikiatri.kesadaran1,penilaian_medis_ralan_gawat_darurat_psikiatri.orientasi,penilaian_medis_ralan_gawat_darurat_psikiatri.daya_ingat,penilaian_medis_ralan_gawat_darurat_psikiatri.persepsi,penilaian_medis_ralan_gawat_darurat_psikiatri.pikiran,penilaian_medis_ralan_gawat_darurat_psikiatri.insight,"+
                "penilaian_medis_ralan_gawat_darurat_psikiatri.lab,penilaian_medis_ralan_gawat_darurat_psikiatri.rad,penilaian_medis_ralan_gawat_darurat_psikiatri.penunjang,penilaian_medis_ralan_gawat_darurat_psikiatri.permasalahan,penilaian_medis_ralan_gawat_darurat_psikiatri.rencana,penilaian_medis_ralan_gawat_darurat_psikiatri.instruksi,"+
                "penilaian_medis_ralan_gawat_darurat_psikiatri.dipulangkan,penilaian_medis_ralan_gawat_darurat_psikiatri.ket_dipulangkan,penilaian_medis_ralan_gawat_darurat_psikiatri.ket_ruang,penilaian_medis_ralan_gawat_darurat_psikiatri.ket_indikasi,penilaian_medis_ralan_gawat_darurat_psikiatri.ket_dirujuk,"+
                "penilaian_medis_ralan_gawat_darurat_psikiatri.ket_alasandirujuk,penilaian_medis_ralan_gawat_darurat_psikiatri.pulang_paksa,penilaian_medis_ralan_gawat_darurat_psikiatri.ket_pulangpaksa,penilaian_medis_ralan_gawat_darurat_psikiatri.meninggal,penilaian_medis_ralan_gawat_darurat_psikiatri.ket_meninggal,"+
                "penilaian_medis_ralan_gawat_darurat_psikiatri.kesadaran2,penilaian_medis_ralan_gawat_darurat_psikiatri.td2,penilaian_medis_ralan_gawat_darurat_psikiatri.nadi2,penilaian_medis_ralan_gawat_darurat_psikiatri.rr2,penilaian_medis_ralan_gawat_darurat_psikiatri.suhu2,penilaian_medis_ralan_gawat_darurat_psikiatri.gcs2,"+
                "penilaian_medis_ralan_gawat_darurat_psikiatri.edukasi,dokter.nm_dokter "+
                "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join penilaian_medis_ralan_gawat_darurat_psikiatri on reg_periksa.no_rawat=penilaian_medis_ralan_gawat_darurat_psikiatri.no_rawat "+
                "inner join dokter on penilaian_medis_ralan_gawat_darurat_psikiatri.kd_dokter=dokter.kd_dokter where penilaian_medis_ralan_gawat_darurat_psikiatri.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'",param);
        }
    }//GEN-LAST:event_MnPenilaianMedisActionPerformed

    private void NyeriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NyeriKeyPressed
         Valid.pindah(evt,Kesadaran,TD);
    }//GEN-LAST:event_NyeriKeyPressed

    private void PerutKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PerutKeyPressed
        Valid.pindah(evt,KeteranganDada,KeteranganPerut);
    }//GEN-LAST:event_PerutKeyPressed

    private void KeteranganKepalaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKepalaKeyPressed
        Valid.pindah(evt,Kepala,Perut);
    }//GEN-LAST:event_KeteranganKepalaKeyPressed

    private void KeteranganLeherKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganLeherKeyPressed
        Valid.pindah(evt,Leher,Gerak);
    }//GEN-LAST:event_KeteranganLeherKeyPressed

    private void KeteranganGerakKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganGerakKeyPressed
        Valid.pindah(evt,Gerak,Dada);
    }//GEN-LAST:event_KeteranganGerakKeyPressed

    private void KesanUmumKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KesanUmumKeyPressed
        Valid.pindah(evt,Dada,Perut);
    }//GEN-LAST:event_KesanUmumKeyPressed

    private void KeteranganPerutKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganPerutKeyPressed
        Valid.pindah(evt,Perut,Gerak);
    }//GEN-LAST:event_KeteranganPerutKeyPressed

    private void KeteranganBadanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganBadanKeyPressed
        Valid.pindah2(evt,KeteranganGerak,KeteranganBadan);
    }//GEN-LAST:event_KeteranganBadanKeyPressed

    private void PenunjangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenunjangKeyPressed
        Valid.pindah2(evt,Radiologi,Rencana);
    }//GEN-LAST:event_PenunjangKeyPressed

    private void LaboratKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LaboratKeyPressed
        Valid.pindah2(evt,KeteranganBadan,Radiologi);
    }//GEN-LAST:event_LaboratKeyPressed

    private void RadiologiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RadiologiKeyPressed
        Valid.pindah2(evt,Laborat,Penunjang);
    }//GEN-LAST:event_RadiologiKeyPressed

    private void EdukasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EdukasiKeyPressed
        Valid.pindah2(evt,Edukasi,BtnSimpan);
    }//GEN-LAST:event_EdukasiKeyPressed

    private void InstruksiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_InstruksiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_InstruksiKeyPressed

    private void PermasalahanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PermasalahanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PermasalahanKeyPressed

    private void DipulangkanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DipulangkanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DipulangkanKeyPressed

    private void KetruangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetruangKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KetruangKeyPressed

    private void KetPulangpaksaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetPulangpaksaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KetPulangpaksaKeyPressed

    private void KetindikasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetindikasiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KetindikasiKeyPressed

    private void AlasandirujukKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlasandirujukKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_AlasandirujukKeyPressed

    private void KetdirujukKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetdirujukKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KetdirujukKeyPressed

    private void MeninggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MeninggalKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_MeninggalKeyPressed

    private void KetdipulangkanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetdipulangkanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KetdipulangkanKeyPressed

    private void PulangpaksaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PulangpaksaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PulangpaksaKeyPressed

    private void KetmeninggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetmeninggalKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KetmeninggalKeyPressed

    private void Kesadaran2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kesadaran2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Kesadaran2KeyPressed

    private void TD1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TD1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TD1KeyPressed

    private void Nadi1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Nadi1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Nadi1KeyPressed

    private void RR1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RR1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_RR1KeyPressed

    private void Suhu1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Suhu1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Suhu1KeyPressed

    private void GCS1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GCS1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_GCS1KeyPressed

    private void GCSKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GCSKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_GCSKeyPressed

    private void PencetusKeluhanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PencetusKeluhanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PencetusKeluhanKeyPressed

    private void GejalaKeluhanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GejalaKeluhanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_GejalaKeluhanKeyPressed

    private void RPSKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RPSKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_RPSKeyPressed

    private void RiwayatKehamilanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RiwayatKehamilanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_RiwayatKehamilanKeyPressed

    private void SosialKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SosialKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SosialKeyPressed

    private void KetSosialKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetSosialKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KetSosialKeyPressed

    private void PekerjaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PekerjaanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PekerjaanKeyPressed

    private void KetPekerjaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetPekerjaanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KetPekerjaanKeyPressed

    private void KeturunanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeturunanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeturunanKeyPressed

    private void FaktorPremorbidKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FaktorPremorbidKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_FaktorPremorbidKeyPressed

    private void KetKeturunanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetKeturunanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KetKeturunanKeyPressed

    private void FaktorOrganikKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FaktorOrganikKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_FaktorOrganikKeyPressed

    private void KetOrganikKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetOrganikKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KetOrganikKeyPressed

    private void KeteranganDadaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganDadaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganDadaKeyPressed

    private void SikapKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SikapKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SikapKeyPressed

    private void Kesadaran1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kesadaran1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Kesadaran1KeyPressed

    private void OrientasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_OrientasiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_OrientasiKeyPressed

    private void DayaIngatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DayaIngatKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DayaIngatKeyPressed

    private void PersepsiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PersepsiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PersepsiKeyPressed

    private void PikiranKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PikiranKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PikiranKeyPressed

    private void InsightKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_InsightKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_InsightKeyPressed

    private void RencanaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RencanaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_RencanaKeyPressed

    private void DiagnosisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosisKeyPressed
        //Valid.pindah2(evt,Laborat,Tatalaksana);
    }//GEN-LAST:event_DiagnosisKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMPenilaianAwalMedisIGDPsikiatri dialog = new RMPenilaianAwalMedisIGDPsikiatri(new javax.swing.JFrame(), true);
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
    private widget.ComboBox Alasandirujuk;
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
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.ComboBox Dada;
    private widget.TextBox DayaIngat;
    private widget.TextArea Diagnosis;
    private widget.ComboBox Dipulangkan;
    private widget.TextArea Edukasi;
    private widget.ComboBox FaktorOrganik;
    private widget.TextBox FaktorPremorbid;
    private widget.PanelBiasa FormInput;
    private widget.TextBox GCS;
    private widget.TextBox GCS1;
    private widget.TextArea GejalaKeluhan;
    private widget.ComboBox Gerak;
    private widget.TextBox Hubungan;
    private widget.TextBox Insight;
    private widget.TextArea Instruksi;
    private widget.TextBox Jk;
    private widget.TextBox KdDokter;
    private widget.TextArea KeluhanUtama;
    private widget.ComboBox Kepala;
    private widget.ComboBox Kesadaran;
    private widget.TextBox Kesadaran1;
    private widget.ComboBox Kesadaran2;
    private widget.TextBox KesanUmum;
    private widget.TextBox KetKeturunan;
    private widget.TextBox KetOrganik;
    private widget.TextBox KetPekerjaan;
    private widget.TextBox KetPulangpaksa;
    private widget.TextArea KetRPS;
    private widget.TextBox KetSosial;
    private widget.TextBox Ketdipulangkan;
    private widget.TextBox Ketdirujuk;
    private widget.TextArea KeteranganBadan;
    private widget.TextBox KeteranganDada;
    private widget.TextBox KeteranganGerak;
    private widget.TextBox KeteranganKepala;
    private widget.TextBox KeteranganLeher;
    private widget.TextBox KeteranganPerut;
    private widget.TextBox Ketindikasi;
    private widget.TextBox Ketmeninggal;
    private widget.TextBox Ketruang;
    private widget.ComboBox Keturunan;
    private widget.Label LCount;
    private widget.TextArea Laborat;
    private widget.ComboBox Leher;
    private widget.editorpane LoadHTML;
    private widget.ComboBox Meninggal;
    private javax.swing.JMenuItem MnPenilaianMedis;
    private widget.TextBox Nadi;
    private widget.TextBox Nadi1;
    private widget.TextBox NmDokter;
    private widget.ComboBox Nyeri;
    private widget.TextBox Orientasi;
    private usu.widget.glass.PanelGlass PanelWall2;
    private widget.ComboBox Pekerjaan;
    private widget.TextArea PencetusKeluhan;
    private widget.TextArea Penunjang;
    private widget.TextArea Permasalahan;
    private widget.TextBox Persepsi;
    private widget.ComboBox Perut;
    private widget.TextBox Pikiran;
    private widget.ComboBox Pulangpaksa;
    private widget.TextArea RPO;
    private widget.ComboBox RPS;
    private widget.TextBox RR;
    private widget.TextBox RR1;
    private widget.TextArea Radiologi;
    private widget.TextArea Rencana;
    private widget.TextArea RiwayatKehamilan;
    private widget.ScrollPane Scroll;
    private widget.TextBox Sikap;
    private widget.ComboBox Sosial;
    private widget.TextBox StatusNutrisi;
    private widget.TextBox Suhu;
    private widget.TextBox Suhu1;
    private widget.TextBox TB;
    private widget.TextBox TCari;
    private widget.TextBox TD;
    private widget.TextBox TD1;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private javax.swing.JTabbedPane TabRawat;
    private widget.Tanggal TglAsuhan;
    private widget.TextBox TglLahir;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.Label jLabel10;
    private widget.Label jLabel101;
    private widget.Label jLabel102;
    private widget.Label jLabel11;
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
    private widget.Label jLabel13;
    private widget.Label jLabel130;
    private widget.Label jLabel131;
    private widget.Label jLabel132;
    private widget.Label jLabel14;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
    private widget.Label jLabel18;
    private widget.Label jLabel19;
    private widget.Label jLabel20;
    private widget.Label jLabel21;
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
    private widget.Label jLabel94;
    private widget.Label jLabel95;
    private widget.Label jLabel99;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private widget.Label label11;
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
    private widget.ScrollPane scrollPane15;
    private widget.ScrollPane scrollPane2;
    private widget.ScrollPane scrollPane3;
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
                "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penilaian_medis_ralan_gawat_darurat_psikiatri.tanggal,"+
                "penilaian_medis_ralan_gawat_darurat_psikiatri.kd_dokter,penilaian_medis_ralan_gawat_darurat_psikiatri.anamnesis,penilaian_medis_ralan_gawat_darurat_psikiatri.hubungan,penilaian_medis_ralan_gawat_darurat_psikiatri.keluhan_utama,"+
                "penilaian_medis_ralan_gawat_darurat_psikiatri.gejala_keluhan,penilaian_medis_ralan_gawat_darurat_psikiatri.pencetus_keluhan,penilaian_medis_ralan_gawat_darurat_psikiatri.rps,penilaian_medis_ralan_gawat_darurat_psikiatri.keterangan_rps,"+
                "penilaian_medis_ralan_gawat_darurat_psikiatri.riwayat_kehamilan,penilaian_medis_ralan_gawat_darurat_psikiatri.sosial,penilaian_medis_ralan_gawat_darurat_psikiatri.ket_sosial,penilaian_medis_ralan_gawat_darurat_psikiatri.pekerjaan,penilaian_medis_ralan_gawat_darurat_psikiatri.ket_pekerjaan,"+
                "penilaian_medis_ralan_gawat_darurat_psikiatri.rpo,penilaian_medis_ralan_gawat_darurat_psikiatri.faktor_premorbid,penilaian_medis_ralan_gawat_darurat_psikiatri.keturunan,penilaian_medis_ralan_gawat_darurat_psikiatri.ket_keturunan,penilaian_medis_ralan_gawat_darurat_psikiatri.faktor_organik,"+
                "penilaian_medis_ralan_gawat_darurat_psikiatri.ket_organik,penilaian_medis_ralan_gawat_darurat_psikiatri.alergi,penilaian_medis_ralan_gawat_darurat_psikiatri.kesadaran,penilaian_medis_ralan_gawat_darurat_psikiatri.nyeri,penilaian_medis_ralan_gawat_darurat_psikiatri.td,"+
                "penilaian_medis_ralan_gawat_darurat_psikiatri.nadi,penilaian_medis_ralan_gawat_darurat_psikiatri.suhu,penilaian_medis_ralan_gawat_darurat_psikiatri.rr,penilaian_medis_ralan_gawat_darurat_psikiatri.bb,penilaian_medis_ralan_gawat_darurat_psikiatri.tb,penilaian_medis_ralan_gawat_darurat_psikiatri.gcs,"+
                "penilaian_medis_ralan_gawat_darurat_psikiatri.status_nutrisi,penilaian_medis_ralan_gawat_darurat_psikiatri.kepala,penilaian_medis_ralan_gawat_darurat_psikiatri.keterangan_kepala,penilaian_medis_ralan_gawat_darurat_psikiatri.leher,penilaian_medis_ralan_gawat_darurat_psikiatri.keterangan_leher,"+
                "penilaian_medis_ralan_gawat_darurat_psikiatri.anggota_gerak,penilaian_medis_ralan_gawat_darurat_psikiatri.keterangan_gerak,penilaian_medis_ralan_gawat_darurat_psikiatri.dada,penilaian_medis_ralan_gawat_darurat_psikiatri.keterangan_dada,"+
                "penilaian_medis_ralan_gawat_darurat_psikiatri.perut,penilaian_medis_ralan_gawat_darurat_psikiatri.keterangan_perut,penilaian_medis_ralan_gawat_darurat_psikiatri.keterangan_badan,penilaian_medis_ralan_gawat_darurat_psikiatri.kesan_utama,penilaian_medis_ralan_gawat_darurat_psikiatri.sikap,"+
                "penilaian_medis_ralan_gawat_darurat_psikiatri.kesadaran1,penilaian_medis_ralan_gawat_darurat_psikiatri.orientasi,penilaian_medis_ralan_gawat_darurat_psikiatri.daya_ingat,penilaian_medis_ralan_gawat_darurat_psikiatri.persepsi,penilaian_medis_ralan_gawat_darurat_psikiatri.pikiran,penilaian_medis_ralan_gawat_darurat_psikiatri.insight,"+
                "penilaian_medis_ralan_gawat_darurat_psikiatri.lab,penilaian_medis_ralan_gawat_darurat_psikiatri.rad,penilaian_medis_ralan_gawat_darurat_psikiatri.penunjang,penilaian_medis_ralan_gawat_darurat_psikiatri.permasalahan,penilaian_medis_ralan_gawat_darurat_psikiatri.rencana,penilaian_medis_ralan_gawat_darurat_psikiatri.instruksi,"+
                "penilaian_medis_ralan_gawat_darurat_psikiatri.dipulangkan,penilaian_medis_ralan_gawat_darurat_psikiatri.ket_dipulangkan,penilaian_medis_ralan_gawat_darurat_psikiatri.ket_ruang,penilaian_medis_ralan_gawat_darurat_psikiatri.ket_indikasi,penilaian_medis_ralan_gawat_darurat_psikiatri.ket_dirujuk,"+
                "penilaian_medis_ralan_gawat_darurat_psikiatri.ket_alasandirujuk,penilaian_medis_ralan_gawat_darurat_psikiatri.pulang_paksa,penilaian_medis_ralan_gawat_darurat_psikiatri.ket_pulangpaksa,penilaian_medis_ralan_gawat_darurat_psikiatri.meninggal,penilaian_medis_ralan_gawat_darurat_psikiatri.ket_meninggal,"+
                "penilaian_medis_ralan_gawat_darurat_psikiatri.kesadaran2,penilaian_medis_ralan_gawat_darurat_psikiatri.td2,penilaian_medis_ralan_gawat_darurat_psikiatri.nadi2,penilaian_medis_ralan_gawat_darurat_psikiatri.rr2,penilaian_medis_ralan_gawat_darurat_psikiatri.suhu2,penilaian_medis_ralan_gawat_darurat_psikiatri.gcs2,"+
                "penilaian_medis_ralan_gawat_darurat_psikiatri.edukasi,dokter.nm_dokter "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join penilaian_medis_ralan_gawat_darurat_psikiatri on reg_periksa.no_rawat=penilaian_medis_ralan_gawat_darurat_psikiatri.no_rawat "+
                        "inner join dokter on penilaian_medis_ralan_gawat_darurat_psikiatri.kd_dokter=dokter.kd_dokter where "+
                        "penilaian_medis_ralan_gawat_darurat_psikiatri.tanggal between ? and ? order by penilaian_medis_ralan_gawat_darurat_psikiatri.tanggal");
            }else{
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penilaian_medis_ralan_gawat_darurat_psikiatri.tanggal,"+
                "penilaian_medis_ralan_gawat_darurat_psikiatri.kd_dokter,penilaian_medis_ralan_gawat_darurat_psikiatri.anamnesis,penilaian_medis_ralan_gawat_darurat_psikiatri.hubungan,penilaian_medis_ralan_gawat_darurat_psikiatri.keluhan_utama,"+
                "penilaian_medis_ralan_gawat_darurat_psikiatri.gejala_keluhan,penilaian_medis_ralan_gawat_darurat_psikiatri.pencetus_keluhan,penilaian_medis_ralan_gawat_darurat_psikiatri.rps,penilaian_medis_ralan_gawat_darurat_psikiatri.keterangan_rps,"+
                "penilaian_medis_ralan_gawat_darurat_psikiatri.riwayat_kehamilan,penilaian_medis_ralan_gawat_darurat_psikiatri.sosial,penilaian_medis_ralan_gawat_darurat_psikiatri.ket_sosial,penilaian_medis_ralan_gawat_darurat_psikiatri.pekerjaan,penilaian_medis_ralan_gawat_darurat_psikiatri.ket_pekerjaan,"+
                "penilaian_medis_ralan_gawat_darurat_psikiatri.rpo,penilaian_medis_ralan_gawat_darurat_psikiatri.faktor_premorbid,penilaian_medis_ralan_gawat_darurat_psikiatri.keturunan,penilaian_medis_ralan_gawat_darurat_psikiatri.ket_keturunan,penilaian_medis_ralan_gawat_darurat_psikiatri.faktor_organik,"+
                "penilaian_medis_ralan_gawat_darurat_psikiatri.ket_organik,penilaian_medis_ralan_gawat_darurat_psikiatri.alergi,penilaian_medis_ralan_gawat_darurat_psikiatri.kesadaran,penilaian_medis_ralan_gawat_darurat_psikiatri.nyeri,penilaian_medis_ralan_gawat_darurat_psikiatri.td,"+
                "penilaian_medis_ralan_gawat_darurat_psikiatri.nadi,penilaian_medis_ralan_gawat_darurat_psikiatri.suhu,penilaian_medis_ralan_gawat_darurat_psikiatri.rr,penilaian_medis_ralan_gawat_darurat_psikiatri.bb,penilaian_medis_ralan_gawat_darurat_psikiatri.tb,penilaian_medis_ralan_gawat_darurat_psikiatri.gcs,"+
                "penilaian_medis_ralan_gawat_darurat_psikiatri.status_nutrisi,penilaian_medis_ralan_gawat_darurat_psikiatri.kepala,penilaian_medis_ralan_gawat_darurat_psikiatri.keterangan_kepala,penilaian_medis_ralan_gawat_darurat_psikiatri.leher,penilaian_medis_ralan_gawat_darurat_psikiatri.keterangan_leher,"+
                "penilaian_medis_ralan_gawat_darurat_psikiatri.anggota_gerak,penilaian_medis_ralan_gawat_darurat_psikiatri.keterangan_gerak,penilaian_medis_ralan_gawat_darurat_psikiatri.dada,penilaian_medis_ralan_gawat_darurat_psikiatri.keterangan_dada,"+
                "penilaian_medis_ralan_gawat_darurat_psikiatri.perut,penilaian_medis_ralan_gawat_darurat_psikiatri.keterangan_perut,penilaian_medis_ralan_gawat_darurat_psikiatri.keterangan_badan,penilaian_medis_ralan_gawat_darurat_psikiatri.kesan_utama,penilaian_medis_ralan_gawat_darurat_psikiatri.sikap,"+
                "penilaian_medis_ralan_gawat_darurat_psikiatri.kesadaran1,penilaian_medis_ralan_gawat_darurat_psikiatri.orientasi,penilaian_medis_ralan_gawat_darurat_psikiatri.daya_ingat,penilaian_medis_ralan_gawat_darurat_psikiatri.persepsi,penilaian_medis_ralan_gawat_darurat_psikiatri.pikiran,penilaian_medis_ralan_gawat_darurat_psikiatri.insight,"+
                "penilaian_medis_ralan_gawat_darurat_psikiatri.lab,penilaian_medis_ralan_gawat_darurat_psikiatri.rad,penilaian_medis_ralan_gawat_darurat_psikiatri.penunjang,penilaian_medis_ralan_gawat_darurat_psikiatri.permasalahan,penilaian_medis_ralan_gawat_darurat_psikiatri.rencana,penilaian_medis_ralan_gawat_darurat_psikiatri.instruksi,"+
                "penilaian_medis_ralan_gawat_darurat_psikiatri.dipulangkan,penilaian_medis_ralan_gawat_darurat_psikiatri.ket_dipulangkan,penilaian_medis_ralan_gawat_darurat_psikiatri.ket_ruang,penilaian_medis_ralan_gawat_darurat_psikiatri.ket_indikasi,penilaian_medis_ralan_gawat_darurat_psikiatri.ket_dirujuk,"+
                "penilaian_medis_ralan_gawat_darurat_psikiatri.ket_alasandirujuk,penilaian_medis_ralan_gawat_darurat_psikiatri.pulang_paksa,penilaian_medis_ralan_gawat_darurat_psikiatri.ket_pulangpaksa,penilaian_medis_ralan_gawat_darurat_psikiatri.meninggal,penilaian_medis_ralan_gawat_darurat_psikiatri.ket_meninggal,"+
                "penilaian_medis_ralan_gawat_darurat_psikiatri.kesadaran2,penilaian_medis_ralan_gawat_darurat_psikiatri.td2,penilaian_medis_ralan_gawat_darurat_psikiatri.nadi2,penilaian_medis_ralan_gawat_darurat_psikiatri.rr2,penilaian_medis_ralan_gawat_darurat_psikiatri.suhu2,penilaian_medis_ralan_gawat_darurat_psikiatri.gcs2,"+
                "penilaian_medis_ralan_gawat_darurat_psikiatri.edukasi,dokter.nm_dokter "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join penilaian_medis_ralan_gawat_darurat_psikiatri on reg_periksa.no_rawat=penilaian_medis_ralan_gawat_darurat_psikiatri.no_rawat "+
                        "inner join dokter on penilaian_medis_ralan_gawat_darurat_psikiatri.kd_dokter=dokter.kd_dokter where "+
                        "penilaian_medis_ralan_gawat_darurat_psikiatri.tanggal between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or "+
                        "penilaian_medis_ralan_gawat_darurat_psikiatri.kd_dokter like ? or dokter.nm_dokter like ?) order by penilaian_medis_ralan_gawat_darurat_psikiatri.tanggal");
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
                        rs.getString("anamnesis"),rs.getString("hubungan"),rs.getString("keluhan_utama"),rs.getString("gejala_keluhan"),rs.getString("pencetus_keluhan"),rs.getString("rps"),rs.getString("keterangan_rps"),rs.getString("riwayat_kehamilan"),
                        rs.getString("sosial"),rs.getString("ket_sosial"),rs.getString("pekerjaan"),rs.getString("ket_pekerjaan"),rs.getString("rpo"),rs.getString("faktor_premorbid"),rs.getString("keturunan"),rs.getString("ket_keturunan"),
                        rs.getString("faktor_organik"),rs.getString("ket_organik"),rs.getString("alergi"),rs.getString("kesadaran"),rs.getString("nyeri"),rs.getString("td"),rs.getString("nadi"),rs.getString("suhu"),rs.getString("rr"),rs.getString("bb"),
                        rs.getString("tb"),rs.getString("gcs"),rs.getString("status_nutrisi"),rs.getString("kepala"),rs.getString("keterangan_kepala"),rs.getString("leher"),rs.getString("keterangan_leher"),rs.getString("anggota_gerak"),rs.getString("keterangan_gerak"),
                        rs.getString("dada"),rs.getString("keterangan_dada"),rs.getString("perut"),rs.getString("keterangan_perut"),rs.getString("keterangan_badan"),rs.getString("kesan_utama"),rs.getString("sikap"),rs.getString("kesadaran1"),
                        rs.getString("orientasi"),rs.getString("daya_ingat"),rs.getString("persepsi"),rs.getString("pikiran"),rs.getString("insight"),rs.getString("lab"),rs.getString("rad"),rs.getString("penunjang"),rs.getString("permasalahan"),
                        rs.getString("rencana"),rs.getString("instruksi"),rs.getString("dipulangkan"),rs.getString("ket_dipulangkan"),rs.getString("ket_ruang"),rs.getString("ket_indikasi"),rs.getString("ket_dirujuk"),rs.getString("ket_alasandirujuk"),
                        rs.getString("pulang_paksa"),rs.getString("ket_pulangpaksa"),rs.getString("meninggal"),rs.getString("ket_meninggal"),rs.getString("kesadaran2"),rs.getString("td2"),rs.getString("nadi2"),rs.getString("rr2"),rs.getString("suhu2"),
                        rs.getString("gcs2"),rs.getString("edukasi")
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
        GejalaKeluhan.setText("");
        PencetusKeluhan.setText("");
        RPS.setSelectedIndex(0);
        KetRPS.setText("");
        RiwayatKehamilan.setText("");
        Sosial.setSelectedIndex(0);
        KetSosial.setText("");
        Pekerjaan.setSelectedIndex(0);
        KetPekerjaan.setText("");
        RPO.setText("");
        FaktorPremorbid.setText("");
        Keturunan.setSelectedIndex(0);
        KetKeturunan.setText("");
        FaktorOrganik.setSelectedIndex(0);
        KetOrganik.setText("");
        Alergi.setText("");
        Kesadaran.setSelectedIndex(0);
        Nyeri.setSelectedIndex(0);
        TD.setText("");
        Nadi.setText("");
        Suhu.setText("");
        RR.setText("");
        BB.setText("");
        GCS.setText("");
        StatusNutrisi.setText("");
        TB.setText("");
        Kepala.setSelectedIndex(0);
        Leher.setSelectedIndex(0);
        Gerak.setSelectedIndex(0);
        Dada.setSelectedIndex(0);
        Perut.setSelectedIndex(0);
        KeteranganBadan.setText("");
        KesanUmum.setText("");
        Sikap.setText("");
        Kesadaran1.setText("");
        Orientasi.setText("");
        DayaIngat.setText("");
        Persepsi.setText("");
        Pikiran.setText("");
        Insight.setText("");
        Laborat.setText("");
        Radiologi.setText("");
        Penunjang.setText("");
        Permasalahan.setText("");
        Rencana.setText("");
        Instruksi.setText("");
        Dipulangkan.setSelectedIndex(0);
        Ketdipulangkan.setText("");
        Ketruang.setText("");
        Ketindikasi.setText("");
        Ketdirujuk.setText("");
        Alasandirujuk.setSelectedIndex(0);
        Pulangpaksa.setSelectedIndex(0);
        KetPulangpaksa.setText("");
        Meninggal.setSelectedIndex(0);
        Ketmeninggal.setText("");
        Kesadaran2.setSelectedIndex(0);
        TD1.setText("");
        Nadi1.setText("");
        RR1.setText("");
        Suhu1.setText("");
        GCS1.setText("");
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
            GejalaKeluhan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            PencetusKeluhan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            RPS.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            KetRPS.setText(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            RiwayatKehamilan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
            Sosial.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            KetSosial.setText(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());
            Pekerjaan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
            KetPekerjaan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
            RPO.setText(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString());
            FaktorPremorbid.setText(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString());
            Keturunan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString());
            KetKeturunan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString());
            FaktorOrganik.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),24).toString());
            KetOrganik.setText(tbObat.getValueAt(tbObat.getSelectedRow(),25).toString());
            Alergi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),26).toString());
            Kesadaran.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),27).toString());
            Nyeri.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),28).toString());
            TD.setText(tbObat.getValueAt(tbObat.getSelectedRow(),29).toString());
            Nadi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),30).toString());
            Suhu.setText(tbObat.getValueAt(tbObat.getSelectedRow(),31).toString());
            RR.setText(tbObat.getValueAt(tbObat.getSelectedRow(),32).toString());
            BB.setText(tbObat.getValueAt(tbObat.getSelectedRow(),33).toString());
            TB.setText(tbObat.getValueAt(tbObat.getSelectedRow(),34).toString());
            GCS.setText(tbObat.getValueAt(tbObat.getSelectedRow(),35).toString());
            StatusNutrisi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),36).toString());
            Kepala.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),37).toString());
            KeteranganKepala.setText(tbObat.getValueAt(tbObat.getSelectedRow(),38).toString());
            Leher.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),39).toString());
            KeteranganLeher.setText(tbObat.getValueAt(tbObat.getSelectedRow(),40).toString());
            Gerak.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),41).toString());
            KeteranganGerak.setText(tbObat.getValueAt(tbObat.getSelectedRow(),42).toString());
            Dada.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),43).toString());
            KeteranganDada.setText(tbObat.getValueAt(tbObat.getSelectedRow(),44).toString());
            Perut.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),45).toString());
            KeteranganPerut.setText(tbObat.getValueAt(tbObat.getSelectedRow(),46).toString());
            KeteranganBadan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),47).toString());
            KesanUmum.setText(tbObat.getValueAt(tbObat.getSelectedRow(),48).toString());
            Sikap.setText(tbObat.getValueAt(tbObat.getSelectedRow(),49).toString());
            Kesadaran1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),50).toString());
            Orientasi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),51).toString());
            DayaIngat.setText(tbObat.getValueAt(tbObat.getSelectedRow(),52).toString());
            Persepsi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),53).toString());
            Pikiran.setText(tbObat.getValueAt(tbObat.getSelectedRow(),54).toString());
            Insight.setText(tbObat.getValueAt(tbObat.getSelectedRow(),55).toString());
            Laborat.setText(tbObat.getValueAt(tbObat.getSelectedRow(),56).toString());
            Radiologi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),57).toString());
            Penunjang.setText(tbObat.getValueAt(tbObat.getSelectedRow(),58).toString());
            Permasalahan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),59).toString());
            Rencana.setText(tbObat.getValueAt(tbObat.getSelectedRow(),60).toString());
            Instruksi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),61).toString());
            Dipulangkan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),62).toString());
            Ketdipulangkan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),63).toString());
            Ketruang.setText(tbObat.getValueAt(tbObat.getSelectedRow(),64).toString());
            Ketindikasi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),65).toString());
            Ketdirujuk.setText(tbObat.getValueAt(tbObat.getSelectedRow(),66).toString());
            Alasandirujuk.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),67).toString());
            Pulangpaksa.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),68).toString());
            KetPulangpaksa.setText(tbObat.getValueAt(tbObat.getSelectedRow(),69).toString());
            Meninggal.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),70).toString());
            Ketmeninggal.setText(tbObat.getValueAt(tbObat.getSelectedRow(),71).toString());
            Kesadaran2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),72).toString());
            TD1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),73).toString());
            Nadi1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),74).toString());
            RR1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),75).toString());
            Suhu1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),76).toString());
            GCS1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),77).toString());
            Edukasi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),78).toString());
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
        BtnSimpan.setEnabled(akses.getpenilaian_medis_ralan_gawat_darurat_psikiatri());
        BtnHapus.setEnabled(akses.getpenilaian_medis_ralan_gawat_darurat_psikiatri());
        BtnEdit.setEnabled(akses.getpenilaian_medis_ralan_gawat_darurat_psikiatri());
        BtnEdit.setEnabled(akses.getpenilaian_medis_ralan_gawat_darurat_psikiatri());
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
        if(Sequel.queryu2tf("delete from penilaian_medis_ralan_gawat_darurat_psikiatri where no_rawat=?",1,new String[]{
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
        if(Sequel.mengedittf("penilaian_medis_ralan_gawat_darurat_psikiatri","no_rawat=?","no_rawat=?,tanggal=?,kd_dokter=?,anamnesis=?,hubungan=?,keluhan_utama=?,gejala_keluhan=?,pencetus_keluhan=?,rps=?,keterangan_rps=?,riwayat_kehamilan=?,sosial=?,ket_sosial=?,pekerjaan=?,ket_pekerjaan=?,rpo=?,faktor_premorbid=?,keturunan=?,ket_keturunan=?,Faktor_organik=?,ket_organik=?,alergi=?,kesadaran=?,nyeri=?,td=?,nadi=?,suhu=?,rr=?,bb=?,"+
                "tb=?,gcs=?,status_nutrisi=?,kepala=?,keterangan_kepala=?,leher=?,keterangan_leher=?,anggota_gerak=?,keterangan_gerak=?,dada=?,keterangan_dada=?,perut=?,keterangan_perut=?,keterangan_badan=?,kesan_utama=?,sikap=?,kesadaran1=?,orientasi=?,daya_ingat=?,persepsi=?,pikiran=?,insight=?,lab=?,rad=?,penunjang=?,permasalahan=?,rencana=?,instruksi=?,dipulangkan=?,ket_dipulangkan=?,ket_ruang=?,ket_indikasi=?,ket_dirujuk=?,"+
                "ket_alasandirujuk=?,pulang_paksa=?,ket_pulangpaksa=?,meninggal=?,ket_meninggal=?,kesadaran2=?,td2=?,nadi2=?,rr2=?,suhu2=?,gcs2=?,edukasi=?",75,new String[]{
                TNoRw.getText(),Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),KdDokter.getText(),Anamnesis.getSelectedItem().toString(),Hubungan.getText(),
                KeluhanUtama.getText(),GejalaKeluhan.getText(),PencetusKeluhan.getText(),RPS.getSelectedItem().toString(),KetRPS.getText(),RiwayatKehamilan.getText(),Sosial.getSelectedItem().toString(),KetSosial.getText(),Pekerjaan.getSelectedItem().toString(),KetPekerjaan.getText(),RPO.getText(),FaktorPremorbid.getText(),Keturunan.getSelectedItem().toString(),KetKeturunan.getText(),FaktorOrganik.getSelectedItem().toString(),KetOrganik.getText(),Alergi.getText(),Kesadaran.getSelectedItem().toString(),Nyeri.getSelectedItem().toString(),TD.getText(),Nadi.getText(), 
                Suhu.getText(),RR.getText(),BB.getText(),TB.getText(),GCS.getText(),StatusNutrisi.getText(),Kepala.getSelectedItem().toString(),KeteranganKepala.getText(),Leher.getSelectedItem().toString(),KeteranganLeher.getText(),Gerak.getSelectedItem().toString(),KeteranganGerak.getText(),Dada.getSelectedItem().toString(),KeteranganDada.getText(),Perut.getSelectedItem().toString(),KeteranganPerut.getText(),KeteranganBadan.getText(),KesanUmum.getText(),Sikap.getText(),Kesadaran1.getText(),Orientasi.getText(),DayaIngat.getText(),Persepsi.getText(),Pikiran.getText(),
                Insight.getText(),Laborat.getText(),Radiologi.getText(),Penunjang.getText(),Permasalahan.getText(),Rencana.getText(),Instruksi.getText(),Dipulangkan.getSelectedItem().toString(),Ketdipulangkan.getText(),Ketruang.getText(),Ketindikasi.getText(),Ketdirujuk.getText(),Alasandirujuk.getSelectedItem().toString(),Pulangpaksa.getSelectedItem().toString(),KetPulangpaksa.getText(),Meninggal.getSelectedItem().toString(),Ketmeninggal.getText(),Kesadaran2.getSelectedItem().toString(),TD1.getText(),Nadi1.getText(),RR1.getText(),Suhu1.getText(),GCS1.getText(),
                Edukasi.getText(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
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
               tbObat.setValueAt(GejalaKeluhan.getText(),tbObat.getSelectedRow(),11);
               tbObat.setValueAt(PencetusKeluhan.getText(),tbObat.getSelectedRow(),12);
               tbObat.setValueAt(RPS.getSelectedItem().toString(),tbObat.getSelectedRow(),13);
               tbObat.setValueAt(KetRPS.getText(),tbObat.getSelectedRow(),14);
               tbObat.setValueAt(RiwayatKehamilan.getText(),tbObat.getSelectedRow(),15);
               tbObat.setValueAt(Sosial.getSelectedItem().toString(),tbObat.getSelectedRow(),16);
               tbObat.setValueAt(KetSosial.getText(),tbObat.getSelectedRow(),17);
               tbObat.setValueAt(Pekerjaan.getSelectedItem().toString(),tbObat.getSelectedRow(),18);
               tbObat.setValueAt(KetPekerjaan.getText(),tbObat.getSelectedRow(),19);
               tbObat.setValueAt(RPO.getText(),tbObat.getSelectedRow(),20);
               tbObat.setValueAt(FaktorPremorbid.getText(),tbObat.getSelectedRow(),21);
               tbObat.setValueAt(Keturunan.getSelectedItem().toString(),tbObat.getSelectedRow(),22);
               tbObat.setValueAt(KetKeturunan.getText(),tbObat.getSelectedRow(),23);
               tbObat.setValueAt(FaktorOrganik.getSelectedItem().toString(),tbObat.getSelectedRow(),24);
               tbObat.setValueAt(KetOrganik.getText(),tbObat.getSelectedRow(),25);
               tbObat.setValueAt(Alergi.getText(),tbObat.getSelectedRow(),26);
               tbObat.setValueAt(Kesadaran.getSelectedItem().toString(),tbObat.getSelectedRow(),27);
               tbObat.setValueAt(Nyeri.getSelectedItem().toString(),tbObat.getSelectedRow(),28);
               tbObat.setValueAt(TD.getText(),tbObat.getSelectedRow(),29);
               tbObat.setValueAt(Nadi.getText(),tbObat.getSelectedRow(),30);
               tbObat.setValueAt(Suhu.getText(),tbObat.getSelectedRow(),31);
               tbObat.setValueAt(RR.getText(),tbObat.getSelectedRow(),32);
               tbObat.setValueAt(BB.getText(),tbObat.getSelectedRow(),33);
               tbObat.setValueAt(TB.getText(),tbObat.getSelectedRow(),34);
               tbObat.setValueAt(GCS.getText(),tbObat.getSelectedRow(),35);
               tbObat.setValueAt(StatusNutrisi.getText(),tbObat.getSelectedRow(),36);
               tbObat.setValueAt(Kepala.getSelectedItem().toString(),tbObat.getSelectedRow(),37);
               tbObat.setValueAt(KeteranganKepala.getText(),tbObat.getSelectedRow(),38);
               tbObat.setValueAt(Leher.getSelectedItem().toString(),tbObat.getSelectedRow(),39);
               tbObat.setValueAt(KeteranganLeher.getText(),tbObat.getSelectedRow(),40);
               tbObat.setValueAt(Gerak.getSelectedItem().toString(),tbObat.getSelectedRow(),41);
               tbObat.setValueAt(KeteranganGerak.getText(),tbObat.getSelectedRow(),42);
               tbObat.setValueAt(Dada.getSelectedItem().toString(),tbObat.getSelectedRow(),43);
               tbObat.setValueAt(KeteranganDada.getText(),tbObat.getSelectedRow(),44);
               tbObat.setValueAt(Perut.getSelectedItem().toString(),tbObat.getSelectedRow(),45);
               tbObat.setValueAt(KeteranganPerut.getText(),tbObat.getSelectedRow(),46);
               tbObat.setValueAt(KeteranganBadan.getText(),tbObat.getSelectedRow(),47);
               tbObat.setValueAt(KesanUmum.getText(),tbObat.getSelectedRow(),48);
               tbObat.setValueAt(Sikap.getText(),tbObat.getSelectedRow(),49);
               tbObat.setValueAt(Kesadaran1.getText(),tbObat.getSelectedRow(),50);
               tbObat.setValueAt(Orientasi.getText(),tbObat.getSelectedRow(),51);
               tbObat.setValueAt(DayaIngat.getText(),tbObat.getSelectedRow(),52);
               tbObat.setValueAt(Persepsi.getText(),tbObat.getSelectedRow(),53);
               tbObat.setValueAt(Pikiran.getText(),tbObat.getSelectedRow(),54);
               tbObat.setValueAt(Insight.getText(),tbObat.getSelectedRow(),55);
               tbObat.setValueAt(Laborat.getText(),tbObat.getSelectedRow(),56);
               tbObat.setValueAt(Radiologi.getText(),tbObat.getSelectedRow(),57);
               tbObat.setValueAt(Penunjang.getText(),tbObat.getSelectedRow(),58);
               tbObat.setValueAt(Permasalahan.getText(),tbObat.getSelectedRow(),59);
               tbObat.setValueAt(Rencana.getText(),tbObat.getSelectedRow(),60);
               tbObat.setValueAt(Instruksi.getText(),tbObat.getSelectedRow(),61);
               tbObat.setValueAt(Dipulangkan.getSelectedItem().toString(),tbObat.getSelectedRow(),62);
               tbObat.setValueAt(Ketdipulangkan.getText(),tbObat.getSelectedRow(),63);
               tbObat.setValueAt(Ketruang.getText(),tbObat.getSelectedRow(),64);
               tbObat.setValueAt(Ketindikasi.getText(),tbObat.getSelectedRow(),65);
               tbObat.setValueAt(Ketdirujuk.getText(),tbObat.getSelectedRow(),66);
               tbObat.setValueAt(Alasandirujuk.getSelectedItem().toString(),tbObat.getSelectedRow(),67);
               tbObat.setValueAt(Pulangpaksa.getSelectedItem().toString(),tbObat.getSelectedRow(),68);
               tbObat.setValueAt(KetPulangpaksa.getText(),tbObat.getSelectedRow(),69);
               tbObat.setValueAt(Meninggal.getSelectedItem().toString(),tbObat.getSelectedRow(),70);
               tbObat.setValueAt(Ketmeninggal.getText(),tbObat.getSelectedRow(),71);
               tbObat.setValueAt(Kesadaran2.getSelectedItem().toString(),tbObat.getSelectedRow(),72);
               tbObat.setValueAt(TD1.getText(),tbObat.getSelectedRow(),73);
               tbObat.setValueAt(Nadi1.getText(),tbObat.getSelectedRow(),74);
               tbObat.setValueAt(RR1.getText(),tbObat.getSelectedRow(),75);
               tbObat.setValueAt(Suhu1.getText(),tbObat.getSelectedRow(),76);
               tbObat.setValueAt(GCS1.getText(),tbObat.getSelectedRow(),77);
               tbObat.setValueAt(Edukasi.getText(),tbObat.getSelectedRow(),78);
               emptTeks();
               TabRawat.setSelectedIndex(1);
        }
    }
}

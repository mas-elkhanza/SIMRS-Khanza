/*
 * Kontribusi Tim IT RSUD Prembun
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
public final class RMPenilaianAwalMedisRalanMata extends javax.swing.JDialog {
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
    private String TANGGALMUNDUR="yes";
    
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public RMPenilaianAwalMedisRalanMata(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.RM","Nama Pasien","Tgl.Lahir","J.K.","Kode Dokter","Nama Dokter","Tanggal","Anamnesis","Hubungan","Keluhan Utama","Riwayat Penyakit Sekarang","Riwayat Penyakit Dahulu",
            "Riwayat Penggunakan Obat","Riwayat Alergi","Status Nutrisi","TD(mmHg)","Nadi(x/menit)","RR(x/menit)","Suhu","Nyeri","BB(Kg)","Visus kanan","Visus Kiri","CC Kanan","CC Kiri","Palpebra Kanan","Palpebra Kiri",
            "Conjungtiva Kanan","Conjungtiva Kiri","Cornea Kanan","Cornea Kiri","COA Kanan","COA kiri","Pupil Kanan","Pupil Kiri","Lensa Kanan","Lensa Kiri","Fundus Kanan","Fundus Kiri","Papil Kanan","Papil Kiri","Retina Kanan","Retina Kiri",
            "Makula Kanan","Makula Kiri","TIO Kanan","TIO Kiri","MBO Kanan","MBO Kiri","Laboratorium","Radiologi","Penunjang Lainnya","Tes Penglihatan","Pemeriksaan Lain","Diagnosis/Asesmen","Diagnosis Banding","Permasalahan",
            "Terapi/Pengobatan","Tindakan","Edukasi"
            
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        
        tbObat.setModel(tabMode);
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 61; i++) {
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
                column.setPreferredWidth(63);
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
                column.setPreferredWidth(90);
            }else if(i==16){
                column.setPreferredWidth(61);
            }else if(i==17){
                column.setPreferredWidth(75);
            }else if(i==18){
                column.setPreferredWidth(69);
            }else if(i==19){
                column.setPreferredWidth(40);
            }else if(i==20){
                column.setPreferredWidth(40);
            }else if(i==21){
                column.setPreferredWidth(40);
            }else if(i==22){
                column.setPreferredWidth(100);
            }else if(i==23){
                column.setPreferredWidth(100);
            }else if(i==24){
                column.setPreferredWidth(100);
            }else if(i==25){
                column.setPreferredWidth(100);
            }else if(i==26){
                column.setPreferredWidth(100);
            }else if(i==27){
                column.setPreferredWidth(100);
            }else if(i==28){
                column.setPreferredWidth(100);
            }else if(i==29){
                column.setPreferredWidth(100);
            }else if(i==30){
                column.setPreferredWidth(100);
            }else if(i==31){
                column.setPreferredWidth(100);
            }else if(i==32){
                column.setPreferredWidth(100);
            }else if(i==33){
                column.setPreferredWidth(100);
            }else if(i==34){
                column.setPreferredWidth(100);
            }else if(i==35){
                column.setPreferredWidth(100);
            }else if(i==36){
                column.setPreferredWidth(100);
            }else if(i==37){
                column.setPreferredWidth(100);
            }else if(i==38){
                column.setPreferredWidth(100);
            }else if(i==39){
                column.setPreferredWidth(100);
            }else if(i==40){
                column.setPreferredWidth(100);
            }else if(i==41){
                column.setPreferredWidth(100);
            }else if(i==42){
                column.setPreferredWidth(100);
            }else if(i==43){
                column.setPreferredWidth(100);
            }else if(i==44){
                column.setPreferredWidth(100);
            }else if(i==45){
                column.setPreferredWidth(100);
            }else if(i==46){
                column.setPreferredWidth(100);
            }else if(i==47){
                column.setPreferredWidth(100);
            }else if(i==48){
                column.setPreferredWidth(100);
            }else if(i==49){
                column.setPreferredWidth(100);
            }else if(i==50){
                column.setPreferredWidth(170);
            }else if(i==51){
                column.setPreferredWidth(170);
            }else if(i==52){
                column.setPreferredWidth(170);
            }else if(i==53){
                column.setPreferredWidth(170);
            }else if(i==54){
                column.setPreferredWidth(170);
            }else if(i==55){
                column.setPreferredWidth(150);
            }else if(i==56){
                column.setPreferredWidth(150);
            }else if(i==57){
                column.setPreferredWidth(200);
            }else if(i==58){
                column.setPreferredWidth(200);
            }else if(i==59){
                column.setPreferredWidth(200);
            }else if(i==60){
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
        StatusNutrisi.setDocument(new batasInput((byte)50).getKata(StatusNutrisi));
        TD.setDocument(new batasInput((byte)8).getKata(TD));
        Nadi.setDocument(new batasInput((byte)5).getKata(Nadi));
        RR.setDocument(new batasInput((byte)5).getKata(RR));
        Suhu.setDocument(new batasInput((byte)5).getKata(Suhu));
        Nyeri.setDocument(new batasInput((byte)50).getKata(Nyeri));
        BB.setDocument(new batasInput((byte)5).getKata(BB));
        Visuskanan.setDocument(new batasInput((int)100).getKata(Visuskanan));
        Visuskiri.setDocument(new batasInput((int)100).getKata(Visuskiri));
        CCkanan.setDocument(new batasInput((int)100).getKata(CCkanan));
        CCkiri.setDocument(new batasInput((int)100).getKata(CCkiri));
        Palkanan.setDocument(new batasInput((int)100).getKata(Palkanan));
        Palkiri.setDocument(new batasInput((int)100).getKata(Palkiri));
        Conkanan.setDocument(new batasInput((int)100).getKata(Conkanan));
        Conkiri.setDocument(new batasInput((int)100).getKata(Conkiri));
        Corneakanan.setDocument(new batasInput((int)100).getKata(Corneakanan));
        Corneakiri.setDocument(new batasInput((int)100).getKata(Corneakiri));
        COAkanan.setDocument(new batasInput((int)100).getKata(COAkanan));
        COAkiri.setDocument(new batasInput((int)100).getKata(COAkiri));
        Pupilkanan.setDocument(new batasInput((int)100).getKata(Pupilkanan));
        Pupilkiri.setDocument(new batasInput((int)100).getKata(Pupilkiri));
        Lensakanan.setDocument(new batasInput((int)100).getKata(Lensakanan));
        Lensakiri.setDocument(new batasInput((int)100).getKata(Lensakiri));
        Funduskanan.setDocument(new batasInput((int)100).getKata(Funduskanan));
        Funduskiri.setDocument(new batasInput((int)100).getKata(Funduskiri));
        Papilkanan.setDocument(new batasInput((int)100).getKata(Papilkanan));
        Papilkiri.setDocument(new batasInput((int)100).getKata(Papilkiri));
        Retinakanan.setDocument(new batasInput((int)100).getKata(Retinakanan));
        Retinakiri.setDocument(new batasInput((int)100).getKata(Retinakiri));
        Makulakanan.setDocument(new batasInput((int)100).getKata(Makulakanan));
        Makulakiri.setDocument(new batasInput((int)100).getKata(Makulakiri));
        TIOkanan.setDocument(new batasInput((int)100).getKata(TIOkanan));
        TIOkiri.setDocument(new batasInput((int)100).getKata(TIOkiri));
        MBOkanan.setDocument(new batasInput((int)100).getKata(MBOkanan));
        MBOkiri.setDocument(new batasInput((int)100).getKata(MBOkiri));
        Laborat.setDocument(new batasInput((int)1000).getKata(Laborat));
        Radiologi.setDocument(new batasInput((int)1000).getKata(Radiologi));
        Penunjang.setDocument(new batasInput((int)1000).getKata(Penunjang));
        TesPenglihatan.setDocument(new batasInput((int)1000).getKata(TesPenglihatan));
        PemeriksaanLain.setDocument(new batasInput((int)1000).getKata(PemeriksaanLain));
        Diagnosis.setDocument(new batasInput((int)500).getKata(Diagnosis));
        DiagnosisBdg.setDocument(new batasInput((int)500).getKata(DiagnosisBdg));
        Permasalahan.setDocument(new batasInput((int)500).getKata(Permasalahan));
        Terapi.setDocument(new batasInput((int)500).getKata(Terapi));
        Tindakan.setDocument(new batasInput((int)200).getKata(Tindakan));
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
        jLabel94 = new widget.Label();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel38 = new widget.Label();
        Hubungan = new widget.TextBox();
        jLabel33 = new widget.Label();
        scrollPane7 = new widget.ScrollPane();
        RPS = new widget.TextArea();
        jSeparator12 = new javax.swing.JSeparator();
        jLabel99 = new widget.Label();
        PanelWall = new usu.widget.glass.PanelGlass();
        jSeparator14 = new javax.swing.JSeparator();
        jLabel100 = new widget.Label();
        jLabel101 = new widget.Label();
        label11 = new widget.Label();
        TglAsuhan = new widget.Tanggal();
        jLabel82 = new widget.Label();
        jSeparator18 = new javax.swing.JSeparator();
        scrollPane19 = new widget.ScrollPane();
        PemeriksaanLain = new widget.TextArea();
        scrollPane21 = new widget.ScrollPane();
        TesPenglihatan = new widget.TextArea();
        jLabel84 = new widget.Label();
        PanelWall1 = new usu.widget.glass.PanelGlass();
        jLabel32 = new widget.Label();
        jLabel34 = new widget.Label();
        jSeparator19 = new javax.swing.JSeparator();
        jLabel35 = new widget.Label();
        jSeparator20 = new javax.swing.JSeparator();
        jSeparator21 = new javax.swing.JSeparator();
        jSeparator22 = new javax.swing.JSeparator();
        jSeparator23 = new javax.swing.JSeparator();
        jSeparator24 = new javax.swing.JSeparator();
        jSeparator25 = new javax.swing.JSeparator();
        jSeparator26 = new javax.swing.JSeparator();
        jSeparator27 = new javax.swing.JSeparator();
        jSeparator28 = new javax.swing.JSeparator();
        jSeparator29 = new javax.swing.JSeparator();
        jSeparator30 = new javax.swing.JSeparator();
        MBOkiri = new widget.TextBox();
        Visuskanan = new widget.TextBox();
        Visuskiri = new widget.TextBox();
        CCkanan = new widget.TextBox();
        CCkiri = new widget.TextBox();
        Palkanan = new widget.TextBox();
        Palkiri = new widget.TextBox();
        Conkanan = new widget.TextBox();
        Conkiri = new widget.TextBox();
        Corneakanan = new widget.TextBox();
        Corneakiri = new widget.TextBox();
        COAkanan = new widget.TextBox();
        COAkiri = new widget.TextBox();
        Pupilkanan = new widget.TextBox();
        Pupilkiri = new widget.TextBox();
        Lensakanan = new widget.TextBox();
        Lensakiri = new widget.TextBox();
        Funduskanan = new widget.TextBox();
        Funduskiri = new widget.TextBox();
        Papilkanan = new widget.TextBox();
        Papilkiri = new widget.TextBox();
        Retinakanan = new widget.TextBox();
        Retinakiri = new widget.TextBox();
        Makulakanan = new widget.TextBox();
        Makulakiri = new widget.TextBox();
        TIOkanan = new widget.TextBox();
        TIOkiri = new widget.TextBox();
        MBOkanan = new widget.TextBox();
        jLabel44 = new widget.Label();
        jLabel45 = new widget.Label();
        jLabel46 = new widget.Label();
        jLabel47 = new widget.Label();
        jLabel48 = new widget.Label();
        jLabel49 = new widget.Label();
        jLabel50 = new widget.Label();
        jLabel51 = new widget.Label();
        jLabel52 = new widget.Label();
        jLabel53 = new widget.Label();
        jLabel54 = new widget.Label();
        jLabel55 = new widget.Label();
        jLabel56 = new widget.Label();
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
        jLabel28 = new widget.Label();
        StatusNutrisi = new widget.TextBox();
        jLabel29 = new widget.Label();
        Nyeri = new widget.TextBox();
        jSeparator31 = new javax.swing.JSeparator();
        scrollPane13 = new widget.ScrollPane();
        Penunjang = new widget.TextArea();
        jLabel109 = new widget.Label();
        scrollPane9 = new widget.ScrollPane();
        Laborat = new widget.TextArea();
        jLabel80 = new widget.Label();
        jLabel81 = new widget.Label();
        scrollPane10 = new widget.ScrollPane();
        Radiologi = new widget.TextArea();
        jSeparator32 = new javax.swing.JSeparator();
        jLabel110 = new widget.Label();
        scrollPane15 = new widget.ScrollPane();
        Diagnosis = new widget.TextArea();
        scrollPane18 = new widget.ScrollPane();
        DiagnosisBdg = new widget.TextArea();
        jLabel83 = new widget.Label();
        jLabel85 = new widget.Label();
        jSeparator33 = new javax.swing.JSeparator();
        jLabel103 = new widget.Label();
        scrollPane22 = new widget.ScrollPane();
        Terapi = new widget.TextArea();
        jLabel111 = new widget.Label();
        jLabel108 = new widget.Label();
        scrollPane20 = new widget.ScrollPane();
        Permasalahan = new widget.TextArea();
        scrollPane23 = new widget.ScrollPane();
        Tindakan = new widget.TextArea();
        jLabel112 = new widget.Label();
        jSeparator34 = new javax.swing.JSeparator();
        jLabel113 = new widget.Label();
        scrollPane14 = new widget.ScrollPane();
        Edukasi = new widget.TextArea();
        jSeparator35 = new javax.swing.JSeparator();
        jLabel36 = new widget.Label();
        jLabel14 = new widget.Label();
        jLabel24 = new widget.Label();
        jLabel39 = new widget.Label();
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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Pengkajian Awal Medis Rawat Jalan Mata ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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
        internalFrame2.setPreferredSize(new java.awt.Dimension(102, 857));
        internalFrame2.setLayout(new java.awt.BorderLayout(1, 1));

        scrollInput.setName("scrollInput"); // NOI18N
        scrollInput.setPreferredSize(new java.awt.Dimension(102, 557));

        FormInput.setBackground(new java.awt.Color(255, 255, 255));
        FormInput.setBorder(null);
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(870, 1503));
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

        jLabel9.setText(":");
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
        scrollPane4.setBounds(184, 170, 255, 42);

        jLabel94.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel94.setText("II. PEMERIKSAAN FISIK");
        jLabel94.setName("jLabel94"); // NOI18N
        FormInput.add(jLabel94);
        jLabel94.setBounds(10, 220, 180, 23);

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

        jLabel33.setText(":");
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

        jLabel99.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel99.setText("I. RIWAYAT KESEHATAN");
        jLabel99.setName("jLabel99"); // NOI18N
        FormInput.add(jLabel99);
        jLabel99.setBounds(10, 70, 180, 23);

        PanelWall.setBackground(new java.awt.Color(255, 255, 255));
        PanelWall.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/picture/mata.png"))); // NOI18N
        PanelWall.setBackgroundImageType(usu.widget.constan.BackgroundConstan.BACKGROUND_IMAGE_STRECT);
        PanelWall.setOpaque(true);
        PanelWall.setPreferredSize(new java.awt.Dimension(200, 200));
        PanelWall.setRound(false);
        PanelWall.setWarna(new java.awt.Color(110, 110, 110));
        PanelWall.setLayout(null);
        FormInput.add(PanelWall);
        PanelWall.setBounds(570, 340, 200, 80);

        jSeparator14.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator14.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator14.setName("jSeparator14"); // NOI18N
        FormInput.add(jSeparator14);
        jSeparator14.setBounds(45, 940, 780, 1);

        jLabel100.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel100.setText("III. STATUS OFTAMOLOGIS");
        jLabel100.setName("jLabel100"); // NOI18N
        FormInput.add(jLabel100);
        jLabel100.setBounds(10, 300, 180, 23);

        jLabel101.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel101.setText("IV. PEMERIKSAAN PENUNJANG");
        jLabel101.setName("jLabel101"); // NOI18N
        FormInput.add(jLabel101);
        jLabel101.setBounds(10, 980, 190, 23);

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label11);
        label11.setBounds(380, 40, 52, 23);

        TglAsuhan.setForeground(new java.awt.Color(50, 70, 50));
        TglAsuhan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "07-08-2024 15:03:07" }));
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

        jLabel82.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel82.setText("Pemeriksaan Lain :");
        jLabel82.setName("jLabel82"); // NOI18N
        FormInput.add(jLabel82);
        jLabel82.setBounds(454, 1090, 150, 23);

        jSeparator18.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator18.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator18.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator18.setName("jSeparator18"); // NOI18N
        FormInput.add(jSeparator18);
        jSeparator18.setBounds(45, 900, 780, 1);

        scrollPane19.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane19.setName("scrollPane19"); // NOI18N

        PemeriksaanLain.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        PemeriksaanLain.setColumns(20);
        PemeriksaanLain.setRows(3);
        PemeriksaanLain.setName("PemeriksaanLain"); // NOI18N
        PemeriksaanLain.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PemeriksaanLainKeyPressed(evt);
            }
        });
        scrollPane19.setViewportView(PemeriksaanLain);

        FormInput.add(scrollPane19);
        scrollPane19.setBounds(454, 1110, 400, 43);

        scrollPane21.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane21.setName("scrollPane21"); // NOI18N

        TesPenglihatan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        TesPenglihatan.setColumns(20);
        TesPenglihatan.setRows(3);
        TesPenglihatan.setName("TesPenglihatan"); // NOI18N
        TesPenglihatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TesPenglihatanKeyPressed(evt);
            }
        });
        scrollPane21.setViewportView(TesPenglihatan);

        FormInput.add(scrollPane21);
        scrollPane21.setBounds(44, 1110, 400, 43);

        jLabel84.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel84.setText("Tes Penglihatan :");
        jLabel84.setName("jLabel84"); // NOI18N
        FormInput.add(jLabel84);
        jLabel84.setBounds(44, 1090, 150, 23);

        PanelWall1.setBackground(new java.awt.Color(255, 255, 255));
        PanelWall1.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/picture/mata.png"))); // NOI18N
        PanelWall1.setBackgroundImageType(usu.widget.constan.BackgroundConstan.BACKGROUND_IMAGE_STRECT);
        PanelWall1.setOpaque(true);
        PanelWall1.setPreferredSize(new java.awt.Dimension(200, 200));
        PanelWall1.setRound(false);
        PanelWall1.setWarna(new java.awt.Color(110, 110, 110));
        PanelWall1.setLayout(null);
        FormInput.add(PanelWall1);
        PanelWall1.setBounds(100, 340, 200, 80);

        jLabel32.setText("OD : Mata Kanan");
        jLabel32.setName("jLabel32"); // NOI18N
        FormInput.add(jLabel32);
        jLabel32.setBounds(0, 320, 126, 23);

        jLabel34.setText("OS : Mata Kiri");
        jLabel34.setName("jLabel34"); // NOI18N
        FormInput.add(jLabel34);
        jLabel34.setBounds(510, 320, 80, 23);

        jSeparator19.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator19.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator19.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator19.setName("jSeparator19"); // NOI18N
        FormInput.add(jSeparator19);
        jSeparator19.setBounds(0, 300, 880, 1);

        jLabel35.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel35.setText("Visus SC");
        jLabel35.setName("jLabel35"); // NOI18N
        FormInput.add(jLabel35);
        jLabel35.setBounds(350, 430, 170, 23);

        jSeparator20.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator20.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator20.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator20.setName("jSeparator20"); // NOI18N
        FormInput.add(jSeparator20);
        jSeparator20.setBounds(45, 460, 780, 1);

        jSeparator21.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator21.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator21.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator21.setName("jSeparator21"); // NOI18N
        FormInput.add(jSeparator21);
        jSeparator21.setBounds(45, 500, 780, 1);

        jSeparator22.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator22.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator22.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator22.setName("jSeparator22"); // NOI18N
        FormInput.add(jSeparator22);
        jSeparator22.setBounds(45, 540, 780, 1);

        jSeparator23.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator23.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator23.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator23.setName("jSeparator23"); // NOI18N
        FormInput.add(jSeparator23);
        jSeparator23.setBounds(45, 580, 780, 1);

        jSeparator24.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator24.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator24.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator24.setName("jSeparator24"); // NOI18N
        FormInput.add(jSeparator24);
        jSeparator24.setBounds(45, 620, 780, 1);

        jSeparator25.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator25.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator25.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator25.setName("jSeparator25"); // NOI18N
        FormInput.add(jSeparator25);
        jSeparator25.setBounds(45, 660, 780, 1);

        jSeparator26.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator26.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator26.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator26.setName("jSeparator26"); // NOI18N
        FormInput.add(jSeparator26);
        jSeparator26.setBounds(45, 700, 780, 1);

        jSeparator27.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator27.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator27.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator27.setName("jSeparator27"); // NOI18N
        FormInput.add(jSeparator27);
        jSeparator27.setBounds(45, 740, 780, 1);

        jSeparator28.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator28.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator28.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator28.setName("jSeparator28"); // NOI18N
        FormInput.add(jSeparator28);
        jSeparator28.setBounds(45, 780, 780, 1);

        jSeparator29.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator29.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator29.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator29.setName("jSeparator29"); // NOI18N
        FormInput.add(jSeparator29);
        jSeparator29.setBounds(45, 820, 780, 1);

        jSeparator30.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator30.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator30.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator30.setName("jSeparator30"); // NOI18N
        FormInput.add(jSeparator30);
        jSeparator30.setBounds(45, 860, 780, 1);

        MBOkiri.setFocusTraversalPolicyProvider(true);
        MBOkiri.setName("MBOkiri"); // NOI18N
        MBOkiri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MBOkiriKeyPressed(evt);
            }
        });
        FormInput.add(MBOkiri);
        MBOkiri.setBounds(520, 950, 300, 23);

        Visuskanan.setFocusTraversalPolicyProvider(true);
        Visuskanan.setName("Visuskanan"); // NOI18N
        Visuskanan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                VisuskananKeyPressed(evt);
            }
        });
        FormInput.add(Visuskanan);
        Visuskanan.setBounds(50, 430, 300, 23);

        Visuskiri.setFocusTraversalPolicyProvider(true);
        Visuskiri.setName("Visuskiri"); // NOI18N
        Visuskiri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                VisuskiriKeyPressed(evt);
            }
        });
        FormInput.add(Visuskiri);
        Visuskiri.setBounds(520, 430, 300, 23);

        CCkanan.setFocusTraversalPolicyProvider(true);
        CCkanan.setName("CCkanan"); // NOI18N
        CCkanan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CCkananKeyPressed(evt);
            }
        });
        FormInput.add(CCkanan);
        CCkanan.setBounds(50, 470, 300, 23);

        CCkiri.setFocusTraversalPolicyProvider(true);
        CCkiri.setName("CCkiri"); // NOI18N
        CCkiri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CCkiriKeyPressed(evt);
            }
        });
        FormInput.add(CCkiri);
        CCkiri.setBounds(520, 470, 300, 23);

        Palkanan.setFocusTraversalPolicyProvider(true);
        Palkanan.setName("Palkanan"); // NOI18N
        Palkanan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PalkananKeyPressed(evt);
            }
        });
        FormInput.add(Palkanan);
        Palkanan.setBounds(50, 510, 300, 23);

        Palkiri.setFocusTraversalPolicyProvider(true);
        Palkiri.setName("Palkiri"); // NOI18N
        Palkiri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PalkiriKeyPressed(evt);
            }
        });
        FormInput.add(Palkiri);
        Palkiri.setBounds(520, 510, 300, 23);

        Conkanan.setFocusTraversalPolicyProvider(true);
        Conkanan.setName("Conkanan"); // NOI18N
        Conkanan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ConkananKeyPressed(evt);
            }
        });
        FormInput.add(Conkanan);
        Conkanan.setBounds(50, 550, 300, 23);

        Conkiri.setFocusTraversalPolicyProvider(true);
        Conkiri.setName("Conkiri"); // NOI18N
        Conkiri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ConkiriKeyPressed(evt);
            }
        });
        FormInput.add(Conkiri);
        Conkiri.setBounds(520, 550, 300, 23);

        Corneakanan.setFocusTraversalPolicyProvider(true);
        Corneakanan.setName("Corneakanan"); // NOI18N
        Corneakanan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CorneakananKeyPressed(evt);
            }
        });
        FormInput.add(Corneakanan);
        Corneakanan.setBounds(50, 590, 300, 23);

        Corneakiri.setFocusTraversalPolicyProvider(true);
        Corneakiri.setName("Corneakiri"); // NOI18N
        Corneakiri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CorneakiriKeyPressed(evt);
            }
        });
        FormInput.add(Corneakiri);
        Corneakiri.setBounds(520, 590, 300, 23);

        COAkanan.setFocusTraversalPolicyProvider(true);
        COAkanan.setName("COAkanan"); // NOI18N
        COAkanan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                COAkananKeyPressed(evt);
            }
        });
        FormInput.add(COAkanan);
        COAkanan.setBounds(50, 630, 300, 23);

        COAkiri.setFocusTraversalPolicyProvider(true);
        COAkiri.setName("COAkiri"); // NOI18N
        COAkiri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                COAkiriKeyPressed(evt);
            }
        });
        FormInput.add(COAkiri);
        COAkiri.setBounds(520, 630, 300, 23);

        Pupilkanan.setFocusTraversalPolicyProvider(true);
        Pupilkanan.setName("Pupilkanan"); // NOI18N
        Pupilkanan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PupilkananKeyPressed(evt);
            }
        });
        FormInput.add(Pupilkanan);
        Pupilkanan.setBounds(50, 670, 300, 23);

        Pupilkiri.setFocusTraversalPolicyProvider(true);
        Pupilkiri.setName("Pupilkiri"); // NOI18N
        Pupilkiri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PupilkiriKeyPressed(evt);
            }
        });
        FormInput.add(Pupilkiri);
        Pupilkiri.setBounds(520, 670, 300, 23);

        Lensakanan.setFocusTraversalPolicyProvider(true);
        Lensakanan.setName("Lensakanan"); // NOI18N
        Lensakanan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LensakananKeyPressed(evt);
            }
        });
        FormInput.add(Lensakanan);
        Lensakanan.setBounds(50, 710, 300, 23);

        Lensakiri.setFocusTraversalPolicyProvider(true);
        Lensakiri.setName("Lensakiri"); // NOI18N
        Lensakiri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LensakiriKeyPressed(evt);
            }
        });
        FormInput.add(Lensakiri);
        Lensakiri.setBounds(520, 710, 300, 23);

        Funduskanan.setFocusTraversalPolicyProvider(true);
        Funduskanan.setName("Funduskanan"); // NOI18N
        Funduskanan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FunduskananKeyPressed(evt);
            }
        });
        FormInput.add(Funduskanan);
        Funduskanan.setBounds(50, 750, 300, 23);

        Funduskiri.setFocusTraversalPolicyProvider(true);
        Funduskiri.setName("Funduskiri"); // NOI18N
        Funduskiri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FunduskiriKeyPressed(evt);
            }
        });
        FormInput.add(Funduskiri);
        Funduskiri.setBounds(520, 750, 300, 23);

        Papilkanan.setFocusTraversalPolicyProvider(true);
        Papilkanan.setName("Papilkanan"); // NOI18N
        Papilkanan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PapilkananKeyPressed(evt);
            }
        });
        FormInput.add(Papilkanan);
        Papilkanan.setBounds(50, 790, 300, 23);

        Papilkiri.setFocusTraversalPolicyProvider(true);
        Papilkiri.setName("Papilkiri"); // NOI18N
        Papilkiri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PapilkiriActionPerformed(evt);
            }
        });
        Papilkiri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PapilkiriKeyPressed(evt);
            }
        });
        FormInput.add(Papilkiri);
        Papilkiri.setBounds(520, 790, 300, 24);

        Retinakanan.setFocusTraversalPolicyProvider(true);
        Retinakanan.setName("Retinakanan"); // NOI18N
        Retinakanan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RetinakananKeyPressed(evt);
            }
        });
        FormInput.add(Retinakanan);
        Retinakanan.setBounds(50, 830, 300, 23);

        Retinakiri.setFocusTraversalPolicyProvider(true);
        Retinakiri.setName("Retinakiri"); // NOI18N
        Retinakiri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RetinakiriKeyPressed(evt);
            }
        });
        FormInput.add(Retinakiri);
        Retinakiri.setBounds(520, 830, 300, 23);

        Makulakanan.setFocusTraversalPolicyProvider(true);
        Makulakanan.setName("Makulakanan"); // NOI18N
        Makulakanan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MakulakananKeyPressed(evt);
            }
        });
        FormInput.add(Makulakanan);
        Makulakanan.setBounds(50, 870, 300, 23);

        Makulakiri.setFocusTraversalPolicyProvider(true);
        Makulakiri.setName("Makulakiri"); // NOI18N
        Makulakiri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MakulakiriKeyPressed(evt);
            }
        });
        FormInput.add(Makulakiri);
        Makulakiri.setBounds(520, 870, 300, 23);

        TIOkanan.setFocusTraversalPolicyProvider(true);
        TIOkanan.setName("TIOkanan"); // NOI18N
        TIOkanan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TIOkananKeyPressed(evt);
            }
        });
        FormInput.add(TIOkanan);
        TIOkanan.setBounds(50, 910, 300, 23);

        TIOkiri.setFocusTraversalPolicyProvider(true);
        TIOkiri.setName("TIOkiri"); // NOI18N
        TIOkiri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TIOkiriKeyPressed(evt);
            }
        });
        FormInput.add(TIOkiri);
        TIOkiri.setBounds(520, 910, 300, 23);

        MBOkanan.setFocusTraversalPolicyProvider(true);
        MBOkanan.setName("MBOkanan"); // NOI18N
        MBOkanan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MBOkananKeyPressed(evt);
            }
        });
        FormInput.add(MBOkanan);
        MBOkanan.setBounds(50, 950, 300, 23);

        jLabel44.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel44.setText("CC");
        jLabel44.setName("jLabel44"); // NOI18N
        FormInput.add(jLabel44);
        jLabel44.setBounds(350, 470, 170, 23);

        jLabel45.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel45.setText("Palpebra");
        jLabel45.setName("jLabel45"); // NOI18N
        FormInput.add(jLabel45);
        jLabel45.setBounds(350, 510, 170, 23);

        jLabel46.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel46.setText("Conjungtiva");
        jLabel46.setName("jLabel46"); // NOI18N
        FormInput.add(jLabel46);
        jLabel46.setBounds(350, 550, 170, 23);

        jLabel47.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel47.setText("Cornea");
        jLabel47.setName("jLabel47"); // NOI18N
        FormInput.add(jLabel47);
        jLabel47.setBounds(350, 590, 170, 23);

        jLabel48.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel48.setText("COA");
        jLabel48.setName("jLabel48"); // NOI18N
        FormInput.add(jLabel48);
        jLabel48.setBounds(350, 630, 170, 23);

        jLabel49.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel49.setText("Pupil");
        jLabel49.setName("jLabel49"); // NOI18N
        FormInput.add(jLabel49);
        jLabel49.setBounds(350, 670, 170, 23);

        jLabel50.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel50.setText("Lensa");
        jLabel50.setName("jLabel50"); // NOI18N
        FormInput.add(jLabel50);
        jLabel50.setBounds(350, 710, 170, 23);

        jLabel51.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel51.setText("Fundus Media");
        jLabel51.setName("jLabel51"); // NOI18N
        FormInput.add(jLabel51);
        jLabel51.setBounds(350, 750, 170, 23);

        jLabel52.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel52.setText("Papil");
        jLabel52.setName("jLabel52"); // NOI18N
        FormInput.add(jLabel52);
        jLabel52.setBounds(350, 790, 170, 23);

        jLabel53.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel53.setText("Retina");
        jLabel53.setName("jLabel53"); // NOI18N
        FormInput.add(jLabel53);
        jLabel53.setBounds(350, 830, 170, 23);

        jLabel54.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel54.setText("Makula");
        jLabel54.setName("jLabel54"); // NOI18N
        FormInput.add(jLabel54);
        jLabel54.setBounds(350, 870, 170, 23);

        jLabel55.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel55.setText("TIO");
        jLabel55.setName("jLabel55"); // NOI18N
        FormInput.add(jLabel55);
        jLabel55.setBounds(350, 910, 170, 23);

        jLabel56.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel56.setText("MBO");
        jLabel56.setName("jLabel56"); // NOI18N
        FormInput.add(jLabel56);
        jLabel56.setBounds(350, 950, 170, 23);

        jLabel12.setText("BB :");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(225, 240, 30, 23);

        BB.setFocusTraversalPolicyProvider(true);
        BB.setName("BB"); // NOI18N
        BB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BBKeyPressed(evt);
            }
        });
        FormInput.add(BB);
        BB.setBounds(259, 240, 55, 23);

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel13.setText("Kg");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(317, 240, 30, 23);

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel16.setText("x/menit");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput.add(jLabel16);
        jLabel16.setBounds(640, 240, 50, 23);

        Nadi.setFocusTraversalPolicyProvider(true);
        Nadi.setName("Nadi"); // NOI18N
        Nadi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NadiKeyPressed(evt);
            }
        });
        FormInput.add(Nadi);
        Nadi.setBounds(582, 240, 55, 23);

        jLabel17.setText("Nadi :");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(538, 240, 40, 23);

        jLabel18.setText("Suhu :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(378, 240, 40, 23);

        Suhu.setFocusTraversalPolicyProvider(true);
        Suhu.setName("Suhu"); // NOI18N
        Suhu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SuhuKeyPressed(evt);
            }
        });
        FormInput.add(Suhu);
        Suhu.setBounds(422, 240, 55, 23);

        jLabel22.setText(":");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(0, 240, 64, 23);

        TD.setFocusTraversalPolicyProvider(true);
        TD.setName("TD"); // NOI18N
        TD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TDKeyPressed(evt);
            }
        });
        FormInput.add(TD);
        TD.setBounds(68, 240, 76, 23);

        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel20.setText("°C");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(480, 240, 30, 23);

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel23.setText("mmHg");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(147, 240, 50, 23);

        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel25.setText("x/menit");
        jLabel25.setName("jLabel25"); // NOI18N
        FormInput.add(jLabel25);
        jLabel25.setBounds(815, 240, 50, 23);

        RR.setFocusTraversalPolicyProvider(true);
        RR.setName("RR"); // NOI18N
        RR.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RRKeyPressed(evt);
            }
        });
        FormInput.add(RR);
        RR.setBounds(757, 240, 55, 23);

        jLabel26.setText("RR :");
        jLabel26.setName("jLabel26"); // NOI18N
        FormInput.add(jLabel26);
        jLabel26.setBounds(688, 240, 65, 23);

        jLabel28.setText("Status Nutrisi :");
        jLabel28.setName("jLabel28"); // NOI18N
        FormInput.add(jLabel28);
        jLabel28.setBounds(450, 270, 100, 23);

        StatusNutrisi.setFocusTraversalPolicyProvider(true);
        StatusNutrisi.setName("StatusNutrisi"); // NOI18N
        StatusNutrisi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                StatusNutrisiKeyPressed(evt);
            }
        });
        FormInput.add(StatusNutrisi);
        StatusNutrisi.setBounds(554, 270, 300, 23);

        jLabel29.setText(":");
        jLabel29.setName("jLabel29"); // NOI18N
        FormInput.add(jLabel29);
        jLabel29.setBounds(0, 270, 76, 23);

        Nyeri.setFocusTraversalPolicyProvider(true);
        Nyeri.setName("Nyeri"); // NOI18N
        Nyeri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NyeriKeyPressed(evt);
            }
        });
        FormInput.add(Nyeri);
        Nyeri.setBounds(80, 270, 300, 23);

        jSeparator31.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator31.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator31.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator31.setName("jSeparator31"); // NOI18N
        FormInput.add(jSeparator31);
        jSeparator31.setBounds(0, 980, 880, 1);

        scrollPane13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane13.setName("scrollPane13"); // NOI18N

        Penunjang.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Penunjang.setColumns(20);
        Penunjang.setRows(5);
        Penunjang.setName("Penunjang"); // NOI18N
        Penunjang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenunjangKeyPressed(evt);
            }
        });
        scrollPane13.setViewportView(Penunjang);

        FormInput.add(scrollPane13);
        scrollPane13.setBounds(594, 1020, 260, 63);

        jLabel109.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel109.setText("Penunjang Lainnya :");
        jLabel109.setName("jLabel109"); // NOI18N
        FormInput.add(jLabel109);
        jLabel109.setBounds(594, 1000, 190, 23);

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
        scrollPane9.setBounds(44, 1020, 260, 63);

        jLabel80.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel80.setText("Laboratorium :");
        jLabel80.setName("jLabel80"); // NOI18N
        FormInput.add(jLabel80);
        jLabel80.setBounds(44, 1000, 150, 23);

        jLabel81.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel81.setText("Radiologi :");
        jLabel81.setName("jLabel81"); // NOI18N
        FormInput.add(jLabel81);
        jLabel81.setBounds(319, 1000, 150, 23);

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
        scrollPane10.setBounds(319, 1020, 260, 63);

        jSeparator32.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator32.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator32.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator32.setName("jSeparator32"); // NOI18N
        FormInput.add(jSeparator32);
        jSeparator32.setBounds(0, 1160, 880, 1);

        jLabel110.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel110.setText("V. DIAGNOSIS/ASESMEN");
        jLabel110.setName("jLabel110"); // NOI18N
        FormInput.add(jLabel110);
        jLabel110.setBounds(10, 1160, 190, 23);

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
        scrollPane15.setBounds(44, 1200, 400, 43);

        scrollPane18.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane18.setName("scrollPane18"); // NOI18N

        DiagnosisBdg.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        DiagnosisBdg.setColumns(20);
        DiagnosisBdg.setRows(3);
        DiagnosisBdg.setName("DiagnosisBdg"); // NOI18N
        DiagnosisBdg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosisBdgKeyPressed(evt);
            }
        });
        scrollPane18.setViewportView(DiagnosisBdg);

        FormInput.add(scrollPane18);
        scrollPane18.setBounds(454, 1200, 400, 43);

        jLabel83.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel83.setText("Asesmen Kerja :");
        jLabel83.setName("jLabel83"); // NOI18N
        FormInput.add(jLabel83);
        jLabel83.setBounds(44, 1180, 150, 23);

        jLabel85.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel85.setText("Asesmen Banding :");
        jLabel85.setName("jLabel85"); // NOI18N
        FormInput.add(jLabel85);
        jLabel85.setBounds(454, 1180, 150, 23);

        jSeparator33.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator33.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator33.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator33.setName("jSeparator33"); // NOI18N
        FormInput.add(jSeparator33);
        jSeparator33.setBounds(0, 1250, 880, 1);

        jLabel103.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel103.setText("VI. PERMASALAHAN & TATALAKSANA");
        jLabel103.setName("jLabel103"); // NOI18N
        FormInput.add(jLabel103);
        jLabel103.setBounds(10, 1250, 190, 23);

        scrollPane22.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane22.setName("scrollPane22"); // NOI18N

        Terapi.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Terapi.setColumns(20);
        Terapi.setRows(3);
        Terapi.setName("Terapi"); // NOI18N
        Terapi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TerapiKeyPressed(evt);
            }
        });
        scrollPane22.setViewportView(Terapi);

        FormInput.add(scrollPane22);
        scrollPane22.setBounds(454, 1290, 400, 43);

        jLabel111.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel111.setText("Terapi/Pengobatan :");
        jLabel111.setName("jLabel111"); // NOI18N
        FormInput.add(jLabel111);
        jLabel111.setBounds(454, 1270, 190, 20);

        jLabel108.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel108.setText("Permasalahan :");
        jLabel108.setName("jLabel108"); // NOI18N
        FormInput.add(jLabel108);
        jLabel108.setBounds(44, 1270, 190, 20);

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
        scrollPane20.setBounds(44, 1290, 400, 43);

        scrollPane23.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane23.setName("scrollPane23"); // NOI18N

        Tindakan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Tindakan.setColumns(20);
        Tindakan.setRows(3);
        Tindakan.setName("Tindakan"); // NOI18N
        Tindakan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TindakanKeyPressed(evt);
            }
        });
        scrollPane23.setViewportView(Tindakan);

        FormInput.add(scrollPane23);
        scrollPane23.setBounds(44, 1360, 810, 43);

        jLabel112.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel112.setText("Tindakan/Rencana Tindakan :");
        jLabel112.setName("jLabel112"); // NOI18N
        FormInput.add(jLabel112);
        jLabel112.setBounds(44, 1340, 320, 20);

        jSeparator34.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator34.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator34.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator34.setName("jSeparator34"); // NOI18N
        FormInput.add(jSeparator34);
        jSeparator34.setBounds(0, 1410, 880, 1);

        jLabel113.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel113.setText("VII. EDUKASI");
        jLabel113.setName("jLabel113"); // NOI18N
        FormInput.add(jLabel113);
        jLabel113.setBounds(10, 1410, 190, 23);

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
        scrollPane14.setBounds(44, 1430, 810, 63);

        jSeparator35.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator35.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator35.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator35.setName("jSeparator35"); // NOI18N
        FormInput.add(jSeparator35);
        jSeparator35.setBounds(45, 420, 780, 1);

        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel36.setText("Keluhan Utama");
        jLabel36.setName("jLabel36"); // NOI18N
        FormInput.add(jLabel36);
        jLabel36.setBounds(44, 90, 90, 23);

        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel14.setText("Riwayat Penggunaan Obat");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(44, 170, 150, 23);

        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel24.setText("TD");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(44, 240, 30, 23);

        jLabel39.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel39.setText("Nyeri");
        jLabel39.setName("jLabel39"); // NOI18N
        FormInput.add(jLabel39);
        jLabel39.setBounds(44, 270, 50, 23);

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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "07-08-2024" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "07-08-2024" }));
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
            if(akses.getkode().equals("Admin Utama")){
                simpan();
            }else{
                if(TanggalRegistrasi.getText().equals("")){
                    TanggalRegistrasi.setText(Sequel.cariIsi("select concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) from reg_periksa where reg_periksa.no_rawat=?",TNoRw.getText()));
                }
                if(Sequel.cekTanggalRegistrasi(TanggalRegistrasi.getText(),Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19))==true){
                    simpan();
                }
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
                    if(Sequel.cekTanggal48jam(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString(),Sequel.ambiltanggalsekarang())==true){
                        hapus();
                    }
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
                        if(Sequel.cekTanggal48jam(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString(),Sequel.ambiltanggalsekarang())==true){
                            if(TanggalRegistrasi.getText().equals("")){
                                TanggalRegistrasi.setText(Sequel.cariIsi("select concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) from reg_periksa where reg_periksa.no_rawat=?",TNoRw.getText()));
                            }
                            if(Sequel.cekTanggalRegistrasi(TanggalRegistrasi.getText(),Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19))==true){
                                ganti();
                            }
                        }
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
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Rawat</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.RM</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Pasien</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tgl.Lahir</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>J.K.</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kode Dokter</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Dokter</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tanggal</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Anamnesis</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Hubungan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keluhan Utama</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Riwayat Penyakit Sekarang</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Riwayat Penyakit Dahulu</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Riwayat Penggunakan Obat</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Riwayat Alergi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Status</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>TD(mmHg)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nadi(x/menit)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>RR(x/menit)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Suhu</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nyeri</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>BB(Kg)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Visuskanan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Visuskiri</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>CCkanan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>CCkiri</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Palkanan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Palkiri</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Conkanan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Conkiri</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Corneakanan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Corneakiri</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>COAkanan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>COAkiri</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Pupilkanan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Pupilkiri</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Lensakanan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Lensakiri</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Funduskanan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Funduskiri</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Papilkanan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Papilkiri</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Retinakanan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Retinakiri</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Makulakanan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Makulakiri</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>TIOkanan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>TIOkiri</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>MBOkanan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>MBOkiri</b></td>"+ 
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Laboratorium</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Radiologi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Penunjang Lainnya</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tes Penglihatan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Pemeriksaan Lain</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Diagnosis/Asesmen</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Diagnosis Banding</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Permasalahan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Terapi/Pengobatan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tindakan/Rencana Pengobatan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Edukasi</b></td>"+
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
                        "</tr>");
                }
                
                LoadHTML.setText(
                    "<html>"+
                      "<table width='5000px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
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
                            "<table width='5000px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
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
        Valid.pindah(evt,Edukasi,Hubungan);
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

    private void RPSKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RPSKeyPressed
        Valid.pindah2(evt,KeluhanUtama,RPD);
    }//GEN-LAST:event_RPSKeyPressed

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
                param.put("lokalis",getClass().getResource("/picture/mata.png").openStream()); 
            } catch (Exception e) {
            }
            try {
                param.put("lokalis2",getClass().getResource("/picture/mata.png").openStream());
            } catch (Exception e) {
            }    
            finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
            param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbObat.getValueAt(tbObat.getSelectedRow(),6).toString()+"\nID "+(finger.equals("")?tbObat.getValueAt(tbObat.getSelectedRow(),5).toString():finger)+"\n"+Valid.SetTgl3(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString()));
            Valid.MyReportqry("rptCetakPenilaianAwalMedisRalanMata.jasper","report","::[ Laporan Pengkajian Awal Medis Rawat Jalan Mata ]::",
                "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penilaian_medis_ralan_mata.tanggal,"+
                "penilaian_medis_ralan_mata.kd_dokter,penilaian_medis_ralan_mata.anamnesis,penilaian_medis_ralan_mata.hubungan,penilaian_medis_ralan_mata.keluhan_utama,penilaian_medis_ralan_mata.rps,penilaian_medis_ralan_mata.rpd,penilaian_medis_ralan_mata.rpo,penilaian_medis_ralan_mata.alergi,"+
                "penilaian_medis_ralan_mata.status,penilaian_medis_ralan_mata.td,penilaian_medis_ralan_mata.nadi,penilaian_medis_ralan_mata.rr,penilaian_medis_ralan_mata.suhu,penilaian_medis_ralan_mata.nyeri,penilaian_medis_ralan_mata.bb,"+
                "penilaian_medis_ralan_mata.visuskanan,penilaian_medis_ralan_mata.visuskiri,penilaian_medis_ralan_mata.cckanan,penilaian_medis_ralan_mata.cckiri,penilaian_medis_ralan_mata.palkanan,penilaian_medis_ralan_mata.palkiri,penilaian_medis_ralan_mata.conkanan,penilaian_medis_ralan_mata.conkiri,"+
                "penilaian_medis_ralan_mata.corneakanan,penilaian_medis_ralan_mata.corneakiri,penilaian_medis_ralan_mata.coakanan,penilaian_medis_ralan_mata.coakiri,penilaian_medis_ralan_mata.pupilkanan,penilaian_medis_ralan_mata.pupilkiri,penilaian_medis_ralan_mata.lensakanan,penilaian_medis_ralan_mata.lensakiri,"+
                "penilaian_medis_ralan_mata.funduskanan,penilaian_medis_ralan_mata.funduskiri,penilaian_medis_ralan_mata.papilkanan,penilaian_medis_ralan_mata.papilkiri,penilaian_medis_ralan_mata.retinakanan,penilaian_medis_ralan_mata.retinakiri,penilaian_medis_ralan_mata.makulakanan,penilaian_medis_ralan_mata.makulakiri,"+
                "penilaian_medis_ralan_mata.tiokanan,penilaian_medis_ralan_mata.tiokiri,penilaian_medis_ralan_mata.mbokanan,penilaian_medis_ralan_mata.mbokiri,penilaian_medis_ralan_mata.lab,penilaian_medis_ralan_mata.rad,penilaian_medis_ralan_mata.penunjang,penilaian_medis_ralan_mata.tes,penilaian_medis_ralan_mata.pemeriksaan,"+
                "penilaian_medis_ralan_mata.diagnosis,penilaian_medis_ralan_mata.diagnosisbdg,penilaian_medis_ralan_mata.permasalahan,penilaian_medis_ralan_mata.terapi,penilaian_medis_ralan_mata.tindakan,penilaian_medis_ralan_mata.edukasi,dokter.nm_dokter "+
                "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join penilaian_medis_ralan_mata on reg_periksa.no_rawat=penilaian_medis_ralan_mata.no_rawat "+
                "inner join dokter on penilaian_medis_ralan_mata.kd_dokter=dokter.kd_dokter where penilaian_medis_ralan_mata.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'",param);
        }
    }//GEN-LAST:event_MnPenilaianMedisActionPerformed

    private void PemeriksaanLainKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PemeriksaanLainKeyPressed
      Valid.pindah2(evt,TesPenglihatan,Diagnosis);
    }//GEN-LAST:event_PemeriksaanLainKeyPressed

    private void TesPenglihatanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TesPenglihatanKeyPressed
      Valid.pindah2(evt,Penunjang,PemeriksaanLain);
    }//GEN-LAST:event_TesPenglihatanKeyPressed

    private void MBOkiriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MBOkiriKeyPressed
      Valid.pindah(evt,MBOkanan,Laborat);
    }//GEN-LAST:event_MBOkiriKeyPressed

    private void VisuskananKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_VisuskananKeyPressed
      Valid.pindah(evt,StatusNutrisi,Visuskiri);
    }//GEN-LAST:event_VisuskananKeyPressed

    private void VisuskiriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_VisuskiriKeyPressed
      Valid.pindah(evt,Visuskanan,CCkanan);
    }//GEN-LAST:event_VisuskiriKeyPressed

    private void CCkananKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CCkananKeyPressed
      Valid.pindah(evt,Visuskiri,CCkiri);
    }//GEN-LAST:event_CCkananKeyPressed

    private void CCkiriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CCkiriKeyPressed
      Valid.pindah(evt,CCkanan,Palkanan);
    }//GEN-LAST:event_CCkiriKeyPressed

    private void PalkananKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PalkananKeyPressed
      Valid.pindah(evt,CCkiri,Palkiri);
    }//GEN-LAST:event_PalkananKeyPressed

    private void PalkiriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PalkiriKeyPressed
      Valid.pindah(evt,Palkanan,Conkanan);
    }//GEN-LAST:event_PalkiriKeyPressed

    private void ConkananKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ConkananKeyPressed
      Valid.pindah(evt,Palkiri,Conkiri);
    }//GEN-LAST:event_ConkananKeyPressed

    private void ConkiriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ConkiriKeyPressed
      Valid.pindah(evt,Conkanan,Corneakanan);
    }//GEN-LAST:event_ConkiriKeyPressed

    private void CorneakananKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CorneakananKeyPressed
      Valid.pindah(evt,Conkiri,Corneakiri);
    }//GEN-LAST:event_CorneakananKeyPressed

    private void CorneakiriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CorneakiriKeyPressed
      Valid.pindah(evt,Corneakanan,COAkanan);
    }//GEN-LAST:event_CorneakiriKeyPressed

    private void COAkananKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_COAkananKeyPressed
      Valid.pindah(evt,Corneakiri,COAkiri);
    }//GEN-LAST:event_COAkananKeyPressed

    private void COAkiriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_COAkiriKeyPressed
      Valid.pindah(evt,COAkanan,Pupilkanan);
    }//GEN-LAST:event_COAkiriKeyPressed

    private void PupilkananKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PupilkananKeyPressed
      Valid.pindah(evt,COAkiri,Pupilkiri);
    }//GEN-LAST:event_PupilkananKeyPressed

    private void PupilkiriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PupilkiriKeyPressed
      Valid.pindah(evt,Pupilkanan,Lensakanan);
    }//GEN-LAST:event_PupilkiriKeyPressed

    private void LensakananKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LensakananKeyPressed
      Valid.pindah(evt,Pupilkiri,Lensakiri);
    }//GEN-LAST:event_LensakananKeyPressed

    private void LensakiriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LensakiriKeyPressed
      Valid.pindah(evt,Lensakanan,Funduskanan);
    }//GEN-LAST:event_LensakiriKeyPressed

    private void FunduskananKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FunduskananKeyPressed
      Valid.pindah(evt,Lensakiri,Funduskiri);
    }//GEN-LAST:event_FunduskananKeyPressed

    private void FunduskiriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FunduskiriKeyPressed
      Valid.pindah(evt,Funduskanan,Papilkanan);
    }//GEN-LAST:event_FunduskiriKeyPressed

    private void PapilkananKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PapilkananKeyPressed
      Valid.pindah(evt,Funduskiri,Papilkiri);
    }//GEN-LAST:event_PapilkananKeyPressed

    private void PapilkiriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PapilkiriKeyPressed
      Valid.pindah(evt,Papilkanan,Retinakanan);
    }//GEN-LAST:event_PapilkiriKeyPressed

    private void RetinakananKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RetinakananKeyPressed
      Valid.pindah(evt,Papilkiri,Retinakiri);
    }//GEN-LAST:event_RetinakananKeyPressed

    private void RetinakiriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RetinakiriKeyPressed
       Valid.pindah(evt,Retinakanan,Makulakanan);
    }//GEN-LAST:event_RetinakiriKeyPressed

    private void MakulakananKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MakulakananKeyPressed
       Valid.pindah(evt,Retinakiri,Makulakiri);
    }//GEN-LAST:event_MakulakananKeyPressed

    private void MakulakiriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MakulakiriKeyPressed
      Valid.pindah(evt,Makulakanan,TIOkanan);
    }//GEN-LAST:event_MakulakiriKeyPressed

    private void TIOkananKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TIOkananKeyPressed
      Valid.pindah(evt,Makulakiri,TIOkiri);
    }//GEN-LAST:event_TIOkananKeyPressed

    private void TIOkiriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TIOkiriKeyPressed
      Valid.pindah(evt,TIOkanan,MBOkanan);
    }//GEN-LAST:event_TIOkiriKeyPressed

    private void MBOkananKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MBOkananKeyPressed
      Valid.pindah(evt,TIOkiri,MBOkiri);
    }//GEN-LAST:event_MBOkananKeyPressed

    private void PapilkiriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PapilkiriActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PapilkiriActionPerformed

    private void BBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BBKeyPressed
        Valid.pindah(evt,TD,Suhu);
    }//GEN-LAST:event_BBKeyPressed

    private void NadiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NadiKeyPressed
        Valid.pindah(evt,Suhu,RR);
    }//GEN-LAST:event_NadiKeyPressed

    private void SuhuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SuhuKeyPressed
        Valid.pindah(evt,BB,Nadi);
    }//GEN-LAST:event_SuhuKeyPressed

    private void TDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TDKeyPressed
        Valid.pindah(evt,Alergi,BB);
    }//GEN-LAST:event_TDKeyPressed

    private void RRKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RRKeyPressed
        Valid.pindah(evt,Nadi,Nyeri);
    }//GEN-LAST:event_RRKeyPressed

    private void StatusNutrisiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_StatusNutrisiKeyPressed
        Valid.pindah(evt,Nyeri,Visuskanan);
    }//GEN-LAST:event_StatusNutrisiKeyPressed

    private void NyeriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NyeriKeyPressed
        Valid.pindah(evt,RR,StatusNutrisi);
    }//GEN-LAST:event_NyeriKeyPressed

    private void PenunjangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenunjangKeyPressed
        Valid.pindah2(evt,Edukasi,TesPenglihatan);
    }//GEN-LAST:event_PenunjangKeyPressed

    private void LaboratKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LaboratKeyPressed
        Valid.pindah2(evt,MBOkiri,Radiologi);
    }//GEN-LAST:event_LaboratKeyPressed

    private void RadiologiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RadiologiKeyPressed
        Valid.pindah2(evt,Laborat,Penunjang);
    }//GEN-LAST:event_RadiologiKeyPressed

    private void DiagnosisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosisKeyPressed
        Valid.pindah2(evt,PemeriksaanLain,DiagnosisBdg);
    }//GEN-LAST:event_DiagnosisKeyPressed

    private void DiagnosisBdgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosisBdgKeyPressed
        Valid.pindah2(evt,Diagnosis,Permasalahan);
    }//GEN-LAST:event_DiagnosisBdgKeyPressed

    private void TerapiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TerapiKeyPressed
        Valid.pindah2(evt,Permasalahan,Tindakan);
    }//GEN-LAST:event_TerapiKeyPressed

    private void PermasalahanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PermasalahanKeyPressed
        Valid.pindah2(evt,DiagnosisBdg,Terapi);
    }//GEN-LAST:event_PermasalahanKeyPressed

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
            RMPenilaianAwalMedisRalanMata dialog = new RMPenilaianAwalMedisRalanMata(new javax.swing.JFrame(), true);
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
    private widget.TextBox CCkanan;
    private widget.TextBox CCkiri;
    private widget.TextBox COAkanan;
    private widget.TextBox COAkiri;
    private widget.TextBox Conkanan;
    private widget.TextBox Conkiri;
    private widget.TextBox Corneakanan;
    private widget.TextBox Corneakiri;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.TextArea Diagnosis;
    private widget.TextArea DiagnosisBdg;
    private widget.TextArea Edukasi;
    private widget.PanelBiasa FormInput;
    private widget.TextBox Funduskanan;
    private widget.TextBox Funduskiri;
    private widget.TextBox Hubungan;
    private widget.TextBox Jk;
    private widget.TextBox KdDokter;
    private widget.TextArea KeluhanUtama;
    private widget.Label LCount;
    private widget.TextArea Laborat;
    private widget.TextBox Lensakanan;
    private widget.TextBox Lensakiri;
    private widget.editorpane LoadHTML;
    private widget.TextBox MBOkanan;
    private widget.TextBox MBOkiri;
    private widget.TextBox Makulakanan;
    private widget.TextBox Makulakiri;
    private javax.swing.JMenuItem MnPenilaianMedis;
    private widget.TextBox Nadi;
    private widget.TextBox NmDokter;
    private widget.TextBox Nyeri;
    private widget.TextBox Palkanan;
    private widget.TextBox Palkiri;
    private usu.widget.glass.PanelGlass PanelWall;
    private usu.widget.glass.PanelGlass PanelWall1;
    private widget.TextBox Papilkanan;
    private widget.TextBox Papilkiri;
    private widget.TextArea PemeriksaanLain;
    private widget.TextArea Penunjang;
    private widget.TextArea Permasalahan;
    private widget.TextBox Pupilkanan;
    private widget.TextBox Pupilkiri;
    private widget.TextArea RPD;
    private widget.TextArea RPO;
    private widget.TextArea RPS;
    private widget.TextBox RR;
    private widget.TextArea Radiologi;
    private widget.TextBox Retinakanan;
    private widget.TextBox Retinakiri;
    private widget.ScrollPane Scroll;
    private widget.TextBox StatusNutrisi;
    private widget.TextBox Suhu;
    private widget.TextBox TCari;
    private widget.TextBox TD;
    private widget.TextBox TIOkanan;
    private widget.TextBox TIOkiri;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private javax.swing.JTabbedPane TabRawat;
    private widget.TextBox TanggalRegistrasi;
    private widget.TextArea Terapi;
    private widget.TextArea TesPenglihatan;
    private widget.Tanggal TglAsuhan;
    private widget.TextBox TglLahir;
    private widget.TextArea Tindakan;
    private widget.TextBox Visuskanan;
    private widget.TextBox Visuskiri;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.Label jLabel10;
    private widget.Label jLabel100;
    private widget.Label jLabel101;
    private widget.Label jLabel103;
    private widget.Label jLabel108;
    private widget.Label jLabel109;
    private widget.Label jLabel11;
    private widget.Label jLabel110;
    private widget.Label jLabel111;
    private widget.Label jLabel112;
    private widget.Label jLabel113;
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
    private widget.Label jLabel31;
    private widget.Label jLabel32;
    private widget.Label jLabel33;
    private widget.Label jLabel34;
    private widget.Label jLabel35;
    private widget.Label jLabel36;
    private widget.Label jLabel37;
    private widget.Label jLabel38;
    private widget.Label jLabel39;
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
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel80;
    private widget.Label jLabel81;
    private widget.Label jLabel82;
    private widget.Label jLabel83;
    private widget.Label jLabel84;
    private widget.Label jLabel85;
    private widget.Label jLabel9;
    private widget.Label jLabel94;
    private widget.Label jLabel99;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator18;
    private javax.swing.JSeparator jSeparator19;
    private javax.swing.JSeparator jSeparator20;
    private javax.swing.JSeparator jSeparator21;
    private javax.swing.JSeparator jSeparator22;
    private javax.swing.JSeparator jSeparator23;
    private javax.swing.JSeparator jSeparator24;
    private javax.swing.JSeparator jSeparator25;
    private javax.swing.JSeparator jSeparator26;
    private javax.swing.JSeparator jSeparator27;
    private javax.swing.JSeparator jSeparator28;
    private javax.swing.JSeparator jSeparator29;
    private javax.swing.JSeparator jSeparator30;
    private javax.swing.JSeparator jSeparator31;
    private javax.swing.JSeparator jSeparator32;
    private javax.swing.JSeparator jSeparator33;
    private javax.swing.JSeparator jSeparator34;
    private javax.swing.JSeparator jSeparator35;
    private widget.Label label11;
    private widget.Label label14;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollInput;
    private widget.ScrollPane scrollPane1;
    private widget.ScrollPane scrollPane10;
    private widget.ScrollPane scrollPane13;
    private widget.ScrollPane scrollPane14;
    private widget.ScrollPane scrollPane15;
    private widget.ScrollPane scrollPane18;
    private widget.ScrollPane scrollPane19;
    private widget.ScrollPane scrollPane2;
    private widget.ScrollPane scrollPane20;
    private widget.ScrollPane scrollPane21;
    private widget.ScrollPane scrollPane22;
    private widget.ScrollPane scrollPane23;
    private widget.ScrollPane scrollPane4;
    private widget.ScrollPane scrollPane7;
    private widget.ScrollPane scrollPane9;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            if(TCari.getText().trim().equals("")){
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penilaian_medis_ralan_mata.tanggal,"+
                        "penilaian_medis_ralan_mata.kd_dokter,penilaian_medis_ralan_mata.anamnesis,penilaian_medis_ralan_mata.hubungan,penilaian_medis_ralan_mata.keluhan_utama,penilaian_medis_ralan_mata.rps,penilaian_medis_ralan_mata.rpd,penilaian_medis_ralan_mata.rpo,penilaian_medis_ralan_mata.alergi,"+
                        "penilaian_medis_ralan_mata.status,penilaian_medis_ralan_mata.td,penilaian_medis_ralan_mata.nadi,penilaian_medis_ralan_mata.rr,penilaian_medis_ralan_mata.suhu,penilaian_medis_ralan_mata.nyeri,penilaian_medis_ralan_mata.bb,"+
                        "penilaian_medis_ralan_mata.visuskanan,penilaian_medis_ralan_mata.visuskiri,penilaian_medis_ralan_mata.cckanan,penilaian_medis_ralan_mata.cckiri,penilaian_medis_ralan_mata.palkanan,penilaian_medis_ralan_mata.palkiri,penilaian_medis_ralan_mata.conkanan,penilaian_medis_ralan_mata.conkiri,"+
                        "penilaian_medis_ralan_mata.corneakanan,penilaian_medis_ralan_mata.corneakiri,penilaian_medis_ralan_mata.coakanan,penilaian_medis_ralan_mata.coakiri,penilaian_medis_ralan_mata.pupilkanan,penilaian_medis_ralan_mata.pupilkiri,penilaian_medis_ralan_mata.lensakanan,penilaian_medis_ralan_mata.lensakiri,"+
                        "penilaian_medis_ralan_mata.funduskanan,penilaian_medis_ralan_mata.funduskiri,penilaian_medis_ralan_mata.papilkanan,penilaian_medis_ralan_mata.papilkiri,penilaian_medis_ralan_mata.retinakanan,penilaian_medis_ralan_mata.retinakiri,penilaian_medis_ralan_mata.makulakanan,penilaian_medis_ralan_mata.makulakiri,"+
                        "penilaian_medis_ralan_mata.tiokanan,penilaian_medis_ralan_mata.tiokiri,penilaian_medis_ralan_mata.mbokanan,penilaian_medis_ralan_mata.mbokiri,penilaian_medis_ralan_mata.lab,penilaian_medis_ralan_mata.rad,penilaian_medis_ralan_mata.penunjang,penilaian_medis_ralan_mata.tes,penilaian_medis_ralan_mata.pemeriksaan,"+
                        "penilaian_medis_ralan_mata.diagnosis,penilaian_medis_ralan_mata.diagnosisbdg,penilaian_medis_ralan_mata.permasalahan,penilaian_medis_ralan_mata.terapi,penilaian_medis_ralan_mata.tindakan,penilaian_medis_ralan_mata.edukasi,dokter.nm_dokter "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join penilaian_medis_ralan_mata on reg_periksa.no_rawat=penilaian_medis_ralan_mata.no_rawat "+
                        "inner join dokter on penilaian_medis_ralan_mata.kd_dokter=dokter.kd_dokter where "+
                        "penilaian_medis_ralan_mata.tanggal between ? and ? order by penilaian_medis_ralan_mata.tanggal");
            }else{
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penilaian_medis_ralan_mata.tanggal,"+
                        "penilaian_medis_ralan_mata.kd_dokter,penilaian_medis_ralan_mata.anamnesis,penilaian_medis_ralan_mata.hubungan,penilaian_medis_ralan_mata.keluhan_utama,penilaian_medis_ralan_mata.rps,penilaian_medis_ralan_mata.rpd,penilaian_medis_ralan_mata.rpo,penilaian_medis_ralan_mata.alergi,"+
                        "penilaian_medis_ralan_mata.status,penilaian_medis_ralan_mata.td,penilaian_medis_ralan_mata.nadi,penilaian_medis_ralan_mata.rr,penilaian_medis_ralan_mata.suhu,penilaian_medis_ralan_mata.nyeri,penilaian_medis_ralan_mata.bb,"+
                        "penilaian_medis_ralan_mata.visuskanan,penilaian_medis_ralan_mata.visuskiri,penilaian_medis_ralan_mata.cckanan,penilaian_medis_ralan_mata.cckiri,penilaian_medis_ralan_mata.palkanan,penilaian_medis_ralan_mata.palkiri,penilaian_medis_ralan_mata.conkanan,penilaian_medis_ralan_mata.conkiri,"+
                        "penilaian_medis_ralan_mata.corneakanan,penilaian_medis_ralan_mata.corneakiri,penilaian_medis_ralan_mata.coakanan,penilaian_medis_ralan_mata.coakiri,penilaian_medis_ralan_mata.pupilkanan,penilaian_medis_ralan_mata.pupilkiri,penilaian_medis_ralan_mata.lensakanan,penilaian_medis_ralan_mata.lensakiri,"+
                        "penilaian_medis_ralan_mata.funduskanan,penilaian_medis_ralan_mata.funduskiri,penilaian_medis_ralan_mata.papilkanan,penilaian_medis_ralan_mata.papilkiri,penilaian_medis_ralan_mata.retinakanan,penilaian_medis_ralan_mata.retinakiri,penilaian_medis_ralan_mata.makulakanan,penilaian_medis_ralan_mata.makulakiri,"+
                        "penilaian_medis_ralan_mata.tiokanan,penilaian_medis_ralan_mata.tiokiri,penilaian_medis_ralan_mata.mbokanan,penilaian_medis_ralan_mata.mbokiri,penilaian_medis_ralan_mata.lab,penilaian_medis_ralan_mata.rad,penilaian_medis_ralan_mata.penunjang,penilaian_medis_ralan_mata.tes,penilaian_medis_ralan_mata.pemeriksaan,"+
                        "penilaian_medis_ralan_mata.diagnosis,penilaian_medis_ralan_mata.diagnosisbdg,penilaian_medis_ralan_mata.permasalahan,penilaian_medis_ralan_mata.terapi,penilaian_medis_ralan_mata.tindakan,penilaian_medis_ralan_mata.edukasi,dokter.nm_dokter "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join penilaian_medis_ralan_mata on reg_periksa.no_rawat=penilaian_medis_ralan_mata.no_rawat "+
                        "inner join dokter on penilaian_medis_ralan_mata.kd_dokter=dokter.kd_dokter where "+
                        "penilaian_medis_ralan_mata.tanggal between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or "+
                        "penilaian_medis_ralan_mata.kd_dokter like ? or dokter.nm_dokter like ?) order by penilaian_medis_ralan_mata.tanggal");
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
                        rs.getString("anamnesis"),rs.getString("hubungan"),rs.getString("keluhan_utama"),rs.getString("rps"),rs.getString("rpd"),rs.getString("rpo"),rs.getString("alergi"),
                        rs.getString("status"),rs.getString("td"),rs.getString("nadi"),rs.getString("rr"),rs.getString("suhu"),rs.getString("nyeri"),rs.getString("bb"),
                        rs.getString("visuskanan"),rs.getString("visuskiri"),rs.getString("cckanan"),rs.getString("cckiri"),rs.getString("palkanan"),rs.getString("palkiri"),rs.getString("conkanan"),rs.getString("conkiri"),
                        rs.getString("corneakanan"),rs.getString("corneakiri"),rs.getString("coakanan"),rs.getString("coakiri"),rs.getString("pupilkanan"),rs.getString("pupilkiri"),rs.getString("lensakanan"),rs.getString("lensakiri"),
                        rs.getString("funduskanan"),rs.getString("funduskiri"),rs.getString("papilkanan"),rs.getString("papilkiri"),rs.getString("retinakanan"),rs.getString("retinakiri"),rs.getString("makulakanan"),rs.getString("makulakiri"),
                        rs.getString("tiokanan"),rs.getString("tiokiri"),rs.getString("mbokanan"),rs.getString("mbokiri"),rs.getString("lab"),rs.getString("rad"),rs.getString("penunjang"),rs.getString("tes"),rs.getString("pemeriksaan"),
                        rs.getString("diagnosis"),rs.getString("diagnosisbdg"),rs.getString("permasalahan"),rs.getString("terapi"),rs.getString("tindakan"),rs.getString("edukasi")
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
        StatusNutrisi.setText("");
        TD.setText("");
        Nadi.setText("");
        RR.setText("");
        Suhu.setText("");
        Nyeri.setText("");
        BB.setText("");
        Visuskanan.setText("");
        Visuskiri.setText("");
        CCkanan.setText("");
        CCkiri.setText("");
        Palkanan.setText("");
        Palkiri.setText("");
        Conkanan.setText("");
        Conkiri.setText("");
        Corneakanan.setText("");
        Corneakiri.setText("");
        COAkanan.setText("");
        COAkiri.setText("");
        Pupilkanan.setText("");
        Pupilkiri.setText("");
        Lensakanan.setText("");
        Lensakiri.setText("");
        Funduskanan.setText("");
        Funduskiri.setText("");
        Papilkanan.setText("");
        Papilkiri.setText("");
        Retinakanan.setText("");
        Retinakiri.setText("");
        Makulakanan.setText("");
        Makulakiri.setText("");
        TIOkanan.setText("");
        TIOkiri.setText("");
        MBOkanan.setText("");
        MBOkiri.setText("");
        Laborat.setText("");
        Radiologi.setText("");
        Penunjang.setText("");
        TesPenglihatan.setText("");
        PemeriksaanLain.setText("");
        Diagnosis.setText("");
        DiagnosisBdg.setText("");
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
            StatusNutrisi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
            TD.setText(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            Nadi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());
            RR.setText(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
            Suhu.setText(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
            Nyeri.setText(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString());
            BB.setText(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString());
            Visuskanan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString());
            Visuskiri.setText(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString());
            CCkanan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),24).toString());
            CCkiri.setText(tbObat.getValueAt(tbObat.getSelectedRow(),25).toString());
            Palkanan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),26).toString());
            Palkiri.setText(tbObat.getValueAt(tbObat.getSelectedRow(),27).toString());
            Conkanan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),28).toString());
            Conkiri.setText(tbObat.getValueAt(tbObat.getSelectedRow(),29).toString());
            Corneakanan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),30).toString());
            Corneakiri.setText(tbObat.getValueAt(tbObat.getSelectedRow(),31).toString());
            COAkanan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),32).toString());
            COAkiri.setText(tbObat.getValueAt(tbObat.getSelectedRow(),33).toString());
            Pupilkanan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),34).toString());
            Pupilkiri.setText(tbObat.getValueAt(tbObat.getSelectedRow(),35).toString());
            Lensakanan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),36).toString());
            Lensakiri.setText(tbObat.getValueAt(tbObat.getSelectedRow(),37).toString());
            Funduskanan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),38).toString());
            Funduskiri.setText(tbObat.getValueAt(tbObat.getSelectedRow(),39).toString());
            Papilkanan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),40).toString());
            Papilkiri.setText(tbObat.getValueAt(tbObat.getSelectedRow(),41).toString());
            Retinakanan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),42).toString());
            Retinakiri.setText(tbObat.getValueAt(tbObat.getSelectedRow(),43).toString());
            Makulakanan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),44).toString());
            Makulakiri.setText(tbObat.getValueAt(tbObat.getSelectedRow(),45).toString());
            TIOkanan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),46).toString());
            TIOkiri.setText(tbObat.getValueAt(tbObat.getSelectedRow(),47).toString());
            MBOkanan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),48).toString());
            MBOkiri.setText(tbObat.getValueAt(tbObat.getSelectedRow(),49).toString());
            Laborat.setText(tbObat.getValueAt(tbObat.getSelectedRow(),50).toString());
            Radiologi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),51).toString());
            Penunjang.setText(tbObat.getValueAt(tbObat.getSelectedRow(),52).toString());
            TesPenglihatan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),53).toString());
            PemeriksaanLain.setText(tbObat.getValueAt(tbObat.getSelectedRow(),54).toString());
            Diagnosis.setText(tbObat.getValueAt(tbObat.getSelectedRow(),55).toString());
            DiagnosisBdg.setText(tbObat.getValueAt(tbObat.getSelectedRow(),56).toString());
            Permasalahan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),57).toString());
            Terapi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),58).toString());
            Tindakan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),59).toString());
            Edukasi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),60).toString());
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
                ps.setString(1,TNoRw.getText());
                rs=ps.executeQuery();
                if(rs.next()){
                    TNoRM.setText(rs.getString("no_rkm_medis"));
                    DTPCari1.setDate(rs.getDate("tgl_registrasi"));
                    TPasien.setText(rs.getString("nm_pasien"));
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
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        DTPCari2.setDate(tgl2);    
        isRawat(); 
    }
    
    public void isCek(){
        BtnSimpan.setEnabled(akses.getpenilaian_awal_medis_ralan_mata());
        BtnHapus.setEnabled(akses.getpenilaian_awal_medis_ralan_mata());
        BtnEdit.setEnabled(akses.getpenilaian_awal_medis_ralan_mata());
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

    private void hapus() {
        if(Sequel.queryu2tf("delete from penilaian_medis_ralan_mata where no_rawat=?",1,new String[]{
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
        if(Sequel.mengedittf("penilaian_medis_ralan_mata","no_rawat=?","no_rawat=?,tanggal=?,kd_dokter=?,anamnesis=?,hubungan=?,keluhan_utama=?,rps=?,rpd=?,rpo=?,alergi=?,status=?,td=?,nadi=?,rr=?,suhu=?,"+
                "nyeri=?,bb=?,visuskanan=?,visuskiri=?,cckanan=?,cckiri=?,palkanan=?,palkiri=?,conkanan=?,conkiri=?,corneakanan=?,corneakiri=?,coakanan=?,coakiri=?,pupilkanan=?,pupilkiri=?,lensakanan=?,lensakiri=?,funduskanan=?,funduskiri=?,papilkanan=?,papilkiri=?,retinakanan=?,retinakiri=?,"+
                "makulakanan=?,makulakiri=?,tiokanan=?,tiokiri=?,mbokanan=?,mbokiri=?,lab=?,rad=?,penunjang=?,tes=?,pemeriksaan=?,diagnosis=?,diagnosisbdg=?,permasalahan=?,terapi=?,tindakan=?,edukasi=?",57,new String[]{
                TNoRw.getText(),Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),KdDokter.getText(),Anamnesis.getSelectedItem().toString(),Hubungan.getText(),
                    KeluhanUtama.getText(),RPS.getText(),RPD.getText(),RPO.getText(),Alergi.getText(),StatusNutrisi.getText(),TD.getText(),Nadi.getText(),RR.getText(),Suhu.getText(),Nyeri.getText(),BB.getText(),
                    Visuskanan.getText(),Visuskiri.getText(),CCkanan.getText(),CCkiri.getText(),Palkanan.getText(),Palkiri.getText(),Conkanan.getText(),Conkiri.getText(),Corneakanan.getText(),Corneakiri.getText(),COAkanan.getText(),COAkiri.getText(),Pupilkanan.getText(),Pupilkiri.getText(),
                    Lensakanan.getText(),Lensakiri.getText(),Funduskanan.getText(),Funduskiri.getText(),Papilkanan.getText(),Papilkiri.getText(),Retinakanan.getText(),Retinakiri.getText(),Makulakanan.getText(),Makulakiri.getText(),TIOkanan.getText(),TIOkiri.getText(),MBOkanan.getText(),MBOkiri.getText(),
                    Laborat.getText(),Radiologi.getText(),Penunjang.getText(),TesPenglihatan.getText(),PemeriksaanLain.getText(),
                    Diagnosis.getText(),DiagnosisBdg.getText(),Permasalahan.getText(),Terapi.getText(),Tindakan.getText(),Edukasi.getText(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
            })==true){
               tbObat.setValueAt(TNoRw.getText(),tbObat.getSelectedRow(),0);
               tbObat.setValueAt(TNoRM.getText(),tbObat.getSelectedRow(),1);
               tbObat.setValueAt(TPasien.getText(),tbObat.getSelectedRow(),2);
               tbObat.setValueAt(TglLahir.getText(),tbObat.getSelectedRow(),3);
               tbObat.setValueAt(Jk.getText(),tbObat.getSelectedRow(),4);
               tbObat.setValueAt(KdDokter.getText(),tbObat.getSelectedRow(),5);
               tbObat.setValueAt(NmDokter.getText(),tbObat.getSelectedRow(),6);
               tbObat.setValueAt(Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),tbObat.getSelectedRow(),7);
               tbObat.setValueAt(Anamnesis.getSelectedItem().toString(),tbObat.getSelectedRow(),8);
               tbObat.setValueAt(Hubungan.getText(),tbObat.getSelectedRow(),9);
               tbObat.setValueAt(KeluhanUtama.getText(),tbObat.getSelectedRow(),10);
               tbObat.setValueAt(RPS.getText(),tbObat.getSelectedRow(),11);
               tbObat.setValueAt(RPD.getText(),tbObat.getSelectedRow(),12);
               tbObat.setValueAt(RPO.getText(),tbObat.getSelectedRow(),13);
               tbObat.setValueAt(Alergi.getText(),tbObat.getSelectedRow(),14);
               tbObat.setValueAt(StatusNutrisi.getText(),tbObat.getSelectedRow(),15);
               tbObat.setValueAt(TD.getText(),tbObat.getSelectedRow(),16);
               tbObat.setValueAt(Nadi.getText(),tbObat.getSelectedRow(),17);
               tbObat.setValueAt(RR.getText(),tbObat.getSelectedRow(),18);
               tbObat.setValueAt(Suhu.getText(),tbObat.getSelectedRow(),19);
               tbObat.setValueAt(Nyeri.getText(),tbObat.getSelectedRow(),20);
               tbObat.setValueAt(BB.getText(),tbObat.getSelectedRow(),21);
               tbObat.setValueAt(Visuskanan.getText(),tbObat.getSelectedRow(),22);
               tbObat.setValueAt(Visuskiri.getText(),tbObat.getSelectedRow(),23);
               tbObat.setValueAt(CCkanan.getText(),tbObat.getSelectedRow(),24);
               tbObat.setValueAt(CCkiri.getText(),tbObat.getSelectedRow(),25);
               tbObat.setValueAt(Palkanan.getText(),tbObat.getSelectedRow(),26);
               tbObat.setValueAt(Palkiri.getText(),tbObat.getSelectedRow(),27);
               tbObat.setValueAt(Conkanan.getText(),tbObat.getSelectedRow(),28);
               tbObat.setValueAt(Conkiri.getText(),tbObat.getSelectedRow(),29);
               tbObat.setValueAt(Corneakanan.getText(),tbObat.getSelectedRow(),30);
               tbObat.setValueAt(Corneakiri.getText(),tbObat.getSelectedRow(),31);
               tbObat.setValueAt(COAkanan.getText(),tbObat.getSelectedRow(),32);
               tbObat.setValueAt(COAkiri.getText(),tbObat.getSelectedRow(),33);
               tbObat.setValueAt(Pupilkanan.getText(),tbObat.getSelectedRow(),34);
               tbObat.setValueAt(Pupilkiri.getText(),tbObat.getSelectedRow(),35);
               tbObat.setValueAt(Lensakanan.getText(),tbObat.getSelectedRow(),36);
               tbObat.setValueAt(Lensakiri.getText(),tbObat.getSelectedRow(),37);
               tbObat.setValueAt(Funduskanan.getText(),tbObat.getSelectedRow(),38);
               tbObat.setValueAt(Funduskiri.getText(),tbObat.getSelectedRow(),39);
               tbObat.setValueAt(Papilkanan.getText(),tbObat.getSelectedRow(),40);
               tbObat.setValueAt(Papilkiri.getText(),tbObat.getSelectedRow(),41);
               tbObat.setValueAt(Retinakanan.getText(),tbObat.getSelectedRow(),42);
               tbObat.setValueAt(Retinakiri.getText(),tbObat.getSelectedRow(),43);
               tbObat.setValueAt(Makulakanan.getText(),tbObat.getSelectedRow(),44);
               tbObat.setValueAt(Makulakiri.getText(),tbObat.getSelectedRow(),45);
               tbObat.setValueAt(TIOkanan.getText(),tbObat.getSelectedRow(),46);
               tbObat.setValueAt(TIOkiri.getText(),tbObat.getSelectedRow(),47);
               tbObat.setValueAt(MBOkanan.getText(),tbObat.getSelectedRow(),48);
               tbObat.setValueAt(MBOkiri.getText(),tbObat.getSelectedRow(),49);
               tbObat.setValueAt(Laborat.getText(),tbObat.getSelectedRow(),50);
               tbObat.setValueAt(Radiologi.getText(),tbObat.getSelectedRow(),51);
               tbObat.setValueAt(Penunjang.getText(),tbObat.getSelectedRow(),52);
               tbObat.setValueAt(TesPenglihatan.getText(),tbObat.getSelectedRow(),53);
               tbObat.setValueAt(PemeriksaanLain.getText(),tbObat.getSelectedRow(),54);
               tbObat.setValueAt(Diagnosis.getText(),tbObat.getSelectedRow(),55);
               tbObat.setValueAt(DiagnosisBdg.getText(),tbObat.getSelectedRow(),56);
               tbObat.setValueAt(Permasalahan.getText(),tbObat.getSelectedRow(),57);
               tbObat.setValueAt(Terapi.getText(),tbObat.getSelectedRow(),58);
               tbObat.setValueAt(Tindakan.getText(),tbObat.getSelectedRow(),59);
               tbObat.setValueAt(Edukasi.getText(),tbObat.getSelectedRow(),60);
               emptTeks();
               TabRawat.setSelectedIndex(1);
        }
    }

    private void simpan() {
        if(Sequel.menyimpantf("penilaian_medis_ralan_mata","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat",56,new String[]{
                TNoRw.getText(),Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),KdDokter.getText(),Anamnesis.getSelectedItem().toString(),Hubungan.getText(),
                KeluhanUtama.getText(),RPS.getText(),RPD.getText(),RPO.getText(),Alergi.getText(),StatusNutrisi.getText(),TD.getText(),Nadi.getText(),RR.getText(),Suhu.getText(),Nyeri.getText(),BB.getText(),
                Visuskanan.getText(),Visuskiri.getText(),CCkanan.getText(),CCkiri.getText(),Palkanan.getText(),Palkiri.getText(),Conkanan.getText(),Conkiri.getText(),Corneakanan.getText(),Corneakiri.getText(),COAkanan.getText(),COAkiri.getText(),Pupilkanan.getText(),Pupilkiri.getText(),
                Lensakanan.getText(),Lensakiri.getText(),Funduskanan.getText(),Funduskiri.getText(),Papilkanan.getText(),Papilkiri.getText(),Retinakanan.getText(),Retinakiri.getText(),Makulakanan.getText(),Makulakiri.getText(),TIOkanan.getText(),TIOkiri.getText(),MBOkanan.getText(),MBOkiri.getText(),
                Laborat.getText(),Radiologi.getText(),Penunjang.getText(),TesPenglihatan.getText(),PemeriksaanLain.getText(),Diagnosis.getText(),DiagnosisBdg.getText(),Permasalahan.getText(),Terapi.getText(),Tindakan.getText(),Edukasi.getText()
            })==true){
                tabMode.addRow(new Object[]{
                    TNoRw.getText(),TNoRM.getText(),TPasien.getText(),TglLahir.getText(),Jk.getText(),KdDokter.getText(),NmDokter.getText(),Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),
                    Anamnesis.getSelectedItem().toString(),Hubungan.getText(),KeluhanUtama.getText(),RPS.getText(),RPD.getText(),RPO.getText(),Alergi.getText(),StatusNutrisi.getText(),TD.getText(),Nadi.getText(),RR.getText(),Suhu.getText(),Nyeri.getText(),BB.getText(),Visuskanan.getText(),Visuskiri.getText(),
                    CCkanan.getText(),CCkiri.getText(),Palkanan.getText(),Palkiri.getText(),Conkanan.getText(),Conkiri.getText(),Corneakanan.getText(),Corneakiri.getText(),COAkanan.getText(),COAkiri.getText(),Pupilkanan.getText(),Pupilkiri.getText(),Lensakanan.getText(),Lensakiri.getText(),Funduskanan.getText(),
                    Funduskiri.getText(),Papilkanan.getText(),Papilkiri.getText(),Retinakanan.getText(),Retinakiri.getText(),Makulakanan.getText(),Makulakiri.getText(),TIOkanan.getText(),TIOkiri.getText(),MBOkanan.getText(),MBOkiri.getText(),Laborat.getText(),Radiologi.getText(),Penunjang.getText(),TesPenglihatan.getText(),
                    PemeriksaanLain.getText(),Diagnosis.getText(),DiagnosisBdg.getText(),Permasalahan.getText(),Terapi.getText(),Tindakan.getText(),Edukasi.getText()
                });
                LCount.setText(""+tabMode.getRowCount());
                emptTeks();
        }
    }
}

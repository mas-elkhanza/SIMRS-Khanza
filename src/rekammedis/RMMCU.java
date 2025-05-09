//modifikasi dari source Mas Ikhsan, RS Karina Medika Purwakarta
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
public final class RMMCU extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;
    private DlgCariDokter dokter=new DlgCariDokter(null,false);
    private String TANGGALMUNDUR="yes";
    
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public RMMCU(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.RM","Nama Pasien","J.K.","Tgl.Lahir","Tanggal","Informasi","Riwayat Peyakit Sekarang","Riwayat Penyakit Keluarga", 
            "Riwayat Penyakit Dahulu","Alergi Makan & Obat","Keadaan Umum","Kesadaran","T.D.", "Nadi", "R.R.","T.B.","B.B.","Suhu","B.M.I.",
            "Klasifikasi B.M.I.","L.P.","Risiko Berdasar L.P.","Submandibula","Axilla","Supraklavikula","Leher","Inguinal","Oedema","Frontalis",
            "Maxilaris","Rambut","Palpebra","Sklera","Cornea","Buta Warna","Konjungtiva","Lensa","Pupil","Kacamata","Visus",
            "Lapang Pandang","Keterangan Luas Lapang Pandang","Lubang Telinga","Daun Telinga","Selaput Pendengaran","Proc.Mastoideus",
            "Septum Nasi","Lubang Hidung","Sinus","Bibir","Gusi","Gigi","Caries","Lidah","Faring","Tonsil","Kelenjar Limfe","Kelenjar Gondok",
            "Gerakan Dada","Vocal Fremitus","Perkusi Dada","Bunyi Napas","Bunyi Tambahan","Ictus Cordis","Bunyi Jantung","Batas","Mamae",
            "Keterangan Mamae","Inspeksi","Palpasi","Hepar","Perkusi Abdomen","Auskultasi","Limpa","Costovertebral","Scoliosis","Kondisi Kulit",
            "Penyakit Kulit","Extrimitas Atas","Keterangan Extrimitas Atas","Extrimitas Bawah","Keterangan Extrimitas Bawah","Area Genitalia",
            "Keterangan Area Genitalia","Anus & Perianal","Keterangan Anus & Perianal","Pemeriksaan Laboratorium", "Rontgen Thorax","EKG",
            "Spirometri","Audiometri","Treadmill","Romberg Test","Back Strength","ABI Tangan Kanan","ABI Tangan Kiri","ABI Kaki Kanan","ABI Kaki Kiri",
            "Lain-lain", "Merokok", "Alkohol","Kesimpulan", "Anjuran","Kode Dokter","Nama Dokter Penanggung Jawab"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 106; i++) {
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
                column.setPreferredWidth(120);
            }else if(i==6){
                column.setPreferredWidth(80);
            }else if(i==7){
                column.setPreferredWidth(200);
            }else if(i==8){
                column.setPreferredWidth(200);
            }else if(i==9){
                column.setPreferredWidth(200);
            }else if(i==10){
                column.setPreferredWidth(150);
            }else if(i==11){
                column.setPreferredWidth(85);
            }else if(i==12){
                column.setPreferredWidth(77);
            }else if(i==13){
                column.setPreferredWidth(45);
            }else if(i==14){
                column.setPreferredWidth(30);
            }else if(i==15){
                column.setPreferredWidth(30);
            }else if(i==16){
                column.setPreferredWidth(30);
            }else if(i==17){
                column.setPreferredWidth(30);
            }else if(i==18){
                column.setPreferredWidth(35);
            }else if(i==19){
                column.setPreferredWidth(45);
            }else if(i==20){
                column.setPreferredWidth(120);
            }else if(i==21){
                column.setPreferredWidth(30);
            }else if(i==22){
                column.setPreferredWidth(120);
            }else if(i==23){
                column.setPreferredWidth(90);
            }else if(i==24){
                column.setPreferredWidth(90);
            }else if(i==25){
                column.setPreferredWidth(90);
            }else if(i==26){
                column.setPreferredWidth(90);
            }else if(i==27){
                column.setPreferredWidth(90);
            }else if(i==28){
                column.setPreferredWidth(60);
            }else if(i==29){
                column.setPreferredWidth(60);
            }else if(i==30){
                column.setPreferredWidth(60);
            }else if(i==31){
                column.setPreferredWidth(160);
            }else if(i==32){
                column.setPreferredWidth(70);
            }else if(i==33){
                column.setPreferredWidth(70);
            }else if(i==34){
                column.setPreferredWidth(70);
            }else if(i==35){
                column.setPreferredWidth(100);
            }else if(i==36){
                column.setPreferredWidth(68);
            }else if(i==37){
                column.setPreferredWidth(60);
            }else if(i==38){
                column.setPreferredWidth(55);
            }else if(i==39){
                column.setPreferredWidth(60);
            }else if(i==40){
                column.setPreferredWidth(150);
            }else if(i==41){
                column.setPreferredWidth(90);
            }else if(i==42){
                column.setPreferredWidth(172);
            }else if(i==43){
                column.setPreferredWidth(82);
            }else if(i==44){
                column.setPreferredWidth(75);
            }else if(i==45){
                column.setPreferredWidth(112);
            }else if(i==46){
                column.setPreferredWidth(86);
            }else if(i==47){
                column.setPreferredWidth(70);
            }else if(i==48){
                column.setPreferredWidth(80);
            }else if(i==49){
                column.setPreferredWidth(70);
            }else if(i==50){
                column.setPreferredWidth(48);
            }else if(i==51){
                column.setPreferredWidth(70);
            }else if(i==52){
                column.setPreferredWidth(70);
            }else if(i==53){
                column.setPreferredWidth(55);
            }else if(i==54){
                column.setPreferredWidth(46);
            }else if(i==55){
                column.setPreferredWidth(58);
            }else if(i==56){
                column.setPreferredWidth(42);
            }else if(i==57){
                column.setPreferredWidth(90);
            }else if(i==58){
                column.setPreferredWidth(90);
            }else if(i==59){
                column.setPreferredWidth(75);
            }else if(i==60){
                column.setPreferredWidth(85);
            }else if(i==61){
                column.setPreferredWidth(70);
            }else if(i==62){
                column.setPreferredWidth(68);
            }else if(i==63){
                column.setPreferredWidth(90);
            }else if(i==64){
                column.setPreferredWidth(75);
            }else if(i==65){
                column.setPreferredWidth(78);
            }else if(i==66){
                column.setPreferredWidth(48);
            }else if(i==67){
                column.setPreferredWidth(70);
            }else if(i==68){
                column.setPreferredWidth(150);
            }else if(i==69){
                column.setPreferredWidth(53);
            }else if(i==70){
                column.setPreferredWidth(172);
            }else if(i==71){
                column.setPreferredWidth(87);
            }else if(i==72){
                column.setPreferredWidth(93);
            }else if(i==73){
                column.setPreferredWidth(112);
            }else if(i==74){
                column.setPreferredWidth(85);
            }else if(i==75){
                column.setPreferredWidth(80);
            }else if(i==76){
                column.setPreferredWidth(55);
            }else if(i==77){
                column.setPreferredWidth(73);
            }else if(i==78){
                column.setPreferredWidth(150);
            }else if(i==79){
                column.setPreferredWidth(83);
            }else if(i==80){
                column.setPreferredWidth(142);
            }else if(i==81){
                column.setPreferredWidth(92);
            }else if(i==82){
                column.setPreferredWidth(151);
            }else if(i==83){
                column.setPreferredWidth(98);
            }else if(i==84){
                column.setPreferredWidth(151);
            }else if(i==85){
                column.setPreferredWidth(87);
            }else if(i==86){
                column.setPreferredWidth(151);
            }else if(i==87){
                column.setPreferredWidth(200);
            }else if(i==88){
                column.setPreferredWidth(200);
            }else if(i==89){
                column.setPreferredWidth(200);
            }else if(i==90){
                column.setPreferredWidth(200);
            }else if(i==91){
                column.setPreferredWidth(200);
            }else if(i==92){
                column.setPreferredWidth(200);
            }else if(i==93){
                column.setPreferredWidth(200);
            }else if(i==94){
                column.setPreferredWidth(200);
            }else if(i==95){
                column.setPreferredWidth(180);
            }else if(i==96){
                column.setPreferredWidth(180);
            }else if(i==97){
                column.setPreferredWidth(180);
            }else if(i==98){
                column.setPreferredWidth(180);
            }else if(i==99){
                column.setPreferredWidth(200);
            }else if(i==100){
                column.setPreferredWidth(120);
            }else if(i==101){
                column.setPreferredWidth(120);
            }else if(i==102){
                column.setPreferredWidth(200);
            }else if(i==103){
                column.setPreferredWidth(200);
            }else if(i==104){
                column.setPreferredWidth(90);
            }else if(i==105){
                column.setPreferredWidth(170);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());
        
        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        RiwayatPenyakitSekarang.setDocument(new batasInput((int)2000).getKata(RiwayatPenyakitSekarang));
        RiwayatPenyakitKeluarga.setDocument(new batasInput((int)1000).getKata(RiwayatPenyakitKeluarga));
        RiwayatPenyakitDahulu.setDocument(new batasInput((int)1000).getKata(RiwayatPenyakitDahulu));
        RiwayatAlergiMakanan.setDocument(new batasInput((int)150).getKata(RiwayatAlergiMakanan));
        TD.setDocument(new batasInput((byte)8).getKata(TD));
        Nadi.setDocument(new batasInput((byte)5).getKata(Nadi));
        RR.setDocument(new batasInput((byte)5).getKata(RR));
        TB.setDocument(new batasInput((byte)5).getKata(TB));
        BB.setDocument(new batasInput((byte)5).getKata(BB));
        Suhu.setDocument(new batasInput((byte)5).getKata(Suhu));
        IMT.setDocument(new batasInput((byte)6).getKata(IMT));
        LP.setDocument(new batasInput((byte)6).getKata(LP));
        Rambut.setDocument(new batasInput((int)100).getKata(Rambut));
        Visus.setDocument(new batasInput((int)50).getKata(Visus));
        KeteranganLuasLapangPandang.setDocument(new batasInput((int)50).getKata(KeteranganLuasLapangPandang));
        KeteranganMamae.setDocument(new batasInput((int)100).getKata(KeteranganMamae));
        PenyakitKulit.setDocument(new batasInput((int)100).getKata(PenyakitKulit));
        KeteranganAreaGenitalia.setDocument(new batasInput((int)100).getKata(KeteranganAreaGenitalia));
        KeteranganAnus.setDocument(new batasInput((int)100).getKata(KeteranganAnus));
        KetExtremitasAtas.setDocument(new batasInput((byte)50).getKata(KetExtremitasAtas));
        KetExtremitasBawah.setDocument(new batasInput((byte)50).getKata(KetExtremitasBawah));
        PemeriksaanLaboratorium.setDocument(new batasInput((int)1000).getKata(PemeriksaanLaboratorium));
        RongsenThorax.setDocument(new batasInput((int)1000).getKata(RongsenThorax));
        EKG.setDocument(new batasInput((int)1000).getKata(EKG));
        Spirometri.setDocument(new batasInput((int)1000).getKata(Spirometri));
        Audiometri.setDocument(new batasInput((int)1000).getKata(Audiometri));
        Treadmill.setDocument(new batasInput((int)1000).getKata(Treadmill));
        Romberg.setDocument(new batasInput((int)1000).getKata(Romberg));
        Backstrength.setDocument(new batasInput((int)1000).getKata(Backstrength));
        AbiTanganKanan.setDocument(new batasInput((int)1000).getKata(AbiTanganKanan));
        AbiTanganKiri.setDocument(new batasInput((int)1000).getKata(AbiTanganKiri));
        AbiKakiKanan.setDocument(new batasInput((int)1000).getKata(AbiKakiKanan));
        AbiKakiKiri.setDocument(new batasInput((int)1000).getKata(AbiKakiKiri));
        Lainlain.setDocument(new batasInput((int)1000).getKata(Lainlain));
        Merokok.setDocument(new batasInput((int)100).getKata(Merokok));
        Alkohol.setDocument(new batasInput((int)100).getKata(Alkohol));
        Kesimpulan.setDocument(new batasInput((int)1000).getKata(Kesimpulan));
        Anjuran.setDocument(new batasInput((int)1000).getKata(Anjuran));
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
        
        IMT.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {
                isLP();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                isLP();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                isLP();
            }
        });
        
        LP.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {
                isLP();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                isLP();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                isLP();
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
        MnPenilaianMCU = new javax.swing.JMenuItem();
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
        Jk = new widget.TextBox();
        jLabel10 = new widget.Label();
        label11 = new widget.Label();
        jLabel11 = new widget.Label();
        jLabel12 = new widget.Label();
        BB = new widget.TextBox();
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
        jLabel25 = new widget.Label();
        RR = new widget.TextBox();
        jLabel26 = new widget.Label();
        jLabel36 = new widget.Label();
        Informasi = new widget.ComboBox();
        jLabel53 = new widget.Label();
        TglAsuhan = new widget.Tanggal();
        jLabel28 = new widget.Label();
        TB = new widget.TextBox();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel9 = new widget.Label();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel54 = new widget.Label();
        jLabel33 = new widget.Label();
        jLabel34 = new widget.Label();
        jLabel35 = new widget.Label();
        jLabel37 = new widget.Label();
        jLabel39 = new widget.Label();
        jLabel55 = new widget.Label();
        jLabel50 = new widget.Label();
        jLabel56 = new widget.Label();
        jLabel51 = new widget.Label();
        RiwayatAlergiMakanan = new widget.TextBox();
        jLabel57 = new widget.Label();
        KeadaanUmum = new widget.ComboBox();
        jLabel52 = new widget.Label();
        jLabel41 = new widget.Label();
        jLabel47 = new widget.Label();
        jLabel48 = new widget.Label();
        jLabel49 = new widget.Label();
        jLabel58 = new widget.Label();
        jLabel43 = new widget.Label();
        jLabel59 = new widget.Label();
        jLabel60 = new widget.Label();
        scrollPane10 = new widget.ScrollPane();
        PemeriksaanLaboratorium = new widget.TextArea();
        jLabel61 = new widget.Label();
        jLabel62 = new widget.Label();
        scrollPane11 = new widget.ScrollPane();
        RongsenThorax = new widget.TextArea();
        scrollPane12 = new widget.ScrollPane();
        EKG = new widget.TextArea();
        jLabel101 = new widget.Label();
        scrollPane14 = new widget.ScrollPane();
        Kesimpulan = new widget.TextArea();
        jLabel102 = new widget.Label();
        scrollPane15 = new widget.ScrollPane();
        Anjuran = new widget.TextArea();
        jLabel103 = new widget.Label();
        jLabel104 = new widget.Label();
        jLabel45 = new widget.Label();
        Submandibula = new widget.ComboBox();
        Kesadaran = new widget.ComboBox();
        Leher = new widget.ComboBox();
        Axila = new widget.ComboBox();
        Inguinal = new widget.ComboBox();
        Supraklavikula = new widget.ComboBox();
        Oedema = new widget.ComboBox();
        NyeriTekanSinusFrontalis = new widget.ComboBox();
        NyeriTekananSinusMaxilaris = new widget.ComboBox();
        Palpebra = new widget.ComboBox();
        Sklera = new widget.ComboBox();
        TestButaWarna = new widget.ComboBox();
        Konjungtiva = new widget.ComboBox();
        Lensa = new widget.ComboBox();
        Cornea = new widget.ComboBox();
        Pupil = new widget.ComboBox();
        LubangTelinga = new widget.ComboBox();
        jLabel105 = new widget.Label();
        jLabel63 = new widget.Label();
        DaunTelinga = new widget.ComboBox();
        jLabel64 = new widget.Label();
        SelaputPendengaran = new widget.ComboBox();
        jLabel65 = new widget.Label();
        NyeriMastoideus = new widget.ComboBox();
        jLabel46 = new widget.Label();
        SeptumNasi = new widget.ComboBox();
        jLabel31 = new widget.Label();
        jLabel67 = new widget.Label();
        LubangHidung = new widget.ComboBox();
        jLabel68 = new widget.Label();
        jLabel29 = new widget.Label();
        jLabel69 = new widget.Label();
        jLabel71 = new widget.Label();
        jLabel72 = new widget.Label();
        jLabel73 = new widget.Label();
        Bibir = new widget.ComboBox();
        Caries = new widget.ComboBox();
        Lidah = new widget.ComboBox();
        Faring = new widget.ComboBox();
        Tonsil = new widget.ComboBox();
        jLabel74 = new widget.Label();
        jLabel75 = new widget.Label();
        jLabel76 = new widget.Label();
        KelenjarLimfe = new widget.ComboBox();
        KelenjarGondok = new widget.ComboBox();
        jLabel77 = new widget.Label();
        jLabel78 = new widget.Label();
        jLabel66 = new widget.Label();
        jLabel79 = new widget.Label();
        jLabel80 = new widget.Label();
        jLabel81 = new widget.Label();
        jLabel82 = new widget.Label();
        jLabel83 = new widget.Label();
        GerakanDada = new widget.ComboBox();
        BunyiNapas = new widget.ComboBox();
        VocalFremitus = new widget.ComboBox();
        BunyiTambahan = new widget.ComboBox();
        PerkusiDada = new widget.ComboBox();
        jLabel44 = new widget.Label();
        jLabel70 = new widget.Label();
        jLabel85 = new widget.Label();
        jLabel38 = new widget.Label();
        jLabel86 = new widget.Label();
        jLabel87 = new widget.Label();
        jLabel88 = new widget.Label();
        jLabel91 = new widget.Label();
        jLabel92 = new widget.Label();
        jLabel93 = new widget.Label();
        jLabel95 = new widget.Label();
        jLabel96 = new widget.Label();
        jLabel98 = new widget.Label();
        jLabel100 = new widget.Label();
        IctusCordis = new widget.ComboBox();
        BunyiJantung = new widget.ComboBox();
        Batas = new widget.ComboBox();
        Inspeksi = new widget.ComboBox();
        Palpasi = new widget.ComboBox();
        PerkusiAbdomen = new widget.ComboBox();
        Auskultasi = new widget.ComboBox();
        Hepar = new widget.ComboBox();
        Limpa = new widget.ComboBox();
        NyeriKtok = new widget.ComboBox();
        jLabel84 = new widget.Label();
        ExtremitasAtas = new widget.ComboBox();
        ExtremitasBawah = new widget.ComboBox();
        jLabel90 = new widget.Label();
        KetExtremitasAtas = new widget.TextBox();
        KetExtremitasBawah = new widget.TextBox();
        Kulit = new widget.ComboBox();
        jLabel99 = new widget.Label();
        jLabel89 = new widget.Label();
        scrollPane2 = new widget.ScrollPane();
        RiwayatPenyakitSekarang = new widget.TextArea();
        scrollPane3 = new widget.ScrollPane();
        RiwayatPenyakitKeluarga = new widget.TextArea();
        jLabel94 = new widget.Label();
        scrollPane4 = new widget.ScrollPane();
        RiwayatPenyakitDahulu = new widget.TextArea();
        jLabel24 = new widget.Label();
        jLabel97 = new widget.Label();
        jLabel13 = new widget.Label();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        jSeparator6 = new javax.swing.JSeparator();
        Merokok = new widget.TextBox();
        Alkohol = new widget.TextBox();
        jSeparator7 = new javax.swing.JSeparator();
        jLabel32 = new widget.Label();
        jLabel30 = new widget.Label();
        jSeparator8 = new javax.swing.JSeparator();
        jSeparator9 = new javax.swing.JSeparator();
        jSeparator10 = new javax.swing.JSeparator();
        jSeparator11 = new javax.swing.JSeparator();
        jSeparator12 = new javax.swing.JSeparator();
        jSeparator13 = new javax.swing.JSeparator();
        jSeparator14 = new javax.swing.JSeparator();
        jLabel106 = new widget.Label();
        jSeparator15 = new javax.swing.JSeparator();
        jLabel107 = new widget.Label();
        scrollPane13 = new widget.ScrollPane();
        Spirometri = new widget.TextArea();
        jSeparator16 = new javax.swing.JSeparator();
        jLabel108 = new widget.Label();
        scrollPane16 = new widget.ScrollPane();
        Audiometri = new widget.TextArea();
        jSeparator17 = new javax.swing.JSeparator();
        jLabel109 = new widget.Label();
        scrollPane17 = new widget.ScrollPane();
        Treadmill = new widget.TextArea();
        jSeparator18 = new javax.swing.JSeparator();
        jLabel110 = new widget.Label();
        scrollPane18 = new widget.ScrollPane();
        Lainlain = new widget.TextArea();
        jLabel111 = new widget.Label();
        Sinus = new widget.ComboBox();
        BtnDokter3 = new widget.Button();
        BtnDokter2 = new widget.Button();
        jLabel27 = new widget.Label();
        IMT = new widget.TextBox();
        jLabel40 = new widget.Label();
        KlasifikasiIMT = new widget.TextBox();
        jLabel42 = new widget.Label();
        jLabel112 = new widget.Label();
        jLabel14 = new widget.Label();
        Scoliosis = new widget.ComboBox();
        jLabel114 = new widget.Label();
        jLabel115 = new widget.Label();
        LP = new widget.TextBox();
        jLabel116 = new widget.Label();
        RisikoLP = new widget.TextBox();
        jLabel117 = new widget.Label();
        jLabel118 = new widget.Label();
        Rambut = new widget.TextBox();
        jLabel113 = new widget.Label();
        jLabel119 = new widget.Label();
        MenggunakanKacamata = new widget.ComboBox();
        jLabel120 = new widget.Label();
        Visus = new widget.TextBox();
        jLabel122 = new widget.Label();
        jLabel123 = new widget.Label();
        jLabel124 = new widget.Label();
        jLabel125 = new widget.Label();
        jLabel126 = new widget.Label();
        jLabel127 = new widget.Label();
        jLabel121 = new widget.Label();
        jLabel128 = new widget.Label();
        jLabel129 = new widget.Label();
        jLabel130 = new widget.Label();
        jLabel131 = new widget.Label();
        jLabel132 = new widget.Label();
        jLabel133 = new widget.Label();
        Gusi = new widget.ComboBox();
        Gigi = new widget.ComboBox();
        jLabel134 = new widget.Label();
        jLabel135 = new widget.Label();
        jLabel136 = new widget.Label();
        jLabel137 = new widget.Label();
        KeteranganLuasLapangPandang = new widget.TextBox();
        LuasLapangPandang = new widget.ComboBox();
        jLabel138 = new widget.Label();
        jLabel139 = new widget.Label();
        jLabel140 = new widget.Label();
        jLabel141 = new widget.Label();
        jLabel142 = new widget.Label();
        Mamae = new widget.ComboBox();
        KeteranganMamae = new widget.TextBox();
        jLabel143 = new widget.Label();
        jLabel144 = new widget.Label();
        jSeparator19 = new javax.swing.JSeparator();
        jLabel145 = new widget.Label();
        jLabel146 = new widget.Label();
        PenyakitKulit = new widget.TextBox();
        jLabel147 = new widget.Label();
        jLabel148 = new widget.Label();
        jSeparator20 = new javax.swing.JSeparator();
        jLabel150 = new widget.Label();
        AreaGenitalia = new widget.ComboBox();
        KeteranganAreaGenitalia = new widget.TextBox();
        jLabel149 = new widget.Label();
        jSeparator21 = new javax.swing.JSeparator();
        jLabel152 = new widget.Label();
        Anus = new widget.ComboBox();
        jLabel151 = new widget.Label();
        KeteranganAnus = new widget.TextBox();
        jSeparator22 = new javax.swing.JSeparator();
        jLabel153 = new widget.Label();
        scrollPane19 = new widget.ScrollPane();
        Romberg = new widget.TextArea();
        jSeparator23 = new javax.swing.JSeparator();
        jLabel154 = new widget.Label();
        scrollPane20 = new widget.ScrollPane();
        Backstrength = new widget.TextArea();
        jSeparator24 = new javax.swing.JSeparator();
        jLabel155 = new widget.Label();
        jLabel156 = new widget.Label();
        scrollPane21 = new widget.ScrollPane();
        AbiTanganKanan = new widget.TextArea();
        jLabel157 = new widget.Label();
        scrollPane22 = new widget.ScrollPane();
        AbiTanganKiri = new widget.TextArea();
        jLabel158 = new widget.Label();
        scrollPane23 = new widget.ScrollPane();
        AbiKakiKanan = new widget.TextArea();
        jLabel159 = new widget.Label();
        scrollPane24 = new widget.ScrollPane();
        AbiKakiKiri = new widget.TextArea();
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

        MnPenilaianMCU.setBackground(new java.awt.Color(255, 255, 254));
        MnPenilaianMCU.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPenilaianMCU.setForeground(new java.awt.Color(50, 50, 50));
        MnPenilaianMCU.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPenilaianMCU.setText("Laporan Penilaian MCU");
        MnPenilaianMCU.setName("MnPenilaianMCU"); // NOI18N
        MnPenilaianMCU.setPreferredSize(new java.awt.Dimension(180, 26));
        MnPenilaianMCU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPenilaianMCUActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnPenilaianMCU);

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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Penilaian Medical Check Up (MCU) ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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
        FormInput.setPreferredSize(new java.awt.Dimension(870, 2413));
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

        label14.setText("Dokter P.J. :");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label14);
        label14.setBounds(0, 40, 70, 23);

        KdDokter.setEditable(false);
        KdDokter.setName("KdDokter"); // NOI18N
        KdDokter.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput.add(KdDokter);
        KdDokter.setBounds(74, 40, 100, 23);

        NmDokter.setEditable(false);
        NmDokter.setName("NmDokter"); // NOI18N
        NmDokter.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmDokter);
        NmDokter.setBounds(176, 40, 180, 23);

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

        jLabel12.setText("Berat Badan :");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(407, 250, 80, 23);

        BB.setFocusTraversalPolicyProvider(true);
        BB.setName("BB"); // NOI18N
        BB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BBKeyPressed(evt);
            }
        });
        FormInput.add(BB);
        BB.setBounds(491, 250, 50, 23);

        jLabel15.setText("Kesadaran :");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(272, 220, 80, 23);

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel16.setText("x/menit");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput.add(jLabel16);
        jLabel16.setBounds(817, 220, 50, 23);

        Nadi.setFocusTraversalPolicyProvider(true);
        Nadi.setName("Nadi"); // NOI18N
        Nadi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NadiKeyPressed(evt);
            }
        });
        FormInput.add(Nadi);
        Nadi.setBounds(764, 220, 50, 23);

        jLabel17.setText("HR/Nadi :");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(700, 220, 60, 23);

        jLabel18.setText("Suhu :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(588, 250, 40, 23);

        Suhu.setFocusTraversalPolicyProvider(true);
        Suhu.setName("Suhu"); // NOI18N
        Suhu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SuhuKeyPressed(evt);
            }
        });
        FormInput.add(Suhu);
        Suhu.setBounds(632, 250, 50, 23);

        jLabel22.setText("TD :");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(532, 220, 30, 23);

        TD.setFocusTraversalPolicyProvider(true);
        TD.setName("TD"); // NOI18N
        TD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TDKeyPressed(evt);
            }
        });
        FormInput.add(TD);
        TD.setBounds(566, 220, 65, 23);

        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel20.setText("°C");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(685, 250, 30, 23);

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel23.setText("mmHg");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(634, 220, 50, 23);

        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel25.setText("x/menit");
        jLabel25.setName("jLabel25"); // NOI18N
        FormInput.add(jLabel25);
        jLabel25.setBounds(154, 250, 50, 23);

        RR.setFocusTraversalPolicyProvider(true);
        RR.setName("RR"); // NOI18N
        RR.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RRKeyPressed(evt);
            }
        });
        FormInput.add(RR);
        RR.setBounds(101, 250, 50, 23);

        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel26.setText("RR/Nafas");
        jLabel26.setName("jLabel26"); // NOI18N
        FormInput.add(jLabel26);
        jLabel26.setBounds(44, 250, 70, 23);

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
        jLabel53.setText("A. ANAMNESA SINGKAT");
        jLabel53.setName("jLabel53"); // NOI18N
        FormInput.add(jLabel53);
        jLabel53.setBounds(10, 70, 180, 23);

        TglAsuhan.setForeground(new java.awt.Color(50, 70, 50));
        TglAsuhan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "07-05-2025 17:42:34" }));
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

        jLabel28.setText("Tinggi Badan :");
        jLabel28.setName("jLabel28"); // NOI18N
        FormInput.add(jLabel28);
        jLabel28.setBounds(225, 250, 80, 23);

        TB.setFocusTraversalPolicyProvider(true);
        TB.setName("TB"); // NOI18N
        TB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TBKeyPressed(evt);
            }
        });
        FormInput.add(TB);
        TB.setBounds(309, 250, 50, 23);

        jSeparator1.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator1.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator1.setName("jSeparator1"); // NOI18N
        FormInput.add(jSeparator1);
        jSeparator1.setBounds(0, 70, 880, 1);

        jLabel9.setText("Sklera :");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(251, 510, 57, 23);

        jSeparator2.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator2.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator2.setName("jSeparator2"); // NOI18N
        FormInput.add(jSeparator2);
        jSeparator2.setBounds(0, 200, 880, 1);

        jLabel54.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel54.setText("B. PEMERIKSAAN FISIK");
        jLabel54.setName("jLabel54"); // NOI18N
        FormInput.add(jLabel54);
        jLabel54.setBounds(10, 200, 180, 23);

        jLabel33.setText("Supraklavikula :");
        jLabel33.setName("jLabel33"); // NOI18N
        FormInput.add(jLabel33);
        jLabel33.setBounds(630, 330, 90, 23);

        jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel34.setText("Kelenjar Limfe :");
        jLabel34.setName("jLabel34"); // NOI18N
        FormInput.add(jLabel34);
        jLabel34.setBounds(44, 310, 121, 23);

        jLabel35.setText("Axilla :");
        jLabel35.setName("jLabel35"); // NOI18N
        FormInput.add(jLabel35);
        jLabel35.setBounds(356, 330, 60, 23);

        jLabel37.setText("Inguinal :");
        jLabel37.setName("jLabel37"); // NOI18N
        FormInput.add(jLabel37);
        jLabel37.setBounds(356, 360, 60, 23);

        jLabel39.setText("Nyeri Tekan Sinus Frontalis :");
        jLabel39.setName("jLabel39"); // NOI18N
        FormInput.add(jLabel39);
        jLabel39.setBounds(264, 430, 170, 23);

        jLabel55.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel55.setText("Riwayat Penyakit Sekarang :");
        jLabel55.setName("jLabel55"); // NOI18N
        FormInput.add(jLabel55);
        jLabel55.setBounds(44, 90, 182, 23);

        jLabel50.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel50.setText("Riwayat Penyakit Keluarga :");
        jLabel50.setName("jLabel50"); // NOI18N
        FormInput.add(jLabel50);
        jLabel50.setBounds(319, 90, 160, 23);

        jLabel56.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel56.setText("Riwayat Penyakit Dahulu :");
        jLabel56.setName("jLabel56"); // NOI18N
        FormInput.add(jLabel56);
        jLabel56.setBounds(594, 90, 150, 23);

        jLabel51.setText(":");
        jLabel51.setName("jLabel51"); // NOI18N
        FormInput.add(jLabel51);
        jLabel51.setBounds(0, 170, 205, 23);

        RiwayatAlergiMakanan.setFocusTraversalPolicyProvider(true);
        RiwayatAlergiMakanan.setName("RiwayatAlergiMakanan"); // NOI18N
        RiwayatAlergiMakanan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RiwayatAlergiMakananKeyPressed(evt);
            }
        });
        FormInput.add(RiwayatAlergiMakanan);
        RiwayatAlergiMakanan.setBounds(209, 170, 645, 23);

        jLabel57.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel57.setText("Keadaan Umum");
        jLabel57.setName("jLabel57"); // NOI18N
        FormInput.add(jLabel57);
        jLabel57.setBounds(44, 220, 127, 23);

        KeadaanUmum.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Baik", "Tidak Baik" }));
        KeadaanUmum.setName("KeadaanUmum"); // NOI18N
        KeadaanUmum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeadaanUmumKeyPressed(evt);
            }
        });
        FormInput.add(KeadaanUmum);
        KeadaanUmum.setBounds(131, 220, 100, 23);

        jLabel52.setText("Nyeri Tekanan Sinus Maxilaris :");
        jLabel52.setName("jLabel52"); // NOI18N
        FormInput.add(jLabel52);
        jLabel52.setBounds(575, 430, 170, 23);

        jLabel41.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel41.setText("2. Mata");
        jLabel41.setName("jLabel41"); // NOI18N
        FormInput.add(jLabel41);
        jLabel41.setBounds(64, 490, 150, 23);

        jLabel47.setText("Cornea :");
        jLabel47.setName("jLabel47"); // NOI18N
        FormInput.add(jLabel47);
        jLabel47.setBounds(437, 510, 50, 23);

        jLabel48.setText("Lensa :");
        jLabel48.setName("jLabel48"); // NOI18N
        FormInput.add(jLabel48);
        jLabel48.setBounds(268, 540, 57, 23);

        jLabel49.setText("Pupil :");
        jLabel49.setName("jLabel49"); // NOI18N
        FormInput.add(jLabel49);
        jLabel49.setBounds(448, 540, 50, 23);

        jLabel58.setText("Buta Warna :");
        jLabel58.setName("jLabel58"); // NOI18N
        FormInput.add(jLabel58);
        jLabel58.setBounds(625, 510, 80, 23);

        jLabel43.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel43.setText("Kulit :");
        jLabel43.setName("jLabel43"); // NOI18N
        FormInput.add(jLabel43);
        jLabel43.setBounds(44, 1140, 60, 23);

        jLabel59.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel59.setText("C. PEMERIKSAAN LABORATORIUM (TERLAMPIR)");
        jLabel59.setName("jLabel59"); // NOI18N
        FormInput.add(jLabel59);
        jLabel59.setBounds(10, 1320, 280, 23);

        jLabel60.setText("Proc. Mastoideus :");
        jLabel60.setName("jLabel60"); // NOI18N
        FormInput.add(jLabel60);
        jLabel60.setBounds(636, 620, 100, 23);

        scrollPane10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane10.setName("scrollPane10"); // NOI18N

        PemeriksaanLaboratorium.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        PemeriksaanLaboratorium.setColumns(20);
        PemeriksaanLaboratorium.setRows(5);
        PemeriksaanLaboratorium.setName("PemeriksaanLaboratorium"); // NOI18N
        PemeriksaanLaboratorium.setPreferredSize(new java.awt.Dimension(182, 52));
        PemeriksaanLaboratorium.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PemeriksaanLaboratoriumKeyPressed(evt);
            }
        });
        scrollPane10.setViewportView(PemeriksaanLaboratorium);

        FormInput.add(scrollPane10);
        scrollPane10.setBounds(44, 1340, 810, 53);

        jLabel61.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel61.setText("E. EKG");
        jLabel61.setName("jLabel61"); // NOI18N
        FormInput.add(jLabel61);
        jLabel61.setBounds(10, 1480, 182, 23);

        jLabel62.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel62.setText("D. RONTGEN THORAX");
        jLabel62.setName("jLabel62"); // NOI18N
        FormInput.add(jLabel62);
        jLabel62.setBounds(10, 1400, 182, 23);

        scrollPane11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane11.setName("scrollPane11"); // NOI18N

        RongsenThorax.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        RongsenThorax.setColumns(20);
        RongsenThorax.setRows(5);
        RongsenThorax.setName("RongsenThorax"); // NOI18N
        RongsenThorax.setPreferredSize(new java.awt.Dimension(182, 52));
        RongsenThorax.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RongsenThoraxKeyPressed(evt);
            }
        });
        scrollPane11.setViewportView(RongsenThorax);

        FormInput.add(scrollPane11);
        scrollPane11.setBounds(44, 1420, 810, 53);

        scrollPane12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane12.setName("scrollPane12"); // NOI18N
        scrollPane12.setPreferredSize(new java.awt.Dimension(199, 52));

        EKG.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        EKG.setColumns(20);
        EKG.setRows(5);
        EKG.setName("EKG"); // NOI18N
        EKG.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EKGKeyPressed(evt);
            }
        });
        scrollPane12.setViewportView(EKG);

        FormInput.add(scrollPane12);
        scrollPane12.setBounds(44, 1500, 810, 53);

        jLabel101.setText(":");
        jLabel101.setName("jLabel101"); // NOI18N
        FormInput.add(jLabel101);
        jLabel101.setBounds(0, 2220, 91, 23);

        scrollPane14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane14.setName("scrollPane14"); // NOI18N

        Kesimpulan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Kesimpulan.setColumns(20);
        Kesimpulan.setRows(5);
        Kesimpulan.setName("Kesimpulan"); // NOI18N
        Kesimpulan.setPreferredSize(new java.awt.Dimension(102, 52));
        Kesimpulan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KesimpulanKeyPressed(evt);
            }
        });
        scrollPane14.setViewportView(Kesimpulan);

        FormInput.add(scrollPane14);
        scrollPane14.setBounds(44, 2270, 810, 53);

        jLabel102.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel102.setText("N. KESIMPULAN");
        jLabel102.setName("jLabel102"); // NOI18N
        FormInput.add(jLabel102);
        jLabel102.setBounds(10, 2250, 190, 23);

        scrollPane15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane15.setName("scrollPane15"); // NOI18N

        Anjuran.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Anjuran.setColumns(20);
        Anjuran.setRows(5);
        Anjuran.setName("Anjuran"); // NOI18N
        Anjuran.setPreferredSize(new java.awt.Dimension(102, 52));
        Anjuran.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AnjuranKeyPressed(evt);
            }
        });
        scrollPane15.setViewportView(Anjuran);

        FormInput.add(scrollPane15);
        scrollPane15.setBounds(44, 2350, 810, 53);

        jLabel103.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel103.setText("O. ANJURAN");
        jLabel103.setName("jLabel103"); // NOI18N
        FormInput.add(jLabel103);
        jLabel103.setBounds(10, 2330, 330, 23);

        jLabel104.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel104.setText("M. RIWAYAT MEROKOK DAN KONSUMSI ALKOHOL");
        jLabel104.setName("jLabel104"); // NOI18N
        FormInput.add(jLabel104);
        jLabel104.setBounds(10, 2200, 340, 23);

        jLabel45.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel45.setText("Kepala :");
        jLabel45.setName("jLabel45"); // NOI18N
        FormInput.add(jLabel45);
        jLabel45.setBounds(44, 390, 84, 23);

        Submandibula.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Membesar", "Membesar", "-" }));
        Submandibula.setName("Submandibula"); // NOI18N
        Submandibula.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SubmandibulaKeyPressed(evt);
            }
        });
        FormInput.add(Submandibula);
        Submandibula.setBounds(144, 330, 130, 23);

        Kesadaran.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Composmentis", "Apatis", "Somnolen" }));
        Kesadaran.setName("Kesadaran"); // NOI18N
        Kesadaran.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KesadaranKeyPressed(evt);
            }
        });
        FormInput.add(Kesadaran);
        Kesadaran.setBounds(356, 220, 125, 23);

        Leher.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Membesar", "Membesar", "-" }));
        Leher.setName("Leher"); // NOI18N
        Leher.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LeherKeyPressed(evt);
            }
        });
        FormInput.add(Leher);
        Leher.setBounds(103, 360, 130, 23);

        Axila.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Membesar", "Membesar", "-" }));
        Axila.setName("Axila"); // NOI18N
        Axila.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AxilaKeyPressed(evt);
            }
        });
        FormInput.add(Axila);
        Axila.setBounds(420, 330, 130, 23);

        Inguinal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Membesar", "Membesar", "-" }));
        Inguinal.setName("Inguinal"); // NOI18N
        Inguinal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                InguinalKeyPressed(evt);
            }
        });
        FormInput.add(Inguinal);
        Inguinal.setBounds(420, 360, 130, 23);

        Supraklavikula.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Membesar", "Membesar", "-" }));
        Supraklavikula.setName("Supraklavikula"); // NOI18N
        Supraklavikula.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SupraklavikulaKeyPressed(evt);
            }
        });
        FormInput.add(Supraklavikula);
        Supraklavikula.setBounds(724, 330, 130, 23);

        Oedema.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada", "Ada", "-" }));
        Oedema.setName("Oedema"); // NOI18N
        Oedema.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                OedemaKeyPressed(evt);
            }
        });
        FormInput.add(Oedema);
        Oedema.setBounds(136, 430, 105, 23);

        NyeriTekanSinusFrontalis.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada", "Ada", "-" }));
        NyeriTekanSinusFrontalis.setName("NyeriTekanSinusFrontalis"); // NOI18N
        NyeriTekanSinusFrontalis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NyeriTekanSinusFrontalisKeyPressed(evt);
            }
        });
        FormInput.add(NyeriTekanSinusFrontalis);
        NyeriTekanSinusFrontalis.setBounds(438, 430, 105, 23);

        NyeriTekananSinusMaxilaris.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada", "Ada", "-" }));
        NyeriTekananSinusMaxilaris.setName("NyeriTekananSinusMaxilaris"); // NOI18N
        NyeriTekananSinusMaxilaris.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NyeriTekananSinusMaxilarisKeyPressed(evt);
            }
        });
        FormInput.add(NyeriTekananSinusMaxilaris);
        NyeriTekananSinusMaxilaris.setBounds(749, 430, 105, 23);

        Palpebra.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Oedem", "Ptosis", "-" }));
        Palpebra.setName("Palpebra"); // NOI18N
        Palpebra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PalpebraKeyPressed(evt);
            }
        });
        FormInput.add(Palpebra);
        Palpebra.setBounds(138, 510, 95, 23);

        Sklera.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Ikterik", "-" }));
        Sklera.setName("Sklera"); // NOI18N
        Sklera.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkleraKeyPressed(evt);
            }
        });
        FormInput.add(Sklera);
        Sklera.setBounds(312, 510, 95, 23);

        TestButaWarna.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Buta Warna Partial", "Buta Warna Total", "-" }));
        TestButaWarna.setName("TestButaWarna"); // NOI18N
        TestButaWarna.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TestButaWarnaKeyPressed(evt);
            }
        });
        FormInput.add(TestButaWarna);
        TestButaWarna.setBounds(709, 510, 145, 23);

        Konjungtiva.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Anemis", "Hiperemis", "-" }));
        Konjungtiva.setName("Konjungtiva"); // NOI18N
        Konjungtiva.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KonjungtivaKeyPressed(evt);
            }
        });
        FormInput.add(Konjungtiva);
        Konjungtiva.setBounds(152, 540, 100, 23);

        Lensa.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Jernih", "Keruh", "Kacamata", "-" }));
        Lensa.setName("Lensa"); // NOI18N
        Lensa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LensaKeyPressed(evt);
            }
        });
        FormInput.add(Lensa);
        Lensa.setBounds(329, 540, 100, 23);

        Cornea.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Tidak Normal", "-" }));
        Cornea.setName("Cornea"); // NOI18N
        Cornea.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CorneaKeyPressed(evt);
            }
        });
        FormInput.add(Cornea);
        Cornea.setBounds(491, 510, 115, 23);

        Pupil.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Isokor", "Anisokor", "-" }));
        Pupil.setName("Pupil"); // NOI18N
        Pupil.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PupilKeyPressed(evt);
            }
        });
        FormInput.add(Pupil);
        Pupil.setBounds(502, 540, 105, 23);

        LubangTelinga.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Tidak Normal", "Lapang", "Sempit", "Serumen Prop", "-" }));
        LubangTelinga.setName("LubangTelinga"); // NOI18N
        LubangTelinga.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LubangTelingaKeyPressed(evt);
            }
        });
        FormInput.add(LubangTelinga);
        LubangTelinga.setBounds(130, 620, 118, 23);

        jLabel105.setText("Alkohol :");
        jLabel105.setName("jLabel105"); // NOI18N
        FormInput.add(jLabel105);
        jLabel105.setBounds(456, 2220, 50, 23);

        jLabel63.setText("Lubang Hidung :");
        jLabel63.setName("jLabel63"); // NOI18N
        FormInput.add(jLabel63);
        jLabel63.setBounds(330, 670, 90, 23);

        DaunTelinga.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Tidak Normal", "-" }));
        DaunTelinga.setName("DaunTelinga"); // NOI18N
        DaunTelinga.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DaunTelingaKeyPressed(evt);
            }
        });
        FormInput.add(DaunTelinga);
        DaunTelinga.setBounds(304, 620, 114, 23);

        jLabel64.setText("Daun :");
        jLabel64.setName("jLabel64"); // NOI18N
        FormInput.add(jLabel64);
        jLabel64.setBounds(257, 620, 43, 23);

        SelaputPendengaran.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Intak", "Tidak Intak", "-" }));
        SelaputPendengaran.setName("SelaputPendengaran"); // NOI18N
        SelaputPendengaran.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SelaputPendengaranKeyPressed(evt);
            }
        });
        FormInput.add(SelaputPendengaran);
        SelaputPendengaran.setBounds(524, 620, 105, 23);

        jLabel65.setText("Selaput Dengar :");
        jLabel65.setName("jLabel65"); // NOI18N
        FormInput.add(jLabel65);
        jLabel65.setBounds(417, 620, 103, 23);

        NyeriMastoideus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Tidak Normal", "-" }));
        NyeriMastoideus.setName("NyeriMastoideus"); // NOI18N
        NyeriMastoideus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NyeriMastoideusKeyPressed(evt);
            }
        });
        FormInput.add(NyeriMastoideus);
        NyeriMastoideus.setBounds(740, 620, 114, 23);

        jLabel46.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel46.setText("3. Telinga");
        jLabel46.setName("jLabel46"); // NOI18N
        FormInput.add(jLabel46);
        jLabel46.setBounds(64, 600, 150, 23);

        SeptumNasi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Deviasi", "-" }));
        SeptumNasi.setName("SeptumNasi"); // NOI18N
        SeptumNasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SeptumNasiKeyPressed(evt);
            }
        });
        FormInput.add(SeptumNasi);
        SeptumNasi.setBounds(156, 670, 100, 23);

        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel31.setText("Konjungtiva");
        jLabel31.setName("jLabel31"); // NOI18N
        FormInput.add(jLabel31);
        jLabel31.setBounds(84, 540, 70, 23);

        jLabel67.setText("Tonsil :");
        jLabel67.setName("jLabel67"); // NOI18N
        FormInput.add(jLabel67);
        jLabel67.setBounds(456, 750, 60, 23);

        LubangHidung.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Lapang", "Rhinore", "Epistaksis", "-" }));
        LubangHidung.setName("LubangHidung"); // NOI18N
        LubangHidung.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LubangHidungKeyPressed(evt);
            }
        });
        FormInput.add(LubangHidung);
        LubangHidung.setBounds(424, 670, 100, 23);

        jLabel68.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel68.setText("4. Hidung");
        jLabel68.setName("jLabel68"); // NOI18N
        FormInput.add(jLabel68);
        jLabel68.setBounds(64, 650, 150, 23);

        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel29.setText("Submandibula");
        jLabel29.setName("jLabel29"); // NOI18N
        FormInput.add(jLabel29);
        jLabel29.setBounds(64, 330, 100, 23);

        jLabel69.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel69.setText("Septum Nasi");
        jLabel69.setName("jLabel69"); // NOI18N
        FormInput.add(jLabel69);
        jLabel69.setBounds(84, 670, 162, 23);

        jLabel71.setText("Caries  :");
        jLabel71.setName("jLabel71"); // NOI18N
        FormInput.add(jLabel71);
        jLabel71.setBounds(689, 720, 63, 23);

        jLabel72.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel72.setText("Lidah");
        jLabel72.setName("jLabel72"); // NOI18N
        FormInput.add(jLabel72);
        jLabel72.setBounds(84, 750, 50, 23);

        jLabel73.setText("Kelenjar Gondok :");
        jLabel73.setName("jLabel73"); // NOI18N
        FormInput.add(jLabel73);
        jLabel73.setBounds(381, 800, 120, 23);

        Bibir.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Lembab", "Kering", "-" }));
        Bibir.setName("Bibir"); // NOI18N
        Bibir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BibirKeyPressed(evt);
            }
        });
        FormInput.add(Bibir);
        Bibir.setBounds(118, 720, 88, 23);

        Caries.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada", "Ada", "-" }));
        Caries.setName("Caries"); // NOI18N
        Caries.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CariesKeyPressed(evt);
            }
        });
        FormInput.add(Caries);
        Caries.setBounds(756, 720, 98, 23);

        Lidah.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Bersih", "Kotor", "Tremor", "-" }));
        Lidah.setName("Lidah"); // NOI18N
        Lidah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LidahKeyPressed(evt);
            }
        });
        FormInput.add(Lidah);
        Lidah.setBounds(121, 750, 85, 23);

        Faring.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Hiperemis", "-" }));
        Faring.setName("Faring"); // NOI18N
        Faring.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FaringKeyPressed(evt);
            }
        });
        FormInput.add(Faring);
        Faring.setBounds(316, 750, 100, 23);

        Tonsil.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "T1-T1", "T2-T2", "T3-T3", "T4-T4", "T0-T0", "-" }));
        Tonsil.setName("Tonsil"); // NOI18N
        Tonsil.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TonsilKeyPressed(evt);
            }
        });
        FormInput.add(Tonsil);
        Tonsil.setBounds(520, 750, 80, 23);

        jLabel74.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel74.setText("5. Mulut");
        jLabel74.setName("jLabel74"); // NOI18N
        FormInput.add(jLabel74);
        jLabel74.setBounds(64, 700, 150, 23);

        jLabel75.setText("Faring :");
        jLabel75.setName("jLabel75"); // NOI18N
        FormInput.add(jLabel75);
        jLabel75.setBounds(252, 750, 60, 23);

        jLabel76.setText("Bunyi Tambahan :");
        jLabel76.setName("jLabel76"); // NOI18N
        FormInput.add(jLabel76);
        jLabel76.setBounds(376, 900, 110, 23);

        KelenjarLimfe.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Membesar", "Membesar", "-" }));
        KelenjarLimfe.setName("KelenjarLimfe"); // NOI18N
        KelenjarLimfe.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KelenjarLimfeKeyPressed(evt);
            }
        });
        FormInput.add(KelenjarLimfe);
        KelenjarLimfe.setBounds(165, 800, 140, 23);

        KelenjarGondok.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Membesar", "Membesar", "-" }));
        KelenjarGondok.setName("KelenjarGondok"); // NOI18N
        KelenjarGondok.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KelenjarGondokKeyPressed(evt);
            }
        });
        FormInput.add(KelenjarGondok);
        KelenjarGondok.setBounds(505, 800, 140, 23);

        jLabel77.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel77.setText("1. Muka");
        jLabel77.setName("jLabel77"); // NOI18N
        FormInput.add(jLabel77);
        jLabel77.setBounds(64, 410, 150, 23);

        jLabel78.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel78.setText("6. Leher");
        jLabel78.setName("jLabel78"); // NOI18N
        FormInput.add(jLabel78);
        jLabel78.setBounds(64, 780, 150, 23);

        jLabel66.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel66.setText("Lubang");
        jLabel66.setName("jLabel66"); // NOI18N
        FormInput.add(jLabel66);
        jLabel66.setBounds(84, 620, 136, 23);

        jLabel79.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel79.setText("Dada :");
        jLabel79.setName("jLabel79"); // NOI18N
        FormInput.add(jLabel79);
        jLabel79.setBounds(44, 830, 76, 23);

        jLabel80.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel80.setText("Kelenjar Limfe");
        jLabel80.setName("jLabel80"); // NOI18N
        FormInput.add(jLabel80);
        jLabel80.setBounds(84, 800, 171, 23);

        jLabel81.setText("Bunyi Jantung :");
        jLabel81.setName("jLabel81"); // NOI18N
        FormInput.add(jLabel81);
        jLabel81.setBounds(376, 950, 110, 23);

        jLabel82.setText("Vocal Fremitus :");
        jLabel82.setName("jLabel82"); // NOI18N
        FormInput.add(jLabel82);
        jLabel82.setBounds(376, 870, 110, 23);

        jLabel83.setText("Perkusi :");
        jLabel83.setName("jLabel83"); // NOI18N
        FormInput.add(jLabel83);
        jLabel83.setBounds(652, 870, 100, 23);

        GerakanDada.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Simetris", "Tidak Simetris", "-" }));
        GerakanDada.setName("GerakanDada"); // NOI18N
        GerakanDada.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GerakanDadaKeyPressed(evt);
            }
        });
        FormInput.add(GerakanDada);
        GerakanDada.setBounds(163, 870, 129, 23);

        BunyiNapas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Vesikuler", "Bronkhial", "Trakeal", "-" }));
        BunyiNapas.setName("BunyiNapas"); // NOI18N
        BunyiNapas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BunyiNapasKeyPressed(evt);
            }
        });
        FormInput.add(BunyiNapas);
        BunyiNapas.setBounds(154, 900, 129, 23);

        VocalFremitus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Sama", "Tidak Sama", "-" }));
        VocalFremitus.setName("VocalFremitus"); // NOI18N
        VocalFremitus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                VocalFremitusKeyPressed(evt);
            }
        });
        FormInput.add(VocalFremitus);
        VocalFremitus.setBounds(490, 870, 116, 23);

        BunyiTambahan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada", "Wheezing", "Ronchi" }));
        BunyiTambahan.setName("BunyiTambahan"); // NOI18N
        BunyiTambahan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BunyiTambahanKeyPressed(evt);
            }
        });
        FormInput.add(BunyiTambahan);
        BunyiTambahan.setBounds(490, 900, 116, 23);

        PerkusiDada.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Sonor", "Pekak" }));
        PerkusiDada.setName("PerkusiDada"); // NOI18N
        PerkusiDada.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PerkusiDadaKeyPressed(evt);
            }
        });
        FormInput.add(PerkusiDada);
        PerkusiDada.setBounds(756, 870, 98, 23);

        jLabel44.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel44.setText("1. Paru");
        jLabel44.setName("jLabel44"); // NOI18N
        FormInput.add(jLabel44);
        jLabel44.setBounds(64, 850, 100, 23);

        jLabel70.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel70.setText("Bibir");
        jLabel70.setName("jLabel70"); // NOI18N
        FormInput.add(jLabel70);
        jLabel70.setBounds(84, 720, 80, 23);

        jLabel85.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel85.setText("Gerakan Dada");
        jLabel85.setName("jLabel85"); // NOI18N
        FormInput.add(jLabel85);
        jLabel85.setBounds(84, 870, 110, 23);

        jLabel38.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel38.setText("Oedema");
        jLabel38.setName("jLabel38"); // NOI18N
        FormInput.add(jLabel38);
        jLabel38.setBounds(84, 430, 70, 23);

        jLabel86.setText("Hepar :");
        jLabel86.setName("jLabel86"); // NOI18N
        FormInput.add(jLabel86);
        jLabel86.setBounds(630, 1030, 90, 23);

        jLabel87.setText("Batas :");
        jLabel87.setName("jLabel87"); // NOI18N
        FormInput.add(jLabel87);
        jLabel87.setBounds(652, 950, 100, 23);

        jLabel88.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel88.setText("2. Jantung");
        jLabel88.setName("jLabel88"); // NOI18N
        FormInput.add(jLabel88);
        jLabel88.setBounds(64, 930, 100, 23);

        jLabel91.setText("Palpasi :");
        jLabel91.setName("jLabel91"); // NOI18N
        FormInput.add(jLabel91);
        jLabel91.setBounds(275, 1030, 90, 23);

        jLabel92.setText("Limpa :");
        jLabel92.setName("jLabel92"); // NOI18N
        FormInput.add(jLabel92);
        jLabel92.setBounds(630, 1060, 90, 23);

        jLabel93.setText("Ekstremitas Bawah :");
        jLabel93.setName("jLabel93"); // NOI18N
        FormInput.add(jLabel93);
        jLabel93.setBounds(465, 1210, 110, 23);

        jLabel95.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel95.setText("Abdomen :");
        jLabel95.setName("jLabel95"); // NOI18N
        FormInput.add(jLabel95);
        jLabel95.setBounds(44, 1010, 97, 23);

        jLabel96.setText("Auskultasi :");
        jLabel96.setName("jLabel96"); // NOI18N
        FormInput.add(jLabel96);
        jLabel96.setBounds(315, 1060, 90, 23);

        jLabel98.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel98.setText("Punggung :");
        jLabel98.setName("jLabel98"); // NOI18N
        FormInput.add(jLabel98);
        jLabel98.setBounds(44, 1090, 99, 23);

        jLabel100.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel100.setText("Anggota Gerak :");
        jLabel100.setName("jLabel100"); // NOI18N
        FormInput.add(jLabel100);
        jLabel100.setBounds(44, 1190, 123, 23);

        IctusCordis.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Terlihat", "Terlihat", "Teraba", "Tidak Teraba", "-" }));
        IctusCordis.setName("IctusCordis"); // NOI18N
        IctusCordis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                IctusCordisKeyPressed(evt);
            }
        });
        FormInput.add(IctusCordis);
        IctusCordis.setBounds(153, 950, 139, 23);

        BunyiJantung.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Reguler", "Irreguler", "Gallop", "Lain-lain", "-" }));
        BunyiJantung.setName("BunyiJantung"); // NOI18N
        BunyiJantung.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BunyiJantungKeyPressed(evt);
            }
        });
        FormInput.add(BunyiJantung);
        BunyiJantung.setBounds(490, 950, 116, 23);

        Batas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Melebar", "-" }));
        Batas.setName("Batas"); // NOI18N
        Batas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BatasKeyPressed(evt);
            }
        });
        FormInput.add(Batas);
        Batas.setBounds(756, 950, 98, 23);

        Inspeksi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Datar", "Cembung", "-" }));
        Inspeksi.setName("Inspeksi"); // NOI18N
        Inspeksi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                InspeksiKeyPressed(evt);
            }
        });
        FormInput.add(Inspeksi);
        Inspeksi.setBounds(115, 1030, 110, 23);

        Palpasi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Supel", "Tegang (Defans Muscular)", "Nyeri Tekan Epigastrium", "Nyeri Tekan Suprapubik", "Nyeri Tekan Right Lower Quadrant", "Nyeri Tekan Left Lower Quadrant", "-" }));
        Palpasi.setName("Palpasi"); // NOI18N
        Palpasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PalpasiKeyPressed(evt);
            }
        });
        FormInput.add(Palpasi);
        Palpasi.setBounds(369, 1030, 227, 23);

        PerkusiAbdomen.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Timpani", "Hipertimpani", "Dull", "-" }));
        PerkusiAbdomen.setName("PerkusiAbdomen"); // NOI18N
        PerkusiAbdomen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PerkusiAbdomenKeyPressed(evt);
            }
        });
        FormInput.add(PerkusiAbdomen);
        PerkusiAbdomen.setBounds(110, 1060, 130, 23);

        Auskultasi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Bising Usus Meningkat", "Bising Usus Menurun", "-" }));
        Auskultasi.setName("Auskultasi"); // NOI18N
        Auskultasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AuskultasiKeyPressed(evt);
            }
        });
        FormInput.add(Auskultasi);
        Auskultasi.setBounds(409, 1060, 175, 23);

        Hepar.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Membesar", "Membesar", "-" }));
        Hepar.setName("Hepar"); // NOI18N
        Hepar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HeparKeyPressed(evt);
            }
        });
        FormInput.add(Hepar);
        Hepar.setBounds(724, 1030, 130, 23);

        Limpa.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Membesar", "Membesar", "-" }));
        Limpa.setName("Limpa"); // NOI18N
        Limpa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LimpaKeyPressed(evt);
            }
        });
        FormInput.add(Limpa);
        Limpa.setBounds(724, 1060, 130, 23);

        NyeriKtok.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada", "Ada Di Kiri", "Ada Di Kanan", "-" }));
        NyeriKtok.setName("NyeriKtok"); // NOI18N
        NyeriKtok.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NyeriKtokKeyPressed(evt);
            }
        });
        FormInput.add(NyeriKtok);
        NyeriKtok.setBounds(261, 1110, 130, 23);

        jLabel84.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel84.setText("Bunyi Napas");
        jLabel84.setName("jLabel84"); // NOI18N
        FormInput.add(jLabel84);
        jLabel84.setBounds(84, 900, 80, 23);

        ExtremitasAtas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Tidak Normal", "-" }));
        ExtremitasAtas.setName("ExtremitasAtas"); // NOI18N
        ExtremitasAtas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ExtremitasAtasKeyPressed(evt);
            }
        });
        FormInput.add(ExtremitasAtas);
        ExtremitasAtas.setBounds(157, 1210, 117, 23);

        ExtremitasBawah.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Tidak Normal", "-" }));
        ExtremitasBawah.setName("ExtremitasBawah"); // NOI18N
        ExtremitasBawah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ExtremitasBawahKeyPressed(evt);
            }
        });
        FormInput.add(ExtremitasBawah);
        ExtremitasBawah.setBounds(579, 1210, 117, 23);

        jLabel90.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel90.setText("Inspeksi");
        jLabel90.setName("jLabel90"); // NOI18N
        FormInput.add(jLabel90);
        jLabel90.setBounds(64, 1030, 70, 23);

        KetExtremitasAtas.setName("KetExtremitasAtas"); // NOI18N
        KetExtremitasAtas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetExtremitasAtasKeyPressed(evt);
            }
        });
        FormInput.add(KetExtremitasAtas);
        KetExtremitasAtas.setBounds(277, 1210, 155, 23);

        KetExtremitasBawah.setName("KetExtremitasBawah"); // NOI18N
        KetExtremitasBawah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetExtremitasBawahKeyPressed(evt);
            }
        });
        FormInput.add(KetExtremitasBawah);
        KetExtremitasBawah.setBounds(699, 1210, 155, 23);

        Kulit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Tato", "Penyakit Kulit", "-" }));
        Kulit.setName("Kulit"); // NOI18N
        Kulit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KulitKeyPressed(evt);
            }
        });
        FormInput.add(Kulit);
        Kulit.setBounds(135, 1160, 118, 23);

        jLabel99.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel99.setText("Ekstremitas Atas");
        jLabel99.setName("jLabel99"); // NOI18N
        FormInput.add(jLabel99);
        jLabel99.setBounds(64, 1210, 120, 23);

        jLabel89.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel89.setText("Ictus Cordis");
        jLabel89.setName("jLabel89"); // NOI18N
        FormInput.add(jLabel89);
        jLabel89.setBounds(84, 950, 90, 23);

        scrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane2.setName("scrollPane2"); // NOI18N

        RiwayatPenyakitSekarang.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        RiwayatPenyakitSekarang.setColumns(20);
        RiwayatPenyakitSekarang.setRows(5);
        RiwayatPenyakitSekarang.setName("RiwayatPenyakitSekarang"); // NOI18N
        RiwayatPenyakitSekarang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RiwayatPenyakitSekarangKeyPressed(evt);
            }
        });
        scrollPane2.setViewportView(RiwayatPenyakitSekarang);

        FormInput.add(scrollPane2);
        scrollPane2.setBounds(44, 115, 260, 48);

        scrollPane3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane3.setName("scrollPane3"); // NOI18N

        RiwayatPenyakitKeluarga.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        RiwayatPenyakitKeluarga.setColumns(20);
        RiwayatPenyakitKeluarga.setRows(5);
        RiwayatPenyakitKeluarga.setName("RiwayatPenyakitKeluarga"); // NOI18N
        RiwayatPenyakitKeluarga.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RiwayatPenyakitKeluargaKeyPressed(evt);
            }
        });
        scrollPane3.setViewportView(RiwayatPenyakitKeluarga);

        FormInput.add(scrollPane3);
        scrollPane3.setBounds(319, 115, 260, 48);

        jLabel94.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel94.setText("Perkusi");
        jLabel94.setName("jLabel94"); // NOI18N
        FormInput.add(jLabel94);
        jLabel94.setBounds(64, 1060, 80, 23);

        scrollPane4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane4.setName("scrollPane4"); // NOI18N

        RiwayatPenyakitDahulu.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        RiwayatPenyakitDahulu.setColumns(20);
        RiwayatPenyakitDahulu.setRows(5);
        RiwayatPenyakitDahulu.setName("RiwayatPenyakitDahulu"); // NOI18N
        RiwayatPenyakitDahulu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RiwayatPenyakitDahuluKeyPressed(evt);
            }
        });
        scrollPane4.setViewportView(RiwayatPenyakitDahulu);

        FormInput.add(scrollPane4);
        scrollPane4.setBounds(594, 115, 260, 48);

        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel24.setText("cm");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(362, 250, 30, 23);

        jLabel97.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel97.setText("Nyeri Ketok Costovertebral Angle/CVA");
        jLabel97.setName("jLabel97"); // NOI18N
        FormInput.add(jLabel97);
        jLabel97.setBounds(64, 1110, 220, 23);

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel13.setText("Kg");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(544, 250, 30, 23);

        jSeparator3.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator3.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator3.setName("jSeparator3"); // NOI18N
        FormInput.add(jSeparator3);
        jSeparator3.setBounds(0, 1320, 880, 1);

        jSeparator4.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator4.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator4.setName("jSeparator4"); // NOI18N
        FormInput.add(jSeparator4);
        jSeparator4.setBounds(0, 1400, 880, 1);

        jSeparator5.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator5.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator5.setName("jSeparator5"); // NOI18N
        FormInput.add(jSeparator5);
        jSeparator5.setBounds(0, 1480, 880, 1);

        jSeparator6.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator6.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator6.setName("jSeparator6"); // NOI18N
        FormInput.add(jSeparator6);
        jSeparator6.setBounds(0, 2200, 880, 1);

        Merokok.setName("Merokok"); // NOI18N
        Merokok.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MerokokKeyPressed(evt);
            }
        });
        FormInput.add(Merokok);
        Merokok.setBounds(95, 2220, 344, 23);

        Alkohol.setName("Alkohol"); // NOI18N
        Alkohol.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlkoholKeyPressed(evt);
            }
        });
        FormInput.add(Alkohol);
        Alkohol.setBounds(510, 2220, 344, 23);

        jSeparator7.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator7.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator7.setName("jSeparator7"); // NOI18N
        FormInput.add(jSeparator7);
        jSeparator7.setBounds(0, 2250, 880, 1);

        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel32.setText("Leher");
        jLabel32.setName("jLabel32"); // NOI18N
        FormInput.add(jLabel32);
        jLabel32.setBounds(64, 360, 50, 23);

        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel30.setText("Palpebra ");
        jLabel30.setName("jLabel30"); // NOI18N
        FormInput.add(jLabel30);
        jLabel30.setBounds(84, 510, 70, 23);

        jSeparator8.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator8.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator8.setName("jSeparator8"); // NOI18N
        FormInput.add(jSeparator8);
        jSeparator8.setBounds(0, 2330, 880, 1);

        jSeparator9.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator9.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator9.setName("jSeparator9"); // NOI18N
        FormInput.add(jSeparator9);
        jSeparator9.setBounds(44, 310, 836, 1);

        jSeparator10.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator10.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator10.setName("jSeparator10"); // NOI18N
        FormInput.add(jSeparator10);
        jSeparator10.setBounds(44, 390, 836, 1);

        jSeparator11.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator11.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator11.setName("jSeparator11"); // NOI18N
        FormInput.add(jSeparator11);
        jSeparator11.setBounds(44, 830, 836, 1);

        jSeparator12.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator12.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator12.setName("jSeparator12"); // NOI18N
        FormInput.add(jSeparator12);
        jSeparator12.setBounds(44, 1010, 836, 1);

        jSeparator13.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator13.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator13.setName("jSeparator13"); // NOI18N
        FormInput.add(jSeparator13);
        jSeparator13.setBounds(44, 1090, 836, 1);

        jSeparator14.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator14.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator14.setName("jSeparator14"); // NOI18N
        FormInput.add(jSeparator14);
        jSeparator14.setBounds(44, 1190, 836, 1);

        jLabel106.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel106.setText("Kondisi Kulit");
        jLabel106.setName("jLabel106"); // NOI18N
        FormInput.add(jLabel106);
        jLabel106.setBounds(64, 1160, 90, 23);

        jSeparator15.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator15.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator15.setName("jSeparator15"); // NOI18N
        FormInput.add(jSeparator15);
        jSeparator15.setBounds(0, 1560, 880, 1);

        jLabel107.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel107.setText("F. SPIROMETRI");
        jLabel107.setName("jLabel107"); // NOI18N
        FormInput.add(jLabel107);
        jLabel107.setBounds(10, 1560, 182, 23);

        scrollPane13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane13.setName("scrollPane13"); // NOI18N
        scrollPane13.setPreferredSize(new java.awt.Dimension(199, 52));

        Spirometri.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Spirometri.setColumns(20);
        Spirometri.setRows(5);
        Spirometri.setName("Spirometri"); // NOI18N
        Spirometri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SpirometriKeyPressed(evt);
            }
        });
        scrollPane13.setViewportView(Spirometri);

        FormInput.add(scrollPane13);
        scrollPane13.setBounds(44, 1580, 810, 53);

        jSeparator16.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator16.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator16.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator16.setName("jSeparator16"); // NOI18N
        FormInput.add(jSeparator16);
        jSeparator16.setBounds(0, 1640, 880, 1);

        jLabel108.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel108.setText("G. AUDIOMETRI");
        jLabel108.setName("jLabel108"); // NOI18N
        FormInput.add(jLabel108);
        jLabel108.setBounds(10, 1640, 182, 23);

        scrollPane16.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane16.setName("scrollPane16"); // NOI18N
        scrollPane16.setPreferredSize(new java.awt.Dimension(199, 52));

        Audiometri.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Audiometri.setColumns(20);
        Audiometri.setRows(5);
        Audiometri.setName("Audiometri"); // NOI18N
        Audiometri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AudiometriKeyPressed(evt);
            }
        });
        scrollPane16.setViewportView(Audiometri);

        FormInput.add(scrollPane16);
        scrollPane16.setBounds(44, 1660, 810, 53);

        jSeparator17.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator17.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator17.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator17.setName("jSeparator17"); // NOI18N
        FormInput.add(jSeparator17);
        jSeparator17.setBounds(0, 1720, 880, 1);

        jLabel109.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel109.setText("H. TREADMILL");
        jLabel109.setName("jLabel109"); // NOI18N
        FormInput.add(jLabel109);
        jLabel109.setBounds(10, 1720, 182, 23);

        scrollPane17.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane17.setName("scrollPane17"); // NOI18N
        scrollPane17.setPreferredSize(new java.awt.Dimension(199, 52));

        Treadmill.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Treadmill.setColumns(20);
        Treadmill.setRows(5);
        Treadmill.setName("Treadmill"); // NOI18N
        Treadmill.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TreadmillKeyPressed(evt);
            }
        });
        scrollPane17.setViewportView(Treadmill);

        FormInput.add(scrollPane17);
        scrollPane17.setBounds(44, 1740, 810, 53);

        jSeparator18.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator18.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator18.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator18.setName("jSeparator18"); // NOI18N
        FormInput.add(jSeparator18);
        jSeparator18.setBounds(0, 2120, 880, 1);

        jLabel110.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel110.setText("L. LAIN-LAIN");
        jLabel110.setName("jLabel110"); // NOI18N
        FormInput.add(jLabel110);
        jLabel110.setBounds(10, 2120, 182, 23);

        scrollPane18.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane18.setName("scrollPane18"); // NOI18N
        scrollPane18.setPreferredSize(new java.awt.Dimension(199, 52));

        Lainlain.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Lainlain.setColumns(20);
        Lainlain.setRows(5);
        Lainlain.setName("Lainlain"); // NOI18N
        Lainlain.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LainlainKeyPressed(evt);
            }
        });
        scrollPane18.setViewportView(Lainlain);

        FormInput.add(scrollPane18);
        scrollPane18.setBounds(44, 2140, 810, 53);

        jLabel111.setText("Sinus :");
        jLabel111.setName("jLabel111"); // NOI18N
        FormInput.add(jLabel111);
        jLabel111.setBounds(532, 670, 100, 23);

        Sinus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Tidak Normal", "-" }));
        Sinus.setName("Sinus"); // NOI18N
        Sinus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SinusKeyPressed(evt);
            }
        });
        FormInput.add(Sinus);
        Sinus.setBounds(636, 670, 114, 23);

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
        FormInput.add(BtnDokter3);
        BtnDokter3.setBounds(14, 1340, 28, 23);

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
        FormInput.add(BtnDokter2);
        BtnDokter2.setBounds(14, 1420, 28, 23);

        jLabel27.setText("BMI(BB/TB²) :");
        jLabel27.setName("jLabel27"); // NOI18N
        FormInput.add(jLabel27);
        jLabel27.setBounds(720, 250, 80, 23);

        IMT.setEditable(false);
        IMT.setFocusTraversalPolicyProvider(true);
        IMT.setName("IMT"); // NOI18N
        FormInput.add(IMT);
        IMT.setBounds(804, 250, 50, 23);

        jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel40.setText("Klasifikasi BMI");
        jLabel40.setName("jLabel40"); // NOI18N
        FormInput.add(jLabel40);
        jLabel40.setBounds(44, 280, 90, 23);

        KlasifikasiIMT.setEditable(false);
        KlasifikasiIMT.setFocusTraversalPolicyProvider(true);
        KlasifikasiIMT.setName("KlasifikasiIMT"); // NOI18N
        FormInput.add(KlasifikasiIMT);
        KlasifikasiIMT.setBounds(125, 280, 170, 23);

        jLabel42.setText(":");
        jLabel42.setName("jLabel42"); // NOI18N
        FormInput.add(jLabel42);
        jLabel42.setBounds(0, 220, 127, 23);

        jLabel112.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel112.setText("Riwayat Alergi Makanan & Obat");
        jLabel112.setName("jLabel112"); // NOI18N
        FormInput.add(jLabel112);
        jLabel112.setBounds(44, 170, 205, 23);

        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel14.setText("BMI Ideal : 18.5 – 22.99");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(333, 280, 140, 23);

        Scoliosis.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada", "Ada", "-" }));
        Scoliosis.setName("Scoliosis"); // NOI18N
        Scoliosis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ScoliosisKeyPressed(evt);
            }
        });
        FormInput.add(Scoliosis);
        Scoliosis.setBounds(516, 1110, 100, 23);

        jLabel114.setText("Scoliosis :");
        jLabel114.setName("jLabel114"); // NOI18N
        FormInput.add(jLabel114);
        jLabel114.setBounds(432, 1110, 80, 23);

        jLabel115.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel115.setText("cm");
        jLabel115.setName("jLabel115"); // NOI18N
        FormInput.add(jLabel115);
        jLabel115.setBounds(561, 280, 30, 23);

        LP.setFocusTraversalPolicyProvider(true);
        LP.setName("LP"); // NOI18N
        LP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LPKeyPressed(evt);
            }
        });
        FormInput.add(LP);
        LP.setBounds(508, 280, 50, 23);

        jLabel116.setText("LP :");
        jLabel116.setName("jLabel116"); // NOI18N
        FormInput.add(jLabel116);
        jLabel116.setBounds(454, 280, 50, 23);

        RisikoLP.setEditable(false);
        RisikoLP.setFocusTraversalPolicyProvider(true);
        RisikoLP.setName("RisikoLP"); // NOI18N
        FormInput.add(RisikoLP);
        RisikoLP.setBounds(714, 280, 140, 23);

        jLabel117.setText("Risiko Berdasar LP :");
        jLabel117.setName("jLabel117"); // NOI18N
        FormInput.add(jLabel117);
        jLabel117.setBounds(600, 280, 110, 23);

        jLabel118.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel118.setText("Merokok");
        jLabel118.setName("jLabel118"); // NOI18N
        FormInput.add(jLabel118);
        jLabel118.setBounds(44, 2220, 91, 23);

        Rambut.setFocusTraversalPolicyProvider(true);
        Rambut.setName("Rambut"); // NOI18N
        Rambut.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RambutKeyPressed(evt);
            }
        });
        FormInput.add(Rambut);
        Rambut.setBounds(125, 460, 729, 23);

        jLabel113.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel113.setText("2. Rambut");
        jLabel113.setName("jLabel113"); // NOI18N
        FormInput.add(jLabel113);
        jLabel113.setBounds(64, 460, 80, 23);

        jLabel119.setText(":");
        jLabel119.setName("jLabel119"); // NOI18N
        FormInput.add(jLabel119);
        jLabel119.setBounds(0, 460, 121, 23);

        MenggunakanKacamata.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya", "-" }));
        MenggunakanKacamata.setName("MenggunakanKacamata"); // NOI18N
        MenggunakanKacamata.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MenggunakanKacamataKeyPressed(evt);
            }
        });
        FormInput.add(MenggunakanKacamata);
        MenggunakanKacamata.setBounds(774, 540, 80, 23);

        jLabel120.setText("Menggunakan Kacamata :");
        jLabel120.setName("jLabel120"); // NOI18N
        FormInput.add(jLabel120);
        jLabel120.setBounds(640, 540, 130, 23);

        Visus.setFocusTraversalPolicyProvider(true);
        Visus.setName("Visus"); // NOI18N
        Visus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                VisusKeyPressed(evt);
            }
        });
        FormInput.add(Visus);
        Visus.setBounds(121, 570, 220, 23);

        jLabel122.setText(":");
        jLabel122.setName("jLabel122"); // NOI18N
        FormInput.add(jLabel122);
        jLabel122.setBounds(0, 250, 97, 23);

        jLabel123.setText(":");
        jLabel123.setName("jLabel123"); // NOI18N
        FormInput.add(jLabel123);
        jLabel123.setBounds(0, 280, 121, 23);

        jLabel124.setText(":");
        jLabel124.setName("jLabel124"); // NOI18N
        FormInput.add(jLabel124);
        jLabel124.setBounds(0, 330, 140, 23);

        jLabel125.setText(":");
        jLabel125.setName("jLabel125"); // NOI18N
        FormInput.add(jLabel125);
        jLabel125.setBounds(0, 360, 99, 23);

        jLabel126.setText(":");
        jLabel126.setName("jLabel126"); // NOI18N
        FormInput.add(jLabel126);
        jLabel126.setBounds(0, 430, 132, 23);

        jLabel127.setText(":");
        jLabel127.setName("jLabel127"); // NOI18N
        FormInput.add(jLabel127);
        jLabel127.setBounds(0, 510, 134, 23);

        jLabel121.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel121.setText("Visus");
        jLabel121.setName("jLabel121"); // NOI18N
        FormInput.add(jLabel121);
        jLabel121.setBounds(84, 570, 158, 23);

        jLabel128.setText(":");
        jLabel128.setName("jLabel128"); // NOI18N
        FormInput.add(jLabel128);
        jLabel128.setBounds(0, 540, 148, 23);

        jLabel129.setText(":");
        jLabel129.setName("jLabel129"); // NOI18N
        FormInput.add(jLabel129);
        jLabel129.setBounds(0, 570, 117, 23);

        jLabel130.setText(":");
        jLabel130.setName("jLabel130"); // NOI18N
        FormInput.add(jLabel130);
        jLabel130.setBounds(0, 620, 126, 23);

        jLabel131.setText(":");
        jLabel131.setName("jLabel131"); // NOI18N
        FormInput.add(jLabel131);
        jLabel131.setBounds(0, 670, 152, 23);

        jLabel132.setText(":");
        jLabel132.setName("jLabel132"); // NOI18N
        FormInput.add(jLabel132);
        jLabel132.setBounds(0, 720, 114, 23);

        jLabel133.setText("Gusi :");
        jLabel133.setName("jLabel133"); // NOI18N
        FormInput.add(jLabel133);
        jLabel133.setBounds(233, 720, 70, 23);

        Gusi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Tidak Normal", "-" }));
        Gusi.setName("Gusi"); // NOI18N
        Gusi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GusiKeyPressed(evt);
            }
        });
        FormInput.add(Gusi);
        Gusi.setBounds(307, 720, 114, 23);

        Gigi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Tidak Normal", "-" }));
        Gigi.setName("Gigi"); // NOI18N
        Gigi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GigiKeyPressed(evt);
            }
        });
        FormInput.add(Gigi);
        Gigi.setBounds(520, 720, 114, 23);

        jLabel134.setText("Gigi :");
        jLabel134.setName("jLabel134"); // NOI18N
        FormInput.add(jLabel134);
        jLabel134.setBounds(446, 720, 70, 23);

        jLabel135.setText(":");
        jLabel135.setName("jLabel135"); // NOI18N
        FormInput.add(jLabel135);
        jLabel135.setBounds(0, 750, 117, 23);

        jLabel136.setText(":");
        jLabel136.setName("jLabel136"); // NOI18N
        FormInput.add(jLabel136);
        jLabel136.setBounds(0, 800, 161, 23);

        jLabel137.setText("Luas Lapang Pandang :");
        jLabel137.setName("jLabel137"); // NOI18N
        FormInput.add(jLabel137);
        jLabel137.setBounds(361, 570, 130, 23);

        KeteranganLuasLapangPandang.setFocusTraversalPolicyProvider(true);
        KeteranganLuasLapangPandang.setName("KeteranganLuasLapangPandang"); // NOI18N
        KeteranganLuasLapangPandang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganLuasLapangPandangKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganLuasLapangPandang);
        KeteranganLuasLapangPandang.setBounds(613, 570, 241, 23);

        LuasLapangPandang.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Tidak Normal", "-" }));
        LuasLapangPandang.setName("LuasLapangPandang"); // NOI18N
        LuasLapangPandang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LuasLapangPandangKeyPressed(evt);
            }
        });
        FormInput.add(LuasLapangPandang);
        LuasLapangPandang.setBounds(495, 570, 115, 23);

        jLabel138.setText(":");
        jLabel138.setName("jLabel138"); // NOI18N
        FormInput.add(jLabel138);
        jLabel138.setBounds(0, 870, 159, 23);

        jLabel139.setText(":");
        jLabel139.setName("jLabel139"); // NOI18N
        FormInput.add(jLabel139);
        jLabel139.setBounds(0, 900, 150, 23);

        jLabel140.setText(":");
        jLabel140.setName("jLabel140"); // NOI18N
        FormInput.add(jLabel140);
        jLabel140.setBounds(0, 950, 149, 23);

        jLabel141.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel141.setText("3. Mamae");
        jLabel141.setName("jLabel141"); // NOI18N
        FormInput.add(jLabel141);
        jLabel141.setBounds(64, 980, 100, 23);

        jLabel142.setText(":");
        jLabel142.setName("jLabel142"); // NOI18N
        FormInput.add(jLabel142);
        jLabel142.setBounds(0, 980, 118, 23);

        Mamae.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Tidak Normal", "-" }));
        Mamae.setName("Mamae"); // NOI18N
        Mamae.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MamaeKeyPressed(evt);
            }
        });
        FormInput.add(Mamae);
        Mamae.setBounds(122, 980, 114, 23);

        KeteranganMamae.setFocusTraversalPolicyProvider(true);
        KeteranganMamae.setName("KeteranganMamae"); // NOI18N
        KeteranganMamae.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganMamaeKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganMamae);
        KeteranganMamae.setBounds(239, 980, 615, 23);

        jLabel143.setText(":");
        jLabel143.setName("jLabel143"); // NOI18N
        FormInput.add(jLabel143);
        jLabel143.setBounds(0, 1030, 111, 23);

        jLabel144.setText(":");
        jLabel144.setName("jLabel144"); // NOI18N
        FormInput.add(jLabel144);
        jLabel144.setBounds(0, 1060, 106, 23);

        jSeparator19.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator19.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator19.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator19.setName("jSeparator19"); // NOI18N
        FormInput.add(jSeparator19);
        jSeparator19.setBounds(44, 1140, 836, 1);

        jLabel145.setText(":");
        jLabel145.setName("jLabel145"); // NOI18N
        FormInput.add(jLabel145);
        jLabel145.setBounds(0, 1110, 257, 23);

        jLabel146.setText(":");
        jLabel146.setName("jLabel146"); // NOI18N
        FormInput.add(jLabel146);
        jLabel146.setBounds(0, 1160, 131, 23);

        PenyakitKulit.setFocusTraversalPolicyProvider(true);
        PenyakitKulit.setName("PenyakitKulit"); // NOI18N
        PenyakitKulit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenyakitKulitKeyPressed(evt);
            }
        });
        FormInput.add(PenyakitKulit);
        PenyakitKulit.setBounds(374, 1160, 480, 23);

        jLabel147.setText("Penyakit Kulit :");
        jLabel147.setName("jLabel147"); // NOI18N
        FormInput.add(jLabel147);
        jLabel147.setBounds(270, 1160, 100, 23);

        jLabel148.setText(":");
        jLabel148.setName("jLabel148"); // NOI18N
        FormInput.add(jLabel148);
        jLabel148.setBounds(0, 1210, 153, 23);

        jSeparator20.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator20.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator20.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator20.setName("jSeparator20"); // NOI18N
        FormInput.add(jSeparator20);
        jSeparator20.setBounds(44, 1240, 836, 1);

        jLabel150.setText(":");
        jLabel150.setName("jLabel150"); // NOI18N
        FormInput.add(jLabel150);
        jLabel150.setBounds(0, 1250, 121, 23);

        AreaGenitalia.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada Kelainan", "Ada Kelainan", "-" }));
        AreaGenitalia.setName("AreaGenitalia"); // NOI18N
        AreaGenitalia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AreaGenitaliaKeyPressed(evt);
            }
        });
        FormInput.add(AreaGenitalia);
        AreaGenitalia.setBounds(125, 1250, 149, 23);

        KeteranganAreaGenitalia.setName("KeteranganAreaGenitalia"); // NOI18N
        KeteranganAreaGenitalia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganAreaGenitaliaKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganAreaGenitalia);
        KeteranganAreaGenitalia.setBounds(277, 1250, 577, 23);

        jLabel149.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel149.setText("Area Genitalia");
        jLabel149.setName("jLabel149"); // NOI18N
        FormInput.add(jLabel149);
        jLabel149.setBounds(44, 1250, 100, 23);

        jSeparator21.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator21.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator21.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator21.setName("jSeparator21"); // NOI18N
        FormInput.add(jSeparator21);
        jSeparator21.setBounds(44, 1280, 836, 1);

        jLabel152.setText(":");
        jLabel152.setName("jLabel152"); // NOI18N
        FormInput.add(jLabel152);
        jLabel152.setBounds(0, 1290, 127, 23);

        Anus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Tidak Normal", "-" }));
        Anus.setName("Anus"); // NOI18N
        Anus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AnusKeyPressed(evt);
            }
        });
        FormInput.add(Anus);
        Anus.setBounds(131, 1290, 114, 23);

        jLabel151.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel151.setText("Anus & Perianal");
        jLabel151.setName("jLabel151"); // NOI18N
        FormInput.add(jLabel151);
        jLabel151.setBounds(44, 1290, 100, 23);

        KeteranganAnus.setName("KeteranganAnus"); // NOI18N
        KeteranganAnus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganAnusKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganAnus);
        KeteranganAnus.setBounds(248, 1290, 606, 23);

        jSeparator22.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator22.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator22.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator22.setName("jSeparator22"); // NOI18N
        FormInput.add(jSeparator22);
        jSeparator22.setBounds(0, 1800, 880, 1);

        jLabel153.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel153.setText("I. ROMBERG TEST");
        jLabel153.setName("jLabel153"); // NOI18N
        FormInput.add(jLabel153);
        jLabel153.setBounds(10, 1800, 182, 23);

        scrollPane19.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane19.setName("scrollPane19"); // NOI18N
        scrollPane19.setPreferredSize(new java.awt.Dimension(199, 52));

        Romberg.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Romberg.setColumns(20);
        Romberg.setRows(5);
        Romberg.setName("Romberg"); // NOI18N
        Romberg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RombergKeyPressed(evt);
            }
        });
        scrollPane19.setViewportView(Romberg);

        FormInput.add(scrollPane19);
        scrollPane19.setBounds(44, 1820, 810, 53);

        jSeparator23.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator23.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator23.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator23.setName("jSeparator23"); // NOI18N
        FormInput.add(jSeparator23);
        jSeparator23.setBounds(0, 1880, 880, 1);

        jLabel154.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel154.setText("J. BACK STRENGTH");
        jLabel154.setName("jLabel154"); // NOI18N
        FormInput.add(jLabel154);
        jLabel154.setBounds(10, 1880, 182, 23);

        scrollPane20.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane20.setName("scrollPane20"); // NOI18N
        scrollPane20.setPreferredSize(new java.awt.Dimension(199, 52));

        Backstrength.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Backstrength.setColumns(20);
        Backstrength.setRows(5);
        Backstrength.setName("Backstrength"); // NOI18N
        Backstrength.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BackstrengthKeyPressed(evt);
            }
        });
        scrollPane20.setViewportView(Backstrength);

        FormInput.add(scrollPane20);
        scrollPane20.setBounds(44, 1900, 810, 53);

        jSeparator24.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator24.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator24.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator24.setName("jSeparator24"); // NOI18N
        FormInput.add(jSeparator24);
        jSeparator24.setBounds(0, 1960, 880, 1);

        jLabel155.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel155.setText("K. ANKLE BRACHIAL INDEX");
        jLabel155.setName("jLabel155"); // NOI18N
        FormInput.add(jLabel155);
        jLabel155.setBounds(10, 1960, 182, 23);

        jLabel156.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel156.setText("Tangan Kanan :");
        jLabel156.setName("jLabel156"); // NOI18N
        FormInput.add(jLabel156);
        jLabel156.setBounds(44, 1980, 97, 23);

        scrollPane21.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane21.setName("scrollPane21"); // NOI18N
        scrollPane21.setPreferredSize(new java.awt.Dimension(199, 52));

        AbiTanganKanan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        AbiTanganKanan.setColumns(20);
        AbiTanganKanan.setRows(5);
        AbiTanganKanan.setName("AbiTanganKanan"); // NOI18N
        AbiTanganKanan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AbiTanganKananKeyPressed(evt);
            }
        });
        scrollPane21.setViewportView(AbiTanganKanan);

        FormInput.add(scrollPane21);
        scrollPane21.setBounds(44, 2000, 395, 43);

        jLabel157.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel157.setText("Tangan Kiri :");
        jLabel157.setName("jLabel157"); // NOI18N
        FormInput.add(jLabel157);
        jLabel157.setBounds(459, 1980, 97, 23);

        scrollPane22.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane22.setName("scrollPane22"); // NOI18N
        scrollPane22.setPreferredSize(new java.awt.Dimension(199, 52));

        AbiTanganKiri.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        AbiTanganKiri.setColumns(20);
        AbiTanganKiri.setRows(5);
        AbiTanganKiri.setName("AbiTanganKiri"); // NOI18N
        AbiTanganKiri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AbiTanganKiriKeyPressed(evt);
            }
        });
        scrollPane22.setViewportView(AbiTanganKiri);

        FormInput.add(scrollPane22);
        scrollPane22.setBounds(459, 2000, 395, 43);

        jLabel158.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel158.setText("Kaki Kanan :");
        jLabel158.setName("jLabel158"); // NOI18N
        FormInput.add(jLabel158);
        jLabel158.setBounds(44, 2050, 97, 23);

        scrollPane23.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane23.setName("scrollPane23"); // NOI18N
        scrollPane23.setPreferredSize(new java.awt.Dimension(199, 52));

        AbiKakiKanan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        AbiKakiKanan.setColumns(20);
        AbiKakiKanan.setRows(5);
        AbiKakiKanan.setName("AbiKakiKanan"); // NOI18N
        AbiKakiKanan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AbiKakiKananKeyPressed(evt);
            }
        });
        scrollPane23.setViewportView(AbiKakiKanan);

        FormInput.add(scrollPane23);
        scrollPane23.setBounds(44, 2070, 395, 43);

        jLabel159.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel159.setText("Kaki Kiri :");
        jLabel159.setName("jLabel159"); // NOI18N
        FormInput.add(jLabel159);
        jLabel159.setBounds(460, 2050, 97, 23);

        scrollPane24.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane24.setName("scrollPane24"); // NOI18N
        scrollPane24.setPreferredSize(new java.awt.Dimension(199, 52));

        AbiKakiKiri.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        AbiKakiKiri.setColumns(20);
        AbiKakiKiri.setRows(5);
        AbiKakiKiri.setName("AbiKakiKiri"); // NOI18N
        AbiKakiKiri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AbiKakiKiriKeyPressed(evt);
            }
        });
        scrollPane24.setViewportView(AbiKakiKiri);

        FormInput.add(scrollPane24);
        scrollPane24.setBounds(460, 2070, 395, 43);

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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "07-05-2025" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "07-05-2025" }));
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
        }else if(Kesimpulan.getText().trim().equals("")){
            Valid.textKosong(Kesimpulan,"Kesimpulan");
        }else if(Anjuran.getText().trim().equals("")){
            Valid.textKosong(Anjuran,"Anjuran");
        }else if(NmDokter.getText().trim().equals("")){
            Valid.textKosong(BtnDokter,"Dokter");
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
            Valid.pindah(evt,Anjuran,BtnBatal);
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
                if(KdDokter.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),78).toString())){
                    if(Sequel.cekTanggal48jam(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString(),Sequel.ambiltanggalsekarang())==true){
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
        }else if(Kesimpulan.getText().trim().equals("")){
            Valid.textKosong(Kesimpulan,"Kesimpulan");
        }else if(Anjuran.getText().trim().equals("")){
            Valid.textKosong(Anjuran,"Anjuran");
        }else if(NmDokter.getText().trim().equals("")){
            Valid.textKosong(BtnDokter,"Dokter");
        }else{
            if(tbObat.getSelectedRow()>-1){
                if(akses.getkode().equals("Admin Utama")){
                    ganti();
                }else{
                    if(KdDokter.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),78).toString())){
                        if(Sequel.cekTanggal48jam(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString(),Sequel.ambiltanggalsekarang())==true){
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

                File f;            
                BufferedWriter bw;
                StringBuilder htmlContent;
                
                String pilihan =(String) JOptionPane.showInputDialog(null,"Silahkan pilih laporan..!","Pilihan Cetak",JOptionPane.QUESTION_MESSAGE,null,new Object[]{"Laporan 1 (HTML)","Laporan 2 (WPS)","Laporan 3 (CSV)"},"Laporan 1 (HTML)");
                switch (pilihan) {
                    case "Laporan 1 (HTML)":
                            htmlContent = new StringBuilder();
                            htmlContent.append(                             
                                "<tr class='isi'>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Rawat</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.RM</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Pasien</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>J.K.</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tgl.Lahir</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tanggal</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Informasi</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Riwayat Peyakit Sekarang</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Riwayat Penyakit Keluarga</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Riwayat Penyakit Dahulu</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Alergi Makan & Obat</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keadaan Umum</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kesadaran</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>T.D.</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nadi</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>R.R.</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>T.B.</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>B.B.</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Suhu</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>B.M.I.</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Klasifikasi B.M.I.</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>L.P.</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Risiko Berdasar L.P.</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Submandibula</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Axilla</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Supraklavikula</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Leher</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Inguinal</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Oedema</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Frontalis</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Maxilaris</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Rambut</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Palpebra</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Sklera</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Cornea</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Buta Warna</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Konjungtiva</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Lensa</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Pupil</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Menggunakan Kacamata</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Visus</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Luas Lapang Pandang</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Luas Lapang Pandang</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Lubang Telinga</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Daun Telinga</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Selaput Pendengaran</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Proc.Mastoideus</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Septum Nasi</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Lubang Hidung</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Sinus</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Bibir</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Gusi</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Gigi</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Caries</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Lidah</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Faring</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tonsil</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kelenjar Limfe</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kelenjar Gondok</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Gerakan Dada</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Vocal Fremitus</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Perkusi Dada</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Bunyi Napas</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Bunyi Tambahan</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Ictus Cordis</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Bunyi Jantung</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Batas</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Mamae</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Mamae</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Inspeksi</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Palpasi</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Hepar</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Perkusi Abdomen</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Auskultasi</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Limpa</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Costovertebral</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Scoliosis</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kondisi Kulit</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Penyakit Kulit</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Extrimitas Atas</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Extrimitas Atas</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Extrimitas Bawah</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Extrimitas Bawah</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Area Genitalia</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Area Genitalia</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Anus & Perianal</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Anus & Perianal</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Pemeriksaan Laboratorium</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Rontgen Thorax</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>EKG</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Spirometri</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Audiometri</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Treadmill</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Romberg Test</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Back Strength</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>ABI Tangan Kanan</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>ABI Tangan Kiri</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>ABI Kaki Kanan</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>ABI Kaki Kiri</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Lain-lain</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Merokok</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Alkohol</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kesimpulan</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Anjuran</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kode Dokter</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Dokter Penanggung Jawab</b></td>").append(
                                "</tr>"
                            );
                            for (i = 0; i < tabMode.getRowCount(); i++) {
                                htmlContent.append(
                                    "<tr class='isi'>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,0).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,1).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,2).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,3).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,4).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,5).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,6).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,7).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,8).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,9).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,10).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,11).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,12).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,13).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,14).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,15).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,16).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,17).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,18).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,19).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,20).toString()).append("</td>").append( 
                                        "<td valign='top'>").append(tbObat.getValueAt(i,21).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,22).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,23).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,24).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,25).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,26).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,27).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,28).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,29).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,30).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,31).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,32).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,33).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,34).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,35).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,36).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,37).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,38).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,39).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,40).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,41).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,42).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,43).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,44).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,45).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,46).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,47).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,48).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,49).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,50).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,51).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,52).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,53).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,54).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,55).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,56).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,57).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,58).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,59).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,60).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,61).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,62).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,63).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,64).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,65).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,66).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,67).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,68).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,69).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,70).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,71).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,72).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,73).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,74).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,75).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,76).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,77).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,78).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,79).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,80).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,81).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,82).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,83).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,84).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,85).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,86).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,87).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,88).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,89).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,90).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,91).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,92).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,93).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,94).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,95).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,96).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,97).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,98).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,99).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,100).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,101).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,102).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,103).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,104).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,105).toString()).append("</td>").append(
                                    "</tr>");
                            }
                            LoadHTML.setText(
                                "<html>"+
                                  "<table width='10000px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
                                   htmlContent.toString()+
                                  "</table>"+
                                "</html>"
                            );

                            f = new File("DataMCU.html");            
                            bw = new BufferedWriter(new FileWriter(f));            
                            bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                                        "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                                        "<table width='10000px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                            "<tr class='isi2'>"+
                                                "<td valign='top' align='center'>"+
                                                    "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                                    akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                                    akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                                    "<font size='2' face='Tahoma'>DATA PEMERIKSAAN MCU<br><br></font>"+        
                                                "</td>"+
                                           "</tr>"+
                                        "</table>")
                            );
                            bw.close();                         
                            Desktop.getDesktop().browse(f.toURI());
                        break;
                    case "Laporan 2 (WPS)":
                            htmlContent = new StringBuilder();
                            htmlContent.append(                             
                                "<tr class='isi'>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Rawat</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.RM</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Pasien</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>J.K.</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tgl.Lahir</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tanggal</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Informasi</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Riwayat Peyakit Sekarang</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Riwayat Penyakit Keluarga</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Riwayat Penyakit Dahulu</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Alergi Makan & Obat</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keadaan Umum</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kesadaran</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>T.D.</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nadi</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>R.R.</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>T.B.</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>B.B.</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Suhu</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>B.M.I.</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Klasifikasi B.M.I.</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>L.P.</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Risiko Berdasar L.P.</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Submandibula</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Axilla</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Supraklavikula</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Leher</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Inguinal</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Oedema</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Frontalis</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Maxilaris</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Rambut</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Palpebra</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Sklera</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Cornea</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Buta Warna</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Konjungtiva</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Lensa</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Pupil</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Menggunakan Kacamata</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Visus</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Luas Lapang Pandang</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Luas Lapang Pandang</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Lubang Telinga</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Daun Telinga</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Selaput Pendengaran</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Proc.Mastoideus</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Septum Nasi</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Lubang Hidung</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Sinus</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Bibir</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Gusi</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Gigi</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Caries</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Lidah</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Faring</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tonsil</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kelenjar Limfe</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kelenjar Gondok</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Gerakan Dada</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Vocal Fremitus</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Perkusi Dada</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Bunyi Napas</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Bunyi Tambahan</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Ictus Cordis</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Bunyi Jantung</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Batas</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Mamae</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Mamae</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Inspeksi</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Palpasi</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Hepar</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Perkusi Abdomen</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Auskultasi</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Limpa</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Costovertebral</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Scoliosis</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kondisi Kulit</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Penyakit Kulit</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Extrimitas Atas</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Extrimitas Atas</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Extrimitas Bawah</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Extrimitas Bawah</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Area Genitalia</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Area Genitalia</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Anus & Perianal</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Anus & Perianal</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Pemeriksaan Laboratorium</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Rontgen Thorax</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>EKG</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Spirometri</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Audiometri</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Treadmill</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Romberg Test</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Back Strength</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>ABI Tangan Kanan</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>ABI Tangan Kiri</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>ABI Kaki Kanan</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>ABI Kaki Kiri</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Lain-lain</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Merokok</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Alkohol</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kesimpulan</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Anjuran</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kode Dokter</b></td>").append(
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Dokter Penanggung Jawab</b></td>").append(
                                "</tr>"
                            );
                            for (i = 0; i < tabMode.getRowCount(); i++) {
                                htmlContent.append(
                                    "<tr class='isi'>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,0).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,1).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,2).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,3).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,4).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,5).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,6).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,7).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,8).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,9).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,10).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,11).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,12).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,13).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,14).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,15).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,16).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,17).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,18).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,19).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,20).toString()).append("</td>").append( 
                                        "<td valign='top'>").append(tbObat.getValueAt(i,21).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,22).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,23).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,24).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,25).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,26).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,27).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,28).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,29).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,30).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,31).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,32).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,33).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,34).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,35).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,36).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,37).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,38).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,39).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,40).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,41).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,42).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,43).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,44).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,45).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,46).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,47).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,48).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,49).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,50).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,51).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,52).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,53).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,54).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,55).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,56).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,57).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,58).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,59).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,60).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,61).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,62).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,63).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,64).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,65).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,66).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,67).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,68).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,69).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,70).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,71).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,72).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,73).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,74).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,75).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,76).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,77).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,78).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,79).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,80).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,81).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,82).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,83).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,84).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,85).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,86).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,87).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,88).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,89).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,90).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,91).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,92).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,93).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,94).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,95).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,96).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,97).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,98).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,99).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,100).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,101).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,102).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,103).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,104).toString()).append("</td>").append(
                                        "<td valign='top'>").append(tbObat.getValueAt(i,105).toString()).append("</td>").append(
                                    "</tr>");
                            }
                            LoadHTML.setText(
                                "<html>"+
                                  "<table width='10000px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
                                   htmlContent.toString()+
                                  "</table>"+
                                "</html>"
                            );

                            f = new File("DataMCU.wps");            
                            bw = new BufferedWriter(new FileWriter(f));            
                            bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                                        "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                                        "<table width='10000px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                            "<tr class='isi2'>"+
                                                "<td valign='top' align='center'>"+
                                                    "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                                    akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                                    akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                                    "<font size='2' face='Tahoma'>DATA PEMERIKSAAN MCU<br><br></font>"+        
                                                "</td>"+
                                           "</tr>"+
                                        "</table>")
                            );
                            bw.close();                         
                            Desktop.getDesktop().browse(f.toURI());
                        break;
                    case "Laporan 3 (CSV)":
                            htmlContent = new StringBuilder();
                            htmlContent.append(                             
                                "\"No.Rawat\";\"No.RM\";\"Nama Pasien\";\"J.K.\";\"Tgl.Lahir\";\"Tanggal\";\"Informasi\";\"Riwayat Peyakit Sekarang\";\"Riwayat Penyakit Keluargan\";\"Riwayat Penyakit Dahulu\";\"Alergi Makan & Obat\";\"Keadaan Umum\";\"Kesadaran\";\"T.D.n\";\"Nadin\";\"R.R.\";\"T.B.\";\"B.B.\";\"Suhu\";\"B.M.I.\";\"Klasifikasi B.M.I.\";\"L.P.\";\"Risiko Berdasar L.P.\";\"Submandibula\";\"Axilla\";\"Supraklavikula\";\"Leher\";\"Inguinal\";\"Oedema\";\"Frontalis\";\"Maxilaris\";\"Rambut\";\"Palpebra\";\"Sklera\";\"Cornea\";\"Buta Warna\";\"Konjungtiva\";\"Lensa\";\"Pupil\";\"Menggunakan Kacamata\";\"Visus\";\"Luas Lapang Pandang\";\"Keterangan Luas Lapang Pandang\";\"Lubang Telinga\";\"Daun Telinga\";\"Selaput Pendengaran\";\"Proc.Mastoideus\";\"Septum Nasi\";\"Lubang Hidung\";\"Sinus\";\"Bibir\";\"Gusi\";\"Gigi\";\"Caries\";\"Lidah\";\"Faring\";\"Tonsil\";\"Kelenjar Limfe\";\"Kelenjar Gondok\";\"Gerakan Dada\";\"Vocal Fremitus\";\"Perkusi Dada\";\"Bunyi Napas\";\"Bunyi Tambahan\";\"Ictus Cordis\";\"Bunyi Jantung\";\"Batas\";\"Mamae\";\"Keterangan Mamae\";\"Inspeksi\";\"Palpasi\";\"Hepar\";\"Perkusi Abdomen\";\"Auskultasi\";\"Limpa\";\"Costovertebral\";\"Scoliosis\";\"Kondisi Kulit\";\"Penyakit Kulit\";\"Extrimitas Atas\";\"Keterangan Extrimitas Atas\";\"Extrimitas Bawah\";\"Keterangan Extrimitas Bawah\";\"Area Genitalia\";\"Keterangan Area Genitalia\";\"Anus & Perianal\";\"Keterangan Anus & Perianal\";\"Pemeriksaan Laboratoriumn\";\"Rontgen Thorax\";\"EKG\";\"Spirometri\";\"Audiometri\";\"Treadmill\";\"Romberg Test\";\"Back Strength\";\"ABI Tangan Kanan\";\"ABI Tangan Kiri\";\"ABI Kaki Kanan\";\"ABI Kaki Kiri\";\"Lain-lainn\";\"Merokokn\";\"Alkohol\";\"Kesimpulann\";\"Anjuran\";\"Kode Dokter\";\"Nama Dokter Penanggung Jawab\"\n"
                            ); 
                            for (i = 0; i < tabMode.getRowCount(); i++) {
                                htmlContent.append("\"").append(tbObat.getValueAt(i,0).toString()).append("\";\"").append(tbObat.getValueAt(i,1).toString()).append("\";\"").append(tbObat.getValueAt(i,2).toString()).append("\";\"").append(tbObat.getValueAt(i,3).toString()).append("\";\"").append(tbObat.getValueAt(i,4).toString()).append("\";\"").append(tbObat.getValueAt(i,5).toString()).append("\";\"").append(tbObat.getValueAt(i,6).toString()).append("\";\"").append(tbObat.getValueAt(i,7).toString()).append("\";\"").append(tbObat.getValueAt(i,8).toString()).append("\";\"").append(tbObat.getValueAt(i,9).toString()).append("\";\"").append(tbObat.getValueAt(i,10).toString()).append("\";\"").append(tbObat.getValueAt(i,11).toString()).append("\";\"").append(tbObat.getValueAt(i,12).toString()).append("\";\"").append(tbObat.getValueAt(i,13).toString()).append("\";\"").append(tbObat.getValueAt(i,14).toString()).append("\";\"").append(tbObat.getValueAt(i,15).toString()).append("\";\"").append(tbObat.getValueAt(i,16).toString()).append("\";\"").append(tbObat.getValueAt(i,17).toString()).append("\";\"").append(tbObat.getValueAt(i,18).toString()).append("\";\"").append(tbObat.getValueAt(i,19).toString()).append("\";\"").append(tbObat.getValueAt(i,20).toString()).append("\";\"").append(tbObat.getValueAt(i,21).toString()).append("\";\"").append(tbObat.getValueAt(i,22).toString()).append("\";\"").append(tbObat.getValueAt(i,23).toString()).append("\";\"").append(tbObat.getValueAt(i,24).toString()).append("\";\"").append(tbObat.getValueAt(i,25).toString()).append("\";\"").append(tbObat.getValueAt(i,26).toString()).append("\";\"").append(tbObat.getValueAt(i,27).toString()).append("\";\"").append(tbObat.getValueAt(i,28).toString()).append("\";\"").append(tbObat.getValueAt(i,29).toString()).append("\";\"").append(tbObat.getValueAt(i,30).toString()).append("\";\"").append(tbObat.getValueAt(i,31).toString()).append("\";\"").append(tbObat.getValueAt(i,32).toString()).append("\";\"").append(tbObat.getValueAt(i,33).toString()).append("\";\"").append(tbObat.getValueAt(i,34).toString()).append("\";\"").append(tbObat.getValueAt(i,35).toString()).append("\";\"").append(tbObat.getValueAt(i,36).toString()).append("\";\"").append(tbObat.getValueAt(i,37).toString()).append("\";\"").append(tbObat.getValueAt(i,38).toString()).append("\";\"").append(tbObat.getValueAt(i,39).toString()).append("\";\"").append(tbObat.getValueAt(i,40).toString()).append("\";\"").append(tbObat.getValueAt(i,41).toString()).append("\";\"").append(tbObat.getValueAt(i,42).toString()).append("\";\"").append(tbObat.getValueAt(i,43).toString()).append("\";\"").append(tbObat.getValueAt(i,44).toString()).append("\";\"").append(tbObat.getValueAt(i,45).toString()).append("\";\"").append(tbObat.getValueAt(i,46).toString()).append("\";\"").append(tbObat.getValueAt(i,47).toString()).append("\";\"").append(tbObat.getValueAt(i,48).toString()).append("\";\"").append(tbObat.getValueAt(i,49).toString()).append("\";\"").append(tbObat.getValueAt(i,50).toString()).append("\";\"").append(tbObat.getValueAt(i,51).toString()).append("\";\"").append(tbObat.getValueAt(i,52).toString()).append("\";\"").append(tbObat.getValueAt(i,53).toString()).append("\";\"").append(tbObat.getValueAt(i,54).toString()).append("\";\"").append(tbObat.getValueAt(i,55).toString()).append("\";\"").append(tbObat.getValueAt(i,56).toString()).append("\";\"").append(tbObat.getValueAt(i,57).toString()).append("\";\"").append(tbObat.getValueAt(i,58).toString()).append("\";\"").append(tbObat.getValueAt(i,59).toString()).append("\";\"").append(tbObat.getValueAt(i,60).toString()).append("\";\"").append(tbObat.getValueAt(i,61).toString()).append("\";\"").append(tbObat.getValueAt(i,62).toString()).append("\";\"").append(tbObat.getValueAt(i,63).toString()).append("\";\"").append(tbObat.getValueAt(i,64).toString()).append("\";\"").append(tbObat.getValueAt(i,65).toString()).append("\";\"").append(tbObat.getValueAt(i,66).toString()).append("\";\"").append(tbObat.getValueAt(i,67).toString()).append("\";\"").append(tbObat.getValueAt(i,68).toString()).append("\";\"").append(tbObat.getValueAt(i,69).toString()).append("\";\"").append(tbObat.getValueAt(i,70).toString()).append("\";\"").append(tbObat.getValueAt(i,71).toString()).append("\";\"").append(tbObat.getValueAt(i,72).toString()).append("\";\"").append(tbObat.getValueAt(i,73).toString()).append("\";\"").append(tbObat.getValueAt(i,74).toString()).append("\";\"").append(tbObat.getValueAt(i,75).toString()).append("\";\"").append(tbObat.getValueAt(i,76).toString()).append("\";\"").append(tbObat.getValueAt(i,77).toString()).append("\";\"").append(tbObat.getValueAt(i,78).toString()).append("\";\"").append(tbObat.getValueAt(i,79).toString()).append("\";\"").append(tbObat.getValueAt(i,80).toString()).append("\";\"").append(tbObat.getValueAt(i,81).toString()).append("\";\"").append(tbObat.getValueAt(i,82).toString()).append("\";\"").append(tbObat.getValueAt(i,83).toString()).append("\";\"").append(tbObat.getValueAt(i,84).toString()).append("\";\"").append(tbObat.getValueAt(i,85).toString()).append("\";\"").append(tbObat.getValueAt(i,86).toString()).append("\";\"").append(tbObat.getValueAt(i,87).toString()).append("\";\"").append(tbObat.getValueAt(i,88).toString()).append("\";\"").append(tbObat.getValueAt(i,89).toString()).append("\";\"").append(tbObat.getValueAt(i,90).toString()).append("\";\"").append(tbObat.getValueAt(i,91).toString()).append("\";\"").append(tbObat.getValueAt(i,92).toString()).append("\";\"").append(tbObat.getValueAt(i,93).toString()).append("\";\"").append(tbObat.getValueAt(i,94).toString()).append("\";\"").append(tbObat.getValueAt(i,95).toString()).append("\";\"").append(tbObat.getValueAt(i,96).toString()).append("\";\"").append(tbObat.getValueAt(i,97).toString()).append("\";\"").append(tbObat.getValueAt(i,98).toString()).append("\";\"").append(tbObat.getValueAt(i,99).toString()).append("\";\"").append(tbObat.getValueAt(i,100).toString()).append("\";\"").append(tbObat.getValueAt(i,101).toString()).append("\";\"").append(tbObat.getValueAt(i,102).toString()).append("\";\"").append(tbObat.getValueAt(i,103).toString()).append("\";\"").append(tbObat.getValueAt(i,104).toString()).append("\";\"").append(tbObat.getValueAt(i,104).toString()).append("\"\n");
                            }
                            f = new File("DataMCU.csv");            
                            bw = new BufferedWriter(new FileWriter(f));            
                            bw.write(htmlContent.toString());
                            bw.close();                         
                            Desktop.getDesktop().browse(f.toURI());
                        break; 
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

    private void BtnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokterActionPerformed
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setAlwaysOnTop(false);
        dokter.setVisible(true);
    }//GEN-LAST:event_BtnDokterActionPerformed

    private void BtnDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokterKeyPressed
        Valid.pindah(evt,Anjuran,Informasi);
    }//GEN-LAST:event_BtnDokterKeyPressed

    private void BBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BBKeyPressed
        Valid.pindah(evt,TB,Suhu);
    }//GEN-LAST:event_BBKeyPressed

    private void NadiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NadiKeyPressed
        Valid.pindah(evt,TD,RR);
    }//GEN-LAST:event_NadiKeyPressed

    private void SuhuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SuhuKeyPressed
        Valid.pindah(evt,BB,LP);
    }//GEN-LAST:event_SuhuKeyPressed

    private void TDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TDKeyPressed
        Valid.pindah(evt,Kesadaran,Nadi);
    }//GEN-LAST:event_TDKeyPressed

    private void RRKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RRKeyPressed
        Valid.pindah(evt,Nadi,TB);
    }//GEN-LAST:event_RRKeyPressed

    private void InformasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_InformasiKeyPressed
        Valid.pindah(evt,TglAsuhan,RiwayatPenyakitSekarang);
    }//GEN-LAST:event_InformasiKeyPressed

    private void TglAsuhanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglAsuhanKeyPressed
        Valid.pindah2(evt,KdDokter,Informasi);
    }//GEN-LAST:event_TglAsuhanKeyPressed

    private void TBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TBKeyPressed
        Valid.pindah(evt,RR,BB);
    }//GEN-LAST:event_TBKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        
    }//GEN-LAST:event_formWindowOpened

    private void RiwayatAlergiMakananKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RiwayatAlergiMakananKeyPressed
        Valid.pindah(evt,RiwayatPenyakitDahulu,KeadaanUmum);
    }//GEN-LAST:event_RiwayatAlergiMakananKeyPressed

    private void KeadaanUmumKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeadaanUmumKeyPressed
        Valid.pindah(evt,RiwayatAlergiMakanan,Kesadaran);
    }//GEN-LAST:event_KeadaanUmumKeyPressed

    private void PemeriksaanLaboratoriumKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PemeriksaanLaboratoriumKeyPressed
        Valid.pindah2(evt,KeteranganAnus,RongsenThorax);
    }//GEN-LAST:event_PemeriksaanLaboratoriumKeyPressed

    private void RongsenThoraxKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RongsenThoraxKeyPressed
        Valid.pindah2(evt,PemeriksaanLaboratorium,EKG);
    }//GEN-LAST:event_RongsenThoraxKeyPressed

    private void EKGKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EKGKeyPressed
        Valid.pindah2(evt,RongsenThorax,Spirometri);
    }//GEN-LAST:event_EKGKeyPressed

    private void KesimpulanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KesimpulanKeyPressed
        Valid.pindah2(evt,Alkohol,Anjuran);
    }//GEN-LAST:event_KesimpulanKeyPressed

    private void AnjuranKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AnjuranKeyPressed
        Valid.pindah2(evt,Kesimpulan,BtnSimpan);
    }//GEN-LAST:event_AnjuranKeyPressed

    private void MnPenilaianMCUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPenilaianMCUActionPerformed
        if(tbObat.getSelectedRow()>-1){
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
            String finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",tbObat.getValueAt(tbObat.getSelectedRow(),104).toString());
            param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbObat.getValueAt(tbObat.getSelectedRow(),105).toString()+"\nID "+(finger.equals("")?tbObat.getValueAt(tbObat.getSelectedRow(),104).toString():finger)+"\n"+Valid.SetTgl3(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString()));
            Valid.MyReportqry("rptCetakPenilaianAwalMCU.jasper","report","::[ Laporan Penilaian Awal MCU ]::",
                "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,pasien.stts_nikah,penilaian_mcu.tanggal,"+
                "concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab,', ',propinsi.nm_prop) as alamat,"+
                "penilaian_mcu.informasi,penilaian_mcu.rps,penilaian_mcu.rpk,penilaian_mcu.rpd,penilaian_mcu.alergi,penilaian_mcu.keadaan,penilaian_mcu.kesadaran,penilaian_mcu.td,"+
                "penilaian_mcu.nadi,penilaian_mcu.rr,penilaian_mcu.tb,penilaian_mcu.bb,penilaian_mcu.suhu,penilaian_mcu.bmi,penilaian_mcu.kasifikasi_bmi,penilaian_mcu.lingkar_pinggang,"+
                "penilaian_mcu.risiko_lingkar_pinggang,penilaian_mcu.submandibula,penilaian_mcu.axilla,penilaian_mcu.supraklavikula,penilaian_mcu.leher,penilaian_mcu.inguinal,"+
                "penilaian_mcu.oedema,penilaian_mcu.sinus_frontalis,penilaian_mcu.sinus_maxilaris,penilaian_mcu.rambut,penilaian_mcu.palpebra,penilaian_mcu.sklera,penilaian_mcu.cornea,"+
                "penilaian_mcu.buta_warna,penilaian_mcu.konjungtiva,penilaian_mcu.lensa,penilaian_mcu.pupil,penilaian_mcu.menggunakan_kacamata,penilaian_mcu.visus,penilaian_mcu.luas_lapang_pandang,"+
                "penilaian_mcu.keterangan_luas_lapang_pandang,penilaian_mcu.lubang_telinga,penilaian_mcu.daun_telinga,penilaian_mcu.selaput_pendengaran,penilaian_mcu.proc_mastoideus,"+
                "penilaian_mcu.septum_nasi,penilaian_mcu.lubang_hidung,penilaian_mcu.sinus,penilaian_mcu.bibir,penilaian_mcu.gusi,penilaian_mcu.gigi,penilaian_mcu.caries,penilaian_mcu.lidah,"+
                "penilaian_mcu.faring,penilaian_mcu.tonsil,penilaian_mcu.kelenjar_limfe,penilaian_mcu.kelenjar_gondok,penilaian_mcu.gerakan_dada,penilaian_mcu.vocal_femitus,"+
                "penilaian_mcu.perkusi_dada,penilaian_mcu.bunyi_napas,penilaian_mcu.bunyi_tambahan,penilaian_mcu.ictus_cordis,penilaian_mcu.bunyi_jantung,penilaian_mcu.batas,penilaian_mcu.mamae,"+
                "penilaian_mcu.keterangan_mamae,penilaian_mcu.inspeksi,penilaian_mcu.palpasi,penilaian_mcu.hepar,penilaian_mcu.perkusi_abdomen,penilaian_mcu.auskultasi,penilaian_mcu.limpa,"+
                "penilaian_mcu.costovertebral,penilaian_mcu.scoliosis,penilaian_mcu.kondisi_kulit,penilaian_mcu.penyakit_kulit,penilaian_mcu.ekstrimitas_atas,penilaian_mcu.ekstrimitas_atas_ket,"+
                "penilaian_mcu.ekstrimitas_bawah,penilaian_mcu.ekstrimitas_bawah_ket,penilaian_mcu.area_genitalia,penilaian_mcu.keterangan_area_genitalia,penilaian_mcu.anus_perianal,"+
                "penilaian_mcu.keterangan_anus_perianal,penilaian_mcu.laborat,penilaian_mcu.radiologi,penilaian_mcu.ekg,penilaian_mcu.spirometri,penilaian_mcu.audiometri,penilaian_mcu.treadmill,"+
                "penilaian_mcu.romberg_test,penilaian_mcu.back_strength,penilaian_mcu.abi_tangan_kanan,penilaian_mcu.abi_tangan_kiri,penilaian_mcu.abi_kaki_kanan,penilaian_mcu.abi_kaki_kiri,"+
                "penilaian_mcu.lainlain,penilaian_mcu.merokok,penilaian_mcu.alkohol,penilaian_mcu.kesimpulan,penilaian_mcu.anjuran,penilaian_mcu.kd_dokter,dokter.nm_dokter "+
                "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join penilaian_mcu on reg_periksa.no_rawat=penilaian_mcu.no_rawat "+
                "inner join kelurahan on pasien.kd_kel=kelurahan.kd_kel "+
                "inner join kecamatan on pasien.kd_kec=kecamatan.kd_kec "+
                "inner join kabupaten on pasien.kd_kab=kabupaten.kd_kab "+
                "inner join propinsi on pasien.kd_prop=propinsi.kd_prop "+
                "inner join dokter on penilaian_mcu.kd_dokter=dokter.kd_dokter where reg_periksa.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'",param);
        }
    }//GEN-LAST:event_MnPenilaianMCUActionPerformed

    private void RiwayatPenyakitSekarangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RiwayatPenyakitSekarangKeyPressed
        Valid.pindah2(evt,Informasi,RiwayatPenyakitKeluarga);
    }//GEN-LAST:event_RiwayatPenyakitSekarangKeyPressed

    private void RiwayatPenyakitKeluargaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RiwayatPenyakitKeluargaKeyPressed
        Valid.pindah2(evt,RiwayatPenyakitSekarang,RiwayatPenyakitDahulu);
    }//GEN-LAST:event_RiwayatPenyakitKeluargaKeyPressed

    private void RiwayatPenyakitDahuluKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RiwayatPenyakitDahuluKeyPressed
        Valid.pindah2(evt,RiwayatPenyakitKeluarga,RiwayatAlergiMakanan);
    }//GEN-LAST:event_RiwayatPenyakitDahuluKeyPressed

    private void MerokokKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MerokokKeyPressed
        Valid.pindah(evt,Lainlain,Alkohol);
    }//GEN-LAST:event_MerokokKeyPressed

    private void AlkoholKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlkoholKeyPressed
        Valid.pindah(evt,Merokok,Kesimpulan);
    }//GEN-LAST:event_AlkoholKeyPressed

    private void KesadaranKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KesadaranKeyPressed
        Valid.pindah(evt,KeadaanUmum,TD);
    }//GEN-LAST:event_KesadaranKeyPressed

    private void SubmandibulaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SubmandibulaKeyPressed
        Valid.pindah(evt,LP,Axila);
    }//GEN-LAST:event_SubmandibulaKeyPressed

    private void AxilaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AxilaKeyPressed
        Valid.pindah(evt,Submandibula,Supraklavikula);
    }//GEN-LAST:event_AxilaKeyPressed

    private void SupraklavikulaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SupraklavikulaKeyPressed
        Valid.pindah(evt,Axila,Leher);
    }//GEN-LAST:event_SupraklavikulaKeyPressed

    private void LeherKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LeherKeyPressed
        Valid.pindah(evt,Supraklavikula,Inguinal);
    }//GEN-LAST:event_LeherKeyPressed

    private void InguinalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_InguinalKeyPressed
        Valid.pindah(evt,Leher,Oedema);
    }//GEN-LAST:event_InguinalKeyPressed

    private void OedemaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_OedemaKeyPressed
        Valid.pindah(evt,Inguinal,NyeriTekanSinusFrontalis);
    }//GEN-LAST:event_OedemaKeyPressed

    private void NyeriTekanSinusFrontalisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NyeriTekanSinusFrontalisKeyPressed
        Valid.pindah(evt,Oedema,NyeriTekananSinusMaxilaris);
    }//GEN-LAST:event_NyeriTekanSinusFrontalisKeyPressed

    private void NyeriTekananSinusMaxilarisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NyeriTekananSinusMaxilarisKeyPressed
        Valid.pindah(evt,NyeriTekanSinusFrontalis,Rambut);
    }//GEN-LAST:event_NyeriTekananSinusMaxilarisKeyPressed

    private void PalpebraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PalpebraKeyPressed
        Valid.pindah(evt,Rambut,Sklera);
    }//GEN-LAST:event_PalpebraKeyPressed

    private void SkleraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkleraKeyPressed
        Valid.pindah(evt,Palpebra,Cornea);
    }//GEN-LAST:event_SkleraKeyPressed

    private void CorneaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CorneaKeyPressed
        Valid.pindah(evt,Sklera,TestButaWarna);
    }//GEN-LAST:event_CorneaKeyPressed

    private void TestButaWarnaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TestButaWarnaKeyPressed
        Valid.pindah(evt,Cornea,Konjungtiva);
    }//GEN-LAST:event_TestButaWarnaKeyPressed

    private void KonjungtivaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KonjungtivaKeyPressed
        Valid.pindah(evt,TestButaWarna,Lensa);
    }//GEN-LAST:event_KonjungtivaKeyPressed

    private void LensaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LensaKeyPressed
        Valid.pindah(evt,Konjungtiva,Pupil);
    }//GEN-LAST:event_LensaKeyPressed

    private void PupilKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PupilKeyPressed
        Valid.pindah(evt,Lensa,MenggunakanKacamata);
    }//GEN-LAST:event_PupilKeyPressed

    private void LubangTelingaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LubangTelingaKeyPressed
        Valid.pindah(evt,KeteranganLuasLapangPandang,DaunTelinga);
    }//GEN-LAST:event_LubangTelingaKeyPressed

    private void DaunTelingaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DaunTelingaKeyPressed
        Valid.pindah(evt,LubangTelinga,SelaputPendengaran);
    }//GEN-LAST:event_DaunTelingaKeyPressed

    private void SelaputPendengaranKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SelaputPendengaranKeyPressed
        Valid.pindah(evt,DaunTelinga,NyeriMastoideus);
    }//GEN-LAST:event_SelaputPendengaranKeyPressed

    private void NyeriMastoideusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NyeriMastoideusKeyPressed
        Valid.pindah(evt,SelaputPendengaran,SeptumNasi);
    }//GEN-LAST:event_NyeriMastoideusKeyPressed

    private void SeptumNasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SeptumNasiKeyPressed
        Valid.pindah(evt,NyeriMastoideus,LubangHidung);
    }//GEN-LAST:event_SeptumNasiKeyPressed

    private void LubangHidungKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LubangHidungKeyPressed
        Valid.pindah(evt,SeptumNasi,Sinus);
    }//GEN-LAST:event_LubangHidungKeyPressed

    private void BibirKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BibirKeyPressed
        Valid.pindah(evt,Sinus,Gusi);
    }//GEN-LAST:event_BibirKeyPressed

    private void CariesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CariesKeyPressed
        Valid.pindah(evt,Gigi,Lidah);
    }//GEN-LAST:event_CariesKeyPressed

    private void LidahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LidahKeyPressed
        Valid.pindah(evt,Caries,Faring);
    }//GEN-LAST:event_LidahKeyPressed

    private void FaringKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FaringKeyPressed
        Valid.pindah(evt,Lidah,Tonsil);
    }//GEN-LAST:event_FaringKeyPressed

    private void TonsilKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TonsilKeyPressed
        Valid.pindah(evt,Faring,KelenjarLimfe);
    }//GEN-LAST:event_TonsilKeyPressed

    private void KelenjarLimfeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KelenjarLimfeKeyPressed
        Valid.pindah(evt,Tonsil,KelenjarGondok);
    }//GEN-LAST:event_KelenjarLimfeKeyPressed

    private void KelenjarGondokKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KelenjarGondokKeyPressed
        Valid.pindah(evt,KelenjarLimfe,GerakanDada);
    }//GEN-LAST:event_KelenjarGondokKeyPressed

    private void GerakanDadaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GerakanDadaKeyPressed
        Valid.pindah(evt,KelenjarGondok,VocalFremitus);
    }//GEN-LAST:event_GerakanDadaKeyPressed

    private void VocalFremitusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_VocalFremitusKeyPressed
        Valid.pindah(evt,GerakanDada,PerkusiDada);
    }//GEN-LAST:event_VocalFremitusKeyPressed

    private void PerkusiDadaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PerkusiDadaKeyPressed
        Valid.pindah(evt,VocalFremitus,BunyiNapas);
    }//GEN-LAST:event_PerkusiDadaKeyPressed

    private void BunyiNapasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BunyiNapasKeyPressed
        Valid.pindah(evt,PerkusiDada,BunyiTambahan);
    }//GEN-LAST:event_BunyiNapasKeyPressed

    private void BunyiTambahanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BunyiTambahanKeyPressed
        Valid.pindah(evt,BunyiNapas,IctusCordis);
    }//GEN-LAST:event_BunyiTambahanKeyPressed

    private void IctusCordisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_IctusCordisKeyPressed
        Valid.pindah(evt,BunyiTambahan,BunyiJantung);
    }//GEN-LAST:event_IctusCordisKeyPressed

    private void BunyiJantungKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BunyiJantungKeyPressed
        Valid.pindah(evt,IctusCordis,Batas);
    }//GEN-LAST:event_BunyiJantungKeyPressed

    private void BatasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BatasKeyPressed
        Valid.pindah(evt,BunyiJantung,Mamae);
    }//GEN-LAST:event_BatasKeyPressed

    private void InspeksiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_InspeksiKeyPressed
        Valid.pindah(evt,KeteranganMamae,Palpasi);
    }//GEN-LAST:event_InspeksiKeyPressed

    private void PalpasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PalpasiKeyPressed
        Valid.pindah(evt,Inspeksi,Hepar);
    }//GEN-LAST:event_PalpasiKeyPressed

    private void HeparKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HeparKeyPressed
        Valid.pindah(evt,Palpasi,PerkusiAbdomen);
    }//GEN-LAST:event_HeparKeyPressed

    private void PerkusiAbdomenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PerkusiAbdomenKeyPressed
        Valid.pindah(evt,Hepar,Auskultasi);
    }//GEN-LAST:event_PerkusiAbdomenKeyPressed

    private void AuskultasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AuskultasiKeyPressed
        Valid.pindah(evt,PerkusiAbdomen,Limpa);
    }//GEN-LAST:event_AuskultasiKeyPressed

    private void LimpaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LimpaKeyPressed
        Valid.pindah(evt,Auskultasi,NyeriKtok);
    }//GEN-LAST:event_LimpaKeyPressed

    private void NyeriKtokKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NyeriKtokKeyPressed
        Valid.pindah(evt,Limpa,Scoliosis);
    }//GEN-LAST:event_NyeriKtokKeyPressed

    private void KulitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KulitKeyPressed
        Valid.pindah(evt,Scoliosis,PenyakitKulit);
    }//GEN-LAST:event_KulitKeyPressed

    private void ExtremitasAtasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ExtremitasAtasKeyPressed
        Valid.pindah(evt,PenyakitKulit,KetExtremitasAtas);
    }//GEN-LAST:event_ExtremitasAtasKeyPressed

    private void KetExtremitasAtasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetExtremitasAtasKeyPressed
        Valid.pindah(evt,ExtremitasAtas,ExtremitasBawah);
    }//GEN-LAST:event_KetExtremitasAtasKeyPressed

    private void ExtremitasBawahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ExtremitasBawahKeyPressed
        Valid.pindah(evt,KetExtremitasAtas,KetExtremitasBawah);
    }//GEN-LAST:event_ExtremitasBawahKeyPressed

    private void KetExtremitasBawahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetExtremitasBawahKeyPressed
        Valid.pindah(evt,ExtremitasBawah,AreaGenitalia);
    }//GEN-LAST:event_KetExtremitasBawahKeyPressed

    private void SpirometriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SpirometriKeyPressed
        Valid.pindah2(evt,EKG,Audiometri);
    }//GEN-LAST:event_SpirometriKeyPressed

    private void AudiometriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AudiometriKeyPressed
        Valid.pindah2(evt,Spirometri,Treadmill);
    }//GEN-LAST:event_AudiometriKeyPressed

    private void TreadmillKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TreadmillKeyPressed
        Valid.pindah2(evt,Audiometri,Romberg);
    }//GEN-LAST:event_TreadmillKeyPressed

    private void LainlainKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LainlainKeyPressed
        Valid.pindah2(evt,AbiKakiKiri,Merokok);
    }//GEN-LAST:event_LainlainKeyPressed

    private void SinusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SinusKeyPressed
        Valid.pindah(evt,LubangHidung,Bibir);
    }//GEN-LAST:event_SinusKeyPressed

    private void BtnDokter3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokter3ActionPerformed
        if(TNoRw.getText().equals("")&&TNoRM.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Pasien masih kosong...!!!");
        }else{
            RMCariHasilLaborat carilaborat=new RMCariHasilLaborat(null,false);
            carilaborat.getTable().addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {}
                @Override
                public void keyPressed(KeyEvent e) {
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        if(carilaborat.getTable().getSelectedRow()!= -1){
                            PemeriksaanLaboratorium.append(carilaborat.getTable().getValueAt(carilaborat.getTable().getSelectedRow(),3).toString()+", ");
                            PemeriksaanLaboratorium.requestFocus();
                        }
                    }
                }
                @Override
                public void keyReleased(KeyEvent e) {}
            });

            carilaborat.BtnKeluar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    for (i= 0; i < carilaborat.getTable().getRowCount(); i++) {
                        if(carilaborat.getTable().getValueAt(i,0).toString().equals("true")){
                            PemeriksaanLaboratorium.append(carilaborat.getTable().getValueAt(i,3).toString()+", ");
                        }
                    }
                    PemeriksaanLaboratorium.requestFocus();
                }
            });
            carilaborat.setNoRawat(TNoRw.getText());
            carilaborat.tampil();
            carilaborat.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            carilaborat.setLocationRelativeTo(internalFrame1);
            carilaborat.setVisible(true);
        }
    }//GEN-LAST:event_BtnDokter3ActionPerformed

    private void BtnDokter2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokter2ActionPerformed
        if(TNoRw.getText().equals("")&&TNoRM.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Pasien masih kosong...!!!");
        }else{
            RMCariHasilRadiologi cariradiologi=new RMCariHasilRadiologi(null,false);
            cariradiologi.addWindowListener(new WindowListener() {
                @Override
                public void windowOpened(WindowEvent e) {}
                @Override
                public void windowClosing(WindowEvent e) {}
                @Override
                public void windowClosed(WindowEvent e) {
                    if(cariradiologi.getTable().getSelectedRow()!= -1){
                        RongsenThorax.append(cariradiologi.getTable().getValueAt(cariradiologi.getTable().getSelectedRow(),2).toString()+", ");
                        RongsenThorax.requestFocus();
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
            cariradiologi.setNoRawat(TNoRw.getText());
            cariradiologi.tampil();
            cariradiologi.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            cariradiologi.setLocationRelativeTo(internalFrame1);
            cariradiologi.setVisible(true);
        }
    }//GEN-LAST:event_BtnDokter2ActionPerformed

    private void ScoliosisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ScoliosisKeyPressed
        Valid.pindah(evt,NyeriKtok,Kulit);
    }//GEN-LAST:event_ScoliosisKeyPressed

    private void LPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LPKeyPressed
        Valid.pindah(evt,Suhu,Submandibula);
    }//GEN-LAST:event_LPKeyPressed

    private void MenggunakanKacamataKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MenggunakanKacamataKeyPressed
        Valid.pindah(evt,Pupil,Visus);
    }//GEN-LAST:event_MenggunakanKacamataKeyPressed

    private void GusiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GusiKeyPressed
        Valid.pindah(evt,Bibir,Gigi);
    }//GEN-LAST:event_GusiKeyPressed

    private void GigiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GigiKeyPressed
        Valid.pindah(evt,Gusi,Caries);
    }//GEN-LAST:event_GigiKeyPressed

    private void LuasLapangPandangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LuasLapangPandangKeyPressed
        Valid.pindah(evt,Visus,KeteranganLuasLapangPandang);
    }//GEN-LAST:event_LuasLapangPandangKeyPressed

    private void MamaeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MamaeKeyPressed
        Valid.pindah(evt,Batas,KeteranganMamae);
    }//GEN-LAST:event_MamaeKeyPressed

    private void AreaGenitaliaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AreaGenitaliaKeyPressed
        Valid.pindah(evt,KetExtremitasBawah,KeteranganAreaGenitalia);
    }//GEN-LAST:event_AreaGenitaliaKeyPressed

    private void KeteranganAreaGenitaliaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganAreaGenitaliaKeyPressed
        Valid.pindah(evt,AreaGenitalia,Anus);
    }//GEN-LAST:event_KeteranganAreaGenitaliaKeyPressed

    private void AnusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AnusKeyPressed
        Valid.pindah(evt,KeteranganAreaGenitalia,KeteranganAnus);
    }//GEN-LAST:event_AnusKeyPressed

    private void KeteranganAnusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganAnusKeyPressed
        Valid.pindah(evt,Anus,PemeriksaanLaboratorium);
    }//GEN-LAST:event_KeteranganAnusKeyPressed

    private void RombergKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RombergKeyPressed
        Valid.pindah2(evt,Treadmill,Backstrength);
    }//GEN-LAST:event_RombergKeyPressed

    private void BackstrengthKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BackstrengthKeyPressed
        Valid.pindah2(evt,Romberg,AbiTanganKanan);
    }//GEN-LAST:event_BackstrengthKeyPressed

    private void AbiTanganKananKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AbiTanganKananKeyPressed
        Valid.pindah2(evt,Backstrength,AbiTanganKiri);
    }//GEN-LAST:event_AbiTanganKananKeyPressed

    private void AbiTanganKiriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AbiTanganKiriKeyPressed
        Valid.pindah2(evt,Backstrength,AbiKakiKanan);
    }//GEN-LAST:event_AbiTanganKiriKeyPressed

    private void AbiKakiKananKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AbiKakiKananKeyPressed
        Valid.pindah2(evt,AbiTanganKiri,AbiKakiKiri);
    }//GEN-LAST:event_AbiKakiKananKeyPressed

    private void AbiKakiKiriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AbiKakiKiriKeyPressed
        Valid.pindah2(evt,AbiTanganKanan,Lainlain);
    }//GEN-LAST:event_AbiKakiKiriKeyPressed

    private void RambutKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RambutKeyPressed
        Valid.pindah(evt,NyeriTekananSinusMaxilaris,Palpebra);
    }//GEN-LAST:event_RambutKeyPressed

    private void VisusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_VisusKeyPressed
        Valid.pindah(evt,MenggunakanKacamata,LuasLapangPandang);
    }//GEN-LAST:event_VisusKeyPressed

    private void KeteranganLuasLapangPandangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganLuasLapangPandangKeyPressed
        Valid.pindah(evt,LuasLapangPandang,LubangTelinga);
    }//GEN-LAST:event_KeteranganLuasLapangPandangKeyPressed

    private void KeteranganMamaeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganMamaeKeyPressed
        Valid.pindah(evt,Mamae,Inspeksi);
    }//GEN-LAST:event_KeteranganMamaeKeyPressed

    private void PenyakitKulitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenyakitKulitKeyPressed
        Valid.pindah(evt,Kulit,ExtremitasAtas);
    }//GEN-LAST:event_PenyakitKulitKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMMCU dialog = new RMMCU(new javax.swing.JFrame(), true);
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
    private widget.TextArea AbiKakiKanan;
    private widget.TextArea AbiKakiKiri;
    private widget.TextArea AbiTanganKanan;
    private widget.TextArea AbiTanganKiri;
    private widget.TextBox Alkohol;
    private widget.TextArea Anjuran;
    private widget.ComboBox Anus;
    private widget.ComboBox AreaGenitalia;
    private widget.TextArea Audiometri;
    private widget.ComboBox Auskultasi;
    private widget.ComboBox Axila;
    private widget.TextBox BB;
    private widget.TextArea Backstrength;
    private widget.ComboBox Batas;
    private widget.ComboBox Bibir;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnDokter;
    private widget.Button BtnDokter2;
    private widget.Button BtnDokter3;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.ComboBox BunyiJantung;
    private widget.ComboBox BunyiNapas;
    private widget.ComboBox BunyiTambahan;
    private widget.ComboBox Caries;
    private widget.ComboBox Cornea;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.ComboBox DaunTelinga;
    private widget.TextArea EKG;
    private widget.ComboBox ExtremitasAtas;
    private widget.ComboBox ExtremitasBawah;
    private widget.ComboBox Faring;
    private widget.PanelBiasa FormInput;
    private widget.ComboBox GerakanDada;
    private widget.ComboBox Gigi;
    private widget.ComboBox Gusi;
    private widget.ComboBox Hepar;
    private widget.TextBox IMT;
    private widget.ComboBox IctusCordis;
    private widget.ComboBox Informasi;
    private widget.ComboBox Inguinal;
    private widget.ComboBox Inspeksi;
    private widget.TextBox Jk;
    private widget.TextBox KdDokter;
    private widget.ComboBox KeadaanUmum;
    private widget.ComboBox KelenjarGondok;
    private widget.ComboBox KelenjarLimfe;
    private widget.ComboBox Kesadaran;
    private widget.TextArea Kesimpulan;
    private widget.TextBox KetExtremitasAtas;
    private widget.TextBox KetExtremitasBawah;
    private widget.TextBox KeteranganAnus;
    private widget.TextBox KeteranganAreaGenitalia;
    private widget.TextBox KeteranganLuasLapangPandang;
    private widget.TextBox KeteranganMamae;
    private widget.TextBox KlasifikasiIMT;
    private widget.ComboBox Konjungtiva;
    private widget.ComboBox Kulit;
    private widget.Label LCount;
    private widget.TextBox LP;
    private widget.TextArea Lainlain;
    private widget.ComboBox Leher;
    private widget.ComboBox Lensa;
    private widget.ComboBox Lidah;
    private widget.ComboBox Limpa;
    private widget.editorpane LoadHTML;
    private widget.ComboBox LuasLapangPandang;
    private widget.ComboBox LubangHidung;
    private widget.ComboBox LubangTelinga;
    private widget.ComboBox Mamae;
    private widget.ComboBox MenggunakanKacamata;
    private widget.TextBox Merokok;
    private javax.swing.JMenuItem MnPenilaianMCU;
    private widget.TextBox Nadi;
    private widget.TextBox NmDokter;
    private widget.ComboBox NyeriKtok;
    private widget.ComboBox NyeriMastoideus;
    private widget.ComboBox NyeriTekanSinusFrontalis;
    private widget.ComboBox NyeriTekananSinusMaxilaris;
    private widget.ComboBox Oedema;
    private widget.ComboBox Palpasi;
    private widget.ComboBox Palpebra;
    private widget.TextArea PemeriksaanLaboratorium;
    private widget.TextBox PenyakitKulit;
    private widget.ComboBox PerkusiAbdomen;
    private widget.ComboBox PerkusiDada;
    private widget.ComboBox Pupil;
    private widget.TextBox RR;
    private widget.TextBox Rambut;
    private widget.TextBox RisikoLP;
    private widget.TextBox RiwayatAlergiMakanan;
    private widget.TextArea RiwayatPenyakitDahulu;
    private widget.TextArea RiwayatPenyakitKeluarga;
    private widget.TextArea RiwayatPenyakitSekarang;
    private widget.TextArea Romberg;
    private widget.TextArea RongsenThorax;
    private widget.ComboBox Scoliosis;
    private widget.ScrollPane Scroll;
    private widget.ComboBox SelaputPendengaran;
    private widget.ComboBox SeptumNasi;
    private widget.ComboBox Sinus;
    private widget.ComboBox Sklera;
    private widget.TextArea Spirometri;
    private widget.ComboBox Submandibula;
    private widget.TextBox Suhu;
    private widget.ComboBox Supraklavikula;
    private widget.TextBox TB;
    private widget.TextBox TCari;
    private widget.TextBox TD;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private javax.swing.JTabbedPane TabRawat;
    private widget.TextBox TanggalRegistrasi;
    private widget.ComboBox TestButaWarna;
    private widget.Tanggal TglAsuhan;
    private widget.TextBox TglLahir;
    private widget.ComboBox Tonsil;
    private widget.TextArea Treadmill;
    private widget.TextBox Visus;
    private widget.ComboBox VocalFremitus;
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
    private widget.Label jLabel14;
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
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator15;
    private javax.swing.JSeparator jSeparator16;
    private javax.swing.JSeparator jSeparator17;
    private javax.swing.JSeparator jSeparator18;
    private javax.swing.JSeparator jSeparator19;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator20;
    private javax.swing.JSeparator jSeparator21;
    private javax.swing.JSeparator jSeparator22;
    private javax.swing.JSeparator jSeparator23;
    private javax.swing.JSeparator jSeparator24;
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
    private widget.ScrollPane scrollPane10;
    private widget.ScrollPane scrollPane11;
    private widget.ScrollPane scrollPane12;
    private widget.ScrollPane scrollPane13;
    private widget.ScrollPane scrollPane14;
    private widget.ScrollPane scrollPane15;
    private widget.ScrollPane scrollPane16;
    private widget.ScrollPane scrollPane17;
    private widget.ScrollPane scrollPane18;
    private widget.ScrollPane scrollPane19;
    private widget.ScrollPane scrollPane2;
    private widget.ScrollPane scrollPane20;
    private widget.ScrollPane scrollPane21;
    private widget.ScrollPane scrollPane22;
    private widget.ScrollPane scrollPane23;
    private widget.ScrollPane scrollPane24;
    private widget.ScrollPane scrollPane3;
    private widget.ScrollPane scrollPane4;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            if(TCari.getText().equals("")){
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penilaian_mcu.tanggal,"+
                        "penilaian_mcu.informasi,penilaian_mcu.rps,penilaian_mcu.rpk,penilaian_mcu.rpd,penilaian_mcu.alergi,penilaian_mcu.keadaan,penilaian_mcu.kesadaran,penilaian_mcu.td,"+
                        "penilaian_mcu.nadi,penilaian_mcu.rr,penilaian_mcu.tb,penilaian_mcu.bb,penilaian_mcu.suhu,penilaian_mcu.bmi,penilaian_mcu.kasifikasi_bmi,penilaian_mcu.lingkar_pinggang,"+
                        "penilaian_mcu.risiko_lingkar_pinggang,penilaian_mcu.submandibula,penilaian_mcu.axilla,penilaian_mcu.supraklavikula,penilaian_mcu.leher,penilaian_mcu.inguinal,"+
                        "penilaian_mcu.oedema,penilaian_mcu.sinus_frontalis,penilaian_mcu.sinus_maxilaris,penilaian_mcu.rambut,penilaian_mcu.palpebra,penilaian_mcu.sklera,penilaian_mcu.cornea,"+
                        "penilaian_mcu.buta_warna,penilaian_mcu.konjungtiva,penilaian_mcu.lensa,penilaian_mcu.pupil,penilaian_mcu.menggunakan_kacamata,penilaian_mcu.visus,penilaian_mcu.luas_lapang_pandang,"+
                        "penilaian_mcu.keterangan_luas_lapang_pandang,penilaian_mcu.lubang_telinga,penilaian_mcu.daun_telinga,penilaian_mcu.selaput_pendengaran,penilaian_mcu.proc_mastoideus,"+
                        "penilaian_mcu.septum_nasi,penilaian_mcu.lubang_hidung,penilaian_mcu.sinus,penilaian_mcu.bibir,penilaian_mcu.gusi,penilaian_mcu.gigi,penilaian_mcu.caries,penilaian_mcu.lidah,"+
                        "penilaian_mcu.faring,penilaian_mcu.tonsil,penilaian_mcu.kelenjar_limfe,penilaian_mcu.kelenjar_gondok,penilaian_mcu.gerakan_dada,penilaian_mcu.vocal_femitus,"+
                        "penilaian_mcu.perkusi_dada,penilaian_mcu.bunyi_napas,penilaian_mcu.bunyi_tambahan,penilaian_mcu.ictus_cordis,penilaian_mcu.bunyi_jantung,penilaian_mcu.batas,penilaian_mcu.mamae,"+
                        "penilaian_mcu.keterangan_mamae,penilaian_mcu.inspeksi,penilaian_mcu.palpasi,penilaian_mcu.hepar,penilaian_mcu.perkusi_abdomen,penilaian_mcu.auskultasi,penilaian_mcu.limpa,"+
                        "penilaian_mcu.costovertebral,penilaian_mcu.scoliosis,penilaian_mcu.kondisi_kulit,penilaian_mcu.penyakit_kulit,penilaian_mcu.ekstrimitas_atas,penilaian_mcu.ekstrimitas_atas_ket,"+
                        "penilaian_mcu.ekstrimitas_bawah,penilaian_mcu.ekstrimitas_bawah_ket,penilaian_mcu.area_genitalia,penilaian_mcu.keterangan_area_genitalia,penilaian_mcu.anus_perianal,"+
                        "penilaian_mcu.keterangan_anus_perianal,penilaian_mcu.laborat,penilaian_mcu.radiologi,penilaian_mcu.ekg,penilaian_mcu.spirometri,penilaian_mcu.audiometri,penilaian_mcu.treadmill,"+
                        "penilaian_mcu.romberg_test,penilaian_mcu.back_strength,penilaian_mcu.abi_tangan_kanan,penilaian_mcu.abi_tangan_kiri,penilaian_mcu.abi_kaki_kanan,penilaian_mcu.abi_kaki_kiri,"+
                        "penilaian_mcu.lainlain,penilaian_mcu.merokok,penilaian_mcu.alkohol,penilaian_mcu.kesimpulan,penilaian_mcu.anjuran,penilaian_mcu.kd_dokter,dokter.nm_dokter "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join penilaian_mcu on reg_periksa.no_rawat=penilaian_mcu.no_rawat "+
                        "inner join dokter on penilaian_mcu.kd_dokter=dokter.kd_dokter where "+
                        "penilaian_mcu.tanggal between ? and ? order by penilaian_mcu.tanggal");
            }else{
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penilaian_mcu.tanggal,"+
                        "penilaian_mcu.informasi,penilaian_mcu.rps,penilaian_mcu.rpk,penilaian_mcu.rpd,penilaian_mcu.alergi,penilaian_mcu.keadaan,penilaian_mcu.kesadaran,penilaian_mcu.td,"+
                        "penilaian_mcu.nadi,penilaian_mcu.rr,penilaian_mcu.tb,penilaian_mcu.bb,penilaian_mcu.suhu,penilaian_mcu.bmi,penilaian_mcu.kasifikasi_bmi,penilaian_mcu.lingkar_pinggang,"+
                        "penilaian_mcu.risiko_lingkar_pinggang,penilaian_mcu.submandibula,penilaian_mcu.axilla,penilaian_mcu.supraklavikula,penilaian_mcu.leher,penilaian_mcu.inguinal,"+
                        "penilaian_mcu.oedema,penilaian_mcu.sinus_frontalis,penilaian_mcu.sinus_maxilaris,penilaian_mcu.rambut,penilaian_mcu.palpebra,penilaian_mcu.sklera,penilaian_mcu.cornea,"+
                        "penilaian_mcu.buta_warna,penilaian_mcu.konjungtiva,penilaian_mcu.lensa,penilaian_mcu.pupil,penilaian_mcu.menggunakan_kacamata,penilaian_mcu.visus,penilaian_mcu.luas_lapang_pandang,"+
                        "penilaian_mcu.keterangan_luas_lapang_pandang,penilaian_mcu.lubang_telinga,penilaian_mcu.daun_telinga,penilaian_mcu.selaput_pendengaran,penilaian_mcu.proc_mastoideus,"+
                        "penilaian_mcu.septum_nasi,penilaian_mcu.lubang_hidung,penilaian_mcu.sinus,penilaian_mcu.bibir,penilaian_mcu.gusi,penilaian_mcu.gigi,penilaian_mcu.caries,penilaian_mcu.lidah,"+
                        "penilaian_mcu.faring,penilaian_mcu.tonsil,penilaian_mcu.kelenjar_limfe,penilaian_mcu.kelenjar_gondok,penilaian_mcu.gerakan_dada,penilaian_mcu.vocal_femitus,"+
                        "penilaian_mcu.perkusi_dada,penilaian_mcu.bunyi_napas,penilaian_mcu.bunyi_tambahan,penilaian_mcu.ictus_cordis,penilaian_mcu.bunyi_jantung,penilaian_mcu.batas,penilaian_mcu.mamae,"+
                        "penilaian_mcu.keterangan_mamae,penilaian_mcu.inspeksi,penilaian_mcu.palpasi,penilaian_mcu.hepar,penilaian_mcu.perkusi_abdomen,penilaian_mcu.auskultasi,penilaian_mcu.limpa,"+
                        "penilaian_mcu.costovertebral,penilaian_mcu.scoliosis,penilaian_mcu.kondisi_kulit,penilaian_mcu.penyakit_kulit,penilaian_mcu.ekstrimitas_atas,penilaian_mcu.ekstrimitas_atas_ket,"+
                        "penilaian_mcu.ekstrimitas_bawah,penilaian_mcu.ekstrimitas_bawah_ket,penilaian_mcu.area_genitalia,penilaian_mcu.keterangan_area_genitalia,penilaian_mcu.anus_perianal,"+
                        "penilaian_mcu.keterangan_anus_perianal,penilaian_mcu.laborat,penilaian_mcu.radiologi,penilaian_mcu.ekg,penilaian_mcu.spirometri,penilaian_mcu.audiometri,penilaian_mcu.treadmill,"+
                        "penilaian_mcu.romberg_test,penilaian_mcu.back_strength,penilaian_mcu.abi_tangan_kanan,penilaian_mcu.abi_tangan_kiri,penilaian_mcu.abi_kaki_kanan,penilaian_mcu.abi_kaki_kiri,"+
                        "penilaian_mcu.lainlain,penilaian_mcu.merokok,penilaian_mcu.alkohol,penilaian_mcu.kesimpulan,penilaian_mcu.anjuran,penilaian_mcu.kd_dokter,dokter.nm_dokter "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join penilaian_mcu on reg_periksa.no_rawat=penilaian_mcu.no_rawat "+
                        "inner join dokter on penilaian_mcu.kd_dokter=dokter.kd_dokter where penilaian_mcu.tanggal between ? and ? and "+
                        "(reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or "+
                        "penilaian_mcu.kd_dokter like ? or dokter.nm_dokter like ?) order by penilaian_mcu.tanggal");
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
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("jk"),rs.getDate("tgl_lahir"),
                        rs.getString("tanggal"),rs.getString("informasi"),rs.getString("rps"),rs.getString("rpk"),rs.getString("rpd"),rs.getString("alergi"),
                        rs.getString("keadaan"),rs.getString("kesadaran"),rs.getString("td"),rs.getString("nadi"),rs.getString("rr"),rs.getString("tb"),
                        rs.getString("bb"),rs.getString("suhu"),rs.getString("bmi"),rs.getString("kasifikasi_bmi"),rs.getString("lingkar_pinggang"),
                        rs.getString("risiko_lingkar_pinggang"),rs.getString("submandibula"),rs.getString("axilla"),rs.getString("supraklavikula"),
                        rs.getString("leher"),rs.getString("inguinal"),rs.getString("oedema"),rs.getString("sinus_frontalis"),rs.getString("sinus_maxilaris"),
                        rs.getString("rambut"),rs.getString("palpebra"),rs.getString("sklera"),rs.getString("cornea"),rs.getString("buta_warna"),
                        rs.getString("konjungtiva"),rs.getString("lensa"),rs.getString("pupil"),rs.getString("menggunakan_kacamata"),rs.getString("visus"),
                        rs.getString("luas_lapang_pandang"),rs.getString("keterangan_luas_lapang_pandang"),rs.getString("lubang_telinga"),rs.getString("daun_telinga"),
                        rs.getString("selaput_pendengaran"),rs.getString("proc_mastoideus"),rs.getString("septum_nasi"),rs.getString("lubang_hidung"),
                        rs.getString("sinus"),rs.getString("bibir"),rs.getString("gusi"),rs.getString("gigi"),rs.getString("caries"),rs.getString("lidah"),
                        rs.getString("faring"),rs.getString("tonsil"),rs.getString("kelenjar_limfe"),rs.getString("kelenjar_gondok"),rs.getString("gerakan_dada"),
                        rs.getString("vocal_femitus"),rs.getString("perkusi_dada"),rs.getString("bunyi_napas"),rs.getString("bunyi_tambahan"),
                        rs.getString("ictus_cordis"),rs.getString("bunyi_jantung"),rs.getString("batas"),rs.getString("mamae"),rs.getString("keterangan_mamae"),
                        rs.getString("inspeksi"),rs.getString("palpasi"),rs.getString("hepar"),rs.getString("perkusi_abdomen"),rs.getString("auskultasi"),
                        rs.getString("limpa"),rs.getString("costovertebral"),rs.getString("scoliosis"),rs.getString("kondisi_kulit"),rs.getString("penyakit_kulit"),
                        rs.getString("ekstrimitas_atas"),rs.getString("ekstrimitas_atas_ket"),rs.getString("ekstrimitas_bawah"),rs.getString("ekstrimitas_bawah_ket"),
                        rs.getString("area_genitalia"),rs.getString("keterangan_area_genitalia"),rs.getString("anus_perianal"),rs.getString("keterangan_anus_perianal"),
                        rs.getString("laborat"),rs.getString("radiologi"),rs.getString("ekg"),rs.getString("spirometri"),rs.getString("audiometri"),
                        rs.getString("treadmill"),rs.getString("romberg_test"),rs.getString("back_strength"),rs.getString("abi_tangan_kanan"),
                        rs.getString("abi_tangan_kiri"),rs.getString("abi_kaki_kanan"),rs.getString("abi_kaki_kiri"),rs.getString("lainlain"),
                        rs.getString("merokok"),rs.getString("alkohol"),rs.getString("kesimpulan"),rs.getString("anjuran"),rs.getString("kd_dokter"),
                        rs.getString("nm_dokter")
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
        RiwayatPenyakitSekarang.setText("");
        RiwayatPenyakitKeluarga.setText("");
        RiwayatPenyakitDahulu.setText("");
        RiwayatAlergiMakanan.setText("");
        KeadaanUmum.setSelectedIndex(0);
        Kesadaran.setSelectedIndex(0);
        TD.setText("");
        Nadi.setText("");
        RR.setText("");
        TB.setText("");
        BB.setText("");
        Suhu.setText("");
        IMT.setText("");
        KlasifikasiIMT.setText("");
        LP.setText("");
        RisikoLP.setText("");
        Rambut.setText("");
        Submandibula.setSelectedIndex(0);
        Axila.setSelectedIndex(0);
        Supraklavikula.setSelectedIndex(0);
        Leher.setSelectedIndex(0);
        Inguinal.setSelectedIndex(0);
        Oedema.setSelectedIndex(0);
        NyeriTekanSinusFrontalis.setSelectedIndex(0);
        NyeriTekananSinusMaxilaris.setSelectedIndex(0);
        Palpebra.setSelectedIndex(0);
        Sklera.setSelectedIndex(0);
        Cornea.setSelectedIndex(0);
        TestButaWarna.setSelectedIndex(0);
        Konjungtiva.setSelectedIndex(0);
        Lensa.setSelectedIndex(0);
        Pupil.setSelectedIndex(0);
        MenggunakanKacamata.setSelectedIndex(0);
        Visus.setText("");
        LuasLapangPandang.setSelectedIndex(0);
        KeteranganLuasLapangPandang.setText("");
        LubangTelinga.setSelectedIndex(0);
        DaunTelinga.setSelectedIndex(0);
        SelaputPendengaran.setSelectedIndex(0);
        NyeriMastoideus.setSelectedIndex(0);
        SeptumNasi.setSelectedIndex(0);
        LubangHidung.setSelectedIndex(0);
        Sinus.setSelectedIndex(0);
        Bibir.setSelectedIndex(0);
        Gusi.setSelectedIndex(0);
        Gigi.setSelectedIndex(0);
        Caries.setSelectedIndex(0);
        Lidah.setSelectedIndex(0);
        Faring.setSelectedIndex(0);
        Tonsil.setSelectedIndex(0);
        KelenjarLimfe.setSelectedIndex(0);
        KelenjarGondok.setSelectedIndex(0);
        GerakanDada.setSelectedIndex(0);
        VocalFremitus.setSelectedIndex(0);
        PerkusiDada.setSelectedIndex(0);
        BunyiNapas.setSelectedIndex(0);
        BunyiTambahan.setSelectedIndex(0);
        IctusCordis.setSelectedIndex(0);
        BunyiJantung.setSelectedIndex(0);
        Batas.setSelectedIndex(0);
        Mamae.setSelectedIndex(0);
        KeteranganMamae.setText("");
        Inspeksi.setSelectedIndex(0);
        Palpasi.setSelectedIndex(0);
        Hepar.setSelectedIndex(0);
        PerkusiAbdomen.setSelectedIndex(0);
        Auskultasi.setSelectedIndex(0);
        Limpa.setSelectedIndex(0);
        NyeriKtok.setSelectedIndex(0);
        Scoliosis.setSelectedIndex(0);
        Kulit.setSelectedIndex(0);
        PenyakitKulit.setText("");
        ExtremitasAtas.setSelectedIndex(0);
        KetExtremitasAtas.setText("");
        ExtremitasBawah.setSelectedIndex(0);
        KetExtremitasBawah.setText("");
        AreaGenitalia.setSelectedIndex(0);
        KeteranganAreaGenitalia.setText("");
        Anus.getSelectedItem().toString();
        KeteranganAnus.setText("");
        PemeriksaanLaboratorium.setText("");
        RongsenThorax.setText("");
        EKG.setText("");
        Spirometri.setText("");
        Audiometri.setText("");
        Treadmill.setText("");
        Romberg.setText("");
        Backstrength.setText("");
        AbiTanganKanan.setText("");
        AbiTanganKiri.setText("");
        AbiKakiKanan.setText("");
        AbiKakiKiri.setText("");
        Lainlain.setText("");
        Merokok.setText("");
        Alkohol.setText("");
        Kesimpulan.setText("");
        Anjuran.setText("");
        TabRawat.setSelectedIndex(0);
        Informasi.requestFocus();
    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()); 
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString()); 
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString()); 
            Jk.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString()); 
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString()); 
            Informasi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString());
            RiwayatPenyakitSekarang.setText(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());
            RiwayatPenyakitKeluarga.setText(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            RiwayatPenyakitDahulu.setText(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            RiwayatAlergiMakanan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            KeadaanUmum.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            Kesadaran.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            TD.setText(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            Nadi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            RR.setText(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
            TB.setText(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            BB.setText(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());
            Suhu.setText(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
            IMT.setText(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
            KlasifikasiIMT.setText(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString());
            LP.setText(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString());
            RisikoLP.setText(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString());
            Submandibula.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString());
            Axila.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),24).toString());
            Supraklavikula.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),25).toString());
            Leher.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),26).toString());
            Inguinal.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),27).toString());
            Oedema.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),28).toString());
            NyeriTekanSinusFrontalis.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),29).toString());
            NyeriTekananSinusMaxilaris.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),30).toString());
            Rambut.setText(tbObat.getValueAt(tbObat.getSelectedRow(),31).toString());
            Palpebra.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),32).toString());
            Sklera.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),33).toString());
            Cornea.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),34).toString());
            TestButaWarna.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),35).toString());
            Konjungtiva.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),36).toString());
            Lensa.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),37).toString());
            Pupil.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),38).toString());
            MenggunakanKacamata.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),39).toString());
            Visus.setText(tbObat.getValueAt(tbObat.getSelectedRow(),40).toString());
            LuasLapangPandang.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),41).toString());
            KeteranganLuasLapangPandang.setText(tbObat.getValueAt(tbObat.getSelectedRow(),42).toString());
            LubangTelinga.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),43).toString());
            DaunTelinga.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),44).toString());
            SelaputPendengaran.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),45).toString());
            NyeriMastoideus.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),46).toString());
            SeptumNasi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),47).toString());
            LubangHidung.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),48).toString());
            Sinus.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),49).toString());
            Bibir.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),50).toString());
            Gusi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),51).toString());
            Gigi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),52).toString());
            Caries.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),53).toString());
            Lidah.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),54).toString());
            Faring.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),55).toString());
            Tonsil.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),56).toString());
            KelenjarLimfe.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),57).toString());
            KelenjarGondok.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),58).toString());
            GerakanDada.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),59).toString());
            VocalFremitus.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),60).toString());
            PerkusiDada.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),61).toString());
            BunyiNapas.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),62).toString());
            BunyiTambahan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),63).toString());
            IctusCordis.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),64).toString());
            BunyiJantung.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),65).toString());
            Batas.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),66).toString());
            Mamae.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),67).toString());
            KeteranganMamae.setText(tbObat.getValueAt(tbObat.getSelectedRow(),68).toString());
            Inspeksi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),69).toString());
            Palpasi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),70).toString());
            Hepar.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),71).toString());
            PerkusiAbdomen.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),72).toString());
            Auskultasi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),73).toString());
            Limpa.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),74).toString());
            NyeriKtok.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),75).toString());
            Scoliosis.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),76).toString());
            Kulit.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),77).toString());
            PenyakitKulit.setText(tbObat.getValueAt(tbObat.getSelectedRow(),78).toString());
            ExtremitasAtas.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),79).toString());
            KetExtremitasAtas.setText(tbObat.getValueAt(tbObat.getSelectedRow(),80).toString());
            ExtremitasBawah.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),81).toString());
            KetExtremitasBawah.setText(tbObat.getValueAt(tbObat.getSelectedRow(),82).toString());
            AreaGenitalia.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),83).toString());
            KeteranganAreaGenitalia.setText(tbObat.getValueAt(tbObat.getSelectedRow(),84).toString());
            Anus.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),85).toString());
            KeteranganAnus.setText(tbObat.getValueAt(tbObat.getSelectedRow(),86).toString());
            PemeriksaanLaboratorium.setText(tbObat.getValueAt(tbObat.getSelectedRow(),87).toString());
            RongsenThorax.setText(tbObat.getValueAt(tbObat.getSelectedRow(),88).toString());
            EKG.setText(tbObat.getValueAt(tbObat.getSelectedRow(),89).toString());
            Spirometri.setText(tbObat.getValueAt(tbObat.getSelectedRow(),90).toString());
            Audiometri.setText(tbObat.getValueAt(tbObat.getSelectedRow(),91).toString());
            Treadmill.setText(tbObat.getValueAt(tbObat.getSelectedRow(),92).toString());
            Romberg.setText(tbObat.getValueAt(tbObat.getSelectedRow(),93).toString());
            Backstrength.setText(tbObat.getValueAt(tbObat.getSelectedRow(),94).toString());
            AbiTanganKanan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),95).toString());
            AbiTanganKiri.setText(tbObat.getValueAt(tbObat.getSelectedRow(),96).toString());
            AbiKakiKanan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),97).toString());
            AbiKakiKiri.setText(tbObat.getValueAt(tbObat.getSelectedRow(),98).toString());
            Lainlain.setText(tbObat.getValueAt(tbObat.getSelectedRow(),99).toString());
            Merokok.setText(tbObat.getValueAt(tbObat.getSelectedRow(),100).toString());
            Alkohol.setText(tbObat.getValueAt(tbObat.getSelectedRow(),101).toString());
            Kesimpulan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),102).toString());
            Anjuran.setText(tbObat.getValueAt(tbObat.getSelectedRow(),103).toString());
            Valid.SetTgl2(TglAsuhan,tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
        }
    }

    private void isRawat() {
        try {
            ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rkm_medis,pasien.nm_pasien, if(pasien.jk='L','Laki-laki','Perempuan') as jk,pasien.tgl_lahir,reg_periksa.tgl_registrasi,"+
                    "reg_periksa.jam_reg from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis where reg_periksa.no_rawat=?");
            try {
                ps.setString(1,TNoRw.getText());
                rs=ps.executeQuery();
                if(rs.next()){
                    TNoRM.setText(rs.getString("no_rkm_medis"));
                    TPasien.setText(rs.getString("nm_pasien"));
                    DTPCari1.setDate(rs.getDate("tgl_registrasi"));
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
    
    public void setNoRm(String norwt, Date tgl2) {
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        DTPCari2.setDate(tgl2);    
        isRawat(); 
    }
    
    public void isCek(){
        BtnSimpan.setEnabled(akses.getpenilaian_mcu());
        BtnHapus.setEnabled(akses.getpenilaian_mcu());
        BtnEdit.setEnabled(akses.getpenilaian_mcu());
        if(akses.getjml2()>=1){
            KdDokter.setEditable(false);
            BtnDokter.setEnabled(false);
            KdDokter.setText(akses.getkode());
            NmDokter.setText(dokter.tampil3(KdDokter.getText()));
            if(NmDokter.getText().equals("")){
                KdDokter.setText("");
                JOptionPane.showMessageDialog(null,"User login bukan dokter...!!");
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
        if(Sequel.queryu2tf("delete from penilaian_mcu where no_rawat=?",1,new String[]{
            tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
        })==true){
            tabMode.removeRow(tbObat.getSelectedRow());
            LCount.setText(""+tabMode.getRowCount());
        }else{
            JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
        }
    }

    private void ganti() {
        if(Sequel.mengedittf("penilaian_mcu","no_rawat=?","no_rawat=?,tanggal=?,kd_dokter=?,informasi=?,rps=?,rpk=?,rpd=?,alergi=?,keadaan=?,kesadaran=?,td=?,nadi=?,rr=?,tb=?,bb=?,suhu=?,bmi=?,kasifikasi_bmi=?,lingkar_pinggang=?,risiko_lingkar_pinggang=?,submandibula=?,"+
                "axilla=?,supraklavikula=?,leher=?,inguinal=?,oedema=?,sinus_frontalis=?,sinus_maxilaris=?,rambut=?,palpebra=?,sklera=?,cornea=?,buta_warna=?,konjungtiva=?,lensa=?,pupil=?,menggunakan_kacamata=?,visus=?,luas_lapang_pandang=?,keterangan_luas_lapang_pandang=?,"+
                "lubang_telinga=?,daun_telinga=?,selaput_pendengaran=?,proc_mastoideus=?,septum_nasi=?,lubang_hidung=?,sinus=?,bibir=?,gusi=?,gigi=?,caries=?,lidah=?,faring=?,tonsil=?,kelenjar_limfe=?,kelenjar_gondok=?,gerakan_dada=?,vocal_femitus=?,perkusi_dada=?,bunyi_napas=?,"+
                "bunyi_tambahan=?,ictus_cordis=?,bunyi_jantung=?,batas=?,mamae=?,keterangan_mamae=?,inspeksi=?,palpasi=?,hepar=?,perkusi_abdomen=?,auskultasi=?,limpa=?,costovertebral=?,scoliosis=?,kondisi_kulit=?,penyakit_kulit=?,ekstrimitas_atas=?,ekstrimitas_atas_ket=?,"+
                "ekstrimitas_bawah=?,ekstrimitas_bawah_ket=?,area_genitalia=?,keterangan_area_genitalia=?,anus_perianal=?,keterangan_anus_perianal=?,laborat=?,radiologi=?,ekg=?,spirometri=?,audiometri=?,treadmill=?,romberg_test=?,back_strength=?,abi_tangan_kanan=?,abi_tangan_kiri=?,"+
                "abi_kaki_kanan=?,abi_kaki_kiri=?,lainlain=?,merokok=?,alkohol=?,kesimpulan=?,anjuran=?",102,new String[]{
                TNoRw.getText(),Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),KdDokter.getText(),Informasi.getSelectedItem().toString(),RiwayatPenyakitSekarang.getText(),RiwayatPenyakitKeluarga.getText(), 
                RiwayatPenyakitDahulu.getText(),RiwayatAlergiMakanan.getText(),KeadaanUmum.getSelectedItem().toString(),Kesadaran.getSelectedItem().toString(),TD.getText(),Nadi.getText(),RR.getText(),TB.getText(),BB.getText(),Suhu.getText(),IMT.getText(),KlasifikasiIMT.getText(),
                LP.getText(),RisikoLP.getText(),Submandibula.getSelectedItem().toString(),Axila.getSelectedItem().toString(),Supraklavikula.getSelectedItem().toString(),Leher.getSelectedItem().toString(),Inguinal.getSelectedItem().toString(),Oedema.getSelectedItem().toString(),
                NyeriTekanSinusFrontalis.getSelectedItem().toString(),NyeriTekananSinusMaxilaris.getSelectedItem().toString(),Rambut.getText(),Palpebra.getSelectedItem().toString(),Sklera.getSelectedItem().toString(),Cornea.getSelectedItem().toString(),
                TestButaWarna.getSelectedItem().toString(),Konjungtiva.getSelectedItem().toString(),Lensa.getSelectedItem().toString(),Pupil.getSelectedItem().toString(),MenggunakanKacamata.getSelectedItem().toString(),Visus.getText(),LuasLapangPandang.getSelectedItem().toString(),
                KeteranganLuasLapangPandang.getText(),LubangTelinga.getSelectedItem().toString(),DaunTelinga.getSelectedItem().toString(),SelaputPendengaran.getSelectedItem().toString(),NyeriMastoideus.getSelectedItem().toString(),SeptumNasi.getSelectedItem().toString(),
                LubangHidung.getSelectedItem().toString(),Sinus.getSelectedItem().toString(),Bibir.getSelectedItem().toString(),Gusi.getSelectedItem().toString(),Gigi.getSelectedItem().toString(),Caries.getSelectedItem().toString(),Lidah.getSelectedItem().toString(),
                Faring.getSelectedItem().toString(),Tonsil.getSelectedItem().toString(),KelenjarLimfe.getSelectedItem().toString(),KelenjarGondok.getSelectedItem().toString(),GerakanDada.getSelectedItem().toString(),VocalFremitus.getSelectedItem().toString(),
                PerkusiDada.getSelectedItem().toString(),BunyiNapas.getSelectedItem().toString(),BunyiTambahan.getSelectedItem().toString(),IctusCordis.getSelectedItem().toString(),BunyiJantung.getSelectedItem().toString(),Batas.getSelectedItem().toString(),Mamae.getSelectedItem().toString(),
                KeteranganMamae.getText(),Inspeksi.getSelectedItem().toString(),Palpasi.getSelectedItem().toString(),Hepar.getSelectedItem().toString(),PerkusiAbdomen.getSelectedItem().toString(),Auskultasi.getSelectedItem().toString(),Limpa.getSelectedItem().toString(),
                NyeriKtok.getSelectedItem().toString(),Scoliosis.getSelectedItem().toString(),Kulit.getSelectedItem().toString(),PenyakitKulit.getText(),ExtremitasAtas.getSelectedItem().toString(),KetExtremitasAtas.getText(),ExtremitasBawah.getSelectedItem().toString(),
                KetExtremitasBawah.getText(),AreaGenitalia.getSelectedItem().toString(),KeteranganAreaGenitalia.getText(),Anus.getSelectedItem().toString(),KeteranganAnus.getText(),PemeriksaanLaboratorium.getText(),RongsenThorax.getText(),EKG.getText(),Spirometri.getText(),
                Audiometri.getText(),Treadmill.getText(),Romberg.getText(),Backstrength.getText(),AbiTanganKanan.getText(),AbiTanganKiri.getText(),AbiKakiKanan.getText(),AbiKakiKiri.getText(),Lainlain.getText(),Merokok.getText(),Alkohol.getText(),Kesimpulan.getText(),Anjuran.getText(),
                tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
             })==true){
                tbObat.setValueAt(TNoRw.getText(),tbObat.getSelectedRow(),0);
                tbObat.setValueAt(TNoRM.getText(),tbObat.getSelectedRow(),1);
                tbObat.setValueAt(TPasien.getText(),tbObat.getSelectedRow(),2);
                tbObat.setValueAt(Jk.getText(),tbObat.getSelectedRow(),3);
                tbObat.setValueAt(TglLahir.getText(),tbObat.getSelectedRow(),4);
                tbObat.setValueAt(Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),tbObat.getSelectedRow(),5);
                tbObat.setValueAt(Informasi.getSelectedItem().toString(),tbObat.getSelectedRow(),6);
                tbObat.setValueAt(RiwayatPenyakitSekarang.getText(),tbObat.getSelectedRow(),7);
                tbObat.setValueAt(RiwayatPenyakitKeluarga.getText(),tbObat.getSelectedRow(),8);
                tbObat.setValueAt(RiwayatPenyakitDahulu.getText(),tbObat.getSelectedRow(),9);
                tbObat.setValueAt(RiwayatAlergiMakanan.getText(),tbObat.getSelectedRow(),10);
                tbObat.setValueAt(KeadaanUmum.getSelectedItem().toString(),tbObat.getSelectedRow(),11);
                tbObat.setValueAt(Kesadaran.getSelectedItem().toString(),tbObat.getSelectedRow(),12);
                tbObat.setValueAt(TD.getText(),tbObat.getSelectedRow(),13);
                tbObat.setValueAt(Nadi.getText(),tbObat.getSelectedRow(),14);
                tbObat.setValueAt(RR.getText(),tbObat.getSelectedRow(),15);
                tbObat.setValueAt(TB.getText(),tbObat.getSelectedRow(),16);
                tbObat.setValueAt(BB.getText(),tbObat.getSelectedRow(),17);
                tbObat.setValueAt(Suhu.getText(),tbObat.getSelectedRow(),18);
                tbObat.setValueAt(IMT.getText(),tbObat.getSelectedRow(),19);
                tbObat.setValueAt(KlasifikasiIMT.getText(),tbObat.getSelectedRow(),20);
                tbObat.setValueAt(LP.getText(),tbObat.getSelectedRow(),21);
                tbObat.setValueAt(RisikoLP.getText(),tbObat.getSelectedRow(),22);
                tbObat.setValueAt(Submandibula.getSelectedItem().toString(),tbObat.getSelectedRow(),23);
                tbObat.setValueAt(Axila.getSelectedItem().toString(),tbObat.getSelectedRow(),24);
                tbObat.setValueAt(Supraklavikula.getSelectedItem().toString(),tbObat.getSelectedRow(),25);
                tbObat.setValueAt(Leher.getSelectedItem().toString(),tbObat.getSelectedRow(),26);
                tbObat.setValueAt(Inguinal.getSelectedItem().toString(),tbObat.getSelectedRow(),27);
                tbObat.setValueAt(Oedema.getSelectedItem().toString(),tbObat.getSelectedRow(),28);
                tbObat.setValueAt(NyeriTekanSinusFrontalis.getSelectedItem().toString(),tbObat.getSelectedRow(),29);
                tbObat.setValueAt(NyeriTekananSinusMaxilaris.getSelectedItem().toString(),tbObat.getSelectedRow(),30);
                tbObat.setValueAt(Rambut.getText(),tbObat.getSelectedRow(),31);
                tbObat.setValueAt(Palpebra.getSelectedItem().toString(),tbObat.getSelectedRow(),32);
                tbObat.setValueAt(Sklera.getSelectedItem().toString(),tbObat.getSelectedRow(),33);
                tbObat.setValueAt(Cornea.getSelectedItem().toString(),tbObat.getSelectedRow(),34);
                tbObat.setValueAt(TestButaWarna.getSelectedItem().toString(),tbObat.getSelectedRow(),35);
                tbObat.setValueAt(Konjungtiva.getSelectedItem().toString(),tbObat.getSelectedRow(),36);
                tbObat.setValueAt(Lensa.getSelectedItem().toString(),tbObat.getSelectedRow(),37);
                tbObat.setValueAt(Pupil.getSelectedItem().toString(),tbObat.getSelectedRow(),38);
                tbObat.setValueAt(MenggunakanKacamata.getSelectedItem().toString(),tbObat.getSelectedRow(),39);
                tbObat.setValueAt(Visus.getText(),tbObat.getSelectedRow(),40);
                tbObat.setValueAt(LuasLapangPandang.getSelectedItem().toString(),tbObat.getSelectedRow(),41);
                tbObat.setValueAt(KeteranganLuasLapangPandang.getText(),tbObat.getSelectedRow(),42);
                tbObat.setValueAt(LubangTelinga.getSelectedItem().toString(),tbObat.getSelectedRow(),43);
                tbObat.setValueAt(DaunTelinga.getSelectedItem().toString(),tbObat.getSelectedRow(),44);
                tbObat.setValueAt(SelaputPendengaran.getSelectedItem().toString(),tbObat.getSelectedRow(),45);
                tbObat.setValueAt(NyeriMastoideus.getSelectedItem().toString(),tbObat.getSelectedRow(),46);
                tbObat.setValueAt(SeptumNasi.getSelectedItem().toString(),tbObat.getSelectedRow(),47);
                tbObat.setValueAt(LubangHidung.getSelectedItem().toString(),tbObat.getSelectedRow(),48);
                tbObat.setValueAt(Sinus.getSelectedItem().toString(),tbObat.getSelectedRow(),49);
                tbObat.setValueAt(Bibir.getSelectedItem().toString(),tbObat.getSelectedRow(),50);
                tbObat.setValueAt(Gusi.getSelectedItem().toString(),tbObat.getSelectedRow(),51);
                tbObat.setValueAt(Gigi.getSelectedItem().toString(),tbObat.getSelectedRow(),52);
                tbObat.setValueAt(Caries.getSelectedItem().toString(),tbObat.getSelectedRow(),53);
                tbObat.setValueAt(Lidah.getSelectedItem().toString(),tbObat.getSelectedRow(),54);
                tbObat.setValueAt(Faring.getSelectedItem().toString(),tbObat.getSelectedRow(),55);
                tbObat.setValueAt(Tonsil.getSelectedItem().toString(),tbObat.getSelectedRow(),56);
                tbObat.setValueAt(KelenjarLimfe.getSelectedItem().toString(),tbObat.getSelectedRow(),57);
                tbObat.setValueAt(KelenjarGondok.getSelectedItem().toString(),tbObat.getSelectedRow(),58);
                tbObat.setValueAt(GerakanDada.getSelectedItem().toString(),tbObat.getSelectedRow(),59);
                tbObat.setValueAt(VocalFremitus.getSelectedItem().toString(),tbObat.getSelectedRow(),60);
                tbObat.setValueAt(PerkusiDada.getSelectedItem().toString(),tbObat.getSelectedRow(),61);
                tbObat.setValueAt(BunyiNapas.getSelectedItem().toString(),tbObat.getSelectedRow(),62);
                tbObat.setValueAt(BunyiTambahan.getSelectedItem().toString(),tbObat.getSelectedRow(),63);
                tbObat.setValueAt(IctusCordis.getSelectedItem().toString(),tbObat.getSelectedRow(),64);
                tbObat.setValueAt(BunyiJantung.getSelectedItem().toString(),tbObat.getSelectedRow(),65);
                tbObat.setValueAt(Batas.getSelectedItem().toString(),tbObat.getSelectedRow(),66);
                tbObat.setValueAt(Mamae.getSelectedItem().toString(),tbObat.getSelectedRow(),67);
                tbObat.setValueAt(KeteranganMamae.getText(),tbObat.getSelectedRow(),68);
                tbObat.setValueAt(Inspeksi.getSelectedItem().toString(),tbObat.getSelectedRow(),69);
                tbObat.setValueAt(Palpasi.getSelectedItem().toString(),tbObat.getSelectedRow(),70);
                tbObat.setValueAt(Hepar.getSelectedItem().toString(),tbObat.getSelectedRow(),71);
                tbObat.setValueAt(PerkusiAbdomen.getSelectedItem().toString(),tbObat.getSelectedRow(),72);
                tbObat.setValueAt(Auskultasi.getSelectedItem().toString(),tbObat.getSelectedRow(),73);
                tbObat.setValueAt(Limpa.getSelectedItem().toString(),tbObat.getSelectedRow(),74);
                tbObat.setValueAt(NyeriKtok.getSelectedItem().toString(),tbObat.getSelectedRow(),75);
                tbObat.setValueAt(Scoliosis.getSelectedItem().toString(),tbObat.getSelectedRow(),76);
                tbObat.setValueAt(Kulit.getSelectedItem().toString(),tbObat.getSelectedRow(),77);
                tbObat.setValueAt(PenyakitKulit.getText(),tbObat.getSelectedRow(),78);
                tbObat.setValueAt(ExtremitasAtas.getSelectedItem().toString(),tbObat.getSelectedRow(),79);
                tbObat.setValueAt(KetExtremitasAtas.getText(),tbObat.getSelectedRow(),80);
                tbObat.setValueAt(ExtremitasBawah.getSelectedItem().toString(),tbObat.getSelectedRow(),81);
                tbObat.setValueAt(KetExtremitasBawah.getText(),tbObat.getSelectedRow(),82);
                tbObat.setValueAt(AreaGenitalia.getSelectedItem().toString(),tbObat.getSelectedRow(),83);
                tbObat.setValueAt(KeteranganAreaGenitalia.getText(),tbObat.getSelectedRow(),84);
                tbObat.setValueAt(Anus.getSelectedItem().toString(),tbObat.getSelectedRow(),85);
                tbObat.setValueAt(KeteranganAnus.getText(),tbObat.getSelectedRow(),86);
                tbObat.setValueAt(PemeriksaanLaboratorium.getText(),tbObat.getSelectedRow(),87);
                tbObat.setValueAt(RongsenThorax.getText(),tbObat.getSelectedRow(),88);
                tbObat.setValueAt(EKG.getText(),tbObat.getSelectedRow(),89);
                tbObat.setValueAt(Spirometri.getText(),tbObat.getSelectedRow(),90);
                tbObat.setValueAt(Audiometri.getText(),tbObat.getSelectedRow(),91);
                tbObat.setValueAt(Treadmill.getText(),tbObat.getSelectedRow(),92);
                tbObat.setValueAt(Romberg.getText(),tbObat.getSelectedRow(),93);
                tbObat.setValueAt(Backstrength.getText(),tbObat.getSelectedRow(),94);
                tbObat.setValueAt(AbiTanganKanan.getText(),tbObat.getSelectedRow(),95);
                tbObat.setValueAt(AbiTanganKiri.getText(),tbObat.getSelectedRow(),96);
                tbObat.setValueAt(AbiKakiKanan.getText(),tbObat.getSelectedRow(),97);
                tbObat.setValueAt(AbiKakiKiri.getText(),tbObat.getSelectedRow(),98);
                tbObat.setValueAt(Lainlain.getText(),tbObat.getSelectedRow(),99);
                tbObat.setValueAt(Merokok.getText(),tbObat.getSelectedRow(),100);
                tbObat.setValueAt(Alkohol.getText(),tbObat.getSelectedRow(),101);
                tbObat.setValueAt(Kesimpulan.getText(),tbObat.getSelectedRow(),102);
                tbObat.setValueAt(Anjuran.getText(),tbObat.getSelectedRow(),103);
                tbObat.setValueAt(KdDokter.getText(),tbObat.getSelectedRow(),104);
                tbObat.setValueAt(NmDokter.getText(),tbObat.getSelectedRow(),105);
                emptTeks();
                TabRawat.setSelectedIndex(1);
        }
    }

    private void simpan() {
        if(Sequel.menyimpantf("penilaian_mcu","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat",101,new String[]{
                TNoRw.getText(),Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),KdDokter.getText(),Informasi.getSelectedItem().toString(),RiwayatPenyakitSekarang.getText(),RiwayatPenyakitKeluarga.getText(), 
                RiwayatPenyakitDahulu.getText(),RiwayatAlergiMakanan.getText(),KeadaanUmum.getSelectedItem().toString(),Kesadaran.getSelectedItem().toString(),TD.getText(),Nadi.getText(),RR.getText(),TB.getText(),BB.getText(),Suhu.getText(),IMT.getText(),KlasifikasiIMT.getText(),
                LP.getText(),RisikoLP.getText(),Submandibula.getSelectedItem().toString(),Axila.getSelectedItem().toString(),Supraklavikula.getSelectedItem().toString(),Leher.getSelectedItem().toString(),Inguinal.getSelectedItem().toString(),Oedema.getSelectedItem().toString(),
                NyeriTekanSinusFrontalis.getSelectedItem().toString(),NyeriTekananSinusMaxilaris.getSelectedItem().toString(),Rambut.getText(),Palpebra.getSelectedItem().toString(),Sklera.getSelectedItem().toString(),Cornea.getSelectedItem().toString(),
                TestButaWarna.getSelectedItem().toString(),Konjungtiva.getSelectedItem().toString(),Lensa.getSelectedItem().toString(),Pupil.getSelectedItem().toString(),MenggunakanKacamata.getSelectedItem().toString(),Visus.getText(),LuasLapangPandang.getSelectedItem().toString(),
                KeteranganLuasLapangPandang.getText(),LubangTelinga.getSelectedItem().toString(),DaunTelinga.getSelectedItem().toString(),SelaputPendengaran.getSelectedItem().toString(),NyeriMastoideus.getSelectedItem().toString(),SeptumNasi.getSelectedItem().toString(),
                LubangHidung.getSelectedItem().toString(),Sinus.getSelectedItem().toString(),Bibir.getSelectedItem().toString(),Gusi.getSelectedItem().toString(),Gigi.getSelectedItem().toString(),Caries.getSelectedItem().toString(),Lidah.getSelectedItem().toString(),
                Faring.getSelectedItem().toString(),Tonsil.getSelectedItem().toString(),KelenjarLimfe.getSelectedItem().toString(),KelenjarGondok.getSelectedItem().toString(),GerakanDada.getSelectedItem().toString(),VocalFremitus.getSelectedItem().toString(),
                PerkusiDada.getSelectedItem().toString(),BunyiNapas.getSelectedItem().toString(),BunyiTambahan.getSelectedItem().toString(),IctusCordis.getSelectedItem().toString(),BunyiJantung.getSelectedItem().toString(),Batas.getSelectedItem().toString(),Mamae.getSelectedItem().toString(),
                KeteranganMamae.getText(),Inspeksi.getSelectedItem().toString(),Palpasi.getSelectedItem().toString(),Hepar.getSelectedItem().toString(),PerkusiAbdomen.getSelectedItem().toString(),Auskultasi.getSelectedItem().toString(),Limpa.getSelectedItem().toString(),
                NyeriKtok.getSelectedItem().toString(),Scoliosis.getSelectedItem().toString(),Kulit.getSelectedItem().toString(),PenyakitKulit.getText(),ExtremitasAtas.getSelectedItem().toString(),KetExtremitasAtas.getText(),ExtremitasBawah.getSelectedItem().toString(),
                KetExtremitasBawah.getText(),AreaGenitalia.getSelectedItem().toString(),KeteranganAreaGenitalia.getText(),Anus.getSelectedItem().toString(),KeteranganAnus.getText(),PemeriksaanLaboratorium.getText(),RongsenThorax.getText(),EKG.getText(),Spirometri.getText(),
                Audiometri.getText(),Treadmill.getText(),Romberg.getText(),Backstrength.getText(),AbiTanganKanan.getText(),AbiTanganKiri.getText(),AbiKakiKanan.getText(),AbiKakiKiri.getText(),Lainlain.getText(),Merokok.getText(),Alkohol.getText(),Kesimpulan.getText(),Anjuran.getText()
            })==true){
            tabMode.addRow(new Object[]{
                TNoRw.getText(),TNoRM.getText(),TPasien.getText(),Jk.getText(),TglLahir.getText(),Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),Informasi.getSelectedItem().toString(),RiwayatPenyakitSekarang.getText(),RiwayatPenyakitKeluarga.getText(), 
                RiwayatPenyakitDahulu.getText(),RiwayatAlergiMakanan.getText(),KeadaanUmum.getSelectedItem().toString(),Kesadaran.getSelectedItem().toString(),TD.getText(),Nadi.getText(),RR.getText(),TB.getText(),BB.getText(),Suhu.getText(),IMT.getText(),KlasifikasiIMT.getText(),
                LP.getText(),RisikoLP.getText(),Submandibula.getSelectedItem().toString(),Axila.getSelectedItem().toString(),Supraklavikula.getSelectedItem().toString(),Leher.getSelectedItem().toString(),Inguinal.getSelectedItem().toString(),Oedema.getSelectedItem().toString(),
                NyeriTekanSinusFrontalis.getSelectedItem().toString(),NyeriTekananSinusMaxilaris.getSelectedItem().toString(),Rambut.getText(),Palpebra.getSelectedItem().toString(),Sklera.getSelectedItem().toString(),Cornea.getSelectedItem().toString(),
                TestButaWarna.getSelectedItem().toString(),Konjungtiva.getSelectedItem().toString(),Lensa.getSelectedItem().toString(),Pupil.getSelectedItem().toString(),MenggunakanKacamata.getSelectedItem().toString(),Visus.getText(),LuasLapangPandang.getSelectedItem().toString(),
                KeteranganLuasLapangPandang.getText(),LubangTelinga.getSelectedItem().toString(),DaunTelinga.getSelectedItem().toString(),SelaputPendengaran.getSelectedItem().toString(),NyeriMastoideus.getSelectedItem().toString(),SeptumNasi.getSelectedItem().toString(),
                LubangHidung.getSelectedItem().toString(),Sinus.getSelectedItem().toString(),Bibir.getSelectedItem().toString(),Gusi.getSelectedItem().toString(),Gigi.getSelectedItem().toString(),Caries.getSelectedItem().toString(),Lidah.getSelectedItem().toString(),
                Faring.getSelectedItem().toString(),Tonsil.getSelectedItem().toString(),KelenjarLimfe.getSelectedItem().toString(),KelenjarGondok.getSelectedItem().toString(),GerakanDada.getSelectedItem().toString(),VocalFremitus.getSelectedItem().toString(),
                PerkusiDada.getSelectedItem().toString(),BunyiNapas.getSelectedItem().toString(),BunyiTambahan.getSelectedItem().toString(),IctusCordis.getSelectedItem().toString(),BunyiJantung.getSelectedItem().toString(),Batas.getSelectedItem().toString(),Mamae.getSelectedItem().toString(),
                KeteranganMamae.getText(),Inspeksi.getSelectedItem().toString(),Palpasi.getSelectedItem().toString(),Hepar.getSelectedItem().toString(),PerkusiAbdomen.getSelectedItem().toString(),Auskultasi.getSelectedItem().toString(),Limpa.getSelectedItem().toString(),
                NyeriKtok.getSelectedItem().toString(),Scoliosis.getSelectedItem().toString(),Kulit.getSelectedItem().toString(),PenyakitKulit.getText(),ExtremitasAtas.getSelectedItem().toString(),KetExtremitasAtas.getText(),ExtremitasBawah.getSelectedItem().toString(),
                KetExtremitasBawah.getText(),AreaGenitalia.getSelectedItem().toString(),KeteranganAreaGenitalia.getText(),Anus.getSelectedItem().toString(),KeteranganAnus.getText(),PemeriksaanLaboratorium.getText(),RongsenThorax.getText(),EKG.getText(),Spirometri.getText(),
                Audiometri.getText(),Treadmill.getText(),Romberg.getText(),Backstrength.getText(),AbiTanganKanan.getText(),AbiTanganKiri.getText(),AbiKakiKanan.getText(),AbiKakiKiri.getText(),Lainlain.getText(),Merokok.getText(),Alkohol.getText(),Kesimpulan.getText(),Anjuran.getText(),
                KdDokter.getText(),NmDokter.getText()
            });
            LCount.setText(""+tabMode.getRowCount());
            emptTeks();
        }
    }
    
    private void isBMI(){
        try {
            if((!TB.getText().equals(""))&&(!BB.getText().equals(""))){
                try {
                    IMT.setText(Valid.SetAngka8(Valid.SetAngka(BB.getText())/((Valid.SetAngka(TB.getText())/100)*(Valid.SetAngka(TB.getText())/100)),1)+"");
                } catch (Exception e) {
                    IMT.setText("");
                }
                if(Valid.SetAngka(IMT.getText())<18.5){
                    KlasifikasiIMT.setText("Berat Badan Kurang");
                }else if((Valid.SetAngka(IMT.getText())>=18.5)&&(Valid.SetAngka(IMT.getText())<=22.9)){
                    KlasifikasiIMT.setText("Berat Badan Normal");
                }else if((Valid.SetAngka(IMT.getText())>=23)&&(Valid.SetAngka(IMT.getText())<=24.9)){
                    KlasifikasiIMT.setText("Kelebihan Berat Badan");
                }else if((Valid.SetAngka(IMT.getText())>=25)&&(Valid.SetAngka(IMT.getText())<=29.9)){
                    KlasifikasiIMT.setText("Obesitas I");
                }else if(Valid.SetAngka(IMT.getText())>=30){
                    KlasifikasiIMT.setText("Obesitas II");
                }else{
                    KlasifikasiIMT.setText("");
                }
            }else{
                IMT.setText("");
                KlasifikasiIMT.setText("");
            }
        } catch (Exception e) {
            IMT.setText("");
            KlasifikasiIMT.setText("");
        }
    }
    
    private void isLP(){
        try {
            if((!LP.getText().equals(""))&&(!Jk.getText().equals(""))&&(!KlasifikasiIMT.getText().equals(""))){
                if(Jk.getText().equals("Laki-laki")){
                    if(KlasifikasiIMT.getText().equals("Berat Badan Kurang")){
                        if(Valid.SetAngka(LP.getText())<90){
                            RisikoLP.setText("Rendah");
                        }else if(Valid.SetAngka(LP.getText())>=90){
                            RisikoLP.setText("Cukup");
                        }
                    }else if(KlasifikasiIMT.getText().equals("Berat Badan Normal")){
                        if(Valid.SetAngka(LP.getText())<90){
                            RisikoLP.setText("Cukup");
                        }else if(Valid.SetAngka(LP.getText())>=90){
                            RisikoLP.setText("Meningkat");
                        }
                    }else if(KlasifikasiIMT.getText().equals("Kelebihan Berat Badan")){
                        if(Valid.SetAngka(LP.getText())<90){
                            RisikoLP.setText("Meningkat");
                        }else if(Valid.SetAngka(LP.getText())>=90){
                            RisikoLP.setText("Moderat");
                        }
                    }else if(KlasifikasiIMT.getText().equals("Obesitas I")){
                        if(Valid.SetAngka(LP.getText())<90){
                            RisikoLP.setText("Moderat");
                        }else if(Valid.SetAngka(LP.getText())>=90){
                            RisikoLP.setText("Berat");
                        }
                    }else if(KlasifikasiIMT.getText().equals("Obesitas II")){
                        if(Valid.SetAngka(LP.getText())<90){
                            RisikoLP.setText("Berat");
                        }else if(Valid.SetAngka(LP.getText())>=90){
                            RisikoLP.setText("Sangat");
                        }
                    }else{
                        RisikoLP.setText("");
                    }
                }else if(Jk.getText().equals("Perempuan")){
                    if(KlasifikasiIMT.getText().equals("Berat Badan Kurang")){
                        if(Valid.SetAngka(LP.getText())<80){
                            RisikoLP.setText("Rendah");
                        }else if(Valid.SetAngka(LP.getText())>=80){
                            RisikoLP.setText("Cukup");
                        }
                    }else if(KlasifikasiIMT.getText().equals("Berat Badan Normal")){
                        if(Valid.SetAngka(LP.getText())<80){
                            RisikoLP.setText("Cukup");
                        }else if(Valid.SetAngka(LP.getText())>=80){
                            RisikoLP.setText("Meningkat");
                        }
                    }else if(KlasifikasiIMT.getText().equals("Kelebihan Berat Badan")){
                        if(Valid.SetAngka(LP.getText())<80){
                            RisikoLP.setText("Meningkat");
                        }else if(Valid.SetAngka(LP.getText())>=80){
                            RisikoLP.setText("Moderat");
                        }
                    }else if(KlasifikasiIMT.getText().equals("Obesitas I")){
                        if(Valid.SetAngka(LP.getText())<80){
                            RisikoLP.setText("Moderat");
                        }else if(Valid.SetAngka(LP.getText())>=80){
                            RisikoLP.setText("Berat");
                        }
                    }else if(KlasifikasiIMT.getText().equals("Obesitas II")){
                        if(Valid.SetAngka(LP.getText())<80){
                            RisikoLP.setText("Berat");
                        }else if(Valid.SetAngka(LP.getText())>=80){
                            RisikoLP.setText("Sangat");
                        }
                    }else{
                        RisikoLP.setText("");
                    }
                }else{
                    RisikoLP.setText("");
                }
            }else{
                RisikoLP.setText("");
            }
        } catch (Exception e) {
            RisikoLP.setText("");
        }
    }
}

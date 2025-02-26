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
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import kepegawaian.DlgCariDokter;
import simrskhanza.DlgCariPasien;


/**
 *
 * @author perpustakaan
 */
public final class RMPenilaianBayiBaruLahir extends javax.swing.JDialog {
    private final DefaultTableModel tabMode,tabModeRiwayatKehamilan,tabModeAPGAR;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;
    private DlgCariDokter dokter=new DlgCariDokter(null,false);
    private StringBuilder htmlContent;
    private String TANGGALMUNDUR="yes";
    
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public RMPenilaianBayiBaruLahir(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        DlgRiwayatPersalinan.setSize(650,192);
        
        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.RM","Nama Pasien","Tgl.Lahir","J.K.","Umur","NIK Bayi","No.RM IBU","Nama Ibu","Tgl.Lahir Ibu","NIK Ibu","Kode Dokter","Nama Dokter","Tanggal",
            "G","P","A","Hidup","Usia Hamil","HbsAg","HIV/AIDS","Syphilis","Riwayat Obstetri Ibu","Keterangan Riwayat Obstetri Ibu","Faktor Risiko Neonatal",
            "Keterangan Faktor Risiko Neonatal","Tgl & Jam Persalinan","Bersalin Di","Inisiasi Menyusui Dini","Jenis Persalinan","Indikasi/Keterangan","Aterm",
            "Bernafas/Menangis","Tonus Otot Baik","Cairan Amnion Jernih","F 1","U 1","T 1","R 1","W 1","N 1'","F 5","U 5","T 5","R 5","W 5","N 5'","F 10","U 10",
            "T 10","R 10","W 10","N 10'","Frekuensi Napas","N.F.N","Retraksi","N.R","Sianosis","N.S","Jalan Masuk Udara","N.J.M","Grunting","N.G","Ttl.D.S",
            "Keterangan Down Score","Nadi(x/menit)","RR(x/menit)","Suhu(°C)","Saturasi O2(%)","BB(gram)","PB(cm)","LK(cm)","LD(cm)","Keadaan Umum","Keterangan Keadaan Umum",
            "Kulit","Keterangan Kulit","Kepala","Keterangan Kepala","Mata","Keterangan Mata","Telinga","Keterangan Telinga","Hidung","Keterangan Hidung","Mulut",
            "Keterangan Mulut","Tenggorokan","Keterangan Tenggorokan","Leher","Keterangan Leher","Thorax","Keterangan Thorax","Abdomen","Keterangan Abdomen",
            "Genitalia","Keterangan Genitalia","Anus","Keterangan Anus","Muskuloskeletal","Keterangan Muskuloskeletal","Ekstrimitas","Keterangan Ekstrimitas",
            "Paru","Keterangan Paru","Refleks Primitif","Keterangan Refleks Primitif","Kelainan Lainnya","Pemeriksaan Regional/Khusus/Tambahan","Laboratorium",
            "Radiologi","Penunjang Lainnya","Diagnosis/Asesmen","Tatalaksana","Edukasi"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        
        tbObat.setModel(tabMode);
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 115; i++) {
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
                column.setPreferredWidth(60);
            }else if(i==5){
                column.setPreferredWidth(40);
            }else if(i==6){
                column.setPreferredWidth(110);
            }else if(i==7){
                column.setPreferredWidth(70);
            }else if(i==8){
                column.setPreferredWidth(150);
            }else if(i==9){
                column.setPreferredWidth(80);
            }else if(i==10){
                column.setPreferredWidth(110);
            }else if(i==11){
                column.setPreferredWidth(90);
            }else if(i==12){
                column.setPreferredWidth(150);
            }else if(i==13){
                column.setPreferredWidth(115);
            }else if(i==14){
                column.setPreferredWidth(25);
            }else if(i==15){
                column.setPreferredWidth(25);
            }else if(i==16){
                column.setPreferredWidth(25);
            }else if(i==17){
                column.setPreferredWidth(38);
            }else if(i==18){
                column.setPreferredWidth(60);
            }else if(i==19){
                column.setPreferredWidth(60);
            }else if(i==20){
                column.setPreferredWidth(60);
            }else if(i==21){
                column.setPreferredWidth(60);
            }else if(i==22){
                column.setPreferredWidth(140);
            }else if(i==23){
                column.setPreferredWidth(170);
            }else if(i==24){
                column.setPreferredWidth(125);
            }else if(i==25){
                column.setPreferredWidth(177);
            }else if(i==26){
                column.setPreferredWidth(115);
            }else if(i==27){
                column.setPreferredWidth(150);
            }else if(i==28){
                column.setPreferredWidth(115);
            }else if(i==29){
                column.setPreferredWidth(88);
            }else if(i==30){
                column.setPreferredWidth(150);
            }else if(i==31){
                column.setPreferredWidth(41);
            }else if(i==32){
                column.setPreferredWidth(102);
            }else if(i==33){
                column.setPreferredWidth(87);
            }else if(i==34){
                column.setPreferredWidth(114);
            }else if(i==35){
                column.setPreferredWidth(27);
            }else if(i==36){
                column.setPreferredWidth(27);
            }else if(i==37){
                column.setPreferredWidth(27);
            }else if(i==38){
                column.setPreferredWidth(27);
            }else if(i==39){
                column.setPreferredWidth(27);
            }else if(i==40){
                column.setPreferredWidth(32);
            }else if(i==41){
                column.setPreferredWidth(27);
            }else if(i==42){
                column.setPreferredWidth(27);
            }else if(i==43){
                column.setPreferredWidth(27);
            }else if(i==44){
                column.setPreferredWidth(27);
            }else if(i==45){
                column.setPreferredWidth(27);
            }else if(i==46){
                column.setPreferredWidth(32);
            }else if(i==47){
                column.setPreferredWidth(30);
            }else if(i==48){
                column.setPreferredWidth(30);
            }else if(i==49){
                column.setPreferredWidth(30);
            }else if(i==50){
                column.setPreferredWidth(30);
            }else if(i==51){
                column.setPreferredWidth(33);
            }else if(i==52){
                column.setPreferredWidth(35);
            }else if(i==53){
                column.setPreferredWidth(88);
            }else if(i==54){
                column.setPreferredWidth(35);
            }else if(i==55){
                column.setPreferredWidth(85);
            }else if(i==56){
                column.setPreferredWidth(30);
            }else if(i==57){
                column.setPreferredWidth(121);
            }else if(i==58){
                column.setPreferredWidth(30);
            }else if(i==59){
                column.setPreferredWidth(160);
            }else if(i==60){
                column.setPreferredWidth(35);
            }else if(i==61){
                column.setPreferredWidth(171);
            }else if(i==62){
                column.setPreferredWidth(30);
            }else if(i==63){
                column.setPreferredWidth(42);
            }else if(i==64){
                column.setPreferredWidth(150);
            }else if(i==65){
                column.setPreferredWidth(76);
            }else if(i==66){
                column.setPreferredWidth(68);
            }else if(i==67){
                column.setPreferredWidth(52);
            }else if(i==68){
                column.setPreferredWidth(85);
            }else if(i==69){
                column.setPreferredWidth(54);
            }else if(i==70){
                column.setPreferredWidth(42);
            }else if(i==71){
                column.setPreferredWidth(42);
            }else if(i==72){
                column.setPreferredWidth(42);
            }else if(i==73){
                column.setPreferredWidth(86);
            }else if(i==74){
                column.setPreferredWidth(150);
            }else if(i==75){
                column.setPreferredWidth(80);
            }else if(i==76){
                column.setPreferredWidth(150);
            }else if(i==77){
                column.setPreferredWidth(80);
            }else if(i==78){
                column.setPreferredWidth(150);
            }else if(i==79){
                column.setPreferredWidth(80);
            }else if(i==80){
                column.setPreferredWidth(150);
            }else if(i==81){
                column.setPreferredWidth(80);
            }else if(i==82){
                column.setPreferredWidth(150);
            }else if(i==83){
                column.setPreferredWidth(80);
            }else if(i==84){
                column.setPreferredWidth(150);
            }else if(i==85){
                column.setPreferredWidth(80);
            }else if(i==86){
                column.setPreferredWidth(150);
            }else if(i==87){
                column.setPreferredWidth(80);
            }else if(i==88){
                column.setPreferredWidth(150);
            }else if(i==89){
                column.setPreferredWidth(80);
            }else if(i==90){
                column.setPreferredWidth(150);
            }else if(i==91){
                column.setPreferredWidth(80);
            }else if(i==92){
                column.setPreferredWidth(150);
            }else if(i==93){
                column.setPreferredWidth(80);
            }else if(i==94){
                column.setPreferredWidth(150);
            }else if(i==95){
                column.setPreferredWidth(80);
            }else if(i==96){
                column.setPreferredWidth(150);
            }else if(i==97){
                column.setPreferredWidth(80);
            }else if(i==98){
                column.setPreferredWidth(150);
            }else if(i==99){
                column.setPreferredWidth(80);
            }else if(i==100){
                column.setPreferredWidth(150);
            }else if(i==101){
                column.setPreferredWidth(80);
            }else if(i==102){
                column.setPreferredWidth(150);
            }else if(i==103){
                column.setPreferredWidth(80);
            }else if(i==104){
                column.setPreferredWidth(150);
            }else if(i==105){
                column.setPreferredWidth(85);
            }else if(i==106){
                column.setPreferredWidth(150);
            }else if(i==107){
                column.setPreferredWidth(150);
            }else if(i==108){
                column.setPreferredWidth(250);
            }else if(i==109){
                column.setPreferredWidth(200);
            }else if(i==110){
                column.setPreferredWidth(200);
            }else if(i==111){
                column.setPreferredWidth(200);
            }else if(i==112){
                column.setPreferredWidth(200);
            }else if(i==113){
                column.setPreferredWidth(300);
            }else if(i==114){
                column.setPreferredWidth(250);
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
        N1.setDocument(new batasInput((int)2).getOnlyAngka(N1));
        N5.setDocument(new batasInput((int)2).getOnlyAngka(N5));
        N10.setDocument(new batasInput((int)2).getOnlyAngka(N10));
        TCari.setDocument(new batasInput((int)100).getKata(TCari));
        TempatPersalinan.setDocument(new batasInput((byte)30).getKata(TempatPersalinan));
        UsiaHamil.setDocument(new batasInput((byte)20).getKata(UsiaHamil));
        JenisPersalinan.setDocument(new batasInput((byte)20).getKata(JenisPersalinan));
        Penolong.setDocument(new batasInput((byte)30).getKata(Penolong));
        Penyulit.setDocument(new batasInput((byte)40).getKata(Penyulit));
        BBPB.setDocument(new batasInput((byte)10).getKata(BBPB));
        Keadaan.setDocument(new batasInput((byte)40).getKata(Keadaan));
        
        KeteranganPenyakitIbu.setDocument(new batasInput((int)70).getKata(KeteranganPenyakitIbu));
        ObatSelamaHamil.setDocument(new batasInput((int)150).getKata(ObatSelamaHamil));
        KeteranganPerawatanAntenatal.setDocument(new batasInput((byte)40).getKata(KeteranganPerawatanAntenatal));
        KeteranganTerdaftarEkohort.setDocument(new batasInput((byte)40).getKata(KeteranganTerdaftarEkohort));
        KeteranganPenyulitKehamilan.setDocument(new batasInput((byte)60).getKata(KeteranganPenyulitKehamilan));
        AlergiIbu.setDocument(new batasInput((byte)60).getKata(AlergiIbu));
        KeteranganRiwayatMaternal.setDocument(new batasInput((int)150).getKata(KeteranganRiwayatMaternal));
        UmurKehamilan.setDocument(new batasInput((byte)30).getKata(UmurKehamilan));
        KeteranganKehamilan.setDocument(new batasInput((byte)30).getKata(KeteranganKehamilan));
        UrutanKehamilan.setDocument(new batasInput((byte)4).getKata(UrutanKehamilan));
        JamKetubanPecah.setDocument(new batasInput((byte)4).getKata(JamKetubanPecah));
        MenitKetubanPecah.setDocument(new batasInput((byte)4).getKata(MenitKetubanPecah));
        JumlahAirKetuban.setDocument(new batasInput((byte)20).getKata(JumlahAirKetuban));
        WarnaAirKetuban.setDocument(new batasInput((byte)20).getKata(WarnaAirKetuban));
        BauAirKetuban.setDocument(new batasInput((byte)20).getKata(BauAirKetuban));
        LetakBayi.setDocument(new batasInput((byte)70).getKata(LetakBayi));
        KeteranganMacamPersalinan.setDocument(new batasInput((byte)40).getKata(KeteranganMacamPersalinan));
        KeteranganIndikasiPersalinanOperatif.setDocument(new batasInput((byte)50).getKata(KeteranganIndikasiPersalinanOperatif));
        LamaGawatJanin.setDocument(new batasInput((byte)4).getKata(LamaGawatJanin));
        ObatSelamaPersalinan.setDocument(new batasInput((byte)150).getKata(ObatSelamaPersalinan));
        BeratPlacenta.setDocument(new batasInput((byte)4).getKata(BeratPlacenta));
        KelainanPlacenta.setDocument(new batasInput((byte)70).getKata(KelainanPlacenta));
        KeteranganRiwayatPersalinan.setDocument(new batasInput((int)150).getKata(KeteranganRiwayatPersalinan));
        BBLahir.setDocument(new batasInput((byte)5).getKata(BBLahir));
        PanjangBadan.setDocument(new batasInput((byte)5).getKata(PanjangBadan));
        LingkarKepala.setDocument(new batasInput((byte)5).getKata(LingkarKepala));
        LingkarDada.setDocument(new batasInput((byte)5).getKata(LingkarDada));
        KeteranganResusitasiLahir.setDocument(new batasInput((byte)50).getKata(KeteranganResusitasiLahir));
        ObatSaatLahir.setDocument(new batasInput((int)150).getKata(ObatSaatLahir));
        KeteranganKeadaanBayi.setDocument(new batasInput((int)150).getKata(KeteranganKeadaanBayi));
        KeteranganKondisiUmum.setDocument(new batasInput((byte)40).getKata(KeteranganKondisiUmum));
        KeteranganKulit.setDocument(new batasInput((byte)40).getKata(KeteranganKulit));
        KeteranganKepala.setDocument(new batasInput((byte)40).getKata(KeteranganKepala));
        KeteranganLeher.setDocument(new batasInput((byte)40).getKata(KeteranganLeher));
        KeteranganMata.setDocument(new batasInput((byte)40).getKata(KeteranganMata));
        KeteranganHidung.setDocument(new batasInput((byte)40).getKata(KeteranganHidung));
        KeteranganTelinga.setDocument(new batasInput((byte)40).getKata(KeteranganTelinga));
        KeteranganDada.setDocument(new batasInput((byte)40).getKata(KeteranganDada));
        KeteranganParu.setDocument(new batasInput((byte)40).getKata(KeteranganParu));
        KeteranganJantung.setDocument(new batasInput((byte)40).getKata(KeteranganJantung));
        KeteranganPerut.setDocument(new batasInput((byte)40).getKata(KeteranganPerut));
        KeteranganTaliPusat.setDocument(new batasInput((byte)40).getKata(KeteranganTaliPusat));
        KeteranganAlatKelamin.setDocument(new batasInput((byte)40).getKata(KeteranganAlatKelamin));
        KeteranganRuasTulangBelakang.setDocument(new batasInput((byte)40).getKata(KeteranganRuasTulangBelakang));
        KeteranganExtrimitas.setDocument(new batasInput((byte)40).getKata(KeteranganExtrimitas));
        KeteranganAnus.setDocument(new batasInput((byte)40).getKata(KeteranganAnus));
        KeteranganRefleks.setDocument(new batasInput((byte)40).getKata(KeteranganRefleks));
        KeteranganDenyutFemoral.setDocument(new batasInput((byte)40).getKata(KeteranganDenyutFemoral));
        PemeriksaanLainnya.setDocument(new batasInput((int)300).getKata(PemeriksaanLainnya));
        PemeriksaanPenunjang.setDocument(new batasInput((int)500).getKata(PemeriksaanPenunjang));
        Diagnosis.setDocument(new batasInput((int)300).getKata(Diagnosis));
        Tatalaksana.setDocument(new batasInput((int)1000).getKata(Tatalaksana));
        
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
        jLabel11 = new widget.Label();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel99 = new widget.Label();
        label11 = new widget.Label();
        TglAsuhan = new widget.Tanggal();
        Scroll6 = new widget.ScrollPane();
        tbRiwayatKehamilan = new widget.Table();
        BtnTambahMasalah = new widget.Button();
        BtnHapusRiwayatPersalinan = new widget.Button();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel115 = new widget.Label();
        jLabel74 = new widget.Label();
        Scroll2 = new widget.ScrollPane();
        tbAPGAR = new widget.Table();
        label71 = new widget.Label();
        N1 = new widget.TextBox2();
        N5 = new widget.TextBox2();
        N10 = new widget.TextBox2();
        jLabel14 = new widget.Label();
        NoRMIbu = new widget.TextBox();
        NmIbu = new widget.TextBox();
        BtnIbuBayi = new widget.Button();
        jLabel9 = new widget.Label();
        TglLahirIbu = new widget.TextBox();
        jLabel15 = new widget.Label();
        NIKIbu = new widget.TextBox();
        jLabel17 = new widget.Label();
        StatusPerkawinanIbu = new widget.TextBox();
        jLabel16 = new widget.Label();
        jLabel38 = new widget.Label();
        PenyakitIbu = new widget.ComboBox();
        KeteranganPenyakitIbu = new widget.TextBox();
        jLabel39 = new widget.Label();
        ObatSelamaHamil = new widget.TextBox();
        jLabel18 = new widget.Label();
        jLabel40 = new widget.Label();
        jLabel41 = new widget.Label();
        PerawatanAntenatal = new widget.ComboBox();
        jLabel42 = new widget.Label();
        KeteranganPerawatanAntenatal = new widget.TextBox();
        jLabel20 = new widget.Label();
        KeteranganTerdaftarEkohort = new widget.TextBox();
        TerdaftarEkohort = new widget.ComboBox();
        jLabel43 = new widget.Label();
        jLabel22 = new widget.Label();
        PenyulitKehamilan = new widget.ComboBox();
        jLabel44 = new widget.Label();
        KeteranganPenyulitKehamilan = new widget.TextBox();
        jLabel45 = new widget.Label();
        AlergiIbu = new widget.TextBox();
        KeteranganRiwayatMaternal = new widget.TextBox();
        jLabel46 = new widget.Label();
        jLabel23 = new widget.Label();
        UmurKehamilan = new widget.TextBox();
        jLabel24 = new widget.Label();
        jLabel25 = new widget.Label();
        jLabel47 = new widget.Label();
        Kehamilan = new widget.ComboBox();
        KeteranganKehamilan = new widget.TextBox();
        jLabel48 = new widget.Label();
        UrutanKehamilan = new widget.TextBox();
        jLabel26 = new widget.Label();
        jLabel27 = new widget.Label();
        JamKetubanPecah = new widget.TextBox();
        jLabel49 = new widget.Label();
        jLabel28 = new widget.Label();
        MenitKetubanPecah = new widget.TextBox();
        jLabel29 = new widget.Label();
        jLabel30 = new widget.Label();
        JumlahAirKetuban = new widget.TextBox();
        WarnaAirKetuban = new widget.TextBox();
        jLabel31 = new widget.Label();
        BauAirKetuban = new widget.TextBox();
        jLabel32 = new widget.Label();
        jLabel34 = new widget.Label();
        LetakBayi = new widget.TextBox();
        jLabel33 = new widget.Label();
        jLabel50 = new widget.Label();
        MacamPersalinan = new widget.ComboBox();
        KeteranganMacamPersalinan = new widget.TextBox();
        jLabel35 = new widget.Label();
        jLabel36 = new widget.Label();
        IndikasiPersalinanOperatif = new widget.ComboBox();
        KeteranganIndikasiPersalinanOperatif = new widget.TextBox();
        jLabel37 = new widget.Label();
        LamaGawatJanin = new widget.TextBox();
        jLabel51 = new widget.Label();
        jLabel52 = new widget.Label();
        jLabel53 = new widget.Label();
        ObatSelamaPersalinan = new widget.TextBox();
        jLabel54 = new widget.Label();
        jLabel56 = new widget.Label();
        BeratPlacenta = new widget.TextBox();
        jLabel55 = new widget.Label();
        jLabel57 = new widget.Label();
        jLabel58 = new widget.Label();
        KelainanPlacenta = new widget.TextBox();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel116 = new widget.Label();
        jLabel60 = new widget.Label();
        BBLahir = new widget.TextBox();
        jLabel59 = new widget.Label();
        jLabel61 = new widget.Label();
        jLabel62 = new widget.Label();
        PanjangBadan = new widget.TextBox();
        jLabel63 = new widget.Label();
        jLabel64 = new widget.Label();
        LingkarKepala = new widget.TextBox();
        jLabel65 = new widget.Label();
        jLabel66 = new widget.Label();
        LingkarDada = new widget.TextBox();
        jLabel67 = new widget.Label();
        jLabel68 = new widget.Label();
        jLabel69 = new widget.Label();
        JenisKelamin = new widget.ComboBox();
        jLabel70 = new widget.Label();
        ResusitasiLahir = new widget.ComboBox();
        KeteranganResusitasiLahir = new widget.TextBox();
        jLabel71 = new widget.Label();
        ObatSaatLahir = new widget.TextBox();
        jLabel72 = new widget.Label();
        jLabel73 = new widget.Label();
        jLabel75 = new widget.Label();
        KeteranganRiwayatPersalinan = new widget.TextBox();
        jLabel76 = new widget.Label();
        jLabel77 = new widget.Label();
        KeteranganKeadaanBayi = new widget.TextBox();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel117 = new widget.Label();
        KondisiUmum = new widget.ComboBox();
        jLabel78 = new widget.Label();
        KeteranganKondisiUmum = new widget.TextBox();
        jLabel79 = new widget.Label();
        jLabel80 = new widget.Label();
        Kulit = new widget.ComboBox();
        KeteranganKulit = new widget.TextBox();
        jLabel81 = new widget.Label();
        Kepala = new widget.ComboBox();
        KeteranganKepala = new widget.TextBox();
        jLabel82 = new widget.Label();
        Leher = new widget.ComboBox();
        KeteranganLeher = new widget.TextBox();
        jLabel83 = new widget.Label();
        Mata = new widget.ComboBox();
        KeteranganMata = new widget.TextBox();
        jLabel84 = new widget.Label();
        Hidung = new widget.ComboBox();
        KeteranganHidung = new widget.TextBox();
        jLabel85 = new widget.Label();
        Telinga = new widget.ComboBox();
        KeteranganTelinga = new widget.TextBox();
        jLabel86 = new widget.Label();
        Dada = new widget.ComboBox();
        KeteranganDada = new widget.TextBox();
        KeteranganParu = new widget.TextBox();
        Paru = new widget.ComboBox();
        jLabel87 = new widget.Label();
        jLabel88 = new widget.Label();
        Jantung = new widget.ComboBox();
        KeteranganJantung = new widget.TextBox();
        jLabel89 = new widget.Label();
        Perut = new widget.ComboBox();
        KeteranganPerut = new widget.TextBox();
        jLabel90 = new widget.Label();
        TaliPusat = new widget.ComboBox();
        KeteranganTaliPusat = new widget.TextBox();
        jLabel91 = new widget.Label();
        AlatKelamin = new widget.ComboBox();
        KeteranganAlatKelamin = new widget.TextBox();
        jLabel92 = new widget.Label();
        RuasTulangBelakang = new widget.ComboBox();
        KeteranganRuasTulangBelakang = new widget.TextBox();
        jLabel93 = new widget.Label();
        Extrimitas = new widget.ComboBox();
        KeteranganExtrimitas = new widget.TextBox();
        jLabel94 = new widget.Label();
        Anus = new widget.ComboBox();
        KeteranganAnus = new widget.TextBox();
        jLabel95 = new widget.Label();
        Refleks = new widget.ComboBox();
        KeteranganRefleks = new widget.TextBox();
        jLabel96 = new widget.Label();
        DenyutFemoral = new widget.ComboBox();
        KeteranganDenyutFemoral = new widget.TextBox();
        jLabel97 = new widget.Label();
        scrollPane12 = new widget.ScrollPane();
        PemeriksaanLainnya = new widget.TextArea();
        jSeparator14 = new javax.swing.JSeparator();
        jLabel139 = new widget.Label();
        scrollPane13 = new widget.ScrollPane();
        PemeriksaanPenunjang = new widget.TextArea();
        jSeparator15 = new javax.swing.JSeparator();
        jLabel140 = new widget.Label();
        scrollPane14 = new widget.ScrollPane();
        Diagnosis = new widget.TextArea();
        jSeparator16 = new javax.swing.JSeparator();
        jLabel141 = new widget.Label();
        scrollPane15 = new widget.ScrollPane();
        Tatalaksana = new widget.TextArea();
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

        DlgRiwayatPersalinan.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        DlgRiwayatPersalinan.setName("DlgRiwayatPersalinan"); // NOI18N
        DlgRiwayatPersalinan.setUndecorated(true);
        DlgRiwayatPersalinan.setResizable(false);

        internalFrame4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(230, 235, 225)), "::[ Riwayat Persalinan Ibu Bayi ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 50))); // NOI18N
        internalFrame4.setName("internalFrame4"); // NOI18N
        internalFrame4.setLayout(new java.awt.BorderLayout(1, 1));

        panelBiasa2.setName("panelBiasa2"); // NOI18N
        panelBiasa2.setLayout(null);

        TanggalPersalinan.setForeground(new java.awt.Color(50, 70, 50));
        TanggalPersalinan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "26-02-2025" }));
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

        TanggalRegistrasi.setHighlighter(null);
        TanggalRegistrasi.setName("TanggalRegistrasi"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Penilaian Bayi Baru Lahir ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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
        FormInput.setPreferredSize(new java.awt.Dimension(870, 1673));
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

        label14.setText("Dokter PJ :");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label14);
        label14.setBounds(0, 40, 70, 23);

        KdDokter.setEditable(false);
        KdDokter.setName("KdDokter"); // NOI18N
        KdDokter.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput.add(KdDokter);
        KdDokter.setBounds(74, 40, 130, 23);

        NmDokter.setEditable(false);
        NmDokter.setName("NmDokter"); // NOI18N
        NmDokter.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmDokter);
        NmDokter.setBounds(206, 40, 417, 23);

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
        BtnDokter.setBounds(625, 40, 28, 23);

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

        jSeparator1.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator1.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator1.setName("jSeparator1"); // NOI18N
        FormInput.add(jSeparator1);
        jSeparator1.setBounds(0, 100, 880, 1);

        jLabel99.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel99.setText("I. RIWAYAT MATERNAL");
        jLabel99.setName("jLabel99"); // NOI18N
        FormInput.add(jLabel99);
        jLabel99.setBounds(10, 100, 180, 23);

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label11);
        label11.setBounds(668, 40, 52, 23);

        TglAsuhan.setForeground(new java.awt.Color(50, 70, 50));
        TglAsuhan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "26-02-2025 08:37:49" }));
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

        Scroll6.setName("Scroll6"); // NOI18N
        Scroll6.setOpaque(true);

        tbRiwayatKehamilan.setName("tbRiwayatKehamilan"); // NOI18N
        Scroll6.setViewportView(tbRiwayatKehamilan);

        FormInput.add(Scroll6);
        Scroll6.setBounds(110, 200, 744, 93);

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
        BtnTambahMasalah.setBounds(77, 200, 28, 23);

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
        BtnHapusRiwayatPersalinan.setBounds(77, 230, 28, 23);

        jSeparator2.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator2.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator2.setName("jSeparator2"); // NOI18N
        FormInput.add(jSeparator2);
        jSeparator2.setBounds(0, 410, 880, 1);

        jLabel115.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel115.setText("II. RIWAYAT PERSALINAN");
        jLabel115.setName("jLabel115"); // NOI18N
        FormInput.add(jLabel115);
        jLabel115.setBounds(10, 410, 180, 23);

        jLabel74.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel74.setText("APGAR Score :");
        jLabel74.setName("jLabel74"); // NOI18N
        FormInput.add(jLabel74);
        jLabel74.setBounds(44, 700, 182, 23);

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
        Scroll2.setBounds(77, 720, 777, 127);

        label71.setText("Jumlah Nilai :");
        label71.setName("label71"); // NOI18N
        label71.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label71);
        label71.setBounds(660, 846, 100, 27);

        N1.setEditable(false);
        N1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(150, 150, 150)));
        N1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        N1.setName("N1"); // NOI18N
        FormInput.add(N1);
        N1.setBounds(764, 846, 29, 27);

        N5.setEditable(false);
        N5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(150, 150, 150)));
        N5.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        N5.setName("N5"); // NOI18N
        FormInput.add(N5);
        N5.setBounds(792, 846, 29, 27);

        N10.setEditable(false);
        N10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(150, 150, 150)));
        N10.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        N10.setName("N10"); // NOI18N
        FormInput.add(N10);
        N10.setBounds(820, 846, 34, 27);

        jLabel14.setText("Ibu Bayi :");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(0, 70, 70, 23);

        NoRMIbu.setEditable(false);
        NoRMIbu.setHighlighter(null);
        NoRMIbu.setName("NoRMIbu"); // NOI18N
        FormInput.add(NoRMIbu);
        NoRMIbu.setBounds(74, 70, 100, 23);

        NmIbu.setEditable(false);
        NmIbu.setHighlighter(null);
        NmIbu.setName("NmIbu"); // NOI18N
        FormInput.add(NmIbu);
        NmIbu.setBounds(176, 70, 270, 23);

        BtnIbuBayi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnIbuBayi.setMnemonic('2');
        BtnIbuBayi.setToolTipText("Alt+2");
        BtnIbuBayi.setName("BtnIbuBayi"); // NOI18N
        BtnIbuBayi.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnIbuBayi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnIbuBayiActionPerformed(evt);
            }
        });
        BtnIbuBayi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnIbuBayiKeyPressed(evt);
            }
        });
        FormInput.add(BtnIbuBayi);
        BtnIbuBayi.setBounds(448, 70, 28, 23);

        jLabel9.setText("Tgl.Lahir Ibu :");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(479, 70, 90, 23);

        TglLahirIbu.setEditable(false);
        TglLahirIbu.setHighlighter(null);
        TglLahirIbu.setName("TglLahirIbu"); // NOI18N
        FormInput.add(TglLahirIbu);
        TglLahirIbu.setBounds(573, 70, 80, 23);

        jLabel15.setText("NIK Ibu :");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(660, 70, 60, 23);

        NIKIbu.setEditable(false);
        NIKIbu.setHighlighter(null);
        NIKIbu.setName("NIKIbu"); // NOI18N
        FormInput.add(NIKIbu);
        NIKIbu.setBounds(724, 70, 130, 23);

        jLabel17.setText(":");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(0, 120, 160, 23);

        StatusPerkawinanIbu.setEditable(false);
        StatusPerkawinanIbu.setHighlighter(null);
        StatusPerkawinanIbu.setName("StatusPerkawinanIbu"); // NOI18N
        FormInput.add(StatusPerkawinanIbu);
        StatusPerkawinanIbu.setBounds(164, 120, 110, 23);

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel16.setText("Status Perkawinan Ibu");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput.add(jLabel16);
        jLabel16.setBounds(44, 120, 130, 23);

        jLabel38.setText("Penyakit Yang Diderita Ibu :");
        jLabel38.setName("jLabel38"); // NOI18N
        FormInput.add(jLabel38);
        jLabel38.setBounds(275, 120, 150, 23);

        PenyakitIbu.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada", "Ada" }));
        PenyakitIbu.setName("PenyakitIbu"); // NOI18N
        PenyakitIbu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenyakitIbuKeyPressed(evt);
            }
        });
        FormInput.add(PenyakitIbu);
        PenyakitIbu.setBounds(429, 120, 100, 23);

        KeteranganPenyakitIbu.setHighlighter(null);
        KeteranganPenyakitIbu.setName("KeteranganPenyakitIbu"); // NOI18N
        KeteranganPenyakitIbu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganPenyakitIbuKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganPenyakitIbu);
        KeteranganPenyakitIbu.setBounds(532, 120, 322, 23);

        jLabel39.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel39.setText("Obat Yang Dikonsumsi Selama Kehamilan");
        jLabel39.setName("jLabel39"); // NOI18N
        FormInput.add(jLabel39);
        jLabel39.setBounds(44, 150, 220, 23);

        ObatSelamaHamil.setHighlighter(null);
        ObatSelamaHamil.setName("ObatSelamaHamil"); // NOI18N
        ObatSelamaHamil.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ObatSelamaHamilKeyPressed(evt);
            }
        });
        FormInput.add(ObatSelamaHamil);
        ObatSelamaHamil.setBounds(257, 150, 597, 23);

        jLabel18.setText(":");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(0, 150, 253, 23);

        jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel40.setText("Riwayat Kehamilan Terdahulu :");
        jLabel40.setName("jLabel40"); // NOI18N
        FormInput.add(jLabel40);
        jLabel40.setBounds(44, 180, 190, 23);

        jLabel41.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel41.setText("Kehamilan Sekarang :");
        jLabel41.setName("jLabel41"); // NOI18N
        FormInput.add(jLabel41);
        jLabel41.setBounds(44, 300, 190, 23);

        PerawatanAntenatal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        PerawatanAntenatal.setName("PerawatanAntenatal"); // NOI18N
        PerawatanAntenatal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PerawatanAntenatalKeyPressed(evt);
            }
        });
        FormInput.add(PerawatanAntenatal);
        PerawatanAntenatal.setBounds(190, 320, 80, 23);

        jLabel42.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel42.setText("Perawatan Antenatal");
        jLabel42.setName("jLabel42"); // NOI18N
        FormInput.add(jLabel42);
        jLabel42.setBounds(77, 320, 140, 23);

        KeteranganPerawatanAntenatal.setHighlighter(null);
        KeteranganPerawatanAntenatal.setName("KeteranganPerawatanAntenatal"); // NOI18N
        KeteranganPerawatanAntenatal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganPerawatanAntenatalKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganPerawatanAntenatal);
        KeteranganPerawatanAntenatal.setBounds(273, 320, 200, 23);

        jLabel20.setText(":");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(0, 320, 186, 23);

        KeteranganTerdaftarEkohort.setHighlighter(null);
        KeteranganTerdaftarEkohort.setName("KeteranganTerdaftarEkohort"); // NOI18N
        KeteranganTerdaftarEkohort.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganTerdaftarEkohortKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganTerdaftarEkohort);
        KeteranganTerdaftarEkohort.setBounds(694, 320, 160, 23);

        TerdaftarEkohort.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        TerdaftarEkohort.setName("TerdaftarEkohort"); // NOI18N
        TerdaftarEkohort.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TerdaftarEkohortKeyPressed(evt);
            }
        });
        FormInput.add(TerdaftarEkohort);
        TerdaftarEkohort.setBounds(611, 320, 80, 23);

        jLabel43.setText("Terdaftar Di EKohort :");
        jLabel43.setName("jLabel43"); // NOI18N
        FormInput.add(jLabel43);
        jLabel43.setBounds(467, 320, 140, 23);

        jLabel22.setText(":");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(0, 350, 177, 23);

        PenyulitKehamilan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada", "Hiperemesis", "CPD", "Kelainan Letak", "Solutio Placenta", "Placenta Previa", "KPD", "Oligohydramnion", "Polyhydramnion", "Prolaps Tali Pusat", "IUGR", "Pre-eklamsi", "Lainnya" }));
        PenyulitKehamilan.setName("PenyulitKehamilan"); // NOI18N
        PenyulitKehamilan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenyulitKehamilanKeyPressed(evt);
            }
        });
        FormInput.add(PenyulitKehamilan);
        PenyulitKehamilan.setBounds(181, 350, 140, 23);

        jLabel44.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel44.setText("Penyulit Kehamilan");
        jLabel44.setName("jLabel44"); // NOI18N
        FormInput.add(jLabel44);
        jLabel44.setBounds(77, 350, 140, 23);

        KeteranganPenyulitKehamilan.setHighlighter(null);
        KeteranganPenyulitKehamilan.setName("KeteranganPenyulitKehamilan"); // NOI18N
        KeteranganPenyulitKehamilan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganPenyulitKehamilanKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganPenyulitKehamilan);
        KeteranganPenyulitKehamilan.setBounds(324, 350, 255, 23);

        jLabel45.setText("Alergi :");
        jLabel45.setName("jLabel45"); // NOI18N
        FormInput.add(jLabel45);
        jLabel45.setBounds(580, 350, 60, 23);

        AlergiIbu.setHighlighter(null);
        AlergiIbu.setName("AlergiIbu"); // NOI18N
        AlergiIbu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlergiIbuKeyPressed(evt);
            }
        });
        FormInput.add(AlergiIbu);
        AlergiIbu.setBounds(644, 350, 210, 23);

        KeteranganRiwayatMaternal.setHighlighter(null);
        KeteranganRiwayatMaternal.setName("KeteranganRiwayatMaternal"); // NOI18N
        KeteranganRiwayatMaternal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganRiwayatMaternalKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganRiwayatMaternal);
        KeteranganRiwayatMaternal.setBounds(151, 380, 703, 23);

        jLabel46.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel46.setText("Keterangan Lainnya");
        jLabel46.setName("jLabel46"); // NOI18N
        FormInput.add(jLabel46);
        jLabel46.setBounds(44, 380, 120, 23);

        jLabel23.setText(":");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(0, 380, 147, 23);

        UmurKehamilan.setEditable(false);
        UmurKehamilan.setHighlighter(null);
        UmurKehamilan.setName("UmurKehamilan"); // NOI18N
        UmurKehamilan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                UmurKehamilanKeyPressed(evt);
            }
        });
        FormInput.add(UmurKehamilan);
        UmurKehamilan.setBounds(135, 430, 180, 23);

        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel24.setText("Umur Kehamilan");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(44, 430, 130, 23);

        jLabel25.setText(":");
        jLabel25.setName("jLabel25"); // NOI18N
        FormInput.add(jLabel25);
        jLabel25.setBounds(0, 430, 131, 23);

        jLabel47.setText("Kehamilan :");
        jLabel47.setName("jLabel47"); // NOI18N
        FormInput.add(jLabel47);
        jLabel47.setBounds(320, 430, 80, 23);

        Kehamilan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tunggal", "Kembar" }));
        Kehamilan.setName("Kehamilan"); // NOI18N
        Kehamilan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KehamilanKeyPressed(evt);
            }
        });
        FormInput.add(Kehamilan);
        Kehamilan.setBounds(404, 430, 90, 23);

        KeteranganKehamilan.setHighlighter(null);
        KeteranganKehamilan.setName("KeteranganKehamilan"); // NOI18N
        KeteranganKehamilan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKehamilanKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganKehamilan);
        KeteranganKehamilan.setBounds(497, 430, 185, 23);

        jLabel48.setText("Urutan Kehamilan :");
        jLabel48.setName("jLabel48"); // NOI18N
        FormInput.add(jLabel48);
        jLabel48.setBounds(690, 430, 110, 23);

        UrutanKehamilan.setHighlighter(null);
        UrutanKehamilan.setName("UrutanKehamilan"); // NOI18N
        UrutanKehamilan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                UrutanKehamilanKeyPressed(evt);
            }
        });
        FormInput.add(UrutanKehamilan);
        UrutanKehamilan.setBounds(804, 430, 50, 23);

        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel26.setText("Air Ketuban :");
        jLabel26.setName("jLabel26"); // NOI18N
        FormInput.add(jLabel26);
        jLabel26.setBounds(310, 460, 90, 23);

        jLabel27.setText(":");
        jLabel27.setName("jLabel27"); // NOI18N
        FormInput.add(jLabel27);
        jLabel27.setBounds(0, 480, 104, 23);

        JamKetubanPecah.setEditable(false);
        JamKetubanPecah.setHighlighter(null);
        JamKetubanPecah.setName("JamKetubanPecah"); // NOI18N
        JamKetubanPecah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JamKetubanPecahKeyPressed(evt);
            }
        });
        FormInput.add(JamKetubanPecah);
        JamKetubanPecah.setBounds(108, 480, 50, 23);

        jLabel49.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel49.setText("Jam");
        jLabel49.setName("jLabel49"); // NOI18N
        FormInput.add(jLabel49);
        jLabel49.setBounds(77, 480, 40, 23);

        jLabel28.setText("Menit :");
        jLabel28.setName("jLabel28"); // NOI18N
        FormInput.add(jLabel28);
        jLabel28.setBounds(155, 480, 50, 23);

        MenitKetubanPecah.setEditable(false);
        MenitKetubanPecah.setHighlighter(null);
        MenitKetubanPecah.setName("MenitKetubanPecah"); // NOI18N
        MenitKetubanPecah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MenitKetubanPecahKeyPressed(evt);
            }
        });
        FormInput.add(MenitKetubanPecah);
        MenitKetubanPecah.setBounds(209, 480, 50, 23);

        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel29.setText("Ketuban Pecah Sebelum Lahir :");
        jLabel29.setName("jLabel29"); // NOI18N
        FormInput.add(jLabel29);
        jLabel29.setBounds(44, 460, 180, 23);

        jLabel30.setText("Jumlah :");
        jLabel30.setName("jLabel30"); // NOI18N
        FormInput.add(jLabel30);
        jLabel30.setBounds(335, 480, 50, 23);

        JumlahAirKetuban.setEditable(false);
        JumlahAirKetuban.setHighlighter(null);
        JumlahAirKetuban.setName("JumlahAirKetuban"); // NOI18N
        JumlahAirKetuban.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JumlahAirKetubanKeyPressed(evt);
            }
        });
        FormInput.add(JumlahAirKetuban);
        JumlahAirKetuban.setBounds(389, 480, 120, 23);

        WarnaAirKetuban.setEditable(false);
        WarnaAirKetuban.setHighlighter(null);
        WarnaAirKetuban.setName("WarnaAirKetuban"); // NOI18N
        WarnaAirKetuban.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                WarnaAirKetubanKeyPressed(evt);
            }
        });
        FormInput.add(WarnaAirKetuban);
        WarnaAirKetuban.setBounds(569, 480, 120, 23);

        jLabel31.setText("Warna :");
        jLabel31.setName("jLabel31"); // NOI18N
        FormInput.add(jLabel31);
        jLabel31.setBounds(515, 480, 50, 23);

        BauAirKetuban.setEditable(false);
        BauAirKetuban.setHighlighter(null);
        BauAirKetuban.setName("BauAirKetuban"); // NOI18N
        BauAirKetuban.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BauAirKetubanKeyPressed(evt);
            }
        });
        FormInput.add(BauAirKetuban);
        BauAirKetuban.setBounds(734, 480, 120, 23);

        jLabel32.setText("Bau :");
        jLabel32.setName("jLabel32"); // NOI18N
        FormInput.add(jLabel32);
        jLabel32.setBounds(680, 480, 50, 23);

        jLabel34.setText(":");
        jLabel34.setName("jLabel34"); // NOI18N
        FormInput.add(jLabel34);
        jLabel34.setBounds(0, 510, 99, 23);

        LetakBayi.setEditable(false);
        LetakBayi.setHighlighter(null);
        LetakBayi.setName("LetakBayi"); // NOI18N
        LetakBayi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LetakBayiKeyPressed(evt);
            }
        });
        FormInput.add(LetakBayi);
        LetakBayi.setBounds(103, 510, 330, 23);

        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel33.setText("Letak Bayi");
        jLabel33.setName("jLabel33"); // NOI18N
        FormInput.add(jLabel33);
        jLabel33.setBounds(44, 510, 130, 23);

        jLabel50.setText("Macam Persalinan :");
        jLabel50.setName("jLabel50"); // NOI18N
        FormInput.add(jLabel50);
        jLabel50.setBounds(451, 510, 100, 23);

        MacamPersalinan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Spontan", "Porceps", "Vacum", "Sectio Caesarea", "Lainnya" }));
        MacamPersalinan.setName("MacamPersalinan"); // NOI18N
        MacamPersalinan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MacamPersalinanKeyPressed(evt);
            }
        });
        FormInput.add(MacamPersalinan);
        MacamPersalinan.setBounds(555, 510, 132, 23);

        KeteranganMacamPersalinan.setHighlighter(null);
        KeteranganMacamPersalinan.setName("KeteranganMacamPersalinan"); // NOI18N
        KeteranganMacamPersalinan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganMacamPersalinanKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganMacamPersalinan);
        KeteranganMacamPersalinan.setBounds(690, 510, 164, 23);

        jLabel35.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel35.setText("Indikasi Persalinan Operatif");
        jLabel35.setName("jLabel35"); // NOI18N
        FormInput.add(jLabel35);
        jLabel35.setBounds(44, 540, 150, 23);

        jLabel36.setText(":");
        jLabel36.setName("jLabel36"); // NOI18N
        FormInput.add(jLabel36);
        jLabel36.setBounds(0, 540, 187, 23);

        IndikasiPersalinanOperatif.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada", "Gawat Janin", "SC Sebelumnya", "Kala II Memanjang", "Komplikasi Tali Pusat", "Malposisi", "Gawat Ibu", "CPD", "Lainnya" }));
        IndikasiPersalinanOperatif.setName("IndikasiPersalinanOperatif"); // NOI18N
        IndikasiPersalinanOperatif.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                IndikasiPersalinanOperatifKeyPressed(evt);
            }
        });
        FormInput.add(IndikasiPersalinanOperatif);
        IndikasiPersalinanOperatif.setBounds(191, 540, 155, 23);

        KeteranganIndikasiPersalinanOperatif.setHighlighter(null);
        KeteranganIndikasiPersalinanOperatif.setName("KeteranganIndikasiPersalinanOperatif"); // NOI18N
        KeteranganIndikasiPersalinanOperatif.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganIndikasiPersalinanOperatifKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganIndikasiPersalinanOperatif);
        KeteranganIndikasiPersalinanOperatif.setBounds(349, 540, 225, 23);

        jLabel37.setText("Lama Gawat Janin Sebelum Lahir :");
        jLabel37.setName("jLabel37"); // NOI18N
        FormInput.add(jLabel37);
        jLabel37.setBounds(581, 540, 187, 23);

        LamaGawatJanin.setEditable(false);
        LamaGawatJanin.setHighlighter(null);
        LamaGawatJanin.setName("LamaGawatJanin"); // NOI18N
        LamaGawatJanin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LamaGawatJaninKeyPressed(evt);
            }
        });
        FormInput.add(LamaGawatJanin);
        LamaGawatJanin.setBounds(772, 540, 50, 23);

        jLabel51.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel51.setText("gram");
        jLabel51.setName("jLabel51"); // NOI18N
        FormInput.add(jLabel51);
        jLabel51.setBounds(167, 880, 40, 23);

        jLabel52.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel52.setText("Obat-obatan Selama Persalinan");
        jLabel52.setName("jLabel52"); // NOI18N
        FormInput.add(jLabel52);
        jLabel52.setBounds(44, 570, 180, 23);

        jLabel53.setText(":");
        jLabel53.setName("jLabel53"); // NOI18N
        FormInput.add(jLabel53);
        jLabel53.setBounds(0, 570, 206, 23);

        ObatSelamaPersalinan.setEditable(false);
        ObatSelamaPersalinan.setHighlighter(null);
        ObatSelamaPersalinan.setName("ObatSelamaPersalinan"); // NOI18N
        ObatSelamaPersalinan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ObatSelamaPersalinanKeyPressed(evt);
            }
        });
        FormInput.add(ObatSelamaPersalinan);
        ObatSelamaPersalinan.setBounds(210, 570, 644, 23);

        jLabel54.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel54.setText("Placenta :");
        jLabel54.setName("jLabel54"); // NOI18N
        FormInput.add(jLabel54);
        jLabel54.setBounds(44, 600, 180, 23);

        jLabel56.setText(":");
        jLabel56.setName("jLabel56"); // NOI18N
        FormInput.add(jLabel56);
        jLabel56.setBounds(0, 620, 110, 23);

        BeratPlacenta.setEditable(false);
        BeratPlacenta.setHighlighter(null);
        BeratPlacenta.setName("BeratPlacenta"); // NOI18N
        BeratPlacenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BeratPlacentaKeyPressed(evt);
            }
        });
        FormInput.add(BeratPlacenta);
        BeratPlacenta.setBounds(114, 620, 50, 23);

        jLabel55.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel55.setText("Berat");
        jLabel55.setName("jLabel55"); // NOI18N
        FormInput.add(jLabel55);
        jLabel55.setBounds(77, 620, 50, 23);

        jLabel57.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel57.setText("menit");
        jLabel57.setName("jLabel57"); // NOI18N
        FormInput.add(jLabel57);
        jLabel57.setBounds(824, 540, 40, 23);

        jLabel58.setText("Kelainan :");
        jLabel58.setName("jLabel58"); // NOI18N
        FormInput.add(jLabel58);
        jLabel58.setBounds(200, 620, 60, 23);

        KelainanPlacenta.setHighlighter(null);
        KelainanPlacenta.setName("KelainanPlacenta"); // NOI18N
        KelainanPlacenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KelainanPlacentaKeyPressed(evt);
            }
        });
        FormInput.add(KelainanPlacenta);
        KelainanPlacenta.setBounds(264, 620, 590, 23);

        jSeparator3.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator3.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator3.setName("jSeparator3"); // NOI18N
        FormInput.add(jSeparator3);
        jSeparator3.setBounds(0, 680, 880, 1);

        jLabel116.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel116.setText("III. KEADAAN BAYI");
        jLabel116.setName("jLabel116"); // NOI18N
        FormInput.add(jLabel116);
        jLabel116.setBounds(10, 680, 180, 23);

        jLabel60.setText(":");
        jLabel60.setName("jLabel60"); // NOI18N
        FormInput.add(jLabel60);
        jLabel60.setBounds(0, 880, 91, 23);

        BBLahir.setEditable(false);
        BBLahir.setHighlighter(null);
        BBLahir.setName("BBLahir"); // NOI18N
        BBLahir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BBLahirKeyPressed(evt);
            }
        });
        FormInput.add(BBLahir);
        BBLahir.setBounds(95, 880, 70, 23);

        jLabel59.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel59.setText("BB Lahir");
        jLabel59.setName("jLabel59"); // NOI18N
        FormInput.add(jLabel59);
        jLabel59.setBounds(44, 880, 70, 23);

        jLabel61.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel61.setText("gram");
        jLabel61.setName("jLabel61"); // NOI18N
        FormInput.add(jLabel61);
        jLabel61.setBounds(166, 620, 40, 23);

        jLabel62.setText("Panjang Badan :");
        jLabel62.setName("jLabel62"); // NOI18N
        FormInput.add(jLabel62);
        jLabel62.setBounds(228, 880, 91, 23);

        PanjangBadan.setEditable(false);
        PanjangBadan.setHighlighter(null);
        PanjangBadan.setName("PanjangBadan"); // NOI18N
        PanjangBadan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PanjangBadanKeyPressed(evt);
            }
        });
        FormInput.add(PanjangBadan);
        PanjangBadan.setBounds(323, 880, 70, 23);

        jLabel63.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel63.setText("cm");
        jLabel63.setName("jLabel63"); // NOI18N
        FormInput.add(jLabel63);
        jLabel63.setBounds(395, 880, 40, 23);

        jLabel64.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel64.setText("cm");
        jLabel64.setName("jLabel64"); // NOI18N
        FormInput.add(jLabel64);
        jLabel64.setBounds(635, 880, 40, 23);

        LingkarKepala.setEditable(false);
        LingkarKepala.setHighlighter(null);
        LingkarKepala.setName("LingkarKepala"); // NOI18N
        LingkarKepala.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LingkarKepalaKeyPressed(evt);
            }
        });
        FormInput.add(LingkarKepala);
        LingkarKepala.setBounds(563, 880, 70, 23);

        jLabel65.setText("Lingkar Kepala (FO) :");
        jLabel65.setName("jLabel65"); // NOI18N
        FormInput.add(jLabel65);
        jLabel65.setBounds(439, 880, 120, 23);

        jLabel66.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel66.setText("cm");
        jLabel66.setName("jLabel66"); // NOI18N
        FormInput.add(jLabel66);
        jLabel66.setBounds(839, 880, 40, 23);

        LingkarDada.setEditable(false);
        LingkarDada.setHighlighter(null);
        LingkarDada.setName("LingkarDada"); // NOI18N
        LingkarDada.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LingkarDadaKeyPressed(evt);
            }
        });
        FormInput.add(LingkarDada);
        LingkarDada.setBounds(767, 880, 70, 23);

        jLabel67.setText("Lingkar Dada :");
        jLabel67.setName("jLabel67"); // NOI18N
        FormInput.add(jLabel67);
        jLabel67.setBounds(672, 880, 91, 23);

        jLabel68.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel68.setText("Jenis Kelamin");
        jLabel68.setName("jLabel68"); // NOI18N
        FormInput.add(jLabel68);
        jLabel68.setBounds(44, 910, 80, 23);

        jLabel69.setText(":");
        jLabel69.setName("jLabel69"); // NOI18N
        FormInput.add(jLabel69);
        jLabel69.setBounds(0, 910, 118, 23);

        JenisKelamin.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Laki-laki", "Perempuan", "Ambigious" }));
        JenisKelamin.setName("JenisKelamin"); // NOI18N
        JenisKelamin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JenisKelaminKeyPressed(evt);
            }
        });
        FormInput.add(JenisKelamin);
        JenisKelamin.setBounds(122, 910, 110, 23);

        jLabel70.setText("Resusitasi Saat Lahir :");
        jLabel70.setName("jLabel70"); // NOI18N
        FormInput.add(jLabel70);
        jLabel70.setBounds(245, 910, 130, 23);

        ResusitasiLahir.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Rangsang Taktil", "O2", "Ventilasi Dengan Maskre", "Ventilasi Dengan EET", "Lainnya" }));
        ResusitasiLahir.setName("ResusitasiLahir"); // NOI18N
        ResusitasiLahir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ResusitasiLahirKeyPressed(evt);
            }
        });
        FormInput.add(ResusitasiLahir);
        ResusitasiLahir.setBounds(379, 910, 171, 23);

        KeteranganResusitasiLahir.setHighlighter(null);
        KeteranganResusitasiLahir.setName("KeteranganResusitasiLahir"); // NOI18N
        KeteranganResusitasiLahir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganResusitasiLahirKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganResusitasiLahir);
        KeteranganResusitasiLahir.setBounds(553, 910, 301, 23);

        jLabel71.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel71.setText("Obat Yang Diberikan Saat Lahir");
        jLabel71.setName("jLabel71"); // NOI18N
        FormInput.add(jLabel71);
        jLabel71.setBounds(44, 940, 180, 23);

        ObatSaatLahir.setEditable(false);
        ObatSaatLahir.setHighlighter(null);
        ObatSaatLahir.setName("ObatSaatLahir"); // NOI18N
        ObatSaatLahir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ObatSaatLahirKeyPressed(evt);
            }
        });
        FormInput.add(ObatSaatLahir);
        ObatSaatLahir.setBounds(208, 940, 646, 23);

        jLabel72.setText(":");
        jLabel72.setName("jLabel72"); // NOI18N
        FormInput.add(jLabel72);
        jLabel72.setBounds(0, 940, 204, 23);

        jLabel73.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel73.setText("Keterangan Lainnya");
        jLabel73.setName("jLabel73"); // NOI18N
        FormInput.add(jLabel73);
        jLabel73.setBounds(44, 650, 120, 23);

        jLabel75.setText(":");
        jLabel75.setName("jLabel75"); // NOI18N
        FormInput.add(jLabel75);
        jLabel75.setBounds(0, 650, 147, 23);

        KeteranganRiwayatPersalinan.setHighlighter(null);
        KeteranganRiwayatPersalinan.setName("KeteranganRiwayatPersalinan"); // NOI18N
        KeteranganRiwayatPersalinan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganRiwayatPersalinanKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganRiwayatPersalinan);
        KeteranganRiwayatPersalinan.setBounds(151, 650, 703, 23);

        jLabel76.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel76.setText("Keterangan Lainnya");
        jLabel76.setName("jLabel76"); // NOI18N
        FormInput.add(jLabel76);
        jLabel76.setBounds(44, 970, 120, 23);

        jLabel77.setText(":");
        jLabel77.setName("jLabel77"); // NOI18N
        FormInput.add(jLabel77);
        jLabel77.setBounds(0, 970, 147, 23);

        KeteranganKeadaanBayi.setHighlighter(null);
        KeteranganKeadaanBayi.setName("KeteranganKeadaanBayi"); // NOI18N
        KeteranganKeadaanBayi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKeadaanBayiKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganKeadaanBayi);
        KeteranganKeadaanBayi.setBounds(151, 970, 703, 23);

        jSeparator4.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator4.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator4.setName("jSeparator4"); // NOI18N
        FormInput.add(jSeparator4);
        jSeparator4.setBounds(0, 1000, 880, 1);

        jLabel117.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel117.setText("IV. PEMERIKSAAN FISIK");
        jLabel117.setName("jLabel117"); // NOI18N
        FormInput.add(jLabel117);
        jLabel117.setBounds(10, 1000, 180, 23);

        KondisiUmum.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        KondisiUmum.setName("KondisiUmum"); // NOI18N
        KondisiUmum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KondisiUmumKeyPressed(evt);
            }
        });
        FormInput.add(KondisiUmum);
        KondisiUmum.setBounds(124, 1020, 129, 23);

        jLabel78.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel78.setText("Kondisi Umum");
        jLabel78.setName("jLabel78"); // NOI18N
        FormInput.add(jLabel78);
        jLabel78.setBounds(44, 1020, 128, 23);

        KeteranganKondisiUmum.setFocusTraversalPolicyProvider(true);
        KeteranganKondisiUmum.setName("KeteranganKondisiUmum"); // NOI18N
        KeteranganKondisiUmum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKondisiUmumKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganKondisiUmum);
        KeteranganKondisiUmum.setBounds(256, 1020, 160, 23);

        jLabel79.setText(":");
        jLabel79.setName("jLabel79"); // NOI18N
        FormInput.add(jLabel79);
        jLabel79.setBounds(0, 1020, 120, 23);

        jLabel80.setText("Kulit :");
        jLabel80.setName("jLabel80"); // NOI18N
        FormInput.add(jLabel80);
        jLabel80.setBounds(0, 1050, 120, 23);

        Kulit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Kulit.setName("Kulit"); // NOI18N
        Kulit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KulitKeyPressed(evt);
            }
        });
        FormInput.add(Kulit);
        Kulit.setBounds(124, 1050, 129, 23);

        KeteranganKulit.setFocusTraversalPolicyProvider(true);
        KeteranganKulit.setName("KeteranganKulit"); // NOI18N
        KeteranganKulit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKulitKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganKulit);
        KeteranganKulit.setBounds(256, 1050, 160, 23);

        jLabel81.setText("Kepala :");
        jLabel81.setName("jLabel81"); // NOI18N
        FormInput.add(jLabel81);
        jLabel81.setBounds(0, 1080, 120, 23);

        Kepala.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Kepala.setName("Kepala"); // NOI18N
        Kepala.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KepalaKeyPressed(evt);
            }
        });
        FormInput.add(Kepala);
        Kepala.setBounds(124, 1080, 129, 23);

        KeteranganKepala.setFocusTraversalPolicyProvider(true);
        KeteranganKepala.setName("KeteranganKepala"); // NOI18N
        KeteranganKepala.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKepalaKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganKepala);
        KeteranganKepala.setBounds(256, 1080, 160, 23);

        jLabel82.setText("Leher :");
        jLabel82.setName("jLabel82"); // NOI18N
        FormInput.add(jLabel82);
        jLabel82.setBounds(0, 1110, 120, 23);

        Leher.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Leher.setName("Leher"); // NOI18N
        Leher.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LeherKeyPressed(evt);
            }
        });
        FormInput.add(Leher);
        Leher.setBounds(124, 1110, 129, 23);

        KeteranganLeher.setFocusTraversalPolicyProvider(true);
        KeteranganLeher.setName("KeteranganLeher"); // NOI18N
        KeteranganLeher.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganLeherKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganLeher);
        KeteranganLeher.setBounds(256, 1110, 160, 23);

        jLabel83.setText("Mata :");
        jLabel83.setName("jLabel83"); // NOI18N
        FormInput.add(jLabel83);
        jLabel83.setBounds(0, 1140, 120, 23);

        Mata.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Mata.setName("Mata"); // NOI18N
        Mata.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MataKeyPressed(evt);
            }
        });
        FormInput.add(Mata);
        Mata.setBounds(124, 1140, 129, 23);

        KeteranganMata.setFocusTraversalPolicyProvider(true);
        KeteranganMata.setName("KeteranganMata"); // NOI18N
        KeteranganMata.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganMataKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganMata);
        KeteranganMata.setBounds(256, 1140, 160, 23);

        jLabel84.setText("Hidung :");
        jLabel84.setName("jLabel84"); // NOI18N
        FormInput.add(jLabel84);
        jLabel84.setBounds(0, 1170, 120, 23);

        Hidung.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Hidung.setName("Hidung"); // NOI18N
        Hidung.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HidungKeyPressed(evt);
            }
        });
        FormInput.add(Hidung);
        Hidung.setBounds(124, 1170, 129, 23);

        KeteranganHidung.setFocusTraversalPolicyProvider(true);
        KeteranganHidung.setName("KeteranganHidung"); // NOI18N
        KeteranganHidung.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganHidungKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganHidung);
        KeteranganHidung.setBounds(256, 1170, 160, 23);

        jLabel85.setText("Telinga :");
        jLabel85.setName("jLabel85"); // NOI18N
        FormInput.add(jLabel85);
        jLabel85.setBounds(0, 1200, 120, 23);

        Telinga.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Telinga.setName("Telinga"); // NOI18N
        Telinga.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TelingaKeyPressed(evt);
            }
        });
        FormInput.add(Telinga);
        Telinga.setBounds(124, 1200, 129, 23);

        KeteranganTelinga.setFocusTraversalPolicyProvider(true);
        KeteranganTelinga.setName("KeteranganTelinga"); // NOI18N
        KeteranganTelinga.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganTelingaKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganTelinga);
        KeteranganTelinga.setBounds(256, 1200, 160, 23);

        jLabel86.setText("Dada :");
        jLabel86.setName("jLabel86"); // NOI18N
        FormInput.add(jLabel86);
        jLabel86.setBounds(0, 1230, 120, 23);

        Dada.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Dada.setName("Dada"); // NOI18N
        Dada.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DadaKeyPressed(evt);
            }
        });
        FormInput.add(Dada);
        Dada.setBounds(124, 1230, 129, 23);

        KeteranganDada.setFocusTraversalPolicyProvider(true);
        KeteranganDada.setName("KeteranganDada"); // NOI18N
        KeteranganDada.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganDadaKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganDada);
        KeteranganDada.setBounds(256, 1230, 160, 23);

        KeteranganParu.setFocusTraversalPolicyProvider(true);
        KeteranganParu.setName("KeteranganParu"); // NOI18N
        KeteranganParu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganParuKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganParu);
        KeteranganParu.setBounds(256, 1260, 160, 23);

        Paru.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Paru.setName("Paru"); // NOI18N
        Paru.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ParuKeyPressed(evt);
            }
        });
        FormInput.add(Paru);
        Paru.setBounds(124, 1260, 129, 23);

        jLabel87.setText("Paru :");
        jLabel87.setName("jLabel87"); // NOI18N
        FormInput.add(jLabel87);
        jLabel87.setBounds(0, 1260, 120, 23);

        jLabel88.setText("Jantung :");
        jLabel88.setName("jLabel88"); // NOI18N
        FormInput.add(jLabel88);
        jLabel88.setBounds(0, 1290, 120, 23);

        Jantung.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Jantung.setName("Jantung"); // NOI18N
        Jantung.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JantungKeyPressed(evt);
            }
        });
        FormInput.add(Jantung);
        Jantung.setBounds(124, 1290, 129, 23);

        KeteranganJantung.setFocusTraversalPolicyProvider(true);
        KeteranganJantung.setName("KeteranganJantung"); // NOI18N
        KeteranganJantung.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganJantungKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganJantung);
        KeteranganJantung.setBounds(256, 1290, 160, 23);

        jLabel89.setText("Perut :");
        jLabel89.setName("jLabel89"); // NOI18N
        FormInput.add(jLabel89);
        jLabel89.setBounds(0, 1320, 120, 23);

        Perut.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Perut.setName("Perut"); // NOI18N
        Perut.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PerutKeyPressed(evt);
            }
        });
        FormInput.add(Perut);
        Perut.setBounds(124, 1320, 129, 23);

        KeteranganPerut.setFocusTraversalPolicyProvider(true);
        KeteranganPerut.setName("KeteranganPerut"); // NOI18N
        KeteranganPerut.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganPerutKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganPerut);
        KeteranganPerut.setBounds(256, 1320, 160, 23);

        jLabel90.setText("Tali Pusat :");
        jLabel90.setName("jLabel90"); // NOI18N
        FormInput.add(jLabel90);
        jLabel90.setBounds(417, 1020, 141, 23);

        TaliPusat.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        TaliPusat.setName("TaliPusat"); // NOI18N
        TaliPusat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TaliPusatKeyPressed(evt);
            }
        });
        FormInput.add(TaliPusat);
        TaliPusat.setBounds(562, 1020, 129, 23);

        KeteranganTaliPusat.setFocusTraversalPolicyProvider(true);
        KeteranganTaliPusat.setName("KeteranganTaliPusat"); // NOI18N
        KeteranganTaliPusat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganTaliPusatKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganTaliPusat);
        KeteranganTaliPusat.setBounds(694, 1020, 160, 23);

        jLabel91.setText("Alat Kelamin :");
        jLabel91.setName("jLabel91"); // NOI18N
        FormInput.add(jLabel91);
        jLabel91.setBounds(417, 1050, 141, 23);

        AlatKelamin.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        AlatKelamin.setName("AlatKelamin"); // NOI18N
        AlatKelamin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlatKelaminKeyPressed(evt);
            }
        });
        FormInput.add(AlatKelamin);
        AlatKelamin.setBounds(562, 1050, 129, 23);

        KeteranganAlatKelamin.setFocusTraversalPolicyProvider(true);
        KeteranganAlatKelamin.setName("KeteranganAlatKelamin"); // NOI18N
        KeteranganAlatKelamin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganAlatKelaminKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganAlatKelamin);
        KeteranganAlatKelamin.setBounds(694, 1050, 160, 23);

        jLabel92.setText("Ruas Tulang Belakang :");
        jLabel92.setName("jLabel92"); // NOI18N
        FormInput.add(jLabel92);
        jLabel92.setBounds(417, 1080, 141, 23);

        RuasTulangBelakang.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        RuasTulangBelakang.setName("RuasTulangBelakang"); // NOI18N
        RuasTulangBelakang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RuasTulangBelakangKeyPressed(evt);
            }
        });
        FormInput.add(RuasTulangBelakang);
        RuasTulangBelakang.setBounds(562, 1080, 129, 23);

        KeteranganRuasTulangBelakang.setFocusTraversalPolicyProvider(true);
        KeteranganRuasTulangBelakang.setName("KeteranganRuasTulangBelakang"); // NOI18N
        KeteranganRuasTulangBelakang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganRuasTulangBelakangKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganRuasTulangBelakang);
        KeteranganRuasTulangBelakang.setBounds(694, 1080, 160, 23);

        jLabel93.setText("Extrimitas :");
        jLabel93.setName("jLabel93"); // NOI18N
        FormInput.add(jLabel93);
        jLabel93.setBounds(417, 1110, 141, 23);

        Extrimitas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Extrimitas.setName("Extrimitas"); // NOI18N
        Extrimitas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ExtrimitasKeyPressed(evt);
            }
        });
        FormInput.add(Extrimitas);
        Extrimitas.setBounds(562, 1110, 129, 23);

        KeteranganExtrimitas.setFocusTraversalPolicyProvider(true);
        KeteranganExtrimitas.setName("KeteranganExtrimitas"); // NOI18N
        KeteranganExtrimitas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganExtrimitasKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganExtrimitas);
        KeteranganExtrimitas.setBounds(694, 1110, 160, 23);

        jLabel94.setText("Anus :");
        jLabel94.setName("jLabel94"); // NOI18N
        FormInput.add(jLabel94);
        jLabel94.setBounds(417, 1140, 141, 23);

        Anus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Anus.setName("Anus"); // NOI18N
        Anus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AnusKeyPressed(evt);
            }
        });
        FormInput.add(Anus);
        Anus.setBounds(562, 1140, 129, 23);

        KeteranganAnus.setFocusTraversalPolicyProvider(true);
        KeteranganAnus.setName("KeteranganAnus"); // NOI18N
        KeteranganAnus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganAnusKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganAnus);
        KeteranganAnus.setBounds(694, 1140, 160, 23);

        jLabel95.setText("Refleks :");
        jLabel95.setName("jLabel95"); // NOI18N
        FormInput.add(jLabel95);
        jLabel95.setBounds(417, 1170, 141, 23);

        Refleks.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Refleks.setName("Refleks"); // NOI18N
        Refleks.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RefleksKeyPressed(evt);
            }
        });
        FormInput.add(Refleks);
        Refleks.setBounds(562, 1170, 129, 23);

        KeteranganRefleks.setFocusTraversalPolicyProvider(true);
        KeteranganRefleks.setName("KeteranganRefleks"); // NOI18N
        KeteranganRefleks.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganRefleksKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganRefleks);
        KeteranganRefleks.setBounds(694, 1170, 160, 23);

        jLabel96.setText("Pemeriksaan Lainnya :");
        jLabel96.setName("jLabel96"); // NOI18N
        FormInput.add(jLabel96);
        jLabel96.setBounds(417, 1230, 141, 23);

        DenyutFemoral.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        DenyutFemoral.setName("DenyutFemoral"); // NOI18N
        DenyutFemoral.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DenyutFemoralKeyPressed(evt);
            }
        });
        FormInput.add(DenyutFemoral);
        DenyutFemoral.setBounds(562, 1200, 129, 23);

        KeteranganDenyutFemoral.setFocusTraversalPolicyProvider(true);
        KeteranganDenyutFemoral.setName("KeteranganDenyutFemoral"); // NOI18N
        KeteranganDenyutFemoral.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganDenyutFemoralKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganDenyutFemoral);
        KeteranganDenyutFemoral.setBounds(694, 1200, 160, 23);

        jLabel97.setText("Denyut Femoral/Radial :");
        jLabel97.setName("jLabel97"); // NOI18N
        FormInput.add(jLabel97);
        jLabel97.setBounds(417, 1200, 141, 23);

        scrollPane12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane12.setName("scrollPane12"); // NOI18N

        PemeriksaanLainnya.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        PemeriksaanLainnya.setColumns(20);
        PemeriksaanLainnya.setRows(20);
        PemeriksaanLainnya.setName("PemeriksaanLainnya"); // NOI18N
        PemeriksaanLainnya.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PemeriksaanLainnyaKeyPressed(evt);
            }
        });
        scrollPane12.setViewportView(PemeriksaanLainnya);

        FormInput.add(scrollPane12);
        scrollPane12.setBounds(562, 1230, 292, 113);

        jSeparator14.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator14.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator14.setName("jSeparator14"); // NOI18N
        FormInput.add(jSeparator14);
        jSeparator14.setBounds(0, 1350, 880, 1);

        jLabel139.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel139.setText("V. PEMERIKSAAN PENUNJANG");
        jLabel139.setName("jLabel139"); // NOI18N
        FormInput.add(jLabel139);
        jLabel139.setBounds(10, 1350, 190, 23);

        scrollPane13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane13.setName("scrollPane13"); // NOI18N

        PemeriksaanPenunjang.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        PemeriksaanPenunjang.setColumns(20);
        PemeriksaanPenunjang.setRows(3);
        PemeriksaanPenunjang.setName("PemeriksaanPenunjang"); // NOI18N
        PemeriksaanPenunjang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PemeriksaanPenunjangKeyPressed(evt);
            }
        });
        scrollPane13.setViewportView(PemeriksaanPenunjang);

        FormInput.add(scrollPane13);
        scrollPane13.setBounds(44, 1370, 810, 43);

        jSeparator15.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator15.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator15.setName("jSeparator15"); // NOI18N
        FormInput.add(jSeparator15);
        jSeparator15.setBounds(0, 1420, 880, 1);

        jLabel140.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel140.setText("VI. DIAGNOSIS/ASESMEN");
        jLabel140.setName("jLabel140"); // NOI18N
        FormInput.add(jLabel140);
        jLabel140.setBounds(10, 1420, 190, 23);

        scrollPane14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane14.setName("scrollPane14"); // NOI18N

        Diagnosis.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Diagnosis.setColumns(20);
        Diagnosis.setRows(3);
        Diagnosis.setName("Diagnosis"); // NOI18N
        Diagnosis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosisKeyPressed(evt);
            }
        });
        scrollPane14.setViewportView(Diagnosis);

        FormInput.add(scrollPane14);
        scrollPane14.setBounds(44, 1440, 810, 43);

        jSeparator16.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator16.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator16.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator16.setName("jSeparator16"); // NOI18N
        FormInput.add(jSeparator16);
        jSeparator16.setBounds(0, 1490, 880, 1);

        jLabel141.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel141.setText("VII. TATALAKSANA");
        jLabel141.setName("jLabel141"); // NOI18N
        FormInput.add(jLabel141);
        jLabel141.setBounds(10, 1490, 190, 23);

        scrollPane15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane15.setName("scrollPane15"); // NOI18N

        Tatalaksana.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Tatalaksana.setColumns(30);
        Tatalaksana.setRows(20);
        Tatalaksana.setName("Tatalaksana"); // NOI18N
        Tatalaksana.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TatalaksanaKeyPressed(evt);
            }
        });
        scrollPane15.setViewportView(Tatalaksana);

        FormInput.add(scrollPane15);
        scrollPane15.setBounds(44, 1510, 810, 153);

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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "26-02-2025" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "26-02-2025" }));
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
        }else if(NoRMIbu.getText().trim().equals("")){
            Valid.textKosong(BtnIbuBayi,"Ibu Bayi");
        }else if(tbAPGAR.getValueAt(0,4).toString().equals("")||tbAPGAR.getValueAt(1,4).toString().equals("")||tbAPGAR.getValueAt(2,4).toString().equals("")||
                tbAPGAR.getValueAt(3,4).toString().equals("")||tbAPGAR.getValueAt(4,4).toString().equals("")||tbAPGAR.getValueAt(0,5).toString().equals("")||
                tbAPGAR.getValueAt(1,5).toString().equals("")||tbAPGAR.getValueAt(2,5).toString().equals("")||tbAPGAR.getValueAt(3,5).toString().equals("")||
                tbAPGAR.getValueAt(4,5).toString().equals("")||tbAPGAR.getValueAt(0,6).toString().equals("")||tbAPGAR.getValueAt(1,6).toString().equals("")||
                tbAPGAR.getValueAt(2,6).toString().equals("")||tbAPGAR.getValueAt(3,6).toString().equals("")||tbAPGAR.getValueAt(4,6).toString().equals("")){
            JOptionPane.showMessageDialog(null,"Nilai APGAR harus valid...!!!");
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
            Valid.pindah(evt,Tatalaksana,BtnBatal);
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
                if(KdDokter.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString())){
                    if(Sequel.cekTanggal48jam(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString(),Sequel.ambiltanggalsekarang())==true){
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
        }else if(NoRMIbu.getText().trim().equals("")){
            Valid.textKosong(BtnIbuBayi,"Ibu Bayi");
        }else if(tbAPGAR.getValueAt(0,4).toString().equals("")||tbAPGAR.getValueAt(1,4).toString().equals("")||tbAPGAR.getValueAt(2,4).toString().equals("")||
                tbAPGAR.getValueAt(3,4).toString().equals("")||tbAPGAR.getValueAt(4,4).toString().equals("")||tbAPGAR.getValueAt(0,5).toString().equals("")||
                tbAPGAR.getValueAt(1,5).toString().equals("")||tbAPGAR.getValueAt(2,5).toString().equals("")||tbAPGAR.getValueAt(3,5).toString().equals("")||
                tbAPGAR.getValueAt(4,5).toString().equals("")||tbAPGAR.getValueAt(0,6).toString().equals("")||tbAPGAR.getValueAt(1,6).toString().equals("")||
                tbAPGAR.getValueAt(2,6).toString().equals("")||tbAPGAR.getValueAt(3,6).toString().equals("")||tbAPGAR.getValueAt(4,6).toString().equals("")){
            JOptionPane.showMessageDialog(null,"Nilai APGAR harus valid...!!!");
        }else{
            if(tbObat.getSelectedRow()>-1){
                if(akses.getkode().equals("Admin Utama")){
                    ganti();
                }else{
                    if(KdDokter.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString())){
                        if(Sequel.cekTanggal48jam(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString(),Sequel.ambiltanggalsekarang())==true){
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
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Umur</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>NIK Bayi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.RM IBU</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Ibu</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tgl.Lahir Ibu</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>NIK Ibu</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kode Dokter</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Dokter</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tanggal</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>G</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>P</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>A</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Hidup</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Usia Hamil</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>HbsAg</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>HIV/AIDS</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Syphilis</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Riwayat Obstetri Ibu</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Riwayat Obstetri Ibu</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Faktor Risiko Neonatal</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Faktor Risiko Neonatal</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tgl & Jam Persalinan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Bersalin Di</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Inisiasi Menyusui Dini</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Jenis Persalinan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Indikasi/Keterangan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Aterm</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Bernafas/Menangis</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tonus Otot Baik</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Cairan Amnion Jernih</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>F 1</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>U 1</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>T 1</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>R 1</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>W 1</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N 1'</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>F 5</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>U 5</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>T 5</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>R 5</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>W 5</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N 5'</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>F 10</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>U 10</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>T 10</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>R 10</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>W 10</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N 10'</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Frekuensi Napas</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.F.N</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Retraksi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.R</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Sianosis</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.S</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Jalan Masuk Udara</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.J.M</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Grunting</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.G</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Ttl.D.S</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Down Score</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nadi(x/menit)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>RR(x/menit)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Suhu(°C)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Saturasi O2(%)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>BB(gram)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>PB(cm)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>LK(cm)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>LD(cm)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keadaan Umum</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Keadaan Umum</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kulit</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Kulit</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kepala</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Kepala</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Mata</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Mata</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Telinga</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Telinga</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Hidung</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Hidung</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Mulut</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Mulut</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tenggorokan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Tenggorokan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Leher</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Leher</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Thorax</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Thorax</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Abdomen</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Abdomen</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Genitalia</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Genitalia</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Anus</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Anus</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Muskuloskeletal</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Muskuloskeletal</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Ekstrimitas</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Ekstrimitas</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Paru</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Paru</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Refleks Primitif</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Refleks Primitif</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kelainan Lainnya</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Pemeriksaan Regional/Khusus/Tambahan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Laboratorium</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Radiologi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Penunjang Lainnya</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Diagnosis/Asesmen</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tatalaksana</b></td>"+
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
                            "<td valign='top'>"+tbObat.getValueAt(i,79).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,80).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,81).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,82).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,83).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,84).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,85).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,86).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,87).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,88).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,89).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,90).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,91).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,92).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,93).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,94).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,95).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,96).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,97).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,98).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,99).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,100).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,101).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,102).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,103).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,104).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,105).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,106).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,107).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,108).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,109).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,110).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,111).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,112).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,113).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,114).toString()+"</td>"+
                        "</tr>");
                }
                LoadHTML.setText(
                    "<html>"+
                      "<table width='10000px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
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

                File f = new File("DataPenilaianAwalMedisRanapNeonatus.html");            
                BufferedWriter bw = new BufferedWriter(new FileWriter(f));            
                bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                            "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                            "<table width='10000px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                "<tr class='isi2'>"+
                                    "<td valign='top' align='center'>"+
                                        "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                        akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                        akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                        "<font size='2' face='Tahoma'>DATA PENILAIAN AWAL MEDIS RAWAT INAP NEONATUS<br><br></font>"+        
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
                tampilPersalinan();
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
                    tampilPersalinan();
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
        Valid.pindah(evt,BtnSimpan,TglAsuhan);
    }//GEN-LAST:event_BtnDokterKeyPressed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if(TabRawat.getSelectedIndex()==1){
            tampil();
        }
    }//GEN-LAST:event_TabRawatMouseClicked

    private void TglAsuhanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglAsuhanKeyPressed
        Valid.pindah2(evt,BtnDokter,BtnIbuBayi);
    }//GEN-LAST:event_TglAsuhanKeyPressed

    private void BtnTambahMasalahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahMasalahActionPerformed
        if(NoRMIbu.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Pilih terlebih dahulu ibu bayi yang mau dimasukkan data kelahirannya...");
            //HbsAg.requestFocus();
        }else{
            emptTeksPersalinan();
            DlgRiwayatPersalinan.setLocationRelativeTo(internalFrame1);
            DlgRiwayatPersalinan.setVisible(true);
        }
    }//GEN-LAST:event_BtnTambahMasalahActionPerformed

    private void BtnHapusRiwayatPersalinanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusRiwayatPersalinanActionPerformed
        if(tbRiwayatKehamilan.getSelectedRow()>-1){
            Sequel.meghapus("riwayat_persalinan_pasien","no_rkm_medis","tgl_thn",NoRMIbu.getText(),tbRiwayatKehamilan.getValueAt(tbRiwayatKehamilan.getSelectedRow(),1).toString());
            tampilPersalinan();
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
                NoRMIbu.getText(),Valid.SetTgl(TanggalPersalinan.getSelectedItem()+""),TempatPersalinan.getText(),UsiaHamil.getText(),JenisPersalinan.getText(),Penolong.getText(),Penyulit.getText(),JK.getSelectedItem().toString().substring(0,1),BBPB.getText(),Keadaan.getText()
            })==true){
                emptTeksPersalinan();
                tampilPersalinan();
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

    private void tbAPGARPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tbAPGARPropertyChange
        if(this.isVisible()==true){
            getDataApgar();
        }
    }//GEN-LAST:event_tbAPGARPropertyChange

    private void BtnIbuBayiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnIbuBayiActionPerformed
        DlgCariPasien ibubayi=new DlgCariPasien(null,false);
        ibubayi.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(ibubayi.getTable().getSelectedRow()!= -1){                   
                    NoRMIbu.setText(ibubayi.getTable().getValueAt(ibubayi.getTable().getSelectedRow(),0).toString());
                    NmIbu.setText(ibubayi.getTable().getValueAt(ibubayi.getTable().getSelectedRow(),1).toString());
                    TglLahirIbu.setText(ibubayi.getTable().getValueAt(ibubayi.getTable().getSelectedRow(),5).toString());
                    NIKIbu.setText(ibubayi.getTable().getValueAt(ibubayi.getTable().getSelectedRow(),2).toString());
                    StatusPerkawinanIbu.setText(ibubayi.getTable().getValueAt(ibubayi.getTable().getSelectedRow(),10).toString());
                    tampilPersalinan();
                }  
                BtnIbuBayi.requestFocus();
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
        
        ibubayi.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    ibubayi.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        ibubayi.emptTeks();
        ibubayi.isCek();
        ibubayi.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        ibubayi.setLocationRelativeTo(internalFrame1);
        ibubayi.setAlwaysOnTop(false);
        ibubayi.setVisible(true);
    }//GEN-LAST:event_BtnIbuBayiActionPerformed

    private void BtnIbuBayiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnIbuBayiKeyPressed
        Valid.pindah(evt,TglAsuhan,PenyakitIbu);
    }//GEN-LAST:event_BtnIbuBayiKeyPressed

    private void PenyakitIbuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenyakitIbuKeyPressed
        Valid.pindah(evt,BtnIbuBayi,KeteranganPenyakitIbu);
    }//GEN-LAST:event_PenyakitIbuKeyPressed

    private void PerawatanAntenatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PerawatanAntenatalKeyPressed
        Valid.pindah(evt,ObatSelamaHamil,KeteranganPerawatanAntenatal);
    }//GEN-LAST:event_PerawatanAntenatalKeyPressed

    private void TerdaftarEkohortKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TerdaftarEkohortKeyPressed
        Valid.pindah(evt,KeteranganPerawatanAntenatal,KeteranganTerdaftarEkohort);
    }//GEN-LAST:event_TerdaftarEkohortKeyPressed

    private void PenyulitKehamilanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenyulitKehamilanKeyPressed
        Valid.pindah(evt,KeteranganTerdaftarEkohort,KeteranganPenyulitKehamilan);
    }//GEN-LAST:event_PenyulitKehamilanKeyPressed

    private void KehamilanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KehamilanKeyPressed
        Valid.pindah(evt,UmurKehamilan,KeteranganKehamilan);
    }//GEN-LAST:event_KehamilanKeyPressed

    private void MacamPersalinanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MacamPersalinanKeyPressed
        Valid.pindah(evt,LetakBayi,KeteranganMacamPersalinan);
    }//GEN-LAST:event_MacamPersalinanKeyPressed

    private void IndikasiPersalinanOperatifKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_IndikasiPersalinanOperatifKeyPressed
        Valid.pindah(evt,KeteranganMacamPersalinan,KeteranganIndikasiPersalinanOperatif);
    }//GEN-LAST:event_IndikasiPersalinanOperatifKeyPressed

    private void JenisKelaminKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JenisKelaminKeyPressed
        Valid.pindah(evt,LingkarDada,ResusitasiLahir);
    }//GEN-LAST:event_JenisKelaminKeyPressed

    private void ResusitasiLahirKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ResusitasiLahirKeyPressed
        Valid.pindah(evt,JenisKelamin,KeteranganResusitasiLahir);
    }//GEN-LAST:event_ResusitasiLahirKeyPressed

    private void KondisiUmumKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KondisiUmumKeyPressed
        Valid.pindah(evt,KeteranganKeadaanBayi,KeteranganKondisiUmum);
    }//GEN-LAST:event_KondisiUmumKeyPressed

    private void KeteranganKondisiUmumKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKondisiUmumKeyPressed
        Valid.pindah(evt,KondisiUmum,Kulit);
    }//GEN-LAST:event_KeteranganKondisiUmumKeyPressed

    private void KulitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KulitKeyPressed
        Valid.pindah(evt,KeteranganKondisiUmum,KeteranganKulit);
    }//GEN-LAST:event_KulitKeyPressed

    private void KeteranganKulitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKulitKeyPressed
        Valid.pindah(evt,Kulit,Kepala);
    }//GEN-LAST:event_KeteranganKulitKeyPressed

    private void KepalaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KepalaKeyPressed
        Valid.pindah(evt,KeteranganKulit,KeteranganKepala);
    }//GEN-LAST:event_KepalaKeyPressed

    private void KeteranganKepalaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKepalaKeyPressed
        Valid.pindah(evt,Kepala,Leher);
    }//GEN-LAST:event_KeteranganKepalaKeyPressed

    private void LeherKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LeherKeyPressed
        Valid.pindah(evt,KeteranganKepala,KeteranganLeher);
    }//GEN-LAST:event_LeherKeyPressed

    private void KeteranganLeherKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganLeherKeyPressed
        Valid.pindah(evt,Leher,Mata);
    }//GEN-LAST:event_KeteranganLeherKeyPressed

    private void MataKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MataKeyPressed
        Valid.pindah(evt,KeteranganLeher,KeteranganMata);
    }//GEN-LAST:event_MataKeyPressed

    private void KeteranganMataKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganMataKeyPressed
        Valid.pindah(evt,Mata,Hidung);
    }//GEN-LAST:event_KeteranganMataKeyPressed

    private void HidungKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HidungKeyPressed
        Valid.pindah(evt,KeteranganMata,KeteranganHidung);
    }//GEN-LAST:event_HidungKeyPressed

    private void KeteranganHidungKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganHidungKeyPressed
        Valid.pindah(evt,Hidung,Telinga);
    }//GEN-LAST:event_KeteranganHidungKeyPressed

    private void TelingaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TelingaKeyPressed
        Valid.pindah(evt,KeteranganHidung,KeteranganTelinga);
    }//GEN-LAST:event_TelingaKeyPressed

    private void KeteranganTelingaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganTelingaKeyPressed
        Valid.pindah(evt,Telinga,Dada);
    }//GEN-LAST:event_KeteranganTelingaKeyPressed

    private void DadaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DadaKeyPressed
        Valid.pindah(evt,KeteranganTelinga,KeteranganDada);
    }//GEN-LAST:event_DadaKeyPressed

    private void KeteranganDadaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganDadaKeyPressed
        Valid.pindah(evt,Dada,Paru);
    }//GEN-LAST:event_KeteranganDadaKeyPressed

    private void KeteranganParuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganParuKeyPressed
        Valid.pindah(evt,Paru,Jantung);
    }//GEN-LAST:event_KeteranganParuKeyPressed

    private void ParuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ParuKeyPressed
        Valid.pindah(evt,KeteranganDada,KeteranganParu);
    }//GEN-LAST:event_ParuKeyPressed

    private void JantungKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JantungKeyPressed
        Valid.pindah(evt,KeteranganParu,KeteranganJantung);
    }//GEN-LAST:event_JantungKeyPressed

    private void KeteranganJantungKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganJantungKeyPressed
        Valid.pindah(evt,Jantung,Perut);
    }//GEN-LAST:event_KeteranganJantungKeyPressed

    private void PerutKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PerutKeyPressed
        Valid.pindah(evt,KeteranganJantung,KeteranganPerut);
    }//GEN-LAST:event_PerutKeyPressed

    private void KeteranganPerutKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganPerutKeyPressed
        Valid.pindah(evt,Perut,TaliPusat);
    }//GEN-LAST:event_KeteranganPerutKeyPressed

    private void TaliPusatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TaliPusatKeyPressed
        Valid.pindah(evt,KeteranganPerut,KeteranganTaliPusat);
    }//GEN-LAST:event_TaliPusatKeyPressed

    private void KeteranganTaliPusatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganTaliPusatKeyPressed
        Valid.pindah(evt,TaliPusat,AlatKelamin);
    }//GEN-LAST:event_KeteranganTaliPusatKeyPressed

    private void AlatKelaminKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlatKelaminKeyPressed
        Valid.pindah(evt,KeteranganTaliPusat,KeteranganAlatKelamin);
    }//GEN-LAST:event_AlatKelaminKeyPressed

    private void KeteranganAlatKelaminKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganAlatKelaminKeyPressed
        Valid.pindah(evt,AlatKelamin,RuasTulangBelakang);
    }//GEN-LAST:event_KeteranganAlatKelaminKeyPressed

    private void RuasTulangBelakangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RuasTulangBelakangKeyPressed
        Valid.pindah(evt,KeteranganAlatKelamin,KeteranganRuasTulangBelakang);
    }//GEN-LAST:event_RuasTulangBelakangKeyPressed

    private void KeteranganRuasTulangBelakangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganRuasTulangBelakangKeyPressed
        Valid.pindah(evt,RuasTulangBelakang,Extrimitas);
    }//GEN-LAST:event_KeteranganRuasTulangBelakangKeyPressed

    private void ExtrimitasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ExtrimitasKeyPressed
        Valid.pindah(evt,KeteranganRuasTulangBelakang,KeteranganExtrimitas);
    }//GEN-LAST:event_ExtrimitasKeyPressed

    private void KeteranganExtrimitasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganExtrimitasKeyPressed
        Valid.pindah(evt,Extrimitas,Anus);
    }//GEN-LAST:event_KeteranganExtrimitasKeyPressed

    private void AnusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AnusKeyPressed
        Valid.pindah(evt,KeteranganExtrimitas,KeteranganAnus);
    }//GEN-LAST:event_AnusKeyPressed

    private void KeteranganAnusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganAnusKeyPressed
        Valid.pindah(evt,Anus,Refleks);
    }//GEN-LAST:event_KeteranganAnusKeyPressed

    private void RefleksKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RefleksKeyPressed
        Valid.pindah(evt,KeteranganAnus,KeteranganRefleks);
    }//GEN-LAST:event_RefleksKeyPressed

    private void KeteranganRefleksKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganRefleksKeyPressed
        Valid.pindah(evt,Refleks,DenyutFemoral);
    }//GEN-LAST:event_KeteranganRefleksKeyPressed

    private void DenyutFemoralKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DenyutFemoralKeyPressed
        Valid.pindah(evt,KeteranganRefleks,KeteranganDenyutFemoral);
    }//GEN-LAST:event_DenyutFemoralKeyPressed

    private void KeteranganDenyutFemoralKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganDenyutFemoralKeyPressed
        Valid.pindah(evt,DenyutFemoral,PemeriksaanLainnya);
    }//GEN-LAST:event_KeteranganDenyutFemoralKeyPressed

    private void PemeriksaanLainnyaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PemeriksaanLainnyaKeyPressed
        Valid.pindah2(evt,KeteranganDenyutFemoral,PemeriksaanPenunjang);
    }//GEN-LAST:event_PemeriksaanLainnyaKeyPressed

    private void PemeriksaanPenunjangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PemeriksaanPenunjangKeyPressed
        Valid.pindah2(evt,PemeriksaanLainnya,Diagnosis);
    }//GEN-LAST:event_PemeriksaanPenunjangKeyPressed

    private void DiagnosisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosisKeyPressed
        Valid.pindah2(evt,PemeriksaanPenunjang,Tatalaksana);
    }//GEN-LAST:event_DiagnosisKeyPressed

    private void TatalaksanaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TatalaksanaKeyPressed
        Valid.pindah2(evt,Diagnosis,BtnSimpan);
    }//GEN-LAST:event_TatalaksanaKeyPressed

    private void KeteranganPenyakitIbuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganPenyakitIbuKeyPressed
        Valid.pindah(evt,PenyakitIbu,ObatSelamaHamil);
    }//GEN-LAST:event_KeteranganPenyakitIbuKeyPressed

    private void ObatSelamaHamilKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ObatSelamaHamilKeyPressed
        Valid.pindah(evt,KeteranganPenyakitIbu,PerawatanAntenatal);
    }//GEN-LAST:event_ObatSelamaHamilKeyPressed

    private void KeteranganPerawatanAntenatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganPerawatanAntenatalKeyPressed
        Valid.pindah(evt,PerawatanAntenatal,TerdaftarEkohort);
    }//GEN-LAST:event_KeteranganPerawatanAntenatalKeyPressed

    private void KeteranganTerdaftarEkohortKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganTerdaftarEkohortKeyPressed
        Valid.pindah(evt,TerdaftarEkohort,PenyulitKehamilan);
    }//GEN-LAST:event_KeteranganTerdaftarEkohortKeyPressed

    private void KeteranganPenyulitKehamilanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganPenyulitKehamilanKeyPressed
        Valid.pindah(evt,PenyulitKehamilan,AlergiIbu);
    }//GEN-LAST:event_KeteranganPenyulitKehamilanKeyPressed

    private void AlergiIbuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlergiIbuKeyPressed
        Valid.pindah(evt,KeteranganPenyulitKehamilan,KeteranganRiwayatMaternal);
    }//GEN-LAST:event_AlergiIbuKeyPressed

    private void KeteranganRiwayatMaternalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganRiwayatMaternalKeyPressed
        Valid.pindah(evt,AlergiIbu,UmurKehamilan);
    }//GEN-LAST:event_KeteranganRiwayatMaternalKeyPressed

    private void UmurKehamilanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_UmurKehamilanKeyPressed
        Valid.pindah(evt,KeteranganRiwayatMaternal,Kehamilan);
    }//GEN-LAST:event_UmurKehamilanKeyPressed

    private void KeteranganKehamilanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKehamilanKeyPressed
        Valid.pindah(evt,Kehamilan,UrutanKehamilan);
    }//GEN-LAST:event_KeteranganKehamilanKeyPressed

    private void UrutanKehamilanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_UrutanKehamilanKeyPressed
        Valid.pindah(evt,KeteranganKehamilan,JamKetubanPecah);
    }//GEN-LAST:event_UrutanKehamilanKeyPressed

    private void JamKetubanPecahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JamKetubanPecahKeyPressed
        Valid.pindah(evt,UrutanKehamilan,MenitKetubanPecah);
    }//GEN-LAST:event_JamKetubanPecahKeyPressed

    private void MenitKetubanPecahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MenitKetubanPecahKeyPressed
        Valid.pindah(evt,JamKetubanPecah,JumlahAirKetuban);
    }//GEN-LAST:event_MenitKetubanPecahKeyPressed

    private void JumlahAirKetubanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JumlahAirKetubanKeyPressed
        Valid.pindah(evt,MenitKetubanPecah,WarnaAirKetuban);
    }//GEN-LAST:event_JumlahAirKetubanKeyPressed

    private void WarnaAirKetubanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_WarnaAirKetubanKeyPressed
        Valid.pindah(evt,JumlahAirKetuban,BauAirKetuban);
    }//GEN-LAST:event_WarnaAirKetubanKeyPressed

    private void BauAirKetubanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BauAirKetubanKeyPressed
        Valid.pindah(evt,WarnaAirKetuban,LetakBayi);
    }//GEN-LAST:event_BauAirKetubanKeyPressed

    private void LetakBayiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LetakBayiKeyPressed
        Valid.pindah(evt,BauAirKetuban,MacamPersalinan);
    }//GEN-LAST:event_LetakBayiKeyPressed

    private void KeteranganMacamPersalinanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganMacamPersalinanKeyPressed
        Valid.pindah(evt,MacamPersalinan,IndikasiPersalinanOperatif);
    }//GEN-LAST:event_KeteranganMacamPersalinanKeyPressed

    private void KeteranganIndikasiPersalinanOperatifKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganIndikasiPersalinanOperatifKeyPressed
        Valid.pindah(evt,IndikasiPersalinanOperatif,LamaGawatJanin);
    }//GEN-LAST:event_KeteranganIndikasiPersalinanOperatifKeyPressed

    private void LamaGawatJaninKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LamaGawatJaninKeyPressed
        Valid.pindah(evt,KeteranganIndikasiPersalinanOperatif,ObatSelamaPersalinan);
    }//GEN-LAST:event_LamaGawatJaninKeyPressed

    private void ObatSelamaPersalinanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ObatSelamaPersalinanKeyPressed
        Valid.pindah(evt,LamaGawatJanin,BeratPlacenta);
    }//GEN-LAST:event_ObatSelamaPersalinanKeyPressed

    private void BeratPlacentaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BeratPlacentaKeyPressed
        Valid.pindah(evt,ObatSelamaPersalinan,KelainanPlacenta);
    }//GEN-LAST:event_BeratPlacentaKeyPressed

    private void KelainanPlacentaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KelainanPlacentaKeyPressed
        Valid.pindah(evt,BeratPlacenta,KeteranganRiwayatPersalinan);
    }//GEN-LAST:event_KelainanPlacentaKeyPressed

    private void KeteranganRiwayatPersalinanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganRiwayatPersalinanKeyPressed
        Valid.pindah(evt,KelainanPlacenta,BBLahir);
    }//GEN-LAST:event_KeteranganRiwayatPersalinanKeyPressed

    private void BBLahirKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BBLahirKeyPressed
        Valid.pindah(evt,KeteranganRiwayatPersalinan,PanjangBadan);
    }//GEN-LAST:event_BBLahirKeyPressed

    private void PanjangBadanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PanjangBadanKeyPressed
        Valid.pindah(evt,BBLahir,LingkarKepala);
    }//GEN-LAST:event_PanjangBadanKeyPressed

    private void LingkarKepalaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LingkarKepalaKeyPressed
        Valid.pindah(evt,PanjangBadan,LingkarDada);
    }//GEN-LAST:event_LingkarKepalaKeyPressed

    private void LingkarDadaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LingkarDadaKeyPressed
        Valid.pindah(evt,LingkarKepala,JenisKelamin);
    }//GEN-LAST:event_LingkarDadaKeyPressed

    private void KeteranganResusitasiLahirKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganResusitasiLahirKeyPressed
        Valid.pindah(evt,ResusitasiLahir,ObatSaatLahir);
    }//GEN-LAST:event_KeteranganResusitasiLahirKeyPressed

    private void ObatSaatLahirKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ObatSaatLahirKeyPressed
        Valid.pindah(evt,KeteranganResusitasiLahir,KeteranganKeadaanBayi);
    }//GEN-LAST:event_ObatSaatLahirKeyPressed

    private void KeteranganKeadaanBayiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKeadaanBayiKeyPressed
        Valid.pindah(evt,ObatSaatLahir,KondisiUmum);
    }//GEN-LAST:event_KeteranganKeadaanBayiKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMPenilaianBayiBaruLahir dialog = new RMPenilaianBayiBaruLahir(new javax.swing.JFrame(), true);
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
    private widget.ComboBox AlatKelamin;
    private widget.TextBox AlergiIbu;
    private widget.ComboBox Anus;
    private widget.TextBox BBLahir;
    private widget.TextBox BBPB;
    private widget.TextBox BauAirKetuban;
    private widget.TextBox BeratPlacenta;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnDokter;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnHapusRiwayatPersalinan;
    private widget.Button BtnIbuBayi;
    private widget.Button BtnKeluar;
    private widget.Button BtnKeluarKehamilan;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.Button BtnSimpanRiwayatKehamilan;
    private widget.Button BtnTambahMasalah;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.ComboBox Dada;
    private widget.ComboBox DenyutFemoral;
    private widget.TextArea Diagnosis;
    private javax.swing.JDialog DlgRiwayatPersalinan;
    private widget.ComboBox Extrimitas;
    private widget.PanelBiasa FormInput;
    private widget.ComboBox Hidung;
    private widget.ComboBox IndikasiPersalinanOperatif;
    private widget.ComboBox JK;
    private widget.TextBox JamKetubanPecah;
    private widget.ComboBox Jantung;
    private widget.ComboBox JenisKelamin;
    private widget.TextBox JenisPersalinan;
    private widget.TextBox Jk;
    private widget.TextBox JumlahAirKetuban;
    private widget.TextBox KdDokter;
    private widget.TextBox Keadaan;
    private widget.ComboBox Kehamilan;
    private widget.TextBox KelainanPlacenta;
    private widget.ComboBox Kepala;
    private widget.TextBox KeteranganAlatKelamin;
    private widget.TextBox KeteranganAnus;
    private widget.TextBox KeteranganDada;
    private widget.TextBox KeteranganDenyutFemoral;
    private widget.TextBox KeteranganExtrimitas;
    private widget.TextBox KeteranganHidung;
    private widget.TextBox KeteranganIndikasiPersalinanOperatif;
    private widget.TextBox KeteranganJantung;
    private widget.TextBox KeteranganKeadaanBayi;
    private widget.TextBox KeteranganKehamilan;
    private widget.TextBox KeteranganKepala;
    private widget.TextBox KeteranganKondisiUmum;
    private widget.TextBox KeteranganKulit;
    private widget.TextBox KeteranganLeher;
    private widget.TextBox KeteranganMacamPersalinan;
    private widget.TextBox KeteranganMata;
    private widget.TextBox KeteranganParu;
    private widget.TextBox KeteranganPenyakitIbu;
    private widget.TextBox KeteranganPenyulitKehamilan;
    private widget.TextBox KeteranganPerawatanAntenatal;
    private widget.TextBox KeteranganPerut;
    private widget.TextBox KeteranganRefleks;
    private widget.TextBox KeteranganResusitasiLahir;
    private widget.TextBox KeteranganRiwayatMaternal;
    private widget.TextBox KeteranganRiwayatPersalinan;
    private widget.TextBox KeteranganRuasTulangBelakang;
    private widget.TextBox KeteranganTaliPusat;
    private widget.TextBox KeteranganTelinga;
    private widget.TextBox KeteranganTerdaftarEkohort;
    private widget.ComboBox KondisiUmum;
    private widget.ComboBox Kulit;
    private widget.Label LCount;
    private widget.TextBox LamaGawatJanin;
    private widget.ComboBox Leher;
    private widget.TextBox LetakBayi;
    private widget.TextBox LingkarDada;
    private widget.TextBox LingkarKepala;
    private widget.editorpane LoadHTML;
    private widget.ComboBox MacamPersalinan;
    private widget.ComboBox Mata;
    private widget.TextBox MenitKetubanPecah;
    private widget.TextBox2 N1;
    private widget.TextBox2 N10;
    private widget.TextBox2 N5;
    private widget.TextBox NIKIbu;
    private widget.TextBox NmDokter;
    private widget.TextBox NmIbu;
    private widget.TextBox NoRMIbu;
    private widget.TextBox ObatSaatLahir;
    private widget.TextBox ObatSelamaHamil;
    private widget.TextBox ObatSelamaPersalinan;
    private widget.TextBox PanjangBadan;
    private widget.ComboBox Paru;
    private widget.TextArea PemeriksaanLainnya;
    private widget.TextArea PemeriksaanPenunjang;
    private widget.TextBox Penolong;
    private widget.ComboBox PenyakitIbu;
    private widget.TextBox Penyulit;
    private widget.ComboBox PenyulitKehamilan;
    private widget.ComboBox PerawatanAntenatal;
    private widget.ComboBox Perut;
    private widget.ComboBox Refleks;
    private widget.ComboBox ResusitasiLahir;
    private widget.ComboBox RuasTulangBelakang;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll2;
    private widget.ScrollPane Scroll6;
    private widget.TextBox StatusPerkawinanIbu;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private javax.swing.JTabbedPane TabRawat;
    private widget.ComboBox TaliPusat;
    private widget.Tanggal TanggalPersalinan;
    private widget.TextBox TanggalRegistrasi;
    private widget.TextArea Tatalaksana;
    private widget.ComboBox Telinga;
    private widget.TextBox TempatPersalinan;
    private widget.ComboBox TerdaftarEkohort;
    private widget.Tanggal TglAsuhan;
    private widget.TextBox TglLahir;
    private widget.TextBox TglLahirIbu;
    private widget.TextBox UmurKehamilan;
    private widget.TextBox UrutanKehamilan;
    private widget.TextBox UsiaHamil;
    private widget.TextBox WarnaAirKetuban;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.InternalFrame internalFrame4;
    private widget.Label jLabel10;
    private widget.Label jLabel100;
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
    private widget.Label jLabel139;
    private widget.Label jLabel14;
    private widget.Label jLabel140;
    private widget.Label jLabel141;
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
    private widget.Label jLabel9;
    private widget.Label jLabel90;
    private widget.Label jLabel91;
    private widget.Label jLabel92;
    private widget.Label jLabel93;
    private widget.Label jLabel94;
    private widget.Label jLabel95;
    private widget.Label jLabel96;
    private widget.Label jLabel97;
    private widget.Label jLabel99;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator15;
    private javax.swing.JSeparator jSeparator16;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private widget.Label label11;
    private widget.Label label14;
    private widget.Label label71;
    private widget.PanelBiasa panelBiasa2;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollInput;
    private widget.ScrollPane scrollPane12;
    private widget.ScrollPane scrollPane13;
    private widget.ScrollPane scrollPane14;
    private widget.ScrollPane scrollPane15;
    private widget.Table tbAPGAR;
    private widget.Table tbObat;
    private widget.Table tbRiwayatKehamilan;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            if(TCari.getText().trim().equals("")){
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,reg_periksa.umurdaftar,reg_periksa.sttsumur,pasien.no_ktp,penilaian_medis_ranap_neonatus.no_rkm_medis_ibu,ibupasien.nm_pasien as nama_ibu,ibupasien.tgl_lahir as lahiribu,ibupasien.no_ktp as ktpibu,"+
                        "penilaian_medis_ranap_neonatus.tanggal,penilaian_medis_ranap_neonatus.kd_dokter,dokter.nm_dokter,penilaian_medis_ranap_neonatus.g,penilaian_medis_ranap_neonatus.p,penilaian_medis_ranap_neonatus.a,penilaian_medis_ranap_neonatus.hidup,penilaian_medis_ranap_neonatus.usiahamil,penilaian_medis_ranap_neonatus.hbsag,penilaian_medis_ranap_neonatus.hiv,"+
                        "penilaian_medis_ranap_neonatus.syphilis,penilaian_medis_ranap_neonatus.riwayat_obstetri_ibu,penilaian_medis_ranap_neonatus.keterangan_riwayat_obstetri_ibu,penilaian_medis_ranap_neonatus.faktor_risiko_neonatal,penilaian_medis_ranap_neonatus.keterangan_faktor_risiko_neonatal,penilaian_medis_ranap_neonatus.tanggal_persalinan,"+
                        "penilaian_medis_ranap_neonatus.bersalin_di,penilaian_medis_ranap_neonatus.inisiasi_menyusui,penilaian_medis_ranap_neonatus.jenis_persalinan,penilaian_medis_ranap_neonatus.indikasi,penilaian_medis_ranap_neonatus.aterm,penilaian_medis_ranap_neonatus.bernafas,penilaian_medis_ranap_neonatus.tanus_otot,penilaian_medis_ranap_neonatus.cairan_amnion,"+
                        "penilaian_medis_ranap_neonatus.f1,penilaian_medis_ranap_neonatus.u1,penilaian_medis_ranap_neonatus.t1,penilaian_medis_ranap_neonatus.r1,penilaian_medis_ranap_neonatus.w1,penilaian_medis_ranap_neonatus.n1,penilaian_medis_ranap_neonatus.f5,penilaian_medis_ranap_neonatus.u5,penilaian_medis_ranap_neonatus.t5,penilaian_medis_ranap_neonatus.r5,"+
                        "penilaian_medis_ranap_neonatus.w5,penilaian_medis_ranap_neonatus.n5,penilaian_medis_ranap_neonatus.f10,penilaian_medis_ranap_neonatus.u10,penilaian_medis_ranap_neonatus.t10,penilaian_medis_ranap_neonatus.r10,penilaian_medis_ranap_neonatus.w10,penilaian_medis_ranap_neonatus.n10,penilaian_medis_ranap_neonatus.frekuensi_napas,"+
                        "penilaian_medis_ranap_neonatus.nilai_frekuensi_napas,penilaian_medis_ranap_neonatus.retraksi,penilaian_medis_ranap_neonatus.nilai_retraksi,penilaian_medis_ranap_neonatus.sianosis,penilaian_medis_ranap_neonatus.nilai_sianosis,penilaian_medis_ranap_neonatus.jalan_masuk_udara,penilaian_medis_ranap_neonatus.nilai_jalan_masuk_udara,"+
                        "penilaian_medis_ranap_neonatus.grunting,penilaian_medis_ranap_neonatus.nilai_grunting,penilaian_medis_ranap_neonatus.total_down_score,penilaian_medis_ranap_neonatus.keterangan_down_Score,penilaian_medis_ranap_neonatus.nadi,penilaian_medis_ranap_neonatus.rr,penilaian_medis_ranap_neonatus.suhu,penilaian_medis_ranap_neonatus.saturasi,"+
                        "penilaian_medis_ranap_neonatus.bb,penilaian_medis_ranap_neonatus.pb,penilaian_medis_ranap_neonatus.lk,penilaian_medis_ranap_neonatus.ld,penilaian_medis_ranap_neonatus.keadaan_umum,penilaian_medis_ranap_neonatus.keterangan_keadaan_umum,penilaian_medis_ranap_neonatus.kulit,penilaian_medis_ranap_neonatus.keterangan_kulit,"+
                        "penilaian_medis_ranap_neonatus.kepala,penilaian_medis_ranap_neonatus.keterangan_kepala,penilaian_medis_ranap_neonatus.mata,penilaian_medis_ranap_neonatus.keterangan_mata,penilaian_medis_ranap_neonatus.telinga,penilaian_medis_ranap_neonatus.keterangan_telinga,penilaian_medis_ranap_neonatus.hidung,penilaian_medis_ranap_neonatus.keterangan_hidung,"+
                        "penilaian_medis_ranap_neonatus.mulut,penilaian_medis_ranap_neonatus.keterangan_mulut,penilaian_medis_ranap_neonatus.tenggorokan,penilaian_medis_ranap_neonatus.keterangan_tenggorokan,penilaian_medis_ranap_neonatus.leher,penilaian_medis_ranap_neonatus.keterangan_leher,penilaian_medis_ranap_neonatus.thorax,"+
                        "penilaian_medis_ranap_neonatus.keterangan_thorax,penilaian_medis_ranap_neonatus.abdomen,penilaian_medis_ranap_neonatus.keterangan_abdomen,penilaian_medis_ranap_neonatus.genitalia,penilaian_medis_ranap_neonatus.keterangan_genitalia,penilaian_medis_ranap_neonatus.anus,penilaian_medis_ranap_neonatus.keterangan_anus,"+
                        "penilaian_medis_ranap_neonatus.muskulos,penilaian_medis_ranap_neonatus.keterangan_muskulos,penilaian_medis_ranap_neonatus.ekstrimitas,penilaian_medis_ranap_neonatus.keterangan_ekstrimitas,penilaian_medis_ranap_neonatus.paru,penilaian_medis_ranap_neonatus.keterangan_paru,penilaian_medis_ranap_neonatus.refleks,"+
                        "penilaian_medis_ranap_neonatus.keterangan_refleks,penilaian_medis_ranap_neonatus.kelainan_lainnya,penilaian_medis_ranap_neonatus.pemeriksaan_regional,penilaian_medis_ranap_neonatus.lab,penilaian_medis_ranap_neonatus.radiologi,penilaian_medis_ranap_neonatus.penunjanglainnya,penilaian_medis_ranap_neonatus.diagnosis,"+
                        "penilaian_medis_ranap_neonatus.tata,penilaian_medis_ranap_neonatus.edukasi from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join penilaian_medis_ranap_neonatus on reg_periksa.no_rawat=penilaian_medis_ranap_neonatus.no_rawat "+
                        "inner join dokter on penilaian_medis_ranap_neonatus.kd_dokter=dokter.kd_dokter "+
                        "inner join pasien as ibupasien on ibupasien.no_rkm_medis=penilaian_medis_ranap_neonatus.no_rkm_medis_ibu where "+
                        "penilaian_medis_ranap_neonatus.tanggal between ? and ? order by penilaian_medis_ranap_neonatus.tanggal");
            }else{
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,reg_periksa.umurdaftar,reg_periksa.sttsumur,pasien.no_ktp,penilaian_medis_ranap_neonatus.no_rkm_medis_ibu,ibupasien.nm_pasien as nama_ibu,ibupasien.tgl_lahir as lahiribu,ibupasien.no_ktp as ktpibu,"+
                        "penilaian_medis_ranap_neonatus.tanggal,penilaian_medis_ranap_neonatus.kd_dokter,dokter.nm_dokter,penilaian_medis_ranap_neonatus.g,penilaian_medis_ranap_neonatus.p,penilaian_medis_ranap_neonatus.a,penilaian_medis_ranap_neonatus.hidup,penilaian_medis_ranap_neonatus.usiahamil,penilaian_medis_ranap_neonatus.hbsag,penilaian_medis_ranap_neonatus.hiv,"+
                        "penilaian_medis_ranap_neonatus.syphilis,penilaian_medis_ranap_neonatus.riwayat_obstetri_ibu,penilaian_medis_ranap_neonatus.keterangan_riwayat_obstetri_ibu,penilaian_medis_ranap_neonatus.faktor_risiko_neonatal,penilaian_medis_ranap_neonatus.keterangan_faktor_risiko_neonatal,penilaian_medis_ranap_neonatus.tanggal_persalinan,"+
                        "penilaian_medis_ranap_neonatus.bersalin_di,penilaian_medis_ranap_neonatus.inisiasi_menyusui,penilaian_medis_ranap_neonatus.jenis_persalinan,penilaian_medis_ranap_neonatus.indikasi,penilaian_medis_ranap_neonatus.aterm,penilaian_medis_ranap_neonatus.bernafas,penilaian_medis_ranap_neonatus.tanus_otot,penilaian_medis_ranap_neonatus.cairan_amnion,"+
                        "penilaian_medis_ranap_neonatus.f1,penilaian_medis_ranap_neonatus.u1,penilaian_medis_ranap_neonatus.t1,penilaian_medis_ranap_neonatus.r1,penilaian_medis_ranap_neonatus.w1,penilaian_medis_ranap_neonatus.n1,penilaian_medis_ranap_neonatus.f5,penilaian_medis_ranap_neonatus.u5,penilaian_medis_ranap_neonatus.t5,penilaian_medis_ranap_neonatus.r5,"+
                        "penilaian_medis_ranap_neonatus.w5,penilaian_medis_ranap_neonatus.n5,penilaian_medis_ranap_neonatus.f10,penilaian_medis_ranap_neonatus.u10,penilaian_medis_ranap_neonatus.t10,penilaian_medis_ranap_neonatus.r10,penilaian_medis_ranap_neonatus.w10,penilaian_medis_ranap_neonatus.n10,penilaian_medis_ranap_neonatus.frekuensi_napas,"+
                        "penilaian_medis_ranap_neonatus.nilai_frekuensi_napas,penilaian_medis_ranap_neonatus.retraksi,penilaian_medis_ranap_neonatus.nilai_retraksi,penilaian_medis_ranap_neonatus.sianosis,penilaian_medis_ranap_neonatus.nilai_sianosis,penilaian_medis_ranap_neonatus.jalan_masuk_udara,penilaian_medis_ranap_neonatus.nilai_jalan_masuk_udara,"+
                        "penilaian_medis_ranap_neonatus.grunting,penilaian_medis_ranap_neonatus.nilai_grunting,penilaian_medis_ranap_neonatus.total_down_score,penilaian_medis_ranap_neonatus.keterangan_down_Score,penilaian_medis_ranap_neonatus.nadi,penilaian_medis_ranap_neonatus.rr,penilaian_medis_ranap_neonatus.suhu,penilaian_medis_ranap_neonatus.saturasi,"+
                        "penilaian_medis_ranap_neonatus.bb,penilaian_medis_ranap_neonatus.pb,penilaian_medis_ranap_neonatus.lk,penilaian_medis_ranap_neonatus.ld,penilaian_medis_ranap_neonatus.keadaan_umum,penilaian_medis_ranap_neonatus.keterangan_keadaan_umum,penilaian_medis_ranap_neonatus.kulit,penilaian_medis_ranap_neonatus.keterangan_kulit,"+
                        "penilaian_medis_ranap_neonatus.kepala,penilaian_medis_ranap_neonatus.keterangan_kepala,penilaian_medis_ranap_neonatus.mata,penilaian_medis_ranap_neonatus.keterangan_mata,penilaian_medis_ranap_neonatus.telinga,penilaian_medis_ranap_neonatus.keterangan_telinga,penilaian_medis_ranap_neonatus.hidung,penilaian_medis_ranap_neonatus.keterangan_hidung,"+
                        "penilaian_medis_ranap_neonatus.mulut,penilaian_medis_ranap_neonatus.keterangan_mulut,penilaian_medis_ranap_neonatus.tenggorokan,penilaian_medis_ranap_neonatus.keterangan_tenggorokan,penilaian_medis_ranap_neonatus.leher,penilaian_medis_ranap_neonatus.keterangan_leher,penilaian_medis_ranap_neonatus.thorax,"+
                        "penilaian_medis_ranap_neonatus.keterangan_thorax,penilaian_medis_ranap_neonatus.abdomen,penilaian_medis_ranap_neonatus.keterangan_abdomen,penilaian_medis_ranap_neonatus.genitalia,penilaian_medis_ranap_neonatus.keterangan_genitalia,penilaian_medis_ranap_neonatus.anus,penilaian_medis_ranap_neonatus.keterangan_anus,"+
                        "penilaian_medis_ranap_neonatus.muskulos,penilaian_medis_ranap_neonatus.keterangan_muskulos,penilaian_medis_ranap_neonatus.ekstrimitas,penilaian_medis_ranap_neonatus.keterangan_ekstrimitas,penilaian_medis_ranap_neonatus.paru,penilaian_medis_ranap_neonatus.keterangan_paru,penilaian_medis_ranap_neonatus.refleks,"+
                        "penilaian_medis_ranap_neonatus.keterangan_refleks,penilaian_medis_ranap_neonatus.kelainan_lainnya,penilaian_medis_ranap_neonatus.pemeriksaan_regional,penilaian_medis_ranap_neonatus.lab,penilaian_medis_ranap_neonatus.radiologi,penilaian_medis_ranap_neonatus.penunjanglainnya,penilaian_medis_ranap_neonatus.diagnosis,"+
                        "penilaian_medis_ranap_neonatus.tata,penilaian_medis_ranap_neonatus.edukasi from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join penilaian_medis_ranap_neonatus on reg_periksa.no_rawat=penilaian_medis_ranap_neonatus.no_rawat "+
                        "inner join dokter on penilaian_medis_ranap_neonatus.kd_dokter=dokter.kd_dokter "+
                        "inner join pasien as ibupasien on ibupasien.no_rkm_medis=penilaian_medis_ranap_neonatus.no_rkm_medis_ibu where "+
                        "penilaian_medis_ranap_neonatus.tanggal between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or "+
                        "penilaian_medis_ranap_neonatus.kd_dokter like ? or dokter.nm_dokter like ?) order by penilaian_medis_ranap_neonatus.tanggal");
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
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("tgl_lahir"),rs.getString("jk"),rs.getString("umurdaftar")+" "+rs.getString("sttsumur"),rs.getString("no_ktp"),
                        rs.getString("no_rkm_medis_ibu"),rs.getString("nama_ibu"),rs.getString("lahiribu"),rs.getString("ktpibu"),rs.getString("kd_dokter"),rs.getString("nm_dokter"),rs.getString("tanggal"),rs.getString("g"),rs.getString("p"),rs.getString("a"),
                        rs.getString("hidup"),rs.getString("usiahamil"),rs.getString("hbsag"),rs.getString("hiv"),rs.getString("syphilis"),rs.getString("riwayat_obstetri_ibu"),rs.getString("keterangan_riwayat_obstetri_ibu"),
                        rs.getString("faktor_risiko_neonatal"),rs.getString("keterangan_faktor_risiko_neonatal"),rs.getString("tanggal_persalinan"),rs.getString("bersalin_di"),rs.getString("inisiasi_menyusui"),rs.getString("jenis_persalinan"),
                        rs.getString("indikasi"),rs.getString("aterm"),rs.getString("bernafas"),rs.getString("tanus_otot"),rs.getString("cairan_amnion"),rs.getString("f1"),rs.getString("u1"),rs.getString("t1"),rs.getString("r1"),
                        rs.getString("w1"),rs.getString("n1"),rs.getString("f5"),rs.getString("u5"),rs.getString("t5"),rs.getString("r5"),rs.getString("w5"),rs.getString("n5"),rs.getString("f10"),rs.getString("u10"),rs.getString("t10"),
                        rs.getString("r10"),rs.getString("w10"),rs.getString("n10"),rs.getString("frekuensi_napas"),rs.getString("nilai_frekuensi_napas"),rs.getString("retraksi"),rs.getString("nilai_retraksi"),rs.getString("sianosis"),
                        rs.getString("nilai_sianosis"),rs.getString("jalan_masuk_udara"),rs.getString("nilai_jalan_masuk_udara"),rs.getString("grunting"),rs.getString("nilai_grunting"),rs.getString("total_down_score"),
                        rs.getString("keterangan_down_Score"),rs.getString("nadi"),rs.getString("rr"),rs.getString("suhu"),rs.getString("saturasi"),rs.getString("bb"),rs.getString("pb"),rs.getString("lk"),rs.getString("ld"),
                        rs.getString("keadaan_umum"),rs.getString("keterangan_keadaan_umum"),rs.getString("kulit"),rs.getString("keterangan_kulit"),rs.getString("kepala"),rs.getString("keterangan_kepala"),rs.getString("mata"),
                        rs.getString("keterangan_mata"),rs.getString("telinga"),rs.getString("keterangan_telinga"),rs.getString("hidung"),rs.getString("keterangan_hidung"),rs.getString("mulut"),rs.getString("keterangan_mulut"),
                        rs.getString("tenggorokan"),rs.getString("keterangan_tenggorokan"),rs.getString("leher"),rs.getString("keterangan_leher"),rs.getString("thorax"),rs.getString("keterangan_thorax"),rs.getString("abdomen"),
                        rs.getString("keterangan_abdomen"),rs.getString("genitalia"),rs.getString("keterangan_genitalia"),rs.getString("anus"),rs.getString("keterangan_anus"),rs.getString("muskulos"),rs.getString("keterangan_muskulos"),
                        rs.getString("ekstrimitas"),rs.getString("keterangan_ekstrimitas"),rs.getString("paru"),rs.getString("keterangan_paru"),rs.getString("refleks"),rs.getString("keterangan_refleks"),rs.getString("kelainan_lainnya"),
                        rs.getString("pemeriksaan_regional"),rs.getString("lab"),rs.getString("radiologi"),rs.getString("penunjanglainnya"),rs.getString("diagnosis"),rs.getString("tata"),rs.getString("edukasi")
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
        NoRMIbu.setText("");
        NmIbu.setText("");
        TglLahirIbu.setText("");
        NIKIbu.setText("");
        StatusPerkawinanIbu.setText("");
        PenyakitIbu.setSelectedIndex(0);
        KeteranganPenyakitIbu.setText("");
        ObatSelamaHamil.setText("");
        PerawatanAntenatal.setSelectedIndex(0);
        KeteranganPerawatanAntenatal.setText("");
        TerdaftarEkohort.setSelectedIndex(0);
        KeteranganTerdaftarEkohort.setText("");
        PenyulitKehamilan.setSelectedIndex(0);
        KeteranganPenyulitKehamilan.setText("");
        AlergiIbu.setText("");
        KeteranganRiwayatMaternal.setText("");
        UmurKehamilan.setText("");
        Kehamilan.setSelectedIndex(0);
        KeteranganKehamilan.setText("");
        UrutanKehamilan.setText("");
        JamKetubanPecah.setText("");
        MenitKetubanPecah.setText("");
        JumlahAirKetuban.setText("");
        WarnaAirKetuban.setText("");
        BauAirKetuban.setText("");
        LetakBayi.setText("");
        MacamPersalinan.setSelectedIndex(0);
        KeteranganMacamPersalinan.setText("");
        IndikasiPersalinanOperatif.setSelectedIndex(0);
        KeteranganIndikasiPersalinanOperatif.setText("");
        LamaGawatJanin.setText("");
        ObatSelamaPersalinan.setText("");
        BeratPlacenta.setText("");
        KelainanPlacenta.setText("");
        KeteranganRiwayatPersalinan.setText("");
        BBLahir.setText("");
        PanjangBadan.setText("");
        LingkarKepala.setText("");
        LingkarDada.setText("");
        JenisKelamin.setSelectedIndex(0);
        ResusitasiLahir.setSelectedIndex(0);
        KeteranganResusitasiLahir.setText("");
        ObatSaatLahir.setText("");
        KeteranganKeadaanBayi.setText("");
        KondisiUmum.setSelectedIndex(0);
        KeteranganKondisiUmum.setText("");
        Kulit.setSelectedIndex(0);
        KeteranganKulit.setText("");
        Kepala.setSelectedIndex(0);
        KeteranganKepala.setText("");
        Leher.setSelectedIndex(0);
        KeteranganLeher.setText("");
        Mata.setSelectedIndex(0);
        KeteranganMata.setText("");
        Hidung.setSelectedIndex(0);
        KeteranganHidung.setText("");
        Telinga.setSelectedIndex(0);
        KeteranganTelinga.setText("");
        Dada.setSelectedIndex(0);
        KeteranganDada.setText("");
        Paru.setSelectedIndex(0);
        KeteranganParu.setText("");
        Jantung.setSelectedIndex(0);
        KeteranganJantung.setText("");
        Perut.setSelectedIndex(0);
        KeteranganPerut.setText("");
        TaliPusat.setSelectedIndex(0);
        KeteranganTaliPusat.setText("");
        AlatKelamin.setSelectedIndex(0);
        KeteranganAlatKelamin.setText("");
        RuasTulangBelakang.setSelectedIndex(0);
        KeteranganRuasTulangBelakang.setText("");
        Extrimitas.setSelectedIndex(0);
        KeteranganExtrimitas.setText("");
        Anus.setSelectedIndex(0);
        KeteranganAnus.setText("");
        Refleks.setSelectedIndex(0);
        KeteranganRefleks.setText("");
        DenyutFemoral.setSelectedIndex(0);
        KeteranganDenyutFemoral.setText("");
        PemeriksaanLainnya.setText("");
        PemeriksaanPenunjang.setText("");
        Diagnosis.setText("");
        Tatalaksana.setText("");
        Valid.tabelKosong(tabModeRiwayatKehamilan);
        Valid.tabelKosong(tabModeAPGAR);
        tabModeAPGAR.addRow(new Object[]{"Frekuensi Jantung","Tidak Ada","< 100","> 100","","",""});
        tabModeAPGAR.addRow(new Object[]{"Usaha Nafas","Tidak Ada","Lambat Tak Teratur","Menangis Kuat","","",""});
        tabModeAPGAR.addRow(new Object[]{"Tanus Otot","Lumpuh","Ext. Fleksi Sedikit","Gerakan Aktif","","",""});
        tabModeAPGAR.addRow(new Object[]{"Refleks","Tidak Ada Respon","Pergerakan Sedikit","Menangis","","",""});
        tabModeAPGAR.addRow(new Object[]{"Warna","Biru Pucat","Tubuh Kemerahan, Tangan & Kaki Biru","Kemerahan","","",""});
        N1.setText("");
        N5.setText("");
        N10.setText("");
        TabRawat.setSelectedIndex(0);
        BtnIbuBayi.requestFocus();
    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
           TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()); 
           TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString()); 
           TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString()); 
           TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString()); 
           Jk.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString()); 
           NoRMIbu.setText(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString()); 
           NmIbu.setText(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString()); 
           TglLahirIbu.setText(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString()); 
           NIKIbu.setText(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString()); 
           KdDokter.setText(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString()); 
           NmDokter.setText(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString()); 
           Valid.SetTgl2(TglAsuhan,tbObat.getValueAt(tbObat.getSelectedRow(),13).toString()); 
           tbAPGAR.setValueAt(tbObat.getValueAt(tbObat.getSelectedRow(),35).toString(),0,4); 
           tbAPGAR.setValueAt(tbObat.getValueAt(tbObat.getSelectedRow(),36).toString(),1,4); 
           tbAPGAR.setValueAt(tbObat.getValueAt(tbObat.getSelectedRow(),37).toString(),2,4); 
           tbAPGAR.setValueAt(tbObat.getValueAt(tbObat.getSelectedRow(),38).toString(),3,4); 
           tbAPGAR.setValueAt(tbObat.getValueAt(tbObat.getSelectedRow(),39).toString(),4,4); 
           N1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),40).toString()); 
           tbAPGAR.setValueAt(tbObat.getValueAt(tbObat.getSelectedRow(),41).toString(),0,5); 
           tbAPGAR.setValueAt(tbObat.getValueAt(tbObat.getSelectedRow(),42).toString(),1,5);
           tbAPGAR.setValueAt(tbObat.getValueAt(tbObat.getSelectedRow(),43).toString(),2,5); 
           tbAPGAR.setValueAt(tbObat.getValueAt(tbObat.getSelectedRow(),44).toString(),3,5); 
           tbAPGAR.setValueAt(tbObat.getValueAt(tbObat.getSelectedRow(),45).toString(),4,5); 
           N5.setText(tbObat.getValueAt(tbObat.getSelectedRow(),46).toString()); 
           tbAPGAR.setValueAt(tbObat.getValueAt(tbObat.getSelectedRow(),47).toString(),0,6); 
           tbAPGAR.setValueAt(tbObat.getValueAt(tbObat.getSelectedRow(),48).toString(),1,6); 
           tbAPGAR.setValueAt(tbObat.getValueAt(tbObat.getSelectedRow(),49).toString(),2,6); 
           tbAPGAR.setValueAt(tbObat.getValueAt(tbObat.getSelectedRow(),50).toString(),3,6); 
           tbAPGAR.setValueAt(tbObat.getValueAt(tbObat.getSelectedRow(),51).toString(), 4,6);
           N10.setText(tbObat.getValueAt(tbObat.getSelectedRow(),52).toString()); 
        }
    }

    private void isRawat() {
        try {
            ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rkm_medis,pasien.nm_pasien, if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,reg_periksa.tgl_registrasi,reg_periksa.jam_reg, "+
                    "reg_periksa.umurdaftar,reg_periksa.sttsumur,pasien.no_ktp from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
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
        BtnSimpan.setEnabled(akses.getpenilaian_bayi_baru_lahir());
        BtnHapus.setEnabled(akses.getpenilaian_bayi_baru_lahir());
        BtnEdit.setEnabled(akses.getpenilaian_bayi_baru_lahir());
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
        if(Sequel.queryu2tf("delete from penilaian_medis_ranap_neonatus where no_rawat=?",1,new String[]{
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
        /*if(Sequel.mengedittf("penilaian_medis_ranap_neonatus","no_rawat=?","no_rawat=?,tanggal=?,kd_dokter=?,no_rkm_medis_ibu=?,g=?,p=?,a=?,hidup=?,usiahamil=?,hbsag=?,hiv=?,syphilis=?,riwayat_obstetri_ibu=?,keterangan_riwayat_obstetri_ibu=?,"+
                "faktor_risiko_neonatal=?,keterangan_faktor_risiko_neonatal=?,tanggal_persalinan=?,bersalin_di=?,inisiasi_menyusui=?,jenis_persalinan=?,indikasi=?,aterm=?,bernafas=?,tanus_otot=?,cairan_amnion=?,f1=?,u1=?,t1=?,r1=?,w1=?,n1=?,f5=?,"+
                "u5=?,t5=?,r5=?,w5=?,n5=?,f10=?,u10=?,t10=?,r10=?,w10=?,n10=?,frekuensi_napas=?,nilai_frekuensi_napas=?,retraksi=?,nilai_retraksi=?,sianosis=?,nilai_sianosis=?,jalan_masuk_udara=?,nilai_jalan_masuk_udara=?,grunting=?,nilai_grunting=?,"+
                "total_down_score=?,keterangan_down_Score=?,nadi=?,rr=?,suhu=?,saturasi=?,bb=?,pb=?,lk=?,ld=?,keadaan_umum=?,keterangan_keadaan_umum=?,kulit=?,keterangan_kulit=?,kepala=?,keterangan_kepala=?,mata=?,keterangan_mata=?,telinga=?,"+
                "keterangan_telinga=?,hidung=?,keterangan_hidung=?,mulut=?,keterangan_mulut=?,tenggorokan=?,keterangan_tenggorokan=?,leher=?,keterangan_leher=?,thorax=?,keterangan_thorax=?,abdomen=?,keterangan_abdomen=?,genitalia=?,keterangan_genitalia=?,"+
                "anus=?,keterangan_anus=?,muskulos=?,keterangan_muskulos=?,ekstrimitas=?,keterangan_ekstrimitas=?,paru=?,keterangan_paru=?,refleks=?,keterangan_refleks=?,kelainan_lainnya=?,pemeriksaan_regional=?,lab=?,radiologi=?,penunjanglainnya=?,"+
                "diagnosis=?,tata=?,edukasi=?",106,new String[]{
                TNoRw.getText(),Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),KdDokter.getText(),NoRMIbu.getText(),G.getText(),P.getText(),A.getText(),Hidup.getText(),UsiaKehamilan.getText(),HbsAg.getSelectedItem().toString(),HIV.getSelectedItem().toString(), 
                Syphilis.getSelectedItem().toString(),RiwayatObstetri.getSelectedItem().toString(),KeteranganRiwayatObstetri.getText(),FaktorRisikoNeonatal.getSelectedItem().toString(),KeteranganFaktorRisikoNeonatal.getText(),Valid.SetTgl(TglJamPersalinan.getSelectedItem()+"")+" "+TglJamPersalinan.getSelectedItem().toString().substring(11,19), 
                BersalinDi.getText(),InisiasiMenyusui.getSelectedItem().toString(),JenisPersalinanBayi.getSelectedItem().toString(),IndikasiKeteranganPersalinan.getText(),Aterm.getSelectedItem().toString(),Bernafas.getSelectedItem().toString(),TonusOtotBaik.getSelectedItem().toString(),CairanAmnion.getSelectedItem().toString(),
                tbAPGAR.getValueAt(0,4).toString(),tbAPGAR.getValueAt(1,4).toString(),tbAPGAR.getValueAt(2,4).toString(),tbAPGAR.getValueAt(3,4).toString(),tbAPGAR.getValueAt(4,4).toString(),N1.getText(),tbAPGAR.getValueAt(0,5).toString(),tbAPGAR.getValueAt(1,5).toString(),tbAPGAR.getValueAt(2,5).toString(),tbAPGAR.getValueAt(3,5).toString(),
                tbAPGAR.getValueAt(4,5).toString(),N5.getText(),tbAPGAR.getValueAt(0,6).toString(),tbAPGAR.getValueAt(1,6).toString(),tbAPGAR.getValueAt(2,6).toString(),tbAPGAR.getValueAt(3,6).toString(),tbAPGAR.getValueAt(4,6).toString(),N10.getText(),FrekuensiNapas.getSelectedItem().toString(),NilaiFrekuensiNapas.getText(), 
                Retraksi.getSelectedItem().toString(),NilaiRetraksi.getText(),Sianosis.getSelectedItem().toString(),NilaiSianosis.getText(),JalanMasukUdara.getSelectedItem().toString(),NilaiJalanMasukUdara.getText(),Grunting.getSelectedItem().toString(),NilaiGrunting.getText(),TotalNilaiDownScore.getText(),KeteranganDownScore.getText(), 
                Nadi.getText(),RR.getText(),Suhu.getText(),Saturasi.getText(),BeratBadan.getText(),PanjangBadan.getText(),LingkarKepala.getText(),LingkarDada.getText(),KondisiUmum.getSelectedItem().toString(),KeteranganKondisiUmum.getText(),Kulit.getSelectedItem().toString(),KeteranganKulit.getText(),Kepala.getSelectedItem().toString(), 
                KeteranganKepala.getText(),Mata.getSelectedItem().toString(),KeteranganMata.getText(),Telinga.getSelectedItem().toString(),KeteranganTelinga.getText(),Hidung.getSelectedItem().toString(),KeteranganHidung.getText(),Mulut.getSelectedItem().toString(),KeteranganMulut.getText(),Tenggorokan.getSelectedItem().toString(),
                KeteranganTenggorokan.getText(),Leher.getSelectedItem().toString(),KeteranganLeher.getText(),Thorax.getSelectedItem().toString(),KeteranganThorax.getText(),Abdomen.getSelectedItem().toString(),KeteranganAbdomen.getText(),Genitalia.getSelectedItem().toString(),KeteranganGenitalia.getText(),Anus.getSelectedItem().toString(), 
                KeteranganAnus.getText(),Muskulos.getSelectedItem().toString(),KeteranganMuskulos.getText(),Ekstrimitas.getSelectedItem().toString(),KeteranganEkstrimitas.getText(),Paru.getSelectedItem().toString(),KeteranganParu.getText(),Refleks.getSelectedItem().toString(),KeteranganRefleks.getText(),KelainanLainnya.getText(), 
                PemeriksaanRegional.getText(),Laborat.getText(),Radiologi.getText(),Penunjang.getText(),Diagnosis.getText(),Tatalaksana.getText(),Edukasi.getText(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
            })==true){
               tbObat.setValueAt(TNoRw.getText(),tbObat.getSelectedRow(),0);
               tbObat.setValueAt(TNoRM.getText(),tbObat.getSelectedRow(),1);
               tbObat.setValueAt(TPasien.getText(),tbObat.getSelectedRow(),2);
               tbObat.setValueAt(TglLahir.getText(),tbObat.getSelectedRow(),3);
               tbObat.setValueAt(Jk.getText(),tbObat.getSelectedRow(),4);
               tbObat.setValueAt(UmurBayi.getText(),tbObat.getSelectedRow(),5);
               tbObat.setValueAt(NIKBayi.getText(),tbObat.getSelectedRow(),6);
               tbObat.setValueAt(NoRMIbu.getText(),tbObat.getSelectedRow(),7);
               tbObat.setValueAt(NmIbu.getText(),tbObat.getSelectedRow(),8);
               tbObat.setValueAt(TglLahirIbu.getText(),tbObat.getSelectedRow(),9);
               tbObat.setValueAt(NIKIbu.getText(),tbObat.getSelectedRow(),10);
               tbObat.setValueAt(KdDokter.getText(),tbObat.getSelectedRow(),11);
               tbObat.setValueAt(NmDokter.getText(),tbObat.getSelectedRow(),12);
               tbObat.setValueAt(Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),tbObat.getSelectedRow(),13);
               tbObat.setValueAt(G.getText(),tbObat.getSelectedRow(),14);
               tbObat.setValueAt(P.getText(),tbObat.getSelectedRow(),15);
               tbObat.setValueAt(A.getText(),tbObat.getSelectedRow(),16);
               tbObat.setValueAt(Hidup.getText(),tbObat.getSelectedRow(),17);
               tbObat.setValueAt(UsiaKehamilan.getText(),tbObat.getSelectedRow(),18);
               tbObat.setValueAt(HbsAg.getSelectedItem().toString(),tbObat.getSelectedRow(),19);
               tbObat.setValueAt(HIV.getSelectedItem().toString(),tbObat.getSelectedRow(),20);
               tbObat.setValueAt(Syphilis.getSelectedItem().toString(),tbObat.getSelectedRow(),21);
               tbObat.setValueAt(RiwayatObstetri.getSelectedItem().toString(),tbObat.getSelectedRow(),22);
               tbObat.setValueAt(KeteranganRiwayatObstetri.getText(),tbObat.getSelectedRow(),23);
               tbObat.setValueAt(FaktorRisikoNeonatal.getSelectedItem().toString(),tbObat.getSelectedRow(),24);
               tbObat.setValueAt(KeteranganFaktorRisikoNeonatal.getText(),tbObat.getSelectedRow(),25);
               tbObat.setValueAt(Valid.SetTgl(TglJamPersalinan.getSelectedItem()+"")+" "+TglJamPersalinan.getSelectedItem().toString().substring(11,19),tbObat.getSelectedRow(),26);
               tbObat.setValueAt(BersalinDi.getText(),tbObat.getSelectedRow(),27);
               tbObat.setValueAt(InisiasiMenyusui.getSelectedItem().toString(),tbObat.getSelectedRow(),28);
               tbObat.setValueAt(JenisPersalinanBayi.getSelectedItem().toString(),tbObat.getSelectedRow(),29);
               tbObat.setValueAt(IndikasiKeteranganPersalinan.getText(),tbObat.getSelectedRow(),30);
               tbObat.setValueAt(Aterm.getSelectedItem().toString(),tbObat.getSelectedRow(),31);
               tbObat.setValueAt(Bernafas.getSelectedItem().toString(),tbObat.getSelectedRow(),32);
               tbObat.setValueAt(TonusOtotBaik.getSelectedItem().toString(),tbObat.getSelectedRow(),33);
               tbObat.setValueAt(CairanAmnion.getSelectedItem().toString(),tbObat.getSelectedRow(),34);
               tbObat.setValueAt(tbAPGAR.getValueAt(0,4).toString(),tbObat.getSelectedRow(),35);
               tbObat.setValueAt(tbAPGAR.getValueAt(1,4).toString(),tbObat.getSelectedRow(),36);
               tbObat.setValueAt(tbAPGAR.getValueAt(2,4).toString(),tbObat.getSelectedRow(),37);
               tbObat.setValueAt(tbAPGAR.getValueAt(3,4).toString(),tbObat.getSelectedRow(),38);
               tbObat.setValueAt(tbAPGAR.getValueAt(4,4).toString(),tbObat.getSelectedRow(),39);
               tbObat.setValueAt(N1.getText(),tbObat.getSelectedRow(),40);
               tbObat.setValueAt(tbAPGAR.getValueAt(0,5).toString(),tbObat.getSelectedRow(),41);
               tbObat.setValueAt(tbAPGAR.getValueAt(1,5).toString(),tbObat.getSelectedRow(),42);
               tbObat.setValueAt(tbAPGAR.getValueAt(2,5).toString(),tbObat.getSelectedRow(),43);
               tbObat.setValueAt(tbAPGAR.getValueAt(3,5).toString(),tbObat.getSelectedRow(),44);
               tbObat.setValueAt(tbAPGAR.getValueAt(4,5).toString(),tbObat.getSelectedRow(),45);
               tbObat.setValueAt(N5.getText(),tbObat.getSelectedRow(),46);
               tbObat.setValueAt(tbAPGAR.getValueAt(0,6).toString(),tbObat.getSelectedRow(),47);
               tbObat.setValueAt(tbAPGAR.getValueAt(1,6).toString(),tbObat.getSelectedRow(),48);
               tbObat.setValueAt(tbAPGAR.getValueAt(2,6).toString(),tbObat.getSelectedRow(),49);
               tbObat.setValueAt(tbAPGAR.getValueAt(3,6).toString(),tbObat.getSelectedRow(),50);
               tbObat.setValueAt(tbAPGAR.getValueAt(4,6).toString(),tbObat.getSelectedRow(),51);
               tbObat.setValueAt(N10.getText(),tbObat.getSelectedRow(),52);
               tbObat.setValueAt(FrekuensiNapas.getSelectedItem().toString(),tbObat.getSelectedRow(),53);
               tbObat.setValueAt(NilaiFrekuensiNapas.getText(),tbObat.getSelectedRow(),54);
               tbObat.setValueAt(Retraksi.getSelectedItem().toString(),tbObat.getSelectedRow(),55);
               tbObat.setValueAt(NilaiRetraksi.getText(),tbObat.getSelectedRow(),56);
               tbObat.setValueAt(Sianosis.getSelectedItem().toString(),tbObat.getSelectedRow(),57);
               tbObat.setValueAt(NilaiSianosis.getText(),tbObat.getSelectedRow(),58);
               tbObat.setValueAt(JalanMasukUdara.getSelectedItem().toString(),tbObat.getSelectedRow(),59);
               tbObat.setValueAt(NilaiJalanMasukUdara.getText(),tbObat.getSelectedRow(),60);
               tbObat.setValueAt(Grunting.getSelectedItem().toString(),tbObat.getSelectedRow(),61);
               tbObat.setValueAt(NilaiGrunting.getText(),tbObat.getSelectedRow(),62);
               tbObat.setValueAt(TotalNilaiDownScore.getText(),tbObat.getSelectedRow(),63);
               tbObat.setValueAt(KeteranganDownScore.getText(),tbObat.getSelectedRow(),64);
               tbObat.setValueAt(Nadi.getText(),tbObat.getSelectedRow(),65);
               tbObat.setValueAt(RR.getText(),tbObat.getSelectedRow(),66);
               tbObat.setValueAt(Suhu.getText(),tbObat.getSelectedRow(),67);
               tbObat.setValueAt(Saturasi.getText(),tbObat.getSelectedRow(),68);
               tbObat.setValueAt(BeratBadan.getText(),tbObat.getSelectedRow(),69);
               tbObat.setValueAt(PanjangBadan.getText(),tbObat.getSelectedRow(),70);
               tbObat.setValueAt(LingkarKepala.getText(),tbObat.getSelectedRow(),71);
               tbObat.setValueAt(LingkarDada.getText(),tbObat.getSelectedRow(),72);
               tbObat.setValueAt(KondisiUmum.getSelectedItem().toString(),tbObat.getSelectedRow(),73);
               tbObat.setValueAt(KeteranganKondisiUmum.getText(),tbObat.getSelectedRow(),74);
               tbObat.setValueAt(Kulit.getSelectedItem().toString(),tbObat.getSelectedRow(),75);
               tbObat.setValueAt(KeteranganKulit.getText(),tbObat.getSelectedRow(),76);
               tbObat.setValueAt(Kepala.getSelectedItem().toString(),tbObat.getSelectedRow(),77);
               tbObat.setValueAt(KeteranganKepala.getText(),tbObat.getSelectedRow(),78);
               tbObat.setValueAt(Mata.getSelectedItem().toString(),tbObat.getSelectedRow(),79);
               tbObat.setValueAt(KeteranganMata.getText(),tbObat.getSelectedRow(),80);
               tbObat.setValueAt(Telinga.getSelectedItem().toString(),tbObat.getSelectedRow(),81);
               tbObat.setValueAt(KeteranganTelinga.getText(),tbObat.getSelectedRow(),82);
               tbObat.setValueAt(Hidung.getSelectedItem().toString(),tbObat.getSelectedRow(),83);
               tbObat.setValueAt(KeteranganHidung.getText(),tbObat.getSelectedRow(),84);
               tbObat.setValueAt(Mulut.getSelectedItem().toString(),tbObat.getSelectedRow(),85);
               tbObat.setValueAt(KeteranganMulut.getText(),tbObat.getSelectedRow(),86);
               tbObat.setValueAt(Tenggorokan.getSelectedItem().toString(),tbObat.getSelectedRow(),87);
               tbObat.setValueAt(KeteranganTenggorokan.getText(),tbObat.getSelectedRow(),88);
               tbObat.setValueAt(Leher.getSelectedItem().toString(),tbObat.getSelectedRow(),89);
               tbObat.setValueAt(KeteranganLeher.getText(),tbObat.getSelectedRow(),90);
               tbObat.setValueAt(Thorax.getSelectedItem().toString(),tbObat.getSelectedRow(),91);
               tbObat.setValueAt(KeteranganThorax.getText(),tbObat.getSelectedRow(),92);
               tbObat.setValueAt(Abdomen.getSelectedItem().toString(),tbObat.getSelectedRow(),93);
               tbObat.setValueAt(KeteranganAbdomen.getText(),tbObat.getSelectedRow(),94);
               tbObat.setValueAt(Genitalia.getSelectedItem().toString(),tbObat.getSelectedRow(),95);
               tbObat.setValueAt(KeteranganGenitalia.getText(),tbObat.getSelectedRow(),96);
               tbObat.setValueAt(Anus.getSelectedItem().toString(),tbObat.getSelectedRow(),97);
               tbObat.setValueAt(KeteranganAnus.getText(),tbObat.getSelectedRow(),98);
               tbObat.setValueAt(Muskulos.getSelectedItem().toString(),tbObat.getSelectedRow(),99);
               tbObat.setValueAt(KeteranganMuskulos.getText(),tbObat.getSelectedRow(),100);
               tbObat.setValueAt(Ekstrimitas.getSelectedItem().toString(),tbObat.getSelectedRow(),101);
               tbObat.setValueAt(KeteranganEkstrimitas.getText(),tbObat.getSelectedRow(),102);
               tbObat.setValueAt(Paru.getSelectedItem().toString(),tbObat.getSelectedRow(),103);
               tbObat.setValueAt(KeteranganParu.getText(),tbObat.getSelectedRow(),104);
               tbObat.setValueAt(Refleks.getSelectedItem().toString(),tbObat.getSelectedRow(),105);
               tbObat.setValueAt(KeteranganRefleks.getText(),tbObat.getSelectedRow(),106);
               tbObat.setValueAt(KelainanLainnya.getText(),tbObat.getSelectedRow(),107);
               tbObat.setValueAt(PemeriksaanRegional.getText(),tbObat.getSelectedRow(),108);
               tbObat.setValueAt(Laborat.getText(),tbObat.getSelectedRow(),109);
               tbObat.setValueAt(Radiologi.getText(),tbObat.getSelectedRow(),110);
               tbObat.setValueAt(Penunjang.getText(),tbObat.getSelectedRow(),111);
               tbObat.setValueAt(Diagnosis.getText(),tbObat.getSelectedRow(),112);
               tbObat.setValueAt(Tatalaksana.getText(),tbObat.getSelectedRow(),113);
               tbObat.setValueAt(Edukasi.getText(),tbObat.getSelectedRow(),114);
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
    
    private void getNilaiDownScore() {
        /*try {
            if(NilaiFrekuensiNapas.getText().equals("")||NilaiRetraksi.getText().equals("")||NilaiSianosis.getText().equals("")||NilaiJalanMasukUdara.getText().equals("")||NilaiGrunting.getText().equals("")){
                TotalNilaiDownScore.setText("0");
            }else{
                TotalNilaiDownScore.setText((Integer.parseInt(NilaiFrekuensiNapas.getText())+Integer.parseInt(NilaiRetraksi.getText())+Integer.parseInt(NilaiSianosis.getText())+Integer.parseInt(NilaiJalanMasukUdara.getText())+Integer.parseInt(NilaiGrunting.getText()))+"");
            }
        } catch (Exception e) {
            TotalNilaiDownScore.setText("0");
        }*/
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
            ps=koneksi.prepareStatement("select * from riwayat_persalinan_pasien where riwayat_persalinan_pasien.no_rkm_medis=? order by riwayat_persalinan_pasien.tgl_thn");
            try {
                ps.setString(1,NoRMIbu.getText());
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

    private void simpan() {
        /*if(Sequel.menyimpantf("penilaian_medis_ranap_neonatus","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat",105,new String[]{
                TNoRw.getText(),Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),KdDokter.getText(),NoRMIbu.getText(),G.getText(),P.getText(),A.getText(),Hidup.getText(),UsiaKehamilan.getText(),HbsAg.getSelectedItem().toString(),HIV.getSelectedItem().toString(), 
                Syphilis.getSelectedItem().toString(),RiwayatObstetri.getSelectedItem().toString(),KeteranganRiwayatObstetri.getText(),FaktorRisikoNeonatal.getSelectedItem().toString(),KeteranganFaktorRisikoNeonatal.getText(),Valid.SetTgl(TglJamPersalinan.getSelectedItem()+"")+" "+TglJamPersalinan.getSelectedItem().toString().substring(11,19), 
                BersalinDi.getText(),InisiasiMenyusui.getSelectedItem().toString(),JenisPersalinanBayi.getSelectedItem().toString(),IndikasiKeteranganPersalinan.getText(),Aterm.getSelectedItem().toString(),Bernafas.getSelectedItem().toString(),TonusOtotBaik.getSelectedItem().toString(),CairanAmnion.getSelectedItem().toString(),
                tbAPGAR.getValueAt(0,4).toString(),tbAPGAR.getValueAt(1,4).toString(),tbAPGAR.getValueAt(2,4).toString(),tbAPGAR.getValueAt(3,4).toString(),tbAPGAR.getValueAt(4,4).toString(),N1.getText(),tbAPGAR.getValueAt(0,5).toString(),tbAPGAR.getValueAt(1,5).toString(),tbAPGAR.getValueAt(2,5).toString(),tbAPGAR.getValueAt(3,5).toString(),
                tbAPGAR.getValueAt(4,5).toString(),N5.getText(),tbAPGAR.getValueAt(0,6).toString(),tbAPGAR.getValueAt(1,6).toString(),tbAPGAR.getValueAt(2,6).toString(),tbAPGAR.getValueAt(3,6).toString(),tbAPGAR.getValueAt(4,6).toString(),N10.getText(),FrekuensiNapas.getSelectedItem().toString(),NilaiFrekuensiNapas.getText(), 
                Retraksi.getSelectedItem().toString(),NilaiRetraksi.getText(),Sianosis.getSelectedItem().toString(),NilaiSianosis.getText(),JalanMasukUdara.getSelectedItem().toString(),NilaiJalanMasukUdara.getText(),Grunting.getSelectedItem().toString(),NilaiGrunting.getText(),TotalNilaiDownScore.getText(),KeteranganDownScore.getText(), 
                Nadi.getText(),RR.getText(),Suhu.getText(),Saturasi.getText(),BeratBadan.getText(),PanjangBadan.getText(),LingkarKepala.getText(),LingkarDada.getText(),KondisiUmum.getSelectedItem().toString(),KeteranganKondisiUmum.getText(),Kulit.getSelectedItem().toString(),KeteranganKulit.getText(),Kepala.getSelectedItem().toString(), 
                KeteranganKepala.getText(),Mata.getSelectedItem().toString(),KeteranganMata.getText(),Telinga.getSelectedItem().toString(),KeteranganTelinga.getText(),Hidung.getSelectedItem().toString(),KeteranganHidung.getText(),Mulut.getSelectedItem().toString(),KeteranganMulut.getText(),Tenggorokan.getSelectedItem().toString(),
                KeteranganTenggorokan.getText(),Leher.getSelectedItem().toString(),KeteranganLeher.getText(),Thorax.getSelectedItem().toString(),KeteranganThorax.getText(),Abdomen.getSelectedItem().toString(),KeteranganAbdomen.getText(),Genitalia.getSelectedItem().toString(),KeteranganGenitalia.getText(),Anus.getSelectedItem().toString(), 
                KeteranganAnus.getText(),Muskulos.getSelectedItem().toString(),KeteranganMuskulos.getText(),Ekstrimitas.getSelectedItem().toString(),KeteranganEkstrimitas.getText(),Paru.getSelectedItem().toString(),KeteranganParu.getText(),Refleks.getSelectedItem().toString(),KeteranganRefleks.getText(),KelainanLainnya.getText(), 
                PemeriksaanRegional.getText(),Laborat.getText(),Radiologi.getText(),Penunjang.getText(),Diagnosis.getText(),Tatalaksana.getText(),Edukasi.getText()
            })==true){
                tabMode.addRow(new String[]{
                    TNoRw.getText(),TNoRM.getText(),TPasien.getText(),TglLahir.getText(),Jk.getText(),UmurBayi.getText(),NIKBayi.getText(),NoRMIbu.getText(),NmIbu.getText(),TglLahirIbu.getText(),NIKIbu.getText(),KdDokter.getText(),NmDokter.getText(),Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),G.getText(),
                    P.getText(),A.getText(),Hidup.getText(),UsiaKehamilan.getText(),HbsAg.getSelectedItem().toString(),HIV.getSelectedItem().toString(),Syphilis.getSelectedItem().toString(),RiwayatObstetri.getSelectedItem().toString(),KeteranganRiwayatObstetri.getText(),FaktorRisikoNeonatal.getSelectedItem().toString(),KeteranganFaktorRisikoNeonatal.getText(),
                    Valid.SetTgl(TglJamPersalinan.getSelectedItem()+"")+" "+TglJamPersalinan.getSelectedItem().toString().substring(11,19),BersalinDi.getText(),InisiasiMenyusui.getSelectedItem().toString(),JenisPersalinanBayi.getSelectedItem().toString(),IndikasiKeteranganPersalinan.getText(),Aterm.getSelectedItem().toString(),Bernafas.getSelectedItem().toString(),
                    TonusOtotBaik.getSelectedItem().toString(),CairanAmnion.getSelectedItem().toString(),tbAPGAR.getValueAt(0,4).toString(),tbAPGAR.getValueAt(1,4).toString(),tbAPGAR.getValueAt(2,4).toString(),tbAPGAR.getValueAt(3,4).toString(),tbAPGAR.getValueAt(4,4).toString(),N1.getText(),tbAPGAR.getValueAt(0,5).toString(),tbAPGAR.getValueAt(1,5).toString(),
                    tbAPGAR.getValueAt(2,5).toString(),tbAPGAR.getValueAt(3,5).toString(),tbAPGAR.getValueAt(4,5).toString(),N5.getText(),tbAPGAR.getValueAt(0,6).toString(),tbAPGAR.getValueAt(1,6).toString(),tbAPGAR.getValueAt(2,6).toString(),tbAPGAR.getValueAt(3,6).toString(),tbAPGAR.getValueAt(4,6).toString(),N10.getText(),FrekuensiNapas.getSelectedItem().toString(),
                    NilaiFrekuensiNapas.getText(),Retraksi.getSelectedItem().toString(),NilaiRetraksi.getText(),Sianosis.getSelectedItem().toString(),NilaiSianosis.getText(),JalanMasukUdara.getSelectedItem().toString(),NilaiJalanMasukUdara.getText(),Grunting.getSelectedItem().toString(),NilaiGrunting.getText(),TotalNilaiDownScore.getText(),KeteranganDownScore.getText(), 
                    Nadi.getText(),RR.getText(),Suhu.getText(),Saturasi.getText(),BeratBadan.getText(),PanjangBadan.getText(),LingkarKepala.getText(),LingkarDada.getText(),KondisiUmum.getSelectedItem().toString(),KeteranganKondisiUmum.getText(),Kulit.getSelectedItem().toString(),KeteranganKulit.getText(),Kepala.getSelectedItem().toString(), 
                    KeteranganKepala.getText(),Mata.getSelectedItem().toString(),KeteranganMata.getText(),Telinga.getSelectedItem().toString(),KeteranganTelinga.getText(),Hidung.getSelectedItem().toString(),KeteranganHidung.getText(),Mulut.getSelectedItem().toString(),KeteranganMulut.getText(),Tenggorokan.getSelectedItem().toString(),
                    KeteranganTenggorokan.getText(),Leher.getSelectedItem().toString(),KeteranganLeher.getText(),Thorax.getSelectedItem().toString(),KeteranganThorax.getText(),Abdomen.getSelectedItem().toString(),KeteranganAbdomen.getText(),Genitalia.getSelectedItem().toString(),KeteranganGenitalia.getText(),Anus.getSelectedItem().toString(), 
                    KeteranganAnus.getText(),Muskulos.getSelectedItem().toString(),KeteranganMuskulos.getText(),Ekstrimitas.getSelectedItem().toString(),KeteranganEkstrimitas.getText(),Paru.getSelectedItem().toString(),KeteranganParu.getText(),Refleks.getSelectedItem().toString(),KeteranganRefleks.getText(),KelainanLainnya.getText(), 
                    PemeriksaanRegional.getText(),Laborat.getText(),Radiologi.getText(),Penunjang.getText(),Diagnosis.getText(),Tatalaksana.getText(),Edukasi.getText()
                });
                LCount.setText(""+tabMode.getRowCount());
                emptTeks();
        }*/
    }
}
